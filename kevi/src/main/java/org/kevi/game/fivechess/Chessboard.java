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
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

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
		shell.setSize(664, 510);
		shell.setText("SWT Application");
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
	/**
	 * 主要是把x,y鼠标坐标转化成格子数
	 * @return
	 */
	private int pixel2GridIndex(int px) {
		return (px+gridSize/2)/gridSize;
	}
	public void initCanvas() {
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		canvas = new Canvas(shell, SWT.NONE);
//		canvas.addMouseMoveListener(new MouseMoveListener() {
//			public void mouseMove(MouseEvent e) {
//				System.out.println(e.x+","+e.y);
//			}
//		});
		canvas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				putChess(pixel2GridIndex(e.x-57), pixel2GridIndex(e.y-56));
			}
		});
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
		
		Button btnNewButton = new Button(canvas, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				start();
			}
		});
		btnNewButton.setBounds(464, 141, 156, 40);
		btnNewButton.setText("重新开始");
		
		Button btnNewButton_1 = new Button(canvas, SWT.NONE);
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				retractChess();
			}
		});
		btnNewButton_1.setBounds(464, 197, 156, 40);
		btnNewButton_1.setText("悔棋");
		
		infoLabel = new Label(canvas, SWT.NONE);
		infoLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		infoLabel.setBounds(464, 304, 156, 54);
		gridSize = 26;
		chessSize = gridSize - 2;//棋子大小比gridSize小4像素
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
		
//		Image image = SWTResourceManager.getImage(this.getClass(), "background.png");
//		gc.drawImage(image, 0, 0);
		int orgin = 56;
		gc.setForeground(SWTResourceManager.getColor(114, 92, 53));
		for (int i = 0; i <= size; i++) {
			if(i==0 || i==size) {
				gc.setLineWidth(2);
			} else {
				gc.setLineWidth(1);
			}
			gc.drawLine(0+orgin, i*gridSize+orgin, size*gridSize+orgin, i*gridSize+orgin);
			gc.drawLine(i*gridSize+orgin, 0+orgin, i*gridSize+orgin, size*gridSize+orgin);
		}
		gc.setBackground(SWTResourceManager.getColor(114, 92, 53));
		gc.fillOval(7*gridSize+orgin-5, 7*gridSize+orgin-5, 10, 10);
		gc.fillOval(3*gridSize+orgin-5, 3*gridSize+orgin-5, 10, 10);
		gc.fillOval(3*gridSize+orgin-5, 11*gridSize+orgin-5, 10, 10);
		gc.fillOval(11*gridSize+orgin-5, 3*gridSize+orgin-5, 10, 10);
		gc.fillOval(11*gridSize+orgin-5, 11*gridSize+orgin-5, 10, 10);
	}
	
	
	/**
	 * 放一个棋子
	 * @param x 相对canvas的鼠标x坐标
	 * @param y 相对canvas的鼠标y坐标
	 */
	public void putChess(int x, int y) {
		if(x < 0 || x > size || y < 0 || y>size)return;
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
