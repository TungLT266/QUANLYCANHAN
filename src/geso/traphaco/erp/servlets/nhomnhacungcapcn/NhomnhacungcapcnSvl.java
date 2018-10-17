package geso.traphaco.erp.servlets.nhomnhacungcapcn;

import geso.traphaco.erp.beans.nhomnhacungcapcn.INhomnhacungcapcn;
import geso.traphaco.erp.beans.nhomnhacungcapcn.INhomnhacungcapcnList;
import geso.traphaco.erp.beans.nhomnhacungcapcn.imp.Nhomnhacungcapcn;
import geso.traphaco.erp.beans.nhomnhacungcapcn.imp.NhomnhacungcapcnList;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class NhomnhacungcapcnSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
	   static final long serialVersionUID = 1L;
	   PrintWriter out;
	   HttpServletRequest request;
	   HttpServletResponse response;
	   INhomnhacungcapcnList obj;
	   dbutils db;

		public NhomnhacungcapcnSvl() {
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
		    obj = new NhomnhacungcapcnList();
		    
		    String chixem = request.getParameter("chixem");
		    System.out.println("chỉ xem" + chixem);
		    obj.setChixem(chixem);
		    
		    String querystring = request.getQueryString();
		    String userId = util.getUserId(querystring);
		    out.println(userId);
		    
		    if (userId.length()==0)
		    	userId = request.getParameter("userId");
		    
		    String action = util.getAction(querystring);
		    out.println(action);
		    
		    String nspId = util.getId(querystring);

		    if (action.equals("delete")){	   		  	    	
		    	Delete(nspId);

		    }
		    
		    List<INhomnhacungcapcn> ncclist = new ArrayList<INhomnhacungcapcn>(); 
		    
		    getNNccBeanList(ncclist,"", session.getAttribute("congtyId").toString() );
		    
			// Save data into session
		    obj.setNccList(ncclist);
			session.setAttribute("obj", obj);
			session.setAttribute("userId", userId);
			
			String nextJSP = "/TraphacoHYERP/pages/Erp/NhomNhaCungCapCN.jsp";
			response.sendRedirect(nextJSP);
		    
		}  	

		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		    request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
		    PrintWriter out = response.getWriter();
		    
			HttpSession session = request.getSession();
		    String userId = request.getParameter("userId");

		    this.obj = new NhomnhacungcapcnList();
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
		    	INhomnhacungcapcn nNccBean = (INhomnhacungcapcn) new Nhomnhacungcapcn();
		    	
		    	nNccBean.UpdateRS();
		    	// Save Data into session
	    		session.setAttribute("nNccBean", nNccBean);
	    		session.setAttribute("userId", userId);
	    		

	    		String nextJSP = "/TraphacoHYERP/pages/Erp/NhomNhaCungCapCNNew.jsp";
	    		response.sendRedirect(nextJSP);
	    		
		    }
		    if (action.equals("search")){
		    	
			    	String search = getSearchQuery(request, obj);
			    	
//			    	out.println(search);
			    	List<INhomnhacungcapcn> ncclist = new ArrayList<INhomnhacungcapcn>(); 
			    	getNNccBeanList(ncclist, search, session.getAttribute("congtyId").toString());	    	

		    		// Saving data into session
				    obj.setNccList(ncclist);
				    obj.setSearch(true);
					session.setAttribute("obj", obj);

		    		session.setAttribute("userId", userId);
			    		
		    		response.sendRedirect("/TraphacoHYERP/pages/Erp/NhomNhaCungCapCN.jsp");
			    }
		    if (action.equals("1")){
		    	List<INhomnhacungcapcn> ncclist = new ArrayList<INhomnhacungcapcn>(); 
		    
		    	getNNccBeanList(ncclist, "", session.getAttribute("congtyId").toString());
		    
			// 	Save data into session
		    	obj.setNccList(ncclist);
		    
		    	session.setAttribute("obj", obj);
		    	session.setAttribute("userId", userId);

		    	String nextJSP = "/TraphacoHYERP/pages/Erp/NhomNhaCungCapCN.jsp";
		    	response.sendRedirect(nextJSP);
		    }
		}

		private void Delete(String nkhId){
		    
			String command;
			
			command = "delete from NHOMNHACUNGCAPCN_NCC where NHOMNHACUNGCAPCN_fk ='" + nkhId + "'";
			db.update(command);

			command = "delete from NHOMNHACUNGCAPCN where pk_seq ='" + nkhId + "'";
		   	db.update(command);
			
		}

		
		private void  getNNccBeanList(List<INhomnhacungcapcn> nkhlist, String search, String congtyId){	
		    String query;
		         
		    if (search.length() > 0){
		    	query = search;
		    }else{
		    	query = " select  a.pk_seq, isnull(a.ten,'')as ten, a.diengiai, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua " + 
		    			" from Nhomnhacungcapcn a, nhanvien b, nhanvien c where a.nguoitao = b.PK_SEQ and a.nguoisua = c.PK_SEQ ";
		    }
		    
		    if(!congtyId.equals("100000"))
		    	query += " and a.congty_fk = '" + congtyId + "' ";
		    
		    query += " order by diengiai ";
		   	ResultSet rs = db.get(query);
		   	try{	
		   		String[] param = new String[11];
	    		INhomnhacungcapcn nkhBean;
	    		while (rs.next()){	    			
					param[0]= rs.getString("pk_seq");
					param[1]= rs.getString("diengiai");				
					param[2]= rs.getString("trangthai");
					param[3]= rs.getString("ngaytao");
					param[4]= rs.getString("ngaysua");
					param[5]= rs.getString("nguoitao");
					param[6]= rs.getString("nguoisua");		
					param[7]= rs.getString("ten");		
					
					nkhBean = new Nhomnhacungcapcn(param);					
					nkhlist.add(nkhBean);
	    		}    		
		   	}catch(java.sql.SQLException e){}
		   	 
			
			
		}
		

		private String getSearchQuery(HttpServletRequest request, INhomnhacungcapcnList obj){
//		    PrintWriter out = response.getWriter();
			geso.traphaco.center.util.Utility util = new geso.traphaco.center.util.Utility();

			String maNcc = util.antiSQLInspection(request.getParameter("maNcc"));
			if(maNcc == null)
				maNcc = "";
			
			String diengiai = util.antiSQLInspection(request.getParameter("ten"));
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
	    	String query = " select distinct a.pk_seq,isnull(a.ten,'') as ten, a.diengiai, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, "+
					    	" c.ten as nguoisua "+
					    	" from Nhomnhacungcapcn a, NHOMNHACUNGCAPCN_NCC n, ERP_NHACUNGCAP ncc, nhanvien b, nhanvien c "+
					    	" where a.nguoitao = b.PK_SEQ "+
					    	" and a.nguoisua = c.PK_SEQ  "+
					    	" and a.pk_seq = n.NHOMNHACUNGCAPCN_FK "+
					    	" and n.NCC_FK = ncc.pk_seq ";
			    	
	    	if(maNcc.length() > 0){
	    		query = query + " and upper(ncc.pk_seq) like upper('%" + maNcc + "%')";
	    		obj.setMaNcc(maNcc);
	    	}
	    	if (diengiai.length() > 0){
				query = query + " and upper(a.ten) like upper(N'%" + diengiai + "%')";
				obj.setTen(diengiai);
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
