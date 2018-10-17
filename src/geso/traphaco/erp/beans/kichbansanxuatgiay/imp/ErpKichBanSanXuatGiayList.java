package geso.traphaco.erp.beans.kichbansanxuatgiay.imp;

import java.sql.ResultSet;
import java.sql.SQLException;

import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.beans.kichbansanxuatgiay.IErpKichBanSanXuatGiayList;

public class ErpKichBanSanXuatGiayList extends Phan_Trang  implements  IErpKichBanSanXuatGiayList
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String ctyId;
	String userId;
	String tungay;
	String denngay;
	String trangthai; 
	String ma;
	String diengiai;
	String sanpham;
	String msg;
	String nhamayid;
	String tennhamay;
	
	ResultSet rsKbsx;
	ResultSet rsNhamay;
	ResultSet SpRs;
	
	public ResultSet getSpRs() {
		return SpRs;
	}

	public void setSpRs(ResultSet spRs) {
		SpRs = spRs;
	}


	dbutils db;
	
	public ErpKichBanSanXuatGiayList()
	{
		this.ctyId = "";
		this.userId = "";
		this.tungay = "";
		this.denngay = "";
		this.trangthai = "";
		this.diengiai = "";
		this.sanpham = "";
		this.nhamayid="";
		this.tennhamay="";
		this.ma = "";
		this.msg = "";
		
		this.db = new dbutils();
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

	public String getMa() 
	{
		return this.ma;
	}

	public void setMa(String ma) 
	{
		this.ma = ma;	
	}

	public String getSanpham() 
	{
		return this.sanpham;
	}

	public void setSanpham(String sanpham) 
	{
		this.sanpham = sanpham;	
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
		Utility util = new Utility();
		
		if(query.length() > 0)
			sql = query;
		else
		{	
			/*sql = 
				"	select a.pk_seq, a.DienGiai as DienGiai, d.ma as spMa,  case when  d.ten is null then masanpham else d.ten + ' (' +d.quycach +')' end  as spTen, a.trangthai,e.tennhamay as NhaMay,"       +    
				"		b.ten as nguoitao, a.ngaytao, c.ten as nguoisua, a.ngaysua     "       +    
				"	from Erp_KichBanSanXuat_Giay a inner join NhanVien b on a.nguoitao = b.pk_seq      "       +    
				"		inner join nhanvien c on a.nguoisua = c.pk_seq   "       +    
				"		left join Erp_sanpham d on a.sanpham_fk = d.pk_seq "       +    
				"		inner join ERP_NHAMAY e on e.pk_seq=a.NhaMay_FK "       +    
				"	where a.congty_fk='"+this.ctyId+"' "       ;*/
			sql =    "	select  distinct a.pk_seq, a.DienGiai as DienGiai, d.ma as spMa,isnull(d.ma + ' ' + d.ten , '') as spTen, a.trangthai,'' as NhaMay," +
			"	b.ten as nguoitao, a.ngaytao, c.ten as nguoisua, a.ngaysua     " +
			"	from Erp_KichBanSanXuat_Giay a inner join NhanVien b on a.nguoitao = b.pk_seq      " +
			"	inner join nhanvien c on a.nguoisua = c.pk_seq   " +
			"	left join erp_sanpham d on  d.ma = a.masanpham and d.congty_fk = a.congty_fk where 1=1 ";
			//"	inner join ERP_NHAMAY e on e.pk_seq=a.NhaMay_FK ";
			if (this.ctyId != null &&this.ctyId.trim().length() > 0 && this.ctyId.trim().equals("null") == false)
				sql += "	and a.congty_fk='" + this.ctyId + "' ";
		}
		
		System.out.println("init___: " + sql);
		this.rsKbsx =createSplittingDataNew(this.db, 50, 10, "pk_seq desc ", sql);
		query = "select pk_seq,tennhamay as Ten from ERP_NHAMAY where congty_fk='" + this.ctyId + "'";
		System.out.println("query lay nha may: \n" + query + "\n=============================================");
		this.rsNhamay = this.db.get(query);
		
		sql= " SELECT PK_SEQ ,MA + ' ' +TEN AS TEN FROM ERP_SANPHAM where congty_fk='" + this.ctyId + "'";
		this.SpRs=db.get(sql);
		
	}

	public void DbClose() 
	{
		try 
		{
			if(this.rsKbsx != null)
				this.rsKbsx.close();
			if(this.rsNhamay != null)
				this.rsNhamay.close();
			
		} 
		catch (SQLException e) {}
		finally
		{
			if(this.db!=null)
			this.db.shutDown();
		}
	}

	public ResultSet getRsKbsx()
	{
		return this.rsKbsx;
	}

	public void setRsKbsx(ResultSet khlRs)
	{
		this.rsKbsx=khlRs;
	}

	
	public String getNhaMayID() {
		
		return this.nhamayid;
	}

	
	public void setNhaMayID(String nhamayid) {
		
		this.nhamayid=nhamayid;
	}

	
	public String getTenNhaMay() {
		
		return this.tennhamay;
	}

	
	public void setTenNhaMay(String tennhamay) {
		
		this.tennhamay=tennhamay;
	}

	
	public ResultSet getRsNhaMay() {
		
		return this.rsNhamay;
	}

	
	public void setRsNhaMay(ResultSet nhamayrs) {
		this.rsNhamay=nhamayrs;
		
	}
	

}
