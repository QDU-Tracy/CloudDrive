
var blobSlice = File.prototype.slice || File.prototype.mozSlice || File.prototype.webkitSlice,
    log = document.getElementById('log'),
    input = document.getElementById('file'),
    running = false,
    ua = navigator.userAgent.toLowerCase();

function registerLog(str, className) {
    var elem = document.createElement('div');

    elem.innerHTML = str;
    elem.className = 'alert-message' + (className ? ' '  + className : '');
    log.appendChild(elem);
}

function doIncrementalTest() {
    if (running) {
        return;
    }

    if (!input.files.length) {
        registerLog('<strong>Please select a file.</strong><br/>');
        return;
    }

    var blobSlice = File.prototype.slice || File.prototype.mozSlice || File.prototype.webkitSlice,
        file = input.files[0],
        chunkSize = 2097152,                           // read in chunks of 2MB
        chunks = Math.ceil(file.size / chunkSize),
        currentChunk = 0,
        spark = new SparkMD5.ArrayBuffer(),
        time,
        uniqueId = 'chunk_' + (new Date().getTime()),
        chunkId = null,
        fileReader = new FileReader();

    fileReader.onload = function (e) {
        if (currentChunk === 0) {
            registerLog('Read chunk number <strong id="' + uniqueId + '">' + (currentChunk + 1) + '</strong> of <strong>' + chunks + '</strong><br/>', 'info');
        } else {
            if (chunkId === null) {
                chunkId = document.getElementById(uniqueId);
            }

            chunkId.innerHTML = currentChunk + 1;
        }

        spark.append(e.target.result);                 // append array buffer
        currentChunk += 1;

        if (currentChunk < chunks) {
            loadNext();
        } else {
            running = false;
            var md5=spark.end();
            registerLog('<strong>Finished loading!</strong><br/>', 'success');
            registerLog('<strong>Computed hash:</strong> ' + md5 + '<br/>', 'success'); // compute hash

            console.info(md5);
            $('#md5-input').val(md5);
            console.info(Date());
            $('#create-time-input').val(Date());

            //获取上传文件的文件名和路径
            var file=$("input[name='file']").val()
            var filename=file.replace(/.*(\/|\\)/, "");
            var fileExt=(/[.]/.exec(filename)) ? /[^.]+$/.exec(filename.toLowerCase()) : '';

            $('#cuntom-name-input').val(filename);

            registerLog('<strong>Total time:</strong> ' + (new Date().getTime() - time) + 'ms<br/>', 'success');

        }
    };

    fileReader.onerror = function () {
        running = false;
        registerLog('<strong>Oops, something went wrong.</strong>', 'error');
    };

    function loadNext() {
        var start = currentChunk * chunkSize,
            end = start + chunkSize >= file.size ? file.size : start + chunkSize;

        fileReader.readAsArrayBuffer(blobSlice.call(file, start, end));
    }

    running = true;
    registerLog('<p></p><strong>Starting incremental test (' + file.name + ')</strong><br/>', 'info');
    time = new Date().getTime();
    loadNext();
}

function clearLog() {
    if (!running) {
        log.innerHTML = '';
    }
}

if (!('FileReader' in window) || !('File' in window) || !blobSlice) {
    registerLog('<p><strong>Your browser does not support the FileAPI or slicing of files.</strong></p>', 'error');
} else {
    registerLog('Keep your devtools closed otherwise this example will be a LOT slower', 'info');

    if (/chrome/.test(ua)) {
        if (location.protocol === 'file:') {
            registerLog('<p><strong>This example might not work in chrome because you are using the file:// protocol.</strong><br/>You can try to start chrome with -allow-file-access-from-files argument or spawn a local server instead. This is a security measure introduced in chrome, please <a target=\'_blank\' href=\'http://code.google.com/p/chromium/issues/detail?id=60889\'>see</a>.</p>');
        }
    }

    //document.getElementById('normal').addEventListener('click', doNormalTest);
    document.getElementById('check-button').addEventListener('click', doIncrementalTest);
    //document.getElementById('clear').addEventListener('click', clearLog);
}

$(document).ready(function () {
    var userPhone = $('#session-tag').text();
    var recordId,originalName,share,fileId,customName;
    var md5;
    console.info(userPhone);

    $('#update-new-file-button').click(function () {
        $('#upload-modal').modal("toggle");
    })

    $('#upload-button').click(function () {
        if ($('#share-input').is(":checked")) {
            $('#share-input-hidden').val(1);
            console.info("checked");
        } else {
            $('#share-input-hidden').val(0);
            console.info("unchecked");
        }

        var formData = new FormData($('#upload-form')[0]);

        $.ajax({
            url: '/file/upload',
            type: 'POST',
            data: formData,
            contentType:false,
            processData:false,
            // datatype: 'json',
            // timeout: 100000,
            success: function (data) {
                // if(data["userId"]!=null){
                //     $('#login-modal').modal('hide');
                //     location.href ='/file/'+data['userId']+'/main';
                // }else {
                //     alert("Error");
                // }
                $('#upload-modal').modal('toggle');
            }
        });
    });

    //获取以-recordId结尾的所有元素
    $("[id$='-recordId']").click(function () {

        //获取元素的的隐藏值
        recordId = $(this).attr("recordId");
        originalName = $(this).attr("customName");
        share = $(this).attr("share");

        console.info(recordId+originalName+share);

        //将modal中的checkbox和customName赋默认值
        $('#rename-input').val(originalName);
        if(share==1){
            $('#share-checkbox').prop("checked", "checked");
        }else if (share==0){
            console.info("Should be unchecked");
            $('#share-checkbox').removeAttr("checked");
        }

        $('#rename-modal').modal('show');

    });

    //重命名按钮点击
    $('#rename-button').click(function () {
        //先检查checkbox的状态
        if ($('#share-checkbox').is(":checked")) {
            $('#share-checkbox-hidden').val(1);
            console.info("checked"+$('#share-checkbox-hidden').val());
        } else {
            // $('#share-checkbox-hidden').val(0);
            console.info("unchecked"+$('#share-checkbox-hidden').val());
        }

        $.ajax({
            type: "POST",
            url: "/file/" + recordId + "/rename",
            data: $('#rename-form').serialize(),
            success: function (data) {
                console.info(data);
                $('#rename-modal').modal('toggle');
            }
        });
    });

    //获取以-delete结尾的所有元素,删除按钮点击事件
    $("[id$='-delete']").click(function () {
        //获取元素的的隐藏值
        recordId = $(this).attr("recordId");
        var data = {};
        data["recordId"] = recordId;
        $.ajax({
            type: "POST",
            url: "/file/delete",
            data: data,
            success: function (data) {
                console.info(data);
                $(this).remove();
            }
        });
    });


    //文件分享
    //获取以-share结尾的所有元素,删除按钮点击事件
    $("[id$='-share']").click(function () {
        //获取元素的的隐藏值
        recordId = $(this).attr("recordId");
        var data = {};
        data["recordId"] = recordId;
        $.ajax({
            type: "POST",
            url: "/file/share",
            data: data,
            success: function (data) {
                console.info(data);
                if(data["success"]==true){
                    window.open("/file/share/"+data["recordMd5"])
                }else {
                    window.alert("Already shared");
                }

            }
        });
    });

    //下载，动态渲染表单，提交表单后再删除
    $.download = function(url, data1,data2,data3, method){
        // 获得url和data
        if( url && data1 && data2 && data3){
            // data 是 string 或者 array/object
            data1 = typeof data1 == 'string' ? data1 : $.param(data1);
            data2 = typeof data2 == 'string' ? data2 : $.param(data2);
            data3 = typeof data3 == 'string' ? data3 : $.param(data3);

            // 把参数组装成 form的  input
            var inputs = '';
            $.each(data1.split('&'), function(){
                var pair = this.split('=');
                inputs+='<input type="hidden" name="'+ pair[0] +'" value="'+ pair[1] +'" />';
            });
            $.each(data2.split('&'), function(){
                var pair = this.split('=');
                inputs+='<input type="hidden" name="'+ pair[0] +'" value="'+ pair[1] +'" />';
            });
            $.each(data3.split('&'), function(){
                var pair = this.split('=');
                inputs+='<input type="hidden" name="'+ pair[0] +'" value="'+ pair[1] +'" />';
            });
            // request发送请求
            console.info(inputs);
            $('<form action="'+ url +'" method="'+ (method||'post') +'">'+inputs+'</form>')
                .appendTo('body').submit().remove();
        };
    };

    //下载按钮点击事件
    //获取以-download结尾的所有元素
    $("[id$='-download']").click(function () {
        //获取元素的的隐藏值
        fileId = $(this).attr("fileId");
        customName = $(this).attr("customName");
        recordId = $(this).attr("recordId");
        var data = {};
        data["fileId"] = fileId;
        data["customName"] = customName;
        data["recordId"] = recordId;

        $.ajax({
            type: "POST",
            url: "/file/downloadurl",
            data: data,
            success: function (value) {
                if (value=="ok"){
                    // $.ajax({
                    //     type: "GET",
                    //     url: "/file/download",
                    //     data: data,
                    //     success: function (data) {
                    //
                    //     }
                    // });

                    // window.open("/file/"+fileId+"/"+customName+"/download");
                    console.info("ok");

                    $.download("/file/download","fileId="+fileId,"customName="+customName, "recordId="+recordId,"post" );
                }
            }
        });
    });

});
