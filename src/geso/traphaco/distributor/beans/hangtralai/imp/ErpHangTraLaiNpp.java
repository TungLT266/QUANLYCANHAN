package geso.traphaco.distributor.beans.hangtralai.imp;

import geso.traphaco.distributor.beans.hangtralai.IErpHangTraLaiNpp;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;



public class ErpHangTraLaiNpp implements IErpHangTraLaiNpp
{
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
	
	String khId;
	ResultSet khRs;
	ResultSet dvtRs;
	String tienvat;
	ResultSet loaihdRs;
	ResultSet infohd;
	public ResultSet getInfohd() {
		return infohd;
	}

	public void setInfohd(ResultSet infohd) {
		this.infohd = infohd;
	}

	public ResultSet getLoaihdRs() {
		return loaihdRs;
	}

	public void setLoaihdRs(ResultSet loaihdRs) {
		this.loaihdRs = loaihdRs;
	}

	public String getTienvat() {
		return tienvat;
	}

	public void setTienvat(String tienvat) {
		this.tienvat = tienvat;
	}

	String so;
	
	public String getSo() {
		return so;
	}

	public void setSo(String so) {
		this.so = so;
	}

	String kbhId;
	String makh;
	public String getMakh() {
		return makh;
	}

	public void setMakh(String makh) {
		this.makh = makh;
	}

	ResultSet kbhRs;

	String lenhdieudong, lddcua, lddveviec, ngaylenhdieudong, sohopdong,ngayhopdong, nguoivanchuyen, ptvanchuyen;
	
	ResultSet spRs;
	
	
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

	
	String nppId;
	String nppTen;
	String sitecode;
	String sochungtu;
	String loaitra;
	String loaihd;
	String dhid;
	public String getDhid() {
		return dhid;
	}

	public void setDhid(String dhid) {
		this.dhid = dhid;
	}

	public String getLoaihd() {
		return loaihd;
	}

	public void setLoaihd(String loaihd) {
		this.loaihd = loaihd;
	}

	public String getLoaitra() {
		return loaitra;
	}

	public void setLoaitra(String loaitra) {
		this.loaitra = loaitra;
	}

	Hashtable<String, String> sanpham_soluong;
	
	dbutils db;
	Utility util;
	
	public ErpHangTraLaiNpp()
	{
		this.id = "";
		this.ngayyeucau = getDateTime();
		this.ghichu = "";
		this.khoNhanId = "";
		this.khoXuatId = "";
		this.khId = "";
		this.kbhId = "";
		this.msg = "";
		this.trangthai = "0";
		this.so="";
		this.lenhdieudong="";
		this.lddcua="";
		this.lddveviec="";
		this.ngaylenhdieudong="";
		this.sohopdong="";
		this.ngayhopdong="";
		this.nguoivanchuyen="";
		this.ptvanchuyen="";
		this.tienvat="";
		
		this.sanpham_soluong = new Hashtable<String, String>();
		
		this.sochungtu ="";
		this.doituong ="";
		this.NgayHoaDon="";
		this.KyHieu="";
		this.SoHoaDon="";
		this.dtId="";
		this.loaitra="";
		this.loaihd="";
		this.db = new dbutils();
		this.util = new Utility();
	}
	
	public ErpHangTraLaiNpp(String id)
	{
		this.id = id;
		this.ngayyeucau = getDateTime();
		this.ghichu = "";
		this.khoNhanId = "";
		this.khoXuatId = "";
		this.khId = "";
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
		
		this.sochungtu ="";
		this.doituong ="";
		this.NgayHoaDon="";
		this.KyHieu="";
		this.SoHoaDon="";
		this.dtId="";
		this.loaihd="";
		this.loaitra="";
		this.db = new dbutils();
		this.util = new Utility();
		this.loaitra="";
		
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
		
		if( this.doituong.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn đối tượng";
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
				
				if( spSolo[i].trim().length() > 0 &&  spNgayHetHan[i].trim().length() > 0  )
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
					if( spMa[i].trim().equals(spMa[j].trim()) &&  spSolo[i].trim().equals(spSolo[j].trim()) && spNgayHetHan[i].trim().equals(spNgayHetHan[j].trim()  ) )
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
	
			String query="";
			if(this.doituong.equals("1"))
			{
				 query ="insert into Erp_HangTraLaiNpp(TrangThai,npp_fk,khachhang_fk,npptra_fk,ngaytra,ghichu,kho_fk,SoHoaDon,KyHieu,NgayTao,NgaySua,NguoiTao,NguoiSua,NgayHoaDon,loaitrahang,loaiEO,fromhd,doituong,kbh_fk)  " +
						" select 0,'"+this.nppId+"',"+this.makh+",null,'"+this.ngayyeucau+"',N'"+this.ghichu+"','"+this.khoXuatId+"','"+this.SoHoaDon+"','"+this.KyHieu+"','"+this.getDateTime()+"','"+this.getDateTime()+"','"+this.userId+"','"+this.userId+"','"+this.NgayHoaDon+"',"+this.loaitra+","+this.loaihd+","+this.dhid+","+this.doituong+","+this.kbhId+"  ";			
			}
			else
			{
			 query ="insert into Erp_HangTraLaiNpp(TrangThai,npp_fk,khachhang_fk,npptra_fk,ngaytra,ghichu,kho_fk,SoHoaDon,KyHieu,NgayTao,NgaySua,NguoiTao,NguoiSua,NgayHoaDon,loaitrahang,loaiEO,fromhd,doituong,kbh_fk)  " +
					" select 0,'"+this.nppId+"',null,"+this.makh+",'"+this.ngayyeucau+"',N'"+this.ghichu+"','"+this.khoXuatId+"','"+this.SoHoaDon+"','"+this.KyHieu+"','"+this.getDateTime()+"','"+this.getDateTime()+"','"+this.userId+"','"+this.userId+"','"+this.NgayHoaDon+"',"+this.loaitra+","+this.loaihd+","+this.dhid+","+this.doituong+","+this.kbhId+"  ";			
			}
			 System.out.println("vao day :"+query);
				if(!db.update(query))
				{
					this.msg = "Không thể tạo mới Erp_HangTraLaiNpp " + query;
					db.getConnection().rollback();
					return false;
				}
				
				query = "select SCOPE_IDENTITY() as hdId";
				ResultSet rs1 = db.get(query);
				rs1.next();
				this.id = rs1.getString("hdId");
				rs1.close();
				
			for(int i = 0; i < spMa.length; i++)
			{
				
				
				if(spMa[i].trim().length() > 0 && spSoluong[i].trim().length() > 0 )
				{
					
					if(spSolo[i].trim().length()<=0||spNgayHetHan[i].length()<=0)
					{
						spSolo[i]="NA";
						spNgayHetHan[i]="2015-12-31";
						
					}
					
					System.out.println(spDonvi[i].trim()+"---"+ spSoluong[i].replaceAll(",", "")+"-----------"+spGianhap[i].replaceAll(",", "") );
					query = 
				"	insert into Erp_HangTraLaiNpp_SanPham(HangTraLai_fk,Sanpham_fk,Dvdl_Fk,SoLuong,DonGia,SoLo,NgayHetHan,GhiChu) "+
				"	select '"+this.id+"' as id,sp.pk_seq as sp,  "+
				"		ISNULL( ( select pk_Seq from DONVIDOLUONG where donvi = N'" + spDonvi[i].trim() + "' ), DVDL_FK ), '" + spSoluong[i].replaceAll(",", "") + "' as SoLuong,'" + spGianhap[i].replaceAll(",", "") + "' as DonGia,'"+spSolo[i].trim()+"' as SoLo,'"+spNgayHetHan[i].trim()+"' as NgayHetHan,'"+spGhiChu[i]+"' "+
				"	from SANPHAM sp left join NGANHHANG nh on nh.PK_SEQ=sp.NGANHHANG_FK " +
				" where sp.ma='"+spMa[i]+"'   ";
					
					System.out.println("3.Insert CK - SP: " + query);
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi Erp_HangTraLaiNpp_SanPham: " + query;
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
		
		if( this.kbhId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn kênh bán hàng";
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
				
				if( spSolo[i].trim().length() > 0 &&  spNgayHetHan[i].trim().length() > 0  )
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
					if( spMa[i].trim().equals(spMa[j].trim()) &&  spSolo[i].trim().equals(spSolo[j].trim()) && spNgayHetHan[i].trim().equals(spNgayHetHan[j].trim()  ) )
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
			
			
		String query="";	
			
			
			query="delete from Erp_HangTraLaiNpp_SanPham where HangTraLai_fk='"+this.id+"' ";
			
			int SoDong=0;
			SoDong=db.updateReturnInt(query);
			System.out.println(SoDong+"SoDong__Erp_HangTraLaiNpp_SanPham__"+query);
			if(SoDong<=0)
			{
				this.msg = "Không thể cập nhật Erp_HangTraLaiNpp_SanPham " + query;
				db.getConnection().rollback();
				return false;
			}
			
			if(this.doituong.equals("1"))
			{			
			query ="update  Erp_HangTraLaiNpp set npp_fk="+this.nppId +", khachhang_fk="+this.makh+" ,"+"npptra_fk=null,"+"ngaytra='"+this.ngayyeucau+"',ghichu='"+this.ghichu+"',kho_fk="+this.khoXuatId+",sohoadon='"+this.SoHoaDon+"',kyhieu='"+this.KyHieu+"',ngaysua='"+this.getDateTime()+"',nguoisua='"+this.userId+"',ngayhoadon='"+this.NgayHoaDon+"',loaitrahang="+this.loaitra+",loaiEO="+this.loaihd+",doituong="+this.doituong+",kbh_fk="+this.kbhId+" where pk_seq="+this.id;
			}
			else
			{
				query ="update  Erp_HangTraLaiNpp set npp_fk="+this.nppId +", npptra_fk="+this.makh+" ,"+"khachhang_fk=null,"+"ngaytra='"+this.ngayyeucau+"',ghichu='"+this.ghichu+"',kho_fk="+this.khoXuatId+",sohoadon='"+this.SoHoaDon+"',kyhieu='"+this.KyHieu+"',ngaysua='"+this.getDateTime()+"',nguoisua='"+this.userId+"',ngayhoadon='"+this.NgayHoaDon+"',loaitrahang="+this.loaitra+",loaiEO="+this.loaihd+",doituong="+this.doituong+",kbh_fk="+this.kbhId+" where pk_seq="+this.id;
			}
			 System.out.println("vao day :"+query);
				if(!db.update(query))
				{
					this.msg = "Không thể tạo mới Erp_HangTraLaiNpp " + query;
					db.getConnection().rollback();
					return false;
				}
				
			for(int i = 0; i < spMa.length; i++)
			{
				
				
				if(spMa[i].trim().length() > 0 && spSoluong[i].trim().length() > 0 )
				{
					
					if(spSolo[i].trim().length()<=0||spNgayHetHan[i].length()<=0)
					{
						spSolo[i]="NA";
						spNgayHetHan[i]="2015-12-31";
						
					}
					
					System.out.println(spDonvi[i].trim()+"---"+ spSoluong[i].replaceAll(",", "")+"-----------"+spGianhap[i].replaceAll(",", "") );
					query = 
				"	insert into Erp_HangTraLaiNpp_SanPham(HangTraLai_fk,Sanpham_fk,Dvdl_Fk,SoLuong,DonGia,SoLo,NgayHetHan,GhiChu) "+
				"	select '"+this.id+"' as id,sp.pk_seq as sp,  "+
				"		ISNULL( ( select pk_Seq from DONVIDOLUONG where donvi = N'" + spDonvi[i].trim() + "' ), DVDL_FK ), '" + spSoluong[i].replaceAll(",", "") + "' as SoLuong,'" + spGianhap[i].replaceAll(",", "") + "' as DonGia,'"+spSolo[i].trim()+"' as SoLo,'"+spNgayHetHan[i].trim()+"' as NgayHetHan,'"+spGhiChu[i]+"' "+
				"	from SANPHAM sp left join NGANHHANG nh on nh.PK_SEQ=sp.NGANHHANG_FK " +
				" where sp.ma='"+spMa[i]+"'   ";
					
					System.out.println("3.Insert CK - SP: " + query);
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi Erp_HangTraLaiNpp_SanPham: " + query;
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
			e.printStackTrace();
			return false;
		}
		
		return true;
	}


	public void createRs() 
	{
		this.getNppInfo();
		System.out.println("---NPP ID: " + this.nppId);
		
		String query = "select PK_SEQ, TEN from KHO where trangthai = '1' and pk_seq in " + this.util.quyen_kho(this.userId)  ;
		this.khoXuatRs = db.get(query);
		
		 query = "select PK_SEQ, MAFAST + '-' + TEN as TEN from KHACHHANG where TRANGTHAI = '1' and npp_fk ='"+ this.nppId +"' " ;
		this.khRs = db.get(query);
		
		query = "select PK_SEQ, MAFAST + '-' + TEN as TEN from NHAPHANPHOI where TRANGTHAI = '1' and  tructhuoc_fk ='"+ this.nppId +"' " ;
		System.out.println("Nha pp : "+query);
		this.dtRs = db.get(query);
		
		this.dvtRs = db.getScrol("select PK_SEQ, DONVI from DONVIDOLUONG where trangthai = '1' ");
		
		//CHECK DUNG CHUNG KENH HAY KHONG
		query =  "select isnull(dungchungkenh, 0) as dungchungkenh from NHAPHANPHOI " +
				"where pk_seq = ( select npp_fk from ERP_CHUYENKHONPP where pk_seq = '" + id + "' )";
		ResultSet rsKENH = db.get(query);
		String dungchungKENH = "0";
		if(rsKENH != null)
		{
			try 
			{
				if(rsKENH.next())
				{
					dungchungKENH = rsKENH.getString("dungchungkenh");
				}
				rsKENH.close();
			} 
			catch (Exception e) {e.printStackTrace(); }
		}
		
		if(dungchungKENH.equals("1"))
			this.kbhRs = db.getScrol("select PK_SEQ, TEN from KENHBANHANG where trangthai = '1' and pk_seq = '100025' ");
		else
			this.kbhRs = db.getScrol("select PK_SEQ, TEN from KENHBANHANG where trangthai = '1' ");
		if((this.loaitra.equals("1")||this.loaitra.equals("0")) && !this.doituong.equals(""))
		{
			if(this.loaihd.equals("0") )
			{
				String sql="select c.MA,c.TEN,b.DONVI,b.SOLUONG,b.DONGIA,b.SOLO,b.NGAYHETHAN,(b.DONGIA*b.SOLUONG) as thanhtien,a.khachhang_fk   \n "+      
						   " from HOADON a inner join HOADON_SP_CHITIET b   \n "+      
						   "on a.PK_SEQ=b.HOADON_FK   \n "+      
						   "inner join SANPHAM c on c.MA=b.MA   \n "+      
						   "where a.PK_SEQ='"+this.dhid+"'   \n "+      
						   "union all   \n "+      
						   "select d.ma,d.TEN,c.DONVI,b.SOLUONG,b.DONGIA,b.SOLO,b.NGAYHETHAN,(b.DONGIA*b.SOLUONG) as thanhtien   \n "+      
						   " from HOADON a inner join HOADON_CTKM_TRAKM_CHITIET b   \n "+      
						   "on a.PK_SEQ=b.hoadonID   \n "+      
						   "inner join HOADON_CTKM_TRAKM c on c.hoadonID=a.PK_SEQ   \n "+      
						   "inner join SANPHAM d on d.PK_SEQ=b.sanpham_fk   \n "+      
						   "where a.PK_SEQ='"+this.dhid+"'   \n" ;
				this.spRs=db.get(sql);
				System.out.println("OTC :::::"+sql);
				sql="select  pk_seq from hoadon where   NGAYXUATHD='"+this.NgayHoaDon+"'  and khachhang_fk in (select pk_seq from khachhang where tructhuoc_fk="+this.nppId+")";
				this.loaihdRs=db.get(sql);
				
				sql="select pk_seq , sohoadon,kyhieu,(select ten from khachhang where pk_seq=khachhang_fk) ten,khachhang_fk as makh from hoadon where pk_seq="+this.dhid;
				this.infohd=db.get(sql);
				this.doituong="1";
			}
			else
			{
				String sql="select c.MA,c.TEN,b.DONVI,b.SOLUONG,b.DONGIA,b.SOLO,b.NGAYHETHAN,(b.DONGIA*b.SOLUONG) as thanhtien,isnull(a.khachhang_fk,a.npp_dat_fk) as khachhang   \n "+      
						   " from ERP_HOADONNPP a inner join ERP_HOADONNPP_SP_CHITIET b   \n "+      
						   "on a.PK_SEQ=b.HOADON_FK   \n "+      
						   "inner join SANPHAM c on c.MA=b.MA   \n "+      
						   "where a.PK_SEQ='"+this.dhid+"'   \n ";
				this.spRs=db.get(sql);
				System.out.println("ETC :::::"+sql);
				if(this.doituong.equals("1"))
				sql="select  pk_seq from ERP_hoadonnpp where  NGAYXUATHD='"+this.NgayHoaDon+"'  and khachhang_fk in (select pk_seq from khachhang where tructhuoc_fk="+this.nppId+") ";
				else
					sql="select  pk_seq from ERP_hoadonnpp where  NGAYXUATHD='"+this.NgayHoaDon+"'  and npp_dat_fk in (select pk_seq from nhaphanphoi where tructhuoc_fk="+this.nppId+") ";
					
				System.out.println("::::::::::::::::::::::::::"+sql);
				this.loaihdRs=db.get(sql);
				if(this.doituong.equals("1"))
				sql="select pk_seq , sohoadon,kyhieu,(select ten from khachhang where pk_seq=khachhang_fk) ten,khachhang_fk as makh from ERP_hoadonnpp where pk_seq="+this.dhid;
				else
					sql="select pk_seq , sohoadon,kyhieu,(select ten from nhaphanphoi where pk_seq=npp_dat_fk) ten,npp_dat_fk as makh from ERP_hoadonnpp where pk_seq="+this.dhid;
				this.infohd=db.get(sql);
			}
		}
		
	}

	private void initSANPHAM() 
	{
		String query =  
			"	select c.MA as spMa,c.TEN as spTEN,d.DIENGIAI as spDonVi,b.SoLuong,b.SoLo as spSoLo,b.ngayhethan as spNGAYHETHAN ,round(b.SoLuong*b.DonGia,0)/b.SoLuong as dongia,isnull(b.GhiChu,'') as spGhiChu,b.vat as spVat  "+
			"		from Erp_HangTraLaiNpp a inner join Erp_HangTraLaiNpp_SanPham b on b.HangTraLai_fk=a.pk_seq  "+
			"			inner join SANPHAM c on c.PK_SEQ=b.Sanpham_fk    "+
			"			inner join DONVIDOLUONG d on d.PK_SEQ=b.Dvdl_Fk   "+
			"		where a.pk_seq='"+this.id+"' ";
		
		System.out.println("___"+query);
		ResultSet spRs = db.get(query);
		NumberFormat formater = new DecimalFormat("##,###,###.####");
		if(spRs != null)
		{
			try 
			{
				String spMA = "";
				String spTEN = "";
				String spDONVI = "";
				String spSOLUONG = "";
				String spGIANHAP = "";
				String spTONKHO = "";
				
				String spSOLO = "";
				String spNGAYHETHAN = "";
				String spGHICHU = "";
				String spVAT = "";
				while(spRs.next())
				{
					spMA += spRs.getString("spMa") + "__";
					spTEN += spRs.getString("spTEN") + "__";
					spDONVI += spRs.getString("spDonVi") + "__";
					spSOLUONG += formater.format(spRs.getDouble("SOLUONG")) + "__";
					spGIANHAP += formater.format(spRs.getDouble("DONGIA")) + "__";
					System.out.println("don gia "+spRs.getDouble("DONGIA"));
					spSOLO += spRs.getString("spSOLO") + "__";
					spNGAYHETHAN += spRs.getString("spNGAYHETHAN") + "__";
					spGHICHU += spRs.getString("spGHICHU") + "__";
					spVAT += spRs.getString("spVat") + "__";
					
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
					
					spGIANHAP = spGIANHAP.substring(0, spGIANHAP.length() - 2);
					this.spGianhap = spGIANHAP.split("__");
					
					spSOLO = spSOLO.substring(0, spSOLO.length() - 2);
					this.spSolo = spSOLO.split("__");
					
					spNGAYHETHAN = spNGAYHETHAN.substring(0, spNGAYHETHAN.length() - 2);
					this.spNgayHetHan = spNGAYHETHAN.split("__");
					
					spGHICHU = spGHICHU.substring(0, spGHICHU.length() - 2);
					this.spGhiChu = spGHICHU.split("__");
					
					spVAT = spVAT.substring(0, spVAT.length() - 2);
					this.spVat = spVAT.split("__");
					
				}
			} 
			catch (Exception e) 
			{
				System.out.println("115.Exception: " + e.getMessage());
				e.printStackTrace();
			}
		}
		
	}

	public void init() 
	{
		String query =  
			"	select loaitrahang,loaiEO,fromhd,ngaytra,pk_seq,npp_fk,khachhang_fk,npptra_fk,doituong,ngaytra,ghichu,kbh_fk,kho_fk,SoHoaDon,KYHIEU,TrangThai,TienTruocThue,TienSauThue,TienThue,NgayHoaDon  "+
			"	from Erp_HangTraLaiNpp  "+
			"	where pk_seq='"+this.id+"' ";
		System.out.println("____INIT NHAP KHO: " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{					
					this.ghichu = rs.getString("ghichu");
					this.khoXuatId = rs.getString("kho_fk");
					
					this.kbhId = rs.getString("kbh_fk");
					this.trangthai = rs.getString("trangthai");
					this.SoHoaDon =rs.getString("SoHoaDon")== null ? "":rs.getString("SoHoaDon");
					this.KyHieu =rs.getString("KyHieu")== null ? "":rs.getString("KyHieu");
					this.NgayHoaDon =rs.getString("NgayHoaDon")== null ? "":rs.getString("NgayHoaDon");
					this.ngayyeucau =rs.getString("ngaytra")== null ? "":rs.getString("ngaytra");
					this.doituong =rs.getString("doituong")== null ? "0":rs.getString("doituong");
				
					this.khId = rs.getString("khachhang_fk")==null?"":rs.getString("khachhang_fk");
					this.dtId = rs.getString("npptra_fk")==null?"":rs.getString("npptra_fk");
					this.tienvat=rs.getString("tienthue")==null?"":rs.getString("tienthue");
					this.loaitra=rs.getString("loaitrahang")==null?"":rs.getString("loaitrahang");
					this.loaihd=rs.getString("loaiEO")==null?"":rs.getString("loaiEO");
					this.dhid=rs.getString("fromhd")==null?"":rs.getString("fromhd");
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				System.out.println("---LOI INIT: " + e.getMessage());
				e.printStackTrace();
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
			
		}catch(Exception er)
		{
			er.printStackTrace();
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

	
	public String getKhId() {
		
		return this.khId;
	}

	
	public void setKhId(String khId) {
		
		this.khId = khId;
	}

	
	public ResultSet getKhRs() {
		
		return this.khRs;
	}

	
	public void setKhRs(ResultSet khRs) {
		
		this.khRs = khRs;
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
	
	public String getNppId() 
	{
		return this.nppId;
	}

	public void setNppId(String nppId) 
	{
		this.nppId = nppId;
	}
	
	public String getNppTen() 
	{
		return this.nppTen;
	}
	
	public void setNppTen(String nppTen) 
	{
		this.nppTen = nppTen;
	}
	
	public String getSitecode() 
	{
		return this.sitecode;
	}

	public void setSitecode(String sitecode) 
	{
		this.sitecode = sitecode;
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
	
	private void getNppInfo()
	{		
		//Phien ban moi
		geso.traphaco.distributor.util.Utility util=new geso.traphaco.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();
		//this.dangnhap = util.getDangNhap();
		this.sitecode=util.getSitecode();
	}

	
	public String getSoChungTu()
	{
		return sochungtu;
	}
	public void setSoChungTu(String sochungtu)
	{
		this.sochungtu=sochungtu;
	}

	String[] spNgayHetHan;

	public String[] getSpNgayHetHan()
	{
		return spNgayHetHan;
	}

	public void setSpNgayHetHan(String[] ngayHetHan)
    {
		this.spNgayHetHan = ngayHetHan;
    }
	
	
	public Hashtable<String, String> getSanpham_Soluong() {
		
		return this.sanpham_soluong;
	}

	
	public void setSanpham_Soluong(Hashtable<String, String> sp_soluong) {
		
		this.sanpham_soluong = sp_soluong;
	}
	
	public ResultSet getSoloTheoSp(String spMa, String tongluong)
	{
		if(this.nppId.trim().length() > 0 && this.kbhId.trim().length() > 0 )
		{
			tongluong = tongluong.replaceAll(",", "");
			//System.out.println("---TONG LUONG: " + tongluong );
			
			String query = "select AVAILABLE + ISNULL( ( select sum(soluong) from ERP_CHUYENKHONPP_SANPHAM_CHITIET where chuyenkho_fk = '" + ( this.id.trim().length() > 0 ? this.id : "-1" ) + "' and sanpham_fk = ct.sanpham_fk and solo = ct.solo  and NgayHetHan=ct.NgayHetHan ), 0 ) as AVAILABLE, NGAYHETHAN, SOLO " +
						   "from NHAPP_KHO_CHITIET ct " +
						   "where KHO_FK = '" + this.khoXuatId + "' and KBH_FK = '" + this.kbhId + "' and NPP_FK = '" + this.nppId + "' and SANPHAM_FK = ( select pk_seq from SANPHAM where ma = '" + spMa + "' ) " +
   		" and ( AVAILABLE + ISNULL( ( select sum(soluong) from ERP_CHUYENKHONPP_SANPHAM_CHITIET where chuyenkho_fk = '" + ( this.id.trim().length() > 0 ? this.id : "-1" ) + "' and sanpham_fk = ( select pk_seq from SANPHAM where ma = '" + spMa + "' ) and solo = ct.solo  and NgayHetHan=ct.NgayHetHan   ), 0 ) ) > 0 " +
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
				e.printStackTrace();
			}
			
			if(query2.trim().length() > 0)
			{
				query2 = query2.substring(0, query2.length() - 10);
				//System.out.println("---TU DONG DE XUAT BIN - LO: " + query2 );
				return db.get(query2);
			}
		}
		
		return null;
	}
	
	
	String doituong,SoHoaDon,NgayHoaDon,KyHieu;

	public String getSoHoaDon()
  {
  	return SoHoaDon;
  }

	public void setSoHoaDon(String soHoaDon)
  {
  	SoHoaDon = soHoaDon;
  }

	public String getNgayHoaDon()
  {
  	return NgayHoaDon;
  }

	public void setNgayHoaDon(String ngayHoaDon)
  {
  	NgayHoaDon = ngayHoaDon;
  }

	public String getKyHieu()
  {
  	return KyHieu;
  }

	public void setKyHieu(String kyHieu)
  {
  	KyHieu = kyHieu;
  }

	public String getDoituong()
  {
  	return doituong;
  }

	public void setDoituong(String doituong)
  {
  	this.doituong = doituong;
  }
	
	ResultSet nppRs;
	public ResultSet getNppRs() {
		
		return this.nppRs;
	}

	
	public void setNppRs(ResultSet nppRs) {
		
		this.nppRs = nppRs;
	}
	
	String[] spGhiChu;

	public String[] getSpGhiChu()
  {
  	return spGhiChu;
  }

	public void setSpGhiChu(String[] spGhiChu)
  {
  	this.spGhiChu = spGhiChu;
  }
	
	String dtId;
	ResultSet dtRs;

	@Override
  public ResultSet getDtRs()
  {
	  return this.dtRs;
  }

	@Override
  public void setDtRs(ResultSet dtRs)
  {
	 this.dtRs=nppRs; 
  }

	@Override
  public String getDtId()
  {
	  return dtId;
  }

	@Override
  public void setDtId(String dtId)
  {
	 this.dtId=dtId; 
  }
	String[] spVat;

	public String[] getSpVat()
  {
  	return spVat;
  }

	public void setSpVat(String[] spVat)
  {
  	this.spVat = spVat;
  }
	
}
