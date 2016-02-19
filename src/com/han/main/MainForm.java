package com.han.main;

import java.util.List;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

import com.han.pojo.User;
import com.han.service.IUserService;
import com.han.service.impl.UserServiceImpl;
import com.han.utils.PageResult;
import com.han.utils.ScreenUtils;

import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.internal.PageEventAction;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

/**
 * 主界面
 * 
 * @author Han
 * 
 */
public class MainForm {

	private IUserService userService;

	protected Shell shell;

	private PageResult pageResult = new PageResult();
	
	private Label lblPage;
	/**
	 * 构造函数
	 */
	public MainForm() {
		super();
		userService = new UserServiceImpl();
	}

	private void createPageButton() {
        lblPage = new Label(shell, SWT.NONE);
        lblPage.setFont(SWTResourceManager.getFont("微软雅黑", 10, SWT.NORMAL));
        lblPage.setAlignment(SWT.CENTER);
        lblPage.setBounds(469, 800, 464, 20);
        formToolkit.adapt(lblPage, true, true);
		
        final Button nextPage = formToolkit.createButton(shell, "下一页", SWT.NONE);
        nextPage.setBounds(217, 797, 80, 27);
        
        final Button prePage = formToolkit.createButton(shell, "上一页", SWT.NONE);
        prePage.setBounds(116, 797, 80, 27);
        
        final Button indexPage = new Button(shell, SWT.NONE);
        indexPage.setBounds(10, 797, 80, 27);
        formToolkit.adapt(indexPage, true, true);
        indexPage.setText("首页");
        
        final Button lastPage = new Button(shell, SWT.NONE);
        lastPage.setBounds(316, 797, 80, 27);
        formToolkit.adapt(lastPage, true, true);
        lastPage.setText("尾页");
        
        SelectionListener listener = new SelectionListener(){

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void widgetSelected(SelectionEvent event) {
				// 首先获得表格中所有的行  
                TableItem[] items = table.getItems();  
                // 循环所有行  
                for (int i = items.length - 1; i >= 0; i--)  
                {
                    table.remove(i);
                }
				if(event.widget ==nextPage){
					if(pageResult.getCurPage()<pageResult.getTotalPage()-1)
						pageResult.setCurPage(pageResult.getCurPage()+1);
				}
				else if(event.widget == prePage){
					if(pageResult.getCurPage()>0)
						pageResult.setCurPage(pageResult.getCurPage()-1);
				}
				else if(event.widget == indexPage){
					pageResult = new PageResult();
				}
				else if(event.widget == lastPage){
					pageResult.setCurPage(pageResult.getTotalPage()-1);
				}
				pageResult = userService.getUserListByPage(pageResult);
				final List<User> userList = (List<User>) pageResult.getList();
				for (User user : userList) {
					TableItem item = new TableItem(table, SWT.NONE);
					item.setText(user.toStringArray(user));
				}
				table.setItemCount(userList.size());
				
		        lblPage.setText("共"+pageResult.getTotalPage()+"页    当前"+(pageResult.getCurPage()+1)+"页    共"+pageResult.getTotal()+"条数据    当前页有"+pageResult.getCurTotal()+"条");
			}
        };
        
        nextPage.addSelectionListener(listener);
        prePage.addSelectionListener(listener);
        indexPage.addSelectionListener(listener);
        lastPage.addSelectionListener(listener);
	}

	private void createToolBar() {
		ToolBar toolBar = new ToolBar(shell, SWT.FLAT | SWT.RIGHT);
		toolBar.setBounds(4, 5, ScreenUtils.getScreenWidth()-50, 25);
		
        final ToolItem del = new ToolItem(toolBar, SWT.PUSH);  
        del.setText("删除"); 
//        del.setImage(new Image(toolBar.getDisplay(), "image//delete.png"));  

        final ToolItem edit = new ToolItem(toolBar, SWT.PUSH);  
        edit.setText("编辑"); 
        
//        del.setImage(new Image(toolBar.getDisplay(), "image//delete.png"));  
        
        // 工具栏按钮事件处理  
        Listener listener = new Listener()  
        {  
            @Override  
            public void handleEvent(Event event)  
            {
                // 如果单击删除按钮  
                if (event.widget == del)  
                {
                    // 首先获得表格中所有的行  
                    TableItem[] items = table.getItems();  
                    // 循环所有行  
                    for (int i = items.length - 1; i >= 0; i--)  
                    {
                        // 如果该行没有被选中，继续循环  
                        if (!items[i].getChecked())  
                            continue;  
                        // 否则选中，查找该表格中是否有该行  
                        int index = table.indexOf(items[i]);  
                        // 如果没有该行，继续循环  
                        if (index < 0)  
                            continue;  
                        // 如果有该行，删除该行 
                        // items[index].dispose();
                        if(userService.delUserById(items[i].getText()) == 1){
                        	table.remove(index);
                        }
                    }
                }
                //单击修改按钮
                else if(event.widget == edit){
                	
                }
            }  
  
        };  
        // 为工具栏的按钮注册事件  
        del.addListener(SWT.Selection, listener);
        
	}

	/**
	 * Launch the application.
	 * 
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
		shell.setSize(ScreenUtils.getScreenWidth(),
				ScreenUtils.getScreenHeight());
		shell.setText("无敌");
		createViewForm();
	}

	private ViewForm viewForm = null;
	private Composite composite = null;
	private Table table;
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());

	/**
	 * 1.创建ViewFrom
	 */
	private void createViewForm() {
		viewForm = new ViewForm(shell, SWT.NONE);
		viewForm.setBounds(0, 30, ScreenUtils.getScreenWidth(),
				ScreenUtils.getScreenHeight()-100);

		
		createPageButton();
		
		createComposite();
		viewForm.setContent(composite);
		
		createToolBar();
		
		
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

		table = new Table(composite, SWT.MULTI | SWT.FULL_SELECTION | SWT.CHECK);
//		table = new Table(shell, SWT.BORDER| SWT.VIRTUAL);//注意此处的设置
		table.setLayoutData(gridData);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setBounds(0, 0, ScreenUtils.getScreenWidth(),
				ScreenUtils.getScreenHeight()-100);

		final TableColumn newColumnTableColumn_ID = new TableColumn(table,
				SWT.NONE);
		newColumnTableColumn_ID.setWidth(ScreenUtils.getCellWidth());
		newColumnTableColumn_ID.setText("ID");

		final TableColumn newColumnTableColumn_NUM = new TableColumn(table,
				SWT.NONE);
		newColumnTableColumn_NUM.setWidth(ScreenUtils.getCellWidth());
		newColumnTableColumn_NUM.setText("编号");

		final TableColumn newColumnTableColumn_NAME = new TableColumn(table,
				SWT.NONE);
		newColumnTableColumn_NAME.setWidth(ScreenUtils.getCellWidth());
		newColumnTableColumn_NAME.setText("姓名");

		final TableColumn newColumnTableColumn_AGE = new TableColumn(table,
				SWT.NONE);
		newColumnTableColumn_AGE.setWidth(ScreenUtils.getCellWidth());
		newColumnTableColumn_AGE.setText("年龄");

		final TableColumn newColumnTableColumn_GARDE = new TableColumn(table,
				SWT.NONE);
		newColumnTableColumn_GARDE.setWidth(ScreenUtils.getCellWidth());
		newColumnTableColumn_GARDE.setText("等级");

		final TableColumn newColumnTableColumn_SEX = new TableColumn(table,
				SWT.NONE);
		newColumnTableColumn_SEX.setWidth(ScreenUtils.getCellWidth());
		newColumnTableColumn_SEX.setText("性别");

		final TableColumn newColumnTableColumn_MONEY = new TableColumn(table,
				SWT.NONE);
		newColumnTableColumn_MONEY.setWidth(ScreenUtils.getCellWidth());
		newColumnTableColumn_MONEY.setText("金额");

		
		
		pageResult = userService.getUserListByPage(pageResult);
		final List<User> userList = (List<User>) pageResult.getList();
		for (User user : userList) {
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(user.toStringArray(user));
		}
		
        lblPage.setText("共"+pageResult.getTotalPage()+"页    当前"+(pageResult.getCurPage()+1)+"页    共"+pageResult.getTotal()+"条数据    当前页有"+pageResult.getCurTotal()+"条");
	}
}
