package demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class ConnDAO {
	private Connection con;
    private String driver;
    private String userName; 
    private String password;
    private String dburl;
    private Statement stmt;
     
  public ConnDAO()
  {
    dburl = "jdbc:mysql://localhost:3306/test";
    userName = "root";
    password = "root";
    driver ="com.mysql.jdbc.Driver";  
  }
  //按照给定的sql进行数据库查询
  public Vector executeQuery(String sql)
 {
    ResultSet rs = null;
    Vector vResult = new Vector();
    try
   {
        Class.forName(driver);
        con = DriverManager.getConnection(dburl,userName,password);
        stmt = con.createStatement();
        rs = stmt.executeQuery(sql);
        ResultSetMetaData rsmd = rs.getMetaData();
        int  numCols = rsmd.getColumnCount();
         while (rs.next())
		{
			String strTempArray[] = new String[numCols];
          for(int i = 1; i <= numCols; i++)
			{
		    /*将存储过程得到的每个字段赋值到字符串数组，并将其做为对象加入到vector类中返回*/
            strTempArray[i - 1] = rs.getString(i);
			}
			vResult.addElement(strTempArray);
		}
        
    }catch(Exception e)
    {
        System.out.println("Query Error!!!!!!!!!");
    }
    return vResult;
  }

  /*//修改、插入、删除
  public void updateDB(String sql)
 {
      try
      {
          Class.forName(driver);
            con = DriverManager.getConnection(dburl,userName,password);
            stmt = con.createStatement();
            stmt.executeUpdate(sql);
      }catch(Exception e)
     {
          System.out.println("UpDate Error!!!!!!!");
      }
  }
  */
  //关闭数据库链接
  public void closeDB()
 {
      try
      {
          con.close();
      }catch(SQLException ex)
     {
          System.err.println(ex.getMessage());
      }
  }
  public void tt(){
//      Class.forName(driver);
//      con = DriverManager.getConnection(dburl,userName,password);
//      stmt = con.createStatement();
//      rs = stmt.executeQuery(sql);
//      ResultSetMetaData rsmd = rs.getMetaData();
//	  for (int i=0;i<20000;i++)
//	  {
//		  String s = "chengang";
//		  sm.executeUpdate("insert into test_table (name0,name1,name2,name3) values ('" + s+1 +i +"','" + s +2+i +"','" + s +3+i +"','" + s +4+i +"')");
//	  }
  }

}
