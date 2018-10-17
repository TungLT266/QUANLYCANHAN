package geso.traphaco.center.servlets.nhomchitieu;

import geso.traphaco.center.beans.nhomsanpham.INhomsanpham;
import geso.traphaco.center.beans.nhomsanpham.imp.Nhomsanpham;
import geso.traphaco.center.beans.nhomsanpham.INhomsanphamList;
import geso.traphaco.center.beans.nhomsanpham.imp.NhomsanphamList;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class NhomchitieuSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
	
   static final long serialVersionUID = 1L;
   PrintWriter out;
   
	public NhomchitieuSvl() {
		super();
	}   	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");

		HttpSession session = request.getSession();	    

		Utility util = new Utility();
		out = response.getWriter();
		INhomsanphamList obj = new NhomsanphamList();
		obj.setIsnhomchitieu("1");

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		out.println(userId);

		if (userId.length()==0)
			userId = util.antiSQLInspection(request.getParameter("userId"));

		String action = util.getAction(querystring);
		out.println(action);

		String nspId = util.getId(querystring);

		if (action.equals("delete")){	

			Delete(nspId);
		}

		obj.init("");
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);

		String nextJSP = "/TraphacoHYERP/pages/Center/NhomChiTieu.jsp";
		response.sendRedirect(nextJSP);
	    
	}  	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		INhomsanphamList obj;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		//PrintWriter out = response.getWriter();

		Utility util = new Utility();

		HttpSession session = request.getSession();
		String userId = util.antiSQLInspection(request.getParameter("userId"));

		obj = new NhomsanphamList();
		obj.setIsnhomchitieu("1");
		
		String action = request.getParameter("action");
		if (action == null){
			action = "";
		}

		if (action.equals("new"))
		{
			INhomsanpham nspBean = (INhomsanpham) new Nhomsanpham();

			nspBean.createRs();

			session.setAttribute("nspBean", nspBean);
			session.setAttribute("userId", userId);
			
			String nextJSP = "/TraphacoHYERP/pages/Center/NhomChiTieuNew.jsp";
			response.sendRedirect(nextJSP);

		}
		else 
		{
			String search = getSearchQuery(request, obj);

			obj.init(search);
			session.setAttribute("obj", obj);

			session.setAttribute("userId", userId);

			response.sendRedirect("/TraphacoHYERP/pages/Center/NhomChiTieu.jsp");
		}
		
	}

	private void Delete(String nspId)
	{
		dbutils db = new dbutils();
		
	    String query = "update nhomsanpham set trangthai = 0 where pk_seq ='" + nspId + "'";
	   	db.update(query);
	   	
	   	db.shutDown();
	}

	private String getSearchQuery(ServletRequest request, INhomsanphamList obj)
	{
		Utility util = new Utility();
		String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
    	if (diengiai == null)
    		diengiai = "";
    	obj.setDiengiai(diengiai);

    	String tungay = util.antiSQLInspection(request.getParameter("tungay"));
    	if (tungay == null)
    		tungay = "";    	
    	obj.setTungay(tungay);
    	
    	String denngay = util.antiSQLInspection(request.getParameter("denngay"));
    	if (denngay == null)
    		denngay = "";    	
    	obj.setDenngay(denngay);
    	
    	String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));   	
    	if (trangthai == null)
    		trangthai = "";   
    	
    	String loainhom = util.antiSQLInspection(request.getParameter("loainhom"));   	
    	if (loainhom == null)
    		loainhom = "";
    	obj.setLoainhom(loainhom);
	
    	if (trangthai.equals("2"))
    		trangthai = "";
    	obj.setTrangthai(trangthai);
    	
    	String query = " select a.PK_SEQ, a.TEN, a.DIENGIAI, a.trangthai, b.TEN as nguoitao, a.ngaytao, c.ten as nguoisua, c.NGAYSUA "+
					   " from NHOMSANPHAM a inner join NHANVIEN b on a.nguoitao = b.pk_seq"+
					   " 	inner join NHANVIEN c on a.nguoisua = c.PK_SEQ";
    	
    	if (diengiai.length()>0){
			query = query + " and upper(dbo.ftBoDau(a.diengiai)) like upper('%" + new geso.traphaco.distributor.util.Utility().replaceAEIOU(diengiai) + "%')";
    	}
    	
    	if (tungay.length() > 0) {
    		query = query + " and a.ngaytao >= '" + tungay + "'";
    	}
    	
    	if (denngay.length() > 0) {
    		query = query + " and a.ngaytao <= '" + denngay + "'";
    	}
    	
    	if(trangthai.length() > 0){
    		query = query + " and a.trangthai = '" + trangthai + "'";
    	}
    	
    	if(loainhom.length() > 0){
    		query = query + " and a.loainhom = '"+ loainhom +"'";
    	}
    	
    	return query;
	}

}