package geso.traphaco.distributor.beans.doihang.imp;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.beans.doihang.IErpDenghidoihangNpp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

import com.sun.org.apache.regexp.internal.REUtil;


public class ErpDenghidoihangNpp implements IErpDenghidoihangNpp
{
	private static final long serialVersionUID = 1L;
	String userId;
	String id;
	
	String ngayyeucau;
	String ngaydenghi;
	String ghichu;

	String msg;
	String trangthai;
	
	String loaidonhang;  //0 đơn đặt hàng, 1 đơn hàng khuyến mại ứng hàng, 3 đơn hàng khuyến mại tích lũy, 4 đơn hàng trưng bày, 5 đơn hàng khác
	String chietkhau;
	String vat;
	
	String khoNhanId;
	ResultSet khoNhanRs;
	
	String nppId;
	ResultSet nppRs;
	
	String nppTen;
	String sitecode;
	
	String dvkdId;
	ResultSet dvkdRs;
	
	String kbhId;
	ResultSet kbhRs;
	
	ResultSet dvtRs;
	
	String schemeId;
	ResultSet schemeRs;
	
	ResultSet sanphamRs;
	Hashtable<String, String> sp_soluong;
	Hashtable<String, String> sp_solo;
	
	String tsdh;
	
	dbutils db;
	Utility util;
	
	public ErpDenghidoihangNpp()
	{
		this.id = "";
		this.ngayyeucau = getDateTime();
		this.ngaydenghi = "";
		this.ghichu = "";
		this.khoNhanId = "";
		this.nppId = "";
		this.msg = "";
		this.loaidonhang = "0";
		this.trangthai = "0";
		this.chietkhau = "0";
		this.vat = "10";
		this.dvkdId = "";
		this.kbhId = "";
		this.schemeId = "";
		this.tsdh = "6";
		
		this.sp_soluong = new Hashtable<String, String>();
		this.sp_solo = new Hashtable<String, String>();
		this.util = new Utility();
		this.db = new dbutils();
	}
	
	public ErpDenghidoihangNpp(String id)
	{
		this.id = id;
		this.ngayyeucau = getDateTime();
		this.ngaydenghi = "";
		this.ghichu = "";
		this.khoNhanId = "";
		this.nppId = "";
		this.msg = "";
		this.loaidonhang = "0";
		this.trangthai = "0";
		this.chietkhau = "0";
		this.vat = "10";
		this.dvkdId = "";
		this.kbhId = "";
		this.schemeId = "";
		this.tsdh = "1";

		this.sp_soluong = new Hashtable<String, String>();
		this.sp_solo = new Hashtable<String, String>();
		this.util = new Utility();
		this.db = new dbutils();
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

	public String getNgayyeucau() 
	{
		return this.ngayyeucau;
	}

	public void setNgayyeucau(String ngayyeucau) 
	{
		this.ngayyeucau = ngayyeucau;
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

	
	public boolean createNK(HttpServletRequest request) 
	{
		if(this.ngaydenghi.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày đề nghị";
			return false;
		}

		if( this.dvkdId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn đơn vị kinh doanh";
			return false;
		}
		
		if( this.kbhId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn kênh bán hàng";
			return false;
		}
		
		if( this.nppId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn nhà phân phối đổi hàng";
			return false;
		}
		
		if(this.sp_soluong == null)
		{
			this.msg = "Vui lòng nhập sản phẩm đề nghị";
			return false;
		}
		else
		{
			if(this.sp_soluong.size() <= 0)
			{
				this.msg = "Vui lòng nhập sản phẩm đề nghị";
				return false;
			}
			
			if(this.sp_solo.size() <= 0)
			{
				this.msg = "Vui lòng nhập số lô của sản phẩm đề nghị";
				return false;
			}
		}
		
		if(this.khoNhanId.trim().length() <= 0)
		{
			this.msg = "Không thể xác định kho hàng lỗi của NPP. vui lòng kiểm tra lại.";
			return false;
		}
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String khonhan_fk = this.khoNhanId.trim().length() <= 0 ? "null" : this.khoNhanId.trim();
			String vat = this.vat.trim().length() <= 0 ? "0" : this.vat.trim();
			
			String query = " insert ERP_DeNghiDoiHang(ngaydenghi, ghichu, trangthai, dvkd_fk, kbh_fk, npp_fk, kho_fk, chietkhau, vat, ngaytao, nguoitao, ngaysua, nguoisua) " +
						   " values('" + this.ngaydenghi + "', N'" + this.ghichu + "', '0', '" + dvkdId + "', '" + kbhId + "', '" + nppId + "', " + khonhan_fk + ", '0', '" + vat + "', '" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "' )";
			
			System.out.println("1.Insert DDH: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_DeNghiDoiHang " + query;
				db.getConnection().rollback();
				return false;
			}
			
			//LAY ID
			ResultSet rsDDH = db.get("select IDENT_CURRENT('ERP_DeNghiDoiHang') as ID ");
			if(rsDDH.next())
			{
				this.id = rsDDH.getString("ID");
			}
			rsDDH.close();
			
			String[] spId = request.getParameterValues("spID");
			String[] spTEN = request.getParameterValues("spTEN");
			String[] spSOLO = request.getParameterValues("spSOLO");
			String[] spSOLUONG = request.getParameterValues("spSOLUONG");
			String[] spTONKHO = request.getParameterValues("spTONKHO");
			String[] spDONGIA = request.getParameterValues("spDONGIA");
			
			if( spId != null )
			{
				for(int i = 0; i < spId.length; i++ )
				{
					if(spSOLO[i].trim().length() > 0 && spSOLUONG[i].trim().length() > 0)
					{
						String[] soLO = spSOLO[i].split(" --- ");
						
						//CHECK TON KHO
						query = "select AVAILABLE " +
								"from NHAPP_KHO_CHITIET where SOLO = '" + soLO[0] + "' and KBH_FK = '" + this.kbhId + "' and SANPHAM_FK = '" + spId[i] + "' and KHO_FK = '" + this.khoNhanId + "' and NPP_FK = '" + this.nppId + "'  ";
						double avai = 0;
						ResultSet rsCHECK = db.get(query);
						if(rsCHECK.next())
						{
							avai = rsCHECK.getDouble("AVAILABLE");
						}
						rsCHECK.close();
						
						if(avai < Double.parseDouble(spSOLUONG[i].trim().replaceAll(",", "")))
						{
							this.msg = "Sản phẩm ( " + spTEN[i] + " ) với số lô ( " + soLO[0] + " ) với số lượng đề nghị ( " + spSOLUONG[i] + " ) không đủ tồn kho ( " + avai + " ) ";
							db.getConnection().rollback();
							return false;
						}
						
						query = "insert ERP_DENGHIDOIHANG_SANPHAM(denghidoihang_fk, sanpham_fk, dvdl_fk, solo, tonkho, denghi, dongia) " +
								"select '" + this.id + "', '" + spId[i] + "', DVDL_FK, '" + soLO[0] + "', '" + spTONKHO[i].replaceAll(",", "") + "', '" + spSOLUONG[i].replaceAll(",", "") + "', '" + spDONGIA[i].replaceAll(",", "") + "' from SANPHAM " +
								"where pk_seq = '" + spId[i] + "' ";
						
						System.out.println("--INSERT SP: " + query);
						if(!db.update(query))
						{
							this.msg = "Không thể tạo mới ERP_DENGHIDOIHANG_SANPHAM " + query;
							db.getConnection().rollback();
							return false;
						}
						
						//UPDATE KHO
						query = "update NHAPP_KHO_CHITIET set AVAILABLE = AVAILABLE - '" + spSOLUONG[i].replaceAll(",", "") + "', BOOKED = BOOKED + '" + spSOLUONG[i].replaceAll(",", "") + "' " +
								"where SOLO = '" + soLO[0] + "' and KBH_FK = '" + this.kbhId + "' and SANPHAM_FK = '" + spId[i] + "' and KHO_FK = '" + this.khoNhanId + "' and NPP_FK = '" + this.nppId + "'  ";
						if(!db.update(query))
						{
							this.msg = "Không thể cập nhật NHAPP_KHO_CHITIET " + query;
							db.getConnection().rollback();
							return false;
						}
						
						
						query = "update NHAPP_KHO set AVAILABLE = AVAILABLE - '" + spSOLUONG[i].replaceAll(",", "") + "', BOOKED = BOOKED + '" + spSOLUONG[i].replaceAll(",", "") + "' " +
								"where KBH_FK = '" + this.kbhId + "' and SANPHAM_FK = '" + spId[i] + "' and KHO_FK = '" + this.khoNhanId + "' and NPP_FK = '" + this.nppId + "'  ";
						if(!db.update(query))
						{
							this.msg = "Không thể cập nhật NHAPP_KHO " + query;
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

	public boolean updateNK(HttpServletRequest request) 
	{
		if(this.ngaydenghi.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày đề nghị";
			return false;
		}

		if( this.dvkdId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn đơn vị kinh doanh";
			return false;
		}
		
		if( this.kbhId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn kênh bán hàng";
			return false;
		}
		
		if( this.nppId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn nhà phân phối đổi hàng";
			return false;
		}
		
		if(this.sp_soluong == null)
		{
			this.msg = "Vui lòng nhập sản phẩm đề nghị";
			return false;
		}
		else
		{
			if(this.sp_soluong.size() <= 0)
			{
				this.msg = "Vui lòng nhập sản phẩm đề nghị";
				return false;
			}
			
			if(this.sp_solo.size() <= 0)
			{
				this.msg = "Vui lòng nhập số lô của sản phẩm đề nghị";
				return false;
			}
		}
		
		if(this.khoNhanId.trim().length() <= 0)
		{
			this.msg = "Không thể xác định kho hàng lỗi của NPP. vui lòng kiểm tra lại.";
			return false;
		}
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String vat = this.vat.trim().length() <= 0 ? "0" : this.vat.trim();
			String query = " update ERP_DeNghiDoiHang set ngaydenghi = N'" + this.ngaydenghi + "', ghichu = N'" + this.ghichu + "', dvkd_fk = '" + this.dvkdId + "', kbh_fk = '" + this.kbhId + "', kho_fk = '" + this.khoNhanId + "', " +
						   "	vat = '" + vat + "', ngaysua = '" + this.getDateTime() + "', nguoisua = '" + this.userId + "' " +
						   " where pk_seq = '" + this.id + "' ";
			System.out.println("1.update DDH: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_DeNghiDoiHang " + query;
				db.getConnection().rollback();
				return false;
			}
			
			//TANG KHO NGUOC LAI
			query = " update kho  " +
					" 	set kho.BOOKED = kho.BOOKED - doihang.denghi, " +
					" 		kho.AVAILABLE = kho.AVAILABLE + doihang.denghi " +
					" from " +
					" ( " +
					" 	select a.kho_fk, a.npp_fk, a.kbh_fk, b.sanpham_fk, b.solo, b.denghi  " +
					" 	from ERP_DENGHIDOIHANG a inner join ERP_DENGHIDOIHANG_SANPHAM b on a.pk_seq = b.denghidoihang_fk " +
					" 	where a.pk_seq = '" + this.id + "' " +
					" ) " +
					" doihang inner join NHAPP_KHO kho on doihang.kho_fk = kho.KHO_FK and doihang.NPP_FK = kho.NPP_FK " +
					" 		and doihang.KBH_FK = kho.KBH_FK and doihang.sanpham_fk = kho.SANPHAM_FK ";
			System.out.println("2.update DDH: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật NHAPP_KHO " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = " update kho  " +
					" 	set kho.BOOKED = kho.BOOKED - doihang.denghi, " +
					" 		kho.AVAILABLE = kho.AVAILABLE + doihang.denghi " +
					" from " +
					" ( " +
					" 	select a.kho_fk, a.npp_fk, a.kbh_fk, b.sanpham_fk, b.solo, b.denghi  " +
					" 	from ERP_DENGHIDOIHANG a inner join ERP_DENGHIDOIHANG_SANPHAM b on a.pk_seq = b.denghidoihang_fk " +
					" 	where a.pk_seq = '" + this.id + "' " +
					" ) " +
					" doihang inner join NHAPP_KHO_CHITIET kho on doihang.kho_fk = kho.KHO_FK and doihang.NPP_FK = kho.NPP_FK " +
					" 		and doihang.KBH_FK = kho.KBH_FK and doihang.sanpham_fk = kho.SANPHAM_FK and doihang.solo = kho.SOLO ";
			System.out.println("3.update DDH: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật NHAPP_KHO_CHITIET " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_DENGHIDOIHANG_SANPHAM where denghidoihang_fk = '" + this.id + "' ";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_DENGHIDOIHANG_SANPHAM " + query;
				db.getConnection().rollback();
				return false;
			}
			
			String[] spId = request.getParameterValues("spID");
			String[] spTEN = request.getParameterValues("spTEN");
			String[] spSOLO = request.getParameterValues("spSOLO");
			String[] spSOLUONG = request.getParameterValues("spSOLUONG");
			String[] spTONKHO = request.getParameterValues("spTONKHO");
			String[] spDONGIA = request.getParameterValues("spDONGIA");
			
			if( spId != null )
			{
				for(int i = 0; i < spId.length; i++ )
				{
					if(spSOLO[i].trim().length() > 0 && spSOLUONG[i].trim().length() > 0)
					{
						String[] soLO = spSOLO[i].split(" --- ");
						
						//CHECK TON KHO
						query = "select AVAILABLE " +
								"from NHAPP_KHO_CHITIET where SOLO = '" + soLO[0] + "' and KBH_FK = '" + this.kbhId + "' and SANPHAM_FK = '" + spId[i] + "' and KHO_FK = '" + this.khoNhanId + "' and NPP_FK = '" + this.nppId + "'  ";
						double avai = 0;
						ResultSet rsCHECK = db.get(query);
						if(rsCHECK.next())
						{
							avai = rsCHECK.getDouble("AVAILABLE");
						}
						rsCHECK.close();
						
						if(avai < Double.parseDouble(spSOLUONG[i].trim().replaceAll(",", "")))
						{
							this.msg = "Sản phẩm ( " + spTEN[i] + " ) với số lô ( " + soLO[0] + " ) với số lượng đề nghị ( " + spSOLUONG[i] + " ) không đủ tồn kho ( " + avai + " ) ";
							db.getConnection().rollback();
							return false;
						}
						
						query = "insert ERP_DENGHIDOIHANG_SANPHAM(denghidoihang_fk, sanpham_fk, dvdl_fk, solo, tonkho, denghi, dongia) " +
								"select '" + this.id + "', '" + spId[i] + "', DVDL_FK, '" + soLO[0] + "', '" + spTONKHO[i].replaceAll(",", "") + "', '" + spSOLUONG[i].replaceAll(",", "") + "', '" + spDONGIA[i].replaceAll(",", "") + "' from SANPHAM " +
								"where pk_seq = '" + spId[i] + "' ";
						
						System.out.println("--INSERT SP: " + query);
						if(!db.update(query))
						{
							this.msg = "Không thể tạo mới ERP_DENGHIDOIHANG_SANPHAM " + query;
							db.getConnection().rollback();
							return false;
						}
						
						//UPDATE KHO
						query = "update NHAPP_KHO_CHITIET set AVAILABLE = AVAILABLE - '" + spSOLUONG[i].replaceAll(",", "") + "', BOOKED = BOOKED + '" + spSOLUONG[i].replaceAll(",", "") + "' " +
								"where SOLO = '" + soLO[0] + "' and KBH_FK = '" + this.kbhId + "' and SANPHAM_FK = '" + spId[i] + "' and KHO_FK = '" + this.khoNhanId + "' and NPP_FK = '" + this.nppId + "'  ";
						if(!db.update(query))
						{
							this.msg = "Không thể cập nhật NHAPP_KHO_CHITIET " + query;
							db.getConnection().rollback();
							return false;
						}
						
						
						query = "update NHAPP_KHO set AVAILABLE = AVAILABLE - '" + spSOLUONG[i].replaceAll(",", "") + "', BOOKED = BOOKED + '" + spSOLUONG[i].replaceAll(",", "") + "' " +
								"where KBH_FK = '" + this.kbhId + "' and SANPHAM_FK = '" + spId[i] + "' and KHO_FK = '" + this.khoNhanId + "' and NPP_FK = '" + this.nppId + "'  ";
						if(!db.update(query))
						{
							this.msg = "Không thể cập nhật NHAPP_KHO " + query;
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
		
		ResultSet rsKHO = db.get("select PK_SEQ, TEN from KHO where trangthai = '1' and pk_seq in " + this.util.quyen_kho(this.userId)+" and loaikho = '1' ");
		if(rsKHO != null)
		{
			try
			{
				if(rsKHO.next())
				{
					this.khoNhanId = rsKHO.getString("PK_SEQ");
				}
				rsKHO.close();
			} 
			catch (Exception e) {}
		}
		

		this.dvtRs = db.getScrol("select PK_SEQ, DONVI from DONVIDOLUONG where trangthai = '1' ");
		
		this.dvkdRs = db.get("select PK_SEQ, DONVIKINHDOANH as TEN from DONVIKINHDOANH where TRANGTHAI = '1' ");
		this.kbhRs = db.get("select PK_SEQ, DIENGIAI as TEN from KENHBANHANG where TRANGTHAI = '1' and PK_SEQ in ( select KBH_FK from NHAPP_KBH where NPP_FK = '" + this.nppId + "' ) ");
		
		if(this.nppId.trim().length() > 0)
		{
			String query =  "select a.PK_SEQ as nppId, d.DVKD_FK, b.KBH_FK  " +
						    "From NhaPhanPhoi a left join nhapp_kbh b on b.NPP_FK = a.PK_SEQ left join NHAPP_NHACC_DONVIKD c on c.NPP_FK = b.NPP_FK  " +
						    "	left join NHACUNGCAP_DVKD d on d.PK_SEQ = c.NCC_DVKD_FK   " +
						    "where a.pk_Seq = '" + this.nppId + "' ";
			ResultSet rsInfo = db.get(query);
			if(rsInfo != null)
			{
				try 
				{
					if(rsInfo.next())
					{
						if(this.dvkdId.trim().length() <= 0)
							this.dvkdId = rsInfo.getString("DVKD_FK");
						if(this.kbhId.trim().length() <= 0 )
							this.kbhId = rsInfo.getString("KBH_FK");
					}
					rsInfo.close();
				} 
				catch (Exception e) {}
			}
	
			//INIT SAN PHAM
			query = " select a.PK_SEQ, a.MA, a.TEN, b.DONVI, BGIA.giamua as donGIA   " +
					" from SANPHAM a inner join DONVIDOLUONG b on a.DVDL_FK = b.PK_SEQ   " +
					" 	 left join   " +
					" 	 (  	 " +
					" 		 select a.PK_SEQ, a.DVDL_FK as dvCHUAN,  isnull( (  select GIAMUA_SAUCK  " +
					" 															from BGMUANPP_SANPHAM    					  " +
					" 															where SANPHAM_FK = a.pk_seq    and BGMUANPP_FK in ( select top(1) PK_SEQ from BANGGIAMUANPP bg inner join BANGGIAMUANPP_NPP bg_npp on bg.PK_SEQ = bg_npp.BANGGIAMUANPP_FK  where bg.TRANGTHAI = '1' and bg_npp.NPP_FK = '" + this.nppId + "' and bg.DVKD_FK = '" + this.dvkdId + "' and bg.KENH_FK = '" + this.kbhId + "'  order by bg.TUNGAY desc ) ), 0) as giamua    	 " +
					" 		 from SANPHAM a   " +
					" 	 )   " +
					" 	 BGIA on a.PK_SEQ = BGIA.PK_SEQ  ";
			
			System.out.println("--INIT SAN PHAM: " + query);
			this.sanphamRs = db.get(query);
			
		}
		
	}

	public void init() 
	{
		String query = "select ngaydenghi, ISNULL(ghichu, '') as ghichu, dvkd_fk, kbh_fk, npp_fk, kho_fk, chietkhau, vat " +
						"from ERP_Denghidoihang where pk_seq = '" + this.id + "'";
		System.out.println("____INIT DNDH: " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					this.ngaydenghi = rs.getString("ngaydenghi");
					this.ghichu = rs.getString("ghichu");
					this.dvkdId = rs.getString("dvkd_fk");
					this.kbhId = rs.getString("kbh_fk");
					this.nppId = rs.getString("npp_fk");
					this.khoNhanId = rs.getString("kho_fk");
					this.chietkhau = rs.getString("chietkhau");
					this.vat = rs.getString("vat");
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				System.out.println("---LOI INIT: " + e.getMessage());
			}
			
			//INIT SO LUONG DAT
			query = "select c.trangthai, b.pk_seq, a.denghi, a.solo,  " +
					"	( select AVAILABLE from NHAPP_KHO_CHITIET where KHO_FK = c.kho_fk and sanpham_fk = a.sanpham_fk and solo = a.solo and KBH_FK = c.kbh_fk and NPP_FK = c.NPP_FK ) as avai   " +
					"from ERP_DeNghiDoiHang_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ   " +
					"	inner join ERP_DeNghiDoiHang c on a.denghidoihang_fk= c.pk_seq  " +
					"where a.denghidoihang_fk = '" + this.id + "' and denghi != 0 ";
			System.out.println("--INIT SP: " + query);
			ResultSet rsSP = db.get(query);
			if(rsSP != null)
			{
				try 
				{
					Hashtable<String, String> sp_soluong = new Hashtable<String, String>();
					Hashtable<String, String> sp_solo = new Hashtable<String, String>();
					while(rsSP.next())
					{
						String trangthai = rsSP.getString("trangthai");
						
						sp_soluong.put(rsSP.getString("pk_seq"), rsSP.getString("denghi"));
						
						if(trangthai.equals("0"))
							sp_solo.put(rsSP.getString("pk_seq"), rsSP.getString("solo") + " --- " + ( rsSP.getDouble("avai") + rsSP.getDouble("denghi") ) );
						else
							sp_solo.put(rsSP.getString("pk_seq"), rsSP.getString("solo") + " --- " + rsSP.getString("avai") );
					}
					rsSP.close();
					
					this.sp_soluong = sp_soluong;
					this.sp_solo = sp_solo;
				} 
				catch (Exception e) {}
			}
			
		}
		
		this.createRs();
		
		
		//this.initSANPHAM();
		
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

	public String getNppId() {
		
		return this.nppId;
	}

	
	public void setNppId(String nppId) {
		
		this.nppId = nppId;
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

	
	public String getChietkhau() {
		
		return this.chietkhau;
	}

	
	public void setChietkhau(String chietkhau) {
		
		this.chietkhau = chietkhau;
	}

	
	public String getVat() {
		
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

	public String getNgaydenghi() {
		
		return this.ngaydenghi;
	}

	public void setNgaydenghi(String ngaydenghi) {
		
		this.ngaydenghi = ngaydenghi;
	}

	public ResultSet getDvtRs() {

		return this.dvtRs;
	}


	public void setDvtRs(ResultSet dvtRs) {
		
		this.dvtRs = dvtRs;
	}

	
	public String getSchemeId() {
		
		return this.schemeId;
	}

	
	public void setSchemeId(String kbhId) {
		
		this.schemeId = kbhId;
	}

	
	public ResultSet getSchemeRs() {
		
		return this.schemeRs;
	}

	
	public void setSchemeRs(ResultSet schemeRs) {
		
		this.schemeRs = schemeRs;
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
		//Phien ban moi
		geso.traphaco.distributor.util.Utility util=new geso.traphaco.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();
		//this.dangnhap = util.getDangNhap();
		this.sitecode=util.getSitecode();
	}

	
	public ResultSet getSanphamRs() {
		
		return this.sanphamRs;
	}

	
	public void setSanphamRs(ResultSet spRs) {
		
		this.sanphamRs = spRs;
	}

	
	public void setSanpham_soluong(Hashtable<String, String> sp_soluong) {
		
		this.sp_soluong = sp_soluong;
	}

	
	public Hashtable<String, String> getSanpham_soluong() {
		
		return this.sp_soluong;
	}

	
	public String getTansuatdathang() {

		return this.tsdh;
	}


	public void setTansauatdathang(String tsdh) {
		
		this.tsdh = tsdh;
	}

	
	public void setSanpham_solo(Hashtable<String, String> sp_solo) {
		
		this.sp_solo = sp_solo;
	}


	public Hashtable<String, String> getSanpham_solo() {

		return this.sp_solo;
	}


	public ResultSet getSoloRs(String spId, String khoId)
	{
		String query = "select SOLO, AVAILABLE from NHAPP_KHO_CHITIET " +
					   "where NPP_FK = '" + this.nppId + "' and SANPHAM_FK = '" + spId + "' and KHO_FK = '" + khoId + "' " +
					   "order by NGAYHETHAN asc";
		
		//System.out.println("LAY SO LO: " + query);
		return db.get(query);
	}


	
	
}
