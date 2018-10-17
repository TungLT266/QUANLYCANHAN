package geso.traphaco.center.beans.bandott.imp;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import geso.traphaco.center.beans.bandott.IBandott;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

public class Bandott implements IBandott
{
	String KHACHHANGSI_ID = "100890";
	
	String userId;
	String mafast;
	String ngay;
	String address;
	String trungtam;
	String msg;
	
	ResultSet ddkdList;
	String ddkdId;
	
	ResultSet nvList;
	String nvId;
	
	
	ResultSet tbhList;
	String tbhId;
	
	ResultSet khSelected; //da vieng tham
	ResultSet khList; //chua vieng tham
	
	ResultSet dstbKhachhang;
	ResultSet khKocodoanhso;
	
	String denngay;
	
	ResultSet vungList;
	String vungId;
	ResultSet kvList;
	String kvId;
	ResultSet nppList;
	String nppId;
	ResultSet cttbList;
	String cttbId;
	
	String khSi;
	
	String[] latcondition;
	String[] longcondition;
	
	ResultSet infoRs;
	ResultSet dpNganhRs;
	ResultSet infoDetail;
	ResultSet kiemTKRs;
	
	dbutils db;

	geso.traphaco.center.util.Utility util = new geso.traphaco.center.util.Utility();
	public Bandott()
	{
		String[] dauthang = this.getDateTime().split("-");
		this.ngay = dauthang[0] + "-" + dauthang[1] + "-" + dauthang[2];
		this.address = "";
		this.trungtam = "";
		this.denngay = this.getDateTime();
		this.mafast="";
		this.vungId = "";
		this.kvId = "";
		this.nppId = "";
		this.cttbId = "";
		this.ddkdId = "";
		this.nvId = "";
		this.tbhId = "";
		this.khSi = "0";
		this.msg = "";
		this.ttId="";		
		this.action="";
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
	
	public String getDate() 
	{
		return this.ngay;
	}

	public void setDate(String date) 
	{
		this.ngay = date;
	}

	public ResultSet getDdkdRs() 
	{
		return this.ddkdList;
	}

	public void setDdkdRs(ResultSet ddkdRs) 
	{
		this.ddkdList = ddkdRs;
	}

	public String getDdkdId() 
	{
		return this.ddkdId;
	}

	public void setDdkdId(String ddkdId) 
	{
		this.ddkdId = ddkdId;
	}

	public ResultSet getTbhRs() 
	{
		return this.tbhList;
	}

	public void setTbhRs(ResultSet tbhRs) 
	{
		this.tbhList = tbhRs;
	}

	public String getTbhId() 
	{
		return this.tbhId;
	}

	public void setTbhId(String tbhId) 
	{
		this.tbhId = tbhId;
	}

	public ResultSet getKhDaViengThamRs() 
	{
		return this.khSelected;
	}

	public void setKhDaViengThamRs(ResultSet KhRs) 
	{
		this.khSelected = KhRs;
	}

	public ResultSet getKhChuaViengThamRs()
	{
		return this.khList;
	}

	public void setKhChuaViengThamRs(ResultSet KhRs) 
	{
		this.khList = KhRs;
	}
	
	Utility Ult = new Utility();
	public void init() 
	{
		
		try
		{

			String			sql = "select phanloai,LOAI from nhanvien where pk_seq=" + this.userId;
			ResultSet rs = this.db.get(sql);
			if (rs != null)
			{
				if (rs.next())
				{
					this.phanloai = rs.getString("phanloai");
					loaiNv= rs.getString("LOAI")==null?"":rs.getString("LOAI");

					if (rs.getString("phanloai").equals("1")||( this.phanloai.equals("2")   && loaiNv.equals("3")   )  )
					{
						this.nppId = Ult.getIdNhapp(this.userId);
					}
					rs.close();
				}
			}
			//System.out.println("sql : " + sql);
		} catch (Exception er)
		{

		}
		
		String query="select pk_Seq,ten from tinhthanh where 1=1 ";;
		if(this.vungId.length()>0)
		{
			query+=" and vung_fk='"+this.vungId+"' ";
		}
		
		 if(this.phanloai.equals("2")&& !loaiNv.equals("3"))
			{
			 query+= " and pk_Seq in " + Ult.Quyen_TinhThanh(userId)+"";
			}
		
		
		this.ttRs=this.db.get(query);
		
		

		query="select pk_seq, ten from vung where trangthai = '1'";
		 if(this.phanloai.equals("2")&& !loaiNv.equals("3"))
			{
			 query+= " and pk_Seq in " + Ult.Quyen_Vung(userId)+"";
			}
		this.vungList = db.get(query);
		
		
		
		
		if(this.vungId.trim().length() > 0)
		{
			this.kvList = db.get("select pk_seq, ten from khuvuc where vung_fk = '" + this.vungId + "' and trangthai = '1'");
		}
		else
		{
			this.kvList = db.get("select pk_seq, ten from khuvuc where trangthai = '1'");
		}
		
		String sqlNpp = "select pk_seq, ten from nhaphanphoi where trangthai = '1' ";
		
		 if(this.phanloai.equals("2")&& !loaiNv.equals("3"))
			{
			 sqlNpp+= " and pk_Seq in " + Ult.quyen_npp(userId)+"";
			}
		
		if(this.kvId.trim().length() > 0)
			sqlNpp += " and khuvuc_fk = '" + this.kvId.trim() + "' ";
		if(this.vungId.trim().length() > 0)
			sqlNpp += " and khuvuc_fk in (select pk_seq from khuvuc where vung_fk = '" + this.vungId.trim() + "') ";
		
		if(this.ttId.length()>0)
			sqlNpp +=" and tinhthanh_fk in ("+this.ttId+")" ;
		
		this.nppList = db.get(sqlNpp);
		
		
		if(this.nppId.trim().length() > 0)
		{
			 query = "select pk_seq as ddkdId, ten as ddkdTen from daidienkinhdoanh where trangthai=1 ";			
			
			if(this.nppId.trim().length() > 0)
				query += "\n and pk_Seq in (select ddkd_fk from daidienkinhdoanh_npp where  npp_fk = '" + this.nppId + "' ) ";
			
			if(this.kvId.length()>0)
				query += "\n and pk_Seq in (select ddkd_fk from daidienkinhdoanh_npp where  npp_fk in (select pk_Seq from nhaphanphoi where khuvuc_fk='"+this.kvId+"' )) ";
			
			if(this.ttId.length()>0)
				query += "\n and pk_Seq in (select ddkd_fk from daidienkinhdoanh_npp where  npp_fk in (select pk_Seq from nhaphanphoi where tinhthanh_fk='"+this.ttId+"' ) )";
			
			this.ddkdList = db.get(query);
			System.out.println("ddkdRs =" + query);
			
			if(this.ddkdId.trim().length() > 0)
			{
				this.tbhList = db.get("select distinct a.PK_SEQ as tbhId, 'T'+CAST(a.NGAYID as CHAR(2)) +b.TEN   as tbhTen from tuyenbanhang a inner join DAIDIENKINHDOANH b on b.PK_SEQ=a.DDKD_FK where a.ddkd_fk = '" + this.ddkdId + "' and a.PK_SEQ in (select distinct tbh_fk from KHACHHANG_TUYENBH) order by tbhTen ");
			}
			
			//Lay cach 3 thang
			if(this.ngay.length() <= 0)
				this.ngay = this.getDateTime();
			
			String[] now = this.ngay.split("-");
			String thang = now[1];
			
			if(thang.startsWith("0"))
				thang = thang.replaceAll("0", "");
			
			String iDay = now[2];
			if(iDay.startsWith("0"))
				iDay = iDay.replaceAll("0", "");
			
			String before1 = Integer.toString(Integer.parseInt(thang) - 1);
			if(before1.length() < 2)
				before1 = "0" + before1;
			
			String before3 = Integer.toString(Integer.parseInt(thang) - 3);
			if(before3.length() < 2)
				before3 = "0" + before3;
			
			String ngaycuoi = LastDayOfMonth(Integer.parseInt(thang) - 1, Integer.parseInt(now[0]));
			
			//String bd_truoc_3_thang = now[0] + "-" + before3 + "-01";
			String bd_truoc_1_thang = now[0] + "-" + before3 + "-" + "01";
			String kt_truoc_1_thang = now[0] + "-" + before1 + "-" + ngaycuoi;
			
			String dauthang = now[0] + "-" + now[1] + "-01";

			int tongsongay = Songaytrongthang(Integer.parseInt(thang) - 1, Integer.parseInt(now[0]));
			tongsongay = (tongsongay - 4); //tru 4 chu nhat
			
			String condition = "";
			if(this.nppId.trim().length() > 0)
				condition += " AND npp_fk = '" + this.nppId + "' ";
			else
				condition += " AND npp_fk in ( select pk_seq from NHAPHANPHOI where khuvuc_fk = '" + this.kvId + "' ) ";
			
			System.out.println("---BAT DAU TRUOC 1 THANG: " + bd_truoc_1_thang + " -- KET THUC TRUOC 1 THANG: " + kt_truoc_1_thang + " -- DAU THANG: " + dauthang );
			
			String sql = "select kh.pk_seq as khId, kh.ten as khTen, isnull(kh.dienthoai, 'NA') as dienthoai, isnull(kh.diachi, '') as diachi, kh.lat, kh.long, " +
							" case when anhcuahang is null then 'hinh1.jpg' else anhcuahang end as anhcuahang,  " +
							" cast(isnull(doanhsoThangtruoc, 0) as numeric(18, 0)) / 3 as doanhsoThangtruoc ,   " +
							" cast( isnull(doanhsoTudauthang, 0) as numeric(18, 0)) as doanhsoTrongthang, " +
							" ( select min(ngaynhap) from DONHANG where trangthai != '2' and khachhang_fk = kh.pk_seq ) AS ngayMHDauTien," +
							" ( select max(ngaynhap) from DONHANG where trangthai != '2' and khachhang_fk = kh.pk_seq ) AS ngayMHCuoicung,	" +
							" ( select tonggiatri from DONHANG where pk_seq = ( select max(pk_seq) from DONHANG where trangthai != '2' and khachhang_fk = kh.pk_seq ) ) AS dhGANNHAT, " +
							" ( select max(thoidiem) from DDKD_KHACHHANG where khachhang_fk = kh.pk_seq ) AS vtGANNHAT," +
							" ( select sum(tonggiatri) / count(pk_seq) from DONHANG where trangthai != '2' and khachhang_fk = kh.pk_seq and ngaynhap >= '" + bd_truoc_1_thang + "' and ngaynhap <= '" + kt_truoc_1_thang + "' ) as tbDONHANG, " +
							" ( select count(distinct dh_sp.sanpham_fk) from DONHANG dh inner join DONHANG_SANPHAM dh_sp on dh.pk_seq = dh_sp.donhang_fk where dh.khachhang_fk = kh.pk_seq and dh.trangthai != '2' ) as dophuSP, " +
							" ( select sum(soluong) as tonkho from KHACHHANG_KHO where KH_FK = kh.pk_seq and ngay = '" + this.ngay + "' ) as tonkhoSP ,	" +
							" CASE WHEN isnull(kh.LOAICUAHANG, '0') = '" + KHACHHANGSI_ID + "' THEN '1' ELSE '0' END AS khachhangsi " +
						"from KhachHang kh  " +
						"left join  " +
						"(   " +
							"select a.khachhang_fk as khId, sum(b.soluong * b.giamua) as doanhsoThangtruoc    " +
							"from donhang a inner join donhang_sanpham b on a.pk_seq = b.donhang_fk  " +
							"where a.khachhang_fk in ( select pk_seq from khachhang where lat is not null " + condition + " ) and a.trangthai = '1'   " +
								"and '" + bd_truoc_1_thang + "' <= a.ngaynhap and a.ngaynhap <= '" + kt_truoc_1_thang + "'   " +
							"group by a.khachhang_fk  " +
						")   " +
						"thangtruoc on kh.pk_seq = thangtruoc.khId left join   " +
						"(   " +
							"select a.khachhang_fk as khId, sum(b.soluong * b.giamua) / 1  as doanhsoTudauthang   " +
							"from donhang a inner join donhang_sanpham b on a.pk_seq = b.donhang_fk  " +
							"where a.khachhang_fk in (select pk_seq from khachhang where lat is not null " + condition + " ) and a.trangthai = '1'   " +
								"and '" + dauthang + "' <= a.ngaynhap and a.ngaynhap <= '" + this.ngay + "'   " +
							"group by a.khachhang_fk  " +
						")   " +
						"trongthang  on kh.pk_seq = trongthang.khId   " +
						"where lat is not null and long is not null ";
			if(this.nppId.trim().length() > 0 )
				sql += " and kh.npp_fk = '" + this.nppId + "'  ";
			else
				sql += " and kh.npp_fk in ( select pk_seq from NHAPHANPHOI where khuvuc_fk = '" + this.kvId + "' ) ";
			
			if(this.tbhId.trim().length() > 0)
			{
				sql += " and kh.pk_seq in (select khachhang_fk from khachhang_tuyenbh where tbh_fk = '" + this.tbhId + "') ";
			} 
			else if(this.ddkdId.trim().length() > 0)
			{
				sql += " and kh.pk_seq in ( select khachhang_fk from khachhang_tuyenbh where tbh_fk in (select pk_seq from tuyenbanhang where ddkd_fk = '" + this.ddkdId + "') ) ";
			}
			
			System.out.println("[Bandott.init] 1.Khoi tao khach hang: " + sql);
			
			if(action.equals("viewBd"))
				this.khList = db.get(sql);
			
			
			if(this.ngay.trim().length() > 0)
			{
				query = " select distinct a.pk_seq as khId, a.ten as khTen, isnull(a.diachi, '') as diachi, isnull(a.dienthoai, 'NA') as dienthoai, b.thoidiem, c.ten as ddkdTen, b.lat, b.long, " +
						"	case when anhcuahang is null then 'hinh1.jpg' else anhcuahang end as anhcuahang, " +
						"   cast(isnull(doanhsoThangtruoc, 0) as numeric(18, 0)) / 3 as doanhsoThangtruoc ,   " +
						"   cast( isnull(doanhsoTudauthang, 0) as numeric(18, 0)) as doanhsoTrongthang, " +
						" ( select min(ngaynhap) from DONHANG where trangthai != '2' and khachhang_fk = a.pk_seq ) AS ngayMHDauTien, " +
						" ( select max(ngaynhap) from DONHANG where trangthai != '2' and khachhang_fk = a.pk_seq ) AS ngayMHCuoicung,	" +
						" ( select tonggiatri from DONHANG where pk_seq = ( select max(pk_seq) from DONHANG where trangthai != '2' and khachhang_fk = a.pk_seq ) ) AS dhGANNHAT, " +
						" ( select max(thoidiem) from DDKD_KHACHHANG where khachhang_fk = a.pk_seq ) AS vtGANNHAT, " +
						" ( select sum(tonggiatri) / count(pk_seq) from DONHANG where trangthai != '2' and khachhang_fk = a.pk_seq and ngaynhap >= '" + bd_truoc_1_thang + "' and ngaynhap <= '" + kt_truoc_1_thang + "' ) as tbDONHANG,	" +
						" ( select count(distinct dh_sp.sanpham_fk) from DONHANG dh inner join DONHANG_SANPHAM dh_sp on dh.pk_seq = dh_sp.donhang_fk where dh.khachhang_fk = a.pk_seq and dh.trangthai != '2' ) as dophuSP, " +
						" ( select sum(soluong) from KHACHHANG_KHO where KH_FK = a.pk_seq and ngay = '" + this.ngay + "' ) as tonkhoSP,	" +
						" ( select sum(tonggiatri)  from DONHANG where trangthai != '2' and khachhang_fk = a.pk_seq and ngaynhap >= '" + this.ngay + "' and ngaynhap <= '" + this.ngay + "' ) as ds	" +
						" from khachhang a inner join ddkd_khachhang b on a.pk_seq = b.khachhang_fk " +
						"	inner join daidienkinhdoanh c on b.ddkd_fk = c.pk_seq " +
						"left join  " +
						"(   " +
							"select a.khachhang_fk as khId, sum(b.soluong * b.giamua)  as doanhsoThangtruoc    " +
							"from donhang a inner join donhang_sanpham b on a.pk_seq = b.donhang_fk  " +
							"where a.khachhang_fk in ( select pk_seq from khachhang where lat is not null " + condition + " ) and a.trangthai = '1'   " +
								"and '" + bd_truoc_1_thang + "' <= a.ngaynhap and a.ngaynhap <= '" + kt_truoc_1_thang + "'   " +
							"group by a.khachhang_fk  " +
						")   " +
						"thangtruoc on a.pk_seq = thangtruoc.khId left join   " +
						"(   " +
							"select a.khachhang_fk as khId, sum(b.soluong * b.giamua) / 1  as doanhsoTudauthang   " +
							"from donhang a inner join donhang_sanpham b on a.pk_seq = b.donhang_fk  " +
							"where a.khachhang_fk in (select pk_seq from khachhang where lat is not null " + condition + " ) and a.trangthai = '1'   " +
								"and '" + dauthang + "' <= a.ngaynhap and a.ngaynhap <= '" + this.ngay + "'   " +
							"group by a.khachhang_fk  " +
						")   " +
						"trongthang  on a.pk_seq = trongthang.khId   " +
						" where replace(convert(nvarchar(10), b.thoidiem, 102), '.', '-') = '" + this.ngay + "' ";
				
				if(this.nppId.trim().length() > 0)
					query += " and a.npp_fk = '" + this.nppId + "' ";
				else
					query += " and a.npp_fk in ( select pk_seq from NHAPHANPHOI where khuvuc_fk = '" + this.kvId + "' ) ";
				
				if(this.tbhId.trim().length() > 0)
				{
					query += " and a.pk_seq in (select khachhang_fk from khachhang_tuyenbh where tbh_fk = '" + this.tbhId + "') ";
				}
				else if(this.ddkdId.trim().length() > 0)
				{
					query += " and b.ddkd_fk = '" + this.ddkdId + "' ";
				}
				
				query += " order by b.thoidiem ";
				
				System.out.println("[Bandott.init] 2.Khach hang Selected: " + query);
				
				
				if(action.equals("viewBd"))
					this.khSelected = db.getScrol(query);
				
				
				//Ve duong noi cac NVBH
				if(this.ddkdId.trim().length() > 0)
				{
					if(this.khSelected != null)
					{
						try 
						{
							String latC = "";
							String longC = "";
							
							this.khSelected.beforeFirst();
							while(khSelected.next())
							{
								latC += this.khSelected.getString("lat") + "__";
								longC += this.khSelected.getString("long") + "__";
							}
							
							if(latC.trim().length() > 0)
							{
								latC = latC.substring(0, latC.length() - 2);
								this.latcondition = latC.split("__");
								
								longC = longC.substring(0, longC.length() - 2);
								this.longcondition = longC.split("__");
							}
						} 
						catch (Exception e) {e.printStackTrace();}
					}
					
				}
				
			}
			
			//Khoi tao toa do Center dau
			query = "select top(1) pk_seq, lat + ',' + long  as trungtam " +
					"from KhachHang where trangthai = '1' and lat is not null and long is not null ";

			if(this.nppId.trim().length() > 0)
				query += " and npp_fk = '" + this.nppId + "' ";
			else
				query += " and npp_fk in ( select pk_seq from NHAPHANPHOI where khuvuc_fk = '" + this.kvId + "' ) ";
			
			if(this.tbhId.trim().length() > 0)
			{
				query += " and pk_seq in (select khachhang_fk from khachhang_tuyenbh where tbh_fk = '" + this.tbhId + "') ";
			}
			else if(this.ddkdId.trim().length() > 0)
			{
				query += " and pk_seq in ( select khachhang_fk from khachhang_tuyenbh where tbh_fk in (select pk_seq from tuyenbanhang where ddkd_fk = '" + this.ddkdId + "') ) ";
			}
			
			System.out.println("[Bandott.init] toa do center dau query = " + query);
			
			ResultSet rsCenter = db.get(query);
			if(rsCenter != null)
			{
				try 
				{
					if(rsCenter.next())
					{
						this.trungtam = rsCenter.getString("trungtam");
					}
					rsCenter.close();
				} 
				catch (Exception e) 
				{
					System.out.println("115.Exception: " + e.getMessage());
				}
			}
			
			//LAY THONG TIN CHI TIET
			/*query = "select  " +
					"	isnull( ( select count(*) from NHAPHANPHOI where trangthai = '1' and khuvuc_fk = kv.pk_seq ), 0  ) as soNPP, " +
					"	isnull( ( select count(*) from DAIDIENKINHDOANH where trangthai = '1' and npp_fk in ( select pk_seq from NHAPHANPHOI where khuvuc_fk = kv.pk_seq ) ), 0  ) as soNVBH, " +
					"	isnull( ( select count(*) from KHACHHANG where trangthai = '1' and npp_fk in ( select pk_seq from NHAPHANPHOI where khuvuc_fk = kv.pk_seq ) ), 0  ) as soKH, " +
					"	isnull( ( select sum(tonggiatri) from DONHANG where trangthai = '1' and npp_fk in ( select pk_seq from NHAPHANPHOI where khuvuc_fk = kv.pk_seq ) and month(ngaynhap) = '6' ), 0  ) as tongDSNGANH, " +
					"	isnull( ( select sum(tonggiatri) from DONHANG where trangthai = '1' and month(ngaynhap) = '6' ), 0  ) as tongDS, " +
					"	 0 as chitieuASM, " +
					"	isnull( ( select sum(tonggiatri) from DONHANG where trangthai = '1' and month(ngaynhap) = '6' and ASM in ( select ASM_FK from ASM_KHUVUC where khuvuc_fk = kv.pk_seq ) ) , 0  ) as thucdatASM " +
					"from KHUVUC kv where pk_seq = '" + this.kvId + "' ";
			System.out.println("---LAY THONG TIN TONG: " + query);
			this.infoRs = db.get(query);
			
			query = "select f.Donvikinhdoanh as ten, count(distinct a.pk_seq) as doPHU  " +
					"from KHACHHANG a inner join NHAPHANPHOI b on a.npp_fk = b.pk_seq " +
					"	inner join DONHANG c on a.pk_seq = c.khachhang_fk " +
					"	inner join DONHANG_SANPHAM d on c.pk_seq = d.donhang_fk " +
					"	inner join SANPHAM e on d.sanpham_fk = e.pk_seq " +
					"	inner join DONVIKINHDOANH f on e.dvkd_fk = f.pk_seq " +
					"where a.trangthai = '1' and b.khuvuc_fk = '" + this.kvId + "' " +
					"group by f.Donvikinhdoanh ";
			this.dpNganhRs = db.get(query);*/
			
			
			//KIEM TON KHO --> DEMO THI CHI DUNG CHO 1 KHACH HANG GESO CO DINH
			/*query = "select b.ma, b.ten, a.soluong " +
					"from KHACHHANG_KHO a inner join SANPHAM b on a.sanpham_fk = b.pk_seq " +
					"where KH_FK = '606538' and ngay = '" + this.ngay + "'";
			this.kiemTKRs = db.getScrol(query);*/
			
		}
		
	}
	
	public void initLoTrinhOnline()
	{

		
		try
		{
			if(this.ngay.length() <= 0)
				this.ngay = this.getDateTime();
			

			String			sql = "select phanloai,LOAI from nhanvien where pk_seq=" + this.userId;
			ResultSet rs = this.db.get(sql);
			if (rs != null)
			{
				if (rs.next())
				{
					this.phanloai = rs.getString("phanloai");
					loaiNv= rs.getString("LOAI")==null?"":rs.getString("LOAI");

					if (rs.getString("phanloai").equals("1")||( this.phanloai.equals("2")   && loaiNv.equals("3")   )  )
					{
						this.nppId = Ult.getIdNhapp(this.userId);
					}
					rs.close();
				}
			}
			//System.out.println("sql : " + sql);
		} catch (Exception er)
		{

		}
		
		String query="select pk_Seq,ten from tinhthanh where 1=1 ";;
		if(this.vungId.length()>0)
		{
			query+=" and vung_fk='"+this.vungId+"' ";
		}		
		if(this.phanloai.equals("2")&& !loaiNv.equals("3"))
			{
			 query+= " and pk_Seq in " + Ult.Quyen_TinhThanh(userId)+"";
			}	
		this.ttRs=this.db.get(query);
		
		

		query="select pk_seq, ten from vung where trangthai = '1'";
		 if(this.phanloai.equals("2")&& !loaiNv.equals("3"))
			{
			 query+= " and pk_Seq in " + Ult.Quyen_Vung(userId)+"";
			}
		this.vungList = db.get(query);
		
		
		
		String sqlNpp = "select pk_seq, ten from nhaphanphoi where trangthai = '1' ";
		
		 if(this.phanloai.equals("2")&& !loaiNv.equals("3"))
			{
			 sqlNpp+= " and pk_Seq in " + Ult.quyen_npp(userId)+"";
			}
		 
		 if(this.ttId.length()>0)
				sqlNpp +=" and tinhthanh_fk in ("+this.ttId+")" ;
		 else
			 if(this.vungId.trim().length() > 0)
				 sqlNpp += " and tinhthanh_fk in (select pk_seq from tinhthanh where vung_fk = '" + this.vungId.trim() + "') ";
			
		this.nppList = db.get(sqlNpp);
		
		
		if(this.nppId.trim().length() > 0)
		{
			// lay' RS trinh duoc vien
			query = "select pk_seq as ddkdId, ten as ddkdTen from daidienkinhdoanh a where trangthai=1 	  -- " + 
					 "\n and pk_Seq in (select ddkd_fk from daidienkinhdoanh_npp where  npp_fk = '"+this.nppId+"' )  -- " + 
					 "\n and	 exists (select 1 from DDKD_VITRI where DDKD_FK =a.pk_Seq  )	"; //and NGAY = '"+this.ngay+"'
								
			this.ddkdList = db.get(query);
			System.out.println("ddkdRs =" + query);
		}
		
		// lay Rs nhan vien
		if(this.nppId.trim().length() > 0)
		{
			query =  "\n select distinct nv.PK_SEQ -- " + 
					 "\n 	, case when ASM_FK IS not null then 'ASM' -- " + 
					 "\n 		when BM_FK IS not null then 'BM' -- " + 
					 "\n 		when GSBH_FK IS not null then 'PTT' end -- " + 
					 "\n 	   + ' ' +nv.ten as TEN -- " + 
					 "\n from  NHANVIEN nv   -- " + 
					 "\n WHERE  nv.TRANGTHAI = 1 and ( GSBH_FK is not null ) -- " + 
					// "\n and exists ( select 1 from  NHANVIEN_VITRI where NHANVIEN_FK = nv.PK_SEQ ) -- " +  // and NGAY = '"+this.ngay+"'
					 "\n AND nv.PK_SEQ IN (SELECT NHANVIEN_FK FROM PHAMVIHOATDONG WHERE NPP_FK IN  -- " + 
					 "\n ( SELECT PK_SEQ FROM NHAPHANPHOI WHERE TRANGTHAI = 1  -- " + 
					 "\n   and PK_SEQ = '"+this.nppId+"' )) ";
			System.out.println("[Bandott.init] 1.get nhanvien RS: " + query);
			this.nvList = this.db.get(query);
		}
		
		
		if(this.nppId.length() > 0 &&!action.equals(""))
		{
			String sql = "";
			if(action.equals("viewBd") && this.ddkdId.trim().length() >0 )
			{
				 sql = "declare @ngay char(10) declare @ddkdId numeric(18,0) -- " + 
				 "\n set @ngay = '"+this.ngay+"' set @ddkdId = '"+this.ddkdId+"' -- " + 
				 "\n select td.lat , td.long , td.stt ,td.khoangcach, TYPE as isVT -- " + 
				 "\n 		, case when kh.PK_SEQ IS not null then isnull(kh.TEN,'') + ' - ' + isnull( kh.CHUCUAHIEU,'')+ ' - ' + isnull(kh.DIACHI,'')  else '' end as thongtinkh -- " + 
				 "\n		,td.thoidiem	"+		
				 "\n from DDKD_VITRI td  -- " + 
				 "\n left join KHACHHANG kh on kh.PK_SEQ = td.khachhang_fk -- " + 
				 "\n where ddkd_fk = @ddkdId and  NGAY = @ngay and td.lat is not null and td.long is not null and ( isnull(td.khoangcach,0) >10 or STT = (select top 1 STT from  DDKD_VITRI where  NGAY = td.NGAY and DDKD_FK =td.DDKD_FK and LAT is not null and LONG is not null  ) )";	
				
			}
			else if(action.equals("viewBdTT") && this.nvId.trim().length() >0 )
			{
				sql = "declare @ngayNv char(10) declare @nvId numeric(18,0)  -- " + 
					 "\n set @ngayNv = '"+this.ngay+"' set @nvId = '"+this.nvId+"'  -- " + 
					 "\n select td.lat , td.long , td.stt ,  td.khoangcach, 0 as isVT  -- " + 
					 "\n 	, td.diachi as thongtinkh  -- " + 
					 "\n		,td.thoidiem	-- " + 	
					 "\n from NHANVIEN_VITRI td   -- " + 
					 "\n where NHANVIEN_FK = @nvId and  NGAY = @ngayNv and td.lat is not null and td.long is not null and ( isnull(td.khoangcach,0) >10 or STT = (select top 1 STT from  DDKD_VITRI where  NGAY = td.NGAY and NHANVIEN_FK =td.NHANVIEN_FK and LAT is not null and LONG is not null  ) ) ";
			}
			if(!sql.equals(""))
			{
				System.out.println("[Bandott.init] 1.Khoi tao LoTrinh online daidienkinhdoanh: " + sql);
				this.khList = db.get(sql);
			}
		}
							
	}
	
	public void initLoTrinhOnlineNPP()
	{

		
		try
		{
			if(this.ngay.length() <= 0)
				this.ngay = this.getDateTime();
			

			String			sql = "select phanloai,LOAI from nhanvien where pk_seq=" + this.userId;
			ResultSet rs = this.db.get(sql);
			if (rs != null)
			{
				if (rs.next())
				{
					this.phanloai = rs.getString("phanloai");
					loaiNv= rs.getString("LOAI")==null?"":rs.getString("LOAI");

					if (rs.getString("phanloai").equals("1")||( this.phanloai.equals("2")   && loaiNv.equals("3")   )  )
					{
						;
					}
					rs.close();
				}
			}
			//System.out.println("sql : " + sql);
		} catch (Exception er)
		{

		}
		
		
		
		

		
		String query = "";
		
		
		String sqlNpp = "select pk_seq, ten from nhaphanphoi where trangthai = '1' ";
		
		 if(this.phanloai.equals("2")&& !loaiNv.equals("3"))
			{
			 sqlNpp+= " and pk_Seq in " + Ult.quyen_npp(userId)+"";
			}else
			{
				sqlNpp+= " and pk_Seq =" + util.getIdNhapp(this.userId);
			}
								 		
		this.nppList = db.get(sqlNpp);
		
		
		if(this.nppId.trim().length() > 0)
		{
			// lay' RS trinh duoc vien
			query = "select pk_seq as ddkdId, ten as ddkdTen from daidienkinhdoanh a where trangthai=1 	  -- " + 
					 "\n and pk_Seq in (select ddkd_fk from daidienkinhdoanh_npp where  npp_fk = '"+this.nppId+"' )  -- " + 
					 "\n and	 exists (select 1 from DDKD_VITRI where DDKD_FK =a.pk_Seq  )	"; //and NGAY = '"+this.ngay+"'
								
			this.ddkdList = db.get(query);
			System.out.println("ddkdRs =" + query);
		}
		

		
		
		if(this.nppId.length() > 0 &&!action.equals(""))
		{
			String sql = "";
			if(action.equals("viewBd") && this.ddkdId.trim().length() >0 )
			{
				 sql = "declare @ngay char(10) declare @ddkdId numeric(18,0) -- " + 
				 "\n set @ngay = '"+this.ngay+"' set @ddkdId = '"+this.ddkdId+"' -- " + 
				 "\n select td.lat , td.long , td.stt ,td.khoangcach, TYPE as isVT -- " + 
				 "\n 		, case when kh.PK_SEQ IS not null then isnull(kh.TEN,'') + ' - ' + isnull( kh.CHUCUAHIEU,'')+ ' - ' + isnull(kh.DIACHI,'')  else '' end as thongtinkh -- " + 
				 "\n		,td.thoidiem	"+		
				 "\n from DDKD_VITRI td  -- " + 
				 "\n left join KHACHHANG kh on kh.PK_SEQ = td.khachhang_fk -- " + 
				 "\n where ddkd_fk = @ddkdId and  NGAY = @ngay and td.lat is not null and td.long is not null and ( isnull(td.khoangcach,0) >10 or STT = (select top 1 STT from  DDKD_VITRI where  NGAY = td.NGAY and DDKD_FK =td.DDKD_FK and LAT is not null and LONG is not null  ) )";	
				
			}			
			if(!sql.equals(""))
			{
				System.out.println("[Bandott.init] 1.Khoi tao LoTrinh online daidienkinhdoanh: " + sql);
				this.khList = db.get(sql);
			}
		}
							
	}
	
	public void createRS() 
	{
		this.ddkdList = db.get("select pk_seq as ddkdId, ten as ddkdTen from daidienkinhdoanh where npp_fk = '" + this.nppId + "'");
		this.tbhList = db.get("select pk_seq as tbhId, diengiai as tbhTen from tuyenbanhang where ddkd_fk = '" + this.ddkdId + "'");
		
		System.out.print("select pk_seq as tbhId, diengiai as tbhTen from tuyenbanhang where ddkd_fk = '" + this.ddkdId + "'");
		
		if(ddkdId.equals(""))
		{
			String sql = "select ten as khTen, diachi, dienthoai as dienthoai, lat, long from khachhang where a.npp_fk = '" + this.nppId + "'";
			this.khList = db.get(sql);
			
			sql = "select distinct a.ten as khTen, a.diachi, a.dienthoai as dienthoai, b.lat, b.long from khachhang a inner join ddkd_khachhang b on a.pk_seq = b.khachhang_fk where a.npp_fk = '" + this.nppId + "'";
			this.khSelected = db.get(sql + " and replace(convert(nvarchar(10), thoidiem, 102), '.', '-') = '" + this.ngay + "' ");
		}
		else
		{
			String query = "select a.ten as khTen, a.diachi, a.dienthoai as dienthoai, a.lat, a.long from khachhang a inner join khachhang_tuyenbh b on a.pk_seq = b.pk_seq inner join tuyenbanhang c on b.tbh_fk = c.pk_seq ";
			query = query +	" where c.ddkd_fk = '" + this.ddkdId + "'";
			
			if(this.tbhId.length() > 0)
			{
				query = query + " and b.tbh_fk = '" + this.tbhId + "'";
			}
			
			query = query + " and a.pk_seq in (select distinct khachhang_fk from ddkd_khachhang where replace(convert(nvarchar(10), thoidiem, 102), '.', '-') = '" + this.ngay + "')";
			System.out.println("1.Khoi tao khach hang Selected: " + query);
			this.khSelected = db.get(query);
			
			query = query + " and a.pk_seq not in (select distinct khachhang_fk from ddkd_khachhang where replace(convert(nvarchar(10), thoidiem, 102), '.', '-') = '" + this.ngay + "')";
			System.out.println("2.Khoi tao khach hang chua Selected: " + query);
			this.khList = db.get(query);
		}
	}
	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	public void DBclose() 
	{
		try 
		{
			if(this.vungList != null) {
				this.vungList.close();
			}
			if(this.kvList != null) {
				this.kvList.close();
			}
			if(this.ddkdList != null) {
				this.ddkdList.close();
			}
			if(this.khList != null) {
				this.khList.close();
			}
			if(this.khSelected != null) {
				this.khSelected.close();
			}
			if(this.tbhList != null) {
				this.tbhList.close();
			}
			if(this.db != null) {
				this.db.shutDown();
			}
			
		} catch (Exception e) {}
	}

	public String getAddress() 
	{
		return this.address;
	}

	public void setAddress(String address) 
	{
		this.address = address;
	}

	public String getTrungtam() 
	{
		return this.trungtam;
	}

	public void setTrungtam(String trungtam) 
	{
		this.trungtam = trungtam;
	}

	public ResultSet getKhKhongDSTrongThang()
	{
		return this.khKocodoanhso;
	}

	public void setKhKhongDSTrongThang(ResultSet KhKoRs) 
	{
		this.khKocodoanhso = KhKoRs;
	}

	public ResultSet getDSTbThang() 
	{
		return this.dstbKhachhang;
	}

	public void getDSTbThang(ResultSet dstb3Thang) 
	{
		this.dstbKhachhang = dstb3Thang;
	}
	
	private String LastDayOfMonth(int month, int year) 
    {
        String ngay = "";
        switch (month)
        {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                {
                    ngay = "31";
                    break;
                }
            case 4:
            case 6:
            case 9:
            case 11:
                {
                    ngay = "30";
                    break;
                }
            case 2:
                {
                    if (year % 4 == 0)
                        ngay = "29";
                    else
                        ngay = "28";
                    break;
                }
        }

        return ngay;
    } 
	
	private int Songaytrongthang(int month, int year) 
    {
        int ngay = 0;
        switch (month)
        {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                {
                    ngay = 31;
                    break;
                }
            case 4:
            case 6:
            case 9:
            case 11:
                {
                    ngay = 30;
                    break;
                }
            case 2:
                {
                    if (year % 4 == 0)
                        ngay = 29;
                    else
                        ngay = 28;
                    break;
                }
        }

        return ngay;
    }

	public String getDenngay() 
	{
		return this.denngay;
	}

	public void setDenngay(String denngay)
	{
		this.denngay = denngay;
	}

	
	public ResultSet getVungRs() {
		
		return this.vungList;
	}

	
	public void setVungRs(ResultSet vungRs) {
		
		this.vungList = vungRs;
	}

	
	public String getVungId() {
		
		return this.vungId;
	}

	
	public void setVungId(String vungId) {
		
		this.vungId = vungId;
	}

	
	public ResultSet getKvRs() {
		
		return this.kvList;
	}

	
	public void setKvRs(ResultSet kvRs) {
		
		this.kvList = kvRs;
	}

	
	public String getkvId() {
		
		return this.kvId;
	}

	
	public void setKvId(String kvId) {
		
		this.kvId = kvId;
	}

	
	public ResultSet getNppRs() {
		
		return this.nppList;
	}

	
	public void setNppRs(ResultSet nppRs) {
		
		this.nppList = nppRs;
	}

	
	public String getNppId() {
		
		return this.nppId;
	}

	
	public void setNppId(String nppId) {
		
		this.nppId = nppId;
	}

	public void initTB() 
	{
		this.vungList = db.get("select pk_seq, ten from vung where trangthai = '1'");
		
		if(this.vungId.trim().length() > 0)
		{
			this.kvList = db.get("select pk_seq, ten from khuvuc where vung_fk = '" + this.vungId + "' and trangthai = '1'");
		}
		else
		{
			this.kvList = db.get("select pk_seq, ten from khuvuc where trangthai = '1'");
		}
		
		String sqlNpp = "select pk_seq, ten from nhaphanphoi where trangthai = '1' and pk_seq in "+ util.quyen_npp(this.userId);
		if(this.kvId.trim().length() > 0)
			sqlNpp += " and khuvuc_fk = '" + this.kvId.trim() + "' ";
		if(this.vungId.trim().length() > 0)
			sqlNpp += " and khuvuc_fk in (select pk_seq from khuvuc where vung_fk = '" + this.vungId.trim() + "') ";
		System.out.println("List NPP: "+sqlNpp);
		this.nppList = db.get(sqlNpp);
		
		String tbQuery = "";
		
		if(this.nppId.trim().length() > 0)
		{
			tbQuery = "select b.pk_seq, b.scheme from dangkytrungbay a inner join cttrungbay b on a.cttrungbay_fk = b.pk_seq " +
			 	  	"where npp_fk = '" + this.nppId + "' and a.trangthai != 2 ";
		}
		else
		{
			tbQuery = "select pk_seq, scheme from cttrungbay";
		}
		
		System.out.println("1.Lay CTTB: " + tbQuery);
		this.cttbList = db.get(tbQuery);
		
		if(this.nppId.trim().length() > 0)
		{
			this.ddkdList = db.get("select pk_seq as ddkdId, ten as ddkdTen from daidienkinhdoanh where npp_fk = '" + this.nppId + "'");
			
			if(this.ddkdId.trim().length() > 0)
			{
				this.tbhList = db.get("select pk_seq as tbhId, diengiai as tbhTen from tuyenbanhang where ddkd_fk = '" + this.ddkdId + "'");
			}
			
			
			if(this.cttbId.trim().length() > 0)
			{

			    String thang = getMonth();
			    String nam = getYear();
			    
				String sql = "select kh.pk_seq as khId, kh.ten as khTen, isnull(kh.dienthoai, 'NA') as dienthoai, kh.lat, kh.long, isnull(dk.ghiChu, '') as ghiChu,   " +
							"isnull( ( select ImageFilePath from DKTRUNGBAY_KHACHHANG_CHITIET where loai = '0' and KHACHHANG_FK = kh.PK_SEQ and DKTRUNGBAY_FK = dk.DKTRUNGBAY_FK  and thang = '"+thang+"' and nam = '"+nam+"') , '') as hinhBenTrai, " +
							"isnull( ( select ImageFilePath from DKTRUNGBAY_KHACHHANG_CHITIET where loai = '2' and KHACHHANG_FK = kh.PK_SEQ and DKTRUNGBAY_FK = dk.DKTRUNGBAY_FK  and thang = '"+thang+"' and nam = '"+nam+"') , '') as hinhBenPhai, " +
							"isnull( ( select ImageFilePath from DKTRUNGBAY_KHACHHANG_CHITIET where loai = '1' and KHACHHANG_FK = kh.PK_SEQ and DKTRUNGBAY_FK = dk.DKTRUNGBAY_FK  and thang = '"+thang+"' and nam = '"+nam+"') , '') as hinhGiua " +
				
							 "from KhachHang kh  left join DKTrungBay_KhachHang dk on kh.pk_seq = dk.khachhang_fk  " +
							 "where kh.npp_fk = '" + this.nppId + "' and lat is not null and long is not null   " +
							 			"and dk.dktrungbay_fk in ( select pk_seq from dangkytrungbay where cttrungbay_fk = '" + this.cttbId + "' and trangthai != 2 and npp_fk = '" + this.nppId + "' )   ";
				
				if(this.ddkdId.trim().length() > 0)
				{
					sql += " and kh.pk_seq in ( select khachhang_fk from khachhang_tuyenbh where tbh_fk in (select pk_seq from tuyenbanhang where ddkd_fk = '" + this.ddkdId + "') ) ";
				}
				
				if(this.tbhId.trim().length() > 0)
				{
					sql += " and kh.pk_seq in ( select khachhang_fk from khachhang_tuyenbh where tbh_fk = '" + this.tbhId + "' ) ";
				}
				
				
				//sql = "select trungbay.* from ( " + sql + " ) trungbay where trungbay.hinhBenTrai != '' or trungbay.hinhBenPhai != '' or trungbay.hinhGiua != ''";
				
				
				System.out.println("1.Khoi tao khach hang trung bay: " + sql);
				this.khList = db.get(sql);
				
	
				//Khoi tao toa do Center dau
				String query = "select top(1) pk_seq, lat + ',' + long  as trungtam " +
						"from KhachHang where trangthai = '1' and lat is not null and long is not null and npp_fk = '" + this.nppId + "'";
				
				if(this.ddkdId.trim().length() > 0)
				{
					query += " and pk_seq in ( select khachhang_fk from khachhang_tuyenbh where tbh_fk in (select pk_seq from tuyenbanhang where ddkd_fk = '" + this.ddkdId + "') ) ";
				}
				
				if(this.tbhId.trim().length() > 0)
				{
					query += " and pk_seq in (select khachhang_fk from khachhang_tuyenbh where tbh_fk = '" + this.tbhId + "') ";
				}
				
				if(this.cttbId.trim().length() > 0)
				{
					sql += " and pk_seq in ( select khachhang_fk from dktrungbay_khachhang where dktrungbay_fk in ( select pk_seq from dangkytrungbay where npp_fk = '" + this.nppId + "' and trangthai != '2' and cttrungbay_fk = '" + this.cttbId + "' )  ) ";
				}
				
				ResultSet rsCenter = db.get(query);
				if(rsCenter != null)
				{
					try 
					{
						if(rsCenter.next())
						{
							this.trungtam = rsCenter.getString("trungtam");
						}
						rsCenter.close();
					} 
					catch (Exception e) 
					{
						System.out.println("115.Exception: " + e.getMessage());
					}
				}
			}
		
		}
	}
	
	public void initToaDo()  {
		
		
		System.out.println("[Bandott.initToado] ------------------------------------ sql = " );
		this.vungList = db.get("select pk_seq, ten from vung where trangthai = '1'");
		
		if(this.vungId.trim().length() > 0)
		{
			this.kvList = db.get("select pk_seq, ten from khuvuc where vung_fk = '" + this.vungId + "' and trangthai = '1'");
		}
		else
		{
			this.kvList = db.get("select pk_seq, ten from khuvuc where trangthai = '1'");
		}
		
		String sqlNpp = "select pk_seq, ten from nhaphanphoi where trangthai = '1' ";
		if(this.kvId.trim().length() > 0)
			sqlNpp += " and khuvuc_fk = '" + this.kvId.trim() + "' ";
		if(this.vungId.trim().length() > 0)
			sqlNpp += " and khuvuc_fk in (select pk_seq from khuvuc where vung_fk = '" + this.vungId.trim() + "') ";
		
		this.nppList = db.get(sqlNpp);
		
		
		System.out.println("nha phan phoi id" + this.nppId);
		if(this.nppId.trim().length() > 0)
		{
			System.out.println("nha phan phoi id" + this.nppId);
			
			this.ddkdList = db.get("select pk_seq as ddkdId, ten as ddkdTen from daidienkinhdoanh  where PK_SEQ in (select ddkd_fk from DAIDIENKINHDOANH_NPP where npp_fk='"+this.nppId+"') ");
		
			System.out.println("trinh duoc vien" + this.ddkdId);
			if(this.ddkdId.trim().length() > 0)
			{
				System.out.println("trinh duoc vien la :"+ this.ddkdId);
				this.tbhList = db.get("select pk_seq as tbhId, diengiai as tbhTen from tuyenbanhang where ddkd_fk = '" + this.ddkdId + "'");
				
			
				
				if(this.tbhId.trim().length() > 0)
					
					System.out.println("Tuyen ban hang 2: " + this.tbhId);
					
					String sql = " select  isnull(kh.CHUCUAHIEU,'NA') as CHUCUAHIEU  , kh.pk_seq as pk_seq, kh.maFAST as mafast, kh.smartid as khId, kh.ten as khTen, isnull(kh.dienthoai, 'NA') as dienthoai, kh.lat as lat, kh.long as lon,isnull(kh.diachi, 'NA') as diachi " +
					 " from KhachHang kh " +
					 " where kh.npp_fk = '" + this.nppId + "' and (lat is not null or long is not null)   ";						 			
					
					if(this.mafast.length()>0){
						sql +=" and kh.maFAST like '%"+this.mafast+"%'"; 
					}
					
					if(this.ddkdId.trim().length() > 0)
					{
						sql += " and kh.pk_seq in ( select khachhang_fk from khachhang_tuyenbh where tbh_fk in (select pk_seq from tuyenbanhang where ddkd_fk = '" + this.ddkdId + "') ) ";
					}
					
					if(this.tbhId.trim().length() > 0)
					{
						sql += " and kh.pk_seq in ( select khachhang_fk from khachhang_tuyenbh where tbh_fk = '" + this.tbhId + "' ) ";
					}
					sql += " order by kh.ten ";
					
					System.out.println("[Bandott.initToado] khachhang sql = " + sql);
					this.khList = db.get(sql);
				}
			}
		}
	
	
	public boolean xoaToaDoKh(String khId) {
		String sql = "UPDATE KHACHHANG SET LAT = NULL, LONG = NULL WHERE PK_SEQ = '" + khId + "'";
		System.out.println("[Bandott.xoaToaDoKh] sql = " + sql);
		return this.db.update(sql);
	}

	
	public ResultSet getCttbRs()
	{
		return this.cttbList;
	}

	public void setCttbRs(ResultSet cttbRs)
	{
		this.cttbList = cttbRs;
	}

	public String getCttbId() 
	{
		return this.cttbId;
	}

	public void setCttbId(String cttbId)
	{
		this.cttbId = cttbId;
	}


	public void initTB_Excel() 
	{
		this.vungList = db.get("select pk_seq, ten from vung where trangthai = '1'");
		
		if(this.vungId.trim().length() > 0)
		{
			this.kvList = db.get("select pk_seq, ten from khuvuc where vung_fk = '" + this.vungId + "' and trangthai = '1'");
		}
		else
		{
			this.kvList = db.get("select pk_seq, ten from khuvuc where trangthai = '1'");
		}
		
		String sqlNpp = "select pk_seq, ten from nhaphanphoi where trangthai = '1' ";
		if(this.kvId.trim().length() > 0)
			sqlNpp += " and khuvuc_fk = '" + this.kvId.trim() + "' ";
		if(this.vungId.trim().length() > 0)
			sqlNpp += " and khuvuc_fk in (select pk_seq from khuvuc where vung_fk = '" + this.vungId.trim() + "') ";
		
		this.nppList = db.get(sqlNpp);
		
		String tbQuery = "";
		
		if(this.nppId.trim().length() > 0)
		{
			tbQuery = "select b.pk_seq, b.scheme from dangkytrungbay a inner join cttrungbay b on a.cttrungbay_fk = b.pk_seq " +
			 	  	"where npp_fk = '" + this.nppId + "' and a.trangthai != 2 ";
		}
		else
		{
			tbQuery = "select pk_seq, scheme from cttrungbay";
		}
		
		System.out.println("1.Lay CTTB: " + tbQuery);
		this.cttbList = db.get(tbQuery);
		
		if(this.nppId.trim().length() > 0)
		{
			this.ddkdList = db.get("select pk_seq as ddkdId, ten as ddkdTen from daidienkinhdoanh where npp_fk = '" + this.nppId + "'");
			
			if(this.ddkdId.trim().length() > 0)
			{
				this.tbhList = db.get("select pk_seq as tbhId, diengiai as tbhTen from tuyenbanhang where ddkd_fk = '" + this.ddkdId + "'");
			}
			
			
			if(this.cttbId.trim().length() > 0)
			{
				String sql = "select kh.pk_seq as khId, kh.ten as khTen, isnull(kh.dienthoai, 'NA') as dienthoai, kh.lat, kh.long, isnull(dk.ghiChu, '') as ghiChu, dk.ImageFilePath    " +
							 "from KhachHang kh  left join DKTrungBay_KhachHang dk on kh.pk_seq = dk.khachhang_fk  " +
							 "where kh.npp_fk = '" + this.nppId + "' and lat is not null and long is not null   " +
							 			"and dk.dktrungbay_fk in ( select pk_seq from dangkytrungbay where cttrungbay_fk = '" + this.cttbId + "' and trangthai != 2 and npp_fk = '" + this.nppId + "' )  and  dk.ImageFilePath is not null  ";
				
				if(this.ddkdId.trim().length() > 0)
				{
					sql += " and kh.pk_seq in ( select khachhang_fk from khachhang_tuyenbh where tbh_fk in (select pk_seq from tuyenbanhang where ddkd_fk = '" + this.ddkdId + "') ) ";
				}
				
				if(this.tbhId.trim().length() > 0)
				{
					sql += " and kh.pk_seq in ( select khachhang_fk from khachhang_tuyenbh where tbh_fk = '" + this.tbhId + "' ) ";
				}
				
				
				System.out.println("1.Khoi tao khach hang trung bay: " + sql);
				this.khList = db.get(sql);
				
	
				//Khoi tao toa do Center dau
				String query = "select top(1) pk_seq, lat + ',' + long  as trungtam " +
						"from KhachHang where trangthai = '1' and lat is not null and long is not null and npp_fk = '" + this.nppId + "'";
				
				if(this.ddkdId.trim().length() > 0)
				{
					query += " and pk_seq in ( select khachhang_fk from khachhang_tuyenbh where tbh_fk in (select pk_seq from tuyenbanhang where ddkd_fk = '" + this.ddkdId + "') ) ";
				}
				
				if(this.tbhId.trim().length() > 0)
				{
					query += " and pk_seq in (select khachhang_fk from khachhang_tuyenbh where tbh_fk = '" + this.tbhId + "') ";
				}
				
				if(this.cttbId.trim().length() > 0)
				{
					sql += " and pk_seq in ( select khachhang_fk from dktrungbay_khachhang where dktrungbay_fk in ( select pk_seq from dangkytrungbay where npp_fk = '" + this.nppId + "' and trangthai != '2' and cttrungbay_fk = '" + this.cttbId + "' )  ) ";
				}
				
				ResultSet rsCenter = db.get(query);
				if(rsCenter != null)
				{
					try 
					{
						if(rsCenter.next())
						{
							this.trungtam = rsCenter.getString("trungtam");
						}
						rsCenter.close();
					} 
					catch (Exception e) 
					{
						System.out.println("115.Exception: " + e.getMessage());
					}
				}
			}
		
		}
	}
	

	
	private String getMonth() {
		DateFormat dateFormat = new SimpleDateFormat("MM");
        Date date = new Date();
        return dateFormat.format(date);
	}
	
	private String getYear() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy");
        Date date = new Date();
        return dateFormat.format(date);
	}

	
	public String getMsg() {
		return this.msg;
	}

	
	public void setMsg(String msg) {
		this.msg = msg;
	}

	
	public String getKhachHangSi() {
		return this.khSi;
	}

	
	public void setKhachHangSi(String khSi) {
		this.khSi = khSi;
	}

	public String[] getLatcondition() {
		
		return this.latcondition;
	}

	
	public void setLatcondition(String[] latcondition) {
		
		this.latcondition = latcondition;
	}

	
	public String[] getLongconditon() {
		
		return this.longcondition;
	}

	
	public void setLongcondition(String[] longcondition) {
		
		this.longcondition = longcondition;
	}

	
	public ResultSet getInfoRs() {
		
		return this.infoRs;
	}

	
	public void setInfoRs(ResultSet infoRs) {
		
		this.infoRs = infoRs;
	}

	
	public ResultSet getDpNganhRs() {
		
		return this.dpNganhRs;
	}

	
	public void setDpNganhRs(ResultSet dpRs) {
		
		this.dpNganhRs = dpRs;
	}

	
	public ResultSet getInfoDetailRs() {
		
		return this.infoDetail;
	}

	
	public void setInfoDetailRs(ResultSet infoDetailRs) {
		
		this.infoDetail = infoDetailRs;
	}

	
	public ResultSet getKiemtkRs() {

		return this.kiemTKRs;
	}

	
	public void setKiemtkRs(ResultSet kiemtkRs) {
		
		this.kiemTKRs = kiemtkRs;
	}

	@Override
	public String getMafast() {
		return this.mafast;
	}

	@Override
	public void setMafast(String mafast) {
		this.mafast=mafast;
		
	}
	
	String loaiNv,phanloai;
	public String getLoaiNv()
  {
  	return loaiNv;
  }

	public void setLoaiNv(String loaiNv)
  {
  	this.loaiNv = loaiNv;
  }

	public String getPhanloai()
  {
  	return phanloai;
  }

	public void setPhanloai(String phanloai)
  {
  	this.phanloai = phanloai;
  }
	
	String ttId;
	ResultSet ttRs;
	public String getTtId()
  {
  	return ttId;
  }

	public void setTtId(String ttId)
  {
  	this.ttId = ttId;
  }

	public ResultSet getTtRs()
  {
  	return ttRs;
  }

	public void setTtRs(ResultSet ttRs)
  {
  	this.ttRs = ttRs;
  }
	
	public String action;
	public String getAction()
  {
  	return action;
  }

	public void setAction(String action)
  {
  	this.action = action;
  }
	
	
	public ResultSet getNvRs() 
	{
		return this.nvList;
	}

	public void setNvRs(ResultSet nvList) 
	{
		this.nvList = nvList;
	}

	public String getNvId() 
	{
		return this.nvId;
	}

	public void setNvId(String nvId) 
	{
		this.nvId = nvId;
	}
	
	
}
