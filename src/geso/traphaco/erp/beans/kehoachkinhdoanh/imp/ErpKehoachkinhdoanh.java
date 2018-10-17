package geso.traphaco.erp.beans.kehoachkinhdoanh.imp;

import geso.traphaco.center.util.Utility;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.erp.beans.kehoachkinhdoanh.IErpKehoachkinhdoanh;
import geso.traphaco.erp.beans.kehoachkinhdoanh.IKenhbanhang;
import geso.traphaco.erp.beans.kehoachkinhdoanh.ISanpham;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lowagie.tools.split_pdf;
import com.rp.util.DateTime;

public class ErpKehoachkinhdoanh implements IErpKehoachkinhdoanh
{
	String congtyId;
	String nppId;
	String userId;
	String ctyId;
	String cty;
	String id;
	String trangthai;
	String nam;
	String diengiai;
	List<IKenhbanhang> kbhListMB;
	List<IKenhbanhang> kbhListMN;
	String msg;
	String loai;
	dbutils db;
	
	private Utility util;

	public ErpKehoachkinhdoanh()
	{
		this.userId = "";
		this.ctyId = "";
		this.cty = "";
		this.id = "";
		this.diengiai = "";
		this.nam = "";
		this.msg = "";
		this.loai = "1";//1: trong nuoc; 2: nhap khau
		this.kbhListMB = new ArrayList<IKenhbanhang>();
		this.kbhListMN = new ArrayList<IKenhbanhang>();
		
		this.db = new dbutils();
		this.util=new Utility();
	}

	public ErpKehoachkinhdoanh(String id)
	{
		this.userId = "";
		this.nppId = "";
		this.ctyId = "";
		this.cty = "";
		this.id = id;
		this.diengiai = "";
		this.nam = "";
		this.msg = "";
		this.loai = "1";
		this.kbhListMB = new ArrayList<IKenhbanhang>();
		this.kbhListMN = new ArrayList<IKenhbanhang>();
		
		this.db = new dbutils();
		this.util=new Utility();
	}

	public String getCtyId()
	{
		return this.ctyId;
	}

	public void setCtyId(String ctyId)
	{
		this.ctyId = ctyId;
	}

	public String getCty()
	{
		return this.cty;
	}

	public void setCty(String cty)
	{
		this.cty = cty;
	}

	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getId()
	{
		return this.id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public List<IKenhbanhang> getKbhListMB()
	{
		return this.kbhListMB;
	}

	public void setKbhListMB(List<IKenhbanhang> kbhListMB)
	{
		this.kbhListMB = kbhListMB;
	}

	public List<IKenhbanhang> getKbhListMN()
	{
		return this.kbhListMN;
	}

	public void setKbhListMN(List<IKenhbanhang> kbhListMN)
	{
		this.kbhListMN = kbhListMN;
	}
	
	public void createRs()
	{
		
	}

	public void init()
	{
		
		System.out.println(" vao day 2");
		NumberFormat formatter = new DecimalFormat("#,###,###.###"); 
		
		String query = " select a.PK_SEQ as id, a.diengiai, a.nam " +
				" from ERP_kehoachkinhdoanh a " +
				" where a.pk_seq = '" + this.id + "' " ;
		
		System.out.println("Ke hoach kinh doanh : " + query);
		ResultSet rs = db.get(query);
		if (rs != null)
		{
			try
			{
				while (rs.next())
				{
					this.id = rs.getString("id");
					this.diengiai = rs.getString("diengiai");
					this.nam = rs.getString("nam");
					
				}
				rs.close();
			}
			catch (Exception e)
			{
				System.out.println("__Exception: " + e.getMessage());
			}
		}
		try
		{
			query = "select * from kenhbanhang where pk_seq in (100025, 100052, 100054, 100056, 100057)";
			List<IKenhbanhang> kbhListMB = new ArrayList<IKenhbanhang>();
			List<IKenhbanhang> kbhListMN = new ArrayList<IKenhbanhang>();
			rs = db.get(query);
			while(rs.next())
			{
				IKenhbanhang kbh = new Kenhbanhang();
				kbh.setId(rs.getString("pk_seq"));
				kbh.setTenkenh(rs.getString("ten"));
				query = "select a.khkd_fk, a.sp_fk, a.vung_fk, a.kbh_fk, a.t1, a.t2, a.t3, a.t4, a.t5, a.t6, a.t7, a.t8, a.t9, a.t10, a.t11, a.t12, b.ten, b.ma_fast from ERP_KEHOACHKINHDOANH_CHITIET a inner join sanpham b on a.sp_fk = b.pk_seq where KBH_FK = '"+kbh.getId()+"' and VUNG_FK = 100001 ";
				if(this.id.length() > 0)
					query += "and KHKD_FK = '"+this.id+"'";
				System.out.println("khkd chi tiet " + query);
				ResultSet rsSP = db.get(query);
				List<ISanpham> spList = new ArrayList<ISanpham>();
				while(rsSP.next())
				{
					ISanpham sp = new Sanpham();
					sp.setId(rsSP.getString("sp_fk"));
					sp.setMasanpham(rsSP.getString("ma_fast"));
					sp.setTensanpham(rsSP.getString("ten"));
					sp.setThang1(formatter.format( rsSP.getDouble("t1") ));
					sp.setThang2(formatter.format( rsSP.getDouble("t2") ));
					sp.setThang3(formatter.format( rsSP.getDouble("t3") ));
					sp.setThang4(formatter.format( rsSP.getDouble("t4") ));
					sp.setThang5(formatter.format( rsSP.getDouble("t5") ));
					sp.setThang6(formatter.format( rsSP.getDouble("t6") ));
					sp.setThang7(formatter.format( rsSP.getDouble("t7") ));
					sp.setThang8(formatter.format( rsSP.getDouble("t8") ));
					sp.setThang9(formatter.format( rsSP.getDouble("t9") ));
					sp.setThang10(formatter.format( rsSP.getDouble("t10") ));
					sp.setThang11(formatter.format( rsSP.getDouble("t11") ));
					sp.setThang12(formatter.format( rsSP.getDouble("t12") ));
					System.out.println("ma " +sp.getMasanpham()+ ", ten " + sp.getTensanpham()+ ", t1 " + sp.getThang1()+ ", t2 " + sp.getThang2()+ ", t3 " + sp.getThang3()+ ", t4 " + sp.getThang4()+ ", t5 " + sp.getThang5()+ ", t6 " + sp.getThang6()+ ", t7 " + sp.getThang7()+ ", t8 " + sp.getThang8()+ ", t9 " + sp.getThang9()+ ", t10 " + sp.getThang10()+ ", t11 " + sp.getThang11()+ ", t12 " + sp.getThang12());
					spList.add(sp);
				}
				kbh.setSanpham(spList);
				System.out.println("sp length " + spList.size());
				kbhListMB.add(kbh);
				
			}
			System.out.println("kbhMB length " + kbhListMB.size());
			
			this.kbhListMB = kbhListMB;
			
			query = "select * from kenhbanhang where pk_seq in (100025, 100052, 100054, 100056, 100057)";
			rs = db.get(query);
			while(rs.next())
			{
				IKenhbanhang kbh = new Kenhbanhang();
				kbh.setId(rs.getString("pk_seq"));
				kbh.setTenkenh(rs.getString("ten"));
				query = "select a.khkd_fk, a.sp_fk, a.vung_fk, a.kbh_fk, a.t1, a.t2, a.t3, a.t4, a.t5, a.t6, a.t7, a.t8, a.t9, a.t10, a.t11, a.t12, b.ten, b.ma_fast from ERP_KEHOACHKINHDOANH_CHITIET a inner join sanpham b on a.sp_fk = b.pk_seq where KBH_FK = '"+kbh.getId()+"' and VUNG_FK = 100009 ";
				if(this.id.length() > 0)
					query += "and KHKD_FK = '"+this.id+"'";
				System.out.println("khkd chi tiet " + query);
				ResultSet rsSP = db.get(query);
				List<ISanpham> spList = new ArrayList<ISanpham>();
				while(rsSP.next())
				{
					ISanpham sp = new Sanpham();
					sp.setId(rsSP.getString("sp_fk"));
					sp.setMasanpham(rsSP.getString("ma_fast"));
					sp.setTensanpham(rsSP.getString("ten"));
					sp.setThang1(formatter.format( rsSP.getDouble("t1") ));
					sp.setThang2(formatter.format( rsSP.getDouble("t2") ));
					sp.setThang3(formatter.format( rsSP.getDouble("t3") ));
					sp.setThang4(formatter.format( rsSP.getDouble("t4") ));
					sp.setThang5(formatter.format( rsSP.getDouble("t5") ));
					sp.setThang6(formatter.format( rsSP.getDouble("t6") ));
					sp.setThang7(formatter.format( rsSP.getDouble("t7") ));
					sp.setThang8(formatter.format( rsSP.getDouble("t8") ));
					sp.setThang9(formatter.format( rsSP.getDouble("t9") ));
					sp.setThang10(formatter.format( rsSP.getDouble("t10") ));
					sp.setThang11(formatter.format( rsSP.getDouble("t11") ));
					sp.setThang12(formatter.format( rsSP.getDouble("t12") ));
					System.out.println("ma " +sp.getMasanpham()+ ", ten " + sp.getTensanpham()+ ", t1 " + sp.getThang1()+ ", t2 " + sp.getThang2()+ ", t3 " + sp.getThang3()+ ", t4 " + sp.getThang4()+ ", t5 " + sp.getThang5()+ ", t6 " + sp.getThang6()+ ", t7 " + sp.getThang7()+ ", t8 " + sp.getThang8()+ ", t9 " + sp.getThang9()+ ", t10 " + sp.getThang10()+ ", t11 " + sp.getThang11()+ ", t12 " + sp.getThang12());
					spList.add(sp);
				}
				kbh.setSanpham(spList);
				System.out.println("sp length " + spList.size());
				kbhListMN.add(kbh);
				
			}
			System.out.println("kbhMB length " + kbhListMN.size());
			this.kbhListMN = kbhListMN;
			
		}
		catch (Exception e)
		{
			System.out.println("__Exception: " + e.getMessage());
		}
		this.createRs();
	}

	public boolean createKhkd()
	{
		// Kiem tra moi them vao
		String query = "";
		this.nppId = util.getIdNhapp(userId);
		
		try
		{
			String ngaytao = getDateTime();
		
			db.getConnection().setAutoCommit(false);
			
			String loaisanpham = "NULL";
			
			query = " Insert into Erp_kehoachkinhdoanh(nam, diengiai, trangthai, NgayTao, NgaySua, NguoiTao, NguoiSua, congty_fk, npp_fk, loai)" +
					" Values('"+ngaytao.substring(0, 4)+"', '" + this.diengiai + "',0, '" + ngaytao + "', '" + ngaytao + "'," + this.userId + "," + this.userId + ", '" + this.congtyId + "', '"+this.nppId+"', '"+this.loai+"')";
			
			System.out.println("Insert into Erp_kehoachkinhdoanh " + query);
			if (!db.update(query))
			{
				this.msg = "Khong the tao moi Mua hang: " + query;
				System.out.println("2.Exception tai day: " + query);
				db.getConnection().rollback();
				return false;
			}

			String dmhCurrent = "";
			query = "select SCOPE_IDENTITY() as id";
			ResultSet rsDmh = db.get(query);
			if (rsDmh.next())
			{
				dmhCurrent = rsDmh.getString("id");
				this.id = dmhCurrent;
				rsDmh.close();
			}				
			
			if(this.kbhListMB.size() > 0)
			{
				System.out.println("vao mien bac");
				for(int i = 0; i < this.kbhListMB.size(); i++)
				{
					IKenhbanhang kbh = this.kbhListMB.get(i);
					List<ISanpham> spList = kbh.getSanpham();
					for(int j = 0; j < spList.size(); j++)
					{
						ISanpham sp = spList.get(j);
						query = " insert into ERP_kehoachkinhdoanh_chitiet(khkd_fk, sp_fk, vung_fk, kbh_fk, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12) " +
								" values(" + dmhCurrent + ", " + sp.getId() + ", 100001, " + kbh.getId() + ", '" + sp.getThang1() + "', '" + sp.getThang2() + "', '" + sp.getThang3() + "', '" + sp.getThang4() + "'"
								+ ", '" + sp.getThang5() + "', '" + sp.getThang6() + "', '" + sp.getThang7() + "', '" + sp.getThang8() + "', '" + sp.getThang9() + "', '" + sp.getThang10() + "', '" + sp.getThang11() + "'"
								+ ", '" + sp.getThang12() + "')";
						
						System.out.println("2.Insert Into ERP_kehoachkinhdoanh_chitiet :" + query);
						
						if (!db.update(query))
						{
							this.msg = "Khong the tao moi Mua hang - San pham: " + query;
							
							db.getConnection().rollback();
							return false;
						}
					}
				}
			}
			
			if(this.kbhListMN.size() > 0)
			{
				System.out.println("vao mien nam");
				for(int i = 0; i < this.kbhListMN.size(); i++)
				{
					IKenhbanhang kbh = this.kbhListMN.get(i);
					List<ISanpham> spList = kbh.getSanpham();
					for(int j = 0; j < spList.size(); j++)
					{
						ISanpham sp = spList.get(j);
						query = " insert into ERP_kehoachkinhdoanh_chitiet(khkd_fk, sp_fk, vung_fk, kbh_fk, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12) " +
								" values(" + dmhCurrent + ", " + sp.getId() + ", 100009, " + kbh.getId() + ", '" + sp.getThang1() + "', '" + sp.getThang2() + "', '" + sp.getThang3() + "', '" + sp.getThang4() + "'"
								+ ", '" + sp.getThang5() + "', '" + sp.getThang6() + "', '" + sp.getThang7() + "', '" + sp.getThang8() + "', '" + sp.getThang9() + "', '" + sp.getThang10() + "', '" + sp.getThang11() + "'"
								+ ", '" + sp.getThang12() + "')";
						
						System.out.println("2.Insert Into ERP_kehoachkinhdoanh_chitiet :" + query);
						
						if (!db.update(query))
						{
							this.msg = "Khong the tao moi Mua hang - San pham: " + query;
							
							db.getConnection().rollback();
							return false;
						}
					}
				}
			}
				
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			this.db.update("rollback");
			this.msg = "1.Exception " + e.getMessage();
			return false;
		}
		
	}

	public boolean updateKhkd()
	{
		String query = "";
		this.nppId = util.getIdNhapp(userId);
		
		try
		{
			String ngaytao = getDateTime();
		
			db.getConnection().setAutoCommit(false);
			
			String loaisanpham = "NULL";
			
			query = " update Erp_kehoachkinhdoanh set diengiai = '"+this.diengiai+"', NgaySua = '"+ngaytao+"', NguoiSua = '"+this.userId+"', congty_fk = '" + this.congtyId + "', npp_fk = '"+this.nppId+"' where pk_seq = '"+this.id+"'";
			
			System.out.println("Update Erp_kehoachkinhdoanh " + query);
			if (!db.update(query))
			{
				this.msg = "Khong the cap nhat ke hoach kinh doanh: " + query;
				System.out.println("2.Exception tai day: " + query);
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete Erp_kehoachkinhdoanh_chitiet where khkd_fk = '"+this.id+"'";
			System.out.println("Update Erp_kehoachkinhdoanh_chitiet " + query);
			if (!db.update(query))
			{
				this.msg = "Khong the cap nhat ke hoach kinh doanh: " + query;
				System.out.println("2.Exception tai day: " + query);
				db.getConnection().rollback();
				return false;
			}
			
			if(this.kbhListMB.size() > 0)
			{
				for(int i = 0; i < this.kbhListMB.size(); i++)
				{
					IKenhbanhang kbh = this.kbhListMB.get(i);
					List<ISanpham> spList = kbh.getSanpham();
					for(int j = 0; j < spList.size(); j++)
					{
						ISanpham sp = spList.get(j);
						query = " insert into ERP_kehoachkinhdoanh_chitiet(khkd_fk, sp_fk, vung_fk, kbh_fk, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12) " +
								" values(" + this.id + ", " + sp.getId() + ", 100001, " + kbh.getId() + ", '" + sp.getThang1() + "', '" + sp.getThang2() + "', '" + sp.getThang3() + "', '" + sp.getThang4() + "'"
								+ ", '" + sp.getThang5() + "', '" + sp.getThang6() + "', '" + sp.getThang7() + "', '" + sp.getThang8() + "', '" + sp.getThang9() + "', '" + sp.getThang10() + "', '" + sp.getThang11() + "'"
								+ ", '" + sp.getThang12() + "')";
						
						System.out.println("2.Insert Into ERP_kehoachkinhdoanh_chitiet :" + query);
						
						if (!db.update(query))
						{
							this.msg = "Khong the tao moi Mua hang - San pham: " + query;
							
							//System.out.println("5.Exception tai day: " + query);
							db.getConnection().rollback();
							return false;
						}
					}
				}
			}
			if(this.kbhListMN.size() > 0)
			{
				for(int i = 0; i < this.kbhListMN.size(); i++)
				{
					IKenhbanhang kbh = this.kbhListMN.get(i);
					List<ISanpham> spList = kbh.getSanpham();
					for(int j = 0; j < spList.size(); j++)
					{
						ISanpham sp = spList.get(j);
						query = " insert into ERP_kehoachkinhdoanh_chitiet(khkd_fk, sp_fk, vung_fk, kbh_fk, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12) " +
								" values(" + this.id + ", " + sp.getId() + ", 100009, " + kbh.getId() + ", '" + sp.getThang1() + "', '" + sp.getThang2() + "', '" + sp.getThang3() + "', '" + sp.getThang4() + "'"
								+ ", '" + sp.getThang5() + "', '" + sp.getThang6() + "', '" + sp.getThang7() + "', '" + sp.getThang8() + "', '" + sp.getThang9() + "', '" + sp.getThang10() + "', '" + sp.getThang11() + "'"
								+ ", '" + sp.getThang12() + "')";
						
						System.out.println("2.Insert Into ERP_kehoachkinhdoanh_chitiet :" + query);
						
						if (!db.update(query))
						{
							this.msg = "Khong the tao moi Mua hang - San pham: " + query;
							
							//System.out.println("5.Exception tai day: " + query);
							db.getConnection().rollback();
							return false;
						}
					}
				}
			}
				
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			this.db.update("rollback");
			this.msg = "1.Exception " + e.getMessage();
			return false;
		}
	}

	public String getMsg()
	{
		return this.msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public void close()
	{
		try
		{
			if(kbhListMB!=null){
				kbhListMB.clear();
			}
			if(kbhListMN!=null){
				kbhListMN.clear();
			}
			this.db.shutDown();
		}
		catch (Exception e)
		{
			
		}		
	}

	public String getTrangthai()
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai)
	{
		this.trangthai = trangthai;
	}
	
	public String getDiengiai()
	{
		return this.diengiai;
	}

	public void setDiengiai(String diengiai)
	{
		this.diengiai = diengiai;
	}
	
	public String getCongtyId() 
	{
		return this.congtyId;
	}

	public void setCongtyId(String congtyId) 
	{
		this.congtyId = congtyId;
	}

	@Override
	public String getLoai() {
		// TODO Auto-generated method stub
		return this.loai;
	}

	@Override
	public void setLoai(String loai) {
		// TODO Auto-generated method stub
		this.loai = loai;
	}
		
}
