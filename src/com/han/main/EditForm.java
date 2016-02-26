package com.han.main;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

import com.han.pojo.User;
import com.han.service.IUserService;
import com.han.service.impl.UserServiceImpl;
import com.han.utils.ScreenUtils;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;

public class EditForm extends Shell implements SelectionListener{

	private String userId;
	private Text textNum;
	private Text textName;
	private Text textMoney;
	private Text textAge;
	private Button btnSave,btnCancel;
	
	private IUserService userService;
	
	private Combo comboGrade,comboParent,comboSex;
	
	private User thisUser;
	
	private List<User> parentUserList;
	/**
	 * Create the shell.
	 * @param display
	 */
	public EditForm(Display display,String userId) {
		super(display, SWT.BORDER | SWT.CLOSE);
		this.userId = userId;
		this.setLocation(Display.getCurrent().getClientArea().width / 2 - this.getShell().getSize().x/2, Display.getCurrent()  
                .getClientArea().height / 2 - this.getSize().y/2);
		this.setImage(SWTResourceManager.getImage("image//edit.png"));
		
		createContents();
		
		createTextEdit();
		
		userService = new UserServiceImpl();
		
		initData();
	}

	private void initData() {
		thisUser = userService.getUserById(userId);
		
		textNum.setText(thisUser.getUser_num());
		
		textMoney.setText(thisUser.getUser_money());
		
		textAge.setText(thisUser.getUser_age());
		
		textName.setText(thisUser.getUser_name());
		
		comboSex.select(Integer.valueOf(thisUser.getUser_sex()));
		
		
		comboGrade.select(Integer.valueOf(thisUser.getUser_grade())-1);
		
		User u = new User();
		u.setUser_grade((Integer.valueOf(thisUser.getUser_grade())-1)+"");
		if(!thisUser.getUser_grade().equals("1")){
			parentUserList = userService.getUserByUser(u);
			
			for(int i=0;i<parentUserList.size();i++){
				comboParent.add("编号："+parentUserList.get(i).getUser_num()+"  姓名："+parentUserList.get(i).getUser_name(), i);
				if(parentUserList.get(i).getId().equals(thisUser.getPid())){
					comboParent.select(i);
				}
			}
			
		}else{
			comboParent.add("无上级");
			comboParent.select(0);
		}
		
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("编辑数据");
		setSize(600, 350);
		
		Label lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setFont(SWTResourceManager.getFont("微软雅黑", 11, SWT.NORMAL));
		lblNewLabel.setAlignment(SWT.CENTER);
		lblNewLabel.setBounds(90, 10, 61, 20);
		lblNewLabel.setText("上级：");
		
		Label lblNewLabel_1 = new Label(this, SWT.NONE);
		lblNewLabel_1.setFont(SWTResourceManager.getFont("微软雅黑", 11, SWT.NORMAL));
		lblNewLabel_1.setAlignment(SWT.CENTER);
		lblNewLabel_1.setBounds(90, 45, 61, 20);
		lblNewLabel_1.setText("编号：");
		
		Label lblNewLabel_2 = new Label(this, SWT.NONE);
		lblNewLabel_2.setFont(SWTResourceManager.getFont("微软雅黑", 11, SWT.NORMAL));
		lblNewLabel_2.setAlignment(SWT.CENTER);
		lblNewLabel_2.setBounds(90, 80, 61, 20);
		lblNewLabel_2.setText("姓名：");
		
		Label lblNewLabel_3 = new Label(this, SWT.NONE);
		lblNewLabel_3.setFont(SWTResourceManager.getFont("微软雅黑", 11, SWT.NORMAL));
		lblNewLabel_3.setAlignment(SWT.CENTER);
		lblNewLabel_3.setBounds(90, 115, 61, 20);
		lblNewLabel_3.setText("年龄：");
		
		Label label = new Label(this, SWT.NONE);
		label.setFont(SWTResourceManager.getFont("微软雅黑", 11, SWT.NORMAL));
		label.setAlignment(SWT.CENTER);
		label.setBounds(90, 150, 61, 17);
		label.setText("等级：");
		
		Label lblNewLabel_4 = new Label(this, SWT.NONE);
		lblNewLabel_4.setFont(SWTResourceManager.getFont("微软雅黑", 11, SWT.NORMAL));
		lblNewLabel_4.setAlignment(SWT.CENTER);
		lblNewLabel_4.setBounds(90, 185, 61, 20);
		lblNewLabel_4.setText("性别：");
		
		Label lblNewLabel_5 = new Label(this, SWT.NONE);
		lblNewLabel_5.setFont(SWTResourceManager.getFont("微软雅黑", 11, SWT.NORMAL));
		lblNewLabel_5.setAlignment(SWT.CENTER);
		lblNewLabel_5.setBounds(90, 220, 61, 20);
		lblNewLabel_5.setText("金额：");
	}
	
	public void createTextEdit(){
		comboParent = new Combo(this, SWT.NONE | SWT.READ_ONLY);
		comboParent.setBounds(200, 10, 200, 25);
		
		textNum = new Text(this, SWT.BORDER);
		textNum.setBounds(200, 45, 200, 23);
		
		textName = new Text(this, SWT.BORDER);
		textName.setBounds(200, 77, 200, 23);
		
		textMoney = new Text(this, SWT.BORDER);
		textMoney.setBounds(200, 220, 200, 23);
		
		comboGrade = new Combo(this, SWT.NONE | SWT.READ_ONLY);
		comboGrade.setItems(new String[] {"1级", "2级", "3级", "4级"});
		comboGrade.setBounds(200, 154, 200, 25);
		comboGrade.select(0);
		
		comboSex = new Combo(this, SWT.NONE | SWT.READ_ONLY);
		comboSex.setItems(new String[] {"男", "女"});
		comboSex.setBounds(200, 185, 200, 25);
		comboSex.select(0);
		
		textAge = new Text(this, SWT.BORDER);
		textAge.setBounds(200, 115, 200, 23);
		
		Label label_1 = new Label(this, SWT.NONE);
		label_1.setBounds(410, 223, 61, 17);
		label_1.setText("元");
		
		btnSave = new Button(this, SWT.NONE);
		btnSave.setBounds(90, 274, 120, 30);
		btnSave.setText("保存");
		btnSave.addSelectionListener(this);
		
		btnCancel = new Button(this, SWT.NONE);
		btnCancel.setBounds(330, 274, 120, 30);
		btnCancel.setText("取消");
		btnCancel.addSelectionListener(this);
	}

	
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent arg0) {
		
	}

	@Override
	public void widgetSelected(SelectionEvent event) {
		if(event.widget == btnSave){
			saveData();
			EditForm.this.close();
		}
		
		else if(event.widget == btnCancel){
			EditForm.this.close();
		}
		
	}
	
	private void saveData(){
		thisUser.setId(userId);
		thisUser.setUser_age(textAge.getText().toString());
		thisUser.setUser_grade((comboGrade.getSelectionIndex()+1)+"");
		thisUser.setUser_money(textMoney.getText().toString());
		thisUser.setUser_name(textName.getText().toString());
		thisUser.setUser_sex(comboSex.getSelectionIndex()+"");
		thisUser.setUser_num(textNum.getText().toString());
		System.out.println();
		if(null != parentUserList && parentUserList.size()!=0 && comboParent.getSelectionIndex() != -1)
			thisUser.setPid(parentUserList.get(comboParent.getSelectionIndex()).getId());
		userService.updateUser(thisUser);
	}
}
