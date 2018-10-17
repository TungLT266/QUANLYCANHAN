package geso.traphaco.erp.beans.tigia.imp;
import java.sql.ResultSet;
import java.sql.SQLException;

import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.erp.beans.tigia.ITigiaList;
public class TigiaList extends Phan_Trang implements ITigiaList
{

	private static final long serialVersionUID = 713786075757450382L;
	String ID, TIENTE_FK, TuNgay, DenNgay, TIGIAQUYDOI, USERID, TRANGTHAI,CongTy;
	String msg;
	dbutils db;
	ResultSet rsTienteList;
	ResultSet rsTigia;
	String chixem;
	
	public TigiaList()
	{
		this.db = new dbutils();
		this.ID = "";
		this.TIENTE_FK = "";
		this.TuNgay = "";
		this.DenNgay = "";
		this.TIGIAQUYDOI = "";
		this.USERID = "";
		this.TRANGTHAI = "";
		this.msg = "";
		this.chixem = "0";
	}
	public String getID()
	{
		return this.ID;
	}
	public void setID(String pk_seo)
	{
		this.ID = pk_seo;
	}
	public String getTRANGTHAI()
	{
		return this.TRANGTHAI;
	}
	public void setTRANGTHAI(String trangthai)
	{
		this.TRANGTHAI = trangthai;
	}
	public String getMessage()
	{
		return this.msg;
	}
	public void setMessage(String msg)
	{
		this.msg = msg;
	}
	public void CreateRs()
	{
		this.rsTienteList = db.get("select PK_SEQ,TEN from ERP_TIENTE ");
	}
	public void setTIENTE_FK(String tiente)
	{
		this.TIENTE_FK = tiente;
	}
	public String getTIENTE_FK()
	{
		return this.TIENTE_FK;
	}
	public void setTIGIAQUYDOI(String tigiaquydoi)
	{
		this.TIGIAQUYDOI = tigiaquydoi;
	}
	public String getTIGIAQUYDOI()
	{
		return this.TIGIAQUYDOI;
	}
	public ResultSet getTienteList()
	{
		return this.rsTienteList;
	}
	public void setTienteList(ResultSet rsTiente)
	{
		this.rsTienteList = rsTiente;
	}
	public void init()
	{
		String query = 	"SELECT TIGIA.PK_SEQ, ISNULL( TIGIA.TuNgay,'')TuNgay, ISNULL(DenNgay,'')DENNGAY, " +
						"ISNULL(TIGIA.TIGIAQUYDOI,0)TIGIAQUYDOI, TIGIA.TIENTE_FK, TIEN.TEN AS TENTIEN, TIGIA.NGAYTAO, TIGIA.NGAYSUA, " +
						"ISNULL( TIGIA.TRANGTHAI ,1)TRANGTHAI, NV.TEN AS TENNV, NV.PK_SEQ AS MANV, NV2.TEN AS TENNVS, NV2.PK_SEQ AS MANVS, " +
						"CTY.TEN AS TENCTY,CTY.MA AS MACTY " +
						"FROM  ERP_TIENTE TIEN " +
						"LEFT JOIN  ERP_TIGIA TIGIA ON TIGIA.TIENTE_FK = TIEN.PK_SEQ " +  
						"INNER JOIN NHANVIEN NV ON TIGIA.NGUOITAO = NV.PK_SEQ " +
						"INNER JOIN NHANVIEN NV2 ON TIGIA.NGUOISUA = NV2.PK_SEQ " +  
						"INNER JOIN ERP_CONGTY CTY ON CTY.PK_SEQ = TIGIA.CONGTY_FK  WHERE CTY.PK_SEQ > 0 " ; // CTY.PK_SEQ = " + this.CongTy + " "; 


		System.out.println("cau lenh sql la " + query);
		if (this.TIENTE_FK.length() > 0)
			query += " AND TIGIA.TIENTE_FK = '" + TIENTE_FK + "'";
		if (this.TuNgay.length() > 0)
			query += " AND TIGIA.TuNGAY  >='" + this.TuNgay + "'";
		if (this.DenNgay.length() > 0)
			query += " AND TIGIA.DenNgay  <= '" + this.DenNgay + "'";
		if (this.TIGIAQUYDOI.length() > 0)
			query += " AND TIGIA.TIGIAQUYDOI like '%" + TIGIAQUYDOI.replaceAll(",", "") + "%'";
		if (this.TRANGTHAI.length() > 0)
			query += " AND TIGIA.TRANGTHAI LIKE '%" + TRANGTHAI + "%'";
		this.rsTigia = createSplittingData(50, 10, " PK_SEQ DESC,TRANGTHAI  ", query);
		this.CreateRs();
	}
	public ResultSet getTigiaList()
	{
		return this.rsTigia;
	}
	public void setTigiaList(ResultSet rsKho)
	{
		this.rsTigia = rsKho;
	}
	public String getUserId()
	{
		return this.USERID;
	}
	public void setUserId(String userId)
	{
		this.USERID = userId;
	}
	public String getTuNgay()
	{
		return this.TuNgay;
	}
	public void setTuNgay(String tungay)
	{
		this.TuNgay = tungay;
	}
	public String getDenNgay()
	{
		return this.DenNgay;
	}
	public void setDenNgay(String denngay)
	{
		this.DenNgay = denngay;
	}
	@Override
	public String getCongTy()
	{
		
		return this.CongTy;
	}
	@Override
	public void setCongTy(String CongTy)
	{
		this.CongTy= CongTy;
		
	}
	
	public void closeDB()
	{
		try
		{
			if (this.rsTienteList != null)
				this.rsTienteList.close();
			if (this.rsTigia != null)
				this.rsTigia.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		if (this.db != null)
			this.db.shutDown();
	}
	
	public void setChixem(String chixem) {
		
		this.chixem = chixem;
	}

	public String getChixem() {
		
		return this.chixem;
	}
	
}
