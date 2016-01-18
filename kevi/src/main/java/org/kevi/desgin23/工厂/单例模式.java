package org.kevi.desgin23.工厂;
/**
 * 单例模式(Singleton)
 * @author 422575
 *
 */
public class 单例模式 {  
  
    /* 私有构造方法，防止被实例化 */  
    private 单例模式() {  
    }  
  
    /* 此处使用一个内部类来维护单例 */  
    private static class SingletonFactory {  
        private static 单例模式 instance = new 单例模式();  
    }  
  
    /* 获取实例 */  
    public static 单例模式 getInstance() {  
        return SingletonFactory.instance;  
    }  
  
    /* 如果该对象被用于序列化，可以保证对象在序列化前后保持一致 */  
    public Object readResolve() {  
        return getInstance();  
    }  
} 