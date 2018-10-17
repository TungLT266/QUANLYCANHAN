package geso.traphaco.erp.beans.lenhsanxuat.imp;

import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.beans.lenhsanxuat.IErpYeucaunguyenlieu;
import geso.traphaco.erp.beans.lenhsanxuat.IYeucau;
import geso.traphaco.erp.beans.nhapkho.IKhu_Vitri;
import geso.traphaco.erp.beans.nhapkho.imp.Khu_Vitri;
import geso.traphaco.erp.beans.phieuxuatkho.ISpDetail;
import geso.traphaco.erp.beans.phieuxuatkho.imp.SpDetail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ErpYeucaunguyenlieu implements IErpYeucaunguyenlieu
{
	private static final long serialVersionUID = 1L;
	String userId;
	String id;
	String ngayyeucau;
	String lydoyeucau;

	String msg;
	String ischuyenNL;
	String trangthai;
	
	String dvkd;
	String tenkhoxuat;
	
	String nhamayId;
	ResultSet nhamayRs;
	
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
	
	public ErpYeucaunguyenlieu()
	{
		this.id = "";
		this.ngayyeucau = getDateTime();
		this.lydoyeucau = "";
		this.tenkhoxuat="";
		this.dvkd="";
		this.khoXuatId = "";
		this.khoNhanId = "";
		this.lsxIds = "";
		this.msg = "";
		this.ischuyenNL = "0";
		this.trangthai = "";
		this.nhamayId = "";

		this.spList = new ArrayList<IYeucau>();
		this.spChoNhapList = new ArrayList<IYeucau>();
		
		this.khuList = new ArrayList<IKhu_Vitri>();
		this.vitriList = new ArrayList<IKhu_Vitri>();
		
		this.db = new dbutils();
	}
	
	public ErpYeucaunguyenlieu(String id)
	{
		this.id = id;
		this.ngayyeucau = "";
		this.lydoyeucau = "";
		this.khoXuatId = "";
		this.khoNhanId = "";
		this.lsxIds = "";
		this.msg = "";
		this.ischuyenNL = "0";
		this.trangthai = "";
		this.nhamayId = "";

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

	
	public boolean createYcnl() 
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
			for(int i = 0; i < this.spList.size(); i++)
			{
				IYeucau yc = this.spList.get(i);
				
				String soluongYC = yc.getSoluongYC().trim().length() > 0 ? yc.getSoluongYC().trim() : "0" ;
				String soluongDN = yc.getSoluongDaNhan().trim().length() > 0 ? yc.getSoluongDaNhan().trim() : "0";
				String soluongN = yc.getSoluongNhan().trim().length() > 0 ? yc.getSoluongNhan().trim() : "0" ;
				
				if(Float.parseFloat(soluongN) > ( Float.parseFloat(soluongYC) - Float.parseFloat(soluongDN) ) )
				{
					this.msg = "Số lượng nhận không được phép vượt quá số lượng yêu cầu và số lượng đã nhập kho";
					return false;
				}
			}
		}
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "insert ERP_YEUCAUNGUYENLIEU(ngayyeucau, lydo, trangthai, khoxuat_fk, khonhan_fk, ngaytao, nguoitao, ngaysua, nguoisua) " +
							"values('" + this.ngayyeucau + "', N'" + this.lydoyeucau + "', '0', '" + this.khoXuatId + "', '" + this.khoNhanId + "', '" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "')";
			
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_YEUCAUNGUYENLIEU " + query;
				db.getConnection().rollback();
				return false;
			}
			
			String ycnlCurrent = "";
			query = "select IDENT_CURRENT('ERP_YEUCAUNGUYENLIEU') as ycnlId";
			
			ResultSet rsPxk = db.get(query);						
			if(rsPxk.next())
			{
				ycnlCurrent = rsPxk.getString("ycnlId");
				rsPxk.close();
			}
			
			if(this.lsxIds.trim().length() > 0)
			{
				query = "Insert ERP_YEUCAUNGUYENLIEU_LSX ( yeucaunguyenlieu_fk, lenhsanxuat_fk ) " +
						"select '" + ycnlCurrent + "', pk_seq from Erp_LenhSanXuat where pk_seq in ( " + this.lsxIds + " ) ";
				
				if(!db.update(query))
				{
					this.msg = "Không thể tạo mới ERP_YEUCAUNGUYENLIEU_LSX " + query;
					db.getConnection().rollback();
					return false;
				}
			}
			
			if(this.spList.size() > 0)
			{
				for(int i = 0; i < this.spList.size(); i++)
				{
					IYeucau sp = this.spList.get(i);
					query = "insert ERP_YEUCAUNGUYENLIEU_SANPHAM(yeucaunguyenlieu_fk, SANPHAM_FK, soluongyeucau, soluongnhan) " +
							"select '" + ycnlCurrent + "', pk_seq, '" + sp.getSoluongYC() + "', '0' " +
							"from ERP_SanPham where ten = N'" + sp.getTen() + "' ";
					
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi ERP_YEUCAUNGUYENLIEU_SANPHAM: " + query;
						db.getConnection().rollback();
						return false;
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

	public boolean updateYcnl() 
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
			for(int i = 0; i < this.spList.size(); i++)
			{
				IYeucau yc = this.spList.get(i);
				
				String soluongYC = yc.getSoluongYC().trim().length() > 0 ? yc.getSoluongYC().trim() : "0" ;
				String soluongDN = yc.getSoluongDaNhan().trim().length() > 0 ? yc.getSoluongDaNhan().trim() : "0";
				String soluongN = yc.getSoluongNhan().trim().length() > 0 ? yc.getSoluongNhan().trim() : "0" ;
				
				if(Float.parseFloat(soluongN) > ( Float.parseFloat(soluongYC) - Float.parseFloat(soluongDN) ) )
				{
					this.msg = "Số lượng nhận không được phép vượt quá số lượng yêu cầu và số lượng đã nhập kho";
					return false;
				}
			}
		}
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "select count(sanpham_fk) as sodong from ERP_YEUCAUNGUYENLIEU_SANPHAM where yeucaunguyenlieu_fk = '" + this.id + "' and soluongnhan > 0";
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
			
			if(sodong > 0)
			{
				this.msg = "Lệnh sản xuất này đã phát sinh chuyển nguyên liệu. Bạn không thể cập nhật.";
				return false;
			}
			
			query = "Update ERP_YEUCAUNGUYENLIEU set ngayyeucau = '" + this.ngayyeucau + "', lydo = N'" + this.lydoyeucau + "', khoxuat_fk = '" + this.khoXuatId + "'," +
					" khonhan_fk = '" + this.khoNhanId + "', ngaysua = '" + getDateTime() + "', nguoisua = '" + this.userId + "' " +
					"where pk_seq = '" + this.id + "' ";
							
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_YEUCAUNGUYENLIEU " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_YEUCAUNGUYENLIEU_LSX where yeucaunguyenlieu_fk = '" + this.id + "' ";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_YEUCAUNGUYENLIEU_LSX " + query;
				db.getConnection().rollback();
				return false;
			}
			
			if(this.lsxIds.trim().length() > 0)
			{
				query = "Insert ERP_YEUCAUNGUYENLIEU_LSX ( yeucaunguyenlieu_fk, lenhsanxuat_fk ) " +
						"select '" + this.id + "', pk_seq from Erp_LenhSanXuat where pk_seq in ( " + this.lsxIds + " ) ";
				
				if(!db.update(query))
				{
					this.msg = "Không thể tạo mới ERP_YEUCAUNGUYENLIEU_LSX " + query;
					db.getConnection().rollback();
					return false;
				}
			}
			
			query = "delete ERP_YEUCAUNGUYENLIEU_SANPHAM where yeucaunguyenlieu_fk = '" + this.id + "' ";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_YEUCAUNGUYENLIEU_SANPHAM " + query;
				db.getConnection().rollback();
				return false;
			}
			
			if(this.spList.size() > 0)
			{
				for(int i = 0; i < this.spList.size(); i++)
				{
					IYeucau sp = this.spList.get(i);
					query = "insert ERP_YEUCAUNGUYENLIEU_SANPHAM(yeucaunguyenlieu_fk, SANPHAM_FK, soluongyeucau, soluongnhan) " +
							"select '" + this.id + "', pk_seq, '" + sp.getSoluongYC() + "', '0' " +
							"from ERP_SanPham where ten = N'" + sp.getTen() + "' ";
					
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi ERP_YEUCAUNGUYENLIEU_SANPHAM: " + query;
						db.getConnection().rollback();
						return false;
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

	public boolean chotYcnl() 
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
			for(int i = 0; i < this.spList.size(); i++)
			{
				IYeucau yc = this.spList.get(i);
				
				String soluongYC = yc.getSoluongYC().trim().length() > 0 ? yc.getSoluongYC().trim() : "0" ;
				String soluongDN = yc.getSoluongDaNhan().trim().length() > 0 ? yc.getSoluongDaNhan().trim() : "0";
				String soluongN = yc.getSoluongNhan().trim().length() > 0 ? yc.getSoluongNhan().trim() : "0" ;
				
				if(Float.parseFloat(soluongN) > ( Float.parseFloat(soluongYC) - Float.parseFloat(soluongDN) ) )
				{
					this.msg = "Số lượng nhận không được phép vượt quá số lượng yêu cầu và số lượng đã nhập kho";
					return false;
				}
			}
		}
		
		//Nhap vao kho. Hoi lai cach tinh gia ton, ghi nhan but toan ke toan luc nay
		try
		{
			db.getConnection().setAutoCommit(false);
			
			for(int i = 0; i < this.spList.size(); i++)
			{
				IYeucau sp = this.spList.get(i);
				
				if( !sp.getSoluongNhan().equals("0"))
				{
					List<ISpDetail> detailList = sp.getSpDetailList();
					for(int j = 0; j < detailList.size(); j++)
					{
						ISpDetail detail = detailList.get(j);
						
						//String[] vitri = detail.getVitriId().split(" - ");
						
						//Luu lai luong nhapkho
						String query =  "insert ERP_YEUCAUNGUYENLIEU_SP_NHAPKHO(yeucaunguyenlieu_fk, sanpham_fk, khott_fk, solo, soluong, bean) " +
										"select '" + this.id + "', a.pk_seq, '" + this.khoNhanId + "', '" + detail.getSolo() + "', '" + detail.getSoluong() + "', b.pk_seq " +
										"from ERP_SanPham a, erp_vitrikho b " +
										"where a.ma = '" + sp.getMa() + "' and b.trangthai = '1' and b.khott_fk = '" + this.khoNhanId + "' ";
						
						if(!db.update(query))
						{
							this.msg = "2.Khong the cap nhat ERP_YEUCAUNGUYENLIEU_SP_NHAPKHO: " + query;
							db.getConnection().rollback();
							return false;
						}
						

						query = "select count(*) as sodong from ERP_KHOTT_SP_CHITIET " +
								"where khott_fk = '" + this.khoNhanId + "' and sanpham_fk = ( select pk_seq from ERP_SanPham where ma = '" + sp.getMa() + "' ) " +
										"and solo = '" + detail.getSolo() + "' and bin in ( select pk_seq from erp_vitrikho where trangthai = '1' and khott_fk = '" + this.khoNhanId + "' ) ";
						
						ResultSet rsCheck = db.get(query);
						boolean flag = true;
						if(rsCheck != null)
						{
							if(rsCheck.next())
							{
								if(rsCheck.getString("sodong").equals("0"))
									flag = false;
								rsCheck.close();
							}
						}
						
						if(flag) //da ton tau, cap nhat booked, avail
						{
							query = "update ERP_KHOTT_SP_CHITIET set soluong = soluong + '" + detail.getSoluong() + "', AVAILABLE = AVAILABLE + '" + detail.getSoluong() + "' " +
									"where KHOTT_FK = '" + this.khoNhanId + "' and SANPHAM_FK = ( select pk_seq from ERP_SanPham where ma = '" + sp.getMa() + "' ) " +
										"and SOLO = '" + detail.getSolo() + "' and BIN in (  select pk_seq from erp_vitrikho where trangthai = '1' and khott_fk = '" + this.khoNhanId + "' ) ";
						}
						else
						{
							//lay ngay sanxuat, ngayhethan tu kho chuyen di
							String ngaysanxuat = "";
							String ngayhethan = "";
							query = "select ngaysanxuat, ngayhethan  " +
									"from erp_khott_sp_chitiet where sanpham_fk = ( select pk_seq from ERP_SanPham where ma = '" + detail.getMa() + "' ) and khott_fk = '" + this.khoXuatId + "' " +
										" and solo = '" + detail.getSolo() + "' and bin in ( select pk_seq from erp_vitrikho where trangthai = '1' and khott_fk = '" + this.khoXuatId + "' )";
							
							System.out.println("__Lay ngay SX + ngay HH: " + query);
							ResultSet rsNgay = db.get(query);
							if(rsNgay != null)
							{
								if(rsNgay.next())
								{
									ngaysanxuat = rsNgay.getString("ngaysanxuat");
									ngayhethan = rsNgay.getString("ngayhethan");
								}
								rsNgay.close();
							}
							
							query = "insert ERP_KHOTT_SP_CHITIET(KHOTT_FK, SANPHAM_FK, SOLUONG, BOOKED, AVAILABLE, SOLO, BIN, NGAYSANXUAT, NGAYHETHAN, NGAYNHAPKHO) " +
									"select '" + this.khoNhanId + "', a.pk_seq, '" + detail.getSoluong() + "', '0', '" + detail.getSoluong() + "', '" + detail.getSolo() + "', b.pk_seq, '" + ngaysanxuat + "', '" + ngayhethan + "', '" + getDateTime() + "' " +
									"from ERP_SanPham a, erp_vitrikho b " +
									"where a.ma = '" + detail.getMa() + "' and b.trangthai = '1' and b.khott_fk = '" + this.khoNhanId + "' ";
						}
						
						System.out.println("1__Tang kho nhan chi tiet: " + query);
						if(!db.update(query))
						{
							this.msg = "3.Khong the cap nhat ERP_KHOTT_SP_CHITIET: " + query;
							db.getConnection().rollback();
							return false;
						}
						
						query = "update ERP_KHOTT_SANPHAM set soluong = soluong + '" + detail.getSoluong() + "', AVAILABLE = AVAILABLE + '" + detail.getSoluong() + "' " +
								"where KHOTT_FK = '" + this.khoNhanId + "' and SANPHAM_FK = ( select pk_seq from ERP_SanPham where ma = '" + sp.getMa() + "' ) ";
						
						System.out.println("1__Tang kho nhan: " + query);
						if(!db.update(query))
						{
							this.msg = "3.Khong the cap nhat ERP_KHOTT_SANPHAM: " + query;
							db.getConnection().rollback();
							return false;
						}
						
						
						
						query = "update ERP_KHOTT_SP_CHITIET set soluong = soluong - '" + detail.getSoluong() + "', AVAILABLE = AVAILABLE - '" + detail.getSoluong() + "' " +
								"where KHOTT_FK = '" + this.khoXuatId + "' and SANPHAM_FK = ( select pk_seq from ERP_SanPham where ma = '" + sp.getMa() + "' ) " +
									"and SOLO = '" + detail.getSolo() + "' and BIN in ( select pk_seq from erp_vitrikho where trangthai = '1' and khott_fk = '" + this.khoXuatId + "' ) ";
						
						System.out.println("3__Giam kho xua chi tiet: " + query);
						if(!db.update(query))
						{
							this.msg = "3.Khong the cap nhat ERP_KHOTT_SP_CHITIET: " + query;
							db.getConnection().rollback();
							return false;
						}
						
						query = "update ERP_KHOTT_SANPHAM set soluong = soluong - '" + detail.getSoluong() + "', AVAILABLE = AVAILABLE - '" + detail.getSoluong() + "' " +
								"where KHOTT_FK = '" + this.khoXuatId + "' and SANPHAM_FK = ( select pk_seq from ERP_SanPham where ma = '" + sp.getMa() + "' ) ";
						
						System.out.println("4__Giam kho xuat: " + query);
						if(!db.update(query))
						{
							this.msg = "4.Khong the cap nhat ERP_KHOTT_SANPHAM: " + query;
							db.getConnection().rollback();
							return false;
						}
						
						
					}
				}
			}
			
			String query = "select count(sanpham_fk) as soDong from ERP_YEUCAUNGUYENLIEU_SANPHAM where soluongyeucau - soluongnhan != 0 and yeucaunguyenlieu_fk = '" + this.id + "'";
			ResultSet rsCheck = db.get(query);
			int sodong = 0;
			if (rsCheck != null)
			{
				if(rsCheck.next())
				{
					sodong = rsCheck.getInt("soDong");
				}
				rsCheck.close();
			}			
			if(sodong <= 0)
			{
				//Hoan tat
				query = " Update ERP_YEUCAUNGUYENLIEU set trangthai = '2' where pk_seq = '" + this.id + "' ";
				if(!db.update(query))
				{
					this.msg = "4.Khong the cap nhat ERP_YEUCAUNGUYENLIEU: " + query;
					db.getConnection().rollback();
					return false;
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (Exception e) 
		{
			db.update("rollback");
			this.msg = "1.Exception: " + e.getMessage();
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
			this.createChuyenNL();
			if(this.msg.trim().length() > 0)
			{
				return false;
			}
			
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
						this.msg += "Vui lòng kiểm tra số lượng Bin / Lô hàng xuất của sản phẩm " + yc.getTen() + ", trước khi thực hiện thao tác.\n";
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
							
							System.out.println("1133____Insert yeucau NL: " + query);
							
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
			
			String query = "Update ERP_YEUCAUNGUYENLIEU set trangthai = '1', nguoisua = '" + this.userId + "', ngaysua = '" + getDateTime() + "' where pk_seq = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat ERP_YEUCAUNGUYENLIEU: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (Exception e) 
		{
			db.update("rollback");
			this.msg = "115.Exception: " + e.getMessage();
			return false;
		}
		
		return true;
		
	}
	
	
	
	public void createRs() 
	{
		this.nhamayRs = db.get("select pk_seq, manhamay + ', ' + tennhamay as nhamayTen from ERP_NHAMAY where congty_fk = '100005'");
		
		//Lay 1 nha may mac dinh
		ResultSet rs = db.get("select pk_seq, manhamay + ', ' + tennhamay from ERP_NHAMAY where congty_fk = '100005'");
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					this.nhamayId = rs.getString("pk_seq");
				}
				rs.close();
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		//Kho xuat la kho nguyen lieu & thanh pham sau san xuat
		this.khoXuatRs = db.get("select PK_SEQ, TEN from ERP_KHOTT where TrangThai = '1' and loai = '2' and congty_fk = '100005' ");
		
		rs = db.get("select PK_SEQ, TEN from ERP_KHOTT where TrangThai = '1' and loai = '2' and congty_fk = '100005' ");
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					this.khoXuatId = rs.getString("pk_seq");
				}
				rs.close();
			} 
			catch (Exception e) {}
		}
		
		
		this.khoNhanRs = db.get("select PK_SEQ, TEN from ERP_KHOTT where TrangThai = '1' and loai = '1' and congty_fk = '100005' ");
		
		rs = db.get("select PK_SEQ, TEN from ERP_KHOTT where TrangThai = '1' and loai = '1' and congty_fk = '100005' ");
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					this.khoNhanId = rs.getString("pk_seq");
				}
				rs.close();
			} 
			catch (Exception e) {}
		}
		
		
		if(this.ischuyenNL.equals("0"))
		{
			String query = 	"select a.PK_SEQ as lsxId, a.ngaybatdau, a.ngaydukienht, b.TEN as spTen  " +
							"from ERP_LENHSANXUAT a inner Join ERP_SanPham b on a.SANPHAM_FK = b.PK_SEQ " +
							"where a.trangthai in (1, 2) and a.nhamay_fk = '" + this.nhamayId + "' and a.pk_seq not in ( select lenhsanxuat_fk from ERP_YEUCAUNGUYENLIEU_LSX where yeucaunguyenlieu_fk in (select pk_seq from ERP_YEUCAUNGUYENLIEU where trangthai != 3) )  ";
			
			if(this.id.trim().length() > 0)
			{
				query += " union ";
				query += " select b.PK_SEQ as lsxId, b.ngaybatdau, b.ngaydukienht, c.TEN as spTen  " +
						"from ERP_YEUCAUNGUYENLIEU_LSX a inner join ERP_LENHSANXUAT b on a.lenhsanxuat_fk = b.PK_SEQ  " +
							"inner Join ERP_SanPham c on b.SANPHAM_FK = c.PK_SEQ  " +
						"where a.yeucaunguyenlieu_fk = '" + this.id + "' and nhamay_fk = '" + this.nhamayId + "' ";
			}
			
			this.lsxRs = db.get(query);
		}
		
		if(this.khoXuatId.trim().length() > 0 && this.khoNhanId.trim().length() > 0 )
		{
			if(this.ischuyenNL.equals("0"))
			{
				if(this.lsxIds.trim().length() > 0)
				{
					this.createYeuCauNL();
				}
			}
			else
			{
				this.createChuyenNL();
			}
		}
		
	}

	
	private void createChuyenNL() 
	{
		for(int i = 0; i < this.spList.size(); i++)
		{
			IYeucau yeucau = this.spList.get(i);
			String soluongNhan = yeucau.getSoluongNhan().trim();
			
			if(soluongNhan.length() > 0)
			{
				List<ISpDetail> spDetail = new ArrayList<ISpDetail>();
				
				String query = "select SANPHAM_FK, isnull(AVAILABLE, 0) as AVAILABLE, SOLO, b.pk_seq as vtId, b.MA as vitri, c.diengiai as khu " +
								"from ERP_KHOTT_SP_CHITIET a inner join ERP_VITRIKHO b on a.BIN = b.PK_SEQ " +
									"inner join ERP_KHUVUCKHO c on b.KHU_FK = c.pk_seq " +
								"where a.sanpham_fk = ( select pk_seq from ERP_SanPham where ma = '" + yeucau.getMa() + "' ) and a.KHOTT_FK = '" + this.khoXuatId + "' " +
								"order by a.ngayhethan asc, a.AVAILABLE asc";
				
				System.out.println("112.Check soluong san pham: " + query);
				
				ResultSet rsSpDetail = db.get(query);
				
				if(rsSpDetail != null)
				{
					int tongluong = 0;
					
					try
					{
						while(rsSpDetail.next())
						{
							int avaiD = rsSpDetail.getInt("AVAILABLE");
							String maspD = yeucau.getMa();
							String soloD = rsSpDetail.getString("SOLO");
							String vitriD = rsSpDetail.getString("vitri");
							String vitriIdD = rsSpDetail.getString("vtId");
							String khuD = rsSpDetail.getString("khu");
							
							if(avaiD > 0)
							{
								tongluong += avaiD;
								if(tongluong < Integer.parseInt(soluongNhan))
								{
									ISpDetail spDetail2 = new SpDetail(maspD, soloD, Integer.toString(avaiD), khuD, vitriD, vitriIdD);
									spDetail.add(spDetail2);
								}
								else
								{
									int slg = Integer.parseInt(soluongNhan) - (tongluong - avaiD);
									ISpDetail spDetail2 = new SpDetail(maspD, soloD, Integer.toString(slg), khuD, vitriD, vitriIdD);
									spDetail.add(spDetail2);
									break;
								}
							}
						}
						
						rsSpDetail.close();
					}
					catch (Exception e) 
					{
						System.out.println("1.Exception: " + e.getMessage());
					}
	
				}
				
				this.spList.get(i).setSpDetailList(spDetail);
				
			}
		}
		
		System.out.println("Size la: " + this.spList.size());
		
		for(int i = 0; i < this.spList.size(); i++)
		{
			IYeucau sp = this.spList.get(i);
			
			if(sp.getSoluongNhan().trim().length() > 0)
			{
				List<ISpDetail> spDetail = sp.getSpDetailList();
				
				int sum = 0;
				for(int j = 0; j < spDetail.size(); j++)
				{
					sum += Integer.parseInt(spDetail.get(j).getSoluong());
				}
				
				if(sum < Integer.parseInt(sp.getSoluongNhan()))
				{
					this.msg += "+ Sản phẩm " + sp.getMa() + " - " + sp.getTen() + ", không đủ số lượng để xuất kho, vui lòng kiểm tra lại \n";
				}
			}
		}
	}

	private void createYeuCauNL() 
	{
		String query = "select c.PK_SEQ as spId, c.MA as spMa, c.TEN as spTen, sum(b.SoLuong) as SoLuong  " +
				"from ERP_LENHSANXUAT a inner join ERP_LENHSANXUAT_BOM b on a.PK_SEQ = b.lenhsanxuat_fk  " +
						"inner Join ERP_SanPham c on b.VatTu_fk = c.PK_SEQ  " +
				"where a.PK_SEQ in ( " + this.lsxIds + " )  " +
				"group by c.PK_SEQ, c.MA,  c.TEN";
		
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
					String soluong = Long.toString( Math.round( spRs.getFloat("soluong") ) );
					
					sp = new Yeucau();
					sp.setId(spId);
					sp.setMa(spMa);
					sp.setTen(spTen);
					sp.setSoluongYC(soluong);
									
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

	public void init() 
	{
		String query = "select ngayyeucau, lydo, khoxuat_fk, khonhan_fk, trangthai from ERP_YEUCAUNGUYENLIEU where pk_seq = '" + this.id + "'";
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					this.ngayyeucau = rs.getString("ngayyeucau");
					this.lydoyeucau = rs.getString("lydo");
					this.khoXuatId = rs.getString("khoxuat_fk");
					this.khoNhanId = rs.getString("khonhan_fk");
					this.trangthai = rs.getString("trangthai");
				}
				rs.close();
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		query = "select lenhsanxuat_fk from ERP_YEUCAUNGUYENLIEU_LSX where yeucaunguyenlieu_fk = '" + this.id + "'";
		rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				String lsxId = "";
				while(rs.next())
				{
					lsxId += rs.getString("lenhsanxuat_fk") + ",";
				}
				rs.close();
				
				if(lsxId.trim().length() > 0)
				{
					lsxId = lsxId.substring(0, lsxId.length() - 1);
				}
				
				this.lsxIds = lsxId;	
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		this.initSanPham();
		
		this.nhamayRs = db.get("select pk_seq, manhamay + ', ' + tennhamay as nhamayTen from ERP_NHAMAY where congty_fk = '100005'");
		
		rs = db.get("select pk_seq, manhamay + ', ' + tennhamay from ERP_NHAMAY where congty_fk = '100005'");
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					this.nhamayId = rs.getString("pk_seq");
				}
				rs.close();
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		//Kho xuat la kho nguyen lieu & thanh pham sau san xuat
		this.khoXuatRs = db.get("select PK_SEQ, TEN from ERP_KHOTT where TrangThai = '1' and loai = '2' and congty_fk = '100005' ");
		this.khoNhanRs = db.get("select PK_SEQ, TEN from ERP_KHOTT where TrangThai = '1' and loai = '1' and congty_fk = '100005' ");
		
		
		
		if(this.ischuyenNL.equals("0"))
		{
			query = "select a.PK_SEQ as lsxId, a.ngaybatdau, a.ngaydukienht, b.TEN as spTen  " +
					"from ERP_LENHSANXUAT a inner Join ERP_SanPham b on a.SANPHAM_FK = b.PK_SEQ " +
					"where a.trangthai = '1' and a.nhamay_fk = '" + this.nhamayId + "' " +
					"and a.pk_seq not in ( " +
					"    select lenhsanxuat_fk from ERP_YEUCAUNGUYENLIEU_LSX " +
					"    where yeucaunguyenlieu_fk in (select pk_seq from ERP_YEUCAUNGUYENLIEU where trangthai != 3) )  ";
			
			if(this.id.trim().length() > 0)
			{
				query += " union ";
				query += " select b.PK_SEQ as lsxId, b.ngaybatdau, b.ngaydukienht, c.TEN as spTen  " +
						"from ERP_YEUCAUNGUYENLIEU_LSX a inner join ERP_LENHSANXUAT b on a.lenhsanxuat_fk = b.PK_SEQ  " +
							"inner Join ERP_SanPham c on b.SANPHAM_FK = c.PK_SEQ  " +
						"where a.yeucaunguyenlieu_fk = '" + this.id + "' and nhamay_fk = '" + this.nhamayId + "' ";
			}
			
			System.out.println("1.Khoi tao LSX: " + query);
			this.lsxRs = db.get(query);
			
			
			//init Bean Nhan
			ResultSet khuRs = db.get("select pk_seq, ten from ERP_KHUVUCKHO where khott_fk = '" + this.khoNhanId + "' order by pk_seq");
			this.khuList.clear();

			if (khuRs != null)
			{
				try
				{
					while (khuRs.next())
					{
						this.khuList.add(new Khu_Vitri(khuRs.getString("pk_seq"), khuRs.getString("ten")));
					}
					khuRs.close();
				} 
				catch (SQLException e){
					e.printStackTrace();
				}
			}

			query = "select cast(b.pk_seq as nvarchar(10)) + ' - ' + cast(a.pk_seq as nvarchar(10)) as pk_seq, a.MA " +
					"from ERP_VITRIKHO a inner join ERP_KHUVUCKHO b on a.khu_fk = b.pk_seq where a.khott_fk = '" + this.khoNhanId + "' " +
					"order by a.khu_fk ASC";
			
			System.out.println("___Khoi tao vi tri: " + query);
			
			ResultSet vitriRs = db.get(query);
			this.vitriList.clear();

			if (vitriRs != null)
			{
				try
				{
					while (vitriRs.next())
					{
						this.vitriList.add(new Khu_Vitri(vitriRs.getString("pk_seq"), vitriRs.getString("ma")));
					}
					vitriRs.close();
				} 
				catch (SQLException e){
					e.printStackTrace();
				}
			}
		}
		
		//this.createRs();
	}

	
	private void initSanPham() 
	{
		String loaiten = "2";
		if(this.ischuyenNL.equals("1"))
			loaiten = "1";
		
		String query =  " select yeucau.spId, yeucau.spMa, yeucau.spTen, yeucau.SoLuongYC, yeucau.soluongNhan, ISNULL(SoLuongNhap, 0) as soLuongNhap  " +
					"from  " +
					"( " +
						"select b.PK_SEQ as spId, b.MA as spMa, isnull(b.TEN" + loaiten + ", b.TEN) as spTen, a.soluongyeucau as SoLuongYC, soluongNhan   " +
						"from ERP_YEUCAUNGUYENLIEU_SANPHAM a    " +
							"inner Join ERP_SanPham b on a.sanpham_fk = b.PK_SEQ    " +
						"where a.yeucaunguyenlieu_fk = '" + this.id + "' " +
					") " +
					"yeucau left join " +
					"(  " +
						"select sanpham_fk, SUM(soluong) as SoLuongNhap   " +
						"from ERP_YEUCAUNGUYENLIEU_SP_NHAPKHO where yeucaunguyenlieu_fk = '" + this.id + "'  " +
						"group by sanpham_fk  " +
					")  " +
					"nhapkho on yeucau.spId = nhapkho.sanpham_fk ";

		System.out.println("1.Query khoi tao sp: " + query);
		ResultSet rs = db.get(query);
		
		if(rs != null)
		{
			List<IYeucau> spList = new ArrayList<IYeucau>();
			
			try 
			{
				IYeucau sanpham;
				IYeucau sanphamDN;
				
				while(rs.next())
				{
					String spId = rs.getString("spId");
					String spMa = rs.getString("spMa");
					String spTen = rs.getString("spTen");
					
					String soluongYC = rs.getString("SoLuongYC");
					String soLuongNhap = rs.getString("soLuongNhap");
					String soluongNhan = rs.getString("soluongNhan");
					
					sanpham = new Yeucau();
					sanpham.setId(spId);
					sanpham.setMa(spMa);
					sanpham.setTen(spTen);
					
					sanphamDN = new Yeucau();
					sanphamDN.setId(spId);
					sanphamDN.setMa(spMa);
					sanphamDN.setTen(spTen);
					
					if(this.ischuyenNL.equals("0"))
					{
						sanpham.setSoluongYC(soluongYC);
						sanpham.setSoluongDaNhan(soLuongNhap);
						
						soluongNhan = Integer.toString(Integer.parseInt(soluongNhan) - Integer.parseInt(soLuongNhap));
						sanpham.setSoluongNhan(soluongNhan);
						
						//System.out.println("2.So luong nhan: " + sanpham.getSoluongNhan());
					}
					else
					{
						sanpham.setSoluongYC(soluongYC);
						sanpham.setSoluongDaNhan(soluongNhan);
						sanpham.setSoluongNhan("0");
					}
			
					query = "select a.SOLO, a.SOLUONG, c.pk_seq as khuId, c.diengiai as khuTen, b.PK_SEQ as vtId, b.MA as vitri, isnull(d.soluong, 0) as soluongNhap   " +
							"from ERP_YEUCAUNGUYENLIEU_SP_CHITIET a inner join ERP_VITRIKHO b on a.BEAN = b.PK_SEQ   " +
								"inner join ERP_KHUVUCKHO c on b.KHU_FK = c.pk_seq  " +
								"left join " +
								"( " +
								"	select sanpham_fk, solo, bean, sum(soluong) as soluong " +
								"	from ERP_YEUCAUNGUYENLIEU_SP_NHAPKHO where yeucaunguyenlieu_fk = '" + this.id + "' " +
								"	group by sanpham_fk, solo, bean  " +
								") " +
								"d on a.sanpham_fk = d.sanpham_fk and a.solo = d.solo and a.Bean = d.Bean " +
							"where a.yeucaunguyenlieu_fk = '" + this.id + "' and a.SANPHAM_FK = '" + spId + "'";
					
					System.out.println("2.San pham Detail: " + query);
					ResultSet rsSpDetail = db.get(query);
					
					List<ISpDetail> spConList = new ArrayList<ISpDetail>();
					ISpDetail spCon = null;
					if(rsSpDetail != null)
					{
						while(rsSpDetail.next())
						{
							String idhangmua = spId;
							String solo = rsSpDetail.getString("solo");
							String slg = Integer.toString(rsSpDetail.getInt("soluong") - rsSpDetail.getInt("soluongNhap"));
							if(this.ischuyenNL.equals("1"))
								slg = "0";
							
							String khu = rsSpDetail.getString("khuTen");
							String vitri = rsSpDetail.getString("vitri");
							String vitriId = rsSpDetail.getString("vtId");
							
							spCon = new SpDetail(idhangmua, solo, slg, khu, vitri, vitriId);
							spConList.add(spCon);
						}
						rsSpDetail.close();
					}
					
					sanpham.setSpDetailList(spConList);	
					
					spList.add(sanpham);
				}
			
				rs.close();
			} 
			catch (Exception e) 
			{
				System.out.println("3.Exception: " + e.getMessage());
			}
			
			this.spList = spList;
		}
	}

	public void DBclose() {
		try{
			if(khoXuatRs!=null){
				khoXuatRs.close();
			}
		
			if(khoNhanRs!=null){
				khoNhanRs.close();
			}
			
			
			if(lsxRs!=null){
				lsxRs.close();
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
			
			if(spList!=null){
				spList.clear();
			}
			
			if(nhamayRs!=null){
				nhamayRs.close();
			}
		}catch(Exception er){
			er.printStackTrace();
		}finally{
			this.db.shutDown();
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

	public String getIschuyenNL() 
	{
		return this.ischuyenNL;
	}

	public void setIschuyenNL(String ischuyenNL)
	{
		this.ischuyenNL = ischuyenNL;
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
	
	public void initsxyeucaunlPdf() {
		// TODO Auto-generated method stub
		String query = 
			" SELECT YCNL.NGAYYEUCAU, YCNL.LYDO, KX.TEN AS KHOXUAT_FK, KN.TEN AS KHONHAN_FK, YCNL.TRANGTHAI " +
			" FROM ERP_PHIEUYEUCAU YCNL " +
			" INNER JOIN ERP_KHOTT KX ON KX.PK_SEQ = YCNL.KHOXUAT_FK " +
			" INNER JOIN ERP_KHOTT KN ON KN.PK_SEQ = YCNL.KHONHAN_FK " +
			" WHERE YCNL.pk_seq = '" + this.id + "'";
		//System.out.println("[ErpYeucaunguyenlieu.initChuyenNLPdf] query = " + query);
		ResultSet rs = db.get(query);
		try 
		{
			if(rs.next())
			{
				this.ngayyeucau = rs.getString("NGAYYEUCAU");
				this.lydoyeucau = rs.getString("LYDO");
				this.khoXuatId = rs.getString("KHOXUAT_FK");
				this.khoNhanId = rs.getString("KHONHAN_FK");
				this.trangthai = rs.getString("TRANGTHAI");
			}
			rs.close();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		
		query = " select ISNULL(A.GHICHU,'')  AS GHICHU, a.SANPHAM_FK, a.SOLUONGYEUCAU as SoLuong, a.SOLUONGNHAN AS SOLUONGNHAN, " +
				" b.MA, b.Ten as Ten, b.dai, b.rong, b.dinhluong, b.trongluong, isnull(b.dvdl_dai, '') as dvdl_dai, isnull(b.dvdl_rong, '') as dvdl_rong, isnull(b.dvdl_dinhluong, '') as dvdl_dinhluong, isnull(b.quycach_nguongoc, '') as nguongoc," +
				" ISNULL(DVDL.DONVI,'') AS DONVI " +
				" from ERP_PHIEUYEUCAU_SANPHAM a " +
				" inner Join ERP_SanPham b on a.SANPHAM_FK = b.PK_SEQ " +
				" left join DONVIDOLUONG DVDL ON DVDL.PK_SEQ = B.DVDL_FK " +
				" where phieuyeucau_fk = '" + this.id + "'  " +
				" order by  b.ma ";
		
		//System.out.println("[ErpYeucaunguyenlieu.initsanpham] query = " + query);
		
		rs = db.get(query);
		
		if(rs != null)
		{
			List<IYeucau> spList = new ArrayList<IYeucau>();
			
			try 
			{
				IYeucau yeucau;
				NumberFormat format = new DecimalFormat("#,###,###.###");
				
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
					yeucau.setSoluongNhan(rs.getString("SOLUONG"));
					yeucau.setDonViTinh(rs.getString("DONVI"));
					yeucau.setDiengiai(rs.getString("GHICHU"));
				
					double fDai = rs.getFloat("Dai"), fRong = rs.getFloat("RONG"), fDinhLuong = rs.getFloat("DinhLuong"), fTrongluong = rs.getFloat("TrongLuong");
					String sDai = format.format(fDai), sRong = format.format(fRong), sDinhluong = format.format(fDinhLuong), sTrongluong = format.format(fTrongluong);
					//Quy cach
					String qc = "";

				    if(fDinhLuong > 0 ) {
				        qc += sDinhluong + rs.getString("DVDL_DINHLUONG");
				    }
				    if(fRong > 0 ) {
				        if(qc.length() > 0) { qc += " X "; }
				        qc += sRong + rs.getString("DVDL_Rong");
				    }
				    if(fDai > 0 ) {
				    	if(qc.length() > 0) { qc += " X "; }
				    	qc += sDai + rs.getString("DVDL_DAI");
				    }
				    yeucau.setQuyCach(qc);
				    
				    yeucau.setNguonGoc(rs.getString("NGUONGOC"));
				
					spList.add(yeucau);
				}
				
				rs.close();
			} 
			catch (Exception e) 
			{ 
				e.printStackTrace();
				
				//System.out.println("1.Exception: " + e.getMessage() + "\n");
			}
			
			this.spList = spList;
		}
	}

	public void initYeucauNLPdf() 
	{
		String query =  "select a.SANPHAM_FK, b.MA, b.Ten as Ten, a.SOLUONGYEUCAU as SoLuong " +
						"from ERP_YEUCAUNGUYENLIEU_SANPHAM a " +
						"inner Join ERP_SanPham b on a.SANPHAM_FK = b.PK_SEQ where yeucaunguyenlieu_fk = '" + this.id + "' ";
		
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
		String query = 
			" SELECT YCNL.NGAYYEUCAU, YCNL.LYDO, KX.TEN AS KHOXUAT_FK, KN.TEN AS KHONHAN_FK, YCNL.TRANGTHAI " +
			" FROM ERP_YEUCAUNGUYENLIEU YCNL " +
			" INNER JOIN ERP_KHOTT KX ON KX.PK_SEQ = YCNL.KHOXUAT_FK " +
			" INNER JOIN ERP_KHOTT KN ON KN.PK_SEQ = YCNL.KHONHAN_FK " +
			" WHERE YCNL.pk_seq = '" + this.id + "'";
		System.out.println("[ErpYeucaunguyenlieu.initChuyenNLPdf] query = " + query);
		ResultSet rs = db.get(query);
		 
		try 
		{
			if(rs.next())
			{
				this.ngayyeucau = rs.getString("NGAYYEUCAU");
				this.lydoyeucau = rs.getString("LYDO");
				this.khoXuatId = rs.getString("KHOXUAT_FK");
				this.khoNhanId = rs.getString("KHONHAN_FK");
				this.trangthai = rs.getString("TRANGTHAI");
			}
			rs.close();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		
		query = " select a.SANPHAM_FK, a.SOLUONGYEUCAU as SoLuong, a.SOLUONGNHAN AS SOLUONGNHAN, " +
				" b.MA, b.Ten as Ten, b.dai, b.rong, b.dinhluong, b.trongluong, isnull(b.dvdl_dai, '') as dvdl_dai, isnull(b.dvdl_rong, '') as dvdl_rong, isnull(b.dvdl_dinhluong, '') as dvdl_dinhluong, isnull(b.quycach_nguongoc, '') as nguongoc," +
				" ISNULL(DVDL.DONVI,'') AS DONVI " +
				" from ERP_YEUCAUNGUYENLIEU_SANPHAM a " +
				" inner Join ERP_SanPham b on a.SANPHAM_FK = b.PK_SEQ " +
				" left join DONVIDOLUONG DVDL ON DVDL.PK_SEQ = B.DVDL_FK " +
				" where yeucaunguyenlieu_fk = '" + this.id + "'" +
				" and a.SOLUONGYEUCAU > 0";
		rs = db.get(query);
		
		if(rs != null)
		{
			List<IYeucau> spList = new ArrayList<IYeucau>();
			
			try 
			{
				IYeucau yeucau;
				NumberFormat format = new DecimalFormat("#,###,###.###");
				
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
					yeucau.setSoluongNhan(rs.getString("SOLUONGNHAN"));
					yeucau.setDonViTinh(rs.getString("DONVI"));
					
					double fDai = rs.getFloat("Dai"), fRong = rs.getFloat("RONG"), fDinhLuong = rs.getFloat("DinhLuong"), fTrongluong = rs.getFloat("TrongLuong");
					String sDai = format.format(fDai), sRong = format.format(fRong), sDinhluong = format.format(fDinhLuong), sTrongluong = format.format(fTrongluong);
		 
					String qc = "";

				    if(fDinhLuong > 0 ) {
				        qc += sDinhluong + rs.getString("DVDL_DINHLUONG");
				    }
				    if(fRong > 0 ) {
				        if(qc.length() > 0) { qc += " X "; }
				        qc += sRong + rs.getString("DVDL_Rong");
				    }
				    if(fDai > 0 ) {
				    	if(qc.length() > 0) { qc += " X "; }
				    	qc += sDai + rs.getString("DVDL_DAI");
				    }
				    yeucau.setQuyCach(qc);
				    
				    yeucau.setNguonGoc(rs.getString("NGUONGOC"));
				     
					spList.add(yeucau);
				}
				
				rs.close();
			} 
			catch (Exception e) 
			{  
				e.printStackTrace();
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

	
	public String getNhamayId() 
	{
		return this.nhamayId;
	}

	public void setNhamayId(String nhamayId) 
	{
		this.nhamayId = nhamayId;
	}

	public ResultSet getNhamayRs()
	{
		return this.nhamayRs;
	}

	public void setNhamayRs(ResultSet nhamayRs) 
	{
		this.nhamayRs = nhamayRs;
	}

	@Override
	public String getDvkd() {
		// TODO Auto-generated method stub
		return this.dvkd;
	}

	@Override
	public void setDvkd(String dvkd) {
		// TODO Auto-generated method stub
		this.dvkd=dvkd;
	}

	@Override
	public String getKhoXuatTen() {
		// TODO Auto-generated method stub
		return this.tenkhoxuat;
	}

	@Override
	public void initXuatkhoPdf() {
		// TODO Auto-generated method stub
		String query = 
			" SELECT KX.TEN AS KHOXUAT, YCNL.NGAYYEUCAU, YCNL.LYDO, KX.TEN AS KHOXUAT_FK, KN.TEN AS KHONHAN_FK, YCNL.TRANGTHAI " +
			" FROM ERP_YEUCAUNGUYENLIEU YCNL " +
			" INNER JOIN ERP_KHOTT KX ON KX.PK_SEQ = YCNL.KHOXUAT_FK " +
			" INNER JOIN ERP_KHOTT KN ON KN.PK_SEQ = YCNL.KHONHAN_FK " +
			" WHERE YCNL.pk_seq = '" + this.id + "'";
		 
		ResultSet rs = db.get(query);
		try 
		{
			if(rs.next())
			{
				this.ngayyeucau = rs.getString("NGAYYEUCAU");
				this.lydoyeucau = rs.getString("LYDO");
				this.khoXuatId = rs.getString("KHOXUAT_FK");
				this.khoNhanId = rs.getString("KHONHAN_FK");
				this.trangthai = rs.getString("TRANGTHAI");
				this.tenkhoxuat=rs.getString("KHOXUAT");
			}
			rs.close();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		
		query = " select  b.ten +isnull(b.quycach,'') as diengiai,  b.dvkd_fk , a.SANPHAM_FK, a.SOLUONGYEUCAU as SoLuong, a.SOLUONGNHAN AS SOLUONGNHAN, " +
				" b.MA, b.Ten as Ten, b.dai, b.rong, b.dinhluong, b.trongluong, isnull(b.dvdl_dai, '') as dvdl_dai, isnull(b.dvdl_rong, '') as dvdl_rong, isnull(b.dvdl_dinhluong, '') as dvdl_dinhluong, isnull(b.quycach_nguongoc, '') as nguongoc," +
				" ISNULL(DVDL.DONVI,'') AS DONVI,  " +
				" a.SOLUONGNHAN *  CASE WHEN b.DVDL_FK=100003 THEN 1 ELSE CAST( QC.SOLUONG2 AS FLOAT)  / CAST( QC.SOLUONG1 AS FLOAT)  END  as TRONGLUONGNHAN " +
				" from ERP_YEUCAUNGUYENLIEU_SANPHAM a " +
				" inner Join ERP_SanPham b on a.SANPHAM_FK = b.PK_SEQ " +
				" LEFT JOIN QUYCACH QC ON QC.SANPHAM_FK=b.PK_SEQ   AND QC.DVDL1_FK=b.DVDL_FK AND QC.DVDL2_FK=100003 " +
				" left join DONVIDOLUONG DVDL ON DVDL.PK_SEQ = B.DVDL_FK " +
				" where yeucaunguyenlieu_fk = '" + this.id + "'" +
				" and a.SOLUONGNHAN > 0";
		rs = db.get(query);
		
		if(rs != null)
		{
			List<IYeucau> spList = new ArrayList<IYeucau>();
			
			try 
			{
				IYeucau yeucau;
				NumberFormat format = new DecimalFormat("#,###,###.###");
				
				while(rs.next())
				{
					String spId = rs.getString("SANPHAM_FK");
					String spMa = rs.getString("MA");
					String spTen = rs.getString("TEN");
					String soluong = rs.getString("SOLUONG");
					this.dvkd= rs.getString("dvkd_fk");
					yeucau = new Yeucau();
					yeucau.setId(spId);
					yeucau.setMa(spMa);
					yeucau.setTen(spTen);
					yeucau.setSoluongYC(soluong);
					yeucau.setSoluongNhan(rs.getString("SOLUONGNHAN"));
					yeucau.setDiengiai(rs.getString("diengiai"));
					yeucau.setDonViTinh(rs.getString("DONVI"));
					yeucau.setTrongLuong( rs.getString("TRONGLUONGNHAN"));
					double fDai = rs.getFloat("Dai"), fRong = rs.getFloat("RONG"), fDinhLuong = rs.getFloat("DinhLuong"), fTrongluong = rs.getFloat("TrongLuong");
					String sDai = format.format(fDai), sRong = format.format(fRong), sDinhluong = format.format(fDinhLuong), sTrongluong = format.format(fTrongluong);
		 
					String qc = "";

				    if(fDinhLuong > 0 ) {
				        qc += sDinhluong + rs.getString("DVDL_DINHLUONG");
				    }
				    if(fRong > 0 ) {
				        if(qc.length() > 0) { qc += " X "; }
				        qc += sRong + rs.getString("DVDL_Rong");
				    }
				    if(fDai > 0 ) {
				    	if(qc.length() > 0) { qc += " X "; }
				    	qc += sDai + rs.getString("DVDL_DAI");
				    }
				    yeucau.setQuyCach(qc);
				    
				    yeucau.setNguonGoc(rs.getString("NGUONGOC"));
				
				 
					spList.add(yeucau);
				}
				
				rs.close();
			} 
			catch (Exception e) 
			{ 
				e.printStackTrace();
			}
			
			this.spList = spList;
		}
	}



}
