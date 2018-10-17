package geso.traphaco.erp.beans.khauhaotaisancodinh.imp;

import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.beans.khauhaotaisancodinh.IKhauhaoCCDCList;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class KhauhaoCCDCList implements IKhauhaoCCDCList {
	String userId;
	
	String ctyId;
	String ctyTen;
	
	ResultSet thangRs;
	String thang;
	
	ResultSet namRs;
	String nam;
	
	ResultSet CCDCRs;
	String tenCCDC;
	
	String action;
	String msg;
	
	dbutils db;
	
	public KhauhaoCCDCList()
	{
		this.thang = "";
		this.nam = "";
		this.tenCCDC = "";
		this.msg = "";
		this.action = "";
		
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

	public String getCongty() 
	{
		return this.ctyTen;
	}

	public void setCongty(String cty) 
	{
		this.ctyTen = cty;
	}

	public String getCtyId() 
	{
		return this.ctyId;
	}

	public void setCtyId(String ctyId) 
	{
		this.ctyId = ctyId;
	}

	public String getAction() 
	{
		return this.action;
	}

	public void setAction(String action) 
	{
		this.action = action;
	}

	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	public ResultSet getThangRs() 
	{
		return this.thangRs;
	}

	public void setThangRs(ResultSet thangRs) 
	{
		this.thangRs = thangRs;
	}

	public String getThang() 
	{
		return this.thang;
	}

	public void setThang(String thang) 
	{
		this.thang = thang;
	}

	public ResultSet getNamRs() 
	{
		return this.namRs;
	}

	public void setNamRs(ResultSet namRs) 
	{
		this.namRs = namRs;
	}

	public String getNam() 
	{
		return this.nam;
	}

	public void setNam(String nam) 
	{
		this.nam = nam;
	}
	
	public ResultSet getCCDCRs() 
	{
		return this.CCDCRs;
	}

	public void setCCDCRs(ResultSet CCDCRs) 
	{
		this.CCDCRs = CCDCRs;
	}

	public String getTenCCDC() 
	{
		return this.tenCCDC;
	}

	public void setTenCCDC(String tenCCDC) 
	{
		this.tenCCDC = tenCCDC;
	}

	
	public void createRs() 
	{
		String query = "";
		for( int i = 1; i <= 12; i++ )
		{
			query += "select " + i + " as thang, N'Tháng " + i + "' as thangTen  ";
			if(i < 12)
			{
				query += " union all ";
			}
		}
		
		this.thangRs = db.get(query);
		
		int nam = Integer.parseInt(this.getDateTime().substring(0, 4));
		
		query = "";
		for(int i = nam - 3; i <= nam + 3; i++)
		{
			query += "select " + i + " as nam, N'Năm " + i + "' as namTen ";
			if(i < nam + 3)
			{
				query += " union all ";
			}
		}
		
		this.namRs = this.db.get(query);
		
		query  = 	"select khccdc.pk_seq,khccdc.nam,khccdc.thang,khccdc.sochungtu,khccdc.trangthai,khccdc.diengiai,khccdc.ngaytao,nv.ten AS NGUOITAO \n" +
		"from CHUNGTUKHAUHAOCCDC khccdc \n" +
		"inner join nhanvien nv on nv.pk_seq = khccdc.nguoitao \n" +
		" where isnull(khccdc.isDaXoa,0)!=1 \n" ;
		
		if(this.action.equals("search")){
			if(this.nam.length() > 0){
				query = query + " and khccdc.nam = '" + this.nam + "' \n";
			}
			
			if(this.thang.length() > 0){
				query = query + " and khccdc.thang = '" + this.thang + "' \n";
			}

			
		}
		query = query + " order by nam DESC, thang ASC \n";
		System.out.println("query: \n" + query + "\n-------------------------------------------------");
		this.CCDCRs = this.db.get(query);
	}

	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	public boolean KiemtraKS(String nam, String thang){
		boolean result = true;
		String query = "SELECT COUNT(*) AS NUM FROM ERP_KHOASOKETOAN WHERE NAM = " + nam + " AND THANGKS = " + Integer.parseInt(thang) + "";
		System.out.println(query);
		ResultSet rs = this.db.get(query);
		try{
			rs.next();
			if(!rs.getString("NUM").equals("0")){
				result = false;
			}
			rs.close();
		}catch(java.sql.SQLException e){}
		return result;
	}
	
	@Override
	public void DbClose() {
		try{
			if(thangRs!=null){
				thangRs.close();
			}
			if(namRs!=null){
				namRs.close();
			}
			if(CCDCRs!=null){
				CCDCRs.close();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			if (this.db != null)
			this.db.shutDown();
		}
	}
}