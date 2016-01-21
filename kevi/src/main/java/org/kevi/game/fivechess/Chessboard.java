package org.kevi.game.fivechess;

import java.util.Stack;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Label;

/**
 * 五子棋棋盘类
 * @author kevi
 *
 */
public class Chessboard {
	protected Shell shell;
	Canvas canvas;
	Label infoLabel;
	int size = 14;//棋盘为15*15大小
	int gridSize = 0;
	int chessSize = 0;
	boolean isTurnBlack = true;//用来标记到谁下棋了，五子棋一般规定黑子先下，所以defaul值是true
	GC gc;
	ChessboardSet cbSet;//用来存放棋盘数据
	Stack<Chess> stepStack;
	AIPlayer pleryer;
	boolean isGameOver = false;
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Chessboard window = new Chessboard();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(501, 538);
		shell.setText("SWT Application");
		shell.setLayout(null);
		
		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);
		
		MenuItem gameMenu = new MenuItem(menu, SWT.CASCADE);
		gameMenu.setText("菜单");
		
		Menu gameMenuList = new Menu(gameMenu);
		gameMenu.setMenu(gameMenuList);
		
		MenuItem reStartMenu = new MenuItem(gameMenuList, SWT.NONE);
		reStartMenu.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				start();
			}
		});
		reStartMenu.setText("重新开始");
		
		MenuItem retractMenu = new MenuItem(gameMenuList, SWT.NONE);
		retractMenu.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				retractChess();
			}
		});
		retractMenu.setText("悔棋");
		initCanvas();
	}
	
	/**
	 * 重新开始
	 */
	public void start() {
		gc.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		gc.fillRectangle(0, 0, canvas.getSize().x, canvas.getSize().y);
		paintChessBoard(gc);
		stepStack = new Stack<Chess>();
		cbSet = new MapChessboardSet();
		isGameOver = false;
		isTurnBlack = true;
		pleryer = new AIPlayerNo2(this);
		updateStatus();
	}
	
	public void initCanvas() {
		canvas = new Canvas(shell, SWT.NONE);
		canvas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				putChess(pixel2GridIndex(e.x), pixel2GridIndex(e.y));
			}
		});
		canvas.setBounds(30, 30, 421, 421);//宽度最好是size(14)的倍数+1，只有14格
		gc = new GC(canvas);
		canvas.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				//画棋盘
				paintChessBoard(gc);
				//画棋子
				paintChess();
			}
		});
		canvas.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		
		infoLabel = new Label(shell, SWT.NONE);
		infoLabel.setBounds(30, 10, 230, 17);
		gridSize = canvas.getBounds().width/size;
		chessSize = gridSize - 4;//棋子大小比gridSize小4像素
		start();
	}
	
	/**
	 * 画棋子
	 */
	public void paintChess() {
		if(cbSet != null) {
			Chess _chess;
			for (int i = 0; i <= size; i++) {
				for(int j = 0; j <= size; j++) {
					_chess = cbSet.getChess(j, i);
					if(_chess != null) {
						_chess.paint(this);
					}
				}
			}
		}
	}
	/**
	 * 画棋盘
	 * @param gc
	 */
	public void paintChessBoard(GC gc) {
		for (int i = 0; i <= size; i++) {
			gc.drawLine(0, i*gridSize, size*gridSize, i*gridSize);
			gc.drawLine(i*gridSize, 0, i*gridSize, size*gridSize);
		}
	}
	
	/**
	 * 主要是把x,y鼠标坐标转化成格子数
	 * @return
	 */
	private int pixel2GridIndex(int px) {
		return (px+gridSize/2)/gridSize;
	}
	
	/**
	 * 放一个棋子
	 * @param x 相对canvas的鼠标x坐标
	 * @param y 相对canvas的鼠标y坐标
	 */
	public void putChess(int x, int y) {
		if(isGameOver)return;
		if(!cbSet.isBlankChess(x, y)) {
			return;
		}
		Chess chess = ChessFactory.producChess(isTurnBlack, chessSize);
		chess.setXY(x, y, gridSize);
		putChess(chess);
		if(!isGameOver && isTurnBlack==false) {
			Chess bestChess = pleryer.getBeastChess();
			putChess(bestChess);
		}
	}
	
	public void putChess(Chess chess) {
		stepStack.push(chess);
		cbSet.addChess(chess);
		if(isTurnBlack) {
			//显示白棋分数
			cbSet.evlation(chess, false);
			Chess _chess = ChessFactory.produceWhiteChess(chessSize);
			_chess.setXY(chess.x, chess.y, gridSize);
			System.out.println("黑棋:"+cbSet.evlation(chess, false)+",白棋:"+cbSet.evlation(_chess, true));
		} else {
			Chess _black = ChessFactory.produceBlackChess(chessSize);
			_black.setXY(chess.x, chess.y, gridSize);
			System.out.println("白棋:"+cbSet.evlation(chess, true)+",黑棋:"+cbSet.evlation(_black, false));
		}
		
//		System.out.println("白棋分数:"+_score+",黑棋分数:"+_score1);
		chess.paint(this);
		isGameOver = cbSet.isGameOver(chess);
		isTurnBlack = !isTurnBlack;
		updateStatus();
	}
	
	public void updateStatus() {
		if(isGameOver) {
			infoLabel.setText(isTurnBlack?"白棋胜出":"黑棋胜出");
			MessageDialog.openInformation(shell,"系统提示",isTurnBlack?"白棋胜出":"黑棋胜出");
		} else {
			infoLabel.setText(isTurnBlack?"轮到黑棋下":"轮到白棋下");
		}
	}
	
	/**
	 * 悔棋
	 */
	public void retractChess() {
		if(isGameOver)return;
		if(stepStack.isEmpty()) {
			return;//已经没有棋可回了
		}
		Chess chess = stepStack.pop();
		cbSet.removeChess(chess);
		Chess tmpChess = ChessFactory.produceBlankChess(chessSize);
		tmpChess.setXY(chess.x, chess.y, gridSize);
		tmpChess.paint(this);
		chess = null;
		isTurnBlack = !isTurnBlack;
		updateStatus();
	}
}
