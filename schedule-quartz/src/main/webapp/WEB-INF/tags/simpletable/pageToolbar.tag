<%@ tag pageEncoding="UTF-8"%>
<%@ attribute name="page" required="true" type="com.hoomsun.page.Page" description="Page.java" %>
<%@ attribute name="pageSizeSelectList" type="java.lang.Number[]" required="false"  %>
<%@ attribute name="isShowPageSizeList" type="java.lang.Boolean" required="false"  %>
<%@ taglib uri="/struts-tags" prefix="s"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	// set default values
	Integer[] defaultPageSizes = new Integer[]{30,50,100};
	if(request.getAttribute("pageSizeSelectList") == null) {
	    request.setAttribute("pageSizeSelectList",defaultPageSizes); 
	}
	
	if(request.getAttribute("isShowPageSizeList") == null) {
	    request.setAttribute("isShowPageSizeList",true); 
	} 
%>

<div class="page-nav page-nav-right mh25">
	<s:if test="#request.page.hasPreviousPage"><a class="page-prev" href="javascript:simpleTable.togglePage(${page.previousPageNumber});">上一页</a></s:if>

	<s:iterator value="#request.page.linkPageNumbers" var="item" status="status">
		<s:if test="#item ==#request.page.thisPageNumber}">[${item}]1</s:if>
		<s:else>
			<a class="${page.thisPageNumber == item ? 'current' : '' }" href="javascript:simpleTable.togglePage(${item});">${item}</a>
		</s:else>
	</s:iterator>

	<s:if test="#request.page.hasNextPage"><a class="page-next" href="javascript:simpleTable.togglePage(${page.nextPageNumber});">下一页</a></s:if>

</div>
