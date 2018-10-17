package geso.traphaco.erp.servlets.yclaymaukiemnghiem;

import geso.traphaco.center.util.GiuDieuKienLoc;
import geso.traphaco.distributor.util.Utility;
import geso.traphaco.erp.beans.kehoachlaymau.IKeHoachLayMau;
import geso.traphaco.erp.beans.kehoachlaymau.imp.KeHoachLayMau;
import geso.traphaco.erp.beans.thanhtoanhoadon.IErpThanhtoanhoadonList;
import geso.traphaco.erp.beans.thanhtoanhoadon.imp.ErpThanhtoanhoadonList;
import geso.traphaco.erp.beans.yclaymaukiemnghiem.IYCLayMauKiemNghiem;
import geso.traphaco.erp.beans.yclaymaukiemnghiem.IYCLayMauKiemNghiemList;
import geso.traphaco.erp.beans.yclaymaukiemnghiem.imp.YCLayMauKiemNghiem;
import geso.traphaco.erp.beans.yclaymaukiemnghiem.imp.YCLayMauKiemNghiemList;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class YCLayMauKiemNghiemSvl
 */
@WebServlet("/YCLayMauKiemNghiemSvl")
public class YCLayMauKiemNghiemSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public YCLayMauKiemNghiemSvl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		PrintWriter out;
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}
		else
		{
			session.setMaxInactiveInterval(30000);
		
			out = response.getWriter();
			Utility util = new Utility();
			
	    	String querystring = request.getQueryString();
		    userId = util.getUserId(querystring);
		    String ctyId = (String)session.getAttribute("congtyId");
		    String nppId = (String)session.getAttribute("nppId");
		    out.println(userId);
		    String id = util.getId(querystring);  	
		    String action = util.getAction(querystring);
		    System.out.println("action343 "+action);
		    if (userId.length()==0)
		    	userId = util.antiSQLInspection(request.getParameter("userId"));
		    
		    IYCLayMauKiemNghiemList obj = new YCLayMauKiemNghiemList();
		    
		    if(action.equals("chotphieu")){
				if(!obj.Chotphieu(id)){
					obj.setMsg("Vui lòng kiểm tra lại phiếu !!!");
				}
				obj.init("");
				System.out.println("vao day");
				String nextJSP;
				nextJSP = "/TraphacoHYERP/pages/Erp/YCLayMauKiemNghiemList.jsp";
				session.setAttribute("obj", obj);
				session.setAttribute("userId", userId);
		        response.sendRedirect(nextJSP);
			} 
		    else if(action.equals("bochotphieu")){
				if(!obj.Bochotphieu(id)){
					obj.setMsg("Vui lòng kiểm tra lại phiếu !!!");
				}
				obj.init("");
				System.out.println("vao day: bo chot phieu");
				String nextJSP;
				nextJSP = "/TraphacoHYERP/pages/Erp/YCLayMauKiemNghiemList.jsp";
				session.setAttribute("obj", obj);
				session.setAttribute("userId", userId);
		        response.sendRedirect(nextJSP);
			} 
			else if(action.equals("xoaphieu")){
				if(!obj.Xoaphieu(id)){
					obj.setMsg("Vui lòng kiểm tra lại phiếu !!!");
				}
				obj.init("");
				System.out.println("vao day");
				String nextJSP;
				nextJSP = "/TraphacoHYERP/pages/Erp/YCLayMauKiemNghiemList.jsp";
				session.setAttribute("obj", obj);
				session.setAttribute("userId", userId);
		        response.sendRedirect(nextJSP);
			} else{
	        obj.setCtyId(ctyId);
			obj.setUserId(userId);			
			obj.setNppId(nppId);
			obj.init("");
	        String nextJSP;
	        nextJSP = "/TraphacoHYERP/pages/Erp/YCLayMauKiemNghiemList.jsp";
	 
	        session.setAttribute("obj", obj);
	        session.setAttribute("userId", userId);
	        response.sendRedirect(nextJSP);
			}
		}
	}

 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		String action = request.getParameter("action");
		String nextJSP;
		
		Utility util = new Utility();
		String querystring = request.getQueryString();
		String ServerletName = this.getServletName();
		
	    String ctyId = (String)session.getAttribute("congtyId");
	    String nppId = (String)session.getAttribute("nppId");
	    
	    IYCLayMauKiemNghiemList obj = new YCLayMauKiemNghiemList();
	    obj.setCtyId(ctyId);
		obj.setUserId(userId);			
		obj.setNppId(nppId);
		
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}else
		if(action.equals("new")){
			IYCLayMauKiemNghiem obj1 = new YCLayMauKiemNghiem();
			obj1.createRs();
			nextJSP = "/TraphacoHYERP/pages/Erp/YCLayMauKiemNghiemNew.jsp";
			session.setAttribute("obj", obj1);
	        response.sendRedirect(nextJSP);
		}else
		if(action.equals("view") || action.equals("next") || action.equals("prev"))
    	{
			
	    	this.getSearchQuery(request, obj, util);
	    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));	    	
	    	obj.init("");
	    	
	    	//// SET QUERY Ở ĐIỀU KIỆN LỌC ( DOPOST ) + 	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	//LƯU Ý : ĐẶT SAU INIT
	    	String querySearch = GiuDieuKienLoc.createParams(obj);
	    	util.setSearchToHM(userId, session,ServerletName, querySearch);
	    	
	    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
	    	session.setAttribute("obj", obj);
	    	session.setAttribute("userId", userId);
	    	nextJSP = "/TraphacoHYERP/pages/Erp/YCLayMauKiemNghiemList.jsp";
	    	response.sendRedirect(nextJSP);
	    }
		else
		{ 
		 	this.getSearchQuery(request, obj, util);
	    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));	    	
	    	obj.init("");
	    	
	    	//// SET QUERY Ở ĐIỀU KIỆN LỌC ( DOPOST ) + 	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	//LƯU Ý : ĐẶT SAU INIT
	    	String querySearch = GiuDieuKienLoc.createParams(obj);
	    	util.setSearchToHM(userId, session,ServerletName, querySearch);
	    	
	    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
	    	session.setAttribute("obj", obj);
	    	session.setAttribute("userId", userId);
	        nextJSP = "/TraphacoHYERP/pages/Erp/YCLayMauKiemNghiemList.jsp";
	        response.sendRedirect(nextJSP);
		}
	}
	
	public void getSearchQuery(HttpServletRequest request, IYCLayMauKiemNghiemList obj, Utility util)
	{
		
		if(request != null)
		{
			String tungay = util.antiSQLInspection(request.getParameter("tungay"));
			if(tungay == null)
				tungay = "";
			obj.setTungay(tungay);
			
			String denngay = util.antiSQLInspection(request.getParameter("denngay"));
			if(denngay == null)
				denngay = "";
			obj.setDenngay(denngay);
			
			String loaikiemdinh = util.antiSQLInspection(request.getParameter("loaikiemdinh"));
			if(loaikiemdinh == null)
				loaikiemdinh = "";
			obj.setLoaiKiemDinh(loaikiemdinh);
			
			String loaimaukn = util.antiSQLInspection(request.getParameter("loaimaukn"));
			if(loaimaukn == null)
				loaimaukn = "";
			obj.setMauKiemNghiemId(loaimaukn);
			
			
			String soidphieu = util.antiSQLInspection(request.getParameter("soidphieu"));
			if(soidphieu == null)
				soidphieu = "";
			obj.setSophieu(soidphieu);
			
			String sophieukd = util.antiSQLInspection(request.getParameter("sophieukd"));
			if(sophieukd == null)
				sophieukd = "";
			obj.setSophieukiemdinh(sophieukd);
			
			String sanphamkiemdinh = util.antiSQLInspection(request.getParameter("sanphamkiemdinh"));
			if(sanphamkiemdinh == null)
				sanphamkiemdinh = "";
			obj.setSanphamId(sanphamkiemdinh);
			
			String quytrinh = util.antiSQLInspection(request.getParameter("quytrinh"));
			if(quytrinh == null)
				quytrinh = "";
			obj.setQuytrinhmauso(quytrinh);
			
		
		}
		
		
	
	}
	
}
