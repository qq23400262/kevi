package org.kevi.map;

import org.eclipse.swt.graphics.GC;

public class Tiles extends Thread {
	Point pixel;
	//tiles size:256*256px
	protected String tilesUrl = "D:/gmap/t_ok/0/0/0.png";
	GC gc;
	public void appendToCanva(GC gc) {
		//gc.drawRectangle(pixel.x, pixel.y, MapUtil.TILES_SIZE, MapUtil.TILES_SIZE);
		this.gc = gc;
		this.start();
	}
	public Tiles(int rIdx, int cIdx, int zIdx){
		pixel = new Point(0, 0);
		int maxIndex = (int) Math.pow(2, zIdx);
		int z = zIdx;
		int x = cIdx;
		int y = rIdx;
		while (x >= maxIndex) {
			x = x - maxIndex;
		}
		while (y >= maxIndex) {
			y = y - maxIndex;
		}
		while (x < 0) {
			x += maxIndex;
		}
		while (y < 0) {
			y += maxIndex;
		}
		tilesUrl = "D:/gmap/t_ok/"+z+"/"+x+"/"+y+".png";
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
		pixel.x = cIdx * MapUtil.TILES_SIZE;
		pixel.y = rIdx * MapUtil.TILES_SIZE;
	}
	public Point getPixel() {
		return pixel;
	}
	public void setPixel(Point pixel) {
		this.pixel = pixel;
	}
	@Override
	public String toString() {
		return "Tiles [pixel=" + pixel + ", tilesUrl=" + tilesUrl + "]";
	}
	@Override
	public void run() {
		gc.drawRectangle(pixel.x, pixel.y, MapUtil.TILES_SIZE, MapUtil.TILES_SIZE);
	}
}
