package geso.traphaco.distributor.servlets.tdvdoanhso;

import geso.traphaco.distributor.beans.tdvdoanhso.ITDVDoanhso;
import geso.traphaco.distributor.beans.tdvdoanhso.ITdvdoanhsoList;
import geso.traphaco.distributor.beans.tdvdoanhso.imp.TdvdoanhsoList;
import geso.traphaco.distributor.beans.tdvdoanhso.imp.tdvdoanhso;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.Font;
import com.aspose.cells.HorizontalAlignmentType;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class TdvdoanhsoSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private int items = 50;
	private int splittings = 20;
	

    public TdvdoanhsoSvl() 
    {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)) {
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		} else {
		
		ITdvdoanhsoList obj;
		PrintWriter out; 
		
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    out  = response.getWriter();

	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	    String khId = util.getId(querystring);
	    String thang = util.antiSQLInspection(getThang(querystring));
		if(thang == null)
			thang ="";
		String nam = util.antiSQLInspection(getNam(querystring));
		if(nam == null)
			nam ="";
	    obj = new TdvdoanhsoList();
	    String msg="";
	    if (action.equals("delete"))
	    {	   		  	    	
			    	try
		        {
			    		msg= Delete(khId, userId, obj,thang,nam);

					    settingPage(obj);
					    	
					    obj.setUserId(userId);
					    obj.init("");
					    
						session.setAttribute("obj", obj);
								
						String nextJSP = "/TraphacoHYERP/pages/Distributor/TDVDoanhSo.jsp";
						response.sendRedirect(nextJSP);
		        } catch (SQLException e)
		        {
			        e.printStackTrace();
		        }
	    	obj.setMsg(msg);
	    }
	    else
	    	 if (action.equals("chot"))
	    	 {
	    		msg =  chot(khId,thang,nam);

			    settingPage(obj);
			    	
			    obj.setUserId(userId);
			    obj.init("");
			    
				session.setAttribute("obj", obj);
						
				String nextJSP = "/TraphacoHYERP/pages/Distributor/TDVDoanhSo.jsp";
				response.sendRedirect(nextJSP);
	    	 }
	    	 else
	    		 if (action.equals("display"))
		    	 {
	    			    System.out.println("display");
	    			    ITDVDoanhso obj1 = new tdvdoanhso();
	    				obj1.setDdkdId(khId);
	    				obj1.setUserId(userId);
	    				obj1.setThang(thang);
	    				obj1.setNam(nam);
	    				obj1.init();
	    				session.setAttribute("khBean", obj1);
	    				String nextJSP = "/TraphacoHYERP/pages/Distributor/TDVDoanhSoDisplay.jsp";
	    				response.sendRedirect(nextJSP);
		    	 }
	    		 else
	    		 {
	    
	    
					    settingPage(obj);
					    	
					    obj.setUserId(userId);
					    obj.init("");
					    
						session.setAttribute("obj", obj);
								
						String nextJSP = "/TraphacoHYERP/pages/Distributor/TDVDoanhSo.jsp";
						response.sendRedirect(nextJSP);
	    		 }
		}
	}
	public String chot(String id,String thang,String nam)
	{
		dbutils db = new dbutils();
		try {
			db.getConnection().setAutoCommit(false);
			String cmd ="update ctv_doanhso set trangthai = 1 where ctv_fk = "+id+" and thang = "+thang+" and nam = "+nam;
			if(!db.update(cmd))
			{
				db.getConnection().rollback();
				return "Không thể cập nhật "+cmd;
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Lỗi không chốt được ";
		}
		return "";
	}
	public String getThang(String querystring){
	    String id;
	    String tmp;
		if (querystring != null){
	    	if (querystring.contains("&")){
	    		tmp = querystring.split("&")[2];
	    		System.out.println("tmp "+tmp);
	    		id = tmp.split("=")[1];
	    		System.out.println("id  "+id);
	    	}else{
	    		id = "";
	    	}
		}else{
			id = "";
		}
	    return id;
		
	}
	public String getNam(String querystring){
	    String id;
	    String tmp;
		if (querystring != null){
	    	if (querystring.contains("&")){
	    		tmp = querystring.split("&")[3];
	    		System.out.println("tmp "+tmp);
	    		id = tmp.split("=")[1];
	    	}else{
	    		id = "";
	    	}
		}else{
			id = "";
		}
	    return id;
		
	}
	private void settingPage(ITdvdoanhsoList obj) {
		Utility util = new Utility();
		if(getServletContext().getInitParameter("items") != null){
	    	String i = getServletContext().getInitParameter("items").trim();
	    	if(util.isNumeric(i))
	    		items = Integer.parseInt(i);
	    }
	    
	    if(getServletContext().getInitParameter("splittings") != null){
	    	String i = getServletContext().getInitParameter("splittings").trim();
	    	if(util.isNumeric(i))
	    		splittings = Integer.parseInt(i);
	    }
	    
    	obj.setItems(items);
    	obj.setSplittings(splittings);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}else{
		
		OutputStream out = response.getOutputStream();	
			
		ITdvdoanhsoList obj = new TdvdoanhsoList();
	
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	  
	    
	    Utility util = new Utility();
	    userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    
	   
	          
	    if (action.equals("new")){
	    	// Empty Bean for distributor
	    	ITDVDoanhso khBean = (ITDVDoanhso) new tdvdoanhso("");
	    	khBean.setUserId(userId);
	    	khBean.createRS();
	    	
	    	 util = new Utility();
	    	util.getIdNhapp(userId);
	    	// Save Data into session
	    	session.setAttribute("khBean", khBean);
    		String nextJSP = "/TraphacoHYERP/pages/Distributor/TDVDoanhSoNew.jsp";
    		response.sendRedirect(nextJSP);
    		
	    }
	    
	    settingPage(obj);
	    
	    if (action.equals("search"))
	    {	    
	    	
	    	String search = getSearchQuery(request, obj);
	    	//search = search + " and a.npp_fk='" + userId + "' order by a.ten";
	    	
	    	//obj = new KhachhangList(search);
	    	obj.setUserId(userId);
	    	obj.init(search);
				
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
	    		
    		response.sendRedirect("/TraphacoHYERP/pages/Distributor/TDVDoanhSo.jsp");	    		    	
	    }
	    else  if(action.equals("view") || action.equals("next") || action.equals("prev")){
	    	
	    	String search = getSearchQuery(request, obj);
	    	
	    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
	    	obj.setUserId(userId);

	    	obj.init(search);
	    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
	    	session.setAttribute("obj", obj);
	    	response.sendRedirect("/TraphacoHYERP/pages/Distributor/TDVDoanhSo.jsp");
	    }
	    
	    
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, ITdvdoanhsoList obj)
	{		
  		
		Utility util = new Utility();
    	
    	String nppId = util.antiSQLInspection(request.getParameter("nppId"));
    	if ( nppId == null)
    		nppId = "";
    	obj.setNppId(nppId);
    	
    	String ddkdId = util.antiSQLInspection(request.getParameter("ddkdId"));
    	if (ddkdId == null)
    		ddkdId = "";    	
    	obj.setDdkdId(ddkdId);
    	
    	String thang = util.antiSQLInspection(request.getParameter("thang"));
    	if (thang == null)
    		thang = "";    	
    	obj.setThang(thang);
    	
    	String nam = util.antiSQLInspection(request.getParameter("nam"));
    	if (nam == null)
    		nam = "";    	
    	obj.setNam(nam);
    	
    	String trangthai = util.antiSQLInspection(request.getParameter("trangthai")); 	
    	if (trangthai == null)
    		trangthai = "";    		
    	obj.setTrangthai(trangthai);
   	String query = 
			"select distinct CTV_FK AS MATDV,B.TEN AS TenTDV,isnull(A.TRANGTHAI,'') as trangthai,A.THANG,A.NAM,A.NGAYTAO,c.ten as nguoitao,d.ten as nguoisua,A.NGAYSUA "+
			" from CTV_DOANHSO A INNER JOIN Congtacvien B"+
			" ON A.CTV_FK = B.PK_SEQ inner join nhanvien c on c.pk_seq = a.nguoitao inner join nhanvien d on d.pk_seq = a.nguoisua where 1 = 1 ";
    			

     
   	
    	if(ddkdId.length()>0)
    	{
    		query+= " and a.CTV_FK ='"+ddkdId+"' ";
    	}
    	
    	if (trangthai.length() > 0)
    	{
    		query = query + " and a.trangthai='" + trangthai + "'";
    	}
    	if(thang.length() > 0)
    	{
    		query = query + " and a.thang ='" + thang + "'";
    	}
    	if(nam.length() > 0)
    	{
    		query = query + " and a.nam ='" + nam + "'";
    	}
    	System.out.println("Query tìm kiếm: " + query + "\n");

    	
    	return query;
	}	
	
	private String Delete(String id, String userId,ITdvdoanhsoList obj,String thang, String nam) throws SQLException
	{
		dbutils db = new dbutils();
		Utility util = new Utility();
		String nppId=util.getIdNhapp(userId);
		try 
		{
			
			db.getConnection().setAutoCommit(false);
			
			String query = "delete from CTV_DoanhSo where CTV_fk = '"+ id +"' and thang = "+thang+" and nam = "+nam;
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Không thể xóa "+query;
			}
			
		db.getConnection().commit();
		db.getConnection().setAutoCommit(true);
		}
		catch(Exception e) 
		{
			db.getConnection().rollback();
			e.printStackTrace();
			return "Không thể xóa CTV Doanh số,do đã phát sinh dữ liệu!";
		}
		finally
		{
			db.shutDown();	
		}
		return "";
	}
		private String getDateTime()
		{
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			return dateFormat.format(date);
		}
		
}
