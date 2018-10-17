package geso.erp.beans.thongtindathang.imp;

import geso.erp.beans.thongtindathang.IThongtindathang;
import geso.dms.db.sql.dbutils;
import geso.dms.center.util.Phan_Trang;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Thongtindathang extends Phan_Trang implements IThongtindathang{
	String userId;
	String id;

	String tensp;
	String masp;
	String tenncc;
	String mancc;
	String MOU;
	String leadtime;
	String spId;
	String nccId;
	String dtsxId;
	String isMua;
	String lotsize;
	String thoigiansx;
	String cleanup;
	ResultSet spRs;
	ResultSet nccRs;
	ResultSet dtsxRs;
	ResultSet nmRs;
	
	String nmId;
	String[] dtsxIds;
	String[] dtsxIds_selected;
	
	String ctyId;
	String msg;

	dbutils db;

	public Thongtindathang()
	{
		this.userId = "";
		this.id = "";
		
		this.tensp = "";
		this.masp = "";
		this.tenncc = "";
		this.mancc = "";
		this.MOU = "";
		this.leadtime = "";
		this.ctyId = "";
		this.isMua = "1";
		this.lotsize = "";
		this.thoigiansx = "";
		this.cleanup = "";
		this.dtsxId = "";
		this.nmId = "";
		this.msg = "";

		this.db = new dbutils();
	}

	public Thongtindathang(String id)
	{
		this.userId = "";
		this.id = id;

		this.tensp = "";
		this.masp = "";
		this.tenncc = "";
		this.mancc = "";
		this.MOU = "";
		this.leadtime = "";
		this.ctyId = "";
		this.isMua = "1";
		this.lotsize = "";
		this.thoigiansx = "";
		this.cleanup = "";
		this.dtsxId = "";
		this.nmId = "";
		this.msg = "";

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

	public String getMasp()
	{
		return this.masp;
	}

	public void setMasp(String masp)
	{
		this.masp = masp;
	}
	
	public String getTensp()
	{
		return this.tensp;
	}

	public void setTensp(String tensp)
	{
		this.tensp = tensp;
	}
	
	public String getMancc()
	{
		return this.mancc;
	}

	public void setMancc(String mancc)
	{
		this.mancc = mancc;
	}
	
	public String getTenncc()
	{
		return this.tenncc;
	}

	public void setTenncc(String tenncc)
	{
		this.tenncc = tenncc;
	}
	
	public String getMOU()
	{
		return this.MOU;
	}

	public void setMOU(String MOU)
	{
		this.MOU = MOU;
	}

	public String getLeadtime()
	{
		return this.leadtime;
	}

	public void setLeadtime(String leadtime)
	{
		this.leadtime = leadtime;
	}

	public String getIsMua()
	{
		return this.isMua;
	}

	public void setIsMua(String isMua)
	{
		this.isMua = isMua;
	}

	public String getLotsize()
	{
		return this.lotsize;
	}

	public void setLotsize(String lotsize)
	{
		this.lotsize = lotsize;
	}

	public String getThoigianSX()
	{
		return this.thoigiansx;
	}

	public void setThoigianSX(String thoigiansx)
	{
		this.thoigiansx = thoigiansx;
	}

	public String getCleanUp()
	{
		return this.cleanup;
	}

	public void setCleanUp(String cleanup)
	{
		this.cleanup = cleanup;
	}

	public String getMsg()
	{
		return this.msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}

	public String getCtyId()
	{
		return this.ctyId;
	}

	public void setCtyId(String ctyId)
	{
		this.ctyId = ctyId;
	}

	public boolean createThongtindathang()
	{
		try
		{
			String query = "";
						
			if(this.isMua.equals("1")){
				query = "SELECT COUNT(*) AS NUM FROM ERP_THONGTINDATHANG WHERE SANPHAM_FK = " + this.spId + " AND NCC_FK = " + this.nccId + "";
				ResultSet rs = this.db.get(query);
				try{
					rs.next();
					if(rs.getInt("NUM") > 0){
						this.msg = "Thông tin sản phẩm mua tại nhà cung cấp đã được lưu. Vui lòng dùng chức năng cập nhật thay vì tạo mới";
						return false;					
					}
					rs.close();
					
				}catch(java.sql.SQLException e){}
				
				if(this.MOU.trim().length() <= 0)
				{
					this.msg = "Vui lòng nhập lượng đặt hàng tối thiểu";
					return false;
				}
	
				if(this.leadtime.trim().length() <= 0)
				{
					this.msg = "Vui lòng nhập thời gian vận chuyển";
					return false;
				}
	
				db.getConnection().setAutoCommit(false);
	
				query = "INSERT ERP_THONGTINDATHANG(ISMUA, SANPHAM_FK, NCC_FK, MOU, LEADTIME, CONGTY_FK, NGUOITAO, NGUOISUA, NGAYTAO, NGAYSUA) " +
						"VALUES(" + this.isMua + ", " + this.spId + ", " + this.nccId + ", '" + this.MOU + "', '" + this.leadtime + "', '" + this.ctyId + "', " +
						"'" + this.userId + "', '" + this.userId + "', '" + this.getDateTime() + "', '" + this.getDateTime() + "')";
	
				if(!db.update(query))
				{
					this.msg = "Không thể tạo thông tin lập kế hoạch: " + query;
					db.getConnection().rollback();
					return false;
				}
	
				query = "select SCOPE_IDENTITY() as ttdhId";
				rs = db.get(query);
				if(rs != null)
				{
					if(rs.next())
					{
						this.id = rs.getString("ttdhId");
					}
					rs.close();
				}
			}else{
				query = "SELECT COUNT(*) AS NUM FROM ERP_THONGTINDATHANG WHERE SANPHAM_FK = " + this.spId + " ";
				ResultSet rs = this.db.get(query);
				try{
					rs.next();
					if(rs.getInt("NUM") > 0){
						this.msg = "Thông tin về sản phẩm đã được lưu. Vui lòng dùng chức năng cập nhật thay vì tạo mới";
						return false;					
					}
					rs.close();
					
				}catch(java.sql.SQLException e){}
				
				if(this.lotsize.trim().length() <= 0)
				{
					this.msg = "Vui lòng nhập số lượng sản xuất";
					return false;
				}
	
				if(this.thoigiansx.trim().length() <= 0)
				{
					this.msg = "Vui lòng nhập thời gian sản xuất";
					return false;
				}
	
				db.getConnection().setAutoCommit(false);
	
				query = "INSERT ERP_THONGTINDATHANG(NHAMAY_FK,ISMUA, SANPHAM_FK, LOTSIZE, THOIGIANSX, CLEANUP, CONGTY_FK, NGUOITAO, NGUOISUA, NGAYTAO, NGAYSUA) " +
						"VALUES(" + this.nmId + ", " + this.isMua + ", " + this.spId + ", " + this.lotsize + ", '" + this.thoigiansx + "', '" + this.cleanup + "', '" + this.ctyId + "', " +
						"'" + this.userId + "', '" + this.userId + "', '" + this.getDateTime() + "', '" + this.getDateTime() + "')";
	
				if(!db.update(query))
				{
					this.msg = "Không thể tạo thông tin lập kế hoạch: " + query;
					db.getConnection().rollback();
					return false;
				}
	
				query = "select SCOPE_IDENTITY() as ttdhId";
				rs = db.get(query);
				if(rs != null)
				{
					if(rs.next())
					{
						this.id = rs.getString("ttdhId");
					}
					rs.close();
				}				
				
				for(int i = 0; i < this.dtsxIds_selected.length; i++){
					if(!this.dtsxIds_selected[i].equals("NULL")){
						query = "INSERT ERP_THONGTINDATHANG_DAYTRUYENSX(THONGTINDATHANG_FK, DAYTRUYENSX_FK) VALUES(" + this.id + ", " + this.dtsxIds_selected[i] + " )";
						this.db.update(query);
					}
				}
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}

		catch (Exception e)
		{
			this.msg = "Lỗi: " + e.getMessage();
			try
			{
				db.getConnection().rollback();
			}
			catch (SQLException e1) {}
			return false;
		}

		return true;
	}

	public boolean updateThongtindathang()
	{
		try
		{
			if(this.isMua.equals("1")){
				String query = "SELECT COUNT(*) AS NUM FROM ERP_THONGTINDATHANG WHERE SANPHAM_FK = " + this.spId + " AND NCC_FK = " + this.nccId + "";
				ResultSet rs = this.db.get(query);
				try{
					rs.next();
					if(rs.getInt("NUM") > 0){
						this.msg = "Thông tin sản phẩm mua tại nhà cung cấp đã được lưu. Vui lòng dùng chức năng cập nhật thay vì tạo mới";
						return false;					
					}
					rs.close();
					
				}catch(java.sql.SQLException e){}
				
				if(this.MOU.trim().length() <= 0)
				{
					this.msg = "Vui lòng nhập lượng đặt hàng tối thiểu";
					return false;
				}
	
				if(this.leadtime.trim().length() <= 0)
				{
					this.msg = "Vui lòng nhập thời gian vận chuyển";
					return false;
				}

				db.getConnection().setAutoCommit(false);

				query = 	"UPDATE ERP_THONGTINDATHANG SET SANPHAM_FK = " + this.spId + ", NCC_FK = " + this.nccId + ", MOU = '" + this.MOU + "', LEADTIME = N'" + this.leadtime + "', " +
							"NGUOISUA = '" + this.userId + "', NGAYSUA = '" + this.getDateTime() + "' WHERE PK_SEQ = '" + this.id + "' ";

				if(!db.update(query))
				{
					this.msg = "Không thể cập nhật thông tin đặt hàng: " + query;
					db.getConnection().rollback();
					return false;
				}
			}else{

				String query = "SELECT COUNT(*) AS NUM FROM ERP_THONGTINDATHANG WHERE SANPHAM_FK = " + this.spId + " AND PK_SEQ <> " + this.id ;
				ResultSet rs = this.db.get(query);
				try{
					rs.next();
					if(rs.getInt("NUM") > 0){
						this.msg = "Thông tin về sản phẩm đã được lưu. Vui lòng dùng chức năng cập nhật thay vì tạo mới";
						return false;					
					}
					rs.close();
					
				}catch(java.sql.SQLException e){}
				
				if(this.lotsize.trim().length() <= 0)
				{
					this.msg = "Vui lòng nhập số lượng sản xuất";
					return false;
				}
	
				if(this.thoigiansx.trim().length() <= 0)
				{
					this.msg = "Vui lòng nhập thời gian sản xuất";
					return false;
				}
	
				db.getConnection().setAutoCommit(false);
	
				query = "UPDATE ERP_THONGTINDATHANG SET NHAMAY_FK = " + this.nmId + ", ISMUA = " + this.isMua + ", SANPHAM_FK = " + this.spId + ", \n " +
						"LOTSIZE = " + this.lotsize + ", THOIGIANSX = " + this.thoigiansx + ", CLEANUP = " + this.cleanup + ", \n " +
						"NGUOISUA = " + this.userId + ", NGAYSUA =  '" + this.getDateTime() + "' \n " +
						"WHERE PK_SEQ = " + this.id + " ";
	
				if(!db.update(query))
				{
					this.msg = "Không thể cập nhật thông tin lập kế hoạch: " + query;
					db.getConnection().rollback();
					return false;
				}
	
				query = "DELETE ERP_THONGTINDATHANG_DAYTRUYENSX WHERE THONGTINDATHANG_FK = "  + this.id + " ";
				this.db.update(query);
				
				for(int i = 0; i < this.dtsxIds_selected.length; i++){
					if(!this.dtsxIds_selected[i].equals("NULL")){
						query = "INSERT ERP_THONGTINDATHANG_DAYTRUYENSX(THONGTINDATHANG_FK, DAYTRUYENSX_FK) VALUES(" + this.id + ", " + this.dtsxIds_selected[i] + " )";
						this.db.update(query);
					}
				}
		
							
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}

		catch (Exception e)
		{
			this.msg = "Lỗi: " + e.getMessage();
			try
			{
				db.getConnection().rollback();
			}
			catch (SQLException e1) {}
			return false;
		}

		return true;
	}

	public void init()
	{
		String query = 
		" SELECT TTDH.PK_SEQ as ID, SP.PK_SEQ as SPID, SP.TEN as SANPHAM, ISNULL(NCC.PK_SEQ, 0) as NCCID, \n " +
		" ISNULL(NCC.TEN, '') as NHACUNGCAP, TTDH.MOU, TTDH.LEADTIME, ISNULL(TTDH.ISMUA, 1) AS ISMUA, ISNULL(TTDH.LOTSIZE, 0) AS LOTSIZE, \n " +
		" ISNULL(TTDH.THOIGIANSX, 0) AS THOIGIANSX, ISNULL(TTDH.CLEANUP, 0) AS CLEANUP, NHAMAY_FK AS NMID  \n " + 
		" FROM ERP_THONGTINDATHANG TTDH  \n " +
		" INNER JOIN ERP_SANPHAM SP on TTDH.SANPHAM_FK = SP.PK_SEQ  \n " +
		" LEFT JOIN ERP_NHACUNGCAP NCC on NCC.PK_SEQ = TTDH.NCC_FK  \n " + 
		" WHERE TTDH.PK_SEQ = '" + this.id + "'";
		
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try
			{
				while(rs.next())
				{
					this.spId = rs.getString("SPID"); 
					this.tensp = rs.getString("SANPHAM");
					this.nccId = rs.getString("NCCID");
					this.tenncc = rs.getString("NHACUNGCAP");
					this.MOU = rs.getString("MOU");
					this.leadtime = rs.getString("LEADTIME");
					this.id = rs.getString("ID");
					this.isMua = rs.getString("ISMUA");
					this.lotsize = rs.getString("LOTSIZE");
					this.thoigiansx = rs.getString("THOIGIANSX");
					this.cleanup  = rs.getString("CLEANUP");
					this.nmId = rs.getString("NMID");
				}
				rs.close();
			}
			catch (Exception e)
			{
				System.out.println("__Exception Init: " + e.getMessage());
			}
		}

		this.createRs();
	}

	public void createRs()
	{
		String query = "";
		query = "SELECT * FROM ( \n " +
				"SELECT PK_SEQ AS ID, '[NL]' + TEN AS SPTEN, TRANGTHAI \n " +
				"FROM ERP_SANPHAM \n " +
				"WHERE LOAISANPHAM_FK IN (100039) AND CONGTY_FK = " + this.ctyId + " \n " +
				
				"UNION ALL \n " +
				"SELECT PK_SEQ AS ID, '[TP]' + TEN AS SPTEN, TRANGTHAI \n " +
				"FROM ERP_SANPHAM \n " +
				"WHERE LOAISANPHAM_FK IN (100041, 100042, 100043)  AND CONGTY_FK = " + this.ctyId + " \n " +

				"UNION ALL \n " +
				"SELECT PK_SEQ AS ID, '[BTP]' + TEN AS SPTEN, TRANGTHAI \n " +
				"FROM ERP_SANPHAM \n " +
				"WHERE LOAISANPHAM_FK IN (100044, 100045)  AND CONGTY_FK = " + this.ctyId + "  \n " +

				"UNION ALL \n " +
				"SELECT PK_SEQ AS ID, '[VLP]' + TEN AS SPTEN, TRANGTHAI \n " +
				"FROM ERP_SANPHAM \n " +
				"WHERE LOAISANPHAM_FK IN (100046) \n  AND CONGTY_FK = " + this.ctyId + " " +

				"UNION ALL \n " +
				"SELECT PK_SEQ AS ID, '[BB]' + TEN AS SPTEN, TRANGTHAI \n " +
				"FROM ERP_SANPHAM \n " +
				"WHERE LOAISANPHAM_FK IN (100049)  AND CONGTY_FK = " + this.ctyId + " \n " +
				")DATA WHERE TRANGTHAI = 1 ORDER BY SPTEN " ;
		this.spRs = this.db.get(query);
		
		query = "SELECT PK_SEQ AS ID, TEN AS NCCTEN FROM ERP_NHACUNGCAP WHERE TRANGTHAI = 1  AND CONGTY_FK = " + this.ctyId + " ";
		this.nccRs = this.db.get(query);
		
		query = "SELECT PK_SEQ AS ID, MANHAMAY + ' - ' + TENNHAMAY AS TEN FROM ERP_NHAMAY WHERE TRANGTHAI = 1 AND CONGTY_FK = " + this.ctyId + " \n ";
		System.out.println(query);
		
		this.nmRs = this.db.get(query);
		
		if(this.nmId.length() > 0){
			if(this.id.length() > 0){
				query = "SELECT DTSX.PK_SEQ AS ID, DTSX.MADAYTRUYENSX + ' - ' + DTSX.TENDAYTRUYENSX AS TEN,  \n " +
						"(SELECT COUNT(*) FROM ERP_THONGTINDATHANG_DAYTRUYENSX " +
						" WHERE THONGTINDATHANG_FK = " + this.id + " AND DAYTRUYENSX_FK = DTSX.PK_SEQ ) AS CHON \n " +
						"FROM ERP_DAYTRUYENSANXUAT DTSX \n " +
						"WHERE DTSX.CONGTY_FK = " + this.ctyId + " AND DTSX.TRANGTHAI = 1 AND DTSX.NHAMAY_FK = " + this.nmId;
			}else{
				query = "SELECT DTSX.PK_SEQ AS ID, DTSX.MADAYTRUYENSX + ' - ' + DTSX.TENDAYTRUYENSX AS TEN,  \n " +
						"0 AS CHON \n " +
						"FROM ERP_DAYTRUYENSANXUAT DTSX \n " +
						"WHERE DTSX.CONGTY_FK = " + this.ctyId + " AND DTSX.TRANGTHAI = 1 AND DTSX.NHAMAY_FK = " + this.nmId;				
			}
			System.out.println(query);
			this.dtsxRs = this.db.get(query);
		}
	}
	
	public ResultSet getSpRs(){
		return this.spRs;
	}
	
	public ResultSet getNccRs(){
		return this.nccRs;
	}
	

	public String getNccId(){
		return this.nccId;
	}
	
	public void setNccId(String nccId){
		this.nccId = nccId;
	}
	
	public String getSpId(){
		return this.spId;
	}
	
	public void setSpId(String spId){
		this.spId = spId;
	}

	public ResultSet getDtsxRs(){
		return this.dtsxRs;
	}

	public String getDtsxId(){
		return this.dtsxId;
	}
	
	public void setDtsxId(String dtsxId){
		this.dtsxId = dtsxId;
	}

	public ResultSet getNhamayRs(){
		return this.nmRs;
	}

	public String getNhamayId(){
		return this.nmId;
	}
	
	public void setNhamayId(String nmId){
		this.nmId = nmId;
	}

	public String[] getDtsxIds(){
		return this.dtsxIds;
	}
	
	public void setDtsxIds(String[] dtsxIds){
		this.dtsxIds = dtsxIds;
	}

	public String[] getDtsxIds_Selected(){
		return this.dtsxIds_selected;
	}
	
	public void setDtsxIds_Selected(String[] dtsxIds_selected){
		this.dtsxIds_selected = dtsxIds_selected;
	}

	public String convertDate(String date)
	{
		if (!date.contains("-"))
			return "";
		String[] arr = date.split("-");
		if (arr[0].length() > arr[2].length())
			return arr[2] + "-" + arr[1] + "-" + arr[0];
		return date;
	}

	public void DbClose()
	{
		try
		{
			this.db.shutDown();
		}
		catch (Exception e) {}
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

}
