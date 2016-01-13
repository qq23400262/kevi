package org.kevi.map;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.eclipse.swt.widgets.Display;
import org.kevi.util.DownloadUtil;

public class MapDownLoadUtil {
	public static String GOOGLE_URL_T="http://mt2.google.cn/vt/lyrs=t@130,r@279000000&hl=zh-cn&gl=cn&src=app&s=Gali&apistyle=s.t%3A3|s.e%3Al.t|p.v%3Aoff%2Cs.t%3A2|p.v%3Aoff%2Cs.t%3A1|s.e%3Al|p.v%3Aoff%2Cs.t%3A5|s.e%3Al|p.v%3Aoff%2Cs.e%3Al|p.v%3Aoff&style=api|smartmaps&x=${x}&y=${y}&z=${z}";//谷歌地理无标签
	public static String SAVE_PATH_T = "D:/gmap/t";
	public static String GOOGLE_URL_S="http://mt2.google.cn/vt/lyrs=s@161&hl=zh-cn&gl=cn&s=Galileo&x=${x}&y=${y}&z=${z}";//谷歌卫星无标签
	public static String SAVE_PATH_S = "D:/gmap/s";
	//http://services.arcgisonline.com/ArcGIS/rest/services/World_Imagery/MapServer/tile/${z}/${y}/${x}
	public static int L = 256;
	
	static double[] CHINA_BOUNDS = {73.3657,53.63411,135.2531,18.14923};
	CountDownLatch latch;// 计算器
	FileDownLoad fileDownLoad;
	MapToolApp mapToolApp;
	boolean startDownLoad = false;
	public MapDownLoadUtil() {
		
	}
	public MapDownLoadUtil(MapToolApp mapToolApp) {
		this.mapToolApp = mapToolApp;
	}
	int totalTaskCount = 0;
	public static double lngToPixel(double lng, int zoom) {
		return (lng + 180) * (256 << zoom) / 360;
	}

	public static double pixelToLng(double pixelX, int zoom) {
		return pixelX * 360 / (256 << zoom) - 180;
	}

	public static double latToPixel(double lat, int zoom) {
		double siny = Math.sin(lat * Math.PI / 180);
		double y = Math.log((1 + siny) / (1 - siny));
		return (128 << zoom) * (1 - y / (2 * Math.PI));
	}

	public static double pixelToLat(double pixelY, int zoom) {
		double y = 2 * Math.PI * (1 - pixelY / (128 << zoom));
		double z = Math.pow(Math.E, y);
		double siny = (z - 1) / (z + 1);
		return Math.asin(siny) * 180 / Math.PI;
	}
	
	/**
	 * 计算总共要下载的任务数
	 * @param zIndex
	 * @param onlyCurLevel
	 * @param onlyChina
	 * @return
	 */
	private int getTotalTaskCountByZIdex(int zIndex, boolean onlyCurLevel, boolean onlyChina) {
		int startZIndex = 0;
		if(onlyCurLevel) {
			startZIndex = zIndex;
		}
		int totalTaskCount = 0;
		for(int z = startZIndex; z <= zIndex; z++) {
			int n = getEndXYByZoom(z);
			int minI = 0;
			int minJ = 0;
			int maxI = n;
			int maxJ = n;
			if(onlyChina) {
				double minX = lngToPixel(CHINA_BOUNDS[0], z);
				double minY = latToPixel(CHINA_BOUNDS[1], z);
				double maxX = lngToPixel(CHINA_BOUNDS[2], z);
				double maxY = latToPixel(CHINA_BOUNDS[3], z);
				minI = (int)(minX/L)-1;
				minJ = (int)(minY/L)-1;
				maxI = (int)(maxX/L)+1;
				maxJ = (int)(maxY/L)+1;
			}
			totalTaskCount += ((maxI+1)-minI) * ((maxJ+1)-minJ);
		}
		return totalTaskCount;
	}
	
	/**
	 * 多层级地图下载
	 * @param mapURL
	 * @param savePath
	 * @param z
	 * @param onlyCurLevel
	 * @param onlyChina
	 * @threadCount 下载线程数量
	 */
	public void download(String mapURL,String savePath, int z,boolean onlyCurLevel,boolean onlyChina, int threadCount) {
		startDownLoad = true;
		totalTaskCount = getTotalTaskCountByZIdex(z, onlyCurLevel, onlyChina);
		mapToolApp.showInfo("开始下载，任务数：" + totalTaskCount, false);
		latch = new MyCountDownLatch(totalTaskCount);
		fileDownLoad = new FileDownLoad(latch, threadCount);
		if(onlyCurLevel) {
			downloadByZIndex(mapURL,savePath, z,onlyChina);
		} else {
			for (int i = 0; i <= z; i++) {
				downloadByZIndex(mapURL,savePath, i,onlyChina);
			}
		}
	}
	
	/**
	 * 获取任务总数
	 * @return
	 */
	public int getTotalTaskCount() {
		return totalTaskCount;
	}
	
	/**
	 * 单层级地图下载
	 * @param mapURL
	 * @param savePath
	 * @param z
	 * @param onlyChina
	 */
	private void downloadByZIndex(String mapURL,String savePath, int z,boolean onlyChina) {
		//0是lng,1是lat,lng对x,lat对y
		List<String> addressList = new ArrayList<String>();
		List<String> savepathList = new ArrayList<String>();
		int n = getEndXYByZoom(z);
		
		int minI = 0;
		int minJ = 0;
		int maxI = n;
		int maxJ = n;
		if(onlyChina) {
			double minX = lngToPixel(CHINA_BOUNDS[0], z);
			double minY = latToPixel(CHINA_BOUNDS[1], z);
			double maxX = lngToPixel(CHINA_BOUNDS[2], z);
			double maxY = latToPixel(CHINA_BOUNDS[3], z);
			minI = (int)(minX/L)-1;
			minJ = (int)(minY/L)-1;
			maxI = (int)(maxX/L)+1;
			maxJ = (int)(maxY/L)+1;
		}
		String address;
		for (int i = minI; i <= maxI; i++) {
			for (int j = minJ; j <= maxJ; j++) {
				address = mapURL.replace("${x}", i+"").replace("${y}", j+"").replace("${z}", z+"");
				addressList.add(address);
				savepathList.add(savePath + "/" + z + "/" + i + "/" + j
						+ ".png");
				if(addressList.size() > 1000) {
					try {
						fileDownLoad.download(addressList,savepathList);
					} catch (Exception e) {
						e.printStackTrace();
					}
					addressList = new ArrayList<String>();
					savepathList = new ArrayList<String>();
				}
			}
		}
		if(addressList.size() > 0) {
			try {
				fileDownLoad.download(addressList,savepathList);
			} catch (Exception e) {
				e.printStackTrace();
			}
			addressList = new ArrayList<String>();
		}
		
	}

	private int getEndXYByZoom(int z) {
		return (int) Math.pow(2, z) - 1;
	}
	
	public void stopDownLoad() {
		if(fileDownLoad != null) {
			fileDownLoad.stopDownload();
		}
	}
	
	public void startDownLoad() {
		if(fileDownLoad != null) {
			fileDownLoad.startDownload();
		}
	}
	
	public void endDownLoad() {
		startDownLoad = false;
		fileDownLoad.shutdownNowDownloadService();
	}
	
	class MyCountDownLatch extends CountDownLatch{
		public MyCountDownLatch(int count) {
			super(count);
		}
		
		@Override
		public void countDown() {
			super.countDown();
			final int thisCount = (int)this.getCount();
			Display.getDefault().syncExec(new Runnable() {
				public void run() {
					if(startDownLoad) {
						// progressBar
						if(thisCount <= 0) {
							//完成任务 
							fileDownLoad.shutdownNowDownloadService();
							if (mapToolApp != null) {
								mapToolApp.finishDownLoad();
							}
						} else if (mapToolApp != null) {
							int finishCount = totalTaskCount - (int)latch.getCount();
							mapToolApp.setSelection(finishCount);
							if(finishCount%100==0) {
								mapToolApp.showInfo("完成进度：" + finishCount + "/"	+ totalTaskCount, false);
							}
						}
					}
				}
			});
		}
	}

}

class FileDownLoad {
	private CountDownLatch latch;// 计算器
	private CountDownLatch latch1;
	ExecutorService service;
	boolean isStop = false;

	/**
	 * 马上关闭下载线程
	 */
	public void shutdownNowDownloadService() {
		isStop = false;
		latch1 = new CountDownLatch(1);
		service.shutdownNow();
	}
	public FileDownLoad(CountDownLatch latch,int threadCount) {
		this.latch = latch;
		this.latch1 = new CountDownLatch(1);
		service = Executors.newFixedThreadPool(threadCount);
	}
	public void stopDownload() {
		latch1 = new CountDownLatch(1);
		isStop = true;
	}
	public void startDownload() {
		latch1.countDown();
		isStop = false;
	}
	public void download(List<String> addressList,List<String> savepathList)
			throws Exception {
		for (int i = 0; i < addressList.size(); i++) {
			service.execute(new DownLoadThread(addressList.get(i), savepathList
					.get(i), false));
		}
	}

	class DownLoadThread implements Runnable {
		private String url;
		private String savepath;
		private boolean isOveride;

		DownLoadThread(String url, String savepath, boolean isOveride) {
			this.url = url;
			this.savepath = savepath;
			this.isOveride = isOveride;
		}

		public void run() {
			try {
				if(isStop) {
					try{
						latch1.await();
					} catch(Exception e) {
						Thread.interrupted();
					}
				}
				DownloadUtil.download(url, savepath, isOveride);
				latch.countDown();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}