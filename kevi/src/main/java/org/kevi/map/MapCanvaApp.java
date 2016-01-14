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
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MapCanvaApp extends Thread implements MapHelper{
	Shell shell;
	Canvas canvas;
	GC gc;
	Point mousePoint = new Point(0, 0);
	MapContext context = new MapContext();
	ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:bean.xml");
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
		shell.addMouseMoveListener(new MouseMoveListener() {
			public void mouseMove(MouseEvent e) {
				
			}
		});
		shell.setSize(672, 672);
		shell.setText("SWT Application");
		initCanvas();
		
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
	
	
	int state = 0;//1是拖动
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
            	mapData = new MapObject(3, canvas.getSize().x,canvas.getSize().y, new LatLng(30.813803, 104.303287));
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
		while(state==1) {
			context.request(this);
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
				mapData.loop(new TilesHandle() {
					public void action(Tiles tiles, int rIdx, int cIdx) {
						// TODO Auto-generated method stub
						tiles.move(mousePoint.x - mapData.originX+xd, mousePoint.y - mapData.originY+yd);
						tiles.appendToCanva(gc);
					}
				});
				mapData.move(new Point(mousePoint.x - mapData.originX+xd, mousePoint.y - mapData.originY+yd));
				mapData.updateOrigin();
//				canvas.setBounds(mousePoint.x - mapData.originX+xd, mousePoint.y - mapData.originY+yd, canvas.getSize().x, canvas.getSize().y);
			}
		});
	}
	private void moveMapStart() {
		state = 1;
		context.setHandler(new MapMoveHandler());
		new Thread(this).start();
	}
	public void moveEnd() {
		state = 0;
	}

	public void showLatLng() {
		// TODO Auto-generated method stub
		System.out.println("cur position:");
	}
	
}
