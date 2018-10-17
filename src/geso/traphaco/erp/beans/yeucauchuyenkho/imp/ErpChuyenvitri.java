package geso.traphaco.erp.beans.yeucauchuyenkho.imp;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.yeucauchuyenkho.IErpChuyenvitri;
import geso.traphaco.erp.beans.yeucauchuyenkho.IYeucau;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
 
public class ErpChuyenvitri implements IErpChuyenvitri
{
	String userId;
	String id;
	String ctyId;
	String ngayyeucau;
	String lydoyeucau;
	String ghichu;
	String msg;
	String trangthai;
	
	String kyhieu;
	String sochungtu;

	ResultSet khoXuatRs;
	String khoXuatId, khoXuatTen;

	String task;
	String khoNhanId, khoNhanTen;
	ResultSet khoNhanRs;
	
	String nccXuatId;
	ResultSet nccXuatRs;
	
	String nccNhanId;
	ResultSet nccNhanRs;
	
	String lsxIds;
	ResultSet lsxRs;
	
	String tongSLYC ;
	
	List<IYeucau> spList;

	dbutils db;
	
	String codoituong;
	String loaidoituong;
	String doituongId;
	ResultSet doituongRs;
	
	String cokhonhan;
	String codoituongNhan;
	String loaidoituongNhan;
	String doituongNhanId;
	ResultSet doituongNhanRs;
	
	Hashtable<String, String> sanpham_soluong;
	Hashtable<String, String> sanpham_soluongDAXUAT;
	Hashtable<String, String> sanpham_vitriNhan;
	
	String vtcId;
	ResultSet vitriChuyenRs;
	ResultSet vitriNhanRs;
	
	String coChiphi;
	String chiphiId;
	ResultSet chiphiRs;
	
	public ErpChuyenvitri()
	{
		this.id = "";
		this.ctyId = "";
		this.ngayyeucau = getDateTime();
		this.lydoyeucau = "";
		this.ghichu = "";
		this.khoXuatId = "";
		this.khoNhanId = "";
		this.task="";
		this.lsxIds = "";
		this.msg = "";
		this.trangthai = "0";
		this.kyhieu="";
		this.sochungtu="";
		
		this.spList = new ArrayList<IYeucau>();
		
		this.nccXuatId = "";
		this.nccNhanId = "";
		this.tongSLYC ="0";

		this.loaidoituong = "";
		this.doituongId = "";
		this.codoituong = "";
		
		this.cokhonhan = "";
		this.loaidoituongNhan = "";
		this.doituongNhanId = "";
		this.codoituongNhan = "";
		
		this.sanpham_soluong = new Hashtable<String, String>();
		this.sanpham_soluongDAXUAT = new Hashtable<String, String>();
		this.sanpham_vitriNhan = new Hashtable<String, String>();
		
		this.coChiphi = "";
		this.chiphiId = "";
		this.vtcId = "";
		
		this.db = new dbutils();
	}
	
	public ErpChuyenvitri(String id)
	{
		this.id = id;
		this.ngayyeucau = getDateTime();
		this.lydoyeucau = "";
		this.ghichu = "";
		this.khoXuatId = "";
		this.khoNhanId = "";
		this.lsxIds = "";
		this.msg = "";
		this.task="";
		this.trangthai = "0";
		this.spList = new ArrayList<IYeucau>();

		this.kyhieu="";
		this.sochungtu="";

		this.nccXuatId = "";
		this.nccNhanId = "";
		this.tongSLYC ="0";

		this.cokhonhan = "";
		this.loaidoituong = "";
		this.doituongId = "";
		this.codoituong = "";
		
		this.cokhonhan = "";
		this.loaidoituongNhan = "";
		this.doituongNhanId = "";
		this.codoituongNhan = "";
		
		this.sanpham_soluong = new Hashtable<String, String>();
		this.sanpham_soluongDAXUAT = new Hashtable<String, String>();
		this.sanpham_vitriNhan = new Hashtable<String, String>();

		this.coChiphi = "";
		this.chiphiId = "";
		this.vtcId = "";
		
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

	public String getLydoyeucau() 
	{
		return this.lydoyeucau;
	}

	public void setLydoyeucau(String lydoyeucau) 
	{
		this.lydoyeucau = lydoyeucau;
	}

	public String getKhoXuatId() 
	{
		return this.khoXuatId;
	}

	public void setKhoXuatId(String khoxuattt) 
	{
		this.khoXuatId = khoxuattt;
	}

	public ResultSet getKhoXuatRs()
	{
		return this.khoXuatRs;
	}

	public void setKhoXuatRs(ResultSet khoxuatRs) 
	{
		this.khoXuatRs = khoxuatRs;
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

	public List<IYeucau> getSpList()
	{
		return this.spList;
	}

	public void setSpList(List<IYeucau> spList) 
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
	
	public boolean createCK() 
	{
		if(this.ngayyeucau.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập ngày chuyển";
			return false;
		}
		
		if(this.khoXuatId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn kho chuyển";
			return false;
		}
		
		if(this.vtcId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn vị trí chuyển";
			return false;
		}
		
		if( this.codoituong.equals("1") && this.doituongId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn đối tượng xuất";
			return false;
		}
		
		if( this.coChiphi.equals("1") && this.chiphiId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn chi phí";
			return false;
		}
				
		if(this.spList.size() <= 0)
		{
			this.msg = "Không có sản phẩm nào được chọn";
			return false;
		}
		else
		{
			//Check trung san pham + 1
			for(int i = 0; i < this.spList.size() - 1; i++)
			{
				IYeucau yc = this.spList.get(i);
				for(int j = i + 1; j < this.spList.size(); j++)
				{
					IYeucau yc2 = this.spList.get(j);
					
					if( yc.getId().trim().equals(yc2.getId().trim()) && yc.getLsxId().trim().equals(yc2.getLsxId().trim()) )
					{
						this.msg = "Sản phẩm ( " + yc.getTen() + " ) đã trùng  . Vui lòng kiểm tra lại.";
						return false;
					}
					
				}
			}
		}
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String _loaidoituongId = this.loaidoituong.trim().length() <= 0 ? "null" : this.loaidoituong.trim();
			String _doituongId = this.doituongId.trim().length() <= 0 ? "null" : this.doituongId.trim();
			
			String _chiphiId = this.chiphiId.trim().length() <= 0 ? "null" : this.chiphiId.trim();
			
			String query = 	" insert ERP_CHUYENVITRI(NgayChuyen, lydo, ghichu, trangthai, KhoChuyen_FK, BinChuyen_FK, ngaytao, nguoitao, ngaysua, nguoisua, loaidoituong, DOITUONG_FK, chiphi_fk ) " +
				   			" values('" + this.ngayyeucau + "',   N'" + this.lydoyeucau + "', N'" + this.ghichu + "', '0', '" + this.khoXuatId + "', " + this.vtcId + ",  '" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "', " + _loaidoituongId + ", " + _doituongId + ", " + _chiphiId + " ) ";
			
			System.out.println("::: CHEN CHUYEN VI TRI: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_CHUYENVITRI " + query;
				db.getConnection().rollback();
				return false;
			}
			
			String ycnlCurrent = "";
			query = "select IDENT_CURRENT('ERP_CHUYENVITRI') as ckId";
			
			ResultSet rsPxk = db.get(query);						
			if(rsPxk.next())
			{
				ycnlCurrent = rsPxk.getString("ckId");
				 
			}
			rsPxk.close();
			
			Utility util = new Utility();
			if(this.spList.size() > 0)
			{
				for(int i = 0; i < this.spList.size(); i++)
				{
					IYeucau sp = this.spList.get(i);
					
					if(sp.getSoluongYC().trim().length() > 0)
					{
						query = " insert ERP_CHUYENVITRI_SANPHAM(chuyenvitri_fk, SANPHAM_FK,  soluongchuyen, ghichu_chuyenkho  ) " +
								" values( '" + ycnlCurrent + "', '" + sp.getId() + "', " + sp.getSoluongYC() + ", N'" + sp.getghichu_CK() + "' ) ";
						
						System.out.println("::: CHEN SAN PHAM: " + query);
						if(!db.update(query))
						{
							this.msg = "Không thể tạo mới ERP_CHUYENVITRI_SANPHAM: " + query;
							db.getConnection().rollback();
							return false;
						}
						
						//INSERT CHI TIET
						if(this.sanpham_soluong != null)
						{
							Enumeration<String> keys = this.sanpham_soluong.keys();
							double totalCT = 0;
							
							while(keys.hasMoreElements())
							{
								String key = keys.nextElement();
								
								if(key.startsWith( sp.getMa() + "__ "  ) )
								{
									String[] _sp = key.split("__");
									
									String _soluongCT = "0"; 
									if(this.sanpham_soluong.get(key) != null)
									{
										_soluongCT = this.sanpham_soluong.get(key).replaceAll(",", "");
									}
									
									String solo = _sp[2];
									String ngayhethan = _sp[3];
									String ngaynhapkho = _sp[4];
									String MAME = _sp[5];
									String MATHUNG = _sp[6];
									
									String bin_fk = _sp[7]; //Bin dang luu ma + ten ( cach nay moi load duoc, de select chon qua nhieu )
									if( bin_fk.contains(" [") )
										bin_fk = bin_fk.substring(bin_fk.indexOf(" [") + 2 , bin_fk.indexOf("]"));
									
									System.out.println("::: BIN LA: " + _sp[7] + " -- START: " + ( bin_fk.indexOf(" [") + 2 ) + " -- END: "  + bin_fk.indexOf("]") + " -- NEW BIN: " + bin_fk );
									
									String MAPHIEU = _sp[8];
									String phieudt = _sp[9];
									String phieueo = _sp[10];
									String MARQ = _sp[11];
									String HAMLUONG = _sp[12];
									String HAMAM = _sp[13];
									String nsxMa = _sp[14];
									String nsx_fk = _sp[15];
									
									if(!_soluongCT.equals("0"))
									{
										totalCT += Double.parseDouble(_soluongCT);
										
										if( bin_fk.trim().length() <= 0 )
											bin_fk = "NULL";
										
										query = "insert ERP_CHUYENVITRI_SANPHAM_CHITIET( chuyenvitri_fk, SANPHAM_FK, scheme, solo, ngayhethan, ngaynhapkho, MAME, MATHUNG, binchuyen_fk, binnhan_fk, MAPHIEU, phieudt, phieueo, MARQ, HAMLUONG, HAMAM,  soluong, nsx_fk ) " +
												"select '" + ycnlCurrent + "', pk_seq, ' ', N'" + solo + "', N'" + ngayhethan + "', '" + ngaynhapkho + "', '" + MAME + "', '" + MATHUNG + "', '" + this.vtcId + "', " + bin_fk + ", " + 
												" 	'" + MAPHIEU + "', '" + phieudt + "', '" + phieueo + "', '" + MARQ + "', '" + HAMLUONG + "', '" + HAMAM + "', '" + _soluongCT.replaceAll(",", "") + "', '" + nsx_fk + "' " +
												"from ERP_SANPHAM where MA = '" + sp.getMa() + "' ";
										
										System.out.println("1.2.Insert ERP_CHUYENVITRI_SANPHAM_CHITIET: " + query);
										if(!db.update(query))
										{
											this.msg = "Khong the tao moi ERP_CHUYENVITRI_SANPHAM_CHITIET: " + query;
											db.getConnection().rollback();
											return false;
										}
										
										//CẬP NHẬT KHO
										this.msg = util.Update_KhoTT_MOI(this.ngayyeucau, "Chuyển vị trí", db, this.khoXuatId, sp.getId(), solo, ngayhethan, ngaynhapkho, MAME, MATHUNG, this.vtcId,
														MAPHIEU, phieudt, phieueo, MARQ, HAMLUONG, HAMAM, this.loaidoituong, this.doituongId, 0, Double.parseDouble(_soluongCT.replaceAll(",", "")), -1 * Double.parseDouble(_soluongCT.replaceAll(",", "")), nsx_fk);
										if( this.msg.trim().length() > 0 )
										{
											db.getConnection().rollback();
											return false;
										}
									}
								}
							}
							
							//NEU TONG SO LUONG CT MA KHONG BANG TONG LUONG XUAT THI KO CHO LUU
							if(totalCT != Double.parseDouble(sp.getSoluongYC().replaceAll(",", "").trim()) )
							{
								this.msg = "Tổng xuất theo lô của sản phẩm ( " + sp.getMa() + " ) ( " + totalCT + " ) phải bằng tổng số lượng xuất ( " + sp.getSoluongYC() + " ) ";
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

	public boolean updateCK() 
	{ 
		if(this.ngayyeucau.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập ngày chuyển";
			return false;
		}
		
		if(this.khoXuatId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn kho chuyển";
			return false;
		}
		
		if(this.vtcId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn vị trí chuyển";
			return false;
		}
		
		if( this.codoituong.equals("1") && this.doituongId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn đối tượng xuất";
			return false;
		}
		
		if( this.coChiphi.equals("1") && this.chiphiId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn chi phí";
			return false;
		}
			
		if(this.spList.size() <= 0)
		{
			this.msg = "Không có sản phẩm nào được chọn";
			return false;
		}
		else
		{
			//Check trung san pham + 1
			for(int i = 0; i < this.spList.size() - 1; i++)
			{
				IYeucau yc = this.spList.get(i);
				for(int j = i + 1; j < this.spList.size(); j++)
				{
					IYeucau yc2 = this.spList.get(j);

					if( yc.getId().trim().equals(yc2.getId().trim()) && yc.getLsxId().trim().equals(yc2.getLsxId().trim()) )
					{
						this.msg = "Sản phẩm ( " + yc.getTen() + " ) đã trùng  . Vui lòng kiểm tra lại.";
						return false;
					}

				}
			}
		}

		try
		{
			db.getConnection().setAutoCommit(false);

			String _loaidoituongId = this.loaidoituong.trim().length() <= 0 ? "null" : this.loaidoituong.trim();
			String _doituongId = this.doituongId.trim().length() <= 0 ? "null" : this.doituongId.trim();
			
			String _chiphiId = this.chiphiId.trim().length() <= 0 ? "null" : this.chiphiId.trim();
			
			String query =  " update  ERP_CHUYENVITRI set NGAYCHUYEN = '" + this.ngayyeucau + "', lydo = N'" + this.lydoyeucau + "', " +
							"  ghichu =N'"+this.ghichu+"', khochuyen_fk="+this.khoXuatId+", binchuyen_fk=" + this.vtcId + ",ngaysua='"+this.getDateTime()+"', nguoisua="+this.userId+"," +
							"  loaidoituong = " + _loaidoituongId + ", DOITUONG_FK = " + _doituongId + ", chiphi_fk = "+ _chiphiId +
							" where pk_seq="+this.id;
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_CHUYENVITRI " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = " DELETE ERP_CHUYENVITRI_SANPHAM WHERE CHUYENVITRI_FK = "+this.id;
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_CHUYENVITRI_SANPHAM " + query;
				db.getConnection().rollback();
				return false;
			}
			
			Utility util = new Utility();
			//Tăng kho ngược lại trước khi xóa
			query = "  select b.loaidoituong, b.DOITUONG_FK as doituongId, b.NGAYCHUYEN, b.Khochuyen_FK, a.SanPham_fk, a.SoLo, a.NgayHetHan, a.ngaynhapkho, " + 
					" 		a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, isnull(a.binchuyen_fk, 0) as binchuyen_fk, a.phieudt, a.phieueo, SUM( a.SoLuong ) as soluong, isnull(a.nsx_fk, 0) as nsx_fk  " + 
					"  from ERP_CHUYENVITRI_SANPHAM_CHITIET a inner join ERP_CHUYENVITRI b on a.chuyenvitri_FK = b.PK_SEQ " + 
					"  where b.PK_SEQ = '" + this.id + "' " + 
					"  group by b.loaidoituong, b.DOITUONG_FK, b.NGAYCHUYEN, b.Khochuyen_FK, a.SanPham_fk, a.SoLo, a.NgayHetHan, a.ngaynhapkho, a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, a.binchuyen_fk, a.phieudt, a.phieueo, a.nsx_fk ";
			
			System.out.println("::: CAP NHAT KHO: " + query);
			ResultSet rs = db.get(query);
			if( rs != null )
			{
				while( rs.next() )
				{
					String khoId = rs.getString("Khochuyen_FK");
					String spId = rs.getString("SanPham_fk");
					String solo = rs.getString("SoLo");
					String ngayhethan = rs.getString("NgayHetHan");
					String ngaynhapkho = rs.getString("ngaynhapkho");
					
					String loaidoituong = rs.getString("loaidoituong") == null ? "" : rs.getString("loaidoituong");
					String doituongId = rs.getString("doituongId") == null ? "" :  rs.getString("doituongId");
					
					String mame = rs.getString("mame");
					String mathung = rs.getString("mathung");
					String bin_fk = rs.getString("binchuyen_fk");
					
					String maphieu = rs.getString("maphieu");
					String phieudt = rs.getString("phieudt");
					String phieueo = rs.getString("phieueo");
					
					String marq = rs.getString("marq");
					String hamluong = rs.getString("hamluong");
					String hamam = rs.getString("hamam");

					double soluong = rs.getDouble("soluong");
					
					String nsx_fk = rs.getString("nsx_fk");
					
					msg = util.Update_KhoTT_MOI(rs.getString("NGAYCHUYEN"), "Cập nhật chuyển vị trí - Tăng kho ngược lại trước khi xóa ", db, khoId, spId, solo, ngayhethan, ngaynhapkho, 
							mame, mathung, bin_fk, maphieu, phieudt, phieueo, marq, hamluong, hamam, loaidoituong, doituongId, 0, -1 * soluong, soluong, nsx_fk);
					if( msg.trim().length() > 0 )
					{
						db.getConnection().rollback();
						return false;
					}
				}
				rs.close();
			}
			
			query = "delete ERP_CHUYENVITRI_SANPHAM_CHITIET where CHUYENVITRI_FK = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_CHUYENVITRI_SANPHAM_CHITIET " + query;
				db.getConnection().rollback();
				return false;
			}
			
			if(this.spList.size() > 0)
			{
				for(int i = 0; i < this.spList.size(); i++)
				{
					IYeucau sp = this.spList.get(i);
					
					if(sp.getSoluongYC().trim().length() > 0)
					{
						query = " insert ERP_CHUYENVITRI_SANPHAM(chuyenvitri_fk, SANPHAM_FK,  soluongchuyen, ghichu_chuyenkho  ) " +
								" values( '" + this.id + "', '" + sp.getId() + "', " + sp.getSoluongYC() + ", N'" + sp.getghichu_CK() + "' ) ";
						
						System.out.println("::: CHEN SAN PHAM: " + query);
						if(!db.update(query))
						{
							this.msg = "Không thể tạo mới ERP_CHUYENVITRI_SANPHAM: " + query;
							db.getConnection().rollback();
							return false;
						}
						
						//INSERT CHI TIET
						if(this.sanpham_soluong != null)
						{
							Enumeration<String> keys = this.sanpham_soluong.keys();
							double totalCT = 0;
							
							while(keys.hasMoreElements())
							{
								String key = keys.nextElement();
								
								if(key.startsWith( sp.getMa() + "__ "  ) )
								{
									String[] _sp = key.split("__");
									
									String _soluongCT = "0"; 
									if(this.sanpham_soluong.get(key) != null)
									{
										_soluongCT = this.sanpham_soluong.get(key).replaceAll(",", "");
									}
									
									String solo = _sp[2];
									String ngayhethan = _sp[3];
									String ngaynhapkho = _sp[4];
									String MAME = _sp[5];
									String MATHUNG = _sp[6];
									
									String bin_fk = _sp[7]; //Bin dang luu ma + ten ( cach nay moi load duoc, de select chon qua nhieu )
									if( bin_fk.contains(" [") )
										bin_fk = bin_fk.substring(bin_fk.indexOf(" [") + 2 , bin_fk.indexOf("]"));
									
									String MAPHIEU = _sp[8];
									String phieudt = _sp[9];
									String phieueo = _sp[10];
									String MARQ = _sp[11];
									String HAMLUONG = _sp[12];
									String HAMAM = _sp[13];
									
									String nsxMa = _sp[14];
									String nsx_fk = _sp[15];
									
									if(!_soluongCT.equals("0"))
									{
										totalCT += Double.parseDouble(_soluongCT);
										
										if( bin_fk.trim().length() <= 0 )
											bin_fk = "NULL";
										
										query = "insert ERP_CHUYENVITRI_SANPHAM_CHITIET( chuyenvitri_fk, SANPHAM_FK, scheme, solo, ngayhethan, ngaynhapkho, MAME, MATHUNG, binchuyen_fk, binnhan_fk, MAPHIEU, phieudt, phieueo, MARQ, HAMLUONG, HAMAM,  soluong, nsx_fk ) " +
												"select '" + this.id + "', pk_seq, ' ', N'" + solo + "', N'" + ngayhethan + "', '" + ngaynhapkho + "', '" + MAME + "', '" + MATHUNG + "', '" + this.vtcId + "', " + bin_fk + ", " + 
												" 	'" + MAPHIEU + "', '" + phieudt + "', '" + phieueo + "', '" + MARQ + "', '" + HAMLUONG + "', '" + HAMAM + "', '" + _soluongCT.replaceAll(",", "") + "', '" + nsx_fk + "' " +
												"from ERP_SANPHAM where MA = '" + sp.getMa() + "' ";
										
										System.out.println("1.2.Insert ERP_CHUYENVITRI_SANPHAM_CHITIET: " + query);
										if(!db.update(query))
										{
											this.msg = "Khong the tao moi ERP_CHUYENVITRI_SANPHAM_CHITIET: " + query;
											db.getConnection().rollback();
											return false;
										}
										
										//CẬP NHẬT KHO
										this.msg = util.Update_KhoTT_MOI(this.ngayyeucau, "Chuyển vị trí", db, this.khoXuatId, sp.getId(), solo, ngayhethan, ngaynhapkho, MAME, MATHUNG, this.vtcId,
														MAPHIEU, phieudt, phieueo, MARQ, HAMLUONG, HAMAM, this.loaidoituong, this.doituongId, 0, Double.parseDouble(_soluongCT.replaceAll(",", "")), -1 * Double.parseDouble(_soluongCT.replaceAll(",", "")), nsx_fk);
										if( this.msg.trim().length() > 0 )
										{
											db.getConnection().rollback();
											return false;
										}
									}
								}
							}
							
							//NEU TONG SO LUONG CT MA KHONG BANG TONG LUONG XUAT THI KO CHO LUU
							if(totalCT != Double.parseDouble(sp.getSoluongYC().replaceAll(",", "").trim()) )
							{
								this.msg = "Tổng xuất theo lô của sản phẩm ( " + sp.getMa() + " ) ( " + totalCT + " ) phải bằng tổng số lượng xuất ( " + sp.getSoluongYC() + " ) ";
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
		String query = "select PK_SEQ, TEN, LOAI from ERP_KHOTT where TrangThai = '1' ";
		query += " and pk_seq in " + util.quyen_khott(this.userId) ;
		query += " order by loai asc, TEN asc ";
			
		System.out.println("::: LAY KHO XUAT: " + query);
		this.khoXuatRs = db.get(query);

		if( this.khoXuatId.trim().length() > 0 )
		{
			query = "select PK_SEQ, MA + ', ' + TEN as TEN from ERP_BIN where KHOTT_FK = '" + this.khoXuatId + "' and trangthai = '1' ";
			
			System.out.println("::: LAY VI TRI CHUYEN: " + query);
			this.vitriChuyenRs = db.get(query + " order by TEN asc ");
			
			//if( this.vtcId.trim().length() > 0 )
				//query += " and pk_seq != '" + this.vtcId + "' ";
			
			//System.out.println("::: LAY VI TRI CHUYEN: " + query);
			//this.vitriNhanRs = db.getScrol(query + "order by TEN asc" );
		}
		
		if( ( this.khoXuatId.trim().length() > 0 || this.khoNhanId.trim().length() > 0 ) && this.spList.size() <= 0 && this.id.trim().length() > 0 )
		{
			this.createChuyenKho_SanPham();
		}

	}

	private void createChuyenKho_SanPham() 
	{
		String query =  "  select b.pk_seq as spId, isnull( b.MA,b.ma) as spMa, b.Ten as spTen,  	 " + 
						"  		isnull(DV.DIENGIAI, '') AS DVT, isnull(ghichu_chuyenkho,'') as ghichu_chuyenkho, ISNULL(a.SOLUONGCHUYEN, 0) as SOLUONGYEUCAU,  " + 
						"  		ISNULL(a.SOLUONGCHUYEN, 0) + ( select SUM(AVAILABLE) from ERP_KHOTT_SP_CHITIET where KHOTT_FK = ycck.khochuyen_fk and BIN_FK = ycck.binchuyen_fk and SANPHAM_FK = a.sanpham_fk ) as soluongton     " + 
						"  from ERP_CHUYENVITRI_SANPHAM a inner Join ERP_SanPham b on a.SANPHAM_FK = b.PK_SEQ   " + 
						"  	inner join ERP_CHUYENVITRI ycck on ycck.pk_seq = a.CHUYENVITRI_FK          " + 
						"  	LEFT JOIN DONVIDOLUONG DV ON DV.PK_SEQ = b.DVDL_FK    " + 
						"  where a.CHUYENVITRI_FK = '" + this.id + "'  ";

		System.out.println("__2.init SP: " + query );

		ResultSet spRs = db.get(query);
		List<IYeucau> spList = new ArrayList<IYeucau>();
		if(spRs != null)
		{
			try 
			{
				IYeucau sp = null;
				while(spRs.next())
				{
					String spId = spRs.getString("spId");
					String spMa = spRs.getString("spMa");
					String spTen = spRs.getString("spTen");

					String donvi = spRs.getString("DVT");

					sp = new Yeucau();
					sp.setId(spId);
					sp.setMa(spMa);
					sp.setghichu_CK(spRs.getString("ghichu_chuyenkho"));
					sp.setTen(spTen);
					sp.setDonViTinh(donvi);
					sp.setSoluongTon(spRs.getString("soluongton"));
					sp.setSoluongYC(spRs.getString("soluongyeucau"));

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

	public void init() 
	{
		String query =  "select NGAYCHUYEN, lydo, isnull(ghichu, '') as ghichu, khochuyen_fk, binchuyen_fk, trangthai,  " + 
						" 	loaidoituong, doituong_fk, chiphi_fk "+
						"from ERP_CHUYENVITRI where pk_seq = '" + this.id + "'";
		
		System.out.println("INIT CHUYENVITRI: "+query);
		ResultSet rs = db.get(query);

		try 
		{
			if(rs.next())
			{
				this.ngayyeucau = rs.getString("NGAYCHUYEN");
				this.lydoyeucau = rs.getString("lydo");
				this.ghichu = rs.getString("ghichu");
				this.khoXuatId = rs.getString("khochuyen_fk");
				this.vtcId = rs.getString("binchuyen_fk");
				this.trangthai = rs.getString("trangthai");

				this.loaidoituong = rs.getString("loaidoituong") == null ? "" : rs.getString("loaidoituong");
				this.doituongId = rs.getString("doituong_fk") == null ? "" : rs.getString("doituong_fk");
				
				this.chiphiId = rs.getString("chiphi_fk") == null ? "" : rs.getString("chiphi_fk");
			}
			rs.close();
			
			//INIT SO LUONG CHI TIET
			query = "select (select MA from ERP_SANPHAM where pk_seq = a.sanpham_fk ) as spMA,  solo, ngayhethan, ngaynhapkho, a.MAME, a.MATHUNG, a.MAPHIEU, marq, hamluong, hamam, soluong, ' ' as scheme, " +
					"	isnull( ( select ma from ERP_BIN where pk_seq = a.binchuyen_fk ), '' ) as vitriTEN, " + 
					"	isnull( ( select ma + ', ' + ten + ' [' + cast(pk_seq as varchar(10)) + ']' from ERP_BIN where pk_seq = a.binnhan_fk ), '' ) as vitriNHANTEN, isnull(a.phieudt, '') as phieudt, isnull(a.phieueo, '') as phieueo,	" +
					" isnull( (select isnull(MA + ' - ' + TEN, '') as NSXMA from ERP_NHASANXUAT where PK_SEQ = a.NSX_FK), '') as NSXMA, isnull(a.nsx_fk, 0) as NSX_FK " +
					"from ERP_CHUYENVITRI_SANPHAM_CHITIET a where chuyenvitri_fk = '" + this.id + "' ";
			
			System.out.println("---INIT SP CHI TIET: " + query);
			rs = db.get(query);
			if(rs != null)
			{
				Hashtable<String, String> sp_soluong = new Hashtable<String, String>();
				this.sanpham_vitriNhan = new Hashtable<String, String>();
				while(rs.next())
				{
					String key = rs.getString("spMA") + "__ " + "__" + rs.getString("solo") + "__" + rs.getString("ngayhethan") + "__" + rs.getString("ngaynhapkho") 
									+ "__" + rs.getString("MAME") + "__" + rs.getString("MATHUNG") + "__" + rs.getString("vitriTEN")
									+ "__" + rs.getString("MAPHIEU") + "__" + rs.getString("phieudt") + "__" + rs.getString("phieueo")
									+ "__" + rs.getString("marq") + "__" + rs.getString("hamluong") + "__" + rs.getString("hamam")
									+ "__" + rs.getString("NSXMA") + "__" + rs.getString("NSX_FK");
					
					String key2 = rs.getString("spMA") + "__ " + "__" + rs.getString("solo") + "__" + rs.getString("ngayhethan") + "__" + rs.getString("ngaynhapkho") 
									+ "__" + rs.getString("MAME") + "__" + rs.getString("MATHUNG") 
									+ "__" + rs.getString("MAPHIEU") + "__" + rs.getString("phieudt") + "__" + rs.getString("phieueo")
									+ "__" + rs.getString("marq") + "__" + rs.getString("hamluong") + "__" + rs.getString("hamam")
									+ "__" + rs.getString("NSXMA") + "__" + rs.getString("NSX_FK");
					
					System.out.println("---KEY CHUYEN: " + key + " -- SO LUONG: " + rs.getString("soluong") );
					System.out.println("---KEY NHAN: " + key2);
					//System.out.println("---BIN: " + rs.getString("vitriNHANTEN"));
					
					sp_soluong.put(key, rs.getString("soluong") );
					this.sanpham_vitriNhan.put(key2, rs.getString("vitriNHANTEN") == null ? "" : rs.getString("vitriNHANTEN") );
				}
				rs.close();
				
				this.sanpham_soluong = sp_soluong;
				this.sanpham_soluongDAXUAT = sp_soluong;
			}
			
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}

		this.createChuyenKho_SanPham();
		this.createRs();
			
	}
	
	public void initDisplay() 
	{
		String query =  "select NGAYCHUYEN, lydo, isnull(ghichu, '') as ghichu, khochuyen_fk, binchuyen_fk, trangthai,  " + 
						" 	loaidoituong, doituong_fk, chiphi_fk "+
						"from ERP_CHUYENVITRI where pk_seq = '" + this.id + "'";
		
		System.out.println("INIT CHUYENVITRI: "+query);
		ResultSet rs = db.get(query);

		try 
		{
			if(rs.next())
			{
				this.ngayyeucau = rs.getString("NGAYCHUYEN");
				this.lydoyeucau = rs.getString("lydo");
				this.ghichu = rs.getString("ghichu");
				this.khoXuatId = rs.getString("khochuyen_fk");
				this.vtcId = rs.getString("binchuyen_fk");
				this.trangthai = rs.getString("trangthai");

				this.loaidoituong = rs.getString("loaidoituong") == null ? "" : rs.getString("loaidoituong");
				this.doituongId = rs.getString("doituong_fk") == null ? "" : rs.getString("doituong_fk");
				
				this.chiphiId = rs.getString("chiphi_fk") == null ? "" : rs.getString("chiphi_fk");
			}
			rs.close();
			
			//INIT SO LUONG CHI TIET
			query = "select (select MA from ERP_SANPHAM where pk_seq = a.sanpham_fk ) as spMA,  solo, ngayhethan, ngaynhapkho, a.MAME, a.MATHUNG, a.MAPHIEU, marq, hamluong, hamam, soluong, ' ' as scheme, " +
					"	isnull( ( select ma from ERP_BIN where pk_seq = a.binnhan_fk ), '' ) as vitriTEN, isnull(a.phieudt, '') as phieudt, isnull(a.phieueo, '') as phieueo,	" +
					" isnull( (select isnull(MA + ' - ' + TEN, '') as NSXMA from ERP_NHASANXUAT where PK_SEQ = a.NSX_FK), '') as NSXMA, isnull(a.NSX_FK, 0) as NSX_FK " +
					"from ERP_CHUYENVITRI_SANPHAM_CHITIET a where chuyenvitri_fk = '" + this.id + "'";
			
			System.out.println("---INIT SP CHI TIET DISPLAY: " + query);
			rs = db.get(query);
			if(rs != null)
			{
				Hashtable<String, String> sp_soluong = new Hashtable<String, String>();
				while(rs.next())
				{
					String key = rs.getString("spMA") + "__ " + "__" + rs.getString("solo") + "__" + rs.getString("ngayhethan") + "__" + rs.getString("ngaynhapkho") 
									+ "__" + rs.getString("MAME") + "__" + rs.getString("MATHUNG") + "__" + rs.getString("vitriTEN")
									+ "__" + rs.getString("MAPHIEU") + "__" + rs.getString("phieudt") + "__" + rs.getString("phieueo")
									+ "__" + rs.getString("marq") + "__" + rs.getString("hamluong") + "__" + rs.getString("hamam")
									+ "__" + rs.getString("NSXMA") + "__" + rs.getString("NSX_FK");
					
					sp_soluong.put(key, rs.getString("soluong") );
				}
				rs.close();
				
				this.sanpham_soluong = sp_soluong;
			}
			
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}

		this.createChuyenKho_SanPham();
		this.createRs();
			
	}

	public void DBclose() {
		
		try{
			
			if(khoNhanRs!=null){
				khoNhanRs.close();
			}
			
			if(lsxRs!=null){
				lsxRs.close();
			}
			if(spList!=null){
				spList.clear();
			}

			if(khoXuatRs!=null){
				khoXuatRs.close();
			}
			this.db.shutDown();
			
		}catch(Exception er){
			
		}
	}
	
	public String getLsxIds()
	{
		return this.lsxIds;
	}

	public void setLsxIds(String lsxIds) 
	{
		this.lsxIds = lsxIds;
	}

	public ResultSet getLsxRs()
	{
		return this.lsxRs;
	}

	public void setLsxRs(ResultSet lsxRs) 
	{
		this.lsxRs = lsxRs;
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

	public String getNccChuyenIds() {
		
		return this.nccXuatId;
	}

	
	public void setNccChuyenIds(String nccChuyenIds) {
		
		this.nccXuatId = nccChuyenIds;
	}

	
	public ResultSet getNccChuyenRs() {
		
		return this.nccXuatRs;
	}

	
	public void setNccChuyenRs(ResultSet nccChuyenRs) {
		
		this.nccXuatRs = nccChuyenRs;
	}

	
	public String getNccNhanIds() {
		
		return this.nccNhanId;
	}

	
	public void setNccNhanIds(String nccNhanIds) {
		
		this.nccNhanId = nccNhanIds;
	}

	
	public ResultSet getNccNhanRs() {
		
		return this.nccNhanRs;
	}

	
	public void setNccNhanRs(ResultSet nccNhanRs) {
		
		this.nccNhanRs = nccNhanRs;
	}

	
	public String getKhoXuatTen() {
		return this.khoXuatTen;
	}

	
	public void setKhoXuatTen(String khoxuattt) {
		this.khoXuatTen = khoxuattt;
	}

	
	public String getKhoNhapTen() {
		return this.khoNhanTen;
	}

	
	public void setKhoNhapTen(String khonhaptt) {
		this.khoNhanTen = khonhaptt;
	}

	
	public String getGhichu() {
		
		return this.ghichu;
	}

	
	public void setGhichu(String ghichu) {
		
		this.ghichu = ghichu;
	}

	public String getTongSLYC() 
	{
		return this.tongSLYC;
	}

	public void setTongSLYC(String tongSLYC) 
	{
		this.tongSLYC = tongSLYC;
		
	}

	public String getCtyId() {
		return this.ctyId;
	}

	
	public void setCtyId(String ctyId) {
		this.ctyId = ctyId;
	}

	
	public String gettask() {
		
		return this.task;
	}

	
	public void settask(String task) {
		
		this.task=task;
	}

	
	public String getKyHieu() {
		
		return this.kyhieu;
	}

	
	public void setKyHieu(String kyhieu) {
		
		this.kyhieu=kyhieu;
	}

	
	public String getSochungtu() {
		
		return this.sochungtu;
	}

	
	public void setSochungtu(String sochungtu) {
		
		this.sochungtu=sochungtu;
	}

	public String getCoKhonhan() {
		
		return this.cokhonhan;
	}

	
	public void setCoKhonhan(String cokhoNHAN) {
		
		this.cokhonhan = cokhoNHAN;
	}

	
	public String getLoaidoituongId() {
		
		return this.loaidoituong;
	}

	
	public void setLoaidoituongId(String loaidoituong) {
		
		this.loaidoituong = loaidoituong;
	}

	
	public ResultSet getDoituongRs() {
		
		return this.doituongRs;
	}

	
	public void setDoituongRs(ResultSet doituongRs) {
		
		this.doituongRs = doituongRs;
	}


	public String getCoDoituong() {

		return this.codoituong;
	}


	public void setCoDoituong(String codoituong) {
		
		this.codoituong = codoituong;
	}

	
	public String getCoDoituongNhan() {
		
		return this.codoituongNhan;
	}

	
	public void setCoDoituongNhan(String codoituongNhan) {
		
		this.codoituongNhan = codoituongNhan;
	}

	
	public String getLoaidoituongNhanId() {
		
		return this.loaidoituongNhan;
	}

	
	public void setLoaidoituongNhanId(String loaidoituongNhan) {
		
		this.loaidoituongNhan = loaidoituongNhan;
	}

	
	public ResultSet getDoituongNhanRs() {
		
		return this.doituongNhanRs;
	}

	
	public void setDoituongNhanRs(ResultSet doituongNhanRs) {
		
		this.doituongNhanRs = doituongNhanRs;
	}

	
	public String getDoituongId() {
		
		return this.doituongId;
	}

	
	public void setDoituongId(String doituongId) {
		
		this.doituongId = doituongId;
	}

	
	public String getDoituongNhanId() {
		
		return this.doituongNhanId;
	}

	
	public void setDoituongNhanId(String doituongNhanId) {
		
		this.doituongNhanId = doituongNhanId;
	}

	public ResultSet getSoloTheoSp(String spMa, String donvi, String tongluong)
	{
		//Nếu vào cập nhật, thì chỉ lấy danh sách đã lưu trước đó, không đề xuất lại nữa
		if( this.id.trim().length() > 0 )
			tongluong = "0";
		
		tongluong = tongluong.replaceAll(",", "");
		//System.out.println("---TONG LUONG: " + tongluong );
		
		String soloDACHON = "";
		if( this.sanpham_soluongDAXUAT != null )
		{
			//KEY: MA - SOLO; 
			Enumeration<String> keys = this.sanpham_soluongDAXUAT.keys();
			while( keys.hasMoreElements() )
			{
				String key = keys.nextElement();
				if(key.startsWith(spMa))
				{
					String solo = key.split("__")[2];
					String ngayhethan = key.split("__")[3];
					String ngaynhapkho = key.split("__")[4];
					
					String mame = key.split("__")[5];
					String mathung = key.split("__")[6];
					String vitri = key.split("__")[7];
					
					String maphieu = key.split("__")[8];
					String phieudt = key.split("__")[9];
					String phieueo = key.split("__")[10];
					
					String marq = key.split("__")[11];
					String hamluong = key.split("__")[12];
					String hamam = key.split("__")[13];
					
					String nsxma = key.split("__")[14];
					String nsx_fk = key.split("__")[15];
					
					String daxuat =  this.sanpham_soluongDAXUAT.get(key);
					
					if( daxuat.trim().length() > 0 )
					{
						soloDACHON += "select '" + solo + "' as soloCHON, '" + ngayhethan + "' as ngayhethanCHON, '" + ngaynhapkho + "' as ngaynhapkhoCHON, " + daxuat + " as soluongDACHON, '" + mame + "' as mame, '" + mathung + "' as mathung, '" + maphieu + "' as maphieu, " + 
									  " '" + marq + "' as marq, '" + hamluong + "' as hamluong, '" + hamam + "' as hamam, " + 
									  " '" + vitri + "' as vitri, '" + phieudt + "' as phieudt, '" + phieueo + "' as phieueo, "
									  + nsx_fk + " as nsx_fk  union ";
					}
				}
			}
		}
		
		if( soloDACHON.trim().length() > 0 )
		{
			soloDACHON = soloDACHON.substring(0, soloDACHON.length() - 7 );
			soloDACHON = " select soloCHON, ngayhethanCHON, ngaynhapkhoCHON, mame, mathung, maphieu, marq, hamluong, hamam, isnull((select pk_seq from ERP_BIN where ma = DT.vitri), 0) as vitri, phieudt, phieueo, isnull(nsx_fk, 0) as nsx_fk, SUM( soluongDACHON ) as soluongDACHON " +
						 " from ( " + soloDACHON + " ) DT group by soloCHON, ngayhethanCHON, ngaynhapkhoCHON, mame, mathung, maphieu, marq, hamluong, hamam, vitri, phieudt, phieueo, nsx_fk ";
		}
		else
			soloDACHON = " select '1' as soloCHON, '' as ngayhethanCHON, '' as ngaynhapkhoCHON, '' as mame, '' as mathung, '' as maphieu, '' as marq, '' as hamluong, '' as hamam, '0' vitri, '' phieudt, '' phieueo,'0' as nsx_fk, 0 as soluongDACHON ";
		
		String sqlDONHANG = "";
		sqlDONHANG = " select SUM(soluong) from ERP_CHUYENVITRI_SANPHAM_CHITIET " + 
					 " where chuyenvitri_fk = '" + ( this.id.trim().length() > 0 ? this.id : "1" ) + "' and SANPHAM_FK = DT.sanpham_fk " + 
					 		" and solo = DT.solo and ngayhethan = DT.ngayhethan and ngaynhapkho = dt.ngaynhapkho " + 
					 		" and isnull(mame, '') = isnull(DT.mame, '') and isnull(mathung, '') = isnull(DT.mathung, '') and isnull(maphieu, '') = isnull(DT.maphieu, '')  " +		
							" and isnull(marq, '') = isnull(DT.marq, '') and isnull(hamluong, '100') = isnull(DT.hamluong, '100') and isnull(hamam, '0') = isnull(DT.hamam, '0')  " + 
							" and isnull(binchuyen_fk, 0) = isnull(DT.vitri, 0) and isnull(phieudt, '') = isnull(DT.phieudt, '') and isnull(phieueo, '') = isnull(DT.phieueo, '')  " +
							" and isnull(nsx_fk, 0) = isnull(DT.nsx_fk, 0) ";;
		
		
		String query = "";
		query =  "\n select DT.NGAYNHAPKHO, DT.NGAYHETHAN, DT.SOLO, isnull(DT.MAME, '') as MAME, DT.MATHUNG, isnull(DT.MAPHIEU, '') as MAPHIEU, " + 
				 "\n  		isnull(DT.MARQ, '') as MARQ, DT.hamluong, DT.hamam, isnull(bin.MA, '') as vitriTEN, DT.vitri, DT.phieudt, DT.phieueo, isnull(DT.nsx_fk, 0) as nsx_fk, isnull(nsx.MA + ' - ' + nsx.TEN, '') as nsxMa, " +
				 " 		DT.AVAILABLE + isnull( ( " + sqlDONHANG + " ), 0) - isnull(DAXUAT.soluongDACHON, 0) as AVAILABLE  "+
				 "\n from "+
				 "\n ( "+
				 //"	select ct.sanpham_fk, dbo.LayQuyCach_DVBan( ct.SANPHAM_FK, null, " + donvi + " ) * ct.AVAILABLE  as AVAILABLE, NGAYHETHAN,  ngaynhapkho ,SOLO  "+
				 "\n 	select ct.sanpham_fk, ct.AVAILABLE, NGAYHETHAN, ngaynhapkho, SOLO, MAME, MATHUNG, MAPHIEU, MARQ, isnull(HAMLUONG, '100') as HAMLUONG, isnull(HAMAM, '0') as HAMAM, isnull( bin_fk, 0 ) as vitri, isnull( maphieudinhtinh, '' ) as phieudt, isnull( phieueo, '' ) as phieueo, isnull( nsx_fk, 0 ) as nsx_fk    "+
				 "\n 	from ERP_KHOTT_SP_CHITIET ct inner join ERP_SANPHAM sp on ct.sanpham_fk = sp.pk_seq  "+
				 "\n 	where KHOTT_FK = '" + this.khoXuatId + "' and SANPHAM_FK = ( select pk_seq from ERP_SANPHAM where ma = '" + spMa + "' )   " +
				 		"  and ngaynhapkho <= '" + this.ngayyeucau + "' and ct.bin_fk = '" + this.vtcId + "' "+
				 "\n ) "+
				 "\n DT left join ERP_BIN bin on DT.vitri = bin.pk_seq " 
				 + "left join ERP_NHASANXUAT nsx on DT.nsx_fk = nsx.pk_seq "
				 + " left join  "+
				 "\n ( "+
				 	soloDACHON +
				 "\n ) "+
				 "\n DAXUAT on DT.SOLO = DAXUAT.soloCHON  and DT.NGAYHETHAN = DAXUAT.ngayhethanCHON and DT.NGAYNHAPKHO = DAXUAT.ngaynhapkhoCHON  "+
				 "\n and isnull(DT.MAME, '') = isnull(DAXUAT.MAME, '') and isnull(DT.MATHUNG, '') = isnull(DAXUAT.MATHUNG, '') and isnull(DT.MAPHIEU, '') = isnull(DAXUAT.MAPHIEU, '')  " +
				 "\n and isnull(DT.MARQ, '') = isnull(DAXUAT.MARQ, '') and isnull(DT.HAMLUONG, '100') = isnull(DAXUAT.HAMLUONG, '100') and isnull(DT.HAMAM, '0') = isnull(DAXUAT.HAMAM, '0')  " +
				 "\n and isnull(DT.vitri, '') = isnull(DAXUAT.vitri, '') and isnull(DT.phieudt, '') = isnull(DAXUAT.phieudt, '') and isnull(DT.phieueo, '') = isnull(DAXUAT.phieueo, '' )  " +
				 "\n and isnull(DT.nsx_fk, 0) = isnull(DAXUAT.nsx_fk, 0) " +
				 "\n where DT.AVAILABLE + isnull( ( " + sqlDONHANG + " ), 0) - isnull(DAXUAT.soluongDACHON, 0) > 0 "+
				 //"\n			and DT.SOLO not in ( select solo from ERP_HANGCHOPHANBO where sanpham_fk = ( select pk_seq from SANPHAM where ma = '" + spMa + "' ) )	" +
				 "\n  order by NGAYHETHAN, dt.mathung  asc  ";
		
		System.out.println("----LAY SO LO ( " + spMa + " ): " + query );
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
					
				if(slg > 0)
				{
					query2 += "select '" + avai + "' as AVAILABLE, '" + rs.getString("NGAYHETHAN") + "' as NGAYHETHAN, '" + rs.getString("NGAYNHAPKHO") + "' as NGAYNHAPKHO, " + 
							" '" + rs.getString("MAME") + "' as MAME, '" + rs.getString("MATHUNG") + "' as MATHUNG, '" + rs.getString("MAPHIEU") + "' as MAPHIEU, " + 
							" '" + rs.getString("MARQ") + "' as MARQ, '" + rs.getString("HAMLUONG") + "' as HAMLUONG, '" + rs.getString("HAMAM") + "' as HAMAM, " + 
							" '" + rs.getString("vitriTEN") + "' as vitri, '" + rs.getString("PHIEUDT") + "' as PHIEUDT, '" + rs.getString("PHIEUEO") + "' as PHIEUEO, " + 
							" '" + rs.getString("nsxMa") + "' as nsxMa, '" + rs.getString("nsx_fk") + "' as nsx_fk, " +
							" '" + rs.getString("SOLO") + "' as SOLO, '" + slg + "' as tuDEXUAT union ALL ";
					
				}
				else
				{
					query2 += "select '" + avai + "' as AVAILABLE, '" + rs.getString("NGAYHETHAN") + "' as NGAYHETHAN, '" + rs.getString("NGAYNHAPKHO") + "' as NGAYNHAPKHO, " + 
							" '" + rs.getString("MAME") + "' as MAME, '" + rs.getString("MATHUNG") + "' as MATHUNG, '" + rs.getString("MAPHIEU") + "' as MAPHIEU, "+ 
							" '" + rs.getString("MARQ") + "' as MARQ, '" + rs.getString("HAMLUONG") + "' as HAMLUONG, '" + rs.getString("HAMAM") + "' as HAMAM, " + 
							" '" + rs.getString("vitriTEN") + "' as vitri, '" + rs.getString("PHIEUDT") + "' as PHIEUDT, '" + rs.getString("PHIEUEO") + "' as PHIEUEO, " + 
							" '" + rs.getString("nsxMa") + "' as nsxMa, '" + rs.getString("nsx_fk") + "' as nsx_fk, " +
							" '" + rs.getString("SOLO") + "' as SOLO, '' as tuDEXUAT union ALL ";
				}
			}
			rs.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
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
	
	public Hashtable<String, String> getSanpham_SoluongDAXUAT_THEODN() 
	{
		return this.sanpham_soluongDAXUAT;
	}


	public void setSanpham_SoluongDAXUAT_THEODN(Hashtable<String, String> sp_soluong) 
	{
		this.sanpham_soluongDAXUAT = sp_soluong;
	}

	public ResultSet getVitriNhanRs()
	{
		return this.vitriNhanRs;
	}

	public void setVitriNhanRs(ResultSet vitriNhanRs) 
	{
		this.vitriNhanRs = vitriNhanRs;
	}
	
	public String getCochiphi() {
		
		return this.coChiphi;
	}

	
	public void setCochiphi(String coChiphi) {
		
		this.coChiphi = coChiphi;
	}

	
	public String getChiphiId() {
		
		return this.chiphiId;
	}

	
	public void setChiphiId(String chiphiId) {
		
		this.chiphiId = chiphiId;
	}

	
	public ResultSet getChiphiRs() {
		
		return this.chiphiRs;
	}

	
	public void setChiphiRs(ResultSet chiphiRs) {
		
		this.chiphiRs = chiphiRs;
	}

	
	public String getVitrichuyenId() {
		
		return this.vtcId;
	}

	
	public void setVitrichuyenId(String vitriChuyenId) {
		
		this.vtcId = vitriChuyenId;
	}

	
	public ResultSet getVitriChuyenRs() {
		
		return this.vitriChuyenRs;
	}

	
	public void setVitriChuyenRs(ResultSet vitriChuyenRs) {
		
		this.vitriChuyenRs = vitriChuyenRs;
	}

	
	public Hashtable<String, String> getSanpham_Vitrinhan() 
	{
		return this.sanpham_vitriNhan;
	}

	public void setSanpham_Vitrinhan(Hashtable<String, String> sp_vitri)
	{
		this.sanpham_vitriNhan = sp_vitri;
	}
	
	public static void main(String[] arg)
	{
		String bin_fk = "1E11.PL24, Kho BB cấp I- số 4 tầng 5 NĐH - Palet 24 [101694]"; //Bin dang luu ma + ten ( cach nay moi load duoc, de select chon qua nhieu )
		//String bin_fk = "1E11.PL24, - Palet 24 [101694]"; 
		
		System.out.println(" -- START: " + ( bin_fk.indexOf(" [") + 2 ) );
		System.out.println(" -- END: " + ( bin_fk.indexOf("]") ) );
		
		//bin_fk = "1E11.PL24,";
		if( bin_fk.contains(" [") )
			bin_fk = bin_fk.substring(bin_fk.indexOf(" [") + 2 , bin_fk.indexOf("]"));
		
		System.out.println(" -- BIN: " + ( bin_fk ) );
		
	}

	 
}
