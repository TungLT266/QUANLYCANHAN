package geso.traphaco.erp.beans.khoasothang.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.beans.khoasothang.IErpTinhgianhap;

public class ErpTinhgianhap implements IErpTinhgianhap 
{
	String userId;
	String tungay;
	String denngay;
	
	ResultSet giavonRs;
	ResultSet giavonCTRs;
	
	String msg;
	
	public ErpTinhgianhap()
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
			
			String query =  " select b.ma, b.ten, c.DONVI, a.DONGIA, isnull( ( select sum( toncuoi ) from ERP_GIATHANH_GIATONKHO_CHENHLECH where thang = '" + thang + "' and nam = '" + nam + "' and sanpham_fk = '100294' ), 0 ) as soluong  "+
							" from ERP_BANGGIA_THANHPHAM_CUOIKY a inner join ERP_SANPHAM b on a.sanpham_fk = b.pk_seq "+
							" 		left join DONVIDOLUONG c on b.dvdl_fk = c.pk_seq "+
							" where sanpham_fk in ( select pk_seq from ERP_SANPHAM where loaisanpham_fk not in ( 100002, 100004, 100005, 100006, 100007 )  ) "+
							" 		and thang = '" + thang + "' and nam = '" + nam + "' "+
							" order by ten asc ";
			
			System.out.println("::: INIT: " + query);
			this.giavonRs = db.get(query);
			
			query = " select b.ten as kho, c.ma, c.ten, a.toncuoi, a.G1, a.G2, a.chenhlech  "+
					" from ERP_GIATHANH_GIATONKHO_CHENHLECH a inner join ERP_KHOTT b on a.kho_fk = b.pk_seq "+
					" 		inner join ERP_SANPHAM c on a.sanpham_fk = c.pk_seq "+
					" where a.thang = '" + thang + "' and a.nam = '" + nam + "'  "+
					" order by b.TEN, c.ten ";
			
			System.out.println("::: INIT CT: " + query);
			this.giavonCTRs = db.get(query);
			
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

	public String updateGiaNhap(String tungay, String denngay) 
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
		
		String sql = "select count(pk_seq) as sodong from erp_khoasothang where nam = '" + nam + "' and thangks = '" + Integer.parseInt(thang) + "'";
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
				db.shutDown();
			} 
			catch (Exception e)
			{
				db.shutDown();
			}
		}
		
		if(count > 0)
		{
			this.msg = "Tháng " + thang + ", năm " + nam + " đã khóa sổ tháng, bạn không thể cập nhật giá nhập";
			return this.msg;
		}
		
		return this.BinhQuanCuoiKy(tungay, denngay);
	}
	
	public String updateGiaNhap2(String tungay, String denngay) 
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

		return this.BinhQuanCuoiKy(tungay, denngay);
	}
	
	private String BinhQuanCuoiKy(String tungay, String denngay)
	{
		dbutils db = new dbutils();
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String thang = "";
			String nam = "";
			
			if(tungay.substring(5, 7).equals("01"))
			{
				thang = "12";
				nam =  Integer.toString((Integer.parseInt(nam) - 1));
			}
			else
			{
				thang = Integer.toString((Integer.parseInt(tungay.substring(5, 7)) - 1));
				if(thang.length() < 2)
					thang = "0" + thang;
				
				nam = tungay.substring(0, 4);
			}
				
			String query = 	"select nhapxuat.sanpham_fk, sum(nhapxuat.soluong) as tongluongnhap, sum(nhapxuat.thanhtien) as tongthanhtien  " +
							"from " +
							"(	" +
								"select sanpham_fk, sum(isnull(soluong, '0')) as soluong, sum(isnull(thanhtienton, '0')) as thanhtien from erp_tonkhothang " +
								"where thang = '" + thang + "' and nam = '" + nam + "' group by sanpham_fk " +
							"union all " +
								"select sanpham_fk, sum(soluongNhap) as soluong, sum(thanhtienViet) as thanhtien " +
								"from erp_nhapkho a inner join erp_nhapkho_sanpham b on b.sonhapkho_fk = a.pk_seq " +
								"where a.trangthai = '1' and a.ngaychot >= '" + tungay + "' and a.ngaychot <= '" + denngay + "' " +
								"group by sanpham_fk " +
							") nhapxuat " +
							"where nhapxuat.sanpham_fk in ( select pk_seq from ERP_SanPham where nguongoc != 1 ) " +
							"group by sanpham_fk";
			
			System.out.println("1.Cau lenh khoi tao: " + query);
			ResultSet rs = db.get(query);
			
			if(rs != null)
			{
				while(rs.next())
				{
					String sanpham_fk = rs.getString("sanpham_fk");
					double tongnhap = rs.getDouble("tongluongnhap");
					double tongtien = rs.getDouble("tongthanhtien");
					
					double dongia = 0;
					if(tongnhap > 0)
						dongia = tongtien / tongnhap;
					
					//Cap nhat gia xuat trong ky ( cho nay sua lai, phai duyet tung phieu xuat, tinh lai but toan ke toan  )
					/*query = " update erp_xuatkho_sanpham set dongia = " + dongia + ", thanhtien = soluong * " + dongia +
							" where sanpham_fk = '" + sanpham_fk + "' " +
									"and xuatkho_fk in (select pk_seq from erp_xuatkho where ngaychot >= '" + tungay + "' " +
									"and ngaychot <= '" + denngay + "' and trangthai = '1' )";
					
					if(!db.update(query))
					{
						this.msg = "4.Loi: " + query;
						db.getConnection().rollback();
						return this.msg;
					}*/
					
					
					query = "select a.PK_SEQ as xkId, a.NOIDUNGXUAT, a.NOIDUNGXUAT, c.LOAISANPHAM_FK, b.SOLUONG, b.DONGIA, b.THANHTIEN " +
							"from ERP_XUATKHO a inner join ERP_XUATKHO_SANPHAM b on a.PK_SEQ = b.XUATKHO_FK  " +
								"inner join erp_SANPHAM c on b.SANPHAM_FK = c.PK_SEQ  " +
							"where a.NGAYCHOT >= '" + tungay + "' and a.NGAYCHOT <= '" + denngay + "' and a.TRANGTHAI = '1' and b.SANPHAM_FK = '" + sanpham_fk + "'";
					
					ResultSet rsPxk = db.get(query);
					if(rsPxk != null)
					{
						while(rsPxk.next())
						{
							String pxk_fk = rsPxk.getString("xkId");
							String noidungXuat = rsPxk.getString("NOIDUNGXUAT");
							String loaisanPham = rsPxk.getString("LOAISANPHAM_FK");
							
							int soluong = rsPxk.getInt("SOLUONG");
							double dongiaXuat = rsPxk.getDouble("DONGIA");

							String taikhoanktCo = "";
							String taikhoanktNo = "";
							
							query = "select TAIKHOANKTCO, TAIKHOANKTNO " +
									"from ERP_CAUHINHDINHKHOANKETOAN  " +
									"where NOIDUNGNHAP_FK = '" + noidungXuat + "' and LOAISANPHAM_FK = '" + loaisanPham + "' ";
							
							System.out.println("5.Query lay tai khoan: " + query);
							
							ResultSet tkRs = db.get(query);
							if(tkRs != null)
							{
								if(tkRs.next())
								{
									taikhoanktCo = tkRs.getString("TAIKHOANKTCO");
									taikhoanktNo = tkRs.getString("TAIKHOANKTNO");
									tkRs.close();
								}
							}
							
							if(taikhoanktCo.trim().length() <= 0 || taikhoanktNo.trim().length() <= 0)
							{
								this.msg = "Không thể lấy được tài khoản kế toán Nợ - Có. Vui lòng kiểm tra lại dữ liệu.";
								rs.close();
								rsPxk.close();
								db.getConnection().rollback();
								return this.msg;
							}
							
							//Thao tac nguoc khi lam Xuatkho
							
							String tiente_fk = "100000";
							query = "update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND - " + dongiaXuat + "*" + soluong + ", " +
																" GIATRINGUYENTE = GIATRINGUYENTE - "  + dongiaXuat + "*" + soluong + 
									" where taikhoankt_fk = '" + taikhoanktNo + "' and nguyente_fk = '" + tiente_fk + "' and thang = '" + thang + "' and nam = '" + nam + "'";
							
							if(!db.update(query))
							{
								rs.close();
								rsPxk.close();
								db.getConnection().rollback();
								
								System.out.println("1.Loi: " + query);
								return "8.Không thể cập nhật ERP_TAIKHOAN_NOCO: " + query;
							}
							
							
							query = "update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND - " + dongiaXuat + "*" + soluong + ", " +
															" GIATRINGUYENTE = GIATRINGUYENTE - "  + dongiaXuat + "*" + soluong + 
									" where taikhoankt_fk = '" + taikhoanktCo + "' and nguyente_fk = '" + tiente_fk + "' and thang = '" + thang + "' and nam = '" + nam + "'";
							
							if(!db.update(query))
							{
								rs.close();
								rsPxk.close();
								db.getConnection().rollback();
								
								System.out.println("1.Loi: " + query);
								return "9.Không thể cập nhật ERP_TAIKHOAN_NOCO: " + query;
							}
							
							
							//Cap nhat lai bang gia binh quan cuoi ky
							query = "update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND + " + dongia + "*" + soluong + ", " +
																" GIATRINGUYENTE = GIATRINGUYENTE + "  + dongia + "*" + soluong + 
									" where taikhoankt_fk = '" + taikhoanktNo + "' and nguyente_fk = '" + tiente_fk + "' and thang = '" + thang + "' and nam = '" + nam + "'";
									
							if(!db.update(query))
							{
								rs.close();
								rsPxk.close();
								db.getConnection().rollback();
								
								System.out.println("10.Loi: " + query);
								return "10.Không thể cập nhật ERP_TAIKHOAN_NOCO: " + query;
							}
									
									
							query = "update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND + " + dongia + "*" + soluong + ", " +
													" GIATRINGUYENTE = GIATRINGUYENTE + "  + dongia + "*" + soluong + 
									" where taikhoankt_fk = '" + taikhoanktCo + "' and nguyente_fk = '" + tiente_fk + "' and thang = '" + thang + "' and nam = '" + nam + "'";
							
							if(!db.update(query))
							{
								rs.close();
								rsPxk.close();
								db.getConnection().rollback();
								
								System.out.println("11.Loi: " + query);
								return "11.Không thể cập nhật ERP_TAIKHOAN_NOCO: " + query;
							}
							
							
							query = "update ERP_XUATKHO_SANPHAM set DONGIA = '" + dongia + "', THANHTIEN = SOLUONG * '" + dongia + "' " +
									"where XUATKHO_FK = '" + pxk_fk + "' and SANPHAM_FK = '" + sanpham_fk + "' ";
							
							if(!db.update(query))
							{
								this.msg = "12.Khong the cap nhat ERP_XUATKHO_SANPHAM: " + query;
								System.out.println(msg);
								rs.close();
								rsPxk.close();
								db.getConnection().rollback();
								return this.msg;
							}
							
						}
						rsPxk.close();
					}
					
					//Cap nhat gia ton trong ky
					query = "update erp_khott_sanpham set giaton = " + dongia + ", thanhtien = soluong * " + dongia + " " +
							"where sanpham_fk = '" + sanpham_fk + "'";
					
					if(!db.update(query))
					{
						this.msg = "13.Loi: " + query;
						rs.close();
						db.getConnection().rollback();
						return this.msg;
					}
					
					//Cap nhat chuyen kho
					query = "update erp_chuyenkho_sanpham set dongia = " + dongia + ", thanhtienXuat = soluongXuat * " + dongia + ", thanhtienNhan = soluongNhan * " + dongia + 
							" where sanpham_fk = '" + sanpham_fk + "' and chuyenkho_fk in (select pk_seq from erp_chuyenkho where ngaychot >= '" + tungay + "' and ngaychot <= '" + denngay + "' and trangthai = '1' )";
					
					if(!db.update(query))
					{
						this.msg = "14.Loi: " + query;
						rs.close();
						db.getConnection().rollback();
						return this.msg;
					}
					
					//Cap nhat dieu chinh ton kho
					query = "update ERP_DIEUCHINHTONKHOTT_SANPHAM set dongia = " + dongia + ", thanhtien = soluong * " + dongia + " " +
							"where sanpham_fk = '" + sanpham_fk + "' and dieuchinhtonkho_fk in (select pk_seq from ERP_DIEUCHINHTONKHOTT where ngaychot >= '" + tungay + "' and ngaychot <= '" + denngay + "' and trangthai = '1' )";
					
					if(!db.update(query))
					{
						this.msg = "15.Loi: " + query;
						rs.close();
						db.getConnection().rollback();
						return this.msg;
					}
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
	
			return "";
		}
		catch (Exception e)
		{
			try 
			{
				db.getConnection().rollback();
				db.shutDown();
			} 
			catch (SQLException e1) {}
			
			this.msg = "16.Khong the tinh binh quan: " + e.getMessage();
			return this.msg;
		}
		
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
		
		String sql = "select count(pk_seq) as sodong from ERP_KHOASOTHANG where nam = '" + nam + "' and thangks = '" + Integer.parseInt(thang) + "'";
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
				db.shutDown();
			} 
			catch (Exception e)
			{
				db.shutDown();
			}
		}
		
		if(count > 0)
		{
			this.msg = "Tháng " + thang + ", năm " + nam + " đã khóa sổ tháng, bạn không thể tính lại giá tồn";
			return this.msg;
		}
		
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
				
			this.B1_ApGiaNhap( db, thangKS, namKS, tungay, denngay );
			
			this.B2_TinhGiaTonKho_BQGiaQuyen(db, tungay, denngay);
			
			this.B3_TinhGiaTonKho_ApPhieuXuat(db, tungay, denngay);
			
			this.B4_TinhGiaTonKho_ChayLaiDinhKhoan(db, tungay, denngay);
			
			this.B5_B6_B7_B8_B9_TinhGiaTonKho_GiaTriTonKhoCuoiKy(db, thangKS, namKS, tungay, denngay);
			
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
		/*1.1. Áp đơn giá (không VAT) cho từng Mã SP trong  2 loại phiếu nhập sau: Nhập dư từ sử dụng; Nhập điều chỉnh tăng kiểm kê .
		Nguyên tắc: Lấy giá  tồn kho kỳ trước của mã Sản phẩm vào, nếu không có giá tồn kỳ trước thì lấy giá nhập mua gần nhất trong kỳ. Nếu vẫn không có giá thì lấy giá =0
		1.2. Fix giá của 2 loại Phiếu này.*/
		
		try
		{
			int thang = Integer.parseInt( tungay.split("-")[1] );
			int nam = Integer.parseInt( tungay.split("-")[0] );
			
			String query = "";
			
			query = "delete ERP_GIATHANH_GIATONKHO where thang = '" + thangKS + "' and nam = '" + namKS + "' ";
			db.update(query);
			
			query = " insert ERP_GIATHANH_GIATONKHO( thang, nam, sanpham_fk, dongia, buoc )  "+
					" 	select '" + thang + "' as thang, '" + nam + "' as nam, sanpham_fk, dongia, 1 as buoc  "+
					" 	from ERP_BANGGIA_THANHPHAM_CUOIKY " + 
					" 	where thang = '" + thangKS + "' and nam = '" + namKS + "'  "+
					"		and sanpham_fk in ( select pk_seq from ERP_SANPHAM where loaisanpham_fk not in ( 100002, 100004, 100005, 100006, 100007 ) )	" +
					" union all "+
					" 	select '" + thang + "' as thang, '" + nam + "' as nam, b.sanpham_fk, b.dongia as dongia, 1 as buoc  "+
					" 	from ERP_NHANHANG a inner join ERP_NHANHANG_SANPHAM b on b.nhanhang_fk = a.pk_seq  "+
					" 	where a.trangthai = '1' and a.NGAYNHAN >= '" + tungay + "' and a.NGAYNHAN <= '" + denngay + "'  "+
					" 		and b.sanpham_fk not in ( select sanpham_fk from ERP_BANGGIA_THANHPHAM_CUOIKY where thang = '" + thangKS + "' and nam = '" + namKS + "'  ) "+
					" 		and a.pk_seq = ( select max(pk_seq) from ERP_NHANHANG nh inner join ERP_NHANHANG_SANPHAM nh_sp on nh_sp.nhanhang_fk = nh.pk_seq  "+
					" 						 where nh.trangthai = '1' and nh.NGAYNHAN >= '" + tungay + "' and nh.NGAYNHAN <= '" + denngay + "' and nh_sp.sanpham_fk = b.sanpham_fk  ) " + 
					"		and b.sanpham_fk in ( select pk_seq from ERP_SANPHAM where loaisanpham_fk not in ( 100002, 100004, 100005, 100006, 100007 ) )	";
			
			System.out.println(":::Bước 1. Giá NVL: " + query);
			db.update(query);
			
			//Cap nhat dieu chinh ton kho
			query = "update a set a.dongia = isnull( ( select dongia from ERP_GIATHANH_GIATONKHO where thang = '" + thang + "' and nam = '" + nam + "' and buoc = 0 and sanpham_fk = a.sanpham_fk ), 0 ) " + 
					"from ERP_DIEUCHINHTONKHOTT_SANPHAM_CHITIET a " +
					"where dctk_fk in (select pk_seq from ERP_DIEUCHINHTONKHOTT where ngaychot >= '" + tungay + "' and ngaychot <= '" + denngay + "' and trangthai = '1' )" + 
					" 		and a.soluongDIEUCHINH > 0	and sanpham_fk in ( select pk_seq from ERP_SANPHAM where loaisanpham_fk not in ( 100002, 100004, 100005, 100006, 100007 ) )	";
			db.update(query);
			
			//NK06	Nhập trả lại từ sử dụng nội bộ ==> chưa có nghiệp vụ
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		return "";
		
	}
	
	private String B2_TinhGiaTonKho_BQGiaQuyen(dbutils db, String tungay, String denngay) 
	{
		/*Tính giá tồn kho gia quyền cuối kỳ (lần 1) cho các Mã sản phẩm trong hệ thống kho Cty (Kho chứa hàng thuộc sở hữu của Cty và bao gồm cả kho hàng Ký gửi tại KH)
		Trong đó:
		+  Giá trị nhập của 1 Mã sản phẩm bao gồm: 
		* Tổng giá thành SX
		* Giá trị nhập kho của các loại Phiếu: Nhập trả từ KH; Nhập dư từ sử dụng; Nhập điều chỉnh tăng kiểm kê; Nhập từ đổi quy cách
		* Chi phí nhận hàng phân bổ cho Mã sản phẩm
		
		+ Số lượng nhập của 1 Mã sản phẩm bao gồm:
		* Số lượng nhập từ SX
		* Số lượng nhập trên các phiếu: Nhập trả từ KH; Nhập dư từ sử dụng; Nhập điều chỉnh tăng kiểm kê; Nhập từ đổi quy cách*/
		
		db.execProceduce2("TinhGiaTonKho", new String[] { tungay, denngay, "2" } );
		return "";
		
	}
	
	private String B3_TinhGiaTonKho_ApPhieuXuat(dbutils db, String tungay, String denngay) 
	{
		/*3.1 Áp giá vừa tính được (P1) cho tất cả các cặp phiếu nhập - xuất sau: Xuất chuyển kho - Nhập chuyển kho; Xuất ký gửi - Nhập ký gửi; Phiếu xuất chuyển lô - Phiếu nhập đổi lô
		3.2 Áp giá P1 cho  tất cả các loại Phiếu xuất khác, trừ Phiếu Xuất Đổi quy cách + Phiếu xuất trả NCC*/
		
		db.execProceduce2("TinhGiaTonKho", new String[] { tungay, denngay, "3" } );
		return "";
		
	}
	
	private String B4_TinhGiaTonKho_ChayLaiDinhKhoan(dbutils db, String tungay, String denngay) 
	{
		/*Chạy lại định khoản kế toán cho tất cả các loại phiếu nhập - xuất có cài định khoản.*/
		
		return "";
		
	}
	
	private String B5_B6_B7_B8_B9_TinhGiaTonKho_GiaTriTonKhoCuoiKy(dbutils db, String thangKS, String namKS, String tungay, String denngay) 
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

		try
		{
			int thang = Integer.parseInt( tungay.split("-")[1] );
			int nam = Integer.parseInt( tungay.split("-")[0] );
			
			String query = "";
			
			query = "delete ERP_BANGGIA_THANHPHAM_CUOIKY where thang = '" + thang + "' and nam = '" + nam + "' " + 
					" 		and sanpham_fk in ( select pk_seq from ERP_SANPHAM where loaisanpham_fk not in ( 100002, 100004, 100005, 100006, 100007 ) ) ";
			db.update(query);
			
			query = " insert ERP_BANGGIA_THANHPHAM_CUOIKY( congty_fk, thang, nam, sanpham_fk, dongia )  "+
					" 	select 100000, thang, nam, sanpham_fk, dongia  "+
					" 	from ERP_GIATHANH_GIATONKHO " + 
					" 	where thang = '" + thang + "' and nam = '" + nam + "' and buoc = 2 "+
					" union all "+
					" 	select 100000, thang, nam, sanpham_fk, dongia  "+
					" 	from ERP_GIATHANH_GIATONKHO " + 
					" 	where thang = '" + thang + "' and nam = '" + nam + "' and buoc = 1 " + 
					" 		and sanpham_fk not in ( select sanpham_fk from ERP_GIATHANH_GIATONKHO where thang = '" + thang + "' and nam = '" + nam + "' and buoc = 2 ) ";
			
			System.out.println(":::Bước 5. Giá tồn kho NVL: " + query);
			db.update(query);
			
			//Lưu lại chênh lệch để chạy kế toán
			query = "delete ERP_GIATHANH_GIATONKHO_CHENHLECH where thang = '" + thangKS + "' and nam = '" + namKS + "' ";
			db.update(query);
			
			query = " insert ERP_GIATHANH_GIATONKHO_CHENHLECH( thang, nam, kho_fk, sanpham_fk, toncuoi, G1, G2, chenhlech )  "+
					" select '" + thang + "', '" + nam + "',  a.KHO_FK, a.SANPHAM_FK, dauky + nhap - xuat as toncuoi, "+
					" 			( TONGTIEN_DK + TONGTIEN_NHAP - ( ( dauky + nhap - xuat ) * isnull( b.dongia, 0 ) ) ) as G1, "+
					" 			TONGTIEN_XUAT as G2, "+
					" 			( TONGTIEN_DK + TONGTIEN_NHAP - ( ( dauky + nhap - xuat ) * isnull( b.dongia, 0 ) ) ) - TONGTIEN_XUAT as chenhlech "+
					" from dbo.UFN_NXT_HO_FULL_TINHGIAVON_NVL( '" + tungay + "', '" + denngay + "' ) a  "+
					" 		left join ERP_BANGGIA_THANHPHAM_CUOIKY b on a.sanpham_fk = b.SANPHAM_FK and b.THANG = 1 and b.NAM = 2017 "+
					" where a.kho_fk is not null and a.sanpham_fk in ( select pk_seq from ERP_SANPHAM where loaisanpham_fk not in ( 100002, 100004, 100005, 100006, 100007 )  ) ";
			
			System.out.println(":::Bước 5. Tính chênh lệch: " + query);
			db.update(query);
			
			//Tạo bút toán và định khoản tự động
			this.TaoButToan_TongHop( db, denngay, thang, nam );
			
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
		
		query = "delete ERP_PHATSINHKETOAN where loaichungtu = N'Bút toán tổng hợp' and sochungtu in ( select pk_seq from ERP_BUTTOANTONGHOP where DIENGIAI = N'Bút toán chạy giá vốn tháng " + thang + ", năm " + nam + "' ) ";
		db.update(query);
		
		query = "update ERP_BUTTOANTONGHOP set trangthai = 2 where DIENGIAI = N'Bút toán chạy giá vốn tháng " + thang + ", năm " + nam + "' ";
		db.update(query);
		
		query = "insert ERP_BUTTOANTONGHOP( NGAYBUTTOAN, DIENGIAI, TRANGTHAI, CONGTY_FK, TIENTE_FK, TIGIA, NGAYTAO, NGUOITAO, NGAYSUA, NGUOISUA ) " +
				"select '" + denngay + "', N'Bút toán chạy giá vốn tháng " + thang + ", năm " + nam + "', 1, 100000, 100000, 1, '" + this.getDateTime() + "', '100000', '" + this.getDateTime() + "', '100000' ";
		db.update(query);
		
		String btthId = " ( select pk_seq from ERP_BUTTOANTONGHOP where DIENGIAI = N'Bút toán chạy giá vốn tháng " + thang + ", năm " + nam + "' and trangthai = '1' )";
		
		query = " insert ERP_BUTTOANTONGHOP_CHITIET( BUTTOANTONGHOP_FK, TAIKHOANKT_FK, NO, CO, SANPHAM_FK, DIENGIAI, stt ) "+
				" 	select " + btthId + ", ( select pk_seq from ERP_TAIKHOANKT where sohieutaikhoan = d.TaikhoanGiavon_sh_fk and npp_fk = '1' ), chenhlech as NO, 0 as CO, a.sanpham_fk, N'Chênh lệch dương kho: ' + b.ten, 1 "+
				" 	from ERP_GIATHANH_GIATONKHO_CHENHLECH a inner join ERP_KHOTT b on a.kho_fk = b.pk_seq "+
				" 			inner join ERP_SANPHAM c on a.sanpham_fk = c.pk_seq "+
				" 			inner join ERP_LOAISANPHAM d on c.loaisanpham_fk = d.pk_seq "+
				" 	where a.thang = '" + thang + "' and a.nam = '" + nam + "' and a.chenhlech > 0  "+
				" union all "+
				" 	select " + btthId + ", ( select pk_seq from ERP_TAIKHOANKT where sohieutaikhoan = '15700000' and npp_fk = '1' ), 0, chenhlech, a.sanpham_fk, N'Chênh lệch dương kho: ' + b.ten, 2 "+
				" 	from ERP_GIATHANH_GIATONKHO_CHENHLECH a inner join ERP_KHOTT b on a.kho_fk = b.pk_seq "+
				" 			inner join ERP_SANPHAM c on a.sanpham_fk = c.pk_seq "+
				" 			inner join ERP_LOAISANPHAM d on c.loaisanpham_fk = d.pk_seq "+
				" 	where a.thang = '" + thang + "' and a.nam = '" + nam + "' and a.chenhlech > 0 "+
				" union all "+
				" 	select " + btthId + ", ( select pk_seq from ERP_TAIKHOANKT where sohieutaikhoan = '15700000' and npp_fk = '1' ), abs(chenhlech) as NO, 0 as CO, a.sanpham_fk, N'Chênh lệch âm kho: ' + b.ten, 3 "+
				" 	from ERP_GIATHANH_GIATONKHO_CHENHLECH a inner join ERP_KHOTT b on a.kho_fk = b.pk_seq "+
				" 			inner join ERP_SANPHAM c on a.sanpham_fk = c.pk_seq "+
				" 			inner join ERP_LOAISANPHAM d on c.loaisanpham_fk = d.pk_seq "+
				" 	where a.thang = '" + thang + "' and a.nam = '" + nam + "' and a.chenhlech < 0 "+
				" union all "+
				" 	select " + btthId + ", ( select pk_seq from ERP_TAIKHOANKT where sohieutaikhoan = d.TaikhoanGiavon_sh_fk and npp_fk = '1' ), 0, abs(chenhlech), a.sanpham_fk, N'Chênh lệch âm kho: ' + b.ten, 4 "+
				" 	from ERP_GIATHANH_GIATONKHO_CHENHLECH a inner join ERP_KHOTT b on a.kho_fk = b.pk_seq "+
				" 			inner join ERP_SANPHAM c on a.sanpham_fk = c.pk_seq "+
				" 			inner join ERP_LOAISANPHAM d on c.loaisanpham_fk = d.pk_seq "+
				" 	where a.thang = '" + thang + "' and a.nam = '" + nam + "' and a.chenhlech < 0 ";
		
		System.out.println("::: BUT TOAN CHI TIET: " + query);
		db.update(query);
		
		
		//DINH KHOAN BUT TOAN
		query = " insert ERP_PHATSINHKETOAN( NGAYCHUNGTU, NGAYGHINHAN, LOAICHUNGTU, SOCHUNGTU, TAIKHOAN_FK, TAIKHOANDOIUNG_FK, NO, CO, DOITUONG, MADOITUONG, TIENTEGOC_FK, TIGIA, TONGGIATRI, DIENGIAI ) "+
				" 	select '" + ngay + "', '" + ngay + "', N'Bút toán tổng hợp', " + btthId + ",  "+
				" 			( select pk_seq from ERP_TAIKHOANKT where sohieutaikhoan = d.TaikhoanGiavon_sh_fk and npp_fk = '1' ) as taikhoan_fk,  "+
				" 			( select pk_seq from ERP_TAIKHOANKT where sohieutaikhoan = '15700000' and npp_fk = '1' ) as doiung,  "+
				" 			chenhlech as NO, 0 as CO, N'Sản phẩm' as DOITUONG, c.pk_seq as madoituong, 100000 as tiente, 1 as tigia, abs(chenhlech) as tongiatri, N'BTTH Chênh lệch dương kho: ' + b.ten "+
				" 	from ERP_GIATHANH_GIATONKHO_CHENHLECH a inner join ERP_KHOTT b on a.kho_fk = b.pk_seq "+
				" 			inner join ERP_SANPHAM c on a.sanpham_fk = c.pk_seq "+
				" 			inner join ERP_LOAISANPHAM d on c.loaisanpham_fk = d.pk_seq "+
				" 	where a.thang = '" + thang + "' and a.nam = '" + nam + "' and a.chenhlech > 0 "+
				" union all "+
				" 	select '" + ngay + "', '" + ngay + "', N'Bút toán tổng hợp', " + btthId + ",  "+
				" 			( select pk_seq from ERP_TAIKHOANKT where sohieutaikhoan = '15700000' and npp_fk = '1' ),  "+
				" 			( select pk_seq from ERP_TAIKHOANKT where sohieutaikhoan = d.TaikhoanGiavon_sh_fk and npp_fk = '1' ),  "+
				" 			0 as NO, chenhlech as CO, N'Sản phẩm' as DOITUONG, c.pk_seq as madoituong, 100000 as tiente, 1 as tigia, abs(chenhlech) as tongiatri, N'BTTH Chênh lệch dương kho: ' + b.ten "+
				" 	from ERP_GIATHANH_GIATONKHO_CHENHLECH a inner join ERP_KHOTT b on a.kho_fk = b.pk_seq "+
				" 			inner join ERP_SANPHAM c on a.sanpham_fk = c.pk_seq "+
				" 			inner join ERP_LOAISANPHAM d on c.loaisanpham_fk = d.pk_seq "+
				" 	where a.thang = '" + thang + "' and a.nam = '" + nam + "' and a.chenhlech > 0 "+
				" union all "+
				" 	select '" + ngay + "', '" + ngay + "', N'Bút toán tổng hợp', " + btthId + ",  "+
				" 			( select pk_seq from ERP_TAIKHOANKT where sohieutaikhoan = '15700000' and npp_fk = '1' ),  "+
				" 			( select pk_seq from ERP_TAIKHOANKT where sohieutaikhoan = d.TaikhoanGiavon_sh_fk and npp_fk = '1' ),  "+
				" 			abs(chenhlech) as NO, 0 as CO, N'Sản phẩm' as DOITUONG, c.pk_seq as madoituong, 100000 as tiente, 1 as tigia, abs(chenhlech) as tongiatri, N'BTTH Chênh lệch âm kho: ' + b.ten "+
				" 	from ERP_GIATHANH_GIATONKHO_CHENHLECH a inner join ERP_KHOTT b on a.kho_fk = b.pk_seq "+
				" 			inner join ERP_SANPHAM c on a.sanpham_fk = c.pk_seq "+
				" 			inner join ERP_LOAISANPHAM d on c.loaisanpham_fk = d.pk_seq "+
				" 	where a.thang = '" + thang + "' and a.nam = '" + nam + "' and a.chenhlech < 0 "+
				" union all "+
				" 	select '" + ngay + "', '" + ngay + "', N'Bút toán tổng hợp', " + btthId + ",  "+
				" 			( select pk_seq from ERP_TAIKHOANKT where sohieutaikhoan = d.TaikhoanGiavon_sh_fk and npp_fk = '1' ),  "+
				" 			( select pk_seq from ERP_TAIKHOANKT where sohieutaikhoan = '15700000' and npp_fk = '1' ), "+
				" 			0 as NO, abs(chenhlech) as CO, N'Sản phẩm' as DOITUONG, c.pk_seq as madoituong, 100000 as tiente, 1 as tigia, abs(chenhlech) as tongiatri, N'BTTH Chênh lệch âm kho: ' + b.ten "+
				" 	from ERP_GIATHANH_GIATONKHO_CHENHLECH a inner join ERP_KHOTT b on a.kho_fk = b.pk_seq "+
				" 			inner join ERP_SANPHAM c on a.sanpham_fk = c.pk_seq "+
				" 			inner join ERP_LOAISANPHAM d on c.loaisanpham_fk = d.pk_seq "+
				" 	where a.thang = '" + thang + "' and a.nam = '" + nam + "' and a.chenhlech < 0 ";
		
		System.out.println("::: BUT TOAN CHI TIET - DINH KHOAN: " + query);
		db.update(query);
		
	}


	
	
	
}
