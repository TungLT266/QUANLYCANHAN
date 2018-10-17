package geso.traphaco.erp.servlets.hoadonphelieu;
 

import geso.traphaco.erp.beans.hoadonphelieu.IErpHoadonphelieu;
import geso.traphaco.erp.beans.hoadonphelieu.IErpHoadonphelieuList;
import geso.traphaco.erp.beans.hoadonphelieu.imp.ErpHoadonphelieu;
import geso.traphaco.erp.beans.hoadonphelieu.imp.ErpHoadonphelieuList;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.db.sql.Idbutils;
import geso.traphaco.center.util.GiuDieuKienLoc;
import geso.traphaco.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpHoadonphelieuSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	
    public ErpHoadonphelieuSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
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
	    
	    IErpHoadonphelieuList obj = new ErpHoadonphelieuList();
	    
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    obj.setUserId(userId);
	    obj.setCongtyId(ctyId);
	    
	    String action = util.getAction(querystring);
	    String khlId = util.getId(querystring);
	    String msg = "";
	    
	    if(action.trim().equals("delete"))
	    {
	    	dbutils db = new dbutils();
	    	
	    	if(!db.update("UPDATE ERP_HOADONPHELIEU SET TRANGTHAI = '2' where pk_seq = '" + khlId + "' and trangthai=0"))
	    	{
	    		msg = "Không thể xóa erp_hoadonphelieu"; 
    		   
	    	}
	    	
	    	obj.init("");
	    	obj.setMsg(msg);
	    	GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
    		session.setAttribute("obj", obj);
    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoaDonPheLieu.jsp";
    		response.sendRedirect(nextJSP);
	    	db.shutDown();
	    }
	    
	    else  if(action.trim().equals("chot"))
	    {
	    	GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
	    	msg = ChotHoaDon(ctyId,khlId);
	    	 obj.init("");
	 	    obj.setMsg(msg);
	 		session.setAttribute("obj", obj);
	 	    
	 	    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoaDonPheLieu.jsp";
	 		response.sendRedirect(nextJSP);
	    }
	    else{
	    	GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
		    obj.init("");
		    obj.setMsg(msg);
			session.setAttribute("obj", obj);
		    
		    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoaDonPheLieu.jsp";
			response.sendRedirect(nextJSP);
	    }
	}

	private static String ChotHoaDon(String congTyId,String Id)
	{
		String msg = "";
		Idbutils db = new dbutils();
    	
		Utility util = new Utility();
		
    	try 
    	{
			db.getConnection().setAutoCommit(false);
			
			String query = 
			
			" SELECT a.doanhthu_fk, ISNULL(a.diengiai,'') as diengiai, isnull(a.loaick,'')as loaick, a.ngayhoadon, a.vat, isnull(c.ma,'') as trungtamdt, b.PK_SEQ KHACHHANG_FK, " +
			"		 case a.doanhthu_fk " +
			"		 when 400004 then isnull(a.avat,0) " +
			"		 when 400005 then isnull(a.avat,0) " +
			"		 else ( select SUM( thanhtien ) from erp_hoadonphelieu_sanpham where hoadonphelieu_fk = a.pk_seq ) end as SOTIEN \n " +			
			" FROM ERP_HoaDonPheLieu a " +
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
			
			String doituong_CO = "";
			String madoituong_CO = "";
			String loaiDT = "";
			while(rs.next())
			{

				double TONGSOTIEN = Math.round(rs.getDouble("SOTIEN"));
				double TONGVAT = Math.round(TONGSOTIEN * rs.getDouble("VAT") / 100 );
				doituong_NO = "Khách hàng";
				madoituong_NO = rs.getString("KHACHHANG_FK");
				String diengiai = rs.getString("diengiai");

				
				doituong_CO = "";
				madoituong_CO = "";
				
				if(rs.getString("doanhthu_fk").equals("400000")) // LOAI HD: HD CHIET KHAU
				{					
					query = "SELECT CASE WHEN HD.KBH_FK = 100000 THEN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '52110000') \n " + //GT
							"		ELSE CASE WHEN  HD.KBH_FK = 100001 THEN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '52120000') \n " +  //MT
							"		ELSE CASE WHEN  HD.KBH_FK = 100007 THEN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '52140000') \n " +  //XK
							"		ELSE (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '52130000') \n " +									   //Khác
							"		END END END AS TAIKHOAN_NO, KH.TAIKHOAN_FK AS TAIKHOAN_CO, \n " +							
							"		(SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '33311000') AS TAIKHOAN_NO_VAT, \n " +
							"		KH.TAIKHOAN_FK AS TAIKHOAN_CO_VAT \n " +
							
							"FROM ERP_HOADONPHELIEU HD \n " +
							"INNER JOIN NHAPHANPHOI KH ON KH.PK_SEQ = HD.KHACHHANG_FK \n " +
							"WHERE HD.PK_SEQ = '" + Id + "' \n ";
					
					ResultSet rsTK = db.get(query);
					if(rsTK != null){
						rsTK.next();
						taikhoanNO =  rsTK.getString("TAIKHOAN_NO");
						taikhoanCO = rsTK.getString("TAIKHOAN_CO");
						taikhoanNO_VAT = rsTK.getString("TAIKHOAN_NO_VAT");
						taikhoanCO_VAT = rsTK.getString("TAIKHOAN_CO_VAT");
						rsTK.close();
					}else{
						taikhoanNO = "";
						taikhoanCO = "";
						taikhoanNO_VAT = "";
						taikhoanCO_VAT = "";
					}

					if(TONGSOTIEN < 0) TONGSOTIEN = TONGSOTIEN*(-1);
					if(TONGVAT < 0) TONGVAT = TONGVAT*(-1);			
					loaiDT = "Hóa đơn chiết khấu";
					
				}else if (rs.getString("doanhthu_fk").equals("400001")) // LOAI HD: THU HOI CK
				{
					query = "SELECT KH.TAIKHOAN_FK AS TAIKHOAN_NO, " +
							"		CASE WHEN HD.KBH_FK = 100000 THEN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '52110000') \n " + //GT
							"		ELSE CASE WHEN  HD.KBH_FK = 100001 THEN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '52120000') \n " +  //MT
							"		ELSE CASE WHEN  HD.KBH_FK = 100007 THEN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '52140000') \n " +  //XK
							"		ELSE (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '52130000') \n " +									   //Khác
							"		END END END AS TAIKHOAN_CO,  \n " +							
							"		KH.TAIKHOAN_FK AS TAIKHOAN_NO_VAT,  \n " +
							"		(SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '33311000') AS TAIKHOAN_CO_VAT " +
														
							"FROM ERP_HOADONPHELIEU HD \n " +
							"INNER JOIN NHAPHANPHOI KH ON KH.PK_SEQ = HD.KHACHHANG_FK \n " +
							"WHERE HD.PK_SEQ = '" + Id + "' \n ";
					
					ResultSet rsTK = db.get(query);
					if(rsTK != null){
						rsTK.next();
						taikhoanNO =  rsTK.getString("TAIKHOAN_NO");
						taikhoanCO = rsTK.getString("TAIKHOAN_CO");
						
						taikhoanNO_VAT =  rsTK.getString("TAIKHOAN_NO_VAT");
						taikhoanCO_VAT = rsTK.getString("TAIKHOAN_CO_VAT");
						rsTK.close();
					}else{
						taikhoanNO = "";
						taikhoanCO = "";
						taikhoanNO_VAT = "";
						taikhoanCO_VAT = "";
					}
					
					loaiDT = "Thu hồi chiết khấu";

				}
				else if(rs.getString("doanhthu_fk").equals("400002")) //LOAI HD: HÓA ĐƠN ĐIỀU CHỈNH TĂNG
				{
					query = "SELECT KH.TAIKHOAN_FK AS TAIKHOAN_NO, HD.TAIKHOANDOANHTHU_FK AS TAIKHOAN_CO, \n " +
							"		KH.TAIKHOAN_FK AS TAIKHOAN_NO_VAT,  " +
							"		(SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '33311000') AS TAIKHOAN_CO_VAT \n " +
							"FROM ERP_HOADONPHELIEU HD \n " +
							"INNER JOIN NHAPHANPHOI KH ON KH.PK_SEQ = HD.KHACHHANG_FK \n " +
							"WHERE HD.PK_SEQ =  '" + Id + "' \n ";
					
					System.out.println(query);
					ResultSet rsTK = db.get(query);
					if(rsTK != null){
						rsTK.next();
						taikhoanNO =  rsTK.getString("TAIKHOAN_NO");
						taikhoanCO = rsTK.getString("TAIKHOAN_CO");
						
						taikhoanNO_VAT =  rsTK.getString("TAIKHOAN_NO_VAT");
						taikhoanCO_VAT = rsTK.getString("TAIKHOAN_CO_VAT");
						rsTK.close();
					}else{
						taikhoanNO = "";
						taikhoanCO = "";
						taikhoanNO_VAT = "";
						taikhoanCO_VAT = "";
					}
					loaiDT = "Hóa đơn điều chỉnh tăng";				
				}
				else if(rs.getString("doanhthu_fk").equals("400003")) //LOAI HD: HÓA ĐƠN ĐIỀU CHỈNH GIẢM
				{
					query = "SELECT HD.TAIKHOANDOANHTHU_FK AS TAIKHOAN_NO, KH.TAIKHOAN_FK AS TAIKHOAN_CO, \n " +							
							"		(SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '33311000') AS TAIKHOAN_NO_VAT, \n " +
							"		KH.TAIKHOAN_FK AS TAIKHOAN_CO_VAT " +
							
							"FROM ERP_HOADONPHELIEU HD \n " +
							"INNER JOIN NHAPHANPHOI KH ON KH.PK_SEQ = HD.KHACHHANG_FK \n " +

							"WHERE HD.PK_SEQ =  '" + Id + "' \n ";
			
					ResultSet rsTK = db.get(query);
					if(rsTK != null){
						rsTK.next();
						taikhoanNO =  rsTK.getString("TAIKHOAN_NO");
						taikhoanCO = rsTK.getString("TAIKHOAN_CO");
				
						taikhoanNO_VAT =  rsTK.getString("TAIKHOAN_NO_VAT");
						taikhoanCO_VAT = rsTK.getString("TAIKHOAN_CO_VAT");
						rsTK.close();
					}else{
						taikhoanNO = "";
						taikhoanCO = "";
						taikhoanNO_VAT = "";
						taikhoanCO_VAT = "";
					}
			
					if(TONGSOTIEN < 0) TONGSOTIEN = TONGSOTIEN*(-1);
					if(TONGVAT < 0) TONGVAT = TONGVAT*(-1);
					loaiDT = "Hóa đơn điều chỉnh giảm";				
				}
				else if(rs.getString("doanhthu_fk").equals("400004")) //LOAI HD: TĂNG GIẢM THUẾ SUẤT
				{
					TONGVAT = TONGSOTIEN;

					if(TONGVAT > 0)// HÓA ĐƠN TĂNG THUẾ
					{
						
						query = "SELECT KH.TAIKHOAN_FK AS TAIKHOAN_NO_VAT,  \n " +								
								"		(SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '33311000') AS TAIKHOAN_CO_VAT \n " +								
								"FROM ERP_HOADONPHELIEU HD \n " +
								"INNER JOIN NHAPHANPHOI KH ON KH.PK_SEQ = HD.KHACHHANG_FK \n " +
								"WHERE HD.PK_SEQ =  '" + Id + "' \n ";
						
						ResultSet rsTK = db.get(query);
						if(rsTK != null){
							rsTK.next();
							taikhoanNO =  "";
							taikhoanCO = "";
					
							taikhoanNO_VAT =  rsTK.getString("TAIKHOAN_NO_VAT");
							taikhoanCO_VAT = rsTK.getString("TAIKHOAN_CO_VAT");
							rsTK.close();
						}else{
							taikhoanNO = "";
							taikhoanCO = "";
							taikhoanNO_VAT = "";
							taikhoanCO_VAT = "";
						}
							
						loaiDT = "Hóa đơn điều chỉnh tăng thuế suất hóa đơn bán hàng";
					}
					else{// HÓA ĐƠN GIẢM THUẾ
						query = "SELECT (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '33311000') AS TAIKHOAN_NO_VAT, \n " +								
								"		KH.TAIKHOAN_FK AS TAIKHOAN_CO_VAT \n " +						
								"FROM ERP_HOADONPHELIEU HD \n " +
								"INNER JOIN NHAPHANPHOI KH ON KH.PK_SEQ = HD.KHACHHANG_FK \n " +
								"WHERE HD.PK_SEQ =  '" + Id + "' \n ";
				
						ResultSet rsTK = db.get(query);
						if(rsTK != null){
							rsTK.next();
							taikhoanNO =  "";
							taikhoanCO = "";
			
							taikhoanNO_VAT =  rsTK.getString("TAIKHOAN_NO_VAT");
							taikhoanCO_VAT = rsTK.getString("TAIKHOAN_CO_VAT");
							rsTK.close();
						}else{
							taikhoanNO = "";
							taikhoanCO = "";
							taikhoanNO_VAT = "";
							taikhoanCO_VAT = "";
						}
						loaiDT = "Hóa đơn điều chỉnh giảm thuế suất hóa đơn bán hàng";
					}		
					TONGSOTIEN = 0;
					if(TONGVAT <0) TONGVAT = TONGVAT*(-1);
				}
				else if(rs.getString("doanhthu_fk").equals("400005")) //Hóa đơn điều chỉnh thuế suất hóa đơn khuyến mãi 
				{
					
					TONGVAT = TONGSOTIEN;
					if(TONGVAT > 0)// Điều chỉnh tăng thuế suất hóa đơn khuyến mãi
					{
						query = "SELECT HD.TAIKHOANGHINO_FK AS TAIKHOAN_NO_VAT,  \n" +						
								"		(SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '33311000') AS TAIKHOAN_CO_VAT \n" +						
								"FROM ERP_HOADONPHELIEU HD \n" +
								"INNER JOIN NHAPHANPHOI KH ON KH.PK_SEQ = HD.KHACHHANG_FK \n" +
								"WHERE HD.PK_SEQ =  '" + Id + "' \n";
				
						ResultSet rsTK = db.get(query);
						if(rsTK != null){
							rsTK.next();
							taikhoanNO =  "";
							taikhoanCO = "";
			
							taikhoanNO_VAT =  rsTK.getString("TAIKHOAN_NO_VAT");
							taikhoanCO_VAT = rsTK.getString("TAIKHOAN_CO_VAT");
							rsTK.close();
						}else{
							taikhoanNO = "";
							taikhoanCO = "";
							taikhoanNO_VAT = "";
							taikhoanCO_VAT = "";
						}
						loaiDT = "Hóa đơn điều chỉnh tăng thuế suất hóa đơn khuyến mãi ";
					}
					else{// Điều chỉnh giảm thuế suất hóa đơn khuyến mãi
						query = "SELECT  (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '33311000') AS TAIKHOAN_NO_VAT, \n " +
								"		HD.TAIKHOANGHINO_FK AS TAIKHOAN_CO_VAT \n " +								
								"FROM ERP_HOADONPHELIEU HD \n " +
								"INNER JOIN NHAPHANPHOI KH ON KH.PK_SEQ = HD.KHACHHANG_FK \n " +
								"WHERE HD.PK_SEQ =  '" + Id + "' \n ";
		
						ResultSet rsTK = db.get(query);
						if(rsTK != null){
							rsTK.next();
							taikhoanNO =  "";
							taikhoanCO = "";
	
							taikhoanNO_VAT =  rsTK.getString("TAIKHOAN_NO_VAT");
							taikhoanCO_VAT = rsTK.getString("TAIKHOAN_CO_VAT");
							rsTK.close();
						}else{
							taikhoanNO = "";
							taikhoanCO = "";
							taikhoanNO_VAT = "";
							taikhoanCO_VAT = "";
						}
						loaiDT = "Hóa đơn điều chỉnh giảm thuế suất hóa đơn khuyến mãi ";
					}	
					
					TONGSOTIEN = 0;
					if(TONGVAT < 0) TONGVAT = TONGVAT*(-1);
					
				}else if(rs.getString("doanhthu_fk").equals("100011")) //Hóa đơn thanh lý tài sản cố định
				{
					query ="SELECT THANHLYTAISAN_FK FROM ERP_HOADONPHELIEU_THANHLY WHERE HOADONPHELIEU_FK ="+Id ;
					ResultSet tlRs=db.get(query);
					String dtltsId="";
					if(tlRs.next())
					{
						dtltsId=tlRs.getString("THANHLYTAISAN_FK");
					}
					tlRs.close();
					
					query ="SELECT NGAYHOADON FROM ERP_HOADONPHELIEU WHERE PK_SEQ ="+Id ;
					tlRs=db.get(query);
					String ngayghinhan="";
					if(tlRs.next())
					{
						ngayghinhan=tlRs.getString("NGAYHOADON");
					}
					tlRs.close();
					if(ngayghinhan.length()<=0)
					{
						db.getConnection().rollback();
						msg= "Vui lòng xem lại ngày hóa đơn";
						return msg;
					}
				
					
					query = "Select loai from erp_thanhlytaisan where pk_seq = " +dtltsId ;
			  	  	ResultSet loairs= db.get(query);
			  	  	String loai="";
			  	  	if(loairs!=null)
			  	  	{
			  	  		try {
							while(loairs.next())
							{
								loai= loairs.getString("loai");
							}
							loairs.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			  	  		
			  	  	}
			  	  	if (loai.equals("1"))
			  	  	{
			  	  		try {
									
					     query = " SELECT TL.KHACHHANG_FK ,TS.TAISAN_FK,TL.NGAY " +
								     	" FROM ERP_THANHLYTAISAN_TAISAN TS " +
								     	" INNER JOIN  ERP_THANHLYTAISAN TL ON TS.THANHLYTAISAN_FK = TL.PK_SEQ " +
								     	" WHERE TL.PK_SEQ = " + dtltsId + " ";
				  	    ResultSet rslaytS = db.get(query);
				  	    if(rslaytS != null)
				  	    {
				  	    	while(rslaytS.next())
							{
								String tsId= rslaytS.getString("TAISAN_FK");
								String khId= rslaytS.getString("KHACHHANG_FK");
								String ngay= rslaytS.getString("NGAY");
								
							    query = "Update ERP_THANHLYTAISAN SET TRANGTHAI='2' "+
							    		"WHERE PK_SEQ ='"+ dtltsId +"' ";
							    System.out.println("Update THANHLYTAISAN " +query);
								if(!db.update(query))
								{
									db.getConnection().rollback();
									msg= "Lỗi update THANH LY TAI SAN ";
									return msg;
								}
							    
								String taikhoanCo_sohieu="";
								String taikhoanNo_sohieu="";
								String tiente_fk = "100000";
								 
												
								query="SELECT TLTS.TAISAN_FK,TL.NGAY AS NGAYGHINHAN,TL.TYGIAQUYDOI, (TLTS.SOLUONG* TLTS.DONGIA ) AS DOANHTHU, (TL.TONGTIENAVAT-TL.TONGTIENBVAT)AS THUE, \n"+
								"	(SELECT SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE NPP_FK = 1 AND SOHIEUTAIKHOAN = (SELECT ISNULL(TAIKHOAN_FK,'') FROM NHAPHANPHOI WHERE PK_SEQ =  TL.KHACHHANG_FK)) AS sohieutaikhoanNO_KH,  \n"+
								"	(SELECT SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE NPP_FK = 1 AND SOHIEUTAIKHOAN LIKE '711100%') AS sohieutaikhoanCO_KH,  \n"+
								"	(SELECT SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE NPP_FK = 1 AND SOHIEUTAIKHOAN LIKE '33313000%') AS sohieutaikhoanCO_VAT,  \n"+
								"	 ISNULL(KHAUHAOLKTT.KhauHaoLuyKeThucTe,0) + ISNULL(TSCD.GIATRIDAKHAUHAO,0) AS KhauHaoLuyKeThucTe\n" +
								"	 ,(isNull(TSCD.THANHTIEN, 0) \n" +
								"		+ ISNULL(THAYDOI.GIATRIDC,0)  \n" +
								"	- ISNULL(TSCD.GIATRIDAKHAUHAO,0) - isnull(KHAUHAOLKTT.KhauHaoLuyKeThucTe,0))*TLTS.SOLUONG  AS GIATRICL, \n"+
								"  TKKH.SOHIEUTAIKHOAN as sohieutaikhoanNOKH,\n"+
								"  (SELECT SOHIEUTAIKHOAN \n" +
								"   FROM ERP_TAIKHOANKT \n" +
								"   WHERE PK_SEQ IN (select taikhoan_fk from Erp_LOAITAISAN where pk_seq= TSCD.LOAITAISAN_FK)) as sohieutaikhoanTS  \n"+                           
								"	FROM ERP_THANHLYTAISAN_TAISAN TLTS \n" +
								"   INNER JOIN ERP_THANHLYTAISAN TL ON TLTS.THANHLYTAISAN_FK= TL.PK_SEQ \n"+
								"	LEFT JOIN 	\n" +
								"   ( \n" +
								"		SELECT TaiSan_FK, SUM(khauHao) as KhauHaoLuyKeThucTe \n"+
								"		FROM ERP_KHAUHAOTAISAN \n"+
								"		WHERE TaiSan_FK=" + tsId + "\n"+
								"		GROUP BY TaiSan_FK  \n" +
								"   ) KHAUHAOLKTT ON TLTS.TAISAN_FK= KHAUHAOLKTT.TaiSan_FK \n"+
								"   LEFT JOIN " +
								"   ( " +
								"		SELECT  TSCD_FK, SUM(GIATRI) as GIATRIDC \n"+
								"		FROM ERP_TAISANCODINH_DIEUCHINH \n"+
								"		WHERE TSCD_FK=" +tsId+ " AND NGAYDIEUCHINH <= '" +ngay +"' \n"+
								"		GROUP BY TSCD_FK " +
								"   ) THAYDOI ON THAYDOI.TSCD_FK = TLTS.TAISAN_FK " +
								"	LEFT JOIN   ERP_TAISANCODINH TSCD ON TLTS.TAISAN_FK=TSCD.PK_SEQ    \n" +
								"	LEFT JOIN ERP_LOAITAISAN LTSCD ON TSCD.loaiTaiSan_FK = LTSCD.PK_SEQ   \n" +
								"	LEFT JOIN ERP_TAIKHOANKT TKKH ON LTSCD.taiKhoanKH_FK = TKKH.PK_SEQ   \n" +
								"WHERE TL.PK_SEQ= "+dtltsId+" \n";
								System.out.println(" Cài kế toán "+query);
								ResultSet Rskt= db.get(query);
								
								if(Rskt!= null)
								{
									while(Rskt.next())
									{
									
										String	thang= ngayghinhan.substring(5, 7);
										String	nam= ngayghinhan.substring(0, 4);
									 
										Double totalDT= Rskt.getDouble("DOANHTHU");
										Double Thue= Rskt.getDouble("THUE");
										Double gtconlai= Rskt.getDouble("GIATRICL");
										Double khauhaoLK= Rskt.getDouble("KhauHaoLuyKeThucTe");
									 
									 //cai kt Doanh thu

		  								taikhoanNo_sohieu= Rskt.getString("sohieutaikhoanNO_KH");
		  								taikhoanCo_sohieu=Rskt.getString("sohieutaikhoanCO_KH");
										if(taikhoanNo_sohieu == null || taikhoanNo_sohieu.trim().length() <= 0 || taikhoanCo_sohieu == null || taikhoanCo_sohieu.trim().length() <= 0 )
										{
										    msg	= "D1.2 Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
											db.getConnection().rollback();
											return msg;
										}
										 msg = util.Update_TaiKhoan_TheoSoHieu_ThemDoiTuong(db, congTyId, thang, nam, Rskt.getString("NGAYGHINHAN"), Rskt.getString("NGAYGHINHAN"), "Hóa đơn thanh lý TSCĐ", Id, taikhoanNo_sohieu,
												 taikhoanCo_sohieu, "NULL", Double.toString(totalDT), Double.toString(totalDT), "Khách hàng", khId, "0", "", "", "", "", 
												 "", tiente_fk, "", "1", Double.toString(totalDT), Double.toString(totalDT), "","1","");	
											
										
										if(msg.trim().length() > 0)
										{
											db.getConnection().rollback();
											return "D1.3 " + msg;
										}
											/*Update_TaiKhoan_TheoSoHieu(dbutils db, String thang, String nam, String ngaychungtu, String ngayghinhan, String loaichungtu, String sochungtu, String taikhoanNO_SoHieu, String taikhoanCO_SoHieu, String NOIDUNGNHAPXUAT_FK, 
											 * String NO, String CO, String DOITUONG,  
													String MADOITUONG, String LOAIDOITUONG, String SOLUONG, String DONGIA, String TIENTEGOC_FK, String DONGIANT, String TIGIA_FK, String TONGGIATRI, String TONGGIATRINT, String khoanmuc)
									 */
									//cai kt Tien thue
									 
										 taikhoanNo_sohieu= Rskt.getString("sohieutaikhoanNO_KH");
			  							taikhoanCo_sohieu=Rskt.getString("sohieutaikhoanCO_VAT");
										 
										 if(taikhoanNo_sohieu == null || taikhoanNo_sohieu.trim().length() <= 0 || taikhoanCo_sohieu.trim().length() <= 0 )
										 {
										    msg	= "D1.4 Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
											db.getConnection().rollback();
											return msg;
										 }																
										 msg = util.Update_TaiKhoan_TheoSoHieu_ThemDoiTuong(db, congTyId, thang, nam, Rskt.getString("NGAYGHINHAN"), Rskt.getString("NGAYGHINHAN"), "Hóa đơn thanh lý TSCĐ", Id, taikhoanNo_sohieu,
												 taikhoanCo_sohieu, "NULL", Double.toString(Thue), Double.toString(Thue), "Khách hàng", khId, "0", "", "", "", "", 
												 "", tiente_fk, "", "1", Double.toString(Thue), Double.toString(Thue), "","1","");
										 if(msg.trim().length() > 0)
										 {
											db.getConnection().rollback();
											return "D1.5 " + msg;
										 }				 
									}
									Rskt.close();
								}
								
							}
				  	    	rslaytS.close();
				  	     }
							
								
						  
						} catch (Exception e) {
							try 
							{
								db.getConnection().rollback();	
								e.printStackTrace();
								msg = "Lỗi chốt hóa đơn thanh lý tài sản ";
								return msg;
								
							} catch (Exception e2) {
								
								msg = "Lỗi chốt hóa đơn thanh lý tài sản ";
								db.getConnection().rollback();	
								return msg;
							}
						}
						}
			  	  	else
			  	  		if(loai.equals("2"))
			  	  		{
			  	  		try {
	
			  							
			  			    query = " SELECT TL.KHACHHANG_FK ,TS.CCDC_FK " +
			  						     	" FROM ERP_THANHLYTAISAN_TAISAN TS " +
			  						     	" INNER JOIN  ERP_THANHLYTAISAN TL ON TS.THANHLYTAISAN_FK = TL.PK_SEQ " +
			  						     	" WHERE TL.PK_SEQ = " + dtltsId + " ";
			  		  	    ResultSet rslaytS = db.get(query);
			  		  	    if(rslaytS != null)
			  		  	    {
			  		  	    	while(rslaytS.next())
			  					{
			  						String ccId= rslaytS.getString("CCDC_FK");
			  						String khId= rslaytS.getString("KHACHHANG_FK");
			  						
			  					    query = "Update ERP_THANHLYTAISAN SET TRANGTHAI='2' "+
			  					    		"WHERE PK_SEQ ='"+ dtltsId +"' ";
			  					    System.out.println("Update THANHLYTAISAN " +query);
			  						if(!db.update(query))
			  						{
			  							db.getConnection().rollback();
			  							msg= "Lỗi update THANH LY TAI SAN ";
			  							return msg;
			  						}
			  					    
			  						String taikhoanCo_sohieu="";
			  						String taikhoanNo_sohieu="";
			  						String tiente_fk = "100000";
			  						 
			  										
			  						query= "SELECT TLTS.CCDC_FK,TL.TYGIAQUYDOI, (TLTS.SOLUONG* TLTS.DONGIA ) AS DOANHTHU , (TL.TONGTIENAVAT-TL.TONGTIENBVAT)AS THUE,  \n "+
											"	(SELECT SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE NPP_FK = 1 AND SOHIEUTAIKHOAN = (SELECT ISNULL(TAIKHOAN_FK,'') FROM NHAPHANPHOI WHERE PK_SEQ =  TL.KHACHHANG_FK)) AS sohieutaikhoanNO_KH,  \n"+
											"	(SELECT SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE NPP_FK = 1 AND SOHIEUTAIKHOAN LIKE '711100%') AS sohieutaikhoanCO_KH,  \n"+
											"	(SELECT SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE NPP_FK = 1 AND SOHIEUTAIKHOAN LIKE '33313000%') AS sohieutaikhoanCO_VAT,  \n"+
			 						"	 KHAUHAOLKTT.KhauHaoLuyKe,(ISNULL(CCDC.TICHLUYDAUKY,CCDC.THANHTIEN) - isnull(KHAUHAOLKTT.KhauHaoLuyKeThucTe,0)) AS GIATRICL, \n "+
			 						" 		TK3.SOHIEUTAIKHOAN as sohieutaikhoanNOKH,         \n "+                  
			 						"  (SELECT SOHIEUTAIKHOAN  \n "+
			 						"   FROM ERP_TAIKHOANKT  \n "+
			 						"   WHERE PK_SEQ IN (select taikhoan_fk from Erp_LOAICCDC where pk_seq= CCDC.LOAICCDC_FK)) as sohieutaikhoanTS        \n "+                     
			 						"	FROM ERP_THANHLYTAISAN_TAISAN TLTS  \n "+
			 						"   INNER JOIN ERP_THANHLYTAISAN TL ON TLTS.THANHLYTAISAN_FK= TL.PK_SEQ \n "+
			 						"	LEFT JOIN 	 \n "+
			 						"   (  \n "+
			 						"		SELECT TOP 1 CCDC_FK, KHAUHAOLUYKE \n "+
			 						"		FROM ERP_KHAUHAOCCDC \n "+
			 						"		WHERE \n "+
			 						"		CCDC_FK= " +ccId+ " AND \n "+
			 						"		KhauHao > 0 \n "+
			 						"		ORDER BY NAM DESC,THANG DESc  \n "+
			 						"   ) KHAUHAOLKTT ON TLTS.CCDC_FK= KHAUHAOLKTT.CCDC_FK \n "+
			 						"	LEFT JOIN   ERP_CONGCUDUNGCU CCDC ON TLTS.CCDC_FK=CCDC.PK_SEQ   \n "+
			 						"	left join Erp_LOAICCDC LCC ON LCC.pk_seq=CCDC.LOAICCDC_FK \n "+
			 						"	LEFT JOIN ERP_TAIKHOANKT TK3 ON LCC.taikhoan_fk= TK3.PK_SEQ \n "+
			 						"    WHERE TL.PK_SEQ= "+dtltsId+" ";
			 						
			  						System.out.println(" Cài kế toán "+query);
			  						ResultSet Rskt= db.get(query);
			  						
			  						if(Rskt!= null)
			  						{
			  							while(Rskt.next())
			  							{
			  							
			  								String	thang= ngayghinhan.substring(5, 7);
			  								String	nam= ngayghinhan.substring(0, 4);
			  							 
			  								Double totalDT= Rskt.getDouble("DOANHTHU");
			  								Double Thue= Rskt.getDouble("THUE");
			  								Double gtconlai= Rskt.getDouble("GIATRICL");
			  								Double khauhaoLK= Rskt.getDouble("KhauHaoLuyKe");
			  							 
			  							 //cai kt Doanh thu
			  							 
			  								taikhoanNo_sohieu= Rskt.getString("sohieutaikhoanNO_KH");
			  								taikhoanCo_sohieu= Rskt.getString("sohieutaikhoanCO_KH");
			  								 
			  								
			  								if(taikhoanNo_sohieu.trim().length() <= 0 || taikhoanCo_sohieu.trim().length() <= 0 )
			  								{
			  								    msg	= "Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
			  									db.getConnection().rollback();
			  									return msg;
			  								}
			  									
			  								if(totalDT>0)
			  								{
//			  									util.Update_TaiKhoan_TheoSoHieu_CAN(db, thang, nam, ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoanNO_SoHieu, taikhoanCO_SoHieu, NOIDUNGNHAPXUAT_FK, NO, CO, DOITUONG, MADOITUONG, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, khoanmuc)
				  								msg = util.Update_TaiKhoan_TheoSoHieu_CAN( db, thang, nam, ngayghinhan, ngayghinhan, "Hóa đơn thanh lý TSCĐ", Id, taikhoanNo_sohieu, taikhoanCo_sohieu, "", 
				  									  Double.toString(totalDT), Double.toString(totalDT), "Tài sản cố định", ccId, "0", "", "", tiente_fk, "", Rskt.getString("TYGIAQUYDOI"), Double.toString(totalDT), Double.toString(totalDT), "Thanh lý tài sản" );
				  								
				  								if(msg.trim().length() > 0)
				  								{
				  									db.getConnection().rollback();
				  									return msg;
				  								}
			  								}
			  									/*Update_TaiKhoan_TheoSoHieu(dbutils db, String thang, String nam, String ngaychungtu, String ngayghinhan, String loaichungtu, String sochungtu, String taikhoanNO_SoHieu, String taikhoanCO_SoHieu, String NOIDUNGNHAPXUAT_FK, 
			  									 * String NO, String CO, String DOITUONG,  
			  											String MADOITUONG, String LOAIDOITUONG, String SOLUONG, String DONGIA, String TIENTEGOC_FK, String DONGIANT, String TIGIA_FK, String TONGGIATRI, String TONGGIATRINT, String khoanmuc)
			  							 */
			  							//cai kt Tien thue
			  							 
			  								 taikhoanNo_sohieu= Rskt.getString("sohieutaikhoanNO_KH");
			  								taikhoanCo_sohieu= Rskt.getString("sohieutaikhoanCO_VAT");
			  								 
			  								 if(taikhoanNo_sohieu.trim().length() <= 0 || taikhoanCo_sohieu.trim().length() <= 0 )
			  								 {
			  								    msg	= "Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
			  									db.getConnection().rollback();
			  									return msg;
			  								 }		
			  								 if(Thue>0)
			  								 {
				  								 msg = util.Update_TaiKhoan_TheoSoHieu_CAN( db, thang, nam, ngayghinhan, ngayghinhan, "Hóa đơn thanh lý TSCĐ", Id, taikhoanNo_sohieu, taikhoanCo_sohieu, "", 
				  									   Double.toString(Thue), Double.toString(Thue), "Tài sản cố định", ccId, "0", "", "", tiente_fk, "", Rskt.getString("TYGIAQUYDOI"), Double.toString(Thue), Double.toString(Thue), "Thanh lý tài sản" );
				  								 if(msg.trim().length() > 0)
				  								 {
				  									db.getConnection().rollback();
				  									return msg;
				  								 }
			  								 }
			  							}
			  						}
			  					}
			  		  	    	
			  		  	    }
			  	  		}
			  	  		catch (Exception e) {
							e.printStackTrace();
							db.getConnection().rollback();
							return "Lỗi khi chốt hóa đơn thanh lý tài sản";
						} 
			  	  	}
					
				}
				
				
			}
			rs.close();
			
			if(!db.update("UPDATE ERP_HOADONPHELIEU SET TRANGTHAI = '1' WHERE pk_seq = '" + Id + "' and trangthai=0"))
	    	{
				db.getConnection().rollback();
	    		msg = "Không thể cập nhật erp_hoadonphelieu";
	    		return msg;
	    	}
			
			db.getConnection().commit();
			db.shutDown();
		} 
    	catch (Exception e) 
    	{
    		db.update("rollback");
    		System.out.println(e);
    		e.printStackTrace();
    		db.shutDown();
		}
    	
		return msg;
	}
	
	public  static void main(String[] args) {
		String[] ids = new String[] {"100001","100002","100003","100004","100005","100006","100007","100008","100010","100011","100012","100013","100014","100015","100016","100017","100018","100019","100020","100021","100022","100023","100024","100025","100026","100027","100028","100029","100030","100031","100032","100033","100034","100035","100036","100037","100038","100039","100040","100041","100042","100043","100044","100045","100046","100047","100048","100049","100050","100051","100052","100053","100054","100055","100056","100057","100058","100059","100060","100061","100062","100063","100064","100065","100066","100067","100068","100069","100070","100071"};
		
		for(int i = 0; i < ids.length; i++) {
//			ChotHoaDon(ids[i]);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out  = response.getWriter();
	    
	    HttpSession session = request.getSession();	
	    
	    out = response.getWriter();
	    Utility util = new Utility();
	    
	    String userId = util.antiSQLInspection(request.getParameter("userId"));  
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    
	    IErpHoadonphelieuList obj;
	    
		String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    
	    if(action.equals("new"))
	    {
    		IErpHoadonphelieu khl = new ErpHoadonphelieu();
    	
    		khl.setCongtyId(ctyId);
    		khl.setUserId(userId);
    		khl.createRS();
    	
	    	session.setAttribute("csxBean", khl);  	
    		session.setAttribute("userId", userId);
		
    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpHoaDonPheLieuNew.jsp");
	    }
	    else
	    {
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
	    		obj = new ErpHoadonphelieuList();
		    	obj.setCongtyId(ctyId);
			    obj.setUserId(userId);

	    		System.out.println( "trang ke tiep la" + Integer.parseInt(request.getParameter("nxtApprSplitting")));
	    		obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));

	    		this.getSearchQuery(request, obj);
		    	
		    	obj.init("");
		    	GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
		    	
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	
		    	session.setAttribute("obj", obj);
		    	
		    	response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpHoaDonPheLieu.jsp");	
		    }
	    	else{
	    	
	    	obj = new ErpHoadonphelieuList();
	    	obj.setCongtyId(ctyId);
		    obj.setUserId(userId);

	    	this.getSearchQuery(request, obj);
	    	
	    	obj.setUserId(userId);
	    	obj.init("");
	    	GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);	
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
		
    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpHoaDonPheLieu.jsp");
	    	}
	    }
	}
	
	private void getSearchQuery(HttpServletRequest request, IErpHoadonphelieuList obj) 
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
		
		
		
//		String sql = " select a.pk_seq, d.tenxuathd as nccTen, isnull(e.ma,'') as poTen,   " +
//					 "		  a.trangthai, b.ten as nguoitao, a.ngaytao, c.ten as nguoisua, a.ngaysua ,a.sohoadon, a.ngayhoadon ,a.vat," +
//					 " sum(a.avat) as tongtien, (case a.doanhthu_fk when '400000' then N'Hóa đơn chiết khấu' when '400001' then N'Thu hồi CK'" +
//					 "		   when '400002' then N'Hóa đơn điều chỉnh tăng ' when '400003'	then N'Hóa đơn điều chỉnh giảm ' when  '400004' then  N'Hóa đơn điều chỉnh thuế suất ' else g.diengiai end ) as doanhthu_fk \n " +
//					 "from ERP_HoaDonPheLieu a " +
//					 "inner join NhanVien b on a.nguoitao = b.pk_seq      " +
//					 "inner join nhanvien c on a.nguoisua = c.pk_seq inner join NHAPHANPHOI d on a.khachhang_fk = d.pk_seq   " +
//					 "left join ERP_TRUNGTAMDOANHTHU e on a.trungtamdoanhthu_fk = e.pk_seq  " +
//					 " left join erp_hoadonphelieu_sanpham f on f.hoadonphelieu_fk=a.pk_seq " +
//					  "		   LEFT JOIN ERP_DOANHTHU g on a.doanhthu_fk = g.pk_seq \n"+
//					 " where a.pk_seq > 0 ";
		
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
//		sql += " group by a.pk_seq, d.tenxuathd, e.ma , a.trangthai, b.ten ,a.ngaytao, c.ten , a.ngaysua ,a.sohoadon , ngayhoadon ,a.vat, a.doanhthu_fk, g.diengiai " ;
//		//sql += " order by a.pk_seq desc ";
//		
//		System.out.println(sql);
//		return sql;
	}
	
	

}
