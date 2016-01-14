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
		_p.y += p.y;
		_p.x += p.x;
		this.lat = MapUtil.pixelToLat(_p.y, zoom);
		this.lng = MapUtil.pixelToLng(_p.x, zoom);
	}
	public String toString() {
		return "lat="+lat+",lng="+lng;
	}
}
