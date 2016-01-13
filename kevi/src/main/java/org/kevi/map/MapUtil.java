package org.kevi.map;

public class MapUtil {
	public static int getEndXYByZoom(int z) {
		return (int) Math.pow(2, z) - 1;
	}
	private static double lngToPixel(double lng, int zoom) {
		return (lng + 180) * (256 << zoom) / 360;
	}

	private static double pixelToLng(double pixelX, int zoom) {
		return pixelX * 360 / (256 << zoom) - 180;
	}

	private static double latToPixel(double lat, int zoom) {
		double siny = Math.sin(lat * Math.PI / 180);
		double y = Math.log((1 + siny) / (1 - siny));
		return (128 << zoom) * (1 - y / (2 * Math.PI));
	}

	private static double pixelToLat(double pixelY, int zoom) {
		double y = 2 * Math.PI * (1 - pixelY / (128 << zoom));
		double z = Math.pow(Math.E, y);
		double siny = (z - 1) / (z + 1);
		return Math.asin(siny) * 180 / Math.PI;
	}
	
	public static Point ll2p(LatLng latLng, int zoom) {
		//lng对x,lat对y
		return new Point((int)lngToPixel(latLng.getLng(),zoom),(int)latToPixel(latLng.getLat(),zoom));
	}
	
	public static LatLng p2ll(Point p, int zoom) {
		//lng对x,lat对y
		return new LatLng(pixelToLng(p.x,zoom),pixelToLat(p.y,zoom));
	}
}
