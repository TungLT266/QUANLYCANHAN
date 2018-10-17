package geso.dms.center.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import geso.dms.db.sql.dbutils;
import geso.traphaco.center.db.sql.Idbutils;

public class Phan_Trang implements IPhan_Trang 
{
	private int crrSplitting;
	private int[] crrSplittings;
	private int[] nextSplittings;
	private int splittings;
	private int nxtApprSplitting;
	private int items;
	private int theLastSpliting;
	private String action;
	private String orderByColumn;
	private int crrApprSplitting;
	private ResultSet splittingData;
	private String splittingData_list;
	private String cp = "";
	
	public Phan_Trang(){
		
		setItems(30);
		setSplittings(10);
		setNxtApprSplitting(1);
	}
	public Phan_Trang(int items, int splittings){
		
		setItems(items);
		setSplittings(splittings);
		setNxtApprSplitting(1);
	}
	
	private	boolean isNumeric(String input){ 
		boolean result = true;
		char[] all = input.toCharArray();
		
		for(int i = 0; i < all.length;i++) {
		   if(!(Character.isDigit(all[i]))) {
			   result = false;
		   }
		}
		return result;
	}
	
	
	public void settingPage(ServletContext svlCtxt) {

		
		if(svlCtxt.getInitParameter("items") != null){
	    	String i = svlCtxt.getInitParameter("items").trim();
	    	if(isNumeric(i))
	    		items = Integer.parseInt(i);
	    }
	    
	    if(svlCtxt.getInitParameter("splittings") != null){
	    	String i = svlCtxt.getInitParameter("splittings").trim();
	    	if(isNumeric(i))
	    		splittings = Integer.parseInt(i);
	    }
	    
    	setItems(items);
    	setSplittings(splittings);
	}
	
	
	public ResultSet createSplittingData(int items, int splittings, String orderByColumn, String query){
		
		setItems(items);
		this.items = items;
		ResultSet rs = createData(orderByColumn, query);// db.get(query);
		
		setCrrSplitting(getTheLastSplitting()<=splittings?getTheLastSplitting():splittings);
		return rs;
	}
	
	public String createSplittingData_List(int items, int splittings, String orderByColumn, String query){
		//1
		setItems(items);
		this.items = items;
		String rs = createData_list(orderByColumn, query);// db.get(query);
		
		setCrrSplitting(getTheLastSplitting()<=splittings?getTheLastSplitting():splittings);
		return rs;
	}
	public String createSplittingData_ListNew(Idbutils dbutils,int items, int splittings, String orderByColumn, String query){
		//1
		setItems(items);
		this.items = items;
		String rs = createData_listNew(dbutils,orderByColumn, query);// db.get(query);
		
		setCrrSplitting(getTheLastSplitting()<=splittings?getTheLastSplitting():splittings);
		return rs;
	}
	
	
	
	public void setCrrApprSplitting(int crrApprSplitting) {
		this.crrApprSplitting = crrApprSplitting;
	}
	
	public int getCrrApprSplitting() {
		return crrApprSplitting;
	}

	private int[] createNextSplittings(){
		
		int[] list = new int[crrSplittings.length];
		 if(action.equals("next"))
	 	    {
			 	
			 if((this.crrSplittings[crrSplittings.length - 1] == this.theLastSpliting && this.nxtApprSplitting > this.crrApprSplitting)
 					|| this.crrSplittings[0] == 1 && (this.nxtApprSplitting < this.crrApprSplitting))
 				list = crrSplittings;
 			else

	 	    	for(int i = 0; i < crrSplittings.length; i++)
	 	    		list[i] = crrSplittings[i] + 1;
	 	    	this.nxtApprSplitting++;

	 	    }
	 	    
	 	    if(action.equals("prev"))
	 	    {
	 	    	if((this.crrSplittings[crrSplittings.length - 1] == this.theLastSpliting && this.nxtApprSplitting > this.crrApprSplitting)
    					|| this.crrSplittings[0] == 1 && (this.nxtApprSplitting < this.crrApprSplitting))
    				list = crrSplittings;
    			else

	 	    	for(int i = 0; i < crrSplittings.length; i++)
	 	    		list[i] = crrSplittings[i] - 1;
	 	    	this.nxtApprSplitting--;

	 	    }
	    	
	    	if(action.equals("view"))
		    {
		    	if(nxtApprSplitting == 1)
		    	{
		    		
		    		for(int i = 0; i< crrSplittings.length; i++)
		    			list[i] = i+1;
		    	}
		    	else
		    	{
		    		if(nxtApprSplitting == -1)
		    		{
		    			//int pos = obj.getLastPage() / n; //so dong tren 1 trang
		    			
		    			int j = 0;
		    			int k = crrSplittings.length;
		    			while(j < crrSplittings.length)
		    			{
		    				list[j++] = (theLastSpliting+1) - k--;

		    			}
		    			this.nxtApprSplitting = this.theLastSpliting;
		    		}
		    		else
		    		{
		    			//for(int i = 0; i < crrSplittings.length; i++)
				    	//	list[i] = crrSplittings[i] + 1;
		    			if((this.crrSplittings[crrSplittings.length - 1] == this.theLastSpliting && this.nxtApprSplitting > this.crrApprSplitting)
		    					|| this.crrSplittings[0] == 1 && (this.nxtApprSplitting < this.crrApprSplitting))
		    				list = crrSplittings;
		    			else if(this.nxtApprSplitting > this.crrApprSplitting)
					    	for(int i = 0; i < crrSplittings.length; i++)
					    		list[i] = crrSplittings[i] + 1;
		    			else if(this.nxtApprSplitting < this.crrApprSplitting)
		    				for(int i = 0; i < crrSplittings.length; i++)
					    		list[i] = crrSplittings[i] - 1;
		    			else 
		    				list = crrSplittings;
		    		}
		    	}
		    	
		    	//this.apprSplitting = apprSplitting;
		    	//obj.setUserId(userId);
		    }
	    	crrSplittings = list;
	    	return list;
	}

	public int getCrrSplitting(){
		return this.crrSplitting;
	}
	
	public void setCrrSplitting(int crrSplitting){
		this.crrSplitting = crrSplitting;
		this.crrSplittings = new int[crrSplitting != -1? crrSplitting: theLastSpliting];
		for(int i = 0; i < this.crrSplitting; i++)
			this.crrSplittings[i]=i+1;
	}

	
	public void setSplittingData(String query){
		
		this.splittingData = createSplittingData(query);
	}
	
	public void setSplittingData_list(String query){
		
		this.splittingData_list = createSplittingData_list(query);
	}
	
	
	
	public ResultSet getSplittingData(){
		return this.splittingData;
	}
	
	public String getSplittingData_list(){
		return this.splittingData_list;
	}
	
	
	private ResultSet createSplittingData(String query)
	{
		if(this.crrSplitting > this.theLastSpliting)
		{
			setCrrSplitting(this.theLastSpliting);
		}
		
		if(this.theLastSpliting > 0)
		{

			query = changeQuery(query);
			
			String order = "";
			int k = this.orderByColumn.indexOf(",");  //cach cu chi sort duoc theo 1 columns.
			if(k > 0)
			{
				//order =  " order by " + this.orderByColumn.substring(k + 1, this.orderByColumn.length());
				order =  " order by " + this.orderByColumn;
			}
			
			//bo sung them sort theo nhieu dieu kien
			
	    	if (this.nxtApprSplitting > 0)
	    	{
	    		int pos = ((this.nxtApprSplitting - 1) * this.items);
	    		query ="select top(" + this.items + ") * from( " + query + ") list where _no > '" + pos + "' ";	
	    		
	    		if(order.length() > 0)
	    		{
	    			query = "select * from (" + query + ") tab " + order;
	    		}
	    	}
	    	if(this.nxtApprSplitting == -1)
	    	{
	    		
	    		int pos = (this.theLastSpliting - 1) * this.items;
	    		query = "select top(" + this.items + ") * from( " +  query + ") list where _no > '" + Integer.toString(pos) + "' ";
	    		
	    		if(order.length() > 0)
	    		{
	    			query = "select * from (" + query + ") tab " + order;
	    		}
	    	}

			dbutils db = new dbutils();
			try 
			{
				System.out.println("sql phan trang: " + this.nxtApprSplitting + ": " + query);
				return db.get(query);
				
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				return null;
			}
		}
		else return null;
	}
	
	public void setTheLastSplitting(String query)
	{//3
		try 
		{
			this.theLastSpliting = createTheLastSplitting(query);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	private String changeQuery(String query)
	{
		//luonghv
		String order = "";
		int pos = this.orderByColumn.indexOf(",");  //cach cu chi sort duoc theo 1 columns.
		if(pos > 0)
			order = this.orderByColumn.substring(0, pos);
		else
			order = this.orderByColumn;
		
		//query = "select row_number() over(order by addNo." + this.orderByColumn + ") as _no, addNo.* from (" + query + " ) addNo";
		query = "select row_number() over(order by addNo." + order + ") as _no, addNo.* from (" + query + " ) addNo";
		return query;
	}
	
	private int createTheLastSplitting(String query) throws Exception 
	{
		query = changeQuery(query);
		
		dbutils db = new dbutils();
		String q = "select count(_no) as c from ("+query+") sc";
		System.out.println("__TONG SO DONG: " + q);
		
		int count = 1;
		try 
		{
			ResultSet rs = db.get(q);
			if(rs != null){
				rs.next();
				count = Integer.parseInt(rs.getString("c"));
				rs.close();
			}
		}
		catch (SQLException e) {e.printStackTrace();}
		count = count % items == 0? (count / this.items): (count / this.items) + 1;
		return count;
	}

	
	public int getSplittings() 
	{
		return this.splittings;
	}

	public void setSplittings(int splittings) 
	{
		this.splittings = splittings;
	}

	
	public int getNxtApprSplitting() 
	{
		return this.nxtApprSplitting;
	}

	public void setNxtApprSplitting(int nxtApprSplitting)
	{
		this.nxtApprSplitting = nxtApprSplitting;
	}

	public int[] getCrrSplittings() 
	{
		return this.crrSplittings;
	}

	public void setCrrSplittings(int[] crrSplittings) 
	{
		this.crrSplittings = crrSplittings;
	}

	public void setItems(int items) 
	{
		this.items = items;
	}

	public int getItems() 
	{
		return this.items;
	}

	public int getTheLastSplitting() 
	{
		return this.theLastSpliting;
	}

	
	public void setNextSplittings() 
	{
		int[] tmp;
		if(action == null)
		{
			 tmp = getCrrSplittings();
		}
		else
		{ 
			 tmp = createNextSplittings();
		}
		
		this.nextSplittings = tmp;
	}

	public int[] getNextSplittings() 
	{
		return this.nextSplittings;
	}
	
	public void setAction(String action) 
	{
		this.action = action;
	}

	public String getAction() 
	{
		return this.action;
	}
	
	public void setOrderByColumn(String orderByColumn) 
	{
		this.orderByColumn = orderByColumn;
	}
	
	public String getOrderByColumn() 
	{
		return this.orderByColumn;
	}
	
	
	public void setAttribute(HttpServletRequest request, String action, String listName, String crrApprSplittingName, String nxtApprSplittingName)
	{
	    String[] str_crrSplittings = request.getParameterValues(listName);
	    int[] crrSplittings = null;
	    setAction(action);

	    if(str_crrSplittings != null)
	    {
		    crrSplittings = new int[str_crrSplittings.length];
		    for(int i = 0; i < str_crrSplittings.length; i++)
		    	crrSplittings[i] = Integer.parseInt(str_crrSplittings[i]);
		    int crrApprSplitting = Integer.parseInt(request.getParameter(crrApprSplittingName));
		    int nxtApprSplitting = Integer.parseInt(request.getParameter(nxtApprSplittingName));
	    	setCrrApprSplitting(crrApprSplitting);
	    	setNxtApprSplitting(nxtApprSplitting);
	    	setCrrSplittings(crrSplittings);
	    	
	    	//System.out.println("kiem tra: "+crrApprSplitting+" va " + nxtApprSplitting);
	    }
	}
	
	
	public ResultSet createData(String orderByColumn, String query)
	{
		this.orderByColumn = orderByColumn;
		setOrderByColumn(orderByColumn);
		setTheLastSplitting(query);
		setSplittingData(query);
		ResultSet rs =  getSplittingData();
		return rs;
	}
	
	public String createData_list(String orderByColumn, String query)
	{//2
		this.orderByColumn = orderByColumn;
		setOrderByColumn(orderByColumn);
		setTheLastSplitting(query);
		setSplittingData_list(query);
		String rs =  getSplittingData_list();
		return rs;
	}
	public String createData_listNew(Idbutils dbutils ,String orderByColumn, String query)
	{//2
		this.orderByColumn = orderByColumn;
		setOrderByColumn(orderByColumn);
		setTheLastSplittingNew(dbutils,query);
		setSplittingData_list(query);
		String rs =  getSplittingData_list();
		return rs;
	}
	

	private String createSplittingData_list(String query)
	{//1
		String query_kq= "";
		if(this.crrSplitting > this.theLastSpliting)
		{
			setCrrSplitting(this.theLastSpliting);
		}
		
		if(this.theLastSpliting > 0)
		{

			query = changeQuery(query);
			
			String order = "";
			int k = this.orderByColumn.indexOf(",");  //cach cu chi sort duoc theo 1 columns.
			if(k > 0)
			{
				//order =  " order by " + this.orderByColumn.substring(k + 1, this.orderByColumn.length());
				order =  " order by " + this.orderByColumn;
			}
			
			//bo sung them sort theo nhieu dieu kien
			
	    	if (this.nxtApprSplitting > 0)
	    	{
	    		int pos = ((this.nxtApprSplitting - 1) * this.items);
	    		query_kq ="select top(" + this.items + ") * from( " + query + ") list where _no > '" + pos + "' ";	
	    		
	    		if(order.length() > 0)
	    		{
	    			query_kq = "select * from (" + query_kq + ") tab " + order;
	    		}
	    	}
	    	if(this.nxtApprSplitting == -1)
	    	{
	    		
	    		int pos = (this.theLastSpliting - 1) * this.items;
	    		query_kq = "select top(" + this.items + ") * from( " +  query + ") list where _no > '" + Integer.toString(pos) + "' ";
	    		
	    		if(order.length() > 0)
	    		{
	    			query_kq = "select * from (" + query_kq + ") tab " + order;
	    		}
	    	}

			try 
			{
				System.out.println("sql phan trang list: " + this.nxtApprSplitting + ": " + query_kq);
				return query_kq;
				
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				return null;
			}
		}
		else return null;
	}
	public ResultSet createSplittingDataNew(Idbutils db, int items, int splittings, String orderByColumn, String query){
		setItems(items);
		this.items = items;
		ResultSet rs = createDataNew(db, orderByColumn, query);// db.get(query);
		
		setCrrSplitting(getTheLastSplitting()<=splittings?getTheLastSplitting():splittings);
		return rs;
	}
	
	public ResultSet createDataNew(Idbutils db, String orderByColumn, String query)
	{
		this.orderByColumn = orderByColumn;
		setOrderByColumn(orderByColumn);
		setTheLastSplittingNew(db, query);
		setSplittingDataNew(db, query);
		ResultSet rs =  getSplittingData();
		return rs;
	}
	
	public void setTheLastSplittingNew(Idbutils db, String query)
	{
		try 
		{
			this.theLastSpliting = createTheLastSplittingNew(db, query);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	private int createTheLastSplittingNew(Idbutils db,String query) throws Exception 
	{
		int count = 1;
		if (query.trim().length() > 0)
		{
			query = changeQuery(query);
			String q = "select count(_no) as c from ("+query+") sc";
			try 
			{
				ResultSet rs = db.get(q);
				if(rs != null){
					rs.next();
					count = Integer.parseInt(rs.getString("c"));
					rs.close();
				}
			}
			catch(Exception e) {e.printStackTrace();}
		}
	//	System.out.println("____"+q);
		
//		System.out.println("_____"+this.items+"___Count"+count);
		
		count = count % items == 0? (count / this.items): (count / this.items) + 1;
		
	//	System.out.println("_____"+this.items+"___Count"+count);
		
		return count;
	}

	public void setSplittingDataNew(Idbutils db, String query){
		
		this.splittingData = createSplittingDataNew(db, query);
	}

	private ResultSet createSplittingDataNew(Idbutils db, String query)
	{
		if(this.crrSplitting > this.theLastSpliting)
		{
			setCrrSplitting(this.theLastSpliting);
		}
		
		if(this.theLastSpliting > 0)
		{
	
			query = changeQuery(query);
			
			String order = "";
			int k = this.orderByColumn.indexOf(",");  //cach cu chi sort duoc theo 1 columns.
			if(k > 0)
			{
				//order =  " order by " + this.orderByColumn.substring(k + 1, this.orderByColumn.length());
				order =  " order by " + this.orderByColumn;
			}
			
			//bo sung them sort theo nhieu dieu kien
			
	    	if (this.nxtApprSplitting > 0)
	    	{
	    		int pos = ((this.nxtApprSplitting - 1) * this.items);
	    		query ="select top(" + this.items + ") * from( " + query + ") list where _no > '" + pos + "' ";	
	    		
	    		if(order.length() > 0)
	    		{
	    			query = "select * from (" + query + ") tab " + order;
	    		}
	    	}
	    	if(this.nxtApprSplitting == -1)
	    	{
	    		int pos = (this.theLastSpliting - 1) * this.items;
	    		query = "select top(" + this.items + ") * from( " +  query + ") list where _no > '" + Integer.toString(pos) + "' ";
	    		
	    		if(order.length() > 0)
	    		{
	    			query = "select * from (" + query + ") tab " + order;
	    		}
	    	}
	
			try 
			{
				System.out.println("[Phan_Trang.createSplittingData] sql phan trang " + this.nxtApprSplitting + ": " + query);
				return db.get(query);
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				return null;
			}
		}
		else return null;
	}
	


}
