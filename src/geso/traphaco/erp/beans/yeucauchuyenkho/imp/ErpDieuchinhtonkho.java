package geso.traphaco.erp.beans.yeucauchuyenkho.imp;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.yeucauchuyenkho.IErpDieuchinhtonkho;
import geso.traphaco.erp.beans.yeucauchuyenkho.IYeucau;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
 
public class ErpDieuchinhtonkho implements IErpDieuchinhtonkho
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
	
	String vtcId;
	ResultSet vitriChuyenRs;
	ResultSet vitriNhanRs;
	
	String coChiphi;
	String chiphiId;
	ResultSet chiphiRs;
	
	public ErpDieuchinhtonkho()
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
		
		this.coChiphi = "";
		this.chiphiId = "";
		this.vtcId = "";
		
		this.db = new dbutils();
	}
	
	public ErpDieuchinhtonkho(String id)
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
			this.msg = "Vui lòng nhập ngày điều chỉnh";
			return false;
		}
		
		if(this.khoXuatId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn kho điều chỉnh";
			return false;
		}
		
		if( this.codoituong.equals("1") && this.doituongId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn đối tượng điều chỉnh";
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
						this.msg = "Sản phẩm ( " + yc.getTen() + " ) đã trùng. Vui lòng kiểm tra lại.";
						return false;
					}
					
				}
			}
		}
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String _vitriId = this.vtcId.trim().length() <= 0 ? "null" : this.vtcId.trim();
			String _loaidoituongId = this.loaidoituong.trim().length() <= 0 ? "null" : this.loaidoituong.trim();
			String _doituongId = this.doituongId.trim().length() <= 0 ? "null" : this.doituongId.trim();
			
			String query = 	" insert ERP_DIEUCHINHTONKHOTT(ngaydieuchinh, lydodieuchinh, trangthai, khott_fk, bin_fk, ngaytao, nguoitao, ngaysua, nguoisua, loaidoituong, DOITUONG_FK ) " +
				   			" values('" + this.ngayyeucau + "',   N'" + this.lydoyeucau + "', '0', '" + this.khoXuatId + "', " + _vitriId + ",  '" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "', " + _loaidoituongId + ", " + _doituongId + " ) ";
			
			System.out.println("::: CHEN DCTK: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_DIEUCHINHTONKHOTT " + query;
				db.getConnection().rollback();
				return false;
			}
			
			String ycnlCurrent = "";
			query = "select IDENT_CURRENT('ERP_DIEUCHINHTONKHOTT') as ckId";
			
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
					
					if(sp.getSoluongYC().trim().length() >= 0)
					{
						query = " insert ERP_DIEUCHINHTONKHOTT_SANPHAM(dctk_fk, SANPHAM_FK, ghichu  ) " +
								" values( '" + ycnlCurrent + "', '" + sp.getId() + "', N'" + sp.getghichu_CK() + "' ) ";
						
						System.out.println("::: CHEN SAN PHAM: " + query);
						if(!db.update(query))
						{
							this.msg = "Không thể tạo mới ERP_DIEUCHINHTONKHOTT_SANPHAM: " + query;
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
									
									String _soluongCT = ""; 
									if(this.sanpham_soluong.get(key) != null)
									{
										_soluongCT = this.sanpham_soluong.get(key).replaceAll(",", "");
									}
									
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
									String NSX = _sp[14];
									if(_soluongCT.trim().length() > 0)
									{
										totalCT += Double.parseDouble(_soluongCT);
										
										query = "insert ERP_DIEUCHINHTONKHOTT_SANPHAM_CHITIET( dctk_fk, SANPHAM_FK, scheme, solo, ngayhethan, ngaynhapkho, MAME, MATHUNG, bin_fk, MAPHIEU, phieudt, phieueo, MARQ, HAMLUONG, HAMAM,  soluong,NSX_FK ) " +
												"select '" + ycnlCurrent + "', pk_seq, ' ', N'" + solo + "', N'" + ngayhethan + "', '" + ngaynhapkho + "', '" + MAME + "', '" + MATHUNG + "', ISNULL( ( select pk_seq from ERP_BIN where ma = N'" + bin_fk + "' and khott_fk = '" + this.khoXuatId + "' ), 0 ), " + 
												" 	'" + MAPHIEU + "', '" + phieudt + "', '" + phieueo + "', '" + MARQ + "', '" + HAMLUONG + "', '" + HAMAM + "', '" + _soluongCT.replaceAll(",", "") + "' " +
												"\n ,"+(NSX.trim().length()==0?"NULL":NSX)+" from ERP_SANPHAM where MA = '" + sp.getMa() + "' ";
										
										System.out.println("1.2.Insert ERP_DIEUCHINHTONKHOTT_SANPHAM_CHITIET: " + query);
										if(!db.update(query))
										{
											this.msg = "Khong the tao moi ERP_CHUYENVITRI_SANPHAM_CHITIET: " + query;
											db.getConnection().rollback();
											return false;
										}
										
										//NẾU ĐIỀU CHỈNH ÂM THÌ PHẢI BOOKED KHO ==> KHÔNG CẦN BOOKED NỮA, LÚC CHỐT SẼ KIỂM TRA
										/*if( Double.parseDouble(_soluongCT ) < 0 )
										{
											double soluongBOOKED = -1 * Double.parseDouble(_soluongCT );
											this.msg = util.Update_KhoTT(this.ngayyeucau, "Điều chỉnh tồn kho", db, this.khoXuatId, sp.getId(), solo, ngayhethan, ngaynhapkho, MAME, MATHUNG, this.vtcId,
															MAPHIEU, phieudt, phieueo, MARQ, HAMLUONG, HAMAM, this.loaidoituong, this.doituongId, 0, soluongBOOKED, -1 * soluongBOOKED );
											if( this.msg.trim().length() > 0 )
											{
												db.getConnection().rollback();
												return false;
											}
										}*/
									}
								}
							}
							
							//NEU TONG SO LUONG CT MA KHONG BANG TONG LUONG XUAT THI KO CHO LUU
							/*if(totalCT != Double.parseDouble(sp.getSoluongYC().replaceAll(",", "").trim()) )
							{
								this.msg = "Tổng xuất theo lô của sản phẩm ( " + sp.getMa() + " ) ( " + totalCT + " ) phải bằng tổng số lượng xuất ( " + sp.getSoluongYC() + " ) ";
								db.getConnection().rollback();
								return false;
							}*/
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
			this.msg = "Vui lòng nhập ngày điều chỉnh";
			return false;
		}
		
		if(this.khoXuatId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn kho điều chỉnh";
			return false;
		}
		
		if( this.codoituong.equals("1") && this.doituongId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn đối tượng điều chỉnh";
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
						this.msg = "Sản phẩm ( " + yc.getTen() + " ) đã trùng. Vui lòng kiểm tra lại.";
						return false;
					}
					
				}
			}
		}

		try
		{
			db.getConnection().setAutoCommit(false);

			String _vitriId = this.vtcId.trim().length() <= 0 ? "null" : this.vtcId.trim();
			String _loaidoituongId = this.loaidoituong.trim().length() <= 0 ? "null" : this.loaidoituong.trim();
			String _doituongId = this.doituongId.trim().length() <= 0 ? "null" : this.doituongId.trim();
			
			String query =  " update  ERP_DIEUCHINHTONKHOTT set NGAYDIEUCHINH = '" + this.ngayyeucau + "', lydodieuchinh = N'" + this.lydoyeucau + "', " +
							"  khott_fk = " + this.khoXuatId + ", bin_fk = " + _vitriId + ", ngaysua ='" + this.getDateTime() + "', nguoisua="+this.userId+"," +
							"  loaidoituong = " + _loaidoituongId + ", DOITUONG_FK = " + _doituongId +
							" where pk_seq="+this.id;
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_DIEUCHINHTONKHOTT " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = " DELETE ERP_DIEUCHINHTONKHOTT_SANPHAM WHERE DCTK_FK = "+this.id;
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_DIEUCHINHTONKHOTT_SANPHAM " + query;
				db.getConnection().rollback();
				return false;
			}
			
			/*Utility util = new Utility();
			//Tăng kho ngược lại trước khi xóa
			query = "  select b.loaidoituong, b.DOITUONG_FK as doituongId, b.NGAYDIEUCHINH, b.Khott_FK, a.SanPham_fk, a.SoLo, a.NgayHetHan, a.ngaynhapkho, " + 
					" 		a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, isnull(a.bin_fk, 0) as bin_fk, a.phieudt, a.phieueo, SUM( a.SoLuong ) as soluong  " + 
					"  from ERP_DIEUCHINHTONKHOTT_SANPHAM_CHITIET a inner join ERP_DIEUCHINHTONKHOTT b on a.dctk_FK = b.PK_SEQ " + 
					"  where b.PK_SEQ = '" + this.id + "' and a.soluong " + 
					"  group by b.loaidoituong, b.DOITUONG_FK, b.NGAYDIEUCHINH, b.Khott_FK, a.bin_fk, a.SanPham_fk, a.SoLo, a.NgayHetHan, a.ngaynhapkho, a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, a.phieudt, a.phieueo ";
			
			System.out.println("::: CAP NHAT KHO: " + query);
			ResultSet rs = db.get(query);
			if( rs != null )
			{
				while( rs.next() )
				{
					String khoId = rs.getString("Khott_FK");
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
					
					msg = util.Update_KhoTT(rs.getString("NGAYDIEUCHINH"), "Cập nhật dctk - Tăng kho ngược lại trước khi xóa ", db, khoId, spId, solo, ngayhethan, ngaynhapkho, 
							mame, mathung, bin_fk, maphieu, phieudt, phieueo, marq, hamluong, hamam, loaidoituong, doituongId, 0, -1 * soluong, soluong);
					if( msg.trim().length() > 0 )
					{
						db.getConnection().rollback();
						return false;
					}
				}
				rs.close();
			}*/
			
			query = "delete ERP_DIEUCHINHTONKHOTT_SANPHAM_CHITIET where DCTK_FK = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_DIEUCHINHTONKHOTT_SANPHAM_CHITIET " + query;
				db.getConnection().rollback();
				return false;
			}
			
			if(this.spList.size() > 0)
			{
				for(int i = 0; i < this.spList.size(); i++)
				{
					IYeucau sp = this.spList.get(i);
					
					if(sp.getSoluongYC().trim().length() >= 0)
					{
						query = " insert ERP_DIEUCHINHTONKHOTT_SANPHAM(dctk_fk, SANPHAM_FK, ghichu  ) " +
								" values( '" + this.id + "', '" + sp.getId() + "', N'" + sp.getghichu_CK() + "' ) ";
						
						System.out.println("::: CHEN SAN PHAM: " + query);
						if(!db.update(query))
						{
							this.msg = "Không thể tạo mới ERP_DIEUCHINHTONKHOTT_SANPHAM: " + query;
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
									
									String _soluongCT = ""; 
									if(this.sanpham_soluong.get(key) != null)
									{
										_soluongCT = this.sanpham_soluong.get(key).replaceAll(",", "");
									}
									
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
									String NSX = _sp[14];
									if(_soluongCT.trim().length() > 0)
									{
										totalCT += Double.parseDouble(_soluongCT);
										
										query = "insert ERP_DIEUCHINHTONKHOTT_SANPHAM_CHITIET( dctk_fk, SANPHAM_FK, scheme, solo, ngayhethan, ngaynhapkho, MAME, MATHUNG, bin_fk, MAPHIEU, phieudt, phieueo, MARQ, HAMLUONG, HAMAM,  soluong,NSX_FK ) " +
												"select '" + this.id + "', pk_seq, ' ', N'" + solo + "', N'" + ngayhethan + "', '" + ngaynhapkho + "', '" + MAME + "', '" + MATHUNG + "', ISNULL( ( select pk_seq from ERP_BIN where ma = N'" + bin_fk + "' and khott_fk = '" + this.khoXuatId + "' ), 0 ), " + 
												" 	'" + MAPHIEU + "', '" + phieudt + "', '" + phieueo + "', '" + MARQ + "', '" + HAMLUONG + "', '" + HAMAM + "', '" + _soluongCT.replaceAll(",", "") + "' " +
												"\n ,"+(NSX.trim().length()==0?"NULL":NSX)+"from ERP_SANPHAM where MA = '" + sp.getMa() + "' ";
										
										System.out.println("1.2.Insert ERP_DIEUCHINHTONKHOTT_SANPHAM_CHITIET: " + query);
										if(!db.update(query))
										{
											this.msg = "Khong the tao moi ERP_CHUYENVITRI_SANPHAM_CHITIET: " + query;
											db.getConnection().rollback();
											return false;
										}
										
										//NẾU ĐIỀU CHỈNH ÂM THÌ PHẢI BOOKED KHO ==> KHÔNG CẦN BOOKED NỮA, LÚC CHỐT SẼ KIỂM TRA
										/*if( Double.parseDouble(_soluongCT ) < 0 )
										{
											double soluongBOOKED = -1 * Double.parseDouble(_soluongCT );
											this.msg = util.Update_KhoTT(this.ngayyeucau, "Điều chỉnh tồn kho", db, this.khoXuatId, sp.getId(), solo, ngayhethan, ngaynhapkho, MAME, MATHUNG, this.vtcId,
															MAPHIEU, phieudt, phieueo, MARQ, HAMLUONG, HAMAM, this.loaidoituong, this.doituongId, 0, soluongBOOKED, -1 * soluongBOOKED );
											if( this.msg.trim().length() > 0 )
											{
												db.getConnection().rollback();
												return false;
											}
										}*/
									}
								}
							}
							
							//NEU TONG SO LUONG CT MA KHONG BANG TONG LUONG XUAT THI KO CHO LUU
							/*if(totalCT != Double.parseDouble(sp.getSoluongYC().replaceAll(",", "").trim()) )
							{
								this.msg = "Tổng xuất theo lô của sản phẩm ( " + sp.getMa() + " ) ( " + totalCT + " ) phải bằng tổng số lượng xuất ( " + sp.getSoluongYC() + " ) ";
								db.getConnection().rollback();
								return false;
							}*/
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
			
			System.out.println("::: LAY VI TRI NHAN: " + query);
			this.vitriChuyenRs = db.get(query + " order by TEN asc ");
			
			if( this.vtcId.trim().length() > 0 )
				query += " and pk_seq != '" + this.vtcId + "' ";
			
			System.out.println("::: LAY VI TRI CHUYEN: " + query);
			this.vitriNhanRs = db.getScrol(query + "order by TEN asc" );
		}
		
		if( ( this.khoXuatId.trim().length() > 0 || this.khoNhanId.trim().length() > 0 ) && this.spList.size() <= 0 && this.id.trim().length() > 0 )
		{
			this.createChuyenKho_SanPham();
		}

	}

	private void createChuyenKho_SanPham() 
	{
		String query =  "  select b.pk_seq as spId, isnull( b.MA, b.ma) as spMa, b.Ten as spTen,  	 " + 
						"  		isnull(DV.DIENGIAI, '') AS DVT, isnull(ghichu, '') as ghichu, 0 as SOLUONGYEUCAU,  0 as soluongton     " + 
						"  from ERP_DIEUCHINHTONKHOTT_SANPHAM a inner Join ERP_SanPham b on a.SANPHAM_FK = b.PK_SEQ   " + 
						"  	LEFT JOIN DONVIDOLUONG DV ON DV.PK_SEQ = b.DVDL_FK    " + 
						"  where a.DCTK_FK = '" + this.id + "'  ";

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
		String query =  "select NGAYDIEUCHINH, lydodieuchinh as lydo, khott_fk, bin_fk, trangthai,  " + 
						" 	loaidoituong, doituong_fk "+
						"from ERP_DIEUCHINHTONKHOTT where pk_seq = '" + this.id + "'";
		
		System.out.println("INIT CHUYENVITRI: "+query);
		ResultSet rs = db.get(query);

		try 
		{
			if(rs.next())
			{
				this.ngayyeucau = rs.getString("NGAYDIEUCHINH");
				this.lydoyeucau = rs.getString("lydo");
				this.khoXuatId = rs.getString("khott_fk");
				this.vtcId = rs.getString("bin_fk") == null ? "" : rs.getString("bin_fk");
				this.trangthai = rs.getString("trangthai");

				this.loaidoituong = rs.getString("loaidoituong") == null ? "" : rs.getString("loaidoituong");
				this.doituongId = rs.getString("doituong_fk") == null ? "" : rs.getString("doituong_fk");
			}
			rs.close();
			
			//INIT SO LUONG CHI TIET
			query = "select (select MA from ERP_SANPHAM where pk_seq = a.sanpham_fk ) as spMA,  solo, ngayhethan, ngaynhapkho, a.MAME, a.MATHUNG, a.MAPHIEU, marq, hamluong, hamam, soluong, ' ' as scheme, " +
					"	isnull( ( select ma from ERP_BIN where pk_seq = a.bin_fk ) , '' ) as vitriTEN, isnull(a.phieudt, '') as phieudt, isnull(a.phieueo, '') as phieueo,a.NSX_FK,isnull(nsx.ma+' - '+nsx.ten,'') AS NSXTEN	" +
					"from ERP_DIEUCHINHTONKHOTT_SANPHAM_CHITIET a LEFT JOIN ERP_NHASANXUAT NSX ON NSX.PK_sEQ = a.NSX_FK where dctk_fk = '" + this.id + "'";
			
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
									+ "__" + rs.getString("marq") + "__" + rs.getString("hamluong") + "__" + rs.getString("hamam")+ "__" +( rs.getString("NSX_FK")==null?"":rs.getString("NSX_FK") ) + "__" + rs.getString("NSXTEN");
					
					sp_soluong.put(key, rs.getString("soluong") );
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

	/*public ResultSet getSoloTheoSp(String spMa, String donvi, String tongluong)
	{
		tongluong = tongluong.replaceAll(",", "");
		//System.out.println("---TONG LUONG: " + tongluong );
		
		String query = "";
		
		if( this.trangthai.equals("0") || this.id.trim().length() <= 0 )
		{
			query =  " 	select ct.sanpham_fk, ct.AVAILABLE, NGAYHETHAN, ngaynhapkho, SOLO, MAME, MATHUNG, isnull(MAPHIEU, '') as MAPHIEU, isnull(MARQ, '') as MARQ, isnull(HAMLUONG, 100) as HAMLUONG, isnull(HAMAM, 0) as HAMAM, isnull( ( select ma from ERP_BIN where pk_seq = ct.bin_fk ), '' ) as vitri, isnull( maphieudinhtinh, '' ) as phieudt, isnull( phieueo, '' ) as phieueo   "+
					 "\n 	from ERP_KHOTT_SP_CHITIET ct inner join ERP_SANPHAM sp on ct.sanpham_fk = sp.pk_seq  "+
					 "\n 	where KHOTT_FK = '" + this.khoXuatId + "' and SANPHAM_FK = ( select pk_seq from ERP_SANPHAM where ma = '" + spMa + "' )   " +
					 		"  and ngaynhapkho <= '" + this.ngayyeucau + "'  ";
			
			query =  " 	select ct.sanpham_fk, (dauky + nhap - xuat) as AVAILABLE, NGAYHETHAN, ngaynhapkho, SOLO, MAME, MATHUNG, MAPHIEU, MARQ, HAMLUONG, HAMAM, vitri, phieudt, phieueo   "+
					 "\n 	from UFN_NXT_HO_FULL( '2017-01-01', '" + this.ngayyeucau + "' )  ct inner join ERP_SANPHAM sp on ct.sanpham_fk = sp.pk_seq  "+
					 "\n 	where KHO_FK = '" + this.khoXuatId + "' and SANPHAM_FK = ( select pk_seq from ERP_SANPHAM where ma = '" + spMa + "' )   " +
					 		"  and ngaynhapkho <= '" + this.ngayyeucau + "'  ";
			
			if( this.vtcId.trim().length() > 0 )
				query += " and ct.bin_fk = '" + this.vtcId + "' "; 
			
			query += "\n order by NGAYHETHAN asc  ";
		}
		else
		{
			query = "select sanpham_fk, isnull(avaiHT, 0) as AVAILABLE, NGAYHETHAN, ngaynhapkho, SOLO, MAME, MATHUNG, MAPHIEU, MARQ, HAMLUONG, HAMAM, isnull( b.ma, '' ) vitri, phieudt, phieueo " +
					"from ERP_DIEUCHINHTONKHOTT_SANPHAM_CHITIET a left join ERP_BIN b on a.bin_fk = b.pk_seq " +
					"where dctk_fk = '" + this.id + "'  ";
			
			query += "\n order by a.NGAYHETHAN asc  ";
		}
		
		System.out.println("----LAY SO LO ( " + spMa + " ): " + query );
		return db.get(query);
		
	}*/
	public ResultSet getSoloTheoSp(String spMa, String donvi, String tongluong)
	{
		tongluong = tongluong.replaceAll(",", "");
		//System.out.println("---TONG LUONG: " + tongluong );
		
		String query = "";
		
		if( this.trangthai.equals("0") || this.id.trim().length() <= 0 )
		{
			//Phải sửa lại điều chỉnh theo số tồn của NXT
			/*query =  " 	select ct.sanpham_fk, ct.AVAILABLE, NGAYHETHAN, ngaynhapkho, SOLO, MAME, MATHUNG, isnull(MAPHIEU, '') as MAPHIEU, isnull(MARQ, '') as MARQ, isnull(HAMLUONG, 100) as HAMLUONG, isnull(HAMAM, 0) as HAMAM, isnull( ( select ma from ERP_BIN where pk_seq = ct.bin_fk ), '' ) as vitri, isnull( maphieudinhtinh, '' ) as phieudt, isnull( phieueo, '' ) as phieueo   "+
					 "\n 	from ERP_KHOTT_SP_CHITIET ct inner join ERP_SANPHAM sp on ct.sanpham_fk = sp.pk_seq  "+
					 "\n 	where KHOTT_FK = '" + this.khoXuatId + "' and SANPHAM_FK = ( select pk_seq from ERP_SANPHAM where ma = '" + spMa + "' )   " +
					 		"  and ngaynhapkho <= '" + this.ngayyeucau + "'  ";*/

			/*query = " select a.sanpham_fk, NGAYHETHAN, ngaynhapkho, SOLO, MAME, MATHUNG, MAPHIEU, MARQ, HAMLUONG, HAMAM, vitri, phieudt, phieueo, \n "+
					" 		(dauky + nhap - xuat) as SOLUONG, \n" + 
					" 		isnull( ( select BOOKED from dbo.ufn_Booked_ChiTiet()  \n"+
					" 		  where KHO_FK = a.KHO_FK and SANPHAM_FK = a.SANPHAM_FK and solo = a.solo and ngayhethan = a.ngayhethan  \n"+
					" 				and ngaynhapkho = a.ngaynhapkho \n"+
					" 				and MAME = a.MAME and MATHUNG = a.MATHUNG  \n"+
					" 				and isnull(vitri, '') = isnull(bin.MA, '') \n"+
					" 				and MAPHIEU = a.MAPHIEU and phieueo = a.phieueo and MARQ = a.MARQ and phieudt = a.phieudt \n"+
					" 				and isnull(hamam, '0') = isnull(a.hamam, '0') and isnull(hamluong, '100') = isnull(a.hamluong, '100')  \n"+
					" 		), 0) as BOOKED  \n"+
					" from UFN_NXT_HO_FULL( '2017-01-01', '" + this.ngayyeucau + "' )  a inner join ERP_SANPHAM sp on a.sanpham_fk = sp.pk_seq   \n"+
					" 			left join ERP_BIN bin on a.vitri = bin.MA and a.kho_fk = bin.KHOTT_FK \n"+
					" where KHO_FK = '" + this.khoXuatId + "' and SANPHAM_FK = ( select pk_seq from ERP_SANPHAM where ma = '" + spMa + "' )     and ngaynhapkho <= '" + this.ngayyeucau + "'   \n";*/
	
			query = " select a.kho_fk, a.sanpham_fk, a.NGAYHETHAN, a.ngaynhapkho, a.SOLO, a.MAME, a.MATHUNG, a.MAPHIEU, a.MARQ, a.HAMLUONG, a.HAMAM, a.vitri, a.phieudt, a.phieueo,  "+
					"   		(dauky + nhap - xuat) as SOLUONG, kho.soluong as soluongHT, kho.booked, kho.available,a.nsx_fk,isnull(nsx.ma+' - '+nsx.ten,'') as nsx "+
					" from UFN_NXT_HO_FULL( '2017-07-01', '" + this.ngayyeucau + "' )  a inner join ERP_SANPHAM sp on a.sanpham_fk = sp.pk_seq    "+
					"  		left join ERP_BIN bin on a.vitri = bin.MA and a.kho_fk = bin.KHOTT_FK  \n"
					+ "		left join erp_nhasanxuat nsx on nsx.pk_Seq = a.nsx_fk \n"+
					" 		left join ERP_KHOTT_SP_CHITIET kho on a.kho_fk = kho.khott_fk and kho.sanpham_fk = sp.pk_seq "+
					" 			and kho.solo = a.solo and kho.ngayhethan = a.ngayhethan   "+
					"  			and kho.ngaynhapkho = a.ngaynhapkho  "+
					"  			and kho.MAME = a.MAME and kho.MATHUNG = a.MATHUNG   "+
					"  			and isnull(kho.bin_fk, 0) = isnull(bin.pk_seq, 0)  "+
					"  			and kho.MAPHIEU = a.MAPHIEU and kho.phieueo = a.phieueo and kho.MARQ = a.MARQ and kho.maphieudinhtinh = a.phieudt  "+
					"  			and isnull(kho.hamam, '0') = isnull(a.hamam, '0') and isnull(kho.hamluong, '100') = isnull(a.hamluong, '100') and isnull(a.nsx_fk,0) = isnull(kho.nsx_fk,0)  "+
					" where a.KHO_FK = '" + this.khoXuatId + "' and a.SANPHAM_FK = ( select pk_seq from ERP_SANPHAM where ma = '" + spMa + "' )     and a.ngaynhapkho <= '" + this.ngayyeucau + "'    ";
			
			query += "\n order by NGAYHETHAN asc  ";
		}
		else
		{
			query = "select sanpham_fk, isnull(soluongHT, 0) as SOLUONG, isnull(soluongHT, 0) as soluongHT, isnull(soluongHT, 0) as AVAILABLE, 0 as booked, NGAYHETHAN, ngaynhapkho, SOLO, MAME, MATHUNG, MAPHIEU, MARQ, HAMLUONG, HAMAM, isnull( b.ma, '' ) vitri, phieudt, phieueo,a.nsx_fk,isnull(nsx.ma+' - '+nsx.ten,'') as nsx  " +
					" \n from ERP_DIEUCHINHTONKHOTT_SANPHAM_CHITIET a left join ERP_BIN b on a.bin_fk = b.pk_seq  left join erp_nhasanxuat nsx on nsx.pk_Seq = a.nsx_fk " +
					"where dctk_fk = '" + this.id + "'  ";
			
			query += "\n order by a.NGAYHETHAN asc  ";
		}
		
		System.out.println("----LAY SO LO ( " + spMa + " ): " + query );
		return db.get(query);
		
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

	 
}
