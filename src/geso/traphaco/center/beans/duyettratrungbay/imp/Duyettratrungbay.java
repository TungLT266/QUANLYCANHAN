package geso.traphaco.center.beans.duyettratrungbay.imp;

import geso.traphaco.center.beans.duyettratrungbay.IDuyettratrungbay;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

public class Duyettratrungbay implements IDuyettratrungbay, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8158813770943365236L;
	String schemeId;
	String scheme;
	int solantt;
	int lantt;
	String msg;
	ResultSet schemeRS;
	ResultSet kv;
	String kvId;
	ResultSet vung;
	String vungId;
	ResultSet npplist;
	String nppId;
	ResultSet khlist;
	String[] khIds;
	String trangthai,userId;
	dbutils db ;
	Utility Ult = new Utility();
	String daduyet;

	Hashtable<String, String> usedPro;
	public Duyettratrungbay()
	{
		this.schemeId = "";
		this.scheme = "";
		this.solantt = 1;
		this.lantt = 1;
		this.msg = "";
		this.kvId = "0";
		this.vungId = "0";
		this.npplist = null;
		this.nppId = "0";
		this.trangthai = "";
		this.daduyet = "0";
		this.db = new dbutils();
		Ult	= new Utility();
	}

	public String getSchemeId()
	{
		return this.schemeId;
	}

	public void setSchemeId(String schemeId)
	{
		this.schemeId = schemeId;
	}
		
	public int getSolantt() 
	{		
		return this.getSolanthanhtoan();
	}
	
	public void setSolantt(int solantt)
	{
		this.solantt = solantt;		
	}
	
	public String getNppId() 
	{		
		return this.nppId;
	}
	
	public void setNppId(String nppId)
	{
		this.nppId = nppId;		
	}

	public String[] getKhIds() 
	{		
		return this.khIds;
	}
	
	public void setKhIds(String[] khIds)
	{
		this.khIds = khIds;		
	}

	public int getLantt() 
	{	
		return this.lantt;
	}
	
	public void setLantt(int lantt)
	{
		this.lantt = lantt;		
	}

	public Hashtable<String, String> getusedPro()
	{
		return this.usedPro;
	}

	public String getTrangthai(){
		
		return this.trangthai;
	}

	public void setusedPro(Hashtable<String, String> usedPro)
	{
		this.usedPro = usedPro;
	}

	public String getScheme()
	{
		ResultSet rs = this.db.get("select scheme from cttrungbay where pk_seq='" + this.schemeId + "'");
		try{
			rs.next();
			this.scheme = rs.getString("scheme");
		}catch(Exception e){}
		return this.scheme;
	}

	public void setScheme(String scheme)
	{
		this.scheme = scheme;
	}

	public String getMessage()
	{
		return this.msg;
	}

	public void setMessage(String msg)
	{
		this.msg = msg;
	}

	public ResultSet getSchemeRS() 
	{
		return this.schemeRS;
	}

	public void setSchemeRS(ResultSet schemeRS) 
	{
		this.schemeRS = schemeRS;
	}

	public ResultSet getVung() 
	{
		return this.vung;
	}

	public void setVung(ResultSet vung) 
	{
		this.vung = vung;
	}

	public String getVungId() 
	{
		return this.vungId;
	}

	public void setVungId(String vungId) 
	{
		this.vungId = vungId;
	}
	
	public ResultSet getKv() 
	{
		return this.kv;
	}

	public void setKv(ResultSet khuvuc) 
	{
		this.kv = khuvuc;
	}

	public String getKvId() 
	{
		return this.kvId;
	}

	public void setKvId(String kvId) 
	{
		this.kvId = kvId;
	}

	public ResultSet getNpp() 
	{
		return this.npplist;
	}

	public void setNpp(ResultSet npplist) 
	{
		this.npplist = npplist;
	}
	
	public ResultSet getKh() 
	{
		return this.khlist;
	}

	public void setKh(ResultSet khlist) 
	{
		this.khlist = khlist;
	}

	private ResultSet createSchemeRS()
	{
		/*String sql_scheme=
				"SELECT  PK_SEQ, SCHEME, DIENGIAI FROM CTTRUNGBAY TB "+ 
				 "WHERE PK_SEQ IN( SELECT CTTRUNGBAY_FK FROM DENGHITRATRUNGBAY WHERE TRANGTHAI=0) ";*/
		
		String sql_scheme = "SELECT  PK_SEQ, SCHEME, DIENGIAI FROM CTTRUNGBAY TB "; 
		
		ResultSet schemeRS = this.db.get(sql_scheme );
		System.out.println("__scheme__"+ sql_scheme);
		return schemeRS;
	}
	
	private ResultSet createVungRS(){  	
		String sql_vung="select pk_seq, diengiai from vung  where trangthai='1'";
		ResultSet vungRS =  this.db.get(sql_vung);
		System.out.println("__vung__"+sql_vung);
		return vungRS;
	}
	
	
	private ResultSet createKvRS(){
		ResultSet kvRS;
		if (!this.vungId.equals("0")){
			kvRS =  this.db.get("select pk_seq, diengiai from khuvuc where trangthai='1' and vung_fk='" + this.vungId + "'");
		}else{
			kvRS =  this.db.get("select pk_seq, diengiai from khuvuc where trangthai='1'");
		}
		return kvRS;
		
	}

	private ResultSet createNppRS(){
		ResultSet nppRS = null;
		String sql_npp="";
		if(this.schemeId.trim().length()>0)
		{
			sql_npp=
			"select  pk_seq, ten from nhaphanphoi NPP " +
			" where NPP.pk_seq in  (SELECT  NPP_FK FROM DENGHITRATRUNGBAY WHERE CTTRUNGBAY_FK='"+this.schemeId+"' " ;
			if(this.lantt!=0)
				sql_npp+=" AND LANTHANHTOAN='"+this.lantt+"' ";
			sql_npp+=
			" ) "+
			" and NPP.trangthai='1' and NPP.pk_seq in "+Ult.quyen_npp(this.userId)+" ";
		}
		else 
			sql_npp="select pk_seq, ten from nhaphanphoi where trangthai='1' and pk_seq in "+Ult.quyen_npp(this.userId)+" ";
	
		if (!this.kvId.equals("0"))
		{
			sql_npp+=" and khuvuc_fk='" + this.kvId + "' ";
		}
		nppRS =  this.db.get(sql_npp);
		System.out.println("__npp___"+sql_npp);
		return nppRS;
	}
	
	public void init()
	{
		this.schemeRS = this.createSchemeRS();
		this.vung = this.createVungRS();
		this.kv = this.createKvRS();
		this.npplist = this.createNppRS();
		this.khlist = this.createKhRs();
		
		if (this.schemeId.length() > 0 && this.lantt > 0 )
		{
			String sql = "select isnull(max(trangthai),0) as trangthai from denghitratrungbay where cttrungbay_fk = '"+ this.schemeId +"' " ;
			if(this.lantt >= 1 )
				sql += "and lanthanhtoan='"+ this.lantt +"' " ;
			
			/*if(this.nppId.trim().length()>0)
				sql+="and NPP_FK='"+this.nppId+"'";*/
			
			ResultSet rs = db.get(sql);
			System.out.println("Trang thai cua de nghi tra tb :"+sql);
			try
			{
				if(rs != null)
				{
					if(rs.next())
					{
						if(rs.getInt("trangthai") >= 1 )
						{
							this.daduyet = "1";
							this.msg = "Chương trình trưng bày này đã được duyệt";
						}
						else
						{
							this.msg = "";
						}
						this.trangthai = rs.getString("trangthai");
					}
					else
					{
						this.trangthai = "";
					}
				}
				
			}catch(Exception e){
				
				System.out.println("Exception: " + e.toString());
			}
		}
		
	}
	
	public String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	public void getLanthanhtoan()
	{
		if(this.schemeId.length()>0)
		{
			String query  = "select max(lanthanhtoan) as num from denghitratrungbay where cttrungbay_fk='"+ this.schemeId + "' and trangthai = 1"; 
			ResultSet rs = db.get(query);
			int lanthanhtoan = 0;
			try
			{
				if(rs != null)
				{
					rs.next();
					lanthanhtoan = rs.getInt("num") + 1;
				}
			}
			catch(Exception e){}
	
			this.lantt =  lanthanhtoan;
		}
		else
		{
			this.lantt = 0;
		}
	}
	
	private int getSolanthanhtoan()
	{
		if(this.schemeId.length()>0)
		{
			String query  = "select solanthanhtoan as num from cttrungbay where pk_seq='"+ this.schemeId + "'"; 
			ResultSet rs = db.get(query);
			int solanthanhtoan = 0;
			try
			{
				if(rs != null){
					rs.next();
					solanthanhtoan = rs.getInt("num");
				}
			}catch(Exception e){}
	
			return solanthanhtoan;
		}else{
			return 0;
		}
	}

	//Loc ra nhung khach hang thuoc NPP ma User do quan ly NPP
	private ResultSet createKhRs()
	{	
		String query;
		
		if (this.schemeId.trim().length() > 0)
		{
			 query= "select distinct npp.ma nppMa,npp.ten as nppTen,a.khachhang_fk as khId,b.smartId, b.ten as khTen, a.xuatdangky, a.xuatdenghi,"+
					" isnull(a.xuatduyet,0) as xuatduyet  from denghitratb_khachhang a  "+
					" inner join khachhang b on a.khachhang_fk = b.pk_seq "+
					" inner join denghitratrungbay c on  a.denghitratb_fk=c.pk_seq "+ 
					" inner join nhaphanphoi npp on npp.pk_seq=c.npp_fk "+
					" inner join khuvuc kv on  kv.pk_seq= npp.khuvuc_fk "+
					" inner join vung v on v.pk_seq=kv.vung_fk "+
					" where c.cttrungbay_fk='"+ this.schemeId +"' and c.lanthanhtoan='"+this.getLantt()+"' and npp.pk_seq in "+Ult.quyen_npp(this.userId)+" and a.xuatdangky>0 " ;
			 String dieukien = "";
			 if(!this.vungId.equals("") && !this.vungId.toString().equals("null")&& !this.vungId.equals("0") )
			 {
				 dieukien= " and v.pk_seq=" + this.vungId;
			 }
			 if(!this.kvId.equals("")&& !this.kvId.toString().equals("null") && !this.kvId.equals("0"))
			 {
				 dieukien=dieukien+  " and kv.pk_seq=" + this.kvId;
			 }
			 if(!this.nppId.equals("")&& !this.nppId.toString().equals("null") && !this.nppId.equals("0"))
			 {
				 dieukien=dieukien+ " and npp.pk_Seq="+ this.nppId;
			 }
			 if(!dieukien.equals(""))
			 {
				 query= query + dieukien;
			 }
			System.out.println("_ds khach hang dk trung bay__"+query  );
			return db.get(query);
		}else{
			return null;
		}
		
	}
	
	public void Luutratb(HttpServletRequest request)
	{
		/*String sql = "select max(trangthai) as num from denghitratrungbay where cttrungbay_fk='"+ this.schemeId +"' " +
						"and lanthanhtoan = '" + this.lantt + "' " ;
		
		//if(this.nppId.trim().length()>0)
			//sql+="and NPP_FK='" + this.nppId + "'";
		
		System.out.println("LUU TRA TB : " + sql);
		ResultSet rs = this.db.get(sql);
		try{
			if(rs != null)
			{
				rs.next();
				if (rs.getString("num").equals("0"))
				{
					for(int i = 0; i < this.khIds.length ; i++)
					{
						String tratb = request.getParameter("duyet" + this.khIds[i]);
						//System.out.println(tratb);
						
						String dangky = request.getParameter("dangky" + this.khIds[i]);
						//System.out.println(dangky);
						
						if(Integer.parseInt(dangky) >= Integer.parseInt(tratb))
						{
							sql = "update denghitratb_khachhang set xuatduyet = '"+ tratb + " ' " +
									"where khachhang_fk='"+ this.khIds[i] +"' and denghitratb_fk in (select pk_seq from denghitratrungbay where trangthai='0' and cttrungbay_fk='"+ this.schemeId +"' and lanthanhtoan='"+ this.lantt +"')";
							System.out.println("__Duyet tra trung bay__"+sql);
							this.db.update(sql);
						}
						else
						{
							this.msg = "So xuat duyet khong the lon hon so xuat dang ky";
							System.out.println("___MSG: " + this.msg);
						}
					}
				}
				else
				{
					this.msg = "De nghi tra trung bay da duoc duyet nen khong the thay doi";
				}
			}
		}
		catch(Exception e)
		{
			System.out.println("___EXCEPTION: " + e.getMessage());
		}*/
		
		for(int i = 0; i < this.khIds.length ; i++)
		{
			String tratb = request.getParameter("duyet" + this.khIds[i]);
			String dangky = request.getParameter("dangky" + this.khIds[i]);
			
			if(Integer.parseInt(dangky) >= Integer.parseInt(tratb))
			{
				String sql = "update denghitratb_khachhang set xuatduyet = '"+ tratb + " ' " +
							 "where khachhang_fk = '" + this.khIds[i] + "' and denghitratb_fk in (select pk_seq from denghitratrungbay where trangthai='0' and cttrungbay_fk='"+ this.schemeId +"' and lanthanhtoan='"+ this.lantt +"')";
				System.out.println("__Duyet tra trung bay__"+sql);
				this.db.update(sql);
			}
			else
			{
				this.msg = "So xuat duyet khong the lon hon so xuat dang ky";
				System.out.println("___MSG: " + this.msg);
			}
		}
		
	}
	
	public boolean Chot( HttpServletRequest request)
	{
		try 
		{
			db.getConnection().setAutoCommit(false);
			for(int i = 0; i < this.khIds.length ; i++)
			{
				String tratb = request.getParameter("duyet" + this.khIds[i]);
				String dangky = request.getParameter("dangky" + this.khIds[i]);
				
				if(Integer.parseInt(dangky) >= Integer.parseInt(tratb))
				{
					String sql = "update denghitratb_khachhang set xuatduyet = '"+ tratb + " ' " +
						  "where khachhang_fk = '" + this.khIds[i] + "' and denghitratb_fk in (select pk_seq from denghitratrungbay where trangthai='0' and cttrungbay_fk='"+ this.schemeId +"' and lanthanhtoan='"+ this.lantt +"')";
					
					System.out.println("__Duyet tra trung bay__"+sql);
					
					if(this.db.updateReturnInt(sql)<=0)
					{
						this.msg = "Không thể cập nhật " + sql;
						db.getConnection().rollback();
						return false;
					}
				}
				else
				{
					this.msg = "So xuat duyet khong the lon hon so xuat dang ky";
					db.getConnection().rollback();
					return false;
				}
			}
			
			String sql = "update denghitratrungbay set trangthai = '1', NguoiSua = '" + this.userId + "', NgaySua='" + getDateTime() + "' " +
					 "where cttrungbay_fk = '" + this.schemeId + "' and lanthanhtoan = '" + this.lantt + "'" ;
			System.out.println("Cap nhat DE NGHI: " + sql);
			
			 if(!this.db.update(sql))
			{
				this.msg=" Khong the cap nhat duyet tra trung bay nay "+sql;
				this.db.getConnection().rollback();
				return false;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			this.db.update("rollback");
			System.out.println("__Exception Chot: " + e.getMessage());
		}
		
		this.msg = "Chốt duyệt trả trưng bày thành công";
		this.daduyet = "1";
		return true;
	}

	public String getUserId()
	{
		
		return this.userId;
	}
	public void setUserId(String UserId)
	{
		this.userId=UserId;
		
	}

	
	public void closeDB()
	{
			try
			{	
				if(this.schemeRS!=null)
				this.schemeRS.close();
				
				if(this.npplist!=null)
					this.npplist.close();
				
				if(this.khlist!=null)
					this.khlist.close();
				
				if(this.vung!=null)
					this.vung.close();
				
				if(this.kv!=null)
					this.kv.close();				
				
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	public String getDaduyet()
	{
		return this.daduyet;
	}

	public void setDaduyet(String daduyet) 
	{
		this.daduyet = daduyet;
	}

}
