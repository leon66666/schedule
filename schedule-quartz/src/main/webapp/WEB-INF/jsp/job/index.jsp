<%@page import="com.hoomsun.model.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ include file="/commons/meta.jsp"%>
<html>
<head>
<title>手动执行任务</title>
</head>
<body>
	<div class="pg-content">
		<div class="content-box">
			<s:form action="/ExecJob/execute.action" method="post">

				<table class="ui-table ui-table-min">
					<tr>
						<td>请选择任务</td>
						<td>
							<select name="name" id="name">
								<option value="scanOverdue">清算任务-逾期扫描</option>
								<option value="loanStatusTask">放款、流标、清算任务（同时需要TaskId）</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>LoanStatusTask表的TaskId</td>
						<td>
							<input type="text" name="taskId">
						</td>
					</tr>
					<tr>
						<td colspan="2" class="text-center">
							<input id="submitButton" class="ui-button mt24 mr36 mb40" name="submitButton" type="submit" value="手动执行" />
						</td>
					</tr>
				</table>
			</s:form>
		</div>
	</div>
</body>
</html>
