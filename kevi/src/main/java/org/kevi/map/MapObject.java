package org.kevi.map;

import org.eclipse.swt.graphics.GC;

public class MapObject {
	//地图原点坐标
	int mapWidth;
	int mapHeight;
	int zoom = 0;
	int minZoom=0;
	int maxZoom=5;
	int originX = 0;
	int originY = 0;
	Tiles[][] tilesArray;
	LatLng center;
	Point centerPixel;
	LatLngBounds curBounds;
	public MapObject(int zoom, int mapWidth, int mapHeight, LatLng center) {
		this.zoom = zoom;
		this.mapWidth = mapWidth;
		this.mapHeight = mapHeight;
		this.center = center;
		this.centerPixel = MapUtil.ll2p(this.center, zoom);
		updateCurBounds();
		init();
	}
	public void updateCurBounds() {
		LatLng start = new LatLng(this.center);
		start.move(new Point(-mapWidth/2, -mapHeight/2), zoom);
		LatLng end = new LatLng(this.center);
		end.move(new Point(mapWidth/2, mapHeight/2), zoom);
		if(curBounds == null) {
			curBounds = new LatLngBounds(start, end);
		} else {
			curBounds.setBounds(start, end);
		}
	}
	
	public void setZIndex(int zoom) {
		this.zoom = zoom;
		this.init();
	}
	public void zoomIn() {
		if(this.zoom - 1 < minZoom)return;
		setZIndex(this.zoom - 1);
	}
	public void zoomOut() {
		if(this.zoom + 1 > maxZoom)return;
		setZIndex(this.zoom + 1);
	}
	private void init() {
		initTiles();
	}
	public void updateOrigin() {
		originX = tilesArray[0][0].pixel.x;
		originY = tilesArray[0][0].pixel.y;
	}
	
	public void loop(TilesHandle handle) {
		for (int rIdx = 0; rIdx < tilesArray.length; rIdx++) {
			for (int cIdx = 0; cIdx < tilesArray[rIdx].length; cIdx++) {
				handle.action(tilesArray[rIdx][cIdx], rIdx, cIdx);
			}
		}
	}
	
	public void initTiles() {
		tilesArray = new Tiles[curBounds.getEndY(zoom)-curBounds.getStartY(zoom)][curBounds.getEndX(zoom)-curBounds.getStartX(zoom)];
		System.out.println(tilesArray.length+","+tilesArray[0].length);
		//初始化Tiles
		loop(new TilesHandle() {
			public void action(Tiles t, int rIdx, int cIdx) {
				t = new MyRectangle(rIdx+curBounds.getStartY(zoom), cIdx+curBounds.getStartX(zoom), zoom);
				t.initPositionByArrayIndex(rIdx, cIdx);
				tilesArray[rIdx][cIdx]=t;
			}
		});
	}
	public void drawTiles(GC gc) {
		Point startPx = curBounds.getStartPixel(zoom);
		int xd = startPx.x%MapUtil.TILES_SIZE;
		int yd = startPx.y%MapUtil.TILES_SIZE;
		int x0 = curBounds.getStartX(zoom);
		int x1 = curBounds.getEndX(zoom);
		int y0 = curBounds.getStartY(zoom);
		int y1 = curBounds.getEndY(zoom);
		for (int i = 0; i <= y1 - y0 ; i++) {
			for(int j = 0; j <= x1 - x0; j++) {
				new MyRectangle(j+y0, i+x0, zoom).appendToCanva(gc);
			}
		}
	}
	
	public void move(Point p) {
		this.center.move(p, zoom);
		System.out.println(this.center);
		
	}
}
