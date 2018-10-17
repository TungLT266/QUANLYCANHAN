package geso.traphaco.erp.beans.erp_capquanly.imp;

import java.sql.ResultSet;
import java.sql.SQLException;




import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.erp.beans.erp_capquanly.IErp_capquanlyList;

public class Erp_capquanlyList  extends Phan_Trang implements IErp_capquanlyList
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 668328923969461422L;
	dbutils db;
	String ID;
	String userId;
	String userTen;
	String MA;
	String TEN;
	String NGAYTAO;
	String NGAYSUA;
	String NGUOITAO;
	String NGUOISUA;
	String TRANGTHAI;
	String Msg;
	ResultSet Rscn;
	String ctyId;
	ResultSet rsCty;
	
	String chixem;
	
	public Erp_capquanlyList()
	{
		db = new dbutils();
		this.userId = "";
		this.userTen = "";
		this.ID = "";
		this.MA = "";
		this.TEN = "";
		this.NGAYTAO = "";
		this.NGAYSUA = "";
		this.NGUOITAO = "";
		this.NGUOISUA = "";
		this.TRANGTHAI = "";
		this.ctyId = "";
		this.Msg = "";
		
		this.chixem = "0";
	}

	
	public String getID() {
		
		return ID;
	}

	
	public String getMA() {
		
		return MA;
	}

	
	public String getTEN() {
		
		return TEN;
	}

	
	public String getNGAYTAO() {
		
		return NGAYTAO;
	}

	
	public String getNGAYSUA() {
		
		return NGAYSUA;
	}

	
	
	
	public String getNGUOITAO() {
		
		return NGUOITAO;
	}

	
	public String getNGUOISUA() {
		
		return NGUOISUA;
	}

	
	public String getTRANGTHAI() {
		
		return TRANGTHAI;
	}

	
	public String getMsg() {
		
		return Msg;
	}

	
	public ResultSet getRscn() {
		
		return Rscn;
	}

	
	
	
	public void setID(String ID) {
		
		this.ID = ID;
	}

	public void setMA(String MA) {
		
		this.MA = MA;
	}

	
	public void setTEN(String TEN) {
		
		this.TEN = TEN;
	}

	
	public void setNGAYTAO(String NGAYTAO) {
		
		this.NGAYTAO = NGAYTAO;
	}

	
	public void setNGAYSUA(String NGAYSUA) {
		
		this.NGAYSUA = NGAYSUA;
	}

	
	public void setNGUOITAO(String NGUOITAO) {
		
		this.NGUOITAO = NGUOITAO;
	}

	
	public void setNGUOISUA(String NGUOISUA) {
		
		this.NGUOISUA = NGUOISUA;
	}

	
	public void setTRANGTHAI(String TRANGTHAI) {
		
		this.TRANGTHAI = TRANGTHAI;
	}

	
	public void setMsg(String Msg) {
		
		this.Msg = Msg;
	}

	

	
	public void init(String sql) 
	{
		Utility util = new Utility();
		

		this.rsCty = this.db.get("SELECT PK_SEQ , MA + ' - '+ TEN AS TEN FROM ERP_CONGTY WHERE TRANGTHAI = '1' ");
		
		String query =" SELECT CN1.PK_SEQ LOAICAP, CN1.TEN TENLOAICAP ,CN.PK_SEQ AS ID_CN,CN.MACAP MA ,CN.TENCAP TEN ,isnull(CN.TRANGTHAI,0) AS TT ,CN.NGAYTAO ,CN.NGAYSUA ,NT.TEN AS \n"+
					  " NGUOITAO,NS.TEN AS NGUOISUA, CTY.TEN TENCTY, CQL.DANGNHAP USERQL \n"+
					  " FROM ERP_CAPQUANLY CN INNER JOIN ERP_LOAICAPQUANLY CN1 ON CN.LOAICAP_FK = CN1.PK_SEQ \n"+
					  " INNER JOIN NHANVIEN NT  ON NT.PK_SEQ = CN.NGUOITAO \n"+
					  " INNER JOIN NHANVIEN NS ON NS.PK_SEQ = CN.NGUOISUA  \n" +
					  " INNER JOIN NHANVIEN CQL ON CN.NVQL_FK = CQL.PK_SEQ \n"+
					  " INNER JOIN ERP_CONGTY CTY ON CN.CONGTY_FK = CTY.PK_SEQ \n" +
					  " where 1= 1  " ;
		
		if (this.MA.trim().length() > 0)
			query += " and CN.ma like N'%" + this.MA+ "%'";
		if (this.TEN.trim().length() > 0)
			query += " and dbo.ftBoDau(CN.ten) like N'%" + util.replaceAEIOU(this.TEN) + "%'";
		if (this.TRANGTHAI.length() > 0)
			query += " and CN.TRANGTHAI = '" + TRANGTHAI + "' ";
		if (this.NGAYTAO.length() > 0)
			query += " and cn.ngaytao >= '%" + NGAYTAO + "%'";		
		if(this.ctyId.length() > 0)
			query+= " and CN.CONGTY_FK = "+this.ctyId;
		
		query+=" ORDER BY CTY.PK_SEQ DESC, CN1.PK_SEQ ASC ";
		System.out.println(query);
		this.Rscn = db.get(query);		
		System.out.println("query list " + query);
		
		
	}
	
	
	
	public void DBClose() 
	{
		if(this.Rscn!=null)
			try
			{
				this.Rscn.close();
				if(this.db!=null)
					this.db.shutDown();
			} catch (SQLException e)
			{
			
				e.printStackTrace();
			}
	}

	
	
	public void setUserTen(String userten) {
		this.userTen=userten;
		
	}

	
	public String getUserTen() {
		
		return this.userTen;
	}

	
	public void setUserid(String userid) {
		
		this.userId = userid;
	}

	
	public String getUserid() {
		
		return userId;
	}

	
	public boolean CheckReferences(String column,String table)
	{
		String query="SELECT count("+column+") AS NUM  FROM "+table+" WHERE "+column+" ="+this.ID+""; 
		System.out.println("CheckReferences "+query);
		ResultSet rs = db.get(query);
		System.out.println("____Kiem tra rang buoc_____ "+query);
		try {//kiem tra ben san pham
		while(rs.next())
		{ if(rs.getString("num").equals("0"))
		   return false;
		}
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		 return true;
	}

	public boolean Delete()
	{
		String query = "UPDATE ERP_CAPQUANLY SET TRANGTHAI = 0 WHERE PK_SEQ = "+this.ID;
		
		if(!db.update(query))
		{	
			db.update("rollback");
			this.Msg = query;
			return false;
		}
		
		return true;
	}
	
	public boolean Chot() {
		
		String query = "UPDATE ERP_CAPQUANLY SET TRANGTHAI = 1 WHERE PK_SEQ = "+this.ID;
		
		if(!db.update(query))
		{	
			db.update("rollback");
			this.Msg = query;
			return false;
		}
		
		return true;
	}
	
	public void setChixem(String chixem) {
		
		this.chixem = chixem;
	}

	public String getChixem() {
		
		return this.chixem;
	}

	public void setRsCty(ResultSet rsCty) {
		this.rsCty = rsCty;
	}

	
	public ResultSet getRsCty() {
		return this.rsCty;
	}

	public String getCtyId()
	{
		return this.ctyId;
	}

	public void setCtyId(String ctyId)
	{
		this.ctyId = ctyId;
	}
}
