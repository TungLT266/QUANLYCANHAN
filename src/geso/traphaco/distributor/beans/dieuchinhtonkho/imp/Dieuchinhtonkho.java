package geso.traphaco.distributor.beans.dieuchinhtonkho.imp;

import geso.traphaco.distributor.db.sql.*;
import geso.traphaco.distributor.util.Utility;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


import javax.servlet.http.HttpServletRequest;

import geso.traphaco.distributor.beans.dieuchinhtonkho.IDieuchinhtonkho;

public class Dieuchinhtonkho implements IDieuchinhtonkho, Serializable
{
	private static final long serialVersionUID = -9217977546733610214L;
	NumberFormat formatter = new DecimalFormat("#,###,###.##");
	String id;
	String nppId;
	String nppTen;
	String userId;
	String userTen;
	String ngaydc;

	String nccId;
	ResultSet ncc;
	String dvkdId;
	ResultSet dvkd;
	String kbhId;
	ResultSet kbh;
	String khoId;
	ResultSet kho;

	String gtdc;
	
	String lydodc;

	String msg;
	String[] spId;
	String[] masp;
	String[] tensp;
	String[] tonkho;
	String[] dv;
	String[] dc;
	String[] soluongthung;
	String[] tkm;
	String[] giamua;
	String[] ttien;
	String[] solo;
	String[] tonthung;
	String[] tonle;
	String[] quycach;
	String[] soluongle;
	String[] dongiathung;
	int size;
	dbutils db;
	
	public Dieuchinhtonkho(String[] param)
	{
		this.db = new dbutils();
		this.id = param[0];
		this.nppId = param[1];
		this.nppTen = param[2];
		this.userId = param[3];
		this.userTen = param[4];
		this.ngaydc = param[5];
		this.nccId = param[6];
		this.dvkdId = param[7];
		this.kbhId = param[8];
		this.gtdc = param[9];
		
		this.lydodc = "";
	}
	
	public Dieuchinhtonkho()
	{
		this.db = new dbutils();
		this.id = "";
		this.nppId = "";
		this.nppTen = "";
		this.userId = "";
		this.userTen = "";
		this.nccId = "";
		this.dvkdId = "";
		this.kbhId = "";
		this.khoId = "";
		this.gtdc = "";
		this.msg = "";
		this.ngaydc = getDateTime();
		
		this.lydodc = "";
	}
	
	public String getId()
	{
		return this.id;
	}
	
	public void setId(String id)
	{
		this.id = id;
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

	public String getUserId()
	{
		return this.userId;
	}
	
	public void setUserId(String userId)
	{
		this.userId = userId;
		geso.traphaco.distributor.util.Utility util=new geso.traphaco.distributor.util.Utility();
		this.nppId = util.getIdNhapp(this.userId);
		this.nppTen = util.getTenNhaPP();
		//this.dangnhap = util.getDangNhap();
		//this.sitecode=util.getSitecode();
	}
	
	public String getUserTen()
	{
		return this.userTen;
	}
	
	public void setUserTen(String userTen)
	{
		this.userTen = userTen;
	}
	
	public String getNgaydc()
	{
		return this.ngaydc;
	}
	
	public void setNgaydc(String ngaydc)
	{
		this.ngaydc = ngaydc;
	}
	
	public String getNccId()
	{
		return this.nccId;
	}
	
	public void setNccId(String nccId)
	{
		this.nccId = nccId;
	}

	public ResultSet getNcc()
	{
		return this.ncc;
	}
	
	public void setNcc(ResultSet ncc)
	{
		this.ncc = ncc;
	}

	public String getDvkdId()
	{
		return this.dvkdId;
	}
	
	public void setDvkdId(String dvkdId)
	{
		this.dvkdId = dvkdId;
	}

	public ResultSet getDvkd()
	{
		return this.dvkd;
	}
	
	public void setDvkd(ResultSet dvkd)
	{
		this.dvkd = dvkd;
	}

	
	public String getKbhId()
	{
		return this.kbhId;
	}
	
	public void setKbhId(String kbhId)
	{
		this.kbhId = kbhId;
	}

	public ResultSet getKbh()
	{
		return this.kbh;
	}
	
	public void setKbh(ResultSet kbh)
	{
		this.kbh = kbh;
	}

	public String getKhoId()
	{
		return this.khoId;
	}
	
	public void setKhoId(String khoId)
	{
		this.khoId = khoId;
	}

	public ResultSet getKho()
	{
		return this.kho;
	}
	
	public void setKho(ResultSet kho)
	{
		this.kho = kho;
	}

	public String getGtdc()
	{
		return this.gtdc;
	}
	
	public void setGtdc(String gtdc)
	{
		this.gtdc = gtdc;
	}
	
	public int getSize()
	{
		return this.size;
	}
	
	public void setSize(int size)
	{
		this.size = size;
	}

	public String[] getSpId()
	{
		return this.spId;
	}
	
	public void setSpId(String[] spId)
	{
		this.spId = spId;
	}

	public String[] getMasp()
	{
		return this.masp;
	}
	
	public void setMasp(String[] masp)
	{
		this.masp = masp;
	}
	
	public String[] getTensp()
	{
		return this.tensp;
	}
	
	public void setTenSp(String[] tensp)
	{
		this.tensp = tensp;
	}
	
	public String[] getTonkho()
	{
		return this.tonkho;
	}
	
	public void setTonkho(String[] tonkho)
	{
		this.tonkho = tonkho;
	}
	
	public String[] getDonvi()
	{
		return this.dv;
	}
	
	public void setDonvi(String[] dv)
	{
		this.dv = dv;
	}

	public String[] getDc()
	{
		return this.dc;
	}
	
	public void setDc(String[] dc)
	{
		this.dc = dc;
	}
	
	public String[] getGiamua()
	{
		return this.giamua;
	}
	
	public void setGiamua(String[] giamua)
	{
		this.giamua = giamua;
	}

	public String[] getTtien()
	{
		return this.ttien;
	}
	
	public void setTtien(String[] ttien)
	{
		this.ttien = ttien;
	}
	
	public String getMessage()
	{
		return this.msg;
	}
	
	public void setMessage(String msg)
	{
		this.msg = msg;
	}

	public void init0(){
		getNppInfo();
		geso.traphaco.center.util.Utility utilCenter = new geso.traphaco.center.util.Utility();
		String query = "select e.pk_seq as dvkdId, e.donvikinhdoanh as dvkd from nhaphanphoi a, nhacungcap b, nhacungcap_dvkd c, nhapp_nhacc_donvikd d, donvikinhdoanh e where a.pk_seq = '"+ this.nppId + "' and a.pk_seq = d.npp_fk and b.pk_seq = c.ncc_fk and c.pk_seq = d.ncc_dvkd_fk and c.dvkd_fk = e.pk_seq";
		this.dvkd = this.db.get(query);
		
		query = "select c.pk_seq as kbhId, c.diengiai as kbh from nhaphanphoi a, nhapp_kbh b, kenhbanhang c where a.pk_seq = '"+ this.nppId +"' and a.pk_seq = b.npp_fk and b.kbh_fk = c.pk_seq";
		this.kbh = this.db.get(query);
	
		query = "select distinct a.pk_seq as khoId, a.ten as ten,a.diengiai from kho a, nhapp_kho b where a.pk_seq = b.kho_fk and b.npp_fk = '"+ this.nppId + "' and a.trangthai ='1' and a.pk_seq in "+utilCenter.quyen_kho(this.userId) ;
		this.kho = this.db.get(query);
	}
	
	public void initNew()
	{
		
	
		init0();
		
		System.out.println("[ngaydc]"+ngaydc+"[kbhId]"+kbhId+"[dvkdId]"+dvkdId);
		
		if( (this.kbhId!=null&&this.dvkdId!=null) && this.kbhId.trim().length() > 0 && this.dvkdId.trim().length() > 0)
		{
			ResultSet rs;
			try
			{
				String query = "select count(*) as num from  nhapp_kho_chitiet a where  a.npp_fk = '" + this.nppId + "' " +
					" and a.kbh_fk = '" + this.kbhId + "' and a.kho_fk = '" + this.khoId + "' ";
				System.out.println("query init new "+query);
					rs = this.db.get(query);
					int size=0;
					rs.next();
					size = rs.getInt("num");
					rs.close();
					this.size = size;
					this.spId = new String[size];
					this.masp = new String[size];
					this.tensp = new String[size];
					this.tonkho = new String[size];
					this.dv = new String[size];
					this.dc  = new String[size];
					this.giamua = new String[size];
					this.ttien = new String[size];
					this.solo= new String[size];
					this.soluongthung=new String[size];
					this.soluongle=new String[size];
					this.tonthung=new String[size];
					this.tonle=new String[size];
					this.quycach=new String[size];
					this.dongiathung=new String[size];
					this.spNgayHetHan=new String[size];
					query = 
					"select a.sanpham_fk as spId, b.ma, b.ten,a.available as tonkho, " +
					" ( 	select top(1) GIAMUANPP  " +
					"		from BGMUANPP_SANPHAM where SANPHAM_FK = b.pk_seq  " +
					"			and BGMUANPP_FK in ( select top(1) bg.PK_SEQ " +
					"								from BANGGIAMUANPP bg inner join BANGGIAMUANPP_NPP bgnpp on bgnpp.BANGGIAMUANPP_FK=bg.PK_SEQ " +
					"		where bg.TUNGAY  <= '" + this.ngaydc + "' and bgnpp.NPP_FK = '" + this.nppId + "' and bg.KENH_FK = '" + this.kbhId + "' and bg.DVKD_FK = '" + this.dvkdId + "' order by bg.tungay desc ) )	as giamua, " +
					"   c.donvi,a.solo,(d.soluong1/d.soluong2) as quycach,a.NgayHetHan " +
					"from nhapp_kho_chitiet a,  " +
					" sanpham b, donvidoluong c,quycach d  " +
					"where a.sanpham_fk = b.pk_seq and b.dvkd_fk = '" + this.dvkdId + "' " +
					" and c.pk_seq = b.dvdl_fk and a.npp_fk = '" + this.nppId + "' " +
					" and a.kbh_fk = '" + this.kbhId + "' and a.kho_fk = '" + this.khoId + "' " +
					" and a.sanpham_fk=d.sanpham_fk and d.dvdl2_fk=100018 and d.dvdl1_fk=b.dvdl_fk " +
					"order by b.ma";				
					rs = this.db.get(query);
					System.out.print("[Init New]"+query);
					int i = 0;
					if(rs!=null)
					{
						while(rs.next())
						{
							this.spId[i] = rs.getString("spId");
							this.masp[i] = rs.getString("ma");
							this.spNgayHetHan[i] = rs.getString("NgayHetHan");
							
							double tonkho=rs.getDouble("tonkho");
							int quycach=rs.getInt("quycach")==0?1:rs.getInt("quycach");
						/*	int thung = (int) tonkho/quycach;
							int le = (int) (tonkho  % quycach);*/
							int thung = (int) tonkho;
							int le = (int) (tonkho );
							System.out.println(this.masp[i]+"----"+"ton kho __"+tonkho +"quy cach "+quycach);
							
							this.tonthung[i] =formatter.format( thung/quycach  );
							this.tonle[i]=formatter.format( le  );
							this.tensp[i]= rs.getString("ten");
							this.tonkho[i] = formatter.format(rs.getDouble("tonkho"));
							this.dv[i] = rs.getString("donvi");
							this.dc[i]="";
							this.giamua[i] =formatter.format(rs.getDouble("giamua"));
							this.ttien[i] = "0";
							this.solo[i]=rs.getString("solo");
							this.quycach[i]=rs.getString("quycach");
							this.soluongle[i]="";
							this.soluongthung[i]="";
							this.dongiathung[i]=formatter.format(rs.getDouble("giamua")*quycach);
							i++;
						}
					}
					this.size=i;
			}
			catch(Exception e){e.printStackTrace();}
		}
				
	}

	public void initUpdate(){
		init0();
		ResultSet rs = null;
		try
		{
			String query;	
			query = "select ngaydc, npp_fk, dvkd_fk, kbh_fk, tongtien,lydodc, kho_fk from dieuchinhtonkho where pk_seq='" + this.id + "' and trangthai='0'";
			rs = this.db.get(query);
			System.out.println(query);
			
			rs.next();
			this.ngaydc = rs.getString("ngaydc");
			this.dvkdId = rs.getString("dvkd_fk");
			this.nppId	= rs.getString("npp_fk");
			this.kbhId	= rs.getString("kbh_fk");
			this.khoId	= rs.getString("kho_fk");
			this.gtdc 	= rs.getString("tongtien");
			this.lydodc=rs.getString("lydodc");
			rs.close();
			//query = "select count(*) as num from  sanpham ";
			  query = "select count(*) as num from  nhapp_kho_chitiet a where  a.npp_fk = '" + this.nppId + "' " +
			" and a.kbh_fk = '" + this.kbhId + "' and a.kho_fk = '" + this.khoId + "' ";
			  
			//System.out.println("get count : "+query);
			rs = this.db.get(query);
			int size=0;
			rs.next();
			size = rs.getInt("num");
			rs.close();
			this.size = size;
			this.spId = new String[size];
			this.masp = new String[size];
			this.tensp = new String[size];
			this.tonkho = new String[size];
			this.dv = new String[size];
			this.dc  = new String[size];
			this.giamua = new String[size];
			this.ttien = new String[size];
			this.solo= new String[size];
			this.soluongthung=new String[size];
			this.soluongle=new String[size];
			this.tonthung=new String[size];
			this.tonle=new String[size];
			this.quycach=new String[size];
			this.dongiathung=new String[size];
				
			this.spNgayHetHan=new String[size];
			
			query = 
			"select  a.sanpham_fk as spId, b.ma, b.ten, a.AVAILABLE as tonkho,a.soluong,isnull(e.tonmoi,0) as TonMoi,e.dieuchinh, "+
			" ( 	select top(1) GIAMUANPP  " +
			"		from BGMUANPP_SANPHAM where SANPHAM_FK = b.pk_seq  " +
			"			and BGMUANPP_FK in ( select top(1) bg.PK_SEQ " +
			"								from BANGGIAMUANPP bg inner join BANGGIAMUANPP_NPP bgnpp on bgnpp.BANGGIAMUANPP_FK=bg.PK_SEQ " +
			"		where bg.TUNGAY  <= '" + this.ngaydc + "' and bgnpp.NPP_FK = '" + this.nppId + "' and bg.KENH_FK = '" + this.kbhId + "' and bg.DVKD_FK = '" + this.dvkdId + "' order by bg.tungay desc ) )	as giamua, " +
			 "c.donvi,a.solo,(d.soluong1/d.soluong2) as qc,isnull(e.thanhtien,0) as ThanhTien,a.NgayHetHan "+
			" from  "+
			" ( "+
			"	select * "+
			" 	from NHAPP_KHO_CHITIET a "+
			" 	where  a.npp_fk = '"+this.nppId+"'  "+
			"		and a.kbh_fk = '"+this.kbhId+"'  and a.kho_fk = '"+this.khoId+"' "+
			" ) as a "+
			" left join SANPHAM b on a.SANPHAM_FK=b.PK_SEQ  "+
			" left join DONVIDOLUONG c on c.PK_SEQ=b.DVDL_FK "+
			" left join QUYCACH d on d.SANPHAM_FK=a.SANPHAM_FK and d.DVDL2_FK=100018 AND D.DVDL1_fK=b.dvdl_Fk "+
			" left join DIEUCHINHTONKHO_SP e on e.SANPHAM_FK=a.SANPHAM_FK and e.SOLO=a.SOLO and e.NgayHetHan=a.NgayHetHan  and e.DIEUCHINHTONKHO_FK='"+this.id+"' " ;				
			System.out.print("Get Du Lieu Dieu Chinh : "+query);
			rs = this.db.get(query);
			int i = 0;
			while(rs.next())
			{
				this.spId[i] = rs.getString("spId");
				this.masp[i] = rs.getString("ma");
				this.tensp[i]= rs.getString("ten");
				this.tonkho[i] =  formatter.format (rs.getDouble("tonkho"));
				this.dv[i] = rs.getString("donvi");
				this.spNgayHetHan[i] = rs.getString("NgayHetHan");
				
				this.giamua[i] = formatter.format (rs.getDouble("giamua"));
				this.solo[i]=rs.getString("solo");
				double ton=rs.getDouble("tonkho");
				int quycach=rs.getInt("qc")==0?1:rs.getInt("qc");
				/*int thung = (int) ton/quycach;
				int le = (int) (ton  % quycach);*/
				
				
				int thung = (int) ton;
				int le = (int) (ton);
				
				this.tonthung[i] =formatter.format( thung/quycach  );
				this.tonle[i]=formatter.format( le  );
				this.dc[i]=rs.getString("dieuchinh")==null?"":rs.getString("dieuchinh");
				
				this.quycach[i]=rs.getString("qc");
				this.soluongle[i]="";
				this.soluongthung[i]="";
				this.dongiathung[i]=formatter.format( rs.getDouble("giamua")*quycach  );
				this.ttien[i] = formatter.format( rs.getDouble("thanhtien")  );
				if(dc[i]!="")
				{
					if(Integer.parseInt(dc[i])<0)
					{
						double tonmoi=rs.getDouble("tonmoi");
						float thung_quy_doi = (float) ((tonmoi-Integer.parseInt(dc[i]))/quycach);
						this.tonle[i]=formatter.format( le- Integer.parseInt(dc[i])  );
						this.tonthung[i]=formatter.format( thung_quy_doi  );
						this.tonkho[i] =  formatter.format (rs.getDouble("soluong"));
						this.soluongle[i]=formatter.format( tonmoi   );
						this.soluongthung[i]=formatter.format( thung_quy_doi  );
						System.out.println(this.tonle[i] +"___"+this.tonthung[i]+"_________"+this.tonkho[i]);
					}
					System.out.println("____"+dc[i]);
					if(Integer.parseInt(dc[i])>0)
					{
							double tonmoi=rs.getDouble("tonmoi");
							float thung_quy_doi = (float) (tonmoi + Integer.parseInt(dc[i]))/quycach;
							this.soluongle[i]=formatter.format( tonmoi + Integer.parseInt(dc[i])  );
							this.soluongthung[i]=formatter.format( thung_quy_doi  );
					}
				}
				i++;
			}			
			this.size=i;
		}catch(Exception e){
			e.printStackTrace();}
		finally{if(rs != null)
			try {
				rs.close();
			} catch(Exception e) 
			{
				e.printStackTrace();
			}}
	}

	public void initDisplay()
	{
		ResultSet rs = null;
		try
		{
			String query = "select ngaydc, npp_fk, dvkd_fk, kbh_fk, tongtien,lydodc, kho_fk,trangthai from dieuchinhtonkho where pk_seq='" + this.id + "' ";
			rs = this.db.get(query);
			rs.next();
			this.ngaydc = rs.getString("ngaydc");
			this.dvkdId = rs.getString("dvkd_fk");
			this.nppId	= rs.getString("npp_fk");
			this.kbhId	= rs.getString("kbh_fk");
			this.khoId	= rs.getString("kho_fk");
			this.gtdc 	= rs.getString("tongtien");
			this.lydodc=rs.getString("lydodc");
			rs.close();
			init0();
				 query = "select count(*) as num from  nhapp_kho_chitiet a where  a.npp_fk = '" + this.nppId + "' " +
			" and a.kbh_fk = '" + this.kbhId + "' and a.kho_fk = '" + this.khoId + "' ";
			rs = this.db.get(query);
			int size=0;
			rs.next();
			size = rs.getInt("num");
			rs.close();
			this.size = size;
			this.spId = new String[size];
			this.masp = new String[size];
			this.tensp = new String[size];
			this.tonkho = new String[size];
			this.dv = new String[size];
			this.dc  = new String[size];
			this.giamua = new String[size];
			this.ttien = new String[size];
			this.solo= new String[size];
			this.soluongthung=new String[size];
			this.soluongle=new String[size];
			this.tonthung=new String[size];
			this.tonle=new String[size];
			this.quycach=new String[size];
			this.dongiathung=new String[size];
			this.spNgayHetHan=new String[size];
				
			query = 
			"select  a.sanpham_fk as spId, b.ma, b.ten, a.soluong as tonkho,isnull(e.tonmoi,0) as TonMoi,e.dieuchinh, "+
			 "a.giamua, c.donvi,a.solo,(d.soluong1/d.soluong2) as qc,isnull(e.thanhtien,0) as ThanhTien,a.NgayHetHan "+
			" from  "+
			" ( "+
			"	select * "+
			" 	from NHAPP_KHO_CHITIET a "+
			" 	where  a.npp_fk = '"+this.nppId+"'  "+
			"		and a.kbh_fk = '"+this.kbhId+"'  and a.kho_fk = '"+this.khoId+"' "+
			" ) as a "+
			" inner join SANPHAM b on a.SANPHAM_FK=b.PK_SEQ  "+
			" left join DONVIDOLUONG c on c.PK_SEQ=b.DVDL_FK "+
			" left join QUYCACH d on d.SANPHAM_FK=a.SANPHAM_FK and d.DVDL2_FK=100018 "+
			" inner join DIEUCHINHTONKHO_SP e on e.SANPHAM_FK=a.SANPHAM_FK and e.SOLO=a.SOLO  and e.DIEUCHINHTONKHO_FK='"+this.id+"' " ;				
			System.out.print(query);
			rs = this.db.get(query);
			int i = 0;
			while(rs.next())
			{
				this.spId[i] = rs.getString("spId");
				this.masp[i] = rs.getString("ma");
				this.tensp[i]= rs.getString("ten");
				this.tonkho[i] =  formatter.format (rs.getDouble("tonkho"));
				this.dv[i] = rs.getString("donvi");
				this.spNgayHetHan[i] = rs.getString("NgayHetHan");
				this.giamua[i] = formatter.format (rs.getDouble("giamua"));
				this.solo[i]=rs.getString("solo");
				double ton=rs.getDouble("tonkho");
				int quycach=rs.getInt("qc");
				/*int thung = (int) ton/quycach;
				int le = (int) (ton  % quycach);*/
				
				
				int thung = (int) ton;
				int le = (int) (ton );
				
				this.tonthung[i] =formatter.format( thung/quycach  );
				this.tonle[i]=formatter.format( le  );
				this.dc[i]=rs.getString("dieuchinh")==null?"":rs.getString("dieuchinh");
				this.quycach[i]=rs.getString("qc");
				this.soluongle[i]="";
				this.soluongthung[i]="";
				this.dongiathung[i]=formatter.format( rs.getDouble("giamua")*quycach  );
				this.ttien[i] = formatter.format( rs.getDouble("thanhtien")  );
				if(dc[i]!="")
				{
					if(Integer.parseInt(dc[i])>0){
					double tonmoi=rs.getDouble("tonmoi");
					float thung_quy_doi = (float) tonmoi/quycach;
					this.soluongle[i]=formatter.format( tonmoi  );
					this.soluongthung[i]=formatter.format( thung_quy_doi  );
					}
					if(Integer.parseInt(dc[i])<0){
						double tonmoi=rs.getDouble("tonmoi");
						float thung_quy_doi = (float) (tonmoi - Integer.parseInt(dc[i]))/quycach;
						this.soluongle[i]=formatter.format( tonmoi - Integer.parseInt(dc[i])  );
						this.soluongthung[i]=formatter.format( thung_quy_doi  );
					}
				}
				i++;
			}			
			this.size=i;
		}catch(Exception e){e.printStackTrace();}
		finally{if(rs != null)
			try {
				rs.close();
			} catch(Exception e) 
			{
				e.printStackTrace();
			}}
	}

	public String CheckNghiepVu()
	{
		int count=0;
		String query="select count(*) as count from donhang where trangthai=0 and npp_fk="+this.nppId+"";
		ResultSet rs=this.db.get(query);
		try
		{
			while(rs.next())
			{
				count=rs.getInt(1);
			}rs.close();
			if(count>0)
			{
				return "Còn "+count+  " đơn hàng chưa chốt.Bạn phải chốt đơn hàng trước khi làm Kiểm kho ";
			} 
			query="select count(*) from dontrahang where trangthai=0  and npp_fk="+this.nppId+" ";
			rs=this.db.get(query);
			while(rs.next())
			{
				count=rs.getInt(1);
			}rs.close();
			if(count>0)
			{
				return "Còn "+count+  " đơn trả hàng nhà cung cấp chưa chốt.Bạn phải chốt đơn hàng trước khi làm Kiểm kho ";
			}
			query="select count(*) from donhangtrave where trangthai=0 and npp_fk="+this.nppId+" ";
			rs=this.db.get(query);
			while(rs.next())
			{
				count=rs.getInt(1);
			}rs.close();
			if(count>0)
			{
				return "Còn "+count+  " đơn trả hàng chưa chốt.Bạn phải chốt đơn hàng trước khi làm Kiểm kho ";
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return "";
	}
	public boolean CreateDctk(HttpServletRequest request) throws SQLException
	{
		getNppInfo();
		
		ResultSet rs=null;
		try
		{
			if(this.dvkdId.equals(""))
			{
				this.msg="Vui lòng chọn đơn vị kinh doanh";
				return false;
			}
			if(this.kbhId.equals(""))
			{
				this.msg="Vui lòng chọn kênh bán hàng";
				return false;
			}
			if(this.khoId.equals(""))
			{
				this.msg="Vui lòng chọn kho";
				return false;
			}
			
			
			
			String query ="select count(pk_seq) as num from dieuchinhtonkho where npp_fk='" + this.nppId + "' and dvkd_fk = '" + this.dvkdId + "' and kbh_fk = '" + this.kbhId + "' and trangthai='0' and kho_fk='"+this.khoId+"'";
			System.out.println("Lay dieu chinh ton kho :"+query);
			rs = this.db.get(query);
			rs.next();
			if (rs.getString("num").equals("0"))
			{
				rs.close();
				this.db.getConnection().setAutoCommit(false);
			
				this.ngaydc = request.getParameter("ngaydc");	
				if (this.ngaydc == null)
					this.ngaydc = this.getDateTime();
				
				this.dvkdId = request.getParameter("dvkdId");
				this.kbhId 	= request.getParameter("kbhId");
				this.khoId  = request.getParameter("khoId");
				this.gtdc 	= request.getParameter("ttien").replace(",", "");
			
				String npp="select tructhuoc_fk from nhaphanphoi where pk_seq="+this.nppId+"";
				query = " insert into dieuchinhtonkho(NGAYDC,NGUOITAO,NGUOISUA,TRANGTHAI,NPP_FK,DVKD_FK,KBH_FK,TONGTIEN,KHO_FK,NGUOIDUYET,LYDODC,tructhuoc_fk)" +
						" values('" + this.ngaydc + "', '" + this.userId + "','" + this.userId + "','0', '" + this.nppId + "','" + this.dvkdId + "','" + this.kbhId + "', '" + this.gtdc + "','" + this.khoId + "','0', N'" + this.lydodc + "',("+npp+"))";
				this.msg= query;
				System.out.println("dieuchinhtonkho ___"+query);
				if(!this.db.update(query))
				{
					this.msg = "Khong The Insert,Vui Long Kiem Tra Lai Du Lieu.Loi :"+query;
					this.db.update("rollback");
					return false;
				}
				query = "select IDENT_CURRENT('dieuchinhtonkho') as dctkId";
				rs = this.db.get(query);
				rs.next();
				this.id = rs.getString("dctkId");
				System.out.println("id dctk"+this.id);
				String sql_check="";
				String ketqua="";
				int flag=0;
				for (int i=0;i<size;i++)
				{
					if(dc[i].length()>0)
					{
						int dieuchinh_= Integer.parseInt(this.dc[i]);
						if(dieuchinh_<0)
						{
							System.out.println("dieu chinh "+dieuchinh_);
							dieuchinh_ =dieuchinh_*(-1);
						query=
								" select SoLuong,Available,Booked from nhapp_kho where  kho_fk="+this.khoId+" and npp_fk="+this.nppId +" and sanpham_fk="+this.spId[i] +" and kbh_fk="+this.kbhId ;
								rs =db.get(query);
								double Available =0;
								while(rs.next())
								{
									Available =rs.getDouble("Available");
									if(dieuchinh_>Available)
									{
										ketqua+=masp[i]+" Tồn hiện tại "+ Available;
										flag++;
									}
								
								}
						}
					}
				}
				System.out.println("flag la "+ flag);
				if(flag>0)
				{
					this.msg =" vui lòng điều chỉnh các sản phẩm sau  "+ketqua+" ";
					this.db.update("rollback");
					return false;
				}
				for (int i = 0; i < size ; i++)
				{
					if (dc[i].length()>0)
					{
						if (Math.abs(Double.parseDouble(this.dc[i].replaceAll(",", ""))) > 0 )
						{
							int dieuchinh_=0;
							dieuchinh_= Integer.parseInt(this.dc[i]);
							String command ="";
							if(dieuchinh_ >0)
							{
							 command =  
											"INSERT INTO dieuchinhtonkho_sp(DIEUCHINHTONKHO_FK,SANPHAM_FK,SOLO,ngayhethan,dieuchinh,donvi,giamua,thanhtien,tonmoi)" +
											 " values('" + this.id + "','" +this.spId[i] + "','"+this.solo[i]+"','"+spNgayHetHan[i]+"','" + this.dc[i].replaceAll(",", "") + "',NULL,'" + this.giamua[i] + "','" + this.ttien[i]+ "','" + this.tonkho[i]+ "')";
							}
							if(dieuchinh_ <0)
							{
								 command =  
											"INSERT INTO dieuchinhtonkho_sp(DIEUCHINHTONKHO_FK,SANPHAM_FK,SOLO,ngayhethan,dieuchinh,donvi,giamua,thanhtien,tonmoi)" +
											 " values('" + this.id + "','" +this.spId[i] + "','"+this.solo[i]+"','"+spNgayHetHan[i]+"','" + this.dc[i].replaceAll(",", "") + "',NULL,'" + this.giamua[i] + "','" + this.ttien[i]+ "','" + (Integer.parseInt(this.tonkho[i]) + dieuchinh_)+ "')";
						
							}
								
							System.out.println("Insert DCTK: " + command);
							if(!this.db.update(command))
							{ 
								this.msg = command;
								this.db.update("rollback");
								return false;
							}
						
							if(dieuchinh_ <0)
							{
								dieuchinh_ =dieuchinh_*(-1);
								String sql=
									"update NHAPP_KHO_CHITIET set booked=booked +  "+dieuchinh_+"  , available=available - "+dieuchinh_+""+ 
									"  where kho_fk="+this.khoId+" and npp_fk="+this.nppId +" and sanpham_fk="+this.spId[i] +" and kbh_fk="+this.kbhId+" and solo='"+this.solo[i]+"' and NgayHetHan='"+spNgayHetHan[i]+"'";
								System.out.println("update kho chi tiet "+sql);
								if(this.db.updateReturnInt(sql)!=1)
								{
									this.msg = sql;
									this.db.update("rollback");
									return false;						
								}
								
								sql="update nhapp_kho  set booked=booked + "+dieuchinh_+" , available=available- "+dieuchinh_+"  where  kho_fk="+this.khoId+" and npp_fk="+this.nppId +" and sanpham_fk="+this.spId[i] +" and kbh_fk="+this.kbhId;
								if(this.db.updateReturnInt(sql)!=1)
								{
									this.msg = sql;
									this.db.update("rollback");
									return false;							
								}
								
							}
						}
					}
				}	
				Utility util = new Utility();
				msg= util.Check_Huy_NghiepVu_KhoaSo("dieuchinhtonkho", this.id, "ngaydc", db);
				if(msg.length()>0)
				{
				    return false;
				}
			    msg= util.Check_Kho_Tong_VS_KhoCT(this.nppId, db);
			    if(msg.length()>0)
			    {
			    	db.getConnection().rollback();
			    	return false;
			    }
			 
				this.db.getConnection().commit();
				this.db.getConnection().setAutoCommit(true);
			}else
			{
				this.msg = "Ban khong the tao moi dieu chinh ton kho khi co 1 dieu chinh dang cho duyet";
				return false;
			}
		}catch(Exception e)
		{
			this.msg = "Error :" +e.getMessage();
			this.db.update("rollback");
			e.printStackTrace();
			return false;
		}
		finally
		{
			if(rs != null)
			try 
			{
				rs.close();
			} catch(Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public boolean UpdateDctk(HttpServletRequest request) throws SQLException
	{

	
		getNppInfo();
		try
		{
			this.db.getConnection().setAutoCommit(false);
			this.ngaydc = request.getParameter("ngaydc");	
			this.gtdc 	= request.getParameter("ttien").replace(",", "");
			String query = "update dieuchinhtonkho set ngaydc='" + this.ngaydc + "', nguoisua = '" + this.userId + "', tongtien='" + this.gtdc + "', lydodc = N'" + this.lydodc + "' where pk_seq ='" + this.id + "'";

			if(!this.db.update(query)){
				this.msg = "KHong The Update,Vui Long Kiem Tra Lai Du Lieu.Loi :"+query;
				this.db.update("rollback");
				return false;
			}

			  query=		"   select dc.KBH_FK,dc.KHO_FK,dcsp.SANPHAM_FK,dc.NPP_FK,dcsp.DIEUCHINH,dcsp.solo,dcsp.ngayhethan from DIEUCHINHTONKHO_SP dcsp"+
								   "  inner join DIEUCHINHTONKHO dc"+
								   "   on dc.PK_SEQ=dcsp.DIEUCHINHTONKHO_FK "+
								   "   where   dcsp.DIEUCHINH<0 and dc.PK_SEQ="+this.id;
		ResultSet kq=db.get(query);
			while (kq.next()){
				
					int numkq=(-1)*kq.getInt("dieuchinh");
					query="update NHAPP_KHO_CHITIET set booked=booked -  "+numkq+"  , available=available+  "+numkq+"  "
							+ " where kho_fk="+kq.getString("kho_fk")+" and npp_fk="+kq.getString("npp_fk") +" and sanpham_fk="+kq.getString("sanpham_fk") +" and kbh_fk="+kq.getString("kbh_fk")+" and solo='"+kq.getString("solo")+"' and NgayHetHan='"+kq.getString("ngayhethan")+"'";
					System.out.println("dieuchinhtonkho ___"+query);
					if( this.db.updateReturnInt(query)!=1){
						this.msg = query;
						this.db.update("rollback");
						return false;						
					}
					query="update nhapp_kho  set booked=booked -  "+numkq+"  , available=available+ "+numkq+" "
							+ " where kho_fk="+kq.getString("kho_fk")+" and npp_fk="+kq.getString("npp_fk") +" and sanpham_fk="+kq.getString("sanpham_fk") +" and kbh_fk="+kq.getString("kbh_fk");
					if( this.db.updateReturnInt(query)!=1){
						this.msg = query;
						this.db.update("rollback");
						return false;						
					}
				
			}
			
			query = "delete from dieuchinhtonkho_sp where dieuchinhtonkho_fk='" + this.id + "'";
			if(	!this.db.update(query)){
				this.msg = "KHong The Update,Vui Long Kiem Tra Lai Du Lieu.Loi :"+query;
				this.db.update("rollback");
				return false;
			}
			
			String sql_check="";
			String ketqua="";
			int flag=0;
			for (int i=0;i<size;i++)
			{
				if(dc[i].length()>0)
				{
					int dieuchinh_= Integer.parseInt(this.dc[i]);
					if(dieuchinh_<0)
					{
						dieuchinh_ =dieuchinh_*(-1);
					query=
							" select SoLuong,Available,Booked from nhapp_kho where  kho_fk="+this.khoId+" and npp_fk="+this.nppId +" and sanpham_fk="+this.spId[i] +" and kbh_fk="+this.kbhId ;
							 ResultSet rs =db.get(query);
							double Available =0;
							while(rs.next())
							{
								Available =rs.getDouble("Available");
								if(dieuchinh_>Available)
								{
									ketqua+=masp[i]+"-";
									flag++;
								}
							
							}
					}
				}
			}
			if(flag>0)
			{
				this.msg =" vui lòng điều chỉnh các sản phẩm sau  "+ketqua+" ";
				this.db.update("rollback");
				return false;
			}
			
			for (int i = 0; i < size ; i++)
			{
				if (dc[i].length()>0)
				{
					System.out.println("_______dc__"+dc[i]);
					if (Math.abs(Double.parseDouble(this.dc[i])) > 0 )
					{
						String command ="";
						if(Integer.parseInt(dc[i])>0){
							command="select soluong from nhapp_kho_chitiet where kho_fk="+this.khoId+" and npp_fk="+this.nppId +" and sanpham_fk="+this.spId[i] +" and kbh_fk="+this.kbhId+" and solo='"+this.solo[i]+"' and ngayhethan='"+this.spNgayHetHan[i]+"'";
							//System.out.println("so luong "+this.);
							ResultSet rs=this.db.get(command);
							while(rs.next())
							{
								if(rs.getInt("soluong") == Integer.parseInt(soluongle[i]))
								{
									command="delete from dieuchinhtonkho_sp where dieuchinhtonkho_fk='" + this.id + "' and sanpham_fk="+this.spId[i]+" and solo='"+this.solo[i]+"'";
									if(!this.db.update(command))
									{
										this.msg = command;
										this.db.update("rollback");
										return false;
									}
								}
							}
						command =  
								"INSERT INTO dieuchinhtonkho_sp(DIEUCHINHTONKHO_FK,SANPHAM_FK,SOLO,ngayhethan,dieuchinh,donvi,giamua,thanhtien,tonmoi)" +
								 " values('" + this.id + "','" +this.spId[i] + "','"+this.solo[i]+"','"+spNgayHetHan[i]+"','" + this.dc[i].replaceAll(",", "") + "',NULL,'" + this.giamua[i] + "','" + this.ttien[i]+ "','" + this.tonkho[i]+ "')";
						
						//System.out.println("Command"+command);
						}
						if(Integer.parseInt(dc[i])<0)
						{
							
						 command =  
									"INSERT INTO dieuchinhtonkho_sp(DIEUCHINHTONKHO_FK,SANPHAM_FK,SOLO,ngayhethan,dieuchinh,donvi,giamua,thanhtien,tonmoi)" +
									 " values('" + this.id + "','" +this.spId[i] + "','"+this.solo[i]+"','"+spNgayHetHan[i]+"','" + this.dc[i].replaceAll(",", "") + "',NULL,'" + this.giamua[i] + "','" + this.ttien[i]+ "','" + (Integer.parseInt(this.tonkho[i]) + Integer.parseInt( dc[i]))+ "')";
						 		
						}
						if(!this.db.update(command))
						{
							this.msg = command;
							this.db.update("rollback");
							return false;
						}
						
						int dieuchinh_=0;
						dieuchinh_= Integer.parseInt(this.dc[i]);
						if(dieuchinh_ <0)
						{
							dieuchinh_ =dieuchinh_*(-1);
							String sql="update NHAPP_KHO_CHITIET set booked=booked +  "+dieuchinh_+"  , available=available - "+dieuchinh_+"  "
									+ "  where kho_fk="+this.khoId+" and npp_fk="+this.nppId +" and sanpham_fk="+this.spId[i] +" and kbh_fk="+this.kbhId+" and solo='"+this.solo[i]+"' and NgayHetHan='"+spNgayHetHan[i]+"'";
							//System.out.println("update kho chi tiet "+sql);
							if(this.db.updateReturnInt(sql)!=1){
								this.msg = command;
								this.db.update("rollback");
								return false;						
							}
							sql="update nhapp_kho  set booked=booked + "+dieuchinh_+" , available=available- "+dieuchinh_+" "
									+ "  where kho_fk="+this.khoId+" and npp_fk="+this.nppId +" and sanpham_fk="+this.spId[i] +" and kbh_fk="+this.kbhId;
							//System.out.println("update kho "+sql1);
							if(this.db.updateReturnInt(sql)!=1){
								this.msg = command;
								this.db.update("rollback");
								return false;							
							}
						}
					}
				}
			}			
			
			Utility util = new Utility();
			msg= util.Check_Huy_NghiepVu_KhoaSo("dieuchinhtonkho", this.id, "ngaydc", db);
			    if(msg.length()>0)
			  {
			    db.getConnection().rollback();
			    return false;
			  }
			
		    msg= util.Check_Kho_Tong_VS_KhoCT(this.nppId, db);
		    if(msg.length()>0)
		  {
		   db.getConnection().rollback();
		    return false;
		  }
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		}catch(Exception e){
			this.msg = "Error :" +e.toString();
			this.db.update("rollback");
			return false;
		}	
		
		return true;

	}

	private void getNppInfo(){
		
	
		//Phien ban moi
		geso.traphaco.distributor.util.Utility util=new geso.traphaco.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();
		/*String sql="select  convert(varchar(10),DATEADD(day,1, max(ngayks)),20)  as ngaydc from khoasongay where npp_fk ="+this.nppId;
		try{
			ResultSet rs=db.get(sql);
			if(rs.next()){
				this.ngaydc=rs.getString("ngaydc");
				
			}
			rs.close();
			
		}catch(Exception er){
			er.printStackTrace();
			this.msg=er.getMessage();
		}*/
	}

	private String convertDate2(String date){
		String newdate = "";
		int year = Integer.valueOf(date.substring(0, 4)).intValue();
		int month = Integer.valueOf(date.substring(5, 7)).intValue();		
		int day = Integer.valueOf(date.substring(8, 10)).intValue();
	    if (month == 1)
	    	newdate = "" + day + "-Jan-" + year;
	    if (month == 2)
	    	newdate = "" + day + "-Feb-" + year;
	    if (month == 3)
	    	newdate = "" + day + "-Mar-" + year;
	    if (month == 4)
	    	newdate = "" + day + "-Apri-" + year;
	    if (month == 5)
	    	newdate = "" + day + "-May-" + year;
	    if (month == 6)
	    	newdate = "" + day + "-Jun-" + year;
	    if (month == 7)
	    	newdate = "" + day + "-Jul-" + year;
	    if (month == 8)
	    	newdate = "" + day + "-Aug-" + year;
	    if (month == 9)
	    	newdate = "" + day + "-Sep-" + year;
	    if (month == 10)
	    	newdate = "" + day + "-Oct-" + year;
	    if (month == 11)
	    	newdate = "" + day + "-Nov-" + year;
	    if (month == 12)
	    	newdate = "" + day + "-Dec-" + year;

        return newdate;	

	}

	private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	public void DBclose(){

		
			try {
				if(this.dvkd != null)
					this.dvkd.close();
				if(this.kbh != null)
					this.kbh.close();
				if(this.kho != null)
					this.kho.close();
				if(this.ncc != null)
					this.ncc.close();
				if(!(this.db == null)){
					this.db.shutDown();
				}
				
			} catch(Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	private boolean CheckNgayDieuChinh()
	{
		Utility util = new Utility();
		String ngayksgn = util.ngaykhoaso(this.nppId);
		
		//System.out.print("\nNgay khoa so gan nhat la: " + this.ngaychungtu + "\n");
		
		if(ngayksgn.equals(""))
			return false;
		
		String[] ngayks = ngayksgn.split("-");
		String[] ngayct = this.ngaydc.split("-");
		
		//System.out.print("\nNgay chung tu la: " + this.ngaychungtu + "\n");
		
		Calendar c1 = Calendar.getInstance(); //new GregorianCalendar();
		Calendar c2 = Calendar.getInstance(); //new GregorianCalendar();

		//NGAY KHOA SO
		
		c1.set(Integer.parseInt(ngayks[0]), Integer.parseInt(ngayks[1]) - 1, Integer.parseInt(ngayks[2]));
	
		//NGAY thuc hien tra hang
		c2.set(Integer.parseInt(ngayct[0]), Integer.parseInt(ngayct[1]) - 1, Integer.parseInt(ngayct[2]));
	
		c1.add(Calendar.DATE, 1);//ngay tra don hang bang ngay khoa so them 1 ngay
		//phep tinh ngay nhan hang - ngay khoa so
			
		long songay = ( c1.getTime().getTime() - c2.getTime().getTime()) / (24 * 3600 * 1000);
		   
		if(songay < 0) //ngay chung tu khong duoc nho hon hoac bang ngay khoa so gan nhat 
		{
			this.msg = "Ngay Tra Don Hang Phai Lon Hon Ngay Khoa So Gan Nhat.";
			return false;
		}
		return true;
	}

	public String getLydodc() 
	{
		return this.lydodc;
	}

	public void setLydodc(String lydodc) 
	{
		this.lydodc = lydodc;
	}

	public String[] getSolo()
	{
		return solo;
	}

	public void setSolo(String[] solo)
	{
		this.solo = solo;
	}

	public String[] getTonthung()
	{
		return tonthung;
	}

	public void setTonthung(String[] tonthung)
	{
		this.tonthung = tonthung;
	}

	public String[] getTonle()
	{
		return tonle;
	}

	public void setTonle(String[] tonle)
	{
		this.tonle = tonle;
	}

	public String[] getSoluongthung()
	{
		return soluongthung;
	}

	public void setSoluongthung(String[] soluongthung)
	{
		this.soluongthung = soluongthung;
	}

	public String[] getQuycach()
	{
		return quycach;
	}

	public void setQuycach(String[] quycach)
	{
		this.quycach = quycach;
	}

	public String[] getSoluongle()
	{
		return soluongle;
	}

	public void setSoluongle(String[] soluongle)
	{
		this.soluongle = soluongle;
	}

	public String[] getDongiathung()
	{
		return dongiathung;
	}

	public void setDongiathung(String[] dongiathung)
	{
		this.dongiathung = dongiathung;
	}
	
	String[] spNgayHetHan;

	public String[] getSpNgayHetHan()
  {
  	return spNgayHetHan;
  }

	public void setSpNgayHetHan(String[] ngayHetHan)
  {
  	this.spNgayHetHan = ngayHetHan;
  }
	
}