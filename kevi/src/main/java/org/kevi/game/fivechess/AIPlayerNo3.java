package org.kevi.game.fivechess;

import java.util.Random;

public class AIPlayerNo3 implements AIPlayer {
	Chessboard chessboard;
	Random r = new Random();
	Chess testWhiteChess;
	Chess testBlackChess;
	ChessboardSet cs;
	public AIPlayerNo3(Chessboard chessboard) {
		this.chessboard = chessboard;
		testWhiteChess = ChessFactory.produceWhiteChess(chessboard.chessSize);
		testBlackChess = ChessFactory.produceBlackChess(chessboard.chessSize);
	}
	
	/**
	 * 评价棋盘分数
	 * @param node
	 * @return
	 */
	public int evaluate(Chess chess, boolean isAI) {
		//return isAI?cs.posValue[chess.y][chess.x]:-cs.posValue[chess.y][chess.x];
		return cs.evlation(chess, isAI);
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
	public int getMaxMin(Chess chess, int depth, int α, int β, boolean isAI) { 
	    if (depth <= 0) { // || node is a terminal node 
	        return evaluate(chess, isAI);
		}
		Chess _chess;
	    if (isAI){
	    	//极大节点
			for (int i = 0; i <= chessboard.size; i++) {
				for (int j = 0; j <= chessboard.size; j++) {
					if(cs.isBlankChess(j, i)) {
						_chess = ChessFactory.produceWhiteChess(chessboard.chessSize);
						_chess.setXY(j, i, chessboard.gridSize);
						α = Math.max(α, getMaxMin(_chess, depth-1, α, β, isAI));
						cs.removeChess(_chess);
						if (α >= β) // 该极大节点的值>=α>=β，该极大节点后面的搜索到的值肯定会大于β，因此不会被其上层的极小节点所选用了。对于根节点，β为正无穷
			                break;
					}
				}
			}
	        return α;
	    }  else { // 极小节点
	    	for (int i = 0; i <= chessboard.size; i++) {
				for (int j = 0; j <= chessboard.size; j++) {
					if(cs.isBlankChess(j, i)) {
						_chess = ChessFactory.produceBlackChess(chessboard.chessSize);
						_chess.setXY(j, i, chessboard.gridSize);
						β = Math.min(α, getMaxMin(_chess, depth-1, α, β, !isAI));
						cs.removeChess(_chess);
						if (β <= α) // 该极大节点的值>=α>=β，该极大节点后面的搜索到的值肯定会大于β，因此不会被其上层的极小节点所选用了。对于根节点，β为正无穷
			                break;
					}
				}
			}
	        return β;
	        		
		}
	}
	
	@Override
	public Chess getBeastChess() {
		cs = chessboard.cbSet.clone();
		int maxScore = 0;
		int _maxScore = 0;
		Chess _chess;
		int ii = 0;
		int jj = 0;
		for (int i = 0; i <= chessboard.size; i++) {
			for (int j = 0; j <= chessboard.size; j++) {
				if(cs.isBlankChess(j, i)) {
					_chess = ChessFactory.produceWhiteChess(chessboard.chessSize);
					_chess.setXY(j, i, chessboard.gridSize);
					cs.addChess(_chess);
					_maxScore = getMaxMin(_chess, 1, -Integer.MAX_VALUE, Integer.MAX_VALUE, true);
					cs.removeChess(_chess);
					if(maxScore<_maxScore) {
						ii = i;
						jj = j;
						maxScore = _maxScore;
						System.out.println(maxScore +"-"+ _maxScore+"=="+ii+","+jj);
					}
				}
				
			}
		}
		System.out.println("getBeastChess1============="+maxScore+","+ii+","+jj);
		Chess chess = ChessFactory.produceWhiteChess(chessboard.chessSize);
		chess.setXY(jj, ii, chessboard.gridSize);
		return chess;
	}

}
