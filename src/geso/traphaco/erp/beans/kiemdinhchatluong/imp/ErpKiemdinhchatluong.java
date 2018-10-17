package geso.traphaco.erp.beans.kiemdinhchatluong.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import geso.traphaco.erp.beans.kiemdinhchatluong.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import geso.traphaco.center.beans.thongtinsanpham.ITieuchikiemdinh;
import geso.traphaco.center.beans.thongtinsanpham.imp.Tieuchikiemdinh;
import geso.traphaco.erp.db.sql.dbutils;

public class ErpKiemdinhchatluong implements IErpKiemdinhchatluong
{
	String userId;
	String ycId;
	
	String spId;
	String spTen;
	
	String nkId;
	String solo;
	String soluong;
	String denghixuly = "";
	
	String msg;
	
	List<ITieuchikiemdinh> tckdList;
	
	dbutils db;
	
	public ErpKiemdinhchatluong()
	{
		this.userId = "";
		this.ycId = "";
		this.spId = "";
		this.spTen = "";
		this.nkId = "";
		this.solo = "";
		this.soluong = "";
		this.msg = "";
		
		this.tckdList = new ArrayList<ITieuchikiemdinh>();
		this.db = new dbutils();
	}
	
	public ErpKiemdinhchatluong(String id)
	{
		this.ycId = id;
		this.spId = "";
		this.spTen = "";
		this.nkId = "";
		this.solo = "";
		this.soluong = "";
		this.msg = "";
		this.msg = "";
		
		this.tckdList = new ArrayList<ITieuchikiemdinh>();
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

	public String getYcId() 
	{
		return this.ycId;
	}

	public void setYcId(String ycId) 
	{
		this.ycId = ycId;
	}

	public String getSpId() 
	{
		return this.spId;
	}

	public void setSpId(String spId) 
	{
		this.spId = spId;
	}

	public String getSpTen() 
	{
		return this.spTen;
	}

	public void setSpTen(String spTen)
	{
		this.spTen = spTen;
	}

	public String getNhapkhoId() 
	{
		return this.nkId;
	}

	public void setNhapkhoId(String nkId)
	{
		this.nkId = nkId;
	}

	public String getSolo() 
	{
		return this.solo;
	}

	public void setSolo(String solo) 
	{
		this.solo = solo;
	}

	public String getSoluong()
	{
		return this.soluong;
	}

	public void setSoluong(String soluong) 
	{
		this.soluong = soluong;
	}

	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}
	
	public void setTieuchikiemdinhList(List<ITieuchikiemdinh> list) 
	{
		this.tckdList = list;
	}

	public List<ITieuchikiemdinh> getTieuchikiemdinhList() 
	{
		return this.tckdList;
	}
	
	public boolean updateKiemdinh()
	{
		if(this.tckdList.size() <= 0)
		{
			this.msg = "Không có tiêu chí kiểm định chất lượng nào.";
			return false;
		}
		else
		{
			for(int i = 0; i < this.tckdList.size(); i++)
			{
				ITieuchikiemdinh tckd = this.tckdList.get(i);
				
				if(!tckd.getDat().equals("1"))
				{
					this.msg = "Tiêu chí: " + tckd.getTieuchi() + " không đạt, vui lòng kiểm tra lại.";
					return false;
				}
			}
		}
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			for(int i = 0; i < this.tckdList.size(); i++)
			{
				ITieuchikiemdinh tckd = this.tckdList.get(i);
				
				String query = "insert ERP_YeuCauKiemDinh_TieuChi(yeucaukiemdinh_fk, tieuchi, pheptoan, giatrichuan, diemdat, dat, trangthai) " +
								"values('" + this.ycId + "', N'" + tckd.getTieuchi() + "', '" + tckd.getToantu() + "', '" + tckd.getGiatrichuan() + "', '" + tckd.getDiemdat() + "', '" + tckd.getDat() + "', N'" + tckd.getTrangthai() + "')";
				
				if(!db.update(query))
				{
					this.msg = "Không thể cập nhật ERP_YeuCauKiemDinh_TieuChi " + query;
					db.getConnection().commit();
					return false;
				}
				
			}
			
			String query = "update ERP_YeuCauKiemDinh set trangthai = '1', nguoisua = '" + this.userId + "', ngaysua = '" + getDateTime() + "' where pk_seq = '" + this.ycId + "'";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_YeuCauKiemDinh " + query;
				db.getConnection().commit();
				return false;
			}
			
			//Tang kho
			query = "select a.sanpham_fk, b.solo, a.soluong, a.khu, a.vitri, b.ngaysanxuat, b.ngayhethan, b.ngaykiem  " +
					"from ERP_YeuCauKiemDinh_ChiTiet a inner join ERP_YeuCauKiemDinh b on a.yeucaukiemdinh_fk = b.pk_seq   " +
					"where yeucaukiemdinh_fk = '" + this.ycId + "'";
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				while(rs.next())
				{
					String sanpham_fk = rs.getString("sanpham_fk");
					String solo = rs.getString("solo");
					String soluong = rs.getString("soluong");
					String vitri = rs.getString("vitri");
					String ngaysanxuat = rs.getString("ngaysanxuat");
					String ngayhethan = rs.getString("ngayhethan");
					String ngaykiem = rs.getString("ngaykiem");
					
					//Tang kho 
					query = "select count(*) as sodong from ERP_KHOTT_SP_CHITIET " +
							"where khott_fk = (select KhoTT_FK from ERP_VITRIKHO where pk_seq = '" + vitri + "') " +
									"and sanpham_fk = '" + sanpham_fk + "' and solo = '" + solo + "' and bin = '" + vitri + "' ";
					
					ResultSet rsCheck = db.get(query);
					boolean flag = true;
					if (rsCheck != null)
					{
						if (rsCheck.next())
						{
							if (rsCheck.getString("sodong").equals("0"))
								flag = false;
							rsCheck.close();
						}
					}
			
					if(flag)
					{
						query = "update ERP_KHOTT_SP_CHITIET set soluong = soluong + '" + soluong + "', AVAILABLE = AVAILABLE + '" + soluong + "', NGAYSANXUAT = '" + ngaysanxuat + "', " +
									"NGAYHETHAN = '" + ngayhethan + "', NGAYNHAPKHO = '" + ngaykiem + "'   " +
								"where KHOTT_FK = (select KhoTT_FK from ERP_VITRIKHO where pk_seq = '" + vitri + "') " +
										"and SANPHAM_FK = '" + sanpham_fk + "' and SOLO = '" + solo + "' and BIN = '" + vitri + "'";
					}
					else
					{
						query = "insert ERP_KHOTT_SP_CHITIET(KHOTT_FK, SANPHAM_FK, SOLUONG, BOOKED, AVAILABLE, SOLO, NGAYSANXUAT, NGAYHETHAN, NGAYNHAPKHO, BIN) " +
								"select KhoTT_fk, '" + sanpham_fk + "', '" + soluong + "', '0', '" + soluong + "', '" + solo + "', '" + ngaysanxuat + "', '" + ngayhethan + "', '" + ngaykiem + "', '" + vitri + "' " +
								"from ERP_VITRIKHO where pk_seq = '" + vitri + "'";
					}
					
					System.out.println("1.Kho chi tiet: " + query);
					
					if(!db.update(query))
					{
						this.msg = "Không thể cập nhật ERP_KHOTT_SP_CHITIET " + query;
						db.getConnection().commit();
						return false;
					}
					
					
					//Chuyen tu trangthai = 0 ---- > trangthai = 1
					query = "update ERP_KHOTT_SP_CHITIET_TRANGTHAI set soluong = soluong - '" + soluong + "', AVAILABLE = AVAILABLE - '" + soluong + "'  " +
							"where KHOTT_FK = (select KhoTT_FK from ERP_VITRIKHO where pk_seq = '" + vitri + "') " +
									"and SANPHAM_FK = '" + sanpham_fk + "' and SOLO = '" + solo + "' and BIN = '" + vitri + "' and trangthai = '0' ";
					System.out.println("2.Kho chi tiet - trang thai: " + query);
					
					if(!db.update(query))
					{
						this.msg = "Không thể cập nhật ERP_KHOTT_SP_CHITIET_TRANGTHAI " + query;
						db.getConnection().commit();
						return false;
					}
					
					
					query = "select count(*) as sodong from ERP_KHOTT_SP_CHITIET_TRANGTHAI " +
							"where khott_fk = (select KhoTT_FK from ERP_VITRIKHO where pk_seq = '" + vitri + "') " +
									"and sanpham_fk = '" + sanpham_fk + "' and solo = '" + solo + "' and bin = '" + vitri + "' and trangthai = '1'";
					rsCheck = db.get(query);
					flag = true;
					if (rsCheck != null)
					{
						if (rsCheck.next())
						{
							if (rsCheck.getString("sodong").equals("0"))
								flag = false;
							rsCheck.close();
						}
					}
					
					if(flag)
					{
						query = "update ERP_KHOTT_SP_CHITIET_TRANGTHAI set soluong = soluong + '" + soluong + "', AVAILABLE = AVAILABLE + '" + soluong + "'  " +
								"where KHOTT_FK = (select KhoTT_FK from ERP_VITRIKHO where pk_seq = '" + vitri + "') " +
										"and SANPHAM_FK = '" + sanpham_fk + "' and SOLO = '" + solo + "' and BIN = '" + vitri + "' and trangthai = '1' ";
					}
					else
					{
						query = "insert ERP_KHOTT_SP_CHITIET_TRANGTHAI(KHOTT_FK, SANPHAM_FK, SOLUONG, BOOKED, AVAILABLE, SOLO, BIN, TrangThai) " +
								"select KhoTT_fk, '" + sanpham_fk + "', '" + soluong + "', '0', '" + soluong + "', '" + solo + "', '" + vitri + "', '1' " +
								"from ERP_VITRIKHO where pk_seq = '" + vitri + "'";
					}
					
					System.out.println("3.Kho chi tiet - trang thai: " + query);
					
					if(!db.update(query))
					{
						this.msg = "Không thể cập nhật ERP_KHOTT_SP_CHITIET_TRANGTHAI " + query;
						db.getConnection().commit();
						return false;
					}
					
					//delete trangthai 0
					query = "delete ERP_KHOTT_SP_CHITIET_TRANGTHAI where soluong = 0 and available = 0 and booked = 0";
					if(!db.update(query))
					{
						this.msg = "4.4.Khong the cap nhat ERP_KHOTT_SP_CHITIET_TRANGTHAI: " + query;
						db.getConnection().rollback();
						return false;
					}
					
					query = "update ERP_KHOTT_SANPHAM set soluong = soluong + '" + soluong + "', AVAILABLE = AVAILABLE + '" + soluong + "'  " +
							"where KHOTT_FK = (select KhoTT_FK from ERP_VITRIKHO where pk_seq = '" + vitri + "') and SANPHAM_FK = '" + sanpham_fk + "'  ";

					System.out.println("4.Kho san pham: " + query);
					
					if(!db.update(query))
					{
						this.msg = "Không thể cập nhật ERP_KHOTT_SANPHAM " + query;
						db.getConnection().commit();
						return false;
					}
					
				}
				rs.close();
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(false);
		} 
		catch (Exception e) 
		{
			System.out.println("5.Exception: " + e.getMessage());
			db.update("rollback");
			return false;
		}
		
		return true;
	}

	
	
	public void init() 
	{
		String query = "select a.pk_seq, b.PREFIX + CAST(a.nhapkho_fk as varchar(10)) as sonhapkho, c.pk_seq as spId, c.Ten as spTen, a.soluong, a.solo, a.trangthai  " +
					   "from ERP_YeuCauKiemDinh a inner join ERP_NHAPKHO b on a.nhapkho_fk = b.PK_SEQ  " +
							"inner join ERP_SANPHAM c on a.sanpham_fk = c.PK_SEQ   " +
					   "where a.pk_seq = '" + this.ycId + "'";
		System.out.println("KIEMDINH INIT: "+query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					this.nkId = rs.getString("sonhapkho");
					this.spTen = rs.getString("spTen");
					this.spId = rs.getString("spId");
					this.solo = rs.getString("solo");
					this.soluong = rs.getString("soluong");
				}
				rs.close();
			}
			catch (Exception e) 
			{
				System.out.println("115.Exception: " + e.getMessage());
			}
		}
		
		
		query = "select count(tieuchi) as sodong from ERP_YeuCauKiemDinh_TieuChi where yeucaukiemdinh_fk = '" + this.ycId + "' ";
		System.out.println("1.Check: " + query);
		
		ResultSet rsCheck = db.get(query);
		boolean flag = false;
		if (rsCheck != null)
		{
			try 
			{
				if (rsCheck.next())
				{
					if (rsCheck.getString("sodong").equals("0"))
						flag = true;
					rsCheck.close();
				}
			} 
			catch (Exception e) {}
		}
		
		
		String sql = "";
		
		if(flag)
		{
			sql = " select tieuchi, pheptoan, giatrichuan, '' as diemdat, '0' as dat, '' as trangthai " +
				  "from ERP_SanPham_TieuChiKiemDinh where sanpham_fk = '" + this.spId + "' ";
		}
		else
		{
			sql = " select tieuchi, pheptoan, giatrichuan, diemdat, dat, isnull(trangthai, '') as trangthai " +
				  "from ERP_YeuCauKiemDinh_TieuChi where yeucaukiemdinh_fk = '" + this.ycId + "' ";
		}
		
        System.out.println("2.Khoi tao tieu chi: " + sql);
		
		List<ITieuchikiemdinh> tckdList = new ArrayList<ITieuchikiemdinh>();
        rs = db.get(sql);
        
		if (rs != null)
		{
			try
			{
				while (rs.next())
				{
					ITieuchikiemdinh tckd = new Tieuchikiemdinh();
					
					tckd.setTieuchi(rs.getString("tieuchi"));
					tckd.setToantu(rs.getString("pheptoan"));
					tckd.setGiatrichuan(rs.getString("giatrichuan"));
					tckd.setDiemdat(rs.getString("diemdat"));
					tckd.setDat(rs.getString("dat"));
					tckd.setTrangthai(rs.getString("trangthai"));
					
					tckdList.add(tckd);
				}
			}
			catch (Exception e)
			{
				System.out.println("116.Loi.....: " + e.toString());
			}
			
			this.tckdList = tckdList;
		}
		
		if(this.tckdList.size() <= 0)
		{
			this.msg = "Sản phẩm ( " + spTen + " ) chưa thiết lập tiêu chí kiểm định. Vui lòng kiểm tra lại dừ liệu nền." ;
		}
		
	}

	
	public void DbClose()
	{
		
	}
	
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	@Override
	public String getDeNghiXuLy() {
		return this.denghixuly;
	}

	@Override
	public void setDeNghiXuLy(String denghixuly) {
		this.denghixuly = denghixuly;
	}
	

}
