package geso.traphaco.erp.servlets.lenhsanxuat;

import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.lenhsanxuat.IErpYeucaunguyenlieu;
import geso.traphaco.erp.beans.lenhsanxuat.IErpYeucaunguyenlieuList;
import geso.traphaco.erp.beans.lenhsanxuat.imp.ErpYeucaunguyenlieu;
import geso.traphaco.erp.beans.lenhsanxuat.imp.ErpYeucaunguyenlieuList;
import java.io.IOException;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpYeucaunguyenlieuSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpYeucaunguyenlieuSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpYeucaunguyenlieuList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    
	    String lsxId = util.getId(querystring);
	    obj = new ErpYeucaunguyenlieuList();
	    if (action.equals("delete"))
	    {	
	    	dbutils db = new dbutils();
	    	db.update("update ERP_YEUCAUNGUYENLIEU set trangthai = '3' where pk_seq = '" + lsxId + "'");
	    	db.shutDown();
	    }
	    else
	    {
	    	if(action.equals("chot"))
	    	{
	    		dbutils db = new dbutils();
		    	db.update("update ERP_YEUCAUNGUYENLIEU set trangthai = '1' where pk_seq = '" + lsxId + "'");
		    	db.shutDown();
	    	}
	    }
	    
	    String task = request.getParameter("task");
		if(task == null)
			task = "";
		if(task.equals("chuyenNL"))
			obj.setIschuyenNL("1");
		
	    obj.setUserId(userId);
	    obj.init("");
	    
		session.setAttribute("obj", obj);
			
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpYeuCauNguyenLieu.jsp";
		if(task.equals("chuyenNL"))
		{
			nextJSP = "/TraphacoHYERP/pages/Erp/ErpChuyenNguyenLieu.jsp";
		}
		
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    String action = request.getParameter("action");
	    if (action == null)
	    {
	    	action = "";
	    }
	    
		IErpYeucaunguyenlieuList obj = new ErpYeucaunguyenlieuList();
	    String task = request.getParameter("task");
		if(task == null)
			task = "";
		if(task.equals("chuyenNL"))
			obj.setIschuyenNL("1");
	    
	    Utility util = new Utility();
	    
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(request.getParameter("userId")); 
	    
	    if(action.equals("Tao moi"))
	    {
	    	IErpYeucaunguyenlieu lsxBean = new ErpYeucaunguyenlieu();
	    	lsxBean.createRs();
    		
	    	session.setAttribute("lsxBean", lsxBean);
	    	
    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpYeuCauNguyenLieuNew.jsp";
    		response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
	    		System.out.println("toi day");
		    	String search = getSearchQuery(request, obj);
		    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
		    	obj.setUserId(userId);
		    	obj.init(search);
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	
		    	if(task.equals("chuyenNL"))
		    	{
		    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpChuyenNguyenLieu.jsp");	
		    	}
		    	else
		    	{
		    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpYeuCauNguyenLieu.jsp");	
		    	}
		    }
	    	else
	    	{
		    	String search = getSearchQuery(request, obj);
		    	obj.init(search);
				obj.setUserId(userId);
				
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
		
	    		if(task.equals("chuyenNL"))
		    	{
		    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpChuyenNguyenLieu.jsp");	
		    	}
		    	else
		    	{
		    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpYeuCauNguyenLieu.jsp");	
		    	}
	    	}
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpYeucaunguyenlieuList obj)
	{
		String query = "select a.PK_SEQ ,a.trangthai, a.ngaybatdau, a.ngayketthuc, sp.TEN as spTen, NV.TEN as nguoitao, " +
						"a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua  " +
						"from ERP_LENHSANXUAT a  " +
						"inner Join ERP_SanPham sp on a.SANPHAM_FK = sp.PK_SEQ inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
						"inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ where a.pk_seq > 0 ";
		
		if(obj.getIschuyenNL().equals("1"))
		{
			query += " and a.trangthai in (1, 2) ";
		}
		
		String tungaySX = request.getParameter("tungaySX");
		if(tungaySX == null)
			tungaySX = "";
		obj.setTungayTao(tungaySX);
		
		String denngaySX = request.getParameter("denngaySX");
		if(denngaySX == null)
			denngaySX = "";
		obj.setDenngayTao(denngaySX);
		
		String tungayDK = request.getParameter("tungayDK");
		if(tungayDK == null)
			tungayDK = "";
		obj.setTungayDk(tungayDK);
		
		String denngayDK = request.getParameter("denngaySX");
		if(denngayDK == null)
			denngayDK = "";
		obj.setDenngayDk(denngayDK);
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		if(tungaySX.length() > 0)
			query += " and a.NgayBatDau >= '" + tungaySX + "'";
		
		if(denngaySX.length() > 0)
			query += " and a.NgayBatDau <= '" + denngaySX + "'";
		
		if(tungayDK.length() > 0)
			query += " and a.NgayKetThuc >= '" + tungayDK + "'";
		
		if(denngayDK.length() > 0)
			query += " and a.NgayKetThuc <= '" + denngayDK + "'";
		
		if(trangthai.length() > 0)
			query += " and a.TrangThai = '" + trangthai + "'";
		
		return query;
	}
		
	public String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	
}
