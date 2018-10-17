package geso.traphaco.erp.beans.nhomnhacungcapcn.imp;
import geso.traphaco.erp.beans.nhomnhacungcapcn.INhomnhacungcapcn;
import geso.traphaco.center.db.sql.dbutils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.Serializable;

public class Nhomnhacungcapcn implements INhomnhacungcapcn, Serializable
{
	private static final long serialVersionUID = -9217977546733690415L;
	String ctyId;
	String ctyTen;
	
	String id;
	String ten;
	String diengiai;
	String trangthai;	
	String ngaytao;
	String ngaysua;
	String nguoitao;
	String nguoisua;
	String msg;
	ResultSet nccList;
	ResultSet nccSelected;
	
	ResultSet vungList;
	ResultSet kvList;
	ResultSet nppList;
	String vungId;
	String kvId;
	String nppId;
	String[] ncc;
	boolean search = false;
	dbutils db ;
	
	public Nhomnhacungcapcn(String[] param)
	{
		this.ctyId = "";
		this.ctyTen = "";
		this.id = param[0];
		this.diengiai = param[1];
		this.trangthai = param[2];
		this.ngaytao = param[3];
		this.ngaysua = param[4];
		this.nguoitao = param[5];
		this.nguoisua = param[6];
		this.ten = param[7];
		this.msg = "";
		this.vungId = "0";
		this.kvId = "0";
		this.nppId = "0";
		this.db = new dbutils();
	}
	
	public Nhomnhacungcapcn()
	{
		this.ctyId = "";
		this.ctyTen = "";
		this.id = "";
		this.ten = "";
		this.diengiai = "";
		this.trangthai = "";
		this.ngaytao = "";
		this.ngaysua = "";
		this.nguoitao = "";
		this.nguoisua = "";	
		this.msg = "";
		this.vungId = "0";
		this.kvId = "0";
		this.nppId = "0";
		this.db = new dbutils();
	}
	
	public String getCtyId()
	{
		return this.ctyId;
	}

	public void setCtyId(String ctyId)
	{
		this.ctyId = ctyId;
	}

	public String getCtyTen() {
		return this.ctyTen;
	}

	public void setCtyTen(String ctyTen) {
		this.ctyTen = ctyTen;
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

	public String getTrangthai()
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai)
	{
		this.trangthai = trangthai;
	}
	
	public String getNgaytao()
	{
		return this.ngaytao;
		
	}

	public void setNgaytao(String ngaytao)
	{
		this.ngaytao = ngaytao;
	}
	
	public String getNgaysua()
	{
		return this.ngaysua;
		
	}

	public void setNgaysua(String ngaysua)
	{
		this.ngaysua = ngaysua;
	}
	
	public String getNguoitao()
	{
		return this.nguoitao;
	}
	
	public void setNguoitao(String nguoitao)
	{
		this.nguoitao = nguoitao;
	}

	public String getNguoisua()
	{
		return this.nguoisua;
	}

	public void setNguoisua(String nguoisua)
	{
		this.nguoisua = nguoisua;
	}
	
	public String getMessage()
	{
		return this.msg;
	}

	public void setMessage(String msg)
	{
		this.msg = msg;
	}
	
	public ResultSet geNccList()
	{
		return this.nccList;
	}

	public void setNccList(ResultSet nccList)
	{
		this.nccList = nccList;
	}

	public ResultSet getNccSelected()
	{
		return this.nccSelected;
	}

	public void setNccSelected(ResultSet nccSelected)
	{
		this.nccSelected = nccSelected;
	}


	public ResultSet getVungList()
	{
		return this.vungList;
	}

	public void setVungList(ResultSet vungList)
	{
		this.vungList = vungList;
	}
	
	public ResultSet getKvList()
	{
		return this.kvList;
	}

	public void setKvList(ResultSet kvList)
	{
		this.kvList = kvList;
	}
	
	public ResultSet getNppList()
	{
		return this.nppList;
	}
   

	
	public void setNppList(ResultSet nppList)
	{
		this.nppList = nppList;
	}

	 
	public String getVungId()
	{
		return this.vungId;
	}

	public void setVungId(String vungId)
	{
		this.vungId = vungId;
	}

	public String getKvId()
	{
		return this.kvId;
	}

	public void setKvId(String kvId)
	{
		this.kvId = kvId;
	}

	public String getNppId()
	{
		return this.nppId;
	}

	public void setNppId(String nppId)
	{
		this.nppId = nppId;
	}


	public String[] getNcc()
	{
		return this.ncc;
	}

	public void setNcc(String[] ncc)
	{
		this.ncc = ncc;
	}
	
	public boolean saveNewNNcc(){
		String command;
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			command = "insert into nhomnhacungcapcn(TEN,DIENGIAI, NGAYSUA, NGAYTAO, NGUOISUA, NGUOITAO, TRANGTHAI, CONGTY_FK) values('"+this.ten+"',N'" + this.diengiai + "', '" + this.ngaytao + "', '" + this.ngaysua +"', '" + this.nguoitao + "', '" + this.nguoisua + "', '"+ this.trangthai +"', '"+ this.ctyId +"')";			
			System.out.println("[Nhomncc.saveNewNkh] query = " + command);
			if(!this.db.update(command)) {
				db.getConnection().rollback();
				this.msg = "Không thể tạo mới nhóm nhà cung cấp công nợ!";
			}
	
			command = "select IDENT_CURRENT('nhomnhacungcapcn') as nkhId";
			ResultSet rs = this.db.get(command);
			try 
			{
				rs.next();
				this.id = rs.getString("nkhId");
				rs.close();
	
				if(this.ncc != null) 
				{
					String[] nccList = this.ncc; 
					int size = (this.ncc).length;
					int m = 0;
					
					while(m < size)
					{
						command = "insert into NHOMNHACUNGCAPCN_NCC(NCC_fk, NHOMNHACUNGCAPCN_FK) values('" + nccList[m] + "', '"+ this.id + "')";
						System.out.println("[Nhomncc.saveNewNkh] query = " + command);
						if(!this.db.update(command)) {
							db.getConnection().rollback();
							this.msg = "Không thể lưu thông tin nha cung cấp " + nccList[m] + " trong nhóm";
						}
						m++ ;
					}
				}
			}
			catch(Exception e)
			{
				db.getConnection().rollback();
				this.msg = "Xảy ra lỗi khi lấy thông tin mã nhóm nhà cung cấp (" + e.getMessage() + ")";
				e.printStackTrace();
				return false;
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch(Exception e)
		{
			try {
				db.getConnection().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			this.msg = "Xảy ra lỗi khi lưu nhóm nhà cung cấp(" + e.getMessage() + ")";
			e.printStackTrace();
			return false;
		}

		return true;
	}
	
	public boolean updateNNcc()
	 {
	  try 
	  {
	   db.getConnection().setAutoCommit(false);
	   
	   String command;  
	 
	   command ="update NHOMNHACUNGCAPCN set ten = '"+this.ten+"', diengiai = N'"+ this.diengiai +"',ngaysua ='"+ this.ngaysua +"', trangthai ='" + this.trangthai + "' where pk_seq = '" + this.id + "'";
	   if(!db.update(command))
	   {
	    this.msg = "Khong the cap nhat ERP_nhomkhtt: " + command;
	    System.out.println(this.msg);
	    db.getConnection().rollback();
	    return false;
	   }


	  command = "delete from NHOMNHACUNGCAPCN_NCC where NHOMNHACUNGCAPCN_FK = '"+ this.id + "'";
	   System.out.println("Câu query delete:... "+ command);
	   if(!db.update(command))
	   {
	    this.msg = "Khong the cap nhat ERP_nhomkhtt: " + command;
	    System.out.println(this.msg);
	    db.getConnection().rollback();
	    return false;
	   }
	 
	   if(this.ncc != null){
	    String[] nccList = this.ncc; 
	    int size = (this.ncc).length;
	    int m = 0;
	     
	    while(m < size){
	    command = "insert into NHOMNHACUNGCAPCN_NCC(NCC_FK, NHOMNHACUNGCAPCN_FK) values('" + nccList[m] + "','" + this.id + "')";
	     this.db.update(command);
	     m++ ;
	    }
	     
	   }
	   db.getConnection().commit();
	   db.getConnection().setAutoCommit(true);
	  }
	  
	  catch(Exception e) {
		  e.printStackTrace();
	   try 
	   {
	    db.getConnection().rollback();
	   }
	   catch (SQLException e1) {}
	   return false;
	  }
	   
	  return true;
	 }

	
	private void createNccRS(){  	

		String query = "";
		String temp = "";
		if (this.ncc != null){
			query = "select pk_seq,ma, ten from erp_nhacungcap where";
			temp =  "select pk_seq from erp_nhacungcap where";
			for(int i=0; i < this.ncc.length; i++){
				if (i==0){
					query = query + " pk_seq = '" + this.ncc[i] + "'";
					temp = temp + " pk_seq = '" + this.ncc[i] + "'";
				}else{						
					query = query + " or pk_seq = '" + this.ncc[i] + "'";
					temp = temp + " or pk_seq = '" + this.ncc[i] + "'";
				}
			}				
			this.nccSelected = this.db.get(query);
		}else{
			this.nccSelected = null;
		}
		
		
		if (this.id.length() > 0){
			if (this.ncc == null){
				query = "select a.pk_seq,a.ma,  a.ten from ERP_NHACUNGCAP a, NHOMNHACUNGCAPCN_NCC b where a.pk_seq = b.NCC_FK and b.NHOMNHACUNGCAPCN_FK = '" + this.id + "'";
				this.nccSelected = this.db.get(query);	
			
				query = "select a.pk_seq, a.ma, a.ten from ERP_NHACUNGCAP a where  a.trangthai = '1' and a.pk_seq not in (select NCC_FK from NHOMNHACUNGCAPCN_NCC where NHOMNHACUNGCAPCN_FK = '" + this.id + "')";
			}else{
				query = "select a.pk_seq, a.ma, a.ten from ERP_NHACUNGCAP a" +
				" where a.trangthai = '1' and a.pk_seq not in (select NCC_FK from NHOMNHACUNGCAPCN_NCC " +
				"where NHOMNHACUNGCAPCN_FK = '" + this.id + "') and a.pk_seq not in(" + temp + ")";
			}
				
		}else{
				query = "select a.pk_seq, a.ma, a.ten from ERP_NHACUNGCAP a where  a.trangthai = '1' and duyet = '1'" ;
		}
		
		
		if (this.ncc != null){
			query = query +  " and a.pk_seq not in (" + temp + ")";
		}
		query = query + " order by a.ten";
		System.out.println(query);
		this.nccList = this.db.get(query);		
	}
	
	public void UpdateRS(){
		
		
		createNccRS();

	}
	
	public void DBClose() {
		
		if(db != null) {
			db.shutDown();
		}
	}

	 public boolean CheckTKKT()
	 {
	  System.out.println("this.ncc"+ this.ncc);
	  int size = (this.ncc).length;
	  String dem="";
	  int m = 0;
	  String nccIds="";
	  System.out.println("Dem......  = " + size );
	  
	  for(m=0;m<size;m++)
	  {      
	   nccIds += ncc[m]+ ",";      
	     } 
	  nccIds= nccIds.substring(0,nccIds.lastIndexOf(","));
	  String query="Select count(distinct Taikhoan_fk) as dem From ERP_Nhacungcap where pk_seq in ( "+nccIds +")";
	  ResultSet rs= db.get(query);
	  try {
	   while (rs.next()) 
	   {   
	    dem=rs.getString("dem");
	    
	   } 
	  } catch (SQLException e) {
	   e.printStackTrace();
	  }
	  if(!dem.equals("1") )
	  {
	   this.msg=( "Nhóm nhà cung cấp mà bạn chọn không có cùng một Tài khoản kế toán. Vui lòng chọn lại!!");
	   return false;
	           
	  }
	  else
	    {
	   return true;
	    }
	 }

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}
	
	 
}


