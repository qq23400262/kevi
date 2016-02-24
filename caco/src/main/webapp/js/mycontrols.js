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
CControl.prototype.containPoint = function(x, y) {
	if(x>=this.x && x<= this.x+this.width && y>= this.y && y<=this.y+this.height) {
		return true;
	}
	return false;
}
/**
 * 专门用来显示文本信息的控件
 */
function CText(options) {
	this.options = options;
	if(options.fontSize==null) {
		options.fontSize = 12;
	}
	this.ctx = options.ctx;
	this.text = options.text;
	this.prototype = CControl.prototype;
	this.x = options.x;
	this.y = options.y;
	if(this.x == null) {
		this.x = 0;
	}
	if(this.y == null) {
		this.y = 0;
	}
};
CText.prototype = new CControl();
CText.prototype.helloWorld = function() {
	this.ctx.font = 'bold 144px consolas';
	this.ctx.textAlign = 'left';
	this.ctx.textBaseline = 'top';
	this.ctx.strokeStyle = '#DF5326';
	this.ctx.strokeText('Hello', 50, 50);
	this.ctx.font = 'bold 144px arial';
	this.ctx.fillStyle = 'red';
	this.ctx.fillText('World', 300,300);
};
CText.prototype.draw = function() {
	this.ctx.font = this.options.fontSize + 'pt Georgia';
	this.ctx.textAlign = 'left';
	this.ctx.textBaseline = 'top';
	this.ctx.strokeStyle = '#DF5326';
	this.ctx.fillText(this.text, this.x, this.y);
	
	this.width = this.ctx.measureText(this.text).width;
	this.height = parseInt(this.ctx.font);
	this.ctx.strokeRect(this.x,this.y,this.width,this.height);
};


function CGrid(options) {
	this.headers = options.headers;
	this.fields = options.fields;
	this.initHeader();
}
CTable.prototype = new CControl();
CTable.prototype.initHeader = function() {
	int 
	for (var i = 0; i < this.headers.length; i++) {
		new CText({ctx:this.ctx,id:1,x:100,y:100,fontSize:30,text:'欢迎光临'});
	}
}
