package geso.traphaco.erp.beans.lapkehoach.imp;

import java.sql.ResultSet;
import java.sql.SQLException;

import geso.traphaco.erp.beans.lapkehoach.IErpBomList;
import geso.traphaco.erp.db.sql.dbutils;
import geso.dms.center.util.IPhan_Trang;
import geso.dms.center.util.PhanTrang;
import geso.dms.center.util.Phan_Trang;
import geso.dms.center.util.Utility;

public class ErpBomList extends Phan_Trang implements IErpBomList 
{
	String ctyId;
	String userId;
	String tungay;
	String denngay;
	String trangthai; 

	String diengiai;
	String msg;
	String lspid;
	String dvkdId = "";
	String tenbom ;
	String vanbanhuongdan;
	ResultSet bomRs, dvkdRs,lspRs,SpRs;
	String sanpham;
	String ghichu;
	String manguyenlieu;
	ResultSet manguyenlieuRs;
	dbutils db;
	
	private int num;
	private int[] listPages;
	private int currentPages;
	
	public ErpBomList()
	{
		this.ctyId = "";
		this.userId = "";
		this.tungay = "";
		this.denngay = "";
		this.trangthai = "";
		this.diengiai = "";
		this.msg = "";
		this.sanpham="";
		this.lspid="";
		this.tenbom="";
		this.vanbanhuongdan="";
		this.ghichu="";
		this.manguyenlieu="";
		this.db = new dbutils();
		
		currentPages = 1;
		num = 1;
	}
	
	public String getCtyId() 
	{
		return this.ctyId;
	}

	public void setCtyId(String ctyId) 
	{
		this.ctyId = ctyId;	
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

	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}

	public String getDiengiai() 
	{
		return this.diengiai;
	}

	public void setDiengiai(String diengiai) 
	{
		this.diengiai = diengiai;
	}

	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	public void init(String query) 
	{
		String sql = "";
		
		if(query.length() > 0) {
		 	sql = query;	
		}
		else
		{	
			sql = " select distinct ISNULL(ISHOATDONG,0) AS ISHOATDONG,ISNULL(ISDADUYET,0) AS ISDADUYET, " +
				  "a.pk_seq, isnull(a.tenbom,'') as tenbom, isnull(a.vanbanhuongdan,'') as vanbanhuongdan, isnull( (select top(1) isnull(nh.tennhamay,'') from erp_nhamay nh inner join ERP_DANHMUCVATTU_NHAMAY nhdm on nh.pk_seq=nhdm.NHAMAY_FK where nhdm.DANHMUCVATTU_FK=a.PK_SEQ),'') as tennhamay, " +
				  " a.diengiai, isnull( (d.ma + ' - ' + d.ten),'')  as sp, (a.hieuluctu + ' - ' + a.hieulucden) as hieuluc , " +
				  " a.trangthai, b.ten as nguoitao, a.ngaytao, c.ten as nguoisua, a.ngaysua, "
				  + " CASE WHEN ISNULL((select sum(a.DMVT_FK) from ( \n "
				  + " SELECT DISTINCT 1 AS DMVT_FK FROM ERP_MUAHANG_SP WHERE DMVT_FK =a.PK_SEQ and MUAHANG_FK IN (SELECT PK_SEQ FROM ERP_MUAHANG WHERE TRANGTHAI not in (2,3,4))	\n "
				  + " UNION ALL	 \n "
				  + " SELECT DISTINCT 1 AS DMVT_FK FROM ERP_MUAHANG_BOM  WHERE  DANHMUCVATTU_FK =a.PK_SEQ AND MUAHANG_FK IN (SELECT PK_SEQ FROM ERP_MUAHANG WHERE TRANGTHAI not in (2,3,4)) \n "
				  + " UNION ALL \n "
				  + " SELECT DISTINCT 1 AS DMVT_FK FROM ERP_LENHSANXUAT_SANPHAM WHERE  DANHMUCVT_FK =a.PK_SEQ AND LENHSANXUAT_FK  IN (SELECT PK_SEQ FROM ERP_LENHSANXUAT_GIAY WHERE TRANGTHAI not in (6,7))	 )a ),0) =0 THEN 0 ELSE 1 END AS CHECKTONTAI \n " +
				  " from ERP_DANHMUCVATTU a " +
				  " inner join NhanVien b on a.nguoitao = b.pk_seq    " +
				  " inner join nhanvien c on a.nguoisua = c.pk_seq " +
				  " left join erp_sanpham d on a.masanpham = d.ma  " +
				  " where a.congty_fk = " + this.ctyId + " ";
				 
		} 
		
		System.out.println("__Bom : " + sql);
		this.bomRs = createSplittingDataNew(this.db, 50, 10, " pk_seq desc ", sql);
		sql = "SELECT PK_SEQ, DONVIKINHDOANH, DIENGIAI FROM DONVIKINHDOANH WHERE TRANGTHAI = 1 AND CONGTY_FK = " + this.ctyId;
		this.dvkdRs = db.get(sql);
		sql = "select    PK_SEQ, MA + ', ' + TEN as Ten from ERP_LOAISANPHAM where TRANGTHAI = '1'  ";
		this.lspRs = db.get(sql);
		
		sql= " SELECT PK_SEQ ,MA + '' +TEN AS TEN FROM ERP_SANPHAM "; 
		this.SpRs=db.get(sql);
		
		sql= "select pk_seq, ma + ' - ' + ten as ten from ERP_SANPHAM";
		this.manguyenlieuRs=db.get(sql);
	}

	public void DbClose() 
	{
		try 
		{
			if(this.bomRs != null) this.bomRs.close();
			if(this.dvkdRs != null) this.dvkdRs.close();
			
		} 
		catch (SQLException e) {}
		finally
		{
			if(this.db!=null)
				this.db.shutDown();
		}
	}

	public ResultSet getBomRs() 
	{
		return this.bomRs;
	}

	public void setBomRs(ResultSet bomRs) 
	{
		this.bomRs = bomRs;
	}


	public String getSanpham() {
		return this.sanpham;
	}


	public void setSanpham(String _sanpham) {
		this.sanpham=_sanpham;
	}


	public String getDvkdId() {
		return this.dvkdId;
	}


	public void setDvkdId(String dvkdId) {
		this.dvkdId = dvkdId;
	}


	public ResultSet getDvkdRs() {
		return this.dvkdRs;
	}


	public void setDvkdRs(ResultSet rs) {
		this.dvkdRs = rs;
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
		this.listPages= listPages;
		
	}

	public ResultSet getLspRs() {
		return this.lspRs;
	}

	public void setLspRs(ResultSet rs) {
		this.lspRs=rs;
	}


	public String getLspId() {
		return this.lspid;
	}


	public void setLspId(String lspid) {
		this.lspid=lspid;
		
	}

	
	public String getTenBOM() {
		
		return this.tenbom;
	}

	
	public void setTenBOM(String tenbom) {
		this.tenbom=tenbom;
		
	}

	
	public String getVanBanHuongDan() {
		
		return this.vanbanhuongdan;
	}

	
	public void setVanBanHuongDan(String vanbanhuongdan) {
		
		this.vanbanhuongdan=vanbanhuongdan;
	}

	@Override
	public ResultSet getSpRs() {
		// TODO Auto-generated method stub
		return this.SpRs;
	}

	@Override
	public void setSpRs(ResultSet rs) {
		// TODO Auto-generated method stub
		this.SpRs=rs;
	}

	public String getGhichu() {
		return ghichu;
	}

	public void setGhichu(String ghichu) {
		this.ghichu = ghichu;
	}

	public String getManguyenlieu() {
		return manguyenlieu;
	}

	public void setManguyenlieu(String manguyenlieu) {
		this.manguyenlieu = manguyenlieu;
	}

	public ResultSet getManguyenlieuRs() {
		return manguyenlieuRs;
	}

	public void setManguyenlieuRs(ResultSet manguyenlieuRs) {
		this.manguyenlieuRs = manguyenlieuRs;
	}
	
}
