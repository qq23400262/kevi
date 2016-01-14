package org.kevi.map;

public class MapContext {
	private MapHandler handler;
	public void setHandler(MapHandler handler) {
		this.handler = handler;
	}
	
	public void request(MapHelper mapHelper) {
        //转调state来处理
		handler.handle(mapHelper);
    }
}
