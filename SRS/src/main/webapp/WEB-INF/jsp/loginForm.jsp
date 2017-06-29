<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%  
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
%> 
<!-- 设置一个项目路径的变量  -->
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
	<title>登录</title>
	<link rel="stylesheet" href="${ctx}/css/reset.css" />
	<link rel="stylesheet" href="${ctx}/css/login.css" />
        <script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
        <script type="text/javascript" src="${ctx}/js/login.js"></script>
</head>
<body>
<div class="page">
	<div class="loginwarrp">
        <div class="login_form">
        	
			<form id="Login" name="Login">
				<span id="error" style="color:red;"></span>
				<ul>
				<li class="login-item">
					<span>用户名：</span>
					<input type="text" id="username" name="userName" class="login_input" />
                                        <span id="count-msg" class="error"></span>
				</li>
				<li class="login-item">
					<span>密　码：</span>
					<input type="password" id="password" name="password" class="login_input" />
                                        <span id="password-msg" class="error"></span>
				</li>
				<li style="padding:5px;text-align:right;font-size:17px;">
					<label><input type="radio" value="student" name="identity"/>学生</label>&nbsp;&nbsp;
					<label><input type="radio" value="teacher" name="identity"/>教师</label>
				</li>
				<li class="login-sub">
					<input type="button" name="submit" value="登录" id="submit"/>
                    <input type="reset" name="Reset" value="重置" />
				</li> 
				</ul>                     
           </form>
		</div>
	</div>
</div>
<script type="text/javascript">
		window.onload = function() {
			var config = {
				vx : 4,
				vy : 4,
				height : 2,
				width : 2,
				count : 100,
				color : "121, 162, 185",
				stroke : "100, 200, 180",
				dist : 6000,
				e_dist : 20000,
				max_conn : 10
			}
			CanvasParticle(config);
		}

		$("#submit").click(function(event){
			event.preventDefault();
			alert($("#Login").serializeArray());
			$.post("loginForm", $("#Login").serializeArray(), function(data, statusText) {
					var result = data;
					if(result == "fail1"){
						$("#error").html("用户名、密码错误！");
					}else if(result == "fail2"){
						$("#error").html("请选择登录的角色！");
					}else if(result == "teacher"){
						window.location.href="http://localhost:8090/SRS/" + "tea/index";
					}else{
						window.location.href="http://localhost:8090/SRS/" + "stu/index";
					}
			 });
			})
	</script>
	<script type="text/javascript" src="${ctx}/js/canvas-particle.js"></script>
</body>
</html>