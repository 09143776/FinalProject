<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 设置一个项目路径的变量  -->
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>学生选课系统后台</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/default.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/js/jquery-easyui-1.3.5/themes/gray/easyui.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/js/jquery-easyui-1.3.5/themes/icon.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/js/JQuery-zTree-v3.5.15/css/zTreeStyle/zTreeStyle.css"/>
<script type="text/javascript" src="${ctx}/js/jquery-easyui-1.3.5/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery-easyui-1.3.5/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/js/JQuery-zTree-v3.5.15/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="${ctx}/js/index.js"></script>
<script type="text/javascript" src="${ctx}/js/extends.js"></script>
<script type="text/javascript" src="${ctx}/js/common.js"></script>

</head>

<body class="easyui-layout">
<!-- 头部标题 -->
<div data-options="region:'north',border:false" style="height:60px; padding:5px; background:#F3F3F3"> 
	<a href="www.solooo.net"><span class="northTitle">学生选课系统后台</span></a>
    <span class="loginInfo">登录用户：${sessionScope.user.getSsn()} &nbsp;&nbsp;姓名：${sessionScope.user.getName()}&nbsp;&nbsp;角色：${sessionScope.user.getTitle()}</span>
</div>

<!-- 左侧导航 -->
<div data-options="region:'west',split:true,title:'导航菜单', fit:false" style="width:200px;"> 
  <ul id="menuTree" class="ztree">
  </ul>
</div>

<!-- 页脚信息 -->
<div data-options="region:'south',border:false" style="height:20px; background:#F3F3F3; padding:2px; vertical-align:middle;">
	<span id="sysVersion">系统版本：V1.0</span>
    <span id="nowTime"></span>
</div>

<!-- 内容tabs -->
<div id="center" data-options="region:'center'">
  <div id="tabs" class="easyui-tabs">
    
  </div>
</div>

<!-- 用于弹出框 -->
<div id="parent_win"></div>

</body>
</html>
