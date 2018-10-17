package geso.traphaco.erp.beans.lapngansach.imp;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.lapngansach.ILapngansach;
import geso.traphaco.erp.db.sql.dbutils;

import java.io.IOException;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public class Lapngansach implements ILapngansach 
{
	String userId;
	String Id;
	String trangthai;
	
	
	String ngay;
	ResultSet namlist;
	
	String ctyId;
	String cty;
	
	String bpId;
	ResultSet bplist;

	String lnsId;

	String ncpId;
	ResultSet ncplist;
	
	String ndtId;
	ResultSet ndtlist;

	String ntsId;
	ResultSet ntslist;

	String[] cpIds;
	ResultSet cpList;
	
	String[] dtIds;
	ResultSet dtList;

	String nam;
	String namtruoc;
	
	String hieuluc;
	
	String msg;
	String view;
	
	float ptgvdutoan;
	String doanhthu;
	String giavon;

	String dttsId;
	String[] tsIds;
	ResultSet tsList;
	
	String[] cdIds;
	ResultSet cdList;
	String chon;
	
	dbutils db;
	Utility util;
	
	String dvkdId;
	ResultSet dvkdRs;
	
	String diengiai;
	
	String nsId;
	ResultSet nsList;
	NumberFormat formatter = new DecimalFormat("#,###,###");
	String[] amount = new String[12];
	String loai;
	public Lapngansach(){
		this.userId = "";
		this.ngay = this.getDateTime();
		this.ctyId = "";
		this.cty = "";
		this.nsId = "";
		this.ntsId = "";
		this.dvkdId = "";
		this.lnsId = "";
		this.bpId = "";
		this.ncpId = "";
		this.ndtId = "";
		this.nam = "" + (Integer.parseInt(this.getDateTime().substring(0, 4)) + 1);
		this.hieuluc = "";
		this.doanhthu = "";
		this.giavon = "";
		this.view = "1";
		this.msg = "";
		this.chon = "";
		this.ptgvdutoan = 0;
		this.diengiai = "";
		this.dttsId = "";
		this.loai = "1";
		this.trangthai = "";
		this.db = new dbutils();
		this.util=new Utility();
	}
	
	public String getId(){
		return this.Id;
	}
	
	public void setId(String Id){
		this.Id = Id;
	}

	public String getTrangthai(){
		return this.trangthai;
	}
	
	public void setTrangthai(String trangthai){
		this.trangthai = trangthai;
	}

	public String getNsId() 
	{
		
		return this.nsId;
	}

	
	public void setNsId(String nsId) 
	{
		
		this.nsId = nsId;
	}

	public String getDvkdId() 
	{
		
		return this.dvkdId;
	}

	
	public void setDvkdId(String dvkdId) 
	{
		
		this.dvkdId= dvkdId;
	}
	
	public ResultSet getDvkd() 
	{
		
		return this.dvkdRs;
	}

	
	public void setDvkd(ResultSet dvkdRs) 
	{
		
		this.dvkdRs = dvkdRs;
	}
	
	public String getUserId(){
		return this.userId;
	}
	
	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getNgay(){
		return this.ngay;
	}
	
	public void setNgay(String ngay){
		this.ngay = ngay;
	}
	
	public String getCtyId(){
		return this.ctyId;
	}
	
	public void setCtyId(String ctyId){
		this.ctyId = ctyId;
	}

	public String getCty(){
		return this.cty;
	}
	
	public void setDoanhthu(String doanhthu){
		this.doanhthu = doanhthu;
	}

	public String getDoanhthu(){
		return this.doanhthu;
	}
	
	public void setGiavon(String giavon){
		this.giavon = giavon;
	}

	public String getGiavon(){
		return this.giavon;
	}

	public void setCty(String cty){
		this.cty = cty;
	}

	public String getView(){
		return this.view;
	}
	
	public void setView(String view){
		this.view = view;
	}

	public String getBpId(){
		return this.bpId;
	}
	
	public void setBpId(String bpId){
		this.bpId = bpId;
	}

	public String getLoai(){
		return this.loai;
	}
	
	public void setLoai(String loai){
		this.loai = loai;
	}
	
	public String getLnsId(){
		return this.lnsId;
	}
	
	public void setLnsId(String lnsId){
		this.lnsId = lnsId;
	}

	public String getNam(){
		return this.nam;
	}
	
	public void setNam(String nam){
		this.nam = nam;
	}

	public String getNamtruoc(){
		return this.namtruoc;
	}
	
	public void setNamtruoc(String namtruoc){
		this.namtruoc = namtruoc;
	}

	public void setCpIds(String[] cpIds){
		this.cpIds = cpIds;
	}

	public String[] getCpIds(){
		return this.cpIds;
	}

	public void setDtIds(String[] dtIds){
		this.dtIds = dtIds;
	}

	public String[] getDtIds(){
		return this.dtIds;
	}

	public void setDubaoThang(String[] amount){
		this.amount = amount;
	}

	public String[] getDubaoThang(){
		return this.amount;
	}

	public void setTsIds(String[] tsIds){
		this.tsIds = tsIds;
	}

	public String[] getTsIds(){
		return this.tsIds;
	}

	public void setCdIds(String[] cdIds){
		this.cdIds = cdIds;
	}

	public String[] getCdIds(){
		return this.cdIds;
	}

	public String getHieuluc(){
		return this.hieuluc;
	}

	public void setHieuluc(String hieuluc){
		this.hieuluc = hieuluc;
	}

	public String getMsg(){
		return this.msg;
	}

	public void setMsg(String msg){
		this.msg = msg;
	}
		
	public float getPTGVdutoan(){
		return this.ptgvdutoan;
	}

	public void setPTGVdutoan(float ptgvdutoan){
		this.ptgvdutoan = ptgvdutoan;
	}

	public ResultSet getBplist(){
		return this.bplist;
	}
	
	public void setBplist(ResultSet bplist){
		this.bplist = bplist;
	}

	public String getNtsId(){
		return this.ntsId;
	}
	
	public void setNtsId(String ntsId){
		this.ntsId = ntsId;
	}

	public ResultSet getNtslist(){
		if(this.loai.equals("1")){
			String query = 	"SELECT PK_SEQ AS NTSID, DIENGIAI " +
							"FROM ERP_NHOMTAISAN WHERE TRANGTHAI = '1'";
			return this.db.get(query);
		}else{
			String query = 	"SELECT PK_SEQ AS NTSID, DIENGIAI " +
							"FROM ERP_NHOMCCDC WHERE TRANGTHAI = '1'";
			return this.db.get(query);
			
		}
		
	}
	
	public void setNtslist(ResultSet ntslist){
		this.ntslist = ntslist;
	}

	public String getNcpId(){
		return this.ncpId;
	}
	
	public void setNcpId(String ncpId){
		this.ncpId = ncpId;
	}
	
	public String getNdtId(){
		return this.ndtId;
	}
	
	public void setNdtId(String ndtId){
		this.ndtId = ndtId;
	}

	public String getChon(){
		return this.chon;
	}
	
	public void setChon(String chon){
		this.chon = chon;
	}

	public ResultSet getNamlist(){
		return this.namlist;
	}
	
	public void setNamlist(ResultSet namlist){
		this.namlist = namlist;
	}


	public ResultSet getNcplist(){
		return this.ncplist;
	}
	
	public void setNcplist(ResultSet ncplist){
		this.ncplist = ncplist;
	}

	public ResultSet getCplist(){
		return this.cpList;
	}
	
	public void setCplist(ResultSet cplist){
		this.cpList = cplist;
	}
	
	public ResultSet getNdtlist(){
		return this.ndtlist;
	}
	
	public void setNdtlist(ResultSet ndtlist){
		this.ndtlist = ndtlist;
	}

	public ResultSet getDtlist(){
		return this.dtList;
	}
	
	public void setDtlist(ResultSet dtlist){
		this.dtList = dtlist;
	}

	public ResultSet getTslist(){
		return this.tsList;
	}
	
	public void setTslist(ResultSet tslist){
		this.tsList = tslist;
	}

	public ResultSet getCdlist(){
		return this.cdList;
	}
	
	public void setCdlist(ResultSet cdlist){
		this.cdList = cdlist;
	}


	public String getDiengiai(){
		return this.diengiai;
	}
	
	public void setDiengiai(String diengiai){
		this.diengiai = diengiai;
	}

	public String getDttsId(){
		return this.dttsId;
	}
	
	public void setDttsId(String dttsId){
		this.dttsId = dttsId;
	}

	public ResultSet getNsList() 
	{
		return this.nsList;
	}
	
	public void setNsList(ResultSet nslist) {
		
		this.nsList = nslist;
	}
	
	
	public ResultSet getDutoantaisan(){
		String query = "SELECT PK_SEQ AS ID, DIENGIAI FROM ERP_LAPNGANSACH WHERE LOAI = 2 ";
		
		if(this.dvkdId.length() > 0){
			query = query + " AND DVKD_FK = " + this.dvkdId ;
		}
		
		return this.db.get(query);
	}
	
		
	private void createNcpList(){
		if(this.bpId.length() > 0){
			String query = 	"SELECT DISTINCT THTC.PK_SEQ AS THTCID, THTC.DIENGIAI " +
							"FROM ERP_TONGHOPTAICHINH THTC " +
							"INNER JOIN ERP_TONGHOPTAICHINH_CHIPHI CP ON THTC.PK_SEQ = CP.THTC_FK " +
							"INNER JOIN ERP_NHOMCHIPHI NCP ON NCP.PK_SEQ = CP.CHIPHI_FK " +
							"WHERE NCP.DONVITHUCHIEN_FK = " + this.bpId + " AND THTC.TRANGTHAI = '1' ";
			
			System.out.println(query);
			this.ncplist = this.db.get(query);
		}
	}
	
	private void createNdtList(){
		if(this.bpId.length() > 0){
			String query = 	"SELECT DISTINCT THTC.PK_SEQ AS THTCID, THTC.DIENGIAI " +
							"FROM ERP_TONGHOPTAICHINH THTC " +
							"INNER JOIN ERP_TONGHOPTAICHINH_DOANHTHU THTC_DT ON THTC.PK_SEQ = THTC_DT.THTC_FK " +
							"INNER JOIN ERP_DOANHTHU DT ON DT.PK_SEQ = THTC_DT.DOANHTHU_FK " +
							"WHERE DT.DONVITHUCHIEN_FK = " + this.bpId + " AND THTC.TRANGTHAI = '1' ";
			
			System.out.println(query);
			this.ndtlist = this.db.get(query);
		}
	}

	private void createCpList(){
		String query = "";
		if(this.lnsId.length() > 0){
			query = "SELECT NAM FROM ERP_LAPNGANSACH WHERE PK_SEQ = " + this.lnsId + " ";
			System.out.println(query);
			
			ResultSet rs = this.db.get(query);
			try{
				rs.next();
				this.nam = rs.getString("NAM");
			}catch(java.sql.SQLException e){}
			
		}
		if(this.bpId.length() > 0 ){
			if(this.view.equals("2")){
			
			query = "SELECT NCP.PK_SEQ AS CPID, NCP.DIENGIAI AS TEN, SUM(ISNULL(CP.DUTOAN, 0)) AS NAMMOI, ISNULL(NAMCU.NAMCU, 0) AS NAMCU,  \n " +
					"0 AS PHANTRAM, SUM(ISNULL(DUTOAN, 0)) AS GIATRI  \n " +
					"FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
					"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK  \n " +
					"INNER JOIN ERP_NHOMCHIPHI NCP ON NCP.PK_SEQ = CP.CHIPHI_FK  \n " +
					"LEFT JOIN(  \n " +
					"	SELECT NCP.PK_SEQ AS NCPID, NCP.DIENGIAI, CP.DONVITHUCHIEN_FK AS BPID, SUM(CP.DUTOAN)AS NAMCU  \n " +
					"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
					"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK  \n " +
					"	INNER JOIN ERP_NHOMCHIPHI NCP ON NCP.PK_SEQ = CP.CHIPHI_FK  \n " +
					"	WHERE CP.DONVITHUCHIEN_FK = '" + this.bpId + "'  AND LNS.NAM = " + (Integer.parseInt(this.nam) - 1) + "  \n " +
					"	GROUP BY NCP.PK_SEQ, NCP.TEN, CP.DONVITHUCHIEN_FK  \n " +
					")NAMCU ON NAMCU.NCPID = NCP.PK_SEQ AND NAMCU.BPID = CP.DONVITHUCHIEN_FK  \n " +
					"WHERE CP.DONVITHUCHIEN_FK = '" + this.bpId + "'  AND LNS.NAM = " + this.nam + " AND CP.LAPNGANSACH_FK = " + this.lnsId + " AND NCP.TTCHIPHI_FK in ( select pk_seq from ERP_TRUNGTAMCHIPHI where CONGANSACH = '1' )  \n " +
					"GROUP BY NCP.PK_SEQ, NCP.TEN, NAMCU.NAMCU  \n " +
					"ORDER BY NCP.TEN ";
			
			System.out.println("1.Lay LIST DETAIL: " + query);
			this.cpList = this.db.get(query);
			
			}else{
				query = "SELECT	NCP.PK_SEQ AS CPID, NCP.DIENGIAI AS TEN, ISNULL(CP.DUTOAN,0) AS NAMMOI, ISNULL(NAMCU.NAMCU, 0) AS NAMCU,  \n " +
						"ISNULL(CP.PHANTRAM, 0) AS PHANTRAM,  \n " +
						"ISNULL(CP.DUTOAN, 0) AS GIATRI,  \n " +
						"ISNULL(CP.T1, 0) AS T1, ISNULL(CP.T2, 0) AS T2, ISNULL(CP.T3, 0) AS T3, ISNULL(CP.T4, 0) AS T4, ISNULL(CP.T5, 0) AS T5, ISNULL(CP.T6, 0) AS T6,  \n " +
						"ISNULL(CP.T7, 0) AS T7, ISNULL(CP.T8, 0) AS T8, ISNULL(CP.T9, 0) AS T9, ISNULL(CP.T10,0) AS T10, ISNULL(CP.T11,0) AS T11, ISNULL(CP.T12,0) AS T12  \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"INNER JOIN ERP_NHOMCHIPHI NCP ON NCP.PK_SEQ = CP.CHIPHI_FK  \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK  \n " +
						"LEFT JOIN  \n " +
						"(  \n " +
						"	SELECT ISNULL(CP.DUTOAN,0) AS NAMCU, CP.CHIPHI_FK, CP.LAPNGANSACH_FK, CP.DONVITHUCHIEN_FK  \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK  \n " +
						"	WHERE NAM = " + (Integer.parseInt(this.nam) - 1) + "  AND CP.DONVITHUCHIEN_FK = '" + this.bpId + "'  \n " +
						")NAMCU ON  NAMCU.DONVITHUCHIEN_FK = CP.DONVITHUCHIEN_FK AND NAMCU.CHIPHI_FK = CP.CHIPHI_FK  \n " + 
						"WHERE LNS.NAM = " + this.nam + " AND CP.DONVITHUCHIEN_FK = '" + this.bpId + "' AND CP.LAPNGANSACH_FK = " + this.lnsId + " AND NCP.TTCHIPHI_FK in ( select pk_seq from ERP_TRUNGTAMCHIPHI where CONGANSACH = '1' )   \n " +
						"ORDER BY NCP.TEN ";
				System.out.println("2.Lay LIST DETAIL: " + query);
				this.cpList = this.db.get(query);

		
			}
		}
	}

	private void PL_Sales_Budget_Detail(){

		String sql = 	"SELECT DB.THANG, SUM(DB.THANHTIEN) AS THANHTIEN \n " +
						"FROM \n " +
						"( \n " +
						"	SELECT DISTINCT SP.MA,  DUBAOSP.THANG, \n " +
						"	SUM(ISNULL(DUBAOSP.DUKIENBAN*BG_SP.GIABAN, 0)) AS THANHTIEN, SUM(ISNULL(DUBAOSP.DUKIENBAN,0)) AS DUBAO \n " +
						"	FROM ERP_LAPNGANSACH_DUBAO DUBAO    \n " +
						"	INNER JOIN ERP_LAPNGANSACH_DUBAOSANPHAM DUBAOSP on DUBAO.PK_SEQ = DUBAOSP.DUBAO_FK \n " +  
						"	INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = DUBAOSP.SANPHAM_FK    \n " +
						"	INNER JOIN ERP_LNSBGBAN_SANPHAM BG_SP ON BG_SP.SANPHAM_FK = DUBAOSP.SANPHAM_FK \n " + 
						"						AND BG_SP.THANG = DUBAOSP.THANG  \n " +
						"	INNER JOIN ERP_LNSBANGGIABAN BG ON BG.PK_SEQ = BG_SP.BGBAN_FK AND BG.NAM = DUBAOSP.NAM AND BG.KENH_FK = DUBAO.KBH_FK \n " +
						"	WHERE DUBAO.LAPNGANSACH_FK =  " + this.lnsId + "  \n " +
						"	AND DUBAO.CONGTY_FK =  " + this.ctyId + "  \n " +  
						"	GROUP BY DUBAOSP.THANG, SP.MA \n " +  
						")DB \n " +  
						"GROUP BY DB.THANG \n " ;
		System.out.println(sql);
		prepareData_Sales(sql);

	}	

	private void prepareData_Sales(String query){
		for(int i = 0; i < 12; i++){
			
			this.amount[i] = "0";
				
		}
		ResultSet rs = this.db.get(query);
		
		if(rs != null){
			try{
				while(rs.next()){
					this.amount[Integer.parseInt(rs.getString("THANG")) - 1] = "" + Math.round(Double.parseDouble(rs.getString("THANHTIEN")));
				}
				rs.close();
			}catch(java.sql.SQLException e){}
		}
		
		
}

	private void createDtList(){
		String query = "";
	
		if(this.bpId.length() > 0 ){

			
			query = 	"SELECT	DT.PK_SEQ AS DTID, DT.TEN,  \n " +
						"ISNULL(LNS_DT.DUTOAN, 0) AS NAMMOI, ISNULL(NAMCU.NAMCU, 0) AS NAMCU,  \n " + 
						"CASE WHEN ISNULL(NAMCU.NAMCU,0) > 0 THEN SUBSTRING(CONVERT(VARCHAR,ROUND(100*LNS_DT.DUTOAN/NAMCU.NAMCU,2)),0,5) ELSE '0' END AS PHANTRAM,  \n " + 
						"ISNULL(LNS_DT.PHANBO, 1) AS PHANBO, ISNULL(LNS_DT.T1, 0) AS T1, ISNULL(LNS_DT.T2, 0) AS T2,  \n " +
						"ISNULL(LNS_DT.T3, 0) AS T3, ISNULL(LNS_DT.T4, 0) AS T4, ISNULL(LNS_DT.T5, 0) AS T5,  \n " +
						"ISNULL(LNS_DT.T6, 0) AS T6, ISNULL(LNS_DT.T7, 0) AS T7, ISNULL(LNS_DT.T8, 0) AS T8,  \n " +
						"ISNULL(LNS_DT.T9, 0) AS T9, ISNULL(LNS_DT.T10,0) AS T10, ISNULL(LNS_DT.T11,0) AS T11,  \n " +
						"ISNULL(LNS_DT.T12,0) AS T12  \n " +
					
						"FROM ERP_LAPNGANSACH_DOANHTHU LNS_DT  \n " + 
						"INNER JOIN ERP_DOANHTHU DT ON DT.PK_SEQ = LNS_DT.DOANHTHU_FK  \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = LNS_DT.LAPNGANSACH_FK  \n " + 
						"LEFT JOIN (  \n " +	
						"	SELECT ISNULL(LNS_DT.DUTOAN,0) AS NAMCU, LNS_DT.DOANHTHU_FK, LNS_DT.LAPNGANSACH_FK, LNS_DT.DONVITHUCHIEN_FK  \n " + 	
						"	FROM ERP_LAPNGANSACH_DOANHTHU LNS_DT  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = LNS_DT.LAPNGANSACH_FK  \n " + 	
						"	WHERE NAM = " + (Integer.parseInt(this.nam) - 1) + "  AND LNS_DT.DONVITHUCHIEN_FK = '" + this.bpId + "'   \n " +
						")NAMCU ON  NAMCU.DONVITHUCHIEN_FK = LNS_DT.DONVITHUCHIEN_FK  \n " +
						"AND NAMCU.DOANHTHU_FK = LNS_DT.DOANHTHU_FK WHERE LNS.NAM = " + this.nam + "  \n " + 
						"AND LNS_DT.DONVITHUCHIEN_FK = '" + this.bpId + "'  AND LNS_DT.LAPNGANSACH_FK = " + this.lnsId + "  ORDER BY DT.TEN " ;

		System.out.println(query);
		this.dtList = this.db.get(query);
			
	}
	
	if(this.bpId.length() > 0 ){
		if(this.view.equals("2")){
		query = "SELECT DT.PK_SEQ AS DTID, DT.TEN, ISNULL(SUM(LNS_DT.DUTOAN),0) AS NAMMOI, ISNULL(NAMCU.NAMCU, 0) AS NAMCU, " +
				"CASE WHEN ISNULL(NAMCU.NAMCU, 0) > 0 THEN 100*SUM(LNS_DT.DUTOAN)/NAMCU.NAMCU ELSE 0 END AS PHANTRAM " +

				"FROM ERP_LAPNGANSACH_DOANHTHU LNS_DT " + 
				"INNER JOIN ERP_DOANHTHU DT ON DT.PK_SEQ = LNS_DT.DOANHTHU_FK " +
				"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = LNS_DT.LAPNGANSACH_FK " + 
				
				"LEFT JOIN( " +
				"	SELECT DT.PK_SEQ AS DTID, DT.TEN, LNS_DT.DONVITHUCHIEN_FK AS BPID, SUM(LNS_DT.DUTOAN)AS NAMCU " +
				"	FROM ERP_LAPNGANSACH_DOANHTHU LNS_DT " +
				"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = LNS_DT.LAPNGANSACH_FK " +
				"	INNER JOIN ERP_DOANHTHU DT ON DT.PK_SEQ = LNS_DT.DOANHTHU_FK " +
				"	WHERE LNS_DT.DONVITHUCHIEN_FK = '" + this.bpId + "'  AND LNS.NAM = " + (Integer.parseInt(this.nam) - 1) + " " +
				"	GROUP BY DT.PK_SEQ, DT.TEN, LNS_DT.DONVITHUCHIEN_FK " +
				")NAMCU ON NAMCU.DTID = DT.PK_SEQ AND NAMCU.BPID = LNS_DT.DONVITHUCHIEN_FK " +
				"WHERE LNS_DT.DONVITHUCHIEN_FK = '" + this.bpId + "'  AND LNS.NAM = " + this.nam + " AND LNS_DT.LAPNGANSACH_FK = " + this.lnsId + " " +
				"GROUP BY DT.PK_SEQ, DT.TEN, NAMCU.NAMCU " +
				"ORDER BY DT.TEM ";
		
		System.out.println(query);
		this.dtList = this.db.get(query);
		}
	}
		
	}
	
	private void createNamList(){
		int nam = Integer.parseInt(this.getDateTime().substring(0, 4));
		
		String query = "";
		for(int i = nam - 1; i <= nam + 1; i++)
		{
			query += "select " + i + " as nam, N'Năm " + i + "' as namTen ";
			if(i < nam + 1)
			{
				query += " union all ";
			}
		}
		
		this.namlist = this.db.get(query);

	}
	
	public void init(){
				
//		this.dvkdRs = this.db.get("SELECT PK_SEQ AS DVKDID, DONVIKINHDOANH AS DVKD FROM DONVIKINHDOANH WHERE CONGTY_FK = " + this.ctyId + " ");
		String query = 	"SELECT LNS.PK_SEQ AS NSID, LNS.DIENGIAI  \n " +
						"FROM ERP_LAPNGANSACH LNS  \n " +
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " ";
		
		System.out.println(query);
		
		this.nsList = this.db.get(query);

		String sql = 	"SELECT DISTINCT NCP.DONVITHUCHIEN_FK AS BPID, DVTH.TEN AS BPTEN \n " +
						"FROM ERP_TRUNGTAMCHIPHI TTCP  \n " +
						"INNER JOIN ERP_NHOMCHIPHI NCP ON NCP.TTCHIPHI_FK = TTCP.PK_SEQ  \n " +
						"INNER JOIN ERP_DONVITHUCHIEN DVTH ON DVTH.PK_SEQ = NCP.DONVITHUCHIEN_FK  \n " +
						"WHERE TTCP.CONGANSACH=1 AND TTCP.CONGTY_FK = " + this.ctyId + " "; 		

		System.out.println(sql);
		this.bplist = this.db.get(sql);
		
//		this.createNcpList();
		this.createCpList();
		this.PL_Sales_Budget_Detail();
	}
	
	public void init_duyetngansach(){
		if(this.Id.length() > 0){
			String query = "SELECT NAM, DIENGIAI, HIEULUC, TRANGTHAI FROM ERP_LAPNGANSACH WHERE PK_SEQ = " + this.Id + "";
			System.out.println("___INIT: " + query);
			ResultSet rs = this.db.get(query);
			try{
				rs.next();
				this.nam = rs.getString("NAM");
				this.diengiai = rs.getString("DIENGIAI");
				this.trangthai = rs.getString("TRANGTHAI");
				this.hieuluc = rs.getString("HIEULUC");
			}catch(java.sql.SQLException e){}
			
		}
	}
	
	
	public void init_doanhthu(){
		
//		this.dvkdRs = this.db.get("SELECT PK_SEQ AS DVKDID, DONVIKINHDOANH AS DVKD FROM DONVIKINHDOANH WHERE CONGTY_FK = " + this.ctyId + " ");
		String query = 	"SELECT LNS.PK_SEQ AS NSID, LNS.DIENGIAI " +
						"FROM ERP_LAPNGANSACH LNS " +
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " ";
		
		System.out.println(query);
		
		this.nsList = this.db.get(query);

		String sql = 	"SELECT DISTINCT DT.DONVITHUCHIEN_FK AS BPID, DVTH.TEN AS BPTEN " +
						"FROM ERP_TRUNGTAMDOANHTHU TTDT " +
						"INNER JOIN ERP_DOANHTHU DT ON DT.TTDOANHTHU_FK = TTDT.PK_SEQ " +
						"INNER JOIN ERP_DONVITHUCHIEN DVTH ON DVTH.PK_SEQ = DT.DONVITHUCHIEN_FK " +
						"WHERE TTDT.CONGANSACH = 1 AND TTDT.CONGTY_FK = " + this.ctyId + " "; 		

		System.out.println(sql);
		this.bplist = this.db.get(sql);
		
//		this.createNdtList();
		this.createDtList();
	}

	public void init_taisan(){
		String query = 	"SELECT LNS.PK_SEQ AS NSID, LNS.DIENGIAI " +
						"FROM ERP_LAPNGANSACH LNS " +
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " ";

		System.out.println(query);

		this.nsList = this.db.get(query);
		query =		"SELECT PK_SEQ AS BPID, TEN AS BPTEN " +
		 			"FROM ERP_DONVITHUCHIEN WHERE CONGTY_FK = " + this.ctyId + " "; 
		
		System.out.println(query);
		this.bplist = this.db.get(query);

		if(this.lnsId.trim().length() > 0)
		{
			query = "select NAM from ERP_LAPNGANSACH where PK_SEQ = '" + this.lnsId + "'";
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				try 
				{
					if(rs.next())
					{
						this.nam = rs.getString("NAM");
					}
					rs.close();
				} 
				catch (Exception e) {}
			}
		}
		
		this.createTsList();
	}

	public void init_phanbovakhauhaotaisan(){
		String query = 	"SELECT LNS.PK_SEQ AS NSID, LNS.DIENGIAI " +
						"FROM ERP_LAPNGANSACH LNS " +
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " ";

		System.out.println(query);

		this.nsList = this.db.get(query);

		
		this.createKHvaPBTsList();
		
	}

	
	private void createTsList(){
	
	if(this.loai.equals("1")){
		if(this.bpId.length() > 0 ){
			String query = 	"SELECT	TS.PK_SEQ AS TSID, TS.DIENGIAI, ISNULL(TS.NHOMTAISAN_FK,0) AS NTSID, ISNULL(TS.THANHTIEN, 0) AS THANHTIEN, ISNULL(TS.DONGIA, 0) AS DONGIA, ISNULL(TS.SOLUONG,0) AS SOLUONG, " +
							"ISNULL(TS.T1, 0) AS T1, ISNULL(TS.T2, 0) AS T2, ISNULL(TS.T3, 0) AS T3, ISNULL(TS.T4, 0) AS T4, ISNULL(TS.T5, 0) AS T5, ISNULL(TS.T6, 0) AS T6, " + 
							"ISNULL(TS.T7, 0) AS T7, ISNULL(TS.T8, 0) AS T8, ISNULL(TS.T9, 0) AS T9, ISNULL(TS.T10,0) AS T10, ISNULL(TS.T11,0) AS T11, ISNULL(TS.T12,0) AS T12	" +	
							"FROM ERP_LAPNGANSACH_TAISAN TS " +
							"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = TS.LAPNGANSACH_FK " +
							"INNER JOIN ERP_CONGTY CTY ON CTY.PK_SEQ = LNS.CONGTY_FK " +
							"WHERE TS.DONVITHUCHIEN_FK = '" + this.bpId + "'  AND LNS.NAM = " + this.nam + " AND LNS.PK_SEQ = '" + this.lnsId + "' " ;
			
			System.out.println("Lay TS: " + query);
			this.tsList = this.db.get(query);
		}
	}else{
		if(this.bpId.length() > 0 ){
			String query = 	"SELECT	TS.PK_SEQ AS TSID, TS.DIENGIAI, ISNULL(TS.NHOMCCDC_FK,0) AS NTSID, ISNULL(TS.THANHTIEN, 0) AS THANHTIEN, ISNULL(TS.DONGIA, 0) AS DONGIA, ISNULL(TS.SOLUONG,0) AS SOLUONG, " +
							"ISNULL(TS.T1, 0) AS T1, ISNULL(TS.T2, 0) AS T2, ISNULL(TS.T3, 0) AS T3, ISNULL(TS.T4, 0) AS T4, ISNULL(TS.T5, 0) AS T5, ISNULL(TS.T6, 0) AS T6, " + 
							"ISNULL(TS.T7, 0) AS T7, ISNULL(TS.T8, 0) AS T8, ISNULL(TS.T9, 0) AS T9, ISNULL(TS.T10,0) AS T10, ISNULL(TS.T11,0) AS T11, ISNULL(TS.T12,0) AS T12	" +	
							"FROM ERP_LAPNGANSACH_CCDC TS " +
							"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = TS.LAPNGANSACH_FK " +
							"INNER JOIN ERP_CONGTY CTY ON CTY.PK_SEQ = LNS.CONGTY_FK " +
							"WHERE TS.DONVITHUCHIEN_FK = '" + this.bpId + "'  AND LNS.NAM = " + this.nam + " AND LNS.PK_SEQ = '" + this.lnsId + "' " ;
			
			System.out.println(query);
			this.tsList = this.db.get(query);
		}		
	}
	}

	public ResultSet createCongdungList(){
		String query = "SELECT PK_SEQ AS CDID, TEN AS CONGDUNG FROM ERP_CONGDUNG ";
		return this.db.get(query);
		
	}
	
	private void createKHvaPBTsList(){
	if(this.loai.equals("1")){
		String query = 	"SELECT	TS.DONVITHUCHIEN_FK AS BPID, DVTH.TEN AS BP, ISNULL(TS.NHOMTAISAN_FK, 0) AS NTSID, TS.PK_SEQ AS TSID, TS.DIENGIAI, " +		
						"ISNULL(TS.SOTHANGKH, 0) AS SOTHANGKH, " +
						"ISNULL(TS.THANHTIEN, 0) AS THANHTIEN " +	
						"FROM ERP_LAPNGANSACH_TAISAN TS " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = TS.LAPNGANSACH_FK " +					
						"INNER JOIN ERP_CONGTY CTY ON CTY.PK_SEQ = LNS.CONGTY_FK " +
						"INNER JOIN ERP_DONVITHUCHIEN DVTH ON DVTH.PK_SEQ = TS.DONVITHUCHIEN_FK " +						
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " ";

		
		if(this.lnsId.length() > 0 ){
			query = query + " AND LNS.PK_SEQ = '" + this.lnsId + "' ";
			
			System.out.println(query);
			this.tsList = this.db.get(query);
		}else{
			this.tsList = null;
		}
	}else{
		String query = 	"SELECT	TS.DONVITHUCHIEN_FK AS BPID, DVTH.TEN AS BP, ISNULL(TS.NHOMCCDC_FK, 0) AS NTSID, TS.PK_SEQ AS TSID, TS.DIENGIAI, " +		
				"ISNULL(TS.SOTHANGKH, 0) AS SOTHANGKH, " +
				"ISNULL(TS.THANHTIEN, 0) AS THANHTIEN " +	
				"FROM ERP_LAPNGANSACH_CCDC TS " +
				"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = TS.LAPNGANSACH_FK " +					
				"INNER JOIN ERP_CONGTY CTY ON CTY.PK_SEQ = LNS.CONGTY_FK " +
				"INNER JOIN ERP_DONVITHUCHIEN DVTH ON DVTH.PK_SEQ = TS.DONVITHUCHIEN_FK " +						
				"WHERE LNS.CONGTY_FK = " + this.ctyId + " ";


		if(this.lnsId.length() > 0 ){
			query = query + " AND LNS.PK_SEQ = '" + this.lnsId + "' ";
	
			System.out.println(query);
			this.tsList = this.db.get(query);
		}else{
			this.tsList = null;
		}		
	}
	}
	
	public void save_taisan(HttpServletRequest request)throws ServletException, IOException{
		String query;
		Utility util = new Utility();
		
	if(this.loai.equals("1")){
		if(this.tsIds != null){
			for(int i = 0; i < this.tsIds.length; i++){
				String diengiai = util.antiSQLInspection(request.getParameter("diengiai" + this.tsIds[i])).trim();
				String soluong = util.antiSQLInspection(request.getParameter("soluong" + this.tsIds[i])).replace(",", "");
				String dongia = util.antiSQLInspection(request.getParameter("dongia" + this.tsIds[i])).replace(",", "");
				String thanhtien = util.antiSQLInspection(request.getParameter("thanhtien" + this.tsIds[i])).replace(",", "");
				
				System.out.println("Thanh tien: " + thanhtien);
				String tmp;
				if(diengiai.length() > 0 & !soluong.equals("0") & !dongia.equals("0")){
					query = "UPDATE ERP_LAPNGANSACH_TAISAN SET " +
							"DIENGIAI = N'" + diengiai + "', " +
							"THANHTIEN = '" + thanhtien + "', " +
							"DONGIA =  '" + dongia + "', " +
							"SOLUONG = '" + soluong + "', " +
							"NGAYTAO = '" + this.getDateTime() + "', " +
							"NGUOITAO = '" + this.userId + "' "  ;

					for(int j = 1; j <= 12; j++){
						tmp = util.antiSQLInspection(request.getParameter("T" + this.tsIds[i] + j)).replace(",", "");
						if(tmp.length() == 0) tmp = "0";

						query = query +	",T" + j + " = '" + tmp + "' " ;
					}
			
					query = query + " WHERE PK_SEQ = '" + this.tsIds[i] + "' ";
			
					System.out.println(query);
					this.db.update(query);
					
					query = "DELETE FROM ERP_LAPNGANSACH_TAISAN_KHAUHAODUKIEN WHERE LAPNGANSACH_TAISAN_FK = '" + this.tsIds[i] + "'";
					this.db.update(query);
					
//					this.Tinhkhauhao_New(this.tsIds[i], 24, Double.parseDouble(thanhtien));
				}
				
			}
			
		}
		
		for(int i = 1; i < 10; i++){
			String diengiai = util.antiSQLInspection(request.getParameter("diengiai_new" + i));
			if(diengiai == null) diengiai = "";
			
			String soluong = util.antiSQLInspection(request.getParameter("soluong_new" + i));
			if(soluong == null) soluong = "";
			
			String dongia = util.antiSQLInspection(request.getParameter("dongia_new" + i));
			if(dongia == null) dongia = "";
			
			String thanhtien = util.antiSQLInspection(request.getParameter("thanhtien_new" + i));
			if(thanhtien == null) thanhtien = "";
			
			String tmp;
			if(diengiai.length() > 0 & !soluong.equals("0") & !dongia.equals("0")){
				query = "INSERT INTO ERP_LAPNGANSACH_TAISAN(LAPNGANSACH_FK, DONVITHUCHIEN_FK, DIENGIAI, SOLUONG, DONGIA, THANHTIEN, NGUOITAO, NGAYTAO, " +
						"T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, SOTHANGKH) VALUES (" +
						"" + this.lnsId + ", " + this.bpId + ", " +
						"N'" + diengiai + "', " +
						"'" + soluong.replace(",", "") + "', " +
						"'" + dongia.replace(",", "") + "', " +
						"'" + thanhtien.replace(",", "") + "', " +
						"'" + this.userId + "', '" + this.getDateTime() + "' " ;
					
				for(int j = 1; j <= 12; j++){
					tmp = util.antiSQLInspection(request.getParameter("T_new" + i + j)).replace(",", "");
					if(tmp.length() == 0) tmp = "0";
					query = query +	",'" + tmp + "' " ;
				}

				query = query + ",'24' )";
				
				System.out.println(query);
				this.db.update(query);
				
				query = "SELECT SCOPE_IDENTITY() AS TSID";
				ResultSet rs = this.db.get(query);
				try{
					rs.next();
//					String tsId = rs.getString("TSID");
					
//					this.Tinhkhauhao_New(tsId, 24, Double.parseDouble(thanhtien.replace(",", "")));
				}catch(java.sql.SQLException e){}
			}
		}
	}else{
		if(this.tsIds != null){
			for(int i = 0; i < this.tsIds.length; i++){
				String diengiai = util.antiSQLInspection(request.getParameter("diengiai" + this.tsIds[i])).trim();
				String soluong = util.antiSQLInspection(request.getParameter("soluong" + this.tsIds[i])).replace(",", "");
				String dongia = util.antiSQLInspection(request.getParameter("dongia" + this.tsIds[i])).replace(",", "");
				String thanhtien = util.antiSQLInspection(request.getParameter("thanhtien" + this.tsIds[i])).replace(",", "");
				
				System.out.println("Thanh tien: " + thanhtien);
				String tmp;
				if(diengiai.length() > 0 & !soluong.equals("0") & !dongia.equals("0")){
					query = "UPDATE ERP_LAPNGANSACH_CCDC SET " +
							"DIENGIAI = N'" + diengiai + "', " +
							"THANHTIEN = '" + thanhtien + "', " +
							"DONGIA =  '" + dongia + "', " +
							"SOLUONG = '" + soluong + "', " +
							"NGAYTAO = '" + this.getDateTime() + "', " +
							"NGUOITAO = '" + this.userId + "' "  ;

					for(int j = 1; j <= 12; j++){
						tmp = util.antiSQLInspection(request.getParameter("T" + this.tsIds[i] + j)).replace(",", "");
						if(tmp.length() == 0) tmp = "0";

						query = query +	",T" + j + " = '" + tmp + "' " ;
					}
			
					query = query + " WHERE PK_SEQ = '" + this.tsIds[i] + "' ";
			
					System.out.println(query);
					this.db.update(query);
					
					query = "DELETE FROM ERP_LAPNGANSACH_CCDC_KHAUHAODUKIEN WHERE LAPNGANSACH_CCDC_FK = '" + this.tsIds[i] + "'";
					this.db.update(query);
					
//					this.Tinhkhauhao_New(this.tsIds[i], 24, Double.parseDouble(thanhtien));
				}
				
			}
			
		}
		
		for(int i = 1; i < 10; i++){
			String diengiai = util.antiSQLInspection(request.getParameter("diengiai_new" + i));
			if(diengiai == null) diengiai = "";
			
			String soluong = util.antiSQLInspection(request.getParameter("soluong_new" + i));
			if(soluong == null) soluong = "";
			
			String dongia = util.antiSQLInspection(request.getParameter("dongia_new" + i));
			if(dongia == null) dongia = "";
			
			String thanhtien = util.antiSQLInspection(request.getParameter("thanhtien_new" + i));
			if(thanhtien == null) thanhtien = "";
			
			String tmp;
			if(diengiai.length() > 0 & !soluong.equals("0") & !dongia.equals("0")){
				query = "INSERT INTO ERP_LAPNGANSACH_CCDC(LAPNGANSACH_FK, DONVITHUCHIEN_FK, DIENGIAI, SOLUONG, DONGIA, THANHTIEN, NGUOITAO, NGAYTAO, " +
						"T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, SOTHANGKH) VALUES (" +
						"" + this.lnsId + ", " + this.bpId + ", " +
						"N'" + diengiai + "', " +
						"'" + soluong.replace(",", "") + "', " +
						"'" + dongia.replace(",", "") + "', " +
						"'" + thanhtien.replace(",", "") + "', " +
						"'" + this.userId + "', '" + this.getDateTime() + "' " ;
					
				for(int j = 1; j <= 12; j++){
					tmp = util.antiSQLInspection(request.getParameter("T_new" + i + j)).replace(",", "");
					if(tmp.length() == 0) tmp = "0";
					query = query +	",'" + tmp + "' " ;
				}

				query = query + ",'24' )";
				
				System.out.println(query);
				this.db.update(query);
				
				query = "SELECT SCOPE_IDENTITY() AS TSID";
				ResultSet rs = this.db.get(query);
				try{
					rs.next();
//					String tsId = rs.getString("TSID");
					
//					this.Tinhkhauhao_New(tsId, 24, Double.parseDouble(thanhtien.replace(",", "")));
				}catch(java.sql.SQLException e){}
			}
		}		
	}
	}

	public boolean save_khvapbtaisan(HttpServletRequest request)throws ServletException, IOException
	{
		String query;
		boolean result = true;
		Utility util = new Utility();
		ResultSet rs;
		String tsstr = "";
	if(this.loai.equals("1"))
	{
		if(this.tsIds != null)
		{
			try
			{
				for(int i = 0; i < tsIds.length; i++)
				{
					tsstr = tsstr + tsIds[i] + ",";
					String ntsId = util.antiSQLInspection(request.getParameter("ntsId" + this.tsIds[i]));
					String sothangkh = util.antiSQLInspection(request.getParameter("sothangKH" + this.tsIds[i]));
					
					if(!ntsId.equals("0")){
						query = "DELETE ERP_LAPNGANSACH_TAISAN_PHANBO WHERE LNSTAISAN_FK = " + this.tsIds[i] + "";
						//System.out.println(query);
						if(!this.db.update(query))
							result =  false;
						
						query = "SELECT TTCP_FK AS TTCPID FROM ERP_NHOMTAISAN_PHANBOKHAUHAO WHERE NHOMTAISAN_FK = " + ntsId + "";
						rs = this.db.get(query);
						while(rs.next()){
							String pt = util.antiSQLInspection(request.getParameter("pt_" + this.tsIds[i] + "_" + rs.getString("TTCPID")));
							if(pt == null) pt = "0";
							
							query = "INSERT INTO ERP_LAPNGANSACH_TAISAN_PHANBO(LNSTAISAN_FK, TTCP_FK, PHANTRAM) " +
									"VALUES(" + this.tsIds[i] + ", " + rs.getString("TTCPID") + ", '" + pt + "')";
							
							//System.out.println(query);
							if(!this.db.update(query))
								result =  false;
							
						}
					
						query = "UPDATE ERP_LAPNGANSACH_TAISAN SET NHOMTAISAN_FK = " + ntsId + ", SOTHANGKH = " + sothangkh + " WHERE PK_SEQ = " + this.tsIds[i] + " ";
						//System.out.println(query);
						if(!this.db.update(query))
							result =  false;
					}
					else{
						query = "UPDATE ERP_LAPNGANSACH_TAISAN SET SOTHANGKH = " + sothangkh + " WHERE PK_SEQ = " + this.tsIds[i] + " ";
						//System.out.println(query);
						if(!this.db.update(query))
							result =  false;
					}
				
				}
				tsstr = tsstr.substring(0, tsstr.length()-1);
				
				this.Khauhao(tsstr);
				
				this.Phanbokhauhao();
				
				this.CapnhatPhanboChiphi_TaiSan();
				
			}catch(java.sql.SQLException e){}
			
		}
	}else{
		if(this.tsIds != null){
			try{
				for(int i = 0; i < tsIds.length; i++){
					tsstr = tsstr + tsIds[i] + ",";
					String ntsId = util.antiSQLInspection(request.getParameter("ntsId" + this.tsIds[i]));
					String sothangkh = util.antiSQLInspection(request.getParameter("sothangKH" + this.tsIds[i]));
					
					if(!ntsId.equals("0")){
						query = "DELETE ERP_LAPNGANSACH_CCDC_PHANBO WHERE LNSCCDC_FK = " + this.tsIds[i] + "";
						System.out.println(query);
						if(!this.db.update(query))
							result =  false;
						
						query = "SELECT TTCP_FK AS TTCPID FROM ERP_NHOMCCDC_PHANBOKHAUHAO WHERE NHOMCCDC_FK = " + ntsId + "";
						rs = this.db.get(query);
						while(rs.next()){
							String pt = util.antiSQLInspection(request.getParameter("pt_" + this.tsIds[i] + "_" + rs.getString("TTCPID")));
							if(pt == null) pt = "0";
							
							query = "INSERT INTO ERP_LAPNGANSACH_CCDC_PHANBO(LNSCCDC_FK, TTCP_FK, PHANTRAM) " +
									"VALUES(" + this.tsIds[i] + ", " + rs.getString("TTCPID") + ", '" + pt + "')";
							
							System.out.println(query);
							if(!this.db.update(query))
								result =  false;
							
						}
					
						query = "UPDATE ERP_LAPNGANSACH_CCDC SET NHOMCCDC_FK = " + ntsId + ", SOTHANGKH = " + sothangkh + " WHERE PK_SEQ = " + this.tsIds[i] + " ";
						System.out.println(query);
						if(!this.db.update(query))
							result =  false;
					}
					else{
						query = "UPDATE ERP_LAPNGANSACH_CCDC SET SOTHANGKH = " + sothangkh + " WHERE PK_SEQ = " + this.tsIds[i] + " ";
						System.out.println(query);
						if(!this.db.update(query))
							result =  false;
					}
				
				}
				tsstr = tsstr.substring(0, tsstr.length()-1);
				
				this.Khauhao(tsstr);
				
				this.Phanbokhauhao();
				
				this.CapnhatPhanboChiphi_TaiSan();
				
			}catch(java.sql.SQLException e){}
			
		}		
	}
		return result;
	}
	
	private void Khauhao(String tsstr)
	{
		if(this.loai.equals("1")){
			String query = "DELETE ERP_LAPNGANSACH_TAISAN_KHAUHAODUKIEN WHERE LAPNGANSACH_TAISAN_FK IN (" + tsstr + ")";
			this.db.update(query);
	
			query = 	"SELECT	LNS_TS.PK_SEQ AS ID, LNS_TS.DONGIA, LNS_TS.T1, LNS_TS.T2, LNS_TS.T3, LNS_TS.T4, LNS_TS.T5, LNS_TS.T6, LNS_TS.T7, LNS_TS.T8, LNS_TS.T9, " +
						"LNS_TS.T10, LNS_TS.T11, LNS_TS.T12, LNS_TS.SOTHANGKH, LNS.NAM " +
						"FROM ERP_LAPNGANSACH_TAISAN LNS_TS " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = LNS_TS.LAPNGANSACH_FK " +
						"WHERE LNS_TS.PK_SEQ IN (" + tsstr + ") AND LNS.PK_SEQ = " + this.lnsId + " ";
			
			//System.out.println(query);
			ResultSet rs = this.db.get(query);
			double thanhtien;
			
			if(rs != null){
				try{
					while(rs.next()){
	
						/*if(!rs.getString("T1").equals("0")){
							thanhtien = Double.parseDouble(rs.getString("DONGIA"))*Double.parseDouble(rs.getString("T1"));
							this.Tinhkhauhao_New(rs.getString("ID"), Integer.parseInt(rs.getString("SOTHANGKH")), thanhtien, 1, Integer.parseInt(rs.getString("NAM")));	
						}
					
						if(!rs.getString("T2").equals("0")){
							thanhtien = Double.parseDouble(rs.getString("DONGIA"))*Double.parseDouble(rs.getString("T2"));
							this.Tinhkhauhao_New(rs.getString("ID"), Integer.parseInt(rs.getString("SOTHANGKH")), thanhtien, 2, Integer.parseInt(rs.getString("NAM")));	
						}
	
						if(!rs.getString("T3").equals("0")){
							thanhtien = Double.parseDouble(rs.getString("DONGIA"))*Double.parseDouble(rs.getString("T3"));
							this.Tinhkhauhao_New(rs.getString("ID"), Integer.parseInt(rs.getString("SOTHANGKH")), thanhtien, 3, Integer.parseInt(rs.getString("NAM")));	
						}
	
						if(!rs.getString("T4").equals("0")){
							thanhtien = Double.parseDouble(rs.getString("DONGIA"))*Double.parseDouble(rs.getString("T4"));
							this.Tinhkhauhao_New(rs.getString("ID"), Integer.parseInt(rs.getString("SOTHANGKH")), thanhtien, 4, Integer.parseInt(rs.getString("NAM")));	
						}
	
						if(!rs.getString("T5").equals("0")){
							thanhtien = Double.parseDouble(rs.getString("DONGIA"))*Double.parseDouble(rs.getString("T5"));
							this.Tinhkhauhao_New(rs.getString("ID"), Integer.parseInt(rs.getString("SOTHANGKH")), thanhtien, 5, Integer.parseInt(rs.getString("NAM")));	
						}
	
						if(!rs.getString("T6").equals("0")){
							thanhtien = Double.parseDouble(rs.getString("DONGIA"))*Double.parseDouble(rs.getString("T6"));
							this.Tinhkhauhao_New(rs.getString("ID"), Integer.parseInt(rs.getString("SOTHANGKH")), thanhtien, 6, Integer.parseInt(rs.getString("NAM")));	
						}
	
						if(!rs.getString("T7").equals("0")){
							thanhtien = Double.parseDouble(rs.getString("DONGIA"))*Double.parseDouble(rs.getString("T7"));
							this.Tinhkhauhao_New(rs.getString("ID"), Integer.parseInt(rs.getString("SOTHANGKH")), thanhtien, 7, Integer.parseInt(rs.getString("NAM")));	
						}
	
						if(!rs.getString("T8").equals("0")){
							thanhtien = Double.parseDouble(rs.getString("DONGIA"))*Double.parseDouble(rs.getString("T8"));
							this.Tinhkhauhao_New(rs.getString("ID"), Integer.parseInt(rs.getString("SOTHANGKH")), thanhtien, 8, Integer.parseInt(rs.getString("NAM")));	
						}
	
						if(!rs.getString("T9").equals("0")){
							thanhtien = Double.parseDouble(rs.getString("DONGIA"))*Double.parseDouble(rs.getString("T9"));
							this.Tinhkhauhao_New(rs.getString("ID"), Integer.parseInt(rs.getString("SOTHANGKH")), thanhtien, 9, Integer.parseInt(rs.getString("NAM")));	
						}
	
						if(!rs.getString("T10").equals("0")){
							thanhtien = Double.parseDouble(rs.getString("DONGIA"))*Double.parseDouble(rs.getString("T10"));
							this.Tinhkhauhao_New(rs.getString("ID"), Integer.parseInt(rs.getString("SOTHANGKH")), thanhtien, 10, Integer.parseInt(rs.getString("NAM")));	
						}
	
						if(!rs.getString("T11").equals("0")){
							thanhtien = Double.parseDouble(rs.getString("DONGIA"))*Double.parseDouble(rs.getString("T11"));
							this.Tinhkhauhao_New(rs.getString("ID"), Integer.parseInt(rs.getString("SOTHANGKH")), thanhtien, 11, Integer.parseInt(rs.getString("NAM")));	
						}
	
						if(!rs.getString("T12").equals("0")){
							thanhtien = Double.parseDouble(rs.getString("DONGIA"))*Double.parseDouble(rs.getString("T12"));
							this.Tinhkhauhao_New(rs.getString("ID"), Integer.parseInt(rs.getString("SOTHANGKH")), thanhtien, 12, Integer.parseInt(rs.getString("NAM")));	
						}*/
						
						
						
						/*************Thang khau hao bat dau sau thang tinh sau khao***************/
						if(!rs.getString("T1").equals("0")){
							thanhtien = Double.parseDouble(rs.getString("DONGIA"))*Double.parseDouble(rs.getString("T1"));
							this.Tinhkhauhao_New(rs.getString("ID"), Integer.parseInt(rs.getString("SOTHANGKH")), thanhtien, 2, Integer.parseInt(rs.getString("NAM")));	
						}
					
						if(!rs.getString("T2").equals("0")){
							thanhtien = Double.parseDouble(rs.getString("DONGIA"))*Double.parseDouble(rs.getString("T2"));
							this.Tinhkhauhao_New(rs.getString("ID"), Integer.parseInt(rs.getString("SOTHANGKH")), thanhtien, 3, Integer.parseInt(rs.getString("NAM")));	
						}
	
						if(!rs.getString("T3").equals("0")){
							thanhtien = Double.parseDouble(rs.getString("DONGIA"))*Double.parseDouble(rs.getString("T3"));
							this.Tinhkhauhao_New(rs.getString("ID"), Integer.parseInt(rs.getString("SOTHANGKH")), thanhtien, 4, Integer.parseInt(rs.getString("NAM")));	
						}
	
						if(!rs.getString("T4").equals("0")){
							thanhtien = Double.parseDouble(rs.getString("DONGIA"))*Double.parseDouble(rs.getString("T4"));
							this.Tinhkhauhao_New(rs.getString("ID"), Integer.parseInt(rs.getString("SOTHANGKH")), thanhtien, 5, Integer.parseInt(rs.getString("NAM")));	
						}
	
						if(!rs.getString("T5").equals("0")){
							thanhtien = Double.parseDouble(rs.getString("DONGIA"))*Double.parseDouble(rs.getString("T5"));
							this.Tinhkhauhao_New(rs.getString("ID"), Integer.parseInt(rs.getString("SOTHANGKH")), thanhtien, 6, Integer.parseInt(rs.getString("NAM")));	
						}
	
						if(!rs.getString("T6").equals("0")){
							thanhtien = Double.parseDouble(rs.getString("DONGIA"))*Double.parseDouble(rs.getString("T6"));
							this.Tinhkhauhao_New(rs.getString("ID"), Integer.parseInt(rs.getString("SOTHANGKH")), thanhtien, 7, Integer.parseInt(rs.getString("NAM")));	
						}
	
						if(!rs.getString("T7").equals("0")){
							thanhtien = Double.parseDouble(rs.getString("DONGIA"))*Double.parseDouble(rs.getString("T7"));
							this.Tinhkhauhao_New(rs.getString("ID"), Integer.parseInt(rs.getString("SOTHANGKH")), thanhtien, 8, Integer.parseInt(rs.getString("NAM")));	
						}
	
						if(!rs.getString("T8").equals("0")){
							thanhtien = Double.parseDouble(rs.getString("DONGIA"))*Double.parseDouble(rs.getString("T8"));
							this.Tinhkhauhao_New(rs.getString("ID"), Integer.parseInt(rs.getString("SOTHANGKH")), thanhtien, 9, Integer.parseInt(rs.getString("NAM")));	
						}
	
						if(!rs.getString("T9").equals("0")){
							thanhtien = Double.parseDouble(rs.getString("DONGIA"))*Double.parseDouble(rs.getString("T9"));
							this.Tinhkhauhao_New(rs.getString("ID"), Integer.parseInt(rs.getString("SOTHANGKH")), thanhtien, 10, Integer.parseInt(rs.getString("NAM")));	
						}
	
						if(!rs.getString("T10").equals("0")){
							thanhtien = Double.parseDouble(rs.getString("DONGIA"))*Double.parseDouble(rs.getString("T10"));
							this.Tinhkhauhao_New(rs.getString("ID"), Integer.parseInt(rs.getString("SOTHANGKH")), thanhtien, 11, Integer.parseInt(rs.getString("NAM")));	
						}
	
						if(!rs.getString("T11").equals("0")){
							thanhtien = Double.parseDouble(rs.getString("DONGIA"))*Double.parseDouble(rs.getString("T11"));
							this.Tinhkhauhao_New(rs.getString("ID"), Integer.parseInt(rs.getString("SOTHANGKH")), thanhtien, 12, Integer.parseInt(rs.getString("NAM")));	
						}
	
					
					}
				}catch(java.sql.SQLException e){}
			}
		}else{
			String query = "DELETE ERP_LAPNGANSACH_CCDC_KHAUHAODUKIEN WHERE LAPNGANSACH_CCDC_FK IN (" + tsstr + ")";
			this.db.update(query);
	
			query = 	"SELECT	LNS_TS.PK_SEQ AS ID, LNS_TS.DONGIA, LNS_TS.T1, LNS_TS.T2, LNS_TS.T3, LNS_TS.T4, LNS_TS.T5, LNS_TS.T6, LNS_TS.T7, LNS_TS.T8, LNS_TS.T9, " +
						"LNS_TS.T10, LNS_TS.T11, LNS_TS.T12, LNS_TS.SOTHANGKH, LNS.NAM " +
						"FROM ERP_LAPNGANSACH_CCDC LNS_TS " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = LNS_TS.LAPNGANSACH_FK " +
						"WHERE LNS_TS.PK_SEQ IN (" + tsstr + ") AND LNS.PK_SEQ = " + this.lnsId + " ";
			
			System.out.println(query);
			ResultSet rs = this.db.get(query);
			double thanhtien;
			
			if(rs != null){
				try{
					while(rs.next()){
	
						/*if(!rs.getString("T1").equals("0")){
							thanhtien = Double.parseDouble(rs.getString("DONGIA"))*Double.parseDouble(rs.getString("T1"));
							this.Tinhkhauhao_New(rs.getString("ID"), Integer.parseInt(rs.getString("SOTHANGKH")), thanhtien, 1, Integer.parseInt(rs.getString("NAM")));	
						}
					
						if(!rs.getString("T2").equals("0")){
							thanhtien = Double.parseDouble(rs.getString("DONGIA"))*Double.parseDouble(rs.getString("T2"));
							this.Tinhkhauhao_New(rs.getString("ID"), Integer.parseInt(rs.getString("SOTHANGKH")), thanhtien, 2, Integer.parseInt(rs.getString("NAM")));	
						}
	
						if(!rs.getString("T3").equals("0")){
							thanhtien = Double.parseDouble(rs.getString("DONGIA"))*Double.parseDouble(rs.getString("T3"));
							this.Tinhkhauhao_New(rs.getString("ID"), Integer.parseInt(rs.getString("SOTHANGKH")), thanhtien, 3, Integer.parseInt(rs.getString("NAM")));	
						}
	
						if(!rs.getString("T4").equals("0")){
							thanhtien = Double.parseDouble(rs.getString("DONGIA"))*Double.parseDouble(rs.getString("T4"));
							this.Tinhkhauhao_New(rs.getString("ID"), Integer.parseInt(rs.getString("SOTHANGKH")), thanhtien, 4, Integer.parseInt(rs.getString("NAM")));	
						}
	
						if(!rs.getString("T5").equals("0")){
							thanhtien = Double.parseDouble(rs.getString("DONGIA"))*Double.parseDouble(rs.getString("T5"));
							this.Tinhkhauhao_New(rs.getString("ID"), Integer.parseInt(rs.getString("SOTHANGKH")), thanhtien, 5, Integer.parseInt(rs.getString("NAM")));	
						}
	
						if(!rs.getString("T6").equals("0")){
							thanhtien = Double.parseDouble(rs.getString("DONGIA"))*Double.parseDouble(rs.getString("T6"));
							this.Tinhkhauhao_New(rs.getString("ID"), Integer.parseInt(rs.getString("SOTHANGKH")), thanhtien, 6, Integer.parseInt(rs.getString("NAM")));	
						}
	
						if(!rs.getString("T7").equals("0")){
							thanhtien = Double.parseDouble(rs.getString("DONGIA"))*Double.parseDouble(rs.getString("T7"));
							this.Tinhkhauhao_New(rs.getString("ID"), Integer.parseInt(rs.getString("SOTHANGKH")), thanhtien, 7, Integer.parseInt(rs.getString("NAM")));	
						}
	
						if(!rs.getString("T8").equals("0")){
							thanhtien = Double.parseDouble(rs.getString("DONGIA"))*Double.parseDouble(rs.getString("T8"));
							this.Tinhkhauhao_New(rs.getString("ID"), Integer.parseInt(rs.getString("SOTHANGKH")), thanhtien, 8, Integer.parseInt(rs.getString("NAM")));	
						}
	
						if(!rs.getString("T9").equals("0")){
							thanhtien = Double.parseDouble(rs.getString("DONGIA"))*Double.parseDouble(rs.getString("T9"));
							this.Tinhkhauhao_New(rs.getString("ID"), Integer.parseInt(rs.getString("SOTHANGKH")), thanhtien, 9, Integer.parseInt(rs.getString("NAM")));	
						}
	
						if(!rs.getString("T10").equals("0")){
							thanhtien = Double.parseDouble(rs.getString("DONGIA"))*Double.parseDouble(rs.getString("T10"));
							this.Tinhkhauhao_New(rs.getString("ID"), Integer.parseInt(rs.getString("SOTHANGKH")), thanhtien, 10, Integer.parseInt(rs.getString("NAM")));	
						}
	
						if(!rs.getString("T11").equals("0")){
							thanhtien = Double.parseDouble(rs.getString("DONGIA"))*Double.parseDouble(rs.getString("T11"));
							this.Tinhkhauhao_New(rs.getString("ID"), Integer.parseInt(rs.getString("SOTHANGKH")), thanhtien, 11, Integer.parseInt(rs.getString("NAM")));	
						}
	
						if(!rs.getString("T12").equals("0")){
							thanhtien = Double.parseDouble(rs.getString("DONGIA"))*Double.parseDouble(rs.getString("T12"));
							this.Tinhkhauhao_New(rs.getString("ID"), Integer.parseInt(rs.getString("SOTHANGKH")), thanhtien, 12, Integer.parseInt(rs.getString("NAM")));	
						}*/
						
						
						if(!rs.getString("T1").equals("0")){
							thanhtien = Double.parseDouble(rs.getString("DONGIA"))*Double.parseDouble(rs.getString("T1"));
							this.Tinhkhauhao_New(rs.getString("ID"), Integer.parseInt(rs.getString("SOTHANGKH")), thanhtien, 2, Integer.parseInt(rs.getString("NAM")));	
						}
					
						if(!rs.getString("T2").equals("0")){
							thanhtien = Double.parseDouble(rs.getString("DONGIA"))*Double.parseDouble(rs.getString("T2"));
							this.Tinhkhauhao_New(rs.getString("ID"), Integer.parseInt(rs.getString("SOTHANGKH")), thanhtien, 3, Integer.parseInt(rs.getString("NAM")));	
						}
	
						if(!rs.getString("T3").equals("0")){
							thanhtien = Double.parseDouble(rs.getString("DONGIA"))*Double.parseDouble(rs.getString("T3"));
							this.Tinhkhauhao_New(rs.getString("ID"), Integer.parseInt(rs.getString("SOTHANGKH")), thanhtien, 4, Integer.parseInt(rs.getString("NAM")));	
						}
	
						if(!rs.getString("T4").equals("0")){
							thanhtien = Double.parseDouble(rs.getString("DONGIA"))*Double.parseDouble(rs.getString("T4"));
							this.Tinhkhauhao_New(rs.getString("ID"), Integer.parseInt(rs.getString("SOTHANGKH")), thanhtien, 5, Integer.parseInt(rs.getString("NAM")));	
						}
	
						if(!rs.getString("T5").equals("0")){
							thanhtien = Double.parseDouble(rs.getString("DONGIA"))*Double.parseDouble(rs.getString("T5"));
							this.Tinhkhauhao_New(rs.getString("ID"), Integer.parseInt(rs.getString("SOTHANGKH")), thanhtien, 6, Integer.parseInt(rs.getString("NAM")));	
						}
	
						if(!rs.getString("T6").equals("0")){
							thanhtien = Double.parseDouble(rs.getString("DONGIA"))*Double.parseDouble(rs.getString("T6"));
							this.Tinhkhauhao_New(rs.getString("ID"), Integer.parseInt(rs.getString("SOTHANGKH")), thanhtien, 7, Integer.parseInt(rs.getString("NAM")));	
						}
	
						if(!rs.getString("T7").equals("0")){
							thanhtien = Double.parseDouble(rs.getString("DONGIA"))*Double.parseDouble(rs.getString("T7"));
							this.Tinhkhauhao_New(rs.getString("ID"), Integer.parseInt(rs.getString("SOTHANGKH")), thanhtien, 8, Integer.parseInt(rs.getString("NAM")));	
						}
	
						if(!rs.getString("T8").equals("0")){
							thanhtien = Double.parseDouble(rs.getString("DONGIA"))*Double.parseDouble(rs.getString("T8"));
							this.Tinhkhauhao_New(rs.getString("ID"), Integer.parseInt(rs.getString("SOTHANGKH")), thanhtien, 9, Integer.parseInt(rs.getString("NAM")));	
						}
	
						if(!rs.getString("T9").equals("0")){
							thanhtien = Double.parseDouble(rs.getString("DONGIA"))*Double.parseDouble(rs.getString("T9"));
							this.Tinhkhauhao_New(rs.getString("ID"), Integer.parseInt(rs.getString("SOTHANGKH")), thanhtien, 10, Integer.parseInt(rs.getString("NAM")));	
						}
	
						if(!rs.getString("T10").equals("0")){
							thanhtien = Double.parseDouble(rs.getString("DONGIA"))*Double.parseDouble(rs.getString("T10"));
							this.Tinhkhauhao_New(rs.getString("ID"), Integer.parseInt(rs.getString("SOTHANGKH")), thanhtien, 11, Integer.parseInt(rs.getString("NAM")));	
						}
	
						if(!rs.getString("T11").equals("0")){
							thanhtien = Double.parseDouble(rs.getString("DONGIA"))*Double.parseDouble(rs.getString("T11"));
							this.Tinhkhauhao_New(rs.getString("ID"), Integer.parseInt(rs.getString("SOTHANGKH")), thanhtien, 12, Integer.parseInt(rs.getString("NAM")));	
						}
	
					
					}
				}catch(java.sql.SQLException e){}
			}
			
		}
	
	}
	
	private void Phanbokhauhao()
	{
		// RESET LAI TABLE ERP_TRUNGTAMCHIPHI_LNS_CCDC
		if(this.loai.equals("1"))
		{
			//Cau lenh dang viet sai.
			/*String query = 	"DELETE DISTINCT ERP_TRUNGTAMCHIPHI_LNS_TAISAN WHERE LNS_TAISAN_FK IN " +
							"	(SELECT KHDK.LAPNGANSACH_TAISAN_FK  " + 
							"	FROM	ERP_LAPNGANSACH_TAISAN_KHAUHAODUKIEN KHDK " +
							"	INNER JOIN ERP_LAPNGANSACH_TAISAN LNSTS ON LNSTS.PK_SEQ = KHDK.LAPNGANSACH_TAISAN_FK " +
							"	WHERE LNSTS.PK_SEQ = " + this.lnsId + "" +
							"	)" ;*/
			String query = 	" DELETE ERP_TRUNGTAMCHIPHI_LNS_TAISAN  " +
							" WHERE LNS_TAISAN_FK IN 	(  " +
														" SELECT DISTINCT '0_' + cast(LNSTS.PK_SEQ as varchar(10))	  " +
														" FROM	ERP_LAPNGANSACH_TAISAN_KHAUHAODUKIEN KHDK 	INNER JOIN ERP_LAPNGANSACH_TAISAN LNSTS ON LNSTS.PK_SEQ = KHDK.LAPNGANSACH_TAISAN_FK 	  " +
														" WHERE LNSTS.LAPNGANSACH_FK = '" + this.lnsId + "' " +
														") " ;
			
			System.out.println("Delete TTCP_LNS_TAISAN: " + query);
			this.db.update(query);
			
			
			query = 	"SELECT A.LNS_TAISAN_FK, A.TTCPID, A.NAM, A.THANG, SUM(A.NGANSACH) AS NGANSACH " +
						"FROM( " +
						"SELECT	LNS_KHDK.LAPNGANSACH_TAISAN_FK AS LNS_TAISAN_FK, LNS_KHDK.KHAUHAODUKIEN, LNS_KHDK.THANG, LNS_KHDK.NAM, PB.TTCP_FK AS TTCPID, PB.PHANTRAM, " +
						"		LNS_KHDK.KHAUHAODUKIEN*PB.PHANTRAM/100 AS NGANSACH " +
						"FROM ERP_LAPNGANSACH_TAISAN_KHAUHAODUKIEN LNS_KHDK " +
						"INNER JOIN ERP_LAPNGANSACH_TAISAN_PHANBO PB ON PB.LNSTAISAN_FK = LNS_KHDK.LAPNGANSACH_TAISAN_FK  " +
						"where LNS_KHDK.NAM = ( select nam from ERP_LapNganSach where pk_seq = '" + this.lnsId + "' ) " + 
						")A " +
						"GROUP BY A.TTCPID, A.NAM, A.THANG, A.LNS_TAISAN_FK " ;

			ResultSet rs = this.db.get(query);
			
			if(rs != null)
			{
				try
				{
					while(rs.next())
					{	
						query = "INSERT INTO ERP_TRUNGTAMCHIPHI_LNS_TAISAN(TTCP_FK, THANG, NAM, NGANSACH, LNS_TAISAN_FK, LAPNGANSACH_FK) VALUES(" + rs.getString("TTCPID") + ", " + rs.getString("THANG") + ", " +
								"" + rs.getString("NAM") + ", " + Math.round(Double.parseDouble(rs.getString("NGANSACH"))) + ", '0_" + rs.getString("LNS_TAISAN_FK") + "', " + this.lnsId + " )";
						
						// Da ton tai nghia la chinh sua lai phan lap ngan sach tai san
						if(!this.db.update(query)){
													
							query = "UPDATE ERP_TRUNGTAMCHIPHI_LNS_TAISAN SET NGANSACH = NGANSACH + " + Math.round(Double.parseDouble(rs.getString("NGANSACH"))) + "  " +
									"WHERE TTCP_FK = " + rs.getString("TTCPID") + " AND THANG = '" + rs.getString("THANG") + "' AND NAM = '" + rs.getString("NAM") + "' " +
									"AND LNS_TAISAN_FK = '0_" + rs.getString("LNS_TAISAN_FK") + "' AND LAPNGANSACH_FK = "  + this.lnsId + "";
							
							//System.out.println("" + i++ + " " +query);
							this.db.update(query);
							
						}
					}
					
					rs.close();
				}
				catch(Exception e){}
				
			}
			
		}
		else
		{
			/*String query = 	"DELETE DISTINCT ERP_TRUNGTAMCHIPHI_LNS_CCDC WHERE LNS_CCDC_FK IN " +
							"	(SELECT KHDK.LAPNGANSACH_CCDC_FK  " + 
							"	FROM	ERP_LAPNGANSACH_CCDC_KHAUHAODUKIEN KHDK " +
							"	INNER JOIN ERP_LAPNGANSACH_CCDC LNSTS ON LNSTS.PK_SEQ = KHDK.LAPNGANSACH_CCDC_FK " +
							"	WHERE LNSTS.PK_SEQ = " + this.lnsId + "" +
							"	)" ;*/
			
			String query = 	" DELETE ERP_TRUNGTAMCHIPHI_LNS_CCDC   " +
							"WHERE LNS_CCDC_FK IN 	(   " +
													"SELECT DISTINCT '2_' + cast(LNSTS.PK_SEQ as varchar(10))	   " +
													"FROM	ERP_LAPNGANSACH_CCDC_KHAUHAODUKIEN KHDK INNER JOIN ERP_LAPNGANSACH_CCDC LNSTS ON LNSTS.PK_SEQ = KHDK.LAPNGANSACH_CCDC_FK	   " +
													"WHERE LNSTS.LAPNGANSACH_FK = '" + this.lnsId + "'  " +
													") " ;
				
			System.out.println("Delete TTCP_LNS_CCDC: " + query);
			this.db.update(query);
	
			query = 	"SELECT A.LNS_CCDC_FK, A.TTCPID, A.NAM, A.THANG, SUM(A.NGANSACH) AS NGANSACH " +
						"FROM( " +
						"SELECT	LNS_KHDK.LAPNGANSACH_CCDC_FK AS LNS_CCDC_FK, LNS_KHDK.KHAUHAODUKIEN, LNS_KHDK.THANG, LNS_KHDK.NAM, PB.TTCP_FK AS TTCPID, PB.PHANTRAM, " +
						"		LNS_KHDK.KHAUHAODUKIEN*PB.PHANTRAM/100 AS NGANSACH " +
						"FROM ERP_LAPNGANSACH_CCDC_KHAUHAODUKIEN LNS_KHDK " +
						"INNER JOIN ERP_LAPNGANSACH_CCDC_PHANBO PB ON PB.LNSCCDC_FK = LNS_KHDK.LAPNGANSACH_CCDC_FK " +
						"where LNS_KHDK.NAM = ( select nam from ERP_LapNganSach where pk_seq = '" + this.lnsId + "' ) " + 
						")A " +
						"GROUP BY A.TTCPID, A.NAM, A.THANG, A.LNS_CCDC_FK " ;
			
			ResultSet rs = this.db.get(query);
	
			if(rs != null){
			try{
				while(rs.next()){
					
					query = "INSERT INTO ERP_TRUNGTAMCHIPHI_LNS_CCDC(TTCP_FK, THANG, NAM, NGANSACH, LNS_CCDC_FK, LAPNGANSACH_FK) VALUES(" + rs.getString("TTCPID") + ", " + rs.getString("THANG") + ", " +
							"" + rs.getString("NAM") + ", " + Math.round(Double.parseDouble(rs.getString("NGANSACH"))) + ", '2_" + rs.getString("LNS_CCDC_FK") + "', " + this.lnsId + " )";
				
					// Da ton tai nghia la chinh sua lai phan lap ngan sach tai san
					if(!this.db.update(query)){
											
						query = "UPDATE ERP_TRUNGTAMCHIPHI_LNS_CCDC SET NGANSACH = NGANSACH + " + Math.round(Double.parseDouble(rs.getString("NGANSACH"))) + "  " +
								"WHERE TTCP_FK = " + rs.getString("TTCPID") + " AND THANG = '" + rs.getString("THANG") + "' AND NAM = '" + rs.getString("NAM") + "' " +
								"AND LNS_CCDC_FK = '2_" + rs.getString("LNS_CCDC_FK") + "' AND LAPNGANSACH_FK = "  + this.lnsId + "";
					
						//System.out.println("" + i++ + " " +query);
						this.db.update(query);
					
					}
				
				}
			
				rs.close();
			}
			catch(java.sql.SQLException e){}
		
			}		
		}
	}
	
	Hashtable<String, Hashtable<String, Long> > nhan_form_choSelected = null; 
	
	private void CapnhatPhanboChiphi_TaiSan()
	{
		nhan_form_choSelected = new Hashtable< String, Hashtable<String,Long> >();
		
		String query = "";
		
		
		//INIT LAIM CAC TAI KHOAN GOC CHI PHI & TAI SAN
		query = "UPDATE ERP_TRUNGTAMCHIPHI_NGANSACH SET NGANSACH = 0  where lapngansach_fk = '" + this.lnsId + "' ";
		this.db.update(query);

		
		//INIT TAI SAN
		query = "select TTCP_FK as TTCPID, THANG, NAM, sum(NGANSACH) as NganSach  " +
				"from ERP_TRUNGTAMCHIPHI_LNS_TAISAN where LAPNGANSACH_FK = '" + this.lnsId + "' " +
				"group by TTCP_FK, THANG, NAM " ;
		
		System.out.println("1___INIT TAI SAN; " + query);
		ResultSet rsTS = this.db.get(query);
		
		if(rsTS != null)
		{
			try
			{
				while(rsTS.next())
				{
					query = "INSERT INTO ERP_TRUNGTAMCHIPHI_NGANSACH(TTCP_FK, THANG, NAM, NGANSACH, LAPNGANSACH_FK) " +
							"VALUES(" + rsTS.getString("TTCPID") + ", " + rsTS.getString("THANG") + ", " + rsTS.getString("NAM") + ", " + rsTS.getString("NGANSACH") + ", " + this.lnsId + ")";
					
					if(!this.db.update(query))
					{
						query = "UPDATE ERP_TRUNGTAMCHIPHI_NGANSACH SET NGANSACH = "  + rsTS.getString("NGANSACH") + " WHERE TTCP_FK = " + rsTS.getString("TTCPID") + " AND " +
								"THANG = '" + rsTS.getString("THANG") + "' AND NAM = '" + rsTS.getString("NAM") + "' AND LAPNGANSACH_FK = "  + this.lnsId ;
						
						this.db.update(query);
					}
				}
				
				rsTS.close();
			}
			catch(Exception e){}
			
		}
		
		
		
		//INIT CHI PHI
		query = "SELECT LNS.PK_SEQ AS LNSID, LNS.NAM, NCP.TTCHIPHI_FK, SUM(ISNULL(NS_CP.T1, 0), 0) AS T1, SUM(ISNULL(NS_CP.T2, 0), 0) AS T2, SUM(ISNULL(NS_CP.T3, 0), 0) AS T3, " +
				"SUM(ISNULL(NS_CP.T4, 0), 0) AS T4, SUM(ISNULL(NS_CP.T5, 0), 0) AS T5, SUM(ISNULL(NS_CP.T6, 0), 0) AS T6, SUM(ISNULL(NS_CP.T7, 0), 0) AS T7, " +
				"SUM(ISNULL(NS_CP.T8, 0), 0) AS T8, SUM(ISNULL(NS_CP.T9, 0), 0) AS T9, SUM(ISNULL(NS_CP.T10, 0),0) AS T10, SUM(ISNULL(NS_CP.T11, 0), 0) AS T11, SUM(ISNULL(NS_CP.T12, 0), 0) AS T12 " +
				"FROM ERP_LAPNGANSACH_CHIPHI NS_CP " +
				"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = NS_CP.LAPNGANSACH_FK " +
				"INNER JOIN ERP_NHOMCHIPHI NCP ON NCP.PK_SEQ = NS_CP.CHIPHI_FK " +
				"WHERE  NS_CP.LAPNGANSACH_FK = '" + this.lnsId + "' AND LNS.CONGTY_FK = '" + this.ctyId + "'  " +
				"GROUP BY LNS.PK_SEQ, LNS.NAM, NCP.TTCHIPHI_FK, ";

		System.out.println("2___INIT CHI PHI; " + query);
		ResultSet rsCP = this.db.get(query);
		if(rsCP != null)
		{
			try
			{
				while(rsCP.next())  //BI SAI TRONG TRUONG HOP CHAY KHAU HAO TAI SAN TRUOC, CO TTCP_FK CHI CHO TRUNG NHAU
				{
					for(int i = 1; i <= 12; i++)
					{
						String thang = "T" + i;
						
						query = "INSERT INTO ERP_TRUNGTAMCHIPHI_NGANSACH(TTCP_FK, THANG, NAM, NGANSACH, LAPNGANSACH_FK) " +
								"VALUES(" + rsCP.getString("TTCHIPHI_FK") + ", " + i + ", " + rsCP.getString("NAM") + ", " + rsCP.getString(thang) + ", " + rsCP.getString("LNSID") + ")";
						
						if(!db.update(query))
						{
							query = "UPDATE ERP_TRUNGTAMCHIPHI_NGANSACH SET NGANSACH = " + rsCP.getString(thang) + " WHERE NAM = " + rsCP.getString("NAM") + " " +
									"AND THANG = '" + i + "' AND LAPNGANSACH_FK = " + rsCP.getString("LNSID") + " AND TTCP_FK = " +  rsCP.getString("TTCHIPHI_FK") + " ";
							
							this.db.update(query);
						}
					}
				}
				rsCP.close();
			}
			catch (Exception e) {}
		}
		
		
		//INIT CONG CU DUNG CU
		query = "select TTCP_FK as TTCPID, THANG, NAM, sum(NGANSACH) as NganSach  " +
				"from ERP_TRUNGTAMCHIPHI_LNS_CCDC where LAPNGANSACH_FK = '" + this.lnsId + "' " +
				"group by TTCP_FK, THANG, NAM " ;

		System.out.println("3___INIT CCDC: " + query);
		ResultSet rsCCDC = this.db.get(query);
		
		if(rsCCDC != null)
		{
			try
			{
				while(rsCCDC.next())
				{
					query = "INSERT INTO ERP_TRUNGTAMCHIPHI_NGANSACH(TTCP_FK, THANG, NAM, NGANSACH, LAPNGANSACH_FK) " +
							"VALUES(" + rsCCDC.getString("TTCPID") + ", " + rsCCDC.getString("THANG") + ", " + rsCCDC.getString("NAM") + ", " + rsCCDC.getString("NGANSACH") + ", " + this.lnsId + ")";
					
					if(!this.db.update(query))
					{
						query = "UPDATE ERP_TRUNGTAMCHIPHI_NGANSACH SET NGANSACH = "  + rsCCDC.getString("NGANSACH") + " WHERE TTCP_FK = " + rsCCDC.getString("TTCPID") + " AND " +
								"THANG = '" + rsCCDC.getString("THANG") + "' AND NAM = '" + rsCCDC.getString("NAM") + "' AND LAPNGANSACH_FK = "  + this.lnsId ;
						
						this.db.update(query);
					}
				}
				
				rsCCDC.close();
			}
			catch(Exception e){}
			
		}
		
		
		//CHEN NHUNG TTCP DO BI LOI KHONG CHEN DUOC
		for(int i = 1; i<= 12; i++)
		{
			query = "insert ERP_TRUNGTAMCHIPHI_NGANSACH(TTCP_FK, THANG, NAM, NGANSACH, LAPNGANSACH_FK) " +
					"select PK_SEQ, " + i + ", " + this.nam + ", 0, '" + this.lnsId + "'   " +
					"from ERP_TRUNGTAMCHIPHI where PK_SEQ not in ( select TTCP_FK from ERP_TRUNGTAMCHIPHI_NGANSACH where LAPNGANSACH_FK = '" + this.lnsId + "' and thang = '" + i + "' )";
			
			db.update(query);
		}
		
		
		//CHAY LAI PHAN BO NGAN SACH TU CAC TTCP GOC
		try 
		{
			query = "select TTCP_FK, THANG, NAM, NGANSACH " +
					"from ERP_TRUNGTAMCHIPHI_NGANSACH " +
					"where TTCP_FK in ( SELECT DISTINCT TTCP_FK AS TTCPID  " +
					"FROM ERP_TRUNGTAMCHIPHI_NGANSACH TTCP_NS  " +
						"WHERE TTCP_FK IN ( SELECT TTCPCHO_FK FROM ERP_TRUNGTAMCHIPHI_PHANBO )  " +
						"AND TTCP_FK NOT IN ( SELECT TTCPNHAN_FK FROM ERP_TRUNGTAMCHIPHI_PHANBO where TTCPCHO_FK in ( select pk_seq from ERP_TRUNGTAMCHIPHI where trangthai = '1' ) )   " +
						"AND LAPNGANSACH_FK =  " + this.lnsId + " )  ";
			
			System.out.println("4____ LAY NHUNG TTCP CHO, KO NHAN: " + query);
			ResultSet rs = db.get(query);
			
			String thang = "";
			while(rs.next())
			{
				if(thang.trim().length() <= 0)
					thang = rs.getString("THANG");
				
				if(!thang.equals(rs.getString("THANG")))
				{
					thang = rs.getString("thang");
					nhan_form_choSelected.clear();  //CHO NHAN LUU VET THEO TUNG THANG, VI MOI THANG PHAN BO LAI KHAC NHAU
				}
				
				UpdatePhanBo(rs.getString("TTCP_FK"), rs.getDouble("ngansach"),rs.getString("THANG"), rs.getString("NAM"));
			}
			rs.close();
		} 
		catch (Exception e) 
		{
			System.out.println("____   EXCEPTION TAI DAY " + e.getMessage());
			nhan_form_choSelected.clear();
			nhan_form_choSelected.clone();
		}
		
		nhan_form_choSelected.clear();
		nhan_form_choSelected.clone();
	}
	
	
	
	//THU THAY DOI HAM PHAN BO CHI PHI - TRUONG HOP TAI SAN
	private boolean UpdatePhanBo(String ttchiphi_fk, double tonggiatri, String thang, String nam) 
	{
		
		String query =  " select TTCPNHAN_FK, PHANTRAM,  case PHANTRAM when 1002 then " + tonggiatri + " * ( " +
						" (	SELECT SUM(ISNULL(LNS_DBSP.DUKIENBAN,0) * ISNULL(BG_SP.GIABAN,0)) AS NUM " +
						" 	FROM ERP_LAPNGANSACH_DUBAO LNS_DB " +
						" 	INNER JOIN ERP_LAPNGANSACH_DUBAOSANPHAM LNS_DBSP ON LNS_DBSP.DUBAO_FK = LNS_DB.PK_SEQ  " +
						" 	INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = LNS_DBSP.SANPHAM_FK AND SP.DVKD_FK = LNS_DB.DVKD_FK  " +
						" 	LEFT JOIN ERP_LNSBANGGIABAN_KH BG_KH ON BG_KH.KH_FK =  LNS_DBSP.KHACHHANG_FK  " +
						" 	LEFT JOIN ERP_LNSBANGGIABAN BG ON BG.PK_SEQ = BG_KH.BANGGIABAN_FK  " +
						" 	LEFT JOIN ERP_LNSBGBAN_SANPHAM BG_SP ON BG_SP.BGBAN_FK = BG.PK_SEQ AND BG_SP.SANPHAM_FK = LNS_DBSP.SANPHAM_FK AND BG_SP.TRANGTHAI = 1  " +
						" 	WHERE	LNS_DB.LAPNGANSACH_FK = '" + this.lnsId + "' AND LNS_DB.CONGTY_FK = '" + this.ctyId + "' AND SP.LOAISANPHAM_FK = '100005' AND LNS_DBSP.THANG = '" + thang + "' and LNS_DBSP.NAM = '" + nam + "' AND SP.DVKD_FK = '100000' " +
						" )  " +
						" /  " +
						" (	 " +
						"	SELECT SUM(ISNULL(LNS_DBSP.DUKIENBAN,0) * ISNULL(BG_SP.GIABAN,0)) AS NUM  " +
						"	FROM ERP_LAPNGANSACH_DUBAO LNS_DB  " +
						"	INNER JOIN ERP_LAPNGANSACH_DUBAOSANPHAM LNS_DBSP ON LNS_DBSP.DUBAO_FK = LNS_DB.PK_SEQ  " +
						"	INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = LNS_DBSP.SANPHAM_FK AND SP.DVKD_FK = LNS_DB.DVKD_FK  " +
						"	LEFT JOIN ERP_LNSBANGGIABAN_KH BG_KH ON BG_KH.KH_FK =  LNS_DBSP.KHACHHANG_FK  " +
						"	LEFT JOIN ERP_LNSBANGGIABAN BG ON BG.PK_SEQ = BG_KH.BANGGIABAN_FK  " +
						"	LEFT JOIN ERP_LNSBGBAN_SANPHAM BG_SP ON BG_SP.BGBAN_FK = BG.PK_SEQ AND BG_SP.SANPHAM_FK = LNS_DBSP.SANPHAM_FK AND BG_SP.TRANGTHAI = 1  " +
						"	WHERE	LNS_DB.LAPNGANSACH_FK = '" + this.lnsId + "' AND LNS_DB.CONGTY_FK = '" + this.ctyId + "' AND SP.LOAISANPHAM_FK = '100005' AND LNS_DBSP.THANG = '" + thang + "' and LNS_DBSP.NAM = '" + nam + "' " +
 						" ) )  " +
						"	" +
						"	when 1003 then " + tonggiatri + " * ( " +
						" (	SELECT SUM(ISNULL(LNS_DBSP.DUKIENBAN,0) * ISNULL(BG_SP.GIABAN,0)) AS NUM  " +
						"	FROM ERP_LAPNGANSACH_DUBAO LNS_DB  " +
						"	INNER JOIN ERP_LAPNGANSACH_DUBAOSANPHAM LNS_DBSP ON LNS_DBSP.DUBAO_FK = LNS_DB.PK_SEQ  " +
						"	INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = LNS_DBSP.SANPHAM_FK AND SP.DVKD_FK = LNS_DB.DVKD_FK " +
						"	LEFT JOIN ERP_LNSBANGGIABAN_KH BG_KH ON BG_KH.KH_FK =  LNS_DBSP.KHACHHANG_FK  " +
						"	LEFT JOIN ERP_LNSBANGGIABAN BG ON BG.PK_SEQ = BG_KH.BANGGIABAN_FK  " +
						"	LEFT JOIN ERP_LNSBGBAN_SANPHAM BG_SP ON BG_SP.BGBAN_FK = BG.PK_SEQ AND BG_SP.SANPHAM_FK = LNS_DBSP.SANPHAM_FK AND BG_SP.TRANGTHAI = 1 " +
						"	WHERE	LNS_DB.LAPNGANSACH_FK = '" + this.lnsId + "' AND LNS_DB.CONGTY_FK = '" + this.ctyId + "' AND SP.LOAISANPHAM_FK = '100005' AND LNS_DBSP.THANG = '" + thang + "' and LNS_DBSP.NAM = '" + nam + "' AND SP.DVKD_FK = '100004' " +
						" )   " +
						" /  " +
						" (	 SELECT SUM(ISNULL(LNS_DBSP.DUKIENBAN,0) * ISNULL(BG_SP.GIABAN,0)) AS NUM  " +
						"    FROM ERP_LAPNGANSACH_DUBAO LNS_DB  " +
						"	 INNER JOIN ERP_LAPNGANSACH_DUBAOSANPHAM LNS_DBSP ON LNS_DBSP.DUBAO_FK = LNS_DB.PK_SEQ  " +
						"	 INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = LNS_DBSP.SANPHAM_FK AND SP.DVKD_FK = LNS_DB.DVKD_FK  " +
						"	 LEFT JOIN ERP_LNSBANGGIABAN_KH BG_KH ON BG_KH.KH_FK =  LNS_DBSP.KHACHHANG_FK  " +
						"	 LEFT JOIN ERP_LNSBANGGIABAN BG ON BG.PK_SEQ = BG_KH.BANGGIABAN_FK  " +
						"	 LEFT JOIN ERP_LNSBGBAN_SANPHAM BG_SP ON BG_SP.BGBAN_FK = BG.PK_SEQ AND BG_SP.SANPHAM_FK = LNS_DBSP.SANPHAM_FK AND BG_SP.TRANGTHAI = 1 " +
						"	 WHERE	LNS_DB.LAPNGANSACH_FK = '" + this.lnsId + "' AND LNS_DB.CONGTY_FK = '" + this.ctyId + "' AND SP.LOAISANPHAM_FK = '100005' AND LNS_DBSP.THANG = '" + thang + "' and LNS_DBSP.NAM = '" + nam + "' " +
 						" ) )  " +
						"  else PHANTRAM * " + tonggiatri + " / 100 end as PhanBo    " +
						" from ERP_TRUNGTAMCHIPHI_PHANBO  " +
						" where TTCPCHO_FK = '" + ttchiphi_fk + "'  ";
		
		ResultSet rsPhanbo = db.get(query);
		if(rsPhanbo != null)
		{
			try
			{
				while(rsPhanbo.next())
				{
					String ttcpNhan_fk = rsPhanbo.getString("TTCPNHAN_FK");
					long tongphanbo = Math.round(rsPhanbo.getDouble("PhanBo"));
					
					if(tongphanbo > 0)
					{
						Hashtable<String, Long> ttcp_from_dacho = null;
						
						if(nhan_form_choSelected.get(ttcpNhan_fk) == null )
							ttcp_from_dacho = new Hashtable<String, Long>();
						else
							ttcp_from_dacho = nhan_form_choSelected.get(ttcpNhan_fk);
						
						nhan_form_choSelected.put(ttcpNhan_fk, ttcp_from_dacho);
						
						long giatriNhanDuoc = tongphanbo  - ( ttcp_from_dacho.get(ttcpNhan_fk) == null ? 0 : ttcp_from_dacho.get(ttcpNhan_fk) ) ;
						
						query = "update ERP_TRUNGTAMCHIPHI_NGANSACH set NGANSACH = NGANSACH +  " + giatriNhanDuoc + "  " +
								" where TTCP_FK = '" + ttcpNhan_fk + "' and thang = '" + thang + "' and nam = '" + nam + "' and lapngansach_fk = '" + this.lnsId + "' ";
						
						/*if(ttcpNhan_fk.equals("100562"))
						{
							System.out.println("____TTCP cho no: " + ttchiphi_fk );
							System.out.println("____Cap nhat TTCP: " + query );
						}*/
						
						ttcp_from_dacho.put( ttchiphi_fk, ( ttcp_from_dacho.get(ttchiphi_fk) == null ? 0 : ttcp_from_dacho.get(ttchiphi_fk) ) + Math.round(tongphanbo) );
						
						if(!db.update(query))
						{
							rsPhanbo.close();
							
							System.out.println("1.Loi: " + query);
							return false;
						}

						
						//Tu trung tam nhan nay, xet truong hop trung tam nhan co trung tam dc phan bo
						//////////Phai them cho kiem tra phan bo, ----> coi chung lap vo han
						if(!UpdatePhanBo(ttcpNhan_fk, giatriNhanDuoc, thang, nam))
						{
							rsPhanbo.close();
							
							System.out.println("2222.Khong the cap nhat ngan sach, vui long kiem tra lai: " + query);
							return false;
						}
						
					}
					
				}
				rsPhanbo.close();
			}
			catch (Exception e) 
			{
				System.out.println("9.Exception phan bo: " + e.getMessage());
				return false;
			}
		}
		
		return true;
	}
	
	
	//CHI PHI
	private void CapnhatPhanboChiphi_ChiPhi()
	{
		nhan_form_choSelected = new Hashtable< String, Hashtable<String,Long> >();
		
		String query = "";
		
		//Neu la truong hop cap nhat lai, thi phai tra lai luong ngan sach cu truoc khi update
		/*query = "update a  " +
				"set a.ngansach = a.NGANSACH - b.NganSach " +
				"from ERP_TRUNGTAMCHIPHI_NGANSACH a inner join ERP_TRUNGTAMCHIPHI_NGANSACH_TEMP_CHIPHI b on a.LAPNGANSACH_FK = b.LAPNGANSACH_FK " +
					" and a.LAPNGANSACH_FK = b.LAPNGANSACH_FK and a.NAM = b.NAM and a.THANG = b.THANG and a.TTCP_FK = b.TTCP_FK  " +
				"where a.LAPNGANSACH_FK = '" + this.lnsId + "' ";
		System.out.println("____CAP NHAT LAI CP: " + query);
		db.update(query);
		*/
		
		//delete BANG TAM (CHI CUA CP TRONG TRUONG HOP NAY)
		/*query = "delete ERP_TRUNGTAMCHIPHI_NGANSACH_TEMP_CHIPHI where lapngansach_fk = '" + this.lnsId + "'";
		db.update(query);*/
		
		query = "update a  " +
				"set a.ngansach = a.NGANSACH - b.NganSach " +
				"from ERP_TRUNGTAMCHIPHI_NGANSACH a inner join ERP_TRUNGTAMCHIPHI_NGANSACH_TEMP b on a.LAPNGANSACH_FK = b.LAPNGANSACH_FK " +
					" and a.LAPNGANSACH_FK = b.LAPNGANSACH_FK and a.NAM = b.NAM and a.THANG = b.THANG and a.TTCP_FK = b.TTCP_FK  " +
				"where a.LAPNGANSACH_FK = '" + this.lnsId + "' ";
		System.out.println("____CAP NHAT LAI TS: " + query);
		db.update(query);
		

		try 
		{
			query = "select TTCP_FK, THANG, NAM, NGANSACH from ERP_TRUNGTAMCHIPHI_NGANSACH " +
					"where TTCP_FK in ( SELECT DISTINCT TTCP_FK AS TTCPID  " +
					"FROM ERP_TRUNGTAMCHIPHI_NGANSACH TTCP_NS  " +
						"WHERE TTCP_FK IN ( SELECT TTCPCHO_FK FROM ERP_TRUNGTAMCHIPHI_PHANBO )  " +
						"AND TTCP_FK NOT IN ( SELECT TTCPNHAN_FK FROM ERP_TRUNGTAMCHIPHI_PHANBO where TTCPCHO_FK in ( select pk_seq from ERP_TRUNGTAMCHIPHI where trangthai = '1' ) )   " +
						"AND LAPNGANSACH_FK =  " + this.lnsId + " )  ";
			
			System.out.println("____      LAY NHUNG TTCP KO CHO, KO NHAN: " + query);
			ResultSet rs = db.get(query);
			
			while(rs.next())
			{
				UpdatePhanBo_CP(rs.getString("TTCP_FK"), rs.getDouble("ngansach"),rs.getString("THANG"), rs.getString("NAM"));
			}
			rs.close();
		} 
		catch (Exception e) 
		{
			System.out.println("____   EXCEPTION TAI DAY " + e.getMessage());
			nhan_form_choSelected.clear();
			nhan_form_choSelected.clone();
		}
		
		nhan_form_choSelected.clear();
		nhan_form_choSelected.clone();
	}
	
	
	private boolean UpdatePhanBo_CP(String ttchiphi_fk, double tonggiatri, String thang, String nam) 
	{
		String query =  "select TTCPNHAN_FK, PHANTRAM * ( " + tonggiatri + " ) / 100 as PhanBo from ERP_TRUNGTAMCHIPHI_PHANBO " +
						"where TTCPCHO_FK = '" + ttchiphi_fk + "' ";

		ResultSet rsPhanbo = db.get(query);
		if(rsPhanbo != null)
		{
			try
			{
				while(rsPhanbo.next())
				{
					String ttcpNhan_fk = rsPhanbo.getString("TTCPNHAN_FK");
					long tongphanbo = Math.round(rsPhanbo.getDouble("PhanBo"));
					
					if(tongphanbo > 0)
					{
						Hashtable<String, Long> ttcp_from_dacho = null;
						
						if(nhan_form_choSelected.get(ttcpNhan_fk) == null )
							ttcp_from_dacho = new Hashtable<String, Long>();
						else
							ttcp_from_dacho = nhan_form_choSelected.get(ttcpNhan_fk);
						
						nhan_form_choSelected.put(ttcpNhan_fk, ttcp_from_dacho);
						
						long giatriNhanDuoc = tongphanbo  - ( ttcp_from_dacho.get(ttcpNhan_fk) == null ? 0 : ttcp_from_dacho.get(ttcpNhan_fk) ) ;
						
						query = "update ERP_TRUNGTAMCHIPHI_NGANSACH set NGANSACH = NGANSACH +  " + giatriNhanDuoc + "  " +
								" where TTCP_FK = '" + ttcpNhan_fk + "' and thang = '" + thang + "' and nam = '" + nam + "' and lapngansach_fk = '" + this.lnsId + "' ";
						
						if(ttcpNhan_fk.equals("100623") || ttcpNhan_fk.equals("100624") || ttcpNhan_fk.equals("100625") )
						{
							System.out.println("____TTCP cho no: " + ttchiphi_fk );
							System.out.println("____Cap nhat TTCP: " + query );
						}
						
						ttcp_from_dacho.put( ttchiphi_fk, ( ttcp_from_dacho.get(ttchiphi_fk) == null ? 0 : ttcp_from_dacho.get(ttchiphi_fk) ) + Math.round(tongphanbo) );
						
						if(!db.update(query))
						{
							rsPhanbo.close();
							
							System.out.println("1.Loi: " + query);
							return false;
						}

						
						query = "insert ERP_TRUNGTAMCHIPHI_NGANSACH_TEMP_CHIPHI(TTCP_FK, THANG, NAM, NGANSACH, LAPNGANSACH_FK) " +
								"values('" + ttcpNhan_fk + "', '" + thang + "', '" + nam + "', '" + giatriNhanDuoc + "', '" + this.lnsId + "')  ";
						if(!db.update(query))
						{
							query = "update ERP_TRUNGTAMCHIPHI_NGANSACH_TEMP_CHIPHI set NGANSACH = NGANSACH +  " + giatriNhanDuoc + "  " +
									" where TTCP_FK = '" + ttcpNhan_fk + "' and thang = '" + thang + "' and nam = '" + nam + "' and lapngansach_fk = '" + this.lnsId + "' ";
							db.update(query);
						}
						
						//Tu trung tam nhan nay, xet truong hop trung tam nhan co trung tam dc phan bo
						//////////Phai them cho kiem tra phan bo, ----> coi chung lap vo han
						if(!UpdatePhanBo_CP(ttcpNhan_fk, giatriNhanDuoc, thang, nam))
						{
							rsPhanbo.close();
							
							System.out.println("2222.Khong the cap nhat ngan sach, vui long kiem tra lai: " + query);
							return false;
						}
						ttcp_from_dacho.clear();
					}
				}
				rsPhanbo.close();
			}
			catch (Exception e) 
			{
				System.out.println("9.Exception phan bo: " + e.getMessage());
				return false;
			}
		}
		
		return true;
	}
	

	private void CapnhatPhanboDoanhthu(){

		String query = 	"SELECT PB.TTDTNHAN_FK, NS.NAM, NS.THANG, SUM(NS.NGANSACH*PB.PHANTRAM/100) AS PHANBO " +
						"FROM ERP_TRUNGTAMDOANHTHU_PHANBO PB " +
						"INNER JOIN ERP_TRUNGTAMDOANHTHU_NGANSACH NS ON NS.TTDT_FK = PB.TTDTCHO_FK " +
						"WHERE PB.TTDTCHO_FK IN( " +
						"	SELECT DISTINCT TTDT_FK AS TTDTID " +
						"	FROM ERP_TRUNGTAMDOANHTHU_NGANSACH TTDT_NS " +
						"	WHERE TTDT_FK IN (SELECT TTDTCHO_FK FROM ERP_TRUNGTAMDOANHTHU_PHANBO) " +
						"	AND TTDT_FK NOT IN (SELECT TTDTNHAN_FK FROM ERP_TRUNGTAMDOANHTHU_PHANBO) " +
						"	AND LAPNGANSACH_FK = " + this.lnsId + " " +
						") GROUP BY PB.TTDTNHAN_FK, NS.NAM, NS.THANG ";

		System.out.println(query);
		ResultSet rs = this.db.get(query);
		String ttdtnhanIds = "";
		String tmp = "";
		if(rs != null){
			try{
				while(rs.next()){
					ttdtnhanIds = ttdtnhanIds + rs.getString("TTDTNHAN_FK") + ",";
					System.out.println(ttdtnhanIds);
					
					query = "UPDATE ERP_TRUNGTAMDOANHTHU_NGANSACH SET NGANSACH = " + Math.round(Double.parseDouble(rs.getString("PHANBO"))) + " " +
							"WHERE TTDT_FK = " + rs.getString("TTDTNHAN_FK") + " AND THANG = " + rs.getString("THANG") + " " +
							"AND NAM = " + rs.getString("NAM") + " AND LAPNGANSACH_FK = " + this.lnsId + "";
					
					System.out.println(query);
					this.db.update(query);
				}
				rs.close();
				
				if(ttdtnhanIds.length() > 0){
					tmp = tmp + ttdtnhanIds;
					ttdtnhanIds = ttdtnhanIds.substring(0, ttdtnhanIds.length()-1);
				
					this.Phanbotiepdoanhthu(ttdtnhanIds, tmp);
				}
				
			}catch(java.sql.SQLException e){}
		}
	}

	private void Phanbotiepdoanhthu(String ttdtnhanIds, String list){
		String tmp = list;
		String query = 	"SELECT PB.TTDTNHAN_FK, NS.NAM, NS.THANG, SUM(NS.NGANSACH*PB.PHANTRAM/100) AS PHANBO " +
						"FROM ERP_TRUNGTAMDOANHTHU_PHANBO PB " +
						"INNER JOIN ERP_TRUNGTAMDOANHTHU_NGANSACH NS ON NS.TTDT_FK = PB.TTDTCHO_FK " +
						"WHERE PB.TTDTCHO_FK IN( " +
						"	SELECT DISTINCT TTDT_FK AS TTDTID " +
						"	FROM ERP_TRUNGTAMDOANHTHU_NGANSACH TTDT_NS " +
						"	WHERE TTDT_FK IN (SELECT TTDTCHO_FK FROM ERP_TRUNGTAMDOANHTHU_PHANBO) " +
						"	AND TTDT_FK IN (SELECT TTDTNHAN_FK FROM ERP_TRUNGTAMDOANHTHU_PHANBO) " +
						"	AND LAPNGANSACH_FK = " + this.lnsId + " " +
						") AND PB.TTDTCHO_FK IN (" + ttdtnhanIds + ") " +
						"GROUP BY PB.TTDTNHAN_FK, NS.NAM, NS.THANG ";
		
		//System.out.println(query);
		ResultSet rs = this.db.get(query);
		String ttdtIds = "";
		if(rs != null){
			try{
				while(rs.next()){
					ttdtIds = ttdtIds + rs.getString("TTDTNHAN_FK") + ",";
					System.out.println(ttdtIds);
					
					if(tmp.indexOf(rs.getString("TTDTNHAN_FK")) > 0){
						query = "UPDATE ERP_TRUNGTAMDOANHTHU_NGANSACH SET NGANSACH = NGANSACH + " + Math.round(Double.parseDouble(rs.getString("PHANBO"))) + " " +
								"WHERE TTDT_FK = " + rs.getString("TTDTNHAN_FK") + " AND THANG = " + rs.getString("THANG") + " " +
								"AND NAM = " + rs.getString("NAM") + " AND LAPNGANSACH_FK = " + this.lnsId + "";						
					}else{
						query = "UPDATE ERP_TRUNGTAMDOANHTHU_NGANSACH SET NGANSACH = " + Math.round(Double.parseDouble(rs.getString("PHANBO"))) + " " +
								"WHERE TTDT_FK = " + rs.getString("TTDTNHAN_FK") + " AND THANG = " + rs.getString("THANG") + " " +
								"AND NAM = " + rs.getString("NAM") + " AND LAPNGANSACH_FK = " + this.lnsId + "";
					}
					
					//System.out.println(query);
					this.db.update(query);
				}
				rs.close();
				
				if(ttdtIds.length() > 0) {
					tmp = tmp + ttdtIds;
					
					ttdtIds = ttdtIds.substring(0, ttdtIds.length()-1);
					
					this.Phanbotiepdoanhthu(ttdtIds, tmp);
				}
				
			}catch(java.sql.SQLException e){}
		}
			
	}
	
	public void save_CP(HttpServletRequest request)throws ServletException, IOException
	{
		String query;
		Utility util = new Utility();
		String cpList = "";
		
		
		if(this.cpIds != null)
		{
			for(int i = 0; i < this.cpIds.length; i++)
			{
				double giatri = Double.parseDouble(util.antiSQLInspection(request.getParameter("giatri" + this.cpIds[i])).replace(",", ""));
				float phantram = Float.parseFloat(util.antiSQLInspection(request.getParameter("phantram" + cpIds[i])).replace(",", ""));
				
				query = "UPDATE ERP_LAPNGANSACH_CHIPHI SET " +
						"DUTOAN = " + giatri + ", " +
						"NGAYSUA = '" + this.getDateTime() + "', " +
						"NGUOISUA = '" + this.userId + "', " +
						"PHANTRAM = " + phantram + " " ;
				
				for(int j = 1; j <= 11; j++){
					query = query +	", T" + j + " = " + giatri/12 + " " ;
				}
			
				query += ", T12 = " + (giatri - 11*giatri/12) + " ";
				
				query = query + " WHERE LAPNGANSACH_FK = '" + this.lnsId + "' AND " +
								" DONVITHUCHIEN_FK = '" + this.bpId + "' AND " +
								" CHIPHI_FK = '" + this.cpIds[i] + "' ";
			
				//System.out.println("____CAP NHAT ERP_LAPNGANSACH_CHIPHI: " + query);
				this.db.update(query);
				
				cpList = cpList + this.cpIds[i] + ",";
			}
			
			cpList = cpList.substring(0, cpList.length() - 1);
			
		//	this.CapnhatPhanboChiphi_TaiSan();
				
		}
	}
	
	public void save_DT(HttpServletRequest request)throws ServletException, IOException{
		String query;
		Utility util = new Utility();
		String dtList = "";
		if(this.dtIds != null){
			for(int i = 0; i < this.dtIds.length; i++){
				query = "UPDATE ERP_LAPNGANSACH_DOANHTHU SET " +
						"DUTOAN = '" + util.antiSQLInspection(request.getParameter("nammoi" + this.dtIds[i])).replace(",", "") + "', " +
						"NGAYSUA = '" + this.getDateTime() + "', " +
						"NGUOISUA = '" + this.userId + "', " +
						"PHANBO = '" + util.antiSQLInspection(request.getParameter("phanbo" + dtIds[i])).replace(",", "") + "' " ;
				
				for(int j = 1; j <= 12; j++){
					query = query +	",T" + j + " = '" + util.antiSQLInspection(request.getParameter("T" + this.dtIds[i] + j)).replace(",", "") + "' " ;
				}
			
				query = query + " WHERE LAPNGANSACH_FK = '" + this.lnsId + "' AND " +
								" DONVITHUCHIEN_FK = '" + this.bpId + "' AND " +
	//							" THTC_FK = '" + this.ndtId + "' AND " +
								" DOANHTHU_FK = '" + this.dtIds[i] + "' ";
			
				//System.out.println(query);
				this.db.update(query);
				
				dtList = dtList + this.dtIds[i] + ",";
			}
			
			dtList = dtList.substring(0, dtList.length() - 1);
			
			query = "SELECT LNS.PK_SEQ AS LNSID, LNS.NAM, DT.TTDOANHTHU_FK, NS_DT.DOANHTHU_FK, ISNULL(NS_DT.T1, 0) AS T1, ISNULL(NS_DT.T2, 0) AS T2, ISNULL(NS_DT.T3, 0) AS T3, " +
					"ISNULL(NS_DT.T4, 0) AS T4, ISNULL(NS_DT.T5, 0) AS T5, ISNULL(NS_DT.T6, 0) AS T6, ISNULL(NS_DT.T7, 0) AS T7, " +
					"ISNULL(NS_DT.T8, 0) AS T8, ISNULL(NS_DT.T9, 0) AS T9, ISNULL(NS_DT.T10, 0) AS T10, ISNULL(NS_DT.T11, 0) AS T11, ISNULL(NS_DT.T12, 0) AS T12 " +
					"FROM ERP_LAPNGANSACH_DOANHTHU NS_DT " +
					"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = NS_DT.LAPNGANSACH_FK " +
					"INNER JOIN ERP_DOANHTHU DT ON DT.PK_SEQ = NS_DT.DOANHTHU_FK " +
					"WHERE DT.PK_SEQ IN (" + dtList + ") AND LNS.CONGTY_FK = " + this.ctyId + "" ;
			
			System.out.println(query);
			ResultSet rs = this.db.get(query);
			if(rs != null)
			{
				try{
					while(rs.next())
					{
						query = "UPDATE ERP_TRUNGTAMDOANHTHU_NGANSACH SET NGANSACH = " + rs.getString("T1") + " WHERE NAM = " + rs.getString("NAM") + " " +
								"AND THANG = '1' AND LAPNGANSACH_FK = " + rs.getString("LNSID") + " AND TTDT_FK = " +  rs.getString("TTDOANHTHU_FK") + " ";
			
						System.out.println(query);
						this.db.update(query);
						
						query = "UPDATE ERP_TRUNGTAMDOANHTHU_NGANSACH SET NGANSACH = " + rs.getString("T2") + " WHERE NAM = " + rs.getString("NAM") + " " +
								"AND THANG = '2' AND LAPNGANSACH_FK = " + rs.getString("LNSID") + " AND TTDT_FK = " +  rs.getString("TTDOANHTHU_FK") + " ";
												
						this.db.update(query);

						query = "UPDATE ERP_TRUNGTAMDOANHTHU_NGANSACH SET NGANSACH = " + rs.getString("T3") + " WHERE NAM = " + rs.getString("NAM") + " " +
								"AND THANG = '3' AND LAPNGANSACH_FK = " + rs.getString("LNSID") + " AND TTDT_FK = " +  rs.getString("TTDOANHTHU_FK") + " ";
						
						this.db.update(query);

						query = "UPDATE ERP_TRUNGTAMDOANHTHU_NGANSACH SET NGANSACH = " + rs.getString("T4") + " WHERE NAM = " + rs.getString("NAM") + " " +
								"AND THANG = '4' AND LAPNGANSACH_FK = " + rs.getString("LNSID") + " AND TTDT_FK = " +  rs.getString("TTDOANHTHU_FK") + " ";
						
						this.db.update(query);

						query = "UPDATE ERP_TRUNGTAMDOANHTHU_NGANSACH SET NGANSACH = " + rs.getString("T5") + " WHERE NAM = " + rs.getString("NAM") + " " +
								"AND THANG = '5' AND LAPNGANSACH_FK = " + rs.getString("LNSID") + " AND TTDT_FK = " +  rs.getString("TTDOANHTHU_FK") + " ";
						
						this.db.update(query);

						query = "UPDATE ERP_TRUNGTAMDOANHTHU_NGANSACH SET NGANSACH = " + rs.getString("T6") + " WHERE NAM = " + rs.getString("NAM") + " " +
								"AND THANG = '6' AND LAPNGANSACH_FK = " + rs.getString("LNSID") + " AND TTDT_FK = " +  rs.getString("TTDOANHTHU_FK") + " ";
						
						this.db.update(query);

						query = "UPDATE ERP_TRUNGTAMDOANHTHU_NGANSACH SET NGANSACH = " + rs.getString("T7") + " WHERE NAM = " + rs.getString("NAM") + " " +
								"AND THANG = '7' AND LAPNGANSACH_FK = " + rs.getString("LNSID") + " AND TTDT_FK = " +  rs.getString("TTDOANHTHU_FK") + " ";
						
						this.db.update(query);

						query = "UPDATE ERP_TRUNGTAMDOANHTHU_NGANSACH SET NGANSACH = " + rs.getString("T8") + " WHERE NAM = " + rs.getString("NAM") + " " +
								"AND THANG = '8' AND LAPNGANSACH_FK = " + rs.getString("LNSID") + " AND TTDT_FK = " +  rs.getString("TTDOANHTHU_FK") + " ";
						
						this.db.update(query);

						query = "UPDATE ERP_TRUNGTAMDOANHTHU_NGANSACH SET NGANSACH = " + rs.getString("T9") + " WHERE NAM = " + rs.getString("NAM") + " " +
								"AND THANG = '9' AND LAPNGANSACH_FK = " + rs.getString("LNSID") + " AND TTDT_FK = " +  rs.getString("TTDOANHTHU_FK") + " ";
						
						this.db.update(query);

						query = "UPDATE ERP_TRUNGTAMDOANHTHU_NGANSACH SET NGANSACH = " + rs.getString("T10") + " WHERE NAM = " + rs.getString("NAM") + " " +
								"AND THANG = '10' AND LAPNGANSACH_FK = " + rs.getString("LNSID") + " AND TTDT_FK = " +  rs.getString("TTDOANHTHU_FK") + " ";
						
						this.db.update(query);

						query = "UPDATE ERP_TRUNGTAMDOANHTHU_NGANSACH SET NGANSACH = " + rs.getString("T11") + " WHERE NAM = " + rs.getString("NAM") + " " +
								"AND THANG = '11' AND LAPNGANSACH_FK = " + rs.getString("LNSID") + " AND TTDT_FK = " +  rs.getString("TTDOANHTHU_FK") + " ";
						
						this.db.update(query);

						query = "UPDATE ERP_TRUNGTAMDOANHTHU_NGANSACH SET NGANSACH = " + rs.getString("T12") + " WHERE NAM = " + rs.getString("NAM") + " " +
								"AND THANG = '12' AND LAPNGANSACH_FK = " + rs.getString("LNSID") + " AND TTDT_FK = " +  rs.getString("TTDOANHTHU_FK") + " ";
						
						System.out.println(query);
						this.db.update(query);

					}
					rs.close();
					
					this.CapnhatPhanboDoanhthu();
					
				}
				catch(java.sql.SQLException e){}
			}
		}
	}

	
	public ResultSet Chonphanbo(String tsId, String ntsId){
		if(loai.equals("1")){
			if(!ntsId.equals("0")){
				String query = 	"SELECT TTCP.PK_SEQ AS TTCPID, TTCP.MA, TTCP.DIENGIAI, PB.PHANTRAM \n " +
								"FROM ERP_LAPNGANSACH_TAISAN_PHANBO PB  \n " +
								"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = PB.TTCP_FK  \n " +
								"WHERE PB.LNSTAISAN_FK = '" + tsId + "'  \n " +
								"UNION ALL  \n " +
								"SELECT NTS_PB.TTCP_FK, TTCP.MA, TTCP.DIENGIAI, 0 \n " + 
								"FROM ERP_NHOMTAISAN_PHANBOKHAUHAO NTS_PB \n " +
								"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NTS_PB.TTCP_FK \n " +
								"WHERE NTS_PB.NHOMTAISAN_FK = '" + ntsId + "' AND NTS_PB.TTCP_FK NOT IN ( \n " +
					
								"SELECT PB.TTCP_FK \n " +
								"FROM ERP_LAPNGANSACH_TAISAN_PHANBO PB \n " + 
								"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = PB.TTCP_FK WHERE PB.LNSTAISAN_FK = '" + tsId + "' \n " + 
								")		 \n " ;
				System.out.println("Chọn PB: " + query);
				return this.db.get(query);
			}else{
				return null;
			}
		}else{
			if(!ntsId.equals("0")){
				String query = 	"SELECT TTCP.PK_SEQ AS TTCPID, TTCP.MA, TTCP.DIENGIAI, PB.PHANTRAM \n " +
								"FROM ERP_LAPNGANSACH_CCDC_PHANBO PB  \n " +
								"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = PB.TTCP_FK  \n " +
								"WHERE PB.LNSCCDC_FK = '" + tsId + "'  \n " +
								"UNION ALL  \n " +
								"SELECT NTS_PB.TTCP_FK, TTCP.MA, TTCP.DIENGIAI, 0 \n " + 
								"FROM ERP_NHOMCCDC_PHANBOKHAUHAO NTS_PB \n " +
								"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NTS_PB.TTCP_FK \n " +
								"WHERE NTS_PB.NHOMCCDC_FK = '" + ntsId + "' AND NTS_PB.TTCP_FK NOT IN ( \n " +
					
								"SELECT PB.TTCP_FK \n " +
								"FROM ERP_LAPNGANSACH_CCDC_PHANBO PB \n " + 
								"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = PB.TTCP_FK WHERE PB.LNSCCDC_FK = '" + tsId + "' \n " + 
								")		 \n " ;
				System.out.println("Chọn PB: " + query);
				return this.db.get(query);
			}else{
				return null;
			}
			
		}
	}

	public ResultSet getChonnhomchiphi(){
		String query = 	"SELECT BP.PK_SEQ AS BPID, BP.TEN AS BP, NCP.PK_SEQ AS NCPID, NCP.TEN AS NCP " +
						"FROM ERP_NHOMCHIPHI NCP " +
						"INNER JOIN ERP_DONVITHUCHIEN BP ON BP.PK_SEQ = NCP.DONVITHUCHIEN_FK " +
						"WHERE LOAI = '1' AND NCP.CONGTY_FK = " + this.ctyId + " and bp.pk_seq in " + this.util.quyen_donvithuchien(this.userId);;	
		
		System.out.println(query);
		return this.db.get(query);
	}
	
	public ResultSet getHienthinhomchiphichon(){
		String query = "";
		if(this.chon.length() > 0){
			query = 	"SELECT NCP.PK_SEQ AS NCPID, BP.TEN + ' - ' + NCP.TEN AS NCP, ISNULL(SUM(CP.DUTOAN), 0) AS DUTOANCP, ISNULL(NAMTRUOC.DUTOAN, 0) AS DUTOANCPNAMTRUOC " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK " +
						"INNER JOIN ERP_NHOMCHIPHI NCP ON NCP.PK_SEQ = CP.NHOMCHIPHI_FK " +
						"INNER JOIN ERP_DONVITHUCHIEN BP ON BP.PK_SEQ = NCP.DONVITHUCHIEN_FK " +						
						"LEFT JOIN " +
						"(" +
						"	SELECT NCP.PK_SEQ AS NCPID, ISNULL(SUM(CP.DUTOAN),0) AS DUTOAN " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK " +
						"	INNER JOIN ERP_NHOMCHIPHI NCP ON NCP.PK_SEQ = CP.NHOMCHIPHI_FK " +
						"	INNER JOIN ERP_DONVITHUCHIEN BP ON BP.PK_SEQ = NCP.DONVITHUCHIEN_FK " +
						"	WHERE NCP.PK_SEQ IN(" + this.chon + ") AND LNS.NAM = " + this.namtruoc + " " +	
						"	GROUP BY NCP.PK_SEQ " +	
						")NAMTRUOC ON NAMTRUOC.NCPID = NCP.PK_SEQ " +
						"WHERE NCP.PK_SEQ IN(" + this.chon + ") AND LNS.NAM = " + this.nam + "  and bp.pk_seq in " + this.util.quyen_donvithuchien(this.userId) +
						"GROUP BY NCP.PK_SEQ, BP.TEN, NCP.TEN, NAMTRUOC.DUTOAN";
			System.out.println(query);
			return this.db.get(query);
		}else{
			return null;
		}
	}
	
	public ResultSet getHienthinhomchiphikhac(){
		String query ;
		if(this.chon.length() > 0){
			query	= 	" SELECT ISNULL(SUM(CP.DUTOAN), 0) AS DUTOANCP, ISNULL(NAMTRUOC.DUTOAN, 0) AS DUTOANCPNAMTRUOC " +
						" FROM ERP_LAPNGANSACH_CHIPHI CP " +
						" INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK " +
						" INNER JOIN ERP_NHOMCHIPHI NCP ON NCP.PK_SEQ = CP.NHOMCHIPHI_FK " +
						" INNER JOIN ERP_DONVITHUCHIEN BP ON BP.PK_SEQ = NCP.DONVITHUCHIEN_FK " +						
						" LEFT JOIN " +
						"(" +
						"	SELECT NCP.PK_SEQ AS NCPID, ISNULL(SUM(CP.DUTOAN),0) AS DUTOAN " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK " +
						"	INNER JOIN ERP_NHOMCHIPHI NCP ON NCP.PK_SEQ = CP.NHOMCHIPHI_FK " +
						"	INNER JOIN ERP_DONVITHUCHIEN BP ON BP.PK_SEQ = NCP.DONVITHUCHIEN_FK " +
						"	WHERE NCP.PK_SEQ IN(" + this.chon + ") AND LNS.NAM = " + this.namtruoc + " " +	
						"	GROUP BY NCP.PK_SEQ " +	
						" )NAMTRUOC ON NAMTRUOC.NCPID = NCP.PK_SEQ " +
						" WHERE NCP.PK_SEQ NOT IN (" + this.chon + ") AND LNS.NAM = " + this.nam + "  and bp.pk_seq in " + this.util.quyen_donvithuchien(this.userId)+
						" GROUP BY NAMTRUOC.DUTOAN";
		}else{
			query	= 	"SELECT SUM(DUTOANCP) AS DUTOANCP, SUM(DUTOANCPNAMTRUOC) AS DUTOANCPNAMTRUOC " +
						"FROM " +
						"( " +
						"	SELECT ISNULL(SUM(CP.DUTOAN), 0) AS DUTOANCP, ISNULL(NAMTRUOC.DUTOAN, 0) AS DUTOANCPNAMTRUOC " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK " +
						"	INNER JOIN ERP_NHOMCHIPHI NCP ON NCP.PK_SEQ = CP.NHOMCHIPHI_FK " +
						"	INNER JOIN ERP_DONVITHUCHIEN BP ON BP.PK_SEQ = NCP.DONVITHUCHIEN_FK " +						
						"	LEFT JOIN " +
						"	(" +
						"		SELECT NCP.PK_SEQ AS NCPID, ISNULL(SUM(CP.DUTOAN),0) AS DUTOAN " +
						"		FROM ERP_LAPNGANSACH_CHIPHI CP " +
						"		INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK " +
						"		INNER JOIN ERP_NHOMCHIPHI NCP ON NCP.PK_SEQ = CP.NHOMCHIPHI_FK " +
						"		INNER JOIN ERP_DONVITHUCHIEN BP ON BP.PK_SEQ = NCP.DONVITHUCHIEN_FK " +
						"		WHERE LNS.NAM = " + this.namtruoc + " " +	
						"		GROUP BY NCP.PK_SEQ " +	
						"	)NAMTRUOC ON NAMTRUOC.NCPID = NCP.PK_SEQ " +
						"	WHERE  LNS.NAM = " + this.nam + "  and bp.pk_seq in " + this.util.quyen_donvithuchien(this.userId)+
						"	GROUP BY NAMTRUOC.DUTOAN" +
						")A" ;
		}
		
		System.out.println("Chi phi khac " + query);
		
		return this.db.get(query);
	}

	public String getDTdutoannamtruoc(){
		String query = "SELECT ISNULL(DOANHTHUDUTOAN,0) AS DOANHTHUDUTOAN FROM ERP_LAPNGANSACH WHERE NAM = " + this.namtruoc + " AND CONGTY_FK = " + this.ctyId + " ";
		System.out.println(query);
		ResultSet rs = this.db.get(query);
		try{
			rs.next();
			String doanhthu = rs.getString("DOANHTHUDUTOAN");
			rs.close();
			return doanhthu;
		}catch(java.sql.SQLException e){
			return "0"; 
		}
	}
	
	public String getDTdutoan(){
		String query = "SELECT ISNULL(DOANHTHUDUTOAN,0) AS DOANHTHUDUTOAN FROM ERP_LAPNGANSACH WHERE NAM = " + this.nam + " AND CONGTY_FK = " + this.ctyId + " ";
		System.out.println(query);
		ResultSet rs = this.db.get(query);
		try{
			rs.next();
			String doanhthu = rs.getString("DOANHTHUDUTOAN");
			rs.close();
			return doanhthu;
		}catch(java.sql.SQLException e){
			return "0"; 
		}
	}

	public String getGVdutoannamtruoc(){
		String query = "SELECT ISNULL(GIAVONDUTOAN, 0) AS GIAVONDUTOAN FROM ERP_LAPNGANSACH WHERE NAM = " + this.namtruoc + " AND CONGTY_FK = " + this.ctyId + " ";
		ResultSet rs = this.db.get(query);
		try{
			rs.next();
			String giavon = rs.getString("GIAVONDUTOAN");
			rs.close();
			return giavon;
		}catch(java.sql.SQLException e){
			return "0"; 
		}
	}
	
	public String getGVdutoan(){
		String query = "SELECT ISNULL(GIAVONDUTOAN, 0) AS GIAVONDUTOAN FROM ERP_LAPNGANSACH WHERE NAM = " + this.nam + " AND CONGTY_FK = " + this.ctyId + " ";
		ResultSet rs = this.db.get(query);
		try{
			rs.next();
			String giavon = rs.getString("GIAVONDUTOAN");
			rs.close();
			return giavon;
		}catch(java.sql.SQLException e){
			return "0"; 
		}		
	}
	
	private void Tinhkhauhao_New(String Id, int n,  double thanhtien, int thangbatdau, int nam){
		int month = thangbatdau; 
		int year = nam;
				
		double[] khdkVal = new double[n];
		double[] lkdkVal = new double[n];
		double[] gtdkVal = new double[n];
		
		lkdkVal[0] = 0;
		
		for(int i = 0; i < n; i++)
		{
			if(i == (n - 1))
			{
				 if(i > 1)
					 khdkVal[i] = thanhtien - lkdkVal[i-1];
				 else
					 khdkVal[i] = thanhtien;

				if(i > 1) 
					lkdkVal[i] = khdkVal[i] + lkdkVal[i-1];
				else
					lkdkVal[i] = khdkVal[i] ;

			 	gtdkVal[i] = thanhtien - lkdkVal[i];
			}
			else
			{
			 	khdkVal[i] = Math.round(thanhtien/n);
				if(i > 0) 
					lkdkVal[i] = khdkVal[i] + lkdkVal[i-1];
				else
					lkdkVal[i] = khdkVal[i] ;
			 	
			 	gtdkVal[i] = thanhtien-lkdkVal[i];
			}					
			 
			String query = "";
			if(this.loai.equals("1")){
				query = "INSERT INTO ERP_LAPNGANSACH_TAISAN_KHAUHAODUKIEN (LAPNGANSACH_TAISAN_FK, KHAUHAODUKIEN, KHAUHAOLUYKEDUKIEN, GIATRICONLAIDUKIEN, THANG, NAM) " +
						"VALUES( '" + Id + "'," + khdkVal[i] + ", " + lkdkVal[i] + ", " + gtdkVal[i] + ", " + month + ", " + year + " )";
			 
				//System.out.println("khauhao_taomoi: " + query);
				if(!this.db.update(query)){
					query = "UPDATE ERP_LAPNGANSACH_TAISAN_KHAUHAODUKIEN SET KHAUHAODUKIEN = KHAUHAODUKIEN + " + khdkVal[i] + ", " +
							"KHAUHAOLUYKEDUKIEN = KHAUHAOLUYKEDUKIEN + " + lkdkVal[i] + ", GIATRICONLAIDUKIEN = GIATRICONLAIDUKIEN + " + gtdkVal[i] + " " +
							"WHERE LAPNGANSACH_TAISAN_FK = '" + Id + "' AND THANG = " + month + " AND NAM = " + year + " ";
			
				//System.out.println("khauhao_capnhat: " + query);
				this.db.update(query);
				}
			}else{
				query = "INSERT INTO ERP_LAPNGANSACH_CCDC_KHAUHAODUKIEN (LAPNGANSACH_CCDC_FK, KHAUHAODUKIEN, KHAUHAOLUYKEDUKIEN, GIATRICONLAIDUKIEN, THANG, NAM) " +
						"VALUES( '" + Id + "'," + khdkVal[i] + ", " + lkdkVal[i] + ", " + gtdkVal[i] + ", " + month + ", " + year + " )";
			 
				//System.out.println("khauhao_taomoi: " + query);
				if(!this.db.update(query)){
					query = "UPDATE ERP_LAPNGANSACH_CCDC_KHAUHAODUKIEN SET KHAUHAODUKIEN = KHAUHAODUKIEN + " + khdkVal[i] + ", " +
							"KHAUHAOLUYKEDUKIEN = KHAUHAOLUYKEDUKIEN + " + lkdkVal[i] + ", GIATRICONLAIDUKIEN = GIATRICONLAIDUKIEN + " + gtdkVal[i] + " " +
							"WHERE LAPNGANSACH_CCDC_FK = '" + Id + "' AND THANG = " + month + " AND NAM = " + year + " ";
			
				//System.out.println("khauhao_capnhat: " + query);
				this.db.update(query);
				}
				
			}
			month++;
			
			if(month > 12) {
				month = 1;
				year++;
			}
		}

	}

	public ResultSet getKhauhaotaisandukien(){
	if(this.loai.equals("1")){
		String query = 	"SELECT A.TSID, A.TEN, A.SOTHANGKHAUHAONAMTOI*B.KHAUHAODUKIEN AS KHAUHAO " +
						"FROM " +
						"( " +
						"	SELECT	TSCD.PK_SEQ AS TSID, TSCD.TEN, TSCD.SOTHANGKH, KH.THANGDAKH, " +
						"	TSCD.SOTHANGKH-ISNULL(KH.THANGDAKH,0) AS SOTHANGKHCONLAI, " +
						"	CASE WHEN TSCD.SOTHANGKH-ISNULL(KH.THANGDAKH,0) > (12-KH.THANG) " +			// Nghia la se phai khau hao tiep trong nam toi
						"	THEN  " +
						"	CASE WHEN (TSCD.SOTHANGKH-ISNULL(KH.THANGDAKH,0) - (12-KH.THANG)) > 12 " +
						"	THEN 12 " +
						"	ELSE (TSCD.SOTHANGKH-ISNULL(KH.THANGDAKH,0) - (12-KH.THANG)) " +
						"	END	" +				
						"	ELSE 0 END AS SOTHANGKHAUHAONAMTOI " +										// Tinh ra so thang khau hao nam toi
						"	FROM ERP_TAISANCODINH TSCD " +
						"	LEFT JOIN " +
						"	( " +
						"		SELECT TAISAN_FK, MAX(THANGTHU) AS THANGDAKH, THANG, NAM " +
						"		FROM ERP_KHAUHAOTAISAN WHERE NAM = " + this.namtruoc + " " +
						"		GROUP BY TAISAN_FK, THANG, NAM " +
						"	)KH ON KH.TAISAN_FK = TSCD.PK_SEQ " +
						"	WHERE TSCD.CONGDUNG_FK <> '9000000' " +						// Loai tru nhung tai san dung cho san xuat
						")A " +
						"INNER JOIN " +
						"( " +
						"	SELECT TAISAN_FK AS TSID , SUM(KHAUHAODUKIEN)/COUNT(TAISAN_FK) AS KHAUHAODUKIEN " +
						"	FROM ERP_TAISAN_CHITIET " +
						"	GROUP BY TAISAN_FK " +
						")B ON B.TSID = A.TSID " +
						"WHERE A.SOTHANGKHAUHAONAMTOI > 0 " +
						"UNION " +										// Doi voi nhung tai san moi du toan ngan sach
						"	SELECT TSID, TEN, SUM(KHAUHAO) AS KHAUHAO " +
						"	FROM ( " +
						"		SELECT	LNS_TS.PK_SEQ AS TSID, LNS_TS.DIENGIAI AS TEN, (T1*LNS_TS.DONGIA/LNS_TS.SOTHANGKH)*11 AS KHAUHAO " +
						"		FROM ERP_LAPNGANSACH_TAISAN LNS_TS " +
						"		INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = LNS_TS.LAPNGANSACH_FK " +
						"		WHERE LNS.NAM = " + this.nam + " AND CONGTY_FK = " + this.ctyId + " AND LNS_TS.CONGDUNG_FK  <> '9000000' " +
						"	UNION " +
						"		SELECT	LNS_TS.PK_SEQ AS TSID, LNS_TS.DIENGIAI AS TEN, (T2*LNS_TS.DONGIA/LNS_TS.SOTHANGKH)*10 AS KHAUHAO " +
						"		FROM ERP_LAPNGANSACH_TAISAN LNS_TS " +
						"		INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = LNS_TS.LAPNGANSACH_FK " +
						"		WHERE LNS.NAM = " + this.nam + " AND CONGTY_FK = " + this.ctyId + " AND LNS_TS.CONGDUNG_FK  <> '9000000' " +
						"	UNION " +
						"		SELECT	LNS_TS.PK_SEQ AS TSID, LNS_TS.DIENGIAI AS TEN, (T3*LNS_TS.DONGIA/LNS_TS.SOTHANGKH)*9 AS KHAUHAO " +
						"		FROM ERP_LAPNGANSACH_TAISAN LNS_TS " +
						"		INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = LNS_TS.LAPNGANSACH_FK " +
						"		WHERE LNS.NAM = " + this.nam + " AND CONGTY_FK = " + this.ctyId + " AND LNS_TS.CONGDUNG_FK  <> '9000000' " +
						"	UNION " +
						"		SELECT	LNS_TS.PK_SEQ AS TSID, LNS_TS.DIENGIAI AS TEN, (T4*LNS_TS.DONGIA/LNS_TS.SOTHANGKH)*8 AS KHAUHAO " +
						"		FROM ERP_LAPNGANSACH_TAISAN LNS_TS " +
						"		INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = LNS_TS.LAPNGANSACH_FK " +
						"		WHERE LNS.NAM = " + this.nam + " AND CONGTY_FK = " + this.ctyId + " AND LNS_TS.CONGDUNG_FK  <> '9000000' " +
						"	UNION " +
						"		SELECT	LNS_TS.PK_SEQ AS TSID, LNS_TS.DIENGIAI AS TEN, (T5*LNS_TS.DONGIA/LNS_TS.SOTHANGKH)*7 AS KHAUHAO " +
						"		FROM ERP_LAPNGANSACH_TAISAN LNS_TS " +
						"		INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = LNS_TS.LAPNGANSACH_FK " +
						"		WHERE LNS.NAM = " + this.nam + " AND CONGTY_FK = " + this.ctyId + " AND LNS_TS.CONGDUNG_FK  <> '9000000' " +
						"	UNION " +
						"		SELECT	LNS_TS.PK_SEQ AS TSID, LNS_TS.DIENGIAI AS TEN, (T6*LNS_TS.DONGIA/LNS_TS.SOTHANGKH)*6 AS KHAUHAO " +
						"		FROM ERP_LAPNGANSACH_TAISAN LNS_TS " +
						"		INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = LNS_TS.LAPNGANSACH_FK " +
						"		WHERE LNS.NAM = " + this.nam + " AND CONGTY_FK = " + this.ctyId + " AND LNS_TS.CONGDUNG_FK  <> '9000000' " +
						"	UNION " +
						"		SELECT	LNS_TS.PK_SEQ AS TSID, LNS_TS.DIENGIAI AS TEN, (T7*LNS_TS.DONGIA/LNS_TS.SOTHANGKH)*5 AS KHAUHAO " +
						"		FROM ERP_LAPNGANSACH_TAISAN LNS_TS " +
						"		INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = LNS_TS.LAPNGANSACH_FK " +
						"		WHERE LNS.NAM = " + this.nam + " AND CONGTY_FK = " + this.ctyId + " AND LNS_TS.CONGDUNG_FK  <> '9000000' " +
						"	UNION " +
						"		SELECT	LNS_TS.PK_SEQ AS TSID, LNS_TS.DIENGIAI AS TEN, (T8*LNS_TS.DONGIA/LNS_TS.SOTHANGKH)*4 AS KHAUHAO " +						
						"		FROM ERP_LAPNGANSACH_TAISAN LNS_TS	" +				
						"		INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = LNS_TS.LAPNGANSACH_FK " +
						"		WHERE LNS.NAM = " + this.nam + " AND CONGTY_FK = " + this.ctyId + " AND LNS_TS.CONGDUNG_FK  <> '9000000' " +
						"	UNION " +
						"		SELECT	LNS_TS.PK_SEQ AS TSID, LNS_TS.DIENGIAI AS TEN, (T9*LNS_TS.DONGIA/LNS_TS.SOTHANGKH)*3 AS KHAUHAO " +
						"		FROM ERP_LAPNGANSACH_TAISAN LNS_TS " +
						"		INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = LNS_TS.LAPNGANSACH_FK " +
						"		WHERE LNS.NAM = " + this.nam + " AND CONGTY_FK = " + this.ctyId + " AND LNS_TS.CONGDUNG_FK  <> '9000000' " +
						"	UNION " +
						"		SELECT	LNS_TS.PK_SEQ AS TSID, LNS_TS.DIENGIAI AS TEN, (T10*LNS_TS.DONGIA/LNS_TS.SOTHANGKH)*2 AS KHAUHAO " +
						"		FROM ERP_LAPNGANSACH_TAISAN LNS_TS " +
						"		INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = LNS_TS.LAPNGANSACH_FK " +
						"		WHERE LNS.NAM = " + this.nam + " AND CONGTY_FK = " + this.ctyId + " AND LNS_TS.CONGDUNG_FK  <> '9000000' " +
						"	UNION " +
						"		SELECT	LNS_TS.PK_SEQ AS TSID, LNS_TS.DIENGIAI AS TEN, (T11*LNS_TS.DONGIA/LNS_TS.SOTHANGKH)*1 AS KHAUHAO " +
						"		FROM ERP_LAPNGANSACH_TAISAN LNS_TS " +
						"		INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = LNS_TS.LAPNGANSACH_FK " +
						"		WHERE LNS.NAM = " + this.nam + " AND CONGTY_FK = " + this.ctyId + " AND LNS_TS.CONGDUNG_FK  <> '9000000' " +
						")C " +
						
						"GROUP BY TEN, TSID " ;
		
		
		System.out.println(query);
		return this.db.get(query);
	}else{
		String query = 	"SELECT A.TSID, A.TEN, A.SOTHANGKHAUHAONAMTOI*B.KHAUHAODUKIEN AS KHAUHAO " +
				"FROM " +
				"( " +
				"	SELECT	TSCD.PK_SEQ AS TSID, TSCD.TEN, TSCD.SOTHANGKH, KH.THANGDAKH, " +
				"	TSCD.SOTHANGKH-ISNULL(KH.THANGDAKH,0) AS SOTHANGKHCONLAI, " +
				"	CASE WHEN TSCD.SOTHANGKH-ISNULL(KH.THANGDAKH,0) > (12-KH.THANG) " +			// Nghia la se phai khau hao tiep trong nam toi
				"	THEN  " +
				"	CASE WHEN (TSCD.SOTHANGKH-ISNULL(KH.THANGDAKH,0) - (12-KH.THANG)) > 12 " +
				"	THEN 12 " +
				"	ELSE (TSCD.SOTHANGKH-ISNULL(KH.THANGDAKH,0) - (12-KH.THANG)) " +
				"	END	" +				
				"	ELSE 0 END AS SOTHANGKHAUHAONAMTOI " +										// Tinh ra so thang khau hao nam toi
				"	FROM ERP_CONGCUDUNGCU TSCD " +
				"	LEFT JOIN " +
				"	( " +
				"		SELECT CCDC_FK, MAX(THANGTHU) AS THANGDAKH, THANG, NAM " +
				"		FROM ERP_KHAUHAOCCDC WHERE NAM = " + this.namtruoc + " " +
				"		GROUP BY CCDC_FK, THANG, NAM " +
				"	)KH ON KH.CCDC_FK = TSCD.PK_SEQ " +
				"	WHERE TSCD.CONGDUNG_FK <> '9000000' " +						// Loai tru nhung tai san dung cho san xuat
				")A " +
				"INNER JOIN " +
				"( " +
				"	SELECT CCDC_FK AS TSID , SUM(KHAUHAODUKIEN)/COUNT(CCDC_FK) AS KHAUHAODUKIEN " +
				"	FROM ERP_CCDC_CHITIET " +
				"	GROUP BY CCDC_FK " +
				")B ON B.TSID = A.TSID " +
				"WHERE A.SOTHANGKHAUHAONAMTOI > 0 " +
				"UNION " +										// Doi voi nhung tai san moi du toan ngan sach
				"	SELECT TSID, TEN, SUM(KHAUHAO) AS KHAUHAO " +
				"	FROM ( " +
				"		SELECT	LNS_TS.PK_SEQ AS TSID, LNS_TS.DIENGIAI AS TEN, (T1*LNS_TS.DONGIA/LNS_TS.SOTHANGKH)*11 AS KHAUHAO " +
				"		FROM ERP_LAPNGANSACH_CCDC LNS_TS " +
				"		INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = LNS_TS.LAPNGANSACH_FK " +
				"		WHERE LNS.NAM = " + this.nam + " AND CONGTY_FK = " + this.ctyId + " AND LNS_TS.CONGDUNG_FK  <> '9000000' " +
				"	UNION " +
				"		SELECT	LNS_TS.PK_SEQ AS TSID, LNS_TS.DIENGIAI AS TEN, (T2*LNS_TS.DONGIA/LNS_TS.SOTHANGKH)*10 AS KHAUHAO " +
				"		FROM ERP_LAPNGANSACH_CCDC LNS_TS " +
				"		INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = LNS_TS.LAPNGANSACH_FK " +
				"		WHERE LNS.NAM = " + this.nam + " AND CONGTY_FK = " + this.ctyId + " AND LNS_TS.CONGDUNG_FK  <> '9000000' " +
				"	UNION " +
				"		SELECT	LNS_TS.PK_SEQ AS TSID, LNS_TS.DIENGIAI AS TEN, (T3*LNS_TS.DONGIA/LNS_TS.SOTHANGKH)*9 AS KHAUHAO " +
				"		FROM ERP_LAPNGANSACH_CCDC LNS_TS " +
				"		INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = LNS_TS.LAPNGANSACH_FK " +
				"		WHERE LNS.NAM = " + this.nam + " AND CONGTY_FK = " + this.ctyId + " AND LNS_TS.CONGDUNG_FK  <> '9000000' " +
				"	UNION " +
				"		SELECT	LNS_TS.PK_SEQ AS TSID, LNS_TS.DIENGIAI AS TEN, (T4*LNS_TS.DONGIA/LNS_TS.SOTHANGKH)*8 AS KHAUHAO " +
				"		FROM ERP_LAPNGANSACH_CCDC LNS_TS " +
				"		INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = LNS_TS.LAPNGANSACH_FK " +
				"		WHERE LNS.NAM = " + this.nam + " AND CONGTY_FK = " + this.ctyId + " AND LNS_TS.CONGDUNG_FK  <> '9000000' " +
				"	UNION " +
				"		SELECT	LNS_TS.PK_SEQ AS TSID, LNS_TS.DIENGIAI AS TEN, (T5*LNS_TS.DONGIA/LNS_TS.SOTHANGKH)*7 AS KHAUHAO " +
				"		FROM ERP_LAPNGANSACH_CCDC LNS_TS " +
				"		INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = LNS_TS.LAPNGANSACH_FK " +
				"		WHERE LNS.NAM = " + this.nam + " AND CONGTY_FK = " + this.ctyId + " AND LNS_TS.CONGDUNG_FK  <> '9000000' " +
				"	UNION " +
				"		SELECT	LNS_TS.PK_SEQ AS TSID, LNS_TS.DIENGIAI AS TEN, (T6*LNS_TS.DONGIA/LNS_TS.SOTHANGKH)*6 AS KHAUHAO " +
				"		FROM ERP_LAPNGANSACH_CCDC LNS_TS " +
				"		INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = LNS_TS.LAPNGANSACH_FK " +
				"		WHERE LNS.NAM = " + this.nam + " AND CONGTY_FK = " + this.ctyId + " AND LNS_TS.CONGDUNG_FK  <> '9000000' " +
				"	UNION " +
				"		SELECT	LNS_TS.PK_SEQ AS TSID, LNS_TS.DIENGIAI AS TEN, (T7*LNS_TS.DONGIA/LNS_TS.SOTHANGKH)*5 AS KHAUHAO " +
				"		FROM ERP_LAPNGANSACH_CCDC LNS_TS " +
				"		INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = LNS_TS.LAPNGANSACH_FK " +
				"		WHERE LNS.NAM = " + this.nam + " AND CONGTY_FK = " + this.ctyId + " AND LNS_TS.CONGDUNG_FK  <> '9000000' " +
				"	UNION " +
				"		SELECT	LNS_TS.PK_SEQ AS TSID, LNS_TS.DIENGIAI AS TEN, (T8*LNS_TS.DONGIA/LNS_TS.SOTHANGKH)*4 AS KHAUHAO " +						
				"		FROM ERP_LAPNGANSACH_CCDC LNS_TS	" +				
				"		INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = LNS_TS.LAPNGANSACH_FK " +
				"		WHERE LNS.NAM = " + this.nam + " AND CONGTY_FK = " + this.ctyId + " AND LNS_TS.CONGDUNG_FK  <> '9000000' " +
				"	UNION " +
				"		SELECT	LNS_TS.PK_SEQ AS TSID, LNS_TS.DIENGIAI AS TEN, (T9*LNS_TS.DONGIA/LNS_TS.SOTHANGKH)*3 AS KHAUHAO " +
				"		FROM ERP_LAPNGANSACH_CCDC LNS_TS " +
				"		INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = LNS_TS.LAPNGANSACH_FK " +
				"		WHERE LNS.NAM = " + this.nam + " AND CONGTY_FK = " + this.ctyId + " AND LNS_TS.CONGDUNG_FK  <> '9000000' " +
				"	UNION " +
				"		SELECT	LNS_TS.PK_SEQ AS TSID, LNS_TS.DIENGIAI AS TEN, (T10*LNS_TS.DONGIA/LNS_TS.SOTHANGKH)*2 AS KHAUHAO " +
				"		FROM ERP_LAPNGANSACH_CCDC LNS_TS " +
				"		INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = LNS_TS.LAPNGANSACH_FK " +
				"		WHERE LNS.NAM = " + this.nam + " AND CONGTY_FK = " + this.ctyId + " AND LNS_TS.CONGDUNG_FK  <> '9000000' " +
				"	UNION " +
				"		SELECT	LNS_TS.PK_SEQ AS TSID, LNS_TS.DIENGIAI AS TEN, (T11*LNS_TS.DONGIA/LNS_TS.SOTHANGKH)*1 AS KHAUHAO " +
				"		FROM ERP_LAPNGANSACH_CCDC LNS_TS " +
				"		INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = LNS_TS.LAPNGANSACH_FK " +
				"		WHERE LNS.NAM = " + this.nam + " AND CONGTY_FK = " + this.ctyId + " AND LNS_TS.CONGDUNG_FK  <> '9000000' " +
				")C " +
				
				"GROUP BY TEN, TSID " ;


		System.out.println(query);
		return this.db.get(query);		
	}
	}
	
	public double getTongDoanhThu(){
		double result = 0;
		String query = 	"SELECT SUM(DBSP.DUKIENBAN*GBSP.GIABAN) AS TDT \n " +
						"FROM ERP_LAPNGANSACH_DUBAOSANPHAM DBSP \n " +
						"INNER JOIN ERP_LAPNGANSACH_DUBAO DB ON DB.PK_SEQ = DBSP.DUBAO_FK \n " +
						"INNER JOIN ERP_LNSBANGGIABAN GB ON GB.NAM = DBSP.NAM AND GB.TRANGTHAI = 1 AND GB.KENH_FK = DB.KBH_FK \n " + 
						"INNER JOIN ERP_LNSBGBAN_SANPHAM GBSP ON GBSP.BGBAN_FK = GB.PK_SEQ AND GBSP.SANPHAM_FK = DBSP.SANPHAM_FK AND GBSP.THANG = DBSP.THANG AND GBSP.NAM = DBSP.NAM \n " +
						"WHERE DBSP.LAPNGANSACH_FK = " + this.lnsId + " AND DB.CONGTY_FK = " + this.ctyId + " AND DBSP.NAM = " + this.nam + " ";

		System.out.println("Tong doanh thu:" + query);
		
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				rs.next();
				result = rs.getDouble("TDT");
				rs.close();
			}catch(java.sql.SQLException e){}
		}
		return result;
	}
	
	private String[] prepareData(String[] data, String query){
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						
						if(rs.getString("THANG").trim().equals("" + (i+1))){							
							data[i] = "" + Math.round(Double.parseDouble(rs.getString("NUM")));
							System.out.println(i + " - " + data[i] );						
						}else{							
							data[i] = "0";
						}

						if(!rs.next()){
							for(int j = i + 1; j < 12; j++){
								data[j] = "0";
							}
							
							i = 12;
						}
					}
					rs.close();
				}else{
					for(int i = 0; i < 12; i++){
													
						data[i] = "0";
						
					}
				
				}
			}catch(java.sql.SQLException e){}
		}else{
			for(int i = 0; i < 12; i++){
				
				data[i] = "0";
				
			}
		}
		
		return data;
	}
	
	private String[][] prepareData_MKT(int count, String query)
	{
		String[][] data = new String[count][13];
		//System.out.println(query);
		ResultSet rs = this.db.get(query);
		
		try 
		{
			int j = -1;
			String maSP = "";
			while(rs.next())
			{
				
				if(!maSP.equals(rs.getString("MA")))
				{
					maSP = rs.getString("MA");
					j++;
				}
				
				data[j][0] = maSP;
				data[j][rs.getInt("THANG")] = "" + Math.round(Double.parseDouble(rs.getString("NUM")));
				
			}
			rs.close();
		} 
		catch (Exception e) {}
		
		/*boolean flag = false;
		if(rs != null)
		{
			try
			{
				if(rs.next())
				{
					for(int j = 0; j < count; j++)
					{
						data[j][0] = rs.getString("MA");

						for(int i = 1; i < 13; i++)
						{
							if(rs.getString("THANG").trim().equals("" + i) && rs.getString("MA").equals(data[j][0]))
							{							
								data[j][i] = "" + Math.round(Double.parseDouble(rs.getString("NUM")));
								System.out.println("MKT - Dong j: " + data[j][0] + " Thang " + i + " - " + data[j][i] );
									
								flag = true;
							}
							else
							{							
								for(int n = i ; n < 13; n++)
								{
									data[j][n] = "0";
								}
									
								i = 13;
								flag = false;
							}
													
							if(flag==true)
							{
								if(!rs.next())
								{
									i = 13; j = count;
								}
							}
						}
						
					}
					
				}
				rs.close();
			}
			catch(Exception e){ System.out.println("---PL_SalesVolumn_LA_Details Exception..."); }
		}*/
		
		return data;
	}

	public String[] PL_SalesVolumn(){
		String[] data = new String[12];
		/*String query = 	"SELECT LNS_DBSP.THANG, ISNULL(SUM(LNS_DBSP.DUKIENBAN),0) AS NUM " +
						"FROM ERP_LAPNGANSACH_DUBAO LNS_DB " +
						"INNER JOIN ERP_LAPNGANSACH_DUBAOSANPHAM LNS_DBSP ON LNS_DBSP.DUBAO_FK = LNS_DB.PK_SEQ " +
						"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = LNS_DBSP.SANPHAM_FK AND SP.DVKD_FK = LNS_DB.DVKD_FK " +
						"WHERE	LNS_DB.LAPNGANSACH_FK = " + this.Id + " AND LNS_DB.CONGTY_FK = " + this.ctyId + "  AND SP.LOAISANPHAM_FK = '100005' " +
						"GROUP BY LNS_DBSP.THANG " +
						"ORDER BY LNS_DBSP.THANG";*/
		
		//LAM CHO NHOM TRUOC
		String query =  " SELECT LNS_DBSP.THANG, ISNULL(SUM(LNS_DBSP.DUKIENBAN),0) AS NUM  " +
						" FROM ERP_LAPNGANSACH_DUBAO LNS_DB  " +
						" 	INNER JOIN ERP_LAPNGANSACH_DUBAOSANPHAM LNS_DBSP ON LNS_DBSP.DUBAO_FK = LNS_DB.PK_SEQ  " +
						" WHERE	LNS_DB.LAPNGANSACH_FK =  '" + this.Id + "'  AND LNS_DB.CONGTY_FK =  '" + this.ctyId + "'  " +
						" 			and LNS_DBSP.SANPHAM_FK in ( select SANPHAM_FK from ERP_SANPHAM where LOAISANPHAM_FK = '100005' )  " +
						" GROUP BY LNS_DBSP.THANG  " +
						" ORDER BY LNS_DBSP.THANG ";
				
		System.out.println("1.SALES VOLUME: " + query);
		data = prepareData(data, query);
		
		return data;
	}

	public String[] PL_SalesAmount(){
		String[] data = new String[12];
		/*String query = 	"SELECT LNS_DBSP.THANG, SUM(ISNULL(LNS_DBSP.DUKIENBAN,0)*ISNULL(BG_SP.GIABAN,0)) AS NUM " +
						"FROM ERP_LAPNGANSACH_DUBAO LNS_DB " +
						"INNER JOIN ERP_LAPNGANSACH_DUBAOSANPHAM LNS_DBSP ON LNS_DBSP.DUBAO_FK = LNS_DB.PK_SEQ " +
						"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = LNS_DBSP.SANPHAM_FK AND SP.DVKD_FK = LNS_DB.DVKD_FK " +
						"LEFT JOIN ERP_LNSBANGGIABAN_KH BG_KH ON BG_KH.KH_FK =  LNS_DBSP.KHACHHANG_FK " +
						"LEFT JOIN ERP_LNSBANGGIABAN BG ON BG.PK_SEQ = BG_KH.BANGGIABAN_FK " +
						"LEFT JOIN ERP_LNSBGBAN_SANPHAM BG_SP ON BG_SP.BGBAN_FK = BG.PK_SEQ AND BG_SP.SANPHAM_FK = LNS_DBSP.SANPHAM_FK AND BG_SP.TRANGTHAI = 1 " +

						"WHERE	LNS_DB.LAPNGANSACH_FK = " + this.Id + " AND LNS_DB.CONGTY_FK = " + this.ctyId + "  AND SP.LOAISANPHAM_FK = '100005' " +
						"GROUP BY LNS_DBSP.THANG " +
						"ORDER BY LNS_DBSP.THANG";*/
		
		//LAM CHO NHOM TRUOC
		String query =  " SELECT LNS_DBSP.THANG, SUM( ISNULL(LNS_DBSP.DUKIENBAN, 0) * ISNULL(LNS_DBSP.DONGIA, 0)) AS NUM  " +
						" FROM ERP_LAPNGANSACH_DUBAO LNS_DB  " +
						" 	INNER JOIN ERP_LAPNGANSACH_DUBAOSANPHAM LNS_DBSP ON LNS_DBSP.DUBAO_FK = LNS_DB.PK_SEQ  " +
						" WHERE	LNS_DB.LAPNGANSACH_FK =  '" + this.Id + "'  AND LNS_DB.CONGTY_FK =  '" + this.ctyId + "'  " +
						" 			and LNS_DBSP.SANPHAM_FK in ( select SANPHAM_FK from ERP_SANPHAM where LOAISANPHAM_FK = '100005' )  " +
						" GROUP BY LNS_DBSP.THANG  " +
						" ORDER BY LNS_DBSP.THANG ";
				
		System.out.println("3.SalesAmount: " + query);
		data = prepareData(data, query);
		return data;
	}

	public String[] PL_SalesAmount_Actual(){
		String[] data = new String[12];
		String query =  "SELECT SUBSTRING(NGAYXUATHD, 6, 2), SUM(TONGTIENAVAT) AS NUM " +
						"FROM ERP_HOADON " +
						"WHERE CONGTY_FK = " + this.ctyId + " AND SUBSTRING(NGAYXUATHD, 1, 4) = '" + this.nam + "' " +
						"AND TRANGTHAI <> 0 " + 
						"AND TRANGTHAI <> 2 " +
						"GROUP BY SUBSTRING(NGAYXUATHD, 6, 2) " +
						"ORDER BY SUBSTRING(NGAYXUATHD, 6, 2) ";
				
		System.out.println("3.SalesAmount: " + query);
		data = prepareData(data, query);
		return data;
	}

	public String[] PL_SalesVolumn_LA(){
		String[] data = new String[12];
		/*String query = 	"SELECT LNS_DB.DVKD_FK, LNS_DBSP.THANG, ISNULL(SUM(LNS_DBSP.DUKIENBAN), 0) AS NUM " +
						"FROM ERP_LAPNGANSACH_DUBAO LNS_DB " +
						"INNER JOIN ERP_LAPNGANSACH_DUBAOSANPHAM LNS_DBSP ON LNS_DBSP.DUBAO_FK = LNS_DB.PK_SEQ " +
						"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = LNS_DBSP.SANPHAM_FK AND SP.DVKD_FK = LNS_DB.DVKD_FK " +
						"WHERE	LNS_DB.LAPNGANSACH_FK = " + this.Id + " AND LNS_DB.DVKD_FK = '100000' AND SP.LOAISANPHAM_FK = '100005' " +
						"AND LNS_DB.CONGTY_FK = " + this.ctyId + "  " +
						"GROUP BY LNS_DB.DVKD_FK, LNS_DBSP.THANG " +
						"ORDER BY LNS_DB.DVKD_FK ";*/
		
		//LAM CHO NHOM TRUOC
		String query =  " SELECT LNS_DBSP.THANG, ISNULL(SUM(LNS_DBSP.DUKIENBAN),0) AS NUM  " +
						" FROM ERP_LAPNGANSACH_DUBAO LNS_DB  " +
						" 	INNER JOIN ERP_LAPNGANSACH_DUBAOSANPHAM LNS_DBSP ON LNS_DBSP.DUBAO_FK = LNS_DB.PK_SEQ  " +
						" WHERE	LNS_DB.LAPNGANSACH_FK =  '" + this.Id + "'  AND LNS_DB.CONGTY_FK =  '" + this.ctyId + "'  " +
						" 			and LNS_DBSP.SANPHAM_FK in ( select SANPHAM_FK from ERP_SANPHAM where LOAISANPHAM_FK = '100005' )  " +
						" GROUP BY LNS_DBSP.THANG  " +
						" ORDER BY LNS_DBSP.THANG ";
		
		System.out.println("2.PL_SalesVolumn_LA: " + query);
		data = prepareData(data, query);
		return data;
	}
	

	public String[][] PL_SalesVolumn_LA_Details(){
		String[][] data;
		try{
		/*String query = 	"SELECT COUNT(A.MA) AS NUM FROM " +
						"(SELECT DISTINCT MKT.MA " +
						"FROM ERP_LAPNGANSACH_DUBAO LNS_DB " +
						"INNER JOIN ERP_LAPNGANSACH_DUBAOSANPHAM LNS_DBSP ON LNS_DBSP.DUBAO_FK = LNS_DB.PK_SEQ " +
						"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = LNS_DBSP.SANPHAM_FK AND SP.DVKD_FK = LNS_DB.DVKD_FK " +
						"INNER JOIN ERP_MAKETOAN MKT ON MKT.PK_SEQ = SP.MAKETOAN_FK " +
						"WHERE	LNS_DB.LAPNGANSACH_FK = " + this.Id + " AND LNS_DB.DVKD_FK = '100000' AND SP.LOAISANPHAM_FK = '100005' " +
						"AND LNS_DB.CONGTY_FK = " + this.ctyId + "  " +
						"GROUP BY LNS_DB.DVKD_FK,  MKT.PK_SEQ, MKT.MA, LNS_DBSP.THANG " +
						")A ";*/
			
		String query =  " select count(A.MA) as NUM " +
						" from " +
						" ( " +
						" 	SELECT distinct LNS_DBSP.SANPHAM_FK AS MA " +
						" 	FROM ERP_LAPNGANSACH_DUBAO LNS_DB  " +
						" 	INNER JOIN ERP_LAPNGANSACH_DUBAOSANPHAM LNS_DBSP ON LNS_DBSP.DUBAO_FK = LNS_DB.PK_SEQ  " +
						" 	WHERE	LNS_DB.LAPNGANSACH_FK =  '" + this.Id + "'  AND LNS_DB.CONGTY_FK =  '100005'  " +
						" 			and LNS_DBSP.SANPHAM_FK in ( select SANPHAM_FK from ERP_SANPHAM where DVKD_FK = '100000' and LOAISANPHAM_FK = '100005' )    " +
						" 	GROUP BY LNS_DB.DVKD_FK, LNS_DBSP.THANG, LNS_DBSP.SANPHAM_FK " +
						" ) " +
						" A ";
		
		System.out.println("4." + query);
		ResultSet rs = this.db.get(query);
		
		rs.next();
		
		int count = rs.getInt("NUM");
		System.out.println("count: " + count);
		rs.close();
		
		/*query = 	"SELECT MKT.PK_SEQ AS MKTID, MKT.MA, LNS_DB.DVKD_FK, LNS_DBSP.THANG, ISNULL(SUM(LNS_DBSP.DUKIENBAN), 0) AS NUM " +
					"FROM ERP_LAPNGANSACH_DUBAO LNS_DB " +
					"INNER JOIN ERP_LAPNGANSACH_DUBAOSANPHAM LNS_DBSP ON LNS_DBSP.DUBAO_FK = LNS_DB.PK_SEQ " +
					"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = LNS_DBSP.SANPHAM_FK AND SP.DVKD_FK = LNS_DB.DVKD_FK " +
					"INNER JOIN ERP_MAKETOAN MKT ON MKT.PK_SEQ = SP.MAKETOAN_FK " +
					"WHERE	LNS_DB.LAPNGANSACH_FK = " + this.Id + " AND LNS_DB.DVKD_FK = '100000' AND SP.LOAISANPHAM_FK = '100005' " +
					"AND LNS_DB.CONGTY_FK = " + this.ctyId + "  " +
					"GROUP BY LNS_DB.DVKD_FK,  MKT.PK_SEQ, MKT.MA, LNS_DBSP.THANG " +
					"ORDER BY MKT.MA ";*/
		
		query = "SELECT LNS_DBSP.SANPHAM_FK AS MKTID, LNS_DBSP.SANPHAM_FK as MA, LNS_DB.DVKD_FK, LNS_DBSP.THANG, ISNULL(SUM(LNS_DBSP.DUKIENBAN), 0) AS NUM  " +
				" FROM ERP_LAPNGANSACH_DUBAO LNS_DB  " +
				" INNER JOIN ERP_LAPNGANSACH_DUBAOSANPHAM LNS_DBSP ON LNS_DBSP.DUBAO_FK = LNS_DB.PK_SEQ  " +
				" WHERE	LNS_DB.LAPNGANSACH_FK =  '" + this.Id + "'  AND LNS_DB.CONGTY_FK =  '100005'  " +
				" 		and LNS_DBSP.SANPHAM_FK in ( select SANPHAM_FK from ERP_SANPHAM where DVKD_FK = '100000' and LOAISANPHAM_FK = '100005' )    " +
				" GROUP BY LNS_DB.DVKD_FK, LNS_DBSP.THANG, LNS_DBSP.SANPHAM_FK " +
				" ORDER BY LNS_DBSP.SANPHAM_FK";
		
		System.out.println("5.PL_SalesVolumn_LA_Details: " + query);
		data = prepareData_MKT(count, query);
		return data;
		
		}catch(java.sql.SQLException e){ return null;}
	
	}

	public String[][] PL_SalesVolumn_PA_Details(){
		String[][] data;
		try{
		String query = 	"SELECT COUNT(A.MA) AS NUM FROM " +
						"(SELECT DISTINCT MKT.MA " +
						"FROM ERP_LAPNGANSACH_DUBAO LNS_DB " +
						"INNER JOIN ERP_LAPNGANSACH_DUBAOSANPHAM LNS_DBSP ON LNS_DBSP.DUBAO_FK = LNS_DB.PK_SEQ " +
						"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = LNS_DBSP.SANPHAM_FK AND SP.DVKD_FK = LNS_DB.DVKD_FK " +
						"INNER JOIN ERP_MAKETOAN MKT ON MKT.PK_SEQ = SP.MAKETOAN_FK " +
						"WHERE	LNS_DB.LAPNGANSACH_FK = " + this.Id + " AND LNS_DB.DVKD_FK = '100004' AND SP.LOAISANPHAM_FK = '100005' " +
						"AND LNS_DB.CONGTY_FK = " + this.ctyId + "  " +
						"GROUP BY LNS_DB.DVKD_FK,  MKT.PK_SEQ, MKT.MA, LNS_DBSP.THANG " +
						")A ";
		
		System.out.println("COUNT-PA:" + query);
		ResultSet rs = this.db.get(query);
		
		rs.next();
		
		int count = rs.getInt("NUM");
		System.out.println("count: " + count);
		rs.close();
		
		query = 	"SELECT MKT.PK_SEQ AS MKTID, MKT.MA, LNS_DB.DVKD_FK, LNS_DBSP.THANG, ISNULL(SUM(LNS_DBSP.DUKIENBAN), 0) AS NUM " +
					"FROM ERP_LAPNGANSACH_DUBAO LNS_DB " +
					"INNER JOIN ERP_LAPNGANSACH_DUBAOSANPHAM LNS_DBSP ON LNS_DBSP.DUBAO_FK = LNS_DB.PK_SEQ " +
					"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = LNS_DBSP.SANPHAM_FK AND SP.DVKD_FK = LNS_DB.DVKD_FK " +
					"INNER JOIN ERP_MAKETOAN MKT ON MKT.PK_SEQ = SP.MAKETOAN_FK " +
					"WHERE	LNS_DB.LAPNGANSACH_FK = " + this.Id + " AND LNS_DB.DVKD_FK = '100004' AND SP.LOAISANPHAM_FK = '100005' " +
					"AND LNS_DB.CONGTY_FK = " + this.ctyId + "  " +
					"GROUP BY LNS_DB.DVKD_FK,  MKT.PK_SEQ, MKT.MA, LNS_DBSP.THANG " +
					"ORDER BY MKT.MA ";
		
		data = prepareData_MKT(count, query);
		return data;
		
		}catch(java.sql.SQLException e){ return null;}
	
	}

	public String[] PL_SalesAmount_LA(){
		String[] data = new String[12];
		/*String query = 	"SELECT LNS_DBSP.THANG, SUM(ISNULL(LNS_DBSP.DUKIENBAN,0)*ISNULL(BG_SP.GIABAN,0)) AS NUM " +
						"FROM ERP_LAPNGANSACH_DUBAO LNS_DB " +
						"INNER JOIN ERP_LAPNGANSACH_DUBAOSANPHAM LNS_DBSP ON LNS_DBSP.DUBAO_FK = LNS_DB.PK_SEQ " +
						"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = LNS_DBSP.SANPHAM_FK AND SP.DVKD_FK = LNS_DB.DVKD_FK " +
						"LEFT JOIN ERP_LNSBANGGIABAN_KH BG_KH ON BG_KH.KH_FK =  LNS_DBSP.KHACHHANG_FK " +
						"LEFT JOIN ERP_LNSBANGGIABAN BG ON BG.PK_SEQ = BG_KH.BANGGIABAN_FK " +
						"LEFT JOIN ERP_LNSBGBAN_SANPHAM BG_SP ON BG_SP.BGBAN_FK = BG.PK_SEQ AND BG_SP.SANPHAM_FK = LNS_DBSP.SANPHAM_FK AND BG_SP.TRANGTHAI = 1 " +
						"WHERE	LNS_DB.LAPNGANSACH_FK = " + this.Id + " AND LNS_DB.DVKD_FK = '100000' AND LNS_DB.CONGTY_FK = " + this.ctyId + " " +
						" AND SP.LOAISANPHAM_FK = '100005' " +
						"GROUP BY LNS_DBSP.THANG " +
						"ORDER BY LNS_DBSP.THANG";*/

		String query =  " SELECT LNS_DBSP.THANG, SUM( ISNULL(LNS_DBSP.DUKIENBAN, 0) * ISNULL(LNS_DBSP.DONGIA, 0)) AS NUM  " +
						" FROM ERP_LAPNGANSACH_DUBAO LNS_DB  " +
						" 	INNER JOIN ERP_LAPNGANSACH_DUBAOSANPHAM LNS_DBSP ON LNS_DBSP.DUBAO_FK = LNS_DB.PK_SEQ  " +
						" WHERE	LNS_DB.LAPNGANSACH_FK =  '" + this.Id + "'  AND LNS_DB.CONGTY_FK =  '" + this.ctyId + "'  " +
						" 			and LNS_DBSP.SANPHAM_FK in ( select SANPHAM_FK from ERP_SANPHAM where LOAISANPHAM_FK = '100005' )  " +
						" GROUP BY LNS_DBSP.THANG  " +
						" ORDER BY LNS_DBSP.THANG ";
		
		System.out.println(query);
		data = prepareData(data, query);
		return data;
	}

	public String[] PL_SalesAmount_PA(){
		String[] data = new String[12];
		String query = 	"SELECT LNS_DBSP.THANG, SUM(ISNULL(LNS_DBSP.DUKIENBAN,0)*ISNULL(BG_SP.GIABAN,0)) AS NUM " +
						"FROM ERP_LAPNGANSACH_DUBAO LNS_DB " +
						"INNER JOIN ERP_LAPNGANSACH_DUBAOSANPHAM LNS_DBSP ON LNS_DBSP.DUBAO_FK = LNS_DB.PK_SEQ " +
						"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = LNS_DBSP.SANPHAM_FK AND SP.DVKD_FK = LNS_DB.DVKD_FK " +
						"LEFT JOIN ERP_LNSBANGGIABAN_KH BG_KH ON BG_KH.KH_FK =  LNS_DBSP.KHACHHANG_FK " +
						"LEFT JOIN ERP_LNSBANGGIABAN BG ON BG.PK_SEQ = BG_KH.BANGGIABAN_FK " +
						"LEFT JOIN ERP_LNSBGBAN_SANPHAM BG_SP ON BG_SP.BGBAN_FK = BG.PK_SEQ AND BG_SP.SANPHAM_FK = LNS_DBSP.SANPHAM_FK AND BG_SP.TRANGTHAI = 1 " +
						"WHERE	LNS_DB.LAPNGANSACH_FK = " + this.Id + " AND LNS_DB.DVKD_FK = '100004' AND LNS_DB.CONGTY_FK = " + this.ctyId + " " +
						" AND SP.LOAISANPHAM_FK = '100005' " +
						"GROUP BY LNS_DBSP.THANG " +
						"ORDER BY LNS_DBSP.THANG";
				
		System.out.println(query);
		data = prepareData(data, query);
		return data;
	}

	public String[][] PL_SalesAmount_LA_Details(){
		String[][] data;
		try{
		/*String query = 	"SELECT COUNT(A.MA) AS NUM FROM " +
						"(SELECT DISTINCT MKT.MA " +
						"FROM ERP_LAPNGANSACH_DUBAO LNS_DB " +
						"INNER JOIN ERP_LAPNGANSACH_DUBAOSANPHAM LNS_DBSP ON LNS_DBSP.DUBAO_FK = LNS_DB.PK_SEQ " +
						"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = LNS_DBSP.SANPHAM_FK AND SP.DVKD_FK = LNS_DB.DVKD_FK " +
						"INNER JOIN ERP_MAKETOAN MKT ON MKT.PK_SEQ = SP.MAKETOAN_FK " +
						"LEFT JOIN ERP_LNSBANGGIABAN_KH BG_KH ON BG_KH.KH_FK =  LNS_DBSP.KHACHHANG_FK " +
						"LEFT JOIN ERP_LNSBANGGIABAN BG ON BG.PK_SEQ = BG_KH.BANGGIABAN_FK " +
						"LEFT JOIN ERP_LNSBGBAN_SANPHAM BG_SP ON BG_SP.BGBAN_FK = BG.PK_SEQ AND BG_SP.SANPHAM_FK = LNS_DBSP.SANPHAM_FK AND BG_SP.TRANGTHAI = 1 " +						
						"WHERE	LNS_DB.LAPNGANSACH_FK = " + this.Id + " AND LNS_DB.DVKD_FK = '100000' AND SP.LOAISANPHAM_FK = '100005' " +
						"AND LNS_DB.CONGTY_FK = " + this.ctyId + "  " +
						"GROUP BY LNS_DB.DVKD_FK,  MKT.PK_SEQ, MKT.MA, LNS_DBSP.THANG " +
						")A ";*/
			
		String query =  " select count(A.MA) as NUM " +
						" from " +
						" ( " +
						" 	SELECT distinct LNS_DBSP.SANPHAM_FK AS MA " +
						" 	FROM ERP_LAPNGANSACH_DUBAO LNS_DB  " +
						" 	INNER JOIN ERP_LAPNGANSACH_DUBAOSANPHAM LNS_DBSP ON LNS_DBSP.DUBAO_FK = LNS_DB.PK_SEQ  " +
						" 	WHERE	LNS_DB.LAPNGANSACH_FK =  '" + this.Id + "'  AND LNS_DB.CONGTY_FK =  '100005'  " +
						" 			and LNS_DBSP.SANPHAM_FK in ( select SANPHAM_FK from ERP_SANPHAM where DVKD_FK = '100000' and LOAISANPHAM_FK = '100005' )    " +
						" 	GROUP BY LNS_DB.DVKD_FK, LNS_DBSP.THANG, LNS_DBSP.SANPHAM_FK " +
						" ) " +
						" A ";
		
		System.out.println(query);
		ResultSet rs = this.db.get(query);
		
		rs.next();
		
		int count = rs.getInt("NUM");
		System.out.println("count: " + count);
		rs.close();
		
		/*query = 	"SELECT MKT.PK_SEQ AS MKTID, MKT.MA, LNS_DB.DVKD_FK, LNS_DBSP.THANG, SUM(ISNULL(LNS_DBSP.DUKIENBAN,0)*ISNULL(BG_SP.GIABAN,0)) AS NUM " +
					"FROM ERP_LAPNGANSACH_DUBAO LNS_DB " +
					"INNER JOIN ERP_LAPNGANSACH_DUBAOSANPHAM LNS_DBSP ON LNS_DBSP.DUBAO_FK = LNS_DB.PK_SEQ " +
					"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = LNS_DBSP.SANPHAM_FK AND SP.DVKD_FK = LNS_DB.DVKD_FK " +
					"INNER JOIN ERP_MAKETOAN MKT ON MKT.PK_SEQ = SP.MAKETOAN_FK " +
					"LEFT JOIN ERP_LNSBANGGIABAN_KH BG_KH ON BG_KH.KH_FK =  LNS_DBSP.KHACHHANG_FK " +
					"LEFT JOIN ERP_LNSBANGGIABAN BG ON BG.PK_SEQ = BG_KH.BANGGIABAN_FK " +
					"LEFT JOIN ERP_LNSBGBAN_SANPHAM BG_SP ON BG_SP.BGBAN_FK = BG.PK_SEQ AND BG_SP.SANPHAM_FK = LNS_DBSP.SANPHAM_FK AND BG_SP.TRANGTHAI = 1 " +						
					"WHERE	LNS_DB.LAPNGANSACH_FK = " + this.Id + " AND LNS_DB.DVKD_FK = '100000' AND SP.LOAISANPHAM_FK = '100005' " +
					"AND LNS_DB.CONGTY_FK = " + this.ctyId + "  " +
					"GROUP BY LNS_DB.DVKD_FK,  MKT.PK_SEQ, MKT.MA, LNS_DBSP.THANG " +
					"ORDER BY MKT.MA ";*/
		
		query = "SELECT LNS_DBSP.SANPHAM_FK AS MKTID, LNS_DBSP.SANPHAM_FK as MA, LNS_DB.DVKD_FK, LNS_DBSP.THANG, ISNULL( SUM( isnull(LNS_DBSP.DUKIENBAN, 0) * isnull(LNS_DBSP.DONGIA, 0) ), 0) AS NUM  " +
				" FROM ERP_LAPNGANSACH_DUBAO LNS_DB  " +
				" INNER JOIN ERP_LAPNGANSACH_DUBAOSANPHAM LNS_DBSP ON LNS_DBSP.DUBAO_FK = LNS_DB.PK_SEQ  " +
				" WHERE	LNS_DB.LAPNGANSACH_FK =  '" + this.Id + "'  AND LNS_DB.CONGTY_FK =  '100005'  " +
				" 		and LNS_DBSP.SANPHAM_FK in ( select SANPHAM_FK from ERP_SANPHAM where DVKD_FK = '100000' and LOAISANPHAM_FK = '100005' )    " +
				" GROUP BY LNS_DB.DVKD_FK, LNS_DBSP.THANG, LNS_DBSP.SANPHAM_FK " +
				" ORDER BY LNS_DBSP.SANPHAM_FK";
		
		data = prepareData_MKT(count, query);
		return data;
		
		}catch(java.sql.SQLException e){ return null;}
	
	}
	
	public String[][] PL_SalesAmount_PA_Details(){
		String[][] data;
		try{
		String query = 	"SELECT COUNT(A.MA) AS NUM FROM " +
						"(SELECT DISTINCT MKT.MA " +
						"FROM ERP_LAPNGANSACH_DUBAO LNS_DB " +
						"INNER JOIN ERP_LAPNGANSACH_DUBAOSANPHAM LNS_DBSP ON LNS_DBSP.DUBAO_FK = LNS_DB.PK_SEQ " +
						"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = LNS_DBSP.SANPHAM_FK AND SP.DVKD_FK = LNS_DB.DVKD_FK " +
						"INNER JOIN ERP_MAKETOAN MKT ON MKT.PK_SEQ = SP.MAKETOAN_FK " +
						"LEFT JOIN ERP_LNSBANGGIABAN_KH BG_KH ON BG_KH.KH_FK =  LNS_DBSP.KHACHHANG_FK " +
						"LEFT JOIN ERP_LNSBANGGIABAN BG ON BG.PK_SEQ = BG_KH.BANGGIABAN_FK " +
						"LEFT JOIN ERP_LNSBGBAN_SANPHAM BG_SP ON BG_SP.BGBAN_FK = BG.PK_SEQ AND BG_SP.SANPHAM_FK = LNS_DBSP.SANPHAM_FK AND BG_SP.TRANGTHAI = 1 " +						
						"WHERE	LNS_DB.LAPNGANSACH_FK = " + this.Id + " AND LNS_DB.DVKD_FK = '100004' AND SP.LOAISANPHAM_FK = '100005' " +
						"AND LNS_DB.CONGTY_FK = " + this.ctyId + "  " +
						"GROUP BY LNS_DB.DVKD_FK,  MKT.PK_SEQ, MKT.MA, LNS_DBSP.THANG " +
						")A ";
		
		System.out.println(query);
		ResultSet rs = this.db.get(query);
		
		rs.next();
		
		int count = rs.getInt("NUM");
		System.out.println("count: " + count);
		rs.close();
		
		query = 	"SELECT MKT.PK_SEQ AS MKTID, MKT.MA, LNS_DB.DVKD_FK, LNS_DBSP.THANG, SUM(ISNULL(LNS_DBSP.DUKIENBAN,0)*ISNULL(BG_SP.GIABAN,0)) AS NUM " +
					"FROM ERP_LAPNGANSACH_DUBAO LNS_DB " +
					"INNER JOIN ERP_LAPNGANSACH_DUBAOSANPHAM LNS_DBSP ON LNS_DBSP.DUBAO_FK = LNS_DB.PK_SEQ " +
					"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = LNS_DBSP.SANPHAM_FK AND SP.DVKD_FK = LNS_DB.DVKD_FK " +
					"INNER JOIN ERP_MAKETOAN MKT ON MKT.PK_SEQ = SP.MAKETOAN_FK " +
					"LEFT JOIN ERP_LNSBANGGIABAN_KH BG_KH ON BG_KH.KH_FK =  LNS_DBSP.KHACHHANG_FK " +
					"LEFT JOIN ERP_LNSBANGGIABAN BG ON BG.PK_SEQ = BG_KH.BANGGIABAN_FK " +
					"LEFT JOIN ERP_LNSBGBAN_SANPHAM BG_SP ON BG_SP.BGBAN_FK = BG.PK_SEQ AND BG_SP.SANPHAM_FK = LNS_DBSP.SANPHAM_FK AND BG_SP.TRANGTHAI = 1 " +						
					"WHERE	LNS_DB.LAPNGANSACH_FK = " + this.Id + " AND LNS_DB.DVKD_FK = '100004' AND SP.LOAISANPHAM_FK = '100005' " +
					"AND LNS_DB.CONGTY_FK = " + this.ctyId + "  " +
					"GROUP BY LNS_DB.DVKD_FK,  MKT.PK_SEQ, MKT.MA, LNS_DBSP.THANG " +
					"ORDER BY MKT.MA ";
		
		data = prepareData_MKT(count, query);
		return data;
		
		}catch(java.sql.SQLException e){ return null;}
	
	}

	public String[] PL_SalesVolumn_PA(){
		String[] data = new String[12];
		String query = 	"SELECT LNS_DB.DVKD_FK, LNS_DBSP.THANG, ISNULL(SUM(LNS_DBSP.DUKIENBAN), 0) AS NUM " +
						"FROM ERP_LAPNGANSACH_DUBAO LNS_DB " +
						"INNER JOIN ERP_LAPNGANSACH_DUBAOSANPHAM LNS_DBSP ON LNS_DBSP.DUBAO_FK = LNS_DB.PK_SEQ " +
						"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = LNS_DBSP.SANPHAM_FK AND SP.DVKD_FK = LNS_DB.DVKD_FK " +
						"WHERE	LNS_DB.LAPNGANSACH_FK = " + this.Id + " AND LNS_DB.DVKD_FK = '100004' AND LNS_DB.CONGTY_FK = " + this.ctyId + "  " +
						"GROUP BY LNS_DB.DVKD_FK, LNS_DBSP.THANG " +
						"ORDER BY LNS_DB.DVKD_FK ";
 
		System.out.println("11.LOI: " + query);
		data = prepareData(data, query);
		
		return data;
	}

	public String[] PL_MaterialConsume(){
		String[] data = new String[12];
		/*String query  = "SELECT LSX.THANG, SUM(ISNULL(YEUCAU,0)*ISNULL(BGNL.GIAMUA, 0)) AS NUM " +
						"FROM ERP_LAPNGANSACH_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU NL " +
						"INNER JOIN ERP_LAPNGANSACH_LENHSANXUATDUKIEN LSX ON LSX.PK_SEQ = NL.LENHSANXUATDUKIEN_FK " +
						"INNER JOIN ERP_LAPNGANSACH_DUBAO LNS_DB ON LNS_DB.LAPNGANSACH_FK = LSX.LAPNGANSACH_FK AND LNS_DB.DVKD_FK = LSX.DVKD_FK " +
						"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = NL.NGUYENLIEU_FK " +
						"INNER JOIN ERP_LOAISANPHAM LSP ON LSP.PK_SEQ = SP.LOAISANPHAM_FK " +
						"INNER JOIN ERP_LNSBGBAN_NGUYENLIEU BGNL ON BGNL.SANPHAM_FK = SP.PK_SEQ " +
						"WHERE LNS_DB.LAPNGANSACH_FK = " + this.Id + " " +
						"GROUP BY LSX.THANG " +
						"ORDER BY LSX.THANG ASC"; */
		
		String query =  " SELECT LSX.THANG, SUM(ISNULL(YEUCAU,0)*ISNULL(BGNL.GIAMUA, 0)) AS NUM  " +
						" FROM ERP_LAPNGANSACH_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU NL  " +
						" 	INNER JOIN ERP_LAPNGANSACH_LENHSANXUATDUKIEN LSX ON LSX.PK_SEQ = NL.LENHSANXUATDUKIEN_FK  " +
						" 	INNER JOIN ERP_LNSBGBAN_NGUYENLIEU BGNL ON BGNL.SANPHAM_FK = NL.NGUYENLIEU_FK  " +
						" WHERE LSX.LAPNGANSACH_FK =  '" + this.Id + "' " +
						" GROUP BY LSX.THANG  " +
						" ORDER BY LSX.THANG ASC ";
		
		System.out.println("4.MATERIALS CONSUMED: " + query);
		data = prepareData(data, query);
		return data;
	}

	public String[] PL_MaterialConsume_LA(){
		String[] data = new String[12];
		/*String query  = "SELECT LSX.THANG, SUM(ISNULL(YEUCAU,0)*ISNULL(BGNL.GIAMUA, 0)) AS NUM " +
						"FROM ERP_LAPNGANSACH_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU NL " +
						"INNER JOIN ERP_LAPNGANSACH_LENHSANXUATDUKIEN LSX ON LSX.PK_SEQ = NL.LENHSANXUATDUKIEN_FK " +
						"INNER JOIN ERP_LAPNGANSACH_DUBAO LNS_DB ON LNS_DB.LAPNGANSACH_FK = LSX.LAPNGANSACH_FK AND LNS_DB.DVKD_FK = LSX.DVKD_FK " +
						"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = NL.NGUYENLIEU_FK " +
						"INNER JOIN ERP_LOAISANPHAM LSP ON LSP.PK_SEQ = SP.LOAISANPHAM_FK " +
						"INNER JOIN ERP_LNSBGBAN_NGUYENLIEU BGNL ON BGNL.SANPHAM_FK = SP.PK_SEQ " +
						"WHERE LNS_DB.DVKD_FK ='100000' AND LNS_DB.LAPNGANSACH_FK = " + this.Id + " " +
						"GROUP BY LSX.THANG " +
						"ORDER BY LSX.THANG ASC"; */
		
		String query =  " SELECT LSX.THANG, SUM(ISNULL(YEUCAU,0)*ISNULL(BGNL.GIAMUA, 0)) AS NUM  " +
						" FROM ERP_LAPNGANSACH_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU NL  " +
						" 	INNER JOIN ERP_LAPNGANSACH_LENHSANXUATDUKIEN LSX ON LSX.PK_SEQ = NL.LENHSANXUATDUKIEN_FK  " +
						" 	INNER JOIN ERP_LNSBGBAN_NGUYENLIEU BGNL ON BGNL.SANPHAM_FK = NL.NGUYENLIEU_FK  " +
						" WHERE LSX.LAPNGANSACH_FK =  '" + this.Id + "' and NL.NGUYENLIEU_FK in ( select MA from ERP_SANPHAM where DVKD_FK = '100000' ) " +
						" GROUP BY LSX.THANG  " +
						" ORDER BY LSX.THANG ASC ";
		
		data = prepareData(data, query);
		return data;
	}

	public String[] PL_MaterialConsume_PA(){
		String[] data = new String[12];
		/*String query  = "SELECT LSX.THANG, SUM(ISNULL(YEUCAU,0)*ISNULL(BGNL.GIAMUA, 0)) AS NUM " +
						"FROM ERP_LAPNGANSACH_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU NL " +
						"INNER JOIN ERP_LAPNGANSACH_LENHSANXUATDUKIEN LSX ON LSX.PK_SEQ = NL.LENHSANXUATDUKIEN_FK " +
						"INNER JOIN ERP_LAPNGANSACH_DUBAO LNS_DB ON LNS_DB.LAPNGANSACH_FK = LSX.LAPNGANSACH_FK AND LNS_DB.DVKD_FK = LSX.DVKD_FK " +
						"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = NL.NGUYENLIEU_FK " +
						"INNER JOIN ERP_LOAISANPHAM LSP ON LSP.PK_SEQ = SP.LOAISANPHAM_FK " +
						"INNER JOIN ERP_LNSBGBAN_NGUYENLIEU BGNL ON BGNL.SANPHAM_FK = SP.PK_SEQ " +
						"WHERE LNS_DB.DVKD_FK ='100004' AND LNS_DB.LAPNGANSACH_FK = " + this.Id + " " +
						"GROUP BY LSX.THANG " +
						"ORDER BY LSX.THANG ASC"; */
		
		String query =  " SELECT LSX.THANG, SUM(ISNULL(YEUCAU,0)*ISNULL(BGNL.GIAMUA, 0)) AS NUM  " +
						" FROM ERP_LAPNGANSACH_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU NL  " +
						" 	INNER JOIN ERP_LAPNGANSACH_LENHSANXUATDUKIEN LSX ON LSX.PK_SEQ = NL.LENHSANXUATDUKIEN_FK  " +
						" 	INNER JOIN ERP_LNSBGBAN_NGUYENLIEU BGNL ON BGNL.SANPHAM_FK = NL.NGUYENLIEU_FK  " +
						" WHERE LSX.LAPNGANSACH_FK =  '" + this.Id + "' and NL.NGUYENLIEU_FK in ( select MA from ERP_SANPHAM where DVKD_FK = '100004' ) " +
						" GROUP BY LSX.THANG  " +
						" ORDER BY LSX.THANG ASC ";
		
		data = prepareData(data, query);
		return data;
	}

	public String[] PL_MaterialConsume_Paper(){
		String[] data = new String[12];
	
		/*String query = 	"SELECT LSX.THANG, SUM(ISNULL(YEUCAU,0)*ISNULL(BGNL.GIAMUA, 0)) AS NUM " +
						"FROM ERP_LAPNGANSACH_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU NL " +
						"INNER JOIN ERP_LAPNGANSACH_LENHSANXUATDUKIEN LSX ON LSX.PK_SEQ = NL.LENHSANXUATDUKIEN_FK " +
						"INNER JOIN ERP_LAPNGANSACH_DUBAO LNS_DB ON LNS_DB.LAPNGANSACH_FK = LSX.LAPNGANSACH_FK AND LNS_DB.DVKD_FK = LSX.DVKD_FK " +
						"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = NL.NGUYENLIEU_FK " +
						"INNER JOIN ERP_LOAISANPHAM LSP ON LSP.PK_SEQ = SP.LOAISANPHAM_FK " +
						"INNER JOIN ERP_LNSBGBAN_NGUYENLIEU BGNL ON BGNL.SANPHAM_FK = SP.PK_SEQ " +
						"WHERE LSP.PK_SEQ in ( 100013 ) AND LNS_DB.LAPNGANSACH_FK = " + this.Id + " " +
						"GROUP BY LSX.THANG " +
						"ORDER BY LSX.THANG ASC" ;*/
		
		String query =  " SELECT LSX.THANG, SUM(ISNULL(YEUCAU,0)*ISNULL(BGNL.GIAMUA, 0)) AS NUM  " +
						" FROM ERP_LAPNGANSACH_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU NL  " +
						" 	INNER JOIN ERP_LAPNGANSACH_LENHSANXUATDUKIEN LSX ON LSX.PK_SEQ = NL.LENHSANXUATDUKIEN_FK  " +
						" 	INNER JOIN ERP_LNSBGBAN_NGUYENLIEU BGNL ON BGNL.SANPHAM_FK = NL.NGUYENLIEU_FK  " +
						" WHERE LSX.LAPNGANSACH_FK =  '" + this.Id + "' and NL.NGUYENLIEU_FK in ( select MA from ERP_SANPHAM where LOAISANPHAM_FK = '100013' ) " +
						" GROUP BY LSX.THANG  " +
						" ORDER BY LSX.THANG ASC ";
		
		System.out.println("5." + query);
		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_MaterialConsume_Paper_LA(){
		String[] data = new String[12];
	
		/*String query = 	"SELECT LSX.THANG, SUM(ISNULL(YEUCAU,0)*ISNULL(BGNL.GIAMUA, 0)) AS NUM " +
						"FROM ERP_LAPNGANSACH_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU NL " +
						"INNER JOIN ERP_LAPNGANSACH_LENHSANXUATDUKIEN LSX ON LSX.PK_SEQ = NL.LENHSANXUATDUKIEN_FK " +
						"INNER JOIN ERP_LAPNGANSACH_DUBAO LNS_DB ON LNS_DB.LAPNGANSACH_FK = LSX.LAPNGANSACH_FK AND LNS_DB.DVKD_FK = LSX.DVKD_FK " +						
						"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = NL.NGUYENLIEU_FK " +
						"INNER JOIN ERP_LOAISANPHAM LSP ON LSP.PK_SEQ = SP.LOAISANPHAM_FK " +
						"INNER JOIN ERP_LNSBGBAN_NGUYENLIEU BGNL ON BGNL.SANPHAM_FK = SP.PK_SEQ " +
						"WHERE LSP.PK_SEQ in ( 100013 ) AND LNS_DB.DVKD_FK ='100000' AND LNS_DB.LAPNGANSACH_FK = " + this.Id + " " +
						"GROUP BY LSX.THANG " +
						"ORDER BY LSX.THANG ASC" ;*/
		
		String query =  " SELECT LSX.THANG, SUM(ISNULL(YEUCAU,0)*ISNULL(BGNL.GIAMUA, 0)) AS NUM  " +
						" FROM ERP_LAPNGANSACH_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU NL  " +
						" 	INNER JOIN ERP_LAPNGANSACH_LENHSANXUATDUKIEN LSX ON LSX.PK_SEQ = NL.LENHSANXUATDUKIEN_FK  " +
						" 	INNER JOIN ERP_LNSBGBAN_NGUYENLIEU BGNL ON BGNL.SANPHAM_FK = NL.NGUYENLIEU_FK  " +
						" WHERE LSX.LAPNGANSACH_FK =  '" + this.Id + "' " +
								" and NL.NGUYENLIEU_FK in ( select MA from ERP_SANPHAM where LOAISANPHAM_FK = '100013' ) " +
								" and NL.NGUYENLIEU_FK in ( select MA from ERP_SANPHAM where DVKD_FK = '100000' ) " +
						" GROUP BY LSX.THANG  " +
						" ORDER BY LSX.THANG ASC ";

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_MaterialConsume_Paper_PA(){
		String[] data = new String[12];
	
		/*String query = 	"SELECT LSX.THANG, SUM(ISNULL(YEUCAU,0)*ISNULL(BGNL.GIAMUA, 0)) AS NUM " +
						"FROM ERP_LAPNGANSACH_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU NL " +
						"INNER JOIN ERP_LAPNGANSACH_LENHSANXUATDUKIEN LSX ON LSX.PK_SEQ = NL.LENHSANXUATDUKIEN_FK " +
						"INNER JOIN ERP_LAPNGANSACH_DUBAO LNS_DB ON LNS_DB.LAPNGANSACH_FK = LSX.LAPNGANSACH_FK AND LNS_DB.DVKD_FK = LSX.DVKD_FK " +						
						"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = NL.NGUYENLIEU_FK " +
						"INNER JOIN ERP_LOAISANPHAM LSP ON LSP.PK_SEQ = SP.LOAISANPHAM_FK " +
						"INNER JOIN ERP_LNSBGBAN_NGUYENLIEU BGNL ON BGNL.SANPHAM_FK = SP.PK_SEQ " +
						"WHERE LSP.PK_SEQ in ( 100013 ) AND LNS_DB.DVKD_FK ='100004' AND LNS_DB.LAPNGANSACH_FK = " + this.Id + " " +
						"GROUP BY LSX.THANG " +
						"ORDER BY LSX.THANG ASC" ;*/
		
		String query =  " SELECT LSX.THANG, SUM(ISNULL(YEUCAU,0)*ISNULL(BGNL.GIAMUA, 0)) AS NUM  " +
						" FROM ERP_LAPNGANSACH_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU NL  " +
						" 	INNER JOIN ERP_LAPNGANSACH_LENHSANXUATDUKIEN LSX ON LSX.PK_SEQ = NL.LENHSANXUATDUKIEN_FK  " +
						" 	INNER JOIN ERP_LNSBGBAN_NGUYENLIEU BGNL ON BGNL.SANPHAM_FK = NL.NGUYENLIEU_FK  " +
						" WHERE LSX.LAPNGANSACH_FK =  '" + this.Id + "' " +
								" and NL.NGUYENLIEU_FK in ( select MA from ERP_SANPHAM where LOAISANPHAM_FK = '100013' ) " +
								" and NL.NGUYENLIEU_FK in ( select MA from ERP_SANPHAM where DVKD_FK = '100004' ) " +
						" GROUP BY LSX.THANG  " +
						" ORDER BY LSX.THANG ASC ";

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_MaterialConsume_Foil(){
		String[] data = new String[12];
	
		/*String query = 	"SELECT LSX.THANG, SUM(ISNULL(YEUCAU,0)*ISNULL(BGNL.GIAMUA, 0)) AS NUM " +
						"FROM ERP_LAPNGANSACH_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU NL " +
						"INNER JOIN ERP_LAPNGANSACH_LENHSANXUATDUKIEN LSX ON LSX.PK_SEQ = NL.LENHSANXUATDUKIEN_FK " +
						"INNER JOIN ERP_LAPNGANSACH_DUBAO LNS_DB ON LNS_DB.LAPNGANSACH_FK = LSX.LAPNGANSACH_FK AND LNS_DB.DVKD_FK = LSX.DVKD_FK " +
						"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = NL.NGUYENLIEU_FK " +
						"INNER JOIN ERP_LOAISANPHAM LSP ON LSP.PK_SEQ = SP.LOAISANPHAM_FK " +
						"INNER JOIN ERP_LNSBGBAN_NGUYENLIEU BGNL ON BGNL.SANPHAM_FK = SP.PK_SEQ " +
						"WHERE LSP.PK_SEQ = 100014 AND LNS_DB.LAPNGANSACH_FK = " + this.Id + " " +
						"GROUP BY LSX.THANG " +
						"ORDER BY LSX.THANG ASC" ;*/
		
		String query =  " SELECT LSX.THANG, SUM(ISNULL(YEUCAU,0)*ISNULL(BGNL.GIAMUA, 0)) AS NUM  " +
						" FROM ERP_LAPNGANSACH_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU NL  " +
						" 	INNER JOIN ERP_LAPNGANSACH_LENHSANXUATDUKIEN LSX ON LSX.PK_SEQ = NL.LENHSANXUATDUKIEN_FK  " +
						" 	INNER JOIN ERP_LNSBGBAN_NGUYENLIEU BGNL ON BGNL.SANPHAM_FK = NL.NGUYENLIEU_FK  " +
						" WHERE LSX.LAPNGANSACH_FK =  '" + this.Id + "' " +
								" and NL.NGUYENLIEU_FK in ( select MA from ERP_SANPHAM where LOAISANPHAM_FK = '100014' ) " +
						" GROUP BY LSX.THANG  " +
						" ORDER BY LSX.THANG ASC ";

		System.out.println("7." + query);
		data = prepareData(data, query);
		return data;
	}

	public String[] PL_MaterialConsume_Foil_LA(){
		String[] data = new String[12];
	
		/*String query = 	"SELECT LSX.THANG, SUM(ISNULL(YEUCAU,0)*ISNULL(BGNL.GIAMUA, 0)) AS NUM " +
						"FROM ERP_LAPNGANSACH_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU NL " +
						"INNER JOIN ERP_LAPNGANSACH_LENHSANXUATDUKIEN LSX ON LSX.PK_SEQ = NL.LENHSANXUATDUKIEN_FK " +
						"INNER JOIN ERP_LAPNGANSACH_DUBAO LNS_DB ON LNS_DB.LAPNGANSACH_FK = LSX.LAPNGANSACH_FK AND LNS_DB.DVKD_FK = LSX.DVKD_FK " +
						"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = NL.NGUYENLIEU_FK " +
						"INNER JOIN ERP_LOAISANPHAM LSP ON LSP.PK_SEQ = SP.LOAISANPHAM_FK " +
						"INNER JOIN ERP_LNSBGBAN_NGUYENLIEU BGNL ON BGNL.SANPHAM_FK = SP.PK_SEQ " +
						"WHERE LSP.PK_SEQ = 100014 AND LNS_DB.DVKD_FK ='100000' AND LNS_DB.LAPNGANSACH_FK = " + this.Id + " " +
						"GROUP BY LSX.THANG " +
						"ORDER BY LSX.THANG ASC" ;*/
		
		String query =  " SELECT LSX.THANG, SUM(ISNULL(YEUCAU,0)*ISNULL(BGNL.GIAMUA, 0)) AS NUM  " +
						" FROM ERP_LAPNGANSACH_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU NL  " +
						" 	INNER JOIN ERP_LAPNGANSACH_LENHSANXUATDUKIEN LSX ON LSX.PK_SEQ = NL.LENHSANXUATDUKIEN_FK  " +
						" 	INNER JOIN ERP_LNSBGBAN_NGUYENLIEU BGNL ON BGNL.SANPHAM_FK = NL.NGUYENLIEU_FK  " +
						" WHERE LSX.LAPNGANSACH_FK =  '" + this.Id + "' " +
								" and NL.NGUYENLIEU_FK in ( select MA from ERP_SANPHAM where LOAISANPHAM_FK = '100014' ) " +
								" and NL.NGUYENLIEU_FK in ( select MA from ERP_SANPHAM where DVKD_FK = '100000' ) " +
						" GROUP BY LSX.THANG  " +
						" ORDER BY LSX.THANG ASC ";

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_MaterialConsume_Foil_PA(){
		String[] data = new String[12];
	
		/*String query = 	"SELECT LSX.THANG, SUM(ISNULL(YEUCAU,0)*ISNULL(BGNL.GIAMUA, 0)) AS NUM " +
						"FROM ERP_LAPNGANSACH_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU NL " +
						"INNER JOIN ERP_LAPNGANSACH_LENHSANXUATDUKIEN LSX ON LSX.PK_SEQ = NL.LENHSANXUATDUKIEN_FK " +
						"INNER JOIN ERP_LAPNGANSACH_DUBAO LNS_DB ON LNS_DB.LAPNGANSACH_FK = LSX.LAPNGANSACH_FK AND LNS_DB.DVKD_FK = LSX.DVKD_FK " +
						"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = NL.NGUYENLIEU_FK " +
						"INNER JOIN ERP_LOAISANPHAM LSP ON LSP.PK_SEQ = SP.LOAISANPHAM_FK " +
						"INNER JOIN ERP_LNSBGBAN_NGUYENLIEU BGNL ON BGNL.SANPHAM_FK = SP.PK_SEQ " +
						"WHERE LSP.PK_SEQ = 100014 AND LNS_DB.DVKD_FK ='100004' AND LNS_DB.LAPNGANSACH_FK = " + this.Id + " " +
						"GROUP BY LSX.THANG " +
						"ORDER BY LSX.THANG ASC" ;*/
		
		String query =  " SELECT LSX.THANG, SUM(ISNULL(YEUCAU,0)*ISNULL(BGNL.GIAMUA, 0)) AS NUM  " +
						" FROM ERP_LAPNGANSACH_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU NL  " +
						" 	INNER JOIN ERP_LAPNGANSACH_LENHSANXUATDUKIEN LSX ON LSX.PK_SEQ = NL.LENHSANXUATDUKIEN_FK  " +
						" 	INNER JOIN ERP_LNSBGBAN_NGUYENLIEU BGNL ON BGNL.SANPHAM_FK = NL.NGUYENLIEU_FK  " +
						" WHERE LSX.LAPNGANSACH_FK =  '" + this.Id + "' " +
								" and NL.NGUYENLIEU_FK in ( select MA from ERP_SANPHAM where LOAISANPHAM_FK = '100014' ) " +
								" and NL.NGUYENLIEU_FK in ( select MA from ERP_SANPHAM where DVKD_FK = '100004' ) " +
						" GROUP BY LSX.THANG  " +
						" ORDER BY LSX.THANG ASC ";

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_MaterialConsume_Glue(){
		String[] data = new String[12];
	
		/*String query = 	"SELECT LSX.THANG, SUM(ISNULL(YEUCAU,0)*ISNULL(BGNL.GIAMUA, 0)) AS NUM " +
						"FROM ERP_LAPNGANSACH_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU NL " +
						"INNER JOIN ERP_LAPNGANSACH_LENHSANXUATDUKIEN LSX ON LSX.PK_SEQ = NL.LENHSANXUATDUKIEN_FK " +
						"INNER JOIN ERP_LAPNGANSACH_DUBAO LNS_DB ON LNS_DB.LAPNGANSACH_FK = LSX.LAPNGANSACH_FK AND LNS_DB.DVKD_FK = LSX.DVKD_FK " +
						"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = NL.NGUYENLIEU_FK " +
						"INNER JOIN ERP_LOAISANPHAM LSP ON LSP.PK_SEQ = SP.LOAISANPHAM_FK " +
						"INNER JOIN ERP_LNSBGBAN_NGUYENLIEU BGNL ON BGNL.SANPHAM_FK = SP.PK_SEQ " +
						"WHERE LSP.PK_SEQ = 100015 AND LNS_DB.DVKD_FK ='100004' AND LNS_DB.LAPNGANSACH_FK = " + this.Id + " " +
						"GROUP BY LSX.THANG " +
						"ORDER BY LSX.THANG ASC" ;*/
		
		String query =  " SELECT LSX.THANG, SUM(ISNULL(YEUCAU,0)*ISNULL(BGNL.GIAMUA, 0)) AS NUM  " +
						" FROM ERP_LAPNGANSACH_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU NL  " +
						" 	INNER JOIN ERP_LAPNGANSACH_LENHSANXUATDUKIEN LSX ON LSX.PK_SEQ = NL.LENHSANXUATDUKIEN_FK  " +
						" 	INNER JOIN ERP_LNSBGBAN_NGUYENLIEU BGNL ON BGNL.SANPHAM_FK = NL.NGUYENLIEU_FK  " +
						" WHERE LSX.LAPNGANSACH_FK =  '" + this.Id + "' " +
								" and NL.NGUYENLIEU_FK in ( select MA from ERP_SANPHAM where LOAISANPHAM_FK = '100015' ) " +
						" GROUP BY LSX.THANG  " +
						" ORDER BY LSX.THANG ASC ";

		System.out.println("8." + query);
		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_MaterialConsume_Glue_LA(){
		String[] data = new String[12];
	
		/*String query = 	"SELECT LSX.THANG, SUM(ISNULL(YEUCAU,0)*ISNULL(BGNL.GIAMUA, 0)) AS NUM " +
						"FROM ERP_LAPNGANSACH_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU NL " +
						"INNER JOIN ERP_LAPNGANSACH_LENHSANXUATDUKIEN LSX ON LSX.PK_SEQ = NL.LENHSANXUATDUKIEN_FK " +
						"INNER JOIN ERP_LAPNGANSACH_DUBAO LNS_DB ON LNS_DB.LAPNGANSACH_FK = LSX.LAPNGANSACH_FK AND LNS_DB.DVKD_FK = LSX.DVKD_FK " +
						"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = NL.NGUYENLIEU_FK " +
						"INNER JOIN ERP_LOAISANPHAM LSP ON LSP.PK_SEQ = SP.LOAISANPHAM_FK " +
						"INNER JOIN ERP_LNSBGBAN_NGUYENLIEU BGNL ON BGNL.SANPHAM_FK = SP.PK_SEQ " +
						"WHERE LSP.PK_SEQ = 100015 AND LNS_DB.DVKD_FK ='100000' AND LNS_DB.LAPNGANSACH_FK = " + this.Id + " " +
						"GROUP BY LSX.THANG " +
						"ORDER BY LSX.THANG ASC" ;*/
		
		String query =  " SELECT LSX.THANG, SUM(ISNULL(YEUCAU,0)*ISNULL(BGNL.GIAMUA, 0)) AS NUM  " +
						" FROM ERP_LAPNGANSACH_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU NL  " +
						" 	INNER JOIN ERP_LAPNGANSACH_LENHSANXUATDUKIEN LSX ON LSX.PK_SEQ = NL.LENHSANXUATDUKIEN_FK  " +
						" 	INNER JOIN ERP_LNSBGBAN_NGUYENLIEU BGNL ON BGNL.SANPHAM_FK = NL.NGUYENLIEU_FK  " +
						" WHERE LSX.LAPNGANSACH_FK =  '" + this.Id + "' " +
								" and NL.NGUYENLIEU_FK in ( select MA from ERP_SANPHAM where LOAISANPHAM_FK = '100015' ) " +
								" and NL.NGUYENLIEU_FK in ( select MA from ERP_SANPHAM where DVKD_FK = '100000' ) " +
						" GROUP BY LSX.THANG  " +
						" ORDER BY LSX.THANG ASC ";

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_MaterialConsume_Glue_PA(){
		String[] data = new String[12];
	
		/*String query = 	"SELECT LSX.THANG, SUM(ISNULL(YEUCAU,0)*ISNULL(BGNL.GIAMUA, 0)) AS NUM " +
						"FROM ERP_LAPNGANSACH_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU NL " +
						"INNER JOIN ERP_LAPNGANSACH_LENHSANXUATDUKIEN LSX ON LSX.PK_SEQ = NL.LENHSANXUATDUKIEN_FK " +
						"INNER JOIN ERP_LAPNGANSACH_DUBAO LNS_DB ON LNS_DB.LAPNGANSACH_FK = LSX.LAPNGANSACH_FK AND LNS_DB.DVKD_FK = LSX.DVKD_FK " +
						"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = NL.NGUYENLIEU_FK " +
						"INNER JOIN ERP_LOAISANPHAM LSP ON LSP.PK_SEQ = SP.LOAISANPHAM_FK " +
						"INNER JOIN ERP_LNSBGBAN_NGUYENLIEU BGNL ON BGNL.SANPHAM_FK = SP.PK_SEQ " +
						"WHERE LSP.PK_SEQ = 100015 AND LNS_DB.DVKD_FK ='100004' AND LNS_DB.LAPNGANSACH_FK = " + this.Id + " " +
						"GROUP BY LSX.THANG " +
						"ORDER BY LSX.THANG ASC" ;*/
		
		String query =  " SELECT LSX.THANG, SUM(ISNULL(YEUCAU,0)*ISNULL(BGNL.GIAMUA, 0)) AS NUM  " +
						" FROM ERP_LAPNGANSACH_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU NL  " +
						" 	INNER JOIN ERP_LAPNGANSACH_LENHSANXUATDUKIEN LSX ON LSX.PK_SEQ = NL.LENHSANXUATDUKIEN_FK  " +
						" 	INNER JOIN ERP_LNSBGBAN_NGUYENLIEU BGNL ON BGNL.SANPHAM_FK = NL.NGUYENLIEU_FK  " +
						" WHERE LSX.LAPNGANSACH_FK =  '" + this.Id + "' " +
								" and NL.NGUYENLIEU_FK in ( select MA from ERP_SANPHAM where LOAISANPHAM_FK = '100015' ) " +
								" and NL.NGUYENLIEU_FK in ( select MA from ERP_SANPHAM where DVKD_FK = '100004' ) " +
						" GROUP BY LSX.THANG  " +
						" ORDER BY LSX.THANG ASC ";

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_MaterialConsume_Lacquer(){
		String[] data = new String[12];
	
		/*String query = 	"SELECT LSX.THANG, SUM(ISNULL(YEUCAU,0)*ISNULL(BGNL.GIAMUA, 0)) AS NUM " +
						"FROM ERP_LAPNGANSACH_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU NL " +
						"INNER JOIN ERP_LAPNGANSACH_LENHSANXUATDUKIEN LSX ON LSX.PK_SEQ = NL.LENHSANXUATDUKIEN_FK " +
						"INNER JOIN ERP_LAPNGANSACH_DUBAO LNS_DB ON LNS_DB.LAPNGANSACH_FK = LSX.LAPNGANSACH_FK AND LNS_DB.DVKD_FK = LSX.DVKD_FK " +
						"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = NL.NGUYENLIEU_FK " +
						"INNER JOIN ERP_LOAISANPHAM LSP ON LSP.PK_SEQ = SP.LOAISANPHAM_FK " +
						"INNER JOIN ERP_LNSBGBAN_NGUYENLIEU BGNL ON BGNL.SANPHAM_FK = SP.PK_SEQ " +
						"WHERE LSP.PK_SEQ = 100016 AND LNS_DB.LAPNGANSACH_FK = " + this.Id + " " +
						"GROUP BY LSX.THANG " +
						"ORDER BY LSX.THANG ASC" ;*/
		
		String query =  " SELECT LSX.THANG, SUM(ISNULL(YEUCAU,0)*ISNULL(BGNL.GIAMUA, 0)) AS NUM  " +
						" FROM ERP_LAPNGANSACH_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU NL  " +
						" 	INNER JOIN ERP_LAPNGANSACH_LENHSANXUATDUKIEN LSX ON LSX.PK_SEQ = NL.LENHSANXUATDUKIEN_FK  " +
						" 	INNER JOIN ERP_LNSBGBAN_NGUYENLIEU BGNL ON BGNL.SANPHAM_FK = NL.NGUYENLIEU_FK  " +
						" WHERE LSX.LAPNGANSACH_FK =  '" + this.Id + "' " +
								" and NL.NGUYENLIEU_FK in ( select MA from ERP_SANPHAM where LOAISANPHAM_FK = '100016' ) " +
						" GROUP BY LSX.THANG  " +
						" ORDER BY LSX.THANG ASC ";

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_MaterialConsume_Lacquer_LA(){
		String[] data = new String[12];
	
		/*String query = 	"SELECT LSX.THANG, SUM(ISNULL(YEUCAU,0)*ISNULL(BGNL.GIAMUA, 0)) AS NUM " +
						"FROM ERP_LAPNGANSACH_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU NL " +
						"INNER JOIN ERP_LAPNGANSACH_LENHSANXUATDUKIEN LSX ON LSX.PK_SEQ = NL.LENHSANXUATDUKIEN_FK " +
						"INNER JOIN ERP_LAPNGANSACH_DUBAO LNS_DB ON LNS_DB.LAPNGANSACH_FK = LSX.LAPNGANSACH_FK AND LNS_DB.DVKD_FK = LSX.DVKD_FK " +
						"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = NL.NGUYENLIEU_FK " +
						"INNER JOIN ERP_LOAISANPHAM LSP ON LSP.PK_SEQ = SP.LOAISANPHAM_FK " +
						"INNER JOIN ERP_LNSBGBAN_NGUYENLIEU BGNL ON BGNL.SANPHAM_FK = SP.PK_SEQ " +
						"WHERE LSP.PK_SEQ = 100016 AND LNS_DB.DVKD_FK ='100000' AND LNS_DB.LAPNGANSACH_FK = " + this.Id + " " +
						"GROUP BY LSX.THANG " +
						"ORDER BY LSX.THANG ASC" ;*/
		
		String query =  " SELECT LSX.THANG, SUM(ISNULL(YEUCAU,0)*ISNULL(BGNL.GIAMUA, 0)) AS NUM  " +
						" FROM ERP_LAPNGANSACH_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU NL  " +
						" 	INNER JOIN ERP_LAPNGANSACH_LENHSANXUATDUKIEN LSX ON LSX.PK_SEQ = NL.LENHSANXUATDUKIEN_FK  " +
						" 	INNER JOIN ERP_LNSBGBAN_NGUYENLIEU BGNL ON BGNL.SANPHAM_FK = NL.NGUYENLIEU_FK  " +
						" WHERE LSX.LAPNGANSACH_FK =  '" + this.Id + "' " +
								" and NL.NGUYENLIEU_FK in ( select MA from ERP_SANPHAM where LOAISANPHAM_FK = '100016' ) " +
								" and NL.NGUYENLIEU_FK in ( select MA from ERP_SANPHAM where DVKD_FK = '100000' ) " +
						" GROUP BY LSX.THANG  " +
						" ORDER BY LSX.THANG ASC ";

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_MaterialConsume_Lacquer_PA(){
		String[] data = new String[12];
	
		/*String query = 	"SELECT LSX.THANG, SUM(ISNULL(YEUCAU,0)*ISNULL(BGNL.GIAMUA, 0)) AS NUM " +
						"FROM ERP_LAPNGANSACH_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU NL " +
						"INNER JOIN ERP_LAPNGANSACH_LENHSANXUATDUKIEN LSX ON LSX.PK_SEQ = NL.LENHSANXUATDUKIEN_FK " +
						"INNER JOIN ERP_LAPNGANSACH_DUBAO LNS_DB ON LNS_DB.LAPNGANSACH_FK = LSX.LAPNGANSACH_FK AND LNS_DB.DVKD_FK = LSX.DVKD_FK " +
						"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = NL.NGUYENLIEU_FK " +
						"INNER JOIN ERP_LOAISANPHAM LSP ON LSP.PK_SEQ = SP.LOAISANPHAM_FK " +
						"INNER JOIN ERP_LNSBGBAN_NGUYENLIEU BGNL ON BGNL.SANPHAM_FK = SP.PK_SEQ " +
						"WHERE LSP.PK_SEQ = 100016 AND LNS_DB.DVKD_FK ='100004' AND LNS_DB.LAPNGANSACH_FK = " + this.Id + " " +
						"GROUP BY LSX.THANG " +
						"ORDER BY LSX.THANG ASC" ;*/
		
		String query =  " SELECT LSX.THANG, SUM(ISNULL(YEUCAU,0)*ISNULL(BGNL.GIAMUA, 0)) AS NUM  " +
						" FROM ERP_LAPNGANSACH_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU NL  " +
						" 	INNER JOIN ERP_LAPNGANSACH_LENHSANXUATDUKIEN LSX ON LSX.PK_SEQ = NL.LENHSANXUATDUKIEN_FK  " +
						" 	INNER JOIN ERP_LNSBGBAN_NGUYENLIEU BGNL ON BGNL.SANPHAM_FK = NL.NGUYENLIEU_FK  " +
						" WHERE LSX.LAPNGANSACH_FK =  '" + this.Id + "' " +
								" and NL.NGUYENLIEU_FK in ( select MA from ERP_SANPHAM where LOAISANPHAM_FK = '100016' ) " +
								" and NL.NGUYENLIEU_FK in ( select MA from ERP_SANPHAM where DVKD_FK = '100004' ) " +
						" GROUP BY LSX.THANG  " +
						" ORDER BY LSX.THANG ASC ";

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_MaterialConsume_Submaterial(){
		String[] data = new String[12];
	
		/*String query = 	"SELECT LSX.THANG, SUM(ISNULL(YEUCAU,0)*ISNULL(BGNL.GIAMUA, 0)) AS NUM " +
						"FROM ERP_LAPNGANSACH_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU NL " +
						"INNER JOIN ERP_LAPNGANSACH_LENHSANXUATDUKIEN LSX ON LSX.PK_SEQ = NL.LENHSANXUATDUKIEN_FK " +
						"INNER JOIN ERP_LAPNGANSACH_DUBAO LNS_DB ON LNS_DB.LAPNGANSACH_FK = LSX.LAPNGANSACH_FK AND LNS_DB.DVKD_FK = LSX.DVKD_FK " +
						"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = NL.NGUYENLIEU_FK " +
						"INNER JOIN ERP_LOAISANPHAM LSP ON LSP.PK_SEQ = SP.LOAISANPHAM_FK " +
						"INNER JOIN ERP_LNSBGBAN_NGUYENLIEU BGNL ON BGNL.SANPHAM_FK = SP.PK_SEQ " +
						"WHERE LSP.PK_SEQ = 100017 AND LNS_DB.LAPNGANSACH_FK = " + this.Id + " " +
						"GROUP BY LSX.THANG " +
						"ORDER BY LSX.THANG ASC" ;*/
		
		String query =  " SELECT LSX.THANG, SUM(ISNULL(YEUCAU,0)*ISNULL(BGNL.GIAMUA, 0)) AS NUM  " +
						" FROM ERP_LAPNGANSACH_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU NL  " +
						" 	INNER JOIN ERP_LAPNGANSACH_LENHSANXUATDUKIEN LSX ON LSX.PK_SEQ = NL.LENHSANXUATDUKIEN_FK  " +
						" 	INNER JOIN ERP_LNSBGBAN_NGUYENLIEU BGNL ON BGNL.SANPHAM_FK = NL.NGUYENLIEU_FK  " +
						" WHERE LSX.LAPNGANSACH_FK =  '" + this.Id + "' " +
								" and NL.NGUYENLIEU_FK in ( select MA from ERP_SANPHAM where LOAISANPHAM_FK = '100017' ) " +
						" GROUP BY LSX.THANG  " +
						" ORDER BY LSX.THANG ASC ";

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_MaterialConsume_Submaterial_LA(){
		String[] data = new String[12];
	
		/*String query = 	"SELECT LSX.THANG, SUM(ISNULL(YEUCAU,0)*ISNULL(BGNL.GIAMUA, 0)) AS NUM " +
						"FROM ERP_LAPNGANSACH_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU NL " +
						"INNER JOIN ERP_LAPNGANSACH_LENHSANXUATDUKIEN LSX ON LSX.PK_SEQ = NL.LENHSANXUATDUKIEN_FK " +
						"INNER JOIN ERP_LAPNGANSACH_DUBAO LNS_DB ON LNS_DB.LAPNGANSACH_FK = LSX.LAPNGANSACH_FK AND LNS_DB.DVKD_FK = LSX.DVKD_FK " +
						"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = NL.NGUYENLIEU_FK " +
						"INNER JOIN ERP_LOAISANPHAM LSP ON LSP.PK_SEQ = SP.LOAISANPHAM_FK " +
						"INNER JOIN ERP_LNSBGBAN_NGUYENLIEU BGNL ON BGNL.SANPHAM_FK = SP.PK_SEQ " +
						"WHERE LSP.PK_SEQ = 100017 AND LNS_DB.DVKD_FK ='100000' AND LNS_DB.LAPNGANSACH_FK = " + this.Id + " " +
						"GROUP BY LSX.THANG " +
						"ORDER BY LSX.THANG ASC" ;*/
		
		String query =  " SELECT LSX.THANG, SUM(ISNULL(YEUCAU,0)*ISNULL(BGNL.GIAMUA, 0)) AS NUM  " +
						" FROM ERP_LAPNGANSACH_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU NL  " +
						" 	INNER JOIN ERP_LAPNGANSACH_LENHSANXUATDUKIEN LSX ON LSX.PK_SEQ = NL.LENHSANXUATDUKIEN_FK  " +
						" 	INNER JOIN ERP_LNSBGBAN_NGUYENLIEU BGNL ON BGNL.SANPHAM_FK = NL.NGUYENLIEU_FK  " +
						" WHERE LSX.LAPNGANSACH_FK =  '" + this.Id + "' " +
								" and NL.NGUYENLIEU_FK in ( select MA from ERP_SANPHAM where LOAISANPHAM_FK = '100017' ) " +
								" and NL.NGUYENLIEU_FK in ( select MA from ERP_SANPHAM where DVKD_FK = '100000' ) " +
						" GROUP BY LSX.THANG  " +
						" ORDER BY LSX.THANG ASC ";

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_MaterialConsume_Submaterial_PA(){
		String[] data = new String[12];
	
		/*String query = 	"SELECT LSX.THANG, SUM(ISNULL(YEUCAU,0)*ISNULL(BGNL.GIAMUA, 0)) AS NUM " +
						"FROM ERP_LAPNGANSACH_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU NL " +
						"INNER JOIN ERP_LAPNGANSACH_LENHSANXUATDUKIEN LSX ON LSX.PK_SEQ = NL.LENHSANXUATDUKIEN_FK " +
						"INNER JOIN ERP_LAPNGANSACH_DUBAO LNS_DB ON LNS_DB.LAPNGANSACH_FK = LSX.LAPNGANSACH_FK AND LNS_DB.DVKD_FK = LSX.DVKD_FK " +
						"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = NL.NGUYENLIEU_FK " +
						"INNER JOIN ERP_LOAISANPHAM LSP ON LSP.PK_SEQ = SP.LOAISANPHAM_FK " +
						"INNER JOIN ERP_LNSBGBAN_NGUYENLIEU BGNL ON BGNL.SANPHAM_FK = SP.PK_SEQ " +
						"WHERE LSP.PK_SEQ = 100017 AND LNS_DB.DVKD_FK ='100004' AND LNS_DB.LAPNGANSACH_FK = " + this.Id + " " +
						"GROUP BY LSX.THANG " +
						"ORDER BY LSX.THANG ASC" ;*/
		
		String query =  " SELECT LSX.THANG, SUM(ISNULL(YEUCAU,0)*ISNULL(BGNL.GIAMUA, 0)) AS NUM  " +
						" FROM ERP_LAPNGANSACH_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU NL  " +
						" 	INNER JOIN ERP_LAPNGANSACH_LENHSANXUATDUKIEN LSX ON LSX.PK_SEQ = NL.LENHSANXUATDUKIEN_FK  " +
						" 	INNER JOIN ERP_LNSBGBAN_NGUYENLIEU BGNL ON BGNL.SANPHAM_FK = NL.NGUYENLIEU_FK  " +
						" WHERE LSX.LAPNGANSACH_FK =  '" + this.Id + "' " +
								" and NL.NGUYENLIEU_FK in ( select MA from ERP_SANPHAM where LOAISANPHAM_FK = '100017' ) " +
								" and NL.NGUYENLIEU_FK in ( select MA from ERP_SANPHAM where DVKD_FK = '100004' ) " +
						" GROUP BY LSX.THANG  " +
						" ORDER BY LSX.THANG ASC ";

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_DirectLabour(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE TTCP_FK = 100560 AND NS.LAPNGANSACH_FK = " + this.Id + " " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_DirectLabour_LA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE TTCP_FK = 100414 AND NS.LAPNGANSACH_FK = " + this.Id + " " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_DirectLabour_PA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100415 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_WorkerSalary(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100540 " +
						"ORDER BY THANG ASC" ;
		
		System.out.println("---PL_WorkerSalary: " + query);
		data = prepareData(data, query);
		return data;
	}

	public String[] PL_WorkerSalary_LA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100412 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_WorkerSalary_PA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100413 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_OvertimeWorkers(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100541 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_OvertimeWorkers_LA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100410 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_OvertimeWorkers_PA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100411" +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_AmortizedWorkers(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100542" +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_AmortizedWorkers_LA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100408 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_AmortizedWorkers_PA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100409 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_SocialInsurance(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100543 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_SocialInsurance_LA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100406 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_SocialInsurance_PA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100407 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_HealthInsurance(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100544 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_HealthInsurance_LA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100404 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_HealthInsurance_PA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100405 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_ProductionOverhead(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100561 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_ProductionOverhead_LA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100423 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_ProductionOverhead_PA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100424 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_WorkerAllowances(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100562 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_WorkerAllowances_LA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100416 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_WorkerAllowances_PA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100418 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_WorkerBonus(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100563 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_WorkerBonus_LA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100417 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_WorkerBonus_PA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100425 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_DepreciationBuilding_Production(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100548 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_DepreciationBuilding_Production_LA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100419 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_DepreciationBuilding_Production_PA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100420 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_DepreciationMachine(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100564 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_DepreciationMachine_LA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100421 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_DepreciationMachine_PA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100422 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_DepreciationMotorVehicle_Production(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100565 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_DepreciationMotorVehicle_Production_LA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100426 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_DepreciationMotorVehicle_Production_PA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100427 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_RentalCharge_Machinery(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100566 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_RentalCharge_Machinery_LA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100428 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_RentalCharge_Machinery_PA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100429 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_ElectricityWater(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100567 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_ElectricityWater_LA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100430 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_ElectricityWater_PA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100431 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_FactoryExpenses(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100568 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_FactoryExpenses_LA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100432 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_FactoryExpenses_PA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100433 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_Printing_Stationery(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100569 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_Printing_Stationery_LA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100434 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_Printing_Stationery_PA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100435 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_Workers_Meals(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100570 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_Workers_Meals_LA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100436 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_Workers_Meals_PA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100437 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_Upkeep_Motorvehicle(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100571 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_Upkeep_Motorvehicle_LA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100438 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_Upkeep_Motorvehicle_PA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100439 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_Upkeep_Machinery(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100572 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_Upkeep_Machinery_LA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100440 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_Upkeep_Machinery_PA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100441 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_TrainingCost(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100573 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_TrainingCost_LA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100442 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_TrainingCost_PA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100443 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_Welfare(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100574 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_Welfare_LA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100444 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_Welfare_PA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100445 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_InsuranceA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100575 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_InsuranceA_LA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100446 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_InsuranceA_PA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100447 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_UpkeepFactory(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100576 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_UpkeepFactory_LA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100448 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_UpkeepFactory_PA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100449 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_Workers_Uniform(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100577 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
		
	public String[] PL_Workers_Uniform_LA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100450 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_Workers_Uniform_PA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100451 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_LandRental(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100578 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_LandRental_LA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100624 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_LandRental_PA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100625 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_Tool_Accessories(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100579 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_Tool_Accessories_LA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100454 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_Tool_Accessories_PA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100455 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_Oil_Machine(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100580 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_Oil_Machine_LA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100456 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_Oil_Machine_PA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100457 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_IndirectLabourSalary(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100581 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_IndirectLabourSalary_LA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100458 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_IndirectLabourSalary_PA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100459 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_Indirect_13th_LabourSalary(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100582 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_Indirect_13th_LabourSalary_LA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100460 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_Indirect_13th_LabourSalary_PA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100461 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_Indirect_LabourBonus(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100583 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_Indirect_LabourBonus_LA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100462 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_Indirect_LabourBonus_PA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100463 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_SellingExpenses(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100589 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_SellingExpenses_LA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100480 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_SellingExpenses_PA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100481 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_Transportation(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100584 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_Transportation_LA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100482 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_Transportation_PA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100483 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_Commission(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100585 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_Commission_LA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100484 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_Commission_PA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100485 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_PromotionExpenses(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100588 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_PromotionExpenses_LA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100486 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_PromotionExpenses_PA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100487 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_AdminOverhead(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100593 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_AdminOverhead_LA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100488 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_AdminOverhead_PA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100489 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_Staff_Allowances(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100594 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_Staff_Allowances_LA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100490 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_Staff_Allowances_PA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100491 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_BankCharges(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100595 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_BankCharges_LA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100492 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_BankCharges_PA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100493 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_StaffsBonus(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100596 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_StaffsBonus_LA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100494 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_StaffsBonus_PA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100495 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_Depreciation_Building_Office(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100545 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_Depreciation_Building_Office_LA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100546 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_Depreciation_Building_Office_PA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100547 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_Depreciation_Equipment_Office(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100549 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_Depreciation_Equipment_Office_LA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100496 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_Depreciation_Equipment_Office_PA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100497 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_Depreciation_Motor_Office(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100597 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_Depreciation_Motor_Office_LA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100498 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_Depreciation_Motor_Office_PA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100499 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_Phone_Fax_Electricity_Office(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100598 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_Phone_Fax_Electricity_Office_LA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100500 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_Phone_Fax_Electricity_Office_PA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100501 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_PublicRelations(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100599 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_PublicRelations_LA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100526 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_PublicRelations_PA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100527 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_Entertainments(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100600 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_Entertainments_LA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100502 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	
	public String[] PL_Entertainments_PA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100503 "  +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_OfficeExpenses(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100601 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_OfficeExpenses_LA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100504 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_OfficeExpenses_PA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100505 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_HandlingCharges(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100602 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_HandlingCharges_LA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100506 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_HandlingCharges_PA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100507 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_Printing_Stationery_Office(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100603 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_Printing_Stationery_Office_LA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100508 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_Printing_Stationery_Office_PA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100509 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_TrainingFees(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100604 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_TrainingFees_LA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100510 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_TrainingFees_PA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100511 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_Trevelling(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100605 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_Trevelling_LA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100512 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_Trevelling_PA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100513 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_Upkeep_Motor_Vehicle_Office(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100606 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_Upkeep_Motor_Vehicle_Office_LA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100514 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_Upkeep_Motor_Vehicle_Office_PA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100515 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_AuditingFees(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100607 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_AuditingFees_LA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100516 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_AuditingFees_PA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100517 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_Insurance_Office(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100608 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_Insurance_Office_LA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100518 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_Insurance_Office_PA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100519 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_StaffUniform(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100609 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_StaffUniform_LA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100520 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_StaffUniform_PA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100521 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_LandRental_Office(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100610 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_LandRental_Office_LA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100452 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_LandRental_Office_PA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100453 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_RD_Expenses(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100611 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_RD_Expenses_LA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100522 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_RD_Expenses_PA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100523 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_ManagementFees(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100612 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_ManagementFees_LA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100524 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_ManagementFees_PA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100525 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_AdminSalary(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100614 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_AdminSalary_LA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100528 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_AdminSalary_PA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100529 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_StaffSalary(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100615 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_StaffSalary_LA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100530 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_StaffSalary_PA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100531 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	
	public String[] PL_StaffOvertime(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100616 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_StaffOvertime_LA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100532 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_StaffOvertime_PA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100533 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	
	public String[] PL_Staff_Amortized_13th_Salary(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100617 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_Staff_Amortized_13th_Salary_LA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100534 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_Staff_Amortized_13th_Salary_PA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100535 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_Staff_SocialInsurance(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100618 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_Staff_SocialInsurance_LA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100536 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_Staff_SocialInsurance_PA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100537 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_Staff_HealthInsurance(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100619 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_Staff_HealthInsurance_LA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100538 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_Staff_HealthInsurance_PA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100539 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_Interest_Income(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMDOANHTHU_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMDOANHTHU TTDT ON TTDT.PK_SEQ = NS.TTDT_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTDT_FK = 100003 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_Interest_Income_LA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMDOANHTHU_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMDOANHTHU TTDT ON TTDT.PK_SEQ = NS.TTDT_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTDT_FK = 100002 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_Interest_Income_PA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMDOANHTHU_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMDOANHTHU TTDT ON TTDT.PK_SEQ = NS.TTDT_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTDT_FK = 100004 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_Gain_Exchange_Reserve(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMDOANHTHU_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMDOANHTHU TTDT ON TTDT.PK_SEQ = NS.TTDT_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTDT_FK = 100005 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_Gain_Exchange_Reserve_LA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMDOANHTHU_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMDOANHTHU TTDT ON TTDT.PK_SEQ = NS.TTDT_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTDT_FK = 100006 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_Gain_Exchange_Reserve_PA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMDOANHTHU_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMDOANHTHU TTDT ON TTDT.PK_SEQ = NS.TTDT_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTDT_FK = 100007 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	
	public String[] PL_Other_Income(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMDOANHTHU_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMDOANHTHU TTDT ON TTDT.PK_SEQ = NS.TTDT_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTDT_FK = 100008 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_Other_Income_LA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMDOANHTHU_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMDOANHTHU TTDT ON TTDT.PK_SEQ = NS.TTDT_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTDT_FK = 100009 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_Other_Income_PA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMDOANHTHU_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMDOANHTHU TTDT ON TTDT.PK_SEQ = NS.TTDT_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTDT_FK = 100010 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_LoanInterest(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100620 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_LoanInterest_LA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100470 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_LoanInterest_PA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100471 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_Loss_Exchange_Reserve(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100621 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_Loss_Exchange_Reserve_LA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100472 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_Loss_Exchange_Reserve_PA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100473 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_OtherExpenses(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100622 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_OtherExpenses_LA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100478 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_OtherExpenses_PA(){
		String[] data = new String[12];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100479 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_OtherActivity(){
		String[] data = new String[12];
		
		String[] tmp1 = this.PL_Other_Income();
		String[] tmp2 = this.PL_OtherExpenses();
		
		for(int i = 0; i < 12; i++){
			data[i] = "" + (Long.parseLong(tmp2[i]) - Long.parseLong(tmp1[i]));			
		}
		return data;
	}

	
	public String[] PL_OtherActivity_LA(){
		String[] data = new String[12];
		
		String[] tmp1 = this.PL_Other_Income_LA();
		String[] tmp2 = this.PL_OtherExpenses_LA();
		
		for(int i = 0; i < 12; i++){
			data[i] = "" + (Long.parseLong(tmp2[i]) - Long.parseLong(tmp1[i]));			
		}
		return data;
	}

	public String[] PL_OtherActivity_PA(){
		String[] data = new String[12];
		
		String[] tmp1 = this.PL_Other_Income_PA();
		String[] tmp2 = this.PL_OtherExpenses_PA();
		
		for(int i = 0; i < 12; i++){
			data[i] = "" + (Long.parseLong(tmp2[i]) - Long.parseLong(tmp1[i]));			
		}
		return data;
	}

	public String[] PL_Financial_Activity(){
		String[] data = new String[12];
		
		String[] tmp1 = this.PL_Interest_Income();
		String[] tmp2 = this.PL_Gain_Exchange_Reserve();
		String[] tmp3 = this.PL_LoanInterest();
		String[] tmp4 = this.PL_Loss_Exchange_Reserve();
		
		for(int i = 0; i < 12; i++){
			data[i] = "" + (Long.parseLong(tmp4[i]) + Long.parseLong(tmp3[i]) - Long.parseLong(tmp2[i]) - Long.parseLong(tmp1[i]));			
		}
		return data;
	}

	public String[] PL_Financial_Activity_LA(){
		String[] data = new String[12];
		
		String[] tmp1 = this.PL_Interest_Income_LA();
		String[] tmp2 = this.PL_Gain_Exchange_Reserve_LA();
		String[] tmp3 = this.PL_LoanInterest_LA();
		String[] tmp4 = this.PL_Loss_Exchange_Reserve_LA();
		
		for(int i = 0; i < 12; i++){
			data[i] = "" + (Long.parseLong(tmp4[i]) + Long.parseLong(tmp3[i]) - Long.parseLong(tmp2[i]) - Long.parseLong(tmp1[i]));			
		}
		return data;
	}

	public String[] PL_Financial_Activity_PA(){
		String[] data = new String[12];
		
		String[] tmp1 = this.PL_Interest_Income_PA();
		String[] tmp2 = this.PL_Gain_Exchange_Reserve_PA();
		String[] tmp3 = this.PL_LoanInterest_PA();
		String[] tmp4 = this.PL_Loss_Exchange_Reserve_PA();
		
		for(int i = 0; i < 12; i++){
			data[i] = "" + (Long.parseLong(tmp4[i]) + Long.parseLong(tmp3[i]) - Long.parseLong(tmp2[i]) - Long.parseLong(tmp1[i]));			
		}
		return data;
	}


	public boolean save_duyetngansach(){
		String query = 	"UPDATE ERP_LAPNGANSACH SET DOANHTHUDUTOAN = '" + this.doanhthu.replace(",", "") + "', GIAVONDUTOAN = '" + this.giavon.replace(",", "") + "' " +
						"WHERE PK_SEQ = " + this.lnsId + " ";
		System.out.println(query);
		if(this.db.update(query)){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean chot_duyetngansach(){
		String query = 	"UPDATE ERP_LAPNGANSACH SET TRANGTHAI = '1' " +
						"WHERE PK_SEQ = " + this.lnsId + " ";
		System.out.println(query);
		if(this.db.update(query)){
			return true;
		}else{
			return false;
		}
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public void closeDB(){
		try{
			if (this.nhan_form_choSelected != null)
				this.nhan_form_choSelected.clear();
			if(this.ncplist != null) this.ncplist.close();
			if(this.namlist != null) this.namlist.close();
			if(this.dvkdRs != null) this.dvkdRs.close();			
			if(this.cpList != null) this.cpList.close();
			if(this.cdList != null) this.cdList.close();
			if(this.bplist != null) this.bplist.close();
			this.db.shutDown();
		}catch(java.sql.SQLException e){}
	}
}
