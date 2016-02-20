var canvas;
function resizeCanvas() {
	$("#canvas").attr("width", $(window).get(0).innerWidth);
	$("#canvas").attr("height", $(window).get(0).innerHeight);
}
$(window).resize(function() {
	resizeCanvas();
});

$(document.body).css({
	"overflow-x" : "hidden",
	"overflow-y" : "hidden"
});
function initCanvas() {
	canvas = document.getElementById('canvas').getContext('2d');
	resizeCanvas();
} 

function drawMenu() {
	var text = new CText(canvas, null);
	text.show();
}

$(function() {
	initCanvas();
	drawMenu();
});