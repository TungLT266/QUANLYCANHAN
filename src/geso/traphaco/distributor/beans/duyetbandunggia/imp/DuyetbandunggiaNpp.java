package geso.traphaco.distributor.beans.duyetbandunggia.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.distributor.beans.duyetbandunggia.IDuyetbandunggiaNpp;

public class DuyetbandunggiaNpp implements IDuyetbandunggiaNpp 
{
	String userId;
	String id;
	String loaict;
	String thang;
	String quy;
	String nam;
	String diengiai;
	
	ResultSet khRs;
	String khIds;
	String kh_QuyIds;
	String kh_ckuh;
	ResultSet nppRs;
	String nppIds;
	
	ResultSet vungRs;
	String vungIds;
	ResultSet kvRs;
	String kvIds;

	String msg;

	String nppId;
	String nppTen;
	String sitecode;
	
	String duyetdunggia;
	String khongtinhthuve;
	String khongtinhdoanhso;


	ArrayList<String> ckquy;
	
	


	public ArrayList<String> getCkquy() {
		return ckquy;
	}

	public void setCkquy(ArrayList<String> ckquy) {
		this.ckquy = ckquy;
	}


	dbutils db;
	
	public DuyetbandunggiaNpp()
	{
		this.id = "";
		this.loaict = "0";
		this.thang = Integer.toString(Integer.parseInt(getDateTime().split("-")[1]));
		this.quy = getQuyHienTai();
		this.nam = getDateTime().split("-")[0];
		this.diengiai = "";
		this.msg = "";
		this.nppIds = "";
		this.khIds = "";
		
		this.duyetdunggia = "1";
		this.khongtinhthuve = "0";
		this.khongtinhdoanhso = "0";
		this.vungIds = "";
		this.kvIds = "";
		this.kh_QuyIds = "";
		this.kh_ckuh="";
		this.db = new dbutils();
	}
	
	public DuyetbandunggiaNpp(String id)
	{
		this.id = id;
		this.loaict = "0";
		this.thang = Integer.toString(Integer.parseInt(getDateTime().split("-")[1]));
		this.quy = getQuyHienTai();
		this.nam = getDateTime().split("-")[0];
		this.diengiai = "";
		this.msg = "";
		this.nppIds = "";
		this.khIds = "";
		this.kh_QuyIds = "";
		this.duyetdunggia = "1";
		this.khongtinhthuve = "0";
		this.khongtinhdoanhso = "0";
		this.kh_ckuh="";
		this.vungIds = "";
		this.kvIds = "";

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

	public String getThang() 
	{
		return this.thang;
	}
	
	public void setThang(String thang) 
	{
		this.thang = thang;
	}
	
	public String getNam() 
	{
		return this.nam;
	}
	
	public void setNam(String nam)
	{
		this.nam = nam;
	}

	public String getId() 
	{
		return this.id;
	}

	public void setId(String id) 
	{
		this.id = id;
	}

	public String getDiengiai()
	{
		return this.diengiai;
	}

	public void setDiengiai(String diengiai) 
	{
		this.diengiai = diengiai;
	}

	public String getMsg()
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	public ResultSet getExportRs(){
		try
		{
			if(this.thang.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn tháng";
				return null;
			}
			
			if(this.nam.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn năm";
				return null;
			}
			if(this.nppId.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn chi nhánh/ đối tác";
				return null;
			}
			int thangTIEP = Integer.parseInt(this.thang) + 1;
			int namTIEP = Integer.parseInt(this.nam);
			if(thangTIEP > 12)
			{
				thangTIEP = 1;
				namTIEP = Integer.parseInt(this.nam) + 1;
			}
			String thangnam = this.nam + "-" + (Integer.parseInt(this.thang) < 10? "0"+this.thang: thang);
			String query = "select ROW_NUMBER() over (order by db.TEN ASC) as row, db.maFAST, db.TEN, db.DIACHI, l.Ma, " +
							" isnull(( select SUM(tongtienavat) from HOADON " +
							"     where LOAIHOADON=0 and KHACHHANG_FK = db.PK_SEQ " +
							"    and TRANGTHAI not in (1,3,5) and NGAYXUATHD like '"+thangnam+"%' " +
							"  ),0) as DoanhSo"+
							"\n from ( " +
							"\nselect pk_seq, MAFAST, TEN, DIACHI, DIENTHOAI, xuatkhau, " +
							"\nISNULL( ( 	select count(khachhang_fk)  " +
							"\n		from DUYETTRAKHUYENMAI_DONHANG where khachhang_fk = kh.pk_seq and  duyetkm_fk in ( select pk_seq from TIEUCHITHUONGTL   " +
							"\n				where nam = ( select nam from DUYETBANDUNGGIA where pk_seq = '"+this.id+"' ) and loaict = '0' " +
							"\n		and donhang_fk in ( select pk_seq from DONHANG where KhachHANG_FK = kh.pk_seq and month(ngaynhap) = '"+thangTIEP+"'  " +
							"\n																			  and year(ngaynhap) = '"+namTIEP+"' and trangthai != 2 )   " +
							"\n				and pk_seq in ( select thuongtl_fk from TIEUCHITHUONGTL_LOAINPP where loaiNPP = ( select loaiNPP from NHAPHANPHOi where pk_seq = '"+this.nppId+"' ) ) ) ), 0 ) as dahuongKM " +
							"\nfrom KHACHHANG kh " +
							"\nwhere trangthai = '1' and NPP_FK in ( "+this.nppId+" ) and kh.KhongKyHopDong =0 " +
							"\n " +
							"\n) as db join LOAIKHACHHANG l on db.XuatKhau = l.pk_seq " +
							"\nwhere db.PK_SEQ not in (select khachhang_fk from DUYETBANDUNGGIA_KHACHHANG  " +
							"\n	where duyet_fk = '"+this.id+"' and npp_fk in ( "+this.nppId+" ))";
			System.out.print("\nQuery Excel: " + query);
			return db.get(query);
			
		} 
		catch (Exception e)
		{
			try 
			{
				db.getConnection().rollback();
				System.out.println("__EXCEPTION EXPORT: " + e.getMessage());
			} catch (SQLException e1) {}
			
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean createTctSKU( ) 
	{
		try
		{
			if(this.thang.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn tháng";
				return false;
			}
			
			if(this.nam.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn năm";
				return false;
			}
			
			//Check Scheme
			String query = "select count(*) as sodong from DUYETBANDUNGGIA where thang = '" + this.thang + "' and nam = '" + this.nam + "' and NPP_FK = '" + this.nppId + "' ";
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				int count = 0;
				if(rs.next())
				{
					count = rs.getInt("sodong");
					if(count > 0)
					{
						this.msg = "Tháng: " + this.thang + ", năm: " + this.nam + " đã có duyệt bán đúng giá";
						rs.close();
						return false;
					}
				}
				rs.close();
			}
			
			db.getConnection().setAutoCommit(false);
			
			query = "insert Duyetbandunggia(loaict, npp_fk, quy, thang, nam, diengiai, trangthai, duyetdunggia, khongtinhtienthuve, khongtinhdoanhso, ngaytao, nguoitao, ngaysua, nguoisua) " +
					"values('0', '" + this.nppId + "', '" + this.quy + "', '" + this.thang + "', '" + this.nam + "', N'" + this.diengiai + "', '0', '" + this.duyetdunggia + "', '" + this.khongtinhthuve + "', '" + this.khongtinhdoanhso + "', " +
							"'" + this.getDateTime() + "', '" + this.userId + "', '" + this.getDateTime() + "', '" + this.userId + "')";
			
			System.out.println("1.Insert: " + query);
			if(!db.update(query))
			{
				this.msg = "Khong the tao moi Duyetbandunggia: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			/*if(this.nppIds.trim().length() > 0)
			{
				query = "Insert DUYETBANDUNGGIA_NPP(duyet_fk, npp_fk) " +
						"select IDENT_CURRENT('DUYETBANDUNGGIA') as tctId, pk_seq from NHAPHANPHOI where pk_seq in (" + this.nppIds + ") ";
				
				System.out.println("4.Insert: " + query);
				if(!db.update(query))
				{
					this.msg = "3.Khong the tao moi DUYETBANDUNGGIA_NPP: " + query;
					db.getConnection().rollback();
					return false;
				}
			}*/
			
			if(this.khIds.trim().length() > 0)
			{
				query = "Insert DUYETBANDUNGGIA_KHACHHANG(duyet_fk, khachhang_fk, npp_fk) " +
						"select IDENT_CURRENT('DUYETBANDUNGGIA') as tctId, pk_seq, npp_fk from KHACHHANG where pk_seq in (" + this.khIds + ") ";
				
				System.out.println("4.Insert: " + query);
				if(!db.update(query))
				{
					this.msg = "3.Khong the tao moi DUYETBANDUNGGIA_KHACHHANG: " + query;
					db.getConnection().rollback();
					return false;
				}
			}
			
			if(this.kh_QuyIds.trim().length() > 0)
			{
				query = "Insert DUYETBANDUNGGIA_KHACHHANG_HUONGQUY(duyet_fk, khachhang_fk, npp_fk) " +
						"select IDENT_CURRENT('DUYETBANDUNGGIA') as tctId, pk_seq, npp_fk from KHACHHANG where pk_seq in (" + this.kh_QuyIds + ") ";
				
				System.out.println("4.Insert: " + query);
				if(!db.update(query))
				{
					this.msg = "3.Khong the tao moi DUYETBANDUNGGIA_KHACHHANG_HUONGQUY: " + query;
					db.getConnection().rollback();
					return false;
				}
			}
			
			if(this.kh_ckuh.trim().length() > 0)
			{
				query = "Insert DUYETBANDUNGGIA_KHACHHANG_CKUNGHO(duyet_fk, khachhang_fk, npp_fk) " +
						"select IDENT_CURRENT('DUYETBANDUNGGIA') as tctId, pk_seq, npp_fk from KHACHHANG where pk_seq in (" + this.kh_ckuh + ") ";
				
				System.out.println("4.Insert: " + query);
				if(!db.update(query))
				{
					this.msg = "3.Khong the tao moi DUYETBANDUNGGIA_KHACHHANG_CKUNGHO: " + query;
					db.getConnection().rollback();
					return false;
				}
			}
			
			/*if(this.kh_QuyIds.trim().length() > 0)
			{
				query = "update DUYETBANDUNGGIA_KHACHHANG set HUONG_CK_QUY = '0' where duyet_fk = ( select IDENT_CURRENT('DUYETBANDUNGGIA') ) " +
						"	and khachhang_fk not in ( select pk_seq from KHACHHANG where pk_seq in (" + this.kh_QuyIds + ") ) ";
				
				System.out.println("5.Insert: " + query);
				if(!db.update(query))
				{
					this.msg = "4.Khong the tao moi DUYETBANDUNGGIA_KHACHHANG: " + query;
					db.getConnection().rollback();
					return false;
				}
			}*/
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e)
		{
			try 
			{
				db.getConnection().rollback();
				System.out.println("__EXCEPTION UPDATE: " + e.getMessage());
			} 
			catch (SQLException e1) {}
			
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	public boolean updateTctSKU()
	{
		try
		{
			if(this.thang.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn tháng";
				return false;
			}
			
			if(this.nam.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn năm";
				return false;
			}
			
			String query = "select count(*) as sodong from DUYETBANDUNGGIA where thang = '" + this.thang + "' and nam = '" + this.nam + "' and npp_fk = '" + this.nppId + "' and pk_seq != '" + this.id + "' ";
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				int count = 0;
				if(rs.next())
				{
					count = rs.getInt("sodong");
					if(count > 0)
					{
						this.msg = "Tháng: " + this.thang + ", năm: " + this.nam + " đã có duyệt bán đúng giá";
						rs.close();
						return false;
					}
				}
				rs.close();
			}
			
			db.getConnection().setAutoCommit(false);
			
			query = "update Duyetbandunggia set loaict = '0', npp_fk = '" + this.nppId + "', quy = '" + this.quy + "', thang = '" + this.thang + "', nam = '" + this.nam + "', diengiai = N'" + this.diengiai + "', " +
					" duyetdunggia = '" + this.duyetdunggia + "', khongtinhtienthuve = '" + this.khongtinhthuve + "', khongtinhdoanhso = '" + this.khongtinhdoanhso + "', " +
						"ngaysua = '" + this.getDateTime() + "', nguoisua = '" + this.userId + "' " +
					"where pk_seq = '" + this.id + "'";
					
			System.out.println("1.Update: " + query);
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat Duyetbandunggia: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete DUYETBANDUNGGIA_NPP where duyet_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat DUYETBANDUNGGIA_NPP: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete DUYETBANDUNGGIA_KHACHHANG where duyet_fk = '" + this.id + "' and npp_fk in ( " + this.nppId + " ) ";
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat DUYETBANDUNGGIA_KHACHHANG: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete DUYETBANDUNGGIA_KHACHHANG_HUONGQUY where duyet_fk = '" + this.id + "' and npp_fk in ( " + this.nppId + " ) ";
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat DUYETBANDUNGGIA_KHACHHANG_HUONGQUY: " + query;
				db.getConnection().rollback();
				return false;
			}
			query = "delete DUYETBANDUNGGIA_KHACHHANG_CKUNGHO where duyet_fk = '" + this.id + "' and npp_fk in ( " + this.nppId + " ) ";
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat DUYETBANDUNGGIA_KHACHHANG_CKUNGHO: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			/*if(this.nppIds.trim().length() > 0)
			{
				query = "Insert DUYETBANDUNGGIA_NPP(duyet_fk, npp_fk) " +
						"select '" + this.id + "' as tctId, pk_seq from NHAPHANPHOI where pk_seq in (" + this.nppIds + ") ";
				
				System.out.println("3.Insert: " + query);
				if(!db.update(query))
				{
					this.msg = "3.Khong the tao moi DUYETBANDUNGGIA_NPP: " + query;
					db.getConnection().rollback();
					return false;
				}
			}*/
			
			if(this.khIds.trim().length() > 0)
			{
				query = "Insert DUYETBANDUNGGIA_KHACHHANG(duyet_fk, khachhang_fk, npp_fk) " +
						"select '" + this.id + "' as tctId, pk_seq, npp_fk from KHACHHANG where pk_seq in (" + this.khIds + ") ";
				
				System.out.println("4.Insert: " + query);
				if(!db.update(query))
				{
					this.msg = "3.Khong the tao moi DUYETBANDUNGGIA_KHACHHANG: " + query;
					db.getConnection().rollback();
					return false;
				}
			}
			
			if(this.kh_QuyIds.trim().length() > 0)
			{
				query = "Insert DUYETBANDUNGGIA_KHACHHANG_HUONGQUY(duyet_fk, khachhang_fk, npp_fk) " +
						"select '" + this.id + "' as tctId, pk_seq, npp_fk from KHACHHANG where pk_seq in (" + this.kh_QuyIds + ") ";
				
				System.out.println("4.Insert: " + query);
				if(!db.update(query))
				{
					this.msg = "3.Khong the tao moi DUYETBANDUNGGIA_KHACHHANG_HUONGQUY: " + query;
					db.getConnection().rollback();
					return false;
				}
			}
			if(this.kh_ckuh.trim().length() > 0)
			{
				query = "Insert DUYETBANDUNGGIA_KHACHHANG_CKUNGHO(duyet_fk, khachhang_fk, npp_fk) " +
						"select '" + this.id + "' as tctId, pk_seq, npp_fk from KHACHHANG where pk_seq in (" + this.kh_ckuh + ") ";
				
				System.out.println("4.Insert: " + query);
				if(!db.update(query))
				{
					this.msg = "3.Khong the tao moi DUYETBANDUNGGIA_KHACHHANG_CKUNGHO: " + query;
					db.getConnection().rollback();
					return false;
				}
			}
			
			/*if(this.kh_QuyIds.trim().length() > 0)
			{
				query = "update DUYETBANDUNGGIA_KHACHHANG set HUONG_CK_QUY = '0' where duyet_fk = '" + this.id + "' " +
						"	and khachhang_fk not in ( select pk_seq from KHACHHANG where pk_seq in (" + this.kh_QuyIds + ") ) ";
				
				System.out.println("5.Insert: " + query);
				if(!db.update(query))
				{
					this.msg = "4.Khong the tao moi DUYETBANDUNGGIA_KHACHHANG: " + query;
					db.getConnection().rollback();
					return false;
				}
			}*/
				
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e)
		{
			try 
			{
				db.getConnection().rollback();
				System.out.println("__EXCEPTION UPDATE: " + e.getMessage());
			} catch (SQLException e1) {}
			
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	public void init() 
	{
		String query = "select loaict, npp_fk, quy, thang, nam, diengiai, duyetdunggia, khongtinhtienthuve, khongtinhdoanhso  " +
					   "from DUYETBANDUNGGIA where pk_seq = '" + this.id + "'";
		
		System.out.println("__Khoi tao tieu chi thuong: " + query);
		
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					this.loaict = rs.getString("loaict");
					this.quy = rs.getString("quy");
					this.thang = rs.getString("thang");
					this.nam = rs.getString("nam");					
					this.diengiai= rs.getString("diengiai");
					this.nppId = rs.getString("npp_fk");
					this.duyetdunggia = rs.getString("duyetdunggia");
					this.khongtinhthuve = rs.getString("khongtinhtienthuve");
					this.khongtinhdoanhso = rs.getString("khongtinhdoanhso");
				}
				rs.close();
			} 
			catch (Exception e)
			{
				System.out.println("115.Error Meg: " + e.getMessage());
			}
		}
		
		this.createRs();
	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	private String getQuyHienTai() 
	{
        int thang = Integer.parseInt(this.getDateTime().split("-")[1]);
        if(1 <= thang && thang <= 3)
        	return "1";
        else if(4 <= thang && thang <= 6)
        	return "2";
        else if(7 <= thang && thang <= 9)
        	return "3";
        else 
        	return "4";
	}

	public void createRs()
	{
		this.getNppInfo();
		String condition="";
		String condition1="";
	
	/*	if(this.ckquy.size()>0)
		{
			//condition=" and kh.mafast not in (select mafast from khachhang where mafast in ("+this.mang+") )";
			//condition1=" and khachhang_fk not in (select pk_seq from khachhang where mafast in ("+this.mang+") )";
		}*/
		String query = "";
		if(this.nppId.trim().length() > 0)
		{
			//LAY THANG TIEP THEO CO DON HANG THI KO DC BO RA
			int thangTIEP = Integer.parseInt(this.thang) + 1;
			int namTIEP = Integer.parseInt(this.nam);
			if(thangTIEP > 12)
			{
				thangTIEP = 1;
				namTIEP = Integer.parseInt(this.nam) + 1;
			}
			
			/*query = "select pk_seq, MAFAST, TEN, DIACHI, DIENTHOAI, " +
					"	ISNULL( ( 	select count(khachhang_fk)  " +
					"		from DUYETTRAKHUYENMAI_DONHANG where khachhang_fk = kh.pk_seq and  duyetkm_fk in ( select pk_seq from TIEUCHITHUONGTL  " +
					"				where nam = ( select nam from DUYETBANDUNGGIA where pk_seq = '" + this.id + "' ) and loaict = '0' " +
							"		and donhang_fk in ( select pk_seq from DONHANG where KhachHANG_FK = kh.pk_seq and month(ngaynhap) = ( select thang from DUYETBANDUNGGIA where pk_seq = '" + this.id + "' ) " +
									"																			  and year(ngaynhap) = ( select nam from DUYETBANDUNGGIA where pk_seq = '" + this.id + "' ) and trangthai != 2 )  " +
					"				and pk_seq in ( select thuongtl_fk from TIEUCHITHUONGTL_LOAINPP where loaiNPP = ( select loaiNPP from NHAPHANPHOi where pk_seq = '" + this.nppId + "' ) ) ) ), 0 ) as dahuongKM " +
					"from KHACHHANG kh " +
					"where trangthai = '1' and NPP_FK in ( " + this.nppId + " ) order by TEN ASC ";*/
			
			query = "select pk_seq, MAFAST, TEN, DIACHI, DIENTHOAI,isnull(kh.tenkyhd,'') as tenkyhd ,isnull(kh.mahd,'') as mahd," +
					"	ISNULL( ( 	select count(khachhang_fk)  " +
					"		from DUYETTRAKHUYENMAI_DONHANG where khachhang_fk = kh.pk_seq and  duyetkm_fk in ( select pk_seq from TIEUCHITHUONGTL  " +
					"				where nam = ( select nam from DUYETBANDUNGGIA where pk_seq ='" + ( this.id.trim().length() > 0 ? this.id : "-1" ) + "' ) and loaict = '0' " +
							"		and donhang_fk in ( select pk_seq from DONHANG where KhachHANG_FK = kh.pk_seq and month(ngaynhap) = '" + thangTIEP + "' " +
									"																			  and year(ngaynhap) = '" + namTIEP + "' and trangthai != 2 )  " +
					"				and pk_seq in ( select thuongtl_fk from TIEUCHITHUONGTL_LOAINPP where loaiNPP = ( select loaiNPP from NHAPHANPHOi where pk_seq = '" + this.nppId + "' ) ) ) ), 0 ) as dahuongKM " +
					"from KHACHHANG kh " +
					"where trangthai = '1' and kh.kbh_fk=100025 and NPP_FK in ( " + this.nppId + " ) " +" and kh.KhongKyHopDong =0 "+ condition+
					"   order by TEN ASC ";
			
			System.out.println("---LAY KHACH HANG: " + query);
			this.khRs = db.get(query);
			
			if(this.id.trim().length() > 0 && ( this.khIds.trim().length() <= 0 || this.kh_QuyIds.trim().length() <= 0 ) )
			{
				//INIT NHUNG KHACH HANG DA DUOC DUYET CUA NPP NAY
				query = "select khachhang_fk, npp_fk, HUONG_CK_QUY from DUYETBANDUNGGIA_KHACHHANG " +
						"where duyet_fk = '" + this.id + "' and npp_fk in ( " + this.nppId + " )  " +condition1;
				System.out.println("___KHOI TAO NPP: " + query);
				
				ResultSet rs = db.get(query);
				if(rs != null)
				{
					try 
					{
						String khId = "";
						//String kh_QuyId = "";
						while(rs.next())
						{
							khId += rs.getString("khachhang_fk") + ",";
							
							/*if(rs.getString("HUONG_CK_QUY").equals("1"))
								kh_QuyId += rs.getString("khachhang_fk") + ",";*/
						}
						rs.close();
						
						if(khId.trim().length() > 0)
						{
							this.khIds = khId.substring(0, khId.length() - 1);
							//this.kh_QuyIds = kh_QuyId.substring(0, kh_QuyId.length() - 1);
						}
					} 
					catch (Exception e) 
					{
						System.out.println("33.Loi khoi tao: " + e.toString());
						e.printStackTrace();
					}
				}
				
				
				//INIT NHUNG KHACH HANG DA DUOC DUYET CUA NPP NAY
				query = "select khachhang_fk, npp_fk from DUYETBANDUNGGIA_KHACHHANG_HUONGQUY " +
						"where duyet_fk = '" + this.id + "' and npp_fk in ( " + this.nppId + " )  "+condition1;
				System.out.println("___KHOI TAO NPP: " + query);
				
				rs = db.get(query);
				if(rs != null)
				{
					try 
					{
						String kh_QuyId = "";
						while(rs.next())
						{
							kh_QuyId += rs.getString("khachhang_fk") + ",";
						}
						rs.close();
						
						if(kh_QuyId.trim().length() > 0)
						{
							this.kh_QuyIds = kh_QuyId.substring(0, kh_QuyId.length() - 1);
						}
					} 
					catch (Exception e) 
					{
						System.out.println("44.Loi khoi tao: " + e.toString());
						e.printStackTrace();
					}
				}
				
				//INIT NHUNG KHACH HANG DA DUOC DUYET ck ung ho CUA NPP NAY
				query = "select khachhang_fk, npp_fk from DUYETBANDUNGGIA_KHACHHANG_CKUNGHO " +
						"where duyet_fk = '" + this.id + "' and npp_fk in ( " + this.nppId + " )  "+condition1;
				System.out.println("___KHOI TAO NPP: " + query);
				
				rs = db.get(query);
				if(rs != null)
				{
					try 
					{
						String kh_uh = "";
						while(rs.next())
						{
							kh_uh += rs.getString("khachhang_fk") + ",";
						}
						rs.close();
						
						if(kh_uh.trim().length() > 0)
						{
							this.kh_ckuh = kh_uh.substring(0, kh_uh.length() - 1);
						}
					} 
					catch (Exception e) 
					{
						System.out.println("44.Loi khoi tao: " + e.toString());
						e.printStackTrace();
					}
				}
				
				
			}
		}
		
	}
	
	public void setNppRs(ResultSet nppRs) {
		
		this.nppRs = nppRs;
	}

	
	public ResultSet getNppRs() {
		
		return this.nppRs;
	}

	
	public String getNppIds() {
		
		return this.nppIds;
	}

	
	public void setNppIds(String nppIds) {
		
		this.nppIds = nppIds;
	}


	public void setVungRs(ResultSet vungRs) {
		
		this.vungRs = vungRs;
	}

	
	public ResultSet getVungRs() {
		
		return this.vungRs;
	}

	
	public String getVungIds() {
		
		return this.vungIds;
	}

	
	public void setVungIds(String vungIds) {
		
		this.vungIds = vungIds;
	}

	
	public void setKhuvucRs(ResultSet kvRs) {
		
		this.kvRs = kvRs;
	}

	
	public ResultSet getKhuvucRs() {
		
		return this.kvRs;
	}

	
	public String getKhuvucIds() {
		
		return this.kvIds;
	}

	
	public void setKhuvucIds(String kvIds) {
		
		this.kvIds = kvIds;
	}

	
	public String getLoaict() {
		
		return this.loaict;
	}

	
	public void setLoaict(String loaict) {
		
		this.loaict = loaict;
	}

	
	public String getQuy() {
		
		return this.quy;
	}

	
	public void setQuy(String quy) {
		
		this.quy = quy;
	}
	
	public void setKhRs(ResultSet khRs) {
		
		this.khRs = khRs;
	}

	
	public ResultSet getKhRs() {
		
		return this.khRs;
	}

	
	public String getKhIds() {
		
		return this.khIds;
	}

	
	public void setKhIds(String khIds) {
		
		this.khIds = khIds;
	}
	
	private void getNppInfo()
	{		
		geso.traphaco.distributor.util.Utility util=new geso.traphaco.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();
		this.sitecode=util.getSitecode();
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

	
	public void init_display() 
	{
		String query =  "select loaict, npp_fk, quy, thang, nam, diengiai, duyetdunggia, khongtinhtienthuve, khongtinhdoanhso  " +
				   		"from DUYETBANDUNGGIA where pk_seq = '" + this.id + "'";
		
		System.out.println("__Khoi tao tieu chi thuong: " + query);
		
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					this.loaict = rs.getString("loaict");
					this.quy = rs.getString("quy");
					this.thang = rs.getString("thang");
					this.nam = rs.getString("nam");					
					this.diengiai= rs.getString("diengiai");
					this.nppId = rs.getString("npp_fk");
					
					this.duyetdunggia = rs.getString("duyetdunggia");
					this.khongtinhthuve = rs.getString("khongtinhtienthuve");
					this.khongtinhdoanhso = rs.getString("khongtinhdoanhso");
				}
				rs.close();
			} 
			catch (Exception e)
			{
				System.out.println("115.Error Meg: " + e.getMessage());
			}
		}
		
		if(this.nppId.trim().length() > 0)
		{
			//LAY THANG TIEP THEO CO DON HANG THI KO DC BO RA
			int thangTIEP = Integer.parseInt(this.thang) + 1;
			int namTIEP = Integer.parseInt(this.nam);
			if(thangTIEP > 12)
			{
				thangTIEP = 1;
				namTIEP = Integer.parseInt(this.nam) + 1;
			}
			else
			{
				thangTIEP = Integer.parseInt(this.thang) + 1;
				namTIEP = Integer.parseInt(this.nam);
			}
			
			query = "select pk_seq, MAFAST, TEN, DIACHI, DIENTHOAI, isnull(kh.mahd,'') as mahd,isnull(kh.tenkyhd,'') as tenkyhd," +
					"	ISNULL( ( 	select count(khachhang_fk)  " +
					"		from DUYETTRAKHUYENMAI_DONHANG where khachhang_fk = kh.pk_seq and  duyetkm_fk in ( select pk_seq from TIEUCHITHUONGTL  " +
					"				where  ISNULL(substring(NgayThang,6,2),'"+thangTIEP+"')='"+thangTIEP+"'  and  ROUND(thanhtoan,0)	>1 and  nam = ( select nam from DUYETBANDUNGGIA where pk_seq = '" + this.id + "' ) and loaict = '0' " +
							"		and donhang_fk in ( select pk_seq from DONHANG where KhachHANG_FK = kh.pk_seq and month(ngaynhap) = ( " + thangTIEP + " ) " +
									"																			  and year(ngaynhap) = ( " + namTIEP + " ) and trangthai != 2 )  " +
					"				and pk_seq in ( select thuongtl_fk from TIEUCHITHUONGTL_LOAINPP where loaiNPP = ( select loaiNPP from NHAPHANPHOi where pk_seq = '" + this.nppId + "' ) ) ) ), 0 ) as dahuongKM " +
					"from KHACHHANG kh " +
					"where trangthai = '1' and kh.kbh_fk=100025 and NPP_FK in ( " + this.nppId + " ) " + "and kh.KhongKyHopDong =0 " +
					"order by TEN ASC ";
			
			System.out.println("---LAY KHACH HANG: " + query);
			this.khRs = db.get(query);
			
			if(this.id.trim().length() > 0 && this.khIds.trim().length() <= 0 )
			{
				//INIT NHUNG KHACH HANG DA DUOC DUYET CUA NPP NAY
				query = "select khachhang_fk, npp_fk, HUONG_CK_QUY from DUYETBANDUNGGIA_KHACHHANG " +
						"where duyet_fk = '" + this.id + "' and npp_fk in ( " + this.nppId + " ) ";
				System.out.println("___KHOI TAO NPP: " + query);
				
				rs = db.get(query);
				if(rs != null)
				{
					try 
					{
						String khId = "";
						while(rs.next())
						{
							khId += rs.getString("khachhang_fk") + ",";
						}
						rs.close();
						
						if(khId.trim().length() > 0)
						{
							this.khIds = khId.substring(0, khId.length() - 1);
						}
					} 
					catch (Exception e) 
					{
						System.out.println("33.Loi khoi tao: " + e.toString());
						e.printStackTrace();
					}
				}
				
				//INIT NHUNG KHACH HANG DA DUOC DUYET CUA NPP NAY
				query = "select khachhang_fk, npp_fk from DUYETBANDUNGGIA_KHACHHANG_HUONGQUY " +
						"where duyet_fk = '" + this.id + "' and npp_fk in ( " + this.nppId + " ) ";
				System.out.println("___KHOI TAO NPP: " + query);
				
				rs = db.get(query);
				if(rs != null)
				{
					try 
					{
						String kh_QuyId = "";
						while(rs.next())
						{
							kh_QuyId += rs.getString("khachhang_fk") + ",";
						}
						rs.close();
						
						if(kh_QuyId.trim().length() > 0)
						{
							this.kh_QuyIds = kh_QuyId.substring(0, kh_QuyId.length() - 1);
						}
					} 
					catch (Exception e) 
					{
						System.out.println("44.Loi khoi tao: " + e.toString());
						e.printStackTrace();
					}
				}
				
				//INIT NHUNG KHACH HANG DA DUOC DUYET CUA NPP NAY
				query = "select khachhang_fk, npp_fk from DUYETBANDUNGGIA_KHACHHANG_CKUNGHO " +
						"where duyet_fk = '" + this.id + "' and npp_fk in ( " + this.nppId + " ) ";
				System.out.println("___KHOI TAO NPP: " + query);
				
				rs = db.get(query);
				if(rs != null)
				{
					try 
					{
						String kh_ud = "";
						while(rs.next())
						{
							kh_ud += rs.getString("khachhang_fk") + ",";
						}
						rs.close();
						
						if(kh_ud.trim().length() > 0)
						{
							this.kh_ckuh = kh_ud.substring(0, kh_ud.length() - 1);
						}
					} 
					catch (Exception e) 
					{
						System.out.println("44.Loi khoi tao: " + e.toString());
						e.printStackTrace();
					}
				}
				
			}
		}
			
	}


	public boolean duyetTctSKU()
	{

		return false;
	}

	
	public String getDuyetdunggia() {
		
		return this.duyetdunggia;
	}

	
	public void setDuyetdunggia(String duyetdunggia) {
		
		this.duyetdunggia = duyetdunggia;
	}

	
	public String getKhongtinhtienthuve() {
		
		return this.khongtinhthuve;
	}

	
	public void setKhongtinhtienthuve(String khongtinhthuve) {
		
		this.khongtinhthuve = khongtinhthuve;
	}

	
	public String getKhongtinhdoanhso() {
		
		return this.khongtinhdoanhso;
	}

	
	public void setKhongtinhdoanhso(String khongtinhdoanhso) {
		
		this.khongtinhdoanhso = khongtinhdoanhso;
	}

	
	public String getKh_QuyIds() {

		return this.kh_QuyIds;
	}


	public void setKh_QuyIds(String kh_QuyIds) {
		
		this.kh_QuyIds = kh_QuyIds;
	}
	
	public String getKh_ckuh() {
		return kh_ckuh;
	}

	public void setKh_ckuh(String kh_ckuh) {
		this.kh_ckuh = kh_ckuh;
	}


}
