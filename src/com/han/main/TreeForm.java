package com.han.main;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.wb.swt.SWTResourceManager;

import com.han.pojo.User;
import com.han.service.IUserService;
import com.han.service.impl.UserServiceImpl;
import com.han.utils.ScreenUtils;
import org.eclipse.swt.widgets.Label;

public class TreeForm extends Shell {

	private IUserService userService;

	private List<User> allUser = new ArrayList<>();

	private Tree tree;

	/**
	 * Create the shell.
	 * 
	 * @param display
	 */
	public TreeForm(Display display) {
		super(display, SWT.SHELL_TRIM);
		createContents();

		createViews(display);

		initData();
	}

	private void initData() {
		userService = new UserServiceImpl();

		allUser = userService.getAllUser();
		TreeItem item, item1, item2, item3;
		for (User user : allUser) {
			if ("0".equals(user.getPid())) {
				item = new TreeItem(tree, SWT.NONE);
				item.setText(user.getUser_name());
				for (User secondUser : allUser) {
					if (secondUser.getPid().equals(user.getId())) {
						item1 = new TreeItem(item, SWT.NONE);
						item1.setText(secondUser.getUser_name());
						for (User thirdUser : allUser) {
							if (thirdUser.getPid().equals(secondUser.getId())) {
								item2 = new TreeItem(item1, SWT.NONE);
								item2.setText(thirdUser.getUser_name());
								for (User forthUser : allUser) {
									if (forthUser.getPid().equals(
											thirdUser.getId())) {
										item3 = new TreeItem(item2, SWT.NONE);
										item3.setText(forthUser.getUser_name());
									}
								}
							}
						}
					}
				}
			}
		}
	}

	private void createViews(final Display display) {
		tree = new Tree(this, SWT.BORDER);
		tree.setBounds(10, 10, (int) (ScreenUtils.getScreenWidth() * 0.8) - 35,
				(int) (ScreenUtils.getScreenHeight() * 0.5));
		tree.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		final Label lblDetailMessage = new Label(this, SWT.NONE);
		lblDetailMessage.setBounds(10, (int) (ScreenUtils.getScreenHeight() * 0.55), (int) (ScreenUtils.getScreenWidth() * 0.8) - 35, (int) (ScreenUtils.getScreenHeight() * 0.25));
		lblDetailMessage.setText("");

		tree.addListener(SWT.MouseDoubleClick, new Listener() {
			public void handleEvent(Event event) {
				Point point = new Point(event.x, event.y);
				TreeItem item = tree.getItem(point);
				if (item != null) {
					for(User user : allUser){
						if(user.getUser_name().equals(item.getText())){
							System.out.println(user);
							EditForm shell = new EditForm(display, user.getId());
							shell.open();
							shell.layout();

							while (!shell.isDisposed()) {
								if (!display.readAndDispatch()) {
									display.sleep();
								}
							}
						}
					}
				}
			}
		});
		tree.addListener(SWT.MouseDown, new Listener() {
			@Override
			public void handleEvent(Event event) {
				Point point = new Point(event.x, event.y);
				TreeItem item = tree.getItem(point);
				if (item != null) {
					for(User user : allUser){
						if(user.getUser_name().equals(item.getText())){ 
							lblDetailMessage.setText("姓名："+user.getUser_name()+"    所获得金额："+user.getUser_money()+"    等级:"+user.getUser_grade());
						}
					}
				}
			}
		});

	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("关联关系");
		this.setLocation(Display.getCurrent().getClientArea().width / 2
				- this.getShell().getSize().x / 2, Display.getCurrent()
				.getClientArea().height / 2 - this.getSize().y / 2);

		this.setImage(SWTResourceManager.getImage("image//relationship.png"));
		this.setSize((int) (ScreenUtils.getScreenWidth() * 0.8),
				(int) (ScreenUtils.getScreenHeight() * 0.8));

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
