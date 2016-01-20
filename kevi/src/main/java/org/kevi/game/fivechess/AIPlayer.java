package org.kevi.game.fivechess;

/**
 * 听说博弈树加上α-β剪枝算法做出来的五子棋AI往前推6步无压力
 * 这里写得比较清楚:
 * http://www.cnblogs.com/pangxiaodong/archive/2011/05/26/2058864.html
 * http://wenku.baidu.com/link?url=f1tlCH4z5GPkNRPEBlYnw8JYDcnyxhhgmlj4iMTvszMx4vmj347ym6-PLolcn7ZT7-9KAW-8eKBjblYbBpnz2jK_opFY8xarmvwuumpie3C
 * http://www.docin.com/p-665121069.html
 * @author 422575
 *
 */
public interface AIPlayer {
	public Chess getBeastChess();
}
