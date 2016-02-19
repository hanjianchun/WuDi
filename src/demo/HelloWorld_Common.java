package demo;




import java.sql.ResultSet;
import java.util.List;
import java.util.Vector;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

public class HelloWorld_Common {

	private static Table table;
	/**
	 * Launch the application
	 * @param args
	 */
	public static void main(String[] args) {
		final Display display = Display.getDefault();
		
		final Shell shell = new Shell();//shell 是主程序窗口
		
		shell.setSize(808, 375);//设置窗体初始化大小
		shell.setText("SWT Application");//设置窗体名称
	
//////////////////////////////新插入界面的核心代
		
/////////////////////////////////////////////////////////

		shell.open();//主程序打开
		
		

		table = new Table(shell, SWT.BORDER);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setBounds(40, 30, 723, 290);
		final TableColumn newColumnTableColumn = new TableColumn(table, SWT.NONE);
		newColumnTableColumn.setWidth(100);
		newColumnTableColumn.setText("id");
		
		final TableColumn newColumnTableColumn_1 = new TableColumn(table, SWT.NONE);
		newColumnTableColumn_1.setWidth(100);
		newColumnTableColumn_1.setText("New column1");

		final TableColumn newColumnTableColumn_2 = new TableColumn(table, SWT.NONE);
		newColumnTableColumn_2.setWidth(100);
		newColumnTableColumn_2.setText("New column2");
		
		final TableColumn newColumnTableColumn_3 = new TableColumn(table, SWT.NONE);
		newColumnTableColumn_3.setWidth(100);
		newColumnTableColumn_3.setText("New column3");
		final TableColumn newColumnTableColumn_4 = new TableColumn(table, SWT.NONE);
		newColumnTableColumn_4.setWidth(100);
		newColumnTableColumn_4.setText("New column4");	

		try   
		{
//			获得数据表格数据
			  ConnDAO dao = new ConnDAO(); 
			  String ss= "select * from user1";//SendTable为表名
		  	  final Vector  vResult = dao.executeQuery(ss);
		  	  for (int i=0;i<vResult.size();i++)
		  	  {
		  		 TableItem  item = new TableItem(table,SWT.NONE);    
		  		  item.setText((String[])vResult.get(i));  
		  	  }

		}catch(Exception exception)   
		{   
		    exception.printStackTrace();   
		}

		shell.layout();//主程序布置
		while (!shell.isDisposed()) {//如果主程序没有关闭，这一直循环
			if (!display.readAndDispatch())//如果不忙
				display.sleep();//休眠
		}
	}

}
