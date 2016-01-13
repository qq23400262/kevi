package org.kevi.map;

import java.text.DecimalFormat;

public class MapUtil {
	final static int TILES_SIZE=256;
	static DecimalFormat df0 = new DecimalFormat("0");
	public static int getEndXYByZoom(int z) {
		return (int) Math.pow(2, z) - 1;
	}
	public static double lngToPixel(double lng, int zoom) {
		return (lng + 180) * (256 << zoom) / 360;
	}

	public static double pixelToLng(double pixelX, int zoom) {
		return pixelX * 360 / (256 << zoom) - 180;
	}

	public static double latToPixel(double lat, int zoom) {
		double siny = Math.sin(lat * Math.PI / 180);
		double y = Math.log((1 + siny) / (1 - siny));
		return (128 << zoom) * (1 - y / (2 * Math.PI));
	}

	public static double pixelToLat(double pixelY, int zoom) {
		double y = 2 * Math.PI * (1 - pixelY / (128 << zoom));
		double z = Math.pow(Math.E, y);
		double siny = (z - 1) / (z + 1);
		return Math.asin(siny) * 180 / Math.PI;
	}
	
	public static Point ll2p(LatLng latLng, int zoom) {
		//lng对x,lat对y
		int x = Integer.parseInt(df0.format(lngToPixel(latLng.getLng(),zoom)));
		int y = Integer.parseInt(df0.format(latToPixel(latLng.getLat(),zoom)));
		return new Point(x, y);
	}
	
	public static LatLng p2ll(Point p, int zoom) {
		//lng对x,lat对y
		return new LatLng(pixelToLat(p.y,zoom),pixelToLng(p.x,zoom));
	}
	
}
