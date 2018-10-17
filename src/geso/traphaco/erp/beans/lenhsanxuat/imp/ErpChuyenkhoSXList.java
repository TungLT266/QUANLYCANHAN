package geso.traphaco.erp.beans.lenhsanxuat.imp;

import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.beans.lenhsanxuat.IErpChuyenkhoSXList;

import java.sql.ResultSet;

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
	String lydo;
	String lsxId;

	String khonhanid;
	String khochuyenid;
	String nguoitao;
	String nguoisua;
	String task;
	String sochungtu;
	ResultSet lsxRs;
	
	ResultSet ndxRs;
	ResultSet khonhanRs;
	ResultSet khochuyenRs;
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
		this.task="";
		this.lsxId="";
		this.khonhanid="";
		this.nguoitao= "";
		this.nguoisua = "";
		this.msg = "";	
		this.khochuyenid="";
		this.isnhanHang = "0";
		currentPages = 1;
		this.sochungtu="";
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
        
        this.nhanvienRs= db.get(" select PK_SEQ, ten from NHANVIEN where trangthai=1");
        this.nhanvien2Rs= db.get(" select PK_SEQ, ten from NHANVIEN where trangthai=1");
		this.khonhanRs=db.get("select PK_SEQ, TEN from ERP_KHOTT where TrangThai = '1' and pk_seq in "+util.quyen_khott(this.userId)+" order by loai asc ");
		this.khochuyenRs=db.get("select PK_SEQ, TEN from ERP_KHOTT where TrangThai = '1' and pk_seq in "+util.quyen_khott(this.userId)+" order by loai asc ");
		
		if(search.length() > 0)
			query = search;
		else
		{
			query = " select  isnull(Yeucauchuyenkho_fk,0) as yeucauchuyenkhoid , a.PK_SEQ, a.trangthai, isnull(KHOTT.TEN,'') as khonhan, a.khonhan_fk, a.ngaychuyen, a.noidungxuat_fk as ndxId, b.ma + ', ' + b.ten as noidungxuat, a.lydo, NV.TEN as nguoitao, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua,   " +
					" isnull(is_doiquycach, 0) as is_doiquycach, isnull( ( select  LenhSanXuat_FK  from ERP_YeuCauKiemDinh c where  c.pk_seq=a.yckd_fk) , isnull(a.lenhsanxuat_fk,0)) as solenhsx,    \n"+
					" isnull((	select lsx.NGAYBATDAU  \n"+
					" 			from ERP_NHAPKHO nk  \n"+
					" 				inner join ERP_LENHSANXUAT_GIAY lsx on nk.SOLENHSANXUAT= lsx.PK_SEQ and nk.pk_seq=a.nhapkho_fk ), '') as ngaysanxuat \n"+
					" from ERP_CHUYENKHO a inner join ERP_NOIDUNGNHAP b on a.noidungxuat_fk = b.pk_seq  " +
					" left join ERP_KHOTT KHOTT on a.khonhan_fk = KHOTT.PK_SEQ   " +
					" inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
					" inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ where a.pk_seq > 0 ";	
		} 
			
		//Nhan Hang Yeu cau xuat kho da chot--->Chuyen sang Nhan Hang(Kho a qua kho b)
		if(this.isnhanHang.equals("1"))
		{
			query += " and a.trangthai >= 1 and a.khonhan_fk is not null " ;
		}
		else  //Xuat kho
		{
			/*if(this.isnhanHang.equals("2"))
			{
				query += " and a.trangthai != 0 and a.noidungxuat_fk in ( '100009', '100014', '100015', '100016', '100020', '100021','100022', 100026) ";
			}*/
		}
		
		System.out.println("___CHUYEN KHO: " + query);
		this.lsxRs = createSplittingDataNew(this.db, 50, 10, " trangthai asc , ngaychuyen desc ", query);
	}
	
	public void DBclose() 
	{
		try{
			if(lsxRs!=null){
				lsxRs.close();
			}
			
			if(ndxRs!=null){
				ndxRs.close();
			}
			if(khonhanRs!=null){
				khonhanRs.close();
			}
			if(khochuyenRs!=null){
				khochuyenRs.close();
			}
			if(nhanvienRs!=null){
				nhanvienRs.close();
			}
			if(nhanvien2Rs!=null){
				nhanvien2Rs.close();
			}
		}catch(Exception err){
			err.printStackTrace();
		}finally{
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

	
	public void settask(String task) {
		
		this.task=task;
	}

	
	public String gettask() {
		
		return this.task;
	}

	
	public String getLsxId() {
		
		return this.lsxId;
	}

	
	public void setLsxId(String lsxid) {
		
		this.lsxId=lsxid;
	}


	public String getKhonhanid() {
		
		return this.khonhanid;
	}


	public void setKhonhanid(String khonhanid) {
		
		this.khonhanid=khonhanid;
	}


	public ResultSet getKhonhanRs() {
		
		return this.khonhanRs;
	}


	public void setKhonhanRs(ResultSet khonhanRs) {
		
		this.khonhanRs=khonhanRs;
	}

	
	public ResultSet getKhochuyenRs() {
		
		return this.khochuyenRs;
	}

	
	public void setKhochuyenRs(ResultSet khochuyenRs) {
		
		this.khochuyenRs=khochuyenRs;
	}

	
	public String getKhochuyenId() {
		
		return this.khochuyenid;
	}

	
	public void setKhochuyenId(String khochuyenid) {
		this.khochuyenid=khochuyenid;
		
	}
}
