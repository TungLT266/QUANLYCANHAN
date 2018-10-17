package geso.traphaco.erp.servlets.erp_trungtamchiphi;

import geso.traphaco.center.util.Utility;

import geso.traphaco.erp.beans.erp_trungtamchiphi.IErp_trungtamdoanhthu;
import geso.traphaco.erp.beans.erp_trungtamchiphi.imp.Erp_trungtamdoanhthu;

import geso.traphaco.erp.beans.erp_trungtamchiphi.IErp_trungtamdoanhthuList;
import geso.traphaco.erp.beans.erp_trungtamchiphi.imp.Erp_trungtamdoanhthuList;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Erp_TrungTamDoanhThuUpdateSvl
 */
@WebServlet("/Erp_TrungTamDoanhThuUpdateSvl")
public class Erp_TrungTamDoanhThuUpdateSvl extends HttpServlet{
private static final long serialVersionUID = 1L;

public Erp_TrungTamDoanhThuUpdateSvl() {
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
    IErp_trungtamdoanhthu TTDTBean = new Erp_trungtamdoanhthu();
    TTDTBean.setUserId(userId);
    TTDTBean.setCtyId(ctyId);
    TTDTBean.setId(Id);   	    
	TTDTBean.init();
	    
	// Data is saved into session
	session.setAttribute("TTDTBean", TTDTBean);
	session.setAttribute("userId", userId);

	String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_TrungTamDoanhThuUpdate.jsp";
	if(querystring.contains("display"))
		nextJSP = "/TraphacoHYERP/pages/Erp/Erp_TrungTamDoanhThuDisplay.jsp";
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
    IErp_trungtamdoanhthu TTDTBean = new Erp_trungtamdoanhthu();
    TTDTBean.setCtyId(ctyId);
    
    String Id = util.antiSQLInspection(request.getParameter("TTDTId"));
    TTDTBean.setId(Id);
    
    String userId = util.antiSQLInspection(request.getParameter("userId"));
    TTDTBean.setUserId(userId);
        
    
    String ma = util.antiSQLInspection(request.getParameter("ma"));
    if(ma.length()==0){
    	err = true;
    	if(TTDTBean.getMsg().length() == 0) TTDTBean.setMsg("Vui lòng nhập Mã của Trung tâm Doanh Thu" );	    	
    }else{
    	TTDTBean.setMa(ma);
    }
    
    String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
    if(diengiai == null) diengiai = ""; 	 

    if(diengiai.length()==0){
    	err = true;
    	if(TTDTBean.getMsg().length() == 0) TTDTBean.setMsg("Vui lòng nhập Diễn giải của Trung tâm Doanh Thu" );	    	
    }else{
    	TTDTBean.setDiengiai(diengiai);
    }

    String action = request.getParameter("action");
    System.out.println("action :" + action);
       	  	    
    String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
    if(trangthai == null){
    	TTDTBean.setTrangthai("0");
    }else{
    	TTDTBean.setTrangthai("1");
    }	    
    
    String pb = util.antiSQLInspection(request.getParameter("pb"));
    if(pb == null){
    	pb = "0";
    	TTDTBean.setPhanbo("0");
    }else{
    	pb = "1";
    	TTDTBean.setPhanbo("1");
    }	    

    
    if(pb.equals("1")){
    	String[] pbIds = request.getParameterValues("pbIds");
    	TTDTBean.setPbIds(pbIds);
    
    	float pt = 0;
    
    	if(pbIds != null){
    		for(int i = 0; i < pbIds.length; i++){
    			String tmp = util.antiSQLInspection(request.getParameter("pt" + pbIds[i] )).trim();
    		
    			if(tmp.length() == 0) tmp = "0";
    		
    			pbIds[i] = pbIds[i] + "-" + tmp;
    		
    			pt = pt + Float.parseFloat(tmp);
    		}
    	
    		if(pt > 100){
    			err = true;
    			TTDTBean.setMsg("Tổng số phần trăm không được vượt quá 100%");
    		}else if (pt < 100){
    			err = true;
    			TTDTBean.setMsg("Tổng số phần trăm không được nhỏ hơn 100%");
    		}
    	
    		TTDTBean.setPbIds(pbIds);
    	}else{
    		err = true;
    		TTDTBean.setMsg("Vui lòng chọn Trung Tâm Doanh Thu và nhập phần trăm để phân bổ");
    	}
    }
    
    if(Id.length()>0) {
    	if(err){
			//TTDTBean.init();
       	    
			// Data is saved into session
			session.setAttribute("TTDTBean", TTDTBean);
			session.setAttribute("userId", userId);

			String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_TrungTamDoanhThuUpdate.jsp";
			response.sendRedirect(nextJSP);
    		
    	}else{
    		if(action.equals("save")){
    			if(!TTDTBean.Update()){
    				//TTDTBean.init();
    				//TTDTBean.set
    				// Data is saved into session
    				session.setAttribute("TTDTBean", TTDTBean);
    				session.setAttribute("userId", userId);

    				String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_TrungTamDoanhThuUpdate.jsp";
    				response.sendRedirect(nextJSP);
    		
    			}else{
    				IErp_trungtamdoanhthuList obj = (IErp_trungtamdoanhthuList) new Erp_trungtamdoanhthuList();
    				obj.setCtyId(ctyId);
    				obj.setUserId(userId);
    				obj.init();
    				session.setAttribute("obj", obj);
    				session.setAttribute("userId", userId);
    			    String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_TrungTamDoanhThu.jsp";
    				
    				response.sendRedirect(nextJSP);	    		
    			}
    		}else{
    			session.setAttribute("TTDTBean", TTDTBean);
    			session.setAttribute("userId", userId);

    			String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_TrungTamDoanhThuUpdate.jsp";
    			response.sendRedirect(nextJSP);
    			
    		}
    	}
    }else{
    	if(err){
//    		TTDTBean.initNew();
    		session.setAttribute("TTDTBean", TTDTBean);
			session.setAttribute("userId", userId);

			String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_TrungTamDoanhThuNew.jsp";
			response.sendRedirect(nextJSP);
    		
    	}else{
    		if(action.equals("save")){
    			if(!TTDTBean.New()){
  //  				TTDTBean.initNew();
    				session.setAttribute("TTDTBean", TTDTBean);
    				session.setAttribute("userId", userId);

    				String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_TrungTamDoanhThuNew.jsp";
    				response.sendRedirect(nextJSP);
    		
    			}else{
    				IErp_trungtamdoanhthuList obj = (IErp_trungtamdoanhthuList) new Erp_trungtamdoanhthuList();
    				obj.setCtyId(ctyId);
    				obj.setUserId(userId);
    				obj.init();
    				session.setAttribute("obj", obj);
    				session.setAttribute("userId", userId);
    			    String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_TrungTamDoanhThu.jsp";
    				
    				response.sendRedirect(nextJSP);	    		
    			}
    		}else{
    			session.setAttribute("TTDTBean", TTDTBean);
    			session.setAttribute("userId", userId);

    			String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_TrungTamDoanhThuNew.jsp";
    			response.sendRedirect(nextJSP);
    			
    		}

    	}
    }
    
}

}
