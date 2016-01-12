package org.kevi.map;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;

public class Tiles {
	protected int x = 20;
	protected int y = 20;
	//tiles size:256*256px
	protected int width = 256;
	protected int height = 256;
	public void appendToCanva(GC gc) {
		gc.drawImage(new Image(null, "D:/gmap/t_ok/0/0/0.png"), x, y);
	}
	public Tiles(){
		
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
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
}
