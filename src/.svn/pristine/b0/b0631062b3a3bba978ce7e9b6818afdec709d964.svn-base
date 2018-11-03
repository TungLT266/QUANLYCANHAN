package geso.traphaco.center.db.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class dbutilsPool implements Idbutils
{ 
	private static final long serialVersionUID = 1L;
	private static List<ConectionItem> conectionList;
	private static String username = "salesup";
	private static String password = "sales@up@1235";
	private static String url = "jdbc:jtds:sqlserver://115.79.59.115/TraphacoERP";
	private static int conectionNumberMax = 1;
	
	private ConectionItem connection;
	private Statement statement;
	
    public dbutilsPool()
    {  
    	connect();
    }  
      
    public boolean connect()  
    {  
    	try 
    	{  
    		if (conectionList == null)
    			conectionList = new ArrayList<ConectionItem>();
    		return true;  
    	}catch (Exception ex) {
    		ex.printStackTrace();    		
    	}  
        	return false;  
    }  
    
    public boolean setAutoCommit(boolean isAuToCommit)
    {
    	try
    	{
	    	if (isAuToCommit == false)
	    	{
	    		if (this.connection == null)
	    		{
	    			this.connection = getFreeConection();
	    			this.connection.setAutoCommit(isAuToCommit);
	    		}
	    	}
	    	else if (isAuToCommit == true && this.connection != null)
	    	{
	    		this.connection.setAutoCommit(isAuToCommit);
	    		this.connection = null;
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
	    	if (this.connection == null)
	    		return false;
	    	this.connection.commit();
	    	this.connection = null;
    	}catch (Exception e) {
			e.printStackTrace();
		}
    	return true;
    }
    
    public boolean rollback()
    {
    	try
    	{
	    	if (this.connection == null)
	    		return false;
	    	this.connection.rollback();
	    	this.connection = null;
    	}catch (Exception e) {
			e.printStackTrace();
		}
    	return true;
    }
    
    public static boolean killOverTimeTrans()
    {
    	boolean result = false;
    	for (ConectionItem item : conectionList)
    	{
    		if (item.isOverTime() == true)
    		{
    			item.kill();
    			result = true;
    		}
    	}
    	return result;
    }
    
    public static ConectionItem getFreeConection()
    {
    	killOverTimeTrans();
    	for (ConectionItem item : conectionList)
    	{
    		if (item.getIsFree() == true)
    			return item;
    	}
    	
    	if (conectionNumberMax > conectionList.size())
    	{
    		ConectionItem item = new ConectionItem(username, password, url);
    		conectionList.add(item);
    		return item;
    	}
    	
    	//Pause for 4 seconds
        try {
        	if (killOverTimeTrans() == false)
        		Thread.sleep(4000);
			return getFreeConection();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	return null;
    }

    public ResultSet get(String query)  
    {  
		ConectionItem item = this.connection;
		if (item == null)
			item = getFreeConection();
		
		if (item != null)
			return item.get(query);
		return null;
    }  
    
    public ResultSet getScrol(String query)  
    {  
//    	try {
//    		statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
//    		ResultSet rs = statement.executeQuery( query );  
//    		return rs;      	 
//    	}catch ( SQLException sqle ) {	
//    		System.out.print(sqle.toString());
    		return null;
//        }                
    }  
//    
    public boolean update(String query)  
    {  
    	ConectionItem item = this.connection;
		if (item == null)
			item = getFreeConection();
		
		if (item != null)
			return item.update(query);
		return false;
    } 
//    
    public int updateReturnInt(String query)  
    {  
//    	try {  
//    		statement = connection.createStatement();  
//    		statement.setQueryTimeout(60);
//    		
//    		return	statement.executeUpdate( query ); 
//    		
//    	}  
//    	catch ( Exception sqle ) {
//    		System.out.println("querry: "+query);
//    		sqle.printStackTrace();
    		return -1;  
//    	}  
    }  		
//    
    public int execProceduce(String procName, String[] param)
    {
//		try 
//		{
//			String query = "";
//	    	for(int i = 0; i < param.length; i++)
//	    		query += "?,";
//	    	if(query.length() > 0)
//	    		query += "?"; //tham so dau ra, luu ket qua sau khi thuc thi thu tuc
//	    	
//	    	//query = "{call exOutParams(?,?)}";
//	    	query = "{call " + procName + "(" + query + ")}";
//	    	System.out.println("Query la: " + query);
//	    	
//			CallableStatement cstmt = connection.prepareCall(query);
//			for(int i = 0; i < param.length; i++)
//			{
//				cstmt.setString(i + 1, param[i]);
//			}
//
//	    	//đăng ký tham số đầu ra
//			cstmt.registerOutParameter(param.length + 1, java.sql.Types.INTEGER);
//	    	cstmt.execute();
//
//	    	//Lấy giá trị trả về
//	    	int resual = cstmt.getInt(param.length + 1);
//	    
//	    	return resual;
//		} 
//		catch (SQLException e) 
//		{
//			e.printStackTrace();
//			System.out.println("Loi: " + e.toString());
			return -1;
//		}
    }
//    
    public ResultSet getRsByPro(String ProcName,String[] param) {
//        try{
//      
//        	String para="";
//       
//        String SPsql = "EXEC "+ ProcName +" ";   // for stored proc taking 2 parameters
//         // java.sql.Connection 
//        String param_num="";
//        if(param != null)
//		{
//			for(int i = 0; i < param.length; i++){
//				param_num=param_num+"?,";
//			}
//			param_num = param_num.substring(0, param_num.length() - 1); //cat dau , cuoi
//		}
//        
//        PreparedStatement ps = connection.prepareStatement(SPsql+param_num);
//        ps.setEscapeProcessing(true);
//       // ps.setQueryTimeout(<timeout value>);
//      
//        if(param != null)
//		{
//			for(int i = 0; i < param.length; i++){
//				para+=param[i]+",";
//				ps.setString(i+1, param[i]);
//			}
//			para = para.substring(0, para.length() - 1); //cat dau , cuoi
//			
//		}
//        System.out.println("___Param: " + SPsql+para);
//        ResultSet rs = ps.executeQuery();
//        return rs;
//        }  
//    	catch ( SQLException sqle ) 
//    	{ 
//    		sqle.printStackTrace();
    		return null;
//    	}  
   }
//   
    public String execProceduce2(String procName, String[] param)
    {
//    	try {  
//    		statement = connection.createStatement();  
//    		  
//    		String query = procName;
//    		
//    		if(param != null)
//    		{
//    			String paramList = "";
//    			for(int i = 0; i < param.length; i++)
//    				paramList += "'" + param[i] + "',";
//    			paramList = paramList.substring(0, paramList.length() - 1); //cat dau , cuoi
//    			
//    			query += " " + paramList;
//    		}
//    		
//    		statement.executeUpdate( query );
//    			
    		return "";  
//    	}  
//    	catch ( SQLException sqle ) {  
//    		return sqle.toString();  
//    	}  
    }
//     
    public boolean shutDown()  
    {  
//    	try {  
//    		if(statement != null)
//    			statement.close();
//    		
//    		if(connection != null)
//    			connection.close();  
//    		
    		return true;  
//    	}  
//    	catch ( SQLException sqlex ) {  
//    		return false;  
//    	}  
    }  
//    
    public List<List<String>> RStoList(ResultSet rs, int num){
//    	List<List<String>> list = new ArrayList<List<String>>();
//
//    	try{
//    		while (rs.next()){
//    			List<String> tmp = new ArrayList<String>();
//    			for (int i = 1; i<= num; i++){
//    				tmp.add(rs.getString(i));
//    			}
//    			list.add(tmp);
//    		}
//    	}catch( SQLException sqlex ) {
//    		
//    	}
//    	
//    	return list;
    	return null;
    }
  
	public static void main(String[] args) {
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!Start!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

		dbutilsPool db = new dbutilsPool();
		
		db.get("select 1");
		
		try {
			dbutilsPool db1 = new dbutilsPool();
			db1.setAutoCommit(false);
			db1.update("update ERP_PHATSINHKETOAN set ngayChungTu = '2' where pk_seq = 117676");
//			db1.roolback();
//			db1.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		db.gET("SELECT 1");
//		
//		DB.UPDATE("UPDATE ERP_PHATSINHKETOAN SET NGAYCHUNGTU = '3' WHERE PK_SEQ = 117676");
//		
//		DB.GET("select 1");
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!complete!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
	}

	@Override
	public Connection getConnection() {
		return null;
	}
}  