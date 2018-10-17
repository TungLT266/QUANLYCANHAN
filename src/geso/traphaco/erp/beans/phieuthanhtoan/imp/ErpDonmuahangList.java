package geso.traphaco.erp.beans.phieuthanhtoan.imp;

import java.sql.ResultSet;
import java.sql.SQLException;

import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.util.*
import geso.traphaco.center.util.*
import geso.traphaco.center.util.*
import geso.traphaco.center.util.*
import geso.traphaco.erp.beans.phieuthanhtoan.IErpDonmuahangList;

public class ErpDonmuahangList extends Phan_Trang implements IErpDonmuahangList 
{
	private static final long serialVersionUID = 1L;
	String congtyId;
	String userId;
	String tungay;
	String denngay;
	
	String dvthId;
	ResultSet dvthRs;
	
	String nccTen;
	String tongtien;
	String msg;
	String task;
	String sodonmuahang;
	String loaisanphamid;
	ResultSet loaisanphamRs;
	
	ResultSet dmhRs;
	
	ResultSet nccRs;    
	String nccIds;
	ResultSet nspRs;
	String nspIds;
	
	ResultSet nguoitaoRs;    
	String nguoitaoIds;
	ResultSet spRs;    
	String spIds;
	
	String isdontrahang;

	String pivot = "0";
	
	private int num;
	private int[] listPages;
	private int currentPages;
	
	dbutils db;
	private Utility util;
	
	public ErpDonmuahangList()
	{
		this.userId = "";
		this.tungay = "";
		this.denngay = "";
		this.dvthId = "";
		this.nccTen = "";
		this.tongtien = "";
		this.task = "";
		this.loaisanphamid="";
		this.msg = "";
		this.nccIds = "";
		this.nspIds = "";
		this.sodonmuahang = "";
		this.isdontrahang = "0";
		this.spIds = "";
		this.nguoitaoIds = "";
		
		currentPages = 1;
		num = 1;
		this.db = new dbutils();
		 util=new Utility();
	}
	
	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}

	public String getTungay()
	{
		return this.tungay;
	}

	public void setTungay(String tungay) 
	{
		this.tungay = tungay;
	}

	public String getDenngay()
	{
		return this.denngay;
	}

	public void setDenngay(String denngay) 
	{
		this.denngay = denngay;
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

	public String getNCC() 
	{
		return this.nccTen;
	}

	public void setNCC(String ncc) 
	{
		this.nccTen = ncc;
	}

	public String getTongtiensauVat() 
	{
		return this.tongtien;
	}

	public void setTongtiensauVat(String ttsauvat)
	{
		this.tongtien = ttsauvat;
	}

	public void setmsg(String msg) 
	{
		this.msg = msg;
	}

	public String getmsg() 
	{
		return this.msg;
	}

	public ResultSet getDmhList() 
	{
		return this.dmhRs;
	}

	public void setDmhList(ResultSet dmhlist) 
	{
		this.dmhRs = dmhlist;
	}

	public void init(String search)
	{
		String query = "";
		if(search.length() <= 0)
		{
			query = " select a.PK_SEQ as dmhId, a.NGAYMUA, b.TEN, c.TEN as nccTen, c.MA as nccMa, " +
					" b.PREFIX + a.PREFIX + CAST(a.PK_SEQ as varchar(20)) as SOCHUNGTU, " +
					" a.TONGTIENAVAT, a.VAT, " +
					" a.TONGTIENBVAT, " +
					" a.TRANGTHAI, a.NGAYSUA, a.NGAYTAO, d.TEN as nguoitao, e.TEN as nguoisua, " +
					" ISNULL(DUYET.DUYET,0) AS DUYET " +
					" from erp_muahang a inner join ERP_DONVITHUCHIEN b on a.DONVITHUCHIEN_FK = b.PK_SEQ inner join ERP_NHACUNGCAP c on a.NHACUNGCAP_FK = c.PK_SEQ " +
					" inner join NHANVIEN d on a.NGUOITAO = d.PK_SEQ inner join NHANVIEN e on a.NGUOISUA = e.PK_SEQ " +
					" left join( " +
					"	SELECT 	MUAHANG_FK AS DMHID, " +
					"			CASE WHEN SUM(QUYETDINH) > 0 " + 
					"			THEN  ( " +
					"				SELECT COUNT(TRANGTHAI) - SUM(TRANGTHAI) " + 
					"				FROM ERP_DUYETMUAHANG  " +
					"				WHERE MUAHANG_FK = DUYETMUAHANG.MUAHANG_FK AND QUYETDINH = 1 " +
					"			) 	" +
					"			ELSE COUNT(TRANGTHAI) - SUM(TRANGTHAI) 	END AS DUYET " + 
					"	FROM ERP_DUYETMUAHANG DUYETMUAHANG " +
					"	GROUP BY MUAHANG_FK " +
					" )DUYET ON DUYET.DMHID = A.PK_SEQ " +
					" where a.congty_fk = '" + this.congtyId + "' and a.type = '" + this.isdontrahang + "' and b.pk_seq in  " +util.quyen_donvithuchien(this.userId);
			
			if(this.task.length() > 0)
					query += " and a.trangthai = 1 ";
			//query += "order by a.NGAYMUA desc, a.trangthai asc, a.pk_seq desc";
		}
		else
			query = search;
	
		System.out.println("Query init 111 232 : " + query);
		
		this.dmhRs = createSplittingData(50, 10, "NGAYMUA desc, trangthai asc, dmhId desc", query);
		query="select pk_seq, ten from ERP_DONVITHUCHIEN where congty_fk = '" + this.congtyId + "' and pk_seq in "+util.quyen_donvithuchien(this.userId);
		this.dvthRs = db.get(query);
		this.loaisanphamRs=db.get("select pk_seq, ten from ERP_LOAISANPHAM");
	}

	
	public void DBclose() 
	{
		try 
		{
			if(this.dvthRs != null) 
				this.dvthRs.close();
			
			if(this.dmhRs != null) 
				this.dmhRs.close(); 
			
			if(this.nccRs != null) 
				this.nccRs.close(); 
			
			if(this.nspRs != null) 
				this.nspRs.close(); 
		}
		catch (SQLException e) {}
		this.db.shutDown();
	}

	public String getTask()
	{
		return this.task;
	}

	public void setTask(String task)
	{
		this.task = task;
	}
	
	public int getNum(){
		return this.num;
	}
	public void setNum(int num){
		this.num = num;
		listPages = PhanTrang.getListPages(num);

	}

	
	public int getCurrentPage() {
		return this.currentPages;
	}

	
	public void setCurrentPage(int current) {
		this.currentPages = current;
	}

	
	public int[] getListPages() {
		return this.listPages;
	}

	
	public void setListPages(int[] listPages) {
		this.listPages = listPages;
	}

	
	public int getLastPage() {
		ResultSet rs = db.get("select count(*) as c from ERP_MUAHANG");
		return PhanTrang.getLastPage(rs);
	}

	
	public int[] getNewPagesList(String action, int num, int currentPage, int theLastPage, String[] listPage) {
		IPhanTrang pt = new PhanTrang();
		return pt.getNewPagesList(action, num, currentPage, theLastPage, listPage);
	}

	public ResultSet getNccRs() 
	{
		return this.nccRs;
	}

	public void setNccRs(ResultSet nccRs) 
	{
		this.nccRs = nccRs;
	}

	public void setNccIds(String nccIds) 
	{
		this.nccIds = nccIds;
	}

	public String getNccIds() 
	{
		return this.nccIds;
	}

	public ResultSet getNspRs() 
	{
		return this.nspRs;
	}

	public void setNspRs(ResultSet nspRs) 
	{
		this.nspRs = nspRs;
	}

	public void setNspIds(String nspIds)
	{
		this.nspIds = nspIds;
	}

	public String getNspIds()
	{
		return this.nspIds;
	}

	public void initBaoCao() 
	{
		this.dvthRs = db.get("select pk_seq, ten from ERP_DONVITHUCHIEN where pk_seq in  "+ util.quyen_donvithuchien(this.userId));
		this.nccRs = db.get("select pk_seq, ma as nccMa, ten as nccTen from erp_nhacungcap where pk_seq in  " + util.quyen_nhacungcap(this.userId));
		this.nspRs = db.get("select PK_SEQ, TEN, DIENGIAI from NHOMSANPHAM where loainhom = '1'");
		
		
		String query1=" select distinct a.PK_SEQ, a.DANGNHAP + ', ' + a.TEN as TEN from NHANVIEN a  inner join  NHANVIEN_DONVITHUCHIEN b on a.PK_SEQ=b.NHANVIEN_FK " +
				     " where TRANGTHAI = '1'";
		if(this.dvthId.trim().length()>0)
			query1 += " and  b.DONVITHUCHIEN_FK='"+this.dvthId+"' ";
		this.nguoitaoRs = db.get(query1);
		
		
		this.loaisanphamRs = db.get("select PK_SEQ, MA + ', ' + TEN as TEN from ERP_LOAISANPHAM where TRANGTHAI = '1'");
		
		if(this.loaisanphamid.trim().length() > 0)
		{
			String query = "select PK_SEQ, case when LEN(ISNULL(MA, '')) <= 0 then MA else MA end as MA, TEN + ' ' + isnull(QUYCACH, '') as TEN  " +
							"from ERP_SANPHAM where pk_seq > 0 ";
			if(this.loaisanphamid.trim().length() > 0)
				query += " and loaisanpham_fk = '" +  this.loaisanphamid+ "' ";
			this.spRs = db.get(query);
		}
		
		
	}

	public String getSodonmuahang()
	{
		return this.sodonmuahang;
	}

	public void setSodonmuahang(String sodonmuahang) 
	{
		this.sodonmuahang = sodonmuahang;
	}

	
	public ResultSet getLoaisanpham() 
	{
		
		return this.loaisanphamRs;
	}

	
	public void setLoaisanpham(ResultSet loaisanpham) 
	{
		
		this.loaisanphamRs=loaisanpham;
	}

	
	public String getLoaisanphamid() {
		
		return this.loaisanphamid;
	}

	
	public void setLoaisanphamid(String loaisanpham) {
		
		this.loaisanphamid=loaisanpham;
	}

	public String getIsdontrahang() 
	{
		return this.isdontrahang;
	}

	public void setIsdontrahang(String dontrahang) 
	{
		this.isdontrahang = dontrahang;
	}

	public String getCongtyId()
	{
		return this.congtyId;
	}

	public void setCongtyId(String congtyId) 
	{
		this.congtyId = congtyId;
	}

	
	public String getCtyId() {
		
		return null;
	}

	
	public void setCtyId(String ctyId) {
		
		
	}

	
	public String getCty() {
		
		return null;
	}

	
	public void setCty(String cty) {
		
		
	}

	
	public ResultSet getNguoitaoRs() {
		
		return this.nguoitaoRs;
	}

	
	public void setNguoitaoRs(ResultSet nccRs) {
		
		this.nguoitaoRs = nccRs;
	}

	
	public void setNguoitaoIds(String nguoitaoIds) {
		
		this.nguoitaoIds = nguoitaoIds;
	}

	
	public String getNguoitaoIds() {
		
		return this.nguoitaoIds;
	}

	
	public ResultSet getSanphamRs() {
		
		return this.spRs;
	}

	
	public void setSanphamRs(ResultSet spRs) {
		
		this.spRs = spRs;
	}

	
	public String getSanphamId() {
		
		return this.spIds;
	}

	
	public void setSanphamId(String spId) {
		
		this.spIds = spId;
	}

	@Override
	public String getPivot() {
		return this.pivot;
	}

	@Override
	public void setPivot(String pivot) {
		this.pivot = pivot;
	}
}
