package db_JDBC;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.Mot;

public class MotModel extends JdbcCorpus{
	
	
	
	public Map<String, Mot> getCollectionsMotsFromDb() 
	{
		Map<String, Mot> words = new HashMap<String, Mot>();
		//String sql = "SELECT * FROM db_CorpusECD.Mots;";
		String sql = "SELECT * FROM db_CorpusECD.Mots ORDER BY tf_cumule DESC LIMIT 2000;";
		System.out.println("Avant impresion Mots");
		ResultSet rs = null;
	    Connection conn = this.getMysqlConnection();
	    Statement stmt = null;
	    try{
		   stmt = conn.createStatement();
		   rs = stmt.executeQuery(sql);
		   while(rs.next()){
				int id = rs.getInt("id");
				String value = rs.getString("value");
				int tf_max = rs.getInt("tf_max");
				int tf_min = rs.getInt("tf_min");
				int tf_cumule = rs.getInt("tf_cumule");
				int df = rs.getInt("df");
				double idf = rs.getDouble("idf");
				double tf_idfmax = rs.getDouble("tf_idfmax");
				double tf_idfmin = rs.getDouble("tf_idfmin");
				double tf_idfcumule = rs.getDouble("tf_idfcumule");
				int polarite_negative = rs.getInt("polarite_negative");
				int polarite_positive = rs.getInt("polarite_positive");
		         
		        Mot motObj = new Mot(
	        		id,
		   			value,
		   			tf_max,
		   			tf_min,
		   			tf_cumule,
		   			df,
		   			idf,
		   			tf_idfmax,
		   			tf_idfmin,
		   			tf_idfcumule,
		   			polarite_negative,
		   			polarite_positive
		        );
		        words.put(value, motObj);
		     }
		   rs.close();
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
		
	   return words;
	}
	
	public List<String> getListMotsFromDb() 
	{
		ArrayList<String> words = new ArrayList<String>();
		String sql = "SELECT * FROM db_CorpusECD.Mots ORDER BY tf_cumule DESC LIMIT 2000;";
		ResultSet rs = null;
	    Connection conn = this.getMysqlConnection();
	    Statement stmt = null;
	    try{
		   stmt = conn.createStatement();
		   rs = stmt.executeQuery(sql);
		   while(rs.next()){
				int id = rs.getInt("id");
				String value = rs.getString("value");
				words.add(value);
		     }
		   rs.close();
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
		
	   return words;
	}
}
