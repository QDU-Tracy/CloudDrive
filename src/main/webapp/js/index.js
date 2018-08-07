/**
 * Created by TracyM on 2017/3/23.
 */
$(document).ready(function () {
    $('#login-modal-button').click(function () {
        $('#login-modal').modal('show');
    });
    $('#login-button').click(function () {
        $.ajax({
            url: '/user/login',
            type: 'POST',
            data: $("#login-form").serialize(),
            timeout: 100000,
            success: function (data) {
                console.log(data["success"]);
                if(data["success"]==1){
                    $('#login-modal').modal('hide');
                    location.href ='/file/list';
                }else {
                    alert("Error");
                }
            }
        });
    });
});