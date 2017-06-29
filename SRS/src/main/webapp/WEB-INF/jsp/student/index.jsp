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
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>" /> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>学生选课系统</title>
<link href="${ctx}/css/bootstrap.min.css" rel="stylesheet" />
<link href="${ctx}/css/bootstrap.css" rel="stylesheet" />
<style type="text/css">
a:link,a:active,a:visited,a:hover {
    font-size: 12px; 
    color: black; 
    font-family: 宋体; 
    text-decoration: none;
}
a:hover{
   	color: #B12C2C;
}
</style>
<!-- 以下两个插件用于在IE8以及以下版本浏览器支持HTML5元素和媒体查询，如果不需要用可以移除 -->
<script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
<script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
</head>
<body class="row">
	 <div class="col-lg-2 col-md-1 col-xs-0"></div>

  	 <div class="col-lg-8 col-md-10 col-xs-12" >
	  <header style="vertical-align: bottom;position: relative;background-color: gray;">
		<div style="display: inline-block;">
				<img id="logo" src="${ctx}/images/title1.png" alt="学生选课系统" />
		</div>
		<div style="display: inline-block;position: absolute;right: 13px;bottom: 0px;">
			<a href="javascript:void(0)">联系我们</a>
		</div>		
	  </header>

	<div style="clear:both;height:5px;"></div>
	<nav id="nav">
			<div class="navbar navbar-default" role="navigation" style="margin-bottom: 0px;">
			<ul class="nav navbar-nav">
        		<li class="dropdown">
         		 <a href="##" data-toggle="dropdown" class="dropdown-toggle">课程设置<span class="caret"></span></a>
         		 <ul class="dropdown-menu">
        			<li id="attend"><a href="javascript:void(0)">选择课程</a></li>
        			<li id="scan"><a href="javascript:void(0)">查看已选课程</a></li>
          		 </ul>
       			</li>
       			<li id="grades"><a href="javascript:void(0)">成绩查询</a></li>
			</ul>
			</div>
		</nav>
		
		<section  style="border: 1px dashed gray;box-shadow: 2px 2px 2px black;">
		<iframe id="mainFrame" name="mainFrame" scrolling="no" src="stu/attend"
            frameborder="0" style="padding: 0px; width: 100%; height: 880px;"></iframe>
	
	    <div style="clear:both;"></div>
	    </section>

	   <footer style="background-color: gray;padding: 10px;">
		<section id="f_section1">
			<p id="right">版权所有：中国矿业大学电子商务专业</p>
			<p id="address"><address>地址：江苏省徐州市大学路1号中国矿业大学南湖校区</address></p>
			<p id="right">邮编：221116</p>
		</section>
		<section id="f_section2">
			<div id="connect">联系我们：</div>			
		</section>
	    </footer>
  	 </div>

  	 <div class="col-lg-2 col-md-1 col-xs-0"></div>
  	 </body>
<script type="text/javascript" src="${ctx}/js/jquery-2.2.3.js"></script>
<script type="text/javascript" src="${ctx}/js/bootstrap.min.js"></script>
<script type="text/javascript">
$(function(){
	$(".dropdown ul li").each(function(index){
		$(this).click(function(){
			var ifr = window.frames[0];
			var src = "stu/" + $(this).attr('id');
			window.frames[0].document.location=src;
			$("ul li").removeClass('active');
			$(this).toggleClass('active');
		})
	})
	$("#grades").click(function(){
		var ifr = window.frames[0];
		var src = "stu/" +$(this).attr('id');
		window.frames[0].document.location=src;
		$("ul li").removeClass('active');
		$(this).toggleClass('active');
	})
});
</script>
<script type="text/javascript">
	startInit('mainFrame', 730);
	var browserVersion = window.navigator.userAgent.toUpperCase();
	var isOpera = browserVersion.indexOf("OPERA") > -1 ? true : false;
	var isFireFox = browserVersion.indexOf("FIREFOX") > -1 ? true : false;
	var isChrome = browserVersion.indexOf("CHROME") > -1 ? true : false;
	var isSafari = browserVersion.indexOf("SAFARI") > -1 ? true : false;
	var isIE = (!!window.ActiveXObject || "ActiveXObject" in window);
	var isIE9More = (! -[1, ] == false);
	function reinitIframe(iframeId, minHeight) {
    try {
        var iframe = document.getElementById(iframeId);
        var bHeight = 0;
        if (isChrome == false && isSafari == false)
            bHeight = iframe.contentWindow.document.body.scrollHeight;

        var dHeight = 0;
        if (isFireFox == true)
            dHeight = iframe.contentWindow.document.documentElement.offsetHeight + 2;
        else if (isIE == false && isOpera == false)
            dHeight = iframe.contentWindow.document.documentElement.scrollHeight;
        else if (isIE == true && isIE9More) {//ie9+
            var heightDeviation = bHeight - eval("window.IE9MoreRealHeight" + iframeId);
            if (heightDeviation == 0) {
                bHeight += 3;
            } else if (heightDeviation != 3) {
                eval("window.IE9MoreRealHeight" + iframeId + "=" + bHeight);
                bHeight += 3;
            }
        }
        else//ie[6-8]、OPERA
            bHeight += 3;

        var height = Math.max(bHeight, dHeight);
        if (height < minHeight) height = minHeight;
        iframe.style.height = height + "px";
    } catch (ex) { }
}
function startInit(iframeId, minHeight) {
    eval("window.IE9MoreRealHeight" + iframeId + "=0");
    window.setInterval("reinitIframe('" + iframeId + "'," + minHeight + ")", 100);
} 
	</script>
</html>