package geso.traphaco.erp.beans.yeucauchuyenkho.imp;

import java.sql.ResultSet;

import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.yeucauchuyenkho.IErpChuyenvitriList;

public class ErpChuyenvitriList extends Phan_Trang implements IErpChuyenvitriList
{
	private static final long serialVersionUID = 1L;
	String userId;
	String tungayTao;
	String denngayTao;

	String masanpham;
	String trangthai;
	String msg;
	String isnhanHang;
	
	String sophieu;
	String ndxuat;
	String khonhanId;
	String khochuyenId;
	String lsxId;
	String lydo;
	String solenh;
	String nguoitao;
	String nguoisua;
	String Sohoadon;
	String solo="";
	

	public String getSolo() {
		return solo;
	}

	public void setSolo(String solo) {
		this.solo = solo;
	}


	String sochungtubn;

	String sochungtu;
	ResultSet lsxRs;

	ResultSet khonhanRs;
	ResultSet khochuyenRs;
	ResultSet ndxRs;
	ResultSet nhanvienRs;
	ResultSet nhanvien2Rs;
	
	private int num;
	private int[] listPages;
	private int currentPages;
	
	dbutils db;
	
	public ErpChuyenvitriList()
	{
		this.tungayTao = "";
		this.denngayTao = "";
		this.trangthai = "";
		this.masanpham = "";
		this.sophieu="";
		this.ndxuat= "";
		this.lydo = "";
		this.khonhanId="";
		this.khochuyenId="";
		this.lsxId="";
		this.nguoitao= "";
		this.nguoisua = "";
		this.msg = "";
		this.solenh="";
		this.isnhanHang = "0";
		currentPages = 1;
		this.sochungtu="";

		this.sochungtubn ="";

		this.Sohoadon="";

		num = 1;
		
		this.db = new dbutils();
	}
	
	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}
	
	public String getTrangthai()
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}
	
	public int getNum()
	{
		return this.num;
	}
	
	public void setNum(int num)
	{
		this.num = num;
		listPages = PhanTrang.getListPages(num);
	}

	public int getCurrentPage()
	{
		return this.currentPages;
	}

	public void setCurrentPage(int current) 
	{
		this.currentPages = current;
	}

	public int[] getListPages() 
	{
		return this.listPages;
	}

	public void setListPages(int[] listPages) 
	{
		this.listPages = listPages;
	}

	public int getLastPage() 
	{
		ResultSet rs = db.get("select count(*) as c from ERP_YEUCAUNGUYENLIEU");
		return PhanTrang.getLastPage(rs);
	}

	public int[] getNewPagesList(String action, int num, int currentPage, int theLastPage, String[] listPage)
	{
		IPhanTrang pt = new PhanTrang();
		return pt.getNewPagesList(action, num, currentPage, theLastPage, listPage);
	}

	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	public void init(String search)
	{
		Utility util = new Utility();
		String query = "";
        
		this.khonhanRs=db.get("select PK_SEQ, TEN from ERP_KHOTT where TrangThai = '1' and pk_seq in "+util.quyen_khott(this.userId) +"  order by loai asc ");
		this.khochuyenRs=db.get("select PK_SEQ, TEN from ERP_KHOTT where TrangThai = '1' and pk_seq in "+util.quyen_khott(this.userId) +"  order by loai asc ");
        
		if(search.length() > 0)
			query = search;
		else
		{
			query = 	" SELECT A.PK_SEQ, A.TRANGTHAI, A.NgayChuyen, KHOTT.TEN as khochuyen, case when bin.pk_seq is null then N'Không có vị trí' else BIN.MA + ' - ' + BIN.TEN end as vitrichuyen, " +
						" 	A.LYDO, NV.TEN AS NGUOITAO, A.NGAYSUA, A.NGAYTAO, NV2.TEN AS NGUOISUA    " +
						" FROM ERP_CHUYENVITRI A   " +
						" INNER join ERP_KHOTT KHOTT on a.khochuyen_fk = KHOTT.PK_SEQ   " +
						" left join ERP_BIN BIN on a.binchuyen_fk = BIN.PK_SEQ   " +
						" INNER JOIN NHANVIEN NV ON A.NGUOITAO = NV.PK_SEQ   " +
						" INNER JOIN NHANVIEN NV2 ON A.NGUOISUA = NV2.PK_SEQ  " +
						" WHERE 1=1  " ;
		} 
		
		//Phân quyền kho
		query += " and A.khochuyen_fk in " + util.quyen_khott(this.userId) ;
			
		this.lsxRs = createSplittingDataNew(this.db, 50, 10, "  trangthai asc,  PK_SEQ  desc ", query);
	}
	
	public void DBclose() 
	{
		try{
			 
			if(lsxRs!=null){
				lsxRs.close();
			}
			if(khonhanRs!=null){
				khonhanRs.close();
			}
			if(ndxRs!=null){
				ndxRs.close();
			}
			if(nhanvienRs!=null){
				nhanvienRs.close();
			}
			if(nhanvien2Rs!=null){
				nhanvien2Rs.close();
			}
		 
			
			
		}catch(Exception er){
			er.printStackTrace();
		}
		finally
		{
			if(this.db!=null)
			this.db.shutDown();
		}
	}

	
	public String getTungayTao() 
	{
		return this.tungayTao;
	}

	public void setTungayTao(String tungay) 
	{
		this.tungayTao = tungay;
	}

	public String getDenngayTao() 
	{
		return this.denngayTao;
	}

	public void setDenngayTao(String denngay)
	{
		this.denngayTao = denngay;
	}

	public String getMasp() 
	{
		return this.masanpham;
	}

	public void setMasp(String masp) 
	{
		this.masanpham = masp;
	}

	public ResultSet getLsxRs() 
	{
		return this.lsxRs;
	}

	public void setLsxRs(ResultSet lsxRs) 
	{
		this.lsxRs = lsxRs;
	}

	public ResultSet getNdxRs() 
	{
		return this.ndxRs;
	}

	public void setNdxRs(ResultSet ndxRs) 
	{
		this.ndxRs = ndxRs;
	}
	
	public ResultSet getNhanvienRs() 
	{
		return this.nhanvienRs;
	}

	public void setNhanvienRs(ResultSet nhanvienRs) 
	{
		this.nhanvienRs = nhanvienRs;
	}
	
	public ResultSet getNhanvien2Rs() 
	{
		return this.nhanvien2Rs;
	}

	public void setNhanvien2Rs(ResultSet nhanvien2Rs) 
	{
		this.nhanvien2Rs = nhanvien2Rs;
	}
	
	public String getIsnhanHang() 
	{
		return this.isnhanHang;
	}

	public void setIsnhanHang(String isnhanHang) 
	{
		this.isnhanHang = isnhanHang;
	}

	
	public String getsochungtu() {
		
		return sochungtu;
	}

	
	public void setsochungtu(String soct) {
		
		this.sochungtu=soct;
	}

	public String getSophieu() {
		return sophieu;
	}

	public void setSophieu(String sophieu) {
		this.sophieu= sophieu;
		
	}

	public String getNdxuat() {
		return ndxuat;
	}

	public void setNdxuat(String ndxuat) {
		this.ndxuat= ndxuat;
		
	}

	public String getLydo() {
		return lydo;
	}

	public void setLydo(String lydo) {
		this.lydo= lydo;
		
	}

	public String getNguoitao() {
		return nguoitao;
	}

	public void setNguoitao(String nguoitao) {
		this.nguoitao= nguoitao;
		
	}

	public String getNguoisua() {
		return nguoisua;
	}

	public void setNguoisua(String nguoisua) {
		this.nguoisua= nguoisua;
		
	}

	
	public String getSolenh() {
		
		return this.solenh;
	}

	
	public void setSolenh(String solenh) {
		
		this.solenh=solenh;
	}

	
	public String getkhonhanId() {
		
		return this.khonhanId;
	}

	
	public void setkhonhanId(String khonhanid) {
		
		this.khonhanId=khonhanid;
	}

	
	public ResultSet getKhonhanRs() {
		
		return this.khonhanRs;
	}

	
	public void setKhonhanRs(ResultSet khonhanRs) {
		
		this.khonhanRs=khonhanRs;
	}

	
	public String getlsxId() {
		
		return this.lsxId;
	}

	
	public void setlsxId(String lsxid) {
		
		this.lsxId=lsxid;
	}


	public String getKhoChuyenId() {
		
		return this.khochuyenId;
	}


	public void setKhoChuyenId(String khochuyenid) {
		
		this.khochuyenId = khochuyenid;
	}


	public ResultSet getKhoChuyenRs() {
		
		return this.khochuyenRs;
	}


	public void setKhoChuyenRS(ResultSet khochuyenrs) {
		
		this.khochuyenRs = khochuyenrs;
	}


	
	public String getsochungtubnId() {
		
		return this.sochungtubn;
	}

	
	public void setsochungtubnId(String sochungtubnid) {
		
		this.sochungtubn = sochungtubnid;
	}


	
	public String getsohoadon() {
		
		return this.Sohoadon;
	}

	
	public void setsohoadon(String sohoadon) {
		
		this.Sohoadon=sohoadon;
	}
}
