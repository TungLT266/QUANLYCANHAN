package geso.traphaco.erp.servlets.thanhlytaisan;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.thanhlytaisan.IErpThanhlytaisan;
import geso.traphaco.erp.beans.thanhlytaisan.IErpThanhlytaisanList;
import geso.traphaco.erp.beans.thanhlytaisan.imp.ErpThanhlytaisan;
import geso.traphaco.erp.beans.thanhlytaisan.imp.ErpThanhlytaisanList;
import geso.traphaco.erp.db.sql.dbutils;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpThanhlytaisanSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpThanhlytaisanSvl() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		ErpThanhlytaisanList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    
	    String dtltsId = util.getId(querystring);
	    
	    obj = (ErpThanhlytaisanList) new ErpThanhlytaisanList();
	    obj.setCongtyId(ctyId);
	    
	    if (action.equals("delete"))
	    {	
	    	String msg = Delete(dtltsId);
	    	if(msg.length() > 0)
	    		obj.setmsg(msg);
	    }
	    else
	    {
	    	if(action.equals("duyet"))
	    	{
	    		String msg = Duyet(ctyId, dtltsId,userId);
		    	if(msg.length() > 0)
		    		obj.setmsg(msg);
	    	}
	    	else
	    	{
	    		//Chốt
		    	if(action.equals("hoantat"))
		    	{
		    		String msg = HoanTat(ctyId, dtltsId,userId);
			    	if(msg.length() > 0)
			    		obj.setmsg(msg);
		    	}
		    	else
		    	{
		    		
		    	}
	    	}
	    }
	    
	    obj.setUserId(userId);
	    obj.setCongtyId((String)session.getAttribute("congtyId"));
	    
	    obj.init("");
	    
		session.setAttribute("obj", obj);
				
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpThanhlytaisan.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpThanhlytaisanList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    
	    Utility util = new Utility();
	    
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(request.getParameter("userId")); 
	    
	    if(action.equals("Tao moi"))
	    {
	    	IErpThanhlytaisan dtltsBean = new ErpThanhlytaisan();
	    	dtltsBean.setCongtyId((String)session.getAttribute("congtyId"));
	    	dtltsBean.setUserId(userId);
	    	
	    	dtltsBean.createRs();
    		
	    	session.setAttribute("dtltsBean", dtltsBean);
    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpThanhlytaisanNew.jsp";
    		response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
	    		obj = new ErpThanhlytaisanList();
	    		obj.setCongtyId((String)session.getAttribute("congtyId"));
		    	String search = getSearchQuery(request, obj);
		    	
		    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
		    	obj.setUserId(userId);
		    	obj.init(search);
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpThanhlytaisan.jsp");	
		    }
	    	else
	    	{
		    	obj = new ErpThanhlytaisanList();
		    	obj.setUserId(userId);
		    	obj.setCongtyId((String)session.getAttribute("congtyId"));
		    	
		    	String search = getSearchQuery(request, obj);
		    	obj.init(search);
				
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
		
	    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpThanhlytaisan.jsp");  
	    	}
	    }
	    
	}

	private String getSearchQuery(HttpServletRequest request, IErpThanhlytaisanList obj)
	{
//		Utility util = new Utility();
		
		String query = " select a.PK_SEQ as dtltsId, a.NGAY, c.TEN as khTen, c.MA as khMa, " +
						" CAST(a.PK_SEQ as varchar(20)) as SOCHUNGTU, " +
						" a.TONGTIENAVAT, a.VAT, " +
						" a.TONGTIENBVAT, " +
						" a.TRANGTHAI, a.NGAYSUA, a.NGAYTAO, d.TEN as nguoitao, e.TEN as nguoisua, a.LOAI " +
						" FROM ERP_THANHLYTAISAN a " +
						" INNER JOIN ERP_THANHLYTAISAN_TAISAN TLTS_TS ON TLTS_TS.THANHLYTAISAN_FK = a.PK_SEQ " + 
						" INNER JOIN NHAPHANPHOI c on a.KHACHHANG_FK = c.PK_SEQ " +
						" INNER JOIN NHANVIEN d on a.NGUOITAO = d.PK_SEQ " +
						" INNER JOIN NHANVIEN e on a.NGUOISUA = e.PK_SEQ " +
						" where a.CONGTY_FK = '" + obj.getCongtyId() + "' ";

		
		System.out.println("cau search "+query);
		String tungay = request.getParameter("tungay");
		if(tungay == null)
			tungay = "";
		obj.setTungay(tungay);
		
		String denngay = request.getParameter("denngay");
		if(denngay == null)
			denngay = "";
		obj.setDenngay(denngay);
				
		String kh = request.getParameter("kh");
		if(kh == null)
			kh = "";
		obj.setKH(kh);
		
		String tongtien = request.getParameter("tongtien");
		if(tongtien == null)
			tongtien = "";
		obj.setTongtiensauVat(tongtien);
		
		String soHD = request.getParameter("soHD");
		if(soHD == null)
			soHD = "";
		obj.setSoHD(soHD);

		String ngayHD = request.getParameter("ngayHD");
		if(ngayHD == null)
			ngayHD = "";
		obj.setNgayHD(ngayHD);

		String loai = request.getParameter("loai");
		if(loai == null)
			loai = "";
		obj.setLoai(loai);
		
		
		String sodonthanhlytaisan = request.getParameter("sodonthanhlytaisan");
		if(sodonthanhlytaisan == null)
			sodonthanhlytaisan = "";
		obj.setSodonthanhlytaisan(sodonthanhlytaisan);
		
		if(tungay.length() > 0)
			query += " and a.ngaymua >= '" + tungay + "'";
		
		if(denngay.length() > 0)
			query += " and a.ngaymua <= '" + denngay + "'";
				
		if(kh.length() > 0)
			query += " and (c.ma like '%" + kh + "%' or c.ten like N'%" + kh + "%')";
		
		if(sodonthanhlytaisan.length() > 0)
			query += " and ( CAST(a.PK_SEQ as varchar(20)) like '%" + sodonthanhlytaisan + "%') ";
		
		if(tongtien.length() > 0)
			query += " and a.TONGTIENBVAT >= '" + tongtien + "'";
		
		if(soHD.length() > 0){
			query += " and a.SOHOADON LIKE N'%" + soHD + "%' ";
		}
		if(loai.length() > 0){
			query += " and a.loai = " + loai + " ";
		}
		
		if(ngayHD.length() > 0){
			query += " and a.NGAYHOADON = '" + ngayHD + "' ";
		}
		//query += " order by a.NGAYMUA desc, a.trangthai asc, a.pk_seq desc";
		return query;
	}

	private String Delete(String tltsId)
	{
		dbutils db = new dbutils();
		String query="";
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			/*query = "select COUNT(*) as sodong from ERP_GIAOHANG where THANHLYTAISAN_FK = '" + dtltsId + "'";
			System.out.println("1.Query check mua hang: " + query);
			int sodong = 0;
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				if(rs.next())
				{
					sodong = rs.getInt("sodong");
					rs.close();
				}
			}
			
			System.out.println("So dong la: " + sodong + "\n");
			
			if(sodong > 0)
			{
				return "Đơn mua hàng này đã giao hàng, bạn phải xóa Giao hàng trước khi xóa đơn thanh lý tài sản này";
			}*/
			
			
			query = "Delete From ERP_THANHLYTAISAN_TAISAN Where THANHLYTAISAN_FK = '" + tltsId + "' ";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Không thể xóa đơn thanh lý tài sản này: " + query;
			}

			query = "UPDATE  ERP_THANHLYTAISAN SET TRANGTHAI=4  where pk_seq = '" + tltsId + "' and trangthai=0";
			if(db.updateReturnInt(query)!=1)
			{
				db.getConnection().rollback();
				return "Không thể xóa đơn thanh lý tài sản này: " + query;
			}
						
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
			
			return "";
		} 
		catch (SQLException e)
		{ 
			e.printStackTrace();
			db.update("rollback");
			db.shutDown(); 
			return "Loi-khong the xoa don thanh lý tài sản này:"+query; 
		}
		
	}
	
	private String HoanTat(String congTyId, String dtltsId, String userId) 
	{
		dbutils db = new dbutils();
		Utility util = new Utility();
		String msg="";
		String ngaysua = getDateTime();
		String query = "Select loai from erp_thanhlytaisan where pk_seq = " +dtltsId ;
  	  	ResultSet rs= db.get(query);
  	  	String loai="";
  	  	if(rs!=null)
  	  	{
  	  		try {
				while(rs.next())
				{
					loai= rs.getString("loai");
				}
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
  	  		
  	  	}
  	  	//Tài sản cố định
  	  	if (loai.equals("1"))
  	  	{
  	  		try {
			db.getConnection().setAutoCommit(false);
						
		     query = " SELECT TL.KHACHHANG_FK ,TS.TAISAN_FK \n" +
					     	" FROM ERP_THANHLYTAISAN_TAISAN TS \n" +
					     	" INNER JOIN  ERP_THANHLYTAISAN TL ON TS.THANHLYTAISAN_FK = TL.PK_SEQ \n" +
					     	" WHERE TL.PK_SEQ = " + dtltsId + " \n";
	  	    ResultSet rslaytS = db.get(query);
	  	    if(rslaytS != null)
	  	    {
	  	    	while(rslaytS.next())
				{
					String tsId= rslaytS.getString("TAISAN_FK");
					String khId= rslaytS.getString("KHACHHANG_FK");
					
				    query = "Update ERP_THANHLYTAISAN SET TRANGTHAI='2', NGUOISUA = '" + userId + "', NGAYSUA = '" + ngaysua + "'  \n" +
				    		"WHERE PK_SEQ ='"+ dtltsId +"' and trangthai=0 \n";
				    System.out.println("Update THANHLYTAISAN \n" + query + "\n------------------------------------------");
					if(db.updateReturnInt(query)!=1)
					{
						db.getConnection().rollback();
						msg= "D1.1 Lỗi update THANH LY TAI SAN ";
						return msg;
					}
				    
					String taikhoanCo_sohieu="";
					String taikhoanNo_sohieu="";
					String tiente_fk = "100000";
									
					query =
					"SELECT TLTS.TAISAN_FK,TL.NGAY AS NGAYGHINHAN,TL.TYGIAQUYDOI, (TLTS.SOLUONG* TLTS.DONGIA ) AS DOANHTHU, (TL.TONGTIENAVAT-TL.TONGTIENBVAT)AS THUE, \n"+
					"	(SELECT ISNULL(TAIKHOAN_FK,'') FROM NHAPHANPHOI WHERE PK_SEQ =  TL.KHACHHANG_FK) AS sohieutaikhoanNO_KH, \n"+
					"	 ISNULL(KHAUHAOLKTT.KhauHaoLuyKeThucTe,0) + ISNULL(TSCD.GIATRIDAKHAUHAO,0) AS KhauHaoLuyKeThucTe\n" +
					"	 ,(isNull(TSCD.DONGIA, 0) \n" +
					"		+ isNull(\n" +
					"			(select sum(giaTri) \n" +
					"			from ERP_TAISANCODINH_DIEUCHINH \n" +
					"			where TSCD_FK = TSCD.PK_SEQ group by TSCD_FK), 0) \n" +
					"	- ISNULL(TSCD.GIATRIDAKHAUHAO,0) - isnull(KHAUHAOLKTT.KhauHaoLuyKeThucTe,0))*TLTS.SOLUONG  AS GIATRICL, \n"+
					"  TKKH.soHieuTaiKhoan as sohieutaikhoanNOKH,\n"+
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
					"	LEFT JOIN ERP_TAISANCODINH TSCD ON TLTS.TAISAN_FK=TSCD.PK_SEQ   \n" +
					"	LEFT JOIN ERP_LOAITAISAN LTSCD ON TSCD.loaiTaiSan_FK = LTSCD.PK_SEQ   \n" +
					"	LEFT JOIN ERP_TAIKHOANKT TKKH ON LTSCD.taiKhoanKH_FK = TKKH.PK_SEQ   \n" +
					"WHERE TL.PK_SEQ= "+dtltsId+" \n";
					System.out.println("Cai ke toan \n" + query + "\n-------------------------------------------");
					ResultSet Rskt= db.get(query);
					
					if(Rskt != null)
					{
						while(Rskt.next())
						{
							String	thang= Rskt.getString("NGAYGHINHAN").substring(5, 7);
							String	nam= Rskt.getString("NGAYGHINHAN").substring(0, 4);
						 
							Double totalDT= Rskt.getDouble("DOANHTHU");
							Double Thue= Rskt.getDouble("THUE");
							Double gtconlai = Rskt.getDouble("GIATRICL");
							Double khauhaoLK = Rskt.getDouble("KhauHaoLuyKeThucTe");
						 
						 //cai kt Doanh thu
						 
							taikhoanNo_sohieu= Rskt.getString("sohieutaikhoanNO_KH");
							taikhoanCo_sohieu="71110000";
							 
							
							if(taikhoanNo_sohieu == null || taikhoanNo_sohieu.trim().length() <= 0 || taikhoanCo_sohieu == null || taikhoanCo_sohieu.trim().length() <= 0 )
							{
							    msg	= "D1.2 Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
								db.getConnection().rollback();
								return msg;
							}
							 msg = util.Update_TaiKhoan_TheoSoHieu_ThemDoiTuong(db, congTyId, thang, nam, Rskt.getString("NGAYGHINHAN"), Rskt.getString("NGAYGHINHAN"), "Thanh lý tài sản", dtltsId, taikhoanNo_sohieu,
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
							 taikhoanCo_sohieu="33313000";
							 
							 if(taikhoanNo_sohieu == null || taikhoanNo_sohieu.trim().length() <= 0 || taikhoanCo_sohieu.trim().length() <= 0 )
							 {
							    msg	= "D1.4 Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
								db.getConnection().rollback();
								return msg;
							 }																
							 msg = util.Update_TaiKhoan_TheoSoHieu_ThemDoiTuong(db, congTyId, thang, nam, Rskt.getString("NGAYGHINHAN"), Rskt.getString("NGAYGHINHAN"), "Thanh lý tài sản", dtltsId, taikhoanNo_sohieu,
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
			
			 db.getConnection().commit();
			 db.getConnection().setAutoCommit(true);
			  
			} catch (SQLException e) {
				e.printStackTrace();
				try 
				{
					db.getConnection().rollback();	
					
				} catch (Exception e2) {
					e2.printStackTrace();
					msg = "D1.12 Lỗi chốt thanh lý tài sản ";
					return msg;
				}
			}
			}
  	  	else
  	  		//Công cụ dụng cụ
  	  		if(loai.equals("2"))
  	  		{
  	  		try {
  				db.getConnection().setAutoCommit(false);
  							
  			    query = " SELECT TL.KHACHHANG_FK ,TS.CCDC_FK \n" +
  						     	" FROM ERP_THANHLYTAISAN_TAISAN TS \n" +
  						     	" INNER JOIN  ERP_THANHLYTAISAN TL ON TS.THANHLYTAISAN_FK = TL.PK_SEQ \n" +
  						     	" WHERE TL.PK_SEQ = " + dtltsId + " \n";
  		  	    ResultSet rslaytS = db.get(query);
  		  	    if(rslaytS != null)
  		  	    {
  		  	    	while(rslaytS.next())
  					{
  						String ccId= rslaytS.getString("CCDC_FK");
  						String khId= rslaytS.getString("KHACHHANG_FK");
  						
  					    query = "Update ERP_THANHLYTAISAN SET TRANGTHAI='3', NGUOISUA = '" + userId + "', NGAYSUA = '" + ngaysua + "' \n" +
  					    		"WHERE PK_SEQ ='"+ dtltsId +"' \n";
  					    System.out.println("Update THANHLYTAISAN \n" + query + "\n----------------------------------------");
  						if(!db.update(query))
  						{
  							db.getConnection().rollback();
  							msg= "D1.13 Lỗi update THANH LY TAI SAN ";
  							return msg;
  						}
  					    
  						String taikhoanCo_sohieu="";
  						String taikhoanNo_sohieu="";
  						String tiente_fk = "100000";
  						 
  										
  						query = 
						"SELECT TLTS.CCDC_FK,TL.NGAY AS NGAYGHINHAN,TL.TYGIAQUYDOI, (TLTS.SOLUONG* TLTS.DONGIA ) AS DOANHTHU , (TL.TONGTIENAVAT-TL.TONGTIENBVAT)AS THUE,  \n "+
						"	(SELECT SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ IN (SELECT TAIKHOAN_FK FROM NHAPHANPHOI WHERE PK_SEQ =  TL.KHACHHANG_FK)) AS sohieutaikhoanNO_KH, \n "+
 						"	 KHAUHAOLKTT.KhauHaoLuyKeThucTe\n" +
 						"	,((isNull(CCDC.DONGIA, 0) " +
 						"	+ isNull((select sum(giaTri) " +
 						"		from ERP_CONGCUDUNGCU_DIEUCHINH " +
 						"		where CCDC_FK = CCDC.PK_SEQ " +
 						"		group by CCDC_FK), 0))*CCDC.SOLUONG " +
 						"	- isnull(KHAUHAOLKTT.KhauHaoLuyKeThucTe,0))*TLTS.SOLUONG  AS GIATRICL, \n "+
 						" 		TK3.SOHIEUTAIKHOAN as sohieutaikhoanNOKH,         \n "+                  
 						"  (SELECT SOHIEUTAIKHOAN  \n "+
 						"   FROM ERP_TAIKHOANKT  \n "+
 						"   WHERE PK_SEQ IN (select taikhoan_fk from Erp_LOAICCDC where pk_seq= CCDC.LOAICCDC_FK)) as sohieutaikhoanTS        \n "+                     
 						"	FROM ERP_THANHLYTAISAN_TAISAN TLTS  \n "+
 						"   INNER JOIN ERP_THANHLYTAISAN TL ON TLTS.THANHLYTAISAN_FK= TL.PK_SEQ \n "+
 						"	LEFT JOIN 	 \n "+
 						"   (  \n "+
 						"		SELECT TOP 1 CCDC_FK, khauHaoLuyke as KhauHaoLuyKeThucTe \n "+
 						"		FROM ERP_KHAUHAOCCDC \n "+
 						"		WHERE \n "+
 						"		CCDC_FK= " +ccId+ "\n "+
 						"		ORDER BY THANGTHU DESC  \n "+
 						"   ) KHAUHAOLKTT ON TLTS.CCDC_FK= KHAUHAOLKTT.CCDC_FK \n "+
 						"	LEFT JOIN   ERP_CONGCUDUNGCU CCDC ON TLTS.CCDC_FK=CCDC.PK_SEQ   \n "+
 						"	left join Erp_LOAICCDC LCC ON LCC.pk_seq=CCDC.LOAICCDC_FK \n "+
 						"	LEFT JOIN ERP_TAIKHOANKT TK3 ON LCC.taikhoan_fk= TK3.PK_SEQ \n "+
 						"   WHERE TL.PK_SEQ= " + dtltsId + " \n";
 						
  						System.out.println(" Cài kế toán \n" + query + "\n-------------------------------------------");
  						ResultSet Rskt= db.get(query);
  						
  						if(Rskt!= null)
  						{
  							while(Rskt.next())
  							{
  							
  								String	thang= Rskt.getString("NGAYGHINHAN").substring(5, 7);
  								String	nam= Rskt.getString("NGAYGHINHAN").substring(0, 4);
  							 
  								Double totalDT= Rskt.getDouble("DOANHTHU");
  								Double Thue= Rskt.getDouble("THUE");
  								Double gtconlai= Rskt.getDouble("GIATRICL");
//  								Double khauhaoLK= Rskt.getDouble("KhauHaoLuyKeThucTe");
  							 
  							 //cai kt Doanh thu
  							 
  								taikhoanNo_sohieu= Rskt.getString("sohieutaikhoanNO_KH");
  								taikhoanCo_sohieu="71180000";
  								 
  								
  								if(taikhoanNo_sohieu.trim().length() <= 0 || taikhoanCo_sohieu.trim().length() <= 0 )
  								{
  								    msg	= "D1.14 Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
  									db.getConnection().rollback();
  									return msg;
  								}
  									
  								 msg = util.Update_TaiKhoan_TheoSoHieu_ThemDoiTuong(db, congTyId, thang, nam, Rskt.getString("NGAYGHINHAN"), Rskt.getString("NGAYGHINHAN"), "Thanh lý tài sản", dtltsId, taikhoanNo_sohieu,
  										 taikhoanCo_sohieu, "NULL", Double.toString(totalDT), Double.toString(totalDT), "Khách hàng", khId, "0", "", "", "", "", 
  										 "", tiente_fk, "", "1", Double.toString(totalDT), Double.toString(totalDT), "","1","");		
  								/*msg = util.Update_TaiKhoan_TheoSoHieu(congTyId, db, thang, nam, Rskt.getString("NGAYGHINHAN"), Rskt.getString("NGAYGHINHAN"), "Thanh lý tài sản", dtltsId, taikhoanNo_sohieu, taikhoanCo_sohieu, "", 
  									  Double.toString(totalDT), Double.toString(totalDT), "Công cụ dụng cụ", ccId, "0", "", "", tiente_fk, "", Rskt.getString("TYGIAQUYDOI"), Double.toString(totalDT), Double.toString(totalDT), "" );
  								*/
  								if(msg.trim().length() > 0)
  								{
  									db.getConnection().rollback();
  									return "D1.15 " + msg;
  								}
  									/*Update_TaiKhoan_TheoSoHieu(dbutils db, String thang, String nam, String ngaychungtu, String ngayghinhan, String loaichungtu, String sochungtu, String taikhoanNO_SoHieu, String taikhoanCO_SoHieu, String NOIDUNGNHAPXUAT_FK, 
  									 * String NO, String CO, String DOITUONG,  
  											String MADOITUONG, String LOAIDOITUONG, String SOLUONG, String DONGIA, String TIENTEGOC_FK, String DONGIANT, String TIGIA_FK, String TONGGIATRI, String TONGGIATRINT, String khoanmuc)
  							 */
  							//cai kt Tien thue
  							 
  								 taikhoanNo_sohieu= Rskt.getString("sohieutaikhoanNO_KH");
  								 taikhoanCo_sohieu="33311000";
  								 
  								 if(taikhoanNo_sohieu.trim().length() <= 0 || taikhoanCo_sohieu.trim().length() <= 0 )
  								 {
  								    msg	= "D1.16 Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
  									db.getConnection().rollback();
  									return msg;
  								 }																
  								 msg = util.Update_TaiKhoan_TheoSoHieu_ThemDoiTuong(db, congTyId, thang, nam, Rskt.getString("NGAYGHINHAN"), Rskt.getString("NGAYGHINHAN"), "Thanh lý tài sản", dtltsId, taikhoanNo_sohieu,
  										 taikhoanCo_sohieu, "NULL", Double.toString(Thue), Double.toString(Thue), "Khách hàng", khId, "0", "", "", "", "", 
  										 "", tiente_fk, "", "1", Double.toString(Thue), Double.toString(Thue), "","1","");
  								 if(msg.trim().length() > 0)
  								 {
  									db.getConnection().rollback();
  									return msg;
  								 }
  							}
  							Rskt.close();
  						}
  					}
  		  	    	rslaytS.close();
  		  	     }
  				 db.getConnection().commit();
  				 db.getConnection().setAutoCommit(true);
  				} catch (SQLException e) {
  					e.printStackTrace();
  					try 
  					{
  						db.getConnection().rollback();	
  					} catch (Exception e2) {
  						e2.printStackTrace();
  						msg = "D1.19 Lỗi chốt thanh lý tài sản ";
  						return msg;
  					}
  				}
  				}
  	  		
			db.shutDown();
       return "";
		
	}
	
	private String Duyet(String congTyId, String dtltsId, String userId) 
	{
		dbutils db = new dbutils();
		Utility util = new Utility();
		String msg="";
		String ngaysua = getDateTime();
		String query = "Select loai from erp_thanhlytaisan where pk_seq = " +dtltsId ;
  	  	ResultSet rs= db.get(query);
  	  	String loai="";
  	  	if(rs!=null)
  	  	{
  	  		try {
				while(rs.next())
				{
					loai= rs.getString("loai");
				}
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
  	  		
  	  	}
  	  	//Tài sản cố định
  	  	if (loai.equals("1"))
  	  	{
  	  		try {
			db.getConnection().setAutoCommit(false);
						
		     query = " SELECT TL.KHACHHANG_FK ,TS.TAISAN_FK,TL.TRANGTHAI \n" +
					     	" FROM ERP_THANHLYTAISAN_TAISAN TS \n" +
					     	" INNER JOIN  ERP_THANHLYTAISAN TL ON TS.THANHLYTAISAN_FK = TL.PK_SEQ \n" +
					     	" WHERE TL.PK_SEQ = " + dtltsId + " \n";
	  	    ResultSet rslaytS = db.get(query);
	  	    if(rslaytS != null)
	  	    {
	  	    	while(rslaytS.next())
				{
					String tsId= rslaytS.getString("TAISAN_FK");
//					String khId= rslaytS.getString("KHACHHANG_FK");
					String TRANGTHAI= rslaytS.getString("TRANGTHAI");
						if(TRANGTHAI.equals("1"))
						{
							db.getConnection().rollback();
							msg= "D1.1.1 PHIẾU THANH LÝ ĐÃ CHỐT";
							return msg;
						}
				    query = "Update ERP_THANHLYTAISAN SET TRANGTHAI='1', NGUOISUA = '" + userId + "', NGAYSUA = '" + ngaysua + "' \n" +
				    		"WHERE PK_SEQ ='"+ dtltsId +"' \n";
				    System.out.println("Update THANHLYTAISAN \n" + query + "\n------------------------------------------");
					if(!db.update(query))
					{
						db.getConnection().rollback();
						msg= "D1.1 Lỗi update THANH LY TAI SAN ";
						return msg;
					}
				    
					String taikhoanCo_sohieu="";
					String taikhoanNo_sohieu="";
					String tiente_fk = "100000";
									
					query =
					"SELECT TLTS.TAISAN_FK,TL.NGAY AS NGAYGHINHAN,TL.TYGIAQUYDOI, (TLTS.SOLUONG* TLTS.DONGIA ) AS DOANHTHU, (TL.TONGTIENAVAT-TL.TONGTIENBVAT)AS THUE, \n"+
					"	(SELECT ISNULL(TAIKHOAN_FK,'') FROM NHAPHANPHOI WHERE PK_SEQ =  TL.KHACHHANG_FK) AS sohieutaikhoanNO_KH, \n"+
					"	 ISNULL(KHAUHAOLKTT.KhauHaoLuyKeThucTe,0) + ISNULL(TSCD.GIATRIDAKHAUHAO,0) AS KhauHaoLuyKeThucTe\n" +
					"	 ,(isNull(TSCD.DONGIA, 0) \n" +
					"		+ isNull(\n" +
					"			(select sum(giaTri) \n" +
					"			from ERP_TAISANCODINH_DIEUCHINH \n" +
					"			where TSCD_FK = TSCD.PK_SEQ group by TSCD_FK), 0) \n" +
					"		- ISNULL(TSCD.GIATRIDAKHAUHAO,0) -  isnull(KHAUHAOLKTT.KhauHaoLuyKeThucTe,0))*TLTS.SOLUONG  AS GIATRICL, \n"+
					"  TKKH.soHieuTaiKhoan as sohieutaikhoanNOKH,\n"+
					"  (SELECT SOHIEUTAIKHOAN \n" +
					"   FROM ERP_TAIKHOANKT \n" +
					"   WHERE PK_SEQ IN (select taikhoan_fk from Erp_LOAITAISAN where pk_seq= TSCD.LOAITAISAN_FK)) as sohieutaikhoanTS  \n"+                           
					"	FROM ERP_THANHLYTAISAN_TAISAN TLTS \n" +
					"   INNER JOIN ERP_THANHLYTAISAN TL ON TLTS.THANHLYTAISAN_FK= TL.PK_SEQ \n"+
					"	LEFT JOIN 	\n" +
					"   ( \n" +
					"		SELECT TOP 1 TaiSan_FK, khauHaoLuyKe as KhauHaoLuyKeThucTe \n"+
					"		FROM ERP_KHAUHAOTAISAN \n"+
//					"		WHERE TaiSan_FK=" + tsId + "\n" +
					"		WHERE TaiSan_FK= " + tsId + " and TRANGTHAI =1 \n" +
					"				and ((convert(nvarchar, NAM) \n" +
					"				+ (case when thang > 9 then '-' else '0-' end) \n" +
					"				+ convert(nvarchar, THANG) + '-01') \n" +
					"				< (select tl.NGAY from ERP_THANHLYTAISAN tl where PK_SEQ = "+dtltsId+"))\n"+
					"		ORDER BY thangThu DESC \n" +
					"   ) KHAUHAOLKTT ON TLTS.TAISAN_FK= KHAUHAOLKTT.TaiSan_FK \n"+
					"	LEFT JOIN ERP_TAISANCODINH TSCD ON TLTS.TAISAN_FK=TSCD.PK_SEQ   \n" +
					"	LEFT JOIN ERP_LOAITAISAN LTSCD ON TSCD.loaiTaiSan_FK = LTSCD.PK_SEQ   \n" +
					"	LEFT JOIN ERP_TAIKHOANKT TKKH ON LTSCD.taiKhoanKH_FK = TKKH.PK_SEQ   \n" +
					"WHERE TL.PK_SEQ= "+dtltsId+" \n";
					System.out.println("Cai ke toan \n" + query + "\n-------------------------------------------");
					ResultSet Rskt= db.get(query);
					
					if(Rskt!= null)
					{
						while(Rskt.next())
						{
							String	thang= Rskt.getString("NGAYGHINHAN").substring(5, 7);
							String	nam= Rskt.getString("NGAYGHINHAN").substring(0, 4);
						 
							Double totalDT= Rskt.getDouble("DOANHTHU");
							Double Thue= Rskt.getDouble("THUE");
							Double gtconlai= Rskt.getDouble("GIATRICL");
							Double khauhaoLK= Rskt.getDouble("KhauHaoLuyKeThucTe");
						 
							//Khấu hao lũy kế
						
							 taikhoanNo_sohieu= Rskt.getString("sohieutaikhoanNOKH");
							
							 taikhoanCo_sohieu=  Rskt.getString("sohieutaikhoanTS");
							 if(khauhaoLK>0)
							 {
								 if((taikhoanNo_sohieu==null)&&(taikhoanCo_sohieu==null))
										 {
											 	msg	= "D1.6 Lỗi xác định tài khoản kế toán vui lòng kiểm tra lại";
												db.getConnection().rollback();
												return msg;
										 }
								 if(taikhoanNo_sohieu.trim().length() <= 0 || taikhoanCo_sohieu.trim().length() <= 0 )
								 {
								    msg	= "D1.7 Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
									db.getConnection().rollback();
									return msg;
								 }																
								 msg = util.Update_TaiKhoan_TheoSoHieu_ThemDoiTuong(db, congTyId, thang, nam,  Rskt.getString("NGAYGHINHAN"),  Rskt.getString("NGAYGHINHAN"), "Thanh lý tài sản", 
										 dtltsId, taikhoanNo_sohieu, taikhoanCo_sohieu, "", Double.toString(khauhaoLK), Double.toString(khauhaoLK), "Tài sản", tsId, "", "Tài sản", tsId, "", "0", "0",
										 tiente_fk, "0", "1", Double.toString(khauhaoLK), Double.toString(khauhaoLK), "", "", "");
								 
								 if(msg.trim().length() > 0)
								 {
									db.getConnection().rollback();
									return "D1.8 " + msg;
								 }
							 }
							 
						 
						 //cai kt Gia tri con lai
						     if(gtconlai>0)
						     {
								 taikhoanNo_sohieu= "81110000";
								 taikhoanCo_sohieu= Rskt.getString("sohieutaikhoanTS");
								 if(taikhoanCo_sohieu==null)
								 {
									 	msg	= "D1.9 Lỗi xác định tài khoản kế toán vui lòng kiểm tra lại";
										db.getConnection().rollback();
										return msg;
								 }
								 
								 if(taikhoanNo_sohieu.trim().length() <= 0 || taikhoanCo_sohieu.trim().length() <= 0 )
								 {
								    msg	= "D1.10 Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
									db.getConnection().rollback();
									return msg;
								 }				
								 
								 msg = util.Update_TaiKhoan_TheoSoHieu_ThemDoiTuong(db, congTyId, thang, nam,  Rskt.getString("NGAYGHINHAN"),  Rskt.getString("NGAYGHINHAN"), "Thanh lý tài sản", 
										 dtltsId, taikhoanNo_sohieu, taikhoanCo_sohieu, "", Double.toString(gtconlai), Double.toString(gtconlai), "", "", "", "Tài sản", tsId, "", "0", "0",
										 tiente_fk, "0","1", Double.toString(gtconlai), Double.toString(gtconlai), "", "", "");
								
								 if(msg.trim().length() > 0)
								 {
									db.getConnection().rollback();
									return "D1.11 " + msg;
								 }
						     }
						}
						Rskt.close();
					}
				}
	  	    	rslaytS.close();
	  	     }
			
	  	    
	  	    
 		  	 query =      
   		  		"update ERP_TAISANCODINH\n" +
		  		"set trangthai = 2, NGAYTHANHLY = getDate()\n" +
		  		"where PK_SEQ in \n" +
		  		"	(select TAISAN_FK \n" +
		  		"	from ERP_THANHLYTAISAN_TAISAN \n" +
		  		"	where THANHLYTAISAN_FK = " + dtltsId + ")";
   				
   				System.out.println(" San pham init: \n" + query + "\n-------------------------------------------------");
   				int num = db.updateReturnInt(query);
   				
   				if (num == 0)
   				{
   					db.getConnection().rollback();
					return "D1.11 Không thể duyệt thanh lý tài sản";
   				}
			 db.getConnection().commit();
			 db.getConnection().setAutoCommit(true);
			  
			} catch (SQLException e) {
				e.printStackTrace();
				try 
				{
					db.getConnection().rollback();	
					
				} catch (Exception e2) {
					e2.printStackTrace();
					msg = "D1.12 Lỗi chốt thanh lý tài sản ";
					return msg;
				}
			}
			}
  	  	else
  	  		//Công cụ dụng cụ
  	  		if(loai.equals("2"))
  	  		{
  	  		try {
  				db.getConnection().setAutoCommit(false);
  							
  			    query = " SELECT TL.KHACHHANG_FK ,TS.CCDC_FK,TL.TRANGTHAI \n" +
  						     	" FROM ERP_THANHLYTAISAN_TAISAN TS \n" +
  						     	" INNER JOIN  ERP_THANHLYTAISAN TL ON TS.THANHLYTAISAN_FK = TL.PK_SEQ \n" +
  						     	" WHERE TL.PK_SEQ = " + dtltsId + " \n";
  		  	    ResultSet rslaytS = db.get(query);
  		  	    if(rslaytS != null)
  		  	    {
  		  	    	while(rslaytS.next())
  					{
  						String ccId= rslaytS.getString("CCDC_FK");
//  						String khId= rslaytS.getString("KHACHHANG_FK");
  						String TRANGTHAI= rslaytS.getString("TRANGTHAI");
  						if(TRANGTHAI.equals("3"))
  						{
  							db.getConnection().rollback();
  							msg= "D1.13.1 PHIẾU THANH LÝ ĐÃ CHỐT";
  							return msg;
  						}
  					    query = "Update ERP_THANHLYTAISAN SET TRANGTHAI='3', NGUOISUA = '" + userId + "', NGAYSUA = '" + ngaysua + "' \n" +
  					    		"WHERE PK_SEQ ='"+ dtltsId +"' \n";
  					    System.out.println("Update THANHLYTAISAN \n" + query + "\n----------------------------------------");
  						if(!db.update(query))
  						{
  							db.getConnection().rollback();
  							msg= "D1.13 Lỗi update THANH LY TAI SAN ";
  							return msg;
  						}
  					    
  						String taikhoanCo_sohieu="";
  						String taikhoanNo_sohieu="";
  						String tiente_fk = "100000";
  						 
  										
  						query = 
						"SELECT TLTS.CCDC_FK,TL.NGAY AS NGAYGHINHAN,TL.TYGIAQUYDOI, (TLTS.SOLUONG* TLTS.DONGIA ) AS DOANHTHU , (TL.TONGTIENAVAT-TL.TONGTIENBVAT)AS THUE,  \n "+
						"	(SELECT SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ IN (SELECT TAIKHOAN_FK FROM NHAPHANPHOI WHERE PK_SEQ =  TL.KHACHHANG_FK)) AS sohieutaikhoanNO_KH, \n "+
 						"	 KHAUHAOLKTT.KhauHaoLuyKeThucTe\n" +
 						"	,((isNull(CCDC.DONGIA, 0) " +
 						"	+ isNull((select sum(giaTri) " +
 						"		from ERP_CONGCUDUNGCU_DIEUCHINH " +
 						"		where CCDC_FK = CCDC.PK_SEQ " +
 						"		group by CCDC_FK), 0))*CCDC.SOLUONG " +
 						"	- isnull(KHAUHAOLKTT.KhauHaoLuyKeThucTe,0))*TLTS.SOLUONG  AS GIATRICL, \n "+
 						" 		TK3.SOHIEUTAIKHOAN as sohieutaikhoanNOKH,         \n "+                  
 						"  (SELECT SOHIEUTAIKHOAN  \n "+
 						"   FROM ERP_TAIKHOANKT  \n "+
 						"   WHERE PK_SEQ IN (select taikhoan_fk from Erp_LOAICCDC where pk_seq= CCDC.LOAICCDC_FK)) as sohieutaikhoanTS        \n "+                     
 						"	FROM ERP_THANHLYTAISAN_TAISAN TLTS  \n "+
 						"   INNER JOIN ERP_THANHLYTAISAN TL ON TLTS.THANHLYTAISAN_FK= TL.PK_SEQ \n "+
 						"	LEFT JOIN 	 \n "+
 						"   (  \n "+
 						"		SELECT TOP 1 CCDC_FK, khauHaoLuyke as KhauHaoLuyKeThucTe \n "+
 						"		FROM ERP_KHAUHAOCCDC \n "+
// 						"		WHERE \n "+
// 						"		CCDC_FK= " +ccId+ "\n "+
 						"		WHERE CCDC_FK= " + ccId + " and TRANGTHAI =1 \n" +
 						"				and ((convert(nvarchar, NAM) \n" +
 						"				+ (case when thang > 9 then '-' else '0-' end) \n" +
 						"				+ convert(nvarchar, THANG) + '-01') \n" +
 						"				< (select tl.NGAY from ERP_THANHLYTAISAN tl where PK_SEQ = "+dtltsId+"))\n"+
 						"		ORDER BY THANGTHU DESC  \n "+
 						"   ) KHAUHAOLKTT ON TLTS.CCDC_FK= KHAUHAOLKTT.CCDC_FK \n "+
 						"	LEFT JOIN   ERP_CONGCUDUNGCU CCDC ON TLTS.CCDC_FK=CCDC.PK_SEQ   \n "+
 						"	left join Erp_LOAICCDC LCC ON LCC.pk_seq=CCDC.LOAICCDC_FK \n "+
 						"	LEFT JOIN ERP_TAIKHOANKT TK3 ON LCC.taikhoan_fk= TK3.PK_SEQ \n "+
 						"   WHERE TL.PK_SEQ= " + dtltsId + " \n";
 						
  						System.out.println(" Cài kế toán \n" + query + "\n-------------------------------------------");
  						ResultSet Rskt= db.get(query);
  						
  						if(Rskt!= null)
  						{
  							while(Rskt.next())
  							{
  							
  								String	thang= Rskt.getString("NGAYGHINHAN").substring(5, 7);
  								String	nam= Rskt.getString("NGAYGHINHAN").substring(0, 4);
  							 
  								Double totalDT= Rskt.getDouble("DOANHTHU");
  								Double Thue= Rskt.getDouble("THUE");
  								Double gtconlai= Rskt.getDouble("GIATRICL");
//  								Double khauhaoLK= Rskt.getDouble("KhauHaoLuyKeThucTe");
  							 
  	  							 //cai kt Gia tri con lai
  	  							 
 								 taikhoanNo_sohieu= "81180000";
 								 taikhoanCo_sohieu= Rskt.getString("sohieutaikhoanTS");
 								 
 								 if(taikhoanNo_sohieu.trim().length() <= 0 || taikhoanCo_sohieu.trim().length() <= 0 )
 								 {
 								    msg	= "D1.17 Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
 									db.getConnection().rollback();
 									return msg;
 								 }	
 								 
 								msg = util.Update_TaiKhoan_TheoSoHieu_ThemDoiTuong(db, congTyId, thang, nam,  Rskt.getString("NGAYGHINHAN"),  Rskt.getString("NGAYGHINHAN"), "Thanh lý tài sản", 
										 dtltsId, taikhoanNo_sohieu, taikhoanCo_sohieu, "", Double.toString(gtconlai), Double.toString(gtconlai), "Công cụ dụng cụ", ccId, "0", "", "", "", "0", "0",
										 tiente_fk, "0","1", Double.toString(gtconlai), Double.toString(gtconlai), "", "", "");
 								/* msg = util.Update_TaiKhoan_TheoSoHieu(congTyId, db, thang, nam, Rskt.getString("NGAYGHINHAN"), Rskt.getString("NGAYGHINHAN"), "Thanh lý tài sản", dtltsId, taikhoanNo_sohieu, taikhoanCo_sohieu, "", 
 									   Double.toString(gtconlai), Double.toString(gtconlai), "Công cụ dụng cụ", ccId, "0", "", "", tiente_fk, "", Rskt.getString("TYGIAQUYDOI"), Double.toString(gtconlai), Double.toString(gtconlai), "" );*/
 								 if(msg.trim().length() > 0)
 								 {
 									db.getConnection().rollback();
 									return "D1.18 " + msg;
 								 }
  							}
  							Rskt.close();
  						}
  					}
  		  	    	rslaytS.close();
  		  	     }
  		  	 query =      
  		  		"update ERP_CONGCUDUNGCU\n" +
   		  		"set isDaThanhLy = 1, NGAYTHANHLY = getDate()\n" +
   		  		"where PK_SEQ in \n" +
   		  		"	(select TAISAN_FK \n" +
   		  		"	from ERP_THANHLYTAISAN_TAISAN \n" +
   		  		"	where THANHLYTAISAN_FK = " + dtltsId + ")";
  				
  				System.out.println(" San pham init: \n" + query + "\n-------------------------------------------------");
  				int num = db.updateReturnInt(query);
  				
  				if (num == 0)
  				{
  					db.getConnection().rollback();
					return "D1.20 Không hoàn tất thanh lý được";
  				}
  				 db.getConnection().commit();
  				 db.getConnection().setAutoCommit(true);
  				} catch (SQLException e) {
  					e.printStackTrace();
  					try 
  					{
  						db.getConnection().rollback();	
  					} catch (Exception e2) {
  						e2.printStackTrace();
  						msg = "D1.19 Lỗi chốt thanh lý tài sản ";
  						return msg;
  					}
  				}
  				}
  	  		
			db.shutDown();
       return "";
		
	}
	

	private String getDateTime() 
	{
	   DateFormat dateformat= new SimpleDateFormat("yyyy-MM-dd");
	   Date date= new Date();
		return dateformat.format(date);
	}
}
