package geso.traphaco.distributor.beans.thanhlyhopdong.imp;

import geso.traphaco.distributor.beans.thanhlyhopdong.IErpThanhlyhopdongNpp;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ErpThanhlyhopdongNpp implements IErpThanhlyhopdongNpp
{
	String userId;
	String id;
	
	String ma;
	String hopdongchung;
	String tungay;
	String denngay;
	String ghichu;
	ResultSet mahdrs;
	String msg;
	String trangthai;
	
	String loaidonhang; 
	String chietkhau;
	String vat;
	
	String khoNhanId;
	ResultSet khoNhanRs;
	String mahoadon[];
	String khId;
	ResultSet khRs;
	int loaithanhly = 0;
	String khApdungId;
	ResultSet khApdungRs;
	String ngaytl = "";
	String ngaytlkt ="";
	String dvkdId;
	ResultSet dvkdRs;
	
	String kbhId;
	ResultSet kbhRs;
	
	String gsbhId;
	ResultSet gsbhRs;
	
	String ddkdId;
	ResultSet ddkdRs;
	
	String npp_duocchon_id;
	String tdv_dangnhap_id;
	
	String hopdongId;
	ResultSet hopdongRs;
	
	ResultSet dvtRs;

	String[] spId;
	String[] spMa;
	String[] spTen;
	String[] spDonvi;
	String[] spSoluong;
	String[] spGianhap;
	String[] spChietkhau;
	String[] spVAT;
	String[] spTungay;
	String[] spDenngay;
	String[] spTrongluong;
	String[] spThetich;
	String[] spQuyDoi;
	
	String[] dhCk_diengiai;
	String[] dhCk_giatri;
	String[] dhCk_loai;
	
	String nppId;
	String nppTen;
	String sitecode;
	
	String ngaytrungthau; 
	String chiphibaolanh;
	
	Utility util;
	dbutils db;
	
	public ErpThanhlyhopdongNpp()
	{
		this.id = "";
		this.ma = "";
		this.tungay = "";
		this.denngay = "";
		this.ghichu = "";
		this.khoNhanId = "";
		this.khId = "";
		this.khApdungId = "";
		this.msg = "";
		this.loaidonhang = "0";
		this.trangthai = "0";
		this.chietkhau = "0";
		this.vat = "10";
		this.dvkdId = "";
		this.kbhId = "";
		this.ddkdId = "";
		this.gsbhId = "";
		this.hopdongId = "";
		this.hopdongchung = "0";
		this.tdv_dangnhap_id = "";
		this.npp_duocchon_id = "";
		
		this.dhCk_diengiai = new String[]{"", "", "", ""};
		this.dhCk_giatri = new String[]{"0", "0", "0", "0"};
		this.dhCk_loai = new String[]{"0", "0", "0", "0"};
		
		this.ngaytrungthau = "";
		this.chiphibaolanh = "0";
		
		this.db = new dbutils();
		this.util = new Utility();
	}
	
	public ErpThanhlyhopdongNpp(String id)
	{
		this.id = id;
		this.tungay = "";
		this.denngay = "";
		this.ghichu = "";
		this.khoNhanId = "";
		this.khId = "";
		this.khApdungId = "";
		this.msg = "";
		this.loaidonhang = "0";
		this.trangthai = "0";
		this.chietkhau = "0";
		this.vat = "10";
		this.dvkdId = "";
		this.kbhId = "";
		this.ddkdId = "";
		this.gsbhId = "";
		this.hopdongId = "";
		this.hopdongchung = "0";
		this.tdv_dangnhap_id = "";
		this.npp_duocchon_id = "";

		this.dhCk_diengiai = new String[]{"", "", "", ""};
		this.dhCk_giatri = new String[]{"0", "0", "0", "0"};
		this.dhCk_loai = new String[]{"0", "0", "0", "0"};
		
		this.ngaytrungthau = "";
		this.chiphibaolanh = "0";
		
		this.db = new dbutils();
		this.util = new Utility();
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

	public void setId(String Id) 
	{
		this.id = Id;
	}

	public String getKhoNhapId()
	{
		return this.khoNhanId;
	}

	public void setKhoNhapId(String khonhaptt) 
	{
		this.khoNhanId = khonhaptt;
	}

	public ResultSet getKhoNhapRs() 
	{
		return this.khoNhanRs;
	}

	public void setKhoNHapRs(ResultSet khonhapRs) 
	{
		this.khoNhanRs = khonhapRs;
	}
	
	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	
	public boolean createNK() 
	{
		System.out.println("ma trong bean "+this.ma);
		if(this.ma.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập mã hợp đồng";
			return false;
		}
		if(loaithanhly == 0)
			if(this.ngaytl.trim().length() < 10)
			{
				this.msg = "Vui lòng nhập ngày thanh lý hợp đồng";
				return false;
			}
		if(loaithanhly == 0)
			if(this.ngaytlkt.trim().length() < 10)
			{
				this.msg = "Vui lòng nhập ngày kết thúc thanh lý hợp đồng";
				return false;
			}
			
	
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			
			
			String query = 
				" INSERT INTO [ERP_THANHLYHOPDONG]([HOPDONGNPP_FK],[NGAYTHANHLY],[NGAYKETTHUCTL],[LOAITL],[TRANGTHAI],[NGAYTAO],[NGUOITAO],[NGAYSUA],[NGUOISUA],[GHICHU]) " +
														" values('" + this.ma + "','"+this.ngaytl+"','"+this.ngaytlkt+"','"+this.loaithanhly+"','0','" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "','" + this.ghichu + "' )";
			System.out.println("1.Insert THANHLYHOPDONG: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới THANHLYHOPDONG " + query;
				db.getConnection().rollback();
				return false;
			}
			query = "select SCOPE_IDENTITY() as id";
			ResultSet rs = db.get(query);
			rs.next();
			String pk_seq = rs.getString("id");
			if(mahoadon != null)
			{
				for(int i = 0 ; i < mahoadon.length; i++)
				{
					if(mahoadon[i].length() > 0 )
					{
						query = "INSERT INTO [ERP_THANHLYHOPDONG_CHITIET]([THANHLYHOPDONG_FK],[HOADONNPP_FK],HOPDONGNPP_FK)"
								+ "values ('"+pk_seq+"','"+mahoadon[i]+"','" + this.ma + "') ";
						System.out.println("1.Insert ERP_THANHLYHOPDONG_CHITIET: " + query);
						if(!db.update(query))
						{
							this.msg = "Không thể tạo mới ERP_THANHLYHOPDONG_CHITIET " + query;
							db.getConnection().rollback();
							return false;
						}
					}		
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			db.update("rollback");
			e.printStackTrace();
			this.msg = "Exception: " + e.getMessage();
			return false;
		}
		
		return true;
	}

	public boolean updateNK(String checkKM) 
	{
		if(this.ma.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập mã hợp đồng";
			return false;
		}
		
		if(loaithanhly == 0)
		if(this.ngaytl.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày thanh lý hợp đồng";
			return false;
		}
		if(loaithanhly == 0)
		if(this.ngaytlkt.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày kết thúc thanh lý hợp đồng";
			return false;
		}
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query =  "Update ERP_THANHLYHOPDONG set hopdongnpp_fk = '"+this.ma+"', set ngaythanhly = '"+this.ngaytl+"', ngayketthuctl = '"+this.ngaytlkt +"', Loaithanhly = '"+this.loaithanhly+"',trangthai = '0', ngaysua = '"+getDateTime()+"', nguoisua = '"+this.userId+"', ghichu = '"+this.ghichu+"'"
					+ "where pk_seq = '"+this.id+"'";
					
				System.out.println("1.Update ERP_THANHLYHOPDONG: " + query);
				if(!db.update(query))
				{
					this.msg = "Không thể cập nhật THANHLYHOPDONG " + query;
					db.getConnection().rollback();
					return false;
				}
				
				query = "delete ERP_THANHLYHOPDONG_CHITIET where THANHLYHOPDONG_FK = '"+this.id+"'";
				if(!db.update(query))
				{
					this.msg = "Không thể cập nhật THANHLYHOPDONG " + query;
					db.getConnection().rollback();
					return false;
				}
				if(mahoadon != null)
				{
					for(int i = 0 ; i < mahoadon.length; i++)
					{
						if(mahoadon[i].length() > 0 )
						{
							query = "INSERT INTO [ERP_THANHLYHOPDONG_CHITIET]([THANHLYHOPDONG_FK],[HOADONNPP_FK],HOPDONGNPP_FK)"
									+ "values ('"+this.id+"','"+mahoadon[i]+"','" + this.ma + "') ";
							System.out.println("1.Insert ERP_THANHLYHOPDONG_CHITIET: " + query);
							if(!db.update(query))
							{
								this.msg = "Không thể tạo mới ERP_THANHLYHOPDONG_CHITIET " + query;
								db.getConnection().rollback();
								return false;
							}
						}		
					}
				}
		
		
			
			
		
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			db.update("rollback");
			this.msg = "Exception: " + e.getMessage();
			return false;
		}
		
		return true;
	}


	public void createRs() 
	{
		this.getNppInfo();
				
		this.khoNhanRs = db.get("select PK_SEQ, TEN from KHO where trangthai = '1' and pk_seq in " + this.util.quyen_kho(this.userId) );
		
		this.dvtRs = db.getScrol("select PK_SEQ, DONVI from DONVIDOLUONG where trangthai = '1' ");
		this.dvkdRs = db.get("select PK_SEQ, DONVIKINHDOANH as TEN from DONVIKINHDOANH where TRANGTHAI = '1'");
		this.kbhRs = db.get("select PK_SEQ, DIENGIAI as TEN from KENHBANHANG where TRANGTHAI = '1' and pk_seq in ( select kbh_fk from nhomkenh_kenhbanhang where nk_fk = '100000' ) ");
		
		this.gsbhRs = db.get("select PK_SEQ, TEN from GIAMSATBANHANG where trangthai = '1' ");
		
		//String query = "select pk_seq, TEN from DAIDIENKINHDOANH where tructhuoc_fk = '" + this.nppId + "' ";
		String query = "select pk_seq, TEN from DAIDIENKINHDOANH where pk_seq in ( select ddkd_fk from DAIDIENKINHDOANH_NPP where npp_fk = '" + this.nppId + "' ) ";
		//if(this.gsbhId.trim().length() > 0)
			//query += " and pk_seq in ( select ddkd_fk from DDKD_GSBH where GSBH_FK = '" + this.gsbhId + "' ) ";
		this.ddkdRs = db.get(query);
		
		//DOI LAI TRONG NHOM KENH
		if(this.trangthai.equals("0") || !this.loaidonhang.equals("3") )
		{
			query = "select PK_SEQ, MAFAST + ', ' + TEN as TEN from KHACHHANG where TRANGTHAI = '1'  AND  PK_SEQ in ( select khachhang_fk from KHACHHANG_KENHBANHANG where kbh_fk in ( select kbh_fk from NHOMKENH_KENHBANHANG where nk_fk = '100000' ) )  AND NPP_FK = '" + this.nppId + "' ";
			
			System.out.println("khachhangRs "+query);
			this.khRs = db.get(query);
			//this.khApdungRs = db.get(query);
		}
		else
		{
//			if(this.khId.trim().length() > 0)
//			{
//				query = "select PK_SEQ, MAFAST + ', ' + TEN as TEN " +
//						"from KHACHHANG where TRANGTHAI = '1'  AND  PK_SEQ in ( select khachhang_fk from KHACHHANG_KENHBANHANG where kbh_fk in ( select kbh_fk from NHOMKENH_KENHBANHANG where nk_fk = '100000' ) ) AND PK_SEQ = '" + this.khId + "' AND NPP_FK = '" + this.nppId + "' ";
//				System.out.println("khachhangRs "+query);
//				this.khRs = db.get(query);
//			}
		}
		
		System.out.println("---NPP ID: " + this.nppId);
		if(this.nppId.trim().length() > 0)
		{
			query = "select GSBH_FK from NHAPP_GIAMSATBH  " +
				    "where NPP_FK = '" + this.nppId + "' ";
			ResultSet rsInfo = db.get(query);
			if(rsInfo != null)
			{
				try 
				{
					if(rsInfo.next())
					{
						this.gsbhId = rsInfo.getString("gsbh_fk");
					}
					rsInfo.close();
				} 
				catch (Exception e) {}
			}
		}
		
		if(this.khId.trim().length() > 0 && this.ddkdId.trim().length() <= 0 )
		{
			query = "select b.ddkd_fk from KHACHHANG_TUYENBH a inner join TUYENBANHANG b on a.TBH_FK = b.pk_seq where a.khachhang_fk = '" + this.khId + "'";
			System.out.println("--LAY DDKD: " + query );
			ResultSet rsDDKD = db.get(query);
			if(rsDDKD != null)
			{
				try 
				{
					if(rsDDKD.next())
					{
						this.ddkdId = rsDDKD.getString("ddkd_fk");
					}
					rsDDKD.close();
				}
				catch (Exception e) { }
			}
		}
		
		if(this.loaidonhang.equals("1") && this.khId.trim().length() > 0 )
		{
			query = "select PK_SEQ, cast(pk_seq as varchar(10)) + ' [' + mahopdong + ']' as diengiai " +
					"from ERP_HOPDONGNPP where khachhang_fk = '" + this.khId + "' order by pk_seq desc ";
			
			System.out.println("ohuj"+ query);
			this.hopdongRs = db.get(query);
			
			if(this.spMa == null && this.hopdongId.trim().length() > 0 )
			{
				//INIT SAN PHAM LUC DAU SE GIONG SP TRONG HOP DONG CU, NHUNG DUOC PHEP SUA LAI SO LUONG
				
				String loaihopdongPL = "";
				if(this.loaidonhang.equals("1") && this.hopdongId.trim().length() > 0 )
				{
					query = "select loaidonhang from ERP_HOPDONGNPP where pk_seq = '" + this.hopdongId + "'";
					ResultSet rsLOAI = db.get(query);
					if(rsLOAI != null)
					{
						try 
						{
							if(rsLOAI.next())
							{
								loaihopdongPL = rsLOAI.getString("loaidonhang");
							}
							rsLOAI.close();
						} 
						catch (Exception e) {}
					}
				}
				
				if( !(this.loaidonhang.equals("1") && loaihopdongPL.equals("2")) )
					initSANPHAM_THEOHD();
			}
			
		}
		String cmd = "select pk_seq as idhd, mahopdong as mahd from erp_hopdongNPP where 1 = 1 and pk_seq not in (select ERP_HopDongNPP_FK from thanhlyhopdong) ";
//		if(khId.length() > 0)
//			cmd += " and khachhang_fk = '"+this.khId+"' "; 
		if(loaidonhang.length() > 0 )
			cmd += " and loaidonhang = '"+this.loaidonhang+"' ";
		System.out.println("Ma Hop dong "+cmd);
		this.mahdrs = db.get(cmd);
	}
	private void initSANPHAM_update() 
	{
		
		String query = "";
		if(loaithanhly == 1)
		{
		query =		"select a.HOADON_FK as mahd, b.MA, b.TEN, DV.donvi, isnull(a.soluong, 0) as soluong, a.dongia, isnull(a.chietkhau, 0) "+ 
				"as chietkhau, ISNULL(b.trongluong, 0) "+ 
				"as trongluong, ISNULL(b.thetich, 0) as thetich, a.VAT, "+     	
				"(select soluong1/ soluong2 from QUYCACH where sanpham_fk=a.sanpham_fk and DVDL1_FK=b.DVDL_FK and DVDL2_FK=100018) "+  
				 "as spQuyDoi "+ 
				"from ERP_HOADONNPP_SP a inner Join SanPham b on a.SANPHAM_FK = b.PK_SEQ "+     
				"INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = b.DVDL_FK "+
				 "inner join  ERP_HOADONNPP_DDH c  "+
				 "on a.HOADON_FK = c.HOADONNPP_FK inner join ERP_DONDATHANGNPP  d on d.PK_SEQ = c.DDH_FK inner join  ERP_HOADONNPP  k on a.HOADON_FK = k.pk_seq "+	   
									"where k.trangthai not in(1,3,5) and d.HOPDONG_FK = ( select HOPDONGNPP_FK from erp_thanhlyhopdong where pk_seq = '"+this.id+"')  and d.loaidonhang = 1";
		}
		else if(ngaytl.length() > 0 && ngaytlkt.length() > 0)
		{
		query =	"select a.HOADON_FK as mahd, b.MA, b.TEN, DV.donvi, isnull(a.soluong, 0) as soluong, a.dongia, isnull(a.chietkhau, 0) "+ 
					"as chietkhau, ISNULL(b.trongluong, 0) "+ 
					"as trongluong, ISNULL(b.thetich, 0) as thetich, a.VAT, "+     	
					"(select soluong1/ soluong2 from QUYCACH where sanpham_fk=a.sanpham_fk and DVDL1_FK=b.DVDL_FK and DVDL2_FK=100018) "+  
					 "as spQuyDoi "+ 
					"from ERP_HOADONNPP_SP a inner Join SanPham b on a.SANPHAM_FK = b.PK_SEQ "+     
					"INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = b.DVDL_FK "+
					 "inner join  ERP_HOADONNPP_DDH c "+
					 "on a.HOADON_FK = c.HOADONNPP_FK inner join ERP_DONDATHANGNPP  d on d.PK_SEQ = c.DDH_FK   inner join ERP_HOADONNPP e on e.pk_seq = a.hoadon_fk "+	   
					"where d.HOPDONG_FK = ( select HOPDONGNPP_FK from erp_thanhlyhopdong where pk_seq = '"+this.id+"') and e.trangthai not in(1,3,5) and e.NGAYXUATHD > '"+ngaytl+"' and e.NGAYXUATHD < '"+ngaytlkt+"' and d.loaidonhang = 1";
		
		}
		ResultSet spRs = null;
		if(query.length() > 0)
			spRs = db.get(query);
		
		System.out.println("---INIT SP: " + query);
		
		
		NumberFormat formater = new DecimalFormat("##,###,####");
		if(spRs != null)
		{
			try 
			{
				String 	mahoadon = "";
				String spMA = "";
				String spTEN = "";
				String spDONVI = "";
				String spSOLUONG = "";
				String spGIANHAP = "";
				String spCHIETKHAU = "";
				String spVAT = "";
				String spTUNGAY = "";
				String spDENNGAY = "";
				
				String spTRONGLUONG = "";
				String spTHETICH = "";
				
				String spQuyDoi ="";
				
				while(spRs.next())
				{
					mahoadon += spRs.getString("mahd")+"__";
					spMA += spRs.getString("MA") + "__";
					spTEN += spRs.getString("TEN") + "__";
					spDONVI += spRs.getString("DONVI") + "__";
					spSOLUONG += formater.format(spRs.getDouble("SOLUONG")) + "__";
					spGIANHAP += spRs.getDouble("DONGIA") + "__";
					spCHIETKHAU += formater.format(spRs.getDouble("chietkhau")) + "__";
					spVAT += formater.format(spRs.getDouble("VAT")) + "__";
					
					spTRONGLUONG += spRs.getString("trongluong") + "__";
					spTHETICH += spRs.getString("thetich") + "__";
					spQuyDoi +=spRs.getString("spQuyDoi") + "__";
				}
				spRs.close();
				
				if(spMA.trim().length() > 0)
				{
				
					mahoadon = mahoadon.substring(0, mahoadon.length() - 2);
					this.mahoadon = mahoadon.split("__");
					spMA = spMA.substring(0, spMA.length() - 2);
					this.spMa = spMA.split("__");
					
					spTEN = spTEN.substring(0, spTEN.length() - 2);
					this.spTen = spTEN.split("__");
					
					spDONVI = spDONVI.substring(0, spDONVI.length() - 2);
					this.spDonvi = spDONVI.split("__");
					
					spSOLUONG = spSOLUONG.substring(0, spSOLUONG.length() - 2);
					this.spSoluong = spSOLUONG.split("__");
					
					spGIANHAP = spGIANHAP.substring(0, spGIANHAP.length() - 2);
					this.spGianhap = spGIANHAP.split("__");
					
					spCHIETKHAU = spCHIETKHAU.substring(0, spCHIETKHAU.length() - 2);
					this.spChietkhau = spCHIETKHAU.split("__");
					
					spVAT = spVAT.substring(0, spVAT.length() - 2);
					this.spVAT = spVAT.split("__");
					
//					spTUNGAY = spTUNGAY.substring(0, spTUNGAY.length() - 2);
//					this.spTungay = spTUNGAY.split("__");
//					
//					spDENNGAY = spDENNGAY.substring(0, spDENNGAY.length() - 2);
//					this.spDenngay = spDENNGAY.split("__");
//					
					spTRONGLUONG = spTRONGLUONG.substring(0, spTRONGLUONG.length() - 2);
					this.spTrongluong = spTRONGLUONG.split("__");
					
					spTHETICH = spTHETICH.substring(0, spTHETICH.length() - 2);
					this.spThetich = spTHETICH.split("__");
					
					spQuyDoi = spQuyDoi.substring(0, spQuyDoi.length() - 2);
					this.spQuyDoi = spQuyDoi.split("__");
					
					
				}
				
				//INIT CHIET KHAU
				query = "select DIENGIAI, GIATRI, LOAI from ERP_HOPDONGNPP_CHIETKHAU where HOPDONG_FK = '" + this.id + "'";
				System.out.println("[INIT_CK]"+query);
				ResultSet rsCK = db.get(query);
				if(rsCK != null)
				{
					String dkCK_diengiai = "";
					String dkCK_giatri = "";
					String dkCK_loai = "";
					
					while(rsCK.next())
					{
						dkCK_diengiai += rsCK.getString("DIENGIAI") + "__";
						dkCK_giatri += formater.format(rsCK.getDouble("GIATRI")) + "__";
						dkCK_loai += rsCK.getString("LOAI") + "__";
					}
					rsCK.close();
					
					if(dkCK_diengiai.trim().length() > 0)
					{
						dkCK_diengiai = dkCK_diengiai.substring(0, dkCK_diengiai.length() - 2);
						this.dhCk_diengiai = dkCK_diengiai.split("__");
						
						dkCK_giatri = dkCK_giatri.substring(0, dkCK_giatri.length() - 2);
						this.dhCk_giatri = dkCK_giatri.split("__");
						
						dkCK_loai = dkCK_loai.substring(0, dkCK_loai.length() - 2);
						this.dhCk_loai = dkCK_loai.split("__");
					}
					
				}
			} 
			catch (Exception e) 
			{
				System.out.println("115.Exception: " + e.getMessage());
				e.printStackTrace();
				
			}
		}
		
	}
	private void initSANPHAM() 
	{
		
		String query = "";
		if(loaithanhly == 1)
		{
		query =		"select a.HOADON_FK as mahd, b.MA, b.TEN, DV.donvi, isnull(a.soluong, 0) as soluong, a.dongia, isnull(a.chietkhau, 0) "+ 
				"as chietkhau, ISNULL(b.trongluong, 0) "+ 
				"as trongluong, ISNULL(b.thetich, 0) as thetich, a.VAT, "+     	
				"(select soluong1/ soluong2 from QUYCACH where sanpham_fk=a.sanpham_fk and DVDL1_FK=b.DVDL_FK and DVDL2_FK=100018) "+  
				 "as spQuyDoi "+ 
				"from ERP_HOADONNPP_SP a inner Join SanPham b on a.SANPHAM_FK = b.PK_SEQ "+     
				"INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = b.DVDL_FK "+
				 "inner join  ERP_HOADONNPP_DDH c  "+
				 "on a.HOADON_FK = c.HOADONNPP_FK inner join ERP_DONDATHANGNPP  d on d.PK_SEQ = c.DDH_FK inner join  ERP_HOADONNPP  k on a.HOADON_FK = k.pk_seq "+	   
									"where k.trangthai not in(1,3,5) and d.HOPDONG_FK ="+this.id +" and d.loaidonhang = 1";
		}
		else if(ngaytl.length() > 0 && ngaytlkt.length() > 0)
		{
		query =	"select a.HOADON_FK as mahd, b.MA, b.TEN, DV.donvi, isnull(a.soluong, 0) as soluong, a.dongia, isnull(a.chietkhau, 0) "+ 
					"as chietkhau, ISNULL(b.trongluong, 0) "+ 
					"as trongluong, ISNULL(b.thetich, 0) as thetich, a.VAT, "+     	
					"(select soluong1/ soluong2 from QUYCACH where sanpham_fk=a.sanpham_fk and DVDL1_FK=b.DVDL_FK and DVDL2_FK=100018) "+  
					 "as spQuyDoi "+ 
					"from ERP_HOADONNPP_SP a inner Join SanPham b on a.SANPHAM_FK = b.PK_SEQ "+     
					"INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = b.DVDL_FK "+
					 "inner join  ERP_HOADONNPP_DDH c "+
					 "on a.HOADON_FK = c.HOADONNPP_FK inner join ERP_DONDATHANGNPP  d on d.PK_SEQ = c.DDH_FK   inner join ERP_HOADONNPP e on e.pk_seq = a.hoadon_fk "+	   
					"where d.HOPDONG_FK ="+this.id +" and e.trangthai not in(1,3,5) and e.NGAYXUATHD > '"+ngaytl+"' and e.NGAYXUATHD < '"+ngaytlkt+"' and d.loaidonhang = 1";
		
		}
		ResultSet spRs = null;
		if(query.length() > 0)
			spRs = db.get(query);
		
		System.out.println("---INIT SP: " + query);
		
		
		NumberFormat formater = new DecimalFormat("##,###,####");
		if(spRs != null)
		{
			try 
			{
				String 	mahoadon = "";
				String spMA = "";
				String spTEN = "";
				String spDONVI = "";
				String spSOLUONG = "";
				String spGIANHAP = "";
				String spCHIETKHAU = "";
				String spVAT = "";
				String spTUNGAY = "";
				String spDENNGAY = "";
				
				String spTRONGLUONG = "";
				String spTHETICH = "";
				
				String spQuyDoi ="";
				
				while(spRs.next())
				{
					mahoadon += spRs.getString("mahd")+"__";
					spMA += spRs.getString("MA") + "__";
					spTEN += spRs.getString("TEN") + "__";
					spDONVI += spRs.getString("DONVI") + "__";
					spSOLUONG += formater.format(spRs.getDouble("SOLUONG")) + "__";
					spGIANHAP += spRs.getDouble("DONGIA") + "__";
					spCHIETKHAU += formater.format(spRs.getDouble("chietkhau")) + "__";
					spVAT += formater.format(spRs.getDouble("VAT")) + "__";
					
					spTRONGLUONG += spRs.getString("trongluong") + "__";
					spTHETICH += spRs.getString("thetich") + "__";
					spQuyDoi +=spRs.getString("spQuyDoi") + "__";
				}
				spRs.close();
				
				if(spMA.trim().length() > 0)
				{
				
					mahoadon = mahoadon.substring(0, mahoadon.length() - 2);
					this.mahoadon = mahoadon.split("__");
					spMA = spMA.substring(0, spMA.length() - 2);
					this.spMa = spMA.split("__");
					
					spTEN = spTEN.substring(0, spTEN.length() - 2);
					this.spTen = spTEN.split("__");
					
					spDONVI = spDONVI.substring(0, spDONVI.length() - 2);
					this.spDonvi = spDONVI.split("__");
					
					spSOLUONG = spSOLUONG.substring(0, spSOLUONG.length() - 2);
					this.spSoluong = spSOLUONG.split("__");
					
					spGIANHAP = spGIANHAP.substring(0, spGIANHAP.length() - 2);
					this.spGianhap = spGIANHAP.split("__");
					
					spCHIETKHAU = spCHIETKHAU.substring(0, spCHIETKHAU.length() - 2);
					this.spChietkhau = spCHIETKHAU.split("__");
					
					spVAT = spVAT.substring(0, spVAT.length() - 2);
					this.spVAT = spVAT.split("__");
					
//					spTUNGAY = spTUNGAY.substring(0, spTUNGAY.length() - 2);
//					this.spTungay = spTUNGAY.split("__");
//					
//					spDENNGAY = spDENNGAY.substring(0, spDENNGAY.length() - 2);
//					this.spDenngay = spDENNGAY.split("__");
//					
					spTRONGLUONG = spTRONGLUONG.substring(0, spTRONGLUONG.length() - 2);
					this.spTrongluong = spTRONGLUONG.split("__");
					
					spTHETICH = spTHETICH.substring(0, spTHETICH.length() - 2);
					this.spThetich = spTHETICH.split("__");
					
					spQuyDoi = spQuyDoi.substring(0, spQuyDoi.length() - 2);
					this.spQuyDoi = spQuyDoi.split("__");
					
					
				}
				
				//INIT CHIET KHAU
				query = "select DIENGIAI, GIATRI, LOAI from ERP_HOPDONGNPP_CHIETKHAU where HOPDONG_FK = '" + this.id + "'";
				System.out.println("[INIT_CK]"+query);
				ResultSet rsCK = db.get(query);
				if(rsCK != null)
				{
					String dkCK_diengiai = "";
					String dkCK_giatri = "";
					String dkCK_loai = "";
					
					while(rsCK.next())
					{
						dkCK_diengiai += rsCK.getString("DIENGIAI") + "__";
						dkCK_giatri += formater.format(rsCK.getDouble("GIATRI")) + "__";
						dkCK_loai += rsCK.getString("LOAI") + "__";
					}
					rsCK.close();
					
					if(dkCK_diengiai.trim().length() > 0)
					{
						dkCK_diengiai = dkCK_diengiai.substring(0, dkCK_diengiai.length() - 2);
						this.dhCk_diengiai = dkCK_diengiai.split("__");
						
						dkCK_giatri = dkCK_giatri.substring(0, dkCK_giatri.length() - 2);
						this.dhCk_giatri = dkCK_giatri.split("__");
						
						dkCK_loai = dkCK_loai.substring(0, dkCK_loai.length() - 2);
						this.dhCk_loai = dkCK_loai.split("__");
					}
					
				}
			} 
			catch (Exception e) 
			{
				System.out.println("115.Exception: " + e.getMessage());
				e.printStackTrace();
				
			}
		}
		
	}
	
	private void initSANPHAM_THEOHD() 
	{
		String query =  
					"select b.MA, b.TEN, DV.donvi, isnull(a.soluong, 0) as soluong, a.dongia, isnull(a.chietkhau, 0) as chietkhau, ISNULL(b.trongluong, 0) as trongluong, ISNULL(b.thetich, 0) as thetich, a.tungay, a.denngay, a.thueVAT    " +	
					"	,(select soluong1/ soluong2 from QUYCACH where sanpham_fk=a.sanpham_fk and DVDL1_FK=b.DVDL_FK and DVDL2_FK=100018)   as spQuyDoi "+
					" from ERP_HopDongNPP_SANPHAM a inner Join SanPham b on a.SANPHAM_FK = b.PK_SEQ    " +
					" 		INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = a.DVDL_FK       " +
					"where a.HOPDONG_FK = '" + this.hopdongId + "' ";
		
		System.out.println("---INIT SP: " + query);
		ResultSet spRs = db.get(query);
		
		NumberFormat formater = new DecimalFormat("##,###,###");
		NumberFormat formater_2 = new DecimalFormat("##,###,###.####");
		if(spRs != null)
		{
			try 
			{
				String spMA = "";
				String spTEN = "";
				String spDONVI = "";
				String spSOLUONG = "";
				String spGIANHAP = "";
				String spCHIETKHAU = "";
				String spVAT = "";
				String spTUNGAY = "";
				String spDENNGAY = "";
				
				String spTRONGLUONG = "";
				String spTHETICH = "";
				
				String spQuyDoi ="";
				
				while(spRs.next())
				{
					spMA += spRs.getString("MA") + "__";
					spTEN += spRs.getString("TEN") + "__";
					spDONVI += spRs.getString("DONVI") + "__";
					spSOLUONG += formater.format(spRs.getDouble("SOLUONG")) + "__";
					spGIANHAP += formater_2.format(spRs.getDouble("DONGIA")) + "__";
					spCHIETKHAU += formater.format(spRs.getDouble("chietkhau")) + "__";
					spVAT += formater.format(spRs.getDouble("thueVAT")) + "__";
					
					if(spRs.getString("tungay").trim().length() > 0)
						spTUNGAY += spRs.getString("tungay") + "__";
					else
						spTUNGAY += " __";
					
					if(spRs.getString("denngay").trim().length() > 0)
						spDENNGAY += spRs.getString("denngay") + "__";
					else
						spDENNGAY += " __";
					
					spTRONGLUONG += spRs.getString("trongluong") + "__";
					spTHETICH += spRs.getString("thetich") + "__";
					spQuyDoi +=spRs.getString("spQuyDoi") + "__";
				}
				spRs.close();
				
				if(spMA.trim().length() > 0)
				{
					spMA = spMA.substring(0, spMA.length() - 2);
					this.spMa = spMA.split("__");
					
					spTEN = spTEN.substring(0, spTEN.length() - 2);
					this.spTen = spTEN.split("__");
					
					spDONVI = spDONVI.substring(0, spDONVI.length() - 2);
					this.spDonvi = spDONVI.split("__");
					
					spSOLUONG = spSOLUONG.substring(0, spSOLUONG.length() - 2);
					this.spSoluong = spSOLUONG.split("__");
					
					spGIANHAP = spGIANHAP.substring(0, spGIANHAP.length() - 2);
					this.spGianhap = spGIANHAP.split("__");
					
					spCHIETKHAU = spCHIETKHAU.substring(0, spCHIETKHAU.length() - 2);
					this.spChietkhau = spCHIETKHAU.split("__");
					
					spVAT = spVAT.substring(0, spVAT.length() - 2);
					this.spVAT = spVAT.split("__");
					
					spTUNGAY = spTUNGAY.substring(0, spTUNGAY.length() - 2);
					this.spTungay = spTUNGAY.split("__");
					
					spDENNGAY = spDENNGAY.substring(0, spDENNGAY.length() - 2);
					this.spDenngay = spDENNGAY.split("__");
					
					spTRONGLUONG = spTRONGLUONG.substring(0, spTRONGLUONG.length() - 2);
					this.spTrongluong = spTRONGLUONG.split("__");
					
					spTHETICH = spTHETICH.substring(0, spTHETICH.length() - 2);
					this.spThetich = spTHETICH.split("__");
					
					spQuyDoi = spQuyDoi.substring(0, spQuyDoi.length() - 2);
					this.spQuyDoi = spQuyDoi.split("__");
				}
				
			} 
			catch (Exception e) 
			{
				System.out.println("115.Exception: " + e.getMessage());
			}
		}
		
	}
	public void initnew() 
	{
		String query = "select a.pk_seq as idhd, a.mahopdong, a.trangthai, a.tungay, a.denngay, ISNULL(b.ghichu, '') as ghichu, a.khachhang_fk , isnull(a.chietkhau, 0) as chietkhau, a.vat, a.ngaytrungthau,a.loaidonhang, a.chiphibaolanh, " +
						"b.loaitl,isnull(b.ngaythanhly,'') as ngaythanhly,isnull(b.ngayketthuctl,'') as ngayketthuctl from ERP_HopDongNPP a left join ERP_THANHLYHOPDONG b on b.HOPDONGNPP_FK = a.pk_seq where a.pk_seq = '" + this.id + "'";
		System.out.println("____INIT NHAP KHO: " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					this.ma = rs.getString("idhd");
					this.tungay = rs.getString("tungay");
					this.denngay = rs.getString("denngay");
					this.ghichu = rs.getString("ghichu");
					this.khId = rs.getString("khachhang_fk") == null ? "" : rs.getString("khachhang_fk");
					this.loaidonhang = rs.getString("loaidonhang");
					this.chietkhau = rs.getString("chietkhau");
					this.vat = rs.getString("vat");
					this.trangthai = rs.getString("trangthai");
					this.ngaytrungthau = rs.getString("ngaytrungthau");
					this.chiphibaolanh = rs.getString("chiphibaolanh");
				}
				rs.close();
				
				//
				query = "select khachhang_fk from ERP_HOPDONGNPP_APDUNG where hopdong_fk = '" + this.id + "'";
				System.out.println("=================== AP DUNG: " + query );
				rs = db.get(query);
				String khApdungId = "";
				while(rs.next())
				{
					khApdungId += rs.getString("khachhang_fk") + ",";
				}
				rs.close();
				
				if(khApdungId.trim().length() > 0)
				{
					khApdungId = khApdungId.substring(0, khApdungId.length() - 1);
					this.khApdungId = khApdungId;
				}
				System.out.println("Khachhangid "+khId);
			} 
			catch (Exception e) 
			{
				System.out.println("---LOI INIT: " + e.getMessage());
			}
		}

		this.initSANPHAM();
		
		this.createRs();
		
	}
	public void init() 
	{
		String query = "select a.pk_seq as idhd, a.mahopdong, a.trangthai, a.tungay, a.denngay, ISNULL(b.ghichu, '') as ghichu, a.khachhang_fk , isnull(a.chietkhau, 0) as chietkhau, a.vat, a.ngaytrungthau,a.loaidonhang, a.chiphibaolanh, " +
						"b.loaitl,isnull(b.ngaythanhly,'') as ngaythanhly,isnull(b.ngayketthuctl,'') as ngayketthuctl from ERP_HopDongNPP a left join ERP_THANHLYHOPDONG b on b.HOPDONGNPP_FK = a.pk_seq where b.pk_seq = '" + this.id + "'";
		System.out.println("____INIT NHAP KHO: " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					this.ma = rs.getString("idhd");
					this.tungay = rs.getString("tungay");
					this.denngay = rs.getString("denngay");
					this.ghichu = rs.getString("ghichu");
					this.khId = rs.getString("khachhang_fk") == null ? "" : rs.getString("khachhang_fk");
					this.loaidonhang = rs.getString("loaidonhang");
					this.ngaytl = rs.getString("ngaythanhly");
					this.ngaytlkt = rs.getString("ngayketthuctl");
					this.chietkhau = rs.getString("chietkhau");
					this.vat = rs.getString("vat");
					this.trangthai = rs.getString("trangthai");
					this.loaithanhly = rs.getInt("loaitl");
					this.ngaytrungthau = rs.getString("ngaytrungthau");
					this.chiphibaolanh = rs.getString("chiphibaolanh");

				}
				rs.close();
				
				//
				query = "select khachhang_fk from ERP_HOPDONGNPP_APDUNG where hopdong_fk = '" + this.id + "'";
				System.out.println("=================== AP DUNG: " + query );
				rs = db.get(query);
				String khApdungId = "";
				while(rs.next())
				{
					khApdungId += rs.getString("khachhang_fk") + ",";
				}
				rs.close();
				
				if(khApdungId.trim().length() > 0)
				{
					khApdungId = khApdungId.substring(0, khApdungId.length() - 1);
					this.khApdungId = khApdungId;
				}
				
			} 
			catch (Exception e) 
			{
				System.out.println("---LOI INIT: " + e.getMessage());
			}
		}

		initSANPHAM_update();
		
		this.createRs();
		
	}

	public void DBclose() {
		
		try{
			
			if(khoNhanRs!=null){
				khoNhanRs.close();
			}
			
			this.db.shutDown();
			
		}catch(Exception er){
			
		}
	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}

	public String getGhichu() {
		
		return this.ghichu;
	}

	public void setGhichu(String ghichu) {
		
		this.ghichu = ghichu;
	}

	
	public String[] getSpId() {
		
		return this.spId;
	}

	
	public void setSpId(String[] spId) {
		
		this.spId = spId;
	}

	
	public String[] getSpMa() {
		
		return this.spMa;
	}

	
	public void setSpMa(String[] spMa) {
		
		this.spMa = spMa;
	}

	
	public String[] getSpTen() {
		
		return this.spTen;
	}

	
	public void setSpTen(String[] spTen) {
		
		this.spTen = spTen;
	}

	
	public String[] getSpDonvi() {
		
		return this.spDonvi;
	}

	
	public void setSpDonvi(String[] spDonvi) {
		
		this.spDonvi = spDonvi;
	}

	
	public String[] getSpSoluong() {
		
		return this.spSoluong;
	}

	
	public void setSpSoluong(String[] spSoluong) {
		
		this.spSoluong = spSoluong;
	}

	
	public String[] getSpGianhap() {
		
		return this.spGianhap;
	}

	
	public void setSpGianhap(String[] spGianhap) {
		
		this.spGianhap = spGianhap;
	}
	
	public String getLoaidonhang() {
		
		return this.loaidonhang;
	}

	
	public void setLoaidonhang(String loaidonhang) {
		
		this.loaidonhang = loaidonhang;
	}

	
	public String getChietkhau() {
		
		return this.chietkhau;
	}

	
	public void setChietkhau(String chietkhau) {
		
		this.chietkhau = chietkhau;
	}

	
	public String getVat() {
		
		System.out.println("---VAT LA: " + this.vat);
		return this.vat;
	}

	
	public void setVat(String vat) {
		
		this.vat = vat;
	}

	
	public String getDvkdId() {
		
		return this.dvkdId;
	}

	
	public void setDvkdId(String dvkdId) {
		
		this.dvkdId = dvkdId;
	}

	
	public ResultSet getDvkdRs() {
		
		return this.dvkdRs;
	}

	
	public void setDvkdRs(ResultSet dvkdRs) {
		
		this.dvkdRs = dvkdRs;
	}

	
	public String getKbhId() {
		
		return this.kbhId;
	}

	
	public void setKbhId(String kbhId) {
		
		this.kbhId = kbhId;
	}

	
	public ResultSet getKbhRs() {
		
		return this.kbhRs;
	}

	
	public void setKbhRs(ResultSet kbhRs) {
		
		this.kbhRs = kbhRs;
	}

	public ResultSet getDvtRs() {

		return this.dvtRs;
	}


	public void setDvtRs(ResultSet dvtRs) {
		
		this.dvtRs = dvtRs;
	}

	public String[] getDhck_diengiai() {
		
		return this.dhCk_diengiai;
	}

	
	public void setDhck_Diengiai(String[] obj) {
		
		this.dhCk_diengiai = obj;
	}

	
	public String[] getDhck_giatri() {
		
		return this.dhCk_giatri;
	}

	
	public void setDhck_giatri(String[] obj) {
		
		this.dhCk_giatri = obj;
	}

	
	public String[] getDhck_loai() {
		
		return this.dhCk_loai;
	}

	
	public void setDhck_loai(String[] obj) {
		
		this.dhCk_loai = obj;
	}

	
	public String[] getSpTrongluong() {
		
		return this.spTrongluong;
	}

	
	public void setSpTrongluong(String[] spTrongluong) {
		
		this.spTrongluong = spTrongluong;
	}

	
	public String[] getSpThetich() {
		
		return this.spThetich;
	}

	
	public void setSpThetich(String[] spThetich) {
		
		this.spThetich = spThetich;
	}

	public String[] getSpQuyDoi()
	{
		return spQuyDoi;
	}
	
	public void setSpQuyDoi(String[] spQuyDoi)
	{
		this.spQuyDoi =spQuyDoi;
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

	
	public String[] getSpChietkhau() {
		
		return this.spChietkhau;
	}

	
	public void setSpChietkhau(String[] spChietkhau) {
		
		this.spChietkhau = spChietkhau;
	}

	
	public String[] getSpTungay() {
		
		return this.spTungay;
	}

	
	public void setSpTungay(String[] spTungay) {
		
		this.spTungay = spTungay;
	}

	
	public String[] getSpDenngay() {
		
		return this.spDenngay;
	}

	
	public void setSpDenngay(String[] spDenngay) {
		
		this.spDenngay = spDenngay;
	}

	
	public String getMahopdong() {
		
		return this.ma;
	}

	
	public void setMahopdong(String ma) {
		
		this.ma = ma;
	}

	
	public String getGsbhId() {
		
		return this.gsbhId;
	}

	
	public void setGsbhId(String gsbhId) {
		
		this.gsbhId = gsbhId;
	}

	
	public ResultSet getGsbhRs() {
		
		return this.gsbhRs;
	}

	
	public void setGsbhRs(ResultSet gsbhRs) {
		
		this.gsbhRs = gsbhRs;
	}

	
	public String getDdkdId() {
		
		return this.ddkdId;
	}

	
	public void setDdkdId(String ddkdId) {
		
		this.ddkdId = ddkdId;
	}

	
	public ResultSet getDdkdRs() {
		
		return this.ddkdRs;
	}

	
	public void setDddkdRs(ResultSet ddkdRs) {
		
		this.ddkdRs = ddkdRs;
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
		geso.traphaco.distributor.util.Utility util = new geso.traphaco.distributor.util.Utility();
		
		if(this.npp_duocchon_id.trim().length() <= 0)
		{
			this.nppId = util.getIdNhapp(this.userId);
			this.nppTen = util.getTenNhaPP();
			this.sitecode = util.getSitecode();
		}
		else
		{
			this.nppId = this.npp_duocchon_id;
			this.nppTen = "";
			this.sitecode = "";
		}
	}

	
	public String getKhId() {
		
		return this.khId;
	}

	
	public void setKhId(String khId) {
		
		this.khId = khId;
	}

	
	public ResultSet getKhRs() {
		
		return this.khRs;
	}

	
	public void setKhRs(ResultSet khRs) {
		
		this.khRs = khRs;
	}
	
	public String[] getSpVat() {
		
		return this.spVAT;
	}

	
	public void setSpVat(String[] spVat) {
		
		this.spVAT = spVat;
	}

	
	public String getHopdongId() {
		
		return this.hopdongId;
	}

	
	public void setHopdongId(String hopdongId) {
		
		this.hopdongId = hopdongId;
	}

	
	public ResultSet getHopdongRs() {
		
		return this.hopdongRs;
	}

	
	public void setHopdongRs(ResultSet hopdongRs) {
		
		this.hopdongRs = hopdongRs;
	}


	public String getHopdongchung() {

		return this.hopdongchung;
	}


	public void setHopdongchung(String hopdongchung) {
		
		this.hopdongchung = hopdongchung;
	}

	
	public String getKhApdungId() {
		
		return this.khApdungId;
	}

	
	public void setKhApdungId(String khApdungId) {
		
		this.khApdungId = khApdungId;
	}

	
	public ResultSet getKhApdungRs() {
		
		return this.khApdungRs;
	}

	
	public void setKhApdungRs(ResultSet khApdungRs) {
		
		this.khApdungRs = khApdungRs;
	}

	
	public boolean convertSO() 
	{
		if(this.khId.trim().length() <= 0)
		{
			this.msg = "Bạn phải chọn khách hàng ETC muốn chuyển SO";
			return false;
		}
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			String query = " select loaidonhang from ERP_HOPDONGNPP where pk_seq = '" + this.id + "' ";
			ResultSet rs = db.get(query);
			String loaidonhang = "";
			if(rs.next())
			{
				loaidonhang = rs.getString("loaidonhang");
				rs.close();
			}
			
			if(loaidonhang.equals("0")) //Hóa đơn bình thường, chỉ được phép đặt bằng số còn lại
			{
				query = "select count(*) as soDONG  " +
						"from " +
						"( " +
						"	select sanpham_fk, dvdl_fk, sum(soluong) as soluong, avg(dongia) as dongia, avg(chietkhau) as chietkhau, avg(thuevat) as thuevat, tungay, denngay " +
						"	from " +
						"	( " +
						"		select sanpham_fk,  " +
						"			case when a.dvdl_fk IS null then a.soluong       " +
						"				 when a.dvdl_fk = b.DVDL_FK then a.soluong      " +
						"				 else  a.soluong * ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )       " +
						"							/ ( select SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )	 end as soluong, dongia, chietkhau, thueVAT, b.pk_seq as dvdl_fk, tungay, denngay  " +
						"		from ERP_HOPDONGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.pk_seq  " +
						"		where HOPDONG_FK = '" + this.id + "'  " +
						"	union ALL " +
						"		select sanpham_fk,  " +
						"			case when a.dvdl_fk IS null then a.soluong       " +
						"				 when a.dvdl_fk = b.DVDL_FK then a.soluong      " +
						"				 else  a.soluong * ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )       " +
						"							/ ( select SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )	 end as soluong, dongia, chietkhau, thueVAT, b.pk_seq as dvdl_fk, tungay, denngay  " +
						"		from ERP_HOPDONGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.pk_seq   " +
						"		where HOPDONG_FK in ( select hopdong_fk from ERP_HOPDONGNPP where hopdong_fk = '" + this.id + "' and trangthai in (1, 2) ) " +
						"	) " +
						"	hopdong group by sanpham_fk, dvdl_fk, tungay, denngay " +
						") " +
						"hd left join " +
						"( " +
						"	select sanpham_fk, sum(soluong) as daDAT " +
						"	from " +
						"	( " +
						"		select a.sanpham_fk, b.DVDL_FK as dvCHUAN,      " +
						"				case when a.dvdl_fk IS null then a.soluong       " +
						"					 when a.dvdl_fk = b.DVDL_FK then a.soluong      " +
						"					 else  a.soluong * ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )       " +
						"									 / ( select SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk ) end as soluong  " +
						"		from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ      " +
						"		where a.dondathang_fk in (   select pk_seq from ERP_DondathangNPP where trangthai != '3' and hopdong_fk = '" + this.id + "'  )     " +
						"	) " +
						"	dathang group by sanpham_fk " +
						") " +
						"dh on hd.sanpham_fk = dh.sanpham_fk " +
						"where hd.soluong > isnull(dh.daDAT, 0) ";  //KHONG CON SP NAO
				
				System.out.println("----CHECK SANPHAM: " + query );
				rs = db.get(query);
				int soDONG = 0;
				if(rs.next())
				{
					soDONG = rs.getInt("soDONG");
				}
				rs.close();
				
				if(soDONG <= 0)
				{
					msg = "Hợp đồng đã chuyển hết thành SO. Bạn không thể chuyển tiếp.";
					db.getConnection().rollback();
					return false;
				}
			}
				
			query = "update ERP_HOPDONGNPP set trangthai = '2' where pk_seq = '" + this.id + "'";
			if(!db.update(query))
			{
				msg = "Lỗi khi chuyển sang đơn đặt hàng: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = " insert ERP_DondathangNPP(ngaydonhang, ngaydenghi, loaidonhang, npp_dachot, ghichu, trangthai, dvkd_fk, kbh_fk, gsbh_fk, ddkd_fk, khachhang_fk, npp_fk, kho_fk, hopdong_fk, chietkhau, vat, ngaytao, nguoitao, ngaysua, nguoisua) " +
					" select tungay, denngay, 0, 1 as npp_dachot, ghichu, 0 as trangthai, dvkd_fk, kbh_fk, gsbh_fk, ddkd_fk, '" + this.khId + "' as khachhang_fk, npp_fk, kho_fk, pk_seq, chietkhau, vat, ngaytao, nguoitao, ngaysua, nguoisua " +
					" from ERP_HOPDONGNPP where pk_seq = '" + this.id + "' ";
			System.out.println("-- INSERT DDH: " + query );
			if(!db.update(query))
			{
				msg = "Lỗi khi chuyển sang đơn đặt hàng: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			//LAY ID
			ResultSet rsDDH = db.get("select IDENT_CURRENT('ERP_DondathangNPP') as ID ");
			if(rsDDH.next())
			{
				msg = rsDDH.getString("ID");
			}
			rsDDH.close();
			
			/*query = "insert ERP_DondathangNPP_SANPHAM( Dondathang_fk, SANPHAM_FK, soluong, dongia, chietkhau, thueVAT, dvdl_fk, tungay, denngay ) " +
					"select '" + msg + "', sanpham_fk, soluong, dongia, chietkhau, thueVAT, dvdl_fk, tungay, denngay " +
					"from ERP_HOPDONGNPP_SANPHAM where HOPDONG_FK = '" + lsxId + "' ";*/
			
			String sqlSOLUONG = " hd.soluong - isnull(dh.daDAT, 0) ";
			if(loaidonhang.equals("2")) //Hợp đồng nguyên tắc
				sqlSOLUONG = " isnull(hd.soluong, 0) ";
			
			query = "insert ERP_DondathangNPP_SANPHAM( Dondathang_fk, SANPHAM_FK, soluong, dongia, chietkhau, thueVAT, dvdl_fk, tungay, denngay ) " +
					"select '" + msg + "', hd.sanpham_fk, " + sqlSOLUONG + ", hd.dongia, hd.chietkhau, hd.thueVAT, hd.dvdl_fk, hd.tungay, hd.denngay " +
					"from " +
					"( " +
					"	select sanpham_fk, dvdl_fk, sum(soluong) as soluong, avg(dongia) as dongia, avg(chietkhau) as chietkhau, avg(thuevat) as thuevat, tungay, denngay " +
					"	from " +
					"	( " +
					"		select sanpham_fk,  " +
					"			case when a.dvdl_fk IS null then a.soluong       " +
					"				 when a.dvdl_fk = b.DVDL_FK then a.soluong      " +
					"				 else  a.soluong * ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )       " +
					"							/ ( select SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )	 end as soluong, dongia, chietkhau, thueVAT, b.dvdl_fk as dvdl_fk, tungay, denngay  " +
					"		from ERP_HOPDONGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.pk_seq  " +
					"		where HOPDONG_FK = '" + this.id + "'  " +
					"	union ALL " +
					"		select sanpham_fk,  " +
					"			case when a.dvdl_fk IS null then a.soluong       " +
					"				 when a.dvdl_fk = b.DVDL_FK then a.soluong      " +
					"				 else  a.soluong * ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )       " +
					"							/ ( select SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )	 end as soluong, dongia, chietkhau, thueVAT, b.dvdl_fk as dvdl_fk, tungay, denngay  " +
					"		from ERP_HOPDONGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.pk_seq   " +
					"		where HOPDONG_FK in ( select hopdong_fk from ERP_HOPDONGNPP where hopdong_fk = '" + this.id + "' and trangthai in (1, 2) ) " +
					"	) " +
					"	hopdong group by sanpham_fk, dvdl_fk, tungay, denngay " +
					") " +
					"hd left join " +
					"( " +
					"	select sanpham_fk, sum(soluong) as daDAT " +
					"	from " +
					"	( " +
					"		select a.sanpham_fk, b.DVDL_FK as dvCHUAN,      " +
					"				case when a.dvdl_fk IS null then a.soluong       " +
					"					 when a.dvdl_fk = b.DVDL_FK then a.soluong      " +
					"					 else  a.soluong * ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )       " +
					"									 / ( select SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk ) end as soluong  " +
					"		from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ      " +
					"		where a.dondathang_fk in (   select pk_seq from ERP_DondathangNPP where trangthai != '3' and hopdong_fk = '" + this.id + "'  )     " +
					"	) " +
					"	dathang group by sanpham_fk " +
					") " +
					"dh on hd.sanpham_fk = dh.sanpham_fk ";
			
			if(!loaidonhang.equals("2"))
					query += "where hd.soluong > isnull(dh.daDAT, 0) ";
			
			System.out.println("--CHEN SP: " + query);
			if(!db.update(query))
			{
				msg = "Lỗi khi chuyển sang đơn đặt hàng: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "insert ERP_DONDATHANGNPP_CHIETKHAU(DONDATHANG_FK, DIENGIAI, GIATRI, LOAI) " +
					"select '" + msg + "', DIENGIAI, GIATRI, LOAI from ERP_HOPDONGNPP_CHIETKHAU where hopdong_fk = '" + this.id + "' ";
			System.out.println("--CHEN SP 2: " + query);
			if(!db.update(query))
			{
				msg = "Lỗi khi chuyển sang đơn đặt hàng: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			db.getConnection().commit();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			db.update("rollback");
			this.msg = "Lỗi: " + e.getMessage();
		}
		
		return true;
	}

	
	public String getNgaytrungthau() {
		
		return this.ngaytrungthau;
	}

	
	public void setNgaytrungthau(String ngaytrungthau) {
		
		this.ngaytrungthau = ngaytrungthau;
	}

	
	public String getChiphibaolanh() {
		
		return this.chiphibaolanh;
	}

	
	public void setChiphibaolanh(String chiphibaolanh) {
		
		this.chiphibaolanh = chiphibaolanh;
	}

	
	public ResultSet getMahdRs() {
		
		return this.mahdrs;
	}

	
	public void setMahdRs(ResultSet MahdRs) {
		
		this.mahdrs = MahdRs;
	}

	
	public int getLoaithanhly() {
		
		return this.loaithanhly;
	}

	
	public void setLoaithanhly(int loaithanhly) {
		
		this.loaithanhly = loaithanhly;
	}

	
	public String getNgaythanhly() {
		
		return this.ngaytl;
	}

	
	public void setNgaythanhly(String ngaytl) {
		
		this.ngaytl = ngaytl;
	}

	
	public String getNgaythanhlykt() {
		
		return this.ngaytlkt;
	}

	
	public void setNgaythanhlykt(String ngaytlkt) {
		this.ngaytlkt = ngaytlkt;
		
	}

	
	public String[] getMahoadon() {
		
		return this.mahoadon;
	}

	
	public void setMahoadon(String mahoadon[]) {
		
		this.mahoadon = mahoadon;
	}
	
   public String getTdv_dangnhap_id() {
		
		return this.tdv_dangnhap_id;
	}

	
	public void setTdv_dangnhap_id(String tdv_dangnhap_id) {
		
		this.tdv_dangnhap_id = tdv_dangnhap_id;
	}
	
	public String getNpp_duocchon_id() {
		
		return this.npp_duocchon_id;
	}

	
	public void setNpp_duocchon_id(String npp_duocchon_id) {
		
		this.npp_duocchon_id = npp_duocchon_id;
	}
	
	
}
