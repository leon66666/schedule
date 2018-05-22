<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!doctype html>
<html lang="en">
<head>
	<title>menu</title>
	<%
      String path = request.getContextPath();
      String basePath = "/";
    %>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	<link rel="stylesheet" href="<%=basePath %>static1/css/one.css" />
	<script src="<%=basePath %>static1/js/lib/jquery/1.9.1/jquery.nocmd.js"></script>
	<base target="main" />
</head>
<body>
<div class="side-bar">
	<ul>
		<s:if test="#session.logingUser.username=='admin'">
			<li class="current set"><a href="/TaskTimer/list.action"><i class="icon-sidebar sidebar-deploy"></i>配置修改</a><i class="icon icon-sidesnow"></i></li>
		</s:if>
		<s:if test="#session.logingUser.username=='luzongwei'">
			<li class="current set"><a href="/TaskTimer/list.action"><i class="icon-sidebar sidebar-deploy"></i>配置修改</a><i class="icon icon-sidesnow"></i></li>
		</s:if>
		<%--<li class="log"><a href="/TaskLog/list.action"><i class="icon-sidebar sidebar-daily"></i>日志展示</a></li>--%>
		<li class="check"><a href="/CheckAccountLog/list.action"><i class="icon-sidebar sidebar-examine"></i>自动检查</a></li>
	</ul>
</div>
</body>
<script type="text/javascript">
  	$('.side-bar li').click(function(){
    	$(".side-bar li i.icon").remove();
    	$('.side-bar li').removeClass('current')
    	$(this).addClass('current');
    	$(this).append('<i class="icon icon-sidesnow"></i>');
  	});
</script>
</html>