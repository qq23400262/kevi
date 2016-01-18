package org.kevi;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.kevi.map.LatLng;
import org.kevi.map.MapUtil;
import org.kevi.map.Point;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }
    
    public void testMapUtil() {
    	Point pStart = new Point(20,39);
    	//Point pStart1 = new Point(1136,736);
    	//System.out.println(pStart);
    	//System.out.println(MapUtil.ll2p(MapUtil.p2ll(pStart, 2), 2));
    	double lng  = MapUtil.pixelToLng(pStart.getX(),2);
    	double lat  = MapUtil.pixelToLat(pStart.getY(),2);
    	
    	System.out.println(MapUtil.lngToPixel(lng,2));
    	System.out.println(MapUtil.latToPixel(lat,2));
    	
    	System.out.println(MapUtil.ll2p(new LatLng(lat, lng), 2));
    }
    
    public static void main(String[] args) {
    	Point pStart1 = new Point(240,330);
    	//System.out.println(pStart);
    	System.out.println(MapUtil.ll2p(MapUtil.p2ll(pStart1, 2), 2));
    	
    	Map<String, String> map = new HashMap<>();
    	map.keySet();
    	for (String key : map.keySet()) {
    		
    	}
    	map.put("1", "A");
    	map.put("2", "B");
    	map.put("26", "Z");
    	Iterator<String> i = map.values().iterator();
    	while (i.hasNext()) {
    		System.out.println(i.next());
		}
    	
	}
}
