package geso.traphaco.erp.servlets.nhanhangtrongnuoc;


import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility_Kho;
import geso.traphaco.center.util.*;

import geso.traphaco.erp.beans.nhanhangtrongnuoc.IErpNhanhangList_DonTH;
import geso.traphaco.erp.beans.nhanhangtrongnuoc.IErpNhanhang_DonTH;
import geso.traphaco.erp.beans.nhanhangtrongnuoc.imp.ErpNhanhangList_DonTH;
import geso.traphaco.erp.beans.nhanhangtrongnuoc.imp.ErpNhanhang_DonTH;

import java.io.IOException;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpNhanhangtrongnuoc_DonTHSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpNhanhangtrongnuoc_DonTHSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpNhanhangList_DonTH obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    
	    String nhId = util.getId(querystring);
	    
	    obj = new ErpNhanhangList_DonTH();
	    obj.setCongtyId((String)session.getAttribute("congtyId"));
	    
	    if (action.equals("delete"))
	    {	
	    	String msg = Delete(nhId);
	    	if(msg.length() > 0)
	    	{
	    		obj.setmsg(msg);
	    	}
	    	else //xoa thanh cong
	    	{
	    		String poId = request.getParameter("poId");
	    		IErpNhanhang_DonTH nhanhang = new ErpNhanhang_DonTH(nhId);
	    		
	    		//nhanhang.init();
	    		nhanhang.updateDonmuahang(poId.substring(5, poId.length()));
	    	}
	    }
	    else if(action.equals("chot"))
	    {
	    	if(action.equals("chot"))
	    	{
	    		String lhhId = request.getParameter("lhhId");
	    		if(lhhId == null)
	    			lhhId = "";
	    		
	    		String msg = ChotNhanHang(nhId, userId, obj.getCongtyId(), lhhId);
	    		obj.setmsg(msg);
	    	}
	    	else  //Chot thanh cong
	    	{
	    		String query = "select isnull(muahang_fk, trahang_fk) as muahang_fk from ERP_NHANHANG where pk_seq = '" + nhId + "'";
	    		dbutils db = new dbutils();
	    		ResultSet rs = db.get(query);
	    		
	    		if(rs != null)
	    		{
	    			try 
	    			{
						if(rs.next())
						{
							String poId = rs.getString("muahang_fk");
				    		IErpNhanhang_DonTH nhanhang = new ErpNhanhang_DonTH(nhId);
				    		
				    		nhanhang.updateDonmuahang(poId.substring(5, poId.length()));
						}
						rs.close();
					} 
	    			catch (Exception e) {}
	    		}
	    		db.shutDown();
	    	}
	    }
	    
	    obj.setUserId(userId);
	    obj.init("");
	    
		session.setAttribute("obj", obj);
		session.setAttribute("congtyId", obj.getCongtyId());
				
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHang_DonTH.jsp";
		response.sendRedirect(nextJSP);
	}

	Hashtable<String, Hashtable<String, Long> > nhan_form_choSelected = null; 
	private String ChotNhanHang(String nhId, String userId, String ctyId, String lhhId) 
	{
		dbutils db = new dbutils();
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			//Chot nhanhang trong nhapkho 
			String msg = chotNhanHang_SanPham(nhId, userId, ctyId, db);
			if(msg.trim().length() > 0)
			{
				db.getConnection().rollback();
				return "Không thể chôt nhận hàng - SP: " + msg;
			}
			
			db.getConnection().commit();
			db.shutDown();
			
			return "";
		} 
		catch (Exception e) 
		{
			try 
			{
				db.getConnection().rollback();
			} 
			catch (Exception e1) {}
			
			db.shutDown();
			
			return "Không thể chôt nhận hàng: " + e.getMessage();
		}
	}

	private boolean UpdatePhanBo(dbutils db, String ttchiphi_fk, String ctyId, double tonggiatri, String thang, String nam) 
	{
 
		
		String query = " select TTCPNHAN_FK, PHANTRAM,  case PHANTRAM when 1002 then " + tonggiatri + " * ( (	SELECT SUM(ISNULL(LNS_DBSP.DUKIENBAN,0) * ISNULL(BG_SP.GIABAN,0)) AS NUM " +
		"														FROM ERP_LAPNGANSACH_DUBAO LNS_DB " +
		"															INNER JOIN ERP_LAPNGANSACH_DUBAOSANPHAM LNS_DBSP ON LNS_DBSP.DUBAO_FK = LNS_DB.PK_SEQ  " +
		"															INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = LNS_DBSP.SANPHAM_FK AND SP.DVKD_FK = LNS_DB.DVKD_FK  " +
		"															LEFT JOIN ERP_LNSBANGGIABAN_KH BG_KH ON BG_KH.KH_FK =  LNS_DBSP.KHACHHANG_FK  " +
		"															LEFT JOIN ERP_LNSBANGGIABAN BG ON BG.PK_SEQ = BG_KH.BANGGIABAN_FK  " +
		"															LEFT JOIN ERP_LNSBGBAN_SANPHAM BG_SP ON BG_SP.BGBAN_FK = BG.PK_SEQ AND BG_SP.SANPHAM_FK = LNS_DBSP.SANPHAM_FK AND BG_SP.TRANGTHAI = 1  " +
		"														WHERE	LNS_DB.LAPNGANSACH_FK = ( select PK_SEQ from ERP_LAPNGANSACH where NAM = '" + nam + "' and HIEULUC = '1' ) AND LNS_DB.CONGTY_FK = '" + ctyId + "' AND SP.LOAISANPHAM_FK = '100005' AND LNS_DBSP.THANG = '" + thang + "' and LNS_DBSP.NAM = '" + nam + "' AND SP.DVKD_FK = '100000' " +
		"													)  " +
		"												/  " +
		"													(	 SELECT SUM(ISNULL(LNS_DBSP.DUKIENBAN,0) * ISNULL(BG_SP.GIABAN,0)) AS NUM  " +
		"														FROM ERP_LAPNGANSACH_DUBAO LNS_DB  " +
		"															INNER JOIN ERP_LAPNGANSACH_DUBAOSANPHAM LNS_DBSP ON LNS_DBSP.DUBAO_FK = LNS_DB.PK_SEQ  " +
		"															INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = LNS_DBSP.SANPHAM_FK AND SP.DVKD_FK = LNS_DB.DVKD_FK  " +
		"															LEFT JOIN ERP_LNSBANGGIABAN_KH BG_KH ON BG_KH.KH_FK =  LNS_DBSP.KHACHHANG_FK  " +
		"															LEFT JOIN ERP_LNSBANGGIABAN BG ON BG.PK_SEQ = BG_KH.BANGGIABAN_FK  " +
		"															LEFT JOIN ERP_LNSBGBAN_SANPHAM BG_SP ON BG_SP.BGBAN_FK = BG.PK_SEQ AND BG_SP.SANPHAM_FK = LNS_DBSP.SANPHAM_FK AND BG_SP.TRANGTHAI = 1  " +
		"														WHERE	LNS_DB.LAPNGANSACH_FK = ( select PK_SEQ from ERP_LAPNGANSACH where NAM = '" + nam + "' and HIEULUC = '1' ) AND LNS_DB.CONGTY_FK = '" + ctyId + "' AND SP.LOAISANPHAM_FK = '100005' AND LNS_DBSP.THANG = '" + thang + "' and LNS_DBSP.NAM = '" + nam + "' " +
			"													) )  " +
		"		when 1003 then " + tonggiatri + " * ( (	SELECT SUM(ISNULL(LNS_DBSP.DUKIENBAN,0) * ISNULL(BG_SP.GIABAN,0)) AS NUM  " +
		"														FROM ERP_LAPNGANSACH_DUBAO LNS_DB  " +
		"															INNER JOIN ERP_LAPNGANSACH_DUBAOSANPHAM LNS_DBSP ON LNS_DBSP.DUBAO_FK = LNS_DB.PK_SEQ  " +
		"															INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = LNS_DBSP.SANPHAM_FK AND SP.DVKD_FK = LNS_DB.DVKD_FK " +
		"															LEFT JOIN ERP_LNSBANGGIABAN_KH BG_KH ON BG_KH.KH_FK =  LNS_DBSP.KHACHHANG_FK  " +
		"															LEFT JOIN ERP_LNSBANGGIABAN BG ON BG.PK_SEQ = BG_KH.BANGGIABAN_FK  " +
		"															LEFT JOIN ERP_LNSBGBAN_SANPHAM BG_SP ON BG_SP.BGBAN_FK = BG.PK_SEQ AND BG_SP.SANPHAM_FK = LNS_DBSP.SANPHAM_FK AND BG_SP.TRANGTHAI = 1 " +
		"														WHERE	LNS_DB.LAPNGANSACH_FK = ( select PK_SEQ from ERP_LAPNGANSACH where NAM = '" + nam + "' and HIEULUC = '1' ) AND LNS_DB.CONGTY_FK = '" + ctyId + "' AND SP.LOAISANPHAM_FK = '100005' AND LNS_DBSP.THANG = '" + thang + "' and LNS_DBSP.NAM = '" + nam + "' AND SP.DVKD_FK = '100004' " +
		"													)   " +
		"												/  " +
		"													(	 SELECT SUM(ISNULL(LNS_DBSP.DUKIENBAN,0) * ISNULL(BG_SP.GIABAN,0)) AS NUM  " +
		"														FROM ERP_LAPNGANSACH_DUBAO LNS_DB  " +
		"															INNER JOIN ERP_LAPNGANSACH_DUBAOSANPHAM LNS_DBSP ON LNS_DBSP.DUBAO_FK = LNS_DB.PK_SEQ  " +
		"															INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = LNS_DBSP.SANPHAM_FK AND SP.DVKD_FK = LNS_DB.DVKD_FK  " +
		"															LEFT JOIN ERP_LNSBANGGIABAN_KH BG_KH ON BG_KH.KH_FK =  LNS_DBSP.KHACHHANG_FK  " +
		"															LEFT JOIN ERP_LNSBANGGIABAN BG ON BG.PK_SEQ = BG_KH.BANGGIABAN_FK  " +
		"															LEFT JOIN ERP_LNSBGBAN_SANPHAM BG_SP ON BG_SP.BGBAN_FK = BG.PK_SEQ AND BG_SP.SANPHAM_FK = LNS_DBSP.SANPHAM_FK AND BG_SP.TRANGTHAI = 1 " +
		"														WHERE	LNS_DB.LAPNGANSACH_FK = ( select PK_SEQ from ERP_LAPNGANSACH where NAM = '" + nam + "' and HIEULUC = '1' ) AND LNS_DB.CONGTY_FK = '" + ctyId + "' AND SP.LOAISANPHAM_FK = '100005' AND LNS_DBSP.THANG = '" + thang + "' and LNS_DBSP.NAM = '" + nam + "' " +
			"													) )  " +
		"		  else PHANTRAM * " + tonggiatri + " / 100 end as PhanBo    " +
		"	from ERP_TRUNGTAMCHIPHI_PHANBO  " +
		"	where TTCPCHO_FK = '" + ttchiphi_fk + "'  ";

		System.out.println("9.Lay chi phi: " + query);
		
		ResultSet rsPhanbo = db.get(query);
		if(rsPhanbo != null)
		{
			try
			{
				while(rsPhanbo.next())
				{
					String ttcpNhan_fk = rsPhanbo.getString("TTCPNHAN_FK");
					long tongphanbo = Math.round(rsPhanbo.getDouble("PhanBo"));
					
					if(tongphanbo > 0)
					{
						Hashtable<String, Long> ttcp_from_dacho = null;
						
						if(nhan_form_choSelected.get(ttcpNhan_fk) == null )
							ttcp_from_dacho = new Hashtable<String, Long>();
						else
							ttcp_from_dacho = nhan_form_choSelected.get(ttcpNhan_fk);
						
						nhan_form_choSelected.put(ttcpNhan_fk, ttcp_from_dacho);
						
						long giatriNhanDuoc = tongphanbo  - ( ttcp_from_dacho.get(ttcpNhan_fk) == null ? 0 : ttcp_from_dacho.get(ttcpNhan_fk) ) ;
						
						//Neu chua co thi INSERT
						query = " select count(*) as sodong from ERP_TRUNGTAMCHIPHI_THUCCHI " +
								" where TTCP_FK = '" + ttcpNhan_fk + "' and thang = '" + thang + "' and nam = '" + nam + "'  ";
						ResultSet rsCheck = db.get(query);
						if(rsCheck.next())
						{
							if(rsCheck.getInt("sodong") <= 0 )
							{
								query = "Insert ERP_TRUNGTAMCHIPHI_THUCCHI(TTCP_FK, Thang, Nam, ThucChi) " +
										"values('" + ttcpNhan_fk + "', '" + thang + "', '" + nam + "', '" + giatriNhanDuoc + "') ";
							}
							else
							{
								query = "update ERP_TRUNGTAMCHIPHI_THUCCHI set THUCCHI = THUCCHI +  " + giatriNhanDuoc + "  " +
										" where TTCP_FK = '" + ttcpNhan_fk + "' and thang = '" + thang + "' and nam = '" + nam + "'  ";
							}
							
							ttcp_from_dacho.put( ttchiphi_fk, ( ttcp_from_dacho.get(ttchiphi_fk) == null ? 0 : ttcp_from_dacho.get(ttchiphi_fk) ) + Math.round(tongphanbo) );
							
							if(!db.update(query))
							{
								rsPhanbo.close();
								
								System.out.println("1.Loi: " + query);
								return false;
							}
							
						}
						rsCheck.close();
						
						
						//Tu trung tam nhan nay, xet truong hop trung tam nhan co trung tam dc phan bo
						//////////Phai them cho kiem tra phan bo, ----> coi chung lap vo han
						if(!UpdatePhanBo(db, ttcpNhan_fk, ctyId, giatriNhanDuoc, thang, nam))
						{
							rsPhanbo.close();
							
							System.out.println("2222.Khong the cap nhat thuc chi, vui long kiem tra lai: " + query);
							return false;
						}
						
					}
					
				}
				rsPhanbo.close();
			}
			catch (Exception e) 
			{
				System.out.println("9.Exception phan bo: " + e.getMessage());
				return false;
			}
		}
		
		return true;
	}
	

	private String chotNhanHang_SanPham(String nhId, String userId, String ctyId, dbutils db)
	{
		String msg = "";
		Utility util=new Utility();
		Utility_Kho util_kho=new Utility_Kho();
		
		//Check khoa so thang
		//String sql = "select ngaychot from erp_nhanhang where pk_seq = '" + nhId + "'";
		String sql="  SELECT A.NGAYNHAN AS NGAYCHUNGTU, A.NGAYCHOT, C.LOAINHACUNGCAP_FK, KHONL_NHO_GC, B.NHACUNGCAP_FK , D.KHACHHANG_FK,  " +  
				   "  ISNULL(B.NGUONGOCHH, 'TN') AS NGUONGOC		 " +  
				   "  FROM ERP_NHANHANG A  " +  
				   "  LEFT JOIN  " +  
				   "  (  " +  
				   "  	SELECT DISTINCT TNK.PK_SEQ,HD_MH.MUAHANG_FK FROM ERP_THUENHAPKHAU TNK  	 " +  
				   "  	INNER JOIN ERP_THUENHAPKHAU_HOADONNCC TNKHD ON TNK.PK_SEQ=TNKHD.THUENHAPKHAU_FK   " +  
				   "  	INNER JOIN ERP_HOADONNCC_DONMUAHANG HD_MH ON TNKHD.HOADONNCC_FK=HD_MH.HOADONNCC_FK   " +  
				   "  ) " +  
				   "  TNK_MH ON TNK_MH.PK_SEQ=A.SOTOKHAI_FK  " +  
				   "  LEFT JOIN ERP_MUAHANG B ON ISNULL(A.MUAHANG_FK,TNK_MH.MUAHANG_FK) = B.PK_SEQ   " +  
				   "  LEFT JOIN ERP_NHACUNGCAP C ON B.NHACUNGCAP_FK = C.PK_SEQ  " +  
				   "  LEFT JOIN DONTRAHANG D ON A.TRAHANG_FK = D.PK_SEQ " +  
				   "  WHERE A.PK_SEQ ="+nhId;
		
		String ngaychotNV = "";
		String ngaychungtu = "";
		String loaiNCC = "";
		String khoNL_Nho_GC = "";
		String nccId = "";
		String khId = "";
		String nguongocHH = "";
		
		ResultSet rs = db.get(sql);
		try 
		{
			if(rs.next())
			{
				ngaychotNV = rs.getString("ngaychot");
				ngaychungtu = rs.getString("ngaychungtu");
				loaiNCC = rs.getString("LOAINHACUNGCAP_FK") == null ? "" : rs.getString("LOAINHACUNGCAP_FK");
				khoNL_Nho_GC = rs.getString("khoNL_Nho_GC") == null ? "" : rs.getString("khoNL_Nho_GC");
				nccId = rs.getString("nhacungcap_fk") == null ? "" : rs.getString("nhacungcap_fk");
				khId = rs.getString("khachhang_fk") == null ? "" : rs.getString("khachhang_fk");
				nguongocHH = rs.getString("NguonGoc");
				rs.close();
			}
		} 
		catch (Exception e) 
		{
			return "10.Vui lòng kiểm tra ngày chốt của nhận hàng " + e.getMessage();
		}
		
		String nam = ngaychotNV.substring(0, 4);
		String thang = ngaychotNV.substring(5, 7);
 
		if(nguongocHH.equals("NN"))  //NEU LA HANG NHAP KHAU, THI CAP NHAT LAI DON GIA VIET THEO TY GIA MOI
		{
			
		}
		
  		String query =  " select ISNULL(c.BATBUOCKIEMDINH,'')  as BATBUOCKIEMDINH , ISNULL(KHOCHOXULY_FK,0) AS KHOCHOXULY_FK ,isnull( NH.SOTOKHAI_FK ,0) AS SOTOKHAI_FK ,isnull( B.MUAHANG_FK ,0 ) AS MUAHANG_FK ,   isnull( nh.loaihanghoa_fk,'1') as loaihanghoa_fk  ,nh.congty_fk  , c.chuannen,isnull(c.trongluong,0) as trongluong ,c.ma as masp ,c.dvkd_fk as dvkdid, c.chungloai_fk as chungloaiid  ,SANPHAM_FK, b.KHONHAN," +
		  				" NoiDungNhap_fk, b.KHONHAN, SOLUONGNHAN, DONGIA, DONGIAVIET, b.TIENTE_FK, b.ngaynhandukien,  " +
						" ISNULL(c.kiemtradinhtinh, 0) as kiemdinhTinh, ISNULL(c.kiemtradinhluong, 0) as kiemdinhLuong, " +
						" c.LOAISANPHAM_FK, c.NguonGoc, c.ten + ' ' + c.quycach as spTen, ISNULL(c.MA, c.ma) as masanpham, nh.NCC_KH_FK KHACHHANG_FK  " +
						" from ERP_NHANHANG nh inner join ERP_NHANHANG_SANPHAM b on nh.PK_SEQ = b.NHANHANG_FK  " +
						" inner join ERP_SANPHAM c on b.SANPHAM_FK = c.PK_SEQ " +
						" where b.NHANHANG_FK = '" + nhId + "' and b.soluongnhan >0 ";
	  		
	  	//System.out.println("[ErpNhanhang_GiaySvl] INIT NHAN HANG: " + query );
	  	rs = db.get(query);
	
		try 
		{
			while(rs.next())
			{
				String batbuockiemdinh=rs.getString("BATBUOCKIEMDINH");
				
				String loaisanpham = rs.getString("LOAISANPHAM_FK");
				String khachhang = rs.getString("KHACHHANG_FK");
				String noidungnhap = rs.getString("NoiDungNhap_fk");
				String masanpham = rs.getString("SANPHAM_FK");
				String spTen = rs.getString("spTen");
				String khonhap = rs.getString("KHONHAN");
				String sotokhai = rs.getString("SOTOKHAI_FK");
				boolean IsKhoNhapQL_Khuvuc=false;
				if(util_kho.getIsQuanLyKhuVuc(khonhap, db).equals("1")){
					IsKhoNhapQL_Khuvuc=true;
				}
				
				double dongia = rs.getDouble("DONGIA");
				double dongiaViet = rs.getDouble("DONGIAVIET");
				String tiente_fk = rs.getString("TienTe_FK");
				float soluong =  rs.getFloat("SOLUONGNHAN");
				 
				String kiemdinh = "0";
				String trongluong=rs.getString("trongluong");
				String congtyid=rs.getString("congty_fk");
				String chuannen=rs.getString("chuannen");
				String masp=rs.getString("masp");
				String dvkdid=rs.getString("dvkdid");
				String ngaynhandukien=rs.getString("ngaynhandukien");
				
				String Khochoxuly=rs.getString("KHOCHOXULY_FK");
				
				
				// Cập nhật lại giá bình quan trong kho
				query=  " SELECT SUM(SOLUONG) AS SOLUONGTON , SUM(GIATON *SOLUONG) AS THANHTIENTON "+
						" FROM ERP_KHOTT_SANPHAM WHERE SANPHAM_FK ="+masanpham+"  AND SOLUONG >0";
				ResultSet rskho=db.get(query);
				double soluongton=0;
				double thanhtienton=0;
				
				if(rskho.next()){
					soluongton =rskho.getDouble("SOLUONGTON");
					thanhtienton =rskho.getDouble("THANHTIENTON");
				}
				rskho.close();
				double dongiaton=(thanhtienton + (dongiaViet* soluong) )/ (soluongton+soluong);
				
				
				
				if( (!rs.getString("kiemdinhTinh").equals("0") || !rs.getString("kiemdinhLuong").equals("0")) && batbuockiemdinh.equals("1"))
				{
					kiemdinh = "1";
					// tăng kho chờ xử lý nếu có kiểm định
					
					query =" select DONGIA ,b.NGAYSANXUAT,B.NGAYHETHAN,B.SOLO,B.KHU_FK,B.SOLUONG " +
					 	   " from ERP_NHANHANG_SANPHAM a inner join ERP_NHANHANG_SP_CHITIET b on a.NHANHANG_FK = b.NHANHANG_FK " +
					 	   "  and a.SANPHAM_FK = b.SANPHAM_FK  and b.ngaynhandukien=a.ngaynhandukien " +
					 	   " where  b.NHANHANG_FK = '" + nhId + "' " +
					 	   " and b.SANPHAM_FK = '" +masanpham+ "'  and a.ngaynhandukien='"+ngaynhandukien+"'";					
	
					ResultSet rsKho=db.get(query);
					while (rsKho.next()){
						
							 String ngaysanxuat=rsKho.getString("NGAYSANXUAT");
							 String NGAYHETHAN=rsKho.getString("NGAYHETHAN");
							 dongia=rsKho.getDouble("DONGIA");
							 String solo=rsKho.getString("solo");
							 String khuvuckho="";
							 boolean IsKhoCXL_QL_Khuvuc=false;
							 
							 if(util_kho.getIsQuanLyKhuVuc(Khochoxuly, db).equals("1")){
								 IsKhoCXL_QL_Khuvuc=true;
							 }
							 
							 if(IsKhoCXL_QL_Khuvuc==true ){
								 return "Không thể lưu kho khi kho chờ xử lý không có khu vực lưu kho";
							 } 
					
							 
							 String vitri = "100000";
							 double soluongct=rsKho.getDouble("soluong");
							 double booked=0;
							 double available =rsKho.getDouble("soluong");
							 String msg1= util_kho.Update_Kho_Sp(db,Khochoxuly, masanpham, soluongct,booked,available,dongiaton);
							 if(msg1.length() >0){
							 
								return msg1;
							 }
							 double CphiCapDong=0;
							 double CphiLuuKho=0;
							 double CphiNhapHang=0;
							 double ThueNhapkhau=0;
							 
							 msg1= util_kho.Update_Kho_Sp_Chitiet(db,Khochoxuly, masanpham, soluongct,booked,available,dongia, solo,vitri,khuvuckho,ngaychotNV,ngaychotNV,ngaysanxuat,NGAYHETHAN,CphiCapDong,CphiLuuKho,CphiNhapHang,ThueNhapkhau);
							 if(msg1.length() >0){
									 
									return msg1;
							 }
	
							 // số lượng đạt
							 //vào kho xử lý tất cả là hàng không đạt ,trangthai=0 là hàng đạt,-1 là hàng không đạt
							 if(util_kho.IsKhoQuanLyTrangThai(Khochoxuly, db)){
								 //kho chờ xử lý thì hàng ở trạng thái không đạt
								 String trangthaisp="-1";
								 msg1= util_kho.Update_Kho_Sp_Chitiet_TrangThai( db,Khochoxuly, masanpham, soluongct,booked,available,dongia, solo,vitri,khuvuckho,ngaychotNV,ngaychotNV,ngaysanxuat,NGAYHETHAN,CphiCapDong,CphiLuuKho,CphiNhapHang,ThueNhapkhau,trangthaisp);
								 
								 if(msg1.length() >0){
									 return msg1;	
								 }
							 }
							//không tăng kho khi nhận hàng,kiểm định xong thì đưa hết vào kho chờ xử lý
							query="update ERP_NHANHANG_SANPHAM set danhan='0' where ngaynhandukien='"+ngaynhandukien+"' and SANPHAM_FK = '" + masanpham + "'  and nhanhang_fk="+nhId+" and khonhan="+khonhap+"";
							if(!db.update(query))
							{
								rs.close();
								return "14.Không thể cập nhật ERP_NHANHANG_SANPHAM: " + query;
							}
					}
					// tăng kho chờ xử lý ngay lên có kiểm định
				}else{
					// tăng thẳng kho nhận hàng nếu không qua kiểm định 
					
					query="update ERP_NHANHANG_SANPHAM set danhan='1' where SANPHAM_FK = '" + masanpham + "'  and nhanhang_fk="+nhId+" and khonhan="+khonhap+" and ngaynhandukien='"+ngaynhandukien+"'";
					if(!db.update(query))
					{
						rs.close();
						return "14.Không thể cập nhật ERP_NHANHANG_SANPHAM: " + query;
					}
					
					
					query =" select a.KHONHAN ,DONGIA ,b.NGAYSANXUAT,B.NGAYHETHAN,B.SOLO,B.KHU_FK,B.SOLUONG " +
					 	   " from ERP_NHANHANG_SANPHAM a inner join ERP_NHANHANG_SP_CHITIET b on a.NHANHANG_FK = b.NHANHANG_FK  " +
					 	   " and a.SANPHAM_FK = b.SANPHAM_FK  and b.ngaynhandukien=a.ngaynhandukien " +
					 	   " where  b.NHANHANG_FK = '" + nhId + "' " +
					 	   " and b.SANPHAM_FK = '" +masanpham+ "' and a.ngaynhandukien='"+ngaynhandukien+"'";					
					
					ResultSet rsKho=db.get(query);
					while (rsKho.next()){
						 String ngaysanxuat=rsKho.getString("NGAYSANXUAT");
						 String NGAYHETHAN=rsKho.getString("NGAYHETHAN");
						 dongia=rsKho.getDouble("DONGIA");
						 String solo=rsKho.getString("solo");
						 String khuvuckho= rsKho.getString("KHU_FK")==null?"":rsKho.getString("KHU_FK");
						 	
						 if(IsKhoNhapQL_Khuvuc==true && rsKho.getString("KHU_FK")==null){
							 return "Không thể lưu kho khi kho nhận hàng không có khu vực lưu kho";
						 }
						 
						 String vitri = "100000";
						 double soluongct=rsKho.getDouble("soluong");
						 double booked=0;
						 double available =rsKho.getDouble("soluong");
						 String msg1= util_kho.Update_Kho_Sp(db,khonhap, masanpham, soluongct,booked,available,dongiaton);
						 if(msg1.length() >0){
							return msg1;
						 }
						 double CphiCapDong=0;
						 double CphiLuuKho=0;
						 double CphiNhapHang=0;
						 double ThueNhapkhau=0;
					 
						 msg1= util_kho.Update_Kho_Sp_Chitiet(db,khonhap, masanpham, soluongct,booked,available,dongia, solo,vitri,khuvuckho,ngaychotNV,ngaychotNV,ngaysanxuat,NGAYHETHAN,CphiCapDong,CphiLuuKho,CphiNhapHang,ThueNhapkhau);
						 if(msg1.length() >0){
								 
								return msg1;
						 }
	
						 // số lượng đạt
						 //vào kho xử lý tất cả là hàng không đạt ,trangthai=0 là hàng đạt
						 if(util_kho.IsKhoQuanLyTrangThai(khonhap, db)){
							 //không có kiểm định thì cho hàng là đạt thẳng vào kho nhận 
							 String trangthaisp="0";
							 msg1= util_kho.Update_Kho_Sp_Chitiet_TrangThai( db,khonhap, masanpham, soluongct,booked,available,dongia, solo,vitri,khuvuckho,ngaychotNV,ngaychotNV,ngaysanxuat,NGAYHETHAN,CphiCapDong,CphiLuuKho,CphiNhapHang,ThueNhapkhau,trangthaisp);
							 if(msg1.length() >0){
								 return msg1;	
							 }
						 }
					
						}
				}
				
				
					if(kiemdinh.equals("1"))
					{ //Tao yeu cau kiem dinh
				 
					// CHƯA TĂNG KHO,BÁO CÁO XUẤT NHẬP TỒN CHƯA CÓ KHO CỦA SẢN PHẨM CHỜ KIỂM ĐỊNH
					
						query = "  insert ERP_YeuCauKiemDinh(nhanhang_fk, ngaynhan, sanpham_fk, solo, soluong, ngaysanxuat, ngayhethan, CongTy_FK, trangthai, dinhluong, dinhtinh, nguoitao, ngaytao, nguoisua, ngaysua)  " +
								"  select '" + nhId + "', a.NgayNhanDuKien, a.SANPHAM_FK, a.SOLO, a.SOLUONG, a.NGAYSANXUAT, a.NGAYHETHAN, '" + ctyId + "', '0', b.kiemtradinhluong, b.kiemtradinhtinh, '" + userId + "', '" + getDateTime() + "', '" + userId + "', '" + getDateTime() + "'  " +
								"  from ERP_NHANHANG_SP_CHITIET a inner join ERP_SANPHAM b on a.sanpham_fk = b.pk_seq   " +
								"  where a.NHANHANG_FK = '" + nhId + "' and a.SANPHAM_FK = '" + masanpham + "'   and a.ngaynhandukien='"+ngaynhandukien+"'";
							
							if(!db.update(query))
							{
								msg = "16.Khong the tao moi ERP_YeuCauKiemDinh: " + query; 
								rs.close();
								return msg;
							}		
					
					//Cap nhat gia theo LO
					
					
						/***** Luu lai gia nhap theo lo **********/
						query = " Update ERP_NHANHANG_SP_CHITIET " +
								" set GiaTheoLo = ( select top(1) DONGIA from ERP_MUAHANG_SP where SANPHAM_FK = '" + masanpham + "' and MUAHANG_FK = ( select muahang_fk from ERP_NhanHang where pk_seq = '" + nhId + "' ) and NGAYNHAN = '" + rs.getString("ngaynhandukien") + "' ) " +
								" where sanpham_fk = '" + masanpham + "' and ngaynhandukien = '" + rs.getString("ngaynhandukien") + "' and nhanhang_fk = '" + nhId + "'";
						
						if(!db.update(query))
						{
							rs.close();
							return "17.Khong the cap nhat ERP_NHANHANG_SP_CHITIET: " + query;
						}
						
					/*****************************************/
					
				}
				
					
					// cập nhật lại giá ở tất cả các kho về giá tồn
					
					query="UPDATE   ERP_KHOTT_SANPHAM SET GIATON="+dongiaton+" WHERE SANPHAM_FK ="+masanpham;
					if(!db.update(query))
					{
						rs.close();
						return "17.KHông thể cập nhật : " + query;
					}
					
		/******************* TIEU HAO ******************************/
				if( !noidungnhap.equals("100004") && loaiNCC.equals("100004") && loaisanpham.equals("100012") )  //Nha cung cap nho gia cong ---- > Tieu hao nguyen lieu ( khong phai dontrahang + loaisanpham la TP NHO GIA CONG )
				{
					// tao ra 1 chung tu ghi nhan lai tieu hao 
					query=  " INSERT INTO  ERP_TIEUHAO_NHANHANG(TRANGTHAI,NGUOITAO,NGAYTAO,NGUOISUA,NGAYSUA,DIENGIAI,NGAYGHINHAN,GIOGHINHAN,NGAYTHUCTE,NHANHANG_FK) VALUES" +
							" (1,"+userId+",'"+this.getDateTime()+"',"+userId+",'"+this.getDateTime()+"',N'Tiêu hao tự động nhờ gia công','"+ngaychotNV+"','"+util.getTime()+"',getdate(),"+nhId+") ";
					//System.out.println("Error: "+query);
					if(!db.update(query))
					{
						rs.close();
						return "17.Khong the cap nhat ERP_TIEUHAO_NHANHANG: " + query;
					}
					query = "select IDENT_CURRENT('ERP_TIEUHAO_NHANHANG') as nhId";
					String tieuhaoId ="";
					ResultSet rsNh = db.get(query);
					if (rsNh.next())
					{
						tieuhaoId = rsNh.getString("nhId");
						rsNh.close();
					}
					rsNh.close();
					
					 
				 		if(!dvkdid.equals("100004")){
							query = " select  a.SOLUONGCHUAN, b.VATTU_FK, b.SOLUONG, c.ten + '' + c.quycach as vattuTen " +
							" from ERP_DANHMUCVATTU a inner join ERP_DANHMUCVATTU_VATTU b on a.PK_SEQ = b.DANHMUCVT_FK inner join ERP_SANPHAM c on b.vattu_fk = c.pk_seq " +
							" where a.mavattu = '" + masp + "' and a.HIEULUCTU <= '" + ngaychotNV + "' and a.HIEULUCDEN >= '" + ngaychotNV + "' and a.TRANGTHAI = '1' and a.congty_fk="+congtyid;
						}else{
						 
							if(rs.getString("chungloai_fk").equals("100040")){
								query = " select   a.SOLUONGCHUAN, b.VATTU_FK, b.SOLUONG, c.ten + '' + c.quycach as vattuTen " +
								" from ERP_DANHMUCVATTU a inner join ERP_DANHMUCVATTU_VATTU b on a.PK_SEQ = b.DANHMUCVT_FK inner join ERP_SANPHAM c on b.vattu_fk = c.pk_seq " +
								" where a.mavattu= '" + masp + "' and trongluong="+trongluong+" and a.HIEULUCTU <= '" + ngaychotNV + "' and a.HIEULUCDEN >= '" + ngaychotNV + "' and a.TRANGTHAI = '1' and a.congty_fk="+congtyid;
								
							}else if(rs.getString("chungloai_fk").equals("100031")){
								query = " select   a.SOLUONGCHUAN, b.VATTU_FK, b.SOLUONG, c.ten + '' + c.quycach as vattuTen " +
								" from ERP_DANHMUCVATTU a inner join ERP_DANHMUCVATTU_VATTU b on a.PK_SEQ = b.DANHMUCVT_FK inner join ERP_SANPHAM c on b.vattu_fk = c.pk_seq " +
								" where chuannen= '" + chuannen + "' and  and a.loaichungloai=1 and a.HIEULUCTU <= '" + ngaychotNV + "' and a.HIEULUCDEN >= '" + ngaychotNV + "' and a.TRANGTHAI = '1' and a.congty_fk="+congtyid;
							}else{
								query = " select  a.SOLUONGCHUAN, b.VATTU_FK, b.SOLUONG, c.ten + '' + c.quycach as vattuTen " +
								" from ERP_DANHMUCVATTU a inner join ERP_DANHMUCVATTU_VATTU b on a.PK_SEQ = b.DANHMUCVT_FK inner join ERP_SANPHAM c on b.vattu_fk = c.pk_seq " +
								" where chuannen= '" + chuannen + "' and  and a.loaichungloai=2 and a.HIEULUCTU <= '" + ngaychotNV + "' and a.HIEULUCDEN >= '" + ngaychotNV + "' and a.TRANGTHAI = '1' and a.congty_fk="+congtyid;
							}
						}
					 
					System.out.println("----LAY BOM: " + query);
					ResultSet rsBom = db.get(query);
					
					boolean exitsBOM = false;
						
						while(rsBom.next())
						{
							exitsBOM = true;
							
							String vattu_fk = rsBom.getString("VatTu_FK");
							
							double soluongTieuHao = rsBom.getDouble("SoLuong") * soluong / rsBom.getDouble("SOLUONGCHUAN");
							//Check kho
							
							if(soluongTieuHao<=0){
								msg="Không xác định được số lượng tiệu hao trong bom của sản phẩm :"+rsBom.getString("vattuTen");
								return msg;
							}
							query = " select b.ten as spTen, soluong, available " +
									" from ERP_KHOTT_SANPHAM a inner join ERP_SANPHAM b on a.sanpham_fk = b.pk_seq " +
									" where sanpham_fk = '" + vattu_fk + "' and khott_fk = '" + khoNL_Nho_GC + "' ";
							
							String tensp="";
							//System.out.println("----CHECK ERP_KHOTT_SANPHAM: "+query);
							ResultSet  rsCheck = db.get(query);
							if(rsCheck != null && rsCheck.next())
							{
								
								tensp=rs.getString("spTen");
								if(rsCheck.getDouble("available") < soluongTieuHao )
								{
									msg = "15.Vật tư (" + rsCheck.getString("spTen") + ") dùng trong BOM của sản phẩm (" + spTen + ") không đủ số lượng trong kho để tiêu hao." ;
									rs.close();
									rsBom.close();
									rsCheck.close();
									return msg;
								}
							}
							else
							{
								msg = "16.Vật tư (" + rsBom.getString("vattuTen") + ") dùng trong BOM của sản phẩm (" + spTen + ") không có trong kho để tiêu hao.";
								rs.close();
								rsBom.close();
								return msg;
							}
							//Cap nhat
							query = " Update ERP_KHOTT_SANPHAM set   soluong = soluong - '" + soluongTieuHao + "', available = available - '" + soluongTieuHao + "' " +
									" where sanpham_fk = '" + vattu_fk + "' and khott_fk = '" + khoNL_Nho_GC + "' ";
							
							 
							if( db.updateReturnInt(query)!=1)
							{
								msg = "18.Khong the cap nhat ERP_KHOTT_SANPHAM: " + query;
								rsBom.close();
								rs.close();
								return msg;
							}
							
							query="INSERT INTO  ERP_TIEUHAO_NHANHANG_SP (TIEUHAO_FK,SANPHAM_FK,SOLUONG,KHOTT_FK  ) " +
									" VALUES('"+tieuhaoId+"','"+vattu_fk+"',"+soluongTieuHao+","+khoNL_Nho_GC+")";
							//System.out.println("ERP_TIEUHAO_NHANHANG_SP: "+query);
							if(!db.update(query))
							{
								msg = "18.Khong the cap nhat ERP_TIEUHAO_NHANHANG_SP: " + query;
								return msg;
							}
							//Tu dong cap nhat lo het han truoc nhat
							
								query = " select SANPHAM_FK, SOLO, BIN, AVAILABLE  " +
										" from ERP_KHOTT_SP_CHITIET_NCC   " +
										" where KHOTT_FK = '" + khoNL_Nho_GC + "' and sanpham_fk = '" + vattu_fk + "' and NCC_FK = '" + nccId + "' " +
										" order by NGAYHETHAN asc, NGAYNHAPKHO asc ";
								//System.out.println("Lay Kho Chi Tiet : "+query);
								
								String query2 = "";
								String query3  ="";
								ResultSet rsUpdate = db.get(query);
								while (rsUpdate.next())
								{
									double slgTH = rsUpdate.getDouble("AVAILABLE");
									if(soluongTieuHao <= slgTH)
									{
										query = "Update ERP_KHOTT_SP_CHITIET_NCC set soluong = soluong - '" + soluongTieuHao + "', AVAILABLE = AVAILABLE - '" + soluongTieuHao + "' " +
												"where sanpham_fk = '" + vattu_fk + "' and khott_fk = '" + khoNL_Nho_GC + "' and bin = '" + rsUpdate.getString("BIN") + "' and solo = '" + rsUpdate.getString("SOLO") + "' and NCC_FK = '" + nccId + "' ";
										
										query2 = "Update ERP_KHOTT_SP_CHITIET set soluong = soluong - '" + soluongTieuHao + "', AVAILABLE = AVAILABLE - '" + soluongTieuHao + "' " +
												"where sanpham_fk = '" + vattu_fk + "' and khott_fk = '" + khoNL_Nho_GC + "' and bin = '" + rsUpdate.getString("BIN") + "' and solo = '" + rsUpdate.getString("SOLO") + "' ";
										
										query3=" INSERT INTO  ERP_TIEUHAO_NHANHANG_SP_CHITIET (TIEUHAO_FK,SANPHAM_FK,SOLUONG,SOLO ) " +
										" VALUES('"+tieuhaoId+"','"+vattu_fk+"',"+soluongTieuHao+",'"+ rsUpdate.getString("SOLO")+"')";
									 
										
										soluongTieuHao = 0;
									}
									else
									{
										soluongTieuHao -= slgTH;
										
										query = "Update ERP_KHOTT_SP_CHITIET_NCC set soluong = soluong - '" + slgTH + "', AVAILABLE = AVAILABLE - '" + slgTH + "' " +
												"where sanpham_fk = '" + vattu_fk + "' and khott_fk = '" + khoNL_Nho_GC + "' and bin = '" + rsUpdate.getString("BIN") + "' and solo = '" + rsUpdate.getString("SOLO") + "' and NCC_FK = '" + nccId + "' ";
										
										query2 = "Update ERP_KHOTT_SP_CHITIET set soluong = soluong - '" + slgTH + "', AVAILABLE = AVAILABLE - '" + slgTH + "' " +
												 "where sanpham_fk = '" + vattu_fk + "' and khott_fk = '" + khoNL_Nho_GC + "' and bin = '" + rsUpdate.getString("BIN") + "' and solo = '" + rsUpdate.getString("SOLO") + "' ";
									
										
										query3="INSERT INTO  ERP_TIEUHAO_NHANHANG_SP_CHITIET (TIEUHAO_FK,SANPHAM_FK,SOLUONG,SOLO ) " +
										" VALUES('"+tieuhaoId+"','"+vattu_fk+"',"+slgTH+",'"+ rsUpdate.getString("SOLO")+"')";
 
										}
										
								/*	System.out.println("query : "+query);
									System.out.println("query2: "+query2);
									System.out.println("query3: "+query3);
								*/	
									
									
									
							 
									if( db.updateReturnInt(query)!=1)
									{
										msg = "19.Khong the cap nhat ERP_KHOTT_SP_CHITIET_NCC: " + query;
										 
										rsBom.close();
										rsCheck.close();
										rs.close();
										return msg;
									}
									
									 
									if( db.updateReturnInt(query2)!=1)
									{
										msg = "20.Khong the cap nhat ERP_KHOTT_SP_CHITIET: " + query;
										 
										rsBom.close();
										rsCheck.close();
										rs.close();
										return msg;
									}
								 
									if( db.updateReturnInt(query3)!=1)
									{
										msg = "18.Khong the cap nhat ERP_TIEUHAO_NHANHANG_SP: " + query;
										return msg;
									}
									if(soluongTieuHao==0){
										break;
									}
								}
								// sau khi đua vao chi tiet,nhung so lượng tiêu hao chi tiêt không đủ
								if(soluongTieuHao >0){
									msg = "Số lượng trong kho chi tiết của sản phẩm [ "+tensp+"] không đủ để tiêu hao ";
									return msg;
								}
						}
						
						
						rsBom.close();
						
						if(!exitsBOM)
						{
							msg = "21.Vui lòng kiểm tra lại thông tin BOM của sản phẩm: " + spTen + " trong nhận hàng." + query;
							//System.out.println(msg);
							rs.close();
							return msg;
						}
						
					
					
				}
		/******************* END TIEU HAO ******************************/		
				
				// GHI NHÂN KẾ TOÁN : TIỀN HÀNG (SL*DONGIATRA), GIAVON

				int thangtruoc = Integer.parseInt(ngaychotNV.substring(5, 7));
				int namtruoc = Integer.parseInt(ngaychotNV.substring(0, 4));
				
				if(thangtruoc == 1){
					namtruoc = namtruoc-1;
					thangtruoc = 12;
					
				}else{
					thangtruoc = thangtruoc-1;
				}
				
				
				String taikhoanktCo = "";
				String taikhoanktNo = "";
				
				String doituong_co = "";
				String madoituong_co = "";
				String doituong_no = "";
				String madoituong_no = "";
				
				String queryTK = "";
				
				
				// TIEN HANG
				queryTK = 	" select a.TAIKHOAN_FK as TAIKHOANKTCO,"
						  + "        case "+ loaisanpham +" when 100041 then ( select pk_seq from erp_taikhoankt where sohieutaikhoan = '53111000' ) " 
						  +	"		 						when 100042 then ( select pk_seq from erp_taikhoankt where sohieutaikhoan = '53111000' )  "
					      + " 						        when 100043 then ( select pk_seq from erp_taikhoankt where sohieutaikhoan = '53112000' ) end "
					      + "        as TAIKHOANKTNO, b.NCC_KH_FK KHACHHANG_FK "
						  + " from   ERP_NHANHANG b INNER JOIN ERP_KHACHHANG a ON b.NCC_KH_FK = a.PK_SEQ " +
							" where  b.pk_seq = '" + nhId + "' ";
				
				doituong_no = "";
				madoituong_no = "";
				doituong_co = "Khách hàng";
				madoituong_co = khachhang;
					

				
				System.out.println("__QUERY TAI KHOAN: " + queryTK );
				
				if(queryTK.trim().length() > 0)
				{
					ResultSet tkRs = db.get(queryTK);
					if(tkRs != null)
					{
						if(tkRs.next())
						{
							taikhoanktCo = tkRs.getString("TAIKHOANKTCO");
							taikhoanktNo = tkRs.getString("TAIKHOANKTNO");
							tkRs.close();
						}
						
						if(taikhoanktCo.trim().length() <= 0 || taikhoanktNo.trim().length() <= 0)
						{
							msg = "222.Loại sản phẩm và nội dung nhập tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
							rs.close();
							tkRs.close();
							return msg;
						}
						
						
						double thanhtien = dongiaViet * soluong;
						msg = util.Update_TaiKhoan( db, thang, nam, ngaychungtu, ngaychotNV, "Nhận hàng", nhId, taikhoanktNo, taikhoanktCo, noidungnhap, 
													Double.toString(thanhtien), Double.toString(thanhtien), doituong_no, madoituong_no, doituong_co, madoituong_co, "0", Float.toString(soluong), Double.toString(dongiaViet), tiente_fk, Double.toString(dongia), "1", dongiaViet + "*" + soluong, dongia + "*" + soluong, "Nhận hàng - Tiền hàng" );
						if(msg.trim().length() > 0)
						{
							rs.close();
							tkRs.close();
							return msg;
						}

					}	
				}
				
				// GIAVON
				query = " SELECT SANPHAM_FK, GIATON, "+
						"        (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE PK_SEQ = "+ loaisanpham +") as TAIKHOANKTNO ,"+
						"        case "+ loaisanpham +" when 100041 then ( select pk_seq from erp_taikhoankt where sohieutaikhoan = '63211000' )  " +
						"         						when 100042 then ( select pk_seq from erp_taikhoankt where sohieutaikhoan = '63211000' )  " +
					    " 						        when 100043 then ( select pk_seq from erp_taikhoankt where sohieutaikhoan = '63212000' ) end "+
						" 		as TAIKHOANKTCO "+
					    " FROM ERP_TONKHOTHANG " +
						" WHERE SANPHAM_FK = " + masanpham + " AND  THANG = '" + thangtruoc + "' " +
						" AND NAM = '" + namtruoc + "' AND KHOTT_FK = '" + khonhap + "'";
				
				System.out.println(query);
				
				doituong_no = "Sản phẩm";
				madoituong_no = masanpham;
				doituong_co = "" ;
				madoituong_co = "";
				
				ResultSet rsgia = db.get(query);
				if(rsgia.next()){
					
					dongiaViet = rsgia.getDouble("GIATON");
					taikhoanktCo = rsgia.getString("TAIKHOANKTCO");
					taikhoanktNo = rsgia.getString("TAIKHOANKTNO");
					
					if(taikhoanktCo.trim().length() <= 0 || taikhoanktNo.trim().length() <= 0)
					{
						msg = "222.Loại sản phẩm và nội dung nhập tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
						rs.close();
						rsgia.close();
						return msg;
					}
					
					
					double thanhtien = dongiaViet * soluong;
					msg = util.Update_TaiKhoan( db, thang, nam, ngaychungtu, ngaychotNV, "Nhận hàng", nhId, taikhoanktNo, taikhoanktCo, noidungnhap, 
												Double.toString(thanhtien), Double.toString(thanhtien), doituong_no, madoituong_no, doituong_co, madoituong_co, "0", Float.toString(soluong), Double.toString(dongiaViet), tiente_fk, Double.toString(dongia), "1", dongiaViet + "*" + soluong, dongia + "*" + soluong, "Nhận hàng - Tiền hàng" );
					if(msg.trim().length() > 0)
					{
						rs.close();
						rsgia.close();
						return msg;
					}
				}
				rsgia.close();
				
					
			}
			rs.close();
			
			query = "update ERP_NHANHANG set trangthai = '1', giochot = '" + getTime() + "',  ngaysua = '" + getDateTime() + "', nguoisua = '" + userId + "' where pk_seq = '" + nhId + "'";
			if(!db.update(query))
			{
				return "25.Khong the cap nhat ERP_NHANHANG: " + query;
			}
			
			//CAP NHAT LAI MUA HANG
			query = "update DONTRAHANG set trangthai = '5' where pk_seq in ( select trahang_fk from ERP_NHANHANG where pk_seq = '" + nhId + "' ) ";
			if(!db.update(query))
			{
				return "26.Khong the cap nhat ERP_NHANHANG: " + query;
			}
		 
			// chuyen trang thai cua mua hang
			if(CheckDahoantat(db,nhId)){
				query="UPDATE  DONTRAHANG   SET TRANGTHAI=6  WHERE  PK_SEQ= (SELECT trahang_fk FROM ERP_NHANHANG WHERE PK_SEQ='"+nhId+"' ) ";
				if(!db.update(query))
				{
					return "27. Chưa hoàn tất đơn mua hàng: " + query;
				}
			}
			
			 
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			db.update("rollback");
			return "Không thể chốt nhận hàng: " + e.getMessage();
		}

		 return msg;
	}
	public boolean CheckDahoantat(dbutils db, String idnhanhang ){
		
	 
		
		String query=" SELECT MUAHANG.SANPHAM_FK, MUAHANG.SOLUONG, MUAHANG.DONGIA, 	     " +  
			   "  MUAHANG.DUNGSAI, MUAHANG.SPMA, MUAHANG.SPTEN, MUAHANG.HANSUDUNG, MUAHANG.DONVI,      " +  
			   "  '100000' as TienTe_fk, '1' as  TyGiaQuyDoi, MUAHANG.DONGIA as DonGiaViet, '' as khottId, '' as khottTen        " +  
			   "  FROM       " +  
			   "  (        " +  
			   "  SELECT  A.SANPHAM_FK,     " +  
			   "  CASE WHEN (SELECT PK_SEQ FROM DONVIDOLUONG WHERE DONVI LIKE A.DONVI)!= B.DVDL_FK      " +  
			   "  THEN SUM( A.SOLUONG * ISNULL(CONVERT(FLOAT,D.SOLUONG1), 1) / ISNULL(CONVERT(FLOAT,D.SOLUONG2), 1) )     " +  
			   "  ELSE SUM(A.SOLUONG) END AS SOLUONG ,       " +  
			   "  CASE WHEN (SELECT PK_SEQ FROM DONVIDOLUONG WHERE DONVI LIKE A.DONVI)!= B.DVDL_FK      " +  
			   "  THEN  A.DONGIA *  ISNULL(CONVERT(FLOAT,D.SOLUONG2), 1)      " +  
			   "  ELSE  A.DONGIA END AS DONGIA ,     " +  
			   "  0 AS DUNGSAI,      " +  
			   "  B.MA AS SPMA,      " +  
			   "  B.TEN   AS SPTEN,     " +  
			   "  B.HANSUDUNG, C.DONVI          " +  
			   "  FROM DONTRAHANG DTH INNER JOIN DONTRAHANG_SP A ON DTH.PK_SEQ = A.DONTRAHANG_FK        " +  
			   "  LEFT JOIN ERP_SANPHAM B ON A.SANPHAM_FK = B.PK_SEQ         " +  
			   "  LEFT JOIN 	DONVIDOLUONG C ON C.PK_SEQ=B.DVDL_FK          " +  
			   "  LEFT JOIN     " +  
			   "  (     " +  
			   "   SELECT D.*,DVDL1.DONVI FROM QUYCACH D     " +  
			   "   INNER JOIN DONVIDOLUONG DVDL1 ON DVDL1.PK_SEQ=D.DVDL2_FK    " +  
			   "  )D  ON  B.PK_SEQ= D.SANPHAM_FK  AND A.DONVI=D.DONVI    " +  
			   "     " +  
			   "  WHERE DTH.PK_SEQ =     (select trahang_fk from erp_nhanhang where pk_seq="+idnhanhang+" )  " +  
			   "  GROUP BY A.SANPHAM_FK, A.DONGIA,B.MA,B.QUYCACH,B.TEN2, B.TEN,B.MA,A.DONVI,B.DVDL_FK ,D.SOLUONG2,D.SOLUONG1, B.HANSUDUNG, C.DONVI     " +  
			   "  )       " +  
			   "  MUAHANG   LEFT JOIN         " +  
			   "  (        " +  
			   "    SELECT B.SANPHAM_FK, B.NGAYNHANDUKIEN, ISNULL(SUM(B.SOLUONGNHAN), '0' ) AS SOLUONG 	     " +  
			   "    FROM ERP_NHANHANG A  INNER JOIN ERP_NHANHANG_SANPHAM B ON A.PK_SEQ = B.NHANHANG_FK        " +  
			   "    WHERE A.TRAHANG_FK = (select trahang_fk from erp_nhanhang where pk_seq="+idnhanhang+" ) AND A.TRANGTHAI not in( '0','3','4')        " +  
			   "    GROUP BY B.SANPHAM_FK, NGAYNHANDUKIEN       " +  
			   "  )       " +  
			   "  NHANHANG  ON MUAHANG.SANPHAM_FK = NHANHANG.SANPHAM_FK    " +  
			   "  WHERE MUAHANG.SOLUONG - ISNULL(NHANHANG.SOLUONG, '0') > 0 ";
		
		System.out.println("Kiemtra:"+query);
		ResultSet  kt = db.get(query);
		try{
			 
			if(kt.next()){
				//dang con du lieu co the nhan
				kt.close();
				return false;
			}
			kt.close();
			
		}catch(Exception e){
			e.printStackTrace();
			return false;
			
		}
		return true;
	}
		
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IErpNhanhangList_DonTH obj;
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
	    	IErpNhanhang_DonTH nhBean = new ErpNhanhang_DonTH();
	    	nhBean.setUserId(userId);
	    	nhBean.setCongtyId((String)session.getAttribute("congtyId"));
	    	nhBean.createRs();
    		
	    	session.setAttribute("nhBean", nhBean);
	    	session.setAttribute("spList", "");
	    	
    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangNew_DonTH.jsp";
    		response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
	    		obj = new ErpNhanhangList_DonTH();
	    		obj.setUserId(userId);
	    		obj.setCongtyId((String)session.getAttribute("congtyId"));
		    	String search = getSearchQuery(request, obj);
		    	
		    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
		
		    	obj.init(search);
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpNhanHang_DonTH.jsp");	
		    }
	    	else
	    	{
		    	obj = new ErpNhanhangList_DonTH();
		    	obj.setUserId(userId);
		    	obj.setCongtyId((String)session.getAttribute("congtyId"));
		    	
		    	String search = getSearchQuery(request, obj);
		    	obj.init(search);
			
				
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
		
	    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpNhanHang_DonTH.jsp");
	    	}
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpNhanhangList_DonTH obj)
	{
		Utility util=new Utility();
		 		
		String query = " select a.PK_SEQ as nhId, a.SOHOADON, a.NGAYNHAN, b.TEN as dvthTen, " +
						" case a.NoiDungNhan_FK when 100000 then isnull(b.PREFIX,'') + isnull(c.PREFIX,'') + cast(c.PK_SEQ as varchar(10)) " +
						" else isnull(b.PREFIX,'') + isnull(th.PREFIX,'') + cast(th.PK_SEQ as varchar(10)) end  as PoId, " +
						" isnull(b.PREFIX,'') + isnull( a.PREFIX,'') + CAST(a.PK_SEQ as varchar(20)) as SOCHUNGTU," +
						"  case  when  NH.SLNhan < NH.SLDat  or a.NGAYNHAN > getdate() then 1 else 0 end as ktra, " +
						" a.TRANGTHAI, a.NGAYSUA, a.NGAYTAO, d.TEN as nguoitao, e.TEN as nguoisua, a.loaihanghoa_fk  " +
						" from erp_nhanhang a  inner join ( Select  b.NHANHANG_FK, SUM(b.SOLUONGDAT)as SLDat, SUM(b.SOLUONGNHAN)as SLNhan "+
						"		 from  ERP_NHANHANG_SANPHAM b "+
						"		 group by  b.NHANHANG_FK) NH on a.PK_SEQ= NH.NHANHANG_FK" +
						" left join ERP_DONVITHUCHIEN b on a.DONVITHUCHIEN_FK = b.PK_SEQ " +
						" left join ERP_MUAHANG c on a.MUAHANG_FK = c.PK_SEQ left join DonTraHang th on a.TRAHANG_FK = th.PK_SEQ " +
						" inner join NHANVIEN d on a.NGUOITAO = d.PK_SEQ  " +
						" inner join NHANVIEN e on a.NGUOISUA = e.PK_SEQ  " +
						" where a.congty_fk = '" + obj.getCongtyId() + "' and a.TRAHANG_FK is not null and" +
						"   (  ( ( c.pk_seq is not null ) and ( c.NHACUNGCAP_FK in " + util.quyen_nhacungcap(obj.getUserId()) + " ) )              " +
						"     or ( ( th.pk_seq is not null ) and ( th.KHACHHANG_FK in  " + util.quyen_npp(obj.getUserId())  + "  ) ) )   ";
		
		String tungay = request.getParameter("tungay");
		if(tungay == null)
			tungay = "";
		obj.setTungay(tungay);
		
		String denngay = request.getParameter("denngay");
		if(denngay == null)
			denngay = "";
		obj.setDenngay(denngay);
		
		String dvthId = request.getParameter("dvth");
		if(dvthId == null)
			dvthId = "";
		obj.setDvthId(dvthId);
		
		String soPo = request.getParameter("sopo");
		if(soPo == null)
			soPo = "";
		obj.setSoPO(soPo);
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		String sonhanhang = request.getParameter("sonhanhang");
		if(sonhanhang == null)
			sonhanhang = "";
		obj.setSoNhanhang(sonhanhang);
		
		String sohoadon = request.getParameter("sohoadon");
		if(sohoadon == null)
			sohoadon = "";
		obj.setSoHoadon(sohoadon);
		
		String ncc = request.getParameter("ncc");
		if(ncc == null)
			ncc = "";
		obj.setNCC(ncc);
		
		if(tungay.length() > 0)
			query += " and a.ngaynhan >= '" + tungay + "'";
		
		if(denngay.length() > 0)
			query += " and a.ngaynhan <= '" + denngay + "'";
		
		if(dvthId.length() > 0)
			query += " and b.pk_seq = '" + dvthId + "'";
		
		if(soPo.length() > 0)
		{
			query += " and ( CAST(c.PK_SEQ as varchar(10)) + '/' + SUBSTRING(c.NGAYMUA, 6, 2) + '/' + SUBSTRING(c.NGAYMUA, 0, 5) like '%" + soPo + "%' ) ";
		}
		
		if(trangthai.length() > 0)
			query += " and a.trangthai = '" + trangthai + "'";
		
		if(sonhanhang.trim().length() > 0)
		{
			query += " and b.PREFIX + a.PREFIX + CAST(a.PK_SEQ as varchar(20)) like N'%" + sonhanhang + "%'  ";
		}
		
		if(sohoadon.trim().length() > 0)
		{
			query += " and a.SOHOADON like N'%" + sohoadon + "%' ";
		}
		
		if(ncc.trim().length() > 0)
		{
			query += " and (  ( ( c.pk_seq is not null ) and ( c.NHACUNGCAP_FK in ( select pk_seq from ERP_NHACUNGCAP where ma like N'%" + ncc + "%' or ten like N'%" + ncc + "%' ) ) )              " +
					 "     or ( ( th.pk_seq is not null ) and ( th.KHACHHANG_FK in  ( select pk_seq from ERP_KHACHHANG where ma like N'%" + ncc + "%' and ten like N'%" + ncc + "%'  )  ) ) )   ";
		}
		
		//query += " order by a.NGAYNHAN desc, a.trangthai asc, a.pk_seq desc;";
		return query;
	}
	
	private String Delete(String nhId)
	{
		dbutils db = new dbutils();
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "select count(*) as sodong  " +
						   "from ERP_HOADONNCC a inner join ERP_HOADONNCC_PHIEUNHAP b on a.pk_seq = b.hoadonncc_fk " +
						   "where b.phieunhan_fk = '" + nhId + "'";
			
			//System.out.println("Query mua hang: " + query);
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
			
			//System.out.println("So dong la: " + sodong + "\n");
			
			if(sodong > 0)
			{
				return "Nhận hàng này đã xuất hóa đơn, bạn không thể xóa";
			}
			
			
			query = "update ERP_NHANHANG set trangthai = '3' where pk_seq = '" + nhId + "'";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Không thể cập nhật ERP_NHANHANG: " + query;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
			return "";
		} 
		catch (Exception e)
		{ 
			db.shutDown(); 
			return "Khong the xoa don mua hang"; 
		}
		
	}
	
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	private String getTime()
	{
		Date date = new Date();
	    SimpleDateFormat simpDate;

	    //format 24h
	    simpDate = new SimpleDateFormat("kk:mm:ss");
	    
	    return simpDate.format(date);
	}
	
	
	
	//Cap Nhat lai NO CO

	public String chotNhanHang_SanPham_Update_No_Co(String nhId, String ctyId, dbutils db)
	{
		String msg = "";
		
		String sql = "select a.NGAYCHOT, c.LOAINHACUNGCAP_FK, khoNL_Nho_GC, b.nhacungcap_fk , d.khachhang_fk " +
					 "from ERP_NHANHANG a left join ERP_MUAHANG b on a.MUAHANG_FK = b.PK_SEQ " +
					 "	left join ERP_NHACUNGCAP c on b.NHACUNGCAP_FK = c.PK_SEQ" +
					 "  left join DonTraHang d on a.trahang_fk = d.pk_seq   " +
					 "where a.PK_SEQ = '" + nhId + "'";
		
		
		String ngaychotNV = "";
		String nccId = "";
		String khId = "";
		
		ResultSet rs = db.get(sql);
		try 
		{
			if(rs.next())
			{
				ngaychotNV = rs.getString("ngaychot");
				nccId = rs.getString("nhacungcap_fk") == null ? "" : rs.getString("nhacungcap_fk");
				khId = rs.getString("khachhang_fk") == null ? "" : rs.getString("khachhang_fk");
				rs.close();
			}
		} 
		catch (Exception e) 
		{
			return "10.Vui lòng kiểm tra ngày chốt của nhận hàng " + e.getMessage();
		}
		
		String nam = ngaychotNV.substring(0, 4);
		String thang = ngaychotNV.substring(5, 7);
		
		String query = "select SANPHAM_FK, b.KHONHAN, NoiDungNhap_fk, b.KHONHAN, SOLUONGNHAN, DONGIA, DONGIAVIET, b.TIENTE_FK, b.ngaynhandukien,  " +
							"ISNULL(c.kiemtradinhtinh, 0) as kiemdinhTinh, ISNULL(c.kiemtradinhluong, 0) as kiemdinhLuong, c.LOAISANPHAM_FK, c.NguonGoc, c.ten + ' ' + c.quycach as spTen   " +
						"from ERP_NHANHANG a inner join ERP_NHANHANG_SANPHAM b on a.PK_SEQ = b.NHANHANG_FK  " +
							"inner join ERP_SANPHAM c on b.SANPHAM_FK = c.PK_SEQ " +
						"where b.NHANHANG_FK = '" + nhId + "'";
		
		rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					String loaisanpham = rs.getString("LOAISANPHAM_FK");
					String noidungnhap = rs.getString("NoiDungNhap_fk");
					double dongia = rs.getDouble("DONGIA");
					double dongiaViet = rs.getDouble("DONGIAVIET");
					String tiente_fk = rs.getString("TienTe_FK");
					float soluong =  rs.getFloat("SOLUONGNHAN");

					String taikhoanktCo = "";
					String taikhoanktNo = "";
					
					String queryTK = "";
					
					query = "select isnull(b.NGUONGOCHH, 'TN') as NguonGoc " +
							"from ERP_NHANHANG a inner join ERP_MUAHANG b on a.muahang_fk = b.pk_seq where a.pk_seq = '" + nhId + "'";
					ResultSet rsNguonGoc = db.get(query);
					if(rsNguonGoc != null)
					{
						if(rsNguonGoc.next())
						{
							if(!rsNguonGoc.getString("NguonGoc").equals("TN"))
							{
								queryTK =   "select a.TaiKhoanKt_fk as TAIKHOANKTNO, b.PK_SEQ as TAIKHOANKTCO  " +
											"from erp_loaisanpham a, ERP_TAIKHOANKT b  " +
											"where a.pk_seq = '" + loaisanpham + "' and b.SOHIEUTAIKHOAN = '151000'";
							}
							else
							{
								if( noidungnhap.equals("100000") || noidungnhap.equals("100004") ) //DOI LAI, CACH MOI KO DUNG BANG CONFIG....
								{
									
									if(noidungnhap.equals("100000"))
									{
										queryTK = 	" select a.TaiKhoanKt_fk as TAIKHOANKTNO, b.TaiKhoan_fk as TAIKHOANKTCO " +
													" from erp_loaisanpham a, erp_nhacungcap b  " +
													"where a.pk_seq = '" + loaisanpham + "' and b.pk_seq = '" + nccId + "' ";
									}
									else
									{
										queryTK = 	" select a.TaiKhoanKt_fk as TAIKHOANKTNO, b.TaiKhoan_fk as TAIKHOANKTCO " +
													" from erp_loaisanpham a, erp_khachhang b  " +
													"where a.pk_seq = '" + loaisanpham + "' and b.pk_seq = '" + khId + "' ";
									}	
								}
							}
							
							System.out.println("20.Query lay tai khoan: " + query);
							
							ResultSet tkRs = db.get(queryTK);
							if(tkRs != null)
							{
								if(tkRs.next())
								{
									taikhoanktCo = tkRs.getString("TAIKHOANKTCO");
									taikhoanktNo = tkRs.getString("TAIKHOANKTNO");
									tkRs.close();
								}
								
								if(taikhoanktCo.trim().length() <= 0 || taikhoanktNo.trim().length() <= 0)
								{
									msg = "22.Loại sản phẩm và nội dung nhập tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
									rs.close();
									tkRs.close();
									return msg;
								}
								
								//Kiem tra xem da co tai khoan nay trong thang chua
								query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
										"where taikhoankt_fk = '" + taikhoanktNo + "' and nguyente_fk = '" + tiente_fk + "' and thang = '" + thang + "' and nam = '" + nam + "'";
								
								ResultSet rsTKNo = db.get(query);
								int sodong = 0;
								if(rsTKNo.next())
								{
									sodong = rsTKNo.getInt("sodong");
								}
								rsTKNo.close();
								
								
								if(sodong > 0) //daco
								{
									query = "update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND + " + dongiaViet + "*" + soluong + ", " +
																		" GIATRINGUYENTE = GIATRINGUYENTE + "  + dongia + "*" + soluong + 
											" where taikhoankt_fk = '" + taikhoanktNo + "' and nguyente_fk = '" + tiente_fk + "' and thang = '" + thang + "' and nam = '" + nam + "'";
								}
								else
								{
									query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, THANG, NAM) " +
											"values('" + taikhoanktNo + "', '0', " + dongiaViet + "*" + soluong + ", '" + tiente_fk + "', " + dongia + "*" + soluong + ", '" + thang + "', '" + nam + "')";
								}
								
								System.out.println("55.Cap nhat No: " + query);
								if(!db.update(query))
								{
									rs.close();
									tkRs.close();
									
									System.out.println("1.Loi: " + query);
									return "23.Không thể cập nhật ERP_TAIKHOAN_NOCO: " + nhId + "  -- " + query;
								}
								
								
								//Tai khoan co
								query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO  " +
										"where taikhoankt_fk = '" + taikhoanktCo + "' and nguyente_fk = '" + tiente_fk + "' and thang = '" + thang + "' and nam = '" + nam + "'";
								
								rsTKNo = db.get(query);
								
								sodong = 0;
								if(rsTKNo.next())
								{
									sodong = rsTKNo.getInt("sodong");
								}
								rsTKNo.close();
								
								if(sodong > 0) //daco
								{
									query = "update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND + " + dongiaViet + "*" + soluong + ", " +
																	" GIATRINGUYENTE = GIATRINGUYENTE + "  + dongia + "*" + soluong + 
											" where taikhoankt_fk = '" + taikhoanktCo + "' and nguyente_fk = '" + tiente_fk + "' and thang = '" + thang + "' and nam = '" + nam + "'";
								}
								else
								{
									query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, THANG, NAM) " +
											"select '" + taikhoanktCo + "', " + dongiaViet + "*" + soluong + ", '0', '" + tiente_fk + "', " + dongia + "*" + soluong + ", '" + thang + "', '" + nam + "' ";
								}
								
								System.out.println("66.Cap nhat Co: " + query);		
								if(!db.update(query))
								{
									rs.close();
									tkRs.close();
									
									return "24.Không thể cập nhật ERP_TAIKHOAN_NOCO: " + nhId + " -- " + query;
								}
							}
							
						}
						rsNguonGoc.close();
					}
					
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				//db.update("rollback");
				return "Không thể chốt nhận hàng: " + nhId + " -- " + e.getMessage();
			}
		}
		
		return msg;
	}

	
	public static void main(String[] arg)
	{
		 
		
		try 
		{
	 
		} 
		catch (Exception e)
		{
			System.out.println("Loi khi chot ALL nhan hang..." + e.getMessage());
		}
	}
	
}
