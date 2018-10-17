package geso.traphaco.erp.beans.lenhsanxuatgiay.imp;

import java.sql.ResultSet;

import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.lenhsanxuatgiay.IErpLenhsanxuatList;

public class ErpLenhsanxuatList extends Phan_Trang implements IErpLenhsanxuatList 
{
	private static final long serialVersionUID = 1L;
	String congtyId;
	String userId;
	String IsDuyet;
	String tungayTao;
	String denngayTao;
	String tungayDk;
	String denngayDk;
	String nguoitaoid;
	String nhamayid;
	String masanpham = "", tensanpham = "";
	String trangthai;
	String msg;
	String lsxid="";
	String iddvkd="";
	ResultSet lsxRs;
	ResultSet dvkdRs;
	ResultSet nguoitaoRs;
	ResultSet nhamayRs;
	String SoLSX;
	String Phanloai;
	private int num;
	private int[] listPages;
	private int currentPages;
	
	dbutils db;
	
	String ghichu = "";
	
	String tungayBD , denngayBD , tungayKT, denngayKT;
	String nppId;
	
	public ErpLenhsanxuatList()
	{
		this.IsDuyet="";
		this.lsxid="";
		this.iddvkd="";
		/*this.tungayTao = "";
		this.denngayTao = "";
		this.tungayDk = "";
		this.denngayDk = "";*/
		this.tungayBD = "";
		this.denngayBD = "";
		this.tungayKT = "";
		this.denngayKT = "";
		this.trangthai = "";
		this.masanpham = "";
		this.msg = "";	
		this.nguoitaoid="";
		this.nhamayid="";
		this.SoLSX="";
		currentPages = 1;
		num = 1;
		this.Phanloai="";
		//this.db = new dbutils();
	}
	
	
	
	public String getTungayBD() {
		return tungayBD;
	}



	public void setTungayBD(String tungayBD) {
		this.tungayBD = tungayBD;
	}



	public String getDenngayBD() {
		return denngayBD;
	}



	public void setDenngayBD(String denngayBD) {
		this.denngayBD = denngayBD;
	}



	public String getTungayKT() {
		return tungayKT;
	}



	public void setTungayKT(String tungayKT) {
		this.tungayKT = tungayKT;
	}



	public String getDenngayKT() {
		return denngayKT;
	}



	public void setDenngayKT(String denngayKT) {
		this.denngayKT = denngayKT;
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
		ResultSet rs = db.get("select count(*) as c from ERP_LENHSANXUAT ");
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
		this.db = new dbutils();
		Utility util = new Utility();
		String query = "";
 
		if(search.length() > 0)
			query = search;
		else
			query = 	" SELECT    isnull(solsx,'') as solenhsanxuat ,  ISNULL(A.DUYET,'0') AS DUYET, A.NGAYBATDAU ,A.PK_SEQ , isnull(A.diengiai,'') as diengiai   ,A.TRANGTHAI, SP.MA AS SPID, SP.TEN AS SPTEN, NV.TEN AS NGUOITAO, \n"+
						" A.NGAYSUA, A.NGAYTAO, NV2.TEN AS NGUOISUA, ISNULL(A.DONDATHANG_FK, '-1') AS DONDATHANG_FK, \n"+
						" A.KICHBANSANXUAT_FK , CASE WHEN SP.DVKD_FK=100005 THEN '' ELSE  ISNULL(( \n"+
						" SELECT TOP 1 D.DIENGIAI FROM ERP_LENHSANXUAT_CONGDOAN_GIAY C INNER JOIN ERP_CONGDOANSANXUAT_GIAY  D ON C.CONGDOAN_FK=D.PK_SEQ \n"+ 
						" WHERE TINHTRANG=0 AND  \n"+
						" LENHSANXUAT_FK=A.PK_SEQ ORDER BY THUTU),N'HOÀN TẤT CÁC CÔNG DOẠN') END AS CONGDOAN\n"+
						" FROM ERP_LENHSANXUAT_GIAY  A \n"+ 
						" LEFT JOIN ERP_KICHBANSANXUAT_GIAY KB ON KB.PK_SEQ=KICHBANSANXUAT_FK \n"+
						"  \n"+
						" left JOIN ERP_SANPHAM SP ON SP.PK_SEQ= a.SANPHAM_FK \n"+   
						" INNER JOIN NHANVIEN NV ON A.NGUOITAO = NV.PK_SEQ    \n"+
						" INNER JOIN NHANVIEN NV2 ON A.NGUOISUA = NV2.PK_SEQ  WHERE A.CONGTY_FK = "+this.congtyId+"";
		
		if(this.IsDuyet.equals("1")){
			query+=" AND ISNULL(DUYET,'0') ='0' and A.TRANGTHAI='0'";
		}
		//query+= "  AND A.NHAMAY_FK in " + util.quyen_nhamay(this.userId);
		
		System.out.println("cau lenh lay lenh san xuat:\n" + query + "\n========================================");
		this.lsxRs = createSplittingData(50, 10, "ngaybatdau desc, trangthai desc ", query);
		this.dvkdRs=db.get("SELECT PK_SEQ,DONVIKINHDOANH as ten FROM DONVIKINHDOANH where CONGTY_FK="+this.congtyId);
		this.nhamayRs=db.get("select PK_SEQ,tennhamay as TEN from ERP_NHAMAY where TRANGTHAI = 1 and CONGTY_FK = "+this.congtyId);
		this.nguoitaoRs=db.get("select distinct  NV.PK_SEQ, NV.TEN from ERP_LENHSANXUAT_GIAY LSX inner join NHANVIEN NV on LSX.NGUOITAO = NV.PK_SEQ WHERE LSX.CONGTY_FK = "+this.congtyId);
		System.out.println(query);
	}
	
	public void DBclose() 
	{
		try{
			if(lsxRs!=null){
				lsxRs.close();
			}
			if(dvkdRs!=null){
				dvkdRs.close();
			}
			if(nguoitaoRs!=null){
				nguoitaoRs.close();
			}
			if(nhamayRs!=null){
				nhamayRs.close();
			}
			if(!(this.db == null)){
				this.db.shutDown();
			}
		}
		catch(Exception err){
			
		}
	}

	public String getIddvkd() {
		return iddvkd;
	}
	public void setIddvkd(String iddvkd) {
		this.iddvkd = iddvkd;
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

	public String getTungayDk() 
	{
		return this.tungayDk;
	}

	public void setTungayDk(String ngaydk)
	{
		this.tungayDk = ngaydk;
	}

	public String getDenngayDk() 
	{	
		return this.denngayDk;
	}

	public void setDenngayDk(String ngaydk) 
	{
		this.denngayDk = ngaydk;
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

	public ResultSet getDvkdRs() 
	{
		return this.dvkdRs;
	}

	public void setDvkdRs(ResultSet dvkdRs) 
	{
		this.dvkdRs = dvkdRs;
	}
	
	public String getCongtyId() 
	{
		return this.congtyId;
	}

	public void setCongtyId(String congtyId) 
	{
		this.congtyId = congtyId;
	}

	
	public String getLSXId() {
		return this.lsxid;
	}

	
	public void setLSXId(String LSXId) {
		this.lsxid=LSXId;
	}

	
	public String getTenSp() {
		return this.tensanpham;
	}

	
	public void setTenSp(String tensp) {
		this.tensanpham = tensp;
	}

	public String getGhichu() {
		return ghichu;
	}

	public void setGhichu(String ghichu) {
		this.ghichu = ghichu;
	}



	
	public ResultSet getNguoiTaoRs() {
		
		return this.nguoitaoRs;
	}



	
	public void setNguoiTaoRs(ResultSet nguoitaoRs) {
		
		this.nguoitaoRs=nguoitaoRs;
	}



	
	public String getNhamayId() {
		
		return this.nhamayid;
	}



	
	public void setNhamayId(String nhamayid) {
		
		this.nhamayid=nhamayid;
	}



	
	public ResultSet getNhaMayRs() {
		
		return this.nhamayRs;
	}



	
	public void setNhaMayRs(ResultSet nhamayRs) {
		
		this.nhamayRs=nhamayRs;
	}



	
	public String getNguoitaoId() {
		
		return this.nguoitaoid;
	}
 	public void setNguoitaoId(String nguoitaoid) {
		
		this.nguoitaoid=nguoitaoid;
	}
 
	@Override
	public String getIsduyet() {
		// TODO Auto-generated method stub
		return this.IsDuyet;
	}
	
 	@Override
	public void setIsduyet(String Isduyet) {
		// TODO Auto-generated method stub
		this.IsDuyet=Isduyet;
	}



	public String getNppId() {
		return nppId;
	}
	public void setNppId(String nppId) {
		this.nppId = nppId;
	}



	@Override
	public String getSoLSX() {
		// TODO Auto-generated method stub
		return this.SoLSX;
	}



	@Override
	public void setSoLSX(String solsx) {
		// TODO Auto-generated method stub
		this.SoLSX=solsx;
	}



	@Override
	public void setPhanLoai(String phanloai) {
		// TODO Auto-generated method stub
		this.Phanloai=phanloai;
	}



	@Override
	public String getPhanLoai() {
		// TODO Auto-generated method stub
		return this.Phanloai;
	}
	
	
}
