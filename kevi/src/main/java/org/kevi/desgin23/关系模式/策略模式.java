package org.kevi.desgin23.关系模式;

/**
 * 策略模式(strategy)
 * 
 * @author 422575
 *
 */
public class 策略模式 {

	public static void main(String[] args) {
		String exp = "2+8";  
        ICalculator cal = new Plus();  
        int result = cal.calculate(exp);
        System.out.println(result);
	}

}

interface ICalculator {
	public int calculate(String exp);
}

/**
 * 设计一个抽象类（可有可无，属于辅助类）
 * @author 422575
 *
 */
abstract class AbstractCalculator {
	public int[] split(String exp, String opt) {
		String array[] = exp.split(opt);
		int arrayInt[] = new int[2];
		arrayInt[0] = Integer.parseInt(array[0]);
		arrayInt[1] = Integer.parseInt(array[1]);
		return arrayInt;
	}
}
class Plus extends AbstractCalculator implements ICalculator {  
	@Override
    public int calculate(String exp) {  
        int arrayInt[] = split(exp,"\\+");  
        return arrayInt[0]+arrayInt[1];  
    }  
}
class Minus extends AbstractCalculator implements ICalculator {  
	@Override
    public int calculate(String exp) {
        int arrayInt[] = split(exp,"-");
        return arrayInt[0]-arrayInt[1];  
    }  
}

class Multiply extends AbstractCalculator implements ICalculator { 
	@Override
    public int calculate(String exp) {  
        int arrayInt[] = split(exp,"\\*");  
        return arrayInt[0]*arrayInt[1];  
    }  
} 