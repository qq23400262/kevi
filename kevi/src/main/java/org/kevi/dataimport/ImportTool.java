package org.kevi.dataimport;

import java.io.File;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class ImportTool {

	protected Shell shell;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ImportTool window = new ImportTool();
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
		shell.setSize(450, 300);
		shell.setText("SWT Application");
		
		Button btnImport = new Button(shell, SWT.NONE);
		btnImport.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				importExcel();
			}
		});
		btnImport.setBounds(10, 10, 80, 27);
		btnImport.setText("Import");
	}
	
	public void importExcel() {
		IExcelHelperBiz excelHelper = new ExcelHelperBiz();
		excelHelper.readExcelTemplateByPath("d:\\user\\422575\\桌面\\fq\\tpl.xlsx");
		List<ExcelData> list = excelHelper.parseExcel(new File("d:\\user\\422575\\桌面\\fq\\jisuanzhi.xlsx"), ExcelData.class);
		for (ExcelData excelData : list) {
			System.out.print(excelData+",");
		}
		
	}
}
