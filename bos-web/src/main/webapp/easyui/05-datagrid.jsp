<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>accordion</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>

</head>
<body >
<!-- 第一种创建datagrid方式  静态网页-->
<table class="easyui-datagrid">
	<thead>
		<tr>
			<th data-options="field:'id'">编号</th>
			<th data-options="field:'name'">姓名</th>
			<th data-options="field:'age'">年龄</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td>001</td>
			<td>小明</td>
			<td>25</td>
		</tr>
		<tr>
			<td>002</td>
			<td>小丽</td>
			<td>3</td>
		</tr>
	</tbody>
</table>

<!-- 方式二：发送ajax请求获取json数据创建datagrid -->
<table data-options="url:'${pageContext.request.contextPath }/json/datagrid_data.json'"
 class="easyui-datagrid">
	<thead>
		<tr>
			<th data-options="field:'id'">编号</th>
			<th data-options="field:'name'">姓名</th>
			<th data-options="field:'age'">年龄</th>
		</tr>
	</thead>
</table>
	<!-- 方式三：使用easyUI提供的API创建datagrid -->
	<script type="text/javascript">
	
		$(function() {
			//页面加载完成之后，创建数据表格datagrid
			$("#mytable").datagrid({
				columns:[[
				          {title:'编号',field:'id'},
				          {title:'姓名',field:'name'},
				          {title:'年龄',field:'age'},
				          {title:'地址',field:'address'}
				          ]],
				url:'${pageContext.request.contextPath }/json/datagrid_data.json',
				//定义工具栏
				toolbar:[
				         {text:"添加",iconCls:'icon-add',
				        	 handler:function() {
				     			alert("add");
				     		}
				         },
				         {text:"删除",iconCls:'icon-remove'},
				         {text:"修改",iconCls:'icon-edit'},
				         {text:"查询",iconCls:'icon-search'},
				         
				         ],
				//显示分页条 
				pagination:true
				
				
			});
		});
	</script>
	<table id="mytable">
	</table>	
</body>
</html>