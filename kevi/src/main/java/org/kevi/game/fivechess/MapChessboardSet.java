package org.kevi.game.fivechess;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class MapChessboardSet extends ChessboardSet {
	Map<String, Chess> chessMap = new HashMap<String, Chess>();
	Stack<String> stepStack = new Stack<>();
	private String getKeyByXY(int x, int y) {
		return x + "-" + y;
	}
	@Override
	public void subAddChess(Chess chess) {
		String key = getKeyByXY(chess.x,chess.y);
		chessMap.put(key, chess);
		stepStack.push(key);
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
	@SuppressWarnings("unchecked")
	@Override
	public ChessboardSet clone() {
		MapChessboardSet newSet = new MapChessboardSet();
		newSet.stepStack = (Stack<String>)this.stepStack.clone();
		newSet.x0 = x0;
		newSet.x1 = x1;
		newSet.y0 = y0;
		newSet.y1 = y1;
		for (int i = 0; i < chessBoardSize; i++) {
			for (int j = 0; j < chessBoardSize; j++) {
				if(!isBlankChess(j, i)) {
					newSet.addChess(getChess(j, i).clone());
				}
			}
		}
		return newSet;
	}
	@Override
	public Chess getLastChess() {
		if(stepStack.isEmpty()) return null;
		String key = stepStack.pop();
		return chessMap.get(key);
	}

}
