package geso.traphaco.erp.servlets.daytruyensanxuat;

import geso.traphaco.erp.beans.daytruyensanxuat.*;
import geso.traphaco.erp.beans.daytruyensanxuat.imp.*;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpDaytruyensanxuatSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	
    public ErpDaytruyensanxuatSvl() {
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
	    
	    IErpDaytruyensanxuatList obj = new ErpDaytruyensanxuatList();
	    String ctyId = (String)session.getAttribute("congtyId");
	    obj.setUserId(userId);
	    obj.setCongtyId(ctyId);
	    
	    String action = util.getAction(querystring);
	    String khlId = util.getId(querystring);
	    String msg = "";
	    
	    if(action.trim().equals("delete"))
	    {
	    	dbutils db = new dbutils();
	    	if(!db.update("UPDATE ERP_DAYTRUYENSANXUAT SET TRANGTHAI = '0' WHERE PK_SEQ = '" + khlId + "'"))
	    	{
	    		msg = "Không thể xóa ERP_DAYTRUYENSANXUAT";
	    	}
	    	db.shutDown();
	    }
	    
	    obj.init("");
	    obj.setMsg(msg);
		session.setAttribute("obj", obj);
	    
	    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDaytruyensanxuat.jsp";
		response.sendRedirect(nextJSP);
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
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    
	    IErpDaytruyensanxuatList obj;
	    
		String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    
	    if(action.equals("new"))
	    {
    		IErpDaytruyensanxuat dtsx = new ErpDaytruyensanxuat();
    		dtsx.setCongtyId(ctyId);
    		dtsx.setUserId(userId);
    		dtsx.createRs();
    		
	    	session.setAttribute("dtsxBean", dtsx);  	
    		session.setAttribute("userId", userId);
		
    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpDaytruyensanxuatNew.jsp");
	    }
	    else
	    {
	    	obj = new ErpDaytruyensanxuatList();
	    	obj.setCongtyId(ctyId);
		    obj.setUserId(userId);

	    	String search = getSearchQuery(request, obj);
	    	
	    	obj.setUserId(userId);
	    	obj.init(search);
				
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
		
    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpDaytruyensanxuat.jsp");	
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpDaytruyensanxuatList obj) 
	{
		
		String ma = request.getParameter("madaytruyensanxuat");
		if(ma == null)
			ma = "";
		obj.setMa(ma);
		
		String diengiai = request.getParameter("diengiai");
		if(diengiai == null)
			diengiai = "";
		obj.setDiengiai(diengiai);
		
		String nmId = request.getParameter("nmId");
		if(nmId == null)
			nmId = "";
		obj.setNhamayId(nmId);

		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		String sql =  "SELECT DTSX.PK_SEQ, DTSX.MADAYTRUYENSX, DTSX.TENDAYTRUYENSX, NM.MANHAMAY, NM.TENNHAMAY, DTSX.TRANGTHAI,  \n " +
					  "NV1.TEN as NGUOITAO, DTSX.NGAYTAO, NV2.TEN as NGUOISUA, DTSX.NGAYSUA \n " +
					  "FROM Erp_DAYTRUYENSANXUAT DTSX \n " +
					  "INNER JOIN NHANVIEN NV1 on DTSX.NGUOITAO = NV1.PK_SEQ \n " +
					  "INNER JOIN NHANVIEN NV2 on DTSX.NGUOISUA = NV2.PK_SEQ \n " +
					  "INNER JOIN ERP_NHAMAY NM ON NM.PK_SEQ = DTSX.NHAMAY_FK \n " +
					  "WHERE DTSX.CONGTY_FK = " + obj.getCongtyId() + " \n " ;
					  
		
		if(ma.length() > 0)
			sql += " AND DTSX.MADAYTRUYENSX LIKE N'%" + ma + "%' ";
		
		if(diengiai.length() > 0)
			sql += " AND DTSX.TENDAYTRUYENSX like N'%" + diengiai + "%' ";
		
		if(trangthai.length() > 0)
			sql += " and DTSX.TRANGTHAI = '" + trangthai + "' ";
		
		if(nmId.length() > 0)
			sql += " and DTSX.NHAMAY_FK = '" + nmId + "' ";

		sql += " ORDER BY DTSX.PK_SEQ DESC ";
		
		return sql;
	}
	
	

}
