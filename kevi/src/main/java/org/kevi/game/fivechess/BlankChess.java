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
		chessboard.gc.setLineWidth(1);
		int x0 = pixelX;
		int y0 = pixelY+size/2;
		int x1 = pixelX+size;
		int y1 = pixelY+size/2;
		if(x==0) {
			x0 += size/2;
		}
		if(x==chessboard.size) {
			x1 -= size/2+2;
		}
		chessboard.gc.drawLine(x0, y0, x1, y1);//横
		int x2 = pixelX+size/2-1;
		int y2 = pixelY;
		int x3 = pixelX+size/2-1;
		int y3 = pixelY+size/2;
		if(y==chessboard.size) {
			y3 -= size/2;
		}
		if(y==0) {
			y2 += size/2;
		}
		chessboard.gc.drawLine(x2, y2, x3, y3);//竖
	}
	public Chess clone() {
		BlankChess c = new BlankChess(size);
		c.x = x;
		c.y = y;
		c.pixelX = pixelX;
		c.pixelY = pixelY;
		return c;
	}
}
