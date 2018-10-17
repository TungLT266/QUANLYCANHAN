package geso.traphaco.distributor.beans.phieuthuhoi.imp;

import geso.traphaco.distributor.beans.donhang.ISanpham;
import geso.traphaco.distributor.beans.donhang.imp.Sanpham;
import geso.traphaco.distributor.beans.phieuthuhoi.IPhieuthuhoi;
import geso.traphaco.distributor.beans.phieuthuhoi.ISanphamSoLo;
import geso.traphaco.distributor.beans.phieuthuhoi.ISanphamthuhoi;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import geso.traphaco.distributor.db.sql.dbutils;

public class Phieuthuhoi implements IPhieuthuhoi, Serializable
{
	private static final long serialVersionUID = -9217977546733610214L;

	String userId;
	String id;
	String pxkId;
	String trangthai;
	String ngaytao;
	String nguoitao;
	String ngaysua;
	String nguoisua;
	String msg;
	
	ResultSet nhanviengn;
	String nvgnId;
	String nvgnTen;
	
	String nppId;
	String nppTen;
	String sitecode;
	
	//in file pdf
	List<ISanphamthuhoi> spList;
	List<ISanphamthuhoi> spkmList;
	List<ISanpham> spkmSauthList;
	
	dbutils db;
	public Phieuthuhoi(String[] param)
	{
		this.id = param[0];
		this.pxkId = param[1];
		this.trangthai = param[2];
		this.ngaytao = param[3];
		this.nguoitao = param[4];
		this.ngaysua = param[5];
		this.nguoisua = param[6];
		this.msg = "";
		db = new dbutils();
	}
	
	public Phieuthuhoi(String id)
	{
		this.id = id;
		this.pxkId = "";
		this.trangthai = "";
		this.ngaytao = "";
		this.nguoitao = "";
		this.ngaysua = "";
		this.nguoisua = "";
		this.nvgnId = "";
		this.msg = "";
		db = new dbutils();
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
	
	public String getTrangthai() 
	{
		return this.trangthai;
	}
	
	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}
	
	public String getNgaytao()
	{
		return this.ngaytao;		
	}

	public void setNgaytao(String ngaytao)
	{
		this.ngaytao = ngaytao;
	}
	
	public String getNgaysua()
	{
		return this.ngaysua;	
	}

	public void setNgaysua(String ngaysua)
	{
		this.ngaysua = ngaysua;
	}
	
	public String getNguoitao()
	{
		return this.nguoitao;
	}
	
	public void setNguoitao(String nguoitao)
	{
		this.nguoitao = nguoitao;
	}

	public String getNguoisua()
	{
		return this.nguoisua;
	}

	public void setNguoisua(String nguoisua)
	{
		this.nguoisua = nguoisua;
	}
	
	public ResultSet getNhanvienGN() 
	{		
		return this.nhanviengn;
	}
	
	public void setNhanvienGN(ResultSet nhanviengn) 
	{
		this.nhanviengn = nhanviengn;		
	}
	
	public String getNvgnId() 
	{		
		return this.nvgnId;
	}
	
	public void setNvgnId(String nvgnId) 
	{
		this.nvgnId = nvgnId;		
	}
	
	public String getPhieuxuatkho() 
	{
		return this.pxkId;
	}

	public void setPhieuxuatkho(String pxkId) 
	{
		this.pxkId = pxkId;
	}
	
	public String getMessage() 
	{
		return this.msg;
	}
	
	public void setMessage(String msg) 
	{
		this.msg = msg;
	}
	
	public String getNppId() 
	{
		return this.nppId;
	}

	public void setNppId(String nppId) 
	{
		this.nppId = nppId;
	}
	
	public String getNppTen() 
	{
		return this.nppTen;
	}

	public void setNppTen(String nppTen) 
	{
		this.nppTen = nppTen;
	}
	
	public String getSitecode() 
	{
		return this.sitecode;
	}

	public void setSitecode(String sitecode) 
	{
		this.sitecode = sitecode;
	}
	
	private void getNppInfo()
	{
		/*try 
		{
			ResultSet rs = db.get("select a.pk_seq, a.ten, a.sitecode from nhaphanphoi a, nhanvien b where b.dangnhap = a.ma and b.pk_seq ='" + this.userId + "'");
			if( rs != null)
			{
				rs.next();
				this.nppId = rs.getString("pk_seq");
				this.nppTen = rs.getString("ten");
				this.sitecode = rs.getString("sitecode");
				
			}else
			{
				this.nppId = "";
				this.nppTen = "";
				this.sitecode = "";				
			}					
		} 
		catch(Exception e) {}
		*/
		//Phien ban moi
		geso.traphaco.distributor.util.Utility util=new geso.traphaco.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();
		//this.dangnhap = util.getDangNhap();
		this.sitecode=util.getSitecode();
	}

	public boolean CreatePth() 
	{
		return true;
	}

	public boolean UpdatePth() 
	{
		return true;
	}

	public void init() 
	{
		this.getNppInfo();
		
		String query = "select b.pk_seq as pxkId, a.ngaytao, c.ten as nvgnTen from phieuthuhoi a inner join phieuxuatkho b on a.phieuxuatkho_fk = b.pk_seq inner join nhanviengiaonhan c on b.nvgn_fk = c.pk_seq ";
		query = query + " where a.pk_seq = '" + this.id + "'";
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try
			{
				rs.next();
				this.pxkId = rs.getString("pxkId");
				this.ngaytao = rs.getDate("ngaytao").toString();
				this.nvgnTen = rs.getString("nvgnTen");
				rs.close();
			} 
			catch(Exception e) {}
			finally{try {
				if(rs != null)
					rs.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}}
		}
		
		query = "select solo,sp.pk_seq as spId, sp.ma as spMa, sp.ten as spTen, pth_sp.soluong from phieuthuhoi_sanpham_chitiet pth_sp inner join sanpham sp on pth_sp.sanpham_fk = sp.pk_seq  where pth_sp.pth_fk = '" + this.id + "'";
		ResultSet sanphamRS = db.get(query);
		List<ISanphamthuhoi> sanphamList = new ArrayList<ISanphamthuhoi>();
		if(sanphamRS != null)
		{
			
			ISanphamthuhoi sp = null;	
			try 
			{
				while(sanphamRS.next())
				{
					sp = new Sanphamthuhoi();
					sp.setSPId(sanphamRS.getString("spId"));
					sp.setMasanpham(sanphamRS.getString("spMa"));
					sp.setTensanpham(sanphamRS.getString("spTen"));
					sp.setSoluong(sanphamRS.getString("soluong"));
					sp.setSolo(sanphamRS.getString("solo"));
					
					sanphamList.add(sp);
				}
				sanphamRS.close();
			} 
			catch(Exception e) {
				e.printStackTrace();
			}
			finally{try {
				if(sanphamRS != null)
					sanphamRS.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}}
		}
		this.spList = sanphamList;
		
		query = "select c.ten as khoTen,solo, d.ten as kbhTen, b.pk_seq as spId, b.ma as spMa, b.ten as spTen, sum(a.soluong) as soluong from phieuthuhoi_spkm_chitiet a inner join sanpham b on a.sanpham_fk = b.pk_seq left join kho c on a.kho_fk = c.pk_seq ";
		query += "left join kenhbanhang d on a.kbh_fk = d.pk_seq where a.pth_fk = '" + this.id + "'" +
				" group by solo,c.ten, d.ten, b.pk_seq, b.ma, b.ten";
		System.out.println(query);
		ResultSet sanphamKmRS = db.get(query);
		List<ISanphamthuhoi> sanphamKmList = new ArrayList<ISanphamthuhoi>();
		if(sanphamKmRS != null)
		{
			
			ISanphamthuhoi sp = null;	
			try 
			{
				while(sanphamKmRS.next())
				{

					sp = new Sanphamthuhoi();
					sp.setSPId(sanphamKmRS.getString("spId"));
					sp.setMasanpham(sanphamKmRS.getString("spMa"));
					sp.setTensanpham(sanphamKmRS.getString("spTen"));
					sp.setSoluong(sanphamKmRS.getString("soluong"));
					sp.setSolo(sanphamKmRS.getString("solo"));
					sp.setKhoTen(sanphamKmRS.getString("khoTen"));
					sp.setKenhTen(sanphamKmRS.getString("kbhTen"));
					sanphamKmList.add(sp);
					
				}
				sanphamKmRS.close();
			} 
			catch(Exception e) { 
				e.printStackTrace();
			}
			
			finally{try {
				if(sanphamKmRS != null)
					sanphamKmRS.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}}
		}
		
		this.spkmList = sanphamKmList;
	}
	
	private void createSpkmSauthuhoi()
	{
		List<ISanpham> spkmNewList = new ArrayList<ISanpham>();
		String query = "select donhang_fk as dhId from phieuxuatkho_donhang where pxk_fk = '" + this.pxkId + "'";
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					String dhId = rs.getString("dhId");
					query = "select a.trakmId, a.soxuat, a.spMa, isnull(a.soluong, 0) as soluong, tkm.loai, tkm.hinhthuc, tkm.tongluong from donhang_ctkm_trakm a inner join trakhuyenmai tkm on a.trakmid = tkm.pk_seq ";
					query = query + "where a.donhangid = '" + dhId + "'";
					
					ResultSet spKhuyenmaiRS = db.get(query);
					if(spKhuyenmaiRS != null) //don hang co spkm
					{
						try 
						{
							while(spKhuyenmaiRS.next())
							{
								String trakmId = spKhuyenmaiRS.getString("trakmId");
								String type = spKhuyenmaiRS.getString("loai");
								String hinhthuc = spKhuyenmaiRS.getString("hinhthuc");
								String soxuat = spKhuyenmaiRS.getString("soxuat");
								String soluong = spKhuyenmaiRS.getString("soluong");
								if(type.equals("3")) //tra sp
								{
									if(hinhthuc.equals("2"))
									{
										//String tongluong = spKhuyenmaiRS.getString("tongluong");
										String spMa = spKhuyenmaiRS.getString("spMa");
										
										String sql = "select pk_seq as spId, ma as spMa, ten as spTen from sanpham where ma = '" + spMa + "'";
										ResultSet spKM = db.get(sql);
										if(spKM != null)
										{
											spKM.next();
											
											String slg = Integer.toString(Integer.parseInt(soxuat) * Integer.parseInt(soluong));																						
											ISanpham sp = new Sanpham(spKM.getString("spId"), spKM.getString("spMa"), spKM.getString("spTen"), slg, "", "", "", "");
											spkmNewList.add(sp);
											
											spKM.close();								
										}
									}
									else
									{
										String sql = "select sp.pk_seq as spId, sp.ma as spMa, sp.ten as spTen, tkm_sp.soluong from trakhuyenmai_sanpham tkm_sp inner join sanpham sp on tkm_sp.sanpham_fk = sp.pk_seq ";
										sql = sql +	" where tkm_sp.trakhuyenmai_fk = '" + trakmId.trim() + "'";
										ResultSet spKM = db.get(sql);
										if(spKM != null)
										{
											while(spKM.next())
											{									
												String slg = Integer.toString( Integer.parseInt(soxuat) * Integer.parseInt(spKM.getString("soluong")) );
												ISanpham sp = new Sanpham(spKM.getString("spId"), spKM.getString("spMa"), spKM.getString("spTen"), slg, "", "", "", "");
												spkmNewList.add(sp);
											}
											spKM.close();
										}
									}
								}
							}
						} 
						catch(Exception e) {}
					}
				}
				rs.close();
			} 
			catch(Exception e) {}
			finally{
				try {
					if(rs != null)
						rs.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}

		//hieu chinh lai spkm (Cong don)
		for(int i=0; i < spkmNewList.size() - 1; i++)
		{
			Sanpham spA  = (Sanpham)spkmNewList.get(i);
			for(int j = i+1; j < spkmNewList.size(); j++)
			{				
				Sanpham spB = (Sanpham)spkmNewList.get(j);
				if(spA.getId().trim().equals(spB.getId().trim()))
				{
					int slg = Integer.parseInt(spA.getSoluong()) + Integer.parseInt(spB.getSoluong());
					spkmNewList.get(i).setSoluong(Integer.toString(slg));
					spkmNewList.remove(j);
					i--;
				}
			}
		}
		
		this.spkmSauthList = spkmNewList;
	}
	
	public void createRS()
	{
		this.getNppInfo();
		this.createNvgnRs();
	}
	
	private void createNvgnRs()
	{
		String sql = "select pk_seq as nvgnId, ten as nvgnTen from nhanviengiaonhan where npp_fk = '" + this.nppId + "' and trangthai='1'";
		this.nhanviengn = db.get(sql);
	}
	
	public List<ISanphamthuhoi> getSanphamList() 
	{
		return this.spList;
	}
	
	public void setSanphamList(List<ISanphamthuhoi> spList) 
	{
		this.spList = spList;
	}

	public List<ISanphamthuhoi> getSpkmList() 
	{
		return this.spkmList;
	}

	public void setSpkmList(List<ISanphamthuhoi> spkmList) 
	{
		this.spkmList = spkmList;
	}
	
	public void DBclose() 
	{
		try 
		{
			if(this.nhanviengn != null)
				this.nhanviengn.close();
			if(this.spList!=null){
				spList.clear();
			}
			if(this.spkmList!=null){
				spkmList.clear();
			}
			if(this.spkmSauthList!=null){
				spkmSauthList.clear();
			}
			if(this.db != null)
			this.db.shutDown();
			 spList=null;
			 spkmList=null;
			 spkmSauthList=null;
		} 
		catch(Exception e) {}
		
		
	}

	public List<ISanpham> getSpkmSauthList() 
	{
		return this.spkmSauthList;
	}

	public void setSpkmSauthList(List<ISanpham> spkmSauthList) 
	{
		this.spkmSauthList = spkmSauthList;
	}

	public String getNvgnTen() 
	{
		return this.nvgnTen;
	}

	public void setNvgnTen(String nvgnTen)
	{
		this.nvgnTen = nvgnTen;
	}

	@Override
	public void init1() {
		// TODO Auto-generated method stub
		this.getNppInfo();
		String query = "select b.pk_seq as pxkId, a.ngaytao, c.ten as nvgnTen from phieuthuhoi a inner join phieuxuatkho b on a.phieuxuatkho_fk = b.pk_seq inner join nhanviengiaonhan c on b.nvgn_fk = c.pk_seq ";
		query = query + " where a.pk_seq = '" + this.id + "'";
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try
			{
				rs.next();
				this.pxkId = rs.getString("pxkId");
				this.ngaytao = rs.getDate("ngaytao").toString();
				this.nvgnTen = rs.getString("nvgnTen");
				rs.close();
			} 
			catch(Exception e) {
				
			}
			finally{try {
				if(rs != null)
					rs.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}}
		}
		  query=" select PTH_FK from PHIEUTHUHOI_SANPHAM_CHITIET where PTH_FK="+this.id  + 
		  		"  union select PTH_FK from PHIEUTHUHOI_SPKM_CHITIET where PTH_FK="+this.id;
		System.out.println("get du lieu thu hoi : "+query);
		
		ResultSet rscheck=db.get(query);
		boolean settudong=true;
		try{
			if(rscheck.next()){
				settudong=false;
			}
		rscheck.close();
		}catch(Exception er){
			this.msg=er.toString();
			er.printStackTrace();
		}
		
		query = " select pth_sp.kbh_fk,pth_sp.kho_fk, sp.pk_seq as spId,dvdl.donvi , sp.ma as spMa, sp.ten as spTen, pth_sp.soluong from phieuthuhoi_sanpham " +
				" pth_sp " +
				" inner join sanpham sp on pth_sp.sanpham_fk = sp.pk_seq " +
				" inner join donvidoluong dvdl on dvdl.pk_seq=sp.dvdl_fk   " +
				" where pth_sp.pth_fk = '" + this.id + "'";
		//System.out.println("get du lieu thu hoi : "+query);
		ResultSet sanphamRS = db.get(query);
		List<ISanphamthuhoi> sanphamList = new ArrayList<ISanphamthuhoi>();
		if(sanphamRS != null)
		{
			String[] param = new String[8];
			ISanphamthuhoi sp = null;	
			try 
			{
				while(sanphamRS.next())
				{
				
				
					
					sp = new Sanphamthuhoi();
					sp.setSPId(sanphamRS.getString("spId"));
					sp.setMasanpham(sanphamRS.getString("spMa"));
					sp.setTensanpham(sanphamRS.getString("spTen"));
					sp.setSoluong(sanphamRS.getString("soluong"));
					long soluongtong=sanphamRS.getLong("soluong");
					
					sp.setDonvitinh(sanphamRS.getString("donvi"));
					sp.setKhoId(sanphamRS.getString("kho_fk"));
					sp.setKenhId(sanphamRS.getString("kbh_fk"));
					//query="select SOLO,NGAYHETHAN FROM PHIEUXUATKHO_SANPHAM_CHITIET WHERE PXK_FK="+ this.pxkId +" and SANPHAM_FK="+sanphamRS.getString("spId") ;
					query=  " SELECT SOLO,NGAYHETHAN,SUM(SOLUONG) as soluong,sum(soluongxuatkho) as soluongxuatkho FROM "+
							" ( "+
							" select SOLO,NGAYHETHAN,0 AS SOLUONG ,soluong  -  ISNULL(( SELECT ISNULL(SUM(SOLUONG),0) FROM PHIEUTHUHOI_SANPHAM_CHITIET PTHSP  "+
							" INNER JOIN PHIEUTHUHOI PTH  ON   PTH.PK_SEQ=PTHSP.PTH_FK WHERE PTH.TRANGTHAI IN (1)  "+
							" AND PTH.PHIEUXUATKHO_FK=A.PXK_FK AND PTHSP.SOLO=A.SOLO  AND A.SANPHAM_FK=PTHSP.SANPHAM_FK  "+
							" AND A.KBH_FK=PTHSP.KBH_FK AND A.KHO_FK=PTHSP.KHO_FK "+
							" ),0) as soluongxuatkho FROM PHIEUXUATKHO_SANPHAM_CHITIET A WHERE PXK_FK="+ this.pxkId +" and SANPHAM_FK="+sanphamRS.getString("spId")+" AND KBH_FK="+sanphamRS.getString("kbh_fk")+" AND KHO_FK= "+sanphamRS.getString("kho_fk")+
							" union all "+
							" select SOLO,NGAYHETHAN,SOLUONG ,0 as soluongxuatkho FROM PHIEUTHUHOI_SANPHAM_CHITIET WHERE PTH_FK="+this.id+" AND  SANPHAM_FK="+sanphamRS.getString("spId")+" AND KBH_FK="+sanphamRS.getString("kbh_fk")+" AND KHO_FK= "+sanphamRS.getString("kho_fk")+
							" ) AS A GROUP BY SOLO,NGAYHETHAN "; 
					System.out.println(query);
					ResultSet rssolo=db.get(query);
					List<ISanphamSoLo> listlo=new ArrayList<ISanphamSoLo>();
					while (rssolo.next()){
						ISanphamSoLo lo=new SanphamSolo();
						
						lo.setMasanpham(sanphamRS.getString("spMa"));
						lo.setSolo(rssolo.getString("solo"));
						
						if(settudong){
							
							//System.out.println(soluongtong);
							if(rssolo.getLong("soluongxuatkho") <= soluongtong){
								lo.setSoluong(rssolo.getString("soluongxuatkho"));
								soluongtong=soluongtong-rssolo.getLong("soluongxuatkho");
								
							}else{
								lo.setSoluong(soluongtong+"");
								soluongtong=0;
							}
							
						}else{
							lo.setSoluong(rssolo.getString("soluong"));
						}
						lo.setNgayhethang(rssolo.getString("NGAYHETHAN"));
						lo.setSoluongXK(rssolo.getString("soluongxuatkho"));
						listlo.add(lo);
					}
					rssolo.close();
					sp.SetSpLoList(listlo);
					sanphamList.add(sp);
				}
				sanphamRS.close();
			} 
			catch(Exception e) {
				this.msg="Vui lòng thông báo với Admin để được giúp đỡ .Lỗi"+e.getMessage();
				e.printStackTrace();
			}
			finally{try {
				if(sanphamRS != null)
					sanphamRS.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}}
		}
		this.spList = sanphamList;
		
		query =  " select a.kho_fk,a.kbh_fk,dvdl.donvi ,c.ten as khoTen, d.ten as kbhTen, " +
				 " b.pk_seq as spId, b.ma as spMa, b.ten as spTen, sum(a.soluong) as soluong " +
				 " from phieuthuhoi_spkm a " +
				 " inner join sanpham b on a.sanpham_fk = b.pk_seq " +
				 " inner join donvidoluong dvdl on dvdl.pk_seq=b.dvdl_fk " +
				 " inner join kho c on a.kho_fk = c.pk_seq  " +
				 " inner join kenhbanhang d on a.kbh_fk = d.pk_seq where a.pth_fk = '" + this.id + "' " +
				 " group by a.kho_fk,a.kbh_fk,c.ten, d.ten, b.pk_seq, b.ma, b.ten, dvdl.donvi ";
		System.out.println("Get Query : "+query);
		ResultSet sanphamKmRS = db.get(query);
		List<ISanphamthuhoi> sanphamKmList = new ArrayList<ISanphamthuhoi>();
		if(sanphamKmRS != null)
		{
			String[] param = new String[8];
			ISanphamthuhoi sp = null;	
			try 
			{
				while(sanphamKmRS.next())
				{
					sp = new Sanphamthuhoi();
					sp.setSPId(sanphamKmRS.getString("spId"));
					sp.setMasanpham(sanphamKmRS.getString("spMa"));
					sp.setTensanpham(sanphamKmRS.getString("spTen"));
					sp.setSoluong(sanphamKmRS.getString("soluong"));
					long soluongtong=sanphamKmRS.getLong("soluong");
					sp.setDonvitinh(sanphamKmRS.getString("donvi"));
					sp.setKhoId(sanphamKmRS.getString("kho_fk"));
					sp.setKenhId(sanphamKmRS.getString("kbh_fk"));
					
					//sp.setCTKM(sanphamRS.getString("ctkmid"));
					//query=  " select SOLO,NGAYHETHAN FROM PHIEUXUATKHO_SPKM_CHITIET WHERE  PXK_FK="+ this.pxkId +" and SANPHAM_FK="+sanphamKmRS.getString("spId") ;
					
					query=  " SELECT SOLO,NGAYHETHAN,SUM(SOLUONG) as soluong ,sum(soluongxuatkho) as soluongxuatkho  FROM "+
					" ( "+
					" select SOLO,NGAYHETHAN,0 AS SOLUONG, SUM(soluong)  -  ISNULL(( SELECT ISNULL(SUM(SOLUONG),0) FROM PHIEUTHUHOI_SPKM_CHITIET PTHSP  "+
							" INNER JOIN PHIEUTHUHOI PTH  ON   PTH.PK_SEQ=PTHSP.PTH_FK WHERE PTH.TRANGTHAI IN (1)  "+
							" AND PTH.PHIEUXUATKHO_FK=A.PXK_FK AND PTHSP.SOLO=A.SOLO  AND A.SANPHAM_FK=PTHSP.SANPHAM_FK  "+
							" AND A.KBH_FK=PTHSP.KBH_FK AND A.KHO_FK=PTHSP.KHO_FK "+
							" ),0)  as soluongxuatkho FROM PHIEUXUATKHO_SPKM_CHITIET A WHERE PXK_FK="+ this.pxkId +" and SANPHAM_FK="+sanphamKmRS.getString("spId")+" AND KBH_FK="+sanphamKmRS.getString("kbh_fk")+" AND KHO_FK= "+sanphamKmRS.getString("kho_fk")+
					" GROUP BY SOLO,NGAYHETHAN,SANPHAM_FK,KBH_FK,KHO_FK,PXK_FK  " +
					" union all "+
					" select SOLO,NGAYHETHAN,SOLUONG,0 as soluongxuatkho  FROM PHIEUTHUHOI_SPKM_CHITIET WHERE PTH_FK="+this.id+" AND SANPHAM_FK="+sanphamKmRS.getString("spId")+" AND KBH_FK="+sanphamKmRS.getString("kbh_fk")+" AND KHO_FK= "+sanphamKmRS.getString("kho_fk")+
					" ) AS A GROUP BY SOLO,NGAYHETHAN"; 
					System.out.println(query);
					ResultSet rssolo=db.get(query);
					List<ISanphamSoLo> listlo=new ArrayList<ISanphamSoLo>();
					while (rssolo.next()){
						ISanphamSoLo lo=new SanphamSolo();
						lo.setMasanpham(sanphamKmRS.getString("spMa"));
						lo.setSolo(rssolo.getString("solo"));
						 
						if(settudong){
							
							//System.out.println(soluongtong);
							if(rssolo.getLong("soluongxuatkho") <= soluongtong){
								lo.setSoluong(rssolo.getString("soluongxuatkho"));
								soluongtong=soluongtong-rssolo.getLong("soluongxuatkho");
								
							}else{
								lo.setSoluong(soluongtong+"");
								soluongtong=0;
							}
							
						}else{
							lo.setSoluong(rssolo.getString("soluong"));
						}
						
						lo.setNgayhethang(rssolo.getString("NGAYHETHAN"));
						lo.setSoluongXK(rssolo.getString("soluongxuatkho"));
						listlo.add(lo);
					}
					rssolo.close();
					sp.SetSpLoList(listlo);
				
					sanphamKmList.add(sp);
				}
				sanphamKmRS.close();
				
			} 
			catch(Exception e) {
				e.printStackTrace();
			}
			
			finally{try {
				if(sanphamKmRS != null)
					sanphamKmRS.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}}
		}
		
		this.spkmList = sanphamKmList;
	}

	@Override
	public boolean UpdatePth(String ischot) {
		// TODO Auto-generated method stub
		try{
			
			
			String sql = " select b.pk_seq as pxkId, a.ngaytao, c.ten as nvgnTen from phieuthuhoi a " +
					" inner join phieuxuatkho b on a.phieuxuatkho_fk = b.pk_seq " +
					" inner join nhanviengiaonhan c on b.nvgn_fk = c.pk_seq "+
					" where a.pk_seq = '" + this.id + "' and a.trangthai=0 ";
			//System.out.println(sql);
			ResultSet rs = db.get(sql);
			if(!rs.next()){
				return false;
			}
			rs.close();
			
			db.getConnection().setAutoCommit(false);
			  sql="delete PHIEUTHUHOI_SANPHAM_CHITIET where pth_fk="+this.id;
			if(!db.update(sql)){
				 db.update("rollback");
				 this.msg="Error :"+ sql;
				 return false;
			 }
			 sql="delete PHIEUTHUHOI_SPKM_CHITIET where pth_fk="+this.id;
			if(!db.update(sql)){
				 db.update("rollback");
				 this.msg="Error :"+ sql;
				 return false;
			 }
				int m=0;
				 sql="";
				while(m <this.spList.size()){
					ISanphamthuhoi sp=this.spList.get(m);
					List<ISanphamSoLo> list=sp.getSPLoList();
					int k=0;
					while(k<list.size()){
						ISanphamSoLo lo=list.get(k);
						 if(Double.parseDouble(lo.getSoluong()) >0) {
							 sql=" INSERT INTO PHIEUTHUHOI_SANPHAM_CHITIET ( PTH_FK,SANPHAM_FK,KHO_FK,KBH_FK,SOLUONG,SOLO,NGAYHETHAN) "+
								 " VALUES ("+this.id+","+sp.getSPId()+","+sp.getKhoId()+","+sp.getKenhId()+","+lo.getSoluong()+",N'"+lo.getSOLo()+"','"+lo.getNgayhethan()+"') ";
							 System.out.println("Insert : "+sql);
							 
							 if(!db.update(sql)){
								 db.update("rollback");
								 this.msg="Error :"+ sql;
								 return false;
							 }
						 }
						k++;
					}
					m++;
				}
				// hang khuyene mai
				m=0;
				while(m <this.spkmList.size()){
					ISanphamthuhoi sp=this.spkmList.get(m);
					List<ISanphamSoLo> list=sp.getSPLoList();
					int k=0;
					while(k<list.size()){
						ISanphamSoLo lo=list.get(k);
						 if(Double.parseDouble(lo.getSoluong()) >0) {
							 sql=" INSERT INTO PHIEUTHUHOI_SPKM_CHITIET ( PTH_FK,SANPHAM_FK,KHO_FK,KBH_FK,SOLUONG,SOLO,NGAYHETHAN) "+
								 " VALUES ("+this.id+","+sp.getSPId()+","+sp.getKhoId()+","+sp.getKenhId()+","+lo.getSoluong()+",N'"+lo.getSOLo()+"','"+lo.getNgayhethan()+"') ";
							 System.out.println("Insert : "+sql);
							 if(!db.update(sql)){
								 db.update("rollback");
								 this.msg="Error :"+ sql;
								 return false;
							 }
						 }
						k++;
					}
					m++;
				}
			/*	sql=  " select a.SOLUONG ,a.SOLO "+
					  " from PHIEUXUATKHO_SANPHAM_CHITIET a  "+ 
					  " where a.PXK_FK= "+this.pxkId +
					  " and "+
					  " (  select isnull(SUM(soluong),0) from PHIEUTHUHOI_SANPHAM_CHITIET pthsp  inner join PHIEUTHUHOI pth "+  
					  " on   pth.PK_SEQ=pthsp.PTH_FK where pth.trangthai in (1,0) " +
					  " and pth.PHIEUXUATKHO_FK=a.PXK_FK and pthsp.SOLO=a.SOLO  and a.SANPHAM_FK=pthsp.SANPHAM_FK and A.KBH_FK=PTHSP.kbh_fk and A.KHO_FK=PTHSP.kho_fk ) >a.SOLUONG " +
					  " union all " +
					  "  select sum(a.SOLUONG) as soluong ,a.SOLO "+ 
					  " from PHIEUXUATKHO_SPKM_CHITIET  a  "+
					  " where a.PXK_FK= "+this.pxkId +
					  " group by  a.SOLO, a.SANPHAM_FK,a.PXK_FK "+ 
					  "  having "+
					  " (  select isnull(SUM(soluong),0) from PHIEUTHUHOI_SPKM_CHITIET pthsp  inner join PHIEUTHUHOI pth    "+
					  "  on   pth.PK_SEQ=pthsp.PTH_FK where pth.PHIEUXUATKHO_FK=a.PXK_FK and pthsp.SOLO=a.SOLO and a.SANPHAM_FK=pthsp.SANPHAM_FK "+ 
					  " ) >SUM(a.SOLUONG)  ";*/
				
				sql= " SELECT SUM(A.SOLUONG )  AS SOLUONG,A.SOLO  FROM PHIEUXUATKHO_SANPHAM_CHITIET A  "+
					 " WHERE  A.PXK_FK= "+ this.pxkId+
					 " GROUP BY A.SOLO, A.SANPHAM_FK,A.PXK_FK, KBH_FK,KHO_FK "+
					 "  HAVING  ( "+
					 " SELECT ISNULL(SUM(SOLUONG),0) FROM PHIEUTHUHOI_SANPHAM_CHITIET PTHSP  "+
					 " INNER JOIN PHIEUTHUHOI PTH  ON   PTH.PK_SEQ=PTHSP.PTH_FK WHERE PTH.TRANGTHAI IN (1,0)   "+
					 " AND PTH.PHIEUXUATKHO_FK=A.PXK_FK AND PTHSP.SOLO=A.SOLO  AND A.SANPHAM_FK=PTHSP.SANPHAM_FK  "+
					 " AND A.KBH_FK=PTHSP.KBH_FK AND A.KHO_FK=PTHSP.KHO_FK "+
					 "  ) >SUM(A.SOLUONG ) "+
					 " UNION ALL  "+
					 " SELECT SUM(A.SOLUONG) AS SOLUONG ,A.SOLO  FROM PHIEUXUATKHO_SPKM_CHITIET  A   "+
					 " WHERE A.PXK_FK="+this.pxkId +
					 " GROUP BY  A.SOLO, A.SANPHAM_FK,A.PXK_FK, KBH_FK,KHO_FK "+
					 " HAVING  (  SELECT ISNULL(SUM(SOLUONG),0) FROM PHIEUTHUHOI_SPKM_CHITIET PTHSP  INNER JOIN PHIEUTHUHOI PTH   "+
					 " ON   PTH.PK_SEQ=PTHSP.PTH_FK WHERE PTH.PHIEUXUATKHO_FK=A.PXK_FK AND PTHSP.SOLO=A.SOLO  "+
					 " AND A.SANPHAM_FK=PTHSP.SANPHAM_FK  AND PTHSP.KBH_FK=A.KBH_FK AND PTHSP.KHO_FK=A.KHO_FK "+
					 "  ) >SUM(A.SOLUONG) ";
					
				//System.out.println("Kiem Tra Co Vuot So Luong Trong PXK : "+sql);
			
				ResultSet rscheck=db.get(sql);
			if(rscheck.next()){
					
				 this.msg="Số lượng hàng thu hồi của lô : "+ rscheck.getString("solo")+" đã vượt quá số lượng trong phiếu xuất kho, vui lòng kiểm tra lại";
				 rscheck.close();
				 db.update("rollback");
				 return false;
				
			}
			rscheck.close();
				
			if(ischot.equals("1")){	
				if(! this.Chotphieuthuhoi(this.id)){
					return false;
				}
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
		}catch(Exception err){
			err.printStackTrace();
			
			 db.update("rollback");
			 this.msg="Error :"+ err;
			 return false;
		}
		return true;
	}
	private boolean Chotphieuthuhoi(String pthId) 
	{
		
		try 
		{
			
		
			//cap nhat ton kho sanpham
		 //	String query = " select sanpham_fk as spId, soluong, kho_fk as khoId, kbh_fk as kbhId from phieuthuhoi_sanpham where pth_fk = '" + pthId + "'";
			String query=   "  select pth.npp_fk ,a.pth_fk,a.sanpham_fk as spId,a.kho_fk as khoId  " +
							"  ,a.kbh_fk as kbhId ,a.soluong,a.solo from PHIEUTHUHOI_SANPHAM_CHITIET a "+
							"  inner join PHIEUTHUHOI pth on pth.PK_SEQ=a.pth_fk  "+
							"  where a.PTH_FK="+pthId  + " and pth.trangthai= 0";
			
			ResultSet rs = db.get(query);
			 
				while(rs.next())
				{
					query = " update nhapp_kho set soluong =  soluong + " + rs.getString("soluong") + ", available = available + " + rs.getString("soluong") + " where " +
							" sanpham_fk=" + rs.getString("spId") +" and npp_fk = " + rs.getString("npp_fk") + " and kbh_fk=" + rs.getString("kbhId") + " and kho_fk = " + rs.getString("khoId");
					System.out.println("Cau lenh cap nhat san pham la: " + query + "\n");
					if(!db.update(query))
					{
						db.update("rollback");
						msg = "Khong the cap nhat nhapp_kho " + query;
						return false;
					}
					
					query = " update nhapp_kho_chitiet set soluong =  soluong + " + rs.getString("soluong") + ", available = available + " + rs.getString("soluong") +  
							" where solo=N'"+rs.getString("solo")+"' and sanpham_fk=" + rs.getString("spId") +" and npp_fk = " +  rs.getString("npp_fk") + " and kbh_fk=" + rs.getString("kbhId") + " and kho_fk = " + rs.getString("khoId");
					System.out.println("Cau lenh cap nhat san pham la: " + query + "\n");
					if(!db.update(query))
					{
						db.update("rollback");
						msg = "Khong the cap nhat nhapp_kho " + query;
						return false;
					}
				}
				rs.close();
			 
			
			//cap nhat ton kho sanpham khuyen mai
			//query = "select sanpham_fk as spId, soluong, kho_fk as khoId, kbh_fk as kbhId from phieuthuhoi_spkm where pth_fk = '" + pthId + "'";
			
			query=" select pth.npp_fk ,a.pth_fk,a.sanpham_fk as spId,a.kho_fk as khoId " +
				  " ,a.kbh_fk as kbhId ,a.soluong,a.solo from PHIEUTHUHOI_SPKM_CHITIET a "+
				  " inner join PHIEUTHUHOI pth on pth.PK_SEQ=a.pth_fk  "+
				  " where a.PTH_FK="+pthId + " and pth.trangthai= 0";
			System.out.println("Cau lenh lay spkm la: " + query + "\n");
			ResultSet rsKm = db.get(query);
			 
				while(rsKm.next())
				{
					query = " update nhapp_kho set soluong = soluong + " + rsKm.getString("soluong") + ", available = available + " + rsKm.getString("soluong") + "" +
							" where sanpham_fk=" + rsKm.getString("spId") +" and npp_fk = " +  rsKm.getString("npp_fk") + " and kbh_fk=" + rsKm.getString("kbhId") + " and kho_fk = " + rsKm.getString("khoId");
					System.out.println("Cau lenh cap nhat san pham khuyen mai la: " + query + "\n");
					if(!db.update(query))
					{
						db.update("rollback");
						msg = "Khong the cap nhat san pham khuyen mai nhapp_kho " + query;
						return false;
					}
					query = "update nhapp_kho_chitiet set soluong = soluong + " + rsKm.getString("soluong") + ", available = available + " + rsKm.getString("soluong") + " " +
							" where solo=N'"+rsKm.getString("solo")+"' and sanpham_fk=" + rsKm.getString("spId") +" and npp_fk = " +  rsKm.getString("npp_fk") + " and kbh_fk=" + rsKm.getString("kbhId") + " and kho_fk = " + rsKm.getString("khoId");
					System.out.println("Cau lenh cap nhat san pham khuyen mai la: " + query + "\n");
					if(!db.update(query))
					{
						db.update("rollback");
						msg = "Khong the cap nhat san pham khuyen mai nhapp_kho " + query;
						return false;
					}
					
				}
				rsKm.close();
			 
			query="update phieuthuhoi set trangthai = '1' where pk_seq = '" + pthId + "'";
			if(!db.update(query))
			{
				db.update("rollback");
				msg = "Khong the cap nhat san pham khuyen mai nhapp_kho " + query;
				return false;
			}
			
		
		} 
		catch(Exception e) {
			e.printStackTrace();
			db.update("rollback");
			this.msg=e.toString();
			return false;
		} 
		
		return true;
	}

}
