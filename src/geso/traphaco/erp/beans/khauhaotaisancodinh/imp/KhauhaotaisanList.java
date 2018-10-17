package geso.traphaco.erp.beans.khauhaotaisancodinh.imp;

import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.beans.khauhaotaisancodinh.IKhauhaotaisanList;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class KhauhaotaisanList implements IKhauhaotaisanList {
	
	String userId;
	
	String ctyId;
	String ctyTen;
	String NppId;
	
	ResultSet thangRs;
	String thang;
	
	ResultSet namRs;
	String nam;
	
	String DVKDID;
	
	ResultSet taisanRs;
	String tentaisan;
	
	String action;
	String msg;
	
	dbutils db;
	
	public KhauhaotaisanList()
	{
		this.thang = "";
		this.nam = "";
		this.tentaisan = "";
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
	
	public ResultSet getTaisanRs() 
	{
		return this.taisanRs;
	}

	public void setTaisanRs(ResultSet taisanRs) 
	{
		this.taisanRs = taisanRs;
	}

	public String getTentaisan() 
	{
		return this.tentaisan;
	}

	public void setTentaisan(String tentaisan) 
	{
		this.tentaisan = tentaisan;
	}

	public boolean KiemtraKS(String nam, String thang){
		boolean result = true;
		String query = "SELECT COUNT(*) AS NUM FROM ERP_KHOASOKETOAN WHERE NAM = " + nam + " AND THANGKS = " + Integer.parseInt(thang) + "";
		System.out.println(query);
		ResultSet rs = this.db.get(query);
		if (rs != null)
		{
			try{
				if (rs.next())
					if(!rs.getString("NUM").equals("0")){
						result = false;
					}
				rs.close();
			}catch(java.sql.SQLException e){
				e.printStackTrace();
			}
		}
		return result;
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
		
		query  = 	"select khts.pk_seq,khts.nam,khts.thang,khts.sochungtu,khts.trangthai,khts.diengiai,khts.ngaytao,nv.ten AS NGUOITAO \n" +
					"from CHUNGTUKHAUHAOTAISAN khts \n" +
					"inner join nhanvien nv on nv.pk_seq = khts.nguoitao \n" ;
//					"inner join erp_taisancodinh_chitiet tsct on tsct.taisan_fk = tscd.pk_seq and tsct.thang = khts.thangthu \n" +
		
		if(this.action.equals("search")){
			if(this.nam.length() > 0){
				query = query + " and khts.nam = '" + this.nam + "' \n";
			}
			
			if(this.thang.length() > 0){
				query = query + " and khts.thang = '" + this.thang + "' \n";
			}

		}
		query = query + " order by nam DESC, thang ASC \n";
		System.out.println("query lay khau hao tai san theo thang nam: \n" + query + "\n------------------------------------------");
		this.taisanRs = this.db.get(query);
	}

	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	@Override
	public void DbClose() {
		// TODO Auto-generated method stub
		try{
			if(thangRs!=null){
				thangRs.close();
			}
			if(namRs!=null){
				namRs.close();
			}
			if(taisanRs!=null){
				taisanRs.close();
			}
			db.shutDown();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public String getNppId() {
		return NppId;
	}

	public void setNppId(String nppId) {
		NppId = nppId;
	}

	public String getDVKDID() {
		return DVKDID;
	}

	public void setDVKDID(String dVKDID) {
		DVKDID = dVKDID;
	}

	
}
