<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 设置一个项目路径的变量  -->
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>课程安排</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/default.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/css/easyui.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/icon.css" />
<script type="text/javascript" src="${ctx}/js/jquery-2.2.3.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/js/extends.js"></script>
<script type="text/javascript" src="${ctx}/js/common.js"></script>
<script type="text/javascript">
	var url = "";
	function newCourse() {
		$('#dlg').dialog('open').dialog('setTitle', '添加新的课程');
		$('#fm').form('clear');
		$("#credits").val("");
		$("input[name='course.courseNo']").attr("disabled", false);
		$("#rowNo").val();
	}
	
	function saveCourse() {
		$('#fm').form('submit', {
			url : 'tea/teacher_Course_saveCourse',
			onSubmit : function() {
				return $(this).form('validate');
			},
			success : function(data) {
				$.messager.alert('成功', '添加/修改成功！');
				$('#fm').form('clear');
				$("#credits").val("");
				$('#dlg').dialog('close');
				$('#dg').datagrid('reload');
			}
		});
	}

	function saveSection() {
		$.post('tea/teacher_Course_saveSection?courseNo2='+$('#courseNo2').val(), $('#fm2').serialize(),function(){
			$.messager.alert('成功', '添加成功！');
			$('#fm2').form('clear');
			$('#dlg2').dialog('close');
			$('#dg2').datagrid('load', {
				courseNo : $('#courseNo2').val()
			});
		});
	}

	function saveSchedule(){
		var row = $('#dg2').datagrid('getSelected');
		if (row) {
			$.messager.confirm('确认', '确认将该课程添加到该学期计划课程中吗？', function(r) {
				if (r) {
					$.post('tea/teacher_Schedule_addToSchedule?sectionNo='+row.sectionNo, $('#fm3').serialize(), function(){
						$.messager.alert('成功', '添加成功！');
						$('#fm3').form('clear');
						$('#dlg3').dialog('close');
					});
				}
			});
		} else {
			$.messager.alert('提醒', '请先选择');
		}
	}

	function editCourse() {
		var row = $('#dg').datagrid('getSelected');
		if (row) {
			$("#dlg").dialog('open').dialog('setTitle', '查看课程');
			$("#credits").val(row.credits);
			$("#preCoursesName").val(row.preCourses);
			$("input[name='course.courseNo']").val(row.courseNo);
			$("input[name='course.courseNo']").attr("disabled", true);
			$("input[name='course.courseName']").val(row.courseName);
			$("#rowNo").val(row.courseNo);
		} else {
			$.messager.alert('提醒', '请先选择');
		}
	}

	function destroyCourse() {
		var row = $('#dg').datagrid('getSelected');
		if (row) {
			$.messager.confirm('确认', '确认删除' + row.courseName + '课程？', function(r) {
				if (r) {
					$.post("tea/teacher_Course_deleteCourse", {
						'courseNo' : row.courseNo
					}, function() {
						$('#dg').datagrid('reload');
					});
				}
			});
		} else {
			$.messager.alert('提醒', '请先选择');
		}
	}

	$(function() {
		$('#dg2').datagrid(
				{
					title : '课程开设',
					pageSize : 5,//默认选择的分页是每页5行数据  
					pageList : [ 5, 10, 15, 20 ],//可以选择的分页集合  
					nowrap : true,//设置为true，当数据长度超出列宽时将会自动截取  
					striped : true,//设置为true将交替显示行背景。  
					resizeHandle : "both",
					url : 'tea/teacher_Course_allSection',//url调用Action方法  
					loadMsg : '数据装载中......',
					singleSelect : true,//为true时只能选择单行  
					fitColumns : true,//允许表格自动缩放，以适应父容器  
					sortName : 'sectionNo',//当数据表格初始化时以哪一列来排序  
					sortOrder : 'asc',//定义排序顺序，可以是'asc'或者'desc'（正序或者倒序）。  
					remoteSort : false,
					pagination : true,//分页  
					rownumbers : true,//行数  
					border : true,
					toolbar : [
							{
								text : '开设新的课程',
								iconCls : 'icon-add',
								size : 'large',
								handler : function() {
									$('#dlg2').dialog('open').dialog('setTitle', '开设新课程');
									$('#fm2').form('clear');
								}
							},{
								text : '添加到学期计划',
								iconCls : 'icon-add',
								size : 'large',
								handler : function() {
									$('#dlg3').dialog('open').dialog('setTitle', '添加课程到学期计划');
									$('#fm3').form('clear');
								}
							}, {
								id : 'delete',
								text : '删除开设课程',
								size : 'large',
								iconCls : 'icon-remove',
								handler : function() {
									var row = $('#dg2').datagrid('getSelected');
									if (row) {
										$.messager.confirm('确认', '确认删除课程？', function(r) {
											if (r) {
												$.post("tea/teacher_Course_deleteSection", {
													'sectionNo' : row.sectionNo,
													'courseNo' : $('#courseNo2').val()
												}, function() {
													$('#dg').datagrid('reload');
												});
											}
										});
									} else {
										$.messager.alert('提醒', '请先选择');
									}
								}
							} ],
					columns : [ [ {
						field : 'sectionNo',
						title : "开设课程编号",
						width : 90,
						align : "center"
					}, {
						field : 'dayOfWeek',
						title : "上课时间",
						width : 90,
						align : "center"
					}, {
						field : 'timeOfDay',
						title : "上课时间段",
						width : 90,
						align : "center"
					}, {
						field : 'room',
						title : "上课教室",
						width : 90,
						align : "center"
					}, {
						field : 'seatingCapacity',
						title : "选课容量",
						width : 90,
						align : "center"
					}, {
						field : 'presentCapacity',
						title : "已选人数",
						width : 90,
						align : "center"
					}, {
						field : 'professorSsn',
						title : "教师编号",
						width : 90,
						align : "center"
					}, {
						field : 'professorName',
						title : "教师姓名",
						width : 90,
						align : "center"
					} ] ]
				}).datagrid("getPager").pagination({
			beforePageText : '第', //页数文本框前显示的汉字    
			afterPageText : '页/{pages}页',
			displayMsg : '共{total}条记录'
		});
	});

	$(function() {
		$('#dg').datagrid({
			title : '全部课程',
			pageSize : 5,//默认选择的分页是每页5行数据  
			pageList : [ 5, 10, 15, 20 ],//可以选择的分页集合  
			nowrap : true,//设置为true，当数据长度超出列宽时将会自动截取  
			striped : true,//设置为true将交替显示行背景。  
			toolbar : "#toolbar",//在添加 增添、删除、修改操作的按钮要用到这个  
			url : 'tea/teacher_Course_allCourses',//url调用Action方法  
			loadMsg : '数据装载中......',
			singleSelect : true,//为true时只能选择单行  
			fitColumns : true,//允许表格自动缩放，以适应父容器  
			sortName : 'courseNo',//当数据表格初始化时以哪一列来排序  
			sortOrder : 'asc',//定义排序顺序，可以是'asc'或者'desc'（正序或者倒序）。  
			remoteSort : false,
			pagination : true,//分页  
			rownumbers : true,//行数  
			border : true,
			columns : [ [ {
				field : 'courseNo',
				title : "课程编号",
				width : 90,
				align : "center"
			}, {
				field : 'courseName',
				title : "课程名称",
				width : 90,
				align : "center"
			}, {
				field : 'credits',
				title : "学分",
				width : 90,
				align : "center"
			}, {
				field : 'preCourses',
				title : "选修课程名称",
				width : 90,
				align : "center"
			} ] ]
		}).datagrid("getPager").pagination({
			beforePageText : '第', //页数文本框前显示的汉字    
			afterPageText : '页/{pages}页',
			displayMsg : '共{total}条记录',
			onBeforeRefresh : function() {
				return true;
			}
		});
	});
   
	$(function() {
		$('#dg').datagrid({
			onSelect : function(index, row) {
				$('#courseNo2').val(row.courseNo);
				$('#dg2').datagrid('load', {
					courseNo : row.courseNo
				});
			},
			onLoadSuccess : function() {
				var rows = $(this).datagrid('getRows');
				if (rows.length) {
					$(this).datagrid('selectRow', 0);
				}
			}
		});
	});

	function searchCourse(){
		$('#dg').datagrid('load', {
			courseNo : $('#courseNo').val(),
			courseName: $('#courseName').val()
		});
	}
</script>
</head>
<body class="easyui-layout">
	<div class="easyui-layout" fit="true">
		<div id="search_area">
			<form id="conditon">
				<table border="0">
					<tr>
						<td>课程编号：</td>
						<td><input name="courseNo" id="courseNo" /></td>
						<td>&nbsp;课程名称：</td>
						<td><input name="courseName" id="courseName" /></td>
						<td><a href="javascript:void(0)"
							class="easyui-linkbutton my-search-button" iconCls="icon-search" onclick="searchCourse()"
							plain="true">查询</a> <a href="javascript:void(0)"
							class="easyui-linkbutton my-search-button" iconCls="icon-reset"
							plain="true" >重置</a></td>
					</tr>
				</table>
			</form>
			<span id="openOrClose">111</span>
		</div>


			<div>
				<table id="dg"></table>
				<table id="dg2"></table>
				<div id="toolbar">
					<a href="#" class="easyui-linkbutton" iconCls="icon-add"
						plain="true" onclick="newCourse()" width="45px">添加</a> <a href="#"
						class="easyui-linkbutton" iconCls="icon-edit" plain="true"
						onclick="editCourse()" width="45px">编辑</a> <a href="#"
						class="easyui-linkbutton" iconCls="icon-remove" plain="true"
						onclick="destroyCourse()" width="45px">删除</a>
				</div>
				<div region="center" border="true" style="overflow: hidden"></div>
			</div>
			
	<div id="dlg" class="easyui-dialog"
		style="width: 400px; height: 300px; padding: 20px" closed="true"
		buttons="#dlg-buttons">
		<form id="fm" method="post">
			<div class="fitem">
				<label>课程编号:</label> <input name="course.courseNo" required="true"
					id="courseNo" />
			</div>
			<br />
			<br />
			<div class="fitem">
				<label>课程名称:</label> <input name="course.courseName"
					class="easyui-validatebox" required="true" id="courseName" />
			</div>
			<br />
			<br />
			<div class="fitem">
				<label>课程学分:</label> <input name="course.credits" type="number"
					required="true" id="credits" />
			</div>
			<br />
			<br />
			<div class="fitem">
				<label>选修课程:</label> <input name="course.preCoursesNo"
					placeHolder="多个选修课程编号请用逗号隔开" id="preCoursesName" />
			</div>
			<br />
			<br /> <input type="hidden" id="rowNo" name="rowNo" />
		</form>
	</div>
	<div id="dlg-buttons">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok"
			onclick="saveCourse()">保存</a> <a href="#" class="easyui-linkbutton"
			iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
	</div>
	
	<input type="hidden" id="courseNo2" name="courseNo2" />
	<div id="dlg2" class="easyui-dialog"
		style="width: 400px; height: 450px; padding: 20px" closed="true"
		buttons="#dlg-buttons2">
		<form id="fm2" method="post">
			<div class="fitem">
				<label>上课地点 :</label> <input name="room"  id="room" require="true"/>
			</div>
			<br/><br/>
			<div class="fitem">
				<label>课程容量:</label> <input name="seatingCapacity"  id="seatingCapacity" class="easyui-numberbox" require="true" data-options="min:0,precision:0" require="true"/>
			</div>
			<br/><br/>
			<div class="fitem">
				<label>课程教师编号:</label>
				<select id="professorSsn" class="easyui-combogrid" name="professorSsn" style="width:200px;" 
					data-options="
					panelWidth:450,
					editable : true,
        			multiple : true,
					idField:'ssn',
					required : true,
					textField:'name',
					url:'tea/teacher_Teacher_getAll',
					columns:[[{field:'ssn',title:'教师编号',width:60},
							{field:'name',title:'教师姓名',width:100},
							{field:'title',title:'职称',width:120},
							{field:'dept',title:'部门',width:100}]]"></select>
			</div>
			<br/><br/>
			<div class="fitem">	
				<label>课程上课时间1:</label>
				<select id="dayOfWeek" class="easyui-combobox" name="dayOfWeek" style="width:200px;" data-options="multiple:true" require="true"> 
					<option>星期一</option>
					<option>星期二</option>
					<option>星期三</option>
					<option>星期四</option>
					<option>星期五</option>
					<option>星期六</option>
					<option>星期日</option>
				</select>
			</div>
			<br/><br/>
			<div class="fitem">
				<label>课程上课时间2:</label>
				<select id="timeOfDay" class="easyui-combobox" name="timeOfDay" style="width:200px;" data-options="multiple:true" require="true"> 
					<option>一二节（8:00 - 9:45 AM）</option>
					<option>三四节（10:15 - 12:00 AM）</option>
					<option>五六节（14:00 - 15:45 PM）</option>
					<option>七八节（16:15 - 18:00 PM）</option>
					<option>九十节（19:00 - 21:45 PM）</option>
				</select>
			</div>
			<br/><br/>
			
		</form>
	</div>
	<div id="dlg-buttons2">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveSection()">保存</a> 
		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg2').dialog('close')">取消</a>
	</div>
	</div>
	
	<div id="dlg3" class="easyui-dialog"
		style="width: 400px; height: 300px; padding: 20px" closed="true"
		buttons="#dlg-buttons3">
		<form id="fm3" method="post">
			<div class="fitem">
				<label>添加到的学期:</label>
				<select id="semester" class="easyui-combobox" name="semester" style="width:200px;" require="true"> 
					<option>大一上学期</option>
					<option>大一下学期</option>
					<option>大二上学期</option>
					<option>大二下学期</option>
					<option>大三上学期</option>
					<option>大三下学期</option>
				</select>
			</div>
			<br/><br/>
		</form>
	</div>
	<div id="dlg-buttons3">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveSchedule()">保存</a> 
		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg3').dialog('close')">取消</a>
	</div>
</body>
</html>