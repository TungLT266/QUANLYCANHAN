package geso.traphaco.center.beans.report.imp;

import geso.traphaco.center.beans.report.IBcTheKho;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;
import geso.traphaco.center.util.Utility_Kho;


import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.aspose.cells.Worksheet;

public class BcTheKho extends Phan_Trang implements IBcTheKho, Serializable
{
	

  private static final long serialVersionUID = 1L;
	String tuNgay,denNgay,spId,nppId,ddkdId,userId,khId;
	double dauky=0;
	boolean IsKhoCoDoiTuong=false;
	String DoiTuongId;
	ResultSet RsDoiTuong;
	String Solo="";
	
	
	public BcTheKho()
	{
		this.spId="";
		this.nppId="";
		this.userId="";
		this.khId="";
		this.msg="";
		this.ddkdId="";
		this.tuNgay="";
		this.denNgay="";
		this.kbhId="";
		this.ttId="";
		this.nhomId="";
		this.vungId="";
		this.loaiHoaDon="0";
		this.action="";
		this.type="1";
		this.khoId="";
		this.dauky=0;
		this.Solo="";
		db = new dbutils();
	}
	public String getKhId()
  {
  	return khId;
  }
	public void setKhId(String khId)
  {
  	this.khId = khId;
  }
	public String getTuNgay()
  {
  	return tuNgay;
  }
	public void setTuNgay(String tuNgay)
  {
  	this.tuNgay = tuNgay;
  }
	public String getDenNgay()
  {
  	return denNgay;
  }
	public void setDenNgay(String denNgay)
  {
  	this.denNgay = denNgay;
  }
	public String getSpId()
  {
  	return spId;
  }
	public void setSpId(String spId)
  {
  	this.spId = spId;
  }
	public String getNppId()
  {
  	return nppId;
  }
	public void setNppId(String nppId)
  {
  	this.nppId = nppId;
  }
	public String getDdkdId()
  {
  	return ddkdId;
  }
	public void setDdkdId(String ddkdId)
  {
  	this.ddkdId = ddkdId;
  }
	public String getUserId()
  {
  	return userId;
  }
	public void setUserId(String userId)
  {
  	this.userId = userId;
  }
	public ResultSet getSpRs()
  {
  	return spRs;
  }
	public void setSpRs(ResultSet spRs)
  {
  	this.spRs = spRs;
  }
	public ResultSet getDdkdRs()
  {
  	return ddkdRs;
  }
	public void setDdkdRs(ResultSet ddkdRs)
  {
  	this.ddkdRs = ddkdRs;
  }
	ResultSet spRs,ddkdRs,khRs,hoadonRs;
	public ResultSet getHoadonRs()
  {
  	return hoadonRs;
  }
	public void setHoadonRs(ResultSet hoadonRs)
  {
  	this.hoadonRs = hoadonRs;
  }
	public ResultSet getKhRs()
  {
  	return khRs;
  }
	public void setKhRs(ResultSet khRs)
  {
  	this.khRs = khRs;
  }
	
	public void closeDB()
	{
		
	    try
      {
	    	if(khRs!=null)
	      khRs.close();
	    	
	    	if(spRs!=null)spRs.close();
	    	
	    	if(db!=null)db.shutDown();
	    	
      } catch (SQLException e)
      {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
      }
		
	}
	
	dbutils db = new dbutils();
	public void createRs()
	{
		
		Utility util = new Utility();
		
		String query="select pk_Seq,ma +  '-'+isnull(ma_fast,'') + '-' + ten as Ten from sanpham where trangthai=1 ";
		
		if(this.nhomId.length()>0)
		{
			query += " and pk_Seq in (select sanpham_fk from nhomhang_sanpham where nhomhang_fk='"+this.nhomId+"' ) ";
		}
		query += "  order by ma  ";
		
		this.spRs = this.db.get(query);
		
		query = "select pk_seq, manhanvien,ten from DaiDienKinhDoanh a where 1=1  ";
		if(this.view.length()>0)
		{
			query += " and pk_seq in  " + util.Quyen_Ddkd(userId) ;
		}
		
		if(this.nppId.length() > 0)
			query += " and a.pk_seq in ( select ddkd_fk from daidienkinhdoanh_npp where npp_fk = '" + nppId + "')  ";
		this.ddkdRs = this.db.get(query);
	
		if(this.nppId.length()>0)
		{
			query="select pk_seq,isnull(mafast,'') +' ' + ten + ' ' + isnull(diachi,'') as Ten from khachhang where 1=1 ";
			
			if(this.view.length()>0)
			{
				query+=" and npp_fk in "+util.quyen_npp(userId);
			}
			
			if(this.nppId.length()>0)
			query+=" and npp_fk='"+this.nppId+"' ";
			
			this.khRs= this.db.get(query);
		}
		query="select pk_Seq,ten,DIENGIAI from KENHBANHANG where TRANGTHAI=1 ";
		this.kbhRs = this.db.get(query);	
		
		query="select pk_seq,ten from tinhthanh where 1=1 ";
		
		if(this.vungId.length()>0)
		{
			query+=" and vung_fk='"+this.vungId+"' ";
		}
		this.ttRs=this.db.get(query);
		
		query="select pk_seq,ten from vung where 1=1 ";
		query+=" and pk_Seq in  "+util.Quyen_Vung(userId)+" ";
		
		this.vungRs=this.db.get(query);
		
		
		query="select pk_Seq,ten from NhomHang ";
		this.nhomRs=this.db.get(query);
		
		
		query="select pk_Seq,ten,diachi from Nhaphanphoi where trangthai=1 and iskhachhang=0 ";
		
		
		
		/*if(this.view.length()>0)
		{
			query+="and pk_Seq in "+util.quyen_npp(userId)+"" ;
		}else 
		{
			query+=" and pk_Seq='"+this.nppId+"' ";
		}*/
		query+=" and pk_Seq='"+this.nppId+"' ";
		
		if(this.ttId.length()>0)
		query+=" and tinhthanh_Fk='"+this.ttId+"' ";
		
		if(this.vungId.length()>0)
			query+=" and khuvuc_fk in (select pk_seq from khuvuc where vung_fk='"+this.vungId+"' ) ";
		
		System.out.println("_NPP_"+query);
		
		this.nppRs=this.db.get(query);
		
		query="select pk_Seq,ten,diengiai from kho where trangthai=1";
		this.khoRs=this.db.get(query);
		Utility_Kho util_kho=new Utility_Kho();
		
		
	 
		
		if(this.khoId!=null && this.khoId.length()>1){
			if(util_kho.getIsKhoCuaNhaCC_Kygui(this.khoId,db).equals("1")){
				this.IsKhoCoDoiTuong=true;
				query=" SELECT PK_SEQ,PK_SEQ,ma + '-' + TEN AS TEN  FROM ERP_NHACUNGCAP WHERE PK_SEQ IN (SELECT NCC_FK FROM NHAPP_KHO_NCC WHERE KHO_FK="+this.khoId+" )";
				
				
			}else if(util_kho.getIsKhoDuTruKhachHang_Kygui(this.khoId, db).equals("1")){
				this.IsKhoCoDoiTuong=true;

				query ="  select distinct  kh.PK_SEQ, kh.MaFast + ' - ' + kh.Ten as Ten" +
					   "  from KHACHHANG kh "+
				 	   "  where 1=1 and kh.TrangThai ='1' and  kh.NPP_FK='"+nppId+"' and pk_seq in (select khachhang_fk from NHAPP_KHO_KYGUI where KHO_FK="+this.khoId+")";

				 
			}else if(util_kho.getIsKhoCuaNhaCC_Kygui(this.khoId, db).equals("1")){
				this.IsKhoCoDoiTuong=true;
				
				query=" SELECT PK_SEQ,PK_SEQ,ma + '-' + TEN AS TEN  FROM ERP_NHACUNGCAP WHERE PK_SEQ IN (SELECT NCC_FK FROM NHAPP_KHO_NCC WHERE KHO_FK="+this.khoId+" )";
				
				
			}else if(util_kho.getIsKhoTrinhDuyetVien(this.khoId, db).equals("1")){
				query=" SELECT pk_seq,ten FROM DAIDIENKINHDOANH d where trangthai=1 "+
				      " and NPP_FK='"+nppId+"' and d.pk_seq in (select ddkd_fk from NHAPP_KHO_DDKD where KHO_FK="+this.khoId+")";
				
				this.IsKhoCoDoiTuong=true;
			}else {
				query="";
				this.IsKhoCoDoiTuong=false;
			}
			if(query.length()> 0){
				this.RsDoiTuong=db.get(query);
			}
			
		}
	}
	
	String queryHd="";
	public String getQueryHd()
	{
		return queryHd;
	}
	public void setQueryHd(String query)
	{
		this.queryHd=query;
	}
	
	public void init(String search)
	{
		createRs();
		
		try{
		String query;
		if(this.khoId.length() >0){
		if(this.IsKhoCoDoiTuong==true){
			String[] param = new String[6];
			param[0] = this.tuNgay;
			param[1] = this.denNgay;
			param[2] = this.nppId;
			param[3]=this.spId;
			param[4]=this.khoId;
			param[5]=this.DoiTuongId;
			
			System.out.println( this.nppId +"this.spId "+ this.spId + " this.khoId" +this.khoId );
		    this.hoadonRs =  db.getRsByPro("GET_CHITIET_NHAP_XUAT_KHO_DOITUONG", param);
		}else{
			String[] param = new String[5];
			param[0] = this.tuNgay;
			param[1] = this.denNgay;
			param[2] = this.nppId;
			param[3]=this.spId;
			param[4]=this.khoId;
			
			System.out.println( this.nppId +"this.spId "+ this.spId + " this.khoId" +this.khoId );
		    this.hoadonRs =  db.getRsByPro("GET_CHITIET_NHAP_XUAT_KHO", param);
		}
		}
		Utility_Kho util_kho=new Utility_Kho();
		if(this.khoId!=null && this.khoId.length()>1){
			 if(util_kho.getIsKhoDuTruKhachHang_Kygui(this.khoId, db).equals("1")){
				 String[] param = new String[6];
					param[0] = this.khoId;
					param[1] =  this.tuNgay.substring(0, 8)+"01";
					param[2] =  this.tuNgay;
					param[3] = this.nppId;
					param[4] = this.DoiTuongId;
					param[5]=this.spId;
					
				    ResultSet rs = db.getRsByPro("REPORT_XUATNHAPTON_KYGUI_SP", param);
				    
				    if(rs.next()){
				    	this.dauky=rs.getDouble("SL cuối kỳ");
				    	System.out.println(" adu ldy :"+rs.getDouble("SL cuối kỳ"));
				    }
				    rs.close();
				    
				    
				 
			}else if(util_kho.getIsKhoCuaNhaCC_Kygui(this.khoId, db).equals("1") || util_kho.getIsKhoCuaNhaCC_Kygui(this.khoId,db).equals("1") ){
				 
				String[] param = new String[6];
				param[0] = this.khoId;
				param[1] =  this.tuNgay.substring(0, 8)+"01";
				param[2] =  this.tuNgay;
				param[3] = this.nppId;
				param[4] = this.DoiTuongId;
				param[5]=this.spId;
				
			    ResultSet rs = db.getRsByPro("REPORT_XUATNHAPTON_NCC_SP", param);
			    
			    if(rs.next()){
			    	this.dauky=rs.getDouble("SL cuối kỳ");
			    	System.out.println(" adu ldy :"+rs.getDouble("SL cuối kỳ"));
			    }
			    rs.close();
			    
				
			}else if(util_kho.getIsKhoTrinhDuyetVien(this.khoId, db).equals("1")){
				String[] param = new String[6];
				param[0] = this.khoId;
				param[1] =  this.tuNgay.substring(0, 8)+"01";
				param[2] =  this.tuNgay;
				param[3] = this.nppId;
				param[4] = this.DoiTuongId;
				param[5]=this.spId;
				
			    ResultSet rs = db.getRsByPro("REPORT_XUATNHAPTON_DDKD_SP", param);
			    
			    if(rs.next()){
			    	this.dauky=rs.getDouble("SL cuối kỳ");
			    	System.out.println(" adu ldy :"+rs.getDouble("SL cuối kỳ"));
			    }
			    rs.close();
			}else {
				
			
				ResultSet rs;
				if(this.Solo.equals("")){
					
					String[] param = new String[5];
					param[0] = this.khoId;
					param[1] =  this.tuNgay.substring(0, 8)+"01";					 
					param[2] =  this.tuNgay;
					param[3] = this.nppId;
					param[4]=this.spId;
					rs = db.getRsByPro("REPORT_XUATNHAPTON_SP", param);
					
				}else{
					
					String[] param = new String[6];
					param[0] = this.khoId;
					param[1] =  this.tuNgay;
					param[2] =  this.tuNgay;
					param[3] = this.nppId;
					param[4]=this.spId;
					param[5]=this.Solo;
					rs = db.getRsByPro("REPORT_XUATNHAPTON_CHITIET_FILTER_LO", param);

				}
			    
			    
			    if(rs.next()){
			    	this.dauky=rs.getDouble("SL cuối kỳ");
			    	//System.out.println(" adu ldy :"+rs.getDouble("SL cuối kỳ"));
			    }
			    rs.close();
			}
		}
	
	    
	 
		}catch(Exception er){
			er.printStackTrace();
		}
	
		
	}
	
	String view,msg;
	public String getMsg()
  {
  	return msg;
  }
	public void setMsg(String msg)
  {
  	this.msg = msg;
  }
	public String getView()
  {
  	return view;
  }
	public void setView(String view)
  {
  	this.view = view;
  }
	
	String kbhId;
	ResultSet kbhRs;
	public String getKbhId()
  {
  	return kbhId;
  }
	public void setKbhId(String kbhId)
  {
  	this.kbhId = kbhId;
  }
	public ResultSet getKbhRs()
  {
  	return kbhRs;
  }
	public void setKbhRs(ResultSet kbhRs)
  {
  	this.kbhRs = kbhRs;
  }
	
	public void setTotal_Query(String param, String query) 
	{
		if(this.type.equals("1"))
		{
		/*	String[] param = new String[7];
			param[0] = this.tuNgay.equals("") ? null : this.tuNgay;
			System.out.println(param[0]);
			param[1] = this.denNgay.equals("") ? null : this.denNgay;
			System.out.println(param[1]);
			param[2] = this.nppId.equals("")? null : this.nppId;
			System.out.println(param[2]);
			param[3] ="".equals("") ? null : "";	
			param[4] ="".equals("") ? null : "";	
			param[5] ="".equals("") ? null : "";	
			param[6] =this.spId.equals("") ? null : this.spId;	
			System.out.println(param[6]);
			this.totalRs=db.getRsByPro("Report_XNT_By_Product", param);*/
			
		}
		else 
		{
			
			query=param+
			   "   select  sum(data.soluong) as soluong, solo,ngayhethan "+      
			   "   from   \n "+      
			   "   (   \n "+query+"   )  as data" +
			   " group by data.solo,data.ngayhethan  \n ";
		this.totalRs=this.db.get(query);
		System.out.println("da vao day sql"+ query);
		}
		
	}
	
	
	ResultSet totalRs;
	public ResultSet getTotalRs()
	{
		return totalRs;
	}
	public void setTotalRs(ResultSet totalRs)
	{
		this.totalRs=totalRs;
	}
	
	String vungId,nhomId,ttId;
	ResultSet vungRs,nhomRs,ttRs;
	public String getVungId()
  {
  	return vungId;
  }
	public void setVungId(String vungId)
  {
  	this.vungId = vungId;
  }
	public String getNhomId()
  {
  	return nhomId;
  }
	public void setNhomId(String nhomId)
  {
  	this.nhomId = nhomId;
  }
	public String getTtId()
  {
  	return ttId;
  }
	public void setTtId(String ttId)
  {
  	this.ttId = ttId;
  }
	public ResultSet getVungRs()
  {
  	return vungRs;
  }
	public void setVungRs(ResultSet vungRs)
  {
  	this.vungRs = vungRs;
  }
	public ResultSet getNhomRs()
  {
  	return nhomRs;
  }
	public void setNhomRs(ResultSet nhomRs)
  {
  	this.nhomRs = nhomRs;
  }
	public ResultSet getTtRs()
  {
  	return ttRs;
  }
	public void setTtRs(ResultSet ttRs)
  {
  	this.ttRs = ttRs;
  }
	
	ResultSet nppRs;
	@Override
  public ResultSet getNppRs()
  {
	
	  return nppRs;
  }
	@Override
  public void setNppRs(ResultSet nppRs)
  {
		this.nppRs=nppRs;
	  
  }
	
	String loaiHoaDon;
	public String getLoaiHoaDon()
  {
  	return loaiHoaDon;
  }
	public void setLoaiHoaDon(String loaiHoaDon)
  {
  	this.loaiHoaDon = loaiHoaDon;
  }

	
	String action;
	public String getAction()
  {
  	return action;
  }
	public void setAction(String timkiem)
  {
  	this.action = timkiem;
  }
	String type;
	public String getType()
  {
  	return type;
  }
	public void setType(String type)
  {
  	this.type = type;
  }
	
	String khoId;
	ResultSet khoRs;
	public String getKhoId()
  {
  	return khoId;
  }
	public void setKhoId(String khoId)
  {
  	this.khoId = khoId;
  }
	public ResultSet getKhoRs()
  {
  	return khoRs;
  }
	public void setKhoRs(ResultSet khoRs)
  {
  	this.khoRs = khoRs;
  }
	@Override
	public double getDauKy() {
		// TODO Auto-generated method stub
		return this.dauky;
	}
	@Override
	public void setDauKy(double Dayky) {
		// TODO Auto-generated method stub
		this.dauky=Dayky;
	}
	@Override
	public String getDoituongId() {
		// TODO Auto-generated method stub
		return this.DoiTuongId;
	}
	@Override
	public void setDoituongId(String DoituongId) {
		// TODO Auto-generated method stub
		this.DoiTuongId=DoituongId;
	}
	@Override
	public ResultSet getRsDoiTuong() {
		// TODO Auto-generated method stub
		return this.RsDoiTuong;
	}
	@Override
	public void setRsDoiTuong(ResultSet rs) {
		// TODO Auto-generated method stub
		this.RsDoiTuong=rs;
	}
	@Override
	public boolean getIsKhoCoDoituong() {
		// TODO Auto-generated method stub
		return this.IsKhoCoDoiTuong;
	}
	@Override
	public String getSolo() {
		// TODO Auto-generated method stub
		return this.Solo;
	}
	@Override
	public void setSolo(String Solo) {
		// TODO Auto-generated method stub
		this.Solo=Solo;
	}
	
}
