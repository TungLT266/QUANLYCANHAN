package geso.traphaco.erp.beans.doituongkhac.imp;

import geso.traphaco.center.util.Erp_Item;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;
import geso.traphaco.center.util.UtilityKeToan;
import geso.traphaco.erp.beans.doituongkhac.IErpDoiTuongKhac;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.util.UtilitySyn;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ErpDoiTuongKhac extends Phan_Trang implements IErpDoiTuongKhac
{
	private static final long serialVersionUID = -7173066606265660920L;
	
	private String id;
	private String userId;
	private String congTyId;
	private String maDoiTuong;
	private String tenDoiTuong;
	private String soHieuTaiKhoan;	
	private String tenTaiKhoan;
	private String trangThai;
	private String nppId;
	private String diaChi;
	private String dienThoai;	
	
	private String fax;
	private String tenNguoiLienHe;
	private String dtNguoiLienHe;
	private String emailNguoiLienHe;
	private String soTaiKhoan;	
	private String tenNganHang;
	private String tenChiNhanh;
	private String nganHangId;
	private String chiNhanhId;
	private String maSoThue;
	private String quanLyCongNo;
	
	private String msg;
	
	private List<Erp_Item> nppList;
	private List<Erp_Item> chiNhanhList;
	private List<Erp_Item> nganHangList;
	
	dbutils db;
	
	public ErpDoiTuongKhac()
	{
		this.id = "";
		this.userId = "";
		this.congTyId = "";
		this.maDoiTuong = "";
		this.tenDoiTuong = "";
		this.soHieuTaiKhoan = "";	
		this.tenTaiKhoan = "";
		this.trangThai = "";
		this.nppId = "";
		this.diaChi = "";
		this.dienThoai = "";	
		
		this.fax = "";
		this.tenNguoiLienHe = "";
		this.dtNguoiLienHe = "";
		this.emailNguoiLienHe = "";
		this.soTaiKhoan = "";	
		this.tenNganHang = "";	
		this.tenChiNhanh = "";	
		this.nganHangId = "";
		this.chiNhanhId = "";
		this.maSoThue = "";
		this.quanLyCongNo = "";
		
		this.msg = "";
		
		this.nppList = new ArrayList<Erp_Item>();
		this.chiNhanhList = new ArrayList<Erp_Item>();
		this.nganHangList = new ArrayList<Erp_Item>();
		
		this.db = new dbutils();
	}

	public ErpDoiTuongKhac(String id)
	{
		this.id = id;
		this.userId = "";
		this.congTyId = "";
		this.maDoiTuong = "";
		this.tenDoiTuong = "";
		this.soHieuTaiKhoan = "";	
		this.tenTaiKhoan = "";
		this.trangThai = "";
		this.nppId = "";
		this.diaChi = "";
		this.dienThoai = "";	
		
		this.fax = "";
		this.tenNguoiLienHe = "";
		this.dtNguoiLienHe = "";
		this.emailNguoiLienHe = "";
		this.soTaiKhoan = "";	
		this.nganHangId = "";
		this.chiNhanhId = "";
		this.maSoThue = "";
		this.quanLyCongNo = "";
		
		this.msg = "";
		
		this.nppList = new ArrayList<Erp_Item>();
		this.chiNhanhList = new ArrayList<Erp_Item>();
		this.nganHangList = new ArrayList<Erp_Item>();
		
		this.db = new dbutils();
	}

	public void init()
	{
		if (null != this.id && this.id.trim().length() > 0)
		{
			String query = 
				"select dtk.MADOITUONG, dtk.TENDOITUONG, dtk.SOHIEUTAIKHOAN, dtk.TENTAIKHOAN\n" +
				", dtk.TRANGTHAI, dtk.NPP_FK, isNull(dtk.DIACHI, '') DIACHI, isNull(dtk.DIENTHOAI, '') DIENTHOAI\n" +
				", isNull(dtk.FAX, '') FAX, isNull(dtk.TEN_NGUOILIENHE, '') tenNguoiLienHe, isNull(dtk.DT_NGUOILIENHE, '') dtNguoiLienHe, isNull(dtk.EMAIL_NGUOILIENHE, '') emailNguoiLienHe\n" +
				", isNull(dtk.SOTAIKHOAN, '') SOTAIKHOAN, dtk.CONGTY_FK, dtk.NGANHANG_FK, dtk.CHINHANH_FK\n" +
				", isNull(dtk.MASOTHUE, '') MASOTHUE, isNull(dtk.quanlycongno, '0') quanlycongno, isNull(nh.ma, '') + ' - ' + isNull(nh.ten, '') as tenNganHang, isNull(cn.ma, '') + ' - ' + isNull(cn.ten, '') as tenChiNhanh\n" +
				"from ERP_DOITUONGKHAC dtk\n" +
				"left join erp_nganHang nh on nh.pk_seq = dtk.nganHang_FK\n" +
				"left join erp_chiNhanh cn on cn.pk_seq = dtk.chiNhanh_FK\n" +
				"where dtk.PK_SEQ = " + this.id;

			System.out.println("Init ncc: \n" + query + "\n-----------------------");
			
			ResultSet rs = this.db.get(query);
			
			try
			{
				if (rs != null)
				{
					while (rs.next())
					{
						this.maDoiTuong = rs.getString("maDoiTuong");
						this.tenDoiTuong = rs.getString("tenDoiTuong");
						this.soHieuTaiKhoan = rs.getString("soHieuTaiKhoan");	
						this.tenTaiKhoan = rs.getString("tenTaiKhoan");
						this.trangThai = rs.getString("trangThai");
						this.nppId = rs.getString("npp_fk");
						this.diaChi = rs.getString("diaChi");
						this.dienThoai = rs.getString("dienThoai");	
						
						this.fax = rs.getString("fax");
						this.tenNguoiLienHe = rs.getString("tenNguoiLienHe");
						this.dtNguoiLienHe = rs.getString("dtNguoiLienHe");
						this.emailNguoiLienHe = rs.getString("emailNguoiLienHe");
						this.soTaiKhoan = rs.getString("soTaiKhoan");	
						this.nganHangId = rs.getString("nganHang_fk");
						this.chiNhanhId = rs.getString("chiNhanh_fk");
						this.maSoThue = rs.getString("maSoThue");
						this.quanLyCongNo = rs.getString("quanLyCongNo");
						
						this.tenNganHang = rs.getString("tenNganHang");
						this.tenChiNhanh = rs.getString("tenChiNhanh");
					}
					rs.close();	
				}
			} 
			catch (SQLException e)
			{
				e.printStackTrace();
			}	
		}
		
		initList();
	}

	private void initList() {
		String query = "select PK_SEQ, ma + ' - ' + TEN as ten from ERP_NGANHANG where trangthai = 1";
		Erp_Item.getListFromQuery(db, query, this.nganHangList);
		
		query = "select PK_SEQ, ma + ' - ' + TEN as ten from ERP_CHINHANH where trangthai = 1";
		Erp_Item.getListFromQuery(db, query, this.chiNhanhList);
		
		query = "select PK_SEQ, isNull(ma, '') + ' - ' + isNull(mafast, '') + ' - ' + isNull(TEN, '') as ten from NHAPHANPHOI where trangthai = 1";
		Erp_Item.getListFromQuery(db, query, this.nppList);
	}

	public boolean edit()
	{
		checkUnique();
		if (this.msg.trim().length() > 0)
		{
			this.msg = "E1.1 " +  this.msg;
			return false;
		}
		
		try
		{
			this.db.getConnection().setAutoCommit(false);
			
			String query = 
				"update ERP_DOITUONGKHAC set MADOITUONG = '" + this.maDoiTuong + "', TENDOITUONG = N'" + this.tenDoiTuong + "', SOHIEUTAIKHOAN = '" + this.soHieuTaiKhoan + "', TENTAIKHOAN=N'" + this.tenTaiKhoan + "'--\n" +
				", NPP_FK = " + this.nppId + ", DIACHI= N'" + this.diaChi + "', DIENTHOAI = '" + this.dienThoai + "'--\n" +
				", FAX = '" + this.fax + "', TEN_NGUOILIENHE = N'" + this.tenNguoiLienHe + "', DT_NGUOILIENHE = '" + this.dtNguoiLienHe + "', EMAIL_NGUOILIENHE = '" + this.emailNguoiLienHe + "'--\n" +
				", SOTAIKHOAN = '" + this.soTaiKhoan + "', CONGTY_FK = " + this.congTyId + ", NGANHANG_FK = " + this.nganHangId + ", CHINHANH_FK = " + this.chiNhanhId + "--\n" +
				", MASOTHUE = '" + this.maSoThue + "', NGUOISUA = " + this.userId + ", NGAYSUA = '" + Utility.getCurrentDate() + "', quanlycongno = '" + this.quanLyCongNo + "'--\n" +
				", trangThai = " + this.trangThai +
				"where PK_SEQ = " + this.id + "--\n";
		
			query = UtilityKeToan.refactorQuery(query);
			
			System.out.println("Lenh sql Update dtk:\n" + query + "\n-------------------------------------------");
			if (!this.db.update(query))
			{
				this.msg = "E1.2 Không thể cập nhật";
				db.getConnection().rollback();
				return false;
			}
	
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			
			//SYN QUA DMS
			String str = UtilitySyn.SynData(db, "ERP_DOITUONGKHAC", "ERP_DOITUONGKHAC", "PK_SEQ", this.id, "1", false);
			if (str.trim().length() > 0)
			{
				this.msg = "E1.3 Không thể đồng bộ xuống DMS " + str;
				return false;
			}
			
			return true;
		
		}
		catch (Exception e)
		{
			e.printStackTrace();
			this.msg = "E1.4 Không thể cập nhật";
			try {
				db.getConnection().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return false;
		}
	}

	public boolean delete()
	{
		String sql = " SELECT count(pk_seq) FROM ERP_phatSinhKeToan WHERE doiTuong = N'Đối tượng khác' and maDoiTuong = " + this.id;
		
		ResultSet rs = db.get(sql);
		int num = 0;
		try
		{
			if(rs!=null)
			{
				while(rs.next())
				{
					num = rs.getInt(1);
				}
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		if(num > 0)
		{
			this.msg = "D1.1 Không thể xóa đối tượng khác. Vì đã phát sinh dữ liệu định khoản";
			return false;
		}
		
		try {
			this.db.getConnection().setAutoCommit(false);
			String query = "DELETE ERP_DOITUONGKHAC WHERE pk_seq = " + this.id + "";
			if (!this.db.update(query))
			{
				this.msg = "D1.2 Không thể xóa";
				this.db.update("rollback");
				return false;
			}
			
			//SYN QUA DMS
			UtilitySyn.SynData(db, "ERP_DOITUONGKHAC", "ERP_DOITUONGKHAC", "PK_SEQ", this.id, "2", true);

			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(false);
		} catch (Exception e) {
			this.db.update("rollback");
			e.printStackTrace();
			this.msg = "D1.3 Không thể xóa";
			return false;
		}
		return true;
	}

	public boolean create()
	{
		checkUnique();
		if (this.msg.trim().length() > 0)
		{
			this.msg = "C1.1 " +  this.msg;
			return false;
		}
		
		try
		{
			this.db.getConnection().setAutoCommit(false);
			String query = 
				"insert into ERP_DOITUONGKHAC(MADOITUONG, TENDOITUONG, SOHIEUTAIKHOAN, TENTAIKHOAN--\n" +
				", TRANGTHAI, NPP_FK, DIACHI, DIENTHOAI--\n" +
				", FAX, TEN_NGUOILIENHE, DT_NGUOILIENHE, EMAIL_NGUOILIENHE--\n" +
				", SOTAIKHOAN, CONGTY_FK, NGANHANG_FK, CHINHANH_FK--\n" +
				", MASOTHUE, NGUOITAO, NGUOISUA, NGAYTAO--\n" +
				", NGAYSUA, quanlycongno)--\n" +
				"values('" + this.maDoiTuong + "', N'" + this.tenDoiTuong + "', '" + this.soHieuTaiKhoan + "', N'" + this.tenTaiKhoan + "'--\n" +
				","+this.trangThai+", " + this.nppId + ", N'" + this.diaChi + "', '" + this.dienThoai + "'--\n" +
				", '" + this.fax + "', N'" + this.tenNguoiLienHe + "', '" + this.dtNguoiLienHe + "', '" + this.emailNguoiLienHe + "'--\n" +
				", '" + this.soTaiKhoan + "', " + this.congTyId + ", " + this.nganHangId + ", " + this.chiNhanhId + "--\n" +
				", '" + this.maSoThue + "', " + this.userId + ", " + this.userId + ", '" + Utility.getCurrentDate() + "'--\n" +
				", '" + Utility.getCurrentDate() + "', '" + this.quanLyCongNo + "')--\n";
			
			query = UtilityKeToan.refactorQuery(query);
			
			System.out.println("cau lenh tao doi tuong khac:\n" + query + "\n-------------------------------------------------");
		
			if (!this.db.update(query))
			{
				this.msg = "C1.2 Lỗi tạo mới";
				db.getConnection().rollback();
				return false;
			}
			
			ResultSet rs = this.db.get("SELECT SCOPE_IDENTITY() AS ID");
			if (rs != null)
			{
				if (rs.next())
					this.id = rs.getString("ID");
				rs.close();
			}
			
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			
			//SYN QUA DMS	
			String str = UtilitySyn.SynData(db, "ERP_DOITUONGKHAC", "ERP_DOITUONGKHAC", "PK_SEQ", this.id, "0", false);
			System.out.println("Syn qua DMS"+str);
			if (str.trim().length() > 0)
			{
				this.msg = "C1.3 Lỗi không đồng bộ được xuống DMS";
				return false;
			}
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
			this.msg = "C1.4 Lỗi tạo mới";
			this.db.update("rollback");
			return false;
		}
		return true;
	}

	private void checkUnique()
	{
		String query = "";
		if (this.id != null && this.id.length() > 0)
			query = 
				"select " +
				"	(Select count(pk_seq) as countMa \n" +
				"	From ERP_DOITUONGKHAC \n" +
				"	Where maDoiTuong = '" + this.maDoiTuong + "' AND PK_SEQ !='" + this.id + "' and NPP_FK="+this.nppId+")\n" +
				"	, (Select count(pk_seq) as countMaSoThue \n" +
				"	From ERP_DOITUONGKHAC \n" +
				"	Where MASOTHUE = '" + this.maSoThue + "' AND PK_SEQ !='" + this.id + "' and NPP_FK="+this.nppId+")\n";
		else
			query = 
				"select " +
				"	(Select count(pk_seq) as countMa \n" +
				"	From ERP_DOITUONGKHAC \n" +
				"	Where maDoiTuong = '" + this.maDoiTuong + "' and NPP_FK="+this.nppId+")\n" +
				"	, (Select count(pk_seq) as countMaSoThue \n" +
				"	From ERP_DOITUONGKHAC \n" +
				"	Where MASOTHUE = '" + this.maSoThue + "' and NPP_FK="+this.nppId+")\n";
		System.out.println("kt ms:\n" + query);
		ResultSet rs = this.db.get(query);
		if (rs != null)
		{
			try
			{
				while (rs.next())
				{
					int countMa = rs.getInt(1);
					int countMaSoThue = rs.getInt(2);
					if (countMa > 0)
						this.msg = "CU1.2 Mã đối tượng này đã có, vui lòng chọn mã khác";
					
					if (countMaSoThue > 0)
						this.msg += "CU1.3 Mã số thuế này đã có, vui lòng chọn mã khác";
				}
				Statement st = rs.getStatement();
				rs.close();
				st.close();
			} catch (SQLException e)
			{
				e.printStackTrace();
				this.msg = "CU1.1 Lỗi kiểm tra mã, mã số thuế";
			}
		}
	}
	
	public void DBClose()
	{
		if (this.db != null)
		{
			this.db.shutDown();
			this.db = null;
		}
	}
	
	public boolean CheckNumerOrNot(String number)
	{
		if (number.trim().length() > 0)
			if (number.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+"))
				return true;
		return false;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCongTyId() {
		return congTyId;
	}

	public void setCongTyId(String congTyId) {
		this.congTyId = congTyId;
	}

	public String getMaDoiTuong() {
		return maDoiTuong;
	}

	public void setMaDoiTuong(String maDoiTuong) {
		this.maDoiTuong = maDoiTuong;
	}

	public String getTenDoiTuong() {
		return tenDoiTuong;
	}

	public void setTenDoiTuong(String tenDoiTuong) {
		this.tenDoiTuong = tenDoiTuong;
	}

	public String getSoHieuTaiKhoan() {
		return soHieuTaiKhoan;
	}

	public void setSoHieuTaiKhoan(String soHieuTaiKhoan) {
		this.soHieuTaiKhoan = soHieuTaiKhoan;
	}

	public String getTenTaiKhoan() {
		return tenTaiKhoan;
	}

	public void setTenTaiKhoan(String tenTaiKhoan) {
		this.tenTaiKhoan = tenTaiKhoan;
	}

	public String getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(String trangThai) {
		this.trangThai = trangThai;
	}

	public String getNppId() {
		return nppId;
	}

	public void setNppId(String nppId) {
		this.nppId = nppId;
	}

	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}

	public String getDienThoai() {
		return dienThoai;
	}

	public void setDienThoai(String dienThoai) {
		this.dienThoai = dienThoai;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getTenNguoiLienHe() {
		return tenNguoiLienHe;
	}

	public void setTenNguoiLienHe(String tenNguoiLienHe) {
		this.tenNguoiLienHe = tenNguoiLienHe;
	}

	public String getDtNguoiLienHe() {
		return dtNguoiLienHe;
	}

	public void setDtNguoiLienHe(String dtNguoiLienHe) {
		this.dtNguoiLienHe = dtNguoiLienHe;
	}

	public String getEmailNguoiLienHe() {
		return emailNguoiLienHe;
	}

	public void setEmailNguoiLienHe(String emailNguoiLienHe) {
		this.emailNguoiLienHe = emailNguoiLienHe;
	}

	public String getSoTaiKhoan() {
		return soTaiKhoan;
	}

	public void setSoTaiKhoan(String soTaiKhoan) {
		this.soTaiKhoan = soTaiKhoan;
	}

	public String getNganHangId() {
		return nganHangId;
	}

	public void setNganHangId(String nganHangId) {
		this.nganHangId = nganHangId;
	}

	public String getChiNhanhId() {
		return chiNhanhId;
	}

	public void setChiNhanhId(String chiNhanhId) {
		this.chiNhanhId = chiNhanhId;
	}

	public String getMaSoThue() {
		return maSoThue;
	}

	public void setMaSoThue(String maSoThue) {
		this.maSoThue = maSoThue;
	}

	public String getQuanLyCongNo() {
		return quanLyCongNo;
	}

	public void setQuanLyCongNo(String quanLyCongNo) {
		this.quanLyCongNo = quanLyCongNo;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<Erp_Item> getNppList() {
		return nppList;
	}

	public void setNppList(List<Erp_Item> nppList) {
		this.nppList = nppList;
	}

	public List<Erp_Item> getChiNhanhList() {
		return chiNhanhList;
	}

	public void setChiNhanhList(List<Erp_Item> chiNhanhList) {
		this.chiNhanhList = chiNhanhList;
	}

	public List<Erp_Item> getNganHangList() {
		return nganHangList;
	}

	public void setNganHangList(List<Erp_Item> nganHangList) {
		this.nganHangList = nganHangList;
	}


	public String getTenNganHang() {
		return tenNganHang;
	}

	public void setTenNganHang(String tenNganHang) {
		this.tenNganHang = tenNganHang;
	}

	public String getTenChiNhanh() {
		return tenChiNhanh;
	}

	public void setTenChiNhanh(String tenChiNhanh) {
		this.tenChiNhanh = tenChiNhanh;
	}
}