package org.kevi.desgin23.结构;

/**
 * 适配器(Adapter)
 * @author 422575
 *
 */
public class 适配器 {
	public static void main(String[] args) {
		//Adapter为类适配，Adapter1为对象适配器
		//建议尽量使用对象适配器的实现方式，多用合成/聚合、少用继承。
		//当然，具体问题具体分析，根据需要来选用实现方式，最适合的才是最好的。
		/**适配器模式的优点
		　　更好的复用性
		　　系统需要使用现有的类，而此类的接口不符合系统的需要。那么通过适配器模式就可以让这些功能得到更好的复用。

		　　更好的扩展性
		　　在实现适配器功能的时候，可以调用自己开发的功能，从而自然地扩展系统的功能。

		适配器模式的缺点
		　　过多的使用适配器，会让系统非常零乱，不易整体进行把握。比如，明明看到调用的是A接口，其实内部被适配成了B接口的实现，
		一个系统如果太多出现这种情况，无异于一场灾难。因此如果不是很有必要，可以不使用适配器，而是直接对系统进行重构。
		 */
		//还有一个缺省适配模式（接口的适配器模式），其实就是定义一个抽象类把一些不用的方法用空方法实现掉，这样子类不必关心一些用不到的方法
	}
}

interface Target {
    /**
     * 这是源类Adaptee也有的方法
     */
    public void sampleOperation1(); 
    /**
     * 这是源类Adapteee没有的方法
     */
    public void sampleOperation2(); 
}

class Adaptee {
    public void sampleOperation1(){}
}

class Adapter extends Adaptee implements Target {
    /**
     * 由于源类Adaptee没有方法sampleOperation2()
     * 因此适配器补充上这个方法
     */
	@Override
    public void sampleOperation2() {
        //写相关的代码
    }
}
class Adapter1 implements Target {
	Adaptee adaptee;
	@Override
	public void sampleOperation1() {
		adaptee.sampleOperation1();
	}
	/**
     * 由于源类Adaptee没有方法sampleOperation2()
     * 因此适配器补充上这个方法
     */
	@Override
    public void sampleOperation2() {
        //写相关的代码
    }
}