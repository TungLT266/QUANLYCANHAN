package geso.traphaco.center.beans.cttrungbay.imp;

import geso.traphaco.center.beans.cttrungbay.*;
import geso.traphaco.center.beans.cttrungbay.imp.Nhomsptrungbay;
import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.db.sql.dbutils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

public class Cttrungbay implements ICttrungbay
{
	String userId;
	String id;
	String scheme;
	String diengiai;
	
	String ngaytds;
	String ngaykttds;
	String ngaytb;
	String ngaykttb;
	String ngaydk;
	String ngayktdk;
	
	String type;
	String ngansach;
	String dasudung;
	String solantt;
	
	String nguoitao;
	String nguoisua;
	String ngaytao;
	String ngaysua;
	String msg;
	
	
	List<INhomsptrungbay> nsptbList = new ArrayList<INhomsptrungbay>();
	ResultSet tratbRs;
	String[] tratbIds;
	String[] pheptoans;
	
	//Search tratrungbay
	String ttb_diengiai;
	String ttb_tungay;
	String ttb_denngay;
	
	//Search nhomsptrungbay
	String nsptb_diengiai;
	String nsptb_tungay;
	String nsptb_denngay;
	
	//THUONG SALE
	String[] diengiaiMuc;
	String[] tumuc;
	String[] denmuc;
	String[] thuongSR;
	String[] thuongTDSR;
	String[] thuongSS;
	String[] thuongTDSS;
	String[] thuongASM;
	String[] thuongTDASM;
	
	String thuongDhdautien;
	String donviThuong;
	
	String isDkthem;
	String isTinhds;
	
	ResultSet kbhRs;
	ResultSet VungRs;
	ResultSet KhuvucRs;
	ResultSet NppRs;
	
	ResultSet vitriKhRs;
	String vitriKhIds;
	ResultSet hangKhRs;
	String hangKhIds;
	
	String kbhIds;
	String vungIds;
	String kvIds;
	String nppIds;
	String active;
	
	dbutils db;
	
	String nhomTbId;
	
	String[] nppId;

	ResultSet nhomTbRs;
	
	public Cttrungbay(String[] param)
	{
		this.id = param[0];
		this.scheme = param[1];
		this.diengiai = param[2];
		
		this.ngaytds = param[3];
		this.ngaykttds = param[4];
		this.ngaytb = param[5];
		this.ngaykttb = param[6];
		this.ngaydk = "";
		this.ngayktdk = "";
		
		this.type = param[7];
		this.ngansach = param[8];
		this.dasudung = param[9];
		this.solantt = param[10];
		
		this.ngaytao = param[11];
		this.nguoitao = param[12];
		this.ngaysua = param[13];
		this.nguoisua = param[14];		
		this.msg = "";
			
		//this.tratbId = "";
		this.ttb_diengiai = "";
		this.ttb_tungay = "";
		this.ttb_denngay = "";
		this.nsptb_diengiai = "";
		this.nsptb_tungay = "";
		this.nsptb_denngay = "";
		
		this.kbhIds = "";
		this.vungIds = "";
		this.kvIds = "";
		this.nppIds = "";
		this.active = "0";
		
		this.thuongDhdautien = "";
		this.donviThuong = "";
		
		this.vitriKhIds = "";
		this.hangKhIds = "";
		
		this.isDkthem = "0";
		this.isTinhds = "1";
		this.nhomTbId="";
		db = new dbutils();
	}
	
	public Cttrungbay(String id)
	{
		this.id = id;
		this.scheme = "";
		this.diengiai = "";
		this.ngaytds = "";
		this.ngaykttds = "";
		this.ngaytb = "";
		this.ngaykttb = "";
		this.type = "";
		this.ngansach = "";
		this.dasudung = "";
		this.solantt = "";
		this.ngaytao = "";
		this.nguoitao = "";
		this.ngaysua = "";
		this.nguoisua = "";
		this.msg = "";
		
		this.ngaydk = "";
		this.ngayktdk = "";
		
		this.kbhIds = "";
		this.vungIds = "";
		this.kvIds = "";
		this.nppIds = "";
		
		//this.tratbId = "";
		this.ttb_diengiai = "";
		this.ttb_tungay = "";
		this.ttb_denngay = "";
		this.nsptb_diengiai = "";
		this.nsptb_tungay = "";
		this.nsptb_denngay = "";
		this.isDkthem = "0";
		this.isTinhds = "1";
		this.active = "0";
		
		this.vitriKhIds = "";
		this.hangKhIds = "";
		
		this.thuongDhdautien = "";
		this.donviThuong = "";
		this.nhomTbId="";
		db = new dbutils();
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
	
	public String getScheme()
	{
		return this.scheme;
	}
	
	public void setScheme(String scheme)
	{
		this.scheme = scheme;
	}
	
	public void setDiengiai(String diengiai) 
	{
		this.diengiai = diengiai;
	}
	
	public String getDiengiai()
	{
		return diengiai;
	}
	
	public String getNgayTds()
	{
		return this.ngaytds;
	}
	
	public void setNgayTds(String ngaytds)
	{
		this.ngaytds = ngaytds;
	}
	
	public String getNgayktTds()
	{
		return this.ngaykttds;
	}
	
	public void setNgayktTds(String ngaykttds)
	{
		this.ngaykttds = ngaykttds;
	}
	
	public String getNgayTb()
	{
		return this.ngaytb;
	}
	
	public void setNgayTb(String ngaytb)
	{
		this.ngaytb = ngaytb;
	}
	
	public String getNgayktTb()
	{
		return this.ngaykttb;
	}
	
	public void setNgayktTb(String ngayktttb)
	{
		this.ngaykttb = ngayktttb;
	}
	
	public String getType()
	{
		return this.type;
	}
	
	public void setType(String type)
	{
		this.type = type;
	}
	
	public String getNgansach()
	{
		return this.ngansach;
	}
	
	public void setNgansach(String ngansach)
	{
		this.ngansach = ngansach;
	}
	
	public String getDasudung()
	{
		return this.dasudung;
	}
	
	public void setDasudung(String dasudung)
	{
		this.dasudung = dasudung;
	}
	
	public List<INhomsptrungbay> getNsptbList() 
	{		
		return this.nsptbList;
	}
	
	public void setNsptbList(List<INhomsptrungbay> nsptblist) 
	{
		this.nsptbList = nsptblist;
	}

	public ResultSet getTratbRs()
	{		
		return this.tratbRs;
	}
	
	public void setTratbRs(ResultSet tratbRs) 
	{
		this.tratbRs = tratbRs;	
	}
	
	public Hashtable<String, Integer> getTratbId()
	{	
		Hashtable<String, Integer> select = new Hashtable<String, Integer>();
		if(this.tratbIds != null){
			int size = (this.tratbIds).length;
			int m = 0;
			while(m < size){
				select.put(this.tratbIds[m], Integer.parseInt(this.pheptoans[m]));
				
				//System.out.println("Tra tb o buoc nay: " + this.tratbIds[m] + " --- Phep toan tuong ung la: " + this.pheptoans[m] + "\n");
				m++;
			}
		}else{
			select.put("null", new Integer(0));
		}
		return select;
	}
	
	public void setTratbId(String[] tratbIds, String[] pheptoans) 
	{
		this.tratbIds = tratbIds;
		this.pheptoans = pheptoans;
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
		
	public boolean CreateCttb() 
	{
		
		this.ngaytao = getDateTime();
		this.nguoitao = this.userId;
		
		try 
		{
			db.getConnection().setAutoCommit(false);
				
			if(this.nhomTbId.length()<=0)
				this.nhomTbId="NULL";
			
			String thuongDHDT = "null";
			if(this.thuongDhdautien.trim().length() > 0)
				thuongDHDT = this.thuongDhdautien.trim();
			
			if(!CheckUnique())
			{
				this.msg="Scheme  "+this.scheme  +" đã được sử dụng ";
				return false;
			}			
			
			String query = "Insert into Cttrungbay(scheme, diengiai, ngayTds, ngayktTds, ngaytrungbay, ngayketthuctb, NgayDangKy, NgayKetThucDK, type, ngansach, solanthanhtoan, ngaytao, nguoitao, ngaysua, nguoisua, isdkthem, istinhds, thuongDhDautien, donviThuong,NHOMCTTB_FK ) " +
						   "values('" + this.scheme + "', N'" + this.diengiai + "', '" + convertDate(this.ngaytds) + "' , '" + convertDate(this.ngaykttds) + "' , '" + convertDate(this.ngaytb) + "', '" + convertDate(this.ngaykttb) + "', '" + this.ngaydk + "', '" + this.ngayktdk + "', " +
						   		"'" + this.type + "', '" + this.ngansach + "', '" + this.solantt + "', '" + this.ngaytao + "', '" + this.nguoitao + "', '" + this.ngaytao + "', '" + this.nguoitao + "', '" + this.isDkthem + "', '" + this.isTinhds + "', " + thuongDHDT + ", '" + this.donviThuong + "',"+this.nhomTbId+" )";
			System.out.println("tao CTTB : "+query);
			if(!db.update(query))
			{
				db.update("rollback");
				this.msg = "Khong the tao moi 'Cttrungbay', " + query;
				return false; 
			}
						
			//lay nsptbID current
			String cttbCurrent = "";
			query = "select IDENT_CURRENT('Cttrungbay') as cttbId";
			
			ResultSet rsCttb = this.db.get(query);						
			rsCttb.next();
			cttbCurrent = rsCttb.getString("cttbId");
			rsCttb.close();
			
			String dktbIds = "";
			for(int i = 0; i < this.nsptbList.size(); i++)
			{
				Nhomsptrungbay nsptb = (Nhomsptrungbay)this.nsptbList.get(i);	
				
				dktbIds += nsptb.getId() + ",";
				
				query = "Insert into CTTB_NHOMSPTRUNGBAY(cttrungbay_fk, nhomsptrungbay_fk, pheptoan, thutudieukien) values('" + cttbCurrent + "', '" + nsptb.getId() + "', '" + nsptb.getPheptoan() + "', '" + Integer.toString(i + 1) + "')";
				if(!db.update(query))
				{
					db.update("rollback");
					this.msg = "Khong the tao moi 'CTTB_NHOMSPTRUNGBAY', " + query;
					return false; 
				}
			}
			
			//Check trong 1 CTTB, neu nhieu DK thi cac DK khong duoc phep trung nhau
			if(dktbIds.trim().length() > 0)
			{
				dktbIds = dktbIds.substring(0, dktbIds.length() - 1);
				
				query = "select SANPHAM_FK, COUNT(*) as sodong  " +
						"from NHOMSPTRUNGBAY_SANPHAM where NHOMSPTRUNGBAY_FK in ( " + dktbIds + " ) " +
						"group by SANPHAM_FK " +
						"having COUNT(*) >= 2";
				
				ResultSet rsCheckSP = db.get(query);
				int coSPtrung = 0;
				
				if(rsCheckSP.next())
				{
					if(rsCheckSP.getInt("sodong") >= 2)
					{
						coSPtrung = 1;
					}
				}
				rsCheckSP.close();
				
				if(coSPtrung >= 1)
				{
					db.getConnection().rollback();
					this.msg = "Sản phẩm trong các điều kiện của CTTB không được phép trùng nhau";
					return false;
				}
				
			}
			
			for(int i = 0; i < this.tratbIds.length; i++ )
			{
				query = "Insert into CTTB_TRATB(cttrungbay_fk, tratrungbay_fk, pheptoan, thutudieukien) values('" + cttbCurrent + "', '" + this.tratbIds[i] + "', '" +  this.pheptoans[i] + "', '" + Integer.toString(i + 1) + "')";
				System.out.println("Query chen du lieu la: " + query + "\n");
				if(!db.update(query))
				{
					db.update("rollback");
					this.msg = "Khong the tao moi 'CTTB_TRATB', " + query;
					return false; 
				}
			}

			if(this.nppIds.length() > 0)
			{	
				String[] listNpp = this.nppIds.split(",");
				for(int i = 0; i < listNpp.length; i++)
				{
					query = "Insert into CTTB_NPP(cttb_fk, Npp_fk) values('" + cttbCurrent + "', '" + listNpp[i] + "')";
					if(!db.update(query))
					{
						db.update("rollback");
						this.msg = "Khong the tao moi 'CTTB_NPP', " + query;
						return false; 
					}
				}
			}
			
			//THUONG SALE
			if(this.diengiaiMuc != null)
			{
				String sql = "";
				for(int i = 0; i < this.diengiaiMuc.length; i++)
				{
					//System.out.println("___THUONG SR: " + this.thuongSR[i] + " -- Thuong SS: " + this.thuongSS[i]);
					if(this.diengiaiMuc[i].trim().length() > 0 && this.tumuc[i].trim().length() > 0 && this.denmuc[i].trim().length() > 0 && this.thuongSR[i].trim().length() > 0)
					{
						String thuongTD_SR = "0";
						if(this.thuongTDSR[i].trim().length() > 0)
							thuongTD_SR = this.thuongTDSR[i].trim().replaceAll(",", "");
						
						String thuong_SS = "0";
						if(this.thuongSS[i].trim().length() > 0)
							thuong_SS = this.thuongSS[i].trim().replaceAll(",", "");
						
						String thuongTD_SS = "0";
						if(this.thuongTDSS[i].trim().length() > 0)
							thuongTD_SS = this.thuongTDSS[i].trim().replaceAll(",", "");
						
						String thuong_ASM = "0";
						if(this.thuongASM[i].trim().length() > 0)
							thuong_ASM = this.thuongASM[i].trim().replaceAll(",", "");
						
						String thuongTD_ASM = "0";
						if(this.thuongTDASM[i].trim().length() > 0)
							thuongTD_ASM = this.thuongTDASM[i].trim().replaceAll(",", "");
						
						sql += "select N'" + this.diengiaiMuc[i].replaceAll(",", "") + "' as diengiaiMuc, '" + this.tumuc[i].replaceAll(",", "") + "' as tumuc, '" + this.denmuc[i].replaceAll(",", "") + "' as denmuc,  " +
								"'" + this.thuongSR[i].replaceAll(",", "") + "' as thuongSR, '" + thuongTD_SR + "' as thuongTDSR, '" + thuong_SS + "' as thuongSS, '" + thuongTD_SS + "' as thuongTDSS, '" + thuong_ASM + "' as ASM, '" + thuongTD_ASM + "' as thuongTDASM union ";
						
					}
				}
				
				if(sql.trim().length() > 0)
				{
					sql = sql.substring(0, sql.length() - 6);
					
					query = "insert TieuchithuongTB_TIEUCHI(cttb_fk, tieuchi, tumuc, denmuc, thuongSR, thuongTDSR, thuongSS, thuongTDSS, thuongASM, thuongTDASM) " +
							"select '" + cttbCurrent + "' as tctId, tieuchi.* from (" + sql + ") tieuchi ";
					
					System.out.println("2.Insert: " + query);
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi TieuchithuongTB_TIEUCHI: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
				
			}
			
			if(this.hangKhIds.trim().length() > 0)
			{
				query = "Insert CTTB_HANGKH( CTTB_FK, HANGKH_FK ) " +
						"select '" + cttbCurrent + "', pk_seq from HANGCUAHANG where pk_seq > 0 ";
//				if(this.loaiKhIds.trim().length() > 0)
//					query += " and pk_seq in ( " + this.hangKhIds + " ) ";
				
				if(!db.update(query))
	    		{
	    			db.getConnection().rollback();
	    			this.setMessage("6.Khong The tao moi CTTB_HANGKH loi :"+ query);
	    			return false;
	    		}
			}
			
			if(this.vitriKhIds.trim().length() > 0)
			{
				query = "Insert CTTB_VITRIKH( CTTB_FK, VITRIKH_FK ) " +
						"select '" + cttbCurrent + "', pk_seq from VITRICUAHANG where pk_seq > 0 ";
				if(this.vitriKhIds.trim().length() > 0)
					query += " and pk_seq in ( " + this.vitriKhIds + " ) ";
				
				if(!db.update(query))
	    		{
	    			db.getConnection().rollback();
	    			this.setMessage("6.Khong The tao moi CTTB_VITRIKH loi :"+ query);
	    			return false;
	    		}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
		} 
		catch(Exception e) 
		{
			this.db.update("rollback");
			this.msg="Khong The Thuc Hien Luu Chuong trinh trung bay Nay,Vui Long Lien He Voi Admin De Sua Loi Nay.Loi :" +e.toString();
			
			return false;
		}
		
		return true;
	}
	
	
	public boolean CheckUnique()
	{
		String query="select COUNT(*) as sodong from CTTRUNGBAY where SCHEME='"+this.scheme+"' ";
		if(this.id.length()>0)
			query+=" and pk_seq !="+this.id+" ";
		
		ResultSet rs=this.db.get(query);
		try
		{
			int sodong=0;
			while(rs.next())
			{
				sodong=rs.getInt("sodong");
			}
			if(sodong!=0)
			{
				return false;
			}
				
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
			
		return true;
	}
	
	
	public boolean UpdateCttb() 
	{
		
		this.ngaysua = getDateTime();
		this.nguoisua = this.userId;
		
		try 
		{
			if(!CheckUnique())
			{
				this.msg="Scheme  "+this.scheme  +" đã được sử dụng ";
				return false;
			}
			if(this.nhomTbId.length()<=0)
				this.nhomTbId="NULL";
			
			String thuongDHDT = "null";
			if(this.thuongDhdautien.trim().length() > 0)
				thuongDHDT = this.thuongDhdautien.trim();
			
			String query=
				"	select COUNT(*) as SoDong "+
				"	from DENGHITRATRUNGBAY "+
				"	where CTTRUNGBAY_FK='"+this.id+"' ";
			ResultSet rs= db.get(query);
			
			while(rs.next())
			{
				if(rs.getInt("SoDong")>0)
				{
					this.msg ="Chương trình trưng bày đã làm đề nghị trả!Bạn không thể cập nhật lại thông tin CTTB này !";
					return false;
				}
			}
			db.getConnection().setAutoCommit(false);
			query = "Update Cttrungbay set scheme = '" + this.scheme + "', diengiai = N'" + this.diengiai + "', ngaytds = '" + convertDate(this.ngaytds) + "', ngaykttds = '" + convertDate(this.ngaykttds) + "'," +
						   " ngaytrungbay = '" + convertDate(this.ngaytb) + "', ngayketthuctb = '" + convertDate(this.ngaykttb) + "', NgayDangKy = '" + this.ngaydk + "', NgayKetThucDK = '" + ngayktdk + "', solanthanhtoan = '" + this.solantt + "',  type = '" + this.type + "', ngansach = '" + this.ngansach + "', ngaysua = '" + this.ngaysua + "', nguoisua = '" + this.nguoisua + "', isdkThem = '" + this.isDkthem + "', " +
						   		"istinhds = '" + this.isTinhds + "', thuongDhDautien = " + thuongDHDT + " , donviThuong = '" + this.donviThuong + "',NHOMCTTB_FK="+this.nhomTbId+" " +
						   " where pk_seq = '" + this.id + "'";
			
			System.out.println("Query cap nhat la: " + query + "\n");
			
			if(!db.update(query))
			{
				db.update("rollback");
				this.msg = "Khong the cap nhat 'Cttrungbay', " + query;
				return false; 
			}
						
			query = "delete from CTTB_NHOMSPTRUNGBAY where cttrungbay_fk = '" + this.id + "'";
			db.update(query);
			
			String dktbIds = "";
			for(int i = 0; i < this.nsptbList.size(); i++)
			{
				Nhomsptrungbay nsptb = (Nhomsptrungbay)this.nsptbList.get(i);	
				
				dktbIds += nsptb.getId() + ",";
				
				query = "Insert into CTTB_NHOMSPTRUNGBAY(cttrungbay_fk, nhomsptrungbay_fk, pheptoan, thutudieukien) values('" + this.id + "', '" + nsptb.getId() + "', '" + nsptb.getPheptoan() + "', '" + Integer.toString(i + 1) + "')";
				if(!db.update(query))
				{
					db.update("rollback");
					this.msg = "Khong the tao moi 'CTTB_NHOMSPTRUNGBAY', " + query;
					return false; 
				}
			}
			
			//Check trong 1 CTTB, neu nhieu DK thi cac DK khong duoc phep trung nhau
			if(dktbIds.trim().length() > 0)
			{
				dktbIds = dktbIds.substring(0, dktbIds.length() - 1);
				
				query = "select SANPHAM_FK, COUNT(*) as sodong  " +
						"from NHOMSPTRUNGBAY_SANPHAM where NHOMSPTRUNGBAY_FK in ( " + dktbIds + " ) " +
						"group by SANPHAM_FK " +
						"having COUNT(*) >= 2";
				
				ResultSet rsCheckSP = db.get(query);
				int coSPtrung = 0;
				
				if(rsCheckSP.next())
				{
					if(rsCheckSP.getInt("sodong") >= 2)
					{
						coSPtrung = 1;
					}
				}
				rsCheckSP.close();
				
				if(coSPtrung >= 1)
				{
					db.getConnection().rollback();
					this.msg = "Sản phẩm trong các điều kiện của CTTB không được phép trùng nhau";
					return false;
				}
				
			}
			
			query = "delete from CTTB_TRATB where cttrungbay_fk = '" + this.id + "'";
			db.update(query);
			for(int i = 0; i < this.tratbIds.length; i++ )
			{
				query = "Insert into CTTB_TRATB(cttrungbay_fk, tratrungbay_fk, pheptoan, thutudieukien) values('" + this.id + "', '" + this.tratbIds[i] + "', '" +  this.pheptoans[i] + "', '" + Integer.toString(i + 1) + "')";
				if(!db.update(query))
				{
					db.update("rollback");
					this.msg = "Khong the tao moi 'CTTB_TRATB', " + query;
					return false; 
				}
			}
			
			if(this.nppIds.length() > 0)
			{	
				db.update("delete from cttb_npp where cttb_fk = '" + this.id + "'");
				String[] listNpp = this.nppIds.split(",");
				for(int i = 0; i < listNpp.length; i++)
				{
					query = "Insert into CTTB_NPP(cttb_fk, npp_fk) values('" + this.id + "', '" + listNpp[i] + "')";
					if(!db.update(query))
					{
						db.update("rollback");
						this.msg = "Khong the tao moi 'CTTB_NPP', " + query;
						return false; 
					}
				}
			}
			
			//THUONG SALE
			query = "delete TieuchithuongTB_TIEUCHI where cttb_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				db.update("rollback");
				this.msg = "Khong the tao moi 'TieuchithuongTB_TIEUCHI', " + query;
				return false; 
			}
			
			if(this.diengiaiMuc != null)
			{
				String sql = "";
				for(int i = 0; i < this.diengiaiMuc.length; i++)
				{
					//System.out.println("___THUONG SR: " + this.thuongSR[i] + " -- Thuong SS: " + this.thuongSS[i]);
					if(this.diengiaiMuc[i].trim().length() > 0 && this.tumuc[i].trim().length() > 0 && this.denmuc[i].trim().length() > 0 && this.thuongSR[i].trim().length() > 0)
					{
						String thuongTD_SR = "0";
						if(this.thuongTDSR[i].trim().length() > 0)
							thuongTD_SR = this.thuongTDSR[i].trim().replaceAll(",", "");
						
						String thuong_SS = "0";
						if(this.thuongSS[i].trim().length() > 0)
							thuong_SS = this.thuongSS[i].trim().replaceAll(",", "");
						
						String thuongTD_SS = "0";
						if(this.thuongTDSS[i].trim().length() > 0)
							thuongTD_SS = this.thuongTDSS[i].trim().replaceAll(",", "");
						
						String thuong_ASM = "0";
						if(this.thuongASM[i].trim().length() > 0)
							thuong_ASM = this.thuongASM[i].trim().replaceAll(",", "");
						
						String thuongTD_ASM = "0";
						if(this.thuongTDASM[i].trim().length() > 0)
							thuongTD_ASM = this.thuongTDASM[i].trim().replaceAll(",", "");
						
						sql += "select N'" + this.diengiaiMuc[i].replaceAll(",", "") + "' as diengiaiMuc, '" + this.tumuc[i].replaceAll(",", "") + "' as tumuc, '" + this.denmuc[i].replaceAll(",", "") + "' as denmuc,  " +
								"'" + this.thuongSR[i].replaceAll(",", "") + "' as thuongSR, '" + thuongTD_SR + "' as thuongTDSR, '" + thuong_SS + "' as thuongSS, '" + thuongTD_SS + "' as thuongTDSS, '" + thuong_ASM + "' as ASM, '" + thuongTD_ASM + "' as thuongTDASM union ";
						
					}
				}
				
				if(sql.trim().length() > 0)
				{
					sql = sql.substring(0, sql.length() - 6);
					
					query = "insert TieuchithuongTB_TIEUCHI(cttb_fk, tieuchi, tumuc, denmuc, thuongSR, thuongTDSR, thuongSS, thuongTDSS, thuongASM, thuongTDASM) " +
							"select '" + this.id + "' as tctId, tieuchi.* from (" + sql + ") tieuchi ";
					
					System.out.println("2.Insert: " + query);
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi TieuchithuongTB_TIEUCHI: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
			}
			
			query = "delete from CTTB_HANGKH where cttb_fk = '" + this.id + "'";
			db.update(query);
			
			query = "delete from CTTB_VITRIKH where cttb_fk = '" + this.id + "'";
			db.update(query);
			
			if(this.hangKhIds.trim().length() > 0)
			{
				query = "Insert CTTB_HANGKH( CTTB_FK, HANGKH_FK ) " +
						"select '" + this.id + "', pk_seq from HANGCUAHANG where pk_seq > 0 ";
//				if(this.loaiKhIds.trim().length() > 0)
//					query += " and pk_seq in ( " + this.hangKhIds + " ) ";
				
				if(!db.update(query))
	    		{
	    			db.getConnection().rollback();
	    			this.setMessage("6.Khong The tao moi CTTB_HANGKH loi :"+ query);
	    			return false;
	    		}
			}
			
			if(this.vitriKhIds.trim().length() > 0)
			{
				query = "Insert CTTB_VITRIKH( CTTB_FK, VITRIKH_FK ) " +
						"select '" + this.id + "', pk_seq from VITRICUAHANG where pk_seq > 0 ";
				if(this.vitriKhIds.trim().length() > 0)
					query += " and pk_seq in ( " + this.vitriKhIds + " ) ";
				
				if(!db.update(query))
	    		{
	    			db.getConnection().rollback();
	    			this.setMessage("6.Khong The tao moi CTTB_VITRIKH loi :"+ query);
	    			return false;
	    		}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);			
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
			db.update("rollback");
			this.msg="Lỗi cập nhật "+e.getMessage();
			return false;
		}
		return true;
	}
	
	public void createRS() 
	{
		this.createKbhRs();
		this.createVungRs();
		this.createKhuvucRs();
		this.createNppRs();
		
		String query = "";
		String ttb = "";
		if(this.id.length() == 0)
		{
			query = "select top(20) pk_seq as tratbId, diengiai, isnull(tongluong, 0) as tongluong, isnull(tongtien, 0) as tongtien, case loai when 2 then 'Tra san pham' when 1 then 'Tra tien' end as loai ";
			query = query + "from tratrungbay where pk_seq > '0' ";			
		}
		else
		{
			if(this.tratbIds!=null)
			{
				for(int i = 0; i < this.tratbIds.length; i++)
					ttb += this.tratbIds[i] + ",";
			}
			if(ttb.length() > 0)
				ttb = ttb.substring(0, ttb.length() - 1);
			query = "select top(19) pk_seq as tratbId, diengiai, isnull(tongluong, 0) as tongluong, isnull(tongtien, 0) as tongtien, case loai when 2 then 'Tra san pham' when 1 then 'Tra tien' end as loai ";
			query = query + "from tratrungbay where pk_seq not in (" + ttb + ")";
		}
		if(this.ttb_diengiai.length() > 0)
			query = query + " and Upper(diengiai) like Upper('%" + this.ttb_diengiai + "%') ";
		if(this.ttb_tungay.length() > 0)
			query = query + " and ngaytao >= '" + convertDate(this.ttb_tungay) + "'";
		if(this.ttb_denngay.length() > 0)
			query = query + " and ngaytao <= " + convertDate(this.ttb_denngay) + "'";
		
		if(this.id.length() > 0)
		{
			query = query + " union (select pk_seq as tratbId, diengiai, isnull(tongluong, 0) as tongluong, isnull(tongtien, 0) as tongtien, case loai when 2 then 'Tra san pham' when 1 then 'Tra tien' end as loai ";
			query = query +	"from tratrungbay where pk_seq in (" + ttb + ") ) ";
		}
		query = query + " order by pk_seq DESC ";
		
		this.tratbRs = db.get(query);
		
		query="select pk_seq,ma,ten from NHOMCTTRUNGBAY  ";
		this.nhomTbRs=this.db.get(query);
		
		this.hangKhRs = db.get("select PK_SEQ, DIENGIAI as TEN from HANGCUAHANG where TRANGTHAI = '1'");
		this.vitriKhRs = db.get("select PK_SEQ, DIENGIAI as TEN from VITRICUAHANG where TRANGTHAI = '1'");
	}

	private void createTratbIds() 
	{
		ResultSet rs = db.get("select tratrungbay_fk, pheptoan from cttb_tratb where cttrungbay_fk = '" + this.id + "'");
		if(rs != null)
		{
			try 
			{
				String ttb = "";
				String pt = "";
				while(rs.next())
				{
					ttb += rs.getString("tratrungbay_fk") + ",";
					pt += rs.getString("pheptoan") + ",";
				}
				if(ttb.length() > 0)
				{
					System.out.println("Tra trung bay la: " + ttb + " -- Phep toan tuong ung: " + pt + "\n");
					this.tratbIds = ttb.split(",");
					this.pheptoans = pt.split(",");
				}
				rs.close();
			} 
			catch(Exception e) {}
		}
	}

	private void createKbhRs() 
	{
		this.kbhRs = db.get("select pk_seq, ten from kenhbanhang where trangthai = '1'  and pk_seq=100025 ");
	}
	
	private void createVungRs() 
	{
		this.VungRs = db.get("select * from vung");
	}
	
	private void createKhuvucRs() 
	{
		String sql = "";
		if(this.vungIds.length() > 0)
		{
			sql = "select * from khuvuc where vung_fk in (" + this.vungIds + ")";
			this.KhuvucRs = db.get(sql); 
		}
		else
		{
			sql = "select * from khuvuc";
			this.KhuvucRs = db.get(sql);
		}
	}

	private void createNppRs() 
	{
	   	String query = "select pk_seq,ma , ten from nhaphanphoi where trangthai = '1' and sitecode=convsitecode and loainpp in ('0','1','4','5') and isKHACHHANG = 0  and pk_seq in( SELECT NPP_FK FROM NHAPP_KBH WHERE KBH_FK=100025 ) ";
	   	
	   	//loc theo khu vuc
	   	if(this.kvIds.length() > 0)
	   		query += " and  khuvuc_fk in (" + kvIds + ") ";
	   	
	   	//khong chon khu vuc, loc theo vung
	   	if(this.vungIds.length() > 0)
	   		query += " and khuvuc_fk in (select pk_seq from khuvuc where vung_fk in (" + vungIds + ") ) ";
	    
   		if(this.nppIds!=null &&this.nppIds.length()>0)
   		{
   			query+=" and pk_seq  in ("+nppIds+") union all select pk_seq,ma , ten from nhaphanphoi where trangthai = '1' and sitecode=convsitecode and priandsecond=0  and pk_Seq in ("+nppIds+" )";
   		}
	   	System.out.println("1.Khoi tao NPP:" + query);

    	this.NppRs = db.get(query);
	}

	private void createNsptbList()
	{
		String query = "select top(200) a.pk_seq as nsptbId, a.diengiai, isnull(a.tongluong, 0) as tongluong, isnull(a.tongtien, 0) as tongtien, b.pheptoan ";
		query = query + "from nhomsptrungbay a inner join cttb_nhomsptrungbay b on a.pk_seq = b.nhomsptrungbay_fk where b.cttrungbay_fk = '" + this.id + "'";
		
		System.out.println("Query khi cap nhat la: " + query + "\n");
		
		ResultSet rs = db.get(query);
		List<INhomsptrungbay> listDkkm = new ArrayList<INhomsptrungbay>();
		if(rs != null)
		{
			try 
			{
				INhomsptrungbay nsptb = null;
				while(rs.next())
				{
					String dkkmId = rs.getString("nsptbId");
					String diengiai = rs.getString("diengiai");
					String tongluong = rs.getString("tongluong");
					String tongtien = rs.getString("tongtien");
					String pheptoan = rs.getString("pheptoan");
					nsptb = new Nhomsptrungbay(dkkmId, diengiai, tongluong, tongtien);
					nsptb.setPheptoan(pheptoan);
					listDkkm.add(nsptb);
				}
				rs.close();
			} 
			catch(Exception e) {}
		}
		this.nsptbList = listDkkm;
	}
	
	public void init() 
	{
		String query = "select a.PK_SEQ as cttbId, a.SCHEME, a.ngaytds, a.ngaykttds, a.ngaytrungbay as ngaytb, a.ngayketthuctb as ngaykttb, " +
						"isnull(ngaydangky, '') as ngaydangky, isnull(ngayketthucDK, '') as ngayketthucDK, " +
						"isnull(a.DIENGIAI, '') as diengiai, a.TYPE, a.solanthanhtoan as solantt, a.NGANSACH, a.DASUDUNG, a.NGAYTAO, a.NGAYSUA," +
						" b.TEN as nguoitao, c.TEN as nguoisua, isnull(isdkthem, 'false') as isdkthem, isnull(istinhds, '1') as istinhds, thuongDhDautien, donviThuong,NHOMCTTB_FK " +
					"from CTTRUNGBAY a inner join NHANVIEN b on a.NGUOITAO = b.PK_SEQ inner join NHANVIEN c on a.NGUOISUA = c.PK_SEQ where a.pk_seq = '" + this.id + "'";
		
		System.out.println("Gia tri cau init la: " + query + "\n");
		
		ResultSet rs = db.get(query);

		try
        {
            rs.next();    	
            this.id = rs.getString("cttbId");
            this.scheme = rs.getString("scheme");
			this.diengiai = rs.getString("diengiai");
			
			this.ngaytds = rs.getString("ngaytds");
			this.ngaykttds = rs.getString("ngaykttds");
			this.ngaytb = rs.getString("ngaytb");
			this.ngaykttb = rs.getString("ngaykttb");
			
			this.ngaydk = rs.getString("ngaydangky");
			this.ngayktdk = rs.getString("ngayketthucDK");
			
			this.type = rs.getString("type");
			this.ngansach = rs.getString("ngansach");
			this.solantt = rs.getString("solantt");
			this.dasudung = rs.getString("dasudung");
			this.ngaytao = rs.getString("ngaytao");
			this.nguoitao = rs.getString("nguoitao");
			this.ngaysua = rs.getString("ngaysua");
			this.nguoisua = rs.getString("nguoisua");
			this.isDkthem = rs.getString("isdkthem");
			System.out.println("is Dang ky them la: " + this.isDkthem + "\n");
			
			this.isTinhds = rs.getString("istinhds");
			this.thuongDhdautien = rs.getString("thuongDhDautien") == null ? "" : rs.getString("thuongDhDautien");
			this.donviThuong = rs.getString("donviThuong") == null ? "0" : rs.getString("donviThuong");
			this.nhomTbId=rs.getString("NHOMCTTB_FK") == null ? "0" : rs.getString("NHOMCTTB_FK");
			rs.close();
       	}
        catch(Exception e){}
        
		if(this.id.length() > 0)
    	{
    		//tao nppIds
    		query = "select npp_fk as nppId from cttb_npp where cttb_fk ='"+ this.id +"'";    		
    		rs = db.get(query);
    		String nppIds = "";
    		if(rs != null)
    		{
    			try 
    			{
					while(rs.next())
					{
						if(rs.getString("nppId") != null)
							nppIds += rs.getString("nppId") + ",";
					}
					rs.close();
				} 
    			catch(Exception e) {}
    			   			
    			if(nppIds.length() > 0)
    			{
    				nppIds = nppIds.substring(0, nppIds.length() - 1);
    				this.nppIds = nppIds;
    			}
    		}
    	}
		
        //Thuong Sale
        this.createNdk();
        
        this.createNsptbList();
        this.createTratbIds();
        this.createRS();
        
        ResultSet rsData = db.get("select HANGKH_FK from CTTB_HANGKH where CTTB_FK ='" + this.id + "'");
        if(rsData != null)
        {
        	try 
        	{
        		String vungStr = "";
				while(rsData.next())
				{
					vungStr += rsData.getString("HANGKH_FK") + ",";
				}
				rsData.close();
				
				if(vungStr.trim().length() > 0)
				{
					this.hangKhIds = vungStr.substring(0, vungStr.length() - 1);
				}
			} 
        	catch (Exception e) { System.out.println("EXCEPTION HANG KH: " + this.hangKhIds ); }
        	
        	System.out.println("HANG KH LA: " + this.hangKhIds );
        }
        
        rsData = db.get("select VITRIKH_FK from CTTB_VITRIKH where CTTB_FK ='" + this.id + "'");
        if(rsData != null)
        {
        	try 
        	{
        		String vungStr = "";
				while(rsData.next())
				{
					vungStr += rsData.getString("VITRIKH_FK") + ",";
				}
				rsData.close();
				
				if(vungStr.trim().length() > 0)
				{
					this.vitriKhIds = vungStr.substring(0, vungStr.length() - 1);
				}
			} 
        	catch (Exception e) { System.out.println("EXCEPTION VI TRI KH: " + this.vitriKhIds ); }
        	
        	System.out.println("VI TRI KH LA: " + this.vitriKhIds );
        }
		
	}
	
	private void createNdk() 
	{
		String query = "select tieuchi, tumuc, denmuc, thuongSR, thuongTDSR, thuongSS, thuongTDSS, thuongASM, thuongTDASM " +
					   "from TieuchithuongTB_TIEUCHI " +
					   "where cttb_fk = '" + this.id + "'";
		
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			NumberFormat format = new DecimalFormat("##,###,###");
			try 
			{
				String tieu_chi = "";
				String tu_muc = "";
				String den_muc = "";
				String thuong_SR = "";
				String thuong_TDSR = "";
				String thuong_SS = "";
				String thuong_TDSS = "";
				String thuong_ASM = "";
				String thuong_TDASM = "";
				
				while(rs.next())
				{
					tieu_chi += rs.getString("tieuchi") + ",,";
					tu_muc += format.format(rs.getDouble("tumuc")) + ",,";
					den_muc += format.format(rs.getDouble("denmuc")) + ",,";
					thuong_SR += format.format(rs.getDouble("thuongSR")) + ",,";
					thuong_TDSR += format.format(rs.getDouble("thuongTDSR")) + ",,";
					thuong_SS += format.format(rs.getDouble("thuongSS")) + ",,";
					thuong_TDSS += format.format(rs.getDouble("thuongTDSS")) + ",,";
					thuong_ASM += format.format(rs.getDouble("thuongASM")) + ",,";
					thuong_TDASM += format.format(rs.getDouble("thuongTDASM")) + ",,";
				}
				rs.close();
				
				if(tieu_chi.trim().length() > 0)
				{
					tieu_chi = tieu_chi.substring(0, tieu_chi.length() - 2);
					this.diengiaiMuc = tieu_chi.split(",,");
					
					tu_muc = tu_muc.substring(0, tu_muc.length() - 2);
					this.tumuc = tu_muc.split(",,");
					
					den_muc = den_muc.substring(0, den_muc.length() - 2);
					this.denmuc = den_muc.split(",,");
					
					thuong_SR = thuong_SR.substring(0, thuong_SR.length() - 2);
					this.thuongSR = thuong_SR.split(",,");
					
					thuong_TDSR = thuong_TDSR.substring(0, thuong_TDSR.length() - 2);
					this.thuongTDSR = thuong_TDSR.split(",,");
					
					thuong_SS = thuong_SS.substring(0, thuong_SS.length() - 2);  System.out.println("___THUONG SS: " + thuong_SS);
					this.thuongSS = thuong_SS.split(",,");
					
					thuong_TDSS = thuong_TDSS.substring(0, thuong_TDSS.length() - 2);
					this.thuongTDSS = thuong_TDSS.split(",,");
					
					thuong_ASM = thuong_ASM.substring(0, thuong_ASM.length() - 2);
					this.thuongASM = thuong_ASM.split(",,");
					
					thuong_TDASM = thuong_TDASM.substring(0, thuong_TDASM.length() - 2);
					this.thuongTDASM = thuong_TDASM.split(",,");
				}
			} 
			catch (Exception e) {
				
				System.out.println("Loi khoi tao: " + e.toString());
			}
		}
		
	}
		
	public String getTtb_diengiai() 
	{	
		return this.ttb_diengiai;
	}
	
	public void setTtb_diengiai(String ttb_diengiai) 
	{
		this.ttb_diengiai = ttb_diengiai;		
	}
	
	public String getTtb_tungay() 
	{	
		return this.ttb_tungay;
	}
	
	public void setTtb_tungay(String ttb_tungay) 
	{
		this.ttb_tungay = ttb_tungay;		
	}
	
	public String getTtb_denngay() 
	{		
		return this.ttb_denngay;
	}
	
	public void setTtb_denngay(String ttb_denngay)
	{
		this.ttb_denngay = ttb_denngay;		
	}
	
	public String getNsptb_diengiai() 
	{	
		return this.nsptb_diengiai;
	}
	
	public void setNsptb_diengiai(String nsptb_diengiai) 
	{
		this.nsptb_diengiai = nsptb_diengiai;		
	}
	
	public String getNsptb_tungay() 
	{	
		return this.nsptb_tungay;
	}
	
	public void setNsptb_tungay(String nsptb_tungay) 
	{
		this.nsptb_tungay = nsptb_tungay;		
	}
	
	public String getNsptb_denngay() 
	{		
		return this.nsptb_denngay;
	}
	
	public void setNsptb_denngay(String nsptb_denngay)
	{
		this.nsptb_denngay = nsptb_denngay;		
	}
	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	private String convertDate(String date)
	{
		//chuyen dinh dang dd-MM-yyyy sang dinh dang yyyy-MM-dd
		if(!date.contains("-"))
			return "";
		String[] arr = date.split("-");
		if(arr[0].length() < arr[2].length())
			return arr[2] + "-" + arr[1] + "-" + arr[0];
		return date;
	}

	public String getSolantt() 
	{
		return this.solantt;
	}

	public void setSolantt(String solantt) 
	{
		this.solantt = solantt;
	}

	public String getNgayBddk() 
	{
		return this.ngaydk;
	}

	public void setNgayBddk(String ngayBddk) 
	{
		this.ngaydk = ngayBddk;
	}

	public String getNgayKtdk() 
	{	
		return this.ngayktdk;
	}

	public void setNgayKtdk(String ngayKtdk) 
	{
		this.ngayktdk = ngayKtdk;
	}

	public String isDangkythem() 
	{
		return this.isDkthem;
	}

	public void setDangkythem(String flag) 
	{
		this.isDkthem = flag;
	}

	public void setKbhRs(ResultSet kbh) 
	{
		this.kbhRs = kbh;
	}

	public ResultSet getKbhRs() 
	{
		return this.kbhRs;
	}
	
	public void setVungRs(ResultSet vung) 
	{
		this.VungRs = vung;
	}
	
	public ResultSet getVungRs()
	{
		return this.VungRs;
	}
	
	public void setKhuvucRs(ResultSet khuvuc) 
	{		
		this.KhuvucRs = khuvuc;
	}

	
	public ResultSet getKhuvucRs() 
	{
		return this.KhuvucRs;
	}

	public void setNppRs(ResultSet npp)
	{
		this.NppRs = npp;
	}

	public ResultSet getNppRs()
	{
		return this.NppRs;
	}
	
	public String getKbhIds() 
	{
		return this.kbhIds;
	}

	public void setKbhIds(String kenhIds)
	{
		this.kbhIds = kenhIds;	
	}

	public String getVungIds() 
	{
		return this.vungIds;
	}

	public void setVungIds(String vungIds) 
	{
		this.vungIds = vungIds;
	}

	public String getKhuvucIds()
	{
		return this.kvIds;
	}

	public void setKhuvucIds(String kvIds) 
	{
		this.kvIds = kvIds;
	}

	public String getNppIds() 
	{
		return this.nppIds;
	}

	public void setNppIds(String nppIds) 
	{
		this.nppIds = nppIds;	
	}

	public void setActive(String active)
	{
		this.active = active;
	}

	public String getActive() 
	{
		return this.active;
	}

	
	public void DbClose() {			
		try{
			if(kbhRs!=null){
				kbhRs.close();
			}
			if(VungRs!=null){
				VungRs.close();
			}
			if(KhuvucRs!=null){
				KhuvucRs.close();
			}
			if(NppRs!=null){
				NppRs.close();
			}
			if(tratbRs!=null){
				tratbRs.close();
			}
			if(db!=null){
				db.shutDown();
			}
			if(nsptbList!=null)
			{
				nsptbList.clear();
				nsptbList=null;
			}
			
		}catch(Exception er){
			
		}
	}

	public String isTinhdoanhso() 
	{
		return this.isTinhds;
	}

	public void setTinhdoanhso(String flag) 
	{
		this.isTinhds = flag;
	}

public String[] getDiengiaiMuc() {
		
		return this.diengiaiMuc;
	}

	
	public void setDiengiaiMuc(String[] diengiai) {
		
		this.diengiaiMuc = diengiai;
	}

	
	public String[] getTumuc() {
		
		return this.tumuc;
	}

	
	public void setTumuc(String[] tumuc) {
		
		this.tumuc = tumuc;
	}

	
	public String[] getDenmuc() {
		
		return this.denmuc;
	}

	
	public void setDenmuc(String[] denmuc) {
		
		this.denmuc = denmuc;
	}

	
	public String[] getThuongSR() {
		
		return this.thuongSR;
	}

	
	public void setThuongSR(String[] thuongSR) {
		
		this.thuongSR = thuongSR;
	}

	
	public String[] getThuongTDSR() {
		
		return this.thuongTDSR;
	}

	
	public void setThuongTDSR(String[] thuongTDSR) {
		
		this.thuongTDSR = thuongTDSR;
	}

	
	public String[] getThuongSS() {
		
		return this.thuongSS;
	}

	
	public void setThuongSS(String[] thuongSS) {
		
		this.thuongSS = thuongSS;
	}

	
	public String[] getThuongTDSS() {
		
		return this.thuongTDSS;
	}

	
	public void setThuongTDSS(String[] thuongTDSS) {
		
		this.thuongTDSS = thuongTDSS;
	}

	
	public String[] getThuongASM() {
		
		return this.thuongASM;
	}

	
	public void setThuongASM(String[] thuongASM) {
		
		this.thuongASM = thuongASM;
	}

	
	public String[] getThuongTDASM() {
		
		return this.thuongTDASM;
	}

	
	public void setThuongTDASM(String[] thuongTDASM) {
		
		this.thuongTDASM = thuongTDASM;
	}

	
	public String getThuongDhDautien() {
		
		return this.thuongDhdautien;
	}

	
	public void setThuongDhDautien(String thuong) {
		
		this.thuongDhdautien = thuong;
	}

	
	public String getDonvithuong() {
		
		return this.donviThuong;
	}

	
	public void setDonvithuong(String dvThuong) {
		
		this.donviThuong = dvThuong;
	}
	public String getNhomTbId()
	{
		return nhomTbId;
	}

	public void setNhomTbId(String nhomTbId)
	{
		this.nhomTbId = nhomTbId;
	}

	public ResultSet getNhomTbRs()
	{
		return nhomTbRs;
	}

	public void setNhomTbRs(ResultSet nhomTbRs)
	{
		this.nhomTbRs = nhomTbRs;
	}

public void setHangkhRs(ResultSet hangKh) {
		
		this.hangKhRs = hangKh;
	}

	
	public ResultSet getHangkhRs() {
		
		return this.hangKhRs;
	}

	
	public void setHangkhIds(String hangIds) {
		
		this.hangKhIds = hangIds;
	}

	
	public String getHangkhIds() {
		
		return this.hangKhIds;
	}

	
	public void setVitrikhRs(ResultSet vtKh) {
		
		this.vitriKhRs = vtKh;
	}

	
	public ResultSet getVitrikhRs() {
		
		return this.vitriKhRs;
	}

	
	public void setVitrikhIds(String vtIds) {
		
		this.vitriKhIds = vtIds;
	}

	
	public String getVitrikhIds() {
		
		return this.vitriKhIds;
	}
}
