package geso.traphaco.erp.servlets.NhomDoiTuongKhac;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.NhomDTKhac.IErpNhomDTKhac;
import geso.traphaco.erp.beans.NhomDTKhac.IErpNhomDTKhacList;
import geso.traphaco.erp.beans.NhomDTKhac.imp.ErpNhomDTKhac;
import geso.traphaco.erp.beans.NhomDTKhac.imp.ErpNhomDTKhacList;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 * Servlet implementation class ErpNhomDTKhacSvl
 */
@WebServlet("/ErpNhomDTKhacSvl")
public class ErpNhomDTKhacSvl extends HttpServlet {
	   static final long serialVersionUID = 1L;
	   PrintWriter out;
	   HttpServletRequest request;
	   HttpServletResponse response;
	   dbutils db;

		public ErpNhomDTKhacSvl() {
			super();
		}   	
		
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		    
		    this.request = request;
		    this.response = response;
		    this.db = new dbutils();
		    
		    response.setContentType("text/html");
		    
		    HttpSession session = request.getSession();	    

		    Utility util = new Utility();
		    out = response.getWriter();

			IErpNhomDTKhacList obj;
		    obj = new ErpNhomDTKhacList();
		    
		    String chixem = request.getParameter("chixem");
		    System.out.println("chỉ xem" + chixem);
		    obj.setChixem(chixem);
		    
		    String querystring = request.getQueryString();
		    String userId = util.getUserId(querystring);
		    System.out.println(userId);
		    
		    if (userId.length()==0)
		    	userId = request.getParameter("userId");
		    
		    String action = util.getAction(querystring);
		    out.println(action);
		    
		    String nspId = util.getId(querystring);

		    if (action.equals("delete")){	   		  	    	
		    	Delete(nspId);

		    }
		    
		    List<IErpNhomDTKhac> ncclist = new ArrayList<IErpNhomDTKhac>(); 
		    
		    getNNccBeanList(ncclist,"", session.getAttribute("congtyId").toString() );
		    
			// Save data into session
		    obj.setNccList(ncclist);
			session.setAttribute("obj", obj);
			session.setAttribute("userId", userId);
			
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhomDTKhac.jsp";
			response.sendRedirect(nextJSP);
		    
		}  	

		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		    request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
		    PrintWriter out = response.getWriter();
		    
			HttpSession session = request.getSession();
		    String userId = request.getParameter("userId");

		    IErpNhomDTKhacList obj;
		    obj = new ErpNhomDTKhacList();
		    this.db = new dbutils();
		    
		    String chixem = request.getParameter("chixem");
		    obj.setChixem(chixem);
		    
		    String action = request.getParameter("action");
		    if (action == null){
		    	action = "";
		    }
		    out.println(action); 
		    
		    // Perform searching. Each Nhomnhacungcapcn is saved into Nhomnhacungcapcn
		    if (action.equals("new")){
		    	// Empty Bean for distributor
		    	IErpNhomDTKhac nNccBean = (IErpNhomDTKhac) new ErpNhomDTKhac();
		    	
		    	nNccBean.setCtyId(session.getAttribute("congtyId").toString());
		    	
		    	nNccBean.UpdateRS();
		    	// Save Data into session
	    		session.setAttribute("nNccBean", nNccBean);
	    		session.setAttribute("userId", userId);
	    		

	    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhomDTKhacNew.jsp";
	    		response.sendRedirect(nextJSP);
	    		
		    }
		    if (action.equals("search")){
		    	
			    	String search = getSearchQuery(request, obj);
			    	
//			    	out.println(search);
			    	List<IErpNhomDTKhac> ncclist = new ArrayList<IErpNhomDTKhac>(); 
			    	getNNccBeanList(ncclist, search, session.getAttribute("congtyId").toString());	    	

		    		// Saving data into session
				    obj.setNccList(ncclist);
				    obj.setSearch(true);
					session.setAttribute("obj", obj);

		    		session.setAttribute("userId", userId);
			    		
		    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpNhomDTKhac.jsp");
			    }
		    if (action.equals("1")){
		    	List<IErpNhomDTKhac> ncclist = new ArrayList<IErpNhomDTKhac>(); 
		    
		    	getNNccBeanList(ncclist, "", session.getAttribute("congtyId").toString());
		    
			// 	Save data into session
		    	obj.setNccList(ncclist);
		    
		    	session.setAttribute("obj", obj);
		    	session.setAttribute("userId", userId);

		    	String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhomDTKhac.jsp";
		    	response.sendRedirect(nextJSP);
		    }
		}

		private void Delete(String nkhId){
		    
			String command;
			
			command = "delete from ErpNhomDTKhac_DT where ErpNhomDTKhac_fk ='" + nkhId + "'";
			db.update(command);

			command = "delete from ErpNhomDTKhac where pk_seq ='" + nkhId + "'";
		   	db.update(command);
			
		}

		
		private void  getNNccBeanList(List<IErpNhomDTKhac> nkhlist, String search, String congtyId){	
		    String query;
		         
		    if (search.length() > 0){
		    	query = search;
		    }else{
		    	query = " select a.pk_seq, isnull(a.ten,'') as ten, a.diengiai, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua " + 
		    			" from ErpNhomDTKhac a, nhanvien b, nhanvien c where a.nguoitao = b.PK_SEQ and a.nguoisua = c.PK_SEQ ";
		    }
		    
		    if(!congtyId.equals("100000"))
		    	query += " and a.congty_fk = '" + congtyId + "' ";
		    
		    query += " order by diengiai ";
		   	ResultSet rs = db.get(query);
		   	try{	
		   		String[] param = new String[11];
		   		IErpNhomDTKhac nkhBean;
	    		while (rs.next()){	    			
					param[0]= rs.getString("pk_seq");
					param[1]= rs.getString("diengiai");				
					param[2]= rs.getString("trangthai");
					param[3]= rs.getString("ngaytao");
					param[4]= rs.getString("ngaysua");
					param[5]= rs.getString("nguoitao");
					param[6]= rs.getString("nguoisua");			
					param[7]= rs.getString("ten");			
					nkhBean = new ErpNhomDTKhac(param);					
					nkhlist.add(nkhBean);
	    		}    		
		   	}catch(java.sql.SQLException e){}
		   	 
			
			
		}
		

		private String getSearchQuery(HttpServletRequest request, IErpNhomDTKhacList obj){
//		    PrintWriter out = response.getWriter();
			geso.traphaco.center.util.Utility util = new geso.traphaco.center.util.Utility();

			
			String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
	    	if (diengiai == null)
	    		diengiai = "";
	  			 
	    	String tungay = util.antiSQLInspection(request.getParameter("tungay"));
	    	if (tungay == null)
	    		tungay = "";    	
	    	
	    	String denngay = util.antiSQLInspection(request.getParameter("denngay"));
	    	if (denngay == null)
	    		denngay = "";    	

	    	String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));   	
	    	if (trangthai == null)
	    		trangthai = "";    	
		
	    	if (trangthai.equals("2"))
	    		trangthai = "";

	    	//String query = "select a.pk_seq, a.diengiai, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua from Nhomnhacungcapcn a, nhanvien b, nhanvien c where a.nguoitao = b.PK_SEQ and a.nguoisua = c.PK_SEQ ";
	    	String query = " select a.pk_seq, isnull(a.ten,'') as ten, a.diengiai, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua " + 
			" from ErpNhomDTKhac a, nhanvien b, nhanvien c where a.nguoitao = b.PK_SEQ and a.nguoisua = c.PK_SEQ ";
			
	    	if (diengiai.length() > 0){
				query = query + " and upper(a.ten) like upper (N'%" + diengiai + "%')";
				obj.setDiengiai(diengiai);
	    	}
	    	
	    	if (tungay.length() > 0) {
	    		query = query + " and a.ngaytao >= '" + tungay + "'";
	    		obj.setTungay(tungay);
	    	}
	    	
	    	if (denngay.length() > 0) {
	    		query = query + " and a.ngaytao <= '" + denngay + "'";
	    		obj.setDenngay(denngay);
	    	}
	    	
	    	if(trangthai.length() > 0){
	    		query = query + " and a.trangthai = '" + trangthai + "'";
	    		obj.setTrangthai(trangthai);
	    	}
	    	//query = query + "  order by a.diengiai";
	    	System.out.println("Tim kiem ,,,,,,,,,,,: "+ query);
	    	return query;
		}
		


}
