package geso.traphaco.erp.servlets.tinhgiathanh;

import geso.traphaco.erp.beans.tinhgiathanh.IErpTieuhaonvl;
import geso.traphaco.erp.beans.tinhgiathanh.IErpTieuhaonvlList;
import geso.traphaco.erp.beans.tinhgiathanh.imp.ErpTieuhaonvl;
import geso.traphaco.erp.beans.tinhgiathanh.imp.ErpTieuhaonvlList;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.db.sql.dbutils;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpTieuhaonvlSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	
    public ErpTieuhaonvlSvl() {
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
	    
	    IErpTieuhaonvlList obj = new ErpTieuhaonvlList();
	    obj.setUserId(userId);
	    
	    String action = util.getAction(querystring);
	    String khlId = util.getId(querystring);
	    String msg = "";
	    
	    if(action.trim().equals("delete"))
	    {
	    	dbutils db = new dbutils();
	    	if(!db.update("update ERP_TIEUHAONGUYENLIEU set trangthai = '0' where pk_seq = '" + khlId + "'"))
	    	{
	    		msg = "Không thể xóa ERP_TIEUHAONGUYENLIEU";
	    	}
	    	db.shutDown();
	    }
	    
	    obj.init("");
	    obj.setMsg(msg);
		session.setAttribute("obj", obj);
	    
	    String nextJSP = "/TraphacoERP/pages/Erp/ErpTieuHaoNVL.jsp";
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
	    IErpTieuhaonvlList obj;
	    
		String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    
	    if(action.equals("new"))
	    {
    		IErpTieuhaonvl khl = new ErpTieuhaonvl();
    		khl.createRs();
    		khl.setUserId(userId);

	    	session.setAttribute("thnvlBean", khl);  	
    		session.setAttribute("userId", userId);
		
    		response.sendRedirect("/TraphacoERP/pages/Erp/ErpTieuHaoNVLNew.jsp");
	    }
	    else
	    {
	    	obj = new ErpTieuhaonvlList();
		    obj.setUserId(userId);

	    	String search = getSearchQuery(request, obj);
	    	
	    	obj.setUserId(userId);
	    	obj.init(search);
				
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
		
    		response.sendRedirect("/TraphacoERP/pages/Erp/ErpTieuHaoNVL.jsp");	
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpTieuhaonvlList obj) 
	{
		String tungay = request.getParameter("tungay");
		if(tungay == null)
			tungay = "";
		obj.setTungay(tungay);
		
		String denngay = request.getParameter("denngay");
		if(denngay == null)
			denngay = "";
		obj.setDenngay(denngay);
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		String sql = "select a.pk_seq, d.ma as spMa, d.ten as spTen, a.trangthai, b.ten as nguoitao, a.ngaytao, c.ten as nguoisua, a.ngaysua    " +
					  "from ERP_TIEUHAONGUYENLIEU a inner join NhanVien b on a.nguoitao = b.pk_seq    " +
				  		"inner join nhanvien c on a.nguoisua = c.pk_seq inner join sanpham d on a.sanpham_fk = d.pk_seq where a.pk_seq > 0 ";
		
		if(tungay.length() > 0)
			sql += " and a.tungay >= '" + tungay + "' ";
		if(denngay.length() > 0)
			sql += " and a.denngay <= '" + denngay + "' ";
		if(trangthai.length() > 0)
			sql += " and a.trangthai = '" + trangthai + "' ";
		
		sql += " order by a.pk_seq desc ";
		
		return sql;
	}
	
	

}
