

$(function() {
	$(document.body).css({
		"overflow-x" : "hidden",
		"overflow-y" : "hidden"
	});
	
	var canvas = new Canvas({id:'canvas'});
	$(window).resize(function() {
		canvas.resizeCanvas();
	});
	//initCanvas();
	//canvas.drawMenu();
});