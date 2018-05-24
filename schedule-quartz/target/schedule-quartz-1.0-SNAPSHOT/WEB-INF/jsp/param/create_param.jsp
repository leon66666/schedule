<%@page import="com.hoomsun.model.*" %>
<%@ page import="wangzhongqiu.model.TaskTimerParam" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<title>jQuery EasyUI</title>
<%@ include file="/commons/meta.jsp"%><html>
</head>
<body>
	<div class="pg-content">
		<div class="content-box">
			<s:form action="/TaskTimerParam/save.action" method="post">
			
				<table class="ui-table ui-table-min">
					<s:bean name="com.hoomsun.schedule.dao.TaskTimerDAO" var="cm"></s:bean>   	
			    	
					<caption>[<s:property value="#cm.getTaskNameById(#request.taskId)"/>]:<%=TaskTimerParam.TABLE_ALIAS%>新增</caption>
					<%@ include file="form_include_param.jsp" %>
					<tr>
						<td colspan="2" class="text-center">
							<input id="submitButton" class="ui-button mt24 mr36 mb40"  type="submit" value="提交" />
							<input type="button"  class="ui-button mt24 mr36 mb40"  value="返回列表" onclick="window.location='/TaskTimerParam/list.action?taskId=${taskId}'"/>
							<input type="button"  class="ui-button mt24 mr36"  value="后退" onclick="history.back();"/>
						</td>
					</tr>
				</table>
			</s:form>
		</div>
	</div>
	<script>
		
		new Validation(document.forms[0],{onSubmit:true,onFormValidate : function(result,form) {
			var finalResult = result;
			
			//在这里添加自定义验证
			
			return disableSubmit(finalResult,'submitButton');
		}});
	</script>
</body>
</html>
