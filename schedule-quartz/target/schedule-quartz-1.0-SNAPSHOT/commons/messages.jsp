<%@ taglib uri="/struts-tags" prefix="s"%>
<%-- Info Messages --%>
<s:if test="#request.flash.success != null">
	<div class="alert alert-success">
		${flash.success}<br/>
	</div>    
</s:if>

<%-- Error Messages --%>
<s:if test="#request.flash.error != null">
	<div class="alert alert-warning">
		${flash.error}<br/>
	</div> 
</s:if>