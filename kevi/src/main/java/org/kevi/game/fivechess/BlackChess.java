package org.kevi.game.fivechess;

import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;

public class BlackChess extends Chess{
	public BlackChess(int gridSize) {
		super("黑棋", gridSize);
	}
	
	public void paint(Chessboard chessboard) {
//		Image image = SWTResourceManager.getImage(this.getClass(), "black.png");
//		chessboard.gc.drawImage(image,pixelX,pixelY);
		chessboard.gc.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		chessboard.gc.fillOval(pixelX, pixelY, size, size);
//		chessboard.gc.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
//		chessboard.gc.drawOval(pixelX, pixelY, size, size);
	}
	
	public Chess clone() {
		BlackChess c = new BlackChess(size);
		c.x = x;
		c.y = y;
		c.pixelX = pixelX;
		c.pixelY = pixelY;
		return c;
	}
}
