package org.kevi.map;

public class LatLng {
	double lat;
	double lng;
	public LatLng(double lat, double lng) {
		this.lat = lat;
		this.lng = lng;
	}
	public LatLng(LatLng latLng) {
		this.lat = latLng.lat;
		this.lng = latLng.lng;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	public void move(Point p,int zoom) {
		Point _p = MapUtil.ll2p(this, zoom);
		if(_p.y + p.y>0)
		_p.y += p.y;
		if(_p.x + p.x>0)
		_p.x += p.x;
		this.lat = MapUtil.pixelToLat(_p.y, zoom);
		this.lng = MapUtil.pixelToLng(_p.x, zoom);
	}
	@Override
	public String toString() {
		return "LatLng [lat=" + lat + ", lng=" + lng + "]";
	}
	
}
