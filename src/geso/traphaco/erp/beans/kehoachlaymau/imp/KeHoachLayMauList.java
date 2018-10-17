package geso.traphaco.erp.beans.kehoachlaymau.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import geso.traphaco.erp.beans.kehoachlaymau.IKeHoachLayMau;
import geso.traphaco.erp.beans.kehoachlaymau.IKeHoachLayMauList;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;

public class KeHoachLayMauList extends Phan_Trang implements IKeHoachLayMauList{
	
	private static final long serialVersionUID = 1L;
	String userId;
	String msg;
	dbutils db;
	private String ngayKeHoach;
	private String soTT;
	private String phongBanId; 
	private String id;
	private String ctyId;
	private String nppId; 
	private String sanphamId; 
	private String trangthai;
	
	private ResultSet rsPhongBan; 
	private List<PhieuKiemDinh> dsPhieuKiemDinhs;
	private ResultSet rsSanPham;
	private ResultSet rsTrangThai;
	private ResultSet rsKeHoach;
	
	public KeHoachLayMauList(){
		this.userId="";
		this.msg="";
		this.id = "";
		this.db = new dbutils(); 
		this.ctyId = "";
		this.nppId = "";
		this.dsPhieuKiemDinhs = new ArrayList<PhieuKiemDinh>();
		this.ngayKeHoach = "";
		this.phongBanId = "";
		this.sanphamId = "";
		this.trangthai = "";
	}
	
	public KeHoachLayMauList(String id){
		this.userId="";
		this.msg="";
		this.id = id;
		this.db = new dbutils(); 
		this.ctyId = "";
		this.nppId = "";
		this.dsPhieuKiemDinhs = new ArrayList<PhieuKiemDinh>();
		this.ngayKeHoach = "";
		this.phongBanId = "";
		this.sanphamId = "";
		this.trangthai = ""; 
	}
	
	public void createRs(){
		
		String query = "";
		 
		query = "SELECT PK_SEQ , MA + ' - ' + TEN AS TEN FROM ERP_DONVITHUCHIEN WHERE TRANGTHAI=1";
		this.rsPhongBan = db.get(query);
 
		/*query = "select PK_SEQ, MA + ' - ' + TEN AS TEN from ERP_LOAIMAUKIEMNGHIEM WHERE TRANGTHAI=1";
		this.rsPhieuNhanHang = db.get(query);*/
		 
		query = "select PK_SEQ, MA + ' - ' + TEN AS TEN from ERP_SANPHAM SP WHERE TRANGTHAI=1"; 
		this.rsSanPham = db.get(query);
		
		query = "select 0 as pk_seq, N'Chưa chốt' as ten union select 1 as pk_seq, N'Đã chốt' as ten union select 2 as pk_seq, N'Đã xóa' as ten";
		this.rsTrangThai = db.get(query);
		
	}
 
	public ResultSet getRsSanPham() {
		return rsSanPham;
	}

	public void setRsSanPham(ResultSet rsSanPham) {
		this.rsSanPham = rsSanPham;
	}
	
	

	/*private void layPhieuKiemDinh(String soPhieuKiemDinh2, String sanphamId) {
		this.dsPhieuKiemDinhs.clear();
		String query =
		
				"SELECT SP.PK_SEQ, SP.TEN, SP.MA, DVT.DONVI DONVI, CT.SOLO, ISNULL(CT.NGAYNHAPKHO,'') NGAYNHAPKHO, CT.NGAYHETHAN, \r\n" + 
				" CT.MATHUNG, CT.MAME, SUM( CT.SOLUONG ) SOLUONG  FROM ERP_YEUCAUKIEMDINH_THUNG_CHITIET CT\r\n" + 
				" INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ= CT.SANPHAM_FK\r\n" + 
				" INNER JOIN DONVIDOLUONG DVT ON DVT.PK_SEQ = SP.DVDL_FK\r\n" + 
				" WHERE CT.YEUCAUKIEMDINH_FK = '"+soPhieuKiemDinh2+"' \r\n" +
				" AND CT.SANPHAM_FK = " + sanphamId + " \r\n"+
				" GROUP BY SP.PK_SEQ, SP.TEN, SP.MA, DVT.DONVI, CT.SOLO, ISNULL(CT.NGAYNHAPKHO,''), CT.NGAYHETHAN, \r\n" + 
				" CT.MATHUNG, CT.MAME";
		System.out.println(query);
		ResultSet rs = db.get(query);
		try {
			PhieuKiemDinh phieuKiemDinh = null;
			while(rs.next()){
				phieuKiemDinh = new PhieuKiemDinh();
				phieuKiemDinh.setMaSp(rs.getString("MA"));
				phieuKiemDinh.setTenSp(rs.getString("TEN"));
				phieuKiemDinh.setDvt(rs.getString("DONVI"));
				phieuKiemDinh.setSanPhamId(rs.getString("PK_SEQ"));
				phieuKiemDinh.setLoHang(rs.getString("SOLO"));
				phieuKiemDinh.setNgaySX(rs.getString("NGAYNHAPKHO"));
				phieuKiemDinh.setNgayHetHan(rs.getString("NGAYHETHAN"));
				phieuKiemDinh.setMaThung(rs.getString("MATHUNG"));
				phieuKiemDinh.setMaMe(rs.getString("MAME"));
				phieuKiemDinh.setSoLuongPhieuNop(rs.getString("SOLUONG"));
				
				this.dsPhieuKiemDinhs.add(phieuKiemDinh);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}*/
 
	public ResultSet getRsKeHoach() {
		return rsKeHoach;
	}

	public void setRsKeHoach(ResultSet rsKeHoach) {
		this.rsKeHoach = rsKeHoach;
	}

	public String getTrangthai() {
		return trangthai;
	}

	public void setTrangthai(String trangthai) {
		this.trangthai = trangthai;
	}

	public ResultSet getRsTrangThai() {
		return rsTrangThai;
	}

	public void setRsTrangThai(ResultSet rsTrangThai) {
		this.rsTrangThai = rsTrangThai;
	}

	public List<PhieuKiemDinh> getDsPhieuKiemDinhs() {
		return dsPhieuKiemDinhs;
	}

	public void setDsPhieuKiemDinhs(List<PhieuKiemDinh> dsPhieuKiemDinhs) {
		this.dsPhieuKiemDinhs = dsPhieuKiemDinhs;
	}

	 
	public String getCtyId() {
		return ctyId;
	}

	public void setCtyId(String ctyId) {
		this.ctyId = ctyId;
	}

	public String getNppId() {
		return nppId;
	}

	public void setNppId(String nppId) {
		this.nppId = nppId;
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
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

	public dbutils getDb() {
		return db;
	}

	public void setDb(dbutils db) {
		this.db = db;
	}
 
	public String getPhongBanId() {
		return phongBanId;
	}

	public void setPhongBanId(String phongBanId) {
		this.phongBanId = phongBanId;
	}
 
	public ResultSet getRsPhongBan() {
		return rsPhongBan;
	}

	public void setRsPhongBan(ResultSet rsPhongBan) {
		this.rsPhongBan = rsPhongBan;
	}
 
	public void DBclose() {
 
	}
 
	public String getNgayKeHoach() {
		return ngayKeHoach;
	}

	public void setNgayKeHoach(String ngayKeHoach) {
		this.ngayKeHoach = ngayKeHoach;
	}

	public String getSoTT() {
		return soTT;
	}

	public void setSoTT(String soTT) {
		this.soTT = soTT;
	}
 
	public String getSanphamId() {
		return sanphamId;
	}

	public void setSanphamId(String sanphamId) {
		this.sanphamId = sanphamId;
	}

	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	public void init() {
		String query = "select KH.PK_SEQ as pk_seq, KH.NGAYKEHOACH as ngaykehoach, DVTH.TEN as bophanthuchien, SP.TEN as sanpham, NV.ten as nguoitao, KH.NGAYTAO as ngaytao, KH.TRANGTHAI as trangthai\r\n" + 
				"from ERP_KEHOACHLAYMAU KH\r\n" + 
				"LEFT JOIN ERP_SANPHAM SP ON KH.SANPHAM_FK=SP.PK_SEQ\r\n" + 
				"LEFT JOIN NHANVIEN NV ON NV.PK_SEQ=KH.NGUOITAO\r\n" + 
				"LEFT JOIN ERP_DONVITHUCHIEN DVTH ON DVTH.PK_SEQ=KH.DVTH_FK \r\n" + 
				"WHERE 1=1";
				
		
		if(this.ngayKeHoach.length() > 0){
			query += "AND KH.NGAYKEHOACH='"+this.ngayKeHoach+"' \r\n";
		}
		
		if(this.phongBanId.length() > 0){
			query += "AND DVTH.PK_SEQ='"+this.phongBanId+"' \r\n";
		}
		
		if(this.sanphamId.length() > 0){
			query += "AND SP.PK_SEQ='"+this.sanphamId+"' \r\n";
		}
		
		if(this.trangthai.length() > 0){
			query += "AND KH.TRANGTHAI='"+this.trangthai+"' \r\n";
		}
		
		query += "ORDER BY KH.PK_SEQ ASC";
		
		System.out.println("________init sp______" + query);
		
		this.rsKeHoach = db.get(query);
	}
	
	/*private void initSanPhamKiemDinh(String id2) throws SQLException {
		String query = "SELECT [YCLAYMAUKIEMNGHIEM_FK]\r\n" + 
				"      ,[SANPHAM_FK]\r\n" + 
				"      ,[SOLUONG]\r\n" + 
				"      ,[SOLUONGLAYMAU]\r\n" + 
				"      ,[SOLUONGMAULUU]\r\n" + 
				"      ,[SLOLUONGONDINH]\r\n" + 
				"      ,[CONLAI]\r\n" + 
				"      ,[MASP]\r\n" + 
				"      ,[TENSP]\r\n" + 
				"      ,[DVT]\r\n" + 
				"      ,[LOHANG]\r\n" + 
				"      ,[NGAYSX]\r\n" + 
				"      ,[NGAYHH]\r\n" + 
				"      ,[MATHUNG]\r\n" + 
				"      ,[MAME]\r\n" + 
				"      ,[THOIGIANHUYMAU]\r\n" + 
				"      ,[STT]\r\n" + 
				"  FROM [dbo].[ERP_YCLAYMAUKIEMNGHIEM_CHITIET]\r\n" + 
				"  WHERE [YCLAYMAUKIEMNGHIEM_FK] = " + id2 + " \r\n" + 
				"  ORDER BY STT ASC \r\n";
		
		System.out.println("________init sp______" + query);
		
		ResultSet rs = db.get(query);
		PhieuKiemDinh phieuKiemDinh = null;
		while(rs.next()){
			phieuKiemDinh = new PhieuKiemDinh();
			 
			getDsPhieuKiemDinhs().add(phieuKiemDinh);
		}
	}*/
	
	
}
