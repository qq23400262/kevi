package org.kevi.game.fivechess;

public abstract class Chess {
	String name;
	int x = 0;
	int y = 0;
	int pixelX = 0;
	int pixelY = 0;
	int size=30;
	
	public Chess(String name, int size) {
		this.size = size;
		this.name = name;
	}
	
	public void updateXY(int x, int y ,int gridSize) {
		this.x = x;
		this.y = y;
		pixelX = x * gridSize-size/2;
		pixelY = y * gridSize-size/2;
	}
	public boolean equals(Chess chess) {
		return name.equals(chess.name);
	}
	public abstract void paint(Chessboard chessboard,int x, int y);
}
