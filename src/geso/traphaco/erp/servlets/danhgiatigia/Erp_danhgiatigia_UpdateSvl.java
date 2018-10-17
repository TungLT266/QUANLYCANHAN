package geso.traphaco.erp.servlets.danhgiatigia;

import geso.traphaco.erp.beans.danhgiatigia.IDanhgiatigiaList;
import geso.traphaco.erp.beans.danhgiatigia.imp.DanhgiatigiaList;
import geso.traphaco.erp.beans.danhgiatigia.IDanhgiatigia;
import geso.traphaco.erp.beans.danhgiatigia.imp.Danhgiatigia;
import geso.traphaco.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class Erp_danhgiatigia_UpdateSvl extends HttpServlet {
	   static final long serialVersionUID = 1L;
	   
	   private Utility util = new Utility();

	   public Erp_danhgiatigia_UpdateSvl() {
			super();
		}   	
		
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		    request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
		    
		    PrintWriter out = response.getWriter();
		    HttpSession session = request.getSession();
		    		    
		    IDanhgiatigia dgtigiaBean = (IDanhgiatigia) new Danhgiatigia();
		    	
		    String querystring = request.getQueryString();
		    String userId = util.getUserId(querystring);
		    
		    String Id = util.getId(querystring);
		    if(Id == null) Id = "";
		    dgtigiaBean.setId(Id);
		    
		    String ctyId = (String)session.getAttribute("congtyId");
		    dgtigiaBean.setCtyId(ctyId);
		    
		    out.println(userId);
		    
		    if (userId.length()==0)
		    	userId = request.getParameter("userId");
		    
		    String action = util.getAction(querystring);
		    System.out.println(action);

		    if (action.equals("update"))
			{
				dgtigiaBean.init_Update();
				session = request.getSession();
				session.setAttribute("dgtigiaBean", dgtigiaBean);

				response.sendRedirect("pages/Erp/Erp_Danhgiatigia_Update.jsp");
			}else{
				dgtigiaBean.init_Update();
				session = request.getSession();
				session.setAttribute("dgtigiaBean", dgtigiaBean);

				response.sendRedirect("pages/Erp/Erp_Danhgiatigia_Display.jsp");
			}
			
		}  	

		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		    request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
		    
			HttpSession session = request.getSession();
		    Utility util = new Utility();
		    
		    IDanhgiatigia dgtigiaBean = (IDanhgiatigia) new Danhgiatigia();
		    String ctyId = (String)session.getAttribute("congtyId");
		    dgtigiaBean.setCtyId(ctyId);

		    String userId = util.antiSQLInspection(request.getParameter("userId"));
		    dgtigiaBean.setUserId(userId);
		    	    
		    String nam = util.antiSQLInspection(request.getParameter("nam"));
		    if(nam == null) nam = "";
		    dgtigiaBean.setNam(nam);
		    
		    String thang = util.antiSQLInspection(request.getParameter("thang"));
		    if(thang == null) thang = "";
		    dgtigiaBean.setThang(thang);

		    String ghidao = util.antiSQLInspection(request.getParameter("ghidao"));
		    System.out.println(ghidao);
		    
		    if(ghidao == null) ghidao = "0";
		    dgtigiaBean.setGhidao(ghidao);

		    userId = util.antiSQLInspection(request.getParameter("userId"));
			String action = request.getParameter("action");

			if (action.equals("Save"))
			{
				dgtigiaBean.Save();
			}else{
				dgtigiaBean.init_New();
				session.setAttribute("dgtigiaBean", dgtigiaBean);
				response.sendRedirect("pages/Erp/Erp_Danhgiatigia_New.jsp");
				return;
			}

		    IDanhgiatigiaList obj = (IDanhgiatigiaList) new DanhgiatigiaList();
		    obj.setCtyId(ctyId);
		    
		    if (userId.length()==0)
		    	userId = request.getParameter("userId");
		    
		    obj.init();
		    
		    session.setAttribute("obj", obj);
		    session.setAttribute("userId", userId);
				
		    String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_Danhgiatigia.jsp";
		    response.sendRedirect(nextJSP);
			
		
		}
}
