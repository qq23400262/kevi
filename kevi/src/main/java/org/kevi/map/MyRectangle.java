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
		try {
		Image image = new Image(null, tilesUrl);
		gc.drawImage(image, pixel.x, pixel.y);
		image.dispose();
		//gc.drawRectangle(x, y, width, height);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
