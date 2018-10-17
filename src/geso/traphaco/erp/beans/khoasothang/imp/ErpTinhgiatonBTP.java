package geso.traphaco.erp.beans.khoasothang.imp;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.beans.khoasothang.IErpTinhgiatonBTP;

public class ErpTinhgiatonBTP implements IErpTinhgiatonBTP
{
	String userId;
	String tungay;
	String denngay;
	
	ResultSet giavonRs;
	ResultSet giavonCTRs;
	
	String msg;
	
	public ErpTinhgiatonBTP()
	{
		this.tungay = getDauthang();
		this.denngay = getDateTime();
		this.msg = "";
	}
	
	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}

	public String getTungay()
	{
		return this.tungay;
	}

	public void setTungay(String tungay) 
	{
		this.tungay = tungay;
	}

	public String getDenngay()
	{
		return this.denngay;
	}

	public void setDenngay(String denngay) 
	{
		this.denngay = denngay;
	}

	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}
	
	public void createRs() 
	{
		if( this.tungay.trim().length() > 0 )
		{
			dbutils db = new dbutils();
			
			int nam = Integer.parseInt(tungay.substring(0, 4));
			int thang = Integer.parseInt(tungay.substring(5, 7));
			
			String query =  " select b.ma, b.ten, c.DONVI, a.DONGIA, isnull( ( select sum( toncuoi ) from ERP_GIATHANH_GIATONKHO_CHENHLECH where thang = '" + thang + "' and nam = '" + nam + "'  and sanpham_fk = a.sanpham_fk ), 0 ) as soluong  "+
							" from ERP_BANGGIA_THANHPHAM_CUOIKY a inner join ERP_SANPHAM b on a.sanpham_fk = b.pk_seq "+
							" 		left join DONVIDOLUONG c on b.dvdl_fk = c.pk_seq "+
							" where b.loaisanpham_fk in ( 100019, 100020, 100021, 100022, 100023, 100024 )  "+
							" 		and a.congty_fk = '" + this.congtyId + "' and thang = '" + thang + "' and nam = '" + nam + "' "+
							" order by ten asc ";
			
			System.out.println("::: INIT: " + query);
			this.giavonRs = db.get(query);
			
			/*query = " select b.ten as kho, c.ma, c.ten, a.toncuoi, a.G1, a.G2, a.chenhlech  "+
					" from ERP_GIATHANH_GIATONKHO_CHENHLECH a inner join ERP_KHOTT b on a.kho_fk = b.pk_seq "+
					" 		inner join ERP_SANPHAM c on a.sanpham_fk = c.pk_seq "+
					" where a.congty_fk = '" + this.congtyId + "' and c.loaisanpham_fk in ( 100019, 100020, 100021, 100022, 100023, 100024 ) and a.thang = '" + thang + "' and a.nam = '" + nam + "'  "+
					" order by b.TEN, c.ten ";
			
			System.out.println("::: INIT CT: " + query);
			this.giavonCTRs = db.get(query);*/
			
		}
	}

	public ResultSet getGiavonRs()
	{
		return this.giavonRs;
	}
	
	public ResultSet getGiavonCTRs() 
	{
		return this.giavonCTRs;
	}

	private String getDauthang()
	{
		return this.getDateTime().substring(0, this.getDateTime().length() - 2) + "01";
	}
	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	public void DbClose() 
	{
	}

	String congtyId;
	String nppId;
	
	public String getCongtyId() {
		
		return this.congtyId;
	}

	
	public void setCongtyId(String congtyId) {
		
		this.congtyId = congtyId;
	}

	public String getNppId() {

		return this.nppId;
	}

	public void setNppId(String nppId) {
		
		this.nppId = nppId;
	}

	public String tinhGiaTonkho(String tungay, String denngay)
	{
		if(!tungay.substring(0, 4).equals(denngay.substring(0, 4)))
		{
			this.msg = "Bạn phải chọn thời gian trong cùng 1 tháng của 1 năm";
			return this.msg;
		}
		
		if(!tungay.substring(5, 7).equals(denngay.substring(5, 7)))
		{
			this.msg = "Bạn phải chọn thời gian trong cùng 1 tháng của 1 năm";
			return this.msg;
		}
		
		//Check thang ngay da khoa so chua ==> tạm mở không cần mở khóa sổ tháng
		/*String nam = tungay.substring(0, 4);
		String thang = tungay.substring(5, 7);
		
		String sql = " select count(pk_seq) as sodong " + 
					 " from ERP_KHOASOTHANG where congty_fk = '" + this.congtyId + "' and npp_fk = '" + this.nppId + "' and  nam = '" + nam + "' and thangks = '" + Integer.parseInt(thang) + "'";
		System.out.println("___Cau lenh check: " + sql);
		
		dbutils db = new dbutils();
		ResultSet rs  = db.get(sql);
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
		
		if(count > 0)
		{
			this.msg = "Tháng " + thang + ", năm " + nam + " đã khóa sổ tháng, bạn không thể tính lại giá tồn";
			return this.msg;
		}*/
		
		return this.TinhGiaTon_BTP(tungay, denngay);
		
	}

	private String TinhGiaTon_BTP(String tungay, String denngay) 
	{
		dbutils db = new dbutils();
		try
		{
			String namKS = tungay.substring(0, 4);
			String thangKS = tungay.substring(5, 7);
			
			if(tungay.substring(5, 7).equals("01"))
			{
				thangKS = "12";
				namKS =  Integer.toString((Integer.parseInt(namKS) - 1));
			}
			else
			{
				thangKS = Integer.toString((Integer.parseInt(tungay.substring(5, 7)) - 1));
				if(thangKS.length() < 2)
					thangKS = "0" + thangKS;
				
				namKS = tungay.substring(0, 4);
			}
			
			//Bắt buộc kỳ N - 1 Phái khóa sổ
			String sql = " select count(pk_seq) as sodong " + 
						 " from ERP_KHOASOTHANG where congty_fk = '" + this.congtyId + "'  and  nam = '" + namKS + "' and thangks = '" + Integer.parseInt(thangKS) + "'";
			System.out.println("___Cau lenh check kỳ N - 1: " + sql);
			
			ResultSet rs  = db.get(sql);
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
				return this.msg;
			}
				
			//B0. Tính nguyên liệu
			this.B0_TinhGiaNguyenLieu( db, tungay, denngay );
			
			this.B1_TinhGiaThanh_BTP( db, thangKS, namKS, tungay, denngay );
			
			this.B2_TinhGiaTonKho_BQGiaQuyen(db, tungay, denngay);
			
			this.B3_TinhGiaTonKho_ApPhieuXuat(db, tungay, denngay);
			
			this.B4_TinhGiaTonKho_ChayLaiDinhKhoan(db, tungay, denngay);
			
			this.B5_TinhGiaTonKho_GiaTriTonKhoCuoiKy(db, tungay, denngay);
			
			db.shutDown();
	
			return "";
		}
		catch (Exception e)
		{
			e.printStackTrace();
			db.shutDown();
			this.msg = "16.Khong the tinh gia ton: " + e.getMessage();
			return this.msg;
		}

	}

	private String B0_TinhGiaNguyenLieu(dbutils db, String tungay, String denngay) 
	{
		int thang = Integer.parseInt( tungay.split("-")[1] );
		int nam = Integer.parseInt( tungay.split("-")[0] );
		
		String query = " delete ERP_GIATHANH_CPNGUYENLIEU " + 
					   " where congty_fk = '" + this.congtyId + "' and thang = '" + thang + "' and nam = '" + nam + "' " + 
					   " 	and sanpham_fk in ( select pk_seq from ERP_SANPHAM where loaisanpham_fk in ( 100019, 100020, 100021, 100022, 100023, 100024 ) ) ";
		db.update(query);
		
		query = " delete ERP_GIATHANH_CPNGUYENLIEU_LENHSANXUAT " + 
				" where congty_fk = '" + this.congtyId + "' and thang = '" + thang + "' and nam = '" + nam + "' " + 
				" 	and sanpham_fk in ( select pk_seq from ERP_SANPHAM where loaisanpham_fk in ( 100019, 100020, 100021, 100022, 100023, 100024 ) ) ";
		db.update(query);
	
		//1 lệnh sản xuất chỉ sản xuất 1 thành phẩm, nếu đổi chương trình SX nhiều thì phải phân bổ cách khác
		query = " insert ERP_GIATHANH_CPNGUYENLIEU( congty_fk, thang, nam, sanpham_fk, tonggiatri, soluongSX )  "+
				" SELECT '" + this.congtyId + "', '" + thang + "', '" + nam + "', LSX_SP.SANPHAM_FK, " + 
				" 		SUM( isnull( THSP.SOLUONG * BG.DONGIA, 0 ) ) AS tonggiatriNL,         "+
				" 		(	SELECT sum(NKSP.SOLUONGNHAN)  "+
				" 			FROM ERP_NHANHANG NK            "+
				" 				INNER JOIN ERP_NHANHANG_SANPHAM NKSP ON NK.PK_SEQ = NKSP.NHANHANG_FK     "+
				" 				INNER JOIN ERP_LENHSANXUAT_GIAY LSX ON LSX.PK_SEQ = NK.lenhsanxuat_fk           "+
				" 			WHERE NK.CONGTY_FK = '" + this.congtyId + "' and SUBSTRING(NK.ngaynhan, 1, 7) = SUBSTRING('" + tungay + "', 1, 7)  AND NK.lenhsanxuat_fk IS NOT NULL AND NK.TRANGTHAI = 1           "+
				" 				and NKSP.SANPHAM_FK = LSX_SP.sanpham_fk	 ) as soluongSX         "+
				" FROM  ERP_TIEUHAONGUYENLIEU TH   "+
				" 	INNER JOIN ERP_LENHSANXUAT_TIEUHAO_CHITIET THSP ON TH.PK_SEQ=THSP.TIEUHAONGUYENLIEU_FK          "+
				" 	INNER JOIN ERP_LENHSANXUAT_GIAY LSX ON LSX.PK_SEQ = TH.LENHSANXUAT_FK "+
				"	INNER JOIN ERP_LENHSANXUAT_SANPHAM LSX_SP ON LSX.PK_SEQ = LSX_SP.LENHSANXUAT_FK " +
				" 	LEFT JOIN ERP_BANGGIA_THANHPHAM_CUOIKY BG on  THSP.SANPHAM_FK = BG.SANPHAM_FK   and thang = '" + thang + "' and nam = '" + nam + "'     "+
				" WHERE   SUBSTRING(TH.NGAYTIEUHAO, 1, 7) = SUBSTRING('" + tungay + "', 1, 7)  AND TH.TRANGTHAI = 1        "+
				"	and LSX_SP.SANPHAM_FK in ( select pk_seq from ERP_SANPHAM where loaisanpham_fk in ( 100019, 100020, 100021, 100022, 100023, 100024 ) )	" +
				" GROUP BY LSX_SP.SANPHAM_FK ";
		
		System.out.println("::: B0 - 0: TINH CP NGUYEN LIEU BTP: " + query );
		if( !db.update(query) )
		{
			return "Bước 1 lỗi: " + query;
		}
		
		query = " insert ERP_GIATHANH_CPNGUYENLIEU_LENHSANXUAT( congty_fk, thang, nam, sanpham_fk, lenhsanxuat_fk, tonggiatri, soluong )  "+
				" SELECT '" + this.congtyId + "', '" + thang + "', '" + nam + "', LSX_SP.SANPHAM_FK, TH.LENHSANXUAT_FK, " + 
				" 		SUM( isnull( THSP.SOLUONG * BG.DONGIA, 0 ) ) AS tonggiatriNL,         "+
				"		( " +
				" 			SELECT sum(NKSP.SOLUONGNHAN) "+
				" 			FROM ERP_NHANHANG NK           "+
				" 	 			INNER JOIN ERP_NHANHANG_SANPHAM NKSP ON NK.PK_SEQ = NKSP.NHANHANG_FK    "+
				" 			WHERE NK.CONGTY_FK = '" + this.congtyId + "' and SUBSTRING(NK.ngaynhan, 1, 7) = SUBSTRING('" + this.tungay + "', 1, 7)  AND NK.lenhsanxuat_fk IS NOT NULL AND NK.TRANGTHAI = 1          "+
				"				and NK.lenhsanxuat_fk = TH.LENHSANXUAT_FK	" +
				"		 ) as soluong	" +
				" FROM  ERP_TIEUHAONGUYENLIEU TH   "+
				" 	INNER JOIN ERP_LENHSANXUAT_TIEUHAO_CHITIET THSP ON TH.PK_SEQ=THSP.TIEUHAONGUYENLIEU_FK          "+
				" 	INNER JOIN ERP_LENHSANXUAT_GIAY LSX ON LSX.PK_SEQ = TH.LENHSANXUAT_FK "+
				"	INNER JOIN ERP_LENHSANXUAT_SANPHAM LSX_SP ON LSX.PK_SEQ = LSX_SP.LENHSANXUAT_FK " +
				" 	LEFT JOIN ERP_BANGGIA_THANHPHAM_CUOIKY BG on  THSP.SANPHAM_FK = BG.SANPHAM_FK   and thang = '" + thang + "' and nam = '" + nam + "'     "+
				" WHERE   SUBSTRING(TH.NGAYTIEUHAO, 1, 7) = SUBSTRING('" + denngay + "', 1, 7)  AND TH.TRANGTHAI = 1        "+
				"	and LSX_SP.SANPHAM_FK in ( select pk_seq from ERP_SANPHAM where loaisanpham_fk in ( 100019, 100020, 100021, 100022, 100023, 100024 ) )	" +
				" GROUP BY LSX_SP.SANPHAM_FK, TH.LENHSANXUAT_FK  ";
		
		System.out.println("::: B0 - 1: TINH CP NGUYEN LIEU BTP: " + query );
		if( !db.update(query) )
		{
			return "Bước 1 lỗi: " + query;
		}
		
		return "";
	}
	
	private String B1_TinhGiaThanh_BTP(dbutils db, String thangKS, String namKS, String tungay, String denngay) 
	{
		/*1.1. Áp đơn giá (không VAT) cho từng Mã SP trong  2 loại phiếu nhập sau: Nhập dư từ sử dụng;
		 *  Nhập điều chỉnh tăng kiểm kê ( những phiếu không nhập đơn giá ) .
		Nguyên tắc: Lấy giá  tồn kho kỳ trước của mã Sản phẩm vào, nếu không có giá tồn kỳ trước thì lấy giá nhập mua gần nhất trong kỳ. Nếu vẫn không có giá thì lấy giá =0
		1.2. Fix giá của 2 loại Phiếu này.*/
		
		try
		{
			int thang = Integer.parseInt( tungay.split("-")[1] );
			int nam = Integer.parseInt( tungay.split("-")[0] );
			
			String query = "";
			
			query = "delete ERP_GIATHANH_GIATONKHO where congty_fk = '" + this.congtyId + "' and thang = '" + thang + "' and nam = '" + nam + "' " +
					"	and sanpham_fk in ( select pk_seq from ERP_SANPHAM where loaisanpham_fk in ( 100019, 100020, 100021, 100022, 100023, 100024 ) )	";
			db.update(query);
			
			//Giá của bán thành phẩm nếu trong tháng có LSX thì lấy, không thì lấy giá tồn tháng trước, ko thì lấy giá nhập mua gần nhất
			//Không tính nhập mua gần nhất trong này, vì công thức bình quân đã có
			query = " insert ERP_GIATHANH_GIATONKHO( congty_fk, thang, nam, sanpham_fk, soluong, dongia, buoc )  "+
					" select '" + this.congtyId + "' as congty_fk, '" + thang + "' as thang, ' " + nam + "' as nam, sanpham_fk, sum( soluong ),  "+
					" 		isnull ( ( select max(dongia) from ERP_BANGGIA_THANHPHAM_CUOIKY where congty_fk = '" + this.congtyId + "' and sanpham_fk = khoct.SANPHAM_FK and thang = '" + thangKS + "' and nam = '" + namKS + "' ), 0 ) as dongia, 1 as buoc "+
					" from ERP_TONKHOTHANG_CHITIET khoct   "+
					" where thang = '" + thangKS + "' and nam = '" + namKS + "'   "+
					" 	and khoct.SANPHAM_FK in ( select pk_seq from ERP_SANPHAM where loaisanpham_fk in ( 100019, 100020, 100021, 100022, 100023, 100024 ) ) "+
					"	and sanpham_fk not in ( select sanpham_fk from ERP_GIATHANH_GIATONKHO where congty_fk = '" + this.congtyId + "' and thang = '" + thang + "' and nam = '" + nam + "' )	" +
					" group by khoct.SANPHAM_FK "+
					" having sum( soluong ) > 0 ";
					
			System.out.println(":::Bước 1. Giá BTP: " + query);
			db.update(query);
			
			//Giá thành của BTP chính là giá của chi phí nguyên liệu sản xuất + gia công
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		return "";
		
	}
	
	private String B2_TinhGiaTonKho_BQGiaQuyen(dbutils db, String tungay, String denngay) 
	{
		/*Tính giá tồn kho gia quyền cuối kỳ (lần 1) cho các Mã Sản phẩm trong hệ thống kho Cty (Kho chứa hàng thuộc sở hữu của Cty và bao gồm cả kho hàng Ký gửi tại KH)
		Trong đó:
		+  Giá trị nhập của 1 Mã Sản phẩm bao gồm: 
		* Giá trị nhập mua (không bao gồm VAT)
		* Thuế nhập khẩu đối với hàng nhập khẩu
		* Chi phí nhận hàng phân bổ cho Mã sản phẩm
		* Giá trị nhập kho trên các loại Phiếu: Nhập trả từ KH; Nhập dư từ sử dụng; Nhập điều chỉnh tăng kiểm kê, Nhập từ Đổi quy cách.
		* Giá trị chưa VAT trên Điều chỉnh tăng/giảm Hóa đơn của NCC
		* Giá trị nhập kho của sản phẩm  trong Đổi quy cách.
		
		+ Số lượng nhập của 1 Mã Sản phẩm bao gồm:
		* Số lượng nhập mua
		* Số lượng nhập trên các phiếu: Nhập trả từ KH; Nhập dư từ sử dụng; Nhập điều chỉnh tăng kiểm kê; Nhập từ đổi quy cách
		Note: Công thức tính giá bình quân gia quyền tính trên toàn hệ thống kho như sau: hàng BBLC chỉ tính trong kho hàng BBLC mới
		Giá BQGQ = (Tồng giá trị tồn đầu kỳ + Tổng giá trị nhập + Tổng giá trị điều chỉnh tăng)/(Tổng số lượng tồn kho đầu kỳ + Tổng số lượng nhập làm tăng Hệ thống kho trong kỳ)
		Chú ý Khi tính toán thì loại trừ Giá trị (nếu có) & Số lượng trên các phiếu điều chuyển giữa các kho trong nội bộ hệ thống kho công ty.*/
		
		db.execProceduce2("TinhGiaTonKho_BTP", new String[] { this.congtyId, tungay, denngay, "2" } );
		return "";
		
	}
	
	private String B3_TinhGiaTonKho_ApPhieuXuat(dbutils db, String tungay, String denngay) 
	{
		/*3.1 Áp giá vừa tính được (P1) cho tất cả các cặp phiếu nhập - xuất sau: Xuất chuyển kho - Nhập chuyển kho; Xuất ký gửi - Nhập ký gửi; Phiếu xuất chuyển lô - Phiếu nhập đổi lô
		3.2 Áp giá P1 cho  tất cả các loại Phiếu xuất khác và phiếu xuất dùng vật tư - CCDC, trừ Phiếu Xuất Đổi quy cách + Phiếu xuất trả NCC + Phiếu điều chỉnh kho tăng*/
		
		db.execProceduce2("TinhGiaTonKho_BTP", new String[] { this.congtyId, tungay, denngay, "3" } );
		return "";
		
	}
	
	private String B4_TinhGiaTonKho_ChayLaiDinhKhoan(dbutils db, String tungay, String denngay) 
	{
		/*Chạy lại định khoản kế toán cho tất cả các loại phiếu nhập - xuất có cài định khoản.*/
		
		db.execProceduce2("TinhGiaTonKho_BTP", new String[] { this.congtyId, tungay, denngay, "4" } );
		return "";
		
	}
	
	private String B5_TinhGiaTonKho_GiaTriTonKhoCuoiKy(dbutils db, String tungay, String denngay) 
	{
		/*B5. "Tính Giá trị tồn kho cuối kỳ của từng kho:
		Công thức: Số lượng tồn kho cuối kỳ * P1" */
		
		try
		{
			int thang = Integer.parseInt( tungay.split("-")[1] );
			int nam = Integer.parseInt( tungay.split("-")[0] );
			
			String query = "";
			
			query = "delete ERP_BANGGIA_THANHPHAM_CUOIKY where congty_fk = '" + this.congtyId + "' and thang = '" + thang + "' and nam = '" + nam + "' " + 
					" 		and sanpham_fk in ( select pk_seq from ERP_SANPHAM where loaisanpham_fk in ( 100019, 100020, 100021, 100022, 100023, 100024  ) ) ";
			db.update(query);
			
			query = " insert ERP_BANGGIA_THANHPHAM_CUOIKY( congty_fk, thang, nam, sanpham_fk, dongia )  "+
					" 	select '" + this.congtyId + "', thang, nam, sanpham_fk, dongia  "+
					" 	from ERP_GIATHANH_GIATONKHO " + 
					" 	where congty_fk = '" + this.congtyId + "' and thang = '" + thang + "' and nam = '" + nam + "' and buoc = 2 "+
					" 		and sanpham_fk in ( select pk_seq from ERP_SANPHAM where loaisanpham_fk in ( 100019, 100020, 100021, 100022, 100023, 100024 ) ) " +
					" union all "+
					" 	select '" + this.congtyId + "', thang, nam, sanpham_fk, dongia  "+
					" 	from ERP_GIATHANH_GIATONKHO " + 
					" 	where congty_fk = '" + this.congtyId + "' and thang = '" + thang + "' and nam = '" + nam + "' and buoc = 1 " + 
					" 		and sanpham_fk in ( select pk_seq from ERP_SANPHAM where loaisanpham_fk in ( 100019, 100020, 100021, 100022, 100023, 100024 ) ) " +
					" 		and sanpham_fk not in ( select sanpham_fk from ERP_GIATHANH_GIATONKHO where thang = '" + thang + "' and nam = '" + nam + "' and buoc = 2 ) ";
			
			System.out.println(":::Bước 5. Giá tồn kho BTP: " + query);
			db.update(query);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return ex.getMessage();
		}
		
		return "";
		
	}
	
	
}
