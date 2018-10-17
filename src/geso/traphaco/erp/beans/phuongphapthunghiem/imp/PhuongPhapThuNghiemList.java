package geso.traphaco.erp.beans.phuongphapthunghiem.imp;

import java.sql.ResultSet;

import geso.dms.db.sql.dbutils;
import geso.traphaco.erp.beans.phuongphapthunghiem.IPhuongPhapThuNghiemList;



public class PhuongPhapThuNghiemList implements IPhuongPhapThuNghiemList{
	private String MaPPTN;
	private String TenVT;
	private String DienGiai;
	private String LoaiTieuChi;
	private String YeuCauKyThuat;
	private String SoLuongMau;
	private String TrangThai;
	private String NgayTao;
	private String NgaySua;
	private String UserId;
	private String UserName;
	private String Msg;
	private ResultSet RsPPTNList;
	private ResultSet RsLoaiTieuChi;
	private ResultSet RsYeuCauKyThuat;
	dbutils db;
	
	public PhuongPhapThuNghiemList(){
		this.MaPPTN="";
		this.TenVT="";
		this.DienGiai="";
		this.LoaiTieuChi="";
		this.NgayTao="";
		this.NgaySua="";
		this.UserId="";
		this.UserName="";
		this.SoLuongMau="0";
		this.YeuCauKyThuat="";
		this.TrangThai="";
		this.Msg="";
		this.db=new dbutils();
	}

	public String getMaPPTN() {
		return MaPPTN;
	}

	public void setMaPPTN(String maPPTN) {
		MaPPTN = maPPTN;
	}

	public String getTenVT() {
		return TenVT;
	}

	public void setTenVT(String tenVT) {
		TenVT = tenVT;
	}

	public String getDienGiai() {
		return DienGiai;
	}

	public void setDienGiai(String dienGiai) {
		DienGiai = dienGiai;
	}

	public String getLoaiTieuChi() {
		return LoaiTieuChi;
	}

	public void setLoaiTieuChi(String loaiTieuChi) {
		LoaiTieuChi = loaiTieuChi;
	}

	public String getNgayTao() {
		return NgayTao;
	}

	public void setNgayTao(String ngayTao) {
		NgayTao = ngayTao;
	}

	public String getNgaySua() {
		return NgaySua;
	}

	public void setNgaySua(String ngaySua) {
		NgaySua = ngaySua;
	}

	public String getUserId() {
		return UserId;
	}

	public void setUserId(String userId) {
		UserId = userId;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getMsg() {
		return Msg;
	}

	public void setMsg(String msg) {
		Msg = msg;
	}

	public ResultSet getRsPPTNList() {
		return RsPPTNList;
	}

	public void setRsPPTNList(ResultSet rsPPTNList) {
		RsPPTNList = rsPPTNList;
	}

	public ResultSet getRsLoaiTieuChi() {
		return RsLoaiTieuChi;
	}

	public void setRsLoaiTieuChi(ResultSet rsLoaiTieuChi) {
		RsLoaiTieuChi = rsLoaiTieuChi;
	}
	
	public ResultSet getRsYeuCauKyThuat() {
		return RsYeuCauKyThuat;
	}

	public void setRsYeuCauKyThuat(ResultSet rsYeuCauKyThuat) {
		RsYeuCauKyThuat = rsYeuCauKyThuat;
	}

	public String getYeuCauKyThuat() {
		return YeuCauKyThuat;
	}

	public void setYeuCauKyThuat(String yeuCauKyThuat) {
		YeuCauKyThuat = yeuCauKyThuat;
	}

	public String getSoLuongMau() {
		return SoLuongMau;
	}

	public void setSoLuongMau(String soLuongMau) {
		SoLuongMau = soLuongMau;
	}
	
	public String getTrangThai() {
		return TrangThai;
	}

	public void setTrangThai(String trangThai) {
		TrangThai = trangThai;
	}

	public void creates(){
		String query="select PK_SEQ,MA,TEN from ERP_TIEUCHIKIEMNGHIEM where trangthai=1";
		System.out.println(query);
		this.RsLoaiTieuChi=this.db.get(query);
		String queryYCKT="select Pk_SEQ, TEN,MA from ERP_YEUCAUKYTHUAT WHERE TRANGTHAI=1";
		this.RsYeuCauKyThuat=this.db.get(queryYCKT);
	}
	public void init(){
		String query="select top(20) a.PK_SEQ,a.MA,a.TEN,a.DIENGIAI,a.NGAYTAO,a.TRANGTHAI,a.NGAYSUA,a.NGUOITAO,a.NGUOISUA,a.LOAITIEUCHI,b.TEN as TENLOAITIEUCHI,c.TEN as TENYEUCAUKYTHUAT "
				+ "\n from ERP_YEUCAUKYTHUAT c inner join ERP_PHUONGPHAPTHUNGHIEM a on c.PK_SEQ=a.YEUCAUKYTHUAT "
				+ "\n inner join ERP_TIEUCHIKIEMNGHIEM b on a.LOAITIEUCHI=b.PK_SEQ ";
		if (this.MaPPTN.trim().length() > 0)
		{
			query += " and  a.MA like N'%" + this.MaPPTN + "%' ";
		}
		 if (this.TenVT.trim().length() > 0)
		{
			query += " and a.TEN like N'%" + this.TenVT + "%'";
		}
		 if (this.LoaiTieuChi.trim().length() > 0)
		{
			query += " and a.LOAITIEUCHI='"+this.LoaiTieuChi+"'";
		}
		 if (this.YeuCauKyThuat.trim().length() > 0)
		{
			query += " and a.YEUCAUKYTHUAT='"+this.YeuCauKyThuat+"'";
		}
		 if (this.TrangThai.trim().length() > 0)
			{
				query += " and a.TRANGTHAI='"+this.TrangThai+"'";
			}
		query+=" order by NGAYTAO desc";
		System.out.println("======================="+query);
		this.RsPPTNList=this.db.get(query);
	}
	
	public void DBClose(){
		try {
			if (this.RsPPTNList!=null)this.RsPPTNList.close();
			if (this.RsLoaiTieuChi!=null)this.RsLoaiTieuChi.close();
			if (this.db!=null)this.db.shutDown();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
