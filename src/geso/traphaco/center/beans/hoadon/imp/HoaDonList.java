package geso.traphaco.center.beans.hoadon.imp;

import geso.traphaco.center.beans.hoadon.IHoaDonList;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;

import java.sql.ResultSet;

public class HoaDonList extends Phan_Trang implements IHoaDonList
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userId;
	private String nppId;
	private String sku;
	private String tungay;
	private String denngay;
	private String trangthai;
	private ResultSet nhaphangList;
	private dbutils db;
	String loaihd;
	
	
	String SoHoaDon;
	ResultSet nppRs;
	String Msg ;
	
	
	public HoaDonList(String[] param)
	{
		this.db = new dbutils();
		this.sku = param[0];
		this.tungay = param[1];
		this.denngay = param[2];
		this.trangthai = param[3];
		this.userId = "";
		this.nppId = "";
		this.SoHoaDon="";
		createNhaphanglist("");
		this.ddhId="";
	}
	
	public HoaDonList()
	{
		this.db = new dbutils();
		this.userId = "";
		this.nppId = "";
		this.sku = "";
		this.tungay = "";
		this.denngay = "";
		this.trangthai = "";
		this.Msg ="";
		this.SoHoaDon="";
		this.loaihd="";
		this.ddhId="";
	}
	
	public ResultSet getNhaphangList()
	{
		return this.nhaphangList;
	}
	
	public void setNhaphangList(ResultSet nhaphangList)
	{
		this.nhaphangList = nhaphangList;
	}

	public String getUserId()
	{
		return this.userId;
	}
	
	public void setUserId(String userId)
	{
		this.userId = userId;
	}
	
	public String getNppId()
	{
		return this.nppId;
	}
	
	public void setNppId(String nppId)
	{
		this.nppId = nppId;
	}
	
	
	public String getSKU()
	{
		return this.sku;
	}
	
	public void setSKU(String sku)
	{
		this.sku = sku;
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


	public void createNhaphanglist(String querystr)
	{
		Utility util = new Utility();
		String query;
		if (querystr.length()>0)
		{
			query = querystr;
		}else
		{
			query =
			" select kbh.TEN as KenhBanHang,dvkd.DONVIKINHDOANH,nh.PK_SEQ as NhapHangId,npp.MA as nppMa,npp.TEN as nppTen,nv.TEN as NguoiSua,\n  "+ 
			" 	nh.ngaychungtu,isnull(CAST(  ( select pk_seq from dondathang where soid=nh.soId ) as varchar(10)),'NA') as DatHang_FK,\n  "+ 
			"	nh.NgayNhan,ISNULL(SOCHUNGTUDOI,CHUNGTU) as SoHoaDon,	\n  "+ 
			"	( \n  "+ 
			"		select COUNT(distinct SanPham_FK) as SoSKU  from NHAPHANG_SP where NHAPHANG_FK=nh.PK_SEQ \n  "+ 
			"	)as SoSKU ,nh.SOTIENAVAT,\n  "+ 
			"	case when nh.TRANGTHAI=0 then N'Chưa nhận HĐ' \n  "+ 
			"	when nh.TRANGTHAI=1 and NH.PK_SEQ IN(SELECT NHAPHANG_FK FROM HUYNHAPHANG  WHERE CHUNGTU=nh.CHUNGTU)THEN N'NPP hủy HĐ'\n  "+ 
			"	when nh.TRANGTHAI=1 and NH.PK_SEQ NOT IN(SELECT NHAPHANG_FK FROM HUYNHAPHANG  WHERE CHUNGTU=nh.CHUNGTU)THEN N'Đã nhận HĐ'\n  "+ 
			"	else N'HĐ Hủy' END as TrangThai,\n  "+ 
			"	case when nh.LOAIHOADON=0 then N'Hóa đơn bán'\n  "+ 
			"	when nh.LOAIHOADON=1 then N'Hóa đơn KM'\n  "+ 
			"	WHEN nh.LOAIHOADON=2 then N'Hóa đơn trả'  " +
			"   WHEN nh.LOAIHOADON=3 then N'Hóa đơn TB'            " +
			"   ELSE N'Không xác định'       end as LoaiHD             \n  "+ 
			"  from  nhaphang nh\n  "+ 
			"	inner join NHAPHANPHOI npp on npp.PK_SEQ=nh.NPP_FK \n  "+ 
			"	inner join DONVIKINHDOANH dvkd on dvkd.PK_SEQ=nh.DVKD_FK\n  "+ 
			"	inner join KENHBANHANG kbh on kbh.PK_SEQ=nh.KBH_FK\n  "+ 
			"	inner join 	NHANVIEN nv on nv.PK_SEQ=nh.NGUOISUA " +
			"where 1=1  and npp.pk_seq in "+ util.quyen_npp(userId) ; 
		}
		this.nhaphangList =  super.createSplittingData(super.getItems(), super.getSplittings(), "ngaychungtu desc, trangthai asc, nhaphangId desc", query); 
		query=" select pk_Seq as nppId,ten as nppTen,ma as nppMa from nhaphanphoi where trangthai=1";
		this.nppRs=this.db.get(query);
	}
	public void DBclose(){
		try 
		{
			if(this.nhaphangList != null)
			this.nhaphangList.close();
			
			if(this.nppRs!=null)
				this.nppRs.close();
		} catch (Exception e) {
		}
		if(!(this.db == null)){
			this.db.shutDown();
		}
	}

	public void setMsg(String Msg) {
	  this.Msg = Msg;
		
	}

   public String getMsg() {
		
		return this.Msg;
	}
	
   public String getSoHoaDon()
	{
		return SoHoaDon;
	}

	public void setSoHoaDon(String soHoaDon)
	{
		SoHoaDon = soHoaDon;
	}

	public ResultSet getNppRs()
	{
		return nppRs;
	}

	public void setNppRs(ResultSet nppRs)
	{
		this.nppRs = nppRs;
	}
	public String getLoaihd() {
		return loaihd;
	}

	public void setLoaihd(String loaihd) {
		this.loaihd = loaihd;
	}
	String ddhId;

	public String getDdhId()
	{
		return ddhId;
	}

	public void setDdhId(String ddhId)
	{
		this.ddhId = ddhId;
	}
   
}
