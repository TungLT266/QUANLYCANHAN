package geso.traphaco.erp.beans.tamung.imp;

import java.sql.ResultSet;
import java.sql.SQLException;

import geso.traphaco.center.util.*;
import geso.traphaco.erp.beans.tamung.IErpTamUngList;
import geso.traphaco.center.db.sql.dbutils;

public class ErpTamUngList extends Phan_Trang implements IErpTamUngList
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String UserId, TuNgay, TrangThai, NhanVienId, TienTeId, SoTienTamUng,
			ThoiGianHoanUng, Msg, DenNgay,DoiTuongTamUng,NccId,TenHienThi, congtyId, nppdangnhap, sochungtu;
	ResultSet rsTamUng, rsTienTe;
	dbutils db;
	String dvthId;
	ResultSet dvthRs;
	ResultSet nguoitaoRs;    
	String nguotaoIds;


	public ErpTamUngList()
	{
		this.UserId = "";
		this.TuNgay = "";
		this.DenNgay = "";
		this.TrangThai = "";
		this.NhanVienId = "";
		this.TienTeId = "";
		this.SoTienTamUng = "";
		this.ThoiGianHoanUng = "";
		this.Msg = "";
		this.NccId="";
		this.DoiTuongTamUng="";
		this.TenHienThi="";
		this.congtyId = "";
		this.nppdangnhap = "";
		this.sochungtu = "";
		this.dvthId = "";
		this.nguotaoIds = "";
		this.db = new dbutils();
	}

	public String getTuNgay()
	{

		return this.TuNgay;
	}

	public void setTuNgay(String TuNgay)
	{
		this.TuNgay = TuNgay;
	}

	public String getNhanVienId()
	{

		return this.NhanVienId;
	}

	public void setNhanVienId(String NhanVienId)
	{

		this.NhanVienId = NhanVienId;
	}

	public String getTrangThai()
	{

		return this.TrangThai;
	}

	public void setTrangThai(String TrangThai)
	{
		this.TrangThai = TrangThai;
	}

	public String getSoTienTamUng()
	{

		return this.SoTienTamUng;
	}

	public void setSoTienTamUng(String SoTienTamUng)
	{
		this.SoTienTamUng = SoTienTamUng;
	}

	public String getTienTeId()
	{

		return this.TienTeId;
	}

	public void setTienTeId(String TienTeId)
	{
		this.TienTeId = TienTeId;
	}

	public String getThoiGianHoanUng()
	{
		return this.ThoiGianHoanUng;
	}

	public void setThoiGianHoanUng(String ThoiGianHoanUng)
	{
		this.ThoiGianHoanUng = ThoiGianHoanUng;
	}

	public String getUserId()
	{

		return this.UserId;
	}

	public void setUserId(String UserId)
	{
		this.UserId = UserId;
	}

	public String getMsg()
	{
		return this.Msg;
	}

	public void setMsg(String Msg)
	{
		this.Msg = Msg;
	}

	public ResultSet getRsTamUng()
	{
		return this.rsTamUng;
	}

	public void setRsTamUng(ResultSet rsTamUng)
	{
		this.rsTamUng = rsTamUng;
	}

	public ResultSet getRsTienTe()
	{
		return this.rsTienTe;
	}

	public void setRsTienTe(ResultSet rsTienTe)
	{
		this.rsTienTe = rsTienTe;
	}

	public void init()
	{
		this.getNppInfo();
		String query = 
				"SELECT TU.PK_SEQ,TU.THOIGIANHOANUNG,TU.NGAYTAMUNG,TU.SOTIENTAMUNG, TU.TRANGTHAI, \n"+
				"		CASE WHEN TU.NCC_FK IS NOT NULL THEN NCC.MA +',' +NCC.TEN  \n"+
				"		WHEN TU.NHANVIEN_FK IS NOT NULL THEN NV.MA +',' +NV.TEN \n"+
				"		END AS NHANVIEN, \n"+
				" 		TU.HINHTHUCHOANUNG, \n"+
				" 		TT.MA AS TIENTE,NT.TEN AS NGUOITAO,NS.TEN AS NGUOISUA,TU.NGAYSUA,TU.NGAYTAO, TU.LYDOTAMUNG, " +
				"		ISNULL(TU.ISDACHOT,0) ISDACHOT, "+
				" 		ISNULL(TU.ISTP,0) AS ISTP, ISNULL(TU.ISKTV,0) AS ISKTV, ISNULL(TU.ISKTT,0) AS ISKTT, " +
				"		ISNULL(TU.ISTHANHTOAN,0) AS ISTHANHTOAN, ISNULL(TU.ISHOANTAT,0) AS ISHOANTAT \n"+
				"FROM ERP_TAMUNG TU \n"+
				"INNER JOIN ERP_TIENTE TT ON TT.PK_SEQ = TU.TIENTE_FK \n"+
				"INNER JOIN NHANVIEN NT ON NT.PK_SEQ = TU.NGUOITAO \n"+
				"LEFT JOIN ERP_NHANVIEN NV ON NV.PK_SEQ = TU.NHANVIEN_FK \n"+
				"LEFT JOIN ERP_NHACUNGCAP NCC ON NCC.PK_SEQ = TU.NCC_FK \n"+
 				"LEFT JOIN ERP_DONVITHUCHIEN DVTH ON DVTH.PK_SEQ = NV.DVTH_FK \n " +
				"INNER JOIN NHANVIEN NS ON NS.PK_SEQ = TU.NGUOISUA WHERE 1 = 1 AND TU.NGUOITAO = "+this.UserId+" AND TU.CONGTY_FK = "+this.congtyId + "\n";
		
		if (this.TuNgay.trim().length() > 0)
			query += " AND TU.NGAYTAMUNG>='" + this.TuNgay + "' \n";

		if (this.DenNgay.trim().length() > 0)
			query += " AND TU.NGAYTAMUNG<='" + this.DenNgay + "' \n";

		if (this.TrangThai.trim().length() > 0 ){
			if(this.TrangThai.equals("0")){
			
				query += " AND TU.TRANGTHAI= '0' AND TU.ISDACHOT = '0' \n";
			
			}else if(this.TrangThai.equals("1")){
				
				query += " AND TU.TRANGTHAI= '0' AND TU.ISDACHOT = '1' \n";
				
			}else if(this.TrangThai.equals("2")){
				
				query += " AND TU.TRANGTHAI= '2'  ";
				
			}else if(this.TrangThai.equals("3")){
				
				query += " AND TU.TRANGTHAI= '1' AND TU.ISTHANHTOAN = '1' \n";
			}
		}		
			
		
		if(this.TrangThai.trim().length()>0 && this.TrangThai.equals("3"))
			query += " AND TU.ISTHANHTOAN = 1\n";
		
		if (this.NhanVienId.trim().length() > 0)
		{
			if (!this.NhanVienId.contains(" "))
				query += " AND (NV.pk_seq = " + this.NhanVienId + ")  \n";
			else query += " AND (NV.ten like N'%" + this.NhanVienId + "%' or NV.ma like N'%" + this.NhanVienId + "%')  \n";
		}
		else if (this.NccId.trim().length() > 0)
		{
			if (!this.NccId.contains(" "))
				query += " AND (NCC.pk_seq = " + this.NccId + ")  \n";
			else query += " AND (NCC.ten like N'%" + this.NccId + "%' or NCC.ma like N'%" + this.NccId + "%')  \n";
		}

		if (this.TienTeId.trim().length() > 0)
			query += " AND TT.MA ='" + this.TienTeId + "'  \n";

		if (this.SoTienTamUng.trim().length() > 0)
			query += " AND TU.SOTIENTAMUNG ='" + this.SoTienTamUng.replaceAll(",", "") + "'  \n";

		if (this.dvthId.trim().length() > 0)
			query += 
				" AND " + this.dvthId + " in (select cd.DVTH_FK\n" +
				"from ERP_CHUCDANH cd\n" +
				"inner join ERP_CHUCDANH_NHANVIEN cn on cn.CHUCDANH_FK = cd.PK_SEQ\n" +
				"where cn.NHANVIEN_FK = tu.nguoiTao\n" +
				"union all\n" +
				"select phongBan_FK from nhanVien where pk_seq = tu.nguoiTao)\n";

		if (this.nguotaoIds.trim().length() > 0)
			query += " AND NT.PK_SEQ = " + this.nguotaoIds + " \n";

		if (this.DoiTuongTamUng.length() > 0)
		{
			if(this.DoiTuongTamUng.equals("1"))
				query += " AND TU.NHANVIEN_FK IS NOT NULL  \n";
			else
				query += " AND TU.NCC_FK IS NOT NULL  \n";
		}
			
		if(this.sochungtu.length()>0)
		{
			query+= " AND TU.PK_SEQ = "+this.sochungtu + "\n";
		}

		System.out.println("Query Tam Ung List: \n" + query + "\n----------------------------------------------------------------");
		
		this.rsTamUng = createSplittingDataNew(this.db, 50, 10,"PK_SEQ DESC, TRANGTHAI asc", query);

		query = "SELECT PK_SEQ,MA,TEN  FROM ERP_TIENTE WHERE TRANGTHAI = 1";
		System.out.println(query);
		this.rsTienTe = this.db.get(query);
		
		query = "select pk_seq, ten from ERP_DONVITHUCHIEN ";
		this.dvthRs = db.get(query);
		
		query = "select pk_seq, ten from NHANVIEN where trangthai = '1' and pk_seq in ( select distinct NGUOITAO from ERP_MUAHANG ) ";
		this.nguoitaoRs = db.get(query);

	}

	public void DBClose()
	{
		try
		{
			if (rsTamUng != null)
				rsTamUng.close();
			if (rsTienTe != null)
				rsTienTe.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		if (db != null)
			this.db.shutDown();
	}

	public String getDenNgay()
	{
		return this.DenNgay;
	}

	public void setDenNgay(String DenNgay)
	{
		this.DenNgay = DenNgay;
	}

	public String getNccId() {
		return this.NccId;
	}

	public void setNccId(String NccId) {
		this.NccId=NccId;
	}

	public String getDoiTuongTamUng() {
		return this.DoiTuongTamUng;
	}

	public void setDoiTuongTamUng(String DoiTuongTamUng) {
		this.DoiTuongTamUng=DoiTuongTamUng;
	}
	
	public String getTenHienThi() {
		return this.TenHienThi;
	}
	
	public void setTenHienThi(String TenHienThi) {
		this.TenHienThi=TenHienThi;
	}

	public String getCongtyId() {
		return this.congtyId;
	}
	
	public void setCongtyId(String congtyId) {
		this.congtyId = congtyId;
	}
	
	public String getnppdangnhap() {
		return this.nppdangnhap;
	}
	
	public void setnppdangnhap(String nppdangnhap) {
		this.nppdangnhap = nppdangnhap;
	}
	
	private void getNppInfo()
	{		
		//Phien ban moi
		geso.traphaco.distributor.util.Utility util=new geso.traphaco.distributor.util.Utility();
		this.nppdangnhap=util.getIdNhapp(this.UserId);
	}

	public String getSochungtu() {
		return this.sochungtu;
	}

	public void setSochungtu(String sochungtu) {
		this.sochungtu = sochungtu;
	}
	
	public String getDvthId() 
	{
		return this.dvthId;
	}

	public void setDvthId(String dvthid) 
	{
		this.dvthId = dvthid;
	}

	public ResultSet getDvthList() 
	{
		return this.dvthRs;
	}
	
	public void setDvthList(ResultSet dvthlist) 
	{
		this.dvthRs = dvthlist;
	}
	
	public ResultSet getNguoitaoRs() {
		
		return this.nguoitaoRs;
	}

	public void setNguoitaoRs(ResultSet nguoitaoRs) {
		this.nguoitaoRs = nguoitaoRs;
	}
	
	public void setNguoitaoIds(String nspIds) {
		this.nguotaoIds = nspIds;
	}
	
	public String getNguoitaoIds() {
		return this.nguotaoIds;
	}
}
