package geso.traphaco.erp.servlets.nhomchiphi;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.nhomchiphi.INhomchiphi;
import geso.traphaco.erp.beans.nhomchiphi.INhomchiphiList;
import geso.traphaco.erp.beans.nhomchiphi.imp.Nhomchiphi;
import geso.traphaco.erp.beans.nhomchiphi.imp.NhomchiphiList;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Erp_NhomchiphiUpdateSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Erp_NhomchiphiUpdateSvl() {
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
	    
	    INhomchiphi ncpBean = new Nhomchiphi();
	    ncpBean.setCtyId(ctyId);
	    ncpBean.setUserId(userId);
   	    ncpBean.setId(Id);   	    
   	    ncpBean.init();
   	    
		// Data is saved into session
		session.setAttribute("ncpBean", ncpBean);
		session.setAttribute("userId", userId);

		String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_NhomchiphiUpdate.jsp";
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
	    
	    INhomchiphi ncpBean = new Nhomchiphi();
	    ncpBean.setCtyId(ctyId);
	    
	    String Id = util.antiSQLInspection(request.getParameter("ncpId"));
	    ncpBean.setId(Id);
	    
	    String userId = util.antiSQLInspection(request.getParameter("userId"));
	    ncpBean.setUserId(userId);
	    
	    String dvttId = util.antiSQLInspection(request.getParameter("dvttId"));
	    if(dvttId.equals("0")){
	    	err = true;
	    	ncpBean.setMsg("Vui lòng chọn Đơn vị thực hiện" );
	    }else{
		    ncpBean.setDvttId(dvttId);
	    }
	    
	    String ten = util.antiSQLInspection(request.getParameter("ten"));
	    if(ten.length()==0){
	    	err = true;
	    	if(ncpBean.getMsg().length() == 0) ncpBean.setMsg("Vui lòng nhập Tên của Nhóm chi phí" );	    	
	    }else{
	    	ncpBean.setTen(ten);
	    }
	    
	    String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
	    if(diengiai == null) diengiai = ""; 	 
	    ncpBean.setDiengiai(diengiai);
	    
	    String loai = util.antiSQLInspection(request.getParameter("loai"));
	    if(loai != null) ncpBean.setLoai(loai);	    
	    
	    String parentId = util.antiSQLInspection(request.getParameter("nhomId"));
	    ncpBean.setParentId(parentId);
	    
	    String tkId = util.antiSQLInspection(request.getParameter("tkId"));
	    String ttcpId = util.antiSQLInspection(request.getParameter("ttcpId"));
	    
	    System.out.println("Loai: " + loai);
	    System.out.println("parentId: " + parentId);
	    
	    String action = request.getParameter("action");
	    System.out.println("action :" + action);
	    
	    if(parentId.length() == 0 & !action.equals("save")){
	    	err = true;
	    	ncpBean.setMsg("Vui lòng chọn Nhóm thuộc về");
	    }else{
	    	ncpBean.setParentId(parentId);	
	    }
	    	
    	ncpBean.setTkId(tkId);
    	ncpBean.setTtcpId(ttcpId);
    	
    	System.out.println("___Trung tam chi phi: " + ttcpId);
	    	    
	    String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
	    if(trangthai == null){
	    	ncpBean.setTrangthai("0");
	    }else{
	    	ncpBean.setTrangthai("1");
	    }	    
	    
	    if(Id.length()>0) {
	    	if(err){
    			ncpBean.init();
	       	    
    			// Data is saved into session
    			session.setAttribute("ncpBean", ncpBean);
    			session.setAttribute("userId", userId);

    			String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_NhomchiphiUpdate.jsp";
    			response.sendRedirect(nextJSP);
	    		
	    	}else{
	    		if(action.equals("save")){
	    			if(!ncpBean.Update()){
	    				ncpBean.init();
	       	    
	    				// Data is saved into session
	    				session.setAttribute("ncpBean", ncpBean);
	    				session.setAttribute("userId", userId);

	    				String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_NhomchiphiUpdate.jsp";
	    				response.sendRedirect(nextJSP);
	    		
	    			}else{
	    				INhomchiphiList obj = (INhomchiphiList) new NhomchiphiList();
	    				obj.setCtyId(ctyId);
	    				obj.init();
	    				session.setAttribute("obj", obj);
	    				session.setAttribute("userId", userId);
	    			
	    				String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_Nhomchiphi.jsp";
	    				response.sendRedirect(nextJSP);	    		
	    			}
	    		}
	    	}
	    }else{
	    	if(err){
	    		ncpBean.initNew();
	    		session.setAttribute("ncpBean", ncpBean);
    			session.setAttribute("userId", userId);

    			String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_NhomchiphiNew.jsp";
    			response.sendRedirect(nextJSP);
	    		
	    	}else{
	    		if(action.equals("save")){
	    			if(!ncpBean.New()){
	    				ncpBean.initNew();
	    				session.setAttribute("ncpBean", ncpBean);
	    				session.setAttribute("userId", userId);

	    				String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_NhomchiphiNew.jsp";
	    				response.sendRedirect(nextJSP);
	    		
	    			}else{
	    				INhomchiphiList obj = (INhomchiphiList) new NhomchiphiList();
	    				obj.setCtyId(ctyId);
	    				obj.init();
	    				session.setAttribute("obj", obj);
	    				session.setAttribute("userId", userId);
	    			
	    				String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_Nhomchiphi.jsp";
	    				response.sendRedirect(nextJSP);	    		
	    			}
	    		}
	    	}
	    }
	    
	}

}
