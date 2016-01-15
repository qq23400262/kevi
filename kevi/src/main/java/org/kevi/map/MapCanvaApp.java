package org.kevi.map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;

public class MapCanvaApp extends Thread implements MapHelper{
	Shell shell;
	Canvas canvas;
	GC gc;
	Point mousePoint = new Point(0, 0);
	MapContext context = new MapContext();
	Thread thread;
	boolean isRunning = true;
	boolean mousePointChange = false;
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MapCanvaApp window = new MapCanvaApp();
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
		shell.addShellListener(new ShellAdapter() {
			@Override
			public void shellClosed(ShellEvent e) {
				isRunning = false;
			}
		});
		shell.addMouseMoveListener(new MouseMoveListener() {
			public void mouseMove(MouseEvent e) {
				
			}
		});
		shell.setSize(672, 672);
		shell.setText("SWT Application");
		init();
	}
	protected void init() {
		initCanvas();
		thread = new Thread(this);
		thread.start();
	}
	private void updateCanvas() {
		mapData.loop(new TilesHandle() {
			public void action(Tiles tiles, int rIdx, int cIdx) {
				tiles.appendToCanva(gc);
			}
		});
	}
	
	private void clearCanvas() {
		gc.fillRectangle(0,0,canvas.getSize().x,canvas.getSize().y);
	}
	
	int xd;
	int yd;
	MapObject mapData;
	
	private void initCanvas() {
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		canvas = new Canvas(shell, SWT.NO_BACKGROUND  |  SWT.DOUBLE_BUFFERED);
		canvas.addMouseWheelListener(new MouseWheelListener() {
			public void mouseScrolled(MouseEvent e) {
				if(e.count==3) {//上滚
					mapData.zoomOut();
					updateCanvas();
				} else if(e.count==-3) {//下滚
					mapData.zoomIn();
					updateCanvas();
				}
			}
		});
		gc = new GC(canvas);
		canvas.addMouseMoveListener(new MouseMoveListener() {
			public void mouseMove(MouseEvent e) {
				mousePoint.setX(e.x+canvas.getBounds().x);
				mousePoint.setY(e.y+canvas.getBounds().y);
				mousePointChange = true;
			}
		});
		
		canvas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				xd = mapData.originX - e.x;
				yd = mapData.originY - e.y;
				shell.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_SIZEALL));
				moveMapStart();
			}
			@Override
			public void mouseUp(MouseEvent e) {
				moveEnd();
				shell.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
			}
		});
		canvas.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		
		this.canvas.addPaintListener(new PaintListener() {
            public void paintControl(final PaintEvent e) {
            	mapData = new MapObject(1, canvas.getSize().x,canvas.getSize().y, new LatLng(30.813803, 104.303287));
            	mapData.loop(new TilesHandle() {
					public void action(Tiles tiles, int rIdx, int cIdx) {
						// TODO Auto-generated method stub
						tiles.appendToCanva(e.gc);
					}
				});
            	e.gc.dispose();
            }
        });  
	}

	public void run() {
		while (isRunning) {
			if(mousePointChange) {
				context.request(this);
				mousePointChange = false;
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public Display getDisplay() {
		return this.getDisplay();
	}
	public void moveMap() {
		
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				clearCanvas();
				
				mapData.drawTiles(gc);
//				mapData.loop(new TilesHandle() {
//					public void action(Tiles tiles, int rIdx, int cIdx) {
//						// TODO Auto-generated method stub
//						tiles.move(mousePoint.x - mapData.originX+xd, mousePoint.y - mapData.originY+yd);
//						tiles.appendToCanva(gc);
//					}
//				});
//				mapData.move(new Point(mousePoint.x - mapData.originX+xd, mousePoint.y - mapData.originY+yd));
//				mapData.updateOrigin();
////				canvas.setBounds(mousePoint.x - mapData.originX+xd, mousePoint.y - mapData.originY+yd, canvas.getSize().x, canvas.getSize().y);
			}
		});
	}
	private void moveMapStart() {
		context.changeHandler(MapContext.HandlerBean.MAP_MOVE_HANDLER);
	}
	public void moveEnd() {
		context.changeHandler(MapContext.HandlerBean.MAP_SHOW_LATLNG_HANDLER);
	}

	public void showLatLng() {
		// TODO Auto-generated method stub
		if(mapData != null)
		System.out.println("cur position:"+mapData.center);
	}
	
}
