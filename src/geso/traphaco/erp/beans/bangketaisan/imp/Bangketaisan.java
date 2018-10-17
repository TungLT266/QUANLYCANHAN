package geso.traphaco.erp.beans.bangketaisan.imp;
import geso.traphaco.erp.beans.bangketaisan.*;
import geso.traphaco.erp.db.sql.dbutils;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.ResultSet;

public class Bangketaisan implements IBangketaisan {
	String userId;
	String ctyId;	
	
	String ctyTen;
	String tungay;
	String denngay;
	String diachi;
	String masothue;	
	String sodudau;
	String loaitsId;
	String ttcpId;
	
	ResultSet rs;
	
	ResultSet dauky;
	
	ResultSet Rsloaits;
	
	ResultSet Rstrungtamcp;
	
	String msg;
	dbutils db;
	
	
	public Bangketaisan() {
		this.userId = "";
		this.ctyId = "100005";
		this.ctyTen = "";
		this.sodudau = "0";
		this.denngay = "";		
		this.tungay = "";
		this.loaitsId="";
		this.ttcpId ="";
		this.msg = "";
		
		this.db = new dbutils();
	}

	public void setuserId(String userId) {

		this.userId = userId;
	}

	public String getuserId() {

		return this.userId;
	}

	public void setCtyId(String ctyId) {

		this.ctyId = ctyId;
	}

	public String getCtyId() {

		return this.ctyId;
	}

	public String getCtyTen() {
		return this.ctyTen;
	}

	public String getDiachi() {

		return this.diachi;
	}

	public String getMasothue() {

		return this.masothue;
	}
	
	public void setTungay(String tungay) {

		this.tungay = tungay;
	}

	public String getTungay() {
		return this.tungay;	
	}

	public String getDenngay() {
		return this.denngay;			
	}

	public void setDenngay(String denngay) {

		this.denngay = denngay;
	}

	public void setMsg(String msg) {

		this.msg = msg;
	}

	public String getMsg() {

		return this.msg;
	}

	public String getSodudau() {

		return this.sodudau;
	}


	public ResultSet getData(){
		return this.rs;
	}
	
	public ResultSet getDauky(){
		return this.dauky;
	}

	
	public String init(){
		
		String query;	
			String timkiem="";
			if(this.tungay.trim().length()>0){
				timkiem += " and nh_sp.NGAYNHANDUKIEN >= '"+this.tungay+"'\n";
			}
			
			if(this.denngay.trim().length()>0){
				timkiem += " and nh_sp.NGAYNHANDUKIEN <='"+this.denngay+"'\n";
			}
			
			if(this.loaitsId.trim().length()>0){
				timkiem += " and TSCD.LOAITAISAN_FK='"+this.loaitsId+"'\n";
			}
			
			if(this.ttcpId.trim().length()>0){
				timkiem += " and TTCP.PK_SEQ='"+this.ttcpId+"'\n";
			}
			
			query=			
			" SELECT tscd.pk_seq,tscd.ma,isnull(TSCD.diengiai,'') ten,nh.LOAIHANGHOA_FK, nh_sp.NGAYNHANDUKIEN NGAYNHAN\n" +
			", ttcp.DIENGIAI TENTTCP, nh_sp.DONVI, SUM(ISNULL(nh_sp.SOLUONGNHAN,0)) SOLUONG \n"+ 
			" FROM ERP_NHANHANG_SANPHAM nh_sp inner join ERP_NHANHANG nh on nh_sp.NHANHANG_FK = nh.PK_SEQ \n"+
			"	  LEFT JOIN ERP_TAISANCODINH tscd on nh_sp.TAISAN_FK = tscd.pk_seq \n"+
			"	  LEFT JOIN ERP_TRUNGTAMCHIPHI ttcp on tscd.TTCP_FK = ttcp.PK_SEQ \n"+
		    " WHERE 1 = 1 and nh_sp.SOLUONGNHAN >0 and nh.TRANGTHAI IN (1,2) \n"+ timkiem+ "\n" +
			"	  and nh.LOAIHANGHOA_FK = 1 \n"+
			" AND TSCD.PK_SEQ NOT IN (\n" +
			"		SELECT DISTINCT TLTS_TS.TAISAN_FK \n" +
			"		FROM ERP_THANHLYTAISAN TLTS \n" +
			"		INNER JOIN ERP_THANHLYTAISAN_TAISAN TLTS_TS ON TLTS_TS.THANHLYTAISAN_FK = TLTS.PK_SEQ \n" +
			"		WHERE TLTS.TRANGTHAI > 0 \n" +
			") \n"+

			" GROUP BY TSCD.pk_seq, TSCD.ma,isnull(TSCD.diengiai,''), nh_sp.NGAYNHANDUKIEN,nh.LOAIHANGHOA_FK, NH.NGAYNHAN, TTCP.DIENGIAI, nh_sp.DONVI \n";
			
			System.out.println("INIT_BC: \n" + query + "\n------------------------------------------------------------------------");
			return query;
		
	}

	public String getDate() 
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public void DBClose(){
		if(db != null) db.shutDown();
	}

	
	public ResultSet getRsLoaiTS() {
		
		return this.Rsloaits;
	}

	
	public void setRsTkLoaiTS(ResultSet Rsloaits) {
		
		this.Rsloaits = Rsloaits;
	}
 
	
	public ResultSet getRsTrungTamCP() {
		
		return this.Rstrungtamcp;
	}

	
	public void setRsTrungTamCP(ResultSet Rstrungtamcp) {
		
		this.Rstrungtamcp = Rstrungtamcp;
	}


	public void setRsLoaiTS(ResultSet Rsloaits) {

		this.Rsloaits = Rsloaits;
	}


	public String getLoaitsId() {

		return this.loaitsId;
	}


	public void setLoaitsId(String loaitsId) {

		this.loaitsId = loaitsId;
	}


	public String getTTCpId() {

		return this.ttcpId;
	}


	public void setTTCpId(String ttcpId) {

		this.ttcpId = ttcpId;
	}


	public void createRs() {
		
		String query="";
		
		query="select pk_seq, diengiai ten from erp_loaitaisan";
		
		this.Rsloaits = db.get(query);
		
		query="select pk_seq,diengiai ten from ERP_TRUNGTAMCHIPHI";
		
		this.Rstrungtamcp = db.get(query);
	}
}
