package org.kevi.desgin23.关系模式;

import java.util.Enumeration;
import java.util.Vector;

/**
 * 观察者模式(Observer)
 * 包括这个模式在内的接下来的四个模式，都是类和类之间的关系，不涉及到继承，学的时候应该 记得归纳，
 * 记得本文最开始的那个图。观察者模式很好理解，类似于邮件订阅和RSS订阅，当我们浏览一些博客或wiki时，
 * 经常会看到RSS图标，就这的意思是，当你订阅了该文章，如果后续有更新，会及时通知你。其实，简单来讲就一句话：
 * 当一个对象变化时，其它依赖该对象的对象都会收到通知，并且随着变化！对象之间是一种一对多的关系
 * @author 422575
 *
 */
public class 观察者模式 {

	public static void main(String[] args) {
		Subject sub = new MySubject();  
        sub.add(new Observer1());
        sub.add(new Observer2());
        sub.operation();
	}

}

interface Observer {
	public void update();
}

class Observer1 implements Observer {
	@Override
	public void update() {
		System.out.println("observer1 has received!");
	}
}

class Observer2 implements Observer {
	@Override
	public void update() {
		System.out.println("observer2 has received!");
	}

}

interface Subject {

	/* 增加观察者 */
	public void add(Observer observer);

	/* 删除观察者 */
	public void del(Observer observer);

	/* 通知所有的观察者 */
	public void notifyObservers();

	/* 自身的操作 */
	public void operation();
}

abstract class AbstractSubject implements Subject {

	private Vector<Observer> vector = new Vector<Observer>();
	@Override
	public void add(Observer observer) {
		vector.add(observer);
	}
	@Override
	public void del(Observer observer) {
		vector.remove(observer);
	}
	@Override
	public void notifyObservers() {
		Enumeration<Observer> enumo = vector.elements();
		while (enumo.hasMoreElements()) {
			enumo.nextElement().update();
		}
	}
}

class MySubject extends AbstractSubject {
	@Override
    public void operation() {  
        System.out.println("update self!");  
        notifyObservers();  
    }  
  
}