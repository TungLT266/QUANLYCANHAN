package geso.traphaco.distributor.beans.xoanokhachhang.imp;

import geso.traphaco.distributor.beans.xoanokhachhang.IXoanokhachhang;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

public class Xoanokhachhang implements IXoanokhachhang, Serializable
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

	String[] HdIds;
	String[] KhIdss;
	String[] HdTienCKs;
	String[] HdLoaiHds;
	
	String[] HdChon;
	String[] HdId;
	String[] HdKhid;
	String[] HdMakh;
	String[] HdMadt;
	String[] HdNgayhd;
	String[] HdKyhieu;
	String[] HdSohd;
	String[] HdSotien;
	String[] HdLoaiHd;
	
	ResultSet hoadonlist;
	String[] khIds;
	
	dbutils db;
	
	public Xoanokhachhang(String[] param)
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
		this.msg = "";
		db = new dbutils();
	}
	
	public Xoanokhachhang(String id)
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
		ResultSet rsXnkh = null;
		try 
		{
			db.getConnection().setAutoCommit(false);
		
			String query = "insert into XOANOKHACHHANG(NGAYCHUNGTU, TRANGTHAI, GHICHU, NGAYTAO, NGAYSUA, NGUOITAO, NGUOISUA, NPP_FK) " ;
			query = query + "values('" + this.ngay + "','0', N'" + this.ghichu + "','" + this.ngaytao + "','" + this.ngaytao + "','" + this.nguoitao +"','" + this.nguoitao +"','" + this.nppId + "')";
			System.out.print(query);
			
			if(!db.update(query))
			{
				db.update("rollback");
				this.msg = "Khong the tao moi 'XOANOKHACHHANG' , " + query;
				return false; 
			}
			
				query = "select IDENT_CURRENT('XOANOKHACHHANG') as xnkhId";
				
				rsXnkh = this.db.get(query);					
				rsXnkh.next();
				this.id = rsXnkh.getString("xnkhId");
				rsXnkh.close();
				
				if(this.HdIds.length > 0)
				{				
					for(int i = 0; i < this.HdIds.length; i++)
					{					
						String sql = "INSERT INTO XOANOKHACHHANG_HOADON(XNKH_FK, HOADON_FK, KHACHHANG_FK, SOTIENXOA, LOAIHD)" +
							         " values('" + this.id + "', '" + this.HdIds[i] + "', '" + this.KhIdss[i] + "', '" + this.HdTienCKs[i].replaceAll(",", "") + "', "+this.HdLoaiHds[i]+")";
						System.out.println(sql);
						if(!db.update(sql))
						{
							db.update("rollback");
							this.msg = "Khong the cap nhat XOANOKHACHHANG_HOADON, " + sql;
							return false; 
						}
					}
				}
			
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch(Exception e) {
			db.update("rollback");
			e.printStackTrace();
			this.msg = "Loi khi cap nhat bang "+e.toString();
			return false; 
		}
		finally{try {
			if(rsXnkh != null)
				rsXnkh.close();
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
			
			String query = "UPDATE XOANOKHACHHANG set ngaychungtu = '" + this.ngay + "', ghichu= N'" + this.ghichu + "', "+
			               "      nguoisua='" + this.nguoisua + "',  ngaysua='" + this.ngaysua + "', npp_fk = '"+ this.nppId +"' " +
			               "WHERE pk_seq='" + this.id + "'";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				this.msg = "Khong the cap nhat 'NhanVienGiaoNhan' , " + query;
				return false; 
			}	
			
			query = "delete XOANOKHACHHANG_HOADON where XNKH_FK = '" + this.id + "'";
			System.out.println(query);
			if(!db.update(query))
			{
				db.getConnection().rollback();
				this.msg = query;
				return false;
			}
			
			if(this.HdIds.length > 0)
			{				
				for(int i = 0; i < this.HdIds.length; i++)
				{					
					String sql = "INSERT INTO XOANOKHACHHANG_HOADON(XNKH_FK, HOADON_FK, KHACHHANG_FK, SOTIENXOA, LOAIHD)" +
						         " values('" + this.id + "', '" + this.HdIds[i] + "', '" + this.KhIdss[i] + "', '" + this.HdTienCKs[i].replaceAll(",", "")+ "', "+this.HdLoaiHds[i]+")";
					System.out.println(sql);
					if(!db.update(sql))
					{
						db.update("rollback");
						this.msg = "Khong the cap nhat XOANOKHACHHANG_HOADON, " + sql;
						return false; 
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
		query = query + " from XOANOKHACHHANG a inner join nhanvien b on a.nguoitao = b.pk_seq inner join nhanvien c on a.nguoisua = c.pk_seq " +
				        " where a.npp_fk='" + this.nppId + "' and a.pk_seq = '"+ this.id +"' ";
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

	public void createRS() 
	{
		this.getNppInfo();
		
		
	        
		String query = "";
		 query ="select kh.PK_SEQ, (kh.maFast+' - '+ kh.Ten) as Ten " +
			"	from KHACHHANG kh where 1=1 and kh.npp_fk= '"+this.nppId+"' ";
	        
	        System.out.println("KH___"+query);
	        this.khlist= db.get(query);
	        
	      query="";
		if(this.id.trim().length() > 0)
		{
		  //OTC
			query += "select hd.PK_SEQ " +
					" as Id, hd.NGAYXUATHD, hd.KYHIEU, hd.SOHOADON, hd.KHACHHANG_FK as khId, kh.MAFAST + '-' + kh.TEN as tenKH , \n" +
					 "       xnhd.SOTIENXOA as Sotienconlai ,xnhd.LOAIHD ,1 as CHON \n" +
					 "from XOANOKHACHHANG xnkh inner join XOANOKHACHHANG_HOADON xnhd on xnkh.PK_SEQ= xnhd.XNKH_FK and xnhd.KHACHHANG_FK  in (select PK_SEQ from KHACHHANG where KBH_FK = '100025' ) \n" +
					 "                         inner join HOADON hd on xnhd.HOADON_FK = hd.PK_SEQ \n" +
					 "                         inner join KHACHHANG kh on xnhd.KHACHHANG_FK = kh.PK_SEQ  \n" +
					 "where xnkh.PK_SEQ = '"+ this.id +"' \n";
			
			query +="UNION ALL  \n";

		 // ETC	
			query += "select hd.PK_SEQ as Id, hd.NGAYXUATHD, hd.KYHIEU, hd.SOHOADON, hd.KHACHHANG_FK as khId, kh.MAFAST + '-' + kh.TEN as tenKH , \n" +
			 "       xnhd.SOTIENXOA  as Sotienconlai , xnhd.LOAIHD, 1 as CHON \n" +
			 "from XOANOKHACHHANG xnkh inner join XOANOKHACHHANG_HOADON xnhd on xnkh.PK_SEQ= xnhd.XNKH_FK and xnhd.KHACHHANG_FK  in (select PK_SEQ from KHACHHANG where KBH_FK = '100052' ) \n" +
			 "                         inner join ERP_HOADONNPP hd on xnhd.HOADON_FK = hd.PK_SEQ \n" +
			 "                         inner join KHACHHANG kh on xnhd.KHACHHANG_FK = kh.PK_SEQ  \n" +
			 "where xnkh.PK_SEQ = '"+ this.id +"' \n";
		
			query +="UNION ALL  \n";

		// DUNO_KH	
			query += "select hd.PK_SEQ as Id, hd.NgayDuNo, 'GES0' KYHIEU, 'GES0' SOHOADON, hd.KHACHHANG_FK as khId, kh.MAFAST + '-' + kh.TEN as tenKH ,  \n" +
			 "       xnhd.SOTIENXOA  as Sotienconlai ,xnhd.LOAIHD ,1 as CHON  \n" +
			 "from XOANOKHACHHANG xnkh inner join XOANOKHACHHANG_HOADON xnhd on xnkh.PK_SEQ= xnhd.XNKH_FK \n"+
			 "                          inner join DUNO_KHACHHANG hd on xnhd.HOADON_FK = hd.PK_SEQ  \n" +
			 "                          inner join KHACHHANG kh on xnhd.KHACHHANG_FK = kh.PK_SEQ   \n" +
			 "where xnkh.PK_SEQ = '"+ this.id +"' \n";
			
		//HOADONKHAC
			query +="UNION ALL  \n";
			
			query += "select hd.PK_SEQ as Id, hd.Ngayhoadon, hd.KYHIEUHOADON KYHIEU, hd.SOHOADON SOHOADON, hd.KHACHHANG_FK as khId, kh.MAFAST + '-' + kh.TEN as tenKH ,  \n" +
			 "       xnhd.SOTIENXOA  as Sotienconlai ,xnhd.LOAIHD ,1 as CHON  \n" +
			 "FROM XOANOKHACHHANG xnkh inner join XOANOKHACHHANG_HOADON xnhd on xnkh.PK_SEQ= xnhd.XNKH_FK \n"+
			 "                          inner join ERP_HOADONPHELIEU hd on xnhd.HOADON_FK = hd.PK_SEQ  \n" +
			 "                          inner join KHACHHANG kh on xnhd.KHACHHANG_FK = kh.PK_SEQ   \n" +
			 "WHERE xnkh.PK_SEQ = '"+ this.id +"' \n";
		}
		
		if(this.tungay.trim().length() > 0 || this.denngay.trim().length() > 0 || this.sohoadontu.trim().length() > 0 || this.sohoadonden.trim().length() > 0 || this.sotientu.trim().length() > 0 || this.sotienden.trim().length() > 0)
		  {
			String conditionKH="";
			String conditionbthd = " ";
			String conditionxnkh=" ";
			String conditiontt="";
			if(this.khId.trim().length()>0)
   			{
				conditionKH = " and PK_SEQ ='"+this.khId+"' \n";
   			}
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
				query +="UNION ALL  \n";
			}
		   // OTC
		   query += "SELECT hd.PK_SEQ as Id, hd.NGAYXUATHD, hd.KYHIEU , hd.SOHOADON, hd.KHACHHANG_FK as khId, kh.MAFAST + '-' + kh.TEN as tenKH , \n" +
		   		    "       cast((hd.tongtienavat + ISNULL(BUTRU.GHINO,0)- ISNULL(THUTIEN.DATHU,0) - ISNULL(XOANO.DAXOA,0) - ISNULL(BUTRU.GHICO,0))as numeric(18,0)) as Sotienconlai, 0 AS LOAIHD ,0 as CHON \n"+
					"FROM HOADON hd LEFT JOIN \n"+
					"     	(" +
					"			SELECT 	tthd.HOADONNPP_FK, SUM(tthd.SOTIENTT) as DATHU \n"+
					"     	 	FROM 	ERP_THUTIENNPP_HOADON tthd inner join ERP_THUTIENNPP tt on tthd.THUTIENNPP_FK=tt.PK_SEQ  \n"+
					"      	 	WHERE 	tthd.LOAIHD =0 AND tt.TRANGTHAI !=2 and tthd.KHACHHANG_FK in \n " +
					"															( \n" +
					"																SELECT PK_SEQ from KHACHHANG where KBH_FK = '100025' " +conditionKH+		   			
					"															 ) \n"+ conditiontt+
					"     	 	GROUP BY tthd.HOADONNPP_FK  \n"+
					"       )as THUTIEN on hd.PK_SEQ = THUTIEN.HOADONNPP_FK \n"+
					"     LEFT JOIN \n"+
			        "      (" +
			        "			SELECT 	xnhd.HOADON_FK, SUM(xnhd.SOTIENXOA) as DAXOA \n"+
					"       	FROM 	XOANOKHACHHANG_HOADON xnhd inner join XOANOKHACHHANG xn on xnhd.XNKH_FK=xn.PK_SEQ  \n"+
					"       	WHERE 	xnhd.LOAIHD = 0 AND xn.TRANGTHAI != 2 and xnhd.KHACHHANG_FK in \n " +
					"															(" +
					"																SELECT PK_SEQ from KHACHHANG where KBH_FK = '100025' " + conditionKH+
					"															) \n"+ conditionxnkh+
					"       GROUP BY xnhd.HOADON_FK  \n"+
			        "      )as XOANO on hd.PK_SEQ = XOANO.HOADON_FK \n"+
				    "    LEFT JOIN \n"+ 
			        "      ( \n"+
					"			SELECT 	bthd.HOADON_FK, SUM(bthd.GHINO) as GHINO, SUM(bthd.GHICO) as GHICO \n"+
					"			FROM	BUTRUCONGNO_HOADON bthd inner join BUTRUCONGNO bt on bthd.BTCN_FK=bt.PK_SEQ \n"+
					"			WHERE 	bt.TRANGTHAI = 1 and bthd.KHACHHANG_FK in " +
					"															( \n" +
					"																SELECT PK_SEQ from KHACHHANG where KBH_FK ='100025'" + conditionKH+
					"														    ) \n"+ conditionbthd+
					"			GROUP BY bthd.HOADON_FK \n"+
			        "      ) as BUTRU on hd.PK_SEQ = BUTRU.HOADON_FK \n"+
			        "    inner join KHACHHANG kh on hd.KHACHHANG_FK = kh.PK_SEQ \n"+     
					"WHERE hd.NPP_FK ='"+this.nppId+"' and hd.trangthai in (2,4) and cast((hd.tongtienavat + ISNULL(BUTRU.GHINO,0)- ISNULL(THUTIEN.DATHU,0) - ISNULL(XOANO.DAXOA,0) - ISNULL(BUTRU.GHINO,0))as numeric(18,0)) > 0 ";
		  
		   if(this.id.trim().length() > 0)
		   {
			 query += "     and hd.PK_SEQ NOT IN (select hoadon_fk from XOANOKHACHHANG_HOADON where xnkh_fk =  '"+ this.id +"') \n";   
		   }
		   
		   // LỌC HÓA ĐƠN
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
		    query += "     and cast((hd.tongtienavat + ISNULL(BUTRU.GHINO,0)- ISNULL(THUTIEN.DATHU,0) - ISNULL(XOANO.DAXOA,0) - ISNULL(BUTRU.GHICO,0)) >= '"+ Double.parseDouble(this.sotientu.replaceAll(",", "")) +"' \n";
		   }
		   if(this.sotienden.trim().length() > 0)
		   {
		    query += "     and cast((hd.tongtienavat + ISNULL(BUTRU.GHINO,0)- ISNULL(THUTIEN.DATHU,0) - ISNULL(XOANO.DAXOA,0) - ISNULL(BUTRU.GHICO,0)) <= '"+ Double.parseDouble(this.sotienden.replaceAll(",", "")) +"' \n";
		   }
		   if(this.khId.trim().length()>0)
		   {
			   query+="  and hd.KHACHHANG_FK ='"+khId+"'";
		   }
			query+=
				
				//DUNO_KHACHHANG
				
		" UNION ALL \n"+
		
			" SELECT hd.PK_SEQ as Id, hd.NgayDuNo , 'GESO' as KYHIEU , 'GESO' as SOHOADON , hd.KHACHHANG_FK as khId, kh.MAFAST + '-' + kh.TEN as tenKH , \n"+ 
		    "   cast((hd.SONO + ISNULL(BUTRU.GHINO,0)- ISNULL(THUTIEN.DATHU,0) - ISNULL(XOANO.DAXOA,0) - ISNULL(BUTRU.GHINO,0))as numeric(18,0)) as Sotienconlai, 2 LOAIHD ,0 as CHON \n"+ 
		    " FROM DUNO_KHACHHANG hd LEFT JOIN \n"+ 
		    " 	( \n"+ 
			"		SELECT 	tthd.HOADONNPP_FK, SUM(tthd.SOTIENTT) as DATHU \n"+ 
		    "  		FROM 	ERP_THUTIENNPP_HOADON tthd inner join ERP_THUTIENNPP tt on tthd.THUTIENNPP_FK=tt.PK_SEQ \n"+  
		    "  		WHERE 	tthd.LOAIHD = 2 AND tt.TRANGTHAI !=2 \n"+conditiontt+    
			"		GROUP BY tthd.HOADONNPP_FK \n" + 
		    "  	)as THUTIEN on hd.PK_SEQ = THUTIEN.HOADONNPP_FK \n"+ 
		    "	LEFT JOIN \n"+ 
		    "	(	SELECT 	xnhd.HOADON_FK, SUM(xnhd.SOTIENXOA) as DAXOA \n"+ 
		   	"		FROM 	XOANOKHACHHANG_HOADON xnhd inner join XOANOKHACHHANG xn on xnhd.XNKH_FK=xn.PK_SEQ \n"+  
		   	"		WHERE 	xnhd.LOAIHD = 2 AND xn.TRANGTHAI != 2 "+ conditionxnkh+			
		   	"		GROUP BY xnhd.HOADON_FK \n"+  
		    "    )as XOANO on hd.PK_SEQ = XOANO.HOADON_FK \n"+ 
			"	LEFT JOIN \n"+ 
		    "  ( \n"+ 
			"		SELECT 	bthd.HOADON_FK, SUM(bthd.GHINO) as GHINO, SUM(bthd.GHICO) as GHICO \n"+ 
			"		FROM 	BUTRUCONGNO_HOADON bthd inner join BUTRUCONGNO bt on bthd.BTCN_FK=bt.PK_SEQ \n"+ 
			"		WHERE 	bt.TRANGTHAI = 1 \n"+ conditionbthd+		
			"		GROUP BY bthd.HOADON_FK \n"+ 
		    "  ) as BUTRU on hd.PK_SEQ = BUTRU.HOADON_FK \n"+ 
		    " inner join KHACHHANG kh on hd.KHACHHANG_FK = kh.PK_SEQ \n"+ 
		    "WHERE hd.NPP_FK ='"+this.nppId+"' and hd.SONO > 0 and cast((hd.SONO + ISNULL(BUTRU.GHINO,0)- ISNULL(THUTIEN.DATHU,0) - ISNULL(XOANO.DAXOA,0) - ISNULL(BUTRU.GHINO,0))as numeric(18,0)) > 0 "; 
			   // LỌC HÓA ĐƠN
			   if(this.tungay.trim().length() > 0)
			   {
			    query += "     and hd.NgayDuNo >= '"+ this.tungay +"' \n";
			   }
			   if(this.denngay.trim().length() > 0)
			   {
			    query += "     and hd.NgayDuNo <= '"+ this.denngay +"' \n";
			   }			  
			   if(this.sotientu.trim().length() > 0)
			   {
			    query += "     and cast((hd.SONO + ISNULL(BUTRU.GHINO,0)- ISNULL(THUTIEN.DATHU,0) - ISNULL(XOANO.DAXOA,0) - ISNULL(BUTRU.GHINO,0))as numeric(18,0)) >= '"+ Double.parseDouble(this.sotientu.replaceAll(",", "")) +"' \n";
			   }
			   if(this.sotienden.trim().length() > 0)
			   {
			    query += "     and cast((hd.SONO + ISNULL(BUTRU.GHINO,0)- ISNULL(THUTIEN.DATHU,0) - ISNULL(XOANO.DAXOA,0) - ISNULL(BUTRU.GHINO,0))as numeric(18,0)) <= '"+ Double.parseDouble(this.sotienden.replaceAll(",", "")) +"' \n";
			   }
			   if(this.khId.trim().length()>0)
			   {
				   query+="  and hd.KHACHHANG_FK ='"+khId+"'";
			   }
			query +="UNION ALL  \n"+
					" \n"+
			//ETC 
					"SELECT hd.PK_SEQ as Id, hd.NGAYXUATHD , hd.KYHIEU , hd.SOHOADON, hd.KHACHHANG_FK as khId, kh.MAFAST + '-' + kh.TEN as tenKH , \n" +
					"       cast((hd.tongtienavat + ISNULL(BUTRU.GHINO,0)- ISNULL(THUTIEN.DATHU,0) - ISNULL(XOANO.DAXOA,0) - ISNULL(BUTRU.GHINO,0))as numeric(18,0)) as Sotienconlai, 0 AS LOAIHD ,0 as CHON \n"+
					"FROM 	ERP_HOADONNPP hd LEFT JOIN  \n"+
					"     	( \n" +
					"			SELECT 	tthd.HOADONNPP_FK, SUM(tthd.SOTIENTT) as DATHU \n"+
					"      		FROM 	ERP_THUTIENNPP_HOADON tthd inner join ERP_THUTIENNPP tt on tthd.THUTIENNPP_FK=tt.PK_SEQ  \n"+
					"      		WHERE 	tthd.LOAIHD = 0  AND tt.TRANGTHAI !=2 and tthd.KHACHHANG_FK in (select PK_SEQ from KHACHHANG where KBH_FK = '100052' " +conditionKH+
					") \n"+ conditiontt+
					"      		GROUP BY tthd.HOADONNPP_FK  \n"+
					"      	)as THUTIEN on hd.PK_SEQ = THUTIEN.HOADONNPP_FK \n"+
					"    	LEFT JOIN \n"+
			        "      (" +
			        "			SELECT 	xnhd.HOADON_FK, SUM(xnhd.SOTIENXOA) as DAXOA \n"+
					"       	FROM 	XOANOKHACHHANG_HOADON xnhd inner join XOANOKHACHHANG xn on xnhd.XNKH_FK=xn.PK_SEQ  \n"+
					"       	WHERE 	xnhd.LOAIHD = 0 AND xn.TRANGTHAI != 2 and xnhd.KHACHHANG_FK in \n " +
					"															(" +
					"																SELECT PK_SEQ from KHACHHANG where KBH_FK = '100052' " +conditionKH+
					"															 ) \n"+ conditionxnkh+
					"			GROUP BY xnhd.HOADON_FK  \n "+
			        "       )as XOANO on hd.PK_SEQ = XOANO.HOADON_FK \n" +
			        "		LEFT JOIN \n"+ 
			        "      ( \n"+
					"			SELECT 	bthd.HOADON_FK, SUM(bthd.GHINO) as GHINO, SUM(bthd.GHICO) as GHICO \n"+
					"			FROM 	BUTRUCONGNO_HOADON bthd inner join BUTRUCONGNO bt on bthd.BTCN_FK=bt.PK_SEQ \n"+
					"			WHERE 	bt.TRANGTHAI = 1 and bthd.KHACHHANG_FK in " +
					"															(" +
					"																SELECT PK_SEQ from KHACHHANG where KBH_FK ='100052'" +conditionKH+
					"															 ) \n"+conditionbthd+
					"		GROUP BY bthd.HOADON_FK \n"+
			        "      ) as BUTRU on hd.PK_SEQ = BUTRU.HOADON_FK \n"+
			        "    inner join KHACHHANG kh on hd.KHACHHANG_FK = kh.PK_SEQ \n"+
					"WHERE hd.NPP_FK='"+nppId+"' and hd.trangthai in (2,4) and cast((hd.tongtienavat + ISNULL(BUTRU.GHINO,0)- ISNULL(THUTIEN.DATHU,0) - ISNULL(XOANO.DAXOA,0) - ISNULL(BUTRU.GHINO,0))as numeric(18,0)) > 0 \n";
			
			 if(this.id.trim().length() > 0)
			   {
				 query += "     and hd.PK_SEQ not in (select hoadon_fk from XOANOKHACHHANG_HOADON where xnkh_fk =  '"+ this.id +"') \n";   
			   }
			
			 // LỌC HÓA ĐƠN
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
			    query += "     and cast((hd.tongtienavat + ISNULL(BUTRU.GHINO,0)- ISNULL(THUTIEN.DATHU,0) - ISNULL(XOANO.DAXOA,0) - ISNULL(BUTRU.GHINO,0))as numeric(18,0))as numeric(18,0)) >= '"+ Double.parseDouble(this.sotientu.replaceAll(",", "")) +"' \n";
			   }
			   if(this.sotienden.trim().length() > 0)
			   {
			    query += "     and cast((hd.tongtienavat + ISNULL(BUTRU.GHINO,0)- ISNULL(THUTIEN.DATHU,0) - ISNULL(XOANO.DAXOA,0) - ISNULL(BUTRU.GHINO,0))as numeric(18,0))as numeric(18,0)) <= '"+ Double.parseDouble(this.sotienden.replaceAll(",", "")) +"' \n";
			   }
			   if(this.khId.trim().length()>0)
			   {
				   query+="  and hd.KHACHHANG_FK ='"+khId+"'";
			   }
			   
			   query +="UNION ALL  \n";
			   
			// HOADON KHAC
			   query += "SELECT hd.PK_SEQ as Id, hd.NGAYHOADON NGAYXUATHD, hd.KYHIEUHOADON KYHIEU , hd.SOHOADON, hd.KHACHHANG_FK as khId, kh.MAFAST + '-' + kh.TEN as tenKH , \n" +
			   		    "       cast((hd.avat - ISNULL(THUTIEN.DATHU,0) - ISNULL(XOANO.DAXOA,0))as numeric(18,0)) as Sotienconlai,1 as LOAIHD ,0 as CHON \n"+
						"FROM ERP_HOADONPHELIEU hd LEFT JOIN \n"+
						"     	(" +
						"			SELECT 	tthd.HOADONNPP_FK, SUM(tthd.SOTIENTT) as DATHU \n"+
						"     	 	FROM 	ERP_THUTIENNPP_HOADON tthd inner join ERP_THUTIENNPP tt on tthd.THUTIENNPP_FK=tt.PK_SEQ  \n"+
						"      	 	WHERE 	tthd.LOAIHD = 1 AND tt.TRANGTHAI !=2 "+ conditiontt+
						"     	 	GROUP BY tthd.HOADONNPP_FK  \n"+
						"       )as THUTIEN on hd.PK_SEQ = THUTIEN.HOADONNPP_FK \n"+
						"     LEFT JOIN \n"+
				        "      (" +
				        "			SELECT 	xnhd.HOADON_FK, SUM(xnhd.SOTIENXOA) as DAXOA \n"+
						"       	FROM 	XOANOKHACHHANG_HOADON xnhd inner join XOANOKHACHHANG xn on xnhd.XNKH_FK=xn.PK_SEQ  \n"+
						"       	WHERE 	xnhd.LOAIHD = 1 AND xn.TRANGTHAI != 2 \n " + conditionxnkh+
						"       GROUP BY xnhd.HOADON_FK  \n"+
				        "      )as XOANO on hd.PK_SEQ = XOANO.HOADON_FK \n"+					    
				        "    inner join KHACHHANG kh on hd.KHACHHANG_FK = kh.PK_SEQ \n"+     
						"WHERE hd.NPP_FK ='"+this.nppId+"' and hd.trangthai = 1 and cast((hd.avat - ISNULL(THUTIEN.DATHU,0) - ISNULL(XOANO.DAXOA,0) )as numeric(18,0)) > 0 ";
			  
			   if(this.id.trim().length() > 0)
			   {
				 query += "     and hd.PK_SEQ NOT IN (select hoadon_fk from XOANOKHACHHANG_HOADON where xnkh_fk =  '"+ this.id +"') \n";   
			   }
			   
			   // LỌC HÓA ĐƠN
			   if(this.tungay.trim().length() > 0)
			   {
			    query += "     and hd.NGAYHOADON >= '"+ this.tungay +"' \n";
			   }
			   if(this.denngay.trim().length() > 0)
			   {
			    query += "     and hd.NGAYHOADON <= '"+ this.denngay +"' \n";
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
			    query += "     and cast((hd.avat - ISNULL(THUTIEN.DATHU,0) - ISNULL(XOANO.DAXOA,0))as numeric(18,0)) >= '"+ Double.parseDouble(this.sotientu.replaceAll(",", "")) +"' \n";
			   }
			   if(this.sotienden.trim().length() > 0)
			   {
			    query += "     and cast((hd.avat - ISNULL(THUTIEN.DATHU,0) - ISNULL(XOANO.DAXOA,0))as numeric(18,0)) <= '"+ Double.parseDouble(this.sotienden.replaceAll(",", "")) +"' \n";
			   }
			   if(this.khId.trim().length()>0)
			   {
				   query+="  and hd.KHACHHANG_FK ='"+khId+"'";
			   }
		  }   
			   
			 query += "ORDER BY CHON desc, NGAYXUATHD DESC ";   
			   
		
		
	   		System.out.println("Câu lấy hóa đơn "+query);
	   		ResultSet rs = db.get(query);
	   		
	   		try
	   		{
	   			String hdId = "" ;
				String hdKhId = "" ;
				String hdMakh = "" ;
				String hdNgayhd = "" ;
				String hdSohoadon = "" ;
				String hdKyhieu = "" ;
				String hdSotien = "" ;
				String hdLoaihd = "" ;
				String hdChon = "";
				
				int i=0;
	   			if(rs!= null)
	   			{
	   				while(rs.next())
	   				{
	   					hdId += rs.getString("Id") + "__";
	   					hdKhId += rs.getString("khId") + "__";
	   					hdMakh+= rs.getString("tenkh") + "__";
	   					hdNgayhd += rs.getString("ngayxuathd")+ "__";
	   					hdSohoadon += rs.getString("sohoadon")+ "__";
	   					hdKyhieu += rs.getString("kyhieu")+ "__";
	   					hdSotien += rs.getString("sotienconlai")+ "__";
	   					hdLoaihd += rs.getString("loaiHd")+ "__";
	   					System.out.println(hdLoaihd);
	   					hdChon += rs.getInt("CHON")+ "__";
	   				}
	   				rs.close();
	   				
	   				if(hdId.trim().length() > 0)
	   				{
	   					hdId = hdId.substring(0, hdId.length() - 2);
	   					this.HdId = hdId.split("__");
	   					System.out.println("HD Id: "+ hdId);
	   					
	   					hdKhId = hdKhId.substring(0, hdKhId.length() - 2);
	   					this.HdKhid = hdKhId.split("__");
	   					
	   					hdMakh = hdMakh.substring(0, hdMakh.length() - 2);
	   					this.HdMakh = hdMakh.split("__");
	   					
	   					hdNgayhd = hdNgayhd.substring(0, hdNgayhd.length() - 2);
	   					this.HdNgayhd = hdNgayhd.split("__");
	   					
	   					hdSohoadon = hdSohoadon.substring(0, hdSohoadon.length() - 2);
	   					this.HdSohd= hdSohoadon.split("__");
	   					
	   					hdKyhieu = hdKyhieu.substring(0, hdKyhieu.length() - 2);
	   					this.HdKyhieu= hdKyhieu.split("__");
	   					
	   					hdSotien = hdSotien.substring(0, hdSotien.length() - 2);
	   					this.HdSotien= hdSotien.split("__");
	   					
	   					hdLoaihd = hdLoaihd.substring(0, hdLoaihd.length() - 2);
	   					this.HdLoaiHd= hdLoaihd.split("__");
	   						   					
	   					hdChon = hdChon.substring(0, hdChon.length() - 2);
	   					this.HdChon= hdChon.split("__");
	   					
	   				}
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

	
	public String[] getKhIdss() {
		
		return this.KhIdss;
	}

	
	public String[] getHdTienCKs() {
		
		return this.HdTienCKs;
	}

	
	public void setHdTienCKs(String[] HdTienCKs) {
		
		this.HdTienCKs = HdTienCKs;
	}

	
	public void setKhIdss(String[] KhIdss) {
		
		this.KhIdss=KhIdss;
	}

	
	public String[] getHdLoaiHd() {
		
		return this.HdLoaiHd;
	}

	
	public void setHdLoaiHd(String[] HdLoaiHd) {
		
		this.HdLoaiHd = HdLoaiHd;
	}

	
	public String[] getHdLoaiHds() {
	
		return this.HdLoaiHds;
	}

	
	public void setHdLoaiHds(String[] HdLoaiHds) {
	
		this.HdLoaiHds = HdLoaiHds;
	}

	

    





		
}
