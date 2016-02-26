package com.han.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class SafeSaveDialogUp {
	public SafeSaveDialogUp() {
	}

	FileDialog dlg;

	public SafeSaveDialogUp(Shell shell, String fileName) {
		dlg = new FileDialog(shell, SWT.SAVE);// 设置为保存对话框
		// 设置保存类型
		dlg.setFileName(fileName);

		dlg.setFilterNames(new String[] { "Excel Files (*.xls)",
				"Excel files (*.xlsx)" });
		dlg.setFilterExtensions(new String[] { "*.xls", "*.xlsx" });
	}

	public String open() {
		// Store the selected file name in fileName
		String saveFileName = null;

		// The user has finished when one of the
		// following happens:
		// 1) The user dismisses the dialog by pressing Cancel
		// 2) The selected file name does not exist
		// 3) The user agrees to overwrite existing file
		boolean done = false;

		while (!done) {
			// Open the File Dialog
			saveFileName = dlg.open(); // 得到输入的文件名,输入文件名时不用加.xls
			// 取消保存对话框
			if (saveFileName == null) {
				// User has cancelled, so quit and return
				MessageBox mg = new MessageBox(dlg.getParent(),
						SWT.ICON_WARNING | SWT.YES);
				mg.setText("Tips");
				mg.setMessage("你取消了保存文件");
				done = mg.open() == SWT.YES;
				done = true;
			} else {
				// 文件名已经存在，是否替换
				File file = new File(saveFileName);
				if (file.exists()) {
					// The file already exists; asks for confirmation
					MessageBox mb = new MessageBox(dlg.getParent(),
							SWT.ICON_WARNING | SWT.YES | SWT.NO);
					mb.setText("Tips");
					mb.setMessage(saveFileName + " 已经存在，是否要替换它?");

					// If they click Yes, drop out. If they click No,
					// redisplay the File Dialog
					done = mb.open() == SWT.YES;
				} else {
					// 不存在文件名重复，可以保存
					done = true;
				}
				FileInputStream in = null;
				FileOutputStream out = null;
				try {
					in = new FileInputStream(new File("file//user.xlsx"));
					file.createNewFile();
					out = new FileOutputStream(file);

//					byte buffer[] = new byte[1024];
					int len;
					while ((len = in.read()) != -1) {
						out.write(len);
					}
				} catch (Exception e) {
				} finally {
					try {
						if (null != in)
							in.close();
						if(null != out)
							out.close();
					} catch (Exception e) {
					}
				}
			}
		}
		return saveFileName;
	}

	public String getFileName() {
		return dlg.getFileName();
	}

	public String[] getFileNames() {
		return dlg.getFileNames();
	}

	public String[] getFilterExtensions() {
		return dlg.getFilterExtensions();
	}

	public String[] getFilterNames() {
		return dlg.getFilterNames();
	}

	public String getFilterPath() {
		return dlg.getFilterPath();
	}

	public void setFileName(String string) {
		dlg.setFileName(string);
	}

	public void setFilterExtensions(String[] extensions) {
		dlg.setFilterExtensions(extensions);
	}

	public void setFilterNames(String[] names) {
		dlg.setFilterNames(names);
	}

	public void setFilterPath(String string) {
		dlg.setFilterPath(string);
	}

	public Shell getParent() {
		return dlg.getParent();
	}

	public int getStyle() {
		return dlg.getStyle();
	}

	public String getText() {
		return dlg.getText();
	}

	public void setText(String string) {
		dlg.setText(string);
	}

	/**
	 * Runs the application
	 */

	/**
	 * Creates the window contents
	 * 
	 * @param shell
	 *            the parent shell
	 */
	public void openSaveFileDialog(String fileName) {
		Shell shell = new Shell();
		SafeSaveDialogUp dlg = new SafeSaveDialogUp(shell, fileName);
		dlg.open();
	}
}