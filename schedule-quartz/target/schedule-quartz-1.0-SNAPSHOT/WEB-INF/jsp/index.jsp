<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!doctype html>
<html lang="en">
<head>
  <%
      String path = request.getContextPath();
      String basePath = "/";
      // String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    %>
    <title>自动任务管理系统</title>
  </head>
  <frameset rows="66,*" cols="*" frameborder="no" border="0" framespacing="0">
    <frame src="/sections/top.jsp" name="topFrame" scrolling="no">
    <frameset cols="160,*" name="btFrame" frameborder="NO" border="0" framespacing="0">
      <frame src="/sections/menu.jsp" noresize name="menu" scrolling="no">
      <s:if test="#session.logingUser.username=='admin'">
        <frame src="/TaskTimer/list.action" noresize name="main" scrolling="yes">
      </s:if>
      <s:elseif test="#session.logingUser.username=='luzongwei'">
        <frame src="/TaskTimer/list.action" noresize name="main" scrolling="yes">
      </s:elseif>
      <s:else>
        <frame src="/CheckAccountLog/list.action" noresize name="main" scrolling="yes">
      </s:else>
    </frameset>
  </frameset>
  <noframes>
    <body>您的浏览器不支持框架！</body>
  </noframes>
</html>
