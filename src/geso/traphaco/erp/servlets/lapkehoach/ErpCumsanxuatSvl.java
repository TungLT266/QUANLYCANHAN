package geso.erp.servlets.lapkehoach;

import geso.erp.beans.lapkehoach.*;
import geso.erp.beans.lapkehoach.imp.*;
import geso.dms.center.util.Utility;
import geso.dms.db.sql.dbutils;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpCumsanxuatSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	
    public ErpCumsanxuatSvl() {
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
	    
	    String ctyId = (String)session.getAttribute("congtyId");
	    IErpCumsanxuatList obj = new ErpCumsanxuatList();
	    obj.setCtyId(ctyId);
	    
	    obj.setUserId(userId);
	    
	    String action = util.getAction(querystring);
	    String khlId = util.getId(querystring);
	    String msg = "";
	    
	    if(action.trim().equals("delete"))
	    {
	    	dbutils db = new dbutils();
	    	if(!db.update("update Erp_CumSanXuat set trangthai = '0' where pk_seq = '" + khlId + "'"))
	    	{
	    		msg = "Không thể xóa Erp_CumSanXuat";
	    	}
	    	db.shutDown();
	    }
	    
	    obj.init("");
	    obj.setMsg(msg);
		session.setAttribute("obj", obj);
	    
	    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpCumSanXuat.jsp";
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
	    
	    IErpCumsanxuatList obj;
	    
		String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    
	    if(action.equals("new"))
	    {
    		IErpCumsanxuat csx = new ErpCumsanxuat();
    		csx.setCtyId(ctyId);
    		
    		csx.createRs();
    		csx.setUserId(userId);

	    	session.setAttribute("csxBean", csx);  	
    		session.setAttribute("userId", userId);
		
    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpCumSanXuatNew.jsp");
	    }
	    else
	    {
	    	obj = new ErpCumsanxuatList();
	    	obj.setCtyId(ctyId);
	    	
		    obj.setUserId(userId);

	    	String search = getSearchQuery(request, obj);
	    	
	    	obj.setUserId(userId);
	    	obj.init(search);
				
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
		
    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpCumSanXuat.jsp");	
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpCumsanxuatList obj) 
	{
		Utility util = new Utility();
		
		String ma = util.antiSQLInspection(request.getParameter("ma"));
		if(ma == null)
			ma = "";
		obj.setMa(ma);
		
		String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
		if(diengiai == null)
			diengiai = "";
		obj.setDiengiai(diengiai);
		
		String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		String  sql = "select a.pk_seq, a.ma, a.diengiai, a.sonhancong, a.trangthai, b.ten as nguoitao, a.ngaytao, c.ten as nguoisua, a.ngaysua    " +
					  "from Erp_CumSanXuat a inner join NhanVien b on a.nguoitao = b.pk_seq    " +
				  	  "inner join nhanvien c on a.nguoisua = c.pk_seq where a.pk_seq > 0 " +
					  "where a.nhamay_fk in (select pk_seq from erp_nhamay where congty_fk = " + obj.getCtyId() + ") " ;
					  				  	  
		
		if(ma.length() > 0)
			sql += " and a.ma like N'%" + ma + "%' ";
		
		if(diengiai.length() > 0)
			sql += " and a.diengiai like N'%" + diengiai + "%' ";
		
		if(trangthai.length() > 0)
			sql += " and a.trangthai = '" + trangthai + "' ";
		
		sql += " order by a.pk_seq desc ";
		
		return sql;
	}
	
	

}
