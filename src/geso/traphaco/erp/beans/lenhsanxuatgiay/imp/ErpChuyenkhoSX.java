package geso.traphaco.erp.beans.lenhsanxuatgiay.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.erp.beans.lenhsanxuatgiay.*;
import geso.traphaco.erp.beans.nhapkho.IKhu_Vitri;
import geso.traphaco.erp.beans.nhapkho.imp.Khu_Vitri;
import geso.traphaco.erp.beans.phieuxuatkho.ISpDetail;
import geso.traphaco.erp.beans.phieuxuatkho.imp.SpDetail;

public class ErpChuyenkhoSX implements IErpChuyenkhoSX
{
	private static final long serialVersionUID = 1L;
	String userId;
	String id;
	String ngayyeucau;
	String lydoyeucau;

	String msg;
	String isnhanHang;
	String trangthai;
	
	String khoXuatId;
	ResultSet khoXuatRs;
	
	String khoNhanId;
	ResultSet khoNhanRs;
	
	String lsxIds;
	ResultSet lsxRs;
	
	List<IYeucau> spList;
	List<IYeucau> spChoNhapList;
	
	List<IKhu_Vitri> khuList;
	List<IKhu_Vitri> vitriList;
	
	dbutils db;
	
	public ErpChuyenkhoSX()
	{
		this.id = "";
		this.ngayyeucau = getDateTime();
		this.lydoyeucau = "";
		this.khoXuatId = "";
		this.khoNhanId = "";
		this.lsxIds = "";
		this.msg = "";
		this.isnhanHang = "0";
		this.trangthai = "0";

		this.spList = new ArrayList<IYeucau>();
		this.spChoNhapList = new ArrayList<IYeucau>();
		
		this.khuList = new ArrayList<IKhu_Vitri>();
		this.vitriList = new ArrayList<IKhu_Vitri>();
		
		this.db = new dbutils();
	}
	
	public ErpChuyenkhoSX(String id)
	{
		this.id = id;
		this.ngayyeucau = getDateTime();
		this.lydoyeucau = "";
		this.khoXuatId = "";
		this.khoNhanId = "";
		this.lsxIds = "";
		this.msg = "";
		this.isnhanHang = "0";
		this.trangthai = "0";

		this.spList = new ArrayList<IYeucau>();
		this.spChoNhapList = new ArrayList<IYeucau>();
		
		this.khuList = new ArrayList<IKhu_Vitri>();
		this.vitriList = new ArrayList<IKhu_Vitri>();
		
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
			this.msg = "Vui lòng nhập ngày yêu cầu";
			return false;
		}
		
		if(this.spList.size() <= 0)
		{
			this.msg = "Không có sản phẩm nào được chọn";
			return false;
		}
		else
		{
			boolean flag = false;
			for(int i = 0; i < this.spList.size(); i++)
			{
				IYeucau yc = this.spList.get(i);
				if(yc.getSoluongYC().trim().length() > 0)
				{
					flag = true;
				
					String tonkho = yc.getTonhientai().trim().length() > 0 ? yc.getTonhientai().trim() : "0" ;
					String soluong = yc.getSoluongYC().trim().length() > 0 ? yc.getSoluongYC().trim() : "0" ;
					
					if( Float.parseFloat(soluong) >  Float.parseFloat(tonkho) )
					{
						this.msg = "Số lượng chuyển không được phép vượt quá số lượng hiện tại trong kho.";
						return false;
					}
				}
			}
			
			//
			if(!flag)
			{
				this.msg = "Không có sản phẩm nào được nhập số lượng.";
				return false;
			}
			
		}
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "insert ERP_CHUYENKHO(ngaychuyen, ngaynhan, ngaychot, lydo, trangthai, khoxuat_fk, khonhan_fk, ngaytao, nguoitao, ngaysua, nguoisua) " +
							"values('" + this.ngayyeucau + "', '" + this.ngayyeucau + "', '" + this.ngayyeucau + "', N'" + this.lydoyeucau + "', '0', '" + this.khoXuatId + "', '" + this.khoNhanId + "', '" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "')";
			
			System.out.println("1.Insert: " + query);
			
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_CHUYENKHO " + query;
				db.getConnection().rollback();
				return false;
			}
			
			String ycnlCurrent = "";
			query = "select SCOPE_IDENTITY() as ckId";
			
			ResultSet rsPxk = db.get(query);						
			if(rsPxk.next())
			{
				ycnlCurrent = rsPxk.getString("ckId");
				rsPxk.close();
			}
			
			if(this.spList.size() > 0)
			{
				for(int i = 0; i < this.spList.size(); i++)
				{
					IYeucau sp = this.spList.get(i);
					
					if(sp.getSoluongYC().trim().length() > 0)
					{
						query = "insert ERP_CHUYENKHO_SANPHAM(chuyenkho_fk, SANPHAM_FK, solo, soluongxuat, vitrixuat, soluongnhan) " +
								"select '" + ycnlCurrent + "', a.pk_seq, '" + sp.getSolo() + "', '" + sp.getSoluongYC() + "', b.pk_seq, 0 " +
								"from ERP_SanPham a, ERP_VITRIKHO b where a.ma = '" + sp.getMa() + "' and b.ma = '" + sp.getVitriXuat() + "' ";
						
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi ERP_CHUYENKHO_SANPHAM: " + query;
							db.getConnection().rollback();
							return false;
						}
						
						
						//Update kho trang thai
						query = "update ERP_KHOTT_SP_CHITIET_TRANGTHAI set AVAILABLE = AVAILABLE - '" + sp.getSoluongYC() + "', BOOKED = BOOKED + '" + sp.getSoluongYC() + "'  " +
								"where KHOTT_FK = ( select KhoTT_FK from ERP_VITRIKHO where ma = '" + sp.getVitriXuat() + "') " +
										"and SANPHAM_FK = (select pk_seq from ERP_SanPham where ma = '" + sp.getMa() + "') and SOLO = '" + sp.getSolo() + "' " +
												"and BIN = ( select pk_seq from ERP_VITRIKHO where ma = '" + sp.getVitriXuat() + "' ) and trangthai = '1' ";
						
						System.out.println("2.Kho chi tiet - trang thai: " + query);
						
						if(!db.update(query))
						{
							this.msg = "Không thể cập nhật ERP_KHOTT_SP_CHITIET_TRANGTHAI " + query;
							db.getConnection().commit();
							return false;
						}
						
						
						//Update kho chi tiet
						query = "update ERP_KHOTT_SP_CHITIET set AVAILABLE = AVAILABLE - '" + sp.getSoluongYC() + "', BOOKED = BOOKED + '" + sp.getSoluongYC() + "'  " +
								"where KHOTT_FK = ( select KhoTT_FK from ERP_VITRIKHO where ma = '" + sp.getVitriXuat() + "') " +
										"and SANPHAM_FK = (select pk_seq from ERP_SanPham where ma = '" + sp.getMa() + "') and SOLO = '" + sp.getSolo() + "' " +
												"and BIN = ( select pk_seq from ERP_VITRIKHO where ma = '" + sp.getVitriXuat() + "' )  ";
						
						System.out.println("2.Kho chi tiet - trang thai: " + query);
						
						if(!db.update(query))
						{
							this.msg = "Không thể cập nhật ERP_KHOTT_SP_CHITIET_TRANGTHAI " + query;
							db.getConnection().commit();
							return false;
						}
						
						//Update KhoTT_SP
						query = "update ERP_KHOTT_SANPHAM set AVAILABLE = AVAILABLE - '" + sp.getSoluongYC() + "', BOOKED = BOOKED + '" + sp.getSoluongYC() + "'  " +
								"where KHOTT_FK = ( select KhoTT_FK from ERP_VITRIKHO where ma = '" + sp.getVitriXuat() + "') " +
										"and SANPHAM_FK = (select pk_seq from ERP_SanPham where ma = '" + sp.getMa() + "') ";
						
						System.out.println("2.Kho chi tiet - trang thai: " + query);
						
						if(!db.update(query))
						{
							this.msg = "Không thể cập nhật ERP_KHOTT_SANPHAM " + query;
							db.getConnection().commit();
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

	public boolean updateCK() 
	{
		if(this.ngayyeucau.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập ngày yêu cầu";
			return false;
		}
		
		if(this.spList.size() <= 0)
		{
			this.msg = "Không có sản phẩm nào được chọn";
			return false;
		}
		else
		{
			boolean flag = false;
			for(int i = 0; i < this.spList.size(); i++)
			{
				IYeucau yc = this.spList.get(i);
				if(yc.getSoluongYC().trim().length() > 0)
				{
					flag = true;
				
					String tonkho = yc.getTonhientai().trim().length() > 0 ? yc.getTonhientai().trim() : "0" ;
					String soluong = yc.getSoluongYC().trim().length() > 0 ? yc.getSoluongYC().trim() : "0" ;
					
					if( Float.parseFloat(soluong) >  Float.parseFloat(tonkho) )
					{
						this.msg = "Số lượng chuyển không được phép vượt quá số lượng hiện tại trong kho.";
						return false;
					}
				}
			}
			
			//
			if(!flag)
			{
				this.msg = "Không có sản phẩm nào được nhập số lượng.";
				return false;
			}
			
		}
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "Update ERP_CHUYENKHO set ngaychuyen = '" + this.ngayyeucau + "', ngaynhan = '" + this.ngayyeucau + "', lydo = N'" + this.lydoyeucau + "', khoxuat_fk = '" + this.khoXuatId + "'," +
							" khonhan_fk = '" + this.khoNhanId + "', ngaysua = '" + getDateTime() + "', nguoisua = '" + this.userId + "' " +
							"where pk_seq = '" + this.id + "' ";
							
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_CHUYENKHO " + query;
				db.getConnection().rollback();
				return false;
			}
			
			//Cap nhat lai Available truoc khi xoa
			query = "UPDATE ERP_KHOTT_SANPHAM   " +
					"SET 	ERP_KHOTT_SANPHAM.BOOKED = ERP_KHOTT_SANPHAM.BOOKED - ERP_CHUYENKHO_SANPHAM.SOLUONGXUAT,  " +
							"ERP_KHOTT_SANPHAM.AVAILABLE = ERP_KHOTT_SANPHAM.AVAILABLE + ERP_CHUYENKHO_SANPHAM.SOLUONGXUAT   " +
					"FROM ERP_KHOTT_SANPHAM inner join ERP_CHUYENKHO on ERP_KHOTT_SANPHAM.KHOTT_FK = ERP_CHUYENKHO.KhoXuat_FK  " +
					"INNER JOIN " +
					"( " +
						"select CHUYENKHO_FK, SANPHAM_FK, SUM(SOLUONGXUAT) as SOLUONGXUAT  " +
						"from ERP_CHUYENKHO_SANPHAM  " +
						"where CHUYENKHO_FK = '" + this.id + "'   " +
						"group by CHUYENKHO_FK, SANPHAM_FK  " +
					")  " +
					"ERP_CHUYENKHO_SANPHAM on ERP_KHOTT_SANPHAM.SANPHAM_FK = ERP_CHUYENKHO_SANPHAM.SANPHAM_FK and ERP_CHUYENKHO.PK_SEQ = ERP_CHUYENKHO_SANPHAM.CHUYENKHO_FK  " +
						"AND ERP_CHUYENKHO.PK_SEQ = '" + this.id + "'";
			
			if(!db.update(query))
			{
				this.msg = "1.Khong the cap nhat ERP_KHOTT_SANPHAM: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "UPDATE ERP_KHOTT_SP_CHITIET  " +
					"SET ERP_KHOTT_SP_CHITIET.BOOKED = ERP_KHOTT_SP_CHITIET.BOOKED - ChiTiet.SOLUONGXUAT,  " +
						"ERP_KHOTT_SP_CHITIET.AVAILABLE = ERP_KHOTT_SP_CHITIET.AVAILABLE + ChiTiet.SOLUONGXUAT   " +
					"FROM ERP_KHOTT_SP_CHITIET inner join ERP_CHUYENKHO on ERP_KHOTT_SP_CHITIET.KHOTT_FK = ERP_CHUYENKHO.KhoXuat_FK   " +
					"INNER JOIN  " +
					"( " +
						"select CHUYENKHO_FK, SANPHAM_FK, SOLO, VITRIXUAT, SUM(SOLUONGXUAT) as SOLUONGXUAT  " +
						"from ERP_CHUYENKHO_SANPHAM  " +
						"where CHUYENKHO_FK = '" + this.id + "' " +
						"group by CHUYENKHO_FK, SANPHAM_FK, SOLO, VITRIXUAT  " +
					")  " +
					"ChiTiet on ERP_KHOTT_SP_CHITIET.SANPHAM_FK = ChiTiet.SANPHAM_FK  " +
						"and ERP_KHOTT_SP_CHITIET.SOLO = ChiTiet.SOLO " +
						"and ERP_KHOTT_SP_CHITIET.BIN = ChiTiet.VITRIXUAT  " +
						"and ERP_CHUYENKHO.PK_SEQ = ChiTiet.CHUYENKHO_FK  " +
						"AND ERP_CHUYENKHO.PK_SEQ = '" + this.id + "'";
			
			if(!db.update(query))
			{
				this.msg = "2.Khong the cap nhat ERP_KHOTT_SP_CHITIET: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "UPDATE ERP_KHOTT_SP_CHITIET_TRANGTHAI  " +
					"SET ERP_KHOTT_SP_CHITIET_TRANGTHAI.BOOKED = ERP_KHOTT_SP_CHITIET_TRANGTHAI.BOOKED - ChiTiet.SOLUONGXUAT,  " +
						"ERP_KHOTT_SP_CHITIET_TRANGTHAI.AVAILABLE = ERP_KHOTT_SP_CHITIET_TRANGTHAI.AVAILABLE + ChiTiet.SOLUONGXUAT   " +
					"FROM ERP_KHOTT_SP_CHITIET_TRANGTHAI inner join ERP_CHUYENKHO on ERP_KHOTT_SP_CHITIET_TRANGTHAI.KHOTT_FK = ERP_CHUYENKHO.KhoXuat_FK    " +
					"INNER JOIN  " +
					"( " +
						"select CHUYENKHO_FK, SANPHAM_FK, SOLO, VITRIXUAT, '1' as trangthai, SUM(SOLUONGXUAT) as SOLUONGXUAT  " +
						"from ERP_CHUYENKHO_SANPHAM  " +
						"where CHUYENKHO_FK = '" + this.id + "' " +
						"group by CHUYENKHO_FK, SANPHAM_FK, SOLO, VITRIXUAT  " +
					")  " +
					"ChiTiet on ERP_KHOTT_SP_CHITIET_TRANGTHAI.SANPHAM_FK = ChiTiet.SANPHAM_FK   " +
						"and ERP_KHOTT_SP_CHITIET_TRANGTHAI.SOLO = ChiTiet.SOLO " +
						"and ERP_KHOTT_SP_CHITIET_TRANGTHAI.BIN = ChiTiet.VITRIXUAT  " +
						"and ERP_KHOTT_SP_CHITIET_TRANGTHAI.TRANGTHAI = ChiTiet.trangthai  " +
						"and ERP_CHUYENKHO.PK_SEQ = ChiTiet.CHUYENKHO_FK  " +
						"AND ERP_CHUYENKHO.PK_SEQ = '" + this.id + "'";
			
			if(!db.update(query))
			{
				this.msg = "3.Khong the cap nhat ERP_KHOTT_SP_CHITIET_TRANGTHAI: " + query;
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
			
			if(this.spList.size() > 0)
			{
				for(int i = 0; i < this.spList.size(); i++)
				{
					IYeucau sp = this.spList.get(i);
					
					if(sp.getSoluongYC().trim().length() > 0)
					{
						query = "insert ERP_CHUYENKHO_SANPHAM(chuyenkho_fk, SANPHAM_FK, solo, soluongxuat, vitrixuat, soluongnhan) " +
								"select '" + this.id + "', a.pk_seq, '" + sp.getSolo() + "', '" + sp.getSoluongYC() + "', b.pk_seq, 0 " +
								"from ERP_SanPham a, ERP_VITRIKHO b where a.ma = '" + sp.getMa() + "' and b.ma = '" + sp.getVitriXuat() + "' ";
						
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi ERP_CHUYENKHO_SANPHAM: " + query;
							db.getConnection().rollback();
							return false;
						}
						
						//Update kho trang thai
						query = "update ERP_KHOTT_SP_CHITIET_TRANGTHAI set AVAILABLE = AVAILABLE - '" + sp.getSoluongYC() + "', BOOKED = BOOKED + '" + sp.getSoluongYC() + "'  " +
								"where KHOTT_FK = ( select KhoTT_FK from ERP_VITRIKHO where ma = '" + sp.getVitriXuat() + "') " +
										"and SANPHAM_FK = (select pk_seq from ERP_SanPham where ma = '" + sp.getMa() + "') and SOLO = '" + sp.getSolo() + "' " +
												"and BIN = ( select pk_seq from ERP_VITRIKHO where ma = '" + sp.getVitriXuat() + "' ) and trangthai = '1' ";
						
						System.out.println("4.Kho chi tiet - trang thai: " + query);
						
						if(!db.update(query))
						{
							this.msg = "Không thể cập nhật ERP_KHOTT_SP_CHITIET_TRANGTHAI " + query;
							db.getConnection().commit();
							return false;
						}
						
						
						//Update kho chi tiet
						query = "update ERP_KHOTT_SP_CHITIET set AVAILABLE = AVAILABLE - '" + sp.getSoluongYC() + "', BOOKED = BOOKED + '" + sp.getSoluongYC() + "'  " +
								"where KHOTT_FK = ( select KhoTT_FK from ERP_VITRIKHO where ma = '" + sp.getVitriXuat() + "') " +
										"and SANPHAM_FK = (select pk_seq from ERP_SanPham where ma = '" + sp.getMa() + "') and SOLO = '" + sp.getSolo() + "' " +
												"and BIN = ( select pk_seq from ERP_VITRIKHO where ma = '" + sp.getVitriXuat() + "' )  ";
						
						System.out.println("5.Kho chi tiet: " + query);
						
						if(!db.update(query))
						{
							this.msg = "Không thể cập nhật ERP_KHOTT_SP_CHITIET_TRANGTHAI " + query;
							db.getConnection().commit();
							return false;
						}
						
						//Update KhoTT_SP
						query = "update ERP_KHOTT_SANPHAM set AVAILABLE = AVAILABLE - '" + sp.getSoluongYC() + "', BOOKED = BOOKED + '" + sp.getSoluongYC() + "'  " +
								"where KHOTT_FK = ( select KhoTT_FK from ERP_VITRIKHO where ma = '" + sp.getVitriXuat() + "') " +
										"and SANPHAM_FK = (select pk_seq from ERP_SanPham where ma = '" + sp.getMa() + "') ";
						
						System.out.println("6.Kho tong: " + query);
						
						if(!db.update(query))
						{
							this.msg = "Không thể cập nhật ERP_KHOTT_SANPHAM " + query;
							db.getConnection().commit();
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

	public boolean nhanHang() 
	{
		if(this.ngayyeucau.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập ngày nhận hàng";
			return false;
		}
		
		if(this.spList.size() <= 0)
		{
			this.msg = "Không có sản phẩm nào được chọn";
			return false;
		}
		else
		{
			boolean flag = false;
			for(int i = 0; i < this.spList.size(); i++)
			{
				IYeucau yc = this.spList.get(i);
				if(yc.getSoluongNhan().trim().length() > 0)
				{
					flag = true;
				
					String soluongNhan = yc.getSoluongNhan().trim().length() > 0 ? yc.getSoluongNhan().trim() : "0" ;
					String soluongChuyen = yc.getSoluongYC().trim().length() > 0 ? yc.getSoluongYC().trim() : "0" ;
					
					if( Float.parseFloat(soluongNhan) >  Float.parseFloat(soluongChuyen) )
					{
						this.msg = "Số lượng nhận không được phép vượt quá số lượng chuyển.";
						return false;
					}
				}
			}
			
			//
			if(!flag)
			{
				this.msg = "Không có sản phẩm nào được nhập số lượng.";
				return false;
			}
			
		}
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "Update ERP_CHUYENKHO set ngaynhan = '" + this.ngayyeucau + "', lydo = N'" + this.lydoyeucau + "', khoxuat_fk = '" + this.khoXuatId + "'," +
							" khonhan_fk = '" + this.khoNhanId + "', ngaysua = '" + getDateTime() + "', nguoisua = '" + this.userId + "', trangthai = '2' " +
							"where pk_seq = '" + this.id + "' ";
							
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_CHUYENKHO " + query;
				db.getConnection().rollback();
				return false;
			}
			
			
			query = "update ERP_CHUYENKHO_SANPHAM set SOLUONGNHAN = '0' where CHUYENKHO_FK = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_CHUYENKHO_SANPHAM " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_CHUYENKHO_SP_NHANHANG where chuyenkho_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_CHUYENKHO_SP_NHANHANG " + query;
				db.getConnection().rollback();
				return false;
			}
			
			
			if(this.spList.size() > 0)
			{
				for(int i = 0; i < this.spList.size(); i++)
				{
					IYeucau sp = this.spList.get(i);
					
					//Update tong nhap
					if(sp.getSoluongNhan().trim().length() > 0)
					{
						query = "update ERP_CHUYENKHO_SANPHAM set SOLUONGNHAN = SOLUONGNHAN + '" + sp.getSoluongNhan() + "' " +
								"where SANPHAM_FK = ( select pk_seq from ERP_SanPham where ma = '" + sp.getMa() + "' ) and SOLO = '" + sp.getSolo() + "' and CHUYENKHO_FK = '" + this.id + "'";
						
						if(!db.update(query))
						{
							this.msg = "Không thể cập nhật ERP_CHUYENKHO_SANPHAM " + query;
							db.getConnection().rollback();
							return false;
						}
					
						List<ISpDetail> spDetail = sp.getSpDetailList();
						if(spDetail.size() <= 0)
						{
							this.msg = "Vui lòng kiểm tra lại vị trí nhận hàng";
							db.getConnection().rollback();
							return false;
						}
						else
						{
							for(int j = 0; j < spDetail.size(); j++ )
							{
								ISpDetail detail = spDetail.get(j);
								
								String vitri = detail.getVitriId().substring(detail.getVitriId().indexOf(" - ") + 3, detail.getVitriId().length());
								
								if(detail.getSoluong().trim().length() > 0 && detail.getVitriId().trim().length() > 0 )
								{
									query = "insert ERP_CHUYENKHO_SP_NHANHANG(chuyenkho_fk, sanpham_fk, solo, vitri, soluong, khu)  " +
											"select '" + this.id + "', pk_seq, '" + sp.getSolo() + "', '" + vitri + "', '" + detail.getSoluong() + "', '" + detail.getKhu() + "' " +
											"from ERP_SanPham where ma = '" + sp.getMa() + "' ";
									
									if(!db.update(query))
									{
										this.msg = "Không thể cập nhật ERP_CHUYENKHO_SP_NHANHANG " + query;
										db.getConnection().rollback();
										return false;
									}
									
								}
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
			return false;
		}
		
		return true;
	}
	
	public boolean chuyenNL() 
	{
		if(this.msg.trim().length() > 0)
		{
			this.msg = "Vui lòng kiểm tra lại các thông tin: \n" + this.msg;
			return false;
		}
		
		if(this.khoXuatId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn kho xuất";
			return false;
		}
		
		if(this.khoNhanId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn kho nhận";
			return false;
		}
	
		if(this.ngayyeucau.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập ngày yêu cầu";
			return false;
		}
		
		if(this.spList.size() <= 0)
		{
			this.msg = "Không có sản phẩm nào được chọn";
			return false;
		}
		else
		{
			boolean flag = false;
			
			for(int i = 0; i < this.spList.size(); i++)
			{
				IYeucau yc = this.spList.get(i);
				
				String soluongYC = yc.getSoluongYC().trim().length() > 0 ? yc.getSoluongYC().trim() : "0" ;
				String soluongDN = yc.getSoluongDaNhan().trim().length() > 0 ? yc.getSoluongDaNhan().trim() : "0";
				String soluongN = yc.getSoluongNhan().trim().length() > 0 ? yc.getSoluongNhan().trim() : "0" ;
				
				if(Integer.parseInt(soluongN) > ( Integer.parseInt(soluongYC) - Integer.parseInt(soluongDN) ) )
				{
					this.msg = "Số lượng chuyển không được phép vượt quá số lượng yêu cầu và số lượng đã chuyển";
					return false;
				}
				
				if(yc.getSoluongNhan().trim().length() > 0)
				{
					if(Integer.parseInt(yc.getSoluongNhan().trim()) > 0)
					{
						flag = true;
					}
					
					List<ISpDetail> detail = yc.getSpDetailList();
					
					int sum = 0;
					for(int j = 0; j < detail.size(); j++)
					{
						if(detail.get(j).getSoluong().trim().length() > 0)
							sum += Integer.parseInt(detail.get(j).getSoluong().trim());
					}
					
					if( Integer.parseInt(yc.getSoluongNhan()) != sum )
					{
						this.msg = "Vui lòng kiểm tra Bin / Lô hàng xuất trước khi thực hiện thao tác.";
						flag = false;
						return false;
						
					}
				}
			}
			
			if(!flag)
			{
				this.msg = "Vui lòng kiểm tra Bin / Lô hàng xuất trước khi thực hiện thao tác.";
				return false;
			}
		}
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			for(int i = 0; i < this.spList.size(); i++)
			{
				IYeucau sp = this.spList.get(i);
				String query =  "Update ERP_YEUCAUNGUYENLIEU_SANPHAM set soluongnhan = soluongnhan + '" + sp.getSoluongNhan() + "' " +
								"where  yeucaunguyenlieu_fk = '" + this.id + "' and  SANPHAM_FK = ( select pk_seq from ERP_SanPham where ma = '" + sp.getMa() + "') ";
				
				if(!db.update(query))
				{
					this.msg = "Khong the cap nhat ERP_YEUCAUNGUYENLIEU_SANPHAM: " + query;
					db.getConnection().rollback();
					return false;
				}
				else
				{
					List<ISpDetail> spCon = sp.getSpDetailList();
					for(int j = 0; j < spCon.size(); j++)
					{
						ISpDetail detail = spCon.get(j);
						
						if( ! detail.equals("0"))
						{
							
							//Luu lai luong xuatkho
							query =  "insert ERP_YEUCAUNGUYENLIEU_SP_XUATKHO(yeucaunguyenlieu_fk, sanpham_fk, khott_fk, solo, soluong, bean) " +
									 "select '" + this.id + "', pk_seq, '" + this.khoXuatId + "', '" + detail.getSolo() + "', '" + detail.getSoluong() + "', '" + detail.getVitriId() + "' " +
									 "from ERP_SanPham where ma = '" + sp.getMa() + "' ";
							
							if(!db.update(query))
							{
								this.msg = "2.Khong the cap nhat ERP_YEUCAUNGUYENLIEU_SP_XUATKHO: " + query;
								db.getConnection().rollback();
								return false;
							}
							
							
							query = "select count(*) as sodong from ERP_YEUCAUNGUYENLIEU_SP_CHITIET " +
									"where yeucaunguyenlieu_fk = '" + this.id + "' and SANPHAM_FK = ( select pk_seq from ERP_SanPham where ma = '" + detail.getMa() + "' ) and SOLO = '" + detail.getSolo() + "' and BEAN = '" + detail.getVitriId() + "'";
							
							ResultSet rsCheck = db.get(query);
							int sodong = 0;
							if(rsCheck != null)
							{
								if(rsCheck.next())
								{
									sodong = rsCheck.getInt("sodong");
								}
								rsCheck.close();
							}
							
							if(sodong <= 0)
							{
								query = "insert ERP_YEUCAUNGUYENLIEU_SP_CHITIET(yeucaunguyenlieu_fk, SANPHAM_FK, SOLO, SOLUONG, BEAN) " +
										"select '" + this.id + "', pk_seq, '" + detail.getSolo() + "', '" + detail.getSoluong() + "', '" + detail.getVitriId() + "' " +
										"from ERP_SanPham where ma = '" + detail.getMa() + "' ";
							}
							else
							{
								query = "update ERP_YEUCAUNGUYENLIEU_SP_CHITIET set soluong = soluong + '" + detail.getSoluong() + "' " + 
										"where yeucaunguyenlieu_fk = '" + this.id + "' and SANPHAM_FK = ( select pk_seq from ERP_SanPham where ma = '" + detail.getMa() + "' ) and SOLO = '" + detail.getSolo() + "' and BEAN = '" + detail.getVitriId() + "'";
							}
							
							if(!db.update(query))
							{
								this.msg = "Khong the cap nhat ERP_YEUCAUNGUYENLIEU_SP_CHITIET: " + query;
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
		catch (Exception e) {}
		
		return true;
		
	}
	
	
	
	public void createRs() 
	{
		this.khoXuatRs = db.get("select PK_SEQ, TEN from ERP_KHOTT where TrangThai = '1' and Loai = '2' ");
		
		String query = "select PK_SEQ, TEN from ERP_KHOTT where TrangThai = '1' " +
							"and pk_seq in ( select ttpp_fk from ERP_NHAMAY_KHOTT where khott_fk in ( select pk_seq from ERP_KHOTT where TrangThai = '1' and LOAI = '2' ) )";
		
		this.khoNhanRs = db.get(query);
		
		/*if(this.isnhanHang.equals("0"))
		{
			query = "select a.PK_SEQ as lsxId, a.ngaybatdau, a.ngayketthuc, b.TEN as spTen  " +
					"from ERP_LENHSANXUAT a inner Join ERP_SanPham b on a.SANPHAM_FK = b.PK_SEQ " +
					"where a.trangthai = '1' and a.pk_seq not in ( select lenhsanxuat_fk from ERP_YEUCAUNGUYENLIEU_LSX where yeucaunguyenlieu_fk = (select pk_seq from ERP_YEUCAUNGUYENLIEU where trangthai != 3) )  ";
			
			if(this.id.trim().length() > 0)
			{
				query += " union ";
				query += " select b.PK_SEQ as lsxId, b.ngaybatdau, b.ngayketthuc, c.TEN as spTen  " +
						"from ERP_YEUCAUNGUYENLIEU_LSX a inner join ERP_LENHSANXUAT b on a.lenhsanxuat_fk = b.PK_SEQ  " +
							"inner Join ERP_SanPham c on b.SANPHAM_FK = c.PK_SEQ  " +
						"where a.yeucaunguyenlieu_fk = '" + this.id + "' ";
			}
			
			this.lsxRs = db.get(query);
		}*/
		
		
		//Khoi tao kho xuat + Kho nhan
		query = "select khoxuat.PK_SEQ as khoXuat, khonhan.PK_SEQ as khoNhan  " +
				"from " +
				"( " +
					"select PK_SEQ, TEN from ERP_KHOTT where TrangThai = '1' and Loai = '2'  " +
				")  " +
				"khoxuat,  " +
				"(  " +
					"select PK_SEQ, TEN from ERP_KHOTT where TrangThai = '1'  " +
							" and pk_seq in ( select ttpp_fk from ERP_NHAMAY_KHOTT where khott_fk in ( select pk_seq from ERP_KHOTT where TrangThai = '1' and LOAI = '2' ) )  " +
				")  " +
				"khonhan ";
		
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					this.khoXuatId = rs.getString("khoXuat");
					this.khoNhanId = rs.getString("khoNhan");
				}
				rs.close();
			} 
			catch (Exception e) {}
		}
		
		if(this.khoXuatId.trim().length() > 0 && this.khoNhanId.trim().length() > 0 && this.spList.size() <= 0 )
		{
			this.createChuyenKho_SanPham();
		}
		
	}

	private void createChuyenKho_SanPham() 
	{
		String query = "";
		
		if(this.id.trim().length() <= 0)
		{
			query = "select b.pk_seq as spId, b.MA as spMa, b.Ten as spTen, a.AVAILABLE as hienhuu, a.SOLO, c.MA as vitri, c.PK_SEQ as vitriId, '' as soluongXuat  " +
					"from ERP_KHOTT_SP_CHITIET_TRANGTHAI a inner Join ERP_SanPham b on a.SANPHAM_FK = b.PK_SEQ  " +
						"inner join ERP_VITRIKHO c on a.BIN = c.PK_SEQ  " +
					"where a.KHOTT_FK = '" + this.khoXuatId + "' and a.TRANGTHAI = '1' and a.AVAILABLE > 0 ";
		}
		else
		{
			if( Integer.parseInt(this.trangthai) <= 0 )
			{
				query = "select b.pk_seq as spId, b.MA as spMa, b.Ten as spTen,  " +
							" case ISNULL(e.TRANGTHAI, '0') when 0 then	a.AVAILABLE + CAST(ISNULL(d.SOLUONGXUAT, 0) as int) else a.AVAILABLE end as hienhuu,  " +
							"a.SOLO, c.MA as vitri, c.PK_SEQ as vitriId, ISNULL(d.SOLUONGXUAT, 0) as soluongXuat   " +
						"from ERP_KHOTT_SP_CHITIET_TRANGTHAI a inner Join ERP_SanPham b on a.SANPHAM_FK = b.PK_SEQ   " +
							"inner join ERP_VITRIKHO c on a.BIN = c.PK_SEQ   " +
							"left join ERP_CHUYENKHO_SANPHAM d on a.SANPHAM_FK = d.SANPHAM_FK and a.SOLO = d.SOLO and a.BIN = d.vitriXuat and d.CHUYENKHO_FK = '" + this.id + "'  " +
							"left join ERP_CHUYENKHO e on d.CHUYENKHO_FK = e.PK_SEQ  " +
						"where a.KHOTT_FK = '" + this.khoXuatId + "' and a.TRANGTHAI = '1' " +
								"and  ( case ISNULL(e.TRANGTHAI, '0') when 0 then	a.AVAILABLE + CAST(ISNULL(d.SOLUONGXUAT, 0) as int) else a.AVAILABLE end  ) > 0 ";
			}
			else
			{
				query = "select b.pk_seq as spId, b.MA as spMa, b.Ten as spTen,   0 as hienhuu,  a.SOLO, c.MA as vitri, c.PK_SEQ as vitriId,  " +
							"ISNULL(a.SOLUONGXUAT, 0) as soluongXuat    " +
						"from ERP_CHUYENKHO_SANPHAM a inner Join ERP_SanPham b on a.SANPHAM_FK = b.PK_SEQ    " +
						"inner join ERP_VITRIKHO c on a.vitrixuat = c.PK_SEQ    " +
						"where a.CHUYENKHO_FK = '" + this.id + "' ";
			}
		}
		
		System.out.println("__1.init SP: " + query);
		
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
					String tonkho = spRs.getString("hienhuu");
					String solo = spRs.getString("solo");
					String vitri = spRs.getString("vitri");
					
					sp = new Yeucau();
					sp.setId(spId);
					sp.setMa(spMa);
					sp.setTen(spTen);
					
					System.out.println("___Trang thai la: " + this.trangthai);
					
					if(this.trangthai.trim().length() > 0)
					{
						if(Integer.parseInt(this.trangthai) <= 0)
						{
							sp.setTonhientai(tonkho);
						}
					}
					
					sp.setSolo(solo);
					sp.setVitriXuat(vitri);
					
					if(spRs.getString("soluongXuat").trim().length() > 0)
						sp.setSoluongYC(spRs.getString("soluongXuat"));
									
					spList.add(sp);
				}
				spRs.close();
			} 
			catch (Exception e) 
			{
				System.out.println("115.Exception: " + e.getMessage());
			}
			
			this.spList = spList;
		}
	}

	public void init() 
	{
		String query = "select ngaychuyen, lydo, khoxuat_fk, khonhan_fk, trangthai from ERP_CHUYENKHO where pk_seq = '" + this.id + "'";
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					this.ngayyeucau = rs.getString("ngaychuyen");
					this.lydoyeucau = rs.getString("lydo");
					this.khoXuatId = rs.getString("khoxuat_fk");
					this.khoNhanId = rs.getString("khonhan_fk");
					this.trangthai = rs.getString("trangthai");
				}
				rs.close();
			} 
			catch (Exception e) {}
		}
		
		this.khoXuatRs = db.get("select PK_SEQ, TEN from ERP_KHOTT where TrangThai = '1' and Loai = '2' ");
		
		query = "select PK_SEQ, TEN from ERP_KHOTT where TrangThai = '1' " +
							"and pk_seq in ( select ttpp_fk from ERP_NHAMAY_KHOTT where khott_fk in ( select pk_seq from ERP_KHOTT where TrangThai = '1' and LOAI = '2' ) )";
		
		this.khoNhanRs = db.get(query);
		
		this.createChuyenKho_SanPham();
		
		//this.createRs();
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
			if(spChoNhapList!=null){
				spChoNhapList.clear();
			}
			if(khuList!=null){
				khuList.clear();
			}
			
			if(vitriList!=null){
				vitriList.clear();
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

	public String getIsnhanHang() 
	{
		return this.isnhanHang;
	}

	public void setIsnhanHang(String isnhanHang)
	{
		this.isnhanHang = isnhanHang;
	}

	
	public List<IKhu_Vitri> getKhuList()
	{
		return this.khuList;
	}

	public void setKhuList(List<IKhu_Vitri> khuList) 
	{
		this.khuList = khuList;
	}

	public List<IKhu_Vitri> getVitriList() 
	{
		return this.vitriList;
	}

	public void setVitriList(List<IKhu_Vitri> vitriList)
	{
		this.vitriList = vitriList;
	}

	public List<IYeucau> getSpChoNhapList()
	{
		return this.spChoNhapList;
	}

	public void setSpChoNhapList(List<IYeucau> spchoNhapList) 
	{
		this.spChoNhapList = spchoNhapList;
	}


	public void initYeucauNLPdf() 
	{
		String query =  "select a.SANPHAM_FK, b.MA, b.TEN, a.SOLUONGYEUCAU as SoLuong " +
						"from ERP_YEUCAUNGUYENLIEU_SANPHAM a " +
						"inner Join ERP_SanPham b on a.SANPHAM_FK = b.PK_SEQ where yeucaunguyenlieu_fk = '" + this.id + "'";
		
		System.out.println("1.Query khoi tao sp: " + query);
		ResultSet rs = db.get(query);
		
		if(rs != null)
		{
			List<IYeucau> spList = new ArrayList<IYeucau>();
			
			try 
			{
				IYeucau yeucau;

				while(rs.next())
				{
					String spId = rs.getString("SANPHAM_FK");
					String spMa = rs.getString("MA");
					String spTen = rs.getString("TEN");
					String soluong = rs.getString("SOLUONG");

					yeucau = new Yeucau();
					yeucau.setId(spId);
					yeucau.setMa(spMa);
					yeucau.setTen(spTen);
					yeucau.setSoluongYC(soluong);
			
					query = " select b.MA, b.TEN, c.MA AS VITRI, d.DIENGIAI AS KHUTEN, e.DONVI as DONVIDOLUONG, nhapkho.solo, nhapkho.tongNhap, f.ma as khoTen  " +
						"from " +
						"( " +
							"select sanpham_fk, khott_fk, solo, bean, SUM(soluong) as tongNhap  " +
							"from ERP_YEUCAUNGUYENLIEU_SP_NHAPKHO   " +
							"where yeucaunguyenlieu_fk = '" + this.id + "' and sanpham_fk = '" + spId + "'  " +
							"group by sanpham_fk, khott_fk, solo, bean  " +
						")  " +
						"nhapkho inner Join ERP_SanPham b on nhapkho.sanpham_fk = b.PK_SEQ " +
						"INNER JOIN ERP_VITRIKHO c ON nhapkho.BEAN = c.PK_SEQ  " +
						"inner join ERP_KHUVUCKHO d on c.KHU_FK = d.PK_SEQ  " +
						"inner join DONVIDOLUONG e on b.DVDL_FK = e.PK_SEQ inner join ERP_KHOTT f on d.khott_fk = f.pk_seq ";
					
					System.out.println("1.San pham lay hang Detail: " + query);
					ResultSet rsSpDetail = db.get(query);
					
					List<ISpDetail> spConList = new ArrayList<ISpDetail>();
					ISpDetail spCon = null;
					
					boolean flag = false;
					if(rsSpDetail != null)
					{
						while(rsSpDetail.next())
						{
							String idhangmua = rsSpDetail.getString("DONVIDOLUONG"); //luu donvidoluong
							String solo = rsSpDetail.getString("solo");
							
							int conlai = Integer.parseInt(soluong) - rsSpDetail.getInt("tongNhap");
							String slg = soluong + " - " + rsSpDetail.getString("tongNhap") + " - " + Integer.toString(conlai);
							String khu = rsSpDetail.getString("KHUTEN");
							String vitri = rsSpDetail.getString("VITRI");
							String vitriId = rsSpDetail.getString("khoTen");
							
							spCon = new SpDetail(idhangmua, solo, slg, khu, vitri, vitriId);
							spConList.add(spCon);
							
							flag = true;
						}
						rsSpDetail.close();
					}
					
					if(!flag)
					{
						String slg = soluong + " - 0 - " + soluong;
						spCon = new SpDetail(" ", " ", slg, " ", " ", " ");
						spConList.add(spCon);
					}
					
					yeucau.setSpDetailList(spConList);	
					spList.add(yeucau);
				}
			
				rs.close();
			} 
			catch (Exception e) 
			{ 
				System.out.println("1.Exception: " + e.getMessage() + "\n");
			}
			
			this.spList = spList;
		}
	}

	public void initChuyenNLPdf() 
	{
		String query =  "select a.SANPHAM_FK, b.MA, b.TEN, a.SOLUONGYEUCAU as SoLuong " +
				"from ERP_YEUCAUNGUYENLIEU_SANPHAM a " +
				"inner Join ERP_SanPham b on a.SANPHAM_FK = b.PK_SEQ where yeucaunguyenlieu_fk = '" + this.id + "'";
		
		System.out.println("1.Query khoi tao sp: " + query);
		ResultSet rs = db.get(query);
		
		if(rs != null)
		{
		List<IYeucau> spList = new ArrayList<IYeucau>();
		
		try 
		{
		IYeucau yeucau;
		
		while(rs.next())
		{
			String spId = rs.getString("SANPHAM_FK");
			String spMa = rs.getString("MA");
			String spTen = rs.getString("TEN");
			String soluong = rs.getString("SOLUONG");
		
			yeucau = new Yeucau();
			yeucau.setId(spId);
			yeucau.setMa(spMa);
			yeucau.setTen(spTen);
			yeucau.setSoluongYC(soluong);
		
			query = " select b.MA, b.TEN, c.MA AS VITRI, d.DIENGIAI AS KHUTEN, e.DONVI as DONVIDOLUONG, nhapkho.solo, nhapkho.tongNhap, f.ma as khoTen  " +
				"from " +
				"( " +
					"select sanpham_fk, khott_fk, solo, bean, SUM(soluong) as tongNhap  " +
					"from ERP_YEUCAUNGUYENLIEU_SP_XUATKHO   " +
					"where yeucaunguyenlieu_fk = '" + this.id + "' and sanpham_fk = '" + spId + "'  " +
					"group by sanpham_fk, khott_fk, solo, bean  " +
				")  " +
				"nhapkho inner Join ERP_SanPham b on nhapkho.sanpham_fk = b.PK_SEQ " +
				"INNER JOIN ERP_VITRIKHO c ON nhapkho.BEAN = c.PK_SEQ  " +
				"inner join ERP_KHUVUCKHO d on c.KHU_FK = d.PK_SEQ  " +
				"inner join DONVIDOLUONG e on b.DVDL_FK = e.PK_SEQ inner join ERP_KHOTT f on d.khott_fk = f.pk_seq ";
			
			System.out.println("1.San pham lay hang Detail: " + query);
			ResultSet rsSpDetail = db.get(query);
			
			List<ISpDetail> spConList = new ArrayList<ISpDetail>();
			ISpDetail spCon = null;
			
			boolean flag = false;
			if(rsSpDetail != null)
			{
				while(rsSpDetail.next())
				{
					String idhangmua = rsSpDetail.getString("DONVIDOLUONG"); //luu donvidoluong
					String solo = rsSpDetail.getString("solo");
					
					int conlai = Integer.parseInt(soluong) - rsSpDetail.getInt("tongNhap");
					String slg = soluong + " - " + rsSpDetail.getString("tongNhap") + " - " + Integer.toString(conlai);
					String khu = rsSpDetail.getString("KHUTEN");
					String vitri = rsSpDetail.getString("VITRI");
					String vitriId = rsSpDetail.getString("khoTen");
					
					spCon = new SpDetail(idhangmua, solo, slg, khu, vitri, vitriId);
					spConList.add(spCon);
					
					flag = true;
				}
				rsSpDetail.close();
			}
			
			if(!flag)
			{
				String slg = soluong + " - 0 - " + soluong;
				spCon = new SpDetail(" ", " ", slg, " ", " ", " ");
				spConList.add(spCon);
			}
			
			yeucau.setSpDetailList(spConList);	
			spList.add(yeucau);
		}
		
		rs.close();
		} 
		catch (Exception e) 
		{ 
		System.out.println("1.Exception: " + e.getMessage() + "\n");
		}
		
		this.spList = spList;
		}
	}

	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}


	public void initNhanhang() 
	{
		String query = "select ngaynhan, lydo, khoxuat_fk, khonhan_fk, trangthai from ERP_CHUYENKHO where pk_seq = '" + this.id + "'";
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					this.ngayyeucau = rs.getString("ngaynhan");
					this.lydoyeucau = rs.getString("lydo");
					this.khoXuatId = rs.getString("khoxuat_fk");
					this.khoNhanId = rs.getString("khonhan_fk");
					this.trangthai = rs.getString("trangthai");
				}
				rs.close();
			} 
			catch (Exception e) {}
		}
		
		this.khoXuatRs = db.get("select PK_SEQ, TEN from ERP_KHOTT where TrangThai = '1' and Loai = '2' ");
		
		query = "select PK_SEQ, TEN from ERP_KHOTT where TrangThai = '1' " +
							"and pk_seq in ( select ttpp_fk from ERP_NHAMAY_KHOTT where khott_fk in ( select pk_seq from ERP_KHOTT where TrangThai = '1' and LOAI = '2' ) )";
		
		this.khoNhanRs = db.get(query);
		
		if( this.spList.size() <= 0 )
		{
			this.createChuyenKho_SanPham_NhanHang();
		}
		
		
		//init Bean nhan
		query = "select pk_seq, ten from ERP_KHUVUCKHO where khott_fk = '" + this.khoNhanId + "' order by pk_seq";
		ResultSet khuRs = db.get(query);
		this.khuList.clear();

		if (khuRs != null)
		{
			try
			{
				while (khuRs.next())
				{
					//this.khuList.add(new Khu_Vitri(khuRs.getString("pk_seq"), khuRs.getString("ten")));
				}
				khuRs.close();
			} 
			catch (SQLException e){}
		}

		query = "select cast(b.pk_seq as nvarchar(10)) + ' - ' + cast(a.pk_seq as nvarchar(10)) as pk_seq, a.MA from ERP_VITRIKHO a inner join ERP_KHUVUCKHO b on a.khu_fk = b.pk_seq where b.khott_fk = '" + this.khoNhanId + "' order by a.khu_fk ASC";
		
		ResultSet vitriRs = db.get(query);
		this.vitriList.clear();

		if (vitriRs != null)
		{
			try
			{
				while (vitriRs.next())
				{
					//this.vitriList.add(new Khu_Vitri(vitriRs.getString("pk_seq"), vitriRs.getString("ma")));
				}
				vitriRs.close();
			} 
			catch (SQLException e){}
		}
		
	}

	private void createChuyenKho_SanPham_NhanHang() 
	{
		String query = " select b.PK_SEQ as spId, b.MA as spMa,  b.Ten as spTen, a.SOLO, SUM(a.SOLUONGXUAT) as tongXuat, isnull(Sum(a.SOLUONGNHAN), 0) as tongNhan  " +
				"from ERP_CHUYENKHO_SANPHAM a inner Join ERP_SanPham b on a.SANPHAM_FK = b.PK_SEQ  " +
				"where a.CHUYENKHO_FK = '" + this.id + "'  " +
				"group by b.PK_SEQ, b.MA, b.Ten, a.SOLO  " +
				"having SUM(a.SOLUONGXUAT) > 0 ";
		
		System.out.println("1.Khoi tao SP: " + query);
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
					String solo = spRs.getString("solo");
					
					sp = new Yeucau();
					sp.setId(spId);
					sp.setMa(spMa);
					sp.setTen(spTen);
					sp.setSolo(solo);
					
					if(spRs.getString("tongXuat").trim().length() > 0)
						sp.setSoluongYC(spRs.getString("tongXuat"));
					
					if(spRs.getString("tongNhan").trim().length() > 0)
						sp.setSoluongNhan(spRs.getString("tongNhan"));
					
					
					//Create kho nhan
					query = "select vitri, soluong, khu from ERP_CHUYENKHO_SP_NHANHANG   " +
							"where chuyenkho_fk = '" + this.id + "' and sanpham_fk = '" + spId + "' and solo = '" + solo + "'";
					
					System.out.println("__Khoi tao kho nhan: " + query);
					
					ResultSet rsSpDetail = db.get(query);
					List<ISpDetail> spConList = new ArrayList<ISpDetail>();
					ISpDetail spCon = null;
					if(rsSpDetail != null)
					{
						while(rsSpDetail.next())
						{
							String slg = rsSpDetail.getString("soluong");
							String khu = rsSpDetail.getString("khu");
							String vitriId = khu + " - " + rsSpDetail.getString("vitri");
							
							spCon = new SpDetail();
							spCon.setSoluong(slg);
							spCon.setKhu(khu);
							spCon.setVitriId(vitriId);
							
							spConList.add(spCon);
						}
						rsSpDetail.close();
					}
					
					sp.setSpDetailList(spConList);	
					
					spList.add(sp);
				}
				spRs.close();
			} 
			catch (Exception e) 
			{
				System.out.println("115.Exception: " + e.getMessage());
			}
			
			this.spList = spList;
		}
	}


	public void initView() 
	{
		String query = "select ngaynhan, lydo, khoxuat_fk, khonhan_fk, trangthai from ERP_CHUYENKHO where pk_seq = '" + this.id + "'";
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					this.ngayyeucau = rs.getString("ngaynhan");
					this.lydoyeucau = rs.getString("lydo");
					this.khoXuatId = rs.getString("khoxuat_fk");
					this.khoNhanId = rs.getString("khonhan_fk");
					this.trangthai = rs.getString("trangthai");
				}
				rs.close();
			} 
			catch (Exception e) {}
		}
		
		this.khoXuatRs = db.get("select PK_SEQ, TEN from ERP_KHOTT where TrangThai = '1' and Loai = '2' ");
		
		query = "select PK_SEQ, TEN from ERP_KHOTT where TrangThai = '1' " +
							"and pk_seq in ( select ttpp_fk from ERP_NHAMAY_KHOTT where khott_fk in ( select pk_seq from ERP_KHOTT where TrangThai = '1' and LOAI = '2' ) )";
		
		this.khoNhanRs = db.get(query);
		
		if( this.spList.size() <= 0 )
		{
			this.createChuyenKho_SanPham_View();
		}
		
		
		//init Bean nhan
		query = "select pk_seq, ten from ERP_KHUVUCKHO where khott_fk = '" + this.khoNhanId + "' order by pk_seq";
		ResultSet khuRs = db.get(query);
		this.khuList.clear();

		if (khuRs != null)
		{
			try
			{
				while (khuRs.next())
				{
				//	this.khuList.add(new Khu_Vitri(khuRs.getString("pk_seq"), khuRs.getString("ten")));
				}
				khuRs.close();
			} 
			catch (SQLException e){}
		}

		query = "select cast(b.pk_seq as nvarchar(10)) + ' - ' + cast(a.pk_seq as nvarchar(10)) as pk_seq, a.MA from ERP_VITRIKHO a inner join ERP_KHUVUCKHO b on a.khu_fk = b.pk_seq where b.khott_fk = '" + this.khoNhanId + "' order by a.khu_fk ASC";
		
		ResultSet vitriRs = db.get(query);
		this.vitriList.clear();

		if (vitriRs != null)
		{
			try
			{
				while (vitriRs.next())
				{
					//this.vitriList.add(new Khu_Vitri(vitriRs.getString("pk_seq"), vitriRs.getString("ma")));
				}
				vitriRs.close();
			} 
			catch (SQLException e){}
		}
		
	}
	
	private void createChuyenKho_SanPham_View() 
	{
		String query = " select b.PK_SEQ as spId, b.MA as spMa, b.Ten as spTen, a.SOLO, SUM(a.SOLUONGXUAT) as tongXuat, isnull(Sum(a.SOLUONGNHAN), 0) as tongNhan  " +
				"from ERP_CHUYENKHO_SANPHAM a inner Join ERP_SanPham b on a.SANPHAM_FK = b.PK_SEQ  " +
				"where a.CHUYENKHO_FK = '" + this.id + "'  " +
				"group by b.PK_SEQ, b.MA, b.TEN, a.SOLO  " +
				"having SUM(a.SOLUONGXUAT) > 0 ";
		
		System.out.println("1.Khoi tao SP: " + query);
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
					String solo = spRs.getString("solo");
					
					sp = new Yeucau();
					sp.setId(spId);
					sp.setMa(spMa);
					sp.setTen(spTen);
					sp.setSolo(solo);
					
					if(spRs.getString("tongXuat").trim().length() > 0)
						sp.setSoluongYC(spRs.getString("tongXuat"));
					
					if(spRs.getString("tongNhan").trim().length() > 0)
						sp.setSoluongNhan(spRs.getString("tongNhan"));
					
					
					//Create kho nhan
					query = "select vitri, soluong, khu from ERP_CHUYENKHO_SP_NHANHANG   " +
							"where chuyenkho_fk = '" + this.id + "' and sanpham_fk = '" + spId + "' and solo = '" + solo + "'";
					
					System.out.println("1.init Nhap: " + query);
					
					ResultSet rsSpDetail = db.get(query);
					List<ISpDetail> spConList = new ArrayList<ISpDetail>();
					ISpDetail spCon = null;
					if(rsSpDetail != null)
					{
						while(rsSpDetail.next())
						{
							String slg = rsSpDetail.getString("soluong");
							String khu = rsSpDetail.getString("khu");
							String vitriId = khu + " - " + rsSpDetail.getString("vitri");
							
							spCon = new SpDetail();
							spCon.setSoluong(slg);
							spCon.setKhu(khu);
							spCon.setVitriId(vitriId);
							
							spConList.add(spCon);
						}
						rsSpDetail.close();
					}
					
					sp.setSpDetailList(spConList);	
					
					
					//Create khoxuat (cho nguoi dung coi lai)
					query = "select SOLO, SOLUONGXUAT, a.vitrixuat, b.MA as vitri, c.Ma as Khu  " +
							"from ERP_CHUYENKHO_SANPHAM a inner join ERP_VITRIKHO b on a.vitrixuat = b.PK_SEQ inner join ERP_KHUVUCKHO c on b.KHU_FK = c.pk_seq   " +
							"where SANPHAM_FK = '" + spId + "' and SOLO = '" + solo + "' and CHUYENKHO_FK = '" + this.id + "'";
					
					System.out.println("2.init Xuat: " + query);
					
					rsSpDetail = db.get(query);
					List<ISpDetail> spCon2List = new ArrayList<ISpDetail>();
					spCon = null;
					if(rsSpDetail != null)
					{
						while(rsSpDetail.next())
						{
							String slg = rsSpDetail.getString("SOLUONGXUAT");
							String khu = rsSpDetail.getString("Khu");
							String vitri = rsSpDetail.getString("vitri");
							
							spCon = new SpDetail();
							spCon.setSoluong(slg);
							spCon.setKhu(khu);
							spCon.setVitri(vitri);
							
							spCon2List.add(spCon);
						}
						rsSpDetail.close();
					}
					
					sp.setSpDetail2List(spCon2List);	
					
					
					spList.add(sp);
				}
				spRs.close();
			} 
			catch (Exception e) 
			{
				System.out.println("1.Exception: " + e.getMessage());
			}
			
			this.spList = spList;
		}
	}
	
	

}
