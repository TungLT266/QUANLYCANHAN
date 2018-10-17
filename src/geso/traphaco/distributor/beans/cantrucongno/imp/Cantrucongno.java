package geso.traphaco.distributor.beans.cantrucongno.imp;

import geso.traphaco.distributor.beans.butrucongno.IButrucongno;
import geso.traphaco.distributor.beans.cantrucongno.ICantrucongno;
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

public class Cantrucongno implements ICantrucongno, Serializable
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
	
	String[] HdTienCK;
	
	ResultSet hoadonlist;
	String[] khIds;
	
	dbutils db;
	
	String tc_duno_tc;
	String tc_duco_tc;
	
	String tc_ghi_co;
	String tc_ghi_no;
	public Cantrucongno(String[] param)
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
		db = new dbutils();
	}
	
	public Cantrucongno(String id)
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

	public Hashtable<Integer, String> getKhIds() 
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
	}
	
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

		ResultSet rsctcn = null;
		System.out.print("Vào CreateNvgn()________");
		try 
		{
			db.getConnection().setAutoCommit(false);
		
			String query = " INSERT INTO CANTRUCONGNO(NGAYCHUNGTU, TRANGTHAI, GHICHU, NGAYTAO, NGAYSUA, NGUOITAO, NGUOISUA, NPP_FK) " +
							"values('"+this.ngay+"',0, N'" + this.ghichu + "','" + this.ngaytao + "','" + this.ngaytao + "','" + this.nguoitao +"','" + this.nguoitao +"','" + this.nppId + "')";
			System.out.print("INSERT CANTRUCONGNO________"+ query);
			
			if(!db.update(query))
			{
				db.update("rollback");
				this.msg = "Khong the tao moi 'CANTRUCONGNO' , " + query;
				return false; 
			}
			
				query = "select IDENT_CURRENT('CANTRUCONGNO') as ctcnId";
				
				rsctcn = this.db.get(query);	
				if(rsctcn!=null)
				{
				  while(rsctcn.next())
				  {
					  this.id = rsctcn.getString("ctcnId");
				  }
				  rsctcn.close();
				}
				
				if(this.HdIds.length > 0)
				{									
					for(int i = 0; i < this.HdIds.length; i++)
					{
						if(HdIds[i].length()>0)
						{
							String sql = "insert into CANTRUCONGNO_HOADON(CANTRUCONGNO_FK, HOADON_FK, KHACHHANG_FK, SOTIENCANTRU)" +
								         " values('" + this.id + "', '" + this.HdIds[i] + "', '" + this.HdKhid[i] + "', '" + this.HdTienCK[i].replaceAll(",", "") + "')";
							System.out.print("INSERT CANTRUCONGNO_HOADON _____ \n"+ sql);
							if(!db.update(sql))
							{
								db.update("rollback");
								this.msg = "Khong the insert bang CANTRUCONGNO_HOADON: " + sql;
								return false; 
							}
						}
					}
				}
			
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch(Exception e) {
			db.update("rollback");
			e.printStackTrace();
			this.msg = "Loi khi insert bang "+e.toString();
			return false; 
		}
		finally{try {
			if(rsctcn != null)
				rsctcn.close();
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
			
			String query = "update CANTRUCONGNO set ngaychungtu = '" + this.ngay + "', ghichu= N'" + this.ghichu + "', "+
			               "      nguoisua='" + this.nguoisua + "',  ngaysua='" + this.ngaysua + "', npp_fk = '"+ this.nppId +"' " +
			               " where pk_seq='" + this.id + "'";
			
			System.out.println("UPDATE CANTRUCONGNO ____"+ query);
			if(!db.update(query))
			{
				db.getConnection().rollback();
				this.msg = "Khong the cap nhat 'CANTRUCONGNO' , " + query;
				return false; 
			}	
			
			query = "delete CANTRUCONGNO_HOADON where CANTRUCONGNO_FK = '" + this.id + "'";
			
			System.out.println("DELETE CANTRUCONGNO_HOADON ____"+ query);
			if(!db.update(query))
			{
				db.getConnection().rollback();
				this.msg = query;
				return false;
			}
			
			System.out.println("độ dài:"+HdIds.length);
			if(this.HdIds.length > 0)
			{									
				for(int i = 0; i < this.HdIds.length; i++)
				{
					
					if(HdIds[i].length()>0)
					{
						String sql = "insert into CANTRUCONGNO_HOADON(CANTRUCONGNO_FK, HOADON_FK, KHACHHANG_FK, SOTIENCANTRU)" +
							         " values('" + this.id + "', '" + this.HdIds[i] + "', '" + this.HdKhid[i] + "', '" + this.HdTienCK[i].replaceAll(",", "") + "')";
						System.out.print("INSERT CANTRUCONGNO_HOADON _____ \n"+ sql);
						if(!db.update(sql))
						{
							db.update("rollback");
							this.msg = "Khong the insert bang CANTRUCONGNO_HOADON: " + sql;
							return false; 
						}
					}
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
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
		String query =  " select a.pk_seq , a.ngaychungtu, isnull(a.ghichu,'') as ghichu ";
		query = query + " from BUTRUCONGNO a inner join nhanvien b on a.nguoitao = b.pk_seq inner join nhanvien c on a.nguoisua = c.pk_seq " +
				        " where a.npp_fk='" + this.nppId + "' and a.pk_seq = '"+ this.id +"' " +
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
       
        createDDKDRS();
        createKHRS();
		
        createRS();
	}
	
	public void createDDKDRS(){
		//GET DAI DIEN KINH DOANH
		String query = "select pk_seq, ten from DAIDIENKINHDOANH where trangthai = '1' and pk_seq in (select ddkd_fk from daidienkinhdoanh_npp where npp_fk='"+this.nppId+"')";
		this.Ddkdlist = db.get(query);
		System.out.println("Cau INIT DDKD _______:" + query);		
	}
	
	public void createKHRS(){
		//GET KHACH HANG
		String query ="select distinct  kh.PK_SEQ, (kh.maFast+' - '+ kh.Ten) as Ten " +
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
			//CHON PHIEU CAN TRU CONG NO
		query+=
			" SELECT  ct.NGAYCHUNGTU,kh.PK_SEQ as khId, (kh.maFAST + ' - '+ kh.TEN) as tenKH, hd.PK_SEQ as Id,hd.SOHOADON, hd.NGAYXUATHD,  \n"+
			" 		  hd.tongtienavat , ct_hd.SOTIENCANTRU TienCK, ct_hd.HOADON_FK as CHON \n"+	   
			" FROM 	  CANTRUCONGNO  ct inner join CANTRUCONGNO_HOADON ct_hd on ct.PK_SEQ = ct_hd.CANTRUCONGNO_FK \n"+
			"			 			   inner join HOADON hd on ct_hd.HOADON_FK = hd.PK_SEQ \n"+
			"			 			   inner join KHACHHANG kh on ct_hd.KHACHHANG_FK = kh.PK_SEQ \n"+
			" WHERE   ct.PK_SEQ ='"+this.id+"' \n";
		}
		
		if(this.tungay.trim().length() > 0 || this.denngay.trim().length() > 0 || this.sohoadontu.trim().length() > 0 || this.sohoadonden.trim().length() > 0 || this.sotientu.trim().length() > 0 || this.sotienden.trim().length() > 0)
		  {
			
			if(this.id.trim().length() > 0)
			{
				query +=" UNION ALL  \n";
			}
						
			query+=
				" SELECT '' as NGAYCHUNGTU,hd.KHACHHANG_FK as khId,(kh.maFAST + ' - ' + kh.TEN) as tenKH,hd.PK_SEQ as Id, hd.SOHOADON, hd.NGAYXUATHD, hd.tongtienavat, round(isnull(hd_ck.TienCK,0),0) TienCK, 0 as CHON \n"+
				" FROM HOADON hd left join \n"+
				"		( SELECT hd_ck.hoadon_fk, SUM( round ( round(  isnull(hd_ck.chietkhau,0), 0 ) * ( 1 + thueVAT / 100 ), 0 ) ) as TienCK \n"+
				"	  	  FROM	 HOADON_CHIETKHAU hd_ck \n"+
				"	  	  WHERE  hd_ck.diengiai = 'CT5' \n"+
				"	  	  GROUP BY hd_ck.hoadon_fk \n"+
				"		)hd_ck on hd_ck.HOADON_FK=hd.PK_SEQ \n"+
				"		left join KHACHHANG kh on hd.KHACHHANG_FK= kh.PK_SEQ \n"+
				"  WHERE hd.TRANGTHAI in (2,4) and hd.LOAIHOADON=0 and TienCK!=0 \n"+
			  	"		 and hd_ck.hoadon_fk not in \n"+ 
			  	"		( \n"+
			  	"			select ct_hd.HOADON_FK \n"+
				"           from CANTRUCONGNO ct INNER JOIN CANTRUCONGNO_HOADON ct_hd \n"+ 
				"	 		on CT.PK_SEQ= ct_hd.CANTRUCONGNO_FK \n"+
				"   		where CT.TRANGTHAI!=2 \n" +
				"		) \n";
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
								
				if(this.id.trim().length() > 0)
					 query+= "    and hd.PK_SEQ NOT IN (select hoadon_fk from BUTRUCONGNO_HOADON where btcn_fk =  '"+ this.id +"') \n"; 
				
				if(this.tungay.trim().length() > 0)
				    query += "     and hd.NGAYXUATHD >= '"+ this.tungay +"' \n";
				
				if(this.denngay.trim().length() > 0)				   
				    query += "     and hd.NGAYXUATHD <= '"+ this.denngay +"' \n";
				
				if(this.sohoadontu.trim().length() > 0)
				    query += "     and cast(hd.SOHOADON as numeric(18,0) ) >= '"+ Integer.parseInt(this.sohoadontu) +"' \n";
				if(this.sohoadonden.trim().length() > 0)
				    query += "     and cast(hd.SOHOADON as numeric(18,0) ) <= '"+ Integer.parseInt(this.sohoadonden) +"' \n";
				if(this.sotientu.trim().length() > 0)
				   {
					 double st = Double.parseDouble(this.sotientu.replaceAll(",", ""));
					 query += "     and hd_ck.TienCK >= "+ st +" \n";
				   }
				if(this.sotienden.trim().length() > 0)
				   {
					   double sd = Double.parseDouble(this.sotienden.replaceAll(",", ""));
					   query += "   and hd_ck.TienCK <= "+ sd +" \n";
				   }
				
			   if(this.khId.trim().length()>0)
			   {
				   query +=" and hd.KHACHHANG_FK ='"+this.khId+"'";			   
			   }
		  }

			   
			 query += "ORDER BY NGAYXUATHD DESC ";   
		
	   		System.out.println("CAU LAY CAN TRU CONG NO createRS(.........): _____________ "+query);
	   		NumberFormat formatter = new DecimalFormat("#,###,###"); 
	   		ResultSet rs = db.get(query);

	   		try
	   		{
	   			String hdNgaychungtu="";
	   			String hdId = "" ;
				String hdKhId = "" ;
				String hdMakh = "" ;
				String hdNgayhd = "" ;
				String hdSohoadon = "" ;
				String hdTienCK="";
				String hdSoTienAvat="";
				String hdChon ="";
				
				
	   			if(rs!= null)
	   			{ 
	   				while(rs.next())
	   				{	
	   					hdNgaychungtu= rs.getString("NGAYCHUNGTU");
	   					hdId += rs.getString("Id") + "__";
	   					hdKhId += rs.getString("khId") + "__";
	   					hdMakh+= rs.getString("tenKH") + "__";
	   					hdNgayhd += rs.getString("ngayxuathd")+ "__";
	   					hdSohoadon += rs.getString("sohoadon")+ "__";
	   					hdSoTienAvat += rs.getString("tongtienavat")+ "__";
	   					hdTienCK+=rs.getString("tienCK")+ "__";
	   					hdChon += rs.getString("chon")+"__";
	   					//tổng tiền avat
		   				this.tc_duno_tc = formatter.format((Double.parseDouble(this.tc_duno_tc.replaceAll(",", ""))+ rs.getDouble("tongtienavat")));
		   				//tổng tiền chiết khấu
		   				if(!rs.getString("chon").equals("0"))
		   					this.tc_duco_tc =formatter.format((Double.parseDouble(this.tc_duco_tc.replaceAll(",", ""))+rs.getDouble("tienCK")));
	   				
	   					
	   				}
	   				rs.close();
	   				
	   				
	   				if(hdId.trim().length() > 0)
	   				{
	   					
	   					this.ngay= hdNgaychungtu;
	   					
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
	   					
	   					hdSoTienAvat = hdSoTienAvat.substring(0, hdSoTienAvat.length() - 2);
	   					this.HdSotien= hdSoTienAvat.split("__");
	   					
	   					hdTienCK = hdTienCK.substring(0, hdTienCK.length() - 2);
	   					this.HdTienCK = hdTienCK.split("__");
	   						   					
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

	
	public String[] getHdTienCK() {
		
		return this.HdTienCK;
	}

	
	public void setHdTienCK(String[] HdTienCK) {
		
		this.HdTienCK=HdTienCK;
	}
    





		
}
