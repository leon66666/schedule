<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ include file="/commons/top_page.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-store"/>
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<link href="<%=basePath %>css/default.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="<%=basePath %>js/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath %>js/themes/icon.css" />
<script type="text/javascript" src="<%=basePath %>js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=basePath %>js/jquery.easyui.pack.js"></script>
    <title>上传部署</title>
<script type="text/javascript">
	$(function(){
		$("#fileButton").bind("click",function(){
			if($("#file").val()==''){
				 $.messager.alert("操作提示", "请选择文件！");
			}else{
				$("#fileForm").submit();
			}
		});
	});
</script>
</head>
<body class="easyui-layout" style="overflow-y: hidden"  scroll="no">
    <div id="upload" region="center" style="background: #eee; overflow-y:hidden">
			<s:if test="#request.uploadFlag==null">
			<s:form action="/Upload.action" id="fileForm" theme="simple" enctype="multipart/form-data">
			<table>
				<tr>
					<td>
					
						<s:file id="file" name="file" label="请选择上传文件" />
						<input type="button" id="fileButton" value="上传"/>
					</td>
				</tr>
			</table>
			</s:form>
			</s:if>
			<h1 style="color: #ff0000">
			${uploadFlag}</h1>
			<%=path %>
			<s:property value="#request.contextPath"/>
    </div>
</body>

</html>