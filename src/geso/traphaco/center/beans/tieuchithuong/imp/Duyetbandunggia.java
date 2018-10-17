package geso.traphaco.center.beans.tieuchithuong.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import geso.traphaco.center.beans.tieuchithuong.IDuyetbandunggia;
import geso.traphaco.center.db.sql.dbutils;

public class Duyetbandunggia implements IDuyetbandunggia 
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
	ResultSet nppRs;
	String nppIds;
	
	ResultSet vungRs;
	String vungIds;
	ResultSet kvRs;
	String kvIds;

	String msg;

	dbutils db;
	
	public Duyetbandunggia()
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

		this.vungIds = "";
		this.kvIds = "";
		this.db = new dbutils();
	}
	
	public Duyetbandunggia(String id)
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
			String query = "select count(*) as sodong from DUYETBANDUNGGIA where thang = '" + this.thang + "' and nam = '" + this.nam + "' ";
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
			
			query = "insert Duyetbandunggia(loaict, quy, thang, nam, diengiai, trangthai, ngaytao, nguoitao, ngaysua, nguoisua) " +
					"values('0', '" + this.quy + "', '" + this.thang + "', '" + this.nam + "', N'" + this.diengiai + "', '0', " +
							"'" + this.getDateTime() + "', '" + this.userId + "', '" + this.getDateTime() + "', '" + this.userId + "')";
			
			System.out.println("1.Insert: " + query);
			if(!db.update(query))
			{
				this.msg = "Khong the tao moi Duyetbandunggia: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			if(this.nppIds.trim().length() > 0)
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
			}
			
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
			
			String query = "select count(*) as sodong from DUYETBANDUNGGIA where thang = '" + this.thang + "' and nam = '" + this.nam + "' and pk_seq != '" + this.id + "' ";
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
			
			query = "update Duyetbandunggia set loaict = '0', quy = '" + this.quy + "', thang = '" + this.thang + "', nam = '" + this.nam + "', diengiai = N'" + this.diengiai + "', " +
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
			
			query = "delete DUYETBANDUNGGIA_KHACHHANG where duyet_fk = '" + this.id + "' and npp_fk in ( " + this.nppIds + " ) ";
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat DUYETBANDUNGGIA_KHACHHANG: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			if(this.nppIds.trim().length() > 0)
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
			}
			
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
		String query = "select loaict, quy, thang, nam, diengiai, npp_fk  " +
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
					this.nppIds = rs.getString("npp_fk");
				}
				rs.close();
			} 
			catch (Exception e)
			{
				System.out.println("115.Error Meg: " + e.getMessage());
			}
		}
		
		this.createNdk();
		this.createRs();
	}
	
	private void createNdk() 
	{
		String query = "select npp_fk from DUYETBANDUNGGIA_NPP where duyet_fk = '" + this.id + "' ";
		System.out.println("___KHOI TAO NPP: " + query);
		
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				String nppId = "";
				while(rs.next())
				{
					nppId += rs.getString("npp_fk") + ",";
				}
				rs.close();
				
				if(nppId.trim().length() > 0)
					this.nppIds = nppId.substring(0, nppId.length() - 1);
			} 
			catch (Exception e) 
			{
				System.out.println("33.Loi khoi tao: " + e.toString());
			}
		}
		
		//INIT THEO MAN HINH HIEN THI
		query = "select khachhang_fk, npp_fk from DUYETBANDUNGGIA_KHACHHANG where duyet_fk = '" + this.id + "' and npp_fk in ( " + this.nppIds + " ) ";
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
					this.khIds = khId.substring(0, khId.length() - 1);
			} 
			catch (Exception e) 
			{
				System.out.println("33.Loi khoi tao: " + e.toString());
			}
		}
		
		System.out.println("__NPP ID: " + this.nppIds);
		
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
		this.vungRs = db.get("select pk_seq, ten from VUNG where trangthai = '1'");
		
		String query = "select pk_seq, ten from KHUVUC where trangthai = '1'";
		if(this.vungIds.trim().length() > 0)
			query += " and vung_fk in ( " + this.vungIds + " ) "; 
		this.kvRs = db.get(query);
		
		query = "select PK_SEQ, MA, TEN  from NhaPhanPhoi where trangthai = '1' and isKHACHHANG = '0' ";
		if(this.kvIds.trim().length() > 0)
			query += " and khuvuc_fk in ( " +  this.kvIds + " ) ";
		if(this.vungIds.trim().length() > 0)
			query += " and khuvuc_fk in ( select pk_seq from KhuVuc where trangthai = '1' and vung_fk in ( " + this.vungIds + " ) ) ";
		
		if(this.id.trim().length() > 0)
		{
			query += " union select PK_SEQ, MA, TEN from NhaPhanPhoi where pk_seq in ( select npp_fk from TIEUCHITHUONGTL_NPP where thuongtl_fk = '" + this.id + "' ) ";
		}
		
		//
		if(this.nppIds.trim().length() > 0)
		{
			query += " union select PK_SEQ, MA, TEN from NhaPhanPhoi where pk_seq in ( " + this.nppIds + " ) ";
		}
		
		query += " order by PK_SEQ desc ";
		this.nppRs = db.getScrol(query);
		
		if(this.nppIds.trim().length() > 0)
		{
			query = "select pk_seq, TEN, DIACHI, DIENTHOAI from KHACHHANG  where trangthai = '1' and NPP_FK in ( " + this.nppIds + " ) ";
			System.out.println("---LAY KHACH HANG: " + query);
			this.khRs = db.get(query);
			
			if(this.id.trim().length() > 0 && this.khIds.trim().length() <= 0 )
			{
				//INIT NHUNG KHACH HANG DA DUOC DUYET CUA NPP NAY
				query = "select khachhang_fk, npp_fk from DUYETBANDUNGGIA_KHACHHANG where duyet_fk = '" + this.id + "' and npp_fk in ( " + this.nppIds + " ) ";
				System.out.println("___KHOI TAO NPP: " + query);
				
				ResultSet rs = db.get(query);
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
							this.khIds = khId.substring(0, khId.length() - 1);
					} 
					catch (Exception e) 
					{
						System.out.println("33.Loi khoi tao: " + e.toString());
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
	
	
	

}
