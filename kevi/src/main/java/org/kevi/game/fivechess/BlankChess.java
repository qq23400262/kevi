package org.kevi.game.fivechess;

/**
 * 这个本身应该不存在，我的想法是棋子移除后，就相当往里放一个空的棋子，
 * 这样棋盘不用重新绘画，只要画这个空棋子即可
 * @author 422575
 *
 */
public class BlankChess extends Chess{
	public BlankChess(int gridSize) {
		super("无棋子", gridSize);
	}
	public void paint(Chessboard chessboard) {
		chessboard.gc.setBackground(chessboard.canvas.getBackground());
		chessboard.gc.fillOval(pixelX, pixelY, size, size);
		chessboard.gc.drawLine(pixelX, pixelY+size/2, pixelX+size, pixelY+size/2);
		chessboard.gc.drawLine(pixelX+size/2, pixelY, pixelX+size/2, pixelY+size);
	}
}
