package geso.traphaco.erp.beans.dubaokinhdoanh.imp;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.Hashtable;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.beans.dubaokinhdoanh.IDubaokinhdoanh_Giay;

public class Dubaokinhdoanh_Giay implements IDubaokinhdoanh_Giay
{
	String 	CTYID, USERID, ID, MSG, KHOID, DIENGIAI, TRANGTHAI, NGAYDUBAO, SOTHANGDUBAO, PHUONGPHAP, LOAISOVOI, TENSANPHAM, NHANHANG, CHUNGLOAI, 
	DVKDID, MOHINH, LOAI, THANG;
	String HIEULUC;
	ResultSet rsKho,rsPhuongphap,rsSovoi,rsSanpham,rsNhanhang,rsChungloai, rsDvkd, rsDubao1, rsDubao2, rsDubao3, rsDubao4;
	String maspstr;
	String[][] data;
	String[] spIds;
	String[] khIds;
	String[] selectedSpIds;
	String[] ngaythang;
	String ngaytonkho;
	ResultSet khRS;
	int count;
	dbutils db;
	
	double thieu ;
	double lotsize ;
	double tonantoan ;
	double tonkho ;
	double tondau ;
	double plnorder;
	int leadtime;
	
	public Dubaokinhdoanh_Giay()
	{
	
	this.CTYID = "";
	this.USERID="";
	this.ID="";
	this.MOHINH = "";
	this.DVKDID = "";
	this.KHOID="";
	this.NGAYDUBAO= this.getDateTime();
	this.LOAISOVOI="";
	this.SOTHANGDUBAO="3";
	this.TENSANPHAM="";
	this.PHUONGPHAP="";
	this.DIENGIAI="";
	this.TRANGTHAI="";
	this.NHANHANG="";
	this.CHUNGLOAI="";
	this.ngaytonkho = "0";
	this.MSG="";
	this.HIEULUC = "1";
	this.maspstr = "";
	this.LOAI = "1";
	this.db= new dbutils();
	
	}
	public Dubaokinhdoanh_Giay(String id)
	{
	
		this.CTYID = "";
		this.ID=id;
		this.MOHINH = "";
		this.DVKDID = "";
		this.USERID="";
		this.DIENGIAI="";		
		this.KHOID="";
		this.NGAYDUBAO= this.getDateTime();
		this.SOTHANGDUBAO="3";
		this.PHUONGPHAP="";
		this.NHANHANG="";
		this.CHUNGLOAI="";
		this.MSG="";
		this.maspstr = "";
		this.HIEULUC = "1";
		this.ngaytonkho = "";
		this.LOAI = "1";
		this.db= new dbutils();
	
	}
	
	public String getCtyId() 
	{
	
	return this.CTYID;
	}
	
	
	public void setCtyId(String ctyId) 
	{
	
	this.CTYID = ctyId;
	}
	
	public String getMohinh()
	{
	
	return this.MOHINH;
	}
	
	public void setMohinh(String MOHINH)
	{
	
	this.MOHINH = MOHINH;
	}
	
	public String getDvkdId()
	{
	
	return this.DVKDID;
	}
	
	public void setDvkdId(String dvkdId)
	{
	
	this.DVKDID = dvkdId;
	}
	
	public ResultSet getDvkdRs()
	{
	
	return this.rsDvkd;
	}
	
	public void setDvkdRs(ResultSet rsDvkd)
	{
	
	this.rsDvkd = rsDvkd;
	}
	
	
	public String getHieuluc() 
	{
	
	return this.HIEULUC;
	}
	
	
	public void setHieuluc(String HIEULUC) 
	{
	
	this.HIEULUC = HIEULUC;
	}
	
	public String getUserId() {
	
	return this.USERID;
	}
	
	
	public void setUserId(String userId) {
	
	this.USERID=userId;
	}
	
	
	public String getId() {
	
	return this.ID;
	}
	
	
	public void setId(String id) 
	{
	
	this.ID=id;
	}
	
	public int getCount() {
	
	return this.count;
	}
	
	
	public void setCount(int count) 
	{
	
	this.count = count;
	}
	
	public String getNgaytonkho() {
	
	return this.ngaytonkho;
	}
	
	
	public void setNgaytonkho(String ngaytonkho) 
	{
	
	this.ngaytonkho = ngaytonkho;
	}
	
	public String getMsg() {
	
	return this.MSG;
	}
	
	
	public void setMsg(String msg) 
	{
	this.MSG=msg;	
	}
	
	
	public String getNgaydubao() {
	
	return this.NGAYDUBAO;
	}
	
	
	public void setNgaydubao(String ngaydubao) {
	
	this.NGAYDUBAO=ngaydubao;
	}
	
	
	public String[] getNgayThang() {
	
	return this.ngaythang;
	}
	
	public String getTrangthai() {
	
	return this.TRANGTHAI;
	}
	
	
	public void setTrangthai(String trangthai) {
	
	this.TRANGTHAI=trangthai;
	}
	
	
	public String getDiengiai() {
	
	return this.DIENGIAI;
	}
	
	
	public void setDiengiai(String diengiai) {
	
	this.DIENGIAI=diengiai;
	}
	
	public String getLoai() {
	
	return this.LOAI;
	}
	
	
	public void setLoai(String loai) {
	
	this.LOAI=loai;
	}
	
	public String getThang() {
	
	return this.THANG;
	}
	
	
	public void setThang(String thang) {
	
	this.THANG = thang;
	}
	
	public String getKhoId() {
	
	return this.KHOID;
	}
	
	
	public void setKhoId(String khoId) {
	
	this.KHOID = khoId;
	}
	
	
	public String getSothangdubao() {
	
	return this.SOTHANGDUBAO;
	}
	
	
	public void setSothangdubao(String sothangdubao) {
	
	this.SOTHANGDUBAO=sothangdubao;
	}
	
	
	public String getPhuongphap() {
	
	return this.PHUONGPHAP;
	}
	
	
	public void setPhuongphap(String phuongphap) {
	
	this.PHUONGPHAP=phuongphap;
	}
	
	public ResultSet getKhoList() {
	
	return this.rsKho;
	}
	
	
	
	public void setKhoList(ResultSet kholist) {
	
	this.rsKho=kholist;
	}
	
	
	
	public ResultSet getPhuongphapList() {
	
	return this.rsPhuongphap;
	}
	
	
	
	public void setPhuongphapList(ResultSet phuongphaplist) {
	
	this.rsPhuongphap=phuongphaplist;
	}
	
	public String[][] getData() {
	return this.data;
	}
	
	public void setData(String[][] data) {
	this.data = data;
	}
	
	public ResultSet getSovoi() {
	return this.rsSovoi;
	}
	
	public void setSovoi(ResultSet sovoi) {
	this.rsSovoi=sovoi;
	}
	
	public ResultSet getSanPhamRs() {
	return this.rsSanpham;
	}
	
	public void setSanPhamRs(ResultSet sanpham) {
	this.rsSanpham=sanpham;
	
	}
	
	public String[] getSanPhamIds() {
	
	return this.spIds;
	}
	
	public void setSanPhamIds(String[] spIds) {
	this.spIds = spIds;
	}
	
	public String[] getKhIds() {
	
	return this.khIds;
	}
	
	public void setKhIds(String[] khIds) {
	this.khIds = khIds;
	}
	
	public String[] getSelectedSpIds() {
	
	return this.selectedSpIds ;
	}
	
	public void setSelectedSpIds(String[] selectedSpIds) {
	this.selectedSpIds = selectedSpIds;
	}
	
	public String getTenPhamIds() {
	return this.TENSANPHAM;
	}
	
	public void setTenPhamIds(String rensanpham) {
	
	this.TENSANPHAM=rensanpham;
	}
	
	public String getNhanhang() {
	
	return this.NHANHANG;
	}
	
	public void setNhanhang(String nhanhang) {
	
	this.NHANHANG=nhanhang;
	}
	
	public String getChungloai() {
	
	return this.CHUNGLOAI;
	}
	
	public void setChungloai(String Chungloai) {
	
	this.CHUNGLOAI=Chungloai;
	}
	
	public ResultSet getNhanhangList() {
	
	return this.rsNhanhang;
	}
	
	public void setNhanhangList(ResultSet nhanhanglist) {
	
	this.rsNhanhang = nhanhanglist;
	}
	
	public ResultSet getKhRS() {
	
	return this.khRS;
	}
	
	public void setKhRS(ResultSet khRS) {
	
	this.khRS = khRS;
	}
	
	public ResultSet getDubaoRS_1() {
	
	return this.rsDubao1;
	}
	
	public void setDubaoRS_1(ResultSet rsDubao1) {
	
	this.rsDubao1 = rsDubao1;
	}
	
	public ResultSet getDubaoRS_2() {
	
	return this.rsDubao2;
	}
	
	public void setDubaoRS_2(ResultSet rsDubao2) {
	
	this.rsDubao2 = rsDubao2;
	}
	
	public ResultSet getDubaoRS_3() {
	
	return this.rsDubao3;
	}
	
	public void setDubaoRS_3(ResultSet rsDubao3) {
	
	this.rsDubao3 = rsDubao3;
	}
	
	public ResultSet getDubaoRS_4() {
	
	return this.rsDubao4;
	}
	
	public void setDubaoRS_4(ResultSet rsDubao4) {
	
	this.rsDubao4 = rsDubao4;
	}
	
	public ResultSet getChungloaiList() {
	
	return this.rsChungloai;
	}
	
	public void setChungloaiList(ResultSet chungloailist) {
	
	this.rsChungloai=chungloailist;
	}
	
	public String getMaspstr() 
	{
	return this.maspstr;
	}
	
	public void setMaspstr(String maspstr) 
	{
	this.maspstr = maspstr;
	}
	
	
	
	public void createRs() 
	{
	String query = "";		
	this.rsNhanhang=this.db.get("select PK_SEQ,TEN from NHANHANG WHERE TRANGTHAI = '1' AND CONGTY_FK = " + this.CTYID  );
	
	this.rsChungloai=this.db.get("select PK_SEQ,TEN from CHUNGLOAI WHERE TRANGTHAI = '1' AND CONGTY_FK = " + this.CTYID );
	
	query = "select pk_seq, ma, ten from erp_sanpham where trangthai = '1' AND CONGTY_FK = " + this.CTYID ;
	
	if(CHUNGLOAI.length()>0)
		query = "select pk_seq, ma, ten from erp_sanpham where trangthai = '1' and chungloai_fk='"+this.CHUNGLOAI+"'";
	
	this.rsSanpham = db.get(query);
		
	int th=0;
		this.ngaythang= this.getNgaydubao().split("-");
		
		if(this.ngaythang.length!=1){
			
		th = Integer.parseInt(this.ngaythang[1]);
		
		if(th == 12) {
			th = 1;        		
			this.ngaythang[1] = "1";        		
			this.ngaythang[0] = "" +  (Integer.parseInt(this.ngaythang[0]) + 1);
		}else{
			th = th + 1;
			this.ngaythang[1] = "" +  (Integer.parseInt(this.ngaythang[1]) + 1);
		}
	
		}
		
	//	System.out.println("So thang du bao: "+this.SOTHANGDUBAO);
	
		
	}
	
	public void createRs_New() 
	{
	String query = "SELECT PK_SEQ AS KHID, TEN FROM ERP_KHACHHANG WHERE CONGTY_FK = 100005 AND MAKETOSTOCK = 0"; 
	this.khRS = this.db.get(query);
	
	query = "select PK_SEQ AS KHOID , TEN + ' - ' + DIACHI AS KHO from ERP_KHOTT WHERE TRANGTHAI = '1' and CONGTY_FK = " + this.CTYID + " and LOAI = 5";
	System.out.println(query);
	
	this.rsKho=this.db.get(query);
	
	//this.rsDvkd = this.db.get("SELECT PK_SEQ AS DVKDID, DONVIKINHDOANH AS DVKD FROM DonViKinhDoanh WHERE CONGTY_FK = " + this.CTYID + " AND TRANGTHAI = '1' ");
	}
	
	public void init() 
	{
	String thang = ""; //this.getDateTime().substring(5, 7);
	String nam = ""; //this.getDateTime().substring(0, 4);
	String M = "", Y = "";
	String m1 = "", m2 = "", m3 = "", m4 = "", m5 = "", m6 = "", m7 = "", m8 = "", m9 = "", m10 = "", m11 = "", m12 = "";
	String y1 = "", y2 = "", y3 = "", y4 = "", y5 = "", y6 = "", y7 = "", y8 = "", y9 = "", y10 = "", y11 = "", y12 = "";
	
	String query = "select *  from ERP_DUBAO where pk_seq = '" + this.ID + "'";
	System.out.println("Cau query la "+query);
	ResultSet rs = db.get(query);
	if(rs != null)
	{
		try 
		{
			while(rs.next())
			{
				this.ID = rs.getString("PK_SEQ");
				
				if(rs.getString("KHO_FK") == null){
					this.KHOID = "";
				}else{
					this.KHOID = rs.getString("KHO_FK");	
				}
				
				this.DIENGIAI=rs.getString("DIENGIAI");
				this.NGAYDUBAO = rs.getString("NGAYDUBAO");
				thang = this.NGAYDUBAO.substring(5, 7);  // Ngày dự báo => tính ra tháng
				nam = this.NGAYDUBAO.substring(0, 4);
				
				this.HIEULUC = rs.getString("HIEULUC");
				
	//			this.MOHINH = rs.getString("MOHINH");
				
			}
			rs.close();
			
			// TÍNH RA 4 THÁNG DỰ BÁO TỪ THÁNG TIẾP THEO TỪ NGÀY DỰ BÁO
			for(int i = 0; i < 4; i++){
	
					for(int j = 1; j <= 4; j++){
						if(Integer.parseInt(thang) + j >  12){
							M = "" + (Integer.parseInt(thang) + j - 12 );	
							Y = "" + (Integer.parseInt(nam) + 1);
						}else{
							M = "" + (Integer.parseInt(thang) + j);
							Y = "" + (Integer.parseInt(nam));
						}
						if(j == 1){
							m1 = M;
							y1 = Y;
						}else if (j == 2){
							m2 = M;
							y2 = Y;
						}else if (j == 3){
							m3 = M;
							y3 = Y;
						}else if (j == 4){
							m4 = M;
							y4 = Y;
						}
					}
				}
				// THÊM SẢN PHẨM
				
			
			//[6:28:33 PM] Nguyễn Duy Hải GESO: cho hiện cả sp ko có giá
			//[6:29:28 PM] Nguyễn Duy Hải GESO: chỉ cần có dự báo số lượng là dc rồi
				query = "SELECT SP.PK_SEQ AS SPID, SP.TEN, DVDL.DONVI, SP.MA, " + //CL.TEN AS CHUNGLOAI, 
						"\n ISNULL((" +
						"	SELECT GIABAN FROM ERP_BGBAN_SANPHAM WHERE BGBAN_FK = 100002 " +
						"	AND SANPHAM_FK = SP.PK_SEQ), 0) AS GIA, " +
						
						"\n ISNULL((SELECT ISNULL(DUKIENBAN, 0) FROM ERP_DUBAOSANPHAMMTS " +
						"WHERE KHO_FK = '100023' AND SANPHAM_FK = SP.PK_SEQ AND DUBAO_FK = " + this.ID + " AND THANG = " + m1 + " AND NAM = " + y1 + "), 0) AS T1, " +
					
						"\n ISNULL((SELECT ISNULL(DUKIENBAN, 0) FROM ERP_DUBAOSANPHAMMTS " +
						"WHERE KHO_FK = '100023' AND SANPHAM_FK = SP.PK_SEQ AND DUBAO_FK = " +  this.ID  + " AND THANG = " + m2 + " AND NAM = " + y2 + "), 0) AS T2, " +
					
						"\n ISNULL((SELECT ISNULL(DUKIENBAN, 0) FROM ERP_DUBAOSANPHAMMTS " +
						"WHERE KHO_FK = '100023' AND SANPHAM_FK = SP.PK_SEQ AND DUBAO_FK = " +  this.ID  + " AND THANG = " + m3 + " AND NAM = " + y3 + "), 0) AS T3, " +
	
						"\n ISNULL((SELECT ISNULL(DUKIENBAN, 0) FROM ERP_DUBAOSANPHAMMTS " +
						"WHERE KHO_FK = '100023' AND SANPHAM_FK = SP.PK_SEQ AND DUBAO_FK = " +  this.ID  + " AND THANG = " + m4 + " AND NAM = " + y4 + "), 0) AS T4 " +						
						
						"\n FROM ERP_SANPHAM SP " +
						"INNER JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ = SP.DVDL_FK " +
						"\n WHERE SP.TRANGTHAI =  1 AND SP.LOAISANPHAM_FK IN (100002, 100003) ";
						/*"\n AND ISNULL((" +
						"	SELECT GIABAN FROM ERP_BGBAN_SANPHAM WHERE BGBAN_FK IN (SELECT TOP 1 PK_SEQ FROM ERP_BANGGIABAN WHERE SUDUNG = 1) " +
						"	AND SANPHAM_FK = SP.PK_SEQ), 0) >= 0 ";*/
				
				if(this.CHUNGLOAI.length() > 0){
					query += " AND SP.CHUNGLOAI_FK = " + this.CHUNGLOAI + " ";
				}
				query += "ORDER BY SP.MA ASC ";
				
				System.out.println("DU BAO 1: " + query);
				this.rsDubao1 = db.get(query);
			
				query = "SELECT  SP.PK_SEQ AS SPID, SP.TEN, DVDL.DONVI, SP.MA, " + //
						"ISNULL((SELECT GIABAN FROM ERP_BGBAN_SANPHAM WHERE BGBAN_FK IN (SELECT TOP 1 PK_SEQ FROM ERP_BANGGIABAN WHERE SUDUNG = 1)  AND TRANGTHAI = 1 AND SANPHAM_FK = SP.PK_SEQ), 0) AS GIA, " +
						"\n ISNULL((SELECT ISNULL(DUKIENBAN, 0) FROM ERP_DUBAOSANPHAMMTS " +
						"WHERE KHO_FK = '100041' AND SANPHAM_FK = SP.PK_SEQ AND DUBAO_FK = " + this.ID + " AND THANG = " + m1 + " AND NAM = " + y1 + "), 0) AS T1, " +
			
						"\n ISNULL((SELECT ISNULL(DUKIENBAN, 0) FROM ERP_DUBAOSANPHAMMTS " +
						"WHERE KHO_FK = '100041' AND SANPHAM_FK = SP.PK_SEQ AND DUBAO_FK = " +  this.ID  + " AND THANG = " + m2 + " AND NAM = " + y2 + "), 0) AS T2, " +
			
						"\n ISNULL((SELECT ISNULL(DUKIENBAN, 0) FROM ERP_DUBAOSANPHAMMTS " +
						"WHERE KHO_FK = '100041' AND SANPHAM_FK = SP.PK_SEQ AND DUBAO_FK = " +  this.ID  + " AND THANG = " + m3 + " AND NAM = " + y3 + "), 0) AS T3, " +
	
						
						"\n ISNULL((SELECT ISNULL(DUKIENBAN, 0) FROM ERP_DUBAOSANPHAMMTS " +
						"WHERE KHO_FK = '100041' AND SANPHAM_FK = SP.PK_SEQ AND DUBAO_FK = " + this.ID + " AND THANG = " + m4 + " AND NAM = " + y4 + "), 0) AS T4 " +
	
						"\n FROM ERP_SANPHAM SP " +
						"INNER JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ = SP.DVDL_FK " +
						"\n WHERE SP.TRANGTHAI =  1  AND SP.LOAISANPHAM_FK IN (100002, 100003) ";
						/*"\n AND ISNULL((" +
						"	SELECT GIABAN FROM ERP_BGBAN_SANPHAM WHERE BGBAN_FK IN (SELECT TOP 1 PK_SEQ FROM ERP_BANGGIABAN WHERE SUDUNG = 1) " +
						"	AND SANPHAM_FK = SP.PK_SEQ), 0) >= 0 ";*/
				
				if(this.CHUNGLOAI.length() > 0){
					query += " AND SP.CHUNGLOAI_FK = " + this.CHUNGLOAI + " ";
				}
				query += "ORDER BY  SP.MA ASC ";
				
				System.out.println("DU BAO 2: " + query);
				this.rsDubao2 = db.get(query);
	
				query = "SELECT SP.PK_SEQ AS SPID, SP.TEN, DVDL.DONVI, SP.MA, " + //CL.TEN AS CHUNGLOAI, 
						"\n ISNULL((SELECT GIABAN FROM ERP_BGBAN_SANPHAM WHERE BGBAN_FK IN (SELECT TOP 1 PK_SEQ FROM ERP_BANGGIABAN WHERE SUDUNG = 1)  AND TRANGTHAI = 1 AND SANPHAM_FK = SP.PK_SEQ), 0) AS GIA, " +
			
						"\n ISNULL((SELECT ISNULL(DUKIENBAN, 0) FROM ERP_DUBAOSANPHAMMTS " +
						"WHERE KHO_FK = '100034' AND SANPHAM_FK = SP.PK_SEQ AND DUBAO_FK = " + this.ID + " AND THANG = " + m1 + " AND NAM = " + y1 + "), 0) AS T1, " +
	
						"\n ISNULL((SELECT ISNULL(DUKIENBAN, 0) FROM ERP_DUBAOSANPHAMMTS " +
						"WHERE KHO_FK = '100034' AND SANPHAM_FK = SP.PK_SEQ AND DUBAO_FK = " +  this.ID  + " AND THANG = " + m2 + " AND NAM = " + y2 + "), 0) AS T2, " +
						
						"\n ISNULL((SELECT ISNULL(DUKIENBAN, 0) FROM ERP_DUBAOSANPHAMMTS " +
						"WHERE KHO_FK = '100034' AND SANPHAM_FK = SP.PK_SEQ AND DUBAO_FK = " +  this.ID  + " AND THANG = " + m3 + " AND NAM = " + y3 + "), 0) AS T3, " +
							
						"\n ISNULL((SELECT ISNULL(DUKIENBAN, 0) FROM ERP_DUBAOSANPHAMMTS " +
						"WHERE KHO_FK = '100034' AND SANPHAM_FK = SP.PK_SEQ AND DUBAO_FK = " + this.ID + " AND THANG = " + m4 + " AND NAM = " + y4 + "), 0) AS T4 " +
	
						"\n FROM ERP_SANPHAM SP " +
	//					"INNER JOIN CHUNGLOAI CL ON CL.PK_SEQ = SP.CHUNGLOAI_FK " +
						"INNER JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ = SP.DVDL_FK " +
						"\n WHERE SP.TRANGTHAI =  1 AND SP.LOAISANPHAM_FK IN (100002, 100003) ";
						/*"\n AND ISNULL((" +
						"	SELECT GIABAN FROM ERP_BGBAN_SANPHAM WHERE BGBAN_FK IN (SELECT TOP 1 PK_SEQ FROM ERP_BANGGIABAN WHERE SUDUNG = 1)" +
						"	AND SANPHAM_FK = SP.PK_SEQ), 0) >= 0 ";*/
				
				if(this.CHUNGLOAI.length() > 0){
					query += " AND SP.CHUNGLOAI_FK = " + this.CHUNGLOAI + " ";
				}
				query += "ORDER BY  SP.MA ASC ";
				System.out.println("DU BAO 3: " + query);
				//System.out.println(query);
				this.rsDubao3 = db.get(query);
	
				query = "SELECT SP.PK_SEQ AS SPID, SP.TEN, DVDL.DONVI, SP.MA, " + 
						"\n ISNULL((SELECT GIABAN FROM ERP_BGBAN_SANPHAM WHERE BGBAN_FK IN (SELECT TOP 1 PK_SEQ FROM ERP_BANGGIABAN WHERE SUDUNG = 1)  AND TRANGTHAI = 1 AND SANPHAM_FK = SP.PK_SEQ), 0) AS GIA, " +
					
						"\n ISNULL((SELECT ISNULL(SUM(DUKIENBAN), 0) FROM ERP_DUBAOSANPHAMMTS " +
						"WHERE SANPHAM_FK = SP.PK_SEQ AND DUBAO_FK = " + this.ID + " AND THANG = " + m1 + " AND NAM = " + y1 + "), 0)  AS T1, " +
	
						"\n ISNULL((SELECT ISNULL(SUM(DUKIENBAN), 0) FROM ERP_DUBAOSANPHAMMTS " +
						"WHERE SANPHAM_FK = SP.PK_SEQ AND DUBAO_FK = " +  this.ID  + " AND THANG = " + m2 + " AND NAM = " + y2 + "), 0) AS T2, " +
	
						"\n ISNULL((SELECT ISNULL(SUM(DUKIENBAN), 0) FROM ERP_DUBAOSANPHAMMTS " +
						"WHERE SANPHAM_FK = SP.PK_SEQ AND DUBAO_FK = " +  this.ID  + " AND THANG = " + m3 + " AND NAM = " + y3 + "), 0) AS T3, " +
						
						"\n ISNULL((SELECT ISNULL(SUM(DUKIENBAN), 0) FROM ERP_DUBAOSANPHAMMTS " +
						"WHERE SANPHAM_FK = SP.PK_SEQ AND DUBAO_FK = " + this.ID + " AND THANG = " + m4 + " AND NAM = " + y4 + "), 0)  AS T4 " +
	
						"\n FROM ERP_SANPHAM SP " +
						" INNER JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ = SP.DVDL_FK " +
						"\n WHERE SP.TRANGTHAI =  1 AND SP.LOAISANPHAM_FK IN (100002, 100003) ";
						/*"\n AND ISNULL((" +
						"	SELECT GIABAN FROM ERP_BGBAN_SANPHAM WHERE BGBAN_FK IN (SELECT TOP 1 PK_SEQ FROM ERP_BANGGIABAN WHERE SUDUNG = 1) " +
						"	AND SANPHAM_FK = SP.PK_SEQ), 0) >= 0 ";*/
				
				if(this.CHUNGLOAI.length() > 0){
					query += " AND SP.CHUNGLOAI_FK = " + this.CHUNGLOAI + " ";
				}
				query += "ORDER BY  SP.MA ASC ";
				//System.out.println(query);
				System.out.println("DU BAO 4: " + query);
				this.rsDubao4 = db.get(query);

		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			System.out.println("Loi roi" + e.toString());
		}
	}
	
	this.createRsMTS();
	
	}
	
	
	private String getDateTime() 
	{
		//bên này có trường hợp chạy lại cho quá khứ, nên YC đổi lại lấy theo ngày tạo dự báo
		if( this.NGAYDUBAO == null )
			this.NGAYDUBAO = "";
		
		if( this.NGAYDUBAO.trim().length() > 0 )
			return this.NGAYDUBAO;
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);	
	}
	
	public boolean CreateDubao(HttpServletRequest request)  throws ServletException, IOException
	{
	System.out.println("vao day ............insert");
	String ngaytao = this.getDateTime();
	
	try 
	{
		this.db.getConnection().setAutoCommit(false);
		System.out.print("---------------Kho------------ "+this.getKhoId());
	
		if(this.HIEULUC.equals("1")){
			this.db.update("UPDATE ERP_DUBAO set HIEULUC = '0' WHERE CONGTY_FK = '" + this.CTYID + "' AND KHO_FK = " + this.KHOID );
		}
		
		String	query = "INSERT ERP_DUBAO (CONGTY_FK, NGAYDUBAO, DIENGIAI, TRANGTHAI, NGUOITAO, NGAYTAO, NGUOISUA, NGAYSUA, " +
						"NGAYTONKHOANTOAN, HIEULUC, MOHINH) " +
						"VALUES(" + this.CTYID + ", '" + this.NGAYDUBAO + "',N'"+this.DIENGIAI+"','"+"0"+"','"+USERID+"', " +
						"'"+ngaytao+"','"+USERID+"','"+ngaytao+"', '0', '" + this.HIEULUC + "', '0' )";
			
		
		System.out.println(query);
		
		if(!db.update(query))
		{
			this.MSG = "Lỗi kỹ thuật : " + query;
			db.getConnection().rollback();
			return false;
		}else{
			System.out.println(query);
		}
	
		query = "select SCOPE_IDENTITY() as dubaoId";
		
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			if(rs.next())
			{
				this.ID = rs.getString("dubaoId");
				rs.close();
			}
		}
		else
		{
			this.MSG = "Lỗi kỹ thuật : " + query;
			db.getConnection().rollback();
			return false;
		}
		System.out.println("id"+this.ID);
		this.db.getConnection().commit();
		this.db.getConnection().setAutoCommit(true);
		
		this.CreateDubaoSPMTS(request, "100023"); // KHO MIỀN BẮC
		this.CreateDubaoSPMTS(request, "100041"); // KHO MIỀN TRUNG
		this.CreateDubaoSPMTS(request, "100034"); // KHO MIỀN NAM
				
	}
	catch(Exception e)
	{
		System.out.println("__EXCEPTION DU BAO: " + e.getMessage());
		return false;
	}
	
	return true;
	}	
	
	private boolean CreateDubaoSPMTS(HttpServletRequest request, String khoId)  throws ServletException, IOException
	{
	String ngaytao = this.getDateTime();
	try
	{
		this.db.getConnection().setAutoCommit(false);
		
		String query = "SELECT DATEADD(d,-1, DATEADD(mm, DATEDIFF(mm, 0 ,'"+ ngaytao + "'), 0)) AS NGAY";
		ResultSet rs = this.db.get(query);			
		rs.next();
	
		String  ngaytd = rs.getString("NGAY").substring(0, 10);
		rs.close();
	
		query = "SELECT DATEADD(d,-1, DATEADD(mm, DATEDIFF(mm, 0 ,'"+ ngaytd +"')-3, 0)) AS NGAY";
		rs = this.db.get(query);
		rs.next();
	
		String  ngaydsd3M = rs.getString("NGAY").substring(0, 10);
		rs.close();
	
		query = "SELECT DATEADD(d,-1, DATEADD(mm, DATEDIFF(mm, 0 ,'"+ ngaytd +"')- 6, 0)) AS NGAY";
		rs = this.db.get(query);
	
		rs.next();
	
		String  ngaydsd6M = rs.getString("NGAY").substring(0, 10);
	
		rs.close();
	
		int thang = Integer.valueOf(ngaytao.substring(5, 7)).intValue();
		int nam   = Integer.valueOf(ngaytao.substring(0, 4)).intValue();
		
		if(thang == 12){
			thang = 1;
			nam = nam + 1;
		}else{
			thang = thang + 1;
		}
		
		String lastmonth;
		
		
		for (int i = 0; i < 12; i++)
		{
			int lastyear = nam - 1;
			
			if(thang < 10)
				lastmonth = lastyear + "-0" + thang;
			else
				lastmonth = lastyear + "-" + thang;
			
			
			//Doi lai ( Ton kho tai thoi diem HIEN TAI + NHAP - XUAT toi cuoi thang bat dau du bao = TonDau )
			String thangTiep = Integer.toString(thang) + 1;
			int namTiep = nam;
			if(thang + 1 > 12)
			{
				thangTiep = "01";
				namTiep = namTiep + 1;
			}
			query =	"INSERT INTO ERP_DUBAOSANPHAMMTS (SANPHAM_FK, SONGAYBANHANG, SOVOI_FK, TANGTRUONG, TONDAU, DUKIENBAN, TONKHOANTOAN," +
					"SANXUAT, TONCUOI, DUBAO_FK, NAM, THANG, AVG3M, AVG6M, LASTYEAR, GIA, KHO_FK) " ;
	
			query += "SELECT PK_SEQ AS SPID, 0 AS SONGAYBANHANG, NULL AS SOVOI_FK, 0 AS TANGTRUONG, ISNULL(TONDAU.SOLUONG, 0) AS TONDAU, " + 
					"ISNULL(AVG3M.AVG3M_PRI, 0) AS DUKIENBAN, 0, 0, 0, " + this.ID + ", '" + nam + "', '" + thang + "', " +
					"ISNULL(AVG3M.AVG3M_PRI, 0) AS AVG3M_PRI, " +
					"ISNULL(AVG6M.AVG6M_PRI, 0) AS AVG6M_PRI, " +
					"ISNULL(LASTYEAR.LASTYEAR_PRI, 0) AS LASTYEAR_PRI, " +
					"0, '" + khoId + "' " +
					
					"FROM ERP_SANPHAM SP " +
					"LEFT JOIN " +
					"( " +
						"select SPID, SUM(soluong) as soluong " +
						"from " +
						"(	 " +
	//					-- Ton DAU 
							"select SANPHAM_FK as SPID, SUM(SOLUONG) as SoLuong  " +
							"from ERP_KHOTT_SANPHAM a " +
							"inner join ERP_KHOTT b on a.KHOTT_FK = b.PK_SEQ  " +
							"inner join ERP_SANPHAM c on a.SANPHAM_FK = c.PK_SEQ  " +
							"where b.PK_SEQ IN ( " + khoId  + ") " +
							"group by SANPHAM_FK  " +
							
	//					-- CHUYỂN KHO TỪ CÔNG TY CHƯA NHẬN HÀNG 
						"UNION ALL " +
						"	SELECT CK_SP.SANPHAM_FK, SUM(CK_SP.SOLUONGNHAN) AS SOLUONG " +
						"	FROM ERP_CHUYENKHO_SANPHAM CK_SP " +
						"	INNER JOIN ERP_CHUYENKHO CK ON CK.PK_SEQ = CK_SP.CHUYENKHO_FK " +
						"	WHERE KHONHAN_FK IN ( " + khoId  + ")  AND TRANGTHAI = 0 " +
						"	AND CK.NGAYNHAN >= '" + this.getDateTime() + "' " +
						"	AND CK.NGAYNHAN < '" + namTiep + "-" + thangTiep + "-01' " +
						"	GROUP BY CK_SP.SANPHAM_FK " +						
	
	//					--XUAT KHO CHUA CHOT 
						"union all " +
						"	select b.SANPHAM_FK as SPID,  (-1) * SUM(b.SOLUONG) as soLuong  " +
						"	from ERP_XUATKHO a " +
						"	inner join ERP_XUATKHO_SANPHAM b on a.PK_SEQ = b.XUATKHO_FK " +
						"	inner join ERP_SANPHAM c on b.SANPHAM_FK = c.PK_SEQ  " +
						"	where a.TRANGTHAI = 0 AND a.KHO_FK IN ( " + khoId  + ") " +
						"	AND a.NGAYXUAT >= '" + this.getDateTime() + "' " +
						"	and a.NGAYXUAT < '" + namTiep + "-" + thangTiep + "-01' " +
						"	group by SANPHAM_FK " +
						")  " +
						"dauky group by SPID	 " +
					")TONDAU ON TONDAU.SPID = SP.PK_SEQ " +
					"LEFT JOIN  " +
					"(  " +
						"SELECT	SP.PK_SEQ AS SPID,  SUM (CONVERT(FLOAT, SOLUONG) )/3 AS AVG3M_PRI    " +
						"FROM ERP_XUATKHO XK " +
						"INNER JOIN ERP_XUATKHO_SANPHAM XKSP ON XK.PK_SEQ = XKSP.XUATKHO_FK    " +
						"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = XKSP.SANPHAM_FK  " +
						"INNER JOIN ERP_DONDATHANG DDH on XK.DONDATHANG_FK = DDH.PK_SEQ 	 " +
						"WHERE XK.TRANGTHAI = 1 AND XK.NGAYCHOT >= '" + ngaydsd3M + "' AND XK.NGAYCHOT <= '" + ngaytd + "' " +
						"AND XK.KHO_FK IN ( " + khoId  + ") " +
						"GROUP BY SP.PK_SEQ   " +
					") " +
					"AVG3M ON AVG3M.SPID = SP.PK_SEQ  " +
					"LEFT JOIN  " +
					"(  " +
						"SELECT	SP.PK_SEQ AS SPID,  SUM (CONVERT(FLOAT, SOLUONG) )/6 AS AVG6M_PRI    " +
						"FROM ERP_XUATKHO XK  " +
						"INNER JOIN ERP_XUATKHO_SANPHAM XKSP ON XK.PK_SEQ = XKSP.XUATKHO_FK    " +
						"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = XKSP.SANPHAM_FK   " +
						"INNER JOIN ERP_DONDATHANG DDH on XK.DONDATHANG_FK = DDH.PK_SEQ 	 " +
						"WHERE XK.TRANGTHAI = 1 AND XK.NGAYCHOT >= '" + ngaydsd6M + "' AND XK.NGAYCHOT <= '" + ngaytd + "'   " +
						"AND XK.KHO_FK IN ( " + khoId  + ") " +
						"GROUP BY SP.PK_SEQ   " +
					")  " +
					"AVG6M ON AVG6M.SPID = SP.PK_SEQ   " +
					"LEFT JOIN   " +
					"(  " +
						"SELECT	SP.PK_SEQ AS SPID,  SUM (CONVERT(FLOAT, SOLUONG) )/12 AS LASTYEAR_PRI    " +
						"FROM ERP_XUATKHO XK  " +
						"INNER JOIN ERP_XUATKHO_SANPHAM XKSP ON XK.PK_SEQ = XKSP.XUATKHO_FK    " +
						"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = XKSP.SANPHAM_FK  " +
						"INNER JOIN ERP_DONDATHANG DDH on XK.DONDATHANG_FK = DDH.PK_SEQ 	  " +
						"WHERE XK.TRANGTHAI = 1 AND SUBSTRING(XK.NGAYCHOT, 1, 7)  = '" + lastmonth + "'  " +
						"AND XK.KHO_FK IN ( " + khoId  + ") " +
						"GROUP BY SP.PK_SEQ   " +
					") " +
					"LASTYEAR ON LASTYEAR.SPID = SP.PK_SEQ    " +
					"WHERE SP.TRANGTHAI = '1' AND SP.CONGTY_FK = '" + this.CTYID + "' AND SP.LOAISANPHAM_FK IN (100002, 100003) ";
	
			System.out.println(query);
			this.db.update(query);
					
			query = "INSERT INTO ERP_DUBAOSANPHAMMTS_TUAN(DUBAO_FK, THANG, NAM, KHO_FK, SANPHAM_FK, T1, T2, T3, T4) " +
					"SELECT " + this.ID + ", '" + thang + "', '" + nam + "', '" + khoId  + "', " +
					"SP.PK_SEQ, ISNULL(AVG3M.AVG3M_PRI/4, 0), ISNULL(AVG3M.AVG3M_PRI/4, 0), ISNULL(AVG3M.AVG3M_PRI/4, 0), ISNULL(AVG3M.AVG3M_PRI/4, 0) " +
					"FROM ERP_SANPHAM SP " +
					"LEFT JOIN  " +
					"(  " +
						"SELECT	SP.PK_SEQ AS SPID,  ISNULL(SUM (CONVERT(FLOAT, SOLUONG) )/3, 0) AS AVG3M_PRI    " +
						"FROM ERP_XUATKHO XK " +
						"INNER JOIN ERP_XUATKHO_SANPHAM XKSP ON XK.PK_SEQ = XKSP.XUATKHO_FK    " +
						"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = XKSP.SANPHAM_FK  " +
						"INNER JOIN ERP_DONDATHANG DDH on XK.DONDATHANG_FK = DDH.PK_SEQ 	 " +
						"WHERE XK.TRANGTHAI = 1 AND XK.NGAYCHOT >= '" + ngaydsd3M + "' AND XK.NGAYCHOT <= '" + ngaytd + "' " +
						"AND XK.KHO_FK IN ( " + khoId  + ") " +
						"GROUP BY SP.PK_SEQ   " +
					") " +
					"AVG3M ON AVG3M.SPID = SP.PK_SEQ  " +
					"WHERE SP.TRANGTHAI = '1' AND SP.CONGTY_FK = '" + this.CTYID + "' AND SP.LOAISANPHAM_FK IN (100002, 100003) ";
			System.out.println(query);
			this.db.update(query);
			
			if(thang == 12) {
				thang = 1;
				nam = nam + 1;
			}else{
				thang = thang + 1;
			}
		}
	
		db.getConnection().commit();
		db.getConnection().setAutoCommit(true);
		return true;
	} 
	catch (Exception e) 
	{
		db.update("rollback");
		this.MSG="Lỗi kỹ thuật( "+e.getMessage()+" )";
		return false;
	}
	}
	
	private boolean CreateDubaoSPMTO(HttpServletRequest request)  throws ServletException, IOException
	{
	String ngaytao = this.getDateTime();
	System.out.println("Vao tao moi du bao.");
	try
	{
		this.db.getConnection().setAutoCommit(false);
		
		String query = "SELECT DATEADD(d,-1, DATEADD(mm, DATEDIFF(mm, 0 ,'"+ ngaytao + "'), 0)) AS NGAY";
		ResultSet rs = this.db.get(query);		
		rs.next();
	
		String  ngaytd = rs.getString("NGAY").substring(0, 10);
	
		System.out.println("Ngay TD: " + ngaytd);
		
		rs.close();
	
		query = "SELECT DATEADD(d,-1, DATEADD(mm, DATEDIFF(mm, 0 ,'"+ ngaytd +"')-3, 0)) AS NGAY";
		rs = this.db.get(query);
		rs.next();
	
		String  ngaydsd3M = rs.getString("NGAY").substring(0, 10);
	
		System.out.println("Ngay DS TB 3 Thang: " + ngaydsd3M);
		
		rs.close();
	
		query = "SELECT DATEADD(d,-1, DATEADD(mm, DATEDIFF(mm, 0 ,'"+ ngaytd +"')- 6, 0)) AS NGAY";
		rs = this.db.get(query);
		rs.next();
	
		String  ngaydsd6M = rs.getString("NGAY").substring(0, 10);
	
		System.out.println("Ngay DS TB 6 Thang: " + ngaydsd6M);
		
		rs.close();
	
	//	String tonkhoantoan = "0";
		int thang = Integer.valueOf(ngaytao.substring(5, 7)).intValue();
		int nam   = Integer.valueOf(ngaytao.substring(0, 4)).intValue();
		
		if(thang == 12){
			thang = 1;
			nam = nam + 1;
		}else{
			thang = thang + 1;
		}
		
	
		String lastmonth;
		for (int i = 0; i < 12; i++)
		{
			int lastyear = nam - 1;
			
			if(thang < 10)
				lastmonth = lastyear + "-0" + thang;
			else
				lastmonth = lastyear + "-" + thang;
	
			query =  " SELECT DB.*, ISNULL(AVG3M.AVG3M_PRI, 0) AS AVG3M_PRI,  " + 
					 " 			 ISNULL(AVG6M.AVG6M_PRI, 0) AS AVG6M_PRI,  " + 
					 " 			 ISNULL(LASTYEAR.LASTYEAR_PRI, 0) AS LASTYEAR_PRI   " + 
					 " FROM" + 
					 " (" + 
					 " 	SELECT distinct	KHSP.KHACHHANG_FK AS KHID, " + 
					 " 		case SP.DVKD_FK when 100000 then MAKT.ma else SP.MA END AS spMa,  " + 
					 " 		case SP.DVKD_FK when 100000 then 0 else 1 END AS MaKeToan_Or_MaLon,  " + 
					 " 		case SP.DVKD_FK when 100000 then SP.RONG when 100005 then SP.RONG else 0 end as DORONG," + 
					 " 		case SP.CHUNGLOAI_FK when 100040 then cast( isnull(SP.TRONGLUONG, 0) as varchar(10)) else isnull(SP.CHUANNEN, '') end as TRONGLUONG_CHUANNEN, " +
					 "		case SP.DVKD_FK when 100000 then 0 else case SP.CHUNGLOAI_FK when 100040 then isnull(DAULON, 0) else isnull(DUONGKINHTRONG, 0) end end as CoreOrConeBigger, " +
					 "		case SP.DVKD_FK when 100000 then 0 else isnull(SP.DAI, 0) end as Length, " +
					 "		case SP.DVKD_FK when 100000 then 0 else case SP.CHUNGLOAI_FK when 100040 then isnull(DAUNHO, 0) else isnull(DODAY, 0) end end as CoreThicknessORConesmaller, " + 
					 " 		ISNULL ( ( case SP.DVKD_FK when 100000 " + 
					 " 				   then ( MAKT.MA + ' - ' + SP.Ten + ' X ' + SP.MAU + ' X ' + cast(SP.RONG as varchar(10)) + ' X ' + cast(SP.DAI as varchar(10)) + ' X ' + cast(SP.DINHLUONG as varchar(10)) ) " + 
					 " 				when 100005 " + 
					 " 				   then ( SP.MA + ' - ' + SP.Ten + ' X ' + SP.MAU + ' X ' + cast(SP.RONG as varchar(10)) + ' X ' + cast(SP.DAI as varchar(10)) + ' X ' + cast(SP.DINHLUONG as varchar(10)) ) " + 
					 "				else " + 
					 " 					case SP.CHUNGLOAI_FK when 100040 then ( SP.MA + ' - ' + SP.Ten + ' X ' + cast( DAULON as varchar(10) ) + ' X ' + cast(SP.DAI as varchar(10)) + ' X ' + cast( DAUNHO as varchar(10) ) + ' X ' + cast(SP.TRONGLUONG as varchar(10))  ) " + 
					 " 					else ( SP.MA + ' - ' + SP.Ten + ' X ' + cast( DUONGKINHTRONG as varchar(10) ) + ' X ' + cast(SP.DAI as varchar(10)) + ' X ' + cast( DODAY as varchar(10) ) + ' X ' + SP.CHUANNEN  ) end		" +
					 " 				end ), '' ) as DienGiai, " + 
					 " 		0 as DONGIA, 0 as SoNgayBanHang, 0 as TANGTRUONG, 0 as TONDAU, 0 as DUKIENBAN, 0 as TONKHOANTOAN,  " + 
					 " 		0 as SANXUAT, 0 as TONCUOI, '" + this.ID + "' as DUBAO_FK, '" + nam + "' as NAM, '" + thang + "' as THANG " + 
					 " 	FROM ERP_SANPHAM SP  " + 
					 " 	INNER JOIN ERP_MAKETOAN MAKT on SP.MAKETOAN_FK = MAKT.PK_SEQ   " + 
					 " 	INNER JOIN ERP_KHACHHANG_SANPHAM KHSP ON SP.PK_SEQ = KHSP.SANPHAM_FK AND KHSP.CHON = 1   " + 
					 " 	INNER JOIN ERP_KHACHHANG KH ON KH.PK_SEQ = KHSP.KHACHHANG_FK   " +
					 " 	WHERE SP.TRANGTHAI = '1' AND SP.CONGTY_FK = '100005'  AND SP.DVKD_FK =  '" + this.DVKDID + "'" + 
					 " )" + 
					 " DB LEFT JOIN  " + 
					 " (  " + 
					 " 	SELECT	DDH.KHACHHANG_FK, " + 
					 " 				case SP.DVKD_FK when 100000 then SP.RONG when 100005 then SP.RONG else ISNULL(DUONGKINHTRONG, DAULON) end as DORONG," + 
					 " 				case SP.DVKD_FK when 100000 " + 
					 " 				   then ( MAKT.MA + ' - ' + SP.Ten + ' X ' + SP.MAU + ' X ' + cast(SP.RONG as varchar(10)) + ' X ' + cast(SP.DAI as varchar(10)) + ' X ' + cast(SP.DINHLUONG as varchar(10)) ) " + 
					 " 				when 100005 " + 
					 " 				   then ( SP.MA + ' - ' + SP.Ten + ' X ' + SP.MAU + ' X ' + cast(SP.RONG as varchar(10)) + ' X ' + cast(SP.DAI as varchar(10)) + ' X ' + cast(SP.DINHLUONG as varchar(10)) ) " + 
					 "				else " + 
					 " 					case SP.CHUNGLOAI_FK when 100040 then ( SP.MA + ' - ' + SP.Ten + ' X ' + cast( DAULON as varchar(10) ) + ' X ' + cast(SP.DAI as varchar(10)) + ' X ' + cast( DAUNHO as varchar(10) ) + ' X ' + cast(SP.TRONGLUONG as varchar(10))  ) " + 
					 " 					else ( SP.MA + ' - ' + SP.Ten + ' X ' + cast( DUONGKINHTRONG as varchar(10) ) + ' X ' + cast(SP.DAI as varchar(10)) + ' X ' + cast( DODAY as varchar(10) ) + ' X ' + SP.CHUANNEN  ) end		" +
					 " 				end as DienGiai, " + 
					 " 			SUM (CONVERT(FLOAT, SOLUONG) )/3 AS AVG3M_PRI    " + 
					 " 	FROM ERP_XUATKHO XK " + 
					 " 		INNER JOIN ERP_XUATKHO_SANPHAM XKSP ON XK.PK_SEQ = XKSP.XUATKHO_FK    " + 
					 " 		INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = XKSP.SANPHAM_FK  " + 
					 " 		INNER JOIN ERP_DONDATHANG DDH on XK.DONDATHANG_FK = DDH.PK_SEQ 	 " + 
					 " 		INNER JOIN ERP_MAKETOAN MAKT on SP.MAKETOAN_FK = MAKT.PK_SEQ" + 
					 " 	WHERE XK.TRANGTHAI = 1 AND XK.NGAYCHOT >= '" + ngaydsd3M + "' AND XK.NGAYCHOT <= '" + ngaytd + "' and SP.DVKD_FK = '" + this.DVKDID + "'  " + 
					 " 	GROUP BY DDH.KHACHHANG_FK, case SP.DVKD_FK when 100000 then SP.RONG when 100005 then SP.RONG else ISNULL(DUONGKINHTRONG, DAULON) end," + 
					 " 				case SP.DVKD_FK when 100000 " + 
					 " 				   then ( MAKT.MA + ' - ' + SP.Ten + ' X ' + SP.MAU + ' X ' + cast(SP.RONG as varchar(10)) + ' X ' + cast(SP.DAI as varchar(10)) + ' X ' + cast(SP.DINHLUONG as varchar(10)) ) " + 
					 " 				when 100005 " + 
					 " 				   then ( SP.MA + ' - ' + SP.Ten + ' X ' + SP.MAU + ' X ' + cast(SP.RONG as varchar(10)) + ' X ' + cast(SP.DAI as varchar(10)) + ' X ' + cast(SP.DINHLUONG as varchar(10)) ) " + 
					 "				else " + 
					 " 					case SP.CHUNGLOAI_FK when 100040 then ( SP.MA + ' - ' + SP.Ten + ' X ' + cast( DAULON as varchar(10) ) + ' X ' + cast(SP.DAI as varchar(10)) + ' X ' + cast( DAUNHO as varchar(10) ) + ' X ' + cast(SP.TRONGLUONG as varchar(10))  ) " + 
					 " 					else ( SP.MA + ' - ' + SP.Ten + ' X ' + cast( DUONGKINHTRONG as varchar(10) ) + ' X ' + cast(SP.DAI as varchar(10)) + ' X ' + cast( DODAY as varchar(10) ) + ' X ' + SP.CHUANNEN  ) end		" +
					 " 				end  " +
					 " ) " + 
					 " AVG3M ON AVG3M.DienGiai = DB.DienGiai AND AVG3M.KHACHHANG_FK = DB.KHID AND AVG3M.DORONG = DB.DORONG  " + 
					 " LEFT JOIN  " + 
					 " (  " + 
					 " 	SELECT	DDH.KHACHHANG_FK, " + 
					 " 			case SP.DVKD_FK when 100000 then SP.RONG when 100005 then SP.RONG else ISNULL(DUONGKINHTRONG, DAULON) end as DORONG," + 
					 " 				case SP.DVKD_FK when 100000 " + 
					 " 				   then ( MAKT.MA + ' - ' + SP.Ten + ' X ' + SP.MAU + ' X ' + cast(SP.RONG as varchar(10)) + ' X ' + cast(SP.DAI as varchar(10)) + ' X ' + cast(SP.DINHLUONG as varchar(10)) ) " + 
					 " 				when 100005 " + 
					 " 				   then ( SP.MA + ' - ' + SP.Ten + ' X ' + SP.MAU + ' X ' + cast(SP.RONG as varchar(10)) + ' X ' + cast(SP.DAI as varchar(10)) + ' X ' + cast(SP.DINHLUONG as varchar(10)) ) " + 
					 "				else " + 
					 " 					case SP.CHUNGLOAI_FK when 100040 then ( SP.MA + ' - ' + SP.Ten + ' X ' + cast( DAULON as varchar(10) ) + ' X ' + cast(SP.DAI as varchar(10)) + ' X ' + cast( DAUNHO as varchar(10) ) + ' X ' + cast(SP.TRONGLUONG as varchar(10))  ) " + 
					 " 					else ( SP.MA + ' - ' + SP.Ten + ' X ' + cast( DUONGKINHTRONG as varchar(10) ) + ' X ' + cast(SP.DAI as varchar(10)) + ' X ' + cast( DODAY as varchar(10) ) + ' X ' + SP.CHUANNEN  ) end		" +
					 " 				end as DienGiai, " + 
					 " 			SUM (CONVERT(FLOAT, SOLUONG) )/6 AS AVG6M_PRI    " + 
					 " 	FROM ERP_XUATKHO XK " + 
					 " 		INNER JOIN ERP_XUATKHO_SANPHAM XKSP ON XK.PK_SEQ = XKSP.XUATKHO_FK    " + 
					 " 		INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = XKSP.SANPHAM_FK  " + 
					 " 		INNER JOIN ERP_DONDATHANG DDH on XK.DONDATHANG_FK = DDH.PK_SEQ 	 " + 
					 " 		INNER JOIN ERP_MAKETOAN MAKT on SP.MAKETOAN_FK = MAKT.PK_SEQ" + 
					 " 	WHERE XK.TRANGTHAI = 1 AND XK.NGAYCHOT >= '" + ngaydsd6M + "' AND XK.NGAYCHOT <= '2013-12-05' and SP.DVKD_FK = '" + this.DVKDID + "'  " + 
					 " 	GROUP BY DDH.KHACHHANG_FK, case SP.DVKD_FK when 100000 then SP.RONG when 100005 then SP.RONG else ISNULL(DUONGKINHTRONG, DAULON) end," + 
					 " 				case SP.DVKD_FK when 100000 " + 
					 " 				   then ( MAKT.MA + ' - ' + SP.Ten + ' X ' + SP.MAU + ' X ' + cast(SP.RONG as varchar(10)) + ' X ' + cast(SP.DAI as varchar(10)) + ' X ' + cast(SP.DINHLUONG as varchar(10)) ) " + 
					 " 				when 100005 " + 
					 " 				   then ( SP.MA + ' - ' + SP.Ten + ' X ' + SP.MAU + ' X ' + cast(SP.RONG as varchar(10)) + ' X ' + cast(SP.DAI as varchar(10)) + ' X ' + cast(SP.DINHLUONG as varchar(10)) ) " + 
					 "				else " + 
					 " 					case SP.CHUNGLOAI_FK when 100040 then ( SP.MA + ' - ' + SP.Ten + ' X ' + cast( DAULON as varchar(10) ) + ' X ' + cast(SP.DAI as varchar(10)) + ' X ' + cast( DAUNHO as varchar(10) ) + ' X ' + cast(SP.TRONGLUONG as varchar(10))  ) " + 
					 " 					else ( SP.MA + ' - ' + SP.Ten + ' X ' + cast( DUONGKINHTRONG as varchar(10) ) + ' X ' + cast(SP.DAI as varchar(10)) + ' X ' + cast( DODAY as varchar(10) ) + ' X ' + SP.CHUANNEN  ) end		" +
					 " 				end  " + 
					 " )  " + 
					 " AVG6M ON AVG6M.DienGiai = DB.DienGiai AND AVG6M.KHACHHANG_FK = DB.KHID AND AVG6M.DORONG = DB.DORONG" + 
					 " LEFT JOIN   " + 
					 " (  " + 
					 " 	SELECT	DDH.KHACHHANG_FK, " + 
					 " 				case SP.DVKD_FK when 100000 then SP.RONG when 100005 then SP.RONG else ISNULL(DUONGKINHTRONG, DAULON) end as DORONG," + 
					 " 				case SP.DVKD_FK when 100000 " + 
					 " 				   then ( MAKT.MA + ' - ' + SP.Ten + ' X ' + SP.MAU + ' X ' + cast(SP.RONG as varchar(10)) + ' X ' + cast(SP.DAI as varchar(10)) + ' X ' + cast(SP.DINHLUONG as varchar(10)) ) " + 
					 " 				when 100005 " + 
					 " 				   then ( SP.MA + ' - ' + SP.Ten + ' X ' + SP.MAU + ' X ' + cast(SP.RONG as varchar(10)) + ' X ' + cast(SP.DAI as varchar(10)) + ' X ' + cast(SP.DINHLUONG as varchar(10)) ) " + 
					 "				else " + 
					 " 					case SP.CHUNGLOAI_FK when 100040 then ( SP.MA + ' - ' + SP.Ten + ' X ' + cast( DAULON as varchar(10) ) + ' X ' + cast(SP.DAI as varchar(10)) + ' X ' + cast( DAUNHO as varchar(10) ) + ' X ' + cast(SP.TRONGLUONG as varchar(10))  ) " + 
					 " 					else ( SP.MA + ' - ' + SP.Ten + ' X ' + cast( DUONGKINHTRONG as varchar(10) ) + ' X ' + cast(SP.DAI as varchar(10)) + ' X ' + cast( DODAY as varchar(10) ) + ' X ' + SP.CHUANNEN  ) end		" +
					 " 				end as DienGiai, " +
					 " 			SUM (CONVERT(FLOAT, SOLUONG) )/12 AS LASTYEAR_PRI    " + 
					 " 	FROM ERP_XUATKHO XK " + 
					 " 		INNER JOIN ERP_XUATKHO_SANPHAM XKSP ON XK.PK_SEQ = XKSP.XUATKHO_FK    " + 
					 " 		INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = XKSP.SANPHAM_FK  " + 
					 " 		INNER JOIN ERP_DONDATHANG DDH on XK.DONDATHANG_FK = DDH.PK_SEQ 	 " + 
					 " 		INNER JOIN ERP_MAKETOAN MAKT on SP.MAKETOAN_FK = MAKT.PK_SEQ" + 
					 " 	WHERE XK.TRANGTHAI = 1 AND SUBSTRING(XK.NGAYCHOT, 1, 7)  = '" + lastmonth + "' and SP.DVKD_FK = '" + this.DVKDID + "'  " + 
					 " 	GROUP BY DDH.KHACHHANG_FK, case SP.DVKD_FK when 100000 then SP.RONG when 100005 then SP.RONG else ISNULL(DUONGKINHTRONG, DAULON) end," + 
					 " 				case SP.DVKD_FK when 100000 " + 
					 " 				   then ( MAKT.MA + ' - ' + SP.Ten + ' X ' + SP.MAU + ' X ' + cast(SP.RONG as varchar(10)) + ' X ' + cast(SP.DAI as varchar(10)) + ' X ' + cast(SP.DINHLUONG as varchar(10)) ) " + 
					 " 				when 100005 " + 
					 " 				   then ( SP.MA + ' - ' + SP.Ten + ' X ' + SP.MAU + ' X ' + cast(SP.RONG as varchar(10)) + ' X ' + cast(SP.DAI as varchar(10)) + ' X ' + cast(SP.DINHLUONG as varchar(10)) ) " + 
					 "				else " + 
					 " 					case SP.CHUNGLOAI_FK when 100040 then ( SP.MA + ' - ' + SP.Ten + ' X ' + cast( DAULON as varchar(10) ) + ' X ' + cast(SP.DAI as varchar(10)) + ' X ' + cast( DAUNHO as varchar(10) ) + ' X ' + cast(SP.TRONGLUONG as varchar(10))  ) " + 
					 " 					else ( SP.MA + ' - ' + SP.Ten + ' X ' + cast( DUONGKINHTRONG as varchar(10) ) + ' X ' + cast(SP.DAI as varchar(10)) + ' X ' + cast( DODAY as varchar(10) ) + ' X ' + SP.CHUANNEN  ) end		" +
					 " 				end  " +
					 " ) " + 
					 " LASTYEAR ON LASTYEAR.DienGiai = DB.DienGiai  AND LASTYEAR.KHACHHANG_FK = DB.KHID  AND LASTYEAR.DORONG = DB.DORONG";
			
				String tmp = "INSERT INTO ERP_DUBAOSANPHAM (KHACHHANG_FK, MASANPHAM, MaKeToan_Or_MaLon, DORONG, TRONGLUONG_CHUANNEN, CoreOrConeBigger, Length, CoreThicknessORConesmaller, DIENGIAI, DONGIA, SONGAYBANHANG, TANGTRUONG, TONDAU, DUKIENBAN, TONKHOANTOAN," +
							 "SANXUAT, TONCUOI, DUBAO_FK, NAM, THANG, AVG3M, AVG6M, LASTYEAR) " + query;
				
				//System.out.println("___Du bao San Pham: " + query);
				if(!this.db.update(tmp))
				{
					System.out.println("ERROR:" + tmp);
					this.MSG = "Không thể tạo ERP_DUBAOSANPHAMMTO: " + tmp;
					db.getConnection().rollback();
					return false;
				}
				
				
				if(thang == 12) {
					thang = 1;
					nam = nam + 1;
				}else{
					thang = thang + 1;
				}
		}
	
		db.getConnection().commit();
		db.getConnection().setAutoCommit(true);
		return true;
	} 
	catch (Exception e) 
	{
		db.update("rollback");
		this.MSG="Lỗi kỹ thuật ( "+e.getMessage()+" )";
		return false;
	}
	}
	
	public void createRsMTS() 
	{		
	this.rsChungloai=this.db.get("select PK_SEQ,TEN from CHUNGLOAI WHERE TRANGTHAI = '1' AND CONGTY_FK = " + this.CTYID + " AND PK_SEQ IN (SELECT DISTINCT CHUNGLOAI_FK FROM ERP_SANPHAM WHERE LOAISANPHAM_FK IN (100041, 100042, 100043)) AND TRANGTHAI = 1 ");
		
	String query = "select pk_seq, ma, ten from erp_sanpham where trangthai = '1' AND CONGTY_FK = " + this.CTYID ;
	
	if(CHUNGLOAI.length()>0)
		query = "select pk_seq, ma, ten from erp_sanpham where trangthai = '1' and chungloai_fk='"+this.CHUNGLOAI+"'";
	
	if(CHUNGLOAI.length()>0 && NHANHANG.length()>0)
		query = "select pk_seq, ma, ten from erp_sanpham where trangthai = '1' and chungloai_fk='"+this.CHUNGLOAI+"' and NHANHANG_FK='"+this.NHANHANG+"'";
		
	this.rsSanpham = db.get(query);
	
	}
	
	public boolean Update(HttpServletRequest request)  throws ServletException, IOException
	{
		
		request.setCharacterEncoding("UTF-8");
		System.out.println("Vao update");
		String ngaytao = this.getDateTime();
		//Utility util = new Utility();
		
		try 
		{
//			db.getConnection().setAutoCommit(false);
				
			if(this.HIEULUC.equals("1")){
				this.db.update("UPDATE ERP_DUBAO set HIEULUC = '0' WHERE CONGTY_FK = '" + this.CTYID + "' AND MOHINH = '0' " +
							   "AND SUBSTRING(NGAYDUBAO, 1, 7) = (SELECT SUBSTRING(NGAYDUBAO, 1, 7) FROM ERP_DUBAO WHERE PK_SEQ = " + this.ID + ") " +
							   "AND PK_SEQ <> " + this.ID + " "
							   );
				
			}
			
			String query =  "UPDATE ERP_DUBAO set DIENGIAI=N'"+this.DIENGIAI+"', NGAYDUBAO = '" + this.NGAYDUBAO + "', " +
							"NGUOISUA='" + this.USERID +"',NGAYSUA='"+ngaytao+"', HIEULUC = '" + this.HIEULUC + "' " +
							"WHERE PK_SEQ = " + this.ID;
			
			//	System.out.println("Update 1: " + query);
		
			if(!db.update(query))
			{
				this.MSG = "Không thể cập nhật dự báo kinh doanh : " + query;
				db.getConnection().rollback();
				return false;
			}
		
			query = "SELECT NGAYDUBAO FROM ERP_DUBAO WHERE PK_SEQ = " + this.ID;
			ResultSet rs = db.get(query);
			if(rs != null){
				rs.next();
				this.NGAYDUBAO = rs.getString("NGAYDUBAO");
				rs.close();
			}
			
			String nam = this.NGAYDUBAO.substring(0, 4);
			String thang = this.NGAYDUBAO.substring(5, 7);
			
			if(Integer.parseInt(this.getDateTime().substring(0, 4)) > Integer.parseInt(nam) 
			|| (Integer.parseInt(this.getDateTime().substring(0, 4)) == Integer.parseInt(nam) & Integer.parseInt(this.getDateTime().substring(5, 7)) > Integer.parseInt(thang)))
			{
				nam = this.getDateTime().substring(0, 4);
				thang = this.getDateTime().substring(5, 7);
			}
			
			if(thang.equals("12")){
				thang = "1" ;
				nam = "" + (Integer.parseInt(nam) + 1);
			}else{
				thang = "" + (Integer.parseInt(thang) + 1);
			}
			
			if(this.HIEULUC.equals("1"))
			{		
				//CAP NHAT
				this.CapnhatYeucauCungUng(thang, nam);
				
				//Lưu lại BOM sẽ sử dụng, trong trường hợp nhiều BOM
				this.CapNhatBOM();
				
				this.TaoYeuCauSanXuat(thang, nam);
				
				this.TaoLenhSanXuatDK_TP(thang, nam);
				this.TaoLenhSanXuatDK_TP_Tuan(thang, nam);
				this.TaoLenhSanXuatDK_BTP(thang, nam);
				
				this.TaoSoLo();
				
				this.TaoYeuCauNguyenLieu(thang, nam);
				
				this.TaoYCBoSungNguyenLieu(thang, nam);
				this.TaoDonMuaNguyenLieuDukien(thang, nam);
			}
			else  //Du bao ngung, huy cac de nghi san xuat, de nghi mua nguyen lieu
			{
				this.NgungHieuLuc();
			}
			
//			db.getConnection().commit();
//			db.getConnection().setAutoCommit(true);
			return true;
		}
		catch (Exception e) 
		{
			db.update("rollback");
			this.MSG="Lỗi kỹ thuật ( "+e.getMessage()+" )";
			e.printStackTrace();
			return false;
		}
	}
	
	private void CapNhatBOM()
	{
		String query = "delete ERP_DANHMUCVATTU_KEHOACH where dubao_fk = '" + this.ID + "' ";
		db.update(query);
		
		/*query = " insert ERP_DANHMUCVATTU_KEHOACH( dubao_fk, danhmucvt_fk, sanpham_fk, soluongCHUAN, dvdl_fk, hieulucden, quycach )  "+
				" select '" + this.ID + "', dm.pk_seq, dm.SANPHAM_FK, dm.soluongCHUAN, dm.dvdl_fk, dm.hieulucden, isnull( dbo.LayQuyCach2( dm.sanpham_fk, null, dm.dvdl_fk ), 1 ) as quycach "+
				" from ERP_DANHMUCVATTU dm inner join ERP_SANPHAM sp on dm.SANPHAM_FK = sp.pk_seq inner join "+
				" ( "+
				" 	select a.SANPHAM_FK, max(b.HIEULUCDEN) as HIEULUCDEN "+
				" 	from ERP_YEUCAUCUNGUNG a inner join ERP_DANHMUCVATTU b on a.sanpham_fk = b.pk_seq  "+
				" 	where a.dubao_fk = '" + this.ID + "' and b.trangthai = '1' and a.yeucau != 0 "+
				" 	group by a.SANPHAM_FK "+
				" ) "+
				" BOM on dm.sanpham_fk = BOM.sanpham_fk and dm.HIEULUCDEN = BOM.HIEULUCDEN ";*/
		
		//Cùng ngày hết hạn, thì lấy ngày tạo sau
		query =  " insert ERP_DANHMUCVATTU_KEHOACH( dubao_fk, danhmucvt_fk, sanpham_fk, soluongCHUAN, dvdl_fk, hieulucden, quycach )  "+
				 " select '" + this.ID + "', dm.pk_seq, dm.SANPHAM_FK, dm.soluongCHUAN, dm.dvdl_fk, dm.hieulucden, isnull( dbo.LayQuyCach2( dm.sanpham_fk, null, dm.dvdl_fk ), 1 ) as quycach  "+
				 " from ERP_DANHMUCVATTU dm inner join ERP_SANPHAM sp on dm.SANPHAM_FK = sp.pk_seq inner join  "+
				 " (  "+
				 " 	select dm.SANPHAM_FK, dm.HIEULUCDEN, max(dm.NGAYTAO) as NGAYTAO "+
				 " 	from ERP_DANHMUCVATTU dm inner join  "+
				 " 	(  "+
				 " 		select a.SANPHAM_FK, max(b.HIEULUCDEN) as HIEULUCDEN  "+
				 " 		from ERP_YEUCAUCUNGUNG a inner join ERP_DANHMUCVATTU b on a.sanpham_fk = b.pk_seq   "+
				 " 		where a.dubao_fk = '" + this.ID + "' and b.trangthai = '1' and a.yeucau != 0		 "+
				 " 		group by a.SANPHAM_FK  "+
				 " 	)  "+
				 " 	BOM on dm.sanpham_fk = BOM.sanpham_fk and dm.HIEULUCDEN = BOM.HIEULUCDEN  "+
				 " 	group by dm.SANPHAM_FK, dm.HIEULUCDEN "+
				 " )  "+
				 " BOM on dm.sanpham_fk = BOM.sanpham_fk and dm.HIEULUCDEN = BOM.HIEULUCDEN  and dm.NGAYTAO = BOM.ngaytao ";
		
		db.update(query);
		
	}
	
	private void NgungHieuLuc() 
			{
			String[] ngaydubao = this.NGAYDUBAO.split("-"); //chua nam, thang, ngay
			int thang;
			int nam;
			
			if(ngaydubao[1].equals("12")){
				ngaydubao[1] = "01";									  // Thang du bao
				ngaydubao[0] = "" + (Integer.valueOf(ngaydubao[0]) + 1) ; // Nam du bao
			}else{
				ngaydubao[1] = "" + (Integer.valueOf(ngaydubao[1]) + 1) ; // Thang du bao
			}
			int	sptt_dubao=0;
			nam  	= Integer.parseInt(ngaydubao[0]);
			try{
				sptt_dubao=Integer.valueOf(this.SOTHANGDUBAO ).intValue();
			
			}catch(Exception err){
				 
			}
			for(int j = 0; j < sptt_dubao ; j++)
			{
				thang = Integer.parseInt(ngaydubao[1]) + j;	
				
				String query = "delete ERP_LENHSANXUATDUKIEN " +
							   "WHERE SANPHAM_FK in ( select sanpham_fk from ERP_YEUCAUCUNGUNG where dubao_fk = '" + this.ID + "'  ) " +
							   "AND THANG = " + thang + " AND NAM = " + nam;
				
				if(!db.update(query))
				{
					System.out.println("222.Khong the cap nhat ERP_LENHSANXUATDUKIEN: " + query);
				}
				
				query = "delete ERP_MUANGUYENLIEUDUKIEN " + 
						"WHERE SANPHAM_FK in ( select sanpham_fk from ERP_YEUCAUCUNGUNG where dubao_fk = '" + this.ID + "'  ) " +
						"AND THANG = " + thang + " AND NAM = " + nam;
				
				if(!db.update(query))
				{
					System.out.println("333.Khong the cap nhat ERP_LENHSANXUATDUKIEN: " + query);
				}	
			}
			
			String query = "delete ERP_YEUCAUCUNGUNG where dubao_fk = '" + this.ID + "'";
			if(!db.update(query))
			{
				System.out.println("444.Loi: " + query);
			}
	
		}
	
		private void CapnhatYeucauCungUng(String thang, String nam)
		{		
			String query, khoId = "";
			String t0 = "", t1 = "", t2 = "", t3 = "", t4 = "";
			String n0 = "", n1 = "", n2 = "", n3 = "", n4 = "";
			t1 = thang;
			n1 = nam;
			
			if(t1.equals("1")){
				t0 = "12";
				n0 = "" + (Integer.parseInt(n1) - 1);
			}else{
				t0 = "" + (Integer.parseInt(t1) - 1);
				n0 = n1;
			}
			
			if(t1.equals("12")){
				t2 = "1";
				n2 = "" + (Integer.parseInt(n1) + 1);
			}else{
				t2 = "" + (Integer.parseInt(t1) + 1);
				n2 = n1;
			}
			
			if(t2.equals("12")){
				t3 = "1";
				n3 = "" + (Integer.parseInt(n2) + 1);
			}else{
				t3 = "" + (Integer.parseInt(t2) + 1);
				n3 = n2;
			}
			
			if(t3.equals("12")){
				t4 = "1";
				n4 = "" + (Integer.parseInt(n3) + 1);
			}else{
				t4 = "" + (Integer.parseInt(t3) + 1);
				n4 = n3;
			}
			
/*			query = "DELETE ERP_YEUCAUCUNGUNG " +
					"WHERE KHOTT_FK IN (100023, 100041, 100034) "  +  //DUBAO_FK = " + this.ID + " AND
					"AND (" +
					"(THANG = '" + t0 + "' AND NAM = '" + n0 + "') " +
					"OR (THANG = '" + t1 + "' AND NAM = '" + n1 + "') " +
					"OR (THANG = '" + t2 + "' AND NAM = '" + n2 + "') " +
					"OR (THANG = '" + t3 + "' AND NAM = '" + n3 + "')" +
					"OR (THANG = '" + t4 + "' AND NAM = '" + n4 + "')" +
					")";*/
			
			query = "DELETE ERP_YEUCAUCUNGUNG WHERE DUBAO_FK = " + this.ID + " ";
			this.db.update(query);
			
			double pt = 1;
			/*query = "SELECT CONVERT(FLOAT, SUBSTRING('" + this.getDateTime() + "', 9, 2))/(SELECT datediff(dd, " + this.getDateTime() + ", dateadd(mm, 1, " + this.getDateTime() + "))) as pt";
			System.out.println(query);
			ResultSet rs = this.db.get(query);
			try{
				rs.next();
				pt = 1.0 - rs.getDouble("pt");
				rs.close();
			}catch(java.sql.SQLException e){}*/
			
			String khoIdTT = "";

			khoId = "100023, 100041, 100034";
			khoIdTT = "100021, 100022";
	
			
			query = "INSERT INTO ERP_YEUCAUCUNGUNG(SANPHAM_FK, THANG, NAM, YEUCAU, DUBAO_FK) \n " +
					"SELECT  DISTINCT KHOTT_SP.SANPHAM_FK AS SPID, " + t0 + " AS THANG, " + n0 + " AS NAM, \n "  +
						
					"ISNULL(CASE WHEN (ISNULL(THANG_0.DUKIENBAN, 0)*" + pt + " + SUM(ISNULL(KHOTT_SP.TONANTOAN, 0)) - SUM(ISNULL(KHOTT_SP.AVAILABLE, 0)) < 0) THEN 0  \n " +
					"ELSE (ISNULL(THANG_0.DUKIENBAN, 0)*" + pt + " + SUM(ISNULL(KHOTT_SP.TONANTOAN, 0)) - SUM(ISNULL(KHOTT_SP.AVAILABLE, 0))) END, 0) AS YCCU_THANG, " + this.ID + " AS DBID  \n " +
					
					"FROM ERP_KHOTT_SANPHAM KHOTT_SP  \n " +
					"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = KHOTT_SP.SANPHAM_FK  \n " +
					"LEFT JOIN  \n " +
					"(  \n " +
					"	SELECT DBSP.SANPHAM_FK, SUM(DBSP.DUKIENBAN) AS DUKIENBAN " +
					"	FROM ERP_DUBAOSANPHAMMTS DBSP " +
					"	INNER JOIN ERP_DUBAO DB ON DB.PK_SEQ = DBSP.DUBAO_FK " +
					"	WHERE THANG = " + t0 + " AND NAM = " + n0 + " AND DB.HIEULUC = 1 " +
					"   AND DB.PK_SEQ = (SELECT TOP 1 PK_SEQ FROM ERP_DUBAO WHERE HIEULUC = 1 ORDER BY NGAYDUBAO DESC)  \n " +
					"   AND DBSP.KHO_FK IN (" + khoId + ") \n " +
					"   GROUP BY DBSP.SANPHAM_FK \n " +
					")THANG_0 ON THANG_0.SANPHAM_FK = KHOTT_SP.SANPHAM_FK  \n " +
					"WHERE KHOTT_SP.KHOTT_FK IN (" + khoIdTT + ") AND SP.TRANGTHAI = 1   \n " +
					"AND SP.LOAISANPHAM_FK IN (100002, 100003)   \n " +
					"GROUP BY KHOTT_SP.SANPHAM_FK, ISNULL(THANG_0.DUKIENBAN, 0)  \n " +

					"UNION ALL \n " +

					"SELECT  DISTINCT KHOTT_SP.SANPHAM_FK AS SPID, " + t1 + " AS THANG, " + n1 + " AS NAM, \n "  +
										
					"ISNULL(CASE WHEN (ISNULL(THANG_0.DUKIENBAN, 0)*" + pt + " + SUM(ISNULL(KHOTT_SP.TONANTOAN, 0)) - SUM(ISNULL(KHOTT_SP.AVAILABLE, 0)) < 0)   \n " +
					"THEN   \n " +
					"	CASE WHEN (ISNULL(THANG_1.DUKIENBAN, 0) + ISNULL(THANG_0.DUKIENBAN, 0)*" + pt + " + SUM(ISNULL(KHOTT_SP.TONANTOAN, 0)) - SUM(ISNULL(KHOTT_SP.AVAILABLE, 0)) < 0)  \n " + 
					"	THEN 0 " +
					"	ELSE (ISNULL(THANG_1.DUKIENBAN, 0) + ISNULL(THANG_0.DUKIENBAN, 0)*" + pt + " + SUM(ISNULL(KHOTT_SP.TONANTOAN, 0)) - SUM(ISNULL(KHOTT_SP.AVAILABLE, 0)))  \n " +
					"	END  " +
					"ELSE ISNULL(THANG_1.DUKIENBAN, 0) END, 0) AS YCCU_THANG, " + this.ID + " AS DBID  \n " +

					"FROM ERP_KHOTT_SANPHAM KHOTT_SP  \n " +
					"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = KHOTT_SP.SANPHAM_FK  \n " +
					"LEFT JOIN  \n " +
					"(  \n " +
					"	SELECT DBSP.SANPHAM_FK, SUM(DBSP.DUKIENBAN) AS DUKIENBAN " +
					"	FROM ERP_DUBAOSANPHAMMTS DBSP " +
					"	INNER JOIN ERP_DUBAO DB ON DB.PK_SEQ = DBSP.DUBAO_FK " +
					"	WHERE THANG = " + t0 + " AND NAM = " + n0 + " AND DB.HIEULUC = 1 " +
					"   AND DB.PK_SEQ = (SELECT TOP 1 PK_SEQ FROM ERP_DUBAO WHERE HIEULUC = 1 ORDER BY NGAYDUBAO DESC)  \n " +
					"   AND DBSP.KHO_FK IN (" + khoId + ") \n " +
					"   GROUP BY DBSP.SANPHAM_FK \n " +
					")THANG_0 ON THANG_0.SANPHAM_FK = KHOTT_SP.SANPHAM_FK  \n " +
					"LEFT JOIN  \n " +
					"(  \n " +
					"	SELECT DBSP.SANPHAM_FK, SUM(DBSP.DUKIENBAN) AS DUKIENBAN " +
					"	FROM ERP_DUBAOSANPHAMMTS DBSP " +
					"	INNER JOIN ERP_DUBAO DB ON DB.PK_SEQ = DBSP.DUBAO_FK " +
					"	WHERE THANG = " + t1 + " AND NAM = " + n1 + " AND DB.HIEULUC = 1 " +
					"   AND DB.PK_SEQ = (SELECT TOP 1 PK_SEQ FROM ERP_DUBAO WHERE HIEULUC = 1 ORDER BY NGAYDUBAO DESC)  \n " +
					"   AND DBSP.KHO_FK IN (" + khoId + ") \n " +
					"   GROUP BY DBSP.SANPHAM_FK \n " +
					")THANG_1 ON THANG_1.SANPHAM_FK = KHOTT_SP.SANPHAM_FK  \n " +
					"WHERE KHOTT_SP.KHOTT_FK IN (" + khoIdTT + ") AND SP.TRANGTHAI = 1   \n " +
					"AND SP.LOAISANPHAM_FK IN (100002, 100003)   \n " +
					"GROUP BY KHOTT_SP.SANPHAM_FK, ISNULL(THANG_0.DUKIENBAN, 0),  \n " +
					"ISNULL(THANG_1.DUKIENBAN, 0)  \n " +

					"UNION ALL \n " +

					"SELECT  DISTINCT KHOTT_SP.SANPHAM_FK AS SPID, " + t2 + " AS THANG, " + n2 + " AS NAM, \n "  +
					
					"ISNULL(CASE WHEN (ISNULL(THANG_1.DUKIENBAN, 0) + ISNULL(THANG_0.DUKIENBAN, 0)*" + pt + " + SUM(ISNULL(KHOTT_SP.TONANTOAN, 0)) - SUM(ISNULL(KHOTT_SP.AVAILABLE, 0)) < 0)   \n " +
					"THEN   \n " +
					"	CASE WHEN (ISNULL(THANG_2.DUKIENBAN, 0) + ISNULL(THANG_1.DUKIENBAN, 0) + ISNULL(THANG_0.DUKIENBAN, 0)*" + pt + " + SUM(ISNULL(KHOTT_SP.TONANTOAN, 0)) - SUM(ISNULL(KHOTT_SP.AVAILABLE, 0)) < 0)  \n " + 
					"	THEN 0 " +
					"	ELSE (ISNULL(THANG_2.DUKIENBAN, 0) + ISNULL(THANG_1.DUKIENBAN, 0) + ISNULL(THANG_0.DUKIENBAN, 0)*" + pt + " + SUM(ISNULL(KHOTT_SP.TONANTOAN, 0)) - SUM(ISNULL(KHOTT_SP.AVAILABLE, 0)))  \n " +
					"	END  " +
					"ELSE ISNULL(THANG_2.DUKIENBAN, 0) END, 0) AS YCCU_THANG, " + this.ID + " AS DBID  \n " +

					"FROM ERP_KHOTT_SANPHAM KHOTT_SP  \n " +
					"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = KHOTT_SP.SANPHAM_FK  \n " +
					"LEFT JOIN  \n " +
					"(  \n " +
					"	SELECT DBSP.SANPHAM_FK, SUM(DBSP.DUKIENBAN) AS DUKIENBAN " +
					"	FROM ERP_DUBAOSANPHAMMTS DBSP " +
					"	INNER JOIN ERP_DUBAO DB ON DB.PK_SEQ = DBSP.DUBAO_FK " +
					"	WHERE THANG = " + t0 + " AND NAM = " + n0 + " AND DB.HIEULUC = 1 " +
					"   AND DB.PK_SEQ = (SELECT TOP 1 PK_SEQ FROM ERP_DUBAO WHERE HIEULUC = 1 ORDER BY NGAYDUBAO DESC)  \n " +
					"   AND DBSP.KHO_FK IN (" + khoId + ") \n " +
					"   GROUP BY DBSP.SANPHAM_FK \n " +
					")THANG_0 ON THANG_0.SANPHAM_FK = KHOTT_SP.SANPHAM_FK  \n " +
					"LEFT JOIN  \n " +
					"(  \n " +
					"	SELECT DBSP.SANPHAM_FK, SUM(DBSP.DUKIENBAN) AS DUKIENBAN " +
					"	FROM ERP_DUBAOSANPHAMMTS DBSP " +
					"	INNER JOIN ERP_DUBAO DB ON DB.PK_SEQ = DBSP.DUBAO_FK " +
					"	WHERE THANG = " + t1 + " AND NAM = " + n1 + " AND DB.HIEULUC = 1 " +
					"   AND DB.PK_SEQ = (SELECT TOP 1 PK_SEQ FROM ERP_DUBAO WHERE HIEULUC = 1 ORDER BY NGAYDUBAO DESC)  \n " +
					"   AND DBSP.KHO_FK IN (" + khoId + ") \n " +
					"   GROUP BY DBSP.SANPHAM_FK \n " +
					")THANG_1 ON THANG_1.SANPHAM_FK = KHOTT_SP.SANPHAM_FK  \n " +
					"LEFT JOIN   \n " +
					"(   \n " +
					"	SELECT DBSP.SANPHAM_FK, SUM(DBSP.DUKIENBAN) AS DUKIENBAN " +
					"	FROM ERP_DUBAOSANPHAMMTS DBSP " +
					"	INNER JOIN ERP_DUBAO DB ON DB.PK_SEQ = DBSP.DUBAO_FK " +
					"	WHERE THANG = " + t2 + " AND NAM = " + n2 + " AND DB.HIEULUC = 1 " +
					"   AND DB.PK_SEQ = (SELECT TOP 1 PK_SEQ FROM ERP_DUBAO WHERE HIEULUC = 1 ORDER BY NGAYDUBAO DESC)  \n " +
					"   AND DBSP.KHO_FK IN (" + khoId + ") \n " +
					"   GROUP BY DBSP.SANPHAM_FK \n " +
					")THANG_2 ON THANG_2.SANPHAM_FK = KHOTT_SP.SANPHAM_FK   \n " +
					"WHERE KHOTT_SP.KHOTT_FK IN (" + khoIdTT + ") AND SP.TRANGTHAI = 1   \n " +
					"AND SP.LOAISANPHAM_FK IN (100002, 100003)   \n " +
					"GROUP BY KHOTT_SP.SANPHAM_FK, ISNULL(THANG_0.DUKIENBAN, 0),  \n " +
					"ISNULL(THANG_1.DUKIENBAN, 0), ISNULL(THANG_2.DUKIENBAN, 0)  \n " +

					"UNION ALL \n " +

					"SELECT  DISTINCT KHOTT_SP.SANPHAM_FK AS SPID, " + t3 + " AS THANG, " + n3 + " AS NAM, \n "  +
						
					"ISNULL( \n " +
					"		CASE WHEN (ISNULL(THANG_2.DUKIENBAN, 0) + ISNULL(THANG_1.DUKIENBAN, 0) + ISNULL(THANG_0.DUKIENBAN, 0)*" + pt + " + SUM(ISNULL(KHOTT_SP.TONANTOAN, 0)) - SUM(ISNULL(KHOTT_SP.AVAILABLE, 0)) < 0)   \n " +
					"		THEN  \n " +
					"			CASE WHEN (ISNULL(THANG_3.DUKIENBAN, 0) + ISNULL(THANG_2.DUKIENBAN, 0) + ISNULL(THANG_1.DUKIENBAN, 0) + ISNULL(THANG_0.DUKIENBAN, 0)*" + pt + " + SUM(ISNULL(KHOTT_SP.TONANTOAN, 0)) - SUM(ISNULL(KHOTT_SP.AVAILABLE, 0)) < 0)  \n " + 
					"			THEN 0 \n " +
					"			ELSE ISNULL(THANG_3.DUKIENBAN, 0) + ISNULL(THANG_2.DUKIENBAN, 0) + ISNULL(THANG_1.DUKIENBAN, 0) + ISNULL(THANG_0.DUKIENBAN, 0)*" + pt + " + SUM(ISNULL(KHOTT_SP.TONANTOAN, 0)) - SUM(ISNULL(KHOTT_SP.AVAILABLE, 0))  \n " +
					"			END  \n " +
					"		ELSE ISNULL(THANG_3.DUKIENBAN, 0) END, 0) AS YCCU_THANG, " + this.ID + " AS DBID \n " +
		
									
					"FROM ERP_KHOTT_SANPHAM KHOTT_SP  \n " +
					"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = KHOTT_SP.SANPHAM_FK  \n " +
					"LEFT JOIN  \n " +
					"(  \n " +
					"	SELECT DBSP.SANPHAM_FK, SUM(DBSP.DUKIENBAN) AS DUKIENBAN " +
					"	FROM ERP_DUBAOSANPHAMMTS DBSP " +
					"	INNER JOIN ERP_DUBAO DB ON DB.PK_SEQ = DBSP.DUBAO_FK " +
					"	WHERE THANG = " + t0 + " AND NAM = " + n0 + " AND DB.HIEULUC = 1 " +
					"   AND DB.PK_SEQ = (SELECT TOP 1 PK_SEQ FROM ERP_DUBAO WHERE HIEULUC = 1 ORDER BY NGAYDUBAO DESC)  \n " +
					"   AND DBSP.KHO_FK IN (" + khoId + ") \n " +
					"   GROUP BY DBSP.SANPHAM_FK \n " +
					")THANG_0 ON THANG_0.SANPHAM_FK = KHOTT_SP.SANPHAM_FK  \n " +
						
					"LEFT JOIN  \n " +
					"(  \n " +
					"	SELECT DBSP.SANPHAM_FK, SUM(DBSP.DUKIENBAN) AS DUKIENBAN " +
					"	FROM ERP_DUBAOSANPHAMMTS DBSP " +
					"	INNER JOIN ERP_DUBAO DB ON DB.PK_SEQ = DBSP.DUBAO_FK " +
					"	WHERE THANG = " + t1 + " AND NAM = " + n1 + " AND DB.HIEULUC = 1 " +
					"   AND DB.PK_SEQ = (SELECT TOP 1 PK_SEQ FROM ERP_DUBAO WHERE HIEULUC = 1 ORDER BY NGAYDUBAO DESC)  \n " +
					"   AND DBSP.KHO_FK IN (" + khoId + ") \n " +
					"   GROUP BY DBSP.SANPHAM_FK \n " +
					")THANG_1 ON THANG_1.SANPHAM_FK = KHOTT_SP.SANPHAM_FK  \n " +
					
					"LEFT JOIN   \n " +
					"(   \n " +
					"	SELECT DBSP.SANPHAM_FK, SUM(DBSP.DUKIENBAN) AS DUKIENBAN " +
					"	FROM ERP_DUBAOSANPHAMMTS DBSP " +
					"	INNER JOIN ERP_DUBAO DB ON DB.PK_SEQ = DBSP.DUBAO_FK " +
					"	WHERE THANG = " + t2 + " AND NAM = " + n2 + " AND DB.HIEULUC = 1 " +
					"   AND DB.PK_SEQ = (SELECT TOP 1 PK_SEQ FROM ERP_DUBAO WHERE HIEULUC = 1 ORDER BY NGAYDUBAO DESC)  \n " +
					"   AND DBSP.KHO_FK IN (" + khoId + ") \n " +
					"   GROUP BY DBSP.SANPHAM_FK \n " +
					")THANG_2 ON THANG_2.SANPHAM_FK = KHOTT_SP.SANPHAM_FK   \n " +
									
					"LEFT JOIN   \n " +
					"(   \n " +
					"	SELECT DBSP.SANPHAM_FK, SUM(DBSP.DUKIENBAN) AS DUKIENBAN " +
					"	FROM ERP_DUBAOSANPHAMMTS DBSP " +
					"	INNER JOIN ERP_DUBAO DB ON DB.PK_SEQ = DBSP.DUBAO_FK " +
					"	WHERE THANG = " + t3 + " AND NAM = " + n3 + " AND DB.HIEULUC = 1 " +
					"   AND DB.PK_SEQ = (SELECT TOP 1 PK_SEQ FROM ERP_DUBAO WHERE HIEULUC = 1 ORDER BY NGAYDUBAO DESC)  \n " +
					"   AND DBSP.KHO_FK IN (" + khoId + ") \n " +
					"   GROUP BY DBSP.SANPHAM_FK \n " +
					")THANG_3 ON THANG_3.SANPHAM_FK = KHOTT_SP.SANPHAM_FK   \n " +
					"WHERE KHOTT_SP.KHOTT_FK IN (" + khoIdTT + ") AND SP.TRANGTHAI = 1   \n " +
					"AND SP.LOAISANPHAM_FK IN (100002, 100003)   \n " +
					"GROUP BY KHOTT_SP.SANPHAM_FK, ISNULL(THANG_0.DUKIENBAN, 0),  \n " +
					"ISNULL(THANG_1.DUKIENBAN, 0), ISNULL(THANG_2.DUKIENBAN, 0),  \n " +
					"ISNULL(THANG_3.DUKIENBAN, 0)  \n " +

 					"UNION ALL \n " +
					"SELECT  DISTINCT KHOTT_SP.SANPHAM_FK AS SPID, " + t4 + " AS THANG, " + n4 + " AS NAM, \n "  +
					"ISNULL( \n " +
					"		CASE WHEN (ISNULL(THANG_3.DUKIENBAN, 0) + ISNULL(THANG_2.DUKIENBAN, 0) + ISNULL(THANG_1.DUKIENBAN, 0) + ISNULL(THANG_0.DUKIENBAN, 0)*" + pt + " + SUM(ISNULL(KHOTT_SP.TONANTOAN, 0)) - SUM(ISNULL(KHOTT_SP.AVAILABLE, 0)) < 0)   \n " +
					"		THEN  \n " +
					"			CASE WHEN (ISNULL(THANG_4.DUKIENBAN, 0) + ISNULL(THANG_3.DUKIENBAN, 0) + ISNULL(THANG_2.DUKIENBAN, 0) + ISNULL(THANG_1.DUKIENBAN, 0)*" + pt + " + ISNULL(THANG_0.DUKIENBAN, 0) + SUM(ISNULL(KHOTT_SP.TONANTOAN, 0)) - SUM(ISNULL(KHOTT_SP.AVAILABLE, 0)) < 0)  \n " + 
					"			THEN 0 \n " +
					"			ELSE ISNULL(ISNULL(THANG_4.DUKIENBAN, 0) + ISNULL(THANG_3.DUKIENBAN, 0), 0) + ISNULL(THANG_2.DUKIENBAN, 0) + ISNULL(THANG_1.DUKIENBAN, 0) + ISNULL(THANG_0.DUKIENBAN, 0)*" + pt + " + SUM(ISNULL(KHOTT_SP.TONANTOAN, 0)) - SUM(ISNULL(KHOTT_SP.AVAILABLE, 0))  \n " +
					"			END  \n " +
					"		ELSE ISNULL(THANG_4.DUKIENBAN, 0) END, 0) AS YCCU_THANG_4, " + this.ID + " AS DBID \n " +

					"FROM ERP_KHOTT_SANPHAM KHOTT_SP  \n " +
					"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = KHOTT_SP.SANPHAM_FK  \n " +
			
					"LEFT JOIN  \n " +
					"(  \n " +
					"	SELECT DBSP.SANPHAM_FK, SUM(DBSP.DUKIENBAN) AS DUKIENBAN " +
					"	FROM ERP_DUBAOSANPHAMMTS DBSP " +
					"	INNER JOIN ERP_DUBAO DB ON DB.PK_SEQ = DBSP.DUBAO_FK " +
					"	WHERE THANG = " + t0 + " AND NAM = " + n0 + " AND DB.HIEULUC = 1 " +
					"   AND DB.PK_SEQ = (SELECT TOP 1 PK_SEQ FROM ERP_DUBAO WHERE HIEULUC = 1 ORDER BY NGAYDUBAO DESC)  \n " +
					"   AND DBSP.KHO_FK IN (" + khoId + ") \n " +
					"   GROUP BY DBSP.SANPHAM_FK \n " +
					")THANG_0 ON THANG_0.SANPHAM_FK = KHOTT_SP.SANPHAM_FK  \n " +
						
					"LEFT JOIN  \n " +
					"(  \n " +
					"	SELECT DBSP.SANPHAM_FK, SUM(DBSP.DUKIENBAN) AS DUKIENBAN " +
					"	FROM ERP_DUBAOSANPHAMMTS DBSP " +
					"	INNER JOIN ERP_DUBAO DB ON DB.PK_SEQ = DBSP.DUBAO_FK " +
					"	WHERE THANG = " + t1 + " AND NAM = " + n1 + " AND DB.HIEULUC = 1 " +
					"   AND DB.PK_SEQ = (SELECT TOP 1 PK_SEQ FROM ERP_DUBAO WHERE HIEULUC = 1 ORDER BY NGAYDUBAO DESC)  \n " +
					"   AND DBSP.KHO_FK IN (" + khoId + ") \n " +
					"   GROUP BY DBSP.SANPHAM_FK \n " +
					")THANG_1 ON THANG_1.SANPHAM_FK = KHOTT_SP.SANPHAM_FK  \n " +
					
					"LEFT JOIN   \n " +
					"(   \n " +
					"	SELECT DBSP.SANPHAM_FK, SUM(DBSP.DUKIENBAN) AS DUKIENBAN " +
					"	FROM ERP_DUBAOSANPHAMMTS DBSP " +
					"	INNER JOIN ERP_DUBAO DB ON DB.PK_SEQ = DBSP.DUBAO_FK " +
					"	WHERE THANG = " + t2 + " AND NAM = " + n2 + " AND DB.HIEULUC = 1 " +
					"   AND DB.PK_SEQ = (SELECT TOP 1 PK_SEQ FROM ERP_DUBAO WHERE HIEULUC = 1 ORDER BY NGAYDUBAO DESC)  \n " +
					"   AND DBSP.KHO_FK IN (" + khoId + ") \n " +
					"   GROUP BY DBSP.SANPHAM_FK \n " +
					")THANG_2 ON THANG_2.SANPHAM_FK = KHOTT_SP.SANPHAM_FK   \n " +
									
					"LEFT JOIN   \n " +
					"(   \n " +
					"	SELECT DBSP.SANPHAM_FK, SUM(DBSP.DUKIENBAN) AS DUKIENBAN " +
					"	FROM ERP_DUBAOSANPHAMMTS DBSP " +
					"	INNER JOIN ERP_DUBAO DB ON DB.PK_SEQ = DBSP.DUBAO_FK " +
					"	WHERE THANG = " + t3 + " AND NAM = " + n3 + " AND DB.HIEULUC = 1 " +
					"   AND DB.PK_SEQ = (SELECT TOP 1 PK_SEQ FROM ERP_DUBAO WHERE HIEULUC = 1 ORDER BY NGAYDUBAO DESC)  \n " +
					"   AND DBSP.KHO_FK IN (" + khoId + ") \n " +
					"   GROUP BY DBSP.SANPHAM_FK \n " +
					")THANG_3 ON THANG_3.SANPHAM_FK = KHOTT_SP.SANPHAM_FK   \n " +

					"LEFT JOIN   \n " +
					"(   \n " +
					"	SELECT DBSP.SANPHAM_FK, SUM(DBSP.DUKIENBAN) AS DUKIENBAN " +
					"	FROM ERP_DUBAOSANPHAMMTS DBSP " +
					"	INNER JOIN ERP_DUBAO DB ON DB.PK_SEQ = DBSP.DUBAO_FK " +
					"	WHERE THANG = " + t4 + " AND NAM = " + n4 + " AND DB.HIEULUC = 1 " +
					"   AND DB.PK_SEQ = (SELECT TOP 1 PK_SEQ FROM ERP_DUBAO WHERE HIEULUC = 1 ORDER BY NGAYDUBAO DESC)  \n " +
					"   AND DBSP.KHO_FK IN (" + khoId + ") \n " +
					"   GROUP BY DBSP.SANPHAM_FK \n " +
					")THANG_4 ON THANG_4.SANPHAM_FK = KHOTT_SP.SANPHAM_FK   \n " +
			
					"WHERE KHOTT_SP.KHOTT_FK IN (" + khoIdTT + ") AND SP.TRANGTHAI = 1   \n " +
					"AND SP.LOAISANPHAM_FK IN (100002, 100003)   \n " +
					"GROUP BY KHOTT_SP.SANPHAM_FK, ISNULL(THANG_0.DUKIENBAN, 0),  \n " +
					"ISNULL(THANG_1.DUKIENBAN, 0), ISNULL(THANG_2.DUKIENBAN, 0),  \n " +
					"ISNULL(THANG_3.DUKIENBAN, 0), ISNULL(THANG_4.DUKIENBAN, 0)  \n " ;
			
			System.out.println("::: YEU CAU CUNG UNG: " + query );
			this.db.update(query);
		}
	
		private void TaoYeuCauSanXuat(String thang, String nam) 
		{
			
			String t0 = "", t1 = "", t2 = "", t3 = "", t4 = "", t5 = "";
			String n0 = "", n1 = "", n2 = "", n3 = "", n4 = "", n5 = "";
			t1 = thang;
			n1 = nam;
			
			if(t1.equals("1")){
				t0 = "12";
				n0 = "" + (Integer.parseInt(n1) - 1);
			}else{
				t0 = "" + (Integer.parseInt(t1) - 1);
				n0 = n1;
			}
			
			if(t1.equals("12")){
				t2 = "1";
				n2 = "" + (Integer.parseInt(n1) + 1);
			}else{
				t2 = "" + (Integer.parseInt(t1) + 1);
				n2 = n1;
			}
			
			if(t2.equals("12")){
				t3 = "1";
				n3 = "" + (Integer.parseInt(n2) + 1);
			}else{
				t3 = "" + (Integer.parseInt(t2) + 1);
				n3 = n2;
			}
			
			if(t3.equals("12")){
				t4 = "1";
				n4 = "" + (Integer.parseInt(n3) + 1);
			}else{
				t4 = "" + (Integer.parseInt(t3) + 1);
				n4 = n3;
			}
			
			if(t4.equals("12")){
				t5 = "1";
				n5 = "" + (Integer.parseInt(n4) + 1);
			}else{
				t5 = "" + (Integer.parseInt(t4) + 1);
				n5 = n4;
			}
			
			
			String query = "DELETE ERP_YEUCAUSANXUAT WHERE THANG = " + t0 + " AND NAM = " + n0 + "";
			this.db.update(query);
			
			query = "DELETE ERP_YEUCAUSANXUAT WHERE THANG = " + t1 + " AND NAM = " + n1 + "";
			this.db.update(query);
					
			query = "DELETE ERP_YEUCAUSANXUAT WHERE THANG = " + t2 + " AND NAM = " + n2 + "";
			this.db.update(query);
			
			query = "DELETE ERP_YEUCAUSANXUAT WHERE THANG = " + t3 + " AND NAM = " + n3 + "";
			this.db.update(query);
			
			query = "DELETE ERP_YEUCAUSANXUAT WHERE THANG = " + t4 + " AND NAM = " + n4 + "";
			this.db.update(query);
			
			//KHÔNG TÍNH LƯỢNG NHẬN HÀNG CỦA LSX, LSX VẪN TÍNH DỰA TRÊN TỔNG SẢN XUẤT.
			
			/*query = 	"INSERT INTO ERP_YEUCAUSANXUAT(CONGTY_FK, SANPHAM_FK, THANG, NAM, SOLUONG,  NGAYTAO, NGUOITAO, NGAYSUA, NGUOISUA, TRANGTHAI) \n " +
						"SELECT  " + this.CTYID + ", KHOTT_SP.SANPHAM_FK AS SPID, '" + t0 + "', '" + n0 + "', \n " +
						
						"(ISNULL(THANG_0.YCCU, 0) + ISNULL(THANG_1.YCCU, 0) - ISNULL(SX_THANG_0.QTY, 0)) AS YCSX_THANG,  \n " + // Yêu cầu SX tháng 0 là để đáp ứng cho Yêu cầu cung ứng tháng 0 và 1
					
						"'" + this.getDateTime() + "', " + this.USERID + ", '" + this.getDateTime() + "', " + this.USERID + ", '1' \n " +
												
						"FROM ERP_KHOTT_SANPHAM KHOTT_SP  \n " +
						"LEFT JOIN   \n " +
						" (   \n " +
						" 	SELECT YC.DUBAO_FK AS DBID, YC.SANPHAM_FK AS SPID, SUM(ISNULL(YC.YEUCAU, 0)) AS YCCU \n " +  
						" 	FROM ERP_YEUCAUCUNGUNG YC    \n " +
						" 	INNER JOIN ERP_DUBAO DB ON DB.PK_SEQ = YC.DUBAO_FK \n " +  
						"	WHERE THANG = " + t0 + " AND NAM = " + n0 + " AND DB.HIEULUC = 1  \n " +					
						"	AND YC.DUBAO_FK =  " + this.ID + "  \n " +
						" 	GROUP BY YC.DUBAO_FK, YC.SANPHAM_FK \n " +  
						" )THANG_0 ON THANG_0.SPID = KHOTT_SP.SANPHAM_FK \n " +  

						"LEFT JOIN  \n " +
						"(  \n " +
						"	SELECT YC.DUBAO_FK AS DBID, YC.SANPHAM_FK AS SPID, SUM(ISNULL(YC.YEUCAU, 0)) AS YCCU  \n " +
						"	FROM ERP_YEUCAUCUNGUNG YC   \n " +
						"	INNER JOIN ERP_DUBAO DB ON DB.PK_SEQ = YC.DUBAO_FK  \n " +
						"	WHERE THANG = " + t1 + " AND NAM = " + n1 + " AND DB.HIEULUC = 1  \n " +					
						"	AND YC.DUBAO_FK =  " + this.ID + "  \n " +
						"	GROUP BY YC.DUBAO_FK, YC.SANPHAM_FK  \n " +
						")THANG_1 ON THANG_1.SPID = KHOTT_SP.SANPHAM_FK  \n " +
						
						"LEFT JOIN(  \n " +
						"	SELECT SPID, SUM(QTY) AS QTY \n " +
						"	FROM(  \n " +		
					
							"	SELECT	LSXSP0.SANPHAM_FK AS SPID,  \n " + 
							"			(SUM(ISNULL(LSXSP0.SOLUONG,0)) -  ISNULL(NH.NHANHANG, 0))  AS QTY  \n " +  			
							"	FROM ERP_LENHSANXUAT_GIAY LSX0    \n  " +
							"	INNER JOIN ERP_LENHSANXUAT_SANPHAM LSXSP0 ON LSXSP0.LENHSANXUAT_FK = LSX0.PK_SEQ  \n " +  
							"	LEFT JOIN(  \n " +
							"		SELECT NK.SOLENHSANXUAT, NKSP.SANPHAM_FK, SUM(ISNULL(NKSP.SOLUONGNHAP, 0)) AS NHANHANG  \n " +
							"		FROM ERP_NHAPKHO NK  \n " +
							"		INNER JOIN ERP_NHAPKHO_SANPHAM NKSP ON NKSP.SONHAPKHO_FK = NK.PK_SEQ  \n " + 
							"		GROUP BY NKSP.SANPHAM_FK, NK.SOLENHSANXUAT  \n " +
							"	)NH ON NH.SANPHAM_FK = LSXSP0.SANPHAM_FK AND NH.SOLENHSANXUAT = LSX0.PK_SEQ  \n " +
					
							"	WHERE LSX0.NGAYDUKIENHT >=  '" + n0 + "-" + (t0.length()== 1? "0" + t0 : t0) + "-01'   \n " +
							"	AND LSX0.NGAYDUKIENHT <= '" + n0 + "-" + (t0.length()== 1? "0" + t0 : t0) + "-31'  AND LSX0.TRANGTHAI < 6  \n " + 
							"	GROUP BY LSXSP0.SANPHAM_FK, NH.NHANHANG  \n " +
						"	)SX GROUP BY SPID  \n " +			
						")SX_THANG_0 ON SX_THANG_0.SPID = KHOTT_SP.SANPHAM_FK  \n " + 
																						
						"WHERE KHOTT_SP.KHOTT_FK IN (100021, 100022)  \n " +
						"GROUP BY KHOTT_SP.SANPHAM_FK, ISNULL(THANG_0.YCCU, 0), \n " +
						"ISNULL(THANG_1.YCCU, 0),  ISNULL(SX_THANG_0.QTY, 0) \n " +
						

						"UNION ALL " +
						"SELECT  " + this.CTYID + ", KHOTT_SP.SANPHAM_FK AS SPID, '" + t1 + "', '" + n1 + "', \n " +
						
						"(ISNULL(THANG_2.YCCU, 0 ) - ISNULL(SX_THANG_1.QTY, 0)) AS YCSX_THANG,  \n " + // Yêu cầu SX tháng 1 là để đáp ứng cho Yêu cầu cung ứng tháng 2
					
						"'" + this.getDateTime() + "', " + this.USERID + ", '" + this.getDateTime() + "', " + this.USERID + ", '1' \n " +
				
						"FROM ERP_KHOTT_SANPHAM KHOTT_SP  \n " +
						
						"LEFT JOIN  \n " +
						"(  \n " +
						"	SELECT YC.DUBAO_FK AS DBID, YC.SANPHAM_FK AS SPID, SUM(ISNULL(YC.YEUCAU, 0)) AS YCCU  \n " +
						"	FROM ERP_YEUCAUCUNGUNG YC  \n " +
						"	INNER JOIN ERP_DUBAO DB ON DB.PK_SEQ = YC.DUBAO_FK " +
						"	WHERE THANG = " + t2 + "  AND NAM = " + n2 + " AND DB.HIEULUC = 1  \n " +
						"	AND YC.DUBAO_FK =  " + this.ID + "  \n " +
						"	GROUP BY YC.DUBAO_FK, YC.SANPHAM_FK  \n " +
						")THANG_2 ON THANG_2.SPID = KHOTT_SP.SANPHAM_FK  \n " +
										
						"LEFT JOIN( \n  " +
						"	SELECT SPID, SUM(QTY) AS QTY \n " +
						"	FROM(  \n " +		
					
						
							"	SELECT	LSXSP1.SANPHAM_FK AS SPID,  \n " + 
							"			(SUM(ISNULL(LSXSP1.SOLUONG,0)) -  ISNULL(NH.NHANHANG, 0))  AS QTY  \n " +  			
							"	FROM ERP_LENHSANXUAT_GIAY LSX1  \n " +   
							"	INNER JOIN ERP_LENHSANXUAT_SANPHAM LSXSP1 ON LSXSP1.LENHSANXUAT_FK = LSX1.PK_SEQ  \n " +  
							"	LEFT JOIN(  \n " +
							"		SELECT NK.SOLENHSANXUAT, NKSP.SANPHAM_FK, SUM(ISNULL(NKSP.SOLUONGNHAP, 0)) AS NHANHANG  \n " +
							"		FROM ERP_NHAPKHO NK  \n " +
							"		INNER JOIN ERP_NHAPKHO_SANPHAM NKSP ON NKSP.SONHAPKHO_FK = NK.PK_SEQ  \n " + 
//							"		WHERE NK.TRANGTHAI = 1  \n " +
							"		GROUP BY NKSP.SANPHAM_FK, NK.SOLENHSANXUAT  \n " +
							"	)NH ON NH.SANPHAM_FK = LSXSP1.SANPHAM_FK AND  NH.SOLENHSANXUAT = LSX1.PK_SEQ  \n " +
					
							"	WHERE LSX1.NGAYDUKIENHT >=  '" + n1 + "-" + (t1.length()== 1? "0" + t1 : t1) + "-01'  \n " + 
							"	AND LSX1.NGAYDUKIENHT <= '" + n1 + "-" + (t1.length()== 1? "0" + t1 : t1) + "-31'  AND LSX1.TRANGTHAI < 6  \n " + 
							"	GROUP BY LSXSP1.SANPHAM_FK, NH.NHANHANG  \n " +
						"	)SX GROUP BY SPID  \n " +
						")SX_THANG_1 ON SX_THANG_1.SPID = KHOTT_SP.SANPHAM_FK  \n " +
																						
						"WHERE KHOTT_SP.KHOTT_FK IN (100021, 100022)  \n " +
						"GROUP BY KHOTT_SP.SANPHAM_FK,  \n " +
						"ISNULL(THANG_2.YCCU, 0), ISNULL(SX_THANG_1.QTY, 0)  \n " + 

						"UNION ALL " +
						"SELECT  " + this.CTYID + ", KHOTT_SP.SANPHAM_FK AS SPID, '" + t2 + "', '" + n2 + "', \n " +
						
						"(ISNULL(THANG_3.YCCU, 0)  - ISNULL(SX_THANG_2.QTY, 0)) AS YCSX_THANG,   \n " + // Yêu cầu SX tháng 2 là để đáp ứng cho Yêu cầu cung ứng tháng 3
					
						"'" + this.getDateTime() + "', " + this.USERID + ", '" + this.getDateTime() + "', " + this.USERID + ", '1' \n " +
												
						"FROM ERP_KHOTT_SANPHAM KHOTT_SP  \n " +
					
						"LEFT JOIN  \n " +
						"(  \n " +
						"	SELECT YC.DUBAO_FK AS DBID, YC.SANPHAM_FK AS SPID, SUM(ISNULL(YC.YEUCAU, 0)) AS YCCU  \n " +
						"	FROM ERP_YEUCAUCUNGUNG YC  \n " +
						"	INNER JOIN ERP_DUBAO DB ON DB.PK_SEQ = YC.DUBAO_FK  \n " +
						"	WHERE THANG =" + t3 + " AND NAM = " + n3 + " AND DB.HIEULUC = 1  \n " +
						"	AND YC.DUBAO_FK =  " + this.ID + "  \n " +
						"	GROUP BY YC.DUBAO_FK, YC.SANPHAM_FK  \n " +
						")THANG_3 ON THANG_3.SPID = KHOTT_SP.SANPHAM_FK  \n " +
																				
						"LEFT JOIN(  \n " +
						"	SELECT SPID, SUM(QTY) AS QTY \n " +
						"	FROM(  \n " +		
										
							"	SELECT	LSXSP2.SANPHAM_FK AS SPID,  \n " + 
							"			(SUM(ISNULL(LSXSP2.SOLUONG,0)) -  ISNULL(NH.NHANHANG, 0))  AS QTY  \n " +  			
							"	FROM ERP_LENHSANXUAT_GIAY LSX2   \n   " +
							"	INNER JOIN ERP_LENHSANXUAT_SANPHAM LSXSP2 ON LSXSP2.LENHSANXUAT_FK = LSX2.PK_SEQ  \n " +  
							"	LEFT JOIN(  \n " +
							"		SELECT NK.SOLENHSANXUAT, NKSP.SANPHAM_FK, SUM(ISNULL(NKSP.SOLUONGNHAP, 0)) AS NHANHANG  \n " +
							"		FROM ERP_NHAPKHO NK  \n " +
							"		INNER JOIN ERP_NHAPKHO_SANPHAM NKSP ON NKSP.SONHAPKHO_FK = NK.PK_SEQ  \n " + 
//							"		WHERE NK.TRANGTHAI = 1  \n " +
							"		GROUP BY NKSP.SANPHAM_FK, NK.SOLENHSANXUAT  \n " +
							"	)NH ON NH.SANPHAM_FK = LSXSP2.SANPHAM_FK AND  NH.SOLENHSANXUAT = LSX2.PK_SEQ  \n " +
					
							"	WHERE LSX2.NGAYDUKIENHT >=  '" + n2 + "-" + (t2.length()== 1? "0" + t2 : t2) + "-01' \n   " +
							"	AND LSX2.NGAYDUKIENHT <= '" + n2 + "-" + (t2.length()== 1? "0" + t2 : t2) + "-31'  AND LSX2.TRANGTHAI < 6  \n " + 
							"	GROUP BY LSXSP2.SANPHAM_FK, NH.NHANHANG  \n " +
						"	)SX GROUP BY SPID  \n " +
						")SX_THANG_2 ON SX_THANG_2.SPID = KHOTT_SP.SANPHAM_FK  \n " +
																	
						"WHERE KHOTT_SP.KHOTT_FK IN (100021, 100022)  \n " +
						"GROUP BY KHOTT_SP.SANPHAM_FK,  \n " +
						"ISNULL(THANG_3.YCCU, 0), ISNULL(SX_THANG_2.QTY, 0)  \n " + 
						
						"UNION ALL " +
						"SELECT  " + this.CTYID + ", KHOTT_SP.SANPHAM_FK AS SPID, '" + t3 + "', '" + n3 + "', \n " +
						
						"(ISNULL(THANG_4.YCCU, 0)  - ISNULL(SX_THANG_3.QTY, 0)) AS YCSX_THANG,   \n " + // Yêu cầu SX tháng 3 là để đáp ứng cho Yêu cầu cung ứng tháng 4
					
						"'" + this.getDateTime() + "', " + this.USERID + ", '" + this.getDateTime() + "', " + this.USERID + ", '1' \n " +
								
						"FROM ERP_KHOTT_SANPHAM KHOTT_SP  \n " +
						"LEFT JOIN  \n " +
						"(  \n " +
						"	SELECT YC.DUBAO_FK AS DBID, YC.SANPHAM_FK AS SPID, SUM(ISNULL(YC.YEUCAU, 0)) AS YCCU  \n " +
						"	FROM ERP_YEUCAUCUNGUNG YC  \n " +
						"	INNER JOIN ERP_DUBAO DB ON DB.PK_SEQ = YC.DUBAO_FK  \n " +
						"	WHERE THANG =" + t4 + " AND NAM = " + n4 + " AND DB.HIEULUC = 1  \n " +
						"	AND YC.DUBAO_FK =  " + this.ID + "  \n " +
						"	GROUP BY YC.DUBAO_FK, YC.SANPHAM_FK  \n " +
						")THANG_4 ON THANG_4.SPID = KHOTT_SP.SANPHAM_FK  \n " +
						
						"LEFT JOIN(  \n " +
						"	SELECT SPID, SUM(QTY) AS QTY \n " +
						"	FROM(  \n " +		
					
			
							"	SELECT	LSXSP3.SANPHAM_FK AS SPID,  \n " + 
							"			(SUM(ISNULL(LSXSP3.SOLUONG,0)) -  ISNULL(NH.NHANHANG, 0))  AS QTY  \n " +  			
							"	FROM ERP_LENHSANXUAT_GIAY LSX3     \n " +
							"	INNER JOIN ERP_LENHSANXUAT_SANPHAM LSXSP3 ON LSXSP3.LENHSANXUAT_FK = LSX3.PK_SEQ  \n " +  
							"	LEFT JOIN(  \n " +
							"		SELECT NK.SOLENHSANXUAT, NKSP.SANPHAM_FK, SUM(ISNULL(NKSP.SOLUONGNHAP, 0)) AS NHANHANG  \n " +
							"		FROM ERP_NHAPKHO NK  \n " +
							"		INNER JOIN ERP_NHAPKHO_SANPHAM NKSP ON NKSP.SONHAPKHO_FK = NK.PK_SEQ  \n " + 
//							"		WHERE NK.TRANGTHAI = 1 \n  " +
							"		GROUP BY NKSP.SANPHAM_FK, NK.SOLENHSANXUAT  \n " +
							"	)NH ON NH.SANPHAM_FK = LSXSP3.SANPHAM_FK AND  NH.SOLENHSANXUAT = LSX3.PK_SEQ \n  " +
					
							"	WHERE LSX3.NGAYDUKIENHT >=  '" + n3 + "-" + (t3.length()== 1? "0" + t3 : t3) + "-01'  \n  " +
							"	AND LSX3.NGAYDUKIENHT <= '" + n3 + "-" + (t3.length()== 1? "0" + t3 : t3) + "-31'  AND LSX3.TRANGTHAI < 6  \n " + 
							"	GROUP BY LSXSP3.SANPHAM_FK, NH.NHANHANG  \n " +
						"	)SX GROUP BY SPID  \n " +
						")SX_THANG_3 ON SX_THANG_3.SPID = KHOTT_SP.SANPHAM_FK  \n " +
						
						"WHERE KHOTT_SP.KHOTT_FK IN (100021, 100022) " + 
						"GROUP BY KHOTT_SP.SANPHAM_FK,  \n " +
						"ISNULL(THANG_4.YCCU, 0), ISNULL(SX_THANG_3.QTY, 0) \n " ;*/ 
			
			//ANH HẢI YÊU CẦU YC CUNG ỨNG THÁNG NÀO THÌ YÊU CẦU SẢN XUẤT THÁNG ĐÓ
			query = 	"INSERT INTO ERP_YEUCAUSANXUAT(CONGTY_FK, SANPHAM_FK, THANG, NAM, SOLUONG,  NGAYTAO, NGUOITAO, NGAYSUA, NGUOISUA, TRANGTHAI) \n " +
					"SELECT  " + this.CTYID + ", KHOTT_SP.SANPHAM_FK AS SPID, '" + t0 + "', '" + n0 + "', \n " +
					
					"(ISNULL(THANG_0.YCCU, 0)  - ISNULL(SX_THANG_0.QTY, 0)) AS YCSX_THANG,  \n " + // Yêu cầu SX tháng 0 là để đáp ứng cho Yêu cầu cung ứng tháng 0 và 1
				
					"'" + this.getDateTime() + "', " + this.USERID + ", '" + this.getDateTime() + "', " + this.USERID + ", '1' \n " +
											
					"FROM ERP_KHOTT_SANPHAM KHOTT_SP  \n " +
					"LEFT JOIN   \n " +
					" (   \n " +
					" 	SELECT YC.DUBAO_FK AS DBID, YC.SANPHAM_FK AS SPID, SUM(ISNULL(YC.YEUCAU, 0)) AS YCCU \n " +  
					" 	FROM ERP_YEUCAUCUNGUNG YC    \n " +
					" 	INNER JOIN ERP_DUBAO DB ON DB.PK_SEQ = YC.DUBAO_FK \n " +  
					"	WHERE THANG = " + t0 + " AND NAM = " + n0 + " AND DB.HIEULUC = 1  \n " +					
					"	AND YC.DUBAO_FK =  " + this.ID + "  \n " +
					" 	GROUP BY YC.DUBAO_FK, YC.SANPHAM_FK \n " +  
					" )THANG_0 ON THANG_0.SPID = KHOTT_SP.SANPHAM_FK \n " +  

					"LEFT JOIN(  \n " +
					"	SELECT SPID, SUM(QTY) AS QTY \n " +
					"	FROM(  \n " +		
				
						"	SELECT	LSXSP0.SANPHAM_FK AS SPID,  \n " + 
						"			(SUM(ISNULL(LSXSP0.SOLUONG,0)) ) * isnull( dbo.LayQuyCach( LSXSP0.sanpham_fk, null, LSXSP0.dvdl_fk ), 1 )  AS QTY  \n " +  			
						"	FROM ERP_LENHSANXUAT_GIAY LSX0    \n  " +
						"	INNER JOIN ERP_LENHSANXUAT_SANPHAM LSXSP0 ON LSXSP0.LENHSANXUAT_FK = LSX0.PK_SEQ  \n " +  
						
						"	WHERE LSX0.NGAYDUKIENHT >=  '" + n0 + "-" + (t0.length()== 1? "0" + t0 : t0) + "-01'   \n " +
						"	AND LSX0.NGAYDUKIENHT <= '" + n0 + "-" + (t0.length()== 1? "0" + t0 : t0) + "-31'  AND LSX0.TRANGTHAI < 6  \n " + 
						"	GROUP BY LSXSP0.SANPHAM_FK , LSXSP0.dvdl_fk \n " +
					"	)SX GROUP BY SPID  \n " +			
					")SX_THANG_0 ON SX_THANG_0.SPID = KHOTT_SP.SANPHAM_FK  \n " + 
																					
					"WHERE KHOTT_SP.KHOTT_FK IN (100021, 100022)  \n " +
					"GROUP BY KHOTT_SP.SANPHAM_FK, ISNULL(THANG_0.YCCU, 0), ISNULL(SX_THANG_0.QTY, 0) \n " +
					
					"UNION ALL " +
					"SELECT  " + this.CTYID + ", KHOTT_SP.SANPHAM_FK AS SPID, '" + t1 + "', '" + n1 + "', \n " +
					
					"(ISNULL(THANG_2.YCCU, 0 ) - ISNULL(SX_THANG_1.QTY, 0)) AS YCSX_THANG,  \n " + // Yêu cầu SX tháng 1 là để đáp ứng cho Yêu cầu cung ứng tháng 2
				
					"'" + this.getDateTime() + "', " + this.USERID + ", '" + this.getDateTime() + "', " + this.USERID + ", '1' \n " +
			
					"FROM ERP_KHOTT_SANPHAM KHOTT_SP  \n " +
					
					"LEFT JOIN  \n " +
					"(  \n " +
					"	SELECT YC.DUBAO_FK AS DBID, YC.SANPHAM_FK AS SPID, SUM(ISNULL(YC.YEUCAU, 0)) AS YCCU  \n " +
					"	FROM ERP_YEUCAUCUNGUNG YC  \n " +
					"	INNER JOIN ERP_DUBAO DB ON DB.PK_SEQ = YC.DUBAO_FK " +
					"	WHERE THANG = " + t1 + "  AND NAM = " + n1 + " AND DB.HIEULUC = 1  \n " +
					"	AND YC.DUBAO_FK =  " + this.ID + "  \n " +
					"	GROUP BY YC.DUBAO_FK, YC.SANPHAM_FK  \n " +
					")THANG_2 ON THANG_2.SPID = KHOTT_SP.SANPHAM_FK  \n " +
									
					"LEFT JOIN( \n  " +
					"	SELECT SPID, SUM(QTY) AS QTY \n " +
					"	FROM(  \n " +		
				
						"	SELECT	LSXSP1.SANPHAM_FK AS SPID,  \n " + 
						"			(SUM(ISNULL(LSXSP1.SOLUONG,0)) ) * isnull( dbo.LayQuyCach( LSXSP1.sanpham_fk, null, LSXSP1.dvdl_fk ), 1 ) AS QTY  \n " +  			
						"	FROM ERP_LENHSANXUAT_GIAY LSX1  \n " +   
						"	INNER JOIN ERP_LENHSANXUAT_SANPHAM LSXSP1 ON LSXSP1.LENHSANXUAT_FK = LSX1.PK_SEQ  \n " +  
						
						"	WHERE LSX1.NGAYDUKIENHT >=  '" + n1 + "-" + (t1.length()== 1? "0" + t1 : t1) + "-01'  \n " + 
						"	AND LSX1.NGAYDUKIENHT <= '" + n1 + "-" + (t1.length()== 1? "0" + t1 : t1) + "-31'  AND LSX1.TRANGTHAI < 6  \n " + 
						"	GROUP BY LSXSP1.SANPHAM_FK, LSXSP1.dvdl_fk  \n " +
					"	)SX GROUP BY SPID  \n " +
					")SX_THANG_1 ON SX_THANG_1.SPID = KHOTT_SP.SANPHAM_FK  \n " +
																					
					"WHERE KHOTT_SP.KHOTT_FK IN (100021, 100022)  \n " +
					"GROUP BY KHOTT_SP.SANPHAM_FK,  \n " +
					"ISNULL(THANG_2.YCCU, 0), ISNULL(SX_THANG_1.QTY, 0)  \n " + 

					"UNION ALL " +
					"SELECT  " + this.CTYID + ", KHOTT_SP.SANPHAM_FK AS SPID, '" + t2 + "', '" + n2 + "', \n " +
					
					"(ISNULL(THANG_3.YCCU, 0)  - ISNULL(SX_THANG_2.QTY, 0)) AS YCSX_THANG,   \n " + // Yêu cầu SX tháng 2 là để đáp ứng cho Yêu cầu cung ứng tháng 3
				
					"'" + this.getDateTime() + "', " + this.USERID + ", '" + this.getDateTime() + "', " + this.USERID + ", '1' \n " +
											
					"FROM ERP_KHOTT_SANPHAM KHOTT_SP  \n " +
				
					"LEFT JOIN  \n " +
					"(  \n " +
					"	SELECT YC.DUBAO_FK AS DBID, YC.SANPHAM_FK AS SPID, SUM(ISNULL(YC.YEUCAU, 0)) AS YCCU  \n " +
					"	FROM ERP_YEUCAUCUNGUNG YC  \n " +
					"	INNER JOIN ERP_DUBAO DB ON DB.PK_SEQ = YC.DUBAO_FK  \n " +
					"	WHERE THANG =" + t2 + " AND NAM = " + n2 + " AND DB.HIEULUC = 1  \n " +
					"	AND YC.DUBAO_FK =  " + this.ID + "  \n " +
					"	GROUP BY YC.DUBAO_FK, YC.SANPHAM_FK  \n " +
					")THANG_3 ON THANG_3.SPID = KHOTT_SP.SANPHAM_FK  \n " +
																			
					"LEFT JOIN(  \n " +
					"	SELECT SPID, SUM(QTY) AS QTY \n " +
					"	FROM(  \n " +		
									
						"	SELECT	LSXSP2.SANPHAM_FK AS SPID,  \n " + 
						"			(SUM(ISNULL(LSXSP2.SOLUONG,0)) ) * isnull( dbo.LayQuyCach( LSXSP2.sanpham_fk, null, LSXSP2.dvdl_fk ), 1 ) AS QTY  \n " +  			
						"	FROM ERP_LENHSANXUAT_GIAY LSX2   \n   " +
						"	INNER JOIN ERP_LENHSANXUAT_SANPHAM LSXSP2 ON LSXSP2.LENHSANXUAT_FK = LSX2.PK_SEQ  \n " +  
						
						"	WHERE LSX2.NGAYDUKIENHT >=  '" + n2 + "-" + (t2.length()== 1? "0" + t2 : t2) + "-01' \n   " +
						"	AND LSX2.NGAYDUKIENHT <= '" + n2 + "-" + (t2.length()== 1? "0" + t2 : t2) + "-31'  AND LSX2.TRANGTHAI < 6  \n " + 
						"	GROUP BY LSXSP2.SANPHAM_FK, LSXSP2.dvdl_fk \n " +
					"	)SX GROUP BY SPID  \n " +
					")SX_THANG_2 ON SX_THANG_2.SPID = KHOTT_SP.SANPHAM_FK  \n " +
																
					"WHERE KHOTT_SP.KHOTT_FK IN (100021, 100022)  \n " +
					"GROUP BY KHOTT_SP.SANPHAM_FK,  \n " +
					"ISNULL(THANG_3.YCCU, 0), ISNULL(SX_THANG_2.QTY, 0)  \n " + 
					
					"UNION ALL " +
					"SELECT  " + this.CTYID + ", KHOTT_SP.SANPHAM_FK AS SPID, '" + t3 + "', '" + n3 + "', \n " +
					
					"(ISNULL(THANG_3.YCCU, 0)  - ISNULL(SX_THANG_2.QTY, 0)) AS YCSX_THANG,   \n " + // Yêu cầu SX tháng 3 là để đáp ứng cho Yêu cầu cung ứng tháng 3
					
					"'" + this.getDateTime() + "', " + this.USERID + ", '" + this.getDateTime() + "', " + this.USERID + ", '1' \n " +
											
					"FROM ERP_KHOTT_SANPHAM KHOTT_SP  \n " +
					
					"LEFT JOIN  \n " +
					"(  \n " +
					"	SELECT YC.DUBAO_FK AS DBID, YC.SANPHAM_FK AS SPID, SUM(ISNULL(YC.YEUCAU, 0)) AS YCCU  \n " +
					"	FROM ERP_YEUCAUCUNGUNG YC  \n " +
					"	INNER JOIN ERP_DUBAO DB ON DB.PK_SEQ = YC.DUBAO_FK  \n " +
					"	WHERE THANG =" + t3 + " AND NAM = " + n3 + " AND DB.HIEULUC = 1  \n " +
					"	AND YC.DUBAO_FK =  " + this.ID + "  \n " +
					"	GROUP BY YC.DUBAO_FK, YC.SANPHAM_FK  \n " +
					")THANG_3 ON THANG_3.SPID = KHOTT_SP.SANPHAM_FK  \n " +
																			
					"LEFT JOIN(  \n " +
					"	SELECT SPID, SUM(QTY) AS QTY \n " +
					"	FROM(  \n " +		
									
						"	SELECT	LSXSP2.SANPHAM_FK AS SPID,  \n " + 
						"			(SUM(ISNULL(LSXSP2.SOLUONG,0)) ) * isnull( dbo.LayQuyCach( LSXSP2.sanpham_fk, null, LSXSP2.dvdl_fk ), 1 ) AS QTY  \n " +  			
						"	FROM ERP_LENHSANXUAT_GIAY LSX2   \n   " +
						"	INNER JOIN ERP_LENHSANXUAT_SANPHAM LSXSP2 ON LSXSP2.LENHSANXUAT_FK = LSX2.PK_SEQ  \n " +  
						
						"	WHERE LSX2.NGAYDUKIENHT >=  '" + n3 + "-" + (t3.length()== 1? "0" + t3 : t3) + "-01' \n   " +
						"	AND LSX2.NGAYDUKIENHT <= '" + n3 + "-" + (t3.length()== 1? "0" + t3 : t3) + "-31'  AND LSX2.TRANGTHAI < 6  \n " + 
						"	GROUP BY LSXSP2.SANPHAM_FK, LSXSP2.dvdl_fk \n " +
					"	)SX GROUP BY SPID  \n " +
					")SX_THANG_2 ON SX_THANG_2.SPID = KHOTT_SP.SANPHAM_FK  \n " +
																
					"WHERE KHOTT_SP.KHOTT_FK IN (100021, 100022)  \n " +
					"GROUP BY KHOTT_SP.SANPHAM_FK,  \n " +
					"ISNULL(THANG_3.YCCU, 0), ISNULL(SX_THANG_2.QTY, 0)  \n " + 
					
					
					"UNION ALL " +
					"SELECT  " + this.CTYID + ", KHOTT_SP.SANPHAM_FK AS SPID, '" + t4 + "', '" + n4 + "', \n " +
					
					"(ISNULL(THANG_4.YCCU, 0)  - ISNULL(SX_THANG_3.QTY, 0)) AS YCSX_THANG,   \n " + // Yêu cầu SX tháng 4 là để đáp ứng cho Yêu cầu cung ứng tháng 4
				
					"'" + this.getDateTime() + "', " + this.USERID + ", '" + this.getDateTime() + "', " + this.USERID + ", '1' \n " +
							
					"FROM ERP_KHOTT_SANPHAM KHOTT_SP  \n " +
					"LEFT JOIN  \n " +
					"(  \n " +
					"	SELECT YC.DUBAO_FK AS DBID, YC.SANPHAM_FK AS SPID, SUM(ISNULL(YC.YEUCAU, 0)) AS YCCU  \n " +
					"	FROM ERP_YEUCAUCUNGUNG YC  \n " +
					"	INNER JOIN ERP_DUBAO DB ON DB.PK_SEQ = YC.DUBAO_FK  \n " +
					"	WHERE THANG =" + t4 + " AND NAM = " + n4 + " AND DB.HIEULUC = 1  \n " +
					"	AND YC.DUBAO_FK =  " + this.ID + "  \n " +
					"	GROUP BY YC.DUBAO_FK, YC.SANPHAM_FK  \n " +
					")THANG_4 ON THANG_4.SPID = KHOTT_SP.SANPHAM_FK  \n " +
					
					"LEFT JOIN(  \n " +
					"	SELECT SPID, SUM(QTY) AS QTY \n " +
					"	FROM(  \n " +		
					
						"	SELECT	LSXSP3.SANPHAM_FK AS SPID,  \n " + 
						"			( SUM(ISNULL(LSXSP3.SOLUONG,0)) ) * isnull( dbo.LayQuyCach( LSXSP3.sanpham_fk, null, LSXSP3.dvdl_fk ), 1 ) AS QTY  \n " +  			
						"	FROM ERP_LENHSANXUAT_GIAY LSX3     \n " +
						"	INNER JOIN ERP_LENHSANXUAT_SANPHAM LSXSP3 ON LSXSP3.LENHSANXUAT_FK = LSX3.PK_SEQ  \n " +
						"	WHERE LSX3.NGAYDUKIENHT >=  '" + n4 + "-" + (t4.length()== 1? "0" + t4 : t4) + "-01'  \n  " +
						"	AND LSX3.NGAYDUKIENHT <= '" + n4 + "-" + (t4.length()== 1? "0" + t4 : t4) + "-31'  AND LSX3.TRANGTHAI < 6  \n " + 
						"	GROUP BY LSXSP3.SANPHAM_FK, LSXSP3.dvdl_fk \n " +
					"	)SX GROUP BY SPID  \n " +
					")SX_THANG_3 ON SX_THANG_3.SPID = KHOTT_SP.SANPHAM_FK  \n " +
					
					"WHERE KHOTT_SP.KHOTT_FK IN (100021, 100022) " + 
					"GROUP BY KHOTT_SP.SANPHAM_FK,  \n " +
					"ISNULL(THANG_4.YCCU, 0), ISNULL(SX_THANG_3.QTY, 0) \n " ;
				
			System.out.println( ":::: CHEN ERP_YEUCAUSANXUAT: " + query);
			this.db.update(query);
			
			//Đề nghị âm thì xóa đi
			query = "delete ERP_YEUCAUSANXUAT where SOLUONG < 0  ";
			this.db.update(query);
			
			
			//TEST
			//query = "delete ERP_YEUCAUSANXUAT where sanpham_fk != '100192' and thang in ( 4, 5, 6 ) and nam = '2017' ";
			//this.db.update(query);
	
		}
	
		private void TaoLenhSanXuatDK_TP(String thang, String nam)
		{
			String query = "";
			
			String t0, t1 = "", t2 = "", t3 = "", t4 = ""; 
			String n0, n1 = "", n2 = "", n3 = "", n4 = "";
			t1 = thang;
			n1 = nam;
			
			if(t1.equals("1")){
				t0 = "12";
				n0 = "" + (Integer.parseInt(n1) - 1);
			}else{
				t0 = "" + (Integer.parseInt(t1) - 1);
				n0 = n1;
			}
			
			if(t1.equals("12")){
				t2 = "1";
				n2 = "" + (Integer.parseInt(n1) + 1);
			}else{
				t2 = "" + (Integer.parseInt(t1) + 1);
				n2 = n1;
			}
			
			if(t2.equals("12")){
				t3 = "1";
				n3 = "" + (Integer.parseInt(n2) + 1);
			}else{
				t3 = "" + (Integer.parseInt(t2) + 1);
				n3 = n2;
			}
			
			if(t3.equals("12")){
				t4 = "1";
				n4 = "" + (Integer.parseInt(n3) + 1);
			}else{
				t4 = "" + (Integer.parseInt(t3) + 1);
				n4 = n3;
			}
			
			query = "DELETE ERP_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU \n " +
					"WHERE LENHSANXUATDUKIEN_FK IN ( \n " +
					"	SELECT PK_SEQ FROM ERP_LENHSANXUATDUKIEN \n " +
					"	WHERE YEUCAUSANXUAT_FK IN (SELECT PK_SEQ FROM ERP_YEUCAUSANXUAT WHERE THANG = " + t0 + " AND NAM = " + n0 + "))";
			System.out.println("::1. XOA YCNL: " + query);
			this.db.update(query);
			
			query = "DELETE ERP_LENHSANXUATDUKIEN " +
					"WHERE SUBSTRING(NGAYKETTHUC, 1, 4) = " + n0 + " AND CONVERT(INT, SUBSTRING(NGAYKETTHUC, 6, 2)) = " + t0 + "";
			System.out.println("::2. XOA LSXDK: " + query);
			this.db.update(query);
			
			query = "DELETE ERP_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU \n " +
					"WHERE LENHSANXUATDUKIEN_FK IN ( \n " +
					"	SELECT PK_SEQ FROM ERP_LENHSANXUATDUKIEN \n " +
					"	WHERE YEUCAUSANXUAT_FK IN (SELECT PK_SEQ FROM ERP_YEUCAUSANXUAT WHERE THANG = " + t1 + " AND NAM = " + n1 + "))";
			System.out.println("::3. XOA LSXDK: " + query);
			this.db.update(query);
			
			query = "DELETE ERP_LENHSANXUATDUKIEN " +
					"WHERE SUBSTRING(NGAYKETTHUC, 1, 4) = " + n1 + " AND CONVERT(INT, SUBSTRING(NGAYKETTHUC, 6, 2)) = " + t1 + "";
			System.out.println("::4. XOA LSXDK: " + query);
			this.db.update(query);
			
			
			query = "DELETE ERP_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU \n " +
					"WHERE LENHSANXUATDUKIEN_FK IN ( \n " +
					"	SELECT PK_SEQ FROM ERP_LENHSANXUATDUKIEN \n " +
					"	WHERE YEUCAUSANXUAT_FK IN (SELECT PK_SEQ FROM ERP_YEUCAUSANXUAT WHERE THANG = " + t2 + " AND NAM = " + n2 + "))";
			this.db.update(query);
			
			query = "DELETE ERP_LENHSANXUATDUKIEN " +
			"WHERE SUBSTRING(NGAYKETTHUC, 1, 4) = " + n2 + " AND CONVERT(INT, SUBSTRING(NGAYKETTHUC, 6, 2)) = " + t2 + "";
			this.db.update(query);
			
			query = "DELETE ERP_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU \n " +
					"WHERE LENHSANXUATDUKIEN_FK IN ( \n " +
					"	SELECT PK_SEQ FROM ERP_LENHSANXUATDUKIEN \n " +
					"	WHERE YEUCAUSANXUAT_FK IN (SELECT PK_SEQ FROM ERP_YEUCAUSANXUAT WHERE THANG = " + t3 + " AND NAM = " + n3 + "))";
			this.db.update(query);
			
			query = "DELETE ERP_LENHSANXUATDUKIEN " +
					"WHERE SUBSTRING(NGAYKETTHUC, 1, 4) = " + n3 + " AND CONVERT(INT, SUBSTRING(NGAYKETTHUC, 6, 2)) = " + t3 + "";
			this.db.update(query);
			
			query = "DELETE ERP_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU \n " +
					"WHERE LENHSANXUATDUKIEN_FK IN ( \n " +
					"	SELECT PK_SEQ FROM ERP_LENHSANXUATDUKIEN \n " +
					"	WHERE YEUCAUSANXUAT_FK IN (SELECT PK_SEQ FROM ERP_YEUCAUSANXUAT WHERE THANG = " + t4 + " AND NAM = " + n4 + "))";
			this.db.update(query);
			
			query = "DELETE ERP_LENHSANXUATDUKIEN " +
					"WHERE SUBSTRING(NGAYKETTHUC, 1, 4) = " + n4 + " AND CONVERT(INT, SUBSTRING(NGAYKETTHUC, 6, 2)) = " + t4 + "";
			this.db.update(query);
			
			//THAY ĐỔI YÊU CẦU, SỐ LƯỢNG CHUẨN LẤY THEO BOM, NHÀ MÁY LẤY TRONG BOM
			//SỐ LƯỢNG ĐỀ NGHỊ PHẢI QUY RA ĐƠN VỊ TRONG BOM
			
			query = "SELECT YCSX.PK_SEQ AS YCID, YCSX.SANPHAM_FK AS SPID, YCSX.SOLUONG * BOM.quycach as SOLUONG, YCSX.NAM, YCSX.THANG, \n " +
			
					"   BOM.SOLUONGCHUAN,	\n" +
					"   ISNULL(TTDH.THOIGIANSX, 0) AS THOIGIAN, ( select top(1) NHAMAY_FK from ERP_DANHMUCVATTU_NHAMAY where DANHMUCVATTU_FK = BOM.danhmucvt_fk ) AS NMID	 \n " +	
					
					"FROM ERP_YEUCAUSANXUAT YCSX \n " +
					"	INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = YCSX.SANPHAM_FK	 \n " +		
					"	LEFT JOIN ERP_THONGTINDATHANG TTDH ON TTDH.SANPHAM_FK = SP.PK_SEQ	 \n " +	
					"	LEFT JOIN ERP_DANHMUCVATTU_KEHOACH BOM ON BOM.SANPHAM_FK = SP.PK_SEQ and BOM.dubao_fk = '" + this.ID + "' \n " +		
					"WHERE YCSX.NAM = " + n0 + " AND YCSX.THANG = " + t0 + " AND YCSX.CONGTY_FK = " + this.CTYID + " 	 \n " +		
					
					"UNION ALL \n " +
										
					"SELECT YCSX.PK_SEQ AS YCID, YCSX.SANPHAM_FK AS SPID, YCSX.SOLUONG * BOM.quycach, YCSX.NAM, YCSX.THANG, \n " +
			
					"   BOM.SOLUONGCHUAN,	\n" +
					"   ISNULL(TTDH.THOIGIANSX, 0) AS THOIGIAN, ( select top(1) NHAMAY_FK from ERP_DANHMUCVATTU_NHAMAY where DANHMUCVATTU_FK = BOM.danhmucvt_fk ) AS NMID	 \n " +	
					
					"FROM ERP_YEUCAUSANXUAT YCSX \n " +
					"	INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = YCSX.SANPHAM_FK	 \n " +		
					"	LEFT JOIN ERP_THONGTINDATHANG TTDH ON TTDH.SANPHAM_FK = SP.PK_SEQ	 \n " +	
					"	LEFT JOIN ERP_DANHMUCVATTU_KEHOACH BOM ON BOM.SANPHAM_FK = SP.PK_SEQ and BOM.dubao_fk = '" + this.ID + "' \n " +	
					"WHERE YCSX.NAM = " + n1 + " AND YCSX.THANG = " + t1 + "  AND YCSX.CONGTY_FK = " + this.CTYID + " 	 \n " +		
					
					"UNION ALL \n " +
					
					"SELECT YCSX.PK_SEQ AS YCID, YCSX.SANPHAM_FK AS SPID, YCSX.SOLUONG * BOM.quycach, YCSX.NAM, YCSX.THANG, \n " +
			
					"   BOM.SOLUONGCHUAN,	\n" +
					"   ISNULL(TTDH.THOIGIANSX, 0) AS THOIGIAN, ( select top(1) NHAMAY_FK from ERP_DANHMUCVATTU_NHAMAY where DANHMUCVATTU_FK = BOM.danhmucvt_fk ) AS NMID	 \n " +	
					
					"FROM ERP_YEUCAUSANXUAT YCSX \n " +
					"	INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = YCSX.SANPHAM_FK	 \n " +		
					"	LEFT JOIN ERP_THONGTINDATHANG TTDH ON TTDH.SANPHAM_FK = SP.PK_SEQ	 \n " +	
					"	LEFT JOIN ERP_DANHMUCVATTU_KEHOACH BOM ON BOM.SANPHAM_FK = SP.PK_SEQ and BOM.dubao_fk = '" + this.ID + "' \n " +	
					"WHERE YCSX.NAM = " + n2 + " AND YCSX.THANG = " + t2 + "  AND YCSX.CONGTY_FK = " + this.CTYID + " 	 \n " +		
					
					"UNION ALL \n " +
										
					"SELECT YCSX.PK_SEQ AS YCID, YCSX.SANPHAM_FK AS SPID, YCSX.SOLUONG * BOM.quycach, YCSX.NAM, YCSX.THANG, \n " +
			
					"   BOM.SOLUONGCHUAN,	\n" +
					"   ISNULL(TTDH.THOIGIANSX, 0) AS THOIGIAN, ( select top(1) NHAMAY_FK from ERP_DANHMUCVATTU_NHAMAY where DANHMUCVATTU_FK = BOM.danhmucvt_fk ) AS NMID	 \n " +	
					
					"FROM ERP_YEUCAUSANXUAT YCSX \n " +
					"	INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = YCSX.SANPHAM_FK	 \n " +		
					"	LEFT JOIN ERP_THONGTINDATHANG TTDH ON TTDH.SANPHAM_FK = SP.PK_SEQ	 \n " +	
					"	LEFT JOIN ERP_DANHMUCVATTU_KEHOACH BOM ON BOM.SANPHAM_FK = SP.PK_SEQ and BOM.dubao_fk = '" + this.ID + "' \n " +
					"WHERE YCSX.NAM = " + n3 + " AND YCSX.THANG = " + t3 + "  AND YCSX.CONGTY_FK = " + this.CTYID + " 	 \n " +			
					
					"UNION ALL \n " +
					
					"SELECT YCSX.PK_SEQ AS YCID, YCSX.SANPHAM_FK AS SPID, YCSX.SOLUONG * BOM.quycach, YCSX.NAM, YCSX.THANG, \n " +
			
					"   BOM.SOLUONGCHUAN,	\n" +
					"   ISNULL(TTDH.THOIGIANSX, 0) AS THOIGIAN, ( select top(1) NHAMAY_FK from ERP_DANHMUCVATTU_NHAMAY where DANHMUCVATTU_FK = BOM.danhmucvt_fk ) AS NMID	 \n " +	
					
					"FROM ERP_YEUCAUSANXUAT YCSX \n " +
					"	INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = YCSX.SANPHAM_FK	 \n " +		
					"	LEFT JOIN ERP_THONGTINDATHANG TTDH ON TTDH.SANPHAM_FK = SP.PK_SEQ	 \n " +	
					"	LEFT JOIN ERP_DANHMUCVATTU_KEHOACH BOM ON BOM.SANPHAM_FK = SP.PK_SEQ and BOM.dubao_fk = '" + this.ID + "' \n " +
					"WHERE YCSX.NAM = " + n4 + " AND YCSX.THANG = " + t4 + "  AND YCSX.CONGTY_FK = " + this.CTYID + " 	 \n ";			
					
			/*query = " select * from ( " + query + " ) DT where SPID = '100112' ";*/
			
			System.out.println("::: LAY YCSX DU KIEN: " + query);
			ResultSet ycSX = this.db.get(query);
			
			//Phải lưu lại lượng hàng còn dư của các tháng trước khi chạy cho tháng tiếp theo
			Hashtable<String, Double> sp_sudung = new Hashtable<String, Double>();
			if(ycSX != null)
			{
				try
				{
					while(ycSX.next())
					{
						String ycId = ycSX.getString("YCID");
						String spId = ycSX.getString("SPID");
						String nmId = ycSX.getString("NMID");
						double soluong = ycSX.getDouble("SOLUONG");
						double soluongchuan = ycSX.getDouble("SOLUONGCHUAN");
						nam = ycSX.getString("NAM");
						thang = ycSX.getString("THANG");
						
						String thangtruoc, namtruoc;
						if(thang.equals("1")){
							thangtruoc = "12";
							namtruoc = "" + (Integer.parseInt(nam) - 1);
						}else{
							thangtruoc = "" + (Integer.parseInt(thang) - 1);
							namtruoc = nam;
						}
						
						String ngaybatdau = "" + nam + "-" + (thang.length() == 1?("0" + thang):thang) + "-01";
						String ngayketthuc = "" + nam + "-" + (thang.length() == 1?("0" + thang):thang) + "-30";
						
						//Từ số lượng dư, sẽ quy định có cần đề nghị sản xuất tiếp không
						boolean denghiTIEP = false;
						
						double totalDU = 0;
						if( sp_sudung.get(spId) != null )
							totalDU = sp_sudung.get(spId);
						
						//Trong trường hợp sản xuất của tháng liền trước dư, sẽ tổng kết lại để chuyển qua tháng sau
						if( soluong < 0 )
						{
							totalDU += Math.abs(soluong);
							sp_sudung.put(spId, soluong);
							denghiTIEP = false;
						}
						else if( soluong > 0 )
						{
							if( soluong <= totalDU )
							{
								totalDU -= soluong;
								denghiTIEP = false;
							}
							else if( totalDU > 0 )
							{
								soluong = soluong - totalDU;
								totalDU = 0;
								denghiTIEP = true;
							}
							else
								denghiTIEP = true;
						}
						sp_sudung.put(spId, totalDU);
							
						if( soluong > 0 && denghiTIEP )
						{
							if(soluongchuan > 0 )
							{
								if(soluong >= soluongchuan)
								{
									double conlai = soluong;
									while (conlai >= soluongchuan){
										
										query = "INSERT INTO ERP_LENHSANXUATDUKIEN (NHAMAY_FK, SANPHAM_FK, SOLUONG, SANXUAT, NGAYBATDAU, NGAYKETTHUC, YEUCAUSANXUAT_FK, " +
												"NGAYTAO, NGUOITAO, NGAYSUA, NGUOISUA, CONGTY_FK, BOM_FK  ) " +
												"SELECT " + nmId + ", " + spId + ", " + soluongchuan + ", 0, '" + ngaybatdau + "', " +											
												"	'" + ngayketthuc + "', " + ycId + ", " +
												"	'" + this.getDateTime() + "', '" + this.USERID + "', '" + this.getDateTime() + "', '" + this.USERID + "', " + this.CTYID + ", " + 
												"( select danhmucvt_fk from ERP_DANHMUCVATTU_KEHOACH where sanpham_fk = '" + spId + "' and dubao_fk = '" + this.ID + "' ) ";
										System.out.println(query);
										this.db.update(query);
										
										conlai = conlai - soluongchuan;
									}
									
									if(conlai > 0){
							
										query = "INSERT INTO ERP_LENHSANXUATDUKIEN (NHAMAY_FK, SANPHAM_FK, SOLUONG, SANXUAT, NGAYBATDAU, NGAYKETTHUC, YEUCAUSANXUAT_FK, " +
												"NGAYTAO, NGUOITAO, NGAYSUA, NGUOISUA, CONGTY_FK, BOM_FK ) " +
												"SELECT " + nmId + ", " + spId + ", " + soluongchuan + ", 0, '" + ngaybatdau + "', " +											
												"'" + ngayketthuc + "', " + ycId + ", " +
												"'" + this.getDateTime() + "', '" + this.USERID + "', '" + this.getDateTime() + "', '" + this.USERID + "', " + this.CTYID + ", " + 
												"( select danhmucvt_fk from ERP_DANHMUCVATTU_KEHOACH where sanpham_fk = '" + spId + "' and dubao_fk = '" + this.ID + "' ) ";
										System.out.println(query);
										this.db.update(query);
									}
								}else{
									
									query = "INSERT INTO ERP_LENHSANXUATDUKIEN (NHAMAY_FK, SANPHAM_FK, SOLUONG, SANXUAT, NGAYBATDAU, NGAYKETTHUC, YEUCAUSANXUAT_FK, " +
											"NGAYTAO, NGUOITAO, NGAYSUA, NGUOISUA, CONGTY_FK, BOM_FK ) " +
											"SELECT " + nmId + ", " + spId + ", " + soluongchuan + ", 0, '" + ngaybatdau + "', " +											
											"'" + ngayketthuc + "', " + ycId + ", " +
											"'" + this.getDateTime() + "', '" + this.USERID + "', '" + this.getDateTime() + "', '" + this.USERID + "', " + this.CTYID + ", " + 
											"( select danhmucvt_fk from ERP_DANHMUCVATTU_KEHOACH where sanpham_fk = '" + spId + "' and dubao_fk = '" + this.ID + "' ) ";
									System.out.println(query);
									this.db.update(query);
									
								}
							}
							else
							{
								query = "INSERT INTO ERP_LENHSANXUATDUKIEN (NHAMAY_FK, SANPHAM_FK, SOLUONG, SANXUAT, NGAYBATDAU, NGAYKETTHUC, YEUCAUSANXUAT_FK, " +
										"NGAYTAO, NGUOITAO, NGAYSUA, NGUOISUA, CONGTY_FK, BOM_FK ) " +
										"SELECT " + nmId + ", " + spId + ", " + soluong + ", 0, '" + ngaybatdau + "', " +											
										"'" + ngayketthuc + "', " + ycId + ", " +
										"'" + this.getDateTime() + "', '" + this.USERID + "', '" + this.getDateTime() + "', '" + this.USERID + "', " + this.CTYID + ", " + 
										"( select danhmucvt_fk from ERP_DANHMUCVATTU_KEHOACH where sanpham_fk = '" + spId + "' and dubao_fk = '" + this.ID + "' ) ";
								System.out.println(query);
								this.db.update(query);
							}
						}
					}
					ycSX.close();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			
			//[11:35:29 AM] Nguyễn Duy Hải GESO: Quản lý cung ứng > Lập kế hoạch > Đề nghị sản xuất
			//[11:35:42 AM] Nguyễn Duy Hải GESO: tách thành 2 loại: sản xuất và gia công -> dựa vào loại hàng hóa
			//[11:35:53 AM] Nguyễn Duy Hải GESO: nếu gia công thì ko có nhà máy
			query = "update a set a.LOAIHANGHOA = b.loaihanghoa, " +
					"			 a.NHAMAY_FK = case b.loaihanghoa when 2 then NULL else a.NHAMAY_FK end  " +
					"from ERP_LENHSANXUATDUKIEN a left join ERP_SANPHAM b on a.SANPHAM_FK = b.PK_SEQ ";
			db.update(query);
			
	
			//CAP NHAT THEO BOM
			query = " update a set a.SOLUONG_BOM = a.soluong, a.SOLUONG_CHUAN = a.soluong / isnull(b.quycach, 1), a.DVDL_FK = b.dvdl_fk, quycach = a.quycach  "+
					" from ERP_LENHSANXUATDUKIEN a left join ERP_DANHMUCVATTU_KEHOACH b on a.bom_fk = b.danhmucvt_fk  "+
					" where b.dubao_fk = '" + this.ID + "' ";
			db.update(query);

		}
	
		private void TaoLenhSanXuatDK_TP_Tuan(String thang, String nam)
		{
			double tuan_1 = 0, tuan_2 = 0, tuan_3 = 0, tuan_4 = 0;

			String query = "";
			
			//B1. Loại hàng hóa sản xuất thì mới có nhà máy, và chia theo tuần
			query = "SELECT PK_SEQ AS NMID, MANHAMAY + ' - ' + TENNHAMAY AS TEN FROM ERP_NHAMAY WHERE TRANGTHAI = 1 AND CONGTY_FK = " + this.CTYID + "";
			ResultSet nmRS = this.db.get(query);
			try
			{
				while(nmRS.next())
				{
					query = "SELECT SANPHAM_FK as SPID, Month(NGAYBATDAU) as thang, Year( NGAYBATDAU ) as nam, count( pk_seq ) as solenh \n " +
							"FROM ERP_LENHSANXUATDUKIEN  \n " +
							"WHERE NGAYKETTHUC >= '" + this.getDateTime() + "'  \n " +
									"AND NHAMAY_FK = " + nmRS.getString("NMID") + "  \n " +
							"group by  SANPHAM_FK, Month(NGAYBATDAU), Year( NGAYBATDAU ) " +
							"ORDER BY  Year( NGAYBATDAU ), Month(NGAYBATDAU), SANPHAM_FK ";
					System.out.println(":: CHIA THEO TUAN: " + query);

					ResultSet rs = this.db.get(query);
					if( rs != null )
					{
						while(rs.next() )
						{
							String spId = rs.getString("SPID");
							int _thang = rs.getInt("thang");
							int _nam = rs.getInt("nam");
							int solenh = rs.getInt("solenh");
							
							String[] tuan_dayStart = new String[]{ "01", "08", "15", "22" };
							String[] tuan_dayEnd = new String[]{ "07", "14", "21", "28" };
							int[] solenh_tuan = this.Phanbolenh(1, solenh);
							
							for( int i = 0; i < solenh_tuan.length; i++ )
							{
								if( solenh_tuan[i] > 0 )
								{
									String ngaybatdau = _nam + "-" + ( _thang < 10 ? ( "0" + _thang ) : _thang ) + "-" + tuan_dayStart[i];
									String ngayketthuc = _nam + "-" + ( _thang < 10 ? ( "0" + _thang ) : _thang ) + "-" + tuan_dayEnd[i];
									
									int startIndex = 0;
									if( i > 0 )
									{
										for( int j = 0; j < i; j++ )
											startIndex += solenh_tuan[j];
									}
									
									int endIndex = startIndex + solenh_tuan[i];
									
									query = "  update lsx  " + 
											"  	set lsx.NGAYBATDAU = '" + ngaybatdau + "', lsx.NGAYKETTHUC = '" + ngayketthuc + "' " + 
											"  from ERP_LENHSANXUATDUKIEN lsx inner join " + 
											"  ( " + 
											"  	select ROW_NUMBER() OVER(ORDER BY pk_seq ASC) AS Row, PK_SEQ  " + 
											"  	from ERP_LENHSANXUATDUKIEN  " + 
											"  	where SANPHAM_FK = '" + spId + "' and Year( NGAYBATDAU ) = '" + _nam + "' and Month(NGAYBATDAU) = '" + _thang + "' " + 
											"  ) " + 
											"  sort on lsx.PK_SEQ = sort.PK_SEQ " + 
											"  where sort.Row > " + startIndex + " and sort.Row <= " + endIndex ;
									
									System.out.print(":::: CAP NHAT NGAY: " + query);
									this.db.update(query);
								}
							}
							
						}
						rs.close();
					}
				}
				
				//Những SP gia công thì không có nhà máy
				query = "SELECT SANPHAM_FK as SPID, Month(NGAYBATDAU) as thang, Year( NGAYBATDAU ) as nam, count( pk_seq ) as solenh \n " +
						"FROM ERP_LENHSANXUATDUKIEN  \n " +
						"WHERE NGAYKETTHUC >= '" + this.getDateTime() + "'  \n " +
								"AND LOAIHANGHOA = '2'  \n " +
						"group by  SANPHAM_FK, Month(NGAYBATDAU), Year( NGAYBATDAU ) " +
						"ORDER BY  Year( NGAYBATDAU ), Month(NGAYBATDAU), SANPHAM_FK ";
				System.out.println(":: CHIA THEO TUAN - GIA CONG: " + query);

				ResultSet rs = this.db.get(query);
				if( rs != null )
				{
					while(rs.next() )
					{
						String spId = rs.getString("SPID");
						int _thang = rs.getInt("thang");
						int _nam = rs.getInt("nam");
						int solenh = rs.getInt("solenh");
						
						String[] tuan_dayStart = new String[]{ "01", "08", "15", "22" };
						String[] tuan_dayEnd = new String[]{ "07", "14", "21", "28" };
						int[] solenh_tuan = this.Phanbolenh(1, solenh);
						
						for( int i = 0; i < solenh_tuan.length; i++ )
						{
							if( solenh_tuan[i] > 0 )
							{
								String ngaybatdau = _nam + "-" + ( _thang < 10 ? ( "0" + _thang ) : _thang ) + "-" + tuan_dayStart[i];
								String ngayketthuc = _nam + "-" + ( _thang < 10 ? ( "0" + _thang ) : _thang ) + "-" + tuan_dayEnd[i];
								
								int startIndex = 0;
								if( i > 0 )
								{
									for( int j = 0; j < i; j++ )
										startIndex += solenh_tuan[j];
								}
								
								int endIndex = startIndex + solenh_tuan[i];
								
								query = "  update lsx  " + 
										"  	set lsx.NGAYBATDAU = '" + ngaybatdau + "', lsx.NGAYKETTHUC = '" + ngayketthuc + "' " + 
										"  from ERP_LENHSANXUATDUKIEN lsx inner join " + 
										"  ( " + 
										"  	select ROW_NUMBER() OVER(ORDER BY pk_seq ASC) AS Row, PK_SEQ  " + 
										"  	from ERP_LENHSANXUATDUKIEN  " + 
										"  	where SANPHAM_FK = '" + spId + "' and Year( NGAYBATDAU ) = '" + _nam + "' and Month(NGAYBATDAU) = '" + _thang + "' " + 
										"  ) " + 
										"  sort on lsx.PK_SEQ = sort.PK_SEQ " + 
										"  where sort.Row > " + startIndex + " and sort.Row <= " + endIndex ;
								
								System.out.print(":::: CAP NHAT NGAY: " + query);
								this.db.update(query);
							}
						}
						
					}
					rs.close();
				}
				
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	
		private int[] Phanbolenh( int minday, int solenh )
		{
			//Hàm phân bổ đều số lệnh sản xuất / tuần trong 1 tháng
			int[] tuan = new int[]{ 0, 0, 0, 0 };
			
			if( solenh <= 4 )
			{
				for( int i = 0; i < solenh; i++ )
				{
					tuan[i] = 1;
				}
			}
			else
			{
				//Ví dụ 10 lệnh sẽ chia tương ứng: 3 3 2 2
				tuan = new int[]{ 1, 1, 1, 1 };
				int index = 0;
				for( int i = 4; i < solenh; i++ )
				{
					tuan[index] += 1;
					index ++;
					
					if( ( i + 1 ) % 4 == 0  )
						index = 0;
				}
			}
			
			return tuan;
			
		}
		
		public static void main(String[] arg)
		{
			Dubaokinhdoanh_Giay db = new Dubaokinhdoanh_Giay();
			
			int[] kq = db.Phanbolenh(1, 5);
			for( int i = 0; i < kq.length; i++ )
			{
				System.out.println(":: TUAN: " + ( i + 1 ) + " -- Số lệnh: " + kq[i] );
			}
			
		}
		
		private void TaoKeHoachSanXuat(String thang, String nam)
		{
//				String query = "SELECT PK_SEQ AS NMID FROM ERP_NHAMAY WHERE TRANGTHAI = 1";
			String nmId = "";
			int sodaytruyen = 0;
			
			try{
				String	query = "SELECT PK_SEQ AS ID, SANPHAM_FK as SPID, NGAYBATDAU, SOLUONG, TRUCTHUOC_FK \n " +
								"FROM ERP_LENHSANXUATDUKIEN  \n " +
								"WHERE NGAYBATDAU >= '" + this.getDateTime() + "' AND NGAYBATDAU <= '2016-06-22' \n " +
								"ORDER BY  NGAYBATDAU ASC, SOLUONG DESC ";	
						
				ResultSet rs = this.db.get(query);
				if(rs != null){
					while(rs.next()){
						String lsxId = rs.getString("ID");
						String loai = "1";
						String tructhuoc = rs.getString("TRUCTHUOC_FK"); 
						String option1 = this.getDayTruyenSX_LSXDK_1(lsxId) ;
						String option2 = this.getDayTruyenSX_LSXDK_2(lsxId) ;								
								
						System.out.println("option 1:" + option1);
						System.out.println("option 2:" + option2);
								
						if(option2.length() == 0 & option1.length() > 0){
							String[] d = option1.split(";");
							String dtsxId = d[0];
							String ngaybatdau = d[1];  	// đây là ngày kết thúc của LSX trước đó trên dây truyền. Ngày này sẽ là ngày bắt đầu cho LSX mới này
							String tugioSX = d[2];   	// đây là giờ kết thúc của LSX trước đó trên dây truyền. Giờ này sẽ là giờ bắt đầu cho LSX mới này
							String thoigianSX = d[3];	// đây là thời gian sx của lệnh sx
							String thoigianVS = d[4];   // đây là thời gian vệ sinh sau sx
							String BDgiolamviec = d[5];
							String KTgiolamviec = d[6];
							String BDnghitrua = d[7];
							String KTnghitrua = d[8];
									
							if(Long.parseLong(tugioSX) >= Long.parseLong(KTgiolamviec)){
								ngaybatdau = this.getNgayketiep(ngaybatdau, 1);
								tugioSX = BDgiolamviec;
							}
									
							TinhKHSX(lsxId, tructhuoc, loai, dtsxId, ngaybatdau, "", tugioSX, thoigianSX, thoigianVS, BDgiolamviec, KTgiolamviec, BDnghitrua, KTnghitrua );
	
						}else if(option2.length() > 0){
							String[] d = option2.split(";");
							String dtsxId = d[0];
							String ngaybatdau = d[1];  	// đây là ngày kết thúc của LSX trước đó trên dây truyền. Ngày này sẽ là ngày bắt đầu cho LSX mới này
							String tugioSX = d[2];   	// đây là giờ kết thúc của LSX trước đó trên dây truyền. Giờ này sẽ là giờ bắt đầu cho LSX mới này
							String thoigianSX = d[3];	// đây là thời gian sx của lệnh sx
							String thoigianVS = d[4];   // đây là thời gian vệ sinh sau sx
							String BDgiolamviec = d[5];
							String KTgiolamviec = d[6];
							String BDnghitrua = d[7];
							String KTnghitrua = d[8];

							if(Long.parseLong(tugioSX) >= Long.parseLong(KTgiolamviec)){
								ngaybatdau = this.getNgayketiep(ngaybatdau, 1);
								tugioSX = BDgiolamviec;
							}

							TinhKHSX_SS(lsxId, tructhuoc, loai, dtsxId, ngaybatdau, "", tugioSX, thoigianSX, thoigianVS, BDgiolamviec, KTgiolamviec, BDnghitrua, KTnghitrua );
									
						}
								
					}
					rs.close();
				}
						
				
			}catch(java.sql.SQLException e){}

		}
	
		private void TinhKHSX(String lsxId, String tructhuoc, String loai, String dtsxId, String ngaybatdau, String ngaytiepnhan, String tugioSX, String thoigianSX, String thoigianVS, String BDgiolamviec, String KTgiolamviec, String BDnghitrua, String KTnghitrua ){
			try{
				String query = "";
				long SX_tu = Long.parseLong(tugioSX);
				double SX_tg = Double.parseDouble(thoigianSX);
				double VS_tg = Double.parseDouble(thoigianVS);
				long gioBD = Long.parseLong(BDgiolamviec);
				long gioKT = Long.parseLong(KTgiolamviec);
				long nghitruaBD = Long.parseLong(BDnghitrua);
				long nghitruaKT = Long.parseLong(KTnghitrua);
				String ngayBD = "";
				String ngayKT = "";
				String ngayTN = "";
				long dengioSX = 0;
				long dengioVS = 0;

				dengioSX = SX_tu + (SX_tg*2 > Math.round(SX_tg*2)?(Math.round(SX_tg*2) + 1): Math.round(SX_tg*2))+ (nghitruaKT - nghitruaBD);
				dengioVS = dengioSX + (VS_tg*2 > Math.round(VS_tg*2)?(Math.round(VS_tg*2) + 1): Math.round(VS_tg*2));

				if(dengioVS <= gioKT){
					ngayBD = ngaybatdau;
					ngayTN = ngaytiepnhan;
					ngayKT = (ngayBD.length() > 0)?ngayBD:ngayTN;
					query = "INSERT INTO ERP_KEHOACHSANXUAT " +
							"(LSXID, TRUCTHUOC, LOAI, DTSXID, NGAYBATDAU, TUGIO_SX, DENGIO_SX, THOIGIAN_SX, THOIGIAN_VS, DENGIO_VS, NGAYKETTHUC, NGAYTIEPNHAN) " +
							"VALUES(" + lsxId + ", " +  tructhuoc + ", " + loai + ", " + dtsxId + ", '" + ngayBD + "', " +
							"" + tugioSX + ", " + dengioSX + ", " + SX_tg + ", " + VS_tg + ", " + dengioVS + ", '" + ngayKT + "', '" + ngayTN + "') ";
					
					System.out.println("dengioVS <= gioKT: " + query);
					this.db.update(query);
					this.db.getConnection().commit();
					
				}else if(dengioSX <= gioKT & dengioVS > gioKT ){
					dengioVS = gioKT;
					ngayBD = ngaybatdau;
					ngayTN = ngaytiepnhan;
					ngayKT = "";
					
					query = "INSERT INTO ERP_KEHOACHSANXUAT " +
							"(LSXID, TRUCTHUOC, LOAI, DTSXID, NGAYBATDAU, TUGIO_SX, DENGIO_SX, THOIGIAN_SX, THOIGIAN_VS, DENGIO_VS, NGAYKETTHUC, NGAYTIEPNHAN) " +
							"VALUES(" + lsxId + ", " +  tructhuoc + ", " + loai + ", " + dtsxId + ", '" + ngayBD + "', " +
							"" + tugioSX + ", " + dengioSX + ", " + SX_tg + ", " + (gioKT - dengioSX)*0.5 + ", " + dengioVS + ", '" + ngayKT + "', '" + ngayTN + "' ) ";
					
					System.out.println("dengioSX <= gioKT & dengioVS > gioKT: " + query);
					this.db.update(query);
					this.db.getConnection().commit();
					
					SX_tg = 0;
					VS_tg = VS_tg - (gioKT - dengioSX)*0.5;
					
					if(ngayBD.length() > 0){
						ngayTN = getNgayketiep(ngayBD, 1);
					}else{
						ngayTN = getNgayketiep(ngayTN, 1);
					}
					ngayBD = "";
					
					TinhKHSX(lsxId, tructhuoc, loai, dtsxId, ngayBD, ngayTN, "" + BDgiolamviec, "" + SX_tg, "" + VS_tg, BDgiolamviec, KTgiolamviec, BDnghitrua, KTnghitrua );
				
				}else if(dengioSX > gioKT  ){
					ngayBD = ngaybatdau;
					ngayTN = ngaytiepnhan;
					ngayKT = "";

					double tgSX = 0;
					
					if(SX_tu < nghitruaBD){
					
						tgSX = (gioKT - SX_tu - 2)*0.5;
						SX_tg = SX_tg - tgSX;
						
					}else if(SX_tu > nghitruaKT){
						
						tgSX = (gioKT - SX_tu)*0.5;
						SX_tg = SX_tg - tgSX;				
						
					}else if(SX_tu > nghitruaBD & SX_tu < nghitruaKT){

						tgSX = (gioKT - nghitruaKT)*0.5;
						SX_tg = SX_tg - tgSX;				
						
					}


					query = "INSERT INTO ERP_KEHOACHSANXUAT " +
							"(LSXID, TRUCTHUOC, LOAI, DTSXID, NGAYBATDAU, TUGIO_SX, DENGIO_SX, THOIGIAN_SX, THOIGIAN_VS, DENGIO_VS, NGAYKETTHUC, NGAYTIEPNHAN) " +
							"VALUES(" + lsxId + ", " +  tructhuoc + ", " + loai + ", " + dtsxId + ", '" + ngayBD + "', " +
							"" + tugioSX + ", " + gioKT + ", " + tgSX + ", 0, NULL, '', '" + ngayBD + "' ) ";
					
					System.out.println("dengioSX <= gioKT & dengioVS > gioKT: " + query);
					this.db.update(query);
					this.db.getConnection().commit();
					
					if(ngayBD.length() > 0){
						ngayTN = getNgayketiep(ngayBD, 1);
					}else{
						ngayTN = getNgayketiep(ngayTN, 1);
					}
					ngayBD = "";

					TinhKHSX(lsxId, tructhuoc, loai, dtsxId, ngayBD, ngayTN, BDgiolamviec, "" + SX_tg, "" + VS_tg, BDgiolamviec, KTgiolamviec, BDnghitrua, KTnghitrua );
				}
			}catch(java.sql.SQLException e){}
		}
			
		private void TinhKHSX_SS(String lsxId, String tructhuoc, String loai, String dtsxId, String ngaybatdau, String ngaytiepnhan, String tugioSX, String thoigianSX, String thoigianVS, String BDgiolamviec, String KTgiolamviec, String BDnghitrua, String KTnghitrua ){
			try{
				String query = "";
				String[] dtsxIds = dtsxId.split("-");
				long SX_tu = Long.parseLong(tugioSX);
				double SX_tg = Double.parseDouble(thoigianSX);
				double VS_tg = Double.parseDouble(thoigianVS);
				long gioBD = Long.parseLong(BDgiolamviec);
				long gioKT = Long.parseLong(KTgiolamviec);
				long nghitruaBD = Long.parseLong(BDnghitrua);
				long nghitruaKT = Long.parseLong(KTnghitrua);
				String ngayBD = "";
				String ngayKT = "";
				String ngayTN = "";
				long dengioSX = 0;
				long dengioVS = 0;
				
				dengioSX = SX_tu + (SX_tg*2 > Math.round(SX_tg*2)?(Math.round(SX_tg*2) + 1): Math.round(SX_tg*2))+ (nghitruaKT - nghitruaBD);
				dengioVS = dengioSX + (VS_tg*2 > Math.round(VS_tg*2)?(Math.round(VS_tg*2) + 1): Math.round(VS_tg*2));

				if(dengioVS <= gioKT){
					ngayBD = ngaybatdau;
					ngayTN = ngaytiepnhan;
					ngayKT = (ngayBD.length() > 0)?ngayBD:ngayTN;

					for(int i = 0; i < dtsxIds.length; i++){
						query = "INSERT INTO ERP_KEHOACHSANXUAT " +
								"(LSXID, TRUCTHUOC, LOAI, DTSXID, NGAYBATDAU, TUGIO_SX, DENGIO_SX, THOIGIAN_SX, THOIGIAN_VS, DENGIO_VS, NGAYKETTHUC, NGAYTIEPNHAN) " +
								"VALUES(" + lsxId + ", " +  tructhuoc + ", " + loai + ", " + dtsxIds[i] + ", '" + ngayBD + "', " +
								"" + tugioSX + ", " + dengioSX + ", " + SX_tg + ", " + VS_tg + ", " + dengioVS + ", '" + ngayKT + "', '" + ngayTN + "') ";
						
						System.out.println("dengioVS <= gioKT: " + query);
						this.db.update(query);
//						this.db.getConnection().commit();
					}
					
				}else if(dengioSX <= gioKT & dengioVS > gioKT ){
					dengioVS = gioKT;
					ngayBD = ngaybatdau;
					ngayTN = ngaytiepnhan;
					ngayKT = "";
					for(int i = 0; i < dtsxIds.length; i++){
					
						query = "INSERT INTO ERP_KEHOACHSANXUAT " +
								"(LSXID, TRUCTHUOC, LOAI, DTSXID, NGAYBATDAU, TUGIO_SX, DENGIO_SX, THOIGIAN_SX, THOIGIAN_VS, DENGIO_VS, NGAYKETTHUC, NGAYTIEPNHAN) " +
								"VALUES(" + lsxId + ", " +  tructhuoc + ", " + loai + ", " + dtsxIds[i] + ", '" + ngayBD + "', " +
								"" + tugioSX + ", " + dengioSX + ", " + SX_tg + ", " + (gioKT - dengioSX)*0.5 + ", " + dengioVS + ", '" + ngayKT + "', '" + ngayTN + "' ) ";
						
						System.out.println("dengioSX <= gioKT & dengioVS > gioKT: " + query);
						this.db.update(query);
//						this.db.getConnection().commit();
					}	
					
					SX_tg = 0;
					VS_tg = VS_tg - (gioKT - dengioSX)*0.5;
					
					if(ngayBD.length() > 0){
						ngayTN = getNgayketiep(ngayBD, 1);
					}else{
						ngayTN = getNgayketiep(ngayTN, 1);
					}
					ngayBD = "";
					
					TinhKHSX_SS(lsxId, tructhuoc, loai, dtsxId, ngayBD, ngayTN, "" + BDgiolamviec, "" + SX_tg, "" + VS_tg, BDgiolamviec, KTgiolamviec, BDnghitrua, KTnghitrua );
				}else if(dengioSX > gioKT  ){
					ngayBD = ngaybatdau;
					ngayTN = ngaytiepnhan;
					ngayKT = "";

					double tgSX = 0;
					
					if(SX_tu < nghitruaBD){
					
						tgSX = (gioKT - SX_tu - 2)*0.5;
						SX_tg = SX_tg - tgSX;
						
					}else if(SX_tu > nghitruaKT){
						
						tgSX = (gioKT - SX_tu)*0.5;
						SX_tg = SX_tg - tgSX;				
						
					}else if(SX_tu > nghitruaBD & SX_tu < nghitruaKT){

						tgSX = (gioKT - nghitruaKT)*0.5;
						SX_tg = SX_tg - tgSX;				
						
					}

					for(int i = 0; i < dtsxIds.length; i++){

						query = "INSERT INTO ERP_KEHOACHSANXUAT " +
								"(LSXID, TRUCTHUOC, LOAI, DTSXID, NGAYBATDAU, TUGIO_SX, DENGIO_SX, THOIGIAN_SX, THOIGIAN_VS, DENGIO_VS, NGAYKETTHUC, NGAYTIEPNHAN) " +
								"VALUES(" + lsxId + ", " +  tructhuoc + ", " + loai + ", " + dtsxIds[i] + ", '" + ngayBD + "', " +
								"" + tugioSX + ", " + gioKT + ", " + tgSX + ", 0, NULL, '', '" + ngayBD + "' ) ";
						
						System.out.println("dengioSX <= gioKT & dengioVS > gioKT: " + query);
						this.db.update(query);
//						this.db.getConnection().commit();
					}
					
					if(ngayBD.length() > 0){
						ngayTN = getNgayketiep(ngayBD, 1);
					}else{
						ngayTN = getNgayketiep(ngayTN, 1);
					}
					ngayBD = "";

					TinhKHSX_SS(lsxId, tructhuoc, loai, dtsxId, ngayBD, ngayTN, BDgiolamviec, "" + SX_tg, "" + VS_tg, BDgiolamviec, KTgiolamviec, BDnghitrua, KTnghitrua );
				}
				this.db.getConnection().commit();
			}catch(java.sql.SQLException e){}
		}

			String getNgayketiep(String ngaybatdau, int num){
				String nbd = ngaybatdau;
				String query = 	"SELECT \n " +
								"CASE WHEN DATENAME(DW, CONVERT(VARCHAR(10), DATEADD(DAY, 1, '" + ngaybatdau + "'), 120)) = 'Sunday'  \n " + 
								"THEN CONVERT(VARCHAR(10), DATEADD(DAY, " + (num + 1) + ", '" + ngaybatdau + "'), 120) \n " +
								"ELSE CONVERT(VARCHAR(10), DATEADD(DAY, " + num + ", '" + ngaybatdau + "'), 120) END AS NGAYBATDAU " ;
				
				ResultSet rs = this.db.get(query);
				try{
					rs.next();
					nbd = rs.getString("NGAYBATDAU");
				}catch(java.sql.SQLException e){}
				return nbd;	
			}
			
			private String getDayTruyenSX_LSXDK_1(String lsxId){
				String tmp = "";
				String query = 	
							"SELECT * " + 
							"FROM( " +
	
								"SELECT LSXDK.PK_SEQ AS ID, LSXDK.TRUCTHUOC_FK AS TRUCTHUOC, LSXDK.SANPHAM_FK as SPID, \n " + 
								"(LSXDK.SOLUONG - LSXDK.SANXUAT) AS SOLUONG, 1 AS LOAI,  \n " +
								"TTDH_DTSX.DAYTRUYENSX_FK AS DTSXID,  \n " +
								"DTSX.TUGIO AS GIOBATDAU, DTSX.DENGIO AS GIORAVE, DTSX.BDNGHITRUA, DTSX.KTNGHITRUA, \n " +
						
								"CASE WHEN TTDH.LOTSIZE = (LSXDK.SOLUONG - LSXDK.SANXUAT)  \n " +
								"THEN TTDH_DTSX.THOIGIAN \n " +
								"ELSE LSXDK.SOLUONG*TTDH_DTSX.THOIGIAN/TTDH.LOTSIZE END AS THOIGIANSX, \n " + 
								"TTDH.CLEANUP AS THOIGIANVS, \n " +
								
								"ISNULL( \n " +
								"( \n " +
								"	SELECT  MAX(DENGIO_VS)AS DENGIO \n " +
								"	FROM ERP_KEHOACHSANXUAT \n " +
								"	WHERE NGAYKETTHUC =  \n " +
								"	( \n " +
								"		SELECT MAX(NGAYKETTHUC) \n " +
								"		FROM ERP_KEHOACHSANXUAT  \n " +
								"		WHERE DTSXID = DTSX.PK_SEQ \n " +
								"		GROUP BY DTSXID \n " +
								"	) \n " +
								"), DTSX.TUGIO) AS GIOKETTHUC, \n " +		
								
								"ISNULL( \n " +
								"( \n " +
								"		SELECT MAX(NGAYKETTHUC) \n " +
								"		FROM ERP_KEHOACHSANXUAT  \n " +
								"		WHERE DTSXID = DTSX.PK_SEQ \n " +
								"		GROUP BY DTSXID \n " +
								"), '" + this.getDateTime() + "') AS NGAYKETTHUC \n " +
									
								"FROM ERP_LENHSANXUATDUKIEN LSXDK \n " +
								"INNER JOIN ERP_THONGTINDATHANG TTDH ON TTDH.SANPHAM_FK = LSXDK.SANPHAM_FK \n " +
								"INNER JOIN ERP_THONGTINDATHANG_DAYTRUYENSX TTDH_DTSX ON TTDH_DTSX.THONGTINDATHANG_FK = TTDH.PK_SEQ AND TTDH_DTSX.LOAI = 1 \n " +
								"INNER JOIN ERP_DAYTRUYENSANXUAT DTSX ON DTSX.PK_SEQ = TTDH_DTSX.DAYTRUYENSX_FK \n " +
								"WHERE LSXDK.PK_SEQ = " + lsxId + " \n " +
							")DATA	ORDER BY (NGAYKETTHUC + ';' + CONVERT(VARCHAR, GIOKETTHUC)) ASC ";
					System.out.println(query);
					ResultSet rs = this.db.get(query);
					if(rs != null){
						try{
							rs.next();
							if(rs.getString("NGAYKETTHUC").length() > 0){
								tmp = rs.getString("DTSXID") + ";" + rs.getString("NGAYKETTHUC") + ";" + rs.getString("GIOKETTHUC") + ";" + rs.getString("THOIGIANSX")+ ";" + 
									  "" + rs.getString("THOIGIANVS") + ";" + rs.getString("GIOBATDAU") + ";" + rs.getString("GIORAVE") + ";" + 
									  "" + rs.getString("BDNGHITRUA") + ";" + rs.getString("KTNGHITRUA") + "";
							}			
							rs.close();
						}catch(java.sql.SQLException e){}
					}
					return tmp;
			}
	
			private String getDayTruyenSX_LSXDK_2(String lsxId)
			{
				String tmp = "";
				String query = 	
						"SELECT * FROM ( \n " +
							"SELECT LSXDK.PK_SEQ AS ID, LSXDK.TRUCTHUOC_FK AS TRUCTHUOC, LSXDK.SANPHAM_FK as SPID, \n " + 
							"(LSXDK.SOLUONG - LSXDK.SANXUAT) AS SOLUONG, 1 AS LOAI,  \n " +
							"TTDH_DTSX.DAYTRUYENSX_FK AS DTSXID,  \n " +
							"DTSX.TUGIO AS GIOBATDAU, DTSX.DENGIO AS GIORAVE, DTSX.BDNGHITRUA, DTSX.KTNGHITRUA, \n " +

							"CASE WHEN TTDH.LOTSIZE = (LSXDK.SOLUONG - LSXDK.SANXUAT)  \n " +
							"THEN TTDH_DTSX.THOIGIAN \n " +
							"ELSE LSXDK.SOLUONG*TTDH_DTSX.THOIGIAN/TTDH.LOTSIZE END AS THOIGIANSX, \n " + 
							"TTDH.CLEANUP AS THOIGIANVS, \n " +
							"ISNULL( \n "	+
							"( \n " +
							"	SELECT  MAX(DENGIO_VS)AS DENGIO \n " +
							"	FROM ERP_KEHOACHSANXUAT \n " +
							"	WHERE NGAYKETTHUC =  \n " +
							"	( \n " +
							"		SELECT MAX(NGAYKETTHUC) \n " +
							"		FROM ERP_KEHOACHSANXUAT  \n " +
							"		WHERE DTSXID = DTSX.PK_SEQ \n " +
							"		GROUP BY DTSXID \n " +
							"	)" +
							"), DTSX.TUGIO) \n " +
							"GIOKETTHUC, \n " +		
							
							"ISNULL( \n " +
							"( \n " +
							"		SELECT MAX(NGAYKETTHUC) \n " +
							"		FROM ERP_KEHOACHSANXUAT  \n " +
							"		WHERE DTSXID = DTSX.PK_SEQ \n " +
							"		GROUP BY DTSXID \n " +
							"), '" + this.getDateTime() +  "') \n " +
							"  AS NGAYKETTHUC \n " +
									
							"FROM ERP_LENHSANXUATDUKIEN LSXDK \n " +
							"INNER JOIN ERP_THONGTINDATHANG TTDH ON TTDH.SANPHAM_FK = LSXDK.SANPHAM_FK \n " +
							"INNER JOIN ERP_THONGTINDATHANG_DAYTRUYENSX TTDH_DTSX ON TTDH_DTSX.THONGTINDATHANG_FK = TTDH.PK_SEQ AND TTDH_DTSX.LOAI = 2 \n " +
							"INNER JOIN ERP_DAYTRUYENSANXUAT DTSX ON DTSX.PK_SEQ = TTDH_DTSX.DAYTRUYENSX_FK \n " +
							"WHERE LSXDK.PK_SEQ = " + lsxId + " \n " +
						")DATA	ORDER BY (NGAYKETTHUC + ';' + CONVERT(VARCHAR, GIOKETTHUC)) DESC ";
			System.out.println(query);
			ResultSet rs = this.db.get(query);
			if(rs != null){
				try{
					String daytruyen = "";
					String ngaybatdau = "";
					String giobatdau = "";
					String thoigianSX = "";
					String thoigianVS = "";
					String gioBD = "";
					String gioKT = "";
					String nghitruaBD = "";
					String nghitruaKT = "";
					
					boolean flag = true;
					while(rs.next()){
						if(daytruyen.length() > 0){
							daytruyen += "-" + rs.getString("DTSXID");
						}else{
							daytruyen += rs.getString("DTSXID");
						}
						
						if(ngaybatdau.length() == 0){	
							ngaybatdau = rs.getString("NGAYKETTHUC");
							giobatdau = rs.getString("GIOKETTHUC");
							thoigianSX = rs.getString("THOIGIANSX");
							thoigianVS = rs.getString("THOIGIANVS");
							gioBD = rs.getString("GIOBATDAU");
							gioKT = rs.getString("GIORAVE");
							nghitruaBD = rs.getString("BDNGHITRUA");
							nghitruaKT = rs.getString("KTNGHITRUA");
						}else{
							if(!ngaybatdau.equals(rs.getString("NGAYKETTHUC"))){
							
								flag = false;
							}
						}
					}
					if(flag & ngaybatdau.length() > 0){
						
						tmp = daytruyen + ";" + ngaybatdau + ";" + giobatdau + ";" + thoigianSX+ ";" + 
							  "" + thoigianVS + ";" + gioBD + ";" + gioKT + ";" + 
							  "" + nghitruaBD + ";" + nghitruaKT + "";

					}
				}catch(java.sql.SQLException e){}
			}
			return tmp;
		}
	
		private void TaoLenhSanXuatDK_BTP(String thang, String nam)
		{
			String query;
			ResultSet SPrs;
			ResultSet BTPrs;
			
			String t0 = "", t1 = "", t2 = "", t3 = "", t4 = ""; 
			String n0 = "", n1 = "", n2 = "", n3 = "", n4 = "";
			t1 = thang;
			n1 = nam;
			
			if(t1.equals("1")){
				t0 = "12";
				n0 = "" + (Integer.parseInt(n1) - 1);
			}else{
				t0 = "" + (Integer.parseInt(t1) - 1);
				n0 = n1;
			}
			
			if(t1.equals("12")){
				t2 = "1";
				n2 = "" + (Integer.parseInt(n1) + 1);
			}else{
				t2 = "" + (Integer.parseInt(t1) + 1);
				n2 = n1;
			}
			
			if(t2.equals("12")){
				t3 = "1";
				n3 = "" + (Integer.parseInt(n2) + 1);
			}else{
				t3 = "" + (Integer.parseInt(t2) + 1);
				n3 = n2;
			}
			
			if(t3.equals("12")){
				t4 = "1";
				n4 = "" + (Integer.parseInt(n3) + 1);
			}else{
				t4 = "" + (Integer.parseInt(t3) + 1);
				n4 = n3;
			}			
			
			query = "DELETE ERP_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU \n " +
					"WHERE LENHSANXUATDUKIEN_FK IN (SELECT PK_SEQ FROM ERP_LENHSANXUATDUKIEN WHERE TRUCTHUOC_FK IS NOT NULL AND SUBSTRING(NGAYBATDAU, 1, 7) >= '" + n1 + "-" + (t1.length() == 1?("0" + t1):t1) + "') " ;
			System.out.println(query);
			this.db.update(query);
			
			query = "DELETE ERP_LENHSANXUATDUKIEN WHERE TRUCTHUOC_FK IS NOT NULL AND SUBSTRING(NGAYBATDAU, 1, 7) >= '" + n1 + "-" + (t1.length() == 1?("0" + t1):t1) + "' " ;
			System.out.println(query);
			this.db.update(query);
			
			query = 
					"SELECT PK_SEQ, SANPHAM_FK, SOLUONG, NGAYBATDAU, NGAYKETTHUC \n " +
					"FROM ERP_LENHSANXUATDUKIEN  \n " +
					"WHERE NGAYBATDAU >= '" + n0 + "-" + (t0.length() == 1?("0" + t0):t0) + "-01'  \n " +
					"AND NGAYKETTHUC <= '" + n0 + "-" + (t0.length() == 1?("0" + t0):t0) + "-31'  \n " +
					"AND CONGTY_FK = '" + this.CTYID + "' AND SOLUONG > 0  \n " +
			
					"UNION ALL  \n " + 
					"SELECT PK_SEQ, SANPHAM_FK, SOLUONG, NGAYBATDAU, NGAYKETTHUC \n " +
					"FROM ERP_LENHSANXUATDUKIEN  \n " +
					"WHERE NGAYBATDAU >= '" + n1 + "-" + (t1.length() == 1?("0" + t1):t1) + "-01'  \n " +
					"AND NGAYKETTHUC <= '" + n1 + "-" + (t1.length() == 1?("0" + t1):t1) + "-31'  \n " +
					"AND CONGTY_FK = '" + this.CTYID + "' AND SOLUONG > 0  \n " +
					
					"UNION ALL  \n " + 
					"SELECT PK_SEQ, SANPHAM_FK, SOLUONG, NGAYBATDAU, NGAYKETTHUC \n " +
					"FROM ERP_LENHSANXUATDUKIEN  \n " +
					"WHERE NGAYBATDAU >= '" + n2 + "-" + (t2.length() == 1?("0" + t2):t2) + "-01'  \n " +
					"AND NGAYKETTHUC <= '" + n2 + "-" + (t2.length() == 1?("0" + t2):t2) + "-31'  \n " +
					"AND CONGTY_FK = '" + this.CTYID + "' AND SOLUONG > 0  \n " +
					
					"UNION ALL  \n " +
					"SELECT PK_SEQ, SANPHAM_FK, SOLUONG, NGAYBATDAU, NGAYKETTHUC \n " +
					"FROM ERP_LENHSANXUATDUKIEN  \n " +
					"WHERE NGAYBATDAU >= '" + n3 + "-" + (t3.length() == 1?("0" + t3):t3) + "-01'  \n " +
					"AND NGAYKETTHUC <= '" + n3 + "-" + (t3.length() == 1?("0" + t3):t3) + "-31'  \n " +
					"AND CONGTY_FK = '" + this.CTYID + "' AND SOLUONG > 0  \n " +
					
					"UNION ALL  \n " +
					"SELECT PK_SEQ, SANPHAM_FK, SOLUONG, NGAYBATDAU, NGAYKETTHUC  \n " +
					"FROM ERP_LENHSANXUATDUKIEN  \n " +
					"WHERE NGAYBATDAU >= '" + n4 + "-" + (t4.length() == 1?("0" + t4):t4) + "-01'  \n " +
					"AND NGAYKETTHUC <= '" + n4 + "-" + (t4.length() == 1?("0" + t4):t4) + "-31'  \n " +
					"AND CONGTY_FK = '" + this.CTYID + "' AND SOLUONG > 0  \n " ;
			
			System.out.println(query);
					
			SPrs = this.db.get(query);
					
			try{
				if(SPrs != null){
					while(SPrs.next()){
						String Id = SPrs.getString("PK_SEQ");
							
						String spId = SPrs.getString("SANPHAM_FK");
						
						double soluongTP = SPrs.getDouble("SOLUONG");
						
						String ngayketthuc = SPrs.getString("NGAYBATDAU");
						String ngaybatdau = SPrs.getString("NGAYBATDAU");
						
						if(soluongTP > 0) {
											
							// Lay ra yeu cau nguyen lieu cho 1 lenh san xuat du kien
							BTPrs = getDanhmucBTP(spId, soluongTP);    
									
							if(BTPrs != null)
							{
								while(BTPrs.next())							
								{
									double soluong = BTPrs.getDouble("SOLUONG");
									double lotsize = BTPrs.getDouble("LOTSIZE");
									
									query = "DELETE ERP_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU WHERE LENHSANXUATDUKIEN_FK = " + Id + "";
									this.db.update(query);
									
									query = "INSERT INTO ERP_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU(LENHSANXUATDUKIEN_FK, NGUYENLIEU_FK, YEUCAU) \n " +
											"VALUES(" + Id + ", " + BTPrs.getString("BTPID") + ", " + soluong + ") ";
			
									this.db.update(query);
									
									if(lotsize > 0){
										if(soluong >= lotsize){
											double conlai = soluong;
											while (conlai >= lotsize){
												
												query = "INSERT INTO ERP_LENHSANXUATDUKIEN (TRUCTHUOC_FK, SANPHAM_FK, SOLUONG, SANXUAT, NGAYBATDAU, NGAYKETTHUC, \n " +
														"NGAYTAO, NGUOITAO, NGAYSUA, NGUOISUA, CONGTY_FK)  \n " +
														"VALUES(" + Id + ", " + BTPrs.getString("BTPID") + ", " + lotsize + ", 0, '" + ngaybatdau + "', " +
														"'" + ngayketthuc + "', " +
														"'" + this.getDateTime() + "', '" + this.USERID + "', '" + this.getDateTime() + "', '" + this.USERID + "', " + this.CTYID + ")";
												//System.out.println(query);
												this.db.update(query);
											
												
												conlai = conlai - lotsize;
											}
											
											if(conlai > 0){
												
												query = "INSERT INTO ERP_LENHSANXUATDUKIEN (TRUCTHUOC_FK, SANPHAM_FK, SOLUONG, SANXUAT, NGAYBATDAU, NGAYKETTHUC, \n " +
														"NGAYTAO, NGUOITAO, NGAYSUA, NGUOISUA, CONGTY_FK)  \n " +
														"VALUES(" + Id + ", " + BTPrs.getString("BTPID") + ", " + lotsize + ", 0, '" + ngaybatdau + "', " +
														"'" + ngayketthuc + "', " +
														"'" + this.getDateTime() + "', '" + this.USERID + "', '" + this.getDateTime() + "', '" + this.USERID + "', " + this.CTYID + ")";
												//System.out.println(query);
												this.db.update(query);
											
											}
										}else{
											
											query = "INSERT INTO ERP_LENHSANXUATDUKIEN (TRUCTHUOC_FK, SANPHAM_FK, SOLUONG, SANXUAT, NGAYBATDAU, NGAYKETTHUC, \n " +
													"NGAYTAO, NGUOITAO, NGAYSUA, NGUOISUA, CONGTY_FK)  \n " +
													"VALUES(" + Id + ", " + BTPrs.getString("BTPID") + ", " + lotsize + ", 0, '" + ngaybatdau + "', " +
													"'" + ngayketthuc + "', " +
													"'" + this.getDateTime() + "', '" + this.USERID + "', '" + this.getDateTime() + "', '" + this.USERID + "', " + this.CTYID + ")";
											//System.out.println(query);
											this.db.update(query);
										
											
										}
									}else{
										
										query = "INSERT INTO ERP_LENHSANXUATDUKIEN (TRUCTHUOC_FK, SANPHAM_FK, SOLUONG, SANXUAT, NGAYBATDAU, NGAYKETTHUC, \n " +
												"NGAYTAO, NGUOITAO, NGAYSUA, NGUOISUA, CONGTY_FK)  \n " +
												"VALUES(" + Id + ", " + BTPrs.getString("BTPID") + ", " + soluong + ", 0, '" + ngaybatdau + "', " +
												"'" + ngayketthuc + "', " +
												"'" + this.getDateTime() + "', '" + this.USERID + "', '" + this.getDateTime() + "', '" + this.USERID + "', " + this.CTYID + ")";
										//System.out.println(query);
										this.db.update(query);
									
										
									}
									
									
								}
								BTPrs.close();								
							}	
							
						}
					}
					SPrs.close();	
				}
							
				
				query = "SELECT * \n " +
						"FROM \n " +
						"( \n " +
						"	SELECT '0' AS LOAI, SANPHAM_FK, " + t0 + " AS THANG, " + n0 + " AS NAM, SUM(ISNULL(TONANTOAN, 0) - ISNULL(AVAILABLE, 0)) AS SOLUONG, 0 AS ID \n " +
						"	FROM ERP_KHOTT_SANPHAM KHOSP \n " +
						"	INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = KHOSP.SANPHAM_FK \n " +
						"	WHERE SP.LOAISANPHAM_FK IN (1100004, 100005, 100006, 100007) \n " +
	//					"	AND KHOSP.KHOTT_FK IN (100005, 100006, 100008, 100009) \n " +
						"	GROUP BY SANPHAM_FK \n " +
						
						"	UNION ALL \n " +
						"	SELECT '1' AS LOAI, LSXDK_YC.NGUYENLIEU_FK AS SANPHAM_FK, CONVERT(INT, SUBSTRING(LSXDK.NGAYBATDAU, 6, 2)) AS THANG,  \n " +
						"	SUBSTRING(LSXDK.NGAYBATDAU, 0, 5) AS NAM, SUM(YEUCAU) AS SOLUONG, LSXDK.PK_SEQ AS ID  \n " +
						"	FROM ERP_LENHSANXUATDUKIEN LSXDK  \n " +
						"	INNER JOIN ERP_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU LSXDK_YC ON LSXDK.PK_SEQ = LSXDK_YC.LENHSANXUATDUKIEN_FK \n " +
						"	INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = LSXDK_YC.NGUYENLIEU_FK \n " +
						"	WHERE SP.LOAISANPHAM_FK IN (1100004, 100005, 100006, 100007)  \n " +
						"	GROUP BY LSXDK_YC.NGUYENLIEU_FK, LSXDK.NGAYBATDAU, LSXDK.PK_SEQ \n " +
						
						"	UNION ALL \n " +
						"	SELECT '2' AS LOAI, LSXDK.SANPHAM_FK, CONVERT(INT, SUBSTRING(LSXDK.NGAYBATDAU, 6, 2)) AS THANG, \n " +
						"	SUBSTRING(LSXDK.NGAYBATDAU, 0, 5) AS NAM, SOLUONG, LSXDK.PK_SEQ AS ID \n " +
						"	FROM ERP_LENHSANXUATDUKIEN LSXDK \n " +
						"	INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = LSXDK.SANPHAM_FK \n " +
						"	WHERE SP.LOAISANPHAM_FK IN (1100004, 100005, 100006, 100007)  \n " +
						
						" 	UNION ALL \n " +	
						" 	SELECT	'3' AS LOAI, LSXSP.SANPHAM_FK, SUBSTRING(NGAYDUKIENHT, 6, 2), SUBSTRING(NGAYDUKIENHT, 1, 4), (ISNULL(LSXSP.SOLUONG,0) -  ISNULL(NH.NHANHANG, 0)), LSX.PK_SEQ AS ID \n " + 	
						"	FROM ERP_LENHSANXUAT_GIAY LSX \n " +
						"	INNER JOIN ERP_LENHSANXUAT_SANPHAM LSXSP ON LSXSP.LENHSANXUAT_FK = LSX.PK_SEQ \n " + 
						"	INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = LSXSP.SANPHAM_FK \n " +
						"	LEFT JOIN( \n " +
						"		SELECT NK.SOLENHSANXUAT, NKSP.SANPHAM_FK, SUM(ISNULL(NKSP.SOLUONGNHAP, 0)) AS NHANHANG \n " + 
						"		FROM ERP_NHAPKHO NK  \n " +
						"		INNER JOIN ERP_NHAPKHO_SANPHAM NKSP ON NKSP.SONHAPKHO_FK = NK.PK_SEQ \n " + 
						"		WHERE NK.TRANGTHAI = 1 \n " +
	//					"		AND NK.KHONHAP IN(100005, 100006)   \n " +
						"		GROUP BY NKSP.SANPHAM_FK, NK.SOLENHSANXUAT  \n " +
						"	)NH ON NH.SANPHAM_FK = LSXSP.SANPHAM_FK AND NH.SOLENHSANXUAT = LSX.PK_SEQ \n " + 
						"	WHERE \n " +
						"	((ISNULL(LSX.SOLUONG,0)) -  ISNULL(NH.NHANHANG, 0)) > 0 \n " + 
						"	AND SP.LOAISANPHAM_FK IN (1100004, 100005, 100006, 100007)  \n " +
						
						")DATA  \n " +
						"WHERE (NAM > " + n0 + " or (THANG >= " + t0 + " and NAM = " + n0 + ")) \n  " +
						"AND SANPHAM_FK IN (  \n  " +
						"		 	SELECT DISTINCT LSXDK_YC.NGUYENLIEU_FK   \n  " +
						"		 	FROM ERP_LENHSANXUATDUKIEN LSXDK   \n  " +
						"		 	INNER JOIN ERP_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU LSXDK_YC ON LSXDK.PK_SEQ = LSXDK_YC.LENHSANXUATDUKIEN_FK  \n  " +
						"		 	INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = LSXDK_YC.NGUYENLIEU_FK  \n  " +
						"			WHERE SP.LOAISANPHAM_FK IN (1100004, 100005, 100006, 100007)  \n " +
						"		 ) \n  " +
						"ORDER BY SANPHAM_FK, CONVERT(INT, NAM), CONVERT(INT, THANG), LOAI" ;
			
				System.out.println(query);
				ResultSet rs = this.db.get(query);
				if(rs != null){
						String list = "";
						double yeucau = 0;
						double yc = 0;
						String spId = "", spId_bk = "";
						String lastID_TP = "";
						String lastID_BTP = "";
						while(rs.next()){
							spId = rs.getString("SANPHAM_FK");
							if(!spId.equals(spId_bk)){
								yeucau = 0;
								spId_bk = spId;
								lastID_TP = "";
								lastID_BTP = "";
							}
							
							// LOẠI 0: Tồn kho		LOẠI 1: Yêu cầu BTP
							if(rs.getString("LOAI").equals("0") || rs.getString("LOAI").equals("1") ){
								yeucau += (-1)*rs.getDouble("SOLUONG");
			
								if(rs.getString("LOAI").equals("1")){
									if(lastID_TP.length() == 0){
										lastID_TP = rs.getString("ID");
									}else{
										lastID_TP += "," + rs.getString("ID");
									}
								}
								
							}else{ // Loại 2: LSX dự kiến, Loai:3 Lệnh SX
								if(yeucau < 0.1*rs.getDouble("SOLUONG")){
									
									if(lastID_BTP.length() > 0){
										query = "SELECT SOLUONG " +
												"FROM ERP_LENHSANXUATDUKIEN WHERE PK_SEQ = " + lastID_BTP + "";
										System.out.println("LastID_BTP:" + query);
										ResultSet rs_ = this.db.get(query);
										rs_.next();
										double sl = rs_.getDouble("SOLUONG");
										rs_.close();
										
										String tmp1 = "", tmp2 = "";
										query = 
												"SELECT LENHSANXUATDUKIEN_FK as ID, SUM(YEUCAU) AS SOLUONG FROM ERP_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU \n " +
												"WHERE LENHSANXUATDUKIEN_FK IN ( " + lastID_TP + ")  \n " +
												"AND NGUYENLIEU_FK = " + spId + " \n " +
												"GROUP BY LENHSANXUATDUKIEN_FK \n " +
												"ORDER BY LENHSANXUATDUKIEN_FK ";
			
										//System.out.println("Yêu cầu từ LSX_lastID_TP:" + query);
										rs_ = this.db.get(query);
										while(rs_.next()){
											yc = rs_.getDouble("SOLUONG");
											if(sl - yc >= 0){
												if(tmp1.length() == 0){
													tmp1 = rs_.getString("ID");
												}else{
													tmp1 += "," + rs_.getString("ID");
												}
												sl = sl - rs_.getDouble("SOLUONG");
											}else{
												if(tmp2.length() == 0){
													tmp2 = rs_.getString("ID");
												}else{
													tmp2 += "," + rs_.getString("ID");
												}
											}
										}									
										
										rs_.close();
										if(sl < yc){
											query = "UPDATE ERP_LENHSANXUATDUKIEN SET TRUCTHUOC_FK = '" + tmp1 + "' WHERE PK_SEQ = " + lastID_BTP + "";
											System.out.println(query);
											this.db.update(query);
											lastID_TP = tmp2;
											lastID_BTP = "";
											
										}
										
										
									}
										
									if(list.length() == 0){
										list = rs.getString("ID");
									}else{
										list += "," + rs.getString("ID");
									}
									yeucau += rs.getDouble("SOLUONG");
			
									lastID_BTP = rs.getString("ID");
			
									
								}
							}
							
						}
						rs.close();
						
						if(yeucau > 0){
							String tmp[] = list.split(",");
							list = "";
							for(int i = 0; i < tmp.length - 1; i++){
								if(list.length() == 0){
									list = tmp[i];								
								}else{
									list += "," + tmp[i];
								}
							}
						}
						if(list.trim().length() > 0){
							query = "DELETE ERP_LENHSANXUATDUKIEN WHERE PK_SEQ NOT IN (" + list + ") AND TRUCTHUOC_FK IS NOT NULL ";
							System.out.println(query);
							this.db.update(query);
						}
			
				}
				
			query = 
						"SELECT LSXDK.PK_SEQ AS ID, " +
						"NGAYBATDAU, \n " +
						"TRUCTHUOC_FK AS TRUCTHUOC \n " +
						"FROM ERP_LENHSANXUATDUKIEN LSXDK \n " +
						"LEFT JOIN ERP_THONGTINDATHANG TT ON TT.SANPHAM_FK = LSXDK.SANPHAM_FK \n " +
						"WHERE TRUCTHUOC_FK IS NOT NULL \n " ;
			
			//System.out.println(query);
			rs = this.db.get(query);
			
			while(rs.next()){
				query = "UPDATE LSXDK \n " +
						"SET NGAYBATDAU = '" + rs.getString("NGAYBATDAU") + "', \n " +
						"NGAYKETTHUC = (SELECT CONVERT(VARCHAR(10), DATEADD(DAY, ISNULL(1.0*TT.THOIGIANSX/8, 0), '" + rs.getString("NGAYBATDAU") + "'), 120)) \n " +
						"FROM ERP_LENHSANXUATDUKIEN LSXDK \n " +
						"LEFT JOIN ERP_THONGTINDATHANG TT ON TT.SANPHAM_FK = LSXDK.SANPHAM_FK \n " +
						"WHERE LSXDK.PK_SEQ IN (" + rs.getString("TRUCTHUOC") + ") \n ";
			//	System.out.println(query);
				this.db.update(query);
			}
			rs.close();	
			}
			catch(Exception e)
			{
				System.out.println("Loi Tao LSX cho BTP: " + e.toString());
			}
	
		}
	
		private void TaoSoLo()
		{
			String tmp = "";
			int solo = getSolo();
			String query =  "SELECT PK_SEQ AS LSXID, SANPHAM_FK AS SPID,  SUBSTRING(NGAYBATDAU, 1, 4) - 2000 AS NAM \n " + 
							"FROM ERP_LENHSANXUATDUKIEN LSXDK \n " +
							"WHERE SOLO IS NULL \n " +
							"ORDER BY SANPHAM_FK, PK_SEQ \n " ;
			System.out.println("::: LAY LSX DU KIEN: " + query );
			ResultSet rs = this.db.get(query);
			try
			{
				if(rs != null)
				{
					int i = solo;
					while(rs.next())
					{
						//Số lô định dạng 000
						String _solo = "";
						if( i < 10 )
							_solo = "00" + i;
						else if( i < 100 )
							_solo = "0" + i;
						else if( i >= 100 )
							_solo = Integer.toString(i);
							
						System.out.println("--- SO LO NEW: " + _solo );
						if(tmp.length() == 0){
							tmp += rs.getString("LSXID") + ":" + _solo  + "-" + rs.getString("NAM"); 
						}else{
							tmp += ";" + rs.getString("LSXID") + ":" + _solo  + "-" + rs.getString("NAM");
						}
						i++;
					}
					rs.close();
				}
				
				if(tmp.length() > 0)
				{
					String[] s = tmp.split(";");
					query = "";
					for(int m = 0; m < s.length; m++)
					{
						query += "UPDATE ERP_LENHSANXUATDUKIEN  SET SOLO = '" + (s[m].split(":"))[1] + "' " + 
								 " WHERE PK_SEQ = " + (s[m].split(":"))[0] + " ";
						
						/*query += 	" update a set a.SOLO = '" + (s[m].split(":"))[1] + "', a.SOLUONG_BOM = a.soluong * isnull(b.quycach, 1)  "+
									" from ERP_LENHSANXUATDUKIEN a left join ERP_DANHMUCVATTU_KEHOACH b on a.sanpham_fk = b.sanpham_fk and  b.dubao_fk = '" + this.ID + "'  "+
									" where a.pk_seq = '" + (s[m].split(":"))[0] + "' ";*/
					}
					
					System.out.println(query);
					this.db.update(query);
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}

		private int getSolo()
		{
			int solo1 = 0;
			int solo2 = 0;
			int solo = 0;
			String query = "SELECT SOLODAUKY FROM ERP_SOLODAUKY WHERE CONGTY_FK = " + this.CTYID + "";
			ResultSet rs = this.db.get(query);
			try
			{
				if(rs != null)
				{
					if(rs.next()){
						solo1 = rs.getInt("SOLODAUKY");
					}
					rs.close();
				}
				
				query = "SELECT MAX(ISNULL(SUBSTRING(SOLO, 1, 3), 0)) AS MAX \n " +
						"FROM ERP_LENHSANXUATDUKIEN \n " +
						"WHERE CONGTY_FK = " + this.CTYID + " \n " ;
				rs = this.db.get(query);
				if(rs != null){
					if(rs.next()){
						solo2 = rs.getInt("MAX");
					}
					rs.close();
				}
				if(solo1 > solo2){
					solo = solo1 + 1;
				}else{
					solo = solo2 + 1;
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			System.out.println("::: SO LO LA: " + solo);
			return solo;
		}
		
		private String[] getSX_Tuan(String nam_thang, String nmId)
		{
			String[] tmp = new String[4];
			tmp[0] = "0";
			tmp[1] = "0";
			tmp[2] = "0";
			tmp[3] = "0";
			
			String query = 	
					"SELECT ISNULL(SUM(ISNULL(SX_TUAN_1, 0)), 0) AS SX_TUAN_1, ISNULL(SUM(ISNULL(SX_TUAN_2, 0)), 0) AS SX_TUAN_2, \n " +
					"ISNULL(SUM(ISNULL(SX_TUAN_3, 0)), 0) AS SX_TUAN_3, ISNULL(SUM(ISNULL(SX_TUAN_4, 0)), 0) AS SX_TUAN_4 \n " +
					"FROM( \n " +
							"SELECT	ISNULL((SUM(ISNULL(LSXSP0.SOLUONG,0)) -  ISNULL(NH.NHANHANG, 0)), 0)  AS SX_TUAN_1, \n " +
							"		0 AS SX_TUAN_2, \n " +
							"		0 AS SX_TUAN_3, \n " +
							"		0 AS SX_TUAN_4   \n " +		
							"FROM ERP_LENHSANXUAT_GIAY LSX0 \n " +    
							"INNER JOIN ERP_LENHSANXUAT_SANPHAM LSXSP0 ON LSXSP0.LENHSANXUAT_FK = LSX0.PK_SEQ \n " +  
							"LEFT JOIN(   \n " +
							"	SELECT NK.SOLENHSANXUAT, NKSP.SANPHAM_FK, SUM(ISNULL(NKSP.SOLUONGNHAP, 0)) AS NHANHANG \n " +  
							"	FROM ERP_NHAPKHO NK   \n " +
							"	INNER JOIN ERP_NHAPKHO_SANPHAM NKSP ON NKSP.SONHAPKHO_FK = NK.PK_SEQ \n " +   
							"	WHERE NK.TRANGTHAI = 1   \n " +
	//						"	AND NK.KHONHAP IN (100005, 100006) \n " +  
							"	GROUP BY NKSP.SANPHAM_FK, NK.SOLENHSANXUAT \n " +  
							")NH ON NH.SANPHAM_FK = LSXSP0.SANPHAM_FK AND NH.SOLENHSANXUAT = LSX0.PK_SEQ \n " +  
										
							"WHERE LSX0.NGAYDUKIENHT >=  '" + nam_thang + "-01'  \n " +   
							"AND LSX0.NGAYDUKIENHT <= '" + nam_thang + "-07'  AND LSX0.TRANGTHAI < 6    \n " +
							"AND LSX0.NHAMAY_FK = " + nmId + " \n " +
							"GROUP BY LSXSP0.SANPHAM_FK, NH.NHANHANG   \n " +
							
							"UNION ALL \n " +
							"SELECT	0 AS SX_TUAN_1, \n " +
							"		ISNULL((SUM(ISNULL(LSXSP0.SOLUONG,0)) -  ISNULL(NH.NHANHANG, 0)), 0)  AS SX_TUAN_2,   \n " +		
							"		0 AS SX_TUAN_3, \n " +
							"		0 AS SX_TUAN_4   \n " +
								
							"FROM ERP_LENHSANXUAT_GIAY LSX0     \n " +
							"INNER JOIN ERP_LENHSANXUAT_SANPHAM LSXSP0 ON LSXSP0.LENHSANXUAT_FK = LSX0.PK_SEQ   \n " +
							"LEFT JOIN(   \n " +
							"	SELECT NK.SOLENHSANXUAT, NKSP.SANPHAM_FK, SUM(ISNULL(NKSP.SOLUONGNHAP, 0)) AS NHANHANG  \n " + 
							"	FROM ERP_NHAPKHO NK   \n " +
							"	INNER JOIN ERP_NHAPKHO_SANPHAM NKSP ON NKSP.SONHAPKHO_FK = NK.PK_SEQ    \n " +
							"	WHERE NK.TRANGTHAI = 1   \n " +
	//						"	AND NK.KHONHAP IN (100005, 100006)   \n " +
							"	GROUP BY NKSP.SANPHAM_FK, NK.SOLENHSANXUAT   \n " +
							")NH ON NH.SANPHAM_FK = LSXSP0.SANPHAM_FK AND NH.SOLENHSANXUAT = LSX0.PK_SEQ  \n " + 
										
							"WHERE LSX0.NGAYDUKIENHT >=  '" + nam_thang + "-08'    \n " +
							"AND LSX0.NGAYDUKIENHT <= '" + nam_thang + "-14'  AND LSX0.TRANGTHAI < 6    \n " +
							"AND LSX0.NHAMAY_FK = " + nmId + " \n " +
							"GROUP BY LSXSP0.SANPHAM_FK, NH.NHANHANG   \n " +
							
							"UNION ALL \n " +
							"SELECT	0 AS SX_TUAN_1, \n " +
							"		0 AS SX_TUAN_2, \n " +
							"		ISNULL((SUM(ISNULL(LSXSP0.SOLUONG,0)) -  ISNULL(NH.NHANHANG, 0)), 0)  AS SX_TUAN_3, \n " +
							"		0 AS SX_TUAN_4 \n " +
							"FROM ERP_LENHSANXUAT_GIAY LSX0     \n " +
							"INNER JOIN ERP_LENHSANXUAT_SANPHAM LSXSP0 ON LSXSP0.LENHSANXUAT_FK = LSX0.PK_SEQ   \n " +
							"LEFT JOIN(   \n " +
							"	SELECT NK.SOLENHSANXUAT, NKSP.SANPHAM_FK, SUM(ISNULL(NKSP.SOLUONGNHAP, 0)) AS NHANHANG   \n " +
							"	FROM ERP_NHAPKHO NK   \n " +
							"	INNER JOIN ERP_NHAPKHO_SANPHAM NKSP ON NKSP.SONHAPKHO_FK = NK.PK_SEQ    \n " +
							"	WHERE NK.TRANGTHAI = 1   \n " +
	//						"	AND NK.KHONHAP IN (100005, 100006)   \n " +
							"	GROUP BY NKSP.SANPHAM_FK, NK.SOLENHSANXUAT  \n " + 
							")NH ON NH.SANPHAM_FK = LSXSP0.SANPHAM_FK AND NH.SOLENHSANXUAT = LSX0.PK_SEQ  \n " + 
										
							"WHERE LSX0.NGAYDUKIENHT >=  '" + nam_thang + "-15'    \n " +
							"AND LSX0.NGAYDUKIENHT <= '" + nam_thang + "-21'  AND LSX0.TRANGTHAI < 6    \n " +
							"AND LSX0.NHAMAY_FK = " + nmId + " \n " +
							"GROUP BY LSXSP0.SANPHAM_FK, NH.NHANHANG   \n " +
							
							"UNION ALL \n " +
							"SELECT	0 AS SX_TUAN_1, \n " +
							"		0 AS SX_TUAN_2, \n " +
							"		0 AS SX_TUAN_3, \n " +
							"		ISNULL((SUM(ISNULL(LSXSP0.SOLUONG,0)) -  ISNULL(NH.NHANHANG, 0)), 0) AS SX_TUAN_4 \n " +  		
							"FROM ERP_LENHSANXUAT_GIAY LSX0     \n " +
							"INNER JOIN ERP_LENHSANXUAT_SANPHAM LSXSP0 ON LSXSP0.LENHSANXUAT_FK = LSX0.PK_SEQ \n " +  
							"LEFT JOIN(   \n " +
							"	SELECT NK.SOLENHSANXUAT, NKSP.SANPHAM_FK, SUM(ISNULL(NKSP.SOLUONGNHAP, 0)) AS NHANHANG \n " +  
							"	FROM ERP_NHAPKHO NK   \n " +
							"	INNER JOIN ERP_NHAPKHO_SANPHAM NKSP ON NKSP.SONHAPKHO_FK = NK.PK_SEQ \n " +   
							"	WHERE NK.TRANGTHAI = 1   \n " +
	//						"	AND NK.KHONHAP IN (100005, 100006) \n " +  
							"	GROUP BY NKSP.SANPHAM_FK, NK.SOLENHSANXUAT \n " +  
							")NH ON NH.SANPHAM_FK = LSXSP0.SANPHAM_FK AND NH.SOLENHSANXUAT = LSX0.PK_SEQ \n " +  
										
							"WHERE LSX0.NGAYDUKIENHT >=  '" + nam_thang + "-22'    \n " +
							"AND LSX0.NGAYDUKIENHT <= '" + nam_thang + "-28'  AND LSX0.TRANGTHAI < 6 \n " +  
							"AND LSX0.NHAMAY_FK = " + nmId + " \n " +
							"GROUP BY LSXSP0.SANPHAM_FK, NH.NHANHANG   \n " +
					")DATA ";
				System.out.println(query);
				try{
					ResultSet rs = this.db.get(query);
					if(rs != null){
						rs.next();
						tmp[0] = rs.getString("SX_TUAN_1");
						tmp[1] = rs.getString("SX_TUAN_2");
						tmp[2] = rs.getString("SX_TUAN_3");
						tmp[3] = rs.getString("SX_TUAN_4");
						rs.close();
					}
					
				}catch(java.sql.SQLException e){}
			
				return tmp;
		}
	
		private double min_tuan(double tuan_1, double tuan_2, double tuan_3, double tuan_4)
		{
			String query = 	"SELECT ISNULL( \n " +
							"CASE WHEN MIN = " + tuan_1 + " THEN 1  \n " +
							"ELSE CASE WHEN MIN = " + tuan_2 + " THEN 2 \n " +
							"ELSE CASE WHEN MIN = " + tuan_3 + " THEN 3 \n " +
							"ELSE CASE WHEN MIN = " + tuan_4 + " THEN 4 \n " +
							"END END END END, 1) AS TUAN \n " +
							"FROM \n " +
							"( \n " +
							"	SELECT MIN(SANLUONG) AS MIN \n " +
							"	FROM( \n " +
							"		SELECT " + tuan_1 + " AS SANLUONG \n " +
									
							"		UNION ALL \n " +
							"		SELECT " + tuan_2 + " \n " +
									
							"		UNION ALL \n " +
							"		SELECT " + tuan_3 + " \n " +
									
							"		UNION ALL \n " +
							"		SELECT " + tuan_4 + " \n " +
									
							"	)SANLUONG \n " +
							")TUAN ";
			//System.out.println(query);
			ResultSet rs = this.db.get(query);
			try{
				rs.next();
				double m = rs.getDouble("TUAN");
				rs.close();
				return m ;
				
			}catch(java.sql.SQLException e){ return 0;}
		}
		
		private String initQuery()
		{
			// Trạng thái của LSX
			// 1 : Đã kích hoạt
				// 0 : Chưa kích hoạt
				// 2 : Đã nhập kho
				// 3 : Hoàn tất
				// 4 : Đã hủy
			
			/*String query = 	"SELECT * FROM " +
						"(  " +
							"SELECT '0DEM_" + this.getDateTime().substring(0, 7) + "-01' AS ID, YC.SANPHAM_FK AS SPID, ISNULL(SUM(YC.YEUCAU),0) AS QTY, " +
							"NULL AS KHO, '" + this.getDateTime() + "' AS NGAY " +
							"FROM ERP_YEUCAUCUNGUNG YC " +
							"INNER JOIN ERP_DUBAO DB ON YC.DUBAO_FK = DB.PK_SEQ " + 
							"WHERE YC.THANG = " + Integer.parseInt(this.getDateTime().substring(5, 7)) + " " +
							"AND YC.NAM = " + Integer.parseInt(this.getDateTime().substring(0, 4)) + " AND DB.HIEULUC = '1' " + 
							"GROUP BY YC.SANPHAM_FK " +
						"UNION " +
			
							"SELECT '1INV-" + this.getDateTime() + "' AS ID,  SANXUAT_SP.SANPHAM_FK AS SPID, ISNULL(SUM(KHO_SP.AVAILABLE), 0) AS QTY, NULL AS KHO, " +  
							"'" + this.getDateTime() + "' AS NGAY  " +  
							"FROM ERP_KHOTT_SANPHAM KHO_SP  " + 
							"INNER JOIN ERP_SANPHAM_SANXUAT SANXUAT_SP ON SANXUAT_SP.SANPHAM_FK = KHO_SP.SANPHAM_FK " +      
							"GROUP BY SANXUAT_SP.SANPHAM_FK " + 
			
						"UNION " +
							"SELECT	'PLO-' + LSXDK.NAM  + '-'  + CONVERT(VARCHAR, LSXDK.THANG) AS ID, " + 
							"LSXDK.SANPHAM_FK AS SPID, (ISNULL(SUM(LSXDK.SOLUONG),0) - ISNULL(SUM(ISNULL(LSX.SOLUONG, 0)),0))  AS QTY, NULL AS KHO, " +    
							"LSXDK.NAM  + '-' + "  +
							"(CASE WHEN LEN(CONVERT(VARCHAR, LSXDK.THANG)) = 1  " +
							" THEN ('0' +  CONVERT(VARCHAR, LSXDK.THANG)) " + 
							" ELSE CONVERT(VARCHAR, LSXDK.THANG) END " +
							" )  + " + 
							"(CASE WHEN (LSXDK.THANG = 2) THEN '-28' ELSE '-30' END) AS NGAY " +  		
							"FROM ERP_LENHSANXUATDUKIEN LSXDK  " +
							"LEFT JOIN ERP_LENHSANXUAT_GIAY LSX ON LSX.LENHSANXUATDUKIEN_FK = LSXDK.PK_SEQ AND LSX.TRANGTHAI <> 4 " +
							"AND (LSX.NGAYDUKIENHT >=   '" + this.getDateTime() + "'   AND LSX.NGAYDUKIENHT <=   ' " + this.getDateTime().substring(0, 8) + "-31' )  " +  		
							"WHERE (LSXDK.NAM = " + this.getDateTime().substring(0, 4) + "  AND LSXDK.THANG = " + Integer.parseInt(this.getDateTime().substring(5,7))  + ") " +  
							"GROUP BY LSXDK.PK_SEQ, LSXDK.SANPHAM_FK, LSXDK.THANG, LSXDK.NAM " +
							
						"UNION  " +
							"( " +
							"	SELECT	'PRO-' + CONVERT(VARCHAR, LSX.PK_SEQ) AS ID, LSXSP.SANPHAM_FK AS SPID, ISNULL(LSX.SOLUONG,0) - ISNULL(SUM(NKSP.SOLUONGNHAN,0)) AS QTY, NULL AS KHO, " +   
							"			LSX.NGAYDUKIENHT AS NGAY " + 				
							"	FROM ERP_LENHSANXUAT_GIAY LSX  " +
							"	INNER JOIN ERP_LENHSANXUAT_SANPHAM LSXSP ON LSXSP.LENHSANXUAT_FK = LSX.PK_SEQ " +
							"	LEFT JOIN ERP_NHAPKHO NK ON NK.SOLENHSANXUAT = LSX.PK_SEQ " +
							"	LEFT JOIN ERP_NHAPKHO_SANPHAM NKSP ON NKSP.SONHAPKHO_FK = NK.PK_SEQ " +
							"	WHERE LSX.NGAYDUKIENHT >=  '" + this.getDateTime() + "' AND LSX.NGAYDUKIENHT <= '" + this.getDateTime().substring(0, 7) + "-31'  AND LSX.TRANGTHAI < 3 " +
							"	AND LSX.LENHSANXUATDUKIEN_FK IS NULL " +
							"	GROUP BY LSX.PK_SEQ, LSXSP.SANPHAM_FK, LSX.NGAYDUKIENHT, LSX.SOLUONG " +
							")  " ;*/
			
			String query = 	"SELECT * FROM " +
						"(  " +
							"SELECT '0DEM_" + this.getDateTime().substring(0, 7) + "-01' AS ID, YC.MASANPHAM AS SPID," +
									" YC.DORONG, YC.TRONGLUONG_CHUANNEN, YC.CoreOrConeBigger, YC.Length, YC.CoreThicknessORConesmaller, ISNULL(SUM(YC.YEUCAU),0) AS QTY, " +
							"NULL AS KHO, '" + this.getDateTime() + "' AS NGAY " +
							"FROM ERP_YEUCAUCUNGUNG YC " +
							"INNER JOIN ERP_DUBAO DB ON YC.DUBAO_FK = DB.PK_SEQ " + 
							"WHERE YC.THANG = " + Integer.parseInt(this.getDateTime().substring(5, 7)) + " " +
							"AND YC.NAM = " + Integer.parseInt(this.getDateTime().substring(0, 4)) + " AND DB.HIEULUC = '1' " + 
							"GROUP BY YC.MASANPHAM, YC.DORONG, YC.TRONGLUONG_CHUANNEN, YC.CoreOrConeBigger, YC.Length, YC.CoreThicknessORConesmaller " +
						"UNION " +
				
							"SELECT '1INV-" + this.getDateTime() + "' AS ID, case SP.DVKD_FK when 100000 then MAKT.MA else SP.MA end AS SPID, " +
									"case SP.DVKD_FK when 100000 then SP.RONG else 0 end as DORONG, " +
									"case SP.CHUNGLOAI_FK when 100040 then cast(SP.TRONGLUONG as varchar(10)) when 100031 then SP.CHUANNEN else '' end AS TRONGLUONG_CHUANNEN, " +
									"case SP.DVKD_FK when 100000 then 0 else case SP.CHUNGLOAI_FK when 100040 then isnull(DAULON, 0) else isnull(DUONGKINHTRONG, 0) end end as CoreOrConeBigger," +
									"case SP.DVKD_FK when 100000 then 0 else isnull(SP.DAI, 0) end as Length," +
									"case SP.DVKD_FK when 100000 then 0 else case SP.CHUNGLOAI_FK when 100040 then isnull(DAUNHO, 0) else isnull(DODAY, 0) end end as CoreThicknessORConesmaller, " +
								"ISNULL(SUM(KHO_SP.AVAILABLE), 0) AS QTY, NULL AS KHO,  '" + this.getDateTime() + "' AS NGAY     " +
							"FROM ERP_KHOTT_SANPHAM KHO_SP    " +
							"INNER JOIN ERP_SANPHAM SP on KHO_SP.SANPHAM_FK = SP.PK_SEQ " +
							"LEFT JOIN ERP_MAKETOAN MAKT on SP.MAKETOAN_FK = MAKT.PK_SEQ    " +
							"GROUP BY SP.DVKD_FK, case SP.DVKD_FK when 100000 then MAKT.MA else SP.MA end, " +
							"	case SP.DVKD_FK when 100000 then SP.RONG else 0  end, " +
							"	case SP.CHUNGLOAI_FK when 100040 then cast(SP.TRONGLUONG as varchar(10)) when 100031 then SP.CHUANNEN else '' end,  " +
							"	case SP.DVKD_FK when 100000 then 0 else case SP.CHUNGLOAI_FK when 100040 then isnull(DAULON, 0) else isnull(DUONGKINHTRONG, 0) end end, " +
							"	case SP.DVKD_FK when 100000 then 0 else isnull(SP.DAI, 0) end," +
							"	case SP.DVKD_FK when 100000 then 0 else case SP.CHUNGLOAI_FK when 100040 then isnull(DAUNHO, 0) else isnull(DODAY, 0) end end " +
				
						"UNION " +
							"SELECT	'PLO-' + LSXDK.NAM  + '-'  + CONVERT(VARCHAR, LSXDK.THANG) AS ID, " + 
							"LSXDK.MASANPHAM AS SPID, LSXDK.DORONG, LSXDK.TRONGLUONG_CHUANNEN, LSXDK.CoreOrConeBigger, LSXDK.Length, LSXDK.CoreThicknessORConesmaller, (ISNULL(SUM(LSXDK.SOLUONG),0) - ISNULL(SUM(ISNULL(LSX.SOLUONG, 0)),0))  AS QTY, NULL AS KHO, " +    
							"LSXDK.NAM  + '-' + "  +
							"(CASE WHEN LEN(CONVERT(VARCHAR, LSXDK.THANG)) = 1  " +
							" THEN ('0' +  CONVERT(VARCHAR, LSXDK.THANG)) " + 
							" ELSE CONVERT(VARCHAR, LSXDK.THANG) END " +
							" )  + " + 
							"(CASE WHEN (LSXDK.THANG = 2) THEN '-28' ELSE '-30' END) AS NGAY " +  		
							"FROM ERP_LENHSANXUATDUKIEN LSXDK  " +
								"LEFT JOIN ERP_LENHSANXUAT_GIAY LSX ON LSX.LENHSANXUATDUKIEN_FK = LSXDK.PK_SEQ AND LSX.TRANGTHAI <> 4 " +
							"AND (LSX.NGAYDUKIENHT >=   '" + this.getDateTime() + "'   AND LSX.NGAYDUKIENHT <=   ' " + this.getDateTime().substring(0, 7) + "-31' )  " +  		
							"WHERE (LSXDK.NAM = " + this.getDateTime().substring(0, 4) + "  AND LSXDK.THANG = " + Integer.parseInt(this.getDateTime().substring(5,7))  + ") " +  
							"GROUP BY LSXDK.PK_SEQ, LSXDK.MASANPHAM, LSXDK.DORONG, LSXDK.TRONGLUONG_CHUANNEN, LSXDK.CoreOrConeBigger, LSXDK.Length, LSXDK.CoreThicknessORConesmaller, LSXDK.THANG, LSXDK.NAM " +
							
						"UNION  " +
							"( " +
							"	SELECT	'PRO-' + CONVERT(VARCHAR, LSX.PK_SEQ) AS ID, " +
							"		case SP.DVKD_FK when 100000 then MAKT.MA else SP.MA end AS SPID, " +
							"		case SP.DVKD_FK when 100000 then SP.RONG else 0 end as DORONG, " +
							"		case SP.CHUNGLOAI_FK when 100040 then cast(SP.TRONGLUONG as varchar(10)) when 100031 then SP.CHUANNEN else '' end as TRONGLUONG_CHUANNEN, " +
							"		case SP.DVKD_FK when 100000 then 0 else case SP.CHUNGLOAI_FK when 100040 then isnull(DAULON, 0) else isnull(DUONGKINHTRONG, 0) end end as CoreOrConeBigger," +
							"		case SP.DVKD_FK when 100000 then 0 else isnull(SP.DAI, 0) end as Length," +
							"		case SP.DVKD_FK when 100000 then 0 else case SP.CHUNGLOAI_FK when 100040 then isnull(DAUNHO, 0) else isnull(DODAY, 0) end end as CoreThicknessORConesmaller, " +
							"ISNULL(LSX.SOLUONG,0) -  SUM( Isnull(NKSP.SOLUONGNHAN, 0))  AS QTY, NULL AS KHO, LSX.NGAYDUKIENHT AS NGAY  				 " +
								"FROM ERP_LENHSANXUAT_GIAY LSX   " +
									"INNER JOIN ERP_LENHSANXUAT_SANPHAM LSXSP ON LSXSP.LENHSANXUAT_FK = LSX.PK_SEQ  " +
									"LEFT JOIN ERP_NHAPKHO NK ON NK.SOLENHSANXUAT = LSX.PK_SEQ  " +
									"LEFT JOIN ERP_NHAPKHO_SANPHAM NKSP ON NKSP.SONHAPKHO_FK = NK.PK_SEQ  " +
									"INNER JOIN ERP_SANPHAM SP on LSXSP.SANPHAM_FK = SP.PK_SEQ " +
									"LEFT JOIN ERP_MAKETOAN MAKT on SP.MAKETOAN_FK = MAKT.PK_SEQ    " +
								"WHERE LSX.NGAYDUKIENHT >=  '" + getDateTime() + "' AND LSX.NGAYDUKIENHT <= '" + this.getDateTime().substring(0, 7) + "-31'  AND LSX.TRANGTHAI < 3  " +
									"AND LSX.LENHSANXUATDUKIEN_FK IS NULL  " +
								"GROUP BY LSX.PK_SEQ, SP.DVKD_FK, case SP.DVKD_FK when 100000 then MAKT.MA else SP.MA end, " +
								"	case SP.DVKD_FK when 100000 then SP.RONG else 0 end, " +
								"	case SP.CHUNGLOAI_FK when 100040 then cast(SP.TRONGLUONG as varchar(10)) when 100031 then SP.CHUANNEN else '' end, LSX.NGAYDUKIENHT, LSX.SOLUONG, " +
								"	case SP.DVKD_FK when 100000 then 0 else case SP.CHUNGLOAI_FK when 100040 then isnull(DAULON, 0) else isnull(DUONGKINHTRONG, 0) end end," +
								"	case SP.DVKD_FK when 100000 then 0 else isnull(SP.DAI, 0) end," +
								"	case SP.DVKD_FK when 100000 then 0 else case SP.CHUNGLOAI_FK when 100040 then isnull(DAUNHO, 0) else isnull(DODAY, 0) end end " +
							")  " ;
			
			return query;
		}
	
		private void taoLSXDK(String spId, String dorong, String trongluong_chuannen, String CoreOrConeBigger, String Length, String CoreThicknessORConesmaller, int nam, int thang, long yeucau)
		{
			try
			{
			// 	Truoc khi tao lenh san xuat du kien, kiem tra xem lenh san xuat du kien da ton tai chua?
				/*String query = 	"SELECT COUNT(PK_SEQ) AS NUM " +
								"FROM ERP_LENHSANXUATDUKIEN WHERE SANPHAM_FK = '" + spId + "' AND THANG = " + thang + " AND NAM = " + nam + " ";*/
			
				String query = 	"SELECT COUNT(PK_SEQ) AS NUM " +
								"FROM ERP_LENHSANXUATDUKIEN WHERE MASANPHAM = '" + spId + "' AND THANG = " + thang + " AND NAM = " + nam + " and DoRong = '" + dorong + "' " +
										"and trongluong_chuannen = '" + trongluong_chuannen + "' and CoreOrConeBigger = '" + CoreOrConeBigger + "' and Length = '" + Length + "' and CoreThicknessORConesmaller = '" + CoreThicknessORConesmaller + "' ";
				
				System.out.println("2.CHECK LSX DU KIEN: " + query);
				
				ResultSet rsCheck = this.db.get(query);
				rsCheck.next();
			
				if(rsCheck.getString("NUM").equals("0"))  // Chua co lenh san xuat
				{ 
					/*query =    	"INSERT INTO ERP_LENHSANXUATDUKIEN " +
								"([CONGTY_FK], [NHAMAY_FK], [MASANPHAM],[SOLUONG], [SANXUAT], [THANG],[NAM]," +
								"[NGAYTAO],[NGUOITAO],[NGAYSUA],[NGUOISUA],[TRANGTHAI]) "	+
								"SELECT " + this.CTYID + ", NHAMAY_FK, '" + spId + "', '" + yeucau + "', '0', " +
								"'" + thang + "', '" + nam + "', '" + this.getDateTime() + "', '" + this.USERID + "', " + 
								"'" + this.getDateTime() + "', '" + this.USERID + "', '1' " +
								"FROM ERP_SANPHAM_SANXUAT WHERE SANPHAM_FK = '" + spId + "' ";*/
					
					query =    	"INSERT INTO ERP_LENHSANXUATDUKIEN " +
								"([CONGTY_FK], [NHAMAY_FK], [MASANPHAM], [DORONG], [TRONGLUONG_CHUANNEN], [CoreOrConeBigger], [Length], [CoreThicknessORConesmaller], [SOLUONG], [SANXUAT], [THANG],[NAM]," +
								"[NGAYTAO],[NGUOITAO],[NGAYSUA],[NGUOISUA],[TRANGTHAI]) "	+
								"SELECT " + this.CTYID + ", " + ( this.DVKDID.equals("100000") ? "100000" : "100001" ) + ", '" + spId + "', '" + dorong + "', '" + trongluong_chuannen + "', '" + CoreOrConeBigger + "', '" + Length + "', '" + CoreThicknessORConesmaller + "', '" + yeucau + "', '0', " +
								"'" + thang + "', '" + nam + "', '" + this.getDateTime() + "', '" + this.USERID + "', " + 
								"'" + this.getDateTime() + "', '" + this.USERID + "', '1' ";
						
					System.out.println("3.Khoi Tao De Nghi - LSX du kien: " + query);
					
					if(!this.db.update(query))
						System.out.println("Co loi: " + query);
				}
				else
				{
					query =		"UPDATE ERP_LENHSANXUATDUKIEN SET SOLUONG =  '" + yeucau + "' " + 
								"WHERE MASANPHAM = '" + spId + "' AND THANG = " + thang + " AND NAM = " + nam + " and DORONG = '" + dorong + "' " +
										"and trongluong_chuannen = '" + trongluong_chuannen + "' and CoreOrConeBigger = '" + CoreOrConeBigger + "' and Length = '" + Length + "' and CoreThicknessORConesmaller = '" + CoreThicknessORConesmaller + "' ";
					
					//System.out.println("4.Khoi Tao De Nghi - Update LSX du kien:   " + query);
					
					if(!this.db.update(query))
						System.out.println("Co loi: " + query);
				}
				rsCheck.close();
			}
			catch(Exception e)
			{
				System.out.println("___Exception LSX Du Kien: " + e.getMessage());
			}
		}
	
		private void TaoYeuCauNguyenLieu(String thang, String nam)
		{
			String query;
			ResultSet SPrs;
			ResultSet NLrs;

			String t0 = "", t1 = "", t2 = "", t3 = "", t4 = ""; 
			String n0 = "", n1 = "", n2 = "", n3 = "", n4 = "";
			t1 = thang;
			n1 = nam;

			if(t1.equals("1")){
				t0 = "12";
				n0 = "" + (Integer.parseInt(n1) - 1);
			}else{
				t0 = "" + (Integer.parseInt(t1) - 1);
				n0 = n1;
			}

			if(t1.equals("12")){
				t2 = "1";
				n2 = "" + (Integer.parseInt(n1) + 1);
			}else{
				t2 = "" + (Integer.parseInt(t1) + 1);
				n2 = n1;
			}

			if(t2.equals("12")){
				t3 = "1";
				n3 = "" + (Integer.parseInt(n2) + 1);
			}else{
				t3 = "" + (Integer.parseInt(t2) + 1);
				n3 = n2;
			}

			if(t3.equals("12")){
				t4 = "1";
				n4 = "" + (Integer.parseInt(n3) + 1);
			}else{
				t4 = "" + (Integer.parseInt(t3) + 1);
				n4 = n3;
			}

			query = "SELECT PK_SEQ, SANPHAM_FK, SOLUONG_BOM as SOLUONG, LOAIHANGHOA, " + t0 + ", " + n0 + " \n " +
							"FROM ERP_LENHSANXUATDUKIEN  \n " +
							"WHERE NGAYBATDAU >= '" + n0 + "-" + (t0.length() == 1?("0" + t0):t0) + "-01'  \n " +
							"AND NGAYKETTHUC <= '" + n0 + "-" + (t0.length() == 1?("0" + t0):t0) + "-31'  \n " +
							"AND CONGTY_FK = '" + this.CTYID + "' AND SOLUONG > 0 and LOAIHANGHOA in ( 1, 2 )  \n " +
							
					"UNION ALL  \n " + 
					"SELECT PK_SEQ, SANPHAM_FK, SOLUONG_BOM as SOLUONG, LOAIHANGHOA,  " + t1 + ", " + n1 + " \n " +
					"FROM ERP_LENHSANXUATDUKIEN  \n " +
					"WHERE NGAYBATDAU >= '" + n1 + "-" + (t1.length() == 1?("0" + t1):t1) + "-01'  \n " +
					"AND NGAYKETTHUC <= '" + n1 + "-" + (t1.length() == 1?("0" + t1):t1) + "-31'  \n " +
					"AND CONGTY_FK = '" + this.CTYID + "' AND SOLUONG > 0 and LOAIHANGHOA in ( 1, 2 )  \n " +

					"UNION ALL  \n " + 
					"SELECT PK_SEQ, SANPHAM_FK, SOLUONG_BOM as SOLUONG, LOAIHANGHOA,  " + t2 + ", " + n2 + "  \n " +
					"FROM ERP_LENHSANXUATDUKIEN  \n " +
					"WHERE NGAYBATDAU >= '" + n2 + "-" + (t2.length() == 1?("0" + t2):t2) + "-01'  \n " +
					"AND NGAYKETTHUC <= '" + n2 + "-" + (t2.length() == 1?("0" + t2):t2) + "-31'  \n " +
					"AND CONGTY_FK = '" + this.CTYID + "' AND SOLUONG > 0 and LOAIHANGHOA in ( 1, 2 ) \n " +

					"UNION ALL  \n " +
					"SELECT PK_SEQ, SANPHAM_FK, SOLUONG_BOM as SOLUONG, LOAIHANGHOA,  " + t3 + ", " + n3 + "  \n " +
					"FROM ERP_LENHSANXUATDUKIEN  \n " +
					"WHERE NGAYBATDAU >= '" + n3 + "-" + (t3.length() == 1?("0" + t3):t3) + "-01'  \n " +
					"AND NGAYKETTHUC <= '" + n3 + "-" + (t3.length() == 1?("0" + t3):t3) + "-31'  \n " +
					"AND CONGTY_FK = '" + this.CTYID + "' AND SOLUONG > 0 and LOAIHANGHOA in ( 1, 2 )  \n " +

					"UNION ALL  \n " +
					"SELECT PK_SEQ, SANPHAM_FK, SOLUONG_BOM as SOLUONG, LOAIHANGHOA,  " + t4 + ", " + n4 + "  \n " +
					"FROM ERP_LENHSANXUATDUKIEN  \n " +
					"WHERE NGAYBATDAU >= '" + n4 + "-" + (t4.length() == 1?("0" + t4):t4) + "-01'  \n " +
					"AND NGAYKETTHUC <= '" + n4 + "-" + (t4.length() == 1?("0" + t4):t4) + "-31'  \n " +
					"AND CONGTY_FK = '" + this.CTYID + "' AND SOLUONG > 0 and LOAIHANGHOA in ( 1, 2 )  \n " ;

			System.out.println(":::LENH SAN XUAT DU KIEN ( SAN XUAT + GIA CONG ): " + query);
			SPrs = this.db.get(query);

			try
			{
				if(SPrs != null)
				{
					while(SPrs.next())
					{
						String Id = SPrs.getString("PK_SEQ");
						String spId = SPrs.getString("SANPHAM_FK");
						double soluong = SPrs.getDouble("SOLUONG");
						if(soluong > 0)
						{
							// Lay ra yeu cau nguyen lieu cho 1 lenh san xuat du kien
							NLrs = getDanhmucvattu(spId, soluong);    

							if(NLrs != null)
							{
								while(NLrs.next())
								{
									//Neu VATTU_FK 
									query = "INSERT INTO ERP_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU" +
											"(LENHSANXUATDUKIEN_FK, NGUYENLIEU_FK, isTINHHAMAM, isTINHHAMLUONG, HAMAM, HAMLUONG, YEUCAU, NHAMAY_FK) " +
											"VALUES( " + Id + ", " + NLrs.getDouble("NLID") + ", " + NLrs.getString("isTINHHAMAM") + ", " +
											"" + NLrs.getString("isTINHHAMLUONG") + ", " +  NLrs.getString("HAMAM") + ", " +  NLrs.getString("HAMLUONG") + ", " + 
											"" + NLrs.getDouble("SOLUONG") + ", " + NLrs.getDouble("NMID") + " )" ;

									System.out.println("1. Insert ERP_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU: " + query);
									if(!this.db.update(query))
									{
										query = " UPDATE ERP_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU SET YEUCAU = YEUCAU + " + NLrs.getDouble("SOLUONG") + 
												" WHERE LENHSANXUATDUKIEN_FK = " + Id + " AND NGUYENLIEU_FK = " + NLrs.getString("NLID") + " " ;

										System.out.println("2.Update ERP_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU: " + query);
										this.db.update(query);
									}
								}	
								NLrs.close();
							}
						}
					}
					SPrs.close();
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
				System.out.println("Loi TaoYeuCauNguyenLieu: " + e.toString());
			}
			
			
			//Nếu là mua ngoài thì chèn yêu cầu NL luôn
			query = " delete ERP_YEUCAUNHANNGUYENLIEU where LOAIHANGHOA = 0 and THANG in ( " + t0 + ", " + t1 + ", " + t2 + ", " + t3 + ", " + t4 + " ) and NAM in ( " + n0 + ", " + n1 + ", " + n2 + ", " + n3 + ", " + n4 + " ) ";
			db.update(query);
			
			query = "SELECT PK_SEQ, SANPHAM_FK, SOLUONG, LOAIHANGHOA, " + t0 + " as thang, " + n0 + " as nam \n " +
							"FROM ERP_LENHSANXUATDUKIEN  \n " +
							"WHERE NGAYBATDAU >= '" + n0 + "-" + (t0.length() == 1?("0" + t0):t0) + "-01'  \n " +
							"AND NGAYKETTHUC <= '" + n0 + "-" + (t0.length() == 1?("0" + t0):t0) + "-31'  \n " +
							"AND CONGTY_FK = '" + this.CTYID + "' AND SOLUONG > 0 and LOAIHANGHOA = 0  \n " +
							
					"UNION ALL  \n " + 
					"SELECT PK_SEQ, SANPHAM_FK, SOLUONG, LOAIHANGHOA,  " + t1 + ", " + n1 + " \n " +
					"FROM ERP_LENHSANXUATDUKIEN  \n " +
					"WHERE NGAYBATDAU >= '" + n1 + "-" + (t1.length() == 1?("0" + t1):t1) + "-01'  \n " +
					"AND NGAYKETTHUC <= '" + n1 + "-" + (t1.length() == 1?("0" + t1):t1) + "-31'  \n " +
					"AND CONGTY_FK = '" + this.CTYID + "' AND SOLUONG > 0 and LOAIHANGHOA = 0  \n " +

					"UNION ALL  \n " + 
					"SELECT PK_SEQ, SANPHAM_FK, SOLUONG, LOAIHANGHOA,  " + t2 + ", " + n2 + "  \n " +
					"FROM ERP_LENHSANXUATDUKIEN  \n " +
					"WHERE NGAYBATDAU >= '" + n2 + "-" + (t2.length() == 1?("0" + t2):t2) + "-01'  \n " +
					"AND NGAYKETTHUC <= '" + n2 + "-" + (t2.length() == 1?("0" + t2):t2) + "-31'  \n " +
					"AND CONGTY_FK = '" + this.CTYID + "' AND SOLUONG > 0 and LOAIHANGHOA = 0  \n " +

					"UNION ALL  \n " +
					"SELECT PK_SEQ, SANPHAM_FK, SOLUONG, LOAIHANGHOA,  " + t3 + ", " + n3 + "  \n " +
					"FROM ERP_LENHSANXUATDUKIEN  \n " +
					"WHERE NGAYBATDAU >= '" + n3 + "-" + (t3.length() == 1?("0" + t3):t3) + "-01'  \n " +
					"AND NGAYKETTHUC <= '" + n3 + "-" + (t3.length() == 1?("0" + t3):t3) + "-31'  \n " +
					"AND CONGTY_FK = '" + this.CTYID + "' AND SOLUONG > 0 and LOAIHANGHOA = 0  \n " +

					"UNION ALL  \n " +
					"SELECT PK_SEQ, SANPHAM_FK, SOLUONG, LOAIHANGHOA,  " + t4 + ", " + n4 + "  \n " +
					"FROM ERP_LENHSANXUATDUKIEN  \n " +
					"WHERE NGAYBATDAU >= '" + n4 + "-" + (t4.length() == 1?("0" + t4):t4) + "-01'  \n " +
					"AND NGAYKETTHUC <= '" + n4 + "-" + (t4.length() == 1?("0" + t4):t4) + "-31'  \n " +
					"AND CONGTY_FK = '" + this.CTYID + "' AND SOLUONG > 0 and LOAIHANGHOA = 0  \n " ;

			query = "insert ERP_YEUCAUNHANNGUYENLIEU( SANPHAM_FK, SOLUONG, SOLUONG_YC, THANG, NAM, NGAYTAO, NGAYSUA, NGUOITAO, NGUOISUA, CONGTY_FK, LOAIHANGHOA )  " + 
					"\n select SANPHAM_FK, sum(SOLUONG), sum(SOLUONG), '" + t0 + "', '" + n0 + "', '" + this.getDateTime() + "', '" + this.getDateTime() + "', '100002', '100002', '" + this.CTYID + "', 0 as LOAIHANGHOA " + 
					"\n from ( " + query + " ) DT group by  SANPHAM_FK "	;
			
			System.out.println(":::LENH SAN XUAT DU KIEN - MUA NGOAI: " + query);
			db.update(query);
			
		}
	
		private ResultSet getDanhmucvattu(String spId, double soluong )
		{		
			//Bước này đổi lại, phải quy từ số lượng theo hàm ẩm, hàm lượng tương ứng
			
			String query = 	"SELECT DMVT_VT.DANHMUCVT_FK, DMVT_VT.VATTU_FK AS NLID, DMVT_VT.SOLUONG * " + soluong + " / DMVT.SOLUONGCHUAN AS SOLUONG, \n " +
							"ISNULL(DMVT_VT.HAMLUONG, 100) AS HAMLUONG, ISNULL(DMVT_VT.HAMAM, 0) AS HAMAM, \n " +
							"ISNULL(ISTINHHAMAM, 0) AS isTINHHAMAM, ISNULL(ISTINHHAMLUONG, 0) AS isTINHHAMLUONG, \n " +
							"(SELECT TOP 1 NHAMAY_FK FROM ERP_KICHBANSANXUAT_GIAY WHERE SANPHAM_FK = " + spId + ") as NMID \n " +
							"FROM ERP_DANHMUCVATTU  DMVT \n " +
							"INNER JOIN ERP_DANHMUCVATTU_VATTU DMVT_VT on DMVT.PK_SEQ = DMVT_VT.DANHMUCVT_FK \n " +
							"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = DMVT_VT.VATTU_FK \n " +
							
							"WHERE DMVT.MASANPHAM like (SELECT MA FROM ERP_SANPHAM WHERE PK_SEQ = '" + spId + "') \n " +
							"AND SP.LOAISANPHAM_FK IN (100000, 100008, 100013) \n " + 
							"AND DMVT.pk_seq = ( select danhmucvt_fk from ERP_DANHMUCVATTU_KEHOACH where dubao_fk = '" + this.ID + "' and sanpham_fk = '" + spId + "' )	"; 
				
			System.out.println(":::LAY Danh muc vat tu: " + query);
			return this.db.get(query);
		}
	
		private ResultSet getDanhmucBTP(String spId, double soluongTP )
		{	
			
			String query = 	// BÁN THÀNH PHẨM, THÀNH PHẨM TRONG THÀNH PHẦN CỦA BOM
							
							"SELECT DMVT_VT.DANHMUCVT_FK, DMVT_VT.VATTU_FK AS BTPID, \n " +
							"DMVT_VT.SOLUONG * " + soluongTP + " / DMVT.SOLUONGCHUAN AS SOLUONG,  \n " +
							"TT.LOTSIZE \n " +
							"FROM ERP_DANHMUCVATTU  DMVT \n " +
							"INNER JOIN ERP_DANHMUCVATTU_VATTU DMVT_VT on DMVT.PK_SEQ = DMVT_VT.DANHMUCVT_FK \n " +
							"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = DMVT_VT.VATTU_FK \n " +
							"INNER JOIN ERP_THONGTINDATHANG TT ON TT.SANPHAM_FK = SP.PK_SEQ AND TT.isMUA = 0 \n " +
							"WHERE DMVT.MASANPHAM like (SELECT MA FROM ERP_SANPHAM WHERE PK_SEQ = '" + spId + "') \n " +
							"AND SP.LOAISANPHAM_FK IN (100004, 100005, 100006, 100007) \n " +
							"AND (SELECT MA FROM ERP_SANPHAM WHERE PK_SEQ = '" + spId + "') IS NOT NULL ";
				
			System.out.println("LAY Danh muc BTP: " + query);
			if(query.trim().length() <= 0 )
				return null;
			
			return this.db.get(query);
		}

		
		private double TinhSoLuong( double dinhmuc, double a2_hamam_dinhmuc, double a1_hamam_thucte, double b2_hamluong_dinhmuc, double b1_hamluong_thucte, int tinhHamluong, int tinhHamam  )
		{
			double soluong = 0;
			
			a2_hamam_dinhmuc = a2_hamam_dinhmuc / 100.0;
			a1_hamam_thucte = a1_hamam_thucte / 100.0;
			b2_hamluong_dinhmuc = b2_hamluong_dinhmuc / 100.0;
			b1_hamluong_thucte = b1_hamluong_thucte / 100.0;
			
			if( tinhHamam == 1 && tinhHamluong == 1 )
			{
				if( ( 1 - a1_hamam_thucte ) * b1_hamluong_thucte <= 0 )
					soluong = dinhmuc;
				else
					soluong = ( dinhmuc * ( 1 - a2_hamam_dinhmuc ) * b2_hamluong_dinhmuc ) / ( ( 1 - a1_hamam_thucte ) * b1_hamluong_thucte );
			}
			else if( tinhHamluong == 1 )
			{
				if( b1_hamluong_thucte <= 0 )
					soluong = dinhmuc;
				else
					soluong = dinhmuc * b2_hamluong_dinhmuc / b1_hamluong_thucte;
			}
			else if( tinhHamam == 1 )
			{
				if( a1_hamam_thucte >= 1 )
					soluong = dinhmuc;
				else
					soluong = dinhmuc * ( 1 - a2_hamam_dinhmuc ) / ( 1 - a1_hamam_thucte );
			}
				
			return soluong;
		}

		private void TaoYCBoSungNguyenLieu(String thang, String nam) 
		{
			String t0 = "", t1 = "", t2 = "", t3 = "", t4 = "";
			String n0 = "", n1 = "", n2 = "", n3 = "", n4 = "";
			t1 = thang;
			n1 = nam;

			if(t1.equals("1")){
				t0 = "12";
				n0 = "" + (Integer.parseInt(n1) - 1);				
			}else{
				t0 = "" + (Integer.parseInt(t1) - 1);
				n0 = n1 ;							
			}

			if(t1.equals("12")){
				t2 = "1";
				n2 = "" + (Integer.parseInt(n1) + 1);
			}else{
				t2 = "" + (Integer.parseInt(t1) + 1);
				n2 = n1;
			}

			if(t2.equals("12")){
				t3 = "1";
				n3 = "" + (Integer.parseInt(n2) + 1);
			}else{
				t3 = "" + (Integer.parseInt(t2) + 1);
				n3 = n2;
			}


			if(t3.equals("12")){
				t4 = "1";
				n4 = "" + (Integer.parseInt(n3) + 1);
			}else{
				t4 = "" + (Integer.parseInt(t3) + 1);
				n4 = n3;
			}


			String query = "DELETE ERP_YEUCAUNHANNGUYENLIEU WHERE THANG = " + t0 + " AND NAM = " + n0 + " and LOAIHANGHOA != 0 ";
			this.db.update(query);

			query = "DELETE ERP_YEUCAUNHANNGUYENLIEU WHERE THANG = " + t1 + " AND NAM = " + n1 + " and LOAIHANGHOA != 0 ";
			this.db.update(query);

			query = "DELETE ERP_YEUCAUNHANNGUYENLIEU WHERE THANG = " + t2 + " AND NAM = " + n2 + " and LOAIHANGHOA != 0 ";
			this.db.update(query);

			query = "DELETE ERP_YEUCAUNHANNGUYENLIEU WHERE THANG = " + t3 + " AND NAM = " + n3 + " and LOAIHANGHOA != 0 ";
			this.db.update(query);

			query = "DELETE ERP_YEUCAUNHANNGUYENLIEU WHERE THANG = " + t4 + " AND NAM = " + n4 + " and LOAIHANGHOA != 0 ";
			this.db.update(query);

			query = "SELECT  KHOTT_SP.SANPHAM_FK AS SPID, SP.TEN, \n " +
					"SUM(ISNULL(KHOTT_SP.AVAILABLE, 0)) AS TONHIENTAI, " +
					"SUM(ISNULL(KHOTT_SP.TONANTOAN, 0)) AS TONANTOAN,  \n  " +

					"ISNULL(YCNLSX_THANG_0.YCNL, 0) AS YCNLSX_THANG_0, \n " +

					"CASE WHEN (ISNULL(YCNLSX_THANG_0.YCNL, 0) + SUM(ISNULL(KHOTT_SP.TONANTOAN, 0)) - (SUM(ISNULL(KHOTT_SP.AVAILABLE, 0)) + ISNULL(MH_THANG_0.QTY, 0)) < 0) THEN 0  \n " +
					"ELSE (ISNULL(YCNLSX_THANG_0.YCNL, 0) + SUM(ISNULL(KHOTT_SP.TONANTOAN, 0)) - (SUM(ISNULL(KHOTT_SP.AVAILABLE, 0)) + ISNULL(MH_THANG_0.QTY, 0))) END AS YC_NHAN_NL_THANG_0,  \n " +

					"ISNULL(YCNLSX_THANG_1.YCNL, 0) AS YCNLSX_THANG_1, \n " +

					"ISNULL(CASE WHEN (ISNULL(YCNLSX_THANG_0.YCNL, 0) + SUM(ISNULL(KHOTT_SP.TONANTOAN, 0)) - (SUM(ISNULL(KHOTT_SP.AVAILABLE, 0)) + ISNULL(MH_THANG_0.QTY, 0) ) < 0)  \n  " + 
					"THEN  " +
					"	CASE WHEN ( ISNULL(YCNLSX_THANG_1.YCNL, 0)  + ISNULL(YCNLSX_THANG_0.YCNL, 0) + SUM(ISNULL(KHOTT_SP.TONANTOAN, 0)) - (SUM(ISNULL(KHOTT_SP.AVAILABLE, 0)) + ISNULL(MH_THANG_0.QTY, 0) + ISNULL(MH_THANG_1.QTY, 0)) < 0) \n  " + 
					"	THEN 0 \n " +
					"	ELSE (ISNULL(YCNLSX_THANG_1.YCNL, 0)  + ISNULL(YCNLSX_THANG_0.YCNL, 0) + SUM(ISNULL(KHOTT_SP.TONANTOAN, 0)) - (SUM(ISNULL(KHOTT_SP.AVAILABLE, 0)) + ISNULL(MH_THANG_0.QTY, 0) + ISNULL(MH_THANG_1.QTY, 0))) \n  " +
					"	END  \n " +
					"ELSE (ISNULL(YCNLSX_THANG_1.YCNL, 0) - ISNULL(MH_THANG_1.QTY, 0)) \n  " +
					"END, 0) AS YC_NHAN_NL_THANG_1, \n " +

					"ISNULL(YCNLSX_THANG_2.YCNL, 0) AS YCNLSX_THANG_2, \n  " +

					"ISNULL(CASE WHEN (ISNULL(YCNLSX_THANG_1.YCNL, 0) + ISNULL(YCNLSX_THANG_0.YCNL, 0) + SUM(ISNULL(KHOTT_SP.TONANTOAN, 0)) - (SUM(ISNULL(KHOTT_SP.AVAILABLE, 0)) + ISNULL(MH_THANG_0.QTY, 0) + ISNULL(MH_THANG_1.QTY, 0)) < 0)  \n  " + 
					"THEN  " +
					"	CASE WHEN (ISNULL(YCNLSX_THANG_2.YCNL, 0) + ISNULL(YCNLSX_THANG_1.YCNL, 0) + ISNULL(YCNLSX_THANG_0.YCNL, 0) + SUM(ISNULL(KHOTT_SP.TONANTOAN, 0)) - (SUM(ISNULL(KHOTT_SP.AVAILABLE, 0)) + ISNULL(MH_THANG_0.QTY, 0) + ISNULL(MH_THANG_1.QTY, 0) + ISNULL(MH_THANG_2.QTY, 0)) < 0) \n  " + 
					"	THEN 0 \n " +
					"	ELSE (ISNULL(YCNLSX_THANG_2.YCNL, 0) + ISNULL(YCNLSX_THANG_1.YCNL, 0) + ISNULL(YCNLSX_THANG_0.YCNL, 0) + SUM(ISNULL(KHOTT_SP.TONANTOAN, 0)) - (SUM(ISNULL(KHOTT_SP.AVAILABLE, 0)) + ISNULL(MH_THANG_0.QTY, 0) + ISNULL(MH_THANG_1.QTY, 0) + ISNULL(MH_THANG_2.QTY, 0))) \n  " +
					"	END  \n " +
					"ELSE (ISNULL(YCNLSX_THANG_2.YCNL, 0) - ISNULL(MH_THANG_2.QTY, 0)) \n  " +
					"END, 0) AS YC_NHAN_NL_THANG_2, \n " +

					"ISNULL(YCNLSX_THANG_3.YCNL, 0) AS YCNLSX_THANG_3, \n " +

					"ISNULL( \n " +
					"	CASE WHEN (ISNULL(YCNLSX_THANG_2.YCNL, 0) + ISNULL(YCNLSX_THANG_1.YCNL, 0)  + ISNULL(YCNLSX_THANG_0.YCNL, 0) + SUM(ISNULL(KHOTT_SP.TONANTOAN, 0)) - (SUM(ISNULL(KHOTT_SP.AVAILABLE, 0)) + ISNULL(MH_THANG_0.QTY, 0) + ISNULL(MH_THANG_1.QTY, 0) + ISNULL(MH_THANG_2.QTY, 0)) < 0)  \n  " + 
					"	THEN  \n " +
					"		CASE WHEN (ISNULL(YCNLSX_THANG_3.YCNL, 0) + ISNULL(YCNLSX_THANG_2.YCNL, 0) + ISNULL(YCNLSX_THANG_1.YCNL, 0)  + ISNULL(YCNLSX_THANG_0.YCNL, 0) + SUM(ISNULL(KHOTT_SP.TONANTOAN, 0)) - (SUM(ISNULL(KHOTT_SP.AVAILABLE, 0)) + ISNULL(MH_THANG_0.QTY, 0) + ISNULL(MH_THANG_1.QTY, 0) + ISNULL(MH_THANG_2.QTY, 0) + ISNULL(MH_THANG_3.QTY, 0)) < 0) \n  " + 
					"		THEN 0 \n " +
					"		ELSE ISNULL(YCNLSX_THANG_3.YCNL, 0) + ISNULL(YCNLSX_THANG_2.YCNL, 0) + ISNULL(YCNLSX_THANG_1.YCNL, 0)  + ISNULL(YCNLSX_THANG_0.YCNL, 0) + SUM(ISNULL(KHOTT_SP.TONANTOAN, 0)) - (SUM(ISNULL(KHOTT_SP.AVAILABLE, 0)) + ISNULL(MH_THANG_0.QTY, 0) + ISNULL(MH_THANG_1.QTY, 0) + ISNULL(MH_THANG_2.QTY, 0) + ISNULL(MH_THANG_3.QTY, 0)) \n " +
					"		END  \n " +
					"	ELSE (ISNULL(YCNLSX_THANG_3.YCNL, 0) - ISNULL(MH_THANG_3.QTY, 0)) \n " +
					"	END, 0) AS YC_NHAN_NL_THANG_3, \n  " +

					"ISNULL( \n " +
					"	CASE WHEN (ISNULL(YCNLSX_THANG_3.YCNL, 0) + ISNULL(YCNLSX_THANG_2.YCNL, 0) + ISNULL(YCNLSX_THANG_1.YCNL, 0)  + ISNULL(YCNLSX_THANG_0.YCNL, 0) + SUM(ISNULL(KHOTT_SP.TONANTOAN, 0)) - (SUM(ISNULL(KHOTT_SP.AVAILABLE, 0)) + ISNULL(MH_THANG_0.QTY, 0) + ISNULL(MH_THANG_1.QTY, 0) + ISNULL(MH_THANG_2.QTY, 0) + ISNULL(MH_THANG_3.QTY, 0)) < 0)  \n  " + 
					"	THEN  \n " +
					"		CASE WHEN (ISNULL(YCNLSX_THANG_4.YCNL, 0) + ISNULL(YCNLSX_THANG_3.YCNL, 0) + ISNULL(YCNLSX_THANG_2.YCNL, 0) + ISNULL(YCNLSX_THANG_1.YCNL, 0)  + ISNULL(YCNLSX_THANG_0.YCNL, 0) + SUM(ISNULL(KHOTT_SP.TONANTOAN, 0)) - (SUM(ISNULL(KHOTT_SP.AVAILABLE, 0)) + ISNULL(MH_THANG_0.QTY, 0) + ISNULL(MH_THANG_1.QTY, 0) + ISNULL(MH_THANG_2.QTY, 0) + ISNULL(MH_THANG_3.QTY, 0) + ISNULL(MH_THANG_4.QTY, 0)) < 0) \n  " + 
					"		THEN 0 \n " +
					"		ELSE ISNULL(YCNLSX_THANG_4.YCNL, 0) + ISNULL(YCNLSX_THANG_3.YCNL, 0) + ISNULL(YCNLSX_THANG_2.YCNL, 0) + ISNULL(YCNLSX_THANG_1.YCNL, 0)  + ISNULL(YCNLSX_THANG_0.YCNL, 0) + SUM(ISNULL(KHOTT_SP.TONANTOAN, 0)) - (SUM(ISNULL(KHOTT_SP.AVAILABLE, 0)) + ISNULL(MH_THANG_0.QTY, 0) + ISNULL(MH_THANG_1.QTY, 0) + ISNULL(MH_THANG_2.QTY, 0) + ISNULL(MH_THANG_3.QTY, 0) + ISNULL(MH_THANG_4.QTY, 0)) \n " +
					"		END  \n " +
					"	ELSE (ISNULL(YCNLSX_THANG_4.YCNL, 0) - ISNULL(MH_THANG_4.QTY, 0)) \n " +
					"	END, 0) AS YC_NHAN_NL_THANG_4 \n  " +

					"FROM ERP_KHOTT_SANPHAM KHOTT_SP \n " +
					"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = KHOTT_SP.SANPHAM_FK \n " +
					"LEFT JOIN \n " +
					"( " +
					"	SELECT YC.NGUYENLIEU_FK AS SPID, SUM(ISNULL(YC.YEUCAU, 0)) AS YCNL \n " +
					"	FROM ERP_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU YC  \n " +
					"	INNER JOIN ERP_LENHSANXUATDUKIEN LSXDK ON LSXDK.PK_SEQ = YC.LENHSANXUATDUKIEN_FK \n  " +
					"	WHERE CONVERT(INT, SUBSTRING(LSXDK.NGAYKETTHUC, 6, 2)) = " + t0 + " AND SUBSTRING(LSXDK.NGAYKETTHUC, 1, 4) = " + n0 + " \n  " +
					"	GROUP BY YC.NGUYENLIEU_FK \n " +
					")YCNLSX_THANG_0 ON YCNLSX_THANG_0.SPID = KHOTT_SP.SANPHAM_FK \n " +

					"LEFT JOIN \n " +
					"( " +
					"	SELECT YC.NGUYENLIEU_FK AS SPID, SUM(ISNULL(YC.YEUCAU, 0)) AS YCNL \n " +
					"	FROM ERP_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU YC  \n " +
					"	INNER JOIN ERP_LENHSANXUATDUKIEN LSXDK ON LSXDK.PK_SEQ = YC.LENHSANXUATDUKIEN_FK \n  " +
					"	WHERE CONVERT(INT, SUBSTRING(LSXDK.NGAYKETTHUC, 6, 2)) = " + t1 + " AND SUBSTRING(LSXDK.NGAYKETTHUC, 1, 4) = " + n1 + " \n  " +
					"	GROUP BY YC.NGUYENLIEU_FK \n " +
					")YCNLSX_THANG_1 ON YCNLSX_THANG_1.SPID = KHOTT_SP.SANPHAM_FK \n " +

					"LEFT JOIN \n " +
					"( \n " +
					"	SELECT YC.NGUYENLIEU_FK AS SPID, SUM(ISNULL(YC.YEUCAU, 0)) AS YCNL \n " +
					"	FROM ERP_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU YC  \n " +
					"	INNER JOIN ERP_LENHSANXUATDUKIEN LSXDK ON LSXDK.PK_SEQ = YC.LENHSANXUATDUKIEN_FK \n " +
					"	WHERE CONVERT(INT, SUBSTRING(LSXDK.NGAYKETTHUC, 6, 2)) = " + t2 + " AND SUBSTRING(LSXDK.NGAYKETTHUC, 1, 4) = " + n2 + " \n " +
					"	GROUP BY YC.NGUYENLIEU_FK \n " +
					")YCNLSX_THANG_2 ON YCNLSX_THANG_2.SPID = KHOTT_SP.SANPHAM_FK \n " +

					"LEFT JOIN \n " +
					"( \n " +
					"	SELECT YC.NGUYENLIEU_FK AS SPID, SUM(ISNULL(YC.YEUCAU, 0)) AS YCNL \n " +
					"	FROM ERP_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU YC  \n " +
					"	INNER JOIN ERP_LENHSANXUATDUKIEN LSXDK ON LSXDK.PK_SEQ = YC.LENHSANXUATDUKIEN_FK \n " +
					"	WHERE CONVERT(INT, SUBSTRING(LSXDK.NGAYKETTHUC, 6, 2)) = " + t3 + " AND SUBSTRING(LSXDK.NGAYKETTHUC, 1, 4) = " + n3 + " \n " +
					"	GROUP BY YC.NGUYENLIEU_FK \n " +
					")YCNLSX_THANG_3 ON YCNLSX_THANG_3.SPID = KHOTT_SP.SANPHAM_FK \n " +

					"LEFT JOIN \n " +
					"( \n " +
					"	SELECT YC.NGUYENLIEU_FK AS SPID, SUM(ISNULL(YC.YEUCAU, 0)) AS YCNL \n " +
					"	FROM ERP_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU YC  \n " +
					"	INNER JOIN ERP_LENHSANXUATDUKIEN LSXDK ON LSXDK.PK_SEQ = YC.LENHSANXUATDUKIEN_FK \n " +
					"	WHERE CONVERT(INT, SUBSTRING(LSXDK.NGAYKETTHUC, 6, 2)) = " + t4 + " AND SUBSTRING(LSXDK.NGAYKETTHUC, 1, 4) = " + n4 + " \n " +
					"	GROUP BY YC.NGUYENLIEU_FK \n " +
					")YCNLSX_THANG_4 ON YCNLSX_THANG_4.SPID = KHOTT_SP.SANPHAM_FK \n " +

					"LEFT JOIN(	 \n " +								
					"	SELECT	MHSP.SANPHAM_FK AS SPID,  \n " +
					"	(SUM(ISNULL(MHSP.SOLUONG,0)) -  ISNULL(NH.NHANHANG, 0))  AS QTY  \n " + 			
					"	FROM ERP_MUAHANG MH   \n " + 
					"	INNER JOIN ERP_MUAHANG_SP MHSP ON MHSP.MUAHANG_FK = MH.PK_SEQ  \n " +
					"	LEFT JOIN( \n " +
					"		SELECT NH.MUAHANG_FK, NHSP.SANPHAM_FK, SUM(ISNULL(NHSP.SOLUONGNHAN, 0)) AS NHANHANG \n " +
					"		FROM ERP_NHANHANG NH \n " +
					"		INNER JOIN ERP_NHANHANG_SANPHAM NHSP ON NHSP.MUAHANG_FK = NH.PK_SEQ  \n " +
					"		GROUP BY NHSP.SANPHAM_FK, NH.MUAHANG_FK \n " +
					"	)NH ON NH.SANPHAM_FK = MHSP.SANPHAM_FK AND NH.MUAHANG_FK = MHSP.MUAHANG_FK \n " +

					"	WHERE MHSP.NGAYNHAN >=  '" + n0 + "-" + (t0.length()== 1? "0" + t0 : t0) + "-01' \n  " +
					"	AND MHSP.NGAYNHAN <= '" + n0 + "-" + (t0.length()== 1? "0" + t0 : t0) + "-31'  AND MH.TRANGTHAI < 2 \n " +
					"	AND MHSP.SANPHAM_FK IS NOT NULL \n " +
					"	GROUP BY MHSP.SANPHAM_FK, NH.NHANHANG \n " +	
					")MH_THANG_0 ON MH_THANG_0.SPID = KHOTT_SP.SANPHAM_FK \n  " +

					"LEFT JOIN(	 \n " +								
					"	SELECT	MHSP.SANPHAM_FK AS SPID,  \n " +
					"	(SUM(ISNULL(MHSP.SOLUONG,0)) -  ISNULL(NH.NHANHANG, 0))  AS QTY  \n " + 			
					"	FROM ERP_MUAHANG MH   \n " + 
					"	INNER JOIN ERP_MUAHANG_SP MHSP ON MHSP.MUAHANG_FK = MH.PK_SEQ  \n " +
					"	LEFT JOIN( \n " +
					"		SELECT NH.MUAHANG_FK, NHSP.SANPHAM_FK, SUM(ISNULL(NHSP.SOLUONGNHAN, 0)) AS NHANHANG \n " +
					"		FROM ERP_NHANHANG NH \n " +
					"		INNER JOIN ERP_NHANHANG_SANPHAM NHSP ON NHSP.MUAHANG_FK = NH.PK_SEQ  \n " +
					"		GROUP BY NHSP.SANPHAM_FK, NH.MUAHANG_FK \n " +
					"	)NH ON NH.SANPHAM_FK = MHSP.SANPHAM_FK AND NH.MUAHANG_FK = MHSP.MUAHANG_FK \n " +

					"	WHERE MHSP.NGAYNHAN >=  '" + n1 + "-" + (t1.length()== 1? "0" + t1 : t1) + "-01' \n  " +
					"	AND MHSP.NGAYNHAN <= '" + n1 + "-" + (t1.length()== 1? "0" + t1 : t1) + "-31'  AND MH.TRANGTHAI < 2 \n " +
					"	AND MHSP.SANPHAM_FK IS NOT NULL \n " +
					"	GROUP BY MHSP.SANPHAM_FK, NH.NHANHANG \n " +	
					")MH_THANG_1 ON MH_THANG_1.SPID = KHOTT_SP.SANPHAM_FK \n  " +

					"LEFT JOIN( \n  " +
					"	SELECT	MHSP.SANPHAM_FK AS SPID,  \n " +
					"	(SUM(ISNULL(MHSP.SOLUONG,0)) -  ISNULL(NH.NHANHANG, 0))  AS QTY   \n " +			
					"	FROM ERP_MUAHANG MH  \n   " +
					"	INNER JOIN ERP_MUAHANG_SP MHSP ON MHSP.MUAHANG_FK = MH.PK_SEQ  \n " +
					"	LEFT JOIN( \n " +
					"		SELECT NH.MUAHANG_FK, NHSP.SANPHAM_FK, SUM(ISNULL(NHSP.SOLUONGNHAN, 0)) AS NHANHANG \n " +
					"		FROM ERP_NHANHANG NH \n " +
					"		INNER JOIN ERP_NHANHANG_SANPHAM NHSP ON NHSP.MUAHANG_FK = NH.PK_SEQ  \n " +
					"		GROUP BY NHSP.SANPHAM_FK, NH.MUAHANG_FK \n " +
					"	)NH ON NH.SANPHAM_FK = MHSP.SANPHAM_FK AND NH.MUAHANG_FK = MHSP.MUAHANG_FK \n " +

					"	WHERE MHSP.NGAYNHAN >=  '" + n2 + "-" + (t2.length()== 1? "0" + t2 : t2) + "-01' \n  " +
					"	AND MHSP.NGAYNHAN <= '" + n2 + "-" + (t2.length()== 1? "0" + t2 : t2) + "-31'  AND MH.TRANGTHAI < 2 \n " +
					"	AND MHSP.SANPHAM_FK IS NOT NULL \n " +
					"	GROUP BY MHSP.SANPHAM_FK, NH.NHANHANG \n " +
					")MH_THANG_2 ON MH_THANG_2.SPID = KHOTT_SP.SANPHAM_FK \n " +

					"LEFT JOIN( \n " +
					"	SELECT	MHSP.SANPHAM_FK AS SPID, \n  " +
					"	(SUM(ISNULL(MHSP.SOLUONG,0)) -  ISNULL(NH.NHANHANG, 0))  AS QTY  \n  " +			
					"	FROM ERP_MUAHANG MH   \n  " +
					"	INNER JOIN ERP_MUAHANG_SP MHSP ON MHSP.MUAHANG_FK = MH.PK_SEQ \n " +
					"	LEFT JOIN( \n " +
					"		SELECT NH.MUAHANG_FK, NHSP.SANPHAM_FK, SUM(ISNULL(NHSP.SOLUONGNHAN, 0)) AS NHANHANG \n " +
					"		FROM ERP_NHANHANG NH \n " +
					"		INNER JOIN ERP_NHANHANG_SANPHAM NHSP ON NHSP.MUAHANG_FK = NH.PK_SEQ  \n " +
					"		GROUP BY NHSP.SANPHAM_FK, NH.MUAHANG_FK \n " +
					")NH ON NH.SANPHAM_FK = MHSP.SANPHAM_FK AND NH.MUAHANG_FK = MHSP.MUAHANG_FK \n " +

					"	WHERE MHSP.NGAYNHAN >=  '" + n3 + "-" + (t3.length()== 1? "0" + t3 : t3) + "-01' \n " +
					"	AND MHSP.NGAYNHAN <= '" + n3 + "-" + (t3.length()== 1? "0" + t3 : t3) + "-31'  AND MH.TRANGTHAI < 2 \n " +
					"	AND MHSP.SANPHAM_FK IS NOT NULL \n " +
					"	GROUP BY MHSP.SANPHAM_FK, NH.NHANHANG \n " +
					")MH_THANG_3 ON MH_THANG_3.SPID = KHOTT_SP.SANPHAM_FK \n " +

					"LEFT JOIN( \n " +
					"	SELECT	MHSP.SANPHAM_FK AS SPID, \n  " +
					"	(SUM(ISNULL(MHSP.SOLUONG,0)) -  ISNULL(NH.NHANHANG, 0))  AS QTY  \n  " +			
					"	FROM ERP_MUAHANG MH   \n  " +
					"	INNER JOIN ERP_MUAHANG_SP MHSP ON MHSP.MUAHANG_FK = MH.PK_SEQ \n " +
					"	LEFT JOIN( \n " +
					"		SELECT NH.MUAHANG_FK, NHSP.SANPHAM_FK, SUM(ISNULL(NHSP.SOLUONGNHAN, 0)) AS NHANHANG \n " +
					"		FROM ERP_NHANHANG NH \n " +
					"		INNER JOIN ERP_NHANHANG_SANPHAM NHSP ON NHSP.MUAHANG_FK = NH.PK_SEQ  \n " +
					"		GROUP BY NHSP.SANPHAM_FK, NH.MUAHANG_FK \n " +
					")NH ON NH.SANPHAM_FK = MHSP.SANPHAM_FK AND NH.MUAHANG_FK = MHSP.MUAHANG_FK \n " +

					"	WHERE MHSP.NGAYNHAN >=  '" + n4 + "-" + (t4.length()== 1? "0" + t4 : t4) + "-01' \n " +
					"	AND MHSP.NGAYNHAN <= '" + n4 + "-" + (t4.length()== 1? "0" + t4 : t4) + "-31'  AND MH.TRANGTHAI < 2 \n " +
					"	AND MHSP.SANPHAM_FK IS NOT NULL \n " +
					"	GROUP BY MHSP.SANPHAM_FK, NH.NHANHANG \n " +
					")MH_THANG_4 ON MH_THANG_4.SPID = KHOTT_SP.SANPHAM_FK \n " +

					"WHERE SP.LOAISANPHAM_FK IN (100000, 100008, 100013)  \n " + 
					"AND KHOTT_SP.KHOTT_FK IN (100014, 100016, 100017, 100018, 100019, 100020, 100049) " +
					"GROUP BY KHOTT_SP.SANPHAM_FK, SP.TEN, YCNLSX_THANG_0.YCNL, YCNLSX_THANG_1.YCNL, MH_THANG_0.QTY, MH_THANG_1.QTY, YCNLSX_THANG_2.YCNL, " +
					"MH_THANG_2.QTY, YCNLSX_THANG_3.YCNL, MH_THANG_3.QTY, YCNLSX_THANG_4.YCNL, MH_THANG_4.QTY";

			//YÊU CẦU ĐỔI LẠI, ĐỀ NGHỊ CHỈ RA CỦA THÁNG CHẠY DỰ BÁO, KHÔNG RA THEO TỪNG THÁNG
			query = "select SPID, TEN, TONHIENTAI, TONANTOAN, YC_NHAN_NL_THANG_0 + YC_NHAN_NL_THANG_1 + YC_NHAN_NL_THANG_2 + YC_NHAN_NL_THANG_3 + YC_NHAN_NL_THANG_4 as YC_NHAN_NL_THANG_0 " + 
					"from ( " + query + " ) DT ";
			
			System.out.println("::: YEU CAU NL: " + query);
			ResultSet rs = this.db.get(query);
			if(rs != null)
			{
				try
				{
					while(rs.next())
					{						
						if(rs.getDouble("YC_NHAN_NL_THANG_0") > 0)
						{
							query = "INSERT INTO ERP_YEUCAUNHANNGUYENLIEU(NHAMAY_FK, CONGTY_FK, SANPHAM_FK, THANG, NAM, SOLUONG, SOLUONG_YC,  NGAYTAO, NGUOITAO, NGAYSUA, NGUOISUA, TRANGTHAI) VALUES (" +
									"(SELECT TOP 1 NHAMAY_FK FROM ERP_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU WHERE NGUYENLIEU_FK = " + rs.getString("SPID") + "), " +
									"" + this.CTYID + ", '" + rs.getString("SPID") + "', '" + t0 + "', '" + n0 + "', " + rs.getDouble("YC_NHAN_NL_THANG_0") + ", " + rs.getDouble("YC_NHAN_NL_THANG_0") + ", " +
									"'" + this.getDateTime() + "', " + this.USERID + ", '" + this.getDateTime() + "', " + this.USERID + ", '1' )";
							//System.out.println(query);
							this.db.update(query);
						}

						/*if(rs.getDouble("YC_NHAN_NL_THANG_1") > 0)
						{
							query = "INSERT INTO ERP_YEUCAUNHANNGUYENLIEU(NHAMAY_FK, CONGTY_FK, SANPHAM_FK, THANG, NAM, SOLUONG,  NGAYTAO, NGUOITAO, NGAYSUA, NGUOISUA, TRANGTHAI) VALUES (" +
									"(SELECT TOP 1 NHAMAY_FK FROM ERP_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU WHERE NGUYENLIEU_FK = " + rs.getString("SPID") + "), " +
									"" + this.CTYID + ", '" + rs.getString("SPID") + "', '" + t1 + "', '" + n1 + "', " + rs.getDouble("YC_NHAN_NL_THANG_1") + ", " +
									"'" + this.getDateTime() + "', " + this.USERID + ", '" + this.getDateTime() + "', " + this.USERID + ", '1' )";
							//System.out.println(query);
							this.db.update(query);
						}

						if(rs.getDouble("YC_NHAN_NL_THANG_2") > 0)
						{
							query = "INSERT INTO ERP_YEUCAUNHANNGUYENLIEU(NHAMAY_FK, CONGTY_FK, SANPHAM_FK, THANG, NAM, SOLUONG,  NGAYTAO, NGUOITAO, NGAYSUA, NGUOISUA, TRANGTHAI) VALUES (" +
									"(SELECT TOP 1 NHAMAY_FK FROM ERP_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU WHERE NGUYENLIEU_FK = " + rs.getString("SPID") + "), " +
									"" + this.CTYID + ", '" + rs.getString("SPID") + "', '" + t2 + "', '" + n2 + "', " + rs.getDouble("YC_NHAN_NL_THANG_2") + ", " +
									"'" + this.getDateTime() + "', " + this.USERID + ", '" + this.getDateTime() + "', " + this.USERID + ", '1')";
							//System.out.println(query);
							this.db.update(query);
						}

						if(rs.getDouble("YC_NHAN_NL_THANG_3") > 0)
						{
							query = "INSERT INTO ERP_YEUCAUNHANNGUYENLIEU(NHAMAY_FK, CONGTY_FK, SANPHAM_FK, THANG, NAM, SOLUONG,  NGAYTAO, NGUOITAO, NGAYSUA, NGUOISUA, TRANGTHAI) VALUES (" +
									"(SELECT TOP 1 NHAMAY_FK FROM ERP_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU WHERE NGUYENLIEU_FK = " + rs.getString("SPID") + "), " +
									"" + this.CTYID + ", '" + rs.getString("SPID") + "', '" + t3 + "', '" + n3 + "', " + rs.getDouble("YC_NHAN_NL_THANG_3") + ", " +
									"'" + this.getDateTime() + "', " + this.USERID + ", '" + this.getDateTime() + "', " + this.USERID + ", '1' )";
							//System.out.println(query);
							this.db.update(query);
						}

						if(rs.getDouble("YC_NHAN_NL_THANG_4") > 0)
						{
							query = "INSERT INTO ERP_YEUCAUNHANNGUYENLIEU(NHAMAY_FK, CONGTY_FK, SANPHAM_FK, THANG, NAM, SOLUONG,  NGAYTAO, NGUOITAO, NGAYSUA, NGUOISUA, TRANGTHAI) VALUES (" +
									"(SELECT TOP 1 NHAMAY_FK FROM ERP_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU WHERE NGUYENLIEU_FK = " + rs.getString("SPID") + "), " +
									"" + this.CTYID + ", '" + rs.getString("SPID") + "', '" + t4 + "', '" + n4 + "', " + rs.getDouble("YC_NHAN_NL_THANG_4") + ", " +
									"'" + this.getDateTime() + "', " + this.USERID + ", '" + this.getDateTime() + "', " + this.USERID + ", '1' )";
							//System.out.println(query);
							this.db.update(query);
						}*/

					}
					rs.close();
					
					//QUY ĐỔI RA HÀM LƯỢNG, HÀM ẨM CẦN ĐẶT
					query = "  update yc  " + 
							"\n  	set yc.isTINHHAMAM = lsx_nl.isTINHHAMAM, yc.isTINHHAMLUONG = lsx_nl.isTINHHAMLUONG, " + 
							"  		yc.HAMAM = isnull(lsx_nl.HAMAM, 0), yc.HAMLUONG = isnull(lsx_nl.HAMLUONG, 100) " + 
							//"  from ERP_YEUCAUNHANNGUYENLIEU yc inner join ERP_LENHSANXUATDUKIEN lsx on yc.THANG = MONTH( lsx.NGAYBATDAU ) and yc.NAM = YEAR( lsx.NGAYBATDAU ) " + 
							"\n  from ERP_YEUCAUNHANNGUYENLIEU yc inner join ERP_LENHSANXUATDUKIEN lsx on yc.THANG = '" + t0 + "' and yc.NAM = '" + n0 + "' " + 
							"\n  		inner join ERP_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU lsx_nl on lsx.PK_SEQ = lsx_nl.LENHSANXUATDUKIEN_FK and yc.SANPHAM_FK = lsx_nl.NGUYENLIEU_FK " + 
							"\n  where yc.SANPHAM_FK in ( select PK_SEQ from ERP_SANPHAM where LOAISANPHAM_FK = '100000' ) and ( lsx_nl.isTINHHAMAM = 1 or lsx_nl.isTINHHAMLUONG = 1 ) " + 
							"\n 		and month(lsx.ngaybatdau) in ( " + t0 + ", " + t1 + ", " + t2 + ", " + t3 + ", " + t4 + " ) and year(lsx.ngaybatdau) in ( " + n0 + ", " + n1 + ", " + n2 + ", " + n3 + ", " + n4 + " ) ";
					System.out.println("::: CAP NHAT QUY DOI: " + query);
					db.update(query);
					
					query = "  select PK_SEQ, SANPHAM_FK, SOLUONG, THANG, NAM, isTINHHAMAM, isTINHHAMLUONG, HAMAM, HAMLUONG,  " + 
							"  	isnull( ( select MAX( cast(hamam as float) ) from ERP_KHOTT_SP_CHITIET where SANPHAM_FK = yc.SANPHAM_FK ), 0 ) as max_hamam,   " + 
							"  	isnull( ( select MIN( cast(hamluong as float) ) from ERP_KHOTT_SP_CHITIET where SANPHAM_FK = yc.SANPHAM_FK ), 100 ) as min_hamluong " + 
							"  from ERP_YEUCAUNHANNGUYENLIEU yc  " + 
							"  where ( isTINHHAMAM = 1 or isTINHHAMLUONG = 1 ) and yc.SANPHAM_FK in ( select PK_SEQ from ERP_SANPHAM where LOAISANPHAM_FK = '100000' )  " + 
							" 		and yc.THANG in ( " + t0 + ", " + t1 + ", " + t2 + ", " + t3 + ", " + t4 + " ) and yc.NAM in ( " + n0 + ", " + n1 + ", " + n2 + ", " + n3 + ", " + n4 + " ) " +
							"  order by SANPHAM_FK asc, THANG asc, NAM asc  ";
					
					System.out.println("::: LAY YEU CAU CAN QUY DOI: " + query);
					rs = db.get(query);
					if( rs != null )
					{
						while( rs.next() )
						{
							String ycsxId = rs.getString("PK_SEQ");
							int tinhHamam = rs.getInt("isTINHHAMAM");
							int tinhHamluong = rs.getInt("isTINHHAMLUONG");
							double a2_hamam_dinhmuc = rs.getDouble("HAMAM");
							double b2_hamluong_dinhmuc = rs.getDouble("HAMLUONG");
							double dinhmuc = rs.getDouble("SOLUONG");
							
							double a1_hamam_thucte = 0;
							double b1_hamluong_thucte = 0;
							if( tinhHamam == 1 )
								a1_hamam_thucte = rs.getDouble("max_hamam");
							else
								b1_hamluong_thucte = rs.getDouble("min_hamluong");
							
							double khoiluongYC = this.TinhSoLuong(dinhmuc, a2_hamam_dinhmuc, a1_hamam_thucte, b2_hamluong_dinhmuc, b1_hamluong_thucte, tinhHamluong, tinhHamam);
							
							query = "update ERP_YEUCAUNHANNGUYENLIEU set HAMAM_YC = '" + rs.getString("max_hamam") + "', HAMLUONG_YC = '" + rs.getString("min_hamluong") + "', SOLUONG_YC = '" + khoiluongYC + "' " + 
									" where pk_seq = '" + ycsxId + "' ";
							
							System.out.println("::: THUC TE: " + query);
							db.update(query);
							
						}
						rs.close();
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	
		private void TaoDonMuaNguyenLieuDukien(String thang, String nam)
		{
			
			String query = "";
			
			String t0 = "", t1 = "", t2 = "", t3 = "", t4 = ""; 
			String n0 = "", n1 = "", n2 = "", n3 = "", n4 = "";
			t1 = thang;
			n1 = nam;
			
			if(t1.equals("1")){
				t0 = "12";
				n0 = "" + (Integer.parseInt(n1) - 1);
			}else{
				t0 = "" + (Integer.parseInt(t1) - 1);
				n0 = n1;
			}
			
			if(t1.equals("12")){
				t2 = "1";
				n2 = "" + (Integer.parseInt(n1) + 1);
			}else{
				t2 = "" + (Integer.parseInt(t1) + 1);
				n2 = n1;
			}
			
			if(t2.equals("12")){
				t3 = "1";
				n3 = "" + (Integer.parseInt(n2) + 1);
			}else{
				t3 = "" + (Integer.parseInt(t2) + 1);
				n3 = n2;
			}
			
			if(t3.equals("12")){
				t4 = "1";
				n4 = "" + (Integer.parseInt(n3) + 1);
			}else{
				t4 = "" + (Integer.parseInt(t3) + 1);
				n4 = n3;
			}
						
			query = "DELETE ERP_DONMUAHANGDUKIEN " +
					"WHERE " +
					"SUBSTRING(NGAYNHANHANG, 0, 8) = '" + n0 + "-" + (t0.length() == 1?"0" + t0: t0) + "'";
			System.out.println(query);
			this.db.update(query);
			
			
			query = "DELETE ERP_DONMUAHANGDUKIEN " +
					"WHERE " +
					"SUBSTRING(NGAYNHANHANG, 0, 8) = '" + n1 + "-" + (t1.length() == 1?"0" + t1: t1) + "'";
			System.out.println(query);
			this.db.update(query);
			
			query = "DELETE ERP_DONMUAHANGDUKIEN " +
					"WHERE " +
					"SUBSTRING(NGAYNHANHANG, 0, 8) = '" + n2 + "-" + (t2.length() == 1?"0" + t2: t2) + "'";
			System.out.println(query);
			this.db.update(query);
			
			query = "DELETE ERP_DONMUAHANGDUKIEN " +
					"WHERE " +
					"SUBSTRING(NGAYNHANHANG, 0, 8) = '" + n3 + "-" + (t3.length() == 1?"0" + t3: t3) + "'";
			System.out.println(query);
			this.db.update(query);
			
			query = "DELETE ERP_DONMUAHANGDUKIEN " +
					"WHERE " +
					"SUBSTRING(NGAYNHANHANG, 0, 8) = '" + n4 + "-" + (t4.length() == 1?"0" + t4: t4) + "'";
			System.out.println(query);
			this.db.update(query);
			
			
			query = "SELECT * FROM ( \n " +
					"SELECT YC_NHAN_NL.PK_SEQ AS YCID, YC_NHAN_NL.SANPHAM_FK AS SPID, YC_NHAN_NL.SOLUONG, YC_NHAN_NL.SOLUONG_YC, YC_NHAN_NL.HAMAM_YC, YC_NHAN_NL.HAMLUONG_YC, YC_NHAN_NL.THANG, YC_NHAN_NL.NAM, \n " +
					"ISNULL(MIN(TTDH.LEADTIME), '0')  AS LEADTIME, ISNULL(TTDH.MOU, 0) AS MOU, NCC.PK_SEQ AS NCCID \n " +
					"FROM ERP_YEUCAUNHANNGUYENLIEU YC_NHAN_NL \n " +
					"LEFT JOIN ERP_THONGTINDATHANG TTDH ON TTDH.SANPHAM_FK = YC_NHAN_NL.SANPHAM_FK \n " +
					"LEFT JOIN ERP_NHACUNGCAP NCC ON NCC.PK_SEQ = TTDH.NCC_FK \n " +
					"WHERE YC_NHAN_NL.NAM = " + n0 + " AND YC_NHAN_NL.THANG = " + t0 + " AND YC_NHAN_NL.CONGTY_FK = " + this.CTYID + " \n " +
					"GROUP BY MOU, YC_NHAN_NL.PK_SEQ, YC_NHAN_NL.SANPHAM_FK, YC_NHAN_NL.SOLUONG, YC_NHAN_NL.SOLUONG_YC, YC_NHAN_NL.HAMAM_YC, YC_NHAN_NL.HAMLUONG_YC, YC_NHAN_NL.THANG, YC_NHAN_NL.NAM,NCC.PK_SEQ \n " +
					
					"UNION ALL \n " +
			
					"SELECT YC_NHAN_NL.PK_SEQ AS YCID, YC_NHAN_NL.SANPHAM_FK AS SPID, YC_NHAN_NL.SOLUONG, YC_NHAN_NL.SOLUONG_YC, YC_NHAN_NL.HAMAM_YC, YC_NHAN_NL.HAMLUONG_YC, YC_NHAN_NL.THANG, YC_NHAN_NL.NAM, \n " +
					"ISNULL(MIN(TTDH.LEADTIME), '0')  AS LEADTIME, ISNULL(TTDH.MOU, 0) AS MOU, NCC.PK_SEQ AS NCCID \n " +
					"FROM ERP_YEUCAUNHANNGUYENLIEU YC_NHAN_NL \n " +
					"LEFT JOIN ERP_THONGTINDATHANG TTDH ON TTDH.SANPHAM_FK = YC_NHAN_NL.SANPHAM_FK \n " +
					"LEFT JOIN ERP_NHACUNGCAP NCC ON NCC.PK_SEQ = TTDH.NCC_FK \n " +
					"WHERE YC_NHAN_NL.NAM = " + n1 + " AND YC_NHAN_NL.THANG = " + t1 + " AND YC_NHAN_NL.CONGTY_FK = " + this.CTYID + " \n " +
					"GROUP BY MOU, YC_NHAN_NL.PK_SEQ, YC_NHAN_NL.SANPHAM_FK, YC_NHAN_NL.SOLUONG, YC_NHAN_NL.SOLUONG_YC, YC_NHAN_NL.HAMAM_YC, YC_NHAN_NL.HAMLUONG_YC, YC_NHAN_NL.THANG, YC_NHAN_NL.NAM,NCC.PK_SEQ \n " +
					
					"UNION ALL \n " +
			
					"SELECT YC_NHAN_NL.PK_SEQ AS YCID, YC_NHAN_NL.SANPHAM_FK AS SPID, YC_NHAN_NL.SOLUONG, YC_NHAN_NL.SOLUONG_YC, YC_NHAN_NL.HAMAM_YC, YC_NHAN_NL.HAMLUONG_YC, YC_NHAN_NL.THANG, YC_NHAN_NL.NAM, \n " +
					"ISNULL(MIN(TTDH.LEADTIME), '0')  AS LEADTIME, ISNULL(TTDH.MOU, 0) AS MOU, NCC.PK_SEQ AS NCCID \n " +
					"FROM ERP_YEUCAUNHANNGUYENLIEU YC_NHAN_NL \n " +
					"LEFT JOIN ERP_THONGTINDATHANG TTDH ON TTDH.SANPHAM_FK = YC_NHAN_NL.SANPHAM_FK \n " +
					"LEFT JOIN ERP_NHACUNGCAP NCC ON NCC.PK_SEQ = TTDH.NCC_FK \n " +
					"WHERE YC_NHAN_NL.NAM = " + n2+ " AND YC_NHAN_NL.THANG = " + t2 + " AND YC_NHAN_NL.CONGTY_FK = " + this.CTYID + " \n " +
					"GROUP BY MOU, YC_NHAN_NL.PK_SEQ, YC_NHAN_NL.SANPHAM_FK, YC_NHAN_NL.SOLUONG, YC_NHAN_NL.SOLUONG_YC, YC_NHAN_NL.HAMAM_YC, YC_NHAN_NL.HAMLUONG_YC, YC_NHAN_NL.THANG, YC_NHAN_NL.NAM,NCC.PK_SEQ \n " +
					
					"UNION ALL \n " +
			
					"SELECT YC_NHAN_NL.PK_SEQ AS YCID, YC_NHAN_NL.SANPHAM_FK AS SPID, YC_NHAN_NL.SOLUONG, YC_NHAN_NL.SOLUONG_YC, YC_NHAN_NL.HAMAM_YC, YC_NHAN_NL.HAMLUONG_YC, YC_NHAN_NL.THANG, YC_NHAN_NL.NAM, \n " +
					"ISNULL(MIN(TTDH.LEADTIME), '0')  AS LEADTIME, ISNULL(TTDH.MOU, 0) AS MOU, NCC.PK_SEQ AS NCCID \n " +
					"FROM ERP_YEUCAUNHANNGUYENLIEU YC_NHAN_NL \n " +
					"LEFT JOIN ERP_THONGTINDATHANG TTDH ON TTDH.SANPHAM_FK = YC_NHAN_NL.SANPHAM_FK \n " +
					"LEFT JOIN ERP_NHACUNGCAP NCC ON NCC.PK_SEQ = TTDH.NCC_FK \n " +
					"WHERE YC_NHAN_NL.NAM = " + n3 + " AND YC_NHAN_NL.THANG = " + t3 + " AND YC_NHAN_NL.CONGTY_FK = " + this.CTYID + " \n " +
					"GROUP BY MOU, YC_NHAN_NL.PK_SEQ, YC_NHAN_NL.SANPHAM_FK, YC_NHAN_NL.SOLUONG, YC_NHAN_NL.SOLUONG_YC, YC_NHAN_NL.HAMAM_YC, YC_NHAN_NL.HAMLUONG_YC, YC_NHAN_NL.THANG, YC_NHAN_NL.NAM,NCC.PK_SEQ \n " +
					
					"UNION ALL \n " +
			
					"SELECT YC_NHAN_NL.PK_SEQ AS YCID, YC_NHAN_NL.SANPHAM_FK AS SPID, YC_NHAN_NL.SOLUONG, YC_NHAN_NL.SOLUONG_YC, YC_NHAN_NL.HAMAM_YC, YC_NHAN_NL.HAMLUONG_YC, YC_NHAN_NL.THANG, YC_NHAN_NL.NAM, \n " +
					"ISNULL(MIN(TTDH.LEADTIME), '0')  AS LEADTIME, ISNULL(TTDH.MOU, 0) AS MOU, NCC.PK_SEQ AS NCCID \n " +
					"FROM ERP_YEUCAUNHANNGUYENLIEU YC_NHAN_NL \n " +
					"LEFT JOIN ERP_THONGTINDATHANG TTDH ON TTDH.SANPHAM_FK = YC_NHAN_NL.SANPHAM_FK \n " +
					"LEFT JOIN ERP_NHACUNGCAP NCC ON NCC.PK_SEQ = TTDH.NCC_FK \n " +
					"WHERE YC_NHAN_NL.NAM = " + n4 + " AND YC_NHAN_NL.THANG = " + t4 + " AND YC_NHAN_NL.CONGTY_FK = " + this.CTYID + " \n " +
					"GROUP BY MOU, YC_NHAN_NL.PK_SEQ, YC_NHAN_NL.SANPHAM_FK, YC_NHAN_NL.SOLUONG, YC_NHAN_NL.SOLUONG_YC, YC_NHAN_NL.HAMAM_YC, YC_NHAN_NL.HAMLUONG_YC, YC_NHAN_NL.THANG, YC_NHAN_NL.NAM,NCC.PK_SEQ \n " +
					
					")DATA ORDER BY SPID, CONVERT(INT, NAM), CONVERT(INT, THANG) ASC \n "; //WHERE SPID = 108798 
			
			System.out.println("::: DON MUA DU KIEN: " + query);
			ResultSet ycNL = this.db.get(query);
			if(ycNL != null){
				try{
					String ycId = "";
					String spId = "";
					String spId_bk = "";
					double soluong;
					double MOU;
					String leadtime;
					String nccId;
					int batdau;
					String ngaycan;
					int isMOU = 0;
					double dadatdu = 0;
					while(ycNL.next())
					{
						
						ycId = ycNL.getString("YCID");
						spId = ycNL.getString("SPID");
			
						if(!spId_bk.equals(spId)){
							spId_bk = ycNL.getString("SPID");
							isMOU = 0;
							dadatdu = 0;
						}
						soluong = ycNL.getDouble("SOLUONG");
						MOU = ycNL.getDouble("MOU") ;
						nam = ycNL.getString("NAM");
						thang = ycNL.getString("THANG");
						leadtime = ycNL.getString("LEADTIME") == null?"0":ycNL.getString("LEADTIME");
						nccId = ycNL.getString("NCCID");
						batdau = 1;
						ngaycan = "" + nam + "-" + (thang.length() == 1?("0" + thang):thang) + "-01";
			
						double SOLUONG_YC = ycNL.getString("SOLUONG_YC") == null ? 0 : ycNL.getDouble("SOLUONG_YC");
						double HAMAM_YC = ycNL.getString("SOLUONG_YC") == null ? 0 : ycNL.getDouble("HAMAM_YC");
						double HAMLUONG_YC = ycNL.getString("SOLUONG_YC") == null ? 100 : ycNL.getDouble("HAMLUONG_YC");
						
						if(MOU > 0 )
						{
							if(soluong >= MOU )
							{
								if(isMOU == 0)
								{
									query = "INSERT INTO ERP_DONMUAHANGDUKIEN(YEUCAUNHANNGUYENLIEU_FK, SANPHAM_FK, NCC_FK, SOLUONG, SOLUONG_YC, HAMAM_YC, HAMLUONG_YC, DATMUA, NGAYDATHANG, " +
											"NGAYNHANHANG,  NGAYDATHANG_DC, \n " +
											"NGAYTAO, NGUOITAO, NGAYSUA, NGUOISUA, CONGTY_FK, TINHTRANG) " +
											"VALUES(" + ycId + ", " + spId + ", " + nccId + ", " + soluong + ", '" + SOLUONG_YC + "', '" + HAMAM_YC + "', '" + HAMLUONG_YC + "', 0,  \n " +
											"'" + this.getDateTime() + "', \n " +
											"(SELECT CONVERT(VARCHAR(10), DATEADD(DAY, +" + leadtime + ", '" + this.getDateTime() + "'), 120)),  \n " +
											"(SELECT CONVERT(VARCHAR(10), DATEADD(DAY, +" + leadtime + ", '" + this.getDateTime()  + "'), 120)),  \n " +
											"'" + this.getDateTime() + "', '" + this.USERID + "', '" + this.getDateTime() + "', '" + this.USERID + "', " + this.CTYID + ", 0)";
									//System.out.println("1: " + query);
									this.db.update(query);
								}
								else
								{
									if(soluong > dadatdu)
									{
										soluong = soluong - dadatdu;
										if(soluong >= MOU & soluong > 0)
										{
											query = "INSERT INTO ERP_DONMUAHANGDUKIEN(YEUCAUNHANNGUYENLIEU_FK, SANPHAM_FK, NCC_FK, SOLUONG, SOLUONG_YC, HAMAM_YC, HAMLUONG_YC, DATMUA, NGAYDATHANG, \n " +
													"NGAYNHANHANG,  NGAYDATHANG_DC, \n " +
													"NGAYTAO, NGUOITAO, NGAYSUA, NGUOISUA, CONGTY_FK, TINHTRANG) " +
													"VALUES(" + ycId + ", " + spId + ", " + nccId + ", " + soluong + ", '" + SOLUONG_YC + "', '" + HAMAM_YC + "', '" + HAMLUONG_YC + "', 0,  \n " +
													"'" + this.getDateTime() + "', \n " +
													"(SELECT CONVERT(VARCHAR(10), DATEADD(DAY, +" + leadtime + ", '" + this.getDateTime() + "'), 120)),  \n " +
													"(SELECT CONVERT(VARCHAR(10), DATEADD(DAY, +" + leadtime + ", '" + this.getDateTime()  + "'), 120)),  \n " +
													"'" + this.getDateTime() + "', '" + this.USERID + "', '" + this.getDateTime() + "', '" + this.USERID + "', " + this.CTYID + ", 0)";
											//System.out.println("2: " +query);
											this.db.update(query);
											dadatdu = 0;
											isMOU = 0;
										}
										else
										{
											query = "INSERT INTO ERP_DONMUAHANGDUKIEN(YEUCAUNHANNGUYENLIEU_FK, SANPHAM_FK, NCC_FK, SOLUONG, SOLUONG_YC, HAMAM_YC, HAMLUONG_YC, DATMUA, NGAYDATHANG, \n " +
													"NGAYNHANHANG,  NGAYDATHANG_DC, \n " +
													"NGAYTAO, NGUOITAO, NGAYSUA, NGUOISUA, CONGTY_FK, TINHTRANG) " +
													"VALUES(" + ycId + ", " + spId + ", " + nccId + ", " + MOU + ", '" + SOLUONG_YC + "', '" + HAMAM_YC + "', '" + HAMLUONG_YC + "', 0,  \n " +
													"'" + this.getDateTime() + "', \n " +
													"(SELECT CONVERT(VARCHAR(10), DATEADD(DAY, +" + leadtime + ", '" + this.getDateTime() + "'), 120)),  \n " +
													"(SELECT CONVERT(VARCHAR(10), DATEADD(DAY, +" + leadtime + ", '" + this.getDateTime()  + "'), 120)),  \n " +
													"'" + this.getDateTime() + "', '" + this.USERID + "', '" + this.getDateTime() + "', '" + this.USERID + "', " + this.CTYID + ", 0)";
											//System.out.println("3: " + query);
											this.db.update(query);
											dadatdu = MOU - soluong;
										
										}
									}
									else
									{
										dadatdu = dadatdu - soluong;
									}
								}
	
							}else{
								if(isMOU == 0){
									isMOU = 1;
									dadatdu = MOU - soluong;
									query = "INSERT INTO ERP_DONMUAHANGDUKIEN(YEUCAUNHANNGUYENLIEU_FK, SANPHAM_FK, NCC_FK, SOLUONG, SOLUONG_YC, HAMAM_YC, HAMLUONG_YC,  DATMUA, NGAYDATHANG, \n " +
											"NGAYNHANHANG,  NGAYDATHANG_DC, \n " +
											"NGAYTAO, NGUOITAO, NGAYSUA, NGUOISUA, CONGTY_FK, TINHTRANG) " +
											"VALUES(" + ycId + ", " + spId + ", " + nccId + ", " + MOU + ", '" + SOLUONG_YC + "', '" + HAMAM_YC + "', '" + HAMLUONG_YC + "', 0,  \n " +
											"'" + this.getDateTime() + "', \n " +
											"(SELECT CONVERT(VARCHAR(10), DATEADD(DAY, +" + leadtime + ", '" + this.getDateTime() + "'), 120)),  \n " +
											"(SELECT CONVERT(VARCHAR(10), DATEADD(DAY, +" + leadtime + ", '" + this.getDateTime()  + "'), 120)),  \n " +
											"'" + this.getDateTime() + "', '" + this.USERID + "', '" + this.getDateTime() + "', '" + this.USERID + "', " + this.CTYID + ", 0)";
									//System.out.println("4: " +query);
									this.db.update(query);
										
								}else{
									dadatdu = dadatdu - soluong;
									if(dadatdu < 0){
										query = "INSERT INTO ERP_DONMUAHANGDUKIEN(YEUCAUNHANNGUYENLIEU_FK, SANPHAM_FK, NCC_FK, SOLUONG, SOLUONG_YC, HAMAM_YC, HAMLUONG_YC,  DATMUA, NGAYDATHANG, \n " +
												"NGAYNHANHANG,  NGAYDATHANG_DC, \n " +
												"NGAYTAO, NGUOITAO, NGAYSUA, NGUOISUA, CONGTY_FK, TINHTRANG) " +
												"VALUES(" + ycId + ", " + spId + ", " + nccId + ", " + MOU + ", '" + SOLUONG_YC + "', '" + HAMAM_YC + "', '" + HAMLUONG_YC + "', 0,  \n " +
												"'" + this.getDateTime() + "', \n " +
												"(SELECT CONVERT(VARCHAR(10), DATEADD(DAY, +" + leadtime + ", '" + this.getDateTime() + "'), 120)),  \n " +
												"(SELECT CONVERT(VARCHAR(10), DATEADD(DAY, +" + leadtime + ", '" + this.getDateTime()  + "'), 120)),  \n " +
												"'" + this.getDateTime() + "', '" + this.USERID + "', '" + this.getDateTime() + "', '" + this.USERID + "', " + this.CTYID + ", 0)";
										//System.out.println("5: " +query);
										this.db.update(query);
										dadatdu = dadatdu + MOU;
									}
									
								}
							}
						}else{
							if(soluong > 0){
							query = "INSERT INTO ERP_DONMUAHANGDUKIEN(YEUCAUNHANNGUYENLIEU_FK, SANPHAM_FK, NCC_FK, SOLUONG, SOLUONG_YC, HAMAM_YC, HAMLUONG_YC,  DATMUA, NGAYDATHANG, \n " +
									"NGAYNHANHANG,  NGAYDATHANG_DC, \n " +
									"NGAYTAO, NGUOITAO, NGAYSUA, NGUOISUA, CONGTY_FK, TINHTRANG) " +
									"VALUES(" + ycId + ", " + spId + ", " + nccId + ", " + soluong + ", '" + SOLUONG_YC + "', '" + HAMAM_YC + "', '" + HAMLUONG_YC + "', 0,  \n " +
									"'" + this.getDateTime() + "', \n " +
									"(SELECT CONVERT(VARCHAR(10), DATEADD(DAY, +" + leadtime + ", '" + this.getDateTime() + "'), 120)),  \n " +
									"(SELECT CONVERT(VARCHAR(10), DATEADD(DAY, +" + leadtime + ", '" + this.getDateTime()  + "'), 120)),  \n " +
									"'" + this.getDateTime() + "', '" + this.USERID + "', '" + this.getDateTime() + "', '" + this.USERID + "', " + this.CTYID + ", 0)";
							//System.out.println("6: " +query);
							this.db.update(query);
							}
						}
						
					}
					ycNL.close();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				
			
			query = 
					"	SELECT DMHDK.PK_SEQ AS ID, ISNULL(TTDH.LEADTIME, 0) AS LEADTIME  \n " +
					"	FROM ERP_DONMUAHANGDUKIEN DMHDK \n " +
					"	INNER JOIN ERP_YEUCAUNHANNGUYENLIEU YC ON YC.PK_SEQ = DMHDK.YEUCAUNHANNGUYENLIEU_FK AND YC.SANPHAM_FK = DMHDK.SANPHAM_FK \n " +
					"	LEFT JOIN ERP_THONGTINDATHANG TTDH ON TTDH.SANPHAM_FK = YC.SANPHAM_FK  \n " +
					"	LEFT JOIN ERP_NHACUNGCAP NCC ON NCC.PK_SEQ = TTDH.NCC_FK  \n " +
					"	WHERE DMHDK.NGAYDATHANG < '" + this.getDateTime() + "'" ;
					
			
			System.out.println(query);
			ResultSet rs = this.db.get(query);
			if(rs != null){
				try{
					while(rs.next()){
						
						query = "UPDATE ERP_DONMUAHANGDUKIEN SET NGAYDATHANG = '" + this.getDateTime() + "', \n " +
								"NGAYNHANHANG = (SELECT CONVERT(VARCHAR(10), DATEADD(DAY, " + rs.getString("LEADTIME") + ", '" + this.getDateTime() + "'), 120)), \n " +
								"TINHTRANG = 1 WHERE PK_SEQ = " + rs.getString("ID") + "";
						System.out.println(query);
						this.db.update(query);
						
					}
					rs.close();
				}catch(java.sql.SQLException e){}
			}
			
			/*query = "SELECT * FROM \n " +
					"( \n " +
			
					"	SELECT '0' AS LOAI, SANPHAM_FK AS SPID, " + t0 + " AS THANG, " + n0 + " AS NAM, SUM(TONANTOAN - AVAILABLE) AS SOLUONG, 0 AS ID \n " +
					"	FROM ERP_KHOTT_SANPHAM KHOSP \n " +
					"	INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = KHOSP.SANPHAM_FK \n " +
					"	WHERE SP.LOAISANPHAM_FK IN (100000, 100003, 100008, 100013) \n " +
					"	AND KHOSP.KHOTT_FK IN (100014, 100016, 100017, 100018, 100019, 100020, 100049) \n " +
					"	GROUP BY SANPHAM_FK \n " +
			
					"	UNION ALL \n " +
					"	SELECT '1' AS LOAI, SANPHAM_FK, CASE WHEN (THANG = 1) THEN 12 ELSE THANG - 1 END AS THANG, CASE WHEN (THANG = 1) THEN NAM - 1 ELSE NAM END AS NAM, SUM(SOLUONG) AS SOLUONG, 0 AS ID \n " +
					"	FROM ERP_YEUCAUNHANNGUYENLIEU  \n " +
					"	GROUP BY SANPHAM_FK, NAM, THANG \n " +
					
					"	UNION ALL \n " +
					"	SELECT '2' AS LOAI, SANPHAM_FK, CONVERT(INT, SUBSTRING(NGAYNHANHANG, 6, 2)) AS THANG, SUBSTRING(NGAYNHANHANG, 0, 5) AS NAM, SOLUONG, PK_SEQ AS ID \n " +
					"	FROM ERP_DONMUAHANGDUKIEN  \n " +
										
					"	UNION ALL	 \n " +								
					"	SELECT	'3' AS LOAI, MHSP.SANPHAM_FK, CONVERT(VARCHAR, SUBSTRING(MHSP.NGAYNHAN, 6, 2)) AS THANG, SUBSTRING(MHSP.NGAYNHAN, 1, 4) AS NAM, \n " +
					"	(SUM(ISNULL(MHSP.SOLUONG,0)) -  ISNULL(NH.NHANHANG, 0))  AS SOLUONG, MH.PK_SEQ AS ID  \n " + 			
					"	FROM ERP_MUAHANG MH   \n " + 
					"	INNER JOIN ERP_MUAHANG_SP MHSP ON MHSP.MUAHANG_FK = MH.PK_SEQ  \n " +
					"	LEFT JOIN( \n " +
					"		SELECT NH.MUAHANG_FK, NHSP.SANPHAM_FK, SUM(ISNULL(NHSP.SOLUONGNHAN, 0)) AS NHANHANG \n " +
					"		FROM ERP_NHANHANG NH \n " +
					"		INNER JOIN ERP_NHANHANG_SANPHAM NHSP ON NHSP.MUAHANG_FK = NH.PK_SEQ  \n " +
					"		GROUP BY NHSP.SANPHAM_FK, NH.MUAHANG_FK \n " +
					"	)NH ON NH.SANPHAM_FK = MHSP.SANPHAM_FK AND NH.MUAHANG_FK = MHSP.MUAHANG_FK \n " +
			
					"	WHERE MH.TRANGTHAI < 2 \n " +
					"	AND MHSP.SANPHAM_FK IS NOT NULL \n " +
					"	GROUP BY MHSP.SANPHAM_FK, MHSP.NGAYNHAN, NH.NHANHANG, MH.PK_SEQ \n " +	
					"   HAVING (SUM(ISNULL(MHSP.SOLUONG,0)) -  ISNULL(NH.NHANHANG, 0)) > 0 \n " +
			
					")DATA  \n " +
					"WHERE (NAM > " + n0 + " or (THANG >= " + t0 + " and NAM = " + n0 + ")) " +
					"ORDER BY SPID, CONVERT(INT, NAM), CONVERT(INT, THANG), LOAI" ;
			
			System.out.println(query);
			rs = this.db.get(query);
			if(rs != null){
				try{
					String list = "";
					double yeucau = 0;
					String spId = "", spId_bk = "";
					while(rs.next()){
						spId = rs.getString("SANPHAM_FK");
						if(!spId.equals(spId_bk)){
							yeucau = 0;
							spId_bk = spId;
						}
						
						if(rs.getString("LOAI").equals("0") || rs.getString("LOAI").equals("1") ){
							yeucau += (-1)*rs.getDouble("SOLUONG");
						}else{
							if(yeucau < 0){
								if(list.length() == 0){
									list = rs.getString("ID");
								}else{
									list += "," + rs.getString("ID");
								}
								yeucau += rs.getDouble("SOLUONG");
							}
						}
						
					}
					rs.close();
					query = "DELETE ERP_DONMUAHANGDUKIEN WHERE PK_SEQ NOT IN (" + list + ")";
					System.out.println(query);
			//		this.db.update(query);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}*/
			
			}
	}
	
	private void getInitData(String spId, String dorong, String lsp_fk, int thang, int nam)
	{
	try{
	
		/*String query =	"SELECT	SUM(KHO_SP.AVAILABLE)  AS TONKHO, " + 
						"CASE WHEN NL.CHOPHEPTHAYTHE = 1 AND NLTHAYTHE <> NULL " +
						"THEN ISNULL(THAYTHE.TONKHOANTOAN, 0) " +
						"ELSE ISNULL(NL.TONKHOANTOAN, 0) " +
						"END AS TONKHOANTOAN, " +  
		
						"CASE WHEN NL.CHOPHEPTHAYTHE = 1 AND NLTHAYTHE <> NULL " +
						"THEN ISNULL(THAYTHE.LUONGDATHANGTOITHIEU, 0) " +
						"ELSE ISNULL(NL.LUONGDATHANGTOITHIEU, 0) " +
						"END AS MINLOTSIZE, " +
						
						"CASE WHEN NL.CHOPHEPTHAYTHE = 1 AND NLTHAYTHE <> NULL " +
						"THEN ISNULL(THAYTHE.THOIGIANCHOGIAO, 0) " +
						"ELSE ISNULL(NL.THOIGIANCHOGIAO, 0) " +
						"END AS LEADTIME  " +
						
						"FROM ERP_KHOTT_SANPHAM KHO_SP " +  
						"INNER JOIN ERP_KHOTT KHOTT ON KHOTT.PK_SEQ = KHO_SP.KHOTT_FK AND KHOTT.LOAI = 2 " +
						"INNER JOIN ERP_NGUYENLIEU NL ON NL.SANPHAM_FK = KHO_SP.SANPHAM_FK   " +
						"LEFT JOIN ( " +
						"	SELECT SANPHAM_FK, TONKHOANTOAN, LUONGDATHANGTOITHIEU, THOIGIANCHOGIAO " +
						"	FROM ERP_NGUYENLIEU " +
						"	WHERE SANPHAM_FK IN (SELECT NLTHAYTHE FROM ERP_NGUYENLIEU WHERE SANPHAM_FK = '" + spId + "') " +
						")THAYTHE ON THAYTHE.SANPHAM_FK = KHO_SP.SANPHAM_FK " +
						"WHERE  KHO_SP.SANPHAM_FK = '" + spId + "' " + 
						"GROUP BY NL.LUONGDATHANGTOITHIEU,THAYTHE.LUONGDATHANGTOITHIEU,THAYTHE.THOIGIANCHOGIAO,NL.THOIGIANCHOGIAO, " + 
						"NL.CHOPHEPTHAYTHE,THAYTHE.TONKHOANTOAN, NL.TONKHOANTOAN  " ;
	
		System.out.println("Tinh ton kho va lot size: " + query);
		ResultSet rs = this.db.get(query);
	
		rs.next();
		this.tonkho = Double.parseDouble(rs.getString("TONKHO"));
		this.lotsize = Double.parseDouble(rs.getString("MINLOTSIZE"));
		this.tonantoan = Double.parseDouble(rs.getString("TONKHOANTOAN"));
		this.leadtime = Integer.parseInt(rs.getString("LEADTIME"));
		
		rs.close();
		System.out.println("Ton an toan: " + this.tonantoan);*/
		
		
		//DU bao theo ma lon nen khong co ton an toan, lot size, leadtime
		//CHECK TON KHO THEO CONG THUC : PAPER = Bội số * Độ rộng + 10
										//FOIL = Bội số * Độ rộng + 2
		
		String condition = "";
		if(lsp_fk.equals("100013"))
		{
			condition = " AND RONG is not null and RONG in ( ";
			for(int i = 1; i <= 20; i++)  //CHẠY 20 lần bội số
			{
				if(i == 1)
					condition += Double.toString( i * Double.parseDouble(dorong) ) + ",";
				else
					condition += Double.toString( i * Double.parseDouble(dorong) + 10 ) + ",";
			}
			condition = condition.substring(0, condition.length() - 1);
			condition += " ) ";
		}
		else
		{
			if(lsp_fk.equals("100014"))
			{
				condition = " AND RONG is not null and RONG in ( ";
				for(int i = 1; i <= 20; i++)
				{
					if(i == 1)
						condition += Double.toString( i * Double.parseDouble(dorong) ) + ",";
					else
						condition += Double.toString( i * Double.parseDouble(dorong) + 2 ) + ",";
				}
				condition = condition.substring(0, condition.length() - 1);
				condition += " ) ";
			}
		}
		
		String query = " select sum(SOLUONG) as tonkho from ERP_KHOTT_SANPHAM " +
				 	   "where SANPHAM_FK in ( select pk_seq from ERP_SANPHAM where MA = '" + spId + "' " + condition + " )";
		System.out.println("Tinh ton kho va lot size: " + query);
		ResultSet rs = this.db.get(query);
		
		if(rs != null)
		{
			if(rs.next())
			{
				this.tonkho = Double.parseDouble(rs.getString("TONKHO"));
				this.lotsize = 0;
				this.tonantoan = 0;
				this.leadtime = 0;
			}
		}
		rs.close();
		
	}
	catch(Exception e){}
	}
	
	private double getTondau(int nam, int thang, String spId, String dorong, String lsp_fk )
	{
	try
	{
	double tondau = 0;
	int t = 0;
	int n = 0;
	String thanghientai = "";
	String thangtruoc = "";
	
	if(thang - 1 == 0) {
		t = 12;
		n = nam - 1;
	}else{
		t = thang -1;
		n = nam;
	}
	if(t < 10) thangtruoc = "0" + t;
	else thangtruoc = "" + t;
	
	
	if(thang < 10) thanghientai = "0" + (thang);
	else thanghientai = "" + (thang);
	
	//YÃªu cáº§u = Tá»•ng yÃªu cáº§u - Tá»•ng Ä‘Ã£ dÃ¹ng cá»§a LSX trong thÃ¡ng
	
	/*String query = 	"SELECT " +
					"(ISNULL(SUM(KHO_SP.SOLUONG), 0)+ ISNULL(PO.PO, 0) - ISNULL(YEUCAU.YEUCAU, 0)) AS TONDAU " +  
					"FROM ERP_KHOTT_SANPHAM KHO_SP  " +
					"INNER JOIN ERP_KHOTT KHO ON KHO.PK_SEQ = KHO_SP.KHOTT_FK " +
					"LEFT JOIN(  " +
					"	SELECT SUM(SOLUONG) AS PO, SANPHAM_FK " +   
					"	FROM ERP_MUAHANG_SP  " +
					"	WHERE NGAYNHAN >= '" + this.getDateTime() + "' AND NGAYNHAN <= '"+ n + "-" + thangtruoc  + "-31' " +
					"	AND SANPHAM_FK = '" + spId + "' " +  
					"	GROUP BY SANPHAM_FK  " +
					")PO ON PO.SANPHAM_FK = KHO_SP.SANPHAM_FK " +  
					"LEFT JOIN(  " +
					"	SELECT SUM(YCNL.YEUCAU) AS YEUCAU, YCNL.NGUYENLIEU_FK AS SANPHAM_FK " +
					"	FROM ERP_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU YCNL " +
					"	INNER JOIN ERP_LENHSANXUATDUKIEN LSXDK ON LSXDK.PK_SEQ = YCNL.LENHSANXUATDUKIEN_FK " +
					"	WHERE LSXDK.THANG >= " + Integer.parseInt(this.getDateTime().substring(5, 7)) + " AND LSXDK.THANG <= " + t + " AND LSXDK.NAM = "+ n + " " +
					"	AND YCNL.NGUYENLIEU_FK = '" + spId + "' " +   
					"	GROUP BY YCNL.NGUYENLIEU_FK  " +
					")YEUCAU ON YEUCAU.SANPHAM_FK = KHO_SP.SANPHAM_FK " +
					"LEFT JOIN( " +
					"	SELECT CASE WHEN BOM.CHOPHEPTHAYTHE = 1 THEN BOM.VATTUTT_FK  " +
					"		ELSE BOM.VATTU_FK END AS SANPHAM_FK,  " +
					"		sum (CASE WHEN BOM.CHOPHEPTHAYTHE = 1 THEN BOM.SOLUONGTT   " +
					"		ELSE BOM.SOLUONG END ) AS SoLuong   " +
					"	FROM ERP_LENHSANXUAT_GIAY LSX   " +
					"		INNER JOIN ERP_LENHSANXUAT_BOM_GIAY BOM ON BOM.LENHSANXUAT_FK = LSX.PK_SEQ   " +
					"		WHERE LSX.Trangthai <= 3 and LSX.CONGTY_FK =  '" + this.CTYID + "' " +
					"		AND LSX.NGAYDUKIENHT >= '" + this.getDateTime() + "' AND LSX.NGAYDUKIENHT <= '" + n + "-" + thangtruoc + "-31' " +
					"		AND ( ( CASE WHEN BOM.CHOPHEPTHAYTHE = 1 THEN BOM.VATTUTT_FK  " +
					"  		ELSE BOM.VATTU_FK END  ) = '" + spId + "' )  " +
					"	group by BOM.CHOPHEPTHAYTHE, VATTUTT_FK, BOM.VATTU_FK " +
					") SANXUAT on SANXUAT.SANPHAM_FK =  KHO_SP.SANPHAM_FK " +  
					"WHERE  KHO_SP.SANPHAM_FK = '" + spId + "'   " +  //AND KHO.LOAI = 2
					"GROUP BY PO.PO, YEUCAU.YEUCAU ";*/
	
	String condition = "";
	if(lsp_fk.equals("100013"))
	{
		condition = " AND RONG is not null and RONG in ( ";
		for(int i = 1; i <= 20; i++)  //CHẠY 20 lần bội số
		{
			if(i == 1)
				condition += Double.toString( i * Double.parseDouble(dorong) ) + ",";
			else
				condition += Double.toString( i * Double.parseDouble(dorong) + 10 ) + ",";
		}
		condition = condition.substring(0, condition.length() - 1);
		condition += " ) ";
	}
	else
	{
		if(lsp_fk.equals("100014"))
		{
			condition = " AND RONG is not null and RONG in ( ";
			for(int i = 1; i <= 20; i++)
			{
				if(i == 1)
					condition += Double.toString( i * Double.parseDouble(dorong) ) + ",";
				else
					condition += Double.toString( i * Double.parseDouble(dorong) + 2 ) + ",";
			}
			condition = condition.substring(0, condition.length() - 1);
			condition += " ) ";
		}
	}
	
	//PHai them nhung PO chua co nhan hang da chot
	String query = 	"SELECT " +
					"( ISNULL(KHO_SP.SOLUONG, 0 )+ ISNULL(PO.PO, 0) - ISNULL(YEUCAU.YEUCAU, 0) ) AS TONDAU " +  
					"FROM " +
					"( " +
					"	Select b.MA, SUM(AVAILABLE) as SoLuong " +
					"	from ERP_KHOTT_SANPHAM a inner join ERP_SANPHAM b on a.sanpham_fk = b.pk_seq " +
					"	where b.ma = '" + spId + "' " + condition +
					"	group by b.MA  " +
					") KHO_SP   " +
					"LEFT JOIN (  " +
					"	SELECT SUM(SOLUONG) AS PO, SP.Ma " +   
					"	FROM ERP_MUAHANG_SP MH inner join ERP_SANPHAM SP on MH.sanpham_fk = SP.pk_seq " +
					"	WHERE NGAYNHAN >= '" + this.getDateTime() + "' AND NGAYNHAN <= '"+ n + "-" + thangtruoc  + "-31' " +
					"	AND SP.Ma = '" + spId + "' " +  condition +
					"	GROUP BY SP.Ma  " +
					") PO ON PO.Ma = KHO_SP.Ma " +  
					"LEFT JOIN(  " +
					"	SELECT SUM(YCNL.YEUCAU) AS YEUCAU, YCNL.MANGUYENLIEU " +
					"	FROM ERP_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU YCNL " +
					"	INNER JOIN ERP_LENHSANXUATDUKIEN LSXDK ON LSXDK.PK_SEQ = YCNL.LENHSANXUATDUKIEN_FK " +
					"	WHERE LSXDK.THANG >= " + Integer.parseInt(this.getDateTime().substring(5, 7)) + " AND LSXDK.THANG <= " + t + " AND LSXDK.NAM = "+ n + " " +
					"	AND YCNL.MANGUYENLIEU = '" + spId + "' " +  condition.replaceAll("RONG", "YCNL.DORONG") + 
					"	GROUP BY YCNL.MANGUYENLIEU  " +
					") YEUCAU ON YEUCAU.MANGUYENLIEU = KHO_SP.MA ";
					/*"LEFT JOIN( " +
					"	SELECT CASE WHEN BOM.CHOPHEPTHAYTHE = 1 THEN BOM.VATTUTT_FK  " +
					"		ELSE BOM.VATTU_FK END AS SANPHAM_FK,  " +
					"		sum (CASE WHEN BOM.CHOPHEPTHAYTHE = 1 THEN BOM.SOLUONGTT   " +
					"		ELSE BOM.SOLUONG END ) AS SoLuong   " +
					"	FROM ERP_LENHSANXUAT_GIAY LSX   " +
					"		INNER JOIN ERP_LENHSANXUAT_BOM_GIAY BOM ON BOM.LENHSANXUAT_FK = LSX.PK_SEQ   " +
					"		WHERE LSX.Trangthai <= 3 and LSX.CONGTY_FK =  '" + this.CTYID + "' " +
					"		AND LSX.NGAYDUKIENHT >= '" + this.getDateTime() + "' AND LSX.NGAYDUKIENHT <= '" + n + "-" + thangtruoc + "-31' " +
					"		AND ( ( CASE WHEN BOM.CHOPHEPTHAYTHE = 1 THEN BOM.VATTUTT_FK  " +
					"  		ELSE BOM.VATTU_FK END  ) = '" + spId + "' )  " +
					"	group by BOM.CHOPHEPTHAYTHE, VATTUTT_FK, BOM.VATTU_FK " +
					") SANXUAT on SANXUAT.SANPHAM_FK =  KHO_SP.SANPHAM_FK " + */ 
					//"WHERE  SP.MA = '" + spId + "'   " ;
					//"GROUP BY PO.PO, YEUCAU.YEUCAU ";
	
	System.out.println("**********************Lay Ton dau :" + query);
	ResultSet rs = this.db.get(query) ;
	
	if(rs.next())
	{
		tondau = Double.parseDouble(rs.getString("TONDAU"));			
		rs.close();
	}
	
	return tondau;
	
	}
	catch(Exception e)
	{
	System.out.println("___Exception lay ton dau: " + e.getMessage());
	return 0;
	}
		
	}
	
	
	private double getDemand(int thang, int nam, String spId, String dorong, String lsp_fk)
	{
	ResultSet rs;
	String th = "" + thang;
	if(thang < 10) 
		th  = "0" + thang;
	String query;
	/*query = 	"SELECT ISNULL(YEUCAU.TONKHOANTOAN, 0) AS TONKHOANTOAN, " +
				"ISNULL(YEUCAU.YEUCAU, 0) - ISNULL(PO.PO,0) AS YEUCAU " +
				"FROM ERP_KHOTT_SANPHAM KHO_SP  " +
				"INNER JOIN ERP_KHOTT KHO ON KHO.PK_SEQ = KHO_SP.KHOTT_FK " +
				"LEFT JOIN(  " +
				"	SELECT SUM(SOLUONG) AS PO, SANPHAM_FK " +   
				"	FROM ERP_MUAHANG_SP  " +
				"	WHERE SUBSTRING(NGAYNHAN, 1, 4) = " + nam + " AND SUBSTRING(NGAYNHAN, 5, 2) = " + th + " " +
				"	AND SANPHAM_FK = '"+ spId + "' " +
				"	GROUP BY SANPHAM_FK  " +
				")PO ON PO.SANPHAM_FK = KHO_SP.SANPHAM_FK " +  
				"LEFT JOIN(  " +
				"	SELECT SUM(NL.YEUCAU) AS YEUCAU, SUM(NL.TONKHOANTOAN) AS TONKHOANTOAN, NL.NGUYENLIEU_FK AS SANPHAM_FK " +
				"	FROM ERP_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU NL " +
				"	INNER JOIN ERP_LENHSANXUATDUKIEN LSXDK ON LSXDK.PK_SEQ = NL.LENHSANXUATDUKIEN_FK " +
				"	WHERE LSXDK.THANG = " + thang + " AND LSXDK.NAM = "+ nam + " " +
				"	AND NL.NGUYENLIEU_FK = '"+ spId + "' " + 
				"	GROUP BY NGUYENLIEU_FK  " +
				")YEUCAU ON YEUCAU.SANPHAM_FK = KHO_SP.SANPHAM_FK " +  
				"WHERE  KHO_SP.SANPHAM_FK = '"+ spId + "'  " ;*/
	
	String condition = "";
	if(lsp_fk.equals("100013"))
	{
		condition = " AND RONG is not null and RONG in ( ";
		for(int i = 1; i <= 20; i++)  //CHẠY 20 lần bội số
		{
			if(i == 1)
				condition += Double.toString( i * Double.parseDouble(dorong) ) + ",";
			else
				condition += Double.toString( i * Double.parseDouble(dorong) + 10 ) + ",";
		}
		condition = condition.substring(0, condition.length() - 1);
		condition += " ) ";
	}
	else
	{
		if(lsp_fk.equals("100014"))
		{
			condition = " AND RONG is not null and RONG in ( ";
			for(int i = 1; i <= 20; i++)
			{
				if(i == 1)
					condition += Double.toString( i * Double.parseDouble(dorong) ) + ",";
				else
					condition += Double.toString( i * Double.parseDouble(dorong) + 2 ) + ",";
			}
			condition = condition.substring(0, condition.length() - 1);
			condition += " ) ";
		}
	}
	
	query = 	"SELECT ISNULL(YEUCAU.TONKHOANTOAN, 0) AS TONKHOANTOAN, " +
					"ISNULL(YEUCAU.YEUCAU, 0) - ISNULL(PO.PO,0) AS YEUCAU " +
				"FROM ( select '" + spId + "' as MA ) SP  " +
				"LEFT JOIN(  " +
				"	SELECT SUM(SOLUONG) AS PO, SP.MA " +   
				"	FROM ERP_MUAHANG_SP MH inner join ERP_SANPHAM SP on MH.sanpham_fk = SP.pk_seq  " +
				"	WHERE SUBSTRING(NGAYNHAN, 1, 4) = " + nam + " AND SUBSTRING(NGAYNHAN, 5, 2) = " + th + " " +
				"	AND SP.MA = '"+ spId + "' " + condition +
				"	GROUP BY SP.MA  " +
				")PO ON PO.MA = SP.MA " +  
				"LEFT JOIN(  " +
				"	SELECT SUM(NL.YEUCAU) AS YEUCAU, SUM(NL.TONKHOANTOAN) AS TONKHOANTOAN, NL.MANGUYENLIEU  " +
				"	FROM ERP_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU NL " +
				"	INNER JOIN ERP_LENHSANXUATDUKIEN LSXDK ON LSXDK.PK_SEQ = NL.LENHSANXUATDUKIEN_FK " +
				"	WHERE LSXDK.THANG = " + thang + " AND LSXDK.NAM = "+ nam + " " +
				"	AND NL.MANGUYENLIEU = '"+ spId + "' " + condition.replaceAll("RONG", "NL.DoRong") +
				"	GROUP BY MANGUYENLIEU  " +
				")YEUCAU ON YEUCAU.MANGUYENLIEU = SP.MA " +  
				"WHERE  SP.MA = '"+ spId + "'  " ;
	
	System.out.println("____________Lay Yeu cau: " + query);
	rs =  this.db.get(query);
	try
	{
		rs.next();
		double tmp = Double.parseDouble(rs.getString("YEUCAU"));			
		this.tonantoan = Double.parseDouble(rs.getString("TONKHOANTOAN"));
		rs.close();
		return tmp;
	}
	catch(Exception e)
	{ 
		System.out.println("___Exception lay YC: " + e.getMessage());
		return 0;
	}
	
	
	}
	
	public void close() 
	
	{
	
	try 
	{
		if(this.rsDvkd != null) this.rsDvkd.close();
		
		if(rsChungloai!=null){
			rsChungloai.close();
		}
		if(rsNhanhang!=null){
			rsNhanhang.close();
		}
		if(rsSanpham!=null){
			rsSanpham.close();
		}
		if(rsSovoi!=null){
			rsSovoi.close();
		}
		
		if(khRS != null){
			khRS.close();
		}
		
		if(this.rsKho != null)
			this.rsKho.close();
		
		if(this.rsPhuongphap != null)
			this.rsPhuongphap.close();
		
		this.db.shutDown();
	}
	catch (SQLException e) 
	{
		System.out.println(e.toString());
	}	
	
	}
	
	
	

}
