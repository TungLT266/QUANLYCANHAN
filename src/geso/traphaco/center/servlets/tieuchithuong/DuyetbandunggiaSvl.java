package geso.traphaco.center.servlets.tieuchithuong;

import geso.traphaco.center.beans.tieuchithuong.IDuyetbandunggia;
import geso.traphaco.center.beans.tieuchithuong.IDuyetbandunggiaList;
import geso.traphaco.center.beans.tieuchithuong.imp.Duyetbandunggia;
import geso.traphaco.center.beans.tieuchithuong.imp.DuyetbandunggiaList;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DuyetbandunggiaSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
   
    public DuyetbandunggiaSvl() {
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
	    
	    IDuyetbandunggiaList obj = new DuyetbandunggiaList();
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
	    
	    if(action.trim().equals("delete"))
	    {
	    	XoaChiTieu(ctskuId);
	    }

	    obj.init("");
		session.setAttribute("obj", obj);
	    
	    String nextJSP = "/TraphacoHYERP/pages/Center/DuyetBanDungGia.jsp";
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
			} catch (SQLException e1) {}
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
	    IDuyetbandunggiaList obj;
	    
		String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    
	    String type = request.getParameter("type");
	    if(type == null)
	    	type = "";
	    
	    if(action.equals("new"))
	    {
    		IDuyetbandunggia tctsku = new Duyetbandunggia();
    		tctsku.setUserId(userId);
    		tctsku.createRs();
    		
	    	session.setAttribute("tctskuBean", tctsku);  	
    		session.setAttribute("userId", userId);
		
    		response.sendRedirect("/TraphacoHYERP/pages/Center/DuyetBanDungGiaNew.jsp");
	    }
	    else
	    {
	    	obj = new DuyetbandunggiaList();
		    obj.setUserId(userId);

	    	String search = getSearchQuery(request, obj);
	    	
	    	obj.setUserId(userId);
	    	obj.init(search);
				
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
		
    		response.sendRedirect("/TraphacoHYERP/pages/Center/DuyetBanDungGia.jsp");	
	    }
	    
	}
	
	private String getSearchQuery(HttpServletRequest request, IDuyetbandunggiaList obj) 
	{
		Utility util = new Utility();
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
		
		String sql = "select a.pk_seq, a.thang, a.nam, ( select ten from NHAPHANPHOI where pk_seq = a.npp_fk ) as nppTEN, diengiai, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua  " +
					"from DUYETBANDUNGGIA a inner join NHANVIEN b on a.NGUOITAO = b.pk_seq " +
					"inner join NHANVIEN c on a.NGUOISUA = c.pk_seq where a.pk_seq > 0 and a.trangthai = '1' and  a.npp_fk in "+ util.quyen_npp(obj.getUserId());
		if(tuthang.length() > 0 && denthang.length()>0)
			sql += " and (a.thang BETWEEN " + tuthang + " and  " + denthang + ") ";
		if(nam.length() > 0)
			sql += " and a.nam = '" + nam + "' ";
		
		sql += "order by nam desc, thang desc";
		
		return sql;
	}

}
