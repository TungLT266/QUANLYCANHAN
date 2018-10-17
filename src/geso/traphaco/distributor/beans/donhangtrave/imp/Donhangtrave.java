package geso.traphaco.distributor.beans.donhangtrave.imp;

import geso.traphaco.distributor.beans.donhangtrave.IDonhangtrave;
import geso.traphaco.distributor.beans.donhangtrave.ISanpham;
import geso.traphaco.distributor.beans.donhangtrave.imp.Sanpham;

import geso.traphaco.distributor.db.sql.*;
import geso.traphaco.distributor.util.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Hashtable;

public class Donhangtrave implements IDonhangtrave 
{	
	private static final long serialVersionUID = -9217977546733610214L;

	String userId;
	String id; //ma don hang
	String ngaygiaodich;
	String daidienkd;
	String trangthai;
	String ngaytao;
	String nguoitao;
	String ngaysua;
	String nguoisua;
	String chietkhau;
	String tongchietkhau;
	String VAT;
	String msg;
	
	String nppId;
	String nppTen;
	String sitecode;
	
	ResultSet ddkdlist;
	String ddkdId;
	
	ResultSet gsbhList;
	String gsbhId;
	String smartId;
	String khTen;
	ResultSet khlist;
	String khId;
	
	ResultSet kholist;
	String khoId;
	
	List<ISanpham> splist;
	
	String tongtientruocVAT;
	String tongtiensauVAT;
	
	Hashtable<String, Integer> spThieuList;
	
	///trakhuyen mai
	Hashtable<String, Float> scheme_tongtien = new Hashtable<String, Float>();
	Hashtable<String, Float> scheme_chietkhau = new Hashtable<String, Float>();
	List<ISanpham> scheme_sanpham = new ArrayList<ISanpham>();
	
	String dhId;
	
	dbutils db;
	
	public Donhangtrave(String[] param)
	{
		this.id = param[0];
		this.khId = param[1];
		this.ngaygiaodich = param[2];
		this.nppTen = param[3];
		this.daidienkd = param[4];
		this.trangthai = param[5];
		this.ngaytao = param[6];
		this.nguoitao = param[7];
		this.ngaysua = param[8];
		this.nguoisua = param[9];	
		this.VAT = param[10];
		this.ddkdId = param[11];
		
		this.gsbhId = "";
		this.chietkhau = "";
		this.tongchietkhau = "";
		this.khoId = "";
		this.dhId = "";
		this.msg = "";
		this.spThieuList = new Hashtable<String, Integer>();
		
		db = new dbutils();
		
	}
	
	public Donhangtrave(String id)
	{
		this.id = id;
		this.khId = "";
		this.ngaygiaodich = "";
		this.nppTen = "";
		this.daidienkd = "";
		this.trangthai = "";
		this.ngaytao = "";
		this.nguoitao = "";
		this.ngaysua = "";
		this.nguoisua = "";	
		this.VAT = "";
		this.ddkdId = "";
		this.gsbhId = "";
		this.chietkhau = "";
		this.tongchietkhau = "";
		this.tongtiensauVAT = "0";
		this.tongtientruocVAT ="0";
		this.khoId = "";
		this.msg = "";
		this.khTen = "";
		this.smartId = "";

		this.spThieuList = new Hashtable<String, Integer>();
		
		this.dhId = "";
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
	
	public String getSmartId() 
	{		
		return this.smartId;
	}

	public void setSmartId(String smartId) 
	{
		this.smartId = smartId;		
	}

	public String getKhTen() 
	{		
		return this.khTen;
	}

	public void setKhTen(String khTen) 
	{
		this.khTen = khTen;		
	}

	public String getId() 
	{
		return this.id;
	}
	
	public void setId(String id) 
	{
		this.id = id;		
	}
	
	public String getKhId() 
	{	
		return this.khId;
	}

	public void setKhId(String khId) 
	{
		this.khId = khId;
	}
	
	public String getNgaygiaodich() 
	{	
		return this.ngaygiaodich;
	}

	public void setNgaygiaodich(String ngaygiaodich) 
	{
		this.ngaygiaodich = ngaygiaodich;		
	}
	
	public String getDaidienkd() 
	{	
		return this.daidienkd;
	}
	
	public void setDaidienkd(String daidienkd) 
	{
		this.daidienkd = daidienkd;		
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
	
	public String getChietkhau() 
	{
		if(this.chietkhau.length() <= 0)
			this.chietkhau = "0";
		return this.chietkhau;
	}
	
	public void setChietkhau(String chietkhau) 
	{
		this.chietkhau = chietkhau;		
	}
	
	public String getVAT() 
	{
		if(this.VAT == "")
			this.VAT = "10";
		return this.VAT;
	}
	
	public void setVAT(String vat) 
	{
		this.VAT = vat;	
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
	
	public ResultSet getDdkdList() 
	{	
		return this.ddkdlist;
	}
	
	public void setDdkdList(ResultSet ddkdList)
	{
		this.ddkdlist = ddkdList;		
	}
	
	public String getDdkdId() 
	{		
		return this.ddkdId;
	}

	public void setDdkdId(String ddkdId) 
	{
		this.ddkdId = ddkdId;	
	}
	
	public List<ISanpham> getSpList()
	{	
		return this.splist;
	}
	
	public void setSpList(List<ISanpham> splist) 
	{
		this.splist = splist;
	}
		
	public String getTongtientruocVAT() 
	{		
		return this.tongtientruocVAT;
	}
	
	public void setTongtientruocVAT(String tttvat) 
	{
		this.tongtientruocVAT = tttvat;		
	}

	public String getTongtiensauVAT()
	{		
		return this.tongtiensauVAT;
	}
	
	public void setTongtiensauVAT(String ttsvat) 
	{
		this.tongtiensauVAT = ttsvat;		
	}
	
	public String getGsbhId() 
	{
		return this.gsbhId;
	}

	public void setGsbhId(String gsbhId) 
	{
		this.gsbhId = gsbhId;
	}

	public ResultSet getKhList() 
	{
		return this.khlist;
	}

	public void setKhList(ResultSet khlist) 
	{
		this.khlist = khlist;
	}
	
	public Hashtable<String, Integer> getSpThieuList() 
	{
		return this.spThieuList;
	}
	
	public void setSpThieuList(Hashtable<String, Integer> spThieuList) 
	{
		this.spThieuList = spThieuList;
	}
	
	//tra km
	public Hashtable<String, Float> getScheme_Tongtien() 
	{
		return this.scheme_tongtien;
	}

	public void setScheme_Tongtien(Hashtable<String, Float> scheme_tongtien) 
	{
		this.scheme_tongtien = scheme_tongtien;
	}

	public Hashtable<String, Float> getScheme_Chietkhau() 
	{
		return this.scheme_chietkhau;
	}

	public void setScheme_Chietkhau(Hashtable<String, Float> scheme_chietkhau) 
	{
		this.scheme_chietkhau = scheme_chietkhau;
	}
	
	public List<ISanpham> getScheme_SpList() 
	{
		return this.scheme_sanpham;
	}

	public void setScheme_SpList(List<ISanpham> splist) 
	{
		this.scheme_sanpham = splist;
	}
	
	private void getNppInfo()
	{		
		ResultSet rs = this.db.get("select a.pk_seq, a.ten, a.sitecode from nhaphanphoi a, nhanvien b where b.dangnhap = a.ma and b.pk_seq ='" + this.userId + "'");
		try{
			if (!(rs == null)){
				rs.next();
				this.nppId = rs.getString("pk_seq");
				this.nppTen = rs.getString("ten");
				this.sitecode = rs.getString("sitecode");
				rs.close();
			}else
			{
				this.nppId = "";
				this.nppTen = "";
				this.sitecode = "";				
			}
			
		}catch(Exception e){}			
	}

	
	String kenh()
	{ 
		String sql ="select kbh_fk from giamsatbanhang where pk_seq ='"+ this.gsbhId +"'";
	   ResultSet rs = db.get(sql);
	   if(rs != null)
	   {
		   try 
		   {
			   rs.next();
			   return rs.getString("kbh_fk");
		   } 
		  catch(Exception e) {}
	   }
	   return "null";
	}
	
	public void createRS() 
	{
		this.getNppInfo();
		this.createDdkd();	
		this.createKho();		
	}
	
	private void createDdkd()
	{
		//tao gsbh
		String sql ="select a.pk_seq,a.ten from giamsatbanhang a inner join NHAPP_GIAMSATBH b on a.pk_seq = b.gsbh_fk where ngaybatdau <='"+this.getDateTime()+"' and ngayketthuc >='"+getDateTime()+"' and  npp_fk ='"+ this.nppId +"'";
		this.gsbhList = db.get(sql);
		
		String query = "select ten as ddkdTen, pk_seq as ddkdId from daidienkinhdoanh where trangthai=1 and npp_fk ='"+ this.nppId +" ' and pk_seq in (select ddkd_fk from ddkd_gsbh where gsbh_fk in (select gsbh_fk from nhapp_giamsatbh where ngaybatdau <='"+this.getDateTime()+"' and ngayketthuc >='"+getDateTime()+"' and gsbh_fk ='"+ this.gsbhId +"' and npp_fk = '" + this.nppId + "') )";
		this.ddkdlist = db.get(query);
	}
	
	private void createKhRs()
	{
		String query = "select a.pk_seq as khId, a.ten as khTen, a.diachi, ISNULL(b.CHIETKHAU,0) as chietkhau ";
		query = query + "from KHACHHANG a left join MUCCHIETKHAU b on a.CHIETKHAU_FK = b.PK_SEQ where a.npp_fk = '" + this.nppId + "' ";

//		if(this.tbhId.length() > 0)
//			query = query + "and a.PK_SEQ in (select distinct khachhang_fk from KHACHHANG_TUYENBH where TBH_FK = '" + this.tbhId + "')";
		
		//System.out.println("Tao Khach Hang list:" + query);
		this.khlist = db.get(query);
		
	}
	
	private void createKho()
	{
		this.kholist = db.get("select distinct PK_SEQ as khoId, Ten, Diengiai from KHO where PK_SEQ in (select kho_fk from NHAPP_KHO where npp_fk = '" + this.nppId + "')");
	}
	
	
	public void init() 
	{
		this.getNppInfo();

		String query = "select a.pk_seq as dhtvId, a.ngaynhap, a.trangthai, a.ngaytao, a.ngaysua, a.khachhang_fk as khId, a.gsbh_fk as gsbhId, a.donhang_fk, g.ten as khTen, g.smartid, a.kho_fk as khoId, b.ten as nguoitao, c.ten as nguoisua, e.pk_seq as ddkdId, e.ten as ddkdTen, f.ten as nppTen";
		query = query + " from donhangtrave a left join nhanvien b on a.nguoitao = b.pk_seq left join nhanvien c on a.nguoisua = c.pk_seq inner join daidienkinhdoanh e on a.ddkd_fk = e.pk_seq inner join nhaphanphoi f on a.npp_fk = f.pk_seq";
		query = query + " inner join khachhang g on a.khachhang_fk=g.pk_seq ";
		query = query + " where a.npp_fk='" + this.nppId + "' and a.pk_seq='" + this.id + "'";
	
        ResultSet rs =  db.get(query);
        try
        {
            rs.next();
            this.id = rs.getString("dhtvId");
			this.khId = rs.getString("khId");
			this.khTen = rs.getString("khTen");
			this.smartId = rs.getString("smartId").substring(rs.getString("smartId").indexOf("_")+1, rs.getString("smartId").length());
			this.ngaygiaodich = rs.getString("ngaynhap");
			
			this.nppTen = rs.getString("nppTen");
			this.daidienkd = rs.getString("ddkdTen");
			this.trangthai = rs.getString("trangthai");
			this.ngaytao = rs.getString("ngaytao");
			this.nguoitao = rs.getString("nguoitao");
			this.ngaysua = rs.getString("ngaysua");
			this.nguoisua = rs.getString("nguoisua");
			this.VAT = "10";
			this.ddkdId = rs.getString("ddkdId");
			this.khoId = rs.getString("khoId");
			
			this.gsbhId = "";
			if(rs.getString("gsbhId") != null)
				this.gsbhId = rs.getString("gsbhId");
			this.dhId = "";
			if(rs.getString("donhang_fk") != null)
				this.dhId = rs.getString("donhang_fk");
	
			rs.close();
       	}
        catch(Exception e){}
        
        this.createDdkd();
		this.createKhRs();
        this.createKho();
        this.CreateSpList();
	}
		
	public String getKhoId() 
	{
		return this.khoId;
	}

	public void setKhoId(String khoId) 
	{
		this.khoId = khoId;
	}

	public ResultSet getKhoList() 
	{
		return this.kholist;
	}

	public void setKhoList(ResultSet kholist) 
	{
		this.kholist = kholist;
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
			if(!(this.ddkdlist == null))
				this.ddkdlist.close();
			if(this.db != null)
				this.db.shutDown();
		} 
		catch(Exception e) {}
	}

	public ResultSet getGsbhList()
	{	
		return this.gsbhList;
	}
	
	public void setGsbhList(ResultSet gsbhList) 
	{
		this.gsbhList = gsbhList;
	}
	
	public String getTongChietKhau() 
	{
		return this.tongchietkhau;
	}

	public void setTongChietKhau(String tck) 
	{
		this.tongchietkhau = tck;
	}
	
	public boolean CreateDhtv(List<ISanpham> splist) 
	{		
		dbutils db = new dbutils();
		this.ngaytao = getDateTime();
		this.nguoisua = this.userId;
		
		String msg = checkNgayTraHang();
		if(msg.length() > 0)
		{
			this.msg = msg;
			return false;
		}
		
		if(this.spThieuList.size() > 0)
		{
			this.msg = "Vui long kiem Tra Lai So Luong cac mat hang muon tra ve...";
			return false;
		}	
		
		try 
		{
			db.getConnection().setAutoCommit(false);		
		
			String kbh = kenh();
			String query = "insert into donhangtrave(ngaynhap, trangthai, ngaytao, ngaysua, nguoitao, nguoisua, ddkd_fk, khachhang_fk, npp_fk, vat, gsbh_fk, kho_fk, kbh_fk) " ;
			query = query + "values('" + this.ngaygiaodich + "','0','" + this.ngaytao + "','" + this.ngaytao + "','" + this.nguoisua +"','" + this.nguoisua +"','" + this.ddkdId + "','" + this.khId + "','" + this.nppId + "','" + this.VAT + "', '" + this.gsbhId + "', '" + this.khoId + "', '" + kbh + "')";
			if(!db.update(query)){
				db.update("rollback");
				this.msg = "Khong the tao moi 'DonHangTraVe' , " + query;
				return false; 
			}
		
			if(splist.size() > 0)
			{
				query = "select IDENT_CURRENT('donhangtrave') as dhId";
				
				ResultSet rsDh = this.db.get(query);
				rsDh.next();
				this.id = rsDh.getString("dhId");
				rsDh.close();
				
				for(int m=0; m < splist.size(); m++)
				{
					ISanpham sp = splist.get(m);
					String pk_seq = "";
					ResultSet rs = db.get("select pk_seq from sanpham where ma='" + sp.getMasanpham() + "'");
					try 
					{
						rs.next();
						pk_seq = rs.getString("pk_seq");
						rs.close();
					} 
					catch(Exception e) {
						db.update("rollback");
						this.msg = "Loi khi cap nhat, " + e.toString();
						return false; 
					}
					if(pk_seq != "")
					{
						query = "insert into donhangtrave_sanpham(sanpham_fk, donhangtrave_fk, soluong, kho_fk, giamua) values('" + pk_seq + "','" + this.id + "','" + sp.getSoluong() + "','100004','" + sp.getDongia() + "')";
						if(!db.update(query))
						{
							db.update("rollback");
							this.msg = "Loi khi cap nhat bang donhangtrave_sanpham, " + query;
							return false; 
						}
					}
				}
				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
			}
		}
		catch(Exception e1) {
			e1.printStackTrace();
			db.update("rollback");
			this.msg = "Loi khi cap nhat bang "+e1.toString();
			return false; 
		}
		return true;
	}
	
	private String checkNgayTraHang() 
	{
		String msg = "";
		msg = this.checkNgaykhoaso();
		if(msg.length() > 0)
			return msg;
		msg = this.checkNgaytradh();
		if(msg.length() > 0)
			return msg;
		
		return "";
	}

	public boolean UpdateDhtv(List<ISanpham> splist)
	{
		dbutils db = new dbutils();
		this.ngaysua = getDateTime();
		this.nguoisua = this.userId;
		
		String msg = checkNgayTraHang();
		if(msg.length() > 0)
		{
			this.msg = msg;
			return false;
		}
		
		if(this.spThieuList.size() > 0)
		{
			this.msg = "Sorry, Kiem Tra Lai So Luong cac mat hang muon tra ve...";
			return false;
		}
		
		try 
		{
			db.getConnection().setAutoCommit(false);
		
			String kbh = kenh();
			String query = "update donhangtrave set ngaynhap='" + this.ngaygiaodich + "', ddkd_fk='"+ this.ddkdId + "', khachhang_fk='" + this.khId + "', gsbh_fk = '" + this.gsbhId + "', kho_fk = '" + this.khoId + "', kbh_fk = '" + kbh + "', ngaysua = '" + this.ngaysua + "', nguoisua = '" + this.nguoisua + "' where pk_seq = '" + this.id + "'" ;
			if(!db.update(query))
			{
				db.update("rollback");
				this.msg = "Khong the cap nhat table 'DonHangTraVe' , " + query;
				return false; 
			}
		
			if(splist.size() > 0)
			{
				//delete san pham cu trong bang donhangtrave_sanpham
				query = "delete from donhangtrave_sanpham where donhangtrave_fk= '" + this.id + "'" ;
				if(!db.update(query)){
					db.update("rollback");
					this.msg = "Loi khi cap nhat bang "+query;
					return false; 
				}
				
				for(int m=0; m < splist.size(); m++)
				{
					ISanpham sp = splist.get(m);
					String pk_seq = "";
					ResultSet rs = db.get("select pk_seq from sanpham where ma='" + sp.getMasanpham() + "'");
					try 
					{
						rs.next();
						pk_seq = rs.getString("pk_seq");
						rs.close();
					} 
					catch(Exception e) {
						db.update("rollback");
						this.msg = "Loi khi cap nhat bang "+e.toString();
						return false; 
					}
					if(pk_seq != "")
					{
						String query2 = "insert into donhangtrave_sanpham(sanpham_fk, donhangtrave_fk, soluong, kho_fk, giamua) values('" + pk_seq + "','" + this.id + "','" + sp.getSoluong() + "','100004','" + sp.getDongia() + "')";
						if(!db.update(query2))
						{
							db.update("rollback");
							this.msg = "Loi khi cap nhat bang donhang_sanpham, " + query2;
							return false; 
						}
					}
				}
				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
			}
		} 
		catch(Exception e1) {
			e1.printStackTrace();
			db.update("rollback");
			this.msg = "Loi khi cap nhat bang "+e1.toString();
			return false;
		}
		return true;
	}

	public void CreateSpList()
	{		
		String command = "";
		if(this.dhId.length() > 0)
		{
			command = "select DISTINCT b.pk_seq as spId, b.ma as spMa, b.ten as spTen, a.soluong, isnull(c.donvi, 'Chua xac dinh') as donvi, a.giamua as dongia ";
			command = command + "from donhang_sanpham a inner join sanpham b on a.sanpham_fk = b.pk_seq left join donvidoluong c on b.dvdl_fk = c.pk_seq ";
			command = command + " where a.donhang_fk = '" + this.dhId + "'";
		}
		else
		{
			command = "select DISTINCT b.pk_seq as spId, b.ma as spMa, b.ten as spTen, a.soluong, isnull(c.donvi, 'Chua xac dinh') as donvi, a.giamua as dongia ";
			command = command + "from donhangtrave_sanpham a inner join sanpham b on a.sanpham_fk = b.pk_seq left join donvidoluong c on b.dvdl_fk = c.pk_seq ";
			command = command + " where a.donhangtrave_fk = '" + this.id + "'";
		}

		ResultSet splistRs = db.get(command);
		float tonggiatri = 0f; 
		List<ISanpham> splist = new ArrayList<ISanpham>();
		if(splistRs != null)
		{
			String[] param = new String[8];
			ISanpham sp = null;	
			try 
			{
				while(splistRs.next())
				{
					param[0] = splistRs.getString("spId");
					param[1] = splistRs.getString("spma");
					param[2] = splistRs.getString("spten");
					param[3] = splistRs.getString("soluong");
					param[4] = splistRs.getString("donvi");
					param[5] = splistRs.getString("dongia");
					param[6] = "";
					param[7] = "";
					
					tonggiatri += Float.parseFloat(param[5]) * Float.parseFloat(param[3]);
					
					sp = new Sanpham(param);
					splist.add(sp);
				}
			} 
			catch(Exception e) {}
		}

		if(this.dhId.length() > 0) //hien thi san pham khuyen mai
		{
			//spkm
			String query = "select d.pk_seq as spId, a.spMa, d.ten as spten, dv.donvi, a.soluong, a.tonggiatri, b.scheme, (a.tonggiatri / a.soluong) as dongia ";
			query += "from donhang_ctkm_trakm a inner join ctkhuyenmai b on a.ctkmid = b.pk_seq left join sanpham d on a.spMa = d.ma left join donvidoluong dv on dv.pk_seq = d.dvdl_fk ";
			query += "where a.donhangId = '" + this.dhId + "' and a.spMa is not null";
			
			ResultSet spkmlistRs = db.get(query);
			if(spkmlistRs != null)
			{
				String[] param = new String[8];
				ISanpham sp = null;	
				try 
				{
					while(spkmlistRs.next())
					{
						param[0] = spkmlistRs.getString("spId");
						param[1] = spkmlistRs.getString("spma");
						param[2] = spkmlistRs.getString("spten");
						param[3] = spkmlistRs.getString("soluong");
						param[4] = spkmlistRs.getString("donvi");
						param[5] = spkmlistRs.getString("dongia");
						param[6] = spkmlistRs.getString("scheme");
						param[7] = "";
			
						sp = new Sanpham(param);
						splist.add(sp);
					}
					spkmlistRs.close();
				} 
				catch(Exception e) {}
			}
		}
		
		this.splist = splist;
	}

	public String getDonhangId() 
	{
		return this.dhId;
	}

	public void setDonhangId(String dhId) 
	{
		this.dhId = dhId;
	}
	
	
	String ngaykhoaso = "";
	private String checkNgaykhoaso() 
	{
		//don hang phai sau ngay khoa so gan nhat
		
		String query = "select max(ngayks) as nks from khoasongay where npp_fk = '" + this.nppId + "'";
		ResultSet rsKs = db.get(query);
		
		if(rsKs != null)
		{
			try 
			{
				if(rsKs.next())
				{
					ngaykhoaso = rsKs.getString("nks");
					rsKs.close();
				}
			} 
			catch(Exception e) {}
		}
		
		//String[] ngaythang = ngaykhoaso.split("-");
		//int ngay = Integer.parseInt(ngaythang[2]) + 1;
		
		//String ngayTang1 = ngaythang[0] + "-" + ngaythang[1] + "-" + Integer.toString(ngay);
		
		query = "SELECT DATEDIFF(day, '" + ngaykhoaso + "', '" + this.ngaygiaodich + "') AS songay";
		System.out.println("Query la: " + query + "\n");
		
		ResultSet rs = db.get(query);
		
		int songay = 0;
		String msg = "";
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					songay = rs.getInt("songay");
				}
				rs.close();
			} 
			catch(Exception e) {}
		}
		
		if(songay < 1)
			msg = "Bạn chỉ được phép trả những đơn hàng sau ngày khóa sổ gần nhất 1 ngay";
		return msg;
	}
	
	private String checkNgaytradh() 
	{
		String msg = "";
		String ngaynhap = this.ngaygiaodich;
		
		String[] nn = ngaynhap.trim().split("-");
		String[] date = this.getDateTime().trim().split("-");
		if(ngaykhoaso.length() > 0)
			date = this.ngaykhoaso.trim().split("-");
		
		
		
		return msg;
	}


}
