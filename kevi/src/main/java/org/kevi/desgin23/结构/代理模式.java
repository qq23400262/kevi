package org.kevi.desgin23.结构;

/**
 * 代理模式(Proxy)
 * 其实每个模式名称就表明了该模式的作用，代理模式就是多一个代理类出来，
 * 替原对象进行一些操作，比如我们在租房子的时候回去找中介，为什么呢？
 * 因为你对该地区房屋的信息掌握的不够全面，希望找一个更熟悉的人去帮你做，此处的代理就是这个意思。
 * 再如我们有的时候打官司，我们需要请律师，因为律师在法律方面有专长，
 * 可以替我们进行操作，表达我们的想法
 * @author 422575
 *
 */
public class 代理模式 {
	public static void main(String[] args) {
		Sourceable source = new Proxy();
		//和装饰者很像，区别是Source是调用方不知道的，但装饰者构造时需要提供Source
        source.method();
	}
}

class Proxy implements Sourceable {  
    private Source source;  
    public Proxy(){
        super();  
        this.source = new Source();  
    }  
    @Override
    public void method() {  
        before();
        source.method();  
        atfer();  
    } 
    private void atfer() {  
        System.out.println("after proxy!");  
    }
    private void before() {  
        System.out.println("before proxy!");  
    }  
}  

