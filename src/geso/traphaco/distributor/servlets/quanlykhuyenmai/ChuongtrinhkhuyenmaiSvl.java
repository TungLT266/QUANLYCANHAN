package geso.traphaco.distributor.servlets.quanlykhuyenmai;

import geso.traphaco.distributor.beans.quanlykhuyenmai.IChuongtrinhkhuyenmai;
import geso.traphaco.distributor.beans.quanlykhuyenmai.imp.Chuongtrinhkhuyenmai;

import geso.traphaco.center.util.Utility;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ChuongtrinhkhuyenmaiSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public ChuongtrinhkhuyenmaiSvl() {
        super();
    }
    
    private Utility util = new Utility();
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}else{
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");

	    IChuongtrinhkhuyenmai ctkmBean = new Chuongtrinhkhuyenmai();
	    ctkmBean.setRequestObj(request);
	    String querystring = request.getQueryString();
	    userId = util.getUserId(querystring);
//	    System.out.println(userId);
	    
	    ctkmBean.setUserId(userId);
	   
	    
	    ctkmBean.init();	    
		session.setAttribute("ctkm", ctkmBean);
		session.setAttribute("userId", userId);
		String nextJSP = "/TraphacoHYERP/pages/Distributor/ChuongTrinhKhuyenMai.jsp";
		response.sendRedirect(nextJSP);
		}
	    
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}else{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
//		HttpSession session = request.getSession();
		//PrintWriter out = response.getWriter();

		//String contentType = request.getContentType();
		//here we are checking the content type is not equal to Null and as well as the passed data from mulitpart/form-data is greater than or equal to 0

		userId = (String)session.getAttribute("userId");
		//IChuongtrinhkhuyenmai ctkmBean = new Chuongtrinhkhuyenmai();
		String action = request.getParameter("action");
		String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
		String tungay = util.antiSQLInspection(request.getParameter("tungay"));
		String denngay = util.antiSQLInspection(request.getParameter("denngay"));
		String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
		
		IChuongtrinhkhuyenmai ctkmBean = new Chuongtrinhkhuyenmai();
		ctkmBean.setDiengiai(diengiai);
		ctkmBean.setTungay(tungay);
		ctkmBean.setDenngay(denngay);
		ctkmBean.setTrangthai(trangthai);
	    ctkmBean.setUserId(userId);
	    
		//a
	    	
    	ctkmBean.setRequestObj(request);  	
	    	
	    	//------------------------
	    	
	    	    	
    	//ctkmBean = new DaidienkinhdoanhList(search);
	    	
    	if(action.equals("view") || action.equals("next") || action.equals("prev")){
		    	
    		ctkmBean.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
	    	ctkmBean.init();
	    	ctkmBean.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
    	}
    	else
	    	ctkmBean.init();		
			
	    	session.setAttribute("ctkm", ctkmBean);  	
    		session.setAttribute("userId", userId);

    		response.sendRedirect("/TraphacoHYERP/pages/Distributor/ChuongTrinhKhuyenMai.jsp");
    		
		}

		
		
	}


	public String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

}
