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

public class MapCanvaApp {

	protected Shell shell;
	protected Canvas canvas;
	protected GC gc; 
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
				final int x = e.x;
				final int y = e.y;
				if(state==1) {
					clearCanvas();
					
					mapData.loop(new TilesHandle() {
						public void action(Tiles tiles, int rIdx, int cIdx) {
							// TODO Auto-generated method stub
							tiles.move(x - mapData.originX+xd, y - mapData.originY+yd);
							tiles.appendToCanva(gc);
						}
					});
					mapData.updateOrigin();
					
				}
			}
		});
		canvas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				state = 1;
				xd = mapData.originX - e.x;
				yd = mapData.originY - e.y;
				shell.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_SIZEALL));
			}
			@Override
			public void mouseUp(MouseEvent e) {
				state = 0;
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
}
