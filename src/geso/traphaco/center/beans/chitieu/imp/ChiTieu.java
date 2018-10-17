package geso.traphaco.center.beans.chitieu.imp;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import geso.traphaco.center.beans.chitieu.IChiTieu;
import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.db.sql.dbutils;

public class ChiTieu implements IChiTieu {

	double Chitiet=0;
	String UserID;
	double Id;
	int Thang;
	int Quy;
	public int getQuy() {
		return Quy;
	}
	public void setQuy(int quy) {
		Quy = quy;
	}
	int Nam;
	String NgayTao;
	String NguoiTao;
	String NguoiSua;
	String NgaySua;
	String NgayKetThuc;
	String KhuVuc="";
	String DienGiai;
	String TenKhuVuc;
	String Message;
	String Dvkd_id;
	String TenDVkd;
	String SoNgayLamViec;
	double ChiTieu;
	String KenhId;
	String TrangThai;
	String LoaiChiTieu;
	ResultSet rsChiTieuNV;
	ResultSet rsChiTieuNV2;
	ResultSet rsChiTieuNV3;
	String nppid;
	public String getNppid() {
		return nppid;
	}
	public void setNppid(String nppid) {
		this.nppid = nppid;
	}
	public ResultSet getRsChiTieuNV2() {
		return rsChiTieuNV2;
	}
	public void setRsChiTieuNV2(ResultSet rsChiTieuNV2) {
		this.rsChiTieuNV2 = rsChiTieuNV2;
	}
	
	public ResultSet getRsChiTieuNV3() {
		return rsChiTieuNV3;
	}
	public void setRsChiTieuNV3(ResultSet rsChiTieuNV3) {
		this.rsChiTieuNV3 = rsChiTieuNV3;
	}
	ResultSet rsKenh;
	ResultSet RsDVKD;
	ResultSet	RsNppNhomSp; 
	
	String ChuoiNhomSP;
	String[] NhomSP;
	String[] NhomSPId;
	List<ChiTieuNPP> listchitieunpp=new ArrayList<ChiTieuNPP>();
	List<ChiTieu> listchitieu =new ArrayList<ChiTieu>();
	ResultSet rsChitieuPri;
	ResultSet rsChitieuPriNpp;
	dbutils db;
	String Tumuc;
	String ToiMuc;
	ResultSet rsnhomkenh;
	String nhomkenh;
	 public String getNhomkenh() {
		return nhomkenh;
	}
	public void setNhomkenh(String nhomkenh) {
		this.nhomkenh = nhomkenh;
	}
	public ResultSet getRsnhomkenh() {
		return rsnhomkenh;
	}
	public void setRsnhomkenh(ResultSet rsnhomkenh) {
		this.rsnhomkenh = rsnhomkenh;
	}
	public ChiTieu()
	 {
		
		 this.NgayKetThuc="";
		 this.SoNgayLamViec="";
		 this.Message="";
		 this.DienGiai="";
		 this.KhuVuc="";
		 this.Dvkd_id="";
		 this.KenhId="";
		 this.Thang=0;
		 this.Nam=0;
		 this.LoaiChiTieu="";
		 this.Tumuc="";
		 this.ToiMuc="";
		 this.Quy=1;
		 this.nhapp="";
		 db=new dbutils();
	 }
	 public ChiTieu(String id,String loaict)
	 {
		 this.NgayKetThuc="";
		 this.SoNgayLamViec="";
		 this.Message="";
		 this.DienGiai="";
		 this.KhuVuc="";
		 this.Dvkd_id="";
		 this.KenhId="";
		 this.Thang=0;
		 this.Nam=0;
		 this.LoaiChiTieu="";
		 this.Tumuc="";
		 this.ToiMuc="";
		 this.Quy=1;
		 this.nhapp="";
		 db=new dbutils();
			try
			{
		 String  sql_getdata="";
		 if(loaict.equals("1"))
		 {
		 sql_getdata="SELECT  isnull( c.nhomkenh,1) as nhomkenh,isnull(c.loaichitieu,'') as loaichitieu ,c.trangthai, c.kenh_fk, C.PK_SEQ, C.THANG, C.NAM, C.CHITIEU,C.DIENGIAI,C.SONGAYLAMVIEC, C.NGAYTAO, C.NGAYSUA,NT.TEN AS NGUOITAO, "+ 
		            "NS.TEN AS NGUOISUA FROM dbo.CHITIEU_SEC AS C  INNER JOIN "+
		            "dbo.NHANVIEN AS NT ON C.NGUOITAO = NT.PK_SEQ INNER JOIN dbo.NHANVIEN AS NS ON C.NGUOISUA = NS.PK_SEQ where c.trangthai!='2' and c.pk_seq="+ id;
		 }
		 else
		 {
			 sql_getdata="SELECT  chotthang1,chotthang2,chotthang3,C.Quy, isnull(c.loaichitieu,'') as loaichitieu ,c.trangthai, c.kenh_fk, C.PK_SEQ, C.NAM,C.DIENGIAI,C.SONGAYLAMVIEC, C.NGAYTAO, C.NGAYSUA,NT.TEN AS NGUOITAO,C.nhapp_fk, "+ 
			            "NS.TEN AS NGUOISUA FROM dbo.Chitieunhanvien_OTC AS C  INNER JOIN "+
			            "dbo.NHANVIEN AS NT ON C.NGUOITAO = NT.PK_SEQ INNER JOIN dbo.NHANVIEN AS NS ON C.NGUOISUA = NS.PK_SEQ where c.trangthai!='2' and c.pk_seq="+ id;
		
		 }
		 System.out.println("SQL data innit"+sql_getdata);
		 ResultSet rsChiTieu=	db.get(sql_getdata);
			if(rsChiTieu!=null){
				while(rsChiTieu.next()){
					this.setID(rsChiTieu.getDouble("PK_SEQ"));
					if(loaict.equals("1"))
					{
						this.setThang(rsChiTieu.getInt("THANG"));
						
					}
					if(!loaict.equals("1"))
					{
						this.setQuy(rsChiTieu.getInt("Quy"));
						this.setChotthang1(rsChiTieu.getInt("chotthang1"));
						this.setChotthang2(rsChiTieu.getInt("chotthang2"));
						this.setChotthang3(rsChiTieu.getInt("chotthang3"));
						this.setNhapp(rsChiTieu.getString("nhapp_fk"));
					}
					this.setNam(rsChiTieu.getInt("NAM"));
					
				
					this.setNgayTao(rsChiTieu.getString("NGAYTAO"));
					this.setNgaySua(rsChiTieu.getString("NGAYSUA"));
					this.setDienGiai(rsChiTieu.getString("DIENGIAI"));
					this.setNguoiTao(rsChiTieu.getString("NGUOITAO"));
					this.setNguoiSua(rsChiTieu.getString("NGUOISUA"));

					this.setSoNgayLamViec(rsChiTieu.getString("SONGAYLAMVIEC"));
					this.setKenhId(rsChiTieu.getString("kenh_fk"));
					this.setTrangThai(rsChiTieu.getString("trangthai"));
					
					
				}
			}
			rsChiTieu.close();		
		 System.out.println("sql_data:"+sql_getdata);
		 String sql_chitieunpp="";
		 {
					 
				/*	 sql_chitieunpp="select  npp.pk_seq ,npp.ten as tennpp,b.chitieu as sellsout,isnull( b.sodonhang,0) as sodonhang ,isnull(b.dophu,0) as dophu , nsp.pk_Seq as manhom, "+ 
						" isnull(nsp.ten,'')  as tennhom, isnull(a.chitieu,0) as chitieu ,isnull(b.SanLuongTrenDh,0) as SanLuongTrenDh  "+
						" from chitieu_nhapp_sec b    "+
						" left join chitieusec_nhapp_nhomsp  a  on a.npp_fk=b.nhapp_fk and b.chitieu_sec_fk=a.chitieusec_fk "+
						" left join nhomsanpham nsp on nsp.pk_seq=a.nhomsp_fk  "+
						 " inner join nhaphanphoi npp on npp.pk_seq=b.nhapp_fk where chitieu_sec_fk="+this.Id+"" +
						 " order by npp.ten ";*/
			 String sql="";		
			 if(loaict.equals("2"))
					 {
					 
							 sql=	"	select distinct nhomsp_fk as nspId,b.TEN  as nspTen,loaichitieu_fk from Chitieunhanvien_OTC_SR_NSP  a "+
							 	"		inner join NHOMSANPHAM  b on b.PK_SEQ=a.NHOMSP_FK "+
							 	"	 where  CHITIEU_NV_FK='"+id+"' ";  
						
					 }
			 else
			 {
				 sql=	"	select distinct nhomsp_fk as nspId,b.TEN  as nspTen from chitieusec_nhapp_nhomsp  a "+
						 	"		inner join NHOMSANPHAM  b on b.PK_SEQ=a.NHOMSP_FK "+
						 	"	 where  CHITIEUSEC_FK='"+id+"' ";  
					
			 }
							 	System.out.println("[NhomSanPham]"+sql);
								ResultSet rs=this.db.get(sql);
								String[] nhom =new String[10];
								String[] nhomid =new String[10];
								
								int i=0;
								String strselect="";
								String strselectCT="";
							
								String strngoac="[0]";
								
								if(rs!=null)
								{
									while (rs.next())
									{
										nhomid[i]=rs.getString("nspId");
										nhom[i]=rs.getString("nspTen");
										if(i==0)
										{
											strselect=" ,["+rs.getString("nspId")+"] as CT"+rs.getString("nspId")+"";
											if(loaict.equals("2"))
											strselectCT=" ,["+rs.getString("nspId")+"] as loaichitieu_fk"+rs.getString("nspId")+"";
											strngoac=" [0], ["+rs.getString("nspId")+"]";
										}else
										{
											strselect=strselect+", ["+rs.getString("nspId")+"] as CT"+rs.getString("nspId")+"";
											if(loaict.equals("2"))
											strselectCT+=" ,["+rs.getString("nspId")+"] as loaichitieu_fk"+rs.getString("nspId")+"";
											
											strngoac=strngoac+ ",["+rs.getString("nspId")+"]";
										}
										i++;
									}
								}
								this.NhomSP=nhom;
								this.NhomSPId=nhomid;	
								System.out.println("nhom spppppppppppppppppppp"+strselect);
								 sql=
								"	 select v.TEN as vungTen,kv.TEN as kvTen, \n"+
								"		npp.PK_SEQ as nppId,npp.MA as nppMa,npp.TEN as nppTen,  \n"+
								"		a.CHITIEU_NPP_FK,NHAPP_FK,CHITIEUMUAVAO, \n"+
								"		CHITIEUBANRA,SODONHANG,SONGAYTONKHOQUYDINH,TYLEGIAOHANGTHANHCONG,TYLECHINHXACTONKHO "+strselect+"  \n"+
								"	from CHITIEU_NHAPP_CHITIET a \n"+
								"	left join  \n"+
								"	( \n"+
								"		select CHITIEUSEC_FK,NPP_FK,NHOMSP_FK,CHITIEU as ctNhom  \n"+
								"		from  chitieusec_nhapp_nhomsp  \n"+
								"		where CHITIEUSEC_FK='"+id+"' \n"+
								"	)p pivot  \n"+
								"	( \n"+
								"		 sum(ctNhom) for NHOMSP_FK in ("+strngoac+" )  "+  
								"	)as ctNhom on ctNhom.CHITIEUSEC_FK=a.CHITIEU_npp_FK and a.NHAPP_FK=ctNhom.NPP_FK "+
								"		left join NHAPHANPHOI npp on npp.PK_SEQ=a.NHAPP_FK "+
								"		left join KHUVUC kv on kv.PK_SEQ=npp.KHUVUC_FK "+
								"		left join VUNG v on v.PK_SEQ=kv.VUNG_FK "+
								"	where a.CHITIEU_NPP_FK='"+id+"' " +
								" order by v.ten,kv.ten,npp.ten  ";
								 
								// System.out.println("[ctNpp]"+sql);
							
								 this.RsNppNhomSp=db.get(sql);
					 
								 int quytempt=0;
								 sql="select distinct quy from Chitieunhanvien_OTC where pk_seq="+id;
								 ResultSet rs1=db.get(sql);
								 if(rs1.next())
								 {
									 quytempt=rs1.getInt("quy");
								 }
								 int t1=0;
								 int t2=0;
								 int t3=0;
								 if(quytempt==1)
								 {
									 t1=1;
									 t2=2;
									 t3=3;
								 }
								 if(quytempt==2)
								 {
									 t1=4;
									 t2=5;
									 t3=6;
								 }
								 if(quytempt==3)
								 {
									 t1=7;
									 t2=8;
									 t3=9;
								 }
								 if(quytempt==4)
								 {
									 t1=10;
									 t2=11;
									 t3=12;
								 }
								 
								 sql=
											"	 select * from (select    distinct  \n"+
											"case when a.chucvu = 'ASM' then (select ten from asm where pk_seq=a.nhanvien_fk)\n"+
											"when a.chucvu = 'RSM' then (select ten from BM where pk_seq=a.nhanvien_fk)  \n"+
											"when a.chucvu = 'SS' then (select ten from GIAMSATBANHANG where pk_seq=a.nhanvien_fk) \n"+
											"else (select ten from DAIDIENKINHDOANH where pk_seq=a.nhanvien_fk)  end as ten ,\n"+
											"	a.CHITIEU_NV_FK,a.NHANVIEN_FK,a.CHUCVU,a.chitieubanraSl,a.CHITIEUBANRA,a.SOLANVIENGTHAM,A.SOCUAHANGMUAHANG,A.SODONHANGTRONGNGAY,A.GIATRIDONHANGTOITHIEU,A.SKU,A.GIAOHANGTHANHCONG "+strselect+"  \n"+
											"	from Chitieunhanvien_OTC_SR a \n"+
											"	left join  \n"+
											"	( \n"+
											"		select distinct  CHITIEU_NV_FK,NHANVIEN_FK,NHOMSP_FK,'TDV' as chucvu,CHITIEU as ctNhom    \n"+
											"		from  Chitieunhanvien_OTC_SR_NSP    \n"+
											"		where CHITIEU_NV_FK='"+id+"' and thang="+t1+" and nam="+this.Nam+" \n"+
											"	)p pivot  \n"+
											"	( \n"+
											"		 sum(ctNhom) for NHOMSP_FK in ("+strngoac+" )  "+  
											"	)as ctNhom on ctNhom.CHITIEU_NV_FK=a.CHITIEU_NV_FK and ctNhom.nhanvien_fk=a.nhanvien_fk and ctNhom.chucvu=a.chucvu 	 	"+
											"	where a.CHITIEU_NV_FK='"+id+"' and a.thang="+t1+" and a.nam="+this.Nam+") as A " ;
									if(loaict.equals("2"))
									{
										 sql+=
													"	inner join   (select    distinct  \n"+
													"case when a.chucvu = 'ASM' then (select ten from asm where pk_seq=a.nhanvien_fk)\n"+
													"when a.chucvu = 'RSM' then (select ten from BM where pk_seq=a.nhanvien_fk)  \n"+
													"when a.chucvu = 'SS' then (select ten from GIAMSATBANHANG where pk_seq=a.nhanvien_fk) \n"+
													"else (select ten from DAIDIENKINHDOANH where pk_seq=a.nhanvien_fk)  end as ten ,\n"+
													"	a.CHITIEU_NV_FK,a.NHANVIEN_FK,a.CHUCVU,a.chitieubanraSl,a.CHITIEUBANRA,a.SOLANVIENGTHAM,A.SOCUAHANGMUAHANG,A.SODONHANGTRONGNGAY,A.GIATRIDONHANGTOITHIEU,A.SKU,A.GIAOHANGTHANHCONG "+strselectCT+"  \n"+
													"	from Chitieunhanvien_OTC_SR a \n"+
													"	left join  \n"+
													"	( \n"+
													"		select distinct  CHITIEU_NV_FK,NHANVIEN_FK,NHOMSP_FK,'TDV' as chucvu,loaichitieu_FK    \n"+
													"		from  Chitieunhanvien_OTC_SR_NSP    \n"+
													"		where CHITIEU_NV_FK='"+id+"' and thang="+t1+" and nam="+this.Nam+" \n"+
													"	)p pivot  \n"+
													"	( \n"+
													"		 sum(loaichitieu_fk) for NHOMSP_FK in ("+strngoac+" )  "+  
													"	)as ctNhom on ctNhom.CHITIEU_NV_FK=a.CHITIEU_NV_FK and ctNhom.nhanvien_fk=a.nhanvien_fk and ctNhom.chucvu=a.chucvu 	 	"+
													"	where a.CHITIEU_NV_FK='"+id+"' and a.thang="+t1+" and a.nam="+this.Nam+") as B on B.CHITIEU_NV_FK=A.CHITIEU_NV_FK " ;
									
									}
						//	System.out.println("[ctDDKD]"+sql);
							this.rsChiTieuNV=db.get(sql);
							
							 sql=
										"	select * from ( select    distinct  \n"+
										"case when a.chucvu = 'ASM' then (select ten from asm where pk_seq=a.nhanvien_fk)\n"+
										"when a.chucvu = 'RSM' then (select ten from BM where pk_seq=a.nhanvien_fk)  \n"+
										"when a.chucvu = 'SS' then (select ten from GIAMSATBANHANG where pk_seq=a.nhanvien_fk) \n"+
										"else (select ten from DAIDIENKINHDOANH where pk_seq=a.nhanvien_fk)  end as ten ,\n"+
										"	a.CHITIEU_NV_FK,a.NHANVIEN_FK,a.CHUCVU,a.chitieubanraSl,a.CHITIEUBANRA,a.SOLANVIENGTHAM,A.SOCUAHANGMUAHANG,A.SODONHANGTRONGNGAY,A.GIATRIDONHANGTOITHIEU,A.SKU,A.GIAOHANGTHANHCONG "+strselect+"  \n"+
										"	from Chitieunhanvien_OTC_SR a \n"+
										"	left join  \n"+
										"	( \n"+
										"		select distinct  CHITIEU_NV_FK,NHANVIEN_FK,NHOMSP_FK,'TDV' as chucvu,CHITIEU as ctNhom    \n"+
										"		from  Chitieunhanvien_OTC_SR_NSP    \n"+
										"		where CHITIEU_NV_FK='"+id+"' and thang="+t2+" and nam="+this.Nam+" \n"+
										"	)p pivot  \n"+
										"	( \n"+
										"		 sum(ctNhom) for NHOMSP_FK in ("+strngoac+" )  "+  
										"	)as ctNhom on ctNhom.CHITIEU_NV_FK=a.CHITIEU_NV_FK and ctNhom.nhanvien_fk=a.nhanvien_fk and ctNhom.chucvu=a.chucvu 	 	"+
										"	where a.CHITIEU_NV_FK='"+id+"' and a.thang="+t2+" and a.nam="+this.Nam+" " +
										"  )as A ";
							 
							 if(loaict.equals("2"))
								{
									 sql+=
												"	inner join   (select    distinct  \n"+
												"case when a.chucvu = 'ASM' then (select ten from asm where pk_seq=a.nhanvien_fk)\n"+
												"when a.chucvu = 'RSM' then (select ten from BM where pk_seq=a.nhanvien_fk)  \n"+
												"when a.chucvu = 'SS' then (select ten from GIAMSATBANHANG where pk_seq=a.nhanvien_fk) \n"+
												"else (select ten from DAIDIENKINHDOANH where pk_seq=a.nhanvien_fk)  end as ten ,\n"+
												"	a.CHITIEU_NV_FK,a.NHANVIEN_FK,a.CHUCVU,a.chitieubanraSl,a.CHITIEUBANRA,a.SOLANVIENGTHAM,A.SOCUAHANGMUAHANG,A.SODONHANGTRONGNGAY,A.GIATRIDONHANGTOITHIEU,A.SKU,A.GIAOHANGTHANHCONG "+strselectCT+"  \n"+
												"	from Chitieunhanvien_OTC_SR a \n"+
												"	left join  \n"+
												"	( \n"+
												"		select distinct  CHITIEU_NV_FK,NHANVIEN_FK,NHOMSP_FK,'TDV' as chucvu ,loaichitieu_FK   \n"+
												"		from  Chitieunhanvien_OTC_SR_NSP    \n"+
												"		where CHITIEU_NV_FK='"+id+"' and thang="+t2+" and nam="+this.Nam+" \n"+
												"	)p pivot  \n"+
												"	( \n"+
												"		 sum(loaichitieu_fk) for NHOMSP_FK in ("+strngoac+" )  "+  
												"	)as ctNhom on ctNhom.CHITIEU_NV_FK=a.CHITIEU_NV_FK and ctNhom.nhanvien_fk=a.nhanvien_fk and ctNhom.chucvu=a.chucvu 	 	"+
												"	where a.CHITIEU_NV_FK='"+id+"' and a.thang="+t2+" and a.nam="+this.Nam+") as B on B.CHITIEU_NV_FK=A.CHITIEU_NV_FK " ;
								
								}
					//	System.out.println("[ctDDKD]"+sql);
						this.rsChiTieuNV2=db.get(sql);
						
						 sql=
									"	 select * from (select    distinct  \n"+
									"case when a.chucvu = 'ASM' then (select ten from asm where pk_seq=a.nhanvien_fk)\n"+
									"when a.chucvu = 'RSM' then (select ten from BM where pk_seq=a.nhanvien_fk)  \n"+
									"when a.chucvu = 'SS' then (select ten from GIAMSATBANHANG where pk_seq=a.nhanvien_fk) \n"+
									"else (select ten from DAIDIENKINHDOANH where pk_seq=a.nhanvien_fk)  end as ten ,\n"+
									"	a.CHITIEU_NV_FK,a.NHANVIEN_FK,a.CHUCVU,a.chitieubanraSL,a.CHITIEUBANRA,a.SOLANVIENGTHAM,A.SOCUAHANGMUAHANG,A.SODONHANGTRONGNGAY,A.GIATRIDONHANGTOITHIEU,A.SKU,A.GIAOHANGTHANHCONG "+strselect+"  \n"+
									"	from Chitieunhanvien_OTC_SR a \n"+
									"	left join  \n"+
									"	( \n"+
									"		select distinct  CHITIEU_NV_FK,NHANVIEN_FK,NHOMSP_FK,'TDV' as chucvu,CHITIEU as ctNhom   \n"+
									"		from  Chitieunhanvien_OTC_SR_NSP    \n"+
									"		where CHITIEU_NV_FK='"+id+"' and thang="+t3+" and nam="+this.Nam+" \n"+
									"	)p pivot  \n"+
									"	( \n"+
									"		 sum(ctNhom) for NHOMSP_FK in ("+strngoac+" )  "+  
									"	)as ctNhom on ctNhom.CHITIEU_NV_FK=a.CHITIEU_NV_FK and ctNhom.nhanvien_fk=a.nhanvien_fk and ctNhom.chucvu=a.chucvu 	 	"+
									"	where a.CHITIEU_NV_FK='"+id+"' and a.thang="+t3+" and a.nam="+this.Nam+") AS A " ;
									
						 
						 if(loaict.equals("2"))
							{
								 sql+=
											"	inner join   (select    distinct  \n"+
											"case when a.chucvu = 'ASM' then (select ten from asm where pk_seq=a.nhanvien_fk)\n"+
											"when a.chucvu = 'RSM' then (select ten from BM where pk_seq=a.nhanvien_fk)  \n"+
											"when a.chucvu = 'SS' then (select ten from GIAMSATBANHANG where pk_seq=a.nhanvien_fk) \n"+
											"else (select ten from DAIDIENKINHDOANH where pk_seq=a.nhanvien_fk)  end as ten ,\n"+
											"	a.CHITIEU_NV_FK,a.NHANVIEN_FK,a.CHUCVU,a.chitieubanraSL,a.CHITIEUBANRA,a.SOLANVIENGTHAM,A.SOCUAHANGMUAHANG,A.SODONHANGTRONGNGAY,A.GIATRIDONHANGTOITHIEU,A.SKU,A.GIAOHANGTHANHCONG "+strselectCT+"  \n"+
											"	from Chitieunhanvien_OTC_SR a \n"+
											"	left join  \n"+
											"	( \n"+
											"		select distinct  CHITIEU_NV_FK,NHANVIEN_FK,NHOMSP_FK,'TDV' as chucvu,loaichitieu_FK    \n"+
											"		from  Chitieunhanvien_OTC_SR_NSP    \n"+
											"		where CHITIEU_NV_FK='"+id+"' and thang="+t3+" and nam="+this.Nam+" \n"+
											"	)p pivot  \n"+
											"	( \n"+
											"		 sum(loaichitieu_fk) for NHOMSP_FK in ("+strngoac+" )  "+  
											"	)as ctNhom on ctNhom.CHITIEU_NV_FK=a.CHITIEU_NV_FK and ctNhom.nhanvien_fk=a.nhanvien_fk and ctNhom.chucvu=a.chucvu 	 	"+
											"	where a.CHITIEU_NV_FK='"+id+"' and a.thang="+t3+" and a.nam="+this.Nam+") as B on B.CHITIEU_NV_FK=A.CHITIEU_NV_FK  " ;
							
							}
				//	System.out.println("[ctDDKD]"+sql);
					this.rsChiTieuNV3=db.get(sql);
						
				 }
			}catch(Exception er)
			{
				er.printStackTrace();
				this.Message="Error ChiTieu.java - line : 88- detail error :"+ er.toString();
			}
			this.Message="";
	 }
	 
	
	public void setChitieu(double chitieu) {

	 this.ChiTieu=chitieu;
	}

	
	public double getChitieu() {

		return this.ChiTieu;
	}

	
	public void setID(double id) {

	 this.Id=id;	
	}

	
	public double getID() {

		return Id;
	}

	
	public void setThang(int thang) {

		this.Thang=thang;
	}

	
	public int getThang() {

		return this.Thang;
	}

	
	public void setNam(int nam) {

		this.Nam=nam;
	}

	
	public int getNam() {

		return this.Nam;
	}

	
	public void setKhuVucID(String khuvucid) {

		this.KhuVuc=khuvucid;
	}

	
	public String getKhuVucID() {

		return this.KhuVuc;
	}

	
	public void setNgayKetThuc(String ngayketthuc) {

		this.NgayKetThuc=ngayketthuc;
	}

	
	public String getNgayKetThuc() {

		return this.NgayKetThuc;
	}

	
	public void setNgayTao(String ngaytao) {

		this.NgayTao =ngaytao;
	}

	
	public String getNgayTao() {

		return this.NgayTao;
	}

	
	public void setNgaySua(String ngaysua) {

		this.NgaySua=ngaysua;
	}

	
	public String getNgaySua() {

		return this.NgaySua;
	}

	
	public void setDienGiai(String diengiai) {

		this.DienGiai=diengiai;
	}

	
	public String getDienGiai() {

		return this.DienGiai;
	}
	 public boolean KiemTraHopLe()
	 {
		 	System.out.println("[KiemTraHopLe]");
		    if(this.NguoiTao.toString().equals("null") || this.NguoiTao.equals("")){
				this.Message="Ten Nguoi Dung Khong Hop Le,Vui Long Dang Xuat De Thu Lai!";
				return false;
			}
		
			
			
			if(this.Nam==0){
				this.Message="Vui Long Chon Nam";
				return false;
			}
			try{
			  Integer.parseInt(this.SoNgayLamViec);
			 }catch(Exception er){
				 this.Message="So Ngay Lam Viec Khong Hop Le,Vui Long Kiem Tra Lai";
				 return false;
			 }
			System.out.println("[Msg]"+this.Message);
		 return true;
	 }
	
	public boolean SaveChiTieu() {//truong hop save la primary

		
		try
		{
			if(!KiemTraHopLe()){
				return false;
			
			}
		db.getConnection().setAutoCommit(false);
		//kiem tra xenm chi tieu trong thoi gian nay da co chua
		String sql_checkexit="select pk_seq from ChiTieu where thang= " +this.Thang+" and nam="+ this.Nam +" and dvkd_fk="+this.Dvkd_id +" and kenh_fk="+this.KenhId+" and trangthai!=2";
		//System.out.println(sql_checkexit);
	   
		ResultSet rs_check=db.get(sql_checkexit);
		if(rs_check!=null){
			if(rs_check.next()){//co du lieu
				this.setMessage("Chi Tieu Trong Thang Da Thiet Lap cho cac nha phan phoi thuoc khu vuc nay, Vui Long Kiem Tra Lai");
			 return false;
			}
		}
		// thuc hien insert
		String sqlSaveChiTieu="insert into CHITIEU(THANG,NAM,CHITIEU,NGAYKETTHUC,NGAYTAO,NGUOITAO,NGAYSUA,NGUOISUA,DIENGIAI,TRANGTHAI,DVKD_FK,songaylamviec,kenh_fk) " +
		"values ("+this.Thang+","+this.Nam+","+this.ChiTieu+",'"+this.NgayKetThuc+"','"+this.NgayTao+"','"+this.NguoiTao+"','"+this.NgaySua+"','"+this.NguoiSua+"',N'"+this.DienGiai+"','0',"+this.Dvkd_id+","+this.SoNgayLamViec+","+this.KenhId+")";
		if(!db.update(sqlSaveChiTieu)){
			this.Message="Ban Khong Them Cap Nhat Chi Tieu Nay, Vui Long Thu Lai,Loi Cau Lenh   - 146 line -Form : Chitieuttchovung.java - Error here :  " +sqlSaveChiTieu;
			//System.out.println("sqlSaveChiTieu : 146 Chitieuttchovung " +sqlSaveChiTieu);
			db.update("rollback");
			return false;
		}
		
		//thuc hien insert chi tiet
		String query = "select IDENT_CURRENT('CHITIEU') as dhId";
		ResultSet rsDh = db.get(query);	
		try
		{
			rsDh.next();
		this.setID(rsDh.getDouble("dhId"));
		}catch(Exception ex){
			return false;
		}
		rsDh.close();
		
		String chuoi=this.getKhuVucID();
		
		String sql="select a.ma "+chuoi+" from chitieutmp a inner join  nhaphanphoi npp on npp.pk_seq=a.ma";
		//System.out.println(sql);
		ResultSet rs=db.get(sql);
		//System.out.println("Chuoi Nhom San Pham : "+this.ChuoiNhomSP);
		
		 String [] mang=this.ChuoiNhomSP.split(";");
		 
		 while (rs.next()){
			 for(int k=0;k<mang.length;k++){
				 	sql="insert into CHITIEU_NHAPP_NHOMSP  values('"+this.Id+"','"+rs.getString("ma")+"','"+mang[k]+"','"+rs.getString("manhom"+(k+1))+"')";
				 	//System.out.println(sql);
				 	if(!db.update(sql)){
						db.update("rollback");
						this.setMessage(sql);
						return false;
					}
			 }
		 }
		
		 sql=" insert into chitieu_nhapp select  chitieu_fk,npp_fk,sum(chitieu) "+
	 		" from CHITIEU_NHAPP_NHOMSP where chitieu_fk="+this.Id+" group by chitieu_fk,npp_fk ";
		 if(!db.update(sql)){
				db.update("rollback");
				this.setMessage(sql);
				return false;
			}
		db.getConnection().commit();
		db.getConnection().setAutoCommit(true);
		}catch(Exception er){
			db.update("rollback");
			this.setMessage("Ban khong the them chi tieu moi nay, loi : " + er.toString());
			return false;
		}
		return true;

	}
	
	public boolean EditChiTieu() {

		
		try{
			if(!KiemTraHopLe()){
				return false;
			}
			
			db.getConnection().setAutoCommit(false);
			String sql_checkexit="select pk_seq from ChiTieu where thang= " +this.Thang+" and nam="+ this.Nam +" and dvkd_fk="+this.Dvkd_id +" and kenh_fk="+this.KenhId+" and trangthai!=2 and pk_seq <>" +this.getID();
			//System.out.println(sql_checkexit);
		   
			ResultSet rs_check=db.get(sql_checkexit);
			if(rs_check!=null){
				if(rs_check.next()){//co du lieu
					this.setMessage("Chi Tieu Trong Thang Da Thiet Lap cho cac nha phan phoi thuoc khu vuc nay, Vui Long Kiem Tra Lai");
				 return false;
				}
			}
			
			//update lai bang chitieu_trungtam;
		String sqlEditChiTieu="update CHITIEU set THANG="+this.Thang+",NAM="+this.Nam+",CHITIEU="+this.ChiTieu+",NGAYKETTHUC='"+this.NgayKetThuc+
		"',NGAYSUA='"+this.NgaySua+"',NGUOISUA='"+this.NguoiSua+"',DIENGIAI='"+this.DienGiai+"' ,dvkd_fk="+this.Dvkd_id+" ,songaylamviec="+this.SoNgayLamViec+" ,kenh_fk="+this.KenhId+"  where pk_seq="+ this.Id;
	
		if(!db.update(sqlEditChiTieu)){
			//System.out.println("SQL : "+ sqlEditChiTieu);
			this.Message="Loi  : "+ sqlEditChiTieu;
			db.update("rollback");
			return false;
		}
		
	
		String chuoi=this.getKhuVucID();
		
		String sql="delete CHITIEU_NHAPP_NHOMSP where chitieu_fk= "+this.Id;
		if(!db.update(sql)){
			db.update("rollback");
			this.setMessage(sql);
			return false;
		}
		
		 sql="delete CHITIEU_NHAPP where chitieu_fk= "+this.Id;
		if(!db.update(sql)){
			db.update("rollback");
			this.setMessage(sql);
			return false;
		}
		
		 sql="select a.ma "+chuoi+" from chitieutmp a inner join  nhaphanphoi npp on npp.pk_seq=a.ma";
		ResultSet rs=db.get(sql);
		 String [] mang=this.ChuoiNhomSP.split(";");
		 
		 while (rs.next()){
			 for(int k=0;k<mang.length;k++){
				 	sql="insert into CHITIEU_NHAPP_NHOMSP  values('"+this.Id+"','"+rs.getString("ma")+"','"+mang[k]+"','"+rs.getString("manhom"+(k+1))+"')";
				 	if(!db.update(sql)){
						db.update("rollback");
						this.setMessage(sql);
						return false;
					}
			 }
		 }
		 sql=" insert into chitieu_nhapp(chitieu_fk,nhapp_fk,chitieu ) select  chitieu_fk,npp_fk,sum(chitieu) "+
		 		" from CHITIEU_NHAPP_NHOMSP where chitieu_fk="+this.Id+" group by chitieu_fk,npp_fk ";
		 if(!db.update(sql)){
				db.update("rollback");
				this.setMessage(sql);
				return false;
			}
		 
		db.getConnection().commit();
		db.getConnection().setAutoCommit(true);
		}catch(Exception er){
			db.update("rollback");
			
			this.Message="Khong the Chinh Sua Chi Tieu :"+ er.toString();
			return false;
		}
		return true;
	}
	
	public boolean DeleteChitieu() {
		
		dbutils db=new dbutils();	
		try{
		db.getConnection().setAutoCommit(false);
		//kiem tra chi tieu phia nha phan phoi xem da co dung chi tieu nay chua
		//khong can kiem tra tai vi chi tieu nay khong pha bo xuong nha dai dien kinh doanh
		String sqlDelChiTieu="update CHITIEU set trangthai='2' where pk_seq="+ this.Id;
		if(!db.update(sqlDelChiTieu)){//khong xoa duoc
		  db.update("rollback");
		  return false;
		}
		db.getConnection().commit();
		db.getConnection().setAutoCommit(true);
		}catch(Exception er){
			db.update("rollback");
			this.setMessage("Ban khong the xoa chi tieu moi nay, loi : " + er.toString());
			return false;
		}
        return false;
	}

	
	public void setUserId(String userid) {

		this.UserID=userid;
	}


	public String getUserId() {

		return this.UserID;
	}


	public List<ChiTieu> getChiTieu() {

		return listchitieu;
	}

	
	public void setListChiTieu(String sql,String loaict) {
		String  sql_getdata="";

		Utility Ult = new Utility(); 
		/*if(loaict.equals("0")){//loai chi tieu la secondary
			  sql_getdata="SELECT    c.trangthai,kenh_fk, C.PK_SEQ, C.THANG, C.NAM, C.CHITIEU,C.DIENGIAI, C.NGAYKETTHUC,C.DVKD_FK,DONVIKINHDOANH,C.SONGAYLAMVIEC, C.NGAYTAO, C.NGAYSUA,NT.TEN AS NGUOITAO, "+ 
			  "NS.TEN AS NGUOISUA FROM dbo.CHITIEU AS C inner join donvikinhdoanh as d on d.pk_seq=c.DVKD_FK  INNER JOIN "+
			  "dbo.NHANVIEN AS NT ON C.NGUOITAO = NT.PK_SEQ INNER JOIN dbo.NHANVIEN AS NS ON C.NGUOISUA = NS.PK_SEQ where 1=1" +
			  //" and  c.pk_seq in (select chitieu_fk from CHITIEU_NHAPP where nhapp_fk in " + Ult.quyen_npp(this.UserID) +")"+
			  " and kenh_fk in "+ Ult.quyen_kenh(this.UserID);
      
          
		}else{//truong hop la primary
	
*/			  
		if(loaict.equals("1"))
		{
				sql_getdata="SELECT   C.PK_SEQ,c.trangthai,kenh_fk, C.THANG, C.NAM,  C.CHITIEU,C.DIENGIAI,C.SONGAYLAMVIEC, C.NGAYTAO, C.NGAYSUA,NT.TEN AS NGUOITAO, "+ 
			            "NT.PK_SEQ AS NGUOISUA FROM dbo.CHITIEU_SEC AS C INNER JOIN "+
			            "dbo.NHANVIEN AS NT ON C.NGUOITAO = NT.PK_SEQ INNER JOIN dbo.NHANVIEN AS NS ON C.NGUOISUA = NS.PK_SEQ" +
			            "  where 1=1 ";
				     		System.out.println("SQL"+sql_getdata);
				if(sql!=""){
					sql_getdata=sql;
				} 
			  try
			  {
			  ResultSet rsChiTieu=	db.get(sql_getdata);
			  if(rsChiTieu!=null){
				  while(rsChiTieu.next()){
				    ChiTieu CT=new ChiTieu();
				    CT.setID(rsChiTieu.getDouble("PK_SEQ"));
				    CT.setThang(rsChiTieu.getInt("THANG"));
				    CT.setNam(rsChiTieu.getInt("NAM"));
				  	 CT.setNgayTao(rsChiTieu.getString("NGAYTAO"));
				    CT.setNgaySua(rsChiTieu.getString("NGAYSUA"));
				    CT.setDienGiai(rsChiTieu.getString("DIENGIAI"));
				     CT.setNguoiTao(rsChiTieu.getString("NGUOITAO"));
				    CT.setNguoiSua(rsChiTieu.getString("NGUOISUA"));
				    CT.setSoNgayLamViec(rsChiTieu.getString("SONGAYLAMVIEC"));
				    CT.setTrangThai(rsChiTieu.getString("trangthai"));
				    CT.setKenhId(rsChiTieu.getString("kenh_fk"));
				    listchitieu.add(CT);
				  }
			  }
			  }catch(Exception er)
			  {
				er.printStackTrace();
			  }
		}
		else
		{
			sql_getdata="SELECT   C.PK_SEQ,c.trangthai,kenh_fk,C.quy, C.NAM,C.DIENGIAI,'' as DVKD_FK, '' as donvikinhdoanh,C.SONGAYLAMVIEC, C.NGAYTAO, C.NGAYSUA,NT.TEN AS NGUOITAO,c.nhapp_fk, "+ 
		            "NT.PK_SEQ AS NGUOISUA FROM dbo.Chitieunhanvien_OTC AS C INNER JOIN "+
		            "dbo.NHANVIEN AS NT ON C.NGUOITAO = NT.PK_SEQ INNER JOIN dbo.NHANVIEN AS NS ON C.NGUOISUA = NS.PK_SEQ" ;
		            
			System.out.println("::::::::::::::::"+sql_getdata);
			if(sql!=""){
				sql_getdata=sql;
			} 
		  try
		  {
		  ResultSet rsChiTieu=	db.get(sql_getdata);
		  if(rsChiTieu!=null){
			  while(rsChiTieu.next()){
			    ChiTieu CT=new ChiTieu();
			    CT.setID(rsChiTieu.getDouble("PK_SEQ"));
			    CT.setNam(rsChiTieu.getInt("NAM"));
			    CT.setNgayTao(rsChiTieu.getString("NGAYTAO"));
			    CT.setNgaySua(rsChiTieu.getString("NGAYSUA"));
			    CT.setDienGiai(rsChiTieu.getString("DIENGIAI"));
			    CT.setTenDVKD(rsChiTieu.getString("DONVIKINHDOANH"));
			    CT.setDVKDID(rsChiTieu.getString("DVKD_FK"));
			    CT.setNguoiTao(rsChiTieu.getString("NGUOITAO"));
			    CT.setNguoiSua(rsChiTieu.getString("NGUOISUA"));
			    CT.setSoNgayLamViec(rsChiTieu.getString("SONGAYLAMVIEC"));
			    CT.setTrangThai(rsChiTieu.getString("trangthai"));
			    CT.setKenhId(rsChiTieu.getString("kenh_fk"));
			    CT.setQuy(rsChiTieu.getInt("quy"));
			    CT.setNhapp(rsChiTieu.getString("nhapp_fk"));
			    listchitieu.add(CT);
			  }
		  }
		  }catch(Exception er)
		  {
			er.printStackTrace();
		  }
		}
		
	}


	
	public void setTenKhuVuc(String tenkhuvuc) {

		this.TenKhuVuc=tenkhuvuc;
	}

	
	public String getTenKhuVuc() {

		return this.TenKhuVuc;
	}

	
	public void setNguoiTao(String nguoitao) {

		this.NguoiTao=nguoitao;		
	}

	
	public String getNguoiTao() {

		return this.NguoiTao;
	}

	
	public void setNguoiSua(String nguoisua) {

		this.NguoiSua=nguoisua;
	}

	
	public String getNguoiSua() {

		return this.NguoiSua;
	}

	
	public void setMessage(String strmessage) {

		this.Message=strmessage;
	}

	
	public String getMessage() {

		return this.Message;
	}

	public List<ChiTieuNPP> getListChiTieuNPP() {

		return this.listchitieunpp;
	}

	public void setListChiTieuNPP(List<ChiTieuNPP> list) {

		this.listchitieunpp=list;
	}
	
	public void setDVKDID(String dvkdid) {

		this.Dvkd_id=dvkdid;
	}
	
	public String getDVKDId() {

		return this.Dvkd_id;
	}
	
	public void setTenDVKD(String tendvkd) {

		this.TenDVkd=tendvkd;
	}
	
	public String getTenDVKD() {

		return this.TenDVkd;
	}
	
	public boolean SaveChiTieu_Sec() {

	
		try
		{
			if(!KiemTraHopLe()){
				return false;
			}
		db.getConnection().setAutoCommit(false);
	
		double tongchitieusellsout=0;
		
		//kiem tra xenm chi tieu trong thoi gian nay da co chua
		String sql="select pk_seq from CHITIEU_SEC where thang= " +this.Thang+" and nam="+ this.Nam +" and kenh_fk="+this.KenhId+" ";
		//System.out.println("sql : "+sql);
		ResultSet rs_check=db.get(sql);
		if(rs_check!=null){
			if(rs_check.next()){//co du lieu
				this.setMessage("Chi Tieu Trong Thang Da Thiet Lap cho cac nha phan phoi thuoc khu vuc nay, Vui Long Kiem Tra Lai");
				
				return false;
			}
		}
		
		// thuc hien insert
		String sqlSaveChiTieu="insert into CHITIEU_SEC(LOAICHITIEU,THANG,NAM,CHITIEU,NGAYKETTHUC,NGAYTAO,NGUOITAO,NGAYSUA,NGUOISUA,TRANGTHAI,songaylamviec,kenh_fk,diengiai,nhomkenh) " +
		"values ('"+this.LoaiChiTieu+"',"+this.Thang+","+this.Nam+","+this.ChiTieu+",'"+this.NgayKetThuc+"','"+this.NgayTao+"','"+this.NguoiTao+"','"+this.NgaySua+"','"+this.NguoiSua+"','0',"+this.SoNgayLamViec+","+this.KenhId+",'"+this.DienGiai+"',"+this.nhomkenh+")";
		if(!db.update(sqlSaveChiTieu)){
			//System.out.println("sqlSaveChiTieu : 146 Chitieuttchovung " +sqlSaveChiTieu);
			this.Message="Loi :"+sqlSaveChiTieu;
			
			db.update("rollback");
			return false;
		}
		
		//thuc hien insert chi tiet
		String query = "select IDENT_CURRENT('CHITIEU_SEC') as dhId";
		ResultSet rsDh = db.get(query);	
		try
		{
			rsDh.next();
		this.setID(rsDh.getDouble("dhId"));
			rsDh.close();
		}catch(Exception ex){
			return false;
		}
		
		//Chuoi de lay nhung nhom hang dua vao
		String chuoi=this.getKhuVucID();
		
		 sql="select NPP.PK_SEQ,a.ma,a.chitieumuavao,a.chitieubanra,a.songaytonkhoquydinh,a.donhang,a.tylegiaohangthanhcong,a.tylechinhxactonkho"+chuoi+" FROM " +
		 		" chitieutmp a inner join NHAPHANPHOI NPP on NPP.pk_seq=a.ma where chucvu='NPP' order by NPP.PK_SEQ";

		
		 ResultSet rs=db.get(sql);
		
		 String idchitieunpp="";

		if(rs!=null){
			while (rs.next()){
				
				double chitieusellsout=0;
				 String [] mang=this.ChuoiNhomSP.split(";");
				 for(int k=0;k<mang.length;k++){
					sql="insert into CHITIEUSEC_NHAPP_NHOMSP values('"+this.Id+"','"+rs.getString("ma")+"','"+mang[k]+"','"+rs.getDouble("manhom"+(k+1))+"')";
					//System.out.println(sql);
					chitieusellsout=chitieusellsout+ rs.getDouble("manhom"+(k+1));
				
					 if(!db.update(sql)){
							db.update("rollback");
							this.setMessage(sql);
							return false;
						}
				 }
				
				/* if(rs.getDouble("sellsout")!=0){
					 chitieusellsout=rs.getDouble("sellsout");
				 }*/
				 tongchitieusellsout=tongchitieusellsout+ chitieusellsout;
					String sql_insertdetail="insert into chitieu_nhapp_chitiet(CHITIEU_NPP_fk,nhapp_fk,chitieumuavao,chitieubanra,sodonhang,songaytonkhoquydinh,tylegiaohangthanhcong,tylechinhxactonkho) values("+this.getID()+","+rs.getString("ma")+","+rs.getFloat("chitieumuavao")+","+rs.getFloat("chitieubanra")+",'"+rs.getInt("donhang")+"','"+rs.getInt("songaytonkhoquydinh")+"',"+rs.getFloat("tylegiaohangthanhcong")+","+rs.getFloat("tylechinhxactonkho")+")";
					if(!db.update(sql_insertdetail)){
						db.update("rollback");
						////System.out.println("sqlSaveChiTieu : 681 Chitieuttchovung " +sql_insertdetail);
						this.Message= "Khong the them chi tieu chi tieu :" +sql_insertdetail;
						return false;
						
					}
			
				
			}

			
		}
		
		
		
		 rs.close();
		 
		db.getConnection().commit();
		db.getConnection().setAutoCommit(true);
		}catch(Exception er)
		{
			db.update("rollback");
			this.setMessage("Ban khong the them chi tieu moi nay, loi : " + er.toString());
			er.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean EditChiTieu_Sec() {

		
		try
		{
			if(!KiemTraHopLe()){
				return false;
			}
		db.getConnection().setAutoCommit(false);
	
		double tongchitieusellsout=0;
		
		//kiem tra xenm chi tieu trong thoi gian nay da co chua
		String sql="select pk_seq from CHITIEU_SEC where thang= " +this.Thang+" and nam="+ this.Nam +" and dvkd_fk="+this.Dvkd_id +" and kenh_fk="+this.KenhId+" and trangthai!=2 and pk_seq <>"+this.Id;
		//System.out.println("sql : "+sql);
		ResultSet rs_check=db.get(sql);
		if(rs_check!=null){
			if(rs_check.next()){//co du lieu
				this.setMessage("Chi Tieu Trong Thang Da Thiet Lap cho cac nha phan phoi thuoc khu vuc nay, Vui Long Kiem Tra Lai");
				
				return false;
			}
		}
		
	
	String	sqlSaveChiTieu="update CHITIEU_SEC set LOAICHITIEU="+this.LoaiChiTieu+",thang="+this.Thang+",nam="+this.Nam+",chitieu="+this.ChiTieu+",ngayketthuc='"+this.NgayKetThuc+"',ngaysua='"+this.NgaySua+"',DVKD_FK="+this.Dvkd_id+",songaylamviec="+this.SoNgayLamViec+",kenh_fk="+this.KenhId+",diengiai='"+this.DienGiai+"',nhomkenh="+this.nhomkenh+" where pk_seq="+this.Id; 
		//thuc hien insert chi tiet
		if(!db.update(sqlSaveChiTieu)){
			//System.out.println("sqlSaveChiTieu : 146 Chitieuttchovung " +sqlSaveChiTieu);
			this.Message="Loi :"+sqlSaveChiTieu;
			
			db.update("rollback");
			return false;
		}
		
		//Chuoi de lay nhung nhom hang dua vao
		String chuoi=this.getKhuVucID();
		
		 sql="select NPP.PK_SEQ,a.ma,a.chitieumuavao,a.chitieubanra,a.songaytonkhoquydinh,a.donhang,a.tylegiaohangthanhcong,a.tylechinhxactonkho"+chuoi+" FROM " +
		 		" chitieutmp a inner join NHAPHANPHOI NPP on NPP.pk_seq=a.ma where chucvu='NPP' order by NPP.PK_SEQ";

		
		 ResultSet rs=db.get(sql);
		
		 String idchitieunpp="";
		 String sql1="delete from chitieu_nhapp_chitiet where chitieu_npp_fk="+this.Id;
		 if(db.updateReturnInt(sql1)<1)
		 {
			 db.update("rollback");
				this.setMessage(sql1);
				return false;
		 }
		 sql1="delete from CHITIEUSEC_NHAPP_NHOMSP where chitieusec_fk="+this.Id;
		 if(db.updateReturnInt(sql1)<1)
		 {
			 db.update("rollback");
				this.setMessage(sql1);
				return false;
		 }
		if(rs!=null){
			while (rs.next()){
				
				double chitieusellsout=0;
				 String [] mang=this.ChuoiNhomSP.split(";");
				 for(int k=0;k<mang.length;k++){
					sql="insert into CHITIEUSEC_NHAPP_NHOMSP values('"+this.Id+"','"+rs.getString("ma")+"','"+mang[k]+"','"+rs.getDouble("manhom"+(k+1))+"')";
					//System.out.println(sql);
					chitieusellsout=chitieusellsout+ rs.getDouble("manhom"+(k+1));
				
					 if(!db.update(sql)){
							db.update("rollback");
							this.setMessage(sql);
							return false;
						}
				 }
				
				/* if(rs.getDouble("sellsout")!=0){
					 chitieusellsout=rs.getDouble("sellsout");
				 }*/
				 tongchitieusellsout=tongchitieusellsout+ chitieusellsout;
					String sql_insertdetail="insert into chitieu_nhapp_chitiet(CHITIEU_NPP_fk,nhapp_fk,chitieumuavao,chitieubanra,sodonhang,songaytonkhoquydinh,tylegiaohangthanhcong,tylechinhxactonkho) values("+this.getID()+","+rs.getString("ma")+","+rs.getFloat("chitieumuavao")+","+rs.getFloat("chitieubanra")+",'"+rs.getInt("donhang")+"','"+rs.getInt("songaytonkhoquydinh")+"',"+rs.getFloat("tylegiaohangthanhcong")+","+rs.getFloat("tylechinhxactonkho")+")";
					if(!db.update(sql_insertdetail)){
						db.update("rollback");
						////System.out.println("sqlSaveChiTieu : 681 Chitieuttchovung " +sql_insertdetail);
						this.Message= "Khong the them chi tieu chi tieu :" +sql_insertdetail;
						
						return false;
						
					}
			
				
			}

			
		}
		
		
		
		 rs.close();
		 
		db.getConnection().commit();
		db.getConnection().setAutoCommit(true);
		}catch(Exception er)
		{
			db.update("rollback");
			this.setMessage("Ban khong the them chi tieu moi nay, loi : " + er.toString());
			er.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean DeleteChitieu_Sec() {

		
		try{
			
			
			
		db.getConnection().setAutoCommit(false);
		
		String sql="select thang,nam,kenh_fk,dvkd_fk from chitieu_sec where pk_seq="+ this.Id;
		ResultSet rs=this.db.get(sql);
		if(rs!=null){
			if(rs.next()){
				this.Thang=rs.getInt("thang");
				this.Nam=rs.getInt("nam");
				this.Dvkd_id=rs.getString("dvkd_fk");
				this.KenhId=rs.getString("kenh_fk");
			}
		}
	
		 //XOA CHI TIEU CAC NHA PHAN PHOI
		 
		 
		 
		 sql="delete chitieu_nhapp_chitiet where chitieu_npp_fk="+this.Id;
		// //System.out.println(sql);
		 if(!db.update(sql)){
				db.update("rollback");
				this.setMessage(sql);
				return false;
			}
		
		 sql="delete CHITIEUSEC_NHAPP_NHOMSP where chitieusec_fk="+this.Id;
		 ////System.out.println(sql);
		 if(!db.update(sql)){
				db.update("rollback");
				this.setMessage(sql);
				return false;
			}
		 sql="delete CHITIEU_SEC  where pk_seq="+ this.Id;
		if(!db.update(sql)){//khong xoa duoc
		  db.update("rollback");
		  this.setMessage("Vui Long Kiem Tra Lai.Loi :"+sql);
		  return false;
		}
		
		db.getConnection().commit();
		db.getConnection().setAutoCommit(true);
		}catch(Exception er){
			db.update("rollback");
			this.setMessage("Ban khong the xoa chi tieu moi nay, loi : " + er.toString());
			return false;
		}
        return false;
	}
	
	public void setSoNgayLamViec(String songaylamviec) {

		this.SoNgayLamViec=songaylamviec;
	}
	
	public String getSoNgayLamViec() {

		return this.SoNgayLamViec;
	}
	
	public String getKenhId() {

		return this.KenhId;
	}
	
	public void setKenhId(String kenhid) {

		this.KenhId=kenhid;
	}
	
	public ResultSet getRsKenh() {

		return this.rsKenh;
		
	}
	
	public String getTenKenh() {

		String sql="select pk_seq,ten from kenhbanhang where pk_seq="+this.KenhId;
	
		ResultSet rs=db.get(sql);
		if(rs!=null){
			try{
			if(rs.next()){
				 String ten= rs.getString("ten");
				rs.close();
				return ten;
				 
			}
			}catch(Exception er){
				return "";
			}
		}
	   return "";
	}
	
	public void setTrangThai(String trangthai) {

		this.TrangThai=trangthai;
	}
	
	public String getTrangThai() {

		return this.TrangThai;
	}
	
	public boolean ChotChiTieu() {
		
		
		try{
		db.getConnection().setAutoCommit(false);
		String sqlchot="update CHITIEU set trangthai='1' where pk_seq="+ this.Id;
		if(!db.update(sqlchot)){//khong xoa duoc
		  db.update("rollback");
		  return false;
		}
		db.getConnection().commit();
		db.getConnection().setAutoCommit(true);
		}catch(Exception er){
			db.update("rollback");
			return false;
		}
        return true;
	}
	
	public boolean ChotChiTieu_Sec() {

		
		//System.out.println("slq chot  : "+sqlchot);
	
		try{
		db.getConnection().setAutoCommit(false);
		String sql="update CHITIEU_SEC set trangthai='1' where pk_seq="+ this.Id;
		if(!db.update(sql)){//khong xoa duoc
		  db.update("rollback");
		  return false;
		}
		//chot trang thai duoi nha phan phoi de nha phan phoi bat dau sua cap nhat cho npp sua là 3
		/* sql="update b set b.trangthai=1 "+
		  	" from chitieunpp b inner join chitieu_sec a on "+
		  	" a.thang=b.thang and a.nam=b.nam and a.dvkd_fk=b.dvkd_fk and a.kenh_fk=b.kenh_fk "+
		  	" where a.pk_seq=" + this.Id;
		 	if(!db.update(sql)){
			  db.update("rollback");
			  return false;
			}*/
		db.getConnection().commit();
		db.getConnection().setAutoCommit(true);
		}catch(Exception er){
			db.update("rollback");
			return false;
		}
        return true;
	
	}
	
	public void closeDB() {

		try{
			this.rsChiTieuNV.close();
			if(this.db!=null){
				db.shutDown();
			}
		}catch(Exception er){
			
		}
	}
	
	public boolean UnChotChiTieu_Sec() {

		
		
		
		try{
		db.getConnection().setAutoCommit(false);
		String sql="update CHITIEU_SEC set trangthai='0' where pk_seq="+ this.Id;
		System.out.println("uncgot "+sql);
		if(!db.update(sql)){//khong xoa duoc
		  db.update("rollback");
		  return false;
		}
		
		
		
		db.getConnection().commit();
		db.getConnection().setAutoCommit(true);
		}catch(Exception er){
			db.update("rollback");
			return false;
		}
        return true;
	}
	
	public ResultSet getRsChitieunhanvien() {

		return this.rsChiTieuNV;
	}
	
	public String[] getNhomSp() {

		return this.NhomSP;
	}
	
	public String getChuoiNhomSp() {

		return this.ChuoiNhomSP;
	}
	
	public void setChuoiNhomSp(String chuoinhomsp) {

		this.ChuoiNhomSP=chuoinhomsp;
	}
	
	public void setRsPri(String sql) {

	//Utility Ult=new Utility();	
		if(sql.equals("")){
			sql = "SELECT   C.PK_SEQ,c.trangthai,kbh.ten as kenh, C.THANG, C.NAM,  C.CHITIEU,C.DIENGIAI, C.NGAYKETTHUC,C.DVKD_FK,D.donvikinhdoanh,C.SONGAYLAMVIEC, C.NGAYTAO, C.NGAYSUA,NT.TEN AS NGUOITAO, "+ 
	            "NS.TEN AS NGUOISUA FROM dbo.CHITIEU AS C INNER JOIN "+
	            "dbo.NHANVIEN AS NT ON C.NGUOITAO = NT.PK_SEQ INNER JOIN dbo.NHANVIEN AS NS ON C.NGUOISUA = NS.PK_SEQ" +
	            " inner join DONVIKINHDOANH D on D.pk_seq=C.DVKD_FK " +
	            " inner  join kenhbanhang kbh on kbh.pk_seq=c.kenh_fk " ;
	      /*      " where  "+
	            "   c.pk_seq in (select CHITIEU_FK from CHITIEU_NHAPP where nhapp_fk in " + Ult.quyen_npp(this.UserID) +")"+
	            " and kenh_fk in "+ Ult.quyen_kenh(this.UserID);*/
		}
		//else
		//{
		//	query = sql;
		//}
		System.out.println("Lay Chi Tieu : "+sql);
		this.rsChitieuPri=this.db.get(sql);
	}
	
	public ResultSet rsChitieuPri() {

		return this.rsChitieuPri;
	}
	
	public void DbClose() {

		try{
			this.db.shutDown();
			rsChiTieuNV.close();
			rsChitieuPri.close();
		
			
		}catch(Exception er){
			
		}finally {
			this.db.shutDown();
		}
		
	}
	
	public ResultSet getRsDvdk() {

		return this.RsDVKD;
	}
	
	public void CreateRs() {
		Utility Ult = new Utility();
		String sql="select pk_Seq,ten from  hethongbanhang  where trangthai='1'  ";
		//System.out.println("kenh ban hang:" + sql);
	
		this.rsKenh=  db.get(sql);
		
		sql="select pk_seq,donvikinhdoanh from donvikinhdoanh where trangthai=1";
		
		this.RsDVKD=db.get(sql);
		
		sql="select pk_seq ,ten from NHOMKENH    ";
		this.rsnhomkenh=db.get(sql);
		sql="select pk_seq,ten from NHAPHANPHOI where PRIANDSECOND=1 and TRANGTHAI=1 and pk_seq ="+getnpp();
		System.out.println("nhapp:"+sql);
		this.rs_npp=db.get(sql);
		
	}
	
	public ResultSet rs_chitieuprinpp() {

		return rsChitieuPriNpp;
	}
	
	public boolean DeleteChitieuSkuin() {

		try{
			db.getConnection().setAutoCommit(false);
		String sql="delete chitieu_nhapp_nhomsp where chitieu_fk ="+ this.Id;
		if(!db.update(sql)){//khong xoa duoc
			  db.update("rollback");
			  return false;
			}
		sql="delete chitieu where pk_seq="+this.Id;
		if(!db.update(sql)){//khong xoa duoc
			  db.update("rollback");
			  return false;
			}
		 sql="delete CHITIEU_GSBH_NHOMSP where chitieu_fk="+this.Id;
	
		if(!db.update(sql)){//khong xoa duoc
			  db.update("rollback");
			  return false;
			}
		sql="delete chitieu_nhapp where chitieu_fk="+this.Id;
		if(!db.update(sql)){//khong xoa duoc
			  db.update("rollback");
			  return false;
			}
		db.getConnection().commit();
		
		db.getConnection().setAutoCommit(true);
		
		
		}catch(Exception er){
			return false;
		}
		
		return false;
	}
	
	public boolean ChotChitieuSkuin() {
		try{
			db.getConnection().setAutoCommit(false);
		String sql="delete chitieu_nhapp where chitieu_fk="+this.Id;
		if(!db.update(sql)){//khong xoa duoc
			  db.update("rollback");
			  return false;
			}
		 sql="update chitieu set trangthai='1' where pk_seq="+this.Id;
		 if(!db.update(sql)){//khong xoa duoc
			  db.update("rollback");
			  return false;
		 }
		 
		 
		sql="insert into chitieu_nhapp select "+this.Id+",npp_fk,sum(chitieu) from chitieu_nhapp_nhomsp  " +
				" where chitieu_fk="+this.Id+"  group by npp_fk  ";
		if(!db.update(sql)){//khong xoa duoc
			  db.update("rollback");
			  return false;
			}
		
		 sql="delete CHITIEU_GSBH_NHOMSP where chitieu_fk="+this.Id;
		 //System.out.println(sql);
		if(!db.update(sql)){//khong xoa duoc
			  db.update("rollback");
			  return false;
			}
		///update target salesup
		sql="INSERT INTO CHITIEU_GSBH_NHOMSP "+
		" select "+this.Id+", c.gsbh_fk,nhomsp_fk , sum(b.chitieu) as chitieu "+ 
		" from chitieu ct inner join  "+
		" CHITIEU_NHAPP_NHOMSP  b on ct.pk_seq=b.chitieu_fk  "+
		" inner join nhapp_giamsatbh   c  on b.npp_fk=c.npp_fk "+ 
		" WHERE  CT.PK_SEQ= "+ this.Id + " AND c.NGAYKETTHUC >='"+getDateTime()+"' AND c.NGAYBATDAU <= '" + getDateTime().substring(0,7)+ "-01'"+
		" group by   ct.kenh_fk,ct.dvkd_fk, c.gsbh_fk,nhomsp_fk ";
		   
		//System.out.println(sql);
		   
		    if(!db.update(sql)){//khong xoa duoc
			  db.update("rollback");
			  return false;
			}
		
		
		db.getConnection().commit();
		
		db.getConnection().setAutoCommit(true);
		
		
		}catch(Exception er){
			return false;
		}
		
		return false;
	}
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public boolean UnChotChitieuSkuin() {

		try{
			db.getConnection().setAutoCommit(false);
	
		
		
		String sql="delete chitieu_nhapp where chitieu_fk="+this.Id;
		if(!db.update(sql)){//khong xoa duoc
			  db.update("rollback");
			  return false;
			}
		 sql="update chitieu set trangthai='0' where pk_seq="+this.Id;
		 if(!db.update(sql)){//khong xoa duoc
			  db.update("rollback");
			  return false;
			}
	
		
		db.getConnection().commit();
		
		db.getConnection().setAutoCommit(true);
		
		
		}catch(Exception er){
			return false;
		}
		
		return false;
	}
	
	public void setLoaiChiTieu(String loaichitieu) {

		this.LoaiChiTieu=loaichitieu;
	}
	
	public String getLoaiChiTieu() {

		return this.LoaiChiTieu;
	}
	
	public String GetTumuc() {

		return this.Tumuc;
	}
	
	public void SetTumuc(String tumuc) {

		this.Tumuc=tumuc;
	}
	
	public String GetToimuc() {

		return this.ToiMuc;
	}
	
	public void SetToimuc(String toimuc) {

		this.ToiMuc=toimuc;
	}
	
	public ResultSet getRsNppNhomSp() {

		return RsNppNhomSp;
	}
	
	@Override
	public boolean EditChiTieu(HttpServletRequest request) 
	{
		try
		{
			if(!KiemTraHopLe())
			{
				return false;
			}
			db.getConnection().setAutoCommit(false);
			String sql="select pk_seq from ChiTieu where thang= " +this.Thang+" and nam="+ this.Nam +" and dvkd_fk="+this.Dvkd_id +" and kenh_fk="+this.KenhId+" and trangthai!=2 and pk_seq <>" +this.getID();

		   
			ResultSet rs_check=db.get(sql);
			if(rs_check!=null)
			{
				if(rs_check.next())
				{
					this.setMessage("Chi Tieu Trong Thang Da Thiet Lap cho cac nha phan phoi thuoc khu vuc nay, Vui Long Kiem Tra Lai");
					return false;
				}
				rs_check.close();
			}
			
			sql=
				"	select distinct nhomsp_fk as nspId,b.TEN  as nspTen from CHITIEU_NHAPP_NHOMSP  a "+
			 	"		inner join NHOMSANPHAM  b on b.PK_SEQ=a.NHOMSP_FK "+
			 	"	 where  CHITIEU_FK='"+this.Id+"' ";  
		 	System.out.println("[NhomSanPham]"+sql);
			ResultSet rs=this.db.get(sql);
			String[] nhom =new String[10];
			String[] nhomid =new String[10];
			
			int i=0;
			String strselect="";
			String strngoac="[0]";
			
			if(rs!=null)
			{
				while (rs.next())
				{
					nhomid[i]=rs.getString("nspId");
					nhom[i]=rs.getString("nspTen");
					if(i==0)
					{
						strselect=" ,["+rs.getString("nspId")+"] as CT"+rs.getString("nspId")+"";
						strngoac=" [0], ["+rs.getString("nspId")+"]";
					}else
					{
						strselect=strselect+", ["+rs.getString("nspId")+"] as CT"+rs.getString("nspId")+"";
						strngoac=strngoac+ ",["+rs.getString("nspId")+"]";
					}
					i++;
				}
				rs.close();
			}
			this.NhomSP=nhom;
			this.NhomSPId=nhomid;
			
			sql="update CHITIEU set THANG="+this.Thang+",NAM="+this.Nam+",CHITIEU="+this.ChiTieu+",NGAYKETTHUC='"+this.NgayKetThuc+
			"',NGAYSUA='"+this.NgaySua+"',NGUOISUA='"+this.NguoiSua+"',DIENGIAI='"+this.DienGiai+"' ,dvkd_fk="+this.Dvkd_id+" ,songaylamviec="+this.SoNgayLamViec+" ,kenh_fk="+this.KenhId+"  where pk_seq="+ this.Id;
			if(!db.update(sql))
			{
				this.Message="Loi  : "+ sql;
				db.update("rollback");
				return false;
			}
		
			sql="delete CHITIEU_NHAPP_NHOMSP where chitieu_fk= "+this.Id;
			if(!db.update(sql))
			{
				db.update("rollback");
				this.setMessage(sql);
				return false;
			}
			
			 sql="delete CHITIEU_NHAPP where chitieu_fk= "+this.Id;
			if(!db.update(sql))
			{
				db.update("rollback");
				this.setMessage(sql);
				return false;
			}
			i=0;
			String[] nppId= request.getParameterValues("nppId");
			if(nppId!=null)
			{
				int soNpp= nppId.length;
				while(i<soNpp)
				{
					 for(int k=0;k<NhomSPId.length;k++)
					 {
						 if(NhomSPId[k]!=null)
						 {
							double 	chitieu=0;
							 	String[] mang= request.getParameterValues("ctNhom"+ NhomSPId[k]);
								try
								{
									chitieu=Double.parseDouble( mang[i].replace(",",""));
									}catch(Exception er)
									{
										er.printStackTrace();
										 db.update("rollback");
										 this.setMessage("Vui lòng kiểm tra lại thông tin chỉ tiêu nhóm sản phẩm");
										 return false;
									}
								if(chitieu>0)
								{
									 sql="INSERT INTO CHITIEU_NHAPP_NHOMSP(CHITIEU_FK,NPP_FK,NHOMSP_FK,CHITIEU) " +
											 " values('"+this.Id+"','"+nppId[i]+"','"+NhomSPId[k]+"',"+chitieu+")";
									 if(!db.update(sql))
									 {
										 db.update("rollback");
										 this.setMessage(sql);
										 return false;
									 }	 
								}
							 }
						 }
					i++;
				}
			}
		 sql=" insert into chitieu_nhapp(chitieu_fk,nhapp_fk,chitieu ) select  chitieu_fk,npp_fk,sum(chitieu) "+
		 		" from CHITIEU_NHAPP_NHOMSP where chitieu_fk="+this.Id+" group by chitieu_fk,npp_fk ";
		 if(!db.update(sql))
		 {
			db.update("rollback");
			this.setMessage(sql);
			return false;
		}
		 sql=
		 " update ct set CHITIEU=ctNpp.ChiTieu "+
		 " from "+
		 "	chitieu ct inner join "+ 
		 "( "+
		 "	select SUM(chitieu) as ChiTieu,CHITIEU_FK  from chitieu_nhapp ctNpp "+
		 "	group by CHITIEU_FK "+
		 " )as ctNpp on ctNpp.CHITIEU_FK=ct.PK_SEQ" +
		 " where ctNpp.CHITIEU_FK='"+this.Id+"'  ";
		 
		 if(!db.update(sql))
		 {
			db.update("rollback");
			this.setMessage(sql);
			return false;
		}
		db.getConnection().commit();
		db.getConnection().setAutoCommit(true);
		}catch(Exception er)
		{
			db.update("rollback");
			this.Message="Khong the Chinh Sua Chi Tieu :"+ er.toString();
			er.printStackTrace();
			return false;			
		}
		return true;
	}
	
	@Override
	public boolean SaveChiTieu_Sec(HttpServletRequest request) 
	{
		System.out.println("[SaveChiTieu_Sec]");
		try
		{
			this.db.getConnection().setAutoCommit(false);
			String sql="delete from chitieutmp";
		 if(!this.db.update(sql))
			{
				this.db.getConnection().rollback();
				this.Message= "Khong the them chi tieu chi tieu :" +sql;
				return false;
			}
			if(!KiemTraHopLe())
			{
				return false;
			}
			sql=
			"	select nhomsp_fk as nspId,b.TEN  as nspTen from chitieusec_nhapp_nhomsp  a "+
		 	"		inner join NHOMSANPHAM  b on b.PK_SEQ=a.NHOMSP_FK "+
		 	"	 where  CHITIEUSEC_FK='"+this.Id+"' "+  
		 	"	 union   "+
		 	"	 select distinct nhomsanpham_fk as nspId ,c.TEN as nspTen "+
		 	"	 from chitieunpp_ddkd_nhomsp a inner join CHITIEUNPP b on b.PK_SEQ=a.CHITIEUNPP_FK "+  
		 	"		inner join NHOMSANPHAM  c on a.NHOMSANPHAM_FK=c.PK_SEQ "+
		 	"	 where b.THANG='"+this.Thang+"' and b.NAM='"+this.Nam+"' and b.KENH_FK='"+this.KenhId+"' and b.DVKD_FK='"+this.Dvkd_id+"' "; 
		 	
		 	System.out.println("[NhomSanPham]"+sql);
			ResultSet rs=this.db.get(sql);
			String[] nhom =new String[10];
			String[] nhomid =new String[10];
			
			int i=0;
			String strselect="";
			String strngoac="[0]";
			
			if(rs!=null)
			{
				while (rs.next())
				{
					nhomid[i]=rs.getString("nspId");
					nhom[i]=rs.getString("nspTen");
					if(i==0)
					{
						strselect=" ,["+rs.getString("nspId")+"] as CT"+rs.getString("nspId")+"";
						strngoac=" [0], ["+rs.getString("nspId")+"]";
					}else
					{
						strselect=strselect+", ["+rs.getString("nspId")+"] as CT"+rs.getString("nspId")+"";
						strngoac=strngoac+ ",["+rs.getString("nspId")+"]";
					}
					i++;
				}
				rs.close();
			}
			this.NhomSP=nhom;
			this.NhomSPId=nhomid;
			
			sql="update CHITIEU_SEC set THANG="+this.Thang+",NAM="+this.Nam+",CHITIEU="+this.ChiTieu+",NGAYKETTHUC='"+this.NgayKetThuc+
						"',NGAYSUA='"+this.NgaySua+"',NGUOISUA='"+this.NguoiSua+"',dvkd_fk="+this.Dvkd_id+",songaylamviec="+this.SoNgayLamViec+",diengiai=N'"+this.DienGiai+"' ,kenh_fk="+this.KenhId+" where pk_seq="+ this.Id;
			 if(!this.db.update(sql))
				{
					this.db.getConnection().rollback();
					this.Message= "Khong the them chi tieu chi tieu :" +sql;
					return false;
				}
			sql="delete chitieunpp_ddkd where chitieunpp_fk in (select ctnpp.pk_seq from chitieunpp ctnpp inner join chitieu_sec ct on ct.thang=ctnpp.thang and ct.nam=ctnpp.nam and ct.dvkd_fk=ctnpp.dvkd_fk and ct.kenh_fk=ctnpp.kenh_fk where ct.pk_seq='"+this.Id+"' )";
			 if(!this.db.update(sql))
				{
					this.db.getConnection().rollback();
					this.Message= "Khong the them chi tieu chi tieu :" +sql;
					return false;
				}
			sql="delete CHITIEUNPP_DDKD_NHOMSP where chitieunpp_fk in  (select ctnpp.pk_seq from chitieunpp ctnpp inner join chitieu_sec ct on ct.thang=ctnpp.thang and ct.nam=ctnpp.nam and ct.dvkd_fk=ctnpp.dvkd_fk and ct.kenh_fk=ctnpp.kenh_fk where ct.pk_seq='"+this.Id+"' )";

			 if(!this.db.update(sql))
				{
					this.db.getConnection().rollback();
					this.Message= "Khong the them chi tieu chi tieu :" +sql;
					return false;
				}
			 
			 sql="delete CHITIEUSEC_GSBH where chitieusec_fk="+this.Id;
			 if(!this.db.update(sql))
				{
					this.db.getConnection().rollback();
					this.Message= "Khong the them chi tieu chi tieu :" +sql;
					return false;
				}
			 
			 sql="delete CHITIEUSEC_GSBH_NHOMSP where chitieusec_fk="+this.Id;
			 if(!this.db.update(sql))
				{
					this.db.getConnection().rollback();
					this.Message= "Khong the them chi tieu chi tieu :" +sql;
					return false;
				}
			 
			 sql="delete CHITIEUSEC_ASM where chitieusec_fk="+this.Id;
			 if(!this.db.update(sql))
				{
					this.db.getConnection().rollback();
					this.Message= "Khong the them chi tieu chi tieu :" +sql;
					return false;
				}
			 
			 sql="delete CHITIEUSEC_ASM_NHOMSP where chitieusec_fk="+this.Id;
			 if(!this.db.update(sql))
				{
					this.db.getConnection().rollback();
					this.Message= "Khong the them chi tieu chi tieu :" +sql;
					return false;
				}
			 
			 sql="delete CHITIEUSEC_NHAPP_NHOMSP where chitieusec_fk="+this.Id;
			 if(!this.db.update(sql))
				{
					this.db.getConnection().rollback();
					this.Message= "Khong the them chi tieu chi tieu :" +sql;
					return false;
				}
			 
			 sql="delete CHITIEU_NHAPP_SEC where CHITIEU_SEC_FK='"+this.Id+"'";
			 if(!this.db.update(sql))
			{
				this.db.getConnection().rollback();
				this.Message= "Khong the them chi tieu chi tieu :" +sql;
				return false;
			}
			 
			String[] nppId = request.getParameterValues("nppId");
			String[] ctSoDonHang = request.getParameterValues("ctSoDonHang");
			String[] ctSanLuong = request.getParameterValues("ctSanLuong");
			String[] ctSalesOut = request.getParameterValues("ctSalesOut");
			i=0;
			
			System.out.println("[SoNhaPhanPhoi]"+nppId.length+"[]"+ctSoDonHang.length+"[]"+ctSanLuong.length+"[]"+ctSalesOut.length);
			
			while (nppId!=null&&i< nppId.length )
			{
				
				double	ct_SoDonHang=0;
				double	ct_SanLuong=0;
				double	ct_SalesOut=0;
				try
				{
					ct_SalesOut=Double.parseDouble(ctSalesOut[i].replace(",",""));
					ct_SoDonHang=Double.parseDouble(ctSoDonHang[i].replace(",",""));
					ct_SanLuong=Double.parseDouble(ctSanLuong[i].replace(",",""));
				}catch(Exception er)
				{
					er.printStackTrace();
				}
				if(ct_SalesOut>0||ct_SoDonHang>0||ct_SanLuong>0)
				{
					 sql=
						"INSERT INTO CHITIEU_NHAPP_SEC(CHITIEU_SEC_FK,NHAPP_FK,CHITIEU,SODONHANG,SANLUONGTRENDH)  " +
						" values("+this.Id+","+nppId[i]+","+ ct_SalesOut+",'"+ct_SoDonHang+"','"+ct_SanLuong+"')";
					 System.out.println("[Dong]"+i+"[nppId]"+nppId[i]+"[Insert]"+sql);
					if(!this.db.update(sql))
					{
						this.db.getConnection().rollback();
						this.Message= "Khong the them chi tieu chi tieu :" +sql;
						return false;
					}
				}
				if(this.NhomSPId!=null && this.NhomSPId.length>0)
				{
					 for(int k=0;k<NhomSPId.length;k++)
					 {
						 if(NhomSPId[k]!=null)
						 {
							double  ctNhom=0;
							String[] mang= request.getParameterValues("ctNppNspId_"+ NhomSPId[k]);
							try
							{
								ctNhom=Double.parseDouble( mang[i].replace(",",""));
							}catch(Exception er){er.printStackTrace();}
						
							if(ctNhom>0)
							{
								sql="insert into CHITIEUSEC_NHAPP_NHOMSP (CHITIEUSEC_FK,NPP_FK,NHOMSP_FK,CHITIEU)  " +
									" values('"+this.Id+"','"+ nppId[i]+"','"+NhomSPId[k]+"',"+ctNhom+")";
								System.out.println("[Dong]"+i+"[nppId]"+nppId[i]+"[Insert]"+sql);
								 if(!db.update(sql))
								 {
									 this.db.getConnection().rollback();
									this.setMessage(sql);
									return false;
								 }	 
							}
						 }
					 }
				}
				i++;
			}
		 	String[] ddkd_NppId = request.getParameterValues("ddkd_NppId");
		   	String[] ddkdId = request.getParameterValues("ddkdId");
			String[] ctSoDonHang_Ddkd = request.getParameterValues("ctSoDonHang_Ddkd");
			String[] ctSanLuong_Ddkd = request.getParameterValues("ctSanLuong_Ddkd");
			String[] ctSalesOut_Ddkd = request.getParameterValues("ctSalesOut_Ddkd");
			
			String nppid_bk="";
			String ctid="";
			 i=0;
			 
			 System.out.println("[SoDaiDien]"+ddkd_NppId.length+"[ctSoDonHang_Ddkd]"+ctSoDonHang_Ddkd.length+"[ctSanLuong_Ddkd]"+ctSanLuong_Ddkd.length+"[ctSalesOut_Ddkd]"+ctSalesOut_Ddkd.length);
			 
			
			
		if(ddkd_NppId!=null)
			{	
				while(i<  ddkdId.length)
				{
					if(!ddkd_NppId[i].trim().equals(nppid_bk))
					{
						nppid_bk=ddkd_NppId[i].trim();
						ctid=getCtidNpp(this.db,nppid_bk);
						if(ctid.equals(""))
						{
							this.db.getConnection().rollback();
							this.setMessage("không tạo được chỉ tiêu dưới NPP :" + nppid_bk);
							return false;
						}
						  sql="delete CHITIEUNPP_DDKD where CHITIEUNPP_FK="+ctid+" " ;
						   if(!db.update(sql))
						   {
							   this.db.getConnection().rollback();
								this.setMessage(sql);
								return false;
							}
						 sql="delete CHITIEUNPP_DDKD_NHOMSP where CHITIEUNPP_FK="+ctid+" " ;
						 	if(!db.update(sql))
						    {
						 		this.db.getConnection().rollback();
								this.setMessage(sql);
								return false;
							}
						 	
						 System.out.println("[IdCtNpp]"+ctid);
					}
				
					double	ct_SoDonHang=0;
					double	ct_SanLuong=0;
					double	ct_SalesOut=0;
					try
					{
						ct_SalesOut=Double.parseDouble(ctSalesOut_Ddkd[i].replace(",",""));
						ct_SoDonHang=Double.parseDouble(ctSoDonHang_Ddkd[i].replace(",",""));
						ct_SanLuong=Double.parseDouble(ctSanLuong_Ddkd[i].replace(",",""));
					}catch(Exception er){er.printStackTrace();}
					if(ct_SalesOut>0||ct_SoDonHang>0||ct_SanLuong>0)
					{
						sql="INSERT INTO CHITIEUNPP_DDKD(CHITIEUNPP_FK,DDKD_FK,CHITIEU,SODONHANG,SANLUONGTRENDH)" +
								" values ("+ctid+","+ddkdId[i]+","+ct_SalesOut+",'"+ct_SoDonHang+"','"+ct_SanLuong+"')";
						 System.out.println("[Dong]"+i+"[DDKD_FK]"+ddkdId[i]+"[Insert]"+sql);
						if(!db.update(sql))
					    {
							this.db.getConnection().rollback();
							this.setMessage(sql);
							return false;
						}
					}					
					
					for(int k=0;k<NhomSPId.length;k++)
					 {
						 if(NhomSPId[k]!=null)
						 {
							double chitieu=0;
								String[] mang= request.getParameterValues("ctDdkdNspId_"+ NhomSPId[k]);
								try
								{
									chitieu=Double.parseDouble( mang[i].replace(",",""));
								}catch(Exception er)
								{
									er.printStackTrace();
								}
								if(chitieu>0)
								{
									 sql="INSERT INTO CHITIEUNPP_DDKD_NHOMSP (CHITIEUNPP_FK,DDKD_FK,NHOMSANPHAM_FK,CHITIEU)  " +
											" values('"+ctid+"','"+ ddkdId[i]+"','"+NhomSPId[k]+"',"+chitieu+")";
									 System.out.println("[Dong]"+i+"[DDKD_FK]"+ddkdId[i]+"[Insert]"+sql);
									 if(!db.update(sql))
									 {
										 this.db.getConnection().rollback();
										this.setMessage(sql);
										return false;
									}	 
								}
						 }
					 }
					 i++;
				}
			}
			sql=
			"update c set CHITIEU=a.CHITIEU "+
			"	from  chitieu_nhapp_sec a inner join CHITIEU_SEC b on b.PK_SEQ=a.CHITIEU_SEC_FK "+
			"	inner join CHITIEUNPP c on c.THANG=b.THANG and c.NAM=b.NAM "+
			"	and c.DVKD_FK=b.DVKD_FK and c.KENH_FK=b.KENH_FK and c.NHAPP_FK=a.NHAPP_FK " +
			" where a.CHITIEU_SEC_FK='"+this.Id+"' ";
			 if(!db.update(sql))
			 {
				this.db.getConnection().rollback();
				this.setMessage("__Lỗi cập nhật__"+sql);
				return false;
			}	
			 sql=" insert into CHITIEUSEC_GSBH_NHOMSP(ChiTieuSEC_FK,GSBH_FK,NHOMSANPHAM_FK,CHITIEU)   SELECT  "+this.Id+ ",B.GSBH_FK,A.NHOMSANPHAM_FK,SUM(A.CHITIEU) FROM CHITIEUNPP_DDKD_NHOMSP A INNER JOIN "+
					 	" CHITIEUNPP CT ON CT.PK_SEQ=CHITIEUNPP_FK "+
					 	" INNER JOIN DDKD_GSBH B ON B.DDKD_FK= A.DDKD_FK "+
					 	" WHERE CT.THANG="+this.getThang()+" AND CT.NAM="+this.getNam()+" and CT.KENH_FK="+this.getKenhId()+"  AND CT.DVKD_FK="+this.getDVKDId()+
					 	"  GROUP BY B.GSBH_FK,A.NHOMSANPHAM_FK";
			if(!db.update(sql))
			{
				db.update("rollback");
				System.out.println("sqlSaveChiTieu : 681 Chitieuttchovung " +sql);
				this.Message= "Khong the them chi tieu chi tieu :" +sql;
				return false;
			}
			 sql="INSERT INTO CHITIEUSEC_GSBH (chitieusec_fk,gsbh_fk,dophu,donhang,chitieu,sku,sanluongtrendh ) "+
			 " SELECT "+this.Id+",B.GSBH_FK ,ISNULL(SUM(DOPHU),0),ISNULL( SUM(SODONHANG),0),ISNULL(SUM(A.CHITIEU),0),ISNULL(SUM(SKU),0),ISNULL(SUM(sanluongtrendh),0) "+
			 " FROM CHITIEUNPP_DDKD A INNER JOIN  "+
			 " CHITIEUNPP CT ON CT.PK_SEQ=CHITIEUNPP_FK "+
			" INNER JOIN DDKD_GSBH B ON B.DDKD_FK= A.DDKD_FK "+
			" WHERE CT.THANG="+this.getThang()+" AND CT.NAM="+this.getNam()+" and CT.KENH_FK="+this.getKenhId()+"  AND CT.DVKD_FK="+this.getDVKDId()+
			" GROUP BY B.GSBH_FK "; 
		 	if(!db.update(sql))
		 	{
				db.update("rollback");
				this.Message= "Khong the them chi tieu chi tieu :" +sql;
				return false;
			}
//			update lai tong chi tieu cua npp
			 sql="update CHITIEU_SEC  set CHITIEU=isnull((select sum(chitieu) from chitieu_nhapp_sec a where a.CHITIEU_SEC_fk=pk_seq),0) where pk_Seq ="+this.Id;
			if(!db.update(sql))
			{
				db.update("rollback");
				this.setMessage("Vui Long Kiem Tra Lai.Loi :"+sql);
				return false;	
			}
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		} catch (Exception e)
		{
			e.printStackTrace();
			this.setMessage("Exception__"+e.getMessage());
			this.db.update("rollback");
			return false;
		}
		System.out.println("End_________");
		return true;
	}
	
	private String getCtidNpp(dbutils db2,String Nppid) 
	{
		try
		{
			
			String chitieunppid="";
			String  sql=" select pk_seq from  chitieunpp where thang="+this.Thang+" and nam="+this.Nam + 
			" and dvkd_fk= "+this.Dvkd_id +"  and  nhapp_fk ="+Nppid+" and kenh_fk ="+this.KenhId;
			
			ResultSet rsce=db2.get(sql);
			if(rsce.next())
			{
				chitieunppid=rsce.getString("pk_seq");
			}else
			{
				sql=" insert into chitieunpp (thang,nam,chitieu,nhapp_fk,ngaytao,nguoitao,ngaysua,nguoisua,trangthai,dvkd_fk,songaylamviec,kenh_fk,ngayketthuc,diengiai)" +
					" values ('"+this.Thang+"','"+this.Nam+"','"+0+"','"+Nppid+"','"+this.getDateTime()+"','"+this.getUserId()+"'," +
					"'"+this.getDateTime()+"','"+this.getUserId()+"','0','"+this.Dvkd_id +"','"+this.SoNgayLamViec+"','"+this.KenhId+"' ,'"+this.NgayKetThuc+"',N'"+this.DienGiai+"')";
				if(!db2.update(sql))
				{
					this.setMessage(sql);
					return "";
				}
			 
				String query = "select IDENT_CURRENT('chitieunpp') as dhId";
				ResultSet rsDh = db2.get(query);	
				try
				{
					rsDh.next();
					chitieunppid= rsDh.getString("dhId");
					rsDh.close();
				}catch(Exception ex)
				{
					this.Message=ex.toString();
					ex.printStackTrace();
					return "";
				}
			}
			rsce.close();
			return chitieunppid;
			}catch(Exception err)
			{
				err.printStackTrace();
				return "";
			}
	}
	
	@Override
	public String[] getNhomSpId() {

		return this.NhomSPId;
	}
	
	
	public boolean SaveChiTieuNhanVien_Sec(int stt) {

		
		try
		{
			if(!KiemTraHopLe()){
				return false;
			}		
		double tongchitieusellsout=0;
		if(stt==0)
		{
			//kiem tra xenm chi tieu trong thoi gian nay da co chua
			String sql="select pk_seq from Chitieunhanvien_OTC where quy= " +this.Quy+" and nam="+ this.Nam +" and dvkd_fk="+this.Dvkd_id +" and kenh_fk="+this.KenhId+" ";
			//System.out.println("sql : "+sql);
			ResultSet rs_check=db.get(sql);
			if(rs_check!=null){
				if(rs_check.next()){//co du lieu
					this.setMessage("Chi Tieu Trong Quy Da Thiet Lap cho cac nhan vien thuoc khu vuc nay, Vui Long Kiem Tra Lai");
					
					return false;
				}
			}
			
			// thuc hien insert
			String sqlSaveChiTieu="insert into Chitieunhanvien_OTC(LOAICHITIEU,NAM,NGAYTAO,NGUOITAO,NGAYSUA,NGUOISUA,TRANGTHAI,songaylamviec,kenh_fk,diengiai,quy,chotthang1,chotthang2,chotthang3,nhapp_FK) " +
			"values ('1',"+this.Nam+",'"+this.NgayTao+"','"+this.NguoiTao+"','"+this.NgaySua+"','"+this.NguoiSua+"','0',"+this.SoNgayLamViec+","+this.KenhId+",'"+this.DienGiai+"',"+this.Quy+",0,0,0,"+this.nhapp+")";
			if(!db.update(sqlSaveChiTieu)){
				//System.out.println("sqlSaveChiTieu : 146 Chitieuttchovung " +sqlSaveChiTieu);
				this.Message="Loi :"+sqlSaveChiTieu;
				return false;
			}
			
				//thuc hien insert chi tiet
			String query = "select IDENT_CURRENT('Chitieunhanvien_OTC') as dhId";
			ResultSet rsDh = db.get(query);	
			try
			{
				rsDh.next();
			this.setID(rsDh.getDouble("dhId"));
				rsDh.close();
			}catch(Exception ex){
				return false;
			}
		
		}
		System.out.println("id chi tieu"+this.Id);
		//Chuoi de lay nhung nhom hang dua vao
		String chuoi=this.getKhuVucID();
		
		String sql="select a.ma,a.chitieubanraSL,a.chitieubanra,a.chucvu,a.solanviengtham,a.socuahangmuahang,a.sodonhangtrongngay,a.giatridonhangtoithieu,a.giaohangthanhcong,a.SKU,chitieu_banra_FK,chitieu_barasl_FK,chitieu_solanviengtham,chitieu_socuahangmuahang,chitieu_sodonhangtrongngay,chitieu_giatridonhangtoithieu,chitieu_SKU,chitieu_tylegiaohangthanhcong"+chuoi+" FROM " +
		 		" chitieutmp a  ";
System.out.println("cau lay data templt"+sql);
		
		 ResultSet rs=db.get(sql);
		
		 String idchitieunpp="";
		 int first = 0;
		 int last=0;
		 if(this.Quy==1)
		 {
			 first=1;
			 last=4;
		 }
		 if(this.Quy==2)
		 {
			 first=4;
			 last=7;
		 }
		 if(this.Quy==3)
		 {
			 first=7;
			 last=10;
		 }
		 if(this.Quy==4)
		 {
			 first=10;
			 last=13;
		 }
		 int h=0;
		 if(stt==0)
			 h=first;
		 if(stt==1)
			 h=first+1;
		 if(stt==2)
			 h=last-1;
		 if(stt==2)
		 {
			 System.out.println("toi v3");
		 }
		if(rs!=null){
			while (rs.next()){
				System.out.println("vao fetch-------------"+stt);
				double chitieusellsout=0;
				 String [] mang=this.ChuoiNhomSP.split(";");
				 
				
					 for(int k=0;k<mang.length;k++){
					
						
						System.out.println("ma nhom  manhom"+(k+1)+"------"+rs.getInt("manhom"+(k+1)));
						 int chitieunspk =(int)(rs.getInt("manhom"+(k+1)));
							 sql="insert into Chitieunhanvien_OTC_SR_NSP (chitieu_nv_fk,nhanvien_fk,nhomsp_fk,chitieu,thang,nam,loaichitieu_fk,quy) values('"+this.getID()+"','"+rs.getString("ma")+"','"+mang[k]+"','"+chitieunspk+"',"+h+","+this.Nam+","+rs.getDouble("chitieu_Manhom"+(k+1))+","+this.Quy+")";
							 if(!db.update(sql)){
								
								this.setMessage(sql);
								return false;
							}			 
							sql="insert into Chitieunhanvien_OTC_QUY_SR_NSP (chitieu_nv_fk,nhanvien_fk,nhomsp_fk,chitieu,thang,nam,loaichitieu_fk,quy) values('"+this.getID()+"','"+rs.getString("ma")+"','"+mang[k]+"','"+rs.getDouble("manhom"+(k+1))+"',"+h+","+this.Nam+","+rs.getDouble("chitieu_Manhom"+(k+1))+","+this.Quy+")";	
							 if(!db.update(sql)){
									
									this.setMessage(sql);
									return false;
								}
							 
						 
							
						 } 
						
				 
			
					 int chitieu=rs.getInt("chitieubanraSL");
					
					 
					 int chitieubanra=rs.getInt("chitieubanra");
					  
					 int solanviengtham=rs.getInt("solanviengtham");
					 
					 
					 int socuahangmuahang=rs.getInt("socuahangmuahang");
					 
					 int sodonhangtrongngay=rs.getInt("socuahangmuahang");
					 
					 int giatridonhangtoithieu=rs.getInt("giatridonhangtoithieu");
					
					 int giaohangthanhcong=rs.getInt("giaohangthanhcong");
					 					 
					 int SKU=rs.getInt("SKU");
 
					String sql_insertdetail1="insert into Chitieunhanvien_OTC_SR(CHITIEU_nv_fk,nhanvien_fk,chucvu,chitieubanraSL,chitieubanra,solanviengtham,socuahangmuahang,sodonhangtrongngay,giatridonhangtoithieu,giaohangthanhcong,SKU,thang,nam,quy) values("+this.getID()+","+rs.getString("ma")+",'"+rs.getString("chucvu")+"',"+chitieu+","+chitieubanra+",'"+solanviengtham+"','"+socuahangmuahang+"',"+sodonhangtrongngay+","+giatridonhangtoithieu+","+giaohangthanhcong+","+SKU+","+h+","+this.Nam+","+this.Quy+")";
					if(!db.update(sql_insertdetail1)){
						
						this.Message= "Khong the them chi tieu chi tieu :" +sql_insertdetail1;
						return false;
						
					}		
			}

		 rs.close();
		}
		}catch(Exception er)
		{
			er.printStackTrace();
			this.setMessage("Ban khong the them chi tieu moi nay, loi : " + er.toString());
			er.printStackTrace();
			return false;
		}
		return true;
	}
	
public boolean EditChiTieu_NV_Sec(int stt) {	
	
	
	
	
	try{

		if(!KiemTraHopLe()){
			return false;
		}	
		
	double tongchitieusellsout=0;
	if(stt==0)
	{
		//kiem tra xenm chi tieu trong thoi gian nay da co chua
		String sql="select pk_seq from Chitieunhanvien_OTC where quy= " +this.Quy+" and nam="+ this.Nam +" and dvkd_fk="+this.Dvkd_id +" and kenh_fk="+this.KenhId+" ";
		//System.out.println("sql : "+sql);
		ResultSet rs_check=db.get(sql);
		if(rs_check!=null){
			if(rs_check.next()){//co du lieu
				this.setMessage("Chi Tieu Trong Quy Da Thiet Lap cho cac nhan vien thuoc khu vuc nay, Vui Long Kiem Tra Lai");
				
				return false;
			}
		}
		
		
		String sqlSaveChiTieu="update Chitieunhanvien_OTC set LOAICHITIEU="+this.LoaiChiTieu + ", quy="+this.Quy +",nam="+this.Nam+",ngaysua='"+this.NgaySua+"',nguoisua="+this.NguoiSua+",songaylamviec="+this.SoNgayLamViec+",diengiai='"+this.DienGiai+"' where pk_seq="+this.Id;
		System.out.println("::::::::::::::::::::::update"+sqlSaveChiTieu);
		
		if(!db.update(sqlSaveChiTieu)){
			//System.out.println("sqlSaveChiTieu : 146 Chitieuttchovung " +sqlSaveChiTieu);
			this.Message="Loi :"+sqlSaveChiTieu;
			
			db.update("rollback");
			return false;
		}
		
		sqlSaveChiTieu="delete from Chitieunhanvien_OTC_SR WHERE CHITIEU_NV_FK="+this.Id;
		if(!db.update(sqlSaveChiTieu)){
			//System.out.println("sqlSaveChiTieu : 146 Chitieuttchovung " +sqlSaveChiTieu);
			this.Message="Loi :"+sqlSaveChiTieu;
			db.update("rollback");
			return false;
		}
		sqlSaveChiTieu="delete from Chitieunhanvien_OTC_SR_NSP WHERE CHITIEU_NV_FK="+this.Id;
		if(!db.update(sqlSaveChiTieu)){
			//System.out.println("sqlSaveChiTieu : 146 Chitieuttchovung " +sqlSaveChiTieu);
			this.Message="Loi :"+sqlSaveChiTieu;
			db.update("rollback");
			return false;
		}
		
	}
	System.out.println("id chi tieu"+this.Id);
	//Chuoi de lay nhung nhom hang dua vao
	String chuoi=this.getKhuVucID();
	
	String sql="select a.ma,a.chitieubanraSL,a.chitieubanra,a.chucvu,a.solanviengtham,a.socuahangmuahang,a.sodonhangtrongngay,a.giatridonhangtoithieu,a.giaohangthanhcong,a.SKU,chitieu_banra_FK,chitieu_barasl_FK,chitieu_solanviengtham,chitieu_socuahangmuahang,chitieu_sodonhangtrongngay,chitieu_giatridonhangtoithieu,chitieu_SKU,chitieu_tylegiaohangthanhcong"+chuoi+" FROM " +
	 		" chitieutmp a  ";
System.out.println("cau lay data templt"+sql);
	
	 ResultSet rs=db.get(sql);
	
	 String idchitieunpp="";
	 int first = 0;
	 int last=0;
	 if(this.Quy==1)
	 {
		 first=1;
		 last=4;
	 }
	 if(this.Quy==2)
	 {
		 first=4;
		 last=7;
	 }
	 if(this.Quy==3)
	 {
		 first=7;
		 last=10;
	 }
	 if(this.Quy==4)
	 {
		 first=10;
		 last=13;
	 }
	 int h=0;
	 if(stt==0)
		 h=first;
	 if(stt==1)
		 h=first+1;
	 if(stt==2)
		 h=last-1;
	 if(stt==2)
	 {
		 System.out.println("toi v3");
	 }
	if(rs!=null){
		while (rs.next()){
			
			double chitieusellsout=0;
			 String [] mang=this.ChuoiNhomSP.split(";");
			 for(int k=0;k<mang.length;k++){
				 int chitieunspk =(int)(rs.getInt("manhom"+(k+1)));
				 sql="insert into Chitieunhanvien_OTC_SR_NSP (chitieu_nv_fk,nhanvien_fk,nhomsp_fk,chitieu,thang,nam,loaichitieu_fk,quy) values('"+this.getID()+"','"+rs.getString("ma")+"','"+mang[k]+"','"+chitieunspk+"',"+h+","+this.Nam+","+rs.getDouble("chitieu_Manhom"+(k+1))+","+this.Quy+")";
					
				//System.out.println(sql);
				chitieusellsout=chitieusellsout+ rs.getDouble("manhom"+(k+1));
			
				 if(!db.update(sql)){
						db.update("rollback");
						this.setMessage(sql);
						return false;
					}
			 }
			
			/* if(rs.getDouble("sellsout")!=0){
				 chitieusellsout=rs.getDouble("sellsout");
			 }*/
			 int chitieu=rs.getInt("chitieubanraSL");
				
			 
			 int chitieubanra=rs.getInt("chitieubanra");
			  
			 int solanviengtham=rs.getInt("solanviengtham");
			 
			 
			 int socuahangmuahang=rs.getInt("socuahangmuahang");
			 
			 int sodonhangtrongngay=rs.getInt("socuahangmuahang");
			 
			 int giatridonhangtoithieu=rs.getInt("giatridonhangtoithieu");
			
			 int giaohangthanhcong=rs.getInt("giaohangthanhcong");
			 					 
			 int SKU=rs.getInt("SKU");

			String sql_insertdetail1="insert into Chitieunhanvien_OTC_SR(CHITIEU_nv_fk,nhanvien_fk,chucvu,chitieubanraSL,chitieubanra,solanviengtham,socuahangmuahang,sodonhangtrongngay,giatridonhangtoithieu,giaohangthanhcong,SKU,thang,nam,quy) values("+this.getID()+","+rs.getString("ma")+",'"+rs.getString("chucvu")+"',"+chitieu+","+chitieubanra+",'"+solanviengtham+"','"+socuahangmuahang+"',"+sodonhangtrongngay+","+giatridonhangtoithieu+","+giaohangthanhcong+","+SKU+","+h+","+this.Nam+","+this.Quy+")";
			if(!db.update(sql_insertdetail1)){
				this.Message= "Khong the them chi tieu chi tieu :" +sql_insertdetail1;
				return false;
				
			}		
		
			
		}
		 rs.close();
		 
	}
	
	}catch (Exception e)
	{
		this.Message=e.toString();
	}
		return true;
	}

public boolean DeleteChitieu_NV_Sec() {

	
	try{
		
		
		
	db.getConnection().setAutoCommit(false);
	
	String sql="select thang,nam,kenh_fk,dvkd_fk from Chitieunhanvien_OTC where pk_seq="+ this.Id;
	ResultSet rs=this.db.get(sql);
	if(rs!=null){
		if(rs.next()){
			this.Thang=rs.getInt("thang");
			this.Nam=rs.getInt("nam");
			this.Dvkd_id=rs.getString("dvkd_fk");
			this.KenhId=rs.getString("kenh_fk");
		}
	}

	 //XOA CHI TIEU CAC NHA PHAN PHOI
	 
	 
	 
	 sql="delete Chitieunhanvien_OTC_SR where chitieu_nv_fk="+this.Id;
	// //System.out.println(sql);
	 if(!db.update(sql)){
			db.update("rollback");
			this.setMessage(sql);
			return false;
		}
	
	 sql="delete Chitieunhanvien_OTC_SR_NSP where chitieu_nv_fk="+this.Id;
	 ////System.out.println(sql);
	 if(!db.update(sql)){
			db.update("rollback");
			this.setMessage(sql);
			return false;
		}
	 sql="delete Chitieunhanvien_OTC  where pk_seq="+ this.Id;
	if(!db.update(sql)){//khong xoa duoc
	  db.update("rollback");
	  this.setMessage("Vui Long Kiem Tra Lai.Loi :"+sql);
	  return false;
	}
	
	db.getConnection().commit();
	db.getConnection().setAutoCommit(true);
	}catch(Exception er){
		db.update("rollback");
		this.setMessage("Ban khong the xoa chi tieu moi nay, loi : " + er.toString());
		return false;
	}
    return false;
}
public boolean ChotChiTieu_NV_Sec() {

	
	//System.out.println("slq chot  : "+sqlchot);

	try{
	db.getConnection().setAutoCommit(false);
	String sql="update Chitieunhanvien_OTC set trangthai='1' where pk_seq="+ this.Id;
	if(!db.update(sql)){//khong xoa duoc
	  db.update("rollback");
	  return false;
	}
	//chot trang thai duoi nha phan phoi de nha phan phoi bat dau sua cap nhat cho npp sua là 3
	/* sql="update b set b.trangthai=1 "+
	  	" from chitieunpp b inner join chitieu_sec a on "+
	  	" a.thang=b.thang and a.nam=b.nam and a.dvkd_fk=b.dvkd_fk and a.kenh_fk=b.kenh_fk "+
	  	" where a.pk_seq=" + this.Id;
	 	if(!db.update(sql)){
		  db.update("rollback");
		  return false;
		}*/
	db.getConnection().commit();
	db.getConnection().setAutoCommit(true);
	}catch(Exception er){
		db.update("rollback");
		return false;
	}
    return true;

}
public boolean UnChotChiTieu_NV_Sec() {

	
	
	
	try{
	db.getConnection().setAutoCommit(false);
	String sql="update Chitieunhanvien_OTC set trangthai='0' where pk_seq="+ this.Id;
	if(!db.update(sql)){//khong xoa duoc
	  db.update("rollback");
	  return false;
	}
	
	
	
	db.getConnection().commit();
	db.getConnection().setAutoCommit(true);
	}catch(Exception er){
		db.update("rollback");
		return false;
	}
    return true;
}


public boolean SaveChiTieu_nhanvien(HttpServletRequest request) 
{
	System.out.println("[SaveChiTieu_Sec]");
	try
	{
		this.db.getConnection().setAutoCommit(false);
		String sql="delete from chitieutmp";
	 if(!this.db.update(sql))
		{
			this.db.getConnection().rollback();
			this.Message= "Khong the them chi tieu chi tieu :" +sql;
			return false;
		}
		if(!KiemTraHopLe())
		{
			return false;
		}
	/*	sql=
		"	select nhomsp_fk as nspId,b.TEN  as nspTen from chitieusec_nhapp_nhomsp  a "+
	 	"		inner join NHOMSANPHAM  b on b.PK_SEQ=a.NHOMSP_FK "+
	 	"	 where  CHITIEUSEC_FK='"+this.Id+"' "+  
	 	"	 union   "+
	 	"	 select distinct nhomsanpham_fk as nspId ,c.TEN as nspTen "+
	 	"	 from chitieunpp_ddkd_nhomsp a inner join CHITIEUNPP b on b.PK_SEQ=a.CHITIEUNPP_FK "+  
	 	"		inner join NHOMSANPHAM  c on a.NHOMSANPHAM_FK=c.PK_SEQ "+
	 	"	 where b.THANG='"+this.Thang+"' and b.NAM='"+this.Nam+"' and b.KENH_FK='"+this.KenhId+"' and b.DVKD_FK='"+this.Dvkd_id+"' "; 
*/	 	
		sql=	"	select distinct nhomsp_fk as nspId,b.TEN  as nspTen  from Chitieunhanvien_OTC_SR_NSP  a "+
			 	"		inner join NHOMSANPHAM  b on b.PK_SEQ=a.NHOMSP_FK "+
			 	"	 where  CHITIEU_NV_FK='"+this.Id+"' and tinhthuong=1 ";  
		
	 	System.out.println("[NhomSanPham]"+sql);
		ResultSet rs=this.db.get(sql);
		String[] nhom =new String[10];
		String[] nhomid =new String[10];
		
		int i=0;
		String strselect="";
		String strngoac="[0]";
		
		if(rs!=null)
		{
			while (rs.next())
			{
				nhomid[i]=rs.getString("nspId");
				nhom[i]=rs.getString("nspTen");
				if(i==0)
				{
					strselect=" ,["+rs.getString("nspId")+"] as CT"+rs.getString("nspId")+"";
					strngoac=" [0], ["+rs.getString("nspId")+"]";
				}else
				{
					strselect=strselect+", ["+rs.getString("nspId")+"] as CT"+rs.getString("nspId")+"";
					strngoac=strngoac+ ",["+rs.getString("nspId")+"]";
				}
				i++;
			}
			rs.close();
		}
		this.NhomSP=nhom;
		this.NhomSPId=nhomid;
		
		sql="update Chitieunhanvien_OTC set Quy="+this.Quy+",NAM="+this.Nam+""+
					",NGAYSUA='"+this.NgaySua+"',NGUOISUA='"+this.NguoiSua+"',dvkd_fk="+this.Dvkd_id+",songaylamviec="+this.SoNgayLamViec+",diengiai=N'"+this.DienGiai+"' ,kenh_fk="+this.KenhId+",loaichitieu="+this.LoaiChiTieu+",nhapp_FK="+this.nhapp+" where pk_seq="+ this.Id;
		 if(!this.db.update(sql))
			{
				this.db.getConnection().rollback();
				this.Message= "Khong the them chi tieu chi tieu :" +sql;
				return false;
			}
		sql="delete Chitieunhanvien_OTC_SR where chitieu_nv_fk="+this.Id;
		 if(!this.db.update(sql))
			{
				this.db.getConnection().rollback();
				this.Message= "Khong the them chi tieu chi tieu :" +sql;
				return false;
			}
		sql="delete Chitieunhanvien_OTC_SR_NSP where chitieu_nv_fk="+this.Id;

		 if(!this.db.update(sql))
			{
				this.db.getConnection().rollback();
				this.Message= "Khong the them chi tieu chi tieu :" +sql;
				return false;
			}
		 
		 int t1=0;
		 int t2=0;
		 int t3=0;
		 if(this.Quy==1)
		 {
			 t1=1;
			 t2=2;
			 t3=3;
		 }
		 if(this.Quy==2)
		 {
			 t1=4;
			 t2=5;
			 t3=6;
		 }
		 if(this.Quy==3)
		 {
			 t1=7;
			 t2=8;
			 t3=9;
		 }
		 if(this.Quy==4)
		 {
			 t1=10;
			 t2=11;
			 t3=12;
		 }
		 
		String[] tennv_1 = request.getParameterValues("tennv_1");
		String[] manv_1 = request.getParameterValues("manv_1");
		String[] chucvu_1 = request.getParameterValues("chucvu_1");
		String[] ctmuavao_1 = request.getParameterValues("ctmuavao_1");
		String[] ctbanra_1 = request.getParameterValues("ctbanra_1");
		String[] ctSOLANVIENGTHAM_1 = request.getParameterValues("ctSOLANVIENGTHAM_1");
		String[] ctSOCUAHANGMUAHANG_1 = request.getParameterValues("ctSOCUAHANGMUAHANG_1");
		String[] ctSODONHANGTRONGNGAY_1 = request.getParameterValues("ctSODONHANGTRONGNGAY_1");
		String[] ctGIATRIDONHANGTOITHIEU_1 = request.getParameterValues("ctGIATRIDONHANGTOITHIEU_1");
		String[] ctSKU_1 = request.getParameterValues("ctSKU_1");
		String[] ctGIAOHANGTHANHCONG_1 = request.getParameterValues("ctGIAOHANGTHANHCONG_1");

		
		
		String[] tennv_2 = request.getParameterValues("tennv_2");
		String[] manv_2 = request.getParameterValues("manv_2");
		String[] chucvu_2 = request.getParameterValues("chucvu_2");
		String[] ctmuavao_2 = request.getParameterValues("ctmuavao_2");
		String[] ctbanra_2 = request.getParameterValues("ctbanra_2");
		String[] ctSOLANVIENGTHAM_2 = request.getParameterValues("ctSOLANVIENGTHAM_2");
		String[] ctSOCUAHANGMUAHANG_2 = request.getParameterValues("ctSOCUAHANGMUAHANG_2");
		String[] ctSODONHANGTRONGNGAY_2 = request.getParameterValues("ctSODONHANGTRONGNGAY_2");
		String[] ctGIATRIDONHANGTOITHIEU_2 = request.getParameterValues("ctGIATRIDONHANGTOITHIEU_2");
		String[] ctSKU_2 = request.getParameterValues("ctSKU_2");
		String[] ctGIAOHANGTHANHCONG_2 = request.getParameterValues("ctGIAOHANGTHANHCONG_2");
		
		
		String[] tennv_3 = request.getParameterValues("tennv_3");
		String[] manv_3 = request.getParameterValues("manv_3");
		String[] chucvu_3 = request.getParameterValues("chucvu_3");
		String[] ctmuavao_3 = request.getParameterValues("ctmuavao_3");
		String[] ctbanra_3 = request.getParameterValues("ctbanra_3");
		String[] ctSOLANVIENGTHAM_3 = request.getParameterValues("ctSOLANVIENGTHAM_3");
		String[] ctSOCUAHANGMUAHANG_3 = request.getParameterValues("ctSOCUAHANGMUAHANG_3");
		String[] ctSODONHANGTRONGNGAY_3 = request.getParameterValues("ctSODONHANGTRONGNGAY_3");
		String[] ctGIATRIDONHANGTOITHIEU_3 = request.getParameterValues("ctGIATRIDONHANGTOITHIEU_3");
		String[] ctSKU_3 = request.getParameterValues("ctSKU_3");
		String[] ctGIAOHANGTHANHCONG_3 = request.getParameterValues("ctGIAOHANGTHANHCONG_3");
		i=0;
			
		
		while (tennv_1!=null && i< tennv_1.length )
		{
			String ct_ma="";
			String	ct_ten="";
			String	ct_chucvu="";
			int	ct_muavao=0;
			int	ct_banra=0;
			int	ct_solanviengtham=0;
			int	ct_socuahangmuahang=0;
			int	ct_sodonhangtrongngay=0;
			int	ct_giatridonhangtoithieu=0;
			int	ct_sku=0;
			float	ct_giaohangthanhcong=0;
			try
			{
				String sqlget="select CHITIEU_nv_fk,nhanvien_fk,chucvu,chitieubanraSL,chitieubanra,solanviengtham,socuahangmuahang,sodonhangtrongngay,giatridonhangtoithieu,giaohangthanhcong,SKU,thang,nam,quy from Chitieunhanvien_OTC_Quy_SR where chitieu_nv_fk="+this.Id+"and thang="+t1+" and nam="+this.Nam;
				ResultSet rsget=db.get(sqlget);
				while (rsget.next())
				{
					float rsct_muavao=rsget.getFloat("chitieubanraSL");
					float rsctct_banra=rsget.getFloat("chitieubanra");
					float rsctct_solanviengtham=rsget.getFloat("solanviengtham");
					float rsctct_socuahangmuahang=rsget.getFloat("socuahangmuahang");
					float rsctsodonhangtrongngayL_muavao=rsget.getFloat("sodonhangtrongngay");
					float rsctgiatridonhangtoithieu_muavao=rsget.getFloat("giatridonhangtoithieu");
					float rsctgiaohangthanhcong_muavao=rsget.getFloat("giaohangthanhcong");
					float rsctSKU_muavao=rsget.getFloat("SKU");
					
					
				}
				ct_ten=tennv_1[i].replace(",","");
				ct_ma=manv_1[i].replace(",","");
				ct_chucvu=chucvu_1[i].replace(",","");
				ct_muavao=Integer.parseInt(ctmuavao_1[i].replace(",",""));
				ct_banra=Integer.parseInt(ctbanra_1[i].replace(",",""));
				ct_solanviengtham=Integer.parseInt(ctSOLANVIENGTHAM_1[i].replace(",",""));
				ct_socuahangmuahang=Integer.parseInt(ctSOCUAHANGMUAHANG_1[i].replace(",",""));
				ct_sodonhangtrongngay=Integer.parseInt(ctSODONHANGTRONGNGAY_1[i].replace(",",""));
				ct_giatridonhangtoithieu=Integer.parseInt(ctGIATRIDONHANGTOITHIEU_1[i].replace(",",""));
				ct_sku=Integer.parseInt(ctSKU_1[i].replace(",",""));
				ct_giaohangthanhcong=Float.parseFloat(ctGIAOHANGTHANHCONG_1[i].replace(",",""));
				
				
			}catch(Exception er)
			{
				er.printStackTrace();
			}
			
				/* sql=
					"INSERT INTO CHITIEU_NHAPP_SEC(CHITIEU_SEC_FK,NHAPP_FK,CHITIEU,SODONHANG,SANLUONGTRENDH)  " +
					" values("+this.Id+","+nppId[i]+","+ ct_SalesOut+",'"+ct_SoDonHang+"','"+ct_SanLuong+"')";
				 System.out.println("[Dong]"+i+"[nppId]"+nppId[i]+"[Insert]"+sql);*/
			try {	 
				String sql_insertdetail="insert into Chitieunhanvien_OTC_SR(CHITIEU_nv_fk,nhanvien_fk,chucvu,chitieubanraSL,chitieubanra,solanviengtham,socuahangmuahang,sodonhangtrongngay,giatridonhangtoithieu,giaohangthanhcong,SKU,thang,nam,quy) "+
						 "values("+this.Id+","+ct_ma+",'"+ct_chucvu+"',"+ct_muavao+","+ct_banra+",'"+ct_solanviengtham+"','"+ct_socuahangmuahang+"',"+ct_sodonhangtrongngay+","+ct_giatridonhangtoithieu+","+ct_giaohangthanhcong+","+ct_sku+","+t1+","+this.Nam+","+this.Quy+")";		
				System.out.println("sql_insertdetail"+sql_insertdetail);
				if(!this.db.update(sql_insertdetail))
				{
					this.db.getConnection().rollback();
					this.Message= "Khong the them chi tieu chi tieu :" +sql_insertdetail;
					return false;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
				
			
			if(this.NhomSPId!=null && this.NhomSPId.length>0)
			{
				 for(int k=0;k<NhomSPId.length;k++)
				 {
					 if(NhomSPId[k]!=null)
					 {
						double  ctNhom=0;
						double  ct_nhom_fk=0;
						String[] mang= request.getParameterValues("ctNppNspId1_"+ NhomSPId[k]);
						String[] mang_ct= request.getParameterValues("TC_ctNppNspId1_"+ NhomSPId[k]);
						try
						{
							ctNhom=Double.parseDouble( mang[i].replace(",",""));
							ct_nhom_fk=Double.parseDouble( mang_ct[i].replace(",",""));
						}catch(Exception er){er.printStackTrace();}
					
						if(ctNhom>0)
						{
							System.out.println("chitieu_FK"+this.Id);
							sql="insert into Chitieunhanvien_OTC_SR_NSP (chitieu_nv_fk,nhanvien_fk,nhomsp_fk,chitieu,thang,nam,loaichitieu_fk,quy) "
									+ "values('"+this.Id+"','"+ct_ma+"','"+NhomSPId[k]+"','"+ctNhom+"',"+t1+","+this.Nam+","+ct_nhom_fk+","+this.Quy+")";
							
							System.out.println(sql);
							 if(!db.update(sql))
							 {
								 this.db.getConnection().rollback();
								this.setMessage(sql);
								return false;
							 }	 
						}
					 }
				 }
			}
			i++;
		}
	
		i=0;
		while (tennv_2!=null && i< tennv_2.length )
		{
			String ct_ma="";
			String	ct_ten="";
			String	ct_chucvu="";
			int	ct_muavao=0;
			int	ct_banra=0;
			int	ct_solanviengtham=0;
			int	ct_socuahangmuahang=0;
			int	ct_sodonhangtrongngay=0;
			int	ct_giatridonhangtoithieu=0;
			int	ct_sku=0;
			float	ct_giaohangthanhcong=0;
			try
			{
				ct_ten=tennv_2[i].replace(",","");
				ct_ma=manv_2[i].replace(",","");
				ct_chucvu=chucvu_2[i].replace(",","");
				ct_muavao=Integer.parseInt(ctmuavao_2[i].replace(",",""));
				ct_banra=Integer.parseInt(ctbanra_2[i].replace(",",""));
				ct_solanviengtham=Integer.parseInt(ctSOLANVIENGTHAM_2[i].replace(",",""));
				ct_socuahangmuahang=Integer.parseInt(ctSOCUAHANGMUAHANG_2[i].replace(",",""));
				ct_sodonhangtrongngay=Integer.parseInt(ctSODONHANGTRONGNGAY_2[i].replace(",",""));
				ct_giatridonhangtoithieu=Integer.parseInt(ctGIATRIDONHANGTOITHIEU_2[i].replace(",",""));
				ct_sku=Integer.parseInt(ctSKU_2[i].replace(",",""));
				ct_giaohangthanhcong=Float.parseFloat(ctGIAOHANGTHANHCONG_2[i].replace(",",""));
				
			}catch(Exception er)
			{
				er.printStackTrace();
			}
			
				/* sql=
					"INSERT INTO CHITIEU_NHAPP_SEC(CHITIEU_SEC_FK,NHAPP_FK,CHITIEU,SODONHANG,SANLUONGTRENDH)  " +
					" values("+this.Id+","+nppId[i]+","+ ct_SalesOut+",'"+ct_SoDonHang+"','"+ct_SanLuong+"')";
				 System.out.println("[Dong]"+i+"[nppId]"+nppId[i]+"[Insert]"+sql);*/
				 
			try {	 
				String sql_insertdetail="insert into Chitieunhanvien_OTC_SR(CHITIEU_nv_fk,nhanvien_fk,chucvu,chitieubanraSL,chitieubanra,solanviengtham,socuahangmuahang,sodonhangtrongngay,giatridonhangtoithieu,giaohangthanhcong,SKU,thang,nam,quy) "+
						 "values("+this.Id+","+ct_ma+",'"+ct_chucvu+"',"+ct_muavao+","+ct_banra+",'"+ct_solanviengtham+"','"+ct_socuahangmuahang+"',"+ct_sodonhangtrongngay+","+ct_giatridonhangtoithieu+","+ct_giaohangthanhcong+","+ct_sku+","+t2+","+this.Nam+","+this.Quy+")";		
				System.out.println("sql_insertdetail"+sql_insertdetail);
				if(!this.db.update(sql_insertdetail))
				{
					this.db.getConnection().rollback();
					this.Message= "Khong the them chi tieu chi tieu :" +sql_insertdetail;
					return false;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
				
			
			if(this.NhomSPId!=null && this.NhomSPId.length>0)
			{
				 for(int k=0;k<NhomSPId.length;k++)
				 {
					 if(NhomSPId[k]!=null)
					 {
						double  ctNhom=0;
						double  ct_nhom_fk=0;
						String[] mang= request.getParameterValues("ctNppNspId2_"+ NhomSPId[k]);
						String[] mang_ct= request.getParameterValues("TC_ctNppNspId2_"+ NhomSPId[k]);
						try
						{
							ctNhom=Double.parseDouble( mang[i].replace(",",""));
							ct_nhom_fk=Double.parseDouble( mang_ct[i].replace(",",""));
						}catch(Exception er){er.printStackTrace();}
					
						if(ctNhom>0)
						{
							sql="insert into Chitieunhanvien_OTC_SR_NSP (chitieu_nv_fk,nhanvien_fk,nhomsp_fk,chitieu,thang,nam,loaichitieu_fk,quy) "
									+ "values('"+this.Id+"','"+ct_ma+"','"+NhomSPId[k]+"','"+ctNhom+"',"+t2+","+this.Nam+","+ct_nhom_fk+","+this.Quy+")";
							
							System.out.println(sql);
							 if(!db.update(sql))
							 {
								 this.db.getConnection().rollback();
								this.setMessage(sql);
								return false;
							 }	 
						}
					 }
				 }
			}
			i++;
		}
		
		i=0;
		while (tennv_3!=null && i< tennv_3.length )
		{
			String ct_ma="";
			String	ct_ten="";
			String	ct_chucvu="";
			int	ct_muavao=0;
			int	ct_banra=0;
			int	ct_solanviengtham=0;
			int	ct_socuahangmuahang=0;
			int	ct_sodonhangtrongngay=0;
			int	ct_giatridonhangtoithieu=0;
			int	ct_sku=0;
			float	ct_giaohangthanhcong=0;
			try
			{
				ct_ten=tennv_3[i].replace(",","");
				ct_ma=manv_3[i].replace(",","");
				ct_chucvu=chucvu_3[i].replace(",","");
				ct_muavao=Integer.parseInt(ctmuavao_3[i].replace(",",""));
				ct_banra=Integer.parseInt(ctbanra_3[i].replace(",",""));
				ct_solanviengtham=Integer.parseInt(ctSOLANVIENGTHAM_3[i].replace(",",""));
				ct_socuahangmuahang=Integer.parseInt(ctSOCUAHANGMUAHANG_3[i].replace(",",""));
				ct_sodonhangtrongngay=Integer.parseInt(ctSODONHANGTRONGNGAY_3[i].replace(",",""));
				ct_giatridonhangtoithieu=Integer.parseInt(ctGIATRIDONHANGTOITHIEU_3[i].replace(",",""));
				ct_sku=Integer.parseInt(ctSKU_3[i].replace(",",""));
				ct_giaohangthanhcong=Float.parseFloat(ctGIAOHANGTHANHCONG_3[i].replace(",",""));
				
			}catch(Exception er)
			{
				er.printStackTrace();
			}
			
				/* sql=
					"INSERT INTO CHITIEU_NHAPP_SEC(CHITIEU_SEC_FK,NHAPP_FK,CHITIEU,SODONHANG,SANLUONGTRENDH)  " +
					" values("+this.Id+","+nppId[i]+","+ ct_SalesOut+",'"+ct_SoDonHang+"','"+ct_SanLuong+"')";
				 System.out.println("[Dong]"+i+"[nppId]"+nppId[i]+"[Insert]"+sql);*/
			try {	 
				String sql_insertdetail="insert into Chitieunhanvien_OTC_SR(CHITIEU_nv_fk,nhanvien_fk,chucvu,chitieubanraSL,chitieubanra,solanviengtham,socuahangmuahang,sodonhangtrongngay,giatridonhangtoithieu,giaohangthanhcong,SKU,thang,nam,quy) "+
						 "values("+this.Id+","+ct_ma+",'"+ct_chucvu+"',"+ct_muavao+","+ct_banra+",'"+ct_solanviengtham+"','"+ct_socuahangmuahang+"',"+ct_sodonhangtrongngay+","+ct_giatridonhangtoithieu+","+ct_giaohangthanhcong+","+ct_sku+","+t3+","+this.Nam+","+this.Quy+")";		
				System.out.println("sql_insertdetail"+sql_insertdetail);
				if(!this.db.update(sql_insertdetail))
				{
					this.db.getConnection().rollback();
					this.Message= "Khong the them chi tieu chi tieu :" +sql_insertdetail;
					return false;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
				
			
			if(this.NhomSPId!=null && this.NhomSPId.length>0)
			{
				 for(int k=0;k<NhomSPId.length;k++)
				 {
					 if(NhomSPId[k]!=null)
					 {
						double  ctNhom=0;
						double  ct_nhom_fk=0;
						String[] mang= request.getParameterValues("ctNppNspId3_"+ NhomSPId[k]);
						String[] mang_ct= request.getParameterValues("TC_ctNppNspId3_"+ NhomSPId[k]);
						try
						{
							ctNhom=Double.parseDouble( mang[i].replace(",",""));
							ct_nhom_fk=Double.parseDouble( mang_ct[i].replace(",",""));
						}catch(Exception er){er.printStackTrace();}
					
						if(ctNhom>0)
						{
							sql="insert into Chitieunhanvien_OTC_SR_NSP (chitieu_nv_fk,nhanvien_fk,nhomsp_fk,chitieu,thang,nam,loaichitieu_fk,quy) "
									+ "values('"+this.getID()+"','"+ct_ma+"','"+NhomSPId[k]+"','"+ctNhom+"',"+t3+","+this.Nam+","+ct_nhom_fk+","+this.Quy+")";
							
							System.out.println(sql);
							 if(!db.update(sql))
							 {
								 this.db.getConnection().rollback();
								this.setMessage(sql);
								return false;
							 }	 
						}
					 }
				 }
			}
			i++;
		}
		
		
		this.db.getConnection().commit();
		this.db.getConnection().setAutoCommit(true);
	} catch (Exception e)
	{
		e.printStackTrace();
		this.setMessage("Exception__"+e.getMessage());
		this.db.update("rollback");
		return false;
	}
	System.out.println("End_________");
	return true;
}


public boolean chotthang(int thang){
	String sql="";
	String sql1="select * from Chitieunhanvien_OTC where pk_seq="+this.Id;
	int slchotthang=0;
	int first=0;
	int year=0;
	System.out.println("QUY:::::::::::::::::::::"+this.Quy);
	if(this.Quy==1)
	{
		System.out.println("vao quy 1 rui2");
		first=1;
		year=this.Nam;
	}
	if(this.Quy==2)
	{
		first=4;
		year=this.Nam;
	}
	if(this.Quy==3)
	{
		first=7;
		year=this.Nam;
	}
	if(this.Quy==4)
	{
		first=10;
		year=this.Nam;
	}

	ResultSet rs=db.get(sql1);
	try {
		while(rs.next())
		{
			 slchotthang=rs.getInt("chotthang1")+rs.getInt("chotthang3")+rs.getInt("chotthang2");
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	if(slchotthang==0)
	{
		 sql="update Chitieunhanvien_OTC set chotthang1=1 where pk_seq="+this.Id;	
		if(db.updateReturnInt(sql)<=0)
			return false;
		sql="update Chitieunhanvien_OTC_SR set trangthai=1 where chitieu_nv_FK="+this.Id+" and thang="+first +" and nam="+year;	
		System.out.println("SQL::::::: Chitieunhanvien_OTC_SR :"+sql);
		if(db.updateReturnInt(sql)<=0)
			return false;
		sql="update Chitieunhanvien_OTC_SR_NSP set trangthai=1 where chitieu_nv_FK="+this.Id+" and thang="+first +" and nam="+year;
		System.out.println("SQL::::::: Chitieunhanvien_OTC_SR_NSP :"+sql);
		
		if(db.updateReturnInt(sql)<=0)
			return false;
	}
	if(slchotthang==1)
	{
		 sql="update Chitieunhanvien_OTC set chotthang2=1,chotthang3=1 where pk_seq="+this.Id;	
		if(db.updateReturnInt(sql)<=0)
			return false;
		sql="update Chitieunhanvien_OTC_SR set trangthai=1 where chitieu_nv_FK="+this.Id+" and thang in ("+(first+1) +","+(first+2)+") and nam="+year;	
		System.out.println("SQL::::::: Chitieunhanvien_OTC_SR :"+sql);
		if(db.updateReturnInt(sql)<=0)
			return false;
		sql="update Chitieunhanvien_OTC_SR_NSP set trangthai=1 where chitieu_nv_FK="+this.Id+" and thang in ("+(first+1) +","+(first+2)+") and nam="+year;
		System.out.println("SQL::::::: Chitieunhanvien_OTC_SR_NSP :"+sql);
		
		if(db.updateReturnInt(sql)<=0)
			return false;
	}
	/*else
	{
		String sql2="select chitieubanraSL,chitieubanra,solanviengtham,socuahangmuahang,sodonhangtrongngay,giatridonhangtoithieu,giaohangthanhcong,SKU from Chitieunhanvien_OTC_SR where  nam="+year+" and thang in ("+first+","+(first+1)+")";
		rs=db.get(sql2);
		try {
			int chitieubanraSL=0;
			int chitieubanra=0;
			int solanviengtham=0;
			int socuahangmuahang=0;
			int giatridonhangtoithieu=0;
			int sodonhangtrongngay=0;
			int giaohangthanhcong=0;
			int SKU=0;
			while(rs.next())
			{
				 chitieubanraSL+=rs.getInt("chitieubanraSL");
				 chitieubanra+=rs.getInt("chitieubanra");
				 solanviengtham+=rs.getInt("solanviengtham");
				 socuahangmuahang+=rs.getInt("socuahangmuahang");
				 giatridonhangtoithieu+=rs.getInt("giatridonhangtoithieu");
				 sodonhangtrongngay+=rs.getInt("sodonhangtrongngay");
				 giaohangthanhcong+=rs.getInt("giaohangthanhcong");
				 SKU+=rs.getInt("SKU");
			}
			int chitieubanraSLT=0;
			int chitieubanraT=0;
			int solanviengthamT=0;
			int socuahangmuahangT=0;
			int giatridonhangtoithieuT=0;
			int sodonhangtrongngayT=0;
			int giaohangthanhcongT=0;
			int SKUT=0;
			String sql3="select top(1) chitieubanraSL,chitieubanra,solanviengtham,socuahangmuahang,sodonhangtrongngay,giatridonhangtoithieu,giaohangthanhcong,SKU from Chitieunhanvien_OTC_QUY_SR where nam="+year +" and thang="+first;
			rs=db.get(sql3);
			while (rs.next())
			{
				 chitieubanraSLT=rs.getInt("chitieubanraSL");
				 chitieubanraT=rs.getInt("chitieubanra");
				 solanviengthamT=rs.getInt("solanviengtham");
				 socuahangmuahangT=rs.getInt("socuahangmuahang");
				 giatridonhangtoithieuT=rs.getInt("giatridonhangtoithieu");
				 sodonhangtrongngayT=rs.getInt("sodonhangtrongngay");
				 giaohangthanhcongT=rs.getInt("giaohangthanhcong");
				 SKUT=rs.getInt("SKU");
			}
			String sql4="update Chitieunhanvien_OTC_SR set chitieubanraSL="+chitieubanraSLT+"-"+chitieubanraSL+",chitieubanra="+chitieubanraT+"-"+chitieubanra+",solanviengtham="+solanviengthamT+"-"+solanviengtham+""
					+ ",socuahangmuahang="+socuahangmuahangT+"-"+socuahangmuahang+",sodonhangtrongngay="+sodonhangtrongngayT+"-"+sodonhangtrongngay+",giatridonhangtoithieu="+giatridonhangtoithieuT+"-"+giatridonhangtoithieu+""
					+ ", giaohangthanhcong="+giaohangthanhcongT+"-"+giaohangthanhcong+",SKU="+SKUT+"-"+SKU+" where chitieu_nv_fk="+this.Id +" and thang="+(first+2)+" and nam="+year;
			System.out.println("SQL:"+sql4);
			if(db.updateReturnInt(sql4)<=0)
				return false;
			
			String sql5=" update data1 set data1.CHITIEU=Data.SL from ( "+
					    " select (B.CHITIEU-A.sl) as SL,A.NHOMSP_FK from ( "+
						"	 select NHOMSP_FK,chitieu from Chitieunhanvien_OTC_QUY_SR_NSP where CHITIEU_NV_FK="+this.Id+"  group by  NHOMSP_FK,chitieu) as B "+
						"	 inner join ( "+
						"	 select SUM(chitieu) as sl,NHOMSP_FK from Chitieunhanvien_OTC_SR_NSP "+
						"	  where THANG in ("+first+","+(first+1)+") and CHITIEU_NV_FK="+this.Id+" "+
						"	  group by NHOMSP_FK) as A "+
						"	  on B.NHOMSP_FK=A.NHOMSP_FK) as Data inner join Chitieunhanvien_OTC_SR_NSP data1 "+
						"	  on data1.NHOMSP_FK=Data.NHOMSP_FK and data1.THANG="+(first+2)+" and  data1.CHITIEU_NV_FK="+this.Id+" "; 
			if(db.updateReturnInt(sql5)<=0)
				return false;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 sql="update Chitieunhanvien_OTC set chotthang2=1 ,chotthang3=1 where pk_seq="+this.Id;	
			if(db.updateReturnInt(sql)<=0)
				return false;
			sql="update Chitieunhanvien_OTC_SR set trangthai=1 where chitieu_nv_FK="+this.Id+" and thang="+(first+1) +" and nam="+year;	
			if(db.updateReturnInt(sql)<=0)
				return false;
			sql="update Chitieunhanvien_OTC_SR_NSP set trangthai=1 where chitieu_nv_FK="+this.Id+" and thang="+(first+2) +" and nam="+year;	
			if(db.updateReturnInt(sql)<=0)
				return false;
	}*/
	
	return true;
} 

int chotthang1;
public int getChotthang1() {
	return chotthang1;
}
public void setChotthang1(int chotthang1) {
	this.chotthang1 = chotthang1;
}
int chotthang2;
public int getChotthang2() {
	return chotthang2;
}
public void setChotthang2(int chotthang2) {
	this.chotthang2 = chotthang2;
}
int chotthang3;
public int getChotthang3() {
	return chotthang3;
}
public void setChotthang3(int chotthang3) {
	this.chotthang3 = chotthang3;
}

ResultSet rs_npp;
public ResultSet getRs_npp() {
	return rs_npp;
}
public void setRs_npp(ResultSet rs_npp) {
	this.rs_npp = rs_npp;
}
String nhapp;
public String getNhapp() {
	return nhapp;
}
public void setNhapp(String nhapp) {
	this.nhapp = nhapp;
}
public String getnpp()
{
	String npp="";
	String sql="select b.PK_SEQ from NHANVIEN a inner join NHAPHANPHOI b on a.CONVSITECODE=b.SITECODE where a.pk_seq='"+this.UserID+"'";
	ResultSet rs=db.get(sql);
	try {
		if(rs.next())
			npp=rs.getString("pk_seq");
		rs.close();
		return npp;
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return npp;
}

}
