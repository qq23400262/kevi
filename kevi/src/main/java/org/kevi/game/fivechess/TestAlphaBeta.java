package org.kevi.game.fivechess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
class Node {
	String name;
	int i;
	int j;
	List<Node> childs;
	public Node(int i, int j, String name) {
		this.name = name;
		this.j = j;
		this.i = i;
	}
	public boolean isTerminal() {
		return childs == null;
	}
	public void addChild(Node node) {
		if(childs==null) {
			childs = new ArrayList<Node>();
		}
		childs.add(node);
	}
}

class Node1 {
	int i;
	int j;
	int score;
	List<Node1> childs;
	public Node1(int i, int j, int score) {
		super();
		this.i = i;
		this.j = j;
		this.score = score;
	}
	public Node1 addChild(Node1 node) {
		if(childs==null) {
			childs = new ArrayList<Node1>();
		}
		childs.add(node);
		return this;
	}
	
	public boolean isTerminal() {
		return childs == null;
	}
}

public class TestAlphaBeta {
	//位置重要性价值表,此表从中间向外,越往外价值越低
		static int[][] posValue = {
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
				{0,1,2,2,2,2,2,2,2,2,2,2,2,1,0},
				{0,1,2,3,3,3,3,3,3,3,3,3,2,1,0},
				{0,1,2,3,4,4,4,4,4,4,4,3,2,1,0},
				{0,1,2,3,4,5,5,5,5,5,4,3,2,1,0},
				{0,1,2,3,4,5,6,6,6,5,4,3,2,1,0},
				{0,1,2,3,4,5,6,7,6,5,4,3,2,1,0},
				{0,1,2,3,4,5,6,6,6,5,4,3,2,1,0},
				{0,1,2,3,4,5,5,5,5,5,4,3,2,1,0},
				{0,1,2,3,4,4,4,4,4,4,4,3,2,1,0},
				{0,1,2,3,3,3,3,3,3,3,3,3,2,1,0},
				{0,1,2,2,2,2,2,2,2,2,2,2,2,1,0},
				{0,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
		};
		public static void main(String[] args) {
			test1();
			
		}
		public static void test0() {
			Map<String, String> map = new HashMap<>();
			int len = 15;
//			for (int i = 5; i < len; i++) {
//				for (int j = 5; j < len; j++) {
//					map.put(i+"-"+j,"");
//				}
//			}
			posValue[12][12]=20;
			posValue[12][2]=22;
			posValue[12][5]=21;
			Node node = new Node(6, 6, "node");
			map.put(node.i+"-"+node.j, node.i+"-"+node.j);
			
			for (int i = 0; i < len; i++) {
				for (int j = 0; j < len; j++) {
					if( map.get(i+"-"+j)==null ) {
						node.addChild(new Node(i, j, i+","+j));
					}
				}
			}
			int a = alphaBeta(node, 6, -Integer.MAX_VALUE, Integer.MAX_VALUE, false);
			System.out.println(a);
			

//			Node node1_1 = new Node(0, 0, "node1_1");
//			Node node1_2 = new Node(1, 1, "node1_2");
//			Node node1_3 = new Node(2, 2, "node1_3");
//			Node node1_4 = new Node(3, 3, "node1_3");
//			Node node1_5 = new Node(4, 4, "node1_3");
//			node.addChild(node1_1);
//			node.addChild(node1_2);
//			node.addChild(node1_3);
//			node.addChild(node1_4);
//			node.addChild(node1_5);
//			
//			Node node1_1_1 = new Node(8, 5, "node1_1_1");
//			Node node1_1_2 = new Node(6, 4, "node1_1_2");
//			node1_1.addChild(node1_1_1);
//			node1_1.addChild(node1_1_2);
//			
//			Node node1_2_1 = new Node(6, 2,"node1_2_1");
//			Node node1_2_2 = new Node(9, 9,"node1_2_2");
//			node1_2.addChild(node1_2_1);
//			node1_2.addChild(node1_2_2);
		}
		/**
		 * 
		 * @param node
		 * @param depth
		 * @param α
		 * @param β
		 * @param isTurnBlack false 代表 极大节点 MaxPlayer，因为白色是AI
		 * @return
		 */
		public static int alphaBeta(Node node, int depth, int α, int β, boolean isTurnBlack) { 
		    if (depth == 0 || node.isTerminal()) { // || node is a terminal node 
		        return posValue[node.i][node.j];
			}
		    if (isTurnBlack==false){
		    	//极大节点
		        for (Node child : node.childs) {// 极小节点
		            α = Math.max(α, alphaBeta(child, depth-1, α, β, !isTurnBlack ));   
		            if (β <= α) // 该极大节点的值>=α>=β，该极大节点后面的搜索到的值肯定会大于β，因此不会被其上层的极小节点所选用了。对于根节点，β为正无穷
		                break;
		        }
		        return α;
		    }  else { // 极小节点
		        for (Node child : node.childs) { // 极大节点
		            β = Math.min(β, alphaBeta(child, depth-1, α, β, isTurnBlack)); // 极小节点
		            if (β <= α) // 该极大节点的值<=β<=α，该极小节点后面的搜索到的值肯定会小于α，因此不会被其上层的极大节点所选用了。对于根节点，α为负无穷
		                break;
		        }
		        return β;
		        		
			}
		  //alphabeta(origin, depth, -infinity, +infinity, MaxPlayer)
		}
		
		
		/**
		 * 
		 * @param node
		 * @param depth
		 * @param α
		 * @param β
		 * @param isTurnBlack false 代表 极大节点 MaxPlayer，因为白色是AI
		 * @return
		 */
		public static int alphaBeta1(Node1 node, int depth, int α, int β, boolean isMax) { 
		    if (depth == 0 || node.isTerminal()) { // || node is a terminal node 
		    	System.out.println(isMax+"node.score"+node.score);
		        return node.score;
			}
		    if (isMax==true){
		    	//极大节点
		        for (Node1 child : node.childs) {// 极小节点
		            α = Math.max(α, alphaBeta1(child, depth-1, α, β, true ));   
		            System.out.println("α="+α);
		            if (β <= α) // 该极大节点的值>=α>=β，该极大节点后面的搜索到的值肯定会大于β，因此不会被其上层的极小节点所选用了。对于根节点，β为正无穷
		                break;
		        }
		        return α;
		    }  else { // 极小节点
		        for (Node1 child : node.childs) { // 极大节点
		            β = Math.min(β, alphaBeta1(child, depth-1, α, β, false)); // 极小节点
		            if (β <= α) // 该极大节点的值<=β<=α，该极小节点后面的搜索到的值肯定会小于α，因此不会被其上层的极大节点所选用了。对于根节点，α为负无穷
		                break;
		        }
		        return β;
		        		
			}
		  //alphabeta(origin, depth, -infinity, +infinity, MaxPlayer)
		}
		public static void test1() {
			Node1 node0 = new Node1(0,0,33);
			Node1 node0_1 = new Node1(1,1,10);
			Node1 node0_2 = new Node1(2,2,20);
			Node1 node0_3 = new Node1(3,3,22);
			
			Node1 node0_1_1 = new Node1(4,4,-9);
			Node1 node0_1_2 = new Node1(5,5,-3);
			
			Node1 node0_2_1 = new Node1(6,6,-4);
			Node1 node0_2_2 = new Node1(7,7,-2);
			
			Node1 node0_3_1 = new Node1(8,8,-3);
			Node1 node0_3_2 = new Node1(9,9,-2);
			
			node0.addChild(node0_1).addChild(node0_2).addChild(node0_3);
			
			node0_1.addChild(node0_1_1).addChild(node0_1_2);
			node0_2.addChild(node0_2_1).addChild(node0_2_2);
			node0_3.addChild(node0_3_1).addChild(node0_3_2);
			int score = -Integer.MAX_VALUE;
			int _score = 0;
			int i = -1;
			int j = -1;
			for (Node1 node1 : node0.childs) {
				_score = alphaBeta1(node1, 2, -Integer.MAX_VALUE, Integer.MAX_VALUE, false);
				System.out.println("====="+_score);
				if(score < _score) {
					score = _score;
					i = node1.i;
					j = node1.j;
				}
			}
			System.out.println("下一步应该走：("+i+","+j+"),对方分数="+score);
//			System.out.println(alphaBeta1(node0, 2, -Integer.MAX_VALUE, Integer.MAX_VALUE, true));
		}
}

