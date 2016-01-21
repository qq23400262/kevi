package org.kevi.game.fivechess;

import java.util.Random;

public class AIPlayerNo1 implements AIPlayer {
	Chessboard chessboard;
	Random r = new Random();
	Chess testWhiteChess;
	Chess testBlackChess;
	public AIPlayerNo1(Chessboard chessboard) {
		this.chessboard = chessboard;
		testWhiteChess = ChessFactory.produceWhiteChess(chessboard.chessSize);
		testBlackChess = ChessFactory.produceBlackChess(chessboard.chessSize);
	}

	@Override
	public Chess getBeastChess() {
		int score = 0;
		int _score = 0;
		int _score1 = 0;
		int ii = 0;
		int jj = 0;
		for (int i = 0; i < chessboard.size; i++) {
			for (int j = 0; j < chessboard.size; j++) {
				if(chessboard.cbSet.isBlankChess(j, i)) {
					testWhiteChess.setXY(j, i, chessboard.gridSize);
					testBlackChess.setXY(j, i, chessboard.gridSize);
					_score = chessboard.cbSet.evlation(testWhiteChess, true);
					_score1 = -chessboard.cbSet.evlation(testBlackChess, false);
					if(score < _score) {
						ii = i;
						jj = j;
						score = _score;
					} else if(score < _score1) {
						ii = i;
						jj = j;
						score = _score1;
					}
				}
				
			}
		}
		System.out.println("白棋分数:"+score+",黑棋分数:"+_score1);
		Chess chess = ChessFactory.produceWhiteChess(chessboard.chessSize);
		chess.setXY(jj, ii, chessboard.gridSize);
		return chess;
	}
	
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

}
