package geso.traphaco.erp.servlets.hoadontaichinh;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.hoadontaichinh.IErpHoaDonTaiChinh;
import geso.traphaco.erp.beans.hoadontaichinh.imp.ErpHoaDonTaiChinh;
import geso.traphaco.erp.beans.phieuxuatkho.IErpPhieuxuatkho;
import geso.traphaco.erp.beans.phieuxuatkho.imp.ErpPhieuxuatkho;

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

import Z.DB;

public class ErpHoaDonTaiChinhSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ErpHoaDonTaiChinhSvl() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		IErpHoaDonTaiChinh obj;
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
	    
	    String hdId = util.getId(querystring);
	    userId = util.getUserId(querystring);
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    obj = new ErpHoaDonTaiChinh();	    
	  	    
	    if(action.equals("chot")){
	    	obj.setId(hdId);
	    	obj.setUserId(userId);
	    	obj.setMessage(obj.ChotHoaDon());
	   	 
	    }else if(action.equals("delete")){
	    	String msg= this.Xoa(hdId,userId);
	    	obj.setMessage(msg);
	    	
	    } 
	    else if(action.equals("xuatkho")){
	    	IErpPhieuxuatkho pxkBean = new ErpPhieuxuatkho("");
	    	pxkBean.setUserId(userId);
	    	pxkBean.setNgayxuatkho(this.getDateTime());
	    	pxkBean.setNgaychotNV(this.getDateTime());
	    	pxkBean.setNdxId("100002");
	    	pxkBean.setLoaixuatkho("HD");
	    	pxkBean.setNppIdKhoId(hdId);
	    	pxkBean.setHDTCId(hdId);
	    	if(pxkBean.isDataoPXK()){
	    		obj.setMessage("Hóa đơn đã được xuất đủ hàng nhưng chưa duyệt phiếu. Vui lòng kiểm tra lại các phiếu xuất kho của hóa đơn này.");
	    	}
	    	else{
		    	if(pxkBean.getNppId() == null || pxkBean.getNppId().length() == 0)
		    		pxkBean.setMsg("Hóa đơn chưa xác định nhà phân phối");
		    	if(pxkBean.getKhoId() == null || pxkBean.getKhoId().length() == 0)
		    		pxkBean.setMsg("Hóa đơn chưa xác định kho");
		    	pxkBean.createRs();
		    	String url = "/TraphacoHYERP/pages/Erp/ErpPhieuXuatKhoNew.jsp";
		    	session.setAttribute("pxkBean", pxkBean);
				response.sendRedirect(url);
				return;
	    	}
	    }
	    obj.setUserId(userId);
	    obj.setListHoaDon("");
		session.setAttribute("obj", obj);
		session.setAttribute("usedId", userId);
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoaDonTaiChinh.jsp";
		response.sendRedirect(nextJSP);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 	request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
		    PrintWriter out = response.getWriter();
		    IErpHoaDonTaiChinh obj;
		    String userId;
		    Utility util = new Utility();
		    
		    obj = new ErpHoaDonTaiChinh();		
			HttpSession session = request.getSession();
		    userId = util.antiSQLInspection(request.getParameter("userId"));
		    
		    String action = request.getParameter("action");
		    System.out.println("action:"+action);
		    if (action == null){
		    	action = "";
		    }       
		    
		    String chungtu=util.antiSQLInspection(request.getParameter("chungtu"));
			if (chungtu == null)
				chungtu = "";
			
		    
		    if(action.equals("view") || action.equals("next") || action.equals("prev")){
		    	obj = new ErpHoaDonTaiChinh();
		    	String search = getSearchQuery(request, obj, userId);
		     	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
		     	obj.setListHoaDon(search);
		    	obj.init();
		     	obj.setUserId(userId);
		     	//obj.setListHoaDon("");
	        	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
	        	session.setAttribute("obj", obj);
	        	String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoaDonTaiChinh.jsp";	    	
	    	    response.sendRedirect(nextJSP);
		    }else if(action.equals("new")){
		    	obj = new ErpHoaDonTaiChinh();		
		    	obj.loadContents();
		    	session.setAttribute("obj", obj);
		    	String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoaDonTaiChinhNew.jsp";	 
		    	response.sendRedirect(nextJSP);
		    }
		    else if(action.equals("chot")){
		    	obj = new ErpHoaDonTaiChinh();	
		    	obj.setId(chungtu);
		    	obj.setUserId(userId);
		    	obj.setMessage(obj.ChotHoaDon());
		    	
		    	String search = getSearchQuery(request, obj, userId);
			    obj.setListHoaDon(search);
				session.setAttribute("obj", obj);
				session.setAttribute("usedId", userId);
				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoaDonTaiChinh.jsp";
				response.sendRedirect(nextJSP);
				
		    }
		    else if (action.equals("delete")){
		    	obj = new ErpHoaDonTaiChinh();	
		    	obj.setId(chungtu);
		    	obj.setUserId(userId);
		    	
		    	String msg= this.Xoa(chungtu,userId);
		    	obj.setMessage(msg);
		    	
		    	String search = getSearchQuery(request, obj, userId);
			    obj.setListHoaDon(search);
				session.setAttribute("obj", obj);
				session.setAttribute("usedId", userId);
				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoaDonTaiChinh.jsp";
				response.sendRedirect(nextJSP);
		    }
		    else{
		    	    
		    	String search = getSearchQuery(request, obj, userId);
		    	obj.setUserId(userId);
		    	obj.setListHoaDon(search);					
		    	session.setAttribute("obj", obj);			    		
		    	String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoaDonTaiChinh.jsp";	    	
		    	out.println(search);
			    response.sendRedirect(nextJSP);
		    }		    

	}

	private String getSearchQuery(HttpServletRequest request, IErpHoaDonTaiChinh obj, String userId){
		
		Utility util = new Utility();
		String sochungtu= util.antiSQLInspection(request.getParameter("sochungtu"));
		if (sochungtu == null)
			sochungtu = "";
		obj.setId(sochungtu);
		
		String nppId=util.antiSQLInspection(request.getParameter("nppId"));
		if (nppId == null)
			nppId = "";
		nppId = nppId.split("-")[0].trim();
		obj.SetNppId(nppId);
		
		
		String sohoadon = util.antiSQLInspection(request.getParameter("sohoadon"));
    	if (sohoadon == null)
    		sohoadon = "";
    	obj.SetSoHoaDon(sohoadon);
    	    	
    	String nguoitao = util.antiSQLInspection(request.getParameter("nguoitao"));
    	if (nguoitao == null)
    		nguoitao = "";
    	obj.setNguoitao(nguoitao);
    	
    	String tongtienhd = util.antiSQLInspection(request.getParameter("tongtienhd"));
		if (tongtienhd == null)
			tongtienhd = "";		
		obj.SetSotienavat(tongtienhd.trim().replaceAll(",", ""));
    
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
  
    	String ddhId = util.antiSQLInspection(request.getParameter("ddhId"));   	
    	if (ddhId == null)
    		ddhId = "";    	
    	obj.setddhId(ddhId);
    	
    	String pxkId = util.antiSQLInspection(request.getParameter("pxkId"));   	
    	if (pxkId == null)
    		pxkId = "";    	
    	obj.setpxkId(pxkId);
    	
    	String diachikh = util.antiSQLInspection(request.getParameter("diachikh"));
		if (diachikh == null)
			diachikh = "";
		obj.SetDiachiKH(diachikh);
		
		String khoId = util.antiSQLInspection(request.getParameter("khoId"));
		if (khoId == null)
			khoId = "";
		obj.setKhoId(khoId);
		
		String Sku = util.antiSQLInspection(request.getParameter("masku"));
		if (Sku == null)
			Sku = "";
		obj.SetSKU(Sku);
		    		 
		String query= 
			" SELECT 	HD.PK_SEQ,HD.NGAYXUATHD,HD.TRANGTHAI,HD.NGAYTAO ,HD.NGAYSUA,HD.KYHIEU,HD.SOHOADON , isnull(HD.SOHOADONDK,'') SOHOADONDK ,HD.HINHTHUCTT,KH.TEN, \n"+
			  " 		NT.TEN AS NGUOITAO,NS.TEN AS NGUOISUA ,ISNULL(HD.TONGTIENAVAT,0) AS TONGTIENAVAT, ISNULL(HD.HTXUATHD,0) HTXUATHD  \n"+
			  " FROM 	ERP_HOADON HD LEFT JOIN ERP_KHACHHANG KH ON KH.PK_SEQ=HD.KHACHHANG_FK \n"+ 
			  "			INNER JOIN NHANVIEN NT ON NT.PK_SEQ=HD.NGUOITAO \n"+
		      "     	INNER JOIN NHANVIEN NS ON NS.PK_SEQ=HD.NGUOISUA \n " +
		      "	WHERE   HD.LOAIHOADON = 0 \n";
	      
    	if (tungay.length()>0){
			query = query + " and HD.NGAYXUATHD >= '" + tungay+ "'";
    	}

    	if (denngay.length()>0){
			query = query + " and HD.NGAYXUATHD <= '" + denngay+ "'";
    	}
    	
    	if (sohoadon.length()>0){
			query = query + " and (HD.sohoadon like '%" + sohoadon+ "%' OR  HD.SOHOADONDK like '%" + sohoadon+ "%')";
    	}
    	
    	if(trangthai.length() > 0){
    		query = query + " and HD.trangthai = '" + trangthai + "'";
    		
    	}
    	if(sochungtu.length()>0){
    		query+=" and hd.pk_seq like '%"+sochungtu+"%' ";
    	}
    	
    	if(nppId.length()>0){
    		query+=" and  KH.PK_SEQ ='"+nppId+"'";
    	}
    	
    	if(pxkId.length()>0){
    		query+="  and HD.PK_SEQ IN (SELECT HOADON_FK FROM ERP_HOADON_XUATKHO WHERE xuatkho_fk = "+pxkId+")";
    	}
    	
    	if(nguoitao.length()>0){
    		query+=" and  NT.TEN LIKE N'%"+nguoitao+"%'";
    	}
    	
    	if(Sku.length() >0){
    		query+=" and  hd.pk_seq in (select a.HOADON_FK from ERP_HOADON_SP a inner join ERP_SANPHAM sp on sp.PK_SEQ=a.SANPHAM_FK where sp.timkiem  like '%"+util.replaceAEIOU(Sku)+"%'  )" ;
    	}
    	
    	if(diachikh.length()>0){
    		query+= " and KH.diachi like N'%"+diachikh+"%'";
    	}
    	
    	if(tongtienhd.length()>0){
    		query+= " and ISNULL(HD.TONGTIENAVAT,0) = "+obj.getSotienavat()+"";
    	}
    	
    	if(khoId.length()>0){
    		query+= " and HD.PK_SEQ IN (SELECT a.HOADON_FK FROM ERP_HOADON_XUATKHO a INNER JOIN ERP_XUATKHO b ON a.xuatkho_fk = b.PK_SEQ  WHERE b.KHO_FK = "+obj.getKhoId()+") ";
    	}
    	
    	System.out.println("SEARCH TRUOC KHI PHAN TRANG__ :"+query);
    	return query;    	
	}	
		
	
	private String Xoa(String hdId,String user) {
		
		try{
			dbutils db = new dbutils();
			db.getConnection().setAutoCommit(false);
			
			String query="";
			 
			//B1: KIỂM TRA TRẠNG THÁI XUẤT KHO CỦA HÓA ĐƠN (LOẠI TRƯỜNG HỢP NGƯỜI DÙNG SỬ DỤNG 2 TAB ĐỂ CHỐT)
			query = "SELECT TRANGTHAI FROM ERP_HOADON WHERE PK_SEQ ='"+hdId+"'";
			
			ResultSet kt_th = db.get(query);
			String trangthai = "";
			
			if(kt_th != null)
			{
				while(kt_th.next())
				{
					trangthai = kt_th.getString("TRANGTHAI");
				}
				kt_th.close();
			}
			
			// NẾU HÓA ĐƠN CHƯA CHỐT CHO PHÉP HỦY HÓA ĐƠN -- NHỮNG HÓA ĐƠN ĐÃ CHỐT TỰ VÀO CHỨC NĂNG HỦY CHỨNG TỪ HÓA ĐƠN
			if(trangthai.trim().equals("0")){
				
				query = "UPDATE  ERP_HOADON set TRANGTHAI = '2',nguoisua='"+user+"' where PK_SEQ='" + hdId + "'";
				if(!db.update(query)){
						db.update("rollback");
					 return "KHONG THE HUY HOA DON ,DONG LENH : "+ query;
					}	
								
			}
			else {
				
				db.getConnection().rollback();				
				return "YÊU CẦU LOAD LẠI TRANG" ;
			}
			
			//NHẢ LẠI TRẠNG THÁI ĐƠN HÀNG
						
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
			return "";
		}
		catch(Exception er)
		{
			er.printStackTrace();
			return er.getMessage();
		}
	}
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
}


