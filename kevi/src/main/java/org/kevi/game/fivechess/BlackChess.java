package org.kevi.game.fivechess;

import org.eclipse.swt.SWT;

public class BlackChess extends Chess{
	public BlackChess(int gridSize) {
		super("黑棋", gridSize);
	}
	
	public void paint(Chessboard chessboard,int x, int y) {
		updateXY(x, y,chessboard.gridSize);
		chessboard.gc.setBackground(chessboard.shell.getDisplay().getSystemColor(SWT.COLOR_BLACK));
		chessboard.gc.fillOval(pixelX, pixelY, size, size);
	}
}
