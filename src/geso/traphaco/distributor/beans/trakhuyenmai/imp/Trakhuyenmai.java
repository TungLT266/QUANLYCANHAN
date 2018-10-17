package geso.traphaco.distributor.beans.trakhuyenmai.imp;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.Enumeration;
import java.util.Hashtable;

import geso.traphaco.distributor.beans.trakhuyenmai.ITrakhuyenmai;
import geso.traphaco.distributor.db.sql.dbutils;

public class Trakhuyenmai implements ITrakhuyenmai, Serializable
{
	private static final long serialVersionUID = 1L;
	private String id;
	private String diengiai;
	private int type; //1 tra tien, 2 tra chiet khau, 3 tra hang
	private int hinhthuc; //chi co td khi type=3, 1 sp co dinh, 2 sp co slg bat ky trong nhom
	private float tongtien;
	private float chietkhau;
	private int tongluong;	
	private int pheptoan;	
	private String nppId;
	private String hinhthucPrimary;
	private double tylequydoi;
	private String spQuidoiId;
	private float dongiaquidoi;
	
	private Hashtable<String, Integer> sanpham_soluong = new Hashtable<String, Integer>(); //ma --> soluong(tuong ung voi id)
	private Hashtable<String, Float> sanpham_dongia = new Hashtable<String, Float>();
	private Hashtable<String, String> sanpham_tensp = new Hashtable<String, String>();
	
	dbutils db;
	
	public Trakhuyenmai()
	{
		this.id = "";
		this.diengiai = "";		
		this.type = 1;
		this.hinhthuc = 2;
		this.tongtien = 0;
		this.chietkhau = 0;
		this.tongluong = 0;	
		this.tylequydoi = 0;
		this.spQuidoiId = "";
		this.spQuidoiId = "";
		this.dongiaquidoi = 0;
		this.db = new dbutils();
	}
	public Trakhuyenmai(String id, String diengiai, int type, float tongtien, float chietkhau, int tongluong)
	{
		this.id = id;
		this.diengiai = diengiai;
		this.type = type;
		this.hinhthuc = 2;
		this.pheptoan = 1; //AND
		this.tongtien = tongtien;
		this.tongluong = tongluong;
		this.chietkhau = chietkhau;	
		this.tylequydoi = 0;
		this.dongiaquidoi = 0;
		this.db = new dbutils();
	}
	
	public Trakhuyenmai(String id, String nppId, String ngaydh, String donhangId, int khoId)
	{
		db = new dbutils();		
		ResultSet trakm = db.get("select pk_seq, diengiai, tongluong, tongtien, chietkhau, loai, hinhthuc from Trakhuyenmai where pk_seq = '" + id + "'");
		//System.out.println("Cau lenh tra khuyen mai la: select pk_seq, diengiai, tongluong, tongtien, chietkhau, loai, hinhthuc from Trakhuyenmai where pk_seq = '" + id + "'\n");
		if(trakm != null)
		{
			try 
			{
				trakm.next();
				this.id = trakm.getString("pk_seq");
				
				this.diengiai = "";
				if(trakm.getString("diengiai") != null)
					this.diengiai = trakm.getString("diengiai");
				
				this.tongluong = 0;
				if(trakm.getString("tongluong") != null)
					this.tongluong = Integer.parseInt(trakm.getString("tongluong"));
				
				this.tongtien = 0;
				if(trakm.getString("tongtien") != null)
					this.tongtien = Float.parseFloat(trakm.getString("tongtien"));
	
				this.chietkhau = 0;
				if(trakm.getString("chietkhau") != null)
					this.chietkhau = Float.parseFloat(trakm.getString("chietkhau"));
					
				this.type = 1;
				if(trakm.getString("loai") != null)
					this.type = Integer.parseInt(trakm.getString("loai"));
				
				this.hinhthuc = 2;
				if(trakm.getString("hinhthuc") != null)
					this.hinhthuc = Integer.parseInt(trakm.getString("hinhthuc"));
				trakm.close();
			} 
			catch(Exception e) {}
		}
		if(this.type == 3) //truong hop tra sanpham
		{
			String sql = "";
			
			/*if(isTrungtam.equals("0"))
			{
				sql = "select a.Trakhuyenmai_fk, a.soluong, f.ma as spMa, f.ten as spTen, f.pk_seq, isnull(NHAPP_KHO.available, 0) as available, " +
						 " 		( select GIAMUA_SAUCK as dongia   " +
						 " 			from BGMUANPP_SANPHAM    " +
						 " 			where SANPHAM_FK = f.pk_seq and BGMUANPP_FK in ( select top(1) bg.PK_SEQ  " +
						 " 															 from BANGGIAMUANPP bg inner join BANGGIAMUANPP_NPP bg_npp on bg.PK_SEQ = bg_npp.BANGGIAMUANPP_FK    " +
						 " 															 where bg.TUNGAY <= '" + ngaydh + "' and bg_npp.NPP_FK = '" + nppId + "' and bg.KENH_FK = '" + kenhkh + "' and bg.DVKD_FK = f.DVKD_FK  " +
						 " 															 order by bg.TUNGAY desc  )  )  as dongia " +
						 "from Trakhuyenmai_sanpham a " +
						 "inner join SANPHAM f on a.SANPHAM_FK = f.PK_SEQ left join NHAPP_KHO on a.sanpham_fk = NHAPP_KHO.sanpham_fk and NHAPP_KHO.npp_fk = '" + npp + "' " + 
						 " 	and NHAPP_KHO.nhomkenh_fk = '" + kenhkh + "' and NHAPP_KHO.kho_fk = '" + khoId + "' " +
						 "where a.TRAKHUYENMAI_FK = '" + this.id + "' " +
						 "order by NHAPP_KHO.available desc";
			}
			else
			{*/
				sql = "select a.Trakhuyenmai_fk, a.soluong, f.ma as spMa, f.ten as spTen, f.tenchuan, f.pk_seq, 0 as available, " +
						 " 		ISNULL ( ( select dongia from BANGGIABANLENPP_SANPHAM where sanpham_fk = f.pk_seq and BANGGIABLNPP_FK in ( select pk_seq from BANGGIABANLENPP where DVKD_FK = '100001' and pk_seq in ( select BANGGIABLNPP_FK from BANGGIABANLENPP_NPP where NPP_FK = '" + nppId +  "' ) ) ), 0)  as dongia " +
						 "from Trakhuyenmai_sanpham a inner join SANPHAM f on a.SANPHAM_FK = f.PK_SEQ  " +
						 "where a.TRAKHUYENMAI_FK = '" + this.id + "' " +
						 "order by f.ma desc";
			//}
			
			System.out.println("5.Cau lenh lay tra khuyen mai: " + sql + " \n");
			
			ResultSet spList = db.get(sql);
			try 
			{
				sanpham_soluong = new Hashtable<String, Integer>();
				sanpham_dongia = new Hashtable<String, Float>();
				sanpham_tensp = new Hashtable<String, String>(); 
				
				while(spList.next())
				{
					String masp = spList.getString("spMa");
					//String tensp = spList.getString("spTen") + " (Tồn hiện tại: " + spList.getInt("available") + ")"; 
					String tensp = spList.getString("spTen"); 
					String tenchuan = spList.getString("tenchuan") == null ? "" : spList.getString("tenchuan").toString() ; 
					if(tenchuan.trim().length() > 0)
						tensp = tenchuan;
					
					int soluong = 0; //mac dinh
					if(spList.getString("soluong") != null)
						soluong = Integer.parseInt(spList.getString("soluong"));
					float dongia = 0;
					if(spList.getString("dongia") != null)
						dongia = Float.parseFloat(spList.getString("dongia"));
					
					this.sanpham_soluong.put(masp, soluong);
					this.sanpham_dongia.put(masp, dongia);
					this.sanpham_tensp.put(masp, tensp);
				}
				spList.close();
			} 
			catch(Exception e) {}
			finally{try {
				if(spList != null)
					spList.close();
			} catch (Exception e2) {
				
			}}
		}		
	}
	
	public Trakhuyenmai(String id, String donhangId, String ctkmId, String nppId) //dung cho don hang da xuat kho ma ap lai khuyen mai
	{
		db = new dbutils();		
		ResultSet trakm = db.get("select pk_seq, diengiai, tongluong, tongtien, chietkhau, loai, hinhthuc from Trakhuyenmai where pk_seq = '" + id + "'");
		//System.out.println("Cau lenh tra khuyen mau la: " + trakm + "\n");
		if(trakm != null)
		{
			try 
			{
				trakm.next();
				this.id = trakm.getString("pk_seq");
				
				this.diengiai = "";
				if(trakm.getString("diengiai") != null)
					this.diengiai = trakm.getString("diengiai");
				
				this.tongluong = 0;
				if(trakm.getString("tongluong") != null)
					this.tongluong = Integer.parseInt(trakm.getString("tongluong"));
				
				this.tongtien = 0;
				if(trakm.getString("tongtien") != null)
					this.tongtien = Float.parseFloat(trakm.getString("tongtien"));
	
				this.chietkhau = 0;
				if(trakm.getString("chietkhau") != null)
					this.chietkhau = Float.parseFloat(trakm.getString("chietkhau"));
					
				this.type = 1;
				if(trakm.getString("loai") != null)
					this.type = Integer.parseInt(trakm.getString("loai"));
				
				this.hinhthuc = 2;
				if(trakm.getString("hinhthuc") != null)
					this.hinhthuc = Integer.parseInt(trakm.getString("hinhthuc"));
				trakm.close();
			} 
			catch(Exception e) {}
			finally{try {
				if(trakm != null)
					trakm.close();
			} catch (Exception e2) {
				
			}}
		}
		if(this.type == 3) //truong hop tra sanpham
		{
			String sql = "select c.*, d.soluong as slDxk from ";
			sql += "(select a.Trakhuyenmai_fk, a.soluong, a.dongia, b.ma as spMa, b.ten as spTen, b.pk_seq from Trakhuyenmai_sanpham a inner join sanpham b on a.sanpham_fk = b.pk_seq ";
			sql += "where a.Trakhuyenmai_fk = '" + id + "' and b.ma in (select spma from donhang_ctkm_trakm where donhangId = '" + donhangId + "' and ctkmId = '" + ctkmId + "')) c inner join ";
			sql += "(select trakmId, spma, soluong from donhang_ctkm_trakm where donhangId = '" + donhangId + "' and ctkmId = '" + ctkmId + "') d on c.Trakhuyenmai_fk = d.trakmId where c.spMa = d.spMa";
			
			System.out.print("\n" + sql + " \n");
			
			ResultSet spList = db.get(sql);
			try 
			{
				sanpham_soluong = new Hashtable<String, Integer>();
				sanpham_dongia = new Hashtable<String, Float>();
				sanpham_tensp = new Hashtable<String, String>(); 
				
				while(spList.next())
				{
					String masp = spList.getString("spMa");
					String tensp = spList.getString("spTen");
					int soluong = 0; //mac dinh
					if(spList.getString("soluong") != null)
						soluong = Integer.parseInt(spList.getString("soluong"));
					
					//Lay don gia cua sp (ko khai bao gia tren trung tam)
					float dongia = 0;
					/*String cmd = "select c.SANPHAM_FK, c.GIAMUANPP as dongia " +
								 "from NHAPHANPHOI a inner join BANGGIAMUANPP_NPP b  on a.PK_SEQ = b.NPP_FK " +
								 " inner join BGMUANPP_SANPHAM c on b.BANGGIAMUANPP_FK = c.BGMUANPP_FK " +
								 " inner join  BANGGIAMUANPP bgmnpp on bgmnpp.pk_seq=c.BGMUANPP_FK   " +
								 "where a.PK_SEQ = '" + nppId + "' and   bgmnpp.kenh_fk=(select kbh_fk from donhang where pk_seq='"+donhangId+"')  and c.SANPHAM_FK = (select pk_seq from sanpham where ma = '" + masp.trim() + "')";*/
					
					String cmd = "select c.SANPHAM_FK, c.GIAMUANPP as dongia " +
								 "from NHAPHANPHOI a inner join BANGGIAMUANPP_NPP b  on a.PK_SEQ = b.NPP_FK " +
								 " 	inner join BGMUANPP_SANPHAM c on b.BANGGIAMUANPP_FK = c.BGMUANPP_FK " +
								 " 	inner join  BANGGIAMUANPP bgmnpp on bgmnpp.pk_seq = c.BGMUANPP_FK   " +
								 "where a.PK_SEQ = '" + nppId + "' " +
								 		"and   bgmnpp.pk_seq in ( select top(1) a.PK_SEQ " +
								 "								  from BANGGIAMUANPP a inner join BANGGIAMUANPP_NPP b on a.PK_SEQ = b.BANGGIAMUANPP_FK   " +
								 "								  where a.TUNGAY <= ( select ngaynhap from donhang where pk_seq = '" + donhangId + "' ) and b.NPP_FK = '" + nppId + "' and a.KENH_FK = (select kbh_fk from donhang where pk_seq='" + donhangId + "') and a.DVKD_FK = '1' " +
								 "								  order by a.TUNGAY desc )  " +
								 		"and c.SANPHAM_FK = (select pk_seq from sanpham where ma = '" + masp.trim() + "')";
					
					ResultSet dong = db.get(cmd);
					if(dong != null)
					{
						if(dong.next())
						{
							if(dong.getString("dongia") != null)
							{
								dongia = Float.parseFloat(dong.getString("dongia"));
							}
							dong.close();
						}
					}
					
					int slDxk = spList.getInt("slDxk");
					
					this.sanpham_soluong.put(masp, slDxk); //lay so Min
					
					this.sanpham_dongia.put(masp, dongia);
					this.sanpham_tensp.put(masp, tensp);
				}
				spList.close();
			} 
			catch(Exception e) {}
			finally{try {
				if(spList != null)
					spList.close();
			} catch (Exception e2) {
				
			}}
		}		
	}
	
	public Trakhuyenmai(String id, String nppId, String ngaydh, String donhangId, String isTT)
	{
		db = new dbutils();		
		ResultSet trakm = db.get("select pk_seq, diengiai, tongluong, tongtien, chietkhau, loai, hinhthuc from Trakhuyenmai where pk_seq = '" + id + "'");
		
		if(trakm != null)
		{
			try 
			{
				trakm.next();
				this.id = trakm.getString("pk_seq");
				
				this.diengiai = "";
				if(trakm.getString("diengiai") != null)
					this.diengiai = trakm.getString("diengiai");
				
				this.tongluong = 0;
				if(trakm.getString("tongluong") != null)
					this.tongluong = Integer.parseInt(trakm.getString("tongluong"));
				
				this.tongtien = 0;
				if(trakm.getString("tongtien") != null)
					this.tongtien = Float.parseFloat(trakm.getString("tongtien"));
	
				this.chietkhau = 0;
				if(trakm.getString("chietkhau") != null)
					this.chietkhau = Float.parseFloat(trakm.getString("chietkhau"));
					
				this.type = 1;
				if(trakm.getString("loai") != null)
					this.type = Integer.parseInt(trakm.getString("loai"));
				
				this.hinhthuc = 2;
				if(trakm.getString("hinhthuc") != null)
					this.hinhthuc = Integer.parseInt(trakm.getString("hinhthuc"));
				trakm.close();
			} 
			catch(Exception e) {}
		}
		if(this.type == 3) //truong hop tra sanpham
		{
			/*String sql = "select a.Trakhuyenmai_fk, a.soluong, bgmnpp.dongia, f.ma as spMa, f.ten as spTen, f.pk_seq, isnull(kho.available, 0) as available " +
						 "from Trakhuyenmai_sanpham a " +
						 " inner join (  select sanpham_fk, GIAMUANPP as dongia  " +
						 "				from BGMUANPP_SANPHAM   " +
						 "				where BGMUANPP_FK in (  select top(1) a.PK_SEQ " +
						 "										from BANGGIAMUANPP a inner join BANGGIAMUANPP_NPP b on a.PK_SEQ = b.BANGGIAMUANPP_FK   " +
						 "										where a.TUNGAY <= '" + ngaydh + "' and b.NPP_FK = '" + nppId + "' and a.KENH_FK = ( select KBH_FK from ERP_DONDATHANG where PK_SEQ = '" + donhangId + "' ) and a.DVKD_FK = '1' " +
						 "										order by a.TUNGAY desc  )   " +
						 "			) bgmnpp on bgmnpp.sanpham_fk = a.sanpham_fk" + 
						 " inner join SANPHAM f on a.SANPHAM_FK = f.PK_SEQ left join ERP_KHOTT_SANPHAM kho on a.sanpham_fk = kho.sanpham_fk  " +
						 "where a.TRAKHUYENMAI_FK = '" + this.id + "' order by kho.available desc";*/
			
			String sql = " select a.Trakhuyenmai_fk, a.soluong, f.ma as spMa, f.ten as spTen, f.pk_seq, " +
						" 		( select GIAMUA_SAUCK as dongia   " +
						" 			from BGMUANPP_SANPHAM    " +
						" 			where SANPHAM_FK = f.pk_seq and BGMUANPP_FK in ( select top(1) bg.PK_SEQ  " +
						" 															 from BANGGIAMUANPP bg inner join BANGGIAMUANPP_NPP bg_npp on bg.PK_SEQ = bg_npp.BANGGIAMUANPP_FK    " +
						" 															 where bg.TUNGAY <= '" + ngaydh + "' and bg_npp.NPP_FK = '" + nppId + "' and bg.KENH_FK = ( select KBH_FK from ERP_DONDATHANG where PK_SEQ = '" + donhangId + "' ) and bg.DVKD_FK = f.DVKD_FK  " +
						" 															 order by bg.TUNGAY desc  )  )  as dongia " +
						" from Trakhuyenmai_sanpham a  " +
						" inner join ERP_SANPHAM f on a.SANPHAM_FK = f.PK_SEQ  " +
						" where a.TRAKHUYENMAI_FK = '" + this.id + "'  ";
			
			System.out.println("5.Cau lenh lay tra khuyen mai: " + sql + " \n");
			
			ResultSet spList = db.get(sql);
			try 
			{
				sanpham_soluong = new Hashtable<String, Integer>();
				sanpham_dongia = new Hashtable<String, Float>();
				sanpham_tensp = new Hashtable<String, String>(); 
				
				while(spList.next())
				{
					String masp = spList.getString("spMa");
					String tensp = spList.getString("spTen"); 
					
					int soluong = 0; //mac dinh
					if(spList.getString("soluong") != null)
						soluong = Integer.parseInt(spList.getString("soluong"));
					float dongia = 0;
					if(spList.getString("dongia") != null)
						dongia = Float.parseFloat(spList.getString("dongia"));
					
					this.sanpham_soluong.put(masp, soluong);
					this.sanpham_dongia.put(masp, dongia);
					this.sanpham_tensp.put(masp, tensp);
				}
				spList.close();
			} 
			catch(Exception e) {}
			finally{try {
				if(spList != null)
					spList.close();
			} catch (Exception e2) {
				
			}}
		}		
	}
	
	//THEM TRUONG HOP NHOM DAC BIET
	public Trakhuyenmai(String id, String nppId, String ngaydh, String donhangId, String sanphamId, String soluongTra, boolean nhomdacbiet)
	{
		db = new dbutils();		
		ResultSet trakm = db.get("select pk_seq, diengiai, tongluong, tongtien, chietkhau, loai, hinhthuc " +
								 "from Trakhuyenmai where pk_seq = '" + id + "'");
		//System.out.println("Cau lenh tra khuyen mai la: select pk_seq, diengiai, tongluong, tongtien, chietkhau, loai, hinhthuc from Trakhuyenmai where pk_seq = '" + id + "'\n");
		if(trakm != null)
		{
			try 
			{
				trakm.next();
				this.id = trakm.getString("pk_seq");
				
				this.diengiai = "";
				if(trakm.getString("diengiai") != null)
					this.diengiai = trakm.getString("diengiai");
				
				this.tongluong = 0;
				if(trakm.getString("tongluong") != null)
					this.tongluong = Integer.parseInt(trakm.getString("tongluong"));
				
				this.tongtien = 0;
				if(trakm.getString("tongtien") != null)
					this.tongtien = Float.parseFloat(trakm.getString("tongtien"));
	
				this.chietkhau = 0;
				if(trakm.getString("chietkhau") != null)
					this.chietkhau = Float.parseFloat(trakm.getString("chietkhau"));
					
				this.type = 1;
				if(trakm.getString("loai") != null)
					this.type = Integer.parseInt(trakm.getString("loai"));
				
				this.hinhthuc = 2;
				if(trakm.getString("hinhthuc") != null)
					this.hinhthuc = Integer.parseInt(trakm.getString("hinhthuc"));
				
				trakm.close();
			} 
			catch(Exception e) {}
		}
		
		if(this.type == 3 || this.type == 4) //truong hop tra sanpham
		{
			String sql = " select '" + id + "' Trakhuyenmai_fk, '" + soluongTra + "' soluong, f.ma as spMa, f.ten as spTen, f.pk_seq, " +
						" 		( select GIAMUA_SAUCK as dongia   " +
						" 			from BGMUANPP_SANPHAM    " +
						" 			where SANPHAM_FK = f.pk_seq and BGMUANPP_FK in ( select top(1) bg.PK_SEQ  " +
						" 															 from BANGGIAMUANPP bg inner join BANGGIAMUANPP_NPP bg_npp on bg.PK_SEQ = bg_npp.BANGGIAMUANPP_FK    " +
						" 															 where bg.TUNGAY <= '" + ngaydh + "' and bg_npp.NPP_FK = '" + nppId + "' and bg.KENH_FK = ( select KBH_FK from ERP_DONDATHANG where PK_SEQ = '" + donhangId + "' ) and bg.DVKD_FK = f.DVKD_FK  " +
						" 															 order by bg.TUNGAY desc  )  )  as dongia " +
						" from ERP_SANPHAM f   " +
						" where pk_seq = '" + sanphamId + "' ";
			
			System.out.println("5.Cau lenh lay tra khuyen mai: " + sql + " \n");
			
			ResultSet spList = db.get(sql);
			try 
			{
				sanpham_soluong = new Hashtable<String, Integer>();
				sanpham_dongia = new Hashtable<String, Float>();
				sanpham_tensp = new Hashtable<String, String>(); 
				
				while(spList.next())
				{
					String masp = spList.getString("spMa");
					String tensp = spList.getString("spTen"); 
					
					int soluong = 0; //mac dinh
					if(spList.getString("soluong") != null)
						soluong = Integer.parseInt(spList.getString("soluong"));
					float dongia = 0;
					if(spList.getString("dongia") != null)
						dongia = Float.parseFloat(spList.getString("dongia"));
					
					this.sanpham_soluong.put(masp, soluong);
					this.sanpham_dongia.put(masp, dongia);
					this.sanpham_tensp.put(masp, tensp);
				}
				spList.close();
			} 
			catch(Exception e) {}
			finally{try {
				if(spList != null)
					spList.close();
			} catch (Exception e2) {
				
			}}
		}
		db.shutDown();
	}
	
	public String getId()
	{
		return this.id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String nppId()
	{
		return this.nppId;
	}
	public void setNppId(String nppId)
	{
		this.nppId = nppId;
	}
	public void setDiengiai(String diengiai) 
	{
		this.diengiai = diengiai;
	}
	public String getDiengiai() 
	{
		return diengiai;
	}
	public int getType()
	{
		return this.type;
	}
	public void setType(int type)
	{
		this.type = type;
	}
	public int getHinhthuc()
	{
		return this.hinhthuc;
	}
	public void setHinhthuc(int hinhthuc)
	{
		this.hinhthuc = hinhthuc;
	}
	public float getTongtien()
	{
		return this.tongtien;
	}
	public void setTongtien(int tongtien)
	{
		this.tongtien = tongtien;
	}
	public float getChietkhau()
	{
		return this.chietkhau;
	}
	public void setChietkhau(float chietkhau)
	{
		this.chietkhau = chietkhau;
	}
	public int getTongluong()
	{
		return this.tongluong;
	}
	public void setTongluong(int tongluong)
	{
		this.tongluong = tongluong;
	}
	public Hashtable<String, Integer> getSanpham_Soluong()
	{
		return this.sanpham_soluong;
	}
	public void setSanpham_Soluong(Hashtable<String, Integer> sanpham)
	{
		this.sanpham_soluong = sanpham;
	}
	public Hashtable<String, Float> getsanpham_dongia()
	{
		return this.sanpham_dongia;
	}
	public void setsanpham_dongia(Hashtable<String, Float> sanpham)
	{
		this.sanpham_dongia = sanpham;
	}
	public Hashtable<String, String> getsanpham_tensp()
	{
		return this.sanpham_tensp;
	}
	public void setsanpham_tensp(Hashtable<String, String> sanpham)
	{
		this.sanpham_tensp = sanpham;
	}
	public float getTongGtriKm() 
	{
		float sum = 0.0f;
		
		Enumeration<String> keys = this.sanpham_soluong.keys();
		
		if(this.hinhthuc == 1)
		{			
			while(keys.hasMoreElements())
			{
				String key = keys.nextElement();
				sum += this.sanpham_soluong.get(key) * this.sanpham_dongia.get(key);
				//System.out.println("Sum gia tri la: " + sum + "\n");
	 		}
		}
		else
		{
			if(keys.hasMoreElements())
			{
				String key = keys.nextElement(); //chi lay don gia sp dau tien (cac sp con lai thuoc cung nhom, don gia xap xi)
				sum = this.tongluong * this.sanpham_dongia.get(key);	
			}
		}
		return sum;
	}
	
	public static void main(String[] arg)
	{
		/*Trakhuyenmai trakm = new Trakhuyenmai("100006");
		Hashtable<String, Integer> spList = trakm.getSanpham_Soluong();
		Enumeration<String> keys = spList.keys();
		while(keys.hasMoreElements())
		{
			String key = keys.nextElement();
			System.out.print(key + " --- " + Integer.toString(spList.get(key)) + "\n");
		}
		System.out.print("Tong gia tri traKM: " + Float.toString(trakm.getTongGtriKm()));*/
	}
	
	public void DBclose() 
	{
		try 
		{
			if(this.db != null)
				this.db.shutDown();
		} 
		catch (Exception e) {}
	}
	
	public String getHinhthucPrimary() 
	{
		return this.hinhthucPrimary;
	}

	public void setHinhthucPrimary(String hinhthuc) 
	{
		this.hinhthucPrimary = hinhthuc;
	}

	public double getTylequidoi()
	{
		return this.tylequydoi;
	}

	public void setTylequidoi(double tyle) 
	{
		this.tylequydoi = tyle;
	}

	public String getSpquidoi() 
	{
		return this.spQuidoiId;
	}

	public void setSpquidoi(String sp) 
	{
		this.spQuidoiId = sp;
	}

	public float getDongiaSpquidoi()
	{
		return this.dongiaquidoi;
	}

	public void setDongiaSpquidoi(float dongia) 
	{
		this.dongiaquidoi = dongia;
	}
	
	public int getPheptoan() 
	{
		return this.pheptoan;
	}
	
	public void setPheptoan(int pheptoan) 
	{
		this.pheptoan = pheptoan;
	}
	
}
