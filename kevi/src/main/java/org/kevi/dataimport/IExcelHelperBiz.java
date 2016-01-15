package org.kevi.dataimport;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.List;

/**
 * 
 * @author 737878
 *
 */
public interface IExcelHelperBiz {

	/**
	 * 读取模板
	 * @param path 文件路径
	 * @return
	 */
	IExcelHelperBiz readExcelTemplateByPath(String path);
	
	/**
	 * 读取模板
	 * @param classpath 类路径
	 * @return
	 */
	IExcelHelperBiz readExcelTemplateByClasspath(String classpath);
	
	/**
	 * 添加待导出数据
	 * @param datas
	 * @return
	 */
	IExcelHelperBiz addExcelData(Collection<?> datas);
	
	/**
	 * 添加待导出数据
	 * @param datas
	 * @param mergeFields 需要合并的属性名
	 * @param mergeStrategy 合并规则
	 * @return
	 */
	IExcelHelperBiz addExcelData(Collection<?> datas,String[] mergeFields,MergeStrategy<?> mergeStrategy);
	
	/**
	 * 导入excel文件为集合
	 * @param target 待导入的excel文件
	 * @param clazz 
	 * @return
	 */
	<T> List<T> parseExcel(File target, Class<T> clazz);
	
	public IExcelHelperBiz addSelectItem(String field,String items);
	
	void writeTo(OutputStream out);
	
	void writeTo(File file);
	
	void writeToFile(String fileName);
	
	/**
	 * 导出
	 * @return
	 */
	InputStream export();
}
