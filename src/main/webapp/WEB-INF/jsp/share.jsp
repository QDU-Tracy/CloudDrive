<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: Tracy
  Date: 2017/4/16
  Time: 10:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<c:set scope="request" var="share" value="${shareInfo}"/>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Shared File</title>

    <meta name="description" content="Source code generated using layoutit.com">
    <meta name="author" content="LayoutIt!">

    <link href="/css/bootstrap.min.css" rel="stylesheet">

</head>
<body>
<div class="container-fluid">
    <div class="row">
        <div class="col-md-12">
            <h3 class="text-center">
                <c:out value="${share.customName}"></c:out>
            </h3>
            <ul>
                <li>
                    文件名：<c:out value="${share.customName}"></c:out>
                </li>
                <%--时间格式化输出--%>
                <li>
                    修改时间：<fmt:formatDate value="${share.createTime}" pattern="yyyy-MM-dd HH:mm"/>
                </li>

                <%--注意这里的数字输出格式,保留了两位，用fmt标签格式化输出--%>

                <li>
                    文件大小：
                    <fmt:formatNumber var="f" value="${share.fileSize/1048576}" pattern="#.00"/>
                    <c:out value="${f}MB"></c:out>
                </li>

                <li>
                    分享人：<c:out value="${share.userName}"></c:out>
                </li>
            </ul>
            <%--<form>--%>
                <%--<input id="record-md5-input" type="hidden" value="">--%>
            <%--</form>--%>
            <button type="button" id="preview-button" record-md5="${share.recordMd5}" class="btn btn-lg btn-block btn-primary">
                在线预览文件
            </button>
            <button type="button" id="share-download" onclick="window.open('/file/download/${share.recordMd5}')" record-md5="${share.recordMd5}" class="btn btn-lg btn-block btn-primary">
                Download
            </button>
        </div>
    </div>
</div>
<div class="modal fade" id="preview-modal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h2 class="modal-title text-center">Preview</h2>
            </div>
            <div class="modal-body">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">${share.customName}</h3>
                    </div>
                    <div class="panel-body" >
                        <p id="text-main" style="word-wrap: break-word"></p>
                        <img id="pic_preview" src="" alt="">
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <a class="btn btn-default" data-dismiss="modal">Close</a>
            </div>
        </div>
    </div>
</div>

<script src="/js/jquery.min.js"></script>
<script src="/js/bootstrap.min.js"></script>
<script>
    //重命名按钮点击
    $('#preview-button').click(function () {
        $('#preview-modal').modal("toggle");
        var recordMd5;
        recordMd5 = $(this).attr("record-md5");

        $.ajax({
            type: "GET",
            url: "/file" + "/test/"+recordMd5,
            success: function (data) {
                console.info(data);

                if (data['type']==1){
                    //预览类型为文本
                    var array = data['text'].split('\r');
                    for (var i=0;i<array.length ;i++ )
                    {
//                    追加元素
                        $('#text-main').append(array[i] + "<br/>");
                    }
                }else if(data['type']==2){
                    //预览类型为图片
                    var url = data['text'];

                    $('#pic_preview').attr('src',url) ;
                }

            }
        });
    });
</script>
</body>
</html>
