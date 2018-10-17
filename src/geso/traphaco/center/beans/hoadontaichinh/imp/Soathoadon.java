package geso.traphaco.center.beans.hoadontaichinh.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import geso.traphaco.center.util.Utility;
import geso.traphaco.center.beans.hoadontaichinh.ISoathoadon;
import geso.traphaco.center.db.sql.dbutils;

public class Soathoadon implements ISoathoadon
{
	private static final long serialVersionUID = 1L;
	String userId;
	String tungay;
	String denngay;
	String trangthai;
	String ptVAT;

	String sohoadontu;
	String sohoadonden;
	
	String nppTen;
	String msg;
	
	ResultSet nppRs;
	ResultSet hoadonRs;
	
	String khTen;
	ResultSet khRs;
	String nppId;
	String kyhieu;
	
	double tongtientruocthue;
	double tongtienthue;
	double tongtienhoadon;
	int TongSLHD;
	
	String nvbhId;
	ResultSet nvbhRs;
	ResultSet KyhieuHDRs;
	String maFAST;
	
	String sohoadon;
	String loaidonhang;
	
	dbutils db;
	Boolean isHO;
	String loaihd;
	public String getLoaihd() {
		return loaihd;
	}

	public void setLoaihd(String loaihd) {
		this.loaihd = loaihd;
	}

	public Boolean getIsHO() {
		return isHO;
	}

	public void setIsHO(Boolean isHO) {
		this.isHO = isHO;
	}

	public Soathoadon()
	{
		//this.tungay = this.getDateTime().split("-")[0] + "-" + this.getDateTime().split("-")[1] + "-01";
		//this.denngay = this.getDateTime();
		
		this.tungay = "";
		this.denngay = "";
		this.nppTen = "";
		this.trangthai = "";
		this.msg = "";
		this.loaidonhang = "0";
	    this.khTen= "";
	    this.nppId="";
	    this.sohoadon="";
	    this.ptVAT = "";
	    this.kyhieu="";
	    this.nvbhId = "";
	    this.maFAST = "";
	    
	    this.tongtientruocthue = 0;
	    this.tongtienthue = 0;
	    this.tongtienhoadon = 0;
	    this.TongSLHD = 0;
	    
	    this.sohoadontu = "";
	    this.sohoadonden = "";
		this.isHO=false;
		this.db = new dbutils();
		this.loaihd="";
	}

	public String getNppId()
	{
		return this.nppId;
	}

	public void setNppId(String nppId) 
	{
		this.nppId = nppId;
	}
	
	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
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

	private void getNppInfo()
	{		
		//Phien ban moi
		geso.traphaco.distributor.util.Utility util=new geso.traphaco.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
		System.out.println("nha phan phoi la"+this.nppId);
	}
	
	public void init()
	{
		this.getNppInfo();
		String info=" select count(*) as sl from nhanvien nv where nv.PHANLOAI=2  and ISNULL(nv.LOAI,0) not in(3,4) and nv.pk_seq='"+this.userId+"'";
		
		System.out.println("info "+info);
		ResultSet checkinfo=db.get(info);
		int kq=0;
		try {
			while(checkinfo.next())
			{
				kq=checkinfo.getInt("sl");
				this.isHO=true;
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Utility util = new Utility();
		if(this.tungay.trim().length() > 0 || this.denngay.trim().length() > 0 || this.sohoadontu.trim().length() > 0 || this.sohoadonden.trim().length() > 0 )
		{
			String query="";
			System.out.println("ket qua la "+kq);
			if(kq==1)
			{
				if(this.tungay.trim().length() > 0)
					query += " and a.ngayxuatHD >= '" + this.tungay + "' ";
				if(this.denngay.trim().length() > 0)
					query += " and a.ngayxuatHD <= '" + this.denngay + "' ";
				if(this.khTen.trim().length() > 0)
					query += " and b.PK_SEQ = '" + this.khTen + "' ";
				if(this.sohoadontu.trim().length() > 0)
					query += " and cast(a.sohoadon as numeric(18, 0)) >= '" + Double.parseDouble(this.sohoadontu) + "' ";
				if(this.sohoadonden.trim().length() > 0)
					query += " and cast(a.sohoadon as numeric(18, 0)) <= '" + Double.parseDouble(this.sohoadonden) + "' ";
				if(this.trangthai.trim().length() > 0)
					query += " and a.trangthai = '" + this.trangthai + "' ";
				if(this.ptVAT.trim().length() > 0)
					query += " and a.pk_seq in ( select hoadon_fk from erp_HOADON_SP where thuesuat = '" + this.ptVAT + "' ) ";			
				if(this.kyhieu.trim().length() > 0)
					query += " and a.KYHIEU = N'" + this.kyhieu.trim() + "' ";
				if(this.loaihd.trim().length() > 0)
				{
					if(this.loaihd.trim().equals("1"))
						query += " and d.loaidonhang = 0 and d.isETC=0 ";
					if(this.loaihd.trim().equals("2"))
						query += " and d.loaidonhang in (2,3) ";
					if(this.loaihd.trim().equals("3"))
						query += " and d.loaidonhang = 4  ";
					
				}
				 String query_total="select distinct a.PK_SEQ,a.NGAYXUATHD,a.SOHOADON,a.NPP_FK as MaFast,b.TEN as KHTEN,a.KYHIEU,a.TRANGTHAI, \n"+
							"(   select	round((AAA.giagoc + AAA.Vat - AAA.chietkhau),0) as TongtiensauVat  \n"+
							"			from ( \n"+
							"				select aa.HOADON_FK,SUM(round((dd.SOLUONG*aa.DONGIA)-isnull(dd.CHIETKHAU,0),0)) as giagoc,SUM(round(round((dd.SOLUONG*aa.DONGIA)-isnull(dd.CHIETKHAU,0),0)*dd.THUEVAT/100.0,0)) as Vat, \n"+
							"				SUM(isnull(aa.CHIETKHAU,0)) as chietkhau \n"+
							"				from ERP_HOADON_SP aa  \n"+
							"				inner Join ERP_SANPHAM bb on aa.SANPHAM_FK = bb.PK_SEQ \n"+
							" inner join  ( select hoadon_fk,MA,SOLO,NGAYHETHAN,THUEVAT,CHIETKHAU ,sum (SOLUONG) as SOLUONG \n"+
							"		   from ERP_HOADON_SP_CHITIET  \n"+
							"		   group by hoadon_fk,MA,SOLO,NGAYHETHAN,THUEVAT,CHIETKHAU ) as dd on dd.MA=bb.MA and dd.hoadon_fk=aa.HOADON_FK \n"+
							"			WHERE aa.HOADON_FK IN (SELECT PK_SEQ FROM ERP_HOADON WHERE TRANGTHAI NOT IN (3,5)) \n"+		
							"				  group by aa.HOADON_FK)  as AAA where AAA.HOADON_FK=a.PK_SEQ) as tongtienAVAT, \n"+
							" (select	ROUND(AAA.Vat,0) as TienVAT \n " +
							"			from ( \n"+
							"				select aa.HOADON_FK,SUM(round((dd.SOLUONG*aa.DONGIA)-isnull(dd.CHIETKHAU,0),0)) as giagoc,SUM(round(round((dd.SOLUONG*aa.DONGIA)-isnull(dd.CHIETKHAU,0),0)*dd.THUEVAT/100.0,0)) as Vat, \n"+
							"				SUM(isnull(aa.CHIETKHAU,0)) as chietkhau \n"+
							"				from ERP_HOADON_SP aa  \n"+
							"				inner Join ERP_SANPHAM bb on aa.SANPHAM_FK = bb.PK_SEQ  \n"+
							" inner join  ( select hoadon_fk,MA,SOLO,NGAYHETHAN,THUEVAT,CHIETKHAU ,sum (SOLUONG) as SOLUONG \n"+
							"		   from ERP_HOADON_SP_CHITIET  \n"+
							"		   group by hoadon_fk,MA,SOLO,NGAYHETHAN,THUEVAT,CHIETKHAU ) as dd on dd.MA=bb.MA and dd.hoadon_fk=aa.HOADON_FK \n"+
							"			WHERE aa.HOADON_FK IN (SELECT PK_SEQ FROM ERP_HOADON WHERE TRANGTHAI NOT IN (3,5)) \n"+	
							"				  group by aa.HOADON_FK)  as AAA where AAA.HOADON_FK=a.PK_SEQ) as VAT, \n"+
							"  (select	ROUND(AAA.chietkhau,0) as TienCK \n"+
							"			from ( \n"+
							"				select aa.HOADON_FK,SUM(round((dd.SOLUONG*aa.DONGIA)-isnull(dd.CHIETKHAU,0),0)) as giagoc,SUM(round(round((dd.SOLUONG*aa.DONGIA)-isnull(dd.CHIETKHAU,0),0)*dd.THUEVAT/100.0,0)) as Vat, \n"+
							"				SUM(isnull(aa.CHIETKHAU,0)) as chietkhau \n"+
							"				from ERP_HOADON_SP aa  \n"+
							"				inner Join ERP_SANPHAM bb on aa.SANPHAM_FK = bb.PK_SEQ  \n"+
							" inner join  ( select hoadon_fk,MA,SOLO,NGAYHETHAN,THUEVAT,CHIETKHAU ,sum (SOLUONG) as SOLUONG \n"+
							"		   from ERP_HOADON_SP_CHITIET  \n"+
							"		   group by hoadon_fk,MA,SOLO,NGAYHETHAN,THUEVAT,CHIETKHAU ) as dd on dd.MA=bb.MA and dd.hoadon_fk=aa.HOADON_FK \n"+
							"			WHERE aa.HOADON_FK IN (SELECT PK_SEQ FROM ERP_HOADON WHERE TRANGTHAI NOT IN (3,5)) \n"+	
							"				  group by aa.HOADON_FK)  as AAA where AAA.HOADON_FK=a.PK_SEQ) as TienCK, \n"+
							" (select ROUND(AAA.giagoc,0) as Giagoc \n"+
							"		from ( \n"+
							"			select aa.HOADON_FK,SUM(round((dd.SOLUONG*aa.DONGIA)-isnull(dd.CHIETKHAU,0),0)) as giagoc,SUM(round(round((dd.SOLUONG*aa.DONGIA)-isnull(dd.CHIETKHAU,0),0)*dd.THUEVAT/100.0,0)) as Vat, \n"+
							"			SUM(isnull(aa.CHIETKHAU,0)) as chietkhau \n"+
							"			from ERP_HOADON_SP aa  \n"+
							"			inner Join ERP_SANPHAM bb on aa.SANPHAM_FK = bb.PK_SEQ \n"+
							" inner join  ( select hoadon_fk,MA,SOLO,NGAYHETHAN,THUEVAT,CHIETKHAU ,sum (SOLUONG) as SOLUONG \n"+
							"		   from ERP_HOADON_SP_CHITIET  \n"+
							"		   group by hoadon_fk,MA,SOLO,NGAYHETHAN,THUEVAT,CHIETKHAU ) as dd on dd.MA=bb.MA and dd.hoadon_fk=aa.HOADON_FK \n"+
							"			WHERE aa.HOADON_FK IN (SELECT PK_SEQ FROM ERP_HOADON WHERE TRANGTHAI NOT IN (3,5)) \n"+	
							"			  group by aa.HOADON_FK)  as AAA where AAA.HOADON_FK=a.PK_SEQ) as tongtienBVAT \n"+	
							"\n	,N'HĐ HO' as loaiHD, 'HO' as type  " +
							"  from erp_hoadon a \n"+
							"   inner join NHAPHANPHOI b on  a.NPP_FK=b.PK_SEQ \n"+
							"  inner join ERP_HOADON_DDH c on a.PK_SEQ=c.HOADON_FK"+
							"  inner join ERP_DONDATHANG d on d.PK_SEQ=c.DDH_FK"+
							"   where a.TRANGTHAI NOT IN (3,5) " +query;
				 this.hoadonRs = db.get(query_total);
				 System.out.println("truy van "+query_total);
				 ResultSet hdRs1 = db.get(query_total);
					if(hdRs1!= null)
					{
					  try
					  {
						while(hdRs1.next())
						{
							this.tongtientruocthue += hdRs1.getDouble("tongtienBVAT");
							this.tongtienthue += hdRs1.getDouble("VAT");
							this.tongtienhoadon+= hdRs1.getDouble("tongtienAVAT");
							if(!hdRs1.getString("pk_seq").equals(""))
							{
								this.TongSLHD = this.TongSLHD + 1;
							}
						}
						hdRs1.close();
					  }catch (Exception e ){e.printStackTrace();}
					}
				
			}
			else
			{
			 query =  "select a.PK_SEQ, a.sohoadon, cast(sohoadon as numeric(18, 0)) as sohoadonSORT, a.kyhieu, a.trangthai, a.ngayxuatHD, KH.MAFAST as MAFAST, KH.TEN as khTEN, a.tongtienBVAT - isnull(a.chietkhau, 0) as tongtienBVAT, a.VAT, a.tongtienAVAT, " +
							"\n	case a.loaihoadon when 0 then N'HĐ hàng bán' else N'HĐ hàng khuyến mại' end as loaiHD, 'OTC' as type   " +
							"\n from HOADON a  " +
							"\n inner join KHACHHANG KH on a.KHACHHANG_FK = KH.PK_SEQ  " +
							"\n where  a.SoHoaDon!='NA' and ISNUMERIC( a.SOHOADON)= 1  and  a.NPP_FK = '" + this.nppId + "' and a.trangthai not in ( 3, 5 ) and a.kho_fk in "+util.quyen_kho(this.userId) ;
			
			if(this.tungay.trim().length() > 0)
				query += " and a.ngayxuatHD >= '" + this.tungay + "' ";
			if(this.denngay.trim().length() > 0)
				query += " and a.ngayxuatHD <= '" + this.denngay + "' ";
			if(this.khTen.trim().length() > 0)
				query += " and KH.PK_SEQ = '" + this.khTen + "' ";
			if(this.sohoadontu.trim().length() > 0)
				query += " and cast(a.sohoadon as numeric(18, 0)) >= '" + Double.parseDouble(this.sohoadontu) + "' ";
			if(this.sohoadonden.trim().length() > 0)
				query += " and cast(a.sohoadon as numeric(18, 0)) <= '" + Double.parseDouble(this.sohoadonden) + "' ";
			if(this.trangthai.trim().length() > 0)
				query += " and a.trangthai = '" + this.trangthai + "' ";
			if(this.ptVAT.trim().length() > 0)
				query += " and a.pk_seq in ( select HOADON_FK from HOADON_SP where VAT = '" + this.ptVAT + "' ) ";			
			if(this.nvbhId.trim().length() > 0)
				query += " and a.pk_seq in ( select hoadon_fk from HOADON_DDH where DDH_FK in ( select pk_seq from DONHANG where DDKD_FK = '" + this.nvbhId + "' ) ) ";
			if(this.maFAST.trim().length() > 0)
				query += " and KH.maFAST = N'" + this.maFAST.trim() + "' ";
			if(this.kyhieu.trim().length() > 0)
				query += " and a.KYHIEU = N'" + this.kyhieu.trim() + "' ";
			
			//GOP CHUNG ETC VA OTC
			String queryETC =   "select a.PK_SEQ, a.sohoadon, cast(sohoadon as numeric(18, 0)) as sohoadonSORT, a.kyhieu, a.trangthai, a.ngayxuatHD,   " +
								"\n	case when a.NPP_DAT_FK is not null then npp.maFAST else KH.maFAST end as MAFAST, " +
								"\n	case when a.NPP_DAT_FK is not null then npp.TEN else KH.TEN end as khTEN, a.tongtienBVAT - isnull(a.chietkhau, 0) as tongtienBVAT, a.VAT, a.tongtienAVAT, " +
								"\n	N'HĐ hàng bán' as loaiHD, 'ETC' as type  " +
								"\n from ERP_HOADONNPP a  " +
								"\n	left join KHACHHANG KH on a.KHACHHANG_FK = KH.PK_SEQ   " +
								"\n	left join NHAPHANPHOI npp on a.NPP_DAT_FK = npp.PK_SEQ   " +
								"\n where   a.SoHoaDon!='NA'  and ISNUMERIC( a.SOHOADON)= 1  and   a.NPP_FK = '" + this.nppId + "' and a.trangthai not in ( 3, 5 ) and a.kho_fk in "+util.quyen_kho(this.userId) ;
			
			if(this.tungay.trim().length() > 0)
				queryETC += " and a.ngayxuatHD >= '" + this.tungay + "' ";
			if(this.denngay.trim().length() > 0)
				queryETC += " and a.ngayxuatHD <= '" + this.denngay + "' ";
			/*if(this.khTen.trim().length() > 0)
				query += " and KH.PK_SEQ = '" + this.khTen + "' ";*/
			if(this.sohoadontu.trim().length() > 0)
				queryETC += " and cast(a.sohoadon as numeric(18, 0)) >= '" + Double.parseDouble(this.sohoadontu) + "' ";
			if(this.sohoadonden.trim().length() > 0)
				queryETC += " and cast(a.sohoadon as numeric(18, 0)) <= '" + Double.parseDouble(this.sohoadonden) + "' ";
			if(this.trangthai.trim().length() > 0)
				queryETC += " and a.trangthai = '" + this.trangthai + "' ";
			if(this.ptVAT.trim().length() > 0)
				queryETC += " and a.pk_seq in ( select HOADON_FK from ERP_HOADONNPP_SP where VAT = '" + this.ptVAT + "' ) ";
			if(this.khTen.trim().length()>0)
				queryETC += " and KH.PK_SEQ = '"+this.khTen +"' ";
			if(this.nvbhId.trim().length() > 0)
				queryETC += " and a.pk_seq in ( select hoadon_fk from HOADON_DDH where DDH_FK in ( select pk_seq from DONHANG where DDKD_FK = '" + this.nvbhId + "' ) ) ";
			if(this.maFAST.trim().length() > 0)
				queryETC += " and KH.maFAST = N'" + this.maFAST.trim() + "' ";
			if(this.kyhieu.trim().length() > 0)
				queryETC += " and a.KYHIEU = N'" + this.kyhieu.trim() + "' ";
			
			//query += " order by cast(a.sohoadon as numeric(18, 0)) asc ";
			
			query = query + " union ALL " + queryETC;
			
			//HOADON KHAC
			
			String queryHDKhac = 
				" SELECT a.pk_seq, a.sohoadon,  cast(sohoadon as numeric(18, 0)) as sohoadonSORT, a.kyhieuhoadon kyhieu, a.trangthai, a.ngayhoadon ngayxuatHD,\n "+
				"		 b.maFAST, b.TEN khTEN, a.bvat, a.vat, a.avat, N'HĐ khác' as loaiHD, 'HĐ Khac' as type \n"+
				" FROM ERP_HoaDonPheLieu a \n"+ 
				"      inner join KHACHHANG b on a.khachhang_fk = b.PK_SEQ \n"+
				" WHERE a.SOHOADON!='NA' and ISNUMERIC( a.SOHOADON)= 1  and a.npp_fk ='"+this.nppId+"' and a.trangthai NOT IN (2) \n";
			if(this.tungay.trim().length() > 0)
				queryHDKhac += " and a.ngayhoadon >= '" + this.tungay + "' ";
			if(this.denngay.trim().length() > 0)
				queryHDKhac += " and a.ngayhoadon <= '" + this.denngay + "' ";
			if(this.sohoadontu.trim().length() > 0)
				queryHDKhac += " and cast(a.sohoadon as numeric(18, 0)) >= '" + Double.parseDouble(this.sohoadontu) + "' ";
			if(this.sohoadonden.trim().length() > 0)
				queryHDKhac += " and cast(a.sohoadon as numeric(18, 0)) <= '" + Double.parseDouble(this.sohoadonden) + "' ";
			if(this.trangthai.trim().length() > 0)
				queryHDKhac += " and a.trangthai = '" + this.trangthai + "' ";
			if(this.ptVAT.trim().length() > 0)
				queryHDKhac += " and a.pk_seq in ( select HOADONPHELIEU_FK from erp_hoadonphelieu_sanpham where thuevat = '" + this.ptVAT + "' ) ";
			if(this.khTen.trim().length()>0)
				queryHDKhac += " and b.PK_SEQ = '"+this.khTen +"' ";
/*			if(this.nvbhId.trim().length() > 0)
				queryHDKhac += " and a.pk_seq in ( select hoadon_fk from HOADON_DDH where DDH_FK in ( select pk_seq from DONHANG where DDKD_FK = '" + this.nvbhId + "' ) ) ";*/
			if(this.maFAST.trim().length() > 0)
				queryHDKhac += " and b.maFAST = N'" + this.maFAST.trim() + "' ";
			if(this.kyhieu.trim().length() > 0)
				queryHDKhac += " and a.KYHIEU = N'" + this.kyhieu.trim() + "' ";
			
			if(nvbhId.trim().length()<=0)// hóa đơn khác k tìm nhân viên bán hàng
				query = query + " union ALL " + queryHDKhac;
			
			query += " order by sohoadonSORT asc ";
			
			System.out.println("---INIT HOA DON: " + query);
			System.out.println(this.nppId + this.nppTen);
			this.hoadonRs = db.get(query);
			
			ResultSet hdRs = db.get(query);
			if(hdRs!= null)
			{
			  try
			  {
				while(hdRs.next())
				{
					this.tongtientruocthue += hdRs.getDouble("tongtienBVAT");
					this.tongtienthue += hdRs.getDouble("VAT");
					this.tongtienhoadon+= hdRs.getDouble("tongtienAVAT");
					if(!hdRs.getString("pk_seq").equals(""))
					{
						this.TongSLHD = this.TongSLHD + 1;
					}
				}
				hdRs.close();
			  }catch (Exception e ){e.printStackTrace();}
			}
		}
		
		//System.out.println("--- LAY NVBH: " + "select  PK_SEQ, TEN AS TEN from DaiDienKinhDoanh where TRANGTHAI = '1' and NPP_FK = '" + this.nppId + "' ");
		this.nvbhRs = db.get("select  PK_SEQ, TEN AS TEN from DaiDienKinhDoanh where TRANGTHAI = '1' ");
		this.khRs = db.get("select  PK_SEQ, MAFAST + ', ' + TEN AS TEN from nhaphanphoi where TRANGTHAI = '1' and tructhuoc_fk=1 and loainpp=4 ");
		this.KyhieuHDRs = db.get("select distinct ISNULL(KYHIEU,'') as KYHIEU from ERP_HOADON where TRANGTHAI <> 3  ");
		}
	}
	
	public void initETC()
	{
		this.getNppInfo();

		Utility util = new Utility();
		if(this.tungay.trim().length() > 0 || this.denngay.trim().length() > 0 || this.sohoadontu.trim().length() > 0 || this.sohoadonden.trim().length() > 0 )
		{
			String query =  "select a.PK_SEQ, a.sohoadon, cast(sohoadon as numeric(18, 0)) as sohoadonSORT, a.kyhieu, a.trangthai, a.ngayxuatHD, KH.MAFAST as MAFAST, KH.TEN as khTEN, a.tongtienBVAT, a.VAT, a.tongtienAVAT, " +
							"	case a.loaihoadon when 0 then N'HĐ hàng bán' else N'HĐ hàng khuyến mại' end as loaiHD, 'OTC' as type   " +
							"from HOADON a  " +
							"inner join KHACHHANG KH on a.KHACHHANG_FK = KH.PK_SEQ  " +
							"where a.sohoadon != 'NA' and ISNUMERIC( a.SOHOADON)= 1  and a.NPP_FK = '" + this.nppId + "' and a.trangthai in ( 2, 4 )  and a.kho_fk in "+util.quyen_kho(this.userId)  ;
			
			if(this.tungay.trim().length() > 0)
				query += " and a.ngayxuatHD >= '" + this.tungay + "' ";
			if(this.denngay.trim().length() > 0)
				query += " and a.ngayxuatHD <= '" + this.denngay + "' ";
			if(this.khTen.trim().length() > 0)
				query += " and KH.PK_SEQ = '" + this.khTen + "' ";
			if(this.sohoadontu.trim().length() > 0)
				query += " and cast(a.sohoadon as numeric(18, 0)) >= '" + Double.parseDouble(this.sohoadontu) + "' ";
			if(this.sohoadonden.trim().length() > 0)
				query += " and cast(a.sohoadon as numeric(18, 0)) <= '" + Double.parseDouble(this.sohoadonden) + "' ";
			if(this.trangthai.trim().length() > 0)
				query += " and a.trangthai = '" + this.trangthai + "' ";
			if(this.ptVAT.trim().length() > 0)
				query += " and a.pk_seq in ( select HOADON_FK from HOADON_SP where VAT = '" + this.ptVAT + "' ) ";
			
			if(this.nvbhId.trim().length() > 0)
				query += " and a.pk_seq in ( select hoadon_fk from HOADON_DDH where DDH_FK in ( select pk_seq from DONHANG where DDKD_FK = '" + this.nvbhId + "' ) ) ";
			if(this.maFAST.trim().length() > 0)
				query += " and KH.maFAST = N'" + this.maFAST.trim() + "' ";
			if(this.kyhieu.trim().length() > 0)
				query += " and a.KYHIEU = N'" + this.kyhieu.trim() + "' ";
			
			//GOP CHUNG ETC VA OTC
			String queryETC =   "select a.PK_SEQ, a.sohoadon, cast(sohoadon as numeric(18, 0)) as sohoadonSORT, a.kyhieu, a.trangthai, a.ngayxuatHD,   " +
								"	case when a.NPP_DAT_FK is not null then npp.maFAST else KH.maFAST end as MAFAST, " +
								"	case when a.NPP_DAT_FK is not null then npp.TEN else KH.TEN end as khTEN, a.tongtienBVAT, a.VAT, a.tongtienAVAT, " +
								"	N'HĐ hàng bán' as loaiHD, 'ETC' as type  " +
								"from ERP_HOADONNPP a  " +
								"	left join KHACHHANG KH on a.KHACHHANG_FK = KH.PK_SEQ   " +
								"	left join NHAPHANPHOI npp on a.NPP_DAT_FK = npp.PK_SEQ   " +
								"where a.sohoadon != 'NA' and ISNUMERIC( a.SOHOADON)= 1  and a.NPP_FK = '" + this.nppId + "' and a.trangthai in ( 2, 4 )  and a.kho_fk in "+util.quyen_kho(this.userId) ;
			
			if(this.tungay.trim().length() > 0)
				queryETC += " and a.ngayxuatHD >= '" + this.tungay + "' ";
			if(this.denngay.trim().length() > 0)
				queryETC += " and a.ngayxuatHD <= '" + this.denngay + "' ";
			/*if(this.khTen.trim().length() > 0)
				query += " and KH.PK_SEQ = '" + this.khTen + "' ";*/
			if(this.sohoadontu.trim().length() > 0)
				queryETC += " and cast(a.sohoadon as numeric(18, 0)) >= '" + Double.parseDouble(this.sohoadontu) + "' ";
			if(this.sohoadonden.trim().length() > 0)
				queryETC += " and cast(a.sohoadon as numeric(18, 0)) <= '" + Double.parseDouble(this.sohoadonden) + "' ";
			if(this.trangthai.trim().length() > 0)
				queryETC += " and a.trangthai = '" + this.trangthai + "' ";
			if(this.ptVAT.trim().length() > 0)
				queryETC += " and a.pk_seq in ( select HOADON_FK from ERP_HOADONNPP_SP where VAT = '" + this.ptVAT + "' ) ";
			
			if(this.nvbhId.trim().length() > 0)
				queryETC += " and a.pk_seq in ( select hoadon_fk from HOADON_DDH where DDH_FK in ( select pk_seq from DONHANG where DDKD_FK = '" + this.nvbhId + "' ) ) ";
			if(this.maFAST.trim().length() > 0)
				queryETC += " and KH.maFAST = N'" + this.maFAST.trim() + "' ";
			if(this.kyhieu.trim().length() > 0)
				queryETC += " and a.KYHIEU = N'" + this.kyhieu.trim() + "' ";
			
			//query += " order by cast(a.sohoadon as numeric(18, 0)) asc ";
			
			query = query + " union ALL " + queryETC;
			
			//HOADON KHAC
			
			String queryHDKhac = 
				" SELECT a.pk_seq, a.sohoadon,  cast(sohoadon as numeric(18, 0)) as sohoadonSORT, a.kyhieuhoadon kyhieu, a.trangthai, a.ngayhoadon ngayxuatHD,\n "+
				"		 b.maFAST, b.TEN khTEN, a.bvat, a.vat, a.avat, N'HĐ khác' as loaiHD, 'HĐ Khac' as type \n"+
				" FROM ERP_HoaDonPheLieu a \n"+ 
				"      inner join KHACHHANG b on a.khachhang_fk = b.PK_SEQ \n"+
				" WHERE a.SOHOADON!='NA' and ISNUMERIC( a.SOHOADON)= 1  and  a.npp_fk ='"+this.nppId+"' and a.trangthai NOT IN (2) \n";
			if(this.tungay.trim().length() > 0)
				queryHDKhac += " and a.ngayhoadon >= '" + this.tungay + "' ";
			if(this.denngay.trim().length() > 0)
				queryHDKhac += " and a.ngayhoadon <= '" + this.denngay + "' ";
			/*if(this.khTen.trim().length() > 0)
				query += " and KH.PK_SEQ = '" + this.khTen + "' ";*/
			if(this.sohoadontu.trim().length() > 0)
				queryHDKhac += " and cast(a.sohoadon as numeric(18, 0)) >= '" + Double.parseDouble(this.sohoadontu) + "' ";
			if(this.sohoadonden.trim().length() > 0)
				queryHDKhac += " and cast(a.sohoadon as numeric(18, 0)) <= '" + Double.parseDouble(this.sohoadonden) + "' ";
			if(this.trangthai.trim().length() > 0)
				queryHDKhac += " and a.trangthai = '" + this.trangthai + "' ";
			if(this.ptVAT.trim().length() > 0)
				queryHDKhac += " and a.pk_seq in ( select HOADONPHELIEU_FK from erp_hoadonphelieu_sanpham where thuevat = '" + this.ptVAT + "' ) ";
			if(this.khTen.trim().length()>0)
				queryHDKhac += " and b.PK_SEQ = '"+this.khTen +"' ";
/*			if(this.nvbhId.trim().length() > 0)
				queryHDKhac += " and a.pk_seq in ( select hoadon_fk from HOADON_DDH where DDH_FK in ( select pk_seq from DONHANG where DDKD_FK = '" + this.nvbhId + "' ) ) ";*/
			if(this.maFAST.trim().length() > 0)
				queryHDKhac += " and b.maFAST = N'" + this.maFAST.trim() + "' ";
			if(this.kyhieu.trim().length() > 0)
				queryHDKhac += " and a.KYHIEU = N'" + this.kyhieu.trim() + "' ";
			
			if(nvbhId.trim().length()<=0)// hóa đơn khác k tìm nhân viên bán hàng
				query = query + " union ALL " + queryHDKhac;
			
			query += " order by sohoadonSORT asc ";
						
			System.out.println("---INIT HOA DON: " + query);
			this.hoadonRs = db.get(query);
			
			ResultSet hdRs = db.get(query);
			if(hdRs!= null)
			{
			  try
			  {
				while(hdRs.next())
				{
					this.tongtientruocthue += hdRs.getDouble("tongtienBVAT");
					this.tongtienthue += hdRs.getDouble("VAT");
					this.tongtienhoadon+= hdRs.getDouble("tongtienAVAT");
					if(!hdRs.getString("pk_seq").equals(""))
					{
						this.TongSLHD = this.TongSLHD + 1;
					}
				}
				hdRs.close();
			  }
			  catch (Exception e ){e.printStackTrace();}
			}
		}
		
		System.out.println("--- LAY NVBH: " + "select  PK_SEQ, TEN AS TEN from DaiDienKinhDoanh where TRANGTHAI = '1' and NPP_FK = '" + this.nppId + "' ");
		this.nvbhRs = db.get("select  PK_SEQ, TEN AS TEN from DaiDienKinhDoanh where TRANGTHAI = '1' and NPP_FK = '" + this.nppId + "' ");
		this.khRs = db.get("select  PK_SEQ, MAFAST + ', ' + TEN AS TEN from KHACHHANG where TRANGTHAI = '1' and NPP_FK = '" + this.nppId + "' ");
		this.KyhieuHDRs = db.get("select distinct ISNULL(KYHIEU,'') as KYHIEU from HOADON where TRANGTHAI <> 3 and NPP_FK = '" + this.nppId + "' ");
		
	}
	
	public void DBclose() 
	{
		this.db.shutDown();
	}

	public ResultSet getHoadonRs() 
	{
		return this.hoadonRs;
	}

	public void setHoadonRs(ResultSet hdRs) 
	{
		this.hoadonRs = hdRs;
	}

	
	public String getTungay() {
		
		return this.tungay;
	}

	
	public void setTungay(String tungay) {
		
		this.tungay = tungay;
	}

	
	public String getDenngay() {
		
		return this.denngay;
	}

	
	public void setDenngay(String denngay) {
		
		this.denngay = denngay;
	}

	
	public String getNppTen() {
		
		return this.nppTen;
	}

	
	public void setNppTen(String nppTen) {
		
		this.nppTen = nppTen;
	}

	
	public ResultSet getNppRs() {
		
		return this.nppRs;
	}

	
	public void setNppRs(ResultSet nppRs) {
		
		this.nppRs = nppRs;
	}

	
	public String getLoaidonhang() {

		return this.loaidonhang;
	}


	public void setLoaidonhang(String loaidonhang) {
		
		this.loaidonhang = loaidonhang;
	}

	
	public String getKhTen() {
		return this.khTen;
	}

	
	public void setKhTen(String KhTen) {
		this.khTen = KhTen;
		
	}

	
	public ResultSet getKhRs() {
		return this.khRs;
	}

	
	public void setKhRs(ResultSet KhRs) {
		this.khRs = KhRs;
		
	}
	
	public String getSoHoaDon()
	{
		return this.sohoadon;
	}
	public void setSoHoaDon(String sohoadon)
	{
		this.sohoadon =sohoadon;
	}

	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	

	public boolean save(HttpServletRequest request) 
	{
		this.getNppInfo();
		String info="  select count(*) as sl from nhanvien nv where nv.PK_SEQ="+this.userId+" and nv.PHANLOAI=2 and nv.PK_SEQ not in ("+
				 " select pk_seq from nhanvien where "+
				  "  LOAI  in (1,2,5)  and PHANLOAI =  2 ) ";
			System.out.println("info "+info);
			ResultSet checkinfo=db.get(info);
			int kq=0;
			try {
				while(checkinfo.next())
				{
					kq=checkinfo.getInt("sl");
					this.isHO=true;
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			String[] mahoadon = request.getParameterValues("mahoadon");
			String[] type = request.getParameterValues("type");
			String[] sohoadon = request.getParameterValues("sohoadon");
			String[] kyhieu = request.getParameterValues("kyhieu");
			String[] ngayhoadon = request.getParameterValues("ngayhoadon");
			if(kq==1)
			{
				System.out.println("da vao day");
				String query = "";

				if(mahoadon != null )
				{
					for(int i = 0; i < mahoadon.length; i++)
					{
						
							query = "Update ERP_HOADON set sohoadon = '" + sohoadon[i] + "', kyhieu = '" + kyhieu[i] + "' , ngayxuathd = '"+ ngayhoadon[i] +"' where pk_seq = '" + mahoadon[i] + "' ";
							System.out.println("--SOAT HOA DON: " + query);
							
							if(!db.update(query))
							{
								this.msg = "Lỗi khi soát hóa đơn ( " + mahoadon[i] + " ) : " + query;
								db.getConnection().rollback();
								return false;
							}	
					}
					
					//CHECK XEM SO SAU KHI SOAT CO BI TRUNG KHONG
					/*query = "select sohoadon from HOADON " +
							"where NPP_FK = '" + this.nppId + "' and trangthai not in ( 3, 5 ) " +
							"group by sohoadon, kyhieu having count(sohoadon) >= 2 ";*/
										
					query = 
					"select sohoadon  " +
					"from " +
					"( " +
					"	select sohoadon, kyhieu, MAUHOADON from HOADON  " +
					"	where  trangthai != 3 and sohoadon != 'NA' and npp_fk = " +this.nppId+ 
					"union ALL " +
					"	select sohoadon, kyhieu, MAUHOADON from ERP_HOADONNPP  " +
					"	where  trangthai != 3 and sohoadon != 'NA' and npp_fk = " +this.nppId+ 
					/*"union ALL " +
					"	select sohoadon, kyhieuhoadon kyhieu from ERP_HOADONPHELIEU  " +
					"	where NPP_FK = '" + this.nppId + "' and trangthai != 2 and sohoadon != 'NA'  " +*/
					") " +
					"checkTRUNG " +
					"group by sohoadon, kyhieu, MAUHOADON  " +
					"having count(*) >= 2 ";
					
					System.out.println("---LENH CHECK TRUNG new: " + query );
					ResultSet rsCHECK = db.get(query);
					if(rsCHECK != null)
					{
						String sohoadonTRUNG = "";
						while(rsCHECK.next())
						{
							sohoadonTRUNG += rsCHECK.getString("sohoadon").trim() + ", ";
						}
						rsCHECK.close();
						
						System.out.println("---SO HOA DON TRUNG: " + sohoadonTRUNG);
						if(sohoadonTRUNG.trim().length() > 0)
						{
							sohoadonTRUNG = sohoadonTRUNG.substring(0, sohoadonTRUNG.length() - 1);
							
							this.msg = "Các số hóa đơn sau: " + sohoadonTRUNG + " đã bị trùng trong hệ thống. Vui lòng kiểm tra lại";
							db.getConnection().rollback();
							return false;
						}
						
					}
				}
			}
			else
			{
			String query = "";

			if(mahoadon != null )
			{
				for(int i = 0; i < mahoadon.length; i++)
				{
					if(type[i].equals("OTC"))
					{
						query = "Update HOADON set sohoadon = '" + sohoadon[i] + "', kyhieu = '" + kyhieu[i] + "' , ngayxuathd = '"+ ngayhoadon[i] +"' where pk_seq = '" + mahoadon[i] + "' ";
						System.out.println("--SOAT HOA DON: " + query);
						
						if(!db.update(query))
						{
							this.msg = "Lỗi khi soát hóa đơn ( " + mahoadon[i] + " ) : " + query;
							db.getConnection().rollback();
							return false;
						}
					}
					else if(type[i].equals("ETC"))  //ETC
					{
						query = "Update ERP_HOADONNPP set sohoadon = '" + sohoadon[i] + "', kyhieu = '" + kyhieu[i] + "' , ngayxuathd = '"+ ngayhoadon[i] +"' where pk_seq = '" + mahoadon[i] + "' ";
						//System.out.println("--SOAT HOA DON: " + query);
						
						if(!db.update(query))
						{
							this.msg = "Lỗi khi soát hóa đơn ( " + mahoadon[i] + " ) : " + query;
							db.getConnection().rollback();
							return false;
						}
					}
					else{
						query = "Update ERP_HOADONPHELIEU set sohoadon = '" + sohoadon[i] + "', kyhieuhoadon = '" + kyhieu[i] + "' , ngayhoadon = '"+ ngayhoadon[i] +"' where pk_seq = '" + mahoadon[i] + "' ";
						//System.out.println("--SOAT HOA DON: " + query);
						
						if(!db.update(query))
						{
							this.msg = "Lỗi khi soát hóa đơn ( " + mahoadon[i] + " ) : " + query;
							db.getConnection().rollback();
							return false;
						}
					}
				}
				
				//CHECK XEM SO SAU KHI SOAT CO BI TRUNG KHONG
				/*query = "select sohoadon from HOADON " +
						"where NPP_FK = '" + this.nppId + "' and trangthai not in ( 3, 5 ) " +
						"group by sohoadon, kyhieu having count(sohoadon) >= 2 ";*/
								
				query = 
					"select sohoadon  " +
					"from " +
					"( " +
					"	select sohoadon, kyhieu, MAUHOADON from HOADON  " +
					"	where  trangthai != 3 and sohoadon != 'NA' and npp_fk = " +this.nppId+
					"union ALL " +
					"	select sohoadon, kyhieu, MAUHOADON from ERP_HOADONNPP  " +
					"	where  trangthai != 3 and sohoadon != 'NA' and npp_fk = " +this.nppId+
					/*"union ALL " +
					"	select sohoadon, kyhieuhoadon kyhieu from ERP_HOADONPHELIEU  " +
					"	where NPP_FK = '" + this.nppId + "' and trangthai != 2 and sohoadon != 'NA'  " +*/
					") " +
					"checkTRUNG " +
					"group by sohoadon, kyhieu, MAUHOADON  " +
					"having count(*) >= 2 ";
				
				System.out.println("---LENH CHECK TRUNG: " + query );
				ResultSet rsCHECK = db.get(query);
				if(rsCHECK != null)
				{
					String sohoadonTRUNG = "";
					while(rsCHECK.next())
					{
						sohoadonTRUNG += rsCHECK.getString("sohoadon").trim() + ", ";
					}
					rsCHECK.close();
					
					System.out.println("---SO HOA DON TRUNG: " + sohoadonTRUNG);
					if(sohoadonTRUNG.trim().length() > 0)
					{
						sohoadonTRUNG = sohoadonTRUNG.substring(0, sohoadonTRUNG.length() - 1);
						
						this.msg = "Các số hóa đơn sau: " + sohoadonTRUNG + " đã bị trùng trong hệ thống. Vui lòng kiểm tra lại";
						db.getConnection().rollback();
						return false;
					}
					
				}
			}
		}
			this.msg = "Soát số hóa đơn thành công.";
			db.getConnection().commit();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			db.update("rollback");
			this.msg = "Lỗi khi soát hóa đơn: " + e.getMessage();
			return false;
		}
		
		return true;
	}

	
	public boolean saveETC(HttpServletRequest request) 
	{
		this.getNppInfo();
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			String[] mahoadon = request.getParameterValues("mahoadon");
			String[] sohoadon = request.getParameterValues("sohoadon");
			String[] kyhieu = request.getParameterValues("kyhieu");
			String[] ngayhoadon = request.getParameterValues("ngayhoadon");
			
			String query = "";
			if(mahoadon != null)
			{
				for(int i = 0; i < mahoadon.length; i++)
				{
					query = "Update ERP_HOADONNPP set sohoadon = '" + sohoadon[i] + "', kyhieu = '" + kyhieu[i] + "' , ngayxuathd = '"+ ngayhoadon[i] +"' where pk_seq = '" + mahoadon[i] + "' ";
					//System.out.println("--SOAT HOA DON: " + query);
					
					if(!db.update(query))
					{
						this.msg = "Lỗi khi soát hóa đơn ( " + mahoadon[i] + " ) : " + query;
						db.getConnection().rollback();
						return false;
					}
				}
				
				//CHECK XEM SO SAU KHI SOAT CO BI TRUNG KHONG
				query = "select sohoadon from ERP_HOADONNPP " +
						"where NPP_FK = '" + this.nppId + "' and trangthai not in ( 3, 5 ) and sohoadon != 'NA' " +
						"group by sohoadon, kyhieu having count(sohoadon) >= 2 ";
				System.out.println("---LENH CHECK TRUNG: " + query );
				ResultSet rsCHECK = db.get(query);
				if(rsCHECK != null)
				{
					String sohoadonTRUNG = "";
					while(rsCHECK.next())
					{
						sohoadonTRUNG += rsCHECK.getString("sohoadon").trim() + ", ";
					}
					rsCHECK.close();
					
					System.out.println("---SO HOA DON TRUNG: " + sohoadonTRUNG);
					if(sohoadonTRUNG.trim().length() > 0)
					{
						sohoadonTRUNG = sohoadonTRUNG.substring(0, sohoadonTRUNG.length() - 1);
						
						this.msg = "Các số hóa đơn sau: " + sohoadonTRUNG + " đã bị trùng trong hệ thống. Vui lòng kiểm tra lại";
						db.getConnection().rollback();
						return false;
					}
					
				}
			}
			
			this.msg = "Soát số hóa đơn thành công.";
			db.getConnection().commit();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			db.update("rollback");
			this.msg = "Lỗi khi soát hóa đơn: " + e.getMessage();
			return false;
		}
		
		return true;
	}

	
	public String getSohoadontu() {
		
		return this.sohoadontu;
	}

	
	public void setSohoadontu(String sohoadontu) {
		
		this.sohoadontu = sohoadontu;
	}

	
	public String getSohoadonden() {
		
		return this.sohoadonden;
	}

	
	public void setSohoadonden(String sohoadonden) {
		
		this.sohoadonden = sohoadonden;
	}

	
	public String getPtVat() {

		return this.ptVAT;
	}


	public void setPtVat(String ptVat) {
		
		this.ptVAT = ptVat;
	}

	
	public String getNvbhId() {
		
		return this.nvbhId;
	}

	
	public void setNvbhId(String nvbhId) {
		
		this.nvbhId = nvbhId;
	}

	
	public ResultSet getNvbhRs() {
		
		return this.nvbhRs;
	}

	
	public void setNvbhRs(ResultSet nvbhRs) {
		
		this.nvbhRs = nvbhRs;
	}

	
	public String getMaFast() {
		
		return this.maFAST;
	}

	
	public void setMaFast(String maFAST) {
		
		this.maFAST = maFAST;
	}


	public void createRs() 
	{
		this.getNppInfo();
		
		this.nvbhRs = db.get("select  PK_SEQ, TEN AS TEN from DaiDienKinhDoanh where TRANGTHAI = '1' and pk_seq in ( select ddkd_fk from DAIDIENKINHDOANH_NPP where npp_fk = '" + this.nppId + "' ) ");
		
		this.khRs = db.get("select  PK_SEQ, MAFAST + ', ' + TEN AS TEN from nhaphanphoi where TRANGTHAI = '1' and tructhuoc_fk=1 and loainpp=4 ");
		this.KyhieuHDRs = db.get("select distinct ISNULL(KYHIEU,'') as KYHIEU from ERP_HOADON where TRANGTHAI <> 3  ");
		System.out.println("KÝ HIỆU: select distinct ISNULL(KYHIEU,'') as KYHIEU from HOADON where TRANGTHAI <> 3  ");
	}

	
	public double getTongtientruocthue() {
		
		return this.tongtientruocthue;
	}

	
	public void setTongtientruocthue(double tongtientruocthue) {
		this.tongtientruocthue = tongtientruocthue;
		
	}

	
	public double getTongtienthue() {
		
		return this.tongtienthue;
	}

	
	public void setTongtienthue(double tongtienthue) {
		this.tongtienthue = tongtienthue;
		
	}

	
	public double getTongtienhoadon() {
		
		return this.tongtienhoadon;
	}

	
	public void setTongtienhoadon(double tongtienhoadon) {
		this.tongtienhoadon = tongtienhoadon;
		
	}

	public int getTongSLHD() 
	{
		return this.TongSLHD;
	}

	public void setTongSLHD(int tongSLHD) 
	{
		this.TongSLHD = tongSLHD;
		
	}

	
	public boolean saveDoihoadon(HttpServletRequest request) 
	{
		this.getNppInfo();
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			String[] mahoadon = request.getParameterValues("mahoadon");
			String[] type = request.getParameterValues("type");
			String[] loaiHD = request.getParameterValues("loai");
			String[] sohoadon = request.getParameterValues("sohoadon");
			String[] sohoadonMOI = request.getParameterValues("sohoadonMOI");
			String[] kyhieu = request.getParameterValues("kyhieu");
			String[] ngayhoadon = request.getParameterValues("ngayhoadon");
			
			
			String query = "";
			
			String SoHD_OTC_new = "";
			
			String SoHD_ETC_new = "";
			
			String SoHD_PHELIEU_new = "";
			
			if(mahoadon != null )
			{
				for(int i = 0; i < mahoadon.length; i++)
				{
					if(sohoadonMOI[i].trim().length() > 0)
					{
						System.out.println("---TYPE LA: " + type[i] );
						
						/*if(type[i].equals("OTC"))
						{
							//COPY HOA DON MOI
							query = "insert HOADON( FROM_HOADON_GOC, ngayxuatHD, trangthai, ngaytao, nguoitao, ngaysua, nguoisua, sohoadon, kyhieu, chietkhau, tongtienbvat, tongtienavat, vat, tongtienkm, hinhthuctt, ghichu, NPP_FK, KHACHHANG_FK, loaihoadon, hopdong ) " +
									"select '" + mahoadon[i] + "', ngayxuatHD, trangthai, ngaytao, nguoitao, '" + this.getDateTime() + "' ngaysua, '" + this.userId + "' nguoisua, '" + sohoadonMOI[i] + "' sohoadon, kyhieu, chietkhau, tongtienbvat, tongtienavat, vat, tongtienkm, hinhthuctt, ghichu, NPP_FK, KHACHHANG_FK, loaihoadon, hopdong  " +
									"from HOADON where pk_seq = '" + mahoadon[i] + "'";
							System.out.println("1. INSERT NEW: " + query);
							if(db.updateReturnInt(query) <= 0)
							{
								this.msg = "1.1.Lỗi khi đổi số hóa đơn ( " + mahoadon[i] + " ) : " + query;
								db.getConnection().rollback();
								return false;
							}
							
							String hdId = "";
							query = "select SCOPE_IDENTITY() as hdId";
							ResultSet rs1 = db.get(query);
							rs1.next();
							hdId = rs1.getString("hdId");
							rs1.close();
							
							query = "insert HOADON_DDH(hoadon_fk, ddh_fk) " +
									"select '" + hdId + "', ddh_fk from HOADON_DDH where hoadon_fk = '" + mahoadon[i] + "'";
							if(!db.update(query))
							{
								this.msg = "1.2.Lỗi khi đổi số hóa đơn ( " + mahoadon[i] + " ) : " + query;
								db.getConnection().rollback();
								return false;
							}
							
							//GIU LAI HOA DON CU  --> CHUYEN THANH DA XOA
							query = "Update HOADON set trangthai = '3' where pk_seq = '" + mahoadon[i] + "' ";
							System.out.println("--DOI SO HOA DON: " + query);
							
							if(db.updateReturnInt(query) <= 0 )
							{
								this.msg = "1.3.Lỗi khi đổi số hóa đơn ( " + mahoadon[i] + " ) : " + query;
								db.getConnection().rollback();
								return false;
							}

							if(loaiHD[i].trim().equals("HĐ hàng khuyến mại"))  //HOA DON KHUYEN MAI
							{
								query = "insert HOADON_CTKM_TRAKM( hoadon_fk, sanpham_fk, sanphamma, soluong, ctkm, donvi, dongia, vat  ) " +
										"select '" + hdId + "', sanpham_fk, sanphamma, soluong, ctkm, donvi, dongia, vat  " +
										"from HOADON_CTKM_TRAKM where hoadon_fk = '" + mahoadon[i] + "'";
								if(db.updateReturnInt(query) <= 0 )
								{
									this.msg = "1.4.Lỗi khi đổi số hóa đơn ( " + mahoadon[i] + " ) : " + query;
									db.getConnection().rollback();
									return false;
								}
									query ="insert HOADON_CTKM_TRAKM_CHITIET(HoaDonId,hoadon_fk, sanpham_fk, soluong, scheme, solo, ngayhethan, kbh_fk, kho_fk, dongia )"+
											"select '" + hdId + "','" + hdId + "', sanpham_fk, soluong, scheme, solo, ngayhethan, kbh_fk, kho_fk, dongia  " +
											"from HOADON_CTKM_TRAKM_CHITIET where hoadon_fk = '" + mahoadon[i] + "'";
									if(db.updateReturnInt(query) <= 0 )
									{
										this.msg = "1.4.Lỗi khi đổi số hóa đơn ( " + mahoadon[i] + " ) : " + query;
										db.getConnection().rollback();
										return false;
									}
							}
							else
							{
								//COPY HOA DON - SAN PHAM
								query = "insert HOADON_SP( hoadon_fk, sanpham_fk, donvitinh, soluong, dongia, thanhtien, chietkhau, scheme, vat ) " +
										"select '" + hdId + "', sanpham_fk, donvitinh, soluong, dongia, thanhtien, chietkhau, scheme, vat " +
										"from HOADON_SP where hoadon_fk = '" + mahoadon[i] + "' ";
								if(db.updateReturnInt(query) <= 0 )
								{
									this.msg = "1.4.Lỗi khi đổi số hóa đơn ( " + mahoadon[i] + " ) : " + query;
									db.getConnection().rollback();
									return false;
								}
								
								query = "insert HOADON_SP_CHITIET( hoadon_fk, MA, TEN, DONVI, DONGIA, SOLO, NGAYHETHAN, THUEVAT, SOLUONG, CHIETKHAU ) " +
										"select '" + hdId + "', MA, TEN, DONVI, DONGIA, SOLO, NGAYHETHAN, THUEVAT, SOLUONG, CHIETKHAU " +
										"from HOADON_SP_CHITIET where hoadon_fk = '" + mahoadon[i] + "' ";
								if(db.updateReturnInt(query) <= 0 )
								{
									this.msg = "1.5.Lỗi khi đổi số hóa đơn ( " + mahoadon[i] + " ) : " + query;
									db.getConnection().rollback();
									return false;
								}
								
								//COPY HOA DON - CHIET KHAU
								query = "insert HOADON_CHIETKHAU(hoadon_fk, donhang_fk, diengiai, chietkhau, thueVAT, STT, tichluyQUY, hienTHI) " +
										"select '" + hdId + "', donhang_fk, diengiai, chietkhau, thueVAT, STT, tichluyQUY, hienTHI " +
										"from HOADON_CHIETKHAU " +
										"where hoadon_fk = '" + mahoadon[i] + "'  ";
								if(!db.update(query) )
								{
									this.msg = "1.6.Lỗi khi đổi số hóa đơn ( " + mahoadon[i] + " ) : " + query;
									db.getConnection().rollback();
									return false;
								}
								
								//COPY PHIEU CAN TRU --> TỐT NHẤT LÀ THAY THẾ LUÔN NẾU CÓ (Traphaco YÊU CẦU PHIẾU CẤN TRỪ SAU SẼ LẤY THEO HÓA ĐƠN MỚI)
								query = "INSERT CANTRUCONGNO(HOADON_FK, NGAYCHUNGTU, TRANGTHAI, NGAYTAO, NGAYSUA, NGUOITAO, NGUOISUA, NPP_FK, GHICHU)   " +
										"SELECT '" + hdId + "' as hoadon_fk, NGAYCHUNGTU, 1 as TRANGTHAI, NGAYTAO, NGAYSUA, NGUOITAO, NGUOISUA, npp_fk, N'Phiếu khấu trừ chiết khấu tháng từ đổi số HD " + hdId + "'  " +
										"FROM CANTRUCONGNO hd where PK_SEQ in ( select CANTRUCONGNO_FK from CANTRUCONGNO_HOADON where hoadon_fk = '" + mahoadon[i] + "' ) and trangthai = '1'";
								if(!db.update(query) )
								{
									this.msg = "1.7.Lỗi khi đổi số hóa đơn ( " + mahoadon[i] + " ) : " + query;
									db.getConnection().rollback();
									return false;
								}
								
								//XOA CAN TRU CU NEU CO
								query = "UPDATE CANTRUCONGNO set trangthai = '2' where pk_seq in ( select CANTRUCONGNO_FK from CANTRUCONGNO_HOADON where hoadon_fk = '" + mahoadon[i] + "' ) ";
								if( !db.update(query) )
								{
									this.msg = "Không thể hủy CANTRUCONGNO " + query;
									db.getConnection().rollback();
									return false;
								}
								
								query = "update CANTRUCONGNO_HOADON set hoadon_fk = '"  + hdId + "' " +
										"where cantrucongno_fk = ( select CANTRUCONGNO_FK from CANTRUCONGNO_HOADON where hoadon_fk = '" + mahoadon[i] + "' ) and hoadon_fk = '" +  mahoadon[i] + "' " +
											" and cantrucongno_fk in ( select PK_SEQ from CANTRUCONGNO where trangthai = '1' and npp_fk = '" + this.nppId + "' ) ";
								if( !db.update(query) )
								{
									this.msg = "1.7.Lỗi khi đổi số hóa đơn ( " + mahoadon[i] + " ) : " + query;
									db.getConnection().rollback();
									return false;
								}
							}
							
							SoHD_OTC_new += sohoadonMOI[i]+",";
							System.out.println("SoHD_OTC_new" + SoHD_OTC_new);
						}*/
						 if(type[i].equals("HO")) //ETC
						{
							System.out.println("vao Ho rui ne");
							//COPY HOA DON MOI
							query = "insert ERP_HOADON(FROM_HOADON_GOC, ngayxuatHD, trangthai, ngaytao, nguoitao, ngaysua, nguoisua, sohoadon, kyhieu, chietkhau, tongtienbvat, tongtienavat, vat, hinhthuctt, ghichu, NPP_FK, KHACHHANG_FK ) " +
									"select '" + mahoadon[i] + "', ngayxuatHD, trangthai, ngaytao, nguoitao, ngaysua, nguoisua, '"+sohoadonMOI[i]+"', kyhieu, chietkhau, tongtienbvat, tongtienavat, vat, hinhthuctt, ghichu, NPP_FK, KHACHHANG_FK " +
									"from ERP_HOADON where pk_seq = '" + mahoadon[i] + "'";
							System.out.println("2. INSERT NEW: " + query);
							if(db.updateReturnInt(query) <= 0)
							{
								this.msg = "2.1.Lỗi khi đổi số hóa đơn ( " + mahoadon[i] + " ) : " + query;
								db.getConnection().rollback();
								return false;
							}
							
						
							String hdId = "";
							query = "select SCOPE_IDENTITY() as hdId";
							ResultSet rs1 = db.get(query);
							rs1.next();
							hdId = rs1.getString("hdId");
							rs1.close();
							
							query = "insert ERP_HOADON_SP( hoadon_fk,SANPHAM_FK,SOLUONG,DONGIA,VAT,DONVITINH,CHIETKHAU,THANHTIEN,thuesuat,giagoc) " +
							"select '" +  hdId + "',SANPHAM_FK,SOLUONG,DONGIA,VAT,DONVITINH,CHIETKHAU,THANHTIEN,thuesuat,giagoc " +
							"from ERP_HOADON_SP where hoadon_fk = '" + mahoadon[i] + "' ";
							if(db.updateReturnInt(query) <= 0 )
							{
								this.msg = "2.2.1.Lỗi khi đổi số hóa đơn ( " + mahoadon[i] + " ) : " + query;
								db.getConnection().rollback();
								return false;
							}
							
							
							query = "insert ERP_HOADON_DDH(hoadon_fk, ddh_fk) " +
									"select '" + hdId + "', ddh_fk from ERP_HOADON_DDH where hoadon_fk = '" + mahoadon[i] + "'";
							if(db.updateReturnInt(query) <= 0 )
							{
								this.msg = "2.2.Lỗi khi đổi số hóa đơn ( " + mahoadon[i] + " ) : " + query;
								db.getConnection().rollback();
								return false;
							}
							
							//GIU LAI HOA DON CU  --> CHUYEN THANH DA HUY
							query = "Update ERP_HOADON set trangthai = '3' where pk_seq = '" + mahoadon[i] + "' ";
							//System.out.println("--SOAT HOA DON: " + query);
							
							if(db.updateReturnInt(query) <= 0 )
							{
								this.msg = "2.3Lỗi khi đổi số hóa đơn ( " + mahoadon[i] + " ) : " + query;
								db.getConnection().rollback();
								return false;
							}
							
							SoHD_ETC_new += sohoadonMOI[i]+",";
							System.out.println("SoHD_HO_new" + SoHD_ETC_new);
						}
						/*else{
							//COPY HOA DON MOI
							query = "insert ERP_HOADONPHELIEU(FROM_HOADON_GOC, ngayhoadon,khachhang_fk,ngayghinhan, trangthai, ngaytao, nguoitao, ngaysua, nguoisua, sohoadon, kyhieuhoadon, bvat, avat, vat, npp_fk, diengiai ) " +
									"select '" + mahoadon[i] + "', ngayhoadon, khachhang_fk, ngayghinhan,trangthai, ngaytao, nguoitao, '" + this.getDateTime() + "' , '" + this.userId + "', "+sohoadonMOI[i]+", kyhieuhoadon, bvat, avat, vat, npp_fk, diengiai " +									
									"from ERP_HOADONPHELIEU where pk_seq = '" + mahoadon[i] + "'";
							System.out.println("2. INSERT NEW: " + query);
							if(db.updateReturnInt(query) <= 0)
							{
								this.msg = "2.1.Lỗi khi đổi số hóa đơn ( " + mahoadon[i] + " ) : " + query;
								db.getConnection().rollback();
								return false;
							}
							
							String hdId = "";
							query = "select SCOPE_IDENTITY() as hdId";
							ResultSet rs1 = db.get(query);
							rs1.next();
							hdId = rs1.getString("hdId");
							rs1.close();
							
							query = "insert erp_hoadonphelieu_sanpham(hoadonphelieu_fk, diengiai, dongia, soluong, thanhtien, donvitinh, ghichu, thuevat, vat) " +
									"select '" + hdId + "', diengiai, dongia, soluong,thanhtien, donvitinh, ghichu, thuevat, vat  from erp_hoadonphelieu_sanpham where hoadonphelieu_fk = '" + mahoadon[i] + "'";
							if(!db.update(query))
							{
								this.msg = "1.2.Lỗi khi đổi số hóa đơn ( " + mahoadon[i] + " ) : " + query;
								db.getConnection().rollback();
								return false;
							}
							
							//GIU LAI HOA DON CU  --> CHUYEN THANH DA HUY
							query = "Update ERP_HOADONPHELIEU set trangthai = '2' where pk_seq = '" + mahoadon[i] + "' ";
							//System.out.println("--SOAT HOA DON: " + query);
							
							if(db.updateReturnInt(query) <= 0 )
							{
								this.msg = "2.3Lỗi khi đổi số hóa đơn ( " + mahoadon[i] + " ) : " + query;
								db.getConnection().rollback();
								return false;
							}
							
							SoHD_PHELIEU_new += sohoadonMOI[i]+",";
							System.out.println("SoHD_ERP_HOADONPHELIEU_new" + SoHD_PHELIEU_new);
						}*/
					}
				}
				
				//Chỉ trạng thái xóa = 3 là được phép trùng số hóa đơn
				/*query = "select sohoadon  " +
						"from " +
						"( " +
						"	select sohoadon, kyhieu from HOADON  " +
						"	where NPP_FK = '" + this.nppId + "' and trangthai != 3 and sohoadon != 'NA' " +
						"union ALL " +
						"	select sohoadon, kyhieu from ERP_HOADONNPP  " +
						"	where NPP_FK = '" + this.nppId + "' and trangthai != 3 and sohoadon != 'NA'  " +
						"union ALL " +
						"	select sohoadon, kyhieuhoadon kyhieu from ERP_HOADONPHELIEU  " +
						"	where NPP_FK = '" + this.nppId + "' and trangthai != 2 and sohoadon != 'NA'  " +
						") " +
						"checkTRUNG " +
						"group by sohoadon, kyhieu  " +
						"having count(*) >= 2 ";*/
					query =
					"select sohoadon  " +
					"from " +
					"( " +
					"	select sohoadon, kyhieu from ERP_HOADON  " +
					"	where  trangthai != 3 and sohoadon != 'NA' and sohoadon !='0000000'  " +
					/*"union ALL " +
					"	select sohoadon, kyhieuhoadon kyhieu from ERP_HOADONPHELIEU  " +
					"	where NPP_FK = '" + this.nppId + "' and trangthai != 2 and sohoadon != 'NA'  " +*/
					") " +
					"checkTRUNG " +
					"group by sohoadon, kyhieu  " +
					"having count(*) >= 2 ";
				
				System.out.println("---LENH CHECK TRUNG: " + query );
				ResultSet rsCHECK = db.get(query);
				if(rsCHECK != null)
				{
					String sohoadonTRUNG = "";
					while(rsCHECK.next())
					{
						sohoadonTRUNG += rsCHECK.getString("sohoadon").trim() + ", ";
					}
					rsCHECK.close();
					
					System.out.println("---SO HOA DON TRUNG: " + sohoadonTRUNG);
					if(sohoadonTRUNG.trim().length() > 0)
					{
						sohoadonTRUNG = sohoadonTRUNG.substring(0, sohoadonTRUNG.length() - 1);
						
						this.msg = "Các số hóa đơn sau: " + sohoadonTRUNG + " đã bị trùng trong hệ thống. Vui lòng kiểm tra lại";
						db.getConnection().rollback();
						return false;
					}
					
				}
			}
			
			String tb = "Đổi số hóa đơn thành công. \n";
			
			 
			if(SoHD_OTC_new.trim().length()>0)
				SoHD_OTC_new = SoHD_OTC_new.substring(0, SoHD_OTC_new.length() - 1);
			
			if(SoHD_PHELIEU_new.trim().length()>0)
				SoHD_PHELIEU_new = SoHD_PHELIEU_new.substring(0, SoHD_PHELIEU_new.length() - 1);
			
			if(SoHD_ETC_new.trim().length()>0)
				SoHD_ETC_new = SoHD_ETC_new.substring(0, SoHD_ETC_new.length() - 1);
						
			String canhbao = "";
			if(SoHD_OTC_new.length() >0 ){
				 canhbao = 	" SELECT distinct isnull(c.SOHOADON ,0) SOHOADONCU  \n"+
				 			" FROM HOADON a left join ( select hoadon_fk from HOADON_CHIETKHAU where diengiai = 'CT5' and hienthi = '0' and round(chietkhau, 0) > 0 ) b on a.FROM_HOADON_GOC = b.hoadon_fk \n"+  	 
				 			"	 left join (SELECT * FROM HOADON WHERE NPP_FK ='"+this.nppId+"') c  ON a.FROM_HOADON_GOC = c.PK_SEQ \n"+
				 			"     inner join KHACHHANG KH on a.KHACHHANG_FK=KH.PK_SEQ \n"+
				 			" WHERE a.FROM_HOADON_GOC is not null and a.SOHOADON IN ("+SoHD_OTC_new+") \n";
			}
			
			if(SoHD_OTC_new.length()>0 && SoHD_ETC_new.length() > 0)
				canhbao += "UNION ALL \n";
			
			if(SoHD_ETC_new.length() > 0){
				 canhbao = " SELECT distinct isnull(c.SOHOADON ,0) SOHOADONCU \n"+
						   " FROM ERP_HOADONNPP a left join ( select hoadon_fk from HOADON_CHIETKHAU where diengiai = 'CT5' and hienthi = '0' and round(chietkhau, 0) > 0 ) b on a.FROM_HOADON_GOC = b.hoadon_fk \n"+  	 
						   "	 left join (SELECT * FROM ERP_HOADONNPP WHERE NPP_FK ='"+this.nppId+"') c  ON a.FROM_HOADON_GOC = c.PK_SEQ \n"+
						   "     inner join KHACHHANG KH on a.KHACHHANG_FK=KH.PK_SEQ \n"+
						   " WHERE a.FROM_HOADON_GOC is not null and a.SOHOADON IN ("+SoHD_OTC_new+") \n";
			}
			
			if(SoHD_OTC_new.length()>0 && SoHD_ETC_new.length() > 0)
				canhbao += "UNION ALL \n";
			
			if(SoHD_PHELIEU_new.length() >0 ){
				 canhbao = 	" SELECT distinct isnull(c.SOHOADON ,0) SOHOADONCU  \n"+
				 			" FROM ERP_HOADONPHELIEU \n"+  	 
				 			"	 left join (SELECT * FROM ERP_HOADONPHELIEU WHERE NPP_FK ='"+this.nppId+"') c  ON a.FROM_HOADON_GOC = c.PK_SEQ \n"+
				 			"     inner join KHACHHANG KH on a.KHACHHANG_FK=KH.PK_SEQ \n"+
				 			" WHERE a.FROM_HOADON_GOC is not null and a.SOHOADON IN ("+SoHD_PHELIEU_new+") \n";
			}
			
			System.out.println("CANHBAO: "+ canhbao);
			
			ResultSet rs = db.get(canhbao);
			
			String kq="";
			if(rs!=null){
				while (rs.next()){
					kq += rs.getString("SOHOADONCU") +", ";
				}
			}
			
			if(kq.trim().length()>0)
				tb =" Những số hóa đơn ảnh hưởng đến biên bản bù trừ: "+ kq.substring(0, kq.length()-1) + " \n";
			
			System.out.println(tb);
			this.msg = tb;
				
			
			db.getConnection().commit();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			db.update("rollback");
			this.msg = "Lỗi khi soát hóa đơn: " + e.getMessage();
			return false;
		}
		
		return true;
	}

	public ResultSet getKyhieuHDRs() {
		return this.KyhieuHDRs;
	}

	public void setKyhieuHDRs(ResultSet kyhieuhdRs) {
		this.KyhieuHDRs=kyhieuhdRs;
	}

	public String getKyhieuHD() {
		return this.kyhieu;
	}

	public void setKyhieuHD(String kyhieuhd) {
		this.kyhieu=kyhieuhd;
	}

}
