package geso.traphaco.erp.beans.lapkehoach.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import geso.traphaco.erp.beans.lapkehoach.IErpLenhsanxuatdk;
import geso.traphaco.erp.db.sql.dbutils;

public class ErpLenhsanxuatdk implements IErpLenhsanxuatdk
{
	String ctyId;
	String userId;
	String id;
	String masp;
	String tensp;
	String trangthai; 
	
	ResultSet nhamayRs;
	String nhamayId;
	
	String soluong;
	String ngayketthuc;
	String donvi;
	String msg;
	
	dbutils db;
	
	public ErpLenhsanxuatdk()
	{
		this.ctyId = "";
		this.userId = "";
		this.id = "";
		this.masp = "";
		this.tensp = "";
		this.trangthai = "";		
		this.nhamayId = "";
		this.soluong = "";
		this.ngayketthuc = "";
		this.donvi = "";
		this.msg = "";
		this.db = new dbutils();
	}
	
	public String ctyId() 
	{
		return this.ctyId;
	}

	public void setCtyId(String ctyId) 
	{
		this.ctyId = ctyId;
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

	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
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

	public String getDonvi() 
	{
		return this.donvi;
	}

	public void setDonvi(String donvi) 
	{
		this.donvi = donvi;
	}

	public String getSoluong() 
	{
		return this.soluong;
	}

	public void setSoluong(String soluong) 
	{
		this.soluong = soluong;
	}
	
	public String getNgay() 
	{
		return this.ngayketthuc;
	}

	public void setNgay(String ngay) 
	{
		this.ngayketthuc = ngay;
	}
	

	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}


	public ResultSet getNhamayRs()
	{
		return this.nhamayRs;
	}

	public void setNhamayRs(ResultSet nhamayRs)
	{
		this.nhamayRs = nhamayRs;
	}

	public String getNhamayId()
	{
		return this.nhamayId;
	}

	public void setNhamayId(String nhamayId)
	{
		this.nhamayId = nhamayId;
	}

	
	public void init()
	{
		/*String query = 	"SELECT SP.MA, SP.TEN + ' ' + SP.QUYCACH AS TEN, LSXDK.NHAMAY_FK, LSXDK.SOLUONG, DVDL.DONVI, " +
						"CASE WHEN LEN(RTRIM(LSXDK.THANG)) = 1 THEN  '0' + RTRIM(LSXDK.THANG)" +
						"ELSE  RTRIM(LSXDK.THANG) END " +
						"+ '-' + " +
						"LSXDK.NAM AS NGAYKETTHUC, LSXDK.TRANGTHAI  " +
						"FROM ERP_LENHSANXUATDUKIEN LSXDK " +
						"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = LSXDK.SANPHAM_FK " +
						"INNER JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ = SP.DVDL_FK " + 
						"WHERE LSXDK.PK_SEQ = '" + this.id + "'" ;*/
		
		String query = 	"SELECT LSXDK.MASANPHAM, LSXDK.NHAMAY_FK, LSXDK.SOLUONG, " +
						"CASE WHEN LEN(RTRIM(LSXDK.THANG)) = 1 THEN  '0' + RTRIM(LSXDK.THANG)" +
						"ELSE  RTRIM(LSXDK.THANG) END " +
						"+ '-' + " +
						"LSXDK.NAM AS NGAYKETTHUC, LSXDK.TRANGTHAI  " +
						"FROM ERP_LENHSANXUATDUKIEN LSXDK " +
						"WHERE LSXDK.PK_SEQ = '" + this.id + "'" ;
		
		System.out.println("Init: " + query);
		
		ResultSet rs = this.db.get(query);
		try
		{
			if(rs != null){
				rs.next();
				this.masp = rs.getString("MASANPHAM");
				//this.tensp = rs.getString("TEN"); 
				this.nhamayId = rs.getString("NHAMAY_FK");
				this.soluong = rs.getString("SOLUONG");
				this.ngayketthuc = rs.getString("NGAYKETTHUC");
				this.trangthai = rs.getString("TRANGTHAI");
				//this.donvi = rs.getString("DONVI");
				rs.close();
			}
			
		}
		catch(Exception e)
		{
			System.out.println("___Exception INIT: " + e.getMessage());
		}
		
		this.createRs();
		
	}
	
	public void createRs() 
	{

		this.nhamayRs = db.get("select pk_seq as id, tennhamay as ten from erp_nhamay where congty_fk = '" + this.ctyId + "'");
	}
	
	public boolean update(){
		String query = 	"UPDATE ERP_LENHSANXUATDUKIEN SET SOLUONG = '" + this.soluong + "', " +
						"TRANGTHAI = '" + this.trangthai + "' WHERE PK_SEQ = '" + this.id + "' ";
		
		System.out.println("Update: " + query);
		
		return this.db.update(query);
	}
	
	
	public void DbClose() 
	{
		try 
		{
			if(this.nhamayRs != null)
				this.nhamayRs.close();
			
			this.db.shutDown();
		} 
		catch (SQLException e) {}
		
	}
	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	
	public static void main(String[] arg)
	{
		Calendar now = Calendar.getInstance();
		
		System.out.println("Tuan cua thang : " +
                now.get(Calendar.WEEK_OF_MONTH));
               
		System.out.println("Tuan cua nam : " +
                now.get(Calendar.WEEK_OF_YEAR));
		
		
		Calendar gregorianCalendar = new GregorianCalendar();  
        gregorianCalendar.setFirstDayOfWeek(Calendar.MONDAY);  
        gregorianCalendar.setMinimalDaysInFirstWeek(4);  
          
        int numWeekofYear = 46;  //INPUT  
        int year = 2012;         //INPUT  
          
        gregorianCalendar.set(Calendar.YEAR , year);  
        gregorianCalendar.set(Calendar.WEEK_OF_YEAR , numWeekofYear);  
         
         		
	}

	
}
