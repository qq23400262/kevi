/**
 * 控件的最原始方法
 */
function CControl() {};
/**
 * 测试方法，暂留
 */
CControl.prototype.alertId = function() {
	alert(this.data.id);
};


/**
 * 专门用来显示文本信息的控件
 */
function CText(cavas, data) {
	this.data = data;
	this.cavas = cavas;
	this.prototype = CControl.prototype;
};
CText.prototype = new CControl();
CText.prototype.show = function() {
	this.cavas.font = 'bold 144px consolas';
	this.cavas.textAlign = 'left';
	this.cavas.textBaseline = 'top';
	this.cavas.strokeStyle = '#DF5326';
	this.cavas.strokeText('Hello', 100, 100);
	this.cavas.font = 'bold 144px arial';
	this.cavas.fillStyle = 'red';
	this.cavas.fillText('World', 300,300);
};