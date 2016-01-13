package org.kevi.map;

public class LatLngBounds {
	LatLng start;
	LatLng end;
	public LatLngBounds(LatLng start, LatLng end) {
		super();
		this.start = start;
		this.end = end;
	}
	
	public void setBounds(LatLng start, LatLng end) {
		this.start = start;
		this.end = end;
	}

	public LatLng getStart() {
		return start;
	}

	public void setStart(LatLng start) {
		this.start = start;
	}

	public LatLng getEnd() {
		return end;
	}

	public void setEnd(LatLng end) {
		this.end = end;
	}
	
	public int getStartX(int zoom) {
		System.out.println(MapUtil.lngToPixel(start.lng, zoom)+"startX");
		return (int)(MapUtil.lngToPixel(start.lng, zoom)/MapUtil.TILES_SIZE)-1;
	}
	public int getStartY(int zoom) {
		System.out.println(MapUtil.latToPixel(start.lat, zoom)+"startY");
		return (int)(MapUtil.latToPixel(start.lat, zoom)/MapUtil.TILES_SIZE)-1;
	}
	public int getEndX(int zoom) {
		System.out.print(MapUtil.lngToPixel(end.lng, zoom)+"endX");
		return (int)(MapUtil.lngToPixel(end.lng, zoom)/MapUtil.TILES_SIZE)+1;
	}
	public int getEndY(int zoom) {
		System.out.println(MapUtil.latToPixel(end.lat, zoom)+"endY");
		return (int)(MapUtil.latToPixel(end.lat, zoom)/MapUtil.TILES_SIZE)+1;
	}
	
}
