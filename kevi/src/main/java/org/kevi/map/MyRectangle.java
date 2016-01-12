package org.kevi.map;


import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;

/**
 * 正方形
 * @author 422575
 *
 */
public class MyRectangle extends Tiles{
	@Override
	public void appendToCanva(GC gc) {
		final Image img = new Image(null, "D:/gmap/t_ok/0/0/0.png");
		gc.drawImage(img, 0, 0);
	}
}
