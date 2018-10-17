package geso.traphaco.erp.beans.nhacungcap.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Erp_Item;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.nhacungcap.IErpNhaCungCap;
import geso.traphaco.erp.util.UtilitySyn;;

public class ErpNhaCungCap extends Phan_Trang implements IErpNhaCungCap
{
	private static final long serialVersionUID = -7173066606265660920L;
	String Id, UserId, Ten, DiaChi_NCC, DienThoai_NCC, LoaiNCC, NganHang, ChiNhanh, Email, TaiKhoan, MST, ThoiHanNo,
			HanMucNo, Ma, NguoiLienHe, TrangThai, DienThoai_NLH, Email_NLH, NguoiMuaHang, Msg, SoTaiKhoan, CongTy;
	ResultSet CongTyRs, NganHangRs, ChiNhanhRs, TaiKhoanRs, NhaCungCapRs, LoaiNCC_Rs, TaikhoanKQ_Rs;
	dbutils db;
	
	ResultSet spNgiacongRs, khoNlRs;
	String spNgcIds, khoId;
	String quanlyCN;
	
	ResultSet loaigiamuaRs;
	String loaigiamua;
	String fax;
    
	String duyet;
	String is_khuchexuat;
	String chixem;
	String muctindung ="", taikhoankq = "";
	private int soItems;
	private String nppId;
	String nhTen;
	String nhId;
	String TenCN;
	String CNId;
	private List<Erp_Item> nppList;
	
	public ErpNhaCungCap()
	{
		this.Id = "";
		this.UserId = "";
		this.Ten = "";
		this.DiaChi_NCC = "";
		this.DienThoai_NCC = "";
		this.LoaiNCC = "";
		this.NganHang = "";
		this.ChiNhanh = "";
		this.Email = "";
		this.TaiKhoan = "";
		this.MST = "";
		this.ThoiHanNo = "";
		this.HanMucNo = "";
		this.Ma = "";
		this.NguoiLienHe = "";
		this.TrangThai = "1";
		this.DienThoai_NLH = "";
		this.Email_NLH = "";
		this.NguoiMuaHang = "";
		this.Msg = "";
		this.SoTaiKhoan = "";
		this.CongTy = "";
		this.loaigiamua = "";
		this.fax = "";
		this.khoId = "";
		this.spNgcIds = "";
		this.quanlyCN = "1";
		this.duyet = "0";
		this.is_khuchexuat="";
		this.chixem = "0";
		this.soItems = 25;
		this.taikhoankq = "";
		this.nhTen="";
		this.nppId = "1" ;
		this.nppList = new ArrayList<Erp_Item>();
		this.nhId="";
		this.CNId="";
		this.TenCN="";
		this.db = new dbutils();
	}

	public ErpNhaCungCap(String id)
	{
		this.Id = id;
		this.UserId = "";
		this.Ten = "";
		this.DiaChi_NCC = "";
		this.DienThoai_NCC = "";
		this.LoaiNCC = "";
		this.NganHang = "";
		this.ChiNhanh = "";
		this.Email = "";
		this.TaiKhoan = "";
		this.MST = "";
		this.ThoiHanNo = "";
		this.HanMucNo = "";
		this.Ma = "";
		this.NguoiLienHe = "";
		this.TrangThai = "1";
		this.DienThoai_NLH = "";
		this.Email_NLH = "";
		this.NguoiMuaHang = "";
		this.Msg = "";
		this.SoTaiKhoan = "";
		this.CongTy = "";
		this.loaigiamua = "";
		this.fax = "";
		this.khoId = "";
		this.spNgcIds = "";
		this.quanlyCN = "1";
		this.duyet = "0";
		this.is_khuchexuat="";
		this.chixem = "0";
		this.soItems = 25;
		this.taikhoankq = "";
		this.nhTen="";
		this.nppId = "1" ;
		this.nppList = new ArrayList<Erp_Item>();
		this.nhId="";
		this.CNId="";
		this.TenCN="";
		this.db = new dbutils();
	}

	public void setIs_khuchexuat(String is_khuchexuat){
		this.is_khuchexuat = is_khuchexuat;
	}
	
	public String getIs_khuchexuat(){
		return this.is_khuchexuat;
	}	
	
	public String getFax() {
		if(fax==null)
		{
			fax = "";
		}
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getTen()
	{
		return Ten;
	}

	public void setTen(String ten)
	{
		Ten = ten;
	}

	public String getId()
	{
		return this.Id;
	}

	public void setId(String id)
	{
		this.Id = id;
	}

	public void setMa(String ma)
	{
		this.Ma = ma;
	}

	public String getMa()
	{
		return this.Ma;
	}

	public String getMST()
	{
		return this.MST;
	}

	public void setMST(String mst)
	{
		this.MST = mst;
	}

	public void setThoiHanNo(String thoihan)
	{
		this.ThoiHanNo = thoihan;
	}

	public String getThoiHanNo()
	{
		return this.ThoiHanNo;
	}

	public String getHanMucNo()
	{
		return this.HanMucNo;
	}

	public void setHanMucNo(String hanmuc)
	{
		this.HanMucNo = hanmuc;
	}

	public String getDienThoai_NCC()
	{
		return this.DienThoai_NCC;
	}

	public void setDienThoai_NCC(String dienthoai_ncc)
	{
		this.DienThoai_NCC = dienthoai_ncc;
	}

	public String getNguoiLienHe()
	{
		return this.NguoiLienHe;
	}

	public void setNguoiLienHe(String nguoilienhe)
	{
		this.NguoiLienHe = nguoilienhe;
	}

	public void setNguoiMuaHang(String nguoimuahang)
	{
		this.NguoiMuaHang = nguoimuahang;
	}

	public void setUserId(String userId)
	{
		this.UserId = userId;
	}

	public String getUserId()
	{
		return this.UserId;
	}

	public String getMsg()
	{
		return this.Msg;
	}

	public void setMsg(String msg)
	{
		this.Msg = msg;
	}

	public void setTrangThai(String trangthai)
	{
		this.TrangThai = trangthai;
	}

	public String getTrangThai()
	{
		return this.TrangThai;
	}

	public String getCongTy()
	{
		return this.CongTy;
	}

	public void setCongTy(String congty)
	{
		this.CongTy = congty;
	}

	public String getDiaChi_NCC()
	{
		return this.DiaChi_NCC;
	}

	public void setDiaChi_NCC(String diachi_ncc)
	{
		this.DiaChi_NCC = diachi_ncc;
	}

	public String getLoaiNCC()
	{
		return this.LoaiNCC;
	}

	public void setLoaiNCC(String loaincc)
	{
		this.LoaiNCC = loaincc;
	}

	public String getNganHang()
	{
		return this.NganHang;
	}

	public void setNganHang(String nganhang)
	{
		this.NganHang = nganhang;
	}

	public String getSoTaiKhoan()
	{
		return this.SoTaiKhoan;
	}

	public void setSoTaiKhoan(String sotaikhoan)
	{
		this.SoTaiKhoan = sotaikhoan;
	}

	public String getTaiKhoan()
	{
		return this.TaiKhoan;
	}

	public void setTaiKhoan(String taikhoan)
	{
		this.TaiKhoan = taikhoan;
	}

	public String getChiNhanh()
	{
		return this.ChiNhanh;
	}

	public void setChiNhanh(String chinhanh)
	{
		this.ChiNhanh = chinhanh;
	}

	public String kiemTraMaSoThue()
	{
		String tenNhaCungCap = "";
		String query = 
			"select convert(nvarchar, ncc.PK_SEQ) + ' - ' + ncc.MA + ' - ' + ncc.TEN as ten\n" + 
			"from ERP_NHACUNGCAP ncc \n" +
			"where ncc.TRANGTHAI <> 2 and ncc.MASOTHUE like '" + this.MST + "'\n";
		if (this.Id.trim().length() > 0)
			query += "and ncc.PK_SEQ <> " + this.Id + "\n";
		
		System.out.println("cau lenh lay ten nha cung cap trung ma so thue:\n" + query + "\n--------------------------------------------");
		try {
			ResultSet rs = this.db.get(query);
			
			if (rs != null)
			{
				if (rs.next())
				{
					tenNhaCungCap = rs.getString("ten");
				}
				rs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return tenNhaCungCap;
	}
	
	public void init()
	{
		String query = 
			"	   Select ncc.PK_SEQ,ISNULL(ncc.Ma,'')Ma,ISNULL(ncc.Ten,'')Ten,ISNULL(ncc.CONGTY_FK,0)CongTy_FK,ISNULL(ncc.LOAINCC,'0')LoaiNCC,ISNULL(ncc.DIACHI,'')DiaChi,ISNULL(ncc.MASOTHUE,'')MaSoThue,ISNULL((select soHieuTaiKhoan from erp_taiKhoanKT tk where tk.pk_seq = ncc.taiKhoan_FK), '') TaiKhoan,\n" +
			"       ncc.NganHang_FK as NganHang, CHINHANH_FK as ChiNhanh,ISNULL(THOIHANNO,0)ThoiHanNo, ISNULL(DUYET,0) AS DUYET , isnull(muctindung,'') as muctindung, \n" +
			"       ISNULL(SOTAIKHOAN,'')SOTAIKHOAN, ISNULL(HANMUCNO,0) HanMucNo,ISNULL(DIENTHOAI,'')DienThoai,ISNULL(TEN_NGUOILIENHE,'')NguoiLienHe,ISNULL(DT_NGUOILIENHE,'')DienThoai_NLH,\n" +
			"       ISNULL(EMAIL_NGUOILIENHE,'')Email_NguoiLienHe,ISNULL(TEN_NGUOIMUAHANG,'')NguoiMuaHang ,\n" +
			"       ISNULL(ncc.TrangThai,0) TrangThai, IsNull(KhoNL_Nhan_GC, KhoNL_Nho_GC) as khoNLId, isnull(quanlycongno, 0) as quanlycongno, isnull(loaigiamua, '') as loaigiamua, ncc.FAX, isnull(is_khuchexuat,'') as is_khuchexuat, ISNULL((select soHieuTaiKhoan from erp_taiKhoanKT tk where tk.pk_seq = ncc.TAIKHOANKQ_FK), '') TAIKHOANKQ_FK \n" +
			"	   , ncc.npp_fk,\n" +
			"		ISNULL(nh.Ten,'') TenNh,ISNULL(ch.Ten,'') as TenCN "+
			"	   From ERP_NHACUNGCAP ncc " +
			"		left join Erp_nganhang nh on ncc.NganHang_FK = nh.PK_SEQ  " +
			"		left join Erp_chinhanh ch on ncc.CHINHANH_FK=ch.PK_SEQ"+
			" Where ncc.PK_SEQ =" + this.Id + "\n" +
			
			"order by ncc.NGAYTAO desc, ncc.NGAYSUA desc, ncc.PK_SEQ desc\n";

		ResultSet rs = this.db.get(query);
		System.out.println("Init ncc: \n" + query + "\n-----------------------");
		if (rs != null)
		{
			try
			{
				while (rs.next())
				{
					this.nppId = rs.getString("npp_fk");
					this.Ma = rs.getString("Ma");
					this.CongTy = rs.getString("CongTy_FK");
					this.Ten = rs.getString("Ten");
					this.LoaiNCC = rs.getString("LoaiNCC");
					this.TaiKhoan = rs.getString("TaiKhoan");
					this.DiaChi_NCC = rs.getString("DiaChi");
					this.MST = rs.getString("MaSoThue");
					this.ChiNhanh = rs.getString("ChiNhanh");
					this.ThoiHanNo = rs.getString("ThoiHanNo");
					this.HanMucNo = rs.getString("HanMucNo");
					this.DienThoai_NCC = rs.getString("DienThoai");
					this.NguoiLienHe = rs.getString("NguoiLienHe");
					this.DienThoai_NLH = rs.getString("DienThoai_NLH");
					this.Email_NLH = rs.getString("Email_NguoiLienHe");
					this.NguoiMuaHang = rs.getString("NguoiMuaHang");
					this.TrangThai = rs.getString("TrangThai");
					this.NganHang = rs.getString("NganHang");
					this.SoTaiKhoan = rs.getString("SoTaiKhoan");
					this.khoId = rs.getString("khoNLId") == null ? "" : rs.getString("khoNLId");
					this.quanlyCN = rs.getString("quanlycongno");
					this.loaigiamua = rs.getString("loaigiamua");
					this.fax = rs.getString("fax");
					this.duyet = rs.getString("DUYET");
					this.muctindung = rs.getString("muctindung");
					this.is_khuchexuat = rs.getString("is_khuchexuat");
					this.taikhoankq = rs.getString("TAIKHOANKQ_FK") == null ? "" : rs.getString("TAIKHOANKQ_FK");
					this.TenCN=rs.getString("TenCN");
					this.nhId=rs.getString("NganHang")==null?"":rs.getString("NganHang");
					this.nhTen=rs.getString("TenNh");
					this.CNId=rs.getString("ChiNhanh")==null?"":rs.getString("ChiNhanh");
				}
				rs.close();	
			} 
			catch (SQLException e)
			{
				e.printStackTrace();
			}	
		}
		
		//Init
		if(this.LoaiNCC.equals("100003"))
		{
			query = "select sanpham_fk from ERP_NHACUNGCAP_SPNHANGIACONG where ncc_fk = '" + this.Id + "'";
		}
		else
		{
			query = "select sanpham_fk from ERP_NHACUNGCAP_SPNHOGIACONG where ncc_fk = '" + this.Id + "'";
		}
		ResultSet rsInit = db.get(query);
		if(rsInit != null)
		{
			try 
			{
				String str = "";
				while(rsInit.next())
				{
					str += rsInit.getString("sanpham_fk") + ",";
				}
				rsInit.close();
				
				if (str.length() > 0)
					this.spNgcIds = str.substring(0, str.length() - 1);
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}			
	}

	public void createaRs()
	{
		String query = 
			"select npp.pk_seq, npp.ten from NHAPHANPHOI npp where npp.trangThai = 1 and isKhachHang = 0 order by pk_seq";
		Erp_Item.getListFromQuery(db, query, this.nppList);
		
		System.out.println("CongtyId:"+this.CongTy);
		query = "Select PK_SEQ,Ten From ERP_CONGTY Where  TrangThai=1 ";
		System.out.println("ABC0:"+query);
		this.CongTyRs = this.db.get(query);
		
		query = "Select PK_SEQ,Ten From Erp_NganHang Where TrangThai=1";
		System.out.println("ABC1:"+query);
		this.NganHangRs = this.db.get(query);
		
		query = "Select PK_SEQ,Ten From Erp_ChiNhanh Where TrangThai = 1";
		System.out.println("ABC2:"+query);
		this.ChiNhanhRs = this.db.get(query);
		
		query = "Select PK_SEQ,TenTaiKhoan As Ten,SoHieuTaiKhoan As Ma, SoHieuTaiKhoan From Erp_TaiKhoanKT WHERE npp_fk = 1 and TRANGTHAI=1 AND DUNGCHONCC = '1' AND CONGTY_FK = '" + this.CongTy + "' ";
		System.out.println("ABC3:"+query);
		this.TaiKhoanRs = this.db.get(query);
		
		query = "Select PK_SEQ,Ten From Erp_LoaiNhaCungCap Where TrangThai=1";
		System.out.println("ABC4:"+query);
		this.LoaiNCC_Rs = this.db.get(query);
		
		try {
			query= " SELECT PK_SEQ AS TKKTID, SOHIEUTAIKHOAN + ' - ' + TENTAIKHOAN, SOHIEUTAIKHOAN as TAIKHOANKT, SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE npp_fk = 1 and TRANGTHAI = 1 AND CONGTY_FK = "+this.CongTy;		
			this.TaikhoanKQ_Rs= this.db.get(query);
		} catch(Exception e){
			e.printStackTrace();
		}
		
		this.loaigiamuaRs = db.get("select pk_seq, diengiai from ERP_LOAIGIAMUA ");
		
		if(this.LoaiNCC.equals("100003"))
		{
			query = "select PK_SEQ, TEN " +
					"from ERP_KHOTT " +
					"where LOAI = '3' and TRANGTHAI = '1' and CONGTY_FK = '" + this.CongTy + "' ";
			
			/*if(this.Id.trim().length() <= 0)
			{
				query += "	and pk_seq not in ( select KhoNL_Nhan_GC from ERP_NhaCungCap where trangthai = '1' and KhoNL_Nhan_GC is not null ) ";
			}
			else
			{
				query += "	and pk_seq not in ( select KhoNL_Nhan_GC from ERP_NhaCungCap where KhoNL_Nhan_GC is not null and trangthai = '1' and pk_seq != '" + this.Id + "' ) ";
			}*/
			
			//System.out.println("__Khoi tao kho: " + query);
			
			this.khoNlRs = db.get(query);
			
			query = "select pk_seq, ma, TEN + ' ' + QUYCACH as spTen " +
					"from ERP_SanPham where TRANGTHAI = '1' and CONGTY_FK = '" + this.CongTy + "' and LOAISANPHAM_FK in ('100009')";
			
			this.spNgiacongRs = db.get(query);
		}
		else
		{
			if(this.LoaiNCC.equals("100004"))
			{
				query = "select PK_SEQ, TEN " +
						"from ERP_KHOTT " +
						"where LOAI = '4' and TRANGTHAI = '1' and CONGTY_FK = '" + this.CongTy + "' ";
				
				/*if(this.Id.trim().length() <= 0)
				{
					query += "	and pk_seq not in ( select KhoNL_Nho_GC from ERP_NhaCungCap where trangthai = '1' and KhoNL_Nho_GC is not null ) ";
				}
				else
				{
					query += "	and pk_seq not in ( select KhoNL_Nho_GC from ERP_NhaCungCap where KhoNL_Nho_GC is not null and trangthai = '1' and pk_seq != '" + this.Id + "' ) ";
				}*/
				
				System.out.println("_____Lay kho: " + query);
				
				this.khoNlRs = db.get(query);
				
				query = "select pk_seq, ma, TEN + ' ' + QUYCACH as spTen " +
						"from ERP_SanPham where TRANGTHAI = '1' and CONGTY_FK = '" + this.CongTy + "' and LOAISANPHAM_FK in ('100012') order by loaisanpham_fk asc";
				
				this.spNgiacongRs = db.get(query);
			}
		}
		
	}

	public boolean UpdateNcc()
	{
		if(this.quanlyCN.equals("1"))
		{
			if(this.TaiKhoan.trim().length() <= 0)
			{
				this.Msg = "Nhà cung cấp quản lý công nợ. Vui lòng chọn tài khoản ghi nhận công nợ";
				return false;
			}
		}
		
		if (!CheckUnique())
		{
			this.Msg = "Mã nhà cung cấp này đã có,vui lòng chọn mã khác";
			return false;
		}
		
		if(!CheckMaSoThue())
		{
			this.Msg = "MST công ty này đã tồn tại,vui lòng chọn mã khác";
			return false;
		}
	
		if(this.ThoiHanNo.trim().length()>0 )
			if (!CheckNumerOrNot(this.ThoiHanNo.trim()))
			{
				this.Msg = "Vui lòng nhập thời hạn nợ (số)";
				return false;
			}
		
		if(this.HanMucNo.trim().length()>0)
			if (!CheckNumerOrNot(this.HanMucNo.trim()))
			{
				this.Msg = "Vui lòng nhập hạn mức nợ (số)";
				return false;
			}
		if(this.HanMucNo.trim().length()>0)
			if (!CheckNumerOrNot(this.HanMucNo.trim()))
			{
				this.Msg = "Vui lòng nhập hạn mức nợ (số)";
				return false;
			}
		if(this.muctindung.trim().length()>0)
			if (!CheckNumerOrNot(this.muctindung.trim()))
			{
				this.Msg = "Vui lòng nhập mức tín dụng (số)";
				return false;
			}
		
		String khoId = this.khoId.trim().length() <= 0 ? "null" : this.khoId.trim();
		
		String khoNL_Nhan_GC = "null";
		String khoNL_Nho_GC = "null";
		
		if(this.LoaiNCC.equals("100003"))
		{
			if(khoId.trim().length() <= 0 || this.spNgcIds.trim().length() <= 0)
			{
				this.Msg = "Vui lòng chọn kho nguyên liệu nhận gia công và sản phẩm nhận gia công";
				return false;
			}
			
			khoNL_Nhan_GC = khoId;
		}
		else
		{
			if(this.LoaiNCC.equals("100004"))
			{
				if(khoId.trim().length() <= 0 || this.spNgcIds.trim().length() <= 0)
				{
					this.Msg = "Vui lòng chọn kho nguyên liệu nhờ gia công và sản phẩm nhờ gia công";
					return false;
				}
				
				khoNL_Nho_GC = khoId;
			}
		}
		
		//kiem tra loai noi bo thi noibo = 1
		String noibo = "0";
		if(this.LoaiNCC.equals("100000"))
		{
			noibo = "1";
		}
		
		if(this.is_khuchexuat.trim().length() <= 0) this.is_khuchexuat = "0";
		
		try
		{
		this.db.getConnection().setAutoCommit(false);
		if(this.nhTen.length()>0 && this.nhId.length()==0)
		{
			String query = "Insert into  ERP_nganhang"  
			+"(TEN,NGAYTAO,NGAYSUA,NGUOITAO,NGUOISUA,trangthai)" 
			+"values (N'"+this.nhTen+"','"+ Utility.getCurrentDate() +"','"+ Utility.getCurrentDate() +"','"+this.UserId+"','"+this.UserId+"',1)";
			boolean check = db.update(query);
			if(check)
			{
				ResultSet rs = this.db.get("select IDENT_CURRENT('ERP_NGANHANG') AS ID");
				try {
					if(rs.next())
					{
						this.nhId=rs.getString("ID");
					}
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
					db.getConnection().rollback();
				}
			}else if(this.nhTen.length()==0)
			{
				this.Msg = "Không thể tạo mới ngân hàng "+query;
				db.getConnection().rollback();
				return false;
			
			}
		
		}else if(this.nhTen.length()==0 )
		{
			this.nhId="NULL";
		}
	
		if(this.TenCN.length()>0 && this.CNId.length()==0)
		{
			String query ="Insert into ERP_chinhanh"
			+"(TEN,NGAYTAO,NGUOITAO,NGUOISUA,NGAYSUA,TRANGTHAI)"
			+"values (N'"+this.TenCN+"','"+ Utility.getCurrentDate() +"','"+this.UserId+"','"+this.UserId+"','"+ Utility.getCurrentDate() +"',1)";
			boolean check = db.update(query);
			if(check)
			{
				ResultSet rs = this.db.get("select IDENT_CURRENT('ERP_chinhanh') AS ID");
				try {
					if(rs.next())
					{
						this.CNId=rs.getString("ID");
					}
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
					db.getConnection().rollback();
				}
			}else
			{
				this.Msg = "Không thể tạo mới chi nhánh "+query;
				
				return false;
			}
			
		}else if(this.TenCN.length()==0 )
		{
			this.CNId="NULL";
		}
		
		
		
		
		
		
		
		String query = "Update Erp_NhaCungCap Set Ma=N'" + this.Ma + "',Ten=N'" + this.Ten + "',CongTy_FK=" + this.CongTy + "\n" +
				",NganHang_FK=" + this.nhId + ",ChiNhanh_FK=" + this.CNId + "\n" +
				",TaiKhoan_FK = (select pk_seq from erp_taiKhoanKT where soHieuTaiKhoan = N'" + this.TaiKhoan + "' and npp_fk = 1)\n" +
				",ThoiHanNo='" + this.ThoiHanNo + "',HanMucNo='" + this.HanMucNo + "', DienThoai='" + this.DienThoai_NCC + "'\n" +
				",Ten_NguoiLienHe=N'" + this.NguoiLienHe + "',DT_NguoiLienHe='" + this.DienThoai_NLH + "'," + "Email_NguoiLienHe='" + this.Email_NLH + "'\n" +
				", LOAINCC = '" + this.LoaiNCC + "', Ten_NguoiMuaHang=N'" + this.NguoiMuaHang + "',DiaChi=N'" + this.DiaChi_NCC + "'\n" +
				",MaSoThue='" + this.MST + "',TrangThai=" + this.TrangThai + ",SoTaiKhoan='" + this.SoTaiKhoan + "',NguoiSua='" + this.UserId + "'\n" +
				",NgaySua='" + Utility.getCurrentDate() + "', KhoNL_Nhan_GC = " + khoNL_Nhan_GC + ", KhoNL_Nho_GC = " + khoNL_Nho_GC + "\n" +
				", quanlycongno = '" + this.quanlyCN + "', loaigiamua = N'" + this.loaigiamua + "', Fax='" +this.fax+"'" +"\n" +
				", NOIBO='"+ noibo +"', is_khuchexuat="+this.is_khuchexuat+" ,muctindung = '"+this.muctindung +"'\n" +
				", TAIKHOANKQ_FK = (select pk_seq from erp_taiKhoanKT where soHieuTaiKhoan = N'"+this.taikhoankq+ "' and npp_fk = " + (this.nppId.trim().length() == 0 ? "1" : this.nppId) + ")\n" +
				", npp_fk = " + this.nppId + "\n" +
				" Where PK_SEQ=" + this.Id + "\n";
		
		
			System.out.println("Lenh sql Update NhaCungCap:\n" + query + "\n-------------------------------------------");
			if (!this.db.update(query))
			{
				this.Msg = "Không thể cập nhật nhà cung cấp! " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_NHACUNGCAP_SPNHANGIACONG where ncc_fk = '" + this.Id + "' ";
			if (!this.db.update(query))
			{
				this.Msg = "Không thể cập nhật nhà cung cấp " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_NHACUNGCAP_SPNHOGIACONG where ncc_fk = '" + this.Id + "' ";
			if (!this.db.update(query))
			{
				this.Msg = "Không thể cập nhật nhà cung cấp " + query;
				db.getConnection().rollback();
				return false;
			}
	
			if(this.LoaiNCC.equals("100003"))
			{
				query = "Insert ERP_NHACUNGCAP_SPNHANGIACONG(ncc_fk, sanpham_fk) " +
						" select '" + this.Id + "', pk_seq from ERP_SanPham where pk_seq in (" + this.spNgcIds + ") ";
				
				if (!this.db.update(query))
				{
					this.Msg = "Không thể cập nhật nhà cung cấp " + query;
					db.getConnection().rollback();
					return false;
				}
			}
			else
			{
				if(this.LoaiNCC.equals("100004"))
				{
					query = "Insert ERP_NHACUNGCAP_SPNHOGIACONG(ncc_fk, sanpham_fk) " +
							" select '" + this.Id + "', pk_seq from ERP_SanPham where pk_seq in (" + this.spNgcIds + ") ";
					
					if (!this.db.update(query))
					{
						this.Msg = "Không thể tạo mới nhà cung cấp " + query;
						db.getConnection().rollback();
						return false;
					}
				}
			}
			
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			
			//SYN QUA DMS
			UtilitySyn.SynData(db, "Erp_NhaCungCap", "Erp_NhaCungCap", "PK_SEQ", this.Id, "1", false);
			
			UtilitySyn.SynData(db, "ERP_NHACUNGCAP_SPNHANGIACONG", "ERP_NHACUNGCAP_SPNHANGIACONG", "NCC_FK", this.Id, "2", true);
			UtilitySyn.SynData(db, "ERP_NHACUNGCAP_SPNHANGIACONG", "ERP_NHACUNGCAP_SPNHANGIACONG", "NCC_FK", this.Id, "0", false);
			
			UtilitySyn.SynData(db, "ERP_NHACUNGCAP_SPNHOGIACONG", "ERP_NHACUNGCAP_SPNHOGIACONG", "NCC_FK", this.Id, "2", true);
			UtilitySyn.SynData(db, "ERP_NHACUNGCAP_SPNHOGIACONG", "ERP_NHACUNGCAP_SPNHOGIACONG", "NCC_FK", this.Id, "0", false);
			
			this.db.shutDown();
			return true;
		
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			this.Msg = "Không thể cập nhật nhà cung cấp! " + e.getMessage();
			db.update("rollback");
			return false;
		}
		
	}

	public boolean DeleteNcc()
	{
		if (!CheckReferences("NhaCungCap_FK", "Erp_MuaHang"))
		{
			this.Msg = "Nhà cung cấp này đã có mua hàng, bạn phải xóa mua hàng của Ncc này trước khi xóa NCC";
			return false;
		}
		
		String sql = " SELECT isnull( NPP_FK, 0) NPP_FK FROM ERP_NHACUNGCAP WHERE PK_SEQ = "+this.Id;
		
		ResultSet rs = db.get(sql);
		
		int npp_fk = 0;
		
		try
		{
			if(rs!=null)
			{
				while(rs.next())
				{
					npp_fk  = rs.getInt("NPP_FK");
				}
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		if(npp_fk == 1)
		{
			this.Msg = "Bạn không thể xóa nhà cung cấp này. Vì đã phát sinh dữ liệu hóa đơn NCC";
			return false;
		}
		
		try {
			this.db.getConnection().setAutoCommit(false);
			String query = "DELETE ERP_NHACUNGCAP_SPNHANGIACONG WHERE ncc_fk='" + this.Id + "'";
			if (!this.db.update(query))
			{
				this.Msg = "Không thể xóa nhà cung cấp " + query;
				this.db.update("rollback");
				return false;
			}
			
			query = "DELETE ERP_NHACUNGCAP WHERE PK_SEQ='" + this.Id + "'";
			if (!this.db.update(query))
			{
				this.Msg = "Không thể xóa nhà cung cấp " + query;
				this.db.update("rollback");
				return false;
			}

			//SYN QUA DMS
			UtilitySyn.SynData(db, "Erp_NhaCungCap", "Erp_NhaCungCap", "PK_SEQ", this.Id, "2", true);
			UtilitySyn.SynData(db, "ERP_NHACUNGCAP_SPNHANGIACONG", "ERP_NHACUNGCAP_SPNHANGIACONG", "NCC_FK", this.Id, "2", true);			
			UtilitySyn.SynData(db, "ERP_NHACUNGCAP_SPNHOGIACONG", "ERP_NHACUNGCAP_SPNHOGIACONG", "NCC_FK", this.Id, "2", true);

			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(false);
		} catch (Exception e) {
			this.db.update("rollback");
			e.printStackTrace();
		}
		return true;
	}

	public boolean CreateNcc()
	{
		if(this.quanlyCN.equals("1"))
		{
			if(this.TaiKhoan.trim().length() <= 0)
			{
				this.Msg = "Nhà cung cấp quản lý công nợ. Vui lòng chọn tài khoản ghi nhận công nợ";
				return false;
			}
		}
		
		if (!CheckUnique())
		{
			this.Msg = "Mã nhà cung cấp này đã có,vui lòng chọn mã khác";
			return false;
		}
		
		if(!CheckMaSoThue())
		{
			this.Msg = "MST công ty này đã tồn tại,vui lòng chọn mã khác";
			return false;
		}
		
		if(this.ThoiHanNo.trim().length()>0 )
			if (!CheckNumerOrNot(this.ThoiHanNo.trim()))
			{
				this.Msg = "Vui lòng nhập thời hạn nợ (số)";
				return false;
			}
		if(this.HanMucNo.trim().length()>0)
			if (!CheckNumerOrNot(this.HanMucNo.trim()))
			{
				this.Msg = "Vui lòng nhập hạn mức nợ (số)";
				return false;
			}
		
		String khoId = this.khoId.trim().length() <= 0 ? "null" : this.khoId.trim();
		
		String khoNL_Nhan_GC = "null";
		String khoNL_Nho_GC = "null";
		
		if(this.LoaiNCC.equals("100003"))
		{
			if(khoId.trim().length() <= 0 || this.spNgcIds.trim().length() <= 0)
			{
				this.Msg = "Vui lòng chọn kho nguyên liệu nhận gia công và sản phẩm nhận gia công";
				return false;
			}
			
			khoNL_Nhan_GC = khoId;

		}
		else
		{
			if(this.LoaiNCC.equals("100004"))
			{
				if(khoId.trim().length() <= 0 || this.spNgcIds.trim().length() <= 0)
				{
					this.Msg = "Vui lòng chọn kho nguyên liệu nhờ gia công và sản phẩm nhờ gia công";
					return false;
				}
				
				khoNL_Nho_GC = khoId;
			}
		}
		
		//kiem tra loai noi bo thi noibo = 1
		String noibo = "0";
		if(this.LoaiNCC.equals("100000"))
		{
			noibo = "1";
		}
		
		if(this.is_khuchexuat.trim().length() <= 0) this.is_khuchexuat = "0";
		System.out.println("aaaaa");
		try
		{
		this.db.getConnection().setAutoCommit(false);
		if(this.nhTen.length()>0 && this.nhId.length()==0)
		{
			String query = "Insert into  ERP_nganhang"  
			+"(TEN,NGAYTAO,NGAYSUA,NGUOITAO,NGUOISUA,trangthai)" 
			+"values (N'"+this.nhTen+"','"+ Utility.getCurrentDate() +"','"+ Utility.getCurrentDate() +"','"+this.UserId+"','"+this.UserId+"',1)";
			boolean check = db.update(query);
			if(check)
			{
				ResultSet rs = this.db.get("select IDENT_CURRENT('ERP_NGANHANG') as ID");
				try {
					if(rs.next())
					{
						this.nhId=rs.getString("ID");
					}
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
					db.getConnection().rollback();
					return false;
				}
			}else
			{
				this.Msg = "Không thể tạo mới ngân hàng "+query;
				db.getConnection().rollback();
				return false;
			
			}
		
		}else if(this.nhTen.length()==0)
		{
			this.nhId="NULL";
		}
		System.out.println("asdasdsa "+this.CNId);
		if(this.TenCN.length()>0 && this.CNId.length()==0)
		{
			String query ="Insert into ERP_chinhanh"
			+"(TEN,NGAYTAO,NGUOITAO,NGUOISUA,NGAYSUA,TRANGTHAI)"
			+"values (N'"+this.TenCN+"','"+ Utility.getCurrentDate() +"','"+this.UserId+"','"+this.UserId+"','"+ Utility.getCurrentDate() +"',1)";
			boolean check = db.update(query);
			if(check)
			{
				ResultSet rs = this.db.get("select IDENT_CURRENT('ERP_chinhanh') as ID");
				try {
					if(rs.next())
					{
						this.CNId=rs.getString("ID");
					}
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
					db.getConnection().rollback();
				}
			}else
			{
				this.Msg = "Không thể tạo mới chi nhánh "+query;
				db.getConnection().rollback();
				return false;
			}
			
		}else if(this.TenCN.length()==0)
		{
			this.CNId="NULL";
		}
		
		String query = " Insert into Erp_NhaCungCap"
				+ "(Ma,Ten,CongTy_FK, TaiKhoan_FK,DiaChi,MaSoThue,SoTaiKhoan,"
				+ "NganHang_FK,ChiNhanh_FK,ThoiHanNo,HanMucNo,DienThoai,Ten_NguoiLienHe,DT_NguoiLienHe,Email_NguoiLienHe,  "
				+ "Ten_NguoiMuaHang,TrangThai,NguoiTao,NguoiSua,NgayTao,NgaySua, KhoNL_Nhan_GC, KhoNL_Nho_GC, quanlycongno, " 
				+ "loaigiamua, fax , noibo, LOAINCC, is_khuchexuat,muctindung, TAIKHOANKQ_FK, npp_fk) values" + "('"
				+ this.Ma+ "',N'"+ this.Ten+ "','"+ this.CongTy+ "'\n" +
				", (select pk_seq from erp_taiKhoanKT where soHieuTaiKhoan = N'" + this.TaiKhoan + "' and npp_fk = 1)\n" +
				",N'"+ this.DiaChi_NCC
				+ "','"+ this.MST+ "', '"+ this.SoTaiKhoan+"',"+ this.nhId+ ","+ this.CNId+ ",'"+ this.ThoiHanNo+ "','"
				+ this.HanMucNo+ "','"+ this.DienThoai_NCC+ "',N'"+ this.NguoiLienHe+ "','"+ this.DienThoai_NLH+ "','"+ this.Email_NLH
				+ "', N'"+ this.NguoiMuaHang+ "',"+ this.TrangThai+ ",'"+ this.UserId+ "','"+ this.UserId+ "','"+ Utility.getCurrentDate() + "','"
				+ Utility.getCurrentDate() + "', " + khoNL_Nhan_GC + ", " + khoNL_Nho_GC + ", '" + this.quanlyCN + "', N'" + this.loaigiamua + "'" +",'"
				+this.fax+"','"+ noibo +"', '" + this.LoaiNCC + "', "+this.is_khuchexuat+",'"+this.muctindung+"'\n" +
				", (select pk_seq from erp_taiKhoanKT where soHieuTaiKhoan = N'"+this.taikhoankq + "' and npp_fk = " + (this.nppId.trim().length() == 0 ? "1" : this.nppId) + "), " + this.nppId + ")";
		
		System.out.println(query);
	
			
			if (!this.db.update(query))
			{
				this.Msg = "Không tạo mới được nhà cung cấp  " + query;
				db.getConnection().rollback();
				return false;
			}
			
			ResultSet rs = this.db.get("SELECT SCOPE_IDENTITY() AS ID");
			rs.next();
			this.Id = rs.getString("ID");
			rs.close();
			
			if(this.LoaiNCC.equals("100003"))
			{
				query = "Insert ERP_NHACUNGCAP_SPNHANGIACONG(ncc_fk, sanpham_fk) " +
						" select '" + this.Id + "', pk_seq from ERP_SanPham where pk_seq in (" + this.spNgcIds + ") ";
				
				if (!this.db.update(query))
				{
					this.Msg = "Không thể tạo mới nhà cung cấp " + query;
					db.getConnection().rollback();
					return false;
				}
			}
			else
			{
				if(this.LoaiNCC.equals("100004"))
				{
					query = "Insert ERP_NHACUNGCAP_SPNHOGIACONG(ncc_fk, sanpham_fk) " +
							" select '" + this.Id + "', pk_seq from ERP_SanPham where pk_seq in (" + this.spNgcIds + ") ";
					
					if (!this.db.update(query))
					{
						this.Msg = "Không thể tạo mới nhà cung cấp " + query;
						db.getConnection().rollback();
						return false;
					}
				}
			}

			
			
			
			
			//Tu Phan quyen cho nguoi tao
			query = " insert NHANVIEN_NHACUNGCAP(nhanvien_fk, ncc_fk) " +
					" select  " + this.UserId + " ,  " + this.Id + "    " +
				    " union select  100211 ,  " + this.Id + "   union select  100308 , " + this.Id +  
				    " union " +
					" select cd.NHANVIEN_FK, "+this.Id + " "+ 
					" from ERP_CHUCDANH_NHANVIEN cdnv inner join ERP_CHUCDANH cd on cd.PK_SEQ=cdnv.CHUCDANH_FK "+
					" where  cdnv.NHANVIEN_FK= "+this.UserId+
					" union select nhanvien_fk,"+this.Id + "  from nhanvien_loaincc where loaincc_fk = '"+this.LoaiNCC+"'";
			if (!this.db.update(query))
			{
				this.Msg = "Không thể tạo mới nhà cung cấp " + query;
				db.getConnection().rollback();
				return false;
			}
			
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			
			//SYN QUA DMS			
			UtilitySyn.SynData(db, "Erp_NhaCungCap", "Erp_NhaCungCap", "PK_SEQ", this.Id, "0", false);
			UtilitySyn.SynData(db, "ERP_NHACUNGCAP_SPNHANGIACONG", "ERP_NHACUNGCAP_SPNHANGIACONG", "NCC_FK", this.Id, "0", false);
			UtilitySyn.SynData(db, "ERP_NHACUNGCAP_SPNHOGIACONG", "ERP_NHACUNGCAP_SPNHOGIACONG", "NCC_FK", this.Id, "0", false);
			
			this.db.shutDown();
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
			this.Msg = "Không tạo mới được nhà cung cấp lỗi " + e.getMessage();
			this.db.update("rollback");
			return false;
		}
		return true;
	}
	
	public static void main(String[] arg)
	{
		dbutils db = new dbutils();
		
		String query = "select pk_seq from ERP_NHACUNGCAP where pk_seq not in ( select pk_seq from LINK_DMS.DataCenter.dbo.ERP_NHACUNGCAP  )  ";
		ResultSet rs = db.get(query);
		if( rs != null )
		{
			try 
			{
				while( rs.next() )
				{
					String nccId = rs.getString("pk_seq");
					
					//SYN QUA DMS			
					UtilitySyn.SynData(db, "Erp_NhaCungCap", "Erp_NhaCungCap", "PK_SEQ", nccId, "0", false);
					//UtilitySyn.SynData(db, "ERP_NHACUNGCAP_SPNHANGIACONG", "ERP_NHACUNGCAP_SPNHANGIACONG", "NCC_FK", nccId, "0", false);
					//UtilitySyn.SynData(db, "ERP_NHACUNGCAP_SPNHOGIACONG", "ERP_NHACUNGCAP_SPNHOGIACONG", "NCC_FK", nccId, "0", false);
				}
				rs.close();
				db.shutDown();
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		
		System.out.println("::: CHAY XONG..... ");
	}

	public boolean CheckUnique()
	{
		String query = "";
		if (this.Id.length() > 0)
			query = "Select count(*) as count From ERP_NHACUNGCAP Where MA='" + this.Ma + "' AND PK_SEQ !='" + this.Id + "' AND CONGTY_FK = '" + this.CongTy + "' ";
		else
			query = "Select count(*) as count From ERP_NHACUNGCAP Where MA='" + this.Ma + "' ";
		
		int count = 0;
		ResultSet rs = this.db.get(query);
		if (rs != null)
			try
			{
				while (rs.next())
				{
					count = rs.getInt("count");
				}
				rs.close();
				if (count > 0)
					return false;
			} catch (SQLException e)
			{
				e.printStackTrace();
				return false;
			}
		return true;
	}
	
	public boolean CheckMaSoThue()
	{
		if(this.MST.trim().length() <= 0) return true;
		
		String query = "";
		if (this.Id.length() > 0)
			query = "Select count(*) as count From ERP_NHACUNGCAP Where MASOTHUE ='" + this.MST + "' AND PK_SEQ !='" + this.Id + "' AND CONGTY_FK = '" + this.CongTy + "' ";
		else
			query = "Select count(*) as count From ERP_NHACUNGCAP Where MASOTHUE='" + this.MST + "' ";
		
		int count = 0;
		ResultSet rs = this.db.get(query);
		if (rs != null)
			try
			{
				while (rs.next())
				{
					count = rs.getInt("count");
				}
				rs.close();
				if (count > 0)
					return false;
			} catch (SQLException e)
			{
				e.printStackTrace();
				return false;
			}
		return true;
	}
	

	public boolean CheckReferences(String column, String table)
	{
		String query = "SELECT count(" + column + ") AS NUM  FROM " + table + " WHERE " + column + " =" + this.Id + "";
		System.out.println("CheckReferences " + query);
		ResultSet rs = db.get(query);
		try
		{// kiem tra ben san pham
			while (rs.next())
			{
				if (rs.getString("num").equals("0"))
					return true;
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}

	public ResultSet getCongTyRs()
	{
		return this.CongTyRs;
	}

	public void setCongTyRs(ResultSet congty)
	{
		this.CongTyRs = congty;
	}

	public ResultSet getLoaiNCCRs()
	{
		return this.LoaiNCC_Rs;
	}

	public void setLoaiNCC(ResultSet LoaiNCC)
	{
		this.LoaiNCC_Rs = LoaiNCC;
	}

	public ResultSet getChiNhanhRs()
	{
		return this.ChiNhanhRs;
	}

	public void setNganHang(ResultSet nganhang)
	{
		this.NganHangRs = nganhang;
	}

	public ResultSet getNganHangRs()
	{
		return this.NganHangRs;
	}

	public void setChiNhanhRs(ResultSet chinhanh)
	{
		this.ChiNhanhRs = chinhanh;
	}

	public ResultSet getTaiKhoanRs()
	{
		return this.TaiKhoanRs;
	}

	public void setTaiKhoanRs(ResultSet taikhoan)
	{
		this.TaiKhoanRs = taikhoan;
	}

	public String getEmail_NLH()
	{
		return this.Email_NLH;
	}

	public void setEmail_NLH(String email)
	{
		this.Email_NLH = email;
	}

	public String getDienThoai_NLH()
	{
		return this.DienThoai_NLH;
	}

	public void setDienThoai_NLH(String dienthoai_nlh)
	{
		this.DienThoai_NLH = dienthoai_nlh;
	}

	public String getNguoiMuaHang()
	{
		return this.NguoiMuaHang;
	}

	public String getLoaiNCC(String loaincc){
		String query = "SELECT MA FROM ERP_LOAINHACUNGCAP WHERE PK_SEQ IN (" + loaincc + ")";
		ResultSet rs = this.db.get(query);
		String tmp = "";
		try{
			while(rs.next()){
				tmp += rs.getString("MA") + ", ";
			}
			if(tmp.trim().length() > 0)
				tmp = tmp.substring(0, tmp.length() - 2);
		}catch(java.sql.SQLException e){
			e.printStackTrace();
		}
		return tmp;
	}
	public void search()
	{
		String query = 
				"Select n.PK_SEQ,n.Ma,n.Ten,isnull(c.TEN,'')  CongTy, LOAINCC AS Loai_NCC,isnull(h.TEN,'') NganHang,isnull(cn.TEN,'') ChiNhanh, isnull(n.Duyet,0) as duyet, \n" +
				"		isnull(t.sohieutaikhoan, '') + ' - ' + isnull(t.TenTaiKhoan,'')  TaiKhoan,isnull(nt.Ten,'')  NguoiTao,isnull(ns.Ten,'') NguoiSua,ISNULL(n.NgayTao,'')NgayTao,ISNULL(n.NgaySua,'')NgaySua,ISNULL(n.TrangThai,0)TrangThai \n"
				+ "From ERP_NHACUNGCAP n \n"
				//+ " LEFT JOIN ERP_LOAINHACUNGCAP l on  l.PK_SEQ=n.LOAINHACUNGCAP_FK "
				+ " LEFT JOIN ERP_CONGTY c   on c.PK_SEQ=n.CONGTY_FK \n"
				+ " LEFT JOIN Erp_TaiKhoanKT t  ON t.PK_SEQ=n.TAIKHOAN_FK \n"
				+ " LEFT JOIN ERP_NGANHANG h  on h.PK_SEQ=n.NGANHANG_FK \n"
				+ " LEFT JOIN ERP_CHINHANH cn  on cn.PK_SEQ=n.CHINHANH_FK \n"
				+ " LEFT JOIN NHANVIEN nt \n"
				+ " on nt.PK_SEQ=n.NGUOITAO " + " LEFT JOIN NHANVIEN ns " + " on ns.PK_SEQ=n.NGUOISUA Where n.pk_seq > 0 \n"; // AND n.CONGTY_FK = '" + this.CongTy + "' " ;
		
		if(!this.CongTy.equals("100000")) //TONG CONG TY
			query += " AND n.congty_fk = '" + this.CongTy + "' ";
		
		if (this.NganHang.length() > 0)
			query += " And n.NganHang_FK=" + this.NganHang + " ";
		if (this.ChiNhanh.length() > 0)
			query += " And n.ChiNhanh_FK= " + this.ChiNhanh + " ";
		if (this.LoaiNCC.length() > 0)
			query += " And n.LOAINCC =" + this.LoaiNCC + "";
		if (this.TaiKhoan.trim().length() > 0)
			query += " And n.TaiKhoan_FK =" + this.TaiKhoan + "";
		
		if(this.Ten.trim().length() > 0)
			query += " and ( n.Ma like N'%" + this.Ten + "%' or n.Ten like N'%" + this.Ten + "%' ) ";
		
		System.out.println("Search123 " + query);
		this.NhaCungCapRs = createSplittingDataNew(this.db, soItems, 10, " ngayTao desc, ngaySua desc, PK_SEQ DESC,TRANGTHAI  ", query);
	}

	public ResultSet getNhaCungCapRs()
	{
		return this.NhaCungCapRs;
	}

	public void setNhaCungCapRs(ResultSet nhacungcap)
	{
		this.NhaCungCapRs = nhacungcap;
	}

	public void close()
	{
		try
		{
			if (NganHangRs != null)
				this.NganHangRs.close();
			if (ChiNhanhRs != null)
				this.ChiNhanhRs.close();
			if (LoaiNCC_Rs != null)
				this.LoaiNCC_Rs.close();
			if (CongTyRs != null)
				this.CongTyRs.close();
			if (TaiKhoanRs != null)
				this.TaiKhoanRs.close();
			if (NhaCungCapRs != null)
				this.NhaCungCapRs.close();
			this.db.shutDown();
			this.db = null;
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public void NewDbUtil()
	{
		if(this.db == null )
		{
			this.db  = new dbutils();
		}
	}

	public boolean CheckNumerOrNot(String number)
	{
		if (number.trim().length() > 0)
			if (number.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+"))
				return true;
		return false;
	}
	
	public void setSpNhanGiaCong(ResultSet spNhangiacong) {
		this.spNgiacongRs = spNhangiacong;
	}
	
	public ResultSet getSpNhanGiaCong() {
		return this.spNgiacongRs;
	}

	public void setSpNhangiacongIds(String spNhangiacongIds) {
		this.spNgcIds = spNhangiacongIds;
	}
	
	public String getSpNhangiacongIds() {
		return this.spNgcIds;
	}

	public void setKhoNlRs(ResultSet khoNlRs) {
		this.khoNlRs = khoNlRs;
	}

	public ResultSet getKhoNlRs() {
		return this.khoNlRs;
	}

	public void setKhoNlId(String khoNlId) {
		this.khoId = khoNlId;
	}

	public String getKhoNlId() {
		return this.khoId;
	}

	public void setQuanlycongno(String quanlyCN) {
		this.quanlyCN = quanlyCN;
	}

	public String getQuanlycongno() {
		return this.quanlyCN;
	}

	public ResultSet getLoaigiamuaRs() {
		return this.loaigiamuaRs;
	}
	
	public void setLoaigiamuaNCC(ResultSet loaigiamuaNCC) {
		this.loaigiamuaRs = loaigiamuaNCC;
	}

	public void setLoaigiamua(String loaigiamua) {
		this.loaigiamua = loaigiamua;
	}

	public String getLoaigiamua() {
		return this.loaigiamua;
	}
	
	public String getDuyet()
	{
		return this.duyet;
	}

	public void setDuyet(String duyet)
	{
		this.duyet = duyet;
	}

	public boolean DuyetNcc() 
	{
		try
		{
			this.db.getConnection().setAutoCommit(false);
			
			String query = " UPDATE ERP_NHACUNGCAP SET DUYET = '1' WHERE PK_SEQ = '"+ this.Id +"'";
			
			if (!this.db.update(query))
			{
				this.Msg = "Không thể duyệt nhà cung cấp! " + query;
				db.getConnection().rollback();
				return false;
			}
			
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			this.db.shutDown();
			return true;
			
		}catch(Exception e)
		{
			this.Msg = "Không thể duyệt nhà cung cấp! " + e.getMessage();
			db.update("rollback");
			e.printStackTrace();
			return false;
		}
	}

	public boolean BoDuyetNcc() 
	{
		try
		{
			this.db.getConnection().setAutoCommit(false);
			
			String query = " UPDATE ERP_NHACUNGCAP SET DUYET = '0' WHERE PK_SEQ = '"+ this.Id +"'";
			
			if (!this.db.update(query))
			{
				this.Msg = "Không thể bỏ duyệt nhà cung cấp! " + query;
				db.getConnection().rollback();
				return false;
			}
			
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			this.db.shutDown();
			return true;
			
		}catch(Exception e)
		{
			this.Msg = "Không thể bỏ duyệt nhà cung cấp! " + e.getMessage();
			db.update("rollback");
			e.printStackTrace();
			return false;
		}
	}
	
	public void setChixem(String chixem) {
		this.chixem = chixem;
	}

	public String getChixem() {
		return this.chixem;
	}

	public String getMucTinDung() {
		return this.muctindung;
	}

	public void setMucTinDung(String muctindung) {
		this.muctindung = muctindung;
	}
	
	public void setSoItems(int soItems) {
		this.soItems = soItems;
	}
	
	public int getSoItems() {
		return this.soItems;
	}
	
	public void setTkkqRs(ResultSet tkkqRs) {
		this.TaikhoanKQ_Rs = tkkqRs;
	}

	public ResultSet getTkkqRs() {
		return this.TaikhoanKQ_Rs;
	}
	
	public void setTkkqId(String tkkqId) {
		this.taikhoankq = tkkqId;
	}
	
	public String getTkkqId() {
		return this.taikhoankq;
	}

	public void setNppId(String nppId) {
		this.nppId = nppId;
	}

	public String getNppId() {
		return nppId;
	}

	public void setNppList(List<Erp_Item> nppList) {
		this.nppList = nppList;
	}

	public List<Erp_Item> getNppList() {
		return nppList;
	}

	public String getNhTen() {
		return nhTen;
	}

	public void setNhTen(String nhTen) {
		this.nhTen = nhTen;
	}

	public String getTenCN() {
		return TenCN;
	}

	public void setTenCN(String tenCN) {
		TenCN = tenCN;
	}

	public String getNhId() {
		return nhId;
	}

	public void setNhId(String nhId) {
		this.nhId = nhId;
	}

	public String getCNId() {
		return CNId;
	}

	public void setCNId(String cNId) {
		CNId = cNId;
	}
}