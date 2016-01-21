package org.kevi.game.fivechess;

import org.eclipse.swt.graphics.Image;
import org.eclipse.wb.swt.SWTResourceManager;

public class WhiteChess extends Chess{
	public WhiteChess(int size) {
		super("白棋", size);
	}
	public void paint(Chessboard chessboard) {
		Image image = SWTResourceManager.getImage(this.getClass(), "white.png");
		chessboard.gc.drawImage(image,pixelX,pixelY);
//		chessboard.gc.setBackground(chessboard.shell.getDisplay().getSystemColor(SWT.COLOR_WHITE));
//		chessboard.gc.fillOval(pixelX, pixelY, size, size);
	}
	public Chess clone() {
		WhiteChess c = new WhiteChess(size);
		c.x = x;
		c.y = y;
		c.pixelX = pixelX;
		c.pixelY = pixelY;
		return c;
	}
}
