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
	
	
}
