package geso.traphaco.center.beans.tieuchithuong.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import geso.traphaco.center.beans.tieuchithuong.IDuyetsuungho;
import geso.traphaco.center.db.sql.dbutils;

public class Duyetsuungho implements IDuyetsuungho 
{
	String userId;
	String id;
	String loaict;
	String thang;
	String quy;
	String nam;
	String diengiai;
	String landuyet;
	String chietkhau;
	
	ResultSet khRs;
	String khIds;
	ResultSet nppRs;
	String nppIds;
	
	ResultSet vungRs;
	String vungIds;
	ResultSet kvRs;
	String kvIds;
	int sl;
	public int getSl() {
		return sl;
	}

	public void setSl(int sl) {
		this.sl = sl;
	}


	String msg;

	dbutils db;
	
	public Duyetsuungho()
	{
		this.id = "";
		this.loaict = "0";
		this.chietkhau = "0";
		this.thang = Integer.toString(Integer.parseInt(getDateTime().split("-")[1]));
		this.quy = getQuyHienTai();
		this.nam = getDateTime().split("-")[0];
		this.diengiai = "";
		this.msg = "";
		this.nppIds = "";
		this.khIds = "";
		this.landuyet = "1";

		this.vungIds = "";
		this.kvIds = "";
		this.db = new dbutils();
	}
	
	public Duyetsuungho(String id)
	{
		this.id = id;
		this.loaict = "0";
		this.chietkhau = "0";
		this.thang = Integer.toString(Integer.parseInt(getDateTime().split("-")[1]));
		this.quy = getQuyHienTai();
		this.nam = getDateTime().split("-")[0];
		this.diengiai = "";
		this.msg = "";
		this.nppIds = "";
		this.khIds = "";
		this.landuyet = "1";
		
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
		System.out.println("________+++++++++++++++++"+this.nppIds);
		try
		{
			if(this.quy.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn quý";
				return false;
			}
			
			if(this.nam.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn năm";
				return false;
			}
			
			if(this.chietkhau.trim().length() <= 0)
			{
				this.msg = "Vui lòng nhập % chiết khấu";
				return false;
			}
			
			//Check Scheme
			String query = "select count(*) as sodong from DUYETSUUNGHO a,DUYETSUUNGHO_NPP b where a.pk_seq=b.duyet_fk and b.npp_fk="+this.nppIds+" and a.quy = '" + this.quy + "' and a.nam = '" + this.nam + "' and a.landuyet = '" + this.landuyet + "' ";
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				int count = 0;
				if(rs.next())
				{
					count = rs.getInt("sodong");
					if(count > 0)
					{
						this.msg = "Qúy: " + this.quy + ", năm: " + this.nam + ", lần duyệt: " + this.landuyet + " đã có duyệt sự ủng hộ.";
						rs.close();
						return false;
					}
				}
				rs.close();
			}
			
			//CHECK KHACH HANG BI TRUNG
			if(this.khIds.trim().length() > 0)
			{
				query = "select pk_seq, ten from KHACHHANG where pk_seq in ( " + khIds +  " )" +
						"	 and pk_seq in ( select khachhang_fk from DUYETSUUNGHO_KHACHHANG where duyet_fk in ( select pk_seq from DUYETSUUNGHO where quy = '" + this.quy + "' and nam = '" + this.nam + "' and landuyet != '" + this.landuyet + "' ) ) ";
				System.out.println("---CHECK TRUNG: " + query );
				rs = db.get(query);
				String maKHDADUYET = "";
				if(rs != null)
				{
					while(rs.next())
					{
						maKHDADUYET += rs.getString("ten") + " (" + rs.getString("pk_seq") + "),";
					}
				}
				rs.close();
				
				if(maKHDADUYET.trim().length() > 0)
				{
					this.msg = "Các mã khách hàng sau đã có duyệt sự ủng hộ trong quý (" + this.quy + "), năm (" + this.nam + "): " + maKHDADUYET;
					return false;
				}
			}
			
			db.getConnection().setAutoCommit(false);
			
			query = "insert Duyetsuungho(loaict, landuyet, chietkhau, quy, thang, nam, diengiai, trangthai, ngaytao, nguoitao, ngaysua, nguoisua) " +
					"values('0', '" + this.landuyet + "', '" + this.chietkhau + "', '" + this.quy + "', '" + this.thang + "', '" + this.nam + "', N'" + this.diengiai + "', '0', " +
							"'" + this.getDateTime() + "', '" + this.userId + "', '" + this.getDateTime() + "', '" + this.userId + "')";
			
			System.out.println("1.Insert: " + query);
			if(!db.update(query))
			{
				this.msg = "Khong the tao moi Duyetsuungho: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			if(this.nppIds.trim().length() > 0)
			{
				query = "Insert DUYETSUUNGHO_NPP(duyet_fk, npp_fk) " +
						"select IDENT_CURRENT('DUYETSUUNGHO') as tctId, pk_seq from NHAPHANPHOI where pk_seq in (" + this.nppIds + ") ";
				
				System.out.println("4.Insert: " + query);
				if(!db.update(query))
				{
					this.msg = "3.Khong the tao moi DUYETSUUNGHO_NPP: " + query;
					db.getConnection().rollback();
					return false;
				}
			}
			
			if(this.khIds.trim().length() > 0)
			{
				query = "Insert DUYETSUUNGHO_KHACHHANG(duyet_fk, khachhang_fk, npp_fk) " +
						"select IDENT_CURRENT('DUYETSUUNGHO') as tctId, pk_seq, npp_fk from KHACHHANG where pk_seq in (" + this.khIds + ") ";
				
				System.out.println("4.Insert: " + query);
				if(!db.update(query))
				{
					this.msg = "3.Khong the tao moi DUYETSUUNGHO_KHACHHANG: " + query;
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
			if(this.quy.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn quý";
				return false;
			}
			
			if(this.nam.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn năm";
				return false;
			}
			
			if(this.chietkhau.trim().length() <= 0)
			{
				this.msg = "Vui lòng nhập % chiết khấu";
				return false;
			}
			
			String query = "select count(*) as sodong from DUYETSUUNGHO where quy = '" + this.quy + "' and nam = '" + this.nam + "' and landuyet = '" + this.landuyet + "' and pk_seq != '" + this.id + "' ";
			System.out.println("---CHECK TRUNG QUY: " + query );
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				int count = 0;
				if(rs.next())
				{
					count = rs.getInt("sodong");
					if(count > 0)
					{
						this.msg = "Qúy: " + this.quy + ", năm: " + this.nam + ", lần duyệt: " + this.landuyet + " đã có duyệt sự ủng hộ.";
						rs.close();
						return false;
					}
				}
				rs.close();
			}
			
			//CHECK KHACH HANG BI TRUNG
			if(this.khIds.trim().length() > 0)
			{
				query = "select pk_seq, ten from KHACHHANG where pk_seq in ( " + khIds +  " )" +
						"	 and pk_seq in ( select khachhang_fk from DUYETSUUNGHO_KHACHHANG where duyet_fk in ( select pk_seq from DUYETSUUNGHO where quy = '" + this.quy + "' and nam = '" + this.nam + "' and landuyet != '" + this.landuyet + "' ) ) ";
				System.out.println("---CHECK TRUNG: " + query );
				rs = db.get(query);
				String maKHDADUYET = "";
				if(rs != null)
				{
					while(rs.next())
					{
						maKHDADUYET += rs.getString("ten") + " (" + rs.getString("pk_seq") + "),";
					}
				}
				rs.close();
				
				if(maKHDADUYET.trim().length() > 0)
				{
					this.msg = "Các mã khách hàng sau đã có duyệt sự ủng hộ trong quý (" + this.quy + "), năm (" + this.nam + "): " + maKHDADUYET;
					return false;
				}
			}
			
			db.getConnection().setAutoCommit(false);
			
			query = "update Duyetsuungho set loaict = '0', landuyet = '" + this.landuyet + "', chietkhau = '" + this.chietkhau + "', quy = '" + this.quy + "', thang = '" + this.thang + "', nam = '" + this.nam + "', diengiai = N'" + this.diengiai + "', " +
						"ngaysua = '" + this.getDateTime() + "', nguoisua = '" + this.userId + "' " +
					"where pk_seq = '" + this.id + "'";
					
			System.out.println("1.Update: " + query);
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat Duyetsuungho: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete DUYETSUUNGHO_NPP where duyet_fk = '" + this.id + "' and npp_fk in ( " + this.nppIds + " ) ";
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat DUYETSUUNGHO_NPP: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete DUYETSUUNGHO_KHACHHANG where duyet_fk = '" + this.id + "' and npp_fk in ( " + this.nppIds + " ) ";
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat DUYETSUUNGHO_KHACHHANG: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			if(this.nppIds.trim().length() > 0)
			{
				query = "Insert DUYETSUUNGHO_NPP(duyet_fk, npp_fk) " +
						"select '" + this.id + "' as tctId, pk_seq from NHAPHANPHOI where pk_seq in (" + this.nppIds + ") ";
				
				System.out.println("3.Insert: " + query);
				if(!db.update(query))
				{
					this.msg = "3.Khong the tao moi DUYETSUUNGHO_NPP: " + query;
					db.getConnection().rollback();
					return false;
				}
			}
			
			if(this.khIds.trim().length() > 0)
			{
				query = "Insert DUYETSUUNGHO_KHACHHANG(duyet_fk, khachhang_fk, npp_fk) " +
						"select '" + this.id + "' as tctId, pk_seq, npp_fk from KHACHHANG where pk_seq in (" + this.khIds + ") ";
				
				System.out.println("4.Insert: " + query);
				if(!db.update(query))
				{
					this.msg = "3.Khong the tao moi DUYETSUUNGHO_KHACHHANG: " + query;
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
		String query = "select loaict, quy, thang, nam, diengiai, landuyet  " +
					   "from DUYETSUUNGHO where pk_seq = '" + this.id + "'";
		
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
					this.landuyet = rs.getString("landuyet");
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
		String query = "select npp_fk from DUYETSUUNGHO_NPP where duyet_fk = '" + this.id + "' ";
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
		query = "select khachhang_fk, npp_fk from DUYETSUUNGHO_KHACHHANG where duyet_fk = '" + this.id + "' and npp_fk in ( " + this.nppIds + " ) ";
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
		String sql="select count(*) as sl,a.pk_seq from nhaphanphoi a,nhanvien b where  a.SITECODE=b.CONVSITECODE and b.PK_SEQ="+this.userId +" group by a.pk_seq";
		ResultSet rs1=this.db.get(sql);
		try {
			if(rs1.next())
			{
				this.sl=rs1.getInt("sl");
				if(this.sl>0)
				{
					this.nppIds=rs1.getString("pk_seq");
					this.vungRs = db.get("select pk_seq, ten from VUNG where trangthai = '1'");
					
					String query = "select pk_seq, ten from KHUVUC where trangthai = '1'";
					if(this.vungIds.trim().length() > 0)
						query += " and vung_fk in ( " + this.vungIds + " ) "; 
					this.kvRs = db.get(query);
					
					if(this.nppIds.trim().length() > 0)
					{
						query = " select PK_SEQ, MA, TEN from NhaPhanPhoi where pk_seq in ( " + this.nppIds + " ) ";
					}
					if(this.kvIds.trim().length() > 0)
						query += " and khuvuc_fk in ( " +  this.kvIds + " ) ";
					if(this.vungIds.trim().length() > 0)
						query += " and khuvuc_fk in ( select pk_seq from KhuVuc where trangthai = '1' and vung_fk in ( " + this.vungIds + " ) ) ";
					System.out.println("id_strim_____________________"+this.id);
					if(this.id.trim().length() > 0)
					{
						query += " union select PK_SEQ, MA, TEN from NhaPhanPhoi where pk_seq in ( select npp_fk from DUYETSUUNGHO_NPP where duyet_fk = '" + this.id + "' ) ";
					}
					
					//
					
					
					query += " order by PK_SEQ desc ";
					System.out.println("___ duyet du ung ho "+query);
					this.nppRs = db.getScrol(query);
					
					
					if(this.nppIds.trim().length() > 0)
					{
						query = "select pk_seq,MaFast, TEN, DIACHI, DIENTHOAI from KHACHHANG  where trangthai = '1' and NPP_FK in ( " + this.nppIds + " ) ";
						System.out.println("---LAY KHACH HANG: " + query);
						this.khRs = db.get(query);
						
						if(this.id.trim().length() > 0 && this.khIds.trim().length() <= 0 )
						{
							//INIT NHUNG KHACH HANG DA DUOC DUYET CUA NPP NAY
							query = "select khachhang_fk, npp_fk from DUYETSUUNGHO_KHACHHANG where duyet_fk = '" + this.id + "' and npp_fk in ( " + this.nppIds + " ) ";
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
				
			}
			else
			{
				this.sl=0;
				this.vungRs = db.get("select pk_seq, ten from VUNG where trangthai = '1'");
				
				//String query = "select pk_seq, ten from KHUVUC where trangthai = '1'";
				String query="";
				if(this.vungIds.trim().length() > 0)
					query += " and vung_fk in ( " + this.vungIds + " ) "; 
				this.kvRs = db.get(query);
				
			//	query = "select PK_SEQ, MA, TEN  from NhaPhanPhoi where trangthai = '1' and isKHACHHANG = '0' ";
				if(this.kvIds.trim().length() > 0)
					query += " and khuvuc_fk in ( " +  this.kvIds + " ) ";
				if(this.vungIds.trim().length() > 0)
					query += " and khuvuc_fk in ( select pk_seq from KhuVuc where trangthai = '1' and vung_fk in ( " + this.vungIds + " ) ) ";
				
				if(this.id.trim().length() > 0)
				{
					query += "  select PK_SEQ, MA, TEN from NhaPhanPhoi where pk_seq in ( select npp_fk from DUYETSUUNGHO_NPP where duyet_fk = '" + this.id + "' ) ";
				}
				
				
				
				
				query += " order by PK_SEQ desc ";
				System.out.println("___ duyet du ung ho "+query);
				this.nppRs = db.getScrol(query);
				
				
				if(this.nppIds.trim().length() > 0)
				{
					query = "select pk_seq,MaFast, TEN, DIACHI, DIENTHOAI from KHACHHANG  where trangthai = '1' and NPP_FK in ( " + this.nppIds + " ) ";
					System.out.println("---LAY KHACH HANG: " + query);
					this.khRs = db.get(query);
					
					if(this.id.trim().length() > 0 && this.khIds.trim().length() <= 0 )
					{
						//INIT NHUNG KHACH HANG DA DUOC DUYET CUA NPP NAY
						query = "select khachhang_fk, npp_fk from DUYETSUUNGHO_KHACHHANG where duyet_fk = '" + this.id + "' and npp_fk in ( " + this.nppIds + " ) ";
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
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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

	
	public String getLanduyet() {

		return this.landuyet;
	}


	public void setLanduyet(String landuyet) {
		
		this.landuyet = landuyet;
	}


	public String getChietkhau() {

		return this.chietkhau;
	}


	public void setChietkhau(String chietkhau) {
		
		this.chietkhau = chietkhau;
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
