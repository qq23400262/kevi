package org.kevi.desgin23.工厂;

/**
 * 建造者模式(Builder)
 *将一个复杂的构建与其表示相分离，使得同样的构建过程可以创建不同的表示。 [构建与表示分离，同构建不同表示]
 *与抽象工厂的区别：在建造者模式里，有个指导者，由指导者来管理建造者，用户是与指导者联系的，
 * 指导者联系建造者最后得到产品。即建造模式可以强制实行一种分步骤进行的建造过程。
 *建造模式是将复杂的内部创建封装在内部，对于外部调用的人来说，只需要传入建造者和建造工具，
 *对于内部是如何建造成成品的，调用者无需关心。举个简单的例子，如汽车，有很多部件，车轮，方向盘，
 *发动机还有各种小零件等等，部件很多，但远不止这些，如何将这些部件装配成一部汽车，
 *这个装配过程也很复杂（需要很好的组装技术）， builder模式就是为了将部件和组装分开。 
 * @author 422575
 *
 */
public class 建造者模式 {
	public static void main(String[] args) {
		ConcreteBuilder builder = new ConcreteBuilder();
		Director director = new Director(builder);
		director.construct();
		Product product = builder.getResult();
		System.out.println(product);
	}
}
interface Product { }
interface Part { }
interface Builder {
	void buildPartA();

	void buildPartB();

	void buildPartC();

	Product getResult();
}

/**
 * 具体建造工具
 * 
 * @author 422575
 *
 */
class ConcreteBuilder implements Builder {
	Part partA, partB, partC;

	public void buildPartA() {
		// 这里是具体如何构建partA的代码
	};

	public void buildPartB() {
		// 这里是具体如何构建partB的代码
	};

	public void buildPartC() {
		// 这里是具体如何构建partB的代码
	};

	public Product getResult() {
		// 返回最后组装成品结果
		return new Product() {
			//产品实现
		};
	};
}

/**
 * 建造者
 * 
 * @author 422575
 *
 */
class Director {
	private Builder builder;

	public Director(Builder builder) {
		this.builder = builder;
	}

	public void construct() {
		builder.buildPartA();
		builder.buildPartB();
		builder.buildPartC();
	}
}