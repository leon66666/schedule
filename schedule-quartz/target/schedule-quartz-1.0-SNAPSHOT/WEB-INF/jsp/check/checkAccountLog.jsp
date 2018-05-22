<%@page import="com.hoomsun.model.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib tagdir="/WEB-INF/tags/simpletable" prefix="simpletable"%>
<%@ include file="/commons/top_page.jsp"%>
<html>
<head>
	<title>数据检查日志</title>
	<script src="<%=basePath %>static1/js/widgets/Util.js"></script>
	<script src="<%=basePath %>static1/js/widgets/simpletable/simpletable.js"></script>
	<script type="text/javascript">
		jQuery(document).ready(function() {
			// 分页需要依赖的初始化动作
			window.simpleTable = new SimpleTable('queryForm',${page.thisPageNumber},${page.pageSize},'${pageRequest.sortColumns}');
		});
	</script>
</head>
<body>
	<div class="pg-content">
		<form id="queryForm" action="<%=request.getContextPath()%>/CheckAccountLog/list.action" method="post">
			<div class="search-container content-box">
				<h3>搜索</h3>
				<div class="search-content fn-clear">
					<div class="fn-left search-form search-check-from">
						<div class="fn-clear">
							<div class="fn-left btn-group mr38 mt2">
								<input type="hidden" name="checkAccountType">
								<button type="button" class="dropdown-toggle" data-toggle="dropdown">
									<span selectid="default">请选择类型</span> 
									<i class="icon icon-snow ml10"></i>
								</button>
								<div class="dropdown-menu" role="menu">
									<i class="icon icon-list-snow"></i>
									<ul>
										<s:iterator value="@com.hoomsun.common.CheckAccountType@values()" var="type_">
											<li selectid="<s:property value="#type_.name()" />" <s:if test="#type_.name() == #request.query.checkAccountType"> data-selected="selected" </s:if> ><s:property value="#type_" /></li>
										</s:iterator>
									</ul>
								</div>
							</div>
							<div class="fn-left btn-group mr38 mt2">
								<input type="hidden" name="checkAccountstatus">
								<button type="button" class="dropdown-toggle" data-toggle="dropdown">
									<span selectid="default">请选择状态</span> 
									<i class="icon icon-snow ml10"></i>
								</button>
								<div class="dropdown-menu" role="menu">
									<i class="icon icon-list-snow"></i>
									<ul>
										<s:iterator value="@com.hoomsun.common.CheckAccountStatus@values()" var="status_">
											<li selectid="<s:property value="#status_.name()" />" <s:if test="#status_.name() == #request.query.checkAccountstatus"> data-selected="selected"</s:if>><s:property value="#status_" /></li>
										</s:iterator>
									</ul>
								</div>
							</div>
							
							<div class="fn-left search-time-box">
								<input class="ui-input ui-input-search ui-input-time" type="text" name="start" value="<s:property value="start" />" placeholder="起始时间" onClick="WdatePicker()">
								<i class="icon icon-timearrow mw12"></i>
								<input class="ui-input ui-input-search ui-input-time mr36" type="text" name="end" value="<s:property value="end" />" placeholder="终止时间" onClick="WdatePicker()">
								<input class="ui-button-search-hidden " type="submit" value="搜索" onclick="jump(1)">
								<a class="ui-button-search ui-icon-search" href="###"><i class="icon icon-search"></i></a>
							</div>
							
						</div>
					</div>
				</div>
			</div>

			<div class="ui-table-list content-box">
				<table class="ui-table">
					<caption>自动检查</caption>
					<thead>
						<tr>
							<th class="text-left">主键</th>
							<th width="20%">类型</th>
							<th width="10%">状态</th>
							<th width="15%">时间</th>
							<th width="10%">错误数量</th>
							<th width="10%">新增数量</th>
							<th width="10%">修复数量</th>
							<th width="15%">检查用时</th>
							<th width="15%">修复用时</th>
						</tr>
					</thead>
					<tbody>
						<s:iterator value="#request.page.result" var="item" status="s">
						<tr class="${s.count % 2 == 0 ? 'dark' : ''}">
								<td class="text-left">
								<s:if test="#item.status.toString() =='正确'">
										${item.id}
								</s:if>
								<s:else>
									<a href="<%=request.getContextPath()%>/CheckAccountError/list.action?logId=${item.id}">${item.id}</a>
								</s:else>
							</td>
							<td class="text-left">
								${item.type.toString()}
							</td>
							<td>
								<s:if test="#item.status.toString()=='正确'">
									<span class="color-text-green">${item.status.toString()}</span>
								</s:if>
								<s:else>
									<span class="color-text-red">${item.status.toString()}</span>
								</s:else>
							</td>							

							<td>
								<s:date name="#item.createTime" format="yyyy-MM-dd HH:mm:ss" />
							</td>
							<td class="text-left">${item.errorCount}条</td>
							<td class="text-left">${item.increaseCount}条</td>
							<td class="text-left">${item.repairCount}条</td>
							<td><s:property value="@com.hoomsun.util.CalendarUtil@getDistanceTimeHour(#item.checkUseSeconds)" /></td>
							<td><s:property value="@com.hoomsun.util.CalendarUtil@getDistanceTimeHour(#item.repairUseSeconds)" /></td>
						</tr>
						</s:iterator>
					</tbody>
				</table>
				<simpletable:pageToolbar page="${page}"></simpletable:pageToolbar>
			</div>
		</form>
	</div>
</body>
</html>