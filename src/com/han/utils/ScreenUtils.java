package com.han.utils;

/**
 * 电脑屏幕相关工具类
 * @author Han
 *
 */
public class ScreenUtils {
	/**
	 * 获取电脑屏幕的宽
	 * @return
	 */
	public static int getScreenWidth(){
		 return (int) (java.awt.Toolkit.getDefaultToolkit().getScreenSize().width*Constrats.WINDOW_SIZE);
	}
	/**
	 * 获取电脑屏幕的高
	 * @return
	 */
	public static int getScreenHeight(){
		return (int) (java.awt.Toolkit.getDefaultToolkit().getScreenSize().height*Constrats.WINDOW_SIZE);
	}
	/**
	 * 获取单个单元格的宽度
	 * @return
	 */
	public static int getCellWidth(){
		return getScreenWidth()/Constrats.CELL_COUNT-4;
	}
}
