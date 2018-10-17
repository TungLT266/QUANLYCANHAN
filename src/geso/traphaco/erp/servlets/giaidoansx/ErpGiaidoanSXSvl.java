package geso.traphaco.erp.servlets.giaidoansx;

import geso.traphaco.erp.beans.giaidoansx.*;
import geso.traphaco.erp.beans.giaidoansx.imp.*;
import geso.traphaco.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpGiaidoanSXSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	
    public ErpGiaidoanSXSvl() {
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
	    
	    IErpGiaidoanSXList obj = new ErpGiaidoanSXList();
	    String ctyId = (String)session.getAttribute("congtyId");
	    obj.setUserId(userId);
	    obj.setCongtyId(ctyId);
	    
	    String action = util.getAction(querystring);
	    String khlId = util.getId(querystring);
	    
	    if(action.trim().equals("delete")) {
	    	obj.delete(khlId);
	    }
	    
	    obj.init("");
		session.setAttribute("obj", obj);
	    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpGiaiDoanSX.jsp";
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
	    
		String action = request.getParameter("action");
	    if (action == null)
	    	action = "";
	    
	    if(action.equals("new")) {
    		IErpGiaidoanSX khl = new ErpGiaidoanSX();
    		khl.setCongtyId(ctyId);
    		khl.setUserId(userId);
    		khl.createRs();
    		
	    	session.setAttribute("csxBean", khl);
    		session.setAttribute("userId", userId);
    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpGiaiDoanSXNew.jsp");
	    } else {
	    	IErpGiaidoanSXList obj = new ErpGiaidoanSXList();
	    	obj.setCongtyId(ctyId);
		    obj.setUserId(userId);

	    	String search = getSearchQuery(request, obj);
	    	
	    	obj.setUserId(userId);
	    	obj.init(search);
				
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
		
    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpGiaiDoanSX.jsp");	
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpGiaidoanSXList obj) 
	{
		Utility util = new Utility();
		
		String ma = request.getParameter("ma");
		if(ma == null)
			ma = "";
		obj.setMa(ma);
		
		String diengiai = request.getParameter("diengiai");
		if(diengiai == null)
			diengiai = "";
		obj.setDiengiai(diengiai);
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		String sql = 
				"select a.pk_seq, a.ma, a.ten, a.trangthai, b.ten as nguoitao, a.ngaytao, c.ten as nguoisua, a.ngaysua " +
			  	"from Erp_Giaidoansx a inner join NhanVien b on a.nguoitao = b.pk_seq " +
			  	"inner join nhanvien c on a.nguoisua = c.pk_seq where a.congty_fk = " + obj.getCongtyId();
		
		if(ma.length() > 0)
			sql += " and a.ma like N'%" + ma + "%' ";
		
		if(diengiai.length() > 0)
			sql += " and dbo.ftBoDau(a.ten) like N'%" + util.replaceAEIOU(diengiai) + "%' ";
		
		if(trangthai.length() > 0)
			sql += " and a.trangthai = '" + trangthai + "' ";
		
		sql += " order by a.pk_seq desc ";
		
		return sql;
	}
	
	

}
