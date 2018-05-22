<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.hoomsun.model.Staff" %>
<%
  String path = request.getContextPath();
  String basePath = "/";
  // String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!doctype html>
<html lang="en">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>自动任务管理系统 - 用户登录</title>

    <link rel="stylesheet" href="<%=basePath %>static1/css/one.css" />
    <link rel="stylesheet" href="<%=basePath %>static1/css/login.css" />
    <script src="<%=basePath %>static1/js/lib/jquery/1.9.1/jquery.nocmd.js"></script>
    <script src="<%=basePath %>static1/js/widgets/md5-min.js"></script>

    <script>
      
      if(self != top) window.top.location.href="/visitor/login.action";
      
      if(!/login/.test(window.top.location.href))window.top.location.href="/visitor/login.action";
    </script>
    <script>
  		$(function(){
    		$('.ui-button-login').on('click',function(){
      			var userName = $('input[name="username"]');
      			var password = $('input[name="password"]');
      			if(!userName.val() || !password.val()){
	        		alert('请输入用户名或密码！');
      			}
    		});
  		});
	</script>
  </head>
  <body>
    <div class="pg-login">
      <div class="pg-login-container">
        <h1>
          <span class="login-logo"></span>
          <span class="mh25">自动任务管理系统</span>
        </h1>
        <div class="fn-clear login-form">
          <form action="/visitor/login.action" method="post">
            <div class="fn-left">
              <div class="fn-clear login-form-input">
                <input class="fn-left ui-input ui-input-login" type="text" name="username" placeholder="用户名" value="admin" />
                <input class="fn-right ui-input ui-input-login" type="password" name="password1" id="password1" placeholder="密码" value="111111" />
                <input type="hidden" name="password" id="password"/>
              </div>
            </div>
            <div class="fn-left login-form-button">
              <input class="ui-input ui-button-login" type="submit" onclick="document.getElementById('password').value = document.getElementById('password1').value" value="登录" />
            </div>
          </form>
        </div>
      </div>
    </div>
  </body>
</html>
