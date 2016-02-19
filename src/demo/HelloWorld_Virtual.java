package demo;





import java.util.Vector;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

public class HelloWorld_Virtual {

	private static Table table;
	
	/**
	 * Launch the application
	 * @param args
	 */
	public static void main(String[] args) {
		//final Vector vResult = new Vector();
		final Display display = Display.getDefault();
		
		final Shell shell = new Shell();//shell 是主程序窗口
		
		shell.setSize(547, 327);//设置窗体初始化大小
		shell.setText("SWT Application");//设置窗体名称
	
//////////////////////////////新插入界面的核心代
		
/////////////////////////////////////////////////////////

		shell.open();//主程序打开
		
		
/////////////////////////创建表格//////////////////////////////////
		table = new Table(shell, SWT.BORDER| SWT.VIRTUAL);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setBounds(0, 0, 539, 290);
///////////////////创建表格四列////////////////////////////////////////////
		final TableColumn newColumnTableColumn = new TableColumn(table, SWT.NONE);
		newColumnTableColumn.setWidth(100);
		newColumnTableColumn.setText("id");
		
		final TableColumn newColumnTableColumn_1 = new TableColumn(table, SWT.NONE);
		newColumnTableColumn_1.setWidth(100);
		newColumnTableColumn_1.setText("Name_1");

		final TableColumn newColumnTableColumn_2 = new TableColumn(table, SWT.NONE);
		newColumnTableColumn_2.setWidth(100);
		newColumnTableColumn_2.setText("Name_2");
		
		final TableColumn newColumnTableColumn_3 = new TableColumn(table, SWT.NONE);
		newColumnTableColumn_3.setWidth(100);
		newColumnTableColumn_3.setText("Name_3");
		
		final TableColumn newColumnTableColumn_4 = new TableColumn(table, SWT.NONE);
		newColumnTableColumn_4.setWidth(100);
		newColumnTableColumn_4.setText("Name_4");
////////////////////////////////////////////////////////////////////////////////////////////////////
		
	    //获得数据表格数据
		  ConnDAO dao = new ConnDAO(); 
		  String ss= "select * from test_table";//SendTable为表名
	  	  final Vector  vResult = dao.executeQuery(ss);
	  	  
		/////////////设置监听/////////////////////////////////
	  	  
	  	 /* table.addListener(SWT.SetData, new Listener() {  
	            public void handleEvent(Event event) {  
	               TableItem item = (TableItem)event.item;  
	               int index = event.index;  
	               item.setText((String[])vResult.get(index));  
	            } 	
	        });  */
	 //////////////另一种方式实现虚拟表格///////////////////////////////////
	  final int PAGE_SIZE = 100;  
	  	table.addListener (SWT.SetData, new Listener () { 
  		    public void handleEvent (Event event) { 
  		        TableItem item = (TableItem) event.item; 
  		        int index = event.index; 
  		        int page = index / PAGE_SIZE; 
  		        int start = page * PAGE_SIZE; 
  		        int end = start + PAGE_SIZE; 
  		        end = Math.min (end, table.getItemCount ()); 
  		        for (int i = start; i < end; i++) { 
  		           item = table.getItem (i); 
  		          item.setText ((String[])vResult.get(i)); 
  	       } 
  		    } 
  		 }); 
	  	  
	    //////////////设计总记录数，便于滚动条计算//////////////////////////////////////////////////	    
	       
		table.setItemCount(vResult.size());  
		////////////////如需要修改，可用下面的方法选择某一列
	/*	
	     Button button = new Button(shell, SWT.PUSH);  
		 button.setText("Change item at index 5");  
		 button.addListener(SWT.Selection, new Listener() {  
		     public void handleEvent(Event event) {  
		    	 itemStrings [5] = "item " + System.currentTimeMillis();  
		       table.clear(5);  
		    }  
		 });  
    */
		////////////////////////////////////////////////

		shell.layout();//主程序布置
		while (!shell.isDisposed()) {//如果主程序没有关闭，这一直循环
			if (!display.readAndDispatch())//如果不忙
				display.sleep();//休眠
		}
	}
	
}
