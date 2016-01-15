package org.kevi.map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MapContext {
	ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:bean.xml");
	private MapHandler handler;

	enum HandlerBean {
		DEFAUL_HANDLER("napShowLatLngHandler"), 
		MAP_SHOW_LATLNG_HANDLER("napShowLatLngHandler"), 
		MAP_MOVE_HANDLER("mapMoveHandler");
		private String name;

		private HandlerBean(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

	public MapContext() {
		changeHandler(HandlerBean.DEFAUL_HANDLER);// 缺省
	}

	public void changeHandler(HandlerBean handlerBean) {
		this.handler = ctx.getBean(handlerBean.name, MapHandler.class);
	}

	public void request(MapHelper mapHelper) {
		// 转调state来处理
		handler.request(mapHelper);
	}
}
