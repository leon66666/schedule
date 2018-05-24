<%@page import="wangzhongqiu.model.TaskTimer"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
	<s:bean name="com.hoomsun.schedule.dao.TaskTimerDAO" var="cm"></s:bean>	
	<s:hidden name="taskTimer.id" />
<!-- ONGL access static field: @package.class@field or @vs@field -->
	<s:textfield label="%{@com.hoomsun.model.TaskTimer@ALIAS_TASK_NAME}" id="taskName" key="taskTimer.taskName" value="%{taskTimer.taskName}"  cssClass="ui-input mt20" required="true" size="50"/>
	<tr>
	    <td class="tdLabel"><%=TaskTimer.ALIAS_TASK_CLASS %></td>	 
	    <td>
			<s:if test="#request.taskTimer.taskStatus==#cm.getRunning()">
				${taskTimer.taskClass}
				<s:hidden key="taskTimer.taskClass" theme="simple"  value="%{taskTimer.taskClass}"/>
			</s:if>
			<s:else>
				<s:textfield theme="simple" label="%{@com.hoomsun.model.TaskTimer@ALIAS_TASK_CLASS}" id="taskClass" key="taskTimer.taskClass" value="%{taskTimer.taskClass}" cssClass="required ui-input" required="true" size="50"/>
			</s:else>
	    </td>
	</tr>   
	<tr>
	            
	    <td class="tdLabel"><%=TaskTimer.ALIAS_TASK_STATUS %></td>	    
	    <td>
	    	<s:if test="#request.taskTimer.taskStatus!=null">
	    		<s:property value="%{taskTimer.taskStatus}"/>
	    	</s:if>
	    	<s:else>
	    		停止
	    	</s:else>	
	    </td>
	</tr>
	<tr>
	    <td class="tdLabel"><%=TaskTimer.ALIAS_ENABLE %></td>	    
	    <td>
	    	<s:if test="#request.taskTimer.taskStatus==#cm.getRunning()">
	    		<s:hidden key="taskTimer.enable" theme="simple"  value="%{taskTimer.enable}"/>
	    		${taskTimer.enable==1?'是':'否'}
	    	</s:if>
	    	<s:else>
	    		<s:select theme="simple" key="taskTimer.enable" value="%{taskTimer.enable}"  list="#{1:'是',0:'否'}" listKey="key" listValue="value"></s:select>
	    	</s:else>
	    </td>
	</tr>

	<s:select label="%{@com.hoomsun.model.TaskTimer@ALIAS_IS_TIMING}"  key="taskTimer.isTiming" value="%{taskTimer.isTiming}"  list="#{1:'是',0:'否'}" listKey="key" listValue="value"></s:select>
	<s:textarea label="%{@com.hoomsun.model.TaskTimer@ALIAS_TASK_DESC}" id="taskDesc" cssClass="ui-textarea" key="taskTimer.taskDesc" value="%{taskTimer.taskDesc}"  required="true"></s:textarea>
		
	
