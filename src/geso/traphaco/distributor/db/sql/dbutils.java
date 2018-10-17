package geso.traphaco.distributor.db.sql;

import geso.traphaco.center.db.sql.Idbutils;
import geso.traphaco.center.util.DbInfo;

import java.sql.*;  
import java.lang.String;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class dbutils implements Idbutils,Serializable
{ 
	private static final long serialVersionUID = 1L;

	private Connection connection;
	private Statement statement;
	
	private String username = DbInfo.username;
	private String password = DbInfo.password;
	private String url = DbInfo.url;
 	 
	public dbutils()
    {  
    	connect();
    }  
    
	public boolean connect() {
		try 
		{
    		Class.forName("net.sourceforge.jtds.jdbc.Driver");
    		connection = DriverManager.getConnection(url, username, password);
    		
			return true;
		} catch (Exception ex) {
			System.out.print(ex.toString());
		}
		return false;
	}
    			
	public ResultSet get(String query) {
		try {

			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(query);
			
			return rs;
		} catch (SQLException sqle) {
			System.out.println("Loi "+query);
			sqle.printStackTrace();
			return null;
		}
	}

	public ResultSet getScrol(String query)  
    {  
    	try {
    		
    		//statement = connection.createStatement();
    		statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
    		ResultSet rs = statement.executeQuery( query );  
    		return rs;      	 
    	}catch ( SQLException sqle ) {	
    		System.out.print(sqle.toString());
    		return null;
        }                
    }  
	 
    public boolean update(String query)
    {  
    	try {  
    		//System.out.println("  UPDATE: " + query);
    		
    		statement = connection.createStatement();  
    		statement.setQueryTimeout(60);
    		statement.executeUpdate( query );  
    		
    		return true;  
    	}  
    	catch ( Exception sqle ) { 
    		System.out.println("::LOI UPDATE: " + query);
    		sqle.printStackTrace();
    		return false;  
    	}  
    }  
    
    public int updateReturnInt(String query)  
    {  
    	try { 
    		
    		statement = connection.createStatement();  
    		statement.setQueryTimeout(60);
    		return	statement.executeUpdate( query );  
   
    	}  
    	catch ( Exception sqle ) {  
    		
    		System.out.println("::LOI UPDATE: " + sqle);
    		sqle.printStackTrace();
    		return -1;  
    	}  
    }  		
    
    public ResultSet getRsByPro(String ProcName,String[] param) {
        try{
       // CallableStatement cStmt = connection.prepareCall("call getRsKpi_report '2012-01-01' , '2012-03-01'" );
       
        String SPsql = "EXEC "+ ProcName +" ";   // for stored proc taking 2 parameters
         // java.sql.Connection 
        String param_num="";
        if(param != null)
		{
			for(int i = 0; i < param.length; i++){
				param_num=param_num+"?,";
			}
			param_num = param_num.substring(0, param_num.length() - 1); //cat dau , cuoi
		}
        System.out.println(SPsql+param_num);
        PreparedStatement ps = connection.prepareStatement(SPsql+param_num);
        ps.setEscapeProcessing(true);
       // ps.setQueryTimeout(<timeout value>);
      
        if(param != null)
		{
			for(int i = 0; i < param.length; i++){
				ps.setString(i+1, param[i]);
			}
			
		}
        
        ResultSet rs = ps.executeQuery();
        return rs;
        }  
    	catch ( SQLException sqle ) { 
    		sqle.printStackTrace();
    		System.out.print("Loi Trong Qua trinh Lay Resultset,file dbutils.java distributor  :"+sqle.toString());    		
    		return null;
    	}  
   }
    
    public int execProceduce(String procName, String[] param)
    {
		try 
		{
			String query = "";
	    	for(int i = 0; i < param.length; i++)
	    		query += "?,";
	    	if(query.length() > 0)
	    		query += "?"; //tham so dau ra, luu ket qua sau khi thuc thi thu tuc
	    	
	    	//query = "{call exOutParams(?,?)}";
	    	query = "{call " + procName + "(" + query + ")}";
	    	System.out.println("Query la: " + query);
	    	
			CallableStatement cstmt = connection.prepareCall(query);
			for(int i = 0; i < param.length; i++)
			{
				cstmt.setString(i + 1, param[i]);
			}

	    	//đăng ký tham số đầu ra
			cstmt.registerOutParameter(param.length + 1, java.sql.Types.INTEGER);
	    	cstmt.execute();

	    	//Lấy giá trị trả về
	    	int resual = cstmt.getInt(param.length + 1);
	    
	    	return resual;
		} 
		catch (Exception e) 
		{
			System.out.println("Loi: " + e.toString());
			return -1;
		}
    }
     
    public String execProceduce2(String procName, String[] param)
    {
    	try 
    	{  
    		statement = connection.createStatement();  
    		  
    		String query = procName;
    		
    		if(param != null)
    		{
    			String paramList = "";
    			for(int i = 0; i < param.length; i++)
    				paramList += "'" + param[i] + "',";
    			paramList = paramList.substring(0, paramList.length() - 1); //cat dau , cuoi
    			
    			query += " " + paramList;
    		}
    		
    		statement.executeUpdate( query );
    			
    		return "";  
    	}  
    	catch (Exception e )
    	{  
    		return e.toString();  
    	}  
    }
    
    public boolean shutDown()  
    {  
    	try {  
    		if(statement != null)
    			statement.close();
    		
    		if(connection != null)
    			connection.close();  
   
    		return true;  
    	}  
    	catch ( SQLException sqlex ) {  
    		return false;  
    	}  
    }  
    
    public List<List<String>> RStoList(ResultSet rs, int num){
    	List<List<String>> list = new ArrayList<List<String>>();

    	try{
    		while (rs.next()){
    			List<String> tmp = new ArrayList<String>();
    			for (int i = 1; i<= num; i++){
    				tmp.add(rs.getString(i));
    			}
    			list.add(tmp);
    		}
    	}catch( SQLException sqlex ) {
    		
    	}
    	
    	return list;
    }
  
    public Connection getConnection()
    {
    	return this.connection;
    }
    

}  
