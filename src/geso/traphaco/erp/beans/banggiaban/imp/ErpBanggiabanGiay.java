package geso.traphaco.erp.beans.banggiaban.imp;

import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.beans.banggiaban.IBanggia_sp;
import geso.traphaco.erp.beans.banggiaban.IErpBanggiabanGiay;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ErpBanggiabanGiay implements IErpBanggiabanGiay
{
	String userId;
	String congtyId;
	String id;
	
	String tenbanggia;
	
	String dvkdId;
	ResultSet dvkdRs;
	
	String kbhId;
	ResultSet kbhRs;
	
	String lkhId;
	ResultSet lkhRs;
	String tungay, denngay;
	String khId;
	ResultSet khRs;
	
	String khApdungIds;
	ResultSet khApdungRs;
	
	String block;
	String trangthai;
	String msg;
	String lspstr;
	ResultSet lspRs;
	
	List<IBanggia_sp> spList;
	
	String[] hopdongIds;
	dbutils db;
	
	ResultSet DonViDoLuongRs;
	String sudung;
	
	public ErpBanggiabanGiay()
	{
		this.userId = "";
		this.id = "";
		this.tenbanggia = "";
		this.dvkdId = "";
		this.kbhId = "";
		this.lkhId = "";
		this.khId = "";

		this.trangthai = "1";
		this.msg = "";
		this.khApdungIds = "";
		this.block = "0";
		this.tungay = "";
		this.denngay = "";
		this.sudung = "";
		this.lspstr="";

		this.spList = new ArrayList<IBanggia_sp>();
		this.db = new dbutils();
		
	}
	
	
	public ErpBanggiabanGiay(String id)
	{
		this.userId = "";
		this.id = id;
		this.kbhId = "";
		this.lkhId = "";
		this.dvkdId = "";
		this.khId = "";

		this.trangthai = "1";
		this.msg = "";
		this.khApdungIds = "";
		this.block = "0";
		this.tungay = "";
		this.denngay = "";
		this.lspstr="";
		this.sudung = "";
		this.spList = new ArrayList<IBanggia_sp>();
		this.db = new dbutils();	
	}
	
	public String getId() 
	{
		return this.id;
	}

	public void setId(String Id) 
	{
		this.id = Id;
	}
	
	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;	
	}

	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	public String getTungay() 
	{
		return this.tungay;
	}

	public void setTungay(String tungay) 
	{
		this.tungay = tungay;
	}

	public String getSudung() 
	{
		return this.sudung;
	}

	public void setSudung(String sudung) 
	{
		this.sudung = sudung;
	}
	
	public String getDenngay() 
	{
		return this.denngay;
	}

	public void setDenngay(String denngay) 
	{
		this.denngay = denngay;
	}
	
	private boolean KiemtraHieuluc(){
		
		String query = 	"";
		if(this.sudung.equals("0")){
			if(this.id.length() > 0){
				query =		"SELECT COUNT(BG.PK_SEQ) AS NUM " +
							"FROM ERP_BANGGIABAN BG " +
							"INNER JOIN ERP_BANGGIABAN_KH BG_KH ON BG_KH.BANGGIABAN_FK = BG.PK_SEQ " +
							"WHERE PK_SEQ <> " + this.id + " AND KENH_FK = " + this.kbhId + " AND DVKD_FK = " + this.dvkdId + " " +
							"AND ((TUNGAY <= '" + this.tungay + "' AND DENNGAY <= '" + this.denngay + "' AND DENNGAY >= '" + this.tungay + "') " +
							"OR (TUNGAY >= '" + this.tungay + "' AND TUNGAY <= '" + this.denngay + "' AND DENNGAY >= '" + this.denngay + "') " +
							"OR (TUNGAY <= '" + this.tungay + "' AND DENNGAY >= '" + this.denngay + "') " +
							"OR (TUNGAY >= '" + this.tungay + "' AND DENNGAY <= '" + this.denngay + "') " +
							") AND BG_KH.KH_FK IN ( " +  this.khApdungIds  + " ) AND BG.TRANGTHAI > 0  ";
			}else{
				query =		"SELECT COUNT(BG.PK_SEQ) AS NUM " +
							"FROM ERP_BANGGIABAN BG " +
							"INNER JOIN ERP_BANGGIABAN_KH BG_KH ON BG_KH.BANGGIABAN_FK = BG.PK_SEQ " +
							"WHERE KENH_FK = " + this.kbhId + " AND DVKD_FK = " + this.dvkdId + " " +
							"AND ((TUNGAY <= '" + this.tungay + "' AND DENNGAY <= '" + this.denngay + "' AND DENNGAY >= '" + this.tungay + "') " +
							"OR (TUNGAY >= '" + this.tungay + "' AND TUNGAY <= '" + this.denngay + "' AND DENNGAY >= '" + this.denngay + "') " +
							"OR (TUNGAY <= '" + this.tungay + "' AND DENNGAY >= '" + this.denngay + "') " +
							"OR (TUNGAY >= '" + this.tungay + "' AND DENNGAY <= '" + this.denngay + "') " +
							") AND BG_KH.KH_FK IN ( " +  this.khApdungIds   + " ) AND BG.TRANGTHAI > 0 ";
				
			}
		}else{
			if(this.id.length() > 0){
				query =		"SELECT COUNT(BG.PK_SEQ) AS NUM " +
							"FROM ERP_BANGGIABAN BG " +
							"INNER JOIN ERP_BANGGIABAN_KH BG_KH ON BG_KH.BANGGIABAN_FK = BG.PK_SEQ " +
							"WHERE PK_SEQ <> " + this.id + " AND KENH_FK IS NULL AND DVKD_FK IS NULL " +
							"AND ((TUNGAY <= '" + this.tungay + "' AND DENNGAY <= '" + this.denngay + "' AND DENNGAY >= '" + this.tungay + "') " +
							"OR (TUNGAY >= '" + this.tungay + "' AND TUNGAY <= '" + this.denngay + "' AND DENNGAY >= '" + this.denngay + "') " +
							"OR (TUNGAY <= '" + this.tungay + "' AND DENNGAY >= '" + this.denngay + "') " +
							"OR (TUNGAY >= '" + this.tungay + "' AND DENNGAY <= '" + this.denngay + "') " +
							") AND BG.TRANGTHAI > 0  ";
			}else{
				query =		"SELECT COUNT(BG.PK_SEQ) AS NUM " +
							"FROM ERP_BANGGIABAN BG " +
							"INNER JOIN ERP_BANGGIABAN_KH BG_KH ON BG_KH.BANGGIABAN_FK = BG.PK_SEQ " +
							"WHERE KENH_FK IS NULL AND DVKD_FK IS NULL " +
							"AND ((TUNGAY <= '" + this.tungay + "' AND DENNGAY <= '" + this.denngay + "' AND DENNGAY >= '" + this.tungay + "') " +
							"OR (TUNGAY >= '" + this.tungay + "' AND TUNGAY <= '" + this.denngay + "' AND DENNGAY >= '" + this.denngay + "') " +
							"OR (TUNGAY <= '" + this.tungay + "' AND DENNGAY >= '" + this.denngay + "') " +
							"OR (TUNGAY >= '" + this.tungay + "' AND DENNGAY <= '" + this.denngay + "') " +
							") AND BG.TRANGTHAI > 0 ";
				
			}			
		}
		System.out.println("::: KIEM TRA HIEU LUC: " + query);
		ResultSet rs = this.db.get(query);
		try{
			rs.next();
			if(!rs.getString("NUM").equals("0")){
				return false;
			}
			rs.close();
		}catch(java.sql.SQLException e){
			e.printStackTrace();
		}
		return true;
	}
	public void init() 
	{
		String query = 	"select TEN, DVKD_FK, KENH_FK, TRANGTHAI, TINHTRANG, ISNULL(TUNGAY, '') AS TUNGAY, ISNULL(DENNGAY, '') AS DENNGAY, SUDUNG " +
						"from ERP_BANGGIABAN where PK_SEQ = '" + this.id + "'";
		//System.out.println("VAO DAY:init " );
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					this.tenbanggia = rs.getString("TEN");
					this.dvkdId = rs.getString("DVKD_FK");
					this.kbhId = rs.getString("KENH_FK");
					//this.kbhId = rs.getString("KENH_FK");
					this.tungay = rs.getString("TUNGAY");
					this.denngay = rs.getString("DENNGAY");
					this.trangthai = (!rs.getString("TRANGTHAI").equals("0")?"1":"0") ;
					this.sudung = rs.getString("SUDUNG");
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				//System.out.println("__Exception: " + e.getMessage());
			}
		}
		
		query =	"  select bgban_fk,loaisanpham_fk from  ERP_BGBAN_LOAISANPHAM" +
				" WHERE  BGBAN_FK="+this.id;
		ResultSet rslsp = db.get(query);
		this.lspstr = "0";
		if (rslsp != null)
		{
			try{
				while(rslsp.next()){
					this.lspstr+=","+rslsp.getString("LOAISANPHAM_FK");
				}
				rslsp.close();
			}catch(Exception er){
				er.printStackTrace();
			}
		}
		
		//init KhachHangApDung
		query = "select kh_fk from ERP_BANGGIABAN_KH where banggiaban_fk = '" + this.id + "' ";
		if (this.lkhId.length()>0)
			query += " and kh_fk in (select pk_seq from erp_khachhang where loaikh_fk='" + this.lkhId + " and trangthai=1')";
		
		ResultSet rsCheck = db.get(query);
		try 
		{
			String khApDung = "";
			int count = 0;
			if (rsCheck != null)
			{
				while(rsCheck.next())
				{
					khApDung += rsCheck.getString("kh_fk") + ",";
					count++;
				}
				rsCheck.close();
			}
			
			if(khApDung.trim().length() > 0)
			{
				khApDung = khApDung.substring(0, khApDung.length() - 1);
					this.khApdungIds = khApDung;
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		this.createRs();
		this.isBlock();
	}
	
	public boolean createBanggia()
	{	
		try 
		{
			if(this.tenbanggia.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn tên bảng giá";
				return false;
			}
			
			if(this.dvkdId.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn đơn vị kinh doanh";
				return false;
			}
			
			if(this.kbhId.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn kênh bán hàng";
				return false;
			}
			
			if(this.tungay.trim().length() < 10)
			{
				this.msg = "Vui lòng nhập ngày hiệu lực (Từ ngày)";
				return false;
			}

			if(this.denngay.trim().length() < 10)
			{
				this.msg = "Vui lòng nhập ngày hiệu lực (Đến ngày)";
				return false;
			}
			
			if(this.spList.size() <= 0)
			{
				this.msg = "Vui lòng kiểm tra lại thông tin sản phẩm trong bảng giá";
				return false;
			}
			
			if(this.khApdungIds.trim().length() <= 0 && this.khId.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn khách hàng áp dụng bảng giá";
				return false;
			}
			
			if(this.KiemtraHieuluc()){
				db.getConnection().setAutoCommit(false);
			
				String query = "insert into ERP_BANGGIABAN(ten, ngaytao, ngaysua, nguoitao, nguoisua, dvkd_fk, kenh_fk, trangthai, congty_fk, tungay, denngay) " +
							"values(N'" + this.tenbanggia + "','" + getDateTime() + "','" + getDateTime() + "','" + this.userId + "', " +
								"'" + this.userId + "','" + this.dvkdId + "','" + this.kbhId + "', '" + this.trangthai + "', '" + this.congtyId + "', '" + this.tungay + "', '" + this.denngay + "')";
			
				////System.out.println("__Tao moi ERP_BANGGIABAN: " + query);
				if(!db.update(query))
				{
					this.msg = "Không thể tạo mới ERP_BANGGIABAN " + query;
					db.getConnection().rollback();
					return false;
				}
				
				
				ResultSet rsBg = this.db.get("select IDENT_CURRENT('ERP_BANGGIABAN') as bgbanId");
				String bgbanId = "";
				if (rsBg != null)
				{
					if (rsBg.next())
						bgbanId = rsBg.getString("bgbanId");
					rsBg.close();
				}				
				
				query=" insert into ERP_BGBAN_LOAISANPHAM (BGBAN_FK,LOAISANPHAM_FK ) " +
				" select "+bgbanId+",pk_seq  from erp_loaisanpham where pk_seq in ("+(lspstr.length()>0?this.lspstr:"0")+")  ";
		
				if(!db.update(query))
				{
					this.msg = "Không thể cập nhật ERP_BANGGIABAN: " + query;
					this.db.getConnection().rollback();
					return false;
				}
				
			
			//Khi tao moi, bang gia o che do cho duyet
				query = "INSERT INTO ERP_SUAGIABAN (BGBAN_FK, NGAYTAO, NGUOITAO, TINHTRANG, Congty_fk) " +
						"VALUES ( '" + bgbanId + "', '" + this.getDateTime() + "', '" + this.userId + "', '1', '" + this.congtyId + "')";
				//System.out.println("__Update: " + query);
			
				String suagiaId = "";
				if(!db.update(query))
				{
					this.msg = "Không thể cập nhật ERP_BANGGIABAN: " + query;
					this.db.getConnection().rollback();
					return false;
				}
				else
				{
					ResultSet tmp = this.db.get("select IDENT_CURRENT('ERP_SUAGIABAN') as suagiaId");
					if (tmp != null)
					{
						if (tmp.next())
							suagiaId = tmp.getString("suagiaId");
						tmp.close();
					}
				}
			
				int count = 0;
			
				for(int i = 0; i < this.spList.size(); i++)
				{
					IBanggia_sp sp = this.spList.get(i);
				
					String tenMoi = sp.getTenNewsp().trim().length() <= 0 ? sp.getTensp() : sp.getTenNewsp();
					String gia = sp.getGiaban().trim().length() <= 0 ? "0" : sp.getGiabanNew().trim().replaceAll(",", "");
					String chonban = sp.getChonban();
					if(gia.equals("0"))
						chonban = "0";
				
				//Chỉ chỉnh sửa dvdl nếu người dùng chọn và tick chọn chọn bán
				
				
					
					if(chonban.equals("1")){
					
					//kiểm tra xem người dùng đã có khai báo quy cách trong dữ liệu nền sản phẩm chưa
						query = 
						"SELECT COUNT (*) demquycach \n"+
						"FROM ( \n"+
						" 		SELECT 	distinct D.DVDL1_FK, D.SANPHAM_FK SANPHAM_FK \n"+
						" 		FROM 	ERP_SANPHAM B INNER JOIN DONVIDOLUONG E ON B.DVDL_FK = E.PK_SEQ \n"+
						"	  			LEFT JOIN QUYCACH D ON B.PK_SEQ = D.SANPHAM_FK	\n"+					 
						" 		WHERE 	B.PK_SEQ ='"+sp.getIdsp()+"' \n"+
							  
						" 		UNION ALL \n"+
		
						" 		SELECT 	distinct D.DVDL2_FK, D.SANPHAM_FK \n"+
						" 		FROM 	ERP_SANPHAM B INNER JOIN DONVIDOLUONG E ON B.DVDL_FK = E.PK_SEQ \n"+
						"	  			LEFT JOIN QUYCACH D ON B.PK_SEQ = D.SANPHAM_FK \n"+ 
						" 		WHERE 	B.PK_SEQ ='"+sp.getIdsp()+"' \n"+
						
						"		UNION ALL \n"+
						
						" 		SELECT 	distinct B.DVDL_FK, B.PK_SEQ \n"+
						" 		FROM 	ERP_SANPHAM B  \n"+						
						" 		WHERE 	B.PK_SEQ ='"+sp.getIdsp()+"' \n"+
						
						" ) AS DVDL \n"+
						" WHERE DVDL1_FK = '"+sp.getDvdlId()+"' AND SANPHAM_FK = '"+sp.getIdsp()+"' \n";
						ResultSet ktquycach = db.get(query);
						int flag = 0;
						if (ktquycach != null)
						{
							while (ktquycach.next()){
								flag = ktquycach.getInt("demquycach");
							}
							ktquycach.close();
						}
						
						if(flag == 0){
							this.msg = "Sản phẩm " + tenMoi +" chưa thiết lập quy cách trong Dữ liệu nền sản phẩm. Vui lòng kiểm tra lại !";
							db.getConnection().rollback();
							return false;
						}
					
						query = "insert into ERP_BGBAN_SANPHAM(bgban_fk, sanpham_fk, ten, giaban, trangthai, giamoi, dvdl_fk) " +
						"values('" + bgbanId + "', '" + sp.getIdsp() + "', N'" + tenMoi + "', '0','" + chonban + "', '" + gia + "', "+sp.getDvdlId()+")";
					}
					else{
						query =" insert into ERP_BGBAN_SANPHAM(bgban_fk, sanpham_fk, ten, giaban, trangthai, giamoi, dvdl_fk) " +
							   " values('" + bgbanId + "', '" + sp.getIdsp() + "', N'" + tenMoi + "', '0','" + chonban + "', '" + gia + "', (SELECT DVDL_FK FROM ERP_SANPHAM WHERE PK_SEQ ='"+sp.getIdsp()+"'))";
					}
					////System.out.println("ERP_BGBAN_SANPHAM: " + bgbanId + " - query: " + query);
				
					if(!this.db.update(query))
					{				
						this.msg = "Không thể tạo mới ERP_BGBAN_SANPHAM " + query;
						db.getConnection().rollback();
						return false;
					}
				
					if(!gia.equals("0"))
					{
						if(chonban.equals("1")){
						query = "INSERT INTO ERP_SUABGBAN_SANPHAM (SUABGBAN_FK, SANPHAM_FK, GIABANCU, GIABANMOI, DVDL_FK) " +
							"VALUES('" + suagiaId + "', '" + sp.getIdsp() + "', '" + gia + "', '" + gia + "',"+sp.getDvdlId()+")";
					}
					else{
						query = "INSERT INTO ERP_SUABGBAN_SANPHAM (SUABGBAN_FK, SANPHAM_FK, GIABANCU, GIABANMOI, DVDL_FK) " +
						"VALUES('" + suagiaId + "', '" + sp.getIdsp() + "', '" + gia + "', '" + gia + "',(SELECT DVDL_FK FROM ERP_SANPHAM WHERE PK_SEQ ='"+sp.getIdsp()+"'))";
					}
					//System.out.println("Sua gia: " + suagiaId + " - query: " + query);
					if(!db.update(query))
					{				
						this.msg = "Không thể cập nhật ERP_SUABGBAN_SANPHAM " + query;
						this.db.getConnection().rollback();
						return false;
					}
					
					count++;
				}
			}
				
				if(this.khApdungIds.trim().length() > 0)
				{
					query = "insert into ERP_BANGGIABAN_KH(BangGiaBan_FK, KH_FK) " +
							"select '" + bgbanId + "', pk_seq from ERP_KhachHang where pk_seq in ( " + this.khApdungIds  + " ) ";
					if(!this.db.update(query))
					{
						this.msg = "Không thể tạo mới ERP_BANGGIABAN_KH " + query;
						this.db.getConnection().rollback();
						return false;
					}
				}
				else  //Bang gia chi co 1 khach hang
				{
					query = "insert into ERP_BANGGIABAN_KH(BangGiaBan_FK, KH_FK) " +
							"select '" + bgbanId + "', pk_seq from ERP_KhachHang where pk_seq in ( " + this.khId  + " ) ";
				
					if(!this.db.update(query))
					{
						this.msg = "Không thể tạo mới ERP_BANGGIABAN_KH " + query;
						this.db.getConnection().rollback();
						return false;
					}
				}
			
				if(count <= 0) 
				{
					query = "DELETE FROM ERP_SUAGIABAN WHERE PK_SEQ = '" + suagiaId + "'";
					//System.out.println("__Update: " + query);
					if(!db.update(query))
					{
						this.msg = "Không thể tạo mới ERP_SUAGIABAN " + query;
						this.db.getConnection().rollback();
						return false;
					}
				}
			
				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
			}else{
				this.msg = "Không thể lưu bảng giá vì ngày hiệu lực không hợp lệ, do đã tồn tại 1 bảng giá có trùng khoảng hiệu lực và kênh bán hàng";
				return false;				
			}
		} 
		catch (Exception e) 
		{
			this.msg = "Loi: " + e.getMessage();
			try 
			{
				db.getConnection().rollback();
			} 
			catch (SQLException e1) {}
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public boolean updateBanggia() 
	{
		try 
		{ 
		
			if(this.tenbanggia.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn tên bảng giá";
				return false;
			}
		
			if(this.sudung.equals("0")){
				if(this.dvkdId.trim().length() <= 0)
				{
					this.msg = "Vui lòng chọn đơn vị kinh doanh";
					return false;
				}
				
				if(this.kbhId.trim().length() <= 0)
				{
					this.msg = "Vui lòng chọn kênh bán hàng";
					return false;
				}
	
				if(this.khApdungIds.trim().length() <= 0 && this.khId.trim().length() <= 0 )
				{
					this.msg = "Vui lòng chọn khách hàng áp dụng bảng giá";
					return false;
				}

			}
			if(this.tungay.trim().length() < 10)
			{
				this.msg = "Vui lòng nhập ngày hiệu lực (Từ ngày)";
				return false;
			}

			if(this.denngay.trim().length() < 10)
			{
				this.msg = "Vui lòng nhập ngày hiệu lực (Đến ngày)";
				return false;
			}			
			if(this.spList.size() <= 0)
			{
				this.msg = "Vui lòng kiểm tra lại thông tin sản phẩm trong bảng giá";
				return false;
			}
			
			
			if(this.KiemtraHieuluc())
			{
				db.getConnection().setAutoCommit(false);
				
				//Check đến ngày không được nhỏ hơn ngày hiện tại
				String query =  " SELECT count(*) as sodong " + 
								" FROM  ERP_BANGGIABAN  WHERE '" + this.denngay + "' < '" + this.getDateTime() + "' and PK_SEQ = '" + this.id + "' "; 
				System.out.println(":::Check thay doi den ngay :" + query);
				ResultSet rs = db.get(query);
				if(rs != null)
				{
					if(rs.next())
					{
						if(rs.getInt("sodong") > 0 )
						{
							db.getConnection().rollback();
							this.msg = "Vui lòng chọn ngày hiệu lực lớn hơn ngày hiện tại";
							rs.close();
							return false;
						}
					}
					rs.close();
				}
				
				//Bổ sung thêm cho phép sửa đến ngày, nhưng không được nhỏ hơn ngày hiện tại
				boolean thaydoiNGAYHIEULUC = false;
				query = " SELECT count(*) as sodong " + 
						" FROM  ERP_BANGGIABAN  WHERE DENNGAY != '" + this.denngay + "' and '" + this.denngay + "' >= '" + this.getDateTime() + "' and  PK_SEQ = '" + this.id + "' "; 
				System.out.println(":::Check thay doi den ngay :" + query);
				rs = db.get(query);
				if(rs != null)
				{
					if(rs.next())
					{
						System.out.println(":::: SO DONG: " + rs.getInt("sodong") );
						if(rs.getInt("sodong") > 0 )
							thaydoiNGAYHIEULUC = true;
					}
					rs.close();
				}
				
				if(this.sudung.equals("0")){
					query = " update ERP_BANGGIABAN set ten = N'" + this.tenbanggia + "', ngaysua = '" + getDateTime() + "'," +
							" nguoisua = '" + this.userId + "', trangthai = '"+(this.trangthai.equals("0")? "0":"1")+"', " +
							" tungay = '" + this.tungay + "', denngay = '" + this.denngay + "' " +
							" where pk_seq = '" + this.id + "'";
				}else{
					query = " update ERP_BANGGIABAN set ten = N'" + this.tenbanggia + "', ngaysua = '" + getDateTime() + "'," +
							" nguoisua = '" + this.userId + "', " +
							" tungay = '" + this.tungay + "', denngay = '" + this.denngay + "' " +
							" where pk_seq = '" + this.id + "'";
					
				}
				if(!db.update(query))
				{
					this.msg = "Không thể cập nhật ERP_BANGGIABAN: " + query;
					this.db.getConnection().rollback();
					return false;
				}

				
				query=" delete ERP_BGBAN_LOAISANPHAM where BGBAN_FK = "+this.id;
				if(!db.update(query))
				{
					this.msg = "Không thể cập nhật ERP_BANGGIABAN: " + query;
					this.db.getConnection().rollback();
					return false;
				}

				query=" insert into ERP_BGBAN_LOAISANPHAM (BGBAN_FK,LOAISANPHAM_FK ) " +
						" select "+this.id+",pk_seq  from erp_loaisanpham where pk_seq in ("+(lspstr.length()>0?this.lspstr:"0")+")  ";

				if(!db.update(query))
				{
					this.msg = "Không thể cập nhật ERP_BANGGIABAN: " + query;
					this.db.getConnection().rollback();
					return false;
				}
				
				query="SELECT PK_SEQ FROM ERP_SUAGIABAN WHERE BGBAN_FK ="+this.id+" AND  TINHTRANG ='1' ";
				String suagiaId = "";
				ResultSet rssuagia=db.get(query);

				if(rssuagia.next())
				{
					suagiaId=rssuagia.getString("PK_SEQ");

					query=" update ERP_SUAGIABAN set NGAYTAO='"+this.getDateTime()+"',NGUOITAO="+this.userId+"  where pk_Seq="+suagiaId;
					if(!db.update(query))
					{
						this.msg = "Không thể cập nhật ERP_BANGGIABAN: " + query;
						this.db.getConnection().rollback();
						return false;
					}
				}
				else
				{
					query = "INSERT INTO ERP_SUAGIABAN (BGBAN_FK, NGAYTAO, NGUOITAO, TINHTRANG,CONGTY_FK) " +
							"VALUES ( '" + this.id + "', '" + this.getDateTime() + "', '" + this.userId + "', '1',"+this.congtyId+")";

					if(!db.update(query))
					{
						this.msg = "Không thể cập nhật ERP_BANGGIABAN: " + query;
						this.db.getConnection().rollback();
						return false;
					}
					else
					{
						ResultSet tmp = this.db.get("select IDENT_CURRENT('ERP_SUAGIABAN') as suagiaId");
						if (tmp != null)
						{
							if (tmp.next())
								suagiaId = tmp.getString("suagiaId");
							tmp.close();
						}
					}
				}
				rssuagia.close();

				query = "delete ERP_SUABGBAN_SANPHAM where SUABGBAN_FK ="+suagiaId;
				if(!db.update(query))
				{
					this.msg = "Không thể cập nhật ERP_SUABGBAN_SANPHAM: " + query;
					this.db.getConnection().rollback();
					return false;
				}
				
				int bien_tmp = 0;
				//	luu lai nhung sanpham thay doi gia
				int count = 0;
				for(int i = 0; i < this.spList.size(); i++)
				{
					IBanggia_sp sp = this.spList.get(i);

					String tenMoi = sp.getTenNewsp().trim().length() <= 0 ? sp.getTensp() : sp.getTenNewsp();
					String giaOld = sp.getGiaban().trim().length() <= 0 ? "0" : sp.getGiaban().trim().replaceAll(",", "");
					String giaNew = sp.getGiabanNew().trim().length() <= 0 ? "0" : sp.getGiabanNew().trim().replaceAll(",", "");

					String chonban = sp.getChonban();
					if(giaNew.equals("0"))
						chonban = "0";

					//System.out.println("Chon ban :"+chonban +"---- " +sp.getIdsp());

					if(chonban.equals("1"))
					{
						query = 
								"SELECT COUNT (*) demquycach \n"+
										"FROM ( \n"+
										" 		SELECT 	distinct D.DVDL1_FK, D.SANPHAM_FK SANPHAM_FK \n"+
										" 		FROM 	ERP_SANPHAM B INNER JOIN DONVIDOLUONG E ON B.DVDL_FK = E.PK_SEQ \n"+
										"	  			LEFT JOIN QUYCACH D ON B.PK_SEQ = D.SANPHAM_FK	\n"+					 
										" 		WHERE 	B.PK_SEQ ='"+sp.getIdsp()+"' \n"+

						" 		UNION ALL \n"+

						" 		SELECT 	distinct D.DVDL2_FK, D.SANPHAM_FK \n"+
						" 		FROM 	ERP_SANPHAM B INNER JOIN DONVIDOLUONG E ON B.DVDL_FK = E.PK_SEQ \n"+
						"	  			LEFT JOIN QUYCACH D ON B.PK_SEQ = D.SANPHAM_FK \n"+ 
						" 		WHERE 	B.PK_SEQ ='"+sp.getIdsp()+"' \n"+
						"		UNION ALL \n"+						
						" 		SELECT 	distinct B.DVDL_FK, B.PK_SEQ \n"+
						" 		FROM 	ERP_SANPHAM B  \n"+						
						" 		WHERE 	B.PK_SEQ ='"+sp.getIdsp()+"' \n"+
						" ) AS DVDL \n"+
						" WHERE DVDL1_FK = '"+sp.getDvdlId()+"' AND SANPHAM_FK = '"+sp.getIdsp()+"' \n";

						////System.out.println(query);
						ResultSet ktquycach = db.get(query);
						int flag = 0;
						if (ktquycach != null)
						{
							while (ktquycach.next()){
								flag = ktquycach.getInt("demquycach");
							}
							ktquycach.close();
						}
						
						if(flag == 0){
							this.msg = "Sản phẩm " + tenMoi +" chưa thiết lập quy cách trong Dữ liệu nền sản phẩm. Vui lòng kiểm tra lại !";
							db.getConnection().rollback();
							return false;
						}

						//Double.parseDouble(giaOld)   != Double.parseDouble(giaNew) && 
						if (Double.parseDouble(giaNew)  >0 )
						{
							count++;

							query = "select count(*) as sodong from ERP_BGBAN_SANPHAM where BGBAN_FK = '" + this.id + "' and sanpham_fk = '" + sp.getIdsp() + "' ";
							ResultSet rsCheck = db.get(query);
							int sodong = 0;
							if (rsCheck != null)
							{
								if(rsCheck.next())
								{
									sodong = rsCheck.getInt("sodong");
								}
								rsCheck.close();
							}
							
							if(sodong <= 0)
							{
								query = "insert into ERP_BGBAN_SANPHAM (BGBAN_FK, SANPHAM_FK, GIABAN, TRANGTHAI, GIAMOI, DVDL_FK) " +
										"values('" + this.id + "', '" + sp.getIdsp() + "', '" + giaOld + "','" + (this.trangthai.equals("0")? "0":"1") + "', '" + giaNew + "',"+sp.getDvdlId()+" )";

							}
							else
							{
								query = " update ERP_BGBAN_SANPHAM set GIABAN = '" + giaOld + "', giamoi = '" + giaNew + "', TEN = N'" + tenMoi + "', trangthai = '" + (this.trangthai.equals("0")? "0":"1") + "', dvdl_fk = " +sp.getDvdlId()+" "+
										" where BGBAN_FK = '" + this.id + "' and sanpham_fk = '" + sp.getIdsp() + "' ";


							}

							////System.out.println(query);
							if(!db.update(query))
							{				
								this.msg = "Không thể cập nhật ERP_BGBAN_SANPHAM " + query;
								this.db.getConnection().rollback();
								return false;
							}					


							if( Double.parseDouble(giaOld)!=Double.parseDouble(giaNew) ){

								bien_tmp=bien_tmp+1;

								query = " INSERT INTO ERP_SUABGBAN_SANPHAM (SUABGBAN_FK, SANPHAM_FK, GIABANCU, GIABANMOI, DVDL_FK) " +
										" VALUES('" + suagiaId + "', '" + sp.getIdsp() + "', '" + giaOld + "', '" + giaNew + "', "+sp.getDvdlId()+")";

								////System.out.println(query);

								if(!db.update(query))
								{				
									this.msg = "Không thể cập nhật ERP_SUABGBAN_SANPHAM " + query;
									this.db.getConnection().rollback();
									return false;
								}	
							}

						}
						else if(giaNew.equals("0"))
						{
							this.msg = "Không thể cập nhật ERP_SUABGBAN_SANPHAM " + query;
							this.db.getConnection().rollback();
							return false;
						}
					}
					else
					{
						query = "update ERP_BGBAN_SANPHAM set trangthai=0 where BGBAN_FK = '" + this.id + "' " +
								" and sanpham_fk = '" + sp.getIdsp() + "' ";
						////System.out.println("Vào đây cập nhật giá : "+query);
						if(!db.update(query))
						{
							this.msg = "Không thể cập nhật ERP_SUABGBAN_SANPHAM " + query;
							this.db.getConnection().rollback();
							return false;
						}

					}
				}

				if(this.sudung.equals("0")){
					//COPY KHÁCH HÀNG TỪ BẢNG ERP_BANGGIABAN_KH SANG MỘT BẢNG TẠM -> DÙNG ĐỂ SO SÁNH NGƯỜI DÙNG ĐÃ CHỈNH SỬA NHỮNG KHÁCH HÀNG NÀO
					query = " insert ERP_SUABGBAN_KH (BangGiaBan_FK,SUABGBAN_FK, KH_FK) \n " +
							" select "+this.id+","+suagiaId+", KH_FK from ERP_BANGGIABAN_KH  where BangGiaBan_FK = '"+this.id+"'";
	
					if(!db.update(query))
					{
						this.msg = "Không thể cập nhật ERP_SUABGBAN_KH " + query;
						this.db.getConnection().rollback();
						return false;
					}
	
					//Sau nay them dieu kien loc khach hang thi chi duoc phep xoa nhung khach hang ko dc chon tren man hinh
					query = "delete ERP_BANGGIABAN_KH where BangGiaBan_FK = '" + this.id + "'";
					if(!db.update(query))
					{
						this.msg = "Không thể cập nhật ERP_BANGGIABAN_KH " + query;
						this.db.getConnection().rollback();
						return false;
					}
	
					if(this.khApdungIds.trim().length() > 0)
					{
						query = "insert into ERP_BANGGIABAN_KH(BangGiaBan_FK, KH_FK) " +
								"select '" + this.id + "', pk_seq from ERP_KhachHang where pk_seq in ( " + this.khApdungIds  + " ) ";
						////System.out.println("__Chen khach hang: " + query);
	
						if(!this.db.update(query))
						{
							this.msg = "Không thể tạo mới ERP_BANGGIABAN_KH " + query;
							this.db.getConnection().rollback();
							return false;
						}
	
					}
					else  //Bang gia chi co 1 khach hang
					{
						query = "insert into ERP_BANGGIABAN_KH(BangGiaBan_FK, KH_FK) " +
								"select '" + this.id + "', pk_seq from ERP_KhachHang where pk_seq in ( " + this.khId  + " ) ";
	
						if(!this.db.update(query))
						{
							this.msg = "Không thể tạo mới ERP_BANGGIABAN_KH " + query;
							this.db.getConnection().rollback();
							return false;
						}
					}
	
					query=" SELECT KH_FK FROM  ERP_BANGGIABAN_KH  WHERE BANGGIABAN_FK= "+this.id+
							" AND KH_FK NOT IN (SELECT KH_FK FROM  ERP_SUABGBAN_KH  WHERE SUABGBAN_FK="+suagiaId+") "+
							" UNION ALL "+
							" SELECT KH_FK FROM  ERP_SUABGBAN_KH  WHERE SUABGBAN_FK=  "+suagiaId+ 
							" AND KH_FK NOT IN (SELECT KH_FK FROM  ERP_BANGGIABAN_KH  WHERE BANGGIABAN_FK="+this.id+")";
	
					//System.out.println(":::Check thay doi thong tin :"+query);
					ResultSet rscheck = db.get(query);
	
					boolean check=false;
					if (rscheck != null)
					{
						if(rscheck.next()){
							check=true;
						}
						rscheck.close();
					}
					
					System.out.println("::: BIEN TMP: " + bien_tmp + " -- CHECK: " + check + " -- thay doi ngay hieu luc: " + thaydoiNGAYHIEULUC );
					if( bien_tmp == 0 && check == false && thaydoiNGAYHIEULUC == false )
					{
						this.msg = "Vui lòng nhập thông tin cần sửa ";
						this.db.getConnection().rollback();
						return false;
					}
				}
				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
			}
			else
			{
				this.msg = "Không thể lưu bảng giá vì ngày hiệu lực không hợp lệ, do đã tồn tại 1 bảng giá có trùng khoảng hiệu lực và kênh bán hàng";
				return false;				
			}
		} 
		catch (Exception e) 
		{
			this.msg = "Loi: " + e.getMessage();
			try 
			{
				db.getConnection().rollback();
			} 
			catch (SQLException e1) {}
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public void DbClose() 
	{
		try 
		{
			if (this.spList != null)
				this.spList.clear(); 
			if (this.spList != null)
				this.spList.clear();
			if (this.dvkdRs != null)
				this.dvkdRs.close();
			if (this.kbhRs != null)
				this.kbhRs.close();
			if (this.lkhRs != null)
				this.lkhRs.close();
			if (this.khRs != null)
				this.khRs.close();
			if (this.khApdungRs != null)
				this.khApdungRs.close();
			if (this.lspRs != null)
				this.lspRs.close();
			if (this.DonViDoLuongRs != null)
				this.DonViDoLuongRs.close();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (this.db != null)
				this.db.shutDown();
		}
	}
	
	private String getDateTime()
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
	
	public String getCongtyId() 
	{
		return this.congtyId;
	}

	public void setCongtyId(String congtyId) 
	{
		this.congtyId = congtyId;
	}

	public String getKhId() {
		
		return this.khId;
	}

	
	public void setKhId(String khId) {
		
		this.khId = khId;
	}

	
	public ResultSet getKhachhangRs() {
		
		return this.khRs;
	}

	
	public void setKhachhangRs(ResultSet khRs) {
		
		this.khRs = khRs;
	}

	
	public String getTenbanggia() {
		
		return this.tenbanggia;
	}

	
	public void setTenbanggia(String tenbanggia) {
		
		this.tenbanggia = tenbanggia;
	}

	
	public String getDvkdId() {
		
		return this.dvkdId;
	}

	
	public void setDvkdId(String dvkdId) {
		
		this.dvkdId = dvkdId;
	}

	
	public ResultSet getDvkdRs() {
		
		return this.dvkdRs;
	}

	
	public void setDvkdRs(ResultSet dvkdRs) {
		
		this.dvkdRs = dvkdRs;
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

	public List<IBanggia_sp> getSpList() {
		
		return this.spList;
	}


	public void setSpList(List<IBanggia_sp> spList) {
		
		this.spList = spList;
	}
	
	
	public String[] getHopdongIds() {
		
		return this.hopdongIds;
	}


	public void setHopdongIds(String[] hdIds) {
		
		this.hopdongIds = hdIds;
	}
	
	
	public String getKhApDungIds() {
		
		return this.khApdungIds;
	}


	
	public void setKhApDungIds(String khApdungId) {
		
		this.khApdungIds = khApdungId;
	}


	
	public ResultSet getKhachhangApdungRs() {
		
		return this.khApdungRs;
	}


	
	public void setKhachhangApdungRs(ResultSet khApdungRs) {
		
		this.khApdungRs = khApdungRs;
	}
	
	
	public void setBlock(String block) {
		
		this.block = block;
	}


	public String getBlock() {

		return this.block;
	}
	
	public void createRs()
	{
		
		String query="select pk_Seq,ten from erp_loaisanpham ";
		
		this.lspRs=db.get(query);
		
		
		  query = 		" select distinct a.donvikinhdoanh as dvkd, a.pk_seq as dvkdId from donvikinhdoanh a," +
						" erp_congty_dvkd b where a.pk_seq = b.DVKD_fk and b.checked ='1' and a.trangthai = '1' and b.congty_fk = '" + this.congtyId + "' " +
						" order by a.donvikinhdoanh";
		////System.out.println("DVKD: "+query);
		this.dvkdRs  =  this.db.get(query);

		this.kbhRs  =  this.db.get("select diengiai as kenh, pk_seq as kenhId from kenhbanhang where trangthai='1'");
		
//		this.lkhRs = this.db.get("select diengiai as loaikh, pk_seq as loaikhId from erp_loaikhachhang where trangthai='1'");
		
		if(this.kbhId != null & this.dvkdId != null){
			if(this.kbhId.trim().length() > 0 && this.dvkdId.length() >0 )
			{
			 
				query = " SELECT distinct PK_SEQ, MA, TEN, ISNULL(DIACHI, 'NA') AS DIACHI, ISNULL(DIENTHOAI, 'NA') AS DIENTHOAI, 0 AS CHON "+
						" FROM ERP_KHACHHANG KH " +
						" INNER JOIN ERP_KHACHHANG_DVKD A ON A.KHACHHANG_FK=KH.PK_SEQ  "+
						" INNER JOIN ERP_KHACHHANG_KENHBANHANG KH_KENH ON KH_KENH.KHACHHANG_FK = KH.PK_SEQ " +
						" WHERE KH.TRANGTHAI = '1'  and A.dvkd_fk= "+this.dvkdId + 
						" AND KH_KENH.KENHBANHANG_FK = " + this.kbhId + "  " ;
	 
				
				if (this.lkhId.length()>0) {
					query += " and   loaikh_fk="+this.lkhId+""; 
				}
				query += " and pk_seq in ( select Npp_fk from phamvihoatdong where Nhanvien_fk = '" + this.userId + "' ) ";
				
				if(this.id.trim().length() > 0)
				{
					query +=    " and KH.PK_SEQ NOT IN ( select KH_FK from ERP_BANGGIABAN_KH  where BANGGIABAN_FK = '" + this.id + "' )  " +
								" union " +
								" select PK_SEQ, ma, Ten, isnull(diachi, 'NA') as diachi, isnull(dienthoai, 'NA') as dienthoai, 1 AS CHON" +
								" from ERP_KHACHHANG " +
								" where pk_seq in ( select KH_FK from ERP_BANGGIABAN_KH  where BANGGIABAN_FK = '" + this.id + "' )  ";
						
							if (this.lkhId.length()>0) query += " and loaikh_fk= "+this.lkhId+"";
							
			 	}
				 
				
				query += " order by CHON DESC, ten asc ";
				 
				this.khRs = db.getScrol(query);
				
				
	
			}
		}
		
		String querySP = "";
		if(this.dvkdId != null && this.kbhId != null){
			if(this.dvkdId.trim().length() > 0 && this.kbhId.trim().length() > 0  )
			{
				String banggia_fk = this.id.trim().length() <= 0 ? "0" : this.id.trim();
				
				if(banggia_fk.equals("0")){
					querySP =   " select distinct isnull(c.hopdong_fk, 0) as hdId, a.PK_SEQ as spId, a.MA as spMa, isnull(a.TEN, '') as spTen,  " +
								" isnull(c.ten, '') as tenmoi, ISNULL(b.donvi, 'NA') as donvi, isnull(c.giaban, 0) as giaban, " +
								" isnull(c.trangthai, 0) as trangthai,  " +
								" isnull(c.giamoi, 0) as giamoi, isnull(c.hopdong_fk, 0) as hopdong , a.LOAISANPHAM_FK, b.PK_SEQ dvdlId   " +
								" from ERP_SanPham a " +
								" left join DONVIDOLUONG b on a.DVDL_FK = b.PK_SEQ  " +
								" left join ERP_BGBAN_SANPHAM c on a.pk_seq = c.sanpham_fk and c.hopdong_fk is null and c.bgban_fk = '" + banggia_fk + "' and a.DVDL_FK is not null " +
								" where a.DVKD_FK = '" + this.dvkdId + "' and a.trangthai = '1' AND A.LOAISANPHAM_FK IN ("+this.lspstr+")  ";
				}
				else{
					 
					querySP =   " select distinct isnull(c.hopdong_fk, 0) as hdId, a.PK_SEQ as spId, a.MA as spMa, isnull(a.TEN, '') as spTen,  " +
								" isnull(c.ten, '') as tenmoi, ISNULL(b.donvi, 'NA') as donvi, isnull(c.giaban, 0) as giaban, " +
								" isnull(c.trangthai, 0) as trangthai,  " +
								" isnull(c.giamoi, 0) as giamoi, isnull(c.hopdong_fk, 0) as hopdong , a.LOAISANPHAM_FK,  (case when c.DVDL_FK IS NOT NULL then c.DVDL_FK  else b.PK_SEQ end) dvdlId   " +
								" from ERP_SanPham a " +
								" left join DONVIDOLUONG b on a.DVDL_FK = b.PK_SEQ  " +
								" left join ERP_BGBAN_SANPHAM c on a.pk_seq = c.sanpham_fk and c.hopdong_fk is null and c.bgban_fk = '" + banggia_fk + "' and a.DVDL_FK is not null " +
								" where a.DVKD_FK = '" + this.dvkdId + "' and a.trangthai = '1' AND A.LOAISANPHAM_FK IN ("+this.lspstr+")  ";
								
				}
				
				querySP +=	" order by trangthai desc, LOAISANPHAM_FK asc, spMa asc, spTen asc  ";
				
				
				////System.out.println("--- Lay S   P: " + querySP);
				
	//			NumberFormat formater = new DecimalFormat("##,###,###");
				if(this.spList.size() <= 0)
				{
					ResultSet rsSp = db.get(querySP);
					if(rsSp != null)
					{
						try 
						{
							List<IBanggia_sp> spList = new ArrayList<IBanggia_sp>();
							
							while(rsSp.next())
							{
								IBanggia_sp sp = new Banggia_sp();
								
								sp.setHopdong(rsSp.getString("hdId"));
								sp.setIdsp(rsSp.getString("spId"));
								sp.setMasp(rsSp.getString("spMa"));
								
								sp.setTensp(rsSp.getString("spTen"));
								
								if(!rsSp.getString("spTen").equals(rsSp.getString("tenmoi")))
								{
									sp.setTenNew(rsSp.getString("tenmoi"));
								}
								else
								{
									sp.setTenNew("");
								}
								
								sp.setDonvi(rsSp.getString("donvi"));
								sp.setDvdlId(rsSp.getString("dvdlId"));
								sp.setGiaban(rsSp.getString("giaban"));//formater.format(rsSp.getDouble("giaban")));
								sp.setChonban(rsSp.getString("trangthai"));
								sp.setGiabanNew(rsSp.getString("giamoi"));//formater.format(rsSp.getDouble("giamoi")));
								sp.setHopdong( rsSp.getString("hdId").equals("0") ? "" : rsSp.getString("hdId") );
								
								spList.add(sp);
							}
							rsSp.close();
							
							this.spList = spList;
						} 
						catch (Exception e) 
						{
							e.printStackTrace();
						}
						
					}
				}
			}
		}else{

			String banggia_fk = this.id.trim().length() <= 0 ? "0" : this.id.trim();
			
			if(banggia_fk.equals("0")){
				querySP =   " select distinct isnull(c.hopdong_fk, 0) as hdId, a.PK_SEQ as spId, a.MA as spMa, isnull(a.TEN, '') as spTen,  " +
							" isnull(c.ten, '') as tenmoi, ISNULL(b.donvi, 'NA') as donvi, isnull(c.giaban, 0) as giaban, " +
							" isnull(c.trangthai, 0) as trangthai,  " +
							" isnull(c.giamoi, 0) as giamoi, isnull(c.hopdong_fk, 0) as hopdong , a.LOAISANPHAM_FK, b.PK_SEQ dvdlId   " +
							" from ERP_SanPham a " +
							" left join DONVIDOLUONG b on a.DVDL_FK = b.PK_SEQ  " +
							" left join ERP_BGBAN_SANPHAM c on a.pk_seq = c.sanpham_fk and c.hopdong_fk is null and c.bgban_fk = '" + banggia_fk + "' and a.DVDL_FK is not null " +
							" where a.trangthai = '1' AND A.LOAISANPHAM_FK IN ("+this.lspstr+")  ";
			}
			else{
				 
				querySP =   " select distinct isnull(c.hopdong_fk, 0) as hdId, a.PK_SEQ as spId, a.MA as spMa, isnull(a.TEN, '') as spTen,  " +
							" isnull(c.ten, '') as tenmoi, ISNULL(b.donvi, 'NA') as donvi, isnull(c.giaban, 0) as giaban, " +
							" isnull(c.trangthai, 0) as trangthai,  " +
							" isnull(c.giamoi, 0) as giamoi, isnull(c.hopdong_fk, 0) as hopdong , a.LOAISANPHAM_FK,  (case when c.DVDL_FK IS NOT NULL then c.DVDL_FK  else b.PK_SEQ end) dvdlId   " +
							" from ERP_SanPham a " +
							" left join DONVIDOLUONG b on a.DVDL_FK = b.PK_SEQ  " +
							" left join ERP_BGBAN_SANPHAM c on a.pk_seq = c.sanpham_fk and c.hopdong_fk is null and c.bgban_fk = '" + banggia_fk + "' and a.DVDL_FK is not null " +
							" where a.trangthai = '1' AND A.LOAISANPHAM_FK IN ("+this.lspstr+")  ";
							
			}
			
			querySP +=	" order by trangthai desc, LOAISANPHAM_FK asc, spMa asc, spTen asc  ";
			
			
			////System.out.println("--- Lay S   P: " + querySP);
			
//			NumberFormat formater = new DecimalFormat("##,###,###");
			if(this.spList.size() <= 0)
			{
				ResultSet rsSp = db.get(querySP);
				if(rsSp != null)
				{
					try 
					{
						List<IBanggia_sp> spList = new ArrayList<IBanggia_sp>();
						
						while(rsSp.next())
						{
							IBanggia_sp sp = new Banggia_sp();
							
							sp.setHopdong(rsSp.getString("hdId"));
							sp.setIdsp(rsSp.getString("spId"));
							sp.setMasp(rsSp.getString("spMa"));
							
							sp.setTensp(rsSp.getString("spTen"));
							
							if(!rsSp.getString("spTen").equals(rsSp.getString("tenmoi")))
							{
								sp.setTenNew(rsSp.getString("tenmoi"));
							}
							else
							{
								sp.setTenNew("");
							}
							
							sp.setDonvi(rsSp.getString("donvi"));
							sp.setDvdlId(rsSp.getString("dvdlId"));
							sp.setGiaban(rsSp.getString("giaban"));//formater.format(rsSp.getDouble("giaban")));
							sp.setChonban(rsSp.getString("trangthai"));
							sp.setGiabanNew(rsSp.getString("giamoi"));//formater.format(rsSp.getDouble("giamoi")));
							sp.setHopdong( rsSp.getString("hdId").equals("0") ? "" : rsSp.getString("hdId") );
							
							spList.add(sp);
						}
						rsSp.close();
						
						this.spList = spList;
					} 
					catch (Exception e) 
					{
						e.printStackTrace();
					}
					
				}
			}
					
		}
		
		this.DonViDoLuongRs = createDvdlRS();
	}

	
	private void isBlock()
	{
		String query = "SELECT COUNT(*) AS NUM FROM ERP_SUAGIABAN WHERE TINHTRANG = '1' ";
		if(this.id.trim().length() > 0)
			query += " and BGBAN_FK = '" + this.id + "'  ";
		
		////System.out.println("isBlock:" + query);
		
		ResultSet rs = this.db.get(query);
		if (rs != null)
		{
			try
			{
				if (rs.next())
					if(!rs.getString("NUM").equals("0"))
						this.block = "1";
				rs.close();
			}
			catch(java.sql.SQLException e){
				e.printStackTrace();
			}
		}
	}

	public String getLkhId() {
		return this.lkhId;
	}

	public void setLkhId(String lkhId) {
		this.lkhId = lkhId;
	}

	public ResultSet getLkhRs() {
		return this.lkhRs;
	}

	public void setLkhRs(ResultSet lkhRs) {
		this.lkhRs = lkhRs;
	}
	
	public ResultSet getDonViDoLuongRs() {
		
		return this.DonViDoLuongRs;
	}
	
	public void setDonViDoLuongRs(ResultSet DonViDoLuongRs) {
		
		this.DonViDoLuongRs = DonViDoLuongRs;
	}


	
	public ResultSet createDvdlRS() {
		
		String query = "select pk_seq as dvdlId, donvi as dvdlTen from donvidoluong where congty_fk = " + this.congtyId + " and trangthai = '1' order by donvi";
		//System.out.println(query);
		ResultSet dvdlRS = db.getScrol(query);
		
		return dvdlRS;
	}


	@Override
	public ResultSet getLspRs() {
		// TODO Auto-generated method stub
		return this.lspRs;
	}


	@Override
	public void setLspRs(ResultSet rs) {
		// TODO Auto-generated method stub
		this.lspRs=rs;
	}


	@Override
	public String getLspstr() {
		// TODO Auto-generated method stub
		return this.lspstr;
	}


	@Override
	public void setLspstr(String Lspstr) {
		// TODO Auto-generated method stub
		this.lspstr=Lspstr;
	}

}
