package geso.traphaco.center.beans.dieuchinhtonkho.imp;

import geso.traphaco.center.beans.dieuchinhtonkho.IErpChuyenkhoTT;
import geso.traphaco.center.db.sql.dbutils;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

public class ErpChuyenkhoTT implements IErpChuyenkhoTT
{
	private static final long serialVersionUID = 1L;
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
	
	String nppId;
	ResultSet nppRs;
	ResultSet dvtRs;
	
	String kbhId;
	ResultSet kbhRs;

	ResultSet spRs;
	
	String lenhdieudong, lddcua, lddveviec, ngaylenhdieudong, sohopdong,ngayhopdong, nguoivanchuyen, ptvanchuyen;
	
	Hashtable<String, String> sanpham_soluong;
	
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
	
	dbutils db;
	
	public ErpChuyenkhoTT()
	{
		this.id = "";
		this.ngayyeucau = getDateTime();
		this.ghichu = "";
		this.khoNhanId = "";
		this.khoXuatId = "";
		this.nppId = "";
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
		this.sochungtu="";
		
		this.sanpham_soluong = new Hashtable<String, String>();
		this.loaidonhang="0";
		this.db = new dbutils();
	}
	
	public ErpChuyenkhoTT(String id)
	{
		this.id = id;
		this.ngayyeucau = getDateTime();
		this.ghichu = "";
		this.khoNhanId = "";
		this.khoXuatId = "";
		this.nppId = "";
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
		this.sochungtu="";
		this.loaidonhang="0";
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
		
		if( this.nppId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn đối tượng nhận";
			return false;
		}
		
		if( this.kbhId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn kênh bán hàng";
			return false;
		}
		
		if( this.sochungtu.trim().length() <= 0 )
		{
			this.msg = "Vui lòng nhập số chứng từ";
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
		
		try
		{
			db.getConnection().setAutoCommit(false);
		
			String query = " insert ERP_CHUYENKHO(SoChungTu,ngaychuyen, ghichu, trangthai, khoxuat_fk, kbh_fk, npp_fk, ngaytao, nguoitao, ngaysua, nguoisua," +
					       "        lenhdieudong, lddcua, lddveviec, ngaydieudong, sohopdong,ngayhopdong, nguoivanchuyen, ptvanchuyen,LoaiDonHang,tructhuoc_Fk) " +
						   " values('"+this.sochungtu+"','" + this.ngayyeucau + "', N'" + this.ghichu + "', '0', '" +  this.khoXuatId + "', '" + this.kbhId + "', " + this.nppId + ", '" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "'," +
						   "        N'"+ this.lenhdieudong +"', N'"+ this.lddcua +"',N'"+ this.lddveviec +"','"+ this.ngaylenhdieudong +"',N'"+ this.sohopdong +"','"+ this.ngayhopdong +"',N'"+ this.nguoivanchuyen +"', N'"+ this.ptvanchuyen +"','"+loaidonhang+"',1 )";
			
			System.out.println("1.Insert CK: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_CHUYENKHO " + query;
				db.getConnection().rollback();
				return false;
			}
			
			for(int i = 0; i < spMa.length; i++)
			{
				if(spMa[i].trim().length() > 0 && spSoluong[i].trim().length() > 0 )
				{
					query = "insert ERP_CHUYENKHO_SANPHAM( chuyenkho_fk, SANPHAM_FK, soluongchuyen, dongia, dvdl_fk ) " +
							"select ident_current('ERP_CHUYENKHO'), pk_seq, '" + spSoluong[i].replaceAll(",", "") + "', '" + spGianhap[i].replaceAll(",", "") + "', ISNULL( ( select pk_Seq from DONVIDOLUONG where donvi = N'" + spDonvi[i].trim() + "' ), DVDL_FK ) " +
							"from SANPHAM where MA = '" + spMa[i].trim() + "' ";
					
					System.out.println("3.Insert CK - SP: " + query);
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi ERP_CHUYENKHO_SANPHAM: " + query;
						db.getConnection().rollback();
						return false;
					}
					
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
									query = "select AVAILABLE from ERP_KHOTT_SP_CHITIET where KHOTT_FK = '" + this.khoXuatId + "' and SOLO = '" + _sp[1] + "' " +
											"	and SANPHAM_FK = ( select pk_seq from SANPHAM where ma = '" + spMa[i] + "' ) ";
									ResultSet rsTK = db.get(query);
									double avai = 0;
									if(rsTK.next())
									{
										avai = rsTK.getDouble("AVAILABLE");
									}
									rsTK.close();
									
									if( Double.parseDouble(_soluongCT) > avai )
									{
										this.msg = "Sản phẩm (" + spTen[i] + ") với số lô (" + _sp[1] + "), số lượng xuất (" + _soluongCT + ") còn tối đa (" + avai + "). ";
										db.getConnection().rollback();
										return false;
									}
									
									
									query = "insert ERP_CHUYENKHO_SANPHAM_CHITIET( chuyenkho_fk, SANPHAM_FK, solo, soluong ) " +
											"select IDENT_CURRENT('ERP_CHUYENKHO'), pk_seq, N'" + _sp[1] + "', '" + _soluongCT.replaceAll(",", "") + "' " +
											"from SANPHAM where MA = '" + spMa[i] + "' ";
									
									System.out.println("1.2.Insert YCXK - SP - CT: " + query);
									if(!db.update(query))
									{
										this.msg = "Khong the tao moi ERP_CHUYENKHO_SANPHAM_CHITIET: " + query;
										db.getConnection().rollback();
										return false;
									}
									
									
									query = "Update ERP_KHOTT_SP_CHITIET set booked = booked + '" + _soluongCT.replaceAll(",", "") + "', AVAILABLE = AVAILABLE - '" + _soluongCT.replaceAll(",", "") + "' " +
											"where KHOTT_FK = '" + this.khoXuatId + "' and SOLO = '" + _sp[1] + "' and SANPHAM_FK = ( select pk_seq from SANPHAM where MA = '" + spMa[i] + "' )  ";
									if(!db.update(query))
									{
										this.msg = "Khong the cap nhat ERP_KHOTT_SP_CHITIET: " + query;
										db.getConnection().rollback();
										return false;
									}
									
									
									query = "Update ERP_KHOTT_SANPHAM set booked = booked + '" + _soluongCT.replaceAll(",", "") + "', AVAILABLE = AVAILABLE - '" + _soluongCT.replaceAll(",", "") + "' " +
											"where KHOTT_FK = '" + this.khoXuatId + "' and SANPHAM_FK = ( select pk_seq from SANPHAM where MA = '" + spMa[i] + "' )  ";
									if(!db.update(query))
									{
										this.msg = "Khong the cap nhat ERP_KHOTT_SANPHAM: " + query;
										db.getConnection().rollback();
										return false;
									}
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
		
		if( this.nppId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn đối tượng nhận";
			return false;
		}
		
		if( this.kbhId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn kênh bán hàng";
			return false;
		}
		
		if( this.sochungtu.trim().length() <= 0 )
		{
			this.msg = "Vui lòng nhập số chứng từ";
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
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query =	" Update ERP_CHUYENKHO set SoChungTu='"+this.sochungtu+"',ngaychuyen = '" + this.ngayyeucau + "', ghichu = N'" + this.ghichu + "', " +
							" khoxuat_fk = " + this.khoXuatId + ", kbh_fk = '" + this.kbhId + "', npp_fk = '" + this.nppId + "', ngaysua = '" + getDateTime() + "', nguoisua = '" + this.userId + "'," +
						    " lenhdieudong = N'"+ this.lenhdieudong +"', lddcua= N'"+ this.lddcua +"', lddveviec=N'"+ this.lddveviec +"', ngaydieudong ='"+ this.ngaylenhdieudong +"' ," +
						    " sohopdong = N'"+ this.sohopdong +"' ,ngayhopdong ='"+ this.ngayhopdong +"', nguoivanchuyen=N'"+ this.nguoivanchuyen +"' , ptvanchuyen=N'"+ this.ptvanchuyen +"'" + 
							" where pk_seq = '" + this.id + "' ";
		
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_CHUYENKHO " + query;
				db.getConnection().rollback();
				return false;
			}
					
			//TANG KHO NGUOC LAI
			query = " update kho set kho.AVAILABLE = kho.AVAILABLE + CT.tongxuat, " +
					" 			   kho.BOOKED = kho.BOOKED - CT.tongxuat " +
					" from " +
					" ( " +
					" 	select a.khoxuat_fk as kho_fk, b.sanpham_fk, b.solo, SUM(b.soluong) as tongxuat  " +
					" 	from ERP_CHUYENKHO a inner join ERP_CHUYENKHO_SANPHAM_CHITIET b on a.pk_seq = b.chuyenkho_fk " +
					" 	where chuyenkho_fk = '" + this.id + "' " +
					" 	group by a.khoxuat_fk, b.solo, b.sanpham_fk " +
					" ) " +
					" CT inner join ERP_KHOTT_SP_CHITIET kho on CT.kho_fk = kho.KHOTT_FK  " +
					" 	and CT.sanpham_fk = kho.SANPHAM_FK and CT.solo = kho.SOLO ";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_KHOTT_SP_CHITIET " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = " update kho set kho.AVAILABLE = kho.AVAILABLE + CT.tongxuat, " +
					" 			   kho.BOOKED = kho.BOOKED - CT.tongxuat " +
					" from " +
					" ( " +
					" 	select a.khoxuat_fk as kho_fk, b.sanpham_fk, SUM(b.soluong) as tongxuat  " +
					" 	from ERP_CHUYENKHO a inner join ERP_CHUYENKHO_SANPHAM_CHITIET b on a.pk_seq = b.chuyenkho_fk " +
					" 	where chuyenkho_fk = '" + this.id + "' " +
					" 	group by a.khoxuat_fk, b.sanpham_fk " +
					" ) " +
					" CT inner join ERP_KHOTT_SANPHAM kho on CT.kho_fk = kho.KHOTT_FK  " +
					" 	and CT.sanpham_fk = kho.SANPHAM_FK  ";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_KHOTT_SANPHAM " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_CHUYENKHO_SANPHAM where chuyenkho_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_CHUYENKHO_SANPHAM " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_CHUYENKHO_SANPHAM_CHITIET where chuyenkho_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_CHUYENKHO_SANPHAM_CHITIET " + query;
				db.getConnection().rollback();
				return false;
			}
			
			for(int i = 0; i < spMa.length; i++)
			{
				if(spMa[i].trim().length() > 0 && spSoluong[i].trim().length() > 0 )
				{
					query = "insert ERP_CHUYENKHO_SANPHAM( chuyenkho_fk, SANPHAM_FK, soluongchuyen, dongia, dvdl_fk ) " +
							"select '" + this.id + "', pk_seq, '" + spSoluong[i].replaceAll(",", "") + "', '" + spGianhap[i].replaceAll(",", "") + "', ISNULL( ( select pk_Seq from DONVIDOLUONG where donvi = N'" + spDonvi[i].trim() + "' ), DVDL_FK ) " +
							"from SANPHAM where MA = '" + spMa[i].trim() + "' ";
					
					System.out.println("3.Insert CK - SP: " + query);
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi ERP_CHUYENKHO_SANPHAM: " + query;
						db.getConnection().rollback();
						return false;
					}
					
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
								
								if(!_soluongCT.equals("0"))
								{
									totalCT += Double.parseDouble(_soluongCT);
									
									//CHECK TON KHO
									query = "select AVAILABLE from ERP_KHOTT_SP_CHITIET where KHOTT_FK = '" + this.khoXuatId + "' and SOLO = '" + _sp[1] + "' " +
											"	and SANPHAM_FK = ( select pk_seq from SANPHAM where ma = '" + spMa[i] + "' ) ";
									ResultSet rsTK = db.get(query);
									double avai = 0;
									if(rsTK.next())
									{
										avai = rsTK.getDouble("AVAILABLE");
									}
									rsTK.close();
									
									if( Double.parseDouble(_soluongCT) > avai )
									{
										this.msg = "Sản phẩm (" + spTen[i] + ") với số lô (" + _sp[1] + "), số lượng xuất (" + _soluongCT + ") còn tối đa (" + avai + "). ";
										db.getConnection().rollback();
										return false;
									}
									
									
									query = "insert ERP_CHUYENKHO_SANPHAM_CHITIET( chuyenkho_fk, SANPHAM_FK, solo, soluong ) " +
											"select '" + this.id + "', pk_seq, N'" + _sp[1] + "', '" + _soluongCT.replaceAll(",", "") + "' " +
											"from SANPHAM where MA = '" + spMa[i] + "' ";
									
									System.out.println("1.2.Insert YCXK - SP - CT: " + query);
									if(!db.update(query))
									{
										this.msg = "Khong the tao moi ERP_CHUYENKHO_SANPHAM_CHITIET: " + query;
										db.getConnection().rollback();
										return false;
									}
									
									
									query = "Update ERP_KHOTT_SP_CHITIET set booked = booked + '" + _soluongCT.replaceAll(",", "") + "', AVAILABLE = AVAILABLE - '" + _soluongCT.replaceAll(",", "") + "' " +
											"where KHOTT_FK = '" + this.khoXuatId + "' and SOLO = '" + _sp[1] + "' and SANPHAM_FK = ( select pk_seq from SANPHAM where MA = '" + spMa[i] + "' )  ";
									if(!db.update(query))
									{
										this.msg = "Khong the cap nhat ERP_KHOTT_SP_CHITIET: " + query;
										db.getConnection().rollback();
										return false;
									}
									
									
									query = "Update ERP_KHOTT_SANPHAM set booked = booked + '" + _soluongCT.replaceAll(",", "") + "', AVAILABLE = AVAILABLE - '" + _soluongCT.replaceAll(",", "") + "' " +
											"where KHOTT_FK = '" + this.khoXuatId + "' and SANPHAM_FK = ( select pk_seq from SANPHAM where MA = '" + spMa[i] + "' )  ";
									if(!db.update(query))
									{
										this.msg = "Khong the cap nhat ERP_KHOTT_SANPHAM: " + query;
										db.getConnection().rollback();
										return false;
									}
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
		String query = "select PK_SEQ, TEN from ERP_KHOTT where trangthai = '1' ";
		this.khoXuatRs = db.get(query);
		
		query = "select PK_SEQ, TEN from NHAPHANPHOI where trangthai = '1' and tructhuoc_fk='1'  ";
		this.nppRs = db.get(query);
		
		this.dvtRs = db.getScrol("select PK_SEQ, DONVI from DONVIDOLUONG where trangthai = '1' ");
		this.kbhRs = db.getScrol("select PK_SEQ, TEN from KENHBANHANG where trangthai = '1' ");
		
	}

	private void initSANPHAM() 
	{
		String query =  
					"select b.MA, b.TEN, DV.donvi, a.soluongchuyen as soluong, a.dongia, 0 as chietkhau, ISNULL(b.trongluong, 0) as trongluong, ISNULL(b.thetich, 0) as thetich, " +
					"	a.soluongchuyen + ISNULL( ( select sum(available) from ERP_KHOTT_SANPHAM where KHOTT_FK = '100001' and sanpham_fk = b.pk_seq ), 0 ) as avai    " +
					" from ERP_CHUYENKHO_SANPHAM a inner Join SanPham b on a.SANPHAM_FK = b.PK_SEQ    " +
					" 		INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = a.DVDL_FK       " +
					"where a.chuyenkho_FK = '" + this.id + "' ";
		
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
				String spTONKHO = "";
				String spGIANHAP = "";
				
				
				while(spRs.next())
				{
					spMA += spRs.getString("MA") + "__";
					spTEN += spRs.getString("TEN") + "__";
					spDONVI += spRs.getString("DONVI") + "__";
					spSOLUONG += formater.format(spRs.getDouble("SOLUONG")) + "__";
					spTONKHO += formater.format(spRs.getDouble("avai")) + "__";
					spGIANHAP += formater.format(spRs.getDouble("DONGIA")) + "__";
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
					
					spTONKHO = spTONKHO.substring(0, spTONKHO.length() - 2);
					this.spTonkho = spTONKHO.split("__");
					
					spGIANHAP = spGIANHAP.substring(0, spGIANHAP.length() - 2);
					this.spGianhap = spGIANHAP.split("__");
					
				}
			} 
			catch (Exception e) 
			{
				System.out.println("115.Exception: " + e.getMessage());
			}
		}
		
	}

	public void init() 
	{
		String query =  " select loaidonhang,SoChungTu,ngaychuyen, ISNULL(ghichu, '') as ghichu, khoxuat_fk, kbh_fk, npp_fk, trangthai," +
						" lenhdieudong, lddcua, lddveviec, ngaydieudong, sohopdong,ngayhopdong, nguoivanchuyen, ptvanchuyen" +
						" from ERP_CHUYENKHO where pk_seq = '" + this.id + "'";
		System.out.println("____INIT NHAP KHO: " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					this.ngayyeucau = rs.getString("ngaychuyen");
					this.ghichu = rs.getString("ghichu");
					this.khoXuatId = rs.getString("khoxuat_fk");
					this.nppId = rs.getString("npp_fk");
					this.kbhId = rs.getString("kbh_fk");
					this.trangthai = rs.getString("trangthai");
					this.lenhdieudong =rs.getString("lenhdieudong")== null ? "":rs.getString("lenhdieudong") ;
					this.lddcua =rs.getString("lddcua")== null ? "":rs.getString("lddcua");
					this.lddveviec =rs.getString("lddveviec")== null ? "":rs.getString("lddveviec");
					this.ngaylenhdieudong =rs.getString("ngaydieudong")== null ? "":rs.getString("ngaydieudong");
					this.sohopdong =rs.getString("sohopdong")== null ? "":rs.getString("sohopdong");
					this.ngayhopdong =rs.getString("ngayhopdong")== null ? "":rs.getString("ngayhopdong");
					this.nguoivanchuyen =rs.getString("nguoivanchuyen")== null ? "":rs.getString("nguoivanchuyen");
					this.ptvanchuyen = rs.getString("ptvanchuyen")== null ? "":rs.getString("ptvanchuyen");
					this.sochungtu  = rs.getString("sochungtu")== null ? "":rs.getString("sochungtu");
					this.loaidonhang  = rs.getString("loaidonhang")== null ? "":rs.getString("loaidonhang");
					
				}
				rs.close();
				
				
				//INIT SO LUONG
				query = "select sanpham_fk, (select MA from SANPHAM where pk_seq = a.sanpham_fk ) as spMA,  solo, soluong " +
						"from ERP_CHUYENKHO_SANPHAM_CHITIET a where chuyenkho_fk = '" + this.id + "'";
				System.out.println("---INIT SP: " + query);
				rs = db.get(query);
				if(rs != null)
				{
					Hashtable<String, String> sp_soluong = new Hashtable<String, String>();
					while(rs.next())
					{
						//System.out.println("---KEY BEAN: " + rs.getString("sanpham_fk") + "__" + rs.getString("LOAI") + "__" + rs.getString("SCHEME") + "__" + rs.getString("solo"));
						sp_soluong.put(rs.getString("spMA") + "__" + rs.getString("solo"), rs.getString("soluong") );
					}
					rs.close();
					
					this.sanpham_soluong = sp_soluong;
				}
				
			} 
			catch (Exception e) 
			{
				System.out.println("---LOI INIT: " + e.getMessage());
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
			
		}catch(Exception er){
			
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

	
	public String[] getSpNgayhethan() {
		
		return this.spNgayhethan;
	}

	
	public void setSpNgayhethan(String[] spNgayhethan) {
		
		this.spNgayhethan = spNgayhethan;
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
	
	public ResultSet getSoloTheoSp(String spMa, String tongluong)
	{
		tongluong = tongluong.replaceAll(",", "");
		//System.out.println("---TONG LUONG: " + tongluong );
		
		String query = "select AVAILABLE + ISNULL( ( select sum(soluong) from ERP_CHUYENKHO_SANPHAM_CHITIET where chuyenkho_fk = '" + ( this.id.trim().length() > 0 ? this.id : "-1" ) + "' and sanpham_fk = ct.sanpham_fk and solo = ct.solo  ), 0 ) as AVAILABLE, NGAYHETHAN, SOLO " +
					   "from ERP_KHOTT_SP_CHITIET ct " +
					   "where KHOTT_FK = '" + this.khoXuatId + "' and SANPHAM_FK = ( select pk_seq from SANPHAM where ma = '" + spMa + "' ) " +
					   		" and ( AVAILABLE + ISNULL( ( select sum(soluong) from ERP_CHUYENKHO_SANPHAM_CHITIET where chuyenkho_fk = '" + ( this.id.trim().length() > 0 ? this.id : "-1" ) + "' and sanpham_fk = ( select pk_seq from SANPHAM where ma = '" + spMa + "' ) and solo = ct.solo  ), 0 ) ) > 0 " +
					   " order by NGAYHETHAN asc ";
		
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
		}
		
		if(query2.trim().length() > 0)
		{
			query2 = query2.substring(0, query2.length() - 10);
			//System.out.println("---TU DONG DE XUAT BIN - LO: " + query2 );
			return db.get(query2);
		}
		
		return null;
	}
	
	public Hashtable<String, String> getSanpham_Soluong() {
		
		return this.sanpham_soluong;
	}

	
	public void setSanpham_Soluong(Hashtable<String, String> sp_soluong) {
		
		this.sanpham_soluong = sp_soluong;
	}
	
	String sochungtu;
	public String getSoChungTu()
	{
		return sochungtu;
	}
	public void setSoChungTu(String sochungtu)
	{
		this.sochungtu=sochungtu;
	}
	String loaidonhang;

	public String getLoaidonhang() {
		return loaidonhang;
	}

	public void setLoaidonhang(String loaidonhang) {
		this.loaidonhang = loaidonhang;
	}
	
}
