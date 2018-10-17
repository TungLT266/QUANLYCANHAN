package geso.traphaco.center.servlets.donvikinhdoanh;
import geso.traphaco.center.beans.donvikinhdoanh.IDonvikinhdoanh;
import geso.traphaco.center.beans.donvikinhdoanh.imp.Donvikinhdoanh;
import geso.traphaco.center.beans.donvikinhdoanh.IDonvikinhdoanhList;
import geso.traphaco.center.beans.donvikinhdoanh.imp.DonvikinhdoanhList;
import geso.traphaco.center.db.sql.dbutils;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import geso.traphaco.center.util.Utility;

 public class DonvikinhdoanhSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
   public DonvikinhdoanhSvl() {
		super();
	}   	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	    Utility util = new Utility();
	    HttpSession session;   
	    IDonvikinhdoanhList obj;
	    String userId;
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    session = request.getSession();
	    PrintWriter out = response.getWriter();   
	    obj = new DonvikinhdoanhList();
	    
	    String querystring = request.getQueryString();
	    userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	    String dvkdId = util.getId(querystring);
	    out.println(dvkdId);
	    
	    
	    //Is a Don Vi Kinh Doanh deleted?, muon delete don vi kinh doanh phai xoa ma DVKD trong san pham,nhan hang va khoa lien ket den nhacc_donvikd_kenh, nhapp_nhacc_dvkd
	 
	    
	    
	    if (action.equals("delete")){	   		
		   //kiem tra ton tai trong san pham
	    	   xoa_dvkd(dvkdId);
	    	//Delete(dvkdId);
	    }
	    
	    if (action.equals("update")){
	    	
    	    IDonvikinhdoanh dvkdBean = new Donvikinhdoanh();    	   
    	    dvkdBean.setId(dvkdId);
    	    dvkdBean.init();
    	    // Data is saved into session
			session.setAttribute("dvkdBean", dvkdBean);
			session.setAttribute("userId", userId);

			String nextJSP = "/TraphacoHYERP/pages/Center/DonViKinhDoanhUpdate.jsp";
       		response.sendRedirect(nextJSP);

	    }
	    else{
	    	// Collect all of Business Unit, each Business Unit is saved into Bean Donvikinhdoanh
	    	
	    	session.setAttribute("obj", obj);
	    	session.setAttribute("userId", userId);	    	
	    	String nextJSP = "/TraphacoHYERP/pages/Center/DonViKinhDoanh.jsp";
	    	response.sendRedirect(nextJSP);
	    }  
	}
	
	void xoa_dvkd(String dvkdId)
	{  
		IDonvikinhdoanhList obj = new DonvikinhdoanhList();
		String sql ="select count(dvkd_fk) as num from sanpham where dvkd_fk='"+ dvkdId +"'";
		if(kiemtra(sql))
		{
			sql ="select count(pk_seq) as num from nhanhang where dvkd_fk='"+ dvkdId +"'";
			if(kiemtra(sql))
				{
					sql = " select count(ncc_dvkd_fk) as num from nhapp_nhacc_donvikd where ncc_dvkd_fk " +
						  " in (select pk_seq from nhacungcap_dvkd where dvkd_fk ='"+ dvkdId +"')";
					if(kiemtra(sql))
					{
						Delete(dvkdId);
					}
					else
						obj.setMsg("Don vi kinh doanh da lien ket voi nha phan phoi roi, nen khong xoa duoc");	
				}
				else
					obj.setMsg("Don vi kinh doanh da ton tai trong nhan hang roi nen khong xoa duoc");
		  }
		else
			obj.setMsg("Don vi kinh doanh da ton tai trong san pham roi nen khong xoa duoc");
		
		System.out.println(obj.getMsg());
	}
	boolean kiemtra(String sql)
	{
		dbutils db  = new dbutils();
		ResultSet rs = db.get(sql);
		
		try {//kiem tra ben san pham
		while(rs.next())
		{ if(rs.getString("num").equals("0"))
		   return true;
		}
		} catch(Exception e) {
		
			e.printStackTrace();
		}
		 return false;
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    	   
	   Utility util = new Utility();
	   HttpSession session;   
	   IDonvikinhdoanhList obj = new DonvikinhdoanhList();
	   String userId;
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    session = request.getSession();
	    userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    // Perform searching. Each Business Unit is saved into Donvikinhdoanh
	    if (request.getParameter("action").equals("search")){
	    	String search = getSearchQuery(request,obj);
	    	obj.setQuery(search);
    		
    		// Saving data into session
    		session.setAttribute("obj", obj);
    		session.setAttribute("userId", userId);
	    		
    		response.sendRedirect("/TraphacoHYERP/pages/Center/DonViKinhDoanh.jsp");
	    }
	    
	    // Create a new Business Unit
	    if (request.getParameter("action").equals("new")){
	    	// Empty Bean for distributor
	    	IDonvikinhdoanh dvkdBean = new Donvikinhdoanh();
	    	// Save Data into session
    		session.setAttribute("dvkdBean", dvkdBean);
    		session.setAttribute("userId", userId);
    		
	    	String nextJSP = "/TraphacoHYERP/pages/Center/DonViKinhDoanhNew.jsp";
    		response.sendRedirect(nextJSP);
	    
	    }
		
	}   
	
	private void Delete(String dvkdId)
	{
		dbutils db  = new dbutils();
		String command = "delete from nhacungcap_dvkd where dvkd_fk ='" + dvkdId + "'";
		db.update(command);	
		command = "delete from donvikinhdoanh where pk_seq ='" + dvkdId + "'";	
		db.update(command);	
	}
		
	private String getSearchQuery(ServletRequest request, IDonvikinhdoanhList obj)
	{
	    Utility util = new Utility();
		String dvkd = util.antiSQLInspection(request.getParameter("dvkd"));    	    	
		if (dvkd == null)
    		dvkd = "";
    	obj.setDvkd(dvkd);
    	
    	String nccId = util.antiSQLInspection(request.getParameter("nccId"));
    	if (nccId == null)
    		nccId = "";    	
    	obj.setNccId(nccId);
    	
    	String tungay = util.antiSQLInspection(request.getParameter("tungay"));
    	if (tungay == null)
    		tungay = "";    	
    	obj.setTungay(tungay);
    	
    	String denngay = util.antiSQLInspection(request.getParameter("denngay"));
    	if (denngay == null)
    		denngay = "";    	
    	obj.setDenngay(denngay);
    	
    	String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));   	
    	if (trangthai == null)
    		trangthai = "";    	
	
    	if (trangthai.equals("2"))
    		trangthai = "";
    	obj.setTrangthai(trangthai);
    	
    	String query = " select a.pk_seq, a.donvikinhdoanh, d.ten as nhacungcap, a.trangthai, a.ngaytao, " +
    				   " a.ngaysua, b.ten as nguoitao, c.ten as nguoisua, d.pk_seq as nccId, a.diengiai " +
    				   " from donvikinhdoanh a, nhanvien b, nhanvien c, nhacungcap d, nhacungcap_dvkd e " +
    				   " where a.PK_SEQ = e.DVKD_FK and d.PK_SEQ = e.NCC_FK and a.nguoitao = b.PK_SEQ " +
    				   " and a.nguoisua = c.PK_SEQ and checked='1' ";
    	
    	if (dvkd.length()>0){
    		obj.setDvkd(dvkd);
			query = query + " and upper(a.donvikinhdoanh) like upper(N'%" + util.replaceAEIOU(dvkd) + "%')";
    	}
    	    	
    	if (!nccId.equals("0")){
    		obj.setNccId(nccId);
    		query = query + " and d.pk_seq = '" + nccId + "'";	    		
    	}
    	
    	if (tungay.length() > 0) {
    		obj.setTungay(tungay);
    		query = query + " and a.ngaytao >= '" + tungay + "'";
    	}
    	
    	if (denngay.length() > 0) {
    		obj.setDenngay(denngay);
    		query = query + " and a.ngaytao < '" + denngay + "'";
    	}
    	
    	if(trangthai.length() > 0){
    		obj.setTrangthai(trangthai);
    		query = query + " and a.trangthai = '" + trangthai + "'";
    	}
    	System.out.println(query);
		return query;
	}
	
	
	/*private ResultSet getNccListByDvkd(String dvkdId){
	
	   // HttpServletRequest request;
	   // HttpServletResponse response;
	   HttpSession session;   
	   IDonvikinhdoanhList obj = new DonvikinhdoanhList();
	   String userId;
		
		String command; 
		command = "select a.pk_seq, a.ten, b.checked from nhacungcap a, nhacungcap_dvkd b where a.pk_seq = b.ncc_fk and b.dvkd_fk='" + dvkdId + "'";
		
		return  (db.get(command));
	}*/
	
 
 }