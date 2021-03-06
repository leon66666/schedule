<%@page import="com.hoomsun.model.*" %>
<%@ page import="wangzhongqiu.model.TaskTimer" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ include file="/commons/meta.jsp"%>
<html>
<head>
	<title>jQuery EasyUI</title>
</head>
<body>
	<div class="pg-content">
		<div class="content-box">
			<s:form action="/TaskTimer/update.action" method="post">
			
			<table class="ui-table ui-table-min">
				<caption><%=TaskTimer.TABLE_ALIAS%>修改(运行状态类名和启用状态不能修改)</caption>
				<%@ include file="form_include.jsp" %>
				<tr>
					<td colspan="2" class="text-center">
						<input id="submitButton" class="ui-button mt24 mr36 mb40" name="submitButton" type="submit" value="提交" />
						<input type="button"  class="ui-button mt24 mr36 mb40"  value="返回列表" onclick="window.location='/TaskTimer/list.action'"/>
						<input type="button"  class="ui-button mt24 mb40"  value="后退" onclick="history.back();"/>
					</td>
				</tr>
			</table>
		</s:form>
		<script>
			
			new Validation(document.forms[0],{onSubmit:true,onFormValidate : function(result,form) {
				var finalResult = result;
				
				//在这里添加自定义验证
				
				return disableSubmit(finalResult,'submitButton');
			}});
		</script>
		</div>
			
		</div>
	</div>
</body>
</html>
