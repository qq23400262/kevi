package org.kevi.dataimport;

public class ExcelData {
	Double lat;
	Double lng;
	Double lat1;
	Double lng1;
	
	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}

	public Double getLat1() {
		return lat1;
	}

	public void setLat1(Double lat1) {
		this.lat1 = lat1;
	}

	public Double getLng1() {
		return lng1;
	}

	public void setLng1(Double lng1) {
		this.lng1 = lng1;
	}

	@Override
	public String toString() {
		return "new google.maps.LatLng("+lat1+", "+lng1+")";
	}
	
}
