package geso.traphaco.center.db.sql;

import java.sql.*;  
import java.lang.String;
import java.io.Serializable;

public class dbutils_syn implements Serializable
{ 
	private static final long serialVersionUID = 1L;
	
	private String username = "salesup";
	private String password = "salesup@123";
	private String url = "jdbc:jtds:sqlserver://115.79.59.115/TraphacoDMS";

	private Connection connection;
	private Statement statement;
	
    public dbutils_syn()
    {  
    	connect();
    }  
      
    
    public boolean connect()  
    {  
    	try 
    	{  
    			
    		Class.forName("net.sourceforge.jtds.jdbc.Driver");
    		connection = DriverManager.getConnection(url, username, password);
    		
    		return true;  
    	}catch ( Exception ex ) {
    		System.out.print(ex.toString());    		
    	}  
        	return false;  
    }  
    public int updateReturnInt(String query)  
    {  
    	try {  
    		statement = connection.createStatement();  
    		return	statement.executeUpdate( query );  
    		
    		 
    	}  
    	catch ( SQLException sqle ) {  
    		return -1;  
    	}  
    }  		

    public ResultSet get(String query)  
    {  
    	try {
    		
    		statement = connection.createStatement();
    		ResultSet rs = statement.executeQuery( query );  
    		return rs;      	 
    	}catch ( SQLException sqle ) {	
    		System.out.print(sqle.toString());
    		return null;
        }                
    }  

    public ResultSet getScrol(String query)  
    {  
    	try {
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
    		statement = connection.createStatement();  
    		statement.executeUpdate( query );  
    		
    		return true;  
    	}  
    	catch ( SQLException sqle ) {  
    		return false;  
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
    
 
  
    public Connection getConnection(){
    	return this.connection;
    }


	public ResultSet getRsByPro(String ProcName, String[] param)
	{
		try
		{
			// CallableStatement cStmt =
			// connection.prepareCall("call getRsKpi_report '2012-01-01' , '2012-03-01'"
			// );

			String SPsql = "EXEC " + ProcName + " "; // for stored proc taking 2
														// parameters
			// java.sql.Connection
			String param_num = "";
			if (param != null)
			{
				for (int i = 0; i < param.length; i++)
				{
					param_num = param_num + "?,";
				}
				param_num = param_num.substring(0, param_num.length() - 1); // cat
																			// dau
																			// ,
																			// cuoi
			}
			//System.out.println(SPsql + param_num);
			PreparedStatement ps = connection.prepareStatement(SPsql + param_num);
			ps.setEscapeProcessing(true);
	 
			if (param != null)
			{
				for (int i = 0; i < param.length; i++)
				{
					ps.setString(i + 1, param[i]);
				}

			}

			ResultSet rs = ps.executeQuery();
			return rs;
		} catch (SQLException sqle)
		{
			sqle.printStackTrace();
			//System.out.print("Loi Trong Qua trinh Lay Resultset,file dbutils.java center  :" + sqle.toString());
			return null;
		}
	}



	public int execProceduce(String procName, String[] param)
	{
		try
		{
			String query = "";
			for (int i = 0; i < param.length; i++)
				query += "?,";
			if (query.length() > 0)
				query += "?"; // tham so dau ra, luu ket qua sau khi thuc thi
								// thu tuc

			// query = "{call exOutParams(?,?)}";
			query = "{call " + procName + "(" + query + ")}";
			//System.out.println("Query la: " + query);

			CallableStatement cstmt = connection.prepareCall(query);
			 
			for (int i = 0; i < param.length; i++)
			{
				cstmt.setString(i + 1, param[i]);
			}

			// Ã„â€˜Ã„Æ’ng kÃƒÂ½ tham sÃ¡Â»â€˜ Ã„â€˜Ã¡ÂºÂ§u ra
			cstmt.registerOutParameter(param.length + 1, java.sql.Types.INTEGER);
			cstmt.execute();

			// LÃ¡ÂºÂ¥y giÃƒÂ¡ trÃ¡Â»â€¹ trÃ¡ÂºÂ£ vÃ¡Â»ï¿½
			int resual = cstmt.getInt(param.length + 1);

			return resual;
		} catch (SQLException e)
		{
			//System.out.println("Loi: " + e.toString());
			return -1;
		}
	}


}  
