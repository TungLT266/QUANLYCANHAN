package geso.traphaco.erp.servlets.sohoadon;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.sohoadon.ISohoadon;
import geso.traphaco.erp.beans.sohoadon.ISohoadonList;
import geso.traphaco.erp.beans.sohoadon.imp.Sohoadon;
import geso.traphaco.erp.beans.sohoadon.imp.SohoadonList;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Erp_SohoadonUpdateSvl extends HttpServlet {
	static final long serialVersionUID = 1L;
	
	public Erp_SohoadonUpdateSvl() {
		super();
	}   	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    PrintWriter out = response.getWriter();
	    HttpSession session = request.getSession();
	    Utility util = new Utility();
	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    if (userId.length()==0)
	    	userId = request.getParameter("userId");
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    	    
	    String Id = "";
	    String nextJSP = "";
	    
	    if(action.equals("update")){
	    	Id = util.antiSQLInspection(request.getParameter("update"));
	    	nextJSP = "/TraphacoHYERP/pages/Erp/Erp_SohoadonUpdate.jsp";
	    }
	    
	    if(action.equals("display")){
	    	Id = util.antiSQLInspection(request.getParameter("display"));
	    	nextJSP = "/TraphacoHYERP/pages/Erp/Erp_SohoadonDisplay.jsp";
	    }

	    out.println(Id);
	    
    	ISohoadon shdBean = new Sohoadon();
    	shdBean.setCtyId(ctyId);
    	shdBean.setUserId(userId);
    	shdBean.setId(Id);
    	shdBean.init();
    	// Save Data into session
    	session.setAttribute("shdBean", shdBean);
    	session.setAttribute("userId", userId);
		 
    	response.sendRedirect(nextJSP);
    
	}  	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
		PrintWriter out = response.getWriter();
	    HttpSession session = request.getSession();
	   
	    ISohoadon shdBean = (ISohoadon) new Sohoadon();
	    String userId = request.getParameter("userId");
	    String ctyId = (String)session.getAttribute("congtyId");
	    shdBean.setCtyId(ctyId);
	    shdBean.setUserId(userId);
	    
		Utility util = new Utility();
		session.setAttribute("util", util);

		String Id = util.antiSQLInspection(request.getParameter("Id"));
		shdBean.setId(Id);

		String tuso = util.antiSQLInspection(request.getParameter("tuso"));
		shdBean.setTuso(tuso);
		
		String denso = util.antiSQLInspection(request.getParameter("denso"));
		shdBean.setDenso(denso);
		
		String kyhieu = util.antiSQLInspection(request.getParameter("kyhieu"));
		shdBean.setKyhieu(kyhieu);

		String khoId = util.antiSQLInspection(request.getParameter("khoId"));
		shdBean.setKhoId(khoId);
		
		String loai = util.antiSQLInspection(request.getParameter("loai"));
		shdBean.setLoai(loai);
		
		String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
		shdBean.setTrangthai(trangthai);

		String action = util.antiSQLInspection(request.getParameter("action"));
		String nextJSP = "";
		if (Id == "")
		{
			nextJSP = "/TraphacoHYERP/pages/Erp/Erp_SohoadonNew.jsp";
		} else
		{
			nextJSP = "/TraphacoHYERP/pages/Erp/Erp_SohoadonUpdate.jsp";
		}
		
		if(action.equals("save")){
			if(Id == null){
				if (!(shdBean.New()))
				{
					shdBean.initNew(); 
					session.setAttribute("shdBean", shdBean);
					response.sendRedirect(nextJSP);
				} else
				{
				    ISohoadonList obj = (ISohoadonList) new SohoadonList();
				    obj.setCtyId(ctyId);
				    
				    obj.init();
				    session.setAttribute("obj", obj);
				    session.setAttribute("userId", userId);
						
				    nextJSP = "/TraphacoHYERP/pages/Erp/Erp_Sohoadon.jsp";
				    response.sendRedirect(nextJSP);
				}
				
			}else{
				if (!(shdBean.Update()))
				{
					shdBean.init(); 
					session.setAttribute("shdBean", shdBean);
					response.sendRedirect(nextJSP);
				} else
				{
				    ISohoadonList obj = (ISohoadonList) new SohoadonList();
				    obj.setCtyId(ctyId);
				    
				    obj.init();
				    session.setAttribute("obj", obj);
				    session.setAttribute("userId", userId);
						
				    nextJSP = "/TraphacoHYERP/pages/Erp/Erp_Sohoadon.jsp";
				    response.sendRedirect(nextJSP);
				}
				
			}
		}


	}

}
