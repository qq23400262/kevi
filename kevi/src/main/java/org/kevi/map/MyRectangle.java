package org.kevi.map;


import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;

/**
 * 正方形
 * @author 422575
 *
 */
public class MyRectangle extends Tiles{
	public MyRectangle(int rIdx, int cIdx, int zIdx) {
		super(rIdx, cIdx, zIdx);
	}

	@Override
	public void appendToCanva(GC gc) {
		gc.drawImage(new Image(null, tilesUrl), pixel.x, pixel.y);
		//gc.drawRectangle(x, y, width, height);
	}
}
