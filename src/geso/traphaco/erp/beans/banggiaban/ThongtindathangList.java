package geso.erp.beans.thongtindathang.imp;

import geso.erp.beans.thongtindathang.IThongtindathangList;
import geso.dms.db.sql.dbutils;
import geso.dms.center.util.Phan_Trang;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

public class ThongtindathangList extends Phan_Trang implements IThongtindathangList
{
	private HttpServletRequest request;
	String ctyId;
	String ctyTen;
	String userId;

	String msg;
	String spId;
	String nccId;
	String lspId;
	String isMua;
	
	ResultSet thongtindathangRs;
	ResultSet spRs;
	ResultSet nccRs;
	ResultSet lspRs;
	
	String ttdhId;

	dbutils db;

	public ThongtindathangList()
	{
		this.ctyId = "";
		this.ctyTen = "";
		this.userId = "";
		this.msg = "";
		this.ttdhId = "";
		this.spId = "";
		this.nccId = "";
		this.lspId = "";
		this.isMua = "";
		this.db = new dbutils();
	}

	public HttpServletRequest getRequestObj()
	{
		return this.request;
	}

	public void setRequestObj(HttpServletRequest request)
	{
		this.request = request;
	}

	public String getCtyId()
	{
		return this.ctyId;
	}

	public void setCtyId(String ctyId)
	{
		this.ctyId = ctyId;
	}
	
	public String getCtyTen()
	{
		return this.ctyTen;
	}

	public void setCtyTen(String ctyTen)
	{
		this.ctyTen = ctyTen;
	}
	
	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
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
			sql = 	"SELECT TTDH.PK_SEQ AS ID, SP.MA + ' - ' + SP.TEN AS TENSP, ISNULL(ISMUA, 0) AS ISMUA, \n " +
					"ISNULL(NCC.TEN, '') AS TENNCC, ISNULL(TTDH.MOU, 0) AS MOU, ISNULL(TTDH.LEADTIME, 0) AS LEADTIME, \n " +
					"ISNULL(TTDH.LOTSIZE, 0) AS LOTSIZE, ISNULL(TTDH.THOIGIANSX, 0) AS THOIGIANSX, ISNULL(TTDH.CLEANUP, 0) AS CLEANUP \n " +
					"FROM ERP_THONGTINDATHANG TTDH \n " +
					"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = TTDH.SANPHAM_FK \n " +
					"LEFT JOIN ERP_NHACUNGCAP NCC ON NCC.PK_SEQ = TTDH.NCC_FK \n " +
					"WHERE TTDH.CONGTY_FK = '" + this.ctyId + "' ";
			
			if(this.lspId.length() > 0) sql += " AND SP.LOAISANPHAM_FK = " + this.lspId + " \n " ;
			if(this.spId.length() > 0) sql += " AND SP.PK_SEQ = " + this.spId + " \n " ;
			if(this.nccId.length() > 0) sql += " AND NCC.PK_SEQ = " + this.nccId + " \n " ;		
			if(this.isMua.length() > 0) sql += " AND TTDH.ISMUA = " + this.isMua + " \n " ;	
//			System.out.println(sql);
		}

		System.out.println(sql);
		this.thongtindathangRs = db.get(sql);
		this.thongtindathangRs = super.createSplittingDataNew(this.db, super.getItems(), super.getSplittings(), "ID DESC", sql);
		createRs();
	}
	
	private void createRs(){
		String query = "";
		query = " SELECT PK_SEQ AS ID, MA + ' - ' + TEN AS LSPTEN \n " +
				" FROM ERP_LOAISANPHAM  \n " +
				" WHERE TRANGTHAI = 1 AND CONGTY_FK = " + this.ctyId + "  \n " +
				" AND PK_SEQ IN (100039, 100041, 100042, 100043, 100044, 100045, 100046, 100049) ";
		this.lspRs = this.db.get(query);
		
		query = "SELECT PK_SEQ AS ID, TEN AS SPTEN \n " +
				"FROM ERP_SANPHAM  \n " +
				"WHERE TRANGTHAI = 1 ";
		
		if(this.lspId.length() > 0) query += " AND LOAISANPHAM_FK IN (" + this.lspId + " ) ";
		
		this.spRs = this.db.get(query);
		
		query = "SELECT PK_SEQ AS ID, TEN AS NCCTEN \n " +
				"FROM ERP_NHACUNGCAP \n " +
				"WHERE TRANGTHAI = 1 ";
		
		this.nccRs = this.db.get(query);
		
	}

	public ResultSet getLspRs(){
		return this.lspRs;
	}
	
	public ResultSet getSpRs(){
		return this.spRs;
	}
	
	public ResultSet getNccRs(){
		return this.nccRs;
	}
	
	public String getNccId(){
		return this.nccId;
	}
	
	public void setNccId(String nccId){
		this.nccId = nccId;
	}
	
	public String getSpId(){
		return this.spId;
	}
	
	public void setSpId(String spId){
		this.spId = spId;
	}

	public String getLspId(){
		return this.lspId;
	}
	
	public void setLspId(String lspId){
		this.lspId = lspId;
	}

	public String getIsMua()
	{
		return this.isMua;
	}

	public void setIsMua(String isMua)
	{
		this.isMua = isMua;
	}
	
	public void DbClose()
	{
		try
		{
			if(this.thongtindathangRs != null)
				this.thongtindathangRs.close();
			
		}
		catch (SQLException e) {}
		finally
		{
			if(this.db!=null)
			this.db.shutDown();
		}
	}

	public ResultSet getThongtindathangRs()
	{
		return this.thongtindathangRs;
	}

	public void setThongtindathangRs(ResultSet thongtindathangRs)
	{
		this.thongtindathangRs = thongtindathangRs;
	}

	public String getTtdhId()
	{
		return this.ttdhId;
	}

	public void setTtdhId(String ttdhId)
	{
		this.ttdhId = ttdhId;
	}
	
	public String convertDate(String date)
	{
		if (!date.contains("-"))
			return "";
		String[] arr = date.split("-");
		if (arr[0].length() > arr[2].length())
			return arr[2] + "-" + arr[1] + "-" + arr[0];
		return date;
	}
	

}
