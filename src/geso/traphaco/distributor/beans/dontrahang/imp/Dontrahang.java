package geso.traphaco.distributor.beans.dontrahang.imp;

import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.beans.dontrahang.IDontrahang;
import geso.traphaco.distributor.db.sql.dbutils;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

public class Dontrahang implements IDontrahang, Serializable
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

	String lenhdieudong, lddcua, lddveviec, ngaylenhdieudong, sohopdong,ngayhopdong, nguoivanchuyen, ptvanchuyen, TienBvat, Tienck, Tienvat, TienAvat;
	
	ResultSet spRs;
	String npp_duocchon_id;
	String tdv_dangnhap_id;
	
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
	
	String nppId;
	String nppTen;
	String sitecode;
	String sochungtu;
	String dungchung;
	Hashtable<String, String> sanpham_soluong;
	
	String tichluyId;
	ResultSet tichluyRs;
	
	dbutils db;
	Utility util;
	
	String loaidonhang;
	
	public Dontrahang()
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
		this.TienBvat= "";
		this.Tienck = "";
		this.Tienvat = "";
		this.TienAvat = "";
		this.tdv_dangnhap_id = "";
		this.npp_duocchon_id = "";
		
		this.sanpham_soluong = new Hashtable<String, String>();
		
		this.sochungtu ="";
		this.dungchung = "";
		this.db = new dbutils();
		this.util = new Utility();
		this.loaidonhang = "";
		this.tichluyId = "";
	}
	
	public Dontrahang(String id)
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
		this.Tienck = "";
		this.Tienvat = "";
		
		this.sanpham_soluong = new Hashtable<String, String>();
		
		this.sochungtu ="";
		this.dungchung = "";
		this.TienBvat = "";
		this.TienAvat = "";
		this.tdv_dangnhap_id = "";
		this.npp_duocchon_id = "";
		
		this.db = new dbutils();
		this.util = new Utility();
		this.loaidonhang = "";
		this.tichluyId = "";
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

	
	public boolean createNK() 
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
		
		if(this.sanpham_soluong == null)
		{
			this.msg = "Vui lòng kiểm tra lại số lô xuất";
			return false;
		}
		
		try
		{
			String khachhang_fk = this.khId;
			String npp_tra_fk = this.khId;
			if(this.khId.substring(0,2).equals("KH")){
				khachhang_fk = this.khId.substring(2,this.khId.length());
				npp_tra_fk = "NULL";
				this.loaidonhang="1";
			}
			else{
				khachhang_fk = "NULL";
				npp_tra_fk = this.khId.substring(3,this.khId.length());
				this.loaidonhang="0";
			}
			
			db.getConnection().setAutoCommit(false);
			String query =  "	INSERT INTO [DONTRAHANG]([NGAYTRA],[NGUOITAO],[NGUOISUA],[TRANGTHAI],[NPP_FK],[NPP_TRA_FK],[KHACHHANG_FK],[VAT],[SOTIENAVAT],[SOTIENBVAT],[CHIETKHAU],[KBH_FK],[KHO_FK],SoChungTu,SoHoaDon,KyHieu,GhiChu, loaidonhang)" +
							"	select 	'"+this.ngayyeucau+"','"+this.userId+"','"+this.userId+"',0,'"+this.nppId+"', " + npp_tra_fk + " , " + khachhang_fk + ", "+this.Tienvat+","+this.TienAvat+","+this.TienBvat+", "+this.Tienck+", '"+this.kbhId+"','"+this.khoXuatId+"','"+this.sochungtu+"','"+this.sochungtu+"','',N'"+this.ghichu+"', '" + this.loaidonhang + "' ";
			
			System.out.println("1.Insert CK: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới DONTRAHANG " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "select SCOPE_IDENTITY() as hdId";
			ResultSet rs = db.get(query);
			rs.next();
			this.id = rs.getString("hdId");
			rs.close();
			
			String _dungchungkenh = "0";
			if(this.loaidonhang.equals("0"))
			{
				query =  "select isnull(dungchungkenh, 0) as dungchungkenh from NHAPHANPHOI where pk_seq = '" + npp_tra_fk + "' ";
				ResultSet rsKENH = db.get(query);
				if(rsKENH != null)
				{
					try 
					{
						if(rsKENH.next())
						{
							_dungchungkenh = rsKENH.getString("dungchungkenh");
						}
						rsKENH.close();
					} 
					catch (Exception e) {e.printStackTrace(); }
				}
			}
			else //Khách hàng trả về MTV thì không quan tâm tới nhóm kên
			{
				this.dungchungkenh = "1";
				_dungchungkenh = this.dungchungkenh;
			}
			
			String nhomkenhId = "(select nk_fk from NHOMKENH_KENHBANHANG where kbh_fk = '" + this.kbhId + "')";
			if(_dungchungkenh.equals("1"))
			{
				nhomkenhId = "100000";
			}
			
			for(int i = 0; i < spMa.length; i++)
			{
				if(spMa[i].trim().length() > 0 && spSoluong[i].trim().length() > 0 )
				{
					query =  "select sp.ten, dv.donvi, case when sp.dvdl_fk != dv.pk_seq   " +
						   "	then ISNULL( ( select soluong2 / soluong1 from QUYCACH where SANPHAM_FK = sp.PK_SEQ and DVDL1_FK = sp.DVDL_FK and DVDL2_FK = dv.pk_seq ), -1 ) else 1 end as quycach   "  +
						   "from SANPHAM sp, DONVIDOLUONG dv " +
						   "where sp.MA = '" + spMa[i].trim() + "' and dv.donvi = N'" + spDonvi[i].trim() + "'   ";
					
					System.out.println("-----CHECK QUY CACH: " + query );
					rs = db.get(query);
					if(rs.next())
					{
						if(rs.getDouble("quycach") <= 0)
					{
							this.msg = "Sản phẩm ( " + rs.getString("ten") + " ) với đơn vị đặt ( " + rs.getString("donvi") + " ) chưa thiết lập quy cách trong DLN ";
							rs.close();
							db.getConnection().rollback();
							return false;
						}
					}
					rs.close();
					
					if(this.sanpham_soluong != null)
					{
						Enumeration<String> keys = this.sanpham_soluong.keys();
						double totalCT = 0;
						
						while(keys.hasMoreElements())
						{
							String key = keys.nextElement();
							
							if(key.startsWith( spMa[i]) )
							{
								String[] _sp = key.split("__");
								
								String _soluongCT = "0"; 
								if(this.sanpham_soluong.get(key) != null)
								{
									_soluongCT = this.sanpham_soluong.get(key).replaceAll(",", "");
								}
								
								totalCT += Double.parseDouble(_soluongCT);
								
								if(!_soluongCT.equals("0"))
								{
									//CHECK TON KHO
									/*if(this.loaidonhang.equals("0"))
									{
										query = "select AVAILABLE, NGAYHETHAN from NHAPP_KHO_CHITIET " +
												"where KHO_FK = '" + this.khoXuatId + "' and NPP_FK = '" + npp_tra_fk + "' and NHOMKENH_FK = " + nhomkenhId + " and SOLO = '" + _sp[1] + "' and NGAYHETHAN = '" + _sp[2] + "' " +
												"	and SANPHAM_FK = ( select pk_seq from SANPHAM where ma = '" + spMa[i] + "' ) ";
										
										System.out.println("_____"+query);
										
										ResultSet rsTK = db.get(query);
										double avai = 0;
										String ngayhethan = "";
										if(rsTK.next())
										{
											avai = rsTK.getDouble("AVAILABLE");
											ngayhethan = rsTK.getString("NGAYHETHAN");
										}
										rsTK.close();
										
										if( Double.parseDouble(_soluongCT) > avai )
										{
											this.msg = "Sản phẩm (" + spTen[i] + ") với số lô (" + _sp[1] + "), số lượng xuất (" + _soluongCT + ") còn tối đa (" + avai + "). ";
											db.getConnection().rollback();
											return false;
										}
									}*/
									
									query = "insert into DONTRAHANG_SP(DONTRAHANG_FK, SANPHAM_FK, SoLo, NgayHetHan, SOLUONG, DONVI, DONGIA, ptVat, nhomkenh_fk) " +
											"select '"+this.id+"', pk_seq, N'" + _sp[1] + "', '" + _sp[2] + "', '" + _soluongCT.replaceAll(",", "") + "',N'"+spDonvi[i]+"','"+spGianhap[i].replaceAll(",", "")+"','"+spVat[i]+"', " + nhomkenhId + " " +
											"from SANPHAM where MA = '" + spMa[i] + "' ";
									System.out.println("1.2.Insert YCCK - SP - CT: " + query);
									if(db.updateReturnInt(query)!=1)
									{
										this.msg = "Khong the tao moi DONTRAHANG_SP: " + query;
										db.getConnection().rollback();
										return false;
									}
									
									/*if(this.loaidonhang.equals("0"))
									{
										query = "Update NHAPP_KHO_CHITIET set booked = booked + '" + _soluongCT.replaceAll(",", "") + "', AVAILABLE = AVAILABLE - '" + _soluongCT.replaceAll(",", "") + "' " +
												"where KHO_FK = '" + this.khoXuatId + "' and NPP_FK = '" + npp_tra_fk + "' and NHOMKENH_FK = " + nhomkenhId + " and SOLO = '" + _sp[1] + "' and SANPHAM_FK = ( select pk_seq from SANPHAM where MA = '" + spMa[i] + "' )  and NgayHetHan='"+ _sp[2] +"' ";
										System.out.println("---CAP NHAT BOOKED: " + query );
										if(db.updateReturnInt(query)!=1)
										{
											this.msg = "Khong the cap nhat NHAPP_KHO_CHITIET: " + query;
											db.getConnection().rollback();
											return false;
										}
										
										query = "Update NHAPP_KHO set booked = booked + '" + _soluongCT.replaceAll(",", "") + "', AVAILABLE = AVAILABLE - '" + _soluongCT.replaceAll(",", "") + "' " +
												"where KHO_FK = '" + this.khoXuatId + "' and NPP_FK = '" + npp_tra_fk + "' and NHOMKENH_FK = " + nhomkenhId + " and SANPHAM_FK = ( select pk_seq from SANPHAM where MA = '" + spMa[i] + "' )  ";
										System.out.println("---CAP NHAT BOOKED KHO TONG: " + query );
										if(db.updateReturnInt(query)!=1)
										{
											this.msg = "Khong the cap nhat NHAPP_KHO: " + query;
											db.getConnection().rollback();
											return false;
										}
									}*/
								}
							}
						}
						//NEU TONG SO LUONG CT MA KHONG BANG TONG LUONG XUAT THI KO CHO LUU
						if(totalCT != Double.parseDouble(spSoluong[i].replaceAll(",", "").trim()) )
						{
							this.msg = "Tổng xuất theo lô của sản phẩm ( " + spTen[i] + " ) ( " + totalCT + " ) phải bằng tổng số lượng xuất ( " + spSoluong[i] + " ) ";
							db.getConnection().rollback();
							return false;
						}
					}
				}
			}
			
			String tichluy_fk = this.tichluyId.trim().length() <= 0 ? "" : this.tichluyId;
			if( tichluy_fk.trim().length() > 0 )
			{
				query = "Insert DONTRAHANG_TICHLUY(dontrahang_fk, tichluy_fk)  " +
						"select '" + this.id + "', pk_seq from TIEUCHITHUONGTL where pk_seq in ( " + tichluy_fk + " )  ";
				if(!db.update(query))
				{
					db.getConnection().rollback();
					this.msg = "4.Khong the tao moi 'DONTRAHANG_TICHLUY', " + query;
					return false; 
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

	public boolean updateNK() 
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
		
	/*	if( this.sochungtu.trim().length() <= 0 )
		{
			this.msg = "Vui lòng nhập số chứng từ";
			return false;
		}*/
		
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
		
		if(this.sanpham_soluong == null)
		{
			this.msg = "Vui lòng kiểm tra lại số lô xuất";
			return false;
		}
		
		try
		{
			db.getConnection().setAutoCommit(false);
			String query = "";
			
			/*String khachhang_fk = this.khId;
			String npp_tra_fk = this.khId;*/
			/*if(this.loaidonhang.equals("0"))
				khachhang_fk = "NULL";
			else
				npp_tra_fk = "NULL";*/
			String khachhang_fk = this.khId;
			String npp_tra_fk = this.khId;
			if(this.khId.substring(0,2).equals("KH")){
				khachhang_fk = this.khId.substring(2,this.khId.length());
				npp_tra_fk = "NULL";
				this.loaidonhang="1";
			}
			else{
				khachhang_fk = "NULL";
				npp_tra_fk = this.khId.substring(3,this.khId.length());
				this.loaidonhang="0";
			}
			
			
			String _dungchungkenh = "0";
			if(this.loaidonhang.equals("0"))
			{
				query =  "select isnull(dungchungkenh, 0) as dungchungkenh from NHAPHANPHOI where pk_seq = '" + npp_tra_fk + "' ";
				ResultSet rsKENH = db.get(query);
				if(rsKENH != null)
				{
					try 
					{
						if(rsKENH.next())
						{
							_dungchungkenh = rsKENH.getString("dungchungkenh");
						}
						rsKENH.close();
					} 
					catch (Exception e) {e.printStackTrace(); }
				}
			}
			else //Khách hàng trả về MTV thì không quan tâm tới nhóm kên
			{
				this.dungchungkenh = "1";
				_dungchungkenh = this.dungchungkenh;
			}
			
			String nhomkenhId = "(select nk_fk from NHOMKENH_KENHBANHANG where kbh_fk = '" + this.kbhId + "')";
			if(_dungchungkenh.equals("1"))
			{
				nhomkenhId = "100000";
			}
			
				
			//TANG KHO NGUOC LAI
/*			if(this.loaidonhang.equals("0"))
			{
				query = " update kho set kho.AVAILABLE = kho.AVAILABLE + CT.tongxuat, " +
						" 			   kho.BOOKED = kho.BOOKED - CT.tongxuat " +
						" from " +
						" ( " +
						" 	select a.kho_fk as kho_fk, a.npp_tra_fk as npp_fk, b.nhomkenh_fk, b.sanpham_fk, b.solo, SUM(b.soluong) as tongxuat,b.NgayHetHan  " +
						" 	from DonTraHang a inner join DONTRAHANG_SP b on a.pk_seq = b.dontrahang_fk " +
						" 	where dontrahang_fk = '" + this.id + "' " +
						" 	group by a.kho_fk, a.npp_tra_fk, b.nhomkenh_fk, b.solo, b.sanpham_fk,b.NgayHetHan " +
						" ) " +
						" CT inner join NHAPP_KHO_CHITIET kho on CT.kho_fk = kho.KHO_FK  " +
						" 	and CT.sanpham_fk = kho.SANPHAM_FK and CT.solo = kho.SOLO and CT.NPP_FK = kho.npp_fk and CT.nhomkenh_fk = kho.nhomkenh_fk  and ct.NgayHetHan=kho.NgayHetHan ";
				System.out.println("---TANG KHO NGUOC LAI: " + query );
				if(db.updateReturnInt(query)<=0)
				{
					this.msg = "Không thể cập nhật NHAPP_KHO_CHITIET " + query;
					db.getConnection().rollback();
					return false;
				}
				
				query = " update kho set kho.AVAILABLE = kho.AVAILABLE + CT.tongxuat, " +
						" 			   kho.BOOKED = kho.BOOKED - CT.tongxuat " +
						" from " +
						" ( " +
						" 	select a.kho_fk as kho_fk, a.npp_tra_fk as npp_fk, b.nhomkenh_fk, b.sanpham_fk, SUM(b.soluong) as tongxuat  " +
						" 	from DonTraHang a inner join DONTRAHANG_SP b on a.pk_seq = b.dontrahang_fk " +
						" 	where dontrahang_fk = '" + this.id + "' " +
						" 	group by a.kho_fk, a.npp_tra_fk, b.nhomkenh_fk, b.sanpham_fk " +
						" ) " +
						" CT inner join NHAPP_KHO kho on CT.kho_fk = kho.KHO_FK  " +
						" 	and CT.sanpham_fk = kho.SANPHAM_FK and CT.NPP_FK = kho.npp_fk and CT.nhomkenh_fk = kho.nhomkenh_fk ";
				System.out.println("---TANG KHO NGUOC LAI 2: " + query );
				if(db.updateReturnInt(query)<=0)
				{
					this.msg = "Không thể cập nhật NHAPP_KHO " + query;
					db.getConnection().rollback();
					return false;
				}
			}*/
			
			query = "delete DONTRAHANG_SP where dontrahang_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật DONTRAHANG_SP " + query;
				db.getConnection().rollback();
				return false;
			}		
			

			 query = " Update DonTraHang set  NGAYTRA='"+this.ngayyeucau+"',NguoiSua='"+this.userId+"',KBH_FK='"+this.kbhId+"',kho_Fk='"+this.khoXuatId+"',NPP_TRA_FK=" + npp_tra_fk + ", KHACHHANG_FK = " + khachhang_fk + ", SoChungTu='"+this.sochungtu+"',SoHoaDon='"+this.sochungtu+"',KyHieu='' "+					
					 ",Modified_Date=getdate(),GhiChu=N'"+this.ghichu+"', CHIETKHAU = "+this.Tienck+", SOTIENAVAT = "+this.TienAvat+", VAT ="+this.Tienvat+", SOTIENBVAT = "+this.TienBvat+" where pk_seq = '" + this.id + "' and trangthai in (0,1) ";

			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật DonTraHang " + query;
				db.getConnection().rollback();
				return false;
			}
			
			for(int i = 0; i < spMa.length; i++)
			{
				if(spMa[i].trim().length() > 0 && spSoluong[i].trim().length() > 0 )
				{
					query =    "select sp.ten, dv.donvi, case when sp.dvdl_fk != dv.pk_seq   " +
						   "	then ISNULL( ( select soluong2 / soluong1 from QUYCACH where SANPHAM_FK = sp.PK_SEQ and DVDL1_FK = sp.DVDL_FK and DVDL2_FK = dv.pk_seq ), -1 ) else 1 end as quycach   "  +
						   "from SANPHAM sp, DONVIDOLUONG dv " +
						   "where sp.MA = '" + spMa[i].trim() + "' and dv.donvi = N'" + spDonvi[i].trim() + "'   ";
					
					System.out.println("-----CHECK QUY CACH: " + query );
					ResultSet		rs = db.get(query);
					if(rs.next())
					{
						if(rs.getDouble("quycach") <= 0)
					{
							this.msg = "Sản phẩm ( " + rs.getString("ten") + " ) với đơn vị đặt ( " + rs.getString("donvi") + " ) chưa thiết lập quy cách trong DLN ";
							rs.close();
							db.getConnection().rollback();
							return false;
						}
					}
					rs.close();
					
					if(this.sanpham_soluong != null)
					{
						Enumeration<String> keys = this.sanpham_soluong.keys();
						double totalCT = 0;
						
						while(keys.hasMoreElements())
						{
							String key = keys.nextElement();
							
							if(key.startsWith( spMa[i]) )
							{
								String[] _sp = key.split("__");
								
								String _soluongCT = "0"; 
								if(this.sanpham_soluong.get(key) != null)
								{
									_soluongCT = this.sanpham_soluong.get(key).replaceAll(",", "");
								}
								
								totalCT += Double.parseDouble(_soluongCT);
								
								if(!_soluongCT.equals("0"))
								{
									//CHECK TON KHO
									/*if(this.loaidonhang.equals("0"))
									{
										query = "select AVAILABLE, NGAYHETHAN from NHAPP_KHO_CHITIET " +
												"where KHO_FK = '" + this.khoXuatId + "' and NPP_FK = '" + npp_tra_fk + "' and NHOMKENH_FK = " + nhomkenhId + " and SOLO = '" + _sp[1] + "' and NGAYHETHAN = '" + _sp[2] + "' " +
												"	and SANPHAM_FK = ( select pk_seq from SANPHAM where ma = '" + spMa[i] + "' ) ";
										
										System.out.println("_____"+query);
										
										ResultSet rsTK = db.get(query);
										double avai = 0;
										String ngayhethan = "";
										if(rsTK.next())
										{
											avai = rsTK.getDouble("AVAILABLE");
											ngayhethan = rsTK.getString("NGAYHETHAN");
										}
										rsTK.close();
										
										if( Double.parseDouble(_soluongCT) > avai )
										{
											this.msg = "Sản phẩm (" + spTen[i] + ") với số lô (" + _sp[1] + "), số lượng xuất (" + _soluongCT + ") còn tối đa (" + avai + "). ";
											db.getConnection().rollback();
											return false;
										}
									}*/
									
									query = "insert into DONTRAHANG_SP(DONTRAHANG_FK,SANPHAM_FK,SoLo,NgayHetHan,SOLUONG,DONVI,DONGIA,ptVat, nhomkenh_fk) " +
											"select '"+this.id+"', pk_seq, N'" + _sp[1] + "', '" + _sp[2] + "', '" + _soluongCT.replaceAll(",", "") + "',N'"+spDonvi[i]+"','"+spGianhap[i].replaceAll(",", "")+"','"+spVat[i]+"', " + nhomkenhId + " " +
											"from SANPHAM where MA = '" + spMa[i] + "' ";
									System.out.println("1.2.Insert YCCK - SP - CT: " + query);
									if(db.updateReturnInt(query)!=1)
									{
										this.msg = "Khong the tao moi DONTRAHANG_SP: " + query;
										db.getConnection().rollback();
										return false;
									}
									
									/*if(this.loaidonhang.equals("0"))
									{
										query = "Update NHAPP_KHO_CHITIET set booked = booked + '" + _soluongCT.replaceAll(",", "") + "', AVAILABLE = AVAILABLE - '" + _soluongCT.replaceAll(",", "") + "' " +
												"where KHO_FK = '" + this.khoXuatId + "' and NPP_FK = '" + npp_tra_fk + "' and NHOMKENH_FK = " + nhomkenhId + " and SOLO = '" + _sp[1] + "' and SANPHAM_FK = ( select pk_seq from SANPHAM where MA = '" + spMa[i] + "' )  and NgayHetHan = '" + _sp[2] + "' ";
										System.out.println("---CAP NHAT BOOKED: " + query );
										if(db.updateReturnInt(query)!=1)
										{
											this.msg = "Khong the cap nhat NHAPP_KHO_CHITIET: " + query;
											db.getConnection().rollback();
											return false;
										}
										query = "Update NHAPP_KHO set booked = booked + '" + _soluongCT.replaceAll(",", "") + "', AVAILABLE = AVAILABLE - '" + _soluongCT.replaceAll(",", "") + "' " +
												"where KHO_FK = '" + this.khoXuatId + "' and NPP_FK = '" + npp_tra_fk + "' and NHOMKENH_FK = " + nhomkenhId + " and SANPHAM_FK = ( select pk_seq from SANPHAM where MA = '" + spMa[i] + "' )  ";
										System.out.println("---CAP NHAT BOOKED KHO TONG: " + query );
										if(db.updateReturnInt(query)!=1)
										{
											this.msg = "Khong the cap nhat NHAPP_KHO: " + query;
											db.getConnection().rollback();
											return false;
										}
									}*/
								}
							}
						}
						//NEU TONG SO LUONG CT MA KHONG BANG TONG LUONG XUAT THI KO CHO LUU
						if(totalCT != Double.parseDouble(spSoluong[i].replaceAll(",", "").trim()) )
						{
							this.msg = "Tổng xuất theo lô của sản phẩm ( " + spTen[i] + " ) ( " + totalCT + " ) phải bằng tổng số lượng xuất ( " + spSoluong[i] + " ) ";
							db.getConnection().rollback();
							return false;
						}
					}
				}
			}
			
			
			//CHECK SOLO NEU DA CO KHONG DUOC KHAC NGAY HET HAN
			query = "  select TH.TEN, TH.SoLo, TH.NgayHetHan  " + 
					"  from NHAPP_KHO_CHITIET kho inner join " + 
					"  ( " + 
					"  	select a.NPP_FK, a.KHO_FK, 100000 as nhomkenhId, b.SANPHAM_FK, b.SoLo, b.NgayHetHan, c.TEN " + 
					"  	from DONTRAHANG a inner join DONTRAHANG_SP b on a.PK_SEQ = b.DONTRAHANG_FK " + 
					"  			inner join SANPHAM c on b.SANPHAM_FK = c.PK_SEQ " + 
					"  	where a.PK_SEQ = '"+this.id+"' " + 
					"  ) " + 
					"  TH on kho.NPP_FK = TH.NPP_FK and kho.KHO_FK = TH.KHO_FK " + 
					"  		and kho.nhomkenh_fk = TH.nhomkenhId and kho.SANPHAM_FK = TH.SANPHAM_FK  " + 
					"  		and kho.solo = TH.SoLo and kho.NGAYHETHAN != isnull(TH.NgayHetHan, '') ";
			ResultSet rs = db.get(query);
			String ngayhethan_khonghople = "";
			if( rs != null )
			{
				while( rs.next() )
				{
					ngayhethan_khonghople += rs.getString("TEN") + " - Số lô: " + rs.getString("SoLo") + " \n";
				}
				rs.close();
			}
			
			if( ngayhethan_khonghople.trim().length() > 0 )
			{
				this.msg = "Số lô của các sản phẩm sau có ngày hết hạn không hợp lệ: " + ngayhethan_khonghople;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete DONTRAHANG_TICHLUY where dontrahang_fk = '" + this.id + "' ";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				this.msg = "3.Khong the cap nhat 'DONTRAHANG_TICHLUY', " + query;
				return false; 
			}
			
			String tichluy_fk = this.tichluyId.trim().length() <= 0 ? "" : this.tichluyId;
			if( tichluy_fk.trim().length() > 0 )
			{
				query = "Insert DONTRAHANG_TICHLUY(dontrahang_fk, tichluy_fk)  " +
						"select '" + this.id + "', pk_seq from TIEUCHITHUONGTL where pk_seq in ( " + tichluy_fk + " )  ";
				if(!db.update(query))
				{
					db.getConnection().rollback();
					this.msg = "4.Khong the tao moi 'DONTRAHANG_TICHLUY', " + query;
					return false; 
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
		this.getNppInfo();
		System.out.println("---NPP ID: " + this.nppId);
		
		String query = "select PK_SEQ, TEN from KHO where trangthai = '1' and pk_seq in " + this.util.quyen_kho(this.userId) ;
		this.khoXuatRs = db.get(query);
		
		
		//query = " select PK_SEQ,TEN from NHAPHANPHOI where  PK_SEQ in (select tructhuoc_fk from NHAPHANPHOI where PK_SEQ='"+this.nppId+"') ";
		//System.out.println("___"+query);
		/*if( this.loaidonhang.equals("0") ) 
		{
			query = "	select PK_SEQ, isnull(maFAST,'') + '-' + TEN as TEN  " +
					"	from NHAPHANPHOI where TRANGTHAI = '1' and tructhuoc_fk = '" + this.nppId + "' ";
		}
		else if( this.loaidonhang.equals("1") ) 
		{
			query = "select PK_SEQ, isnull(maFAST,'') + '-' + TEN as TEN " +
					"	from KHACHHANG where TRANGTHAI = '1' and KBH_FK in ( select kbh_fk from NHOMKENH_KENHBANHANG where nk_fk = '100000' ) and npp_fk = '" + this.nppId + "' ";
		}
		else
		{
			query = "select PK_SEQ, isnull(maFAST,'') + '-' + TEN as TEN " +
					"	from KHACHHANG where TRANGTHAI = '1' and KBH_FK in ( select kbh_fk from NHOMKENH_KENHBANHANG where nk_fk = '100001' ) and npp_fk = '" + this.nppId + "' ";
		}*/
		
		
		query = " select  'KH'+ CAST(pk_seq AS VARCHAR(10)) as pk_seq, isnull(maFAST,'') + '-' + TEN as TEN " +
				" from KHACHHANG where TRANGTHAI = '1' and npp_fk = '" + this.nppId + "' ";
		
		if(this.kbhId != null)
		{
			if(this.kbhId.trim().length() > 0)
				query += " and PK_SEQ in ( select khachhang_fk from KHACHHANG_KENHBANHANG where kbh_fk = '" + this.kbhId + "' )  ";
		}
		
		query += util.getPhanQuyen_TheoNhanVien("KHACHHANG", "KHACHHANG", "pk_seq", this.getLoainhanvien(), this.getDoituongId() );
		
		query += " union SELECT   'NPP'+ CAST(pk_seq AS VARCHAR(10)),isnull(maFAST,'') + ' - '  + TEN AS TEN   FROM NHAPHANPHOI ";
		
		System.out.println("Du lieu : "+query);
		this.khRs = db.get(query);
		
		this.dvtRs = db.getScrol("select PK_SEQ, DONVI from DONVIDOLUONG where trangthai = '1' ");
		
		//CHECK DUNG CHUNG KENH HAY KHONG
		this.dungchungkenh = "1";
		/*query =  "select isnull(dungchungkenh, 0) as dungchungkenh from NHAPHANPHOI where pk_seq = '" + this.nppId + "' ";
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
		}*/
	
		query = "select PK_SEQ, TEN from KENHBANHANG where trangthai = '1' ";
		if(this.khId != null )
		{
			if(this.khId.trim().length() > 0 )
			{
				
				 
				String khachhang_fk = this.khId;
				String npp_tra_fk = this.khId;
				if(this.khId.substring(0,2).equals("KH")){
					khachhang_fk = this.khId.substring(2,this.khId.length());
					npp_tra_fk = "NULL";
					this.loaidonhang="1";
					query += " and PK_SEQ IN (select kbh_fk FROM KHACHHANG_KENHBANHANG where khachhang_fk = "+khachhang_fk+")";
					ResultSet rs = db.get(query);
					if( rs != null )
					{
						try
						{
							if( rs.next() )
							{
								this.kbhId = rs.getString("PK_SEQ");
							}else{
								query = "select PK_SEQ, TEN from KENHBANHANG where trangthai = '1' ";
							}
							rs.close();
						} 
						catch (Exception e) { }
					}
					
				}
				else{
					khachhang_fk = "NULL";
					npp_tra_fk = this.khId.substring(3,this.khId.length());
					this.loaidonhang="0";
					
					query += " and PK_SEQ IN (select kbh_fk FROM nhapp_kbh where npp_fk = "+npp_tra_fk+")";
					ResultSet rs = db.get(query);
					if( rs != null )
					{
						try
						{
							if( rs.next() )
							{
								this.kbhId = rs.getString("PK_SEQ");
								
							}else{
								query = "select PK_SEQ, TEN from KENHBANHANG where trangthai = '1' ";
							}
							rs.close();
						} 
						catch (Exception e) { }
					}
				}
			}
		}
		
		System.out.println(query);
		this.kbhRs = db.getScrol(query);
		
		query = "select PK_SEQ, SCHEME as diengiai from TIEUCHITHUONGTL where TRANGTHAI = '1' order by NAM desc";
		this.tichluyRs = db.get(query);
			
	}

	private void initSANPHAM() 
	{
		String kenhId="100025";
		if(!this.dungchungkenh.equals("1"))
		{
			 kenhId="(select KBH_FK from DonTraHang where PK_SEQ= '" + this.id + "')";
		}
		
		String query =  
			"	 select b.MA, b.TEN, DV.donvi,( a.SOLUONG) as soluong, a.dongia, 0 as chietkhau,  "+
			"		ISNULL(b.trongluong, 0) as trongluong, ISNULL(b.thetich, 0) as thetich ,       " +
			"	a.SOLUONG + ISNULL( ( select sum(available)  from NHAPP_KHO   where KHO_FK = (select Kho_fk from DonTraHang where PK_SEQ= '"+this.id+"') and sanpham_fk = b.pk_seq  and NPP_FK = (select NPP_FK from DonTraHang where PK_SEQ= '"+this.id+"') " + 
			"			and KBH_FK ="+kenhId+"   ), 0 ) as avai,a.ptVat   "+  
			"	 from   "+ 
			"	 (  "+
			"			 select a.DONTRAHANG_FK,sum(a.SOLUONG)  as SoLuong,SANPHAM_FK,isnull(ptVat,0) as ptVat,DONGIA  "+
			"			 from DONTRAHANG_SP a  "+
			"			 where a.dontrahang_Fk = '"+this.id+"'   "+
			"			 group by DONTRAHANG_FK,SANPHAM_FK,a.DONGIA,ptvat  "+  
			"	 ) a inner Join SanPham b on a.SANPHAM_FK = b.PK_SEQ       "+		
			"		INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = b.dvdl_Fk  ";       
		
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
				
				while(spRs.next())
				{
					spMA += spRs.getString("MA") + "__";
					spTEN += spRs.getString("TEN") + "__";
					spDONVI += spRs.getString("DONVI") + "__";
					spSOLUONG += formater.format(spRs.getDouble("SOLUONG")) + "__";
					spGIANHAP += formater.format(spRs.getDouble("DONGIA")) + "__";
					spTONKHO += formater.format(spRs.getDouble("avai")) + "__";
					spVAT += spRs.getString("ptVat") + "__";
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
					
				}
			} 
			catch (Exception e) 
			{
				System.out.println("115.Exception: " + e.getMessage());
				e.printStackTrace();
			}
		}
		
	}

	public void init() 
	{
		String query =  "select PK_SEQ,NGAYTRA,TRANGTHAI,NPP_FK,NCC_FK,DVKD_FK,KBH_FK,KHO_FK,NPP_TRA_FK, KHACHHANG_FK, GhiChu,SoHoaDon,KyHieu, loaidonhang " +
						"from DONTRAHANg where pk_seq = '" + this.id + "'";
		System.out.println("____INIT NHAP KHO: " + query);
		ResultSet rs = db.get(query);
		/*if(rs != null)*/
		{
			try 
			{
				if(rs.next())
				{
					this.ngayyeucau = rs.getString("NGAYTRA");
					this.ghichu = rs.getString("ghichu");
					this.khoXuatId = rs.getString("KHO_FK");
					
					if(rs.getString("loaidonhang").equals("0"))
						this.khId = "NPP"+rs.getString("NPP_TRA_FK");
					else
						this.khId = "KH"+rs.getString("KHACHHANG_FK");
					
					this.kbhId = rs.getString("kbh_fk");
					this.trangthai = rs.getString("trangthai");
					
					this.sochungtu = rs.getString("SoHoaDon");
					this.loaidonhang = rs.getString("loaidonhang");
					
				}
				rs.close();
				
				//INIT SO LUONG
				query = "select sanpham_fk, (select MA from SANPHAM where pk_seq = a.sanpham_fk ) as spMA,  solo, soluong, a.NgayHetHan " +
						"from DONTRAHANG_SP a where dontrahang_fk = '" + this.id + "'";
				System.out.println("---INIT SP: " + query);
				rs = db.get(query);
				/*if(rs != null)*/
				{
					Hashtable<String, String> sp_soluong = new Hashtable<String, String>();
					while(rs.next())
					{
						sp_soluong.put(rs.getString("spMA") + "__" + rs.getString("solo") + "__" + rs.getString("NgayHetHan")  , rs.getString("soluong") );
					}
					rs.close();
					
					this.sanpham_soluong = sp_soluong;
				}
				
			} 
			catch (Exception e) 
			{
				System.out.println("---LOI INIT: " + e.getMessage());
				e.printStackTrace();
			}
		}
		
		//Init tich luy
        rs = db.get("select tichluy_fk from DONTRAHANG_TICHLUY where dontrahang_fk ='" + this.id + "'");
        if(rs != null)
        {
        	try 
        	{
        		String tichluyIds = "";
				while(rs.next())
				{
					tichluyIds += rs.getString("tichluy_fk") + ",";
				}
				rs.close();
				
				if(tichluyIds.trim().length() > 0)
				{
					tichluyIds = tichluyIds.substring(0, tichluyIds.length() - 1);
					this.tichluyId = tichluyIds;
				}
			} 
        	catch (Exception e) 
        	{
        		System.out.println("Exception TICH LUY: " + e.getMessage());
        	}
        }
		
		System.out.println("---KHO XUAT: " + this.khoXuatId);
		this.createRs();
		
		this.initSANPHAM();
		
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
		if(this.nppId.trim().length() > 0 && this.kbhId.trim().length() > 0 && this.khId.trim().length() > 0 )
		{
			tongluong = tongluong.replaceAll(",", "");
			
			
			String _dungchungkenh = "0";
			if(this.loaidonhang.equals("0"))
			{
				String query =  "select isnull(dungchungkenh, 0) as dungchungkenh from NHAPHANPHOI where pk_seq = '" + this.khId + "' ";
				ResultSet rsKENH = db.get(query);
				if(rsKENH != null)
				{
					try 
					{
						if(rsKENH.next())
						{
							_dungchungkenh = rsKENH.getString("dungchungkenh");
						}
						rsKENH.close();
					} 
					catch (Exception e) {e.printStackTrace(); }
				}
			}
			else //Khách hàng trả về MTV thì không quan tâm tới nhóm kên
			{
				this.dungchungkenh = "1";
				_dungchungkenh = this.dungchungkenh;
			}
			
			String nhomkenhId = "( select nk_fk from NHOMKENH_KENHBANHANG where kbh_fk = '" + this.kbhId + "' )";
			if(!_dungchungkenh.equals("1"))
				nhomkenhId = "100000" ;
			
			
			//LẤY LOẠI KHO - ĐỂ BIẾT LẤY SỐ LÔ CHO ĐÚNG
			String query = "Select loai from KHO WHERE PK_SEQ = "+this.khoXuatId;
			System.out.println(query);
			ResultSet loaikho = db.get(query);
			
			int loaiKHO = 0;
			if(loaikho!=null)
			{
				try {
					while(loaikho.next())
					{
						loaiKHO = loaikho.getInt("loai");
					}
					loaikho.close();
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
			}
			
			
			if(loaiKHO == 1 || loaiKHO == 3 || loaiKHO == 4)
			{
				query = "select AVAILABLE + ISNULL( ( select sum(soluong) from DONTRAHANG_SP where dontrahang_fk = '" + ( this.id.trim().length() > 0 ? this.id : "-1" ) + "' and sanpham_fk = ct.sanpham_fk and solo = ct.solo  and NgayHetHan=ct.NgayHetHan ), 0 ) as AVAILABLE, NGAYHETHAN, SOLO " +
				   "from NHAPP_KHO_CHITIET ct " +
				   "where KHO_FK = '" + this.khoXuatId + "' and NHOMKENH_FK = " + nhomkenhId + "  and NPP_FK = '" + this.nppId + "' and SANPHAM_FK = ( select pk_seq from SANPHAM where ma = '" + spMa + "' ) " +
				   //" and ( AVAILABLE + ISNULL( ( select sum(soluong) from DONTRAHANG_SP where dontrahang_fk = '" + ( this.id.trim().length() > 0 ? this.id : "-1" ) + "' and sanpham_fk = ( select pk_seq from SANPHAM where ma = '" + spMa + "' ) and solo = ct.solo  and NgayHetHan=ct.NgayHetHan   ), 0 ) ) > 0 " +
				   " order by NGAYHETHAN asc ";
			}
			else if(loaiKHO == 2 || loaiKHO == 7)
			{
				query = "select AVAILABLE + ISNULL( ( select sum(soluong) from DONTRAHANG_SP where dontrahang_fk = '" + ( this.id.trim().length() > 0 ? this.id : "-1" ) + "' and sanpham_fk = ct.sanpham_fk and solo = ct.solo  and NgayHetHan=ct.NgayHetHan ), 0 ) as AVAILABLE, NGAYHETHAN, SOLO " +
				   "from NHAPP_KHO_CHITIET_NCC ct " +
				   "where KHO_FK = '" + this.khoXuatId + "' and NHOMKENH_FK = " + nhomkenhId + "  and NPP_FK = '" + this.nppId + "' and SANPHAM_FK = ( select pk_seq from SANPHAM where ma = '" + spMa + "' ) " +
				   //" and ( AVAILABLE + ISNULL( ( select sum(soluong) from DONTRAHANG_SP where dontrahang_fk = '" + ( this.id.trim().length() > 0 ? this.id : "-1" ) + "' and sanpham_fk = ( select pk_seq from SANPHAM where ma = '" + spMa + "' ) and solo = ct.solo  and NgayHetHan=ct.NgayHetHan   ), 0 ) ) > 0 " +
				   " order by NGAYHETHAN asc ";
			}
			else if( loaiKHO == 5 || loaiKHO == 8)
			{
				query = "select AVAILABLE + ISNULL( ( select sum(soluong) from DONTRAHANG_SP where dontrahang_fk = '" + ( this.id.trim().length() > 0 ? this.id : "-1" ) + "' and sanpham_fk = ct.sanpham_fk and solo = ct.solo  and NgayHetHan=ct.NgayHetHan ), 0 ) as AVAILABLE, NGAYHETHAN, SOLO " +
				   		"from NHAPP_KHO_KYGUI_CHITIET ct " +
				   		"where KHO_FK = '" + this.khoXuatId + "' and NHOMKENH_FK = " + nhomkenhId + "  and NPP_FK = '" + this.nppId + "' and SANPHAM_FK = ( select pk_seq from SANPHAM where ma = '" + spMa + "' ) " +
				   		//" and ( AVAILABLE + ISNULL( ( select sum(soluong) from DONTRAHANG_SP where dontrahang_fk = '" + ( this.id.trim().length() > 0 ? this.id : "-1" ) + "' and sanpham_fk = ( select pk_seq from SANPHAM where ma = '" + spMa + "' ) and solo = ct.solo  and NgayHetHan=ct.NgayHetHan   ), 0 ) ) > 0 " +
				   		" order by NGAYHETHAN asc ";
			}
			else if( loaiKHO == 6)
			{
				query = "select AVAILABLE + ISNULL( ( select sum(soluong) from DONTRAHANG_SP where dontrahang_fk = '" + ( this.id.trim().length() > 0 ? this.id : "-1" ) + "' and sanpham_fk = ct.sanpham_fk and solo = ct.solo  and NgayHetHan=ct.NgayHetHan ), 0 ) as AVAILABLE, NGAYHETHAN, SOLO " +
				   		"from NHAPP_KHO_DDKD_CHITIET ct " +
				   		"where KHO_FK = '" + this.khoXuatId + "' and NHOMKENH_FK = " + nhomkenhId + "  and NPP_FK = '" + this.nppId + "' and SANPHAM_FK = ( select pk_seq from SANPHAM where ma = '" + spMa + "' ) " +
				   		//" and ( AVAILABLE + ISNULL( ( select sum(soluong) from DONTRAHANG_SP where dontrahang_fk = '" + ( this.id.trim().length() > 0 ? this.id : "-1" ) + "' and sanpham_fk = ( select pk_seq from SANPHAM where ma = '" + spMa + "' ) and solo = ct.solo  and NgayHetHan=ct.NgayHetHan   ), 0 ) ) > 0 " +
				   		" order by NGAYHETHAN asc ";
			}
			
			System.out.println("----LAY SO LO: " + query );
			String query2 = "";
			ResultSet rs = db.get(query);
			try 
			{
				double total = 0;
				
				while(rs.next())
				{
					double slg = 0;
					double avai = rs.getDouble("AVAILABLE");
					
					total += avai;
					
					if(total < Double.parseDouble(tongluong))
					{
						slg = avai;
					}
					else
					{
						slg =  Double.parseDouble(tongluong) - ( total - avai );
					}
						
					if(slg >= 0)
					{
						query2 += "select '" + avai + "' as AVAILABLE, '" + rs.getString("NGAYHETHAN") + "' as NGAYHETHAN, '" + rs.getString("SOLO") + "' as SOLO, '" + slg + "' as tuDEXUAT union ALL ";
					}
					else
					{
						query2 += "select '" + avai + "' as AVAILABLE, '" + rs.getString("NGAYHETHAN") + "' as NGAYHETHAN, '" + rs.getString("SOLO") + "' as SOLO, '' as tuDEXUAT union ALL ";
					}
					
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				System.out.println("EXCEPTION INIT SOLO: " + e.getMessage());
				e.printStackTrace();
			}
			
			if(query2.trim().length() > 0)
			{
				query2 = query2.substring(0, query2.length() - 10);
				System.out.println("---TU DONG DE XUAT BIN - LO: " + query2 );
				return db.get(query2);
			}
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

	
	public String getTienBvat() {
		
		return this.TienBvat;
	}

	
	public void setTienBvat(String tienbvat) {
		
		this.TienBvat = tienbvat;
	}

	
	public String getTienCK() {
		
		return this.Tienck;
	}

	
	public void setTienCK(String tienck) {
		
		this.Tienck = tienck;
	}

	
	public String getTienVat() {
	
		return this.Tienvat;
	}

	
	public void setTienVat(String tienvat) {
	
		this.Tienvat = tienvat;
	}

	
	public String getTienAVat() {
	
		return this.TienAvat;
	}

	
	public void setTienAVat(String tienavat) {
	
		this.TienAvat = tienavat;
	}
	
	public void setTichluyRs(ResultSet tichluyRs) {
		
		this.tichluyRs = tichluyRs;
	}

	
	public ResultSet getTichluyRs() {
		
		return this.tichluyRs;
	}

	
	public void setTichluyId(String tichluyId) {
		
		this.tichluyId = tichluyId;
	}

	
	public String getTichluyId() {
		
		return this.tichluyId;
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
