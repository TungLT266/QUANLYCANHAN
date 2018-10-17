package geso.traphaco.center.beans.bcbanhangctsp.imp;

import geso.traphaco.center.beans.bcbanhangctsp.IBcbanhangctspList;
import geso.traphaco.center.db.sql.dbutils;

import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.distributor.beans.donhang.IDonhangList;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BcbanhangctspList  extends Phan_Trang implements IBcbanhangctspList, Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String userId;
	String query;

	String tungay;
	String denngay;
	String makh;
	String mafast;
	String ddkd;
	String msg;
	String kho;

	ResultSet bcbanhangctspRs;
	ResultSet ddkdRs;
	String bcbanhangctspId;

	dbutils db;

	public BcbanhangctspList()
	{
		this.userId = "";
		this.msg= "";

		this.tungay = "";
		this.denngay = "";
		this.makh="";
		this.mafast="";
		this.ddkd="";
		this.bcbanhangctspId = "";
		this.kho="";
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

		if(query.length() > 0)
			sql = query;
		else
		{
			sql = "	select sp.MA,sp.TEN as tensanpham, dvdl.DIENGIAI  , donhang.SLban, (donhang.SLban*bg.GIABANLECHUAN) as doanhthu, " +
					" (donhang.SLban*bg.GIABANLECHUAN*donhang.THUEVAT)/100 as VAT, (donhang.SLban*bg.GIABANLECHUAN*(1+donhang.THUEVAT/100)) as Tongtien  " +
					" from " +
					" (select dhsp.SANPHAM_FK, dhsp.THUEVAT ,SUM(dhsp.SOLUONG) as SLban " +
					" from DONHANG dh " +
					" inner join DONHANG_SANPHAM dhsp on dh.PK_SEQ=dhsp.DONHANG_FK "+
					" where dh.TRANGTHAI=1  " +
					" group by dhsp.SANPHAM_FK, dhsp.THUEVAT) donhang " +
					" inner join BANGGIABLC_SANPHAM bg on donhang.SANPHAM_FK= bg.SANPHAM_FK " +
					" inner join SANPHAM sp on donhang.SANPHAM_FK = sp.PK_SEQ " +
					" inner join DONVIDOLUONG dvdl on sp.DVDL_FK = dvdl.PK_SEQ" ;
		}
		
		System.out.println("[DS BH San pham CT:]: " + sql);
		this.bcbanhangctspRs = super.createSplittingData(super.getItems(), super.getSplittings(), "MA asc, tensanpham asc", sql); //db.get(query);;
		
		String ddkd=" select PK_SEQ, TEN from DAIDIENKINHDOANH ";
		this.ddkdRs = db.get(ddkd);
		
		/*this.bcbanhangctspRs = super.createSplittingData(super.getItems(), super.getSplittings(), "pk_seq desc", query);*/
	}

	public void DbClose()
	{
		try
		{
			if(this.bcbanhangctspRs != null)
				this.bcbanhangctspRs.close();
			this.db.shutDown();
		}
		catch (SQLException e) {}
	}

	public ResultSet getBcbanhangctspRs()
	{
		return this.bcbanhangctspRs;
	}

	public void setBcbanhangctspRs(ResultSet bcbanhangctspRs)
	{
		this.bcbanhangctspRs = bcbanhangctspRs;
	}

	public String getBcbanhangctspId()
	{
		return this.bcbanhangctspId;
	}

	public void setBcbanhangctspId(String bcbanhangctspId)
	{
		this.bcbanhangctspId = bcbanhangctspId;
	}


	public String getmaKH() {
		
		return this.makh;
	}


	public void setmaKH(String maKH) {
		this.makh=maKH;
		
	}


	public String getmaFast() {

		return this.mafast;
	}


	public void setmaFast(String mafast) {
		this.mafast= mafast;
		
	}


	public String getQuery() {

		return this.query;
	}


	public void setQuery(String query) {
		this.query= query;
		
	}


	public ResultSet getddkdRs() {

		return this.ddkdRs;
	}


	public void setddkdRS(ResultSet ddkd) {
		this.ddkdRs= ddkd;
		
	}


	public String getddkd() {

		return this.ddkd;
	}


	public void setddkd(String ddkd) {
		this.ddkd= ddkd;
		
	}


	public String getKhohang() {
	
		return this.kho;
	}


	public void setKhohang(String kho) {
		this.kho=kho;
		
	}

}
