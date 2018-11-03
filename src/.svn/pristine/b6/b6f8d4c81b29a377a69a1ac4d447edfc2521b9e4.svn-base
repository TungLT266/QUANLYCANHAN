package geso.traphaco.center.db.sql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ConectionItem
{ 
	private Connection connection;
	private Statement statement;
	private long maximumConectionTime;
	private long startTime;
	private boolean isFree;
	private String userName;
	private String password;
	private String url;
	
	public ConectionItem(long maximumConectionTime, long startTime, boolean isFree, String userName, String password, String url)
	{
		this.maximumConectionTime = maximumConectionTime;
		this.startTime = startTime;
		this.isFree = isFree;
		this.userName = userName;
		this.password = password;
		this.url = url;
		
		connect();
	}
	
	public ConectionItem(String userName, String password, String url)
	{
//		this.maximumConectionTime = 20000;//20 seconds
		this.maximumConectionTime = 200;//20 seconds
		this.startTime = -1;
		this.isFree = true;
		this.userName = userName;
		this.password = password;
		this.url = url;
		
		connect();
	}
	
	public boolean isOverTime()
	{
		long endTime = System.currentTimeMillis();
		long totalConectionTime = endTime - this.startTime;
		
		try {
			if (this.connection.isClosed() == true || ((this.connection.getAutoCommit() == false || isFree == false) && totalConectionTime >= this.maximumConectionTime))
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	public boolean connect()  
    {  
    	try 
    	{  
    		Class.forName("net.sourceforge.jtds.jdbc.Driver");
    		connection = DriverManager.getConnection(url, userName, password);
    		
    		return true;  
    	}catch ( Exception ex ) {
    		ex.printStackTrace();  		
    	}  
    	
    	return false;  
    }  
	
	public boolean setAutoCommit(boolean isAuToCommit)
    {
    	try
    	{
    		this.connection.setAutoCommit(isAuToCommit);
	    	if (isAuToCommit == false)
	    	{
    			this.isFree = false;
    			this.startTime = System.currentTimeMillis();
	    	}
	    	else
	    	{
    			this.isFree = true;
    			this.startTime = -1;
	    	}
    	}catch (Exception e) {
			e.printStackTrace();
		}
    	return true;
    }
	
	public boolean commit()
    {
    	try
    	{
    		this.connection.commit();
    		this.connection.setAutoCommit(true);
			this.isFree = true;
			this.startTime = -1;
    	}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    	return true;
    }
	
	public boolean rollback()
    {
    	try
    	{
    		this.connection.rollback();
    		this.connection.setAutoCommit(true);
			this.isFree = true;
			this.startTime = -1;
    	}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    	return true;
    }
	
	public boolean kill()
    {
    	try
    	{
    		System.out.println("killing");
//    		this.connection.close();
//    		connection = DriverManager.getConnection(url, userName, password);
    		if (this.connection.isClosed() == true)
    		{
    			connect();
    		}
    		
    		if (this.connection.getAutoCommit() == false)
    		{
    			System.out.println("rollbacking");
    			connection.rollback();
    			this.connection.setAutoCommit(true);
    			System.out.println("rollbacking complete");
    		}
    		
			this.isFree = true;
			this.startTime = -1;
    	}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    	return true;
    }
	
    public ResultSet get(String query)  
    {  
    	boolean isAutoCommit = true;
    	try {
    		isAutoCommit = this.connection.getAutoCommit();
    		if (isAutoCommit == true)
    			this.startTime = System.currentTimeMillis();
    		this.isFree = false;
    		statement = connection.createStatement();
    		ResultSet rs = statement.executeQuery(query);  
    		return rs;      	 
    	}catch ( SQLException sqle) {
    		sqle.printStackTrace();
    		return null;
        }finally{
			if (isAutoCommit == true)
			{
				this.isFree = true;
				this.startTime = -1;
			}
        }
    }  

    
    public ResultSet getScrol(String query)  
    {  
    	try {
    		statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
    		ResultSet rs = statement.executeQuery( query );  
    		return rs;      	 
    	}catch ( SQLException sqle ) {	
    		sqle.printStackTrace();
    		return null;
        }                
    }  
    
    public boolean update(String query)  
    {  
    	boolean isAutoCommit = true;
    	try {
    		isAutoCommit = this.connection.getAutoCommit();
    		if (isAutoCommit == true)
    			this.startTime = System.currentTimeMillis();
    		this.isFree = false;
    		statement = connection.createStatement();  
    		statement.setQueryTimeout(60);
    		statement.executeUpdate( query );
    		
    		return true;      	 
    	}catch ( SQLException sqle) {
    		sqle.printStackTrace();
    		return false;
        }finally{
			if (isAutoCommit == true)
			{
				this.isFree = true;
				this.startTime = -1;
			}
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
    		sqle.printStackTrace();
    		return -1;  
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
		catch (SQLException e) 
		{
			e.printStackTrace();
			return -1;
		}
    }
    
    public ResultSet getRsByPro(String ProcName,String[] param) {
        try{
      
        	String para = "";
       
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
	        
	        PreparedStatement ps = connection.prepareStatement(SPsql+param_num);
	        ps.setEscapeProcessing(true);

	        if(param != null)
			{
				for(int i = 0; i < param.length; i++){
					para+=param[i]+",";
					ps.setString(i+1, param[i]);
				}
				para = para.substring(0, para.length() - 1); //cat dau , cuoi
				
			}
	        ResultSet rs = ps.executeQuery();
	        return rs;
        }  
    	catch ( SQLException sqle ) 
    	{ 
    		sqle.printStackTrace();
    		return null;
    	}  
   }
   
    public String execProceduce2(String procName, String[] param)
    {
    	try {  
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
    	catch ( SQLException sqle ) {  
    		return sqle.toString();  
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
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	
	public Connection getConnection() {
		return connection;
	}
	
	public Statement getStatement() {
		return statement;
	}
	
	public void setStatement(Statement statement) {
		this.statement = statement;
	}
	
	public double getMaximumConectionTime() {
		return maximumConectionTime;
	}
	
	public void setMaximumTime(long maximumConectionTime) {
		this.maximumConectionTime = maximumConectionTime;
	}
	
	public long getConectionTime() {
		return startTime;
	}

	public void setConectionTime(long startTime) {
		this.startTime = startTime;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setIsFree(boolean isFree) {
		this.isFree = isFree;
	}

	public boolean getIsFree() {
		return isFree;
	}
}  
