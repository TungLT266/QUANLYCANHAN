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

public class ErpTinhgiatonTPSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    
    public ErpTinhgiatonTPSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpTinhgiatonTP obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	   
	    obj = new ErpTinhgiatonTP();
	    obj.setUserId(userId);
	    obj.setCongtyId( session.getAttribute("congtyId").toString() );
	    obj.setNppId( session.getAttribute("nppId").toString() );
	    
		session.setAttribute("obj", obj);
				
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpTinhGiaTonTP.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpTinhgiatonTP obj;
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
	    
	    obj = new ErpTinhgiatonTP();
	    obj.setUserId(userId);
	    obj.setCongtyId( session.getAttribute("congtyId").toString() );
	    obj.setNppId( session.getAttribute("nppId").toString() );
	    
	    String tungay = request.getParameter("tungay");
	    if(tungay == null)
	    	tungay = "";
	    obj.setTungay(tungay);
	    
	    String denngay = request.getParameter("denngay");
	    if(denngay == null)
	    	denngay = "";
	    obj.setDenngay(denngay);
	    
	    if(tungay.length() <= 0)
	    	obj.setMsg("Bạn phải chọn từ ngày");
	    
	    if(denngay.length() <= 0)
	    	obj.setMsg("Bạn phải chọn đến ngày");
	    
	    if( action.equals("tinhgiavon") )
	    {
		    String msg = obj.tinhGiaTonkho(tungay, denngay);
		    if(msg.length() <= 0)
		    	msg = "Tính giá tồn kho từ ngày: " + tungay + ", đến ngày: " + denngay + " thành công";
		    obj.setMsg(msg);
	    }
	    
	    obj.createRs();
		session.setAttribute("obj", obj);
				
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpTinhGiaTonTP.jsp";
		response.sendRedirect(nextJSP);
	}

}
