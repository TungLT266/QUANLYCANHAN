package geso.traphaco.erp.beans.khoasothang.imp;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.beans.khoasothang.IErpKhoasothang;

public class ErpKhoaSoThang implements IErpKhoasothang 
{
	String userId;
	String thang;
	String nam;
	
	String thangKSGannhat;
	String namKSGannhat;
	
	ResultSet chungtuRs;
	ResultSet amkhoRs;
	
	String msg;
	dbutils db; 
	
	public ErpKhoaSoThang()
	{
		this.thang = "";
		this.nam = "";
		this.msg = "";
		this.thangKSGannhat = "";
		this.namKSGannhat = "";
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

	public ResultSet getChungtuRs() 
	{
		return this.chungtuRs;
	}

	public void setChungtuRs(ResultSet ctRs) 
	{
		this.chungtuRs = ctRs;
	}
	
	public ResultSet getAmkhoRs() 
	{
		return this.amkhoRs;
	}

	public void setAmkhoRs(ResultSet amkhoRs)
	{
		this.amkhoRs = amkhoRs;
	}
	
	public String getMsg()
	{
		return this.msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}

	String congtyId;
	String nppId;
	
	public String getCongtyId() {
		
		return this.congtyId;
	}

	public void setCongtyId(String congtyId) {
		
		this.congtyId = congtyId;
	}

	public String getNppId() {

		return this.nppId;
	}

	public void setNppId(String nppId) {
		
		this.nppId = nppId;
	}

	public void Init() 
	{
		String query = "SELECT TOP 1 THANGKS, NAM FROM ERP_KHOASOTHANG  order by NAM desc, THANGKS desc";
		ResultSet rs = db.get(query);

		String thangKsMax = "";
		String namKsMax = "";

		try
		{
			while(rs.next())
			{
				thangKsMax = rs.getString("THANGKS");
				namKsMax = rs.getString("NAM"); 
				
				this.thangKSGannhat = thangKsMax;
				this.namKSGannhat = namKsMax;
	
				if(thangKsMax.equals("12"))
				{
					this.thang = "1";
					this.nam = Integer.toString(Integer.parseInt(namKsMax) + 1);
				}
				else
				{
					this.thang = Integer.toString(Integer.parseInt(thangKsMax) + 1);
					this.nam = namKsMax;
				}
			}
			rs.close();
		}
		catch(Exception ex){ }
		
	}
	
	public void createRs() 
	{
		try 
		{
			this.congtyId = "100000";
			String query = "SELECT TOP 1 THANGKS, NAM FROM ERP_KHOASOTHANG order by NAM desc, THANGKS desc";
			ResultSet rs = db.get(query);
			
			try
			{
				while(rs.next())
				{
					this.thangKSGannhat = rs.getString("THANGKS");
					this.namKSGannhat = rs.getString("NAM");
				}
				rs.close();
			}
			catch(Exception ex){ }
			
			//Check chuyển kho chưa nhận
			query = "select a.PK_SEQ, a.NgayChuyen, b.TEN as khochuyen, c.ten as khonhan  "+
					"from ERP_CHUYENKHO a inner join ERP_KHOTT b on a.KhoXuat_FK = b.PK_SEQ "+
					"					inner join ERP_KHOTT c on a.KhoNhan_FK = c.PK_SEQ "+
					"where a.TRANGTHAI = '1' and a.noidungxuat_fk != '100073' and month( a.NgayChuyen ) = '" + this.thang + "' and YEAR( a.NgayChuyen ) = '" + this.nam + "' and a.khonhan_fk is not null " + 
					"order by a.NgayChuyen asc	";
			
			System.out.println(":: CHUA NHAN HANG: " + query);
			this.chungtuRs = db.get(query);
			
			//Check âm kho
			String tungay = this.GetTuNgay( this.thang, this.nam );
			String denngay = this.GetDenNgay( this.thang, this.nam );
			
//			query = "select d.ma as masanpham, d.TEN as tensanpham, sum( dauky + nhap - xuat ) as toncuoi  "+
//					"from dbo.UFN_NXT_HO_FULL( '" + tungay + "', '" + denngay + "' ) a  "+
//					"		inner join ERP_SANPHAM d on a.sanpham_fk = d.pk_seq "+
//					"group by d.ma, d.TEN	" +
//					"having sum( dauky + nhap - xuat ) < 0	";
//			
//			System.out.println(":: AM KHO: " + query);
//			this.amkhoRs = db.get(query);
			
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Exception: " + e.getMessage());
		}
	}

	private String GetDenNgay(String thang, String nam) 
	{
		String ngay = LastDayOfMonth(Integer.parseInt(thang), Integer.parseInt(nam) );
		
		if( thang.trim().length() < 2 )
			thang = "0" + thang;

		return nam + "-" + thang + "-" + ngay;
	}

	private String GetTuNgay(String thang, String nam) 
	{
		if( thang.trim().length() < 2 )
			thang = "0" + thang;
		
		return nam + "-" + thang + "-01";
	}

	public boolean KhoaSoThang() 
	{
		try 
		{
			String query = "SELECT TOP 1 THANGKS, NAM FROM ERP_KHOASOTHANG  order by NAM desc, THANGKS desc";
			System.out.println(":: CHECK KS: " + query);
			ResultSet rs = db.get(query);

			String thangKsMax = "";
			String namKsMax = "";
			
			String _thang = "";
			String _nam = "";

			while(rs.next())
			{
				thangKsMax = rs.getString("THANGKS");
				namKsMax = rs.getString("NAM"); 
				
				this.thangKSGannhat = thangKsMax;
				this.namKSGannhat = namKsMax;
				
				if(thangKsMax.equals("12"))
				{
					_thang = "1";
					_nam = Integer.toString(Integer.parseInt(namKsMax) + 1);
				}
				else
				{
					_thang = Integer.toString(Integer.parseInt(thangKsMax) + 1);
					_nam = namKsMax;
				}
			}
			rs.close();
			
			if( !_thang.equals(this.thang) || !_nam.equals(this.nam) )
			{
				this.msg = "Tháng, năm khóa sổ không hợp lệ. Vui lòng kiểm tra lại";
				return false;
			}
			
			//Check chuyển kho chưa nhận
			/*query = "select count(*) as sodong  "+
					"from ERP_CHUYENKHO a inner join ERP_KHOTT b on a.KhoXuat_FK = b.PK_SEQ "+
					"					inner join ERP_KHOTT c on a.KhoNhan_FK = c.PK_SEQ "+
					"where a.TRANGTHAI = '1' and a.noidungxuat_fk != '100073' and a.khonhan_fk is not null and month( a.NgayChuyen ) = '" + this.thang + "' and YEAR( a.NgayChuyen ) = '" + this.nam + "' ";
			
			System.out.println(":: CHUA NHAN HANG: " + query);
			rs = db.get(query);
			if( rs != null )
			{
				int sodong = 0;
				if( rs.next() )
				{
					sodong = rs.getInt("sodong");
					rs.close();
				}
				
				if( sodong > 0 )
				{
					this.msg = "Vui lòng kiểm tra lại các chứng từ chưa nhận hàng";
					return false;
				}
			}*/
			
			String tungay = this.GetTuNgay( this.thang, this.nam );
			String denngay = this.GetDenNgay( this.thang, this.nam );
			
			//TAM BO CHECK AM KHO
			/*query = "select count(*)  "+
					"from dbo.UFN_NXT_HO_FULL(  '" + tungay + "', '" + denngay + "' ) a  "+
					"		inner join ERP_SANPHAM d on a.sanpham_fk = d.pk_seq "+
					"having sum( dauky + nhap - xuat ) < 0	";
			
			System.out.println("::: CHECK AM KHO: " + query);
			rs = db.get(query);
			if( rs != null )
			{
				int sodong = 0;
				if( rs.next() )
				{
					sodong = rs.getInt("sodong");
					rs.close();
				}
				
				if( sodong > 0 )
				{
					this.msg = "Vui lòng kiểm tra lại các mặt hàng âm kho";
					return false;
				}
			}*/
			

			db.getConnection().setAutoCommit(false);
			
			query = "insert ERP_KHOASOTHANG( THANGKS, NAM, trangthai,congty_fk ) " + 
					"select '" + _thang + "', '" + _nam + "', '1',"+this.congtyId+" ";
			
			System.out.println("::: KHOA SO THANG: " + query);
			if( !db.update(query) )
			{
				this.msg = "Lỗi khóa sổ: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			//Chèn tồn kho tháng
			query = "insert ERP_TONKHOTHANG_CHITIET( KHOTT_FK, SANPHAM_FK, BIN_FK, SOLO, NGAYHETHAN, NGAYNHAPKHO, MATHUNG, MAME, MARQ, MAPHIEU, MAPHIEUDINHTINH, PHIEUEO, HAMLUONG, HAMAM, SOLUONG, BOOKED, AVAILABLE, THANG, NAM ) "+
					"select a.KHO_FK, SANPHAM_FK, b.pk_seq as BIN_FK, SOLO, NGAYHETHAN, NGAYNHAPKHO, MATHUNG, MAME, MARQ, MAPHIEU, a.phieudt as MAPHIEUDINHTINH, PHIEUEO, HAMLUONG, HAMAM, "+
					"	dauky + nhap - xuat as soluong, 0 as booked, dauky + nhap - xuat as avai, " + _thang + ", " + _nam + " "+
					"from dbo.UFN_NXT_HO_FULL( '" + tungay + "', '" + denngay + "' ) a left join ERP_BIN b on a.vitri = b.MA and a.kho_fk = b.KHOTT_FK  " + 
					"where a.KHO_FK is not null ";
			
			System.out.println("::: CHEN TON KHO THANG: " + query);
			if( !db.update(query) )
			{
				this.msg = "Lỗi khóa sổ: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			//Kết chuyển kho cuối tháng
//			this.TaoButToan_TongHop( db, denngay, Integer.parseInt(_thang), Integer.parseInt(_nam) );
			
			//Phân bổ chi phí F3D qua đây chạy lại
//			db.execProceduce2("TinhGiaTonKho_PhanBoChiPhi", new String[] { this.congtyId, _thang, _nam } );
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
			return true;
			
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Exception: " + e.getMessage());
			db.update("rollback");
			return false;
		}

		
	}
	
	public boolean MoKhoaSoThang() 
	{
		try 
		{
			String query = "SELECT TOP 1 THANGKS, NAM FROM ERP_KHOASOTHANG order by NAM desc, THANGKS desc";
			System.out.println(":: CHECK KS: " + query);
			ResultSet rs = db.get(query);

			String thangKsMax = "";
			String namKsMax = "";
			while(rs.next())
			{
				thangKsMax = rs.getString("THANGKS");
				namKsMax = rs.getString("NAM"); 				
			}
			rs.close();
			
			if( !thangKsMax.equals(this.thang) || !namKsMax.equals(this.nam) )
			{
				this.msg = "Tháng, năm mở khóa sổ không hợp lệ. Vui lòng kiểm tra lại";
				return false;
			}

			db.getConnection().setAutoCommit(false);
			
			query = "delete ERP_KHOASOTHANG where  THANGKS = '" + this.thang + "' and NAM = '" + this.nam + "'  ";
			System.out.println("::: MO KHOA SO THANG: " + query);
			if( !db.update(query) )
			{
				this.msg = "Lỗi mở khóa sổ: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			//Chèn tồn kho tháng
			query = "delete ERP_TONKHOTHANG_CHITIET where THANG = '" + this.thang + "' and NAM = '" + this.nam + "' ";				
			System.out.println("::: MO KHOA SO THANG: " + query);
			if( !db.update(query) )
			{
				this.msg = "Lỗi mở khóa sổ: " + query;
				db.getConnection().rollback();
				return false;
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
			return true;
			
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Exception: " + e.getMessage());
			db.update("rollback");
			return false;
		}
		
	}
	
	
	public String getDate() 
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	private String LastDayOfMonth(int month, int year) 
    {
        String ngay = "";
        switch (month)
        {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                {
                    ngay = "31";
                    break;
                }
            case 4:
            case 6:
            case 9:
            case 11:
                {
                    ngay = "30";
                    break;
                }
            case 2:
                {
                    if (year % 4 == 0)
                        ngay = "29";
                    else
                        ngay = "28";
                    break;
                }
        }

        return ngay;
    }

	public void DbClose() 
	{
		try
		{
			db.shutDown();
			
			if( this.chungtuRs != null )
				this.chungtuRs = null;
		}
		catch(Exception ex){}
		
	}

	
	public String getThangKSGannhat() {
		
		return this.thangKSGannhat;
	}

	
	public void setThangKSGannhat(String thangksGannhat) {
		
		this.thangKSGannhat = thangksGannhat;
	}

	
	public String getNamKSGannhat() {
		
		return this.namKSGannhat;
	}

	
	public void setNamKSGannhat(String namksGannhat) {
		
		this.namKSGannhat = namksGannhat;
	}

	
	
}
