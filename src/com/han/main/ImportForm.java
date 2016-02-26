package com.han.main;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.wb.swt.SWTResourceManager;

import com.han.utils.ExcelUtils;
import com.han.utils.SafeSaveDialogUp;

public class ImportForm extends Dialog implements SelectionListener{

	
	private Shell parentShell;
	
	private Button btnDownload,btnSearch,importOK;
	private Text text;
	String url="";
	private Label lblWarning;
	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public ImportForm(Shell parentShell) {
		super(parentShell);
		this.parentShell = parentShell;
		
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		GridLayout gridLayout = (GridLayout) container.getLayout();
		gridLayout.numColumns = 11;
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		
		btnDownload = new Button(container, SWT.NONE);
		btnDownload.setText("下载Excel模板");
		btnDownload.addSelectionListener(ImportForm.this);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		
		text = new Text(container, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		new Label(container, SWT.NONE);
		
		btnSearch = new Button(container, SWT.NONE);
		btnSearch.setText("..浏览");
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		
		lblWarning = new Label(container, SWT.NONE);
		lblWarning.setAlignment(SWT.CENTER);
		lblWarning.setText("                                  ");
		lblWarning.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 7, 1));
		lblWarning.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		lblWarning.setFont(SWTResourceManager.getFont("微软雅黑", 9, SWT.NORMAL));
		btnSearch.addSelectionListener(ImportForm.this);
		return container;
	}

	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		importOK = createButton(parent, IDialogConstants.YES_ID, "导入",
				false);
		importOK.setTouchEnabled(true);
		importOK.addSelectionListener(ImportForm.this);
		createButton(parent, IDialogConstants.CANCEL_ID,
				"取消", false);
		
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(450, 300);
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent event) {

	}

	@Override
	public void widgetSelected(SelectionEvent event) {
		if(event.widget == btnSearch){
	    	FileDialog fileSelect=new FileDialog(parentShell,SWT.SINGLE);   
	    	fileSelect.setFilterNames(new String[]{"*.xls","*.xlsx"});   
	    	fileSelect.setFilterExtensions(new String[]{"*.xls","*.xlsx"});   
	    	url=fileSelect.open();
	    	if(url != null){
	    		text.setText(url);
	    		lblWarning.setText("");
	    	}
		}
		else if(event.widget == btnDownload){
			SafeSaveDialogUp s = new SafeSaveDialogUp();
			s.openSaveFileDialog("User");
		}
		else if(event.widget == importOK){
			if(text.getText().toString().trim()==""){
				lblWarning.setText("请选择需要导入的Excel");
			}else{
				String path = text.getText().toString().trim();
				ImportForm.this.close();
				ExcelUtils.insertExcel(parentShell,path);
			}
		}
	}
}
