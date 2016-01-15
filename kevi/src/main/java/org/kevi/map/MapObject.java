package org.kevi.map;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Display;

public class MapObject {
	//地图原点坐标
	int mapWidth;
	int mapHeight;
	int bufferSize = 256*2;
	int zoom = 0;
	int minZoom=0;
	int maxZoom=5;
	LatLng center;
	LatLngBounds curBounds;
	public MapObject(int zoom, int mapWidth, int mapHeight, LatLng center) {
		this.zoom = zoom;
		this.mapWidth = mapWidth;
		this.mapHeight = mapHeight;
		this.center = center;
		updateCurBounds();
	}
	
	public void setZIndex(int zoom) {
		this.zoom = zoom;
	}
	public void zoomIn() {
		if(this.zoom - 1 < minZoom)return;
		setZIndex(this.zoom - 1);
	}
	public void zoomOut() {
		if(this.zoom + 1 > maxZoom)return;
		setZIndex(this.zoom + 1);
	}
	
	public void drawTiles(final GC gc) {
		Point startPx = curBounds.getStartPixel(zoom);
		int xd = startPx.x%MapUtil.TILES_SIZE;
		int yd = startPx.y%MapUtil.TILES_SIZE;
		int x0 = curBounds.getStartX(zoom);
		int x1 = curBounds.getEndX(zoom);
		int y0 = curBounds.getStartY(zoom);
		int y1 = curBounds.getEndY(zoom);
		for (int i = 0; i <= y1 - y0 ; i++) {
			for(int j = 0; j <= x1 - x0; j++) {
				final Tiles t = new MyRectangle(j+y0, i+x0, zoom);
				t.setPixel(new Point(i*MapUtil.TILES_SIZE-xd, j*MapUtil.TILES_SIZE-yd));
				t.appendToCanva(gc);
			}
		}
	}
	public synchronized void updateCurBounds() {
		LatLng start = new LatLng(this.center);
		start.move(new Point(-(mapWidth+bufferSize)/2, -(mapHeight+bufferSize)/2), zoom);
		LatLng end = new LatLng(this.center);
		end.move(new Point((mapWidth+bufferSize)/2, (mapHeight+bufferSize)/2), zoom);
		if(curBounds == null) {
			curBounds = new LatLngBounds(start, end);
		} else {
			curBounds.setBounds(start, end);
		}
	}
	public synchronized void move(Point p) {
		this.center.move(p, zoom);
//		System.out.println(this.center);
		updateCurBounds();
	}
}
