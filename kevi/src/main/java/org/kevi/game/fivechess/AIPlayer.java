package org.kevi.game.fivechess;

/**
 * 听说博弈树加上α-β剪枝算法做出来的五子棋AI往前推6步无压力
 * 这里写得比较清楚:
 * http://www.cnblogs.com/pangxiaodong/archive/2011/05/26/2058864.html
 * @author 422575
 *
 */
public interface AIPlayer {
	public Chess getBeastChess();
}
