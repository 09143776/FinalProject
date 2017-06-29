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
<body class="row" style="padding: 20px;">
	<section style="relative">
	<ol class="breadcrumb" style="margin-bottom: 0px;background-color: white;margin-top: -9px;">
  		<li><a href="#">>>课程设置</a></li>
  		<li class="active"><a href="#">选择课程</a></li>
	</ol> 
	<div class="panel panel-warning">
		<div class="panel-heading">
			学号： ${user} &nbsp;&nbsp;&nbsp; 姓名：${sessionScope.user.getName()}  
		</div>
		<div class="panel-body">
		<label>请先选择你所在学期：</label>
		<select name="semester" id="semester">
			<option>大一上学期</option>
			<option>大一下学期</option>
			<option>大二上学期</option>
			<option>大二下学期</option>
			<option>大三上学期</option>
			<option>大三下学期</option>
		</select>
		
		<form name="attendSection" id="attendSection">
		<input type="hidden" name="stuNo" value="${user}"/>
		<table class="table table-striped table-bordered table-hover" id="data" style="margin-top: 13px;">
		<tr>
			<th>选择</th>
			<th>课程编号</th>
			<th>课程名</th>
			<th>课程学分</th>
			<th>任课老师</th>
			<th>上课时间</th>
			<th>上课地点</th>
			<th>可选人数</th>
			<th>已选人数</th>
		</tr>
		</table>
		</form>
		</div>
	</div>
	</section>
	<footer style="position: absolute;right: 0px;">
		<button class="btn btn-default" onclick="sure()">选定</button>
		<button class="btn btn-default" onclick="cancel()">取消选定</button>
	</footer>
<script type="text/javascript" src="${ctx}/js/jquery-2.2.3.js"></script>
<script type="text/javascript" src="${ctx}/js/bootstrap.min.js"></script>
<script type="text/javascript">
		function sure(){
			$.post("stu/student_Course_attend", $('#attendSection').serialize(), function(data, statusText) {
				alert(data);
 			});
		}
		function cancel(){
			$("input[name='fullNo']").each(function(){
                this.checked= false;
            });
		}
		$("#semester").change(function(){
			$.post('stu/student_Schedule_getSectionsBySemester', {'semester': $(this).val()},  function(data, statusText) {          
		        json=data;  
		        var jsonData = eval(json);
		        $("table#data tr:not(:eq(0))").remove();	       
		        $.each(jsonData,function(i,n){  
		        	$("#data").append("<tr><td><input type='checkbox' name='fullNo' value='"+n.fullNo+"'/></td><td>"+n.fullNo+"</td><td>"+n.courseName+"</td><td>"+n.crdits+"</td><td>"+n.professorName+"</td><td>"+n.timeOfDay+"</td><td>"+n.dayOfWeek+"</td><td>"+n.seatingCapacity+"</td><td>"+n.presentCapacity+"</td></tr>");        
		           });
		  	  }); 
		});
</script>
</body>
</html>