package geso.traphaco.center.servlets.canhbao;

import geso.traphaco.center.beans.canhbao.ICanhbao;
import geso.traphaco.center.beans.canhbao.ICanhbaoList;
import geso.traphaco.center.beans.canhbao.imp.Canhbao;
import geso.traphaco.center.beans.canhbao.imp.CanhbaoList;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CanhbaoSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	
    public CanhbaoSvl() {
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
	    
	    ICanhbaoList obj = new CanhbaoList();
	    obj.setUserId(userId);
	    
	    String action = util.getAction(querystring);
	    String khlId = util.getId(querystring);
	    String msg = "";
	    
	    if(action.trim().equals("delete"))
	    {
	    	dbutils db = new dbutils();
	    	if(!db.update("delete CanhBao_NhanVien where CanhBao_fk = '" + khlId + "' and Nhanvien_fk = '" + userId + "'"))
	    	{
	    		msg = "Không thể xóa CanhBao_NhanVien";
	    	}
	    	db.shutDown();
	    }
	    
	    obj.init("");
	    obj.setMsg(msg);
		session.setAttribute("obj", obj);
	    
	    String nextJSP = "/TraphacoHYERP/pages/Center/CanhBao.jsp";
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
	    
	    
	    ICanhbaoList obj;
	    
		String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    
	    if(action.equals("new"))
	    {
    		ICanhbao khl = new Canhbao();
    		khl.setCongtyId(ctyId);
    		khl.setUserId(userId);

	    	session.setAttribute("csxBean", khl);  	
    		session.setAttribute("userId", userId);
		
    		response.sendRedirect("/TraphacoHYERP/pages/Center/NganhHangNew.jsp");
	    }
	    else
	    {
	    	obj = new CanhbaoList();
		    obj.setUserId(userId);

	    	String search = getSearchQuery(request, obj);
	    	
	    	obj.setUserId(userId);
	    	obj.init(search);
				
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
		
    		response.sendRedirect("/TraphacoHYERP/pages/Center/CanhBao.jsp");	
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, ICanhbaoList obj) 
	{
		String diengiai = request.getParameter("diengiai");
		if(diengiai == null)
			diengiai = "";
		obj.setDiengiai(diengiai);
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		String sql = "select a.pk_seq, a.noidung, a.loaithongbao, a.ngaytao    " +
					  "from CanhBao a inner join CanhBao_NhanVien b on a.pk_seq = b.canhbao_fk " +
					  "where b.nhanvien_fk = '" + obj.getUserId() + "' ";
		
		if(diengiai.length() > 0)
			sql += " and a.noidung like N'%" + diengiai + "%' ";
		
		if(trangthai.length() > 0)
			sql += " and a.loaithongbao like N'%" + trangthai + "%' ";
		
		sql += " order by a.pk_seq desc ";
		
		return sql;
	}
	
	

}
