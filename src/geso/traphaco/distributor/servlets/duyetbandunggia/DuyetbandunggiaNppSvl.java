package geso.traphaco.distributor.servlets.duyetbandunggia;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.beans.duyetbandunggia.IDuyetbandunggiaNpp;
import geso.traphaco.distributor.beans.duyetbandunggia.IDuyetbandunggiaNppList;
import geso.traphaco.distributor.beans.duyetbandunggia.imp.DuyetbandunggiaNpp;
import geso.traphaco.distributor.beans.duyetbandunggia.imp.DuyetbandunggiaNppList;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DuyetbandunggiaNppSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
   
    public DuyetbandunggiaNppSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out  = response.getWriter();
	    
	    HttpSession session = request.getSession();	
	    
	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    IDuyetbandunggiaNppList obj = new DuyetbandunggiaNppList();
	    obj.setUserId(userId);
	    
	    String action = util.getAction(querystring);
	    String ctskuId = util.getId(querystring);
	    
	    //System.out.println("___Action: " + action + " -- Id la: " + ctskuId);
	    if(action.trim().equals("duyet"))
	    {
	    	dbutils db = new dbutils();
	    	db.update("update DUYETBANDUNGGIA set trangthai = '1' where pk_seq = '" + ctskuId + "'");
	    	db.shutDown();
	    }
	    else if(action.equals("unduyet"))
	    {
	    	dbutils db = new dbutils();
	    	db.update("update DUYETBANDUNGGIA set trangthai = '0' where pk_seq = '" + ctskuId + "'");
	    	db.shutDown();
	    }
	    else if(action.trim().equals("delete"))
	    {
	    	XoaChiTieu(ctskuId);
	    }

	    obj.init("");
		session.setAttribute("obj", obj);
	    
	    String nextJSP = "/TraphacoHYERP/pages/Distributor/DuyetBanDungGiaNpp.jsp";
		response.sendRedirect(nextJSP);
	}

	private void XoaChiTieu(String ctskuId) 
	{
		dbutils db = new dbutils();
    	
    	try 
    	{
			db.getConnection().setAutoCommit(false);
			
			if(!db.update("delete DUYETBANDUNGGIA_NPP where duyet_fk = '" + ctskuId + "'"))
	    	{
	    		db.getConnection().rollback();
				return;
	    	}
			
	    	if(!db.update("delete DUYETBANDUNGGIA where pk_seq = '" + ctskuId + "'"))
			{
				db.getConnection().rollback();
				return;
			}
	    	
	    	db.getConnection().commit();
	    	db.shutDown();
		} 
    	catch (Exception e)
    	{
    		try 
    		{
				db.getConnection().rollback();
			} 
    		catch (SQLException e1) {}
    	}
    	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out  = response.getWriter();
	    
	    HttpSession session = request.getSession();	
	    
	    out = response.getWriter();
	    Utility util = new Utility();
	    
	    String userId = util.antiSQLInspection(request.getParameter("userId"));     
	    IDuyetbandunggiaNppList obj;
	    
		String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    
	    String type = request.getParameter("type");
	    if(type == null)
	    	type = "";
	    
	    if(action.equals("new"))
	    {
    		IDuyetbandunggiaNpp tctsku = new DuyetbandunggiaNpp();
    		tctsku.setUserId(userId);
    		tctsku.createRs();
    		
	    	session.setAttribute("tctskuBean", tctsku);  	
    		session.setAttribute("userId", userId);
		
    		response.sendRedirect("/TraphacoHYERP/pages/Distributor/DuyetBanDungGiaNppNew.jsp");
	    }
	    else
	    {
	    	obj = new DuyetbandunggiaNppList();
		    obj.setUserId(userId);

	    	String search = getSearchQuery(request, obj);
	    	
	    	obj.setUserId(userId);
	    	obj.init(search);
				
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
		
    		response.sendRedirect("/TraphacoHYERP/pages/Distributor/DuyetBanDungGiaNpp.jsp");	
	    }
	    
	}
	
	private String getSearchQuery(HttpServletRequest request, IDuyetbandunggiaNppList obj) 
	{
		String tuthang = request.getParameter("tuthang");
		if(tuthang == null)
			tuthang = "";
		obj.setTuthang(tuthang);
		
		String denthang = request.getParameter("denthang");
		if(denthang == null)
			denthang = "";
		obj.setDenthang(denthang);
		
		String nam = request.getParameter("nam");
		if(nam == null)
			nam = "";
		obj.setNam(nam);
		

		String nppId = request.getParameter("nppId");
		if(nppId == null)
			nppId = "";
		obj.setNppId(nppId);
		
		String sql = "select a.pk_seq, a.thang, a.nam, diengiai, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua  " +
					"from DUYETBANDUNGGIA a inner join NHANVIEN b on a.NGUOITAO = b.pk_seq " +
					"inner join NHANVIEN c on a.NGUOISUA = c.pk_seq where a.npp_fk = '" + nppId + "' ";
		if(tuthang.length() > 0 && denthang.length()>0)
			sql += " and (a.thang BETWEEN " + tuthang + " and  " + denthang + ") ";
		if(nam.length() > 0)
			sql += " and a.nam = '" + nam + "' ";
		
		sql += "order by nam desc, thang desc";
		
		return sql;
	}

}
