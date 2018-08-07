<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: TracyM
  Date: 2017/3/23
  Time: 11:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"  pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>主页</title>

    <meta name="description" content="Source code generated using layoutit.com">
    <meta name="author" content="LayoutIt!">

    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <link href="/css/list.css" rel="stylesheet">
    <link href="/css/bootstrap-editable.css" rel="stylesheet">



</head>
<body>

<div class="container-fluid">
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-default">
                <div class="panel-heading col-md-12">
                    <div class="col-md-8">File List</div>
                    <div class="btn-group col-md-4">
                        <button id="toggle-editable" class="btn btn-primary btn-sm" >Toggle</button>
                        <button type="button" class="btn btn-primary dropdown-toggle btn-sm" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <span class="caret"></span>
                            <span class="sr-only">Toggle Dropdown</span>
                        </button>
                        <ul class="dropdown-menu">
                            <li ><a href="#">Rename</a></li>
                            <li><a href="#">Delete</a></li>
                            <li role="separator" class="divider"></li>
                            <li><a href="#">Share</a></li>
                        </ul>
                    </div>
                </div>

                <table class="table">
                    <thead>
                        <tr>
                            <th><input id="checkAll" type="checkbox" /></th>
                            <th>#</th>
                            <th>File</th>
                            <th>Time</th>
                            <th>Status</th>
                            <th>Opr</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="rcd" items="${list}" varStatus="status">
                            <tr>
                                <td>
                                    <input name="checkItem" type="checkbox" />
                                    <%--<input id="${rcd.recordId}-recordId" type="hidden" value="${rcd.recordId}">--%>
                                </td>
                                <td>
                                    <c:out value="${status.count}"/>
                                </td>
                                <td>
                                    <a class="a-editable" href="#">
                                        <c:out value="${rcd.customName}"></c:out>
                                    </a>
                                </td>

                                <td>
                                    <fmt:formatDate value="${rcd.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                </td>

                                <td>
                                    <c:out value="${rcd.share}"></c:out>
                                </td>
                                <td>
                                    <div class="btn-group">
                                        <a id="${rcd.recordId}-download" class="btn btn-primary btn-sm" recordId="${rcd.recordId}" fileId="${rcd.fileId}" customName="${rcd.customName}">Download</a>
                                        <button type="button" class="btn btn-primary dropdown-toggle btn-sm" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                            <span class="caret"></span>
                                            <span class="sr-only">Toggle Dropdown</span>
                                        </button>
                                        <ul class="dropdown-menu">
                                            <li><a id="${rcd.recordId}-recordId" customName="${rcd.customName}" recordId="${rcd.recordId}" share="${rcd.share}">Rename</a></li>
                                            <li><a id="${rcd.recordId}-delete" href="#" recordId="${rcd.recordId}">Delete</a></li>
                                            <li role="separator" class="divider"></li>
                                            <li><a href="#" id="${rcd.recordId}-share" recordId="${rcd.recordId}">Share</a></li>
                                        </ul>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>

            <%--首部导航条--%>
            <nav class="navbar navbar-default navbar-fixed-top" role="navigation">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse"
                            data-target="#bs-example-navbar-collapse-1">
                        <span class="sr-only">Toggle navigation</span><span class="icon-bar"></span><span
                            class="icon-bar"></span><span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="#">Madcatz</a>
                </div>

                <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                    <ul class="nav navbar-nav">
                        <li class="active">
                            <a href="#">文件</a>
                        </li>
                        <li>
                            <a href="#">Link</a>
                        </li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">Dropdown<strong
                                    class="caret"></strong></a>
                            <ul class="dropdown-menu">
                                <li>
                                    <a href="#">Action</a>
                                </li>
                                <li>
                                    <a href="#">Another action</a>
                                </li>
                                <li>
                                    <a href="#">Something else here</a>
                                </li>
                                <li class="divider">
                                </li>
                                <li>
                                    <a href="#">Separated link</a>
                                </li>
                                <li class="divider">
                                </li>
                                <li>
                                    <a href="#">One more separated link</a>
                                </li>
                            </ul>
                        </li>
                        <form class="navbar-form navbar-right" role="search">
                            <div class="form-group">
                                <input type="text" class="form-control">
                            </div>
                            <button type="submit" class="btn btn-default">
                                搜索
                            </button>
                        </form>
                    </ul>

                    <ul class="nav navbar-nav navbar-right" style="align-items:center;">
                        <li>
                            <a href="#" id="session-tag">${sessionScope.User.userPhone}</a>
                        </li>
                        <%--上传按钮--%>
                        <div class="col-md-2">
                            <a class="btn btn-primary" id="update-new-file-button" style="">Upload New File</a>
                        </div>
                        <div class="col-md-1">

                        </div>
                    </ul>
                </div>

            </nav>

            <div class="modal fade" id="upload-modal">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">Close</button>
                            <h4 class="modal-title">Upload</h4>
                        </div>
                        <div class="modal-body">
                            <form action="/file/upload" method="post" enctype="multipart/form-data" id="upload-form">
                                <div class="form-group">
                                    <label for="cuntom-name-input">
                                        Cumtom Name:
                                    </label>
                                    <input id="cuntom-name-input" type="text" class="form-control" name="customName">
                                </div>
                                <div class="form-group">
                                    <label for="file">Select File:</label>
                                    <input id="file" type="file" name="file">
                                    <p class="help-block">
                                        //TODO
                                    </p>
                                </div>
                                <div class="checkbox">
                                    <label>
                                        <!--不选中为null-->
                                        <input type="checkbox" id="share-input" value="1"> Share?
                                    </label>
                                </div>
                                <input type="hidden" id="md5-input" name="fileMd5">
                                <input type="hidden" id="create-time-input" name="createTime">
                                <input type="hidden" id="share-input-hidden" name="share">
                            </form>
                        </div>
                        <div id="log"></div>
                        <div class="modal-footer">
                            <a class="btn btn-default" data-dismiss="modal">Close</a>
                            <button class="btn btn-primary" id="check-button" value="check-button">Check</button>
                            <button class="btn btn-primary" type="submit" id="upload-button" value="upload-button">
                                Upload
                            </button>
                        </div>
                    </div>
                </div>
            </div>

            <div class="modal fade" id="rename-modal">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">Close</button>
                            <h4 class="modal-title">Rename</h4>
                        </div>
                        <div class="modal-body">
                            <form  method="post" id="rename-form">
                                <div class="form-group">
                                    <label for="cuntom-name-input">Rename:</label>
                                    <input id="rename-input" type="text" class="form-control" name="customName">
                                    <div class="checkbox">
                                        <label>
                                            <input type="checkbox" id="share-checkbox">是否分享
                                            <input type="hidden" id="share-checkbox-hidden" name="share" value="0">
                                        </label>
                                    </div>
                                </div>
                                <%--<input type="hidden" id="md5-input" name="fileMd5">--%>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <a class="btn btn-default" data-dismiss="modal">Close</a>
                            <button class="btn btn-primary" type="submit" id="rename-button" value="rename-button">Confirm</button>
                        </div>
                    </div>
                </div>
            </div>


        </div>

    </div>
</div>
</div>

<script src="/js/jquery.min.js" type="text/javascript"></script>
<script src="/js/bootstrap.min.js" type="text/javascript"></script>
<script type="text/javascript " src="/js/spark-md5.min.js"></script>
<script type="text/javascript" src="/js/bootstrap-editable.min.js"></script>
<script type="text/javascript " src="/js/list.js"></script>

</body>
</html>
