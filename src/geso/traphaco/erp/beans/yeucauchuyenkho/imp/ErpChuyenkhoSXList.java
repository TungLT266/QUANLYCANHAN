package geso.traphaco.erp.beans.yeucauchuyenkho.imp;

import java.sql.ResultSet;

import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.yeucauchuyenkho.IErpChuyenkhoSXList;

public class ErpChuyenkhoSXList extends Phan_Trang implements IErpChuyenkhoSXList
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
	
	String sanphamct="";

	public String getSanphamct() {
		return sanphamct;
	}

	public void setSanphamct(String sanphamct) {
		this.sanphamct = sanphamct;
	}


	String sochungtubn;

	String sochungtu;
	ResultSet lsxRs;
	ResultSet spRs;

	public ResultSet getSpRs() {
		return spRs;
	}

	public void setSpRs(ResultSet spRs) {
		this.spRs = spRs;
	}


	ResultSet khonhanRs;
	ResultSet khochuyenRs;
	ResultSet ndxRs;
	ResultSet nhanvienRs;
	ResultSet nhanvien2Rs;
	
	private int num;
	private int[] listPages;
	private int currentPages;
	
	dbutils db;
	
	public ErpChuyenkhoSXList()
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
 
			query = 	" SELECT A.PK_SEQ, A.TRANGTHAI, A.NGAYYEUCAU, A.NOIDUNGXUAT_FK AS NDXID, B.MA + ', ' + B.TEN AS NOIDUNGXUAT,  isnull(KHOTT.TEN,'') as khonhan," +
						" A.LYDO, NV.TEN AS NGUOITAO, A.NGAYSUA, A.NGAYTAO, NV2.TEN AS NGUOISUA  ,isnull(a.sochungtu,'') as sochungtu, isnull(A.tooltip, '') as tooltip, " +
						" case when a.dathang_fk is not null then d.ten else '' end nppdat  " +
						" FROM ERP_YEUCAUCHUYENKHO A LEFT JOIN ERP_NOIDUNGNHAP B ON A.NOIDUNGXUAT_FK = B.PK_SEQ  " +
						" left join ERP_KHOTT KHOTT on a.khonhan_fk = KHOTT.PK_SEQ   " +
						" LEFT JOIN NHANVIEN NV ON A.NGUOITAO = NV.PK_SEQ   " +
						" LEFT JOIN NHANVIEN NV2 ON A.NGUOISUA = NV2.PK_SEQ  " +
						" left join nhaphanphoi d on a.doituongnhan_fk = d.PK_SEQ" +
						" WHERE 1=1  " ;
			if(this.tungayTao.length() > 0)
				query += "\n and a.ngayyeucau >= '" + this.tungayTao + "'";
			
			if(this.denngayTao.length() > 0)
				query += "\n and a.ngayyeucau  <= '" + this.denngayTao + "'";
		
			if(this.trangthai.length() > 0)
				query += "\n and a.TrangThai = '" + this.trangthai + "'";
			
			if(sophieu.length() > 0){
				query += "\n and  cast( a.pk_seq as nvarchar(10))  like '%" + sophieu + "%'";
			}
			
			if(this.khochuyenId.length() > 0){
				query += "\n and  cast( a.khoxuat_fk as nvarchar(10))  like '%" + this.khochuyenId + "%'";
			}
			
			if(khonhanId.length() > 0){
				query += "\n and  cast( a.khonhan_fk as nvarchar(10))  like '%" + khonhanId + "%'";
			}
			
			if(lsxId.length() > 0){
				query += "\n and  cast( a.LENHSANXUAT_FK as nvarchar(10))  like '%" + lsxId + "%'";
			}
			
			if(lydo.length() > 0){
				query += "\n and a.lydo like N'%" + lydo + "%'";
			}
			
			if(ndxuat.length() > 0){
				query += "\n and a.noidungxuat_fk = " +ndxuat+ " ";
			}
			if(nguoitao.length() > 0){
				query += "\n and a.nguoitao = " +nguoitao+ " ";
			}
			if(nguoisua.length() > 0){
				query += "\n and a.nguoisua = " +nguoisua+ " ";
			}
			
			if(this.sochungtubn.trim().length()>0){
				query+= "\n and A.LENHDIEUDONG LIKE '%"+sochungtubn+"%' ";
			}
			 
		 
			if(this.Sohoadon.length() >0){
				query+="\n AND A.sochungtu LIKE  '%"+this.Sohoadon+"%'";
			}
				
			if(this.sanphamct.length() >0){		
				query+=	"\n and a.pk_seq in ( "+    
						"\n select c.YEUCAUCHUYENKHO_fk from ERP_YEUCAUCHUYENKHO_sanpham c inner join erp_sanpham sp on sp.pk_seq= c.sanpham_fk "+    
						"\n where sp.pk_seq ="+ this.sanphamct +")" ;
			}
		} 
		
		//Phân quyền kho
		query += " and A.khoxuat_fk in " + util.quyen_khott(this.userId) ;
		
		this.lsxRs = createSplittingDataNew(this.db, 50, 10, "  NGAYYEUCAU desc,  PK_SEQ  desc ", query);
		
		
		//get spRS
		query ="select PK_SEQ,MA, TEN from ERP_SANPHAM where trangthai ='1'";
		this.spRs = db.get(query);


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
