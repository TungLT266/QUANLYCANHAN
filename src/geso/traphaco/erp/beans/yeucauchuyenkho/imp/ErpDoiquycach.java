package geso.traphaco.erp.beans.yeucauchuyenkho.imp;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.yeucauchuyenkho.IErpDoiquycach;
import geso.traphaco.erp.beans.yeucauchuyenkho.IYeucau;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
 
public class ErpDoiquycach implements IErpDoiquycach
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
	List<IYeucau> spNhanList;

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
	
	Hashtable<String, String> sanpham_soluongNHAN;
	
	String vtcId;
	ResultSet vitriChuyenRs;
	ResultSet vitriNhanRs;
	
	String[] sotaikhoanCP;
	String[] chiphi;
	
	public ErpDoiquycach()
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
		this.spNhanList = new ArrayList<IYeucau>();
		
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
		
		this.sanpham_soluongNHAN = new Hashtable<String, String>();
		
		this.vtcId = "";
		
		this.db = new dbutils();
	}
	
	public ErpDoiquycach(String id)
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
		this.spNhanList = new ArrayList<IYeucau>();

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
		
		this.sanpham_soluongNHAN = new Hashtable<String, String>();

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
			this.msg = "Vui lòng nhập ngày chứng từ";
			return false;
		}
		
		if(this.khoXuatId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn kho đổi";
			return false;
		}
		
		if(this.khoNhanId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn kho nhập";
			return false;
		}
				
		if( this.sanpham_soluongNHAN == null || this.sanpham_soluongNHAN.size() <= 0 )
		{
			this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm nhận đổi quy cách";
			return false;
		}
		
		if(this.spList.size() <= 0)
		{
			this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm đổi quy cách";
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
						this.msg = "Sản phẩm ( " + yc.getTen() + " ) trong danh sách đổi quy cách đã trùng. Vui lòng kiểm tra lại.";
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
			
			String query = 	" insert ERP_DOIQUYCACH(NgayDoi, lydo, ghichu, trangthai, khoxuat_fk, khonhan_fk,  ngaytao, nguoitao, ngaysua, nguoisua, loaidoituong, DOITUONG_FK ) " +
				   			" values('" + this.ngayyeucau + "', N'" + this.lydoyeucau + "', N'" + this.ghichu + "', '0', '" + this.khoXuatId + "', " + this.khoNhanId + ",  '" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "', " + _loaidoituongId + ", " + _doituongId + " ) ";
			
			System.out.println("::: CHEN ERP_DOIQUYCACH: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_DOIQUYCACH " + query;
				db.getConnection().rollback();
				return false;
			}
			
			String ycnlCurrent = "";
			query = "select IDENT_CURRENT('ERP_DOIQUYCACH') as ckId";
			
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
						query = " insert ERP_DOIQUYCACH_SANPHAM(doiquycach_fk, SANPHAM_FK,  soluong, dongia, stt  ) " +
								" values( '" + ycnlCurrent + "', '" + sp.getId() + "', " + sp.getSoluongYC() + ", '" + sp.getDongia().replaceAll(",", "") + "', '" + i + "' ) ";
						
						System.out.println("::: CHEN SAN PHAM: " + query);
						if(!db.update(query))
						{
							this.msg = "Không thể tạo mới ERP_DOIQUYCACH_SANPHAM: " + query;
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
								
								System.out.println("::: KEY UPDATE: " + key );
								if(key.startsWith( sp.getMa() + "__ "  ) )
								{
									String[] _sp = key.split("__");
									
									String _soluongCT = "0"; 
									if(this.sanpham_soluong.get(key) != null)
									{
										_soluongCT = this.sanpham_soluong.get(key).replaceAll(",", "");
									}
									
									System.out.println("::: KEY UPDATE: " + key + " -- SO LUONG CT: " + _soluongCT );
									String solo = _sp[2];
									String ngayhethan = _sp[3];
									String ngaynhapkho = _sp[4];
									String MAME = _sp[5];
									String MATHUNG = _sp[6];
									String bin_fk = _sp[7];
									String MAPHIEU = _sp[8];
									String phieudt = _sp[9];
									String phieueo = _sp[10];
									String MARQ = _sp[11];
									String HAMLUONG = _sp[12];
									String HAMAM = _sp[13];
									
									if(!_soluongCT.equals("0"))
									{
										totalCT += util.Round(Double.parseDouble(_soluongCT), 3);
										System.out.println("::: TOTAL BUOC NAY: " + totalCT + " -- SO LAM TRON: " + util.Round(Double.parseDouble(_soluongCT), 3));
										
										query = "insert ERP_DOIQUYCACH_SANPHAM_CHITIET( doiquycach_fk, SANPHAM_FK, scheme, solo, ngayhethan, ngaynhapkho, MAME, MATHUNG, bin_fk, MAPHIEU, phieudt, phieueo, MARQ, HAMLUONG, HAMAM,  soluong, stt ) " +
												"select '" + ycnlCurrent + "', pk_seq, ' ', N'" + solo + "', N'" + ngayhethan + "', '" + ngaynhapkho + "', '" + MAME + "', '" + MATHUNG + "', ( select PK_SEQ from ERP_BIN where KHOTT_FK = '" + this.khoXuatId + "' and MA = N'" + bin_fk + "' ), " + 
												" 	'" + MAPHIEU + "', '" + phieudt + "', '" + phieueo + "', '" + MARQ + "', '" + HAMLUONG + "', '" + HAMAM + "', '" + _soluongCT.replaceAll(",", "") + "', '" + i + "' " +
												"from ERP_SANPHAM where MA = '" + sp.getMa() + "' ";
										
										//System.out.println("1.2.Insert ERP_CHUYENKHO_SANPHAM_CHITIET: " + query);
										if(!db.update(query))
										{
											this.msg = "Khong the tao moi ERP_DOIQUYCACH_SANPHAM_CHITIET: " + query;
											db.getConnection().rollback();
											return false;
										}
										
										//CẬP NHẬT KHO
										this.msg = util.Update_KhoTT(this.ngayyeucau, "Đổi quy cách " + ycnlCurrent, db, this.khoXuatId, sp.getId(), solo, ngayhethan, ngaynhapkho, MAME, MATHUNG, " isnull( ( select PK_SEQ from ERP_BIN where KHOTT_FK = '" + this.khoXuatId + "' and MA = N'" + bin_fk + "' ) , 0) ",
														MAPHIEU, phieudt, phieueo, MARQ, HAMLUONG, HAMAM, this.loaidoituong, this.doituongId, 0, Double.parseDouble(_soluongCT.replaceAll(",", "")), -1 * Double.parseDouble(_soluongCT.replaceAll(",", "")));
										if( this.msg.trim().length() > 0 )
										{
											db.getConnection().rollback();
											return false;
										}
									}
								}
							}
							
							//NEU TONG SO LUONG CT MA KHONG BANG TONG LUONG XUAT THI KO CHO LUU
							//LAM TRONG TUNG SO ROI, MA XUONG DUOI JAVA NO VAN DE LE CA CHUC SO DANG SAU, NEN PHAI LAM TRONG LAN NUA
							if(util.Round(totalCT, 3) != Double.parseDouble(sp.getSoluongYC().replaceAll(",", "").trim()) )
							{
								this.msg = "Tổng xuất theo lô của sản phẩm ( " + sp.getMa() + " ) ( " + totalCT + " ) phải bằng tổng số lượng xuất ( " + sp.getSoluongYC() + " ) ";
								db.getConnection().rollback();
								return false;
							}
						}
					}
				}
			}
			
			//SAN PHẨM NHẬN
			if(this.spNhanList.size() > 0)
			{
				for(int i = 0; i < this.spNhanList.size(); i++)
				{
					IYeucau sp = this.spNhanList.get(i);
					
					if(sp.getSoluongYC().trim().length() > 0)
					{
						query = " insert ERP_DOIQUYCACH_SANPHAM( doiquycach_fk, SANPHAM_FK,  soluong, dongia, dongiasauPB, isNhandoi, stt  ) " +
								" values( '" + ycnlCurrent + "', '" + sp.getId() + "', " + sp.getSoluongYC() + ", '" + sp.getDongia().replaceAll(",", "") + "', '" + sp.getDongia2().replaceAll(",", "") + "', 1, '" + i + "' ) ";
						
						System.out.println("::: CHEN SAN PHAM: " + query);
						if(!db.update(query))
						{
							this.msg = "Không thể tạo mới ERP_DOIQUYCACH_SANPHAM: " + query;
							db.getConnection().rollback();
							return false;
						}
						
						//INSERT CHI TIET
						if(this.sanpham_soluongNHAN != null)
						{
							Enumeration<String> keys = this.sanpham_soluongNHAN.keys();
							double totalCT = 0;
							
							while(keys.hasMoreElements())
							{
								String key = keys.nextElement();
								
								System.out.println("::: KEY NHAN UPDATE: " + key );
								if(key.startsWith( i + "__"  ) )
								{
									String[] _sp = Utility.mySplit(key, "__");
									
									String _soluongCT = "0"; 
									if(this.sanpham_soluongNHAN.get(key) != null)
										_soluongCT = this.sanpham_soluongNHAN.get(key).replaceAll(",", "");
									
									System.out.println("::: KEY NHAN UPDATE: " + key + " -- SO LUONG CT: " + _soluongCT );
									String solo = _sp[2];
									String ngayhethan = _sp[3];
									String ngaynhapkho = this.ngayyeucau;
									String MAME = _sp[4];
									String MATHUNG = _sp[5];
									
									String bin_fk = _sp[6];
									if( bin_fk.contains(" [") )
										bin_fk = bin_fk.substring(bin_fk.indexOf(" [") + 2 , bin_fk.indexOf("]"));
									
									String MAPHIEU = _sp[7];
									String phieudt = _sp[8];
									String phieueo = _sp[9];
									String MARQ = _sp[10];
									String HAMLUONG = _sp[11];
									String HAMAM = _sp[12];
									
									if(!_soluongCT.equals("0"))
									{
										totalCT += util.Round(Double.parseDouble(_soluongCT), 3);
										System.out.println("::: TOTAL BUOC NAY: " + totalCT + " -- SO LAM TRON: " + util.Round(Double.parseDouble(_soluongCT), 3));
										
										query = "insert ERP_DOIQUYCACH_SANPHAM_CHITIET_NHANDOI( doiquycach_fk, SANPHAM_FK, solo, ngayhethan, ngaynhapkho, MAME, MATHUNG, bin_fk, MAPHIEU, phieudt, phieueo, MARQ, HAMLUONG, HAMAM,  soluong, stt ) " +
												"select '" + ycnlCurrent + "', pk_seq, N'" + solo + "', N'" + ngayhethan + "', '" + ngaynhapkho + "', '" + MAME + "', '" + MATHUNG + "', ( select PK_SEQ from ERP_BIN where KHOTT_FK = '" + this.khoXuatId + "' and MA = N'" + bin_fk + "' ), " + 
												" 	'" + MAPHIEU + "', '" + phieudt + "', '" + phieueo + "', '" + MARQ + "', '" + HAMLUONG + "', '" + HAMAM + "', '" + _soluongCT.replaceAll(",", "") + "', '" + i + "' " +
												"from ERP_SANPHAM where MA = '" + sp.getMa() + "' ";
										
										//System.out.println("1.2.Insert ERP_CHUYENKHO_SANPHAM_CHITIET: " + query);
										if(!db.update(query))
										{
											this.msg = "Khong the tao moi ERP_DOIQUYCACH_SANPHAMNHAN_CHITIET: " + query;
											db.getConnection().rollback();
											return false;
										}
									}
								}
							}
							
							//NEU TONG SO LUONG CT MA KHONG BANG TONG LUONG XUAT THI KO CHO LUU
							//LAM TRONG TUNG SO ROI, MA XUONG DUOI JAVA NO VAN DE LE CA CHUC SO DANG SAU, NEN PHAI LAM TRONG LAN NUA
							if(util.Round(totalCT, 3) != Double.parseDouble(sp.getSoluongYC().replaceAll(",", "").trim()) )
							{
								this.msg = "Tổng xuất theo lô của sản phẩm ( " + sp.getMa() + " ) ( " + totalCT + " ) phải bằng tổng số lượng xuất ( " + sp.getSoluongYC() + " ) ";
								db.getConnection().rollback();
								return false;
							}
						}
					}
				}
			}
			
			//CHi phi
			if( this.sotaikhoanCP != null )
			{
				for( int i = 0; i < this.sotaikhoanCP.length; i++ )
				{
					if( this.chiphi[i].trim().length() > 0 && this.sotaikhoanCP[i].trim().length() > 0 )
					{
						String sotaikhoan = this.sotaikhoanCP[i].split(" - ")[0];
						query = "insert ERP_DOIQUYCACH_CHIPHI( doiquycach_fk, taikhoan_fk, chiphi ) " + 
								"select '" + ycnlCurrent + "', pk_seq, '" + this.chiphi[i].replaceAll(",", "") + "' " + 
								"from ERP_TAIKHOANKT where sohieutaikhoan = '" + sotaikhoan + "' and npp_fk = '1' ";
						
						System.out.println("::: CHI PHI: " + query);
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi ERP_DOIQUYCACH_CHIPHI: " + query;
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
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	public boolean updateCK() 
	{ 
		if(this.ngayyeucau.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập ngày chứng từ";
			return false;
		}
		
		if(this.khoXuatId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn kho đổi";
			return false;
		}
		
		if(this.khoNhanId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn kho nhập";
			return false;
		}
				
		if( this.sanpham_soluongNHAN == null || this.sanpham_soluongNHAN.size() <= 0 )
		{
			this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm nhận đổi quy cách";
			return false;
		}
		
		if(this.spList.size() <= 0)
		{
			this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm đổi quy cách";
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
						this.msg = "Sản phẩm ( " + yc.getTen() + " ) trong danh sách đổi quy cách đã trùng. Vui lòng kiểm tra lại.";
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
			
			String query = 	" update ERP_DOIQUYCACH set NgayDoi = '" + this.ngayyeucau + "', lydo = N'" + this.lydoyeucau + "', ghichu = N'" + this.ghichu + "', " + 
							" 	khoxuat_fk = '" + this.khoXuatId + "', khonhan_fk = '" + this.khoNhanId + "', ngaysua = '" + this.getDateTime() + "', nguoisua = '" + this.userId + "', loaidoituong = " + _loaidoituongId + ", DOITUONG_FK = " + _doituongId + " " +
				   			" where pk_seq = '" + this.id + "' ";
			
			System.out.println("::: UPDATE ERP_DOIQUYCACH: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_DOIQUYCACH " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_DOIQUYCACH_SANPHAM where doiquycach_fk = '" + this.id + "' ";
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_DOIQUYCACH " + query;
				db.getConnection().rollback();
				return false;
			}
			
			Utility util = new Utility();
			//Tăng kho ngược lại trước khi xóa
			query = "  select b.loaidoituong, b.DOITUONG_FK as doituongId, b.NGAYDOI, b.KhoXuat_FK, a.SanPham_fk, a.SoLo, a.NgayHetHan, a.ngaynhapkho, " + 
					" 		a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, isnull(a.bin_fk, 0) as bin_fk, a.phieudt, a.phieueo, SUM( a.SoLuong ) as soluong  " + 
					"  from ERP_DOIQUYCACH_SANPHAM_CHITIET a inner join ERP_DOIQUYCACH b on a.doiquycach_FK = b.PK_SEQ " + 
					"  where b.PK_SEQ = '" + this.id + "' " + 
					"  group by b.loaidoituong, b.DOITUONG_FK, b.NGAYDOI, b.KhoXuat_FK, a.SanPham_fk, a.SoLo, a.NgayHetHan, a.ngaynhapkho, a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, a.bin_fk, a.phieudt, a.phieueo ";
			
			System.out.println("::: CAP NHAT KHO: " + query);
			ResultSet rs = db.get(query);
			while( rs.next() )
			{
				String khoId = rs.getString("KhoXuat_FK");
				String spId = rs.getString("SanPham_fk");
				String solo = rs.getString("SoLo");
				String ngayhethan = rs.getString("NgayHetHan");
				String ngaynhapkho = rs.getString("ngaynhapkho");
				
				String loaidoituong = rs.getString("loaidoituong") == null ? "" : rs.getString("loaidoituong");
				String doituongId = rs.getString("doituongId") == null ? "" :  rs.getString("doituongId");
				
				String mame = rs.getString("mame");
				String mathung = rs.getString("mathung");
				String bin_fk = rs.getString("bin_fk");
				
				String maphieu = rs.getString("maphieu");
				String phieudt = rs.getString("phieudt");
				String phieueo = rs.getString("phieueo");
				
				String marq = rs.getString("marq");
				String hamluong = rs.getString("hamluong");
				String hamam = rs.getString("hamam");

				double soluong = rs.getDouble("soluong");
				
				msg = util.Update_KhoTT(rs.getString("NGAYDOI"), "Cập nhật DQC - Tăng kho ngược lại trước khi xóa đổi quy cách " + this.id, db, khoId, spId, solo, ngayhethan, ngaynhapkho, 
						mame, mathung, bin_fk, maphieu, phieudt, phieueo, marq, hamluong, hamam, loaidoituong, doituongId, 0, -1 * soluong, soluong);
				if( msg.trim().length() > 0 )
				{
					db.getConnection().rollback();
					return false;
				}
			}
			rs.close();
			
			query = "delete ERP_DOIQUYCACH_SANPHAM_CHITIET where doiquycach_fk = '" + this.id + "' ";
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_DOIQUYCACH " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_DOIQUYCACH_SANPHAM_CHITIET_NHANDOI where doiquycach_fk = '" + this.id + "' ";
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_DOIQUYCACH " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_DOIQUYCACH_CHIPHI where doiquycach_fk = '" + this.id + "' ";
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_DOIQUYCACH " + query;
				db.getConnection().rollback();
				return false;
			}
			
			String ycnlCurrent = this.id;
			if(this.spList.size() > 0)
			{
				for(int i = 0; i < this.spList.size(); i++)
				{
					IYeucau sp = this.spList.get(i);
					
					if(sp.getSoluongYC().trim().length() > 0)
					{
						query = " insert ERP_DOIQUYCACH_SANPHAM(doiquycach_fk, SANPHAM_FK,  soluong, dongia, stt  ) " +
								" values( '" + ycnlCurrent + "', '" + sp.getId() + "', " + sp.getSoluongYC() + ", '" + sp.getDongia().replaceAll(",", "") + "', '" + i + "' ) ";
						
						System.out.println("::: CHEN SAN PHAM: " + query);
						if(!db.update(query))
						{
							this.msg = "Không thể tạo mới ERP_DOIQUYCACH_SANPHAM: " + query;
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
								
								System.out.println("::: KEY UPDATE: " + key );
								if(key.startsWith( sp.getMa() + "__ "  ) )
								{
									String[] _sp = key.split("__");
									
									String _soluongCT = "0"; 
									if(this.sanpham_soluong.get(key) != null)
									{
										_soluongCT = this.sanpham_soluong.get(key).replaceAll(",", "");
									}
									
									System.out.println("::: KEY UPDATE: " + key + " -- SO LUONG CT: " + _soluongCT );
									String solo = _sp[2];
									String ngayhethan = _sp[3];
									String ngaynhapkho = _sp[4];
									String MAME = _sp[5];
									String MATHUNG = _sp[6];
									String bin_fk = _sp[7];
									String MAPHIEU = _sp[8];
									String phieudt = _sp[9];
									String phieueo = _sp[10];
									String MARQ = _sp[11];
									String HAMLUONG = _sp[12];
									String HAMAM = _sp[13];
									
									if(!_soluongCT.equals("0"))
									{
										totalCT += util.Round(Double.parseDouble(_soluongCT), 3);
										System.out.println("::: TOTAL BUOC NAY: " + totalCT + " -- SO LAM TRON: " + util.Round(Double.parseDouble(_soluongCT), 3));
										
										query = "insert ERP_DOIQUYCACH_SANPHAM_CHITIET( doiquycach_fk, SANPHAM_FK, scheme, solo, ngayhethan, ngaynhapkho, MAME, MATHUNG, bin_fk, MAPHIEU, phieudt, phieueo, MARQ, HAMLUONG, HAMAM,  soluong, stt ) " +
												"select '" + ycnlCurrent + "', pk_seq, ' ', N'" + solo + "', N'" + ngayhethan + "', '" + ngaynhapkho + "', '" + MAME + "', '" + MATHUNG + "', ( select PK_SEQ from ERP_BIN where KHOTT_FK = '" + this.khoXuatId + "' and MA = N'" + bin_fk + "' ), " + 
												" 	'" + MAPHIEU + "', '" + phieudt + "', '" + phieueo + "', '" + MARQ + "', '" + HAMLUONG + "', '" + HAMAM + "', '" + _soluongCT.replaceAll(",", "") + "', '" + i + "' " +
												"from ERP_SANPHAM where MA = '" + sp.getMa() + "' ";
										
										//System.out.println("1.2.Insert ERP_CHUYENKHO_SANPHAM_CHITIET: " + query);
										if(!db.update(query))
										{
											this.msg = "Khong the tao moi ERP_DOIQUYCACH_SANPHAM_CHITIET: " + query;
											db.getConnection().rollback();
											return false;
										}
										
										//CẬP NHẬT KHO
										this.msg = util.Update_KhoTT(this.ngayyeucau, "Đổi quy cách " + ycnlCurrent, db, this.khoXuatId, sp.getId(), solo, ngayhethan, ngaynhapkho, MAME, MATHUNG, " isnull( ( select PK_SEQ from ERP_BIN where KHOTT_FK = '" + this.khoXuatId + "' and MA = N'" + bin_fk + "' ) , 0) ",
														MAPHIEU, phieudt, phieueo, MARQ, HAMLUONG, HAMAM, this.loaidoituong, this.doituongId, 0, Double.parseDouble(_soluongCT.replaceAll(",", "")), -1 * Double.parseDouble(_soluongCT.replaceAll(",", "")));
										if( this.msg.trim().length() > 0 )
										{
											db.getConnection().rollback();
											return false;
										}
									}
								}
							}
							
							//NEU TONG SO LUONG CT MA KHONG BANG TONG LUONG XUAT THI KO CHO LUU
							//LAM TRONG TUNG SO ROI, MA XUONG DUOI JAVA NO VAN DE LE CA CHUC SO DANG SAU, NEN PHAI LAM TRONG LAN NUA
							if(util.Round(totalCT, 3) != Double.parseDouble(sp.getSoluongYC().replaceAll(",", "").trim()) )
							{
								this.msg = "Tổng xuất theo lô của sản phẩm ( " + sp.getMa() + " ) ( " + totalCT + " ) phải bằng tổng số lượng xuất ( " + sp.getSoluongYC() + " ) ";
								db.getConnection().rollback();
								return false;
							}
						}
					}
				}
			}
			
			//SAN PHẨM NHẬN
			if(this.spNhanList.size() > 0)
			{
				for(int i = 0; i < this.spNhanList.size(); i++)
				{
					IYeucau sp = this.spNhanList.get(i);
					
					if(sp.getSoluongYC().trim().length() > 0)
					{
						query = " insert ERP_DOIQUYCACH_SANPHAM( doiquycach_fk, SANPHAM_FK,  soluong, dongia, dongiasauPB, isNhandoi, stt  ) " +
								" values( '" + ycnlCurrent + "', '" + sp.getId() + "', " + sp.getSoluongYC() + ", '" + sp.getDongia().replaceAll(",", "") + "', '" + sp.getDongia2().replaceAll(",", "") + "', 1, '" + i + "' ) ";
						
						System.out.println("::: CHEN SAN PHAM: " + query);
						if(!db.update(query))
						{
							this.msg = "Không thể tạo mới ERP_DOIQUYCACH_SANPHAM: " + query;
							db.getConnection().rollback();
							return false;
						}
						
						//INSERT CHI TIET
						if(this.sanpham_soluongNHAN != null)
						{
							Enumeration<String> keys = this.sanpham_soluongNHAN.keys();
							double totalCT = 0;
							
							while(keys.hasMoreElements())
							{
								String key = keys.nextElement();
								
								System.out.println("::: KEY NHAN UPDATE: " + key );
								if(key.startsWith( i + "__"  ) )
								{
									String[] _sp = Utility.mySplit(key, "__");
									
									String _soluongCT = "0"; 
									if(this.sanpham_soluongNHAN.get(key) != null)
										_soluongCT = this.sanpham_soluongNHAN.get(key).replaceAll(",", "");
									
									System.out.println("::: KEY NHAN UPDATE: " + key + " -- SO LUONG CT: " + _soluongCT );
									String solo = _sp[2];
									String ngayhethan = _sp[3];
									String ngaynhapkho = this.ngayyeucau;
									String MAME = _sp[4];
									String MATHUNG = _sp[5];
									
									String bin_fk = _sp[6];
									if( bin_fk.contains(" [") )
										bin_fk = bin_fk.substring(bin_fk.indexOf(" [") + 2 , bin_fk.indexOf("]"));
									
									String MAPHIEU = _sp[7];
									String phieudt = _sp[8];
									String phieueo = _sp[9];
									String MARQ = _sp[10];
									String HAMLUONG = _sp[11];
									String HAMAM = _sp[12];
									
									if(!_soluongCT.equals("0"))
									{
										totalCT += util.Round(Double.parseDouble(_soluongCT), 3);
										System.out.println("::: TOTAL BUOC NAY: " + totalCT + " -- SO LAM TRON: " + util.Round(Double.parseDouble(_soluongCT), 3));
										
										query = "insert ERP_DOIQUYCACH_SANPHAM_CHITIET_NHANDOI( doiquycach_fk, SANPHAM_FK, solo, ngayhethan, ngaynhapkho, MAME, MATHUNG, bin_fk, MAPHIEU, phieudt, phieueo, MARQ, HAMLUONG, HAMAM,  soluong, stt ) " +
												"select '" + ycnlCurrent + "', pk_seq, N'" + solo + "', N'" + ngayhethan + "', '" + ngaynhapkho + "', '" + MAME + "', '" + MATHUNG + "', ( select PK_SEQ from ERP_BIN where KHOTT_FK = '" + this.khoXuatId + "' and MA = N'" + bin_fk + "' ), " + 
												" 	'" + MAPHIEU + "', '" + phieudt + "', '" + phieueo + "', '" + MARQ + "', '" + HAMLUONG + "', '" + HAMAM + "', '" + _soluongCT.replaceAll(",", "") + "', '" + i + "' " +
												"from ERP_SANPHAM where MA = '" + sp.getMa() + "' ";
										
										//System.out.println("1.2.Insert ERP_CHUYENKHO_SANPHAM_CHITIET: " + query);
										if(!db.update(query))
										{
											this.msg = "Khong the tao moi ERP_DOIQUYCACH_SANPHAMNHAN_CHITIET: " + query;
											db.getConnection().rollback();
											return false;
										}
									}
								}
							}
							
							//NEU TONG SO LUONG CT MA KHONG BANG TONG LUONG XUAT THI KO CHO LUU
							//LAM TRONG TUNG SO ROI, MA XUONG DUOI JAVA NO VAN DE LE CA CHUC SO DANG SAU, NEN PHAI LAM TRONG LAN NUA
							if(util.Round(totalCT, 3) != Double.parseDouble(sp.getSoluongYC().replaceAll(",", "").trim()) )
							{
								this.msg = "Tổng xuất theo lô của sản phẩm ( " + sp.getMa() + " ) ( " + totalCT + " ) phải bằng tổng số lượng xuất ( " + sp.getSoluongYC() + " ) ";
								db.getConnection().rollback();
								return false;
							}
						}
					}
				}
			}
			
			//CHi phi
			if( this.sotaikhoanCP != null )
			{
				for( int i = 0; i < this.sotaikhoanCP.length; i++ )
				{
					if( this.chiphi[i].trim().length() > 0 && this.sotaikhoanCP[i].trim().length() > 0 )
					{
						String sotaikhoan = this.sotaikhoanCP[i].split(" - ")[0];
						query = "insert ERP_DOIQUYCACH_CHIPHI( doiquycach_fk, taikhoan_fk, chiphi ) " + 
								"select '" + ycnlCurrent + "', pk_seq, '" + this.chiphi[i].replaceAll(",", "") + "' " + 
								"from ERP_TAIKHOANKT where sohieutaikhoan = '" + sotaikhoan + "' and npp_fk = '1' ";
						
						System.out.println("::: CHI PHI: " + query);
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi ERP_DOIQUYCACH_CHIPHI: " + query;
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

		/*if( this.khoXuatId.trim().length() > 0 )
		{
			query = "select PK_SEQ, MA + ', ' + TEN as TEN from ERP_BIN where KHOTT_FK = '" + this.khoXuatId + "' and trangthai = '1' ";
			
			System.out.println("::: LAY VI TRI NHAN: " + query);
			this.vitriChuyenRs = db.get(query + " order by TEN asc ");
			
			if( this.vtcId.trim().length() > 0 )
				query += " and pk_seq != '" + this.vtcId + "' ";
			
			System.out.println("::: LAY VI TRI CHUYEN: " + query);
			this.vitriNhanRs = db.getScrol(query + "order by TEN asc" );
		}*/
		
		query = "select PK_SEQ, TEN, LOAI from ERP_KHOTT where TrangThai = '1' ";
		query += " and pk_seq in " + util.quyen_khott(this.userId) ;
		query += " order by loai asc, TEN asc ";
			
		System.out.println("::: LAY KHO NHAN: " + query);
		this.khoNhanRs = db.get(query);
		
		if( ( this.khoXuatId.trim().length() > 0 || this.khoNhanId.trim().length() > 0 ) && this.spList.size() <= 0 && this.id.trim().length() > 0 )
		{
			this.createChuyenKho_SanPham();
		}

	}

	private void createChuyenKho_SanPham() 
	{
		String query =  "  select b.pk_seq as spId, isnull( b.MA, b.ma) as spMa, b.Ten as spTen,  	 " + 
						"  		isnull(DV.DIENGIAI, '') AS DVT, '' ghichu, soluong, dongia, dongiasauPB     " + 
						"  from ERP_DOIQUYCACH_SANPHAM a inner Join ERP_SanPham b on a.SANPHAM_FK = b.PK_SEQ   " + 
						"  	LEFT JOIN DONVIDOLUONG DV ON DV.PK_SEQ = b.DVDL_FK    " + 
						"  where a.DOIQUYCACH_FK = '" + this.id + "' and isNhandoi = '0' order by stt asc  ";

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
					sp.setghichu_CK(spRs.getString("ghichu"));
					sp.setTen(spTen);
					sp.setDonViTinh(donvi);
					sp.setSoluongYC(spRs.getString("soluong"));
					sp.setDongia(spRs.getString("dongia"));

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
		
		query =  "  select b.pk_seq as spId, isnull( b.MA, b.ma) as spMa, b.Ten as spTen,  	 " + 
						"  		isnull(DV.DIENGIAI, '') AS DVT, '' ghichu, soluong, dongia, dongiasauPB     " + 
						"  from ERP_DOIQUYCACH_SANPHAM a inner Join ERP_SanPham b on a.SANPHAM_FK = b.PK_SEQ   " + 
						"  	LEFT JOIN DONVIDOLUONG DV ON DV.PK_SEQ = b.DVDL_FK    " + 
						"  where a.DOIQUYCACH_FK = '" + this.id + "' and isNhandoi = '1' order by stt asc  ";
		
		System.out.println("__2.init SP NHAN: " + query );
		
		spRs = db.get(query);
		spList = new ArrayList<IYeucau>();
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
					sp.setghichu_CK(spRs.getString("ghichu"));
					sp.setTen(spTen);
					sp.setDonViTinh(donvi);
					sp.setSoluongYC(spRs.getString("soluong"));
					sp.setDongia(spRs.getString("dongia"));
					sp.setDongia2(spRs.getString("dongiasauPB"));
		
					spList.add(sp);
				}
				spRs.close();
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		
			this.spNhanList = spList;
		}
		
	}

	public void init() 
	{
		String query =  "select NGAYDOI, lydo, khoxuat_fk, khonhan_fk, trangthai,  " + 
						" 	loaidoituong, doituong_fk "+
						"from ERP_DOIQUYCACH where pk_seq = '" + this.id + "'";
		
		System.out.println(":: INIT: "+query);
		ResultSet rs = db.get(query);

		try 
		{
			if(rs.next())
			{
				this.ngayyeucau = rs.getString("NGAYDOI");
				this.lydoyeucau = rs.getString("lydo");
				this.khoXuatId = rs.getString("khoxuat_fk");
				this.khoNhanId = rs.getString("khonhan_fk");
				this.trangthai = rs.getString("trangthai");

				this.loaidoituong = rs.getString("loaidoituong") == null ? "" : rs.getString("loaidoituong");
				this.doituongId = rs.getString("doituong_fk") == null ? "" : rs.getString("doituong_fk");
			}
			rs.close();
			
			//INIT SO LUONG CHI TIET
			query = "select (select MA from ERP_SANPHAM where pk_seq = a.sanpham_fk ) as spMA,  solo, ngayhethan, ngaynhapkho, a.MAME, a.MATHUNG, a.MAPHIEU, marq, hamluong, hamam, soluong, ' ' as scheme, " +
					"	isnull( ( select ma from ERP_BIN where pk_seq = a.bin_fk ) , '' ) as vitriTEN, isnull(a.phieudt, '') as phieudt, isnull(a.phieueo, '') as phieueo	" +
					"from ERP_DOIQUYCACH_SANPHAM_CHITIET a where doiquycach_fk = '" + this.id + "'";
			
			System.out.println("---INIT SP CHI TIET: " + query);
			rs = db.get(query);
			if(rs != null)
			{
				Hashtable<String, String> sp_soluong = new Hashtable<String, String>();
				while(rs.next())
				{
					System.out.println("---KEY BEAN: " + ( rs.getString("spMA") + "__ " + "__" + rs.getString("solo") + "__" + rs.getString("ngayhethan") + "__" + rs.getString("ngaynhapkho") ) );
					String key = rs.getString("spMA") + "__ " + "__" + rs.getString("solo") + "__" + rs.getString("ngayhethan") + "__" + rs.getString("ngaynhapkho") 
									+ "__" + rs.getString("MAME") + "__" + rs.getString("MATHUNG") + "__" + rs.getString("vitriTEN")
									+ "__" + rs.getString("MAPHIEU") + "__" + rs.getString("phieudt") + "__" + rs.getString("phieueo")
									+ "__" + rs.getString("marq") + "__" + rs.getString("hamluong") + "__" + rs.getString("hamam");
					
					sp_soluong.put(key, rs.getString("soluong") );
				}
				rs.close();
				
				this.sanpham_soluong = sp_soluong;
				this.sanpham_soluongDAXUAT = sp_soluong;
			}
			
			//INIT NHAN DOI
			query = "select stt, (select MA from ERP_SANPHAM where pk_seq = a.sanpham_fk ) as spMA,  solo, ngayhethan, ngaynhapkho, a.MAME, a.MATHUNG, a.MAPHIEU, marq, hamluong, hamam, soluong, ' ' as scheme, " +
					"	isnull( ( select ma from ERP_BIN where pk_seq = a.bin_fk ) , '' ) as vitriTEN, isnull(a.phieudt, '') as phieudt, isnull(a.phieueo, '') as phieueo	" +
					"from ERP_DOIQUYCACH_SANPHAM_CHITIET_NHANDOI a where doiquycach_fk = '" + this.id + "'";
			
			System.out.println("---INIT SP NHAN DOI CHI TIET: " + query);
			rs = db.get(query);
			if(rs != null)
			{
				Hashtable<String, String> sp_soluong = new Hashtable<String, String>();
				while(rs.next())
				{
					System.out.println("---KEY BEAN: " + ( rs.getString("spMA") + "__ " + "__" + rs.getString("solo") + "__" + rs.getString("ngayhethan") + "__" + rs.getString("ngaynhapkho") ) );
					String key = rs.getString("stt") + "__" + rs.getString("spMA") + "__" + rs.getString("solo") + "__" + rs.getString("ngayhethan")  
									+ "__" + rs.getString("MAME") + "__" + rs.getString("MATHUNG") + "__" + rs.getString("vitriTEN")
									+ "__" + rs.getString("MAPHIEU") + "__" + rs.getString("phieudt") + "__" + rs.getString("phieueo")
									+ "__" + rs.getString("marq") + "__" + rs.getString("hamluong") + "__" + rs.getString("hamam");
					
					sp_soluong.put(key, rs.getString("soluong") );
				}
				rs.close();
				
				this.sanpham_soluongNHAN = sp_soluong;
			}
			
			//INIT CHI PHI
			query = "select b.sohieutaikhoan, a.chiphi " +
					"from ERP_DOIQUYCACH_CHIPHI a inner join ERP_TAIKHOANKT b on a.taikhoan_fk = b.pk_seq " + 
					"where doiquycach_fk = '" + this.id + "'";
			
			System.out.println("---INIT CHI PHI: " + query);
			rs = db.get(query);
			if(rs != null)
			{
				String _sotaikhoan = "";
				String _chiphi = "";
				
				while(rs.next())
				{
					_sotaikhoan += rs.getString("sohieutaikhoan") + "__";
					_chiphi += rs.getString("chiphi") + "__";
				}
				rs.close();
				
				if( _sotaikhoan.trim().length() > 0 )
				{
					_sotaikhoan = _sotaikhoan.substring(0, _sotaikhoan.length() - 2);
					this.sotaikhoanCP = _sotaikhoan.split("__");
					
					_chiphi = _chiphi.substring(0, _chiphi.length() - 2);
					this.chiphi = _chiphi.split("__");
				}
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
		/*tongluong = tongluong.replaceAll(",", "");
		
		String query = "";
		query =  " 	select ct.sanpham_fk, ct.AVAILABLE, NGAYHETHAN, ngaynhapkho, SOLO, MAME, MATHUNG, isnull(MAPHIEU, '') as MAPHIEU, isnull(MARQ, '') as MARQ, isnull(HAMLUONG, 100) as HAMLUONG, isnull(HAMAM, 0) as HAMAM, isnull( ( select ma from ERP_BIN where pk_seq = ct.bin_fk ), '' ) as vitri, isnull( maphieudinhtinh, '' ) as phieudt, isnull( phieueo, '' ) as phieueo   "+
				 "\n 	from ERP_KHOTT_SP_CHITIET ct inner join ERP_SANPHAM sp on ct.sanpham_fk = sp.pk_seq  "+
				 "\n 	where KHOTT_FK = '" + this.khoXuatId + "' and SANPHAM_FK = ( select pk_seq from ERP_SANPHAM where ma = '" + spMa + "' )   " +
				 		"  and ngaynhapkho <= '" + this.ngayyeucau + "'  ";
		
		query += "\n order by NGAYHETHAN asc  ";
		
		System.out.println("----LAY SO LO ( " + spMa + " ): " + query );
		return db.get(query);*/
		
		//Nếu vào cập nhật, thì chỉ lấy danh sách đã lưu trước đó, không đề xuất lại nữa
		if( this.id.trim().length() > 0 )
			tongluong = "0";
		
		tongluong = tongluong.replaceAll(",", "");
		//System.out.println("---TONG LUONG: " + tongluong );
		
		Utility util = new Utility();
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
					String solo = key.split("__")[1];
					String ngayhethan = key.split("__")[2];
					String ngaynhapkho = key.split("__")[3];
					
					String mame = key.split("__")[4];
					String mathung = key.split("__")[5];
					String vitri = key.split("__")[6];
					
					String maphieu = key.split("__")[7];
					String phieudt = key.split("__")[8];
					String phieueo = key.split("__")[9];
					
					String marq = key.split("__")[10];
					String hamluong = key.split("__")[11];
					String hamam = key.split("__")[12];
					
					String daxuat =  this.sanpham_soluongDAXUAT.get(key);
					
					if( daxuat.trim().length() > 0 )
					{
						soloDACHON += "select '" + solo + "' as soloCHON, '" + ngayhethan + "' as ngayhethanCHON, '" + ngaynhapkho + "' as ngaynhapkhoCHON, " + daxuat + " as soluongDACHON, '" + mame + "' as mame, '" + mathung + "' as mathung, '" + maphieu + "' as maphieu, " + 
									  " '" + marq + "' as marq, '" + hamluong + "' as hamluong, '" + hamam + "' as hamam, " + 
									  " isnull( ( select PK_SEQ from ERP_BIN where KHOTT_FK = '" + this.khoXuatId + "' and MA = N'" + vitri + "' ), 0 ) as vitri, '" + phieudt + "' as phieudt, '" + phieueo + "' as phieueo union ";
					}
				}
			}
		}
		
		if( soloDACHON.trim().length() > 0 )
		{
			soloDACHON = soloDACHON.substring(0, soloDACHON.length() - 7 );
			soloDACHON = " select soloCHON, ngayhethanCHON, ngaynhapkhoCHON, mame, mathung, maphieu, marq, hamluong, hamam, isnull(vitri, 0) as vitri, phieudt, phieueo, SUM( soluongDACHON ) as soluongDACHON " +
						 " from ( " + soloDACHON + " ) DT group by soloCHON, ngayhethanCHON, ngaynhapkhoCHON, mame, mathung, maphieu, marq, hamluong, hamam, vitri, phieudt, phieueo ";
		}
		else
			soloDACHON = " select '1' as soloCHON, '' as ngayhethanCHON, '' as ngaynhapkhoCHON, '' as mame, '' as mathung, '' as maphieu, '' as marq, '' as hamluong, '' as hamam, '0' vitri, '' phieudt, '' phieueo, 0 as soluongDACHON ";
		
		String sqlDONHANG = "";
		sqlDONHANG = " select SUM(soluong) from ERP_DOIQUYCACH_SANPHAM_CHITIET " + 
					 " where doiquycach_fk = '" + ( this.id.trim().length() > 0 ? this.id : "1" ) + "' and SANPHAM_FK = DT.sanpham_fk " + 
					 		" and solo = DT.solo and ngayhethan = DT.ngayhethan and rtrim(ltrim(ngaynhapkho)) =  rtrim(ltrim(dt.ngaynhapkho)) " + 
					 		" and isnull(mame, '') = isnull(DT.mame, '') and isnull(mathung, '') = isnull(DT.mathung, '') and isnull(maphieu, '') = isnull(DT.maphieu, '')  " +		
							" and isnull(marq, '') = isnull(DT.marq, '') and isnull(hamluong, 100) = isnull(DT.hamluong, 100) and isnull(hamam, 0) = isnull(DT.hamam, 0)  " + 
							" and isnull(bin_fk, 0) = isnull(DT.vitri, 0) and isnull(phieudt, '') = isnull(DT.phieudt, '') and isnull(phieueo, '') = isnull(DT.phieueo, '')  " ;
		
		String condition = "";
		if( this.loaidoituong.trim().length() > 0 )
			condition += " and isnull(ct.loaidoituong, -1) = '" + this.loaidoituong + "' ";
		if( this.doituongId.trim().length() > 0 )
			condition += " and isnull(ct.doituongId, 0) = '" + this.doituongId + "' ";
		
		String query = "";
		query =  "\n select DT.NGAYNHAPKHO, DT.NGAYHETHAN, DT.SOLO, isnull(DT.MAME, '') as MAME, isnull(DT.MATHUNG, '') as MATHUNG, isnull(DT.MAPHIEU, '') as MAPHIEU, " + 
				 "\n  		isnull(DT.MARQ, '') as MARQ, DT.hamluong, DT.hamam, isnull(bin.MA, '') as vitriTEN, DT.vitri, DT.phieudt, DT.phieueo, " +
				 " 		DT.AVAILABLE + isnull( ( " + sqlDONHANG + " ), 0) - isnull(DAXUAT.soluongDACHON, 0) as AVAILABLE  "+
				 "\n from "+
				 "\n ( "+
				 //"	select ct.sanpham_fk, dbo.LayQuyCach_DVBan( ct.SANPHAM_FK, null, " + donvi + " ) * ct.AVAILABLE  as AVAILABLE, NGAYHETHAN,  ngaynhapkho ,SOLO  "+
				 "\n 	select ct.sanpham_fk, ct.AVAILABLE, NGAYHETHAN, ngaynhapkho, SOLO, MAME, MATHUNG, MAPHIEU, MARQ, isnull(HAMLUONG, 100) as HAMLUONG, isnull(HAMAM, 0) as HAMAM, isnull( bin_fk, 0 ) as vitri, isnull( maphieudinhtinh, '' ) as phieudt, isnull( phieueo, '' ) as phieueo   "+
				 "\n 	from ERP_KHOTT_SP_CHITIET ct inner join ERP_SANPHAM sp on ct.sanpham_fk = sp.pk_seq  "+
				 "\n 	where KHOTT_FK = '" + this.khoXuatId + "' and SANPHAM_FK = ( select pk_seq from ERP_SANPHAM where ma = '" + spMa + "' )   " +
				 		"  and ngaynhapkho <= '" + this.ngayyeucau + "' "+ condition +
				 		"  and ( isnull(sp.batbuockiemdinh, 0) = 0 or KHOTT_FK in (100023,100058) or ( sp.batbuockiemdinh = 1 and isnull( maphieu, '' ) != ''  ) )		" +
				 "\n ) "+
				 "\n DT left join ERP_BIN bin on DT.vitri = bin.pk_seq left join  "+
				 "\n ( "+
				 	soloDACHON +
				 "\n ) "+
				 "\n DAXUAT on DT.SOLO = DAXUAT.soloCHON  and rtrim(ltrim( DT.NGAYHETHAN)) = rtrim(ltrim(DAXUAT.ngayhethanCHON)) and DT.NGAYNHAPKHO = DAXUAT.ngaynhapkhoCHON  "+
				 "\n and isnull(DT.MAME, '') = isnull(DAXUAT.MAME, '') and isnull(DT.MATHUNG, '') = isnull(DAXUAT.MATHUNG, '') and isnull(DT.MAPHIEU, '') = isnull(DAXUAT.MAPHIEU, '')  " +
				 "\n and isnull(DT.MARQ, '') = isnull(DAXUAT.MARQ, '') and isnull(DT.HAMLUONG, 100) = isnull(DAXUAT.HAMLUONG, 100) and isnull(DT.HAMAM, 0) = isnull(DAXUAT.HAMAM, 0)  " +
				 "\n and isnull(DT.vitri, '') = isnull(DAXUAT.vitri, '') and isnull(DT.phieudt, '') = isnull(DAXUAT.phieudt, '') and isnull(DT.phieueo, '') = isnull(DAXUAT.phieueo, '' )  " +
				 "\n where DT.AVAILABLE + isnull( ( " + sqlDONHANG + " ), 0) - isnull(DAXUAT.soluongDACHON, 0) > 0 "+
				 //"\n order by  MAPHIEU, SOLO, cast(DT.MAME as float), cast(DT.MATHUNG as float) asc      ";
				 "\n order by  DT.NGAYHETHAN      ";
		
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
					
				slg = util.Round(slg, 3);
				if(slg > 0)
				{
					query2 += "select '" + avai + "' as AVAILABLE, '" + rs.getString("NGAYHETHAN") + "' as NGAYHETHAN, '" + rs.getString("NGAYNHAPKHO") + "' as NGAYNHAPKHO, " + 
							" '" + rs.getString("MAME") + "' as MAME, '" + rs.getString("MATHUNG") + "' as MATHUNG, '" + rs.getString("MAPHIEU") + "' as MAPHIEU, " + 
							" '" + rs.getString("MARQ") + "' as MARQ, '" + rs.getString("HAMLUONG") + "' as HAMLUONG, '" + rs.getString("HAMAM") + "' as HAMAM, " + 
							" '" + rs.getString("vitriTEN") + "' as vitri, '" + rs.getString("PHIEUDT") + "' as PHIEUDT, '" + rs.getString("PHIEUEO") + "' as PHIEUEO, " + 
							" '" + rs.getString("SOLO") + "' as SOLO, '" + slg + "' as tuDEXUAT union ALL ";
					
				}
				else
				{
					query2 += "select '" + avai + "' as AVAILABLE, '" + rs.getString("NGAYHETHAN") + "' as NGAYHETHAN, '" + rs.getString("NGAYNHAPKHO") + "' as NGAYNHAPKHO, " + 
							" '" + rs.getString("MAME") + "' as MAME, '" + rs.getString("MATHUNG") + "' as MATHUNG, '" + rs.getString("MAPHIEU") + "' as MAPHIEU, "+ 
							" '" + rs.getString("MARQ") + "' as MARQ, '" + rs.getString("HAMLUONG") + "' as HAMLUONG, '" + rs.getString("HAMAM") + "' as HAMAM, " + 
							" '" + rs.getString("vitriTEN") + "' as vitri, '" + rs.getString("PHIEUDT") + "' as PHIEUDT, '" + rs.getString("PHIEUEO") + "' as PHIEUEO, " + 
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
			
			//SORT LAI THEO YEU CAU Lô - PKN - Mẻ - Thùng
			query2 = " select * from ( " + query2 + " ) DT order by SOLO, MAPHIEU, dbo.ftConvertToNumber(MAME), dbo.ftConvertToNumber(MATHUNG) ";
			
			System.out.println("---TU DONG DE XUAT BIN - LO: " + query2 );
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
	
	public static void main(String[] arg)
	{
		dbutils db = new dbutils();
		
		//đề xuất số lượng cần theo ngày nhập kho tăng dần, để bù vào lượng đã tăng ảo lên 0
		String query =  " select b.khott_fk, a.sanpham_fk, cast( sum( a.soluongDIEUCHINH ) as numeric(18, 3) ) as  soluongDIEUCHINH,  "+
						"	( select pk_seq from ERP_DIEUCHINHTONKHOTT where lydodieuchinh like N'%Điều chỉnh cuối kỳ 10/2016 - giam%' and khott_fk = b.khott_fk  ) as dctkId	" +
						" from ERP_DIEUCHINHTONKHOTT_SANPHAM_CHITIET a inner join ERP_DIEUCHINHTONKHOTT b on a.dctk_fk = b.pk_seq "+
						" where dctk_fk in ( select pk_seq from ERP_DIEUCHINHTONKHOTT where lydodieuchinh like N'%Điều chỉnh cuối kỳ 10/2016%' ) "+
						" group by b.khott_fk, a.sanpham_fk "+
						" order by b.khott_fk asc ";
		
		System.out.println("::: LAY SAN PHAM: " + query);
		ResultSet rsYC = db.get(query);
		if( rsYC != null )
		{
			try 
			{
				Utility util = new Utility();
				while( rsYC.next() )
				{
					String dctkId = rsYC.getString("dctkId");
					String khoId = rsYC.getString("khott_fk");
					String spId = rsYC.getString("sanpham_fk");
					double tongluong = rsYC.getDouble("soluongDIEUCHINH");
					
					query = " select cast ( ( dauky + nhap - xuat ) as numeric(18, 3) ) as AVAILABLE, NGAYHETHAN, ngaynhapkho, SOLO, MAME, MATHUNG, MAPHIEU, MARQ, HAMLUONG, HAMAM, isnull( c.pk_seq, 0 ) as vitri, phieudt, phieueo   "+
							" from TEMP_XL_NXT_10_30 ct left join ERP_BIN c on ct.vitri = c.ma and ct.kho_fk = c.khott_fk  "+
							" where KHO_FK = '" + khoId + "' and SANPHAM_FK = '" + spId + "'   " +
							 	" and cast ( ( dauky + nhap - xuat ) as numeric(18, 3) ) > 0 " + 
							" order by ngaynhapkho asc, ngayhethan asc  ";
					
					System.out.println("----DE XUAT TU DONG KHO ( " + khoId + " ) - SP ( " + spId + " ): " + query );
					ResultSet rs = db.get(query);

					double total = 0;
					double totalCT = 0;
					boolean exit = false;
					while(rs.next())
					{
						double slg = 0;
						double avai = rs.getDouble("AVAILABLE");
						
						total += avai;
						if(total < tongluong)
						{
							slg = avai;
						}
						else
						{
							slg =  tongluong - ( total - avai );
							exit = true;
						}
							
						//CHÈN VÀO BẢNG CHI TIẾT
						String solo = rs.getString("SOLO");
						String ngayhethan = rs.getString("NGAYHETHAN");
						String ngaynhapkho = rs.getString("ngaynhapkho");
						String MAME = rs.getString("MAME");
						String MATHUNG = rs.getString("MATHUNG");
						String bin_fk = rs.getString("vitri");
						String MAPHIEU = rs.getString("MAPHIEU");
						String phieudt = rs.getString("phieudt");
						String phieueo = rs.getString("phieueo");
						String MARQ = rs.getString("MARQ") == null ? "" : rs.getString("MARQ");
						String HAMLUONG = rs.getString("HAMLUONG");
						String HAMAM = rs.getString("HAMAM");
						
						//ROUND 3 SỐ
						slg = util.Round(slg, 3);
						if( slg > 0 )
						{
							totalCT += slg;
							//System.out.println("::: TONG LUONG: " + totalCT);
							
							//DIEU CHINH GIAM NEN SLG AM
							double slgDC = -1 * slg;
							query = "insert ERP_DIEUCHINHTONKHOTT_SANPHAM_CHITIET( dctk_fk, SANPHAM_FK, scheme, solo, ngayhethan, ngaynhapkho, MAME, MATHUNG, bin_fk, MAPHIEU, phieudt, phieueo, MARQ, HAMLUONG, HAMAM,  soluong, soluongDIEUCHINH ) " +
									"select '" + dctkId + "', pk_seq, ' ', N'" + solo + "', N'" + ngayhethan + "', '" + ngaynhapkho + "', '" + MAME + "', '" + MATHUNG + "', '" + bin_fk + "', " + 
									" 	'" + MAPHIEU + "', '" + phieudt + "', '" + phieueo + "', " + MARQ + ", '" + HAMLUONG + "', '" + HAMAM + "', '" + slgDC + "', '" + slgDC + "' " +
									"from ERP_SANPHAM where pk_seq = '" + spId + "' ";
							
							System.out.println("1.2.Insert ERP_CHUYENKHO_SANPHAM_CHITIET: " + query);
							if(!db.update(query))
							{
								/*msg = "Khong the tao moi ERP_CHUYENKHO_SANPHAM_CHITIET: " + query;
								db.getConnection().rollback();
								db.shutDown();
								return msg;*/
							}
						}
						
						if( exit )
							break;
					}
					rs.close();
					
					if( util.Round(totalCT, 3 ) != util.Round( tongluong, 3 ) )
					{
						//Lưu lại phiếu lệch, để qua tháng 11 tìm cách xử lý
						String sql = "Insert TEMP_XL_NXT_10_30_LECH( dctk_fk, sanpham_fk, soluongCAN, soluongDEXUAT ) " + 
									 "values( '" + dctkId + "', '" + spId + "', '" + tongluong + "', '" +  totalCT + "' )  ";
						System.out.println("::: BI THIEU: " + sql);
						db.update(query);
						
						/*msg = "Tổng xuất theo lô của sản phẩm ( " + spId + " ) ( " + util.Round(totalCT, 3 ) + " ) không bằng tổng số lượng xuất ( " + util.Round( Double.parseDouble(tongluong), 3 ) + " ). Vui lòng liên hệ Admin để xử lý ";
						db.getConnection().rollback();
						db.shutDown();
						return msg;*/
					}
					
				}
				rsYC.close();
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		
		System.out.println(":::: CHAY XONG.............. ");
		
	}

	
	public List<IYeucau> getSpNhanList() {
		
		return this.spNhanList;
	}

	
	public void setSpNhanList(List<IYeucau> spNhanList) {
		
		this.spNhanList = spNhanList;
	}

	
	public Hashtable<String, String> getSanpham_SoluongNHAN() {
		
		return this.sanpham_soluongNHAN;
	}

	
	public void setSanpham_SoluongNHAN(Hashtable<String, String> sp_soluong) {
		
		this.sanpham_soluongNHAN = sp_soluong;
	}

	
	public ResultSet getSoloTheoSpNHAN(String spIds, String donvi, String tongluong) 
	{
		
		return null;
	}

	
	public String[] getSotaikhoanChiphi() {
		
		return this.sotaikhoanCP;
	}

	
	public void setSotaikhoanChiphi(String[] sotaikhoan) {
		
		this.sotaikhoanCP = sotaikhoan;
	}

	
	public String[] getChiphi() {
		
		return this.chiphi;
	}

	
	public void setChiphi(String[] chiphi) {
		
		this.chiphi = chiphi;
	}

	 
}
