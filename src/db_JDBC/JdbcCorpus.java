package db_JDBC;

//STEP 1. Import required packages
import java.sql.*;
public class JdbcCorpus 
{
	   // JDBC driver name and database URL
	   private final String DB_Name = "db_CorpusECD";
	   private final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	   //private final String DB_URL = "jdbc:mysql://localhost:3306/"+DB_Name;
	   private final String DB_URL = "jdbc:mysql://localhost:3306/"+DB_Name;

	   //  Database credentials
	   private final String USER = "root";
	   private final String PASS = "password";
	   
	   protected Connection getMysqlConnection()
	   {
		   Connection conn = null;
		   try{
		      //STEP 2: Register JDBC driver
		      Class.forName("com.mysql.jdbc.Driver");

		      //STEP 3: Open a connection
		      conn = DriverManager.getConnection(DB_URL, USER, PASS);
		      
		   }catch(SQLException se){
		      //Handle errors for JDBC
		      se.printStackTrace();
		   }catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
		   }
		   return conn;
	   }
	   
	   /**
	    *
	    * Example de recuperaion de donnes à partir d'un result Ser
	    sql = "SELECT id, first, last, age FROM Employees";
      	ResultSet rs = stmt.executeQuery(sql);
	      while(rs.next()){
	         //Retrieve by column name
	         int id  = rs.getInt("id");
	         int age = rs.getInt("age");
	         String first = rs.getString("first");
	         String last = rs.getString("last");
	
	         //Display values
	         System.out.print("ID: " + id);
	         System.out.print(", Age: " + age);
	         System.out.print(", First: " + first);
	         System.out.println(", Last: " + last);
	      }
	    * @param sql
	    * @return
	    */
	   public ResultSet executeQuery(String sql)
	   {
		   ResultSet rs = null;
		   Connection conn = this.getMysqlConnection();
		   Statement stmt = null;
		   try{
			   stmt = conn.createStatement();
			   rs = stmt.executeQuery(sql);
			   if(conn!=null){
				   conn.close();
			   }
			   stmt.close();
		   }
		   catch(SQLException se){
		      //Handle errors for JDBC
		      se.printStackTrace();
		   }catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
		   }finally{
		      //finally block used to close resources
		      try{
		         if(stmt!=null)
		            conn.close();
		      }catch(SQLException se){
		      }// do nothing
		      try{
		         if(conn!=null)
		            conn.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }//end finally try
		   }//end try
		   return rs;
	  }
	   
	   public int executeUpdate(String sql)
	   {
		   Connection conn = this.getMysqlConnection();
		   Statement stmt = null;
		   int result = 0;
		   try{
			   stmt = conn.createStatement();
			   result = stmt.executeUpdate(sql);
			   if(conn!=null){
		            conn.close();
			   }
			   stmt.close();
		   }
		   catch(SQLException se){
		      //Handle errors for JDBC
		      se.printStackTrace();
		   }catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
		   }finally{
		      //finally block used to close resources
		      try{
		         if(stmt!=null)
		            conn.close();
		      }catch(SQLException se){
		      }// do nothing
		      try{
		         if(conn!=null)
		            conn.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }//end finally try
		   }//end try
		   return result;
	  }
	   
	  public void testDb(){
		  String sql = ""
		  		+ "INSERT INTO `db_CorpusECD`.`Documents`"
		  		+ "(`avis`,"
				+ "`polarite`)"
				+ "VALUES("
				+ "	'Test avis',"
				+ " 1);"; 
		  
		  System.out.println("Beforne Query: ");
		  int result = this.executeUpdate(sql);
		  System.out.println("After Query Result: " + result);
	  }
	   
	}//end JDBCExample
