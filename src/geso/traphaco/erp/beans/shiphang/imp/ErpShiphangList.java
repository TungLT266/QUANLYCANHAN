package geso.traphaco.erp.beans.shiphang.imp;

import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.DinhKhoanKeToan;
import geso.traphaco.center.util.IDinhKhoanKeToan;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.IThongTinHienThi;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.ThongTinHienThi;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.shiphang.*;

public class ErpShiphangList extends Phan_Trang implements IErpShiphangList
{
	private static final long serialVersionUID = 1L;
	String congtyId;
	String userId;
	String tungay;
	String denngay;
	
	String dvthId;
	ResultSet dvthRs;
	
	String trangthai;
	String nccTen;
	String soPO;
	String msg;
	
	String soshiphang;
	
	ResultSet nhRs;
	
	String loaidh = "";
	
	private int num;
	private int soItems;
	private int[] listPages;
	private int currentPages;
	
	List<IThongTinHienThi> hienthiList;
	
	dbutils db;
	Utility util;
	String nppId;
	
	
	public ErpShiphangList()
	{
		this.userId = "";
		this.tungay = "";
		this.denngay = "";
		this.dvthId = "";
		this.trangthai = "";
		this.soPO = "";
		this.soshiphang = "";
		this.nccTen = "";
		this.msg = "";
		this.loaidh = "";
		this.soItems = 25;
		this.util=new Utility();
		currentPages = 1;
		num = 1;
		
		this.hienthiList = new ArrayList<IThongTinHienThi>();
		
		this.db = new dbutils();
	}
	
	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
		this.nppId=util.getIdNhapp(userId);
		
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


	public void setmsg(String msg) 
	{
		this.msg = msg;
	}

	public String getmsg() 
	{
		return this.msg;
	}

	public ResultSet getNhList() 
	{
		return this.nhRs;
	}

	public void setNhList(ResultSet nhlist) 
	{
		this.nhRs = nhlist;
	}

	
	public void init(String search)
	{
		String query = "";
		if(search.length() <= 0)
		{
			query = " SELECT distinct a.PK_SEQ as shId, ncc.Ten as nccTen, a.NGAYCHUNGTU, b.TEN as dvthTen, \n"+
					" c.sopo as PoId,  CAST(a.PK_SEQ as varchar(20)) as SOCHUNGTU, \n"+
					" a.TRANGTHAI, a.NGAYSUA, a.NGAYTAO, d.TEN as nguoitao, e.TEN as nguoisua, ISNULL(a.ISHOANTAT,0) ISHOANTAT \n"+
					" FROM ERP_SHIPHANG a "+
					" inner join ERP_DONVITHUCHIEN b on a.DONVITHUCHIEN_FK = b.PK_SEQ \n"+
					" inner join NHANVIEN d on a.NGUOITAO = d.PK_SEQ  \n"+
					" inner join NHANVIEN e on a.NGUOISUA = e.PK_SEQ  \n"+
					" inner join ERP_MUAHANG c on  a.muahang_fk= c.pk_seq \n"+  
					" inner join ERP_NHACUNGCAP ncc on a.NCC_FK = ncc.pk_seq   \n"+
					" WHERE c.LOAI = "+ this.loaidh +" and a.congty_fk = '" + this.congtyId + "' ";
							//"  and b.pk_seq in  " + this.util.quyen_donvithuchien(this.userId) + " ";
			
		}
		else {
			query = search;
		}
		
		System.out.println(" query init :" + query);
		//String query_init = createSplittingData_List(soItems, 10, "NGAYCHUNGTU desc, trangthai asc, shId desc ", query);		
		this.nhRs = createSplittingDataNew(this.db, 50, 10, "NGAYCHUNGTU desc, trangthai asc, shId desc ", query);
		
		query="select pk_seq, ten from ERP_DONVITHUCHIEN where trangthai = 1 " ; //this.util.quyen_donvithuchien(this.userId)
		this.dvthRs = db.get(query);

	}

	
	public void DBclose() 
	{
		this.db.shutDown();
	}

	public String getTrangthai()
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai)
	{
		this.trangthai = trangthai;
	}

	public String getSoPO() 
	{
		return this.soPO;
	}

	public void setSoPO(String soPO) 
	{
		this.soPO = soPO;
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
		ResultSet rs = db.get("select count(*) as c from ERP_SHIPHANG");
		return PhanTrang.getLastPage(rs);
	}

	
	public int[] getNewPagesList(String action, int num, int currentPage, int theLastPage, String[] listPage) {
		IPhanTrang pt = new PhanTrang();
		return pt.getNewPagesList(action, num, currentPage, theLastPage, listPage);
	}

	public String getCongtyId() 
	{
		return this.congtyId;
	}

	public void setCongtyId(String congtyId) 
	{
		this.congtyId = congtyId;
	}

	
	public String getSoShiphang() {
		
		return this.soshiphang;
	}

	
	public void setSoShiphang(String soshiphang) {
		
		this.soshiphang = soshiphang;
	}

	
	public String getNCC() 
	{
		return this.nccTen;
	}

	public void setNCC(String ncc) 
	{
		this.nccTen = ncc;
	}

	
	public List<IThongTinHienThi> getHienthiList() {
		
		return this.hienthiList;
	}

	
	public void setHienthiList(List<IThongTinHienThi> hienthiList) {
		
		this.hienthiList = hienthiList;
	}

	public String getNppId() {
		return this.nppId;
	}

	public void setLoaidh(String loaidh) 
	{
		this.loaidh = loaidh ;
	}

	public String getLoaidh() 
	{
		return this.loaidh;
	}
	
	@Override
	public void setSoItems(int soItems) {
		
		this.soItems = soItems;
	}
	@Override
	public int getSoItems() {
		
		return this.soItems;
	}
	
}
