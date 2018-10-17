package geso.traphaco.erp.servlets.chucdanh;

import geso.traphaco.erp.beans.chucdanh.IChucdanh;
import geso.traphaco.erp.beans.chucdanh.imp.Chucdanh;
import geso.traphaco.erp.beans.chucdanh.IChucdanhList;
import geso.traphaco.erp.beans.chucdanh.imp.ChucdanhList;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Erp_ChucdanhUpdateSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Erp_ChucdanhUpdateSvl() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");

	    Utility util = new Utility();
	    HttpSession session = request.getSession();

	    String querystring = request.getQueryString();
	    
	    String userId = util.getUserId(querystring);
	    System.out.println("UserId: " + userId);
	    
	    if (userId.length()==0)
	    	userId = request.getParameter("userId");
	    
	    String action = util.getAction(querystring);		
	    String Id = util.getId(querystring);

   	    IChucdanh cdBean = new Chucdanh();
   	    cdBean.setId(Id);
   	    cdBean.init();
   	    cdBean.creaters();
		// Data is saved into session
		session.setAttribute("cdBean", cdBean);
		session.setAttribute("userId", userId);

		String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_ChucdanhUpdate.jsp";
		if(querystring.contains("display"))
			nextJSP = "/TraphacoHYERP/pages/Erp/Erp_ChucdanhDisplay.jsp";
   		response.sendRedirect(nextJSP);


	}  	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();

		Utility util = new Utility();
		
		IChucdanh cdBean = new Chucdanh();	
		
    	String Id = util.antiSQLInspection(request.getParameter("cdId"));
    	cdBean.setId(Id);

		String chucdanh = util.antiSQLInspection(request.getParameter("chucdanh"));
		cdBean.setChucdanh(chucdanh);

    	String ctyId = util.antiSQLInspection(request.getParameter("ctyId"));
    	cdBean.setCtyId(ctyId);

    	String nvId = util.antiSQLInspection(request.getParameter("nvId"));
    	cdBean.setNvId(nvId);

		String userId = util.antiSQLInspection(request.getParameter("userId"));
		cdBean.setUserId(userId);
		
		String pbId = util.antiSQLInspection(request.getParameter("pbId"));
		if(pbId == null) pbId = "";
    	cdBean.setDvthId(pbId);

		String action = util.antiSQLInspection(request.getParameter("action"));
	 
    	String trangthai;
    	if(request.getParameter("trangthai")!= null)
			trangthai = "1";
		else
			trangthai = "0";
    	cdBean.setTrangthai(trangthai);
    	
    	String isKTT = util.antiSQLInspection(request.getParameter("isKTT"));
    	if(request.getParameter("isKTT")!= null)
    		isKTT = "1";
		else
			isKTT = "0";
		cdBean.setisKTT(isKTT);
		
		String isKTV = util.antiSQLInspection(request.getParameter("isKTV"));
    	if(request.getParameter("isKTV")!= null)
    		isKTV = "1";
		else
			isKTV = "0";
		cdBean.setisKTV(isKTV);
		
		String isTP = util.antiSQLInspection(request.getParameter("isTP"));
    	if(request.getParameter("isTP")!= null)
    		isTP = "1";
		else
			isTP = "0";
		cdBean.setisTP(isTP);
    	
    	String loai = util.antiSQLInspection(request.getParameter("loai"));
		if(loai == null) loai = "";
    	cdBean.setLoai(loai);
    	
    	String duyetdtvt = util.antiSQLInspection(request.getParameter("duyetdtvt"));
		if(duyetdtvt == null) 
			duyetdtvt = "";
    	cdBean.setDuyetDtvt(duyetdtvt);
		
		boolean error = false;
		if (chucdanh.trim().length()> 0)
			cdBean.setChucdanh(chucdanh);
		else{
			cdBean.setMessage("Vui lòng nhập chức danh");
			error = true;
		}
		
		String[] mangnv=request.getParameterValues("Idnvchon");
		String chuoi="";
		if(mangnv!=null){
			for(int i=0;i<mangnv.length;i++){
				if(chuoi.trim().length() > 0)
					chuoi=chuoi+","+mangnv[i];
				else
					chuoi=mangnv[i];
			}
		}
		cdBean.setNvQuanly(chuoi);
		
 	    cdBean.creaters();
 	    
		if (error){ //Error in data entry
			session.setAttribute("userId", userId);
			session.setAttribute("cdBean", cdBean);   		
    		String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_ChucdanhUpdate.jsp";
    		response.sendRedirect(nextJSP);
		}
		else{
			// userId is saved into session
			session.setAttribute("userId", userId);
			if(action.equals("save")){
			//if there is any error when saving Business Unit
			if(Id.length() == 0){
				if (!cdBean.saveNewChucdanh()){
					session.setAttribute("cdBean", cdBean);
					String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_ChucdanhNew.jsp";
					response.sendRedirect(nextJSP);
				}
				else{				
					IChucdanhList obj = new ChucdanhList(ctyId);
					session.setAttribute("obj", obj);
					session.setAttribute("userId", userId);
					
					String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_Chucdanh.jsp";
					response.sendRedirect(nextJSP);
				}
				
			}else{
				if (!cdBean.UpdateChucdanh()){
					session.setAttribute("cdBean", cdBean);
					String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_ChucdanhUpdate.jsp";
					response.sendRedirect(nextJSP);
				}
				else{				
					IChucdanhList obj = new ChucdanhList(ctyId);
					session.setAttribute("obj", obj);
					session.setAttribute("userId", userId);
					
					String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_Chucdanh.jsp";
					response.sendRedirect(nextJSP);
				}
			}
			}else{
				session.setAttribute("userId", userId);
				session.setAttribute("cdBean", cdBean);   		
	    		String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_ChucdanhUpdate.jsp";
	    		response.sendRedirect(nextJSP);
			}
			
		}
		
	}   	  	 
	private ResultSet getCdBeanList(String query){  	
		dbutils db = new dbutils();
		ResultSet rs =  db.get(query);
		return rs;
	}

}
