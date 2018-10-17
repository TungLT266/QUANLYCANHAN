package geso.erp.servlets.lapkehoach;

import geso.erp.beans.lapkehoach.*;
import geso.erp.beans.lapkehoach.imp.*;
import geso.dms.db.sql.dbutils;
import geso.dms.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpKehoachtongtheSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	
    public ErpKehoachtongtheSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out  = response.getWriter();
	    
	    HttpSession session = request.getSession();	
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    IErpKehoachtongtheList obj = new ErpKehoachtongtheList();
	    obj.setUserId(userId);
	    obj.setCtyId(ctyId);
	    
	    String action = util.getAction(querystring);
	    String khlId = util.getId(querystring);
	    String msg = "";
	    
	    if(action.trim().equals("delete"))
	    {
	    	msg = DeleteKeHoach(khlId);
	    }
	    
	    obj.init("");
	    obj.setMsg(msg);
		session.setAttribute("obj", obj);
	    
	    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKeHoachTongThe.jsp";
		response.sendRedirect(nextJSP);
	}

	private String DeleteKeHoach(String khlId) 
	{
		dbutils db = new dbutils();
    	
    	try 
    	{
			db.getConnection().setAutoCommit(false);
			
			String query = "update ERP_KeHoachTongThe set trangthai = '2' where pk_seq = '" + khlId + "'";
			if(!db.update(query))
	    	{
	    		db.getConnection().rollback();
	    		return "Không thể xóa ERP_KeHoachTongThe";
	    	}
			
			query = "update ERP_LENHSANXUATDUKIEN set trangthai = '2' where kehoach_fk = '" + khlId + "'";
			if(!db.update(query))
	    	{
	    		db.getConnection().rollback();
	    		return "Không thể xóa ERP_LENHSANXUATDUKIEN";
	    	}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
    	catch (Exception e) {}
    	
    	db.shutDown();
    	
    	return "";
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out  = response.getWriter();
	    
	    HttpSession session = request.getSession();	
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    out = response.getWriter();
	    Utility util = new Utility();
	    
	    String userId = util.antiSQLInspection(request.getParameter("userId"));     
	    IErpKehoachtongtheList obj;
	    	    
		String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    
	    if(action.equals("new"))
	    {
    		IErpKehoachtongthe khl = new ErpKehoachtongthe();
    		khl.setCtyId(ctyId);
    		khl.createRs();
    		khl.setUserId(userId);

	    	session.setAttribute("khlBean", khl);  	
    		session.setAttribute("userId", userId);
		
    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpKeHoachTongTheNew.jsp");
	    }
	    else
	    {
	    	obj = new ErpKehoachtongtheList();
		    obj.setUserId(userId);
		    obj.setCtyId(ctyId);
		    
	    	String search = getSearchQuery(request, obj);
	    	
	    	obj.setUserId(userId);
	    	obj.init(search);
				
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
		
    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpKeHoachTongThe.jsp");	
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpKehoachtongtheList obj) 
	{
		Utility util = new Utility();
		String tungay = util.antiSQLInspection(request.getParameter("tungay"));
		if(tungay == null)
			tungay = "";
		obj.setTungay(tungay);
		
		String denngay = util.antiSQLInspection(request.getParameter("denngay"));
		if(denngay == null)
			denngay = "";
		obj.setDenngay(denngay);
		
		
		String sql = "select a.pk_seq, a.ngaylap, a.thang, a.nam, b.ten as nguoitao  " +
	 	  			 "from ERP_KeHoachTongThe a " +
	 	  			 "inner join NhanVien b on a.nguoitao = b.pk_seq  " +	 	  			 
	 	  			 "where a.pk_seq > 0 and a.congty_fk = " + obj.getCtyId() + " ";
		
		if(tungay.length() > 0)
			sql += " and a.ngaylap >= '" + tungay + "' ";
		if(denngay.length() > 0)
			sql += " and a.ngaylap <= '" + denngay + "' ";
		
		sql += " order by a.ngaylap desc ";
		
		return sql;
	}
	
	

}
