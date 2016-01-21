package org.kevi.game.fivechess;

public class Test1 {
	static boolean f(int x) {
		int[] op = {1,3,7,8};
		for (int i : op) {
			if(x>=i) {
				if(f(x-i)==false)return true;//对方必输
			}
		}
		return false;//无论怎么走棋，对方都必赢
	}
	
	public static void main(String[] args) {
		//有若干球，每次要拿走1，3，7，8，如果轮到拿不了这些数就输
		System.out.println(f(5-1));
		System.out.println(f(5-3));//都是输，所以不能剩余5个
	}
}
