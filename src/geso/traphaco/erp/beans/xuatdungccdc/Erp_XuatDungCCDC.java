package geso.traphaco.erp.beans.xuatdungccdc;
import geso.traphaco.center.util.DinhKhoanKeToan;
import geso.traphaco.distributor.util.Utility;
import geso.traphaco.center.util.UtilityKeToan;
import geso.traphaco.erp.beans.khauhaotaisancodinh.imp.KhauhaoCCDC;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.util.CapnhatKT;
import geso.traphaco.erp.util.Utility_Kho;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Erp_XuatDungCCDC
{
	private String id;
	private String ccdcId;
	private String tenCcdc;
	private String ghiChu;
	private String trangThai;
	private String ngayTao;
	private String ngaySua;
	private String nguoiTao;
	private String nguoiSua;
	private String congTyId;
	private String ngayChot;
	private String ngayXuatDung;
	private String msg;
	private boolean warning;
	private boolean xoaKhauHao;
	
	private List<Erp_Item> ccdcList;
	private List<Erp_Item> khoList;
	
	private List<Erp_VatTu> vatTuList;
	private List<DinhKhoanKeToan> dinhKhoanList;
	private dbutils db;

	public Erp_XuatDungCCDC()
	{
		id = "";
		this.ccdcId = "";
		this.tenCcdc = "";
		this.ghiChu = "";
		this.trangThai = "";
		this.ngayTao = "";
		this.ngaySua = "";
		this.nguoiTao = "";
		this.nguoiSua = "";
		this.congTyId = "";
		this.ngayChot = "";
		this.ngayXuatDung = "";
		this.msg = "";
		this.warning = false;
		xoaKhauHao = false;
		
		this.ccdcList = new ArrayList<Erp_Item>();
		this.khoList = new ArrayList<Erp_Item>();
		
		this.vatTuList = new ArrayList<Erp_VatTu>();
		this.dinhKhoanList = new ArrayList<DinhKhoanKeToan>();
		this.db = new dbutils();
	}
	
	public void init()
	{
		if (this.id.trim().length() > 0)
		{
			String query = "select isnull(NGAYXUATDUNG, '') as NGAYXUATDUNG, PK_SEQ, CCDC_FK, GHICHU, NGAYTAO, NGAYSUA from ERP_XUATDUNG where PK_SEQ = " + this.id;
			
			ResultSet rs = null;
			
			try {
				rs = this.db.get(query);
				
				if (rs != null)
					if (rs.next())
					{
						this.id = rs.getString("PK_SEQ");
						this.ccdcId = rs.getString("CCDC_FK");
						this.ghiChu = rs.getString("GHICHU");
						this.ngayTao = rs.getString("NGAYTAO");
						this.ngaySua = rs.getString("NGAYSUA");
						this.ngayXuatDung = rs.getString("NGAYXUATDUNG");
					}
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally{
				if (rs != null)
					try {
						rs.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
			}
			
			initVatTu();
		}
		initList();
	}
	
	private void initVatTu() {
		this.vatTuList.clear();
		String query = "select VT.PK_SEQ, sp.MA, VT.SANPHAM_FK, VT.KHOTT_FK, VT.TENVATTU, VT.DONVITINH, VT.SOLUONG, VT.DONGIA, VT.THANHTIEN \n" +
					   ", (select AVAILABLE from ERP_KHOTT_SANPHAM where SANPHAM_FK = VT.SANPHAM_FK and KHOTT_FK = VT.KHOTT_FK)+ VT.SOLUONG as tonHienTai \n" +
				       "from ERP_XUATDUNG_VATTU VT \n" +
				       "inner join ERP_SANPHAM sp on sp.PK_SEQ = VT.SANPHAM_FK \n" +
				       "where XUATDUNG_FK = " + this.id;
		
		System.out.println("init vat tu: \n" + query + "\n===============================");
		ResultSet rs = null;
		
		try {
			rs = this.db.get(query);
			
			if (rs != null)
				while (rs.next())
				{
					String id = rs.getString("PK_SEQ");
					String sanPhamId =  rs.getString("SANPHAM_FK");
					String maSanPham = rs.getString("MA");
					String tenVatTu = rs.getString("TENVATTU");
					String khoId = rs.getString("KHOTT_FK");
					String donViTinh = rs.getString("DONVITINH");
					double soLuong = rs.getDouble("SOLUONG");
					double donGia = rs.getDouble("DONGIA");
					double thanhTien = rs.getDouble("THANHTIEN");
					double tonHienTai = rs.getDouble("tonHienTai");
					Erp_VatTu vatTu = new Erp_VatTu(id, sanPhamId, khoId, maSanPham, tenVatTu, donViTinh, soLuong, donGia,thanhTien, tonHienTai);
					this.vatTuList.add(vatTu);
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		
		//Init vat tu lay theo so lo
		if (this.vatTuList.size() > 0)
		{
			for (Erp_VatTu vatTu : this.vatTuList)
			{
				query = "select SOLO, SOLUONG, NGAYNHAPKHO from ERP_XUATDUNG_VATTU_CHITIET where XUATDUNG_VATTU_FK = " + vatTu.getId();
				
				rs = null;
				
				try {
					rs = this.db.get(query);
					
					if (rs != null)
					{
						List<Erp_VatTuSoLo> loList = vatTu.getVatTuSoLoList();
						while (rs.next())
						{
							String soLo =  rs.getString("SOLO");
							double soLuong = rs.getDouble("SOLUONG");
							String ngayNhapKho =  rs.getString("NGAYNHAPKHO");
							
							Erp_VatTuSoLo vatTuSoLo = new Erp_VatTuSoLo(soLo, soLuong, ngayNhapKho);
							loList.add(vatTuSoLo);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				finally{
					if (rs != null)
						try {
							rs.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
				}
			}
		}
	}

	private void initList() {
		//Định khoản kế toán
		initDinhKhoanList();
		
		int thangKSMax;
		int namKSMax;

		List<Integer> l = new ArrayList<Integer>();
		geso.traphaco.center.util.Utility.GetThangKhoaSoMax(this.db, l);
		
		thangKSMax = l.get(0);
		namKSMax = l.get(1);
		
		//Công cụ dụng cụ
		this.ccdcList.clear();
		String query = 
			"SELECT cc.PK_SEQ, (cc.ma + ' - ' + cc.DIENGIAI) as ten, isnull(cc.SOTHANGDAKHAUHAO, 0) as SOTHANGDAKHAUHAO \n" +
			", (select top 1 kh.THANG from ERP_KHAUHAOCCDC kh where kh.CCDC_FK = cc.PK_SEQ and kh.TRANGTHAI = 1 order by kh.NAM, kh.THANG) as thangKH_Min\n" +
			", (select top 1 kh.NAM from ERP_KHAUHAOCCDC kh where kh.CCDC_FK = cc.PK_SEQ and kh.TRANGTHAI = 1 order by kh.NAM, kh.THANG) as namKH_Min\n" +
			", isNull((select top 1 'true' from ERP_KHAUHAOCCDC kh where kh.CCDC_FK = cc.PK_SEQ and kh.TRANGTHAI = 1 order by kh.NAM, kh.THANG), 'false') as daKhauHao\n" +
			"FROM ERP_CONGCUDUNGCU cc\n" +
			"where (cc.isDaThanhLy = 0 or cc.isDaThanhLy is null)\n" +
			"and cc.CONGTY_FK = " + this.congTyId;
//			" and (cc.TRANGTHAI = 0 " + (this.id.trim().length() > 0 ? ("or cc.pk_seq = " + this.ccdcId) : "") + ")";
		
		System.out.println("query lay ccdc : \n " + query + "\n===================");
		ResultSet rs = null;
		
		try {
			rs = this.db.get(query);
			
			if (rs != null)
				while (rs.next())
				{
					//Kiểm tra xem CCDC, không lấy những CCDC đã khấu hao trong tháng đã khóa sổ kế toán
					int soThangDaKH = rs.getInt("SOTHANGDAKHAUHAO");
					
					String daKhauHao = rs.getString("daKhauHao");
					
					boolean isGet = true;
					boolean isWarning = false;
					if (soThangDaKH > 0 || daKhauHao.trim().equals("true"))
					{
						isWarning = true;
						int namKH_Min = rs.getInt("namKH_Min");
						int thangKH_Min = rs.getInt("thangKH_Min");
						
						if (namKH_Min < namKSMax)
							isGet = false;
						else if (namKH_Min == namKSMax && thangKSMax > thangKH_Min)
							isGet = false;
					}
					
//					if (isGet == true)
//					{
						Erp_Item item = new Erp_Item(rs.getString("PK_SEQ"), rs.getString("TEN"));
//						if (isWarning == true)
//							item.setDifField("C--");
						this.ccdcList.add(item);
//					}
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		
		//Kho
		this.khoList.clear();
		//Chỉ lấy kho thuộc loại ccdc
		query = "select PK_SEQ, TEN from ERP_KHOTT where CONGTY_FK = " + this.congTyId + " and TRANGTHAI = 1 --and LOAI = '11'";
		
		rs = null;
		
		try {
			rs = this.db.get(query);
			
			if (rs != null)
				while (rs.next())
				{
					Erp_Item item = new Erp_Item(rs.getString("PK_SEQ"), rs.getString("TEN"));
					this.khoList.add(item);
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}
	
	private void initDinhKhoanList() {
		if (this.id.trim().length() > 0)
		{
			this.dinhKhoanList.clear();
			String query = 
				"select ps.PK_SEQ, tk.SOHIEUTAIKHOAN, case CO when 0 then N'NỢ' else N'CÓ' end as NO_CO \n" + 
				", case CO when 0 then no else CO end as soTien \n" + 
				", (case doiTuong when N'Sản phẩm' then (select sp.ten from ERP_SANPHAM sp where cast(sp.pk_seq as varchar(10)) = cast(ps.MADOITUONG as varchar(10))) \n" + 
				"	else (select cc.MA from ERP_CONGCUDUNGCU cc where cast(cc.PK_SEQ as varchar(10)) = cast(ps.MADOITUONG as varchar(10))) end) as doiTuong \n" + 
				"from ERP_PHATSINHKETOAN ps \n" + 
				"inner join ERP_TAIKHOANKT tk on tk.PK_SEQ = ps.TAIKHOAN_FK \n" + 
				"where LOAICHUNGTU like N'Xuất dùng vật tư - CCDC' and SOCHUNGTU = " + this.id + " \n";
			
			try {
				ResultSet rs = this.db.get(query);
				
				if (rs != null)
				{
					
					while (rs.next())
					{
						String psId = rs.getString("PK_SEQ");
						String soHieuTaiKhoan = rs.getString("SOHIEUTAIKHOAN");
						String noCo = rs.getString("NO_CO");
						String soTien = rs.getString("soTien");
						String doiTuong = rs.getString("doiTuong");
						DinhKhoanKeToan dinhKhoan = new DinhKhoanKeToan(psId, noCo, soHieuTaiKhoan, soTien, doiTuong
								, "", "", "");
						this.dinhKhoanList.add(dinhKhoan);
					}
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}

	//Hàm check sản phẩm có trong kho không, có đủ available không
	private boolean check_SanPham_Kho()
	{
		try {
			for (Erp_VatTu vatTu : this.vatTuList)
			{
				String query = "select SOLUONG, BOOKED, AVAILABLE, k.TEN from ERP_KHOTT_SANPHAM sp \n" +
							"inner join ERP_KHOTT k on k.PK_SEQ = sp.KHOTT_FK \n" + 
							"where KHOTT_FK = " + vatTu.getKhoId() + " and SANPHAM_FK = " + vatTu.getSanPhamId();
				ResultSet rs = this.db.get(query);
				
				if (rs != null)
				{
					if (rs.next())
					{
//						double soLuong = rs.getDouble("SOLUONG");
//						double booked = rs.getDouble("BOOKED");
						double availble = rs.getDouble("AVAILABLE");
						
						if (availble < vatTu.getSoLuongTinh())
							this.msg += "Sản phẩm " + vatTu.getMaSanPham() + " không không đủ tồn kho, tồn kho hiện tại là " + availble + "\n";
					}
					else
					{
						this.msg += "Sản phẩm " + vatTu.getMaSanPham() + " không có trong kho\n";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		if (this.msg.trim().length() > 0)
			return false;
		return true;
	}
	
	private boolean nhaBooked()
	{
		try {
			Utility_Kho uKho = new Utility_Kho();
			Erp_XuatDungCCDC xuatDung = new Erp_XuatDungCCDC();
			xuatDung.setCongTyId(this.congTyId);
			xuatDung.setId(this.id);
			xuatDung.init();
			xuatDung.DbClose();
			
			for (Erp_VatTu vatTu : xuatDung.getVatTuList())
			{
				String mess = uKho.Update_Kho_Sp("Xuất dùng", this.ngaySua, this.db, vatTu.getKhoId()
						, vatTu.getSanPhamId(), 0, -1 * vatTu.getSoLuongTinh(), vatTu.getSoLuongTinh(), 0);
				
				if (mess.trim().length() > 0)
				{
					this.msg = "NB1.1 Không thể tạo xóa / sửa phiếu xuất dùng \n" + mess;
					this.db.getConnection().rollback();
					return false;
				}
				
				for (Erp_VatTuSoLo vatTuSoLo : vatTu.getVatTuSoLoList())
				{
					mess = uKho.Update_Kho_Sp_Chitiet(this.db, vatTu.getKhoId()
					, vatTu.getSanPhamId(), 0, -1 * vatTuSoLo.getSoLuong(), vatTuSoLo.getSoLuong(), 0, vatTuSoLo.getSoLo(), vatTuSoLo.getNgayNhapKho(), "", "");
					
					if (mess.trim().length() > 0)
					{
						this.msg = "NB1.2 Không thể tạo xóa / sửa phiếu xuất dùng \n" + mess;
						this.db.getConnection().rollback();
						return false;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		if (this.msg.trim().length() > 0)
			return false;
		return true;
	}
	
	private boolean insert_VatTu_BookKho()
	{
		try {
			Utility_Kho uKho = new Utility_Kho(); 
			for (Erp_VatTu vatTu : this.vatTuList)
			{
				if (vatTu.getSanPhamId().trim().length() > 0)
				{
					//Insert vật tư
					String query = 
						"insert into ERP_XUATDUNG_VATTU(XUATDUNG_FK, SANPHAM_FK, KHOTT_FK \n" +
						", TENVATTU, DONVITINH, SOLUONG \n" +
						", DONGIA, THANHTIEN) \n" +
						"values(" + this.id + ", " + vatTu.getSanPhamId() + "," + vatTu.getKhoId() + " \n" +
						", N'" + vatTu.getTenVatTu() + "', N'" + vatTu.getDonViTinh() + "', " + vatTu.getSoLuongTinh() + " \n" +
						", " + vatTu.getDonGia() + ", " + vatTu.getThanhTien() + ")";
					
					if (!this.db.update(query))
					{
						this.msg = "I1.1 Không thể tạo mới / sửa phiếu xuất dùng";
						this.db.getConnection().rollback();
						return false;
					}
					
					//Lấy ID Xuất dùng vật tư mới insert
					//Lấy Id xuất dùng mới insert
					query = "select IDENT_CURRENT('ERP_XUATDUNG_VATTU') as vt";
					ResultSet rs = this.db.get(query);
					
					if (rs != null)
					{
						if (rs.next())
						{
							vatTu.setId(rs.getString("vt"));
						}
						rs.close();
					}
					
					//Insert vật tư chi tiết
					query = 
						"select SOLO, SOLUONG, BOOKED, AVAILABLE, NGAYNHAPKHO from ERP_KHOTT_SP_CHITIET  \n" + 
						"where KHOTT_FK = " + vatTu.getKhoId() + " and SANPHAM_FK = " + vatTu.getSanPhamId() + " and AVAILABLE > 0 and NGAYNHAPKHO <= '" + this.ngayTao + "' \n" + 
						"order by NGAYNHAPKHO desc \n";
					
					rs = this.db.get(query);
					double tongLuong = 0;
					vatTu.getVatTuSoLoList().clear();
					if (rs != null)
					{
						while (rs.next() && tongLuong < vatTu.getSoLuongTinh())
						{
							String soLo = rs.getString("SOLO");
//							double soLuong = rs.getDouble("SOLUONG");
//							double booked = rs.getDouble("BOOKED");
							String ngayNhapKho = rs.getString("ngayNhapKho");
							double available = rs.getDouble("AVAILABLE");
							Erp_VatTuSoLo vatTuSoLo = new Erp_VatTuSoLo();
							vatTu.getVatTuSoLoList().add(vatTuSoLo);
							vatTuSoLo.setSoLo(soLo);
							vatTuSoLo.setNgayNhapKho(ngayNhapKho);
							if (available <= (vatTu.getSoLuongTinh() - tongLuong))
							{
								vatTuSoLo.setSoLuongTinh(available);
								tongLuong += available;
							}
							else
							{
								vatTuSoLo.setSoLuongTinh(vatTu.getSoLuongTinh() - tongLuong);
								tongLuong = vatTu.getSoLuongTinh();
							}
						}
						rs.close();
					}

					query = "";
					for (Erp_VatTuSoLo vatTuSoLo : vatTu.getVatTuSoLoList())
					{
						query += 
							"insert into ERP_XUATDUNG_VATTU_CHITIET(XUATDUNG_VATTU_FK, SANPHAM_FK, KHOTT_FK" +
							", SOLUONG, SOLO, NGAYNHAPKHO) \n" +
							"values(" + vatTu.getId() + ", " + vatTu.getSanPhamId() + ", " + vatTu.getKhoId() + 
							", " + vatTuSoLo.getSoLuong() + ", '" + vatTuSoLo.getSoLo() + "', '" + vatTuSoLo.getNgayNhapKho() + "');";
					}
					
					System.out.println("tao moi chi tiet:\n" + query + "\n===============================");
					if (query.trim().length() > 0)
						if (!this.db.update(query))
						{
							this.msg = "I1.1 Không thể tạo mới / sửa phiếu xuất dùng \n";
							this.db.getConnection().rollback();
							return false;
						}
					
					//Book kho
					String mess = uKho.Update_Kho_Sp("Xuất dùng", this.ngaySua, this.db, vatTu.getKhoId()
							, vatTu.getSanPhamId(), 0, vatTu.getSoLuongTinh(), -1 * vatTu.getSoLuongTinh(), 0);
					
					if (mess.trim().length() > 0)
					{
						this.msg = "I1.2 Không thể tạo mới / sửa phiếu xuất dùng \n" + mess;
						this.db.getConnection().rollback();
						return false;
					}
					
					for (Erp_VatTuSoLo vatTuSoLo : vatTu.getVatTuSoLoList())
					{
						mess = uKho.Update_Kho_Sp_Chitiet( this.db, vatTu.getKhoId()
						, vatTu.getSanPhamId(), 0, vatTuSoLo.getSoLuong(), -1 * vatTuSoLo.getSoLuong(), 0, vatTuSoLo.getSoLo(), vatTuSoLo.getNgayNhapKho(), "", "");
						
						if (mess.trim().length() > 0)
						{
							this.msg = "I1.3 Không thể tạo mới / sửa phiếu xuất dùng \n" + mess;
							this.db.getConnection().rollback();
							return false;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		if (this.msg.trim().length() > 0)
			return false;
		return true;
	}
	
	public void changeKho(String khoIndex)
	{
		
//		int index = Integer.parseInt(khoIndex);
//		Erp_VatTu vatTu = this.vatTuList.get(index);
	}
	
	private boolean capNhatNguyenGiaCCDC()
	{
		//Cập nhật nguyên giá CCDC
		String query = 
			"insert into ERP_CONGCUDUNGCU_DIEUCHINH\n" +
			"(CCDC_FK,GIATRI, LOAICHUNGTU, BANGTHAMCHIEU, SOCHUNGTU, NGAYDIEUCHINH)\n" +
			"values(" + this.ccdcId+ "" +
			", (select sum(THANHTIEN) from ERP_XUATDUNG_VATTU where XUATDUNG_FK = " + this.id+ " group by XUATDUNG_FK)" +
			", 0, 'ERP_XUATDUNGCCDC', " + this.id + "" +
			", (select NGAYXUATDUNG from ERP_XUATDUNG where PK_SEQ = " + this.id+ "))";
		System.out.println("Cau cap nhat nguyen gia: \n" + query + "\n=======================================");
		try {
			if (!this.db.update(query))
			{
				this.msg = "CNNGCCDC1.1 Không thể cập nhật nguyên giá CCDC";
				this.db.getConnection().rollback();
				return false;
			}
			
			query = "select count(*) as num from ERP_CONGCUDUNGCU where (isDaThanhLy = 0 OR isDaThanhLy is null) and PK_SEQ = " + this.ccdcId;
			ResultSet rs = this.db.get(query);
			
			if (rs != null)
				if (rs.next())
					if (rs.getInt("num") == 0)
					{
						this.msg = "CNNGCCDC1.2 Công cụ dụng cụ đã thanh lý";
						this.db.getConnection().rollback();
						return false;
					}
			query = "update ERP_CONGCUDUNGCU set trangThai = 1 where PK_SEQ = " + this.ccdcId;
			if (!this.db.update(query))
			{
				this.msg = "CNNGCCDC1.3 Không thể cập nhật nguyên giá CCDC";
				this.db.getConnection().rollback();
				return false;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	private boolean chayKeHoachPhanBoCCDC()
	{
		String[] param = new String[1];
		param[0] = this.ccdcId;
		try{
			String result = this.db.execProceduce2("KeHoachPhanBo", param);
			if (result.trim().length() > 0)
			{
				this.setMsg("CKHPBCCDC1.2 Không thể tạo mới tài sản cố định \n" + result);
				this.db.getConnection().rollback();
				return false;
			}
		}catch (Exception e) {
			e.printStackTrace();
			this.setMsg("CKHPBCCDC1.3 Không thể tạo mới tài sản cố định \n");
			try {
				this.db.getConnection().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return false;
		}
		return true;
	}
	
	//Kiểm tra xemCCDC: hợp lệ: 0 
	//Có có khấu hao trong tháng đã khóa sổ: 1 -> Error
	//Có khấu hao trong tháng chưa khóa sổ: 2 -> Warning
	private int checkCCDC()
	{
		int result = 0;
		
		//Lấy tháng năm khóa sổ max
		int thangKSMax = new Integer(0);
		int namKSMax = new Integer(0);
		
		List<Integer> l = new ArrayList<Integer>();
		geso.traphaco.center.util.Utility.GetThangKhoaSoMax(this.db, l);
		
		thangKSMax = l.get(0);
		namKSMax = l.get(1);
		System.out.println("thangKSMaxcheckCCDC: " + thangKSMax);
		System.out.println("namKSMaxcheckCCDC: " + namKSMax);
		
		//Mã CCCDC đã khấu hao?
		String query = 
			"select THANG, NAM " +
			"from ERP_KHAUHAOCCDC " +
			"where TRANGTHAI = 1 and CCDC_FK = " + this.ccdcId + "" +
			"order by nam, thang";
		
		ResultSet rs = null;
		
		try{
			rs = this.db.get(query);

			if (rs != null)
			{
				if (rs.next())
				{
					int namKH_Min = rs.getInt("nam");
					int thangKH_Min = rs.getInt("thang");
					this.msg = "Tháng " + thangKH_Min + " năm " + namKH_Min;

					if (namKH_Min < namKSMax)
						result = 1;
					else if (namKH_Min == namKSMax && thangKSMax > thangKH_Min)
						result = 1;
					else
					{
						result = 2;
						while (rs.next())
						{
							namKH_Min = rs.getInt("nam");
							thangKH_Min = rs.getInt("thang");
							this.msg += "\nTháng " + thangKH_Min + " năm " + namKH_Min;
						}
					}
				}
			}
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	//Kiểm tra tháng xuất dùng đã khóa sổ chưa
	boolean checkNgayXuatDung()
	{
		List<Integer> l = new ArrayList<Integer>();
		geso.traphaco.center.util.Utility.GetThangKhoaSoMax(this.db, l);
		
		//Lấy tháng năm khóa sổ max
		int thangKSMax = l.get(0);
		int namKSMax = l.get(1);
		
		int namXuatDung = Integer.parseInt(this.ngayXuatDung.split("-")[0]);
		int thangXuatDung = Integer.parseInt(this.ngayXuatDung.split("-")[1]);
		
		System.out.println("thangKSMax: " + thangKSMax);
		System.out.println("namKSMax: " + namKSMax);
		if (namXuatDung < namKSMax || (namXuatDung == namKSMax && thangKSMax >= thangXuatDung))
			return false;
		
		return true;
	}
	
	public boolean newXuatDung() {
		try {
			this.db.getConnection().setAutoCommit(false);
			
			if (this.ngayXuatDung == null || this.ngayXuatDung.trim().length() == 0)
			{
				this.msg = "N1.0.0 Vui lòng chọn ngày 'Ngày xuất dùng'\n";
				this.db.getConnection().rollback();
				return false;
			}
			
			//Kiểm tra ngày xuất dùng có nằm trong kì đã khóa sổ
			boolean result = checkNgayXuatDung();
			
			if (result == false)
			{
				this.msg = "N1.0.1 Không thể tạo mới phiếu xuất dùng\nCCDC đã có khấu hao trong tháng đã khóa sổ\n" + this.msg;
				this.db.getConnection().rollback();
				return false;
			}	
			
			if (!check_SanPham_Kho())
			{
				this.msg = "N1.0 Không thể tạo mới phiếu xuất dùng\n" + this.msg;
				this.db.getConnection().rollback();
				return false;
			}	
			
			String query = "insert into ERP_XUATDUNG(NGAYXUATDUNG, CONGTY_FK, CCDC_FK, GHICHU" +
					", TRANGTHAI, NGAYTAO, NGAYSUA" +
					",NGUOITAO,NGUOISUA) \n" +
					"values('" + this.ngayXuatDung + "', " + this.congTyId + ", " + this.ccdcId + ", N'" + this.ghiChu + 
					"', 0, '" + getDateTime() + "', '" + getDateTime() + 
					"', " + this.nguoiTao + ", " + this.nguoiTao + ")";
			if (!this.db.update(query))
			{
				this.msg = "N1.1 Không thể tạo mới phiếu xuất dùng";
				this.db.getConnection().rollback();
				return false;
			}
			
			//Lấy Id xuất dùng mới insert
			query = "select IDENT_CURRENT('ERP_XUATDUNG') as xd";
			ResultSet rs = this.db.get(query);
			
			if (rs != null)
			{
				if (rs.next())
				{
					this.id = rs.getString("xd");
					
					//Insert vật tư + book kho
					if (!insert_VatTu_BookKho())
					{
						this.db.getConnection().rollback();
						return false;
					}
				}
			}
			
//			if (capNhatNguyenGiaCCDC() == false)
//			{
//				this.db.getConnection().rollback();
//				return false;
//			}	
			
			this.db.getConnection().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			try {
				this.db.getConnection().setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return true;
	}
	
	//Cập nhật lại: chỉ xóa phân bổ,không chạy lại
	private boolean capNhatPhanBo()
	{
		List<String> namList = new ArrayList<String>();
		List<String> thangList = new ArrayList<String>();
		List<String> thangThuList = new ArrayList<String>();
		
		if (xoaKhauHao(namList, thangList, thangThuList) == false)
			return false;
		
		//Chạy lại kế hoạch phân bổ
//		if (chayKeHoachPhanBoCCDC() == false)
//		{
//			return false;
//		}
		

//		if (themPhanBo(namList, thangList, thangThuList) == false)
//			return false;
		
		return true;
	}
	
	private boolean xoaKhauHao(List<String> namList, List<String> thangList, List<String> thangThuList) {
		boolean result = true;
		KhauhaoCCDC khauHao = new KhauhaoCCDC();
		String namXuatDung = this.ngayXuatDung.split("-")[0];
		String thangXuatDung = this.ngayXuatDung.split("-")[1];
		
		//Xóa những tháng phân bổ kể từ tháng xuất dùng
		String query = 
			"select THANG, NAM, THANGTHU\n" +
			"from ERP_KHAUHAOCCDC \n" +
			"where TRANGTHAI = 1 and CCDC_FK = " + this.ccdcId +
			" and (nam > " + namXuatDung + " or (nam = " + namXuatDung + " and thang >= " + thangXuatDung + ")) ";
		
		ResultSet rs = null;
		try {
			rs = this.db.get(query);
			
			if (rs != null)
			{
				while (rs.next())
				{
					String nam = rs.getString("nam");
					String thang = rs.getString("thang");
					String lanthu = rs.getString("thangThu");

					namList.add(nam);
					thangList.add(thang);
					thangThuList.add(lanthu);
					
					String str = nam + ";" + thang + ";" + this.ccdcId + ";" + lanthu;
					boolean kq = khauHao.Cancel(this.db, str);
					if (kq == false)
					{
						this.msg = "XKH1.0 Lỗi xóa khấu hao\n" + khauHao.getMsg();
						this.db.getConnection().rollback();
						this.warning = true;
						return false;
					}
				}
				rs.close();
			}
			khauHao.DBClose();
		} catch (SQLException e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}
	
	private boolean themPhanBo(List<String> namList, List<String> thangList, List<String> thangThuList) 
	{
		try {
				for (int index = 0; index < namList.size(); index++)
				{
					String nam = namList.get(index);
					String thang = thangList.get(index);
					String lanthu = thangThuList.get(index);
					String khauHao = "";
					
					String query = "select khauHaoDuKien from ERP_CONGCUDUNGCU_CHITIET where CCDC_FK = " + this.ccdcId + " and thang = " + lanthu + "";
					
					ResultSet rs = this.db.get(query);
					
					if (rs != null)
					{
						if (rs.next())
							khauHao = rs.getString("khauHaoDuKien");
						rs.close();
					}
					else
						return false;
					
					if (savePhanBo(thang, nam, lanthu, khauHao) == false)
						return false;
					
//					String query = "INSERT INTO ERP_KHAUHAOCCDC(THANG, NAM, CCDC_FK\n" +
//							", KHAUHAO, THANGTHU, NGUOITAO\n" +
//							", NGAYTAO, NGUOISUA, NGAYSUA, TRANGTHAI) \n" +
//					"VALUES(" + thang + "," + nam + "," + this.ccdcId + "\n" +
//							", (select khauHaoDuKien from ERP_CONGCUDUNGCU_CHITIET where CCDC_FK = " + this.ccdcId + " and thang = " + lanthu + ")\n" +
//							", '" + lanthu + "','" + this.nguoiSua + "'\n" +
//							",'" + this.getDateTime() + "','" + this.nguoiSua + "','" + this.getDateTime() + "', '1' )";
//	
//					
//					System.out.println("them khau hao: \n" + query + "\n-------------------------------------");
//					if(this.db.update(query))
//					{
//						query = "UPDATE ERP_CONGCUDUNGCU_CHITIET \n" +
//								"SET KHAUHAOTHUCTE = KHAUHAODUKIEN\n" +
//								", KHAUHAOLUYKETHUCTE = KHAUHAOLUYKEDUKIEN\n" +
//								", GIATRICONLAITHUCTE = GIATRICONLAIDUKIEN \n" +
//								"WHERE CCDC_FK = '" + this.ccdcId + "' AND THANG = '" + thang + "' \n";
//			
//						System.out.println(query);
//						if(!this.db.update(query))
//						{							
//							this.msg="Không thể cập nhật ERP_CONGCUDUNGCU_CHITIET";
//							this.db.getConnection().rollback();
//							return false;
//					
//						}						
//					}
//					else
//					{
//						return false;
//					}
				}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean savePhanBo(String thang, String nam, String thangThu, String khauHao)
	{	
		String query;
		try
		{
			db.getConnection().setAutoCommit(false);
			
			Utility util = new Utility();
			if(this.ccdcId != null)
			{
				query = "INSERT INTO ERP_KHAUHAOCCDC(THANG, NAM, CCDC_FK, KHAUHAO, THANGTHU, NGUOITAO, NGAYTAO, NGUOISUA, NGAYSUA, TRANGTHAI) " +
						"VALUES(" + thang + "," + nam + "," + this.ccdcId + ", " + khauHao + ", " +
							"'" + thangThu + "','" + this.nguoiSua + "','" + this.getDateTime() + "','" + this.nguoiSua + "','" + this.getDateTime() + "', '1' )";
		
				System.out.println("1.Khau hao tai san: " + query);
				if(this.db.update(query))
				{
					query = "UPDATE ERP_CONGCUDUNGCU_CHITIET " +
							"SET KHAUHAOTHUCTE = " + khauHao + "" +
							", KHAUHAOLUYKETHUCTE = KHAUHAOLUYKEDUKIEN" +
							", GIATRICONLAITHUCTE = GIATRICONLAIDUKIEN " +
							"WHERE CCDC_FK = " + this.ccdcId + " AND THANG = '" + thangThu + "' ";
		
					System.out.println(query);
					if(!this.db.update(query))
					{							
						this.msg="Không thể cập nhật ERP_CONGCUDUNGCU_CHITIET";
						this.db.getConnection().rollback();
						return false;
				
					}						
				}
				

				//Chạy kế toán
				query = "SELECT CCDC.PK_SEQ as ccdcId, TK1.SOHIEUTAIKHOAN, CCDC.MA as MACCDC\n" +
						", SUM(ISNULL(CCDC_CD.PHANTRAM, 0) * KH.KHAUHAO / 100 ) AS TOTALKHAUHAO, \n" +  
						"TK2.SOHIEUTAIKHOAN AS SOHIEUTAIKHOANCO \n" +   
						"FROM ERP_CONGCUDUNGCU_CONGDUNG CCDC_CD \n" +
						"INNER JOIN ERP_CONGDUNGCCDC CD ON CCDC_CD.CONGDUNG_FK = CD.PK_SEQ \n" + 
						"INNER JOIN ERP_TAIKHOANKT TK1 ON CD.TAIKHOAN_FK = TK1.PK_SEQ \n" + 
						"INNER JOIN ERP_CONGCUDUNGCU CCDC ON CCDC_CD.CCDC_FK = CCDC.PK_SEQ " + 
						"INNER JOIN Erp_LOAICCDC LCCDC ON LCCDC.PK_SEQ = CCDC.LOAICCDC_FK \n" +
						"INNER JOIN ERP_TAIKHOANKT TK2 ON LCCDC.TAIKHOAN_FK = TK2.PK_SEQ  \n" +
						"INNER JOIN ERP_KHAUHAOCCDC KH ON CCDC.PK_SEQ = KH.CCDC_FK  and kh.trangThai != 2 \n" +
						"WHERE CCDC.PK_SEQ = " + this.ccdcId + " AND KH.THANG = '" + thang + "' AND KH.NAM = '" + nam + "' \n" +  
						"GROUP BY TK1.SOHIEUTAIKHOAN, CCDC.MA, CCDC.LOAICCDC_FK, CD.TAIKHOAN_FK, TK2.SOHIEUTAIKHOAN,CCDC.PK_SEQ \n";					
				
				
				System.out.println("____LAY TAI KHOAN KHAU HAO: \n" + query  + "\n================================");
				ResultSet rsTk = this.db.get(query);
				CapnhatKT KT;
				if (rsTk != null)
				{
					while(rsTk.next())
					{
						double totalKhauHao = rsTk.getDouble("totalKhauHao");
						
						String taikhoanCO_SoHieu = rsTk.getString("sohieutaikhoanCO");
						String taikhoanNO_SoHieu = rsTk.getString("sohieutaikhoan");
						
						String ngayghinhan = nam + "-" + ( thang.trim().length() < 2 ? "0" + thang : thang ) + "-30";
						ngayghinhan = getLastDayOfMonth(ngayghinhan);
					
						if(taikhoanCO_SoHieu.trim().length() <= 0 || taikhoanNO_SoHieu.trim().length() <= 0 )
						{
							msg = "Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
							db.getConnection().rollback();
							return false;
						}
						
						String tiente_fk = "100000";
						
						String soChungTu = nam + "" + (thang.trim().length() < 2 ? ("0" + thang) : thang);
						
						KT = new CapnhatKT();
						KT.setSochungtu(this.id);
						//KT.setSpId(rsTk.getString("ccdcId"));
						KT.setNam(nam);
						KT.setThang(thang);
						KT.setTaikhoanCO_fk(taikhoanCO_SoHieu);
						KT.setTaikhoanNO_fk(taikhoanNO_SoHieu);
						KT.setLoaichungtu("Khấu hao công cụ dụng cụ");
						KT.setDOITUONG_CO("");
						KT.setDOITUONG_NO("Công cụ dụng cụ");
						
						KT.setMADOITUONG_CO("");
						KT.setMADOITUONG_NO(rsTk.getString("ccdcId"));
						KT.setTIGIA_FKl("1");
						KT.setDONGIANT("0");
					    KT.setChiPhiId("NULL");
					    KT.setTIENTEGOC_FK(tiente_fk);
					    KT.setSOLUONG("0");	
					    KT.setDONGIA("0");		
					    KT.setNO(Double.toString(totalKhauHao));
						KT.setCO(Double.toString(totalKhauHao));
						KT.setTONGGIATRI(Double.toString(totalKhauHao));
						KT.setNgaychotnv(ngayghinhan);
						KT.setNgaychungtu(ngayghinhan);
						KT.setNgayghinhan(ngayghinhan);
						KT.setKhoanmuc("");
						String msg1=KT.CapNhatKeToan_Kho(util, db);
						if(msg1.length()> 0){
							this.msg = msg1;
							this.db.getConnection().rollback();
							return false;
						}
						
						
						/*this.msg = util.Update_TaiKhoan_TheoSoHieu_DoiTuong_NoCo(this.congTyId, db, thangThu, nam, ngayghinhan, ngayghinhan,
								"Khấu hao công cụ dụng cụ", soChungTu, taikhoanNO_SoHieu, taikhoanCO_SoHieu, "", Double.toString(totalKhauHao), Double.toString(totalKhauHao)
								, "Công cụ dụng cụ", rsTk.getString("ccdcId"), "", "", "", "", "", "", tiente_fk, "", "1", Double.toString(totalKhauHao), Double.toString(totalKhauHao), "", "", "", "");*/
						/*this.msg = util.Update_TaiKhoan_TheoSoHieu(this.congTyId, db, thang, nam, ngayghinhan, ngayghinhan,
								"Khấu hao công cụ dụng cụ", soChungTu, taikhoanNO_SoHieu, taikhoanCO_SoHieu, "", 
								Double.toString(totalKhauHao), Double.toString(totalKhauHao), "Công cụ dụng cụ", rsTk.getString("ccdcId"), "0", "", "", tiente_fk, "", "1", Double.toString(totalKhauHao), Double.toString(totalKhauHao), "" );*/
						if(this.msg.trim().length() > 0)
						{
							db.getConnection().rollback();
							return false;
						}

					}
					rsTk.close();
				}
				
				query =  "SELECT CCDC.TTCP_FK AS TTCPID, PB.TTCPNHAN_FK AS TTCPNHANID, PB.PHANTRAM, KHCCDC.KHAUHAO   \n" +
						 "FROM ERP_CONGCUDUNGCU CCDC \n" +
						 "INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = CCDC.TTCP_FK  \n" +
						 "INNER JOIN ERP_TRUNGTAMCHIPHI_PHANBO PB ON TTCPCHO_FK = TTCP.PK_SEQ AND CCDC.PK_SEQ = PB.CCDC_FK \n" +
						 "INNER JOIN ERP_KHAUHAOCCDC KHCCDC ON KHCCDC.CCDC_FK = CCDC.PK_SEQ  " +
						 "WHERE CCDC.PK_SEQ IN (" + this.ccdcId + ") AND KHCCDC.THANG = " + thang + " AND KHCCDC.NAM = " + nam + "  \n" +
						 "union ALL \n" +
						 "SELECT CCDC.TTCP_FK AS TTCPID, TTCP.PK_SEQ AS TTCPNHANID, 100 as PHANTRAM, KHCCDC.KHAUHAO   \n" +
						 "FROM ERP_CONGCUDUNGCU CCDC \n" +
						 "INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = CCDC.TTCP_FK  \n" +
						 "INNER JOIN ERP_KHAUHAOCCDC KHCCDC ON KHCCDC.CCDC_FK = CCDC.PK_SEQ  \n" +
						 "WHERE CCDC.PK_SEQ IN (" + this.ccdcId + ") AND KHCCDC.THANG = " + thang + " AND KHCCDC.NAM = " + nam + " \n" ;
				
				System.out.println(query);
				
				ResultSet rs = this.db.get(query);
				
				if (rs != null)
				{
					while(rs.next()){

						query = "INSERT INTO ERP_TRUNGTAMCHIPHI_THUCCHI(TTCP_FK, THANG, NAM, THUCCHI) VALUES" +
								"(" + rs.getString("TTCPNHANID") + ", '" + thang + "','" + nam + "', CONVERT(float, " + rs.getString("KHAUHAO") + ")*CONVERT(float," + rs.getString("PHANTRAM") +")/100 )";
						
						System.out.println(query);
						
						if(!this.db.update(query)){
							query = "UPDATE ERP_TRUNGTAMCHIPHI_THUCCHI SET THUCCHI = THUCCHI + CONVERT(float, " + rs.getString("KHAUHAO") + ")*CONVERT(float," + rs.getString("PHANTRAM") +")/100  " +
									"WHERE TTCP_FK = " + rs.getString("TTCPNHANID") + " AND NAM = '" + nam + "' AND THANG = '" + thang + "'";
							
							System.out.println(query);
							this.db.update(query);
						}
						
					}
					rs.close();
				}
			}
			
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			db.update("rollback");
			this.msg = "Không thể thực hiện khấu hao. " + e.getMessage();
			return false;
		}
	}

	public boolean editXuatDung() {
		try {
			this.db.getConnection().setAutoCommit(false);
			this.ngaySua = getDateTime();
			
			if (this.ngayXuatDung == null || this.ngayXuatDung.trim().length() == 0)
			{
				this.msg = "E1.0.0 Vui lòng chọn ngày 'Ngày xuất dùng'\n";
				this.db.getConnection().rollback();
				return false;
			}
			
			//Kiểm tra CCDC
//			//Kiểm tra ngày xuất dùng có nằm trong kì đã khóa sổ
			boolean result = checkNgayXuatDung();
			
			if (result == false)
			{
				this.msg = "E1.0.1 Không thể tạo mới phiếu xuất dùng\ntháng xuất dùng đã khóa sổ\n" + this.msg;
				this.db.getConnection().rollback();
				return false;
			}	
			
			
			if (!nhaBooked())
			{
				this.msg = "E1.0 Không thể sửa phiếu xuất dùng\n" + this.msg;
				this.db.getConnection().rollback();
				return false;
			}	
			
			String query = 
				"update ERP_XUATDUNG set CCDC_FK = " + this.ccdcId+ ", GHICHU = N'" + this.ghiChu+ "'\n" +
				", NGAYSUA = '" + getDateTime() + "'\n" +
				", NGUOISUA = " + this.nguoiSua + " \n" +
				", NGAYXUATDUNG = '" + this.ngayXuatDung + "'" +
				"where PK_SEQ = " + this.id;
			if (!this.db.update(query))
			{
				this.msg = "E1.1 Không thể sửa phiếu xuất dùng";
				this.db.getConnection().rollback();
				return false;
			}

			
			query = "delete ERP_XUATDUNG_VATTU_CHITIET " +
					"where XUATDUNG_VATTU_FK in " +
					"	(select pk_seq from erp_xuatdung_vattu " +
					"	where xuatdung_fk = " + this.id + ")";
			
			if (!this.db.update(query))
			{
				this.msg = "E1.2.0 Không thể sửa phiếu xuất dùng";
				this.db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_XUATDUNG_VATTU where XUATDUNG_FK = " + this.id;
			
			if (!this.db.update(query))
			{
				this.msg = "E1.2 Không thể sửa phiếu xuất dùng";
				this.db.getConnection().rollback();
				return false;
			}
			
			if (!check_SanPham_Kho())
			{
				this.msg = "E1.3 Không thể sửa phiếu xuất dùng\n" + this.msg;
				this.db.getConnection().rollback();
				return false;
			}	
			
			//Insert vật tư + book kho
			if (!insert_VatTu_BookKho())
			{
				this.msg = "E1.4 Không thể sửa phiếu xuất dùng\n" + this.msg;
				this.db.getConnection().rollback();
				return false;
			}
			
//			if (capNhatNguyenGiaCCDC() == false)
//			{
//				this.db.getConnection().rollback();
//				return false;
//			}	
			
			this.db.getConnection().commit();
		} catch (Exception e) {
			e.printStackTrace();
			this.msg = "E1 Không thể sửa phiếu xuất dùng";
			return false;
		}
		finally
		{
			try {
				this.db.getConnection().setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return true;
	}
	
	public boolean deleteXuatDung()
	{
		try {
			this.db.getConnection().setAutoCommit(false);
			
			if (!nhaBooked())
			{
				this.msg = "D1.0 Không thể xóa phiếu xuất dùng\n" + this.msg;
				this.db.getConnection().rollback();
				return false;
			}	
			
			String query = "update ERP_XUATDUNG set trangThai = 2 where trangThai = 0 and PK_SEQ = " + this.id;
			int num = this.db.updateReturnInt(query);
			if (num < 1)
			{
				this.msg = "D1.1 Không thể xóa phiếu xuất dùng";
				this.db.getConnection().rollback();
				return false;
			}

			this.db.getConnection().commit();
		} catch (Exception e) {
			e.printStackTrace();
			this.msg = "D1 Không thể xóa phiếu xuất dùng";
			return false;
		}
		finally
		{
			try {
				this.db.getConnection().setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return true;
	}
	
	public boolean chotXuatDung() {
		//Lấy giá tồn kì gần nhất áp cho sản phẩm
		try {
			this.db.getConnection().setAutoCommit(false);
			//Kiểm tra CCDC
			int result = checkCCDC();
			
			
			Utility_Kho uKho = new Utility_Kho();
			String query = null;
			this.ngayChot = getDateTime();
			for (Erp_VatTu vatTu : this.vatTuList)
			{
				//Lấy kì khóa sổ gần nhất
				query = "select NAM, THANGKS from ERP_KHOASOTHANG_KHO where KHOTT_FK = " + vatTu.getKhoId() + " order by NAM desc, THANGKS desc";
				System.out.println("ky khoa so:\n" + query + "\n--------------------------------");
				ResultSet rs = this.db.get(query);
				String thang = null;
				String nam = null;
				boolean isContinue = true;
				if (rs != null )
				{
					while (rs.next() && isContinue == true)
					{
						thang = rs.getString("THANGKS");
						nam = rs.getString("NAM");
						
						//Lấy giá tồn kì khóa sổ gần nhất
						query = "select GIATON  from ERP_TONKHOTHANG \n" +
							" where KHOTT_FK = " + vatTu.getKhoId() + " and THANG = " + thang + 
							" and NAM = " + nam + " and SANPHAM_FK = " + vatTu.getSanPhamId();
						System.out.println("gia ton khoa so:\n" + query + "\n--------------------------------");
						ResultSet rs1 = this.db.get(query);
						if (rs1 != null )
						{
							if (rs1.next())
							{
								isContinue = false;
								double giaTon = rs1.getDouble("GIATON");
								System.out.println("gia ton hien tai:" + giaTon);
								if (giaTon <= 0)
								{
									System.out.println("gia ton cung");
									giaTon = 100000;
								}
								vatTu.setDonGia(giaTon);
								vatTu.setThanhTien(vatTu.getDonGia() * vatTu.getSoLuongTinh());
							}
							rs1.close();
						}
					}
					
					//Vật tư không có trong kì trước, phát sinh kì này => lấy giá tồn hiện tại
					if (isContinue == true)
					{
						query = " select GIATON from ERP_KHOTT_SANPHAM where KHOTT_FK = " + vatTu.getKhoId() + " and SANPHAM_FK = " + vatTu.getSanPhamId();
						System.out.println("gia ton hien tai:\n" + query + "\n--------------------------------");
						ResultSet rs1 = this.db.get(query);
						if (rs1 != null )
						{
							if (rs1.next())
							{
								isContinue = false;
								double giaTon = rs1.getDouble("GIATON");
								System.out.println("gia ton hien tai:" + giaTon);
								if (giaTon <= 0)
								{
									System.out.println("gia ton cung");
									giaTon = 100000;
								}
								vatTu.setDonGia(giaTon);
								vatTu.setThanhTien(vatTu.getDonGia() * vatTu.getSoLuongTinh());
							}
							rs1.close();
						}
					}
					rs.close();
				}
				
//				vatTu.setDonGia(uKho.getDonGiaTonChayKT(this.db, vatTu.getSanPhamId(), vatTu.getKhoId()));
//				vatTu.setThanhTien(vatTu.getDonGia() * vatTu.getSoLuongTinh());
				if (isContinue == true)
				{
					vatTu.setDonGia(100000);
					vatTu.setThanhTien(vatTu.getDonGia() * vatTu.getSoLuongTinh());
				}
				
				//Lưu giá xuống cho xuất dùng
				query = "update ERP_XUATDUNG_VATTU \n" +
					"set DONGIA = " + vatTu.getDonGia() + ", THANHTIEN = " + vatTu.getThanhTien() + " \n" +
					"where PK_SEQ = " + vatTu.getId();
				
				if (!this.db.update(query))
				{
					this.db.getConnection().rollback();
					this.msg = "C1.1 Không thể chốt phiếu xuất dùng";
					return false;
				}
				
				
				//Trừ kho
				String mess = uKho.Update_Kho_Sp("Xuất dùng", this.ngaySua, this.db, vatTu.getKhoId()
						, vatTu.getSanPhamId(), -1 * vatTu.getSoLuongTinh(), -1 * vatTu.getSoLuongTinh(), 0, 0);
				
				if (mess.trim().length() > 0)
				{
					this.msg = "C1.2 Không thể chốt phiếu xuất dùng \n" + mess;
					this.db.getConnection().rollback();
					return false;
				}
				
				for (Erp_VatTuSoLo vatTuSoLo : vatTu.getVatTuSoLoList())
				{
					mess = uKho.Update_Kho_Sp_Chitiet( this.db, vatTu.getKhoId()
					, vatTu.getSanPhamId(), -1 * vatTuSoLo.getSoLuong(), -1 * vatTuSoLo.getSoLuong(), 0, 0, vatTuSoLo.getSoLo(), vatTuSoLo.getNgayNhapKho(), "", "");
					
					if (mess.trim().length() > 0)
					{
						this.msg = "C1.3 Không thể chốt phiếu xuất dùng \n" + mess;
						this.db.getConnection().rollback();
						return false;
					}
				}
			}
			
			//Lưu nguyên giá cho CCDC
			if (capNhatNguyenGiaCCDC() == false)
			{
				this.msg = "C1.1.1 Không thể chốt phiếu xuất dùng \n" + this.msg;
				this.db.getConnection().rollback();
				return false;
			}
			
			//Xóa phân bổ
			if (result == 2 || result == 1)
			{
				boolean kq = capNhatPhanBo();
				if (kq == false)
				{
					this.msg = "C1.0.3 Lỗi xóa khấu hao\n" + this.msg;
					this.db.getConnection().rollback();
					this.warning = true;
					return false;
				}
			}
			
			//Update trạng thái xuất dùng
			query = "update ERP_XUATDUNG set TRANGTHAI = 1, NGAYCHOT = '" + this.ngayChot + "' where TRANGTHAI = 0 and  PK_SEQ = " + this.id;
			int num = this.db.updateReturnInt(query);
			if (num == 0)
			{
				this.msg = "C1.7 Không thể chốt phiếu xuất dùng\n";
				this.db.getConnection().rollback();
				return false;
			}
			
			//Chạy kế toán
			query = 
				"select tk.PK_SEQ \n" + 
				"from ERP_XUATDUNG xd \n" + 
				"	inner join ERP_CONGCUDUNGCU cc on cc.PK_SEQ = xd.CCDC_FK \n" + 
				"	inner join Erp_LOAICCDC lcc on lcc.pk_seq = cc.LOAICCDC_FK \n" + 
				"	inner join ERP_TAIKHOANKT tk on tk.PK_SEQ = lcc.taikhoan_fk \n" + 
				"where xd.PK_SEQ = " + this.id + " \n";
			
			System.out.println("cau lenh lay tai khoan no: \n" + query + "\n===============================");
			
			ResultSet rs = this.db.get(query);
			String taiKhoanNo = null;
			
			if (rs != null)
			{
				if (rs.next())
				{
					taiKhoanNo = rs.getString("PK_SEQ");
				}
				rs.close();
			}
			
			if (taiKhoanNo == null || taiKhoanNo.trim().length() == 0)
			{
				this.msg = "C1.4 Không thể chốt phiếu xuất dùng: không tìm thấy tài khoản bên dưới CCDC \n";
				this.db.getConnection().rollback();
				return false;
			}
			
			Utility util = new Utility();
//			String nam = this.ngayChot.substring(0, 4);
//			String thang = this.ngayChot.substring(5, 7);
			String nam = this.ngayXuatDung.substring(0, 4);
			String thang = this.ngayXuatDung.substring(5, 7);
			CapnhatKT KT;
			for (Erp_VatTu vatTu : this.vatTuList)
			{
				query =
					"select isnull(tk1.PK_SEQ, tk2.PK_SEQ) as SOHIEUTAIKHOAN \n" + 
					"from ERP_SANPHAM sp \n" + 
					"	inner join ERP_LOAISANPHAM lsp on lsp.PK_SEQ = sp.LOAISANPHAM_FK \n" + 
					"	left join ERP_TAIKHOANKT tk1 on tk1.PK_SEQ = lsp.TAIKHOANKT_FK \n" +
					"	left join ERP_TAIKHOANKT tk2 on tk2.soHieuTaiKhoan = lsp.TAIKHOANKT_FK \n" +
					"where sp.PK_SEQ = " + vatTu.getSanPhamId();
				
				System.out.println("cau lenh lay tai khoan co: \n" + query + "\n======================================");
				
				String taiKhoanCo = null;
				rs = this.db.get(query);
				
				if (rs != null)
				{
					if (rs.next())
					{
						taiKhoanCo = rs.getString("SOHIEUTAIKHOAN");
					}
					rs.close();
				}
				
				if (taiKhoanCo == null || taiKhoanCo.trim().length() == 0)
				{
					this.msg = "C1.5 Không thể chốt phiếu xuất dùng: không tìm thấy tài khoản bên dưới vật tư \n";
					this.db.getConnection().rollback();
					return false;
				}
				
				String ngayChungTu = this.ngaySua;
//				String ngayGhiNhan = this.ngayChot;
				String ngayGhiNhan = this.ngayXuatDung;
				String loaiChungTu = "Xuất dùng Vật tư - CCDC";
				String co;
				String no = co = Double.toString(vatTu.getThanhTien());//
				
				String doiTuongNo = "CCDC";
				String maDoiTuongNo = this.getCcdcId();
				String loaiDoiTuongNo = "0";
				
				String doiTuongCo = "Sản phẩm";
				String maDoiTuongCo = vatTu.getSanPhamId();
				String loaiDoiTuongCo = "0";
				
				String donGia = Double.toString(vatTu.getDonGia());
				String soLuong = Double.toString(vatTu.getSoLuongTinh());
				
				String tienTeId = "100000";
				String tiGia = "1";
				KT = new CapnhatKT();
				KT.setSochungtu(this.id);
				KT.setSpId(vatTu.getSanPhamId());
				KT.setNam(nam);
				KT.setThang(thang);
				KT.setTaikhoanCO_fk(taiKhoanCo);
				KT.setTaikhoanNO_fk(taiKhoanNo);
				
				KT.setDOITUONG_CO(doiTuongCo);
				KT.setDOITUONG_NO(doiTuongNo);
				KT.setLoaichungtu(loaiChungTu);
				KT.setMADOITUONG_CO(maDoiTuongCo);
				KT.setMADOITUONG_NO(maDoiTuongNo);
				KT.setTIGIA_FKl("1");
				KT.setDONGIANT("0");
			    KT.setChiPhiId("NULL");
			    KT.setTIENTEGOC_FK(tienTeId);
			    KT.setSOLUONG(soLuong);	
			    KT.setDONGIA(donGia);		
			    KT.setNO(no);
				KT.setCO(co);
				KT.setTONGGIATRI(co);
				KT.setNgaychotnv(ngayGhiNhan);
				KT.setNgaychungtu(ngayChungTu);
				KT.setNgayghinhan(ngayGhiNhan);
				KT.setKhoanmuc("");
				String msg1=KT.CapNhatKeToan_Kho(util, db);
				if(msg1.length()> 0){
					this.msg = msg1;
					this.db.getConnection().rollback();
					return false;
				}
				
				/*UtilityKeToan kt = new UtilityKeToan(loaiChungTu, soChungTu, ngayChungTu, ngayGhiNhan, taiKhoanNo, taiKhoanCo, no, co, tiGia, tienTeId, thang, nam, loaiDoiTuongNo, maDoiTuongNo, loaiDoiTuongCo, maDoiTuongCo, "", "");
				kt.setTongGiaTri(no);
				kt.setTongGiaTriNT(no);
				kt.setMasp(vatTu.getSanPhamId());
				this.msg = kt.Update_TaiKhoanBySoHieu(db, "1");*/
				/*this.msg = util.Update_TaiKhoan_TheoSoHieu_DoiTuong_NoCo(this.congTyId, this.db, thang, nam, ngayChungTu, ngayGhiNhan
				, loaiChungTu, soChungTu, taiKhoanNo, taiKhoanCo, ""
				, no, co
				, doiTuongNo, maDoiTuongNo, loaiDoiTuongNo
				, doiTuongCo, maDoiTuongCo, loaiDoiTuongCo
				, soLuong, donGia, tienTeId, donGia, tiGia, no, no, "","","","");*/
				
//				util.Update_TaiKhoan_TheoSoHieu(db, thang, nam, this.ngayChot, this.ngayTao, "Xuất dùng Vật tư - CCDC", this.id, taiKhoanNo, taiKhoanCo, "", Double.toString(vatTu.getThanhTien()), Double.toString(vatTu.getThanhTien()), "", "", "0", "", "", "100000", "", "1", Double.toString(vatTu.getThanhTien()), Double.toString(vatTu.getThanhTien()), "");
//				this.msg = util.Update_TaiKhoan_TheoSoHieu( db, thang, nam, this.ngayChot, this.ngayTao, "Xuất dùng Vật tư - CCDC", this.id, taiKhoanNo, taiKhoanCo, "", 
//						Double.toString(vatTu.getThanhTien()), Double.toString(vatTu.getThanhTien()), "", "", "0", "", "", "100000", "", "1", Double.toString(vatTu.getThanhTien()), Double.toString(vatTu.getThanhTien()), "Tiền gốc" );
				if(this.msg.trim().length() > 0)
				{
					this.msg = "C1.6 Không thể chốt phiếu xuất dùng: không tìm thấy tài khoản bên dưới vật tư \n" + this.msg;
					System.out.println(this.msg);
					db.getConnection().rollback();
					return false;
				}
			}
			this.db.getConnection().commit();
				
		} catch (Exception e) {
			e.printStackTrace();
			this.msg = "C1 Không thể chốt phiếu xuất dùng";
			return false;
		}
		finally
		{
			try {
				this.db.getConnection().setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public void DbClose()
	{
		if (this.db != null)
			this.db.shutDown();
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCcdcId() {
		return ccdcId;
	}

	public void setCcdcId(String ccdcId) {
		this.ccdcId = ccdcId;
	}

	public String getTenCcdc() {
		return tenCcdc;
	}

	public void setTenCcdc(String tenCcdc) {
		this.tenCcdc = tenCcdc;
	}

	public String getGhiChu() {
		return ghiChu;
	}

	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
	}

	public String getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(String trangThai) {
		this.trangThai = trangThai;
	}

	public String getNgayTao() {
		return ngayTao;
	}

	public void setNgayTao(String ngayTao) {
		this.ngayTao = ngayTao;
	}

	public String getNgaySua() {
		return ngaySua;
	}

	public void setNgaySua(String ngaySua) {
		this.ngaySua = ngaySua;
	}

	public String getNguoiTao() {
		return nguoiTao;
	}

	public void setNguoiTao(String nguoiTao) {
		this.nguoiTao = nguoiTao;
	}

	public String getNguoiSua() {
		return nguoiSua;
	}

	public void setNguoiSua(String nguoiSua) {
		this.nguoiSua = nguoiSua;
	}

	public String getCongTyId() {
		return congTyId;
	}

	public void setCongTyId(String congTyId) {
		this.congTyId = congTyId;
	}

	public List<Erp_VatTu> getVatTuList() {
		return vatTuList;
	}

	public void setVatTuList(List<Erp_VatTu> vatTuList) {
		this.vatTuList = vatTuList;
	}

	public dbutils getDb() {
		return db;
	}

	public void setDb(dbutils db) {
		this.db = db;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}
	
	public List<Erp_Item> getCcdcList() {
		return ccdcList;
	}

	public void setCcdcList(List<Erp_Item> ccdcList) {
		this.ccdcList = ccdcList;
	}

	public List<Erp_Item> getKhoList() {
		return khoList;
	}

	public void setKhoList(List<Erp_Item> khoList) {
		this.khoList = khoList;
	}
	
	private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	public void setNgayChot(String ngayChot) {
		this.ngayChot = ngayChot;
	}

	public String getNgayChot() {
		return ngayChot;
	}

	public void setDinhKhoanList(List<DinhKhoanKeToan> dinhKhoanList) {
		this.dinhKhoanList = dinhKhoanList;
	}

	public List<DinhKhoanKeToan> getDinhKhoanList() {
		return dinhKhoanList;
	}

	public void setNgayXuatDung(String ngayXuatDung) {
		this.ngayXuatDung = ngayXuatDung;
	}

	public String getNgayXuatDung() {
		return ngayXuatDung;
	}
	
	public boolean isWarning() {
		return warning;
	}

	public void setWarning(boolean warning) {
		this.warning = warning;
	}
	
	public boolean getXoaKhauHao() {
		return xoaKhauHao;
	}

	public void setXoaKhauHao(boolean xoaKhauHao) {
		this.xoaKhauHao = xoaKhauHao;
	}
	
	public static String getLastDayOfMonth(String date)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date convertedDate;
		Calendar c = null;
		try {
			convertedDate = dateFormat.parse(date);
			c = Calendar.getInstance();
			c.setTime(convertedDate);
			c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		} catch (ParseException e) {
			e.printStackTrace();
			return "";
		}
		Date lastDayOfMonth = c.getTime();  
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		return sdf.format(lastDayOfMonth);
	}
}