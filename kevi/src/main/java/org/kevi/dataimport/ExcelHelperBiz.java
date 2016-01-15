package org.kevi.dataimport;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ConditionalFormatting;
import org.apache.poi.ss.usermodel.ConditionalFormattingRule;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.SheetConditionalFormatting;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;

/**
 * 
 * @author 737878
 * 
 */
public class ExcelHelperBiz implements IExcelHelperBiz {
	private final static String DATA_LINE_FLAG = "\\$\\{[A-Za-z]\\w*\\}";
	private Workbook workbook;
	private Sheet sheet;
	private Row curRow;
	private int lastRowIndex;
	private int initRowIndex; 
	private int initColumnIndex;
	private int curRowIndex;//表示当前光标位置
	private int curColumnIndex;
	private boolean hasTail = false;//是否有表尾
	private List<?> models;
	private List<String> expressions;//单元格内的表达式
	private List<CellStyle> styles;//每列的样式
	private float lineHeight;//行高
	@SuppressWarnings("rawtypes")
	private MergeStrategy mergeStrategy;//合并策略
	private String[] mergeFields;//合并字段
	
	/**
	 * 加载模板
	 * @param path
	 */
	public IExcelHelperBiz readExcelTemplateByPath(String path){
		try {
			workbook = WorkbookFactory.create(new File(path));
			sheet = workbook.getSheetAt(0);
			lastRowIndex = sheet.getLastRowNum();
			this.initExcelTemplate();
		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return this;//链式调用
	}
	
	public IExcelHelperBiz readExcelTemplateByClasspath(String classpath){
		InputStream in = this.getClass().getClassLoader().getResourceAsStream(classpath);
		try {
			workbook = WorkbookFactory.create(in);
			sheet = workbook.getSheetAt(0);
			lastRowIndex = sheet.getLastRowNum();
			this.initExcelTemplate();
		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(in != null)
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return this;
	}
	
	public IExcelHelperBiz addExcelData(Collection<?> datas){
		List<Object> list = new ArrayList<Object>();
		list.addAll(datas);
		this.models = list;
		return this;
	}
	
	/**
	 * 添加数据,并设置合并规则
	 * @param datas
	 * @param mergeStrategy
	 * @param mergeFields
	 */
	public IExcelHelperBiz addExcelData(Collection<?> datas,String[] mergeFields,MergeStrategy<?> mergeStrategy){
		this.addExcelData(datas);
		this.mergeFields = mergeFields;
		this.mergeStrategy = mergeStrategy;
		return this;
	}
	
	private Map<String,String[]> selects;
	//添加数据下拉列表限制
	//poi无法读取模板的数据有效性,故此处只能手动设置
	public IExcelHelperBiz addSelectItem(String field,String items){
		if(selects == null)
			selects = new HashMap<String,String[]>();
		String[] textlist = items.split(",");
		selects.put(field, textlist);
		return this;
	}
	
	private void setSelectItems(){
		if(selects == null || this.models == null || this.models.size() == 0)
			return;
		for(int i = 0; i < expressions.size(); i++){
			String field = expressions.get(i).replace("${", "").replace("}", "").trim();
			String[] textlist = selects.get(field);
			if(textlist == null || textlist.length == 0)
				continue;
			DataValidationHelper helper = sheet.getDataValidationHelper();
			DataValidationConstraint dvc = helper.createExplicitListConstraint(textlist);
			int firstRow = initRowIndex;
			int lastRow = initRowIndex + models.size() - 1;
			int firstCol = initColumnIndex + i;
			DataValidation dv = helper.createValidation(dvc, new CellRangeAddressList(firstRow, lastRow, firstCol, firstCol));
			dv.setShowErrorBox(true);
			sheet.addValidationData(dv);			
		}
	}
	
	/**
	 * 初始化模板,找到初始行列索引;
	 * 获取每列的${field}表达式以及每列的样式
	 */
	private void initExcelTemplate() {
		expressions = new ArrayList<String>();
		styles = new ArrayList<CellStyle>();
		boolean findData = false;
		for(Row row : sheet){
			if(findData)
				break;
			for(Cell cell : row){
				if(cell.getCellType() != Cell.CELL_TYPE_STRING)
					continue;
				if(cell.getStringCellValue().matches(DATA_LINE_FLAG)){
					initColumnIndex = cell.getColumnIndex();
					initRowIndex = cell.getRowIndex();
					curColumnIndex = initColumnIndex;
					curRowIndex = initRowIndex - 1;//光标初始位置在数据行前
					findData = true;
					break;
				}
			}
		}
		if(lastRowIndex > initRowIndex)
			this.hasTail = true;//有表尾
		Row initRow = sheet.getRow(initRowIndex);
		lineHeight = initRow.getHeightInPoints();//行高
		for(Cell cc : initRow){
			styles.add(cc.getCellStyle());	
			if(Cell.CELL_TYPE_BLANK == cc.getCellType())
				expressions.add("");
			else 
				expressions.add(cc.getStringCellValue().trim());//初始化单元格表达式
		}		
	}
	
	/**
	 * 填充单元格
	 * @param cell 待填充的单元格
	 * @param model 
	 * @param columnExp
	 */
	private void fillCellValue(Cell cell,Object model,String columnExp){
		if(!columnExp.matches(DATA_LINE_FLAG))
			return;//防止模板中initRow某些单元格不是${xxx}
		
		//#{username}
		String fieldName = columnExp.replace("${", "").replace("}", "").trim();
		//getUsername
		String methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		Object result = null;
		String returnType = null;
		try {
			Method method = null;
			Method[] methods = model.getClass().getMethods();
			for(Method m : methods){
				if( m.getName().equals(methodName)){
					method = m;
					break;
				}
				//BUG更正:如果实体类中的getter返回的是"boolean",那么该getter方法一般是isXxx的形式
				if( m.getName().matches("is\\w+") && "boolean".equals(m.getReturnType().getName()) ){
					String temp = m.getName().replace("is", "");
					temp = temp.substring(0, 1).toLowerCase()+ temp.substring(1);
					if(temp.equals(fieldName)){
						method = m;
						break;
					}
				}
			}
			if(method == null)//找不到对应的setter不做处理
				return;
			returnType = method.getReturnType().getName();
			result = method.invoke(model, new Object[]{});
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		if(result != null){
			if("int".equals(returnType)||"java.lang.Integer".equals(returnType)
					||"long".equals(returnType)||"java.lang.Long".equals(returnType)
					||"short".equals(returnType)||"java.lang.Short".equals(returnType)
					||"byte".equals(returnType)||"java.lang.Byte".equals(returnType)
					||"float".equals(returnType)||"java.lang.Float".equals(returnType)
					||"double".equals(returnType)||"java.lang.Double".equals(returnType)){
				double d = Double.parseDouble(result.toString());
				cell.setCellValue(d);
			} else if("java.lang.String".equals(returnType) || "char".equals(returnType)||"java.lang.Character".equals(returnType)){
				cell.setCellValue(result.toString());
			} else if("java.util.Date".equals(returnType)){
				Date date = (Date)result;
				cell.setCellValue(date);
			} else if("boolean".equals(returnType)||"java.lang.Boolean".equals(returnType)){
				boolean b = Boolean.valueOf(result.toString());
				cell.setCellValue(b);
			} else if("java.util.Calendar".equals(returnType)){
				Calendar calendar = (Calendar) result;
				cell.setCellValue(calendar);
			}else{
				cell.setCellValue(result.toString());
			}
		} 
	}
	
	/**
	 * 创建单元格
	 * @param model
	 * @param exp
	 * @param style
	 */
	private void createCell(Object model,String exp,CellStyle style){
		Cell cell = curRow.createCell(curColumnIndex++);
		cell.setCellStyle(style);
		this.fillCellValue(cell, model, exp);
	}
	
	/**
	 * 创建新的一行
	 */
	private void createRow(){
		curRowIndex++;//光标下移
		if(hasTail && curRowIndex != initRowIndex){
			sheet.shiftRows(curRowIndex, lastRowIndex++, 1, true, true);//表尾下移
		}
		curRow = sheet.createRow(curRowIndex);
		//复制行高：
		curRow.setHeightInPoints(lineHeight);
		curColumnIndex = initColumnIndex;
	}
	
	/**
	 * 列表生成
	 */
	private void createRows(){
		if(this.models == null || this.models.size() == 0){
			this.sheet.removeRow(sheet.getRow(initRowIndex));
			return;
		}
		//遍历models:
		for(Object model : models){
			//创建一行
			this.createRow();
			for(int i = 0; i < expressions.size(); i++){
				String exp = expressions.get(i);
				CellStyle style = styles.get(i);
				this.createCell(model, exp,style);
			}
		}		
	}
	
	/**
	 * 合并
	 */
	@SuppressWarnings("unchecked")
	private void mergeRows(){
		if(this.mergeFields == null || this.mergeFields.length == 0)
			return;
		if(this.models == null || this.models.size() < 2)
			return;
		int start = 0;//起始位置
		for(int i = 0; i < models.size(); i++){
			Object model = models.get(i);
			if(i > 0 && !this.mergeStrategy.compare(models.get(start), model)){
				this.mergeRow(start,i-1);
				start = i;
			} else if (i == models.size() - 1){
				this.mergeRow(start,i);
			}
		}
	}
	
	//合并单元格
	private void mergeRow(int start, int end) {
		for(int i = 0; i < this.mergeFields.length; i++){
			String str = "${"+this.mergeFields[i]+"}";
			if(!this.expressions.contains(str))
				continue;
			int column = this.expressions.indexOf(str);
			CellRangeAddress cra=new CellRangeAddress(initRowIndex+start, initRowIndex+end, initColumnIndex+column, initColumnIndex+column); 
			sheet.addMergedRegion(cra);
		}
	}
	
	/**
	 * 条件格式
	 */
	private void setConditionalFormatting(){
		SheetConditionalFormatting scf = sheet.getSheetConditionalFormatting();
		if(scf.getNumConditionalFormattings() < 1 || this.models == null || this.models.size() < 2)
			return;
		//遍历当前sheet中的所有条件格式
		int count = scf.getNumConditionalFormattings();
		for(int i = 0; i < count; i++){
			ConditionalFormatting cf = scf.getConditionalFormattingAt(i);//条件格式
			CellRangeAddress cra = cf.getFormattingRanges()[0];
			cra.setLastRow(cra.getLastRow() + this.models.size() - 1);//改变范围
			for(int j = 0; j < cf.getNumberOfRules(); j++){
				ConditionalFormattingRule rule = cf.getRule(j);//条件
				scf.addConditionalFormatting(new CellRangeAddress[]{cra}, rule);
			}
		}
		for(int i = 0; i < count; i++){
			scf.removeConditionalFormatting(0);
		}
	}
	
	/**
	 * 函数,求和,平均,总数,最大,最小
	 */
	private void setCellFormulas(){
		for(Row row : sheet){
			if(row.getRowNum() >= initRowIndex && row.getRowNum() < (models.size()+initRowIndex) )
				continue;//遍历sheet的时候跳过非数据行
			for(Cell cell : row){
				if(cell.getCellType() == Cell.CELL_TYPE_FORMULA){
					String formula = cell.getCellFormula();
					if(formula.startsWith("SUM") || formula.startsWith("AVERAGE") || formula.startsWith("COUNT")
							|| formula.startsWith("MAX") || formula.startsWith("MIN")){
						String param = formula.substring(formula.indexOf("(")+1, formula.lastIndexOf(")"));
						String prefix = param.replaceAll("\\d+", "");
						formula = formula.replace(param, param+":"+prefix+(initRowIndex+models.size()));
						cell.setCellFormula(formula);
					}
				}
			}
		}		
	}
	
	public <T> List<T> parseExcel(File target, Class<T> clazz){
		int tailLength = 0;//表尾行数
		if(this.hasTail)
			tailLength = this.lastRowIndex - this.initRowIndex;		
		List<T> models = new ArrayList<T>();
		Workbook tbook = null;
		try {
			tbook = WorkbookFactory.create(target);
			Sheet tsheet = tbook.getSheetAt(0);
			for(int i = initRowIndex; i <= tsheet.getLastRowNum() - tailLength; i++){
				Row row = tsheet.getRow(i);
				T model = this.buildModel(row, clazz);
				models.add(model);
			}
		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			e.printStackTrace();
		}finally {
			try {
				if(tbook != null)
				tbook.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return models;		
	}
	
	/**
	 * 根据一行结果创建一个model
	 * @param row
	 * @param clazz
	 * @return
	 */
	private <T> T buildModel(Row row,Class<T> clazz){
		T model = null;
		try {
			model = clazz.newInstance();
			for(int j = initColumnIndex; j < this.expressions.size()+initColumnIndex;j++){
				Cell cell = row.getCell(j);
				if(cell==null || cell.getCellType() == Cell.CELL_TYPE_BLANK)
					continue;
				String exp = this.expressions.get(j - initColumnIndex);
				String fieldName = exp.replace("${", "").replace("}", "").trim();
				String methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
				Method[] methods = clazz.getMethods();
				//查找这个set方法
				for(Method method : methods){
					if(!method.getName().equals(methodName))
						continue;
					Class<?>[] classes = method.getParameterTypes();
					if(classes.length != 1)
						continue;
					Class<?> c = classes[0];
					//判断set方法的参数类型
					String parameterType = c.getName();
					Object param = null;
					boolean invalidSetter = false;
					try{
						if(Cell.CELL_TYPE_BLANK == cell.getCellType()
								|| "".equals(cell.toString().trim()))
							continue;
						
						if(parameterType.equals("java.lang.String")){
							if(Cell.CELL_TYPE_STRING == cell.getCellType())
								param = cell.getStringCellValue().trim();
							else if(Cell.CELL_TYPE_NUMERIC == cell.getCellType()){
								//bug:
								//如果一个单元格中的文本全部为数字,除非在前面加上'号,否则excel会将该单元格的类型识别为数字类型,
								//而数字单元格(CELL_TYPE_NUMERIC)在poi中只能提取为double值(cell.getNumericCellValue),如123,会得到123.0
								//所以,必须判断是否有小数位,1)如果没有小数位转换为long
								//2)有小数位,必须先求出整数位,拼接.号,在求出小数位拼接
								double d = cell.getNumericCellValue();
								param = (long)d + "";//暂时这样处理
							}
							else
								param = cell.toString();
						} else if(parameterType.equals("java.lang.Double") || parameterType.equals("double")) {
							param = cell.getNumericCellValue();
						} else if(parameterType.equals("java.lang.Float") || parameterType.equals("float")) {
							double d = cell.getNumericCellValue();
							param = (float)d;
						} else if(parameterType.equals("java.lang.Long") || parameterType.equals("long")) {
							double d = cell.getNumericCellValue();
							param = (long)d;
						} else if(parameterType.equals("java.lang.Integer") || parameterType.equals("int")) {
							double d = cell.getNumericCellValue();
							param = (int)d;
						} else if(parameterType.equals("java.lang.Short") || parameterType.equals("short")) {
							double d = cell.getNumericCellValue();
							param = (short)d;
						} else if(parameterType.equals("java.lang.Byte") || parameterType.equals("byte")) {
							double d = cell.getNumericCellValue();
							param = (byte)d;
						} else if(parameterType.equals("java.lang.Boolean") || parameterType.equals("boolean")) {
							param = Boolean.parseBoolean(cell.getStringCellValue());
						} else if(parameterType.equals("java.lang.Character") || parameterType.equals("char")) {
							param = cell.getStringCellValue().charAt(0);
						} else if(parameterType.equals("java.util.Date")) {
							param = cell.getDateCellValue();
						} else if(parameterType.equals("java.util.Calendar")) {
							Date d = cell.getDateCellValue();
							Calendar cc = Calendar.getInstance();
							cc.setTime(d);
							param = cc;
						} else {
							invalidSetter = true;//发现了无效setter
							throw new RuntimeException();
						}
					} catch(Exception e){
						if(invalidSetter)
							throw new RuntimeException("不支持的setter:"+methodName);
						else
							throw new RuntimeException("数据解析错误:"+(row.getRowNum()+1) +"行,"+(j+1)+"列,"+"错误的值:"+cell.toString());
					}
					method.invoke(model,param);
				}
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return model;		
	}
	
	/**
	 * 导出为输入流
	 * @return
	 */
	public InputStream export(){
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		this.writeTo(out);
		return new ByteArrayInputStream(out.toByteArray()) ;//输出流转输入流
	}
	
	/**
	 * 写到一个输出流
	 */
	public void writeTo(OutputStream out){
		this.createRows();//生成excel表格
		this.setConditionalFormatting();//设置格式
		this.mergeRows();//处理需要合并的
		this.setCellFormulas();//设置函数
		this.setSelectItems();//设置数据有效性下拉选择框
		try {
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 写到文件
	 */
	public void writeTo(File file){
		this.createRows();
		this.setConditionalFormatting();
		this.mergeRows();
		this.setCellFormulas();
		this.setSelectItems();
		OutputStream out = null;
		try {
			out = new BufferedOutputStream(new FileOutputStream(file));
			workbook.write(out);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(out != null)
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}		
	}
	
	/**
	 * 写到文件
	 * @param fileName
	 */
	public void writeToFile(String fileName){
		File target = new File(fileName);
		this.writeTo(target);
	}	
}