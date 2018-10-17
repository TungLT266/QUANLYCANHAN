package geso.traphaco.erp.beans.tinhgiathanh.imp;

import geso.dms.db.sql.dbutils;
import geso.traphaco.erp.beans.tinhgiathanh.IErp_SanPhamQuyDoi;
import geso.traphaco.erp.beans.tinhgiathanh.IErp_Tinhgiathanh;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class Erp_Tinhgiathanh implements IErp_Tinhgiathanh 
{
	private String thang;
	private String nam;
	private String UserId;
	private dbutils db;
	private ResultSet RsGiaThanhBTP_TieuThu;
	private String msg;
	private ResultSet rsgiathanh;
	private ResultSet rsbanggiabinhquannl;
	private ResultSet rsTieuThuNL_LSX;
	private ResultSet rsHoaChatTieuThuThang;
	private ResultSet RsPhanBoHoaChat_Thang;
	private ResultSet RsPhanBoChiPhi_Thang;
	private ResultSet RsChiphi;
	private ResultSet RsNhamay;
	private ResultSet RsTapHopNL_HC;
	private ResultSet RsGiaThanhTP;
	private ResultSet RsXuatNhapTon_BTP;
	private String nhamayId;
	private String congtyid;
	String chuoingaythang="";

	private ResultSet RsTieuThuNl_Chinh;
	private ResultSet RsTieuThuNl_Phu;
	private ResultSet RsTinhGia_Hoi_cb1;
	private ResultSet RsTinhGia_Hoi_cb6;
	private int thangtruoc;
	private int namtruoc;
	private int isView;
	private List<ResultSet> listRs;

	Hashtable< String, Double> htpMaketoan_soluong_trongky;
	
	ResultSet giathanhTPRs;
	ResultSet giathanhLSXRs;
	ResultSet chitietRs;
	
	public Erp_Tinhgiathanh() 
	{
		db = new dbutils();
		thang = "";
		nam = "";
		this.nhamayId = "";
		this.msg = "";
		chuoingaythang = "";
		this.isView = 0;
	}


	public List<ResultSet> getListRs() {
		return listRs;
	}
	public void setListRs(List<ResultSet> listRs) {
		this.listRs = listRs;
	}


	public int getIsView() {
		return isView;
	}
	public void setIsView(int isView) {
		this.isView = isView;
	}


	public String getUserId() {

		return this.UserId;
	}

	public void setUserId(String userId) {

		this.UserId=userId;
	}


	public String getthang() {

		return this.thang;
	}


	public void setthang(String _thang) {

		this.thang=_thang;
	}


	public String getNam() {

		return this.nam;
	}


	public void setNam(String _nam) {

		this.nam=_nam;
	}


	public void Init() 
	{
		String query = "";
		this.chuoingaythang = this.nam + "-" +(this.thang.length()>1?this.thang:"0"+this.thang);
		try
		{
			query = "SELECT PK_SEQ,TENNHAMAY FROM ERP_NHAMAY WHERE CONGTY_FK="+this.congtyid;
			this.RsNhamay = db.get(query);

			if( this.isView == 1 && this.thang.trim().length() > 0  && this.nam.trim().length() > 0 )
			{
				query = " select b.ma, b.ten, c.donvi, a.soluong, a.dongia, a.soluong * a.dongia as tonggiatri  "+
						" from ERP_GIATHANH_THANHPHAM a inner join ERP_SANPHAM b on a.sanpham_fk = b.pk_seq "+
						" 		inner join DONVIDOLUONG c on b.dvdl_fk = c.pk_seq "+
						" where a.thang = '" + this.thang + "' and a.nam = '" + this.nam + "' "+
						" order by b.ten asc ";
				
				System.out.println("::: 1.INIT: " + query );
				this.giathanhTPRs = db.get(query);
				
				query = " select a.lenhsanxuat_fk, b.ma + ' ' + b.ten as spTen, c.donvi, a.soluong, a.dongia, " + 
						" 	a.chiphi_nguyenlieu, a.chiphi_nhancong, a.chiphi_khauhao, a.chiphi_sanxuatchung, a.tongchiphi, a.soluong * a.dongia as tonggiatri "+
						" from ERP_GIATHANH_LENHSANXUAT a inner join ERP_SANPHAM b on a.sanpham_fk = b.pk_seq "+
						" 		inner join DONVIDOLUONG c on b.dvdl_fk = c.pk_seq "+
						" where a.thang = '" + this.thang + "' and a.nam = '" + this.nam + "'  "+
						" order by b.ten asc ";
				
				System.out.println("::: 2.INIT: " + query );
				this.giathanhLSXRs = db.get(query);
				
				query = " select DT.lenhsanxuat_fk, SP.ma + ' ' + SP.ten as spTen,  "+
						" 		isnull( D.tonggiatri, 0 ) as cp_nguyenlieu,  "+
						" 		isnull( A.luongCongnhan, 0 ) as cp_nhancong_luong, isnull( A.thuongCongnhan, 0 ) as cp_nhancong_thuong, isnull( a.chiphiKhac, 0 ) as cp_nhancong_khac, "+
						" 		isnull( B.phanbo_chiphi_sxtructiep, 0 ) as cp_sx_tructiep, isnull( B.phanbo_chiphi_phanxuong, 0 ) as cp_sx_phanxuong, isnull( B.phanbo_chiphi_chung, 0 ) as cp_sx_chung, "+
						" 		isnull( C.phanbo_khauhao_sxtructiep, 0 ) as cp_khauhao_tructiep, isnull( C.phanbo_khauhao_phanxuong, 0 ) as cp_khauhao_phanxuong, isnull( C.phanbo_khauhao_chung, 0 ) as cp_khauhao_chung "+
						" from ERP_GIATHANH_LENHSANXUAT DT inner join ERP_SANPHAM SP on DT.sanpham_fk = SP.pk_seq  "+
						" 	left join ERP_GIATHANH_CPNHANCONG_CHITIET_LENHSANXUAT A on DT.lenhsanxuat_fk = A.lenhsanxuat_fk and A.thang = '" + this.thang + "' and A.nam = '" + this.nam + "' "+
						" 	left join ERP_GIATHANH_CPSXCHUNG_CHITIET_LENHSANXUAT B on DT.lenhsanxuat_fk = B.lenhsanxuat_fk and B.thang = '" + this.thang + "' and B.nam = '" + this.nam + "' "+
						" 	left join ERP_GIATHANH_KHAUHAOTS_CHITIET_LENHSANXUAT C on DT.lenhsanxuat_fk = C.lenhsanxuat_fk and C.thang = '" + this.thang + "' and C.nam = '" + this.nam + "' "+
						" 	left join ERP_GIATHANH_CPNGUYENLIEU_LENHSANXUAT D on DT.lenhsanxuat_fk = D.lenhsanxuat_fk and D.thang = '" + this.thang + "' and D.nam = '" + this.nam + "' "+
						" where DT.thang = '" + this.thang + "' and DT.nam = '" + this.nam + "' " + 
						" order by SP.ten asc ";
				
				System.out.println("::: 3.INIT: " + query );
				this.chitietRs = db.get(query);
			}
		}
		catch(Exception err)
		{
			err.printStackTrace();
		}

	}

	public String getMsg() {

		return this.msg;
	}

	public void setMsg(String _msg) {

		this.msg=_msg;
	}

	public void DbClose() {

		try{
			if (this.htpMaketoan_soluong_trongky != null)
				this.htpMaketoan_soluong_trongky.clear(); 
			if (this.listquydoi != null)
				this.listquydoi.clear();
			if (this.listquydoi_dacogia != null)
				this.listquydoi_dacogia.clear(); 
			if(this.rsbanggiabinhquannl!=null){
				this.rsbanggiabinhquannl.close();
			}
			if(this.rsgiathanh!=null){
				this.rsgiathanh.close();
			}
			if(this.rsHoaChatTieuThuThang!=null){
				this.rsHoaChatTieuThuThang.close();

			}
			if(this.rsTieuThuNL_LSX!=null){
				this.rsTieuThuNL_LSX.close();
			}
			if(this.RsPhanBoHoaChat_Thang!=null){
				RsPhanBoHoaChat_Thang.close();
			}

			if(this.rsTieuThuNL_LSX!=null){
				this.rsTieuThuNL_LSX.close();
			}
			if(this.RsTapHopNL_HC!=null){
				RsTapHopNL_HC.close();
			}
			if(this.RsNhamay!=null){
				this.RsNhamay.close();
			}
			if(this.RsGiaThanhTP!=null){
				this.RsGiaThanhTP.close();
			}
			if(this.RsChiphi!=null){
				RsChiphi.close();
			}

			if(this.RsGiaThanhBTP_TieuThu!=null){
				RsGiaThanhBTP_TieuThu.close();
			}

			if(this.RsPhanBoChiPhi_Thang!=null){
				RsPhanBoChiPhi_Thang.close();
			}
		}catch(Exception er){
			er.printStackTrace();
		}finally{
			db.shutDown();
		}
	}

	public boolean TinhGiaThanh() {
		return false;				
	}


	public  boolean TinhGiaThanh_TheoMaTP( String isImport )
	{
		System.out.println("::: 1. TOI DAY: " + isImport);
		if( this.thang.trim().length() <= 0 )
		{
			this.msg = "Bạn phải chọn tháng";
			return false;
		}
		
		if( this.nam.trim().length() <= 0 )
		{
			this.msg = "Bạn phải chọn năm";
			return false;
		}
		
		this.chuoingaythang = this.nam + "-" + ( this.thang.length() > 1 ? this.thang : "0" + this.thang );
		try
		{
			String query = "";
			ResultSet rs = null;
			
			/*String query = "select count(*) as sodong from ERP_KHOASOTHANG where thangks = '" + this.thang + "' and nam = '" + this.nam + "' " ;
			ResultSet rs = db.get(query);
			if (rs.next())
			{
				if( rs.getInt("sodong") >= 1 )
				{
					this.msg = "Tháng (" + this.thang + ") năm ( " + this.nam + " ) đã khóa sổ. Bạn không được tính giá thành lại";
					return false;
				}
			}
			rs.close();*/

			//Check HE SO PHAN BO
			if( isImport.equals("0") )
			{
				query = "select count(*) as sodong from ERP_GIATHANH_DONGIALUONG where  thang = '" + this.thang + "' and nam = '" + this.nam + "' " ;
				rs = db.get(query);
				if (rs.next())
				{
					if( rs.getInt("sodong") <= 0 )
					{
						this.msg = "Tháng (" + this.thang + ") năm ( " + this.nam + " ) chưa upload hệ số phân bổ.";
						return false;
					}
				}
				rs.close();
			}
			
			//Check khóa sổ tháng trước
			//Bắt buộc kỳ N - 1 Phái khóa sổ
			String thangKS = "";
			String namKS = "";
			
			if( Integer.parseInt(this.thang) == 1 )
			{
				thangKS = "12";
				namKS = Integer.toString( Integer.parseInt(this.nam) - 1 );
			}
			else
			{
				thangKS = Integer.toString( Integer.parseInt(this.thang) - 1 );
				namKS = Integer.toString( Integer.parseInt(this.nam));
			}
			
			String sql = " select count(pk_seq) as sodong " + 
						 " from ERP_KHOASOTHANG where   nam = '" + namKS + "' and thangks = '" + Integer.parseInt(thangKS) + "'";
			System.out.println("___Cau lenh check kỳ N - 1: " + sql);
			
			rs = db.get(sql);
			int count = 0;
			if(rs != null)
			{
				try 
				{
					if(rs.next())
					{
						count = rs.getInt("sodong");
					}
					rs.close();
				} 
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			
			if(count <= 0)
			{
				this.msg = "Bạn phải khóa sổ KHO tháng trước, nghiệp vụ này mới thực hiện được";
				return false;
			}
			
			sql = "select TOP 1 NAM, THANGKS as thang from ERP_KHOASOTHANG order by NAM desc, THANGKS desc ";
			ResultSet rscheckngay = db.get(sql);

			if(rscheckngay.next())
			{
				thangtruoc = rscheckngay.getInt("thang");
				namtruoc = rscheckngay.getInt("NAM");
			}
			else
			{
				this.msg = "Không xác định được khóa sổ tháng trước";
				return false;
			}
			rscheckngay.close();
			
			System.out.println("::: 2. TOI DAY: " + isImport);
			if( isImport.equals("0") )
			{
				//Reset bút toán tổng hợp
				this.Clear_DinhKhoan();
				
				//B0. Tính nguyên liệu
				this.B0_TinhGiaNguyenLieu();
				
				//B1. Tính hệ số Alpha1 + Alpha2
				this.B1_TinhHeSoLuong();
				
				//B2. Phân bổ chi phí nhân công trực tiếp
				this.B2_PhanBoChiPhi_NhanCongTrucTiep();
				
				//B3. Phân bổ khấu hao tài sản cố định
				this.B3_PhanBoKhauHao_TaiSan();
				
				//B4. Phân bổ chi phí sản xuất chung
				this.B4_PhanBoChiPhi_SanXuatChung();
				
				//Kết chuyển giá vốn
				this.TaoButToan_TongHop_ChiPhi();
				
				//B5. Tổng hợp giá thành theo lệnh + sản phẩm
				this.B5_GiaThanh( );
			}
			else
			{
				this.B5_GiaThanh_Import();
			}
			

			//CHEN VAO BANG TINH GIA VON DE BIET LA DA TIEN HANH THUC HIEN
			query = "delete ERP_TINHGIAVON where thang = '" + this.thang + "' and nam = '" + this.nam + "' ";
			if(!db.update(query))
			{
				this.msg = "Lỗi khi tính giá thành " + query;
				return false;
			}

			query = " Insert ERP_TINHGIAVON ( THANG, NAM, NGUOITAO ) values( '" + this.thang + "', '" + this.nam + "', '" + this.UserId + "' ) ";
			if(!db.update(query))
			{
				this.msg = "Lỗi khi tính giá thành " + query;
				return false;
			}

			this.msg = "Tính giá thành tháng ( " + this.thang + " ), năm ( " + this.nam + " ) thành công ";
			return true;
		}
		catch(Exception er)
		{
			er.printStackTrace();
			this.msg="Error: " +er.toString();
			return false;
		}
	}
	
	private String Clear_DinhKhoan( )
	{
		String query = "";
		
		query = "delete ERP_PHATSINHKETOAN where loaichungtu = N'Kết chuyển CPSX' " + 
				" 	and sochungtu in ( select pk_seq from ERP_BUTTOANTONGHOP where  DIENGIAI = N'Kết chuyển CPSX giá thành tháng " + this.thang + ", " + this.nam + "' ) ";
		System.out.println(":: XOA DINH KHOAN: " + query);
		db.update(query);
		
		query = "update ERP_BUTTOANTONGHOP set trangthai = 2 where DIENGIAI = N'Kết chuyển CPSX giá thành tháng " + this.thang + ", " + this.nam + "' ";
		System.out.println(":: XOA BUT TOAN: " + query);
		db.update(query);
		
		return "";
	}
	
	private String TaoButToan_TongHop_ChiPhi( ) 
	{
		String denngay = this.GetDenNgay( this.thang, this.nam );
		db.execProceduce2("TinhGiaTonKho_KetChuyenCPSX", new String[] { denngay } );
		return "";
	}
	
	private String GetDenNgay(String thang, String nam) 
	{
		String ngay = LastDayOfMonth(Integer.parseInt(thang), Integer.parseInt(nam) );
		
		if( thang.trim().length() < 2 )
			thang = "0" + thang;

		return nam + "-" + thang + "-" + ngay;
	}

	private String B5_GiaThanh( ) 
	{
		String query = "delete ERP_GIATHANH_LENHSANXUAT where thang = '" + this.thang + "' and nam = '" + this.nam + "' ";
		db.update(query);
		
		query = "delete ERP_GIATHANH_THANHPHAM where thang = '" + this.thang + "' and nam = '" + this.nam + "' ";
		db.update(query);
		
		
		query = " insert ERP_GIATHANH_LENHSANXUAT( thang, nam, sanpham_fk, lenhsanxuat_fk, soluong, chiphi_nguyenlieu, chiphi_nhancong, chiphi_khauhao, chiphi_sanxuatchung, tongchiphi, dongia )  "+
				" select  '" + this.thang + "', '" + this.nam + "', DT.sanpham_fk, DT.lenhsanxuat_fk, DT.SOLUONGNHAN, isnull( D.tonggiatri, 0 ) chiphi_nguyenlieu,  "+
				" 		isnull(A.tongchiphi, 0) as chiphi_nhancong, isnull(C.tonggiatri, 0) as chiphi_khauhao, isnull(B.tonggiatri, 0) as chiphi_sanxuatchung,  "+
				" 		isnull(A.tongchiphi, 0) + isnull(C.tonggiatri, 0) + isnull(B.tonggiatri, 0) + isnull( D.tonggiatri, 0 ) as tongchiphi, "+
				" 		round( ( isnull(A.tongchiphi, 0) + isnull(C.tonggiatri, 0) + isnull(B.tonggiatri, 0) + isnull( D.tonggiatri, 0 ) ) / DT.SOLUONGNHAN, 5 ) as dongia "+
				" from  "+
				" (  "+
				" 	SELECT NKSP.SANPHAM_FK, NK.lenhsanxuat_fk, sum(NKSP.SOLUONGNHAN) as SOLUONGNHAN "+
				" 	FROM ERP_NHANHANG NK           "+
				" 	 	INNER JOIN ERP_NHANHANG_SANPHAM NKSP ON NK.PK_SEQ = NKSP.NHANHANG_FK    "+
				" 	 	INNER JOIN ERP_LENHSANXUAT_GIAY LSX ON LSX.PK_SEQ = NK.lenhsanxuat_fk          "+
				" 	WHERE  SUBSTRING(NK.ngaynhan, 1, 7) = '" + this.chuoingaythang + "'  AND NK.lenhsanxuat_fk IS NOT NULL AND NK.TRANGTHAI = 1          "+
				"		and NKSP.SANPHAM_FK in ( select pk_seq from ERP_SANPHAM where loaisanpham_fk in ( 100002 ) )	" +
				" 	GROUP BY NKSP.SANPHAM_FK,  NK.lenhsanxuat_fk  "+
				"  )  "+
				" DT  	"+
				" left join "+
				" ( "+
				" 	SELECT sanpham_fk,lenhsanxuat_fk,thang,nam,sum(tongchiphi) as tongchiphi from  ERP_GIATHANH_CPNHANCONG_CHITIET_LENHSANXUAT "+
				" 	where  thang = '" + this.thang + "' and nam = '" + this.nam + "'   "+
				" 	group by sanpham_fk,lenhsanxuat_fk,thang,nam "+
				" )A on DT.SANPHAM_FK = A.SANPHAM_FK and DT.lenhsanxuat_fk = A.lenhsanxuat_fk  and A.thang = '" + this.thang + "' and A.nam = '" + this.nam + "'  "+
			    " left join "+
				" ( "+
				" 	SELECT sanpham_fk,lenhsanxuat_fk,thang,nam,sum(tonggiatri) as tonggiatri from  ERP_GIATHANH_CPSXCHUNG_CHITIET_LENHSANXUAT "+
				" 	where  thang = '" + this.thang + "' and nam = '" + this.nam + "'   "+
				" 	group by sanpham_fk,lenhsanxuat_fk,thang,nam "+
				" )B on DT.SANPHAM_FK = B.SANPHAM_FK and DT.lenhsanxuat_fk = B.lenhsanxuat_fk and B.thang = '" + this.thang + "' and B.nam = '" + this.nam + "'  "+
			    " left join "+
				" ( "+
				" 	SELECT sanpham_fk,lenhsanxuat_fk,thang,nam,sum(tonggiatri) as tonggiatri from  ERP_GIATHANH_CPNGUYENLIEU_LENHSANXUAT "+
				" 	where  thang = '" + this.thang + "' and nam = '" + this.nam + "'   "+
				" 	group by sanpham_fk,lenhsanxuat_fk,thang,nam "+
				" )D on DT.SANPHAM_FK = D.SANPHAM_FK and DT.lenhsanxuat_fk = D.lenhsanxuat_fk and D.thang = '" + this.thang + "' and D.nam = '" + this.nam + "'  "+
				" left join "+
				" ( "+
				" 	SELECT sanpham_fk,lenhsanxuat_fk,thang,nam,sum(tonggiatri) as tonggiatri from  ERP_GIATHANH_KHAUHAOTS_CHITIET_LENHSANXUAT "+
				" 	where  thang = '" + this.thang + "' and nam = '" + this.nam + "'   "+
				" 	group by sanpham_fk,lenhsanxuat_fk,thang,nam "+
				" )C on DT.SANPHAM_FK = C.SANPHAM_FK and DT.lenhsanxuat_fk = C.lenhsanxuat_fk and C.thang = '" + this.thang + "' and C.nam = '" + this.nam + "'  ";
			
		
		
		System.out.println("::: B5.0: GIA THANH LSX: " + query );
		if( !db.update(query) )
		{
			return "Bước 5.1 lỗi: " + query;
		}
		
		query = " insert ERP_GIATHANH_THANHPHAM(  thang, nam, sanpham_fk, soluong, chiphi_nguyenlieu, chiphi_nhancong, chiphi_khauhao, chiphi_sanxuatchung, tongchiphi, dongia )  "+
				" select  '" + this.thang + "', '" + this.nam + "', DT.sanpham_fk, DT.SOLUONGNHAN, isnull( D.tonggiatri, 0 ) chiphi_nguyenlieu,  "+
				" 		isnull(A.tongchiphi, 0) as chiphi_nhancong, isnull(C.tonggiatri, 0) as chiphi_khauhao, isnull(B.tonggiatri, 0) as chiphi_sanxuatchung,  "+
				" 		isnull(A.tongchiphi, 0) + isnull(C.tonggiatri, 0) + isnull(B.tonggiatri, 0) + isnull( D.tonggiatri, 0 )  as tongchiphi, "+
				" 		round(  ( isnull(A.tongchiphi, 0) + isnull(C.tonggiatri, 0) + isnull(B.tonggiatri, 0) + isnull( D.tonggiatri, 0 ) )  / DT.SOLUONGNHAN, 5 ) as dongia "+
				" from  "+
				" (  "+
				" 	SELECT NKSP.SANPHAM_FK, SUM(NKSP.SOLUONGNHAN) as SOLUONGNHAN "+
				" 	FROM ERP_NHANHANG NK           "+
				" 	 	INNER JOIN ERP_NHANHANG_SANPHAM NKSP ON NK.PK_SEQ = NKSP.NHANHANG_FK    "+
				" 	 	INNER JOIN ERP_LENHSANXUAT_GIAY LSX ON LSX.PK_SEQ = NK.lenhsanxuat_fk          "+
				" 	WHERE  SUBSTRING(NK.ngaynhan, 1, 7) = '" + this.chuoingaythang + "'  AND NK.lenhsanxuat_fk IS NOT NULL AND NK.TRANGTHAI = 1          "+
				"		and NKSP.SANPHAM_FK in ( select pk_seq from ERP_SANPHAM where loaisanpham_fk in ( 100002 ) )	" +
				" 	GROUP BY NKSP.SANPHAM_FK  "+
				"  )  "+
				" DT "+
				" left join "+
				" ( "+
				" 	SELECT sanpham_fk,thang,nam,sum(tongchiphi) as tongchiphi from  ERP_GIATHANH_CPNHANCONG_CHITIET "+
				" 	where  thang = '" + this.thang + "' and nam = '" + this.nam + "'  "+
				" 	group by sanpham_fk,thang,nam "+
				" )A on DT.SANPHAM_FK = A.SANPHAM_FK and A.thang = '" + this.thang + "' and A.nam = '" + this.nam + "'   "+
			    " left join "+
				" ( "+
				" 	SELECT sanpham_fk,thang,nam,sum(tonggiatri) as tonggiatri from  ERP_GIATHANH_CPSXCHUNG_CHITIET "+
				" 	where  thang = '" + this.thang + "' and nam = '" + this.nam + "'  "+
				" 	group by sanpham_fk,thang,nam "+
				" )B on DT.SANPHAM_FK = B.SANPHAM_FK and  B.thang = '" + this.thang + "' and B.nam = '" + this.nam + "'   "+
			    " left join "+
				" ( "+
				"	 SELECT sanpham_fk,thang,nam,sum(tonggiatri) as tonggiatri from  ERP_GIATHANH_CPNGUYENLIEU "+
				"	 where  thang = '" + this.thang + "' and nam = '" + this.nam + "'   "+
				"	 group by sanpham_fk,thang,nam "+
				" )D on DT.SANPHAM_FK = D.SANPHAM_FK and D.thang = '" + this.thang + "' and D.nam = '" + this.nam + "'  "+
				" left join "+
				" ( "+
				"	 SELECT sanpham_fk,thang,nam,sum(tonggiatri) as tonggiatri from  ERP_GIATHANH_KHAUHAOTS_CHITIET "+
				"	 where  thang = '" + this.thang + "' and nam = '" + this.nam + "'  "+
				"	 group by sanpham_fk,thang,nam "+
				" )C on DT.SANPHAM_FK = C.SANPHAM_FK and C.thang = '" + this.thang + "' and C.nam = '" + this.nam + "'   ";
			
		
		System.out.println("::: B5.1: GIA THANH TP: " + query );
		if( !db.update(query) )
		{
			return "Bước 5.2 lỗi: " + query;
		}
		
		//CẬP NHẬT ĐỊNH KHOẢN THEO GIÁ THÀNH
		query = "update PS  "+
				"	set PS.DONGIA = NV.dongia, "+
				"		PS.NO = case when ( PS.NO != 0 or isnull( IS_NO, '0' ) = 1 ) then round( PS.SOLUONG * NV.dongia, 0 ) else 0 end, "+
				"		PS.CO = case when ( PS.CO != 0 or isnull( IS_CO, '0' ) = 1 ) then round( PS.SOLUONG * NV.dongia, 0 ) else 0 end  "+
				"from ERP_PHATSINHKETOAN PS inner join  "+
				"( "+
				"	select  cast( a.pk_seq as varchar(10) ) as sochungtu, cast(b.sanpham_fk as varchar(10)) as sanpham,  "+
				"			isnull( (  select dongia from ERP_GIATHANH_LENHSANXUAT  "+
				"						where  thang = '" + this.thang + "' and nam = '" + this.nam + "' and sanpham_fk = b.sanpham_fk and lenhsanxuat_fk = a.lenhsanxuat_fk ), 0 ) as dongia "+
				"	from ERP_NHANHANG a inner join ERP_NHANHANG_SANPHAM b on b.nhanhang_fk = a.pk_seq  "+
				"	where  a.lenhsanxuat_fk is not null and a.trangthai = '1' " + 
				" 		and month( a.NGAYNHAN ) = '" + this.thang + "' and year( a.NGAYNHAN ) = '" + this.nam + "'  "+
				"		and b.sanpham_fk in ( select pk_seq from ERP_SANPHAM where loaisanpham_fk in ( 100002 ) ) "+
				") "+
				"NV on PS.SOCHUNGTU = NV.sochungtu and PS.SANPHAM_FK = NV.sanpham  "+
				"where PS.LOAICHUNGTU in ( N'Nhập kho sản xuất' ) ";
		
		System.out.println("::: B5.3: KE TOAN: " + query );
		if( !db.update(query) )
		{
			return "Bước 5.3 lỗi: " + query;
		}
		
		//CẬP NHẬT GIÁ CỦA SẢN XUẤT
		query = "	update b set b.dongia =  "+
				"			isnull( (  select dongia from ERP_GIATHANH_LENHSANXUAT  "+
				"						where  thang = '" + this.thang + "' and nam = '" + this.nam + "' and sanpham_fk = b.sanpham_fk and lenhsanxuat_fk = a.lenhsanxuat_fk ), 0 ),  "+
				"			b.DONGIAVIET =  "+
				"			isnull( (  select dongia from ERP_GIATHANH_LENHSANXUAT  "+
				"						where  thang = '" + this.thang + "' and nam = '" + this.nam + "' and sanpham_fk = b.sanpham_fk and lenhsanxuat_fk = a.lenhsanxuat_fk ), 0 )  "+
				"	from ERP_NHANHANG a inner join ERP_NHANHANG_SANPHAM b on b.nhanhang_fk = a.pk_seq  "+
				"	where  a.lenhsanxuat_fk is not null and a.trangthai = '1' " + 
				" 		and month( a.NGAYNHAN ) = '" + this.thang + "' and year( a.NGAYNHAN ) = '" + this.nam + "'  "+
				"		and b.sanpham_fk in ( select pk_seq from ERP_SANPHAM where loaisanpham_fk in ( 100002 ) ) ";
		
		System.out.println("::: B5.4: KE TOAN: " + query );
		if( !db.update(query) )
		{
			return "Bước 5.4 lỗi: " + query;
		}
		
		return "";
	}
	
	private String B5_GiaThanh_Import() 
	{
		String query = "delete ERP_GIATHANH_LENHSANXUAT where thang = '" + this.thang + "' and nam = '" + this.nam + "' ";
		db.update(query);
		
		query = "delete ERP_GIATHANH_THANHPHAM where thang = '" + this.thang + "' and nam = '" + this.nam + "' ";
		db.update(query);
		
		query = " insert ERP_GIATHANH_THANHPHAM(  thang, nam, sanpham_fk, soluong, chiphi_nguyenlieu, chiphi_nhancong, chiphi_khauhao, chiphi_sanxuatchung, tongchiphi, dongia, isImport )  "+
				" select '" + this.thang + "' thang, '" + this.nam + "' nam, b.pk_seq as sanpham_fk, a.soluong, 0 chiphi_nguyenlieu, 0 chiphi_nhancong, 0 chiphi_khauhao, 0 chiphi_sanxuatchung, 0 tongchiphi, " + 
				" 		a.giathanh as dongia, 1 as isImport    " + 
				" from ERP_GIATHANH_TEMP a inner join ERP_SANPHAM b on a.masanpham = b.ma " + 
				" where a.thang = '" + this.thang + "' and a.nam = '" + this.nam + "' ";
			
		System.out.println("::: B5.1: GIA THANH TP - Import: " + query );
		if( !db.update(query) )
		{
			return "Bước 5.2 lỗi: " + query;
		}
		
		//CẬP NHẬT ĐỊNH KHOẢN THEO GIÁ THÀNH
		query = "update PS  "+
				"	set PS.DONGIA = NV.dongia, "+
				"		PS.NO = case when ( PS.NO != 0 or isnull( IS_NO, '0' ) = 1 ) then round( PS.SOLUONG * NV.dongia, 0 ) else 0 end, "+
				"		PS.CO = case when ( PS.CO != 0 or isnull( IS_CO, '0' ) = 1 ) then round( PS.SOLUONG * NV.dongia, 0 ) else 0 end  "+
				"from ERP_PHATSINHKETOAN PS inner join  "+
				"( "+
				"	select  cast( a.pk_seq as varchar(10) ) as sochungtu, cast(b.sanpham_fk as varchar(10)) as sanpham,  "+
				"			isnull( (  select dongia from ERP_GIATHANH_LENHSANXUAT  "+
				"						where  thang = '" + this.thang + "' and nam = '" + this.nam + "' and sanpham_fk = b.sanpham_fk and lenhsanxuat_fk = a.lenhsanxuat_fk ), 0 ) as dongia "+
				"	from ERP_NHANHANG a inner join ERP_NHANHANG_SANPHAM b on b.nhanhang_fk = a.pk_seq  "+
				"	where  a.lenhsanxuat_fk is not null and a.trangthai = '1' " + 
				" 		and month( a.NGAYNHAN ) = '" + this.thang + "' and year( a.NGAYNHAN ) = '" + this.nam + "'  "+
				"		and b.sanpham_fk in ( select pk_seq from ERP_SANPHAM where loaisanpham_fk in ( 100002 ) ) "+
				") "+
				"NV on PS.SOCHUNGTU = NV.sochungtu and PS.SANPHAM_FK = NV.sanpham  "+
				"where PS.LOAICHUNGTU in ( N'Nhập kho sản xuất' ) ";
		
		System.out.println("::: B5.3: KE TOAN: " + query );
		if( !db.update(query) )
		{
			return "Bước 5.3 lỗi: " + query;
		}
		
		//CẬP NHẬT GIÁ CỦA SẢN XUẤT
		query = "	update b set b.dongia =  "+
				"			isnull( (  select dongia from ERP_GIATHANH_LENHSANXUAT  "+
				"						where  thang = '" + this.thang + "' and nam = '" + this.nam + "' and sanpham_fk = b.sanpham_fk and lenhsanxuat_fk = a.lenhsanxuat_fk ), 0 ),  "+
				"			b.DONGIAVIET =  "+
				"			isnull( (  select dongia from ERP_GIATHANH_LENHSANXUAT  "+
				"						where  thang = '" + this.thang + "' and nam = '" + this.nam + "' and sanpham_fk = b.sanpham_fk and lenhsanxuat_fk = a.lenhsanxuat_fk ), 0 )  "+
				"	from ERP_NHANHANG a inner join ERP_NHANHANG_SANPHAM b on b.nhanhang_fk = a.pk_seq  "+
				"	where  a.lenhsanxuat_fk is not null and a.trangthai = '1' " + 
				" 		and month( a.NGAYNHAN ) = '" + this.thang + "' and year( a.NGAYNHAN ) = '" + this.nam + "'  "+
				"		and b.sanpham_fk in ( select pk_seq from ERP_SANPHAM where loaisanpham_fk in ( 100002 ) ) ";
		
		System.out.println("::: B5.4: KE TOAN: " + query );
		if( !db.update(query) )
		{
			return "Bước 5.4 lỗi: " + query;
		}
		
		return "";
	}


	private String B4_PhanBoChiPhi_SanXuatChung() 
	{
		String query = "delete ERP_GIATHANH_CPSXCHUNG where thang = '" + this.thang + "' and nam = '" + this.nam + "' ";
		db.update(query);
		
		query = "delete ERP_GIATHANH_CPSXCHUNG_CHITIET where thang = '" + this.thang + "' and nam = '" + this.nam + "' and sanpham_fk in ( select pk_seq from ERP_SANPHAM where loaisanpham_fk in ( 100002 ) ) ";
		db.update(query);
		
		query = "delete ERP_GIATHANH_CPSXCHUNG_CHITIET_LENHSANXUAT where  thang = '" + this.thang + "' and nam = '" + this.nam + "' and sanpham_fk in ( select pk_seq from ERP_SANPHAM where loaisanpham_fk in ( 100002 ) ) ";
		db.update(query);
		
		query = " insert ERP_GIATHANH_CPSXCHUNG( thang, nam,nhamay_fk,chiphi_fk, tonggiatri,tonggiatriNHAP  )	\n" +
				" select '" + this.thang + "', '" + this.nam + "', nm.pk_seq,a.khoanmucchiphi_fk, sum( a.NO -a.CO) as tongchiphi,  "+
				" 		isnull( (  "+
				" 			SELECT  sum( NKSP.SOLUONGNHAN * isnull(HSL.hesoLuongNhom_alpha2, 0) )   "+
				" 			FROM ERP_NHANHANG NK          "+
				" 				 INNER JOIN ERP_NHANHANG_SANPHAM NKSP ON NK.PK_SEQ = NKSP.NHANHANG_FK  "+
				" 				 INNER JOIN ERP_LENHSANXUAT_GIAY LSX ON LSX.PK_SEQ = NK.lenhsanxuat_fk  "+
				" 				 LEFT JOIN  ERP_GIATHANH_HESOLUONG HSL on NKSP.SANPHAM_FK =  HSL.SANPHAM_FK and HSL.thang = '" + this.thang + "' and HSL.NAM = '" + this.nam + "' "+
				" 				 INNER JOIN ERP_NHAMAY NM2 on LSX.nhamay_fk = NM.pk_seq      "+
				" 			WHERE SUBSTRING(NK.ngaynhan, 1, 7) = '" + this.chuoingaythang + "'  AND NK.lenhsanxuat_fk IS NOT NULL  AND NK.TRANGTHAI = 1  "+
				" 				and  NM2.pk_seq = nm.pk_seq     "+
				" 		 ), 0 ) as tonggiatriNHAP "+
				" from ERP_PHATSINHKETOAN a inner join ERP_NHOMCHIPHI b on a.KHOANMUCCHIPHI_FK = b.PK_SEQ "+
				" left join erp_donvithuchien dvth on dvth.pk_seq= b.DONVITHUCHIEN_FK "+
				" left join erp_nhamay nm on nm.dvth_fk = dvth.pk_seq "+
				" where loaichungtu != N'Khấu hao tài sản' and ngaychungtu like '" + this.chuoingaythang + "%' and KHOANMUCCHIPHI_FK is not null "+
				" 		and a.taikhoan_fk in ( select pk_seq from ERP_TAIKHOANKT where sohieutaikhoan like '627%'   )	" +
				"       and nm.pk_seq is not null  " +
				" group by nm.pk_seq,a.khoanmucchiphi_fk " +
				" having sum( isnull(a.NO-a.CO, 0) )  != 0 ";
		
		
		System.out.println("::: B4.1: TINH CHI PHI: " + query );
		if( !db.update(query) )
		{
			return "Bước 1 lỗi: " + query;
		}
		
		
		
		query = " insert ERP_GIATHANH_CPSXCHUNG( thang, nam,chiphi_fk, tonggiatri,tonggiatriNHAP  )	\n" +
				" select '" + this.thang + "', '" + this.nam + "',a.khoanmucchiphi_fk, sum( a.NO -a.CO) as tongchiphi,  "+
				" 		isnull( (  "+
				" 			SELECT  sum( NKSP.SOLUONGNHAN * isnull(HSL.hesoLuongChung_alpha1, 0) )   "+
				" 			FROM ERP_NHANHANG NK          "+
				" 				 INNER JOIN ERP_NHANHANG_SANPHAM NKSP ON NK.PK_SEQ = NKSP.NHANHANG_FK  "+
				" 				 INNER JOIN ERP_LENHSANXUAT_GIAY LSX ON LSX.PK_SEQ = NK.lenhsanxuat_fk  "+
				" 				 LEFT JOIN  ERP_GIATHANH_HESOLUONG HSL on NKSP.SANPHAM_FK =  HSL.SANPHAM_FK and HSL.thang = '" + this.thang + "' and HSL.NAM = '" + this.nam + "' "+
				" 				 INNER JOIN ERP_NHAMAY NM2 on LSX.nhamay_fk = NM.pk_seq      "+
				" 			WHERE SUBSTRING(NK.ngaynhan, 1, 7) = '" + this.chuoingaythang + "'  AND NK.lenhsanxuat_fk IS NOT NULL  AND NK.TRANGTHAI = 1  "+
				" 		 ), 0 ) as tonggiatriNHAP "+
				" from ERP_PHATSINHKETOAN a inner join ERP_NHOMCHIPHI b on a.KHOANMUCCHIPHI_FK = b.PK_SEQ "+
				" left join erp_donvithuchien dvth on dvth.pk_seq= b.DONVITHUCHIEN_FK "+
				" left join erp_nhamay nm on nm.dvth_fk = dvth.pk_seq "+
				" where loaichungtu != N'Khấu hao tài sản' and ngaychungtu like '" + this.chuoingaythang + "%' and KHOANMUCCHIPHI_FK is not null "+
				" 		and a.taikhoan_fk in ( select pk_seq from ERP_TAIKHOANKT where sohieutaikhoan like '627%'   )	" +
				"       and nm.pk_seq is null  " +
				" group by nm.pk_seq,a.khoanmucchiphi_fk " +
				" having sum( isnull(a.NO-a.CO, 0) )  != 0 ";
		
		
		System.out.println("::: B4.1: TINH CHI PHI: " + query );
		if( !db.update(query) )
		{
			return "Bước 1 lỗi: " + query;
		}
		
		//PHÂN BỔ VÀO SẢN PHẨM
		/*query = "insert ERP_GIATHANH_CPSXCHUNG_CHITIET(THANG, NAM, SANPHAM_FK,CHIPHI_FK, SOLUONGNHAP, GIATRINHAP, phanbo_chiphi_sxtructiep, phanbo_chiphi_phanxuong, phanbo_chiphi_chung, tonggiatri ) "+
				" select '" + this.thang + "', '" + this.nam + "', PB.SANPHAM_FK,PB.CHIPHI_FK, SUM( PB.SOLUONGNHAP ) as SOLUONGNHAP, SUM( PB.GIATRINHAP ) as GIATRINHAP,  "+
				" 		0 as phanbo_chiphi_sxtructiep, "+
				" 		0 as phanbo_chiphi_phanxuong, "+
				" 		round( SUM( PB.tonggiatriPB ), 0 ) as phanbo_chiphi_chung, "+
				" 		round( SUM( PB.tonggiatriPB ), 0 )  as tonggiatri "+
				" from  "+
				" ( "+
				" 	SELECT KH.CHIPHI_FK, NKSP.SANPHAM_FK, sum( NKSP.SOLUONGNHAN )  AS SOLUONGNHAP,  "+
				" 			  sum( NKSP.SOLUONGNHAN * isnull(HSL.hesoLuongChung_alpha1, 0) ) as giatriNHAP, "+
				" 			  sum(KH.tonggiatri)* isnull(HSL.hesoLuongChung_alpha1, 0) as tonggiatriPB " +
				" 	FROM ERP_NHANHANG NK          "+
				" 		INNER JOIN ERP_NHANHANG_SANPHAM NKSP ON NK.PK_SEQ = NKSP.NHANHANG_FK   "+
				" 		INNER JOIN ERP_LENHSANXUAT_GIAY LSX ON LSX.PK_SEQ = NK.lenhsanxuat_fk  "+
				" 		LEFT JOIN  ERP_GIATHANH_HESOLUONG HSL on NKSP.SANPHAM_FK =  HSL.SANPHAM_FK and HSL.thang = '" + this.thang + "' and HSL.NAM = '" + this.nam + "'     "+
				" 		INNER JOIN ERP_GIATHANH_CPSXCHUNG KH on  KH.thang = '" + this.thang + "' and KH.NAM = '" + this.nam + "'  AND KH.NHAMAY_FK IS NULL      "+
				" 	WHERE SUBSTRING(NK.ngaynhan, 1, 7) = '" + this.chuoingaythang + "'  AND NK.lenhsanxuat_fk IS NOT NULL  AND NK.TRANGTHAI=1         "+
				"   AND NKSP.SANPHAM_FK in ( select pk_seq from ERP_SANPHAM where loaisanpham_fk in ( 100002 ) ) "+
				"   and HSL.hesoLuongChung_alpha1 > 0 "+
				" 	GROUP BY KH.CHIPHI_FK, KH.tonggiatriNHAP, KH.tonggiatri, NKSP.SANPHAM_FK, HSL.hesoLuongChung_alpha1 "+
				" ) "+
				" PB "+
				" group by PB.SANPHAM_FK,PB.CHIPHI_FK ";
		
		System.out.println("::: B3.2: PHAN BO KHAU HAO - SP: " + query );
		
		
		if( !db.update(query) )
		{
			return "Bước 1 lỗi: " + query;
		}
		
		
		query = "insert ERP_GIATHANH_CPSXCHUNG_CHITIET(THANG, NAM, SANPHAM_FK,CHIPHI_FK, SOLUONGNHAP, GIATRINHAP, phanbo_chiphi_sxtructiep, phanbo_chiphi_phanxuong, phanbo_chiphi_chung, tonggiatri ) "+
				" select '" + this.thang + "', '" + this.nam + "', PB.SANPHAM_FK,PB.CHIPHI_FK, SUM( PB.SOLUONGNHAP ) as SOLUONGNHAP, SUM( PB.GIATRINHAP ) as GIATRINHAP,  "+
				" 		0 as phanbo_chiphi_sxtructiep, "+
				" 		round( SUM( PB.tonggiatriPB ), 0 ) as phanbo_chiphi_phanxuong, "+
				" 		0 as phanbo_chiphi_chung, "+
				" 		round( SUM( PB.tonggiatriPB ), 0 )  as tonggiatri "+
				" from  "+
				" ( "+
				" 	SELECT NM.dvth_fk, LSX.NHAMAY_FK,KH.CHIPHI_FK, NKSP.SANPHAM_FK, sum( NKSP.SOLUONGNHAN )  AS SOLUONGNHAP,  "+
				" 			  sum( NKSP.SOLUONGNHAN * isnull(HSL.hesoLuongNhom_alpha2, 0) ) as giatriNHAP, "+
				"   sum(KH.tonggiatri)*  isnull(HSL.hesoLuongNhom_alpha2, 0) as tonggiatriPB " +
				" 	FROM ERP_NHANHANG NK          "+
				" 		INNER JOIN ERP_NHANHANG_SANPHAM NKSP ON NK.PK_SEQ = NKSP.NHANHANG_FK   "+
				" 		INNER JOIN ERP_LENHSANXUAT_GIAY LSX ON LSX.PK_SEQ = NK.lenhsanxuat_fk  "+
				" 		INNER JOIN ERP_NHAMAY NM on LSX.nhamay_fk = NM.pk_seq   "+
				" 		LEFT JOIN  ERP_GIATHANH_HESOLUONG HSL on NKSP.SANPHAM_FK =  HSL.SANPHAM_FK and HSL.thang = '" + this.thang + "' and HSL.NAM = '" + this.nam + "'     "+
				" 		INNER JOIN ERP_GIATHANH_CPSXCHUNG KH on NM.PK_SEQ = KH.NHAMAY_FK and KH.thang = '" + this.thang + "' and KH.NAM = '" + this.nam + "'        "+
				" 	WHERE SUBSTRING(NK.ngaynhan, 1, 7) = '" + this.chuoingaythang + "'  AND NK.lenhsanxuat_fk IS NOT NULL  AND NK.TRANGTHAI=1         "+
				"   AND NKSP.SANPHAM_FK in ( select pk_seq from ERP_SANPHAM where loaisanpham_fk in ( 100002 ) ) "+
				"   AND HSL.hesoLuongNhom_alpha2 >0 "+
				" 	GROUP BY NM.dvth_fk,KH.CHIPHI_FK, KH.tonggiatriNHAP, KH.tonggiatri, LSX.NHAMAY_FK, NKSP.SANPHAM_FK, HSL.hesoLuongNhom_alpha2 "+
				" ) "+
				" PB  "+
				" group by PB.SANPHAM_FK,PB.CHIPHI_FK ";
		
		System.out.println("::: B3.2: PHAN BO KHAU HAO - SP: " + query );
		if( !db.update(query) )
		{
			return "Bước 1 lỗi: " + query;
		}*/
		
		//phân bổ all nhà máy (alpha 1 chung )
		query = "insert ERP_GIATHANH_CPSXCHUNG_CHITIET(THANG, NAM, SANPHAM_FK,CHIPHI_FK, SOLUONGNHAP, GIATRINHAP, phanbo_chiphi_sxtructiep, phanbo_chiphi_phanxuong, phanbo_chiphi_chung, tonggiatri ) "+
				"select pb.thang,pb.nam,pb.sanpham_fk,pb.chiphi_fk,pb.soluongnhap ,round(  tonggiatripb /soluongnhap , 0 )   as giatriNHAP , \n" +
				" 		0 as phanbo_chiphi_sxtructiep, "+
				" 		0 as phanbo_chiphi_phanxuong, "+
				" 		round( PB.tonggiatriPB , 0 ) as phanbo_chiphi_chung, "+
				" 		round(  PB.tonggiatriPB , 0 )  as tonggiatri "+
				"from \n" +
				"( \n" +
				"select kh.thang, kh.nam, kh.chiphi_fk,hsl.sanpham_fk , \n" +
				"(select sum (NKSP.SOLUONGNHAN) FROM ERP_NHANHANG NK  \n" +
				"INNER JOIN ERP_NHANHANG_SANPHAM NKSP ON NK.PK_SEQ = NKSP.NHANHANG_FK \n" +
				"where nk.lenhsanxuat_fk is not null and SUBSTRING(NK.ngaynhan, 1, 7) = '" + this.chuoingaythang + "' \n" +
				"and  NK.TRANGTHAI=1  and NKSP.sanpham_fk = hsl.sanpham_fk \n" +
				" ) as SOLUONGNHAP , \n" +
				"KH.tonggiatri * isnull(HSL.hesoluongchung_alpha1, 0)/100  as tonggiatriPB   	 \n" +
				" from  ERP_GIATHANH_HESOLUONG hsl \n" +
				"CROSS JOIN  ERP_GIATHANH_CPSXCHUNG  KH \n" +
				"where  kh.nhamay_Fk is null and KH.thang = '" + this.thang + "' and KH.NAM = '" + this.nam + "'  \n" +
				"and hsl.thang = '" + this.thang + "' and hsl.NAM = '" + this.nam + "'  \n" +
				"and isnull(HSL.hesoluongchung_alpha1, 0) > 0 and hsl.sanpham_fk in ( select pk_seq from ERP_SANPHAM where loaisanpham_fk in ( 100002 )) \n" +
				")pb \n";
		System.out.println("::: B3.3: TINH khau hao tai san :  " + query );
		if( !db.update(query) )
		{
			return "Bước 3.3 lỗi: " + query;
		}
				
		//phân bổ chi tiết vào từng nhà máy
		query = "insert ERP_GIATHANH_CPSXCHUNG_CHITIET(THANG, NAM, SANPHAM_FK,CHIPHI_FK, SOLUONGNHAP, GIATRINHAP, phanbo_chiphi_sxtructiep, phanbo_chiphi_phanxuong, phanbo_chiphi_chung, tonggiatri ) "+
				"select pb.thang,pb.nam,pb.sanpham_fk,pb.chiphi_fk,pb.soluongnhap ,round(  tonggiatripb /soluongnhap , 0 )   as giatriNHAP , \n" +
				"0 as phanbo_chiphi_sxtructiep,round(  PB.tonggiatriPB , 0 ) as phanbo_chiphi_phanxuong, \n" +
				"0 as phanbo_chiphi_chung,round( PB.tonggiatriPB , 0 )  as tonggiatri \n" +
				"from \n" +
				"( \n" +
				"select kh.thang, kh.nam, kh.chiphi_fk,hsl.sanpham_fk , \n" +
				"(select sum (NKSP.SOLUONGNHAN) FROM ERP_NHANHANG NK  \n" +
				"INNER JOIN ERP_NHANHANG_SANPHAM NKSP ON NK.PK_SEQ = NKSP.NHANHANG_FK \n" +
				"where nk.lenhsanxuat_fk is not null and SUBSTRING(NK.ngaynhan, 1, 7) = '" + this.chuoingaythang + "' \n" +
				"and  NK.TRANGTHAI=1  and NKSP.sanpham_fk = hsl.sanpham_fk \n" +
				" ) as SOLUONGNHAP , \n" +
				"KH.tonggiatri * isnull(HSL.hesoLuongNhom_alpha2, 0)/100  as tonggiatriPB   	 \n" +
				" from  ERP_GIATHANH_HESOLUONG hsl \n" +
				"inner join ERP_GIATHANH_CPSXCHUNG  KH on   hsl.nhamay_fk = kh.nhamay_fk \n" +
				"where  KH.thang = '" + this.thang + "' and KH.NAM = '" + this.nam + "'  \n" +
				"and isnull(HSL.hesoLuongNhom_alpha2, 0) > 0 and hsl.sanpham_fk in ( select pk_seq from ERP_SANPHAM where loaisanpham_fk in ( 100002 )) \n" +
				")pb \n";
		System.out.println("::: B3.4: TINH khau hao tai san : " + query );
		if( !db.update(query) )
		{
			return "Bước 3.4 lỗi: " + query;
		}
		
		
		
		//Phân bổ vào từng lệnh sản xuất
		query = " insert ERP_GIATHANH_CPSXCHUNG_CHITIET_LENHSANXUAT(THANG, NAM, SANPHAM_FK, LENHSANXUAT_FK, SOLUONGNHAP, GIATRINHAP, tile, phanbo_chiphi_sxtructiep, phanbo_chiphi_phanxuong, phanbo_chiphi_chung, tonggiatri ) "+
				" select '" + this.thang + "', '" + this.nam + "', PB.SANPHAM_FK, PB.LENHSANXUAT_FK, PB.SOLUONGNHAN, PB.giatriNHAP, PB.tile, "+
				" 		round( PB.tile * sum(phanbo_chiphi_sxtructiep), 0), round( PB.tile * sum(phanbo_chiphi_phanxuong), 0 ), round( PB.tile * sum(phanbo_chiphi_chung), 0 ), "+
				" 		round( PB.tile * ( sum(phanbo_chiphi_sxtructiep) +  sum(phanbo_chiphi_phanxuong) + sum(phanbo_chiphi_chung) ), 0 ) as tongiatri "+
				" from "+
				" ( "+
				" 	SELECT NKSP.SANPHAM_FK,  NK.lenhsanxuat_fk, sum(NKSP.SOLUONGNHAN) as SOLUONGNHAN, "+
				" 			sum( NKSP.SOLUONGNHAN * isnull(HSL.dongiaLuong, 0) ) as giatriNHAP, "+
				" 			sum( NKSP.SOLUONGNHAN * isnull(HSL.dongiaLuong, 0) ) /  "+
				" 			( select sum( soluongSX * dongiaLuong ) from ERP_GIATHANH_HESOLUONG where sanpham_fk = NKSP.SANPHAM_FK and thang = '" + this.thang + "' and nam = '" + this.nam + "' ) as tile "+
				" 	FROM ERP_NHANHANG NK          "+
				" 		INNER JOIN ERP_NHANHANG_SANPHAM NKSP ON NK.PK_SEQ = NKSP.NHANHANG_FK   "+
				" 		INNER JOIN ERP_LENHSANXUAT_GIAY LSX ON LSX.PK_SEQ = NK.lenhsanxuat_fk   "+
				" 		LEFT JOIN  ERP_GIATHANH_HESOLUONG HSL on NKSP.SANPHAM_FK =  HSL.SANPHAM_FK and HSL.thang = '" + this.thang + "' and HSL.NAM = '" + this.nam + "'       "+
				" 	WHERE SUBSTRING(NK.ngaynhan, 1, 7) = '" + this.chuoingaythang + "'  AND NK.lenhsanxuat_fk IS NOT NULL AND NK.TRANGTHAI = 1         "+
				" 	GROUP BY NKSP.SANPHAM_FK,  NK.lenhsanxuat_fk "+
				" ) "+
				" PB INNER JOIN ERP_GIATHANH_CPSXCHUNG_CHITIET KH on PB.sanpham_fk = KH.sanpham_fk and KH.thang = '" + this.thang + "' and KH.NAM = '" + this.nam + "'  "+
				" GROUP BY PB.SANPHAM_FK, PB.LENHSANXUAT_FK, PB.SOLUONGNHAN, PB.giatriNHAP, PB.tile ";
		System.out.println("::: B3.3: PHAN BO KHAU HAO - SP - LSX: " + query );
		if( !db.update(query) )
		{
			return "Bước 1 lỗi: " + query;
		}
		
		//Lưu lại số tiền từng tài khoản 62xx phân bổ vào phòng ban nào để lấy 15xx tương ứng lúc kết chuyển
		
		
		return "";
	}

	private String B3_PhanBoKhauHao_TaiSan() 
	{
		
		

		String query = "delete ERP_GIATHANH_KHAUHAOTS where thang = '" + this.thang + "' and nam = '" + this.nam + "' ";
		db.update(query);
		
		query = "delete ERP_GIATHANH_KHAUHAOTS_CHITIET where thang = '" + this.thang + "' and nam = '" + this.nam + "' and sanpham_fk in ( select pk_seq from ERP_SANPHAM where loaisanpham_fk in ( 100002 ) ) ";
		db.update(query);
		
		query = "delete ERP_GIATHANH_KHAUHAOTS_CHITIET_LENHSANXUAT where  thang = '" + this.thang + "' and nam = '" + this.nam + "' and sanpham_fk in ( select pk_seq from ERP_SANPHAM where loaisanpham_fk in ( 100002 ) ) ";
		db.update(query);
		
		query = " insert ERP_GIATHANH_KHAUHAOTS( thang, nam,nhamay_fk,chiphi_fk, tonggiatri,tonggiatriNHAP  )	\n" +
				" select '" + this.thang + "', '" + this.nam + "', nm.pk_seq,a.khoanmucchiphi_fk, sum( a.NO -a.CO) as tongchiphi,  "+
				" 		isnull( (  "+
				" 			SELECT  sum( NKSP.SOLUONGNHAN * isnull(HSL.hesoLuongNhom_alpha2, 0) )   "+
				" 			FROM ERP_NHANHANG NK          "+
				" 				 INNER JOIN ERP_NHANHANG_SANPHAM NKSP ON NK.PK_SEQ = NKSP.NHANHANG_FK  "+
				" 				 INNER JOIN ERP_LENHSANXUAT_GIAY LSX ON LSX.PK_SEQ = NK.lenhsanxuat_fk  "+
				" 				 LEFT JOIN  ERP_GIATHANH_HESOLUONG HSL on NKSP.SANPHAM_FK =  HSL.SANPHAM_FK and HSL.thang = '" + this.thang + "' and HSL.NAM = '" + this.nam + "' "+
				" 				 INNER JOIN ERP_NHAMAY NM2 on LSX.nhamay_fk = NM.pk_seq      "+
				" 			WHERE SUBSTRING(NK.ngaynhan, 1, 7) = '" + this.chuoingaythang + "'  AND NK.lenhsanxuat_fk IS NOT NULL  AND NK.TRANGTHAI = 1  "+
				" 				and  NM2.pk_seq = nm.pk_seq     "+
				" 		 ), 0 ) as tonggiatriNHAP "+
				" from ERP_PHATSINHKETOAN a inner join ERP_NHOMCHIPHI b on a.KHOANMUCCHIPHI_FK = b.PK_SEQ "+
				" left join erp_donvithuchien dvth on dvth.pk_seq= b.DONVITHUCHIEN_FK "+
				" left join erp_nhamay nm on nm.dvth_fk = dvth.pk_seq "+
				" where loaichungtu = N'Khấu hao tài sản' and ngaychungtu like '" + this.chuoingaythang + "%' and KHOANMUCCHIPHI_FK is not null "+
				" 		and a.taikhoan_fk in ( select pk_seq from ERP_TAIKHOANKT where sohieutaikhoan like '627%'   )	" +
				"       and nm.pk_seq is not null  " +
				" group by nm.pk_seq,a.khoanmucchiphi_fk " +
				" having sum( isnull(a.NO-a.CO, 0) )  != 0 ";
		
		
		System.out.println("::: B3.1: TINH khau hao tai san : " + query );
		if( !db.update(query) )
		{
			return "Bước 3.1 lỗi: " + query;
		}
		
		
		
		query = " insert ERP_GIATHANH_KHAUHAOTS( thang, nam,chiphi_fk, tonggiatri,tonggiatriNHAP  )	\n" +
				" select '" + this.thang + "', '" + this.nam + "',a.khoanmucchiphi_fk, sum( a.NO -a.CO) as tongchiphi,  "+
				" 		isnull( (  "+
				" 			SELECT  sum( NKSP.SOLUONGNHAN * isnull(HSL.hesoLuongChung_alpha1, 0) )   "+
				" 			FROM ERP_NHANHANG NK          "+
				" 				 INNER JOIN ERP_NHANHANG_SANPHAM NKSP ON NK.PK_SEQ = NKSP.NHANHANG_FK  "+
				" 				 INNER JOIN ERP_LENHSANXUAT_GIAY LSX ON LSX.PK_SEQ = NK.lenhsanxuat_fk  "+
				" 				 LEFT JOIN  ERP_GIATHANH_HESOLUONG HSL on NKSP.SANPHAM_FK =  HSL.SANPHAM_FK and HSL.thang = '" + this.thang + "' and HSL.NAM = '" + this.nam + "' "+
				" 				 INNER JOIN ERP_NHAMAY NM2 on LSX.nhamay_fk = NM.pk_seq      "+
				" 			WHERE SUBSTRING(NK.ngaynhan, 1, 7) = '" + this.chuoingaythang + "'  AND NK.lenhsanxuat_fk IS NOT NULL  AND NK.TRANGTHAI = 1  "+
				" 		 ), 0 ) as tonggiatriNHAP "+
				" from ERP_PHATSINHKETOAN a inner join ERP_NHOMCHIPHI b on a.KHOANMUCCHIPHI_FK = b.PK_SEQ "+
				" left join erp_donvithuchien dvth on dvth.pk_seq= b.DONVITHUCHIEN_FK "+
				" left join erp_nhamay nm on nm.dvth_fk = dvth.pk_seq "+
				" where loaichungtu = N'Khấu hao tài sản' and ngaychungtu like '" + this.chuoingaythang + "%' and KHOANMUCCHIPHI_FK is not null "+
				" 		and a.taikhoan_fk in ( select pk_seq from ERP_TAIKHOANKT where sohieutaikhoan like '627%'   )	" +
				"       and nm.pk_seq is null  " +
				" group by nm.pk_seq,a.khoanmucchiphi_fk " +
				" having sum( isnull(a.NO-a.CO, 0) )  != 0 ";
		
		
		System.out.println("::: B3.2: TINH khau hao tai san : " + query );
		if( !db.update(query) )
		{
			return "Bước 3.2 lỗi: " + query;
		}
		
		//PHÂN BỔ VÀO SẢN PHẨM
		/*query = "insert ERP_GIATHANH_KHAUHAOTS_CHITIET(THANG, NAM, SANPHAM_FK,CHIPHI_FK, SOLUONGNHAP, GIATRINHAP, phanbo_khauhao_sxtructiep, phanbo_khauhao_phanxuong, phanbo_khauhao_chung, tonggiatri ) "+
				" select '" + this.thang + "', '" + this.nam + "', PB.SANPHAM_FK,PB.CHIPHI_FK, SUM( PB.SOLUONGNHAP ) as SOLUONGNHAP, SUM( PB.GIATRINHAP ) as GIATRINHAP,  "+
				" 		0 as phanbo_chiphi_sxtructiep, "+
				" 		0 as phanbo_chiphi_phanxuong, "+
				" 		round( SUM( PB.tonggiatriPB ), 0 ) as phanbo_chiphi_chung, "+
				" 		round( SUM( PB.tonggiatriPB ), 0 )  as tonggiatri "+
				" from  "+
				" ( "+
				" 	SELECT KH.CHIPHI_FK, NKSP.SANPHAM_FK, sum( NKSP.SOLUONGNHAN )  AS SOLUONGNHAP,  "+
				" 			  sum( NKSP.SOLUONGNHAN * isnull(HSL.hesoLuongChung_alpha1, 0) ) as giatriNHAP, "+
				" 			 KH.tonggiatri * isnull(HSL.hesoLuongChung_alpha1, 0)/100 as tonggiatriPB " +
				" 	FROM ERP_NHANHANG NK          "+
				" 		INNER JOIN ERP_NHANHANG_SANPHAM NKSP ON NK.PK_SEQ = NKSP.NHANHANG_FK   "+
				" 		INNER JOIN ERP_LENHSANXUAT_GIAY LSX ON LSX.PK_SEQ = NK.lenhsanxuat_fk  "+
				" 		LEFT JOIN  ERP_GIATHANH_HESOLUONG HSL on NKSP.SANPHAM_FK =  HSL.SANPHAM_FK and HSL.thang = '" + this.thang + "' and HSL.NAM = '" + this.nam + "'     "+
				" 		INNER JOIN ERP_GIATHANH_KHAUHAOTS KH on  KH.thang = '" + this.thang + "' and KH.NAM = '" + this.nam + "'  AND KH.NHAMAY_FK IS NULL      "+
				" 	WHERE SUBSTRING(NK.ngaynhan, 1, 7) = '" + this.chuoingaythang + "'  AND NK.lenhsanxuat_fk IS NOT NULL  AND NK.TRANGTHAI=1         "+
				"   AND NKSP.SANPHAM_FK in ( select pk_seq from ERP_SANPHAM where loaisanpham_fk in ( 100002 ) ) "+
				"   AND HSL.hesoLuongChung_alpha1 >0 "+
				" 	GROUP BY KH.CHIPHI_FK, KH.tonggiatriNHAP, KH.tonggiatri, NKSP.SANPHAM_FK, HSL.hesoLuongChung_alpha1,KH.tonggiatri "+
				" ) "+
				" PB "+
				" group by PB.SANPHAM_FK,PB.CHIPHI_FK ";
		
		System.out.println("::: B3.3: TINH khau hao tai san :  " + query );
		
		
		if( !db.update(query) )
		{
			return "Bước 3.3 lỗi: " + query;
		}
		
		
		query = "insert ERP_GIATHANH_KHAUHAOTS_CHITIET(THANG, NAM, SANPHAM_FK,CHIPHI_FK, SOLUONGNHAP, GIATRINHAP, phanbo_khauhao_sxtructiep, phanbo_khauhao_phanxuong, phanbo_khauhao_chung, tonggiatri ) "+
				" select '" + this.thang + "', '" + this.nam + "', PB.SANPHAM_FK,PB.CHIPHI_FK, SUM( PB.SOLUONGNHAP ) as SOLUONGNHAP, SUM( PB.GIATRINHAP ) as GIATRINHAP,  "+
				" 		0 as phanbo_chiphi_sxtructiep, "+
				" 		round( SUM( PB.tonggiatriPB ), 0 ) as phanbo_chiphi_phanxuong, "+
				" 		0 as phanbo_chiphi_chung, "+
				" 		round( SUM( PB.tonggiatriPB ), 0 )  as tonggiatri "+
				" from  "+
				" ( "+
				" 	SELECT NM.dvth_fk, LSX.NHAMAY_FK,KH.CHIPHI_FK, NKSP.SANPHAM_FK, sum( NKSP.SOLUONGNHAN )  AS SOLUONGNHAP,  "+
				" 			  sum( NKSP.SOLUONGNHAN * isnull(HSL.hesoLuongNhom_alpha2, 0) ) as giatriNHAP, "+
				" 			KH.tonggiatri * isnull(HSL.hesoLuongNhom_alpha2, 0)/100 as tonggiatriPB  " +
				" 	FROM ERP_NHANHANG NK          "+
				" 		INNER JOIN ERP_NHANHANG_SANPHAM NKSP ON NK.PK_SEQ = NKSP.NHANHANG_FK   "+
				" 		INNER JOIN ERP_LENHSANXUAT_GIAY LSX ON LSX.PK_SEQ = NK.lenhsanxuat_fk  "+
				" 		INNER JOIN ERP_NHAMAY NM on LSX.nhamay_fk = NM.pk_seq   "+
				" 		LEFT JOIN  ERP_GIATHANH_HESOLUONG HSL on NKSP.SANPHAM_FK =  HSL.SANPHAM_FK and HSL.thang = '" + this.thang + "' and HSL.NAM = '" + this.nam + "'     "+
				" 		INNER JOIN ERP_GIATHANH_KHAUHAOTS KH on NM.PK_SEQ = KH.NHAMAY_FK and KH.thang = '" + this.thang + "' and KH.NAM = '" + this.nam + "'        "+
				" 	WHERE SUBSTRING(NK.ngaynhan, 1, 7) = '" + this.chuoingaythang + "'  AND NK.lenhsanxuat_fk IS NOT NULL  AND NK.TRANGTHAI=1         "+
				"   AND NKSP.SANPHAM_FK in ( select pk_seq from ERP_SANPHAM where loaisanpham_fk in ( 100002 ) ) "+
				"   AND HSL.hesoLuongNhom_alpha2 >0 "+
				" 	GROUP BY NM.dvth_fk,KH.CHIPHI_FK, KH.tonggiatriNHAP, KH.tonggiatri, LSX.NHAMAY_FK, NKSP.SANPHAM_FK, HSL.hesoLuongNhom_alpha2,KH.tonggiatri "+
				" ) "+
				" PB  "+
				" group by PB.SANPHAM_FK,PB.CHIPHI_FK ";
		
		System.out.println("::: B3.4: TINH khau hao tai san : " + query );
		if( !db.update(query) )
		{
			return "Bước 3.4 lỗi: " + query;
		}*/
		
		//phân bổ all nhà máy (alpha 1 chung )
		query = "insert ERP_GIATHANH_KHAUHAOTS_CHITIET(THANG, NAM, SANPHAM_FK,CHIPHI_FK, SOLUONGNHAP, GIATRINHAP, phanbo_khauhao_sxtructiep, phanbo_khauhao_phanxuong, phanbo_khauhao_chung, tonggiatri ) "+
				"select pb.thang,pb.nam,pb.sanpham_fk,pb.chiphi_fk,pb.soluongnhap ,round(  tonggiatripb /soluongnhap , 0 )   as giatriNHAP , \n" +
				" 		0 as phanbo_chiphi_sxtructiep, "+
				" 		0 as phanbo_chiphi_phanxuong, "+
				" 		round( PB.tonggiatriPB , 0 ) as phanbo_chiphi_chung, "+
				" 		round(  PB.tonggiatriPB , 0 )  as tonggiatri "+
				"from \n" +
				"( \n" +
				"select kh.thang, kh.nam, kh.chiphi_fk,hsl.sanpham_fk , \n" +
				"(select sum (NKSP.SOLUONGNHAN) FROM ERP_NHANHANG NK  \n" +
				"INNER JOIN ERP_NHANHANG_SANPHAM NKSP ON NK.PK_SEQ = NKSP.NHANHANG_FK \n" +
				"where nk.lenhsanxuat_fk is not null and SUBSTRING(NK.ngaynhan, 1, 7) = '" + this.chuoingaythang + "' \n" +
				"and  NK.TRANGTHAI=1  and NKSP.sanpham_fk = hsl.sanpham_fk \n" +
				" ) as SOLUONGNHAP , \n" +
				"KH.tonggiatri * isnull(HSL.hesoluongchung_alpha1, 0)/100  as tonggiatriPB   	 \n" +
				" from  ERP_GIATHANH_HESOLUONG hsl \n" +
				"CROSS JOIN  ERP_GIATHANH_KHAUHAOTS  KH \n" +
				"where  kh.nhamay_Fk is null and KH.thang = '" + this.thang + "' and KH.NAM = '" + this.nam + "'  \n" +
				"and hsl.thang = '" + this.thang + "' and hsl.NAM = '" + this.nam + "'  \n" +
				"and isnull(HSL.hesoluongchung_alpha1, 0) > 0 and hsl.sanpham_fk in ( select pk_seq from ERP_SANPHAM where loaisanpham_fk in ( 100002 )) \n" +
				")pb \n";
		System.out.println("::: B3.3: TINH khau hao tai san :  " + query );
		if( !db.update(query) )
		{
			return "Bước 3.3 lỗi: " + query;
		}
		
		//phân bổ chi tiết vào từng nhà máy
		query = "insert ERP_GIATHANH_KHAUHAOTS_CHITIET(THANG, NAM, SANPHAM_FK,CHIPHI_FK, SOLUONGNHAP, GIATRINHAP, phanbo_khauhao_sxtructiep, phanbo_khauhao_phanxuong, phanbo_khauhao_chung, tonggiatri ) "+
				"select pb.thang,pb.nam,pb.sanpham_fk,pb.chiphi_fk,pb.soluongnhap ,round(  tonggiatripb /soluongnhap , 0 )   as giatriNHAP , \n" +
				"0 as phanbo_chiphi_sxtructiep,round(  PB.tonggiatriPB , 0 ) as phanbo_chiphi_phanxuong, \n" +
				"0 as phanbo_chiphi_chung,round( PB.tonggiatriPB , 0 )  as tonggiatri \n" +
				"from \n" +
				"( \n" +
				"select kh.thang, kh.nam, kh.chiphi_fk,hsl.sanpham_fk , \n" +
				"(select sum (NKSP.SOLUONGNHAN) FROM ERP_NHANHANG NK  \n" +
				"INNER JOIN ERP_NHANHANG_SANPHAM NKSP ON NK.PK_SEQ = NKSP.NHANHANG_FK \n" +
				"where nk.lenhsanxuat_fk is not null and SUBSTRING(NK.ngaynhan, 1, 7) = '" + this.chuoingaythang + "' \n" +
				"and  NK.TRANGTHAI=1  and NKSP.sanpham_fk = hsl.sanpham_fk \n" +
				" ) as SOLUONGNHAP , \n" +
				"KH.tonggiatri * isnull(HSL.hesoLuongNhom_alpha2, 0)/100  as tonggiatriPB   	 \n" +
				" from  ERP_GIATHANH_HESOLUONG hsl \n" +
				"inner join ERP_GIATHANH_KHAUHAOTS  KH on   hsl.nhamay_fk = kh.nhamay_fk \n" +
				"where  KH.thang = '" + this.thang + "' and KH.NAM = '" + this.nam + "'  \n" +
				"and isnull(HSL.hesoLuongNhom_alpha2, 0) > 0 and hsl.sanpham_fk in ( select pk_seq from ERP_SANPHAM where loaisanpham_fk in ( 100002 )) \n" +
				")pb \n";
		System.out.println("::: B3.4: TINH khau hao tai san : " + query );
		if( !db.update(query) )
		{
			return "Bước 3.4 lỗi: " + query;
		}
		
		//Phân bổ vào từng lệnh sản xuất
		query = " insert ERP_GIATHANH_KHAUHAOTS_CHITIET_LENHSANXUAT(THANG, NAM, SANPHAM_FK, LENHSANXUAT_FK, SOLUONGNHAP, GIATRINHAP, tile, phanbo_khauhao_sxtructiep, phanbo_khauhao_phanxuong, phanbo_khauhao_chung, tonggiatri ) "+
				" select '" + this.thang + "', '" + this.nam + "', PB.SANPHAM_FK, PB.LENHSANXUAT_FK, PB.SOLUONGNHAN, PB.giatriNHAP, PB.tile, "+
				" 		round( PB.tile * sum(phanbo_khauhao_sxtructiep), 0), round( PB.tile * sum(phanbo_khauhao_phanxuong), 0 ), round( PB.tile * sum(phanbo_khauhao_chung), 0 ), "+
				" 		round( PB.tile * ( sum(phanbo_khauhao_sxtructiep) +  sum(phanbo_khauhao_phanxuong) + sum(phanbo_khauhao_chung) ), 0 ) as tongiatri "+
				" from "+
				" ( "+
				" 	SELECT NKSP.SANPHAM_FK,  NK.lenhsanxuat_fk, sum(NKSP.SOLUONGNHAN) as SOLUONGNHAN, "+
				" 			sum( NKSP.SOLUONGNHAN * isnull(HSL.dongiaLuong, 0) ) as giatriNHAP, "+
				" 			sum( NKSP.SOLUONGNHAN * isnull(HSL.dongiaLuong, 0) ) /  "+
				" 			( select sum( soluongSX * dongiaLuong ) from ERP_GIATHANH_HESOLUONG where sanpham_fk = NKSP.SANPHAM_FK and thang = '" + this.thang + "' and nam = '" + this.nam + "' ) as tile "+
				" 	FROM ERP_NHANHANG NK          "+
				" 		INNER JOIN ERP_NHANHANG_SANPHAM NKSP ON NK.PK_SEQ = NKSP.NHANHANG_FK   "+
				" 		INNER JOIN ERP_LENHSANXUAT_GIAY LSX ON LSX.PK_SEQ = NK.lenhsanxuat_fk   "+
				" 		LEFT JOIN  ERP_GIATHANH_HESOLUONG HSL on NKSP.SANPHAM_FK =  HSL.SANPHAM_FK and HSL.thang = '" + this.thang + "' and HSL.NAM = '" + this.nam + "'       "+
				" 	WHERE SUBSTRING(NK.ngaynhan, 1, 7) = '" + this.chuoingaythang + "'  AND NK.lenhsanxuat_fk IS NOT NULL AND NK.TRANGTHAI = 1         "+
				" 	GROUP BY NKSP.SANPHAM_FK,  NK.lenhsanxuat_fk "+
				" ) "+
				" PB INNER JOIN ERP_GIATHANH_KHAUHAOTS_CHITIET KH on PB.sanpham_fk = KH.sanpham_fk and KH.thang = '" + this.thang + "' and KH.NAM = '" + this.nam + "'  "+
				" GROUP BY PB.SANPHAM_FK, PB.LENHSANXUAT_FK, PB.SOLUONGNHAN, PB.giatriNHAP, PB.tile ";
		System.out.println("::: B3.5: TINH khau hao tai san : " + query );
		if( !db.update(query) )
		{
			return "Bước 3.5 lỗi: " + query;
		} 
		
		
		
		//Lưu lại số tiền từng tài khoản 62xx phân bổ vào phòng ban nào để lấy 15xx tương ứng lúc kết chuyển
		
		
		return "";
	}

	private String B2_PhanBoChiPhi_NhanCongTrucTiep() 
	{
		//62210000				+ Chi phí Lương nhân viên trực tiếp
		//62220000				+ Thưởng nhân viên trực tiếp
		//622 khác 2 mục trên	+ Chi phí khác cho Nhân viên trực tiếp
		
		String query = "delete ERP_GIATHANH_CPNHANCONG where thang = '" + this.thang + "' and nam = '" + this.nam + "' ";
		db.update(query);
		
		query = "delete ERP_GIATHANH_CPNHANCONG_CHITIET where thang = '" + this.thang + "' and nam = '" + this.nam + "' ";
		db.update(query);
		
		query = "delete ERP_GIATHANH_CPNHANCONG_CHITIET_LENHSANXUAT where thang = '" + this.thang + "' and nam = '" + this.nam + "' ";
		db.update(query);
		
		query = " insert ERP_GIATHANH_CPNHANCONG( thang, nam, luongCongnhan, thuongCongnhan, chiphiKhac )	" +
				" select '" + this.thang + "' as thang, '" + this.nam + "' as nam, "+
				" 	isnull "+
				" 	( ( "+
				" 		select SUM(  NO - CO  ) "+
				" 		from ERP_PHATSINHKETOAN  "+
				" 		where ngaychungtu like '" + this.chuoingaythang + "%'  and NO > 0 and taikhoan_fk in ( select pk_seq from ERP_TAIKHOANKT where sohieutaikhoan = '62210000' and npp_fk = '1' ) "+
				"       ), 0 ) as luongCN_tructiep, "+
				" 	isnull "+
				" 	( ( "+
				" 		select SUM( NO - CO ) "+
				" 		from ERP_PHATSINHKETOAN  "+
				" 		where ngaychungtu like '" + this.chuoingaythang + "%' and NO > 0 and taikhoan_fk in ( select pk_seq from ERP_TAIKHOANKT where sohieutaikhoan = '62220000'  and npp_fk = '1' ) "+
				" 	  ), 0 ) as thuongCN_tructiep, "+
				" 	isnull "+
				" 	( ( "+
				" 		select SUM(  NO - CO  ) "+
				" 		from ERP_PHATSINHKETOAN  "+
				" 		where ngaychungtu like '" + this.chuoingaythang + "%'  and NO > 0 and taikhoan_fk in ( select pk_seq from ERP_TAIKHOANKT where sohieutaikhoan like '622%' and sohieutaikhoan not in ( '62210000', '62220000' )  and npp_fk = '1' ) "+
				" 	  ), 0 ) as chiphiKhac ";
		
		//and loaichungtu = N'Bút toán tổng hợp'
		System.out.println("::: B2.1: TINH CHI PHI: " + query );
		if( !db.update(query) )
		{
			return "Bước 1 lỗi: " + query;
		}
		
		query = " insert ERP_GIATHANH_CPNHANCONG_CHITIET( thang, nam, sanpham_fk, luongCongnhan, thuongCongnhan, chiphiKhac, tongchiphi )  "+
				" select hs.thang, hs.nam, hs.sanpham_fk, " + 
				" 			round( luongCongnhan * hesoluongCHUNG_alpha1 / 100, 0) as luongCongnhan, " + 
				" 			round( thuongCongnhan * hesoluongCHUNG_alpha1 / 100, 0) as thuongCongnhan, " + 
				" 			round( chiphiKhac * hesoluongCHUNG_alpha1 / 100, 0) as chiphiKhac, "+
				" 			round( ( luongCongnhan + thuongCongnhan + chiphiKhac )	* ( hesoluongCHUNG_alpha1 / 100 ), 0 ) as 	tongchiphi "+
				" from ERP_GIATHANH_HESOLUONG hs inner join ERP_GIATHANH_CPNHANCONG cp on hs.thang = cp.thang and hs.nam = cp.nam "+
				" where hs.thang = '" + this.thang + "' and hs.nam = '" + this.nam + "' ";
		
		System.out.println("::: B2.2: PB CHI PHI VAO SAN PHAM: " + query );
		if( !db.update(query) )
		{
			return "Bước 1 lỗi: " + query;
		}
		
		//Phân bổ vào từng lệnh sản xuất
		query = " insert ERP_GIATHANH_CPNHANCONG_CHITIET_LENHSANXUAT(THANG, NAM, SANPHAM_FK, LENHSANXUAT_FK, tile, luongCongnhan, thuongCongnhan, chiphiKhac, tongchiphi ) "+
				" select '" + this.thang + "', '" + this.nam + "', PB.SANPHAM_FK, PB.LENHSANXUAT_FK, PB.tile,  "+
				" 		round( PB.tile * luongCongnhan, 0), round( PB.tile * thuongCongnhan, 0), round( PB.tile * chiphiKhac, 0), "+
				" 		round( PB.tile * ( luongCongnhan +  thuongCongnhan + chiphiKhac ), 0) as tongchiphi "+
				" from "+
				" ( "+
				" 	SELECT NKSP.SANPHAM_FK,  NK.lenhsanxuat_fk, sum(NKSP.SOLUONGNHAN) as SOLUONGNHAN, "+
				" 			sum( NKSP.SOLUONGNHAN * isnull(HSL.dongiaLuong, 0) ) as giatriNHAP, "+
				" 			sum( NKSP.SOLUONGNHAN * isnull(HSL.dongiaLuong, 0) ) /  "+
				" 			( select sum( soluongSX * dongiaLuong ) from ERP_GIATHANH_HESOLUONG where sanpham_fk = NKSP.SANPHAM_FK and thang = '" + this.thang + "' and nam = '" + this.nam + "' ) as tile "+
				" 	FROM ERP_NHANHANG NK          "+
				" 		INNER JOIN ERP_NHANHANG_SANPHAM NKSP ON NK.PK_SEQ = NKSP.NHANHANG_FK   "+
				" 		INNER JOIN ERP_LENHSANXUAT_GIAY LSX ON LSX.PK_SEQ = NK.lenhsanxuat_fk   "+
				" 		LEFT JOIN  ERP_GIATHANH_HESOLUONG HSL on NKSP.SANPHAM_FK =  HSL.SANPHAM_FK and HSL.thang = '" + this.thang + "' and HSL.NAM = '" + this.nam + "'       "+
				" 	WHERE SUBSTRING(NK.ngaynhan, 1, 7) = '" + this.chuoingaythang + "'  AND NK.lenhsanxuat_fk IS NOT NULL AND NK.TRANGTHAI = 1         "+
				" 	GROUP BY NKSP.SANPHAM_FK,  NK.lenhsanxuat_fk "+
				" ) "+
				" PB INNER JOIN ERP_GIATHANH_CPNHANCONG_CHITIET KH on PB.sanpham_fk = KH.sanpham_fk and KH.thang = '" + this.thang + "' and KH.NAM = '" + this.nam + "'  ";
		
		System.out.println("::: B2.3: PB CHI PHI VAO SP - LSX: " + query );
		if( !db.update(query) )
		{
			return "Bước 2 lỗi: " + query;
		}
		
		return "";
	}

	private String B1_TinhHeSoLuong() 
	{
		String query = "";
		
		query = " delete ERP_GIATHANH_HESOLUONG where thang = '" + this.thang + "' and nam = '" + this.nam + "' ";
		db.update(query);
		
		query = " insert ERP_GIATHANH_HESOLUONG( thang, nam, sanpham_fk, nhamay_fk, nhomsanpham_fk, soluongSX, dongiaLuong, luongdinhmuc, hesoLuongChung_alpha1, hesoLuongNhom_alpha2 )  "+
				" select '" + this.thang + "' thang, '" + this.nam + "' nam, NK.sanpham_fk, NK.nhamay_fk, NK.nhamay_fk, NK.SOLUONGNHAP, DG.dongiaLuong, NK.SOLUONGNHAP * DG.dongiaLuong as luongdinhmuc,   "+
				"  		( NK.SOLUONGNHAP * isnull(DG.dongiaLuong, 0) ) / ( SELECT  sum( NKSP2.SOLUONGNHAN   * isnull(nsp2.dongiaLuong, 0)  )       "+
				"  												FROM ERP_NHANHANG NK2 INNER JOIN ERP_NHANHANG_SANPHAM NKSP2 ON NK2.PK_SEQ = NKSP2.NHANHANG_FK    "+
				"														INNER join ERP_GIATHANH_DONGIALUONG nsp2 on NKSP2.sanpham_fk = nsp2.sanpham_fk and nsp2.thang = '" + this.thang + "' and nsp2.nam = '" + this.nam + "' 		" +
				"  												WHERE SUBSTRING(NK2.ngaynhan, 1, 7) = '" + this.chuoingaythang + "'  AND NK2.lenhsanxuat_fk IS NOT NULL  AND NK2.TRANGTHAI = 1 "+
				"  												and NKSP2.sanpham_fk in ( select pk_seq from ERP_SANPHAM where loaisanpham_fk in ( 100002 ) )   "+
				")   "+
				"  			* 100  as hesoLuongChung_alpha1,   "+
				"  		( NK.SOLUONGNHAP * isnull(DG.dongiaLuong, 0) ) / ( SELECT sum( NKSP2.SOLUONGNHAN   * nsp2.dongiaLuong  )    "+
				"  												FROM ERP_NHANHANG NK2 INNER JOIN ERP_NHANHANG_SANPHAM NKSP2 ON NK2.PK_SEQ = NKSP2.NHANHANG_FK    "+
				"  													 LEFT join ERP_GIATHANH_DONGIALUONG nsp2 on NKSP2.sanpham_fk = nsp2.sanpham_fk and nsp2.thang = '" + this.thang + "' and nsp2.nam = '" + this.nam + "'         "+
				"  												WHERE SUBSTRING(NK2.ngaynhan, 1, 7) = '" + this.chuoingaythang + "' AND NK2.lenhsanxuat_fk IS NOT NULL  AND NK2.TRANGTHAI = 1 AND isnull(NSP2.NHAMAY_FK, 1) = isnull(DG.NHAMAY_FK, 1)  "+
				"  												and NKSP2.sanpham_fk in ( select pk_seq from ERP_SANPHAM where loaisanpham_fk in ( 100002 ) )   "+
				"  )   "+
				"  			* 100  as hesoLuongNhom_alpha2  "+
				"  from  "+
				"  (  "+
				"  	SELECT  NKSP.SANPHAM_FK, isnull(nsp.nhamay_fk, 1) as nhamay_fk, sum( NKSP.SOLUONGNHAN )  AS SOLUONGNHAP      "+
				"  	FROM ERP_NHANHANG NK           "+
				"  		INNER JOIN ERP_NHANHANG_SANPHAM NKSP ON NK.PK_SEQ = NKSP.NHANHANG_FK   "+
				"  		LEFT join ERP_GIATHANH_DONGIALUONG nsp on NKSP.sanpham_fk = nsp.sanpham_fk and nsp.thang = '" + this.thang + "' and nsp.nam = '" + this.nam + "'               "+
				"  	WHERE SUBSTRING(NK.ngaynhan, 1, 7) = '" + this.chuoingaythang + "'  AND NK.lenhsanxuat_fk IS NOT NULL  AND NK.TRANGTHAI=1          "+
				"   and nksp.sanpham_fk in ( select pk_seq from ERP_SANPHAM where loaisanpham_fk in ( 100002 ) )  "+
				"  	GROUP BY NKSP.SANPHAM_FK, isnull(nsp.nhamay_fk, 1)  "+
				"  )  "+
				"  NK left join ERP_GIATHANH_DONGIALUONG DG on NK.sanpham_fk = dg.sanpham_fk and dg.thang = '" + this.thang + "' and dg.nam = '" + this.nam + "'   ";

		System.out.println("::: B1: TINH HE SO LUONG " + query );
		if( !db.update(query) )
		{
			return "Bước 1 lỗi: " + query;
		}
		
		return "";
		
	}

	private String B0_TinhGiaNguyenLieu() 
	{
		String query = " delete ERP_GIATHANH_CPNGUYENLIEU where thang = '" + this.thang + "' and nam = '" + this.nam + "'  and congty_fk = '"+this.congtyid+"' ";
		db.update(query);
		
		query = " delete ERP_GIATHANH_CPNGUYENLIEU_LENHSANXUAT where thang = '" + this.thang + "' and nam = '" + this.nam + "' and congty_fk = '"+this.congtyid+"' ";
		db.update(query);
	
		//1 lệnh sản xuất chỉ sản xuất 1 thành phẩm, nếu đổi chương trình SX nhiều thì phải phân bổ cách khác
		query = " insert ERP_GIATHANH_CPNGUYENLIEU( thang, nam, sanpham_fk, tonggiatri,congty_fk )  "+
				" SELECT '" + this.thang + "', '" + this.nam + "', LSX_SP.SANPHAM_FK, SUM( isnull( THSP.SOLUONG * BG.DONGIA, 0 ) ) AS tonggiatriNL,"+this.congtyid+"         "+
				" FROM  ERP_TIEUHAONGUYENLIEU TH   "+
				" 	INNER JOIN ERP_LENHSANXUAT_TIEUHAO_CHITIET THSP ON TH.PK_SEQ=THSP.TIEUHAONGUYENLIEU_FK          "+
				" 	INNER JOIN ERP_LENHSANXUAT_GIAY LSX ON LSX.PK_SEQ = TH.LENHSANXUAT_FK "+
				"	INNER JOIN ERP_LENHSANXUAT_SANPHAM LSX_SP ON LSX.PK_SEQ = LSX_SP.LENHSANXUAT_FK " +
				" 	LEFT JOIN ERP_BANGGIA_THANHPHAM_CUOIKY BG on  THSP.SANPHAM_FK = BG.SANPHAM_FK   and thang = '" + this.thang + "' and nam = '" + this.nam + "'     "+
				" WHERE    SUBSTRING(TH.NGAYTIEUHAO, 1, 7) = '" + this.chuoingaythang + "'   AND TH.TRANGTHAI = 1        "+
				" GROUP BY LSX_SP.SANPHAM_FK ";
		
		System.out.println("::: B0 - 0: TINH CP NGUYEN LIEU " + query );
		if( !db.update(query) )
		{
			return "Bước 1 lỗi: " + query;
		}
		
		query = " insert ERP_GIATHANH_CPNGUYENLIEU_LENHSANXUAT( thang, nam, sanpham_fk, lenhsanxuat_fk, tonggiatri,congty_fk )  "+
				" SELECT '" + this.thang + "', '" + this.nam + "', LSX_SP.SANPHAM_FK, TH.LENHSANXUAT_FK, SUM( isnull( THSP.SOLUONG * BG.DONGIA, 0 ) ) AS tonggiatriNL  ,"+this.congtyid+"          "+
				" FROM  ERP_TIEUHAONGUYENLIEU TH   "+
				" 	INNER JOIN ERP_LENHSANXUAT_TIEUHAO_CHITIET THSP ON TH.PK_SEQ=THSP.TIEUHAONGUYENLIEU_FK          "+
				" 	INNER JOIN ERP_LENHSANXUAT_GIAY LSX ON LSX.PK_SEQ = TH.LENHSANXUAT_FK "+
				"	INNER JOIN ERP_LENHSANXUAT_SANPHAM LSX_SP ON LSX.PK_SEQ = LSX_SP.LENHSANXUAT_FK " +
				" 	LEFT JOIN ERP_BANGGIA_THANHPHAM_CUOIKY BG on  THSP.SANPHAM_FK = BG.SANPHAM_FK   and thang = '" + this.thang + "' and nam = '" + this.nam + "'     "+
				" WHERE    SUBSTRING(TH.NGAYTIEUHAO, 1, 7) = '" + this.chuoingaythang + "'   AND TH.TRANGTHAI = 1        "+
				" GROUP BY LSX_SP.SANPHAM_FK, TH.LENHSANXUAT_FK  ";
		
		System.out.println("::: B0 - 1: TINH CP NGUYEN LIEU " + query );
		if( !db.update(query) )
		{
			return "Bước 1 lỗi: " + query;
		}
		
		return "";
	}

	List<IErp_SanPhamQuyDoi> listquydoi= new ArrayList<IErp_SanPhamQuyDoi>();
	List<IErp_SanPhamQuyDoi> listquydoi_dacogia= new ArrayList<IErp_SanPhamQuyDoi>();

	public ResultSet getRsGiaThanh() {

		return this.rsgiathanh;
	}

	public void setRsGiaThanh(ResultSet rs) {

		this.rsgiathanh=rs;
	}

	public ResultSet getRsBangGiaBinhQuanNL() {

		return this.rsbanggiabinhquannl;
	}

	public void setRsBangGiaBinhQuanNL(ResultSet rs) {

		this.rsbanggiabinhquannl=rs;
	}

	public ResultSet getRsTieuThuNL_LSX() {

		return this.rsTieuThuNL_LSX;
	}

	public void setRsTieuThuNL_LSX(ResultSet rs) {

		this.rsTieuThuNL_LSX=rs;
	}

	public ResultSet getRsHoaChatTieuthuThang() {

		return this.rsHoaChatTieuThuThang;
	}

	public void setRsHoaChatTieuthuThang(ResultSet rs) {

		this.rsHoaChatTieuThuThang=rs;
	}

	public ResultSet getRsPhanBoHoaChatTT_Thang() {

		return this.RsPhanBoHoaChat_Thang;
	}

	public void setPhanBoHoaChatTT_Thang(ResultSet rs) {

		this.RsPhanBoHoaChat_Thang=rs;
	}

	public ResultSet getRsPhanBoChiPhi_Thang() {

		return this.RsPhanBoChiPhi_Thang;
	}

	public void setPhanBoChiPhi_Thang(ResultSet rs) {

		this.RsPhanBoChiPhi_Thang=rs;
	}

	public ResultSet getRsChiPhi() {

		return this.RsChiphi;
	}

	public void setRsChiPhi(ResultSet rs) {

		this.RsChiphi=rs;
	}

	public String getNhaMayId() {

		return this.nhamayId;
	}

	public void setNhaMayId(String nhamayid) {

		this.nhamayId=nhamayid;
	}

	public String getCongtyId() {

		return this.congtyid;
	}

	public void setCongtyId(String _congtyid) {

		this.congtyid=_congtyid;
	}

	public ResultSet getRsNhaMay() {

		return this.RsNhamay;
	}

	public void setRsNhaMay(ResultSet rs) {

		this.RsNhamay=rs;
	}

	public ResultSet getRsTapHopNL_HC() {

		return this.RsTapHopNL_HC;
	}

	public void setRsTapHopNL_HC(ResultSet rs) {

		this.RsTapHopNL_HC=rs;
	}

	public ResultSet getRsGiaThanhBTP_TieuThu() {

		return this.RsGiaThanhBTP_TieuThu;
	}

	public void setRsgetRsGiaThanhBTP_TieuThu(ResultSet rs) {

		this.RsGiaThanhBTP_TieuThu=rs;
	}

	public ResultSet getRsGiaThanh_ThanhPham() {

		return this.RsGiaThanhTP;
	}

	public void setRsgetRsGiaThanh_ThanhPham(ResultSet rs) {

		this.RsGiaThanhTP=rs;
	}

	public ResultSet getRsXuatNhapTon_BTP() {

		return this.RsXuatNhapTon_BTP;
	}

	public void setRsXuatNhapTon_BTP(ResultSet rs) {

		this.RsXuatNhapTon_BTP=rs;
	}

	
	public ResultSet getRsTieuhaoNL_Chinh() {
		
		return this.RsTieuThuNl_Chinh;
	}

	
	public void setRsTieuhaoNl_CHinh(ResultSet rs) {
		
		RsTieuThuNl_Chinh=rs;
	}

	
	public ResultSet getRsTieuhaoNL_Phu() {
		
		return RsTieuThuNl_Phu;
	}

	
	public void setRsTieuhaoNl_Phu(ResultSet rs) {
		
		RsTieuThuNl_Phu=rs;
	}

	
	public ResultSet getRsTinhGiaHoi() {
		
		return RsTinhGia_Hoi_cb1;
	}

	
	public void setRsTinhGiaHoi(ResultSet rs) {
		
		RsTinhGia_Hoi_cb1=rs;
	}

	
	public ResultSet getRsTinhGiaHoi_CB6() {
		
		return RsTinhGia_Hoi_cb6;
	}

	
	public void setRsTinhGiaHoi_CB6(ResultSet rs) {
		
		RsTinhGia_Hoi_cb6=rs;
	}

	
	public List<ResultSet> createView() 
	{
		return null;
	}


	
	public void createView_XemKeHoach() {
		
		List<ResultSet> listRs = new ArrayList<ResultSet>();

		// Worksheet worksheet_1 = worksheets.getSheet(1);
		// lấy ra lệnh sản xuất và định mức trong lệnh sản xuất.
		String chuoingaythang=this.getNam() +"-"+( this.getthang().length()>1? this.getthang():"0"+ this.getthang()); 

		String[]  mang=new String[3];
		mang[0]=this.thang;
		mang[1]=this.nam;
		mang[2]=this.chuoingaythang;

		ResultSet rsGIATHANH_CHITIET=db.getRsByPro("PRO_DINHMUC_SANXUAT", mang);
		listRs.add(rsGIATHANH_CHITIET);
		this.isView = 1;
		this.listRs = listRs;

	}

	public ResultSet getGiathanhTP() {
		
		return this.giathanhTPRs;
	}

	public void setGiathanhTP(ResultSet giathanhTP) {
		
		this.giathanhTPRs = giathanhTP;
	}
	
	public ResultSet getGiathanhLSX() {
		
		return this.giathanhLSXRs;
	}

	public void setGiathanhLSX(ResultSet giathanhLSX) {
		
		this.giathanhLSXRs = giathanhLSX;
	}

	public ResultSet getChitietRs() {
		
		return this.chitietRs;
	}

	public void setChitietRs(ResultSet chitietRs) {
		
		this.chitietRs = chitietRs;
	}
	
	private String LastDayOfMonth(int month, int year) 
    {
        String ngay = "";
        switch (month)
        {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                {
                    ngay = "31";
                    break;
                }
            case 4:
            case 6:
            case 9:
            case 11:
                {
                    ngay = "30";
                    break;
                }
            case 2:
                {
                    if (year % 4 == 0)
                        ngay = "29";
                    else
                        ngay = "28";
                    break;
                }
        }

        return ngay;
    }
	

}