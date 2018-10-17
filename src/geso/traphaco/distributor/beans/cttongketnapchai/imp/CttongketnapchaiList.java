package geso.traphaco.distributor.beans.cttongketnapchai.imp;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import geso.traphaco.distributor.beans.cttongketnapchai.ICttongketnapchaiList;
import geso.traphaco.distributor.beans.dangkykhuyenmaitichluy.IDangkykhuyenmaitichluyList;
import geso.traphaco.distributor.db.sql.dbutils;

public class CttongketnapchaiList implements ICttongketnapchaiList, Serializable{
	    String userId;
	    String nppId;
	    String nppTen;
	    String ma;
	    String diengiai;
	    String ten;
		ResultSet CttongketRS;
		dbutils db;
	public CttongketnapchaiList()
	{
	     this.userId="";
	     this.nppId="";
	     this.nppTen="";
	     this.ma="";
	     this.diengiai="";
	     this.ten="";
	     db = new dbutils();
	    
	}
	public String getUserId() {
		
		return this.userId;
	}

	
	public void setUserId(String userId) {
		
		this.userId = userId;
	}

	
	public String getNppId() {
		
		return this.nppId;
	}

	
    public void setNppId(String nppId) {
		
	   this.nppId = nppId;	
	}

	public void setTen(String ten) {
		
		this.ten = ten; 
	}

	
	public String getTen() {
		
		return this.ten;
	}

	public void setNppTen(String nppTen) {
		
		this.nppTen = nppTen;		
	}
	public String getNppTen() {
		
		return this.nppTen;
	}
	
	private void getNppInfo()
	{	
		/*ResultSet rs = this.db.get("select a.pk_seq, a.ten, a.sitecode from nhaphanphoi a, nhanvien b where b.dangnhap = a.ma and b.pk_seq ='" + this.userId + "'");
	   
		try{
			if (!(rs == null)){
				rs.next();
				this.nppId = rs.getString("pk_seq");
				System.out.println("npp:"+this.nppId);
			}else
			{
				this.nppId = "";
								
			}
			
		}catch(Exception e){}		
		*/
		//Phien ban moi
		geso.traphaco.distributor.util.Utility util=new geso.traphaco.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();
		//this.sitecode=util.getSitecode();
	}
	public void init() 
	{
		getNppInfo();
	
		String sql ="select a.pk_seq, a.ma,a.diengiai,a.ngaytao,a.ngaysua,a.nguoitao, a.nguoisua, a.trangthai " +
		 			"from CTTKNAPCHAI a ";
		
		ResultSet rs = db.get(sql);
		System.out.println("Get Du Lieu :"+sql);
		this.CttongketRS = rs; 
		
	}
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	public void setDiengiai(String diengiai) {
		this.diengiai = diengiai;
		
	}
	public String getDiengiai() {

		return this.diengiai;
	}
	@Override
	public void DBclose() {
		// TODO Auto-generated method stub
		
		try {
			if(CttongketRS != null)
				CttongketRS.close();
			if(this.db != null)
				this.db.shutDown();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	@Override
	public void setMa(String ma) {
		// TODO Auto-generated method stub
		this.ma = ma;
	}
	@Override
	public String getMa() {
		// TODO Auto-generated method stub
		return this.ma;
	}
	@Override
	public void setCttongketRS(ResultSet CttongketRS) {
		// TODO Auto-generated method stub
		this.CttongketRS = CttongketRS;
	}
	@Override
	public ResultSet getCttongketRS() {
		// TODO Auto-generated method stub
		return this.CttongketRS;
	}
	}
