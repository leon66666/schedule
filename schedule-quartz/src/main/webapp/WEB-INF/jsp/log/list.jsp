<%@page import="com.hoomsun.model.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib tagdir="/WEB-INF/tags/simpletable" prefix="simpletable"%>
<%@ include file="/commons/top_page.jsp"%>
<html>
<head>
	<title>日志列表</title>
	<script src="<%=basePath %>static1/js/widgets/Util.js"></script>
	<script src="<%=basePath%>static1/js/widgets/simpletable/simpletable.js"></script>
	<script type="text/javascript">
		jQuery(document).ready(function() {
			// 分页需要依赖的初始化动作
			window.simpleTable = new SimpleTable('queryForm',${page.thisPageNumber},${page.pageSize},'${pageRequest.sortColumns}');
		});
	</script>
</head>
<body>
	<div class="pg-content">
		<form id="queryForm" name="queryForm" action="/TaskLog/list.action" method="post">
			<div class="search-container content-box">
				<h3>搜索</h3>
				<div class="search-content fn-clear">
					<div class="fn-left search-form">
						<div class="fn-clear">
							<div class="fn-left btn-group mr38 mt5">
								<input type="hidden" name="operateType"/>
								<button type="button" class="dropdown-toggle" data-toggle="dropdown">
									<span selectid="default">请选择操作类型</span> 
									<i class="icon icon-snow ml10"></i>
								</button>
								<div class="dropdown-menu" role="menu">
									<i class="icon icon-list-snow"></i>
									<ul>
										<s:set var="map" value="#{'RUNNING':'开始','STOPPING':'结束'}"></s:set>
										<s:iterator value="#map">
										        <li selectid="<s:property value="key" />" <s:if test="#request.query.operateType == key"> data-selected="selected" </s:if> ><s:property value="value" /></li>
										</s:iterator> 
									</ul>
								</div>
							</div>
							<div class="fn-left btn-group mr82 mt5">
								<input type="hidden" name="version"/>
								<button type="button" class="dropdown-toggle" data-toggle="dropdown">
									<span selectid="default">请选择运行机器</span> 
									<i class="icon icon-snow ml10"></i>
								</button>
								<div class="dropdown-menu" role="menu">
									<i class="icon icon-list-snow"></i>
									<ul>
										<s:set var="map" value="#{'master':'主机','slave':'备机'}"></s:set>
										<s:iterator value="#map">
										        <li selectid="<s:property value="key" />" <s:if test="#request.query.version == key"> data-selected="selected" </s:if> ><s:property value="value" /></li>
										</s:iterator> 
									</ul>
								</div>
							</div>
							<div class="fn-left search-box">
								<input class="ui-input ui-input-search" type="text" value="${query.taskName}" id="taskName" name="taskName" maxlength="250" placeholder="请输入任务名称">
								<input class="ui-button-search-hidden" type="submit" value="搜索" onclick="getReferenceForm(this).action='Tasklog/list.action'">
								<a class="ui-button-search" href="###" ><i class="icon icon-search"></i></a>
							</div>
							
						</div>
					</div>
				</div>
			</div>

			<div class="ui-table-list content-box">
				<table class="ui-table">
					<s:bean name="com.hoomsun.schedule.dao.TaskTimerDAO" var="cm"></s:bean>	    	
					<caption class="tableTitle">日志列表</caption>
						<thead>
							<tr>
								<!-- 排序时为th增加sortColumn即可,new SimpleTable('sortColumns')会为tableHeader自动增加排序功能; -->
								<th width="10%" sortColumn="tt.taskName">所属任务</th >
								<th width="15%" sortColumn="t.startTime">上次开始运行时间</th >
								<th width="15%" sortColumn="t.endTime">上次结束运行时间</th>								
								<th width="8%" sortColumn="t.version">运行机器</th>
								<th width="12%">日志描述</th>
								<th width="8%" sortColumn="t.operateType">操作类型</th>
								<th width="15%" sortColumn="t.createTime">创建时间</th>
								<th sortColumn="t.creater">创建人</th>
							</tr>
						</thead>
						<tbody>
							<s:iterator value="#request.page.result" var="item" status="status">
								<tr class="${status.count % 2 == 0 ? 'dark' : ''}">
									<td class="text-left">
										<span class="fn-text-overflow table-title" title="${item[1]}">${item[1]}</span>
									</td>
									<td>
										<s:date name="#item[0].startTime" format="yyyy-MM-dd HH:mm:ss"/>
									</td>
									
									<td>
										<s:date name="#item[0].endTime" format="yyyy-MM-dd HH:mm:ss"/>
									</td>
									<td>
										<s:property value="#item[0].version"/>
									</td>
									
									<td>
										<s:if test="%{#item[0].taskLogDesc.length() > 15}">
											<font title="<s:property value="#item[0].taskLogDesc"/>"><s:property value="%{#item[0].taskLogDesc.substring(0,15) + \"...\"}"/></font>
										</s:if>
										<s:else>
											<s:property value="#item[0].taskLogDesc"/>
										</s:else>
									</td>
									<td>
									<s:if test="'STOPPING'==#item[0].operateType">
										<span class="color-text-red">结束</span>
									</s:if>
									<s:else>
										<span class="color-text-green">开始</span>
									</s:else>
									</td>
									<td>
										<s:date name="#item[0].createTime" format="yyyy-MM-dd HH:mm:ss"/>
									</td>
									<td>
										<span class="fn-text-overflow table-title" title="<s:property value="#item[0].creater"/>"><s:property value="#item[0].creater"/></span>
									</td>
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