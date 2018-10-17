package geso.traphaco.erp.servlets.hoadontravencc;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.hoadontravencc.IErpHoaDon;
import geso.traphaco.erp.beans.hoadontravencc.imp.ErpHoaDon;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
public class ErpHoaDonTraveNccSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ErpHoaDonTraveNccSvl() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		IErpHoaDon obj;
		String userId;

		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    out = response.getWriter();
	    
	    String querystring = request.getQueryString();
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	    String ddhId = util.getId(querystring);
	    
	    userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    obj = new ErpHoaDon();
	    out.println(userId);
	    if(action.equals("chot")){
	    	obj.setId(ddhId);
	    	obj.setUserId(userId);
	    	
	    	obj.setMessage(obj.ChotHoaDon());
	    	 
	    }else if(action.equals("delete")){
	    	String msg= this.Xoa(ddhId,userId);
	    	obj.setMessage(msg);
	    } 
 
	    obj.setUserId(userId);
	    obj.setCongtyId((String)session.getAttribute("congtyId"));
	    obj.setListHoaDon("");
	    
		session.setAttribute("obj", obj);
				
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoaDonTraveNcc.jsp";
		response.sendRedirect(nextJSP);
	}
	private String Xoa(String ddhId,String user) {
		// TODO Auto-generated method stub
		dbutils db = new dbutils();
		String query = "update  Erp_HoaDon set trangthai='2',nguoisua='"+user+"' where pk_Seq='" + ddhId + "' and trangthai=0";
		if(db.updateReturnInt(query)!=1){
			db.update("rollback");
			 return "Khong The Huy Hoa Don ,Loi Dong Lenh : "+ query;
			}
		db.shutDown();
		return "";
	}
 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 	request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
		    PrintWriter out = response.getWriter();
		    IErpHoaDon obj;
		    String userId;
		    Utility util = new Utility();
		    
		    obj = new ErpHoaDon();		
			HttpSession session = request.getSession();
		    userId = util.antiSQLInspection(request.getParameter("userId"));
		    
		    obj.setCongtyId((String)session.getAttribute("congtyId"));
		    
		    String action = request.getParameter("action");
		    if (action == null){
		    	action = "";
		    }    
		    
		   // System.out.println("action la: " + action); 	    
		    
		    if(action.equals("view") || action.equals("next") || action.equals("prev")){
		    	obj = new ErpHoaDon();
		    	
		     	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
	    		
	        	//obj.createDdhlist("");
		     	obj.setUserId(userId);
		     	obj.setListHoaDon("");
	        	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	
	        
	        	session.setAttribute("obj", obj);
	    	    		
	        	String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoaDonTraveNcc.jsp";	    	
	        	
	    	    response.sendRedirect(nextJSP);

		    }else if(action.equals("new")){
		    	 obj = new ErpHoaDon();		
		    	 obj.CreateRs(false);
		    	 
		    	session.setAttribute("ctyId", "");
		    	session.setAttribute("obj", obj);
		    	String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoaDonTraveNccNew.jsp";	 
		    	response.sendRedirect(nextJSP);
		    }
		    else{
		    	    
	    	String search = getSearchQuery(request, obj, userId);
	    	obj.setCongtyId((String)session.getAttribute("congtyId"));
	    	obj.setUserId(userId);
	    	obj.setListHoaDon(search);
				
	    	session.setAttribute("obj", obj);
		    		
	    	String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoaDonTraveNcc.jsp";	    	
	    	out.println(search);
		    response.sendRedirect(nextJSP);
		    }
		    

	}

	private String getSearchQuery(HttpServletRequest request, IErpHoaDon obj, String userId){
//	    PrintWriter out = response.getWriter();
		
		Utility util = new Utility();
		String sochungtu= util.antiSQLInspection(request.getParameter("sochungtu"));
		if (sochungtu == null)
			sochungtu = "";
		obj.setId(sochungtu);
		
		String nppId=util.antiSQLInspection(request.getParameter("nppId"));
		if (nppId == null)
			nppId = "";
		obj.SetNppId(nppId);
		
		
		String sohoadon = util.antiSQLInspection(request.getParameter("sohoadon"));
    	if (sohoadon == null)
    		sohoadon = "";
    	obj.SetSoHoaDon(sohoadon);
    	
    	String ngayhoadon = util.antiSQLInspection(request.getParameter("ngayhoadon"));
    	if (ngayhoadon == null)
    		ngayhoadon = "";
    	obj.setNgayxuathd(ngayhoadon);
    
    	String tungay = util.antiSQLInspection(request.getParameter("tungay"));
    	if (tungay == null)
    		tungay = "";    	
    	obj.setTungay(tungay);

    	String denngay = util.antiSQLInspection(request.getParameter("denngay"));
    	if (denngay == null)
    		denngay = "";    	
    	obj.setDenNgay(denngay);
    	   	
    	String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));   	
    	if (trangthai == null)
    		trangthai = "";    		
    	obj.setTrangthai(trangthai);
  
    	String Sku = util.antiSQLInspection(request.getParameter("masku"));
		if (Sku == null)
			Sku = "";
		obj.SetSKU(Sku);
		
		String sodontrahang = util.antiSQLInspection(request.getParameter("dontrahang"));
		if (sodontrahang == null)
			sodontrahang = "";
		obj.setSodontrahang(sodontrahang);
 
    	String query=" SELECT HD.LOAIHOADON , HD.PK_SEQ, HD.NGAYXUATHD,HD.TRANGTHAI,HD.NGAYTAO ,HD.NGAYSUA,HD.KYHIEU,HD.SOHOADON,HD.HINHTHUCTT,NV.TEN , HD.TONGTIENAVAT, "+
				"	 CASE WHEN HD.LOAIHOADON = 6 THEN NV.TEN ELSE  NPP.TEN END AS NPP, NT.TEN AS NGUOITAO,NS.TEN AS NGUOISUA ,isnull( HD.HOANTAT,'0') as HOANTAT "+
				"   FROM ERP_HOADON HD LEFT JOIN ERP_NHACUNGCAP NPP ON NPP.PK_SEQ=HD.NCC_FK " +
				"	LEFT JOIN ERP_NHACUNGCAP NV ON NV.PK_SEQ=HD.NCC_FK "+
				" INNER JOIN NHANVIEN NT ON NT.PK_SEQ=HD.NGUOITAO "+
				" INNER JOIN NHANVIEN NS ON NS.PK_SEQ=HD.NGUOISUA  " +
				"  WHERE  LOAIHOADON = 6 AND HD.CONGTY_FK = "+ obj.getCongtyId()  +" ";
    	
    	if (tungay.length()>0){
			query = query + " and HD.ngaytao >= '" + tungay+ "'";
 
    	}

    	if (denngay.length()>0){
			query = query + " and HD.ngaytao <= '" + denngay+ "'";
		
    		
    	}
    	
    	if (ngayhoadon.length()>0){
			query = query + " and HD.NGAYXUATHD = '" + ngayhoadon+ "'";
		
    		
    	}
    	
    	if (sohoadon.length()>0){
			query = query + " and HD.sohoadon like '%" + sohoadon+ "%'";
			
    		
    	}
    	
    	if(trangthai.length() > 0){
    		query = query + " and HD.trangthai = '" + trangthai + "'";
    		
    	}
    	if(sochungtu.length()>=0){
    		query+=" and hd.pk_seq like '%"+sochungtu+"%' ";
    	}
    	
    	if(nppId.length()>0){
    		query+=" and ( NV.Ten like N'%"+nppId+"%' OR NV.PK_SEQ LIKE '%"+nppId+"%' OR NV.MA like '%"+nppId+"%' ) ";
    	}
    	
    	if(Sku.length() >0){
    		query+=" and  hd.pk_seq in (select a.HOADON_FK from ERP_HOADON_SP a inner join SANPHAM sp on sp.PK_SEQ=a.SANPHAM_FK where sp.timkiem  like '%"+util.replaceAEIOU(Sku)+"%'  )" ;
    	}
    		
    	if(sodontrahang.length()>0)
    		query += " and HD.PK_SEQ IN (select HOADON_FK from ERP_HOADON_DDH where DDH_FK like '%"+sodontrahang+"%')";
    	
    	System.out.println("Get Truoc khi Phan Trang :"+query);
    	return query;    	
	}	
		

}
