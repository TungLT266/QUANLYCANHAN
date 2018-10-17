package geso.traphaco.erp.servlets.nhomchiphi;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.nhomchiphi.IChiphi;
import geso.traphaco.erp.beans.nhomchiphi.IChiphiList;
import geso.traphaco.erp.beans.nhomchiphi.imp.Chiphi;
import geso.traphaco.erp.beans.nhomchiphi.imp.ChiphiList;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Erp_ChiphiUpdateSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Erp_ChiphiUpdateSvl() {
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
	    
	    IChiphi ncpBean = new Chiphi();
	    ncpBean.setCtyId(ctyId);
	    ncpBean.setUserId(userId);
   	    ncpBean.setId(Id);   	    
   	    ncpBean.init();
   	    
		// Data is saved into session
		session.setAttribute("ncpBean", ncpBean);
		session.setAttribute("userId", userId);

		String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_ChiphiUpdate.jsp";
		if(querystring.contains("display"))
			nextJSP = "/TraphacoHYERP/pages/Erp/Erp_ChiphiDisplay.jsp";
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
	    
	    IChiphi ncpBean = new Chiphi();
	    ncpBean.setCtyId(ctyId);
	    
	    String Id = util.antiSQLInspection(request.getParameter("ncpId"));
	    ncpBean.setId(Id);
	    
	    String userId = util.antiSQLInspection(request.getParameter("userId"));
	    ncpBean.setUserId(userId);
	    
	    String ten = util.antiSQLInspection(request.getParameter("ten"));
	    if(ten.length()==0){
	    	err = true;
	    	if(ncpBean.getMsg().length() == 0) ncpBean.setMsg("Vui lòng nhập Tên của Khoản Mục Chi Phí" );	    	
	    }else{
	    	ncpBean.setTen(ten);
	    }
	    
	    String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
	    if(diengiai == null) diengiai = ""; 	 
	    ncpBean.setDiengiai(diengiai);
	    
	    
	    String ttcpId = util.antiSQLInspection(request.getParameter("ttcpId"));
	    if(ttcpId.length() > 0) ncpBean.setTtcpId(ttcpId);
	    else{
	    	err = true;
	    	ncpBean.setMsg("Vui lòng chọn Trung Tâm Chi Phí");
	    }
	    	    
	    String tkId = util.antiSQLInspection(request.getParameter("tkId"));
	    if(tkId == null ) tkId = "";
	    System.out.println(tkId);
	    
	    if(tkId.length() > 0){
	    	
	    	ncpBean.setTkId(tkId);
		    
	    }else {
	    	ncpBean.setMsg("Vui lòng chọn Tài Khoản Kế Toán");
	    }

	    String dvttId = util.antiSQLInspection(request.getParameter("dvttId"));
	    if(dvttId == null) dvttId = "";
	    if(dvttId.trim().equals("0")) dvttId = "";
	    
	    if(dvttId.length() > 0){
	    	
		    ncpBean.setDvttId(dvttId);
		    
	    }else{
	    	err = true;
	    	ncpBean.setMsg("Vui lòng chọn Phòng ban chịu trách nhiệm nhập chi phí");
	    }
	    	    
	    String action = request.getParameter("action");
	    System.out.println("action :" + action);
	    	    	    	
    	System.out.println("___Trung tam chi phi: " + ttcpId);
	    	    
	    String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
	    if(trangthai == null){
	    	ncpBean.setTrangthai("0");
	    }else{
	    	ncpBean.setTrangthai("1");
	    }	    
	    String kmdacbiet = util.antiSQLInspection(request.getParameter("kmdacbiet"));
	    if(kmdacbiet == null){
	    	ncpBean.setKmDacBiet("0");
	    }else{
	    	ncpBean.setKmDacBiet("1");
	    }	  
	    if(Id.length()>0) {
    		if(action.equals("save") & !err){
    			if(!ncpBean.Update()){
//    				ncpBean.init();
    				ncpBean.createTkList();
    				ncpBean.createTtcpList();
    				ncpBean.createDvttList();
	       	    
    				// Data is saved into session
    				session.setAttribute("ncpBean", ncpBean);
    				session.setAttribute("userId", userId);

    				String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_ChiphiUpdate.jsp";
    				response.sendRedirect(nextJSP);
	    		
    			}else{
    				IChiphiList obj = (IChiphiList) new ChiphiList();
    				obj.setCtyId(ctyId);
    				obj.init();
    				session.setAttribute("obj", obj);
    				session.setAttribute("userId", userId);
    			
    				String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_Chiphi.jsp";
    				response.sendRedirect(nextJSP);	    		
    			}
    		}else{
  //      			ncpBean.init();
    				ncpBean.createTkList();
    				ncpBean.createTtcpList();
    				ncpBean.createDvttList();

        			// Data is saved into session
        			session.setAttribute("ncpBean", ncpBean);
        			session.setAttribute("userId", userId);

        			String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_ChiphiUpdate.jsp";
        			response.sendRedirect(nextJSP);
    	    		
	    	}
	    }else{
	    	if(action.equals("save") & !err){
	    		if(!ncpBean.New()){
//	    			ncpBean.initNew();
    				ncpBean.createTkList();
    				ncpBean.createTtcpList();
    				ncpBean.createDvttList();

	    			session.setAttribute("ncpBean", ncpBean);
	    			session.setAttribute("userId", userId);

	    			String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_ChiphiNew.jsp";
	    			response.sendRedirect(nextJSP);
	    		
	    		}else{
	    			IChiphiList obj = (IChiphiList) new ChiphiList();
	    			obj.setCtyId(ctyId);
	    			obj.init();
	    			session.setAttribute("obj", obj);
	    			session.setAttribute("userId", userId);
	    			
	    			String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_Chiphi.jsp";
	    			response.sendRedirect(nextJSP);	    		
	    		}
	    		
	    	}else{
				ncpBean.createTkList();
				ncpBean.createTtcpList();
				ncpBean.createDvttList();

	    		session.setAttribute("ncpBean", ncpBean);
    			session.setAttribute("userId", userId);

    			String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_ChiphiNew.jsp";
    			response.sendRedirect(nextJSP);
	    		
	    	}
	    }
	    
	}

}
