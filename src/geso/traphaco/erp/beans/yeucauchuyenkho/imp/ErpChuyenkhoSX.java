package geso.traphaco.erp.beans.yeucauchuyenkho.imp;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.center.util.Utility_Kho;
import geso.traphaco.erp.beans.nhapkho.IKhu_Vitri;
import geso.traphaco.erp.beans.phieuxuatkho.ISpDetail;
import geso.traphaco.erp.beans.phieuxuatkho.imp.SpDetail;
import geso.traphaco.erp.beans.yeucauchuyenkho.IErpChuyenkhoSX;
import geso.traphaco.erp.beans.yeucauchuyenkho.ILenhsanxuat;
import geso.traphaco.erp.beans.yeucauchuyenkho.IYeucau;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
 
public class ErpChuyenkhoSX implements IErpChuyenkhoSX
{
	String userId;
	String id;
	String ctyId;
	String ngayyeucau;
	String lydoyeucau;
	String ghichu;
	String Nguoinhan;
	String msg;
	String isnhanHang;
	String trangthai;
	String IsChuyenHangSX;
	String khoXuatId, khoXuatTen;
	String kyhieu;
	String sochungtu;
	String lenhdieudong;
	String ngaydieudong;
	String nguoidieudong;
	String veviec;
	String nguoivanchuyen;
	String phuongtien;
	String hopdong;
	String CdsxId;
	ResultSet CdsxRs;
	ResultSet khoXuatRs;
	ResultSet tsddRs;
	
	String tsddId;
	String IsChuyenhangkhongdat;
	boolean ChoPhepchuyenhangdat;
	
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
	List<IYeucau> spChoNhapList;
	
	List<ILenhsanxuat> lsxList;
	String lenhsxId;
	
	List<IKhu_Vitri> khuList;
	List<IKhu_Vitri> vitriList;
	
	String ndxId;
	ResultSet ndxRs;
	
	String trangthaisp;  // -1 khong dat, 1 dat
	dbutils db;
	Utility_Kho util_kho = new Utility_Kho();
	
	String codoituong;
	String loaidoituong;
	String doituongId;
	ResultSet doituongRs;
	
	String cokhonhan;
	String codoituongNhan;
	String loaidoituongNhan;
	String doituongNhanId;
	ResultSet doituongNhanRs;
	
	String coChiphi;
	String chiphiId;
	ResultSet chiphiRs;
	
	String muahang_fk;
	ResultSet muahangList;
	
	public ErpChuyenkhoSX()
	{
		IsChuyenhangkhongdat="0";
		
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
		this.Nguoinhan="";
		this.IsChuyenHangSX="";
		this.isnhanHang = "0";
		this.trangthai = "0";
		this.trangthaisp = "";
		this.kyhieu="";
		this.sochungtu="";
		this.lenhdieudong="";
		this.ngaydieudong="";
		this.nguoidieudong="";
		this.veviec="";
		this.nguoivanchuyen="";
		this.phuongtien="";
		this.hopdong="";
		this.CdsxId="";

		this.spList = new ArrayList<IYeucau>();
		this.spChoNhapList = new ArrayList<IYeucau>();
		
		this.khuList = new ArrayList<IKhu_Vitri>();
		this.vitriList = new ArrayList<IKhu_Vitri>();
		
		this.lsxList = new ArrayList<ILenhsanxuat>();
		this.lenhsxId = "";
		
		this.nccXuatId = "";
		this.nccNhanId = "";
		this.ndxId = "";
		this.tongSLYC ="0";
		this.IsChuyenhangkhongdat="";
		this.tsddId = "";
		
		this.loaidoituong = "";
		this.doituongId = "";
		this.codoituong = "";
		
		this.cokhonhan = "";
		this.loaidoituongNhan = "";
		this.doituongNhanId = "";
		this.codoituongNhan = "";
		
		this.coChiphi = "";
		this.chiphiId = "";
		this.muahang_fk = "";
		this.db = new dbutils();
	}
	
	public ErpChuyenkhoSX(String id)
	{
		this.id = id;
		this.ngayyeucau = getDateTime();
		this.lydoyeucau = "";
		this.ghichu = "";
		this.khoXuatId = "";
		this.khoNhanId = "";
		this.IsChuyenHangSX="";
		this.lsxIds = "";
		this.msg = "";
		this.task="";
		this.isnhanHang = "0";
		this.IsChuyenhangkhongdat="";
		this.trangthai = "0";
		this.trangthaisp = "";
		this.Nguoinhan="";
		this.spList = new ArrayList<IYeucau>();
		this.spChoNhapList = new ArrayList<IYeucau>();
		this.CdsxId="";
		this.khuList = new ArrayList<IKhu_Vitri>();
		this.vitriList = new ArrayList<IKhu_Vitri>();
		
		this.lsxList = new ArrayList<ILenhsanxuat>();
		this.lenhsxId = "";
		this.kyhieu="";
		this.sochungtu="";
		this.lenhdieudong="";
		this.ngaydieudong="";
		this.nguoidieudong="";
		this.veviec="";
		this.nguoivanchuyen="";
		this.phuongtien="";
		this.hopdong="";
		
		this.ndxId = "";
		this.nccXuatId = "";
		this.nccNhanId = "";
		this.tongSLYC ="0";
		this.tsddId = "";
		
		this.cokhonhan = "";
		this.loaidoituong = "";
		this.doituongId = "";
		this.codoituong = "";
		
		this.cokhonhan = "";
		this.loaidoituongNhan = "";
		this.doituongNhanId = "";
		this.codoituongNhan = "";
		
		this.coChiphi = "";
		this.chiphiId = "";
		this.muahang_fk="";
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

	public String getTsddId() 
	{
		return this.tsddId;
	}

	public void setTsddId(String tsddId) 
	{
		this.tsddId = tsddId;
	}
	
	
	public String getMuahang_fk() {
		return muahang_fk;
	}

	public void setMuahang_fk(String muahang_fk) {
		this.muahang_fk = muahang_fk;
	}

	public ResultSet getMuahangList() {
		return muahangList;
	}

	public void setMuahangList(ResultSet muahangList) {
		this.muahangList = muahangList;
	}

	public boolean createCK() 
	{
		if(this.ngayyeucau.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập ngày yêu cầu";
			return false;
		}
		
		if(this.ndxId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn nội dung xuất";
			return false;
		}
		
		if(this.khoXuatId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn kho xuất";
			return false;
		}
		
		if( this.cokhonhan.equals("1") && this.khoNhanId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn kho nhận";
			return false;
		}
		
		if( this.codoituong.equals("1") && this.doituongId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn đối tượng xuất";
			return false;
		}
		
		if( this.codoituongNhan.equals("1") && this.doituongNhanId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn đối tượng nhận";
			return false;
		}
			
		/*if(this.ndxId.equals("100025"))
		{
			if(this.sochungtu.trim().length()>0)
			{
				if(this.checkSoHoaDon())
				{
					this.msg = "Số hóa đơn : "+this.sochungtu+", ký hiệu : "+this.kyhieu+" đã có, vui lòng chọn lại.";
					System.out.println(this.msg);
					return false;
				}
			}
		}*/
		
		
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
		
		if( this.getMaNDX( this.ndxId ).equals("XK09")  || this.getMaNDX( this.ndxId ).equals("XK12"))
		{
			 if( this.muahang_fk.trim().length() <=0){
				 //this.muahang_fk="NULL";
				 this.msg = "Bạn chưa chọn đơn hàng gia công. Vui lòng kiểm tra lại.";
				 return false;
			 }
		}
		
		if( this.getMaNDX( this.ndxId ).equals("XK10") )
		{
			if(this.lsxIds.trim().length() <= 0)
			{
				 this.msg = "Bạn chưa chọn lệnh SX. Vui lòng kiểm tra lại.";
				 return false;
			 }
		
			
			
		}
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String khonhan_fk = this.khoNhanId.trim().length() <= 0 ? "null" : this.khoNhanId.trim();
			
			String _loaidoituongId = this.loaidoituong.trim().length() <= 0 ? "null" : this.loaidoituong.trim();
			String _doituongId = this.doituongId.trim().length() <= 0 ? "null" : this.doituongId.trim();
			
			String _loaidoituongNhanId = this.loaidoituongNhan.trim().length() <= 0 ? "null" : this.loaidoituongNhan.trim();
			String _doituongNhanId = this.doituongNhanId.trim().length() <= 0 ? "null" : this.doituongNhanId.trim();
			
			String _chiphiId = this.chiphiId.trim().length() <= 0 ? "null" : this.chiphiId.trim();
			String _lsxId = this.lsxIds.trim().length() <= 0 ? "null" : this.lsxIds.trim();
			
			String _cdsxId = this.CdsxId.trim().length() <= 0 ? "null" : this.CdsxId.trim();
			
			String _muahangId = this.muahang_fk.trim().length() <= 0 ? "null" : this.muahang_fk.trim();
			
			
			
			String query = 	" insert ERP_YEUCAUCHUYENKHO(IsChuyenHangSX, LENHSANXUAT_FK,CONGDOAN_FK, NGUOINHAN, noidungxuat_fk, NGAYYEUCAU, lydo, ghichu, trangthai, khoxuat_fk, khonhan_fk,  ngaytao, nguoitao, ngaysua, nguoisua, loaidoituong, DOITUONG_FK, loaidoituongNHAN, DOITUONGNHAN_FK, KYHIEU, SOCHUNGTU, LENHDIEUDONG, NGAYDIEUDONG, NGUOIDIEUDONG, VEVIEC, NGUOIVANCHUYEN, PHUONGTIEN, HOPDONG, chiphi_fk, muahang_fk) " +
				   			" values(" + this.IsChuyenHangSX + ", " + _lsxId + ","+_cdsxId+", N'" + this.Nguoinhan + "', '" + this.ndxId + "', '" + this.ngayyeucau + "',   N'" + this.lydoyeucau + "', N'" + this.ghichu + "', '0', '" + this.khoXuatId + "', " + khonhan_fk + ",  '" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "', " + _loaidoituongId + ", " + _doituongId + ", " + _loaidoituongNhanId + ", " + _doituongNhanId + ", "+
				   			"		'" + this.kyhieu + "', '" + this.sochungtu + "', N'" + this.lenhdieudong + "', '" + this.ngaydieudong + "', N'" + this.nguoidieudong + "', N'" + this.veviec + "', N'" + this.nguoivanchuyen + "', N'" + this.phuongtien + "', N'" + this.hopdong + "', " + _chiphiId +", " + _muahangId + " ) ";

			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_YEUCAUCHUYENKHO " + query;
				db.getConnection().rollback();
				return false;
			}
			
			String ycnlCurrent = "";
			query = "select IDENT_CURRENT('ERP_YEUCAUCHUYENKHO') as ckId";
			
			ResultSet rsPxk = db.get(query);						
			if(rsPxk.next())
			{
				ycnlCurrent = rsPxk.getString("ckId");
				 
			}
			rsPxk.close();
			if(this.spList.size() > 0)
			{
				for(int i = 0; i < this.spList.size(); i++)
				{
					IYeucau sp = this.spList.get(i);
					
					if(sp.getSoluongYC().trim().length() > 0 && Double.parseDouble(sp.getSoluongYC())>0)
					{
						query = " insert ERP_YEUCAUCHUYENKHO_SANPHAM(yeucauchuyenkho_fk, SANPHAM_FK,  soluongyeucau, ghichu_chuyenkho ,lenhsanxuat_fk  ) " +
								" values( '" + ycnlCurrent + "', '" + sp.getId() + "',    " + sp.getSoluongYC() + ", N'"+sp.getghichu_CK()+"' , "+ (sp.getLsxId().trim().length() > 0 ? sp.getLsxId() : null) +"   ) ";
						if(!db.update(query))
						{
							this.msg = "Không thể tạo mới ERP_YEUCAUCHUYENKHO_SANPHAM: " + query;
							db.getConnection().rollback();
							return false;
						}
				 
					}
				}
			}
			
			//Cập nhật tooltip
			db.execProceduce2("CapNhatTooltip_YCCK", new String[] { ycnlCurrent } );
			
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
			this.msg = "Vui lòng nhập ngày yêu cầu";
			return false;
		}

		if(this.ndxId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn nội dung xuất";
			return false;
		}

		if(this.khoXuatId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn kho xuất";
			return false;
		}

		if(this.ngayyeucau.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập ngày yêu cầu";
			return false;
		}
		
		if(this.ndxId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn nội dung xuất";
			return false;
		}
		
		if(this.khoXuatId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn kho xuất";
			return false;
		}
		
		if( this.cokhonhan.equals("1") && this.khoNhanId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn kho nhận";
			return false;
		}
		
		if( this.codoituong.equals("1") && this.doituongId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn đối tượng xuất";
			return false;
		}
		
		if( this.codoituongNhan.equals("1") && this.doituongNhanId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn đối tượng nhận";
			return false;
		}
			
		/*if(this.ndxId.equals("100025"))
		{
			if(this.sochungtu.trim().length()>0)
			{
				if(this.checkSoHoaDon())
				{
					this.msg = "Số hóa đơn : "+this.sochungtu+", ký hiệu : "+this.kyhieu+" đã có, vui lòng chọn lại.";
					System.out.println(this.msg);
					return false;
				}
			}
		}*/
		if( this.getMaNDX( this.ndxId ).equals("XK09")  || this.getMaNDX( this.ndxId ).equals("XK12"))
		{
			 if( this.muahang_fk.trim().length() <=0){
				 //this.muahang_fk="NULL";
				 this.msg = "Bạn chưa chọn đơn hàng gia công. Vui lòng kiểm tra lại.";
				 return false;
			 }
		}
		
		if( this.getMaNDX( this.ndxId ).equals("XK10") )
		{
			if(this.lsxIds.trim().length() <= 0)
			{
				 this.msg = "Bạn chưa chọn lệnh SX. Vui lòng kiểm tra lại.";
				 return false;
			 }
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

			String khonhan_fk = this.khoNhanId.trim().length() <= 0 ? "null" : this.khoNhanId.trim();
			String _loaidoituongId = this.loaidoituong.trim().length() <= 0 ? "null" : this.loaidoituong.trim();
			String _doituongId = this.doituongId.trim().length() <= 0 ? "null" : this.doituongId.trim();
			
			String _loaidoituongNhanId = this.loaidoituongNhan.trim().length() <= 0 ? "null" : this.loaidoituongNhan.trim();
			String _doituongNhanId = this.doituongNhanId.trim().length() <= 0 ? "null" : this.doituongNhanId.trim();

			String _chiphiId = this.chiphiId.trim().length() <= 0 ? "null" : this.chiphiId.trim();
			String _lsxId = this.lsxIds.trim().length() <= 0 ? "null" : this.lsxIds.trim();
			String _muahangId = this.muahang_fk.trim().length() <= 0 ? "null" : this.muahang_fk.trim();
			String _cdsxId = this.CdsxId.trim().length() <= 0 ? "null" : this.CdsxId.trim();
			String query =  " update  ERP_YEUCAUCHUYENKHO set congdoan_fk ="+_cdsxId+", IsChuyenHangSX='" + this.IsChuyenHangSX + "', LENHSANXUAT_FK = " + _lsxId + ", NGUOINHAN=N'" + this.Nguoinhan + "', noidungxuat_fk = '" + this.ndxId + "', NGAYYEUCAU = '" + this.ngayyeucau + "', lydo = N'" + this.lydoyeucau + "' " +
							" ,ghichu =N'"+this.ghichu+"', khoxuat_fk="+this.khoXuatId+", khonhan_fk=" + khonhan_fk + ",ngaysua='"+this.getDateTime()+"', nguoisua="+this.userId+"," +
							"  loaidoituong = " + _loaidoituongId + ", DOITUONG_FK = " + _doituongId + ", loaidoituongNHAN = " + _loaidoituongNhanId + ", DOITUONGNHAN_FK = " + _doituongNhanId + ", "+
							" kyhieu='"+this.kyhieu+"', sochungtu='"+this.sochungtu+"',lenhdieudong=N'"+this.lenhdieudong+"',ngaydieudong='"+this.ngaydieudong+"',nguoidieudong=N'"+this.nguoidieudong+"',veviec=N'"+this.veviec+"',"+
							" nguoivanchuyen=N'"+this.nguoivanchuyen+"',phuongtien=N'"+this.phuongtien+"',hopdong=N'"+this.hopdong+"', chiphi_fk = "+ _chiphiId +",muahang_fk = " + _muahangId + " "+ 
							" where pk_seq="+this.id;
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_YEUCAUCHUYENKHO " + query;
				db.getConnection().rollback();
				return false;
			}
			query = " DELETE ERP_YEUCAUCHUYENKHO_SANPHAM WHERE YEUCAUCHUYENKHO_FK = "+this.id;
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_YEUCAUCHUYENKHO " + query;
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
						query = "insert ERP_YEUCAUCHUYENKHO_SANPHAM(yeucauchuyenkho_fk, SANPHAM_FK,  soluongyeucau, ghichu_chuyenkho ,lenhsanxuat_fk  ) " +
								"values( '" +this.id+ "', '" + sp.getId() + "',    " + sp.getSoluongYC() + ", N'"+sp.getghichu_CK()+"' , "+ (sp.getLsxId().trim().length() > 0 ? sp.getLsxId() : null) +"   ) ";
						if(!db.update(query))
						{
							this.msg = "Không thể tạo mới ERP_YEUCAUCHUYENKHO_SANPHAM: " + query;
							db.getConnection().rollback();
							return false;
						}

					}
				}
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
			//Cập nhật tooltip
			db.execProceduce2("CapNhatTooltip_YCCK", new String[] { this.id } );
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
	
	public boolean updateSoHoaDon() 
	{ 
		try
		{
			db.getConnection().setAutoCommit(false);

			if(this.ndxId.equals("100025"))
			{
				if(this.sochungtu.trim().length()>0){
					if(this.checkSoHoaDon())
					{
						this.msg = "Số hóa đơn : "+this.sochungtu+" đã có, vui lòng chọn lại.";
						System.out.println(this.msg);
						return false;
					}
				}
			}

			String query =  " update  ERP_YEUCAUCHUYENKHO set   NGUOINHAN=N'"+this.Nguoinhan+"'   " +
					" ,ghichu =N'"+this.ghichu+"', "+
					" ngaysua='"+this.getDateTime()+"', nguoisua="+this.userId+"," +
					" kyhieu='"+this.kyhieu+"', sochungtu='"+this.sochungtu+"',lenhdieudong=N'"+this.lenhdieudong+"',ngaydieudong='"+this.ngaydieudong+"',nguoidieudong=N'"+this.nguoidieudong+"',veviec=N'"+this.veviec+"',"+
					" nguoivanchuyen=N'"+this.nguoivanchuyen+"',phuongtien=N'"+this.phuongtien+"',hopdong=N'"+this.hopdong+"'"+
					" where pk_seq="+this.id;

			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_YEUCAUCHUYENKHO " + query;
				db.getConnection().rollback();
				return false;
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
			
			String query = "Update ERP_CHUYENKHO set ngaynhan = '" + this.ngayyeucau + "', ngaysua = '" + getDateTime() + "', nguoisua = '" + this.userId + "', trangthai = '2' " +
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
			
			/*query = "delete ERP_CHUYENKHO_SP_NHANHANG where chuyenkho_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_CHUYENKHO_SP_NHANHANG " + query;
				db.getConnection().rollback();
				return false;
			}*/
			
			if(this.spList.size() > 0)
			{
				for(int i = 0; i < this.spList.size(); i++)
				{
					IYeucau sp = this.spList.get(i);
					
					//Update tong nhap
					if(sp.getSoluongNhan().trim().length() > 0)
					{
						query = "update ERP_CHUYENKHO_SANPHAM set SOLUONGNHAN = SOLUONGNHAN + '" + sp.getSoluongNhan() + "' " +
								"where SANPHAM_FK = '" + sp.getId() + "' and SOLO = '" + sp.getSolo() + "' and CHUYENKHO_FK = '" + this.id + "'";
						
						if(!db.update(query))
						{
							this.msg = "Không thể cập nhật ERP_CHUYENKHO_SANPHAM " + query;
							db.getConnection().rollback();
							return false;
						}
					
						/*List<ISpDetail> spDetail = sp.getSpDetailList();
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
						}*/
						
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
	
	public boolean xuatHang() 
	{
		if(this.ngayyeucau.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập ngày xuất hàng";
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
						this.msg = "Số lượng xuất không được phép vượt quá số lượng yêu cầu.";
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
			
			String query = "Update ERP_CHUYENKHO set ngaynhan = '" + this.ngayyeucau + "', ngaysua = '" + getDateTime() + "', nguoisua = '" + this.userId + "', trangthai = '2' " +
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
			
			/*query = "delete ERP_CHUYENKHO_SP_XUATHANG where chuyenkho_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_CHUYENKHO_SP_XUATHANG " + query;
				db.getConnection().rollback();
				return false;
			}*/
			
			if(this.spList.size() > 0)
			{
				for(int i = 0; i < this.spList.size(); i++)
				{
					IYeucau sp = this.spList.get(i);
					
					//Update tong nhap
					if(sp.getSoluongNhan().trim().length() > 0)
					{
						query = "update ERP_CHUYENKHO_SANPHAM set SOLUONGNHAN = SOLUONGNHAN + '" + sp.getSoluongNhan() + "' " +
								"where SANPHAM_FK = '" + sp.getId() + "' and SOLO = '" + sp.getSolo() + "' and CHUYENKHO_FK = '" + this.id + "'";
						
						if(!db.update(query))
						{
							this.msg = "Không thể cập nhật ERP_CHUYENKHO_SANPHAM " + query;
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
			e.printStackTrace();
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
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
	public void createRs() 
	{
		Utility util = new Utility();
		
		String query = "select pk_seq, MA + ' - ' + TEN as TEN from ERP_NOIDUNGNHAP  " +
					   "where trangthai = '1' and MA in ( 'XK03', 'XK04', 'XK07', 'XK08', 'XK09', 'XK10', 'XK12', 'XK17','XK19' ) " ;
		this.ndxRs = db.get(query);

		/*pk_seq	MA	TEN	LOAIKHO_XUAT
		100059	XK03	Xuất tiêu dùng nội bộ (Không xuất hóa đơn)	1,2,4
		100060	XK04	Xuất hủy	3
		100063	XK07	Xuất chuyển đến kho ký gửi	1,2,4,5
		100064	XK08	Xuất chuyển kho nội bộ	1,2,3,4,5,6,99
		100065	XK10	Xuất chuyển cho nhà gia công	1,2,4,5
		100066	XK12	Xuất chuyển sản xuất	2,3,4,10
		100068	XK14	Xuất đổi cho KH/NCC	1,2,4,5,8
		100073	XK17	Xuất chuyển từ HO-> CN	1*/
		
		/*  CAC LOAI KHO
		1	Kho hàng bán
		2	Kho nguyên vật liệu
		3	Kho hàng hỏng(kho biệt trữ)
		4	Kho vật tư
		5	Kho dữ trữ khách hàng
		6	Kho trình duyệt viên
		7	Kho  nhà gia công
		8	Kho hàng ký gửi tại KH
		9	Kho khách hàng ký gửi tại công ty
		11	Kho CCDC, Phụ tùng thay thế*/

		
		if( this.ndxId.trim().length() > 0 )
		{
			query = "select LOAIKHO_XUAT, coChiphi from ERP_NOIDUNGNHAP where pk_seq = '" + this.ndxId + "'";
			ResultSet rs = db.get(query);
			
			String LOAIKHO_XUAT = "";
			if( rs != null )
			{
				try 
				{
					if( rs.next() )
					{
						LOAIKHO_XUAT = rs.getString("LOAIKHO_XUAT");
						this.coChiphi = rs.getString("coChiphi");
					}
					rs.close();
				} 
				catch (Exception e) { 
					e.printStackTrace();
				}
			}
			
			query = "select PK_SEQ, TEN, LOAI from ERP_KHOTT where TrangThai = '1' ";
			if( LOAIKHO_XUAT.trim().length() > 0 )
				query += " and loaiKHO in ( " + LOAIKHO_XUAT + " ) ";
			
			query += " and pk_seq in " + util.quyen_khott(this.userId);
			query += " order by loai asc, TEN asc ";
			
			System.out.println("::: LAY KHO XUAT: " + query);
			this.khoXuatRs = db.get(query);
			
			if( this.coChiphi.equals("1") )
			{
				if(this.getMaNDX( this.ndxId ).equals("XK19"))
				{
					query = "select PK_SEQ, TEN + ', ' + DIENGIAI as TEN from ERP_NHOMCHIPHI where TRANGTHAI = '1' and taikhoan_fk like '632%' order by TEN asc";
					this.chiphiRs = db.get(query);
				}else{
					query = "select PK_SEQ, TEN + ', ' + DIENGIAI as TEN from ERP_NHOMCHIPHI where TRANGTHAI = '1' order by TEN asc";
					this.chiphiRs = db.get(query);
				}
			}
			
		}
		
		if( this.khoXuatId.trim().length() > 0 )
		{ 
			query = "select loaiKHO from ERP_KHOTT where PK_SEQ = '" + this.khoXuatId + "' ";
			ResultSet rs = db.get(query);
			String loaikho = "";
			if( rs != null )
			{
				try 
				{
					if( rs.next() )
					{
						loaikho = rs.getString("loaiKHO");
					}
					rs.close();
				} 
				catch (Exception e) { 
					e.printStackTrace();
				}
			}

			System.out.println( " loai kho : "+ loaikho);

			if( loaikho.equals("7") || loaikho.equals("8") || loaikho.equals("9") || loaikho.equals("77") || loaikho.equals("13")|| loaikho.equals("14")|| loaikho.equals("10"))
			{
				this.codoituong = "1";

				//LOAIDOITUONG 0 NHÀ CUNG CẤP, 1 KHÁCH HÀNG, 3 phòng ban,2 nhà phân phối, 4 là nhân viên
				if( loaikho.equals("7") )
				{
					this.loaidoituong = "0";
					this.doituongRs = db.get("select pk_seq, ten from ERP_NHACUNGCAP where TRANGTHAI = 1 and duyet = '1' and congty_fk="+this.ctyId+" order by TEN asc");
				}
				else if(loaikho.equals("77"))
				{
					this.loaidoituong = "3";
					this.doituongRs = db.get("select pk_seq, ten from ERP_DONVITHUCHIEN where TRANGTHAI = 1 and congty_fk="+this.ctyId+" order by TEN asc");
				}
				else if( loaikho.equals("8") || loaikho.equals("9") || loaikho.equals("13") )
				{
					this.loaidoituong = "1";
					this.doituongRs = db.get("select pk_seq, ten from ERP_KHACHHANG where   TRANGTHAI = 1 order by TEN asc");
				}else if(loaikho.equals("14")){
					this.loaidoituong = "4";
					this.doituongRs = db.get("select pk_seq, ten from erp_nhanvien where   TRANGTHAI = 1 order by TEN asc");
				}else if(loaikho.equals("10")){
				 
					this.loaidoituong = "5";
					if(this.lsxIds!=null && this.CdsxId !=null && this.CdsxId.length() > 0 && this.lsxIds.length() >0  ) {
					  query="select lsx_cd.PK_SEQ , N'Số LSX:' + cast(lsx_cd.LENHSANXUAT_FK as nvarchar(20)) +', CĐ: ' + cd.DienGiai as TEN "
							+ "from ERP_LENHSANXUAT_CONGDOAN_GIAY lsx_cd inner join Erp_CongDoanSanXuat_Giay cd on cd.PK_SEQ=lsx_cd.CONGDOAN_FK"
							+ " where lsx_cd.LENHSANXUAT_FK= "+lsxIds+" ";
								//	+ "and CONGDOAN_FK="+CdsxId;
					  this.doituongRs = db.get(query);
					}
					

					//System.out.println( " doi tuong kho:  "+ "select pk_seq, ten from NHAPHANPHOI where isCongty = 0 and TRANGTHAI = 1 and congty_fk = "+this.ctyId+" and pk_seq in ( select CHINHANH_FK from ERP_KHOTT_CHINHANH where KHOTT_FK = '" + this.khoXuatId + "' ) order by TEN asc");

				}	
				else
				{
					this.loaidoituong = "2";
					this.doituongRs = db.get("select pk_seq, ten from NHAPHANPHOI where isCongty = 0 and TRANGTHAI = 1 and congty_fk = "+this.ctyId+" and pk_seq in ( select CHINHANH_FK from ERP_KHOTT_CHINHANH where KHOTT_FK = '" + this.khoXuatId + "' ) order by TEN asc");

					System.out.println( " doi tuong kho:  "+ "select pk_seq, ten from NHAPHANPHOI where isCongty = 0 and TRANGTHAI = 1 and congty_fk = "+this.ctyId+" and pk_seq in ( select CHINHANH_FK from ERP_KHOTT_CHINHANH where KHOTT_FK = '" + this.khoXuatId + "' ) order by TEN asc");

				}	
				
				
			}
			else
			{
				this.codoituong = "";
				this.loaidoituong = "";
			}
		}
		
		if( this.getMaNDX( this.ndxId ).equals("XK07") || this.getMaNDX( this.ndxId ).equals("XK08") || this.getMaNDX( this.ndxId ).equals("XK09")
				|| this.getMaNDX( this.ndxId ).equals("XK10") || this.getMaNDX( this.ndxId ).equals("XK12") || this.getMaNDX( this.ndxId ).equals("XK17")	)
		{
			query = "select LOAIKHO_NHAP from ERP_NOIDUNGNHAP where pk_seq = '" + this.ndxId + "'";
			System.out.println("::: SETTING: " + query);
			ResultSet rs = db.get(query);
			
			String LOAIKHO_NHAP = "";
			if( rs != null )
			{
				try 
				{
					if( rs.next() )
					{
						LOAIKHO_NHAP = rs.getString("LOAIKHO_NHAP");
					}
					rs.close();
				} 
				catch (Exception e) { 
					e.printStackTrace();
				}
			}
			
			query = " select PK_SEQ, TEN, LOAI from ERP_KHOTT where TrangThai = '1' ";
			if(this.khoXuatId.trim().length() > 0 && !this.getMaNDX( this.ndxId ).equals("XK10") )
				query += " and pk_seq != '" + this.khoXuatId + "' ";
			if( LOAIKHO_NHAP.trim().length() > 0 )
				query += " and loaiKHO in ( " + LOAIKHO_NHAP + " )  ";

			query += " order by loai asc ";
			
			System.out.println("::: LAY KHO NHAN: " + query);
			this.khoNhanRs = db.get(query);
			
			this.cokhonhan = "1";
		}
		else
			this.cokhonhan = "0";
		
		
		if( this.khoNhanId.trim().length() > 0 )
		{
			query = "select loaiKHO from ERP_KHOTT where PK_SEQ = '" + this.khoNhanId + "' ";
			ResultSet rs = db.get(query);
			String loaikhonhan = "";
			if( rs != null )
			{
				try 
				{
					if( rs.next() )
					{
						loaikhonhan = rs.getString("loaiKHO");
					}
					rs.close();
				} 
				catch (Exception e) { 
					e.printStackTrace();
				}
			}


			System.out.println("loai kho nhan: "+loaikhonhan);


			if( loaikhonhan.trim().equals("7") || loaikhonhan.trim().equals("8")  || loaikhonhan.trim().equals("13") || loaikhonhan.trim().equals("14")  || loaikhonhan.trim().equals("9")  )
			{
				this.codoituongNhan = "1";

				//LOAIDOITUONG 0 NHÀ CUNG CẤP, 1 KHÁCH HÀNG
				if( loaikhonhan.equals("8")  || loaikhonhan.trim().equals("13")  || loaikhonhan.trim().equals("9")  )
				{
					this.loaidoituongNhan = "1";
					this.doituongNhanRs = db.get("select pk_seq, ten from erp_khachhang where TRANGTHAI = 1   order by TEN asc");
				}else if( loaikhonhan.trim().equals("14")){
					this.loaidoituongNhan = "4";
					this.doituongNhanRs = db.get("select pk_seq, ten from erp_nhanvien where TRANGTHAI = 1  order by TEN asc");
				}
				else
				{
					this.loaidoituongNhan = "0";
					this.doituongNhanRs = db.get("select pk_seq, ten from ERP_NHACUNGCAP where TRANGTHAI = 1 and duyet = '1' and congty_fk="+this.ctyId+" order by TEN asc");
				}			
			}
			else if( loaikhonhan.trim().equals("12") )
			{
				 
				
				//bên Tuelinh không còn đối tượng chi nhánh nữa 
			}else if(loaikhonhan.equals("10")){
					this.codoituongNhan = "1";
					this.loaidoituongNhan = "5";
					if(this.lsxIds!=null && this.CdsxId !=null && this.CdsxId.length() > 0 && this.lsxIds.length() >0  ) {
					  query="select lsx_cd.PK_SEQ , N'Số LSX:' + cast(lsx_cd.LENHSANXUAT_FK as nvarchar(20)) +', CĐ: ' + cd.DienGiai as TEN "
							+ "from ERP_LENHSANXUAT_CONGDOAN_GIAY lsx_cd inner join Erp_CongDoanSanXuat_Giay cd on cd.PK_SEQ=lsx_cd.CONGDOAN_FK"
							+ " where lsx_cd.LENHSANXUAT_FK= "+lsxIds+" and CONGDOAN_FK="+CdsxId;
					  this.doituongNhanRs = db.get(query);
					}
					

					//System.out.println( " doi tuong kho:  "+ "select pk_seq, ten from NHAPHANPHOI where isCongty = 0 and TRANGTHAI = 1 and congty_fk = "+this.ctyId+" and pk_seq in ( select CHINHANH_FK from ERP_KHOTT_CHINHANH where KHOTT_FK = '" + this.khoXuatId + "' ) order by TEN asc");

				}
			else
			{
				this.codoituongNhan = "";
				this.loaidoituongNhan = "";
			}
		}
	 
		if( this.getMaNDX( this.ndxId ).equals("XK10") )
		{
			//Chọn được lệnh sản xuất
			//query = "select PK_SEQ, cast(PK_SEQ as varchar(10)) + ' - [' + NGAYBATDAU + '][' + NGAYDUKIENHT + '] ' + diengiai as diengiai from ERP_LENHSANXUAT_GIAY where TRANGTHAI != 7 ";
			
			
		 
			query = " select A.PK_SEQ  ,cast(A.PK_SEQ as varchar(10)) +' - ' + sp.TEN as diengiai  "
					+ "from  ERP_LENHSANXUAT_GIAY A inner join erp_sanpham sp on sp.pk_Seq= a.SANPHAM_FK  where a.TRANGTHAI != 7  ";
			
			
			System.out.println(" lsxRS : "+ query);
			this.lsxRs = db.get(query);
			
			if(this.lsxIds!=null && this.lsxIds.length() >0){
				  query="SELECT PK_SEQ,DIENGIAI FROM ERP_CONGDOANSANXUAT_GIAY CD WHERE CD.PK_SEQ IN (SELECT CONGDOAN_FK FROM ERP_LENHSANXUAT_CONGDOAN_GIAY where LENHSANXUAT_FK= "+this.lsxIds+") ";
				this.CdsxRs=db.get(query);
			}
			
			
			
		}
		
		if( this.getMaNDX( this.ndxId ).equals("XK09")  || this.getMaNDX( this.ndxId ).equals("XK12"))
		{
			query = " select mh.PK_SEQ, mh.SOPO+'--'+mh.NGAYMUA+'--'+sp.Ten+'--'+mh.GHICHUGC as sopo from ERP_MUAHANG mh " +
					" INNER JOIN ERP_MUAHANG_SP mhsp ON mh.pk_seq = mhsp.muahang_fk " +
					" INNER JOIN ERP_SANPHAM sp ON sp.pk_seq = mhsp.sanpham_fk " +
					" WHERE mh.ISGIACONG='1'  ";
			System.out.println("QUERY MUAHANG :"+ query);
			this.muahangList = db.get(query);
		}

		if( ( this.khoXuatId.trim().length() > 0 || this.khoNhanId.trim().length() > 0 ) && this.spList.size() <= 0 && this.id.trim().length() > 0 )
		{
			System.out.println("danh sach san pham");
			this.createChuyenKho_SanPham();
		}

	}

	public String getMaNDX(String ndxId) 
	{
		//Không nối database, lấy ID luôn, bảng này là không đổi
		if( ndxId.equals("100057") )
			return "XK01";
		if( ndxId.equals("100058") )
			return "XK02";
		if( ndxId.equals("100059") )
			return "XK03";
		if( ndxId.equals("100060") )
			return "XK04";
		if( ndxId.equals("100061") )
			return "XK05";
		if( ndxId.equals("100062") )
			return "XK06";
		if( ndxId.equals("100063") )
			return "XK07";
		if( ndxId.equals("100064") )
			return "XK08";
		if( ndxId.equals("100065") )
			return "XK09";
		if( ndxId.equals("100066") )
			return "XK10";
		if( ndxId.equals("100067") )
			return "XK11";
		if( ndxId.equals("100068") )
			return "XK12";
		if( ndxId.equals("100069") )
			return "XK13";
		if( ndxId.equals("100070") )
			return "XK14";
		if( ndxId.equals("100071") )
			return "XK15";
		if( ndxId.equals("100072") )
			return "XK16";
		if( ndxId.equals("100073") )
			return "XK17";
		if( ndxId.equals("100076") )
			return "XK19";
		
		return "";
	}

	private void createChuyenKho_SanPham() 
	{
		System.out.println("lenh san xuat " + this.lsxIds + "ma noiX"+ this.getMaNDX(this.ndxId));
		String query="select count (PK_SEQ) dem from erp_khott where pk_seq='"+this.khoXuatId+"' and loaiKHO=10";
		int dem=0;
		ResultSet spRs=db.get(query);
		
		try {
			if(spRs.next())
			dem=spRs.getInt("dem");
			spRs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 query =  " select isnull(ghichu_chuyenkho,'') as ghichu_chuyenkho , b.pk_seq as spId, isnull( b.MA,b.ma) as spMa, b.Ten    as spTen, " +
						" DONVIDOLUONG.DIENGIAI AS DVT, ycck.LENHSANXUAT_FK as lsxId,  " +
						" ISNULL(a.SOLUONGYEUCAU, 0) as SOLUONGYEUCAU ,isnull(kho.available ,0) as soluongton   " +
						" from ERP_YEUCAUCHUYENKHO_SANPHAM a inner Join ERP_SanPham b on a.SANPHAM_FK = b.PK_SEQ " +
						" inner join erp_yeucauchuyenkho ycck on ycck.pk_seq= a.YEUCAUCHUYENKHO_FK  " +
						" left join dbo.ufn_tonhientai_full( ) kho on kho.khott_fk=ycck.khoxuat_fk and kho.sanpham_fk= a.sanpham_fk       " +
						" LEFT JOIN DONVIDOLUONG ON DONVIDOLUONG.PK_SEQ = b.DVDL_FK  " +
						" where a.YEUCAUCHUYENKHO_FK = '" + this.id + "' ";
		 if(this.lsxIds.trim().length()>0 && this.getMaNDX(this.ndxId).trim().equals("XK10") && dem>0){
				query =  " select isnull(ghichu_chuyenkho,'') as ghichu_chuyenkho , b.pk_seq as spId, isnull( b.MA,b.ma) as spMa, b.Ten    as spTen, " +
						" DONVIDOLUONG.DIENGIAI AS DVT, a.LENHSANXUAT_FK as lsxId,  " +
						" ISNULL(a.SOLUONGYEUCAU, 0) as SOLUONGYEUCAU ,  isnull( (select sum(available) from UFN_GETLSX_TON(ycck.khoxuat_fk,ycck.LENHSANXUAT_FK,a.SANPHAM_FK)),0)soluongton     " +
						" from ERP_YEUCAUCHUYENKHO_SANPHAM a inner Join ERP_SanPham b on a.SANPHAM_FK = b.PK_SEQ " +
						" inner join erp_yeucauchuyenkho ycck on ycck.pk_seq= a.YEUCAUCHUYENKHO_FK  " +
						" LEFT JOIN DONVIDOLUONG ON DONVIDOLUONG.PK_SEQ = b.DVDL_FK  " +
						" where a.YEUCAUCHUYENKHO_FK = '" + this.id + "' ";
			}
		System.out.println("__2.init SP: " + query );
		
		 spRs = db.get(query);
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
					sp.setLsxId(spRs.getString("lsxId")== null ? "" :spRs.getString("lsxId") );
									
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
		String query =  "select ISNULL(CONGDOAN_FK,0) AS CONGDOAN_FK ,ISNULL(TRANGTHAISP,1) AS TRANGTHAISP,isnull(ISCHUYENHANGSX,'0') as ISCHUYENHANGSX ,isnull(NGUOINHAN,'') as NGUOINHAN , Lenhsanxuat_fk, noidungxuat_fk, NGAYYEUCAU, lydo, isnull(ghichu, '') as ghichu, khoxuat_fk, khonhan_fk, trangthai,  " + 
				" 	loaidoituong, doituong_fk, loaidoituongNhan, doituongNhan_fk, lenhsanxuat_fk, " +
				" ISNULL(KYHIEU,'') as KYHIEU, ISNULL(SOCHUNGTU,'') as SOCHUNGTU, ISNULL(LENHDIEUDONG,'') as LENHDIEUDONG, ISNULL(NGAYDIEUDONG,'') AS NGAYDIEUDONG, "+
				" ISNULL(NGUOIDIEUDONG,'') AS NGUOIDIEUDONG, ISNULL(VEVIEC,'') AS VEVIEC, ISNULL(NGUOIVANCHUYEN,'') AS NGUOIVANCHUYEN, ISNULL(PHUONGTIEN,'') AS PHUONGTIEN, ISNULL(HOPDONG,'') AS HOPDONG, chiphi_fk, muahang_fk "+
				"from ERP_YEUCAUCHUYENKHO where pk_seq = '" + this.id + "'";
		
		System.out.println("INIT YEUCAUCHUYENKHO: "+query);
		ResultSet rs = db.get(query);

		try 
		{
			if(rs.next())
			{
				this.lsxIds = rs.getString("Lenhsanxuat_fk") == null ? "" : rs.getString("Lenhsanxuat_fk");
				this.ndxId = rs.getString("noidungxuat_fk");
				this.ngayyeucau = rs.getString("NGAYYEUCAU");
				this.lydoyeucau = rs.getString("lydo");
				this.Nguoinhan = rs.getString("nguoinhan");
				this.ghichu = rs.getString("ghichu");
				this.khoXuatId = rs.getString("khoxuat_fk");
				this.khoNhanId = rs.getString("khonhan_fk");
				this.trangthai = rs.getString("trangthai");

				this.loaidoituong = rs.getString("loaidoituong") == null ? "" : rs.getString("loaidoituong");
				this.doituongId = rs.getString("doituong_fk") == null ? "" : rs.getString("doituong_fk");
				this.loaidoituongNhan = rs.getString("loaidoituongNhan") == null ? "" : rs.getString("loaidoituongNhan");
				this.doituongNhanId = rs.getString("doituongNhan_fk") == null ? "" : rs.getString("doituongNhan_fk");

				this.IsChuyenHangSX=rs.getString("ISCHUYENHANGSX");
				this.IsChuyenhangkhongdat=rs.getString("TRANGTHAISP");
				this.kyhieu = rs.getString("KYHIEU");
				this.sochungtu = rs.getString("SOCHUNGTU");
				this.lenhdieudong = rs.getString("LENHDIEUDONG");
				this.ngaydieudong = rs.getString("NGAYDIEUDONG");
				this.nguoidieudong = rs.getString("NGUOIDIEUDONG");
				this.veviec = rs.getString("VEVIEC");
				this.nguoivanchuyen = rs.getString("NGUOIVANCHUYEN");
				this.phuongtien = rs.getString("PHUONGTIEN");
				this.hopdong = rs.getString("HOPDONG");
				this.CdsxId= (rs.getString("CONGDOAN_FK")==null?"":rs.getString("CONGDOAN_FK"));
				this.chiphiId = rs.getString("chiphi_fk") == null ? "" : rs.getString("chiphi_fk");
				this.lsxIds = rs.getString("lenhsanxuat_fk") == null ? "" : rs.getString("lenhsanxuat_fk");
				this.muahang_fk = rs.getString("muahang_fk") == null ? "" : rs.getString("muahang_fk");
			}
			rs.close();
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
			er.printStackTrace();
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
		String query =  "select a.SANPHAM_FK, b.MA AS MA, b.TEN, a.SOLUONGYEUCAU as SoLuong " +
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
			
					query = " select b.MA AS MA, b.TEN, c.MA AS VITRI, d.DIENGIAI AS KHUTEN, e.DONVI as DONVIDOLUONG, nhapkho.solo, nhapkho.tongNhap, f.ma as khoTen  " +
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
				e.printStackTrace();
			}
			
			this.spList = spList;
		}
	}

	public void initChuyenNLPdf() 
	{
		String query =  "select a.SANPHAM_FK, b.MA AS MA, b.TEN, a.SOLUONGYEUCAU as SoLuong " +
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
		
			query = " select b.MA AS MA, b.TEN, c.MA AS VITRI, d.DIENGIAI AS KHUTEN, e.DONVI as DONVIDOLUONG, nhapkho.solo, nhapkho.tongNhap, f.ma as khoTen  " +
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


	public void initNhanhang() 
	{
		String query = "select noidungxuat_fk, ngaychuyen, lydo, khoxuat_fk, khonhan_fk, trangthai, isnull(trangthaisp, 0) as trangthaisp, NCC_CHUYEN_FK, NCC_NHAN_FK " +
						"from ERP_CHUYENKHO where pk_seq = '" + this.id + "'";
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					this.ndxId = rs.getString("noidungxuat_fk");
					this.ngayyeucau = rs.getString("ngaychuyen");
					this.lydoyeucau = rs.getString("lydo");
					this.khoXuatId = rs.getString("khoxuat_fk");
					this.khoNhanId = rs.getString("khonhan_fk") == null ? "" : rs.getString("khonhan_fk");
					this.trangthai = rs.getString("trangthai");
					this.trangthaisp = rs.getString("trangthaisp");
					
					this.nccXuatId = rs.getString("NCC_CHUYEN_FK") == null ? "" : rs.getString("NCC_CHUYEN_FK");
					this.nccNhanId = rs.getString("NCC_NHAN_FK") == null ? "" : rs.getString("NCC_NHAN_FK");
				}
				rs.close();
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}
				
	 
		
		if( this.spList.size() <= 0 )
		{
			this.createChuyenKho_SanPham_NhanHang();
		}
		createRs();
		
		
	}
	
	public void initXuathang() 
	{
		String query = "select noidungxuat_fk, ngaychuyen, lydo, khoxuat_fk, khonhan_fk, trangthai, isnull(trangthaisp, 0) as trangthaisp, NCC_CHUYEN_FK, NCC_NHAN_FK " +
						"from ERP_CHUYENKHO where pk_seq = '" + this.id + "'";
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					this.ndxId = rs.getString("noidungxuat_fk");
					this.ngayyeucau = rs.getString("ngaychuyen");
					this.lydoyeucau = rs.getString("lydo");
					this.khoXuatId = rs.getString("khoxuat_fk");
					this.khoNhanId = rs.getString("khonhan_fk") == null ? "" : rs.getString("khonhan_fk");
					this.trangthai = rs.getString("trangthai");
					this.trangthaisp = rs.getString("trangthaisp");
					
					this.nccXuatId = rs.getString("NCC_CHUYEN_FK") == null ? "" : rs.getString("NCC_CHUYEN_FK");
					this.nccNhanId = rs.getString("NCC_NHAN_FK") == null ? "" : rs.getString("NCC_NHAN_FK");
				}
				rs.close();
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	 
		if( this.spList.size() <= 0 )
		{
			this.createChuyenKho_SanPham_XuatHang();
		}
		
		createRs();
		
		
	}

	private void createChuyenKho_SanPham_NhanHang() 
	{
		String query = " select b.PK_SEQ as spId, b.MA as spMa,  b.Ten   as spTen, dvdl.donvi as spDvdl, a.SOLO, SUM(a.SOLUONGXUAT) as tongXuat, isnull(Sum(a.SOLUONGNHAN), 0) as tongNhan  " +
				" from ERP_CHUYENKHO_SANPHAM a " +
				" inner Join ERP_SanPham b on a.SANPHAM_FK = b.PK_SEQ  " +
				" left join donvidoluong dvdl on dvdl.pk_seq = b.dvdl_fk " +
				" where a.CHUYENKHO_FK = '" + this.id + "'  " +
				" group by b.PK_SEQ, b.MA, b.Ten,   dvdl.donvi, a.SOLO  " +
				" having SUM(a.SOLUONGXUAT) > 0 ";
		
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
					sp.setDonViTinh(spRs.getString("spDvdl"));
					
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
				e.printStackTrace();
			}
			
			this.spList = spList;
		}
	}

	private void createChuyenKho_SanPham_XuatHang() 
	{
		String query = " select b.PK_SEQ as spId, b.MA as spMa,  b.Ten   as spTen, dvdl.donvi as spDvdl, a.SOLO, SUM(a.SOLUONGXUAT) as tongXuat, isnull(Sum(a.SOLUONGNHAN), 0) as tongNhan  " +
				" from ERP_CHUYENKHO_SANPHAM a " +
				" inner Join ERP_SanPham b on a.SANPHAM_FK = b.PK_SEQ  " +
				" left join donvidoluong dvdl on dvdl.pk_seq = b.dvdl_fk " +
				" where a.CHUYENKHO_FK = '" + this.id + "'  " +
				" group by b.PK_SEQ, b.MA, b.Ten , dvdl.donvi, a.SOLO  " +
				" having SUM(a.SOLUONGXUAT) > 0 ";
		
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
					sp.setDonViTinh(spRs.getString("spDvdl"));
					
					if(spRs.getString("tongXuat").trim().length() > 0)
						sp.setSoluongYC(spRs.getString("tongXuat"));
					
					if(spRs.getString("tongNhan").trim().length() > 0)
						sp.setSoluongNhan(spRs.getString("tongNhan"));
					
					
					//Create kho nhan
					query = "select vitri, soluong, khu from ERP_CHUYENKHO_SP_XUATHANG   " +
							"where chuyenkho_fk = '" + this.id + "' and sanpham_fk = '" + spId + "' and solo = '" + solo + "'";
					
					System.out.println("__Khoi tao kho xuat: " + query);
					
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
				e.printStackTrace();
			}
			
			this.spList = spList;
		}
	}

	public void initView() 
	{
		String query = "select ISNULL(TRANGTHAISP,1) AS TRANGTHAISP ,isnull(ISCHUYENHANGSX,'0') as ISCHUYENHANGSX ,isnull(NGUOINHAN,'') as NGUOINHAN , isnull(Lenhsanxuat_fk,0 ) as lsxid ,noidungxuat_fk, NGAYYEUCAU, lydo, isnull(ghichu, '') as ghichu, khoxuat_fk, khonhan_fk, trangthai,   NCC_CHUYEN_FK, NCC_NHAN_FK, " +
				" ISNULL(KYHIEU,'') as KYHIEU, ISNULL(SOCHUNGTU,'') as SOCHUNGTU, ISNULL(LENHDIEUDONG,'') as LENHDIEUDONG, ISNULL(NGAYDIEUDONG,'') AS NGAYDIEUDONG, "+
				" ISNULL(NGUOIDIEUDONG,'') AS NGUOIDIEUDONG, ISNULL(VEVIEC,'') AS VEVIEC, ISNULL(NGUOIVANCHUYEN,'') AS NGUOIVANCHUYEN, ISNULL(PHUONGTIEN,'') AS PHUONGTIEN, ISNULL(HOPDONG,'') AS HOPDONG, muahang_fk "+
					"from ERP_YEUCAUCHUYENKHO where pk_seq = '" + this.id + "'";
		System.out.println("INIT YEUCAUCHUYENKHO: "+query);
		ResultSet rs = db.get(query);
		 
			try 
			{
				if(rs.next())
				{
					this.lsxIds=rs.getString("lsxid");
					this.ndxId = rs.getString("noidungxuat_fk");
					this.ngayyeucau = rs.getString("NGAYYEUCAU");
					this.lydoyeucau = rs.getString("lydo");
					this.Nguoinhan = rs.getString("nguoinhan");
					this.ghichu = rs.getString("ghichu");
					this.khoXuatId = rs.getString("khoxuat_fk");
					this.khoNhanId = rs.getString("khonhan_fk");
					this.trangthai = rs.getString("trangthai");
					this.nccXuatId = rs.getString("NCC_CHUYEN_FK") == null ? "" : rs.getString("NCC_CHUYEN_FK");
					this.nccNhanId = rs.getString("NCC_NHAN_FK") == null ? "" : rs.getString("NCC_NHAN_FK");
					this.IsChuyenHangSX=rs.getString("ISCHUYENHANGSX");
					this.IsChuyenhangkhongdat=rs.getString("TRANGTHAISP");
					this.kyhieu = rs.getString("KYHIEU");
					this.sochungtu = rs.getString("SOCHUNGTU");
					this.lenhdieudong = rs.getString("LENHDIEUDONG");
					this.ngaydieudong = rs.getString("NGAYDIEUDONG");
					this.nguoidieudong = rs.getString("NGUOIDIEUDONG");
					this.veviec = rs.getString("VEVIEC");
					this.nguoivanchuyen = rs.getString("NGUOIVANCHUYEN");
					this.phuongtien = rs.getString("PHUONGTIEN");
					this.hopdong = rs.getString("HOPDONG");
					this.muahang_fk = rs.getString("muahang_fk");
				}
				rs.close();
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		
		if( this.spList.size() <= 0 )
		{
			System.out.println(" Size : "+this.spList.size());
			
			this.createChuyenKho_SanPham_View();
		}
		 createRs();
		 
	}

//-----------------------------------------PDF-----------------------------------------------//
	public void initPdf() 
	{
		String query = 
			" select ndn.ten as ndx, isnull(ck.ngaychuyen, '') as ngaychuyen, isnull(ck.lydo, '') as lydo, " +
			"     ck.khoxuat_fk as khoxuat_fk, isnull(kx.ten, '') as khoxuat, ck.khonhan_fk as khonhan_fk, isnull(kn.ten, '') as khonhan, " +
			"     isnull(nccnhan.ten, '') as nccnhan, isnull(nccchuyen.ten, '') as nccchuyen" +
			" from ERP_CHUYENKHO ck " +
			" left join ERP_NOIDUNGNHAP ndn on ndn.pk_seq = ck.noidungxuat_fk " +
			" left join erp_khott kx on kx.pk_seq = ck.khoxuat_fk " +
			" left join erp_khott kn on kn.pk_seq = ck.khonhan_fk " +
			" left join erp_nhacungcap nccnhan on nccnhan.pk_seq = ck.ncc_nhan_fk " +
			" left join erp_nhacungcap nccchuyen on nccchuyen.pk_seq = ck.ncc_chuyen_fk " +
			" where ck.pk_seq = '" + this.id + "'";
		System.out.println("[ErpChuyenkhoSX] query = " + query);
		ResultSet rs = db.get(query);
		String khoxuat_fk = ""; 
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					this.ndxId = rs.getString("ndx");
					this.lydoyeucau = rs.getString("lydo");
					this.khoXuatId = rs.getString("khoxuat_fk");
					this.khoNhanId = rs.getString("khonhan_fk");
					this.khoXuatTen = rs.getString("khoxuat");
					this.khoNhanTen = rs.getString("khonhan");
					this.nccNhanId = rs.getString("nccnhan");
					this.nccXuatId = rs.getString("nccchuyen");
					khoxuat_fk = rs.getString("khoxuat_fk");
					
					//Ngày yêu cầu
					try {
						String[] nyc = rs.getString("ngaychuyen").split("-");
						this.ngayyeucau = nyc[2]+"/"+nyc[1]+"/"+nyc[0];
					} catch(Exception e) {
						e.printStackTrace();
						this.ngayyeucau = rs.getString("ngaychuyen");
					}
				}
				rs.close();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(khoxuat_fk == null) khoxuat_fk = "";
		
		//Lấy sản phẩm
		if(khoxuat_fk.trim().equals("100016") || khoxuat_fk.trim().equals("100010") || khoxuat_fk.trim().equals("100011") ) 
		{
			//Kho xuất là kho vật tư (Spide Part) - 100016
			//Kho nhờ gia công, kho nhận gia công - 100010, 100011
			this.spList = layDanhSachSanPhamVatTuGiaCong();
		}
		else 
		{
			//Lấy sản phẩm phụ liệu
			String lspStr = "100015,100016,100017"; //loại sản phẩm phụ liệu
			this.spChoNhapList = this.layDanhSachSanPhamPhuLieu(lspStr, false);
			
			//Lấy các sản phẩm khác phụ liệu
			this.spList = this.layDanhSachSanPhamKhac(lspStr, true);
			
			if(this.spList.size() > 0) {
				int sospthieu = 3 - this.spList.size() % 3;
				for(int i = 0; i < sospthieu; i++) {
					this.spList.add(new Yeucau());
				}
			}
		}
		
	}
	
	/**
	 * Lấy danh sách sản phẩm của chuyển kho "this.id" và kho là kho vật tư (spide part) hoặc kho gia công
	 * @return List<IYeucau>
	 */
	private List<IYeucau> layDanhSachSanPhamVatTuGiaCong() {
		//Kho xuất là kho vật tư (Spide Part) - 100016
		//Kho nhờ gia công, kho nhận gia công - 100010, 100011
		String query = " SELECT B.PK_SEQ as SPID, B.MA, B.MA, B.TEN as SPTEN, ISNULL(C.DONVI, '') AS DVDL, B.DAI, B.DVDL_DAI, B.RONG, B.DVDL_RONG, B.DINHLUONG, B.DVDL_DINHLUONG, A.SOLUONGXUAT " + 
				" FROM ERP_CHUYENKHO_SANPHAM A " + 
				" INNER JOIN ERP_SANPHAM B ON A.SANPHAM_FK = B.PK_SEQ " +
				" LEFT JOIN DONVIDOLUONG C ON B.DVDL_FK = C.PK_SEQ " +
				" WHERE A.CHUYENKHO_FK = " + this.id + " AND A.SOLUONGXUAT > 0 ";
		System.out.println("[ErpChuyenkhoSX] query = " + query);
		ResultSet spRs = db.get(query);
		
		List<IYeucau> spList = new ArrayList<IYeucau>();
		
		if(spRs != null)
		{
			try 
			{
				IYeucau sp = null;
				while(spRs.next())
				{
					sp = new Yeucau();
					sp.setId(spRs.getString("SPID"));
					sp.setMa(spRs.getString("MA"));
					sp.setTen(spRs.getString("SPTEN"));
					sp.setDonViTinh(spRs.getString("DVDL"));
					
					if(spRs.getString("SOLUONGXUAT").trim().length() > 0)
						sp.setSoluongYC(spRs.getString("SOLUONGXUAT"));
					
					double dai = 0, rong = 0, dinhluong = 0;
					try { dai = Double.parseDouble(spRs.getString("DAI")); } catch(Exception e) {e.printStackTrace(); }
					try { rong = Double.parseDouble(spRs.getString("RONG")); } catch(Exception e) {e.printStackTrace(); }
					try { dinhluong = Double.parseDouble(spRs.getString("DINHLUONG")); } catch(Exception e) {e.printStackTrace(); }
					String quycach = "";
					if(dai > 0) {
						quycach = dai + " " + spRs.getString("DVDL_DAI");
					}
					if(rong > 0) {
						if(quycach.length() > 0) { quycach += " x "; }
						quycach += rong + " " + spRs.getString("DVDL_RONG");
					}
					if(dinhluong > 0) {
						if(quycach.length() > 0) { quycach += " x "; }
						quycach += dinhluong + " " + spRs.getString("DVDL_DINHLUONG");
					}
					sp.setSolo(quycach);
					
					spList.add(sp);
				}
				spRs.close();
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				System.out.println("[ErpChuyenkhoSX.layDanhSachSanPhamPhuLieu] Exception: " + e.getMessage());
			}
		}
		
		return spList;
	}
	
	/**
	 * Lấy danh sách sản phẩm của chuyển kho "this.id" và có loại sản phẩm nằm trong "loaisanphamStr"
	 * @param loaisanphamStr String chuỗi loại sản phẩm cách nhau bởi dấu ,
	 * @param notIn boolean nếu notIn = true thì lấy những sản phẩm không nằm trong chuỗi loaisanphamStr
	 * @return List<IYeucau>
	 */
	private List<IYeucau> layDanhSachSanPhamPhuLieu(String loaisanphamStr, boolean notIn) {
		ResultSet spRs;
		
		String lsp = loaisanphamStr == null || loaisanphamStr.trim().length() <= 0 ? "null" : loaisanphamStr.trim();
		String not = notIn ? " not " : "";
		
		String 
		query = " SELECT B.PK_SEQ as SPID, B.MA as SPMA, B.MA, B.TEN as SPTEN, ISNULL(C.DONVI, '') AS DVDL, B.DAI, B.DVDL_DAI, B.RONG, B.DVDL_RONG, B.DINHLUONG, B.DVDL_DINHLUONG, A.SOLUONGXUAT " + 
				" FROM ERP_CHUYENKHO_SANPHAM A " + 
				" INNER JOIN ERP_SANPHAM B ON A.SANPHAM_FK = B.PK_SEQ " +
				" left join ERP_LOAISANPHAM lsp on b.LOAISANPHAM_FK = lsp.PK_SEQ " +
				" LEFT JOIN DONVIDOLUONG C ON B.DVDL_FK = C.PK_SEQ " +
				" WHERE A.CHUYENKHO_FK = " + this.id + " AND A.SOLUONGXUAT > 0 and B.LOAISANPHAM_FK " + not + " in ( " + lsp + " ) ";
		System.out.println("[ErpChuyenkhoSX] query = " + query);
		spRs = db.get(query);
		List<IYeucau> spList = new ArrayList<IYeucau>();
		try 
		{
			IYeucau sp = null;
			while(spRs.next())
			{
				sp = new Yeucau();
				//sp.setId(spRs.getString("spId"));
				sp.setMa(spRs.getString("MA"));
				sp.setTen(spRs.getString("SPTEN"));
				sp.setDonViTinh(spRs.getString("DVDL"));
				
				double dai = 0, rong = 0, dinhluong = 0;
				try { dai = Double.parseDouble(spRs.getString("DAI")); } catch(Exception e) {e.printStackTrace(); }
				try { rong = Double.parseDouble(spRs.getString("RONG")); } catch(Exception e) {e.printStackTrace(); }
				try { dinhluong = Double.parseDouble(spRs.getString("DINHLUONG")); } catch(Exception e) {e.printStackTrace(); }
				String quycach = "";
				if(dai > 0) {
					quycach = dai + " " + spRs.getString("DVDL_DAI");
				}
				if(rong > 0) {
					if(quycach.length() > 0) { quycach += " x "; }
					quycach += rong + " " + spRs.getString("DVDL_RONG");
				}
				if(dinhluong > 0) {
					if(quycach.length() > 0) { quycach += " x "; }
					quycach += dinhluong + " " + spRs.getString("DVDL_DINHLUONG");
				}
				sp.setSolo(quycach);
				
				if(spRs.getString("SOLUONGXUAT").trim().length() > 0)
					sp.setSoluongYC(spRs.getString("SOLUONGXUAT"));
				
				spList.add(sp);
			}
			spRs.close();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			System.out.println("1.Exception: " + e.getMessage());
		}

		return spList;
	}
	
	/**
	 * Lấy danh sách sản phẩm của chuyển kho "this.id" và có loại sản phẩm nằm trong "loaisanphamStr" (chi tiết sp nhóm theo mã chi tiết và định lượng)
	 * @param loaisanphamStr String chuỗi loại sản phẩm cách nhau bởi dấu ,
	 * @param notIn boolean nếu notIn = true thì lấy những sản phẩm không nằm trong chuỗi loaisanphamStr
	 * @return List<IYeucau>
	 */
	private List<IYeucau> layDanhSachSanPhamKhac(String loaisanphamStr, boolean notIn) {
		ResultSet spRs, spCtRs;
		
		String lsp = loaisanphamStr == null || loaisanphamStr.trim().length() <= 0 ? "null" : loaisanphamStr.trim();
		String not = notIn ? " not " : "";
		
		String 
		query = " select b.MA as spMa, b.Ten as spTen,  SUM(a.SOLUONGXUAT) as tongXuat, isnull(Sum(a.SOLUONGNHAN), 0) as tongNhan, c.ten as lspTen  " +
				" from ERP_CHUYENKHO_SANPHAM a " +
				" inner Join ERP_SanPham b on a.SANPHAM_FK = b.PK_SEQ  " +
				" left join ERP_LOAISANPHAM c on b.LOAISANPHAM_FK = C.PK_SEQ " +
				" where a.CHUYENKHO_FK = '" + this.id + "' and B.LOAISANPHAM_FK " + not + " in ( " + lsp + " ) " +
				" group by b.MA, b.TEN, c.ten  " +
				" having SUM(a.SOLUONGXUAT) > 0 ";
		System.out.println("[ErpChuyenkhoSX] query = " + query);
		spRs = db.get(query);
		List<IYeucau> spList = new ArrayList<IYeucau>();
		try 
		{
			IYeucau sp = null;
			ISpDetail spct = null;
			while(spRs.next())
			{
				sp = new Yeucau();
				//sp.setId(spRs.getString("spId"));
				sp.setMa(spRs.getString("spMa"));
				sp.setTen(spRs.getString("spTen"));
				sp.setSolo(spRs.getString("lspTen"));
				
				if(spRs.getString("tongXuat").trim().length() > 0)
					sp.setSoluongYC(spRs.getString("tongXuat"));
				
				if(spRs.getString("tongNhan").trim().length() > 0)
					sp.setSoluongNhan(spRs.getString("tongNhan"));
				spList.add(sp);
				
				//Lấy chi tiết
				query = " SELECT B.MA, B.TEN, B.DINHLUONG, B.DVDL_DINHLUONG, SUM(A.SOLUONGXUAT) AS SOLUONGXUAT " + 
						" FROM ERP_CHUYENKHO_SANPHAM A " + 
						" INNER JOIN ERP_SANPHAM B ON A.SANPHAM_FK = B.PK_SEQ   " + 
						" WHERE B.MA = N'" + sp.getMa() + "' AND A.SOLUONGXUAT > 0 AND A.CHUYENKHO_FK = " + this.id + " " + 
						" GROUP BY B.MA, B.TEN, B.DINHLUONG, B.DVDL_DINHLUONG " + 
						" HAVING SUM(A.SOLUONGXUAT) > 0 ";
				//System.out.println("[ErpChuyenkhoSX] query = " + query);
				spCtRs = db.get(query);
				while(spCtRs.next())
				{
					spct = new SpDetail();
					spct.setMa(spCtRs.getString("MA"));
					spct.setTen(spCtRs.getString("TEN"));
					spct.setSoluong(spCtRs.getString("SOLUONGXUAT"));
					
					double dinhluong = 0;
					try { dinhluong = Double.parseDouble(spCtRs.getString("DINHLUONG")); } catch(Exception e) {e.printStackTrace(); }
					spct.setDvt(dinhluong > 0 ? dinhluong + " " + spCtRs.getString("DVDL_DINHLUONG") : "");
					spct.setVitri(dinhluong+"");
					
					sp.getSpDetailList().add(spct);
				}
				spCtRs.close();
			}
			spRs.close();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			System.out.println("1.Exception: " + e.getMessage());
		}

		return spList;
	}
//-----------------------------------------END PDF-----------------------------------------------//
	
	
	
	private void createChuyenKho_SanPham_View() 
	{
	 
		String query=" SELECT  ISNULL(a.ghichu_chuyenkho,'') as ghichu , B.PK_SEQ AS SPID, ISNULL(B.MA,B.MA) AS SPMA, B.TEN   AS SPTEN, DVDL.DONVI AS SPDVDL, SOLUONGYEUCAU, "+
					 " ( SELECT ISNULL(SUM (SOLUONGXUAT),0)  " +
					 "   FROM ERP_CHUYENKHO CK1  INNER JOIN ERP_CHUYENKHO_SANPHAM CKSP1 ON CK1.PK_SEQ=CKSP1.CHUYENKHO_FK  "+
					 "        AND CK1.YEUCAUCHUYENKHO_FK=CK.PK_SEQ AND CKSP1.SANPHAM_FK=A.SANPHAM_FK  and CK1.trangthai <>4  ) AS TONGSOLUONG," +
					 "  A.LENHSANXUAT_FK AS LSXID, kho.AVAILABLE as tonkhoxuat "+
					 " FROM ERP_YEUCAUCHUYENKHO_SANPHAM A "+
					 " INNER JOIN ERP_YEUCAUCHUYENKHO CK ON CK.PK_SEQ=A.YEUCAUCHUYENKHO_FK "+ 
					 " INNER JOIN ERP_SANPHAM B ON A.SANPHAM_FK = B.PK_SEQ   "+
					 " LEFT JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ = B.DVDL_FK "+ 
					 " LEFT JOIN erp_khott_sanpham kho on CK.khoxuat_fk=kho.khott_fk and A.sanpham_fk=kho.sanpham_fk "+ 
					 " WHERE A.YEUCAUCHUYENKHO_FK =  '" + this.id + "' ";
		
		///System.out.println("221.Khoi tao SP: " + query);
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
					sp = new Yeucau();
					sp.setId(spId);
					sp.setMa(spMa);
					sp.setTen(spTen);
					sp.setSolo("");
					sp.setDonViTinh(spRs.getString("spDvdl"));
					sp.setSoluongYC(spRs.getString("soluongyeucau"));
					sp.setghichu_CK(spRs.getString("ghichu"));
					sp.setSoluongNhan(spRs.getString("tongsoluong"));
					sp.setSoluongTon(spRs.getString("tonkhoxuat"));
					sp.setLsxId(spRs.getString("lsxId")== null ? "":spRs.getString("lsxId"));
					spList.add(sp);
				}
				spRs.close();
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				System.out.println("1.Exception: " + e.getMessage());
			}
			
			this.spList = spList;
		}
	}

	public String getTrangthaiSP() 
	{
		return this.trangthaisp;
	}

	public void setTrangthaiSP(String trangthaisp) 
	{
		this.trangthaisp = trangthaisp;
	}
	
	public String getNdxId()
	{
		return this.ndxId;
	}

	public void setNdxId(String ndxId) 
	{
		this.ndxId = ndxId;
	}

	public ResultSet getNdxList()
	{
		return this.ndxRs;
	}

	public void setNdxList(ResultSet ndxList) 
	{
		this.ndxRs = ndxList;	
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

	public ResultSet getTsddRs() 
	{
		return this.tsddRs;
	}

	public void setTsddRS(ResultSet tsddRs) 
	{
		this.tsddRs = tsddRs;
	}
	
	public boolean createChuyenKho_LSX() {
		
		try{
			this.db.getConnection().setAutoCommit(false);
			
			String query=" select khoxuat_fk, khonhan_fk from erp_phieuyeucau a  " +
						 " inner join ERP_PHIEUYEUCAU_LSX  b on a.pk_seq=b.phieuyeucau_fk " +
					     " where  b.lenhsanxuat_fk= "+this.lsxIds;
			
			ResultSet rs=db.get(query);
			
			if (rs.next()){
				    query = " insert ERP_YEUCAUCHUYENKHO(LENHSANXUAT_FK,noidungxuat_fk, ngayyeucau, lydo, ghichu, trangthai, khoxuat_fk, khonhan_fk,  ngaytao, nguoitao, ngaysua, nguoisua) " +
				            " values("+this.lsxIds+",'" + this.ndxId + "', '" + this.ngayyeucau + "',   N'" + this.lydoyeucau + "', N'" + this.ghichu + "', '0', '" +rs.getString("khoxuat_fk") + "', " + rs.getString("khonhan_fk") + ",   '" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "')";
					
					
			}else{
				query=  " SELECT PK_SEQ as KHONHAN_FK ,KHONHANNGUYENLIEU_FK AS KHOXUAT_FK " +
						" FROM ERP_KHOTT WHERE PK_SEQ= (SELECT A.KHOSANXUAT_FK FROM ERP_LENHSANXUAT_GIAY " +
						" A WHERE A.PK_SEQ="+this.lsxIds+")";
				
				ResultSet rskho=db.get(query);
				rskho.next();
				 query = " insert ERP_YEUCAUCHUYENKHO(LENHSANXUAT_FK,noidungxuat_fk, ngayyeucau, lydo, ghichu, trangthai, khoxuat_fk, khonhan_fk,  ngaytao, nguoitao, ngaysua, nguoisua) " +
		            " values("+this.lsxIds+",'" + this.ndxId + "', '" + this.ngayyeucau + "',   N'" + this.lydoyeucau + "', N'" + this.ghichu + "', '0', '" +rskho.getString("khoxuat_fk") + "', " + rskho.getString("khonhan_fk") + ",   '" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "')";

				 rskho.close();
			}
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_YEUCAUCHUYENKHO " + query;
				db.getConnection().rollback();
				return false;
			}
			query = "select IDENT_CURRENT('ERP_YEUCAUCHUYENKHO') as ckId";
			ResultSet rsPxk = db.get(query);						
			rsPxk.next();
			this.id = rsPxk.getString("ckId");
			rsPxk.close();
			
			rs.close();
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			
			return false;
			
		}catch(Exception er){
			er.printStackTrace();
			this.msg=er.getMessage();
		
			return false;
		}
	}
	
	public  static void main ( String args [  ]  )   {
			try{
			  IErpChuyenkhoSX ck=new ErpChuyenkhoSX();
			  ck.setLenhSXId("100912");
			  
			  ck.createChuyenKho_LSX_DANH();
			  
			}catch(Exception er){
				er.printStackTrace();
			}
	}
	public boolean createChuyenKho_LSX_DANH() {
		System.out.println("Vào đây : ");
		try{
			this.db.getConnection().setAutoCommit(false);
		 
			String query =  " select   sp.ma+ ' ' +sp.ten as tensp, bom.VATTU_FK,bom.SOLUONG -ISNULL(kho.AVAILABLE,0)    as SOLUONGYC, ISNULL(kho.AVAILABLE,0) as AVAI" +
							" ,  bom.LENHSANXUAT_FK  from ERP_LENHSANXUAT_BOM_GIAY bom  " +
							" left join ERP_KHOTT_SANPHAM kho on kho.KHOTT_FK = bom.KHOTT_FK " +
							" and bom.VATTU_FK = kho.SANPHAM_FK INNER join ERP_SANPHAM sp on sp.PK_SEQ = bom.VATTU_FK " +
							" where    bom.LENHSANXUAT_FK = '"+this.lenhsxId+"' ";
			System.out.println(query);
				
			ResultSet rsspyc=db.getScrol(query);
			
				if(!rsspyc.next()){
					//chuyển trạng thái sang đã yêu cầu nguyên liệu
					query = "update ERP_LENHSANXUAT_GIAY set TRANGTHAI = 1 where PK_SEQ = "+this.lenhsxId;
					if(!db.update(query))
					{
						this.msg = "Không thể cập nhật trạng thái ERP_LENHSANXUAT_GIAY";
						db.getConnection().rollback();
						rsspyc.close();
						return false;
					}
					this.db.getConnection().commit();
					this.db.getConnection().setAutoCommit(true);
					rsspyc.close();
					return true;
				}
				//kiểm tra những hàng đang còn thiếu trong kho sản xuất thì mới yêu cầu chuyển thêm 
				query=  " SELECT A.KHOSANXUAT_FK FROM ERP_LENHSANXUAT_GIAY  A WHERE A.PK_SEQ="+this.lenhsxId;
				
				 ResultSet rskho=db.get(query);
				 if(rskho.next()){
					 this.khoNhanId=rskho.getString("KHOSANXUAT_FK");
				 }
				 rskho.close();
				 List<IYeucau>  listyc = new ArrayList<IYeucau>();
				 rsspyc.beforeFirst();
				 while (rsspyc.next()){
					 
					 String tensp=rsspyc.getString("tensp");
					 String sanpham_fk =rsspyc.getString("VATTU_FK");
					 double SOLUONGYC=rsspyc.getDouble("SOLUONGYC");
					 
					 query=" SELECT KHO.PK_SEQ AS KHOID ,KHO_SP.AVAILABLE as soluong "+
					  	   " FROM ERP_KHOTT_SANPHAM KHO_SP  "+
					  	   " INNER JOIN ERP_KHOTT KHO ON KHO.PK_SEQ=KHO_SP.KHOTT_FK "+
					  	   " WHERE KHO.PK_SEQ  IN ( SELECT KHOTT_NL_FK FROM ERP_KHOSX_KHONHANNL  WHERE KHOTT_SX_FK ="+this.khoNhanId +" )"+
					  	   " AND SANPHAM_FK ="+sanpham_fk+ " and KHO_SP.AVAILABLE> 0 " +
					  	   "   order by  KHO_SP.AVAILABLE  desc"; 
					 System.out.println("kho : "+query);
					 ResultSet rstonkho=db.get(query);
					 
					 while(rstonkho.next() && SOLUONGYC >0 ) {
						 
						 
						 	double soluongyeucau=0;
						 	if(rstonkho.getDouble("soluong") >SOLUONGYC ){
						 		soluongyeucau=SOLUONGYC;
						 		SOLUONGYC=0;
						 	}else{
						 		soluongyeucau=rstonkho.getDouble("soluong");
						 		SOLUONGYC=SOLUONGYC - rstonkho.getDouble("soluong");
						 	}
						  
							IYeucau sp =new Yeucau();
							sp.setId(sanpham_fk);
							sp.setSoluongTon(soluongyeucau+"");
							sp.setKhoid(rstonkho.getString("KHOID"));
							listyc.add(sp);
							  
					 }
					 
					 rstonkho.close();
	 
					 if(SOLUONGYC >0){
						 	this.msg = "Vui lòng kiểm tra lại nguyên liệu : ["+tensp+"],không đủ tồn kho nguyên liệu để yêu cầu.";
							return false;
					 }
				 
				 }
				 
				 // sau khi kiểm tra nguyên liệu đủ thì tạo phiếu yêu cầu với các kho tương ứng.
				 
//				 		String khobk="";
				 		// lấy kho ra được 1 mảng 
				 		
				 		String mangkho[]=new String[listyc.size()];
				 		int k=0;
				 		for(int i=0;i< listyc.size();i++ )
						{
				 			
							IYeucau yc1 =	listyc.get(i);
							if(this.kiemtrachuacotrongmang(mangkho,yc1.getKhoid()) ){
								 
								mangkho[k]=yc1.getKhoid();
								k++;
							}
						}
				 		//tạo các yeu cầu theo kho lấy được
				 		for(int i=0;i< mangkho.length;i++ )
						{
				 			if(mangkho[i]!=null && mangkho[i].length() >0){
				 				System.out.println(mangkho[i]);
				 				 this.TaoPhieuYeuCau(listyc,mangkho[i]);
				 			}
						}
				 			
				 	/*	//booked những sản phẩm nào đang có trong kho
						query=  " SELECT  BOM.SOTT, BOM.VATTU_FK, CASE WHEN  (BOM.SOLUONG  -ISNULL(BOM.BOOKED_LSX,0)) >  KHO.AVAILABLE THEN  KHO.AVAILABLE "+
								" ELSE (BOM.SOLUONG  -ISNULL(BOM.BOOKED_LSX,0)) END    SOLUONG,  "+
								" BOM.LENHSANXUAT_FK ,KHO.KHOTT_FK FROM ERP_LENHSANXUAT_BOM_GIAY BOM  "+ 
								" LEFT JOIN ERP_KHOTT_SANPHAM KHO ON KHO.KHOTT_FK = BOM.KHOTT_FK   "+
								" AND BOM.VATTU_FK = KHO.SANPHAM_FK INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = BOM.VATTU_FK "+  
								" WHERE  ISNULL(KHO.AVAILABLE,0) > 0 AND BOM.LENHSANXUAT_FK ="+this.lenhsxId;
						  rskho=db.get(query);
						while (rskho.next()){
							String khoid=rskho.getString("KHOTT_FK");
							String spid=rskho.getString("VATTU_FK");
							double avai=(-1)*rskho.getDouble("SOLUONG");
							double booked=rskho.getDouble("SOLUONG");
							double soluong=0;
							String msg1 =util_kho.Update_Kho_Sp(db, khoid, spid, soluong, booked, avai, 0);
							if(msg1.length() >0){
								this.msg = msg1;
								db.getConnection().rollback();
								return false;
							}
							// cập nhật booked đối với những sản phẩm trong kho
							query=" update ERP_LENHSANXUAT_BOM_GIAY set BOOKED_LSX=isnull(BOOKED_LSX,0)+"+rskho.getDouble("SOLUONG")+" where sott="+rskho.getString("SOTT")+" and  vattu_fk="+spid+" and lenhsanxuat_fk= "+ this.lenhsxId;
							if(db.updateReturnInt(query) != 1)
							{
								this.msg = "Không thể cập nhật: "+query;
								db.getConnection().rollback();
								return false;
							}
						}
						rskho.close();*/
						
				 		
				query = "update ERP_LENHSANXUAT_GIAY set TRANGTHAI = 1 where PK_SEQ = "+this.lenhsxId;
				if(!db.update(query))
				{
					this.msg = "Không thể cập nhật trạng thái ERP_LENHSANXUAT_GIAY";
					db.getConnection().rollback();
					return false;
				}
			 
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			
			return true;
			
		}catch(Exception er){
			
			db.update("rollback");
			er.printStackTrace();
			this.msg=er.getMessage();
		
			return false;
		}
	}

	private boolean kiemtrachuacotrongmang(String[] mangkho, String khoid) {
		
		for(int i=0;i< mangkho.length;i++ )
		{
 			if(mangkho[i]!=null && mangkho[i].length() >0){
 				if(mangkho[i].trim().equals(khoid.trim())){ 
 					return false;
 				}
 				 
 			}
		}
		return true;
	}

	private boolean TaoPhieuYeuCau( List<IYeucau>  listyc , String khoxuat ) {
		
		try{
		 
			 String query="";
			 	this.ndxId="100066";
				 query = " insert ERP_YEUCAUCHUYENKHO (LENHSANXUAT_FK,noidungxuat_fk, ngayyeucau, lydo, trangthai, khoxuat_fk, khonhan_fk,  ngaytao, nguoitao, ngaysua, nguoisua) " +
		 		 " values("+this.lenhsxId+",'" + this.ndxId + "', '" + getDateTime() + "', N'Chuyển kho cho LSX: " + this.lenhsxId + "', '0', '" +khoxuat + "', " + this.khoNhanId + ",   '" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "')";
				 
				 if(!db.update(query))
				 {
					this.msg = "Không thể tạo mới ERP_YEUCAUCHUYENKHO " + query;
					db.getConnection().rollback();
					return false;
				 } 
			 
				query = "select IDENT_CURRENT('ERP_YEUCAUCHUYENKHO') as ckId ";
				ResultSet rsPxk = db.get(query);						
				rsPxk.next();
				this.id = rsPxk.getString("ckId");
				rsPxk.close();
				
				
				
				for(int i=0;i< listyc.size();i++ )
				 
				{
					IYeucau yc1 =	listyc.get(i);
				
					if(yc1.getKhoid().equals(khoxuat)){
				
						query = " INSERT INTO ERP_YEUCAUCHUYENKHO_SANPHAM(YEUCAUCHUYENKHO_FK, SANPHAM_FK, SOLUONGYEUCAU, LENHSANXUAT_FK) " +
								" values("+this.id+","+yc1.getId()+","+yc1.getSoluongTon()+","+this.lenhsxId+")";
						if(!db.update(query))
						{
							this.msg = "Không thể tạo mới ERP_YEUCAUCHUYENKHO_SANPHAM " + query;
							db.getConnection().rollback();
							return false;
						}
					}
				}
		 
		}catch(Exception err){
			err.printStackTrace();
			this.msg= err.getMessage();
			return false;
		}
		return true;
	}

	public String getTongSLYC() 
	{
		return this.tongSLYC;
	}

	public void setTongSLYC(String tongSLYC) 
	{
		this.tongSLYC = tongSLYC;
		
	}

	
	public String getNguoinhan() {
		
		return this.Nguoinhan;
	}

	
	public void setNguoinhan(String Nguoinhan) {
		
		this.Nguoinhan=Nguoinhan;
	}

	
	public String getIsChuyenHangSX() {
		
		return this.IsChuyenHangSX;
	}


	public void setIsChuyenHangSX(String _IsChuyenHangSX) {
		
		this.IsChuyenHangSX=_IsChuyenHangSX;
	}


	public List<ILenhsanxuat> getLenhSXList() 
	{
		return this.lsxList;
	}


	public void setLenhSXList(List<ILenhsanxuat> lenhSXList) 
	{
		this.lsxList = lenhSXList;
	}

	public String getLenhSXId() 
	{
    	return this.lenhsxId;
	}

	public void setLenhSXId(String LenhSXId) 
	{
		this.lenhsxId = LenhSXId;
	}

 

 

	
	public boolean getChoPhepChuyenhangdat() {
		
		//phương thức trả về biến cho phép chuyển hàng đạt và không đạt,nếu trong kho có quản lý hàng theo trạng thái thì mới chuyển được hàng này và được phép nhận hàng này
		//Kho chuyển và kho nhận đều cho phép xử lý hàng này
		//hoặc là chuyển hàng nội dung chuyển là xuất kho phế liệu
		
		return this.ChoPhepchuyenhangdat;
	}

	
	public String getIsChuyenhangkhongdat() {
		
		return this.IsChuyenhangkhongdat;
	}

	
	public void setIsChuyenhangkhongdat(String chuyenhangkhongdat) {
		
		this.IsChuyenhangkhongdat= chuyenhangkhongdat;
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

	
	public boolean createChuyenKhoThem(String khoxuat) {
		try{
			
			String query=  " SELECT A.KHOSANXUAT_FK FROM ERP_LENHSANXUAT_GIAY "+
			" A WHERE A.PK_SEQ="+this.lenhsxId;
	
			 ResultSet rskho=db.get(query);
			 if(rskho.next()){
				 this.khoNhanId=rskho.getString("KHOSANXUAT_FK");
			 }
			 rskho.close();
			 
			 query = " insert ERP_YEUCAUCHUYENKHO (LENHSANXUAT_FK,noidungxuat_fk, ngayyeucau, lydo, trangthai, khoxuat_fk, khonhan_fk,  ngaytao, nguoitao, ngaysua, nguoisua) " +
	 		 " values("+this.lenhsxId+",'" + this.ndxId + "', '" + getDateTime() + "', N'Chuyển kho cho LSX: " + this.lenhsxId + "', '0', '" +khoxuat + "', " + this.khoNhanId + ",   '" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "')";
			 
			 System.out.println("cau lenh them phieu yeu cau chuyen kho:\n" + query + "\n==============================");
			 if(!db.update(query))
			 {
				this.msg = "Không thể tạo mới ERP_YEUCAUCHUYENKHO " + query;
				db.getConnection().rollback();
				return false;
			 } 
		 
			query = "select IDENT_CURRENT('ERP_YEUCAUCHUYENKHO') as ckId ";
			ResultSet rsPxk = db.get(query);						
			rsPxk.next();
			this.id = rsPxk.getString("ckId");
			rsPxk.close();
 
			  query=  " SELECT   BOM.VATTU_FK,  (BOM.SOLUONG - isnull(KHO.available,0) -ISNULL(bom.booked_lsx,0)) as soluongthieu , BOM.LENHSANXUAT_FK  FROM ERP_LENHSANXUAT_BOM_GIAY BOM "+  
			  		  " LEFT JOIN ERP_KHOTT_SANPHAM KHO ON KHO.KHOTT_FK = BOM.KHOTT_FK  AND BOM.VATTU_FK = KHO.SANPHAM_FK "+
			  		  " INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = BOM.VATTU_FK   "+
			  		  " WHERE BOM.LENHSANXUAT_FK = "+this.lenhsxId+	 
			  		  " and  ( BOM.SOLUONG - isnull(KHO.available,0) -ISNULL(bom.booked_lsx,0)) > 0  " +
			  		  " and sp.pk_seq in (select sanpham_fk from erp_khott_sanpham where khott_fk="+khoxuat+")";

			  ResultSet  rscheckkho=db.getScrol(query);
 
				rscheckkho.beforeFirst();
				while(rscheckkho.next()){
					query = " INSERT INTO ERP_YEUCAUCHUYENKHO_SANPHAM(YEUCAUCHUYENKHO_FK, SANPHAM_FK, SOLUONGYEUCAU, LENHSANXUAT_FK) " +
							" values("+this.id+","+rscheckkho.getString("VATTU_FK")+","+rscheckkho.getString("soluongthieu")+","+rscheckkho.getString("LENHSANXUAT_FK")+")";
					if(!db.update(query))
					{
						this.msg = "Không thể tạo mới ERP_YEUCAUCHUYENKHO_SANPHAM " + query;
						db.getConnection().rollback();
						return false;
					}
				}
			rscheckkho.close();
			
			//booked những sản phẩm nào đang có trong kho
			query=  " SELECT  BOM.SOTT, BOM.VATTU_FK, CASE WHEN  (BOM.SOLUONG  -ISNULL(BOM.BOOKED_LSX,0)) >  KHO.AVAILABLE THEN  KHO.AVAILABLE "+
					" ELSE (BOM.SOLUONG  -ISNULL(BOM.BOOKED_LSX,0)) END    SOLUONG,  "+
					" BOM.LENHSANXUAT_FK ,KHO.KHOTT_FK FROM ERP_LENHSANXUAT_BOM_GIAY BOM  "+ 
					" LEFT JOIN ERP_KHOTT_SANPHAM KHO ON KHO.KHOTT_FK = BOM.KHOTT_FK   "+
					" AND BOM.VATTU_FK = KHO.SANPHAM_FK INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = BOM.VATTU_FK "+  
					" WHERE  ISNULL(KHO.AVAILABLE,0) > 0 AND BOM.LENHSANXUAT_FK ="+this.lenhsxId;
			  rskho=db.get(query);
			while (rskho.next()){
				String khoid=rskho.getString("KHOTT_FK");
				String spid=rskho.getString("VATTU_FK");
				double avai=(-1)*rskho.getDouble("SOLUONG");
				double booked=rskho.getDouble("SOLUONG");
				double soluong=0;
				String msg1 =util_kho.Update_Kho_Sp(db, khoid, spid, soluong, booked, avai, 0);
				if(msg1.length() >0){
					this.msg = msg1;
					db.getConnection().rollback();
					return false;
				}
				// cập nhật booked đối với những sản phẩm trong kho
				query=" update ERP_LENHSANXUAT_BOM_GIAY set BOOKED_LSX=isnull(BOOKED_LSX,0)+"+rskho.getDouble("SOLUONG")+" where sott="+rskho.getString("SOTT")+" and  vattu_fk="+spid+" and lenhsanxuat_fk= "+ this.lenhsxId;
				if(db.updateReturnInt(query) != 1)
				{
					this.msg = "Không thể cập nhật: "+query;
					db.getConnection().rollback();
					return false;
				}
			}
			rskho.close();
	   
		}catch(Exception err){
			err.printStackTrace();
			this.msg= err.getMessage();
			return false;
		}
		return true;
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

	
	public String getLenhdieudong() {
		
		return lenhdieudong;
	}

	
	public void setLenhdieudong(String lenhdieudong) {
		
		this.lenhdieudong=lenhdieudong;
	}

	
	public String getNgaydieudong() {
		
		return ngaydieudong;
	}

	
	public void setNgaydieudong(String ngaydieudong) {
		
		this.ngaydieudong=ngaydieudong;
	}

	
	public String getNguoidieudong() {
		
		return this.nguoidieudong;
	}

	
	public void setNguoidieudong(String nguoidieudong) {
		
		this.nguoidieudong=nguoidieudong;
	}

	
	public String getVeviec() {
		
		return this.veviec;
	}

	
	public void setVeviec(String veviec) {
		
		this.veviec=veviec;
	}

	
	public String getNguoivanchuyen() {
		
		return this.nguoivanchuyen;
	}

	
	public void setNguoivanchuyen(String nguoivanchuyen) {
		
		this.nguoivanchuyen=nguoivanchuyen;
	}

	
	public String getPhuongtien() {
		
		return this.phuongtien;
	}

	
	public void setPhuongtien(String phuongtien) {
		
		this.phuongtien=phuongtien;
	}

	
	public String getHopdong() {
		
		return this.hopdong;
	}

	
	public void setHopdong(String hopdong) {
		
		this.hopdong=hopdong;
	}
	
	private boolean checkSoHoaDon() {
		
		try{
			String sql= 
			" SELECT COUNT (*) NUM" +
			" FROM ("+
			"		SELECT * FROM ERP_YEUCAUCHUYENKHO WHERE SOCHUNGTU = '"+this.sochungtu+"' AND KYHIEU = '"+this.kyhieu+"' and TRANGTHAI NOT IN (4) and NOIDUNGXUAT_FK = 100025 ";
					if(this.id .length() > 0){
						sql=sql+" and pk_seq NOT IN ("+this.id +")";
					}			
			sql+= ") a";
			
			System.out.println(sql);
			ResultSet rs = db.get(sql);
			try {
					rs.next();
					if(rs.getInt("NUM") > 0) {
						return true;
					}
					rs.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			rs.close();
		
		}catch(Exception er){
			er.printStackTrace();
			return false;
		}
		return false;
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
	@Override
	public String getCDSXId() {
		// TODO Auto-generated method stub
		return this.CdsxId;
	}

	@Override
	public void setCDSXId(String CDSXId) {
		// TODO Auto-generated method stub
		this.CdsxId= CDSXId;
	}

	@Override
	public ResultSet getCDSXRs() {
		// TODO Auto-generated method stub
		return this.CdsxRs;
	}

	@Override
	public void setCDSXRs(ResultSet CDSXRs) {
		// TODO Auto-generated method stub
		this.CdsxRs= CDSXRs;
	}

	public ResultSet getTonKhoChiTiet(String khoId, String spMa, String lenhsxId)
	{
		String query="select count (PK_SEQ) dem from erp_khott where pk_seq='"+this.khoXuatId+"' and loaiKHO=10";
		int dem=0;
		ResultSet spRs=db.get(query);
		
		try {
			if(spRs.next())
			dem=spRs.getInt("dem");
			spRs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 query = " select solo, ngayhethan, sum( available ) as available  " + 
					   " from dbo.ufn_tonhientai_chitiet_full() " + 
					   " where khott_fk = '" + khoId + "' and sanpham_fk = ( select pk_seq from ERP_SANPHAM where ma = '" + spMa + "' ) and available > 0 " + 
					   " group by solo, ngayhethan order by ngayhethan asc ";
		 if(lenhsxId.trim().length()>0 && this.getMaNDX(this.ndxId).trim().equals("XK10") && dem>0)
			{
			 query="select * from UFN_GETLSX_TON("+khoId+","+lenhsxId+",( select pk_seq from ERP_SANPHAM where ma = '" + spMa + "' ))";
			}
		System.out.println("::: LAY SO LO: " + query);
		return db.get(query);
	}

	 

	 
}
