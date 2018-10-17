package geso.traphaco.distributor.beans.dontrahang.imp;

import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.beans.dontrahang.IErpNhaphangtrave;
import geso.traphaco.distributor.db.sql.dbutils;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

public class ErpNhaphangtrave implements IErpNhaphangtrave, Serializable
{
	private static final long serialVersionUID = -4877175563344609017L;
	String userId;
	String id;
	
	String ngayyeucau;
	String ghichu;

	String msg;
	String trangthai;
	
	String khoXuatId;
	ResultSet khoXuatRs;
	
	String khoNhanId;
	ResultSet khoNhanRs;
	
	String khId;
	ResultSet khRs;
	ResultSet dvtRs;
	
	String kbhId;
	ResultSet kbhRs;

	String lenhdieudong, lddcua, lddveviec, ngaylenhdieudong, sohopdong,ngayhopdong, nguoivanchuyen, ptvanchuyen;
	
	ResultSet spRs;
	
	String[] spId;
	String[] spMa;
	String[] spTen;
	String[] spDonvi;
	String[] spSoluong;
	String[] spGianhap;
	String[] spSolo;
	String[] spTonkho;
	String[] spBooked;
	String[] spNgaysanxuat;
	String[] spNgayhethan;
	
	String[] spSoluongNHAN_DAT;
	String[] spSoluongNHAN_KHONGDAT;
	
	String nppId;
	String nppTen;
	String sitecode;
	String sochungtu;
	String dungchung;
	
	Hashtable<String, String> sanpham_soluong;
	Hashtable<String, String> sanpham_soluongNHAN_DAT;
	Hashtable<String, String> sanpham_soluongNHAN_KHONGDAT;
	
	dbutils db;
	Utility util;
	
	String loaidonhang;
	
	String trahangId;
	ResultSet trahangRs;
	
	// thêm 2 hashmap<masp, List<String> solo>
	// hashmap <masp, List<String> ngayhethan>
	HashMap<String, List<String>> sanpham_solo;
	HashMap<String, List<String>> sanpham_solo_ngayhethan;
	
	// thêm hashmap<masp, Lis<String> sologoc> //số lô lúc chưa đổi được lưu hidden trên jsp
	HashMap<String, List<String>> sanpham_sologoc;
	String trahangdienta;
	
	String tdv_dangnhap_id;
	String npp_duocchon_id;
	
	public ErpNhaphangtrave()
	{
		this.id = "";
		this.ngayyeucau = getDateTime();
		this.ghichu = "";
		this.khoNhanId = "";
		this.khoXuatId = "";
		this.khId = "";
		this.kbhId = "";
		this.msg = "";
		this.trangthai = "0";
		
		this.lenhdieudong="";
		this.lddcua="";
		this.lddveviec="";
		this.ngaylenhdieudong="";
		this.sohopdong="";
		this.ngayhopdong="";
		this.nguoivanchuyen="";
		this.ptvanchuyen="";
		this.tdv_dangnhap_id = "";
		this.npp_duocchon_id = "";
		
		this.sanpham_soluong = new Hashtable<String, String>();
		this.sanpham_soluongNHAN_DAT = new Hashtable<String, String>();
		this.sanpham_soluongNHAN_KHONGDAT = new Hashtable<String, String>();
		
		this.sochungtu ="";
		this.dungchung = "";
		this.db = new dbutils();
		this.util = new Utility();
		this.loaidonhang = "";
		this.trahangId = "";
		this.sanpham_solo = new HashMap<String, List<String>>();
		this.sanpham_solo_ngayhethan = new HashMap<String, List<String>>();
		this.sanpham_sologoc = new HashMap<String, List<String>>();
		this.trahangdienta ="";
	}
	
	public ErpNhaphangtrave(String id)
	{
		this.id = id;
		this.ngayyeucau = getDateTime();
		this.ghichu = "";
		this.khoNhanId = "";
		this.khoXuatId = "";
		this.khId = "";
		this.kbhId = "";
		this.msg = "";
		this.trangthai = "0";
		
		this.lenhdieudong="";
		this.lddcua="";
		this.lddveviec="";
		this.ngaylenhdieudong="";
		this.sohopdong="";
		this.ngayhopdong="";
		this.nguoivanchuyen="";
		this.ptvanchuyen="";
		
		this.sanpham_soluong = new Hashtable<String, String>();
		this.sanpham_soluongNHAN_DAT = new Hashtable<String, String>();
		this.sanpham_soluongNHAN_KHONGDAT = new Hashtable<String, String>();
		
		this.sochungtu ="";
		this.dungchung = "";
		this.db = new dbutils();
		this.util = new Utility();
		this.loaidonhang = "";
		this.trahangId = "";
		
		this.tdv_dangnhap_id = "";
		this.npp_duocchon_id = "";
		
		this.sanpham_solo = new HashMap<String, List<String>>();
		this.sanpham_solo_ngayhethan = new HashMap<String, List<String>>();
		this.trahangdienta ="";
	}
	
	
	public HashMap<String, List<String>> getSanpham_sologoc() {
		return sanpham_sologoc;
	}

	public void setSanpham_sologoc(HashMap<String, List<String>> sanpham_sologoc) {
		this.sanpham_sologoc = sanpham_sologoc;
	}

	public String getTrahangdienta() {
		return trahangdienta;
	}

	public void setTrahangdienta(String trahangdienta) {
		this.trahangdienta = trahangdienta;
	}

	public HashMap<String, List<String>> getSanpham_solo() {
		return sanpham_solo;
	}

	public void setSanpham_solo(HashMap<String, List<String>> sanpham_solo) {
		this.sanpham_solo = sanpham_solo;
	}

	public HashMap<String, List<String>> getSanpham_solo_ngayhethan() {
		return sanpham_solo_ngayhethan;
	}

	public void setSanpham_solo_ngayhethan(
			HashMap<String, List<String>> sanpham_solo_ngayhethan) {
		this.sanpham_solo_ngayhethan = sanpham_solo_ngayhethan;
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

	
	public boolean createNK(String congtyId) 
	{
		if(this.ngayyeucau.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập ngày chuyển";
			return false;
		}
		
		if( this.khoXuatId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn kho xuất";
			return false;
		}
		
		if( this.khId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn đối tượng nhận";
			return false;
		}
		
		if( this.kbhId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn kênh bán hàng";
			return false;
		}
		
	
		boolean coSP = false;
		for(int i = 0; i < spMa.length; i++)
		{
			if(spMa[i].trim().length() > 0 && spSoluong[i].trim().length() > 0  )
			{
				if( spSoluong[i].trim().length() > 0 )
				{
					coSP = true;
				}
			}
		}
		
		if(!coSP)
		{
			this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm xuất";
			return false;
		}
		
		//CHECK TRUNG MA 
		for(int i = 0; i < spMa.length - 1; i++)
		{
			for(int j = i + 1; j < spMa.length; j++)
			{
				if(spMa[i].trim().length() > 0 && spMa[j].trim().length() > 0 )
				{
					if( spMa[i].trim().equals(spMa[j].trim()) )
					{
						this.msg = "Sản phẩm ( " + spTen[i] + " )  đã bị trùng.";
						return false;
					}
				}
			}
		}	
		
		if(this.sanpham_soluongNHAN_DAT == null && this.sanpham_soluongNHAN_KHONGDAT == null )
		{
			this.msg = "Vui lòng kiểm tra lại số lô nhận về";
			return false;
		}
		
		try
		{
			String isNPP = "0";
			if(this.loaidonhang.equals("0"))
				isNPP = "1";
		
			db.getConnection().setAutoCommit(false);
			
			String query =  " insert ERP_NHANHANG(NGAYNHAN, NGAYCHOT, LOAIHANGHOA_FK, NOIDUNGNHAN_FK, SOHOADON, DIENGIAI, MUAHANG_FK, TRAHANG_FK, NGAYTAO, NGAYSUA, NGUOITAO, NGUOISUA, TRANGTHAI, CONGTY_FK, NoiDungNhap_fk, NCC_KH_FK, KHOCHOXULY_FK, isNPP, NHAPHANPHOI_FK, GHICHU, KBH_FK ) " +
							" values('" + this.ngayyeucau + "', '" + this.ngayyeucau + "', '0', '100001', 'NA', N'" + this.ghichu + "',  NULL,  "
								+ this.trahangId + " , '" + this.getDateTime() +"', '" + this.getDateTime()  +"', '" + this.userId + "', '" + this.userId +"', '0', '" + congtyId + "', '100004', " + this.khId + ","+this.khoXuatId+", '" + isNPP + "', '" + this.nppId + "', N'" + this.ghichu + "', "+this.kbhId+" )";

			System.out.println("1.Insert CK: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_NHANHANG " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "select SCOPE_IDENTITY() as hdId";
			ResultSet rs = db.get(query);
			rs.next();
			this.id = rs.getString("hdId");
			rs.close();
			
			for(int i = 0; i < spMa.length; i++)
			{
				if(spMa[i].trim().length() > 0  )
				{
					if(spSoluongNHAN_DAT[i].trim().length() > 0 )
					{
						query = "insert ERP_NHANHANG_SANPHAM( NHANHANG_FK, SANPHAM_FK, DIENGIAI, SOLUONGDAT, SOLUONGNHAN, DONGIA, NGAYNHANDUKIEN, DUNGSAI, DONVI, TIENTE_FK, TYGIAQUYDOI, DONGIAVIET, KHONHAN, DANHAN )  " +
								"select '" + this.id + "', sp.pk_seq, sp.ten, '" + spSoluong[i].replaceAll(",", "") + "' as SOLUONGDAT, '" + spSoluongNHAN_DAT[i].replaceAll(",", "") + "' as SOLUONGNHAN, '" + spGianhap[i].replaceAll(",", "") + "' as DONGIA, '" + this.ngayyeucau + "' NGAYNHANDUKIEN, 0 DUNGSAI, dv.DONVI, '100000' as TIENTE_FK, 1 as TYGIAQUYDOI, '" + spGianhap[i].replaceAll(",", "") + "' as DONGIAVIET, '" + this.khoXuatId + "' KHONHAN, 1 as DANHAN  " +
								"from SANPHAM sp inner join DONVIDOLUONG dv on sp.dvdl_fk = dv.pk_seq " + 
								"where sp.MA = '" + spMa[i] + "' ";
						System.out.println("1.2.Insert NH - SP: " + query);
						if(db.updateReturnInt(query)!=1)
						{
							this.msg = "Khong the tao moi ERP_NHANHANG_SANPHAM: " + query;
							db.getConnection().rollback();
							return false;
						}
						
						// xử lý insert số lô và sản phẩm
						
						List<String> listsolo = this.sanpham_solo.get(spMa[i]);
						List<String> listsolongayhethan = this.sanpham_solo_ngayhethan.get(spMa[i]);
						for(int j=0; j<listsolo.size() ; j++){
							query = "insert ERP_NHANHANG_SP_CHITIET(NHANHANG_FK, SANPHAM_FK, LANNHAN, SOLO, SOLUONG, NGAYSANXUAT, NGAYHETHAN, " +
							"NGAYNHANDUKIEN, KHO_FK,GIATHEOLO) " +
							"select '" + this.id + "', PK_SEQ, 1 as LANNHAN, '" + listsolo.get(j) + "' as SOLO, " +
									" dhsp.SOLUONG as SOLUONG,'" + this.ngayyeucau + "' as NGAYSANXUAT," +
									" '" + listsolongayhethan.get(j) + "' as NGAYHETHAN, '" + this.ngayyeucau + "' NGAYNHANDUKIEN, '" 
									+ this.khoXuatId + "',dhsp.DONGIA as DONGIA " +
									"from SANPHAM sp inner join DONTRAHANG_SP dhsp on sp.PK_SEQ= dhsp.SANPHAM_FK " +
							" where sp.MA='" + spMa[i] + "'  and dhsp.DONTRAHANG_FK ='"+this.trahangId+"' and"+
							" dhsp.SOLO = '"+listsolo.get(j)+"'";
							
							System.out.println("1.2.Insert NH - SP - CT: " + query);
							
							if(db.updateReturnInt(query)!=1)
							{
								this.msg = "Khong the tao moi DONTRAHANG_SP: " + query;
								db.getConnection().rollback();
								return false;
							}
		
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

	public boolean updateNK(String congtyId) 
	{
		if(this.ngayyeucau.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập ngày chuyển";
			return false;
		}
		
		if( this.khoXuatId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn kho xuất";
			return false;
		}
		
		if( this.khId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn đối tượng nhận";
			return false;
		}
		
		if( this.kbhId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn kênh bán hàng";
			return false;
		}
		
	
		boolean coSP = false;
		for(int i = 0; i < spMa.length; i++)
		{
			if(spMa[i].trim().length() > 0 && spSoluong[i].trim().length() > 0  )
			{
				if( spSoluong[i].trim().length() > 0 )
				{
					coSP = true;
				}
			}
		}
		
		if(!coSP)
		{
			this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm xuất";
			return false;
		}
		
		//CHECK TRUNG MA 
		for(int i = 0; i < spMa.length - 1; i++)
		{
			for(int j = i + 1; j < spMa.length; j++)
			{
				if(spMa[i].trim().length() > 0 && spMa[j].trim().length() > 0 )
				{
					if( spMa[i].trim().equals(spMa[j].trim()) )
					{
						this.msg = "Sản phẩm ( " + spTen[i] + " )  đã bị trùng.";
						return false;
					}
				}
			}
		}	
		
		if(this.sanpham_soluongNHAN_DAT == null && this.sanpham_soluongNHAN_KHONGDAT == null )
		{
			this.msg = "Vui lòng kiểm tra lại số lô nhận về";
			return false;
		}
		
		try
		{
			db.getConnection().setAutoCommit(false);
			String query = "";
			
			query = "delete ERP_NHANHANG_SANPHAM where nhanhang_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_NHANHANG_SANPHAM " + query;
				db.getConnection().rollback();
				return false;
			}	

			query = "delete ERP_NHANHANG_SP_CHITIET where nhanhang_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_NHANHANG_SP_CHITIET " + query;
				db.getConnection().rollback();
				return false;
			}	
			
			query = " Update ERP_NHANHANG set  NGAYNHAN = '" + this.ngayyeucau + "', NguoiSua = '" + this.userId 
			+ "', NCC_KH_FK = " + this.khId + ", TRAHANG_FK = " + this.trahangId 
			+ ", ngaysua = getdate(), GhiChu = N'" + this.ghichu + "', KBH_FK = " +this.kbhId + 
			",KHOCHOXULY_FK = "+ this.khoXuatId +
			" where pk_seq = '" + this.id + "' ";

			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật DonTraHang " + query;
				db.getConnection().rollback();
				return false;
			}

			for(int i = 0; i < spMa.length; i++)
			{
				if(spMa[i].trim().length() > 0  )
				{
					if(spSoluongNHAN_DAT[i].trim().length() > 0 )
					{
						query = "insert ERP_NHANHANG_SANPHAM( NHANHANG_FK, SANPHAM_FK, DIENGIAI, SOLUONGDAT, SOLUONGNHAN, DONGIA, NGAYNHANDUKIEN, DUNGSAI, DONVI, TIENTE_FK, TYGIAQUYDOI, DONGIAVIET, KHONHAN, DANHAN )  " +
								"select '" + this.id + "', sp.pk_seq, sp.ten, '" + spSoluong[i].replaceAll(",", "") + "' as SOLUONGDAT, '" + spSoluongNHAN_DAT[i].replaceAll(",", "") + "' as SOLUONGNHAN, '" + spGianhap[i].replaceAll(",", "") + "' as DONGIA, '" + this.ngayyeucau + "' NGAYNHANDUKIEN, 0 DUNGSAI, dv.DONVI, '100000' as TIENTE_FK, 1 as TYGIAQUYDOI, '" + spGianhap[i].replaceAll(",", "") + "' as DONGIAVIET, '" + this.khoXuatId + "' KHONHAN, 1 as DANHAN  " +
								"from SANPHAM sp inner join DONVIDOLUONG dv on sp.dvdl_fk = dv.pk_seq " + 
								"where sp.MA = '" + spMa[i] + "' ";
						System.out.println("1.2.Insert NH - SP: " + query);
						if(db.updateReturnInt(query)!=1)
						{
							this.msg = "Khong the tao moi ERP_NHANHANG_SANPHAM: " + query;
							db.getConnection().rollback();
							return false;
						}
						
						// xử lý insert số lô và sản phẩm
						
						List<String> listsolo = this.sanpham_solo.get(spMa[i]);
						List<String> listsolongayhethan = this.sanpham_solo_ngayhethan.get(spMa[i]);
						
						for(int j=0; j<listsolo.size() ; j++){
							query = "insert ERP_NHANHANG_SP_CHITIET(NHANHANG_FK, SANPHAM_FK, LANNHAN, SOLO, SOLUONG, NGAYSANXUAT, NGAYHETHAN, " +
							"NGAYNHANDUKIEN, KHO_FK,GIATHEOLO) " +
							"select '" + this.id + "', PK_SEQ, 1 as LANNHAN, '" + listsolo.get(j) + "' as SOLO, " +
									" dhsp.SOLUONG as SOLUONG,'" + this.ngayyeucau + "' as NGAYSANXUAT," +
									" '" + listsolongayhethan.get(j) + "' as NGAYHETHAN, '" + this.ngayyeucau + "' NGAYNHANDUKIEN, '" 
									+ this.khoXuatId + "',dhsp.DONGIA as DONGIA " +
									"from SANPHAM sp inner join DONTRAHANG_SP dhsp on sp.PK_SEQ= dhsp.SANPHAM_FK " +
									" where sp.MA='" + spMa[i] + "'  and dhsp.DONTRAHANG_FK ='"+this.trahangId+"' and"+
									" dhsp.SOLO = '"+listsolo.get(j)+"'";
							
							System.out.println("1.2.Insert NH - SP - CT: " + query);
							
							if(db.updateReturnInt(query)!=1)
							{
								this.msg = "Khong the tao moi DONTRAHANG_SP: " + query;
								db.getConnection().rollback();
								return false;
							}
		
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
			e.printStackTrace();
			return false;
		}
		
		return true;
	}


	public void createRs() 
	{
		Utility util = new Utility();
		this.getNppInfo();
		System.out.println("---NPP ID: " + this.nppId);
		
		String query = "select PK_SEQ, TEN from KHO where trangthai = '1' and pk_seq in " + this.util.quyen_kho(this.userId) ;
		this.khoXuatRs = db.get(query);
		
		
		//query = " select PK_SEQ,TEN from NHAPHANPHOI where  PK_SEQ in (select tructhuoc_fk from NHAPHANPHOI where PK_SEQ='"+this.nppId+"') ";
		//System.out.println("___"+query);
		query = "	select PK_SEQ, 'NPP / ' + isnull(maFAST, '') + '-' + TEN as TEN  " +
				"	from NHAPHANPHOI where TRANGTHAI = '1' and pk_seq in ( select Npp_fk from PHAMVIHOATDONG where Nhanvien_fk = '" + this.userId + "'  ) " +
				" UNION ALL	" +
				"	select PK_SEQ, 'KH / ' + isnull(maFAST,'') + '-' + TEN as TEN " +
				"	from KHACHHANG a where TRANGTHAI = '1'  and npp_fk = '" + this.nppId + "' " + util.getPhanQuyen_TheoNhanVien("KHACHHANG", "a", "PK_SEQ", this.getLoainhanvien(), this.getDoituongId() );
		System.out.println(":::: INIT KHACH HANG: " + query );
		this.khRs = db.get(query);
		
		this.dvtRs = db.getScrol("select PK_SEQ, DONVI from DONVIDOLUONG where trangthai = '1' ");
		
		//CHECK DUNG CHUNG KENH HAY KHONG
		query =  "select isnull(dungchungkenh, 0) as dungchungkenh from NHAPHANPHOI where pk_seq = '" + this.nppId + "' ";
		ResultSet rsKENH = db.get(query);
		this.dungchungkenh = "0";
		if(rsKENH != null)
		{
			try 
			{
				if(rsKENH.next())
				{
					this.dungchungkenh = rsKENH.getString("dungchungkenh");
				}
				rsKENH.close();
			} 
			catch (Exception e) {e.printStackTrace(); }
		}
	
		this.kbhRs = db.getScrol("select PK_SEQ, TEN from KENHBANHANG where trangthai = '1' ");
			
		System.out.println("::: KHACH HANG ID: " + this.khId);
		if(this.khId.trim().length() > 0)
		{
			query = "select PK_SEQ, CAST(pk_seq as varchar(10)) + ' [' + NGAYTRA + ']' as diengiai " +
					"from DONTRAHANG  " +
					"where trangthai in ( 1, 2 ) and ( ( loaidonhang = 0 and NPP_TRA_FK = '" + this.khId + "' ) or ( loaidonhang != 0 and KHACHHANG_FK = '" + this.khId + "' ) )";
			
			System.out.println("::: LAY TRA HANG: " + query);
			this.trahangRs = db.get(query);
		}
		
		if(this.trahangId.trim().length() > 0  )
		{
			//CHECK XEM DA NHAP DU LIEU BEN NGOAI CHUA
			boolean reload = true;
			if( this.spSoluongNHAN_DAT == null )
			{
				reload = true;
			}
			else
			{
				for(int i = 0; i < this.spSoluongNHAN_DAT.length; i++ )
				{
					if(!this.spSoluongNHAN_DAT[i].trim().equals("0"))
						reload = false;
				}
			}
			
			query = "	 select b.MA, b.TEN, DV.donvi,( a.SOLUONG) as soluong, a.dongia, 0 as chietkhau,  \n"+
					"		ISNULL(b.trongluong, 0) as trongluong, ISNULL(b.thetich, 0) as thetich ,       \n" +
					"		a.SOLUONG + ISNULL( ( select sum(available)  from NHAPP_KHO   where KHO_FK = (select Kho_fk from DonTraHang where PK_SEQ= '"+this.trahangId+"') and sanpham_fk = b.pk_seq  and NPP_FK = (select NPP_FK from DonTraHang where PK_SEQ= '"+this.trahangId+"') and NHOMKENH_FK = 100000 ), 0 ) as avai, a.ptVat,   \n"+
					"		c.KBH_FK, c.KHO_FK \n"+
					"	 from   \n"+ 
					"	 (  \n"+
					"			 select a.DONTRAHANG_FK,sum(a.SOLUONG)  as SoLuong,SANPHAM_FK,isnull(ptVat,0) as ptVat,DONGIA  \n"+
					"			 from DONTRAHANG_SP a  \n"+
					"			 where a.dontrahang_Fk = '" + this.trahangId + "'   \n"+
					"			 group by DONTRAHANG_FK,SANPHAM_FK,a.DONGIA,ptvat  \n"+  
					"	 ) a inner Join SanPham b on a.SANPHAM_FK = b.PK_SEQ       \n"+		
					"	 INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = b.dvdl_Fk  \n" +
					"    INNER JOIN DONTRAHANG c on a.DONTRAHANG_FK = c.PK_SEQ ";       

			System.out.println("---INIT SP: " + query);
			ResultSet spRs = db.get(query);

			NumberFormat formater = new DecimalFormat("##,###,###");
			/*if(spRs != null)*/
			{
				try 
				{
					String spMA = "";
					String spTEN = "";
					String spDONVI = "";
					String spSOLUONG = "";
					String spGIANHAP = "";
					String spTONKHO = "";
					String spVAT = "";
					
					String spSOLUONG_NHAN_DAT = "";
					String spSOLUONG_NHAN_KHONGDAT = "";

					while(spRs.next())
					{
						spMA += spRs.getString("MA") + "__";
						spTEN += spRs.getString("TEN") + "__";
						spDONVI += spRs.getString("DONVI") + "__";
						spSOLUONG += formater.format(spRs.getDouble("SOLUONG")) + "__";
						spGIANHAP += formater.format(spRs.getDouble("DONGIA")) + "__";
						spTONKHO += formater.format(spRs.getDouble("avai")) + "__";
						spVAT += spRs.getString("ptVat") + "__";
						
						spSOLUONG_NHAN_DAT += formater.format(spRs.getDouble("SOLUONG")) + "__";
						spSOLUONG_NHAN_KHONGDAT += "0__";
						
						if( this.khoXuatId.trim().length() <=0){
							this.khoXuatId = spRs.getString("KHO_FK");
						}
						this.kbhId = spRs.getString("KBH_FK");
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

						spGIANHAP = spGIANHAP.substring(0, spGIANHAP.length() - 2);
						this.spGianhap = spGIANHAP.split("__");

						spTONKHO = spTONKHO.substring(0, spTONKHO.length() - 2);
						this.spTonkho = spTONKHO.split("__");

						spVAT = spVAT.substring(0, spVAT.length() - 2);
						this.spVat = spVAT.split("__");
						
						if( reload )
						{
							spSOLUONG = spSOLUONG.substring(0, spSOLUONG.length() - 2);
							this.spSoluong = spSOLUONG.split("__");
							
							spSOLUONG_NHAN_DAT = spSOLUONG_NHAN_DAT.substring(0, spSOLUONG_NHAN_DAT.length() - 2);
							this.spSoluongNHAN_DAT = spSOLUONG_NHAN_DAT.split("__");
							
							spSOLUONG_NHAN_KHONGDAT = spSOLUONG_NHAN_KHONGDAT.substring(0, spSOLUONG_NHAN_KHONGDAT.length() - 2);
							this.spSoluongNHAN_KHONGDAT = spSOLUONG_NHAN_KHONGDAT.split("__");
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
		
	}

	private void initSANPHAM() 
	{
		String query =   "select b.MA, b.TEN, c.DONVI, a.SOLUONGDAT, a.SOLUONGNHAN, a.DONGIA, 0 as avai, d.THUEXUAT as ptVat,  " + 
						 "	ISNULL( ( select SOLUONGNHAN from ERP_NHANHANG_SANPHAM where NHANHANG_FK = a.NHANHANG_FK and SANPHAM_FK = a.SANPHAM_FK and KHONHAN = '100004' ), 0) as soluongkhongDAT " + 
						 "from ERP_NHANHANG_SANPHAM a inner join SANPHAM b on a.SANPHAM_FK = b.PK_SEQ  " + 
						 "		inner join DONVIDOLUONG c on b.DVDL_FK = c.PK_SEQ " + 
						 "		inner join NGANHHANG d on b.NGANHHANG_FK = d.PK_SEQ " + 
						 "where a.NHANHANG_FK = '" + this.id + "' and a.KHONHAN != '100004' ";
		
		System.out.println("---INIT SP: " + query);
		ResultSet spRs = db.get(query);
		
		NumberFormat formater = new DecimalFormat("##,###,###");
		if(spRs != null)
		{
			try 
			{
				String spMA = "";
				String spTEN = "";
				String spDONVI = "";
				String spSOLUONG = "";
				String spGIANHAP = "";
				String spTONKHO = "";
				String spVAT = "";
				
				String spSOLUONG_NHAN_DAT = "";
				String spSOLUONG_NHAN_KHONGDAT = "";

				while(spRs.next())
				{
					spMA += spRs.getString("MA") + "__";
					spTEN += spRs.getString("TEN") + "__";
					spDONVI += spRs.getString("DONVI") + "__";
					spSOLUONG += formater.format(spRs.getDouble("SOLUONGDAT")) + "__";
					spGIANHAP += formater.format(spRs.getDouble("DONGIA")) + "__";
					spTONKHO += formater.format(spRs.getDouble("avai")) + "__";
					spVAT += spRs.getString("ptVat") + "__";
					
					spSOLUONG_NHAN_DAT += formater.format(spRs.getDouble("SOLUONGNHAN")) + "__";
					spSOLUONG_NHAN_KHONGDAT += formater.format(spRs.getDouble("soluongkhongDAT")) + "__";
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

					spTONKHO = spTONKHO.substring(0, spTONKHO.length() - 2);
					this.spTonkho = spTONKHO.split("__");

					spVAT = spVAT.substring(0, spVAT.length() - 2);
					this.spVat = spVAT.split("__");
					
					spSOLUONG_NHAN_DAT = spSOLUONG_NHAN_DAT.substring(0, spSOLUONG_NHAN_DAT.length() - 2);
					this.spSoluongNHAN_DAT = spSOLUONG_NHAN_DAT.split("__");
					
					spSOLUONG_NHAN_KHONGDAT = spSOLUONG_NHAN_KHONGDAT.substring(0, spSOLUONG_NHAN_KHONGDAT.length() - 2);
					this.spSoluongNHAN_KHONGDAT = spSOLUONG_NHAN_KHONGDAT.split("__");
				}
			} 
			catch (Exception e) 
			{
				System.out.println("115.Exception: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}

	public void init(String action) 
	{
		//( select KHO_FK from DONTRAHANG where PK_SEQ = a.TRAHANG_FK ) 
		String query= "";
		if(!action.equals("display")){
			query =  "select PK_SEQ, NGAYNHAN, TRANGTHAI, NCC_KH_FK, " + 
						" a.KHOCHOXULY_FK as KHO_FK," +
						" isnull(GhiChu, '') as ghichu, TRAHANG_FK, '' as TRAHANGDIENTA " +
						"from ERP_NHANHANG a where pk_seq = '" + this.id + "'";
		} else{
			query =  " select a.PK_SEQ, a.NGAYNHAN, a.TRANGTHAI, NCC_KH_FK,  a.KHOCHOXULY_FK " +
					" as KHO_FK, isnull(a.GhiChu, '') as ghichu,  CAST(b.pk_seq as varchar(10)) + ' [' + NGAYTRA + ']' " +
					" as TRAHANGDIENTA, a.TRAHANG_FK as TRAHANG_FK from ERP_NHANHANG a inner join DONTRAHANG b on a.TRAHANG_FK= b.PK_SEQ " +
					" where a.pk_seq = '" + this.id + "'";
		}
		System.out.println("____INIT NHAP KHO: " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					this.ngayyeucau = rs.getString("NGAYNHAN");
					this.ghichu = rs.getString("ghichu");
					this.khoXuatId = rs.getString("KHO_FK");
					
					this.khId = rs.getString("NCC_KH_FK");
					this.trahangId = rs.getString("TRAHANG_FK");
					this.trangthai = rs.getString("trangthai");
					this.trahangdienta = rs.getString("TRAHANGDIENTA");
				}
				rs.close();
				
				//INIT SO LUONG DAT
				query = "select sanpham_fk, (select MA from SANPHAM where pk_seq = a.sanpham_fk ) as spMA,  solo, soluong, a.NgayHetHan " +
						"from ERP_NHANHANG_SP_CHITIET a where NHANHANG_FK = '" + this.id + "' and kho_fk != '100004' ";
				System.out.println("---INIT SP: " + query);
				rs = db.get(query);
				if(rs != null)
				{
					Hashtable<String, String> sp_soluong = new Hashtable<String, String>();
					while(rs.next())
					{
						sp_soluong.put(rs.getString("spMA") + "__" + rs.getString("solo") + "__" + rs.getString("NgayHetHan")  , rs.getString("soluong") );
					}
					rs.close();
					
					this.sanpham_soluongNHAN_DAT = sp_soluong;
				}
				
				//INIT SO LUONG KHONG DAT
				query = "select sanpham_fk, (select MA from SANPHAM where pk_seq = a.sanpham_fk ) as spMA,  solo, soluong, a.NgayHetHan " +
						"from ERP_NHANHANG_SP_CHITIET a where NHANHANG_FK = '" + this.id + "' and kho_fk = '100004' ";
				System.out.println("---INIT SP: " + query);
				rs = db.get(query);
				if(rs != null)
				{
					Hashtable<String, String> sp_soluong = new Hashtable<String, String>();
					while(rs.next())
					{
						sp_soluong.put(rs.getString("spMA") + "__" + rs.getString("solo") + "__" + rs.getString("NgayHetHan")  , rs.getString("soluong") );
					}
					rs.close();
					
					this.sanpham_soluongNHAN_KHONGDAT = sp_soluong;
				}
				
			} 
			catch (Exception e) 
			{
				System.out.println("---LOI INIT: " + e.getMessage());
				e.printStackTrace();
			}
		}
		
		this.initSANPHAM();
		
		this.createRs();
		
	}

	public void DBclose() {
		
		try{
			
			if(khoNhanRs!=null){
				khoNhanRs.close();
			}
			
			this.db.shutDown();
			
		}catch(Exception er)
		{
			er.printStackTrace();
		}
	}
	
	public String getDateTime() 
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

	
	public String[] getSpSolo() {
		
		return this.spSolo;
	}

	
	public void setSpSolo(String[] spSolo) {
		
		this.spSolo = spSolo;
	}

	
	public String[] getSpNgaysanxuat() {
		
		return this.spNgaysanxuat;
	}

	
	public void setSpNgaysanxuat(String[] spNgaysanxuat) {
		
		this.spNgaysanxuat = spNgaysanxuat;
	}

	
	public String[] getSpTonkho() {

		return this.spTonkho;
	}


	public void setSpTonkho(String[] spTonkho) {
		
		this.spTonkho = spTonkho;
	}

	
	public String[] getSpBooked() {

		return this.spBooked;
	}


	public void setSpBooked(String[] spBooked) {
		
		this.spBooked = spBooked;
	}

	
	public ResultSet getSanphamRs() {

		return this.spRs;
	}


	public void setSanphamRs(ResultSet spRs) {
		
		this.spRs = spRs;
	}

	
	public String getKhoXuatId() {
		
		return this.khoXuatId;
	}

	
	public void setKhoXuatId(String khoxuattt) {
		
		this.khoXuatId = khoxuattt;
	}

	
	public ResultSet getKhoXuatRs() {
		
		return this.khoXuatRs;
	}

	
	public void setKhoXuatRs(ResultSet khoxuatRs) {
		
		this.khoXuatRs = khoxuatRs;
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

	
	public ResultSet getDvtRs() {

		return this.dvtRs;
	}


	public void setDvtRs(ResultSet dvtRs) {

		this.dvtRs = dvtRs;
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
	
	public String getLenhdieudong() 
	{
		return this.lenhdieudong;
	}

	public void setLenhdieudong(String lenhdieudong) 
	{
		this.lenhdieudong =lenhdieudong;
		
	}

	public String getLDDcua() 
	{
		return this.lddcua;
	}

	public void setLDDcua(String LDDcua) 
	{
		this.lddcua= LDDcua;
		
	}

	public String getLDDveviec() 
	{
		return this.lddveviec;
	}

	public void setLDDveviec(String LDDveviec) 
	{
		this.lddveviec= LDDveviec;
		
	}

	public String getNgaydieudong() 
	{
		return this.ngaylenhdieudong;
	}

	public void setNgaydieudong(String ngaydieudong) 
	{
		this.ngaylenhdieudong =ngaydieudong;
		
	}

	public String getNguoivanchuyen() 
	{
		return this.nguoivanchuyen;
	}

	public void setNguoivanchuyen(String nguoivanchuyen) 
	{
		this.nguoivanchuyen = nguoivanchuyen;
		
	}

	public String getPtvanchuyen() 
	{
		return this.ptvanchuyen;
	}

	public void setPtvanchuyen(String ptvanchuyen) 
	{
		this.ptvanchuyen = ptvanchuyen;
		
	}

	public String getSohopdong() 
	{
		return this.sohopdong;
	}

	public void setSohopdong(String sohopdong) 
	{
		this.sohopdong = sohopdong;
		
	}

	public String getNgayhopdong() 
	{
		return this.ngayhopdong;
	}

	public void setNgayhopdong(String ngayhopdong) 
	{
		this.ngayhopdong = ngayhopdong;
		
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

	
	public String getSoChungTu()
	{
		return sochungtu;
	}
	public void setSoChungTu(String sochungtu)
	{
		this.sochungtu=sochungtu;
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
	
	
	public Hashtable<String, String> getSanpham_Soluong() {
		
		return this.sanpham_soluong;
	}

	
	public void setSanpham_Soluong(Hashtable<String, String> sp_soluong) {
		
		this.sanpham_soluong = sp_soluong;
	}
	
	public ResultSet getSoloTheoSp(String spMa, String tongluong)
	{
		if(this.trahangId.trim().length() > 0 )
		{
			String query = " select SoLo, NgayHetHan, SOLUONG, 0 as AVAILABLE " + 
						   " from DONTRAHANG_SP where DONTRAHANG_FK = '" + this.trahangId + "' and sanpham_fk = ( select pk_seq from SANPHAM where ma = '" + spMa + "' ) ";
			return db.get(query);
		}
		
		return null;
	}
	
	String dungchungkenh;

	public String getDungchungkenh()
  {
  	return dungchungkenh;
  }

	public void setDungchungkenh(String dungchungkenh)
  {
  	this.dungchungkenh = dungchungkenh;
  }
	
	Hashtable<String, Integer> sp_sl;
	public Hashtable<String, Integer> getSp_Soluong() 
	{
		return this.sp_sl;
	}

	public void setSSp_Soluong(Hashtable<String, Integer> sp_sl) 
	{
		this.sp_sl = sp_sl;
	}
	
	public ResultSet getSpRs() 
	{
		return this.spRs;
	}

	public void setSpRs(ResultSet spRs) 
	{
		this.spRs = spRs;
	}
	
	String[] spVat;

	public String[] getSpVat()
	{
		return spVat;
	}

	public void setSpVat(String[] spVat)
	{
		this.spVat = spVat;
	}
	
	public String getLoaidonhang() {
		
		return this.loaidonhang;
	}

	
	public void setLoaidonhang(String loaidonhang) {
		
		this.loaidonhang = loaidonhang;
	}

	
	public String getTrahangId() {
		
		return this.trahangId;
	}

	
	public void setTrahangId(String trahangId) {
		
		this.trahangId = trahangId;
	}

	
	public ResultSet getTrahangRs() {
		
		return this.trahangRs;
	}

	
	public void setTrahangRs(ResultSet trahangRs) {
		
		this.trahangRs = trahangRs;
	}

	
	public String[] getSpSoluongNHAN_DAT() {
		
		return this.spSoluongNHAN_DAT;
	}

	
	public void setSpSoluongNHAN_DAT(String[] spSoluong) {
		
		this.spSoluongNHAN_DAT = spSoluong;
	}

	
	public String[] getSpSoluongNHAN_KHONGDAT() {
		
		return this.spSoluongNHAN_KHONGDAT;
	}

	
	public void setSpSoluongNHAN_KHONGDAT(String[] spSoluong) {
		
		this.spSoluongNHAN_KHONGDAT = spSoluong;
	}

	
	public Hashtable<String, String> getSanpham_SoluongNHAN_DAT() {
		
		return this.sanpham_soluongNHAN_DAT;
	}

	
	public void setSanpham_SoluongNHAN_DAT(Hashtable<String, String> sp_soluong) {
		
		this.sanpham_soluongNHAN_DAT = sp_soluong;
	}

	
	public Hashtable<String, String> getSanpham_SoluongNHAN_KHONGDAT() {
		
		return this.sanpham_soluongNHAN_KHONGDAT;
	}

	
	public void setSanpham_SoluongNHAN_KHONGDAT( Hashtable<String, String> sp_soluong) {
		
		this.sanpham_soluongNHAN_KHONGDAT = sp_soluong;
	}

	
	public ResultSet getSoloTheoSpNHAN_DAT(String spIds, String tongluong) 
	{
		if(this.trahangId.trim().length() > 0 )
		{
			String query = " select SoLo, NgayHetHan, SOLUONG as tuDEXUAT, 0 as AVAILABLE " + 
						   " from DONTRAHANG_SP where DONTRAHANG_FK = '" + this.trahangId + "' and sanpham_fk = ( select pk_seq from SANPHAM where ma = '" + spIds + "' ) ";
			
			System.out.print("SP NHAN DAT: " + query);
			return db.get(query);
		}
		
		return null;
	}

	
	public ResultSet getSoloTheoSpNHAN_KHONGDAT(String spIds, String tongluong) 
	{
		if(this.trahangId.trim().length() > 0 )
		{
			String query = " select SoLo, NgayHetHan, 0 as tuDEXUAT, 0 as AVAILABLE " + 
						   " from DONTRAHANG_SP where DONTRAHANG_FK = '" + this.trahangId + "' and sanpham_fk = ( select pk_seq from SANPHAM where ma = '" + spIds + "' ) ";
			return db.get(query);
		}
		
		return null;
	}
	
	Object loainhanvien;
	Object doituongId;
	public String getLoainhanvien() 
	{
		if( this.loainhanvien == null )
			return "";
		
		return this.loainhanvien.toString();
	}

	public void setLoainhanvien(Object loainhanvien) 
	{
		this.loainhanvien = loainhanvien;
	}
	
	public String getDoituongId() 
	{
		if( this.doituongId == null )
			return "";
		
		return this.doituongId.toString();
	}

	public void setDoituongId(Object doituongId) 
	{
		this.doituongId = doituongId;
	}

	public void updateSoLo_NgayHetHan(){
		try{
			this.db.getConnection().setAutoCommit(false);
			for(int i=0; i< this.spMa.length; i++){
				List<String> listsolo = this.sanpham_solo.get(spMa[i]);
				List<String> listngayhethan = this.sanpham_solo_ngayhethan.get(spMa[i]);
				List<String> listsologoc = this.sanpham_sologoc.get(spMa[i]);
			
				// cập nhật lại db 
				if( listsolo.size()> 0){
					
					for(int j=0; j< listsolo.size(); j++){
						String query = "update DONTRAHANG_SP set SoLo = ?, NgayHetHan =? where DONTRAHANG_FK=? " +
								" and SANPHAM_FK= (select pk_seq from SANPHAM where MA= ?) and SOLO = ?";
						PreparedStatement pre = this.db.getConnection().prepareStatement(query);
						pre.setString(1,listsolo.get(j));
						pre.setString(2,listngayhethan.get(j));
						pre.setString(3,this.trahangId);
						pre.setString(4,this.spMa[i]);
						pre.setString(5,listsologoc.get(j));
						
						int k = pre.executeUpdate();
						if( k !=1){
							System.out.println("Đổi tên, ngày hết hạn không thành công:"+ listsolo.get(j)
									+"-" +listngayhethan.get(j));
							this.db.getConnection().rollback();
						}
						
					}
				
				}
			}
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		} catch(Exception ex){
			try {
				ex.printStackTrace();
				this.db.getConnection().rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ex.printStackTrace();
		} 
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
