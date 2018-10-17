package geso.erp.beans.lapkehoach.imp;

import geso.dms.db.sql.dbutils;
import geso.erp.beans.danhmucvattu.IDanhmucvattu_SP;
import geso.erp.beans.danhmucvattu.imp.Danhmucvattu_SP;
import geso.erp.beans.lapkehoach.IErpKichbansanxuat;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ErpKichbansanxuat implements IErpKichbansanxuat
{
	String ctyId;
	String userId;
	String id;
	
	String ma;
	String diengiai;
	String soluongchuan;
	String trangthai;
	ResultSet spRs;
	String spIds;
	String cpTT;
	
	ResultSet daychuyenRs;
	String daychuyenIds;
	
	ResultSet bomRs;
	String bomIds;
	
	List<IDanhmucvattu_SP> dmvtList;
	String msg;
	
	dbutils db;
	
	public ErpKichbansanxuat()
	{
		this.ctyId = "";
		this.userId = "";
		this.id = "";
		this.ma = "";
		this.diengiai = "";
		this.soluongchuan = "";
		this.spIds = "";
		this.cpTT = "0";
		this.trangthai="1";
		this.daychuyenIds = "";
		this.bomIds = "";
		this.msg = "";
		
		this.dmvtList = new ArrayList<IDanhmucvattu_SP>();
		this.db = new dbutils();
	}
	
	public ErpKichbansanxuat(String id)
	{
		this.ctyId = "";
		this.id = id;
		this.ma = "";
		this.diengiai = "";
		this.soluongchuan = "";
		this.spIds = "";
		this.cpTT = "0";
		this.trangthai="1";
		this.daychuyenIds = "";
		this.bomIds = "";
		this.msg = "";
		
		this.dmvtList = new ArrayList<IDanhmucvattu_SP>();
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
	
	public String getId() 
	{
		return this.id;
	}

	public void setId(String Id) 
	{
		this.id = Id;
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

	public void init() 
	{
		String query = "select ma, diengiai, SANPHAM_FK, DayChuyen_fk,TrangThai from Erp_KichBanSanXuat where PK_SEQ = '" + this.id + "'";
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					this.ma = rs.getString("ma");
					this.diengiai = rs.getString("diengiai");
					this.spIds = rs.getString("SANPHAM_FK");
					this.daychuyenIds = rs.getString("DayChuyen_fk");
					this.bomIds = rs.getString("BOM_fk");
					this.trangthai=rs.getString("TrangThai");
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				System.out.println("__Exception: " + e.getMessage());
			}
		}
			
		query = "SELECT SP1.MA AS vtMa, SP1.TEN AS vtTen, DVDL1.DONVI AS vtDonvi, VATTU.SOLUONG,  " +
				"ISNULL(SP2.MA, '') AS vtTTMa, ISNULL(SP2.TEN, '') AS vtTTTen, ISNULL(DVDL2.donvi, '') AS vtTTDonvi, " + 
				"VATTU.tile, DANHMUC.SOLUONGCHUAN, DANHMUC.CHOPHEPTT AS CHOPHEPTHAYTHE    " +
				"FROM ERP_KICHBANSANXUAT KICHBAN	" +			
				"INNER JOIN ERP_DANHMUCVATTU DANHMUC ON DANHMUC.PK_SEQ = KICHBAN.BOM_FK " +
				"INNER JOIN ERP_DANHMUCVATTU_VATTU VATTU ON VATTU.DANHMUCVT_FK = DANHMUC.PK_SEQ " +
				"INNER JOIN SANPHAM SP1 ON VATTU.VATTU_FK = SP1.PK_SEQ " +
				"LEFT  JOIN SANPHAM SP2 ON VATTU.VATTUTT_FK = SP2.PK_SEQ  " + 
				"INNER JOIN DONVIDOLUONG DVDL1 ON SP1.DVDL_FK = DVDL1.PK_SEQ " +
				"LEFT  JOIN DONVIDOLUONG DVDL2 ON SP2.DVDL_FK = DVDL2.PK_SEQ " +
				"WHERE KICHBAN.PK_SEQ = '" + this.id + "'";
		
		System.out.println("__Khoi tao BOM: " + query);
		
		rs = db.get(query);
		if(rs != null)
		{
			List<IDanhmucvattu_SP> dmvtList = new ArrayList<IDanhmucvattu_SP>();
			
			try 
			{
				IDanhmucvattu_SP vt = null;
				while(rs.next())
				{
					vt = new Danhmucvattu_SP();
					
					vt.setMaVatTu(rs.getString("vtMa"));
					vt.setTenVatTu(rs.getString("vtTen"));
					vt.setDvtVT(rs.getString("vtDonvi"));
					vt.setSoLuong(rs.getString("SOLUONG"));
					
					vt.setMaVatTuThayThe(rs.getString("vtTTMa"));
					vt.setTenVatTuThayThe(rs.getString("vtTTTen"));
					vt.setDvtThayThe(rs.getString("vtTTDonvi"));
					vt.setTile(rs.getString("tile"));
					
					if(this.soluongchuan.trim().length() <= 0)
						this.soluongchuan = rs.getString("soluongchuan");
					
					this.cpTT = rs.getString("chophepthaythe");
					
					dmvtList.add(vt);
					
				}
				rs.close();
				
				this.dmvtList = dmvtList;
			} 
			catch (Exception e) 
			{
				System.out.println("__Exception: " + e.getMessage());
			}
		}
		
		this.createRs();
	}
	
	public ResultSet getSpRs() 
	{
		return this.spRs;
	}

	public void setSpRs(ResultSet spRs)
	{
		this.spRs = spRs;	
	}

	public String getSpIds() 
	{
		return this.spIds;
	}

	public void setSpIds(String spIds) 
	{
		this.spIds = spIds;
	}


	public boolean createKichbansanxuat()
	{	
		try 
		{
			
			if(this.ma.trim().length() <= 0)
			{
				this.msg = "Vui lòng nhập mã kịch bản";
				return false;
			}
			
			if(this.spIds.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn sản phẩm";
				return false;
			}
			
			if(this.daychuyenIds.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn dây chuyền sản xuất";
				return false;
			}
			
			if(this.bomIds.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn BOM";
				return false;
			}
			
			db.getConnection().setAutoCommit(false);
			
			String query = "INSERT ERP_KICHBANSANXUAT(CONGTY_FK, MA, DIENGIAI, SANPHAM_FK, DAYCHUYEN_FK, BOM_FK, " +
							"NGUOITAO, NGUOISUA, NGAYTAO, NGAYSUA, TRANGTHAI) " +
	   						"VALUES (" + this.ctyId + ", N'" + this.ma + "', N'" + this.diengiai + "', '" + this.spIds + "','" + this.daychuyenIds +"','" + this.bomIds + "', " +
	   						"'" + this.userId +"','" + this.userId + "','" + this.getDateTime() + "','" + this.getDateTime() + "', '1')";
			
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới Kichbansanxuat: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			this.msg = "Loi: " + e.getMessage();
			try 
			{
				db.getConnection().rollback();
			} 
			catch (SQLException e1) {}
			return false;
		}
		
		return true;
	}
	
	public boolean updateKichbansanxuat() 
	{
		
		try 
		{
			
			if(this.ma.trim().length() <= 0)
			{
				this.msg = "Vui lòng nhập mã kịch bản";
				return false;
			}
			
			if(this.spIds.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn sản phẩm";
				return false;
			}
			
			if(this.daychuyenIds.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn dây chuyền sản xuất";
				return false;
			}
			
			
			if(this.bomIds.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn BOM";
				return false;
			}
			
			db.getConnection().setAutoCommit(false);
			
			String query = "update Erp_KichBanSanXuat set MA = N'" + this.ma + "', DIENGIAI = N'" + this.diengiai + "', SANPHAM_FK = '" + this.spIds + "', " +
							"DAYCHUYEN_FK = '" + this.daychuyenIds + "', BOM_FK = '" + this.bomIds + "', NGUOISUA = '" + this.userId + "', NGAYSUA = '" + this.getDateTime() + "'" +
							" where pk_seq = '" + this.id + "' ";
	   					
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật Kichbansanxuat: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			this.msg = "Loi: " + e.getMessage();
			try 
			{
				db.getConnection().rollback();
			} 
			catch (SQLException e1) {}
			return false;
		}
		
		return true;
		
	}

	public void createRs() 
	{
		this.spRs = db.get("select pk_seq, ten from sanpham where trangthai = '1' and loaisanpham_fk in ( 100005, 100011 ) and congty_fk = " + this.ctyId + " ");
		
		if(this.spIds.trim().length() > 0)
		{
			this.daychuyenRs = db.get("select pk_seq, ma from Erp_DayChuyenSanXuat where sanpham_fk = '" + this.spIds + "' and trangthai = '1' and congty_fk = " + this.ctyId + " ");
			//System.out.println("1.Day chuyen SX: select pk_seq, ma from Erp_DayChuyenSanXuat where sanpham_fk = '" + this.spIds + "' and trangthai = '1'");
			
			this.bomRs = db.get("select PK_SEQ, DIENGIAI from ERP_DANHMUCVATTU where SANPHAM_FK = '" + this.spIds + "' and trangthai = '1' " +
								"AND HIEULUCDEN > '" + this.getDateTime() + "' AND CONGTY_FK = " + this.ctyId + " ");
			//System.out.println("2.BOM SX: select PK_SEQ, DIENGIAI from ERP_DANHMUCVATTU where SANPHAM_FK = '" + this.spIds + "' and trangthai = '1'");
		}
	}
	
	
	public String getDiengiai()
	{
		return this.diengiai;
	}

	public void setDiengiai(String diengiai)
	{
		this.diengiai = diengiai;
	}

	public String getSpId() 
	{
		return this.spIds;
	}

	public void setSpId(String spId) 
	{
		this.spIds = spId;
	}

	public String getSoluongchuan() 
	{
		return this.soluongchuan;
	}

	public void setSoluongchuan(String slgchuan) 
	{
		this.soluongchuan = slgchuan;
	}

	public void setListDanhMuc(List<IDanhmucvattu_SP> list) 
	{
		this.dmvtList = list;
	}

	public List<IDanhmucvattu_SP> getListDanhMuc() 
	{
		return this.dmvtList;
	}

	public String getChophepTT() 
	{
		return this.cpTT;
	}

	public void setChophepTT(String chophepTT) 
	{
		this.cpTT = chophepTT;
	}

	public void DbClose() 
	{
		try 
		{
			if (this.dmvtList != null)
				this.dmvtList.clear(); 
			if(this.spRs != null)
				this.spRs.close();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.db.shutDown();
		}
	}
	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	
	public ResultSet getDaychuyenRs() 
	{
		return this.daychuyenRs;
	}

	public void setDaychuyenRs(ResultSet dcRs) 
	{
		this.daychuyenRs = dcRs;
	}

	public ResultSet getBomRs() 
	{
		return this.bomRs;
	}

	public void setBomRs(ResultSet bomRs) 
	{
		this.bomRs = bomRs;
	}

	public String getBomId() 
	{
		return this.bomIds;
	}

	public void setBomId(String bomId) 
	{
		this.bomIds = bomId;
	}

	public String getMa()
	{
		return this.ma;
	}

	public void setMa(String ma) 
	{
		this.ma = ma;
	}

	public String getDaychuyenId() 
	{
		return this.daychuyenIds;
	}

	public void setDaychuyenId(String dcId)
	{
		this.daychuyenIds = dcId;
	}

	public void changeBom()
	{
		if(this.bomIds.trim().length() > 0)
		{
			String query = 	"SELECT SP1.MA AS vtMa, SP1.TEN AS vtTen, DVDL1.DONVI AS vtDonvi, VATTU.SOLUONG,  " +
							"ISNULL(SP2.MA, '') AS vtTTMa, ISNULL(SP2.TEN, '') AS vtTTTen, ISNULL(DVDL2.donvi, '') AS vtTTDonvi, " + 
							"VATTU.tile, DANHMUC.SOLUONGCHUAN, DANHMUC.CHOPHEPTT    " +	
							"FROM ERP_DANHMUCVATTU DANHMUC  " +
							"INNER JOIN ERP_DANHMUCVATTU_VATTU VATTU ON VATTU.DANHMUCVT_FK = DANHMUC.PK_SEQ " +
							"INNER JOIN SANPHAM SP1 ON VATTU.VATTU_FK = SP1.PK_SEQ " +
							"LEFT  JOIN SANPHAM SP2 ON VATTU.VATTUTT_FK = SP2.PK_SEQ  " + 
							"INNER JOIN DONVIDOLUONG DVDL1 ON SP1.DVDL_FK = DVDL1.PK_SEQ " +
							"LEFT  JOIN DONVIDOLUONG DVDL2 ON SP2.DVDL_FK = DVDL2.PK_SEQ " +
							"WHERE DANHMUC.PK_SEQ = '" + this.bomIds + "'";
			
			System.out.println("Change BOM: " + query);
			
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				List<IDanhmucvattu_SP> dmvtList = new ArrayList<IDanhmucvattu_SP>();
				
				try 
				{
					IDanhmucvattu_SP vt = null;
					while(rs.next())
					{
						vt = new Danhmucvattu_SP();
						
						vt.setMaVatTu(rs.getString("vtMa"));
						vt.setTenVatTu(rs.getString("vtTen"));
						vt.setDvtVT(rs.getString("vtDonvi"));
						vt.setSoLuong(rs.getString("SOLUONG"));
						
						vt.setMaVatTuThayThe(rs.getString("vtTTMa"));
						vt.setTenVatTuThayThe(rs.getString("vtTTTen"));
						vt.setDvtThayThe(rs.getString("vtTTDonvi"));
						vt.setTile(rs.getString("tile"));
						
						if(this.soluongchuan.trim().length() <= 0)
							this.soluongchuan = rs.getString("soluongchuan");
						
						this.cpTT = rs.getString("chopheptt");
						
						dmvtList.add(vt);
						
					}
					rs.close();
					
					this.dmvtList = dmvtList;
				} 
				catch (Exception e) 
				{
					System.out.println("__Loi khi khoi tao SP cua BOM: " + e.getMessage());
				}
			}
			
		}
	}

	@Override
	public String getTrangthai()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTrangthai(String trangthai)
	{
		// TODO Auto-generated method stub
		
	}
}
