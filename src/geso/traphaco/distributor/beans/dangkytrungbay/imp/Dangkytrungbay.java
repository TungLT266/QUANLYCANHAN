package geso.traphaco.distributor.beans.dangkytrungbay.imp;

import geso.traphaco.distributor.beans.dangkytrungbay.IDangkytrungbay;
import geso.traphaco.distributor.db.sql.dbutils;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

public class Dangkytrungbay implements IDangkytrungbay, Serializable
{
	private static final long serialVersionUID = -9217977546733610214L;

	String userId;
	
	String id;		
	String tgTinhds; //thoi gian bat dau tinh doanh so
	String tgKtTinhds;
	String tgBatdau;
	String tgKetthuc;	
	
	String tgBatdauDk;
	String tgKetthucDk;
	
	String solantt;
	String istinhds;
	String nhomTbId;
	
	int type;
	String trangthai;
	String ngaytao;
	String nguoitao;
	String ngaysua;
	String nguoisua;
	String msg;
	String sosuatphanbo;
	ResultSet cttrunggbay;
	String cttbId;
	String cttbTen;
	String sosuatdaphanbo;
	String dangkythem;
	ResultSet nvbanhang;
	String[] nvbhIds;
	String scheme;
	ResultSet khlist;
	String diengiai;
	Hashtable<String, Integer> kh_soxuat = new Hashtable<String, Integer>();
	ResultSet khSelectedlist;
		
	String nppId;
	String nppTen;
	String sitecode;
	
	String mucdangky;
	
	Hashtable<String, Integer> sanpham_soluong;
	Hashtable<String, Float> sanpham_sotien;
	Hashtable<String,Integer>soxuat;	
	dbutils db;
	
	public Dangkytrungbay(String[] param)
	{
		this.id = param[0];
		this.cttbId = param[1];
		this.ngaytao = param[2];
		this.nguoitao = param[3];
		this.ngaysua = param[4];
		this.nguoisua = param[5];
		this.trangthai = param[6];
		this.diengiai = param[7];
		this.tgBatdau = "";
		this.tgKetthuc = "";
		this.solantt = "";
		this.istinhds = "";
		this.msg = "";
		this.scheme ="";
		this.dangkythem ="";
		this.tgBatdauDk="";
		this.tgKetthucDk="";
		this.mucdangky = "";
		db = new dbutils();
	}
	
	public Dangkytrungbay(String id)
	{
		this.id = id;
		this.ngaytao = "";
		this.nguoitao = "";
		this.ngaysua = "";
		this.nguoisua = "";
		this.cttbId = "";
		this.tgBatdau = "";
		this.tgKetthuc = "";
		this.solantt = "";
		this.msg = "";
		this.tgTinhds ="";
		this.tgKtTinhds="";
		this.sosuatphanbo ="0";
		this.sosuatdaphanbo ="0";
		this.scheme ="";
		this.diengiai ="";
		this.dangkythem ="";
		this.istinhds = "";
		this.tgBatdauDk="";
		this.tgKetthucDk="";
		this.mucdangky = "";
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
	
	private void getNppInfo()
	{		
		geso.traphaco.distributor.util.Utility util=new geso.traphaco.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();
		this.sitecode=util.getSitecode();
	}
	
	public ResultSet getNvBanhang() 
	{		
		return this.nvbanhang;
	}
	
	public void setNvBanhang(ResultSet nvbanhang) 
	{
		this.nvbanhang = nvbanhang;		
	}
	
	public ResultSet getCttrungbay() 
	{	
		return this.cttrunggbay;
	}
	
	public void setCttrungbay(ResultSet cttrungbay) 
	{
		this.cttrunggbay = cttrungbay;		
	}
	
	public String getCttbId() 
	{		
		return this.cttbId;
	}
	
	public void setCttbId(String cttbId) 
	{
		this.cttbId = cttbId;		
	}
	
	public String getCttbTen() 
	{		
		return this.cttbTen;
	}
	
	public void setCttbTen(String cttbten) 
	{
		this.cttbTen = cttbten;		
	}
	
	public String getThoigianbd() 
	{		
		return this.tgBatdau;
	}
	
	public void setThoigianbd(String tgBatdau) 
	{
		this.tgBatdau = tgBatdau;		
	}
	
	public String getThoigiankt() 
	{		
		return this.tgKetthuc;
	}
	
	public void setThoigiankt(String tgKetthuc) 
	{
		this.tgKetthuc = tgKetthuc;		
	}
	
	public String getSolantt() 
	{		
		return this.solantt;
	}
	
	public void setSolantt(String solantt)
	{
		this.solantt = solantt;		
	}

	public ResultSet getKhList() 
	{
		return this.khlist;
	}

	public void setKhList(ResultSet khlist)
	{
		this.khlist = khlist;
	}

	public Hashtable<String, Integer> getKh_Soxuat() 
	{
		return this.kh_soxuat;
	}

	public void setKh_Soxuat(Hashtable<String, Integer> kh_soxuat) 
	{
		this.kh_soxuat = kh_soxuat;
	}
	
	public int getType()
	{
		return this.type;
	}

	public void setType(int type) 
	{
		this.type = type;
	}
	
	public boolean CreateDktb(String[] khIds, String[] ddkdIds, String[] soxuat, String[] dangky) 
	{
		this.ngaytao = getDateTime();
		this.nguoitao = this.userId;
		try 
		{
			db.getConnection().setAutoCommit(false);
		
			long tong=0;
			for(int k=0;k < dangky.length; k++)
			{
				if(dangky[k].length()==0)
				{
					dangky[k] = "0";
				}
				tong += Long.parseLong(dangky[k]);
			}
			if(tong > Long.parseLong(this.sosuatphanbo))
			{
				db.update("ROLLBACK");
				this.msg = "Bạn đã vượt ngân sách được phân bổ, vui lòng giảm số xuất đăng ký";
				return false;
			}
			
			if(this.cttbId.trim().length() <= 0 )
			{
				db.update("ROLLBACK");
				this.msg = "Vui lòng chọn chương trình đăng ký";
				return false;
			}
			
			if(this.mucdangky.trim().length() <= 0 )
			{
				db.update("ROLLBACK");
				this.msg = "Vui lòng chọn mức đăng ký";
				return false;
			}
			
			
			//CHECK XEM MUC NAY DA CO DANG KY CHUA
			String query = " select count(*) as sodong from dangkytrungbay where cttrungbay_fk = '" + this.cttbId + "' and muc = '" + this.mucdangky + "' ";
			ResultSet rs = db.get(query);
			int count = 0;
			if(rs != null)
			{
				if(rs.next())
				{
					count = rs.getInt("sodong");
					rs.close();
				}
			}
			
			if(count > 0 )
			{
				db.update("ROLLBACK");
				this.msg = "Chương trình trưng bày với mức ( " + ( Integer.parseInt(this.mucdangky) + 1 ) + " ) đã được đăng ký.";
				return false;
			}
			
			query = "insert into dangkytrungbay(cttrungbay_fk, ngaydangky, ngaysua, nguoitao, nguoisua, npp_fk, trangthai, muc) " +
					"values('" + this.cttbId + "', '" + this.ngaytao + "', '" + this.ngaytao + "', '"  + this.nguoitao + "', '" + this.nguoitao + "', '" + this.nppId + "', '0', '" + this.mucdangky + "')";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				this.msg = "1.Khong the tao moi 'dangkytrungbay' " + query;
				return false;
			}
			
			query = "select IDENT_CURRENT('dangkytrungbay') as dktbId";
			ResultSet rsDktb = this.db.get(query);						
			rsDktb.next();
			String dktbId = rsDktb.getString("dktbId");
			rsDktb.close();
			
			if(khIds.length > 0)
			{
				for(int i = 0; i < khIds.length; i++)
				{
					if(dangky[i].length() > 0)
					{
						if(soxuat[i] == "")
							soxuat[i] = "0";
						
						query = "insert into dktrungbay_khachhang(dktrungbay_fk, khachhang_fk, soxuat, dangky, dadangky) " +
								"select '" + dktbId + "', '" + khIds[i] + "', '" + soxuat[i] + "', '" + dangky[i] + "', '1' ";
						
						System.out.println("2.Insert khachhang:" + query);
						if(!db.update(query))
						{
							db.update("ROLLBACK");
							this.msg = "Khong the tao moi 'dktrungbay_khachhang' " + query;
							return false;
						}
						
						/*query = "UPDATE NHOMCTTRUNGBAY_NPP SET NHOMCTTRUNGBAY_NPP.DASUDUNG = NPP.DASUDUNG + '"+dangky[i]+"' " +
								"FROM NHOMCTTRUNGBAY_NPP NPP INNER JOIN NHOMCTTRUNGBAY NTB "+
								"	ON NPP.NHOMCTTRUNGBAY_FK=NTB.PK_SEQ  "+
								"	INNER JOIN CTTRUNGBAY TB ON TB.NHOMCTTB_FK=NTB.PK_SEQ "+
								" WHERE TB.PK_SEQ='"+this.cttbId+"' AND NPP.NPP_FK='"+this.nppId+"' ";
						if(!db.update(query))
						{
							db.update("ROLLBACK");
							this.msg = "3.Khong the cap nhat 'phanbotrungbay' " + query;
							return false;
						}*/
					}
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch(Exception e)
		{
			try {
				db.getConnection().rollback();
			} catch (SQLException e1) {e.printStackTrace();}
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean UpdateDktb(String[] khIds, String[] ddkdIds, String[] soxuat, String[] dangky) 
	{	
		this.ngaysua = getDateTime();
		this.nguoisua = this.userId;
		try 
		{
			db.getConnection().setAutoCommit(false);
		
			if(this.cttbId.trim().length() <= 0 )
			{
				db.update("ROLLBACK");
				this.msg = "Vui lòng chọn chương trình đăng ký";
				return false;
			}
			
			if(this.mucdangky.trim().length() <= 0 )
			{
				db.update("ROLLBACK");
				this.msg = "Vui lòng chọn mức đăng ký";
				return false;
			}
			
			//CHECK XEM MUC NAY DA CO DANG KY CHUA
			String query = " select count(*) as sodong from dangkytrungbay where pk_seq != '" + this.id + "' and cttrungbay_fk = '" + this.cttbId + "' and muc = '" + this.mucdangky + "' ";
			ResultSet rs = db.get(query);
			int count = 0;
			if(rs != null)
			{
				if(rs.next())
				{
					count = rs.getInt("sodong");
					rs.close();
				}
			}
			
			if(count > 0 )
			{
				db.update("ROLLBACK");
				this.msg = "Chương trình trưng bày với mức ( " + ( Integer.parseInt(this.mucdangky) + 1 ) + " ) đã được đăng ký.";
				return false;
			}
			
			query = "update dangkytrungbay set nguoisua = '" + this.nguoisua + "', ngaysua = '" + this.ngaysua + "' where pk_Seq='" + this.id + "'";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				this.msg = "Khong the cap nhat 'dangkytrungbay' " + query;
				return false;
			}
			
			/*query =
			"	UPDATE NHOMCTTRUNGBAY_NPP SET NHOMCTTRUNGBAY_NPP.DASUDUNG =PBTB.DASUDUNG - DK.DANGKY "+
			"	FROM  "+
			"	( "+
			"		SELECT SUM(DKKH.DANGKY) AS DANGKY,DK.CTTRUNGBAY_FK,NPP_FK "+
			"		FROM DKTRUNGBAY_KHACHHANG "+
			"		DKKH INNER JOIN DANGKYTRUNGBAY DK "+
			"		ON DK.PK_SEQ=DKKH.DKTRUNGBAY_FK "+
			"		WHERE DK.NPP_FK="+this.nppId+" AND DK.CTTRUNGBAY_FK="+this.cttbId+" and dk.pk_seq='"+this.id+"' "+
			"		GROUP BY CTTRUNGBAY_FK,NPP_FK "+
			"	)AS DK INNER JOIN CTTRUNGBAY TB ON TB.PK_SEQ=DK.CTTRUNGBAY_FK "+
			"		INNER JOIN  NHOMCTTRUNGBAY NTB ON NTB.PK_SEQ=TB.NHOMCTTB_FK "+ 
			"		INNER JOIN NHOMCTTRUNGBAY_NPP PBTB ON PBTB.NHOMCTTRUNGBAY_FK=NTB.PK_SEQ "+
			"		AND DK.NPP_FK=PBTB.NPP_FK "+
			"	 WHERE TB.PK_SEQ='"+this.cttbId+"' AND PBTB.NPP_FK='"+this.nppId+"' ";*/
			
			if(!db.update(query))
			{
				db.getConnection().rollback();
				this.msg = "Khong the cap nhat 'dangkytrungbay' " + query;
				return false;
			}
			
			if(khIds.length > 0)
			{
				query = "delete from dktrungbay_khachhang where dktrungbay_fk = (select pk_seq from dangkytrungbay where pk_seq = '" + this.id + "')";
				System.out.println("[dktrungbay_khachhang]"+query);
				if(!db.update(query))
				{
					db.getConnection().rollback();
					this.msg = "Loi khi cap nhat 'dktrungbay_khachhang' " + query;
					return false;
				}
				long tong=0;
				for(int k=0;k<dangky.length;k++)
				{
					if(dangky[k].length()==0)
					{
						dangky[k] = "0";
					}
					tong+=Long.parseLong(dangky[k]);
				}

				if(tong>Long.parseLong(this.sosuatphanbo))
				{
					db.update("ROLLBACK");
					this.msg = "Bạn đã vượt ngân sách được phân bổ, vui lòng giảm số xuất đăng ký";
					return false;
				}
				for(int i = 0; i < khIds.length; i++)
				{

						if(dangky[i].length()==0)
						{
							dangky[i] = "0";
						}
						
					
					if(this.tgTinhds.equals("1"))
					{
						if(Integer.parseInt(soxuat[i])<Integer.parseInt(dangky[i]))
						{
							db.update("ROLLBACK");
							String queryidsp = "select smartid from KHACHHANG where pk_seq='"+ khIds[i] +"' and npp_fk='"+this.nppId+"'";
							String pk_seq="";
							ResultSet rskh = db.get(queryidsp);
							if(rskh!=null)
							{
								if(rskh.next())
								{
									pk_seq = rskh.getString("smartid");
									rskh.close();
								}
							}
							else
							{
								db.update("ROLLBACK");
								this.msg = "Không tìm thấy khách hàng lỗi tại: "+queryidsp;
								return false;
								
							}
							this.msg = "Chương trình tính doanh số: số xuất đăng ký của khách hàng có mã " + pk_seq+" không thể lớn hơn số xuất mua";
							return false;
						}
					}
					
					//String nhomTbId="(select NHOMCTTB_FK from cttrungbay where pk_seq='"+this.cttbId+"')";
					query = "insert into dktrungbay_khachhang(dktrungbay_fk, khachhang_fk, soxuat, dangky, dadangky) " +
							"select '" + this.id + "', '" + khIds[i] + "', '" + soxuat[i] + "', '" + dangky[i] + "', '1' ";
					
					if(!db.update(query))
					{
						db.update("ROLLBACK");
						this.msg = "Khong the tao moi 'dktrungbay_khachhang' " + query;
						return false;
					}
	  			}
				query="DELETE FROM DKTRUNGBAY_KHACHHANG WHERE DANGKY=0 AND DKTRUNGBAY_FK="+this.id+" ";
				if(!db.update(query))
				{
					db.update("ROLLBACK");
					this.msg = "Khong the tao moi 'dktrungbay_khachhang' " + query;
					return false;
				}
				
				/*query =
				"	UPDATE NHOMCTTRUNGBAY_NPP SET NHOMCTTRUNGBAY_NPP.DASUDUNG =PBTB.DASUDUNG +DK.DANGKY "+
				"	FROM  "+
				"	( "+
				"		SELECT SUM(DKKH.DANGKY) AS DANGKY,DK.CTTRUNGBAY_FK,NPP_FK "+
				"		FROM DKTRUNGBAY_KHACHHANG "+
				"		DKKH INNER JOIN DANGKYTRUNGBAY DK "+
				"		ON DK.PK_SEQ=DKKH.DKTRUNGBAY_FK "+
				"		WHERE DK.NPP_FK="+this.nppId+" AND DK.CTTRUNGBAY_FK="+this.cttbId+" and dk.pk_seq='"+this.id+"' "+
				"		GROUP BY CTTRUNGBAY_FK,NPP_FK "+
				"	)AS DK INNER JOIN CTTRUNGBAY TB ON TB.PK_SEQ=DK.CTTRUNGBAY_FK "+
				"		INNER JOIN  NHOMCTTRUNGBAY NTB ON NTB.PK_SEQ=TB.NHOMCTTB_FK "+ 
				"		INNER JOIN NHOMCTTRUNGBAY_NPP PBTB ON PBTB.NHOMCTTRUNGBAY_FK=NTB.PK_SEQ "+
				"		AND DK.NPP_FK=PBTB.NPP_FK "+
				"	 WHERE TB.PK_SEQ='"+this.cttbId+"' AND PBTB.NPP_FK='"+this.nppId+"' ";
				System.out.println("5.Cap nhat phan bo trung bay:"+query);
				if(!db.update(query))
				{
					db.getConnection().rollback();
					this.msg = "Loi khi cap nhat 'phanbotrungbay' " + query;
					return false;
				}*/
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch(Exception e)
		{
			db.update("ROLLBACK");
			this.msg=e.toString();
			return false;
		}
		return true;
	}
	
	boolean kiemtra(String khachhang)
	{
		String sql = " select count(*) as num from DKTRUNGBAY_KHACHHANG where dktrungbay_fk ='"+ this.id +"' and khachhang_fk ='"+ khachhang +"'";
		ResultSet rs = db.get(sql);
		try 
		{
			if(rs.next())
			{
				if(rs.getString("num").equals("0"))
				{
					rs.close();
					return false;
				}
				if(rs != null)
					rs.close();
			}
			
		} catch(Exception e) 
		{
			e.printStackTrace();
			return false;
		}
		
	   	return true;
	}
	
	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai)
	{
		this.trangthai = trangthai;
	}
	
	public String getTgbdTinhds() 
	{
		return this.tgTinhds;
	}

	public void setTgbdTinhds(String tgbdTinhds)
	{
		this.tgTinhds = tgbdTinhds;
	}

	public String getTgktTinhds() 
	{
		return this.tgKtTinhds;
	}

	public void setTgktTinhds(String tgktTinhds) 
	{
		this.tgKtTinhds = tgktTinhds;
	}
	
	public Hashtable<Integer, String> getNvbhIds() 
	{
		Hashtable<Integer, String> selected = new Hashtable<Integer, String>();
		if(this.nvbhIds != null){
			int size = (this.nvbhIds).length;
			int m = 0;
			while(m < size){
				selected.put(new Integer(m), this.nvbhIds[m]);
				m++;
			}
		}else{
			selected.put(new Integer(0), "null");
		}
		return selected;
	}

	public void setNvbhIds(String[] nvbhIds) 
	{
		this.nvbhIds = nvbhIds;
	}

	public ResultSet getKhSelectedList() 
	{
		return this.khSelectedlist;
	}

	public void setKhSelectedList(ResultSet khSelectedlist) 
	{
		this.khSelectedlist = khSelectedlist;
	}
	
	public void createRS() 
	{
		this.getNppInfo();
		this.createNvbhRs();
		if( this.id.length() > 0 )
			createCttbUpdateRs();
		else
			this.createCttbTaomoiRs();
		
		this.initThoigian();
		this.createKhRs();
	}
	
	private void createCttbTaomoiRs()
	{
		/*String sql = 
		"	SELECT NCT.MA AS NHOMTBMA,NCT.TEN AS NHOMTBTEN,CT.PK_SEQ AS CTTBID, SCHEME, DIENGIAI AS CTTBTEN "+ 
		"		FROM CTTRUNGBAY CT "+
		"		INNER JOIN NHOMCTTRUNGBAY NCT ON NCT.PK_SEQ=CT.NHOMCTTB_FK "+
		"		INNER JOIN NHOMCTTRUNGBAY_NPP PBTB ON PBTB.NPP_FK = '"+this.nppId+"' AND PBTB.NHOMCTTRUNGBAY_FK=NCT.PK_SEQ "+
		"	WHERE CT.PK_SEQ NOT IN "+ 
		"		( "+
		"			SELECT CTTRUNGBAY_FK  "+
		"			FROM DANGKYTRUNGBAY  "+
		"			WHERE NPP_FK= '"+this.nppId+"' "+
		"		)   ";		
		sql+= " order by nct.pk_seq ";*/
		
		String sql = "select '' as NHOMTBMA, '' as NHOMTBTEN, PK_SEQ AS CTTBID, SCHEME, DIENGIAI AS CTTBTEN " + 
					 " from TIEUCHITHUONGTL where phanloai = '2' and TRANGTHAI = '1' and PK_SEQ in ( select thuongtl_fk from TIEUCHITHUONGTL_NPP where npp_fk = '" + this.nppId + "' ) " + 
					 "  order by nam desc ";
		
		System.out.println("1.Lay CT TB :"+ sql);
		this.cttrunggbay = db.get(sql);
	}
	
	private void createCttbUpdateRs()
	{
		String sql = "select '' as NHOMTBMA, '' as NHOMTBTEN, PK_SEQ AS CTTBID, SCHEME, DIENGIAI AS CTTBTEN from TIEUCHITHUONGTL where phanloai = '2' and TRANGTHAI = '1' and PK_SEQ in ( select thuongtl_fk from TIEUCHITHUONGTL_NPP where npp_fk = '" + this.nppId + "' ) order by nam desc ";
		
		System.out.println("INIT TB: " + sql);
		this.cttrunggbay = db.get(sql);
	}
	
	private void createNvbhRs()
	{
		String sql = "select pk_seq as nvbhId, ten as nvbhTen, diachi, dienthoai " + 
					 " from daidienkinhdoanh where npp_fk = '" + this.nppId + "' and trangthai = '1'";
		this.nvbanhang = db.get(sql);
	}
	
	private void initThoigian()
	{
		/*String query =  "	SELECT CT.PK_SEQ, CT.NGAYTDS AS NBD, CT.NGAYKTTDS AS NKT, CT.NGAYTRUNGBAY, CT.NGAYKETTHUCTB, NgayDangKy,NgayKetThucDK,CT.SOLANTHANHTOAN, CT.TYPE, CT.SCHEME, "+  
						"		ISNULL(CT.ISDKTHEM, 'FALSE') AS ISDKTHEM, ISNULL(CT.ISTINHDS, 1) AS ISTINHDS, PBTB.NGANSACH AS NGANSACH, "+
						"		ISNULL(PBTB.DASUDUNG, 0) AS DASUDUNG 		 "+
						"	FROM CTTRUNGBAY CT INNER JOIN NHOMCTTRUNGBAY NCT ON NCT.PK_SEQ=CT.NHOMCTTB_FK "+ 		
						"		INNER JOIN NHOMCTTRUNGBAY_NPP PBTB ON PBTB.NHOMCTTRUNGBAY_FK=NCT.PK_SEQ 	 "+
						"		LEFT JOIN  "+
						"		( "+
						"			SELECT SUM(DKKH.DANGKY) AS SUDUNG,NHOMCTTRUNGBAY_FK "+
						"			FROM DANGKYTRUNGBAY DK INNER JOIN DKTRUNGBAY_KHACHHANG DKKH ON DK.PK_SEQ = DKKH.DKTRUNGBAY_FK "+
						"			WHERE DK.CTTRUNGBAY_FK != '" + this.cttbId + "' "+
						"			GROUP BY DKKH.NHOMCTTRUNGBAY_FK "+
						"		) SUDUNG ON SUDUNG.NHOMCTTRUNGBAY_FK=PBTB.NHOMCTTRUNGBAY_FK "+
						"	WHERE CT.PK_SEQ = '"+this.cttbId+"' AND PBTB.NPP_FK = '"+this.nppId+"'  ";*/
		
		if(this.cttbId.trim().length() > 0 && this.mucdangky.trim().length() > 0 )
		{
			String query =   "SELECT CT.PK_SEQ, CT.ngayds_tungay AS NBD, CT.ngayds_denngay AS NKT, CT.THANG as NGAYTRUNGBAY, CT.NAM as NGAYKETTHUCTB,  " + 
							 "	   CT.ngaytb_tungay as NgayDangKy, CT.ngaytb_denngay as NgayKetThucDK, 0 as SOLANTHANHTOAN, 1 as TYPE, CT.SCHEME,    " + 
							 "		'FALSE' AS ISDKTHEM, 0 AS ISTINHDS, PBTB.soluong AS NGANSACH,  " + 
							 "		0 AS DASUDUNG 		  " + 
							 "FROM TIEUCHITHUONGTL CT INNER JOIN TIEUCHITHUONGTL_NPP PBTB ON CT.PK_SEQ = PBTB.thuongtl_fk 	  " + 
							 "	LEFT JOIN   " + 
							 "	(  " + 
							 "		SELECT SUM(DKKH.DANGKY) AS SUDUNG,NHOMCTTRUNGBAY_FK  " + 
							 "		FROM DANGKYTRUNGBAY DK INNER JOIN DKTRUNGBAY_KHACHHANG DKKH ON DK.PK_SEQ = DKKH.DKTRUNGBAY_FK  " + 
							 "		WHERE DK.CTTRUNGBAY_FK != '" + this.cttbId + "' and dk.muc = '" + this.mucdangky + "'  " + 
							 "		GROUP BY DKKH.NHOMCTTRUNGBAY_FK  " + 
							 "	)  " + 
							 "	SUDUNG ON SUDUNG.NHOMCTTRUNGBAY_FK = PBTB.thuongtl_fk  " + 
							 "WHERE CT.PK_SEQ = '" + this.cttbId + "' AND PBTB.NPP_FK = '" + this.nppId + "' and PBTB.muc = '" + this.mucdangky + "' ";
			System.out.println("1.initxxx: " +query);
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				try 
				{
					rs.next();
					this.cttbId = rs.getString("pk_seq");
					
					this.tgTinhds = rs.getString("nbd");
					this.tgKtTinhds = rs.getString("nkt");
					this.tgBatdau = rs.getString("ngaytrungbay");
					this.tgKetthuc = rs.getString("ngayketthuctb");
					this.tgBatdauDk = rs.getString("NgayDangKy");
					this.tgKetthucDk= rs.getString("NgayKetThucDK");
					
					this.solantt = rs.getString("solanthanhtoan");
					this.dangkythem =rs.getString("isdkthem").trim();
					this.type = rs.getInt("type");
					this.scheme = rs.getString("scheme");
					this.sosuatphanbo = rs.getString("ngansach");
					this.sosuatdaphanbo = rs.getString("dasudung");
					this.istinhds = rs.getString("istinhds");
					rs.close();
				} 
				
				catch(Exception e) 
				{
					System.out.println("115.Loi:"+ e.toString());
					e.printStackTrace();
				}
				
			}
		}
	}
	
	public void createNvbhIds()
	{
		/*String query = "select ddkd_fk from dktrungbay_khachhang where dktrungbay_fk='" + this.id +"'";
		String str = "";
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				int m = 0;
				while(rs.next())
				{
					str = str + rs.getString("ddkd_fk") + ",";
					m++;
				}
				rs.close();
			} 
			catch(Exception e) {}
		}
		if(str.length() > 0)
		{
			str = str.substring(0, str.length() - 1);
			this.nvbhIds = str.split(",");
		}		*/
	}
	
	private void createKhSelectedList()
	{
		String query = "select  a.khachhang_fk, b.ten as khTen, b.SMARTID as ma, isnull(b.diachi, 'na') as DiaChi, isnull(b.dienthoai, 'na') as DienThoai, " +
							"a.ddkd_fk as ddkdId, isnull(a.soxuat, '0') as soxuat, a.dangky, a.dadangky " +
						"from dktrungbay_khachhang a inner join khachhang b on a.khachhang_fk = b.pk_seq " +
						"where a.dktrungbay_fk = '" + this.id + "' order by dangky desc ";
		
		System.out.println("___Khach hang da dang ky: " + query);
		
		//this.khSelectedlist = db.get(query);
		this.khlist = db.get(query);
	}
	
	public void init() 
	{
		this.getNppInfo();
		
		String query = "select pk_seq as dktbId, cttrungbay_fk as cttbId, ngaydangky, ngaysua, isnull(trangthai, 0) as trangthai, muc " +
						"from dangkytrungbay where pk_seq = '" + this.id + "' ";
		
		System.out.println("xuat :"+ query);
		ResultSet rs =  db.get(query);
		try
        {
            rs.next();        	
            this.cttbId = rs.getString("cttbId");
			this.trangthai = rs.getString("trangthai");		
			this.mucdangky = rs.getString("muc");	
			rs.close();
       	}
        catch(Exception e){}
        
        this.createCttbUpdateRs();
        this.createNvbhRs();		
		this.initThoigian(); 
		
		//this.createNvbhIds();
		System.out.println("Trang thai: " + this.trangthai.equals("0"));
		if(this.trangthai.equals("0"))
		{
			this.createKhRs();
		}
		else  //Huy hoac chot
		{
			this.createKhSelectedList();
		}
	}

	public void createKhRs()
	{	
		String str = "";
		if(this.nvbhIds != null)
		{
			for(int i = 0; i < this.nvbhIds.length; i++)
				str += this.nvbhIds[i] + ",";
			
			if(str.length() > 0)
				str = str.substring(0, str.length() - 1); //cat dau phay cuoi cung
		}
		
		if(this.cttbId.trim().length() > 0 && this.mucdangky.trim().length() > 0 )
		{
			String query =  " 	SELECT PK_SEQ AS KHACHHANG_FK, SMARTID AS MA, TEN AS KHTEN, ISNULL(DIACHI, 'NA') AS DIACHI, ISNULL(DIENTHOAI, 'NA') AS DIENTHOAI, 0 AS SOXUAT, ISNULL(b.DANGKY, 0) AS DANGKY " +
							"	FROM KHACHHANG a left join DKTRUNGBAY_KHACHHANG b on a.PK_SEQ = b.KHACHHANG_FK and b.DKTRUNGBAY_FK = ( " + ( this.id.trim().length() <= 0 ? "1" : this.id ) + " )  and b.DKTRUNGBAY_FK in ( select PK_SEQ from DANGKYTRUNGBAY where NPP_FK = '" + this.nppId + "' and muc = 0 ) " +
							"	WHERE TRANGTHAI = '1' AND NPP_FK = '" + this.nppId + "' ";
			if(str.length() > 0)
				query += " AND a.pk_seq in ( select KHACHHANG_FK from KHACHHANG_TUYENBH where TBH_FK in ( select pk_seq from TUYENBANHANG where DDKD_FK in ( " + str + " ) ) ) ";
			
			this.khlist = db.get(query); 
			System.out.println("___Khoi tao khach hangxxxxx " + query);
		}
	}
		
	public int checkTgDangky()
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try 
		{
			System.out.println("__Tgkt DS: " + this.tgKtTinhds + " -- Thoi gian bat dau Tb: " + this.tgBatdau + " -- Thoi gian KT: " + this.tgKetthuc);
			
			Date tgkt = df.parse(this.tgKtTinhds);				
			Date tgbd = df.parse(this.tgBatdau);
			Date tgktTrungbay = df.parse(this.tgKetthuc);
			
			String query = "select top(1) ngayks from khoasongay where npp_fk = '"+ this.nppId + "' order by ngayks DESC";
			System.out.println(query);
			ResultSet rs = this.db.get(query);
			
			Date current;
			try
			{
				if(rs.next())
				{
					current = df.parse(rs.getString("ngayks"));
					System.out.println("1.ngaykhoaso:" + rs.getString("ngayks"));
//					current = df.parse(ngaykhoaso);
					
					if(this.type == 1) //thoi gian dang ky phai trong khoang (tg ketthuc tinhktds - tgbdtrungbay)
					{				
						if((current.compareTo(tgkt)) < 0 || (current.compareTo(tgbd) > 0) )
						{
							return -1;
						}
					}
					else
					{
						if((current.compareTo(tgkt)) < 0 || (current.compareTo(tgktTrungbay) > 0) )
						{
							return 1;
						}
					}
				}
				else
				{
					current = df.parse(this.getDateTime());
			
					if(this.type == 1) //thoi gian dang ky phai trong khoang (tg ketthuc tinhktds - tgbdtrungbay)
					{				
						if((current.compareTo(tgkt)) < 0 || (current.compareTo(tgbd) > 0) )
						{
							return -1;
						}
					}
					else
					{
						if((current.compareTo(tgkt)) < 0 || (current.compareTo(tgktTrungbay) > 0) )
						{
							return 1;
						}
						
					}
				}
			}
			catch(Exception e)
			{
				System.out.println("Exception: " + e.getMessage());
				return -2;
			}
		} 
		catch (ParseException e) 
		{ 
			System.out.println("__Ket qua check la -2, Exception: " + e.getMessage());
			return -2;
		}
		return 0;
	}
	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	public void DBclose() 
	{
		
		try {
			if(this.db != null) this.db.shutDown();
			if(this.cttrunggbay != null) this.cttrunggbay.close();
			if(this.khlist != null) this.khlist.close();
			if(this.khSelectedlist != null) this.khSelectedlist.close();
			if(this.nvbanhang != null) this.nvbanhang.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	public void setSosuatphanbo(String sosuatphanbo) {
		this.sosuatphanbo = sosuatphanbo;
	}

	public String getSosuatphanbo() {
		return this.sosuatphanbo;
	}

	public void setSosuatdaphanbo(String sosuatdaphanbo) {
	
		this.sosuatdaphanbo = sosuatdaphanbo;
	}
	
	public String getSosuatdaphanbo()
	{
		return this.sosuatdaphanbo;
	}

	
	public String getscheme() {
		
		return this.scheme;
	}

	
	public void setscheme(String scheme) {
		
		this.scheme = scheme;
	}

	
	public String getdiengiai() {
		
		return this.diengiai;
	}

	
	public void setdiengiai(String diengiai) {
		
		this.diengiai = diengiai;
	}
	
	/*
	
	boolean kiemtra_or(String cttb,String khachhang,int lan) //voi san pham khong bat buoc
	{
		String sql = "select isnull(tongtien,0) as tien,isnull(tongluong,0) as sl from NHOMSPTRUNGBAY where pk_seq ='"+ cttb +"'";
		//System.out.println("dk trung bay :"+ sql);
		ResultSet rs = db.get(sql);
		try {
			rs.next();
			float tongtien =Float.parseFloat(rs.getString("tien"))*lan;
			int soluong  =Integer.parseInt(rs.getString("sl"))*lan;
			sql ="select isnull(sum(soluong),0) as tongsoluong,isnull(sum(soluong*giamua),0)* 1.1 as tongtien from donhang_sanpham where donhang_fk in(select pk_seq from donhang where trangthai ='1' and ngaynhap >='"+ this.tgTinhds +"' and ngaynhap <='"+  this.tgKtTinhds +"' and khachhang_fk ='"+ khachhang +"')" +
				" and sanpham_fk in(select sanpham_fk from NHOMSPTRUNGBAY_SANPHAM where nhomsptrungbay_fk = '"+ cttb +"')" ;
					
		//	System.out.println("dk trung bay :"+ sql);
			ResultSet tb = db.get(sql);
			if(tb!=null)
			if(tb.next())
			{
				if(tongtien >0)
				{ 
					if(tongtien <= Float.parseFloat(tb.getString("tongtien")))//neu so tien 
						return true;
				}
				else
				{
					//System.out.println("so luong:"+ soluong +" : tongsoluong:"+tb.getString("tongsoluong"));
					if(soluong <= Integer.parseInt(tb.getString("tongsoluong"))) //neu la so luong
					  return true;
				}
			}
			else
				return false;
			tb.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	boolean kiemtra_and(String cttb,String khachhang, int lan)
	{
		String sql ="select sanpham_fk,case when soluong is null then 0 else soluong * "+ lan +" end as soluong from NHOMSPTRUNGBAY_SANPHAM where nhomsptrungbay_fk ='"+ cttb +"'";
	//	System.out.println("dk trung bay :"+ sql);
		ResultSet rs = db.get(sql);
		if(rs!=null)
		{
			try {
				while(rs.next())
				{  String sanpham = rs.getString("sanpham_fk");
				   String soluong =rs.getString("soluong");
					
					sql = "select sum(soluong) as sl from donhang_sanpham where  donhang_fk in(select pk_seq from donhang where trangthai ='1' and ngaynhap >= '"+ this.tgTinhds +"' and ngaynhap <='"+ this.tgKtTinhds +"' and khachhang_fk ='"+ khachhang +"') " +
							" and sanpham_fk ='"+ sanpham +"' group by sanpham_fk having sum(soluong) >="+ soluong +"";
				 //  sql ="select sum(soluong) from donhang_sanpham where  donhang_fk in(select pk_seq from donhang where ngaynhap >= '2012-01-01' and ngaynhap <='2012-02-30' and khachhang_fk ='247979')  and sanpham_fk ='100900' group by sanpham_fk having sum(soluong) >=82";
					ResultSet tb = db.get(sql);
			System.out.println("dk trung bay :"+ tb.getRow()+"------- Tinnh Trung bay :  "+sql);
					
					if(tb !=null)
						if(!tb.next())
							return false;
					tb.close();
				}
				rs.close();
			} catch(Exception e) {
				
				e.printStackTrace();
				return false;
			}
		}	
		
		return true;
	}
	
	int loaict(String cttb)
	{    
		int st = 0;
		String sql = "select * from NHOMSPTRUNGBAY where pk_seq ='"+ cttb +"'";
		ResultSet rs = db.get(sql);
		if(rs != null)
		{
			try 
			{
				rs.next();
				st = Integer.parseInt(rs.getString("loai"));
				rs.close();
			} 
			catch(Exception e) 
			{	
				e.printStackTrace();
			}
			
		}
		return st;
	}
	*/
	
	public static void main(String[] args) {
	
		
		Dangkytrungbay tb = new Dangkytrungbay("");
		tb.tgTinhds = "2012-07-01";
		tb.tgKtTinhds = "2012-07-31";
		
		//System.out.println("Tong so xuat duoc huong" + tb.getTinh("100016", "460429"));
	}

	/*
	public int getTinh(String cttb_fk, String khachhang) 
	{
		int soxuat =0;
		if(cttbId != null)
		{
		String sql ="select * from CTTB_NHOMSPTRUNGBAY where cttrungbay_fk ='"+ cttb_fk +"' order by thutudieukien";
		//System.out.println(sql);
		ResultSet dieukien = db.get(sql);
		String madk[] = new String[100];
		int i = 0;
		
		String chuoidk="";
		
			if(dieukien!=null)
				try {
					while(dieukien.next())
					{
						chuoidk = chuoidk + dieukien.getString("nhomsptrungbay_fk") +",";
						if(Integer.parseInt(dieukien.getString("pheptoan"))==0)
						{
							madk[i] = chuoidk; //moi mang la mot chuoi cac dieu kien ANH lien tiep
							i++;
							chuoidk ="";
						}
						
					}//while
					if(i==0 && chuoidk.length()>0)
						madk[i] = chuoidk;
					dieukien.close();
					int j = 0;
					
					
					while(j<=i && chuoidk.length() >0)
						{
						
						  // System.out.println("chuoi ma dk:"+madk[j]+"-----------------------------------");
							String arr[] = madk[j].split(",");//lay ra cac dieu kien co cung phep toan AND lien tieo
							boolean flag = true;
							int lan = 1;
							while(flag)
							{	int h = 0;
							
								while(h<arr.length && flag)
									{
										
											int loai = loaict(arr[h]);
											if(loai==2)//dieu kien or 
											{ //System.out.println("so luong:"+kiemtra_or(arr[h],khachhang,lan));
												if(!kiemtra_or(arr[h],khachhang,lan))//neu la tong luong hoac tong tien
												{   
													flag = false;
													break;
												}
											}
											else
											{  //neu la san pham bat buoc
												if(!kiemtra_and(arr[h],khachhang,lan))
												{
													flag = false;
													break;
												}
											}
											
											h++;	
											
									}//end while
								
								if(flag)lan++;//neu thoa thi tiep tuc
								
								if(lan > soxuat && lan >1 )
								{
									soxuat = lan;
								//	System.out.println("so xuat:"+ soxuat);
								}
							}//end while
							j++;//tang dieu kien or tiep theo
							
							
						}//end while
						
				} catch(Exception e) {
					
					e.printStackTrace();
				}

		if(soxuat >1)
			soxuat = soxuat -1;
		}
		return soxuat;	
	}
*/

	public void setdangkythem(String dangkythem) 
	{
		this.dangkythem = dangkythem;
	}

	public String getdangkythem() 
	{	
		return this.dangkythem;
	}

	public String getIstinhds() 
	{
		return this.istinhds;
	}

	public void setIstinhds(String tinhds) 
	{
		this.istinhds = tinhds;
	}
	
	public String getNhomTbId()
	{
		return nhomTbId;
	}

	public void setNhomTbId(String nhomTbId)
	{
		this.nhomTbId = nhomTbId;
	}

	
	public String getThoigianDk()
	{
		return this.tgBatdauDk;
	}

	
	public void setThoigianDk(String thoigiandk)
	{
		this.tgBatdauDk=thoigiandk;
	}

	
	public String getThoigianKtDk()
	{
		return this.tgKetthucDk;
	}

	
	public void setThoigianKtDk(String thoigianKtDk)
	{
		this.tgKetthucDk=thoigianKtDk;
	}

	
	public String getMucdangky() {
		
		return this.mucdangky;
	}

	
	public void setMucdangky(String mucdangky) {
		
		this.mucdangky = mucdangky;
	}
	
}