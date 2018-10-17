package geso.traphaco.erp.servlets.khoasothang;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.khoasothang.*;
import geso.traphaco.erp.beans.khoasothang.imp.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpCapnhatgianhapkhoSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpCapnhatgianhapkhoSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpCapnhatgianhapList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	   
	    obj = new ErpCapnhatgianhapList();
	    obj.setUserId(userId);
	    obj.init("");
	    
		session.setAttribute("obj", obj);
				
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpCapNhatGiaNhap.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IErpCapnhatgianhapList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    
	    Utility util = new Utility();
	    
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(request.getParameter("userId")); 
	    
	    if(action.equals("Tao moi"))
	    {
	    	IErpCapnhatgianhap nhBean = new ErpCapnhatgianhap();
	    	nhBean.createRs();
    	
	    	session.setAttribute("obj", nhBean);
    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpCapNhatGiaNhapNew.jsp";
    		response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	obj = new ErpCapnhatgianhapList();
	    	
	    	String search = getSearchQuery(request, obj);
	    	obj.init(search);
			obj.setUserId(userId);
			
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
	
    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpCapNhatGiaNhap.jsp");
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpCapnhatgianhapList obj)
	{
		Utility util = new Utility();
		
		String query = "select a.pk_seq, isnull(ghichu, 'na') as ghichu, a.trangthai, a.tungay, a.denngay, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua  " +
					"from ERP_CAPNHATGIANHAP a inner join nhanvien b on a.nguoitao = b.pk_seq inner join nhanvien c on a.nguoisua = c.pk_seq where a.pk_seq > 0 ";
					
		String tungay = request.getParameter("tungay");
		if(tungay == null)
			tungay = "";
		obj.setTungay(tungay);
		
		String macapnhat = request.getParameter("macapnhat");
		if(macapnhat == null)
			macapnhat = "";
		obj.setMacapnhat(macapnhat);
		
		String diengiai = request.getParameter("diengiai");
		if(diengiai == null)
			diengiai = "";
		obj.setDiengiai(diengiai);
		
		String denngay = request.getParameter("denngay");
		if(denngay == null)
			denngay = "";
		obj.setDenngay(denngay);
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		if(tungay.length() > 0)
			query += " and a.tungay >= '" + tungay + "'";
		
		if(macapnhat.length() > 0)
			query += " and a.pk_seq like '%" + macapnhat + "%' ";
		
		if(diengiai.length() > 0)
			query += " and dbo.ftBoDau(upper(a.ghichu)) like upper(N'%" + util.replaceAEIOU(diengiai) + "%')";
		
		if(denngay.length() > 0)
			query += " and a.denngay <= '" + denngay + "'";
		
		if(trangthai.length() > 0)
			query += " and a.trangthai = '" + trangthai + "'";
		
		
		query += "order by a.trangthai desc";
		return query;
	}

}
