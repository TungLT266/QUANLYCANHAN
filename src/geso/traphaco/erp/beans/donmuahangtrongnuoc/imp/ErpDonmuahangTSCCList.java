package geso.traphaco.erp.beans.donmuahangtrongnuoc.imp;

import java.sql.ResultSet;
import java.sql.SQLException;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.donmuahangtrongnuoc.IErpDonmuahangTSCCList;

public class ErpDonmuahangTSCCList extends Phan_Trang implements IErpDonmuahangTSCCList 
{
	private static final long serialVersionUID = 1L;
	String congtyId;
	String userId;
	String tungay;
	String denngay;
	String loaiDonMuaHang;
	
	String dvthId;
	ResultSet dvthRs;
	
	String nccTen;
	String tongtien;
	String msg;
	String task;
	String sodonmuahang;
	String loaisanphamid;
	String loaihanghoa = "";
	String sothamchieu="";
	
	ResultSet loaisanphamRs;
	
	ResultSet dmhRs;
	
	ResultSet nccRs;    
	String nccIds;
	ResultSet nspRs;
	String nspIds;
	
	String isdontrahang;
	String trangthai;
	
	ResultSet nguoitaoRs;    
	String nguotaoIds;
	
	String mactsp = "";
	String loai = "";
	
	private int num;
	private int soItems;
	private int[] listPages;
	private int currentPages;
	
	dbutils db;
	private Utility util;
	
	public ErpDonmuahangTSCCList()
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
		// 0 - DONMUA HANG, 1 phieu thanh toan
		this.isdontrahang = "0";
		this.trangthai = "";
		this.nguotaoIds = "";
		this.sothamchieu="";
		this.loaiDonMuaHang = "";
		
		this.loai = "2";
		this.soItems = 25;
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
		
		String sql = "";
		
		if(search.length() <= 0)
		{
			query = " select a.PK_SEQ as dmhId,ISNULL(( select SUM(soluong) from ERP_MUAHANG_SP where MUAHANG_FK = a.PK_SEQ ), 0) AS tongluong, a.NGAYMUA , \n " +
					" isnull((select TEN from ERP_DONVITHUCHIEN where PK_SEQ = a.DONVITHUCHIEN_FK), '') as ten, isnull(c.TEN, '') as nccTen, c.MA as nccMa,  \n " +
					" SOPO as SOCHUNGTU,  \n " +
					" a.TONGTIENAVAT/(	select top(1) tg.TIGIAQUYDOI from ERP_TIGIA  tg " +
									"	where TIENTE_FK = a.TIENTE_FK and tg.TRANGTHAI ='1' order by PK_SEQ desc ) as TONGTIENAVAT" +
					" , a.VAT/(select top(1) tg.TIGIAQUYDOI from ERP_TIGIA  tg " +
									"	where TIENTE_FK = a.TIENTE_FK  and tg.TRANGTHAI ='1' order by PK_SEQ desc ) as VAT,  \n " +
					" a.TONGTIENBVAT/(select top(1) tg.TIGIAQUYDOI from ERP_TIGIA  tg " +
									"	where TIENTE_FK = a.TIENTE_FK  and tg.TRANGTHAI ='1' order by PK_SEQ desc ) as TONGTIENBVAT,  \n " +
					" a.TRANGTHAI, a.NGAYSUA, a.NGAYTAO, d.TEN as nguoitao, e.TEN as nguoisua, isnull(a.DaInPdf, 0) as DaInPdf,   \n " +
					" ISNULL(DUYET.DUYET,0) AS DUYET, ISNULL(tt.ma, 'NA') as tiente, isnull(a.NOTE, '') as NOTE, isnull(a.DACHOT, 0) as DACHOT,  \n " +
					" isnull(c.noibo, 0) as noibo,  \n " +
					" (select count(*) from ERP_HOADONNCC_PHIEUNHAP where MUAHANG_FK = a.pk_seq )as ktPO, a.SOTHAMCHIEU, a.LOAI, a.ISCOHOADON,   \n " +
					" isnull((select (cast(PK_SEQ as nvarchar(50)) +' ' + (case trangthai when 0 then N'Chưa chốt' when 1 then N'Đã chốt' end) ) + ','  from ERP_TAMUNG  WHERE muahang_fk = a.PK_SEQ and trangthai not in (2) for xml path('')),'') DENGHITAMUNG, \n"+
					" isnull(a.isduocphanbo,0) isduocphanbo, a.NHACUNGCAP_FK NCCID, a.tooltip,  \n"+
					" isnull((select sopo from erp_muahang mh inner join erp_phanbomuahang pb on mh.pk_seq = pb.MUAHANG_FK \n"+
					" inner join ERP_PHANBOMUAHANG_PO pbpo on pb.PK_SEQ = pbpo.PHANBO_FK \n"+
					" where pbpo.PODUOCPB = a.PK_SEQ), '') as POgoc";
			
			query+=	" from erp_muahang a  \n " +
					 
					" left join ERP_NHACUNGCAP c on a.NHACUNGCAP_FK = c.PK_SEQ \n " +
					" inner join NHANVIEN d on a.NGUOITAO = d.PK_SEQ inner join NHANVIEN e on a.NGUOISUA = e.PK_SEQ  \n " +
					" left join ERP_TIENTE tt on a.tiente_fk = tt.pk_seq  \n " +
 
					" left join(  \n " +
					"	SELECT 	MUAHANG_FK AS DMHID,  \n " +
					"			CASE WHEN SUM(QUYETDINH) > 0 THEN  \n " +
					"			(CASE WHEN  \n " +
					"			( SELECT SUM(TRANGTHAI)  \n " +
					"			FROM ERP_DUYETMUAHANG   \n " +				
					"			WHERE MUAHANG_FK = DUYETMUAHANG.MUAHANG_FK AND QUYETDINH = 1) > 0 THEN 0  \n " +
					"			ELSE 1  \n " +
					"			END)	 \n " +
					"			ELSE COUNT(TRANGTHAI) - SUM(TRANGTHAI) 	END AS DUYET  \n " + 
					"	FROM ERP_DUYETMUAHANG DUYETMUAHANG  \n " +
					"	GROUP BY MUAHANG_FK  \n " +
					"  )DUYET ON DUYET.DMHID = A.PK_SEQ  \n " +
					" where a.type = '"+ this.isdontrahang +"' and  a.congty_fk = '" + this.congtyId + "' and a.LOAI = 2 ";
			
			if(this.task.length() > 0)
					query += " and a.trangthai = 1  \n ";

		}
		else
			query = search;
	
		System.out.println("Query init 111 : " + query);
		
		String query_init = createSplittingData_ListNew(this.db, soItems, 10, "dmhId desc, NGAYMUA desc", query);
		this.dmhRs = db.get(query_init);
		
		//this.dmhRs = createSplittingData(50, 10, "dmhId desc", query);
		query = "select pk_seq, ten from ERP_DONVITHUCHIEN where trangthai = 1 ";
		this.dvthRs = db.get(query);
		this.loaisanphamRs=db.get("select pk_seq, ten from ERP_LOAISANPHAM where trangthai = '1' ");
		
		query = "select pk_seq, ten from NHANVIEN where trangthai = '1' and pk_seq in ( select distinct NGUOITAO from ERP_MUAHANG ) ";
		this.nguoitaoRs = db.get(query);
		
		// tạo ra resultset NCC
		this.nccRs = db.get(" select PK_SEQ, MA +'-'+ TEN as TEN from ERP_NHACUNGCAP " +
				            " where PK_SEQ in ( select distinct NHACUNGCAP_FK from " +
				            " ERP_DUYETNHACUNGCAP where TRANGTHAI = 1)");
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
		this.nccRs = db.get("select pk_seq, ma as nccMa, ten as nccTen from erp_nhacungcap");
		this.nspRs = db.get("select PK_SEQ, TEN, DIENGIAI from NHOMSANPHAM where loainhom = '1'");
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

	
	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) {
		
		this.trangthai = trangthai;
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

	@Override
	public String getMaCtSp() {
		return this.mactsp;
	}

	@Override
	public void setMaCtSp(String mact) {
		this.mactsp = mact;
	}

	@Override
	public String getLoaihanghoa() {
		return this.loaihanghoa;
	}

	@Override
	public void setLoaihanghoa(String loaihh) {
		this.loaihanghoa = loaihh;
	}


	public String getsothamchieu() {
		
		return this.sothamchieu;
	}


	public void setsothamchieu(String sothamchieu) {
		this.sothamchieu= sothamchieu;
	}


	public String getLoai() 
	{
		return this.loai;
	}

	public void setLoai(String loai) 
	{
		this.loai = loai;
	}@Override
	public void setSoItems(int soItems) {
		
		this.soItems = soItems;
	}
	@Override
	public int getSoItems() {
		
		return this.soItems;
	}

	@Override
	public String getLoaiDonMuaHang() {		
		return this.loaiDonMuaHang;
	}

	@Override
	public void setLoaiDonMuaHang(String loaiDonMuaHang) {
		this.loaiDonMuaHang = loaiDonMuaHang;
	}
}
