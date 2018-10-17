package geso.traphaco.erp.servlets.doanhthu;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.doanhthu.IMucdoanhthu;
import geso.traphaco.erp.beans.doanhthu.IMucdoanhthuList;
import geso.traphaco.erp.beans.doanhthu.imp.Mucdoanhthu;
import geso.traphaco.erp.beans.doanhthu.imp.MucdoanhthuList;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Erp_MucdoanhthuUpdateSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Erp_MucdoanhthuUpdateSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");

	    Utility util = new Utility();
	    PrintWriter out = response.getWriter();
	    HttpSession session = request.getSession();

	    String querystring = request.getQueryString();
	    
	    String userId = util.getUserId(querystring);
	    System.out.println("UserId: " + userId);
	    
	    if (userId.length()==0)
	    	userId = request.getParameter("userId");
	    
	    String Id = util.getId(querystring);
	    System.out.println(Id);
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    IMucdoanhthu DtBean = new Mucdoanhthu();
	    DtBean.setCtyId(ctyId);
	    DtBean.setUserId(userId);
   	    DtBean.setId(Id);   	    
   	    DtBean.init();
   	    
		// Data is saved into session
		session.setAttribute("DtBean", DtBean);
		session.setAttribute("userId", userId);

		String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_MucdoanhthuUpdate.jsp";
		if(querystring.contains("display"))
			nextJSP = "/TraphacoHYERP/pages/Erp/Erp_MucdoanhthuDisplay.jsp";
   		response.sendRedirect(nextJSP);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    boolean err = false;
	    
	    PrintWriter out = response.getWriter();
	    HttpSession session = request.getSession();
	    Utility util = new Utility();
	    
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    IMucdoanhthu DtBean = new Mucdoanhthu();
	    DtBean.setCtyId(ctyId);
	    
	    String Id = util.antiSQLInspection(request.getParameter("DtId"));
	    DtBean.setId(Id);
	    
	    String userId = util.antiSQLInspection(request.getParameter("userId"));
	    DtBean.setUserId(userId);

	    String ten = util.antiSQLInspection(request.getParameter("ten"));
	    if(ten.length()==0){
	    	err = true;
	    	if(DtBean.getMsg().length() == 0) DtBean.setMsg("Vui lòng nhập Tên của Khoản Mục Chi Phí" );	    	
	    }else{
	    	DtBean.setTen(ten);
	    }
	    
	    String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
	    if(diengiai == null) diengiai = ""; 	 
	    DtBean.setDiengiai(diengiai);
	    
	    String tkId = util.antiSQLInspection(request.getParameter("tkId"));
	    if(tkId == null ) tkId = "";
	    
	    if(tkId.length() > 0){
	    	
	    	DtBean.setTkId(tkId);
		    
	    }else{
	    	err = true;
	    	DtBean.setMsg("Vui lòng chọn Tài Khoản Kế Toán");
	    }

	    String dvttId = util.antiSQLInspection(request.getParameter("dvttId"));
	    if(dvttId == null) dvttId = "";
	    if(dvttId.trim().equals("0")) dvttId = "";
	    
	    if(dvttId.length() > 0){
	    	
		    DtBean.setDvttId(dvttId);
		    
	    }else {
	    	err = true;
	    	DtBean.setMsg("Vui lòng chọn Phòng ban chịu trách nhiệm nhập doanh thu");
	    }
	    	

	    String ttdtId = util.antiSQLInspection(request.getParameter("TtdtId"));
	    if(ttdtId == null) 
	    	ttdtId = "";
	    
	    if(ttdtId.length() > 0) 
	    	DtBean.setTtdtId(ttdtId);
	    else{
	    	err = true;
	    	DtBean.setMsg("Vui lòng chọn Trung Tâm Doanh Thu");
	    }
	    
	    String action = request.getParameter("action");
	    System.out.println("action :" + action);
	    	    	    	
    	System.out.println("___Trung tam chi phi: " + ttdtId);
	    	    
	    String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
	    if(trangthai == null){
	    	DtBean.setTrangthai("0");
	    }else{
	    	DtBean.setTrangthai("1");
	    }	    
	    
	    System.out.println("__EROOR: " + err);
	    
	    if(Id.length()>0) {
    		if(action.equals("save") && err == false ){
    			System.out.println("I am here");
    			if(!DtBean.Update()){
//    				DtBean.init();
    				DtBean.createTkList();
    				DtBean.createTtdtList();
    				DtBean.createDvttList();
	       	    
    				// Data is saved into session
    				session.setAttribute("DtBean", DtBean);
    				session.setAttribute("userId", userId);

    				String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_MucdoanhthuUpdate.jsp";
    				response.sendRedirect(nextJSP);
	    		
    			}else{
    				IMucdoanhthuList obj = (IMucdoanhthuList) new MucdoanhthuList();
    				obj.setCtyId(ctyId);
    				obj.init();
    				session.setAttribute("obj", obj);
    				session.setAttribute("userId", userId);
    			
    				String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_Mucdoanhthu.jsp";
    				response.sendRedirect(nextJSP);	    		
    			}
    		}else{
  //      			DtBean.init();
    				DtBean.createTkList();
    				DtBean.createTtdtList();
    				DtBean.createDvttList();

        			// Data is saved into session
        			session.setAttribute("DtBean", DtBean);
        			session.setAttribute("userId", userId);

        			String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_MucdoanhthuUpdate.jsp";
        			response.sendRedirect(nextJSP);
    	    		
	    	}
	    }else{
	    	if(action.equals("save") && err == false )
	    	{
	    		System.out.println("___ERROR 115: ___________________");
	    		
	    		if(!DtBean.New()){
//	    			DtBean.initNew();
    				DtBean.createTkList();
    				DtBean.createTtdtList();
    				DtBean.createDvttList();

	    			session.setAttribute("DtBean", DtBean);
	    			session.setAttribute("userId", userId);

	    			String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_MucdoanhthuNew.jsp";
	    			response.sendRedirect(nextJSP);
	    		
	    		}else{
	    			IMucdoanhthuList obj = (IMucdoanhthuList) new MucdoanhthuList();
	    			obj.setCtyId(ctyId);
	    			obj.init();
	    			session.setAttribute("obj", obj);
	    			session.setAttribute("userId", userId);
	    			
	    			String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_Mucdoanhthu.jsp";
	    			response.sendRedirect(nextJSP);	    		
	    		}
	    		
	    	}else{
//	    		DtBean.initNew();
	    		
	    		System.out.println("___ERROR: ___________________");
				DtBean.createTkList();
				DtBean.createTtdtList();
				DtBean.createDvttList();

	    		session.setAttribute("DtBean", DtBean);
    			session.setAttribute("userId", userId);

    			String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_MucdoanhthuNew.jsp";
    			response.sendRedirect(nextJSP);
	    		
	    	}
	    }
	    
	}

}
