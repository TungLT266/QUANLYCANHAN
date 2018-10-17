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

public class ErpNhanhang implements IErpNhanhang
{
	String congtyId;
	String userId;
	String id;
	String ngaynhanhang;
	String ngaychot;
	String sohoadon;
	
	String dvthId;
	ResultSet dvthRs;

	String poId;
	ResultSet poRs;
	
	String ndnId;
	ResultSet ndnRs;
	
	String mahangmuaId;
	int ngayhethan;
	ResultSet mahangmuaRs;
	
	String diengiai;
	String trangthai;
	String soluongPO;
	String soluongDaNhan;
	String loaihanghoa;
	
	List<ISanpham> spList;

	String msg;
	
	dbutils db;
	private Utility util;
	
	public ErpNhanhang()
	{
		this.userId = "";
		this.id = "";
		this.ngaynhanhang = "";
		this.ngaychot = "";
		this.sohoadon = "";
		this.diengiai = "";
		this.dvthId = "";
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
		this.spList = new ArrayList<ISanpham>();
		this.db = new dbutils();
		this.util=new Utility();
		
	}
	
	public ErpNhanhang(String id)
	{
		this.userId = "";
		this.id = id;
		this.ngaynhanhang = "";
		this.ngaychot = "";
		this.sohoadon = "";
		this.dvthId = "";
		this.poId = "";
		this.mahangmuaId = "";
		this.diengiai = "";
		this.trangthai = "";
		this.soluongPO = "";
		this.soluongDaNhan = "0";
		this.ngayhethan = 0;
		this.loaihanghoa = "0";

		this.msg = "";
		this.ndnId = "";
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
		return this.dvthId;
	}
	
	public void setDvthId(String dvthid)
	{
		this.dvthId = dvthid;
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
		
		this.ndnRs = db.get("select pk_seq, noidung from ERP_NOIDUNGNHANHANG where trangthai = 1");
		
		if (this.dvthId.length() > 0 && this.ndnId.trim().length() > 0)
		{
			String query = "";
			
			if(this.ndnId.equals("100000"))
			{
				query = " select A.PK_SEQ as poId, A.PREFIX + cast(A.PK_SEQ as nvarchar(10)) + N', Ngày mua ' + A.NGAYMUA as poTen " +
						" from ERP_MUAHANG A " +
						" inner join( " +
						"	SELECT 	MUAHANG_FK AS DMHID, " +
						"			CASE WHEN SUM(QUYETDINH) > 0 " + 
						"			THEN  ( " +
						"				SELECT COUNT(TRANGTHAI) - SUM(TRANGTHAI) " + 
						"				FROM ERP_DUYETMUAHANG  " +
						"				WHERE MUAHANG_FK = DUYETMUAHANG.MUAHANG_FK AND QUYETDINH = 1 " +
						"			) 	" +
						"			ELSE COUNT(TRANGTHAI) - SUM(TRANGTHAI) 	END AS DUYET " + 
						"	FROM ERP_DUYETMUAHANG DUYETMUAHANG " +
						"	GROUP BY MUAHANG_FK " +
						" ) DUYET ON DUYET.DMHID = A.PK_SEQ " +
						" where A.trangthai != 2 and ISNULL(DUYET.DUYET, 1) = 0 and donvithuchien_fk = '" + this.dvthId + "' and loaihanghoa_fk = '" + this.loaihanghoa + "' ";
				
				if (this.id.length() > 0)
				{
					query += " union " +
							 " select PK_SEQ as poId, PREFIX + cast(PK_SEQ as nvarchar(10)) + N', Ngày mua ' + NGAYMUA as poTen  " +
							 " from ERP_MUAHANG where loaihanghoa_fk = '" + this.loaihanghoa + "' and pk_seq in (select MUAHANG_FK from ERP_NHANHANG where PK_SEQ = '" + this.id + "')";
				}
			}
			else
			{
				query = "select PK_SEQ as poId, PREFIX + cast(PK_SEQ as nvarchar(10)) + N', Ngày trả ' + NGAYMUA as poTen " +
						"from ERP_TRAHANG where TRANGTHAI = '1' and donvithuchien_fk = '" + this.dvthId + "' ";
		
				if (this.id.length() > 0)
				{
					query += " union " + 
							 "select PK_SEQ as poId, PREFIX + cast(PK_SEQ as nvarchar(10)) + N', Ngày trả ' + NGAYMUA as poTen  " +
							 "from ERP_TRAHANG where pk_seq in (select TRAHANG_FK from ERP_NHANHANG where PK_SEQ = '" + this.id + "')";
				}
			}
			
			System.out.println("1.Khoi tao PO: " + query);
			this.poRs = db.get(query);
		}
		
		if (this.poId.length() > 0 && this.dvthId.length() > 0 && this.ndnId.trim().length() > 0 && this.spList.size() <= 0)
		{
			String query = "";
			
			NumberFormat formater = new DecimalFormat("#,###,###");
			
			if(this.ndnId.equals("100000"))
			{
				query = "SELECT MUAHANG.SANPHAM_FK, MUAHANG.SOLUONG - ISNULL(NHANHANG.SOLUONG, '0') AS SOLUONG, MUAHANG.DONGIA, 	" +
								"MUAHANG.NGAYNHAN, MUAHANG.DUNGSAI, MUAHANG.SPMA, MUAHANG.SPTEN, MUAHANG.HANSUDUNG, " +
								"MUAHANG.DONVI, MUAHANG.TienTe_fk, MUAHANG.TyGiaQuyDoi, MUAHANG.DonGiaViet   " +
						"FROM  " +
						"(   " +
							" SELECT case MH.loaihanghoa_fk when 0 then A.SANPHAM_FK when 1 then tscd.PK_SEQ else ncp.pk_seq end as SanPham_FK,  " +
									"case MH.loaihanghoa_fk when 0 then B.MA when 1 then tscd.Ma else ncp.Ten end AS SPMA,  " +
									"case MH.loaihanghoa_fk when 0 then B.Ten else A.diengiai end AS SPTEN,  " +
									"isnull(A.DONVI, 'NA') as DonVi, A.SOLUONG, A.DONGIA, A.NGAYNHAN, A.DUNGSAI, B.HANSUDUNG, mh.TienTe_fk, mh.TyGiaQuyDoi, A.DonGiaViet  " +
							"FROM ERP_MUAHANG_SP A INNER JOIN ERP_MUAHANG mh on A.muahang_fk = mh.pk_seq    " +
								"LEFT join ERP_SANPHAM B ON A.SANPHAM_FK = B.PK_SEQ    " +
								"LEFT JOIN erp_taisancodinh tscd on a.taisan_fk = tscd.pk_seq     " +
								"LEFT JOIN erp_nhomtaisan nts on tscd.NhomTaiSan_fk = nts.pk_seq     " +
								"LEFT JOIN erp_nhomchiphi ncp on a.chiphi_fk = ncp.pk_seq   " +
								"LEFT JOIN  erp_trungtamchiphi ttcp on ncp.ttchiphi_fk = ttcp.pk_seq    " +
							"WHERE A.MUAHANG_FK = '" + this.poId + "'   " +
						")  " +
						"MUAHANG   LEFT JOIN   " +
						"(  " +
							"SELECT case A.loaihanghoa_fk when 0 then B.SANPHAM_FK when 1 then B.TAISAN_FK else B.CHIPHI_FK end as SanPham_FK, " +
								"B.NGAYNHANDUKIEN, ISNULL(SUM(B.SOLUONGNHAN), '0' ) AS SOLUONG 	 " +
							"FROM ERP_NHANHANG A  " +
							"INNER JOIN ERP_NHANHANG_SANPHAM B ON A.PK_SEQ = B.NHANHANG_FK   " +
							"WHERE A.MUAHANG_FK = '" + this.poId + "' AND A.TRANGTHAI != '3'   ";
				
						//if(this.id.trim().length() > 0)
							//query += " AND A.PK_SEQ != '" + this.id + "' ";
						query += " GROUP BY A.loaihanghoa_fk, B.SANPHAM_FK, B.TAISAN_FK, B.CHIPHI_FK, NGAYNHANDUKIEN  " +
						
						")  " +
						"NHANHANG  ON MUAHANG.SANPHAM_FK = NHANHANG.SANPHAM_FK  AND MUAHANG.NGAYNHAN = NHANHANG.NGAYNHANDUKIEN   " +
						"WHERE MUAHANG.SOLUONG - ISNULL(NHANHANG.SOLUONG, '0') > 0";
			}
			else
			{
				query = "SELECT MUAHANG.SANPHAM_FK, MUAHANG.SOLUONG - ISNULL(NHANHANG.SOLUONG, '0') AS SOLUONG, MUAHANG.DONGIA, 	" +
								"MUAHANG.NGAYNHAN, MUAHANG.DUNGSAI, MUAHANG.SPMA, MUAHANG.SPTEN, MUAHANG.HANSUDUNG, MUAHANG.DONVI, " +
								"'100000' as TienTe_fk, '1' as  TyGiaQuyDoi, MUAHANG.DONGIA as DonGiaViet  " +
						"FROM  " +
						"(   " +
							"SELECT  A.SANPHAM_FK, A.SOLUONG, A.DONGIA, A.NGAYNHAN, A.DUNGSAI, B.MA AS SPMA,  B.TEN AS SPTEN,  B.HANSUDUNG,C.DONVI   " +
							"FROM ERP_TRAHANG_SP A  INNER join ERP_SANPHAM B ON A.SANPHAM_FK = B.PK_SEQ  LEFT JOIN 	DONVIDOLUONG C ON C.PK_SEQ=B.DVDL_FK    " +
							"WHERE A.TRAHANG_FK = '" + this.poId + "'  AND A.TRAHANG_FK IN (SELECT PK_SEQ FROM ERP_TRAHANG WHERE TRANGTHAI = 1 AND PK_SEQ = '" + this.poId + "')   " +
						")  " +
						"MUAHANG   LEFT JOIN   " +
						"(  " +
							"SELECT B.SANPHAM_FK, B.NGAYNHANDUKIEN, ISNULL(SUM(B.SOLUONGNHAN), '0' ) AS SOLUONG 	 " +
							"FROM ERP_NHANHANG A  INNER JOIN ERP_NHANHANG_SANPHAM B ON A.PK_SEQ = B.NHANHANG_FK   " +
							"WHERE A.TRAHANG_FK = '" + this.poId + "' AND A.TRANGTHAI != '3'   ";
				
						//if(this.id.trim().length() > 0)
							//query += " AND A.PK_SEQ != '" + this.id + "' ";
						query += " GROUP BY B.SANPHAM_FK, NGAYNHANDUKIEN  " +
						
						")  " +
						"NHANHANG  ON MUAHANG.SANPHAM_FK = NHANHANG.SANPHAM_FK  AND MUAHANG.NGAYNHAN = NHANHANG.NGAYNHANDUKIEN   " +
						"WHERE MUAHANG.SOLUONG - ISNULL(NHANHANG.SOLUONG, '0') > 0";
			}
			
			System.out.println("1.Init san pham PO: " + query);
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
						String soluongDat = formater.format(spRs.getDouble("soluong"));
						String hansudung = spRs.getString("HANSUDUNG");
						String ngaynhandukien = spRs.getString("NGAYNHAN");
						String dungsai = spRs.getString("DUNGSAI");
						String dvdl = spRs.getString("DonVi");
						String dongia = Double.toString(spRs.getDouble("DONGIA"));
						
						sp = new Sanpham(spId, spMa, spTen, "", hansudung, ngaynhandukien, soluongDat, dvdl);
						sp.setDungsai(dungsai);
						sp.setDongia(dongia);
						sp.setTiente(spRs.getString("TienTe_fk"));
						sp.setTigiaquydoi(spRs.getString("TyGiaQuyDoi"));
						sp.setDongiaViet(spRs.getString("DonGiaViet"));
						
						spList.add(sp);
					}
					spRs.close();
				}
				catch (SQLException e)
				{
				}
				
				this.spList = spList;
			}
		}
	}
	
	public void init()
	{
		String query =  " select a.PK_SEQ as nhId, a.loaihanghoa_fk, a.noidungnhan_fk, a.SOHOADON, a.NGAYNHAN, a.NGAYCHOT, a.diengiai, b.pk_seq as dvthId, b.TEN as dvthTen," +
						" 	case when a.noidungnhan_fk = 100000 then c.PK_SEQ else d.PK_SEQ end as PoId, a.TRANGTHAI " +
						"from erp_nhanhang a inner join ERP_DONVITHUCHIEN b on a.DONVITHUCHIEN_FK = b.PK_SEQ " +
						"left join ERP_MUAHANG c on a.MUAHANG_FK = c.PK_SEQ left join ERP_TRAHANG d on a.TRAHANG_FK = d.PK_SEQ " +
						"where a.pk_seq = '" + this.id + "' and b.pk_seq in "+this.util.quyen_donvithuchien(this.userId);
		
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
					this.dvthId = rs.getString("dvthId");
					this.poId = rs.getString("PoId");
					this.diengiai = rs.getString("diengiai");
					this.ndnId = rs.getString("noidungnhan_fk");
					this.loaihanghoa = rs.getString("loaihanghoa_fk");
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
	
	public void initPdf()
	{
		String query = "select a.PK_SEQ as nhId, a.noidungnhan_fk, a.NGAYNHAN, a.diengiai,  " +
							"case when a.noidungnhan_fk = 100000 then c.Prefix + cast(c.PK_SEQ as varchar(10)) else th.Prefix + cast(th.PK_SEQ as varchar(10)) end as PoId,  " +
							"case when a.noidungnhan_fk = 100000 then cast(d.pk_seq as nvarchar(10)) + ' - ' + d.ma + ', ' + d.TEN  " +
							"else  cast(e.pk_seq as nvarchar(10)) + ' - ' + e.ma + ', ' + e.TEN end as nccTen  " +
						"from erp_nhanhang a left join ERP_MUAHANG c on a.MUAHANG_FK = c.PK_SEQ left join ERP_TRAHANG th on a.TRAHANG_FK = th.PK_SEQ  " +
						"left join Erp_Nhacungcap d on c.NHACUNGCAP_FK = d.PK_SEQ  " +
						"left join NHAPHANPHOI e on th.NHAPHANPHOI_FK = e.PK_SEQ " +
						"where a.pk_seq = '" + this.id + "'	";
		
		System.out.print("Khoi tao nhan hang: " + query + "\n");
		ResultSet rs = db.get(query);
		if (rs != null)
		{
			try
			{
				while (rs.next())
				{
					this.ngaynhanhang = rs.getString("ngaynhan");
					this.dvthId = rs.getString("nccTen");
					this.poId = rs.getString("PoId");
					this.diengiai = rs.getString("diengiai");
					this.ndnId =  rs.getString("noidungnhan_fk");
				}
				rs.close();
			}
			catch (SQLException e)
			{
			}
		}
		
		this.initSanPhamPdf();
	}
	
	private void initSanPham()
	{
		NumberFormat formatter = new DecimalFormat("#,###,###"); 
		
		String query = "select  case nh.loaihanghoa_fk when 0 then A.SANPHAM_FK when 1 then tscd.PK_SEQ else ncp.pk_seq end as spId, " +
								"case nh.loaihanghoa_fk when 0 then sp.MA when 1 then tscd.Ma else ncp.Ten end AS spMa,  " +
								"case nh.loaihanghoa_fk when 0 then sp.Ten else a.DienGiai end AS spTen, " +
								" a.NGAYNHANDUKIEN, a.DUNGSAI, a.DONGIA, a.SOLUONGDAT, a.SOLUONGNHAN, isnull(sp.HANSUDUNG, '0') as HanSuDung, " +
								"isnull(a.DonVi, 'NA') as donvi, a.TienTe_Fk, a.TyGiaQuyDoi, a.DonGiaViet     " +
					  "from ERP_NHANHANG_SANPHAM a inner join ERP_NhanHang nh on a.nhanhang_fk = nh.pk_seq   " +
					  	"LEFT join ERP_SANPHAM sp on a.SANPHAM_FK = sp.PK_SEQ   " +
					  	"LEFT JOIN erp_taisancodinh tscd on a.taisan_fk = tscd.pk_seq        " +
					  	"LEFT JOIN erp_nhomchiphi ncp on a.chiphi_fk = ncp.pk_seq    " +
					  "where a.NHANHANG_FK = '" + this.id + "'";
		
		System.out.println("initSanPham: " + query);
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
					String spMa = rsSp.getString("spMa");
					String spTen = rsSp.getString("spTen");
					String ngaynhandk = rsSp.getString("NGAYNHANDUKIEN");
					
					String soluongdat = formatter.format(rsSp.getDouble("SOLUONGDAT"));
					String soluongnhan = formatter.format(rsSp.getDouble("SOLUONGNHAN"));
					
					String hansudung = rsSp.getString("HANSUDUNG");
					String dungsai = rsSp.getString("DUNGSAI");
					String dvdl = rsSp.getString("DonVi");
					String dongia = rsSp.getString("DONGIA");
					
					sanpham = new Sanpham(spId, spMa, spTen, soluongnhan, hansudung, ngaynhandk, soluongdat, dvdl);
					if (soluongdat != "" && soluongnhan != "")
						sanpham.setCOnlai(Float.toString(Float.parseFloat(soluongdat.replaceAll(",", "") ) - Float.parseFloat(soluongnhan.replaceAll(",", ""))));
					
					if(this.loaihanghoa.equals("0"))
					{
						String comand = " select sanpham_fk, solo, soluong, ngaysanxuat, ngayhethan from ERP_NHANHANG_SP_CHITIET " +
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
								
								spCon = new SpDetail(spConMa, solo, soluong, ngaysanxuat, ngayhethan);
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
					
					spList.add(sanpham);
				}
				rsSp.close();
				this.spList = spList;
			}
			catch (Exception e)
			{
				System.out.println("115.Exception: " + e.getMessage());
				e.printStackTrace();
				
			}
		}
	}
	
	
	private void initSanPhamPdf()
	{
		String query = "";
		
		String soPO = this.poId.substring(3, this.poId.length());
		if(this.ndnId.equals("100000"))
		{
			query = "select nhanhang.*, muahang.ten as khoTen  " +
				"from  " +
				"( " +
					"select a.SANPHAM_FK, b.MA as spMa, b.TEN as spTen, a.NGAYNHANDUKIEN, a.SOLUONGDAT, a.SOLUONGNHAN, c.donvi  " +
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
					"select a.SANPHAM_FK, b.MA as spMa, b.TEN as spTen, a.NGAYNHANDUKIEN, a.SOLUONGDAT, a.SOLUONGNHAN, c.donvi  " +
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
		}
		
		System.out.println("San pham Pdf: " + query + "\n");
		
		ResultSet rsSp = db.get(query);
		
		if (rsSp != null)
		{
			try
			{
				ISanpham sanpham;
				List<ISanpham> spList = new ArrayList<ISanpham>();
				while (rsSp.next())
				{
					String spId = rsSp.getString("donvi");
					String spMa = rsSp.getString("spMa");
					String spTen = rsSp.getString("spTen");
					String ngaynhandk = rsSp.getString("NGAYNHANDUKIEN");
					String soluongdat = rsSp.getString("SOLUONGDAT");
					String soluongnhan = rsSp.getString("SOLUONGNHAN");
					String hansudung = rsSp.getString("khoTen"); // luu ten kho
					String dvdl = rsSp.getString("DonVi");
					
					System.out.println("Kho ten: " + hansudung + "\n");
					
					sanpham = new Sanpham(spId, spMa, spTen, soluongnhan, hansudung, ngaynhandk, soluongdat, dvdl);
					spList.add(sanpham);
				}
				rsSp.close();
				this.spList = spList;
			}
			catch (Exception e)
			{
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
		
		if(this.ndnId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn nội dung nhận hàng";
			return false;
		}
		
		String cmd = "";
		
		if(this.ndnId.equals("100000"))
			cmd = "select ngaymua from erp_muahang where pk_seq = '" + this.poId + "' and ngaymua > '" + this.ngaynhanhang + "'";
		else
			cmd = "select ngaymua from erp_trahang where pk_seq = '" + this.poId + "' and ngaymua > '" + this.ngaynhanhang + "'";
		
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
			if(this.ndnId.equals("100000"))
				this.msg = "Ngày nhận hàng(" + this.ngaynhanhang + ") phải lớn hơn ngày đặt hàng (" + ngaymua + ")";
			else
				this.msg = "Ngày nhận hàng(" + this.ngaynhanhang + ") phải lớn hơn ngày trả hàng (" + ngaymua + ")";
			return false;
		}
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String muahang_fk = "null";
			String trahang_fk = "null";
			if(this.ndnId.equals("100000"))
				muahang_fk = this.poId;
			else
				trahang_fk = this.poId;
			
			String query = "insert ERP_NHANHANG(NGAYNHAN, NGAYCHOT, LOAIHANGHOA_FK, NOIDUNGNHAN_FK, SOHOADON, DIENGIAI, DONVITHUCHIEN_FK, MUAHANG_FK, TRAHANG_FK, NGAYTAO, NGAYSUA, NGUOITAO, NGUOISUA, TRANGTHAI, CONGTY_FK) " +
							"values('" +this.ngaynhanhang +"', '" + this.ngaynhanhang + "', '" + this.loaihanghoa + "', '" + this.ndnId + "', '" +this.sohoadon +"', N'" +this.diengiai +"', '" +this.dvthId +"', " + muahang_fk + ",  "
								+ trahang_fk + " , '" + ngaytao +"', '" +ngaytao +"', '" +this.userId + "', '" +this.userId +"', '0', '" + this.congtyId + "')";

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
							
							double dungsai = Double.parseDouble(sp.getDungsai().replaceAll(",", ""));
							double soluongdat = Double.parseDouble(sp.getSoluongdat().replaceAll(",", ""));
							
							double slgMax = soluongdat + Math.abs(dungsai) * soluongdat / 100;
							
							if(soluongnhan > slgMax)
							{
								this.msg = "Tổng số lượng nhận của sản phẩm: " + sp.getMa() + " không được phép vượt quá tổng đặt (" + sp.getSoluongdat() + ") và dung sai cho phép ( " + sp.getDungsai() + " ) ";
								db.getConnection().rollback();
								return false;
							}
						}
						
						query = "insert ERP_NHANHANG_SANPHAM(NHANHANG_FK, SANPHAM_FK, TAISAN_FK, CHIPHI_FK, DIENGIAI, NGAYNHANDUKIEN, SOLUONGDAT, SOLUONGNHAN, DUNGSAI, DONGIA, TIENTE_FK, TYGIAQUYDOI, DONGIAVIET) " +
								"values('" + nhCurrent + "', " + SanPham_FK + ", " + TaiSan_FK + ", " + ChiPhi_FK + ", N'" + sp.getDiengiai() + "', '" + sp.getNgaynhandukien() + "', " +
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
								query = "insert ERP_NHANHANG_SP_CHITIET(NHANHANG_FK, SANPHAM_FK, LANNHAN, SOLO, SOLUONG, NGAYSANXUAT, NGAYHETHAN,NGAYNHANDUKIEN) " +
										"values('" + nhCurrent + "', '" + sp.getId() + "', '" + Integer.toString(j + 1) + "', '" + detail.getSolo() + "', " +
												"'" + detail.getSoluong().replaceAll(",", "") + "', '" +detail.getNgaySx() +"', '" + detail.getNgayHethan() + "','" + sp.getNgaynhandukien() + "')";
								
								System.out.println("ERP_NHANHANG_SP_CHITIET: " + query);
								
								if (!db.update(query))
								{
									this.msg = "Khong the tao moi ERP_NHANHANG_SP_CHITIET: " + query;
									System.out.println(this.msg);
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
		catch (SQLException e)
		{
			System.out.println("Exception: " + e.getMessage());
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
		
		if(this.ndnId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn nội dung nhận hàng";
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
			if(this.ndnId.equals("100000"))
				muahang_fk = this.poId;
			else
				trahang_fk = this.poId;
			
			String query = "update ERP_NHANHANG set NGAYNHAN = '" + this.ngaynhanhang + "', NGAYCHOT = '" + this.ngaychot + "', NOIDUNGNHAN_FK = '" + this.ndnId + "', SOHOADON = '" + this.sohoadon + "', " +
							"DIENGIAI = N'" + this.diengiai + "', " + "DONVITHUCHIEN_FK = '" + this.dvthId + "', " +
							"MUAHANG_FK = " + muahang_fk + ", TRAHANG_FK = " + trahang_fk + ", NGAYSUA = '" + this.getDateTime() + "', " + "NGUOISUA = '" + this.userId + "' " +
							"where pk_seq = '" + this.id + "'";
			
			System.out.println("Query update: " + query);
			
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
							
							double dungsai = Double.parseDouble(sp.getDungsai().replaceAll(",", ""));
							double soluongdat = Double.parseDouble(sp.getSoluongdat().replaceAll(",", ""));
							
							double slgMax = soluongdat + Math.abs(dungsai) * soluongdat / 100;
							
							if(soluongnhan > slgMax)
							{
								this.msg = "Tổng số lượng nhận của sản phẩm: " + sp.getMa() + " không được phép vượt quá tổng đặt (" + sp.getSoluongdat() + ") và dung sai cho phép ( " + sp.getDungsai() + " ) ";
								db.getConnection().rollback();
								return false;
							}
						}
						
						query = "insert ERP_NHANHANG_SANPHAM(NHANHANG_FK, SANPHAM_FK, TAISAN_FK, CHIPHI_FK, DIENGIAI, NGAYNHANDUKIEN, SOLUONGDAT, SOLUONGNHAN, DUNGSAI, DONGIA, TIENTE_FK, TYGIAQUYDOI, DONGIAVIET) " +
								"values('" + this.id + "', " + SanPham_FK + ", " + TaiSan_FK + ", " + ChiPhi_FK + ", N'" + sp.getDiengiai() + "', '" + sp.getNgaynhandukien() + "', " +
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
								query = "insert ERP_NHANHANG_SP_CHITIET(NHANHANG_FK, SANPHAM_FK, LANNHAN, SOLO, SOLUONG, NGAYSANXUAT, NGAYHETHAN,NGAYNHANDUKIEN) " +
										"values('" + this.id + "', '" + sp.getId() + "', '" + Integer.toString(j + 1) + "', '" + detail.getSolo() + "', " +
												"'" + detail.getSoluong().replaceAll(",", "") + "', '" +detail.getNgaySx() +"', '" + detail.getNgayHethan() + "','" + sp.getNgaynhandukien() + "')";
								
								System.out.println("ERP_NHANHANG_SP_CHITIET: " + query);
								
								if (!db.update(query))
								{
									this.msg = "Khong the tao moi ERP_NHANHANG_SP_CHITIET: " + query;
									System.out.println(this.msg);
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
			db.update("rollback");
			return false;
		}
	}
	
	
	public void updateDonmuahang(String poId)
	{
		this.poId = poId.substring(5, poId.length());
		
		// Cap nhat trang thai PO la hoan tat neu tong so luong nhan >= tong so luong dat
		String query = "select noidungnhan_fk from ERP_NhanHang where pk_seq = '" + this.id + "'";
		ResultSet rsNoiDungNhan = db.get(query);
		try 
		{
			if(rsNoiDungNhan.next())
			{
				this.ndnId = rsNoiDungNhan.getString("noidungnhan_fk");
			}
			rsNoiDungNhan.close();
		} 
		catch (Exception e1) {}
		
		
		
		if(this.ndnId.equals("100000"))
		{
			query = "select count(muahang.SANPHAM_FK) as sodong   " +
				"from   " +
				"(    " +
					"select case a.loaihanghoa_fk when 0 then b.SANPHAM_FK when 1 then b.TaiSan_fk else b.ChiPhi_fk end as SANPHAM_FK, sum(SOLUONG) as SOLUONG     " +
					"from erp_muahang a inner join erp_muahang_sp b on b.MuaHang_FK = a.pk_seq    " +
					"where MUAHANG_FK = '" + this.poId + "'    " +
					"group by a.loaihanghoa_fk, b.SANPHAM_FK, b.TaiSan_fk, b.ChiPhi_fk  " +
				") " +
				"muahang   left join    " +
				"( " +
					"select case a.loaihanghoa_fk when 0 then b.SANPHAM_FK when 1 then b.TaiSan_fk else b.ChiPhi_fk end as SANPHAM_FK,  " +
						"isnull(SUM(b.soluongnhan), '0') as soluong   " +
					"from ERP_NHANHANG a inner join ERP_NHANHANG_SANPHAM b on a.PK_SEQ = b.NHANHANG_FK  " +
					"where a.MUAHANG_FK = '" + this.poId + "' and a.TRANGTHAI != '3'   " +
					"group by a.loaihanghoa_fk, b.SANPHAM_FK, b.TaiSan_fk, b.ChiPhi_fk  " +
				") nhanhang   " +
				"on muahang.SANPHAM_FK = nhanhang.SANPHAM_FK  where muahang.SOLUONG - isnull(nhanhang.soluong, '0') > 0";
			
			System.out.println("1.Check don mua hang: " + query);
			
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
				catch (SQLException e)
				{
				}
				
				if (sodong <= 0) // het sp co the nhan
					query = "update ERP_MUAHANG set trangthai = '2' where pk_seq = '" + this.poId + "'";
				else
				{
					//Neu da xoa nhan hang, ma chua co nhan hang phat sinh thi trang thai = 0
					String trangthai = "0";
					query = "select COUNT(*) as soDong    " +
							"from ERP_NHANHANG a inner join ERP_NHANHANG_SANPHAM b on a.PK_SEQ = b.NHANHANG_FK    " +
							"where a.MUAHANG_FK = '" + this.poId + "' and a.TRANGTHAI != '3'  ";
					
					ResultSet rsCheck = db.get(query);
					try 
					{
						if(rsCheck.next())
						{
							if(rsCheck.getInt("soDong") > 0)
								trangthai = "1";
						}
						rsCheck.close();
					} 
					catch (SQLException e) {}
					
					
					query = "update ERP_MUAHANG set trangthai = '" + trangthai + "' where pk_seq = '" + this.poId + "'";
				}
				
				db.update(query);
				System.out.println("2.Cap nhat mua hang: " + query);
			}
		}
		else
		{
			query = "select count(muahang.SANPHAM_FK) as sodong " + "from  (  " + "select SANPHAM_FK, sum(SOLUONG) as SOLUONG   " +
					"from erp_trahang_sp where TRAHANG_FK = '" + this.poId + "'  " + "group by SANPHAM_FK ) muahang  " +
					"left join  " + "(select b.SANPHAM_FK, isnull(SUM(b.soluongnhan), '0') as soluong  " +
					"from ERP_NHANHANG a inner join ERP_NHANHANG_SANPHAM b on a.PK_SEQ = b.NHANHANG_FK " +
					"where a.TRAHANG_FK = '" + this.poId + "' and a.TRANGTHAI != '3'  group by b.SANPHAM_FK) nhanhang  " +
					"on muahang.SANPHAM_FK = nhanhang.SANPHAM_FK " + "where muahang.SOLUONG - isnull(nhanhang.soluong, '0') > 0";
	
			System.out.println("Check don tra hang: " + query);
			
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
				catch (SQLException e)
				{
				}
				
				if (sodong <= 0) // het sp cp the nhan
					query = "update ERP_TRAHANG set trangthai = '2' where pk_seq = '" + this.poId + "'";
				else
					query = "update ERP_TRAHANG set trangthai = '1' where pk_seq = '" + this.poId + "'";
				
				db.update(query);
				System.out.println("Cap nhat tra hang: " + query);
			}
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
	
}
