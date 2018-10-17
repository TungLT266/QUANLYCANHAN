package geso.traphaco.distributor.beans.kiemkhotdv.imp;

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
import geso.traphaco.distributor.beans.kiemkhotdv.IKiemkhotdv;

public class Kiemkhotdv implements IKiemkhotdv, Serializable
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
	String[] book;
	String[] soluong;
	String[] Available;
	int size;
	dbutils db;
	
	public Kiemkhotdv(String[] param)
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
	
	public Kiemkhotdv()
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
		String query = "select ddkd_fk,b.ten from DAIDIENKINHDOANH_NPP a, daidienkinhdoanh b where a.ddkd_fk = b.pk_seq and a.npp_fk = '"+this.nppId+"' ";
		System.out.println("DVKD "+query);
		this.dvkd = this.db.get(query);
		
		query = "select c.pk_seq as kbhId, c.diengiai as kbh from nhaphanphoi a, nhapp_kbh b, kenhbanhang c where a.pk_seq = '"+ this.nppId +"' and a.pk_seq = b.npp_fk and b.kbh_fk = c.pk_seq";
		this.kbh = this.db.get(query);
	
		query = "select distinct a.pk_seq as Id,a.diengiai as ten from nhomkenh a where a.trangthai ='1'" ;
		System.out.println("nhom kenh "+query);
		this.kho = this.db.get(query);
	}
	
	public void initNew()
	{
		
	
		init0();
		
		System.out.println("[ngaydc]"+ngaydc+"[kbhId]"+kbhId+"[dvkdId]"+dvkdId);
		
		if( (this.dvkdId!=null) && this.dvkdId.trim().length() > 0 && this.khoId.length() > 0)
		{
			ResultSet rs;
			try
			{
				String query = 
						"select count(*) as num from (select a.npp_fk, a.sanpham_fk as spId, a.kho_fk,b.ma as ma,b.ten, a.nhomkenh_fk, a.solo, a.ngayhethan, a.soluong, a.booked, a.available, "+
									"   c.donvi "+
							 " from NHAPP_KHO_DDKD_CHITIET a, sanpham b,donvidoluong c  where npp_fk = '"+this.nppId+"' and DDKD_fk = "+this.dvkdId+" and nhomkenh_fk = '"+this.khoId+"' and a.sanpham_fk = b.pk_seq and c.pk_seq = b.dvdl_fk "+
							 "union " + 
							" select "+this.nppId+" npp_fk, SANPHAM.PK_SEQ as spId, '10000' kho_fk,ma,SANPHAM.ten, "+this.khoId+" nhomkenh_fk, 'NA' solo, '2030-12-31' ngayhethan, "+
							 " 0 soluong, 0 booked, 0 available, "+
							"   c.donvi  "+
							" from  SANPHAM,donvidoluong c where c.pk_seq = SANPHAM.dvdl_fk  and  SANPHAM.TRANGTHAI = '1' and SANPHAM.PK_SEQ not in "+
							" ( select sanpham_fk from NHAPP_KHO_DDKD_CHITIET where npp_fk = '"+this.nppId+"' and kho_fk = '100008' and ddkd_fk = '"+this.dvkdId+"'and nhomkenh_fk = '"+this.khoId+"') ) as dt ";
				System.out.println("in query "+query);
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
					this.soluong = new String[size];
					this.book = new String[size];
					this.Available = new String[size];
					query = 
							"select a.npp_fk, a.sanpham_fk as spId, a.kho_fk,b.ma as ma,b.ten, a.nhomkenh_fk, a.solo, a.ngayhethan, a.soluong, a.booked, a.available, "+
									"   c.donvi "+
							 " from NHAPP_KHO_DDKD_CHITIET a, sanpham b,donvidoluong c  where npp_fk = '"+this.nppId+"' and kho_fk = '100008' and ddkd_fk = '"+this.dvkdId+"'and nhomkenh_fk = '"+this.khoId+"' and a.sanpham_fk = b.pk_seq and c.pk_seq = b.dvdl_fk "+ 
							 "union " + 
							" select "+this.nppId+" npp_fk, SANPHAM.PK_SEQ as spId, '10000' kho_fk,ma,SANPHAM.ten, "+this.khoId+" nhomkenh_fk, 'NA' solo, '2030-12-31' ngayhethan, "+
							 " 0 soluong, 0 booked, 0 available, "+
							"   c.donvi  "+
							" from  SANPHAM,donvidoluong c where c.pk_seq = SANPHAM.dvdl_fk  and  SANPHAM.TRANGTHAI = '1' and SANPHAM.PK_SEQ not in "+
							" ( select sanpham_fk from NHAPP_KHO_DDKD_CHITIET where npp_fk = '"+this.nppId+"' and kho_fk = '100008'  and ddkd_fk = '"+this.dvkdId+"'and nhomkenh_fk = '"+this.khoId+"' )";
					rs = this.db.get(query);
					System.out.print("[Init New]"+query);
					int i = 0;
					if(rs!=null)
					{
						while(rs.next())
						{
							System.out.println("spid "+rs.getString("spId"));
							this.spId[i] = rs.getString("spId");
							this.masp[i] = rs.getString("ma");
							this.spNgayHetHan[i] = rs.getString("NgayHetHan");
							this.tensp[i] = rs.getString("ten");
							double tonkho=rs.getDouble("available");
							//int quycach=rs.getInt("quycach")==0?1:rs.getInt("quycach");
						/*	int thung = (int) tonkho/quycach;
							int le = (int) (tonkho  % quycach);*/
							int thung = (int) tonkho;
							int le = (int) (tonkho );
							
							System.out.println(this.masp[i]+"----"+"ton kho __"+tonkho +"quy cach "+quycach);
							this.Available[i] = rs.getString("available");
//							this.quycach[i] = rs.getString("donvi");
							//this.tonthung[i] =formatter.format( thung/quycach  );
							//this.tonle[i]=formatter.format( le  );
							//this.tensp[i]= rs.getString("ten");
							//this.tonkho[i] = formatter.format(rs.getDouble("tonkho"));
							this.dv[i] = rs.getString("donvi");
							this.dc[i]="";
//							this.giamua[i] =formatter.format(rs.getDouble("giamua"));
//							this.ttien[i] = "0";
							this.solo[i]=rs.getString("solo");
							//this.quycach[i]=rs.getString("quycach");
							this.book[i] = rs.getString("booked");
							this.soluong[i] = rs.getString("soluong");
//							this.dongiathung[i]=formatter.format(rs.getDouble("giamua")*quycach);
							i++;
						}
					}
					this.size=i;
			}
			catch(Exception e){
				e.printStackTrace();
				}
		}
				
	}

	public void initUpdate(){
		init0();
		ResultSet rs = null;
		try
		{
			String query;	
			query = "select ngaydc, npp_fk, ddkd_fk,nhomkenh_fk,lydodc, kho_fk from kiemkhotdv where pk_seq='" + this.id + "' and trangthai ='0'";
			rs = this.db.get(query);
			System.out.println(query);
			
			rs.next();
			this.ngaydc = rs.getString("ngaydc");
			this.dvkdId = rs.getString("ddkd_fk");
			this.nppId	= rs.getString("npp_fk");
			//this.kbhId	= rs.getString("kbh_fk");
			this.khoId	= rs.getString("nhomkenh_fk");
			//this.gtdc 	= rs.getString("tongtien");
			this.lydodc=rs.getString("lydodc");
			rs.close();
			//query = "select count(*) as num from  sanpham ";
			  query  = 
						"select count(*) as num from (select  a.sanpham_fk as spId, b.ma, b.ten, a.AVAILABLE as tonkho,a.soluong,isnull(e.tonmoi,0) as TonMoi,e.dieuchinh, "+
					 "c.donvi,a.solo,a.NgayHetHan "+
					" from  "+
					" ( "+
					"	select * "+
					" 	from NHAPP_KHO_DDKD_CHITIET a "+
					" 	where npp_fk = '"+this.nppId+"' and  a.ddkd_fk = '"+this.dvkdId+"' "+
					"		and a.nhomkenh_fk = '"+this.khoId+"'  and a.kho_fk = '100008' "+
					" ) as a "+
					" left join SANPHAM b on a.SANPHAM_FK=b.PK_SEQ  "+
					" left join DONVIDOLUONG c on c.PK_SEQ=b.DVDL_FK "+
					" inner join Kiemkhotdv_SP e on e.SANPHAM_FK=a.SANPHAM_FK and e.SOLO=a.SOLO and e.NgayHetHan=a.NgayHetHan  and e.Kiemkhotdv_FK='"+this.id+"' )as dt ";
			  
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
			this.soluong = new String[size];
			this.book = new String[size];
			this.Available = new String[size];	
			this.spNgayHetHan=new String[size];
			
			query = 
					"select  a.sanpham_fk as spId, b.ma, b.ten, a.AVAILABLE,a.soluong,a.booked,isnull(e.tonmoi,0) as TonMoi,e.dieuchinh, "+
					 "c.donvi,a.solo,a.NgayHetHan "+
					" from  "+
					" ( "+
					"	select * "+
					" 	from NHAPP_KHO_DDKD_CHITIET a "+
					" 	where npp_fk = '"+this.nppId+"' and  a.ddkd_fk = '"+this.dvkdId+"'  "+
					"		and a.nhomkenh_fk = '"+this.khoId+"'  and a.kho_fk = '100008' "+
					" ) as a "+
					" left join SANPHAM b on a.SANPHAM_FK=b.PK_SEQ  "+
					" left join DONVIDOLUONG c on c.PK_SEQ=b.DVDL_FK "+
					" inner join Kiemkhotdv_SP e on e.SANPHAM_FK=a.SANPHAM_FK and e.SOLO=a.SOLO and e.NgayHetHan=a.NgayHetHan  and e.Kiemkhotdv_FK='"+this.id+"' " ;
			System.out.print("Get Du Lieu Dieu Chinh : "+query);
			rs = this.db.get(query);
			int i = 0;
			while(rs.next())
			{
				System.out.println("spid "+rs.getString("spId"));
				this.spId[i] = rs.getString("spId");
				this.masp[i] = rs.getString("ma");
				this.spNgayHetHan[i] = rs.getString("NgayHetHan");
				this.tensp[i] = rs.getString("ten");
//				double tonkho=rs.getDouble("available");
				//int quycach=rs.getInt("quycach")==0?1:rs.getInt("quycach");
			/*	int thung = (int) tonkho/quycach;
				int le = (int) (tonkho  % quycach);*/
//				int thung = (int) tonkho;
//				int le = (int) (tonkho );
				
				System.out.println(this.masp[i]+"----"+"ton kho __"+tonkho +"quy cach "+quycach);
				this.Available[i] = rs.getString("AVAILABLE");
//				this.quycach[i] = rs.getString("donvi");
				//this.tonthung[i] =formatter.format( thung/quycach  );
				//this.tonle[i]=formatter.format( le  );
				//this.tensp[i]= rs.getString("ten");
				//this.tonkho[i] = formatter.format(rs.getDouble("tonkho"));
				this.dv[i] = rs.getString("donvi");
				this.dc[i]= rs.getString("dieuchinh")== null ?"":rs.getString("dieuchinh");
//				this.giamua[i] =formatter.format(rs.getDouble("giamua"));
//				this.ttien[i] = "0";
				this.solo[i]=rs.getString("solo");
				//this.quycach[i]=rs.getString("quycach");
				this.book[i] = rs.getString("booked");
				this.soluong[i] = rs.getString("soluong");
//				this.dongiathung[i]=formatter.format(rs.getDouble("giamua")*quycach);
			
				if(dc[i]!="")
				{
					if(Integer.parseInt(dc[i])<0)
					{
						double tonmoi=rs.getDouble("tonmoi");
			//			float thung_quy_doi = (float) ((tonmoi-Integer.parseInt(dc[i]))/quycach);
			//			this.tonle[i]=formatter.format( le- Integer.parseInt(dc[i])  );
			//			this.tonthung[i]=formatter.format( thung_quy_doi  );
				//		this.tonkho[i] =  formatter.format (rs.getDouble("soluong"));
				//		this.soluongle[i]=formatter.format( tonmoi   );
				//		this.soluongthung[i]=formatter.format( thung_quy_doi  );
				//		System.out.println(this.tonle[i] +"___"+this.tonthung[i]+"_________"+this.tonkho[i]);
					}
					System.out.println("____"+dc[i]);
					if(Integer.parseInt(dc[i])>0)
					{
							double tonmoi=rs.getDouble("tonmoi");
					//		float thung_quy_doi = (float) (tonmoi + Integer.parseInt(dc[i]))/quycach;
						//	this.soluongle[i]=formatter.format( tonmoi + Integer.parseInt(dc[i])  );
						//	this.soluongthung[i]=formatter.format( thung_quy_doi  );
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
		init0();
		ResultSet rs = null;
		try
		{
			String query;	
			query = "select ngaydc, npp_fk, ddkd_fk,nhomkenh_fk,lydodc, kho_fk from kiemkhotdv where pk_seq='" + this.id + "'";
			rs = this.db.get(query);
			System.out.println(query);
			
			rs.next();
			this.ngaydc = rs.getString("ngaydc");
			this.dvkdId = rs.getString("ddkd_fk");
			this.nppId	= rs.getString("npp_fk");
			//this.kbhId	= rs.getString("kbh_fk");
			this.khoId	= rs.getString("nhomkenh_fk");
			//this.gtdc 	= rs.getString("tongtien");
			this.lydodc=rs.getString("lydodc");
			rs.close();
			//query = "select count(*) as num from  sanpham ";
			  query  = 
						"select count(*) as num from (select  a.sanpham_fk as spId, b.ma, b.ten, a.AVAILABLE as tonkho,a.soluong,isnull(e.tonmoi,0) as TonMoi,e.dieuchinh, "+
					 "c.donvi,a.solo,a.NgayHetHan "+
					" from  "+
					" ( "+
					"	select * "+
					" 	from NHAPP_KHO_DDKD_CHITIET a "+
					" 	where  a.npp_fk = '"+this.nppId+"'  "+
					"		and a.nhomkenh_fk = '"+this.khoId+"'  and a.kho_fk = '100008'  and ddkd_fk = '"+this.dvkdId+"' "+ 
					" ) as a "+
					" left join SANPHAM b on a.SANPHAM_FK=b.PK_SEQ  "+
					" left join DONVIDOLUONG c on c.PK_SEQ=b.DVDL_FK "+
					" inner join Kiemkhotdv_SP e on e.SANPHAM_FK=a.SANPHAM_FK and e.SOLO=a.SOLO and e.NgayHetHan=a.NgayHetHan  and e.Kiemkhotdv_FK='"+this.id+"' )as dt ";
			  
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
			this.soluong = new String[size];
			this.book = new String[size];
			this.Available = new String[size];	
			this.spNgayHetHan=new String[size];
			
			query = 
					"select  a.sanpham_fk as spId, b.ma, b.ten, a.AVAILABLE,a.soluong,a.booked,isnull(e.tonmoi,0) as TonMoi,e.dieuchinh, "+
					 "c.donvi,a.solo,a.NgayHetHan "+
					" from  "+
					" ( "+
					"	select * "+
					" 	from NHAPP_KHO_DDKD_CHITIET a "+
					" 	where  a.npp_fk = '"+this.nppId+"'  "+
					"		and a.nhomkenh_fk = '"+this.khoId+"'  and a.kho_fk = '100008' and ddkd_fk = '"+this.dvkdId+"'"+
					" ) as a "+
					" left join SANPHAM b on a.SANPHAM_FK=b.PK_SEQ  "+
					" left join DONVIDOLUONG c on c.PK_SEQ=b.DVDL_FK "+
					" inner join Kiemkhotdv_SP e on e.SANPHAM_FK=a.SANPHAM_FK and e.SOLO=a.SOLO and e.NgayHetHan=a.NgayHetHan  and e.Kiemkhotdv_FK='"+this.id+"' " ;
			System.out.print("Get Du Lieu Dieu Chinh : "+query);
			rs = this.db.get(query);
			int i = 0;
			while(rs.next())
			{
				System.out.println("spid "+rs.getString("spId"));
				this.spId[i] = rs.getString("spId");
				this.masp[i] = rs.getString("ma");
				this.spNgayHetHan[i] = rs.getString("NgayHetHan");
				this.tensp[i] = rs.getString("ten");
//				double tonkho=rs.getDouble("available");
				//int quycach=rs.getInt("quycach")==0?1:rs.getInt("quycach");
			/*	int thung = (int) tonkho/quycach;
				int le = (int) (tonkho  % quycach);*/
//				int thung = (int) tonkho;
//				int le = (int) (tonkho );
				
				System.out.println(this.masp[i]+"----"+"ton kho __"+tonkho +"quy cach "+quycach);
				this.Available[i] = rs.getString("AVAILABLE");
//				this.quycach[i] = rs.getString("donvi");
				//this.tonthung[i] =formatter.format( thung/quycach  );
				//this.tonle[i]=formatter.format( le  );
				//this.tensp[i]= rs.getString("ten");
				//this.tonkho[i] = formatter.format(rs.getDouble("tonkho"));
				this.dv[i] = rs.getString("donvi");
				this.dc[i]= rs.getString("dieuchinh")== null ?"":rs.getString("dieuchinh");
//				this.giamua[i] =formatter.format(rs.getDouble("giamua"));
//				this.ttien[i] = "0";
				this.solo[i]=rs.getString("solo");
				//this.quycach[i]=rs.getString("quycach");
				this.book[i] = rs.getString("booked");
				this.soluong[i] = rs.getString("soluong");
//				this.dongiathung[i]=formatter.format(rs.getDouble("giamua")*quycach);
			
				if(dc[i]!="")
				{
					if(Integer.parseInt(dc[i])<0)
					{
						double tonmoi=rs.getDouble("tonmoi");
			//			float thung_quy_doi = (float) ((tonmoi-Integer.parseInt(dc[i]))/quycach);
			//			this.tonle[i]=formatter.format( le- Integer.parseInt(dc[i])  );
			//			this.tonthung[i]=formatter.format( thung_quy_doi  );
				//		this.tonkho[i] =  formatter.format (rs.getDouble("soluong"));
				//		this.soluongle[i]=formatter.format( tonmoi   );
				//		this.soluongthung[i]=formatter.format( thung_quy_doi  );
				//		System.out.println(this.tonle[i] +"___"+this.tonthung[i]+"_________"+this.tonkho[i]);
					}
					System.out.println("____"+dc[i]);
					if(Integer.parseInt(dc[i])>0)
					{
							double tonmoi=rs.getDouble("tonmoi");
					//		float thung_quy_doi = (float) (tonmoi + Integer.parseInt(dc[i]))/quycach;
						//	this.soluongle[i]=formatter.format( tonmoi + Integer.parseInt(dc[i])  );
						//	this.soluongthung[i]=formatter.format( thung_quy_doi  );
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
				this.msg="Vui lòng chọn trình dược viên";
				return false;
			}
//			if(this.kbhId.equals(""))
//			{
//				this.msg="Vui lòng chọn kênh bán hàng";
//				return false;
//			}
			if(this.khoId.equals(""))
			{
				this.msg="Vui lòng chọn nhóm kênh";
				return false;
			}
			
			
			
			String query ="select count(pk_seq) as num from KIEMKHOTDV where npp_fk='" + this.nppId + "' and ddkd_fk = '" + this.dvkdId + "' and nhomkenh_fk = '" + this.khoId + "' and trangthai='0' and kho_fk='100008'";
			System.out.println("Lay dieu chinh ton kho :"+query);
			rs = this.db.get(query);
			rs.next();
			if (rs.getString("num").equals("0"))
			{
				rs.close();
				System.out.println("XXXXXXXXXXXXXXXX");
				this.db.getConnection().setAutoCommit(false);
			
				this.ngaydc = request.getParameter("ngaydc");	
				if (this.ngaydc == null)
					this.ngaydc = this.getDateTime();
				// trình dược viên
				this.dvkdId = request.getParameter("dvkdId");
				//this.kbhId 	= request.getParameter("kbhId");
				// lấy nhóm kênh
				this.khoId  = request.getParameter("khoId");
//				this.gtdc 	= request.getParameter("ttien").replace(",", "");
			
				String npp="select tructhuoc_fk from nhaphanphoi where pk_seq="+this.nppId+"";
				query = " insert into kiemkhotdv(NGAYDC,NGUOITAO,NGUOISUA,TRANGTHAI,NPP_FK,DDKD_FK,Nhomkenh_FK,KHO_FK,NGUOIDUYET,LYDODC,tructhuoc_fk)" +
						" values('" + this.ngaydc + "', '" + this.userId + "','" + this.userId + "','0', '" + this.nppId + "','" + this.dvkdId + "','" + this.khoId+ "','100008','0', N'" + this.lydodc + "',("+npp+"))";
				this.msg= query;
				System.out.println("dieuchinhtonkho ___"+query);
				if(!this.db.update(query))
				{
					this.msg = "Khong The Insert,Vui Long Kiem Tra Lai Du Lieu.Loi :"+query;
					this.db.update("rollback");
					return false;
				}
				query = "select Scope_identity() as dctkId";
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
	
							System.out.println("dieu chinh "+dieuchinh_);
						
						query=
								" select SoLuong,Available,Booked from NHAPP_KHO_DDKD where  kho_fk='100008' and npp_fk="+this.nppId +" and sanpham_fk="+this.spId[i] +" and nhomkenh_fk="+this.khoId ;
								System.out.println("in kiem tra "+query);
								rs =db.get(query);
								double booked = 0;
								boolean kt = false;
								while(rs.next())
								{
									booked =rs.getDouble("booked");
									System.out.println("dieu chinh "+dc[i]);
									if(dieuchinh_ < booked)
									{
										ketqua+=masp[i]+" Số lượng đã book "+ booked;
										flag++;
									}
									kt = true;
								}
								System.out.println("kt "+kt);
								if(kt == false)
								{
									String sql = "Insert into NHAPP_KHO_DDKD(Kho_fk,Npp_fk,sanpham_fk,nhomkenh_fk,ddkd_fk,soluong,booked,available) "
												+ "values('100008',"+this.nppId +",'"+this.spId[i]+"','"+this.khoId+"','"+this.dvkdId+"','"+this.soluong[i]+"','"+this.book[i]+"','"+this.Available[i]+"') ";
									if(!this.db.update(sql))
									{ 
										this.msg = "Lỗi "+sql;
										this.db.update("rollback");
										return false;
									}
									
								 sql = "Insert into NHAPP_KHO_DDKD_chitiet(Kho_fk,Npp_fk,sanpham_fk,nhomkenh_fk,ddkd_fk,solo,ngayhethan,soluong,booked,available) "
											+ "values('100008',"+this.nppId +",'"+this.spId[i]+"','"+this.khoId+"','"+this.dvkdId+"','"+this.solo[i]+"','"+this.spNgayHetHan[i]+"','"+this.soluong[i]+"','"+this.book[i]+"','"+this.Available[i]+"') ";
								 if(!this.db.update(sql))
								 { 
									 this.msg = "Lỗi "+sql;
									 this.db.update("rollback");
									 return false;
								 }
									
									
									
								}
								
						
					}
				}
				System.out.println("flag la "+ flag);
				if(flag>0)
				{
					this.msg =" vui lòng điều chỉnh các sản phẩm sau để có thể lưu kiểm kho  "+ketqua+" ";
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
							dieuchinh_= Integer.parseInt(this.dc[i].replaceAll(",", ""));
							String command ="";
							if(dieuchinh_ >0)
						
							 command =  "INSERT INTO KIEMKHOTDV_SP(KIEMKHOTDV_FK,SANPHAM_FK,SOLO,ngayhethan,dieuchinh,donvi,tonmoi,tonhientai)" +
											 " values('" + this.id + "','" +this.spId[i] + "','"+this.solo[i]+"','"+spNgayHetHan[i]+"','" + this.dc[i].replaceAll(",", "") + "',N'"+this.dv[i]+"','" +  this.dc[i].replaceAll(",", "")+ "','" +  this.Available[i].replaceAll(",", "")+ "')";							
							System.out.println("Insert Kiemkho: " + command);
							if(!this.db.update(command))
							{ 
								this.msg = command;
								this.db.update("rollback");
								return false;
							}
						

						}
					}
				}	
			 
				this.db.getConnection().commit();
				this.db.getConnection().setAutoCommit(true);
			}else
			{
				this.msg = "Ban khong the tao moi kiem kho, do đang có kiểm kho đang chờ duyệt";
				return false;
			}
		}catch(Exception e)
		{
			this.msg = "Error :" +e.getMessage();
			e.printStackTrace();
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
			String query = "update kiemkhotdv set ngaydc='" + this.ngaydc + "', nguoisua = '" + this.userId + "', lydodc = N'" + this.lydodc + "' where pk_seq ='" + this.id + "'";

			if(!this.db.update(query)){
				this.msg = "KHong The Update,Vui Long Kiem Tra Lai Du Lieu.Loi :"+query;
				this.db.update("rollback");
				return false;
			}

			  query=		"   select dc.Nhomkenh_FK,dc.KHO_FK,dcsp.SANPHAM_FK,dc.NPP_FK,dcsp.DIEUCHINH,dcsp.solo,dcsp.ngayhethan from kiemkhotdv_SP dcsp"+
								   "  inner join kiemkhotdv dc"+
								   "   on dc.PK_SEQ=dcsp.kiemkhotdv_FK "+
								   "   where   dcsp.DIEUCHINH<0 and dc.PK_SEQ="+this.id;
			query = "delete from kiemkhotdv_sp where kiemkhotdv_fk='" + this.id + "'";
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
					
					query= " select SoLuong,Available,Booked from nhapp_kho_ddkd where  kho_fk='100008' and npp_fk="+this.nppId +" and sanpham_fk="+this.spId[i] +" and nhomkenh_fk="+this.khoId ;
							 ResultSet rs =db.get(query);
							double Booked =0;
							while(rs.next())
							{
								Booked =rs.getDouble("Booked");
								if(dieuchinh_ < Booked)
								{
									ketqua+=masp[i]+"-" +" điều chỉnh "+dc[i];
									flag++;
								}
							
							}
					}
				}
			}
			if(flag>0)
			{
				this.msg =" vui lòng điều chỉnh các sản phẩm sau do điều chỉnh nhỏ hơn book  "+ketqua+" ";
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
					
					
							
						String	command="delete from kiemkhotdv_sp where kiemkhotdv_fk='" + this.id + "' and sanpham_fk="+this.spId[i]+" and solo='"+this.solo[i]+"'";
									if(!this.db.update(command))
									{
										this.msg = command;
										this.db.update("rollback");
										return false;
									}
							
						command = "INSERT INTO kiemkhotdv_sp(kiemkhotdv_FK,SANPHAM_FK,SOLO,ngayhethan,dieuchinh,donvi,tonmoi,tonhientai)" +
								  " values('" + this.id + "','" +this.spId[i] + "','"+this.solo[i]+"','"+spNgayHetHan[i]+"','" + this.dc[i].replaceAll(",", "") + "',N'"+this.dv[i]+"','" +  this.dc[i].replaceAll(",", "")+ "','" +  this.Available[i].replaceAll(",", "")+ "')";
						
						if(!this.db.update(command))
						{
							this.msg = command;
							this.db.update("rollback");
							return false;
						}
						
						System.out.println("insert kiem kho "+command);
					}
				}
			}			
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		}catch(Exception e){
			this.msg = "Error :" +e.toString();
			e.printStackTrace();
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

	
	public String[] getBook() {
		
		return this.book;
	}

	
	public void setBook(String[] book) {
		
		this.book = book;
	}

	
	public String[] getSoluong() {
		
		return this.soluong;
	}

	
	public void setSoluong(String[] soluong) {
		
		this.soluong = soluong;
	}

	
	public String[] getAvailable() {
	
		return this.Available;
	}

	
	public void setAvailable(String[] Available) {
	
		this.Available = Available;
	}
	
}