package geso.traphaco.erp.beans.nhapkhoNK.imp;

import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.nhapkhoNK.*;

public class ErpNhapkhoNKList extends Phan_Trang implements IErpNhapkhoNKList
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
	
	String soNK;
	
	ResultSet nkRs;
	
	private int num;
	private int soItems;
	private int[] listPages;
	private int currentPages;
	
	dbutils db;
	Utility util;
	String nppId;
	
	
	public ErpNhapkhoNKList()
	{
		this.userId = "";
		this.tungay = "";
		this.denngay = "";
		this.dvthId = "";
		this.trangthai = "";
		this.soPO = "";
		this.soNK = "";
		this.nccTen = "";
		this.msg = "";
		this.soItems = 25;
		this.util=new Utility();
		currentPages = 1;
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

	public ResultSet getNkList() 
	{
		return this.nkRs;
	}

	public void setNkList(ResultSet nklist) 
	{
		this.nkRs = nklist;
	}

	
	public void init(String search)
	{
		String query = "";
		if(search.length() <= 0)
		{
			query = " SELECT distinct a.PK_SEQ as nkId,ncc.pk_seq as nccId, ncc.Ten as nccTen, a.NGAYNHAP,a.tooltip, \n"+
					" c.sopo as PoId,  CAST(a.PK_SEQ as varchar(20)) as SOCHUNGTU, \n"+
					" a.TRANGTHAI, a.NGAYSUA, a.NGAYTAO, d.TEN as nguoitao, e.TEN as nguoisua, c.pk_seq muahang_fk, \n"+
					" ( \n"+
					"	 select COUNT(MH.PK_SEQ) \n"+
					"	 from ERP_NHAPKHONHAPKHAU MH  INNER JOIN \n"+ 
					"	 (SELECT B.MUAHANG_FK,sum(isnull(B.SOLUONG,0)) as SOLUONG \n"+
					"		FROM ERP_HOADONNCC A INNER JOIN ERP_HOADONNCC_DONMUAHANG B ON A.pk_seq = B.HOADONNCC_FK \n"+ 
					"	   INNER JOIN ERP_PARK C ON A.park_fk = C.pk_seq \n"+ 
					"	 	WHERE A.trangthai != 4 \n"+ 
					"	 GROUP BY B.MUAHANG_FK) MH_SLTT on MH.PK_SEQ = MH_SLTT.MUAHANG_FK \n"+ 
					"	 where  MH.CONGTY_FK = "+this.congtyId+"  and a.PK_SEQ = MH.PK_SEQ "+
					"	) CONXUATHANG "+					
					" FROM ERP_NHAPKHONHAPKHAU a "+
					" inner join NHANVIEN d on a.NGUOITAO = d.PK_SEQ  \n"+
					" inner join NHANVIEN e on a.NGUOISUA = e.PK_SEQ  \n"+
					" inner join ERP_MUAHANG c on  a.muahang_fk= c.pk_seq \n"+  
					" inner join ERP_NHACUNGCAP ncc on a.NCC_FK = ncc.pk_seq   \n"+
					" WHERE  a.congty_fk = '" + this.congtyId +"'"; 
					//+ "' AND  A.NPP_FK= "+this.nppId;
							//"  and b.pk_seq in  " + this.util.quyen_donvithuchien(this.userId) + " ";
			
		}
		else {
			query = search;
		}
		
		System.out.println(" query init :" + query);
		
		this.nkRs = createSplittingDataNew(this.db, soItems, 10, "NGAYNHAP desc, trangthai asc, nkId desc ", query);
		
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
		ResultSet rs = db.get("select count(*) as c from ERP_NHAPKHONHAPKHAU");
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

	
	public String getSoNK() {
		
		return this.soNK;
	}

	
	public void setSoNK(String soNK) {
		
		this.soNK = soNK;
	}

	
	public String getNCC() 
	{
		return this.nccTen;
	}

	public void setNCC(String ncc) 
	{
		this.nccTen = ncc;
	}

	public String getNppId() {
		return this.nppId;
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
