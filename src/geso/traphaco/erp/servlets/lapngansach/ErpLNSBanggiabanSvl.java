package geso.traphaco.erp.servlets.lapngansach;

import geso.traphaco.erp.beans.lapngansach.ILNSBanggiaban;
import geso.traphaco.erp.beans.lapngansach.ILNSBanggiabanList;
import geso.traphaco.erp.beans.lapngansach.ILNSBanggiaban_kh;
import geso.traphaco.erp.beans.lapngansach.imp.LNSBanggiaban;
import geso.traphaco.erp.beans.lapngansach.imp.LNSBanggiabanList;
import geso.traphaco.erp.beans.lapngansach.imp.LNSBanggiaban_kh;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

 public class ErpLNSBanggiabanSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
   public ErpLNSBanggiabanSvl() {
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
	    	ILNSBanggiaban_kh assign = new LNSBanggiaban_kh();
	    	assign.setCtyId(ctyId);
	    	assign.setId(bgId);
	    	assign.setUserId(userId);
	    	assign.init();
	    	
	    	session.setAttribute("assign", assign);
	    	String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_LNSBangGiaBanAssign.jsp";
	    	response.sendRedirect(nextJSP);

	    }else
		    if (action.equals("approve")){
		    	ILNSBanggiaban bgBean = (ILNSBanggiaban) new LNSBanggiaban();
		    	bgBean.setUserId(userId);
		    	bgBean.setId(bgId);
		    	bgBean.Chot();
		    	bgBean.closeDB();

		    	ILNSBanggiabanList obj = new LNSBanggiabanList();
		    	obj.setUserId(userId);
		    	session.setAttribute("obj", obj);
					
		    	String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_LNSBangGiaBan.jsp";
		    	response.sendRedirect(nextJSP);
		    	
		    }
		else{
	    	ILNSBanggiabanList obj = new LNSBanggiabanList();
	    	obj.setCtyId(ctyId);
	    	obj.setCtyTen(ctyTen);
	    	obj.setUserId(userId);
	    	obj.init("");
	    	
	    	session.setAttribute("obj", obj);
				
	    	String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_LNSBangGiaBan.jsp";
	    	response.sendRedirect(nextJSP);
	    }

	}  	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    
	    ILNSBanggiabanList obj = new LNSBanggiabanList();		
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
	    	ILNSBanggiaban bgBean = (ILNSBanggiaban) new LNSBanggiaban(ctyId);
	    	bgBean.setCtyId(ctyId);
	    	bgBean.setUserId(userId);
	    	bgBean.createRS();
//	    	obj.closeDB();
	    	session.setAttribute("bgbanBean", bgBean);
    		
    		String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_LNSBangGiaBanNew.jsp";
    		response.sendRedirect(nextJSP);
    		
	    }
	    if (action.equals("search")){
	    	String search = getSearchQuery(request, obj);   	
	    	
	    	obj.init(search);
			
	    	obj.setUserId(userId);
			
	    	session.setAttribute("obj", obj);
	    		
    		response.sendRedirect("/TraphacoHYERP/pages/Erp/Erp_LNSBangGiaBan.jsp");	    	
	    	
	    }
	    
	    if (action.equals("assign")){
	    	
	    	ILNSBanggiaban_kh assign = new LNSBanggiaban_kh();
	
	    	assign.setId(request.getParameter("id"));
	    	
	    	assign.setUserId(userId);
	    	
	    	assign.init();
	    	
	    	session.setAttribute("assign", assign);
	    	
	    	String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_LNSBangGiaBanAssign.jsp";
	    	
	    	response.sendRedirect(nextJSP);

	    }	    
	    
	    if (action.equals("save")){
	    	
	    	ILNSBanggiaban_kh assign = new LNSBanggiaban_kh();
	    	
	    	assign.setId(request.getParameter("id"));

	    	assign.setUserId(userId);
	    	
	    	assign.init();
	    	
	    	assign.UpdateBgban(request);

			obj.setUserId(userId);
			obj.init("");
			
			session.setAttribute("obj", obj);
				
			String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_LNSBangGiaBan.jsp";
			
			response.sendRedirect(nextJSP);			    			    										    	
	    	
	    }
	    
	}   
	
	private String getSearchQuery(HttpServletRequest request, ILNSBanggiabanList obj ){
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
    	
		String query = 	"select a.pk_seq as id, a.ten as ten, a.trangthai as trangthai, a.tinhtrang, d.ten as nguoitao, a.ngaytao as ngaytao, " +
						"e.ten as nguoisua, a.ngaysua as ngaysua, b.donvikinhdoanh as dvkd, c.ten as kenh, c.pk_seq as kenhId " +
						"from ERP_LNSBANGGIABAN a, donvikinhdoanh b, kenhbanhang c, nhanvien d, nhanvien e " +
						"where a.dvkd_fk=b.pk_seq and a.kenh_fk = c.pk_seq and a.nguoitao = d.pk_seq and a.nguoisua = e.pk_seq " +
						"and a.congty_fk = " + obj.getCtyId() + " ";
    	
    	if (bgTen.length()>0){
    		geso.traphaco.center.util.Utility util = new geso.traphaco.center.util.Utility();
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
		
		db.update("delete from erp_lnsbgban_sanpham where bgban_fk='" + id + "'");
		db.update("delete from erp_lnsbanggiaban where pk_seq = '" + id + "'");
		db.shutDown();
		
	}
	
}