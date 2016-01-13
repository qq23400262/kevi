package org.kevi.map;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.kevi.util.DownloadUtil;
import org.kevi.util.FileUtil;

public class MapToolApp{
	protected Shell shlv;
	private Text logText;
	private Text level;
	private Text textUserMapType;
	private Text threadCount;
	private Button btnStart;
	private Button btnStop;
	private Button btnEnd;
	private Button btnSystemMapType;
	private Button btnUserMapType;
	private Combo comboSystemMapTypes;
	private Button onlyCurLevel;
	private MapDownLoadUtil mapDownLoadUtil;
	ProgressBar progressBar;
	private Label savePathLabel;
	private Text savePathText;
	Map<String, String> mapTypeMap;
	private Button btnAddMapType;
	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MapToolApp window = new MapToolApp();
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
		while (!shlv.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	
	/**
	 * 禁止下载操作
	 * @param value
	 */
	public void disableDownLoadOperation(boolean value) {
		btnStart.setEnabled(!value);
		btnStop.setEnabled(false);
		btnEnd.setEnabled(false);
	}
	
	/**
	 * 禁止修改下载设置
	 * @param value
	 */
	public void disableDownLoadOptions(boolean value) {
		btnStart.setEnabled(!value);
		level.setEnabled(!value);
		textUserMapType.setEnabled(!value);
		threadCount.setEnabled(!value);
		btnSystemMapType.setEnabled(!value);
		btnUserMapType.setEnabled(!value);
		comboSystemMapTypes.setEnabled(!value);
		onlyCurLevel.setEnabled(!value);
		savePathText.setEnabled(!value);
	}
	/**
	 * 开始下载
	 */
	public void startDownLoad() {
//		progressBar.setMinimum(0);
//		progressBar.setMinimum(100);
//		progressBar.setSelection(50);
//		if(true)return;
		//禁止所有选项
		disableDownLoadOptions(true);
		btnStop.setEnabled(true);
		btnEnd.setEnabled(true);
		//MapDownLoadUtil.downloadByZIndex(mapURL, savePath, 1, false);
		if(mapDownLoadUtil == null) {
			mapDownLoadUtil = new MapDownLoadUtil(this);
		}
		String mapURL="";
		if(btnUserMapType.getSelection()) {
			mapURL = textUserMapType.getText();
		} else {
			String mapType = comboSystemMapTypes.getText();
			mapURL = mapTypeMap.get(mapType);
		}
		String savePath = savePathText.getText();
		if(savePathText.getText()==null || savePathText.getText().trim().equals("")) {
			savePath = System.getProperty("user.dir");
		}
		mapDownLoadUtil.download(mapURL, savePath, Integer.parseInt(level.getText()), onlyCurLevel.getSelection(), false, Integer.parseInt(threadCount.getText()));
		progressBar.setMinimum(0);
		progressBar.setMaximum(mapDownLoadUtil.getTotalTaskCount());
	}
	
	/**
	 * 暂停下载
	 */
	public void stopDownLoad() {
		if(btnStop.getText().equals("暂停下载")) {
			mapDownLoadUtil.stopDownLoad();
			btnStop.setText("继续下载");
		} else {
			btnStop.setText("暂停下载");
			mapDownLoadUtil.startDownLoad();
		}
		
	}
	
	/**
	 * 终止下载
	 */
	public void endDownLoad() {
		disableDownLoadOptions(false);
		btnStop.setEnabled(false);
		btnEnd.setEnabled(false);
		mapDownLoadUtil.endDownLoad();
		progressBar.setSelection(0);
		
	}
	
	/**
	 * 加载配置
	 */
	public void loadConfig(String name, String url) {
		// 读取一般的属性文件
		try {
			String iniPath = System.getProperty("user.dir") + "\\conf.ini";
			File file = new File(iniPath);
			boolean notExists = false;
			if(!file.exists()){
				notExists = true;
				file.createNewFile();
			}
			Properties props = new Properties(); // 建立属性类
			String mapTypes;
			if(notExists) {
				String google_t="http://mt2.google.cn/vt/lyrs=t@130,r@279000000&hl=zh-cn&gl=cn&src=app&s=Gali&apistyle=s.t%3A3|s.e%3Al.t|p.v%3Aoff%2Cs.t%3A2|p.v%3Aoff%2Cs.t%3A1|s.e%3Al|p.v%3Aoff%2Cs.t%3A5|s.e%3Al|p.v%3Aoff%2Cs.e%3Al|p.v%3Aoff&style=api|smartmaps&x=${x}&y=${y}&z=${z}";//谷歌地理无标签
				String google_s="http://mt2.google.cn/vt/lyrs=s@161&hl=zh-cn&gl=cn&s=Galileo&x=${x}&y=${y}&z=${z}";//谷歌卫星无标签
				String arcgis_s="http://services.arcgisonline.com/ArcGIS/rest/services/World_Imagery/MapServer/tile/${z}/${y}/${x}";//ArcGis
				mapTypes = "谷歌地理>"+google_t+";谷歌卫星>"+google_s+";ArcGis>"+arcgis_s;
				props.setProperty("mapTypes", mapTypes);
				FileOutputStream out = new FileOutputStream(file);
				props.store(out, "kevi--");
				out.close();
			} else {
				FileInputStream fin = new FileInputStream(file);// 打开文件
				props.load(fin); // 读入文件
				mapTypes = props.get("mapTypes").toString();
				fin.close(); // 关闭文件
				if(name != null) {
					//保存
					String[] list = mapTypes.split(";");
					for (int i = 0; i < list.length; i++) {
						if(list[i].split(">")[0].equals(name)) {
							MessageDialog.openInformation(shlv,"系统提示","名称已经存在，保存失败!");
							return;
						}
					}
					mapTypes += ";"+name+">"+url;
					props.setProperty("mapTypes", mapTypes);
					FileOutputStream out = new FileOutputStream(file);
					props.store(out, "kevi--");
					out.close();
				}
			}
			if(mapTypes != null && !"".equals(mapTypes)) {
				String[] list = mapTypes.split(";");
				String[] texts = new String[list.length];
				mapTypeMap = new HashMap<String,String>();
				for (int i = 0; i < list.length; i++) {
					texts[i] =  list[i].split(">")[0];
					mapTypeMap.put(texts[i], list[i].split(">")[1]);
				}
				comboSystemMapTypes.setItems(texts);
				comboSystemMapTypes.select(0);
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void  finishDownLoad() {
		MessageDialog.openInformation(shlv,"系统提示","下载完毕!");
		showInfo("Download success!!", false);
		endDownLoad();
	}
	
	/**
	 * 选项发生改变
	 */
	public void optionChange() {
		if(btnUserMapType.getSelection()) {
			textUserMapType.setEditable(true);
			if(textUserMapType.getText()==null || textUserMapType.getText().trim().equals("")) {
				disableDownLoadOperation(true);
				return;
			}
		} else {
			textUserMapType.setEditable(false);
		}
		if(level.getText() == null || level.getText().trim().equals("")) {
			disableDownLoadOperation(true);
			return;
		}
		if(threadCount.getText() == null || threadCount.getText().trim().equals("")) {
			disableDownLoadOperation(true);
			return;
		}
		disableDownLoadOperation(false);
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlv = new Shell();
		shlv.setSize(554, 464);
		shlv.setImage(SWTResourceManager.getImage(this.getClass(), "icon.png"));
		shlv.setText("地图下载器V1.0");
		shlv.setLayout(null);
		
		Label downLoadLevelLabel = new Label(shlv, SWT.NONE);
		downLoadLevelLabel.setBounds(10, 128, 60, 17);
		downLoadLevelLabel.setText("下载层级：");
		
		onlyCurLevel = new Button(shlv, SWT.CHECK);
		onlyCurLevel.setSelection(true);
		onlyCurLevel.setBounds(156, 128, 81, 17);
		onlyCurLevel.setText("只下载该层");
		
		logText = new Text(shlv, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);
		logText.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		logText.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_INACTIVE_FOREGROUND));
		logText.setBounds(10, 271, 516, 145);
		
		level = new Text(shlv, SWT.BORDER);
		level.addVerifyListener(new VerifyListener() {
			public void verifyText(VerifyEvent e) {
				if(e.text.length()>1)return;
				boolean b = "0123456789".indexOf(e.text) >= 0;
				e.doit = b; // doit属性如果为true,则字符允许输入,反之不允许
				return;
			}
		});
		level.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				optionChange();
			}
		});
		level.setBounds(77, 125, 73, 23);
		
		comboSystemMapTypes = new Combo(shlv, SWT.NONE);
		comboSystemMapTypes.setItems(new String[] {"谷歌卫星", "谷歌地理"});
		comboSystemMapTypes.setBounds(113, 49, 149, 25);
		comboSystemMapTypes.select(1);
		
		Label mayTypeLabel = new Label(shlv, SWT.NONE);
		mayTypeLabel.setText("地图类型：");
		mayTypeLabel.setBounds(10, 18, 61, 17);
		
		btnSystemMapType = new Button(shlv, SWT.RADIO);
		btnSystemMapType.setSelection(true);
		btnSystemMapType.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				optionChange();
			}
		});
		btnSystemMapType.setBounds(26, 52, 73, 17);
		btnSystemMapType.setText("系统自带：");
		
		btnUserMapType = new Button(shlv, SWT.RADIO);
		btnUserMapType.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				optionChange();
			}
		});
		
		btnUserMapType.setText("自 定  义：");
		btnUserMapType.setBounds(26, 88, 73, 17);
		
		textUserMapType = new Text(shlv, SWT.BORDER);
		textUserMapType.setText("http://services.arcgisonline.com/ArcGIS/rest/services/World_Imagery/MapServer/tile/${z}/${y}/${x}");
		textUserMapType.setEditable(false);
		textUserMapType.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				optionChange();
			}
		});
		textUserMapType.setBounds(113, 82, 307, 23);
		
		Label threadCountLabel = new Label(shlv, SWT.NONE);
		threadCountLabel.setText("线  程 数：");
		threadCountLabel.setBounds(277, 128, 60, 17);
		
		threadCount = new Text(shlv, SWT.BORDER);
		threadCount.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				optionChange();
			}
		});
		threadCount.addVerifyListener(new VerifyListener() {
			public void verifyText(VerifyEvent e) {
				if(e.text.length()>1)return;
				boolean b = "0123456789".indexOf(e.text) >= 0;
				e.doit = b; // doit属性如果为true,则字符允许输入,反之不允许
				return;
			}
		});
		threadCount.setText("10");
		threadCount.setBounds(347, 125, 73, 23);
		
		progressBar = new ProgressBar(shlv, SWT.NONE);
		progressBar.setBounds(10, 248, 516, 17);
		
		btnStart = new Button(shlv, SWT.NONE);
		btnStart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				startDownLoad();
			}
		});
		btnStart.setEnabled(false);
		btnStart.setFont(SWTResourceManager.getFont("微软雅黑", 9, SWT.BOLD));
		btnStart.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
			}
		});
		btnStart.setBounds(10, 206, 80, 36);
		btnStart.setText("开始");
		
		btnStop = new Button(shlv, SWT.NONE);
		btnStop.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				stopDownLoad();
			}
		});
		btnStop.setEnabled(false);
		btnStop.setFont(SWTResourceManager.getFont("微软雅黑", 9, SWT.BOLD));
		btnStop.setText("暂停下载");
		btnStop.setBounds(96, 206, 80, 36);
		
		btnEnd = new Button(shlv, SWT.NONE);
		btnEnd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				endDownLoad();
			}
		});
		btnEnd.setEnabled(false);
		btnEnd.setFont(SWTResourceManager.getFont("微软雅黑", 9, SWT.BOLD));
		btnEnd.setText("终止");
		btnEnd.setBounds(182, 206, 80, 36);
		
		savePathLabel = new Label(shlv, SWT.NONE);
		savePathLabel.setText("保存路径:");
		savePathLabel.setBounds(10, 169, 60, 17);
		
		savePathText = new Text(shlv, SWT.BORDER);
		savePathText.setText("D:/gmap/test");
		savePathText.setBounds(77, 166, 433, 23);
		
		btnAddMapType = new Button(shlv, SWT.NONE);
		btnAddMapType.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				if(textUserMapType.getText()==null || textUserMapType.getText().trim().equals("null")) {
					//没有路径
					MessageDialog.openInformation(shlv,"系统提示","请输入地址!");
				} else {
					InputDialog inputDialog = new InputDialog(shlv,"系统提示","输入地图类型名称：","地图1",null);
					if(inputDialog.open() == InputDialog.OK){   
					     String value = inputDialog.getValue();
					     if(value != null && !value.trim().equals("")) {
					    	 //保存
					    	 loadConfig(value, textUserMapType.getText());
					     }
					} 
				}
			}
		});
		btnAddMapType.setBounds(426, 80, 80, 27);
		btnAddMapType.setText("添加到系统");
		
		shlv.open();
		loadConfig(null, null);
		DownloadUtil.setMapToolApp(this);
	}
	
	public void setSelection(int value) {
		if(progressBar.getSelection() < mapDownLoadUtil.getTotalTaskCount()) {
			progressBar.setSelection(value);
		} else {
			progressBar.setSelection(mapDownLoadUtil.getTotalTaskCount());
		}
	}
	
	/**
	 * 记录Log
	 */
	public void log(String value) {
		DateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		String logPath = System.getProperty("user.dir") + "\\error.log";
		FileUtil.writeFile(logPath, "["+formatter.format(new Date())+"]:"+value+"\n\t", true);
	}
	/**
	 * 显示信息
	 * @param info
	 */
	public void showInfo(final String info, final boolean isLog) {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				if(logText.getLineCount()>200) {
					//最多显示n行
					logText.setText("");
				}
				logText.append(info+"\n");
				if(isLog) {
					log(info);
				}
			}
		});
	}
}
