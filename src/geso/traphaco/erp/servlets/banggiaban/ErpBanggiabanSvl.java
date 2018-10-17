package geso.traphaco.erp.servlets.banggiaban;

import geso.traphaco.erp.beans.banggiaban.IBanggiaban;
import geso.traphaco.erp.beans.banggiaban.IBanggiabanList;
import geso.traphaco.erp.beans.banggiaban.IBanggiaban_kh;
import geso.traphaco.erp.beans.banggiaban.imp.Banggiaban;
import geso.traphaco.erp.beans.banggiaban.imp.BanggiabanList;
import geso.traphaco.erp.beans.banggiaban.imp.Banggiaban_kh;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.ResultSet;

public class ErpBanggiabanSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
   public ErpBanggiabanSvl() {
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
	    	IBanggiaban_kh assign = new Banggiaban_kh();
	    	assign.setId(bgId);
	    	assign.setUserId(userId);
	    	assign.init();
	    	session.setAttribute("assign", assign);
	    	String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_BangGiaBanAssign.jsp";
	    	response.sendRedirect(nextJSP);

	    }else
		    if (action.equals("approve")){
		    	IBanggiaban bgBean = (IBanggiaban) new Banggiaban();
		    	bgBean.setUserId(userId);
		    	bgBean.setId(bgId);
		    	bgBean.Chot();
		    	bgBean.closeDB();

		    	IBanggiabanList obj = new BanggiabanList();
		    	obj.setUserId(userId);
		    	session.setAttribute("obj", obj);
					
		    	String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_BangGiaBan.jsp";
		    	response.sendRedirect(nextJSP);
		    	
		    }
		else{
	    	IBanggiabanList obj = new BanggiabanList();
	    	obj.setCtyId(ctyId);
	    	obj.setCtyTen(ctyTen);
	    	obj.setUserId(userId);
	    	obj.init("");
	    	
	    	session.setAttribute("obj", obj);
				
	    	String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_BangGiaBan.jsp";
	    	response.sendRedirect(nextJSP);
	    }

	}  	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    
	    IBanggiabanList obj = new BanggiabanList();		
		HttpSession session = request.getSession();
	    String userId = request.getParameter("userId");
	    
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    out.println(action);         
	    
	    if (action.equals("new"))
	    {
	    	IBanggiaban bgBean = (IBanggiaban) new Banggiaban(ctyId);
	    	bgBean.setCtyId(ctyId);
	    	bgBean.setUserId(userId);

	    	session.setAttribute("bgbanBean", bgBean);
    		
    		String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_BangGiaBanNew.jsp";
    		response.sendRedirect(nextJSP);
    		
	    }
	    if (action.equals("search")){
	    	String search = getSearchQuery(request, obj);   	
	    	
	    	obj.init(search);
			
	    	obj.setUserId(userId);
			
	    	session.setAttribute("obj", obj);
	    		
    		response.sendRedirect("/TraphacoHYERP/pages/Erp/Erp_BangGiaBan.jsp");	    	
	    	
	    }
	    
	    if (action.equals("assign")){
	    	
	    	IBanggiaban_kh assign = new Banggiaban_kh();
	
	    	assign.setId(request.getParameter("id"));
	    	
	    	assign.setUserId(userId);
	    	
	    	assign.init();
	    	
	    	session.setAttribute("assign", assign);
	    	
	    	String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_BangGiaBanAssign.jsp";
	    	
	    	response.sendRedirect(nextJSP);

	    }	    
	    
	    if (action.equals("save")){
	    	
	    	IBanggiaban_kh assign = new Banggiaban_kh();
	    	
	    	assign.setId(request.getParameter("id"));

	    	assign.setUserId(userId);
	    	
	    	assign.init();
	    	
	    	assign.UpdateBgban(request);
	    	
			obj.setUserId(userId);
			
			session.setAttribute("obj", obj);
				
			String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_BangGiaBan.jsp";
			
			response.sendRedirect(nextJSP);			    			    										    	
	    	
	    }
	    
	}   
	
	private String getSearchQuery(HttpServletRequest request, IBanggiabanList obj ){
//	    PrintWriter out = response.getWriter();
		
		String bgTen = request.getParameter("bgTen");
    	if (bgTen == null)
    		bgTen = "";
    	obj.setTen(bgTen);
    	
    	String dvkdId = request.getParameter("dvkdId");
    	if (dvkdId == null)
    		dvkdId = "";    	
    	obj.setDvkdId(dvkdId);
    	   	
    	String kenhId = request.getParameter("kenhId");
    	if (kenhId == null)
    		kenhId = "";    	
    	obj.setKenhId(kenhId);

    	String trangthai = request.getParameter("trangthai");   	
    	if (trangthai == null)
    		trangthai = "";    	
	
    	if (trangthai.equals("2"))
    		trangthai = "";
    	
    	obj.setTrangthai(trangthai);
    	
	    String query = "select a.pk_seq as id, a.ten as ten, a.trangthai as trangthai, d.ten as nguoitao, a.ngaytao as ngaytao, e.ten as nguoisua, a.ngaysua as ngaysua, b.donvikinhdoanh as dvkd, c.diengiai as kenh, c.pk_seq as kenhId from banggiamuanpp a, donvikinhdoanh b, kenhbanhang c, nhanvien d, nhanvien e where a.dvkd_fk=b.pk_seq and a.kenh_fk = c.pk_seq and a.nguoitao = d.pk_seq and a.nguoisua = e.pk_seq ";
    	
    	if (bgTen.length()>0){
    		Utility util = new Utility();
			query = query + " and upper(dbo.ftBoDau(a.ten)) like upper(N'%"+ util.replaceAEIOU(bgTen) + "%')";
			
    	}
    	
    	if (dvkdId.length()>0){
			query = query + " and b.pk_seq = '" + dvkdId + "'";
    		
    	}

    	if (kenhId.length()>0){
			query = query + " and c.pk_seq = '" + kenhId + "'";
    		
    	}

    	if(trangthai.length() > 0){
    		query = query + " and a.trangthai = '" + trangthai + "'";
    		
    	}
    	query = query + "  order by ten";
    	return query;
	}	
	
	private void Delete(String id){
		dbutils db = new dbutils();
		ResultSet rs = db.get("SELECT COUNT(*) AS NUM FROM ERP_DONDATHANG WHERE BGID = " + id + "");
		try{
			rs.next();
			if(!rs.getString("NUM").equals("0")){
				db.update("delete from ERP_BANGGIABAN_KH where banggiaban_fk ='" + id + "'");
				db.update("delete from erp_bgban_sanpham where bgban_fk='" + id + "'");
				db.update("delete from erp_banggiaban where pk_seq = '" + id + "'");

			}
			rs.close();
		}catch(java.sql.SQLException e){}
		db.shutDown();
		
	}
	
}