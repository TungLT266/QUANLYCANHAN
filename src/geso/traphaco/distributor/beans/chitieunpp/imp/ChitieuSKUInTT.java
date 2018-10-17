package geso.traphaco.distributor.beans.chitieunpp.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.distributor.beans.chitieunpp.IChitieuSKUInTT;
import geso.traphaco.distributor.beans.chitieunpp.IChitieusku;
import geso.traphaco.distributor.util.Utility;


public class ChitieuSKUInTT implements IChitieuSKUInTT 
{
	String userId;
	String id;
	
	String thang;
	String nam;
	String trangthai;
	String msg;
	String diengiai;
	
	ResultSet nppRs;
	String nppIds;
	
	ResultSet nspRs;
	String nspIds;
	List<IChitieusku> spList;
	ResultSet KbhRs;
	ResultSet DvkdRs;
	
	String KbhId;
	String DvkdId;
	
	dbutils db;
	
	public ChitieuSKUInTT()
	{
		this.id = "";
		this.thang = "";
		this.nam = "";
		this.trangthai = "";
		this.msg = "";
		this.diengiai = "";
		this.nppIds = "";
		this.nspIds = "";
		this.KbhId="";
		this.DvkdId="";
		
		this.spList = new ArrayList<IChitieusku>();
		this.db = new dbutils();
	}
	
	public ChitieuSKUInTT(String id)
	{
		this.id = id;
		this.thang = "";
		this.nam = "";
		this.trangthai = "";
		this.msg = "";
		this.diengiai = "";
		this.nppIds = "";
		this.spList = new ArrayList<IChitieusku>();
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
	
	public String getThang() 
	{
		return this.thang;
	}
	
	public void setThang(String thang) 
	{
		this.thang = thang;
	}
	
	public String getNam() 
	{
		return this.nam;
	}
	
	public void setNam(String nam)
	{
		this.nam = nam;
	}

	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}

	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	public String getDiengiai()
	{
		return this.diengiai;
	}

	public void setDiengiai(String diengiai)
	{
		this.diengiai = diengiai;
	}

	public boolean createChiTieuThang() 
	{
		try 
		{
			if(this.DvkdId.equals("")){
				this.msg="Chọn đơn vị kinh doanh";
				return false;
			}
			if(this.KbhId.equals("")){
				this.msg="Chọn kênh bán hàng";
				return false;
				
			}
			
			if(this.thang.equals("")){
				this.msg="Chọn tháng";
				return false;
				
			}
			if(this.nam.equals("")){
				this.msg="Chọn năm";
				return false;
				
			}
			
			//check chi tieu
			String query = "select count(*) as sodong from CHITIEUSKUIN_TT where kbh_fk='"+this.KbhId+"' and dvkd_fk='"+this.DvkdId+"' and thang = '" + this.thang + "' and nam = '" + this.nam + "' and nhapp_fk = '" + this.nppIds + "'  ";
			System.out.println("___Check chi tieu: " + query);
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				int count = 0;
				if(rs.next())
				{
					count = rs.getInt("sodong");
					if(count > 0)
					{
						this.msg = "Thang " + this.thang + " Cua nam " + this.nam + " da chia chi tieu roi.";
						rs.close();
						return false;
					}
				}
				rs.close();
			}
			
			db.getConnection().setAutoCommit(false);
			
			String ngaysua = this.getDateTime();
			
			query = "Insert CHITIEUSKUIN_TT(thang, nam, diengiai, trangthai, ngaytao, nguoitao, ngaysua, nguoisua,dvkd_fk,kbh_fk) " +
					"values('" + this.thang + "', '" + this.nam + "', N'" + this.diengiai + "', '0', '" + ngaysua + "', '" + this.userId + "', '" + ngaysua + "', '" + this.userId + "','"+this.DvkdId+"','"+this.KbhId+"')";
			
			System.out.println("1.Insert: " + query);
			if(!db.update(query))
			{
				this.msg = "Khong the tao moi chi tieu thang: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (SQLException e) {}
		return true;
	}

	public boolean updateChiTieuThang() 
	{
		try 
		{
			if(this.nppIds.length() <= 0)
			{
				this.msg = "Vui long chon nha phan phoi";
				return false;
			}
			
			db.getConnection().setAutoCommit(false);
			
			String ngaysua = this.getDateTime();
			
			String query = "update CHITIEUSKUIN_TT set diengiai = N'" + this.diengiai + "',  ngaysua = '" + ngaysua + "', nguoisua = '" + this.userId + "'";
							
			System.out.println("1.Update: " + query);
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat chi tieu thang: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "update chitieuSKUin set trangthai = '1' where thang = '" + this.thang + "' and nam = '" + this.nam + "'  and dvkd_fk='"+this.DvkdId+"' and kbh_fk='"+this.KbhId+"' and nhapp_fk in (" + this.nppIds + ")";
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat chi tieu SKU In: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			//Neu da duyet het, tu dong cap nhat trang thai cua CHITIEUSKUIN_TT
			query = "select count(*) as sodong from chitieuSKUin where thang = '" + this.thang + "' and dvkd_fk='"+this.DvkdId+"' and kbh_fk='"+this.KbhId+"' and nam = '" + this.nam + "' and trangthai = '0'";
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				int count = 0;
				if(rs.next())
				{
					count = rs.getInt("sodong");
					if(count <= 0)
					{
						if(!db.update("update CHITIEUSKUIN_TT set trangthai = '1' where thang = '" + this.thang + "' and nam = '" + this.nam + "' and dvkd_fk='"+this.DvkdId+"' and kbh_fk='"+this.KbhId+"'"))
						{
							this.msg = "Khong the cap nhat CHITIEUSKUIN_TT";
							db.getConnection().rollback();
							return false;
						}
						//THUC HIEN CHO CHI TIEU VAO SS VA ASM
						
						
						
						String sql=" INSERT INTO CHITIEUPRI  " +
								"SELECT CT.THANG,CT.NAM,CT.DVKD_FK,CT.KBH_FK,'SS' ,GSBH.PK_SEQ ,SUM( ISNULL(BG.GIAMUANPP,0) * CT_SKU.CHITIEU) AS CHITIEUPRI "+ 
							" FROM CHITIEUSKUIN CT INNER JOIN CHITIEUSKUIN_SKU CT_SKU "+
							" ON CT.PK_SEQ=CT_SKU.CHITIEUSKUIN_FK "+
							" INNER JOIN NHAPP_GIAMSATBH NPP_GS ON NPP_GS.NPP_FK=CT.NHAPP_FK  "+
							" INNER JOIN GIAMSATBANHANG GSBH ON GSBH.PK_SEQ=NPP_GS.GSBH_FK "+ 
							" LEFT JOIN "+ 
							" ( "+
							" SELECT DISTINCT BGM.KENH_FK AS KBH_FK,BGM_SP.SANPHAM_FK,BGMNPP.NPP_FK,BGM_SP.GIAMUANPP AS GIAMUANPP "+
							" FROM BANGGIAMUANPP_NPP BGMNPP 		  INNER JOIN BANGGIAMUANPP BGM ON BGM.PK_SEQ = BGMNPP.BANGGIAMUANPP_FK "+ 	
							" INNER JOIN BGMUANPP_SANPHAM BGM_SP ON BGM_SP.BGMUANPP_FK = BGM.PK_SEQ  "+
							" ) AS BG  "+
							" ON BG.SANPHAM_FK=CT_SKU.SKU AND BG.KBH_FK=CT.KBH_FK AND BG.NPP_FK=CT.NHAPP_FK "+
							" WHERE NPP_GS.NGAYBATDAU <='"+this.nam+"-"+this.thang+"-01' AND SUBSTRING(NPP_GS.NGAYKETTHUC,1,4)='2100' AND CT.THANG='"+this.thang+"' and ct.nam='"+this.nam+"' and ct.dvkd_fk='"+this.DvkdId+"' and ct.bkh_fk='"+this.KbhId+"' "+
							" GROUP BY CT.KBH_FK,CT.THANG,CT.NAM,CT.DVKD_FK,GSBH.PK_SEQ";
							if(!this.db.update(sql)){
								this.msg = "Khong the cap nhat" + sql;
								db.getConnection().rollback();
								return false;
							}
							
							sql="INSERT INTO CHITIEUPRI "+
								" SELECT CT.THANG,CT.NAM,CT.DVKD_FK,CT.KBH_FK,'ASM' ,ASM.PK_SEQ ,SUM( ISNULL(BG.GIAMUANPP,0) * CT_SKU.CHITIEU) AS CHITIEUPRI "+ 
								" FROM CHITIEUSKUIN CT INNER JOIN CHITIEUSKUIN_SKU CT_SKU "+
								" ON CT.PK_SEQ=CT_SKU.CHITIEUSKUIN_FK "+
								" INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ=CT.NHAPP_FK "+
								" INNER JOIN ASM_KHUVUC ASM_KV ON NPP.KHUVUC_FK=ASM_KV.KHUVUC_FK "+
								" INNER JOIN ASM ON ASM.PK_SEQ=ASM_KV.ASM_FK "+
								" LEFT JOIN "+ 
								" ( "+
								" SELECT DISTINCT BGM.KENH_FK AS KBH_FK,BGM_SP.SANPHAM_FK,BGMNPP.NPP_FK,BGM_SP.GIAMUANPP AS GIAMUANPP "+
								" FROM BANGGIAMUANPP_NPP BGMNPP 		  INNER JOIN BANGGIAMUANPP BGM ON BGM.PK_SEQ = BGMNPP.BANGGIAMUANPP_FK "+ 	
								" INNER JOIN BGMUANPP_SANPHAM BGM_SP ON BGM_SP.BGMUANPP_FK = BGM.PK_SEQ "+ 
								" ) AS BG  "+
								" ON BG.SANPHAM_FK=CT_SKU.SKU AND BG.KBH_FK=CT.KBH_FK AND BG.NPP_FK=CT.NHAPP_FK "+
								" WHERE ASM_KV.NGAYBATDAU <='"+this.nam+"-"+this.thang+"-01' AND SUBSTRING(ASM_KV.NGAYKETTHUC,1,4)='2100' AND CT.THANG='"+this.thang+"' and ct.nam='"+this.nam+"' and ct.dvkd_fk='"+this.DvkdId+"' and ct.bkh_fk='"+this.KbhId+"' "+
								" GROUP BY CT.THANG,CT.NAM,CT.DVKD_FK,CT.KBH_FK ,ASM.PK_SEQ ";
							
							if(!this.db.update(sql)){
								this.msg = "Khong the cap nhat" + sql;
								db.getConnection().rollback();
								return false;
							}
					}
				}
				rs.close();
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (SQLException e) {}
		return true;
	}

	public String getId() 
	{
		return this.id;
	}

	public void setId(String Id) 
	{
		this.id = Id;
	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	public void init()
	{
		Utility util= new Utility();
		String nppId = util.getIdNhapp(this.userId);
		String query =  "select distinct a.kbh_fk,a.dvkd_fk,a.pk_seq, a.thang, a.nam, a.diengiai, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, a.ngaytao, c.ten as nguoisua " +
						  "from CHITIEUSKUIN a inner join CHITIEUSKUIN_SKU sub on a.PK_SEQ = sub.CHITIEUSKUIN_FK   inner join NHANVIEN b on a.nguoitao = b.pk_seq inner join NHANVIEN c on a.nguoisua = c.pk_seq " +
						  "where a.pk_seq = '" + this.id + "' and sub.npp_fk="+nppId ;
		
		System.out.println("1....Khoi tao: ............. " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				
				while(rs.next())
				{
					this.thang = rs.getString("thang");
					this.nam = rs.getString("nam");
					this.diengiai = rs.getString("diengiai");
					this.DvkdId=rs.getString("dvkd_fk");
					this.KbhId=rs.getString("kbh_fk");
				}
				rs.close();
			} 
			catch (SQLException e) {}
		}
		List<IChitieusku> ctList = new ArrayList<IChitieusku>();
		query="select a.chitieu,b.ma,b.ten,a.npp_fk,soluong1  " +
				"from CHITIEUSKUIN_SKU a" +
				" inner join sanpham b on a.sku= b.pk_seq " +
				" inner join quycach qc on qc.sanpham_fk=b.pk_seq and b.dvdl_fk=qc.dvdl1_fk and qc.dvdl2_fk=100018 " +
				" where CHITIEUSKUIN_FK= '"+this.id+ "' and a.npp_fk='"+nppId+"'"+
				" order by NPP_FK  ";
		System.out.println(query);
		ResultSet hdRs = db.get(query);
		if(hdRs != null)
		{
			try 
			{
				while(hdRs.next())
				{
					IChitieusku sp = null;
					sp = new ChitieuSku();
					sp.setNpp(hdRs.getString("npp_fk"));
					sp.setMasanpham(hdRs.getString("ma"));
					sp.setTensanpham(hdRs.getString("ten"));
					sp.setSoluong((Integer.parseInt(hdRs.getString("chitieu"))/hdRs.getInt("soluong1")) +"");
					System.out.println(hdRs.getInt("soluong1"));
					System.out.println(hdRs.getString("chitieu"));
					ctList.add(sp);
				}
				hdRs.close();
			}
			catch (SQLException e) {};
		}
		this.spList=ctList;
		
		this.createRs();
		
	}


	public ResultSet getNppRs() 
	{
		return this.nppRs;
	}

	public void setNppRs(ResultSet nppRs) 
	{
		this.nppRs = nppRs;
	}

	public String getNppIds() 
	{
		return this.nppIds;
	}

	public void setNppIds(String nppIds)
	{
		this.nppIds = nppIds;
	}

	public ResultSet getNhomspRs() 
	{
		return this.nspRs;
	}

	public void setNhomspRs(ResultSet nspRs) 
	{
		this.nspRs = nspRs;
	}

	public String getNspId() 
	{
		return this.nspIds;
	}

	public void setNspId(String nspId) 
	{
		this.nspIds = nspId;
	}

	public boolean createChiTieuSkuin() 
	{
		Hashtable< String, String[]> htbSp=new Hashtable<String, String[]>(); 
		htbSp=this.gethtbSanPham();
		try 
		{
			
			//check chi tieu
			String query = "select count(*) as sodong from CHITIEUSKUIN where thang = '" + this.thang + "' and nam = '" + this.nam + "'";
			System.out.println("ccccc" +query);
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				int count = 0;
				if(rs.next())
				{
					count = rs.getInt("sodong");
					if(count > 0)
					{
						query = "select count(*) as sodong from CHITIEUSKUIN where thang = '" + this.thang + "' and nam = '" + this.nam +"' and dvkd_fk='"+this.DvkdId+"'";
						System.out.println("ccccc" +query);
						ResultSet rs2 = db.get(query);
						if(rs2 != null)
						{
							count = 0;
							if(rs2.next())
							{
								count = rs2.getInt("sodong");
								if(count > 0)
								{
									query = "select count(*) as sodong from CHITIEUSKUIN where thang = '" + this.thang + "' and nam = '" + this.nam +"' and dvkd_fk='"+this.DvkdId+"' and KBH_FK='"+this.KbhId+"'";
									System.out.println("ccccc" +query);
									ResultSet rs3 = db.get(query);
									if(rs3 != null)
									{
										count = 0;
										if(rs3.next())
										{
											count = rs3.getInt("sodong");
											if(count > 0)
											{
												this.msg = "Chỉ tiêu này đã tồn tại";
												rs3.close();
												return false;
											}
										}
										rs3.close();
									}
								}
								else
								{
									
								}
							}
							rs2.close();
						}
						
					}
				}
				rs.close();
			}
			
			db.getConnection().setAutoCommit(false);
			
			String ngaysua = this.getDateTime();
			
			query = "Insert CHITIEUSKUIN(thang, nam, diengiai, trangthai, ngaytao, nguoitao, ngaysua, nguoisua,DVKD_FK,KBH_FK) " +
					"values('" + this.thang + "', '" + this.nam + "', N'" + this.diengiai + "', '0','"+ ngaysua + "', '" + this.userId + "', '" + ngaysua + "', '" + this.userId + "', '"+ this.DvkdId+"', '"+this.KbhId+ "')";
			
			System.out.println("1.Insert: " + query);
			if(!db.update(query))
			{
				this.msg = "Lỗi: " + query;
				db.getConnection().rollback();
				return false;
			}
			String parkCurrent = "";//Lay so PK_SEQ park vua insert
			query = "select IDENT_CURRENT('CHITIEUSKUIN') as skuId";
			
			ResultSet rsNh = db.get(query);						
			if(rsNh.next())
			{
				parkCurrent = rsNh.getString("skuId");
				rsNh.close();
			}
			for(int i=0;i<this.spList.size();i++)
			{
				 IChitieusku ddkd = this.spList.get(i);
				String[] mang=htbSp.get(ddkd.getMasanpham().trim());
				if(mang==null)
				{
					this.msg = "Sku có mã: " + ddkd.getMasanpham()+" của nhà phân phối "+ddkd.getNpp()+" tại dòng thứ "+(i+6)+" trong file không tồn tại";
					db.getConnection().rollback();
					return false;
				}
				 String sp=mang[0];
				 Double qc=Double.parseDouble(mang[1]);
				 
				System.out.println("queryvvvv"+Integer.parseInt(ddkd.getSoluong())*qc);
				
				query = "Insert CHITIEUSKUIN_SKU(CHITIEUSKUIN_FK, SKU,CHITIEU,NPP_FK) " +
				"values('" + parkCurrent+ "', '" + sp + "', '" +  Integer.parseInt(ddkd.getSoluong())*qc + "','"+ddkd.getNpp()+"')";
					
					System.out.println("1.Insert: " + query);
					if(!db.update(query))
					{
						query=" update CHITIEUSKUIN_SKU set CHITIEU= "+ Integer.parseInt(ddkd.getSoluong())*qc +" where npp_fk="+ddkd.getNpp()+" and CHITIEUSKUIN_FK="+parkCurrent+" and SKU='"+sp+"' ";
						if(!db.update(query))
						{
							this.msg = "Lỗi: " + query;
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
			e.printStackTrace();
			System.out.println("Error Here :"+e.toString());
			db.update("rollback");
			return false;
		}
		return true;
	}
	
	

	private Hashtable<String, String[]> gethtbSanPham() {
		Hashtable<String, String[]> htb=new Hashtable<String, String[]>();
		try
		{
		String sql=" select pk_seq,ma, isnull( (select top 1 soluong1/soluong2 from quycach qc "+ 
					" where qc.sanpham_fk=sp.pk_seq),1) as quycach from sanpham sp"; 
		ResultSet rs=db.get(sql);
		String[] mang;
		while(rs.next())
		{
			mang=new String[2];
			mang[0]=rs.getString("pk_seq");
			mang[1]=rs.getString("quycach");
			htb.put(rs.getString("ma").trim(), mang);
		}
		rs.close();
		}catch (Exception e) 
		{
			System.out.println("Error Here : "+e.toString());
		}
		return htb;
	}

	public boolean updateChiTieuSkuin() 
	{
		try 
		{
			Hashtable< String, String[]> htbSp=new Hashtable<String, String[]>(); 
			htbSp=this.gethtbSanPham();
			//check chi tieu
			String query = "select count(*) as sodong from CHITIEUSKUIN where thang = '" + this.thang + "' and nam = '" + this.nam + "' and pk_seq!='"+this.id+"'";
			System.out.println("ccccc" +query);
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				int count = 0;
				if(rs.next())
				{
					count = rs.getInt("sodong");
					if(count > 0)
					{
						query = "select count(*) as sodong from CHITIEUSKUIN where thang = '" + this.thang + "' and nam = '" + this.nam + "' and pk_seq!='"+this.id+"' and dvkd_fk='"+this.DvkdId+"'";
						System.out.println("ccccc" +query);
						ResultSet rs2 = db.get(query);
						if(rs2 != null)
						{
							count = 0;
							if(rs2.next())
							{
								count = rs2.getInt("sodong");
								if(count > 0)
								{
									query = "select count(*) as sodong from CHITIEUSKUIN where thang = '" + this.thang + "' and nam = '" + this.nam + "' and pk_seq!='"+this.id+"' and dvkd_fk='"+this.DvkdId+"' and KBH_FK='"+this.KbhId+"'";
									System.out.println("ccccc" +query);
									ResultSet rs3 = db.get(query);
									if(rs3 != null)
									{
										count = 0;
										if(rs3.next())
										{
											count = rs3.getInt("sodong");
											if(count > 0)
											{
												this.msg = "Chỉ tiêu này đã tồn tại";
												rs3.close();
												return false;
											}
		
										}
										rs3.close();
									}
								}
							}
							rs2.close();
						}
						
						/*if(  )
						{
							this.msg = "Tháng " + this.thang + " của năm " + this.nam + " đã chia chỉ tiêu rồi";
							rs.close();
							return false;
						}
						else
						{
							
						}*/
					}
					else
					{
						
					}
				}
				rs.close();
			}
			db.getConnection().setAutoCommit(false);
			
			String ngaysua = this.getDateTime();
			
			/*query = "Insert CHITIEUSKUIN(thang, nam, diengiai, trangthai,npp_fk ngaytao, nguoitao, ngaysua, nguoisua) " +
					"values('" + this.thang + "', '" + this.nam + "', N'" + this.diengiai + "', '0','"+this.nppIds +"','" + ngaysua + "', '" + this.userId + "', '" + ngaysua + "', '" + this.userId + "')";*/
		 query = "update CHITIEUSKUIN set thang='"+this.thang+"',nam='"+this.nam+"',diengiai=N'"+this.diengiai +"',dvkd_fk='"+ this.DvkdId +"',kbh_fk='"+ this.KbhId +"' where pk_seq='"+this.id+"'";
			System.out.println("1.Insert: " + query);
			if(!db.update(query))
			{
				this.msg = "Lỗi: " + query;
				db.getConnection().rollback();
				return false;
			}
			query = "delete CHITIEUSKUIN_SKU where chitieuskuin_fk='"+ this.id +"'";
			System.out.println("1.Insert: " + query);
			if(!db.update(query))
			{
				this.msg = "Lỗi: " + query;
				db.getConnection().rollback();
				return false;
			}
			/*String parkCurrent = "";//Lay so PK_SEQ park vua insert
			query = "select IDENT_CURRENT('CHITIEUSKUIN') as skuId";
			
			ResultSet rsNh = db.get(query);						
			if(rsNh.next())
			{
				parkCurrent = rsNh.getString("skuId");
				rsNh.close();
			}*/
			for(int i=0;i<this.spList.size();i++)
			{
				 IChitieusku ddkd = this.spList.get(i);
				/*query = "select pk_seq from sanpham where ma='"+ ddkd.getMasanpham() +"'";
				String sp="NULL";
				ResultSet rsNhsp = db.get(query);						
				if(rsNhsp.next())
				{
					sp = rsNhsp.getString("pk_seq");
					rsNhsp.close();
				}
				if(sp=="NULL")
				{
					this.msg = "Không có sku nào có mã: " + ddkd.getMasanpham()+" của nhà phân phối "+ddkd.getNpp()+" tại dòng thứ "+(i+5)+" trong file upload";
					db.getConnection().rollback();
					return false;
				}
				query = "select  top 1 SOLUONG1 from SANPHAM sp inner join QUYCACH qc on qc.SANPHAM_FK=sp.PK_SEQ where PK_SEQ='"+ sp +"'and sp.dvkd_fk='"+ this.DvkdId+"'";
				int qc=-1;
				System.out.println(query);
				ResultSet rsqc= db.get(query);						
				if(rsqc.next())
				{
					qc = rsqc.getInt("SOLUONG1");
					
					rsqc.close();
				}
				if(qc==-1)
				{
					this.msg = "Sku có mã: " + ddkd.getMasanpham()+" của nhà phân phối "+ddkd.getNpp()+" tại dòng thứ "+(i+6)+" trong file quy cách ";
					db.getConnection().rollback();
					return false;
				}*/
				//System.out.println("queryvvvv"+query);
				 String[] mang=htbSp.get(ddkd.getMasanpham().trim());
					if(mang==null){
						this.msg = "Sku có mã: " + ddkd.getMasanpham()+" của nhà phân phối "+ddkd.getNpp()+" tại dòng thứ "+(i+6)+" trong file không xác định ";
						db.getConnection().rollback();
						return false;
					}
					 String sp=mang[0];
					 Double qc=Double.parseDouble(mang[1]);
				query = "Insert CHITIEUSKUIN_SKU(CHITIEUSKUIN_FK, SKU,CHITIEU,NPP_FK) " +
				"values('" + this.id+ "', '" + sp + "', '" +  Integer.parseInt(ddkd.getSoluong())*qc + "','"+ddkd.getNpp()+"')";
					
				
					System.out.println("1.Insert: " + query);
					if(!db.update(query))
					{
						
						/*this.msg = "Lỗi: " + query;
						db.getConnection().rollback();
						return false;*/
						query=" update CHITIEUSKUIN_SKU set CHITIEU= "+ Integer.parseInt(ddkd.getSoluong())*qc +" where npp_fk="+ddkd.getNpp()+" and CHITIEUSKUIN_FK="+this.id+" and SKU='"+sp+"' ";
						if(!db.update(query))
						{
							this.msg = "Lỗi: " + query;
							db.getConnection().rollback();
							return false;
						}
					}
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			System.out.println("vao day...............");
		} 
		catch (Exception e) {
			System.out.println(e.toString());
			db.update("rollback");
			return false;
		}
		return true;
	}

	public void initnpp() 
	{
		String query =  "select a.dvkd_fk,a.kbh_fk,a.pk_seq, a.thang, a.nam, a.diengiai, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, a.ngaytao, c.ten as nguoisua " +
		  "from CHITIEUSKUIN a inner join  NHANVIEN b on a.nguoitao = b.pk_seq inner join NHANVIEN c on a.nguoisua = c.pk_seq " +
		  "where a.pk_seq = '" + this.id + "'";
		 System.out.println(query);
			ResultSet rs = db.get(query);
			if(rs != null)
			{
			try 
			{
			while(rs.next())
			{
				this.thang = rs.getString("thang");
				this.nam = rs.getString("nam");
				this.diengiai = rs.getString("diengiai");
				this.KbhId = rs.getString("kbh_fk");
				this.DvkdId = rs.getString("dvkd_fk");
			}
			rs.close();
			} 
			catch (SQLException e) {}
			}
			List<IChitieusku> ctList = new ArrayList<IChitieusku>();
			query="select a.chitieu,b.ma,b.ten,a.npp_fk,npp.ten as tennpp,soluong1  from CHITIEUSKUIN_SKU a inner join sanpham b on a.sku= b.pk_seq inner join NHAPHANPHOI npp on npp.pk_seq=a.npp_fk inner join quycach qc on qc.sanpham_fk=b.pk_seq and qc.dvdl1_fk=b.dvdl_fk and qc.dvdl2_fk=100018 where CHITIEUSKUIN_FK= "+this.id+" order by NPP_FK  ";
			System.out.println(query);
			ResultSet hdRs = db.get(query);
			if(hdRs != null)
			{
				try 
				{
					while(hdRs.next())
					{
						IChitieusku sp = null;
						sp = new ChitieuSku();
						sp.setNppten(hdRs.getString("tennpp"));
						sp.setNpp(hdRs.getString("npp_fk"));
						sp.setMasanpham(hdRs.getString("ma"));
						sp.setTensanpham(hdRs.getString("ten"));
						sp.setSoluong(Math.round(hdRs.getDouble("chitieu")/hdRs.getDouble("soluong1")) +"");
						
						ctList.add(sp);
					}
					hdRs.close();
				}
				catch (SQLException e) {};
			}
			this.spList=ctList;
			this.createRs();
	}


	public List<IChitieusku> getSpList() {
		return this.spList;
	}
	public void setSpList(List<IChitieusku> spList) {

		this.spList=spList;
	}


	public String getKbhId() {
		
		return this.KbhId;
	}


	public void setKbhId(String KbhId) {
		
		this.KbhId=KbhId;
		
	}


	public String getDvkdId() {
		
		return this.DvkdId;
	}


	public void setDvkdId(String DvkdId) {
		
		this.DvkdId=DvkdId;
		
	}


	public ResultSet getDvkdRs() {
		
		return this.DvkdRs;
	}


	public void setDvkdRs(ResultSet dvkdRs) {
		
		this.DvkdRs=dvkdRs;
		
	}


	public ResultSet getKbhRs() {
		
		return this.KbhRs;
	}


	public void setKbhRs(ResultSet kbhRs) {
		
		this.KbhRs=kbhRs;
	}

	public void DbClose() 
	{
		try{
			
			if(nppRs!=null){
				nppRs.close();
			}
			if(nspRs!=null){
				nspRs.close();
			}
			if(spList!=null)
				spList.clear();
			
			if(KbhRs!=null){
				KbhRs.close();
			}
			if(DvkdRs!=null){
				DvkdRs.close();
			}
			
			if(db!=null){
				db.shutDown();
			}
		}
		catch(Exception er){}
	}

	public void createRs() 
	{
		/*String query = "select a.pk_seq as nppId, a.ma, a.ten, a.diachi, b.trangthai as trang_thai, " +
						"case b.trangthai when 0 then N'Chưa duyệt' else N'Đã duyệt' end as trangthai " +
					"from nhaphanphoi a inner join CHITIEUSKUIN b on a.pk_seq = b.nhapp_fk " +
					"where thang = '" + this.thang + "' and nam = '" + this.nam + "' and b.dvkd_fk='"+this.DvkdId+"' and b.kbh_fk='"+this.KbhId+"' " +
					"order by b.trangthai desc";


		
		/*this.nppRs = db.get(query);*/
		
		this.nspRs = db.get("select pk_seq, diengiai from  nhomsanpham where trangthai = '1' and type = '0'");
		
		this.KbhRs=db.get("select pk_seq,ten from kenhbanhang where trangthai=1");
		System.out.println("select pk_seq,ten from kenhbanhang where trangthai=1");
		this.DvkdRs=db.get("select pk_seq,donvikinhdoanh as ten from donvikinhdoanh where trangthai=1");
		System.out.println("select pk_seq,donvikinhdoanh as ten from donvikinhdoanh where trangthai=1");
		
	}





	
}
