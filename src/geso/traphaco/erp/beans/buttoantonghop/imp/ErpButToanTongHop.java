package geso.traphaco.erp.beans.buttoantonghop.imp;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.center.util.UtilityKeToan;
import geso.traphaco.erp.beans.buttoantonghop.IErpButToanTongHop;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

public class ErpButToanTongHop implements IErpButToanTongHop
{
	String congtyId;
	String Id, NgayButToan, DienGiai, UserId, Msg, soChungTu;
	String[] Sotien, TaiKhoanNoIds, TaiKhoanCoIds, DoituongNo, DoituongCo, TtcpNoIds, TtcpCoIds, kbhIds, dg, dc_noIds,dc_noTens, dc_coIds, dc_coTens, MavvIds, DiabanIds, TinhthanhIds, BenhvienIds, SanphamIds, PKSEQIds, SoTienNT;

	int count;
	ResultSet kho_NoRs, kho_CoRs;
	ResultSet nganhang_NoRs, nganhang_CoRs;
	ResultSet Ncc_NoRs, Ncc_CoRs;
	ResultSet Kh_NoRs, Kh_CoRs;
	ResultSet Ts_NoRs, Ts_CoRs;
	ResultSet Nv_NoRs, Nv_CoRs;
	ResultSet TrungtamchiphiNoRs, TrungtamchiphiCoRs;
	ResultSet TaiKhoanKT_NoRs, TaiKhoanKT_CoRs; 
	ResultSet kbh, mavvRs, diabanRs, tinhthanhRs, benhvienRs, sanphamRs;
	ResultSet rsTienTe;
	String npp_duocchon_id;
	String nppId;
	String tienTe;
	ResultSet rsSanPham;
	double tiGia;
	
	private String trangThai;
	dbutils db;
	
	
	public dbutils getDb() {
		return db;
	}
	public void setDb(dbutils db) {
		this.db = db;
	}
	public String getBTTTHTiepTheo(){
		
		int pk_seq = 0;
		String query = "select top 1 PK_SEQ FROM ERP_BUTTOANTONGHOP ORDER BY PK_SEQ DESC";
		ResultSet rspk = db.get(query);
		try {
			if(rspk != null){
				if(rspk.next()){
					pk_seq = rspk.getInt("PK_SEQ") +1;
				}
				rspk.close();
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return String.valueOf(pk_seq);
	}
	public String thangnam(String ngay){
		String tn = "";
		String[]tach = ngay.split("-");
		tn = tach[1] + tach[0];
		
		return tn;
	}
	
	public ErpButToanTongHop(String Id)
	{
		this.Id = Id;
		this.NgayButToan = "";
		this.DienGiai = "";
		this.UserId = "";
		this.Msg = "";
		this.count=0;
		this.npp_duocchon_id = "";
		this.nppId = "";
		this.tiGia = 0;
		this.tienTe = "";
		this.trangThai = "0";
		db = new dbutils();
	}
	
	public ErpButToanTongHop()
	{
		this.Id = "";
		this.NgayButToan = "";
		this.DienGiai = "";
		this.UserId = "";
		this.Msg = "";
		this.count=0;
		this.npp_duocchon_id = "";
		this.nppId = "";
		this.tiGia = 1.;
		this.tienTe = "100000";
		this.soChungTu = "";
		this.trangThai = "0";
		db = new dbutils();
	}
	
	
	public String getSoChungTu() {
		return soChungTu;
	}
	public void setSoChungTu(String soChungTu) {
		this.soChungTu = soChungTu;
	}
	public double getTiGia() {
		return tiGia;
	}
	public void setTiGia(double tiGia) {
		this.tiGia = tiGia;
	}
	public String getTienTe() {
		return tienTe;
	}
	public void setTienTe(String tienTe) {
		this.tienTe = tienTe;
	}
	public ResultSet getRsSanPham() {
		String query = "select PK_SEQ, MA , TEN  from ERP_SANPHAM where (LOAISANPHAM_FK = 100002 or LOAISANPHAM_FK = 100003) AND TRANGTHAI = 1";
		
		return db.getScrol(query);
	}
	public void setRsSanPham(ResultSet rsSanPham) {
		this.rsSanPham = rsSanPham;
	}
	public ResultSet getRsTienTe() {
		
		String query = "select PK_SEQ, MA, TEN from ERP_TIENTE where TRANGTHAI = 1";
		rsTienTe = db.get(query);
		return rsTienTe;
	}
	public void setRsTienTe(ResultSet rsTienTe) {
		this.rsTienTe = rsTienTe;
	}
	public String getId()
	{

		return this.Id;
	}

	public void setId(String Id)
	{
		this.Id = Id;

	}

	public String getNgayButToan()
	{

		return this.NgayButToan;
	}

	public void setNgayButToan(String NgayButToan)
	{
		this.NgayButToan = NgayButToan;
	}

	public String getDienGiai()
	{

		return this.DienGiai;
	}

	public void setDienGiai(String DienGiai)
	{
		this.DienGiai = DienGiai;
	}

	public String getMsg()
	{

		return this.Msg;
	}

	public void setMsg(String Msg)
	{
		this.Msg = Msg;
	}

	public String getUserId()
	{

		return this.UserId;
	}

	public void setUserId(String UserId)
	{
		this.UserId = UserId;
	}


	public String[] getTaiKhoanNoIds()
	{

		return this.TaiKhoanNoIds;
	}

	public void setTaiKhoanNoIds(String[] TaiKhoanNoIds)
	{
		this.TaiKhoanNoIds = TaiKhoanNoIds;
	}

	public String[] getTaiKhoanCoIds()
	{

		return this.TaiKhoanCoIds;
	}

	public void setTaiKhoanCoIds(String[] TaiKhoanCoIds)
	{
		this.TaiKhoanCoIds = TaiKhoanCoIds;
	}

	public String[] getSotien()
	{

		return this.Sotien;
	}

	public void setSotien(String[] sotien)
	{
		this.Sotien = sotien;
	}

	public String[] getTtcpNoIds()
	{

		return this.TtcpNoIds;
	}

	public void setTtcpNoIds(String[] TtcpNoIds)
	{
		this.TtcpNoIds = TtcpNoIds;
	}
	
	public String[] getTtcpCoIds()
	{

		return this.TtcpCoIds;
	}

	public void setTtcpCoIds(String[] TtcpCoIds)
	{
		this.TtcpCoIds = TtcpCoIds;
	}

	public String[] getDg()
	{

		return this.dg;
	}

	public void setDg(String[] dg)
	{
		this.dg = dg;
	}

	public String[] getDungcho_No()
	{

		return this.dc_noIds;
	}

	public void setDungcho_No(String[] dc_noIds)
	{
		this.dc_noIds = dc_noIds;
	}
	
	public String[] getDungcho_Co()
	{

		return this.dc_coIds;
	}

	public void setDungcho_Co(String[] dc_coIds)
	{
		this.dc_coIds = dc_coIds;
	}
	
	public String[] getKbhIds()
	{

		return this.kbhIds;
	}

	public void setKbhIds(String[] kbhIds)
	{
		this.kbhIds = kbhIds;
	}
	
	public ResultSet getSanpham_NoRs(){
		String query = 	" SELECT PK_SEQ,   isnull(MA,ma)  AS MA, TEN + '' + QUYCACH AS TEN   FROM ERP_SANPHAM " +
						" WHERE TRANGTHAI = 1 and loaisanpham_fk >=100013 " +
						" order by isnull(MA,ma)  ";
		return this.db.getScrol(query);
	}

	public ResultSet getSanpham_CoRs(){
		String query = 	" SELECT PK_SEQ,   isnull(MA,ma)  AS MA, TEN + '' + QUYCACH AS TEN   FROM ERP_SANPHAM " +
						" WHERE TRANGTHAI = 1 and loaisanpham_fk >=100013 " +
						" order by isnull(MA,ma)  ";
		System.out.println("lay san pham co:\n" + query + "\n--------------------------------------");
		return this.db.getScrol(query);
	}

	public ResultSet getKho_NoRs(){
		String query = 	"SELECT DISTINCT  KHO.PK_SEQ, KHO.TEN " +
						"FROM NHAPP_KHO NPP_KHO " +
						"INNER JOIN KHO KHO ON KHO.PK_SEQ = NPP_KHO.KHO_FK " +
						"WHERE NPP_FK = (SELECT PK_SEQ FROM NHAPHANPHOI WHERE CONGTY_FK = " + this.congtyId + ")";
		System.out.println("lay san pham no:\n" + query + "\n--------------------------------------");
		return this.db.getScrol(query);
	}
	
	public ResultSet getKho_CoRs(){
		String query = 	"SELECT DISTINCT  KHO.PK_SEQ, KHO.TEN " +
						"FROM NHAPP_KHO NPP_KHO " +
						"INNER JOIN KHO KHO ON KHO.PK_SEQ = NPP_KHO.KHO_FK " +
						"WHERE NPP_FK = (SELECT PK_SEQ FROM NHAPHANPHOI WHERE CONGTY_FK = " + this.congtyId + ")";
		System.out.println("Kho_Co: " + query);
		return this.db.getScrol(query);
	}

	public ResultSet getNganhang_NoRs(){
		String query = 	"SELECT DISTINCT NH.PK_SEQ, MA, TEN " +
						"FROM ERP_NGANHANG NH " +
						"INNER JOIN ERP_NGANHANG_CONGTY NH_CTY ON NH_CTY.NGANHANG_FK = NH.PK_SEQ " +
						"WHERE  NH.TRANGTHAI=1 AND NH_CTY.CONGTY_FK = " + this.congtyId + "";
		System.out.println("Ngân hàng - No: " + query);
		return this.db.getScrol(query);
	}
	
	public ResultSet getNganhang_CoRs(){
		String query = 	"SELECT DISTINCT NH.PK_SEQ, MA, TEN " +
						"FROM ERP_NGANHANG NH " +
						"INNER JOIN ERP_NGANHANG_CONGTY NH_CTY ON NH_CTY.NGANHANG_FK = NH.PK_SEQ " +
						"WHERE  NH.TRANGTHAI=1 AND NH_CTY.CONGTY_FK = " + this.congtyId + "";
		System.out.println("Ngân hàng - Co: " + query);
		return this.db.getScrol(query);
	}

	public ResultSet getNcc_NoRs(){
		String query = "SELECT PK_SEQ, MA, TEN FROM ERP_NHACUNGCAP WHERE  TRANGTHAI=1 AND CONGTY_FK = " + this.congtyId + "  and duyet = '1'";
		return this.db.getScrol(query);
	}
	
	public ResultSet getNcc_CoRs(){
		String query = "SELECT PK_SEQ, MA, TEN FROM ERP_NHACUNGCAP WHERE  TRANGTHAI=1 AND CONGTY_FK = " + this.congtyId + "  and duyet = '1'";
		return this.db.getScrol(query);
	}
	
	public ResultSet getTaisan_NoRs(){
		String query = "SELECT PK_SEQ, MA, DIENGIAI FROM ERP_TAISANCODINH WHERE  TRANGTHAI=1 AND CONGTY_FK = " + this.congtyId + " ";
		return this.db.getScrol(query);
	}

	public ResultSet getTaisan_CoRs(){
		String query = "SELECT PK_SEQ, MA, DIENGIAI FROM ERP_TAISANCODINH WHERE  TRANGTHAI=1 AND CONGTY_FK = " + this.congtyId + " ";
		return this.db.getScrol(query);
	}
	
	public ResultSet getKhachhang_NoRs(){
		String query =  "SELECT CONVERT(VARCHAR, PK_SEQ) + '-0' AS PK_SEQ, MA, TEN " +
						"FROM KHACHHANG WHERE  TRANGTHAI = 1 AND CONGTY_FK = " + this.congtyId + " " +
						"UNION ALL " +
						"SELECT CONVERT(VARCHAR, PK_SEQ) + '-1' AS PK_SEQ, MA, TEN  " +
						"FROM NHAPHANPHOI WHERE  TRANGTHAI=1 AND TRUCTHUOC_FK = (SELECT PK_SEQ FROM NHAPHANPHOI WHERE CONGTY_FK = " + this.congtyId + ") " +
						"UNION ALL " +
						"SELECT CONVERT(VARCHAR, PK_SEQ) + '-2' AS PK_SEQ, MA, TEN  " +
						"FROM ERP_NHANVIEN WHERE  TRANGTHAI=1  AND CONGTY_FK = " + this.congtyId + " " +
						"ORDER BY TEN ";
		System.out.println("Khach hang - No: " + query);
		return this.db.getScrol(query);
	}

	public ResultSet getKhachhang_CoRs(){
		String query =  "SELECT CONVERT(VARCHAR, PK_SEQ) + '-0' AS PK_SEQ, MA, TEN " +
						"FROM KHACHHANG WHERE  TRANGTHAI = 1 AND CONGTY_FK = " + this.congtyId + " " +
						"UNION ALL " +
						"SELECT CONVERT(VARCHAR, PK_SEQ) + '-1' AS PK_SEQ, MA, TEN  " +
						"FROM NHAPHANPHOI WHERE  TRANGTHAI=1 AND TRUCTHUOC_FK = (SELECT PK_SEQ FROM NHAPHANPHOI WHERE CONGTY_FK = " + this.congtyId + ") " +
						"UNION ALL " +
						"SELECT CONVERT(VARCHAR, PK_SEQ) + '-2' AS PK_SEQ, MA, TEN  " +
						"FROM ERP_NHANVIEN WHERE  TRANGTHAI=1 AND CONGTY_FK = " + this.congtyId + " " +
						"ORDER BY TEN ";
		System.out.println("Khach hang - Co: " + query);
		return this.db.getScrol(query);
	}
	
	public ResultSet getKbhRs(){
		String query =  "SELECT CONVERT(VARCHAR, PK_SEQ) AS PK_SEQ, DIENGIAI AS TEN " +
						"FROM KENHBANHANG WHERE  TRANGTHAI = 1  " ;
		System.out.println(query);
		return this.db.getScrol(query);
	}
	public void masodautaikhoan(String taikhoan){
		
	}
	public String getSoHieuTK(String tknId){
		String soHieuTaiKhoan = "NULL";
		String query = "select SOHIEUTAIKHOAN from ERP_TAIKHOANKT where 1 = 1 ";
		if(tknId != null && !tknId.trim().equals("")){
			query = query + " AND PK_SEQ = " + tknId;
		}
		
		ResultSet rs = db.get(query);
		if(rs != null){
			try {
				if(rs.next()){
					soHieuTaiKhoan = rs.getString("SOHIEUTAIKHOAN");
				}
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return soHieuTaiKhoan;
	}
	
	public ResultSet getTrungTamChiPhiNoRs(String tknoId) {
		if (tknoId == null || tknoId.trim().length() == 0)
			return null;
		/*String query = 	"SELECT PK_SEQ,MA,TEN FROM ERP_TRUNGTAMCHIPHI " +
						"WHERE TRANGTHAI = 1 AND CONGTY_FK = " + this.congtyId + " " +
						"AND 1 = (SELECT ISNULL(COTTCHIPHI, 0) FROM ERP_TAIKHOANKT WHERE PK_SEQ = " + tknoId + " AND CONGTY_FK = " + this.congtyId + ") ";*/

		//TRUNG TAM CHI PHI KHONG PHAN BIET CONG TY
		
		String sohieu = getSoHieuTK(tknoId);
		String query = 	
			"SELECT n.PK_SEQ, n.TEN as MA, n.DIENGIAI as TEN \n" +
			"FROM ERP_TRUNGTAMCHIPHI tt\n" +
			"inner join ERP_NHOMCHIPHI n on n.TTCHIPHI_FK = tt.PK_SEQ\n" +
			"WHERE tt.TRANGTHAI = 1  \n" +
			" AND n.TAIKHOAN_FK = "+ sohieu + " \n " ;
		//	"	AND 1 = (SELECT ISNULL(COTTCHIPHI, 0) FROM ERP_TAIKHOANKT WHERE PK_SEQ = " + tknoId + " AND CONGTY_FK = " + this.congtyId + ") \n";
		
		System.out.println("TTCP NO:\n" + query + "\n---------------------------------------------------------------");
		System.out.println("MA TAI KHOAN: " + tknoId );
		
		return this.db.getScrol(query);
	}

	public ResultSet getTrungTamChiPhiCoRs(String tkcoId) {
		if (tkcoId == null || tkcoId.trim().length() == 0)
			return null;
		/*String query = 	"SELECT PK_SEQ,MA,TEN FROM ERP_TRUNGTAMCHIPHI " +
						"WHERE TRANGTHAI = 1 AND CONGTY_FK = " + this.congtyId + " " +
						"AND 1 = (SELECT ISNULL(COTTCHIPHI, 0) FROM ERP_TAIKHOANKT WHERE PK_SEQ = " + tkcoId + " AND CONGTY_FK = " + this.congtyId + ") ";*/
		String sohieu = getSoHieuTK(tkcoId);
		String query = 	
			"SELECT n.PK_SEQ, n.TEN as MA, n.DIENGIAI as TEN \n" +
			"FROM ERP_TRUNGTAMCHIPHI tt\n" +
			"inner join ERP_NHOMCHIPHI n on n.TTCHIPHI_FK = tt.PK_SEQ\n" +
			"WHERE tt.TRANGTHAI = 1 " +
			" AND n.TAIKHOAN_FK = "+ sohieu + " \n " ;
			//"AND 1 = (SELECT ISNULL(COTTCHIPHI, 0) FROM ERP_TAIKHOANKT WHERE PK_SEQ = " + tkcoId + " AND CONGTY_FK = " + this.congtyId + ") ";

		System.out.println("TTCP CO:\n" + query + "\n---------------------------------------------------");
		
		return this.db.getScrol(query);
	}
	
	public ResultSet getTaiKhoanKT_CoRs() {
		return this.TaiKhoanKT_CoRs;
	}

	public ResultSet getTaiKhoanKT_NoRs() {
		return this.TaiKhoanKT_NoRs;
	}
	
	public ResultSet getNhanvien_NoRs() {
		String query = "SELECT PK_SEQ, MA, TEN FROM ERP_NHANVIEN WHERE  TRANGTHAI=1  AND CONGTY_FK = " + this.congtyId + " " ;  
		return this.db.getScrol(query);
	}
	
	public ResultSet getNhanvien_CoRs() {
		String query = "SELECT PK_SEQ, MA, TEN FROM ERP_NHANVIEN WHERE  TRANGTHAI=1  AND CONGTY_FK = " + this.congtyId + " " ;  
		return this.db.getScrol(query);
	}
	
	public String getSoHieuTaiKhoan(String loai, int vitri){
		String soHieu = "";
		String query = "";
		if(loai.equals("no")){
			query = "SELECT SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ = " + this.TaiKhoanNoIds[vitri];
		}else{
			query = "SELECT SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ = " + this.TaiKhoanCoIds[vitri];
		}
		
		System.out.println("query so hieu: " + query);
		ResultSet rs = db.get(query);
		if(rs != null){
			try {
				if(rs.next()){
					soHieu = rs.getString("SOHIEUTAIKHOAN").substring(0, 3);
				}
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return soHieu;
	}
	public String getSoHieuTaiKhoanTheoDong(String loai, int vitri){
		String soHieu = "";
	
		if(loai.equals("no")){
			soHieu =  this.TaiKhoanNoIds[vitri];
		}else{
			soHieu =  this.TaiKhoanCoIds[vitri];
		}

		return soHieu;
	}
	
	public ResultSet getDoituong_Rs(String dungchoSelected, String loai, int vitri){
		if (dungchoSelected.trim().length() == 0)
			return null;
		
		String loaiTaiKhoan = this.getSoHieuTaiKhoan(loai, vitri);
//		String sohieuTaiKhoan = this.getSoHieuTaiKhoanTheoDong(loai, vitri);
		System.out.println("-----So hieu tai khoan: " + loaiTaiKhoan);
		String query = "";
/*		if(dungchoSelected.length() == 1){
			if(dungchoSelected.equals("1")){
				query = 	" SELECT '1' AS LOAI, PK_SEQ,   isnull(MA,ma)  AS MA, TEN + '' + QUYCACH AS TEN   FROM ERP_SANPHAM " +
							" WHERE TRANGTHAI = 1 and loaisanpham_fk >= 100013 " +
							" order by isnull(MA,ma)  ";
				
			}else if(dungchoSelected.equals("2")){
				query = 	"SELECT '2' AS LOAI, PK_SEQ, MA, TEN FROM ERP_NHACUNGCAP WHERE  TRANGTHAI=1 AND CONGTY_FK = " + this.congtyId + " ";

			}else if(dungchoSelected.equals("3")){
				query = 	"SELECT DISTINCT '3' AS LOAI,  NH.PK_SEQ, MA, TEN " +
							"FROM ERP_NGANHANG NH " +
							"INNER JOIN ERP_NGANHANG_CONGTY NH_CTY ON NH_CTY.NGANHANG_FK = NH.PK_SEQ " +
							"WHERE  NH.TRANGTHAI = 1 AND NH_CTY.CONGTY_FK = " + this.congtyId + "";
				
			}else if(dungchoSelected.equals("4")){
				query = "SELECT '4' AS LOAI, PK_SEQ, MA, DIENGIAI FROM ERP_TAISANCODINH WHERE  TRANGTHAI=1 AND CONGTY_FK = " + this.congtyId + " ";
				
			}else if(dungchoSelected.equals("5")){
				query =  	"SELECT '5' AS LOAI, CONVERT(VARCHAR, PK_SEQ) AS PK_SEQ, MA, TEN " +
							"FROM KHACHHANG WHERE  TRANGTHAI = 1 AND CONGTY_FK = " + this.congtyId + " " +
							"UNION ALL " +
							"SELECT 'KH' AS LOAI, CONVERT(VARCHAR, PK_SEQ) + '-0' AS PK_SEQ, MA, TEN  " +
							"FROM NHAPHANPHOI WHERE  TRANGTHAI=1 AND TRUCTHUOC_FK = (SELECT PK_SEQ FROM NHAPHANPHOI WHERE CONGTY_FK = " + this.congtyId + ") " +
							"ORDER BY TEN ";
			}else if(dungchoSelected.equals("6")){
				
				query = 	"SELECT PK_SEQ, MA, TEN FROM ERP_TRUNGTAMCHIPHI WHERE TRANGTHAI = 1 AND CONGTY_FK = " + this.congtyId + " " ; 
				
			}else if(dungchoSelected.equals("7")){
				query = 	"SELECT '6' AS LOAI, PK_SEQ, MA, TEN FROM ERP_NHANVIEN WHERE  TRANGTHAI = 1  AND CONGTY_FK = " + this.congtyId + " " ; 
			}
			
		}else{*/
			query = "SELECT * \n " +
					"FROM (	\n " +
					"	SELECT 1 AS LOAI, CONVERT(VARCHAR, PK_SEQ) AS PK_SEQ, '[SP]' + ' - ' + MA AS MA, TEN, 0 AS SOHIEU \n " +
					", trangThai\n" +
					"	FROM ERP_SANPHAM  \n " +

					"	UNION ALL				 \n " +
					"	SELECT 2 AS LOAI, CONVERT(VARCHAR, a.PK_SEQ) AS PK_SEQ, '[NCC]' + ' - ' + a.MA AS MA, a.TEN, b.sohieutaikhoan as SOHIEU \n" +
					", a.trangThai\n" +
					" FROM ERP_NHACUNGCAP a left join erp_taikhoankt  b on a.taikhoan_fk = b.pk_seq  \n " +
					"	WHERE a.CONGTY_FK = " + this.congtyId;// + " AND TAIKHOAN_FK  = "+ sohieuTaiKhoan +" \n" ;
					if(dungchoSelected.indexOf("2") >=0 && (loaiTaiKhoan.indexOf("136")>= 0 || loaiTaiKhoan.indexOf("336")>=0)){
						query = query + " AND b.SOHIEUTAIKHOAN LIKE '"+loaiTaiKhoan+"%'";
					}
					
					query = query + "	UNION ALL \n " +
					"	SELECT DISTINCT 3 AS LOAI,  CONVERT(VARCHAR, NH.PK_SEQ) AS PK_SEQ, '[NH]' + ' - ' + MA AS MA, TEN, 0 as SOHIEU  \n " +
					"   , NH.trangThai\n" +
					"	FROM ERP_NGANHANG NH  \n " +
					"	INNER JOIN ERP_NGANHANG_CONGTY NH_CTY ON NH_CTY.NGANHANG_FK = NH.PK_SEQ  \n " +
					"	WHERE NH_CTY.CONGTY_FK  = " + this.congtyId + "  \n " +

					"	UNION ALL \n " +
					"	SELECT 4 AS LOAI, CONVERT(VARCHAR, PK_SEQ) AS PK_SEQ, '[TS]' + ' - ' + MA AS MA, DIENGIAI AS TEN, 0 as SOHIEU \n" +
					"   , trangThai\n" +
					"   FROM ERP_TAISANCODINH  \n " +
					"	WHERE CONGTY_FK  = " + this.congtyId + " \n " +
				
					"	UNION ALL \n " +
					
					"	SELECT 5 AS LOAI, PK_SEQ,ISNULL( MA, '') AS MA, TEN, ISNULL(SOHIEU,0) AS SOHIEU  \n " +
					"   , trangThai\n" +
					"	FROM gopKHACHHANG  WHERE 1 = 1 \n " ;
					if(dungchoSelected.indexOf("5") >=0 && (loaiTaiKhoan.indexOf("136")>= 0 || loaiTaiKhoan.indexOf("336")>=0)){
						query = query + " AND SOHIEU LIKE '"+loaiTaiKhoan+"%'";
					}	

//					"	UNION ALL \n " +
//					"	SELECT 6 AS LOAI, PK_SEQ, MA, TEN FROM ERP_TRUNGTAMCHIPHI WHERE TRANGTHAI = 1 AND CONGTY_FK = " + this.congtyId + " \n " +
					
				query = query +	"	UNION ALL  \n " +
					"	SELECT 7 AS LOAI, CONVERT(VARCHAR, PK_SEQ) AS PK_SEQ, '[NV]' + ' - ' + MA AS MA, TEN, 0 AS SOHIEU \n" +
					"   , trangThai\n" +
					"   FROM ERP_NHANVIEN WHERE CONGTY_FK = " + this.congtyId + "  \n " +

					")RS WHERE RS.LOAI IN (" + dungchoSelected + ")";
				
//				if (!(this.trangThai.trim().equals("1") || this.trangThai.trim().equals("2")))		
//					query += " and RS.TRANGTHAI = 1 \n ";
//		}
		System.out.println("cau lenh get doi tuong:\n" + query + "\n-------------------------------------------------------------");
		return this.db.getScrol(query);
	}

	public void createRs()
	{
		this.getNppInfo();
		
		String query = 	" SELECT PK_SEQ,SOHIEUTAIKHOAN as MA,TENTAIKHOAN AS TEN, ISNULL(COTTCHIPHI,0)COTTCHIPHI, " +
						" ISNULL(DUNGCHOKHO, 0) DUNGCHOKHO, ISNULL(DUNGCHONGANHANG, 0) DUNGCHONGANHANG, " +
						" ISNULL(DUNGCHONCC, 0) DUNGCHONCC, ISNULL(DUNGCHOTAISAN, 0) DUNGCHOTAISAN, " +
						" ISNULL(DUNGCHOKHACHHANG, 0) DUNGCHOKHACHHANG, ISNULL(DUNGCHONHANVIEN, 0) DUNGCHONHANVIEN, " +
						" ISNULL(DUNGCHODOITUONGKHAC, 0) DUNGCHODOITUONGKHAC,ISNULL(DUNGCHOMASCLON,0) DUNGCHOMASCLON,ISNULL(DUNGCHOCHIPHITRATRUOC,0) AS DUNGCHOCHIPHITRATRUOC " +
						" FROM ERP_TAIKHOANKT " +
						" WHERE CONGTY_FK = " + this.congtyId + "";
		
		System.out.println("TK: " + query);
		this.TaiKhoanKT_NoRs = this.db.getScrol(query);

		this.TaiKhoanKT_CoRs = this.db.getScrol(query);

	}

	public boolean Save()
	{
		String ngaytao=getDateTime();
		try
		{
			this.db.getConnection().setAutoCommit(false);
			
			String query=" INSERT INTO ERP_BUTTOANTONGHOP(NGAYBUTTOAN,DIENGIAI,NGUOITAO,NGAYTAO,NGUOISUA,NGAYSUA, CONGTY_FK,TRANGTHAI,TIENTE_FK, TIGIA, MACHUNGTU ) VALUES" +
					"('"+this.NgayButToan+"',N'"+this.DienGiai+"','"+this.UserId+"','"+ngaytao+"','"+this.UserId+"','"+ngaytao+"', '" + this.congtyId + "','0', "+ this.tienTe+","+ this.tiGia+", '" + this.soChungTu+"') ";
			
			System.out.println(query);
			
			if(!this.db.update(query))
			{
				this.db.getConnection().rollback();
				this.Msg = "S1.1 Loi dong lenh sau "+query;
				return false;
			}
			
			query = "select SCOPE_IDENTITY() as btId";
			ResultSet rsBtId = db.get(query);	
			
			rsBtId.next();
		    this.Id = rsBtId.getString("btId");
		    rsBtId.close();
			
			int i=0; 

			int stt = 1;
			
			String sqlHOADON_BTTH = "";
			
			if(this.TaiKhoanNoIds != null & this.TaiKhoanCoIds != null)
			{
				
				while(this.TaiKhoanNoIds.length > i)
				{
					if(Double.parseDouble(this.Sotien[i].replaceAll(",", "")==""?"0":this.Sotien[i].replaceAll(",", ""))>0 )
					{
						String nv_noId = "null";			
						String nv_coId = "null";
	
						String sp_noId = "null";			
						String sp_coId = "null";
						
						String ts_noId = "null";			
						String ts_coId = "null";
	
						String nh_noId = "null";			
						String nh_coId = "null";
	
						String kho_noId = "null";			
						String kho_coId = "null";
	
						String ncc_noId = "null";			
						String ncc_coId = "null";
	
						String kh_noId = "null";			
						String kh_coId = "null";
	
						String dtk_noId = "null";			
						String dtk_coId = "null";
	
						String ttcp_no = "null";
						String ttcp_co = "null";					
											
						String masclon_noId="null";
						String masclon_coId="null";
						
						
						String CPTT_noId="null";
						String CPTT_coId="null";
						
						if(this.TaiKhoanNoIds[i].trim().length() > 0 && this.TaiKhoanCoIds[i].trim().length() <=0)
						{
								this.db.getConnection().rollback();
								this.Msg = "S1.2 Vui lòng chọn tài khoản có. ";
								return false;
							
						}
						
						if(this.TaiKhoanNoIds[i].trim().length() <= 0 && this.TaiKhoanCoIds[i].trim().length() > 0)
						{
							this.db.getConnection().rollback();
							this.Msg = "S1.3 Vui lòng chọn tài khoản nợ. ";
							return false;						
						}
						
						if(this.TaiKhoanNoIds[i].trim().length() > 0 & this.TaiKhoanCoIds[i].trim().length() > 0)
						{										
							query = "SELECT DUNGCHOKHO, DUNGCHONCC, DUNGCHONGANHANG, DUNGCHOTAISAN, DUNGCHOKHACHHANG, COTTCHIPHI, " +
									"DUNGCHONHANVIEN, ISNULL(DUNGCHODOITUONGKHAC, 0) AS DUNGCHODOITUONGKHAC, "
									+ " ISNULL(DUNGCHOMASCLON,0) AS DUNGCHOMASCLON,ISNULL(DUNGCHOCHIPHITRATRUOC,0) AS DUNGCHOCHIPHITRATRUOC,"
									+ " SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ = " + this.TaiKhoanNoIds[i] + "";
							System.out.println(query);
							ResultSet rs  = this.db.get(query);
							rs.next();
							
							//KIỂM TRA
							if(rs.getString("COTTCHIPHI").equals("1") )
							{
								if(TtcpNoIds[i].trim().length() == 0){
									this.db.getConnection().rollback();
									this.Msg = "S1.4 Tài khoản "+ rs.getString("SOHIEUTAIKHOAN") +" có dùng khoản mục chi phí chi phí. Vui lòng chọn khoản mục chi phí! ";
									return false;
								}else{
									ttcp_no = this.TtcpNoIds[i].trim();
								}
							}
					
							
							//KIỂM TRA
							if(rs.getString("DUNGCHONHANVIEN").equals("1") && this.dc_noIds[i].trim().equals("")){
									this.db.getConnection().rollback();
									this.Msg = "S1.5 Vui lòng chọn nhân viên cho đối tượng nợ. ";
									return false;
							}
							
							//KIỂM TRA
							if(rs.getString("DUNGCHOKHO").equals("1") && this.dc_noIds[i].trim().equals("")){
						
								this.db.getConnection().rollback();
								this.Msg = "S1.6 Vui lòng chọn sản phẩm cho đối tượng nợ. ";
								return false;
								
							}
							
							//KIỂM TRA
							if(rs.getString("DUNGCHONCC").equals("1") && this.dc_noIds[i].trim().equals("")){
								
								this.db.getConnection().rollback();
								this.Msg = "S1.7 Vui lòng chọn nhà cung cấp cho đối tượng nợ. ";
								return false;
								
							}
							
							//KIỂM TRA
							if(rs.getString("DUNGCHONGANHANG").equals("1") && this.dc_noIds[i].trim().equals("")){
								
								this.db.getConnection().rollback();
								this.Msg = "S1.8 Vui lòng chọn ngân hàng cho đối tượng nợ. ";
								return false;
								
							}
							
							//KIỂM TRA
							if(rs.getString("DUNGCHOTAISAN").equals("1")&& this.dc_noIds[i].equals("")){
								
								this.db.getConnection().rollback();
								this.Msg = "S1.9 Vui lòng chọn tài sản cho đối tượng nợ. ";
								return false;
								
							}
							
							//KIỂM TRA
							if(rs.getString("DUNGCHOKHACHHANG").equals("1")&& this.dc_noIds[i].trim().equals("")){
								 
								this.db.getConnection().rollback();
								this.Msg = "S1.10 Vui lòng chọn khách hàng cho đối tượng nợ. ";
								return false;
								
							}
							
							//KIỂM TRA
							if(rs.getString("DUNGCHODOITUONGKHAC").equals("1")&& this.dc_noIds[i].trim().equals("")){
								 
								this.db.getConnection().rollback();
								this.Msg = "S1.10 Vui lòng chọn Đối tượng khác cho đối tượng nợ. ";
								return false;
								
							}
							
							if(rs.getString("DUNGCHOMASCLON").equals("1")&& this.dc_noIds[i].trim().equals("")){
								 
								this.db.getConnection().rollback();
								this.Msg = "S1.10 Vui lòng chọn mã sửa chữa lớn cho đối tượng nợ. ";
								return false;
								
							}
							
							
							if(rs.getString("DUNGCHOCHIPHITRATRUOC").equals("1")&& this.dc_noIds[i].trim().equals("")){
								 
								this.db.getConnection().rollback();
								this.Msg = "S1.10 Vui lòng chọn mã chi phí trả trước cho đối tượng nợ. ";
								return false;
								
							}
							//KIỂM TRA - NẾU CÓ TRUNG TÂM CHI PHÍ THÌ BẮT BUỘC PHẢI CHỌN KÊNH BÁN HÀNG, ĐỊA BÀN
	//						if( rs.getString("COTTCHIPHI").equals("1") )
	//						{
	//							if(kbhIds[i].trim().length() == 0){
	//								this.db.getConnection().rollback();
	//								this.Msg = "Tài khoản "+ rs.getString("SOHIEUTAIKHOAN") +" có dùng trung tâm chi phí. Vui lòng chọn kênh bán hàng! ";
	//								return false;
	//							}
	//							
	//							if(DiabanIds[i].trim().length() == 0)
	//							{
	//								this.db.getConnection().rollback();
	//								this.Msg = "Tài khoản "+ rs.getString("SOHIEUTAIKHOAN") +" có dùng trung tâm chi phí. Vui lòng chọn địa bàn! ";
	//								return false;
	//							}
	//						}
							
							rs.close();
							String isnpp_no = "NULL";
							String isnpp_co = "NULL";
							String[] doituongno = this.dc_noIds[i].split(",");
							if(doituongno[0].equals("1")){
								kho_noId = doituongno[1];
							}
												
							if(doituongno[0].equals("2")){
								ncc_noId = doituongno[1];
							}
	
							if(doituongno[0].equals("3")){
								nh_noId = doituongno[1];
							}
	
							if(doituongno[0].equals("4")){
								ts_noId = doituongno[1];
							}
	
							if(doituongno[0].equals("5")){
								kh_noId = doituongno[1];
								isnpp_no = doituongno[2];
							}
							
	
							if(doituongno[0].equals("7")){
								nv_noId = doituongno[1];
							}
	
							if(doituongno[0].equals("8")){
								dtk_noId = doituongno[1];
							}
	
							if(doituongno[0].equals("9")){
								masclon_noId = doituongno[1];
							}
							
							if(doituongno[0].equals("10")){
								CPTT_noId = doituongno[1];
							}
							
							query = "SELECT DUNGCHOKHO, DUNGCHONCC, DUNGCHONGANHANG, DUNGCHOTAISAN, DUNGCHOKHACHHANG, COTTCHIPHI, " +
									"DUNGCHONHANVIEN, ISNULL(DUNGCHODOITUONGKHAC, 0) AS DUNGCHODOITUONGKHAC,"
									+ "ISNULL(DUNGCHOMASCLON,0) AS DUNGCHOMASCLON,ISNULL(DUNGCHOCHIPHITRATRUOC,0) AS DUNGCHOCHIPHITRATRUOC ,"
									+ " SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ = " + this.TaiKhoanCoIds[i] + "";
							System.out.println(query);
							rs  = this.db.get(query);
							rs.next();
							
							//KIỂM TRA
							if(rs.getString("COTTCHIPHI").equals("1") && TtcpCoIds[i].trim().length() == 0)
							{
								this.db.getConnection().rollback();
								this.Msg = "S1.11 Tài khoản "+ rs.getString("SOHIEUTAIKHOAN") +" có dùng trung tâm chi phí. Vui lòng chọn trung tâm chi phí! ";
								return false;
							}else if(rs.getString("COTTCHIPHI").equals("1") && TtcpCoIds[i].trim().length() > 0){
								ttcp_co = this.TtcpCoIds[i].trim();
							}
					
							
							//KIỂM TRA
							if(rs.getString("DUNGCHONHANVIEN").equals("1")&& this.dc_coIds[i].trim().equals("")){
								
								this.db.getConnection().rollback();
								this.Msg = "S1.12 Vui lòng chọn nhân viên cho đối tượng có. ";
								return false;
								
							}
							
							//KIỂM TRA
							if(rs.getString("DUNGCHOKHO").equals("1")&& this.dc_coIds[i].trim().equals("")){
								
								this.db.getConnection().rollback();
								this.Msg = "S1.13 Vui lòng chọn kho cho đối tượng có. ";
								return false;
								
							}
							
							//KIỂM TRA
							if(rs.getString("DUNGCHONCC").equals("1") && this.dc_coIds[i].trim().equals("")){
								
								this.db.getConnection().rollback();
								this.Msg = "S1.14 Vui lòng chọn nhà cung cấp cho đối tượng có. ";
								return false;
								
							}
							
							//KIỂM TRA
							if(rs.getString("DUNGCHONGANHANG").equals("1") && this.dc_coIds[i].trim().equals("")){
								
								this.db.getConnection().rollback();
								this.Msg = "S1.15 Vui lòng chọn ngân hàng cho đối tượng có. ";
								return false;
								
							}
							
							//KIỂM TRA
							if(rs.getString("DUNGCHOTAISAN").equals("1")&& this.dc_coIds[i].trim().equals("")){
								
								this.db.getConnection().rollback();
								this.Msg = "S1.16 Vui lòng chọn tài sản cho đối tượng có. ";
								return false;
								
							}
							
							//KIỂM TRA
							if(rs.getString("DUNGCHOKHACHHANG").equals("1")&& this.dc_coIds[i].trim().equals("")){
								 
								this.db.getConnection().rollback();
								this.Msg = "S1.17 Vui lòng chọn khách hàng cho đối tượng có. ";
								return false;
								
							}
							
							//KIỂM TRA
							if(rs.getString("DUNGCHODOITUONGKHAC").equals("1")&& this.dc_coIds[i].trim().equals("")){
								 
								this.db.getConnection().rollback();
								this.Msg = "S1.17 Vui lòng chọn Đối tượng khác cho đối tượng có. ";
								return false;
								
							}
							
							if(rs.getString("DUNGCHOMASCLON").equals("1")&& this.dc_coIds[i].trim().equals("")){
								 
								this.db.getConnection().rollback();
								this.Msg = "S1.17 Vui lòng chọn Mã sửa chữa lớn cho đối tượng có. ";
								return false;
								
							}
							
							
							if(rs.getString("DUNGCHOCHIPHITRATRUOC").equals("1")&& this.dc_coIds[i].trim().equals("")){
								 
								this.db.getConnection().rollback();
								this.Msg = "S1.17 Vui lòng chọn Mã chi phí trả trước cho đối tượng có. ";
								return false;
								
							}
	
							//KIỂM TRA - NẾU CÓ TRUNG TÂM CHI PHÍ THÌ BẮT BUỘC PHẢI CHỌN KÊNH BÁN HÀNG, ĐỊA BÀN => bỏ ràng buộc
	//						if( rs.getString("COTTCHIPHI").equals("1") )
	//						{
	//							if(kbhIds[i].trim().length() == 0){
	//								this.db.getConnection().rollback();
	//								this.Msg = "Tài khoản "+ rs.getString("SOHIEUTAIKHOAN") +" có dùng trung tâm chi phí. Vui lòng chọn kênh bán hàng! ";
	//								return false;
	//							}d
	//							
	//							if(DiabanIds[i].trim().length() == 0)
	//							{
	//								this.db.getConnection().rollback();
	//								this.Msg = "Tài khoản "+ rs.getString("SOHIEUTAIKHOAN") +" có dùng trung tâm chi phí. Vui lòng chọn địa bàn! ";
	//								return false;
	//							}
	//						}
							
							if(this.Sotien[i] == null) this.Sotien[i] = "0";
							if(this.Sotien[i].trim().length() == 0) this.Sotien[i] = "0";
							
							if(Double.parseDouble(this.Sotien[i].replaceAll(",", "")) == 0){
								 
								this.db.getConnection().rollback();
								this.Msg = "S1.18 Vui lòng nhập số tiền ";
								return false;
								
							}
	
							String[] doituongco = this.dc_coIds[i].split(",");
						
							if(doituongco[0].equals("1")){
								kho_coId = doituongco[1];
							}
												
							if(doituongco[0].equals("2")){
								ncc_coId = doituongco[1];
							}
	
							if(doituongco[0].equals("3")){
								nh_coId = doituongco[1];
							}
	
							if(doituongco[0].equals("4")){
								ts_coId = doituongco[1];
							}
	
							if(doituongco[0].equals("5")){
								kh_coId = doituongco[1];
								isnpp_co = doituongco[2];
							}
	
							if(doituongco[0].equals("7")){
								nv_coId = doituongco[1];
							}
							
							if(doituongco[0].equals("8")){
								dtk_coId = doituongco[1];
							}
	
							if(doituongco[0].equals("9")){
								masclon_coId = doituongco[1];
							}
							
							if(doituongco[0].equals("10")){
								CPTT_coId = doituongco[1];
							}
							
							String kbh_fk = this.kbhIds[i].trim().length() <= 0 ? "NULL" : this.kbhIds[i];
							String mavv_fk = this.MavvIds[i].trim().length() <= 0 ? "NULL" : this.MavvIds[i];
							String diaban_fk = this.DiabanIds[i].trim().length() <= 0 ? "NULL" : this.DiabanIds[i];
							String tinhthanh_fk = this.TinhthanhIds[i].trim().length() <= 0 ? "NULL" : this.TinhthanhIds[i];
							String benhvien_fk = this.BenhvienIds[i].trim().length() <= 0 ? "NULL" : this.BenhvienIds[i];
	//						String sanpham_fk = ""/*this.SanphamIds[i].trim().length() <= 0 ? "NULL" : this.SanphamIds[i]*/;
							String GiaTriNT = "NULL";
							if( SoTienNT != null){
								GiaTriNT = this.SoTienNT[i] == null ? "NULL": this.SoTienNT[i].replaceAll(",", "");
							}
							
													
							query = "INSERT INTO ERP_BUTTOANTONGHOP_CHITIET(BUTTOANTONGHOP_FK, TAIKHOANKT_FK, NO, CO, NHOMCHIPHI_FK, KHACHHANG_FK, " +
									"NCC_FK, KHO_FK, NGANHANG_FK, TAISAN_FK, SANPHAM_FK, DOITUONGKHAC_FK, STT, NHANVIEN_FK, KBH_FK, DIENGIAI, isNPP, VUVIEC_FK, DIABAN_FK, TINHTHANH_FK, BENHVIEN_FK,GIATRINT"
									+ ",MASCLON_FK,CPTT_FK ) " +
									"VALUES(" + this.Id + ", " + this.TaiKhoanNoIds[i] + ", " + this.Sotien[i].replaceAll(",", "") + ", 0, " + ttcp_no + ", " + kh_noId + ", " +
									"" + ncc_noId + ", " + kho_noId + ", " + nh_noId + ", " +  ts_noId + ", " + sp_noId + ", " + dtk_noId + ", " + ( stt++ ) + ", " + nv_noId + ", " + kbh_fk + ",  N'" + this.dg[i] + "', "+isnpp_no+" " +
									"," + mavv_fk + ", "+ diaban_fk + ", "+tinhthanh_fk+", "+ benhvien_fk+ ","+ GiaTriNT +""
											+ ","+masclon_noId+","+CPTT_noId+") ";
							
							System.out.println(query);
							if(!this.db.update(query))
							{
								this.db.getConnection().rollback();
								this.Msg="S1.19 Loi dong lenh sau "+query;
								return false;
							}
							
							query = "INSERT INTO ERP_BUTTOANTONGHOP_CHITIET(BUTTOANTONGHOP_FK, TAIKHOANKT_FK, NO, CO, NHOMCHIPHI_FK, KHACHHANG_FK, " +
									"NCC_FK, KHO_FK, NGANHANG_FK, TAISAN_FK, SANPHAM_FK, DOITUONGKHAC_FK, STT, NHANVIEN_FK, KBH_FK, DIENGIAI, isNPP, VUVIEC_FK, DIABAN_FK, TINHTHANH_FK, BENHVIEN_FK, PKSEQ, GIATRINT "
									+ ",MASCLON_FK,CPTT_FK) " +
									"VALUES(" + this.Id + ", " + this.TaiKhoanCoIds[i] + ", 0, " + this.Sotien[i].replaceAll(",", "") + ", " + ttcp_co + ", " + kh_coId + ", " +
									"" + ncc_coId + ", " + kho_coId + ", " + nh_coId + ", " +  ts_coId + ", " + sp_coId + ", " + dtk_coId + ", " + ( stt++ ) + ", " + nv_coId + ", " + kbh_fk + ",  N'" + this.dg[i] + "', "+isnpp_co+" " +
									"," + mavv_fk + ", "+ diaban_fk + ", "+tinhthanh_fk+", "+ benhvien_fk+ ", "+i+","+ GiaTriNT +","+masclon_coId+","+CPTT_coId+") ";
					
							System.out.println(query);
							if(!this.db.update(query))
							{
								this.db.getConnection().rollback();
								this.Msg="S1.20 Loi dong lenh sau "+query;
								return false;
							}
							//is
							query = " INSERT INTO ERP_BUTTOANTONGHOP_CHITIET_TH \n" +
									"(BUTTOANTONGHOP_FK, TAIKHOANNOKT_FK, TAIKHOANCOKT_FK , PKSEQ\n" +
									", NHOMCHIPHI_FK_NO, NHOMCHIPHI_FK_CO , KHACHHANG_FK_NO, KHACHHANG_FK_CO \n" +
									", NCC_FK_NO, NCC_FK_CO, KHO_FK_NO, KHO_FK_CO\n" +
									", NGANHANG_FK_NO, NGANHANG_FK_CO, TAISAN_FK_NO, TAISAN_FK_CO\n" +
									", SANPHAM_FK_NO, SANPHAM_FK_CO, NHANVIEN_FK_NO, NHANVIEN_FK_CO, DOITUONGKHAC_FK_NO, DOITUONGKHAC_FK_CO " +
									", KBH_FK, DIENGIAI, isNPP_NO, isNPP_CO\n" +
									", VUVIEC_FK, DIABAN_FK, TINHTHANH_FK, BENHVIEN_FK, SP_FK,MASCLON_FK_NO,MASCLON_FK_CO,CPTT_FK_NO,CPTT_FK_CO ) VALUES \n"+
									" ("+this.Id+", "+this.TaiKhoanNoIds[i]+", "+this.TaiKhoanCoIds[i]+", "+this.PKSEQIds[i]+"\n" +
									", "+ttcp_no+", "+ttcp_co+", "+kh_noId+"  , "+kh_coId+"\n" +
									", "+ncc_noId+", "+ncc_coId+", "+kho_noId+", "+kho_coId+"\n" +
									", "+nh_noId+", "+nh_coId+", "+ts_noId+", "+ts_coId+"\n" +
									", " + sp_noId + ", " + sp_coId + ", " + nv_noId + ", " + nv_coId + ", " + dtk_noId + ", " + dtk_coId + "\n" +
									", "+kbh_fk+", N'" + this.dg[i] + "', "+isnpp_no+", "+isnpp_co+" \n" +
									", "+mavv_fk+", "+diaban_fk+", "+tinhthanh_fk+", "+benhvien_fk+", NULL,"+masclon_noId+","+masclon_coId+","+CPTT_noId+","+CPTT_coId+") ";
			
							System.out.println("::: INSET ERP_BUTTOANTONGHOP_CHITIET_TH: \n" + query + "\n------------------------------------------------------------------");
							if(!this.db.update(query))
							{
								this.db.getConnection().rollback();
								this.Msg="S1.21 Loi dong lenh sau "+query;
								return false;
							}
							
						}
						
						// HÓA ĐƠN BTTH
											
					}
					i++;
				}
				
				if(this.btth_tienhanghd != null)
				{					
					Enumeration<String> keys = this.btth_tienhanghd.keys();					
					
					while(keys.hasMoreElements())
					{
						String key = keys.nextElement();
						
						String _hdTienhang = "0"; 
						
						System.out.println("tienhang_ "+ key + ": "+ btth_tienhanghd.get(key));
						
						String pkseq_stt [] = key.split("__");
						
						if(this.btth_tienhanghd.get(key) != null)
						{
							_hdTienhang = this.btth_tienhanghd.get(key).replaceAll(",", "");
						}	
												
						if(_hdTienhang.trim().length() > 0)
						{
							if(Double.parseDouble(_hdTienhang) > 0)
							{
								sqlHOADON_BTTH += " SELECT "+this.Id+" AS ID, " + pkseq_stt[0] +" AS PK_SEQ , N'' AS MAHOADON, N'"+this.btth_mauhd.get(key)+"' MAUSOHOADON, N'"+this.btth_kyhieuhd.get(key)+"' AS KYHIEU, " +
												  " N'"+this.btth_Sohd.get(key)+"' AS SOHOADON, N'"+this.btth_ngayhd.get(key)+"' NGAYHOADON, N'"+this.btth_tenNCChd.get(key)+"' TENNCC, N'"+this.btth_msthd.get(key)+"' AS MST, " +
												  " "+this.btth_tienhanghd.get(key)+" AS TIENHANG, "+this.btth_thuesuathd.get(key)+" AS THUESUAT, "+this.btth_tienthuehd.get(key)+" AS TIENTHUE, N'"+this.btth_ghichuhd.get(key)+"' AS GHICHU, "+pkseq_stt[1]+" AS SOTT, "+this.btth_conghd.get(key)+" AS TONGCONG, N'"+this.btth_diachi.get(key)+"' as DIACHI";
								
								sqlHOADON_BTTH += " union ALL ";
								
								System.out.println(sqlHOADON_BTTH);
							}
						}
					}
				}
								
				if(sqlHOADON_BTTH.trim().length() > 0)
				{
					sqlHOADON_BTTH = sqlHOADON_BTTH.substring(0, sqlHOADON_BTTH.length() - 10);
					
					query = " insert ERP_BUTTOANTONGHOP_CHITIET_HOADON(BTTH_FK, PKSEQ, MAHOADON, MAUSOHOADON, KYHIEU, SOHOADON, NGAYHOADON, TENNHACUNGCAP, MASOTHUE, TIENHANG, THUESUAT, TIENTHUE, GHICHU, SOTT , TONGCONG, DIACHI ) \n" 
							+ sqlHOADON_BTTH;
				
					System.out.println("::::CHEN HOA DON CHI TIET: " + query);
					if(!db.update(query))
					{
						this.Msg = "S1.22 Lỗi khi thực thi câu lệnh: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
							
				// cập nhật mã chứng từ
/*				query = " update ERP_BUTTOANTONGHOP set machungtu = 'BTTH' + SUBSTRING(NGAYBUTTOAN, 6, 2) + SUBSTRING(NGAYBUTTOAN, 0, 5) + '-' + dbo.LaySoChungTu( " + this.Id + " ) " + 
						" where pk_seq = '" + this.Id + "' ";
				
				System.out.println("[ERP_BUTTOANTONGHOP] error message:" + query);
				
				if(!db.update(query))
				{
					this.Msg = "S1.23 Khong the tao moi ERP_BUTTOANTONGHOP: " + query;
					System.out.println("[ErpThutien.createTTHD] error message:" + this.Msg);
					db.getConnection().rollback();
					return false;
				}*/
				
				
			}else
			{
				this.db.getConnection().rollback();
				this.Msg="S1.24 Vui long chon tai khoan ke toan";
				return false;
			}
			
			//====================SẢN PHẨM POPUP ========================//
			
			if(this.maSanPham != null){
				Enumeration<String> keysMaSP = this.maSanPham.keys();			
					
				
				while(keysMaSP.hasMoreElements())
				{
					String keyMaSP = keysMaSP.nextElement();
					String maSP = this.maSanPham.get(keyMaSP);
					String[] sttv = keyMaSP.split("_");
					Double phantram = this.phanTram.get(keyMaSP);
					Double tienV = this.tienViet.get(keyMaSP);
					Double tienN = this.tienNT.get(keyMaSP);
					
					
					String queryInsert = "INSERT INTO ERP_BUTTOANTONGHOP_CHITIET_SANPHAM (BUTTOANTONGHOP_FK, STT, SANPHAM_FK, PHANTRAM, TIEN, TIENNT)\n VALUES("+this.Id+","+sttv[0]+","+maSP+","+phantram+", "+ tienV+","+ tienN+")";
					
					System.out.println("--Them san pham:" + queryInsert);
					if(!db.update(queryInsert)){
						this.Msg = "S1.24 Không thể thêm popup sản phẩm: " + queryInsert;
						System.out.println(" error message:" + this.Msg);
						db.getConnection().rollback();
						return false;
					}
					
				}
			}
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		} catch (Exception e)
		{
			db.update("rollback");
			this.Msg = "S1.25 " + e.getMessage();
			e.printStackTrace();
			return false;
		}
		return true;
	}


	public boolean Edit()
	{
		String ngaysua=getDateTime();
		try
		{
			this.db.getConnection().setAutoCommit(false);

			String query="UPDATE  ERP_BUTTOANTONGHOP SET NGAYBUTTOAN='"+this.NgayButToan+"',DIENGIAI=N'"+this.DienGiai+"',NGUOISUA='"+this.UserId+"',NGAYSUA='"+ngaysua+"', TIENTE_FK ='"+ this.tienTe +"', TIGIA ='"+ this.tiGia +"', MACHUNGTU ='"+ this.soChungTu.toUpperCase() +"' WHERE PK_SEQ='"+this.Id+"' and trangthai=0" ;
			if(this.db.updateReturnInt(query)!=1)
			{
				this.db.getConnection().rollback();
				this.Msg="E1.1 Loi dong lenh sau "+query;
				return false;
			}
			query="DELETE FROM ERP_BUTTOANTONGHOP_CHITIET WHERE BUTTOANTONGHOP_FK='"+this.Id+"'";
			if(!this.db.update(query))
			{
				this.db.getConnection().rollback();
				this.Msg="E1.2 Loi dong lenh sau "+query;
				return false;
			}
			
			query="DELETE FROM ERP_BUTTOANTONGHOP_CHITIET_HOADON WHERE BTTH_FK='"+this.Id+"'";
			if(!this.db.update(query))
			{
				this.db.getConnection().rollback();
				this.Msg="E1.3 Loi dong lenh sau "+query;
				return false;
			}
			
			query="DELETE FROM ERP_BUTTOANTONGHOP_CHITIET_TH WHERE BUTTOANTONGHOP_FK='"+this.Id+"'";
			if(!this.db.update(query))
			{
				this.db.getConnection().rollback();
				this.Msg="E1.4 Loi dong lenh sau "+query;
				return false;
			}
			
			query = "DELETE FROM ERP_BUTTOANTONGHOP_CHITIET_SANPHAM WHERE BUTTOANTONGHOP_FK = '" + this.Id + "'";
			if(!this.db.update(query))
			{
				this.db.getConnection().rollback();
				this.Msg="E1.5 Loi dong lenh sau "+query;
				return false;
			}
			int i=0;

			int stt = 1;
			
			String sqlHOADON_BTTH = "";
			
			if(this.TaiKhoanNoIds != null & this.TaiKhoanCoIds != null)
			{
				while(this.TaiKhoanNoIds.length > i)
				{
					if(Double.parseDouble((this.Sotien[i].replaceAll(",", "")==""?"0":this.Sotien[i].replaceAll(",", "")))>0)
					{
						String nv_noId = "null";			
						String nv_coId = "null";
	
						String sp_noId = "null";			
						String sp_coId = "null";
	
						String ts_noId = "null";			
						String ts_coId = "null";
	
						String nh_noId = "null";			
						String nh_coId = "null";
	
						String kho_noId = "null";			
						String kho_coId = "null";
	
						String ncc_noId = "null";			
						String ncc_coId = "null";
	
						String kh_noId = "null";			
						String kh_coId = "null";
	
						String dtk_noId = "null";
						String dtk_coId = "null";
	
						String ttcp_no = "null";
						String ttcp_co = "null";
						
						String masclon_noId="null";
						String masclon_coId="null";
						
						
						String CPTT_noId="null";
						String CPTT_coId="null";
	
						if(this.TaiKhoanNoIds[i].trim().length() > 0 && this.TaiKhoanCoIds[i].trim().length() <=0)
						{
								this.db.getConnection().rollback();
								this.Msg = "E1.5 Vui lòng chọn tài khoản có. ";
								return false;
							
						}
						
						if(this.TaiKhoanNoIds[i].trim().length() <= 0 && this.TaiKhoanCoIds[i].trim().length() > 0)
						{
							this.db.getConnection().rollback();
							this.Msg = "E1.6 Vui lòng chọn tài khoản nợ. ";
							return false;						
						}
						
						if(this.TaiKhoanNoIds[i].trim().length() > 0 & this.TaiKhoanCoIds[i].trim().length() > 0)
						{
							if(this.TaiKhoanCoIds[i].trim().length() <=0 )
							{
								this.db.getConnection().rollback();
								this.Msg = "E1.7 Vui lòng chọn tài khoản có. ";
								return false;
							}
	
							query = "SELECT DUNGCHOKHO, DUNGCHONCC, DUNGCHONGANHANG, DUNGCHOTAISAN, DUNGCHOKHACHHANG, COTTCHIPHI, " +
									"DUNGCHONHANVIEN, DUNGCHODOITUONGKHAC,"
									+ " ISNULL(DUNGCHOMASCLON,0) AS DUNGCHOMASCLON,ISNULL(DUNGCHOCHIPHITRATRUOC,0) AS DUNGCHOCHIPHITRATRUOC,"
									+ "SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ = " + this.TaiKhoanNoIds[i] + "";
							System.out.println(query);
							ResultSet rs  = this.db.get(query);
							rs.next();
	
							//KIỂM TRA
							if(rs.getString("COTTCHIPHI").equals("1") )
							{
								if(TtcpNoIds[i].trim().length() == 0){
									this.db.getConnection().rollback();
									this.Msg = "E1.8 Tài khoản "+ rs.getString("SOHIEUTAIKHOAN") +" có dùng trung tâm chi phí. Vui lòng chọn trung tâm chi phí! ";
									return false;
								}else{
									ttcp_no = this.TtcpNoIds[i].trim();
								}
							}
	
	
							//KIỂM TRA
							if(rs.getString("DUNGCHONHANVIEN").equals("1") && this.dc_noIds[i].trim().equals("")){
								this.db.getConnection().rollback();
								this.Msg = "E1.9 Vui lòng chọn nhân viên cho đối tượng nợ. ";
								return false;
	
							}
	
							//KIỂM TRA
							if(rs.getString("DUNGCHOKHO").equals("1") && this.dc_noIds[i].trim().equals("")){
	
								this.db.getConnection().rollback();
								this.Msg = "E1.10 Vui lòng chọn sản phẩm cho đối tượng nợ. ";
								return false;
	
							}
	
							//KIỂM TRA
							if(rs.getString("DUNGCHONCC").equals("1") && this.dc_noIds[i].trim().equals("")){
	
								this.db.getConnection().rollback();
								this.Msg = "E1.11 Vui lòng chọn nhà cung cấp cho đối tượng nợ. ";
								return false;
	
							}
	
							//KIỂM TRA
							if(rs.getString("DUNGCHONGANHANG").equals("1") && this.dc_noIds[i].trim().equals("")){
	
								this.db.getConnection().rollback();
								this.Msg = "E1.12 Vui lòng chọn ngân hàng cho đối tượng nợ. ";
								return false;
	
							}
	
							//KIỂM TRA
							if(rs.getString("DUNGCHOTAISAN").equals("1")&& this.dc_noIds[i].equals("")){
	
								this.db.getConnection().rollback();
								this.Msg = "E1.13 Vui lòng chọn tài sản cho đối tượng nợ. ";
								return false;
	
							}
	
							//KIỂM TRA
							if(rs.getString("DUNGCHOKHACHHANG").equals("1")&& this.dc_noIds[i].trim().equals("")){
	
								this.db.getConnection().rollback();
								this.Msg = "E1.14 Vui lòng chọn khách hàng cho đối tượng nợ. ";
								return false;
	
							}
	
							//KIỂM TRA
							if(rs.getString("DUNGCHODOITUONGKHAC").equals("1")&& this.dc_noIds[i].trim().equals("")){
	
								this.db.getConnection().rollback();
								this.Msg = "E1.14 Vui lòng chọn Đối tượng khác cho đối tượng nợ. ";
								return false;
	
							}
							
							if(rs.getString("DUNGCHOMASCLON").equals("1")&& this.dc_noIds[i].trim().equals("")){
								 
								this.db.getConnection().rollback();
								this.Msg = "S1.10 Vui lòng chọn mã sửa chữa lớn cho đối tượng nợ. ";
								return false;
								
							}
							
							
							if(rs.getString("DUNGCHOCHIPHITRATRUOC").equals("1")&& this.dc_noIds[i].trim().equals("")){
								 
								this.db.getConnection().rollback();
								this.Msg = "S1.10 Vui lòng chọn mã chi phí trả trước cho đối tượng nợ. ";
								return false;
								
							}
							
	
							rs.close();
	
							String[] doituongno = this.dc_noIds[i].split(",");
							String isnpp_no = "NULL";
							String isnpp_co = "NULL";
							if(doituongno[0].equals("1")){
								kho_noId = doituongno[1];
							}
	
							if(doituongno[0].equals("2")){
								ncc_noId = doituongno[1];
							}
	
							if(doituongno[0].equals("3")){
								nh_noId = doituongno[1];
							}
	
							if(doituongno[0].equals("4")){
								ts_noId = doituongno[1];
							}
	
							if(doituongno[0].equals("5")){
								kh_noId = doituongno[1];
								isnpp_no = doituongno[2];
							}
	
							if(doituongno[0].equals("7")){
								nv_noId = doituongno[1];
							}
	
							if(doituongno[0].equals("8")){
								dtk_noId = doituongno[1];
							}
	
							if(doituongno[0].equals("9")){
								masclon_noId = doituongno[1];
							}
							
							if(doituongno[0].equals("10")){
								CPTT_noId = doituongno[1];
							}
	
							query = "SELECT DUNGCHOKHO, DUNGCHONCC, DUNGCHONGANHANG, DUNGCHOTAISAN, DUNGCHOKHACHHANG, COTTCHIPHI, " +
									"DUNGCHONHANVIEN, ISNULL(DUNGCHODOITUONGKHAC, 0) AS DUNGCHODOITUONGKHAC, SOHIEUTAIKHOAN,"
									+ "ISNULL(DUNGCHOMASCLON,0) AS DUNGCHOMASCLON,ISNULL(DUNGCHOCHIPHITRATRUOC,0)as DUNGCHOCHIPHITRATRUOC \n"
									+ "FROM ERP_TAIKHOANKT WHERE PK_SEQ = " + this.TaiKhoanCoIds[i] + "";
							System.out.println(query);
							rs  = this.db.get(query);
							rs.next();
	
							//KIỂM TRA
							if(rs.getString("COTTCHIPHI").equals("1") && TtcpCoIds[i].trim().length() == 0)
							{
								this.db.getConnection().rollback();
								this.Msg = "E1.15 Tài khoản "+ rs.getString("SOHIEUTAIKHOAN") +" có dùng trung tâm chi phí. Vui lòng chọn trung tâm chi phí! ";
								return false;
							}else if(rs.getString("COTTCHIPHI").equals("1") && TtcpCoIds[i].trim().length() > 0){
								ttcp_co = this.TtcpCoIds[i].trim();
							}
	
	
							//KIỂM TRA
							if(rs.getString("DUNGCHONHANVIEN").equals("1")&& this.dc_coIds[i].trim().equals("")){
	
								this.db.getConnection().rollback();
								this.Msg = "E1.16 Vui lòng chọn nhân viên cho đối tượng có. ";
								return false;
	
							}
	
							//KIỂM TRA
							if(rs.getString("DUNGCHOKHO").equals("1")&& this.dc_coIds[i].trim().equals("")){
	
								this.db.getConnection().rollback();
								this.Msg = "E1.17 Vui lòng chọn kho cho đối tượng có. ";
								return false;
	
							}
	
							//KIỂM TRA
							if(rs.getString("DUNGCHONCC").equals("1") && this.dc_coIds[i].trim().equals("")){
	
								this.db.getConnection().rollback();
								this.Msg = "E1.18 Vui lòng chọn nhà cung cấp cho đối tượng có. ";
								return false;
	
							}
	
							//KIỂM TRA
							if(rs.getString("DUNGCHONGANHANG").equals("1") && this.dc_coIds[i].trim().equals("")){
	
								this.db.getConnection().rollback();
								this.Msg = "E1.19 Vui lòng chọn ngân hàng cho đối tượng có. ";
								return false;
	
							}
	
							//KIỂM TRA
							if(rs.getString("DUNGCHOTAISAN").equals("1")&& this.dc_coIds[i].trim().equals("")){
	
								this.db.getConnection().rollback();
								this.Msg = "E1.20 Vui lòng chọn tài sản cho đối tượng có. ";
								return false;
	
							}
	
							//KIỂM TRA
							if(rs.getString("DUNGCHOKHACHHANG").equals("1")&& this.dc_coIds[i].trim().equals("")){
	
								this.db.getConnection().rollback();
								this.Msg = "E1.21 Vui lòng chọn khách hàng cho đối tượng có. ";
								return false;
	
							}
	
							//KIỂM TRA
							if(rs.getString("DUNGCHODOITUONGKHAC").equals("1")&& this.dc_coIds[i].trim().equals("")){
	
								this.db.getConnection().rollback();
								this.Msg = "E1.21 Vui lòng chọn Đối tượng khác cho đối tượng có. ";
								return false;
	
							}
							if(rs.getString("DUNGCHOMASCLON").equals("1")&& this.dc_coIds[i].trim().equals("")){
								 
								this.db.getConnection().rollback();
								this.Msg = "S1.17 Vui lòng chọn Mã sửa chữa lớn cho đối tượng có. ";
								return false;
								
							}
							
							
							if(rs.getString("DUNGCHOCHIPHITRATRUOC").equals("1")&& this.dc_coIds[i].trim().equals("")){
								 
								this.db.getConnection().rollback();
								this.Msg = "S1.17 Vui lòng chọn Mã chi phí trả trước cho đối tượng có. ";
								return false;
								
							}
							
							//KIỂM TRA - NẾU CÓ TRUNG TÂM CHI PHÍ THÌ BẮT BUỘC PHẢI CHỌN KÊNH BÁN HÀNG, ĐỊA BÀN
	//						if( rs.getString("COTTCHIPHI").equals("1") )
	//						{
	//							if(kbhIds[i].trim().length() == 0){
	//								this.db.getConnection().rollback();
	//								this.Msg = "Tài khoản "+ rs.getString("SOHIEUTAIKHOAN") +" có dùng trung tâm chi phí. Vui lòng chọn kênh bán hàng! ";
	//								return false;
	//							}
	//							
	//							if(DiabanIds[i].trim().length() == 0)
	//							{
	//								this.db.getConnection().rollback();
	//								this.Msg = "Tài khoản "+ rs.getString("SOHIEUTAIKHOAN") +" có dùng trung tâm chi phí. Vui lòng chọn địa bàn! ";
	//								return false;
	//							}
	//						}
							
							
							String[] doituongco = this.dc_coIds[i].split(",");
							if(doituongco[0].equals("1")){
								kho_coId = doituongco[1];
							}
	
							if(doituongco[0].equals("2")){
								ncc_coId = doituongco[1];
							}
	
							if(doituongco[0].equals("3")){
								nh_coId = doituongco[1];
							}
	
							if(doituongco[0].equals("4")){
								ts_coId = doituongco[1];
							}
	
							if(doituongco[0].equals("5")){
								kh_coId = doituongco[1];
								isnpp_co = doituongco[2];
								
							}
	
							if(doituongco[0].equals("7")){
								nv_coId = doituongco[1];
							}
	
							if(doituongco[0].equals("8")){
								dtk_coId = doituongco[1];
							}
							
							if(doituongco[0].equals("9")){
								masclon_coId = doituongco[1];
							}
							
							if(doituongco[0].equals("10")){
								CPTT_coId = doituongco[1];
							}
	
							System.out.println("masclon_coId "+masclon_coId);
							if(this.Sotien[i] == null) this.Sotien[i] = "0";
							if(this.Sotien[i].trim().length() == 0) this.Sotien[i] = "0";
	
							if(Double.parseDouble(this.Sotien[i].replaceAll(",", "")) == 0){
	
								this.db.getConnection().rollback();
								this.Msg = "E1.22 Vui lòng nhập số tiền ";
								return false;
	
							}
	
							String kbh_fk = this.kbhIds[i].trim().length() <= 0 ? "NULL" : this.kbhIds[i];
							String mavv_fk = this.MavvIds[i].trim().length() <= 0 ? "NULL" : this.MavvIds[i];
							String diaban_fk = this.DiabanIds[i].trim().length() <= 0 ? "NULL" : this.DiabanIds[i];
							String tinhthanh_fk = this.TinhthanhIds[i].trim().length() <= 0 ? "NULL" : this.TinhthanhIds[i];
							String benhvien_fk = this.BenhvienIds[i].trim().length() <= 0 ? "NULL" : this.BenhvienIds[i];
	//						String sanpham_fk = ""/*this.SanphamIds[i].trim().length() <= 0 ? "NULL" : this.SanphamIds[i]*/;
							String GiaTriNT = "NULL";
							if( SoTienNT != null){
								GiaTriNT = this.SoTienNT[i] == null ? "NULL": this.SoTienNT[i].replaceAll(",", "");
							}
							
							
													
							query = "INSERT INTO ERP_BUTTOANTONGHOP_CHITIET(BUTTOANTONGHOP_FK, TAIKHOANKT_FK, NO, CO, NHOMCHIPHI_FK, KHACHHANG_FK, " +
									"NCC_FK, KHO_FK, NGANHANG_FK, TAISAN_FK, SANPHAM_FK, DOITUONGKHAC_FK, STT, NHANVIEN_FK, KBH_FK, DIENGIAI, isNPP, VUVIEC_FK, DIABAN_FK, TINHTHANH_FK, BENHVIEN_FK,GIATRINT,MASCLON_FK,CPTT_FK  ) " +
									"VALUES(" + this.Id + ", " + this.TaiKhoanNoIds[i] + ", " + this.Sotien[i].replaceAll(",", "") + ", 0, " + ttcp_no + ", " + kh_noId + ", " +
									"" + ncc_noId + ", " + kho_noId + ", " + nh_noId + ", " +  ts_noId + ", " + sp_noId + ", " + dtk_noId + ", " + ( stt++ ) + ", " + nv_noId + ", " + kbh_fk + ",  N'" + this.dg[i] + "', "+isnpp_no+"" +
									"," + mavv_fk + ", "+ diaban_fk + ", "+tinhthanh_fk+", "+ benhvien_fk+ ","+ GiaTriNT +","+masclon_noId+","+CPTT_noId+") ";
							
							System.out.println(query);
							if(!this.db.update(query))
							{
								this.db.getConnection().rollback();
								this.Msg="S1.19 Loi dong lenh sau "+query;
								return false;
							}
							
							query = "INSERT INTO ERP_BUTTOANTONGHOP_CHITIET(BUTTOANTONGHOP_FK, TAIKHOANKT_FK, NO, CO, NHOMCHIPHI_FK, KHACHHANG_FK, " +
									"NCC_FK, KHO_FK, NGANHANG_FK, TAISAN_FK, SANPHAM_FK, DOITUONGKHAC_FK, STT, NHANVIEN_FK, KBH_FK, DIENGIAI, isNPP, VUVIEC_FK, DIABAN_FK, TINHTHANH_FK, BENHVIEN_FK, PKSEQ, GIATRINT,MASCLON_FK,CPTT_FK ) " +
									"VALUES(" + this.Id + ", " + this.TaiKhoanCoIds[i] + ", 0, " + this.Sotien[i].replaceAll(",", "") + ", " + ttcp_co + ", " + kh_coId + ", " +
									"" + ncc_coId + ", " + kho_coId + ", " + nh_coId + ", " +  ts_coId + ", " + sp_coId + ", " + dtk_coId + ", " + ( stt++ ) + ", " + nv_coId + ", " + kbh_fk + ",  N'" + this.dg[i] + "', "+isnpp_co+" " +
									"," + mavv_fk + ", "+ diaban_fk + ", "+tinhthanh_fk+", "+ benhvien_fk+ ", "+i+","+ GiaTriNT +","+masclon_coId+","+CPTT_coId+") ";
					
							System.out.println(query);
							if(!this.db.update(query))
							{
								this.db.getConnection().rollback();
								this.Msg="S1.24 Loi dong lenh sau "+query;
								return false;
							}
							
							//phân biết khách hàng hoặc npp
							
							query = " INSERT INTO ERP_BUTTOANTONGHOP_CHITIET_TH (BUTTOANTONGHOP_FK, TAIKHOANNOKT_FK, TAIKHOANCOKT_FK , PKSEQ, NHOMCHIPHI_FK_NO, NHOMCHIPHI_FK_CO , KHACHHANG_FK_NO, KHACHHANG_FK_CO , " +
									" NCC_FK_NO, NCC_FK_CO, KHO_FK_NO, KHO_FK_CO, NGANHANG_FK_NO, NGANHANG_FK_CO, TAISAN_FK_NO, TAISAN_FK_CO, SANPHAM_FK_NO, SANPHAM_FK_CO, NHANVIEN_FK_NO, NHANVIEN_FK_CO, DOITUONGKHAC_FK_NO, DOITUONGKHAC_FK_CO, " +
									" KBH_FK, DIENGIAI, isNPP_NO, isNPP_CO, VUVIEC_FK, DIABAN_FK, TINHTHANH_FK, BENHVIEN_FK, SP_FK ,MASCLON_FK_NO,MASCLON_FK_CO,CPTT_FK_NO,CPTT_FK_CO ) VALUES \n"+
									" ("+this.Id+", "+this.TaiKhoanNoIds[i]+", "+this.TaiKhoanCoIds[i]+", "+this.PKSEQIds[i]+", "+ttcp_no+", "+ttcp_co+", "+kh_noId+"  , "+kh_coId+", " +
									" "+ncc_noId+", "+ncc_coId+", "+kho_noId+", "+kho_coId+", "+nh_noId+", "+nh_coId+", "+ts_noId+", "+ts_coId+", "+sp_noId+", "+sp_coId+", "+nv_noId+", "+nv_coId+", "+dtk_noId+", "+dtk_coId+", " +
									" "+kbh_fk+", N'" + this.dg[i] + "', "+isnpp_no+", "+isnpp_co+", "+mavv_fk+", "+diaban_fk+", "+tinhthanh_fk+", "+benhvien_fk+", NULL,"+masclon_noId+","+masclon_coId+","+CPTT_noId+","+CPTT_coId+") ";
	
							System.out.println("::: INSET ERP_BUTTOANTONGHOP_CHITIET_TH: " + query);
							if(!this.db.update(query))
							{
								this.db.getConnection().rollback();
								this.Msg="E1.25 Loi dong lenh sau "+query;
								return false;
							}
							
							
						}
						
						
						
					}
					i++;
				}

				
				
				if(this.btth_tienhanghd != null)
				{
					Enumeration<String> keys = this.btth_tienhanghd.keys();					
					
					while(keys.hasMoreElements())
					{
						String key = keys.nextElement();
						
						String _hdTienhang = "0"; 
						
						System.out.println("tienhang_ "+ key + ": "+ btth_tienhanghd.get(key));
						
						String pkseq_stt [] = key.split("__");
						
						if(this.btth_tienhanghd.get(key) != null)
						{
							_hdTienhang = this.btth_tienhanghd.get(key).replaceAll(",", "");
						}	
												
						if(_hdTienhang.trim().length() > 0)
						{
							if(Double.parseDouble(_hdTienhang) > 0)
							{
								sqlHOADON_BTTH += " SELECT "+this.Id+" AS ID, " + pkseq_stt[0] +" AS PK_SEQ , N'' AS MAHOADON, N'"+this.btth_mauhd.get(key)+"' MAUSOHOADON, N'"+this.btth_kyhieuhd.get(key)+"' AS KYHIEU, " +
												  " N'"+this.btth_Sohd.get(key)+"' AS SOHOADON, N'"+this.btth_ngayhd.get(key)+"' NGAYHOADON, N'"+this.btth_tenNCChd.get(key)+"' TENNCC, N'"+this.btth_msthd.get(key)+"' AS MST, " +
												  " "+this.btth_tienhanghd.get(key)+" AS TIENHANG, "+this.btth_thuesuathd.get(key)+" AS THUESUAT, "+this.btth_tienthuehd.get(key)+" AS TIENTHUE, N'"+this.btth_ghichuhd.get(key)+"' AS GHICHU, "+pkseq_stt[1]+" AS SOTT, "+this.btth_conghd.get(key)+" AS TONGCONG, N'"+this.btth_diachi.get(key)+"' as DIACHI ";
								
								sqlHOADON_BTTH += " union ALL ";
								
								System.out.println(sqlHOADON_BTTH);
								
							}
						}
						
					}
					
				}
				
				System.out.println( "aaaaaaaaaaaaaaaaaaaaaaaaaaaa :"+sqlHOADON_BTTH);
				if(sqlHOADON_BTTH.trim().length() > 0)
				{
					sqlHOADON_BTTH = sqlHOADON_BTTH.substring(0, sqlHOADON_BTTH.length() - 10);
					
					query = " insert ERP_BUTTOANTONGHOP_CHITIET_HOADON(BTTH_FK, PKSEQ, MAHOADON, MAUSOHOADON, KYHIEU, SOHOADON, NGAYHOADON, TENNHACUNGCAP, MASOTHUE, TIENHANG, THUESUAT, TIENTHUE, GHICHU, SOTT , TONGCONG, DIACHI ) \n" 
							+ sqlHOADON_BTTH;
				
					System.out.println("::::CHEN HOA DON CHI TIET: " + query);
					if(!db.update(query))
					{
						this.Msg = "E1.26 Lỗi khi thực thi câu lệnh: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
				

			}else
			{
				this.db.getConnection().rollback();
				this.Msg="E1.28 Vui long chon tai khoan ke toan";
				return false;
			}
			
			//=======================SẢN PHẨM POPUP===============================//
			if(this.maSanPham != null){
				Enumeration<String> keysMaSP = this.maSanPham.keys();			
					
				
				while(keysMaSP.hasMoreElements())
				{
					String keyMaSP = keysMaSP.nextElement();
					String maSP = this.maSanPham.get(keyMaSP);
					String[] sttv = keyMaSP.split("_");
					Double phantram = this.phanTram.get(keyMaSP);
					Double tienV = this.tienViet.get(keyMaSP);
					Double tienN = this.tienNT.get(keyMaSP);
					
					
					String queryInsert = "INSERT INTO ERP_BUTTOANTONGHOP_CHITIET_SANPHAM (BUTTOANTONGHOP_FK, STT, SANPHAM_FK, PHANTRAM, TIEN, TIENNT)\n VALUES("+this.Id+","+sttv[0]+","+maSP+","+phantram+", "+ tienV+","+ tienN+")";
					
					System.out.println("--Them san pham:" + queryInsert);
					if(!db.update(queryInsert)){
						this.Msg = "S1.24 Không thể thêm popup sản phẩm: " + queryInsert;
						System.out.println(" error message:" + this.Msg);
						db.getConnection().rollback();
						return false;
					}
					
				}
			}
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		} catch ( Exception e)
		{
			db.update("rollback");
			this.Msg="E1.29 " + e.getMessage();
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public void Init()
	{
		this.getNppInfo();
		
		String query="";
		ResultSet rs;
		if(this.Id .length() >0){
			  query=" SELECT PK_SEQ,NGAYBUTTOAN,DIENGIAI, TIENTE_FK, TIGIA, isnull(MACHUNGTU,'') as MACHUNGTU, trangThai  FROM ERP_BUTTOANTONGHOP WHERE PK_SEQ='"+this.Id+"'";
			  rs=this.db.get(query);
			if(rs!=null)
			{
				try
				{
					while(rs.next())
					{
						this.trangThai = rs.getString("trangThai");
						this.DienGiai=rs.getString("DienGiai");
						this.NgayButToan=rs.getString("NgayButToan");
						this.tienTe = rs.getString("TIENTE_FK")== null?"":rs.getString("TIENTE_FK") ;
						this.tiGia = rs.getString("TIGIA")== null?0:rs.getDouble("TIGIA");
						this.soChungTu = rs.getString("MACHUNGTU").toUpperCase();
					}rs.close();
				} catch (SQLException e)
				{
					e.printStackTrace();
				}
			}
		}
		this.count=1;
		if(this.Id.length() >0){
			query=	" SELECT COUNT(*) as count"+
					" FROM  ERP_BUTTOANTONGHOP BT "+
					" INNER JOIN ERP_BUTTOANTONGHOP_CHITIET CT ON CT.BUTTOANTONGHOP_FK=BT.PK_SEQ AND BT.PK_SEQ='"+this.Id+"'";
			rs =this.db.get(query);
			if(rs!=null)
			{
				try
				{
					while(rs.next())
					{
						this.count=rs.getInt("count")/2;
					}rs.close();
				} catch (SQLException e)
				{
					e.printStackTrace();
				}
			}
		}
		this.TaiKhoanNoIds = new String[count];
		this.TaiKhoanCoIds = new String[count];
		this.Sotien = new String [count];
		this.dc_noIds =  new String [count];
		this.dc_coIds = new String [count];
		this.dc_noTens =  new String [count];
		this.dc_coTens = new String [count];
		this.TtcpNoIds = new String[count];
		this.TtcpCoIds = new String[count];
		this.kbhIds = new String[count];
		this.dg = new String[count];
		this.MavvIds = new String[count];
		this.DiabanIds = new String[count];
		this.TinhthanhIds = new String[count];
		this.BenhvienIds = new String[count];
		this.SanphamIds = new String[count];
		this.SoTienNT = new String[count];
		

		
		for (int j = 0; j < count ; j++){
			this.TaiKhoanNoIds[j] = "";
			this.TaiKhoanCoIds[j] = "";
			this.Sotien[j] = "0";
			this.SoTienNT[j] = "0";
			this.TtcpNoIds[j] = "";
			this.TtcpCoIds[j] = "";
			this.kbhIds[j] = "";
			this.dg[j] = "";
			this.MavvIds[j] = "";
			this.DiabanIds[j] = "";
			this.TinhthanhIds[j] = "";
			this.BenhvienIds[j] = "";
			this.dc_noIds[j] = "";
			this.dc_coIds[j] = "";
			this.SanphamIds[j] = "";
			this.dc_noTens[j] = "";
			this.dc_coTens[j] = "";
		}
		
		if(this.Id.length() > 0 ){
			try
			{
				//DO LƯU 1 dòng thành 2 trong CSDL nên không lấy theo STT được
				query=	" SELECT BT.PK_SEQ AS BTID, CT.NO, CT.GIATRINT, ISNULL(CONVERT(VARCHAR, CT.NHOMCHIPHI_FK), '') AS TTCP_FK, \n" +
						" CT.KHO_FK, CT.NGANHANG_FK, CT.NCC_FK, CT.TAISAN_FK, CT.NHANVIEN_FK, CT.DOITUONGKHAC_FK,  \n" +
						"	CT.KHACHHANG_FK , CT.MASCLON_FK,CT.CPTT_FK,\n"+
						" CT.TAIKHOANKT_FK, ISNULL(CONVERT(VARCHAR, CT.KBH_FK), '') AS KBH_FK, CT.DIENGIAI, \n" +
						" ISNULL(CONVERT(VARCHAR, CT.VUVIEC_FK), '') AS VUVIEC_FK, \n"+
						" ISNULL(CONVERT(VARCHAR, CT.DIABAN_FK), '') AS DIABAN_FK, \n"+
						" ISNULL(CONVERT(VARCHAR, CT.TINHTHANH_FK), '') AS TINHTHANH_FK, \n"+
						" ISNULL(CONVERT(VARCHAR, CT.BENHVIEN_FK), '') AS BENHVIEN_FK, \n"+
						" ISNULL(CONVERT(VARCHAR, CT.SP_FK), '') AS SP_FK, isNull(CONVERT(VARCHAR, CT.NHOMCHIPHI_FK), '') as NHOMCHIPHI_FK, \n" +
						
						" isNull(ct.isNPP, '0') isNPP ,\n"+
						" CASE WHEN CT.NCC_FK IS NOT NULL THEN  ISNULL(NCC.MA,'') +'-' + ISNULL(NCC.TEN,'') \n"+
						" WHEN CT.TAISAN_FK IS NOT NULL THEN ISNULL(TSCD.MA,'') +'-'+ ISNULL(TSCD.DIENGIAI,'') \n"+
						" WHEN CT.NHANVIEN_FK IS NOT NULL THEN ISNULL(NV.MA,'') + '-' + ISNULL(NV.TEN,'') \n"+
						" WHEN CT.NGANHANG_FK IS NOT NULL THEN ISNULL(NH.MA,'') + '-' + ISNULL(NH.TEN,'') \n"+
						" WHEN NPP.PK_SEQ IS NOT NULL THEN ISNULL(NPP.MAFAST,'') +'-'+ ISNULL(NPP.TEN,'') \n"+
						" WHEN KH.PK_SEQ IS NOT NULL THEN ISNULL(KH.MAFAST,'') +'-'+ ISNULL(KH.TEN,'') \n"+
						" WHEN DTK.PK_SEQ IS NOT NULL THEN ISNULL(DTK.MADOITUONG,'') +'-'+ ISNULL(DTK.TENDOITUONG,'') \n"+
						" WHEN MASCLON.PK_SEQ IS NOT NULL THEN ISNULL(MASCLON.MA,'') +'-'+ ISNULL(MASCLON.TEN,'') \n"+
						" WHEN CCDC.PK_SEQ IS NOT NULL THEN ISNULL(CCDC.MA,'') +'-'+ ISNULL(CCDC.DIENGIAI,'') \n"+
						
						" ELSE '' END DTTEN \n"+
						" FROM ERP_BUTTOANTONGHOP BT \n"+
						" INNER JOIN ERP_BUTTOANTONGHOP_CHITIET CT ON CT.BUTTOANTONGHOP_FK = BT.PK_SEQ \n" +
						" LEFT JOIN KHACHHANG KH ON KH.PK_SEQ= CT.KHACHHANG_FK AND ISNULL(CT.ISNPP,'0')='0' \n "+
						" LEFT JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ= CT.KHACHHANG_FK AND ISNULL(CT.ISNPP,'1')='1' \n "+
						" LEFT JOIN ERP_NHACUNGCAP NCC ON NCC.PK_SEQ= CT.NCC_FK  \n "+
						" LEFT JOIN ERP_NGANHANG NH ON NH.PK_SEQ= CT.NGANHANG_FK  \n "+
						" LEFT JOIN ERP_NHANVIEN NV ON NV.PK_SEQ= CT.NHANVIEN_FK  \n "+
						" LEFT JOIN ERP_TAISANCODINH TSCD ON TSCD.PK_SEQ= CT.TAISAN_FK  \n " +
						" LEFT JOIN ERP_DOITUONGKHAC DTK ON DTK.PK_SEQ = CT.DOITUONGKHAC_FK \n " +
						" LEFT JOIN ERP_MASCLON MASCLON ON MASCLON.PK_SEQ = CT.MASCLON_FK \n " +
						" LEFT JOIN ERP_CONGCUDUNGCU CCDC ON CCDC.PK_SEQ = CT.CPTT_FK \n " +
						
						//" WHERE BT.PK_SEQ = '" +  this.Id + "' AND BT.CONGTY_FK = " + this.congtyId + " AND STT = 1 ";
						" WHERE BT.PK_SEQ = '" +  this.Id + "' AND BT.CONGTY_FK = " + this.congtyId + " AND CT.NO != 0 order by STT asc ";
			
				int i=0;
				System.out.println(":::LAY TRUNG TAM CHI PHI NO: \n" + query + "\n---------------------------------");
				rs = this.db.get(query);
				if(rs != null)
				{
					while(rs.next())
					{
						this.TaiKhoanNoIds[i] = rs.getString("TAIKHOANKT_FK");
						
						this.Sotien[i] = rs.getString("NO") == null?"":rs.getString("NO").replaceAll(",", "");
						
						this.SoTienNT[i] = rs.getString("GIATRINT") == null || rs.getString("GIATRINT") == "" ?"0":rs.getString("GIATRINT").replaceAll(",", "");
					
						this.TtcpNoIds[i] = rs.getString("NHOMCHIPHI_FK");
						
						if(rs.getString("KHO_FK") != null){
						
							dc_noIds[i] = "1," + rs.getString("KHO_FK");
						
						}else if(rs.getString("NGANHANG_FK") != null){
						
							dc_noIds[i] = "3," + rs.getString("NGANHANG_FK");
						
						}else if(rs.getString("NCC_FK") != null){ 
						
							dc_noIds[i] = "2," + rs.getString("NCC_FK");
						
						}else if(rs.getString("TAISAN_FK") != null){ 
						
							dc_noIds[i] = "4," + rs.getString("TAISAN_FK");
						
						}else if(rs.getString("KHACHHANG_FK") != null){
							dc_noIds[i] = "5," + rs.getString("KHACHHANG_FK") + "," + rs.getString("isNPP").trim();
						}else if(rs.getString("NHANVIEN_FK") != null){
							
							dc_noIds[i] = "7," + rs.getString("NHANVIEN_FK");
						}else if(rs.getString("DOITUONGKHAC_FK") != null){
							
							dc_noIds[i] = "8," + rs.getString("DOITUONGKHAC_FK");
						
						}
						else if(rs.getString("MASCLON_FK") != null)
						{
												
							dc_noIds[i] = "9," + rs.getString("MASCLON_FK");
											
						}
											
					else if(rs.getString("CPTT_FK") != null){
						
						dc_noIds[i] = "10," + rs.getString("CPTT_FK");
					
					}
						else{
							dc_noIds[i] = "";
						}
						this.dc_noTens[i] = rs.getString("DTTEN") ;
						this.kbhIds[i] = rs.getString("KBH_FK") == null ? "" : rs.getString("KBH_FK");
						this.MavvIds[i] = rs.getString("VUVIEC_FK") == null ? "" : rs.getString("VUVIEC_FK");
						this.DiabanIds[i] = rs.getString("DIABAN_FK") == null ? "" : rs.getString("DIABAN_FK");
						this.TinhthanhIds[i] = rs.getString("TINHTHANH_FK") == null ? "" : rs.getString("TINHTHANH_FK");
						this.BenhvienIds[i] = rs.getString("BENHVIEN_FK") == null ? "" : rs.getString("BENHVIEN_FK");
						this.SanphamIds[i] = rs.getString("SP_FK") == null ? "" : rs.getString("SP_FK");
						this.dg[i] = rs.getString("DIENGIAI");
						
						System.out.println("dc_coIds[" + i + "] = " + dc_coIds[i]);
						//System.out.println("SP_FK[" + i + "] = " + SanphamIds[i]);
						i++;
					}rs.close();
				}
				
				query=	" SELECT BT.PK_SEQ AS BTID, CT.NO, ISNULL(CONVERT(VARCHAR, CT.NHOMCHIPHI_FK), '') AS TTCP_FK, " +
						" CT.KHO_FK, CT.NGANHANG_FK, CT.NCC_FK, CT.TAISAN_FK, CT.NHANVIEN_FK,  " +
						"	  CT.KHACHHANG_FK khachhang_fk , CT.MASCLON_FK,CT.CPTT_FK,\n	" +
						" CT.TAIKHOANKT_FK, ISNULL(CONVERT(VARCHAR, CT.KBH_FK), '') AS KBH_FK, CT.DIENGIAI, " +
						" ISNULL(CONVERT(VARCHAR, CT.VUVIEC_FK), '') AS VUVIEC_FK, "+
						" ISNULL(CONVERT(VARCHAR, CT.DIABAN_FK), '') AS DIABAN_FK, "+
						" ISNULL(CONVERT(VARCHAR, CT.TINHTHANH_FK), '') AS TINHTHANH_FK, "+
						" ISNULL(CONVERT(VARCHAR, CT.BENHVIEN_FK), '') AS BENHVIEN_FK, "+
						" ISNULL(CONVERT(VARCHAR, CT.SP_FK), '') AS SP_FK , isNull(CONVERT(VARCHAR, CT.NHOMCHIPHI_FK), '') as NHOMCHIPHI_FK,\n"+
					//	" ISNULL(CONVERT(VARCHAR, CT.DOITUONGKHAC_FK), '') AS DOITUONGKHAC_FK, "+

						" CT.DOITUONGKHAC_FK,isNull(ct.isNPP, '0') isNPP ,\n"+
						
						" CASE WHEN CT.NCC_FK IS NOT NULL THEN  ISNULL(NCC.MA,'') +'-' + ISNULL(NCC.TEN,'') \n"+
						" WHEN CT.TAISAN_FK IS NOT NULL THEN ISNULL(TSCD.MA,'') +'-'+ ISNULL(TSCD.DIENGIAI,'') \n"+
						" WHEN CT.NHANVIEN_FK IS NOT NULL THEN ISNULL(NV.MA,'') + '-' + ISNULL(NV.TEN,'') \n"+
						" WHEN CT.NGANHANG_FK IS NOT NULL THEN ISNULL(NH.MA,'') + '-' + ISNULL(NH.TEN,'') \n"+
						" WHEN NPP.PK_SEQ IS NOT NULL THEN ISNULL(NPP.MAFAST,'') +'-'+ ISNULL(NPP.TEN,'') \n"+
						" WHEN KH.PK_SEQ IS NOT NULL THEN ISNULL(KH.MAFAST,'') +'-'+ ISNULL(KH.TEN,'') \n"+
						" WHEN DTK.PK_SEQ IS NOT NULL THEN ISNULL(DTK.MADOITUONG,'') +'-'+ ISNULL(DTK.TENDOITUONG,'') \n"+
						" WHEN MASCLON.PK_SEQ IS NOT NULL THEN ISNULL(MASCLON.MA,'') +'-'+ ISNULL(MASCLON.TEN,'') \n"+
						" WHEN CCDC.PK_SEQ IS NOT NULL THEN ISNULL(CCDC.MA,'') +'-'+ ISNULL(CCDC.DIENGIAI,'') \n"+
						
						" ELSE '' END DTTEN \n"+
						
						" FROM ERP_BUTTOANTONGHOP BT \n"+
						" INNER JOIN ERP_BUTTOANTONGHOP_CHITIET CT ON CT.BUTTOANTONGHOP_FK = BT.PK_SEQ \n" +
						" LEFT JOIN KHACHHANG KH ON KH.PK_SEQ= CT.KHACHHANG_FK AND ISNULL(CT.ISNPP,'0')='0' \n "+
						" LEFT JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ= CT.KHACHHANG_FK AND ISNULL(CT.ISNPP,'1')='1' \n "+
						" LEFT JOIN ERP_NHACUNGCAP NCC ON NCC.PK_SEQ= CT.NCC_FK  \n "+
						" LEFT JOIN ERP_NGANHANG NH ON NH.PK_SEQ= CT.NGANHANG_FK  \n "+
						" LEFT JOIN ERP_NHANVIEN NV ON NV.PK_SEQ= CT.NHANVIEN_FK  \n "+
						" LEFT JOIN ERP_TAISANCODINH TSCD ON TSCD.PK_SEQ= CT.TAISAN_FK  \n "+
						" LEFT JOIN ERP_DOITUONGKHAC DTK ON DTK.PK_SEQ = CT.DOITUONGKHAC_FK \n " +
						" LEFT JOIN ERP_MASCLON MASCLON ON MASCLON.PK_SEQ = CT.MASCLON_FK \n " +
						" LEFT JOIN ERP_CONGCUDUNGCU CCDC ON CCDC.PK_SEQ = CT.CPTT_FK \n " +
						
						//" WHERE BT.PK_SEQ = '" +  this.Id + "' AND BT.CONGTY_FK = " + this.congtyId + " AND STT = 2 \n";
						" WHERE BT.PK_SEQ = '" +  this.Id + "' AND BT.CONGTY_FK = " + this.congtyId + " AND CT.CO != 0 order by STT asc \n";
				
				System.out.println(":::LAY TRUNG TAM CHI PHI CO: " + query);
				i=0;
				System.out.println("query "+query);
				rs = this.db.get(query);
				if(rs != null)
				{
					while(rs.next())
					{
						this.TaiKhoanCoIds[i] = rs.getString("TAIKHOANKT_FK");
						
						//this.Sotien[i] = rs.getString("NO") == null?"":rs.getString("NO");
					
						this.TtcpCoIds[i] = rs.getString("NHOMCHIPHI_FK");
						
						if(rs.getString("KHO_FK") != null){
						
							dc_coIds[i] = "1," + rs.getString("KHO_FK");
						
						}else if(rs.getString("NGANHANG_FK") != null){
						
							dc_coIds[i] = "3," + rs.getString("NGANHANG_FK");
						
						}else if(rs.getString("NCC_FK") != null){ 
						
							dc_coIds[i] = "2," + rs.getString("NCC_FK");
						
						}else if(rs.getString("TAISAN_FK") != null){ 
						
							dc_coIds[i] = "4," + rs.getString("TAISAN_FK");
						
						}else if(rs.getString("KHACHHANG_FK") != null){
							dc_coIds[i] = "5," + rs.getString("KHACHHANG_FK") + "," + rs.getString("isNPP").trim();
						}else if(rs.getString("NHANVIEN_FK") != null){
							
							dc_coIds[i] = "7," + rs.getString("NHANVIEN_FK");
						}else if(rs.getString("DOITUONGKHAC_FK") != null){
							
							dc_coIds[i] = "8," + rs.getString("DOITUONGKHAC_FK");
						}
						else if(rs.getString("MASCLON_FK") != null)
						{
												
							dc_coIds[i] = "9," + rs.getString("MASCLON_FK");
											
						}
											
					else if(rs.getString("CPTT_FK") != null){
						
						dc_coIds[i] = "10," + rs.getString("CPTT_FK");
					}
					else{
							dc_coIds[i] = "";
						}
						this.dc_coTens[i] = rs.getString("DTTEN");
						this.kbhIds[i] = rs.getString("KBH_FK") == null ? "" : rs.getString("KBH_FK");
						this.MavvIds[i] = rs.getString("VUVIEC_FK") == null ? "" : rs.getString("VUVIEC_FK");
						this.DiabanIds[i] = rs.getString("DIABAN_FK") == null ? "" : rs.getString("DIABAN_FK");
						this.TinhthanhIds[i] = rs.getString("TINHTHANH_FK") == null ? "" : rs.getString("TINHTHANH_FK");
						this.BenhvienIds[i] = rs.getString("BENHVIEN_FK") == null ? "" : rs.getString("BENHVIEN_FK");
						this.SanphamIds[i] = rs.getString("SP_FK") == null ? "" : rs.getString("SP_FK");
						this.dg[i] = rs.getString("DIENGIAI");
						
						System.out.println("dc_coIds[" + i + "] = " + dc_coIds[i]);
						i++;
					}rs.close();
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}
			

			//INIT HÓA ĐƠN BTTH
			query = " select BTTH_FK , PKSEQ, MAHOADON, MAUSOHOADON, KYHIEU, SOHOADON, NGAYHOADON, TENNHACUNGCAP, MASOTHUE, TIENHANG, THUESUAT, TIENTHUE, GHICHU, SOTT , TONGCONG, ISNULL(DIACHI,'') as DIACHI " +
					" from ERP_BUTTOANTONGHOP_CHITIET_HOADON " +
					" where BTTH_FK = '" + this.Id + "' ";
			
			System.out.println(query);
			rs = db.get(query);
			Hashtable<String, String> btth_mauhd = new Hashtable<String, String>();
			Hashtable<String, String> btth_kyhieuhd = new Hashtable<String, String>();
			Hashtable<String, String> btth_msthd = new Hashtable<String, String>();
			Hashtable<String, String> btth_ngayhd = new Hashtable<String, String>();
			Hashtable<String, String> btth_sohd = new Hashtable<String, String>();
			Hashtable<String, String> btth_tenNCChd = new Hashtable<String, String>();
			Hashtable<String, String> btth_thuesuathd = new Hashtable<String, String>();
			Hashtable<String, String> btth_tienhanghd = new Hashtable<String, String>();
			Hashtable<String, String> btth_tienthuehd = new Hashtable<String, String>();
			Hashtable<String, String> btth_ghichuhd = new Hashtable<String, String>();
			Hashtable<String, String> btth_conghd = new Hashtable<String, String>();
			Hashtable<String, String> btth_diachi = new Hashtable<String, String>();
			
			try
			{
				while( rs.next() )
				{
					btth_mauhd.put( rs.getString("PKSEQ") +"__" + rs.getString("SOTT"),rs.getString("MAUSOHOADON")  );
					btth_kyhieuhd.put( rs.getString("PKSEQ") +"__" + rs.getString("SOTT"),rs.getString("KYHIEU")  );
					btth_msthd.put( rs.getString("PKSEQ") +"__" + rs.getString("SOTT"),rs.getString("MASOTHUE")  );
					btth_ngayhd.put( rs.getString("PKSEQ") +"__" + rs.getString("SOTT"),rs.getString("NGAYHOADON")  );
					btth_sohd.put( rs.getString("PKSEQ") +"__" + rs.getString("SOTT"),rs.getString("SOHOADON")  );
					btth_tenNCChd.put( rs.getString("PKSEQ") +"__" + rs.getString("SOTT"),rs.getString("TENNHACUNGCAP")  );
					btth_thuesuathd.put( rs.getString("PKSEQ") +"__" + rs.getString("SOTT"),rs.getString("THUESUAT")  );
					btth_tienthuehd.put( rs.getString("PKSEQ") +"__" + rs.getString("SOTT"),rs.getString("TIENTHUE")  );
					btth_tienhanghd.put( rs.getString("PKSEQ") +"__" + rs.getString("SOTT"),rs.getString("TIENHANG")  );
					btth_ghichuhd.put( rs.getString("PKSEQ") +"__" + rs.getString("SOTT"),rs.getString("GHICHU")  );
					btth_conghd.put( rs.getString("PKSEQ") +"__" + rs.getString("SOTT"),rs.getString("TONGCONG")  );
					btth_diachi.put( rs.getString("PKSEQ") +"__" + rs.getString("SOTT"),rs.getString("DIACHI")  );
				}
				rs.close();
				this.btth_mauhd = btth_mauhd;
				this.btth_kyhieuhd = btth_kyhieuhd;
				this.btth_msthd = btth_msthd;
				this.btth_ngayhd = btth_ngayhd;
				this.btth_Sohd = btth_sohd;
				this.btth_tenNCChd = btth_tenNCChd;
				this.btth_thuesuathd = btth_thuesuathd;
				this.btth_tienhanghd = btth_tienhanghd;
				this.btth_tienthuehd = btth_tienthuehd;
				this.btth_ghichuhd = btth_ghichuhd;
				this.btth_conghd = btth_conghd;
				this.btth_diachi = btth_diachi;
				
				
				//=====select danh sách sản phẩm=======//
				String querySP = "select BUTTOANTONGHOP_FK as BTTH_PK , STT , SANPHAM_FK AS maSP, PHANTRAM " +
						"\n from ERP_BUTTOANTONGHOP_CHITIET_SANPHAM where BUTTOANTONGHOP_FK = " + this.Id;
				System.out.println("==Query sản phẩm popup: " + querySP);
				
				ResultSet rsSP = db.get(querySP);
				
				Hashtable<String, String> spSTT = new Hashtable<String, String>();
				Hashtable<String, String> spMaSP = new Hashtable<String, String>();
				Hashtable<String, Double> spPhanTram = new Hashtable<String, Double>();
				try {
					while (rsSP.next()) {
						spSTT.put(rsSP.getString("STT") + "_" + rsSP.getString("maSP"),rsSP.getString("STT")  );
						spMaSP.put(rsSP.getString("STT") + "_" + rsSP.getString("maSP"),rsSP.getString("maSP")  );
						spPhanTram.put(rsSP.getString("STT") + "_" + rsSP.getString("maSP"),rsSP.getDouble("PHANTRAM")  );
						
					}
					rsSP.close();
					this.phanTram = spPhanTram;
					this.maSanPham = spMaSP;
					this.stt = spSTT;
				} catch (Exception e) {
					e.printStackTrace();
				}
				//=====================================//
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		
		createRs();
	}
	
	private String getDateTime() 
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public int getCount()
	{
		return this.count;
	}
	
	public void setCount(int count)
	{
		this.count=count;	
	}
	
	
	public String Chot()
	{
		if(this.UserId == null || this.UserId.equals("") || this.UserId.equals("null") )
			return "Vui lòng đăng nhập lại";
		
		try
		{
			
			Utility util = new Utility();
			db.getConnection().setAutoCommit(false);
			String query="";
			int dem_Maclon=0;
			query="SELECT COUNT (*) AS DEM " +
					"FROM ERP_MASCLON_DIEUCHINH MASCLON_DC \n" +
					"INNER JOIN ERP_MASCLON MASCLON ON MASCLON.PK_SEQ=MASCLON_DC.MASCLON_FK \n" +
					" WHERE SOCHUNGTU="+this.Id+" and bangthamchieu='ERP_BUTTOANTONGHOP' AND MASCLON.TRANGTHAI=3";
			System.out.println("query mochot "+query);
			ResultSet rsCheck_Masclon = this.db.get(query);
		
			if (rsCheck_Masclon != null)
			{
				if (rsCheck_Masclon.next())
				{
					dem_Maclon=rsCheck_Masclon.getInt("dem");
				}
				rsCheck_Masclon.close();
			}
			if(dem_Maclon>=1)
			{
				
				db.getConnection().rollback();
				return "MC1.3 Trạng thái của Mã sửa chữa lớn không hợp lệ!!!";
				
			}		
			
			query="SELECT COUNT (*) AS DEM \n" +
				  "FROM ERP_CONGCUDUNGCU_DIEUCHINH CCDC_DC \n" +
				  "INNER JOIN ERP_CONGCUDUNGCU CCDC ON CCDC.PK_SEQ=CCDC_DC.CCDC_FK \n " +
				  "INNER JOIN ERP_KHAUHAOCCDC KH_CCDC ON CCDC.PK_SEQ=KH_CCDC.CCDC_FK \n" +
				  "WHERE CCDC_DC.SOCHUNGTU="+this.Id+" and CCDC_DC.bangthamchieu='ERP_BUTTOANTONGHOP' AND CCDC.TRANGTHAI=1";
			System.out.println("sql : "+query);
			ResultSet rsCheck_Btth = this.db.get(query);
			int dem_CPTT=0;
			if (rsCheck_Btth != null)
			{
				if (rsCheck_Btth.next())
				{
					dem_CPTT=rsCheck_Btth.getInt("dem");
				}
				rsCheck_Btth.close();
			}
			if(dem_CPTT>=1)
			{
				db.getConnection().rollback();
				return "MC1.3 Trạng thái của Mã CPTT không hợp lệ!!!";
				
				
			}
			util.HuyUpdate_TaiKhoan_NgayChungTu(db, this.Id, "Bút toán tổng hợp", "NGAYBUTTOAN", "ERP_BUTTOANTONGHOP");
			
			query = "UPDATE ERP_BUTTOANTONGHOP SET TRANGTHAI =1,NGUOISUA='"+this.UserId+"' WHERE PK_SEQ='"+this.Id+"' and trangthai=0";
			if(this.db.updateReturnInt(query)!=1)
			{
				db.getConnection().rollback();
				return "Không thể chốt bút toán này " + query;
			}
			
			String thang = "";
			String nam = "";
			String ngaybuttoan = "";
			
			query = "select NGAYBUTTOAN from ERP_BUTTOANTONGHOP where PK_SEQ = '" + this.Id + "'";
			ResultSet rsNGAYBT = db.get(query);
			if(rsNGAYBT.next())
			{
				ngaybuttoan = rsNGAYBT.getString("NGAYBUTTOAN");
				nam = ngaybuttoan.substring(0, 4);
				thang = ngaybuttoan.substring(5, 7);
			}
			rsNGAYBT.close();
			
			query = "SELECT COUNT(*) AS NUM FROM ERP_BUTTOANTONGHOP_CHITIET WHERE BUTTOANTONGHOP_FK = '" + this.Id + "'";
			ResultSet rs = this.db.get(query);
			rs.next();
			
			int n = rs.getInt("NUM");
			rs.close();
			
			query = "";
			int dem =0;
			for(int i = 1; i <= n; i++){
				dem ++;
			//GHI NHAN TAI KHOAN KE TOAN
				if(query.length() > 0) query += "UNION ALL " ;
				
				query +="SELECT BT.NGAYBUTTOAN AS NGAYCHUNGTU, BT.NGAYBUTTOAN AS NGAYGHINHAN, N'Bút toán tổng hợp' AS LOAICHUNGTU, BT.PK_SEQ AS SOCHUNGTU, BT.DIENGIAI DIENGIAI_CT, BT.TIGIA, BT.TIENTE_FK, \n" +
						" (SELECT TAIKHOANKT_FK FROM ERP_BUTTOANTONGHOP_CHITIET WHERE BUTTOANTONGHOP_FK = BT.PK_SEQ AND STT = " + i + ") AS TK_NO, \n" +
						" ( \n" +
						"	SELECT  \n" +
						"	CASE WHEN NHOMCHIPHI_FK is not null then N'Khoản mục chi phí'   \n" +
						"		 WHEN KHACHHANG_FK is not null then N'Khách hàng'  \n" +
						"		 WHEN KHO_FK is not null then N'Sản phẩm'   \n" +
						"		 WHEN NCC_FK is not null then N'Nhà cung cấp'   \n" +
						"		 WHEN NGANHANG_FK is not null then N'Ngân hàng' \n" +
						"		 WHEN NHANVIEN_FK is not null then N'Nhân viên' \n" +
						"		 WHEN TAISAN_FK   is not null then N'Tài sản'   \n" +
						"		 WHEN DOITUONGKHAC_FK   is not null then N'Đối tượng khác'   \n" +
						"		 WHEN MASCLON_FK   is not null then N'Mã sửa chữa lớn'   \n" +
						"		 WHEN CPTT_FK   is not null then N'Chi phí trả trước'   \n" +
						
						"	ELSE ''  \n" +
						"	END AS DOITUONG_NO \n" +
						"	FROM ERP_BUTTOANTONGHOP_CHITIET WHERE BUTTOANTONGHOP_FK = BT.PK_SEQ AND STT = " + i + " \n" +
						" )AS DOITUONGNO, \n" +
						" ( \n" +
						"	SELECT \n" +
						"	CASE WHEN NHOMCHIPHI_FK is not null then CAST(NHOMCHIPHI_FK as nvarchar(50))  \n" +
						"		 WHEN KHACHHANG_FK is not null then CAST(KHACHHANG_FK as nvarchar(50))  \n" +
						"		 WHEN KHO_FK is not null then CAST( KHO_FK as nvarchar(50))  \n" +
						"		 WHEN NCC_FK is not null then CAST (NCC_FK as nvarchar(50))  \n" +
						"		 WHEN NGANHANG_FK is not null then CAST(NGANHANG_FK as nvarchar(50))  \n" +
						"		 WHEN NHANVIEN_FK is not null then CAST(NHANVIEN_FK as nvarchar(50))  \n" +
						"		 WHEN TAISAN_FK is not null then CAST(TAISAN_FK as nvarchar(50))  \n" +
						"		 WHEN DOITUONGKHAC_FK is not null then CAST(DOITUONGKHAC_FK as nvarchar(50))  \n" +
						"		 WHEN MASCLON_FK is not null then CAST(MASCLON_FK as nvarchar(50))  \n" +
						"		 WHEN CPTT_FK is not null then CAST(CPTT_FK as nvarchar(50))  \n" +
						
						
						"	ELSE ''  \n" +
						"	END AS MADOITUONG_NO \n" +
						"	FROM ERP_BUTTOANTONGHOP_CHITIET WHERE BUTTOANTONGHOP_FK = BT.PK_SEQ AND STT = " + i + " \n" +
						" )AS MADOITUONGNO, \n" +
						" (SELECT TAIKHOANKT_FK FROM ERP_BUTTOANTONGHOP_CHITIET WHERE BUTTOANTONGHOP_FK = BT.PK_SEQ AND STT = " + (i + 1) + ") AS TK_CO, \n" +
						" ( \n" +
						"	SELECT  \n" +
						"	CASE WHEN NHOMCHIPHI_FK is not null then N'Khoản mục chi phí'   \n" +
						"		 WHEN KHACHHANG_FK is not null then N'Khách hàng'  \n" +
						"		 WHEN KHO_FK is not null then N'Sản phẩm'   \n" +
						"		 WHEN NCC_FK is not null then N'Nhà cung cấp'   \n" +
						"		 WHEN NGANHANG_FK is not null then N'Ngân hàng' \n" +
						"		 WHEN NHANVIEN_FK is not null then N'Nhân viên' \n" +
						"		 WHEN TAISAN_FK   is not null then N'Tài sản'   \n" +
						"		 WHEN DOITUONGKHAC_FK   is not null then N'Đối tượng khác'   \n" +
						"		 WHEN MASCLON_FK   is not null then N'Mã sửa chữa lớn'   \n" +
						"		 WHEN CPTT_FK   is not null then N'Chi phí trả trước'   \n" +
						
						
						"	ELSE ''  \n" +
						"	END AS DOITUONG_NO \n" +
						"	FROM ERP_BUTTOANTONGHOP_CHITIET WHERE BUTTOANTONGHOP_FK = BT.PK_SEQ AND STT = " + (i + 1) + "  \n" +
						" )AS DOITUONGCO, \n" +
						" ( \n" +
						"	SELECT \n" +
						"	CASE WHEN NHOMCHIPHI_FK is not null then CAST(NHOMCHIPHI_FK as nvarchar(50))  \n" +
						"		 WHEN KHACHHANG_FK is not null then CAST(KHACHHANG_FK as nvarchar(50))  \n" +
						"		 WHEN KHO_FK is not null then CAST( KHO_FK as nvarchar(50))  \n" +
						"		 WHEN NCC_FK is not null then CAST (NCC_FK as nvarchar(50))  \n" +
						"		 WHEN NGANHANG_FK is not null then CAST(NGANHANG_FK as nvarchar(50))  \n" +
						"		 WHEN NHANVIEN_FK is not null then CAST(NHANVIEN_FK as nvarchar(50))  \n" +
						"		 WHEN TAISAN_FK is not null then CAST(TAISAN_FK as nvarchar(50))  \n" +
						"		 WHEN DOITUONGKHAC_FK is not null then CAST(DOITUONGKHAC_FK as nvarchar(50))  \n" +
						"		 WHEN MASCLON_FK is not null then CAST(MASCLON_FK as nvarchar(50))  \n" +
						"		 WHEN CPTT_FK is not null then CAST(CPTT_FK as nvarchar(50))  \n" +
						
						
						"	ELSE ''  \n" +
						"	END AS MADOITUONG_NO \n" +
						"	FROM ERP_BUTTOANTONGHOP_CHITIET WHERE BUTTOANTONGHOP_FK = BT.PK_SEQ AND STT = " + (i + 1) + " \n" +
						" )AS MADOITUONGCO, \n" +
						" (SELECT ISNULL(GIATRINT,0) FROM ERP_BUTTOANTONGHOP_CHITIET WHERE BUTTOANTONGHOP_FK = BT.PK_SEQ AND STT = " + i + " AND NO > 0) AS SOTIENNT, \n" +
						" (SELECT ISNULL(sum(TIENHANG),0) FROM ERP_BUTTOANTONGHOP_CHITIET_HOADON WHERE BTTH_FK = BT.PK_SEQ AND PKSEQ = "+ (dem -1)+") AS SOTIEN, \n" +
						" (select ISNULL(sum(TIENTHUE),0) from erp_buttoantonghop_chitiet_hoadon WHERE BTTH_FK = BT.PK_SEQ AND PKSEQ = "+ (dem -1)+") AS TIENTHUE, \n" +
	
						" (SELECT DIENGIAI FROM ERP_BUTTOANTONGHOP_CHITIET WHERE BUTTOANTONGHOP_FK = BT.PK_SEQ AND STT = " + i + ") AS DIENGIAI, \n" +
						
						" BT.MACHUNGTU, BT.DIENGIAI DGIAI\n" +
						" , ( \n" +
						"	SELECT isnull( isNPP , 0) isNPP \n" +
						"	FROM ERP_BUTTOANTONGHOP_CHITIET WHERE BUTTOANTONGHOP_FK = BT.PK_SEQ AND STT = " + (i + 1) + " \n" +
						" )AS ISNPP_Co \n" +
						" , ( \n" +
						"	SELECT isnull( isNPP , 0) isNPP \n" +
						"	FROM ERP_BUTTOANTONGHOP_CHITIET WHERE BUTTOANTONGHOP_FK = BT.PK_SEQ AND STT = " + (i) + " \n" +
						" )AS ISNPP_No \n" +
						" , ( \n" +
						"	SELECT isnull( kbh.TEN ,'') TEN \n" +
						"	FROM ERP_BUTTOANTONGHOP_CHITIET A LEFT JOIN KENHBANHANG kbh on A.KBH_FK = kbh.PK_SEQ " +
						"   WHERE A.BUTTOANTONGHOP_FK = BT.PK_SEQ AND STT = " + (i + 1) + " \n" +
						" )AS KBH_FK, \n" +
						" ( \n" +
						"	SELECT isnull( vv.TEN ,'') TEN \n" +
						"	FROM ERP_BUTTOANTONGHOP_CHITIET A LEFT JOIN VUVIEC vv on A.VUVIEC_FK = vv.PK_SEQ " +
						"   WHERE A.BUTTOANTONGHOP_FK = BT.PK_SEQ AND STT = " + (i + 1) + " \n" +
						" )AS VV_FK, \n" +
						" ( \n" +
						"	SELECT isnull( db.TEN ,'') TEN \n" +
						"	FROM ERP_BUTTOANTONGHOP_CHITIET A LEFT JOIN DIABAN db on A.DIABAN_FK = db.PK_SEQ " +
						"   WHERE A.BUTTOANTONGHOP_FK = BT.PK_SEQ AND STT = " + (i + 1) + " \n" +
						" )AS DIABAN_FK, \n" +
						" ( \n" +
						"	SELECT isnull( tt.TEN ,'') TEN \n" +
						"	FROM ERP_BUTTOANTONGHOP_CHITIET A LEFT JOIN TINHTHANH tt on A.TINHTHANH_FK = tt.PK_SEQ " +
						"   WHERE A.BUTTOANTONGHOP_FK = BT.PK_SEQ AND STT = " + (i + 1) + " \n" +
						" )AS TINHTHANH_FK, \n" +
						" ( \n" +
						"	SELECT isnull( bv.TEN ,'') TEN \n" +
						"	FROM ERP_BUTTOANTONGHOP_CHITIET A LEFT JOIN KHACHHANG bv on A.BENHVIEN_FK = bv.PK_SEQ " +
						"   WHERE A.BUTTOANTONGHOP_FK = BT.PK_SEQ AND STT = " + (i + 1) + " \n" +
						" )AS BENHVIEN_FK, \n" +
						" ( \n" +
						"	SELECT top 1 isnull( NHOMCHIPHI_FK_NO ,'0') TEN \n" +
						"	FROM ERP_BUTTOANTONGHOP_CHITIET_TH A  " +
						"   WHERE A.BUTTOANTONGHOP_FK = BT.PK_SEQ AND A.PKSEQ = " +(dem-1) +" \n" +
						" )AS CHIPHINO, \n" +
						" ( \n" +
						"	SELECT top 1 isnull( NHOMCHIPHI_FK_CO ,'0') TEN \n" +
						"	FROM ERP_BUTTOANTONGHOP_CHITIET_TH A  " +
						"   WHERE A.BUTTOANTONGHOP_FK = BT.PK_SEQ AND A.PKSEQ = " +(dem-1) +" \n" +
						" )AS CHIPHICO, \n" +
						" ( \n" +
						"	SELECT isnull( sp.TEN ,'') TEN \n" +
						"	FROM ERP_BUTTOANTONGHOP_CHITIET A LEFT JOIN SANPHAM sp on A.SP_FK = sp.PK_SEQ " +
						"   WHERE A.BUTTOANTONGHOP_FK = BT.PK_SEQ AND A.STT = " + (i + 1) + " \n" +
						" )AS SP_FK \n" +
	
						" FROM ERP_BUTTOANTONGHOP BT \n" +
	
						" WHERE BT.PK_SEQ = '" + this.Id + "' \n" ;
				i++;
			}
			
			System.out.println("___INIT TAI KHOAN: " + query);
			rs = db.get(query);
			
		
				while(rs.next())
				{
				
					if(rs.getString("TK_NO").trim().length() > 0 & rs.getString("TK_CO").trim().length() > 0)
					{						
						String isNPP_No = rs.getString("isNPP_No");
						String isNPP_Co = rs.getString("isNPP_Co");
						String MAHOADON = "";
						String MAUHOADON = "";
						String KYHIEU = "";
						String SOHOADON = "";
						String NGAYHOADON = "";
						String TENNCC = "";
						String MST = "";
						String TIENHANG = "";
						String THUESUAT = "";
						String TEN_KBH = rs.getString("KBH_FK");
						String TEN_VV = rs.getString("VV_FK");
						String TEN_DIABAN = rs.getString("DIABAN_FK");
						String TEN_TINHTHANH = rs.getString("TINHTHANH_FK");
						String TEN_BENHVIEN = rs.getString("BENHVIEN_FK");
						String TEN_SANPHAM = rs.getString("SP_FK");
						String TEN_DT = "";
						String TEN_PB = "";
						String chiphico = rs.getString("CHIPHICO")=="0"?"NULL":rs.getString("CHIPHICO");
						String chiphino = rs.getString("CHIPHINO")=="0"?"NULL":rs.getString("CHIPHINO");
						chiphico = rs.getString("CHIPHICO")==""?"NULL":rs.getString("CHIPHICO");
						chiphino = rs.getString("CHIPHINO")==""?"NULL":rs.getString("CHIPHINO");
						String DIENGIAI_CT = rs.getString("DIENGIAI_CT");
						String DIENGIAI = rs.getString("DIENGIAI_CT");
						
						String doiTuongNo = rs.getString("DOITUONGNO");
						String doiTuongCo = rs.getString("DOITUONGCO");
						String maDoiTuongNo = rs.getString("MADOITUONGNO");
						String maDoiTuongCo = rs.getString("MADOITUONGCO");
						String nppPhatSinhNoFk = "";
						String nppPhatSinhCoFk = "";
						if(doiTuongNo.toUpperCase().equals("KHÁCH HÀNG")){
							if (isNPP_No.equals("0")){
								String queryTemp = " SELECT NPP_FK FROM KHACHHANG WHERE PK_SEQ = " + maDoiTuongNo;
								ResultSet rsNPP_PhatSinh = db.get(queryTemp);
								if (rsNPP_PhatSinh != null){
									while(rsNPP_PhatSinh.next()){
										if(rsNPP_PhatSinh.getString("NPP_FK") != null && !rsNPP_PhatSinh.getString("NPP_FK").equals("1"))
											nppPhatSinhNoFk = rsNPP_PhatSinh.getString("NPP_FK");
									}
								}
							}
							if(isNPP_No.equals("1")){
								String queryTemp = " SELECT TRUCTHUOC_FK FROM NHAPHANPHOI WHERE PK_SEQ = " + maDoiTuongNo;
								ResultSet rsNPP_PhatSinh = db.get(queryTemp);
								if (rsNPP_PhatSinh != null){
									while(rsNPP_PhatSinh.next()){
										if(rsNPP_PhatSinh.getString("TRUCTHUOC_FK") != null && !rsNPP_PhatSinh.getString("TRUCTHUOC_FK").equals("1"))
											nppPhatSinhNoFk = rsNPP_PhatSinh.getString("TRUCTHUOC_FK");
									}
								}
							}
						}
						
						if(doiTuongNo.toUpperCase().equals("MÃ SỬA CHỮA LỚN"))
						{
							String sohieutaikhoan_No_Masclon="";
							query="SELECT SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ="+rs.getString("TK_NO")+" ";
							ResultSet rsSoHieuTaiKhoan_No_Masclon = db.get(query);
							try {
								if (rsSoHieuTaiKhoan_No_Masclon != null) {
									while (rsSoHieuTaiKhoan_No_Masclon.next()) {
										 sohieutaikhoan_No_Masclon = rsSoHieuTaiKhoan_No_Masclon.getString("SOHIEUTAIKHOAN");
									}
									rsSoHieuTaiKhoan_No_Masclon.close();
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							
							if(sohieutaikhoan_No_Masclon.startsWith("241"))
							{
								query="INSERT INTO ERP_MASCLON_DIEUCHINH " +
									  "(MASCLON_FK,GIATRI,LOAICHUNGTU,SOCHUNGTU,BANGTHAMCHIEU,NGAYDIEUCHINH)" +
									  " VALUES " +
									  "	('"+maDoiTuongNo+"',"+Double.parseDouble(rs.getString("sotien"))+",N'Bút toán tổng hợp',"+this.Id+",N'ERP_BUTTOANTONGHOP','"+rs.getString("NGAYCHUNGTU")+"' )";
								
								System.out.println("cccccccccccccccccccc"+query);
								if (!db.update(query)) 
								{
									String msg = "Khong the insert vo ERP_MASCLON_DIEUCHINH" + query;
									db.getConnection().rollback();
									return msg;
								}			
							}
										
						}
						
						
						
						if(doiTuongCo.toUpperCase().equals("MÃ SỬA CHỮA LỚN"))
						{
							String sohieutaikhoan_CO_Masclon="";
							query="SELECT SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ="+rs.getString("TK_CO")+" ";
							ResultSet rsSoHieuTaiKhoan_CO_Masclon = db.get(query);
							try {
								if (rsSoHieuTaiKhoan_CO_Masclon != null) {
									while (rsSoHieuTaiKhoan_CO_Masclon.next()) {
										sohieutaikhoan_CO_Masclon = rsSoHieuTaiKhoan_CO_Masclon.getString("SOHIEUTAIKHOAN");
									}
									rsSoHieuTaiKhoan_CO_Masclon.close();
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							
							if(sohieutaikhoan_CO_Masclon.startsWith("241"))
							{
								query="INSERT INTO ERP_MASCLON_DIEUCHINH " +
									  "(MASCLON_FK,GIATRI,LOAICHUNGTU,SOCHUNGTU,BANGTHAMCHIEU,NGAYDIEUCHINH)" +
									  " VALUES " +
									  "	('"+maDoiTuongCo+"',"+Double.parseDouble(rs.getString("sotien"))+"*(-1)"+",N'Bút toán tổng hợp',"+this.Id+",N'ERP_BUTTOANTONGHOP','"+rs.getString("NGAYCHUNGTU")+"') ";
								
								System.out.println("dddddddddddddddddddd"+query);
								if (!db.update(query)) 
								{
									String msg = "Khong the insert vo ERP_MASCLON_DIEUCHINH" + query;
									db.getConnection().rollback();
									return msg;
								}			
							}
							
							
		
							
						}
						
						
						
						
						if(doiTuongNo.toUpperCase().equals("CHI PHÍ TRẢ TRƯỚC"))
						{
							String sohieutaikhoan_No_Cptt="";
							query="SELECT SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ="+rs.getString("TK_NO")+" ";
							ResultSet rsSoHieuTaiKhoan_No_Cptt = db.get(query);
							try {
								if (rsSoHieuTaiKhoan_No_Cptt != null) {
									while (rsSoHieuTaiKhoan_No_Cptt.next()) {
										sohieutaikhoan_No_Cptt = rsSoHieuTaiKhoan_No_Cptt.getString("SOHIEUTAIKHOAN");
									}
									rsSoHieuTaiKhoan_No_Cptt.close();
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							
							if(sohieutaikhoan_No_Cptt.startsWith("242"))
							{
								query="INSERT INTO ERP_CONGCUDUNGCU_DIEUCHINH " +
									  "(CCDC_FK,GIATRI,LOAICHUNGTU,BANGTHAMCHIEU,SOCHUNGTU,NGAYDIEUCHINH,createlog)" +
									  " VALUES " +
									  "	('"+maDoiTuongNo+"',"+Double.parseDouble(rs.getString("sotien"))+",1,N'ERP_BUTTOANTONGHOP',"+this.Id+",'"+rs.getString("NGAYCHUNGTU")+"',getdate() )";
								
								System.out.println("cccccccccccccccccccc"+query);
								if (!db.update(query)) 
								{
									String msg = "Khong the insert vo ERP_CONGCUDUNGCU_DIEUCHINH" + query;
									db.getConnection().rollback();
									return msg;
								}	
								
								query="UPDATE ERP_CONGCUDUNGCU SET TRANGTHAI=1 WHERE PK_SEQ="+maDoiTuongNo;
								if (!db.update(query)) 
								{
									String msg = "Khong the UPDATE vo ERP_CONGCUDUNGCU" + query;
									db.getConnection().rollback();
									return msg;
								}	
							}
							
							
						}
							
							if(doiTuongCo.toUpperCase().equals("CHI PHÍ TRẢ TRƯỚC"))
							{
								String sohieutaikhoan_Co_Cptt="";
								query="SELECT SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ="+rs.getString("TK_CO")+" ";
								ResultSet rsSoHieuTaiKhoan_Co_Cptt = db.get(query);
								try {
									if (rsSoHieuTaiKhoan_Co_Cptt != null) {
										while (rsSoHieuTaiKhoan_Co_Cptt.next()) {
											sohieutaikhoan_Co_Cptt = rsSoHieuTaiKhoan_Co_Cptt.getString("SOHIEUTAIKHOAN");
										}
										rsSoHieuTaiKhoan_Co_Cptt.close();
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
								
								if(sohieutaikhoan_Co_Cptt.startsWith("242"))
								{
									query="INSERT INTO ERP_CONGCUDUNGCU_DIEUCHINH " +
										  "(CCDC_FK,GIATRI,LOAICHUNGTU,BANGTHAMCHIEU,SOCHUNGTU,NGAYDIEUCHINH,createlog)" +
										  " VALUES " +
										  "	('"+maDoiTuongNo+"',"+Double.parseDouble(rs.getString("sotien"))+"*(-1)"+",1,N'ERP_BUTTOANTONGHOP',"+this.Id+",'"+rs.getString("NGAYCHUNGTU")+"',getdate() )";
									
									System.out.println("cccccccccccccccccccc"+query);
									if (!db.update(query)) 
									{
										String msg = "Khong the insert vo ERP_CONGCUDUNGCU_DIEUCHINH" + query;
										db.getConnection().rollback();
										return msg;
									}
									
									
									query="UPDATE ERP_CONGCUDUNGCU SET TRANGTHAI=1 WHERE PK_SEQ="+maDoiTuongNo;
									if (!db.update(query)) 
									{
										String msg = "Khong the UPDATE vo ERP_CONGCUDUNGCU" + query;
										db.getConnection().rollback();
										return msg;
									}	
								}
							}
						
						if(doiTuongCo.toUpperCase().equals("KHÁCH HÀNG")){
							if (isNPP_Co.equals("0")){
								String queryTemp = " SELECT NPP_FK FROM KHACHHANG WHERE PK_SEQ = " + maDoiTuongCo;
								ResultSet rsNPP_PhatSinh = db.get(queryTemp);
								if (rsNPP_PhatSinh != null){
									while(rsNPP_PhatSinh.next()){
										if(rsNPP_PhatSinh.getString("NPP_FK") != null && !rsNPP_PhatSinh.getString("NPP_FK").equals("1"))
											nppPhatSinhCoFk = rsNPP_PhatSinh.getString("NPP_FK");
									}
								}
							}
							if(isNPP_Co.equals("1")){
								String queryTemp = " SELECT TRUCTHUOC_FK FROM NHAPHANPHOI WHERE PK_SEQ = " + maDoiTuongCo;
								ResultSet rsNPP_PhatSinh = db.get(queryTemp);
								if (rsNPP_PhatSinh != null){
									while(rsNPP_PhatSinh.next()){
										if(rsNPP_PhatSinh.getString("TRUCTHUOC_FK") != null && !rsNPP_PhatSinh.getString("TRUCTHUOC_FK").equals("1"))
											nppPhatSinhCoFk = rsNPP_PhatSinh.getString("TRUCTHUOC_FK");
									}
								}
							}
						}
						
						if(doiTuongNo.toUpperCase().equals("CHI NHÁNH/ĐỐI TÁC")){
							String queryTemp = " SELECT TRUCTHUOC_FK FROM NHAPHANPHOI WHERE PK_SEQ = " + maDoiTuongNo;
							ResultSet rsNPP_PhatSinh = db.get(queryTemp);
							if (rsNPP_PhatSinh != null){
								while(rsNPP_PhatSinh.next()){
									if(rsNPP_PhatSinh.getString("TRUCTHUOC_FK") != null && !rsNPP_PhatSinh.getString("TRUCTHUOC_FK").equals("1"))
										nppPhatSinhNoFk = rsNPP_PhatSinh.getString("TRUCTHUOC_FK");
								}
							}
							 
						}
						
						if(doiTuongCo.toUpperCase().equals("CHI NHÁNH/ĐỐI TÁC")){
							String queryTemp = " SELECT TRUCTHUOC_FK FROM NHAPHANPHOI WHERE PK_SEQ = " + maDoiTuongCo;
							ResultSet rsNPP_PhatSinh = db.get(queryTemp);
							if (rsNPP_PhatSinh != null){
								while(rsNPP_PhatSinh.next()){
									if(rsNPP_PhatSinh.getString("TRUCTHUOC_FK") != null && !rsNPP_PhatSinh.getString("TRUCTHUOC_FK").equals("1"))
										nppPhatSinhCoFk = rsNPP_PhatSinh.getString("TRUCTHUOC_FK");
								}
							}
							 
						}
						
						double TIENTHUE =  rs.getDouble("TIENTHUE");
						System.out.println("TIENTHUE: "+TIENTHUE);		
						String msg =  	util.Update_TaiKhoan_Vat_DienGiai_CHIKHAC_NPP_PHATSINH( db, thang, nam,rs.getString("NGAYCHUNGTU"), rs.getString("NGAYGHINHAN"), "Bút toán tổng hợp", this.Id, rs.getString("TK_NO"), rs.getString("TK_CO"), "", 
									  	rs.getString("SOTIEN"), rs.getString("SOTIEN"), rs.getString("DOITUONGNO"), rs.getString("MADOITUONGNO"), rs.getString("DOITUONGCO"), rs.getString("MADOITUONGCO"), "0", "", "", rs.getString("TIENTE_FK"), "", rs.getString("TIGIA"), 
									  	rs.getString("SOTIEN"), rs.getString("SOTIENNT"), "", "0" , rs.getString("DIENGIAI") , rs.getString("MACHUNGTU"), isNPP_No, isNPP_Co, 
										MAHOADON, MAUHOADON, KYHIEU, SOHOADON,  NGAYHOADON,  TENNCC, MST,  TIENHANG,  THUESUAT, DIENGIAI ,  TEN_DT,  TEN_PB,
										TEN_KBH,  TEN_VV,  TEN_DIABAN,  TEN_TINHTHANH,  TEN_BENHVIEN,  TEN_SANPHAM, DIENGIAI_CT	,chiphino,chiphico,nppPhatSinhNoFk,nppPhatSinhCoFk);

						if(TIENTHUE !=0.0 ){
							String tienthue1 = "" + Math.round(TIENTHUE);
							
							String q = "select pk_seq from erp_taikhoankt where sohieutaikhoan like '13311000' and npp_fk =1";
							ResultSet rtk = db.get(q);
							String tkno = "";
							try {
								if(rtk.next()){
									tkno = rtk.getString("pk_seq");
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							 msg =  	util.Update_TaiKhoan_Vat_DienGiai_CHIKHAC_NPP_PHATSINH( db, thang, nam,rs.getString("NGAYCHUNGTU"), rs.getString("NGAYGHINHAN"), "Bút toán tổng hợp", this.Id, tkno, rs.getString("TK_CO"), "", 
									 tienthue1, tienthue1, rs.getString("DOITUONGNO"), rs.getString("MADOITUONGNO"), rs.getString("DOITUONGCO"), rs.getString("MADOITUONGCO"), "0", "", "", rs.getString("TIENTE_FK"), "", rs.getString("TIGIA"), 
									 tienthue1, "0", "Tiền thuế", "0" , rs.getString("DIENGIAI") , rs.getString("MACHUNGTU"), isNPP_No, isNPP_Co, 
									MAHOADON, MAUHOADON, KYHIEU, SOHOADON,  NGAYHOADON,  TENNCC, MST,  TIENHANG,  THUESUAT, DIENGIAI ,  TEN_DT,  TEN_PB,
									TEN_KBH,  TEN_VV,  TEN_DIABAN,  TEN_TINHTHANH,  TEN_BENHVIEN,  TEN_SANPHAM, DIENGIAI_CT	,chiphino,chiphico,nppPhatSinhNoFk,nppPhatSinhCoFk);
						}
						
						if(msg.trim().length() > 0)
						{
							db.getConnection().rollback();
							return msg;
						}			
					}
				}
				
			db.getConnection().commit();
			db.shutDown();
		}
		catch(Exception er)
		{
			db.update("rollback");
			er.printStackTrace();
			return "Lỗi chốt bút toán: " + er.getMessage();
		}
		
		return "";
	}
	
	public String MoChot()
	{
		if(this.UserId == null || this.UserId.equals("") || this.UserId.equals("null") )
			return "MC1.1 Vui lòng đăng nhập lại";
		
		try
		{
			db.getConnection().setAutoCommit(false);

			String query = "UPDATE ERP_BUTTOANTONGHOP SET TRANGTHAI = 0,NGUOISUA='"+this.UserId+"' WHERE PK_SEQ='"+this.Id+"'";
			if(!this.db.update(query))
			{
				db.getConnection().rollback();
				return "MC1.2 Không thể mở chốt bút toán này ";
			}
			
			UtilityKeToan ukt = new UtilityKeToan();
			ukt.setSoChungTu(this.Id);
			ukt.setLoaiChungTu("Bút toán tổng hợp");
			
			query = "select month(NGAYBUTTOAN) thang, year(ngayButToan) nam from ERP_BUTTOANTONGHOP WHERE PK_SEQ = " + this.Id + "";
			ResultSet rs = this.db.get(query);
			if (rs != null)
			{
				if (rs.next())
				{
					ukt.setThang(rs.getString("thang"));
					ukt.setNam(rs.getString("nam"));
				}
				rs.close();
			}
			
			this.Msg = ukt.HuyUpdate_TaiKhoan(db, this.Id, "N'Bút toán tổng hợp'");
			if(this.Msg.trim().length() > 0)
			{
				db.getConnection().rollback();
				return "MC1.3 Không thể mở chốt bút toán này: không xóa được định khoản " + this.Msg;
			}
			
			int dem_Maclon=0;
			query="SELECT COUNT (*) AS DEM " +
					"FROM ERP_MASCLON_DIEUCHINH MASCLON_DC \n" +
					"INNER JOIN ERP_MASCLON MASCLON ON MASCLON.PK_SEQ=MASCLON_DC.MASCLON_FK \n" +
					" WHERE SOCHUNGTU="+this.Id+" and bangthamchieu='ERP_BUTTOANTONGHOP' AND MASCLON.TRANGTHAI=3";
			ResultSet rsCheck_Masclon = this.db.get(query);
		
			if (rsCheck_Masclon != null)
			{
				if (rsCheck_Masclon.next())
				{
					dem_Maclon=rsCheck_Masclon.getInt("dem");
				}
				rsCheck_Masclon.close();
			}
			if(dem_Maclon>=1)
			{
				
				db.getConnection().rollback();
				return "MC1.3 Trạng thái của Mã sửa chữa lớn không hợp lệ!!!";
				
			}
			
			else if(dem_Maclon==0)
			{
				query="DELETE  erp_masclon_dieuchinh  WHERE SOCHUNGTU="+this.Id+" and bangthamchieu='ERP_BUTTOANTONGHOP'";				
				if(!this.db.update(query))
				{
					db.getConnection().rollback();
					return "MC1.2 Không thể  xóa erp_masclon_dieuchinh"+query;
				}
			}
			
			
			
			query="SELECT COUNT (*) AS DEM \n" +
				  "FROM ERP_CONGCUDUNGCU_DIEUCHINH CCDC_DC \n" +
				  "INNER JOIN ERP_CONGCUDUNGCU CCDC ON CCDC.PK_SEQ=CCDC_DC.CCDC_FK \n " +
				  "INNER JOIN ERP_KHAUHAOCCDC KH_CCDC ON CCDC.PK_SEQ=KH_CCDC.CCDC_FK \n" +
				  "WHERE CCDC_DC.SOCHUNGTU="+this.Id+" and CCDC_DC.bangthamchieu='ERP_BUTTOANTONGHOP' AND CCDC.TRANGTHAI=1";
			System.out.println("sql : "+query);
			ResultSet rsCheck_Btth = this.db.get(query);
			int dem_CPTT=0;
			if (rsCheck_Btth != null)
			{
				if (rsCheck_Btth.next())
				{
					dem_CPTT=rsCheck_Btth.getInt("dem");
				}
				rsCheck_Btth.close();
			}
			if(dem_CPTT>=1)
			{
				db.getConnection().rollback();
				return "MC1.3 Trạng thái của Mã CPTT không hợp lệ!!!";
				
				
			}
			
			else
			{
				
				query="UPDATE ERP_CONGCUDUNGCU SET TRANGTHAI=0 WHERE PK_SEQ in (select ccdc_fk from ERP_CONGCUDUNGCU_DIEUCHINH where sochungtu='"+this.Id+"' and bangthamchieu='ERP_BUTTOANTONGHOP')";
				if(!this.db.update(query))
				{
					db.getConnection().rollback();
					return "MC1.2 Không thể  UPDATE ERP_CONGCUDUNGCU"+query;
				}
				
				
				
				query="DELETE  ERP_CONGCUDUNGCU_DIEUCHINH  WHERE SOCHUNGTU="+this.Id+" and bangthamchieu='ERP_BUTTOANTONGHOP'";	
					if(!this.db.update(query))
					{
						db.getConnection().rollback();
						return "MC1.2 Không thể  xóa ERP_CONGCUDUNGCU_DIEUCHINH"+query;
					}
				
			}
			
			db.getConnection().commit();
			db.shutDown();
		}
		catch(Exception er)
		{
			db.update("rollback");
			er.printStackTrace();
			return "Lỗi chốt bút toán: " + er.getMessage();
		}
		
		return "";
	}
	
	public String Delete()
	{
		
		if(this.UserId==null ||this.UserId.equals("")|| this.UserId.equals("null") )
			return "Vui lòng đăng nhập lại";
		String query="UPDATE ERP_BUTTOANTONGHOP SET TRANGTHAI =2,NGUOISUA='"+this.UserId+"' WHERE PK_SEQ='"+this.Id+"' and trangthai=0";
		if(this.db.updateReturnInt(query)!=1) {
			return "Không thể xóa bút toán này "+query;
		}
		return "";
		
	}

	public String getCongtyId() 
	{
		return this.congtyId;
	}

	public void setCongtyId(String congtyId) 
	{
		this.congtyId = congtyId;
	}
	
	
	
	public void DBClose(){
		try{
			if(kho_NoRs != null) kho_NoRs.close();
			if(kho_CoRs != null) kho_CoRs.close();
			if(nganhang_NoRs != null) nganhang_NoRs.close();
			if(nganhang_CoRs  != null) nganhang_CoRs.close();
			if(Ncc_NoRs  != null) Ncc_NoRs.close();
			if(Ncc_CoRs != null) Ncc_CoRs.close();
			if(Kh_NoRs != null) Kh_NoRs.close();
			if(Kh_CoRs != null) Kh_CoRs.close();
			if(Ts_NoRs != null) Ts_NoRs.close();
			if(Ts_CoRs != null) Ts_CoRs.close();
			if(Nv_NoRs != null) Nv_NoRs.close();
			if(Nv_CoRs != null) Nv_CoRs.close();
			if(TrungtamchiphiNoRs != null) TrungtamchiphiNoRs.close();
			if(TrungtamchiphiCoRs != null) TrungtamchiphiCoRs.close();
			if(kbh != null) kbh.close();	
			if(TaiKhoanKT_NoRs != null) TaiKhoanKT_NoRs.close();
			if(TaiKhoanKT_CoRs != null) TaiKhoanKT_CoRs.close();
			if(mavvRs != null) mavvRs.close();
			
			db.shutDown();
		}catch(java.sql.SQLException e){
			e.printStackTrace();
		}
	}
	
	public String[] getMavvIds() {
		
		return this.MavvIds;
	}
	
	public void setMavvIds(String[] mavvIds) {
		
		this.MavvIds = mavvIds;
	}
	
	public ResultSet getMavvRs() {
		
		String query =  "SELECT CONVERT(VARCHAR, PK_SEQ) AS PK_SEQ, TEN AS TEN " +
						"FROM vuviec WHERE  TRANGTHAI = 1  " ;
		System.out.println(query);

		return this.db.getScrol(query);
	}
	
	public ResultSet getDiabanRs() {
		String query = "SELECT CONVERT(VARCHAR, PK_SEQ) AS PK_SEQ, TEN AS TEN FROM diaban WHERE  TRANGTHAI = 1 ";
		System.out.println(query);
		
		return this.db.getScrol(query);
	}
	
	public String[] getDiabanIds() {
		
		return this.DiabanIds;
	}
	
	public void setDiabanIds(String[] diabanIds) {
		
		this.DiabanIds = diabanIds;
	}
	
	public ResultSet getTinhthanhRs() {
		
		String query = "SELECT CONVERT(VARCHAR, PK_SEQ) AS PK_SEQ, TEN AS TEN FROM TINHTHANH WHERE  TRANGTHAI = 1  ";
		System.out.println(query);
		
		return this.db.getScrol(query);
	}
	
	public String[] getTinhthanhIds() {
		
		return this.TinhthanhIds;
	}
	
	public void setTinhthanhIds(String[] tinhthanhIds) {
		
		this.TinhthanhIds = tinhthanhIds;
	}
	
	public ResultSet getBenhvienRs() {
		
		// LẤY KHÁCH HÀNG LÀ LOẠI BỆNH VIỆN
		String query = "SELECT CONVERT(VARCHAR, PK_SEQ) AS PK_SEQ, MAFAST + ' - ' + TEN AS TEN FROM KHACHHANG WHERE  TRANGTHAI = 1 AND LCH_FK IN (100011) ";
				
		System.out.println(query);
		
		return this.db.getScrol(query);
	}
	
	public String[] getBenhvienIds() {
		
		return this.BenhvienIds;
	}
	
	public void setBenhvienIds(String[] benhvienIds) {
		
		this.BenhvienIds = benhvienIds;
	}
	
	public ResultSet getSanphamRs() {
		
		String query = "SELECT CONVERT(VARCHAR, PK_SEQ) AS PK_SEQ, MA_FAST + ' - ' + TEN AS TEN FROM SANPHAM WHERE  TRANGTHAI = 1";
		
		System.out.println(query);
		return this.db.getScrol(query);
	}
	
	public String[] getSanphamIds() {
		
		return this.SanphamIds;
	}
	
	public void setSanphamIds(String[] sanphamIds) {
		
		this.SanphamIds = sanphamIds;
	}
	
	Hashtable<String, String> btth_hoadon = new Hashtable<String, String>();
	
	public Hashtable<String, String> getBtth_HoaDon() {
		
		return this.btth_hoadon;
	}

	
	public void setBtth_HoaDon(Hashtable<String, String> btth_hoadon) {
		
		this.btth_hoadon = btth_hoadon;
	}

	Hashtable<String, String> btth_mauhd = new Hashtable<String, String>();
	
	public Hashtable<String, String> getBtth_Mauhd() {
		
		return this.btth_mauhd;
	}
	
	public void setBtth_Mauhd(Hashtable<String, String> btth_Mauhd) {
		
		this.btth_mauhd = btth_Mauhd;
	}
	
	Hashtable<String, String> btth_kyhieuhd = new Hashtable<String, String>();
	
	public Hashtable<String, String> getBtth_Kyhieuhd() {
		
		return this.btth_kyhieuhd;
	}
	
	public void setBtth_Kyhieuhd(Hashtable<String, String> btth_Kyhieuhd) {
		
		this.btth_kyhieuhd = btth_Kyhieuhd;
	}
	
	Hashtable<String, String> btth_Sohd = new Hashtable<String, String>();
	
	public Hashtable<String, String> getBtth_Sohd() {
		
		return this.btth_Sohd;
	}
	
	public void setBtth_Sohd(Hashtable<String, String> btth_Sohd) {
		
		this.btth_Sohd = btth_Sohd;
	}
	
	Hashtable<String, String> btth_ngayhd = new Hashtable<String, String>();
	
	public Hashtable<String, String> getBtth_Ngayhd() {
		
		return this.btth_ngayhd;
	}
	
	public void setBtth_Ngayhd(Hashtable<String, String> btth_Ngayhd) {
		
		this.btth_ngayhd = btth_Ngayhd;
	}
	
	Hashtable<String, String> btth_tenNCChd = new Hashtable<String, String>();
	
	public Hashtable<String, String> getBtth_TenNCChd() {
		
		return this.btth_tenNCChd;
	}
	
	public void setBtth_TenNCChd(Hashtable<String, String> btth_TenNCChd) {
		
		this.btth_tenNCChd = btth_TenNCChd;
	}
	
	Hashtable<String, String> btth_msthd = new Hashtable<String, String>();
	
	public Hashtable<String, String> getBtth_MSThd() {
		
		return this.btth_msthd;
	}
	
	public void setBtth_MSThd(Hashtable<String, String> btth_MSThd) {
		
		this.btth_msthd = btth_MSThd;
	}
	
	Hashtable<String, String> btth_tienhanghd = new Hashtable<String, String>();
	
	public Hashtable<String, String> getBtth_Tienhanghd() {
		
		return this.btth_tienhanghd;
	}
	
	public void setBtth_Tienhanghd(Hashtable<String, String> btth_Tienhanghd) {
		
		this.btth_tienhanghd = btth_Tienhanghd;
	}
	
	Hashtable<String, String> btth_thuesuathd = new Hashtable<String, String>();
	
	public Hashtable<String, String> getBtth_Thuesuathd() {
		
		return this.btth_thuesuathd;
	}
	
	public void setBtth_Thuesuathd(Hashtable<String, String> btth_Thuesuathd) {
		
		this.btth_thuesuathd = btth_Thuesuathd;
	}
	
	Hashtable<String, String> btth_tienthuehd = new Hashtable<String, String>();
	
	public Hashtable<String, String> getBtth_Tienthuehd() {
		
		return this.btth_tienthuehd;
	}
	
	public void setBtth_Tienthuehd(Hashtable<String, String> btth_Tienthuehd) {
		
		this.btth_tienthuehd = btth_Tienthuehd;
	}
	
	Hashtable<String, String> btth_ghichuhd = new Hashtable<String, String>();
	
	public Hashtable<String, String> getBtth_Ghichuhd() {
		
		return this.btth_ghichuhd;
	}
	
	public void setBtth_Ghichuhd(Hashtable<String, String> btth_Ghichuhd) {
		
		this.btth_ghichuhd = btth_Ghichuhd;
	}
	
	Hashtable<String, String> btth_conghd = new Hashtable<String, String>();
	
	public Hashtable<String, String> getBtth_Conghd() {
		
		return this.btth_conghd;
	}
	
	public void setBtth_Conghd(Hashtable<String, String> btth_Cong) {
		
		this.btth_conghd = btth_Cong;
	}
	Hashtable<String, String> btth_diachi = new Hashtable<String, String>();
	
	
	public Hashtable<String, String> getBtth_diachi() {
		return btth_diachi;
	}
	public void setBtth_diachi(Hashtable<String, String> btth_diachi) {
		this.btth_diachi = btth_diachi;
	}
	public boolean Editsauchot() {
		
			this.getNppInfo();
			String ngaysua=getDateTime();
			try
			{
				
				Utility util = new Utility();
				
				String sql = "Select NGAYBUTTOAN from ERP_BUTTOANTONGHOP WHERE PK_SEQ = "+this.Id;
				
				System.out.println(sql); 
				
				ResultSet rsKT = db.get(sql);
				
				try 
				{
					while(rsKT.next())
					{							
						String ngayghinhan = rsKT.getString("NGAYBUTTOAN");
						
						System.out.println("Ngayghinhan");
						
						this.Msg = util.CheckKhoaSoKT(db, ngayghinhan, this.nppId);
					}
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
				
				if(this.Msg.trim().length() > 0)
				{
					return false;
				}
					
				//CHECK THANG KHOA SO CO HOP LE HAY KHONG ( CHI DUOC CHOT SAU THANG KHOA SO + 1 )
					
				this.db.getConnection().setAutoCommit(false);
								
				String query="UPDATE  ERP_BUTTOANTONGHOP SET DIENGIAI=N'"+this.DienGiai+"',NGUOISUA='"+this.UserId+"',NGAYSUA='"+ngaysua+"' WHERE PK_SEQ='"+this.Id+"'" ;
				if(!this.db.update(query))
				{
					this.db.getConnection().rollback();
					this.Msg="Loi dong lenh sau "+query;
					return false;
				}
				query="DELETE FROM ERP_BUTTOANTONGHOP_CHITIET WHERE BUTTOANTONGHOP_FK='"+this.Id+"'";
				if(!this.db.update(query))
				{
					this.db.getConnection().rollback();
					this.Msg="Loi dong lenh sau "+query;
					return false;
				}
				
				query="DELETE FROM ERP_BUTTOANTONGHOP_CHITIET_HOADON WHERE BTTH_FK ='"+this.Id+"'";
				if(!this.db.update(query))
				{
					this.db.getConnection().rollback();
					this.Msg="Loi dong lenh sau "+query;
					return false;
				}
				
				query="DELETE FROM ERP_BUTTOANTONGHOP_CHITIET_TH WHERE BUTTOANTONGHOP_FK ='"+this.Id+"'";
				if(!this.db.update(query))
				{
					this.db.getConnection().rollback();
					this.Msg="Loi dong lenh sau "+query;
					return false;
				}
				
				int i=0;
	
				int stt = 1;
				
				String sqlHOADON_BTTH = "";
				
				if(this.TaiKhoanNoIds != null & this.TaiKhoanCoIds != null)
				{
					while(this.TaiKhoanNoIds.length > i)
					{
						String nv_noId = "null";			
						String nv_coId = "null";
	
						String sp_noId = "null";			
						String sp_coId = "null";
	
						String ts_noId = "null";			
						String ts_coId = "null";
	
						String nh_noId = "null";			
						String nh_coId = "null";
	
						String kho_noId = "null";			
						String kho_coId = "null";
	
						String ncc_noId = "null";			
						String ncc_coId = "null";
	
						String kh_noId = "null";			
						String kh_coId = "null";
	
						String dtk_noId = "null";			
						String dtk_coId = "null";

						String ttcp_no = "null";
						String ttcp_co = "null";
						
						if(this.TaiKhoanNoIds[i].trim().length() > 0 && this.TaiKhoanCoIds[i].trim().length() <=0)
						{
								this.db.getConnection().rollback();
								this.Msg = "Vui lòng chọn tài khoản có. ";
								return false;
							
						}
						
						if(this.TaiKhoanNoIds[i].trim().length() <= 0 && this.TaiKhoanCoIds[i].trim().length() > 0)
						{
							this.db.getConnection().rollback();
							this.Msg = "Vui lòng chọn tài khoản nợ. ";
							return false;						
						}
						
						if(this.TaiKhoanNoIds[i].trim().length() > 0 & this.TaiKhoanCoIds[i].trim().length() > 0)
						{
							if(this.TaiKhoanCoIds[i].trim().length() <=0 )
							{
								this.db.getConnection().rollback();
								this.Msg = "Vui lòng chọn tài khoản có. ";
								return false;
							}
	
							query = "SELECT DUNGCHOKHO, DUNGCHONCC, DUNGCHONGANHANG, DUNGCHOTAISAN, DUNGCHOKHACHHANG, COTTCHIPHI, " +
									"DUNGCHONHANVIEN, DUNGCHODOITUONGKHAC, SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ = " + this.TaiKhoanNoIds[i] + "";
							System.out.println(query);
							ResultSet rs  = this.db.get(query);
							rs.next();
	
							//KIỂM TRA
							if(rs.getString("COTTCHIPHI").equals("1") )
							{
								if(TtcpNoIds[i].trim().length() == 0){
									this.db.getConnection().rollback();
									this.Msg = "Tài khoản "+ rs.getString("SOHIEUTAIKHOAN") +" có dùng trung tâm chi phí. Vui lòng chọn trung tâm chi phí! ";
									return false;
								}else{
									ttcp_no = this.TtcpNoIds[i].trim();
								}
							}
	
	
							//KIỂM TRA
							if(rs.getString("DUNGCHONHANVIEN").equals("1") && this.dc_noIds[i].trim().equals("")){
								this.db.getConnection().rollback();
								this.Msg = "Vui lòng chọn nhân viên cho đối tượng nợ. ";
								return false;
	
							}
	
							//KIỂM TRA
							if(rs.getString("DUNGCHOKHO").equals("1") && this.dc_noIds[i].trim().equals("")){
	
								this.db.getConnection().rollback();
								this.Msg = "Vui lòng chọn sản phẩm cho đối tượng nợ. ";
								return false;
	
							}
	
							//KIỂM TRA
							if(rs.getString("DUNGCHONCC").equals("1") && this.dc_noIds[i].trim().equals("")){
	
								this.db.getConnection().rollback();
								this.Msg = "Vui lòng chọn nhà cung cấp cho đối tượng nợ. ";
								return false;
	
							}
	
							//KIỂM TRA
							if(rs.getString("DUNGCHONGANHANG").equals("1") && this.dc_noIds[i].trim().equals("")){
	
								this.db.getConnection().rollback();
								this.Msg = "Vui lòng chọn ngân hàng cho đối tượng nợ. ";
								return false;
	
							}
	
							//KIỂM TRA
							if(rs.getString("DUNGCHOTAISAN").equals("1")&& this.dc_noIds[i].equals("")){
	
								this.db.getConnection().rollback();
								this.Msg = "Vui lòng chọn tài sản cho đối tượng nợ. ";
								return false;
	
							}
	
							//KIỂM TRA
							if(rs.getString("DUNGCHOKHACHHANG").equals("1")&& this.dc_noIds[i].trim().equals("")){
	
								this.db.getConnection().rollback();
								this.Msg = "Vui lòng chọn khách hàng cho đối tượng nợ. ";
								return false;
	
							}
	
							//KIỂM TRA
							if(rs.getString("DUNGCHODOITUONGKHAC").equals("1")&& this.dc_noIds[i].trim().equals("")){
	
								this.db.getConnection().rollback();
								this.Msg = "Vui lòng chọn Đối tượng khác cho đối tượng nợ. ";
								return false;
	
							}

							rs.close();
	
							String[] doituongno = this.dc_noIds[i].split(",");
							if(doituongno[0].equals("1")){
								kho_noId = doituongno[1];
							}
	
							if(doituongno[0].equals("2")){
								ncc_noId = doituongno[1];
							}
	
							if(doituongno[0].equals("3")){
								nh_noId = doituongno[1];
							}
	
							if(doituongno[0].equals("4")){
								ts_noId = doituongno[1];
							}
	
							if(doituongno[0].equals("5")){
								kh_noId = doituongno[1];
							}
	
							if(doituongno[0].equals("7")){
								nv_noId = doituongno[1];
							}
	
							if(doituongno[0].equals("8")){
								dtk_noId = doituongno[1];
							}

							query = "SELECT DUNGCHOKHO, DUNGCHONCC, DUNGCHONGANHANG, DUNGCHOTAISAN, DUNGCHOKHACHHANG, COTTCHIPHI, " +
									"DUNGCHONHANVIEN, DUNGCHODOITUONGKHAC, SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ = " + this.TaiKhoanCoIds[i] + "";
							System.out.println(query);
							rs  = this.db.get(query);
							rs.next();
	
							//KIỂM TRA
							if(rs.getString("COTTCHIPHI").equals("1") && TtcpCoIds[i].trim().length() == 0)
							{
								this.db.getConnection().rollback();
								this.Msg = "Tài khoản "+ rs.getString("SOHIEUTAIKHOAN") +" có dùng trung tâm chi phí. Vui lòng chọn trung tâm chi phí! ";
								return false;
							}else if(rs.getString("COTTCHIPHI").equals("1") && TtcpCoIds[i].trim().length() > 0){
								ttcp_co = this.TtcpCoIds[i].trim();
							}
	
	
							//KIỂM TRA
							if(rs.getString("DUNGCHONHANVIEN").equals("1")&& this.dc_coIds[i].trim().equals("")){
	
								this.db.getConnection().rollback();
								this.Msg = "Vui lòng chọn nhân viên cho đối tượng có. ";
								return false;
	
							}
	
							//KIỂM TRA
							if(rs.getString("DUNGCHOKHO").equals("1")&& this.dc_coIds[i].trim().equals("")){
	
								this.db.getConnection().rollback();
								this.Msg = "Vui lòng chọn kho cho đối tượng có. ";
								return false;
	
							}
	
							//KIỂM TRA
							if(rs.getString("DUNGCHONCC").equals("1") && this.dc_coIds[i].trim().equals("")){
	
								this.db.getConnection().rollback();
								this.Msg = "Vui lòng chọn nhà cung cấp cho đối tượng có. ";
								return false;
	
							}
	
							//KIỂM TRA
							if(rs.getString("DUNGCHONGANHANG").equals("1") && this.dc_coIds[i].trim().equals("")){
	
								this.db.getConnection().rollback();
								this.Msg = "Vui lòng chọn ngân hàng cho đối tượng có. ";
								return false;
	
							}
	
							//KIỂM TRA
							if(rs.getString("DUNGCHOTAISAN").equals("1")&& this.dc_coIds[i].trim().equals("")){
	
								this.db.getConnection().rollback();
								this.Msg = "Vui lòng chọn tài sản cho đối tượng có. ";
								return false;
	
							}
	
							//KIỂM TRA
							if(rs.getString("DUNGCHOKHACHHANG").equals("1")&& this.dc_coIds[i].trim().equals("")){
	
								this.db.getConnection().rollback();
								this.Msg = "Vui lòng chọn khách hàng cho đối tượng có. ";
								return false;
	
							}
	
							//KIỂM TRA
							if(rs.getString("DUNGCHODOITUONGKHAC").equals("1")&& this.dc_coIds[i].trim().equals("")){
	
								this.db.getConnection().rollback();
								this.Msg = "Vui lòng chọn Đối tượng khác cho đối tượng có. ";
								return false;
	
							}
	
							//KIỂM TRA - NẾU CÓ TRUNG TÂM CHI PHÍ THÌ BẮT BUỘC PHẢI CHỌN KÊNH BÁN HÀNG, ĐỊA BÀN
//							if( rs.getString("COTTCHIPHI").equals("1") )
//							{
//								if(kbhIds[i].trim().length() == 0){
//									this.db.getConnection().rollback();
//									this.Msg = "Tài khoản "+ rs.getString("SOHIEUTAIKHOAN") +" có dùng trung tâm chi phí. Vui lòng chọn kênh bán hàng! ";
//									return false;
//								}
//								
//								if(DiabanIds[i].trim().length() == 0)
//								{
//									this.db.getConnection().rollback();
//									this.Msg = "Tài khoản "+ rs.getString("SOHIEUTAIKHOAN") +" có dùng trung tâm chi phí. Vui lòng chọn địa bàn! ";
//									return false;
//								}
//							}
							
	
							String[] doituongco = this.dc_coIds[i].split(",");
							if(doituongco[0].equals("1")){
								
								kho_coId = doituongco[1];
							}
	
							if(doituongco[0].equals("2")){
								ncc_coId = doituongco[1];
							}
	
							if(doituongco[0].equals("3")){
								nh_coId = doituongco[1];
							}
	
							if(doituongco[0].equals("4")){
								ts_coId = doituongco[1];
							}
	
							if(doituongco[0].equals("5")){
								kh_coId = doituongco[1];
								if(this.dc_coIds[i].indexOf("[KH]") >=0) {
									
								}
							}
	
							if(doituongco[0].equals("7")){
								nv_coId = doituongco[1];
							}
	
							if(doituongco[0].equals("8")){
								dtk_coId = doituongco[1];
							}

							if(this.Sotien[i] == null) this.Sotien[i] = "0";
							if(this.Sotien[i].trim().length() == 0) this.Sotien[i] = "0";
	
							if(Double.parseDouble(this.Sotien[i].replaceAll(",", "")) == 0){
	
								this.db.getConnection().rollback();
								this.Msg = "Vui lòng nhập số tiền ";
								return false;
	
							}
	
							String kbh_fk = this.kbhIds[i].trim().length() <= 0 ? "NULL" : this.kbhIds[i];
							String mavv_fk = this.MavvIds[i].trim().length() <= 0 ? "NULL" : this.MavvIds[i];
							String diaban_fk = this.DiabanIds[i].trim().length() <= 0 ? "NULL" : this.DiabanIds[i];
							String tinhthanh_fk = this.TinhthanhIds[i].trim().length() <= 0 ? "NULL" : this.TinhthanhIds[i];
							String benhvien_fk = this.BenhvienIds[i].trim().length() <= 0 ? "NULL" : this.BenhvienIds[i];
							String sanpham_fk = this.SanphamIds[i].trim().length() <= 0 ? "NULL" : this.SanphamIds[i];
	
							//CHỖ NÀY ĐỂ PHÂN BIỆT LÀ NPP HAY KHÁCH HÀNG
							String isNPP_NO = "NULL";
							
							if( kh_noId.contains("-") )
							{
								String kh[] = kh_noId.split("-");
								kh_noId = kh[0];
								isNPP_NO = kh[1];
							}
	
							query = "INSERT INTO ERP_BUTTOANTONGHOP_CHITIET(BUTTOANTONGHOP_FK, TAIKHOANKT_FK, NO, CO, NHOMCHIPHI_FK, KHACHHANG_FK, " +
									"NCC_FK, KHO_FK, NGANHANG_FK, TAISAN_FK, SANPHAM_FK, STT, NHANVIEN_FK, DOITUONGKHAC_FK, KBH_FK, DIENGIAI, isNPP, VUVIEC_FK, DIABAN_FK, TINHTHANH_FK, BENHVIEN_FK, SP_FK) " +
									"VALUES(" + this.Id + ", " + this.TaiKhoanNoIds[i] + ", " + this.Sotien[i].replaceAll(",", "") + ", 0, " + ttcp_no + ", " + kh_noId + ", " +
									"" + ncc_noId + ", " + kho_noId + ", " + nh_noId + ", " +  ts_noId + ", " + sp_noId + ", " + dtk_noId + ", " + ( stt++ ) + ", " + nv_noId + ", " + kbh_fk + ",  N'" + this.dg[i] + "', " + isNPP_NO +
									"," + mavv_fk + ", "+ diaban_fk + ", "+tinhthanh_fk+", "+ benhvien_fk+ ", " + sanpham_fk + ") ";
	
	
							System.out.println("::: INSET LAN 1: " + query);
							if(!this.db.update(query))
							{
								this.db.getConnection().rollback();
								this.Msg="Loi dong lenh sau "+query;
								return false;
							}
	
							System.out.println(":::: KHACHH HANG LAY DUOC: " + kh_coId + " -- DOI TUONG CO ID: " + this.dc_coIds[i] );
	
							//CHỖ NÀY ĐỂ PHÂN BIỆT LÀ NPP HAY KHÁCH HÀNG
							String isNPP_CO = "NULL";
							if( kh_coId.contains("-") )
							{
								String kh[] = kh_coId.split("-");
								kh_coId = kh[0];
								isNPP_CO = kh[1];
							}
	
							query = "INSERT INTO ERP_BUTTOANTONGHOP_CHITIET(BUTTOANTONGHOP_FK, TAIKHOANKT_FK, NO, CO, NHOMCHIPHI_FK, KHACHHANG_FK, " +
									"NCC_FK, KHO_FK, NGANHANG_FK, TAISAN_FK, SANPHAM_FK, STT, NHANVIEN_FK, DOITUONGKHAC_FK, KBH_FK, DIENGIAI, isNPP, VUVIEC_FK, DIABAN_FK, TINHTHANH_FK, BENHVIEN_FK, SP_FK) " +
									"VALUES(" + this.Id + ", " + this.TaiKhoanCoIds[i] + ", 0, " + this.Sotien[i].replaceAll(",", "") + ", " + ttcp_co + ", " + kh_coId + ", " +
									"" + ncc_coId + ", " + kho_coId + ", " + nh_coId + ", " +  ts_coId + ", " + sp_coId + ", " + dtk_coId + ", " + ( stt++ ) + ", " + nv_coId + ", " + kbh_fk + ",  N'" + this.dg[i] + "', " + isNPP_CO + 
									"," + mavv_fk + ", "+ diaban_fk + ", "+tinhthanh_fk+", "+ benhvien_fk+ ", " + sanpham_fk + ") ";
	
							System.out.println("::: INSET LAN 2: " + query);
							if(!this.db.update(query))
							{
								this.db.getConnection().rollback();
								this.Msg="Loi dong lenh sau "+query;
								return false;
							}
							
							query = " INSERT INTO ERP_BUTTOANTONGHOP_CHITIET_TH (BUTTOANTONGHOP_FK, TAIKHOANNOKT_FK, TAIKHOANCOKT_FK , PKSEQ, NHOMCHIPHI_FK_NO, NHOMCHIPHI_FK_CO , NHOMCHIPHI_FK_CO, KHACHHANG_FK_CO , " +
									" NCC_FK_NO, NCC_FK_CO, KHO_FK_NO, KHO_FK_CO, NGANHANG_FK_NO, NGANHANG_FK_CO, TAISAN_FK_NO, TAISAN_FK_CO, SANPHAM_FK_NO, SANPHAM_FK_CO, NHANVIEN_FK_NO, NHANVIEN_FK_CO, DOITUONGKHAC_FK_NO, DOITUONGKHAC_FK_CO, " +
									" KBH_FK, DIENGIAI, isNPP_NO, isNPP_CO, VUVIEC_FK, DIABAN_FK, TINHTHANH_FK, BENHVIEN_FK, SP_FK ) VALUES \n"+
									" ("+this.Id+", "+this.TaiKhoanNoIds[i]+", "+this.TaiKhoanCoIds[i]+", "+this.PKSEQIds[i]+", "+ttcp_no+", "+ttcp_co+", "+kh_noId+"  , "+kh_coId+", " +
									" "+ncc_noId+", "+ncc_coId+", "+kho_noId+", "+kho_coId+", "+nh_noId+", "+nh_coId+", "+ts_noId+", "+ts_coId+", "+sp_noId+", "+sp_coId+", "+nv_noId+", "+nv_coId+", "+dtk_noId+", "+dtk_coId+", " +
									" "+kbh_fk+", N'" + this.dg[i] + "', "+isNPP_NO+", "+isNPP_CO+", "+mavv_fk+", "+diaban_fk+", "+tinhthanh_fk+", "+benhvien_fk+", "+sanpham_fk+") ";
			
									System.out.println("::: INSET ERP_BUTTOANTONGHOP_CHITIET_TH: " + query);
									if(!this.db.update(query))
									{
										this.db.getConnection().rollback();
										this.Msg="Loi dong lenh sau "+query;
										return false;
									}
	
						}
						
						i++;
	
					}
					
					if(this.btth_tienhanghd != null)
					{
						Enumeration<String> keys = this.btth_tienhanghd.keys();					
						
						while(keys.hasMoreElements())
						{
							String key = keys.nextElement();
							
							String _hdTienhang = "0"; 
							
							System.out.println("tienhang_ "+ key + ": "+ btth_tienhanghd.get(key));
							
							String pkseq_stt [] = key.split("__");
							
							if(this.btth_tienhanghd.get(key) != null)
							{
								_hdTienhang = this.btth_tienhanghd.get(key).replaceAll(",", "");
							}	
													
							if(_hdTienhang.trim().length() > 0)
							{
								if(Double.parseDouble(_hdTienhang) > 0)
								{
									sqlHOADON_BTTH += " SELECT "+this.Id+" AS ID, " + pkseq_stt[0] +" AS PK_SEQ , N'' AS MAHOADON, N'"+this.btth_mauhd.get(key)+"' MAUSOHOADON, N'"+this.btth_kyhieuhd.get(key)+"' AS KYHIEU, " +
													  " N'"+this.btth_Sohd.get(key)+"' AS SOHOADON, N'"+this.btth_ngayhd.get(key)+"' NGAYHOADON, N'"+this.btth_tenNCChd.get(key)+"' TENNCC, N'"+this.btth_msthd.get(key)+"' AS MST, " +
													  " "+this.btth_tienhanghd.get(key)+" AS TIENHANG, "+this.btth_thuesuathd.get(key)+" AS THUESUAT, "+this.btth_tienthuehd.get(key)+" AS TIENTHUE, N'"+this.btth_ghichuhd.get(key)+"' AS GHICHU, "+pkseq_stt[1]+" AS SOTT, "+this.btth_conghd.get(key)+" AS TONGCONG ";
									
									sqlHOADON_BTTH += " union ALL ";
									
									System.out.println(sqlHOADON_BTTH);
									
								}
							}
							
						}
						
					}
						
					
					if(sqlHOADON_BTTH.trim().length() > 0)
					{
						sqlHOADON_BTTH = sqlHOADON_BTTH.substring(0, sqlHOADON_BTTH.length() - 10);
						
						query = " insert ERP_BUTTOANTONGHOP_CHITIET_HOADON( BTTH_FK, PKSEQ, MAHOADON, MAUSOHOADON, KYHIEU, SOHOADON, NGAYHOADON, TENNHACUNGCAP, MASOTHUE, TIENHANG, THUESUAT, TIENTHUE, GHICHU, SOTT , TONGCONG ) \n" 
								+ sqlHOADON_BTTH;
					
						System.out.println("::::CHEN HOA DON CHI TIET: " + query);
						if(!db.update(query))
						{
							this.Msg = "Lỗi khi thực thi câu lệnh: " + query;
							db.getConnection().rollback();
							return false;
						}
					}
											
					// HỦY KẾ TOÁN
					
					//GHI NHAN NGUOC LAI TAI KHOAN NO - CO
					query = "select SOCHUNGTU, TAIKHOAN_FK, TAIKHOANDOIUNG_FK, NO, CO, TIENTEGOC_FK, TONGGIATRINT, NGAYGHINHAN  " +
						    "from ERP_PHATSINHKETOAN " +
						    "where LOAICHUNGTU = N'Bút toán tổng hợp' and SOCHUNGTU = '" + Id + "' ";

					System.out.println(query); // round( ( TIENBVAT - isnull(CHIETKHAU, 0) ) * VAT / 100.0, 0 )
					ResultSet rsPSKT = db.get(query);
					
					String nam = "";
					String thang = "";
					try 
					{
						while(rsPSKT.next())
						{
							String taikhoan_fk = rsPSKT.getString("TAIKHOAN_FK");
							String tiente_fk = rsPSKT.getString("TIENTEGOC_FK");
							String ngayghinhan = rsPSKT.getString("NGAYGHINHAN");
							double NO = rsPSKT.getDouble("NO");
							double CO = rsPSKT.getDouble("CO");
							double TONGGIATRINT = rsPSKT.getDouble("TONGGIATRINT");
							
							nam = ngayghinhan.substring(0, 4);
							thang = ngayghinhan.substring(5, 7);
							
							//NEU LA CO THI BAY GIO GHI GIAM CO LAI
							if( NO > 0 )
							{
								query = " update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND - " + NO + ", GIATRINONGUYENTE = GIATRINONGUYENTE - " + TONGGIATRINT + "  " +
										" where TAIKHOANKT_FK = '" + taikhoan_fk + "' and THANG = '" + Integer.parseInt(thang) + "' and NAM = '" + Integer.parseInt(nam) + "' and NGUYENTE_FK = '" + tiente_fk + "' ";
							}
							else
							{
								query = " update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND - " + CO + ", GIATRICONGUYENTE = GIATRICONGUYENTE - " + TONGGIATRINT + "  " +
										" where TAIKHOANKT_FK = '" + taikhoan_fk + "' and THANG = '" + Integer.parseInt(thang) + "' and NAM = '" + Integer.parseInt(nam) + "' and NGUYENTE_FK = '" + tiente_fk + "' ";
							}
							
							System.out.println("1.REVERT NO-CO: " + query);
							
							if(db.updateReturnInt(query)<0)
							{
								this.Msg = "KHÔNG THỂ REVERT KẾ TOÁN. YÊU CẦU LIÊN HỆ LẬP TRÌNH ";
								db.getConnection().rollback();
								return false;
							}
							
						}
						rsPSKT.close();
						
						//HỦY KẾ TOÁN ĐÃ GHI NHẬN
						query = " DELETE ERP_PHATSINHKETOAN WHERE LOAICHUNGTU =  N'Bút toán tổng hợp' and SOCHUNGTU = '"+Id+"'";			
						if(!db.update(query))
						{
							this.Msg = "Không thể hủy ERP_PHATSINHKETOAN " + query;
							db.getConnection().rollback();
							return false;
						}						

						// cập nhật mã chứng từ
						/*query = " update ERP_BUTTOANTONGHOP set machungtu = 'BTTH' + SUBSTRING(NGAYBUTTOAN, 6, 2) + SUBSTRING(NGAYBUTTOAN, 0, 5) + '-' + dbo.LaySoChungTu( " + this.Id + " ), NGAYBUTTOAN='"+this.NgayButToan+"' " + 
								" where pk_seq = '" + this.Id + "' ";
						
						System.out.println("[ERP_BUTTOANTONGHOP] error message:" + query);
						
						if(!db.update(query))
						{
							this.Msg = "Khong the tao moi ERP_BUTTOANTONGHOP: " + query;
							System.out.println("[ErpThutien.createTTHD] error message:" + this.Msg);
							db.getConnection().rollback();
							return false;
						}*/
						
						this.Msg = util.CheckKhoaSoKT(db, this.NgayButToan, this.nppId);	
						
						if(this.Msg.trim().length() > 0)
						{
							return false;
						}
												
						// CHỐT LẠI KẾ TOÁN
						
						query = "SELECT COUNT(*) AS NUM FROM ERP_BUTTOANTONGHOP_CHITIET WHERE BUTTOANTONGHOP_FK = '" + this.Id + "'";
						ResultSet rs = this.db.get(query);
						rs.next();
						
						int n = rs.getInt("NUM");
						rs.close();
						
						query = "";
						
						for(int m = 1; m <= n; m++){
						//GHI NHAN TAI KHOAN KE TOAN
							if(query.length() > 0) query += "UNION ALL " ;
							
							query +="SELECT BT.NGAYBUTTOAN AS NGAYCHUNGTU, BT.NGAYBUTTOAN AS NGAYGHINHAN, N'Bút toán tổng hợp' AS LOAICHUNGTU, BT.PK_SEQ AS SOCHUNGTU, BT.DIENGIAI DIENGIAI_CT,  \n" +
									" (SELECT TAIKHOANKT_FK FROM ERP_BUTTOANTONGHOP_CHITIET WHERE BUTTOANTONGHOP_FK = BT.PK_SEQ AND STT = " + m + ") AS TK_NO, \n" +
									" ( \n" +
									"	SELECT  \n" +
									"	CASE WHEN NHOMCHIPHI_FK is not null then N'Khoản mục chi phí'   \n" +
									"		 WHEN KHACHHANG_FK is not null then N'Khách hàng'  \n" +
									"		 WHEN KHO_FK is not null then N'Sản phẩm'   \n" +
									"		 WHEN NCC_FK is not null then N'Nhà cung cấp'   \n" +
									"		 WHEN NGANHANG_FK is not null then N'Ngân hàng' \n" +
									"		 WHEN NHANVIEN_FK is not null then N'Nhân viên' \n" +
									"		 WHEN TAISAN_FK   is not null then N'Tài sản'   \n" +
									"		 WHEN DOITUONGKHAC_FK   is not null then N'Đối tượng khác'   \n" +

									"	ELSE ''  \n" +
									"	END AS DOITUONG_NO \n" +
									"	FROM ERP_BUTTOANTONGHOP_CHITIET WHERE BUTTOANTONGHOP_FK = BT.PK_SEQ AND STT = " + m + " \n" +
									" )AS DOITUONGNO, \n" +
									" ( \n" +
									"	SELECT \n" +
									"	CASE WHEN NHOMCHIPHI_FK is not null then CAST(NHOMCHIPHI_FK as nvarchar(50))  \n" +
									"		 WHEN KHACHHANG_FK is not null then CAST(KHACHHANG_FK as nvarchar(50))  \n" +
									"		 WHEN KHO_FK is not null then CAST( KHO_FK as nvarchar(50))  \n" +
									"		 WHEN NCC_FK is not null then CAST (NCC_FK as nvarchar(50))  \n" +
									"		 WHEN NGANHANG_FK is not null then CAST(NGANHANG_FK as nvarchar(50))  \n" +
									"		 WHEN NHANVIEN_FK is not null then CAST(NHANVIEN_FK as nvarchar(50))  \n" +
									"		 WHEN TAISAN_FK is not null then CAST(TAISAN_FK as nvarchar(50))  \n" +
									"		 WHEN DOITUONGKHAC_FK is not null then CAST(DOITUONGKHAC_FK as nvarchar(50))  \n" +

									"	ELSE ''  \n" +
									"	END AS MADOITUONG_NO \n" +
									"	FROM ERP_BUTTOANTONGHOP_CHITIET WHERE BUTTOANTONGHOP_FK = BT.PK_SEQ AND STT = " + m + " \n" +
									" )AS MADOITUONGNO, \n" +
									" (SELECT TAIKHOANKT_FK FROM ERP_BUTTOANTONGHOP_CHITIET WHERE BUTTOANTONGHOP_FK = BT.PK_SEQ AND STT = " + (m + 1) + ") AS TK_CO, \n" +
									" ( \n" +
									"	SELECT  \n" +
									"	CASE WHEN NHOMCHIPHI_FK is not null then N'Khoản mục chi phí'   \n" +
									"		 WHEN KHACHHANG_FK is not null then N'Khách hàng'  \n" +
									"		 WHEN KHO_FK is not null then N'Sản phẩm'   \n" +
									"		 WHEN NCC_FK is not null then N'Nhà cung cấp'   \n" +
									"		 WHEN NGANHANG_FK is not null then N'Ngân hàng' \n" +
									"		 WHEN NHANVIEN_FK is not null then N'Nhân viên' \n" +
									"		 WHEN TAISAN_FK   is not null then N'Tài sản'   \n" +
									"		 WHEN DOITUONGKHAC_FK   is not null then N'Đối tượng khác'   \n" +
									
									"	ELSE ''  \n" +
									"	END AS DOITUONG_CO \n" +
									"	FROM ERP_BUTTOANTONGHOP_CHITIET WHERE BUTTOANTONGHOP_FK = BT.PK_SEQ AND STT = " + (m + 1) + "  \n" +
									" )AS DOITUONGCO, \n" +
									" ( \n" +
									"	SELECT \n" +
									"	CASE WHEN NHOMCHIPHI_FK is not null then CAST(NHOMCHIPHI_FK as nvarchar(50))  \n" +
									"		 WHEN KHACHHANG_FK is not null then CAST(KHACHHANG_FK as nvarchar(50))  \n" +
									"		 WHEN KHO_FK is not null then CAST( KHO_FK as nvarchar(50))  \n" +
									"		 WHEN NCC_FK is not null then CAST (NCC_FK as nvarchar(50))  \n" +
									"		 WHEN NGANHANG_FK is not null then CAST(NGANHANG_FK as nvarchar(50))  \n" +
									"		 WHEN NHANVIEN_FK is not null then CAST(NHANVIEN_FK as nvarchar(50))  \n" +
									"		 WHEN TAISAN_FK is not null then CAST(TAISAN_FK as nvarchar(50))  \n" +
									"		 WHEN DOITUONGKHAC_FK is not null then CAST(DOITUONGKHAC_FK as nvarchar(50))  \n" +

									"	ELSE ''  \n" +
									"	END AS MADOITUONG_NO \n" +
									"	FROM ERP_BUTTOANTONGHOP_CHITIET WHERE BUTTOANTONGHOP_FK = BT.PK_SEQ AND STT = " + (m + 1) + " \n" +
									" )AS MADOITUONGCO, \n" +
									" (SELECT ISNULL(NO,0) FROM ERP_BUTTOANTONGHOP_CHITIET WHERE BUTTOANTONGHOP_FK = BT.PK_SEQ AND STT = " + m + ") AS SOTIEN, \n" +
				
									" (SELECT DIENGIAI FROM ERP_BUTTOANTONGHOP_CHITIET WHERE BUTTOANTONGHOP_FK = BT.PK_SEQ AND STT = " + m + ") AS DIENGIAI, \n" +
									
									" BT.MACHUNGTU, BT.DIENGIAI DGIAI, " +
									" ( \n" +
									"	SELECT isnull( isNPP , 0) isNPP \n" +
									"	FROM ERP_BUTTOANTONGHOP_CHITIET WHERE BUTTOANTONGHOP_FK = BT.PK_SEQ AND STT = " + (m + 1) + " \n" +
									" )AS ISNPP, \n" +
									" ( \n" +
									"	SELECT isnull( kbh.TEN ,'') TEN \n" +
									"	FROM ERP_BUTTOANTONGHOP_CHITIET A LEFT JOIN KENHBANHANG kbh on A.KBH_FK = kbh.PK_SEQ " +
									"   WHERE A.BUTTOANTONGHOP_FK = BT.PK_SEQ AND STT = " + (m + 1) + " \n" +
									" )AS KBH_FK, \n" +
									" ( \n" +
									"	SELECT isnull( vv.TEN ,'') TEN \n" +
									"	FROM ERP_BUTTOANTONGHOP_CHITIET A LEFT JOIN VUVIEC vv on A.VUVIEC_FK = vv.PK_SEQ " +
									"   WHERE A.BUTTOANTONGHOP_FK = BT.PK_SEQ AND STT = " + (m + 1) + " \n" +
									" )AS VV_FK, \n" +
									" ( \n" +
									"	SELECT isnull( db.TEN ,'') TEN \n" +
									"	FROM ERP_BUTTOANTONGHOP_CHITIET A LEFT JOIN DIABAN db on A.DIABAN_FK = db.PK_SEQ " +
									"   WHERE A.BUTTOANTONGHOP_FK = BT.PK_SEQ AND STT = " + (m + 1) + " \n" +
									" )AS DIABAN_FK, \n" +
									" ( \n" +
									"	SELECT isnull( tt.TEN ,'') TEN \n" +
									"	FROM ERP_BUTTOANTONGHOP_CHITIET A LEFT JOIN TINHTHANH tt on A.TINHTHANH_FK = tt.PK_SEQ " +
									"   WHERE A.BUTTOANTONGHOP_FK = BT.PK_SEQ AND STT = " + (m + 1) + " \n" +
									" )AS TINHTHANH_FK, \n" +
									" ( \n" +
									"	SELECT isnull( bv.TEN ,'') TEN \n" +
									"	FROM ERP_BUTTOANTONGHOP_CHITIET A LEFT JOIN KHACHHANG bv on A.BENHVIEN_FK = bv.PK_SEQ " +
									"   WHERE A.BUTTOANTONGHOP_FK = BT.PK_SEQ AND STT = " + (m + 1) + " \n" +
									" )AS BENHVIEN_FK, \n" +
									" ( \n" +
									"	SELECT isnull( sp.TEN ,'') TEN \n" +
									"	FROM ERP_BUTTOANTONGHOP_CHITIET A LEFT JOIN SANPHAM sp on A.SP_FK = sp.PK_SEQ " +
									"   WHERE A.BUTTOANTONGHOP_FK = BT.PK_SEQ AND A.STT = " + (m + 1) + " \n" +
									" )AS SP_FK \n" +
				
									" FROM ERP_BUTTOANTONGHOP BT \n" +
				
									" WHERE BT.PK_SEQ = '" + this.Id + "' \n" ;
							m++;
						}
						System.out.println(query);
						
						rs = db.get(query);
						
						if(rs != null)
						{
							while(rs.next())
							{
							
								if(rs.getString("TK_NO").trim().length() > 0 & rs.getString("TK_CO").trim().length() > 0)
								{						
									String isNPP = rs.getString("isNPP");
									String MAHOADON = "";
									String MAUHOADON = "";
									String KYHIEU = "";
									String SOHOADON = "";
									String NGAYHOADON = "";
									String TENNCC = "";
									String MST = "";
									String TIENHANG = "";
									String THUESUAT = "";
									String TEN_KBH = rs.getString("KBH_FK");
									String TEN_VV = rs.getString("VV_FK");
									String TEN_DIABAN = rs.getString("DIABAN_FK");
									String TEN_TINHTHANH = rs.getString("TINHTHANH_FK");
									String TEN_BENHVIEN = rs.getString("BENHVIEN_FK");
									String TEN_SANPHAM = rs.getString("SP_FK");
									String TEN_DT = "";
									String TEN_PB = "";
									String DIENGIAI_CT = rs.getString("DIENGIAI_CT");
									String DIENGIAI = rs.getString("DIENGIAI_CT");
															
									String msg =  	util.Update_TaiKhoan_Vat_DienGiai_CHIKHAC( db, thang, nam,rs.getString("NGAYCHUNGTU"), rs.getString("NGAYGHINHAN"), "Bút toán tổng hợp", this.Id, rs.getString("TK_NO"), rs.getString("TK_CO"), "", 
												  	rs.getString("SOTIEN"), rs.getString("SOTIEN"), rs.getString("DOITUONGNO"), rs.getString("MADOITUONGNO"), rs.getString("DOITUONGCO"), rs.getString("MADOITUONGCO"), "0", "", "", "100000", "", "1", 
												  	rs.getString("SOTIEN"), rs.getString("SOTIEN"), "", "0" , rs.getString("DIENGIAI") , rs.getString("MACHUNGTU"), isNPP,
													MAHOADON, MAUHOADON, KYHIEU, SOHOADON,  NGAYHOADON,  TENNCC, MST,  TIENHANG,  THUESUAT, DIENGIAI ,  TEN_DT,  TEN_PB,
													TEN_KBH,  TEN_VV,  TEN_DIABAN,  TEN_TINHTHANH,  TEN_BENHVIEN,  TEN_SANPHAM, DIENGIAI_CT	,"NULL","NULL");
									
									if(msg.trim().length() > 0)
									{
										db.getConnection().rollback();
										return false;
									}			
								
								}
								
							}
							
						}
						
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}
					
				}else
				{
					this.db.getConnection().rollback();
					this.Msg="Vui long chon tai khoan ke toan";
					return false;
				}
				this.db.getConnection().commit();
				this.db.getConnection().setAutoCommit(true);
			} catch ( Exception e)
			{
				db.update("rollback");
				this.Msg=e.getMessage();
				e.printStackTrace();
				return false;
			}
			return true;
	}
	
	public static String XOA( dbutils db, String Id, String loaict ) 
	{
		String msg = "";
		
		try
		{
//			Utility util = new Utility();
			
			db.getConnection().setAutoCommit(false);
			
			//CHECK KHOA SO THANG
			String query = "";			
			
			//CÀI KẾ TOÁN
			
			String nam = "";
			String thang = "";
			
			//GHI NHAN NGUOC LAI TAI KHOAN NO - CO
			query = "select SOCHUNGTU, TAIKHOAN_FK, TAIKHOANDOIUNG_FK, NO, CO, TIENTEGOC_FK, TONGGIATRINT, NGAYGHINHAN  " +
				    "from ERP_PHATSINHKETOAN " +
				    "where LOAICHUNGTU = N'"+loaict+"' and SOCHUNGTU = '" + Id + "' ";

			System.out.println(query); // round( ( TIENBVAT - isnull(CHIETKHAU, 0) ) * VAT / 100.0, 0 )
			ResultSet rsPSKT = db.get(query);
			
			try 
			{
				while(rsPSKT.next())
				{
					String taikhoan_fk = rsPSKT.getString("TAIKHOAN_FK");
					String tiente_fk = rsPSKT.getString("TIENTEGOC_FK");
					String ngayghinhan = rsPSKT.getString("NGAYGHINHAN");
					double NO = rsPSKT.getDouble("NO");
					double CO = rsPSKT.getDouble("CO");
					double TONGGIATRINT = rsPSKT.getDouble("TONGGIATRINT");
					
					nam = ngayghinhan.substring(0, 4);
					thang = ngayghinhan.substring(5, 7);
					
					//NEU LA CO THI BAY GIO GHI GIAM CO LAI
					if( NO > 0 )
					{
						query = " update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND - " + NO + ", GIATRINONGUYENTE = GIATRINONGUYENTE - " + TONGGIATRINT + "  " +
								" where TAIKHOANKT_FK = '" + taikhoan_fk + "' and THANG = '" + Integer.parseInt(thang) + "' and NAM = '" + Integer.parseInt(nam) + "' and NGUYENTE_FK = '" + tiente_fk + "' ";
					}
					else
					{
						query = " update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND - " + CO + ", GIATRICONGUYENTE = GIATRICONGUYENTE - " + TONGGIATRINT + "  " +
								" where TAIKHOANKT_FK = '" + taikhoan_fk + "' and THANG = '" + Integer.parseInt(thang) + "' and NAM = '" + Integer.parseInt(nam) + "' and NGUYENTE_FK = '" + tiente_fk + "' ";
					}
					
					System.out.println("1.REVERT NO-CO: " + query);
					
					if(db.updateReturnInt(query)<0)
					{
						msg = "KHÔNG THỂ REVERT KẾ TOÁN. YÊU CẦU LIÊN HỆ LẬP TRÌNH ";
						db.getConnection().rollback();
						return msg;
					}
					
				}
				rsPSKT.close();
				

				//HỦY KẾ TOÁN ĐÃ GHI NHẬN
				query = " DELETE ERP_PHATSINHKETOAN WHERE LOAICHUNGTU = N'"+loaict+"' and SOCHUNGTU = '"+Id+"'";			
				if(!db.update(query))
				{
					msg = "Không thể hủy ERP_PHATSINHKETOAN " + query;
					db.getConnection().rollback();
					return msg;
				}
			} 
			catch (Exception e) { e.printStackTrace();}
			
			
			//LUU LAI THONG TIN
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (Exception e) 
		{
			db.update("rollback");
			e.printStackTrace();
			
			try {
				db.getConnection().setAutoCommit(true);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			return e.getMessage();
		}
		
		try {
			db.getConnection().setAutoCommit(true);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		return "";
		
	}
	
	public String getNpp_duocchon_id() {
		
		return this.npp_duocchon_id;
	}

	
	public void setNpp_duocchon_id(String npp_duocchon_id) {
		
		this.npp_duocchon_id = npp_duocchon_id;
	}
	
	private void getNppInfo()
	{				
		geso.traphaco.distributor.util.Utility util = new geso.traphaco.distributor.util.Utility();
		
		if(this.npp_duocchon_id.trim().length() <= 0)
		{
			this.nppId = util.getIdNhapp(this.UserId);
		}
		else
		{
			this.nppId = this.npp_duocchon_id;
		}
	}
	
	public String getnppId()
	{
		return this.nppId;
	}

	public void setnppId(String nppId) 
	{
		this.nppId = nppId;
	}
	
	public String[] getPKSEQIds() {
		
		return this.PKSEQIds;
	}
	
	public void setPKSEQIds(String[] PKSEQIds) {
		
		this.PKSEQIds = PKSEQIds;
	}
	//khu vực popup sản phẩm---------------------------------//
	
	Hashtable<String, String> stt = new Hashtable<String, String>();
	Hashtable<String, String> maSanPham = new Hashtable<String, String>();
	Hashtable<String, Double> phanTram = new Hashtable<String, Double>();
	Hashtable<String, Double> tienViet = new Hashtable<String, Double>();
	Hashtable<String, Double> tienNT = new Hashtable<String, Double>();

	public Hashtable<String, String> getStt() {
		return stt;
	}
	public void setStt(Hashtable<String, String> stt) {
		this.stt = stt;
	}
	public Hashtable<String, String> getMaSanPham() {
		return maSanPham;
	}
	public void setMaSanPham(Hashtable<String, String> maSanPham) {
		this.maSanPham = maSanPham;
	}
	public Hashtable<String, Double> getPhanTram() {
		return phanTram;
	}
	public void setPhanTram(Hashtable<String, Double> phanTram) {
		this.phanTram = phanTram;
	}
	public Hashtable<String, Double> getTienViet() {
		return tienViet;
	}
	public void setTienViet(Hashtable<String, Double> tienViet) {
		this.tienViet = tienViet;
	}
	public Hashtable<String, Double> getTienNT() {
		return tienNT;
	}
	public void setTienNT(Hashtable<String, Double> tienNT) {
		this.tienNT = tienNT;
	}
	public String[] getSoTienNT() {
		return SoTienNT;
	}
	public void setSoTienNT(String[] soTienNT) {
		SoTienNT = soTienNT;
	}
	//lấy số hiệu tài khoản
	public String getSoHieu( String pk, dbutils db){
		String soHieu = "";
		String query = "select PK_SEQ, SOHIEUTAIKHOAN from erp_taikhoankt";
		ResultSet rs = db.get(query);
		if(rs != null){
			try {
				while(rs.next()){
					if(rs.getString("PK_SEQ").equals(pk)){
						soHieu = rs.getString("SOHIEUTAIKHOAN");
					}
				}
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return soHieu;
	}
	public void setTrangThai(String trangThai) {
		this.trangThai = trangThai;
	}
	public String getTrangThai() {
		return trangThai;
	}

	public String[] getDungcho_NoTen() {
		return dc_noTens;
	}
	public void setDungcho_NoTen(String[] dc_noTens) {
		this.dc_noTens = dc_noTens;
	}
	public String[] getDungcho_CoTen() {
		return dc_coTens;
	}
	public void setDungcho_CoTen(String[] dc_coTens) {
		this.dc_coTens = dc_coTens;
	}
	
}
