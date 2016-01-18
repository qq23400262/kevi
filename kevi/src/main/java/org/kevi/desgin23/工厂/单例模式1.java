package org.kevi.desgin23.工厂;
/**
 * 单例模式(Singleton)
 * @author 422575
 *
 */
public class 单例模式1 {  
  
    private static 单例模式1 instance = null;  
  
    private 单例模式1() {  
    }  
  
    private static synchronized void syncInit() {  
        if (instance == null) {
            instance = new 单例模式1();  
        }  
    }  
  
    public static 单例模式1 getInstance() {  
        if (instance == null) {  
            syncInit();  
        }  
        return instance;  
    }  
}  