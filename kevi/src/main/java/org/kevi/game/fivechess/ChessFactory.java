package org.kevi.game.fivechess;

/**
 * 学习工厂模式，用工厂模式创建棋子
 * @author 422575
 *
 */
public class ChessFactory {
	public static Chess produceWhiteChess(int size) {
		return new WhiteChess(size);
	}
	public static Chess produceBlackChess(int size) {
		return new BlackChess(size);
	}
	public static Chess produceBlankChess(int size) {
		return new BlankChess(size);
	}
	public static Chess producChess(boolean isTurnBlack, int size) {
		if(isTurnBlack) {
			return produceBlackChess(size);
		}
		return produceWhiteChess(size); 
	}
}
