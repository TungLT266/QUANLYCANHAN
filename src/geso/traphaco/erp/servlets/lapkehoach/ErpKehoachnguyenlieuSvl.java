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

public class ErpKehoachnguyenlieuSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	
    public ErpKehoachnguyenlieuSvl() {
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
	    
	    IErpKehoachnguyenlieuList obj = new ErpKehoachnguyenlieuList();
	    obj.setCtyId(ctyId);
	    obj.setUserId(userId);
	    
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
	    
	    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKeHoachNVL.jsp";
		response.sendRedirect(nextJSP);
	}

	private String DeleteKeHoach(String khnlId) 
	{
		dbutils db = new dbutils();
    	
    	try 
    	{
			db.getConnection().setAutoCommit(false);
			
			String query = "UPDATE ERP_KEHOACHNGUYENLIEU SET TRANGTHAI = '2' WHERE PK_SEQ = '" + khnlId + "'";
			if(!db.update(query))
	    	{
	    		db.getConnection().rollback();
	    		return "Không thể xóa ERP_KEHOACHNGUYENLIEU";
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
	    IErpKehoachnguyenlieuList obj;
	    
		String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    
	    if(action.equals("new"))
	    {
    		IErpKehoachnguyenlieu khnl = new ErpKehoachnguyenlieu();
    		khnl.setCtyId(ctyId);
    		
    		khnl.createRs();
    		khnl.setUserId(userId);

	    	session.setAttribute("khnlBean", khnl);  	
    		session.setAttribute("userId", userId);
		
    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpKeHoachNguyenLieuNew.jsp");
	    }
	    else
	    {
	    	obj = new ErpKehoachnguyenlieuList();
	    	obj.setCtyId(ctyId);
		    obj.setUserId(userId);

	    	String search = getSearchQuery(request, obj);
	    	
	    	obj.setUserId(userId);
	    	obj.init(search);
				
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
		
    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpKeHoachNguyenLieu.jsp");	
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpKehoachnguyenlieuList obj) 
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
		
		
		String sql = "";

		sql = 	"SELECT  KHNL.PK_SEQ, KHNL.NGAYLAP, NV.TEN AS NGUOITAO, ISNULL(LOAISP.TEN, N'Không chọn') AS LOAISP, " +
				"SOSP.NUM " +
				"FROM ERP_KEHOACHNGUYENLIEU KHNL " +
				"LEFT JOIN ERP_LOAISANPHAM LOAISP ON LOAISP.PK_SEQ = KHNL.LOAISANPHAM_FK " +
				"LEFT JOIN ( " +
				"	SELECT COUNT(DISTINCT SANPHAM_FK) AS NUM, KEHOACH_FK AS KHID " + 
				"	FROM ERP_KEHOACHNGUYENLIEU_SANPHAM GROUP BY KEHOACH_FK " +  
				")SOSP ON SOSP.KHID = KHNL.PK_SEQ " +
				"INNER JOIN NHANVIEN NV ON KHNL.NGUOITAO = NV.PK_SEQ " +  
				"WHERE KHNL.CONGTY_FK = " + obj.getCtyId() + " " ;
		
		if(tungay.length() > 0)
			sql += " AND KHNL.NGAYLAP >= '" + tungay + "' ";
		if(denngay.length() > 0)
			sql += " AND KHNL.NGAYLAP  <= '" + denngay + "' ";
		
		sql += " ORDER BY KHNL.PK_SEQ DESC" ;
		
		return sql;
	}
	
	

}
