package geso.traphaco.center.servlets.tieuchithuong;

import geso.traphaco.center.beans.tieuchithuong.ITieuchithuongTL;
import geso.traphaco.center.beans.tieuchithuong.ITieuchithuongTLList;
import geso.traphaco.center.beans.tieuchithuong.imp.TieuchithuongTL;
import geso.traphaco.center.beans.tieuchithuong.imp.TieuchithuongTLList;
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

public class TieuchithuongTLSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
   
    public TieuchithuongTLSvl() {
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
	    
	    String phanloai = request.getParameter("phanloai");
	    
	    ITieuchithuongTLList obj = new TieuchithuongTLList();
	    obj.setUserId(userId);
	    obj.setPhanloai(phanloai);
	    
	    String action = util.getAction(querystring);
	    String ctskuId = util.getId(querystring);
	    
	    System.out.println("___Action: " + action + " -- Id la: " + ctskuId);
	    if(action.trim().equals("duyet"))
	    {
	    	Duyet(ctskuId,userId);
	    }
	    
	    if(action.trim().equals("delete"))
	    {
	    	XoaChiTieu(ctskuId);
	    }

	    obj.init("");
		session.setAttribute("obj", obj);
	    
	    String nextJSP = "/TraphacoHYERP/pages/Center/TieuChiThuongTL.jsp";
		response.sendRedirect(nextJSP);
	}
	
	private String Duyet(String ctskuId,String userId )
	{
		dbutils db = new dbutils();
    	try
    	{
    		db.getConnection().setAutoCommit(false);	
    		db.update("update tieuchithuongTL set trangthai = '1' where pk_seq = '" + ctskuId + "'");
    		
    		/*String query ="delete DANGKYKM_TICHLUY where thuongtl_fk = "+ctskuId ;
    		if(!db.update(query))
    		{
    			db.getConnection().rollback();
    			return "Loi:"+query;
    		}
    		query = "\n	insert DANGKYKM_TICHLUY(CTKM_FK,NPP_FK,TRANGTHAI,NGUOITAO,NGUOISUA,NGAYTAO,NGAYSUA,thuongtl_fk ) " +
    				"\n	select (select pk_seq from CTKHUYENMAI where thuongtl_fk =a.thuongtl_fk ) ,a.npp_fk,1,"+userId+","+userId+",'"+getDateTime()+"','"+getDateTime()+"',thuongtl_fk from TIEUCHITHUONGTL_NPP a where   ISNULL(soluong,0) >0 and a.thuongtl_fk ="+ctskuId;	    		
    		if(!db.update(query))
    		{
    			db.getConnection().rollback();
    			return "Loi:"+query;
    		}*/
    		
    		db.getConnection().commit();
    		db.shutDown();
    		return "Duyệt thành công";
    	
    	}
    	catch (Exception e) 
    	{
			db.update("rollback");
			db.shutDown();
			e.printStackTrace();
			return "ExceptionL" + e.getMessage();

		}
    	
	}

	private void XoaChiTieu(String ctskuId) 
	{
		dbutils db = new dbutils();
    	
    	try 
    	{
			db.getConnection().setAutoCommit(false);
			
			if(!db.update("delete TIEUCHITHUONGTL_MUCTHUONG where thuongtl_fk = '" + ctskuId + "'"))
	    	{
	    		db.getConnection().rollback();
				return;
	    	}
			
			if(!db.update("delete TIEUCHITHUONGTL_SANPHAM where thuongtl_fk = '" + ctskuId + "'"))
	    	{
	    		db.getConnection().rollback();
				return;
	    	}
			
			if(!db.update("delete TIEUCHITHUONGTL_SPTRA where thuongtl_fk = '" + ctskuId + "'"))
	    	{
	    		db.getConnection().rollback();
				return;
	    	}
			
			if(!db.update("delete TIEUCHITHUONGTL_TIEUCHI where thuongtl_fk = '" + ctskuId + "'"))
	    	{
	    		db.getConnection().rollback();
				return;
	    	}
	    	
	    	if(!db.update("delete TIEUCHITHUONGTL where pk_seq = '" + ctskuId + "'"))
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
	    ITieuchithuongTLList obj;
	    
	    String phanloai = request.getParameter("phanloai");
	    
		String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    
	    //AJAX
	    String type = request.getParameter("type");
	    if(type == null)
	    	type = "";
	    
	    if(action.equals("new"))
	    {
    		ITieuchithuongTL tctsku = new TieuchithuongTL();
    		tctsku.setUserId(userId);
    		tctsku.createRs();
    		tctsku.setPhanloai(phanloai);
    		
	    	session.setAttribute("tctskuBean", tctsku);  	
    		session.setAttribute("userId", userId);
		
    		if( phanloai.equals("3") )
    			response.sendRedirect("/TraphacoHYERP/pages/Center/TieuChiThuongTLGiaiDoanNew.jsp");
    		else
    			response.sendRedirect("/TraphacoHYERP/pages/Center/TieuChiThuongTLNew.jsp");
	    }
	    else
	    {
	    	obj = new TieuchithuongTLList();
		    obj.setUserId(userId);
		    obj.setPhanloai(phanloai);

	    	String search = getSearchQuery(request, obj);
	    	
	    	obj.setUserId(userId);
	    	obj.init(search);
				
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
		
    		response.sendRedirect("/TraphacoHYERP/pages/Center/TieuChiThuongTL.jsp");	
	    }
	    
	}
	
	private String getSearchQuery(HttpServletRequest request, ITieuchithuongTLList obj) 
	{
		String thang = request.getParameter("thang");
		if(thang == null)
			thang = "";
		obj.setThang(thang);
		
		String nam = request.getParameter("nam");
		if(nam == null)
			nam = "";
		obj.setNam(nam);
		
		String sql = "select a.scheme, a.pk_seq, a.thang, a.nam, a.diengiai, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua  " +
					"from TIEUCHITHUONGTL a inner join NHANVIEN b on a.NGUOITAO = b.pk_seq " +
					"inner join NHANVIEN c on a.NGUOISUA = c.pk_seq where a.pk_seq > 0 ";
		if(thang.length() > 0)
			sql += " and a.thang = '" + thang + "' ";
		if(nam.length() > 0)
			sql += " and a.nam = '" + nam + "' ";
		
		sql += "order by nam desc, thang desc";
		
		return sql;
	}

}
