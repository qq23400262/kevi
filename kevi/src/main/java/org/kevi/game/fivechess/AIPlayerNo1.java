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
					if(_score == 100000) {
						//自己赢了
						System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
						ii = i;
						jj = j;
						score = _score;
						break;
					} else if(_score1==100000 && score < _score1) {
						//对方要赢了必须守
						ii = i;
						jj = j;
						score = _score1;
					} else if(_score == 10000) {
						//优先自己的双四必赢步
						ii = i;
						jj = j;
						score = _score;
						//这里不能break因为怕后面的赢了或必输的情况，所以要把循环跑完
					} else if(_score1 == 10000 && score < _score1) {
						//如果我方还没遇到双四，这里就必需要守了，但后面如果遇到我方有双四，我方会先下
						ii = i;
						jj = j;
						score = _score1;
					} else if(_score == 5000 && score <= _score) {
						ii = i;
						jj = j;
						score = _score1;
					} else if(_score1 == 5000 && _score1 > score) {
						ii = i;
						jj = j;
						score = _score1;
					} else if(_score == 1000 && score <= _score) {
						ii = i;
						jj = j;
						score = _score;
					} else if(_score1 == 1000 && _score1 > score) {
						ii = i;
						jj = j;
						score = _score1;
					} else if(_score == 500 && score <= _score) {
						ii = i;
						jj = j;
						score = _score;
					} else if(_score1 == 500 && _score1 > score) {
						ii = i;
						jj = j;
						score = _score1;
					} else if(_score == 200 && score <= _score) {
						ii = i;
						jj = j;
						score = _score;
					} else if(_score1 == 200 && _score1 > score) {
						ii = i;
						jj = j;
						score = _score1;
					} else if(_score == 100 && score <= _score) {
						ii = i;
						jj = j;
						score = _score;
					} else if(_score1 == 100 && _score1 > score) {
						ii = i;
						jj = j;
						score = _score1;
					} else if(_score == 60 && score <= _score) {
						ii = i;
						jj = j;
						score = _score;
					} else if(_score1 == 60 && _score1 > score) {
						ii = i;
						jj = j;
						score = _score1;
					} else if(_score == 30 && score <= _score) {
						ii = i;
						jj = j;
						score = _score;
					} else if(_score1 == 30 && _score1 > score) {
						ii = i;
						jj = j;
						score = _score1;
					} else if(_score == 20 && score <= _score) {
						ii = i;
						jj = j;
						score = _score;
					} else if(_score1 == 20 && _score1 > score) {
						ii = i;
						jj = j;
						score = _score1;
					} else if(_score == 10 && score <= _score) {
						ii = i;
						jj = j;
						score = _score;
					} else if(_score1 == 10 && _score1 > score) {
						ii = i;
						jj = j;
						score = _score1;
					} else if(score < _score) {
						ii = i;
						jj = j;
						score = _score;
					}
				}
			}
			if(score==100000) {
				break;
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
