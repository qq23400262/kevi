package org.kevi.map;

import org.eclipse.swt.graphics.GC;

public class Tiles {
	Point pixel;
	//tiles size:256*256px
	protected int width = 256;
	protected int height = 256;
	protected String tilesUrl = "D:/gmap/t_ok/0/0/0.png";
	public void appendToCanva(GC gc) {
		gc.drawRectangle(pixel.x, pixel.y, width, height);
	}
	public Tiles(int rIdx, int cIdx, int zIdx){
		pixel = new Point(0, 0);
		tilesUrl = "D:/gmap/t_ok/"+zIdx+"/"+cIdx+"/"+rIdx+".png";
		initPositionByArrayIndex(cIdx, zIdx);
		
	}
	public void move(int x, int y) {
		pixel.x += x;
		pixel.y += y;
	}
	public void move(Point p) {
		move(p.x, p.y);
	}
	public void initPositionByArrayIndex(int rIdx, int cIdx) {
		pixel.x = cIdx * height;
		pixel.y = rIdx * width;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public Point getPixel() {
		return pixel;
	}
	public void setPixel(Point pixel) {
		this.pixel = pixel;
	}
	
	
}
