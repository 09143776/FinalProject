<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 设置一个项目路径的变量  -->
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>教师管理</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/default.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/js/jquery-easyui-1.3.5/themes/gray/easyui.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/js/jquery-easyui-1.3.5/themes/icon.css" />
<script type="text/javascript" src="${ctx}/js/jquery-easyui-1.3.5/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery-easyui-1.3.5/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/js/extends.js"></script>
<script type="text/javascript" src="${ctx}/js/common.js"></script>
<script>

$(function(){
	$("#tt").datagrid({
		height:$("#body").height()-$('#search_area').height()-5,
		width:$("#body").width(),
		url:'tea/teacher_Teacher_getAll', 
		nowrap:true,
		fitColumns:true,
		rownumbers:true,
		showPageList:false,
		singleSelect : true,
		toolbar : [
					{
						text : '新增',
						iconCls : 'icon-add',
						size : 'large',
						handler: function() {
							$('#dlg').dialog('open').dialog('setTitle', '新增');
							$('#fm').form('clear');
							$('#ssn').attr('disabled', false);
							$('#teaSsn').val('');
						}
					},{
						text : '修改',
						iconCls : 'icon-add',
						size : 'large',
						handler: function() {
							var row = $('#tt').datagrid('getSelected');
							if (row) {
										$('#dlg').dialog('open').dialog('setTitle', '修改');
										$('#fm').form('load',{
											ssn: row.ssn,
											title: row.title,
											dept: row.dept,
											name: row.dept
										});	
										$('#ssn').attr('disabled', true);	
										$('#teaSsn').val(row.ssn);							
							} else {
								$.messager.alert('提醒', '请先选择');
							}
						}
					}, {
						text : '删除',
						size : 'large',
						iconCls : 'icon-remove',
						handler : function() {
							var row = $('#tt').datagrid('getSelected');
							if (row) {
								$.messager.confirm('确认', '确认删除课程？', function(r) {
									if (r) {
										$.post("tea/teacher_Teacher_deleteTeacher", {
											'ssn' : row.ssn
										}, function() {
											$('#tt').datagrid('reload');
										});
									}
								});
							} else {
								$.messager.alert('提醒', '请先选择');
							}
						}
					} ],
		columns:[[{field:'ssn',title:'教师编号',width:60},
				{field:'name',title:'教师姓名',width:100},
				{field:'title',title:'职称',width:120},
				{field:'dept',title:'部门',width:100}]],
        pagination:true
	});
});

function searchTea(){
	$('#tt').datagrid('load', {
		name : $('#sName').val(),
		dept: $('#sDept').val()
	});
}

function saveTea(){
	$('#fm').form('submit', {
		url : 'tea/teacher_Teacher_saveTeacher',
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(data) {
			$.messager.alert('成功', '添加/修改成功！');
			$('#fm').form('clear');
			$('#dlg').dialog('close');
			$('#tt').datagrid('reload');
		}
	});
}

//监听窗口大小变化
window.onresize = function(){
	setTimeout(domresize,300);
};
//改变表格宽高
function domresize(){
	$('#tt').datagrid('resize',{  
		height:$("#body").height()-$('#search_area').height()-5,
		width:$("#body").width()
	});
}
</script>
</head>
<body class="easyui-layout" >
<div id="body" region="center" > 
  <!-- 查询条件区域 -->
  <div id="search_area" >
    <div id="conditon">
      <table border="0">
        <tr>
          <td>用户名：</td>
          <td ><input  name="sName" id="sName"   /></td>
          <td>&nbsp;部门：</td>
          <td><input  name="sDept" id="sDept"  /></td>
          <td>
              <a  href="javascript:void(0)" class="easyui-linkbutton my-search-button" iconCls="icon-search" plain="true" onclick="searchTea()">查询</a> 
              <a  href="javascript:void(0)" class="easyui-linkbutton my-search-button" iconCls="icon-reset" plain="true" >重置</a>
          </td>
        </tr>
      </table>
    </div>
    <span id="openOrClose">111</span> 
  </div>
  <!-- 数据表格区域 -->
  <table id="tt" style="table-layout:fixed;" ></table>
   
   <div id="dlg" class="easyui-dialog"
		style="width: 400px; height: 300px; padding: 20px" closed="true"
		buttons="#dlg-buttons">
		<form id="fm" method="post">
			<div class="fitem">
				<label>教师编号:</label> <input name="ssn" required="true"
					id="ssn" />
			</div>
			<br />
			<br />
			<div class="fitem">
				<label>教师姓名:</label> <input name="name"
					class="easyui-validatebox" required="true" id="name" />
			</div>
			<br />
			<br />
			<div class="fitem">
				<label>教师职称:</label> <input name="title" 
					required="true" id="title" />
			</div>
			<br />
			<br />
			<div class="fitem">
				<label>所在部门:</label> <input name="dept" id="dept" required="true"/>
			</div>
			<br />
			<br />
			<input type="hidden" id="teaSsn" name="teaSsn" />
		</form>
	</div>
	<div id="dlg-buttons">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok"
			onclick="saveTea()">保存</a> <a href="#" class="easyui-linkbutton"
			iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
	</div>
</div>
</body>
</html>
