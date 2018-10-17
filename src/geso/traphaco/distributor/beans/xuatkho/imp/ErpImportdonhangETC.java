package geso.traphaco.distributor.beans.xuatkho.imp;

import geso.traphaco.distributor.beans.xuatkho.IErpImportdonhangETC;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ErpImportdonhangETC implements IErpImportdonhangETC
{
	String userId;
	String id;
	
	String ngayyeucau;
	String ngaygiaohangGui;
	String ghichu;

	String msg;
	String trangthai;
	String khoNhanId;
	ResultSet khoNhanRs;
	
	String tinhthanhId;
	ResultSet tinhthanhRs;
	String quanhuyenId;
	ResultSet quanhuyenRs;
	String nvgnId;
	ResultSet nvgnRs;
	String nvbhId;
	ResultSet nvbhRs;
	
	String khId;
	ResultSet khRs;
	
	String ddhId;
	ResultSet ddhRs;

	String nppId;
	String nppTen;
	String sitecode;
	String xuatcho;
	
	String phanloai;
	
	String tungay;
	String denngay;
	
	String nhanvien_TuId;
	String nhanvien_DenId;
	String nhanvien_DenQL;
	
	String nhanvien_CCQLID;
	String nhanvien_CCQL;
	
	ResultSet nhanvienRs;
	
	String chanhxe;
	String dienthoai;
	String soluong;
	String donvi;
	
	String maFast;
	String tenkhachhang;
	String machungtu;
	String nguoitao;
	
	dbutils db;
	Utility util;
	
	public ErpImportdonhangETC()
	{
		this.id = "";
		this.ngayyeucau = getDateTime();
		this.ghichu = "";
		this.khoNhanId = "";
		this.khId = "";
		this.msg = "";
		this.trangthai = "0";
		this.ddhId = "";
		this.xuatcho = "";
		this.ngaygiaohangGui = "";
		this.tinhthanhId = "";
		this.quanhuyenId = "";
		this.nvbhId = "";
		this.nvgnId = "";
		
		this.phanloai = "";
		this.db = new dbutils();
		this.util = new Utility();
		
		this.tungay = "";
		this.denngay = "";
		
		this.chanhxe = "";
		this.dienthoai = "";
		this.soluong = "";
		this.donvi = "";
		this.nhanvien_TuId = "";
		this.nhanvien_DenId = "";
		this.nhanvien_DenQL = "1";
		this.nhanvien_CCQLID = "";
		this.nhanvien_CCQL = "";
		
		this.maFast = "";
		this.tenkhachhang = "";
		this.machungtu = "";
		this.nguoitao = "";
		
	}
	
	public ErpImportdonhangETC(String id)
	{
		this.id = id;
		this.ngayyeucau = getDateTime();
		this.ghichu = "";
		this.khoNhanId = "";
		this.khId = "";
		this.msg = "";
		this.trangthai = "0";
		this.ddhId = "";
		this.xuatcho = "";
		this.ngaygiaohangGui = "";
		this.tinhthanhId = "";
		this.quanhuyenId = "";
		this.nvbhId = "";
		this.nvgnId = "";
		
		this.phanloai = "";
		this.db = new dbutils();
		this.util = new Utility();
		
		this.tungay = "";
		this.denngay = "";
		
		this.chanhxe = "";
		this.dienthoai = "";
		this.soluong = "";
		this.donvi = "";
		this.nhanvien_TuId = "";
		this.nhanvien_DenId = "";
		this.nhanvien_DenQL = "1";
		this.nhanvien_CCQLID = "";
		this.nhanvien_CCQL = "";
		
		this.maFast = "";
		this.tenkhachhang = "";
		this.machungtu = "";
		this.nguoitao = "";
		
	}

	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}

	public String getId() 
	{
		return this.id;
	}

	public void setId(String Id) 
	{
		this.id = Id;
	}

	public String getNgayyeucau() 
	{
		return this.ngayyeucau;
	}

	public void setNgayyeucau(String ngayyeucau) 
	{
		this.ngayyeucau = ngayyeucau;
	}

	public String getKhoNhapId()
	{
		return this.khoNhanId;
	}

	public void setKhoNhapId(String khonhaptt) 
	{
		this.khoNhanId = khonhaptt;
	}

	public ResultSet getKhoNhapRs() 
	{
		return this.khoNhanRs;
	}

	public void setKhoNHapRs(ResultSet khonhapRs) 
	{
		this.khoNhanRs = khonhapRs;
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
		this.tinhthanhRs = db.get("select PK_SEQ, TEN from TINHTHANH where TRANGTHAI = '1'");
		String sql = "select PK_SEQ, TEN from QUANHUYEN where TRANGTHAI = '1'";
		if(this.tinhthanhId.trim().length() > 0)
			sql += " AND TINHTHANH_FK = '" + this.tinhthanhId + "' ";
		this.quanhuyenRs = db.get(sql);
		
		this.getNppInfo();
		this.khoNhanRs = db.get("select PK_SEQ, TEN from KHO where trangthai = '1' and pk_seq in " + this.util.quyen_kho(this.userId)     );
		
		this.nvbhRs = db.get("select PK_SEQ, TEN from DAIDIENKINHDOANH where TRANGTHAI = '1' AND pk_seq in ( select ddkd_fk from DAIDIENKINHDOANH_NPP where npp_fk = '" + this.nppId + "' ) ");
		this.nvgnRs = db.get("select PK_SEQ, TEN from NHANVIENGIAONHAN where TRANGTHAI = '1' AND npp_fk = '" + this.nppId + "' ");
		
		String query = "select PK_SEQ, MA + ' - ' + TEN as TEN from NHAPHANPHOI where TRANGTHAI = '1'  AND PK_SEQ != '" + this.nppId + "'  ";
		if(this.xuatcho.equals("1"))
			query = " select pk_seq, TEN from KHACHHANG where trangthai = '1' and NPP_FK = '" + this.nppId + "' and PK_SEQ in ( select khachhang_fk from KHACHHANG_KENHBANHANG where kbh_fk in ( select kbh_fk from NHOMKENH_KENHBANHANG where nk_fk = '100000' ) ) ";
		else if(this.xuatcho.equals("2"))
			query = " select pk_seq, TEN from KHACHHANG where trangthai = '1' and NPP_FK = '" + this.nppId + "' and PK_SEQ in ( select khachhang_fk from KHACHHANG_KENHBANHANG where kbh_fk in ( select kbh_fk from NHOMKENH_KENHBANHANG where nk_fk = '100001' ) ) ";
		
		if(this.tinhthanhId.trim().length() > 0)
			query += " and tinhthanh_fk = '" + this.tinhthanhId + "' ";
		if(this.quanhuyenId.trim().length() > 0)
			query += " and quanhuyen_fk = '" + this.quanhuyenId + "' ";
		
		this.khRs = db.get(query);
		
		query = "select PK_SEQ, TEN + ' ' + isnull(DIENTHOAI, 'NA') + ' ' + isnull(EMAIL, 'NA') as diengiai " + 
				" from NHANVIEN where TRANGTHAI = '1' order by TEN asc";
		System.out.println("::: LAY NHAN VIEN: " + query );
		this.nhanvienRs = db.getScrol(query);
		
		if( ( this.khId.trim().length() > 0 || this.tinhthanhId.trim().length() > 0 || this.quanhuyenId.trim().length() > 0 
				|| this.nvbhId.trim().length() > 0 || this.nvgnId.trim().length() > 0
				|| this.maFast.trim().length() > 0 || this.tenkhachhang.trim().length() > 0 || this.machungtu.trim().length() > 0 || this.nguoitao.trim().length() > 0 ) )
		{
			query = "select a.PK_SEQ, a.machungtu, ISNULL( b.maFAST, c.MaFAST ) as maKH, ISNULL( b.TEN, c.TEN ) as tenKH, a.NgayDonHang, ISNULL(d.ten, e.ten) as tinhthanh, ISNULL( SONGAYVANCHUYEN, 0 ) as songayvanchuyen "+
					 "from ERP_DONDATHANGNPP a left join KHACHHANG b on a.KHACHHANG_FK = b.PK_SEQ  "+
					 "		left join NHAPHANPHOI c on a.NPP_DAT_FK = c.PK_SEQ "+
					 "		left join TINHTHANH d on b.TINHTHANH_FK = d.PK_SEQ "+
					 "		left join TINHTHANH e on c.TINHTHANH_FK = e.PK_SEQ "+
					 "where a.TRANGTHAI in ( 2, 4 ) and a.npp_fk = '" + this.nppId + "' ";
			
			/*if( this.tungay.trim().length() > 0 )
				query += " AND a.NGAYDONHANG >= '" + this.tungay + "' ";
			if( this.denngay.trim().length() > 0 )
				query += " AND a.NGAYDONHANG <= '" + this.denngay + "'  ";*/
			
			//Chỉ lấy đơn loại bán E và O
			//query += " AND a.loaidonhang in ( 1, 2 ) ";
			
			if( this.xuatcho.trim().length() > 0 )
				query += " AND a.loaidonhang = '" + this.xuatcho + "'  ";
			
			if(this.khId.trim().length() > 0)
			{
				if(this.xuatcho.equals("0")) //XUAT CHO DOI TAC
					query += " and a.NPP_DAT_FK = '" + this.khId + "' ";
				else
					query += " and a.KHACHHANG_FK = '" + this.khId + "' ";
			}
			
			query += " AND a.pk_seq not in ( select ddh_fk from ERP_GUISMSTDV_DDH where guisms_fk in ( select pk_seq from ERP_GUISMSTDV where npp_fk = '" + this.nppId + "' and trangthai != 2 and pk_seq != '" + ( this.id.trim().length() > 0 ? this.id : "1" ) + "' ) ) ";
			
			if(this.nvbhId.trim().length() > 0)
				query += " AND a.DDKD_FK = '" + this.nvbhId + "' ";
			if(this.nvgnId.trim().length() > 0 )
			{
				if(!this.xuatcho.equals("0")) 
					query += " and a.KHACHHANG_FK in ( select KHACHHANG_FK from NVGN_KH where NVGN_FK = '" + this.nvgnId + "' ) ";
			}
			
			if(this.xuatcho.equals("0"))
			{
				if( this.tinhthanhId.trim().length() > 0 )
					query += " and a.NPP_DAT_FK in ( select PK_SEQ from NHAPHANPHOI where TINHTHANH_FK = '" + this.tinhthanhId + "' ) ";
				if( this.quanhuyenId.trim().length() > 0 )
					query += " and a.NPP_DAT_FK in ( select PK_SEQ from NHAPHANPHOI where QUANHUYEN_FK = '" + this.quanhuyenId + "' ) ";
			}
			else
			{
				if( this.tinhthanhId.trim().length() > 0 )
					query += " and a.KHACHHANG_FK in ( select PK_SEQ from KHACHHANG where NPP_FK = '" + this.nppId + "' and TINHTHANH_FK = '" + this.tinhthanhId + "' ) ";
				if( this.quanhuyenId.trim().length() > 0 )
					query += " and a.KHACHHANG_FK in ( select PK_SEQ from KHACHHANG where NPP_FK = '" + this.nppId + "' and QUANHUYEN_FK = '" + this.quanhuyenId + "' ) ";
			}
			
			if( this.maFast.trim().length() > 0 )
				query += " AND b.maFAST like N'%" + this.maFast + "%' ";
			if( this.tenkhachhang.trim().length() > 0 )
				query += " AND b.timkiem like N'%" + util.replaceAEIOU(this.tenkhachhang) + "%' ";
			if( this.machungtu.trim().length() > 0 )
				query += " AND a.machungtu like '%" + this.machungtu + "%' ";
			if( this.nguoitao.trim().length() > 0 )
				query += " AND a.nguoitao in ( select pk_seq from NHANVIEN where dbo.ftBoDau(ten) like N'%" + util.replaceAEIOU(this.nguoitao) + "%' ) ";
			
			
			System.out.println("----LAY DON DAT HANG: " + query );
			this.ddhRs = db.get(query);
		}
		else //Mặc định load ra các đơn tạo trong ngày
		{
			this.ddhId = "";
			
			query = "select a.PK_SEQ, a.machungtu, ISNULL( b.maFAST, c.MaFAST ) as maKH, ISNULL( b.TEN, c.TEN ) as tenKH, a.NgayDonHang, ISNULL(d.ten, e.ten) as tinhthanh "+
					 "from ERP_DONDATHANGNPP a left join KHACHHANG b on a.KHACHHANG_FK = b.PK_SEQ  "+
					 "		left join NHAPHANPHOI c on a.NPP_DAT_FK = c.PK_SEQ "+
					 "		left join TINHTHANH d on b.TINHTHANH_FK = d.PK_SEQ "+
					 "		left join TINHTHANH e on c.TINHTHANH_FK = e.PK_SEQ "+
					 "where a.TRANGTHAI in ( 2, 4 ) and a.npp_fk = '" + this.nppId + "' ";
			//query += " AND a.loaidonhang in ( 1, 2 ) ";
			query += " AND a.ngaydonhang = '" + this.getDateTime() + "' ";
			
			System.out.println("----LAY DON DAT HANG MAC DINH: " + query );
			this.ddhRs = db.get(query);
		}
		
	}

	public void init() 
	{
		String query =  "select ngaygiaohang, ngaydukienHANGDEN, ngayvanchuyen, xuatcho, ghichu, chanhxe, dienthoai, soluong, donvitinh, " + 
						" 	tinhthanh_fk, quanhuyen_fk, nvbh_fk, nvgn_fk, nhanvien_tuId, nhanvien_denId, nhanvien_denQL, trangthai, makhachhang, tenkhachhang, machungtuSEARCH, nguoitaoSEARCH " +
						"from ERP_GUISMSTDV where pk_seq = '" + this.id + "'";
		System.out.println("____INIT NHAP KHO: " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					this.ngayyeucau = rs.getString("ngaygiaohang");
					this.tungay = rs.getString("ngaydukienHANGDEN");
					this.denngay = rs.getString("ngayvanchuyen");
					this.xuatcho = rs.getString("xuatcho") == null ? "" : rs.getString("xuatcho");
					this.ghichu = rs.getString("ghichu");

					this.chanhxe = rs.getString("chanhxe");
					this.dienthoai = rs.getString("dienthoai");
					this.soluong = rs.getString("soluong");
					this.donvi = rs.getString("donvitinh");
					
					this.nhanvien_TuId = rs.getString("nhanvien_tuId");
					this.nhanvien_DenId = rs.getString("nhanvien_denId");
					this.nhanvien_DenQL = rs.getString("nhanvien_denQL");
					
					this.trangthai = rs.getString("trangthai");
					
					this.tinhthanhId = rs.getString("tinhthanh_fk") == null ? "" : rs.getString("tinhthanh_fk");
					this.quanhuyenId = rs.getString("quanhuyen_fk") == null ? "" : rs.getString("quanhuyen_fk");
					
					this.nvbhId = rs.getString("nvbh_fk") == null ? "" : rs.getString("nvbh_fk");
					this.nvgnId = rs.getString("nvgn_fk") == null ? "" : rs.getString("nvgn_fk");
					
					this.maFast = rs.getString("makhachhang");
					this.tenkhachhang = rs.getString("tenkhachhang");
					this.machungtu = rs.getString("machungtuSEARCH");
					this.nguoitao = rs.getString("nguoitaoSEARCH");
				}
				rs.close();
				
				//INIT DDH
				query = "select ddh_fk from ERP_GUISMSTDV_DDH where guisms_fk = '" + this.id + "' ";
				rs = db.get(query);
				String ddhID = "";
				while(rs.next())
				{
					ddhID += rs.getString("ddh_fk") + ",";
				}
				rs.close();
				
				if(ddhID.trim().length() > 0)
				{
					this.ddhId = ddhID.substring(0, ddhID.length() - 1);
				}
				
				query = "select nhanvien_fk, nhanvien_CCQL from ERP_GUISMSTDV_CC where guisms_fk = '" + this.id + "' ";
				rs = db.get(query);
				ddhID = "";
				while(rs.next())
				{
					ddhID += rs.getString("nhanvien_fk") + ",";
					this.nhanvien_CCQL = rs.getString("nhanvien_CCQL");
				}
				rs.close();
				
				if(ddhID.trim().length() > 0)
				{
					this.nhanvien_CCQLID = ddhID.substring(0, ddhID.length() - 1);
				}
				
			} 
			catch (Exception e) 
			{
				System.out.println("---LOI INIT: " + e.getMessage());
				e.printStackTrace();
			}
		}
		
		this.createRs();
		
	}

	public void DBclose() {
		
		try{
			
			if(khoNhanRs!=null){
				khoNhanRs.close();
			}
			
			this.db.shutDown();
			
		}catch(Exception er){
			
		}
	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}

	public String getGhichu() {
		
		return this.ghichu;
	}

	public void setGhichu(String ghichu) {
		
		this.ghichu = ghichu;
	}

	
	public String getNppId() {
		
		return this.nppId;
	}

	
	public void setNppId(String khId) {
		
		this.nppId = khId;
	}

	
	public ResultSet getKhRs() {
		
		return this.khRs;
	}

	
	public void setKhRs(ResultSet khRs) {
		
		this.khRs = khRs;
	}

	
	public String getDondathangId() {
		
		return this.ddhId;
	}

	
	public void setDondathangId(String kbhId) {
		
		this.ddhId = kbhId;
	}

	
	public ResultSet getDondathangRs() {
		
		return this.ddhRs;
	}

	
	public void setDondathangRs(ResultSet ddhRs) {
		
		this.ddhRs = ddhRs;
	}
	
	public ResultSet getSoloTheoSp(String spIds, String tongluong, String scheme )
	{
		String query = "select sum(soluong) as tuDEXUAT, SOLO, NGAYHETHAN, 0 as AVAILABLE " +
					   "from ERP_DONDATHANGNPP_SANPHAM_CHITIET " + 
					   " where sanpham_fk = '" + spIds + "' and dondathang_fk in ( " + this.ddhId + " ) and ltrim(rtrim(scheme)) = '" + scheme + "' group by SOLO, NGAYHETHAN ";
		System.out.println("---LAY SO LO OLD:: " + query);
		return db.get(query);
	}

	public ResultSet getSoloTheoSpOLD(String spIds, String tongluong)
	{
		String query = "select soluong, SOLO, NGAYHETHAN " +
					   "from ERP_DONDATHANGNPP_SANPHAM_CHITIET where sanpham_fk = '" + spIds + "' and dondathang_fk = ( select ddh_fk from ERP_SOXUATHANGNPP where PK_SEQ = '" + this.id + "' ) ";
		System.out.println("---LAY SO LO OLD:: " + query);
		return db.get(query);
	}
	

	public boolean create( String[] machungtu ) 
	{
		if(this.ngayyeucau.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày yêu cầu";
			return false;
		}
		
		if(this.nhanvien_TuId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn nhân viên gửi";
			return false;
		}
		
		if(this.nhanvien_DenId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn nhân viên nhận";
			return false;
		}
		
		if(this.ddhId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn đơn đặt hàng";
			return false;
		}
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String khachhang_fk = "NULL";
			String npp_dat_fk = "NULL";
			if(this.xuatcho.equals("0"))
				npp_dat_fk = this.khId.trim().length() <= 0 ? "NULL" : this.khId;
			else
				khachhang_fk = this.khId.trim().length() <= 0 ? "NULL" : this.khId;
			
			String xuatcho = this.xuatcho.trim().length() <= 0 ? "NULL" : this.xuatcho;
			String tinhthanh_fk = this.tinhthanhId.trim().length() <= 0 ? "NULL" : this.tinhthanhId;
			String quanhuyen_fk = this.quanhuyenId.trim().length() <= 0 ? "NULL" : this.quanhuyenId;
			String nvbh_fk = this.nvbhId.trim().length() <= 0 ? "NULL" : this.nvbhId;
			String nvgn_fk = this.nvgnId.trim().length() <= 0 ? "NULL" : this.nvgnId;
			
			String query = " insert ERP_GUISMSTDV(ngaygiaohang, chanhxe, dienthoai, soluong, donvitinh, ghichu, nhanvien_tuId, nhanvien_denId, nhanvien_denQL, trangthai, npp_fk, xuatcho, ngaytao, nguoitao, ngaysua, nguoisua, tinhthanh_fk, quanhuyen_fk, nvbh_fk, nvgn_fk, ngaydukienHANGDEN, ngayvanchuyen, makhachhang, tenkhachhang, machungtuSEARCH, nguoitaoSEARCH) " +
						   " values ( '" + this.ngayyeucau + "', N'" + this.chanhxe + "', N'" + this.dienthoai + "', N'" + this.soluong + "', N'" + this.donvi + "', N'" + this.ghichu + "', '" + this.nhanvien_TuId + "', '" + this.nhanvien_DenId + "', '" + this.nhanvien_DenQL + "', '0', '" + this.nppId + "', " + xuatcho + ", " +
						   		" '" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "', " + tinhthanh_fk + ", " + quanhuyen_fk + ", " + nvbh_fk + ", " + nvgn_fk + ", '" + this.tungay + "', '" + this.denngay + "', N'" + this.maFast + "', N'" + this.tenkhachhang + "', N'" + this.machungtu + "', N'" + this.nguoitao + "' ) ";
			
			System.out.println("1.Insert ERP_GUISMSTDV: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_GUISMSTDV " + query;
				db.getConnection().rollback();
				return false;
			}
			
			//LAY ID
			ResultSet rsDDH = db.get("select scope_identity() as ID ");
			if(rsDDH.next())
			{
				this.id = rsDDH.getString("ID");
			}
			rsDDH.close();
			
			query = "Insert ERP_GUISMSTDV_DDH(guisms_fk, ddh_fk) " +
					"select '" + this.id + "', pk_seq from ERP_DONDATHANGNPP where pk_seq in ( " + this.ddhId + " )  ";
			System.out.println("2.chen ERP_GUISMSTDV_DDH: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_GUISMSTDV_DDH " + query;
				db.getConnection().rollback();
				return false;
			}
			
			if( this.nhanvien_CCQLID.trim().length() > 0 )
			{
				query = "Insert ERP_GUISMSTDV_CC(guisms_fk, nhanvien_fk, nhanvien_CCQL) " +
						"select '" + this.id + "', pk_seq, '" + this.nhanvien_CCQL + "' from NHANVIEN where pk_seq in ( " + this.nhanvien_CCQLID + " )  ";
				System.out.println("3.chen ERP_GUISMSTDV_CC: " + query);
				if(!db.update(query))
				{
					this.msg = "Không thể tạo mới ERP_GUISMSTDV_CC " + query;
					db.getConnection().rollback();
					return false;
				}
			}
			
			//CAP NHAT SO NGAY VAN CHUYEN TRONG DLN
			if( this.denngay.trim().length() > 0 )
			{
				query = "UPDATE KHACHHANG set songayvanchuyen = '" + this.denngay.replaceAll(",", "") + "' " + 
						" where pk_seq in ( select PK_SEQ from KHACHHANG " +
						"					where PK_SEQ in  ( select khachhang_FK from ERP_DONDATHANGNPP where KHACHHANG_FK is not null and PK_SEQ in ( select top(1) DDH_FK from ERP_GUISMSTDV_DDH where guisms_fk = '" + this.id + "' order by stt asc ) ) ) ";
				if( !db.update(query) )
				{
					this.msg = "Không thể tạo mới ERP_GUISMSTDV " + query;
					db.getConnection().rollback();
					return false;
				}
			}
			
			//LAY MA CHUNG TU
			String _mact = "";
			
			query = " select b.machungtu from ERP_GUISMSTDV_DDH a inner join ERP_DONDATHANGNPP b on a.ddh_fk = b.pk_seq where a.guisms_fk = '" + this.id + "' ";
			ResultSet rs = db.get(query);
			while( rs.next() )
			{
				_mact += rs.getString("machungtu") + ";";
			}
			rs.close();
			
			String _id = this.id;
			while( _id.trim().length() <= 4 )
				_id = "0" + _id;
			
			String smsMSG = "SPXN:" + this.ngayyeucau.substring(0, 4) + this.ngayyeucau.substring(5, 7) + _id;
			smsMSG += ". " + util.replaceAEIOU(this.ghichu).replaceAll("-", " ");
			smsMSG += ". NG:" + this.ngayyeucau;
			smsMSG += ". ND:" + this.tungay;
			smsMSG += ". Xe:" + util.replaceAEIOU(this.chanhxe).replaceAll("-", " ");
			smsMSG += ". DT:" + this.dienthoai;
			smsMSG += ". So" + util.replaceAEIOU(this.donvi).replaceAll("-", " ") + ": " + this.soluong;
			smsMSG += ". DH: " + _mact.substring(0, _mact.length() - 1);
			
			System.out.println("::: SMS MSG: " + smsMSG );
			if( smsMSG.trim().length() >= 160 )
			{
				this.msg = "Số ký tự trong nội dung gửi SMS ( " + smsMSG.length() + " ) không được vượt quá 160 ký tự";
				db.getConnection().rollback();
				return false;
			}
			
			query = "update ERP_GUISMSTDV set machungtu = SUBSTRING('" + this.ngayyeucau + "', 0, 5) + SUBSTRING('" + this.ngayyeucau + "', 6, 2) + '" + _id + "', smsMSG = '" + smsMSG + "' " + 
					" where pk_seq = '" + this.id + "' ";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_GUISMSTDV " + query;
				db.getConnection().rollback();
				return false;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			db.update("rollback");
			this.msg = "Exception: " + e.getMessage();
			return false;
		}
		
		return true;
	}

	
	public boolean update( String[] machungtu ) 
	{
		if(this.ngayyeucau.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày yêu cầu";
			return false;
		}
		
		if(this.nhanvien_TuId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn nhân viên gửi";
			return false;
		}
		
		if(this.nhanvien_DenId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn nhân viên nhận";
			return false;
		}
		
		if(this.ddhId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn đơn đặt hàng";
			return false;
		}
		
		try
		{
			db.getConnection().setAutoCommit(false);
		
			String khachhang_fk = "NULL";
			String npp_dat_fk = "NULL";
			if(this.xuatcho.equals("0"))
				npp_dat_fk = this.khId.trim().length() <= 0 ? "NULL" : this.khId;
			else
				khachhang_fk = this.khId.trim().length() <= 0 ? "NULL" : this.khId;
			
			String xuatcho = this.xuatcho.trim().length() <= 0 ? "NULL" : this.xuatcho;
			String tinhthanh_fk = this.tinhthanhId.trim().length() <= 0 ? "NULL" : this.tinhthanhId;
			String quanhuyen_fk = this.quanhuyenId.trim().length() <= 0 ? "NULL" : this.quanhuyenId;
			String nvbh_fk = this.nvbhId.trim().length() <= 0 ? "NULL" : this.nvbhId;
			String nvgn_fk = this.nvgnId.trim().length() <= 0 ? "NULL" : this.nvgnId;
			
			String query = " Update ERP_GUISMSTDV set ngaygiaohang = '" + this.ngayyeucau + "', ghichu = N'" + this.ghichu + "', npp_fk = '" + this.nppId + "', " +
						   "	xuatcho = " + xuatcho + ", chanhxe = N'" + this.chanhxe + "', dienthoai = '" + this.dienthoai + "', soluong = '" + this.soluong + "', donvitinh = N'" + this.donvi + "', nhanvien_tuId = '" + this.nhanvien_TuId + "', nhanvien_denId = '" + this.nhanvien_DenId + "', nhanvien_denQL = '" + this.nhanvien_DenQL + "', " +
						   "	ngaysua = '" + this.getDateTime() + "', nguoisua = '" + this.userId + "', tinhthanh_fk = " + tinhthanh_fk + ", quanhuyen_fk = " + quanhuyen_fk + ", nvbh_fk = " + nvbh_fk + ", nvgn_fk = " + nvgn_fk + ", ngaydukienHANGDEN = '" + this.tungay + "', ngayvanchuyen = '" + this.denngay + "', " + 
						   "	makhachhang = N'" + this.maFast + "', tenkhachhang = N'" + this.tenkhachhang + "', machungtuSEARCH = N'" + this.machungtu + "', nguoitaoSEARCH = N'" + this.nguoitao + "'	" +
						   " where pk_seq = '" + this.id + "' ";
			
			System.out.println("1.Update ERP_GUISMSTDV: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_GUISMSTDV " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_GUISMSTDV_DDH where guisms_fk = '" + this.id + "' ";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_GUISMSTDV_DDH " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_GUISMSTDV_CC where guisms_fk = '" + this.id + "' ";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_GUISMSTDV_CC " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "Insert ERP_GUISMSTDV_DDH(guisms_fk, ddh_fk) " +
					"select '" + this.id + "', pk_seq from ERP_DONDATHANGNPP where pk_seq in ( " + this.ddhId + " )  ";
			System.out.println("2.chen ERP_GUISMSTDV_DDH: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_GUISMSTDV_DDH " + query;
				db.getConnection().rollback();
				return false;
			}
			
			if( this.nhanvien_CCQLID.trim().length() > 0 )
			{
				query = "Insert ERP_GUISMSTDV_CC(guisms_fk, nhanvien_fk, nhanvien_CCQL) " +
						"select '" + this.id + "', pk_seq, '" + this.nhanvien_CCQL + "' from NHANVIEN where pk_seq in ( " + this.nhanvien_CCQLID + " )  ";
				System.out.println("3.chen ERP_GUISMSTDV_CC: " + query);
				if(!db.update(query))
				{
					this.msg = "Không thể tạo mới ERP_GUISMSTDV_CC " + query;
					db.getConnection().rollback();
					return false;
				}
			}
			
			//CAP NHAT SO NGAY VAN CHUYEN TRONG DLN
			if( this.denngay.trim().length() > 0 )
			{
				query = "UPDATE KHACHHANG set songayvanchuyen = '" + this.denngay.replaceAll(",", "") + "' " + 
						" where pk_seq in ( select PK_SEQ from KHACHHANG " +
						"					where PK_SEQ in  ( select khachhang_FK from ERP_DONDATHANGNPP where KHACHHANG_FK is not null and PK_SEQ in ( select top(1) DDH_FK from ERP_GUISMSTDV_DDH where guisms_fk = '" + this.id + "' order by stt asc ) ) ) ";
				System.out.println("::: CAP NHAT NGAY VC: " + query );
				if( !db.update(query) )
				{
					this.msg = "Không thể tạo mới ERP_GUISMSTDV " + query;
					db.getConnection().rollback();
					return false;
				}
			}
			
			//LAY MA CHUNG TU
			String _mact = "";
			
			query = " select b.machungtu from ERP_GUISMSTDV_DDH a inner join ERP_DONDATHANGNPP b on a.ddh_fk = b.pk_seq where a.guisms_fk = '" + this.id + "' ";
			ResultSet rs = db.get(query);
			while( rs.next() )
			{
				_mact += rs.getString("machungtu") + ";";
			}
			rs.close();
			
			String _id = this.id;
			while( _id.trim().length() <= 4 )
				_id = "0" + _id;
			
			String smsMSG = "SPXN:" + this.ngayyeucau.substring(0, 4) + this.ngayyeucau.substring(5, 7) + _id;
			smsMSG += ". " + util.replaceAEIOU(this.ghichu).replaceAll("-", " ");
			smsMSG += ". NG:" + this.ngayyeucau;
			smsMSG += ". ND:" + this.tungay;
			smsMSG += ". Xe:" + util.replaceAEIOU(this.chanhxe).replaceAll("-", " ");
			smsMSG += ". DT:" + this.dienthoai;
			smsMSG += ". So" + util.replaceAEIOU(this.donvi).replaceAll("-", " ") + ": " + this.soluong;
			smsMSG += ". DH: " + _mact.substring(0, _mact.length() - 1);
			
			System.out.println("::: SMS MSG: " + smsMSG );
			if( smsMSG.trim().length() >= 160 )
			{
				this.msg = "Số ký tự trong nội dung gửi SMS ( " + smsMSG.length() + " ) không được vượt quá 160 ký tự";
				db.getConnection().rollback();
				return false;
			}
			
			query = "update ERP_GUISMSTDV set machungtu = SUBSTRING('" + this.ngayyeucau + "', 0, 5) + SUBSTRING('" + this.ngayyeucau + "', 6, 2) + '" + _id + "', smsMSG = '" + smsMSG + "' " + 
					" where pk_seq = '" + this.id + "' ";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_GUISMSTDV " + query;
				db.getConnection().rollback();
				return false;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			db.update("rollback");
			this.msg = "Exception: " + e.getMessage();
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	public String getKhId() 
	{
		return this.khId;
	}

	public void setKhId(String khId) 
	{
		this.khId = khId;
	}
	
	public String getNppTen() 
	{
		return this.nppTen;
	}
	
	public void setNppTen(String nppTen) 
	{
		this.nppTen = nppTen;
	}
	
	public String getSitecode() 
	{
		return this.sitecode;
	}

	public void setSitecode(String sitecode) 
	{
		this.sitecode = sitecode;
	}
	
	private void getNppInfo()
	{		
		//Phien ban moi
		geso.traphaco.distributor.util.Utility util=new geso.traphaco.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();
		//this.dangnhap = util.getDangNhap();
		this.sitecode=util.getSitecode();
	}

	public String getXuatcho() {

		return this.xuatcho;
	}

	public void setXuatcho(String xuatcho) {
		
		this.xuatcho = xuatcho;
	}

	public String getNgaygiaohanggui() {

		return this.ngaygiaohangGui;
	}


	public void setNgaygiaohanggui(String ngaygiaohanggui) {
		
		this.ngaygiaohangGui = ngaygiaohanggui;
	}

	public String getPhanloai() 
	{
		return this.phanloai;
	}

	public void setPhanloai(String phanloai) 
	{
		this.phanloai = phanloai;
	}

	
	public String getTinhthanhId() {
		
		return this.tinhthanhId;
	}

	
	public void setTinhthanhId(String tinhthanhId) {
		
		this.tinhthanhId = tinhthanhId;
	}

	
	public ResultSet getTinhthanhRs() {
		
		return this.tinhthanhRs;
	}

	
	public void setTinhthanhRs(ResultSet tinhthanhRs) {
		
		this.tinhthanhRs = tinhthanhRs;
	}

	
	public String getQuanhuyenId() {
		
		return this.quanhuyenId;
	}

	
	public void setQuanhuyenId(String quanhuyenId) {
		
		this.quanhuyenId = quanhuyenId;
	}

	
	public ResultSet getQuanhuyenRs() {
		
		return this.quanhuyenRs;
	}

	
	public void setQuanhuyenRs(ResultSet qunahuyenRs) {
		
		this.quanhuyenRs = qunahuyenRs;
	}

	
	public String getNVGNId() {
		
		return this.nvgnId;
	}

	
	public void setNVGNId(String nvgnId) {
		
		this.nvgnId = nvgnId;
	}

	
	public ResultSet getNVGNRs() {
		
		return this.nvgnRs;
	}

	
	public void setNVGNRs(ResultSet nvgnRs) {
		
		this.nvgnRs = nvgnRs;
	}

	
	public String getNVBHId() {
		
		return this.nvbhId;
	}

	
	public void setNVBHId(String nvbhId) {
		
		this.nvbhId = nvbhId;
	}

	
	public ResultSet getNVBHRs() {
		
		return this.nvbhRs;
	}

	
	public void setNVBHRs(ResultSet nvbhRs) {
		
		this.nvbhRs = nvbhRs;
	}

	
	public String getTungay() {
		
		return this.tungay;
	}

	
	public void setTungay(String tungay) {
		
		this.tungay = tungay;
	}

	
	public String getDenngay() {
		
		return this.denngay;
	}

	
	public void setDenngay(String denngay) {
		
		this.denngay = denngay;
	}

	
	public ResultSet getNhanvienRs() {
		
		return this.nhanvienRs;
	}

	
	public void setNhanvienRs(ResultSet nvRs) {
		
		this.nhanvienRs = nvRs;
	}

	
	public String getChanhxe() {
		
		return this.chanhxe;
	}

	
	public void setChanhxe(String chanhxe) {
		
		this.chanhxe = chanhxe;
	}

	
	public String getDienthoai() {
		
		return this.dienthoai;
	}

	
	public void setDienthoai(String dienthoai) {
		
		this.dienthoai = dienthoai;
	}

	
	public String getSoluong() {
		
		return this.soluong;
	}

	
	public void setSoluong(String soluong) {
		
		this.soluong = soluong;
	}

	
	public String getDonvi() {
		
		return this.donvi;
	}

	
	public void setDonvi(String donvi) {
		
		this.donvi = donvi;
	}

	
	public String getNhanvien_TuId() {
		
		//return this.nhanvien_TuId;
		
		//Lúc nào cũng là user đăng nhập và không được sửa
		return this.userId;
	}

	
	public void setNhanvien_TuId(String nvTuId) {
		
		this.nhanvien_TuId = nvTuId;
	}

	
	public String getNhanvien_DenId() {
		
		return this.nhanvien_DenId;
	}

	
	public void setNhanvien_DenId(String nvDenId) {
		
		this.nhanvien_DenId = nvDenId;
	}


	public String getNhanvien_DenQL() {
	
		return this.nhanvien_DenQL;
	}


	public void setNhanvien_DenQL(String nvDenQl) {
		
		this.nhanvien_DenQL = nvDenQl;
	}

	
	public String getNhanvien_CCQLId() {
		
		return this.nhanvien_CCQLID;
	}

	
	public void setNhanvien_CCQLId(String nvDenId) {
		
		this.nhanvien_CCQLID = nvDenId;
	}

	
	public String getNhanvien_CCQL() {
		
		return this.nhanvien_CCQL;
	}

	
	public void setNhanvien_CCQL(String nvDenQl) {
		
		this.nhanvien_CCQL = nvDenQl;
	}

	
	public String getMaFast() {
		
		return this.maFast;
	}

	
	public void setMaFast(String mafast) {
		
		this.maFast = mafast;
	}

	
	public String getTenkhachhang() {
		
		return this.tenkhachhang;
	}

	
	public void setTenkhachhang(String tenkh) {
		
		this.tenkhachhang = tenkh;
	}

	
	public String getMachungtu() {
		
		return this.machungtu;
	}

	
	public void setMachungtu(String machungtu) {
		
		this.machungtu = machungtu;
	}

	
	public String getNguoitaodon() {
		
		return this.nguoitao;
	}

	
	public void setNguoitaodon(String nguoitaodon) {
		
		this.nguoitao = nguoitaodon;
	}

		
}
