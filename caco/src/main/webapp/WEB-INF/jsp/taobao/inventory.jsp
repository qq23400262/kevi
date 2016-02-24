<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE HTML>
<html>
<head>
<script type="text/javascript" src="../js/jquery-2.2.0.min.js"></script>
<style type="text/css">
body,canvas {
	Overflow：hidden;
	overflow-x: "hidden", overflow-y:"hidden"
}
</style>
</head>
<body>
	<form action="../util/fileupload" method="post" enctype="multipart/form-data">
		上传库存Excel:<input type="file" name="files" />
		<input type="submit">
	</form>
</body>
</html>
