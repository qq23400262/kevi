package org.kevi.map;


import org.eclipse.swt.graphics.GC;

/**
 * 正方形
 * @author 422575
 *
 */
public class MyRectangle extends Tiles{
	@Override
	public void appendToCanva(GC gc) {
		gc.drawRectangle(x, y, width, height);
	}
}
