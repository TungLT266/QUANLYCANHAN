package geso.traphaco.erp.beans.nhanhangtrongnuoc.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import geso.traphaco.center.util.*;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.beans.nhanhangtrongnuoc.*;

public class ErpNhanhang_DonTH implements IErpNhanhang_DonTH
{
	String congtyId;
	String userId;
	String id;
	String ngaynhanhang;
	String ngaychot;
	String sohoadon;
	
	//String dvthId;
	ResultSet dvthRs;
	boolean IsKhoNhanQL_khuvuc=false;
	String poId;
	ResultSet poRs;
	
	String ndnId;
	ResultSet ndnRs;
	
	String ldnId;
	ResultSet ldnRs;
	
	String nccId;
	ResultSet nccRs;
	
	String mahangmuaId;
	int ngayhethan;
	ResultSet mahangmuaRs;
	ResultSet KhoCXLRs;
	
	String diengiai;
	String trangthai;
	String soluongPO;
	String soluongDaNhan;
	String loaihanghoa;
	String KhoCxlId;
	
	List<ISanpham> spList;

	String msg;
	
	dbutils db;
	private Utility util;
	
	public ErpNhanhang_DonTH()
	{
		this.userId = "";
		this.id = "";
		this.ngaynhanhang = "";
		this.ngaychot = "";
		this.sohoadon = "";
		this.diengiai = "";
	 
		this.poId = "";
		this.mahangmuaId = "";
		this.diengiai = "";
		this.trangthai = "";
		this.soluongPO = "";
		this.soluongDaNhan = "0";
		this.loaihanghoa = "0";
		
		this.msg = "";
		this.ngayhethan = 0;
		this.ndnId = "";
		this.ldnId = "";
		this.nccId = "";
		this.KhoCxlId="";
		this.spList = new ArrayList<ISanpham>();
		this.db = new dbutils();
		this.util=new Utility();
		
	}
	
	public ErpNhanhang_DonTH(String id)
	{
		this.userId = "";
		this.id = id;
		this.ngaynhanhang = "";
		this.ngaychot = "";
		this.sohoadon = "";
		 
		this.poId = "";
		this.mahangmuaId = "";
		this.diengiai = "";
		this.trangthai = "";
		this.soluongPO = "";
		this.soluongDaNhan = "0";
		this.ngayhethan = 0;
		this.loaihanghoa = "0";
		this.KhoCxlId="";
		this.msg = "";
		this.ndnId = "";
		this.ldnId = "";
		this.nccId = "";
		this.spList = new ArrayList<ISanpham>();
		this.db = new dbutils();
		this.util=new Utility();
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
	
	public String getNgaynhanhang()
	{
		return this.ngaynhanhang;
	}
	
	public void setNgaynhanhang(String ngaynhanhang)
	{
		this.ngaynhanhang = ngaynhanhang;
	}
	
	public String getDvthId()
	{
		return "";
	}
	
	public void setDvthId(String dvthid)
	{
		 
	}
	
	public ResultSet getDvthList()
	{
		return this.dvthRs;
	}
	
	public void setDvthList(ResultSet dvthlist)
	{
		this.dvthRs = dvthlist;
	}
	
	public String getTrangthai()
	{
		return this.trangthai;
	}
	
	public void setTrangthai(String trangthai)
	{
		this.trangthai = trangthai;
	}
	
	public String getSohoadon()
	{
		return this.sohoadon;
	}
	
	public void setSohoadon(String sohoadon)
	{
		this.sohoadon = sohoadon;
	}
	
	public String getDonmuahangId()
	{
		return this.poId;
	}
	
	public void setDonmuahangId(String dmhid)
	{
		this.poId = dmhid;
	}
	
	public ResultSet getDmhList()
	{
		return this.poRs;
	}
	
	public void setDmhList(ResultSet dmhlist)
	{
		this.poRs = dmhlist;
	}
	
	public String getMahangmuaId()
	{
		return this.mahangmuaId;
	}
	
	public void setMahangmuaId(String mhmId)
	{
		this.mahangmuaId = mhmId;
	}
	
	public ResultSet getMahangmuaList()
	{
		return this.mahangmuaRs;
	}
	
	public void setMahangmuaList(ResultSet mhmlist)
	{
		this.mahangmuaRs = mhmlist;
	}
	
	public String getDiengiai()
	{
		return this.diengiai;
	}
	
	public void setDiengiai(String diengiai)
	{
		this.diengiai = diengiai;
	}
	
	public String getTongSoluongPO()
	{
		return this.soluongPO;
	}
	
	public void setTongSoluongPO(String tongslgPO)
	{
		this.soluongPO = tongslgPO;
	}
	
	public String getTongSoluongDN()
	{
		return this.soluongDaNhan;
	}
	
	public void setTongSoluongDN(String tongslgDN)
	{
		this.soluongDaNhan = tongslgDN;
	}
	
	public List<ISanpham> getSpList()
	{
		return this.spList;
	}
	
	public void setSpList(List<ISanpham> spList)
	{
		this.spList = spList;
	}
	
	public String getMsg()
	{
		return this.msg;
	}
	
	public void setMsg(String msg)
	{
		this.msg = msg;
	}
	
	public void createRs()
	{
		
		
		String sql="select pk_seq, ten from ERP_DONVITHUCHIEN where trangthai = '1' and congty_fk = '" + this.congtyId + "' and pk_seq in " + this.util.quyen_donvithuchien(this.userId);;
		this.dvthRs = db.get(sql);
		
		sql="SELECT   PK_SEQ, MA +' - '+ TEN  AS TEN  FROM ERP_KHOTT kho  where kho.PK_SEQ in (100018,100019,100020)";
		this.KhoCXLRs=db.get(sql);
		
		
		this.nccRs = db.get("select PK_SEQ, ma + ', ' + TEN as ten  from ERP_KHACHHANG where pk_seq in " + this.util.quyen_npp(this.userId) + " ");
		 
	 
				
		    String query = "";
			   query =  " select PK_SEQ as poId, PREFIX + cast(PK_SEQ as nvarchar(10)) + N', Ngày trả ' + NGAYTRA as poTen " +
						" from DONTRAHANG where TRANGTHAI in(4,5) and KhachHang_fk in " + this.util.quyen_npp(this.userId) + "  ";
		
				if(this.nccId.trim().length() > 0)
				{
					query += " AND KhachHang_FK = '" + this.nccId + "' ";
				}
				
				if (this.id.length() > 0)
				{
					query += " union " + 
							 " select PK_SEQ as poId, PREFIX + cast(PK_SEQ as nvarchar(10)) + N', Ngày trả ' + NGAYTRA as poTen  " +
							 " from DONTRAHANG where pk_seq in (select TRAHANG_FK from ERP_NHANHANG where PK_SEQ = '" + this.id + "')";
				}
 
			this.poRs = db.get(query);
		 
		
		if (this.poId.length() > 0  && this.spList.size() <= 0)
		{
		 
			
			NumberFormat formater = new DecimalFormat("#,###,###.#######");
			
 
			query=  " SELECT MUAHANG.SANPHAM_FK, MUAHANG.SOLUONG, MUAHANG.DONGIA, 	  " +  
					"   MUAHANG.NGAYNHAN, MUAHANG.DUNGSAI, MUAHANG.SPMA, MUAHANG.SPTEN, MUAHANG.HANSUDUNG, MUAHANG.DONVI,   " +  
					"   '100000' as TienTe_fk, '1' as  TyGiaQuyDoi, MUAHANG.DONGIA as DonGiaViet, '' as khottId, '' as khottTen     " +  
					"   FROM    " +  
					"   (     " +  
					"   SELECT  A.SANPHAM_FK,  " +  
					"   CASE WHEN (SELECT PK_SEQ FROM DONVIDOLUONG WHERE DONVI LIKE A.DONVI)!= B.DVDL_FK   " +  
					"   THEN SUM( A.SOLUONG * ISNULL(CONVERT(FLOAT,D.SOLUONG1), 1) / ISNULL(CONVERT(FLOAT,D.SOLUONG2), 1) )  " +  
					"   ELSE SUM(A.SOLUONG) END AS SOLUONG ,    " +  
					"   CASE WHEN (SELECT PK_SEQ FROM DONVIDOLUONG WHERE DONVI LIKE A.DONVI)!= B.DVDL_FK   " +  
					"   THEN  A.DONGIA *  ISNULL(CONVERT(FLOAT,D.SOLUONG2), 1)   " +  
					"   ELSE  A.DONGIA END AS DONGIA ,  " +  
					"   '" + getDateTime() + "' AS NGAYNHAN, 0 AS DUNGSAI,   " +  
					"   B.MA AS SPMA, "+     
					"   B.TEN   AS SPTEN , "+     
					"   B.HANSUDUNG, C.DONVI       " +  
					"   FROM DONTRAHANG DTH INNER JOIN DONTRAHANG_SP A ON DTH.PK_SEQ = A.DONTRAHANG_FK     " +  
					"   LEFT JOIN ERP_SANPHAM B ON A.SANPHAM_FK = B.PK_SEQ      " +  
					"   LEFT JOIN 	DONVIDOLUONG C ON C.PK_SEQ=B.DVDL_FK       " +  
					"   LEFT JOIN  " +  
					"   (  " +  
					"  	 SELECT D.*,DVDL1.DONVI FROM QUYCACH D  " +  
					"  	 INNER JOIN DONVIDOLUONG DVDL1 ON DVDL1.PK_SEQ=D.DVDL2_FK " +  
					"   )D  ON  B.PK_SEQ= D.SANPHAM_FK  AND A.DONVI=D.DONVI " +  
					"    " +  
					"   WHERE DTH.PK_SEQ =   "+this.poId+"     " +  
					"   GROUP BY A.SANPHAM_FK, A.DONGIA,B.MA,B.QUYCACH,B.TEN2, B.TEN,B.MA,A.DONVI,B.DVDL_FK ,D.SOLUONG2,D.SOLUONG1, B.HANSUDUNG, C.DONVI  " +  
					"  )    " +  
					"  MUAHANG   LEFT JOIN      " +  
					"  (     " +  
					"  SELECT B.SANPHAM_FK, B.NGAYNHANDUKIEN, ISNULL(SUM(B.SOLUONGNHAN), '0' ) AS SOLUONG 	  " +  
					"  FROM ERP_NHANHANG A  INNER JOIN ERP_NHANHANG_SANPHAM B ON A.PK_SEQ = B.NHANHANG_FK     " +  
					"  WHERE A.TRAHANG_FK = "+this.poId+" AND A.TRANGTHAI != '3'     " +  
					"  GROUP BY B.SANPHAM_FK, NGAYNHANDUKIEN    " +  
					"  )    " +  
					"  NHANHANG  ON MUAHANG.SANPHAM_FK = NHANHANG.SANPHAM_FK  AND MUAHANG.NGAYNHAN = NHANHANG.NGAYNHANDUKIEN     " +  
					"  WHERE MUAHANG.SOLUONG - ISNULL(NHANHANG.SOLUONG, '0') > 0  ";
			
			 
			ResultSet spRs = db.get(query);
			
			List<ISanpham> spList = new ArrayList<ISanpham>();
			
			if (spRs != null)
			{
				try
				{
					ISanpham sp = null;
					while (spRs.next())
					{
						String spId = spRs.getString("SANPHAM_FK");
						String spMa = spRs.getString("spMa");
						String spTen = spRs.getString("spTen");						
						String soluongDat =  formater.format( spRs.getDouble("soluong")); // lam tron 3 chu so		
						String hansudung = spRs.getString("HANSUDUNG");
						String ngaynhandukien = spRs.getString("NGAYNHAN");
						String dungsai = spRs.getString("DUNGSAI");
						String dvdl = spRs.getString("DonVi");
						String dongia = Double.toString(spRs.getDouble("DONGIA"));
						
						String khottId = spRs.getString("khottId") == null ? "" : spRs.getString("khottId");
						
					 
						
						String khottTen = spRs.getString("khottTen") == null ? "" : spRs.getString("khottTen");
						
						sp = new Sanpham(spId, spMa, spTen, "", hansudung, ngaynhandukien, soluongDat, dvdl);
						sp.setDungsai(dungsai);
						sp.setDongia(dongia);
						sp.setTiente(spRs.getString("TienTe_fk"));
						sp.setTigiaquydoi(spRs.getString("TyGiaQuyDoi"));
						sp.setDongiaViet(spRs.getString("DonGiaViet"));
						sp.setKhonhanId(khottId);
						sp.setKhonhanTen(khottTen);
						
						//Tinh so luong MAX + dung sai co the nhan
						double soluongPONhan = 0;
						double soluongMax = Double.parseDouble(soluongDat.replaceAll(",", ""));
						
						if(this.poId.trim().length() > 0 )
						{
							query = " select sum(b.SOLUONGNHAN)  as soluongDaNhan  " +
									" from ERP_NHANHANG a inner join ERP_NHANHANG_SANPHAM b on a.PK_SEQ = b.NHANHANG_FK  " +
									" where a.TRAHANG_FK = '" + this.poId + "'  and SANPHAM_FK = '" + spId + "' and a.TRANGTHAI != '3'";
							
							if(this.id.trim().length() > 0)
							{
								query += " and a.pk_seq != '" + this.id + "' ";
							}
						
						 
							ResultSet rsNhanTD = db.get(query);
							if(rsNhanTD != null)
							{
								if(rsNhanTD.next())
								{
									//double soluongPODat = rsNhanTD.getDouble("soluongDat");
									double soluongPODat = Double.parseDouble(soluongDat.replaceAll(",", "") );
									
									soluongPONhan = rsNhanTD.getString("soluongDaNhan") == null ? 0 : rsNhanTD.getDouble("soluongDaNhan") ;
									soluongMax = ( soluongPODat + ( soluongPODat * Double.parseDouble(dungsai.replaceAll(",", "")) / 100 ) ) - soluongPONhan;
								}
								rsNhanTD.close();
							}
						}
						
						
							query = " select pk_seq as khoId,TEN as khoTen from ERP_KHOTT where LOAI=4 and PK_SEQ not in (select PK_SEQ from ERP_KHOTT where ma like '%-CXL-%' )  " +
									" and  TRANGTHAI = '1' and PK_SEQ in ( select distinct KHOTT_FK from ERP_KHOTT_SANPHAM where SANPHAM_FK = '" + spId + "' )";
							
						 
							ResultSet rsKho = db.get(query);
							if(rsKho != null)
							{
								sp.setKhoNhanRs(rsKho);
							}
							else
							{
								this.msg = "Sản phẩm ( " + spTen + " ) chưa thiết lập kho trong dữ liệu nền KHO - SẢN PHẨM ";
							}
							
							
							query = " select sum(b.SOLUONGNHAN)  as soluongDaNhan  " +
									" from ERP_NHANHANG a inner join ERP_NHANHANG_SANPHAM b on a.PK_SEQ = b.NHANHANG_FK  " +
									" where a.TRAHANG_FK = '" + this.poId + "'  and SANPHAM_FK = '" + spId + "' and a.TRANGTHAI != '3'";
							
							if(this.id.trim().length() > 0)
							{
								query += " and a.pk_seq != '" + this.id + "' ";
							}
						
							ResultSet rsNhanTD = db.get(query);
							if(rsNhanTD != null)
							{
								if(rsNhanTD.next())
								{
									double soluongPODat = Double.parseDouble(soluongDat.replaceAll(",", ""));
									
									soluongPONhan = rsNhanTD.getDouble("soluongDaNhan");
									soluongMax = ( soluongPODat + ( soluongPODat * Double.parseDouble(dungsai) / 100 ) ) - soluongPONhan;
								}
								rsNhanTD.close();
							}
							
						
						String thanhtien = formater.format(spRs.getDouble("DONGIA")*soluongPONhan);
						sp.setSoluongDaNhan(formater.format(soluongPONhan));
						sp.setSoluongMaxNhan(Double.toString(soluongMax));
						
						sp.setthanhtien(thanhtien);
						
						spList.add(sp);
					}
					spRs.close();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				
				this.spList = spList;
			}
		}
	}
	
	public void init()
	{
		String query =  " select isnull( a.KHOCHOXULY_FK,0) as KHOCHOXULY_FK , a.PK_SEQ as nhId, a.trangthai, a.loaihanghoa_fk, a.noidungnhan_fk, a.SOHOADON, a.NGAYNHAN, a.NGAYCHOT, a.diengiai, b.pk_seq as dvthId, b.TEN as dvthTen," +
						" case when a.noidungnhan_fk = 100000 then c.PK_SEQ else d.PK_SEQ end as PoId, a.TRANGTHAI, a.NoiDungNhap_fk , a.NCC_KH_FK  " +
						" from erp_nhanhang a left join ERP_DONVITHUCHIEN b on a.DONVITHUCHIEN_FK = b.PK_SEQ " +
						" left join ERP_MUAHANG c on a.MUAHANG_FK = c.PK_SEQ " +
						" left join DonTraHang d on a.TRAHANG_FK = d.PK_SEQ " +
						" where a.pk_seq = '" + this.id + "'";
		
		
		ResultSet rs = db.get(query);
		if (rs != null)
		{
			try
			{
				while (rs.next())
				{
					this.ngaynhanhang = rs.getString("ngaynhan");
					this.ngaychot = rs.getString("ngaychot");
					this.sohoadon = rs.getString("sohoadon");
					 
					this.poId = rs.getString("PoId");
					this.diengiai = rs.getString("diengiai");
					this.ndnId = rs.getString("noidungnhan_fk");
					this.loaihanghoa = rs.getString("loaihanghoa_fk");
					this.ldnId = rs.getString("NoiDungNhap_fk");
					this.trangthai = rs.getString("trangthai");
					this.nccId = rs.getString("NCC_KH_FK") == null ? "" : rs.getString("NCC_KH_FK");
					this.KhoCxlId=rs.getString("KHOCHOXULY_FK");
					//System.out.println("___NCC ID: " + this.nccId);
				}
				rs.close();
			}
			catch (Exception e)
			{
				System.out.println("1.Exception: " + e.getMessage());
			}
		}
		
		this.initSanPham();
		
		this.createRs();
	}
	
	private void initSanPham()
	{
		NumberFormat formatter = new DecimalFormat("#,###,###.###"); 
		
		String query = 			" select  case nh.loaihanghoa_fk when 0 then A.SANPHAM_FK when 1 then tscd.PK_SEQ else ncp.pk_seq end as spId, " +
								" case nh.loaihanghoa_fk when 0 then isnull( case when ( len(ltrim(rtrim(sp.MA))) <= 0 or ( sp.MA is null ) ) then sp.ma else sp.MA end, '' )  when 1 then tscd.Ma else ncp.Ten end AS spMa,  " +
								" CASE nh.loaihanghoa_fk WHEN 0 THEN isnull(sp.Ten2, sp.Ten) + ' ' + case when substring(LTRIM(isnull(SP.QUYCACH,'')), 1, 1 ) = 'x' then STUFF(LTRIM(isnull(SP.QUYCACH,'')),1,1,'') else isnull(SP.QUYCACH,'') end   ELSE a.DienGiai END AS spTen, " +
								" a.NGAYNHANDUKIEN, a.DUNGSAI, a.DONGIA, a.SOLUONGDAT, a.SOLUONGNHAN, isnull(sp.HANSUDUNG, '0') as HanSuDung, " +
								" isnull(a.DonVi, 'NA') as donvi, a.TienTe_Fk, a.TyGiaQuyDoi, a.DonGiaViet, " +
								" khott.pk_seq as khottId, khott.ma + ', ' + khott.ten as khottTen     " +
								" from ERP_NHANHANG_SANPHAM a inner join ERP_NhanHang nh on a.nhanhang_fk = nh.pk_seq   " +
								" LEFT join ERP_SANPHAM sp on a.SANPHAM_FK = sp.PK_SEQ   " +
								" LEFT JOIN erp_taisancodinh tscd on a.taisan_fk = tscd.pk_seq        " +
								" LEFT JOIN erp_nhomchiphi ncp on a.chiphi_fk = ncp.pk_seq  " +
								" LEFT JOIN ERP_KHOTT khott on a.khonhan = khott.pk_seq  " +
								" where a.NHANHANG_FK = '" + this.id + "'";
		
		//System.out.println("[ErpNhanhang_Giay.initSanPham] query = " + query);
		ResultSet rsSp = db.get(query);
		
		if (rsSp != null)
		{
			NumberFormat formater = new DecimalFormat("#,###,###.###");
			
			try
			{
				ISanpham sanpham;
				List<ISanpham> spList = new ArrayList<ISanpham>();
				while (rsSp.next())
				{
					String spId = rsSp.getString("spId");
					String spMa = rsSp.getString("spMa");
					String spTen = rsSp.getString("spTen");
					String ngaynhandk = rsSp.getString("NGAYNHANDUKIEN");
					
					String soluongdat = formatter.format(rsSp.getDouble("SOLUONGDAT"));
					String soluongnhan = formatter.format(rsSp.getDouble("SOLUONGNHAN"));
					
					String hansudung = rsSp.getString("HANSUDUNG");
					String dungsai = rsSp.getString("DUNGSAI");
					String dvdl = rsSp.getString("DonVi");
					String dongia = rsSp.getString("DONGIA");
					
					String thanhtien = formatter.format(rsSp.getDouble("SOLUONGNHAN")*rsSp.getDouble("DONGIA"));
					
					sanpham = new Sanpham(spId, spMa, spTen, soluongnhan, hansudung, ngaynhandk, soluongdat, dvdl);
					if (soluongdat != "" && soluongnhan != "")
						sanpham.setCOnlai(Float.toString(Float.parseFloat(soluongdat.replaceAll(",", "") ) - Float.parseFloat(soluongnhan.replaceAll(",", ""))));
					
					if(this.loaihanghoa.equals("0"))
					{
						String comand = " select sanpham_fk, solo, soluong, ngaysanxuat, ngayhethan , khu_fk " +
										" from ERP_NHANHANG_SP_CHITIET " +
										" where NGAYNHANDUKIEN='" + ngaynhandk + "' AND nhanhang_fk = '" + this.id + "' and sanpham_fk = '" + spId + "' order by lannhan asc ";
						
						System.out.println("Khoi tao san pham con: " + comand);
						ResultSet spConRs = db.get(comand);
						
						List<ISpDetail> spConList = new ArrayList<ISpDetail>();
						ISpDetail spCon = null;
						if (spConRs != null)
						{
							while (spConRs.next())
							{
								String spConMa = spConRs.getString("sanpham_fk");
								String solo = spConRs.getString("solo");
								String soluong = formatter.format(spConRs.getDouble("soluong"));
								String ngaysanxuat = spConRs.getString("ngaysanxuat");
								String ngayhethan = spConRs.getString("ngayhethan");
								String khu_fk= spConRs.getString("khu_fk");
								spCon = new SpDetail(spConMa, solo, soluong, ngaysanxuat, ngayhethan);
								spCon.setkhuid(khu_fk);
								System.out.println("khu_fk : "+khu_fk);
								spConList.add(spCon);
							}
							spConRs.close();
						}
						
						sanpham.setSpDetail(spConList);
					}
					
					sanpham.setDungsai(dungsai);
					sanpham.setDongia(dongia);
					sanpham.setTiente(rsSp.getString("TienTe_Fk"));
					sanpham.setTigiaquydoi(rsSp.getString("TyGiaQuyDoi"));
					sanpham.setDongiaViet(rsSp.getString("DonGiaViet"));
					
					sanpham.setKhonhanId( rsSp.getString("khottId") == null ? "" : rsSp.getString("khottId") );
					sanpham.setKhonhanTen( rsSp.getString("khottTen") == null ? "" : rsSp.getString("khottTen") );
					
					double soluongDat = rsSp.getDouble("SOLUONGDAT");
					
					//Tinh so luong MAX + dung sai co the nhan
					double soluongPONhan = 0;
					double soluongMax = soluongDat;
					
					if(this.poId.trim().length() > 0  )
					{
						query = "select sum(b.SOLUONGNHAN)  as soluongDaNhan  " +
								"from ERP_NHANHANG a inner join ERP_NHANHANG_SANPHAM b on a.PK_SEQ = b.NHANHANG_FK  " +
								"where a.MUAHANG_FK = '" + this.poId + "'  and SANPHAM_FK = '" + spId + "' and a.TRANGTHAI != '3'";
						
						if(this.id.trim().length() > 0  &&  (this.trangthai.trim().equals("")||this.trangthai.trim().equals("0") ))
						{
							query += " and a.pk_seq != '" + this.id + "' ";
						}
					
						ResultSet rsNhanTD = db.get(query);
						if(rsNhanTD != null)
						{
							if(rsNhanTD.next())
							{
								//double soluongPODat = rsNhanTD.getDouble("soluongDat");
								double soluongPODat = soluongDat;
								
								soluongPONhan = rsNhanTD.getDouble("soluongDaNhan");
								soluongMax = ( soluongPODat + ( soluongPODat * Double.parseDouble(dungsai.replaceAll(",", "")) / 100 ) ) - soluongPONhan;
							}
							rsNhanTD.close();
						}
					}
					
						// lấy kho là kho hàng trả về
					
					query = " select pk_seq as khoId,TEN as khoTen from ERP_KHOTT where LOAI=4 and PK_SEQ not in (select PK_SEQ from ERP_KHOTT where ma like '%-CXL-%' )  " +
							" and  TRANGTHAI = '1' and PK_SEQ in ( select distinct KHOTT_FK from ERP_KHOTT_SANPHAM where SANPHAM_FK = '" + spId + "' )";
						//System.out.println("___LAY KHO: " + query);
						ResultSet rsKho = db.get(query);
						if(rsKho != null)
						{
							sanpham.setKhoNhanRs(rsKho);
						}
						else
						{
							this.msg = "Sản phẩm ( " + spTen + " ) chưa thiết lập kho trong dữ liệu nền KHO - SẢN PHẨM ";
						}
						
						
						query = " select sum(b.SOLUONGNHAN)  as soluongDaNhan  " +
								" from ERP_NHANHANG a inner join ERP_NHANHANG_SANPHAM b on a.PK_SEQ = b.NHANHANG_FK  " +
								" where a.TRAHANG_FK = '" + this.poId + "'   and SANPHAM_FK = '" + spId + "' and a.TRANGTHAI != '3'";
						
						if(this.id.trim().length() > 0 &&  (this.trangthai.trim().equals("")||this.trangthai.trim().equals("0") ))
						{
							query += " and a.pk_seq != '" + this.id + "' ";
						}
						 
						ResultSet rsNhanTD = db.get(query);
						if(rsNhanTD != null)
						{
							if(rsNhanTD.next())
							{
								double soluongPODat = soluongDat;
								
								soluongPONhan = rsNhanTD.getDouble("soluongDaNhan");
								
								
								soluongMax = ( soluongPODat + ( soluongPODat * Double.parseDouble(dungsai) / 100 ) ) - soluongPONhan;
							}
							rsNhanTD.close();
						}
					 
					
					sanpham.setSoluongDaNhan(formater.format(soluongPONhan));
					sanpham.setSoluongMaxNhan(Double.toString(soluongMax));
					
					sanpham.setthanhtien(thanhtien);
					
					spList.add(sanpham);
				}
				rsSp.close();
				this.spList = spList;
			}
			catch (Exception e)
			{
				 
				e.printStackTrace();
				
			}
		}
	}
	
	public void initPdf(String spId)
	{
		String query =  " select a.PK_SEQ as nhId, a.trangthai, a.loaihanghoa_fk, a.noidungnhan_fk, a.SOHOADON, a.NGAYNHAN, a.NGAYTAO, a.diengiai, b.pk_seq as dvthId, b.TEN as dvthTen, a.TRANGTHAI, a.NoiDungNhap_fk , a.NCC_KH_FK, " +
						" 	case when a.noidungnhan_fk = 100000 then c.Prefix + cast(c.PK_SEQ as varchar(10)) else d.Prefix + cast(d.PK_SEQ as varchar(10)) end as sochungtu,  " +
						"   case when a.noidungnhan_fk = 100000 then ncc.TEN else kh.TEN end as donviban " +
						" from erp_nhanhang a left join ERP_DONVITHUCHIEN b on a.DONVITHUCHIEN_FK = b.PK_SEQ " +
						" left join ERP_MUAHANG c on a.MUAHANG_FK = c.PK_SEQ " +
						" left join DonTraHang d on a.TRAHANG_FK = d.PK_SEQ " +
						" left join Erp_Nhacungcap ncc on ncc.PK_SEQ=c.NHACUNGCAP_FK " +
						" left join ERP_KHACHHANG kh on kh.PK_SEQ=d.KHACHHANG_FK " +
						" where a.pk_seq = '" + this.id + "' ";
		
		System.out.println("[ErpNhanhang_Giay.initPdf] Khoi tao nhan hang: " + query );
		ResultSet rs = db.get(query);
		if (rs != null)
		{
			try
			{
				while (rs.next())
				{					
					this.ngaychot = rs.getString("ngaytao"); //NGAY TAO
					this.ngaynhanhang = rs.getString("ngaynhan");
				 
					this.poId = rs.getString("sochungtu");
					this.diengiai = rs.getString("diengiai");
					this.ndnId =  rs.getString("noidungnhan_fk");
					this.sohoadon = rs.getString("sohoadon");
					
					 
				}
				rs.close();
			}
			catch (SQLException e)
			{
			}
		}
		
		this.initSanPhamPdf(spId);
	}
	
	
	private void initSanPhamPdf(String _spId)
	{	
		String query = "select  case nh.loaihanghoa_fk when 0 then A.SANPHAM_FK when 1 then tscd.PK_SEQ else ncp.pk_seq end as spId, " +
								"case nh.loaihanghoa_fk when 0 then isnull( case when ( len(ltrim(rtrim(sp.MA))) <= 0 or ( sp.MA is null ) ) then sp.ma else sp.MA end, '' )  when 1 then tscd.Ma else ncp.Ten end AS spMa,  " +
								"case nh.loaihanghoa_fk when 0 then isnull(sp.Ten2, sp.Ten) + ' ' + sp.QuyCach  else a.DienGiai end AS spTen, " +
								" a.NGAYNHANDUKIEN, a.DUNGSAI, a.DONGIA, a.SOLUONGDAT, a.SOLUONGNHAN, isnull(sp.HANSUDUNG, '0') as HanSuDung, " +
								"isnull(a.DonVi, 'NA') as donvi, a.TienTe_Fk, a.TyGiaQuyDoi, a.DonGiaViet, " +
								"khott.pk_seq as khottId, khott.ma + ', ' + khott.ten as khoTen, cast(ROUND(sp.TRONGLUONG, 5) as numeric(10, 5)) as TRONGLUONG, cast(ROUND(isnull(sp.THETICH, 0), 5) as numeric(10, 5)) as THETICH " +
						"from ERP_NHANHANG_SANPHAM a inner join ERP_NhanHang nh on a.nhanhang_fk = nh.pk_seq   " +
							"LEFT join ERP_SANPHAM sp on a.SANPHAM_FK = sp.PK_SEQ   " +
							"LEFT JOIN erp_taisancodinh tscd on a.taisan_fk = tscd.pk_seq        " +
							"LEFT JOIN erp_nhomchiphi ncp on a.chiphi_fk = ncp.pk_seq  " +
							"LEFT JOIN ERP_KHOTT khott on a.khonhan = khott.pk_seq  " +
						"where a.NHANHANG_FK = '" + this.id + "' ";
		
		//truong hop muon in 1 san pham
		if(_spId.length() > 0) {
			query += " and a.SANPHAM_FK in (" + _spId + ")";
		}
		
		/*if(this.ndnId.equals("100000"))
		{
			query = "select nhanhang.*, muahang.ten as khoTen  " +
				"from  " +
				"( " +
					"select a.SANPHAM_FK, b.MA as spMa, b.TEN + ' ' + b.quycach as spTen, a.NGAYNHANDUKIEN, a.SOLUONGDAT, a.SOLUONGNHAN, c.donvi, cast(ROUND(b.TRONGLUONG, 5) as numeric(10, 5)) as TRONGLUONG, cast(ROUND(isnull(b.THETICH, 0), 5) as numeric(10, 5)) as THETICH " +
					"from ERP_NHANHANG_SANPHAM a inner join ERP_SANPHAM b on a.SANPHAM_FK = b.PK_SEQ inner join DONVIDOLUONG c on b.dvdl_fk = c.pk_seq  " +
					"where a.NHANHANG_FK = '" + this.id + "' " +
				")  " +
				"nhanhang inner join " +
				"(  " +
					"select a.SANPHAM_FK, a.NGAYNHAN, b.ten  " +
					"from ERP_MUAHANG_SP a inner join ERP_KHOTT b on a.khott_fk = b.pk_seq  " +
					"where MUAHANG_FK = '" + soPO + "' " +
				")  " +
				"muahang on nhanhang.sanpham_fk = muahang.sanpham_fk and nhanhang.ngaynhandukien = muahang.ngaynhan";
		}
		else
		{
			query = "select nhanhang.*, muahang.ten as khoTen  " +
				"from  " +
				"( " +
					"select a.SANPHAM_FK, b.MA as spMa, b.TEN as spTen, a.NGAYNHANDUKIEN, a.SOLUONGDAT, a.SOLUONGNHAN, c.donvi, cast(ROUND(b.TRONGLUONG, 5) as numeric(10, 5)) as TRONGLUONG, cast(ROUND(isnull(b.THETICH, 0), 5) as numeric(10, 5)) as THETICH " + 
					"from ERP_NHANHANG_SANPHAM a inner join ERP_SANPHAM b on a.SANPHAM_FK = b.PK_SEQ inner join DONVIDOLUONG c on b.dvdl_fk = c.pk_seq  " +
					"where a.NHANHANG_FK = '" + this.id + "' " +
				")  " +
				"nhanhang inner join " +
				"(  " +
					"select a.SANPHAM_FK, a.NGAYNHAN, b.ten  " +
					"from ERP_TRAHANG_SP a inner join ERP_KHOTT b on a.khott_fk = b.pk_seq  " +
					"where TRAHANG_FK = '" + soPO + "' " +
				")  " +
				"muahang on nhanhang.sanpham_fk = muahang.sanpham_fk and nhanhang.ngaynhandukien = muahang.ngaynhan";
		}*/
		
		System.out.println("[ErpNhanhang_Giay.initSanPhamPdf] query = " + query);
		
		ResultSet rsSp = db.get(query);
		
		if (rsSp != null)
		{
			try
			{
				ISanpham sanpham;
				List<ISanpham> spList = new ArrayList<ISanpham>();
				while (rsSp.next())
				{
					String spId = rsSp.getString("spId");
					System.out.println("[ErpNhanhang_Giay.initSanPhamPdf] spId = " + spId);
					String spMa = rsSp.getString("spMa");
					String spTen = rsSp.getString("spTen");
					String ngaynhandk = rsSp.getString("NGAYNHANDUKIEN");
					String soluongdat = rsSp.getString("SOLUONGDAT");
					String soluongnhan = rsSp.getString("SOLUONGNHAN");
					String hansudung = rsSp.getString("khoTen"); // luu ten kho
					String dvdl = rsSp.getString("DonVi");
					
					sanpham = new Sanpham(spId, spMa, spTen, soluongnhan, hansudung, ngaynhandk, soluongdat, dvdl);
					sanpham.setTrongluong( rsSp.getString("TRONGLUONG") == null ? "0" : rsSp.getString("TRONGLUONG") );
					sanpham.setThetich( rsSp.getString("THETICH") == null ? "0" : rsSp.getString("THETICH") );
					spList.add(sanpham);
				}
				rsSp.close();
				this.spList = spList;
			}
			catch (Exception e)
			{
				System.out.println("[ErpNhanhang_Giay.initSanPhamPdf] Exception Message = " + e.getMessage());
			}
		}
	}
	
	
	public boolean createNhanHang()
	{
		String ngaytao = this.getDateTime();
		
		if (this.spList.size() <= 0)
		{
			this.msg = "Không có sản phẩm nào để nhận hàng, vui lòng kiểm tra lại";
			return false;
		}
		
		if(this.KhoCxlId==null|| this.KhoCxlId.length()==0){
			this.msg = "Vui lòng chọn kho chờ xử lý ";
			return false;
		}
		
		String cmd = "";
		
			cmd = " select ngaytra from dontrahang  where pk_seq = '" + this.poId + "' and ngaytra > '" + this.ngaynhanhang + "'";
		
		ResultSet rsCheck = db.get(cmd);
		
		String ngaymua = "";
		if (rsCheck != null)
		{
			try
			{
				while (rsCheck.next())
				{
					ngaymua = rsCheck.getString("ngaymua");
					rsCheck.close();
				}
			}
			catch (SQLException e){}
		}
		
		if (ngaymua.length() > 0)
		{
			
				this.msg = "Ngày nhận hàng(" + this.ngaynhanhang + ") phải lớn hơn ngày trả hàng (" + ngaymua + ")";
				return false;
		}
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String muahang_fk = "null";
			String trahang_fk = "null";
			
			
				trahang_fk = this.poId;
			
			String ldn_fk = "100004";
			
			String NCC_KH_FK = "null";
			if(this.nccId.trim().length() > 0)
				NCC_KH_FK = this.nccId;
			
			String query =  " insert ERP_NHANHANG(NGAYNHAN, NGAYCHOT, LOAIHANGHOA_FK, NOIDUNGNHAN_FK, SOHOADON, DIENGIAI, MUAHANG_FK, TRAHANG_FK, NGAYTAO, NGAYSUA, NGUOITAO, NGUOISUA, TRANGTHAI, CONGTY_FK, NoiDungNhap_fk, NCC_KH_FK,KHOCHOXULY_FK) " +
							" values('" +this.ngaynhanhang +"', '" + this.ngaynhanhang + "', '" + this.loaihanghoa + "', '" + this.ndnId + "', '" +this.sohoadon +"', N'" +this.diengiai +"',  " + muahang_fk + ",  "
								+ trahang_fk + " , '" + ngaytao +"', '" +ngaytao +"', '" +this.userId + "', '" +this.userId +"', '0', '" + this.congtyId + "', " + ldn_fk + ", " + NCC_KH_FK + ","+this.KhoCxlId+")";

			if (!db.update(query))
			{
				this.msg = "Khong the tao moi Nhan hang: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			String nhCurrent = "";
			query = "select IDENT_CURRENT('Erp_NHANHANG') as nhId";
			
			ResultSet rsNh = db.get(query);
			if (rsNh.next())
			{
				nhCurrent = rsNh.getString("nhId");
				rsNh.close();
			}
			
			if (this.spList.size() > 0)
			{
				for (int i = 0; i < spList.size(); i++)
				{
					ISanpham sp = spList.get(i);
					
					if (sp.getSoluongnhan() != "") // chi luu nhung san pham nguoi dung nhap so luong
					{
						String SanPham_FK = "NULL";
						String ChiPhi_FK = "NULL";
						String TaiSan_FK = "NULL";
						
						if(this.loaihanghoa.equals("0"))
						{
							SanPham_FK = sp.getId();
							ChiPhi_FK = "NULL";
							TaiSan_FK = "NULL";
						}
						else
						{
							if(this.loaihanghoa.equals("1"))
							{
								SanPham_FK = "NULL";
								ChiPhi_FK = "NULL";
								TaiSan_FK = sp.getId();
							}
							else
							{
								SanPham_FK = "NULL";
								ChiPhi_FK = sp.getId();
								TaiSan_FK = "NULL";
							}
						}
						
						double soluongnhan = 0;
						if(sp.getSoluongnhan().trim().length() < 0)
						{
							this.msg = "Bạn phải nhập số lượng nhận cho sản phẩm: " + sp.getMa();
							db.getConnection().rollback();
							return false;
						}
						else
						{
							soluongnhan = Double.parseDouble(sp.getSoluongnhan().replaceAll(",", ""));
							
							if(sp.getDungsai().trim().length() <= 0)
								sp.setDungsai("0");
							
							//double dungsai = Double.parseDouble(sp.getDungsai().replaceAll(",", ""));
							//double soluongdat = Double.parseDouble(sp.getSoluongdat().replaceAll(",", ""));
							
							//double slgMax = soluongdat + Math.abs(dungsai) * soluongdat / 100;
							double slgMax = Double.parseDouble(sp.getSoluongMaxNhan());
							
							if(soluongnhan > slgMax)
							{
								this.msg = "Tổng số lượng nhận của sản phẩm: " + sp.getMa() + " không được phép vượt quá tổng đặt (" + sp.getSoluongdat() + ") và dung sai cho phép ( " + sp.getDungsai() + " ). Bạn chỉ có thể nhận tối đa là ( " + slgMax + " )  ";
								db.getConnection().rollback();
								return false;
							}
						}
						
						String khonhan = sp.getKhonhanId().trim().length() <= 0 ? "null" : sp.getKhonhanId().trim();
						if(this.loaihanghoa.equals("0") && sp.getKhonhanId().trim().length() <= 0)
						{
							this.msg = "Vui lòng kiểm tra lại kho nhận của sản phẩm ( " + sp.getMa() + " ) ";
							db.getConnection().rollback();
							return false;
						}
						
						query = "insert ERP_NHANHANG_SANPHAM(NHANHANG_FK, SANPHAM_FK, TAISAN_FK, CHIPHI_FK, DIENGIAI, DONVI, NGAYNHANDUKIEN, KHONHAN, SOLUONGDAT, SOLUONGNHAN, DUNGSAI, DONGIA, TIENTE_FK, TYGIAQUYDOI, DONGIAVIET) " +
								"values('" + nhCurrent + "', " + SanPham_FK + ", " + TaiSan_FK + ", " + ChiPhi_FK + ", N'" + sp.getDiengiai() + "', N'" + sp.getDvdl() + "', '" + sp.getNgaynhandukien() + "', " + khonhan + ", " +
										"'" + sp.getSoluongdat().replaceAll(",", "") + "',  '" + sp.getSoluongnhan().replaceAll(",", "") + "', '" + sp.getDungsai() + "', " + Double.parseDouble(sp.getDongia().replaceAll(",", "")) + ", '" + sp.getTiente() + "', '" + sp.getTigiaquydoi() + "', '" + sp.getDongiaViet().replaceAll(",", "") + "')";
						
						if (!db.update(query))
						{
							this.msg = "Khong the tao moi ERP_NHANHANG_SANPHAM: " + query;
							db.getConnection().rollback();
							return false;
						}
						
						List<ISpDetail> detailList = sp.getSpDetail();
						for (int j = 0; j < detailList.size(); j++)
						{
							ISpDetail detail = detailList.get(j);
							
							if (detail.getSoluong().trim().length() > 0 && !detail.getSoluong().equals("0") && detail.getSolo() != "" && detail.getNgaySx() != "" )
							{
								query = "insert ERP_NHANHANG_SP_CHITIET(NHANHANG_FK, SANPHAM_FK, LANNHAN, SOLO, SOLUONG, NGAYSANXUAT, NGAYHETHAN, NGAYNHANDUKIEN,khu_fk) " +
										"values('" + nhCurrent + "', '" + sp.getId() + "', '" + Integer.toString(j + 1) + "', '" + detail.getSolo() + "', " +
												"'" + detail.getSoluong().replaceAll(",", "") + "', '" +detail.getNgaySx() +"', '" + detail.getNgayHethan() + "','" + sp.getNgaynhandukien() + "',"+(detail.getkhuid().length()>0? detail.getkhuid():"NULL")+")";
							 
								if (!db.update(query))
								{
									this.msg = "Khong the tao moi ERP_NHANHANG_SP_CHITIET: " + query;
									 
									db.getConnection().rollback();
									return false;
								}
							}
						}
					}
				}
			}
			
			query=" UPDATE DONTRAHANG SET TRANGTHAI=5 WHERE trangthai < 5 and PK_SEQ="+this.poId ;
			if (!db.update(query))
			{ 
				this.msg = "Không thể cập nhật dòng lệnh : " + query;
				db.getConnection().rollback();
				return false;
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;
		}
		catch (Exception e)
		{	
			e.printStackTrace();
			db.update("rollback");
			this.msg=e.getMessage();
			return false;
		}
		
	}
	
	
	public boolean updateNhanHang()
	{
		if (this.spList.size() <= 0)
		{
			this.msg = "Không có sản phẩm nào để nhận hàng, vui lòng kiểm tra lại";
			return false;
		}
		
		if(this.KhoCxlId==null|| this.KhoCxlId.length()==0){
			this.msg = "Vui lòng chọn kho chờ xử lý ";
			return false;
		}
		
		

		String cmd = "select ngaymua from erp_muahang where pk_seq = '" + this.poId + "' and ngaymua > '" + this.ngaynhanhang + "'";
		ResultSet rsCheck = db.get(cmd);
		
		String ngaymua = "";
		if (rsCheck != null)
		{
			try
			{
				while (rsCheck.next())
				{
					ngaymua = rsCheck.getString("ngaymua");
					rsCheck.close();
				}
			}
			catch (SQLException e) { }
		}
		
		if (ngaymua.length() > 0)
		{
			this.msg = "Ngày nhận hàng(" + this.ngaynhanhang + ") phải lớn hơn ngày đặt hàng (" + ngaymua + ")";
			return false;
		}
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String muahang_fk = "null";
			String trahang_fk = "null";
			
				trahang_fk = this.poId;
			
			String ldn_fk = "100004";
			
			String NCC_KH_FK = "null";
			if(this.nccId.trim().length() > 0)
				NCC_KH_FK = this.nccId;
			
			String query = "update ERP_NHANHANG set KHOCHOXULY_FK="+this.KhoCxlId+", NGAYNHAN = '" + this.ngaynhanhang + "', NGAYCHOT = '" + this.ngaychot + "', NOIDUNGNHAN_FK = '" + this.ndnId + "', SOHOADON = '" + this.sohoadon + "', " +
							"DIENGIAI = N'" + this.diengiai + "',  " +
							"MUAHANG_FK = " + muahang_fk + ", TRAHANG_FK = " + trahang_fk + ", NGAYSUA = '" + this.getDateTime() + "', " + "NGUOISUA = '" + this.userId + "', NoiDungNhap_fk = " + ldn_fk + ", NCC_KH_FK = " + NCC_KH_FK + " " +
							"where pk_seq = '" + this.id + "'";
			
			System.out.println("Query update NH: " + query);
			
			if (!db.update(query))
			{
				this.msg = "Khong the tao moi Nhan hang: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_NHANHANG_SANPHAM where nhanhang_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat ERP_NHANHANG_SANPHAM: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_NHANHANG_SP_CHITIET where nhanhang_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat ERP_NHANHANG_SP_CHITIET: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			if (this.spList.size() > 0)
			{
				for (int i = 0; i < spList.size(); i++)
				{
					ISanpham sp = spList.get(i);
					
					if (sp.getSoluongnhan() != "") // chi luu nhung san pham nguoi dung nhap so luong
					{
						String SanPham_FK = "NULL";
						String ChiPhi_FK = "NULL";
						String TaiSan_FK = "NULL";
						
						if(this.loaihanghoa.equals("0"))
						{
							SanPham_FK = sp.getId();
							ChiPhi_FK = "NULL";
							TaiSan_FK = "NULL";
						}
						else
						{
							if(this.loaihanghoa.equals("1"))
							{
								SanPham_FK = "NULL";
								ChiPhi_FK = "NULL";
								TaiSan_FK = sp.getId();
							}
							else
							{
								SanPham_FK = "NULL";
								ChiPhi_FK = sp.getId();
								TaiSan_FK = "NULL";
							}
						}
						
						double soluongnhan = 0;
						if(sp.getSoluongnhan().trim().length() < 0)
						{
							this.msg = "Bạn phải nhập số lượng nhận cho sản phẩm: " + sp.getMa();
							db.getConnection().rollback();
							return false;
						}
						else
						{
							soluongnhan = Double.parseDouble(sp.getSoluongnhan().replaceAll(",", ""));
							
							if(sp.getDungsai().trim().length() <= 0)
								sp.setDungsai("0");
							
							//double dungsai = Double.parseDouble(sp.getDungsai().replaceAll(",", ""));
							//double soluongdat = Double.parseDouble(sp.getSoluongdat().replaceAll(",", ""));
							
							//double slgMax = soluongdat + Math.abs(dungsai) * soluongdat / 100;
							double slgMax = Double.parseDouble(sp.getSoluongMaxNhan());
							
							if(soluongnhan > slgMax)
							{
								this.msg = "Tổng số lượng nhận của sản phẩm: " + sp.getMa() + " không được phép vượt quá tổng đặt (" + sp.getSoluongdat() + ") và dung sai cho phép ( " + sp.getDungsai() + " ). Bạn chỉ có thể nhận tối đa là ( " + slgMax + " )  ";
								db.getConnection().rollback();
								return false;
							}
						}
						
						String khonhan = sp.getKhonhanId().trim().length() <= 0 ? "null" : sp.getKhonhanId().trim();
						if(this.loaihanghoa.equals("0") && sp.getKhonhanId().trim().length() <= 0)
						{
							this.msg = "Vui lòng kiểm tra lại kho nhận của sản phẩm ( " + sp.getMa() + " ) ";
							db.getConnection().rollback();
							return false;
						}
						
						query = "insert ERP_NHANHANG_SANPHAM(NHANHANG_FK, SANPHAM_FK, TAISAN_FK, CHIPHI_FK, DIENGIAI, DONVI, NGAYNHANDUKIEN, KHONHAN, SOLUONGDAT, SOLUONGNHAN, DUNGSAI, DONGIA, TIENTE_FK, TYGIAQUYDOI, DONGIAVIET) " +
								"values('" + this.id + "', " + SanPham_FK + ", " + TaiSan_FK + ", " + ChiPhi_FK + ", N'" + sp.getDiengiai() + "', N'" + sp.getDvdl() + "', '" + sp.getNgaynhandukien() + "', " + khonhan + ", " +
										"'" + sp.getSoluongdat().replaceAll(",", "") + "',  '" + sp.getSoluongnhan().replaceAll(",", "") + "', '" + sp.getDungsai() + "', " + Double.parseDouble(sp.getDongia().replaceAll(",", "")) + ", '" + sp.getTiente() + "', '" + sp.getTigiaquydoi() + "', '" + sp.getDongiaViet().replaceAll(",", "") + "')";
						
						if (!db.update(query))
						{
							this.msg = "Khong the tao moi ERP_NHANHANG_SANPHAM: " + query;
							System.out.println(this.msg);
							db.getConnection().rollback();
							return false;
						}
						
						List<ISpDetail> detailList = sp.getSpDetail();
						for (int j = 0; j < detailList.size(); j++)
						{
							ISpDetail detail = detailList.get(j);
							
							if (detail.getSoluong() != "" && !detail.getSoluong().equals("0") && detail.getSolo() != "" && detail.getNgaySx() != "" )
							{
								query = "insert ERP_NHANHANG_SP_CHITIET(NHANHANG_FK, SANPHAM_FK, LANNHAN, SOLO, SOLUONG, NGAYSANXUAT, NGAYHETHAN, NGAYNHANDUKIEN,khu_fk) " +
								"values('" + this.id + "', '" + sp.getId() + "', '" + Integer.toString(j + 1) + "', '" + detail.getSolo() + "', " +
										"'" + detail.getSoluong().replaceAll(",", "") + "', '" +detail.getNgaySx() +"', '" + detail.getNgayHethan() + "','" + sp.getNgaynhandukien() + "',"+(detail.getkhuid().length()>0? detail.getkhuid():"NULL")+")";
							 
								if (!db.update(query))
								{
									this.msg = "Khong the tao moi ERP_NHANHANG_SP_CHITIET: " + query;
								 
									db.getConnection().rollback();
									return false;
								}
							}
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
			db.update("rollback");
			return false;
		}
	}
	
	
	public void updateDonmuahang(String poId)
	{
		this.poId = poId;

		String query = "";
	
			 			
			query = " SELECT COUNT(*) as sodong " +
					"FROM   " +
					"(    " +
						"SELECT  A.SANPHAM_FK, A.SOLUONG, A.DONGIA, DTH.NGAYTRA as ngayNhan, 0 as DungSai, B.MA AS SPMA,  B.TEN AS SPTEN, B.HANSUDUNG, C.DONVI     " +
						"FROM DONTRAHANG DTH inner join DONTRAHANG_SP A on DTH.PK_SEQ = A.DONTRAHANG_FK   " +
							"left join ERP_SANPHAM B ON A.SANPHAM_FK = B.PK_SEQ   " +
							"LEFT JOIN 	DONVIDOLUONG C ON C.PK_SEQ=B.DVDL_FK     " +
						"WHERE DTH.PK_SEQ = '" + this.poId + "'  AND DTH.TRANGTHAI in (3, 4)    " +
					")   " +
					"MUAHANG   LEFT JOIN    " +
					"(   " +
						"SELECT B.SANPHAM_FK, B.NGAYNHANDUKIEN, ISNULL(SUM(B.SOLUONGNHAN), '0' ) AS SOLUONG 	  " +
						"FROM ERP_NHANHANG A  INNER JOIN ERP_NHANHANG_SANPHAM B ON A.PK_SEQ = B.NHANHANG_FK    " +
						"WHERE A.TRAHANG_FK = '" + this.poId + "' AND A.TRANGTHAI != '3'    " +
						"GROUP BY B.SANPHAM_FK, NGAYNHANDUKIEN   " +
					")   " +
					"NHANHANG  ON MUAHANG.SANPHAM_FK = NHANHANG.SANPHAM_FK  AND MUAHANG.NGAYNHAN = NHANHANG.NGAYNHANDUKIEN    " +
					"WHERE MUAHANG.SOLUONG - ISNULL(NHANHANG.SOLUONG, '0') > 0 ";
	
			System.out.println("___Check don tra hang: " + query);
			
			ResultSet nhanhangRs = db.get(query);
			int sodong = 0;
			if (nhanhangRs != null)
			{
				try
				{
					if (nhanhangRs.next())
					{
						sodong = nhanhangRs.getInt("sodong");
						nhanhangRs.close();
					}
				}
				catch (SQLException e) { }
				
				if (sodong <= 0) // het sp cp the nhan
				{
					query = "update DONTRAHANG set trangthai = '4' where pk_seq = '" + this.poId + "'";
				}
				else
				{
					String trangthai = "3";
					query = "select COUNT(*) as soDong    " +
							"from ERP_NHANHANG a inner join ERP_NHANHANG_SANPHAM b on a.PK_SEQ = b.NHANHANG_FK    " +
							"where a.TRAHANG_FK = '" + this.poId + "' and a.TRANGTHAI != '3'  ";
					
					ResultSet rsCheck = db.get(query);
					try 
					{
						if(rsCheck.next())
						{
							if(rsCheck.getInt("soDong") > 0)
								trangthai = "4";
						}
						rsCheck.close();
					} 
					catch (SQLException e) {}
					
					query = "update DONTRAHANG set trangthai = '" + trangthai + "' where pk_seq = '" + this.poId + "'";
				}
				
				db.update(query);
				System.out.println("Cap nhat tra hang: " + query);
			}
		
	}
	
	public void close()
	{
		try{
		
			if(spList!=null)
			{
				spList.clear();
			}
			
			if(ndnRs!=null){
				ndnRs.close();
			}
			
			if(mahangmuaRs!=null){
				mahangmuaRs.close();
			}
			if(dvthRs!=null){
				dvthRs.close();
			}
			if(poRs!=null){
				poRs.close();
			}
			db.shutDown();
			
		}catch(Exception er){
			
		}
	}
	
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public int getNgayhethan()
	{
		return this.ngayhethan;
	}
	
	public void setNgayhethan(int ngayhethan)
	{
		this.ngayhethan = ngayhethan;
	}
	
	public String getNdnId() {
		
		return this.ndnId;
	}

	
	public void setNdnId(String mhmId) {
		
		this.ndnId = mhmId;
	}

	
	public ResultSet getNdnList() {
		
		return this.ndnRs;
	}

	
	public void setNdnList(ResultSet ndnlist) {
		
		this.ndnRs = ndnlist;
	}

	public String getLoaihanghoa() 
	{
		return this.loaihanghoa;
	}

	public void setLoaihanghoa(String loaihh) 
	{
		this.loaihanghoa = loaihh;
	}

	public String getNgaychot() 
	{
		return this.ngaychot;
	}

	public void setNgaychot(String ngaychot)
	{
		this.ngaychot = ngaychot;
	}

	public String getCongtyId() 
	{
		return this.congtyId;
	}

	public void setCongtyId(String congtyId) 
	{
		this.congtyId = congtyId;
	}

	
	public void setLdnId(String ndnId) {
		
		this.ldnId = ndnId;
	}

	
	public String getLdnId() {
		
		return this.ldnId;
	}

	
	public ResultSet getLdnList() {
		
		return this.ldnRs;
	}

	
	public void setLdnList(ResultSet ldnList) {
		
		this.ldnRs = ldnList;
	}

	
	public void setNccId(String ndnId) {
		
		this.nccId = ndnId;
	}

	
	public String getNccId() {
		
		return this.nccId;
	}

	
	public ResultSet getNccList() {
		
		return this.nccRs;
	}

	
	public void setNccList(ResultSet nccList) {
		
		this.nccRs = nccList;
	}

	@Override
	public ResultSet getRsKhoCXl() {
		// TODO Auto-generated method stub
		return this.KhoCXLRs;
	}

	@Override
	public void setRsKhoCXl(ResultSet rs) {
		// TODO Auto-generated method stub
		this.KhoCXLRs=rs;
	}

	@Override
	public String getKhoCxlId() {
		// TODO Auto-generated method stub
		return this.KhoCxlId;
	}

	@Override
	public void setKhoCxlId(String khocxlid) {
		// TODO Auto-generated method stub
		this.KhoCxlId=khocxlid;
	}

	@Override
	public String getIsKhoNhanQL_Khuvuc() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultSet getKhuRs(String khoid) {
		// TODO Auto-generated method stub
		
		String query="select pk_seq,ten from erp_khuvuckho where khott_fk="+(khoid.length() >0?khoid:"0");
		return db.get(query);
		
	}

	@Override
	public ResultSet getkhoRs(String spid) {
		// TODO Auto-generated method stub
		String query = " select pk_seq as khoId,TEN as khoTen from ERP_KHOTT where LOAI=4 and PK_SEQ not in (select PK_SEQ from ERP_KHOTT where ma like '%-CXL-%' )  " +
		" and  TRANGTHAI = '1' and PK_SEQ in ( select distinct KHOTT_FK from ERP_KHOTT_SANPHAM where SANPHAM_FK = '" + spid + "' )";
		return db.get(query);
	}

	@Override
	public void init_nhanhang() {
		// TODO Auto-generated method stub
		String query="select KHACHHANG_FK  from DONTRAHANG where PK_SEQ= "+this.poId;
		ResultSet rs=db.get(query);
		try {
			if(rs.next()){
				
				this.nccId=rs.getString("KHACHHANG_FK");
			}
			rs.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.createRs();
	
	}
	
	
}
