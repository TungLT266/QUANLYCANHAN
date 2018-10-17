package geso.traphaco.erp.beans.tigia.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.erp.beans.tigia.ITiGia_TienTe;
import geso.traphaco.erp.beans.tigia.ITigia;

public class Tigia implements ITigia
{
	String Id,TenTienTe, MaTienTe, TuNgay, DenNgay, TiGiaQuyDoi, UserId, TrangThai, SoLuongGoc, CongTy;
	String msg;
	dbutils db;
	ResultSet CongTyRs;
	List<ITiGia_TienTe> TiGia_TienTeList;

	public Tigia(String id)
	{
		this.db = new dbutils();
		this.Id = id;
		this.TenTienTe = "";
		this.TuNgay = "";
		this.DenNgay = "";
		this.TiGiaQuyDoi = "";
		this.UserId = "";
		this.TrangThai = "1";
		this.SoLuongGoc = "";
		this.msg = "";
		this.CongTy="";
		this.TiGia_TienTeList=new ArrayList<ITiGia_TienTe>();
	}

	public Tigia()
	{
		this.db = new dbutils();
		this.Id = "";
		this.TenTienTe = "";
		this.TuNgay = "";
		this.DenNgay = "";
		this.TiGiaQuyDoi = "";
		this.UserId = "";
		this.TrangThai = "1";
		this.SoLuongGoc = "";
		this.msg = "";
		this.CongTy="";
		this.TiGia_TienTeList=new ArrayList<ITiGia_TienTe>();
	}

	public String getId()
	{
		return this.Id;
	}

	public void setId(String pk_seo)
	{
		this.Id = pk_seo;
	}

	public String getTrangThai()
	{
		return this.TrangThai;
	}

	public void setTrangThai(String trangthai)
	{
		this.TrangThai = trangthai;
	}

	public String getMessage()
	{
		return this.msg;
	}

	public void setMessage(String msg)
	{
		this.msg = msg;
	}

	public void init()
	{
		String query = 	"SELECT TIGIA.PK_SEQ, ISNULL( TIGIA.TuNgay,'')TuNgay, ISNULL(DenNgay,'')DENNGAY, " +
						"ISNULL(TIGIA.TIGIAQUYDOI,0)TIGIAQUYDOI, TIGIA.TIENTE_FK, TIEN.TEN AS TENTIENTE, TIGIA.NGAYTAO, TIGIA.NGAYSUA, " +
						"ISNULL( TIGIA.TRANGTHAI ,1)TRANGTHAI, NV.TEN AS TENNV, NV.PK_SEQ AS MANV, NV2.TEN AS TENNVS, NV2.PK_SEQ AS MANVS, " +
						"CTY.TEN AS TENCTY,CTY.MA AS MACTY, TIGIA.SOLUONGGOC, TIEN.MA AS MATIENTE " +
						"FROM  ERP_TIENTE TIEN " +
						"LEFT JOIN  ERP_TIGIA TIGIA ON TIGIA.TIENTE_FK = TIEN.PK_SEQ " +  
						"INNER JOIN NHANVIEN NV ON TIGIA.NGUOITAO = NV.PK_SEQ " +
						"INNER JOIN NHANVIEN NV2 ON TIGIA.NGUOISUA = NV2.PK_SEQ " +  
						"LEFT JOIN ERP_CONGTY CTY ON CTY.PK_SEQ = TIGIA.CONGTY_FK  WHERE TIGIA.PK_SEQ = '" + this.Id + "' "; 

		System.out.println("__"+query);
		ResultSet rs = db.get(query);
		if (rs != null)
		{
			try
			{
				while (rs.next())
				{
					this.TuNgay = rs.getString("TuNgay");
					this.DenNgay = rs.getString("DenNgay");
					this.TiGiaQuyDoi = rs.getString("TiGiaQuyDoi");
					this.TrangThai = rs.getString("trangthai");
					this.SoLuongGoc = rs.getString("SoLuongGoc");
					this.CongTy = rs.getString("TENCTY");
					this.MaTienTe=rs.getString("MaTienTe");
					this.TenTienTe=rs.getString("TenTienTe");
					
				}
				rs.close();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
	}

	public boolean UpdateTigia()
	{
		try
		{
			this.db.getConnection().setAutoCommit(false);
			String ngaysua = getDateTime();
			String command = "Update Erp_TiGia Set TiGiaQuyDoi='" + this.TiGiaQuyDoi
					+ "',TuNgay='" + this.TuNgay + "',DenNgay='" + this.DenNgay + "',NgaySua='" + ngaysua
					+ "',NguoiSua='" + this.UserId + "',SoLuongGoc='" + this.SoLuongGoc + "',TrangThai='"
					+ this.TrangThai + "' Where PK_SEQ='" + this.Id + "'";
			System.out.println("__Cap Nhat Ty Gia__"+command);
			if (!this.db.update(command))
			{
				this.msg = "khong the cap nhat Ti gia nay,loi tai dong lenh sau :" +command;
				this.db.update("rollback");
				return false;
			}
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			return true;
		} catch (Exception er)
		{
			this.db.update("rollback");
			this.msg = "khong the cap nhat Ti gia nay,loi tai dong lenh sau :" + er.toString();
			er.getMessage();
			return false;
		}
	}


	public void closeDB()
	{
		if (this.db != null)
			this.db.shutDown();	
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public void setTiGiaQuyDoi(String tigiaquydoi)
	{
		this.TiGiaQuyDoi = tigiaquydoi;
	}

	public String getTiGiaQuyDoi()
	{
		return this.TiGiaQuyDoi;
	}

	public String getUserId()
	{
		return this.UserId;
	}

	public void setUserId(String userId)
	{
		this.UserId = userId;
	}

	public void setTuNgay(String tungay)
	{
		this.TuNgay = tungay;
	}

	public String getTuNgay()
	{
		return this.TuNgay;
	}

	public String getDenNgay()
	{
		return this.DenNgay;
	}

	public void setDenNgay(String denngay)
	{
		this.DenNgay = denngay;
	}

	public String getSoLuongGoc()
	{

		return this.SoLuongGoc;
	}

	public void setSoLuongGoc(String SoLuongGoc)
	{
		this.SoLuongGoc = SoLuongGoc;

	}

	public String GetTenTienTe()
	{

		return this.TenTienTe;
	}

	public void setTenTienTe(String TenTienTe)
	{
		this.TenTienTe = TenTienTe;

	}

	public String getTenTienTe()
	{

		return this.TenTienTe;
	}

	public String getMaTienTe()
	{

		return this.MaTienTe;
	}

	public void setMaTienTe(String MaTienTe)
	{
		this.MaTienTe = MaTienTe;

	}

	
	public String getCongTy()
	{

		return this.CongTy;
	}

	
	public void setCongTy(String CongTy)
	{
		this.CongTy = CongTy;

	}

	
	public ResultSet getCongTyRs()
	{
	
		return this.CongTyRs;
	}

	
	public void setCongTyRs(ResultSet CongTyRs)
	{
	this.CongTyRs=CongTyRs;
		
	}

	
	public void createRs()
	{
		String query="SELECT PK_SEQ,MA,TEN FROM ERP_Congty WHERE TRANGTHAI=1";
		this.CongTyRs=this.db.get(query);
	}


	public List<ITiGia_TienTe> getTiGia_TienTeList()
	{
		
		return this.TiGia_TienTeList;
	}

	
	public void setTiGia_TienTeList(List<ITiGia_TienTe> TiGia_TienTeList)
	{
		this.TiGia_TienTeList= TiGia_TienTeList;
	}
	
	public boolean CreateTiGia()
	{
		String query="";
		String ngaytao=getDateTime();
		try
		{
			this.db.getConnection().setAutoCommit(false );
			int sodong=this.TiGia_TienTeList.size();
			for(int i=0;i<sodong;i++)
			{	
				ITiGia_TienTe e=this.TiGia_TienTeList.get(i);
				if(e.getTiGiaId().equals("0"))
				{
				query="INSERT INTO ERP_TIGIA(TIENTE_FK,TIGIAQUYDOI,SOLUONGGOC,CONGTY_FK,TUNGAY,DENNGAY,NGAYTAO,NGAYSUA,NGUOITAO,NGUOISUA,TRANGTHAI)" +
					" VALUES('"+e.getTienTeId()+ "','"+e.getTiGiaQuyDoi()+"','"+e.getSoLuongGoc()+"','"+ this.CongTy +"','"+e.getTuNgay()+"','"+e.getDenNgay()+"','"+ngaytao+"','"+ngaytao+"','"+this.UserId+"','"+this.UserId+"',1)";
				}else
				{
					query="UPDATE ERP_TIGIA SET SOLUONGGOC='"+e.getSoLuongGoc()+"',TiGiaQuyDoi='"+e.getTiGiaQuyDoi()+"',TuNgay='"+e.getTuNgay()+"',DenNgay='"+e.getDenNgay()+"'" +
							" WHERE PK_SEQ='"+e.getTiGiaId()+"' " ;
				}
				if(e.getTuNgay().trim().length()==0||e.getDenNgay().trim().length()==0)
				{
					this.db.update("rollback");
					this.setMessage("Vui lòng điền đầy đủ thông tin");
					return false;
				}
				
				if(!this.db.update(query))
				{
					this.db.update("rollback");
					this.setMessage("Khong the tao moi ti gia "+query);
					return false;
				}
			}		
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		} catch (SQLException e)
		{
			this.db.update("rollback");
			e.printStackTrace();
			return false;
		}
		return true;
	}


	public void CreateTiGiaTienTeList()
	{
		if(this.CongTy.trim().length()>0)
		{
		/*	String query =
					" SELECT TT.PK_SEQ AS TIENTEID,TT.MA AS MATIENTE ,TT.TEN AS TENTIENTE,TT.TRANGTHAI,ISNULL(TG.TUNGAY,'"+getDateTime()+"')TUNGAY, "+
					" ISNULL(TG.DENNGAY,'"+getDateTime()+"')DENNGAY,ISNULL(TG.SOLUONGGOC,1)SOLUONGGOC, "+
					" ISNULL(TG.TIGIAQUYDOI,1)TIGIAQUYDOI, CTY.MA AS MACTY, CTY.TEN AS TENCTY, CTY.PK_SEQ AS CONGTYID, ISNULL(TG.PK_SEQ,0)TIGIAID "+
					" FROM ERP_TIENTE TT LEFT JOIN ERP_TIGIA TG "+
					" ON TG.TIENTE_FK=TT.PK_SEQ AND TG.CONGTY_FK='"+this.CongTy+"'  "+
					" CROSS JOIN ( SELECT PK_SEQ,MA,TEN FROM ERP_CONGTY  WHERE PK_SEQ='"+ this.CongTy + "') CTY ";*/
			String query =
				 	" SELECT TT.PK_SEQ AS TIENTEID, TT.MA AS MATIENTE , TT.TEN AS TENTIENTE,TT.TRANGTHAI, '' AS TUNGAY, \n"+
				    "        '' AS DENNGAY, 1 AS SOLUONGGOC,  '' AS TIGIAQUYDOI, CTY.MA AS MACTY, CTY.TEN AS TENCTY, CTY.PK_SEQ AS CONGTYID, 0 AS  TIGIAID \n"+
				    " FROM ERP_TIENTE TT \n"+
				    " CROSS JOIN ( SELECT PK_SEQ,MA,TEN \n"+
				    "              FROM ERP_CONGTY  \n"+
				    "              WHERE PK_SEQ='100005') CTY  \n";
			
		ResultSet rsTiGia_TienTe=this.db.get(query);
		System.out.println("__danh sach ti gia tien te___"+query);
		if(this.TiGia_TienTeList!=null)
			this.TiGia_TienTeList.clear();
		if(rsTiGia_TienTe!=null)
		{
			try
			{
				while(rsTiGia_TienTe.next())
				{
					ITiGia_TienTe e=null;
					e=new TiGia_TienTe();
					e.setTiGiaId(rsTiGia_TienTe.getString("TiGiaId"));
					e.setTienTeId(rsTiGia_TienTe.getString("TienTeId"));
//					e.setCongTyId(rsTiGia_TienTe.getString("CongTyId"));
					e.setTuNgay(rsTiGia_TienTe.getString("TuNgay"));
					e.setDenNgay(rsTiGia_TienTe.getString("DenNgay"));
					e.setMaTienTe(rsTiGia_TienTe.getString("MaTienTe"));
					e.setTenTienTe(rsTiGia_TienTe.getString("TenTienTe"));
					e.setTiGiaQuyDoi(rsTiGia_TienTe.getString("TiGiaQuyDoi"));
					e.setSoLuongGoc(rsTiGia_TienTe.getString("SoLuongGoc"));
					this.TiGia_TienTeList.add(e);
				}
				rsTiGia_TienTe.close();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
	}
		
	}
}