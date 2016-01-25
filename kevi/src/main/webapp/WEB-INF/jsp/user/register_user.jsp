<%--
 * 
 * @author Shengzhao Li
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>注册用户</title>
    <script src="${contextPath}/resources/angular.min.js"></script>

</head>
<body>
<a href="./">Home</a>

<h2>注册用户</h2>

<div ng-app>
<p class="help-block">
    提供简单的<code>用户注册</code>功能
</p>

<div ng-controller="RegisterClientCtrl">
        <form:form commandName="formDto"  cssClass="form-horizontal">
            <div class="form-group">
                <label for="username" class="col-sm-2 control-label">Username<em class="text-danger">*</em></label>

                <div class="col-sm-10">
                    <form:input path="username" cssClass="form-control" id="username" placeholder="username"
                                required="required"/>

                    <p class="help-block"><code><em>Username</em></code>必须输入,且必须唯一,建议长度至少5位.</p>
                </div>
            </div>
            <div class="form-group">
                <label for="password" class="col-sm-2 control-label">Password<em
                        class="text-danger">*</em></label>

                <div class="col-sm-10">
                    <form:password path="password" cssClass="form-control" id="password"
                                placeholder="password" required="required"/>

                    <p class="help-block"><code><em>Password</em></code>必须输入,建议长度至少8位.</p>
                </div>
            </div>
			<!-- <div class="form-group">
                <label for="password" class="col-sm-2 control-label">Confirm password<em
                        class="text-danger">*</em></label>

                <div class="col-sm-10">
                    <form:password path="password" cssClass="form-control" id="password"
                                placeholder="password" required="required"/>

                    <p class="help-block">确认密码.</p>
                </div>
            </div>
             -->
            <div class="form-group">
                <label for="email" class="col-sm-2 control-label">Email</label>

                <div class="col-sm-10">
                    <form:input path="email" id="email" placeholder="Email"
                                cssClass="form-control"/>

                    <p class="help-block">请输入你的<code><em>email</em></code>地址</p>
                </div>
            </div>

            <div class="form-group">
                <label for="phone" class="col-sm-2 control-label">Phone</label>

                <div class="col-sm-10">
                    <form:input path="phone" id="phone" placeholder="Phone"
                                cssClass="form-control"/>

                    <p class="help-block">请输入<code>手机</code>或者<code>座机</code>号码.</p>
                </div>
            </div>

            <div class="form-group">
                <div class="col-sm-2"></div>
                <div class="col-sm-10">
                    <form:errors path="*" cssClass="text-danger"/>
                </div>
            </div>


            <div class="form-group">
                <div class="col-sm-2"></div>
                <div class="col-sm-10">
                    <button type="submit" class="btn btn-success">注册</button>
                    <a href="client_details">取消</a>
                </div>
            </div>
        </form:form>
    </div>
</div>
</body>
</html>