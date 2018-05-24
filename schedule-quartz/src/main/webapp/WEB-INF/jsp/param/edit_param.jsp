<%@page import="com.hoomsun.model.*" %>
<%@ page import="wangzhongqiu.model.TaskTimerParam" %>
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
			<s:form action="/TaskTimerParam/update.action" method="post">
				<table class="ui-table ui-table-min">
					<s:bean name="com.hoomsun.schedule.dao.TaskTimerDAO" var="cm"></s:bean>   	
					<caption>[<s:property value="#cm.getTaskNameById(#request.taskId)"/>]:<%=TaskTimerParam.TABLE_ALIAS%>修改</caption>
					
					<input type="hidden" name="taskId" value="${taskId}">
					<input type="hidden" name="taskTimerParam.id" value="${taskTimerParam.id }">
					<tr>
					    <td class="tdLabel"><%=TaskTimerParam.ALIAS_PARAM_KEY %></td>	    
					    <td>
					    	${taskTimerParam.paramKey }
							<input type="hidden" name="taskTimerParam.paramKey" class="ui-input mt20 max-length-50" value="${taskTimerParam.paramKey }">	    	
					    </td>
					</tr>
					<tr>
					    <td class="tdLabel"><%=TaskTimerParam.ALIAS_PARAM_VALUE %></td>	    
					    <td>
							<input class="ui-input" type="text" name="taskTimerParam.paramValue" value="${taskTimerParam.paramValue}">	    	
					    </td>
					</tr>
					<tr>
					    <td class="tdLabel"><%=TaskTimerParam.ALIAS_DISPLAY_NAME %></td>	    
					    <td>
							<input class="ui-input" type="text" name="taskTimerParam.displayName" value="${taskTimerParam.displayName}">	    	
					    </td>
					</tr>
					<tr>
					    <td class="tdLabel"><%=TaskTimerParam.ALIAS_PARAM_DESC %></td>	    
					    <td>
							<textarea class="ui-textarea" name="taskTimerParam.taskParamDesc" rows="5" cols="19">${taskTimerParam.taskParamDesc}</textarea>
					    </td>
					</tr>
					<tr>
					    <td class="tdLabel"><%=TaskTimerParam.ALIAS_TASK_ID %></td>	    
					    <td>
					    	<s:property value="#cm.getTaskNameById(#request.taskId)"/>
					    	
					    </td>
					</tr>
					<tr>
						<td colspan="2" class="text-center">
							<input id="submitButton" class="ui-button mt24 mr36 mb40" type="submit" value="提交" />
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
	</div>
</body>
</html>
