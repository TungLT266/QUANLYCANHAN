package geso.traphaco.erp.servlets.chucdanh;

import geso.traphaco.erp.beans.chucdanh.IChucdanhList;
import geso.traphaco.erp.beans.chucdanh.imp.ChucdanhList;
import geso.traphaco.erp.beans.chucdanh.IChucdanh;
import geso.traphaco.erp.beans.chucdanh.imp.Chucdanh;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class Erp_ChucdanhSvl extends HttpServlet {
	   static final long serialVersionUID = 1L;
	   
	   public Erp_ChucdanhSvl() {
			super();
		}   	
		
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		    request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
		    
		    PrintWriter out = response.getWriter();
		    HttpSession session = request.getSession();		    
		    	
		    String querystring = request.getQueryString();
		    Utility util = new Utility();
		    
		    String userId = util.getUserId(querystring);
		    out.println(userId);
		    
		    if (userId.length()==0)
		    	userId = util.antiSQLInspection(request.getParameter("userId"));
		    
		    String action = util.getAction(querystring);
		    out.println(action);
		    	    
		    String Id = util.getId(querystring);
		    
		    if(Id.contains(";")){
		    	Id = Id.split(";")[1];
		    }
		    		  
		    out.println(Id);

		    if (action.equals("delete")){	   		
			 	Delete(Id);
		    }
		    
		    String ctyId = (String)session.getAttribute("congtyId");

		    IChucdanhList obj = (IChucdanhList) new ChucdanhList(ctyId);

		    session.setAttribute("obj", obj);
		    session.setAttribute("userId", userId);
				
		    String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_Chucdanh.jsp";
		    response.sendRedirect(nextJSP);
			
		}  	

		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		    request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
		    Utility util = new Utility();
		    
			PrintWriter out = response.getWriter();
		    HttpSession session = request.getSession();

		    String ctyId = (String)session.getAttribute("congtyId");
		    
		    IChucdanhList obj = (IChucdanhList) new ChucdanhList(ctyId);
		    String userId = util.antiSQLInspection(request.getParameter("userId"));
		    
		    // Perform searching. Each Business Unit is saved into Donvikinhdoanh
		    if (request.getParameter("action").equals("search")){
		    	String search = getSearchQuery(obj, request, ctyId);
		    	out.println(search);

		    	ResultSet cdlist = getCdBeanList(search);	    	

	    		// Saving data into session
	    		obj.setCdlist(cdlist);
		    	session.setAttribute("obj", obj);
	    		session.setAttribute("userId", userId);
	    		
	    		response.sendRedirect("/TraphacoHYERP/pages/Erp/Erp_Chucdanh.jsp");
		    }
		    
		    // Create a new Business Unit
		    if (request.getParameter("action").equals("new")){

		    	IChucdanh cdBean = new Chucdanh();
		    	cdBean.setCtyId(ctyId);
		    	// Save Data into session
	    		session.setAttribute("cdBean", cdBean);
	    		session.setAttribute("userId", userId);
	    		cdBean.creaters();
		    	String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_ChucdanhNew.jsp";
	    		response.sendRedirect(nextJSP);
		    
		    }

		}

		private void Delete(String id){
			dbutils db = new dbutils();
						
		    String sql = " UPDATE   ERP_CHUCDANH SET TRANGTHAI=0 WHERE PK_SEQ= '" + id + "'";
		    if(!db.update(sql)){
//		    	obj.setMsg("Chức danh này đã tham gia trong chính sách duyệt PO rồi, nên không xóa được");
		    }
		    
		    System.out.println(sql);
		    
		    db.shutDown();		    	
		}
		    
		
		private ResultSet getCdBeanList(String query){  	
			dbutils db = new dbutils();
			ResultSet rs =  db.get(query);
			return rs;
		}

		private String getSearchQuery(IChucdanhList obj, HttpServletRequest request, String ctyId){
//		    PrintWriter out = response.getWriter();
			Utility util = new Utility();
			String chucdanh = util.antiSQLInspection(request.getParameter("chucdanh"));
	    	if (chucdanh == null)
	    		chucdanh = "";
	    	obj.setChucdanh(chucdanh);
	    		    	   	
	    	String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));   	
	    	if (trangthai == null)
	    		trangthai = "";    	
	    	
	    	obj.setTrangthai(trangthai);
	    	
	    	if (trangthai.equals("2"))
	    		trangthai = "";    	
	    	
	    	String query = 	"SELECT	CONGTY.PK_SEQ AS CTYID, CONGTY.TEN AS CTYTEN,	\n" + 
	    					"CHUCDANH.PK_SEQ AS CDID, CHUCDANH.DIENGIAI AS CHUCDANH, \n" +
	    					"NHANVIEN.PK_SEQ AS NVID, NHANVIEN.TEN, CHUCDANH.TRANGTHAI, CONGTY.TEN TENCT \n" +
	    					"FROM ERP_CHUCDANH CHUCDANH \n" +
	    					"INNER JOIN NHANVIEN NHANVIEN ON CHUCDANH.NHANVIEN_FK = NHANVIEN.PK_SEQ \n" + 
	    					"INNER JOIN ERP_CONGTY CONGTY ON CONGTY.PK_SEQ = CHUCDANH.CONGTY_FK WHERE 1 = 1 ";
	    	
	    	if (chucdanh.length()>0){
				query = query + " AND dbo.ftBoDau(UPPER(CHUCDANH.DIENGIAI)) LIKE UPPER(N'%" + util.replaceAEIOU(chucdanh) + "%')";
	    	}
	    	    	
	    	if(trangthai.length() > 0){
	    		query = query + " AND CHUCDANH.TRANGTHAI = '" + trangthai + "'";
	    	}
	    	System.out.print(query);
			return query;
		}		
}
