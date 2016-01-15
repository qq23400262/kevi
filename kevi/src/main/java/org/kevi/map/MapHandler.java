package org.kevi.map;

import java.util.ArrayList;
import java.util.List;

public abstract class  MapHandler {
	List<MapHandler> handlers = new ArrayList<MapHandler>();
	public void request(MapHelper mapHelper) {
		this.handle(mapHelper);
		for (MapHandler handler : handlers) {
			handler.handle(mapHelper);
		}
	}
	public abstract void handle(MapHelper mapHelper);
	public void setHandlers(List<MapHandler> handlers) {
		this.handlers = handlers;
	}
	
}
