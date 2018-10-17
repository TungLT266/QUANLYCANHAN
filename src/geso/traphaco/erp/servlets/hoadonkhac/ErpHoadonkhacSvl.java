package geso.traphaco.erp.servlets.hoadonkhac;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.center.util.UtilityKeToan;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import geso.traphaco.erp.beans.hoadonkhac.IErpHoadonkhacList;
import geso.traphaco.erp.beans.hoadonkhac.imp.ErpHoadonkhacList;
import geso.traphaco.erp.beans.hoadonkhac.IErpHoadonkhac;
import geso.traphaco.erp.beans.hoadonkhac.imp.ErpHoadonkhac;
import geso.traphaco.erp.beans.sohoadon.imp.Sohoadon;
import geso.traphaco.erp.util.UtilitySyn;

public class ErpHoadonkhacSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	PrintWriter out;
	
    public ErpHoadonkhacSvl() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out  = response.getWriter();
	    
	    HttpSession session = request.getSession();	
	    
	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    IErpHoadonkhacList obj = new ErpHoadonkhacList();
	    
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    obj.setUserId(userId);
	    obj.setCongtyId(ctyId);
	    
	    String action = util.getAction(querystring);
	    String hdId = util.getId(querystring);
	    String msg = "";
	    
	    if(action.trim().equals("delete"))
	    {
	    	dbutils db = new dbutils();
	    	if(!db.update("UPDATE ERP_HOADONKHAC SET TRANGTHAI = '2' where pk_seq = '" + hdId + "' and trangthai=0"))
	    	{
	    		msg = "Không thể xóa erp_hoadonkhac";
	    	}
	    	
	    }
	    
	    if(action.trim().equals("chot"))
	    {
	    	dbutils db = new dbutils();
	    	msg = ChotHoaDon(ctyId, hdId, db);
	    	db.shutDown();
	    }
	    
	    
	    if(action.trim().equals("unchot"))
	    {
	    	dbutils db = new dbutils();
	    	msg = boChotHoaDon(ctyId, hdId, db);
	    	
	    	db.shutDown();
	    }
	    
	    obj.init("");
	    obj.setMsg(msg);
		session.setAttribute("obj", obj);
	    
	    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoadonkhac.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out  = response.getWriter();
	    
	    HttpSession session = request.getSession();	
	    
	    out = response.getWriter();
	    Utility util = new Utility();
	    
	    String userId = util.antiSQLInspection(request.getParameter("userId"));  
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    
	    IErpHoadonkhacList obj;
	    
		String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    
	    if(action.equals("new"))
	    {
    		IErpHoadonkhac hdk = new ErpHoadonkhac();
    	
    		hdk.setCongtyId(ctyId);
    		hdk.setUserId(userId);
    		hdk.createRS();
    	
	    	session.setAttribute("hdkBean", hdk);  	
    		session.setAttribute("userId", userId);
		
    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpHoadonkhacNew.jsp");
	    }
	    else
	    {
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
	    		obj = new ErpHoadonkhacList();
		    	obj.setCongtyId(ctyId);
			    obj.setUserId(userId);

	    		System.out.println( "trang ke tiep la" + Integer.parseInt(request.getParameter("nxtApprSplitting")));
	    		obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));

	    		this.getSearchQuery(request, obj);
		    	
		    	obj.init("");

		    	
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	
		    	session.setAttribute("obj", obj);
		    	
		    	response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpHoadonkhac.jsp");	
		    }
	    	else{
	    	
	    	obj = new ErpHoadonkhacList();
	    	obj.setCongtyId(ctyId);
		    obj.setUserId(userId);

	    	this.getSearchQuery(request, obj);
	    	
	    	obj.setUserId(userId);
	    	obj.init("");
				
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
		
    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpHoadonkhac.jsp");
	    	}
	    }
	}

	private static String ChotHoaDon(String ctyId, String Id, dbutils db)
	{
		String msg = "";

    	
		Utility util = new Utility();
		
    	try 
    	{
			db.getConnection().setAutoCommit(false);
			
			String query = 
			
			" SELECT ISNULL(a.thoihanno,0) thoihanno, a.doanhthu_fk, ISNULL(A.DIENGIAI,'') as diengiai, isnull(a.loaick,'')as loaick,a.ngayghinhan, a.ngayhoadon, a.vat, " +
			" isnull(c.ma,'') as trungtamdt, b.PK_SEQ KHACHHANG_FK, " +
//			" ( select SUM( ROUND(thanhtien, 0) ) from erp_hoadonkhac_sanpham where hoadonkhac_fk = a.pk_seq ) as SOTIEN,  \n " +
			" ( select SUM( ROUND(DONGIA, 0) * ISNULL(ROUND(SOLUONG,0),0) ) from erp_hoadonkhac_sanpham where hoadonkhac_fk = a.pk_seq ) as SOTIEN,  \n " +
			" a.tienvat as TIENVAT, A.SOHOADON AS MACHUNGTU " +
			" FROM ERP_HOADONKHAC a " +
			" inner join NHAPHANPHOI b on a.khachhang_fk = b.PK_SEQ \n " +
			" left join ERP_TRUNGTAMDOANHTHU c on a.trungtamdoanhthu_fk = c.pk_seq \n " +			
			" where a.pk_seq = '" + Id + "' \n ";
			
			System.out.println("Query: "+query);
			ResultSet rs = db.get(query);
			
			String taikhoanCO = "";
			String taikhoanCO_VAT = "";
			String taikhoanNO = "";
			String taikhoanNO_VAT = "";
			
			String doituong_NO = "";
			String madoituong_NO = "";
			String maChungTu = "";
			String doituong_CO = "";
			String madoituong_CO = "";
			String loaiDT = "";
			String isNPPNo = "";
			String isNPPCo = "";
			String thoihanno = "";
			while(rs.next())
			{

				double TONGSOTIEN = rs.getDouble("SOTIEN");
				double TONGVAT = rs.getDouble("TIENVAT");
				doituong_NO = "Khách hàng";
				madoituong_NO = rs.getString("KHACHHANG_FK");
				maChungTu = rs.getString("MACHUNGTU");
				String diengiai = rs.getString("diengiai");
//				if(TONGSOTIEN < 0) TONGSOTIEN = TONGSOTIEN*(-1);
//				if(TONGVAT < 0) TONGVAT = TONGVAT*(-1);
				thoihanno = rs.getString("THOIHANNO");
				doituong_CO = "";
				madoituong_CO = "";
				String ngayghinhan = rs.getString("ngayghinhan");
				String nam = ngayghinhan.substring(0, 4);
				String thang = ngayghinhan.substring(5, 7);
					
				String tiente_fk = "100000";
				

				if(rs.getString("doanhthu_fk").equals("400002")) //LOAI HD: HÓA ĐƠN ĐIỀU CHỈNH TĂNG
				{
//					query = "SELECT (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = KH.TAIKHOAN_FK AND NPP_FK=1) AS TAIKHOAN_NO, " +
//							"TK.PK_SEQ AS TAIKHOAN_CO, \n " +
//							"(SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = KH.TAIKHOAN_FK AND NPP_FK=1) AS TAIKHOAN_NO_VAT,  " +
//							"(SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '33311000' AND NPP_FK=1 ) AS TAIKHOAN_CO_VAT \n " +
//							" ,ABS(HD_SP.THANHTIEN) AS TIENHANG,HD_SP.SANPHAM_FK " + 
//							"FROM ERP_HOADONKHAC HD \n " +
//							"INNER JOIN ERP_HOADONKHAC_SANPHAM HD_SP ON HD_SP.HOADONKHAC_FK = HD.PK_SEQ " +
//							"INNER JOIN NHAPHANPHOI KH ON KH.PK_SEQ = HD.KHACHHANG_FK \n " +
//							"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = HD_SP.SANPHAM_FK "+
//							"LEFT JOIN ERP_LOAISANPHAM LSP ON LSP.PK_SEQ= SP.LOAISANPHAM_FK "+
//							"LEFT JOIN ERP_TAIKHOANKT TK ON TK.SOHIEUTAIKHOAN= LSP.TaikhoanDoanhthu_sh_fk  AND TK.NPP_FK =1 "+
//							"WHERE HD.PK_SEQ =  '" + Id + "' \n ";
					query=  "SELECT (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = KH.TAIKHOAN_FK AND NPP_FK=1) AS TAIKHOAN_NO, \r\n" + 
							"							TK.PK_SEQ AS TAIKHOAN_CO, \r\n" + 
							"							(SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = KH.TAIKHOAN_FK AND NPP_FK=1) AS TAIKHOAN_NO_VAT,  \r\n" + 
							"							(SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '33311000' AND NPP_FK=1 ) AS TAIKHOAN_CO_VAT \r\n" + 
							"							 ,ABS(SUM(ROUND((HD_SP.DONGIACK - HD_SP.DONGIA) *HD_SP.SOLUONG, 0) )) AS TIENHANG,HD_SP.SANPHAM_FK  \r\n" + 
							"							FROM ERP_HOADONKHAC HD \r\n" + 
							"							INNER JOIN ERP_HOADONKHAC_SANPHAM HD_SP ON HD_SP.HOADONKHAC_FK = HD.PK_SEQ \r\n" + 
							"							INNER JOIN NHAPHANPHOI KH ON KH.PK_SEQ = HD.KHACHHANG_FK \r\n" + 
							"							INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = HD_SP.SANPHAM_FK \r\n" + 
							"							LEFT JOIN ERP_LOAISANPHAM LSP ON LSP.PK_SEQ= SP.LOAISANPHAM_FK \r\n" + 
							"							LEFT JOIN ERP_TAIKHOANKT TK ON TK.SOHIEUTAIKHOAN= LSP.TaikhoanDoanhthu_sh_fk  AND TK.NPP_FK =1 \r\n" + 
							"							WHERE HD.PK_SEQ = '"+Id+"'\r\n" + 
							"							GROUP BY KH.TAIKHOAN_FK,TK.PK_SEQ,HD_SP.SANPHAM_FK";
					
					System.out.println("query chi tiet : " + query);
					ResultSet rsTK = db.get(query);
					loaiDT = "Hóa đơn điều chỉnh tăng";
					if(rsTK != null){
						while(rsTK.next()){
							taikhoanNO =  rsTK.getString("TAIKHOAN_NO")==null?"":rsTK.getString("TAIKHOAN_NO");
							taikhoanCO = rsTK.getString("TAIKHOAN_CO");
							taikhoanNO_VAT =  rsTK.getString("TAIKHOAN_NO_VAT");
							taikhoanCO_VAT = rsTK.getString("TAIKHOAN_CO_VAT");
							doituong_CO = (rsTK.getString("SANPHAM_FK")==null)? "" : "Sản phẩm";
							madoituong_CO = (rsTK.getString("SANPHAM_FK")==null)? "" : rsTK.getString("SANPHAM_FK");
							TONGSOTIEN =rsTK.getDouble("TIENHANG");
							doituong_NO = "Khách hàng";
							madoituong_NO = rs.getString("KHACHHANG_FK");
							if(taikhoanCO.trim().length() <= 0 || taikhoanNO.trim().length() <= 0 )
							{
								msg = "Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
								System.out.println(msg);
								db.getConnection().rollback();
								return msg;
							}
							isNPPNo = "1";
							isNPPCo = "null";
							msg=util.Update_TaiKhoan_Diengiai_NPP_TUOINO (db, thang, nam, ngayghinhan, ngayghinhan, "Hóa đơn khác", Id, taikhoanNO, taikhoanCO, "", Double.toString(TONGSOTIEN),Double.toString(TONGSOTIEN), doituong_NO, madoituong_NO, doituong_CO,  madoituong_CO, "", "0", "", "100000", "NULL", "1", Double.toString(TONGSOTIEN), Double.toString(TONGSOTIEN), loaiDT + " - Tiền hàng", diengiai,isNPPNo,isNPPCo,maChungTu,maChungTu,thoihanno);
						
							if(msg.trim().length() > 0)
							{
								db.getConnection().rollback();
								return msg;
							}
						}
						rsTK.close();
						doituong_CO = "";
						madoituong_CO = "";
						
					}
				}
				else if(rs.getString("doanhthu_fk").equals("400003")) //LOAI HD: HÓA ĐƠN ĐIỀU CHỈNH GIẢM
				{
//					query = "SELECT tk.PK_SEQ AS TAIKHOAN_NO, " +
//							"(SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = KH.TAIKHOAN_FK AND CONGTY_FK = " + ctyId + ") AS TAIKHOAN_CO, \n " +							
//							"(SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '33311000' AND CONGTY_FK = " + ctyId + " ) AS TAIKHOAN_NO_VAT, \n " +
//							"(SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = KH.TAIKHOAN_FK AND CONGTY_FK = " + ctyId + ") AS TAIKHOAN_CO_VAT " +
//							" ,ABS(HD_SP.THANHTIEN) AS TIENHANG,HD_SP.SANPHAM_FK " + 
//							"FROM ERP_HOADONKHAC HD \n " +
//							"INNER JOIN ERP_HOADONKHAC_SANPHAM HD_SP ON HD_SP.HOADONKHAC_FK = HD.PK_SEQ " +
//							"INNER JOIN NHAPHANPHOI KH ON KH.PK_SEQ = HD.KHACHHANG_FK \n " +
//							"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = HD_SP.SANPHAM_FK "+
//							"LEFT JOIN ERP_LOAISANPHAM LSP ON LSP.PK_SEQ= SP.LOAISANPHAM_FK "+
//							"LEFT JOIN ERP_TAIKHOANKT TK ON TK.SOHIEUTAIKHOAN= LSP.TaikhoanDoanhthu_sh_fk AND TK.NPP_FK =1"+
//							"WHERE HD.PK_SEQ =  '" + Id + "' \n ";
					query = "SELECT tk.PK_SEQ AS TAIKHOAN_NO,\r\n" + 
							"							(SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = KH.TAIKHOAN_FK AND CONGTY_FK = 100000 ) AS TAIKHOAN_CO, 						\r\n" + 
							"							(SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '33311000' AND CONGTY_FK = 100000  ) AS TAIKHOAN_NO_VAT, \r\n" + 
							"							(SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = KH.TAIKHOAN_FK AND CONGTY_FK = 100000 ) AS TAIKHOAN_CO_VAT\r\n" + 
							"							 ,ABS(SUM(ROUND((HD_SP.DONGIACK - HD_SP.DONGIA) *HD_SP.SOLUONG, 0) )) AS TIENHANG,HD_SP.SANPHAM_FK \r\n" + 
							"							FROM ERP_HOADONKHAC HD \r\n" + 
							"							INNER JOIN ERP_HOADONKHAC_SANPHAM HD_SP ON HD_SP.HOADONKHAC_FK = HD.PK_SEQ\r\n" + 
							"							INNER JOIN NHAPHANPHOI KH ON KH.PK_SEQ = HD.KHACHHANG_FK \r\n" + 
							"							INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = HD_SP.SANPHAM_FK \r\n" + 
							"							LEFT JOIN ERP_LOAISANPHAM LSP ON LSP.PK_SEQ= SP.LOAISANPHAM_FK \r\n" + 
							"							LEFT JOIN ERP_TAIKHOANKT TK ON TK.SOHIEUTAIKHOAN= LSP.TaikhoanDoanhthu_sh_fk AND TK.NPP_FK =1\r\n" + 
							"							WHERE HD.PK_SEQ =  '"+Id+"'\r\n" + 
							"							GROUP BY tk.PK_SEQ,KH.TAIKHOAN_FK,HD_SP.SANPHAM_FK ";
					System.out.println("query chi tiet : " + query);
					
					ResultSet rsTK = db.get(query);
					if(rsTK != null){
						while(rsTK.next()){
							taikhoanNO =  rsTK.getString("TAIKHOAN_NO")==null?"":rsTK.getString("TAIKHOAN_NO");
							taikhoanCO = rsTK.getString("TAIKHOAN_CO");
					
							taikhoanNO_VAT =  rsTK.getString("TAIKHOAN_NO_VAT");
							taikhoanCO_VAT = rsTK.getString("TAIKHOAN_CO_VAT");
							TONGSOTIEN =rsTK.getDouble("TIENHANG");
							doituong_NO = (rsTK.getString("SANPHAM_FK")==null)? "" : "Sản phẩm";
							madoituong_NO = (rsTK.getString("SANPHAM_FK")==null)? "" : rsTK.getString("SANPHAM_FK");
							isNPPNo = "null";
							isNPPCo = "1";
							doituong_CO = "Khách hàng";
							madoituong_CO = rs.getString("KHACHHANG_FK");
							
				
							if(taikhoanCO.trim().length() <= 0 || taikhoanNO.trim().length() <= 0 )
							{
								msg = "Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
								System.out.println(msg);
								db.getConnection().rollback();
								return msg;
							}
									
							msg=util.Update_TaiKhoan_Diengiai_NPP_TUOINO (db, thang, nam, ngayghinhan, ngayghinhan, "Hóa đơn khác", Id, taikhoanNO, taikhoanCO, "", Double.toString(TONGSOTIEN),Double.toString(TONGSOTIEN), doituong_NO, madoituong_NO, doituong_CO,  madoituong_CO, "", "0", "", "100000", "NULL", "1", Double.toString(TONGSOTIEN), Double.toString(TONGSOTIEN), loaiDT + " - Tiền hàng", diengiai,isNPPNo,isNPPCo,maChungTu,maChungTu,thoihanno);
						
							if(msg.trim().length() > 0)
							{
								db.getConnection().rollback(); 
								return msg;
							}
						}

						rsTK.close();
						doituong_NO = "";
						madoituong_NO = "";
					}else{
						taikhoanNO = "";
						taikhoanCO = "";
						taikhoanNO_VAT = "";
						taikhoanCO_VAT = "";
					}
			
					if(TONGVAT < 0) TONGVAT = TONGVAT*(-1);
					loaiDT = "Hóa đơn điều chỉnh giảm";				
				}
				else if(rs.getString("doanhthu_fk").equals("100012")){ 
					//LOAI HD: 	
					//			Thu nhập bán phế liệu, phế phẩm

					query = "SELECT (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = KH.TAIKHOAN_FK AND CONGTY_FK = " + ctyId + ") AS TAIKHOAN_NO, " +
							"(SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = DT.TAIKHOAN_FK AND CONGTY_FK = " + ctyId + ") AS TAIKHOAN_CO, \n " +
							"(SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = KH.TAIKHOAN_FK AND CONGTY_FK = " + ctyId + ") AS TAIKHOAN_NO_VAT,  " +
							"(SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '33311000' AND CONGTY_FK = " + ctyId + " ) AS TAIKHOAN_CO_VAT \n " +														"FROM ERP_HOADONKHAC HD \n " +
							"INNER JOIN ERP_DOANHTHU DT ON HD.doanhthu_fk = DT.PK_SEQ \n " +
							"INNER JOIN NHAPHANPHOI KH ON KH.PK_SEQ = HD.KHACHHANG_FK \n " +
							"WHERE HD.PK_SEQ =  '" + Id + "' \n ";
			
					System.out.println("query chi tiet : " + query);
					ResultSet rsTK = db.get(query);
					if(rsTK != null){
						while(rsTK.next()){
							taikhoanNO =  rsTK.getString("TAIKHOAN_NO");
							taikhoanCO = rsTK.getString("TAIKHOAN_CO");
							
							taikhoanNO_VAT =  rsTK.getString("TAIKHOAN_NO_VAT");
							taikhoanCO_VAT = rsTK.getString("TAIKHOAN_CO_VAT");
							query = "SELECT DIENGIAI FROM ERP_DOANHTHU WHERE PK_SEQ = " + rs.getString("doanhthu_fk") + "";
							ResultSet ldt = db.get(query);
							if(ldt != null){
								ldt.next();
								loaiDT = ldt.getString("DIENGIAI");
								ldt.close();
							}else{
								loaiDT = "Doanh thu khác";
							}
							isNPPNo = "1";
							isNPPCo = "null";
							
							if(taikhoanCO.trim().length() <= 0 || taikhoanNO.trim().length() <= 0 )
							{
								msg = "Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
								System.out.println(msg);
								db.getConnection().rollback();
								return msg;
							}
									
							msg=util.Update_TaiKhoan_Diengiai_NPP_TUOINO (db, thang, nam, ngayghinhan, ngayghinhan, "Hóa đơn khác", Id, taikhoanNO, taikhoanCO, "", Double.toString(TONGSOTIEN),Double.toString(TONGSOTIEN), doituong_NO, madoituong_NO, doituong_CO,  madoituong_CO, "", "0", "", "100000", "NULL", "1", Double.toString(TONGSOTIEN), Double.toString(TONGSOTIEN), loaiDT + " - Tiền hàng", diengiai,isNPPNo,isNPPCo,maChungTu,maChungTu,thoihanno);
						
							if(msg.trim().length() > 0)
							{
								db.getConnection().rollback();
								return msg;
							}
						}
						rsTK.close();
					}else{
						taikhoanNO = "";
						taikhoanCO = "";
						taikhoanNO_VAT = "";
						taikhoanCO_VAT = "";
					}
					
					
					
								
				}
				
				else if(rs.getString("doanhthu_fk").equals("100003")) {
					//LOAI HD: 	Doanh thu cho thuê nhà, kho, kiot
					
					query = "SELECT (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = KH.TAIKHOAN_FK AND CONGTY_FK = " + ctyId + ") AS TAIKHOAN_KH_NO_CHUAVAT, " +
					" HD.TAIKHOANDOANHTHU_FK AS TAIKHOANCO_DOANHTHU ,\n"+ 
					"(SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = KH.TAIKHOAN_FK AND CONGTY_FK = " + ctyId + ") AS TAIKHOAN_KH_NO_CHUAVAT ,\n"+
					"(SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '33311000' AND CONGTY_FK = " + ctyId + " ) AS TAIKHOAN_KH_CO_VAT \n " 		+					
					"FROM ERP_HOADONKHAC HD \n " +
					"  INNER JOIN  NHAPHANPHOI KH ON KH.PK_SEQ = HD.KHACHHANG_FK  \n "  +
					"WHERE HD.PK_SEQ =  '" + Id + "' \n ";
					
					System.out.println("query chi tiet : " + query);
					ResultSet rsTK = db.get(query);
					if(rsTK != null){
						while(rsTK.next()){
							taikhoanNO =  rsTK.getString("TAIKHOAN_KH_NO_CHUAVAT")==null?"":rsTK.getString("TAIKHOAN_KH_NO_CHUAVAT");
							taikhoanCO = rsTK.getString("TAIKHOANCO_DOANHTHU")==null?"":rsTK.getString("TAIKHOANCO_DOANHTHU");
					
							taikhoanNO_VAT =  rsTK.getString("TAIKHOAN_KH_NO_CHUAVAT")==null?"":rsTK.getString("TAIKHOAN_KH_NO_CHUAVAT");
							taikhoanCO_VAT = rsTK.getString("TAIKHOAN_KH_CO_VAT")==null?"":rsTK.getString("TAIKHOAN_KH_CO_VAT");
							if(taikhoanCO.trim().length() <= 0 || taikhoanNO.trim().length() <= 0 )
							{
								msg = "Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
								System.out.println(msg);
								db.getConnection().rollback();
								return msg;
							}
							isNPPNo = "1";
							isNPPCo = "null";		
							msg=util.Update_TaiKhoan_Diengiai_NPP_TUOINO (db, thang, nam, ngayghinhan, ngayghinhan, "Hóa đơn khác", Id, taikhoanNO, taikhoanCO, "", Double.toString(TONGSOTIEN),Double.toString(TONGSOTIEN), doituong_NO, madoituong_NO, doituong_CO,  madoituong_CO, "", "0", "", "100000", "NULL", "1", Double.toString(TONGSOTIEN), Double.toString(TONGSOTIEN), loaiDT + " - Tiền hàng", diengiai,isNPPNo,isNPPCo,maChungTu,maChungTu,thoihanno);
						
							if(msg.trim().length() > 0)
							{
								db.getConnection().rollback();
								return msg;
							}
						}
						rsTK.close();
					}else{
						taikhoanNO = "";
						taikhoanCO = "";
						taikhoanNO_VAT = "";
						taikhoanCO_VAT = "";
					}
					
					
				}

				
				
				
				/*Tiền hàng định khoản từng dòng ( đối với trường hợp tăng giảm ) ==> comment lại
				 * if(TONGSOTIEN > 0)
				{
						
					if(taikhoanCO.trim().length() <= 0 || taikhoanNO.trim().length() <= 0 )
					{
						msg = "Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
						System.out.println(msg);
						db.getConnection().rollback();
						return msg;
					}
							
					msg=util.Update_TaiKhoan_Diengiai_NPP (db, thang, nam, ngayghinhan, ngayghinhan, "Hóa đơn khác", Id, taikhoanNO, taikhoanCO, "", Double.toString(TONGSOTIEN),Double.toString(TONGSOTIEN), doituong_NO, madoituong_NO, doituong_CO,  madoituong_CO, madoituong_CO, "0", "", "100000", "NULL", "1", Double.toString(TONGSOTIEN), Double.toString(TONGSOTIEN), loaiDT + " - Tiền hàng", diengiai,"1","null",sohoadon,sohoadon);
				
					if(msg.trim().length() > 0)
					{
						db.getConnection().rollback();
						return msg;
					}
				}*/
					
				// DINH KHOAN VAT
				if(TONGVAT != 0)
				{
					if(taikhoanCO_VAT.trim().length() <= 0 || taikhoanNO_VAT.trim().length() <= 0 )
					{
						msg = "Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
						System.out.println(msg);
						db.getConnection().rollback();
						return msg;
					}
//					msg = util.Update_TaiKhoan_KeToan (db, thang, nam, ngayghinhan, ngayghinhan, "Hóa đơn khác", Id, taikhoanNO_VAT, taikhoanCO_VAT, "", Double.toString(TONGVAT), Double.toString(TONGVAT), 
//							doituong_NO, madoituong_NO, doituong_CO, madoituong_CO,"0","", "", tiente_fk, Double.toString(TONGVAT), "1",  Double.toString(TONGVAT),  Double.toString(TONGVAT),loaiDT + " - VAT", "", diengiai,maChungTu, "1", "", "","", "");
//					
					msg=util.Update_TaiKhoan_Diengiai_NPP_TUOINO (db, thang, nam, ngayghinhan, ngayghinhan, "Hóa đơn khác", Id, taikhoanNO_VAT, taikhoanCO_VAT, "", Double.toString(TONGVAT),Double.toString(TONGVAT), doituong_NO, madoituong_NO, doituong_CO,  madoituong_CO, "", "0", "", "100000", "NULL", "1", Double.toString(TONGVAT),  Double.toString(TONGVAT), loaiDT + " - VAT", diengiai,isNPPNo,isNPPCo,maChungTu,maChungTu,thoihanno);

					if(msg.trim().length() > 0)
					{
						db.getConnection().rollback();
						return msg;
					}
				}
					
			}
			rs.close();
			
			if(!db.update("UPDATE ERP_HOADONkhac SET TRANGTHAI = '1' WHERE pk_seq = '" + Id + "' and trangthai=0"))
	    	{
				db.getConnection().rollback();
	    		msg = "Không thể cập nhật erp_hoadonkhac";
	    		return msg;
	    	}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
			
			//SYN QUA DMS			
			
		} 
    	catch (Exception e) 
    	{
    		e.printStackTrace();
//    		db.update("rollback");
    		//db.shutDown();
		}
//    	msg = util.Update_DoiTuong(db,"Hóa đơn khác", Id);
		return msg;
	}
	
	
	
	private String  boChotHoaDon(String ctyId, String Id, dbutils db)
	{
		String msg = "";

    	
		UtilityKeToan util = new UtilityKeToan();
		
    	try 
    	{
			db.getConnection().setAutoCommit(false);
			
			
			
			String query = "update ERP_HOADONKHAC set TRANGTHAI = '0' where PK_SEQ = '" + Id+ "' and trangthai=1";
			System.out.println("1.Cap nhat ERP_HOADONKHAC: " + query);
			
			if (db.updateReturnInt(query)!=1) {
				msg = "CTTHD1.1 Khong the xoa ERP_THUTIEN: " + query;
				db.getConnection().rollback();
			
			}
			
			
			query = "select month(NGAYGHINHAN) thang, year(NGAYGHINHAN) nam from ERP_HOADONKHAC WHERE PK_SEQ = " + Id + "";
			System.out.println("ngay thang " +query);
			ResultSet rs = db.get(query);
			if (rs != null)
			{
				if (rs.next())
				{
					util.setThang(rs.getString("thang"));
					util.setNam(rs.getString("nam"));
				}
				rs.close();
			}
			String loaiChungTu=" N'Hóa đơn khác'";
			msg=util.HuyUpdate_TaiKhoan(db, Id, loaiChungTu);
			if(msg.length()>0)
			{
				db.getConnection().rollback();
				
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
    	catch (Exception e) 
    	{
    		e.printStackTrace();
//    		db.update("rollback");
    		//db.shutDown();
		}
    	
		return msg;
	}

	private void getSearchQuery(HttpServletRequest request, IErpHoadonkhacList obj) 
	{
		Utility util = new Utility();
		
		String tennguoitao = request.getParameter("tennguoitao");
		if(tennguoitao == null)
			tennguoitao = "";
		tennguoitao=tennguoitao.trim();
		obj.setTennguoitao(tennguoitao);
		
		
		String ma = request.getParameter("magiamgiahangmua");
		if(ma == null)
			ma = "";
		obj.setMa(ma);
		
		String diengiai = request.getParameter("diengiai");
		if(diengiai == null)
			diengiai = "";
		obj.setDiengiai(diengiai);
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		String doanhthuId = request.getParameter("doanhthuId");
		if(doanhthuId == null)
			doanhthuId = "";
		obj.setKhoanmucDTId(doanhthuId);
		
		String sohoadon = request.getParameter("sohoadon");
		if(sohoadon == null)
			sohoadon = "";
		obj.setSohoadon(sohoadon);
		
		String khachhang = request.getParameter("khachhang");
		if(khachhang == null)
			khachhang = "";
		obj.setKhachhang(khachhang);
		
		String tungay = request.getParameter("tungay");
		if(tungay == null)
			tungay = "";
		obj.setTungay(tungay);
		
		String denngay = request.getParameter("denngay");
		if(denngay == null)
			denngay = "";
		obj.setDenngay(denngay);
		
		String maHD = request.getParameter("maHD");
		if(maHD == null)
			maHD = "";
		obj.setMa(maHD);
		
//		String sql = "select a.pk_seq, isnull(d.ten, d.tenchuNPP) as nccTen, isnull(e.ma,'') as poTen,   " +
//					 "a.trangthai, b.ten as nguoitao, a.ngaytao, c.ten as nguoisua, a.ngaysua ,a.sohoadon, a.ngayhoadon ,a.vat, " +
//					 "a.avat as tongtien, " +
//					 "(case a.doanhthu_fk " +
//					 " when '400002' then N'Hóa đơn điều chỉnh tăng' " +
//					 " when '400003' then N'Hóa đơn điều chỉnh giảm' " +
//					 " else g.diengiai end ) as doanhthu_fk \n " +
//					 "from ERP_HoaDonkhac a " +
//					 "inner join NhanVien b on a.nguoitao = b.pk_seq      " +
//					 "inner join nhanvien c on a.nguoisua = c.pk_seq " +
//					 "inner join NHAPHANPHOI d on a.khachhang_fk = d.pk_seq   " +
//					 "left join ERP_TRUNGTAMDOANHTHU e on a.trungtamdoanhthu_fk = e.pk_seq  " +
//					 "left join erp_hoadonkhac_sanpham f on f.hoadonkhac_fk=a.pk_seq " +
//					 "LEFT JOIN ERP_DOANHTHU g on a.doanhthu_fk = g.pk_seq \n "+
//					 "where a.pk_seq > 0 ";
//		
		/*if(ma.length() > 0)
			sql += " and a.magiamgiahangmua like N'%" + ma + "%' ";*/
//		if(tennguoitao.length() > 0)
//			sql += " and b.ten like N'%" + tennguoitao + "%' ";
//		if(diengiai.length() > 0)
//			sql += " and a.diengiai like N'%" + diengiai + "%' ";
//		
//		if(trangthai.length() > 0)
//			sql += " and a.trangthai = '" + trangthai + "' ";
//		
//		if(sohoadon.length() > 0)
//		{
//			sql += " and a.sohoadon like N'%" + sohoadon + "%' ";
//		}
//		if(doanhthuId.length() > 0)
//		{
//			sql += " and a.doanhthu_fk = " + doanhthuId + " ";
//		}
//		if(khachhang.length() > 0)
//		{
//			sql += " and (dbo.ftBoDau(d.ten)) like N'%" + util.replaceAEIOU(khachhang) + "%'  " +
//					"or  dbo.ftBoDau(d.ma) like N'%"+ util.replaceAEIOU(khachhang) +"%' or dbo.ftBoDau(d.pk_seq) like N'%"+ util.replaceAEIOU(khachhang) +"%'";
//			
//		}
//		//sql += " order by a.pk_seq desc ";
//		
//		System.out.println(sql);
//		return sql;
	}
	
	public static void main(String []arg)
	{
			Utility util = new Utility();
			dbutils db = new dbutils();
			String query= "SELECT PK_SEQ, CONGTY_FK  FROM ERP_HOADONKHAC WHERE TRANGTHAI=1  \n";
			ResultSet rs= db.get(query);
			try
			{
				while(rs.next())
				{
					String id= rs.getString("PK_SEQ");
					String congtyId= rs.getString("CONGTY_FK");
//					String nppId= rs.getString("NPP_FK");
					String msg="";
					msg=util.HuyUpdate_TaiKhoan(db, id, "Hóa đơn khác");
					if(msg!=null&& msg.length()>0)
					{
						System.out.println(" sai tiep cmnr");
					}
					msg=ChotHoaDon(congtyId,id, db);
					if(msg!=null&& msg.length()>0)
					{
						System.out.println(" sai tiep cmnr" +msg);
					}
				}rs.close();
				db.shutDown();
				
			}catch (Exception e) {
				System.out.println("LOI CON ME NO ROI");
			}
			
		
	}
	
	


}
