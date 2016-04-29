package db_JDBC;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.Document;

public class DocumentModel extends JdbcCorpus{
	
	
	
	public List<Document> getCollectionsMotsFromDb() 
	{
		List<Document> documentsList = new ArrayList<Document>();
		String sql = "SELECT * FROM Documents;";
		ResultSet rs = null;
	    Connection conn = this.getMysqlConnection();
	    Statement stmt = null;
	    try{
		   stmt = conn.createStatement();
		   rs = stmt.executeQuery(sql);
		   while(rs.next()){
				int id = rs.getInt("id");
				String avis = rs.getString("avis");
				int polarite = rs.getInt("polarite");
				int nombre_mots = rs.getInt("nombre_mots");
				
				Document documentObj = new Document(
	        		id,
	        		avis,
	        		polarite,
	        		nombre_mots
		        );
				documentsList.add(documentObj);
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
		
	   return documentsList;
	}
	
	public int createDocument(String avis,int polarite, int nombre_mots ){
		  String sql = ""
		  		+ "INSERT INTO `Documents`"
		  		+ "(`avis`,"
				+ "`polarite`,"
				+ " nombre_mots"
				+ ")"
				+ "VALUES("
				+ "	' " + avis + " ',"
				+ " " + polarite + ","
				+ " " + nombre_mots + ");"; 
		  
		  int result = this.executeUpdate(sql);
		  return result;
	  }
}
