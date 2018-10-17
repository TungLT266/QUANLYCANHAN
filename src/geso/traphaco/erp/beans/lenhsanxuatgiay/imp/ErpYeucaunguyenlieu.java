package geso.traphaco.erp.beans.lenhsanxuatgiay.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.beans.lenhsanxuatgiay.*;
import geso.traphaco.erp.beans.nhapkho.IKhu_Vitri;
import geso.traphaco.erp.beans.nhapkho.imp.Khu_Vitri;
import geso.traphaco.erp.beans.phieuxuatkho.ISpDetail;
import geso.traphaco.erp.beans.phieuxuatkho.imp.SpDetail;

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
	
	String nhamayId;
	ResultSet nhamayRs;
	
	String CtyId;
	String khoXuatId;
	ResultSet khoXuatRs;
	
	String khoNhanId;
	ResultSet khoNhanRs;
	
	String lsxIds;
	ResultSet lsxRs;
	
	List<IYeucau> spList;
	List<IYeucau> spChoNhapList;
	
	List<ISpDetail > spConList ;
	ResultSet spDetailRs;
	
	List<IKhu_Vitri> khuList;
	List<IKhu_Vitri> vitriList;
	NumberFormat formatter = new DecimalFormat("#######.###");
	dbutils db ;
	
	public ErpYeucaunguyenlieu()
	{
		this.id = "";
		this.ngayyeucau = getDateTime();
		this.lydoyeucau = "";
		this.khoXuatId = "";
		this.khoNhanId = "";
		this.lsxIds = "";
		this.msg = "";
		this.ischuyenNL = "0";
		this.trangthai = "";
		this.nhamayId = "";
		
		this.spConList  = new ArrayList<ISpDetail>();
		this.spList = new ArrayList<IYeucau>();
		this.spChoNhapList = new ArrayList<IYeucau>();
		

		
		this.khuList = new ArrayList<IKhu_Vitri>();
		this.vitriList = new ArrayList<IKhu_Vitri>();
		
		this.db = new dbutils();
	}
	
	public ErpYeucaunguyenlieu(String id)
	{
		this.id = id;
		this.ngayyeucau = getDateTime();
		this.lydoyeucau = "";
		this.khoXuatId = "";
		this.khoNhanId = "";
		this.lsxIds = "";
		this.msg = "";
		this.ischuyenNL = "0";
		this.trangthai = "";
		this.nhamayId = "";
		
		this.spConList  = new ArrayList<ISpDetail>();
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
						"select '" + ycnlCurrent + "', pk_seq from erp_lenhsanxuat_giay where pk_seq in ( " + this.lsxIds + " ) ";
				
				if(!db.update(query))
				{
					this.msg = "Không thể tạo mới ERP_YEUCAUNGUYENLIEU_LSX " + query;
					db.getConnection().rollback();
					return false;
				}
			}
			//Update lai Lenh san xuat, khi da tao phieu yeu cau.
			query=" update lsx "+
			" set lsx.trangthai=2 "+
			" from ERP_YEUCAUNGUYENLIEU_LSX  yc "+
			" inner join erp_lenhsanxuat_giay lsx on yc.lenhsanxuat_fk=lsx.pk_seq "+
			" where yc.yeucaunguyenlieu_fk='" + ycnlCurrent+ "' ";
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_YEUCAUNGUYENLIEU_LSX " + query;
				db.getConnection().rollback();
				return false;
			}
			if(this.spList.size() > 0)
			{
				for(int i = 0; i < this.spList.size(); i++)
				{
					IYeucau sp = this.spList.get(i);
					query = "insert ERP_YEUCAUNGUYENLIEU_SANPHAM(yeucaunguyenlieu_fk, SANPHAM_FK, soluongyeucau, soluongnhan,ghichu) " +
							"select '" + ycnlCurrent + "', pk_seq, '" + sp.getSoluongYC() + "', '0',N'"+sp.getGhiChu()+"' " +
							"from ERP_SanPham where pk_seq = N'" + sp.getId() + "' ";
					
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
			
			double sodong = 0;
			if(rsCheck != null)
			{
				if(rsCheck.next())
				{
					sodong = rsCheck.getDouble("sodong");
				}
				rsCheck.close();
			}
			
			if(sodong > 0)
			{
				this.msg = "Lệnh sản xuất này đã phát sinh chuyển nguyên liệu. Bạn không thể cập nhật.";
				return false;
			}
			//luu lai so luong
			query = " Update ERP_YEUCAUNGUYENLIEU set ngayyeucau = '" + this.ngayyeucau + "', lydo = N'" + this.lydoyeucau + "', khoxuat_fk = '" + this.khoXuatId + "'," +
					" khonhan_fk = '" + this.khoNhanId + "', ngaysua = '" + getDateTime() + "', nguoisua = '" + this.userId + "' " +
					" where pk_seq = '" + this.id + "' ";
			
 	
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
						"select '" + this.id + "', pk_seq from erp_lenhsanxuat_giay where pk_seq in ( " + this.lsxIds + " ) ";
				if(!db.update(query))
				{
					this.msg = "Không thể tạo mới ERP_YEUCAUNGUYENLIEU_LSX " + query;
					db.getConnection().rollback();
					return false;
				}
			}
			query=" update lsx "+
			" set lsx.trangthai=2 "+
			" from ERP_YEUCAUNGUYENLIEU_LSX  yc "+
			" inner join erp_lenhsanxuat_giay lsx on yc.lenhsanxuat_fk=lsx.pk_seq "+
			" where yc.yeucaunguyenlieu_fk='" + this.id + "' ";
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_YEUCAUNGUYENLIEU_LSX " + query;
				db.getConnection().rollback();
				return false;
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
					query = "insert ERP_YEUCAUNGUYENLIEU_SANPHAM(yeucaunguyenlieu_fk, SANPHAM_FK, soluongyeucau, soluongnhan,ghichu) " +
							"select '" + this.id + "', pk_seq, '" + sp.getSoluongYC() + "', '0',N'"+sp.getGhiChu()+"' " +
							"from ERP_SanPham where pk_seq = N'" + sp.getId() + "' ";
					
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
			
			String err=this.createChuyenNL();
			if(err.length() > 0){
				this.msg = err;
				return false;
			}
			 
		}
		
	 
		try
		{
			
			//KIỂM TRA SẢN PHẨM VỚI KHO NHẬN
			
			String query=" " ;
			
			 
			Utility util=new Utility();
			
			db.getConnection().setAutoCommit(false);
			
			if(this.spList.size() ==0){
				this.msg = "Khong the cap nhat chua xac dinh duoc san pham";
				db.getConnection().rollback();
				return false;
			}
			for(int i = 0; i < this.spList.size(); i++)
			{
				
				IYeucau sp = this.spList.get(i);
				if( !sp.getSoluongNhan().equals("0"))
				{
					List<ISpDetail> detailList = sp.getSpDetailList();
					for(int j = 0; j < detailList.size(); j++)
					{
						ISpDetail detail = detailList.get(j);
						 query =  		" insert ERP_YEUCAUNGUYENLIEU_SP_NHAPKHO(yeucaunguyenlieu_fk, sanpham_fk, khott_fk, solo, soluong, bean) " +
										" select '" + this.id + "', a.pk_seq, '" + this.khoNhanId + "', '" + detail.getSolo() + "', '" + detail.getSoluong() + "',100000 " +
										" from ERP_SanPham a  " +
										" where a.PK_SEQ = '" + sp.getId() + "'";
						// System.out.println("câu insert nè: "+ query);
						if(db.updateReturnInt(query)<1)
						{
							this.msg = "2.Khong the cap nhat ERP_YEUCAUNGUYENLIEU_SP_NHAPKHO: " + query;
							db.getConnection().rollback();
							return false;
						}
						
						query = " select count(*) as sodong from ERP_KHOTT_SP_CHITIET " +
								" where khott_fk = '" + this.khoNhanId + "' and sanpham_fk = ( select pk_seq from ERP_SanPham where PK_SEQ = '" + sp.getId() + "' ) " +
										" and solo = '" + detail.getSolo() + "'";
						
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
						
						System.out.println("flag: "+ flag);
						if(flag) //da ton tau, cap nhat booked, avail
						{
							query = " update ERP_KHOTT_SP_CHITIET set soluong = soluong + '" + detail.getSoluong() + "', AVAILABLE = AVAILABLE + '" + detail.getSoluong() + "' " +
									" where KHOTT_FK = '" + this.khoNhanId + "' and SANPHAM_FK = ( select pk_seq from ERP_SanPham where PK_SEQ = '" + sp.getId()+ "' ) " +
									" and SOLO = '" + detail.getSolo() + "'";
							//System.out.println("câu cập nhật: "+query);
						}
						else
						{
							//lay ngay sanxuat, ngayhethan tu kho chuyen di
							String ngaysanxuat = "";
							String ngayhethan = "";
							query = " select ngaysanxuat, ngayhethan  " +
									" from erp_khott_sp_chitiet where sanpham_fk = ( select pk_seq from ERP_SanPham where PK_SEQ = '" + detail.getId() + "' ) and khott_fk = '" + this.khoXuatId + "' " +
										" and solo = '" + detail.getSolo() + "'";
							
							 
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
							
							query = "insert INTO ERP_KHOTT_SP_CHITIET(KHOTT_FK, SANPHAM_FK, SOLUONG, BOOKED, AVAILABLE, SOLO, BIN, NGAYSANXUAT, NGAYHETHAN, NGAYNHAPKHO) " +
								" VALUES ('" + this.khoNhanId + "','"+detail.getId()+"' , '" + detail.getSoluong() + "', '0', '" + detail.getSoluong() + "', '" + detail.getSolo() + "',100000, '" + ngaysanxuat + "', '" + ngayhethan + "', '" + getDateTime() + "') ";
								 
						}
						
						//System.out.println("1__Tang kho nhan chi tiet: " + query);
						if(db.updateReturnInt(query)!=1)
						{
							this.msg = "3.Khong the cap nhat ERP_KHOTT_SP_CHITIET: " + query;
							db.getConnection().rollback();
							return false;
						}
						
						query=  " SELECT SANPHAM_FK FROM ERP_KHOTT_SANPHAM where KHOTT_FK = '" + this.khoNhanId + "' " +
								" AND SANPHAM_FK = ( select pk_seq from ERP_SanPham where PK_SEQ = '" + sp.getId() + "' ) ";
						
						ResultSet rscheckkho=db.get(query);
						
						
						if(rscheckkho.next()){
							query = "  update ERP_KHOTT_SANPHAM set soluong = soluong + '" + detail.getSoluong() + "', available = available + '" + detail.getSoluong() + "' " +
									"  where KHOTT_FK = '" + this.khoNhanId + "' and SANPHAM_FK = ( select pk_seq from ERP_SanPham where PK_SEQ = '" + sp.getId() + "' ) ";
							
						}else{
							query=  " INSERT INTO ERP_KHOTT_SANPHAM (KHOTT_FK,SANPHAM_FK,SOLUONG,BOOKED,AVAILABLE) VALUES " +
									" ("+this.khoNhanId+","+sp.getId()+","+detail.getSoluong()+",0,"+detail.getSoluong()+") ";
							
						}
						rscheckkho.close();
						
						if(db.updateReturnInt(query)!=1)
						{
							this.msg = "3.Khong the cap nhat ERP_KHOTT_SANPHAM: " + query;
							db.getConnection().rollback();
							return false;
						}
						util.CapNhatLog(this.id,"YCNL_SX",this.khoNhanId, sp.getId(), query,this.db);
						
						/********************BEGIN GIAM KHO BEN KHO XUAT*********************/
						
						// THEM VAO KHO CHI TIET 
						 
						
						query = " update ERP_KHOTT_SP_CHITIET set soluong = soluong - '" + detail.getSoluong() + "', AVAILABLE = AVAILABLE - '" + detail.getSoluong() + "' " +
								" where KHOTT_FK = '" + this.khoXuatId + "' and SANPHAM_FK =  " + sp.getId() + "   " +
								" and SOLO = '" + detail.getSolo() + "'";
						
						if(db.updateReturnInt(query)!=1)
						{
							this.msg = "3.Khong the cap nhat ERP_KHOTT_SP_CHITIET: " + query;
							db.getConnection().rollback();
							return false;
						}
						
						query = " update ERP_KHOTT_SANPHAM set   soluong = soluong - '" + detail.getSoluong() + "',booked=booked- '" + detail.getSoluong() + "'" +
								" where KHOTT_FK = '" + this.khoXuatId + "' and SANPHAM_FK =  " + sp.getId() ;
 
						if(db.updateReturnInt(query)!=1)
						{
							this.msg = "4.Khong the cap nhat ERP_KHOTT_SANPHAM: " + query;
							db.getConnection().rollback();
							return false;
						}
						util.CapNhatLog(this.id,"YCNL_SX",this.khoXuatId, sp.getId(), query,this.db);
						
						query =  "insert ERP_YEUCAUNGUYENLIEU_SP_XUATKHO(yeucaunguyenlieu_fk, sanpham_fk, khott_fk, solo, soluong, bean) " +
						 "select '" + this.id + "', pk_seq, '" + this.khoXuatId + "', '" + detail.getSolo() + "', '" + detail.getSoluong() + "', '100000' " +
						 "from ERP_SanPham where pk_seq = '" + sp.getId() + "' ";
						//System.out.println("3__Giam kho xua chi tiet: " + query);
						if(!db.update(query))
						{
							this.msg = "2.Khong the cap nhat ERP_YEUCAUNGUYENLIEU_SP_XUATKHO: " + query;
							db.getConnection().rollback();
							return false;
						}
		 
						query = " select count(*) as sodong from ERP_YEUCAUNGUYENLIEU_SP_CHITIET " +
								" where yeucaunguyenlieu_fk = '" + this.id + "' and SANPHAM_FK =  '" + detail.getId() + "' " +
								" and SOLO = '" + detail.getSolo() + "'";
						
						rsCheck = db.get(query);
						double sodong = 0;
						if(rsCheck != null)
						{
							if(rsCheck.next())
							{
								sodong = rsCheck.getDouble("sodong");
							}
							rsCheck.close();
						}
				
						if(sodong <= 0)
						{
							 
							query = "insert ERP_YEUCAUNGUYENLIEU_SP_CHITIET(yeucaunguyenlieu_fk, SANPHAM_FK, SOLO, SOLUONG, BEAN) " +
									"select '" + this.id + "', pk_seq, '" + detail.getSolo() + "', '" + detail.getSoluong() + "', '100000' " +
									"from ERP_SanPham where pk_seq = '" + detail.getId() + "' ";
						}
						else
						{
							query = " update ERP_YEUCAUNGUYENLIEU_SP_CHITIET set soluong = soluong + '" + detail.getSoluong() + "' " + 
							 		" where yeucaunguyenlieu_fk = '" + this.id + "' and SANPHAM_FK = ( select pk_seq from ERP_SanPham where pk_seq = '" + detail.getId() + "' ) and SOLO = '" + detail.getSolo() + "' ";
						}
				
						//System.out.println("1133____Insert yeucau NL: " + query);
				
						if(!db.update(query))
						{
							this.msg = "Khong the cap nhat ERP_YEUCAUNGUYENLIEU_SP_CHITIET: " + query;
							db.getConnection().rollback();
							return false;
						}
						
						/********************END GIAM KHO BEN KHO XUAT*********************/
						
						
					}
				}
			}
			
			 
				query = " Update ERP_YEUCAUNGUYENLIEU  set giochot='"+util.getTime()+"',ngayhethong=getdate(), trangthai = '2', nguoisua ='"+this.userId+"' where pk_seq = '" + this.id + "' ";
				//System.out.println("câu cập nhật bảng ERP_YEUCAUNGUYENLIEU: "+query );
				if(!db.update(query))
				{
					this.msg = "4.Khong the cap nhat ERP_YEUCAUNGUYENLIEU: " + query;
					db.getConnection().rollback();
					return false;
				}
				query="update erp_lenhsanxuat_giay set trangthai=3 where trangthai < 3 and  pk_seq=( select lenhsanxuat_fk from ERP_YEUCAUNGUYENLIEU_LSX where yeucaunguyenlieu_fk="+this.id+" )";
				if(!db.update(query))
				{
					this.msg = "4.Khong the cap nhat ERP_YEUCAUNGUYENLIEU: " + query;
					db.getConnection().rollback();
					return false;
				}
				
				// cập nhật trạng thái phiếu yêu cầu sang trạng thái đã nhận nguyên liệu
		    	query=" update erp_phieuyeucau set TrangThai=4 where pk_seq= (select phieuyeucau_fk from ERP_YEUCAUNGUYENLIEU where pk_seq="+this.id+") ";
		    	if(!db.update(query))
				{
					 msg = "Không thể cập nhật bảng erp_phieuyeucau: " + query;
					 db.getConnection().rollback();
					 return false;
				}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
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
	 
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			
			String query=	" SELECT B.KHOXUAT_FK,A.SANPHAM_FK,A.SOLUONGNHAN "+
							" FROM ERP_YEUCAUNGUYENLIEU_SANPHAM A   "+
							" INNER JOIN ERP_YEUCAUNGUYENLIEU B ON A.YEUCAUNGUYENLIEU_FK=B.PK_SEQ "+
							" WHERE B.PK_SEQ="+this.id;
			
			ResultSet rs=db.get(query);
			
			while (rs.next()){
				query= " update erp_khott_sanpham set available=available + "+ rs.getString("SOLUONGNHAN")+"  " +
					   " ,booked=booked -"+ rs.getString("SOLUONGNHAN")+" where sanpham_fk="+rs.getString("SANPHAM_FK")+"  and khott_fk= "+rs.getString("KHOXUAT_FK");
				if(db.updateReturnInt(query) != 1)
				{
					this.msg = "Khong the cap nhat erp_khott_sanpham: " + query;
					db.getConnection().rollback();
					return false;
				}
			}			
			
			query="delete ERP_PXKNL_GHICHU where PXK_FK ="+this.id;
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat ERP_PXKNL_GHICHU: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			
			for(int i = 0; i < this.spList.size(); i++)
			{
				
				IYeucau sp = this.spList.get(i);
				if( Double.parseDouble(sp.getSoluongNhan())  <0)
				{
					this.msg = "Bạn không thể chuyển số lượng sản phẩm nhỏ hơn 0.Vui lòng kiểm tra lại";
					db.getConnection().rollback();
					return false;
				}
						
				  query =  " Update ERP_YEUCAUNGUYENLIEU_SANPHAM set ghichu=N'"+sp.getGhiChu()+"',soluongnhan =   '" + sp.getSoluongNhan() + "' " +
						   " where  yeucaunguyenlieu_fk = '" + this.id + "' and  SANPHAM_FK = ( select pk_seq from ERP_SanPham where pk_seq = '" + sp.getId() + "') ";
					
				if(!db.update(query))
				{
					this.msg = "Khong the cap nhat ERP_YEUCAUNGUYENLIEU_SANPHAM: " + query;
					db.getConnection().rollback();
					return false;
				}
				query=  " select isnull(kho.available,0) as available,sp.ma+' - '+sp.ten as tensanpham from  erp_Sanpham sp  " +
						" inner join erp_khott_Sanpham    kho on sp.pk_Seq= kho.sanpham_fk and khott_fk= "+this.khoXuatId +
						" where sp.pk_seq ="+sp.getId();
				
				ResultSet rscheck=db.get(query);
				double soluongton=0;
				if(rscheck.next()){
					soluongton=rscheck.getDouble("available");
				}
				
				if(soluongton < Double.parseDouble(sp.getSoluongNhan())  )
				{
					this.msg="Sản phẩm "+rscheck.getString("tensanpham")+" không đủ số lượng trong kho ,vui lòng kiểm tra lại,tồn kho còn :"+rscheck.getString("available");
					db.getConnection().rollback();
					return false;
				}
				rscheck.close();
				// cập nhật booked kho này
				
				query="update erp_khott_sanpham set available=available- "+ sp.getSoluongNhan()+" ,booked=booked +"+ sp.getSoluongNhan()+" where sanpham_fk="+sp.getId()+"  and khott_fk= "+this.khoXuatId;
				if(db.updateReturnInt(query) != 1)
				{
					this.msg = "Khong the cap nhat ERP_YEUCAUNGUYENLIEU_SANPHAM: " + query;
					db.getConnection().rollback();
					return false;
				}
				
				
				for( int k = 0; k < sp.getSpDetailList().size(); k++)
				{
					ISpDetail sanphamDetail = sp.getSpDetailList().get(k);
				 
					query = " insert into ERP_PXKNL_GHICHU ( SANPHAM_FK, PXK_FK,SOLUONG,GHICHU,MASO )" +
							" values('"+sp.getId()+"','"+this.id+"','"+sanphamDetail.getSoluong()+"',N'"+sanphamDetail.getGhiChu()+"',N'"+sanphamDetail.getMaso()+"' )";
					
					if(!db.update(query)  )
					{
						this.msg = "Khong the cap nhat ERP_PXKNL_GHICHU: " + query;
						db.getConnection().rollback();
						return false;
					}
				} 
			}
			
			 query = "Update ERP_YEUCAUNGUYENLIEU set trangthai = '1', nguoisua = '" + this.userId + "', ngaysua = '" + getDateTime() + "' where pk_seq = '" + this.id + "'";
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
			e.printStackTrace();
			return false;
		}
		
		return true;
		
	}
	
	
	
	public void createRs() 
	{
		this.nhamayRs = db.get("select pk_seq, manhamay + ', ' + tennhamay as nhamayTen from ERP_NHAMAY where congty_fk = '"+this.CtyId+"'");
		
		//Lay 1 nha may mac dinh
		ResultSet rs = db.get("select pk_seq, manhamay + ', ' + tennhamay from ERP_NHAMAY where congty_fk = '"+this.CtyId+"'");
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
			catch (Exception e) {}
		}
		
		if(this.lsxIds.length() >0){
			String sql=" select nhamay_fk,congty_fk from erp_lenhsanxuat_giay where pk_seq in ("+this.lsxIds+") ";
			 rs=db.get(sql);
			if(rs!=null){
				try 
				{
					
					while(rs.next()){
						this.nhamayId=rs.getString("nhamay_fk");
					}
					
				}catch (Exception e) {
					// TODO: handle exception
				}
			}
			
		}
  
		this.khoXuatRs = db.get("select PK_SEQ, TEN from ERP_KHOTT where TrangThai = '1' and loai = '2' and congty_fk = '"+this.CtyId+"' ");
		this.khoNhanRs = db.get("select PK_SEQ, TEN from ERP_KHOTT where TrangThai = '1' and loai = '1' and congty_fk = '"+this.CtyId+"' and nhamay_fk='"+this.nhamayId+"' ");
		
		if(this.ischuyenNL.equals("0"))
		{
			String query = 	   "  SELECT DISTINCT A.PK_SEQ AS LSXID, A.NGAYBATDAU, A.NGAYDUKIENHT, B.TEN AS SPTEN    " +  
							   "  FROM ERP_LENHSANXUAT_GIAY A  " +  
							   "  INNER JOIN ERP_LENHSANXUAT_SANPHAM  LSXSP ON LSXSP.LENHSANXUAT_FK=A.PK_SEQ   " +  
							   "  INNER JOIN ERP_SANPHAM B ON LSXSP.SANPHAM_FK = B.PK_SEQ    WHERE  A.TRANGTHAI IN (1)   " +  
							   "  AND A.NHAMAY_FK = "+this.nhamayId+" AND A.CONGTY_FK="+this.CtyId+"  AND A.PK_SEQ NOT IN   " +  
							   "  ( SELECT LENHSANXUAT_FK FROM ERP_YEUCAUNGUYENLIEU_LSX     " +  
							   "  WHERE YEUCAUNGUYENLIEU_FK IN (SELECT PK_SEQ FROM ERP_YEUCAUNGUYENLIEU WHERE TRANGTHAI < 2 ) ) ";
			 
			
			if(this.id.trim().length() > 0)
			{
				query += " union ";
				query += " select distinct b.PK_SEQ as lsxId, b.ngaybatdau, b.ngaydukienht,  c.TEN as spTen  "+
				"  from ERP_YEUCAUNGUYENLIEU_LSX a inner join erp_lenhsanxuat_giay b on a.lenhsanxuat_fk = b.PK_SEQ   "+
				"  inner join erp_kichbansanxuat_giay kb on kb.pk_seq=b.kichbansanxuat_fk   inner Join ERP_SanPham c on kb.masanpham = c.ma  "+  
				"  where a.yeucaunguyenlieu_fk ='" + this.id + "' and b.nhamay_fk = '" + this.nhamayId + "' and b.congty_fk='"+this.CtyId+"'   ";
			}
			System.out.println("GET LENH SAN XUAT : "+query);
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
			 
		}
		
	}

	
	private String createChuyenNL() 
	{
		for(int i = 0; i < this.spList.size(); i++)
		{
			IYeucau yeucau = this.spList.get(i);
			
			String soluongNhan = yeucau.getSoluongNhan().trim();
	 
			if(soluongNhan.length() > 0)
			{
				List<ISpDetail> spDetail = new ArrayList<ISpDetail>();
				
				String query =  " select SANPHAM_FK, isnull(AVAILABLE, 0) as AVAILABLE, SOLO, b.pk_seq as vtId, b.MA as vitri, c.diengiai as khu " +
								" from ERP_KHOTT_SP_CHITIET a inner join ERP_VITRIKHO b on a.BIN = b.PK_SEQ " +
								" inner join ERP_KHUVUCKHO c on b.KHU_FK = c.pk_seq " +
								" where  a.AVAILABLE >0  and  a.sanpham_fk = '" + yeucau.getId() + "' and a.KHOTT_FK = '" + this.khoXuatId + "' " +
								" order by a.ngayhethan asc, a.AVAILABLE asc ";
	 
					ResultSet rsSpDetail = db.get(query);
					double tongluong = Double.parseDouble(soluongNhan);
					try
					{
						while(rsSpDetail.next())
						{
							 
								String maspD = yeucau.getMa();
								String soloD = rsSpDetail.getString("SOLO");
								String vitriD = "100000";
								String vitriIdD = "100000";
								String khuD = "100000";
								String idsp=rsSpDetail.getString("SANPHAM_FK");
 
								if(tongluong <  rsSpDetail.getDouble("AVAILABLE"))
								{
							 
									ISpDetail spDetail2 = new SpDetail(idsp,maspD, soloD,formatter.format(tongluong), khuD, vitriD, vitriIdD);
									spDetail.add(spDetail2);
									tongluong=0;
									
								}
								else
								{
									tongluong  =tongluong  - rsSpDetail.getDouble("AVAILABLE");
									ISpDetail spDetail2 = new SpDetail(idsp,maspD, soloD, formatter.format(rsSpDetail.getDouble("AVAILABLE")), khuD, vitriD, vitriIdD);
									spDetail.add(spDetail2);
									
								}
								if(tongluong==0){
									break;
								}
						}
						
						rsSpDetail.close();
					}
					catch (Exception e) 
					{
						e.printStackTrace();
						this.msg="Lỗi trong quá trình lấy lô sản xuất :"+e.getMessage();
						return this.msg;
					}
					 
					 
				if(Double.parseDouble(formatter.format(tongluong)) >0){
					return "Sản phẩm trong lô chi tiết của sản phẩm " +yeucau.getMa()+"--"+yeucau.getTen()+" Không đủ số lượng trong kho chi tiết";
				}
					
				this.spList.get(i).setSpDetailList(spDetail);
				
			}
		}
		return "";
		
		 
	}

	private void createYeuCauNL() 
	{
		String query =  " select c.PK_SEQ as spId, c.MA as spMa, c.TEN +'('+ isnull( c.quycach,'')+')'   as spTen, sum(b.SoLuong  )    as SoLuong  " +
						"  from erp_lenhsanxuat_giay a inner join erp_lenhsanxuat_BOM_giay b on a.PK_SEQ = b.lenhsanxuat_fk  " +
						" inner Join ERP_SanPham c on b.VatTu_fk = c.PK_SEQ   " +
						" where  b.loai='1'   and a.PK_SEQ in ( " + this.lsxIds + " )  " +
						" group by c.PK_SEQ, c.MA,  c.TEN,c.MA, c.quycach";
	
		
		System.out.println("Vào đây nek : "+query);
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
					String soluong = formatter.format(spRs.getDouble("soluong"));
					
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
				e.printStackTrace();
			}
			
			this.spList = spList;
		}
	}

	public void init() 
	{
		String query = "	select ngayyeucau, lydo, khoxuat_fk, khonhan_fk, trangthai from " +
						"   ERP_YEUCAUNGUYENLIEU where pk_seq = '" + this.id + "'";
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
			catch (Exception e) {}
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
			catch (Exception e) {}
		}
		
		
		this.initSanPham();
		
		this.nhamayRs = db.get("select pk_seq, manhamay + ', ' + tennhamay as nhamayTen from ERP_NHAMAY where congty_fk = '"+this.CtyId+"'");
		
		rs = db.get("select pk_seq, manhamay + ', ' + tennhamay from ERP_NHAMAY where congty_fk = '"+this.CtyId+"'");
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
			catch (Exception e) {}
		}
		
		//LAY NHA MAY BEN LENH SAN XUAT
		
		if(this.lsxIds.length() >0){
			String sql=" select nhamay_fk,congty_fk from erp_lenhsanxuat_giay where pk_seq in ("+this.lsxIds+") ";
			 rs=db.get(sql);
			if(rs!=null){
				try 
				{
					
					while(rs.next()){
						this.nhamayId=rs.getString("nhamay_fk");
					}
					
				}catch (Exception e) {
					// TODO: handle exception
				}
			}
			
		}
		
		
		//Kho xuat la kho nguyen lieu & thanh pham sau san xuat
		this.khoXuatRs = db.get("select PK_SEQ, TEN from ERP_KHOTT where TrangThai = '1' and loai = '2' and congty_fk = '"+this.CtyId+"' ");
		this.khoNhanRs = db.get("select PK_SEQ, TEN from ERP_KHOTT where TrangThai = '1' and loai = '1' and congty_fk = '"+this.CtyId+"' and nhamay_fk='"+this.nhamayId+"' ");
		
		
		
		if(this.ischuyenNL.equals("0"))
		{
 
		query  =" SELECT DISTINCT A.PK_SEQ AS LSXID, A.NGAYBATDAU, A.NGAYDUKIENHT, B.TEN AS SPTEN "+ 
				" FROM ERP_LENHSANXUAT_GIAY A "+
				" INNER JOIN ERP_LENHSANXUAT_SANPHAM LSXSP ON LSXSP.LENHSANXUAT_FK=A.PK_SEQ "+ 
				" INNER JOIN ERP_SANPHAM B ON LSXSP.SANPHAM_FK = B.PK_SEQ "+
				" WHERE A.TRANGTHAI IN (1) AND A.NHAMAY_FK = "+this.nhamayId+" AND A.CONGTY_FK="+this.CtyId+"   "+
				" AND A.PK_SEQ NOT IN   ( SELECT LENHSANXUAT_FK FROM ERP_YEUCAUNGUYENLIEU_LSX  "+
				" WHERE YEUCAUNGUYENLIEU_FK IN (SELECT PK_SEQ FROM ERP_YEUCAUNGUYENLIEU WHERE TRANGTHAI < 2) )";
		
			if(this.trangthai.equals("1")){
				query += "  and a.pk_seq in ("+this.lsxIds+") ";
			}
			System.out.println("SQL get Lenh San Xuat  :  "+query);
		if(this.id.trim().length() > 0)
		{
			query += " union ";
			query +=" SELECT DISTINCT B.PK_SEQ AS LSXID, B.NGAYBATDAU, B.NGAYDUKIENHT,  C.TEN AS SPTEN  "+
					" FROM ERP_YEUCAUNGUYENLIEU_LSX A INNER JOIN ERP_LENHSANXUAT_GIAY B ON A.LENHSANXUAT_FK = B.PK_SEQ "+ 
					" INNER JOIN ERP_LENHSANXUAT_SANPHAM LSXSP ON LSXSP.LENHSANXUAT_FK=B.PK_SEQ   "+
					" INNER JOIN ERP_SANPHAM C ON LSXSP.SANPHAM_FK = C.PK_SEQ "+  
					" where a.yeucaunguyenlieu_fk ='" + this.id + "' and b.nhamay_fk = '" + this.nhamayId + "' and b.congty_fk='"+this.CtyId+"'   ";
		}
		if(this.trangthai.equals("1")){
			query += "  and b.pk_seq in ("+this.lsxIds+") ";
		}
		
		System.out.println("1.Khoi tao LSX: " + query);
			this.lsxRs = db.get(query);
			
			
			
			
		}
		
		//this.createRs();
	}

	
	private void initSanPham() 
	{
		String loaiten = "2";
		if(this.ischuyenNL.equals("1"))
			loaiten = "1";
		
		String query =  " select yeucau.ghichu , yeucau.tonhientai ,yeucau.spId, yeucau.spMa, yeucau.spTen, yeucau.donvi, yeucau.SoLuongYC,ISNULL(dachuyen.SoLuongdachuyen,0) AS SoLuongdachuyen , yeucau.soluongNhan, ISNULL(SoLuongNhap, 0) as soLuongNhap  " +
						" from  " +
						" ( " +
						"     select isnull(a.ghichu,'') as ghichu  ,isnull(kho.available,0) as tonhientai , b.PK_SEQ as spId, b.MA as spMa, b.TEN +'('+isnull(b.quycach,'') +')' as spTen, a.soluongyeucau as SoLuongYC, soluongNhan, isnull(dvdl.donvi,'') as donvi   " +
						"     from ERP_YEUCAUNGUYENLIEU_SANPHAM a  " +
						"	  inner join ERP_YEUCAUNGUYENLIEU ycnl on ycnl.pk_seq=a.yeucaunguyenlieu_fk   " +
						"     inner Join ERP_SanPham b on a.sanpham_fk = b.PK_SEQ    " +
						"     left join donvidoluong dvdl on dvdl.pk_seq = b.dvdl_fk  " +
						"     LEFT JOIN ERP_KHOTT_SANPHAM KHO ON KHO.khott_fk=ycnl.khoxuat_fk and kho.sanpham_fk=a.sanpham_fk  " +
						"     where a.yeucaunguyenlieu_fk = '" + this.id + "' " +
						" ) " +
						" yeucau left join " +
						" (  " +
						"     select sanpham_fk, SUM(soluong) as SoLuongNhap   " +
						"     from ERP_YEUCAUNGUYENLIEU_SP_NHAPKHO where yeucaunguyenlieu_fk = '" + this.id + "'  " +
						"     group by sanpham_fk  " +
						" )  " +
						" nhapkho on yeucau.spId = nhapkho.sanpham_fk " +
						" left join " +
						" (  " +
						"     select sanpham_fk, SUM(SOLUONGNHAN) as SoLuongdachuyen " +  
						"     from ERP_YEUCAUNGUYENLIEU_SANPHAM  a " +
						"     inner join ERP_YEUCAUNGUYENLIEU ycnl on ycnl.pk_seq=a.yeucaunguyenlieu_fk " +
						"     where ycnl.PHIEUYEUCAU_FK=(SELECT PHIEUYEUCAU_FK FROM ERP_YEUCAUNGUYENLIEU WHERE PK_SEQ="+this.id+" ) " +
						"     AND ycnl.TrangThai!=3  " ;
						if(!this.trangthai.equals("2")){
							query=query + " and ycnl.pk_seq <> "+this.id;
							
						}
						query=query +  "     group by sanpham_fk  " +
						" )   dachuyen on   yeucau.spId = dachuyen.sanpham_fk ";

		//System.out.println("1.Query khoi tao sp: " + query);
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
					sanpham.setDonVi(rs.getString("donvi"));
					sanpham.setGhiChu(rs.getString("ghichu"));
					sanpham.setTonhientai(rs.getString("tonhientai"));
					sanphamDN = new Yeucau();
					sanphamDN.setId(spId);
					sanphamDN.setMa(spMa);
					sanphamDN.setTen(spTen);
					
					if(this.ischuyenNL.equals("0"))
					{
						sanpham.setSoluongYC(soluongYC);
						sanpham.setSoluongDaNhan(soLuongNhap);
						
						soluongNhan =formatter.format(Double.parseDouble(soluongNhan) - Double.parseDouble(soLuongNhap));
						sanpham.setSoluongNhan(soluongNhan);
						sanpham.setSoluongdachuyen(rs.getString("SoLuongdachuyen"));
						//System.out.println("2.So luong nhan: " + sanpham.getSoluongNhan());
					}
					else
					{
						sanpham.setSoluongYC(soluongYC);
						sanpham.setSoluongDaNhan(soluongNhan);
						sanpham.setSoluongNhan(soluongNhan);
						sanpham.setSoluongdachuyen(rs.getString("SoLuongdachuyen"));
					}
			
					// ----- HẾT DÙNG TỪ NGÀY  04/08/2014 ----//
					
					/*query = "select a.SOLO, a.SOLUONG, c.pk_seq as khuId, c.diengiai as khuTen, b.PK_SEQ as vtId, b.MA as vitri, isnull(d.soluong, 0) as soluongNhap   " +
							"from ERP_YEUCAUNGUYENLIEU_SP_CHITIET a inner join ERP_VITRIKHO b on a.BEAN = b.PK_SEQ   " +
								" inner join ERP_KHUVUCKHO c on b.KHU_FK = c.pk_seq  " +
								" left join " +
								" ( " +
								"	select sanpham_fk, solo, bean, sum(soluong) as soluong " +
								"	from ERP_YEUCAUNGUYENLIEU_SP_NHAPKHO where yeucaunguyenlieu_fk = '" + this.id + "' " +
								"	group by sanpham_fk, solo, bean  " +
								") " +
								"d on a.sanpham_fk = d.sanpham_fk and a.solo = d.solo and a.Bean = d.Bean " +
							"where a.yeucaunguyenlieu_fk = '" + this.id + "' and a.SANPHAM_FK = '" + spId + "'";*/
					
					
					
					//----- THÊM "SỐ LƯỢNG - GHI CHÚ"  CHO MỖI SẢN PHẨM (popup)" -----//
					
					query = "select a.SOLUONG, a.GHICHU,isnull(a.maso,'') as maso from ERP_PXKNL_GHICHU a WHERE a.PXK_FK='"+this.id+"' and a.SANPHAM_FK='"+sanpham.getId()+"'";
					
					 
					ResultSet rsSpDetail = db.get(query);
					
					List<ISpDetail> spConList = new ArrayList<ISpDetail>();
					ISpDetail spCon = null;
					if(rsSpDetail != null)
					{
						while(rsSpDetail.next())
						{
							
							// --- HÊT DÙNG TỪ NGÀY 04/08/2014 ---//
							
						/*	String idhangmua = spId;
							String solo = rsSpDetail.getString("solo");
							String slg =formatter.format(rsSpDetail.getDouble("soluong") - rsSpDetail.getDouble("soluongNhap"));
							if(this.ischuyenNL.equals("1"))
								slg = "0";
							
							String khu = rsSpDetail.getString("khuTen");
							String vitri = rsSpDetail.getString("vitri");
							String vitriId = rsSpDetail.getString("vtId");
							
							spCon = new SpDetail(idhangmua, solo, slg, khu, vitri, vitriId);
							spConList.add(spCon);*/
							
						
							String soluong = rsSpDetail.getString("SOLUONG");
							String ghichu =  rsSpDetail.getString("GHICHU");
							spCon = new SpDetail(soluong, ghichu);
							spCon.setMaso(rsSpDetail.getString("maso"));
							spConList.add(spCon);
						}
						rsSpDetail.close();
					}
					
					sanpham.setSpDetailList(spConList);	
					
					// -------- hết pop - up ------//
					
					spList.add(sanpham);
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


	public void initYeucauNLPdf() 
	{
		String query =  "select a.SANPHAM_FK, b.MA as ma, b.Ten+ ' - '+ isnull(b.quycach,'') as Ten, a.SOLUONGYEUCAU as SoLuong " +
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
							
							double conlai = Double.parseDouble(soluong) - rsSpDetail.getDouble("tongNhap");
							String slg = soluong + " - " + rsSpDetail.getString("tongNhap") + " - " + formatter.format(conlai);
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
		String query =  "select a.SANPHAM_FK, b.MA, b.Ten as Ten, a.SOLUONGYEUCAU as SoLuong " +
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
					
					double conlai = Double.parseDouble(soluong) - rsSpDetail.getDouble("tongNhap");
					String slg = soluong + " - " + rsSpDetail.getString("tongNhap") + " - " + formatter.format(conlai);
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
	public String getCtyId() {
		// TODO Auto-generated method stub
		return this.CtyId;
	}

	@Override
	public void SetCtyId( String ctyid ) {
		// TODO Auto-generated method stub
		this.CtyId=ctyid;
	}

	@Override
	public boolean createYeuCauNL_Lsx() {
		// TODO Auto-generated method stub
		try{
			
			db.getConnection().setAutoCommit(false);
			
			String query= "  select PK_SEQ  from ERP_KHOTT where TrangThai = '1' " +
						"  and loai = '1' and congty_fk = '"+this.CtyId+"' and nhamay_fk=(SELECT NHAMAY_FK FROM  ERP_LENHSANXUAT_GIAY WHERE PK_SEQ="+this.lsxIds+" )";
			
			ResultSet rs = db.get(query);
			 	 
			if(rs.next())
			{
				this.khoNhanId = rs.getString("pk_seq");
			}
			rs.close();
			
			
			
			
		 	query=" SELECT distinct B.KHOTT_FK  "+
				" FROM ERP_LENHSANXUAT_GIAY A  "+
				" INNER JOIN ERP_LENHSANXUAT_BOM_GIAY B ON A.PK_SEQ = B.LENHSANXUAT_FK "+   
				" WHERE  B.LOAI='1'   AND A.PK_SEQ IN ( "+this.lsxIds+" )   ";
			rs=db.get(query);
			// đối với lệnh sản xuất có nguyên liệu lấy từ nhiều kho thì sẽ tạo bằng đó các yêu cầu
			while(rs.next()){
				this.khoXuatId=rs.getString("KHOTT_FK");
				
				
				  query = "insert ERP_YEUCAUNGUYENLIEU(ngayyeucau, lydo, trangthai, khoxuat_fk, khonhan_fk, ngaytao, nguoitao, ngaysua, nguoisua) " +
				"values('" + this.ngayyeucau + "', N'Yêu cầu tự động cho lệnh sản xuất"+this.lsxIds+"', '0', '" + this.khoXuatId + "', '" + this.khoNhanId + "', '" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "')";
	
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
 
				query = "Insert ERP_YEUCAUNGUYENLIEU_LSX ( yeucaunguyenlieu_fk, lenhsanxuat_fk ) " +
						"select '" + ycnlCurrent + "', pk_seq from erp_lenhsanxuat_giay where pk_seq in ( " + this.lsxIds + " ) ";
				
				if(!db.update(query))
				{
					this.msg = "Không thể tạo mới ERP_YEUCAUNGUYENLIEU_LSX " + query;
					db.getConnection().rollback();
					return false;
				}
			 
				//Update lai Lenh san xuat, khi da tao phieu yeu cau.
				query=" update lsx "+
				" set lsx.trangthai=2 "+
				" from ERP_YEUCAUNGUYENLIEU_LSX  yc "+
				" inner join erp_lenhsanxuat_giay lsx on yc.lenhsanxuat_fk=lsx.pk_seq "+
				" where yc.yeucaunguyenlieu_fk='" + ycnlCurrent+ "' ";
				if(!db.update(query))
				{
					this.msg = "Không thể tạo mới ERP_YEUCAUNGUYENLIEU_LSX " + query;
					db.getConnection().rollback();
					return false;
				}
 
		 
				  query =       " select c.PK_SEQ as spId, c.MA as spMa, c.TEN +'('+ isnull( c.quycach,'')+')'   as spTen, sum(b.SoLuong  )    as SoLuong  " +
								"  from erp_lenhsanxuat_giay a inner join erp_lenhsanxuat_BOM_giay b on a.PK_SEQ = b.lenhsanxuat_fk  " +
								" inner Join ERP_SanPham c on b.VatTu_fk = c.PK_SEQ   " +
								" where  b.loai='1'   and a.PK_SEQ in ( " + this.lsxIds + " )  and khott_fk= " +this.khoXuatId + 
								" group by c.PK_SEQ, c.MA,  c.TEN,c.MA, c.quycach ";

					 ResultSet spRs = db.get(query);
 
						while(spRs.next())
						{
 
							String spId = spRs.getString("spId");
							String soluong = formatter.format(spRs.getDouble("soluong"));
							
							// tăng booked  kho sản xuất,khi nào tiêu hao thì cập nhật lại booked (giảm booked giảm số lượng),khi hoàn tất lệnh sản xuất mà booked vẫn còn thì xóa hết booked đi trong trường hợp tiêu hao ít hơn booked.
							//nếu tiêu hao nhiều hơn booked,(nguyên liệu chuyển nhiều),thì giảm booked tới mức booked theo BOM,còn phần số lượng tiêu thụ dư ra thì trừ thẳng vào avai và số lượng.
						  
							query = "insert ERP_YEUCAUNGUYENLIEU_SANPHAM(yeucaunguyenlieu_fk, SANPHAM_FK, soluongyeucau, soluongnhan) " +
							" values( '" + ycnlCurrent + "', "+spId+", '" +soluong + "', '0' )";
							 
							
							if(!db.update(query))
							{
								this.msg = "Khong the tao moi ERP_YEUCAUNGUYENLIEU_SANPHAM: " + query;
								db.getConnection().rollback();
								return false;
							}
						}
						spRs.close();
						this.id=ycnlCurrent;
		  
			}
			rs.close();
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
			return true;
		}catch(Exception er){
			db.update("rollback");
			er.printStackTrace();
			this.msg=er.getMessage();
			return false;
		}
	}

	@Override
	public boolean CreateChuyenKho(String Idyeucau) {
		// TODO Auto-generated method stub
		try{
			db.getConnection().setAutoCommit(false);
			
			String query =  " insert ERP_YEUCAUNGUYENLIEU (ngayyeucau, lydo, trangthai, khoxuat_fk, khonhan_fk, ngaytao, nguoitao, ngaysua, nguoisua,PHIEUYEUCAU_FK) " +
							" select ngayyeucau, lydo,0, khoxuat_fk, khonhan_fk, '"+this.getDateTime()+"', "+this.userId+", '"+this.getDateTime()+"', "+this.userId+",PK_SEQ from erp_phieuyeucau where pk_seq="+Idyeucau;
			

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
				query = "Insert ERP_YEUCAUNGUYENLIEU_LSX ( yeucaunguyenlieu_fk, lenhsanxuat_fk ) " +
					"select '" + ycnlCurrent + "', lenhsanxuat_fk from ERP_PHIEUYEUCAU_LSX where phieuyeucau_fk="+Idyeucau;
	
				if(!db.update(query))
				{
					this.msg = "Không thể tạo mới ERP_YEUCAUNGUYENLIEU_LSX " + query;
					db.getConnection().rollback();
					return false;
				}
				query = "insert ERP_YEUCAUNGUYENLIEU_SANPHAM(yeucaunguyenlieu_fk, SANPHAM_FK, soluongyeucau, soluongnhan,ghichu) " +
				"  select "+ycnlCurrent+", SANPHAM_FK, soluongyeucau, soluongnhan,ghichu from ERP_PHIEUYEUCAU_SANPHAM WHERE  phieuyeucau_fk="+Idyeucau;
				if(!db.update(query))
				{
					this.msg = "Khong the tao moi ERP_YEUCAUNGUYENLIEU_SANPHAM: " + query;
					db.getConnection().rollback();
					return false;
				}
				this.id=ycnlCurrent;
				
				// phát sinh ra phiếu yêu cầu 
				query="Update erp_phieuyeucau set trangthai=2 where pk_seq="+Idyeucau;
				if(!db.update(query))
				{
					this.msg = "Khong the tao moi ERP_YEUCAUNGUYENLIEU_SANPHAM: " + query;
					db.getConnection().rollback();
					return false;
				}
				
			    db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
				return true;
		}catch(Exception er){
				db.update("rollback");
				er.printStackTrace();
				return false;
		}
	}

	
	public List<ISpDetail> getSpConList() {
		
		return this.spConList;
	}


	public void setSpConList(List<ISpDetail> spConList) {
		this.spConList = spConList;
	}


	public ResultSet getspDetailRs() {

		return this.spDetailRs;
	}

	@Override
	public void setspDetailRs(ResultSet spDetailRs) {
		this.spDetailRs = spDetailRs;
		
	}






}
