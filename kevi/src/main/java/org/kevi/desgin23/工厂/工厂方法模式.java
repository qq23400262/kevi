package org.kevi.desgin23.工厂;

/**
 * 工厂方法模式（Factory Method）
 * 简单工厂模式有一个问题就是，类的创建依赖工厂类，也就是说，如果想要拓展程序，必须对工厂类进行修改，
 * 这违背了闭包原则，所以，从设计角度考虑，有一定的问题，如何解决？就用到工厂方法模式，
 * 创建一个工厂接口和创建多个工厂实现类，
 * 这样一旦需要增加新的功能，直接增加新的工厂类就可以了，不需要修改之前的代码。
 * 其实这个模式的好处就是，如果你现在想增加一个功能：发及时信息，则只需做一个实现类，实现Sender接口，
 * 同时做一个工厂类，实现Provider接口，就OK了，无需去改动现成的代码。这样做，拓展性较好！
 * 
 * 工厂方法模式只有一个抽象产品类，而抽象工厂模式有多个。   
工厂方法模式的具体工厂类只能创建一个具体产品类的实例，而抽象工厂模式可以创建多个。
 * @author 422575
 *
 */
public class 工厂方法模式 {

	public static void main(String[] args) {
		Provider provider = new SendMailFactory();
		Sender sender = provider.produce();
		sender.Send();
	}

}

interface Sender {
	public void Send();
}

class MailSender implements Sender {
	@Override
	public void Send() {
		System.out.println("this is mailsender!");
	}
}

class SmsSender implements Sender {

	@Override
	public void Send() {
		System.out.println("this is sms sender!");
	}
}

class SendMailFactory implements Provider {

	@Override
	public Sender produce() {
		return new MailSender();
	}
}

class SendSmsFactory implements Provider {

	@Override
	public Sender produce() {
		return new SmsSender();
	}
}

interface Provider {
	public Sender produce();
}