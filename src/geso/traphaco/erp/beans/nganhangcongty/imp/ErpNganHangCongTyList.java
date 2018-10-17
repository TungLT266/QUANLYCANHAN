package geso.traphaco.erp.beans.nganhangcongty.imp;

import java.sql.ResultSet;
import java.sql.SQLException;

import geso.traphaco.erp.beans.nganhangcongty.IErpNganHangCongTyList;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Phan_Trang;

public class ErpNganHangCongTyList extends Phan_Trang implements IErpNganHangCongTyList
{

	private static final long serialVersionUID = -2500226131122702265L;
	String Id, CongTy, ctyId, SoTaiKhoan, ChuTaiKhoan, LoaiTien, ChiNhanh, NganHang;
	ResultSet rsNganHang, rsCongTy, rsLoaiTien, rsNganHangCongTy, rsChiNhanh;
	dbutils db;

	String chixem;
	
	public ErpNganHangCongTyList()
	{
		this.Id = "";
		this.CongTy = "";
		this.ctyId = "";
		this.SoTaiKhoan = "";
		this.ChuTaiKhoan = "";
		this.LoaiTien = "";
		this.ChiNhanh = "";
		this.NganHang = "";
		this.chixem = "0";
		this.db = new dbutils();
	}

	public String getId()
	{
		return this.Id;
	}

	public void setId(String id)
	{
		this.Id = id;
	}

	public String getCongTy()
	{
		return this.CongTy;
	}

	public void setCongTy(String congty)
	{
		this.CongTy = congty;
	}

	public String getCtyId()
	{
		return this.ctyId;
	}

	public void setCtyId(String ctyId)
	{
		this.ctyId = ctyId;
	}

	public String getChuTaiKhoan()
	{
		return this.ChuTaiKhoan;
	}

	public void setChuTaiKhoan(String chutaikhoan)
	{
		this.ChuTaiKhoan = chutaikhoan;
	}

	public String getLoaiTien()
	{
		return this.LoaiTien;
	}

	public void setLoaiTien(String loaitien)
	{
		this.LoaiTien = loaitien;
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

	public String getChiNhanh()
	{
		return this.ChiNhanh;
	}

	public void setChiNhanh(String chinhanh)
	{
		this.ChiNhanh = chinhanh;
	}

	public void init(String congtyId)
	{
		String query = 	" Select n.PK_SEQ,nt.Ten as NguoiTao,ct.Ma As CongTy,n.SoTaiKhoan, ns.Ten As NguoiSua,n.TrangThai,n.NgayTao,n.NgaySua, \n" +
						" t.Ten as LoaiTien,nh.Ma as NganHang, \n"+
						" c.Ten As ChiNhanh From Erp_NganHang_CongTy n  \n"+
						" INNER JOIN NhanVien nt on nt.PK_SEQ=n.NguoiTao \n"+
						" INNER JOIN NhanVien ns on ns.PK_SEQ=n.NguoiSua \n"+
						" LEFT JOIN Erp_NganHang nh on nh.PK_SEQ=n.NganHang_FK \n"+
						" LEFT JOIN Erp_TienTe t on t.PK_SEQ=n.TienTe_FK \n"+
						" LEFT JOIN Erp_ChiNhanh c on c.PK_SEQ=n.ChiNhanh_FK  \n"+
						" LEFT JOIN ERP_CONGTY ct on ct.PK_SEQ=n.CongTy_FK  Where n.pk_seq > 0 "; // AND n.CONGTY_FK = '" + this.ctyId + "' ";
		
		if(!congtyId.equals("100000")) //TONG CONG TY
			query += " AND n.congty_fk = '" + congtyId + "' ";
		
		if (this.NganHang.length() > 0)
			query += " And nh.Ma Like N'%" + this.NganHang + "%' OR nh.Ten LIKE N'%" + this.NganHang + "%' ";

		if (this.ChiNhanh.length() > 0)
			query += " And c.Ma Like N'%" + this.ChiNhanh + "%' OR c.Ten LIKE N'%" + this.ChiNhanh + "%' ";

		if (this.LoaiTien.length() > 0)
			query += " And n.TienTe_FK =" + this.LoaiTien + "";
		this.rsNganHangCongTy = createSplittingDataNew(this.db, 50, 10, " PK_SEQ DESC,TRANGTHAI  ", query);
		System.out.println("ErpNganHangCongTyList " + query);
		createaRs();
	}

	public void createaRs()
	{
		String query = "Select PK_SEQ,Ma,Ten From Erp_NganHang Where TrangThai=1 ";
		this.rsNganHang = this.db.get(query);
		query = "Select PK_SEQ,Ma,Ten From Erp_ChiNhanh Where TrangThai=1 ";
		this.rsChiNhanh = this.db.get(query);
		query = "Select PK_SEQ,Ma,Ten From Erp_TienTe Where TrangThai=1";
		this.rsLoaiTien = this.db.get(query);
		query = "Select PK_SEQ,Ma,Ten From ERP_CONGTY Where TrangThai=1";
		this.rsCongTy = this.db.get(query);
		System.out.println("createaRs " + query);
	}

	public ResultSet getCongTyRs()
	{
		return this.rsCongTy;
	}

	public void setCongTyRs(ResultSet congty)
	{
		this.rsCongTy = congty;
	}

	public ResultSet getLoaiTienRs()
	{
		return this.rsLoaiTien;
	}

	public void setLoaiTien(ResultSet loaitien)
	{
		this.rsLoaiTien = loaitien;
	}

	public ResultSet getChiNhanhRs()
	{
		return this.rsChiNhanh;
	}

	public void setChiNhanhRs(ResultSet chinhanh)
	{
		this.rsChiNhanh = chinhanh;
	}

	public ResultSet getNganHangCongTy()
	{
		return this.rsNganHangCongTy;
	}

	public void setNganHangCongTy(ResultSet nganhangcongty)
	{
		this.rsNganHangCongTy = nganhangcongty;
	}

	public void setNganHang(ResultSet nganhang)
	{
		this.rsNganHang = nganhang;
	}

	public ResultSet getNganHangRs()
	{
		return this.rsNganHang;
	}

	public void closeDB()
	{
		try
		{
			if (rsNganHang != null)
				rsNganHang.close();
			if (rsCongTy != null)
				rsCongTy.close();
			if (rsLoaiTien != null)
				rsLoaiTien.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public void setChixem(String chixem) {
		
		this.chixem = chixem;
	}

	public String getChixem() {
		
		return this.chixem;
	}
	
}
