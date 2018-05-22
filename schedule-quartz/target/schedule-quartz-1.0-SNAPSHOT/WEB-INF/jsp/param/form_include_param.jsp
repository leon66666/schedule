<%@page import="com.hoomsun.model.TaskTimerParam"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
	<input type="hidden" name="taskId" value="${taskId }">
	<input type="hidden" name="taskTimerParam.id" value="${taskTimerParam.id }">
	
	<s:if test="#request.task.isTiming==1">
	<td class="tdLabel">
		参数键(单位：h)
	</td>
	<td>
		timingDate
		<s:hidden name="taskTimerParam.paramKey" value="timingDate"/>
	</td>
	</s:if>
	<s:else>
		<s:select list="#{'delayDate':'延迟执行时间','intervalDate':'任务执行间隔时间'}" id="paramKey" key="taskTimerParam.paramKey" label="%{@com.hoomsun.model.TaskTimerParam@ALIAS_PARAM_KEY}" required="true"></s:select>
	</s:else>
	<s:textfield label="%{@com.hoomsun.model.TaskTimerParam@ALIAS_PARAM_VALUE}" id="paramValue"   key="taskTimerParam.paramValue" value="%{taskTimerParam.paramValue}" cssClass="ui-input"  required="true" size="50"/>
	<s:textfield label="%{@com.hoomsun.model.TaskTimerParam@ALIAS_DISPLAY_NAME}" id="displayName" key="taskTimerParam.displayName" value="%{taskTimerParam.displayName}" cssClass="ui-input"  size="50"/>
	<s:textarea cssClass="ui-textarea" label="%{@com.hoomsun.model.TaskTimerParam@ALIAS_PARAM_DESC}" id="taskParamDesc" key="taskTimerParam.taskParamDesc" value="%{taskTimerParam.taskParamDesc}"  ></s:textarea>
	<tr>
	    <td class="tdLabel"><%=TaskTimerParam.ALIAS_TASK_ID %>:</td>	    
	    <td>
	    	<s:bean name="com.hoomsun.schedule.dao.TaskTimerDAO" var="cm"></s:bean>	   	
	    	<s:property value="#cm.getTaskNameById(#request.taskId)"/>
	    	
	    </td>
	</tr>
		
	
