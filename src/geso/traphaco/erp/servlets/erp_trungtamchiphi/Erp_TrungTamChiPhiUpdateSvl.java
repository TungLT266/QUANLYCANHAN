package geso.traphaco.erp.servlets.erp_trungtamchiphi;

import geso.traphaco.center.util.Utility;

import geso.traphaco.erp.beans.erp_trungtamchiphi.IErp_trungtamchiphi;
import geso.traphaco.erp.beans.erp_trungtamchiphi.imp.Erp_trungtamchiphi;

import geso.traphaco.erp.beans.erp_trungtamchiphi.IErp_trungtamchiphiList;
import geso.traphaco.erp.beans.erp_trungtamchiphi.imp.Erp_trungtamchiphiList;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Erp_TrungTamChiPhiUpdateSvl
 */
@WebServlet("/Erp_TrungTamChiPhiUpdateSvl")
public class Erp_TrungTamChiPhiUpdateSvl extends HttpServlet{
private static final long serialVersionUID = 1L;

public Erp_TrungTamChiPhiUpdateSvl() {
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
    IErp_trungtamchiphi ttcpBean = new Erp_trungtamchiphi();
    ttcpBean.setUserId(userId);
    ttcpBean.setCtyId(ctyId);
    ttcpBean.setId(Id);   	    
	ttcpBean.init();
	    
	// Data is saved into session
	session.setAttribute("ttcpBean", ttcpBean);
	session.setAttribute("userId", userId);

	String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_TrungTamChiPhiUpdate.jsp";
	if(querystring.contains("display"))
		nextJSP = "/TraphacoHYERP/pages/Erp/Erp_TrungTamChiPhiDisplay.jsp";
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
    String action = request.getParameter("action");
    System.out.println("action :" + action);
    System.out.println("congtyId:"+ctyId);
    IErp_trungtamchiphi ttcpBean = new Erp_trungtamchiphi();
    ttcpBean.setCtyId(ctyId);
    
    String Id = util.antiSQLInspection(request.getParameter("ttcpId"));
    ttcpBean.setId(Id);
    
    String userId = util.antiSQLInspection(request.getParameter("userId"));
    ttcpBean.setUserId(userId);
        
    
    String ma = util.antiSQLInspection(request.getParameter("ma"));
    if(ma.length()==0){
    	err = true;
    	if(ttcpBean.getMsg().length() == 0) ttcpBean.setMsg("Vui lòng nhập Mã của Trung tâm chi phí" );	    	
    }else{
    	ttcpBean.setMa(ma);
    }
    
    String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
    if(diengiai == null) diengiai = ""; 	 

    if(diengiai.length()==0){
    	err = true;
    	if(ttcpBean.getMsg().length() == 0) ttcpBean.setMsg("Vui lòng nhập Diễn giải của Trung tâm chi phí" );	    	
    }else{
    	ttcpBean.setDiengiai(diengiai);
    }

    
       	  	    
    String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
    if(trangthai == null){
    	ttcpBean.setTrangthai("0");
    }else{
    	ttcpBean.setTrangthai("1");
    }	    

    String ngansach = util.antiSQLInspection(request.getParameter("ngansach"));
    if(ngansach == null){
    	ngansach = "0";
    	ttcpBean.setCongansach("0");
    }else{
    	ngansach = "1";
    	ttcpBean.setCongansach("1");
    }	    

    String pb = util.antiSQLInspection(request.getParameter("pb"));
    if(pb == null){
    	pb = "0";
    	ttcpBean.setPhanbo("0");
    }else{
    	pb = "1";
    	ttcpBean.setPhanbo("1");
    }	    

    String timkiem = util.antiSQLInspection(request.getParameter("timkiem"));
    if(timkiem == null) timkiem = "";
    ttcpBean.setTimkiem(timkiem);

//    String npb = util.antiSQLInspection(request.getParameter("npbId"));
//    ttcpBean.setNhanphanbo(npb);
    
    if(pb.equals("1")){
    	String[] pbIds = request.getParameterValues("pbIds");
    	ttcpBean.setPbIds(pbIds);
    
//    	float pt = 0;
    	
    	if(pbIds != null){
    		String chon = "";
    		for(int i = 0; i < pbIds.length; i++){
    			chon = chon + pbIds[i] + ",";
    			String tmp = "";
    			String tmp1 = util.antiSQLInspection(request.getParameter("pt" + pbIds[i] ));
    			String tmp2 = util.antiSQLInspection(request.getParameter("ptds" + pbIds[i] ));
    			
    			if(tmp1 != null){ 
    				tmp = tmp1.trim();
    			}else{
    				tmp = tmp2.trim();
    			}
    			if(tmp.length() == 0) tmp = "0";
    		
    			pbIds[i] = pbIds[i] + "-" + tmp;
    		
//    			pt = pt + Float.parseFloat(tmp);
    		}
    		
    		chon = chon.substring(0, chon.length() - 1);
    	
/*    		if(pt > 100){
    			err = true;
    			ttcpBean.setMsg("Tổng số phần trăm không được vượt quá 100%");
    		}else if (pt < 100){
    			err = true;
    			ttcpBean.setMsg("Tổng số phần trăm không được nhỏ hơn 100%");
    		}*/
    	
    		ttcpBean.setPbIds(pbIds);
    		ttcpBean.setChon(chon);
    	}else{
    		err = true;
    		ttcpBean.setMsg("Vui lòng chọn Trung Tâm Chi Phí và nhập phần trăm để phân bổ");
    	}
    }
    
    if(Id.length()>0) {
    	if(err){
			//ttcpBean.init();
       	    
			// Data is saved into session
			session.setAttribute("ttcpBean", ttcpBean);
			session.setAttribute("userId", userId);

			String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_TrungTamChiPhiUpdate.jsp";
			response.sendRedirect(nextJSP);
    		
    	}else{
    		if(action.equals("save")){
    			if(!ttcpBean.Update()){
    				//ttcpBean.init();
    				//ttcpBean.set
    				// Data is saved into session
    				session.setAttribute("ttcpBean", ttcpBean);
    				session.setAttribute("userId", userId);

    				String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_TrungTamChiPhiUpdate.jsp";
    				response.sendRedirect(nextJSP);
    		
    			}else{
    				IErp_trungtamchiphiList obj = (IErp_trungtamchiphiList) new Erp_trungtamchiphiList();
    				obj.setCtyId(ctyId);
    				obj.setUserId(userId);
    				obj.init();
    				session.setAttribute("obj", obj);
    				session.setAttribute("userId", userId);
    			    String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_TrungTamChiPhi.jsp";
    				
    				response.sendRedirect(nextJSP);	    		
    			}
    		}else{
    			session.setAttribute("ttcpBean", ttcpBean);
    			session.setAttribute("userId", userId);

    			String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_TrungTamChiPhiUpdate.jsp";
    			response.sendRedirect(nextJSP);
    			
    		}
    	}
    }else{
    	if(err){
//    		ttcpBean.initNew();
    		session.setAttribute("ttcpBean", ttcpBean);
			session.setAttribute("userId", userId);

			String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_TrungTamChiPhiNew.jsp";
			response.sendRedirect(nextJSP);
    		
    	}else{
    		if(action.equals("save")){
    			if(!ttcpBean.New()){
  //  				ttcpBean.initNew();
    				session.setAttribute("ttcpBean", ttcpBean);
    				session.setAttribute("userId", userId);

    				String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_TrungTamChiPhiNew.jsp";
    				response.sendRedirect(nextJSP);
    		
    			}else{
    				IErp_trungtamchiphiList obj = (IErp_trungtamchiphiList) new Erp_trungtamchiphiList();
    				obj.setCtyId(ctyId);
    				obj.setUserId(userId);
    				obj.init();
    				session.setAttribute("obj", obj);
    				session.setAttribute("userId", userId);
    			    String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_TrungTamChiPhi.jsp";
    				
    				response.sendRedirect(nextJSP);	    		
    			}
    		}    			
    		else{
    			session.setAttribute("ttcpBean", ttcpBean);
    			session.setAttribute("userId", userId);

    			String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_TrungTamChiPhiNew.jsp";
    			response.sendRedirect(nextJSP);
    			
    		}

    	}
    }
    
}

}
