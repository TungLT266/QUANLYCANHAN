package geso.traphaco.center.servlets.Nhomkenh;

import geso.traphaco.center.beans.nhomkenh.INhomkenh;
import geso.traphaco.center.beans.nhomkenh.INhomkenhList;
import geso.traphaco.center.beans.nhomkenh.imp.Nhomkenh;
import geso.traphaco.center.beans.nhomkenh.imp.NhomkenhList;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class NhomkenhSvl  extends HttpServlet 
{
private static final long serialVersionUID = 1L;
	
	
    public NhomkenhSvl()
    {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		INhomkenhList obj;
		PrintWriter out;
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    out  = response.getWriter();
	    	    
	    HttpSession session = request.getSession();	    
	    obj = new NhomkenhList();
	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	    String htbhId = util.getId(querystring);

	    if (action.equals("delete")){	   		  	    	
	    	Delete(htbhId);
	    	out.print(htbhId);
	    }
	   	
	   
	    obj.setUserId(userId);
	    obj.init("");
		session.setAttribute("obj", obj);
				
		String nextJSP = "/TraphacoHYERP/pages/Center/NhomKenh.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		INhomkenhList obj;
		//PrintWriter out;
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    //out = response.getWriter();
	    
	    Utility util = new Utility();
	    
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    //out.println(action); 
	        
	    
	    if (action.equals("new")){
	    	// Empty Bean for distributor
	    	INhomkenh nkBean = (INhomkenh) new Nhomkenh("");
	    	nkBean.setUserId(userId);
	    	// Save Data into session
	    	session.setAttribute("nkBean", nkBean);
	    	nkBean.createKbhRs();
    		String nextJSP = "/TraphacoHYERP/pages/Center/NhomKenhNew.jsp";
    		response.sendRedirect(nextJSP);
    		
	    }

	    else if (request.getParameter("action").equals("excel")) {
	    	obj = new NhomkenhList();
	    	obj.init(getSearchQuery(request,obj));
	    }
	    
	    else if (action.equals("search")){
	    	obj = new NhomkenhList();
	    	String search = getSearchQuery(request,obj);
	    	System.out.println("nhap lai " + search);
	    	obj.init(search);
			obj.setUserId(userId);
			
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
	    		
    		response.sendRedirect("/TraphacoHYERP/pages/Center/NhomKenh.jsp");	    	
	    	
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request,INhomkenhList obj)
	{
		
		Utility util = new Utility();
			
			String Nhomkenh = util.antiSQLInspection(request.getParameter("nhomkenh"));
	    	if ( Nhomkenh == null)
	    		Nhomkenh = "";
	    	obj.setNhomkenh(Nhomkenh);
	    	
	    	String diengiai = util.antiSQLInspection(request.getParameter("DienGiai"));
	    	if (diengiai == null)
	    		diengiai = "";    	
	    	obj.setDiengiai(diengiai);
	    	
	    	String trangthai = util.antiSQLInspection(request.getParameter("TrangThai"));   	
	    	if (trangthai == null)
	    		trangthai = "";    	
		
	    	if (trangthai.equals("2"))
	    		trangthai = "";
	    	
	    	obj.setTrangthai(trangthai);

			String query = "select a.pk_seq as id, a.ten as nkTen, a.diengiai, a.trangthai, a.ngaytao, b.ten as nguoitao, a.ngaysua, c.ten as nguoisua from Nhomkenh a, nhanvien b, nhanvien c ";
			query = query + " where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq"; 			
	    	if (Nhomkenh.length()>0){
	    	
				query = query + " and upper(dbo.ftBoDau(a.ten)) like upper(N'%" + util.replaceAEIOU(Nhomkenh) + "%')";
				
	    	}
	    	
	    	if (diengiai.length()>0){
				query = query + " and upper(dbo.ftBoDau(a.diengiai)) like upper(N'%" + util.replaceAEIOU(diengiai) + "%')";
				
	    	}
	  
	    	if(trangthai.length() > 0){
	    		query = query + " and a.trangthai = '" + trangthai + "'";
	    		
	    	}
	    	query = query + "  order by a.ten";
	    	System.out.println("tim kiem " + query);
	    	return query;
		}	
//	boolean kiemtra(String sql)
//	{dbutils db =new dbutils();
//    	ResultSet rs = db.get(sql);
//		try {//kiem tra ben san pham
//		while(rs.next())
//		{ if(rs.getString("num").equals("0"))
//		   return true;
//		}
//		} catch(Exception e) {
//		
//			e.printStackTrace();
//		}
//		 return false;
//	}
	private void Delete(String id)
	{
		
		INhomkenhList obj = new NhomkenhList();
		
		dbutils db = new dbutils();
//		String sql ="select count(kbh_fk) as num from khachhang where kbh_fk='"+ id + "'";
//		if(kiemtra(sql))
//		{  sql = " select count(kbh_fk) as num from giamsatbanhang where kbh_fk ='"+ id +"'";
//		   System.out.println(sql);
//		   if(kiemtra(sql))
//		   {
				db.update("delete from Nhomkenh_kenhbanhang where nk_fk = '" + id + "'");
			   	db.update("delete from Nhomkenh where pk_seq = '" + id + "'");
			   	
				db.shutDown();
//		   }
//		   else
//			   obj.setMsg("Kenh nay da co giam sat ban hang roi, khong the xoa duoc");
//		}
//		else
//			obj.setMsg("Kenh nay da co khach hang roi, khong the xoa duoc");
	
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
}
