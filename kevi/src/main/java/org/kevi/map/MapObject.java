package org.kevi.map;

public class MapObject {
	//地图原点坐标
	int mapWidth;
	int mapHeight;
	int minZoom=0;
	int maxZoom=5;
	int originX = 0;
	int originY = 0;
	int zoom = 0;
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
		Point pStart = new Point(centerPixel.x - mapWidth/2, centerPixel.y - mapHeight/2);
		Point pEnd = new Point(centerPixel.x + mapWidth/2, centerPixel.y + mapHeight/2);
		if(curBounds == null) {
			curBounds = new LatLngBounds(MapUtil.p2ll(pStart, zoom), MapUtil.p2ll(pEnd, zoom));
		} else {
			curBounds.setBounds(MapUtil.p2ll(pStart, zoom), MapUtil.p2ll(pEnd, zoom));
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
	public static void main(String[] args) {
	}
	private void initTiles() {
		System.out.println(curBounds.getEndY(zoom)+"==="+curBounds.getStartY(zoom));
		System.out.println(curBounds.getEndX(zoom)+"==="+curBounds.getStartX(zoom));
		tilesArray = new Tiles[curBounds.getEndY(zoom)-curBounds.getStartY(zoom)][curBounds.getEndX(zoom)-curBounds.getStartX(zoom)];
		//初始化Tiles
		loop(new TilesHandle() {
			public void action(Tiles t, int rIdx, int cIdx) {
				t = new MyRectangle(rIdx, cIdx, zoom);
				t.initPositionByArrayIndex(rIdx, cIdx);
				tilesArray[rIdx][cIdx]=t;
			}
		});
	}
	public void move(final int x,final int y) {
		loop(new TilesHandle() {
			public void action(Tiles t, int rIdx, int cIdx) {
				move(x, y);
			}
		});
	}
	public void panTo(LatLng latLng) {
		Point p = MapUtil.ll2p(latLng, zoom);
		move(p.x, p.y);		
	}
}
