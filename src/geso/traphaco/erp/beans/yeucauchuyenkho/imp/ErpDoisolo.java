package geso.traphaco.erp.beans.yeucauchuyenkho.imp;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.yeucauchuyenkho.IErpDoisolo;
import geso.traphaco.erp.beans.yeucauchuyenkho.IYeucau;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
 
public class ErpDoisolo implements IErpDoisolo
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
	Hashtable<String, String> sanpham_thongtindoi;
	Hashtable<String, String> sanpham_ngayhethanDoi;
	
	String vtcId;
	ResultSet vitriChuyenRs;
	ResultSet vitriNhanRs;
	
	String coChiphi;
	String chiphiId;
	ResultSet chiphiRs;
	
	String loaidieuchinh;
	
	public ErpDoisolo()
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
		this.sanpham_thongtindoi = new Hashtable<String, String>();
		this.sanpham_ngayhethanDoi = new Hashtable<String, String>();
		
		this.coChiphi = "";
		this.chiphiId = "";
		this.vtcId = "";
		this.loaidieuchinh = "0";
		
		this.db = new dbutils();
	}
	
	public ErpDoisolo(String id)
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
		this.sanpham_thongtindoi = new Hashtable<String, String>();
		this.sanpham_ngayhethanDoi = new Hashtable<String, String>();

		this.coChiphi = "";
		this.chiphiId = "";
		this.vtcId = "";
		this.loaidieuchinh = "0";
		
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
			
			String query = 	" insert ERP_DOISOLO(ngaydieuchinh, lydodieuchinh, loaidieuchinh, trangthai, khott_fk, bin_fk, ngaytao, nguoitao, ngaysua, nguoisua, loaidoituong, DOITUONG_FK ) " +
				   			" values('" + this.ngayyeucau + "', N'" + this.lydoyeucau + "', '" + this.loaidieuchinh + "', '0', '" + this.khoXuatId + "', " + _vitriId + ",  '" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "', " + _loaidoituongId + ", " + _doituongId + " ) ";
			
			System.out.println("::: CHEN DCTK: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_DOISOLO " + query;
				db.getConnection().rollback();
				return false;
			}
			
			String ycnlCurrent = "";
			query = "select IDENT_CURRENT('ERP_DOISOLO') as ckId";
			
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
						query = " insert ERP_DOISOLO_SANPHAM(doisolo_fk, SANPHAM_FK, ghichu  ) " +
								" values( '" + ycnlCurrent + "', '" + sp.getId() + "', N'" + sp.getghichu_CK() + "' ) ";
						
						System.out.println("::: CHEN SAN PHAM: " + query);
						if(!db.update(query))
						{
							this.msg = "Không thể tạo mới ERP_DOISOLO_SANPHAM: " + query;
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
									String nsx_fk = _sp[11];
									String MARQ = _sp[12];
									String HAMLUONG = _sp[13];
									String HAMAM = _sp[14];
									
									if(_soluongCT.trim().length() > 0)
									{
										totalCT += Double.parseDouble(_soluongCT);
										
										String ttDOI = this.sanpham_thongtindoi.get(key);
										String soloNew = "";
										String ngayhethanNew = "";
										String marquetteNew = "";
										String maphieuNew = "";
										String thungNew = "";
										String meNew = "";
										String phieudtNew = "";
										String hamamNew = "";
										String hamluongNew = "";
										String nsxNew = null;
										
										if( this.loaidieuchinh.equals("0") )
										{
											soloNew = ttDOI;
											ngayhethanNew = this.sanpham_ngayhethanDoi.get(key);
											if( ngayhethanNew == null || ngayhethanNew.trim().length() <= 0 )
											{
												this.msg = "Mã sản phẩm : " + sp.getMa() + " chưa nhập thông tin ngày hết hạn.";
												db.getConnection().rollback();
												return false;
											}
										}
										else if( this.loaidieuchinh.equals("1") )
											marquetteNew = ttDOI;
										else if( this.loaidieuchinh.equals("2") )
											maphieuNew = ttDOI;
										else if( this.loaidieuchinh.equals("3") )
											thungNew = ttDOI;
										else if( this.loaidieuchinh.equals("4") )
											meNew = ttDOI;
										else if( this.loaidieuchinh.equals("5") )
											phieudtNew = ttDOI;
										else if( this.loaidieuchinh.equals("6") )
										{
											hamamNew = ttDOI;
											hamluongNew = this.sanpham_ngayhethanDoi.get(key);
											if( hamluongNew == null || hamluongNew.trim().length() <= 0 || hamamNew.trim().length() <= 0 )
											{
												this.msg = "Mã sản phẩm : " + sp.getMa() + " chưa nhập thông tin hàm ẩm / hàm lượng.";
												db.getConnection().rollback();
												return false;
											}
										}else if( this.loaidieuchinh.equals("7") ){
											String[] temp=ttDOI.split(" - ");
											nsxNew = temp[1];
											System.out.println("NSXNEW: " + nsxNew);
										}
										
										//Check Unicode
										if( Utility.checkUnicode( new String[]{ soloNew, ngayhethanNew, marquetteNew, maphieuNew, thungNew, meNew, phieudtNew, hamamNew, hamluongNew } ) )
										{
											this.msg = "Thông tin bạn nhập đang có dấu. Vui lòng kiểm tra lại";
											db.getConnection().rollback();
											return false;
										}
										
										query = "insert ERP_DOISOLO_SANPHAM_CHITIET( doisolo_fk, SANPHAM_FK, scheme, solo, ngayhethan, ngaynhapkho, MAME, MATHUNG, bin_fk, MAPHIEU, phieudt, phieueo, MARQ, HAMLUONG, HAMAM,  soluong, soloMoi, ngayhethanMoi, marquetteMoi, maphieuMoi, thungMoi, meMoi, phieudtMoi, hamamMoi, hamluongMoi, nsx_fk, nsxNEW_fk  ) " +
												"select '" + ycnlCurrent + "', pk_seq, ' ', N'" + solo + "', N'" + ngayhethan + "', '" + ngaynhapkho + "', '" + MAME + "', '" + MATHUNG + "', ISNULL( ( select pk_seq from ERP_BIN where ma = N'" + bin_fk + "' and khott_fk = '" + this.khoXuatId + "' ), 0 ), " + 
												" 	'" + MAPHIEU + "', '" + phieudt + "', '" + phieueo + "', '" + MARQ + "', '" + HAMLUONG + "', '" + HAMAM + "', '" + _soluongCT.replaceAll(",", "") + "', N'" + soloNew + "', N'" + ngayhethanNew + "', N'" + marquetteNew + "', N'" + maphieuNew + "', N'" + thungNew + "', N'" + meNew + "', N'" + phieudtNew + "', '" + hamamNew + "', '" + hamluongNew + "' ,"+nsx_fk+", "+nsxNew+"  " +
												"from ERP_SANPHAM where MA = '" + sp.getMa() + "' ";
										
										System.out.println("1.2.Insert ERP_DOISOLO_SANPHAM_CHITIET: " + query);
										if(!db.update(query))
										{
											this.msg = "Khong the tao moi ERP_DOISOLO_SANPHAM_CHITIET: " + query;
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
			
			String query =  " update  ERP_DOISOLO set NGAYDIEUCHINH = '" + this.ngayyeucau + "', lydodieuchinh = N'" + this.lydoyeucau + "', loaidieuchinh = '" + this.loaidieuchinh + "', " +
							"  khott_fk = " + this.khoXuatId + ", bin_fk = " + _vitriId + ", ngaysua ='" + this.getDateTime() + "', nguoisua="+this.userId+"," +
							"  loaidoituong = " + _loaidoituongId + ", DOITUONG_FK = " + _doituongId +
							" where pk_seq="+this.id;
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_DOISOLO " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = " DELETE ERP_DOISOLO_SANPHAM WHERE DOISOLO_FK = "+this.id;
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_DOISOLO_SANPHAM " + query;
				db.getConnection().rollback();
				return false;
			}
			
			/*Utility util = new Utility();
			//Tăng kho ngược lại trước khi xóa
			query = "  select b.loaidoituong, b.DOITUONG_FK as doituongId, b.NGAYDIEUCHINH, b.Khott_FK, a.SanPham_fk, a.SoLo, a.NgayHetHan, a.ngaynhapkho, " + 
					" 		a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, isnull(a.bin_fk, 0) as bin_fk, a.phieudt, a.phieueo, SUM( a.SoLuong ) as soluong  " + 
					"  from ERP_DOISOLO_SANPHAM_CHITIET a inner join ERP_DOISOLO b on a.dctk_FK = b.PK_SEQ " + 
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
			
			query = "delete ERP_DOISOLO_SANPHAM_CHITIET where DOISOLO_FK = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_DOISOLO_SANPHAM_CHITIET " + query;
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
						query = " insert ERP_DOISOLO_SANPHAM(doisolo_fk, SANPHAM_FK, ghichu  ) " +
								" values( '" + this.id + "', '" + sp.getId() + "', N'" + sp.getghichu_CK() + "' ) ";
						
						System.out.println("::: CHEN SAN PHAM: " + query);
						if(!db.update(query))
						{
							this.msg = "Không thể tạo mới ERP_DOISOLO_SANPHAM: " + query;
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
									String nsx_fk = _sp[11];
									String MARQ = _sp[12];
									String HAMLUONG = _sp[13];
									String HAMAM = _sp[14];
									
									if(_soluongCT.trim().length() > 0)
									{
										totalCT += Double.parseDouble(_soluongCT);
										
										String ttDOI = this.sanpham_thongtindoi.get(key);
										String soloNew = "";
										String ngayhethanNew = "";
										String marquetteNew = "";
										String maphieuNew = "";
										String thungNew = "";
										String meNew = "";
										String phieudtNew = "";
										String hamamNew = "";
										String hamluongNew = "";
										String nsxNew = null;
										
										if( this.loaidieuchinh.equals("0") )
										{
											soloNew = ttDOI;
											ngayhethanNew = this.sanpham_ngayhethanDoi.get(key);
											if( ngayhethanNew == null || ngayhethanNew.trim().length() <= 0 )
											{
												this.msg = "Mã sản phẩm : " + sp.getMa() + " chưa nhập thông tin ngày hết hạn.";
												db.getConnection().rollback();
												return false;
											}
										}
										else if( this.loaidieuchinh.equals("1") )
											marquetteNew = ttDOI;
										else if( this.loaidieuchinh.equals("2") )
											maphieuNew = ttDOI;
										else if( this.loaidieuchinh.equals("3") )
											thungNew = ttDOI;
										else if( this.loaidieuchinh.equals("4") )
											meNew = ttDOI;
										else if( this.loaidieuchinh.equals("5") )
											phieudtNew = ttDOI;
										else if( this.loaidieuchinh.equals("6") )
										{
											hamamNew = ttDOI;
											hamluongNew = this.sanpham_ngayhethanDoi.get(key);
											if( hamluongNew == null || hamluongNew.trim().length() <= 0 || hamamNew.trim().length() <= 0 )
											{
												this.msg = "Mã sản phẩm : " + sp.getMa() + " chưa nhập thông tin hàm ẩm / hàm lượng.";
												db.getConnection().rollback();
												return false;
											}
										}else if( this.loaidieuchinh.equals("7") ){
											String[] temp=ttDOI.split(" - ");
											nsxNew = temp[1];
											System.out.println("NSXNEW: " + nsxNew);
										}
										
										//Check Unicode
										if( Utility.checkUnicode( new String[]{ soloNew, ngayhethanNew, marquetteNew, maphieuNew, thungNew, meNew, phieudtNew, hamamNew, hamluongNew } ) )
										{
											this.msg = "Thông tin bạn nhập đang có dấu. Vui lòng kiểm tra lại";
											db.getConnection().rollback();
											return false;
										}
										
										query = "insert ERP_DOISOLO_SANPHAM_CHITIET( doisolo_fk, SANPHAM_FK, scheme, solo, ngayhethan, ngaynhapkho, MAME, MATHUNG, bin_fk, MAPHIEU, phieudt, phieueo, MARQ, HAMLUONG, HAMAM,  soluong, soloMoi, ngayhethanMoi, marquetteMoi, maphieuMoi, thungMoi, meMoi, phieudtMoi, hamamMoi, hamluongMoi,nsx_fk, nsxNEW_fk  ) " +
												"select '" + this.id + "', pk_seq, ' ', N'" + solo + "', N'" + ngayhethan + "', '" + ngaynhapkho + "', '" + MAME + "', '" + MATHUNG + "', ISNULL( ( select pk_seq from ERP_BIN where ma = N'" + bin_fk + "' and khott_fk = '" + this.khoXuatId + "' ), 0 ), " + 
												" 	'" + MAPHIEU + "', '" + phieudt + "', '" + phieueo + "', '" + MARQ + "', '" + HAMLUONG + "', '" + HAMAM + "', '" + _soluongCT.replaceAll(",", "") + "', N'" + soloNew + "', N'" + ngayhethanNew + "', N'" + marquetteNew + "', N'" + maphieuNew + "', N'" + thungNew + "', N'" + meNew + "', N'" + phieudtNew + "', '" + hamamNew + "', '" + hamluongNew + "' ,"+nsx_fk+", "+nsxNew+" " +
												"from ERP_SANPHAM where MA = '" + sp.getMa() + "' ";
										
										System.out.println("1.2.Insert ERP_DOISOLO_SANPHAM_CHITIET: " + query);
										if(!db.update(query))
										{
											this.msg = "Khong the tao moi ERP_DOISOLO_SANPHAM_CHITIET: " + query;
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
						"  from ERP_DOISOLO_SANPHAM a inner Join ERP_SanPham b on a.SANPHAM_FK = b.PK_SEQ   " + 
						"  	LEFT JOIN DONVIDOLUONG DV ON DV.PK_SEQ = b.DVDL_FK    " + 
						"  where a.DOISOLO_FK = '" + this.id + "'  ";

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
		String query =  "select NGAYDIEUCHINH, lydodieuchinh as lydo, loaidieuchinh, khott_fk, bin_fk, trangthai,  " + 
						" 	loaidoituong, doituong_fk "+
						"from ERP_DOISOLO where pk_seq = '" + this.id + "'";
		
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
				this.loaidieuchinh = rs.getString("loaidieuchinh");

				this.loaidoituong = rs.getString("loaidoituong") == null ? "" : rs.getString("loaidoituong");
				this.doituongId = rs.getString("doituong_fk") == null ? "" : rs.getString("doituong_fk");
			}
			rs.close();
			
			//INIT SO LUONG CHI TIET
			query = "select isnull((select ten from erp_nhasanxuat nsxs where a.nsxNEW_fk=nsxs.pk_seq),'') nsxNEWTen, a.nsxNEW_FK, a.nsx_fk, (select MA from ERP_SANPHAM where pk_seq = a.sanpham_fk ) as spMA,  solo, ngayhethan, ngaynhapkho, a.MAME, a.MATHUNG, a.MAPHIEU, marq, hamluong, hamam, soluong, ' ' as scheme, " +
					"	isnull( ( select ma from ERP_BIN where pk_seq = a.bin_fk ) , '' ) as vitriTEN, isnull(a.phieudt, '') as phieudt, isnull(a.phieueo, '') as phieueo,	" +
					"	soloMoi, isnull(ngayhethanMoi, '') as ngayhethanMoi, marquetteMoi, maphieuMoi, thungMoi, meMoi, phieudtMoi, hamamMoi, hamluongMoi		" +
					"from ERP_DOISOLO_SANPHAM_CHITIET a where doisolo_fk = '" + this.id + "'";
			
			System.out.println("---INIT SP CHI TIET: " + query);
			rs = db.get(query);
			if(rs != null)
			{
				Hashtable<String, String> sp_soluong = new Hashtable<String, String>();
				Hashtable<String, String> sp_thongtindoi = new Hashtable<String, String>();
				Hashtable<String, String> sp_ngayhethandoi = new Hashtable<String, String>();
				while(rs.next())
				{
					System.out.println("---KEY BEAN: " + ( rs.getString("spMA") + "__ " + "__" + rs.getString("solo") + "__" + rs.getString("ngayhethan") + "__" + rs.getString("ngaynhapkho") ) );
					String key = rs.getString("spMA") + "__ " + "__" + rs.getString("solo") + "__" + rs.getString("ngayhethan") + "__" + rs.getString("ngaynhapkho") 
									+ "__" + rs.getString("MAME") + "__" + rs.getString("MATHUNG") + "__" + rs.getString("vitriTEN")
									+ "__" + rs.getString("MAPHIEU") + "__" + rs.getString("phieudt") + "__" + rs.getString("phieueo")
									+ "__" + rs.getString("nsx_fk")
									+ "__" + rs.getString("marq") + "__" + rs.getString("hamluong") + "__" + rs.getString("hamam");
					
					sp_soluong.put(key, rs.getString("soluong") );
					
					String ttDOI = "";
					if( this.loaidieuchinh.equals("0") )
					{
						ttDOI = rs.getString("soloMoi");
						sp_ngayhethandoi.put(key, rs.getString("ngayhethanMoi"));
					}
					else if( this.loaidieuchinh.equals("1") )
						ttDOI = rs.getString("marquetteMoi");
					else if( this.loaidieuchinh.equals("2") )
						ttDOI = rs.getString("maphieuMoi");
					else if( this.loaidieuchinh.equals("3") )
						ttDOI = rs.getString("thungMoi");
					else if( this.loaidieuchinh.equals("4") )
						ttDOI = rs.getString("meMoi");
					else if( this.loaidieuchinh.equals("5") )
						ttDOI = rs.getString("phieudtMoi");
					else if( this.loaidieuchinh.equals("6") )
					{
						ttDOI = rs.getString("hamamMoi");
						sp_ngayhethandoi.put(key, rs.getString("hamluongMoi"));
					}else if( this.loaidieuchinh.equals("7") ){
						ttDOI = rs.getString("nsxNEWTen") + " - " + rs.getString("nsxNEW_FK");
					}
					
					sp_thongtindoi.put(key, ttDOI);
					
				}
				rs.close();
				
				this.sanpham_soluong = sp_soluong;
				this.sanpham_soluongDAXUAT = sp_soluong;
				this.sanpham_thongtindoi = sp_thongtindoi;
				this.sanpham_ngayhethanDoi = sp_ngayhethandoi;
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
		tongluong = tongluong.replaceAll(",", "");
		//System.out.println("---TONG LUONG: " + tongluong );
		
		String query = "";
		query =  " 		select isnull((select TEN from ERP_NHASANXUAT ak where ct.nsx_fk=ak.pk_seq),'') nsxten,ct.nsx_fk, ct.sanpham_fk, ct.AVAILABLE, NGAYHETHAN, ngaynhapkho, SOLO, MAME, MATHUNG, isnull(MAPHIEU, '') as MAPHIEU, isnull(MARQ, '') as MARQ, isnull(HAMLUONG, 100) as HAMLUONG, isnull(HAMAM, 0) as HAMAM, isnull( ( select ma from ERP_BIN where pk_seq = ct.bin_fk ), '' ) as vitri, isnull( maphieudinhtinh, '' ) as phieudt, isnull( phieueo, '' ) as phieueo   "+
				 "\n 	from ERP_KHOTT_SP_CHITIET ct inner join ERP_SANPHAM sp on ct.sanpham_fk = sp.pk_seq  "+
				 "\n 	where KHOTT_FK = '" + this.khoXuatId + "' and SANPHAM_FK = ( select pk_seq from ERP_SANPHAM where ma = '" + spMa + "' )   " +
				 " 		and ngaynhapkho <= '" + this.ngayyeucau + "' and ct.AVAILABLE > 0   ";
		
		if( this.vtcId.trim().length() > 0 )
			query += " and ct.bin_fk = '" + this.vtcId + "' ";
		
		query += "\n order by ct.solo ,ct.maphieu , dbo.ftConvertToNumber(ct.MAME) ,  " +
						 "  dbo.ftConvertToNumber(ct.MATHUNG)  ";
		
		System.out.println("----LAY SO LO ( " + spMa + " ): " + query );
		return db.get(query);
		
	}
	
	public ResultSet getSoloDisplay(String spMa, String donvi, String tongluong)
	{
		tongluong = tongluong.replaceAll(",", "");
		//System.out.println("---TONG LUONG: " + tongluong );
		
		String query = "";
		query = "\n select case dsl.loaidieuchinh when 7 then (select ten from erp_nhasanxuat nsxs where nsxs.pk_seq=ct.nsxNEW_fk) else '' end as nsxNEWTen, isnull((select ten from ERP_NHASANXUAT ak where ct.nsx_fk=ak.pk_seq),'' ) nsxTEN ,ct.nsX_fk, ct.sanpham_fk,"
				+ " case dsl.loaidieuchinh when 7 then (select ten from erp_nhasanxuat nsxs where nsxs.pk_seq=ct.nsxNEW_fk) when 0 then ct.soloMoi when 1 then ct.marquetteMoi when 2 then ct.maphieuMoi when 3 then ct.thungMoi when 4 then ct.meMoi when 5 then ct.phieudtMoi else ct.hamamMoi end as thongtinDoi, " + 
				"\n 	case dsl.loaidieuchinh when 0 then ct.ngayhethanMoi else '' end as ngayhethanMOI, " + 
				"\n  	case dsl.loaidieuchinh when 6 then ct.hamluongMoi else '' end as hamluongMoi, " +      
				"\n 	CT.soluong, 0 AVAILABLE, NGAYHETHAN, ngaynhapkho, SOLO, MAME, MATHUNG, isnull(MAPHIEU, '') as MAPHIEU, isnull(MARQ, '') as MARQ, isnull(HAMLUONG, 100) as HAMLUONG, isnull(HAMAM, 0) as HAMAM, isnull( ( select ma from ERP_BIN where pk_seq = ct.bin_fk ), '' ) as vitri, phieudt, isnull( phieueo, '' ) as phieueo    " +      
				"\n from ERP_DOISOLO_SANPHAM_CHITIET ct inner join ERP_SANPHAM sp on ct.sanpham_fk = sp.pk_seq " +      
				"\n 			inner join ERP_DOISOLO dsl on ct.doisolo_fk = dsl.pk_seq  " +      
				"\n where ct.doisolo_fk = '" + this.id + "' and ct.soluong != 0 and sp.MA = '" + spMa + "' ";
		
		query += "\n order by ct.solo ,ct.maphieu , dbo.ftConvertToNumber(ct.MAME) ,  " +
				 "  dbo.ftConvertToNumber(ct.MATHUNG)  ";
		
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

	public String getLoaidieuchinh() {

		return this.loaidieuchinh;
	}

	public void setLoaidieuchinh(String loaidieuchinh) {
		
		this.loaidieuchinh = loaidieuchinh;
	}

	
	public Hashtable<String, String> getSanpham_Thongtindoi() {

		return this.sanpham_thongtindoi;
	}


	public void setSanpham_Thongtindoi(Hashtable<String, String> sp_thongtindoi) {
		
		this.sanpham_thongtindoi = sp_thongtindoi;
	}

	
	public Hashtable<String, String> getSanpham_Ngayhethandoi() {

		return this.sanpham_ngayhethanDoi;
	}


	public void setSanpham_Ngayhethandoi(Hashtable<String, String> sp_ngayhethandoi) {
		
		this.sanpham_ngayhethanDoi = sp_ngayhethandoi;
	}

	 
}
