package com.han.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import com.han.pojo.User;
import com.han.service.IUserService;
import com.han.service.impl.UserServiceImpl;

public class ExcelUtils {

	private static IUserService userService;
	
	private static DecimalFormat df = new DecimalFormat("0");  
	
	public static void insertExcel(Shell shell,String fileName) {

		InputStream in = null;

		try {
			in = new FileInputStream(fileName);
			User u = null;
			List<User> userList = new ArrayList<>();
			if (isExcel2007(new FileInputStream(fileName))) {
				XSSFWorkbook xw = new XSSFWorkbook(in);
				XSSFSheet sheet = xw.getSheetAt(0);
				XSSFRow row;
				for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
					row = sheet.getRow(i);
					u = new User();
					u.setUser_num(row.getCell(0).toString());
					u.setUser_name(row.getCell(1).toString());
					u.setUser_age(row.getCell(2).toString());
					u.setUser_grade(df.format(row.getCell(3).getNumericCellValue()));
					u.setUser_sex(df.format(row.getCell(4).getNumericCellValue()));
					u.setUser_money(row.getCell(5).toString());
					userList.add(u);
				}
			} else {
				POIFSFileSystem fs = new POIFSFileSystem(in);
				// 得到Excel工作簿对象
				HSSFWorkbook wb = new HSSFWorkbook(fs);
				// 得到Excel工作表对象
				HSSFSheet sheet = wb.getSheetAt(0);
				// 获取总行数
				HSSFRow row = null;
				for(int i=1;i<sheet.getPhysicalNumberOfRows();i++){
					row = sheet.getRow(i);
					u = new User();
					u.setUser_num(row.getCell(0).toString());
					u.setUser_name(row.getCell(1).toString());
					u.setUser_age(row.getCell(2).toString());
					u.setUser_grade(df.format(row.getCell(3).getNumericCellValue()));
					u.setUser_sex(df.format(row.getCell(4).getNumericCellValue()));
					u.setUser_money(row.getCell(5).toString());
					userList.add(u);
				}
			}
			
			System.out.println(userList);
			String message = "";
			userService = new UserServiceImpl();
			message = userService.addUserList(userList);
			 MessageBox dialog=new MessageBox(shell,SWT.OK|SWT.ICON_INFORMATION);
             dialog.setText("无敌提示");
             dialog.setMessage(message);
             dialog.open();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != in)
					in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 判断文件是否是2007版本
	 * 
	 * @param is
	 * @return
	 */
	public static boolean isExcel2007(InputStream is) {
		try {
			new XSSFWorkbook(is);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

}
