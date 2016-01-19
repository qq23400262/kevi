package org.kevi.game.fivechess;

import java.util.Random;

public class AIPlayerNo1 implements AIPlayer {
	Chessboard chessboard;
	Random r = new Random();
	public AIPlayerNo1(Chessboard chessboard) {
		this.chessboard = chessboard;
	}
	//位置重要性价值表,此表从中间向外,越往外价值越低
	int[][] posValue = {
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
	@Override
	public void putChess() {
		int step = chessboard.stepStack.size();
		if(step==1) {
			putByPosValue();
			return;
		}
		int allStep = chessboard.size * chessboard.size;
		int leave = allStep - step;
		int index = new Random().nextInt(leave);
		for (int i = 0; i < chessboard.size; i++) {
			for (int j = 0; j < chessboard.size; j++) {
				index--;
				if(index==0) {
					chessboard.putChess(j, i);
				}
			}
		}
		//chessboard.
	}
	
//	public static void main(String[] args) {
//		for (int i = 0; i < 10; i++) {
//			System.out.println(new Random().nextInt(3)-1);
//		}
//	}
	public void putByPosValue() {
		int x = 7;
		int y = 7;
		//第一步
		if(chessboard.cbSet.isBlankChess(x, y)) {
			chessboard.putChess(x, y);
			return;
		}
		int _x = new Random().nextInt(3)-1;
		int _y = new Random().nextInt(3)-1;
		while(_x == 0 && _y == 0) {
			_x = new Random().nextInt(3)-1;
			_y = new Random().nextInt(3)-1;
		}
		chessboard.putChess(x+_x, y+_x);
	}
	
	public int eveluate() {
		return 0;
	}
	
//	public boolean isTow(int x, int y) {
//		//注：x对j,y对i
//		for (int i = 0; i < chessboard.size; i++) {
//			for (int j = 0; j < chessboard.size; j++) {
//				chessboard.cbSet.countSameChess(chess, offsetX, offsetY, chessBoardSize, result);
//			}
//		}
//	}

}
