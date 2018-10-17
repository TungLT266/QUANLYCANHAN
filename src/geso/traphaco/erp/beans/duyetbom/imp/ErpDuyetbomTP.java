package geso.traphaco.erp.beans.duyetbom.imp;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.duyetbom.IErpDuyetbomTP;
import geso.traphaco.erp.db.sql.dbutils;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ErpDuyetbomTP implements IErpDuyetbomTP
{
	String Msg, ctyId, bomId, UserId, masp,diengiai,tensanpham,tenBom;
	ResultSet rsBom;
	dbutils db;

	public ErpDuyetbomTP()
	{
		this.ctyId = "";
		this.bomId = "";
		this.UserId = "";
		this.Msg = "";
		this.masp = "";
		this.tensanpham="";
		this.diengiai="";
		this.tenBom = "";
		this.db = new dbutils();
	}
	public void init()
	{
		String query = "SELECT DMVT.PK_SEQ,DMVT.TENBOM,DMVT.DIENGIAI, DMVT.MASANPHAM,ISNULL(DMVT.TENSANPHAM,(SP.TEN)) AS TENSANPHAM ,NV.TEN,DMVT.NGAYTAO	\n"
				+ " FROM ERP_DANHMUCVATTU DMVT 	\n"
				+ "	INNER JOIN NHANVIEN NV ON NV.PK_SEQ = DMVT.NGUOITAO \n"
				+ " INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = DMVT.SANPHAM_FK	\n"
				+ "	WHERE ISNULL(DMVT.ISHOATDONG,0) =1 AND ISNULL(DMVT.ISDADUYET,0)=0  AND DMVT.CONGTY_FK = "+this.ctyId+" \n";
		if(this.masp.trim().length()>0)
			query +=" DMVT.MASANPHAM LIKE '%"+this.masp+"%'	\n";
		if(this.tenBom.trim().length()>0)
			query +=" DMVT.TENBOM LIKE '%"+this.masp+"%'	\n";
		if(this.diengiai.trim().length()>0)
			query +=" ISNULL(DMVT.TENSANPHAM,(SP.TEN)) LIKE N'%"+this.diengiai+"%' OR DMVT.DIENGIAI LIKE N'%"+this.masp+"%'	\n";
		System.out.println("Query: "+query);
		this.rsBom  = this.db.get(query);
	}
	public String duyet()
	{
		try {
			this.db.getConnection().setAutoCommit(false);
			
		
			String query = "SELECT * FROM ERP_CHUCDANH WHERE PK_SEQ = 100049 AND NHANVIEN_FK = "+this.UserId;
			/*ResultSet rscheck = this.db.get(query);
			if(rscheck.next())
			{
				rscheck.close();
			}else
			{
				db.getConnection().rollback();
				return "User không có quyền duyệt. chỉ có trưởng phòng đảm bảo chất lượng mới được duyệt";
			}*/
			
			query = "UPDATE ERP_DANHMUCVATTU SET ISDADUYET =1, NGUOIDUYET = "+this.UserId+",TENNGUOIDUYET = (SELECT TEN FROM NHANVIEN WHERE PK_SEQ ="+this.UserId+"), TRANGTHAI =1 WHERE PK_SEQ ="+this.bomId;
			if(!this.db.update(query))
			{
				
				db.getConnection().rollback();
				return "Không lấy được user đang sử dụng. Vui lòng kiểm tra lại hoặc liên hệ GESO xử lý";
			}
			query ="SELECT CASE WHEN  NGAYTAO >='2017-12-26' THEN 1 ELSE 0 END AS CHECKNGAY FROM ERP_DANHMUCVATTU WHERE PK_SEQ = "+this.bomId+"";
			ResultSet rscheck = this.db.get(query);
			String check = "";
			if(rscheck.next())
			{
				check = rscheck.getString("CHECKNGAY");
			}
			rscheck.close();
			
			
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			if(check.equals("1"))
			{
				query = "EXEC pro_syn_BOM "+this.bomId;
				System.out.println("Query dong bo: "+query);
				this.db.update(query);
				
			}
		} catch (Exception e) {
			this.db.update("rollback");
			e.printStackTrace();
		}
		return this.Msg;
	}
	public void Close()
	{
		try
		{
			if (rsBom != null)
				rsBom.close();
			this.db.shutDown();
			
		} catch (Exception e)
		{
			System.out.print("Exception  Close" + e.getMessage());
			e.printStackTrace();
		}
	}


	public ResultSet getRsBom() {
		return rsBom;
	}
	public void setRsBom(ResultSet rsBom) {
		this.rsBom = rsBom;
	}
	public String getMsg() {
		return Msg;
	}


	public void setMsg(String msg) {
		Msg = msg;
	}


	public String getCtyId() {
		return ctyId;
	}


	public void setCtyId(String ctyId) {
		this.ctyId = ctyId;
	}


	public String getBomId() {
		return bomId;
	}


	public void setBomId(String bomId) {
		this.bomId = bomId;
	}


	public String getUserId() {
		return UserId;
	}


	public void setUserId(String userId) {
		UserId = userId;
	}


	public String getMasp() {
		return masp;
	}


	public void setMasp(String masp) {
		this.masp = masp;
	}


	public String getDiengiai() {
		return diengiai;
	}


	public void setDiengiai(String diengiai) {
		this.diengiai = diengiai;
	}


	public String getTensanpham() {
		return tensanpham;
	}


	public void setTensanpham(String tensanpham) {
		this.tensanpham = tensanpham;
	}


	public String getTenBom() {
		return tenBom;
	}


	public void setTenBom(String tenBom) {
		this.tenBom = tenBom;
	}


}
