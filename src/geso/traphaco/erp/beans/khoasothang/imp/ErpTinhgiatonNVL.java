package geso.traphaco.erp.beans.khoasothang.imp;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.beans.khoasothang.IErpTinhgiatonNVL;

public class ErpTinhgiatonNVL implements IErpTinhgiatonNVL
{
	String userId;
	String tungay;
	String denngay;
	
	ResultSet giavonRs;
	ResultSet giavonCTRs;
	
	String msg;
	
	public ErpTinhgiatonNVL()
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
			
			String query =  " select b.ma, b.ten, c.DONVI, a.DONGIA, isnull( ( select sum( toncuoi ) from ERP_GIATHANH_GIATONKHO_CHENHLECH where thang = '" + thang + "' and nam = '" + nam + "' and sanpham_fk = a.sanpham_fk ), 0 ) as soluong  "+
							" from ERP_BANGGIA_THANHPHAM_CUOIKY a inner join ERP_SANPHAM b on a.sanpham_fk = b.pk_seq "+
							" 		left join DONVIDOLUONG c on b.dvdl_fk = c.pk_seq "+
							" where sanpham_fk in ( select pk_seq from ERP_SANPHAM where loaisanpham_fk not in ( 100002, 100004, 100005, 100006, 100007, 100019, 100020, 100021 )  ) "+
							" 		and a.congty_fk = '" + this.congtyId + "' and thang = '" + thang + "' and nam = '" + nam + "' "+
							" order by ten asc ";
			
			System.out.println("::: INIT: " + query);
			this.giavonRs = db.get(query);
			
			/*query = " select b.ten as kho, c.ma, c.ten, a.toncuoi, a.G1, a.G2, a.chenhlech  "+
					" from ERP_GIATHANH_GIATONKHO_CHENHLECH a inner join ERP_KHOTT b on a.kho_fk = b.pk_seq "+
					" 		inner join ERP_SANPHAM c on a.sanpham_fk = c.pk_seq "+
					" where a.congty_fk = '" + this.congtyId + "'  and a.thang = '" + thang + "' and a.nam = '" + nam + "'  "+
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
		
		//Check thang ngay da khoa so chua
		String nam = tungay.substring(0, 4);
		String thang = tungay.substring(5, 7);
		
		/*String sql = " select count(pk_seq) as sodong " + 
					 " from ERP_KHOASOTHANG where nam = '" + nam + "' and thangks = '" + Integer.parseInt(thang) + "'";
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
		
		return this.TinhGiaTon_NVL_HH(tungay, denngay);
		
	}

	private String TinhGiaTon_NVL_HH(String tungay, String denngay) 
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
						 " from ERP_KHOASOTHANG where  nam = '" + namKS + "' and thangks = '" + Integer.parseInt(thangKS) + "' and congty_fk  = "+this.congtyId+"";
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
				
			
			this.B1_ApGiaNhap( db, thangKS, namKS, tungay, denngay );
			
			this.B2_TinhGiaTonKho_BQGiaQuyen(db, tungay, denngay);
			
			this.B3_TinhGiaTonKho_ApPhieuXuat(db, tungay, denngay);
			
			this.B4_TinhGiaTonKho_ChayLaiDinhKhoan(db, tungay, denngay);
			
			this.B5_B6_B7_B8_B9_TinhGiaTonKho_GiaTriTonKhoCuoiKy(db, tungay, denngay);
			
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

	private String B1_ApGiaNhap(dbutils db, String thangKS, String namKS, String tungay, String denngay) 
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
					"		and sanpham_fk in ( select pk_seq from ERP_SANPHAM where loaisanpham_fk not in ( 100002, 100004, 100005, 100006, 100007, 100019, 100020, 100021 ) )	"	;
			
			System.out.println(":::B1.Xoa gia nhap kho cu: " + query);
			db.update(query);
			
			//Không tính nhận hàng vào đây, do nếu có nhận hàng, ở công thức bình quân bên dưới đã cộng vào rồi
			query = " insert ERP_GIATHANH_GIATONKHO( congty_fk, thang, nam, sanpham_fk, soluong, dongia, buoc )  "+
					" 	select congty_fk, '" + thang + "' as thang, '" + nam + "' as nam, sanpham_fk, " + 
					" 		( select sum( soluong ) from ERP_TONKHOTHANG_CHITIET where sanpham_fk = a.sanpham_fk and thang = '" + thangKS + "' and nam = '" + namKS + "' ), " + 
					" 		dongia, 1 as buoc  "+
					" 	from ERP_BANGGIA_THANHPHAM_CUOIKY a " + 
					" 	where congty_fk = '" + this.congtyId + "' and thang = '" + thangKS + "' and nam = '" + namKS + "'  "+
					"		and sanpham_fk in ( select pk_seq from ERP_SANPHAM where loaisanpham_fk not in ( 100002, 100004, 100005, 100006, 100007, 100019, 100020, 100021 ) )	" +
					" union all "+
					" 	select a.congty_fk, '" + thang + "' as thang, '" + nam + "' as nam, b.sanpham_fk, b.soluongNHAN, b.dongiaVIET as dongia, 11 as buoc  "+
					" 	from ERP_NHANHANG a inner join ERP_NHANHANG_SANPHAM b on b.nhanhang_fk = a.pk_seq  "+
					" 	where a.congty_fk = '" + this.congtyId + "' and a.trangthai = '1' and a.NGAYNHAN >= '" + tungay + "' and a.NGAYNHAN <= '" + denngay + "'  "+
					" 		and b.sanpham_fk not in ( select sanpham_fk from ERP_BANGGIA_THANHPHAM_CUOIKY where congty_fk = '" + this.congtyId + "' and thang = '" + thangKS + "' and nam = '" + namKS + "'  ) "+
					" 		and a.pk_seq = ( select max(pk_seq) from ERP_NHANHANG nh inner join ERP_NHANHANG_SANPHAM nh_sp on nh_sp.nhanhang_fk = nh.pk_seq  "+
					" 						 where nh.congty_fk = '" + this.congtyId + "' and nh.trangthai = '1' and nh.NGAYNHAN >= '" + tungay + "' and nh.NGAYNHAN <= '" + denngay + "' and nh_sp.sanpham_fk = b.sanpham_fk  ) " + 
					"		and b.sanpham_fk in ( select pk_seq from ERP_SANPHAM where loaisanpham_fk not in ( 100002, 100004, 100005, 100006, 100007, 100019, 100020, 100021 ) )	";
			
			System.out.println(":::Bước 1. Giá NVL: " + query);
			db.update(query);
			
			//Cap nhat dieu chinh ton kho ==> lấy giá tồn tháng trước, hoặc giá nhập mua gân nhất
			query = "update a set a.dongia = isnull( ( select dongia from ERP_GIATHANH_GIATONKHO where congty_fk = '" + this.congtyId + "' and thang = '" + thang + "' and nam = '" + nam + "' and buoc in ( 1, 11 ) and sanpham_fk = a.sanpham_fk ), 0 ) " + 
					"from ERP_DIEUCHINHTONKHOTT_SANPHAM_CHITIET a " +
					"where dctk_fk in (select pk_seq from ERP_DIEUCHINHTONKHOTT where ngaychot >= '" + tungay + "' and ngaychot <= '" + denngay + "' and trangthai = '1' )" + 
					" 		and a.soluongDIEUCHINH > 0 and isnull(a.dongia, 0) = 0 " + 
					"		and sanpham_fk in ( select pk_seq from ERP_SANPHAM where loaisanpham_fk not in ( 100002, 100004, 100005, 100006, 100007, 100019, 100020, 100021 ) )	";
			
			System.out.println(":::Bước 1.1 CAP NHAT DCTK: " + query);
			db.update(query);
			
			//NK06	Nhập trả lại từ sử dụng nội bộ ==> chưa có nghiệp vụ
			
			//Xóa trung gian giá nhập lần đâu, bắt đầu bình quân lại giá
			query = "delete ERP_GIATHANH_GIATONKHO where congty_fk = '" + this.congtyId + "' and thang = '" + thang + "' and nam = '" + nam + "' and buoc in ( 11 )  ";
			db.update(query);
			
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
		
		System.out.println(":::B2_TinhGiaTonKho_BQGiaQuyen");
		db.execProceduce2("TinhGiaTonKho", new String[] { this.congtyId, tungay, denngay, "2" } );
		
		//Phải bổ sung tính giá cho nguyên liệu gia công, bước này chỉ tính được khi các nguyên liệu trong BOM của nó đã có giá
		//db.execProceduce2("TinhGiaTonKho_NVL_GIACONG", new String[] { this.nppId, tungay, denngay, "2" } );
		
		return "";
		
	}
	
	private String B3_TinhGiaTonKho_ApPhieuXuat(dbutils db, String tungay, String denngay) 
	{
		/*3.1 Áp giá vừa tính được (P1) cho tất cả các cặp phiếu nhập - xuất sau: Xuất chuyển kho - Nhập chuyển kho; Xuất ký gửi - Nhập ký gửi; Phiếu xuất chuyển lô - Phiếu nhập đổi lô
		3.2 Áp giá P1 cho  tất cả các loại Phiếu xuất khác và phiếu xuất dùng vật tư - CCDC, trừ Phiếu Xuất Đổi quy cách + Phiếu xuất trả NCC + Phiếu điều chỉnh kho tăng*/
		
		System.out.println(":::B3_TinhGiaTonKho_ApPhieuXuat");
		db.execProceduce2("TinhGiaTonKho", new String[] { this.congtyId, tungay, denngay, "3" } );
		return "";
		
	}
	
	private String B4_TinhGiaTonKho_ChayLaiDinhKhoan(dbutils db, String tungay, String denngay) 
	{
		/*Chạy lại định khoản kế toán cho tất cả các loại phiếu nhập - xuất có cài định khoản.*/
		
		System.out.println(":::B4_TinhGiaTonKho_ChayLaiDinhKhoan");
		db.execProceduce2("TinhGiaTonKho", new String[] { this.congtyId, tungay, denngay, "4" } );
		return "";
		
	}
	
	private String B5_B6_B7_B8_B9_TinhGiaTonKho_GiaTriTonKhoCuoiKy(dbutils db, String tungay, String denngay) 
	{
		/*B5. "Tính Giá trị tồn kho cuối kỳ của từng kho:
		Công thức: Số lượng tồn kho cuối kỳ * P1" */
		
		/*B6. "Tính Giá trị xuất kho trong kỳ của từng kho theo cách 1:
		Công thức: Giá trị xuất trong kỳ = Giá trị tồn đầu + Giá trị nhập (không phân biệt nội dung nhập) - Giá trị tồn cuối" */
		
		/*B7. "Tính Giá trị xuất kho trong kỳ của từng kho theo cách 2:
		Công thức: Giá trị xuất trong kỳ = Tổng giá trị tất cả phiếu xuất kho trong kỳ" */
		
		/*B8. "Tính chênh lệch về Giá trị xuất kho theo từng kho giữa 2 cách tính
		Công thức: Chênh lệch = G1 - G2" */
		
		/*B9. "Cộng tổng các giá trị chênh lệch tại các kho cùng loại, cài Định khoản tự động để ghi nhận vào hệ thống tại ngày cuối tháng:
		11.1 Với kho Cty:
		Nếu G1-G2>0: Nợ <TK 632 bên dưới loại sản phẩm>/Có <TK bên dưới loại sản phẩm>: Giá trị chênh lệch (dấu dương)
		Nếu G1-G2<0: Nợ <TK bên dưới loại SP>/Có <TK 632 bên dưới loại sản phẩm>: Giá trị chênh lệch (dấu dương)
		11.1 Với kho ký gửi:
		Nếu G1-G2>0: Nợ <TK 632 bên dưới loại sản phẩm>/Có 15700000:Giá trị chênh lệch (dấu dương)
		Nếu G1-G2<0: Nợ 15700000/Có <TK 632 bên dưới loại sản phẩm>: Giá trị chênh lệch (dấu dương)" */

		System.out.println(":::B5_B6_B7_B8_B9_TinhGiaTonKho_GiaTriTonKhoCuoiKy");
		try
		{
			int thang = Integer.parseInt( tungay.split("-")[1] );
			int nam = Integer.parseInt( tungay.split("-")[0] );
			
			String query = "";
			
			query = "delete ERP_BANGGIA_THANHPHAM_CUOIKY where congty_fk = '" + this.congtyId + "' and thang = '" + thang + "' and nam = '" + nam + "' " + 
					" 		and sanpham_fk in ( select pk_seq from ERP_SANPHAM where loaisanpham_fk not in ( 100002, 100004, 100005, 100006, 100007, 100019, 100020, 100021 ) ) ";
			
			System.out.println(":::Bước 5. Xóa Giá tồn kho NVL: " + query);
			db.update(query);
			
			query = " insert ERP_BANGGIA_THANHPHAM_CUOIKY( congty_fk, thang, nam, sanpham_fk, dongia )  "+
					" 	select '" + this.congtyId + "', thang, nam, sanpham_fk, dongia  "+
					" 	from ERP_GIATHANH_GIATONKHO " + 
					" 	where congty_fk = '" + this.congtyId + "' and thang = '" + thang + "' and nam = '" + nam + "' and buoc = 2 "+
					" 		and sanpham_fk in ( select pk_seq from ERP_SANPHAM where loaisanpham_fk not in ( 100002, 100004, 100005, 100006, 100007, 100019, 100020, 100021 ) ) " +
					" union all "+
					" 	select '" + this.congtyId + "', thang, nam, sanpham_fk, dongia  "+
					" 	from ERP_GIATHANH_GIATONKHO " + 
					" 	where congty_fk = '" + this.congtyId + "' and thang = '" + thang + "' and nam = '" + nam + "' and buoc = 1 " + 
					" 		and sanpham_fk in ( select pk_seq from ERP_SANPHAM where loaisanpham_fk not in ( 100002, 100004, 100005, 100006, 100007, 100019, 100020, 100021 ) ) " +
					" 		and sanpham_fk not in ( select sanpham_fk from ERP_GIATHANH_GIATONKHO where thang = '" + thang + "' and nam = '" + nam + "' and buoc = 2 ) ";
			
			System.out.println(":::Bước 5. Giá tồn kho NVL: " + query);
			db.update(query);
			
			//Lưu lại chênh lệch để chạy kế toán
			query = "delete ERP_GIATHANH_GIATONKHO_CHENHLECH where congty_fk = '" + this.congtyId + "' and thang = '" + thang + "' and nam = '" + nam + "' ";
			db.update(query);
			
			query = " insert ERP_GIATHANH_GIATONKHO_CHENHLECH( thang, nam, kho_fk, sanpham_fk, toncuoi, G1, G2, chenhlech,congty_fk )  "+
					" select '" + thang + "', '" + nam + "',  a.KHO_FK, a.SANPHAM_FK, dauky + nhap - xuat as toncuoi, "+
					" 			( TONGTIEN_DK + TONGTIEN_NHAP - ( ( dauky + nhap - xuat ) * isnull( b.dongia, 0 ) ) ) as G1, "+
					" 			TONGTIEN_XUAT as G2, "+
					" 			( TONGTIEN_DK + TONGTIEN_NHAP - ( ( dauky + nhap - xuat ) * isnull( b.dongia, 0 ) ) ) - TONGTIEN_XUAT as chenhlech ,'" + this.congtyId + "' "+
					" from dbo.UFN_NXT_HO_FULL_TINHGIAVON_NVL( '" + this.congtyId + "', '" + tungay + "', '" + denngay + "' ) a  "+
					" 		left join ERP_BANGGIA_THANHPHAM_CUOIKY b on a.sanpham_fk = b.SANPHAM_FK and b.THANG = '" + thang + "' and b.NAM = '" + nam + "' and congty_fk = '" + this.congtyId + "' "+
					" where a.kho_fk is not null " + 
					" 	and a.sanpham_fk in ( select pk_seq from ERP_SANPHAM where loaisanpham_fk not in ( 100002, 100004, 100005, 100006, 100007, 100019, 100020, 100021 )  ) ";
			
			System.out.println(":::Bước 5. Tính chênh lệch: " + query);
			db.update(query);
			
			//Tạo bút toán và định khoản tự động
			//this.TaoButToan_TongHop( db, denngay, thang, nam );
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return ex.getMessage();
		}
		
		return "";
		
	}

	private void TaoButToan_TongHop(dbutils db, String ngay, int thang, int nam) 
	{
		String query = "";
		
		query = "delete ERP_PHATSINHKETOAN where loaichungtu = N'Bút toán tổng hợp' and sochungtu in ( select pk_seq from ERP_BUTTOANTONGHOP where NPP_FK = '" + this.nppId + "' and DIENGIAI = N'Bút toán chạy giá vốn tháng " + thang + ", năm " + nam + "' ) ";
		db.update(query);
		
		query = "update ERP_BUTTOANTONGHOP set trangthai = 2 where NPP_FK = '" + this.nppId + "' and DIENGIAI = N'Bút toán chạy giá vốn tháng " + thang + ", năm " + nam + "' ";
		db.update(query);
		
		query = "insert ERP_BUTTOANTONGHOP( NGAYBUTTOAN, DIENGIAI, TRANGTHAI, NPP_FK, CONGTY_FK, TIENTE_FK, TIGIA, NGAYTAO, NGUOITAO, NGAYSUA, NGUOISUA ) " +
				"select '" + denngay + "', N'Bút toán chạy giá vốn tháng " + thang + ", năm " + nam + "', 1, '" + this.nppId + "', '" + this.congtyId + "', 100000, 1, '" + this.getDateTime() + "', '100000', '" + this.getDateTime() + "', '100000' ";
		db.update(query);
		
		String btthId = " ( select pk_seq from ERP_BUTTOANTONGHOP where NPP_FK = '" + this.nppId + "' and DIENGIAI = N'Bút toán chạy giá vốn tháng " + thang + ", năm " + nam + "' and trangthai = '1' )";
		
		query = " insert ERP_BUTTOANTONGHOP_CHITIET( BUTTOANTONGHOP_FK, TAIKHOANKT_FK, NO, CO, SANPHAM_FK, DIENGIAI, stt ) "+
				" 	select " + btthId + ", ( select pk_seq from ERP_TAIKHOANKT where sohieutaikhoan = d.TaikhoanGiavon_sh_fk  ), chenhlech as NO, 0 as CO, a.sanpham_fk, N'Chênh lệch dương kho: ' + b.ten, 1 "+
				" 	from ERP_GIATHANH_GIATONKHO_CHENHLECH a inner join ERP_KHOTT b on a.kho_fk = b.pk_seq "+
				" 			inner join ERP_SANPHAM c on a.sanpham_fk = c.pk_seq "+
				" 			inner join ERP_LOAISANPHAM d on c.loaisanpham_fk = d.pk_seq "+
				" 	where a.thang = '" + thang + "' and a.nam = '" + nam + "' and a.chenhlech > 0  "+
				" union all "+
				" 	select " + btthId + ", ( select pk_seq from ERP_TAIKHOANKT where sohieutaikhoan = '15700000'  ), 0, chenhlech, a.sanpham_fk, N'Chênh lệch dương kho: ' + b.ten, 2 "+
				" 	from ERP_GIATHANH_GIATONKHO_CHENHLECH a inner join ERP_KHOTT b on a.kho_fk = b.pk_seq "+
				" 			inner join ERP_SANPHAM c on a.sanpham_fk = c.pk_seq "+
				" 			inner join ERP_LOAISANPHAM d on c.loaisanpham_fk = d.pk_seq "+
				" 	where a.thang = '" + thang + "' and a.nam = '" + nam + "' and a.chenhlech > 0 "+
				" union all "+
				" 	select " + btthId + ", ( select pk_seq from ERP_TAIKHOANKT where sohieutaikhoan = '15700000'  ), abs(chenhlech) as NO, 0 as CO, a.sanpham_fk, N'Chênh lệch âm kho: ' + b.ten, 3 "+
				" 	from ERP_GIATHANH_GIATONKHO_CHENHLECH a inner join ERP_KHOTT b on a.kho_fk = b.pk_seq "+
				" 			inner join ERP_SANPHAM c on a.sanpham_fk = c.pk_seq "+
				" 			inner join ERP_LOAISANPHAM d on c.loaisanpham_fk = d.pk_seq "+
				" 	where a.thang = '" + thang + "' and a.nam = '" + nam + "' and a.chenhlech < 0 "+
				" union all "+
				" 	select " + btthId + ", ( select pk_seq from ERP_TAIKHOANKT where sohieutaikhoan = d.TaikhoanGiavon_sh_fk  ), 0, abs(chenhlech), a.sanpham_fk, N'Chênh lệch âm kho: ' + b.ten, 4 "+
				" 	from ERP_GIATHANH_GIATONKHO_CHENHLECH a inner join ERP_KHOTT b on a.kho_fk = b.pk_seq "+
				" 			inner join ERP_SANPHAM c on a.sanpham_fk = c.pk_seq "+
				" 			inner join ERP_LOAISANPHAM d on c.loaisanpham_fk = d.pk_seq "+
				" 	where a.thang = '" + thang + "' and a.nam = '" + nam + "' and a.chenhlech < 0 ";
		
		System.out.println("::: BUT TOAN CHI TIET: " + query);
		db.update(query);
		
		
		//DINH KHOAN BUT TOAN
		query = " insert ERP_PHATSINHKETOAN( NGAYCHUNGTU, NGAYGHINHAN, LOAICHUNGTU, SOCHUNGTU, TAIKHOAN_FK, TAIKHOANDOIUNG_FK, NO, CO, DOITUONG, MADOITUONG, TIENTEGOC_FK, TIGIA, TONGGIATRI, DIENGIAI ) "+
				" 	select '" + ngay + "', '" + ngay + "', N'Bút toán tổng hợp', " + btthId + ",  "+
				" 			( select pk_seq from ERP_TAIKHOANKT where sohieutaikhoan = d.TaikhoanGiavon_sh_fk  ) as taikhoan_fk,  "+
				" 			( select pk_seq from ERP_TAIKHOANKT where sohieutaikhoan = '15700000'  ) as doiung,  "+
				" 			chenhlech as NO, 0 as CO, N'Sản phẩm' as DOITUONG, c.pk_seq as madoituong, 100000 as tiente, 1 as tigia, abs(chenhlech) as tongiatri, N'BTTH Chênh lệch dương kho: ' + b.ten "+
				" 	from ERP_GIATHANH_GIATONKHO_CHENHLECH a inner join ERP_KHOTT b on a.kho_fk = b.pk_seq "+
				" 			inner join ERP_SANPHAM c on a.sanpham_fk = c.pk_seq "+
				" 			inner join ERP_LOAISANPHAM d on c.loaisanpham_fk = d.pk_seq "+
				" 	where a.thang = '" + thang + "' and a.nam = '" + nam + "' and a.chenhlech > 0 "+
				" union all "+
				" 	select '" + ngay + "', '" + ngay + "', N'Bút toán tổng hợp', " + btthId + ",  "+
				" 			( select pk_seq from ERP_TAIKHOANKT where sohieutaikhoan = '15700000'  ),  "+
				" 			( select pk_seq from ERP_TAIKHOANKT where sohieutaikhoan = d.TaikhoanGiavon_sh_fk  ),  "+
				" 			0 as NO, chenhlech as CO, N'Sản phẩm' as DOITUONG, c.pk_seq as madoituong, 100000 as tiente, 1 as tigia, abs(chenhlech) as tongiatri, N'BTTH Chênh lệch dương kho: ' + b.ten "+
				" 	from ERP_GIATHANH_GIATONKHO_CHENHLECH a inner join ERP_KHOTT b on a.kho_fk = b.pk_seq "+
				" 			inner join ERP_SANPHAM c on a.sanpham_fk = c.pk_seq "+
				" 			inner join ERP_LOAISANPHAM d on c.loaisanpham_fk = d.pk_seq "+
				" 	where a.thang = '" + thang + "' and a.nam = '" + nam + "' and a.chenhlech > 0 "+
				" union all "+
				" 	select '" + ngay + "', '" + ngay + "', N'Bút toán tổng hợp', " + btthId + ",  "+
				" 			( select pk_seq from ERP_TAIKHOANKT where sohieutaikhoan = '15700000'  ),  "+
				" 			( select pk_seq from ERP_TAIKHOANKT where sohieutaikhoan = d.TaikhoanGiavon_sh_fk  ),  "+
				" 			abs(chenhlech) as NO, 0 as CO, N'Sản phẩm' as DOITUONG, c.pk_seq as madoituong, 100000 as tiente, 1 as tigia, abs(chenhlech) as tongiatri, N'BTTH Chênh lệch âm kho: ' + b.ten "+
				" 	from ERP_GIATHANH_GIATONKHO_CHENHLECH a inner join ERP_KHOTT b on a.kho_fk = b.pk_seq "+
				" 			inner join ERP_SANPHAM c on a.sanpham_fk = c.pk_seq "+
				" 			inner join ERP_LOAISANPHAM d on c.loaisanpham_fk = d.pk_seq "+
				" 	where a.thang = '" + thang + "' and a.nam = '" + nam + "' and a.chenhlech < 0 "+
				" union all "+
				" 	select '" + ngay + "', '" + ngay + "', N'Bút toán tổng hợp', " + btthId + ",  "+
				" 			( select pk_seq from ERP_TAIKHOANKT where sohieutaikhoan = d.TaikhoanGiavon_sh_fk  ),  "+
				" 			( select pk_seq from ERP_TAIKHOANKT where sohieutaikhoan = '15700000'  ), "+
				" 			0 as NO, abs(chenhlech) as CO, N'Sản phẩm' as DOITUONG, c.pk_seq as madoituong, 100000 as tiente, 1 as tigia, abs(chenhlech) as tongiatri, N'BTTH Chênh lệch âm kho: ' + b.ten "+
				" 	from ERP_GIATHANH_GIATONKHO_CHENHLECH a inner join ERP_KHOTT b on a.kho_fk = b.pk_seq "+
				" 			inner join ERP_SANPHAM c on a.sanpham_fk = c.pk_seq "+
				" 			inner join ERP_LOAISANPHAM d on c.loaisanpham_fk = d.pk_seq "+
				" 	where a.thang = '" + thang + "' and a.nam = '" + nam + "' and a.chenhlech < 0 ";
		
		System.out.println("::: BUT TOAN CHI TIET - DINH KHOAN: " + query);
		db.update(query);
		
	}

	
}
