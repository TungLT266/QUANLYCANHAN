package geso.traphaco.erp.beans.bosanpham.imp;

import java.sql.ResultSet;

import geso.traphaco.erp.beans.bosanpham.IBosanphamList;
import geso.traphaco.erp.db.sql.dbutils;

public class BosanphamList implements IBosanphamList{
   
	String spId;
	String spTen;
	String ngay;
	String soluong;
	ResultSet bspRS;
	String userId;
	String msg;
	String ctyId;
	dbutils db;
	public BosanphamList()
	{   
		this.spId = "";
		this.spTen = "";
		this.ngay = "";
	    this.soluong ="";
	    this.ctyId = "";
	    this.msg ="";
	    db = new dbutils();
	}

	public void setSpId(String spId) {
		this.spId = spId;
		
	}

	public String getSpId() {
		return this.spId;
	}
	
	public void setSpTen(String spTen) {
		this.spTen = spTen;
		
	}

	public String getSpTen() {
		return this.spTen;
	}

	public void setNgay(String ngay) {
		this.ngay = ngay;
		
	}

	public String getNgay() {
		return this.ngay;
	}

	public void setSoluong(String soluong) {
		this.soluong = soluong;
		
	}

	public String getSoluong() {
			return this.soluong;
	}

	public void setCtyId(String ctyId) {
		this.ctyId = ctyId;
		
	}

	public String getCtyId() {
		return this.ctyId;
	}
	
	public ResultSet getBspRS() {
		
		return this.bspRS;
	}

	
	public void init(String st) {
		
		String sql = "";
		if(st.length()>0)
		{
			sql =	"SELECT BSP.PK_SEQ AS BSPID, BSP.NGAY, KHO.TEN AS KHO, SP.MA  + ' - ' + SP.TEN AS SP, BSP.SOLUONG, BSP.TRANGTHAI " + 
					"FROM ERP_BOSANPHAM BSP " +
					"INNER JOIN ERP_KHOTT KHO ON KHO.PK_SEQ = BSP.KHO_FK " + 
					"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = BSP.SANPHAM_FK  " +
					"WHERE 1 = 1 " + st + " " +
					"ORDER BY BSP.PK_SEQ DESC ";
		}else
			sql =	"SELECT BSP.PK_SEQ AS BSPID, BSP.NGAY, KHO.TEN AS KHO, SP.MA  + ' - ' + SP.TEN AS SP, BSP.SOLUONG, BSP.TRANGTHAI " + 
					"FROM ERP_BOSANPHAM BSP " +
					"INNER JOIN ERP_KHOTT KHO ON KHO.PK_SEQ = BSP.KHO_FK " + 
					"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = BSP.SANPHAM_FK  " +
					"ORDER BY BSP.PK_SEQ DESC ";
		
		System.out.print("chuoi: "+ sql);
		this.bspRS = db.get(sql);
	}
	
	public ResultSet getSP(String Id){
		String query = 	"SELECT SP.MA + ' - ' + SP.TEN + ' [' + ISNULL(DVDL.DONVI, '') + ']' AS SP, XHDB.SOLUONG " + 
						"FROM ERP_XUATHANGDEBO XHDB " +
						"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = XHDB.SANPHAM_FK " + 
						"INNER JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ = SP.DVDL_FK " +
						"WHERE BOSANPHAM_FK = " + Id + "";

		return this.db.get(query);
	}
	public void Xoa(String Id){
		
	}
	
	public void setUserId(String userId) {
	    this.userId = userId;
	}

	public String getUserId() {
		return this.userId;
	}
		
	public void setMsg(String msg) {
		
		this.msg = msg;
	}
	
	public String getMsg() {
		
		return this.msg;
	}
	
	public void DbClose() {
		// TODO Auto-generated method stub
		try{
			if(bspRS !=null){
				bspRS.close();
			}
			db.shutDown();
		
		}catch(Exception err){
			
		}
	}
	
}
