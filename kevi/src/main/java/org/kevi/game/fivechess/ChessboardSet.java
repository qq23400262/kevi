package org.kevi.game.fivechess;

/**
 * 原来数组、list、map不知道用哪个，百度一下说数组效率会好一点 后面想想，不如先不定死，
 * 让子类去实现，也就是说可以多种实现方法
 * 
 * @author 422575
 *
 */
public abstract class ChessboardSet {
	//位置重要性价值表,此表从中间向外,越往外价值越低
	int[][] posValue = {
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
			{0,1,2,2,2,2,2,2,2,2,2,2,2,1,0},
			{0,1,2,3,3,3,3,3,3,3,3,3,2,1,0},
			{0,1,2,3,4,4,4,4,4,4,4,3,2,1,0},
			{0,1,2,3,4,5,5,5,5,5,4,3,2,1,0},
			{0,1,2,3,4,5,6,6,6,5,4,3,2,1,0},
			{0,1,2,3,4,5,6,7,6,5,4,3,2,1,0},
			{0,1,2,3,4,5,6,6,6,5,4,3,2,1,0},
			{0,1,2,3,4,5,5,5,5,5,4,3,2,1,0},
			{0,1,2,3,4,4,4,4,4,4,4,3,2,1,0},
			{0,1,2,3,3,3,3,3,3,3,3,3,2,1,0},
			{0,1,2,2,2,2,2,2,2,2,2,2,2,1,0},
			{0,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
		};
	static final int winCount = 5;
	static final int chessBoardSize = 15;
	int x0=6,y0=6,x1=8,y1=8;
	public void addChess(Chess chess) {
		subAddChess(chess);
		int aiSize = 2;
		if(x0 > chess.x - aiSize) {
			x0 = chess.x - aiSize;
			if(x0 < 0) {
				x0 = 0;
			}
		}
		if(y0 > chess.y - aiSize) {
			y0 = chess.y - aiSize;
			if(y0 < 0) {
				y0 = 0;
			}
		}
		if(x1 < chess.x + aiSize) {
			x1 = chess.x + aiSize;
			if(x1 >= chessBoardSize) {
				x1 = chessBoardSize-1;
			}
		}
		if(y1 < chess.y + aiSize) {
			y1 = chess.y + aiSize;
			if(y1 >= chessBoardSize) {
				y1 = chessBoardSize-1;
			}
		}
	}
	public abstract void subAddChess(Chess chess);
	public abstract Chess getLastChess();
	public abstract Chess getChess(int x, int y);
	public abstract void removeChess(int x, int y);
	public abstract void removeChess(Chess chess);
	public abstract ChessboardSet clone();
	int[][] lines = {{-1, -1, 1, 1},{1, -1, -1, 1},{-1, 0, 1, 0},{0, -1, 0, 1}};//"米"字方向递增减扫描
	public boolean isBlankChess(int x, int y) {
		return getChess(x, y) == null;
	}
	
	/**
	 * 向某个方向统计相同颜色的棋子
	 * @param chess
	 * @param offsetX -1,0,1
	 * @param offsetY -1,0,1
	 * @param result
	 * @return
	 */
	public int[] countSameChess(Chess chess, int offsetX, int offsetY, int result) {
		int _x = chess.x + offsetX;
		if(_x<0 || _x >= chessBoardSize) {
			int[] r = {result, 0};//0代表端点不能放棋子了
			return r;
		}
		int _y = chess.y + offsetY;
		if(_y<0 || _y >= chessBoardSize) {
			int[] r = {result, 0};//0代表端点不能放棋子了
			return r;
		}
		Chess _chess = getChess(_x, _y);
		if(_chess == null) {
			int[] r = {result, 1};//1代表可以放棋子
			return r;
		} else if(!_chess.equals(chess)) {
			int[] r = {result, 0};//0代表端点不能放棋子了
			return r;
		}
		result++;
		return countSameChess(_chess, offsetX, offsetY, result);
	}
	/**
	 * 根据最后一个子判断游戏是否结束
	 * @param chess
	 */
	public boolean isGameOver(Chess chess) {
		for (int[] ls : lines) {
			int count1 = countSameChess(chess, ls[0], ls[1], 0)[0];
			int count2 = countSameChess(chess, ls[2], ls[3], 0)[0];
			if(count1 + count2 + 1 >= winCount) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 
	 * @param chess
	 * @param chessBoardSize 这个参数可以说没用，因为五子棋是固定15*15的
	 * @return
	 */
	public int evlation(Chess chess, boolean isAI) {
		int score = 0;
		int nearCount = 0;
		int blankCount = 0;
		int blankCount4_2 = 0;
		int blankCount4_1 = 0;
		int blankCount3_2 = 0;
		int blankCount3_1 = 0;
		int blankCount2_2 = 0;
		int blankCount2_1 = 0;
		for (int[] ls : lines) {
			nearCount = 1 + countSameChess(chess, ls[0], ls[1], 0)[0] + countSameChess(chess, ls[2], ls[3], 0)[0];
			blankCount = countSameChess(chess, ls[0], ls[1], 0)[1] + countSameChess(chess, ls[2], ls[3], 0)[1];
//			System.out.println(nearCount+","+blankCount);
			//判断是否能成5,   如果是机器方的话给予100000分，如果是人方的话给予－100000   分；
			if(nearCount >= winCount) {
				System.out.println("!!!!!!!!"+chess.x+"..."+chess.y);
				score = 100000;
				score = isAI?score:-score;
				return score;
			} else {
				if(nearCount == 4 && blankCount==2) {
					blankCount4_2++;
				} else if(nearCount == 4 && blankCount==1) {
					blankCount4_1++;
				} else if(nearCount == 3 && blankCount==2) {
					blankCount3_2++;
				} else if(nearCount == 3 && blankCount==1) {
					blankCount3_1++;
				} else if(nearCount == 2 && blankCount==2) {
					blankCount2_2++;
				} else if(nearCount == 2 && blankCount==1) {
					blankCount2_1++;
				}
			}
		}
		//判断是否能成活4或者是双死4或者是死4活3，如果是机器方的话给予10000分，如果是人方的话给予－10000分；
		if(blankCount4_2>0 || blankCount4_1 >= 2 || (blankCount4_1>=1&&blankCount3_2>0)) {
			score = 10000+posValue[chess.x][chess.y];
			score = isAI?score:-score;
			return score;
		}
		//判断是否已成双活3，如果是机器方的话给予5000分，如果是人方的话给予－5000   分；
		if(blankCount3_2==4) {
			score = 5000+posValue[chess.x][chess.y];
			score = isAI?score:-score;
			return score;
		}
		//判断是否成死3活3，如果是机器方的话给予1000分，如果是人方的话给予－1000   分；   
		if(blankCount3_1>0 && blankCount3_2==2) {
			score = 1000+posValue[chess.x][chess.y];
			score = isAI?score:-score;
			return score;
		}
		//判断是否能成死4，如果是机器方的话给予500分，如果是人方的话给予－500分；     
		if(blankCount4_1>0) {
			score = 500+posValue[chess.x][chess.y];
			score = isAI?score:-score;
			return score;
		}
		//判断是否能成单活3，如果是机器方的话给予200分，如果是人方的话给予－200分；    
		if(blankCount3_2==1) {
			score = 200+posValue[chess.x][chess.y];
			score = isAI?score:-score;
			return score;
		}
		//判断是否已成双活2，如果是机器方的话给予100分，如果是人方的话给予－100分；
		if(blankCount2_2==4) {
			score = 100+posValue[chess.x][chess.y];
			score = isAI?score:-score;
			return score;
		}
		//判断是否能成死3，如果是机器方的话给予60分，如果是人方的话给予－60分； 
		if(blankCount3_1>0) {
			score = 60+posValue[chess.x][chess.y];
			score = isAI?score:-score;
			return score;
		}
		//判断是否能成双活2，如果是机器方的话给予30分，如果是人方的话给予－30分；
		if(blankCount2_2==2) {
			score = 30+posValue[chess.x][chess.y];
			score = isAI?score:-score;
			return score;
		}
		//判断是否能成活2，如果是机器方的话给予20分，如果是人方的话给予－20分；
		if(blankCount2_2>0) {
			score = 20+posValue[chess.x][chess.y];
			score = isAI?score:-score;
			return score;
		}
		//判断是否能成死2，如果是机器方的话给予10分，如果是人方的话给予－10分。
		if(blankCount2_1>0) {
			score = 10+posValue[chess.x][chess.y];
			score = isAI?score:-score;
			return score;
		}
		score = posValue[chess.x][chess.y];
		score = isAI?score:-score;
		return score;
	}
	@Override
	public String toString() {
//		return "ChessboardSet [x0=" + x0 + ", y0=" + y0 + ", x1=" + x1 + ", y1=" + y1 + "]";
		String str = "";
		Chess chess;
		for (int i = 0; i < chessBoardSize; i++) {
			for (int j = 0; j < chessBoardSize; j++) {
				chess = getChess(j, i);
				if(chess==null) {
					str += "_";
				} else if(chess.name.equals("黑棋")) {
					str += "黑";
				} else {
					str += "白";
				}
			}
			str+="\n";
		}
		return str;
//		return "ChessboardSet [x0=" + x0 + ", y0=" + y0 + ", x1=" + x1 + ", y1=" + y1 + "]";
	}
	
}
