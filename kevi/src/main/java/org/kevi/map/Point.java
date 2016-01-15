package org.kevi.map;

public class Point {
	int x;
	int y;
	public Point(int x, int y) {
		super();
		this.x = x;
		this.y = y;
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
	
	public Point setPoint(Point p) {
		this.x = p.x;
		this.y = p.y;
		return this;
	}
	public Point subPoint(Point p) {
		this.x -= p.x;
		this.y -= p.y;
		return this;
	}
	public Point addPoint(Point p) {
		this.x += p.x;
		this.y += p.y;
		return this;
	}
	
	@Override
	public String toString() {
		return "Point [x=" + x + ", y=" + y + "]";
	}
	public boolean equals(Point p) {
		return p.x == this.x && p.y == this.y; 
	}
	
}
