package org.kevi.game.fivechess;

import java.util.HashMap;
import java.util.Map;

public class MapChessboardSet extends ChessboardSet {
	Map<String, Chess> chessMap = new HashMap<String, Chess>();
	static final int winCount = 5;
	private String getKeyByXY(int x, int y) {
		return x + "-" + y;
	}
	@Override
	public void addChess(Chess chess, int x, int y) {
		chessMap.put(getKeyByXY(x,y), chess);
	}

	@Override
	public Chess getChess(int x, int y) {
		return chessMap.get(getKeyByXY(x,y));
	}
	@Override
	public void removeChess(int x, int y) {
		chessMap.remove(getKeyByXY(x,y));
	}
	@Override
	public void removeChess(Chess chess) {
		removeChess(chess.x, chess.y);
	}
	@Override
	public boolean isGameOver(Chess chess, int chessBoardSize) {
		int countLeftUp = countSameChess(chess, -1, -1, chessBoardSize, 0);
		int countRightDown = countSameChess(chess, 1, 1, chessBoardSize, 0);
		if(countLeftUp + countRightDown + 1 >= winCount) {
			return true;
		}
		int countRightUp = countSameChess(chess, 1, -1, chessBoardSize, 0);
		int countLeftDown = countSameChess(chess, -1, 1, chessBoardSize, 0);
		if(countRightUp + countLeftDown + 1 >= winCount) {
			return true;
		}
		int countLeft = countSameChess(chess, -1, 0, chessBoardSize, 0);
		int countRight = countSameChess(chess, 1, 0, chessBoardSize, 0);
		if(countLeft + countRight + 1 >= winCount) {
			return true;
		}
		int countUp = countSameChess(chess, 0, -1, chessBoardSize, 0);
		int countDown = countSameChess(chess, 0, 1, chessBoardSize, 0);
		if(countUp + countDown + 1 >= winCount) {
			return true;
		}
		return false;
	}
	
	/**
	 * 向某个方向统计相同颜色的棋子
	 * @param chess
	 * @param offsetX -1,0,1
	 * @param offsetY -1,0,1
	 * @param chessBoardSize
	 * @param result
	 * @return
	 */
	public int countSameChess(Chess chess, int offsetX, int offsetY, int chessBoardSize, int result) {
		int _x = chess.x + offsetX;
		if(_x<0 || _x >= chessBoardSize) {
			return result;
		}
		int _y = chess.y + offsetY;
		if(_y<0 || _y >= chessBoardSize) {
			return result;
		}
		Chess _chess = getChess(_x, _y);
		if(_chess == null) {
			return result;
		} else if(!_chess.equals(chess)) {
			return result;
		}
		result++;
		return countSameChess(_chess, offsetX, offsetY,chessBoardSize, result);
	}

}
