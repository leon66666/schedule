<%@page import="com.hoomsun.model.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib tagdir="/WEB-INF/tags/simpletable" prefix="simpletable"%>
<%@ include file="/commons/top_page.jsp"%>
<head>
	<title>参数列表</title>
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
	<%@ include file="/commons/messages.jsp"%>
	<div class="pg-content">
		<form id="queryForm" name="queryForm" action="/TaskTimerParam/list.action" method="post">

		<div class="search-container content-box">
			<h3>搜索</h3>
			<div class="search-content fn-clear">
				<div class="fn-left search-form">
					<div class="search-box">
						<input class="ui-input ui-input-search" type="text"  value="${query.paramKey}" id="paramKey"
							name="paramKey" maxlength="250"placeholder="请输入参数名称">
						<input class="ui-button-search-hidden" type="submit" value="搜索" onclick="getReferenceForm(this).action='TaskTimerParam/list.action'">
						<input value="${taskId}" id="taskId"
							name="taskId" type="hidden"/>
						<a class="ui-button-search" href="###"><i class="icon icon-search"></i></a>
					</div>
				</div>
				<div class="fn-left search-button-group">
					<button type="button" class="ui-button mr36" onclick="location.href='/TaskTimerParam/create.action?taskId=<s:property value="#request.taskId"/>'"><i class="icon icon-add mr5"></i>增加</button>
					<button type="button" class="ui-button" id="delButton"><i class="icon icon-del mr5"></i>删除</button>
				</div>
			</div>
		</div>


		<div class="ui-table-list content-box">
			<table class="ui-table">
			<s:bean name="com.hoomsun.schedule.dao.TaskTimerDAO" var="cm"></s:bean>	    	
			<caption class="tableTitle"><b>[<s:property value="#cm.getTaskNameById(taskId)"/>]</b>:对应的<%=TaskTimerParam.TABLE_ALIAS%>列表</caption>
				<thead>
					<tr>
						<th width="1%">&nbsp</th>
						<!-- 排序时为th增加sortColumn即可,new SimpleTable('sortColumns')会为tableHeader自动增加排序功能; -->
						<th width="17%" class="text-left"><input type="checkbox" id="items" class="ui-checkbox"><i class="icon icon-uncheckbox"></i>所属任务</th >
						<th width="8%" sortColumn="t.paramKey">参数键</th >
						<th width="10%" sortColumn="t.paramValue">参数值</th >								
						<th width="12%" sortColumn="t.displayName">参数显示名称</th>
						<th width="12%">参数描述</th>
						<th width="15%" sortColumn="t.createTime">创建时间</th>
						<th width="10%" sortColumn="t.creater">创建人</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<s:iterator value="#request.page.result" var="item" status="status">
						<tr class="${status.count % 2 == 0 ? 'odd' : 'even'}">
							<td>${page.thisPageFirstElementNumber + status.index}</td>
							<td class="text-left">
								<input type="checkbox" name="items" value="${item.id}" class="ui-checkbox ui-list-checkbox"><i class="icon icon-uncheckbox"></i>
								<span class="fn-text-overflow table-title" title="<s:property value="#cm.getTaskNameById(taskId)"/>"><s:property value="#cm.getTaskNameById(taskId)"/></span>
							</td>
							<td>
								<span class="fn-text-overflow table-title" title="<s:property value="#item.paramKey"/>"><s:property value="#item.paramKey"/></span>
							</td>
							
							<td>
								<s:property value="#item.paramValue"/>
							</td>
							<td>
								<s:property value="#item.displayName"/>
							</td>
							<td>
								<span class="fn-text-overflow table-title" title="<s:property value="#item.taskParamDesc"/>"><s:property value="#item.taskParamDesc"/></span>
							</td>
							<td>
								<s:date name="#item.createTime" format="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td>
								<span class="fn-text-overflow table-title" title="<s:property value="#item.creater"/>"><s:property value="#item.creater"/></span>
							</td>
							<td>
								<a href="/TaskTimerParam/edit.action?taskId=<s:property value='#item.taskId'/>&paramId=<s:property value='#item.id'/>"><i class="icon icon-edit" title="编辑"></i></a>
							</td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
			<simpletable:pageToolbar page="${page}"></simpletable:pageToolbar>
			</div>
		</form>	
	</div>
	<script type="text/javascript">
		$(function(){

			// checkBox 全选
			var i = 0;
			$("#items").click(function(){
				var checked = $(this).prop("checked");
				var iconChecked = checked ? "icon-checkbox" : "icon-uncheckbox";
				$("input[name=items]").prop("checked",checked);
				$(this).siblings('i').removeClass().addClass("icon "+iconChecked);
				$("input[name=items]").siblings('i').removeClass().addClass("icon "+iconChecked);
				if(checked){
					i = $("input[name=items]").length;
				}else{
					i = 0;
				}
			});

			$('.ui-list-checkbox').click(function(){
				var checked = $(this).prop("checked");
				var iconChecked = checked ? "icon-checkbox" : "icon-uncheckbox";
				$(this).siblings('i').removeClass().addClass("icon "+iconChecked);

  				if($(this).prop("checked")){
					i++;
				}else{
					i--;
				}

				if(i == $("input[name=items]").length){
					$("#items").prop("checked", true);
					$("#items").siblings('i').removeClass().addClass("icon icon-checkbox");
				}else{
					$("#items").prop("checked", false);
					$("#items").siblings('i').removeClass().addClass("icon icon-uncheckbox");
				}
			});

			// 删除
			$("#delButton").bind("click",function(){
				var items = new Array();
				if(!$(":checkbox:checked[name='items']").val()){
					alert("请选中数据再删除");
				}else{
					$(":checkbox:checked").each(function(){
						if($(this).val()!='on')
						items.push($(this).val());
					});
				}
				location.href="/TaskTimerParam/delete.action?items="+items;
			});
			// 第一次执行
			getRunCount();
			// 每隔半小时循环执行
			setInterval("getRunCount()",1800000);
		});
		// 获取运行次数
		function getRunCount(){
			$(".runCount").each(function(){
				var $_this=$(this);
				var className=$_this.attr("val");
				$.post("<%=basePath%>TaskTimer/getRunCount.action?className="+className,function(data){
					var util =new Util();
					if(util.isAllNotEmpty(data,data.className));
				var $_className=data.className;
					$_this.html(data[$_className]);
				});
			});
		}

	</script>
</body>
</html>