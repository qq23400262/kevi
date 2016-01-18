package org.kevi.desgin23.结构;

/**
 * 代理模式(Proxy)
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

