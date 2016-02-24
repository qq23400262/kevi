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
	<form id="fileForm" action="../util/fileupload" method="post" enctype="multipart/form-data">
		上传库存Excel:<input type="file" name="files" />
		<input type="button" value="上传更新库存" onclick="uploadFile()">
		<input type="button" id='button1' value="test">
	</form>
</body>
<script type="text/javascript">
function uploadFile() {
	//$('form').submit();
	/*$.ajax({
	    type: 'POST',
	    contentType: 'application/json',
	    url: 'WS_Page.asmx/DeleteInfo',
	    data: "{id:" + id + "}",
	    dataType: 'json',
	    cache: false,
	    success: function(json) {
	        if (json.d == "success") {
	            alert('删除成功!');
	            InitList();
	        } else {
	            alert(json.d);
	        }
	    },
	    error: function(err) {
	        alert(err.responseText);
	    }
	});*/
	/*$.ajax({
        type: "POST",
        dataType: "html",
        url: '../util/fileupload',
        data: $('#fileForm').serialize(),
        success: function (result) {
            var strresult=result;
            alert(strresult);
            //加载最大可退金额
            $("#spanMaxAmount").html(strresult);
        },
        error: function(data) {
            alert("error:"+data.responseText);
         }
    });*/
	/*$.ajax({
	    type: 'POST',
	    url: '../util/fileupload',
	    data: new FormData( this ),
	    processData : false,
	    contentType: 'application/json',           
	    success : function(r) {
	        console.log(r);
	        //if (errors != null) { } else context.close();
	    },
	    error: function(r) {console.info(r); alert('jQuery Error'); }
	});*/
    $.ajax({
        contentType: "multipart/form-data;charset=UTF-8;boundary=boundary;",
        url: '../util/fileupload',
        type: 'POST',
        dataType: 'text',
        enctype: 'multipart/form-data',
        data: $("#fileForm").serialize(),
        error: function(){
             alert('操作失败');
        },
        success: function(data){
           console.info(data);
        }
    });
	/*$.ajax({
		  type : "POST",
		  url : "inventory/update?fileName=2e22",
		  data:null,
		  contentType:"application/json",  //发送至服务器的类型
		  dataType : "json",     //预期服务器返回类型
		  async:false,
		  success: function(data){
		   	console.info(data);
		   	return;
		  },
		  error: function(r) {console.info(r); alert('jQuery Error'); }
		 });*/
}
//http://blog.okbase.net/jquery2000/archive/774.html
$('#button1').click(function(){
    var formData = new FormData($('form')[0]);
    $.ajax({
        url: '../util/fileupload',  //server script to process data
        type: 'POST',
        xhr: function() {  // custom xhr
            myXhr = $.ajaxSettings.xhr();
            if(myXhr.upload){ // check if upload property exists
                myXhr.upload.addEventListener('progress',progressHandlingFunction, false); // for handling the progress of the upload
            }
            return myXhr;
        },
        //Ajax事件
        beforeSend: beforeSendHandler,
        success: completeHandler,
        error: errorHandler,
        // Form数据
        data: formData,
        //Options to tell JQuery not to process data or worry about content-type
        cache: false,
        contentType: false,
        processData: false
    });
});
function beforeSendHandler(e) {
	
}
function completeHandler(data) {
	//console.info("fileName="+data.entity_data);
	$.ajax({
	  type : "POST",
	  url : "inventory/update",
	  data:"fileName="+data.entity_data,
	  dataType : "json",     //预期服务器返回类型
	  async:false,
	  success: function(data){
	   	alert(data.entity_data);
	   	return;
	  },
	  error: function(r) {console.info(r); alert('jQuery Error'); }
	 });
}
function errorHandler(e) {
	
}
function progressHandlingFunction(e){
    if(e.lengthComputable){
        $('progress').attr({value:e.loaded,max:e.total});
    }
}
</script>
</html>
