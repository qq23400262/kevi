<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Insert title here</title>
</head>
<body>

</body>
<script type="text/javascript">
	$(function(){
		var myCanvas = "<canvas id='myCanvas' style='background: red;' width='" + screen.availWidth + "px' height='"+ screen.availHeight + "px'></vanvas>";
		document.body.insertAdjacentHTML("beforeEnd", myCanvas);
	});
</script>
</html>