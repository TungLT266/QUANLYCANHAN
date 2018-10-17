package geso.traphaco.center.servlets.Hethongbanhang;

import geso.traphaco.center.beans.hethongbanhang.IHethongbanhang;
import geso.traphaco.center.beans.hethongbanhang.IHethongbanhangList;
import geso.traphaco.center.beans.hethongbanhang.imp.Hethongbanhang;
import geso.traphaco.center.beans.hethongbanhang.imp.HethongbanhangList;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class HethongbanhangSvl extends HttpServlet 
{
private static final long serialVersionUID = 1L;
	
	
    public HethongbanhangSvl()
    {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		IHethongbanhangList obj;
		PrintWriter out;
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    out  = response.getWriter();
	    	    
	    HttpSession session = request.getSession();	    
	    obj = new HethongbanhangList();
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
				
		String nextJSP = "/TraphacoHYERP/pages/Center/HeThongBanHang.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IHethongbanhangList obj;
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
	    	IHethongbanhang htbhBean = (IHethongbanhang) new Hethongbanhang("");
	    	htbhBean.setUserId(userId);
	    	// Save Data into session
	    	session.setAttribute("htbhBean", htbhBean);
	    	htbhBean.createKbhRs();
    		String nextJSP = "/TraphacoHYERP/pages/Center/HeThongBanHangNew.jsp";
    		response.sendRedirect(nextJSP);
    		
	    }

	    else if (request.getParameter("action").equals("excel")) {
	    	obj = new HethongbanhangList();
	    	obj.init(getSearchQuery(request,obj));
	    }
	    
	    else if (action.equals("search")){
	    	obj = new HethongbanhangList();
	    	String search = getSearchQuery(request,obj);
	    	
	    	obj.init(search);
			obj.setUserId(userId);
			
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
	    		
    		response.sendRedirect("/TraphacoHYERP/pages/Center/HeThongBanHang.jsp");	    	
	    	
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request,IHethongbanhangList obj)
	{
		
		Utility util = new Utility();
			
			String hethongbanhang = util.antiSQLInspection(request.getParameter("HeThongBanHang"));
	    	if ( hethongbanhang == null)
	    		hethongbanhang = "";
	    	obj.setHethongbanhang(hethongbanhang);
	    	
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

			String query = "select a.pk_seq as id, a.ten as htbhTen, a.diengiai, a.trangthai, a.ngaytao, b.ten as nguoitao, a.ngaysua, c.ten as nguoisua from hethongbanhang a, nhanvien b, nhanvien c ";
			query = query + " where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq"; 			
	    	if (hethongbanhang.length()>0){
	    	
				query = query + " and upper(dbo.ftBoDau(a.ten)) like upper(N'%" + util.replaceAEIOU(hethongbanhang) + "%')";
				
	    	}
	    	
	    	if (diengiai.length()>0){
				query = query + " and upper(dbo.ftBoDau(a.diengiai)) like upper(N'%" + util.replaceAEIOU(diengiai) + "%')";
				
	    	}
	  
	    	if(trangthai.length() > 0){
	    		query = query + " and a.trangthai = '" + trangthai + "'";
	    		
	    	}
	    	query = query + "  order by a.ten";
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
		
		IHethongbanhangList obj = new HethongbanhangList();
		
		dbutils db = new dbutils();
//		String sql ="select count(kbh_fk) as num from khachhang where kbh_fk='"+ id + "'";
//		if(kiemtra(sql))
//		{  sql = " select count(kbh_fk) as num from giamsatbanhang where kbh_fk ='"+ id +"'";
//		   System.out.println(sql);
//		   if(kiemtra(sql))
//		   {
				db.update("delete from hethongbanhang_kenhbanhang where htbh_fk = '" + id + "'");
			   	db.update("delete from hethongbanhang where pk_seq = '" + id + "'");
			   	
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
