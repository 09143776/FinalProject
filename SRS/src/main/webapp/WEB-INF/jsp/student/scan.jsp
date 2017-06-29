<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 设置一个项目路径的变量  -->
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<c:set var="user" value="${sessionScope.user.getSsn()}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>学生选课系统</title>
<link href="${ctx}/css/bootstrap.min.css" rel="stylesheet" />
<link href="${ctx}/css/bootstrap.css" rel="stylesheet" />
<script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
<script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
</head>
<body class="row"  style="padding: 20px;" onload="initPage()">
	<section style="relative">
	<ol class="breadcrumb" style="margin-bottom: 0px;background-color: white;margin-top: -9px;">
  		<li><a href="#">>>课程设置</a></li>
  		<li class="active"><a href="#">查看/退选已选课程</a></li>
	</ol> 
	<input type="hidden" name="stuNo" id="stuNo" value="${user}"/>
	<table class="table table-striped table-bordered table-hover" id="data">
		<tr>
			<th>课程编号</th>
			<th>课程名</th>
			<th>课程学分</th>
			<th>任课老师</th>
			<th>上课时间</th>
			<th>上课地点</th>
			<th>操作</th>
		</tr>
		<tr>
			<td>GOVT101-1</td>
			<td>Beginning Computer Technology</td>
			<td>3.0</td>
			<td>Jacquie Barker</td>
			<td>8:10 - 10:00 PM</td>
			<td>M</td>
			<td><span style="color:blue;">退选</span></td>
		</tr>
	</table>
	</section>
<script type="text/javascript" src="${ctx}/js/jquery-2.2.3.js"></script>
<script type="text/javascript" src="${ctx}/js/bootstrap.min.js"></script>
<script type="text/javascript">
	function initPage(){
		$.post('stu/student_Course_getErolledSections', {'stuNo':$('#stuNo').val()}, function(data, statusText) {          
	        json=data;  
	        var jsonData = eval(json);
	        $("table#data tr:not(:eq(0))").remove();	       
	        $.each(jsonData,function(i,n){  
	        	$("#data").append("<tr><td>"+n.fullNo+"</td><td>"+n.courseName+"</td><td>"+n.crdits+"</td><td>"+n.professorName+"</td><td>"+n.timeOfDay+"</td><td>"+n.dayOfWeek+"</td><td><span style='color:blue;' onclick='unattend("+n.sectionNo+")'>退选</span></td></tr>");        
	           });
	  	  }); 
	}
	function unattend(sectionNo){
		alert("sbvb");
		$.post('stu/student_Course_drop', {'stuNo':$('#stuNo').val(), 'sectionNo': sectionNo}, function(data, statusText) {          
				initPage();
		       alert("退选成功！！！");
	  	}); 
	}
</script>
</body>
</html>