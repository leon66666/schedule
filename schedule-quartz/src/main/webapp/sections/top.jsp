<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ include file="/commons/top_page.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>top</title>
    <link rel="stylesheet" href="<%=basePath %>static1/css/one.css" />
</head>
<script language='javascript'>
var preFrameW = '206,*';
var FrameHide = 0;
var curStyle = 1;
var totalItem = 9;
function ChangeMenu(way){
	var addwidth = 10;
	var fcol = top.document.all.btFrame.cols;
	if(way==1) addwidth = 10;
	else if(way==-1) addwidth = -10;
	else if(way==0){
		if(FrameHide == 0){
			preFrameW = top.document.all.btFrame.cols;
			top.document.all.btFrame.cols = '0,*';
			FrameHide = 1;
			return;
		}else{
			top.document.all.btFrame.cols = preFrameW;
			FrameHide = 0;
			return;
		}
	}
	fcols = fcol.split(',');
	fcols[0] = parseInt(fcols[0]) + addwidth;
	top.document.all.btFrame.cols = fcols[0]+',*';
}


function mv(selobj,moveout,itemnum)
{
   if(itemnum==curStyle) return false;
   if(moveout=='m') selobj.className = 'itemsel';
   if(moveout=='o') selobj.className = 'item';
   return true;
}

function changeSel(itemnum)
{
  curStyle = itemnum;
  for(i=1;i<=totalItem;i++)
  {
     if(document.getElementById('item'+i)) document.getElementById('item'+i).className='item';
  }
  document.getElementById('item'+itemnum).className='itemsel';
}

</script>

</head>
<%/*
	if(session.getAttribute("users")!=null){
	session.removeAttribute("users");
	}
	Employee emp1=new Employee();
	EmployeeDAO empDao= new EmployeeDAO();
	emp1=empDao.findById(Integer.valueOf("3"));
	session.setAttribute("users", emp1);
	*/	
 %>
 <script type="text/javascript">

function operationTip(a,b,c){
if(confirm(a)){
window.open(b,c);
}}</script>
<div class="fn-clear header-nav content-box">
	<h1 class="fn-left"><a class="pg-logo" href="http://hoomxb.com" target="_blank"></a></h1>
	<h2 class="fn-left">自动任务管理系统</h2>
	<div class="fn-right header-nav-list">
		<ul class="fn-clear">
			<li><a href="javascript:operationTip('确认退出吗','/Login/logOut.action','_top')"><i class="icon icon-quit mr5"></i>注销退出</a></li>
			<li><i class="icon icon-user mr10"></i>${logingUser.username}</li>
			<li><a href="http://www.hoomxb.com" target="_blank"><i class="icon icon-home mr5"></i>红小宝首页</a></li>
		</ul>
	</div>
</div>
