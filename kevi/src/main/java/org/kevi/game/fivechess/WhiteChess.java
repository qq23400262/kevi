package org.kevi.game.fivechess;

import org.eclipse.swt.SWT;

public class WhiteChess extends Chess{
	public WhiteChess(int size) {
		super("白棋", size);
	}
	public void paint(Chessboard chessboard,int x, int y) {
		updateXY(x, y,chessboard.gridSize);
		chessboard.gc.setBackground(chessboard.shell.getDisplay().getSystemColor(SWT.COLOR_WHITE));
		chessboard.gc.fillOval(pixelX, pixelY, size, size);
	}
}
