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
			$(document).ready(function() {
				// 分页需要依赖的初始化动作
				window.simpleTable = new SimpleTable('queryForm',${page.thisPageNumber},${page.pageSize},'${pageRequest.sortColumns}');
			});
		</script>
	</head>
<body>
	<div class="pg-content">
		<form id="queryForm" action="<%=request.getContextPath()%>/CheckAccountError/list.action" method="post">
		<input type="hidden" name="logId" value="${logId }" />
		<div class="mb20">
			<a href="<%=request.getContextPath()%>/CheckAccountLog/list.action">返回</a>
		</div>
		<div class="ui-table-list content-box">
			<table class="ui-table">
				<tr>
					<th>主键</th>
					<!-- 
					<th width="200px">时间</th>
					 -->
					<th width="7%">数字1</th>
					<th width="7%">数字2</th>
					<th width="7%">数字3</th>
					<th width="7%">数字4</th>
					<th width="7%">数字5</th>
					<th width="7%">金额1</th>
					<th width="7%">金额2</th>
					<th width="7%">金额3</th>
					<!-- 
					<th width="100px">金额4</th>
					<th width="100px">日期1</th>
					<th width="100px">日期2</th>
					 -->
					<th width="20%">字符串1</th>
					<th width="20%">字符串2</th>
					<!-- 
					<th width="100px">字符串3</th>
					<th width="100px">字符串4</th>
					<th width="100px">字符串5</th>
					<th width="100px">字符串6</th>
					<th width="100px">字符串7</th>
					<th width="100px">字符串8</th>
					 -->
				</tr>
				<s:iterator value="#request.page.result" var="item" status="s">
					<tr class="${s.count % 2 == 0 ? 'dark' : ''}">
						<td align="center">
							${item.id}
						</td>
						<!-- 
						<td align="center">
							<s:date name="#log.createTime" format="yyyy-MM-dd HH:mm:ss" />
						</td>
						 -->
						<td>
							${item.custNumber1}
						</td>
						<td>
							${item.custNumber2}
						</td>
						<td>
							${item.custNumber3}
						</td>
						<td>
							${item.custNumber4}
						</td>
						<td>
							${item.custNumber5}
						</td>
						<td>
							${item.custAmount1}
						</td>
						<td>
							${item.custAmount2}
						</td>
						<td>
							${item.custAmount3}
						</td>
						<!-- 
						<td>
							<s:property value="#log.custAmount4" />
						</td>
						<td>
							<s:date name="#log.custDate1" format="yyyy-MM-dd HH:mm:ss" />
						</td>
						<td>
							<s:date name="#log.custDate2" format="yyyy-MM-dd HH:mm:ss" />
						</td>
						 -->
						<td>
							${item.custText1}
						</td>
						<td>
							${item.custText2}
						</td>
						<!-- 
						<td>
							<s:property value="#log.custText3" />
						</td>
						<td>
							<s:property value="#log.custText4" />
						</td>
						<td>
							<s:property value="#log.custText5" />
						</td>
						<td>
							<s:property value="#log.custText6" />
						</td>
						<td>
							<s:property value="#log.custText7" />
						</td>
						<td>
							<s:property value="#log.custText8" />
						</td>
						 -->
					</tr>
				</s:iterator>
			</table>
			<simpletable:pageToolbar page="${page}"></simpletable:pageToolbar>
		</div>
		</form>
	</div>
</body>
</html>