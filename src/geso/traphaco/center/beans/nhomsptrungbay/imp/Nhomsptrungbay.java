package geso.traphaco.center.beans.nhomsptrungbay.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import geso.traphaco.center.beans.nhomsptrungbay.INhomsptrungbay;
import geso.traphaco.center.beans.nhomsptrungbay.ISanpham;
import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.db.sql.dbutils;

public class Nhomsptrungbay implements INhomsptrungbay
{
	String userId;
	String id;
	String diengiai;
	String hinhthuc; //0 nhap tong tien, 1 nhap tong luong
	String tongtien;
	String tongluong;
	String type;
	String nguoitao;
	String nguoisua;
	String ngaytao;
	String ngaysua;
	String msg;
	
	ResultSet nhomspRs;
	String nhomspId;
	
	List<ISanpham> spList;
	Hashtable<String, Integer> sp_nhomspIds;
	List<ISanpham> spSudungList; //list sanpham nguoi dung nhap
	String isThung;
	dbutils db;
	
	public Nhomsptrungbay(String[] param)
	{
		this.id = param[0];
		this.diengiai = param[1];
		this.tongtien = param[2];
		this.tongluong = param[3];
		this.type = param[4];
		this.ngaytao = param[5];
		this.nguoitao = param[6];
		this.ngaysua = param[7];
		this.nguoisua = param[8];		
		this.msg = "";
		this.hinhthuc = "";
		this.nhomspId = "";
		this.isThung = "0";
		sp_nhomspIds = new Hashtable<String, Integer>();
		spSudungList = new ArrayList<ISanpham>();
		db = new dbutils();
	}
	
	public Nhomsptrungbay(String id)
	{
		this.id = id;
		this.diengiai = "";
		this.tongtien = "";
		this.tongluong = "";
		this.type = "";
		this.ngaytao = "";
		this.nguoitao = "";
		this.ngaysua = "";
		this.nguoisua = "";
		this.msg = "";
		this.hinhthuc = "";
		this.nhomspId = "";
		this.isThung = "0";
		sp_nhomspIds = new Hashtable<String, Integer>();
		spSudungList = new ArrayList<ISanpham>();
		db = new dbutils();
	}
	
	//dung trong dangkytrungbay
	public Nhomsptrungbay(String id, String tongluong, String tongtien, String type)
	{
		this.id = id;		
		this.tongluong = tongluong;
		this.tongtien = tongtien;
		this.type = type;
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
	public void setDiengiai(String diengiai) 
	{
		this.diengiai = diengiai;
	}
	public String getDiengiai()
	{
		return diengiai;
	}
	public String getTongtien()
	{
		return this.tongtien;
	}
	public void setTongtien(String tongtien)
	{
		this.tongtien = tongtien;
	}
	public String getTongluong()
	{
		return this.tongluong;
	}
	public void setTongluong(String tongluong)
	{
		this.tongluong = tongluong;
	}
	public String getType()
	{
		return this.type;
	}
	public void setType(String type)
	{
		this.type = type;
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
	
	public List<ISanpham> getSpList() 
	{
		return this.spList;
	}

	public void setSpList(List<ISanpham> spList)
	{
		this.spList = spList;
	}
	
	private boolean checkExits(dbutils db) 
	{
		ResultSet rs = db.get("select count(*) as sodong from CTTB_NHOMSPTRUNGBAY where NHOMSPTRUNGBAY_FK = '" + this.id + "'");
		int sodong = 0;
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					sodong = rs.getInt("sodong");
					rs.close();
				}
			} catch(Exception e) { sodong = 0; }
		}
		if(sodong > 0)
			return true;
		return false;
	}
	

	public boolean CreateNsptb(String[] masp, String[] soluong) 
	{
		dbutils db = new dbutils();
		this.ngaytao = getDateTime();
		this.nguoitao = this.userId;
		
		if(this.type.equals("1"))
			this.tongluong = this.tongtien = "";
		if(this.tongluong == "")
			this.tongluong = "null";
		if(this.tongtien == "")
			this.tongtien = "null";
				
		try 
		{
			db.getConnection().setAutoCommit(false);
			ResultSet rsCheck = db.get("select COUNT(PK_SEQ) as sodong from Nhomsptrungbay where  upper(ma) = upper('" + this.diengiai + "')");
			boolean chk = true;
			if(rsCheck != null)
			{
				if(rsCheck.next())
				{
					if(rsCheck.getInt("sodong") > 0)
					{
						chk = false;
					}
					rsCheck.close();
				}
			}
			if(!chk)
			{
				this.msg = "Diễn giải (" + this.diengiai + ") bạn muốn cập nhật đã tồn tại trong hệ thống, vui lòng nhập mã khác.";
				return false;
			}
			String query = "Insert into Nhomsptrungbay(diengiai, tongluong, tongtien, loai, ngaytao, nguoitao, ngaysua, nguoisua,isthung) values(";
			query = query + "N'" + this.diengiai + "', " + this.tongluong + ", " + this.tongtien + ", '" + this.type + "', '" + this.ngaytao + "', '" + this.nguoitao + "', '" + this.ngaytao + "', '" + this.nguoitao + "','"+this.isThung+"')";
			
			if(!db.update(query))
			{
				db.update("rollback");
				this.msg = "Khong the tao moi 'Nhomsptrungbay', " + query;
				return false; 
			}
			
			if(masp.length > 0)
			{
				//lay dkkm current
				String nsptbCurrent = "";
				query = "select IDENT_CURRENT('Nhomsptrungbay') as nsptbId";
				
				ResultSet rsNsptb = this.db.get(query);						
				rsNsptb.next();
				nsptbCurrent = rsNsptb.getString("nsptbId");
				rsNsptb.close();
				
				for(int i = 0; i < masp.length; i++)
				{
					if(masp[i].length() > 0)
					{
						ResultSet rs = db.get("select pk_seq from sanpham where ma='" + masp[i].trim() + "'");
						String pk_seq = "";
						if(rs != null) 
						{
							try
							{
								rs.next();
								pk_seq = rs.getString("pk_seq");
								rs.close();
							}
							catch(Exception e) {}
						} 
						
						//if(this.type.equals("2"))
							//soluong[i] = "null";
						
						if(soluong[i].trim().length() <= 0)
							soluong[i] = "0";
						
						if(pk_seq != "")
							query = "Insert into nhomsptrungbay_sanpham(nhomsptrungbay_fk, sanpham_fk, soluong, sotien) values('" + nsptbCurrent + "', '" + pk_seq + "', " + soluong[i].trim() + ", null)";
						
						if(!db.update(query))
						{
							db.update("rollback");
							this.msg = "Loi khi them moi bang nhomsptrungbay_sanpham, " + query;
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
			return false;
		}
		return true;
	}

	public boolean UpdateNsptb(String[] masp, String[] soluong) 
	{
		dbutils db = new dbutils();
		this.ngaysua = getDateTime();
		this.nguoisua = this.userId;
		
		if(this.type.equals("1"))
			this.tongluong = this.tongtien = "";
		if(this.tongluong == "")
			this.tongluong = "null";
		if(this.tongtien == "")
			this.tongtien = "null";
				
		try 
		{
			if(checkExits(db))
			{
				this.msg = "Điều kiện trưng bày đã được sử dụng trong CTTB. Bạn không thể sửa lại.";
				return false;
			}
			
			ResultSet rsCheck = db.get("select COUNT(PK_SEQ) as sodong from Nhomsptrungbay where pk_seq != '" + this.id + "' and upper(ma) = upper('" + this.diengiai + "')");
			boolean chk = true;
			if(rsCheck != null)
			{
				if(rsCheck.next())
				{
					if(rsCheck.getInt("sodong") > 0)
					{
						chk = false;
					}
					rsCheck.close();
				}
			}
			if(!chk)
			{
				this.msg = "Diễn giải (" + this.diengiai + ") bạn muốn cập nhật đã tồn tại trong hệ thống, vui lòng nhập mã khác.";
				return false;
			}
			db.getConnection().setAutoCommit(false);
				
			
			
			String query = "Update Nhomsptrungbay set diengiai = N'" + this.diengiai + "', tongluong = " + this.tongluong + ", tongtien = " + this.tongtien + ", loai = '" + this.type + "', ngaysua = '" + this.ngaysua + "', nguoisua = '" + this.nguoisua + "',isthung='"+this.isThung+"' where pk_seq='" + this.id + "'";
		
			if(!db.update(query))
			{
				db.update("rollback");
				this.msg = "Khong the cap nhat 'Nhomsptrungbay', " + query;
				return false; 
			}
			
			if(masp.length > 0)
			{
				query = "delete from nhomsptrungbay_sanpham where nhomsptrungbay_fk = '" + this.id + "'";
				db.update(query);
				
				for(int i = 0; i < masp.length; i++)
				{
					if(masp[i].length() > 0)
					{
						ResultSet rs = db.get("select pk_seq from sanpham where ma='" + masp[i].trim() + "'");
						String pk_seq = "";
						if(rs != null) 
						{
							try
							{
								rs.next();
								pk_seq = rs.getString("pk_seq");
								rs.close();
							}
							catch(Exception e) {}
						} 
						/*if(this.type.equals("2"))
							soluong[i] = "null";*/
						
						if(soluong[i].trim().length() <= 0)
							soluong[i] = "0";
						
						if(pk_seq != "")
							query = "Insert into nhomsptrungbay_sanpham(nhomsptrungbay_fk, sanpham_fk, soluong, sotien) values('" + this.id + "', '" + pk_seq + "', " + soluong[i].trim() + ", null)";
						if(!db.update(query))
						{
							db.update("rollback");
							this.msg = "Loi khi cap nhat bang nhomsptrungbay_sanpham, " + query;
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
			return false;
		}
		return true;
	}

	public void createRS() 
	{Utility Ult = new Utility();
		this.nhomspRs = db.get("select pk_seq as nspId, diengiai as nspTen from nhomsanpham where type = '1' and trangthai='1' and pk_seq in (select nsp_fk from NHOMSANPHAM_SANPHAM where sp_fk in"+ Ult.quyen_sanpham(this.userId)+")");		
		if(this.nhomspId.length() > 0)
			this.createSpList();
	}
	
	private void createSpList()
	{
		String query = "select b.pk_seq, b.MA, b.TEN from NHOMSANPHAM_SANPHAM a inner join SANPHAM b on a.SP_FK = b.PK_SEQ where a.NSP_FK = '" + this.nhomspId + "'";	
		ResultSet rs = db.get(query);
		List<ISanpham> splist = new ArrayList<ISanpham>();
		if(rs != null)
		{
			try 
			{
				ISanpham sp = null;
				while(rs.next())
				{
					String spId = rs.getString("pk_seq");
					String ma = rs.getString("MA");
					String ten = rs.getString("TEN");
					sp = new Sanpham(spId, ma, ten, "", "");
					splist.add(sp);
				}
			} 
			catch(Exception e) {}
		}
		this.spList = splist;
	}
	
	public void createNsptbSpList()
	{		
		String query = "select a.soluong, b.pk_seq, b.MA, b.TEN from nhomsptrungbay_sanpham a inner join SANPHAM b on a.SanPham_FK = b.PK_SEQ where a.nhomsptrungbay_fk = '" + this.id + "'";				
		ResultSet rs = db.get(query);
		List<ISanpham> splist = new ArrayList<ISanpham>();
		if(rs != null)
		{
			try 
			{
				ISanpham sp = null;
				while(rs.next())
				{
					String spId = rs.getString("pk_seq");
					String ma = rs.getString("MA");
					String ten = rs.getString("TEN");
					String soluong = "";
					if(rs.getString("soluong") != null)
						soluong = rs.getString("soluong");
					sp = new Sanpham(spId, ma, ten, soluong, "");
					splist.add(sp);
				}
			} 
			catch(Exception e) {}
		}
		this.spSudungList = splist;
	}

	public void init() 
	{
		String query = "select a.PK_SEQ as nsptbId, a.DIENGIAI, tongluong, tongtien, a.LOAI, a.NGAYTAO, a.NGAYSUA, b.TEN as nguoitao, c.TEN as nguoisua,isnull(a.isthung,0) as isthung ";
		query = query + " from Nhomsptrungbay a inner join NHANVIEN b on a.NGUOITAO = b.PK_SEQ inner join NHANVIEN c on a.NGUOISUA = c.PK_SEQ where a.PK_SEQ='" + this.id + "'";
		ResultSet rs = db.get(query);
		try
        {
            rs.next();        	
            this.id = rs.getString("nsptbId");
			this.diengiai = rs.getString("diengiai");
			if(rs.getString("tongluong") != null)
				this.tongluong = rs.getString("tongluong");
			if(rs.getString("tongtien") != null)
				this.tongtien = rs.getString("tongtien");
			
			this.type = rs.getString("loai");
			this.ngaytao = rs.getString("ngaytao");
			this.nguoitao = rs.getString("nguoitao");
			this.ngaysua = rs.getString("ngaysua");
			this.nguoisua = rs.getString("nguoisua");	
			this.isThung = rs.getString("isThung");
			rs.close();
       	}
        catch(Exception e){}
        
        if(this.tongtien.length() > 0)
        	this.hinhthuc = "2";
        else
        	this.hinhthuc = "1";
        
        Utility Ult = new Utility();
		this.nhomspRs = db.get("select pk_seq as nspId, diengiai as nspTen from nhomsanpham where type = '1' and trangthai='1' and pk_seq in (select nsp_fk from NHOMSANPHAM_SANPHAM where sp_fk in"+ Ult.quyen_sanpham(this.userId)+")");		
	
       // this.nhomspRs = db.get("select pk_seq as nspId, diengiai as nspTen from nhomsanpham where type = '1' and trangthai='1' ");	
        this.createNsptbSpList();
	}
	
	public ResultSet getNhomspRs()
	{
		return this.nhomspRs;
	}

	public void setNhomspRs(ResultSet nhomspRs) 
	{
		this.nhomspRs = nhomspRs;
	}

	public String getNhomspId()
	{
		return this.nhomspId;
	}

	public void setNhomspId(String nhomspId) 
	{
		this.nhomspId = nhomspId;
	}

	public String getHinhthuc() 
	{
		return this.hinhthuc;
	}

	public void setHinhthuc(String hinhthuc) 
	{
		this.hinhthuc = hinhthuc;
	}

	public Hashtable<String, Integer> getSp_nhomspIds()
	{
		return this.sp_nhomspIds;
	}

	public void setSp_nhomspIds(Hashtable<String, Integer> sp_nhomspIds)
	{
		this.sp_nhomspIds = sp_nhomspIds;
	}

	public List<ISanpham> getSpSudungList() 
	{
		return this.spSudungList;
	}

	public void setSpSudungList(List<ISanpham> splist) 
	{
		this.spSudungList = splist;
	}
	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	public String getIsThung() 
	{
		return this.isThung;
	}

	public void setIsThung(String isThung) 
	{
		this.isThung = isThung;
	}
	
	
}

