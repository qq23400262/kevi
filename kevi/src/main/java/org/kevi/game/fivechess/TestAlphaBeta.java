package org.kevi.game.fivechess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}

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
