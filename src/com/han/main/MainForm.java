package com.han.main;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
/**
 * 
 * @author Han
 *
 */
public class MainForm {

	protected Shell shell;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MainForm window = new MainForm();
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
		shell.setSize(900, 600);
		shell.setText("无敌");
		createViewForm();
	}
	private ViewForm viewForm = null;
	private Composite composite = null;
	private Table table;
	/**
	 * 1.创建ViewFrom
	 */
	private void createViewForm() {
		viewForm = new ViewForm(shell, SWT.NONE);
		viewForm.setBounds(0, 50, 880, 500);
		
		createComposite();  
        viewForm.setContent(composite);
	}
	/**
	 * 2.创建Composite
	 */
	private void createComposite() {
		GridLayout gridLayout = new GridLayout();  
        gridLayout.numColumns = 1;   
        
		composite = new Composite(viewForm, SWT.NONE);
		composite.setLayout(gridLayout); 
		
		createTable();
	}
	/**
	 * 3.创建表格
	 */
	private void createTable() {
        // 表格布局  
        GridData gridData = new org.eclipse.swt.layout.GridData();  
        gridData.horizontalAlignment = SWT.FILL;  
        gridData.grabExcessHorizontalSpace = true;  
        gridData.grabExcessVerticalSpace = true;  
        gridData.verticalAlignment = SWT.FILL;  
  
        // 创建表格，使用SWT.FULL_SELECTION样式，可同时选中一行  
        table = new Table(composite, SWT.MULTI | SWT.FULL_SELECTION | SWT.CHECK);  
        table.setHeaderVisible(true);// 设置显示表头  
        table.setLayoutData(gridData);// 设置表格布局  
        table.setLinesVisible(true);// 设置显示表格线/*  
        // 创建表头的字符串数组  
        String[] tableHeader = {"姓名", "性别", "电话", "电子邮件"};  
        for (int i = 0; i < tableHeader.length; i++)  
        {  
            TableColumn tableColumn = new TableColumn(table, SWT.NONE);  
            tableColumn.setText(tableHeader[i]);  
            // 设置表头可移动，默认为false  
            tableColumn.setMoveable(true);  
        }  
        // 添加三行数据  
        TableItem item = new TableItem(table, SWT.NONE);  
        item.setText(new String[]{"张三", "男", "123", ""});  
        // 设置图标  
        // item.setImage( ImageFactory.loadImage(  
        // table.getDisplay(),ImageFactory.ICON_BOY));  
  
        for (int i = 0; i < 5; i++)  
        {  
            item = new TableItem(table, SWT.NONE);  
            item.setText(new String[]{"李四", "男", "4582", ""});  
        }  
        // 设置图标  
        // item.setImage( ImageFactory.loadImage(  
        // table.getDisplay(),ImageFactory.ICON_BOY));  
  
        item = new TableItem(table, SWT.NONE);  
        item.setText(new String[]{"周五", "女", "567", ""});  
        // 设置图标  
        // item.setImage( ImageFactory.loadImage(  
        // table.getDisplay(),ImageFactory.ICON_GIRL));  
	}
	
	
}
