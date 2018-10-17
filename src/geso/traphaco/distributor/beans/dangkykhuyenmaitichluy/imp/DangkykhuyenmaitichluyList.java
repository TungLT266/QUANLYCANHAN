package geso.traphaco.distributor.beans.dangkykhuyenmaitichluy.imp;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import geso.traphaco.distributor.beans.dangkykhuyenmaitichluy.IDangkykhuyenmaitichluyList;
import geso.traphaco.distributor.db.sql.dbutils;

public class DangkykhuyenmaitichluyList implements IDangkykhuyenmaitichluyList, Serializable{
	    String userId;
	    String nppId;
	    String nppTen;
	    String tungay;
	    String denngay;
	    String diengiai;
	    String ten;
		ResultSet dstichluy;
		dbutils db;
	public DangkykhuyenmaitichluyList()
	{
	     this.userId="";
	     this.nppId="";
	     this.nppTen="";
	     this.tungay="";
	     this.denngay="";
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

	
	public void setTungay(String tungay) {
		
		this.tungay = tungay;
	}

	
	public String getTungay() {
		
		return this.tungay;
	}

	
	public void setDenngay(String denngay) {
		
		this.denngay = denngay;
	}

	
	public String getDenngay() {
		
		return this.denngay;
	}

	
	public void setTen(String ten) {
		
		this.ten = ten; 
	}

	
	public String getTen() {
		
		return this.ten;
	}

	
	public void setDsTichluy(ResultSet dstichluy) {
		
		this.dstichluy = dstichluy;
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
	public ResultSet getDsTichluy() {
		return this.dstichluy;
	}

	public void init(String isDuyet) 
	{
		getNppInfo();

		String sql ="select dkkm.pk_seq, ctkm.pk_seq,ctkm.scheme,ctkm.diengiai,ctkm.thang as tungay,ctkm.nam as denngay, dkkm.trangthai, dkkm.daduyet " +
				"from dangkykm_tichluy dkkm inner join TIEUCHITHUONGTL ctkm on dkkm.tieuchitl_fk = ctkm.pk_seq " +
				"where dkkm.npp_fk ='"+ this.nppId +"'";

		if(this.tungay.length()>0){
			sql=sql + "and ctkm.thang>='"+this.tungay+"'";

		}
		if(this.denngay.length()>0){
			sql=sql + "and ctkm.nam <='"+this.denngay+"'";
		}
		if(this.diengiai.length()>0){
			sql=sql + "and ctkm.diengiai like '%"+this.diengiai+"'%";
		}
		
		if(isDuyet.equals("1"))
			sql += " and dkkm.trangthai = '1' ";
			
		ResultSet rs  =db.get(sql);
		System.out.println("Get Du Lieu :"+sql);
		this.dstichluy = rs; 
		
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
			if(dstichluy != null)
				dstichluy.close();
			if(this.db != null)
				this.db.shutDown();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	}
