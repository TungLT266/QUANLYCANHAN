package geso.traphaco.erp.servlets.chiphinhapkhau;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.GiuDieuKienLoc;
import geso.traphaco.center.util.Utility;
import geso.traphaco.center.util.UtilityKeToan;
import geso.traphaco.erp.beans.chiphinhapkhau.IErpChiphinhapkhau;
import geso.traphaco.erp.beans.chiphinhapkhau.IErpChiphinhapkhauList;
import geso.traphaco.erp.beans.chiphinhapkhau.imp.ErpChiphinhapkhau;
import geso.traphaco.erp.beans.chiphinhapkhau.imp.ErpChiphinhapkhauList;
import geso.traphaco.erp.beans.masclon.imp.Erp_MaSCLon;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpChiphinhapkhauSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	
    public ErpChiphinhapkhauSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out  = response.getWriter();
	    
	    HttpSession session = request.getSession();	
	    // khai bao Severlet
	    String ServerletName = this.getServletName();
	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    IErpChiphinhapkhauList obj = new ErpChiphinhapkhauList();
	    String ctyId = (String)session.getAttribute("congtyId");
	    obj.setUserId(userId);
	    obj.setCongtyId(ctyId);
	    
	    String action = util.getAction(querystring);
	    String Id = util.getId(querystring);
	    String msg = "";
	    
	    if(action.trim().equals("delete"))
	    {
	    	dbutils db = new dbutils();
	    	if(!db.update("update ERP_CHIPHINHAPKHAU set trangthai = '2' where pk_seq = '" + Id + "'"))
	    	{
	    		msg = "Không thể xóa ERP_CHIPHINHAPKHAU";
	    	}
	    	db.shutDown();
	    }
	    
	    if(action.trim().equals("chot"))
	    {
	    	msg = ChotChiPhi_NhapKhau(Id);
	    }
	    
// PHÂN BỔ LẠI
	    dbutils db = new dbutils();


	    String searchQuery=util.getSearchFromHM(userId,ServerletName, session);
		geso.traphaco.center.util.GiuDieuKienLoc.setParamsToOject(obj, searchQuery);
	    obj.init("");
	    String querySearch = GiuDieuKienLoc.createParams(obj);
	    util.setSearchToHM(userId, session, ServerletName, querySearch);
	    obj.setMsg(msg);
		session.setAttribute("obj", obj);
	    
	    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpChiPhiNhapKhau.jsp";
		response.sendRedirect(nextJSP);
	}

	private String ChotChiPhi_NhapKhau(String Id) 
	{
		String msg = "";
		
		dbutils db = new dbutils();
		
		String ngayChot = getDateTime();
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "update ERP_CHIPHINHAPKHAU set trangthai = '1', NGAYCHOT = '" + ngayChot + "' where pk_seq = '" + Id + "'";
			System.out.println("update cpnk: \n" + query + "\n---------------------------------------------------");
			if(!db.update(query))
	    	{
				db.getConnection().rollback();
	    		msg = "CCPNK1.1 Không thể chot ERP_CHIPHINHAPKHAU " + query;
	    		return msg;
	    	}
			
			// HÀNG HÓA LÀ NHẬP KHẨU'
			// LẤY TIỀN HÀNG TRƯỚC THUẾ ĐỂ PHÂN BỔ CHI PHÍ LẠI CHO SẢN PHẨM
			
			query = " SELECT distinct ( NH.LOAIHANGHOA_FK ) isTSCD  \n"+
					" FROM ERP_CHIPHINHAPKHAU CPNK \n"+
					" INNER JOIN ERP_CHIPHINHAPKHAU_NHANHANG NHCT ON NHCT.CHIPHINHAPKHAU_FK = CPNK.pk_seq \n"+
					" INNER JOIN ERP_NHANHANG NH ON NH.PK_SEQ = NHCT.NHANHANG_FK  AND NH.TRANGTHAI IN (1,2) \n"+
					"  WHERE 1 = 1 AND CPNK.PK_SEQ = " + Id + "";  
			
			ResultSet rs = db.get(query);
			int isTSCD = 1;
			
			if(rs!=null)
			{
				while(rs.next())
				{
					isTSCD = rs.getInt("isTSCD"); 
				}
				rs.close();
			}
			
			if(isTSCD == 1) // NẾU NHẬN HÀNG LÀ TSCD
			{
				//Cập nhật nguyên giá cho mã sửa chữa tài sản cố đinh
				String result = capNhatMSC(db, Id, ngayChot);
				if (result.trim().length() > 0)
				{
					msg = "CCPNK1.2 Không thể cập nhật mã sửa chữa lớn tài sản \n" + result;
					db.getConnection().rollback();
					return msg;
				}
			}
			  
			
			Utility util = new Utility();
			String ncc = "";
			double tienhang = 0;
			double tienthue = 0;
			String maChungTu = "";
			String dienGiai = "";
			String namNV = "";
			String thangNV = "";
			String nhId = "";
			String spId = "";
			String tiente_fk = "100000";
			String taikhoanktCo = "";
			String ngay = "";
			
			//Lay no co ( TIEN HANG SE LA TIEN SAU KHI DA PHAN BO CHO SAN PHAM )  --> TIEN THUE CHUNG
			query = "select a.ngay, a.NCCID_CN as nhacungcap, C.sanpham_fk as spId, C.tienhang,  A.TONGTIENVAT AS tienthue, \n" +
					"(select TAIKHOAN_FK from ERP_NHACUNGCAP where pk_seq = a.NCCID_CN ) as TAIKHOANKTCO, ISNULL(C.NHANHANG_FK, 0) AS NHID,  \n" +
					"isNull(a.ghiChu, '') dienGiai, isNull(a.soChungTu_Chu + a.soChungTu_So, '') as maChungTu\n" +
					"from ERP_CHIPHINHAPKHAU a \n" +
					"INNER JOIN ERP_CHIPHINHAPKHAU_PHANBO C ON C.CHIPHINHAPKHAU_FK = A.PK_SEQ \n " +
					"where a.pk_seq = '" + Id + "'\n";
			
			System.out.println("1.INIT NO-CO: \n" + query + "\n------------------------------------");
			ResultSet rsNoco = db.get(query);
		 
				while(rsNoco.next())
				{
					ncc = rsNoco.getString("nhacungcap");
					tienhang = rsNoco.getDouble("tienhang");
					tienthue = rsNoco.getDouble("tienthue");
					maChungTu = rsNoco.getString("maChungTu");
					dienGiai = rsNoco.getString("dienGiai");
					namNV = rsNoco.getString("ngay").substring(0, 4);
        			thangNV = rsNoco.getString("ngay").substring(5, 7);
					nhId = rsNoco.getString("NHID").equals("0")?"":rsNoco.getString("NHID");
					spId = rsNoco.getString("SPID");
					ngay = rsNoco.getString("ngay");
					
					
					//Phân bổ tiền hàng
					if(tienhang > 0)
					{
						taikhoanktCo = rsNoco.getString("TAIKHOANKTCO") == null ? "" : rsNoco.getString("TAIKHOANKTCO");
						
						String taikhoanktNo = "";
						
						if(nhId.length() > 0){
							//LAY HET CAC SP TRONG NHAN HANG VA TINH RA PHAN TRAM PHAN BO
							query = "SELECT distinct * FROM ( \n " +
									" select case f.LOAIHANGHOA_FK when 0 then a.SANPHAM_FK when 1 then msc.TAISAN_FK when 3 then CCDC_FK end as spID, \n" +
									" case f.LOAIHANGHOA_FK when 0 then N'Sản phẩm' when 1 then N'Tài sản' when 2 then N'Chi phí dịch vụ' when 3 then N'Công cụ dụng cụ' end as loai, " +
									" c.PK_SEQ as LSP, e.pk_seq as LTS, m.PK_SEQ as LCCDC, \n" +
										 	    
						 			" CASE f.LOAIHANGHOA_FK WHEN 0 then (( SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = c.TAIKHOANKT_FK AND CONGTY_FK = f.CONGTY_FK  )) \n" +
						 			" WHEN 1 then (( SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE PK_SEQ = msc.TAIKHOAN_FK AND CONGTY_FK = f.CONGTY_FK  )) \n"+
						 			" WHEN 3 then (( SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = m.TAIKHOAN_FK AND CONGTY_FK = f.CONGTY_FK  )) \n"+
						 			" end TAIKHOANKTNO \n"+
						 	    
									" FROM ERP_NHANHANG_SANPHAM a  \n" +
									" left join ERP_SANPHAM b on a.SANPHAM_FK = b.PK_SEQ  \n" +
									" left join ERP_LOAISANPHAM c on b.LOAISANPHAM_FK = c.PK_SEQ \n" +
									" left join ERP_MASCLON msc on msc.PK_SEQ = a.TAISAN_FK \n" +
									" left join ERP_TAISANCODINH d on msc.TAISAN_FK = d.pk_seq \n" +
									" left join Erp_LOAITAISAN e on d.LOAITAISAN_FK = e.pk_seq \n" +
									" left join ERP_CONGCUDUNGCU k on a.CCDC_FK = k.PK_SEQ \n"+
									" left join Erp_LOAICCDC m on k.LOAICCDC_FK = m.PK_SEQ \n"+
									" inner join ERP_NHANHANG f on a.NHANHANG_FK = f.PK_SEQ \n" +
									" where a.NHANHANG_FK  IN  ( select NHANHANG_FK from ERP_CHIPHINHAPKHAU_NHANHANG where CHIPHINHAPKHAU_FK  = '" + Id + "' ) \n" +
									" and a.soluongNHAN * a.DONGIAVIET > 0 \n" +
									")DATA WHERE SPID = " + spId + "";
						}else{
							query = "SELECT distinct " + spId + " AS SPID, N'Sản phẩm' AS LOAI, TK.PK_SEQ AS TAIKHOANKTNO \n " +
									"FROM ERP_SANPHAM SP \n " +
									"INNER JOIN ERP_LOAISANPHAM LSP ON LSP.PK_SEQ = SP.LOAISANPHAM_FK \n " +
									"INNER JOIN ERP_TAIKHOANKT TK ON TK.SOHIEUTAIKHOAN = LSP.TAIKHOANKT_FK \n " +
									"WHERE SP.PK_SEQ = '" + spId+ "' \n " +
									"AND TK.CONGTY_FK IN (SELECT DISTINCT CONGTY_FK FROM  ERP_CHIPHINHAPKHAU WHERE PK_SEQ = '" + Id + "') ";
						}
						
						System.out.println("2.LAY DINH KHOAN NỢ CHO TIEN HANG: \n" + query + "\n----------------------------------------------");
						ResultSet rsTaikhoan = db.get(query);
						
						
						String doituong_no = "";
							String madoituong_no = "";
							String doituong_co = "";
							String madoituong_co = "";
							
							while(rsTaikhoan.next())
							{
								taikhoanktNo = rsTaikhoan.getString("TAIKHOANKTNO") == null ? "": rsTaikhoan.getString("TAIKHOANKTNO");
								
								doituong_no = rsTaikhoan.getString("loai");
								madoituong_no = rsTaikhoan.getString("spID");
								
								doituong_co = "Nhà cung cấp";
								madoituong_co = ncc;
																
								if(taikhoanktCo.trim().length() <= 0 || taikhoanktNo.trim().length() <= 0 )
								{
									msg = "CCPNK1.5 Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
									db.getConnection().rollback();
									return msg;
								}
								
								UtilityKeToan ukt = new UtilityKeToan("Chi phí nhập khẩu", Id, rsNoco.getString("ngay"), rsNoco.getString("ngay")
										, taikhoanktNo, taikhoanktCo, Double.toString(tienhang), Double.toString(tienhang)
										, "1", tiente_fk, thangNV, namNV
										, doituong_no, madoituong_no, doituong_co, madoituong_co);
								ukt.setTongGiaTri(Double.toString(tienhang));
								ukt.setTongGiaTriNT(Double.toString(tienhang));
								ukt.setKhoanMucNoCo("Tiền hàng");
								ukt.setMaChungTuNoCo(maChungTu);
								ukt.setDienGiaiNoCo(dienGiai);
								ukt.setDienGiai_CTNoCo(dienGiai);
								
								msg = ukt.Update_TaiKhoanByTaiKhoan_FK(db);
								
								//UPDATE NO-CO NEW
//								msg = util.Update_TaiKhoan( db, thangNV, namNV, rsNoco.getString("ngay"), rsNoco.getString("ngay"), "Chi phí nhập khẩu", Id, taikhoanktNo, taikhoanktCo, "", 
//															Double.toString(tienhang), Double.toString(tienhang), doituong_no, madoituong_no, doituong_co, madoituong_co, "0", "0", "0", tiente_fk, "0", "1", Double.toString(tienhang), Double.toString(tienhang), "Tiền hàng" );
								System.out.println("1-----MSG: " + msg);
								if(msg.trim().length() > 0)
								{
									rsNoco.close();
									rsTaikhoan.close();
									db.getConnection().rollback();
									return "CCPNK1.6 " + msg;
								}
							}
							rsTaikhoan.close();
						 
					}
					
				}
				
			 
				
				//Định khoản thuế : 1 dòng cho 1 hóa đơn
				if(tienthue > 0){
					String taikhoanktNo = "";
					query = "SELECT (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN ='13311000' AND CONGTY_FK = CPNK.CONGTY_FK) AS TAIKHOANKTNO \n" + 
							"FROM ERP_CHIPHINHAPKHAU CPNK \n" + 
							"WHERE CPNK.PK_SEQ = "+Id;
					System.out.println("3.LAY TIEN THUE: \n" + query + "\n------------------------------------------------------------");
					ResultSet rsTaikhoan = db.get(query);
					if(rsTaikhoan != null){
						String doituong_no = "";
						String madoituong_no = "";
						String doituong_co = "";
						String madoituong_co = "";
						
						while(rsTaikhoan.next())
						{
							taikhoanktNo = rsTaikhoan.getString("TAIKHOANKTNO")== null ? "": rsTaikhoan.getString("TAIKHOANKTNO");
							
							doituong_no = "";
							madoituong_no = "";
							
							doituong_co = "Nhà cung cấp";
							madoituong_co = ncc;
															
							if(taikhoanktCo.trim().length() <= 0 || taikhoanktNo.trim().length() <= 0 )
							{
								msg = "CCPNK1.7 Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
								db.getConnection().rollback();
								return msg;
							}
							
							UtilityKeToan ukt = new UtilityKeToan("Chi phí nhập khẩu", Id, ngay, ngay
									, taikhoanktNo, taikhoanktCo, Double.toString(tienthue), Double.toString(tienthue)
									, "1", tiente_fk, thangNV, namNV
									, doituong_no, madoituong_no, doituong_co, madoituong_co);
							ukt.setTongGiaTri(Double.toString(tienthue));
							ukt.setTongGiaTriNT(Double.toString(tienthue));
							ukt.setKhoanMucNoCo("Tiền thuế");
							ukt.setMaChungTuNoCo(maChungTu);
							ukt.setDienGiaiNoCo(dienGiai);
							ukt.setDienGiai_CTNoCo(dienGiai);
							
							msg = ukt.Update_TaiKhoanByTaiKhoan_FK(db);

							//UPDATE NO-CO NEW
//							msg = util.Update_TaiKhoan( db, thangNV, namNV, rsNoco.getString("ngay"), rsNoco.getString("ngay"), "Chi phí nhập khẩu", Id, taikhoanktNo, taikhoanktCo, "", 
//														Double.toString(tienthue), Double.toString(tienthue), doituong_no, madoituong_no, doituong_co, madoituong_co, "0", "0", "0", tiente_fk, "0", "1", Double.toString(tienthue), Double.toString(tienthue), "Tiền thuế" );
							System.out.println("2-----MSG: " + msg);
							if(msg.trim().length() > 0)
							{
								rsNoco.close();
								rsTaikhoan.close();
								db.getConnection().rollback();
								return "CCPNK1.8 " + msg;
							}
						}
						rsTaikhoan.close();
					}
				}
				
				 
				rsNoco.close();

	
			
			db.getConnection().commit();
			db.shutDown();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			db.update("rollback");
			
			return "CCPNK1.9 Lỗi khi chốt: " + e.getMessage();
		}
		
    	return msg;
	}

	private String taoPhanBo(dbutils db, String Id)
	{
		String query = "";
		String pnkIdstr = "";
		query = "select DISTINCT ISNULL(NHANHANG_FK, 0) AS NHID  from ERP_CHIPHINHAPKHAU_NHANHANG where CHIPHINHAPKHAU_FK = " + Id;
		
		ResultSet rs = db.get(query);
		try{
			while(rs.next()){
				if(pnkIdstr.length() > 0){
					pnkIdstr = pnkIdstr + "," + (rs.getString("NHID").equals("0")?"":rs.getString("NHID")) ;
				}else{
					pnkIdstr = "" + (rs.getString("NHID").equals("0")?"":rs.getString("NHID")) ;
				}
			}			
			rs.close();
		}catch(java.sql.SQLException e){
			e.printStackTrace();
		}
		
		if(pnkIdstr.length() > 0){
			query =
			   "  INSERT INTO ERP_CHIPHINHAPKHAU_PHANBO \n" +
		   	   "  (SOLO, CHIPHINHAPKHAU_FK, NHANHANG_FK , SANPHAM_FK, \n" +
		   	   "   TAISAN_FK, TIENHANG, TIENHANG_GOC, THUESUAT, TIENTHUE)  \n" +
		   	   
			   "  SELECT NHSP.SOLO, " + Id + ", CP_NH1.NHANHANG_FK, NHSP.SANPHAM_FK, \n" +
		   	   "  NHTS.TAISAN_FK, TIENCHUATHUE.TIENHANG * isNull(NHSP.thanhTienNhan, NHTS.thanhTienNhan) /TONGTIEN.TONGTIEN AS CPPHANBO, \n" +  
			   "  TIENCHUATHUE.TIENHANG, TIENCHUATHUE.THUESUAT,   \n" +
			   "  (TIENCHUATHUE.TIENHANG * isNull(NHSP.thanhTienNhan, NHTS.thanhTienNhan) /TONGTIEN.TONGTIEN) / 100 * TIENCHUATHUE.thuesuat as TIENTHUE\n" +
			   
			   "  FROM ERP_CHIPHINHAPKHAU CPNK      \n" +  
			   "  INNER JOIN ERP_CHIPHINHAPKHAU_NHANHANG CP_NH1 ON CPNK.pk_seq=CP_NH1.CHIPHINHAPKHAU_FK    \n" +  
			   "  INNER JOIN ERP_NHANHANG NH ON NH.PK_SEQ = CP_NH1.NHANHANG_FK      \n" +  
			   "  LEFT JOIN \n" +
			   "  (" +
			   "	select NHSP.NHANHANG_FK, NHSP.SANPHAM_FK, NHSP.SOLO,SUM(NHSP.soLuong) as soLuongNhan, SUM(NHSP.soLuong * NH.DONGIA) as thanhTienNhan \n" +
			   "  	from ERP_NHANHANG_SP_CHITIET NHSP\n" +
			   "  	INNER JOIN ERP_NHANHANG_SANPHAM NH ON NH.NHANHANG_FK = NHSP.NHANHANG_FK AND NHSP.SANPHAM_FK = NH.SANPHAM_FK" +
			   "  	group by NHSP.NHANHANG_FK, NHSP.SANPHAM_FK, NHSP.SOLO" +
			   "  ) \n" +
			   "  NHSP ON NHSP.NHANHANG_FK = NH.PK_SEQ \n" +  
			   
			   "  LEFT JOIN\n" +  
			   "  (" +
			   "	SELECT NHSP.NHANHANG_FK, NHSP.TAISAN_FK, '' as SOLO, SUM(SOLUONGNHAN) as soLuongNhan, SUM(NHSP.soLuongNhan * NHSP.DONGIA) as thanhTienNhan \n" +
			   "  	FROM ERP_NHANHANG_SANPHAM NHSP\n" +
			   "  	GROUP by NHSP.NHANHANG_FK, NHSP.TAISAN_FK" +
			   "  )NHTS ON NHTS.NHANHANG_FK = NH.PK_SEQ \n" + 
			   
			   "  LEFT JOIN(      \n" +  
			   "  	SELECT   SUM(SOLUONGNHAN * DONGIA) AS TONGTIEN     \n" +  
			   "  	FROM ERP_NHANHANG_SANPHAM      \n" +  
			   "  	WHERE NHANHANG_FK in ( select NHANHANG_FK from ERP_CHIPHINHAPKHAU_NHANHANG where CHIPHINHAPKHAU_FK= "+Id+")     \n" +  
			   "  ) TONGTIEN  on 1=1    \n" +  
			   
			   "  LEFT JOIN(      \n" +  
			   "  	SELECT  CPNKCT.CHIPHINHAPKHAU_FK, sum(ISNULL( CPNKCT.TIENHANG,0) + ISNULL(CPNKCT.TIENTHUE,0) ) as TIENHANG, SUM(CPNKCT.THUESUAT) as THUESUAT \n" +  
			   "  	FROM ERP_CHIPHINHAPKHAU_CHITIET CPNKCT      \n" +  
			   "  	INNER JOIN ERP_CHIPHINHAPKHAU CPNK ON CPNK.PK_SEQ = CPNKCT.CHIPHINHAPKHAU_FK      \n" +  
			   "  	WHERE CPNKCT.CHIPHINHAPKHAU_FK = " + Id + " \n" +  
			   "  	GROUP BY  CPNKCT.CHIPHINHAPKHAU_FK    \n" +  
			   "  )TIENCHUATHUE ON TIENCHUATHUE.CHIPHINHAPKHAU_FK = CPNK.PK_SEQ    \n" +  
			   
			   "  WHERE CPNK.PK_SEQ = " + Id + " AND (NHSP.SOLUONGNHAN > 0 or NHTS.SOLUONGNHAN > 0) \n" ;
		}else{
			
		}
	
		return "";
	}
	
	private	String capNhatMSC(dbutils db, String Id, String ngayChot)
	{
		String query = 
			" SELECT NHSP.DIENGIAI, NHSP.TAISAN_FK, NHSP.TONGTIEN*TIENCHUATHUE.TIENHANG/( TONGNHSP.TONGTIEN ) AS CPPHANBO \n"+
			
			", isNull((select cast(msc.PK_SEQ as nvarchar(50))\n" +
			"from ERP_MASCLON msc\n" +
			"inner join erp_TAISANCODINH ts on ts.PK_SEQ = msc.TAISAN_FK\n" +
			"where (ts.isDaThanhLy is null or ts.isDaThanhLy = 0) and msc.TRANGTHAI = 1 and msc.PK_SEQ = NHSP.TAISAN_FK), '') as maSC\n" +
			
			", isNull((select cast(ts.PK_SEQ as nvarchar(50)) + ' - ' + ts.dienGiai\n" +
			"from erp_TAISANCODINH ts\n" +
			"where ( ts.isDaThanhLy is null or ts.isDaThanhLy = 0) and ts.PK_SEQ = NHSP.TAISAN_FK), '') as maTaiSan\n" +
			", isNull((select 1 from ERP_TAISANCODINH ts inner join ERP_MASCLON msc on msc.TAISAN_FK = ts.pk_seq where msc.pk_seq = NHSP.TAISAN_FK), 0) as isTonTai\n" +
			", isNull((select 1 from ERP_TAISANCODINH ts inner join ERP_MASCLON msc on msc.TAISAN_FK = ts.pk_seq where msc.pk_seq = NHSP.TAISAN_FK and isDaThanhLy = 1), 0) as isDaThanhLy\n" +
			" FROM ERP_CHIPHINHAPKHAU CPNK \n"+
			" INNER JOIN ERP_CHIPHINHAPKHAU_NHANHANG NHCT ON NHCT.CHIPHINHAPKHAU_FK = CPNK.pk_seq \n"+
			" INNER JOIN ERP_NHANHANG NH ON NH.PK_SEQ = NHCT.NHANHANG_FK  AND NH.TRANGTHAI IN (1,2) \n"+
			" INNER JOIN ( \n"+ 
			" 	SELECT  NHANHANG_FK , SUM(SOLUONGNHAN * DONGIA * ISNULL(TYGIAQUYDOI,1) )  AS TONGTIEN  \n"+    
			" 	FROM ERP_NHANHANG_SANPHAM  \n"+
			" 	WHERE TAISAN_FK IS NOT NULL \n"+
			" 	GROUP BY NHANHANG_FK   \n"+
			" ) TONGNHSP ON TONGNHSP.NHANHANG_FK = NH.PK_SEQ  \n"+
			" INNER JOIN ( \n"+ 
			" 	SELECT  NHANHANG_FK , TAISAN_FK , SUM(SOLUONGNHAN * DONGIA * ISNULL(TYGIAQUYDOI,1) )  AS TONGTIEN , DIENGIAI \n"+    
			" 	FROM ERP_NHANHANG_SANPHAM  \n"+
			" 	WHERE TAISAN_FK IS NOT NULL \n"+
			" 	GROUP BY NHANHANG_FK , TAISAN_FK, DIENGIAI \n"+
			" ) NHSP ON NHSP.NHANHANG_FK = NH.PK_SEQ  \n"+
			" LEFT JOIN ( \n"+   
			"   SELECT  CPNKCT.CHIPHINHAPKHAU_FK, SUM(ISNULL( CPNKCT.TIENHANG,0)) as TIENHANG  \n"+   
			"   FROM ERP_CHIPHINHAPKHAU_CHITIET CPNKCT   \n"+     
			"   INNER JOIN ERP_CHIPHINHAPKHAU CPNK ON CPNK.PK_SEQ = CPNKCT.CHIPHINHAPKHAU_FK    \n"+    
			"   where CPNKCT.chiphinhapkhau_fk = "+Id+"   \n"+
			"   group by  CPNKCT.CHIPHINHAPKHAU_FK     \n"+ 
			"  ) TIENCHUATHUE ON TIENCHUATHUE.CHIPHINHAPKHAU_FK = CPNK.pk_seq \n" +
			"  WHERE 1 = 1 AND CPNK.PK_SEQ = " + Id + "";  
		
		System.out.println("CAP NHAT TienHANG:\n" + query + "\n-------------------------------------------");
		try{
			ResultSet rs = db.get(query);
			
			double CPPHANBO = 0;
			
			if(rs!=null)
			{
				while (rs.next())
				{
					CPPHANBO = rs.getDouble("CPPHANBO");
					String maSC = rs.getString("maSC");
					
					if(CPPHANBO > 0)
					{
						//Thêm điều chỉnh cho mã sửa chữa lớn
						String result = Erp_MaSCLon.InsertDieuChinhMSCL
						(db, maSC, ngayChot, Double.toString(CPPHANBO)
						, Id, "Chi phí nhận hàng nhập khẩu", "ERP_CHIPHINHAPKHAU");
	
						if (result.trim().length() > 0)
						{
							String msg = "CNMSC1.1 Không thể cập nhật mã sửa chữa lớn tài sản " + query;
							return msg;
						}
					}					
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			String msg = "CNMSC1.2 Không thể cập nhật mã sửa chữa lớn tài sản ";
			return msg;
		}
		return "";
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
	    
	    //lấy tiền phân bổ sản phẩm
	    // khai bao Severlet
	    String ServerletName = this.getServletName();
	    
	    IErpChiphinhapkhauList obj;
	    
		String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    
	    if(action.equals("new"))
	    {
    		IErpChiphinhapkhau cpnk = new ErpChiphinhapkhau();
    		cpnk.setCongtyId(ctyId);
    		cpnk.setUserId(userId);
    		cpnk.createRs();

	    	session.setAttribute("cpnkBean", cpnk);  	
    		session.setAttribute("userId", userId);
		
    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpChiPhiNhapKhauNew.jsp");
	    }
	    else
	    {
	    	obj = new ErpChiphinhapkhauList();
	    	obj.setCongtyId(ctyId);
		    obj.setUserId(userId);

	    	this.getSearchQuery(request, obj);
	    	
	    	obj.setUserId(userId);
	    	obj.init("");
	    	String querySearch = GiuDieuKienLoc.createParams(obj);
	 	    util.setSearchToHM(userId, session, ServerletName, querySearch);
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
		
    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpChiPhiNhapKhau.jsp");	
	    }
	}
	
	private void getSearchQuery(HttpServletRequest request, IErpChiphinhapkhauList obj) 
	{
		String poId = request.getParameter("poId");
		if(poId == null)
			poId = "";
		obj.setPoId(poId);

		String nccId = request.getParameter("nccId");
		if(nccId == null)
			nccId = "";
		obj.setNccId(nccId);

		String ncc = request.getParameter("ncc");
		if(ncc == null)
			ncc = "";
		obj.setNcc(ncc);

		String ghichu = request.getParameter("ghichu");
		if(ghichu == null)
			ghichu = "";
		obj.setDiengiai(ghichu);
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		String sotokhai = request.getParameter("sotokhai");
		if(sotokhai == null)
			sotokhai = "";
		obj.setSotokhai(sotokhai);
		
		
		String tungay = request.getParameter("tungay");
		if(tungay == null)
			tungay = "";
		obj.setTungay(tungay);
		
		String denngay = request.getParameter("denngay");
		if(denngay == null)
			denngay = "";
		obj.setDenngay(denngay);
		
		
		String nguoitao = request.getParameter("nguoitao");
		if(nguoitao == null)
			nguoitao = "";
		obj.setNguoitaoId(nguoitao);
		
		String tusotien = request.getParameter("tusotien");
		if(tusotien == null)
			tusotien = "";
		System.out.println("SOTIEN :"+ tusotien.replaceAll(",", ""));
		obj.setTusotien(tusotien.replaceAll(",", ""));
		
		String densotien = request.getParameter("densotien");
		if(densotien == null)
			densotien = "";
		obj.setDensotien(densotien.replaceAll(",", ""));		

//		String  sql =   " SELECT CP.PK_SEQ, CP.NGAY AS NGAYNHAP,  CP.GHICHU,CP.TRANGTHAI, B.TEN AS NGUOITAO, CP.NGAYTAO, C.TEN AS NGUOISUA, CP.NGAYSUA  , "+
//						" isnull( ( SELECT  ISNULL( TNK.SOCHUNGTU,'')   + ', ' AS [text()] FROM ERP_CHIPHINHAPKHAU_NHANHANG CP_NH  "+
//						" INNER JOIN ERP_NHANHANG NH ON NH.PK_SEQ=CP_NH.NHANHANG_FK "+
//						" INNER JOIN ERP_THUENHAPKHAU TNK ON TNK.PK_SEQ=NH.SOTOKHAI_FK "+  
//						" WHERE CP_NH.CHIPHINHAPKHAU_FK=CP.PK_SEQ "+
//						" for XML PATH ('') ),'')  AS SOTOKHAI  "+
//						" FROM ERP_CHIPHINHAPKHAU CP INNER JOIN NHANVIEN B ON CP.NGUOITAO = B.PK_SEQ "+   
//						" INNER JOIN NHANVIEN C ON CP.NGUOISUA = C.PK_SEQ    where 1=1  ";
					
		
//		if(poId.length() > 0){
//			sql += " and CP.PK_SEQ  in ( "+
//				   " SELECT CP_NH.CHIPHINHAPKHAU_FK "+
//				   " FROM ERP_CHIPHINHAPKHAU_NHANHANG CP_NH "+  
//				   " INNER JOIN ERP_NHANHANG NH ON NH.PK_SEQ=CP_NH.NHANHANG_FK "+  
//				   " INNER JOIN ERP_THUENHAPKHAU TNK ON TNK.PK_SEQ=NH.SOTOKHAI_FK "+ 
//				   " INNER JOIN ERP_THUENHAPKHAU_HOADONNCC TNKHD ON TNKHD.THUENHAPKHAU_FK=TNK.PK_SEQ "+
//				   " INNER JOIN ERP_HOADONNCC_DONMUAHANG HDNCC_DMH ON HDNCC_DMH.HOADONNCC_FK=TNKHD.HOADONNCC_FK "+
//				   " INNER JOIN ERP_MUAHANG MH ON MH.PK_SEQ=HDNCC_DMH.MUAHANG_FK "+
//				   " WHERE MH.SOPO LIKE '%"+poId+"%') ";
//		}
//		
//		if(nccId.length() > 0){
//			sql += " and CP.PK_SEQ  in ( SELECT CP_NH.CHIPHINHAPKHAU_FK "+ 
//			" FROM ERP_CHIPHINHAPKHAU_NHANHANG CP_NH "+
//			" INNER JOIN ERP_NHANHANG NH ON NH.PK_SEQ=CP_NH.NHANHANG_FK "+
//			" INNER JOIN ERP_THUENHAPKHAU TNK ON TNK.PK_SEQ=NH.SoToKhai_fk "+
//			" WHERE TNK.ncc_fk ="+nccId+" )";
//		}
//
//		if(ghichu.length() > 0)
//			sql += " and CP.ghichu like N'%" + ghichu + "%' ";
//		
//		if(trangthai.length() > 0)
//			sql += " and CP.trangthai = '" + trangthai + "' ";
//		
//		if(sotokhai.length() >0){
//			
//			sql=sql +" and cp.pk_seq in ( SELECT CP_NH.CHIPHINHAPKHAU_FK "+ 
//			" FROM ERP_CHIPHINHAPKHAU_NHANHANG CP_NH "+
//			" INNER JOIN ERP_NHANHANG NH ON NH.PK_SEQ=CP_NH.NHANHANG_FK "+
//			" INNER JOIN ERP_THUENHAPKHAU TNK ON TNK.PK_SEQ=NH.SoToKhai_fk "+
//			" WHERE TNK.SOCHUNGTU LIKE N'%"+sotokhai+"%' )";
//		}
//		sql +=  	" ORDER BY CP.NGAY DESC ";  
//		
//		System.out.println(sql);
//		return sql;
	}
	
	private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
}