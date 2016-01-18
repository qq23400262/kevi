package org.kevi.desgin23.结构;

/**
 * 装饰模式(Decorator)
 * 
 * @author 422575
 *
 */
public class 装饰模式 {
	public static void main(String[] args) {
		Sourceable source = new Source();
		Sourceable obj = new Decorator(source);
		obj.method();
	}
}

class Decorator implements Sourceable {
	private Sourceable source;
	public Decorator(Sourceable source) {
		super();
		this.source = source;
	}
	@Override
	public void method() {
		System.out.println("before decorator!");
		source.method();
		System.out.println("after decorator!");
	}
}