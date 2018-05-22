<%@page import="com.hoomsun.model.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib tagdir="/WEB-INF/tags/simpletable" prefix="simpletable"%>
<%@ include file="/commons/top_page.jsp"%>
<html>
<head>
	<title>任务列表</title>
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
		<form id="queryForm" name="queryForm" action="/TaskTimer/list.action" method="post">
			<div class="search-container content-box">
				<h3>搜索</h3>
				<div class="search-content fn-clear">
					<div class="fn-left search-form">
						<div class="fn-clear">
							<div class="fn-left btn-group mr38 mt5">
								<input type="hidden" name="enable" value="">
								<button type="button" class="dropdown-toggle" data-toggle="dropdown">
									<span selectid="default">请选择启用状态</span> 
									<i class="icon icon-snow ml10"></i>
								</button>
								<div class="dropdown-menu" role="menu">
									<i class="icon icon-list-snow"></i>
									<ul>
										<s:set var="map" value="#{1:'是',0:'否'}"></s:set>
										<s:iterator value="#map">
										    	
										        <li selectid="<s:property value="key" />" <s:if test="#request.query.enable == key"> data-selected="selected" </s:if> ><s:property value="value" /></li>
										      
										 
										</s:iterator> 
									</ul>
								</div>
							</div>
							<div class="fn-left search-box">
								<input class="ui-input ui-input-search" type="text"  value="${query.taskName}" id="taskName" name="taskName" placeholder="请输入任务名称">
								<input class="ui-button-search-hidden" type="submit" value="搜索">
								<a class="ui-button-search" href="###"><i class="icon icon-search"></i></a>
							</div>
						</div>
					</div>
					<div class="fn-left search-button-group">
						<button type="button" class="ui-button mr36" onclick="location.href='/TaskTimer/create.action'"><i class="icon icon-add mr5"></i>增加</button>
						<button type="button" class="ui-button mr36" id="delButton"><i class="icon icon-del mr5"></i>删除</button>
						<button type="button" class="ui-button" id="runCountButton"><i class="icon icon-refresh mr5"></i>刷新次数</button>
					</div>
				</div>
			</div>


			<div class="ui-table-list content-box">
				<table class="ui-table">
					<caption>自动任务列表</caption>
					<thead>
						<tr>
							<th sortColumn="t.taskName" class="text-left" width="20%"><input type="checkbox" id="items" class="ui-checkbox"><i class="icon icon-uncheckbox"></i>任务名称</th>
							<th sortColumn="t.taskClass" class="text-left" width="2%">任务类</th>
							<th sortColumn="t.taskStatus" width="11%">运行状态&nbsp;/&nbsp;次数</th>
							<th sortColumn="t.enable" width="8%">是否启用</th>
							<th sortColumn="t.isTiming" width="8%">定时任务</th>
							<th sortColumn="t.version" width="14%">上次运行机器</th>
							<th sortColumn="t.createTime" width="14%">创建时间</th>
							<th sortColumn="t.creater">创建人</th>
							<th width="17%">操作</th>
						</tr>
					</thead>
					<tbody>
					<s:iterator  value="#request.page.result" var="item" status="status">
						<tr class="${status.count % 2 == 0 ? 'dark' : ''}">
							<td class="text-left">
								<input type="checkbox" name="items" value="${item.id}" class="ui-checkbox ui-list-checkbox"><i class="icon icon-uncheckbox"></i><span title="<s:property value="#item.taskName"/>"><s:property value="#item.taskName"/></span>
							</td>
								
							<td class="text-left">
								<span title="<s:property value="#item.taskClass"/>"><s:property value="#item.taskClass"/></span>
							</td>
							<td>
								<s:bean name="com.hoomsun.schedule.dao.TaskTimerDAO" var="cm"></s:bean>
								<s:if test="#cm.getStoping()==#item.taskStatus">
									<span class="color-text-red">
										<s:property value="#item.taskStatus"/>&nbsp;/&nbsp;<span name="runCount" val="${item.taskClass}">0</span>
									</span>
								</s:if>
								<s:else>
									<span class="color-text-green">
										<s:property value="#item.taskStatus"/>&nbsp;/&nbsp;<span class="runCount" val="${item.taskClass}"></span>
									</span>
								</s:else>
							</td>
							<td>
								<span class="${item.enable==1?'color-text-green':'color-text-red'}">${item.enable==1?'是':'否'}</span>
							</td>
							<td >
								<span class="${item.isTiming==1?'color-text-green':'color-text-red'}">${item.isTiming==1?'是':'否'}</span>
							</td>
							<td >
								<s:property value="#item.version"/>
							</td>
							<td >
								<s:date name="#item.createTime" format="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td >
							<s:if test="%{#item.creater.length() > 10}">
								<font title="<s:property value="#item.creater"/>"><s:property value="%{#item.creater.substring(0,10) + \"...\"}"/></font>
								</s:if>
								<s:else>
								<s:property value="#item.creater"/>
								</s:else>
							</td>
							<td>
								<span class="table-link-group color-text-blue">
									<a href="/TaskTimer/edit.action?taskId=<s:property value='#item.id'/>"><i class="icon icon-edit" title="编辑"></i></a>
									<a href="/TaskTimerParam/list.action?taskId=<s:property value='#item.id'/>"><i class="icon icon-set" title="参数配置"></i></a>
								 	<s:bean name="com.hoomsun.schedule.dao.TaskTimerDAO" var="cm"></s:bean>	 
									<s:if test="#cm.getStoping()==#item.taskStatus">
										<s:if test="#item.enable==1">
											<a href="/TaskTimer/startTask.action?taskId=<s:property value='#item.id'/>"><i class="icon icon-task" title="启动任务"></i></a>
										</s:if>
										<s:else>
											<i class="icon icon-task" title="未启用的任务不能启动"></i>
										</s:else>
									</s:if>
									<s:else>
										<s:if test="#cm.getCurrentHost()==#item.version">
											<a href="/TaskTimer/stopTask.action?taskId=<s:property value='#item.id'/>"><i class="icon icon-stop" title="停止任务"></i></a>								
										</s:if>
										<s:else>
								   			<i class="icon icon-stop" title="不是当前主机启动，不能停止"></i>					
										</s:else>
									</s:else>
								</span> 
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

			// 刷新次数按钮
			$("#runCountButton").bind("click",function(){
				getRunCount();
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
				location.href="/TaskTimer/delete.action?items="+items;
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
