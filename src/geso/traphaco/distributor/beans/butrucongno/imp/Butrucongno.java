package geso.traphaco.distributor.beans.butrucongno.imp;

import geso.traphaco.distributor.beans.butrucongno.IButrucongno;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import java.text.NumberFormat;

public class Butrucongno implements IButrucongno, Serializable
{
	private static final long serialVersionUID = -9217977546733610214L;
	String userId;
	String id;
	String ngay;
	String trangthai;
	String tungay;
	String ngaytao;
	String nguoitao;
	String ngaysua;
	String nguoisua;
	String denngay;
	String msg;
	
	String sohoadontu = "";
	String sohoadonden = "";
	String sotientu = "";
	String sotienden = "";
	
	String ghichu;
	
	String nppId;
	String nppTen;
	String sitecode;
	
	ResultSet khlist;
	String khId;
	
	String DdkdId;
	ResultSet Ddkdlist;

	String[] HdIds;
	String[] KhIds;
	
	String[] HdChon;
	String[] HdId;
	String[] HdKhid;
	String[] HdMakh;
	String[] HdMadt;
	String[] HdNgayhd;
	String[] HdKyhieu;
	String[] HdSohd;
	String[] HdSotien;
	
	String[] HdDuno;
	String[] HdDuco;
	String[] HdGhino;
	String[] HdGhico;
	
	String[] HdDunos;
	String[] HdDucos;
	String[] HdGhinos;
	String[] HdGhicos;
	
	ResultSet hoadonlist;
	String[] khIds;
	
	dbutils db;
	
	String tc_duno_tc;
	String tc_duco_tc;
	
	String tc_ghi_co;
	String tc_ghi_no;
	String ckKh;
	
	public Butrucongno(String[] param)
	{
		this.id = param[0];
		this.ngay = param[1];
		this.trangthai = param[2];
		this.tungay = param[3];
		this.ngaytao = param[4];
		this.nguoitao = param[5];
		this.ngaysua = param[6];
		this.nguoisua = param[7];
		this.denngay = param[8];
		this.khId = "";
		this.DdkdId="";
		this.msg = "";
		this.tc_duco_tc="0";
		this.tc_duno_tc="0";
		this.tc_ghi_co="0";
		this.tc_ghi_no="0";
		this.ckKh="";
		db = new dbutils();
	}
	
	public Butrucongno(String id)
	{
		this.id = id;
		this.ngay = "";
		this.trangthai = "2";
		this.tungay = "";
		this.ngaytao = "";
		this.nguoitao = "";
		this.ngaysua = "";
		this.nguoisua = "";
		this.denngay = "";
		this.khId = "";
		this.msg = "";
		this.ghichu= "";
		this.sohoadonden= "";
		this.sohoadontu = "";
		this.sotienden= "";
		this.sotientu = "";
		this.DdkdId="";
		this.tc_duco_tc="0";
		this.tc_duno_tc="0";
		this.tc_ghi_co="0";
		this.tc_ghi_no="0";
		this.ckKh="";
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
	
	public String getId()
	{
		return this.id;
	}
	
	public void setId(String id)
	{
		this.id = id;
	}
	
	public String getNgay()
	{
		return this.ngay;
	}
	
	public void setNgay(String ngay)
	{
		this.ngay = ngay;
	}
	
	public String getTrangthai()
	{
		return this.trangthai;
	}
	
	public void setTrangthai(String trangthai)
	{
		this.trangthai = trangthai;
	}
	
	public String getTungay() 
	{
		return this.tungay;
	}
	
	public void setTungay(String tungay)
	{
		this.tungay = tungay;
	}
	
	public String getNgaytao()
	{
		return this.ngaytao;
	}
	
	public void setNgaytao(String ngaytao)
	{
		this.ngaytao = ngaytao;
	}
	
	public String getNguoitao()
	{
		return this.nguoitao;
	}
	
	public void setNguoitao(String nguoitao)
	{
		this.nguoitao = nguoitao;
	}
	
	public String getNgaysua()
	{
		return this.ngaysua;
	}
	
	public void setNgaysua(String ngaysua)
	{
		this.ngaysua = ngaysua;
	}
	
	public String getNguoisua()
	{
		return this.nguoisua;
	}
	
	public void setNguoisua(String nguoisua)
	{
		this.nguoisua = nguoisua;
	}
	
	public String getDenngay()
	{
		return this.denngay;
	}
	
	public void setDenngay(String denngay)
	{
		this.denngay = denngay;
	}
	
	public String getMessage() 
	{
		return this.msg;
	}
	
	public void setMessage(String msg) 
	{
		this.msg = msg;
	}
	
	public String getNppId() 
	{
		return this.nppId;
	}

	public void setNppId(String nppId) 
	{
		this.nppId = nppId;
	}
	
	public String getNppTen() 
	{
		return this.nppTen;
	}

	public void setNppTen(String nppTen) 
	{
		this.nppTen = nppTen;
	}
	
	public String getSitecode() 
	{
		return this.sitecode;
	}

	public void setSitecode(String sitecode) 
	{
		this.sitecode = sitecode;
	}

	public ResultSet getKhList() 
	{
		return this.khlist;
	}
	
	public void setKhList(ResultSet khlist) 
	{
		this.khlist = khlist;
	}
	
	public String getKhId() 
	{		
		return this.khId;
	}
	
	public void setKhId(String khId) 
	{
		this.khId = khId;		
	}
	
	

	public ResultSet getHoadonList() 
	{		
		return this.hoadonlist;
	}
	
	public void setHoadonList(ResultSet hoadonList) 
	{
		this.hoadonlist = hoadonList;		
	}

/*	public Hashtable<Integer, String> getKhIds() 
	{		
		Hashtable<Integer, String> selected = new Hashtable<Integer, String>();
		if(this.khIds != null){
			int size = (this.khIds).length;
			int m = 0;
			while(m < size){
				selected.put(new Integer(m), this.khIds[m]) ;
				m++;
			}
		}else{
			selected.put(new Integer(0), "null");
		}
		return selected;
	}*/
	
	public void setKhIds(String[] khIds) 
	{
		this.khIds = khIds;
	}
	
	private void getNppInfo()
	{	
		//Phien ban moi
		geso.traphaco.distributor.util.Utility util=new geso.traphaco.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();
		this.sitecode=util.getSitecode();
	}
	
	public boolean CreateNvgn()
	{	
		
		this.ngaytao = getDateTime();
		this.nguoitao = this.userId;
		ResultSet rsbtcn = null;
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			String query ="";
			if(ckKh.trim().length()<=0)//CHECK BÙ TRỪ THEO HÓA ĐƠN
			{
				query = " insert into BUTRUCONGNO(NGAYCHUNGTU, TRANGTHAI, GHICHU, NGAYTAO, NGAYSUA, NGUOITAO, NGUOISUA, NPP_FK, LOAIBUTRU) " +
							"values('"+ngay+"',0, N'" + this.ghichu + "','" + this.ngaytao + "','" + this.ngaytao + "','" + this.nguoitao +"','" + this.nguoitao +"','" + this.nppId +"',"+0+")";
			System.out.print("INSERT BUTRUCONGNO THEO HOA DON ________"+ query);
			}
			
			if(ckKh.trim().length()>0)//CHECK BÙ TRỪ THEO KHÁCH HÀNG
			{
				query = " insert into BUTRUCONGNO(NGAYCHUNGTU, TRANGTHAI, GHICHU, NGAYTAO, NGAYSUA, NGUOITAO, NGUOISUA, NPP_FK,LOAIBUTRU) " +
				" values('"+ngay+"',0, N'" + this.ghichu + "','" + this.ngaytao + "','" + this.ngaytao + "','" + this.nguoitao +"','" + this.nguoitao +"','" + this.nppId + "',"+1+")";
				
				System.out.print("INSERT BUTRUCONGNO THEO KHACH HANG 1 ________"+ query);
			}
			
			
			if(!db.update(query))
			{
				db.update("rollback");
				this.msg = "Khong the tao moi 'BUTRUCONGNO' , " + query;
				return false; 
			}
			
			query = "select IDENT_CURRENT('BUTRUCONGNO') as btcnId";
			System.out.println(query);
			rsbtcn = this.db.get(query);	
			if(rsbtcn!=null)
			{
			  while(rsbtcn.next())
			  {
				  this.id = rsbtcn.getString("btcnId");
			  }
			  rsbtcn.close();
			}
			
			if(ckKh.trim().length()<=0)//cấn trừ công nợ theo hóa đơn
			{				
				if(this.HdIds.length > 0)
				{									
					for(int i = 0; i < this.HdIds.length; i++)
					{
						if(this.HdDuno[i]==null||this.HdDuno[i].equals(""))
							this.HdDuno[i]="0";
						if(this.HdDuco[i]==null||this.HdDuco[i].equals(""))
							this.HdDuco[i]="0";
						if(this.HdGhino[i]==null||this.HdGhino[i].equals(""))
							this.HdGhino[i]="0";
						if(this.HdGhico[i]==null||this.HdGhico[i].equals(""))
							this.HdGhico[i]="0";
												
						if(HdIds[i].length()>0)
						{
							String sql = "insert into BUTRUCONGNO_HOADON(BTCN_FK, HOADON_FK, KHACHHANG_FK, DUNO, DUCO, GHINO, GHICO)" +
								         " values('" + this.id + "', '" + this.HdIds[i] + "', '" + this.HdKhid[i] + "', '" + this.HdDuno[i].replaceAll(",", "") + "', '"+this.HdDuco[i].replaceAll(",", "") + "', '"+this.HdGhino[i].replaceAll(",", "") +"', '" + this.HdGhico[i].replaceAll(",", "") +"')";
							System.out.print("INSERT BUTRUCONGNO_HOADON________"+ sql);
							if(!db.update(sql))
							{
								db.update("rollback");
								this.msg = "Khong the cap nhat BUTRUCONGNO_HOADON, " + sql;
								return false; 
							}
						}
					}
				}	
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
			else//cấn trừ công nợ theo khách hàng
			{
				if(khIds.length>0)
				{
					for(int i = 0; i < this.khIds.length; i++)
					{
						if(this.HdDuno[i]==null||this.HdDuno[i].equals(""))
							this.HdDuno[i]="0";
						if(this.HdDuco[i]==null||this.HdDuco[i].equals(""))
							this.HdDuco[i]="0";
						if(this.HdGhino[i]==null||this.HdGhino[i].equals(""))
							this.HdGhino[i]="0";
						if(this.HdGhico[i]==null||this.HdGhico[i].equals(""))
							this.HdGhico[i]="0";
												
						if(khIds[i].length()>0)
						{
							String sql="";							
							sql = "insert into BUTRUCONGNO_HOADON(BTCN_FK, KHACHHANG_FK, DUNO, DUCO, GHINO, GHICO)" +
								         " values('" + this.id + "', '" + this.HdKhid[i] + "', '" + this.HdDuno[i].replaceAll(",", "") + "', '"+this.HdDuco[i].replaceAll(",", "") + "', '"+this.HdGhino[i].replaceAll(",", "") +"', '" + this.HdGhico[i].replaceAll(",", "") +"')";
							System.out.print("INSERT BUTRUCONGNO_HOADON________"+ sql);
							if(!db.update(sql))
							{
								db.update("rollback");
								this.msg = "Khong the cap hat BUTRUCONGNO_HOADON, " + sql;
								return false; 
							}
						}
					}
				}
				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
			}
		} 
		catch(Exception e) {
			db.update("rollback");
			e.printStackTrace();
			this.msg = "Loi khi cap nhat bang "+e.toString();
			return false; 
		}
		finally{try {
			if(rsbtcn != null)
				rsbtcn.close();
		} catch (Exception e2) {
		}}
		return true;	
	}
	
	public boolean UpdateNvgn()
	{
		this.ngaysua = getDateTime();
		this.nguoisua = this.userId;
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			String query="";
			if(this.ckKh.trim().length()<=0)//tick chọn bù trừ công nợ theo hóa đơn
			{	
				query = "update BUTRUCONGNO set ngaychungtu = '" + this.ngay + "', ghichu= N'" + this.ghichu + "', "+
				               "      nguoisua='" + this.nguoisua + "',  ngaysua='" + this.ngaysua + "', npp_fk = '"+ this.nppId +"', " +
				               " loaibutru="+0+""+
				               " where pk_seq='" + this.id + "'";
			}
			else //tick chọn bù trừ công nợ theo khách hàng
			{
				query = "update BUTRUCONGNO set ngaychungtu = '" + this.ngay + "', ghichu= N'" + this.ghichu + "', "+
	               "      nguoisua='" + this.nguoisua + "',  ngaysua='" + this.ngaysua + "', npp_fk = '"+ this.nppId +"', " +
	               " 	loaibutru="+1+""+
	               " 	where pk_seq='" + this.id + "'";
			}
			System.out.println("UPDATE BUTRUCONGNO ____"+ query);
			
			if(!db.update(query))
			{
				db.getConnection().rollback();
				this.msg = "Khong the cap nhat 'BUTRUCONGNO' , " + query;
				return false; 
			}	
			
			query = "delete BUTRUCONGNO_HOADON where BTCN_FK = '" + this.id + "'";
			
			System.out.println("DELETE BUTRUCONGNO ____"+ query);
			if(!db.update(query))
			{
				db.getConnection().rollback();
				this.msg = query;
				return false;
			}
			
			if(this.ckKh.trim().length()<=0)
			{
				if(this.HdIds.length > 0)
				{									
					for(int i = 0; i < this.HdIds.length; i++)
					{
						if(this.HdDuno[i]==null||this.HdDuno[i].equals(""))
							this.HdDuno[i]="0";
						if(this.HdDuco[i]==null||this.HdDuco[i].equals(""))
							this.HdDuco[i]="0";
						if(this.HdGhino[i]==null||this.HdGhino[i].equals(""))
							this.HdGhino[i]="0";
						if(this.HdGhico[i]==null||this.HdGhico[i].equals(""))
							this.HdGhico[i]="0";
						System.out.println("HĐN: "+this.HdDuno[i]);
						System.out.println("HĐN: "+this.HdDuco[i]);
						System.out.println("HĐN: "+this.HdGhino[i]);
						System.out.println("KH: "+this.HdKhid[i]);
						
						System.out.print("INSERT HDDUNO________"+ this.HdDuno[i]);
						
						if(HdIds[i].length()>0)
						{
							String sql = "insert into BUTRUCONGNO_HOADON(BTCN_FK, HOADON_FK, KHACHHANG_FK, DUNO, DUCO, GHINO, GHICO)" +
								         " values('" + this.id + "', '" + this.HdIds[i] + "', '" + this.HdKhid[i] + "', '" + this.HdDuno[i].replaceAll(",", "") + "', '"+this.HdDuco[i].replaceAll(",", "") + "', '"+this.HdGhino[i].replaceAll(",", "") +"', '" + this.HdGhico[i].replaceAll(",", "") +"')";
							System.out.print("INSERT BUTRUCONGNO_HOADON________"+ sql);
							if(!db.update(sql))
							{
								db.update("rollback");
								this.msg = "Khong the cap nhat BUTRUCONGNO_HOADON, " + sql;
								return false; 
							}
						}
					}
				}
				
				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
			}
			else
			{
				if(khIds.length>0)
				{
					for(int i = 0; i < this.khIds.length; i++)
					{
						if(this.HdDuno[i]==null||this.HdDuno[i].equals(""))
							this.HdDuno[i]="0";
						if(this.HdDuco[i]==null||this.HdDuco[i].equals(""))
							this.HdDuco[i]="0";
						if(this.HdGhino[i]==null||this.HdGhino[i].equals(""))
							this.HdGhino[i]="0";
						if(this.HdGhico[i]==null||this.HdGhico[i].equals(""))
							this.HdGhico[i]="0";
												
						if(khIds[i].length()>0)
						{
							String sql = "insert into BUTRUCONGNO_HOADON(BTCN_FK, KHACHHANG_FK, DUNO, DUCO, GHINO, GHICO)" +
								         " values('" + this.id + "', '" + this.HdKhid[i] + "', '" + this.HdDuno[i].replaceAll(",", "") + "', '"+this.HdDuco[i].replaceAll(",", "") + "', '"+this.HdGhino[i].replaceAll(",", "") +"', '" + this.HdGhico[i].replaceAll(",", "") +"')";
							System.out.print("INSERT BUTRUCONGNO_HOADON________"+ sql);
							if(!db.update(sql))
							{
								db.update("rollback");
								this.msg = "Khong the cap hat BUTRUCONGNO_HOADON, " + sql;
								return false; 
							}
						}
					}
				}
				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
			
			}
		} 
		catch(Exception e) {
			db.update("rollback");
			this.msg = "Loi khi cap nhat bang "+e.toString();
			return false; 
		}
		return true;
	}
	
	public void init() 
	{
		this.getNppInfo();
		String query =  " select a.pk_seq , a.SOCHUNGTU , isnull(a.NGAYBUTRU,'') as NGAYBUTRU, KH_CHUYENNO, KH_NHANNO, TONGTIEN ";
		query = query + " from ERP_BUTRUKHACHHANG " +
				        " where  and a.pk_seq = '"+ this.id +"' " +
				        " order by a.pk_seq desc";
		ResultSet rs =  db.get(query );
		System.out.println(query );
		try
        {
            rs.next();        	
            this.id = rs.getString("pk_seq");
			this.ngay = rs.getString("ngaychungtu");
			this.ghichu = rs.getString("ghichu");						
			rs.close();			
       	}
        catch(Exception e){}
        finally{try {
			if(rs != null)
				rs.close();
		} catch (Exception e2) {
		
		}}
       		
        createRS();
	}
	
	public void createDDKDRS(){
		//GET DAI DIEN KINH DOANH
		String query = "select pk_seq, ten from DAIDIENKINHDOANH where trangthai = '1' and npp_fk ='"+ this.nppId +"' ";
		this.Ddkdlist = db.get(query);
		System.out.println("Cau INIT DDKD _______:" + query);		
	}
	
	public void createKHRS(){
		//GET KHACH HANG
		String query ="select distinct  kh.PK_SEQ, (kh.maFast + '---' + kh.Ten) as Ten " +
			"	from KHACHHANG kh inner join KHACHHANG_TUYENBH kh_tbh on kh.PK_SEQ=kh_tbh.KHACHHANG_FK "+
								" inner join TUYENBANHANG tbh on tbh.PK_SEQ=kh_tbh.TBH_FK" +
								" where 1=1 and kh.npp_fk= '"+this.nppId+"' ";
	        if(DdkdId.length()>0)
	        {
	        	query+= " and tbh.ddkd_fk = '"+this.DdkdId+"'";
	        }
			this.khlist = db.get(query);
			System.out.println("Cau INIT KH ________:" + query);
			createDDKDRS();
	}
	
	//CAU LAY DU LIEU BU TRU
	public void createRS() 
	{
		this.getNppInfo();
		
		createDDKDRS();
        createKHRS();
        
		String query = "";
		
		String conditionbthd = " ";
		String conditionxnkh=" ";
		String conditiontt="";
		
		System.out.println("nppId:"+ nppId);
		if(nppId.trim().length()>0)
		{
			conditionbthd += " and bt.NPP_FK='"+this.nppId+"'";
		}
		
		if(nppId.trim().length()>0)
		{
			conditionxnkh +=" and xn.NPP_FK='"+this.nppId+"'";
		}
		
		if(nppId.trim().length()>0)
		{
			conditiontt += " and tt.NPP_FK='"+this.nppId+"'";
		}
		
		if(this.id.trim().length() > 0)
		{	
			//CHON PHIEU BU TRU CONG NO
		query="SELECT LOAIBUTRU FROM BUTRUCONGNO WHERE PK_SEQ='"+this.id+"'";
		ResultSet rslt = db.get(query);
		String loaibutru="";
		db.get(query);
		if(rslt!= null)
			{ 
				try {
					while(rslt.next())
					{	 
						loaibutru= rslt.getString("LOAIBUTRU");
					}
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
			}
		
		if(loaibutru.equals("0"))
			{
			query=
				" SELECT  kh.PK_SEQ as khId, (kh.maFAST + '---'+ kh.TEN) as tenKH, hd.PK_SEQ as Id, hd.NGAYXUATHD, hd.SOHOADON, \n"+
				" 		  bthd.DUNO, bthd.DUCO, bthd.GHINO, bthd.GHICO, hd.PK_SEQ as CHON \n"+	   
				" FROM 	  BUTRUCONGNO  bt inner join BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK and bthd.KHACHHANG_FK in (SELECT PK_SEQ from KHACHHANG where KBH_FK='100025') \n"+
				"			 inner join HOADON hd on bthd.HOADON_FK = hd.PK_SEQ \n"+
				"			 inner join KHACHHANG kh on bthd.KHACHHANG_FK = kh.PK_SEQ \n"+
				" WHERE   bt.PK_SEQ ='"+this.id+"' \n"+
				
				" UNION ALL \n" +
				
				" SELECT  kh.PK_SEQ as khId, (kh.maFAST + '---'+ kh.TEN) as tenKH, hd.PK_SEQ as Id, hd.NGAYXUATHD, hd.SOHOADON, \n"+
				"		  bthd.DUNO, bthd.DUCO, bthd.GHINO, bthd.GHICO, hd.PK_SEQ as CHON \n"+	   
				" FROM 	  BUTRUCONGNO  bt inner join BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK and bthd.KHACHHANG_FK in (SELECT PK_SEQ from KHACHHANG where KBH_FK='100052') \n"+
				"					 inner join ERP_HOADONNPP hd on bthd.HOADON_FK = hd.PK_SEQ \n"+
				"					 inner join KHACHHANG kh on bthd.KHACHHANG_FK = kh.PK_SEQ \n"+
				" WHERE   bt.PK_SEQ ='"+this.id+"' \n";	
			this.ckKh="";
			}
		else
			{
			query=
				" SELECT  kh.PK_SEQ as khId, (kh.maFAST + '---'+ kh.TEN) as tenKH, \n"+
				" 		  bthd.DUNO, bthd.DUCO, bthd.GHINO, bthd.GHICO, kh.PK_SEQ as CHON \n"+	   
				" FROM 	  BUTRUCONGNO  bt inner join BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK and bthd.KHACHHANG_FK in (SELECT PK_SEQ from KHACHHANG where KBH_FK='100025') \n"+				
				"			 inner join KHACHHANG kh on bthd.KHACHHANG_FK = kh.PK_SEQ \n"+
				" WHERE   bt.PK_SEQ ='"+this.id+"' \n"+
				
				" UNION ALL \n" +
				
				" SELECT  kh.PK_SEQ as khId, (kh.maFAST + '---'+ kh.TEN) as tenKH, \n"+
				"		  bthd.DUNO, bthd.DUCO, bthd.GHINO, bthd.GHICO, kh.PK_SEQ as CHON \n"+	   
				" FROM 	  BUTRUCONGNO  bt inner join BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK and bthd.KHACHHANG_FK in (SELECT PK_SEQ from KHACHHANG where KBH_FK='100052') \n"+				
				"					 inner join KHACHHANG kh on bthd.KHACHHANG_FK = kh.PK_SEQ \n"+
				" WHERE   bt.PK_SEQ ='"+this.id+"' \n";
				this.ckKh="1";
			}
		}
		
		if(this.tungay.trim().length() > 0 || this.denngay.trim().length() > 0 || this.sohoadontu.trim().length() > 0 || this.sohoadonden.trim().length() > 0 || this.sotientu.trim().length() > 0 || this.sotienden.trim().length() > 0)
		  {			
			
			if(this.id.trim().length() > 0)
			{
				query +="UNION ALL  \n";
			}
			
			if(ckKh.trim().length()<=0)// TICK CHỌN BU TRU THEO HOA DON
			{
			query+= 				
				"	SELECT	hd.KHACHHANG_FK as khId,(kh.maFAST + '---' + kh.TEN) as tenKH, hd.PK_SEQ as Id, hd.NGAYXUATHD, hd.SOHOADON, \n"+ 
				"	 	    round((hd.tongtienAVAT  + ISNULL(BUTRUNO.BUTRUNO,0) - ISNULL(THUTIEN.DATHU,0) - ISNULL(XOANO.DAXOA,0) - ISNULL(BUTRUCO.BUTRUCO,0)),0) as DUNO, 0 as DUCO, 0 as GHINO, 0 as GHICO, 0 as CHON \n"+ 
					 
				"	FROM	( \n"+
				"			 SELECT * \n"+
				"			 FROM ( \n"+
				"					SELECT hd.SOHOADON,hd.TRANGTHAI, hd.NGAYXUATHD, hd.LOAIHOADON, hd.NPP_FK, hd.KHACHHANG_FK,hd.PK_SEQ, hd.tongtienavat as tongtienAVAT \n"+ 
				"					FROM HOADON hd \n"+
				"                   WHERE hd.LOAIHOADON = 0 and hd.TRANGTHAI in (2,4) and hd.NGAYXUATHD >='"+this.tungay+"' and hd.NGAYXUATHD <='"+this.denngay+"' \n";
			
				if(this.DdkdId.trim().length()>0)
				{
					query+="           				and hd.KHACHHANG_FK in \n"+ 
					"														( \n"+ 
					"															SELECT 	c.KHACHHANG_FK \n"+ 
					"															FROM 	DAIDIENKINHDOANH a inner join TUYENBANHANG b on b.DDKD_FK=a.PK_SEQ \n"+ 
					"																	inner join KHACHHANG_TUYENBH c on c.TBH_FK=b.PK_SEQ \n"+ 
					"															WHERE a.PK_SEQ ='"+this.DdkdId+"' \n"+ 
					"														) \n";
				}
				if(nppId.trim().length()>0)
					query += "    					and hd.NPP_FK ='"+this.nppId+"' \n";
				
				query+=
				"	             ) hd) hd \n"+
				"						LEFT JOIN \n"+ 
				"							 ( \n"+ 
				"						SELECT tt.KHACHHANG_FK,tt.HOADONNPP_FK, round(SUM(isnull(tt.sotienTT,0)),0) as DATHU \n"+ 
				"	  							FROM   HOADON hd inner join KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ \n"+ 
				"	   									inner join ( \n"+ 
				"	      												SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(ISNULL(tthd.sotienTT,0),0)) as sotienTT \n"+ 
				"	      												FROM ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK \n"+ 
				"	      												WHERE tt.TRANGTHAI = 1 and tt.NGAYCHUNGTU <='"+denngay+"' and tt.NGAYCHUNGTU >='"+tungay+"' \n"+ 
				"	      												GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK \n"+ 
				"	     											) \n"+ 
				"	     											tt on hd.KHACHHANG_FK = tt.KHACHHANG_FK and hd.PK_SEQ = tt.HOADONNPP_FK \n"+ 
				"	 							WHERE 1=1 and hd.NGAYXUATHD >='"+tungay+"' and hd.NGAYXUATHD <='"+denngay+"' \n";
						
				if(this.DdkdId.trim().length()>0)
				{
					query+="           and hd.KHACHHANG_FK in \n"+ 
					"														( \n"+ 
					"															SELECT 	c.KHACHHANG_FK \n"+ 
					"															FROM 	DAIDIENKINHDOANH a inner join TUYENBANHANG b on b.DDKD_FK=a.PK_SEQ \n"+ 
					"																	inner join KHACHHANG_TUYENBH c on c.TBH_FK=b.PK_SEQ \n"+ 
					"															WHERE a.PK_SEQ ='"+this.DdkdId+"' \n"+ 
					"														) \n";
				}
				if(nppId.trim().length()>0)
					 query+= "        and hd.NPP_FK ='"+nppId+"'";
				
				query+=
				"	 							GROUP BY tt.KHACHHANG_FK,tt.HOADONNPP_FK \n"+ 
				"							 ) as THUTIEN on hd.PK_SEQ=THUTIEN.HOADONNPP_FK and hd.KHACHHANG_FK =THUTIEN.KHACHHANG_FK \n"+ 
				"					  LEFT JOIN \n"+ 
				"							 ( \n"+ 
				"								SELECT	xnhd.KHACHHANG_FK,xnhd.HOADON_FK, round(SUM(xnhd.SOTIENXOA),0) as DAXOA \n"+ 
				"								FROM	XOANOKHACHHANG_HOADON xnhd inner join XOANOKHACHHANG xn on xnhd.XNKH_FK = xn.PK_SEQ \n"+ 
				"								WHERE	xn.TRANGTHAI = 1 \n"; 
				if(nppId.trim().length()>0)
						 query+= "        and xn.NPP_FK ='"+nppId+"'";
				if(this.DdkdId.trim().length()>0)
				{
					   query+=
						"		  and xnhd.KHACHHANG_FK in \n"+
						"		  						( \n"+
						"			 						SELECT 	c.KHACHHANG_FK \n"+ 
						"									FROM 	DAIDIENKINHDOANH a inner join TUYENBANHANG b on b.DDKD_FK=a.PK_SEQ \n"+   			
						"											inner join KHACHHANG_TUYENBH c on c.TBH_FK=b.PK_SEQ \n"+
						"									WHERE a.PK_SEQ ='"+this.DdkdId+"' \n"+
						"		  						) \n";
				   }
				query+=
				"								GROUP BY xnhd.HOADON_FK, xnhd.KHACHHANG_FK \n"+ 
				"							 ) as XOANO on hd.PK_SEQ=XOANO.HOADON_FK and XOANO.KHACHHANG_FK = hd.KHACHHANG_FK \n"+ 
				"					  LEFT JOIN \n"+ 
				"							 ( \n"+ 		
				"								SELECT  bt.KHACHHANG_FK,bt.HOADON_FK, round(SUM(ISNULL(GHICO,0)),0) as BUTRUCO \n"+ 
				"		  						FROM 	HOADON hd inner join KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ \n"+ 
				"			   										inner join ( \n"+ 
				"				  													SELECT 	bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHICO,0),0)) as GHICO \n"+ 
				"			  	  													FROM 	BUTRUCONGNO bt inner join BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK \n"+
				"				  													WHERE   bt.TRANGTHAI = 1 \n"+
				"		 		  													GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK \n"+ 
				"	 			 												) \n"+ 
				"				 												 bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK \n"+ 
				"								WHERE 	1=1 and hd.NGAYXUATHD <='"+denngay+"' and hd.NGAYXUATHD >='"+tungay+"' \n"; 
				if(this.DdkdId.trim().length()>0)
				{
					query+="           and hd.KHACHHANG_FK in \n"+ 
					"														( \n"+ 
					"															SELECT 	c.KHACHHANG_FK \n"+ 
					"															FROM 	DAIDIENKINHDOANH a inner join TUYENBANHANG b on b.DDKD_FK=a.PK_SEQ \n"+ 
					"																	inner join KHACHHANG_TUYENBH c on c.TBH_FK=b.PK_SEQ \n"+ 
					"															WHERE a.PK_SEQ ='"+this.DdkdId+"' \n"+ 
					"														) \n";
				}
				if(nppId.trim().length()>0)
					 query+= "        and kh.NPP_FK ='"+nppId+"'";
				
				query+=
				"		 						GROUP BY bt.KHACHHANG_FK,bt.HOADON_FK \n"+
				"							  ) as BUTRUCO on hd.PK_SEQ = BUTRUCO.HOADON_FK and hd.KHACHHANG_FK = BUTRUCO.KHACHHANG_FK \n"+
				"						LEFT JOIN \n"+ 
				"						 ( \n"+ 							
				"								SELECT  bt.KHACHHANG_FK,bt.HOADON_FK, round(SUM(ISNULL(GHINO,0)),0) as BUTRUNO \n"+
				"		  						FROM 	HOADON hd inner join KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ \n"+ 
				"			   										inner join ( \n"+ 
				"				  													SELECT 	bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHINO,0),0)) as GHINO \n"+
				"			  	  													FROM 	BUTRUCONGNO bt inner join BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK \n"+  
				"				  													WHERE   bt.TRANGTHAI = 1 \n"+
				"		 		  													GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK \n"+ 
				"	 			 												) \n"+ 
				"				 												 bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK \n"+ 
				"								WHERE 	1=1 and hd.NGAYXUATHD <='"+denngay+"' and hd.NGAYXUATHD >='"+tungay+"' \n";
				if(this.DdkdId.trim().length()>0)
				{
					query+="           and hd.KHACHHANG_FK in \n"+ 
					"														( \n"+ 
					"															SELECT 	c.KHACHHANG_FK \n"+ 
					"															FROM 	DAIDIENKINHDOANH a inner join TUYENBANHANG b on b.DDKD_FK=a.PK_SEQ \n"+ 
					"																	inner join KHACHHANG_TUYENBH c on c.TBH_FK=b.PK_SEQ \n"+ 
					"															WHERE a.PK_SEQ ='"+this.DdkdId+"' \n"+ 
					"														) \n";
				}
				if(nppId.trim().length()>0)
					 query+= "        and kh.NPP_FK ='"+nppId+"'";
				
				query+="					GROUP BY bt.KHACHHANG_FK,bt.HOADON_FK \n"+
				"							  ) as BUTRUNO on hd.PK_SEQ = BUTRUNO.HOADON_FK and hd.KHACHHANG_FK = BUTRUNO.KHACHHANG_FK \n"+
				"					LEFT JOIN KHACHHANG kh on kh.PK_SEQ = hd.KHACHHANG_FK \n"+
				"	WHERE hd.TRANGTHAI in (2,4) and round((hd.tongtienavat + ISNULL(BUTRUNO.BUTRUNO,0) - ISNULL(THUTIEN.DATHU,0) - ISNULL(XOANO.DAXOA,0) - ISNULL(BUTRUCO.BUTRUCO,0)),0)<>0 and hd.LOAIHOADON =0 \n";
				
				 if(this.id.trim().length() > 0)
				   {
					 	query+= "    and hd.PK_SEQ NOT IN (select hoadon_fk from BUTRUCONGNO_HOADON where btcn_fk =  '"+ this.id +"') \n";   
				   }		
				
				 if(this.tungay.trim().length() > 0)
				   {
				    query += "     and hd.NGAYXUATHD >= '"+ this.tungay +"' \n";
				   }
				   if(this.denngay.trim().length() > 0)
				   {
				    query += "     and hd.NGAYXUATHD <= '"+ this.denngay +"' \n";
				   }
				   if(this.sohoadontu.trim().length() > 0)
				   {
				    query += "     and cast(hd.SOHOADON as numeric(18,0) ) >= '"+ Integer.parseInt(this.sohoadontu) +"' \n";
				   }
				   if(this.sohoadonden.trim().length() > 0)
				   {
				    query += "     and cast(hd.SOHOADON as numeric(18,0) ) <= '"+ Integer.parseInt(this.sohoadonden) +"' \n";
				   }
				
				   if(this.sotientu.trim().length() > 0)
				   {
					   double st = Double.parseDouble(this.sotienden.replaceAll(",", ""));
					   st=st*(-1);
				    query += "     and round((hd.tongtienavat + ISNULL(BUTRUNO.BUTRUNO,0) - ISNULL(THUTIEN.DATHU,0) - ISNULL(XOANO.DAXOA,0) - ISNULL(BUTRUCO.BUTRUCO,0)),0) >= "+ st +" \n";
				   }
				   if(this.sotienden.trim().length() > 0)
				   {
					   double sd = Double.parseDouble(this.sotienden.replaceAll(",", ""));
					   query += "     and round((hd.tongtienavat + ISNULL(BUTRUNO.BUTRUNO,0) - ISNULL(THUTIEN.DATHU,0) - ISNULL(XOANO.DAXOA,0) - ISNULL(BUTRUCO.BUTRUCO,0)),0) <= "+ sd +" \n";
				   }
				   
				   if(this.nppId.trim().length()>0)
				   {
					   query+= " and hd.NPP_FK='"+this.nppId+"' and hd.LOAIHOADON=0";
				   }
					
				   if(this.khId.trim().length()>0				   )
				   {
					   query +=" and hd.KHACHHANG_FK ='"+this.khId+"'";			   
				   }
				   
				   if(this.DdkdId.trim().length()>0)
				   {
					   query+=
						"		  and hd.KHACHHANG_FK in \n"+
						"		  						( \n"+
						"			 						SELECT 	c.KHACHHANG_FK \n"+ 
						"									FROM 	DAIDIENKINHDOANH a inner join TUYENBANHANG b on b.DDKD_FK=a.PK_SEQ \n"+   			
						"											inner join KHACHHANG_TUYENBH c on c.TBH_FK=b.PK_SEQ \n"+
						"									WHERE 	a.PK_SEQ ='"+this.DdkdId+"' \n "+
						"		  						) \n";
				   }
				   
				query+=
				" \n \n" +				
				"UNION ALL \n"+	 
				
				" \n \n"+
				"	SELECT hd.KHACHHANG_FK as khId,(kh.maFAST + '---' + kh.TEN) as tenKH, hd.PK_SEQ as Id, hd.NGAYXUATHD, hd.SOHOADON, \n"+ 
				"		   round((hd.tongtienavat + ISNULL(BUTRUNO.BUTRUNO,0) - ISNULL(THUTIEN.DATHU,0) - ISNULL(XOANO.DAXOA,0) - ISNULL(BUTRUCO.BUTRUCO,0)),0) as DUNO, 0 as DUCO, 0 as GHINO, 0 as GHICO, 0 as CHON \n"+ 
				"	FROM \n"+
				"			( \n"+
				"			 SELECT * \n"+
				"			 FROM ( \n"+
				"					SELECT  hd.PK_SEQ,hd.SOHOADON,hd.TRANGTHAI, hd.NGAYXUATHD,hd.LoaiHoaDon, hd.NPP_FK, hd.KHACHHANG_FK, round(SUM(hdETC.AVAT - hdETC.AVAT_CK),0) as tongtienavat \n"+ 
				"					FROM \n"+
				"						( \n"+
				"							SELECT  ETC.PK_SEQ,ETC.NGAYXUATHD, ETC.SOHOADON,ETC.KHACHHANG_FK,ETC.ddkd_fk,ETC.PK_SEQ as HOADONNPP_FK,npp_fk, \n"+ 
				"									sum(soluong) as soluong, ( sum( soluong * dongia ) / sum(soluong) ) as dongia, \n"+    
				"									sum( soluong * dongia )  as BVAT,( sum( soluong * dongia*thuexuat/100 ) ) as VAT,   \n"+
				"									sum( soluong * dongia*(1+thuexuat/100 ) ) as AVAT, \n"+
				"									sum(isnull(chietkhau,0)*(1+thuexuat/100)) as AVAT_CK,  \n"+         
				"									sum(isnull(thuexuat,0)) as BVAT_CK     \n"+
				"	   						FROM (   \n"+
				"									SELECT  c.HOADON_FK as PK_SEQ,a.NGAYXUATHD,a.SOHOADON,a.KHACHHANG_fk,c.HOADON_FK as HOADONNPP_FK, a.NPP_FK,c.chietkhau,c.vat, \n"+ 
				"											(   \n"+
				"												SELECT	top(1) bb.DDKD_FK \n"+   
				"	 											FROM 	ERP_HOADONNPP_DDH aa inner join ERP_DONDATHANGNPP bb on bb.PK_SEQ=aa.DDH_FK \n"+   
				"	  											WHERE 	aa.HOADONNPP_FK=c.HOADON_FK   \n"+
				"	  										) as ddkd_fk , 								\n"+					
				"	  											case when c.donvitinh = e.donvi then c.soluong else c.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as soluong, \n"+  
				"	 											case when c.donvitinh = e.donvi then c.dongia else c.dongia * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as dongia, c.vat as thuexuat \n"+    
				"									FROM 	ERP_HOADONNPP a  \n"+
				"	 												 inner join ERP_HOADONNPP_SP c on a.pk_seq = c.hoadon_fk \n"+  
				"	  												 inner join SANPHAM d on c.sanpham_fk = d.pk_seq  \n"+
				"	 												 inner join DONVIDOLUONG e on d.DVDL_FK = e.pk_seq  \n"+
				"	 								WHERE 	1=1 and c.SOLUONG > 0 and a.trangthai in ( 2,4 ) and a.NgayXuatHD >='"+tungay+"' and a.NgayXuatHD <='"+denngay+"' \n"+ 
				"								)ETC \n"+									
				"							GROUP BY ETC.PK_SEQ,ETC.NGAYXUATHD, ETC.SOHOADON,ETC.KHACHHANG_FK,ETC.ddkd_fk,ETC.PK_SEQ,npp_fk	\n"+							
				"						)as hdETC inner join ERP_HOADONNPP hd on hd.PK_SEQ=hdETC.HOADONNPP_FK  \n"+
				"									left join DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ=hdETC.DDKD_FK \n"+ 
				"									inner join KHACHHANG kh on kh.PK_SEQ=hdETC.KHACHHANG_FK \n"+
				"									inner join NHAPHANPHOI npp on npp.PK_SEQ=hdETC.NPP_FK  \n"+
				"					WHERE 1=1  \n";
				if(DdkdId.trim().length()>0)
				{
					   query+=
						"		  and hd.KHACHHANG_FK in \n"+
						"		  						( \n"+
						"			 						SELECT 	c.KHACHHANG_FK \n"+ 
						"									FROM 	DAIDIENKINHDOANH a inner join TUYENBANHANG b on b.DDKD_FK=a.PK_SEQ \n"+   			
						"											inner join KHACHHANG_TUYENBH c on c.TBH_FK=b.PK_SEQ \n"+
						"									WHERE a.PK_SEQ ='"+this.DdkdId+"' \n"+
						"		  						) \n";
				   }
				   if(nppId.trim().length()>0)
						 query+= "        and hd.NPP_FK ='"+nppId+"' \n";
				query+=
				"   GROUP BY hd.PK_SEQ,hd.SOHOADON,hd.TRANGTHAI, hd.NGAYXUATHD,hd.LoaiHoaDon, hd.NPP_FK, hd.KHACHHANG_FK \n"+
				"	             ) hd) hd \n"+
				"	 LEFT JOIN \n"+ 
				"				( \n"+ 	
				"					SELECT tt.KHACHHANG_FK,tt.HOADONNPP_FK, round(SUM(isnull(tt.sotienTT,0)),0) as DATHU \n"+ 
				"	  				FROM   ERP_HOADONNPP hd inner join KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ \n"+ 
				"	   								inner join ( \n"+ 
				"	      												SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(ISNULL(tthd.sotienTT,0),0)) as sotienTT \n"+ 
				"	      												FROM ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK \n"+ 
				"	      												WHERE tt.TRANGTHAI = 1 and tt.NGAYCHUNGTU <='"+denngay+"' and tt.NGAYCHUNGTU >='"+tungay+"' \n"+ 
				"	      												GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK \n"+ 
				"	     											) \n"+ 
				"	     											tt on hd.KHACHHANG_FK = tt.KHACHHANG_FK and hd.PK_SEQ = tt.HOADONNPP_FK \n"+ 
				"	 				WHERE 1=1 and hd.NGAYXUATHD >='"+tungay+"' and hd.NGAYXUATHD <='"+denngay+"' \n";
				if(this.DdkdId.trim().length()>0)
				{
					   query+=
						"		  and hd.KHACHHANG_FK in \n"+
						"		  						( \n"+
						"			 						SELECT 	c.KHACHHANG_FK \n"+ 
						"									FROM 	DAIDIENKINHDOANH a inner join TUYENBANHANG b on b.DDKD_FK=a.PK_SEQ \n"+   			
						"											inner join KHACHHANG_TUYENBH c on c.TBH_FK=b.PK_SEQ \n"+
						"									WHERE a.PK_SEQ ='"+this.DdkdId+"' \n"+
						"		  						) \n";
				   }
				   if(nppId.trim().length()>0)
						 query+= "        and kh.NPP_FK ='"+nppId+"'";
				   
				   query+=
				" 							GROUP BY tt.KHACHHANG_FK,tt.HOADONNPP_FK \n"+ 
				"				) as THUTIEN on hd.PK_SEQ=THUTIEN.HOADONNPP_FK and hd.KHACHHANG_FK =THUTIEN.KHACHHANG_FK \n"+ 
				"						LEFT JOIN \n"+ 
				"								( \n"+ 
				"									SELECT	xnhd.KHACHHANG_FK, xnhd.HOADON_FK, round(SUM(xnhd.SOTIENXOA),0) as DAXOA \n"+ 
				"									FROM	XOANOKHACHHANG_HOADON xnhd inner join XOANOKHACHHANG xn on xnhd.XNKH_FK = xn.PK_SEQ \n"+ 
				"									WHERE	xn.TRANGTHAI = 1 \n ";  
				 if(nppId.trim().length()>0)
						 query+= "        and xn.NPP_FK ='"+nppId+"' \n ";
				if(this.DdkdId.trim().length()>0)
				{
					   query+=
						"		  and xnhd.KHACHHANG_FK in \n"+
						"		  						( \n"+
						"			 						SELECT 	c.KHACHHANG_FK \n"+ 
						"									FROM 	DAIDIENKINHDOANH a inner join TUYENBANHANG b on b.DDKD_FK=a.PK_SEQ \n"+   			
						"											inner join KHACHHANG_TUYENBH c on c.TBH_FK=b.PK_SEQ \n"+
						"									WHERE a.PK_SEQ ='"+this.DdkdId+"' \n"+
						"		  						) \n";
				   }
				query+=
				"									GROUP BY xnhd.HOADON_FK , xnhd.KHACHHANG_FK \n"+ 
				"								) as XOANO on hd.PK_SEQ=XOANO.HOADON_FK and XOANO.KHACHHANG_FK = hd.KHACHHANG_FK \n"+ 
				"						LEFT JOIN \n"+ 
				"								( \n"+ 	
				"									SELECT  bt.KHACHHANG_FK,bt.HOADON_FK, round(SUM(ISNULL(GHICO,0)),0) as BUTRUCO \n"+ 
				"		  							FROM 	ERP_HOADONNPP hd inner join KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ \n"+
				"			   											inner join ( \n"+
				"				  														SELECT 	bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHICO,0),0)) as GHICO \n"+
				"			  	  														FROM 	BUTRUCONGNO bt inner join BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK \n"+
				"				  														WHERE   bt.TRANGTHAI = 1 \n"+
				"		 		  														GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK \n"+ 
				"	 			 													) \n"+
				"				 													 bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK \n"+ 
				"									WHERE 	1=1 and hd.NGAYXUATHD <='"+denngay+"' and hd.NGAYXUATHD >='"+tungay+"' \n";
				if(this.DdkdId.trim().length()>0)
				{
					   query+=
						"		  and hd.KHACHHANG_FK in \n"+
						"		  						( \n"+
						"			 						SELECT 	c.KHACHHANG_FK \n"+ 
						"									FROM 	DAIDIENKINHDOANH a inner join TUYENBANHANG b on b.DDKD_FK=a.PK_SEQ \n"+   			
						"											inner join KHACHHANG_TUYENBH c on c.TBH_FK=b.PK_SEQ \n"+
						"									WHERE a.PK_SEQ ='"+this.DdkdId+"'  \n"+
						"		  						) \n";
				   }
				   if(nppId.trim().length()>0)
						 query+= "        and kh.NPP_FK ='"+nppId+"'";
				query+=
				"		 							GROUP BY bt.KHACHHANG_FK,bt.HOADON_FK \n"+
				"								) as BUTRUCO on hd.PK_SEQ = BUTRUCO.HOADON_FK and hd.KHACHHANG_FK = BUTRUCO.KHACHHANG_FK \n"+
				"						LEFT JOIN \n"+
				"						 ( 	\n"+
				"								SELECT  bt.KHACHHANG_FK,bt.HOADON_FK, round(SUM(ISNULL(GHINO,0)),0) as BUTRUNO \n"+
				"		  						FROM 	HOADON hd inner join KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ \n"+
				"			   										inner join ( \n"+
				"				  													SELECT 	bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHINO,0),0)) as GHINO \n"+
				"			  	  													FROM 	BUTRUCONGNO bt inner join BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK \n"+
				"				  													WHERE   bt.TRANGTHAI = 1 \n"+
				"		 		  													GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK \n"+ 
				"	 			 												) \n"+
				"				 												 bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK \n"+
				"								WHERE 	1=1 and hd.NGAYXUATHD <='"+denngay+"' and hd.NGAYXUATHD >='"+tungay+"' \n";
				if(this.DdkdId.trim().length()>0)
				{
					   query+=
						"		  and hd.KHACHHANG_FK in \n"+
						"		  						( \n"+
						"			 						SELECT 	c.KHACHHANG_FK \n"+ 
						"									FROM 	DAIDIENKINHDOANH a inner join TUYENBANHANG b on b.DDKD_FK=a.PK_SEQ \n"+   			
						"											inner join KHACHHANG_TUYENBH c on c.TBH_FK=b.PK_SEQ \n"+
						"									WHERE a.PK_SEQ ='"+this.DdkdId+"' \n"+
						"		  						) \n";
				}
				   if(nppId.trim().length()>0)
						 query+= "        and kh.NPP_FK ='"+nppId+"'";
				query+=
				"		 						GROUP BY bt.KHACHHANG_FK,bt.HOADON_FK \n"+
				"							  ) as BUTRUNO on hd.PK_SEQ = BUTRUNO.HOADON_FK and hd.KHACHHANG_FK = BUTRUNO.KHACHHANG_FK \n"+									  
				"						left join KHACHHANG kh on hd.KHACHHANG_FK = kh.PK_SEQ \n"+ 
				"	WHERE 	hd.TRANGTHAI in (2,4) and round((hd.tongtienavat + ISNULL(BUTRUNO.BUTRUNO,0) - ISNULL(THUTIEN.DATHU,0) - ISNULL(XOANO.DAXOA,0) - ISNULL(BUTRUCO.BUTRUCO,0)),0)<>0 \n"; 
				
				
				 if(this.id.trim().length() > 0)
				   {
					 	query+= "     	and hd.PK_SEQ not in (select hoadon_fk from BUTRUCONGNO_HOADON where btcn_fk =  '"+ this.id +"') \n";   
				   }
				 
				
				 if(this.tungay.trim().length() > 0)
				   {
				    query += "     and hd.NGAYXUATHD >= '"+ this.tungay +"' \n";
				   }
				   if(this.denngay.trim().length() > 0)
				   {
				    query += "     and hd.NGAYXUATHD <= '"+ this.denngay +"' \n";
				   }
				   if(this.sohoadontu.trim().length() > 0)
				   {
				    query += "     and cast(hd.SOHOADON as numeric(18,0) ) >= '"+ Integer.parseInt(this.sohoadontu) +"' \n";
				   }
				   if(this.sohoadonden.trim().length() > 0)
				   {
				    query += "     and cast(hd.SOHOADON as numeric(18,0) ) <= '"+ Integer.parseInt(this.sohoadonden) +"' \n";
				   }
				
				   if(this.sotientu.trim().length() > 0)
				   {
					   double st = Double.parseDouble(this.sotienden.replaceAll(",", ""));
					   st=st*(-1);
					   query += "     and round((hd.tongtienavat + ISNULL(BUTRUNO.BUTRUNO,0) - ISNULL(THUTIEN.DATHU,0) - ISNULL(XOANO.DAXOA,0) - ISNULL(BUTRUCO.BUTRUCO,0)),0) >= "+ st +" \n";
				   }
				   if(this.sotienden.trim().length() > 0)
				   {
					   double sd = Double.parseDouble(this.sotienden.replaceAll(",", ""));
					   query += "     and round((hd.tongtienavat + ISNULL(BUTRUNO.BUTRUNO,0) - ISNULL(THUTIEN.DATHU,0) - ISNULL(XOANO.DAXOA,0) - ISNULL(BUTRUCO.BUTRUCO,0)),0) <= "+ sd +" \n";
				   }
				   
				   if(this.nppId.trim().length()>0)
				   {
					   query+= " and hd.NPP_FK='"+this.nppId+"' and hd.LOAIHOADON=0";
				   }
					
				   if(this.khId.trim().length()>0				   )
				   {
					   query +=" and hd.KHACHHANG_FK ='"+this.khId+"'";			   
				   }
				   
				   if(this.DdkdId.trim().length()>0)
				   {
					   query+=
						"		  and hd.KHACHHANG_FK in \n"+
						"		  						( \n"+
						"			 						SELECT 	c.KHACHHANG_FK \n"+ 
						"									FROM 	DAIDIENKINHDOANH a inner join TUYENBANHANG b on b.DDKD_FK=a.PK_SEQ \n"+   			
						"											inner join KHACHHANG_TUYENBH c on c.TBH_FK=b.PK_SEQ \n"+
						"									WHERE a.PK_SEQ ='"+this.DdkdId+"' \n"+
						"		  						) \n";
				   }
				   query += "ORDER BY NGAYXUATHD DESC "; 
			 }
			
			/*********************CHECK THEO KHACH HANG*********************************************/
			 if( ckKh.trim().length() > 0 ) //TICK CHỌN THEO KHÁCH HÀNG
			 {
				 query = "SELECT 	kh.PK_SEQ as khId, lkh.ten LoaiKH, kh.DIACHI, kh.maFAST, (kh.maFAST + '---' + kh.TEN) as tenKH,   " +
						 "		isnull(dndk.tongtienAVAT, 0) + isnull(psntk.tongtienAVAT, 0) - isnull(dcdk.SOTIENTT, 0) - isnull(psctk.sotienTT, 0)  as duno,  0 duco, 0 as GHINO, 0 as GHICO, 0 as CHON  " +
						 "FROM KHACHHANG kh left join LOAIKHACHHANG lkh on kh.XuatKhau = lkh.pk_seq LEFT JOIN   " +
						 "(   " +
						 "	SELECT psn_dk.PK_SEQ KHACHHANG_FK, SUM(ISNULL(psn_dk.tongtienAVAT,0)) as tongtienAVAT   " +
						 "	FROM   " +
						 "	(	  " +
						 "		SELECT psn.PK_SEQ, sum(tongtienAVAT) as tongtienAVAT   " +
						 "		FROM   " +
						 "		(   " +
						 "			SELECT hd.KHACHHANG_FK PK_SEQ, SUM(tongtienavat) tongtienAVAT   " +
						 "			FROM HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK = kh.PK_SEQ   " +
						 "			WHERE 1=1 and  hd.LOAIHOADON = 0 and hd.TRANGTHAI not in ( 1, 3, 5 ) and hd.NGAYXUATHD < '" +this.tungay +"' and kh.NPP_FK = '" + this.nppId + "'   " +
						 "            GROUP BY hd.KHACHHANG_FK   " +
						 "		UNION ALL  " +
						 "			SELECT  btcn_hd.KHACHHANG_FK PK_SEQ, SUM(btcn_hd.GHINO) as tongtienAVAT   " +
						 "			FROM 	BUTRUCONGNO btcn inner join BUTRUCONGNO_HOADON btcn_hd on btcn.PK_SEQ = btcn_hd.BTCN_FK    " +
						 "			WHERE 	btcn.LoaiBuTru = 1 and btcn.TRANGTHAI = 1 and btcn.NGAYCHUNGTU < '" +this.tungay +"' and btcn.NPP_FK = '" + this.nppId + "'                                 " +
						 "			GROUP BY btcn_hd.KHACHHANG_FK   " +
						 "		UNION ALL	  " +
						 "			SELECT 	hd.KHACHHANG_FK PK_SEQ, round(bt.tongtienavat,0) tongtienavat   " +
						 "			FROM    HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ   " +
						 "					inner join (   " +
						 "									SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHINO,0),0)) as tongtienavat   " +
						 "									FROM   BUTRUCONGNO bt inner join BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK   " +
						 "									WHERE bt.LoaiBuTru = 1 and bt.TRANGTHAI = 1   " +
						 "									GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK   " +
						 "								)   " +
						 "								bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK   " +
						 "					 LEFT JOIN NHAPHANPHOI npp on hd.NPP_FK = npp.PK_SEQ   " +
						 "			WHERE  1=1 and hd.TRANGTHAI not in ( 1, 3, 5 ) and hd.LOAIHOADON = 0 and hd.NGAYXUATHD <'" +this.tungay +"' and kh.NPP_FK ='" + this.nppId + "'   " +
						 "		UNION ALL	  " +
						 "			SELECT 	hd.KHACHHANG_FK PK_SEQ,  round(bt.tongtienavat,0) tongtienavat   " +
						 "			FROM 	ERP_HOADONNPP hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ   " +
						 "					INNER JOIN (   " +
						 "									SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHINO,0),0)) as tongtienavat   " +
						 "									FROM   BUTRUCONGNO bt INNER JOIN BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK   " +
						 "									WHERE bt.LoaiBuTru = 1 and bt.TRANGTHAI = 1   " +
						 "									GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK   " +
						 "								)   " +
						 "								bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK   " +
						 "					 LEFT JOIN NHAPHANPHOI npp on hd.NPP_FK = npp.PK_SEQ   " +
						 "			WHERE 	1=1 and hd.TRANGTHAI not in ( 1, 3, 5 ) and hd.NGAYXUATHD <'" +this.tungay +"' and kh.NPP_FK ='" + this.nppId + "'   " +
						 "		UNION ALL   " +
						 "			SELECT hd.khachhang_fk, SUM(round( (hdETC.AVAT - hdETC.AVAT_CK),0 )) as tongtienavat    " +
						 "			FROM   " +
						 "			(   " +
						 "				SELECT  ETC.PK_SEQ ,ETC.NGAYXUATHD, ETC.SOHOADON, ETC.KHACHHANG_FK, ETC.ddkd_fk, ETC.PK_SEQ as HOADONNPP_FK, npp_fk,   " +
						 "						sum(soluong) as soluong, ( sum( soluong * dongia ) / sum(soluong) ) as dongia,       " +
						 "						sum(round(( soluong * dongia ),0))  as BVAT,( sum(round((round(( soluong * dongia),0)*thuexuat/100 ),0 ))) as VAT,    " +
						 "						sum( soluong * dongia*(1+thuexuat/100 ) ) as AVAT,   " +
						 "						round(  sum( ( isnull(chietkhau,0)*(1+thuexuat/100))),0 ) as AVAT_CK,             " +
						 "						sum(isnull(thuexuat,0)) as BVAT_CK       " +
						 "				FROM (     " +
						 "						SELECT  c.HOADON_FK as PK_SEQ,a.NGAYXUATHD,a.SOHOADON,a.KHACHHANG_fk,c.HOADON_FK as HOADONNPP_FK, a.NPP_FK,c.chietkhau, c.vat as thuexuat,   " +
						 "							(     " +
						 "								SELECT top(1) bb.DDKD_FK     " +
						 "								FROM   ERP_HOADONNPP_DDH aa inner join ERP_DONDATHANGNPP bb on bb.PK_SEQ=aa.DDH_FK     " +
						 "								WHERE aa.HOADONNPP_FK=c.HOADON_FK    " +
						 "							) as ddkd_fk , 													case when c.donvitinh = e.donvi then soluong else c.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as soluong,    " +
						 "								case when c.donvitinh = e.donvi then c.dongia else c.dongia * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as dongia       " +
						 "						FROM  ERP_HOADONNPP a     " +
						 "							  INNER JOIN ERP_HOADONNPP_SP c on a.pk_seq = c.hoadon_fk    " +
						 "							  INNER JOIN SANPHAM d on c.sanpham_fk = d.pk_seq    " +
						 "							  INNER JOIN DONVIDOLUONG e on d.DVDL_FK = e.pk_seq    " +
						 "						WHERE 1=1 and c.SOLUONG > 0 and a.trangthai not in ( 1, 3, 5 ) and a.NgayXuatHD <'" +this.tungay +"'   " +
						 "					)ETC									  " +
						 "				GROUP BY ETC.PK_SEQ,ETC.NGAYXUATHD, ETC.SOHOADON,ETC.KHACHHANG_FK, ETC.ddkd_fk, ETC.PK_SEQ, npp_fk								  " +
						 "			)   " +
						 "			hdETC INNER JOIN ERP_HOADONNPP hd on hd.PK_SEQ = hdETC.HOADONNPP_FK   " +
						 "					  LEFT JOIN DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ = hdETC.DDKD_FK   " +
						 "			WHERE 1=1 and hd.NPP_FK ='" + this.nppId + "'   " +
						 "            GROUP BY hd.khachhang_fk   " +
						 "		)   " +
						 "		psn   " +
						 "		GROUP BY psn.PK_SEQ   " +
						 "	UNION ALL   " +
						 "		SELECT 	dnkh.KHACHHANG_FK PK_SEQ, sum(round(isnull(dnkh.SONO,0),0)) as tongtienAVAT   " +
						 "		FROM   	DUNO_KHACHHANG dnkh   " +
						 "				LEFT JOIN NHAPHANPHOI npp on dnkh.NPP_FK = npp.PK_SEQ   " +
						 "				LEFT JOIN KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK   " +
						 "				LEFT JOIN VUNG v on v.PK_SEQ = kv.VUNG_FK   " +
						 "	    WHERE 	1=1 and MONTH(dnkh.NgayDuNo) <= ( month('" + this.tungay + "') - 1 ) and dnkh.SONO >= 0  and  dnkh.NPP_FK='" + this.nppId + "'			GROUP BY dnkh.KHACHHANG_FK   " +
						 "	)   " +
						 "	psn_dk   " +
						 "	GROUP BY psn_dk.PK_SEQ   " +
						 ")   " +
						 "dndk on kh.PK_SEQ = dndk.KHACHHANG_FK LEFT JOIN   " +
						 "(   " +
						 "	SELECT 	psc.KHACHHANG_FK, round(SUM (isnull(psc.SOTIENTT,0)),0) as SOTIENTT   " +
						 "	FROM   " +
						 "	(   " +
						 "		SELECT 	hd.KHACHHANG_FK, SUM(round(isnull(tt.sotienTT,0),0)) as SOTIENTT   " +
						 "		FROM 	HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ   " +
						 "				INNER JOIN (   " +
						 "  								SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(ISNULL(tthd.sotienTT,0),0)) as sotienTT   " +
						 "  								FROM ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK   " +
						 "  								WHERE tt.TRANGTHAI = 1 and tt.NGAYCHUNGTU < '" +this.tungay +"'   " +
						 "  								GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK   " +
						 " 							) 	  " +
						 " 							tt on hd.KHACHHANG_FK = tt.KHACHHANG_FK and hd.PK_SEQ = tt.HOADONNPP_FK   " +
						 "		WHERE 	1=1 and kh.NPP_FK = '" + this.nppId + "'   " +
						 "		GROUP BY hd.KHACHHANG_FK   " +
						 "	UNION ALL   " +
						 "		SELECT 	dnkh.KHACHHANG_FK PK_SEQ, sum(round(isnull((-1)*dnkh.SONO,0),0)) as SOTIENTT   " +
						 "		FROM   	DUNO_KHACHHANG dnkh   " +
						 "				LEFT JOIN NHAPHANPHOI npp on dnkh.NPP_FK = npp.PK_SEQ   " +
						 "		WHERE 	1=1 and MONTH(dnkh.NgayDuNo) <= ( month('" + this.tungay + "') - 1 )	and dnkh.SONO <0  and dnkh.NPP_FK = '" + this.nppId + "' 		  " +
						 "		GROUP BY dnkh.KHACHHANG_FK   " +
						 "	UNION ALL   " +
						 "		SELECT  btcn_hd.KHACHHANG_FK, SUM(btcn_hd.GHICO) as tongtienTT   " +
						 "		FROM 	BUTRUCONGNO btcn inner join BUTRUCONGNO_HOADON btcn_hd on btcn.PK_SEQ = btcn_hd.BTCN_FK   " +
						 "		WHERE 	btcn.LoaiBuTru = 1 and btcn.TRANGTHAI = 1 and btcn.NGAYCHUNGTU < '" +this.tungay +"'   " +
						 "		  and btcn.NPP_FK='" + this.nppId + "' GROUP BY btcn_hd.KHACHHANG_FK   " +
						 "	UNION ALL  " +
						 "		SELECT dnkh.KHACHHANG_FK, sum(round(isnull(tt.sotienTT,0),0)) as SOTIENTT   " +
						 "		FROM   DUNO_KHACHHANG dnkh INNER JOIN KHACHHANG kh on dnkh.KHACHHANG_FK=kh.PK_SEQ   " +
						 "	 				INNER JOIN   " +
						 "	 				(   " +
						 "						SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(ISNULL(tthd.sotienTT,0),0)) as sotienTT   " +
						 "						FROM   ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK   " +
						 "						WHERE  tt.TRANGTHAI=1 and tt.NGAYCHUNGTU < '" +this.tungay +"' and tt.NPP_FK = '" + this.nppId + "'						  " +
						 "						GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK   " +
						 "		 			)  " +
						 "		 			tt on dnkh.KHACHHANG_FK = tt.KHACHHANG_FK and dnkh.PK_SEQ = tt.HOADONNPP_FK   " +
						 "		 WHERE 1=1 and kh.NPP_FK = '" + this.nppId + "'   " +
						 "		 GROUP BY dnkh.KHACHHANG_FK   " +
						 "	UNION ALL	  " +
						 "		SELECT 	tttl.KHACHHANG_FK, SUM(round(isnull(tttl.TienSauThue,0),0)) as SOTIENTT 		  " +
						 "		FROM   	Erp_HangTraLaiNpp  tttl				  " +
						 "		WHERE 	1=1 and tttl.trangthai=1 and tttl.NGAYTRA < '" +this.tungay +"' and tttl.NPP_FK = '" + this.nppId + "' 	  " +
						 "		GROUP BY tttl.KHACHHANG_FK   " +
						 "	UNION ALL	  " +
						 "		SELECT hd.KHACHHANG_FK, Sum(round(isnull(tt.sotienTT,0),0)) as tongtienTT   " +
						 "		FROM   ERP_HOADONNPP hd inner join KHACHHANG kh on hd.KHACHHANG_FK = kh.PK_SEQ   " +
						 "			   INNER JOIN (   " +
						 " 								SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(isnull(tthd.SOTIENTT,0),0)) as sotienTT   " +
						 " 								FROM ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK   " +
						 " 								WHERE tt.TRANGTHAI=1 and tt.NGAYCHUNGTU < '" +this.tungay +"'   " +
						 " 								GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK   " +
						 "  							)   " +
						 "  							tt on hd.KHACHHANG_FK=tt.KHACHHANG_FK and hd.PK_SEQ=tt.HOADONNPP_FK   " +
						 "		WHERE 1=1 and kh.NPP_FK = '" + this.nppId + "'   " +
						 "		GROUP BY  hd.KHACHHANG_FK   " +
						 "	UNION ALL   " +
						 "		SELECT xnkh_hd.KHACHHANG_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as SOTIENXOA   " +
						 "		FROM   XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK   " +
						 "		   			INNER JOIN HOADON hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK)   " +
						 "		WHERE  1=1 and xnkh.TRANGTHAI =1 and hd.NGAYXUATHD < '" +this.tungay +"' and xnkh.NPP_FK = '" + this.nppId + "'		  " +
						 "		GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK   " +
						 "	UNION ALL   " +
						 "		SELECT 	xnkh_hd.KHACHHANG_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as tongtienTT   " +
						 "		FROM 	XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK   " +
						 "				INNER JOIN ERP_HOADONNPP hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK)   " +
						 "		WHERE 	1=1 and xnkh.TRANGTHAI =1 and hd.NGAYXUATHD < '" +this.tungay +"' and xnkh.NPP_FK = '" + this.nppId + "'		  " +
						 "		GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK   " +
						 "	UNION ALL   " +
						 " 		SELECT  hd.KHACHHANG_FK, Sum(round(ISNULL(GHICO,0),0)) as tongtienTT   " +
						 "  		FROM 	HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ   " +
						 "   				INNER JOIN (   " +
						 "	  						SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHICO,0),0)) as GHICO   " +
						 "	  						FROM   BUTRUCONGNO bt INNER JOIN BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK   " +
						 "	  						WHERE bt.LoaiBuTru = 1 and bt.TRANGTHAI = 1   " +
						 "	  						GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK   " +
						 "	 					   )   " +
						 "	 						bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK   " +
						 " 		WHERE 	1=1 and hd.NGAYXUATHD < '" +this.tungay +"' and kh.NPP_FK = '" + this.nppId + "'   " +
						 " 		GROUP BY hd.KHACHHANG_FK   " +
						 "  " +
						 "	UNION ALL   " +
						 "		SELECT  hd.KHACHHANG_FK, SUM(round(ISNULL(GHICO,0),0)) as tongtienTT   " +
						 "  		FROM 	ERP_HOADONNPP hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ   " +
						 "   				INNER JOIN (   " +
						 "	  							SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHICO,0),0)) as GHICO   " +
						 "	  							FROM   BUTRUCONGNO bt inner join BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK   " +
						 "	  							WHERE  bt.LoaiBuTru = 1 and bt.TRANGTHAI = 1   " +
						 "	  							GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK   " +
						 "	 						)   " +
						 "					 		bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK   " +
						 " 		WHERE 	1=1 and hd.NGAYXUATHD < '" +this.tungay +"' and kh.NPP_FK = '" + this.nppId + "'   " +
						 " 		GROUP BY hd.KHACHHANG_FK   " +
						 "  " +
						 "	UNION ALL   " +
						 "		SELECT 	ttna.KHACHHANG_FK, Sum(round(ISNULL(ttna.SOTIENTT,0),0)) as tongtienTT   " +
						 "		FROM 	ERP_THUTIENNPP_HOADONTHEM ttna INNER JOIN ERP_THUTIENNPP tt on ttna.THUTIEN_FK = tt.PK_SEQ   " +
						 "		WHERE 1 =1 and tt.NGAYCHUNGTU < '" +this.tungay +"' and ttna.NPP_FK = '" + this.nppId + "'   	  " +
						 "		GROUP BY ttna.KHACHHANG_FK   " +
						 "	UNION ALL   " +
						 "		SELECT  hd.KHACHHANG_FK, SUM(round(ISNULL(SOTIENCANTRU,0),0)) as tongtienTT   " +
						 "		FROM 	HOADON hd INNER JOIN KHACHHANG k on hd.KHACHHANG_FK=k.PK_SEQ   " +
						 "				INNER JOIN (   " +
						 "								SELECT cthd.KHACHHANG_FK, cthd.HOADON_FK, SUM(round(ISNULL(cthd.SOTIENCANTRU,0),0)) as SOTIENCANTRU   " +
						 "								FROM   CANTRUCONGNO ct INNER JOIN CANTRUCONGNO_HOADON cthd on ct.PK_SEQ = cthd.CANTRUCONGNO_FK   " +
						 "								WHERE  ct.TRANGTHAI = 1   " +
						 "								GROUP BY cthd.KHACHHANG_FK, cthd.HOADON_FK   " +
						 "							)   " +
						 "							ct on hd.KHACHHANG_FK = ct.KHACHHANG_FK and hd.PK_SEQ = ct.HOADON_FK   " +
						 "		WHERE 	1=1 and hd.NGAYXUATHD <'" +this.tungay +"'  and hd.NPP_FK='" + this.nppId + "'	  " +
						 "		GROUP BY hd.KHACHHANG_FK   " +
						 "	)   " +
						 "	psc group by psc.KHACHHANG_FK   " +
						 ")   " +
						 "dcdk on kh.PK_SEQ = dcdk.KHACHHANG_FK left join  " +
						 "(   " +
						 "	SELECT psn.PK_SEQ as khachhang_fk, isnull( sum(tongtienAVAT), 0 ) as tongtienAVAT   " +
						 "	FROM   " +
						 "	(   " +
						 "		SELECT hd.KHACHHANG_FK PK_SEQ, SUM(tongtienavat) tongtienAVAT   " +
						 "		FROM HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK = kh.PK_SEQ   " +
						 "		WHERE 1=1 and  hd.LOAIHOADON = 0 and hd.TRANGTHAI not in ( 1, 3, 5 ) and hd.NGAYXUATHD >= '" +this.tungay +"' and hd.NGAYXUATHD <= '" + this.denngay + "' and kh.NPP_FK ='" + this.nppId + "'   " +
						 "        GROUP BY hd.KHACHHANG_FK   " +
						 "	UNION ALL  " +
						 "		SELECT  btcn_hd.KHACHHANG_FK PK_SEQ, SUM(btcn_hd.GHINO) as tongtienAVAT   " +
						 "		FROM 	BUTRUCONGNO btcn inner join BUTRUCONGNO_HOADON btcn_hd on btcn.PK_SEQ = btcn_hd.BTCN_FK    " +
						 "		WHERE 	btcn.LoaiBuTru = 1 and btcn.TRANGTHAI = 1 and btcn.NGAYCHUNGTU >= '" +this.tungay +"' and btcn.NGAYCHUNGTU <= '" + this.denngay + "' and btcn.NPP_FK='" + this.nppId + "'                                 " +
						 "		GROUP BY btcn_hd.KHACHHANG_FK   " +
						 "	UNION ALL	  " +
						 "		SELECT 	hd.KHACHHANG_FK PK_SEQ, round(bt.tongtienavat,0) tongtienavat   " +
						 "		FROM    HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ   " +
						 "				inner join (   " +
						 "								SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHINO,0),0)) as tongtienavat   " +
						 "								FROM   BUTRUCONGNO bt inner join BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK   " +
						 "								WHERE bt.LoaiBuTru = 1 and bt.TRANGTHAI = 1   " +
						 "								GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK   " +
						 "							)   " +
						 "							bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK   " +
						 "				 LEFT JOIN NHAPHANPHOI npp on hd.NPP_FK = npp.PK_SEQ   " +
						 "		WHERE  1=1 and hd.TRANGTHAI not in ( 1, 3, 5 ) and hd.LOAIHOADON = 0 and hd.NGAYXUATHD >= '" +this.tungay +"' and hd.NGAYXUATHD >= '" + this.denngay + "' and kh.NPP_FK ='" + this.nppId + "'   " +
						 "	UNION ALL	  " +
						 "		SELECT 	hd.KHACHHANG_FK PK_SEQ,  round(bt.tongtienavat,0) tongtienavat   " +
						 "		FROM 	ERP_HOADONNPP hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ   " +
						 "				INNER JOIN (   " +
						 "								SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHINO,0),0)) as tongtienavat   " +
						 "								FROM   BUTRUCONGNO bt INNER JOIN BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK   " +
						 "								WHERE bt.LoaiBuTru = 1 and bt.TRANGTHAI = 1   " +
						 "								GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK   " +
						 "							)   " +
						 "							bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK   " +
						 "				 LEFT JOIN NHAPHANPHOI npp on hd.NPP_FK = npp.PK_SEQ   " +
						 "		WHERE 	1=1 and hd.TRANGTHAI not in ( 1, 3, 5 ) and hd.NGAYXUATHD >= '" +this.tungay +"' and hd.NGAYXUATHD <= '" + this.denngay + "' and kh.NPP_FK ='" + this.nppId + "'   " +
						 "	UNION ALL   " +
						 "		SELECT hd.khachhang_fk, SUM(round( (hdETC.AVAT - hdETC.AVAT_CK),0 )) as tongtienavat    " +
						 "		FROM   " +
						 "		(   " +
						 "			SELECT  ETC.PK_SEQ ,ETC.NGAYXUATHD, ETC.SOHOADON, ETC.KHACHHANG_FK, ETC.ddkd_fk, ETC.PK_SEQ as HOADONNPP_FK, npp_fk,   " +
						 "					sum(soluong) as soluong, ( sum( soluong * dongia ) / sum(soluong) ) as dongia,       " +
						 "					sum(round(( soluong * dongia ),0))  as BVAT,( sum(round((round(( soluong * dongia),0)*thuexuat/100 ),0 ))) as VAT,    " +
						 "					sum( soluong * dongia*(1+thuexuat/100 ) ) as AVAT,   " +
						 "					round(  sum( ( isnull(chietkhau,0)*(1+thuexuat/100))),0 ) as AVAT_CK,             " +
						 "					sum(isnull(thuexuat,0)) as BVAT_CK       " +
						 "			FROM (     " +
						 "					SELECT  c.HOADON_FK as PK_SEQ,a.NGAYXUATHD,a.SOHOADON,a.KHACHHANG_fk,c.HOADON_FK as HOADONNPP_FK, a.NPP_FK,c.chietkhau, c.vat as thuexuat,   " +
						 "						(     " +
						 "							SELECT top(1) bb.DDKD_FK     " +
						 "							FROM   ERP_HOADONNPP_DDH aa inner join ERP_DONDATHANGNPP bb on bb.PK_SEQ=aa.DDH_FK     " +
						 "							WHERE aa.HOADONNPP_FK=c.HOADON_FK    " +
						 "						) as ddkd_fk , 													case when c.donvitinh = e.donvi then soluong else c.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as soluong,    " +
						 "							case when c.donvitinh = e.donvi then c.dongia else c.dongia * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as dongia       " +
						 "					FROM  ERP_HOADONNPP a     " +
						 "						  INNER JOIN ERP_HOADONNPP_SP c on a.pk_seq = c.hoadon_fk    " +
						 "						  INNER JOIN SANPHAM d on c.sanpham_fk = d.pk_seq    " +
						 "						  INNER JOIN DONVIDOLUONG e on d.DVDL_FK = e.pk_seq    " +
						 "					WHERE 1=1 and c.SOLUONG > 0 and a.trangthai not in ( 1, 3, 5 ) and a.NgayXuatHD >= '" +this.tungay +"' and a.NGAYXUATHD <= '" + this.denngay + "'   " +
						 "				)ETC									  " +
						 "			GROUP BY ETC.PK_SEQ,ETC.NGAYXUATHD, ETC.SOHOADON,ETC.KHACHHANG_FK, ETC.ddkd_fk, ETC.PK_SEQ, npp_fk								  " +
						 "		)   " +
						 "		hdETC INNER JOIN ERP_HOADONNPP hd on hd.PK_SEQ = hdETC.HOADONNPP_FK   " +
						 "				  LEFT JOIN DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ = hdETC.DDKD_FK   " +
						 "		WHERE 1=1 and hd.NPP_FK ='" + this.nppId + "'   " +
						 "        GROUP BY hd.khachhang_fk   " +
						 "	)   " +
						 "	psn   " +
						 "	GROUP BY psn.PK_SEQ   " +
						 ")   " +
						 "psntk on kh.PK_SEQ = psntk.KHACHHANG_FK LEFT JOIN   " +
						 "(   " +
						 "	SELECT 	psc.KHACHHANG_FK, round(SUM (isnull(psc.SOTIENTT,0)),0) as SOTIENTT   " +
						 "	FROM   " +
						 "	(   " +
						 "		SELECT 	hd.KHACHHANG_FK, SUM(round(isnull(tt.sotienTT,0),0)) as SOTIENTT   " +
						 "		FROM 	HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ   " +
						 "				INNER JOIN (   " +
						 "  								SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(ISNULL(tthd.sotienTT,0),0)) as sotienTT   " +
						 "  								FROM ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK   " +
						 "  								WHERE tt.TRANGTHAI = 1 and tt.NGAYCHUNGTU >= '" +this.tungay +"'  and tt.NGAYCHUNGTU <= '" + this.denngay + "'  " +
						 "  								GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK   " +
						 " 							) 	  " +
						 " 							tt on hd.KHACHHANG_FK = tt.KHACHHANG_FK and hd.PK_SEQ = tt.HOADONNPP_FK   " +
						 "		WHERE 	1=1 and kh.NPP_FK = '" + this.nppId + "'   " +
						 "		GROUP BY hd.KHACHHANG_FK   " +
						 "	UNION ALL   " +
						 "		SELECT 	dnkh.KHACHHANG_FK PK_SEQ, sum(round(isnull((-1)*dnkh.SONO,0),0)) as SOTIENTT   " +
						 "		FROM   	DUNO_KHACHHANG dnkh   " +
						 "				LEFT JOIN NHAPHANPHOI npp on dnkh.NPP_FK = npp.PK_SEQ   " +
						 "		WHERE 	1=1 and MONTH(dnkh.NgayDuNo)<=8	and dnkh.SONO <0  and dnkh.NPP_FK = '" + this.nppId + "' 		  " +
						 "		GROUP BY dnkh.KHACHHANG_FK   " +
						 "	UNION ALL   " +
						 "		SELECT  btcn_hd.KHACHHANG_FK, SUM(btcn_hd.GHICO) as tongtienTT   " +
						 "		FROM 	BUTRUCONGNO btcn inner join BUTRUCONGNO_HOADON btcn_hd on btcn.PK_SEQ = btcn_hd.BTCN_FK   " +
						 "		WHERE 	btcn.LoaiBuTru = 1 and btcn.TRANGTHAI = 1 and btcn.NGAYCHUNGTU >= '" +this.tungay +"' and btcn.NGAYCHUNGTU <= '" + this.denngay + "' and btcn.NPP_FK='" + this.nppId + "'   " +
						 "		GROUP BY btcn_hd.KHACHHANG_FK   " +
						 "	UNION ALL  " +
						 "		SELECT dnkh.KHACHHANG_FK, sum(round(isnull(tt.sotienTT,0),0)) as SOTIENTT   " +
						 "		FROM   DUNO_KHACHHANG dnkh INNER JOIN KHACHHANG kh on dnkh.KHACHHANG_FK=kh.PK_SEQ   " +
						 "	 				INNER JOIN   " +
						 "	 				(   " +
						 "						SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(ISNULL(tthd.sotienTT,0),0)) as sotienTT   " +
						 "						FROM   ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK   " +
						 "						WHERE  tt.TRANGTHAI=1 and tt.NGAYCHUNGTU >= '" +this.tungay +"' and tt.NGAYCHUNGTU <= '" + this.denngay + "' and tt.NPP_FK = '" + this.nppId + "'						  " +
						 "						GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK   " +
						 "		 			)  " +
						 "		 			tt on dnkh.KHACHHANG_FK = tt.KHACHHANG_FK and dnkh.PK_SEQ = tt.HOADONNPP_FK   " +
						 "		 WHERE 1=1 and kh.NPP_FK ='" + this.nppId + "'   " +
						 "		 GROUP BY dnkh.KHACHHANG_FK   " +
						 "	UNION ALL	  " +
						 "		SELECT 	tttl.KHACHHANG_FK, SUM(round(isnull(tttl.TienSauThue,0),0)) as SOTIENTT 		  " +
						 "		FROM   	Erp_HangTraLaiNpp  tttl				  " +
						 "		WHERE 	1=1 and tttl.trangthai=1 and tttl.NGAYTRA >= '" +this.tungay +"' and tttl.NGAYTRA <= '" + this.denngay + "' and tttl.NPP_FK = '" + this.nppId + "' 	  " +
						 "		GROUP BY tttl.KHACHHANG_FK   " +
						 "	UNION ALL	  " +
						 "		SELECT hd.KHACHHANG_FK, Sum(round(isnull(tt.sotienTT,0),0)) as tongtienTT   " +
						 "		FROM   ERP_HOADONNPP hd inner join KHACHHANG kh on hd.KHACHHANG_FK = kh.PK_SEQ   " +
						 "			   INNER JOIN (   " +
						 " 								SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(isnull(tthd.SOTIENTT,0),0)) as sotienTT   " +
						 " 								FROM ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK   " +
						 " 								WHERE tt.TRANGTHAI=1 and tt.NGAYCHUNGTU >= '" +this.tungay +"' and tt.NGAYCHUNGTU <= '" + this.denngay + "'  " +
						 " 								GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK   " +
						 "  							)   " +
						 "  							tt on hd.KHACHHANG_FK=tt.KHACHHANG_FK and hd.PK_SEQ=tt.HOADONNPP_FK   " +
						 "		WHERE 1=1 and kh.NPP_FK = '" + this.nppId + "'   " +
						 "		GROUP BY  hd.KHACHHANG_FK   " +
						 "	UNION ALL   " +
						 "		SELECT xnkh_hd.KHACHHANG_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as SOTIENXOA   " +
						 "		FROM   XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK   " +
						 "		   			INNER JOIN HOADON hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK)   " +
						 "		WHERE  1=1 and xnkh.TRANGTHAI =1 and hd.NGAYXUATHD >= '" +this.tungay +"' and hd.NGAYXUATHD <= '" + this.denngay + "' and xnkh.NPP_FK='" + this.nppId + "'		  " +
						 "		GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK   " +
						 "	UNION ALL   " +
						 "		SELECT 	xnkh_hd.KHACHHANG_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as tongtienTT   " +
						 "		FROM 	XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK   " +
						 "				INNER JOIN ERP_HOADONNPP hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK)   " +
						 "		WHERE 	1=1 and xnkh.TRANGTHAI =1 and hd.NGAYXUATHD >= '" +this.tungay +"' and hd.NGAYXUATHD <= '" + this.denngay + "' and xnkh.NPP_FK='" + this.nppId + "'		  " +
						 "		GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK   " +
						 "	UNION ALL   " +
						 " 		SELECT  hd.KHACHHANG_FK, Sum(round(ISNULL(GHICO,0),0)) as tongtienTT   " +
						 "  		FROM 	HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ   " +
						 "   				INNER JOIN (   " +
						 "	  						SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHICO,0),0)) as GHICO   " +
						 "	  						FROM   BUTRUCONGNO bt INNER JOIN BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK   " +
						 "	  						WHERE  bt.LoaiBuTru = 1 and bt.TRANGTHAI = 1   " +
						 "	  						GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK   " +
						 "	 					   )   " +
						 "	 						bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK   " +
						 " 		WHERE 	1=1 and hd.NGAYXUATHD >= '" +this.tungay +"' and hd.NGAYXUATHD <= '" + this.denngay + "' and kh.NPP_FK = '" + this.nppId + "'   " +
						 " 		GROUP BY hd.KHACHHANG_FK   " +
						 "  " +
						 "	UNION ALL   " +
						 "		SELECT  hd.KHACHHANG_FK, SUM(round(ISNULL(GHICO,0),0)) as tongtienTT   " +
						 "  		FROM 	ERP_HOADONNPP hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ   " +
						 "   				INNER JOIN (   " +
						 "	  							SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHICO,0),0)) as GHICO   " +
						 "	  							FROM   BUTRUCONGNO bt inner join BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK   " +
						 "	  							WHERE  bt.LoaiBuTru = 1 and bt.TRANGTHAI = 1   " +
						 "	  							GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK   " +
						 "	 						)   " +
						 "					 		bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK   " +
						 " 		WHERE 	1=1 and hd.NGAYXUATHD >= '" +this.tungay +"' and hd.NGAYXUATHD <= '" + this.denngay + "' and kh.NPP_FK = '" + this.nppId + "'   " +
						 " 		GROUP BY hd.KHACHHANG_FK   " +
						 "  " +
						 "	UNION ALL   " +
						 "		SELECT 	ttna.KHACHHANG_FK, Sum(round(ISNULL(ttna.SOTIENTT,0),0)) as tongtienTT   " +
						 "		FROM 	ERP_THUTIENNPP_HOADONTHEM ttna INNER JOIN ERP_THUTIENNPP tt on ttna.THUTIEN_FK = tt.PK_SEQ   " +
						 "		WHERE 1 =1 and tt.NGAYCHUNGTU >= '" +this.tungay +"' and tt.NGAYCHUNGTU <= '" + this.denngay + "' and ttna.NPP_FK = '" + this.nppId + "'   	  " +
						 "		GROUP BY ttna.KHACHHANG_FK   " +
						 "	UNION ALL   " +
						 "		SELECT  hd.KHACHHANG_FK, SUM(round(ISNULL(SOTIENCANTRU,0),0)) as tongtienTT   " +
						 "		FROM 	HOADON hd INNER JOIN KHACHHANG k on hd.KHACHHANG_FK=k.PK_SEQ   " +
						 "				INNER JOIN (   " +
						 "								SELECT cthd.KHACHHANG_FK, cthd.HOADON_FK, SUM(round(ISNULL(cthd.SOTIENCANTRU,0),0)) as SOTIENCANTRU   " +
						 "								FROM   CANTRUCONGNO ct INNER JOIN CANTRUCONGNO_HOADON cthd on ct.PK_SEQ = cthd.CANTRUCONGNO_FK   " +
						 "								WHERE  ct.TRANGTHAI = 1   " +
						 "								GROUP BY cthd.KHACHHANG_FK, cthd.HOADON_FK   " +
						 "							)   " +
						 "							ct on hd.KHACHHANG_FK = ct.KHACHHANG_FK and hd.PK_SEQ = ct.HOADON_FK   " +
						 "		WHERE 	1=1 and hd.NGAYXUATHD >= '" +this.tungay +"' and hd.NGAYXUATHD <= '" + this.denngay + "'  and hd.NPP_FK = '" + this.nppId + "'	  " +
						 "		GROUP BY hd.KHACHHANG_FK   " +
						 "	)   " +
						 "	psc group by psc.KHACHHANG_FK   " +
						 ")   " +
						 "psctk on kh.PK_SEQ = psctk.KHACHHANG_FK   "  +
						 "where ABS( isnull(dndk.tongtienAVAT, 0) + isnull(psntk.tongtienAVAT, 0) - isnull(dcdk.SOTIENTT, 0) - isnull(psctk.sotienTT, 0) ) >= '" + this.sotientu.replaceAll(",", "") + "' " +
						 		" and ABS( isnull(dndk.tongtienAVAT, 0) + isnull(psntk.tongtienAVAT, 0) - isnull(dcdk.SOTIENTT, 0) - isnull(psctk.sotienTT, 0) ) != '0' " +
						 		" and ABS ( isnull(dndk.tongtienAVAT, 0) + isnull(psntk.tongtienAVAT, 0) - isnull(dcdk.SOTIENTT, 0) - isnull(psctk.sotienTT, 0) ) <= '" + this.sotienden.replaceAll(",", "") + "' ";
				 
				 /*
				 query = "SELECT 	kh.PK_SEQ as khId, lkh.ten LoaiKH, kh.DIACHI, kh.maFAST, kh.TEN as tenKH, " +
		 		 "	    isnull(dndk.tongtienAVAT, 0) + isnull(psntk.tongtienAVAT, 0) - isnull(dcdk.SOTIENTT, 0) - isnull(psctk.sotienTT, 0)  as duno,  " +
		 		 "		0 duco, 0 as GHINO, 0 as GHICO, 0 as CHON  " +
				 "FROM KHACHHANG kh left join LOAIKHACHHANG lkh on kh.XuatKhau = lkh.pk_seq LEFT JOIN  " +
				 "(  " +
				 "	SELECT psn_dk.PK_SEQ KHACHHANG_FK, SUM(ISNULL(psn_dk.tongtienAVAT,0)) as tongtienAVAT  " +
				 "	FROM  " +
				 "	(	 " +
				 "		SELECT psn.PK_SEQ, sum(tongtienAVAT) as tongtienAVAT  " +
				 "		FROM  " +
				 "		(  " +
				 "			SELECT hd.KHACHHANG_FK PK_SEQ, SUM(tongtienavat) tongtienAVAT  " +
				 "			FROM HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK = kh.PK_SEQ  " +
				 "			WHERE 1=1 and  hd.LOAIHOADON = 0 and hd.TRANGTHAI in(2,4) and hd.NGAYXUATHD < '"+this.tungay+"'   " +
				 "            GROUP BY hd.KHACHHANG_FK  " +
				 "		UNION ALL " +
				 "			SELECT  btcn_hd.KHACHHANG_FK PK_SEQ, SUM(btcn_hd.GHINO) as tongtienAVAT  " +
				 "			FROM 	BUTRUCONGNO btcn inner join BUTRUCONGNO_HOADON btcn_hd on btcn.PK_SEQ = btcn_hd.BTCN_FK   " +
				 "			WHERE 	btcn.LoaiBuTru=1 and btcn.TRANGTHAI in ( 0, 1 ) and btcn.NGAYCHUNGTU < '"+this.tungay+"'                                " +
				 "			GROUP BY btcn_hd.KHACHHANG_FK  " +
				 "		UNION ALL	 " +
				 "			SELECT 	hd.KHACHHANG_FK PK_SEQ, round(bt.tongtienavat,0) tongtienavat  " +
				 "			FROM    HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ  " +
				 "					inner join (  " +
				 "									SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHINO,0),0)) as tongtienavat  " +
				 "									FROM   BUTRUCONGNO bt inner join BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK  " +
				 "									WHERE bt.TRANGTHAI in ( 0, 1 )  " +
				 "									GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK  " +
				 "								)  " +
				 "								bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK  " +
				 "					 LEFT JOIN NHAPHANPHOI npp on hd.NPP_FK = npp.PK_SEQ  " +
				 "			WHERE  1=1 and hd.TRANGTHAI in (2,4) and hd.LOAIHOADON = 0 and hd.NGAYXUATHD <'"+this.tungay+"'   " +
				 "		UNION ALL	 " +
				 "			SELECT 	hd.KHACHHANG_FK PK_SEQ,  round(bt.tongtienavat,0) tongtienavat  " +
				 "			FROM 	ERP_HOADONNPP hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ  " +
				 "					INNER JOIN (  " +
				 "									SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHINO,0),0)) as tongtienavat  " +
				 "									FROM   BUTRUCONGNO bt INNER JOIN BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK  " +
				 "									WHERE bt.TRANGTHAI = 1  " +
				 "									GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK  " +
				 "								)  " +
				 "								bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK  " +
				 "					 LEFT JOIN NHAPHANPHOI npp on hd.NPP_FK = npp.PK_SEQ  " +
				 "			WHERE 	1=1 and hd.TRANGTHAI in (2,4) and hd.NGAYXUATHD <'"+this.tungay+"'   " +
				 "		UNION ALL  " +
				 "			SELECT hd.khachhang_fk, SUM(round( (hdETC.AVAT - hdETC.AVAT_CK),0 )) as tongtienavat   " +
				 "			FROM  " +
				 "			(  " +
				 "				SELECT  ETC.PK_SEQ ,ETC.NGAYXUATHD, ETC.SOHOADON, ETC.KHACHHANG_FK, ETC.ddkd_fk, ETC.PK_SEQ as HOADONNPP_FK, npp_fk,  " +
				 "						sum(soluong) as soluong, ( sum( soluong * dongia ) / sum(soluong) ) as dongia,      " +
				 "						sum(round(( soluong * dongia ),0))  as BVAT,( sum(round((round(( soluong * dongia),0)*thuexuat/100 ),0 ))) as VAT,   " +
				 "						sum( soluong * dongia*(1+thuexuat/100 ) ) as AVAT,  " +
				 "						round(  sum( ( isnull(chietkhau,0)*(1+thuexuat/100))),0 ) as AVAT_CK,            " +
				 "						sum(isnull(thuexuat,0)) as BVAT_CK      " +
				 "				FROM (    " +
				 "						SELECT  c.HOADON_FK as PK_SEQ,a.NGAYXUATHD,a.SOHOADON,a.KHACHHANG_fk,c.HOADON_FK as HOADONNPP_FK, a.NPP_FK,c.chietkhau, c.vat as thuexuat,  " +
				 "							(    " +
				 "								SELECT top(1) bb.DDKD_FK    " +
				 "								FROM   ERP_HOADONNPP_DDH aa inner join ERP_DONDATHANGNPP bb on bb.PK_SEQ=aa.DDH_FK    " +
				 "								WHERE aa.HOADONNPP_FK=c.HOADON_FK   " +
				 "							) as ddkd_fk , 													case when c.donvitinh = e.donvi then soluong else c.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as soluong,   " +
				 "								case when c.donvitinh = e.donvi then c.dongia else c.dongia * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as dongia      " +
				 "						FROM  ERP_HOADONNPP a    " +
				 "							  INNER JOIN ERP_HOADONNPP_SP c on a.pk_seq = c.hoadon_fk   " +
				 "							  INNER JOIN SANPHAM d on c.sanpham_fk = d.pk_seq   " +
				 "							  INNER JOIN DONVIDOLUONG e on d.DVDL_FK = e.pk_seq   " +
				 "						WHERE 1=1 and c.SOLUONG > 0 and a.trangthai in ( 2,4 ) and a.NgayXuatHD <'"+this.tungay+"'  " +
				 "					)ETC									 " +
				 "				GROUP BY ETC.PK_SEQ,ETC.NGAYXUATHD, ETC.SOHOADON,ETC.KHACHHANG_FK, ETC.ddkd_fk, ETC.PK_SEQ, npp_fk								 " +
				 "			)  " +
				 "			hdETC INNER JOIN ERP_HOADONNPP hd on hd.PK_SEQ = hdETC.HOADONNPP_FK  " +
				 "					  LEFT JOIN DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ = hdETC.DDKD_FK  " +
				 "			WHERE 1=1   " +
				 "            GROUP BY hd.khachhang_fk  " +
				 "		)  " +
				 "		psn  " +
				 "		GROUP BY psn.PK_SEQ  " +
				 "	UNION ALL  " +
				 "		SELECT 	dnkh.KHACHHANG_FK PK_SEQ, sum(round(isnull(dnkh.SONO,0),0)) as tongtienAVAT  " +
				 "		FROM   	DUNO_KHACHHANG dnkh  " +
				 "				LEFT JOIN NHAPHANPHOI npp on dnkh.NPP_FK = npp.PK_SEQ  " +
				 "				LEFT JOIN KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK  " +
				 "				LEFT JOIN VUNG v on v.PK_SEQ = kv.VUNG_FK  " +
				 "	    WHERE 	1=1 and MONTH(dnkh.NgayDuNo)<= 8 and dnkh.SONO >= 0  		GROUP BY dnkh.KHACHHANG_FK  " +
				 "	)  " +
				 "	psn_dk  " +
				 "	GROUP BY psn_dk.PK_SEQ  " +
				 ")  " +
				 "dndk on kh.PK_SEQ = dndk.KHACHHANG_FK LEFT JOIN  " +
				 "(  " +
				 "	SELECT 	psc.KHACHHANG_FK, round(SUM (isnull(psc.SOTIENTT,0)),0) as SOTIENTT  " +
				 "	FROM  " +
				 "	(  " +
				 "		SELECT 	hd.KHACHHANG_FK, SUM(round(isnull(tt.sotienTT,0),0)) as SOTIENTT  " +
				 "		FROM 	HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ  " +
				 "				INNER JOIN (  " +
				 "  								SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(ISNULL(tthd.sotienTT,0),0)) as sotienTT  " +
				 "  								FROM ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK  " +
				 "  								WHERE tt.TRANGTHAI = 1 and tt.NGAYCHUNGTU < '"+this.tungay+"'  " +
				 "  								GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK  " +
				 " 							) 	 " +
				 " 							tt on hd.KHACHHANG_FK = tt.KHACHHANG_FK and hd.PK_SEQ = tt.HOADONNPP_FK  " +
				 "		WHERE 	1=1   " +
				 "		GROUP BY hd.KHACHHANG_FK  " +
				 "	UNION ALL  " +
				 "		SELECT 	dnkh.KHACHHANG_FK PK_SEQ, sum(round(isnull((-1)*dnkh.SONO,0),0)) as SOTIENTT  " +
				 "		FROM   	DUNO_KHACHHANG dnkh  " +
				 "				LEFT JOIN NHAPHANPHOI npp on dnkh.NPP_FK = npp.PK_SEQ  " +
				 "		WHERE 	1=1 and MONTH(dnkh.NgayDuNo)<=8	and dnkh.SONO <0  		 " +
				 "		GROUP BY dnkh.KHACHHANG_FK  " +
				 "	UNION ALL  " +
				 "		SELECT  btcn_hd.KHACHHANG_FK, SUM(btcn_hd.GHICO) as tongtienTT  " +
				 "		FROM 	BUTRUCONGNO btcn inner join BUTRUCONGNO_HOADON btcn_hd on btcn.PK_SEQ = btcn_hd.BTCN_FK  " +
				 "		WHERE 	btcn.LoaiBuTru=1 and btcn.TRANGTHAI in ( 0, 1 ) and btcn.NGAYCHUNGTU < '"+this.tungay+"'  " +
				 "		GROUP BY btcn_hd.KHACHHANG_FK  " +
				 "	UNION ALL " +
				 "		SELECT dnkh.KHACHHANG_FK, sum(round(isnull(tt.sotienTT,0),0)) as SOTIENTT  " +
				 "		FROM   DUNO_KHACHHANG dnkh INNER JOIN KHACHHANG kh on dnkh.KHACHHANG_FK=kh.PK_SEQ  " +
				 "	 				INNER JOIN  " +
				 "	 				(  " +
				 "						SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(ISNULL(tthd.sotienTT,0),0)) as sotienTT  " +
				 "						FROM   ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK  " +
				 "						WHERE  tt.TRANGTHAI=1 and tt.NGAYCHUNGTU < '"+this.tungay+"' 					 " +
				 "						GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK  " +
				 "		 			) " +
				 "		 			tt on dnkh.KHACHHANG_FK = tt.KHACHHANG_FK and dnkh.PK_SEQ = tt.HOADONNPP_FK  " +
				 "		 WHERE 1=1   " +
				 "		 GROUP BY dnkh.KHACHHANG_FK  " +
				 "	UNION ALL	 " +
				 "		SELECT 	tttl.KHACHHANG_FK, SUM(round(isnull(tttl.TienSauThue,0),0)) as SOTIENTT 		 " +
				 "		FROM   	Erp_HangTraLaiNpp  tttl				 " +
				 "		WHERE 	1=1 and tttl.trangthai=1 and tttl.NGAYTRA < '"+this.tungay+"' 	 " +
				 "		GROUP BY tttl.KHACHHANG_FK  " +
				 "	UNION ALL	 " +
				 "		SELECT hd.KHACHHANG_FK, Sum(round(isnull(tt.sotienTT,0),0)) as tongtienTT  " +
				 "		FROM   ERP_HOADONNPP hd inner join KHACHHANG kh on hd.KHACHHANG_FK = kh.PK_SEQ  " +
				 "			   INNER JOIN (  " +
				 " 								SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(isnull(tthd.SOTIENTT,0),0)) as sotienTT  " +
				 " 								FROM ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK  " +
				 " 								WHERE tt.TRANGTHAI=1 and tt.NGAYCHUNGTU < '"+this.tungay+"'  " +
				 " 								GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK  " +
				 "  							)  " +
				 "  							tt on hd.KHACHHANG_FK=tt.KHACHHANG_FK and hd.PK_SEQ=tt.HOADONNPP_FK  " +
				 "		WHERE 1=1   " +
				 "		GROUP BY  hd.KHACHHANG_FK  " +
				 "	UNION ALL  " +
				 "		SELECT xnkh_hd.KHACHHANG_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as SOTIENXOA  " +
				 "		FROM   XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK  " +
				 "		   			INNER JOIN HOADON hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK)  " +
				 "		WHERE  1=1 and xnkh.TRANGTHAI =1 and hd.NGAYXUATHD < '"+this.tungay+"' 	 " +
				 "		GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK  " +
				 "	UNION ALL  " +
				 "		SELECT 	xnkh_hd.KHACHHANG_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as tongtienTT  " +
				 "		FROM 	XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK  " +
				 "				INNER JOIN ERP_HOADONNPP hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK)  " +
				 "		WHERE 	1=1 and xnkh.TRANGTHAI =1 and hd.NGAYXUATHD < '"+this.tungay+"' 	 " +
				 "		GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK  " +
				 "	UNION ALL  " +
				 " 		SELECT  hd.KHACHHANG_FK, Sum(round(ISNULL(GHICO,0),0)) as tongtienTT  " +
				 "  		FROM 	HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ  " +
				 "   				INNER JOIN (  " +
				 "	  						SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHICO,0),0)) as GHICO  " +
				 "	  						FROM   BUTRUCONGNO bt INNER JOIN BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK  " +
				 "	  						WHERE  bt.TRANGTHAI = 1  " +
				 "	  						GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK  " +
				 "	 					   )  " +
				 "	 						bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK  " +
				 " 		WHERE 	1=1 and hd.NGAYXUATHD < '"+this.tungay+"'  " +
				 " 		GROUP BY hd.KHACHHANG_FK  " +
				 " " +
				 "	UNION ALL  " +
				 "		SELECT  hd.KHACHHANG_FK, SUM(round(ISNULL(GHICO,0),0)) as tongtienTT  " +
				 "  		FROM 	ERP_HOADONNPP hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ  " +
				 "   				INNER JOIN (  " +
				 "	  							SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHICO,0),0)) as GHICO  " +
				 "	  							FROM   BUTRUCONGNO bt inner join BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK  " +
				 "	  							WHERE  bt.TRANGTHAI in ( 0, 1 )  " +
				 "	  							GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK  " +
				 "	 						)  " +
				 "					 		bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK  " +
				 " 		WHERE 	1=1 and hd.NGAYXUATHD < '"+this.tungay+"'  " +
				 " 		GROUP BY hd.KHACHHANG_FK  " +
				 " " +
				 "	UNION ALL  " +
				 "		SELECT 	ttna.KHACHHANG_FK, Sum(round(ISNULL(ttna.SOTIENTT,0),0)) as tongtienTT  " +
				 "		FROM 	ERP_THUTIENNPP_HOADONTHEM ttna INNER JOIN ERP_THUTIENNPP tt on ttna.THUTIEN_FK = tt.PK_SEQ  " +
				 "		WHERE 1 =1 and tt.NGAYCHUNGTU < '"+this.tungay+"'    	 " +
				 "		GROUP BY ttna.KHACHHANG_FK  " +
				 "	UNION ALL  " +
				 "		SELECT  hd.KHACHHANG_FK, SUM(round(ISNULL(SOTIENCANTRU,0),0)) as tongtienTT  " +
				 "		FROM 	HOADON hd INNER JOIN KHACHHANG k on hd.KHACHHANG_FK=k.PK_SEQ  " +
				 "				INNER JOIN (  " +
				 "								SELECT cthd.KHACHHANG_FK, cthd.HOADON_FK, SUM(round(ISNULL(cthd.SOTIENCANTRU,0),0)) as SOTIENCANTRU  " +
				 "								FROM   CANTRUCONGNO ct INNER JOIN CANTRUCONGNO_HOADON cthd on ct.PK_SEQ = cthd.CANTRUCONGNO_FK  " +
				 "								WHERE  ct.TRANGTHAI = 1  " +
				 "								GROUP BY cthd.KHACHHANG_FK, cthd.HOADON_FK  " +
				 "							)  " +
				 "							ct on hd.KHACHHANG_FK = ct.KHACHHANG_FK and hd.PK_SEQ = ct.HOADON_FK  " +
				 "		WHERE 	1=1 and hd.NGAYXUATHD <'"+this.tungay+"'   " +
				 "		GROUP BY hd.KHACHHANG_FK  " +
				 "	)  " +
				 "	psc group by psc.KHACHHANG_FK  " +
				 ")  " +
				 "dcdk on kh.PK_SEQ = dcdk.KHACHHANG_FK left join " +
				 "(  " +
				 "	SELECT psn.PK_SEQ as khachhang_fk, isnull( sum(tongtienAVAT), 0 ) as tongtienAVAT  " +
				 "	FROM  " +
				 "	(  " +
				 "		SELECT hd.KHACHHANG_FK PK_SEQ, SUM(tongtienavat) tongtienAVAT  " +
				 "		FROM HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK = kh.PK_SEQ  " +
				 "		WHERE 1=1 and  hd.LOAIHOADON = 0 and hd.TRANGTHAI in(2,4) and hd.NGAYXUATHD >= '"+this.tungay+"' and hd.NGAYXUATHD <= '"+this.denngay+"'   " +
				 "        GROUP BY hd.KHACHHANG_FK  " +
				 "	UNION ALL " +
				 "		SELECT  btcn_hd.KHACHHANG_FK PK_SEQ, SUM(btcn_hd.GHINO) as tongtienAVAT  " +
				 "		FROM 	BUTRUCONGNO btcn inner join BUTRUCONGNO_HOADON btcn_hd on btcn.PK_SEQ = btcn_hd.BTCN_FK   " +
				 "		WHERE 	btcn.LoaiBuTru=1 and btcn.TRANGTHAI in ( 0, 1 ) and btcn.NGAYCHUNGTU >= '"+this.tungay+"'                               " +
				 "		GROUP BY btcn_hd.KHACHHANG_FK  " +
				 "	UNION ALL	 " +
				 "		SELECT 	hd.KHACHHANG_FK PK_SEQ, round(bt.tongtienavat,0) tongtienavat  " +
				 "		FROM    HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ  " +
				 "				inner join (  " +
				 "								SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHINO,0),0)) as tongtienavat  " +
				 "								FROM   BUTRUCONGNO bt inner join BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK  " +
				 "								WHERE bt.TRANGTHAI in ( 0, 1 )  " +
				 "								GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK  " +
				 "							)  " +
				 "							bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK  " +
				 "				 LEFT JOIN NHAPHANPHOI npp on hd.NPP_FK = npp.PK_SEQ  " +
				 "		WHERE  1=1 and hd.TRANGTHAI in (2,4) and hd.LOAIHOADON = 0 and hd.NGAYXUATHD >= '"+this.tungay+"' and hd.NGAYXUATHD >= '"+this.denngay+"'  " +
				 "	UNION ALL	 " +
				 "		SELECT 	hd.KHACHHANG_FK PK_SEQ,  round(bt.tongtienavat,0) tongtienavat  " +
				 "		FROM 	ERP_HOADONNPP hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ  " +
				 "				INNER JOIN (  " +
				 "								SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHINO,0),0)) as tongtienavat  " +
				 "								FROM   BUTRUCONGNO bt INNER JOIN BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK  " +
				 "								WHERE bt.TRANGTHAI in ( 0, 1 )  " +
				 "								GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK  " +
				 "							)  " +
				 "							bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK  " +
				 "				 LEFT JOIN NHAPHANPHOI npp on hd.NPP_FK = npp.PK_SEQ  " +
				 "		WHERE 	1=1 and hd.TRANGTHAI in (2,4) and hd.NGAYXUATHD >= '"+this.tungay+"' and hd.NGAYXUATHD <= '"+this.denngay+"'   " +
				 "	UNION ALL  " +
				 "		SELECT hd.khachhang_fk, SUM(round( (hdETC.AVAT - hdETC.AVAT_CK),0 )) as tongtienavat   " +
				 "		FROM  " +
				 "		(  " +
				 "			SELECT  ETC.PK_SEQ ,ETC.NGAYXUATHD, ETC.SOHOADON, ETC.KHACHHANG_FK, ETC.ddkd_fk, ETC.PK_SEQ as HOADONNPP_FK, npp_fk,  " +
				 "					sum(soluong) as soluong, ( sum( soluong * dongia ) / sum(soluong) ) as dongia,      " +
				 "					sum(round(( soluong * dongia ),0))  as BVAT,( sum(round((round(( soluong * dongia),0)*thuexuat/100 ),0 ))) as VAT,   " +
				 "					sum( soluong * dongia*(1+thuexuat/100 ) ) as AVAT,  " +
				 "					round(  sum( ( isnull(chietkhau,0)*(1+thuexuat/100))),0 ) as AVAT_CK,            " +
				 "					sum(isnull(thuexuat,0)) as BVAT_CK      " +
				 "			FROM (    " +
				 "					SELECT  c.HOADON_FK as PK_SEQ,a.NGAYXUATHD,a.SOHOADON,a.KHACHHANG_fk,c.HOADON_FK as HOADONNPP_FK, a.NPP_FK,c.chietkhau, c.vat as thuexuat,  " +
				 "						(    " +
				 "							SELECT top(1) bb.DDKD_FK    " +
				 "							FROM   ERP_HOADONNPP_DDH aa inner join ERP_DONDATHANGNPP bb on bb.PK_SEQ=aa.DDH_FK    " +
				 "							WHERE aa.HOADONNPP_FK=c.HOADON_FK   " +
				 "						) as ddkd_fk , 													case when c.donvitinh = e.donvi then soluong else c.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as soluong,   " +
				 "							case when c.donvitinh = e.donvi then c.dongia else c.dongia * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as dongia      " +
				 "					FROM  ERP_HOADONNPP a    " +
				 "						  INNER JOIN ERP_HOADONNPP_SP c on a.pk_seq = c.hoadon_fk   " +
				 "						  INNER JOIN SANPHAM d on c.sanpham_fk = d.pk_seq   " +
				 "						  INNER JOIN DONVIDOLUONG e on d.DVDL_FK = e.pk_seq   " +
				 "					WHERE 1=1 and c.SOLUONG > 0 and a.trangthai in ( 2,4 ) and a.NgayXuatHD >= '"+this.tungay+"' and a.NGAYXUATHD >= '"+this.tungay+"'  " +
				 "				)ETC									 " +
				 "			GROUP BY ETC.PK_SEQ,ETC.NGAYXUATHD, ETC.SOHOADON,ETC.KHACHHANG_FK, ETC.ddkd_fk, ETC.PK_SEQ, npp_fk								 " +
				 "		)  " +
				 "		hdETC INNER JOIN ERP_HOADONNPP hd on hd.PK_SEQ = hdETC.HOADONNPP_FK  " +
				 "				  LEFT JOIN DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ = hdETC.DDKD_FK  " +
				 "		WHERE 1=1   " +
				 "        GROUP BY hd.khachhang_fk  " +
				 "	)  " +
				 "	psn  " +
				 "	GROUP BY psn.PK_SEQ  " +
				 ")  " +
				 "psntk on kh.PK_SEQ = psntk.KHACHHANG_FK LEFT JOIN  " +
				 "(  " +
				 "	SELECT 	psc.KHACHHANG_FK, round(SUM (isnull(psc.SOTIENTT,0)),0) as SOTIENTT  " +
				 "	FROM  " +
				 "	(  " +
				 "		SELECT 	hd.KHACHHANG_FK, SUM(round(isnull(tt.sotienTT,0),0)) as SOTIENTT  " +
				 "		FROM 	HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ  " +
				 "				INNER JOIN (  " +
				 "  								SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(ISNULL(tthd.sotienTT,0),0)) as sotienTT  " +
				 "  								FROM ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK  " +
				 "  								WHERE tt.TRANGTHAI = 1 and tt.NGAYCHUNGTU >= '"+this.tungay+"'  and tt.NGAYCHUNGTU <= '"+this.denngay+"' " +
				 "  								GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK  " +
				 " 							) 	 " +
				 " 							tt on hd.KHACHHANG_FK = tt.KHACHHANG_FK and hd.PK_SEQ = tt.HOADONNPP_FK  " +
				 "		WHERE 	1=1   " +
				 "		GROUP BY hd.KHACHHANG_FK  " +
				 "	UNION ALL  " +
				 "		SELECT 	dnkh.KHACHHANG_FK PK_SEQ, sum(round(isnull((-1)*dnkh.SONO,0),0)) as SOTIENTT  " +
				 "		FROM   	DUNO_KHACHHANG dnkh  " +
				 "				LEFT JOIN NHAPHANPHOI npp on dnkh.NPP_FK = npp.PK_SEQ  " +
				 "		WHERE 	1=1 and MONTH(dnkh.NgayDuNo)<=8	and dnkh.SONO <0  		 " +
				 "		GROUP BY dnkh.KHACHHANG_FK  " +
				 "	UNION ALL  " +
				 "		SELECT  btcn_hd.KHACHHANG_FK, SUM(btcn_hd.GHICO) as tongtienTT  " +
				 "		FROM 	BUTRUCONGNO btcn inner join BUTRUCONGNO_HOADON btcn_hd on btcn.PK_SEQ = btcn_hd.BTCN_FK  " +
				 "		WHERE 	btcn.LoaiBuTru=1 and btcn.TRANGTHAI in ( 0, 1 ) and btcn.NGAYCHUNGTU >= '"+this.tungay+"'    " +
				 "		GROUP BY btcn_hd.KHACHHANG_FK  " +
				 "	UNION ALL " +
				 "		SELECT dnkh.KHACHHANG_FK, sum(round(isnull(tt.sotienTT,0),0)) as SOTIENTT  " +
				 "		FROM   DUNO_KHACHHANG dnkh INNER JOIN KHACHHANG kh on dnkh.KHACHHANG_FK=kh.PK_SEQ  " +
				 "	 				INNER JOIN  " +
				 "	 				(  " +
				 "						SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(ISNULL(tthd.sotienTT,0),0)) as sotienTT  " +
				 "						FROM   ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK  " +
				 "						WHERE  tt.TRANGTHAI=1 and tt.NGAYCHUNGTU >= '"+this.tungay+"' and tt.NGAYCHUNGTU <= '"+this.denngay+"' 				 " +
				 "						GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK  " +
				 "		 			) " +
				 "		 			tt on dnkh.KHACHHANG_FK = tt.KHACHHANG_FK and dnkh.PK_SEQ = tt.HOADONNPP_FK  " +
				 "		 WHERE 1=1   " +
				 "		 GROUP BY dnkh.KHACHHANG_FK  " +
				 "	UNION ALL	 " +
				 "		SELECT 	tttl.KHACHHANG_FK, SUM(round(isnull(tttl.TienSauThue,0),0)) as SOTIENTT 		 " +
				 "		FROM   	Erp_HangTraLaiNpp  tttl				 " +
				 "		WHERE 	1=1 and tttl.trangthai=1 and tttl.NGAYTRA >= '"+this.tungay+"' and tttl.NGAYTRA <= '"+this.denngay+"' 	 " +
				 "		GROUP BY tttl.KHACHHANG_FK  " +
				 "	UNION ALL	 " +
				 "		SELECT hd.KHACHHANG_FK, Sum(round(isnull(tt.sotienTT,0),0)) as tongtienTT  " +
				 "		FROM   ERP_HOADONNPP hd inner join KHACHHANG kh on hd.KHACHHANG_FK = kh.PK_SEQ  " +
				 "			   INNER JOIN (  " +
				 " 								SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(isnull(tthd.SOTIENTT,0),0)) as sotienTT  " +
				 " 								FROM ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK  " +
				 " 								WHERE tt.TRANGTHAI=1 and tt.NGAYCHUNGTU >= '"+this.tungay+"' and tt.NGAYCHUNGTU <= '"+this.denngay+"' " +
				 " 								GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK  " +
				 "  							)  " +
				 "  							tt on hd.KHACHHANG_FK=tt.KHACHHANG_FK and hd.PK_SEQ=tt.HOADONNPP_FK  " +
				 "		WHERE 1=1   " +
				 "		GROUP BY  hd.KHACHHANG_FK  " +
				 "	UNION ALL  " +
				 "		SELECT xnkh_hd.KHACHHANG_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as SOTIENXOA  " +
				 "		FROM   XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK  " +
				 "		   			INNER JOIN HOADON hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK)  " +
				 "		WHERE  1=1 and xnkh.TRANGTHAI =1 and hd.NGAYXUATHD >= '"+this.tungay+"' and hd.NGAYXUATHD <= '"+this.denngay+"' 		 " +
				 "		GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK  " +
				 "	UNION ALL  " +
				 "		SELECT 	xnkh_hd.KHACHHANG_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as tongtienTT  " +
				 "		FROM 	XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK  " +
				 "				INNER JOIN ERP_HOADONNPP hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK)  " +
				 "		WHERE 	1=1 and xnkh.TRANGTHAI =1 and hd.NGAYXUATHD >= '"+this.tungay+"' and hd.NGAYXUATHD <= '"+this.denngay+"' 	 " +
				 "		GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK  " +
				 "	UNION ALL  " +
				 " 		SELECT  hd.KHACHHANG_FK, Sum(round(ISNULL(GHICO,0),0)) as tongtienTT  " +
				 "  		FROM 	HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ  " +
				 "   				INNER JOIN (  " +
				 "	  						SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHICO,0),0)) as GHICO  " +
				 "	  						FROM   BUTRUCONGNO bt INNER JOIN BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK  " +
				 "	  						WHERE  bt.TRANGTHAI in ( 0, 1 )  " +
				 "	  						GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK  " +
				 "	 					   )  " +
				 "	 						bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK  " +
				 " 		WHERE 	1=1 and hd.NGAYXUATHD >= '"+this.tungay+"' and hd.NGAYXUATHD <= '"+this.denngay+"'  " +
				 " 		GROUP BY hd.KHACHHANG_FK  " +
				 " " +
				 "	UNION ALL  " +
				 "		SELECT  hd.KHACHHANG_FK, SUM(round(ISNULL(GHICO,0),0)) as tongtienTT  " +
				 "  		FROM 	ERP_HOADONNPP hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ  " +
				 "   				INNER JOIN (  " +
				 "	  							SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHICO,0),0)) as GHICO  " +
				 "	  							FROM   BUTRUCONGNO bt inner join BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK  " +
				 "	  							WHERE  bt.TRANGTHAI in ( 0, 1 )  " +
				 "	  							GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK  " +
				 "	 						)  " +
				 "					 		bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK  " +
				 " 		WHERE 	1=1 and hd.NGAYXUATHD >= '"+this.tungay+"' and hd.NGAYXUATHD <= '"+this.denngay+"'   " +
				 " 		GROUP BY hd.KHACHHANG_FK  " +
				 " " +
				 "	UNION ALL  " +
				 "		SELECT 	ttna.KHACHHANG_FK, Sum(round(ISNULL(ttna.SOTIENTT,0),0)) as tongtienTT  " +
				 "		FROM 	ERP_THUTIENNPP_HOADONTHEM ttna INNER JOIN ERP_THUTIENNPP tt on ttna.THUTIEN_FK = tt.PK_SEQ  " +
				 "		WHERE 1 =1 and tt.NGAYCHUNGTU >= '"+this.tungay+"' and tt.NGAYCHUNGTU <= '"+this.denngay+"'   	 " +
				 "		GROUP BY ttna.KHACHHANG_FK  " +
				 "	UNION ALL  " +
				 "		SELECT  hd.KHACHHANG_FK, SUM(round(ISNULL(SOTIENCANTRU,0),0)) as tongtienTT  " +
				 "		FROM 	HOADON hd INNER JOIN KHACHHANG k on hd.KHACHHANG_FK=k.PK_SEQ  " +
				 "				INNER JOIN (  " +
				 "								SELECT cthd.KHACHHANG_FK, cthd.HOADON_FK, SUM(round(ISNULL(cthd.SOTIENCANTRU,0),0)) as SOTIENCANTRU  " +
				 "								FROM   CANTRUCONGNO ct INNER JOIN CANTRUCONGNO_HOADON cthd on ct.PK_SEQ = cthd.CANTRUCONGNO_FK  " +
				 "								WHERE  ct.TRANGTHAI = 1  " +
				 "								GROUP BY cthd.KHACHHANG_FK, cthd.HOADON_FK  " +
				 "							)  " +
				 "							ct on hd.KHACHHANG_FK = ct.KHACHHANG_FK and hd.PK_SEQ = ct.HOADON_FK  " +
				 "		WHERE 	1=1 and hd.NGAYXUATHD >= '"+this.tungay+"' and hd.NGAYXUATHD <= '"+this.denngay+"'  	 " +
				 "		GROUP BY hd.KHACHHANG_FK  " +
				 "	)  " +
				 "	psc group by psc.KHACHHANG_FK  " +
				 ")  " +
				 "psctk on kh.PK_SEQ = psctk.KHACHHANG_FK " +
				 "where  isnull(dndk.tongtienAVAT, 0) + isnull(psntk.tongtienAVAT, 0) - isnull(dcdk.SOTIENTT, 0) - isnull(psctk.sotienTT, 0) >= '" + this.sotientu.replaceAll(",", "") + "' " +
				 	"and isnull(dndk.tongtienAVAT, 0) + isnull(psntk.tongtienAVAT, 0) - isnull(dcdk.SOTIENTT, 0) - isnull(psctk.sotienTT, 0) <= '" + this.sotienden.replaceAll(",", "") + "' ";
		 */
				 
				 if(this.DdkdId.trim().length() > 0 )
					 query += " AND kh.pk_seq in ( " +
					 		  "  SELECT 	c.KHACHHANG_FK  " +
					 		  " FROM 	DAIDIENKINHDOANH a inner join TUYENBANHANG b on b.DDKD_FK=a.PK_SEQ  " +
					 		  "  		inner join KHACHHANG_TUYENBH c on c.TBH_FK=b.PK_SEQ " +
					 		  " WHERE a.PK_SEQ = '" + this.DdkdId + "' ) ";
				 if(this.khId.trim().length() > 0)
					 query += " AND kh.pk_seq = '" + this.khId + "' ";
				 
				 if(this.nppId.trim().length() > 0)
					 query += " AND kh.npp_fk = '" + this.nppId + "' ";
				 
			  }
			
		  }

	   		System.out.println("CAU LAY HOA DON createRS(.........): _____________ "+query);
	   		NumberFormat formatter = new DecimalFormat("#,###,###"); 
	   		ResultSet rs = db.get(query);
	   		
	   		if(ckKh.trim().length()<=0){// tick chọn bù trừ hóa đơn
	   		try
	   		{
	   			String hdId = "" ;
				String hdKhId = "" ;
				String hdMakh = "" ;
				String hdNgayhd = "" ;
				String hdSohoadon = "" ;
				String hdDuno = "" ;
				String hdDuco = "";
				String hdGhino = "" ;
				String hdGhico = "";
				String hdChon ="";
				
				
	   			if(rs!= null)
	   			{ System.out.println("Vào đây!!!");
	   				while(rs.next())
	   				{	   			
	   					
	   					hdId += rs.getString("Id") + "__";
	   					hdKhId += rs.getString("khId") + "__";
	   					hdMakh+= rs.getString("tenKH") + "__";
	   					hdNgayhd += rs.getString("ngayxuathd")+ "__";
	   					hdSohoadon += rs.getString("sohoadon")+ "__";
	   					hdDuno += rs.getString("duno")+ "__";	   					
	   					hdDuco+=rs.getString("duco")+ "__";
	   					hdGhino+= rs.getString("ghino")+ "__";
	   					hdGhico+= rs.getString("ghico")+ "__";
	   					hdChon += rs.getString("chon")+"__";
	   					
	   					if(!rs.getString("chon").equals("0"))
	   					{
		   					this.tc_duno_tc = formatter.format((Double.parseDouble(this.tc_duno_tc.replaceAll(",", ""))+ rs.getDouble("duno")));		   				
		   					this.tc_duco_tc =formatter.format((Double.parseDouble(this.tc_duco_tc.replaceAll(",", ""))+rs.getDouble("duco")));
		   					System.out.println("duno_tc" + this.tc_duno_tc );
	   					}
	   					else
	   					{
	   						System.out.println("duno_tc");
	   						if(rs.getDouble("duno")>=0)
		   					{
		   						this.tc_duno_tc = formatter.format((Double.parseDouble(this.tc_duno_tc.replaceAll(",", ""))+ rs.getDouble("duno")));
		   					}
		   					else
		   					{
		   						this.tc_duco_tc =formatter.format((Double.parseDouble(this.tc_duco_tc.replaceAll(",", ""))+rs.getDouble("duno")*(-1)));
		   					}
	   					}
	   					this.tc_ghi_no=formatter.format((Double.parseDouble(this.tc_ghi_no.replaceAll(",", ""))+ rs.getDouble("ghino")));
	   					this.tc_ghi_co=formatter.format((Double.parseDouble(this.tc_ghi_co.replaceAll(",", ""))+ rs.getDouble("ghico")));
	   					
	   				}
	   				rs.close();
	   				
	   				
	   				if(hdId.trim().length() > 0)
	   				{
	   					hdId = hdId.substring(0, hdId.length() - 2);
	   					this.HdId = hdId.split("__");
	   					
	   					hdKhId = hdKhId.substring(0, hdKhId.length() - 2);
	   					this.HdKhid = hdKhId.split("__");
	   					
	   					hdMakh = hdMakh.substring(0, hdMakh.length() - 2);
	   					this.HdMakh = hdMakh.split("__");
	   					
	   					hdNgayhd = hdNgayhd.substring(0, hdNgayhd.length() - 2);
	   					this.HdNgayhd = hdNgayhd.split("__");
	   					
	   					hdSohoadon = hdSohoadon.substring(0, hdSohoadon.length() - 2);
	   					this.HdSohd= hdSohoadon.split("__");
	   					
	   					hdDuno = hdDuno.substring(0, hdDuno.length() - 2);
	   					this.HdDuno=hdDuno.split("__");
	   					
	   					hdDuco = hdDuco.substring(0, hdDuco.length() - 2);
	   					this.HdDuco=hdDuco.split("__");
	   					
	   					hdGhino = hdGhino.substring(0, hdGhino.length() - 2);
	   					this.HdGhino=hdGhino.split("__");
	   					
	   					hdGhico = hdGhico.substring(0, hdGhico.length() - 2);
	   					this.HdGhico=hdGhico.split("__");
	   						   					
	   					hdChon = hdChon.substring(0, hdChon.length() - 2);
	   					this.HdChon= hdChon.split("__");
	   				}
	   				
	   			}
	   			else{
	   				return;
	   			}
	   			
	   		}catch (Exception e) {
				e.printStackTrace();
			}
	   		}
	   		
	   		if(ckKh.trim().length()>0)
	   		{
	   		// tick chọn bù trừ theo khách háng
		   		try
		   		{
					String hdKhId = "" ;
					String hdMakh = "" ;
					String hdDuno = "" ;
					String hdDuco = "";
					String hdGhino = "" ;
					String hdGhico = "";
					String hdChon ="";
					
					
		   			if(rs!= null)
		   			{ 
		   				while(rs.next())
		   				{	   			
		   					
		   					hdKhId += rs.getString("khId") + "__";
		   					hdMakh+= rs.getString("tenKH") + "__";
		   					hdDuno += rs.getString("duno")+ "__";	   					
		   					hdDuco+=rs.getString("duco")+ "__";
		   					hdGhino+= rs.getString("ghino")+ "__";
		   					hdGhico+= rs.getString("ghico")+ "__";
		   					hdChon += rs.getString("chon")+"__";
		   					if(!rs.getString("chon").equals("0"))
		   					{
		   						
			   					this.tc_duno_tc = formatter.format((Double.parseDouble(this.tc_duno_tc.replaceAll(",", ""))+ rs.getDouble("duno")));		   				
			   					this.tc_duco_tc =formatter.format((Double.parseDouble(this.tc_duco_tc.replaceAll(",", ""))+rs.getDouble("duco")));
			   					System.out.println("duno_tc" + this.tc_duno_tc );
		   					}
		   					else
		   					{
		   						System.out.println("duno_tc");
		   						if(rs.getDouble("duno")>=0)
			   					{
			   						this.tc_duno_tc = formatter.format((Double.parseDouble(this.tc_duno_tc.replaceAll(",", ""))+ rs.getDouble("duno")));
			   					}
			   					else
			   					{
			   						this.tc_duco_tc =formatter.format((Double.parseDouble(this.tc_duco_tc.replaceAll(",", ""))+rs.getDouble("duno")*(-1)));
			   					}
		   					}
		   					this.tc_ghi_no=formatter.format((Double.parseDouble(this.tc_ghi_no.replaceAll(",", ""))+ rs.getDouble("ghino")));
		   					this.tc_ghi_co=formatter.format((Double.parseDouble(this.tc_ghi_co.replaceAll(",", ""))+ rs.getDouble("ghico")));
		   					
		   				}
		   				rs.close();
		   				
		   				
		   				if(hdKhId.trim().length() > 0)
		   				{
		   					
		   					hdKhId = hdKhId.substring(0, hdKhId.length() - 2);
		   					this.HdKhid = hdKhId.split("__");
		   					
		   					hdMakh = hdMakh.substring(0, hdMakh.length() - 2);
		   					this.HdMakh = hdMakh.split("__");
		   					
		   					hdDuno = hdDuno.substring(0, hdDuno.length() - 2);
		   					this.HdDuno=hdDuno.split("__");
		   					
		   					hdDuco = hdDuco.substring(0, hdDuco.length() - 2);
		   					this.HdDuco=hdDuco.split("__");
		   					
		   					hdGhino = hdGhino.substring(0, hdGhino.length() - 2);
		   					this.HdGhino=hdGhino.split("__");
		   					
		   					hdGhico = hdGhico.substring(0, hdGhico.length() - 2);
		   					this.HdGhico=hdGhico.split("__");
		   						   					
		   					hdChon = hdChon.substring(0, hdChon.length() - 2);
		   					this.HdChon= hdChon.split("__");
		   				}
		   				
		   			}
		   			else{
		   				
		   				System.out.println("adfdas");
		   				return;
		   			}
		   			
		   		}catch (Exception e) {
					e.printStackTrace();
				}
		   		
	   		}
	   		
	}
	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	public void DBclose()
	{
		try 
		{
			if(this.khlist != null)
				this.khlist.close();
			if(this.hoadonlist != null)
				this.hoadonlist.close();
			
			if(this.db != null)
				this.db.shutDown();
			
		} 
		catch(Exception e) {}
	}


	public String[] getHdId()
	{
		return this.HdId;
	}

	public void setHdId(String[] HdId) 
	{
		this.HdId = HdId;
		
	}


	public String[] getHdMakh() 
	{

		return this.HdMakh;
	}


	public void setHdMakh(String[] HdMakh)
	{

		this.HdMakh = HdMakh;
	}

	public String[] getHdMadt()
	{

		return this.HdMadt;
	}


	public void setHdMadt(String[] HdMadt) 
	{
		this.HdMadt = HdMadt;
		
	}


	public String[] getHdKyhieu() 
	{

		return this.HdKyhieu;
	}

	public void setHdKyhieu(String[] HdKyhieu) 
	{
		this.HdKyhieu = HdKyhieu;
		
	}


	public String[] getHdSohd() 
	{

		return this.HdSohd;
	}


	public void setHdSohd(String[] HdSohd) 
	{
		this.HdSohd = HdSohd;
		
	}


	public String[] getHdSotien() 
	{

		return this.HdSotien ;
	}


	public void setHdSotien(String[] HdSotien)
	{
		this.HdSotien = HdSotien;
	}

	public String[] getHdChon() 
	{
		return this.HdChon;
	}

	public void setHdChon(String[] hdChon) 
	{
		this.HdChon = hdChon;
	}

	public String[] getHdNgayhd() 
	{
		return this.HdNgayhd;
	}

	public void setHdNgayhd(String[] hdNgayhd) 
	{
		this.HdNgayhd = hdNgayhd;
	}

	public String[] getHdKhid() 
	{
		return this.HdKhid;
	}
	
	public void setHdKhid(String[] hdKhid) 
	{
		this.HdKhid = hdKhid;
	}

	public String getGhichu() 
	{
		return this.ghichu;
	}

	public void setGhichu(String ghichu)
	{
		this.ghichu = ghichu;
	}

	public String[] getHdIds() 
	{
		return this.HdIds;
	}


	public void setHdIds(String[] hdIds) 
	{
		this.HdIds = hdIds;
	}

	public String getSohoadontu() 
	{
		return this.sohoadontu;
	}

	public void setSohoadontu(String sohoadontu) 
	{
		this.sohoadontu = sohoadontu;
	}

	public String getSohoadonden() 
	{
		return this.sohoadonden;
	}

	public void setSohoadonden(String sohoadonden) 
	{
		this.sohoadonden = sohoadonden;
	}

	public String getSotientu() 
	{
		return this.sotientu ;
	}

	public void setSotientu(String sotientu) 
	{
		this.sotientu = sotientu;
	}

	public String getSotienden() 
	{
		return this.sotienden;
	}

	public void setSotienden(String sotienden) 
	{
		this.sotienden = sotienden;
	}

	
	public String getDdkdId() {
		
		return this.DdkdId;
	}

	
	public void setDdkdId(String ddkdId) {
		
		this.DdkdId=ddkdId;
	}

	
	public ResultSet getDdkdRs() {
		
		return this.Ddkdlist;
	}

	
	public void setDdkdRs(ResultSet ddkdRs) {
		
		this.Ddkdlist=ddkdRs;
	}

	
	public String[] getHdDuno() {
		
		return this.HdDuno;
	}

	
	public void setHdDuno(String[] HdDuno) {
		
		this.HdDuno=HdDuno;
	}

	
	public String[] getHdDuco() {
		
		return this.HdDuco;
	}

	
	public void setHdDuco(String[] HdDuco) {
		
		this.HdDuco=HdDuco;
	}

	
	public String[] getHdGhino() {
		
		return this.HdGhino;
	}

	
	public void setHdGhino(String[] HdGhino) {
		
		this.HdGhino=HdGhino;
	}

	
	public String[] getHdGhico() {
		
		return this.HdGhico;
	}

	
	public void setHdGhico(String[] HdGhico) {
		
		this.HdGhico=HdGhico;
	}

	
	public String[] getHdDunos() {
		
		return null;
	}

	
	public void setHdDunos(String[] HdDunos) {
		
		this.HdDunos=HdDunos;
	}

	
	public String[] getHdDucos() {
		
		return this.HdDucos;
	}

	
	public void setHdDucos(String[] HdDucos) {
		
		this.HdDucos=HdDucos;
	}

	
	public String[] getHdGhinos() {
		
		return this.HdGhinos;
	}

	
	public void setHdGhinos(String[] HdGhinos) {
		
		this.HdGhinos=HdGhinos;
	}

	
	public String[] getHdGhicos() {
		
		return this.HdGhicos;
	}

	
	public void setHdGhicos(String[] HdGhicos) {
		
		this.HdGhicos=HdGhicos;
	}

	
	public String getTc_duno() {
		
		return this.tc_duno_tc;
	}

	
	public void setTc_duno(String tc_duno_tc) {
		
		this.tc_duno_tc=tc_duno_tc;
	}

	
	public String getTc_duco() {
		
		return this.tc_duco_tc;
	}

	
	public void setTc_duco(String tc_duco_tc) {
		
		this.tc_duco_tc = tc_duco_tc;
	}

	
	public String getTc_ghico() {
		
		return this.tc_ghi_co;
	}

	
	public void setTc_ghico(String tc_ghico_tc) {
		
		this.tc_ghi_co=tc_ghico_tc;
	}

	
	public String getTc_ghino() {
		
		return this.tc_ghi_no;
	}

	
	public void setTc_ghino(String tc_ghino_tc) {
		
		this.tc_ghi_no = tc_ghino_tc;
	}

	
	public String getckKh() {
		
		return this.ckKh;
	}

	
	public void setckKh(String ckKh) {
		
		this.ckKh=ckKh;
	}

	
	public String[] getKhIds() {
		
		return this.KhIds;
	}
    





		
}
