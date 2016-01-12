package org.kevi.map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
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
		shell.setSize(617, 462);
		shell.setText("SWT Application");
		shell.setLayout(null);
		initCanvas();
	}
	private void initCanvas() {
		canvas = new Canvas(shell, SWT.NONE);
		canvas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				gc.drawLine(0, 0, 50, 50);
			}
		});
		canvas.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		canvas.setBounds(0, 0, shell.getSize().x, shell.getSize().y);
		
		this.canvas.addPaintListener(new PaintListener() {
            public void paintControl(PaintEvent e) {
            	gc = new GC(canvas);
        		Tiles tiles = ctx.getBean("myRectangle",Tiles.class);
        		tiles.appendToCanva(gc);
            }
        });  
	}
}
