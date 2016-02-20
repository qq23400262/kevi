function CText(cavas, data) {
	this.data = data;
	this.cavas = cavas;
}

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