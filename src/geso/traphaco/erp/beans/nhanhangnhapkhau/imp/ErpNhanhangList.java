package geso.traphaco.erp.beans.nhanhangnhapkhau.imp;

import java.sql.ResultSet;

import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.util.*
import geso.traphaco.center.util.*
import geso.traphaco.center.util.*
import geso.traphaco.center.util.*
import geso.traphaco.erp.beans.nhanhangnhapkhau.*;

public class ErpNhanhangList extends Phan_Trang implements IErpNhanhangList 
{
	private static final long serialVersionUID = 1L;
	String congtyId;
	String userId;
	String tungay;
	String denngay;
	
	String dvthId;
	ResultSet dvthRs;
	
	String trangthai;
	String soPO;
	String msg;
	
	ResultSet nhRs;
	
	private int num;
	private int[] listPages;
	private int currentPages;
	
	dbutils db;
	Utility util;
	
	public ErpNhanhangList()
	{
		this.userId = "";
		this.tungay = "";
		this.denngay = "";
		this.dvthId = "";
		this.trangthai = "";
		this.soPO = "";
		this.msg = "";
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
			query = " select a.PK_SEQ as nhId, a.SOHOADON, a.NGAYNHAN, b.TEN as dvthTen, " +
					" case a.NoiDungNhan_FK when 100000 then c.PREFIX + cast(c.PK_SEQ as varchar(10)) " +
					" else th.PREFIX + cast(th.PK_SEQ as varchar(10)) end  as PoId, " +
					" b.PREFIX + a.PREFIX + CAST(a.PK_SEQ as varchar(20)) as SOCHUNGTU, " +
					" a.TRANGTHAI, a.NGAYSUA, a.NGAYTAO, d.TEN as nguoitao, e.TEN as nguoisua, a.loaihanghoa_fk  " +
					" from erp_nhanhang a inner join ERP_DONVITHUCHIEN b on a.DONVITHUCHIEN_FK = b.PK_SEQ " +
					" left join ERP_MUAHANG c on a.MUAHANG_FK = c.PK_SEQ left join ERP_TRAHANG th on a.TRAHANG_FK = th.PK_SEQ " +
					" inner join NHANVIEN d on a.NGUOITAO = d.PK_SEQ  " +
					" inner join NHANVIEN e on a.NGUOISUA = e.PK_SEQ  " +
					" where a.congty_fk = '" + this.congtyId + "' and b.pk_seq in  "+ this.util.quyen_donvithuchien(this.userId);
		}
		else
			query = search;
		
		System.out.println("Query init nhan hang: " + query);
		
		this.nhRs = createSplittingData(50, 10, "NGAYNHAN desc, trangthai asc, nhId desc ", query);
		query="select pk_seq, ten from ERP_DONVITHUCHIEN where pk_seq in  "+ this.util.quyen_donvithuchien(this.userId);
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
		ResultSet rs = db.get("select count(*) as c from ERP_NHANHANG");
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
}
