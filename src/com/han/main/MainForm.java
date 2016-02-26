package com.han.main;

import java.util.List;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

import com.han.pojo.User;
import com.han.service.IUserService;
import com.han.service.impl.UserServiceImpl;
import com.han.utils.PageResult;
import com.han.utils.ScreenUtils;

import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Combo;

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
		lblPage = new Label(shell, SWT.SHADOW_IN | SWT.CENTER);
		lblPage.setFont(SWTResourceManager.getFont("微软雅黑", 10, SWT.NORMAL));
		lblPage.setAlignment(SWT.CENTER);
		lblPage.setBounds((int) (ScreenUtils.getScreenWidth() * 0.71), 16,
				(int) (ScreenUtils.getScreenWidth() * 0.28), 20);
	}

	/**
	 * 创建工具栏
	 */
	private void createToolBar() {
		ToolBar toolBarMenu = new ToolBar(shell, SWT.FLAT);
		toolBarMenu.setBounds(10, 5, ScreenUtils.getScreenWidth() / 4, 50);

		final ToolItem del = new ToolItem(toolBarMenu, SWT.PUSH);
		del.setImage(new Image(toolBarMenu.getDisplay(), "image//delete.png"));
		del.setText("删  除");

		final ToolItem seperator_1 = new ToolItem(toolBarMenu, SWT.NONE);
		seperator_1.setEnabled(false);
		seperator_1.setText(" ");
		seperator_1.setWidth(5);

		final ToolItem edit = new ToolItem(toolBarMenu, SWT.PUSH);
		edit.setText("编  辑");
		edit.setImage(new Image(toolBarMenu.getDisplay(), "image//edit.png"));

		final ToolItem seperator_2 = new ToolItem(toolBarMenu, SWT.NONE);
		seperator_2.setEnabled(false);
		seperator_2.setText(" ");
		seperator_2.setWidth(15);

		final ToolItem check = new ToolItem(toolBarMenu, SWT.PUSH);
		check.setText("查看关系");
		check.setImage(new Image(toolBarMenu.getDisplay(),
				"image//relationship.png"));

		final ToolItem seperator_3 = new ToolItem(toolBarMenu, SWT.NONE);
		seperator_3.setEnabled(false);
		seperator_3.setText(" ");
		seperator_3.setWidth(15);

		final ToolItem add = new ToolItem(toolBarMenu, SWT.PUSH);
		add.setText("导入Excel");
		add.setImage(new Image(toolBarMenu.getDisplay(), "image//import.png"));

		final ToolItem seperator_8 = new ToolItem(toolBarMenu, SWT.NONE);
		seperator_8.setEnabled(false);
		seperator_8.setText(" ");
		seperator_8.setWidth(15);

		final ToolItem refresh = new ToolItem(toolBarMenu, SWT.PUSH);
		refresh.setText("刷新");
		refresh.setImage(new Image(toolBarMenu.getDisplay(),
				"image//refresh.png"));

		// 工具栏按钮事件处理
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				// 如果单击删除按钮
				if (event.widget == del) {
					int count = 0;
					// 首先获得表格中所有的行
					TableItem[] items = table.getItems();
					// 循环所有行
					for (int i = items.length - 1; i >= 0; i--) {
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
						if (userService.delUserById(items[i].getText()) == 1) {
							table.remove(index);
							count++;
						}
					}
					if (count == 0) {
						MessageBox dialog = new MessageBox(shell, SWT.OK
								| SWT.ICON_INFORMATION);
						dialog.setText("无敌提示");
						dialog.setMessage("请勾选需要删除的记录！");
						dialog.open();
					} else {
						addTableData();
					}
				}
				// 单击修改按钮
				else if (event.widget == edit) {
					// 首先获得表格中所有的行
					TableItem[] items = table.getItems();
					// 循环所有行
					for (int i = items.length - 1; i >= 0; i--) {
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
						try {
							String firstInfo = items[index].getText(0);
							Display display = Display.getDefault();
							EditForm shell = new EditForm(display, firstInfo);
							shell.open();
							shell.layout();

							while (!shell.isDisposed()) {
								if (!display.readAndDispatch()) {
									display.sleep();
								}
							}

						} catch (Exception e) {
						}
					}
					addTableData();
				}
				//单击查看关系
				else if (event.widget == check) {
					try {
						Display display = Display.getDefault();
						TreeForm shell = new TreeForm(display);
						shell.open();
						shell.layout();
						while (!shell.isDisposed()) {
							if (!display.readAndDispatch()) {
								display.sleep();
							}
						}
					} catch (Exception e) {
					}
				} else if (event.widget == add) {
					// 打开对话框
					ImportForm imp = new ImportForm(shell);
					imp.open();
				}

				else if (event.widget == refresh) {
					addTableData();
				}
			}
		};
		// 为工具栏的按钮注册事件
		del.addListener(SWT.Selection, listener);
		add.addListener(SWT.Selection, listener);
		check.addListener(SWT.Selection, listener);
		refresh.addListener(SWT.Selection, listener);
		edit.addListener(SWT.Selection, listener);

		final Combo comboSearch;
		comboSearch = new Combo(shell, SWT.NONE | SWT.READ_ONLY);
		comboSearch
				.setBounds(ScreenUtils.getScreenWidth() / 4 + 20, 16, 60, 25);
		comboSearch
				.setItems(new String[] { "编号", "姓名", "年龄", "等级", "性别", "金额" });
		comboSearch.select(1);

		textSearch = new Text(shell, SWT.BORDER | SWT.SEARCH);
		textSearch.setText(comboSearch.getText());
		textSearch
				.setBounds(ScreenUtils.getScreenWidth() / 4 + 80, 16, 100, 25);
		textSearch.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent arg0) {
				if (textSearch.getText().toString().trim().equals("")) {
					textSearch.setText(comboSearch.getText());
				}
			}

			@Override
			public void focusGained(FocusEvent arg0) {
				if (textSearch.getText().toString().trim()
						.equals(comboSearch.getText())) {
					textSearch.setText("");
				}
			}
		});

		comboSearch.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				textSearch.setText(comboSearch.getText());
				table.setFocus();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		textSearch.addTraverseListener(new TraverseListener() {
			@Override
			public void keyTraversed(TraverseEvent e) {
				if (e.keyCode == 13 || e.keyCode == 16777296) {
					e.doit = true;
					// 首先获得表格中所有的行
					TableItem[] items = table.getItems();
					// 循环所有行
					for (int i = items.length - 1; i >= 0; i--) {
						table.remove(i);
					}
					String[] strArray;
					User u = new User();
					switch (comboSearch.getSelectionIndex()) {// "编号","姓名","年龄","等级","性别","金额"
					case 0:
						u.setUser_num(textSearch.getText().toString());
						break;
					case 1:
						u.setUser_name(textSearch.getText().toString());
						break;
					case 2:
						u.setUser_age(textSearch.getText().toString());
						break;
					case 3:
						u.setUser_grade(textSearch.getText().toString());
						break;
					case 4:
						if ("男".equals(textSearch.getText().toString()))
							u.setUser_sex("0");
						else
							u.setUser_sex("1");
						break;
					case 5:
						u.setUser_money(textSearch.getText().toString());
						break;
					default:
						break;
					}
					if (!"".equals(textSearch.getText().toString().trim())) {
						List<User> userList = userService.getUserByUser(u);
						for (User user : userList) {
							TableItem item = new TableItem(table, SWT.NONE);
							strArray = user.toStringArray(user);
							User pUser = userService.getUserById(strArray[1]);
							if (!user.getPid().equals("0")) {
								strArray[1] = pUser.getUser_name();
							}
							item.setText(strArray);
						}
						table.setItemCount(userList.size());

						lblPage.setText("");
					} else {
						addTableData();
					}
				}
			}
		});

		final Combo combo = new Combo(shell, SWT.NONE | SWT.READ_ONLY);
		combo.setBounds(ScreenUtils.getScreenWidth() / 2 - 80, 16, 60, 25);
		formToolkit.adapt(combo);
		formToolkit.paintBordersFor(combo);
		combo.setItems(new String[] { "10", "20", "30", "40", "50" });
		combo.select(1);
		combo.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				pageResult.setSize(Integer.valueOf(combo.getText()));
				pageResult.setCurPage(0);
				addTableData();

				table.setFocus();

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {

			}
		});
		// ==============================================分页工具栏====================================================================================

		ToolBar toolBarPage = new ToolBar(shell, SWT.FLAT);
		toolBarPage.setBounds(ScreenUtils.getScreenWidth() / 2, 5,
				ScreenUtils.getScreenWidth() / 4, 50);
		// formToolkit.adapt(toolBarPage);
		// formToolkit.paintBordersFor(toolBarPage);

		final ToolItem indexPage = new ToolItem(toolBarPage, SWT.PUSH);
		indexPage.setImage(new Image(toolBarPage.getDisplay(),
				"image//indexPage.png"));
		indexPage.setText("首页");

		final ToolItem seperator_4 = new ToolItem(toolBarPage, SWT.NONE);
		seperator_4.setWidth(15);
		seperator_4.setText("  ");
		seperator_4.setEnabled(false);

		final ToolItem prePage = new ToolItem(toolBarPage, SWT.PUSH);
		prePage.setImage(new Image(toolBarPage.getDisplay(),
				"image//prePage.png"));
		prePage.setText("上一页");

		final ToolItem seperator_5 = new ToolItem(toolBarPage, SWT.NONE);
		seperator_5.setWidth(15);
		seperator_5.setText("  ");
		seperator_5.setEnabled(false);

		final ToolItem nextPage = new ToolItem(toolBarPage, SWT.PUSH);
		nextPage.setImage(new Image(toolBarPage.getDisplay(),
				"image//nextPage.png"));
		nextPage.setText("下一页");

		final ToolItem seperator_6 = new ToolItem(toolBarPage, SWT.NONE);
		seperator_6.setWidth(15);
		seperator_6.setText("  ");
		seperator_6.setEnabled(false);

		final ToolItem lastPage = new ToolItem(toolBarPage, SWT.PUSH);
		lastPage.setImage(new Image(toolBarPage.getDisplay(),
				"image//lastPage.png"));
		lastPage.setText("尾页");

		Listener pageListener = new Listener() {

			@Override
			public void handleEvent(Event event) {
				// if(event.widget == indexPage){
				//
				// }

				if (event.widget == nextPage) {
					if (pageResult.getCurPage() < pageResult.getTotalPage() - 1)
						pageResult.setCurPage(pageResult.getCurPage() + 1);
				} else if (event.widget == prePage) {
					if (pageResult.getCurPage() > 0)
						pageResult.setCurPage(pageResult.getCurPage() - 1);
				} else if (event.widget == indexPage) {
					pageResult = new PageResult();
					pageResult.setSize(Integer.valueOf(combo.getText()));
				} else if (event.widget == lastPage) {
					pageResult.setCurPage(pageResult.getTotalPage() - 1);
				}

				addTableData();
			}
		};

		// 为工具栏的按钮注册事件
		indexPage.addListener(SWT.Selection, pageListener);
		prePage.addListener(SWT.Selection, pageListener);
		nextPage.addListener(SWT.Selection, pageListener);
		lastPage.addListener(SWT.Selection, pageListener);
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
		shell.setLocation(Display.getCurrent().getClientArea().width / 2
				- shell.getShell().getSize().x / 2, Display.getCurrent()
				.getClientArea().height / 2 - shell.getSize().y / 2);

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
		Display display = Display.getDefault();
		shell = new Shell(display, SWT.CLOSE);
		shell.setImage(SWTResourceManager.getImage("image//fish.png"));
		shell.setSize(ScreenUtils.getScreenWidth(),
				ScreenUtils.getScreenHeight());
		shell.setText("无敌是多么寂寞");
		createViewForm();
	}

	private ViewForm viewForm = null;
	private Composite composite = null;
	private Table table;
	private final FormToolkit formToolkit = new FormToolkit(
			Display.getDefault());
	private Text textSearch;
	private Combo comboSearch;

	/**
	 * 1.创建ViewFrom
	 */
	private void createViewForm() {
		viewForm = new ViewForm(shell, SWT.NONE);
		viewForm.setBounds(0, 50, ScreenUtils.getScreenWidth(),
				ScreenUtils.getScreenHeight() - 80);

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
		// table = new Table(shell, SWT.BORDER| SWT.VIRTUAL);//注意此处的设置
		table.setLayoutData(gridData);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setBounds(0, 0, ScreenUtils.getScreenWidth(),
				ScreenUtils.getScreenHeight() - 100);

		final TableColumn newColumnTableColumn_ID = new TableColumn(table,
				SWT.NONE);
		newColumnTableColumn_ID.setWidth(ScreenUtils.getCellWidth());
		newColumnTableColumn_ID.setText("ID");

		final TableColumn newColumnTableColumn_PID = new TableColumn(table,
				SWT.NONE);
		newColumnTableColumn_PID.setWidth(ScreenUtils.getCellWidth());
		newColumnTableColumn_PID.setText("上级");

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

		addTableData();

		table.addListener(SWT.MouseDoubleClick, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				TableItem[] itemList = table.getItems();
				int listHavaChoise = table.getSelectionIndex();
				try {
					String firstInfo = itemList[listHavaChoise].getText(0);
					Display display = Display.getDefault();
					EditForm shell = new EditForm(display, firstInfo);
					shell.open();
					shell.layout();

					while (!shell.isDisposed()) {
						if (!display.readAndDispatch()) {
							display.sleep();
						}
					}
					addTableData();
				} catch (Exception e) {
				}
			}
		});
	}

	/**
	 * table里添加数据
	 */
	public void addTableData() {
		// 首先获得表格中所有的行
		TableItem[] items = table.getItems();
		// 循环所有行
		for (int i = items.length - 1; i >= 0; i--) {
			table.remove(i);
		}

		pageResult = userService.getUserListByPage(pageResult);
		final List<User> userList = (List<User>) pageResult.getList();
		String[] strArray;
		if (null != userList && userList.size() != 0)
			for (User user : userList) {
				TableItem item = new TableItem(table, SWT.NONE);
				strArray = user.toStringArray(user);
				User pUser = userService.getUserById(strArray[1]);
				if (!user.getPid().equals("0")) {
					strArray[1] = pUser.getUser_name();
				}
				item.setText(strArray);
			}

		lblPage.setText("总记录：" + pageResult.getTotal() + "\t当前页："
				+ (pageResult.getCurPage() + 1) + "/"
				+ pageResult.getTotalPage());
	}
}
