package geso.traphaco.erp.servlets.debit;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.debit.IErpCredit;
import geso.traphaco.erp.beans.debit.IErpCreditList;
import geso.traphaco.erp.beans.debit.imp.ErpCredit;
import geso.traphaco.erp.beans.debit.imp.ErpCreditList;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpCreditSvl extends HttpServlet{
	private static final long serialVersionUID = 1L;

    public ErpCreditSvl() {
        super();
    }
    String URL;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		HttpSession session = request.getSession();
		String ctyId = (String)session.getAttribute("congtyId");
		String userTen = (String) session.getAttribute("userTen"); 
		
		PrintWriter out = response.getWriter();
		IErpCreditList obj;    	    
		    
		obj = new ErpCreditList();
		    
		Utility util = new Utility();
		out = response.getWriter();
			    
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		
		out.println(userId);
		obj.init("");
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		session.setAttribute("userTen", userTen);
		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpCredit.jsp");	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		HttpSession session=request.getSession();
		String ctyId = (String)session.getAttribute("congtyId");
		Utility util = new Utility();	
		
		String userId = (String)session.getAttribute("userId");
		String userTen = (String)session.getAttribute("userTen");
		String action = request.getParameter("action");
		String nextJSP ="";
		
		// get params from input
		String maChungTu = request.getParameter("sochungtu");
		String trangThai = request.getParameter("trangthai");
		String dienGiai = request.getParameter("diengiai");
		
		if( maChungTu == null){
			maChungTu = "";
		}
		if( trangThai == null){
			trangThai = "";
		}
		if( dienGiai == null){
			dienGiai = "";
		}
		
		if( action.equals("new")){
			IErpCredit obj = new ErpCredit();
			obj.init("", false);
			
			session.setAttribute("obj", obj);
			session.setAttribute("userId", userId);
			session.setAttribute("userTen", userTen);
			session.setAttribute("action", "new");
			nextJSP = "/TraphacoHYERP/pages/Erp/ErpCreditNew.jsp";
			response.sendRedirect(nextJSP);
			
		} else{
			
			// set các thuộc tính vào
			
			IErpCreditList obj = new ErpCreditList();
			obj.setDienGiai(dienGiai);
			obj.setTrangThai(trangThai);
			obj.setMa(maChungTu);
			
			String query = SearchQuery(obj);
			obj.init(query);
			
			session.setAttribute("obj", obj);
			session.setAttribute("userId", userId);
			session.setAttribute("userTen", userTen);
			nextJSP = "/TraphacoHYERP/pages/Erp/ErpCredit.jsp";
			response.sendRedirect(nextJSP);
		}
		
	}

	private String SearchQuery(IErpCreditList obj) {
		String query =  " select de.PK_SEQ, de.DOITUONG, isnull(nv.TEN,'') as NGUOITAO, " +
        				" isnull(nv2.TEN,'') as NGUOISUA, de.NGAYTAO, de.NGAYSUA," +
        				" de.DIENGIAI, de.TRANGTHAI from ERP_CREDIT  de inner join NHANVIEN nv on de.NGUOITAO= nv.PK_SEQ " +
        				" inner join NHANVIEN nv2 on nv2.PK_SEQ = de.NGUOISUA where 1=1";
		if( obj.getMa().trim().length() >0){
			query += " AND de.PK_SEQ like '%" + obj.getMa()+"'";
		}
		if( obj.getTrangThai().trim().length() >0){
			query += " AND de.TRANGTHAI =" + obj.getTrangThai();
		}
		if( obj.getDienGiai().trim().length() >0){
			query += " AND de.DIENGIAI like N'%" + obj.getDienGiai()+"%'";
		}
		return query;
	}
}
