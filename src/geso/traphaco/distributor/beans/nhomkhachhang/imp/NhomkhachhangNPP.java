package geso.traphaco.distributor.beans.nhomkhachhang.imp;
import geso.traphaco.center.db.sql.*;
import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.beans.nhomkhachhang.INhomkhachhangNPP;

import java.sql.ResultSet;
import java.io.Serializable;

public class NhomkhachhangNPP implements INhomkhachhangNPP, Serializable
{
	private static final long serialVersionUID = -9217977546733690415L;
	String id;
	String diengiai;
	String ten;
	String trangthai;	
	String ngaytao;
	String ngaysua;
	String nguoitao;
	String nguoisua;
	String msg;
	ResultSet khList;
	ResultSet khSelected;
	String congtyId;
	String userId;
	String nppId;
	ResultSet vungList;
	ResultSet kvList;
	String loaiNpp;
	ResultSet nppList;
	String vungId;
	String kvId;
	String[] khachhang;
	boolean search = false;
	dbutils db = new dbutils();
	private Utility util;
	public NhomkhachhangNPP(String[] param)
	{
		this.id = param[0];
		this.diengiai = param[1];
		this.trangthai = param[2];
		this.ngaytao = param[3];
		this.ngaysua = param[4];
		this.nguoitao = param[5];
		this.nguoisua = param[6];	
		this.msg = "";
		this.vungId = "0";
		this.kvId = "0";
		this.nppId = "0";
		this.loaiNpp= "";
		this.ten ="";
	
		this.util=new Utility();
	}
	
	public NhomkhachhangNPP()
	{
		this.id = "";
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
		this.loaiNpp= "";
		this.ten ="";
		this.util=new Utility();
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
	
	public ResultSet getKhList()
	{
		return this.khList;
	}

	public void setKhList(ResultSet khList)
	{
		this.khList = khList;
	}

	public ResultSet getKhSelected()
	{
		return this.khSelected;
	}

	public void setKhSelected(ResultSet KhSelected)
	{
		this.khSelected = KhSelected;
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


	public String[] getKhachhang()
	{
		return this.khachhang;
	}

	public void setKhachhang(String[] khachhang)
	{
		this.khachhang = khachhang;
	}
	
	public boolean saveNewNkh(){
		this.nppId = util.getIdNhapp(userId);
		String command;
		try
		{
			db.getConnection().setAutoCommit(false);
			command = "insert into NHOMKHACHHANGNPP(TEN,DIENGIAI,NGAYTAO,NGAYSUA,NGUOITAO,NGUOISUA,TRANGTHAI) values(N'"+this.ten+"',N'" + this.diengiai + "', '" + this.ngaytao + "', '" + this.ngaysua +"', '" + this.nguoitao + "', '" + this.nguoisua + "', '"+ this.trangthai +"')";			
			System.out.print("tao moi nhom khach hang " + command);
			if (!db.update(command))
			{
				this.msg = "Khong the tao moi nhom khach hang: " + command;
				System.out.println("1.Exception tai day: " + command);
				db.getConnection().rollback();
				return false;
			}
	
			command = "select IDENT_CURRENT('NHOMKHACHHANGNPP') as nkhId";
			ResultSet rs = this.db.get(command);
			try{			
				rs.next();
				this.id = rs.getString("nkhId");
	
				if(this.khachhang != null){
					String[] khachhangList = this.khachhang; 
					int size = (this.khachhang).length;
					int m = 0;
					
					while(m < size){
						String [] khachhang = khachhangList[m].split(",");
						command = "insert into NHOMKHACHHANGNPP_NPP(NPP_FK, NHOMKHACHHANGNPP_FK,LOAINPP) values('" + khachhang[0] + "', '"+ this.id + "', "+khachhang[1]+")";
						System.out.print(command);
						if (!db.update(command))
						{
							this.msg = "Khong the tao moi nhom khach hang: " + command;
							System.out.println("1.Exception tai day: " + command);
							db.getConnection().rollback();
							return false;
						}
						m++ ;
					}
				}
			}catch(Exception e){}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;
		}
		catch (Exception e)
		{
			this.msg="Lỗi tạo nhận hàng: "+e.getMessage();
			e.printStackTrace();
			db.update("rollback");
			 
			return false;
		}
	}
	
	public boolean updateNkh(){
		this.nppId = util.getIdNhapp(userId);
		String command;		
		try
		{
			db.getConnection().setAutoCommit(false);
		//	command ="update nhomkhachhang set ten ='"+ this.ten +"', diengiai = '"+ this.diengiai +"', trangthai ='" + this.trangthai + "' where pk_seq = '" + this.id + "'";
			command ="update NHOMKHACHHANGNPP set ten=N'"+this.ten+"',diengiai = N'"+ this.diengiai +"',ngaysua ='"+ this.ngaysua +"', trangthai ='" + this.trangthai + "' where pk_seq = '" + this.id + "'";
			
			if (!db.update(command))
			{
				this.msg = "Khong the cap nhat nhom khach hang: " + command;
				System.out.println("2.Exception tai day: " + command);
				db.getConnection().rollback();
				return false;
			}
					
			command = "delete from NHOMKHACHHANGNPP_NPP where NHOMKHACHHANGNPP_FK = '"+ this.id + "'";
			if (!db.update(command))
			{
				this.msg = "Khong the cap nhat nhom khach hang: " + command;
				System.out.println("2.Exception tai day: " + command);
				db.getConnection().rollback();
				return false;
			}
	
			if(this.khachhang != null){
				String[] khachhangList = this.khachhang; 
				int size = (this.khachhang).length;
				int m = 0;
				
				while(m < size){
					String [] khachhang = khachhangList[m].split(",");
					command = "insert into NHOMKHACHHANGNPP_NPP(NPP_FK, NHOMKHACHHANGNPP_FK,LOAINPP) values('" + khachhang[0] + "', '"+ this.id + "', "+khachhang[1]+")";
					System.out.print(command);
					if (!db.update(command))
					{
						this.msg = "Khong the tao moi nhom khach hang: " + command;
						System.out.println("1.Exception tai day: " + command);
						db.getConnection().rollback();
						return false;
					}
					m++ ;
				}
					
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;
		}
		catch (Exception e)
		{
			this.msg="Lỗi tạo nhận hàng: "+e.getMessage();
			e.printStackTrace();
			db.update("rollback");
			 
			return false;
		}
	}

	private ResultSet createVungRS(){  	
		
		ResultSet vungRS =  this.db.get("select pk_seq, diengiai from vung  where trangthai='1'");
		
		return vungRS;
	}
	
	private ResultSet createKvRS(){
		ResultSet kvRS;
		if (!this.vungId.equals("0")){
			kvRS =  this.db.get("select pk_seq, diengiai from khuvuc where trangthai='1' and vung_fk='" + this.vungId + "'");
		}else{
			kvRS =  null;	
		}
		System.out.print("select pk_seq, diengiai from khuvuc where trangthai='1' and vung_fk='" + this.vungId + "'");	
		return kvRS;
		
	}

	private ResultSet createNppRS() {  	
		ResultSet nppRS;
	
		if(!this.kvId.equals("0")){
			nppRS = this.db.get("select distinct pk_seq, ten from nhaphanphoi where trangthai='1' and khuvuc_fk ='" + this.kvId + "'");
		}else{
			nppRS = null;
		}
		return nppRS;
			
	}
	
	
	private void createKhRS(){  	
		String query=" SELECT * FROM ( ";
		
		if(this.id.length()>0)
		{
			query +=   " SELECT CONVERT(NVARCHAR,NKH_NPP.NPP_FK)+','+CONVERT(NVARCHAR,NKH_NPP.LOAINPP) AS PK_SEQ,ISNULL(NPP.MAFAST,KH.MAFAST) AS MAFAST,ISNULL(NPP.TEN,KH.TEN) AS TEN,1 AS CHON,NKH_NPP.LOAINPP AS LOAINPP FROM NHOMKHACHHANGNPP_NPP NKH_NPP LEFT JOIN "+util.prefixDMS+"NHAPHANPHOI NPP ON NPP.PK_SEQ=NKH_NPP.NPP_FK and NKH_NPP.LOAINPP in (1,2,4)  " +
						" LEFT JOIN "+util.prefixDMS+"KHACHHANG kh on kh.pk_seq = NKH_NPP.NPP_FK and NKH_NPP.LOAINPP=3 \n"+
					   " WHERE NHOMKHACHHANGNPP_FK="+this.id+" \n"+
					   " UNION ALL \n";
			
		}
			query +=
				 	   " select CONVERT(NVARCHAR,PK_SEQ)+',' +'1' AS PK_SEQ,MaFAST,TEN,0 AS CHON,1 AS LOAINPP from "+util.prefixDMS+"NHAPHANPHOI where loaiNPP in (4,5) and TRUCTHUOC_FK =1 \n"+
					   " and isKHACHHANG=0 \n";
		if(this.id.length()>0)
		{
			query +=   " AND PK_SEQ NOT IN (SELECT NPP_FK FROM NHOMKHACHHANGNPP_NPP WHERE NHOMKHACHHANGNPP_FK="+this.id+" AND LOAINPP=1) " ;
		}
			query +=   " UNION \n"+
					   " select CONVERT(NVARCHAR,PK_SEQ)+',' +'2' AS PK_SEQ,MaFAST,TEN,0 AS CHON,2 AS LOAINPP from "+util.prefixDMS+"NHAPHANPHOI where isKHACHHANG=1 \n";
	    if(this.id.length()>0)
		{
			query +=   " AND PK_SEQ NOT IN (SELECT NPP_FK FROM NHOMKHACHHANGNPP_NPP WHERE NHOMKHACHHANGNPP_FK="+this.id+" AND LOAINPP=2) " ;
		}
	    	query +=   " UNION \n"+
	    				" select CONVERT(NVARCHAR,PK_SEQ)+',' +'3' AS PK_SEQ,MaFAST,TEN,0 AS CHON,3 AS LOAINPP from "+util.prefixDMS+"KHACHHANG where mauho='1' \n";
	    if(this.id.length()>0)
		{
				query +=   " AND PK_SEQ NOT IN (SELECT NPP_FK FROM NHOMKHACHHANGNPP_NPP WHERE NHOMKHACHHANGNPP_FK="+this.id+" AND LOAINPP=3) " ;
		}
		query +=   " UNION \n"+
				" select CONVERT(NVARCHAR,PK_SEQ)+',' +'4' AS PK_SEQ,MaFAST,TEN,0 AS CHON,4 AS LOAINPP from "+util.prefixDMS+"NHAPHANPHOI where congnochung='1' \n";
		if(this.id.length()>0)
		{
				query +=   " AND PK_SEQ NOT IN (SELECT NPP_FK FROM NHOMKHACHHANGNPP_NPP WHERE NHOMKHACHHANGNPP_FK="+this.id+" AND LOAINPP=4) " ;
		}

	    
					   
	    query += " ) Data WHERE 1=1 ";	   
	    
	    if(this.loaiNpp.length()>0)
		{
			query +=   " AND data.loainpp ="+this.loaiNpp+" " ;
		}
		   
	    query += " ORDER BY DATA.CHON DESC,Data.MAFAST ASC  ";
		
		System.out.print(query);
		this.khList = this.db.get(query);	
		
	
		
	}
	
	public void UpdateRS(){
//		this.vungList = createVungRS();
//		this.kvList = createKvRS();
//		this.nppList = createNppRS();
		
		createKhRS();

	}
	public String getCongtyId() 
	{
		return this.congtyId;
	}

	public void setCongtyId(String congtyId) 
	{
		this.congtyId = congtyId;
	}
	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	@Override
	public void DBClose() {
		// TODO Auto-generated method stub
		this.db.shutDown();
	}

	public String getLoaiNpp() {
		return loaiNpp;
	}

	public void setLoaiNpp(String loaiNpp) {
		this.loaiNpp = loaiNpp;
	}

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}


}


