package org.kevi.desgin23.关系模式;

/**
 * 策略模式(strategy)
 * 策略模式定义了一系列算法，并将每个算法封装起来，使他们可以相互替换，
 * 且算法的变化不会影响到使用算法的客户。需要设计一个接口，为一系列实现类提供统一的方法，
 * 多个实现类实现该接口，设计一个抽象类（可有可无，属于辅助类），提供辅助函数
 * 策略模式的决定权在用户，系统本身提供不同算法的实现，新增或者删除算法，对各种算法做封装。
 * 因此，策略模式多用在算法决策系统中，外部用户只需要决定用哪个算法即可
 * 
 * 与模板方法区别：
 * Strategy模式允许外界使用其接口方法,因而可以将这个接口方法认为是"一整个算法";
 * 而Template Method模式可以限制所留下的虚方法只对其继承类可见,外部使用者不一定能够直接使用这些虚方法,
 * 因而可以将这些虚方法认为是"一个算法的一部分".GoF的设计模式那本书里有这么一句话:
 * "Template methods use inheritance to vary part of an algorithm. Strategies use 
 * delegation to vary the entire algorithm.",说的正是这个问题.回到具体问题上,
 * 如果我们要封装的算法适合于提供给用户任意使用,是"一整个算法",那么用Strategy模式较好;
 * 如果要封装的变化是一个算法中的部分(换言之,大算法的步骤是固定的),而且我们不希望用户直接使用这些方法,
 * 那么应该使用Template Method模式.就此,问题的"痛处"算是抓住了.
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