/**
 * 打算把这个Canvas打造成一个实现系统的Canvas
 */
function Canvas(options) {
	this.listeners = [];
	this.createCanvas(options);
	//this.canvas = document.getElementById('canvas').getContext('2d');
	this.canvas = $('#'+options.id);
	this.ctx = this.canvas[0].getContext('2d');
	this.initEventListener();
	this.initControls();
	this.resizeCanvas();
};

Canvas.prototype.initControls = function() {
	var text1 = new CText({ctx:this.ctx,id:1,x:100,y:100,fontSize:30,text:'欢迎光临'});
	text1.mousemove = function(e){console.info('欢迎光临mousemove')};
	this.addEventListener('mousemove',text1, true);
	
	var text2 = new CText({ctx:this.ctx,id:1,x:300,y:300,fontSize:30,text:'请点击我'});
	text2.click = function(e){alert('请点击我'+e.x+","+e.y)};
	text2.mouseover = function(e){console.info('mouseover')};
	text2.mouseout = function(e){console.info('mouseout')};
	this.addEventListener('click',text2,true);
	this.addEventListener('mouseover',text2, true);
	
	this.controls = [];
	this.controls.push(text1);
	this.controls.push(text2);
};

Canvas.prototype.createCanvas = function(options) {
	var canvas = $('<canvas></canvas>');
	canvas.attr('id', options.id);
	//canvas.attr('width', 1224);
	//canvas.attr('height', 768);
	console.info(canvas);
	console.info(canvas[0].outerHTML);
	
	//this.canvas.style.zIndex = 8;
	//this.canvas.style.position = "absolute";
	//this.canvas.style.border = "1px solid";
	
	$('body').append(canvas[0].outerHTML);
	//this.document.body.appendChild(canvas);
};

Canvas.prototype.drawMenu = function() {
	for(var i = 0; i < this.controls.length; i++) {
		this.controls[i].draw();
	}
};

Canvas.prototype.resizeCanvas = function() {
	this.canvas.attr("width", $(window).get(0).innerWidth);
	this.canvas.attr("height", $(window).get(0).innerHeight);
	this.drawMenu();
}

Canvas.prototype.initEventListener = function() {
	var _this = this;
	//添加事件响应   
    this.canvas[0].addEventListener('click', function(e){
        _this.click(e);
    }, false);
    //添加事件响应
    this.canvas[0].addEventListener('mousemove', function(e){
        _this.mousemove(e);
    }, false);
}

Canvas.prototype.click = function(e) {
	//p = getEventPosition(e);
	this.execListeners('click', e);
}
Canvas.prototype.mousemove = function(e) {
	//p = getEventPosition(e);
	this.execListeners('mousemove', e);
	this.execListeners('mouseover', e);
}

Canvas.prototype.execListeners = function(type, e) {
	if(this.listeners[type] == null)return;
	//console.info(type);
	for(var i = 0; i < this.listeners[type].length; i++) {
		if(this.listeners[type][i].control.containPoint(e.x, e.y)) {
			if(type != 'mouseover') {
				eval('this.listeners[type][i].control.'+type+'(e);');
				break;
			}
			var isMousein = this.listeners[type][i].control.isMousein;
			if(type == 'mouseover' && (isMousein == null || isMousein == false)) {
				this.listeners[type][i].control.isMousein = true;
				if(this.listeners[type][i].control.mouseover) {
					this.listeners[type][i].control.mouseover(e);
				}
			}
		} else {
			var isMousein = this.listeners[type][i].control.isMousein;
			if(isMousein == true) {
				this.listeners[type][i].control.isMousein = false;
				if(this.listeners[type][i].control.mouseout) {
					this.listeners[type][i].control.mouseout(e);
				}
			}
		}
	}
}


/**
 * @param control 要监听的对像
 * @param type 字符串，事件名称，如click,dbclick,mousemove,mouseover,mouseout
 * @param listener     事件处理的函数，实现EventListener接口
 * @param useCapture   是否使用捕获--- true为捕获，false为冒泡
 */
Canvas.prototype.addEventListener = function(type,control,listener, useCapture) {
	console.info(this.listeners);
	if(this.listeners[type]==null) {
		this.listeners[type] = [];
	}
	this.listeners[type].push({control:control,type:type,listener:listener, useCapture:useCapture});
};
