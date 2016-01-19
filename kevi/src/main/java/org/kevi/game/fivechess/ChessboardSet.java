package org.kevi.game.fivechess;

/**
 * 原来数组、list、map不知道用哪个，百度一下说数组效率会好一点 后面想想，不如先不定死，
 * 让子类去实现，也就是说可以多种实现方法
 * 
 * @author 422575
 *
 */
public abstract class ChessboardSet {
	public abstract void addChess(Chess chess, int x, int y);
	public abstract Chess getChess(int x, int y);
	public abstract void removeChess(int x, int y);
	public abstract void removeChess(Chess chess);
	/**
	 * 根据最后一个子判断游戏是否结束
	 * @param chess
	 */
	public abstract boolean isGameOver(Chess chess, int chessBoardSize);
	public boolean isBlankChess(int x, int y) {
		return getChess(x, y) == null;
	}
	
	public abstract int countSameChess(Chess chess, int offsetX, int offsetY, int chessBoardSize, int result);
}
