package geso.traphaco.erp.servlets.chinhsachduyetmuahang;
import geso.traphaco.erp.beans.chinhsachduyetmuahang.IChinhsachduyetmuahang;
import geso.traphaco.erp.beans.chinhsachduyetmuahang.IChinhsachduyetmuahangList;
import geso.traphaco.erp.beans.chinhsachduyetmuahang.imp.Chinhsachduyetmuahang;
import geso.traphaco.erp.beans.chinhsachduyetmuahang.imp.ChinhsachduyetmuahangList;
import geso.traphaco.center.util.Utility;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import geso.traphaco.center.db.sql.dbutils;

public class Erp_ChinhsachduyetmuahangSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Erp_ChinhsachduyetmuahangSvl() {
        super();
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    Utility util=new Utility();
	    HttpSession session = request.getSession();
	    String ctyId = (String)session.getAttribute("congtyId");
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    String Id = util.getId(querystring);
	    String action = util.getAction(querystring);
	    
	    if(action.equals("delete")){
	    	dbutils db = new dbutils();
	    	try{
	    		db.getConnection().setAutoCommit(false);
		    	String query = "DELETE ERP_CHINHSACHDUYET_CHIPHI WHERE CHINHSACHDUYET_FK = " + Id + " ";	    	
		    	System.out.println(query);	    	
		    	db.update(query);
		    		    	
		    	query = "DELETE ERP_CHINHSACHDUYET_NCC WHERE CHINHSACHDUYET_FK = " + Id + " ";	    	
		    	System.out.println(query);	    	
		    	db.update(query);
	
		    	query = "DELETE ERP_CHINHSACHDUYET_SANPHAM WHERE CHINHSACHDUYET_FK = " + Id + " ";	    	
		    	System.out.println(query);	    	
		    	db.update(query);
	
		    	query =	"DELETE ERP_CHINHSACHDUYET WHERE PK_SEQ = " + Id + " ";
		    	System.out.println(query);	    
		    	db.update(query);
		    	
		    	db.getConnection().commit();
		    	db.getConnection().setAutoCommit(true);
		    	
	    	} catch(Exception ex){
	    		ex.printStackTrace();
	    	}

	    	db.shutDown();
	    }
	    
	    if (userId.length()==0){
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    }
	    	
   	    IChinhsachduyetmuahangList csBean = new ChinhsachduyetmuahangList();
   	    csBean.SetUserId(userId);
   	    csBean.setCtyId(ctyId);
   	    
   	    String chixem = request.getParameter("chixem");
   	    csBean.setChixem(chixem);
   	    
   	    csBean.init();
   	    
		// Data is saved into session
		session.setAttribute("csBean", csBean);
		String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_ChinhSachDuyetMuaHang.jsp";
   		response.sendRedirect(nextJSP);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");

	    Utility util = new Utility();
	    
	    HttpSession session = request.getSession();
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    String dvthId = util.antiSQLInspection(request.getParameter("dvthId"));
	    if(dvthId == null) dvthId = "";
	    
	    String action = request.getParameter("action"); 
	
	    String userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    System.out.println("DvthId:" + dvthId);

	    if(action.equals("new")){
	   	    IChinhsachduyetmuahang csBean = new Chinhsachduyetmuahang();
	   	    csBean.SetUserId(userId);
	   	    csBean.setCtyId(ctyId);
	   	    csBean.init_new();
	   	    
			// Data is saved into session
			session.setAttribute("csBean", csBean);
			String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_ChinhSachDuyetMuaHangNew.jsp";
	   		response.sendRedirect(nextJSP);
	   		return;
	    }else{
	    	
	    	IChinhsachduyetmuahangList csBean = new ChinhsachduyetmuahangList();
	    	csBean.SetUserId(userId);
	    	csBean.setCtyId(ctyId);
	    	csBean.setDvthId(dvthId);
   	    
	    	String chixem = request.getParameter("chixem");
	   	    csBean.setChixem(chixem);
	   	    
	    	csBean.init();
		
	    	session.setAttribute("csBean", csBean);

	    	String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_ChinhSachDuyetMuaHang.jsp";
	    	response.sendRedirect(nextJSP);
	    	return;
	    }
	}

}
