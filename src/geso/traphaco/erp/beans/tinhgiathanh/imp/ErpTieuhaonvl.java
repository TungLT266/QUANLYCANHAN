package geso.traphaco.erp.beans.tinhgiathanh.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import geso.traphaco.erp.beans.danhmucvattu.IDanhmucvattu_SP;
import geso.traphaco.erp.beans.danhmucvattu.imp.Danhmucvattu_SP;
import geso.traphaco.erp.beans.tinhgiathanh.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import geso.traphaco.erp.db.sql.dbutils;

public class ErpTieuhaonvl implements IErpTieuhaonvl
{
	String userId;
	String id;
	
	String diengiai;
	String soluongchuan;
	
	ResultSet spRs;
	String spIds;
	String cpTT;
	
	List<IDanhmucvattu_SP> dmvtList;
	String msg;
	
	dbutils db;
	
	public ErpTieuhaonvl()
	{
		this.userId = "";
		this.id = "";
		this.diengiai = "";
		this.soluongchuan = "";
		this.spIds = "";
		this.cpTT = "0";
		
		this.msg = "";
		
		this.dmvtList = new ArrayList<IDanhmucvattu_SP>();
		this.db = new dbutils();
	}
	
	public ErpTieuhaonvl(String id, String kehoachId)
	{
		this.userId = "";
		this.id = "";
		this.diengiai = "";
		this.soluongchuan = "";
		this.spIds = "";
		this.cpTT = "0";
		
		this.msg = "";
		
		this.dmvtList = new ArrayList<IDanhmucvattu_SP>();
		this.db = new dbutils();
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
		String query = "select SANPHAM_FK, soluongchuan, chophepTT, diengiai from ERP_DANHMUCVATTU where PK_SEQ = '" + this.id + "'";
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					this.spIds = rs.getString("SANPHAM_FK");
					this.soluongchuan = rs.getString("soluongchuan");
					this.cpTT = rs.getString("chophepTT");
					this.diengiai = rs.getString("diengiai");
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				System.out.println("__Exception: " + e.getMessage());
			}
		}
			
		query = "select b.MA as vtMa, b.TEN as vtTen, d.DONVI as vtDonvi, a.SOLUONG,  ISNULL(c.MA, '') as vtTTMa, ISNULL(c.TEN, '') as vtTTTen, ISNULL(e.donvi, '') as vtTTDonvi, a.tile   " +
				"from ERP_DANHMUCVATTU_VATTU a inner join SANPHAM b on a.vattu_fk = b.PK_SEQ left join SANPHAM c on a.vattutt_fk = c.pk_seq  " +
				"inner join DONVIDOLUONG d on b.DVDL_FK = d.PK_SEQ left join DONVIDOLUONG e on c.DVDL_FK = e.PK_SEQ   " +
				"where DANHMUCVT_FK = '" + this.id + "'";
		
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


	public boolean createBom()
	{	
		try 
		{
			//Check san pham
			if(this.spIds.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn sản phẩm";
				return false;
			}
			
			if(this.soluongchuan.trim().length() <= 0)
			{
				this.msg = "Vui lòng nhập số lượng chuẩn";
				return false;
			}
			
			if(this.dmvtList.size() <= 0)
			{
				this.msg = "Vui lòng nhập đày đủ thông tin vật tư";
				return false;
			}
			
			db.getConnection().setAutoCommit(false);
			
			String query = "INSERT ERP_DANHMUCVATTU(DIENGIAI, SANPHAM_FK, SOLUONGCHUAN, NGUOITAO, NGUOISUA, NGAYTAO, NGAYSUA, TRANGTHAI, CHOPHEPTT) " +
	   						"VALUES (N'" + this.diengiai + "', '" + this.spIds + "','" + this.soluongchuan +"','" + this.userId +"','" + this.userId + "','" + this.getDateTime() + "','" + this.getDateTime() + "', '1', '" + this.cpTT + "')";
			
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới BOM: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			for(int i = 0; i < this.dmvtList.size(); i++)
			{
				IDanhmucvattu_SP vattu = this.dmvtList.get(i);
				
				if(vattu.getMaVatTu().trim().length() > 0 && vattu.getSoLuong().trim().length() > 0)
				{
					if(this.cpTT.equals("0"))
					{
						query = "insert ERP_DANHMUCVATTU_VATTU(DANHMUCVT_FK, VATTU_FK, SOLUONG)  select IDENT_CURRENT('ERP_DANHMUCVATTU'), pk_seq, '" + vattu.getSoLuong() + "' from sanpham where ma = '" + vattu.getMaVatTu() + "' ";
					}
					else
					{
						if(vattu.getMaVatTuThayThe().trim().length() <= 0  || vattu.getTile().trim().length() <= 0 )
						{
							this.msg = "Vui lòng kiểm tra lại thông tin của vật tư thay thể vật tư (" + vattu.getMaVatTu() + ") ";
							db.getConnection().rollback();
							return false;
						}
						query = "insert ERP_DANHMUCVATTU_VATTU(DANHMUCVT_FK, VATTU_FK, SOLUONG, VATTUTT_FK, TiLe)  " +
								"select IDENT_CURRENT('ERP_DANHMUCVATTU') as DANHMUCVT_FK, a.pk_seq as vattu_fk, '" + vattu.getSoLuong() + "', b.PK_SEQ as vttt_fk, '" + vattu.getTile() + "' " +
								"from SANPHAM a, SANPHAM b where a.ma = '" + vattu.getMaVatTu() + "' and b.MA = '" + vattu.getMaVatTuThayThe() + "' ";
					}
					
					if(!db.update(query))
					{
						this.msg = "Không thể tạo mới BOM: " + query;
						db.getConnection().rollback();
						return false;
					}
					
				}
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
	
	public boolean updateBom() 
	{
		
		try 
		{
			//Check san pham
			if(this.spIds.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn sản phẩm";
				return false;
			}
			
			if(this.soluongchuan.trim().length() <= 0)
			{
				this.msg = "Vui lòng nhập số lượng chuẩn";
				return false;
			}
			
			if(this.dmvtList.size() <= 0)
			{
				this.msg = "Vui lòng nhập đày đủ thông tin vật tư";
				return false;
			}
			
			db.getConnection().setAutoCommit(false);
			
			String query = "UPDATE ERP_DANHMUCVATTU set DIENGIAI = N'" + this.diengiai + "', SANPHAM_FK = '" + this.spIds + "', SOLUONGCHUAN = '" + this.soluongchuan + "', " +
								"NGUOISUA = '" + this.userId + "', NGAYSUA = '" + this.getDateTime() + "', CHOPHEPTT = '" + this.cpTT + "' where pk_seq = '" + this.id + "' "; 
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật BOM: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_DANHMUCVATTU_VATTU where DANHMUCVT_FK = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật BOM: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			for(int i = 0; i < this.dmvtList.size(); i++)
			{
				IDanhmucvattu_SP vattu = this.dmvtList.get(i);
				
				if(vattu.getMaVatTu().trim().length() > 0 && vattu.getSoLuong().trim().length() > 0)
				{
					if(this.cpTT.equals("0"))
					{
						query = "insert ERP_DANHMUCVATTU_VATTU(DANHMUCVT_FK, VATTU_FK, SOLUONG)  " +
								"select '" + this.id + "', pk_seq, '" + vattu.getSoLuong() + "' from sanpham where ma = '" + vattu.getMaVatTu() + "' ";
					}
					else
					{
						if(vattu.getMaVatTuThayThe().trim().length() <= 0  || vattu.getTile().trim().length() <= 0 )
						{
							this.msg = "Vui lòng kiểm tra lại thông tin của vật tư thay thể vật tư (" + vattu.getMaVatTu() + ") ";
							db.getConnection().rollback();
							return false;
						}
						query = "insert ERP_DANHMUCVATTU_VATTU(DANHMUCVT_FK, VATTU_FK, SOLUONG, VATTUTT_FK, TiLe)  " +
								"select '" + this.id + "', a.pk_seq as vattu_fk, '" + vattu.getSoLuong() + "', b.PK_SEQ as vttt_fk, '" + vattu.getTile() + "' " +
								"from SANPHAM a, SANPHAM b where a.ma = '" + vattu.getMaVatTu() + "' and b.MA = '" + vattu.getMaVatTuThayThe() + "' ";
					}
					
					if(!db.update(query))
					{
						this.msg = "Không thể tạo mới BOM: " + query;
						db.getConnection().rollback();
						return false;
					}
					
				}
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
		this.spRs = db.get("select pk_seq, ten from sanpham where trangthai = '1' and loaisanpham_fk in ( 100005, 100011 ) ");
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

	
	
	
	

}
