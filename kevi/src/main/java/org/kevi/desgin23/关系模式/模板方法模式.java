package org.kevi.desgin23.关系模式;

/**
 * 模板方法模式(Template Method)
 * 解释一下模板方法模式，就是指：一个抽象类中，有一个主方法，再定义1...n个方法，可以是抽象的，
 * 也可以是实际的方法，定义一个类，继承该抽象类，重写抽象方法，通过调用抽象类，实现对子类的调用
 * @author 422575
 *
 */
public class 模板方法模式 {

	public static void main(String[] args) {
		String exp = "8+8";
		AbstractCalculator1 cal = new Plus1();
		int result = cal.calculate(exp, "\\+");
		System.out.println(result);

	}

}

abstract class AbstractCalculator1 {

	/* 主方法，实现对本类其它方法的调用 */
	public final int calculate(String exp, String opt) {
		int array[] = split(exp, opt);
		return calculate(array[0], array[1]);
	}

	/* 被子类重写的方法 */
	abstract public int calculate(int num1, int num2);

	public int[] split(String exp, String opt) {
		String array[] = exp.split(opt);
		int arrayInt[] = new int[2];
		arrayInt[0] = Integer.parseInt(array[0]);
		arrayInt[1] = Integer.parseInt(array[1]);
		return arrayInt;
	}
}

class Plus1 extends AbstractCalculator1 {

	@Override
	public int calculate(int num1, int num2) {
		return num1 + num2;
	}
}