package com.han.main;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

import com.han.utils.ScreenUtils;

public class EditForm extends Shell {

	/**
	 * Create the shell.
	 * @param display
	 */
	public EditForm(Display display) {
		super(display, SWT.BORDER | SWT.CLOSE);
		this.setLocation(Display.getCurrent().getClientArea().width / 2 - this.getShell().getSize().x/2, Display.getCurrent()  
                .getClientArea().height / 2 - this.getSize().y/2);
		this.setImage(SWTResourceManager.getImage("image//edit.png"));
		
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("编辑数据");
		setSize(600, 350);
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
