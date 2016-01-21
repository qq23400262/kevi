package org.kevi.game.fivechess;

import java.util.Stack;

public class Test {

	public static void main(String[] args) {
		Stack<String> t1 = new Stack<>();
		t1.push("a");
		t1.push("b");
		Stack<String> t2 = (Stack<String>)t1.clone();
		t2.pop();
		System.out.println(t1.size()+","+t2.size());

	}

}
