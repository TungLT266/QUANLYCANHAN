package geso.traphaco.center.db.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

public interface Idbutils {
	 public boolean connect()  ;
	    public int updateReturnInt(String query)  ;
	    public ResultSet get(String query)  ;

	    public ResultSet getScrol(String query)  ;
	    public boolean update(String query)  ;
	    
	    public int execProceduce(String procName, String[] param);
	    public ResultSet getRsByPro(String ProcName,String[] param) ;
	   
	    public String execProceduce2(String procName, String[] param);
	     
	    public boolean shutDown() ;
	    
	    
	    public List<List<String>> RStoList(ResultSet rs, int num);
	  
	    public Connection getConnection();
}
