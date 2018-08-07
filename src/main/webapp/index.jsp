<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script type="text/javascript" src="http://cdnjs.cloudflare.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
    <script type="text/javascript" src="http://netdna.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/js/index.js"></script>

    <link href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet"
          type="text/css">
    <%--<link href="http://pingendo.github.io/pingendo-bootstrap/themes/default/bootstrap.css" rel="stylesheet"--%>
          <%--type="text/css">--%>
    <link href="/css/bootstrap.css" rel="stylesheet" type="text/css">

</head>
<body>
<div class="cover">
    <div class="navbar">
        <div class="container">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="#" style="color:white"><span>Tracy</span></a>
            </div>
            <div class="collapse navbar-collapse navbar-ex1-collapse">
                <ul class="nav navbar-nav navbar-right">
                    <li class="active">
                        <a href="#" style="color:white">Home</a>
                    </li>
                    <li>
                        <a href="#" style="color:white">Contacts</a>
                    </li>
                </ul>
            </div>
        </div>
    </div>
    <div class="cover-image"
         style="background-image : url('/pic/photo-bg.jpg')"></div>
    <div class="container">
        <div class="row">
            <div class="col-md-12 text-center">
                <h1 style="color:white">Bad Cloud</h1>
                <p style="color:white">Come to join us.</p>
                <br>
                <br>
                <a class="btn btn-info btn-lg" id="login-modal-button" data-toggle="modal" role="button">Join Us</a>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="login-modal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h2 class="modal-title text-center">Login</h2>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="login-form" role="form">
                    <div class="form-group">
                        <div class="col-sm-2">
                            <label for="phone-input" class="control-label">Phone</label>
                        </div>
                        <div class="col-sm-10">
                            <input type="number" class="form-control"  id="phone-input" name="userPhone"
                                   placeholder="Phone" min="11" max="11" autofocus="autofocus">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-2">
                            <label for="pwd-input" class="control-label">Password</label>
                        </div>
                        <div class="col-sm-10">
                            <input type="password" class="form-control" id="pwd-input" name="userPwd" placeholder="Password">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox">Remember me
                                </label>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <a class="btn btn-default" data-dismiss="modal">Close</a>
                <button id="login-button" class="btn btn-primary">Login</button>
            </div>
        </div>
    </div>
</div>
<footer class="section section-info">
    <div class="container">
        <div class="row">
            <div class="col-sm-6">
                <h1>About us</h1>
                <p>Lorem ipsum dolor sit amet, consectetur adipisici elit,
                    <br>sed eiusmod tempor incidunt ut labore et dolore magna aliqua.
                    <br>Ut enim ad minim veniam, quis nostrud</p>
            </div>
            <div class="col-sm-6">
                <p class="text-info text-right">
                    <br>
                    <br>
                </p>
                <div class="row">
                    <div class="col-md-12 hidden-lg hidden-md hidden-sm text-left">
                        <a href="#"><i class="fa fa-3x fa-fw fa-instagram text-inverse"></i></a>
                        <a href="#"><i class="fa fa-3x fa-fw fa-twitter text-inverse"></i></a>
                        <a href="#"><i class="fa fa-3x fa-fw fa-facebook text-inverse"></i></a>
                        <a href="#"><i class="fa fa-3x fa-fw fa-github text-inverse"></i></a>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12 hidden-xs text-right">
                        <a href="#"><i class="fa fa-3x fa-fw text-inverse fa-weibo"></i></a>
                        <a href="#"><i class="fa fa-3x fa-fw text-inverse fa-weixin"></i></a>
                        <a href="#"><i class="fa fa-3x fa-fw text-inverse fa-qq"></i></a>
                        <a href="#"><i class="fa fa-3x fa-fw fa-github text-inverse"></i></a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</footer>

</body>

</html>