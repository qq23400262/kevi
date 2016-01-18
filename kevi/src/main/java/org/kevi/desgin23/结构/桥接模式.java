package org.kevi.desgin23.结构;

public class 桥接模式 {

	public static void main(String[] args) {
		Bridge bridge = new MyBridge();

		/* 调用第一个对象 */
		Sourceable source1 = new SourceSub1();
		bridge.setSource(source1);
		bridge.method();

		/* 调用第二个对象 */
		Sourceable source2 = new SourceSub2();
		bridge.setSource(source2);
		bridge.method();

	}

}

class SourceSub1 implements Sourceable {
	@Override
	public void method() {
		System.out.println("this is the first sub!");
	}
}

class SourceSub2 implements Sourceable {
	@Override
	public void method() {
		System.out.println("this is the second sub!");
	}
}

abstract class Bridge {
	private Sourceable source;
	public void method() {
		source.method();
	}

	public Sourceable getSource() {
		return source;
	}

	public void setSource(Sourceable source) {
		this.source = source;
	}
}

class MyBridge extends Bridge {
	/**
	 * 重写method可以实现其它逻辑
	 */
//	@Override
//	public void method() {
//		getSource().method();
//	}
}