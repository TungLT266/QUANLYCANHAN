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

public class ErpKichbansanxuatSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	
    public ErpKichbansanxuatSvl() {
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
	    IErpKichbansanxuatList obj = new ErpKichbansanxuatList();
	    obj.setCtyId(ctyId);
	    
	    obj.setUserId(userId);
	    
	    String action = util.getAction(querystring);
	    String khlId = util.getId(querystring);
	    String msg = "";
	    
	    if(action.trim().equals("delete"))
	    {
	    	dbutils db = new dbutils();
	    	if(!db.update("update Erp_KichBanSanXuat set trangthai = '0' where pk_seq = '" + khlId + "'"))
	    	{
	    		msg = "Không thể cập nhật Erp_KichBanSanXuat";
	    	}
	    	db.shutDown();
	    }
	    
	    obj.init("");
	    obj.setMsg(msg);
		session.setAttribute("obj", obj);
	    
	    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKichBanSanXuat.jsp";
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
	    
	    IErpKichbansanxuatList obj;
	    
		String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    
	    if(action.equals("new"))
	    {
    		IErpKichbansanxuat khl = new ErpKichbansanxuat();
    		khl.setCtyId(ctyId);
    		khl.createRs();
    		khl.setUserId(userId);

	    	session.setAttribute("kbsxBean", khl);  	
    		session.setAttribute("userId", userId);
		
    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpKichBanSanXuatNew.jsp");
	    }
	    else
	    {
	    	obj = new ErpKichbansanxuatList();
	    	obj.setCtyId(ctyId);
	    	
		    obj.setUserId(userId);

	    	String search = getSearchQuery(request, obj);
	    	
	    	obj.setUserId(userId);
	    	obj.init(search);
				
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
		
    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpKichBanSanXuat.jsp");	
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpKichbansanxuatList obj) 
	{
		Utility util = new Utility();
		
		String ma = util.antiSQLInspection(request.getParameter("ma"));
		if(ma == null)
			ma = "";
		obj.setMa(ma);
		
		String sanpham = util.antiSQLInspection(request.getParameter("sanpham"));
		if(sanpham == null)
			sanpham = "";
		obj.setSanpham(sanpham);
		
		String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		String sql =  	"select a.pk_seq, a.ma as kbMa, d.ma as spMa, d.ten as spTen, a.trangthai, b.ten as nguoitao, a.ngaytao, " +
						"c.ten as nguoisua, a.ngaysua    " +
					  	"from Erp_KichBanSanXuat a inner join NhanVien b on a.nguoitao = b.pk_seq    " +
			  			"inner join nhanvien c on a.nguoisua = c.pk_seq " +
				  		"inner join sanpham d on a.sanpham_fk = d.pk_seq where a.pk_seq > 0 " + 
				  		" and a.congty_fk = " + obj.getCtyId() + " " ;
		
		if(ma.length() > 0)
			sql += " and a.ma like N'%" + ma + "%'";
		
		if(sanpham.length() > 0)
			sql += " and d.ten like  N'%" + sanpham + "%'";
		
		if(trangthai.length() > 0)
			sql += " and a.trangthai = '" + trangthai + "' ";
		
		sql += " order by a.pk_seq desc ";
		
		return sql;
	}
	
	

}
