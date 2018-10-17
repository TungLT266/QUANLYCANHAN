package geso.traphaco.erp.servlets.lapngansach;

import geso.traphaco.erp.beans.lapngansach.ILNSBanggianguyenlieu;
import geso.traphaco.erp.beans.lapngansach.ILNSBanggianguyenlieuList;

import geso.traphaco.erp.beans.lapngansach.imp.LNSBanggianguyenlieu;
import geso.traphaco.erp.beans.lapngansach.imp.LNSBanggianguyenlieuList;

import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

 public class ErpLNSBanggianguyenlieuSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
   public ErpLNSBanggianguyenlieuSvl() {
		super();
	}   	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    System.out.println("User  : "+userId);
	    
	    if (userId.length()==0)
	    	userId = (String) session.getAttribute("userId");
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	    String bgId = util.getId(querystring);

	    String ctyId = (String)session.getAttribute("congtyId");
	    String ctyTen = (String)session.getAttribute("congtyTen");
	    
	    if (action.equals("delete")){	   		  	    	
	    	Delete(bgId);
	    	out.print(bgId);
	    }
	    if (action.equals("assign")){
	

	    }else
		    if (action.equals("approve")){
		    	ILNSBanggianguyenlieu bgBean = (ILNSBanggianguyenlieu) new LNSBanggianguyenlieu();
		    	bgBean.setUserId(userId);
		    	bgBean.setId(bgId);
		    	//bgBean.Chot();
		    	bgBean.closeDB();

		    	ILNSBanggianguyenlieuList obj = new LNSBanggianguyenlieuList();
		    	obj.setUserId(userId);
		    	session.setAttribute("obj", obj);
					
		    	String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_LNSBangGiaNguyenLieu.jsp";
		    	response.sendRedirect(nextJSP);
		    	
		    }
		else{
	    	ILNSBanggianguyenlieuList obj = new LNSBanggianguyenlieuList();
	    	obj.setCtyId(ctyId);
	    	obj.setCtyTen(ctyTen);
	    	obj.setUserId(userId);
	    	obj.init("");
	    	
	    	session.setAttribute("obj", obj);
				
	    	String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_LNSBangGiaNguyenLieu.jsp";
	    	response.sendRedirect(nextJSP);
	    }

	}  	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    
	    ILNSBanggianguyenlieuList obj = new LNSBanggianguyenlieuList();		
		HttpSession session = request.getSession();
	    String userId = request.getParameter("userId");
	    
	    String ctyId = (String)session.getAttribute("congtyId");
	    obj.setCtyId(ctyId);
	    
	    String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    out.println(action);         
	    
	    if (action.equals("new"))
	    {
	    	ILNSBanggianguyenlieu bgBean = (ILNSBanggianguyenlieu) new LNSBanggianguyenlieu();
	    	bgBean.setCtyId(ctyId);
	    	bgBean.setUserId(userId);
	    	
	    	bgBean.init();
	    	session.setAttribute("bgnlBean", bgBean);
    		
    		String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_LNSBangGiaNguyenLieuNew.jsp";
    		response.sendRedirect(nextJSP);
    		
	    }
	    if (action.equals("search")){
	    	String search = getSearchQuery(request, obj);   	
	    	
	    	obj.init(search);
			
	    	obj.setUserId(userId);
			
	    	session.setAttribute("obj", obj);
	    		
    		response.sendRedirect("/TraphacoHYERP/pages/Erp/Erp_LNSBangGiaNguyenLieu.jsp");	    	
	    	
	    }
	    
	}   
	
	private String getSearchQuery(HttpServletRequest request, ILNSBanggianguyenlieuList obj ){
//	    PrintWriter out = response.getWriter();
		
		String bgTen = request.getParameter("bgTen");
    	if (bgTen == null)
    		bgTen = "";
    	obj.setTen(bgTen);
    	
    	String trangthai = request.getParameter("trangthai");
    	if (trangthai == null)
    		trangthai = "";
    	obj.setTrangthai(trangthai);
    	
		String query = 	"select a.pk_seq as id, a.ten as ten, a.trangthai as trangthai, a.tinhtrang, d.ten as nguoitao, a.ngaytao as ngaytao, " +
						"e.ten as nguoisua, a.ngaysua as ngaysua " +
						"from ERP_LNSBANGGIANGUYENLIEU a, nhanvien d, nhanvien e " +
						"where a.nguoitao = d.pk_seq and a.nguoisua = e.pk_seq " +
						"and a.congty_fk = " + obj.getCtyId() + " ";
    	
    	if (bgTen.length()>0){
    		
			query = query + " and a.ten like upper(N'%"+ bgTen + "%')";
			
    	}
    	
    	if(trangthai.length() > 0){
    		query = query + " and a.trangthai = " + trangthai + "";
    	}
    	
    	query = query + "  order by ten";
    	return query;
	}	
	
	private void Delete(String id){
		dbutils db = new dbutils();
		
		db.update("delete from ERP_LNSBGNGUYENLIEU_NGUYENLIEU where bgnguyenlieu_fk='" + id + "'");
		db.update("delete from ERP_LNSBANGGIANGUYENLIEU where pk_seq = '" + id + "'");
		db.shutDown();
		
	}
	
}