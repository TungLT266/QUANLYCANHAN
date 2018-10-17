package geso.traphaco.erp.beans.nhaphangungtra.imp;

 
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.util.Library;
import geso.traphaco.erp.util.Utility_Kho;
import geso.dms.center.util.Utility;
 
import geso.traphaco.erp.beans.nhaphangungtra.*; 
import geso.traphaco.erp.beans.nhapkho.IKhu_Vitri;
import geso.traphaco.erp.beans.phieuxuatkho.ISpDetail;
import geso.traphaco.erp.beans.phieuxuatkho.imp.SpDetail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.rmi.CORBA.Util;
 
public class ErpNhaphangungtra implements IErpNhaphangungtra
{
	private static final long serialVersionUID = 1L;
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
	ResultSet khoXuatRs;
	String nhomkenhId="100000";
	String loaikhoxuat;
	String IsChuyenhangkhongdat;
	boolean ChoPhepchuyenhangdat;
	boolean iscokhonhan=false;
	String task;
	String khoNhanId, khoNhanTen;
	ResultSet khoNhanRs;
	
	String nccXuatId;
	ResultSet nccXuatRs;
	
	String nccNhanId;
	ResultSet nccNhanRs;
	String Nguoidenghi="";
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
	String ndxMa;
	ResultSet ndxRs;
	
	String trangthaisp;  // -1 khong dat, 1 dat
	dbutils db;
	Utility_Kho util_kho=new Utility_Kho();
	Utility util=new Utility();
	ResultSet NvRs_xuat;
	ResultSet NvRs_nhan;
	String NvId_nhan="";
	String NvId_xuat="";
	
	String KhId_nhan="";
	String KhId_xuat="";
	
	ResultSet KhRs_xuat;
	ResultSet KhRs_nhan;
	String DonvithuchienId;
	String NhomChiPhiId;
	String CongtyId;
	ResultSet DonViThucHienRs;
	ResultSet NhomChiPhiRs;
	
	String DoituongunghangId;
	String KenhId;
	ResultSet RsKenh;
	ResultSet RsDoituongunghang;
	
	String lspId;
	ResultSet lspRs;
	
	
	public ErpNhaphangungtra()
	{
		IsChuyenhangkhongdat="0";
		  DonvithuchienId="";
		  NhomChiPhiId="";
		  CongtyId="";
		this.id = "";
		this.ctyId = "";
		this.ngayyeucau = getDateTime();
		this.lydoyeucau = "";
		this.ghichu = "";
		this.khoXuatId = "";
		this.khoNhanId = "";
		this.task="";
		this.lsxIds = "";
		this.Nguoidenghi="";
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
		this.ndxMa="";
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
		DoituongunghangId="";
		KenhId="";
		this.lspId="";
		this.db = new dbutils();
	}
	
	public ErpNhaphangungtra(String id)
	{
		this.id = id;
		  DonvithuchienId="";
		  NhomChiPhiId="";
		  CongtyId="";
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
		this.Nguoidenghi="";
		this.Nguoinhan="";
		DoituongunghangId="";
		KenhId="";
		this.ndxMa="";
		this.spList = new ArrayList<IYeucau>();
		this.spChoNhapList = new ArrayList<IYeucau>();
		
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
		this.lspId="";
		
		this.db = new dbutils();
	}
	
	
	public String getLspId() {
		return lspId;
	}

	public void setLspId(String lspId) {
		this.lspId = lspId;
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
		
		if(this.ndxId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn nội dung xuất";
			return false;
		}
		 
		
		if(this.getIsCoKhoNhan() )
		{
			if(this.khoNhanId.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn kho nhận";
				return false;
			}
		}
		
		if(this.getCoKhoanMucChiPhi() ){
			if(this.NhomChiPhiId.length() <=0){
				this.msg = "Vui lòng chọn khoản mục chi phí ";
				return false;
			}
			
		}
		
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
		
		if( util_kho.getIsKhoGiaCong(db,this.khoXuatId))
		{
			if(this.KhId_xuat.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn  khách hàng  chuyển";
				return false;
			}
		}
		
		if( util_kho.getIsKhoGiaCong(db,this.khoNhanId))
		{
			if(this.KhId_nhan.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn khách hàng nhận";
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
			
			if(this.getCoKhoanMucChiPhi() ){
				if(this.NhomChiPhiId.length() <=0){
					 
					this.msg = "Vui lòng chọn khoản mục chi phí ";
					db.getConnection().rollback();
					return false;
				}else{
					//kiểm tra khoản mục chi phí này đã có tài khoản kế toán đúng của công ty chưa?
					 
					String query=  " SELECT TK.PK_SEQ,TK.CONGTY_FK FROM ERP_TAIKHOANKT TK WHERE   SOHIEUTAIKHOAN    IN ( "+
									" SELECT TAIKHOAN_FK FROM ERP_NHOMCHIPHI NCP WHERE NCP.PK_SEQ="+this.NhomChiPhiId+")";
					
					ResultSet rscheck=db.get(query);
					if(!rscheck.next()){
						// KHoản mục chi phí không xác định được tài khoản KT
						this.msg="Vui lòng kiểm tra lại khoản mục chi phí, tài khoản kế toán đi kèm không không xác định đối với khoản mục chi phí này";
						db.getConnection().rollback();
						return false;
						
					}
					rscheck.close();
						
						
				}
			}
			
			
			
			String khonhan_fk = this.khoNhanId.trim().length() <= 0 ? "null" : this.khoNhanId.trim();
			String ncc_xuat_fk = this.nccXuatId.trim().length() <= 0 ? "null" : this.nccXuatId.trim();
			String ncc_nhan_fk = this.nccNhanId.trim().length() <= 0 ? "null" : this.nccNhanId.trim();
			String lspId = this.lspId.trim().length() <= 0 ? "null" : this.lspId.trim();
			
			String query = " insert into ERP_YEUCAUNHAPHANG(DOITUONGUNGHANG_FK,KENH_FK,CONGTY_FK, NGUOIDENGHI,NHOMCHIPHI_FK ,DDKD_FK, DDKD_NHAN_FK,KH_XUAT_FK,KH_NHAN_FK,IsChuyenHangSX,NGUOINHAN,noidungxuat_fk, NGAYYEUCAU,   lydo, ghichu, trangthai, khonhan_fk,  ngaytao, nguoitao, ngaysua, nguoisua, NCC_CHUYEN_FK, NCC_NHAN_FK,TRANGTHAISP,KYHIEU, SOCHUNGTU, LENHDIEUDONG, NGAYDIEUDONG, NGUOIDIEUDONG, VEVIEC, NGUOIVANCHUYEN, PHUONGTIEN, HOPDONG,DVTH_FK,loaisanpham_fk) " +
						   " values("+(this.DoituongunghangId.equals("")?"NULL":this.DoituongunghangId)+","+(this.KenhId.equals("")?"NULL":this.KenhId)+","+this.CongtyId+",N'"+this.Nguoidenghi+"',"+(this.NhomChiPhiId.length()>0?this.NhomChiPhiId:"NULL" )+","+(NvId_xuat.length()>0?NvId_xuat:"NULL")+","+(NvId_nhan.length()>0?NvId_nhan:"NULL")+","+(KhId_xuat.length()>0?KhId_xuat:"NULL")+","+(KhId_nhan.length()>0?KhId_nhan:"NULL")+", " +
						   		""+this.IsChuyenHangSX+",N'"+this.Nguoinhan+"','" + this.ndxId + "', '" + this.ngayyeucau + "',   N'" + this.lydoyeucau + "', N'" + this.ghichu + "', '0',   " + khonhan_fk + ",   '" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "', " + ncc_xuat_fk + ", " + ncc_nhan_fk + ",'"+this.IsChuyenhangkhongdat+"', "+
						    "'"+this.kyhieu+"','"+this.sochungtu+"',N'"+this.lenhdieudong+"','"+this.ngaydieudong+"',N'"+this.nguoidieudong+"',N'"+this.veviec+"',N'"+this.nguoivanchuyen+"',N'"+this.phuongtien+"',N'"+this.hopdong+"','"+this.DonvithuchienId+"',"+lspId+")";
			System.out.println("=============================Query To Moi:0"+query);
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_YEUCAUNHAPHANG " + query;
				db.getConnection().rollback();
				return false;
			}
			
			String ycnlCurrent = "";
			query = "select IDENT_CURRENT('ERP_YEUCAUNHAPHANG') as ckId";
			
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
					
					if(sp.getSoluongYC().trim().length() > 0)
					{
						query = " insert into ERP_YEUCAUNHAPHANG_SANPHAM(YEUCAUNHAPHANG_FK, SANPHAM_FK,  soluongyeucau, ghichu_chuyenkho ,lenhsanxuat_fk  ) " +
								" values( '" +ycnlCurrent+ "', '" + sp.getId() + "',    " + sp.getSoluongYC() + ", N'"+sp.getghichu_CK()+"' , "+ (sp.getLsxId().trim().length() > 0 ? sp.getLsxId() : null) +"   ) ";
						if(!db.update(query))
						{
							this.msg = "Không thể tạo mới ERP_YEUCAUNHAPHANG_SANPHAM: " + query;
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
				this.msg = "Vui lòng nhập ngày yêu cầu";
				return false;
			}
			
			if(this.ndxId.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn nội dung xuất";
				return false;
			}
			
			 
			
			if(this.getCoKhoanMucChiPhi()){
				if(this.NhomChiPhiId.length() <=0){
					this.msg = "Vui lòng chọn khoản mục chi phí ";
					return false;
				}
			}
			
			
			if( this.getIsCoKhoNhan())
			{
				if(this.khoNhanId.trim().length() <= 0)
				{
					this.msg = "Vui lòng chọn kho nhận";
					return false;
				}
			}
			
			if(this.ndxId.equals("100025"))
			{
				if(this.sochungtu.trim().length()>0){
					if(this.checkSoHoaDon())
					{
						this.msg = "Số hóa đơn : "+this.sochungtu+" đã có, vui lòng chọn lại.";
						 
						return false;
					}
				}
			}
			
			
			if( util_kho.getIsKhoGiaCong(db,this.khoXuatId))
			{
				if(this.nccXuatId.trim().length() <= 0)
				{
					this.msg = "Vui lòng chọn nhà cung cấp chuyển";
					return false;
				}
			}
			
			if( util_kho.getIsKhoGiaCong(db,this.khoNhanId))
			{
				if(this.KhId_nhan.trim().length() <= 0)
				{
					this.msg = "Vui lòng chọn khách hàng nhận";
					return false;
				}
			}
			
			if(this.getSpList().size() <= 0)
			{
				System.out.println("CHIEU DAI DANH SACH SAN PHAM: " + this.getSpList().size());
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
			
			Library lib =new Library();
			if(!lib.check_trangthaihople(this.id, "ERP_YEUCAUNHAPHANG", "0", db)){
				// khac trang thai 0 thì hk cho chot
				this.msg="Không thể chỉnh đơn hàng này: phiếu này đã được chốt hoặc trạng thái không hợp lệ! ";
				return false;
				
			}
			
			try
			{
				db.getConnection().setAutoCommit(false);
				if(this.getCoKhoanMucChiPhi() ){
					if(this.NhomChiPhiId.length() <=0){
						
						
						this.msg = "Vui lòng chọn khoản mục chi phí ";
						db.getConnection().rollback();
						return false;
					}else{
						//kiểm tra khoản mục chi phí này đã có tài khoản kế toán đúng của công ty chưa?
						String query=   " SELECT TK.PK_SEQ,TK.CONGTY_FK FROM ERP_TAIKHOANKT TK WHERE  SOHIEUTAIKHOAN    IN ( "+
										" SELECT TAIKHOAN_FK FROM ERP_NHOMCHIPHI NCP WHERE NCP.PK_SEQ="+this.NhomChiPhiId+"  )";
		
						ResultSet rscheck=db.get(query);
						if(!rscheck.next()){
							// KHoản mục chi phí không xác định được tài khoản KT
							this.msg="Vui lòng kiểm tra lại khoản mục chi phí, tài khoản kế toán đi kèm không không xác định đối với khoản mục chi phí này";
							db.getConnection().rollback();
							return false;
							
						}
						rscheck.close();
											
							
					}
				}
				
				String khonhan_fk = this.khoNhanId.trim().length() <= 0 ? "null" : this.khoNhanId.trim();
				String ncc_xuat_fk = this.nccXuatId.trim().length() <= 0 ? "null" : this.nccXuatId.trim();
				String ncc_nhan_fk = this.nccNhanId.trim().length() <= 0 ? "null" : this.nccNhanId.trim();
				String lspId = this.lspId.trim().length() <= 0 ? "null" : this.lspId.trim();
				 
				String query =  " update  ERP_YEUCAUNHAPHANG set  DOITUONGUNGHANG_FK= "+(this.DoituongunghangId.equals("")?"NULL":this.DoituongunghangId)+",  " +
								" KENH_FK= "+(this.KenhId.equals("")?"NULL":this.KenhId)+" ,CONGTY_FK="+this.CongtyId+" , " +
								" NGUOIDENGHI=N'"+this.Nguoidenghi+"',  NHOMCHIPHI_FK="+(this.NhomChiPhiId.length()>0?this.NhomChiPhiId:"NULL" )+",  " +
								" NHOMKENH_FK= "+this.nhomkenhId+", " +
								" DDKD_FK= "+(NvId_xuat.length()>0?NvId_xuat:"NULL")+
								",DDKD_NHAN_FK= "+(NvId_nhan.length()>0?NvId_nhan:"NULL")+", " +
								" KH_XUAT_FK="+(KhId_xuat.length()>0?KhId_xuat:"NULL")+
								",KH_NHAN_FK="+(KhId_nhan.length()>0?KhId_nhan:"NULL")+" " +
								" ,IsChuyenHangSX='"+this.IsChuyenHangSX+"', NGUOINHAN=N'"+this.Nguoinhan+"',noidungxuat_fk ='"+this.ndxId+
								"', NGAYYEUCAU ='"+this.ngayyeucau+"' ,   lydo=N'"+this.lydoyeucau+"' " +
								" ,ghichu =N'"+this.ghichu+"',    khonhan_fk="+khonhan_fk+"  , loaisanpham_fk="+lspId+","+
								" ngaysua='"+this.getDateTime()+"', nguoisua="+this.userId+"," +
								" NCC_CHUYEN_FK="+ncc_xuat_fk+", NCC_NHAN_FK="+ncc_nhan_fk +
								",TRANGTHAISP='"+this.IsChuyenhangkhongdat+"',"+
								" kyhieu='"+this.kyhieu+"', sochungtu='"+this.sochungtu+"',lenhdieudong=N'"+this.lenhdieudong+
								"',ngaydieudong='"+this.ngaydieudong+"',nguoidieudong=N'"+this.nguoidieudong+"',veviec=N'"+this.veviec+"',"+
								" nguoivanchuyen=N'"+this.nguoivanchuyen+"',phuongtien=N'"+this.phuongtien+"',hopdong=N'"+this.hopdong+"',DVTH_FK='"+
								this.DonvithuchienId+"'"+
								" where pk_seq="+this.id;
				if(!db.update(query))
				{
					this.msg = "Không thể tạo mới ERP_YEUCAUNHAPHANG " + query;
					db.getConnection().rollback();
					return false;
				}
				query = " DELETE ERP_YEUCAUNHAPHANG_SANPHAM WHERE YEUCAUNHAPHANG_FK = "+this.id;
				if(!db.update(query))
				{
					this.msg = "Không thể tạo mới ERP_YEUCAUNHAPHANG " + query;
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
							query = "insert ERP_YEUCAUNHAPHANG_SANPHAM(YEUCAUNHAPHANG_FK, SANPHAM_FK,  soluongyeucau, ghichu_chuyenkho ,lenhsanxuat_fk  ) " +
							"values( '" +this.id+ "', '" + sp.getId() + "',    " + sp.getSoluongYC() + ", N'"+sp.getghichu_CK()+"' , "+ (sp.getLsxId().trim().length() > 0 ? sp.getLsxId() : null) +"   ) ";
							if(!db.update(query))
							{
								this.msg = "Không thể tạo mới ERP_YEUCAUNHAPHANG_SANPHAM: " + query;
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
			
			
			String query =  " update  ERP_YEUCAUNHAPHANG set  sochungtu='"+this.sochungtu+"'  where pk_seq="+this.id;			
			
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_YEUCAUNHAPHANG " + query;
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
		catch (Exception e) {}
		
		return true;
		
	}
	
	
	
	public void createRs() 
	{
		  
		this.ChoPhepchuyenhangdat=false;
		if(util_kho.IsKhoQuanLyTrangThai(this.khoXuatId,this.db) ){
			if(this.ndxId.equals("100015")){
				//nếu là xuất hủy 
				this.ChoPhepchuyenhangdat=true;
			}else{
				//nếu không phải xuất hủy thì kho nhận có quản lý trạng thái không, 
				//nếu có quản lý trạng thái thì mới nhận được hàng chờ xử lý ,VD: chuyển từ kho sản xuât hàng không đạt sang kho chờ xử lý
				if(util_kho.IsKhoQuanLyTrangThai(this.khoNhanId, this.db)){
					this.ChoPhepchuyenhangdat=true;
				}
			}
		}
		
		 
		
		String query =  " select pk_seq, MA, MA + ' - ' + TEN as TEN from ERP_NOIDUNGNHAP where MA in ('NK06','NK12')";
		this.ndxRs = db.get(query);
		 
		
		String queryLSP="select PK_SEQ ,MA +' - '+ TEN as TEN from ERP_LOAISANPHAM where trangthai=1 and MA in ('HC1','CDC')";
		this.lspRs=db.get(queryLSP);
		
		Utility util=new Utility();
		String loaikho_xuat="";
		String loaikho_nhap="";
		if(this.ndxId.length()>0){
			query=	" select isnull(loaikho_xuat,'') as loaikho_xuat ,isnull(loaikho_nhap,'')  as loaikho_nhap " +
					" from ERP_NOIDUNGNHAP where PK_SEQ="+this.ndxId;
			System.out.println("sfdsfdsfdsfdsfdsf: " + query);
			ResultSet rs=db.get(query);
			try {
				if(rs.next()){
					loaikho_xuat=rs.getString("loaikho_xuat");
					loaikho_nhap=rs.getString("loaikho_nhap");
					if(loaikho_nhap.length() >0){
						iscokhonhan=true;
					}
				}
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
			 query="SELECT PK_SEQ, TEN   FROM ERP_DONVITHUCHIEN where congty_fk="+this.CongtyId+" ";
			 
			 
			 this.DonViThucHienRs=db.get(query);
			 
			 if(this.DonvithuchienId!=null && this.DonvithuchienId.length()>0){
				 query="SELECT PK_SEQ,TEN + ' -  '+DIENGIAI AS TEN FROM ERP_NHOMCHIPHI WHERE   DONVITHUCHIEN_FK="+this.DonvithuchienId;
				 this.NhomChiPhiRs=db.get(query);
			 }
				 
				
		 	if(loaikho_xuat.length() >0){
			 	query="select PK_SEQ, TEN,LOAI \r\n"
		 			+ " from ERP_KHOTT where TrangThai = '1' "
		 			+ " and loai in ("+loaikho_xuat+") AND CONGTY_FK="+this.CongtyId;
			 	this.khoXuatRs = db.get(query);
			 	System.out.println("sfdsfdsfdsfdsfdsf: " + query);
		 	}
		 	if(loaikho_nhap.length() >0){
		 	/*	query = " select PK_SEQ, TEN ,LOAI from ERP_KHOTT "
		 			  + "\n where TrangThai = '1' and loai in ("+loaikho_nhap+")  AND CONGTY_FK="+this.CongtyId +" and loaikho not in (5,7,8)";*/
		 		if(this.ndxId.equals("100051")){
		 		query="select PK_SEQ,TEN ,LOAI"
		 		    + "\n from ERP_KHOTT "
		 		    + "\n where TrangThai = '1' and CONGTY_FK='"+this.CongtyId+"' and loai not in (14)";
				
		 		this.khoNhanRs = db.get(query);
		 		System.out.println("sfdsfdsfdsfdsfdsf: " + query);
		 		}
		 		
		 		if(this.ndxId.equals("100078")){
		 			query="select PK_SEQ,TEN ,LOAI"
				 		    + "\n from ERP_KHOTT "
				 		    + "\n where TrangThai = '1' and CONGTY_FK='"+this.CongtyId+"' and loai in (14)";
						
				 		this.khoNhanRs = db.get(query);
				 		System.out.println("sfdsfdsfdsfdsfdsf: " + query);
		 		}
		 		
		 	}
		
		 
		 	if(this.khoXuatId!=null && this.khoXuatId.length() >0){
				if(util_kho.getLoaikho(this.khoXuatId,this.db).equals("13")    ){
					query="select PK_SEQ, TEN from ERP_KHACHHANG WHERE  LOAIKH_FK IN (SELECT PK_sEQ FROM LOAIKHACHHANG WHERE ISGIACONG='1') ";
					this.KhRs_xuat = db.get(query);
				}
		 	}
		 	if(this.khoNhanId!=null && this.khoNhanId.length() >0){
		 		query="select PK_SEQ, TEN,CONGTY_FK from ERP_KHACHHANG WHERE  CONGTY_FK='"+this.CongtyId+"' ";
				System.out.println("khach hang " +query);
		 		this.KhRs_nhan = db.get(query);
		 	}
	 		
			/*if( ( this.khoXuatId.trim().length() > 0 || this.khoNhanId.trim().length() > 0 ) && this.spList.size() <= 0 && this.id.trim().length() > 0 )
			{
				this.createChuyenKho_SanPham();
			}*/
			

			
	}

	private void createChuyenKho_SanPham() 
	{
		String query = "";
		 
			query = " select isnull(ghichu_chuyenkho,'') as ghichu_chuyenkho , b.pk_seq as spId, isnull( b.MA,b.ma) as spMa, b.Ten    as spTen, " +
			" DONVIDOLUONG.DIENGIAI AS DVT, a.LENHSANXUAT_FK as lsxId,  " +
			" ISNULL(a.SOLUONGYEUCAU, 0) as SOLUONGYEUCAU ,0 as soluongton   " +
			" from ERP_YEUCAUNHAPHANG_SANPHAM a inner Join ERP_SanPham b on a.SANPHAM_FK = b.PK_SEQ " +
			" inner join ERP_YEUCAUNHAPHANG ycck on ycck.pk_seq= a.YEUCAUNHAPHANG_FK  " +
			 
			" LEFT JOIN DONVIDOLUONG ON DONVIDOLUONG.PK_SEQ = b.DVDL_FK  " +
			" where a.YEUCAUNHAPHANG_FK = '" + this.id + "'    order by a.SANPHAM_FK ";

			 
 
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
		init_ycck();		 
		this.createChuyenKho_SanPham();
		createRs();
	}

	private void init_ycck() {
		// TODO Auto-generated method stub
		String query =  " select LOAISANPHAM_FK,  ISNULL(DOITUONGUNGHANG_FK,0) AS DOITUONGUNGHANG_FK  , ISNULL(KENH_FK,0) AS KENH_FK, " +
						"\n      isnull(NGUOIDENGHI,'') as NGUOIDENGHI, ISNULL(NHOMCHIPHI_FK,0) AS NHOMCHIPHI_FK, isnull( DDKD_FK,0) as  DDKD_FK, "+
					    "\n      isnull(DDKD_NHAN_FK,0) as DDKD_NHAN_FK ,isnull(KH_XUAT_FK,0) as KH_XUAT_FK, " +
					    "\n      isnull(KH_NHAN_FK,0) as KH_NHAN_FK, ISNULL(TRANGTHAISP,1) AS TRANGTHAISP ,isnull(ISCHUYENHANGSX,'0') as ISCHUYENHANGSX ,"+
					    "\n      isnull(NGUOINHAN,'') as NGUOINHAN , isnull(Lenhsanxuat_fk,0 ) as lsxid ,noidungxuat_fk, NGAYYEUCAU, lydo,"+
					    "\n      isnull(ghichu, '') as ghichu, khoxuat_fk, isnull(khonhan_fk,0) as khonhan_fk, trangthai,   NCC_CHUYEN_FK, NCC_NHAN_FK, " +
						"\n      ISNULL(KYHIEU,'') as KYHIEU, ISNULL(SOCHUNGTU,'') as SOCHUNGTU, ISNULL(LENHDIEUDONG,'') as LENHDIEUDONG, "+
						"\n      ISNULL(NGAYDIEUDONG,'') AS NGAYDIEUDONG, ISNULL(NGUOIDIEUDONG,'') AS NGUOIDIEUDONG, ISNULL(VEVIEC,'') AS VEVIEC,"+
						"\n      ISNULL(NGUOIVANCHUYEN,'') AS NGUOIVANCHUYEN, ISNULL(PHUONGTIEN,'') AS PHUONGTIEN, ISNULL(HOPDONG,'') AS HOPDONG ,DVTH_FK"+
						"\n from ERP_YEUCAUNHAPHANG where pk_seq = '" + this.id + "'";
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
				this.khoXuatId = "";
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
				this.NhomChiPhiId=rs.getString("NHOMCHIPHI_FK");
				
				this.Nguoidenghi=rs.getString("NGUOIDENGHI");
				this.lspId=rs.getString("LOAISANPHAM_FK");
				this.KhId_nhan=rs.getString("KH_NHAN_FK");
				this.KhId_xuat=rs.getString("KH_XUAT_FK");
				this.NvId_nhan=rs.getString("DDKD_NHAN_FK");
				this.NvId_xuat=rs.getString("DDKD_FK");
				this.DoituongunghangId=rs.getString("DOITUONGUNGHANG_FK");
				this.KenhId=rs.getString("Kenh_FK");
				this.DonvithuchienId=rs.getString("DVTH_FK");
				
				
				if(this.NhomChiPhiId.length() >0){
					query="SELECT isnull(DONVITHUCHIEN_FK,0) as DONVITHUCHIEN_FK FROM ERP_NHOMCHIPHI WHERE PK_SEQ="+this.NhomChiPhiId;
					
					ResultSet rs1=db.get(query);
					if(rs1.next()){
						this.DonvithuchienId=rs1.getString("DONVITHUCHIEN_FK");
					}
					//System.out.println("============================DVTH================"+this.DonvithuchienId);
					rs1.close();
					
					
				}
			}
			rs.close();
			} 
			catch (Exception e) {
			e.printStackTrace();
			}
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
				System.out.println("1.Exception: " + e.getMessage() + "\n");
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
			catch (Exception e) {}
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
			catch (Exception e) {}
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
				System.out.println("115.Exception: " + e.getMessage());
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
				System.out.println("115.Exception: " + e.getMessage());
			}
			
			this.spList = spList;
		}
	}
	
	public void initView() 
	{
		init_ycck();
		
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
						this.ngayyeucau = rs.getString("ngaychuyen");
					}
				}
				rs.close();
			}
			catch (Exception e) {}
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
					try { dai = Double.parseDouble(spRs.getString("DAI")); } catch(Exception e) { }
					try { rong = Double.parseDouble(spRs.getString("RONG")); } catch(Exception e) { }
					try { dinhluong = Double.parseDouble(spRs.getString("DINHLUONG")); } catch(Exception e) { }
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
				try { dai = Double.parseDouble(spRs.getString("DAI")); } catch(Exception e) { }
				try { rong = Double.parseDouble(spRs.getString("RONG")); } catch(Exception e) { }
				try { dinhluong = Double.parseDouble(spRs.getString("DINHLUONG")); } catch(Exception e) { }
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
					try { dinhluong = Double.parseDouble(spCtRs.getString("DINHLUONG")); } catch(Exception e) { }
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
					 "        AND CK1.YEUCAUNHAPHANG_FK=CK.PK_SEQ AND CKSP1.SANPHAM_FK=A.SANPHAM_FK  and CK1.trangthai <>4  ) AS TONGSOLUONG," +
					 "  A.LENHSANXUAT_FK AS LSXID "+
					 " FROM ERP_YEUCAUNHAPHANG_SANPHAM A "+
					 " INNER JOIN ERP_YEUCAUNHAPHANG CK ON CK.PK_SEQ=A.YEUCAUNHAPHANG_FK "+ 
					 " INNER JOIN ERP_SANPHAM B ON A.SANPHAM_FK = B.PK_SEQ   "+
					 " LEFT JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ = B.DVDL_FK "+ 
					 " WHERE A.YEUCAUNHAPHANG_FK =  '" + this.id + "' ";
		
		System.out.println("221.Khoi tao SP: " + query);
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
				 
					sp.setLsxId(spRs.getString("lsxId")== null ? "":spRs.getString("lsxId"));
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

	
	public ResultSet getLspRs() {
		return lspRs;
	}

	public void setLspRs(ResultSet lspRs) {
		this.lspRs = lspRs;
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

	
	public boolean createChuyenKho_LSX() {
		
		try{
			this.db.getConnection().setAutoCommit(false);
			
			String query=" select khoxuat_fk, khonhan_fk from erp_phieuyeucau a  " +
						 " inner join ERP_PHIEUYEUCAU_LSX  b on a.pk_seq=b.phieuyeucau_fk " +
					     " where  b.lenhsanxuat_fk= "+this.lsxIds;
			
			ResultSet rs=db.get(query);
			
			if (rs.next()){
				    query = " insert ERP_YEUCAUNHAPHANG(LENHSANXUAT_FK,noidungxuat_fk, ngayyeucau, lydo, ghichu, trangthai, khoxuat_fk, khonhan_fk,  ngaytao, nguoitao, ngaysua, nguoisua,loaisanpham_fk) " +
				            " values("+this.lsxIds+",'" + this.ndxId + "', '" + this.ngayyeucau + "',   N'" + this.lydoyeucau + "', N'" + this.ghichu + "', '0', '" +rs.getString("khoxuat_fk") + "', " + rs.getString("khonhan_fk") + ",   '" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId +"','"+this.lspId+ "')";
					
					
			}else{
				query=  " SELECT PK_SEQ as KHONHAN_FK ,KHONHANNGUYENLIEU_FK AS KHOXUAT_FK " +
						" FROM ERP_KHOTT WHERE PK_SEQ= (SELECT A.KHOSANXUAT_FK FROM ERP_LENHSANXUAT_GIAY " +
						" A WHERE A.PK_SEQ="+this.lsxIds+")";
				
				ResultSet rskho=db.get(query);
				rskho.next();
				 query = " insert ERP_YEUCAUNHAPHANG(LENHSANXUAT_FK,noidungxuat_fk, ngayyeucau, lydo, ghichu, trangthai, khoxuat_fk, khonhan_fk,  ngaytao, nguoitao, ngaysua, nguoisua,,loaisanpham_fk) " +
		            " values("+this.lsxIds+",'" + this.ndxId + "', '" + this.ngayyeucau + "',   N'" + this.lydoyeucau + "', N'" + this.ghichu + "', '0', '" +rskho.getString("khoxuat_fk") + "', " + rskho.getString("khonhan_fk") + ",   '" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId +"', '" + getDateTime() + "', '" + this.userId +"','"+this.lspId+ "')";

				 rskho.close();
			}
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_YEUCAUNHAPHANG " + query;
				db.getConnection().rollback();
				return false;
			}
			query = "select IDENT_CURRENT('ERP_YEUCAUNHAPHANG') as ckId";
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
	
	public boolean createChuyenKho_LSX_DANH() {
		
		try{
			this.db.getConnection().setAutoCommit(false);
		 
			String query =  " select   sp.ma+ ' ' +sp.ten as tensp, bom.VATTU_FK,bom.SOLUONG -ISNULL(kho.AVAILABLE,0)    as SOLUONGYC, ISNULL(kho.AVAILABLE,0) as AVAI" +
							" ,  bom.LENHSANXUAT_FK  from ERP_LENHSANXUAT_BOM_GIAY bom  " +
							" left join ERP_KHOTT_SANPHAM kho on kho.KHOTT_FK = bom.KHOTT_FK " +
							" and bom.VATTU_FK = kho.SANPHAM_FK INNER join ERP_SANPHAM sp on sp.PK_SEQ = bom.VATTU_FK " +
							" where ISNULL(kho.AVAILABLE,0) - bom.SOLUONG < 0  and  bom.LENHSANXUAT_FK = '"+this.lenhsxId+"' ";
			
				
			ResultSet rsspyc=db.getScrol(query);
			
				if(!rsspyc.next()){
					//chuyển trạng thái sang đã yêu cầu nguyên liệu
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
					  	   " AND SANPHAM_FK ="+sanpham_fk+
					  	   "   order by  KHO_SP.AVAILABLE  desc"; 
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
				 
				 		String khobk="";
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
				 				this.TaoPhieuYeuCau(listyc,mangkho[i]);
				 			}
						}
				 			
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
							/*String msg1 =util_kho.Update_Kho_Sp(db, khoid, spid, soluong, booked, avai, 0);
							if(msg1.length() >0){
								this.msg = msg1;
								db.getConnection().rollback();
								return false;
							}*/
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
			 
				 query = " insert ERP_YEUCAUNHAPHANG (LENHSANXUAT_FK,noidungxuat_fk, ngayyeucau, lydo, trangthai, khoxuat_fk, khonhan_fk,  ngaytao, nguoitao, ngaysua, nguoisua) " +
		 		 " values("+this.lenhsxId+",'" + this.ndxId + "', '" + getDateTime() + "', N'Chuyển kho cho LSX: " + this.lenhsxId + "', '0', '" +khoxuat + "', " + this.khoNhanId + ",   '" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "')";
				 
				 if(!db.update(query))
				 {
					this.msg = "Không thể tạo mới ERP_YEUCAUNHAPHANG " + query;
					db.getConnection().rollback();
					return false;
				 } 
			 
				query = "select IDENT_CURRENT('ERP_YEUCAUNHAPHANG') as ckId ";
				ResultSet rsPxk = db.get(query);						
				rsPxk.next();
				this.id = rsPxk.getString("ckId");
				rsPxk.close();
				
				
				
				for(int i=0;i< listyc.size();i++ )
				 
				{
					IYeucau yc1 =	listyc.get(i);
				
					if(yc1.getKhoid().equals(khoxuat)){
				
						query = " INSERT INTO ERP_YEUCAUNHAPHANG_SANPHAM(YEUCAUNHAPHANG_FK, SANPHAM_FK, SOLUONGYEUCAU, LENHSANXUAT_FK) " +
								" values("+this.id+","+yc1.getId()+","+yc1.getSoluongTon()+","+this.lenhsxId+")";
						if(!db.update(query))
						{
							this.msg = "Không thể tạo mới ERP_YEUCAUNHAPHANG_SANPHAM " + query;
							db.getConnection().rollback();
							return false;
						}
					}
				}
		 
		}catch(Exception err){
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
			 
			 query = " insert ERP_YEUCAUNHAPHANG (LENHSANXUAT_FK,noidungxuat_fk, ngayyeucau, lydo, trangthai, khoxuat_fk, khonhan_fk,  ngaytao, nguoitao, ngaysua, nguoisua,loaisanpham_fk) " +
	 		 " values("+this.lenhsxId+",'" + this.ndxId + "', '" + getDateTime() + "', N'Chuyển kho cho LSX: " + this.lenhsxId + "', '0', '" +khoxuat + "', " + this.khoNhanId + ",   '" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId +"','"+this.lspId+ "')";
			 
			 if(!db.update(query))
			 {
				this.msg = "Không thể tạo mới ERP_YEUCAUNHAPHANG " + query;
				db.getConnection().rollback();
				return false;
			 } 
		 
			query = "select IDENT_CURRENT('ERP_YEUCAUNHAPHANG') as ckId ";
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
					query = " INSERT INTO ERP_YEUCAUNHAPHANG_SANPHAM(YEUCAUNHAPHANG_FK, SANPHAM_FK, SOLUONGYEUCAU, LENHSANXUAT_FK) " +
							" values("+this.id+","+rscheckkho.getString("VATTU_FK")+","+rscheckkho.getString("soluongthieu")+","+rscheckkho.getString("LENHSANXUAT_FK")+")";
					if(!db.update(query))
					{
						this.msg = "Không thể tạo mới ERP_YEUCAUNHAPHANG_SANPHAM " + query;
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
				/*String msg1 =util_kho.Update_Kho_Sp(db, khoid, spid, soluong, booked, avai, 0);
				if(msg1.length() >0){
					this.msg = msg1;
					db.getConnection().rollback();
					return false;
				}*/
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
			"		SELECT * FROM ERP_YEUCAUNHAPHANG WHERE SOCHUNGTU = '"+this.sochungtu+"' and TRANGTHAI NOT IN (4) and NOIDUNGXUAT_FK = 100025 ";
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
			} catch(Exception e) { }
			
			rs.close();
		
		}catch(Exception er){
			return false;
		}
		return false;
	}

	@Override
	public String getIsKhoTrinhDuyetVien(String khoid) {
		// TODO Auto-generated method stub
		return  "";
	}



	@Override
	public ResultSet getNvRs_nhan() {
		// TODO Auto-generated method stub
		return this.NvRs_nhan;
	}

	@Override
	public ResultSet getNvRs_xuat() {
		// TODO Auto-generated method stub
		return this.NvRs_xuat;
	}

	@Override
	public void setNvId_nhan(String nvid_nhan) {
		// TODO Auto-generated method stub
		this.NvId_nhan=nvid_nhan;
	}

	@Override
	public void setNvid_xuat(String nvid_xuat) {
		// TODO Auto-generated method stub
		this.NvId_xuat=nvid_xuat;
	}

	@Override
	public String getNvId_nhan() {
		// TODO Auto-generated method stub
		return this.NvId_nhan;
	}

	@Override
	public String getNvId_xuat() {
		// TODO Auto-generated method stub
		return this.NvId_xuat;
	}

	@Override
	public String getIsKhoDuTruKhachHang_Kygui(String khoid) {
		// TODO Auto-generated method stub
		
		String loai="0";
		if(khoid!=null &&  khoid.length() >0){
			 if( util_kho.getLoaikho(khoid, db).equals("13")){
				 loai ="1";
			}
		}
		return loai;
		
		 
	}

	@Override
	public ResultSet getKhachHangRs_nhan() {
		// TODO Auto-generated method stub
		return this.KhRs_nhan;
	}

	@Override
	public ResultSet getKhachHangRs_xuat() {
		// TODO Auto-generated method stub
		return this.KhRs_xuat;
	}

	@Override
	public void setKhachHangId_nhan(String KhachHangid_nhan) {
		// TODO Auto-generated method stub
		this.KhId_nhan=KhachHangid_nhan;
	}

	@Override
	public void setKhachHangid_xuat(String KhachHangid_xuat) {
		// TODO Auto-generated method stub
		this.KhId_xuat=KhachHangid_xuat;
	}

	@Override
	public String getKhachHangId_nhan() {
		// TODO Auto-generated method stub
		return this.KhId_nhan;
	}

	@Override
	public String getKhachHangId_xuat() {
		// TODO Auto-generated method stub
		return this.KhId_xuat;
	}

	@Override
	public String getIsKhoCuaNCC_KyGui(String khoid) {
		// TODO Auto-generated method stub
		String loai="";
		if(khoid!=null &&  khoid.length() >0){
			loai = util_kho.getLoaikho(khoid, db);
		}
		return loai;
	}

	@Override
	public boolean getIsCoKhoNhan() {
		// TODO Auto-generated method stub
		return   iscokhonhan;
	}

	@Override
	public String getLoaiKhoXuat() {
		// TODO Auto-generated method stub
		String loai="";
		if( this.khoXuatId!=null &&   this.khoXuatId.length() >0){
			loai = this.util_kho.getLoaikho( this.khoXuatId,db);
		}
		
		return loai;
	}

	@Override
	public String getNhomKenhId() {
		// TODO Auto-generated method stub
		return this.nhomkenhId;
	}

	@Override
	public ResultSet getDonvithuchienRs() {
		// TODO Auto-generated method stub
		return this.DonViThucHienRs;
	}

	@Override
	public ResultSet getNhomChiPhiRs() {
		// TODO Auto-generated method stub
		return this.NhomChiPhiRs;
	}

	@Override
	public void setDonvithuchienId(String dvthId) {
		// TODO Auto-generated method stub
		this.DonvithuchienId=dvthId;
	}

	@Override
	public void setNhomChiPhiId(String ncphiid) {
		// TODO Auto-generated method stub
		this.NhomChiPhiId=ncphiid;
	}

	@Override
	public String getDonvithuchienId() {
		// TODO Auto-generated method stub
		return this.DonvithuchienId;
	}

	@Override
	public String getNhomChiPhiId() {
		// TODO Auto-generated method stub
		return this.NhomChiPhiId;
	}

	@Override
	public String getNguoidenghi() {
		// TODO Auto-generated method stub
		return this.Nguoidenghi;
	}

	@Override
	public void setNguoidenghi(String nguoidenghi) {
		// TODO Auto-generated method stub
		this.Nguoidenghi=nguoidenghi;
	}

	@Override
	public boolean getCoKhoanMucChiPhi() {
		// TODO Auto-generated method stub
		if( this.ndxId!=null && this.ndxId.length() >0){
			if(this.ndxId.equals("100003")|| this.ndxId.equals("100014") ||this.ndxId.equals("100015")  || this.ndxId.equals("100021")|| this.ndxId.equals("100028")){
				return true;
			}
		} 
		return false;
	}

	@Override
	public String getCongytyId() {
		// TODO Auto-generated method stub
		return this.CongtyId;
	}

	@Override
	public void setCongtyId(String congtyId) {
		// TODO Auto-generated method stub
		this.CongtyId=congtyId;
	}

	@Override
	public String getDoiTuongUngHangId() {
		// TODO Auto-generated method stub
		return this.DoituongunghangId;
	}

	@Override
	public void setDoiTuongUngHangId(String DoiTuongUngHangId) {
		// TODO Auto-generated method stub
		this.DoituongunghangId=DoiTuongUngHangId;
	}

	@Override
	public ResultSet getRsDoiTuongUngHang() {
		// TODO Auto-generated method stub
		return this.RsDoituongunghang;
	}

	@Override
	public void setRsDoiTuongUngHang(ResultSet Rs) {
		// TODO Auto-generated method stub
		this.RsDoituongunghang=Rs;
	}

	@Override
	public String getKenhId() {
		// TODO Auto-generated method stub
		return this.KenhId;
	}

	@Override
	public void setKenhId(String kenhId) {
		// TODO Auto-generated method stub
		this.KenhId=kenhId;
	}

	@Override
	public ResultSet getRsKenh() {
		// TODO Auto-generated method stub
		return this.RsKenh;
	}

	@Override
	public void setRsKenh(ResultSet Rs) {
		// TODO Auto-generated method stub
		this.RsKenh=Rs;
	}

	public String getNdxMa() {
		return this.ndxMa;
	}

	public void setNdxMa(String ndxMa) {
		this.ndxMa = ndxMa;
	}


	
	 
}
