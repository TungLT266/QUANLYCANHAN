package geso.traphaco.center.servlets.nhomctkhuyenmai;

import geso.traphaco.center.beans.nhomctkhuyenmai.*;
import geso.traphaco.center.beans.nhomctkhuyenmai.imp.*;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class NhomctkhuyenmaiSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
       
	PrintWriter out;
    public NhomctkhuyenmaiSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out  = response.getWriter();
	    	    
	    HttpSession session = request.getSession();	    
		INhomctkhuyenmaiList obj = new NhomctkhuyenmaiList();
		
	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	    String nctkmId = util.getId(querystring);

	    if (action.equals("delete"))
	    {
	    	Delete(nctkmId);
	    }
	
	    obj.setUserId(userId);
	    obj.createNhomctkmBean("");
	
	    session.setAttribute("obj", obj);
		String nextJSP = "/TraphacoHYERP/pages/Center/NhomCTKhuyenMai.jsp";
		
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out = response.getWriter();
	    
	    INhomctkhuyenmaiList obj = new NhomctkhuyenmaiList();	    
		HttpSession session = request.getSession();
		Utility util = new Utility();
		
	    String userId = util.antiSQLInspection(request.getParameter("userId"));
	    String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
		String tungay = util.antiSQLInspection(request.getParameter("tungay"));
		String denngay = util.antiSQLInspection(request.getParameter("denngay"));
		String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
	    
		obj.setDiengiai(diengiai);
		obj.setTungay(tungay);
		obj.setDenngay(denngay);
		obj.setTrangthai(trangthai);
		
	    String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }

	    if (action.equals("new"))
	    {
	    	INhomctkhuyenmai nctkmBean = (INhomctkhuyenmai) new Nhomctkhuyenmai();
	    	nctkmBean.setUserId(userId);
	    	nctkmBean.createRs();
	    	
	    	session.setAttribute("nctkmBean", nctkmBean);
    		String nextJSP = "/TraphacoHYERP/pages/Center/NhomCTKMNew.jsp";
    		response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	String search = getSearchQuery(request, obj);
	    	
	    	obj.setUserId(userId);
	    	obj.createNhomctkmBean(search);
	    	
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
	    		
    		response.sendRedirect("/TraphacoHYERP/pages/Center/NhomCTKhuyenMai.jsp");    	
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, INhomctkhuyenmaiList obj) 
	{
		Utility util = new Utility();
		String query = "select a.pk_seq as nkmId, a.tennhom, a.diengiai, a.ngaytao, a.ngaysua, a.trangthai, b.ten as nguoitao, c.ten as nguoisua " +
		"from nhomctkhuyenmai a inner join nhanvien b on a.nguoitao = b.pk_seq inner join nhanvien c on a.nguoisua = c.pk_seq where a.pk_seq > 0 ";
		
		if(obj.getDiengiai().length() > 0)
			query += " and ( upper(dbo.ftBoDau(a.tennhom)) like upper(N'%" + util.replaceAEIOU(obj.getDiengiai()) + "%')) or ( upper(dbo.ftBoDau(a.diengiai)) like upper(N'%" + util.replaceAEIOU(obj.getDiengiai()) + "%'))";
		if(obj.getTungay().length() > 0)
			query += " and a.ngaytao >= '" + obj.getTungay() + "'";
		if(obj.getDenngay().length() > 0)
			query += " and a.ngaytao <= '" + obj.getDenngay() + "'";
		
		query += "order by a.ngaytao desc";
		
		return query;
	}

	private void Delete(String nctkmId)
	{
		dbutils db = new dbutils();
		db.update("delete nhomctkhuyenmai_ctkm where nhomctkmId = '" + nctkmId + "'");
		db.update("delete NHOMCTKHUYENMAI where pk_seq = '" + nctkmId + "'");
		db.shutDown();
	}

}
