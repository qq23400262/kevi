package org.kevi.game.fivechess;

import java.util.Random;

public class AIPlayerNo2 implements AIPlayer {
	Chessboard chessboard;
	Random r = new Random();
	Chess testWhiteChess;
	Chess testBlackChess;
	ChessboardSet cs;
	public AIPlayerNo2(Chessboard chessboard) {
		this.chessboard = chessboard;
		testWhiteChess = ChessFactory.produceWhiteChess(chessboard.chessSize);
		testBlackChess = ChessFactory.produceBlackChess(chessboard.chessSize);
	}
	
	/**
	 * 获取AI方最优走法
	 * @param depth
	 * @return
	 */
	public int getMax(Chess chess, int depth, int curMax){
		if (depth <= 0) {
			return evaluate(chess, true);
		} 
		int best = -Integer.MAX_VALUE;//取最大值为best，但从负无穷开始
		int val=0;
		Chess _chess;
		for (int i = cs.y0; i <= cs.y1; i++) {
			for (int j = cs.x0; j <= cs.x1; j++) {
				if (cs.isBlankChess(j, i)) {
					_chess = ChessFactory.produceWhiteChess(chessboard.chessSize);
					_chess.setXY(j, i, chessboard.gridSize);
					cs.addChess(_chess);
					val = getMin(_chess, depth - 1, -Integer.MAX_VALUE);
					cs.removeChess(_chess);
					// 剪枝优化
					if (val >= curMax) {
						// 添加 =
						// 以后的提升程序的效率!原因：在象棋博弈的时候，很少有吃棋的动作，基本上都是走棋！所以局面分相等的情况很多！
						return val; // 当后面分支出现较大值时（必然不可能走），直接return！后面的分支不用再计算，提高速度！
					}
					cs.removeChess(_chess);
					if (val > best) {
						best = val;
					}
				}
			}
		}
		return best;
	}
	
	/**
	 * 获取人类(黑棋)最优走法
	 * @param depth
	 * @return
	 */
	public int getMin(Chess chess, int depth, int curMin){
		int best = Integer.MAX_VALUE;//取最小值为best，但从正无穷开始
		int val=0;
		if (depth <= 0) {
			int a = evaluate(chess, false);
			if(a >0) {
				System.out.println(a+"==========");
			}
			return a ;
		}
		Chess _chess;
		for (int i = cs.y0; i <= cs.y1; i++) {
			for (int j = cs.x0; j <= cs.x1; j++) {
				if(cs.isBlankChess(j, i)) {
					_chess = ChessFactory.produceBlackChess(chessboard.chessSize);
					_chess.setXY(j, i, chessboard.gridSize);
					cs.addChess(_chess);
					val = getMax(_chess, depth - 1, Integer.MAX_VALUE);
					cs.removeChess(_chess);
					 //剪枝优化  
			        if(val <= curMin) {    
			        	//添加 = 以后的提升程序的效率！原因：在象棋博弈的时候，很少有吃棋的动作，基本上都是走棋！所以局面分相等的情况很多！  
			            return val;//当在下层后面分支（分支的分支）出现更小的值时（必然不可能走），直接return！后面的分支不用再计算，提高速度！  
			        }
					cs.removeChess(_chess);
					if (val < best) { // 注意这里不同于“最大”算法
						best = val;
					}
				}
			}
		}
		return best;
	}
	
	public Chess getBeastChess1(ChessboardSet cs) {
		int totalScore = 0;
		int _score = 0;
		int _score1 = 0;
		int ii = 0;
		int jj = 0;
		for (int i = cs.y0; i <= cs.y1; i++) {
			for (int j = cs.x0; j <= cs.x1; j++) {
				if(chessboard.cbSet.isBlankChess(j, i)) {
					testWhiteChess.setXY(j, i, chessboard.gridSize);
					testBlackChess.setXY(j, i, chessboard.gridSize);
					_score = chessboard.cbSet.evlation(testWhiteChess, true);
					_score1 = chessboard.cbSet.evlation(testBlackChess, false);
					if(_score>=100000) {
						//胜了
						ii = i;
						jj = j;
						totalScore = _score;
						break;
					} else if(_score1<=-5000) {
						//活四必须要守
						ii = i;
						jj = j;
						totalScore = Math.abs(_score+_score1);
					} else if(totalScore < Math.abs(_score+_score1)) {
						ii = i;
						jj = j;
						totalScore = Math.abs(_score+_score1);
					} else if(_score+_score1==0&&totalScore<_score){
						ii = i;
						jj = j;
						totalScore = _score;
					}
				}
				
			}
		}
//		testWhiteChess.setXY(jj, ii, chessboard.gridSize);
//		testBlackChess.setXY(jj, ii, chessboard.gridSize);
//		_score = chessboard.cbSet.evlation(testWhiteChess, true);
//		_score1 = chessboard.cbSet.evlation(testBlackChess, false);
//		System.out.println("白棋分数:"+_score+",黑棋分数:"+_score1);
		Chess chess = ChessFactory.produceWhiteChess(chessboard.chessSize);
		chess.setXY(jj, ii, chessboard.gridSize);
		return chess;
	}

	@Override
	public Chess getBeastChess() {
		cs = chessboard.cbSet.clone();
		System.out.println(cs);
		int a = getMax(null, 2, Integer.MAX_VALUE);
		Chess c = cs.getLastChess();
		if(c == null) {
			System.out.println("getBeastChess1============="+a);
			c = getBeastChess1(chessboard.cbSet);
		}
		return c;
	}
	
	/**
	 * 评价棋盘分数
	 * @param node
	 * @return
	 */
	public int evaluate(Chess chess, boolean isAI) {
		int score = cs.evlation(chess, isAI);
		if(score==0) {
			System.out.println("------------"+score);
		}
		return score;
	}

}
