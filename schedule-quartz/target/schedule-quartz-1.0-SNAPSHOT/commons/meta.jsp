<%
String path = request.getContextPath();
String basePath = "/";
%>
<!-- rapid-validation BEGIN-->
<link rel="stylesheet" href="<%=basePath %>static1/css/one.css" />
<script src="<%=basePath%>static1/js/lib/rapid-validation/prototype_for_validation.js" type="text/javascript"></script>
	
<!-- show validation error as tooptips -->
<script src="<%=basePath%>static1/js/lib/rapid-validation/tooltips.js" type="text/javascript"></script>
<link href="<%=basePath%>static1/js/lib/rapid-validation/tooltips.css" type="text/css" rel="stylesheet">
<script src="<%=basePath%>static1/js/lib/rapid-validation/validation_cn.js" type="text/javascript"></script>

<!-- rapid-validation END-->
<script src="<%=basePath %>static1/js/lib/jquery/1.9.1/jquery.nocmd.js"></script>
<script src="<%=basePath%>static1/js/lib/application.js" type="text/javascript"></script>
<script src="<%=basePath%>static1/js/lib/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script type="text/javascript">
	jQuery(document).ready(function(){
		jQuery("input[type=text]").addClass("max-length-50");
	});
</script>