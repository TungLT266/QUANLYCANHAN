package geso.traphaco.distributor.beans.phieuxuatkho.imp;

import java.io.Serializable;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.beans.donhang.ISanpham;
import geso.traphaco.distributor.beans.donhang.imp.Sanpham;
import geso.traphaco.distributor.beans.phieuxuatkho.imp.Phieuxuatkho;
import geso.traphaco.distributor.beans.phieuxuatkho.IPhieuxuatkho;
import geso.traphaco.distributor.db.sql.dbutils;

public class Phieuxuatkho implements IPhieuxuatkho, Serializable
{
	private static final long serialVersionUID = -9217977546733610214L;

	String userId;
	String id;
	String trangthai;
	String ngaylapphieu;
	String ngaytao;
	String nguoitao;
	String ngaysua;
	String nguoisua;
	String msg;
	String ngaykhoaso;
	ResultSet nhanviengn;
	String nvgnId;
	String nvgnTen;
	
	ResultSet nvbanhang;
	String nvbhId;
	ResultSet tuyenbanhang;
	String tbhId;
	
	ResultSet dhList;
	ResultSet dhIdsList;
	String[] dhIds;

	List<ISanpham> pxk_spList;
	List<ISanpham> pxk_spkmList;
	List<ISanpham> pxk_tienkmList;
	ResultSet spkmList;
	ResultSet tienkmList;
	Hashtable<String, Long> credits;
	String nppId;
	String nppTen;
	String sitecode;
	
	ResultSet spThieu;
		
	dbutils db;
	
	public Phieuxuatkho(String[] param)
	{
		this.id = param[0];
		this.trangthai = param[1];
		this.ngaytao = param[2];
		this.nguoitao = param[3];
		this.ngaysua = param[4];
		this.nguoisua = param[5];
		this.nvgnId = param[6];
		this.ngaylapphieu = param[7];
		this.nvgnTen = "";
		this.nvbhId = "";
		this.tbhId = "";
		this.msg = "";
		this.pxk_spList = new ArrayList<ISanpham>();
		this.pxk_spkmList = new ArrayList<ISanpham>();
		db = new dbutils();
	}
	
	public Phieuxuatkho(String id)
	{
		this.id = id;
		this.trangthai = "";
		this.ngaytao = "";
		this.nguoitao = "";
		this.ngaysua = "";
		this.nguoisua = "";
		this.nvgnId = "";
		this.ngaylapphieu = "";
		this.nvgnTen = "";
		this.nvbhId = "";
		this.tbhId = "";
		this.msg = "";
		this.ngaykhoaso="";
		this.pxk_spList = new ArrayList<ISanpham>();
		this.pxk_spkmList = new ArrayList<ISanpham>();
		
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
	
	public ResultSet getNhanvienGN() 
	{		
		return this.nhanviengn;
	}
	
	public void setNhanvienGN(ResultSet nhanviengn) 
	{
		this.nhanviengn = nhanviengn;		
	}
	
	public String getNvgnId() 
	{		
		return this.nvgnId;
	}
	
	public void setNvgnId(String nvgnId) 
	{
		this.nvgnId = nvgnId;		
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
	
	public ResultSet getDonhangList() 
	{
		return this.dhList;
	}

	public void setDonhangList(ResultSet dhlist) 
	{
		this.dhList = dhlist;
	}

	public String getNgaylap() 
	{
		return this.ngaylapphieu;
	}

	public void setNgaylap(String ngaylap) 
	{
		this.ngaylapphieu = ngaylap;
	}

	public Hashtable<Integer, String> getDonhangIds() 
	{
		Hashtable<Integer, String> select = new Hashtable<Integer, String>();
		if(this.dhIds != null){
			int size = (this.dhIds).length;
			int m = 0;
			while(m < size){
				select.put(new Integer(m), this.dhIds[m]) ;
				m++;
			}
		}else{
			select.put(new Integer(0), "null");
		}
		return select;
	}

	public void setDonhangIds(String[] donhangIds) 
	{
		this.dhIds = donhangIds;
	}
	
	public String[] getDhIds() 
	{
		return this.dhIds;
	}
	
	public String getNvgnTen() 
	{
		return this.nvgnTen;
	}

	public void setNvgnTen(String nvgnTen)
	{
		this.nvgnTen = nvgnTen;
	}
	
	public ResultSet getNvBanhang() 
	{		
		return this.nvbanhang;
	}
	
	public void setNvBanhang(ResultSet nvbanhang) 
	{
		this.nvbanhang = nvbanhang;		
	}
	
	public ResultSet getSpkmList() 
	{		
		return this.spkmList;
	}
	
	public void setSpkmList(ResultSet spkmList) 
	{
		this.spkmList = spkmList;		
	}

	public ResultSet getTienkmList() 
	{		
		return this.tienkmList;
	}
	
	public void setTienkmList(ResultSet tienkmList) 
	{
		this.tienkmList = tienkmList;		
	}

	public String getNvbhId() 
	{		
		return this.nvbhId;
	}
	
	public void setNvbhId(String nvbhId) 
	{
		this.nvbhId = nvbhId;		
	}
	
	public ResultSet getTuyenbanhang()
	{		
		return this.tuyenbanhang;
	}
	
	public void setNvTuyenhang(ResultSet tuyenbanhang) 
	{
		this.tuyenbanhang = tuyenbanhang;		
	}
	
	public String getTbhId()
	{	
		return this.tbhId;
	}
	
	public void setTbhId(String tbhId)
	{
		this.tbhId = tbhId;		
	}

	public List<ISanpham> getPxk_spList()
	{
		return this.pxk_spList;
	}

	public void setPxk_spList(List<ISanpham> spList) 
	{
		this.pxk_spList = spList;
	}

	public List<ISanpham> getPxk_spkmList() 
	{
		return this.pxk_spkmList;
	}

	public void setPxk_spkmList(List<ISanpham> spkmList) 
	{
		this.pxk_spkmList = spkmList;
	}

	public ResultSet getDhIdsList() 
	{
		return this.dhIdsList;
	}

	public void setDhIdsList(ResultSet dhIdsList) 
	{
		this.dhIdsList = dhIdsList;
	}
	
	public Hashtable<String, Long> getCredits()
	{
		this.credits = new Hashtable<String, Long>();
		String query ="select sum(a.sotienno) - isnull(sum(b.sotien),0) as credit, a.khachhang_fk as khId from khachhang_congno a left join PHIEUTHUTIEN_KH b on a.khachhang_fk = b.khachhang_fk where  a.khachhang_fk in (select pk_seq from khachhang where npp_fk='"+ this.nppId +"') group by a.khachhang_fk";
		ResultSet rs = this.db.get(query);
		
		if(rs != null){
			try{
				while(rs.next()){
					if(Double.valueOf(rs.getString("credit")).doubleValue() > 0){
						this.credits.put(rs.getString("khId"), Math.round(Double.valueOf(rs.getString("credit")).doubleValue()));
					}
				}
				
			}catch(Exception e){}
			finally{try {
				if(rs != null)
					rs.close();
			} catch (Exception e2) {
				
			}}
		}else{
			this.credits.put("0", 0L);
		}
		
		return this.credits;
	}
	
	private void getNppInfo()
	{		
		//Phien ban moi
		geso.traphaco.distributor.util.Utility util=new geso.traphaco.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();
		//this.dangnhap = util.getDangNhap();
		this.sitecode=util.getSitecode();
	}

	public boolean CreatePxk() 
	{
		this.ngaytao = getDateTime();
		this.nguoitao = this.userId;
		ResultSet rsPxk = null;
		try 
		{
			db.getConnection().setAutoCommit(false);
				
			String query = "insert into Phieuxuatkho(nvgn_fk, trangthai, ngaytao, ngaysua, nguoitao, nguoisua, npp_fk, ngaylapphieu) " ;
			query = query + "values('" + this.nvgnId + "','0','" + this.ngaytao + "','" + this.ngaytao + "','" + this.nguoitao + "','" + this.nguoitao +"','" + this.nppId +"','" + convertDate(this.ngaylapphieu) + "')";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				this.msg = "Khong the tao moi 'Phieuxuatkho', " + query;
				return false; 
			}
			
			query = "select IDENT_CURRENT('phieuxuatkho') as pxkId";
			
			rsPxk = this.db.get(query);
			rsPxk.next();
			this.id = rsPxk.getString("pxkId");
			rsPxk.close();
			
			//bang phieuxuatkho_donhang
			System.out.println("So don hang la: " + this.dhIds .length);
			if(this.dhIds != null)
			{				
				for(int m = 0; m < this.dhIds.length; m++)
				{
					float tonggiatri = 0.0f;
					
					//chua can luu tonggiatri o buoc nay
					String sql = "Insert into phieuxuatkho_donhang(pxk_fk, donhang_fk, tonggiatri) values('" + this.id + "', '" + this.dhIds[m] + "', null)";
					System.out.println("Insert duoc :" + sql);
					if(!db.update(sql))
					{
						db.update("rollback");
						this.msg = "Khong the them moi bang 'phieuxuatkho_donhang', " + sql;
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

	public boolean UpdatePxk() 
	{
		//dbutils db = new dbutils();
		this.ngaysua = getDateTime();
		this.nguoisua = this.userId;
		
		try 
		{
			db.getConnection().setAutoCommit(false);
				
			String query = "update Phieuxuatkho set nvgn_fk='" + this.nvgnId + "', ngaylapphieu='" + convertDate(this.ngaylapphieu) + "', ngaysua='" + this.ngaysua + "', nguoisua='" + this.nguoisua + "',NGAYGIOSUA=GETDATE()  where pk_seq='" + this.id + "'" ;
			if(!db.update(query))
			{
				db.getConnection().rollback();
				this.msg = "Khong the cap nhat 'Phieuxuatkho', " + query;
				return false; 
			}
	
			if(this.dhIds != null)
			{
				query = "delete from phieuxuatkho_donhang where pxk_fk='" + this.id + "'";
				db.update(query);
				
				for(int m = 0; m < this.dhIds.length; m++)
				{
					
					String sql = "Insert into phieuxuatkho_donhang(pxk_fk, donhang_fk, tonggiatri) values('" + this.id + "','" + this.dhIds[m] + "', null)";
					System.out.println("Cau lenh Insert: " + sql + "\n");
					if(!db.update(sql))
					{
						db.getConnection().rollback();
						this.msg = "Khong the cap nhat bang 'Phieuxuatkho_Donhang', " + sql;
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
	
		ResultSet rs =  db.get("select * from phieuxuatkho where pk_seq = '" + this.id + "'");
		try
        {
            rs.next();        	
            this.id = rs.getString("pk_seq");
            this.nvgnId = rs.getString("nvgn_fk");
            this.ngaylapphieu = rs.getString("ngaylapphieu");
			this.trangthai = rs.getString("trangthai");
			this.ngaytao = rs.getString("ngaytao");
			this.nguoitao = rs.getString("nguoitao");
			this.ngaysua = rs.getString("ngaysua");
			this.nguoisua = rs.getString("nguoisua");					
			rs.close();
       	}
        catch(Exception e){}
        finally{try {
			if(rs != null)
				rs.close();
		} catch (Exception e2) {
			
		}}
        this.createNvbhRs();
        this.createNvgnRs();  
        this.createDonhangList();
        this.createDonhangIds();
        createKhoasongay();
        
        this.checkSPThieu();
	}
	
	private void checkSPThieu() 
	{
		String query =  "select pxk_sp.spId, pxk_sp.spMa, ( select ten from SANPHAM where pk_seq = pxk_sp.spId ) as spTEN, " +
						"	isnull(kho_npp.soluong,0) as AVAI, pxk_sp.soluong as tongXUAT " + 
						"from " +
						"(" +
							"select khoId, kbhId, spId, spMa, sum(soluong) as soluong " +
							"from( " +
								"select c.kho_fk as khoId, c.kbh_fk as kbhId, b.pk_seq as spId, b.ma as spMa, sum(a.soluong) as soluong " +
								"from donhang_sanpham a inner join sanpham b on a.sanpham_fk = b.pk_seq inner join donhang c on a.donhang_fk = c.pk_seq " +
								"where c.trangthai != 2 and a.donhang_fk in (select donhang_fk from phieuxuatkho_donhang where pxk_fk = " + this.id + ") and a.khoNVBH = '0' " + 
								"group by c.kho_fk, c.kbh_fk, b.pk_seq, b.ma " +
							"union all " +
								"select b.kho_fk as khoId, e.kbh_fk as kbhId, d.pk_seq as spId, a.spMa, sum(a.soluong) as soluong " +
								"from donhang_ctkm_trakm a inner join ctkhuyenmai b on a.ctkmid = b.pk_seq " +
								"	inner join sanpham d on a.spMa = d.ma inner join donhang e on a.donhangId = e.pk_seq " +
								"where e.trangthai != 2 and a.spMa is not null and a.donhangId in (select donhang_fk from phieuxuatkho_donhang where pxk_fk = " + this.id + ") and a.khoNVBH = '0' " +
								"group by b.kho_fk, e.kbh_fk, a.ctkmId, a.spMa, d.pk_seq " +
							" ) a " +
							" group by khoId, kbhId, spId, spMa " +
						") " +
						"pxk_sp left join " +
						"( " +
						"	select kho_fk, npp_fk, sanpham_fk, kbh_fk, soluong " +
						"	from nhapp_kho where npp_fk = '" + this.nppId + "' " +
						") kho_npp on pxk_sp.khoId = kho_npp.kho_fk and pxk_sp.kbhId = kho_npp.kbh_fk and pxk_sp.spId = kho_npp.sanpham_fk " +
						"where (isnull(kho_npp.soluong,0) - pxk_sp.soluong) < 0";

		System.out.println("Query check chot phieu xuat kho: " + query + "\n");
		this.spThieu = db.get(query);

	}

	public void init2()
	{
		this.getNppInfo();
		
		ResultSet rs =  db.get("select a.*, b.ten as nvgnTen from phieuxuatkho a inner join nhanviengiaonhan b on a.nvgn_fk = b.pk_seq where a.pk_seq='" + this.id + "'");
		try
        {
            rs.next();        	
            this.id = rs.getString("pk_seq");
            this.nvgnId = rs.getString("nvgn_fk");
            this.nvgnTen = rs.getString("nvgnTen");
            this.ngaylapphieu = rs.getString("ngaylapphieu");
			this.trangthai = rs.getString("trangthai");
			this.ngaytao = rs.getString("ngaytao");
			this.nguoitao = rs.getString("nguoitao");
			this.ngaysua = rs.getString("ngaysua");
			this.nguoisua = rs.getString("nguoisua");					
			rs.close();
       	}
        catch(Exception e){
        	
        	System.out.println(e.toString());
        }
 	       
        this.createDonhangList();
        
       /* String query = "select b.pk_seq as dhId, b.ngaynhap, isnull(a.tonggiatri, '0') as tonggiatri,c.smartid, c.pk_seq as khId, c.ten as khTen, c.diachi as dc ";
        query += "from phieuxuatkho_donhang a inner join donhang b on a.donhang_fk = b.pk_seq inner join khachhang c on b.khachhang_fk = c.pk_seq where a.pxk_fk = '" + this.id + "' "; */
        
        String query = "select b.pk_seq as dhId, b.ngaynhap, ( (  " +
						"select sum( (soluong * giamua) - chietkhau ) as tienBvat  " +
						"from donhang_sanpham  " +
						"where donhang_fk = b.pk_seq " +
						"group by donhang_fk ) - " +
						"isnull( (select sum(tonggiatri) as giatriKM from donhang_ctkm_trakm where donhangId = b.pk_seq and SPMA is null ), '0') " +
						"- isnull( ( select sum(thanhtoan) as giatriKMTL from DUYETTRAKHUYENMAI_DONHANG where donhang_fk = b.pk_seq )	, 0 )  ) as tonggiatri, " +
						"c.smartid, c.pk_seq as khId, c.ten as khTen, c.diachi as dc  " +
				"from phieuxuatkho_donhang a inner join donhang b on a.donhang_fk = b.pk_seq inner join khachhang c on b.khachhang_fk = c.pk_seq  " +
				"where a.pxk_fk = '" + this.id + "' ";
				
		System.out.println("Cau lenh SQL lay gia tri: " + query + "\n");
		this.dhIdsList = db.get(query);
        
        System.out.println("Cau lenh SQL lay gia tri: " + query + "\n");
        //this.dhIdsList = db.get(query);
	}
	
	public void init3()
	{
		this.getNppInfo();
		
		ResultSet rs =  db.get("select a.*, b.ten as nvgnTen from phieuxuatkho a inner join nhanviengiaonhan b on a.nvgn_fk = b.pk_seq where a.pk_seq='" + this.id + "'");
		try
        {
            rs.next();        	
            this.id = rs.getString("pk_seq");
            this.nvgnId = rs.getString("nvgn_fk");
            this.nvgnTen = rs.getString("nvgnTen");
            this.ngaylapphieu = rs.getString("ngaylapphieu");
			this.trangthai = rs.getString("trangthai");
			this.ngaytao = rs.getString("ngaytao");
			this.nguoitao = rs.getString("nguoitao");
			this.ngaysua = rs.getString("ngaysua");
			this.nguoisua = rs.getString("nguoisua");					
			rs.close();
       	}
        catch(Exception e){}
 	       
        this.createDonhangList();
        
        String query = "";
        if(!this.trangthai.equals("1")) //phieuxuatkho chua chot
        {
        	this.createPxk_SpList(this.id);
            this.createPxk_SpkmList(this.id);
            this.createPxk_TienkmList(this.id);
            
        	/*query = "select  b.pk_seq as dhId, b.ngaynhap, isnull(b.tonggiatri, '0') as tonggiatri, c.pk_seq as khId,c.smartid, c.ten as khTen, c.diachi as dc ";
        	query += "from phieuxuatkho_donhang a inner join donhang b on a.donhang_fk = b.pk_seq inner join khachhang c on b.khachhang_fk = c.pk_seq where a.pxk_fk = '" + this.id + "' ";*/
        }
        else //phieu xuat kho da chot
        {
        	System.out.println("Trang thai la da chot");
        	this.createPxk_SpList(this.id, db);
            this.createPxk_SpkmList(this.id, db);
            this.createPxk_TienkmList(this.id, db);
            
        	/*query = "select b.pk_seq as dhId, b.ngaynhap, isnull(a.tonggiatri, '0') as tonggiatri, c.pk_seq as khId,c.smartid ,c.ten as khTen, c.diachi as dc ";
        	query += "from phieuxuatkho_donhang a inner join donhang b on a.donhang_fk = b.pk_seq inner join khachhang c on b.khachhang_fk = c.pk_seq where a.pxk_fk = '" + this.id + "' ";*/
        }	
        
        query = "select b.pk_seq as dhId, b.ngaynhap, ( (  " +
					"select sum( (soluong * giamua) - chietkhau ) as tienBvat  " +
					"from donhang_sanpham  " +
					"where donhang_fk = b.pk_seq " +
					"group by donhang_fk ) - " +
					"isnull( (select sum(tonggiatri) as giatriKM from donhang_ctkm_trakm where donhangId = b.pk_seq and SPMA is null ), '0') " +
					" - isnull( ( select sum(thanhtoan) as giatriKMTL from DUYETTRAKHUYENMAI_DONHANG where donhang_fk = b.pk_seq )	, 0 )  ) as tonggiatri, " +
					"c.smartid, c.pk_seq as khId, c.ten as khTen, c.diachi as dc  " +
			"from phieuxuatkho_donhang a inner join donhang b on a.donhang_fk = b.pk_seq inner join khachhang c on b.khachhang_fk = c.pk_seq  " +
			"where a.pxk_fk = '" + this.id + "' ";
        System.out.println("Cau lenh SQL lay gia tri: " + query + "\n");
        this.dhIdsList = db.get(query);
	}
		
	public void createRS()
	{
		this.getNppInfo();
		this.createNvbhRs();
		this.createNvgnRs();
		this.createDonhangList();
		createKhoasongay();
	}
	
	private void createNvgnRs()
	{
		String sql = "select pk_seq as nvgnId, ten as nvgnTen from nhanviengiaonhan ";
		sql = sql +	"where npp_fk = '" + this.nppId + "' and trangthai='1' ";	
		this.nhanviengn = db.get(sql);
	}
	
	private void createNvbhRs()
	{
		String sql = "select pk_seq as nvbhId, ten as nvbhTen from daidienkinhdoanh where npp_fk = '" + this.nppId + "' and pk_seq in ";
		
		sql = sql + "(select distinct b.ddkd_fk from khachhang_tuyenbh a inner join tuyenbanhang b on b.pk_seq = a.tbh_fk inner join nvgn_kh c on a.khachhang_fk = c.khachhang_fk where b.npp_fk = '" + this.nppId + "' ";
		if(this.nvgnId.length() > 0)
			sql = sql + "and c.nvgn_fk = '" + this.nvgnId + "'";
		sql = sql + " )";
		
		this.nvbanhang = db.get(sql);
	}
	
	private void createPxk_SpList(String pxkId)
	{
		List<ISanpham> pxk_splist = new ArrayList<ISanpham>();
		//String query = "select a.sanpham_fk as spId, b.ma as spMa, a.soluong, b.ten as spTen, c.ten as khoTen, d.ten as kbhTen from phieuxuatkho_sanpham a inner join sanpham b on a.sanpham_fk = b.pk_seq ";
		//query += " left join kho c on a.kho_fk = c.pk_seq left join kenhbanhang d on a.kbh_fk = d.pk_seq where a.pxk_fk = '" + pxkId + "'";
		
		String query = "select a.kho_fk,a.kbh_fk,a.npp_fk,c.pk_seq as spId, isnull(c.barcode,'') as barcode, c.ma as spMa, sum(b.soluong) as soluong, c.ten as spTen, d.ten as khoTen, e.ten as kbhTen,soluong1 as quicach " +
				" from donhang a inner join donhang_sanpham b on a.pk_seq = b.donhang_fk inner join sanpham c on b.sanpham_fk = c.pk_seq " +
				"left join kho d on a.kho_fk = d.pk_seq " +
				"left join kenhbanhang e on a.kbh_fk = e.pk_seq " +
				" left join quycach q on q.sanpham_fk = b.sanpham_fk and q.dvdl2_fk=100018 " +
				" left join donvidoluong  d2 on d2.pk_Seq = dvdl2_fk " +
				" left join donvidoluong d1 on d1.pk_seq = dvdl1_fk"+
				" where a.pk_seq in (select donhang_fk from phieuxuatkho_donhang  " +
				" where pxk_fk = '" + pxkId + "') " +
				 " group by a.kho_fk,a.kbh_fk,a.npp_fk , c.pk_seq, c.ma, c.ten, d.ten, e.ten,c.barcode,soluong1";
		System.out.print("\nQuery truy van du lieu laxxxxx: " + query + "\n");
		
		ResultSet rsPxk_sp = db.get(query);
		if(rsPxk_sp != null)
		{
			String[] param = new String[10];
			ISanpham sp = null;	
			try 
			{
				while(rsPxk_sp.next())
				{
					
					query=" SELECT SOLO,AVAILABLE,NGAYHETHAN "+
				 	 " FROM NHAPP_KHO_CHITIET  where SANPHAM_FK="+rsPxk_sp.getString("spId")+
			 	 	 " AND KHO_FK="+rsPxk_sp.getString("KHO_FK")+" AND KBH_FK="+rsPxk_sp.getString("KBH_FK")+ 
			 	 	 " AND NPP_FK="+rsPxk_sp.getString("NPP_FK")+" AND AVAILABLE >0 "+
				 	 " ORDER BY NGAYHETHAN ASC ";
					System.out.println("Query : "+query);
					
					ResultSet rskho=db.get(query);
				 
					  boolean chuadusl=true;
					  double sumsoluong=rsPxk_sp.getDouble("soluong");
					  double sumsoluongtronglo=0;
					  while (rskho.next() && chuadusl){
						  sp = new Sanpham(param);
						  double soluonglo=0;
						  if(sumsoluong > rskho.getDouble("AVAILABLE")){
							  soluonglo=rskho.getDouble("AVAILABLE");
							  sumsoluong=sumsoluong-soluonglo;
							  
						  }else{
							  soluonglo= sumsoluong;
							  chuadusl=false;
						  }
						
						  sumsoluongtronglo= sumsoluongtronglo +soluonglo;
						   sp=new Sanpham();
						  sp.setMasanpham(rsPxk_sp.getString("spMa"));
						  sp.setTensanpham(rsPxk_sp.getString("spTen"));
						  sp.setSoluong(Math.round(soluonglo)+"");
						  sp.setSPId(rsPxk_sp.getString("SPID"));
						  
						  sp.setSolo(rskho.getString("solo"));
						  sp.setNgayHetHan(rskho.getString("ngayhethan"));
						  sp.setQuicach(rsPxk_sp.getInt("quicach")+"");
						  sp.setBarcode(rsPxk_sp.getString("barcode"));
						  sp.setKhoTen(rsPxk_sp.getString("khoTen"));
						  
							pxk_splist.add(sp);
					  }
					  rskho.close();
				
					
				
				}
				rsPxk_sp.close();
			} 
			catch(Exception e) {
				e.printStackTrace();
				
			}
			finally{try {
				if(rsPxk_sp != null)
					rsPxk_sp.close();
			} catch (Exception e2) {
				
			}}
		}
		this.pxk_spList = pxk_splist;
	}
	
	private void createSpkmList()
	{
		//Khong dung cach nay duoc, vi cac don hang co the bi thay doi soluong KM trong qua trinh xuat kho
		//String query = "select a.spma as ma, c.ten, sum(a.soxuat) as tongsoxuat, a.ctkmid, b.diengiai, b.scheme from donhang_ctkm_trakm a, ctkhuyenmai b, sanpham c where a.spma is not null and b.pk_seq = a.ctkmid and a.spma=c.ma and a.donhangid in (select a.donhang_fk from phieuxuatkho_donhang a inner join phieuxuatkho b on a.pxk_fk = b.pk_seq where a.pxk_fk = '" + this.id + "') group by a.spma, a.ctkmid, b.diengiai, c.ten, b.scheme";
		String query = "select a.spma as ma, c.ten, sum(a.soluong), a.ctkmid, b.diengiai, b.scheme from donhang_ctkm_trakm a, ctkhuyenmai b, sanpham c where a.spma is not null and b.pk_seq = a.ctkmid and a.spma=c.ma and a.donhangid in (select a.donhang_fk from phieuxuatkho_donhang a inner join phieuxuatkho b on a.pxk_fk = b.pk_seq where a.pxk_fk = '" + this.id + "') where a.spma is not null group by a.spma, a.ctkmid, b.diengiai, c.ten, b.scheme";
		this.spkmList = this.db.get(query);
	}
	
	private void createTienkmList(){
		String query = "select sum(a.tonggiatri) as tonggiatri, a.ctkmid, b.diengiai, b.scheme from donhang_ctkm_trakm  a, ctkhuyenmai b where a.spma is null  and b.pk_seq = a.ctkmid and a.donhangid in (select a.donhang_fk from phieuxuatkho_donhang a inner join phieuxuatkho b on a.pxk_fk = b.pk_seq where a.pxk_fk = '" + this.id + "') group by a.ctkmid, b.diengiai, b.scheme";
		this.tienkmList = this.db.get(query);
	}

	private void createPxk_SpkmList(String pxkId)
	{
		List<ISanpham> pxk_spkmlist = new ArrayList<ISanpham>();
		//String query = "select c.ten as khoTen, d.ten as kbhTen, e.scheme + '-' + e.diengiai as ctkmTen, a.sanpham_fk as spId, b.ma as spMa, b.ten as spTen, sum(a.soluong) as soluong from phieuxuatkho_spkm a inner join sanpham b on a.sanpham_fk = b.pk_seq inner join kho c on a.kho_fk = c.pk_seq ";
		//query += "inner join kenhbanhang d on a.kbh_fk = d.pk_seq inner join ctkhuyenmai e on a.scheme = e.pk_seq where a.pxk_fk = '" + this.id + "' group by c.ten, d.ten, e.scheme + '-' + e.diengiai, a.sanpham_fk, b.ma, b.ten";
		
		String query = "select e.npp_fk,e.kbh_fk,b.kho_fk ,f.ten as khoTen, g.ten as kbhTen, b.scheme + '-' + b.diengiai as ctkmTen," +
				" d.pk_seq as spId, isnull(d.barcode,'') as barcode, d.ma as spMa, d.ten as spTen,  sum(a.soluong) as soluong,soluong1 as quicach " +
				"from donhang_ctkm_trakm a inner join ctkhuyenmai b on a.ctkmid = b.pk_seq " +
				" inner join sanpham d on a.spMa = d.ma inner join donhang e on a.donhangId = e.pk_seq " +
				"left join kho f on b.kho_fk = f.pk_seq left join kenhbanhang g on e.kbh_fk = g.pk_seq " +
				" left join quycach q on q.sanpham_fk = d.pk_seq and q.dvdl2_fk=100018  " +
				" left join donvidoluong  d2 on d2.pk_Seq = dvdl2_fk " +
				" left join donvidoluong d1 on d1.pk_seq = dvdl1_fk"+
				" where a.spMa is not null and a.donhangId in " +
				" (select donhang_fk from phieuxuatkho_donhang where pxk_fk = '" + pxkId + "') " +
				" group by  e.npp_fk,e.kbh_fk,b.kho_fk, f.ten, g.ten, b.scheme + '-' + b.diengiai, d.pk_seq, d.ma, d.ten,d.barcode,soluong1";
		System.out.println("xxxxxx......."+query);
		ResultSet rsPxk_spkm = db.get(query);
		if(rsPxk_spkm != null)
		{	
			try
			{
				while(rsPxk_spkm.next())
				{
					String[] param = new String[10];
					ISanpham sp = null;
					query=" SELECT SOLO,AVAILABLE,NGAYHETHAN "+
				 	 " FROM NHAPP_KHO_CHITIET  where SANPHAM_FK="+rsPxk_spkm.getString("spId")+
			 	 	 " AND KHO_FK="+rsPxk_spkm.getString("KHO_FK")+" AND KBH_FK="+rsPxk_spkm.getString("KBH_FK")+ 
			 	 	 " AND NPP_FK="+rsPxk_spkm.getString("NPP_FK")+" AND AVAILABLE >0 "+
				 	 " ORDER BY NGAYHETHAN ASC ";
					System.out.println("Query : "+query);
					
					ResultSet rskho=db.get(query);
				 
					  boolean chuadusl=true;
					  double sumsoluong=rsPxk_spkm.getDouble("soluong");
					  double sumsoluongtronglo=0;
					  while (rskho.next() && chuadusl){
						  sp = new Sanpham(param);
						  double soluonglo=0;
						  if(sumsoluong > rskho.getDouble("AVAILABLE")){
							  soluonglo=rskho.getDouble("AVAILABLE");
							  sumsoluong=sumsoluong-soluonglo;
							  
						  }else{
							  soluonglo= sumsoluong;
							  chuadusl=false;
						  }
						
						  sumsoluongtronglo= sumsoluongtronglo +soluonglo;
						   sp=new Sanpham();
						  sp.setMasanpham(rsPxk_spkm.getString("spMa"));
						  sp.setTensanpham(rsPxk_spkm.getString("spTen"));
						  sp.setSoluong(Math.round(soluonglo)+"");
						  sp.setSPId(rsPxk_spkm.getString("SPID"));
						  
						  sp.setSolo(rskho.getString("solo"));
						  sp.setNgayHetHan(rskho.getString("ngayhethan"));
						  sp.setQuicach(rsPxk_spkm.getInt("quicach")+"");
						  sp.setBarcode(rsPxk_spkm.getString("barcode"));
						  sp.setKhoTen(rsPxk_spkm.getString("khoTen"));
						  sp.setCTKM(rsPxk_spkm.getString("ctkmTen") );
						  pxk_spkmlist.add(sp);
					  }
					  rskho.close();
				/*	param[0] = rsPxk_spkm.getString("spId");					
					param[1] = rsPxk_spkm.getString("spMa");		
					param[2] = rsPxk_spkm.getString("spTen");
					param[3] = rsPxk_spkm.getString("soluong");
					
					//luu kho
					param[4] = "";
					if(rsPxk_spkm.getString("khoTen") != null)
						param[4] = rsPxk_spkm.getString("khoTen");
					
					//luu kenh ban hang
					param[5] = "";
					if(rsPxk_spkm.getString("kbhTen") != null)
						param[5] = rsPxk_spkm.getString("kbhTen");

					//luu ten ctkm
					param[6] = "";
					if(rsPxk_spkm.getString("ctkmTen") != null)
						param[6] = rsPxk_spkm.getString("ctkmTen");
					
					param[7] = "";
					param[8]= rsPxk_spkm.getString("barcode");
					param[9]=String.valueOf(rsPxk_spkm.getInt("quicach"));
					sp = new Sanpham(param);
					pxk_spkmlist.add(sp);*/
				}
				rsPxk_spkm.close();
			} 
			catch(Exception e) {System.out.println(e.toString());}
			finally{try {
				if( rsPxk_spkm != null)
					rsPxk_spkm.close();
			} catch (Exception e2) {
				
			}}
		}
		this.pxk_spkmList = pxk_spkmlist;
	}
	
	private void createPxk_TienkmList(String id) 
	{
		List<ISanpham> pxk_tienkmlist = new ArrayList<ISanpham>();
		//String query = "select b.scheme + '-' + b.diengiai as ctkmTen, sum(a.tonggiatri) as tonggiatri from phieuxuatkho_tienkm a inner join ctkhuyenmai b on a.scheme = b.pk_seq ";
		//query += " where a.pxk_fk = '" + this.id + "' group by b.scheme + '-' + b.diengiai";
		
		String query = "select b.scheme + '-' + b.diengiai as ctkmTen, sum(a.tonggiatri) as tonggiatri from donhang_ctkm_trakm a inner join ctkhuyenmai b on a.ctkmId = b.pk_seq  " +
				"where a.spMa is null and a.donhangId in (select donhang_fk from phieuxuatkho_donhang where pxk_fk = '" + id + "') " +
				"group by b.scheme + '-' + b.diengiai";
		
		ResultSet rsPxk_spkm = db.get(query);
		if(rsPxk_spkm != null)
		{	
			try
			{
				while(rsPxk_spkm.next())
				{
					String[] param = new String[8];
					ISanpham sp = null;
					
					param[0] = "";					
					param[1] = "";		
					param[2] = "";
					param[3] = "";		
					param[4] = "";
					param[5] = "";
					
					//luu ten ctkm
					param[6] = "";
					if(rsPxk_spkm.getString("ctkmTen") != null)
						param[6] = rsPxk_spkm.getString("ctkmTen");
					
					param[7] = "";
					if(rsPxk_spkm.getString("tonggiatri") != null)
						param[7] = rsPxk_spkm.getString("tonggiatri");
					
					sp = new Sanpham(param);
					pxk_tienkmlist.add(sp);
				}
				rsPxk_spkm.close();
			} 
			catch(Exception e) {}
			finally{try {
				if(rsPxk_spkm != null)
					rsPxk_spkm.close();
			} catch (Exception e2) {
			}}
		}
		this.pxk_tienkmList = pxk_tienkmlist;
	}

	private void createDonhangList()
	{
		Utility util = new Utility();
		//dh thuoc nvgn nay chua duoc chon
		String sql =" select dh.pk_seq as dhId, dh.ngaynhap, ( (  " +
					" select sum( (soluong * giamua) - chietkhau ) as tienBvat  " +
					" from donhang_sanpham  " +
					" where donhang_fk = dh.pk_seq " +
					" group by donhang_fk ) - " +
					" isnull( (select sum(tonggiatri) as giatriKM from donhang_ctkm_trakm where donhangId = dh.pk_seq and SPMA is null ), '0')" +
					" - isnull( ( select sum(thanhtoan) as giatriKMTL from DUYETTRAKHUYENMAI_DONHANG where donhang_fk = dh.pk_seq )	, 0 )  ) as tonggiatri, " +
					" kh.smartid, kh.pk_seq as khId, kh.ten as khTen, kh.diachi as dc " +
					" from donhang dh inner join khachhang kh on dh.khachhang_fk = kh.pk_seq " +
					" where 1=1  and dh.trangthai = '0' and dh.npp_fk = '" + this.nppId + "' and dh.kho_fk in  "+util.quyen_kho(this.userId) ;
		
		if(this.ngaylapphieu.length()>0)
		{
			sql+=" and dh.ngaynhap = '" + this.ngaylapphieu + "' ";
		}
		
			/*if(this.nvbhId.length()>0)
				sql+=" and dh.khachhang_fk in (select khachhang_fk from NVGN_KH where nvgn_fk = '" + this.nvgnId + "') " ;*/
					
					sql+=
					" and dh.pk_seq not in (select donhang_fk from phieuxuatkho_donhang where pxk_fk in (select pk_seq from phieuxuatkho where trangthai = '0' and npp_fk = '" + this.nppId + "')) ";
		
		if(this.id.trim().length() > 0)
			sql = sql + " and dh.pk_seq not in (select donhang_fk from phieuxuatkho_donhang where pxk_fk = '" + this.id + "') ";
		
		if(this.nvbhId.length() > 0)
			sql = sql + "and dh.ddkd_fk = '" + this.nvbhId + "'";
		sql = sql + " order by dh.ngaynhap ASC";
		
		System.out.println("Don hang chua chon la: " + sql);
		
		this.dhList = db.get(sql);
		
		//donhang thuoc nvgn nay
		sql = "select dh.pk_seq as dhId, dh.ngaynhap, ( (  " +
			 "	select sum( (soluong * giamua) - chietkhau )  as tienBvat  " +
			 "	from donhang_sanpham  " +
			 "	where donhang_fk = dh.pk_seq " +
			 "	group by donhang_fk ) - " +
			 "	isnull( (select sum(tonggiatri) as giatriKM from donhang_ctkm_trakm where donhangId = dh.pk_seq and SPMA is null ), '0') " +
			 "  - isnull( ( select sum(thanhtoan) as giatriKMTL from DUYETTRAKHUYENMAI_DONHANG where donhang_fk = dh.pk_seq )	, 0 )  ) as tonggiatri, " +
			 " kh.smartid, kh.pk_seq as khId, kh.ten as khTen, kh.diachi as dc " +
			 " from donhang dh inner join khachhang kh on dh.khachhang_fk = kh.pk_seq " +
			 " where dh.ngaynhap = '" + this.ngaylapphieu + "' and dh.pk_seq in (select donhang_fk from phieuxuatkho_donhang where pxk_fk = '" + this.id + "') and dh.kho_fk in  "+util.quyen_kho(this.userId) ;
		System.out.println("Don hang da chon la: " + sql + "\n");
		
		if(this.nvbhId.length() > 0)
			sql = sql + "and dh.ddkd_fk = '" + this.nvbhId + "'";
		sql = sql + " order by dh.ngaynhap ASC";
		
		this.dhIdsList = db.get(sql);
	}
	
	private void createDonhangIds()
	{
		String sql = "select donhang_fk from phieuxuatkho_donhang where pxk_fk = '" + this.id + "'";
		ResultSet dhRS = db.get(sql);
		if(dhRS != null)
		{
			String str = "";
			try 
			{
				while(dhRS.next())
				{
					str = str + dhRS.getString("donhang_fk") + ",";
				}
				dhRS.close();
			} 
			catch(Exception e) {}
			finally{try {
				if(dhRS != null)
					dhRS.close();
			} catch (Exception e2) {
				
			}}
			if(str.length() > 0)
			{
				str = str.trim().substring(0, str.length() - 1);
				this.dhIds = str.split(",");
			}
		}
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
			return getDateTime();
		String[] arr = date.split("-");
		if(arr[0].length() < arr[2].length())
			return arr[2] + "-" + arr[1] + "-" + arr[0];
		return date;
	}
	
	public void DBclose() 
	{
		try {

			if(this.dhIdsList != null)
				this.dhIdsList.close();
			if(this.dhList != null)
				this.dhList.close();
			if(this.nhanviengn != null)
				this.nhanviengn.close();
			if(this.nvbanhang != null)
				this.nvbanhang.close();
			if(this.spkmList != null)
				this.spkmList.close();
			if(this.tienkmList != null)
				this.tienkmList.close();
			if(this.tuyenbanhang != null)
				this.tuyenbanhang.close();
			credits=null;
			pxk_spList=null;
			pxk_spkmList=null;
			pxk_tienkmList=null;
			
			if(this.db != null)
				this.db.shutDown();
			
		} catch (Exception e) {
			
		}
	}
	

	
	public void setngaykhoaso(String ngaykhoaso) 
	{		
		this.ngaykhoaso = ngaykhoaso;
	}

	
	public String getngaykhoaso() {
		
		return this.ngaykhoaso;
	}
	
	public void createKhoasongay()
	{   
		String st = "";
	    	String sql = "select max(NGAYKS) as ngaykhoaso from KHOASONGAY where npp_fk ='"+ this.nppId +"'";
	    	//System.out.println(sql);
	    	ResultSet rs = db.get(sql);
	    	if(rs != null)
	    	{
	    		try {
					rs.next();
					st = rs.getString("ngaykhoaso");
					if(st ==null)
						st="";
				} catch(Exception e) {
					
					e.printStackTrace();
				}
				finally{try {
					if(rs != null)
						rs.close();
				} catch (Exception e2) {
					
				}}
	    		
	    	}
	    	this.ngaykhoaso = st;
	    }

	public List<ISanpham> getPxk_tienkmList() 
	{
		return this.pxk_tienkmList;
	}

	public void setPxk_tienkmList(List<ISanpham> tienkmList)
	{
		this.pxk_tienkmList = tienkmList;
	}

	
	private void createPxk_SpList(String pxkId, dbutils db)
	{
		List<ISanpham> pxk_splist = new ArrayList<ISanpham>();
		String query = "select a.sanpham_fk as spId, b.ma as spMa, a.soluong, b.ten as spTen, c.ten as khoTen, d.ten as kbhTen,isnull(b.barcode,'') as barcode,soluong1 as quicach " +
		"from phieuxuatkho_sanpham a inner join sanpham b on a.sanpham_fk = b.pk_seq ";
			query += " left join kho c on a.kho_fk = c.pk_seq" +
		" left join kenhbanhang d on a.kbh_fk = d.pk_seq " +
		" left join quycach q on q.sanpham_fk = a.sanpham_fk and q.dvdl2_fk=100018 " +
		" left join donvidoluong  d2 on d2.pk_Seq = dvdl2_fk " +
		" left join donvidoluong d1 on d1.pk_seq = dvdl1_fk" +
		" where a.pxk_fk = '" + pxkId + "'";
		System.out.print("\nQuery truy van du lieu la:.... xxxx2222" + query + "\n");
		
		ResultSet rsPxk_sp = db.get(query);
		if(rsPxk_sp != null)
		{
			String[] param = new String[10];
			ISanpham sp = null;	
			try 
			{
				while(rsPxk_sp.next())
				{
					param[0] = rsPxk_sp.getString("spId");
					param[1] = rsPxk_sp.getString("spMa");
					param[2] = rsPxk_sp.getString("spTen");
					param[3] = rsPxk_sp.getString("soluong");
					
					param[4] = "";
					if(rsPxk_sp.getString("khoTen") != null)
						param[4] = rsPxk_sp.getString("khoTen");
					
					param[5] = "";
					if(rsPxk_sp.getString("kbhTen") != null)
						param[5] = rsPxk_sp.getString("kbhTen");
					
					param[6] = "";
					param[7] = "";
					param[8]=rsPxk_sp.getString("barcode");
					param[9]=String.valueOf(rsPxk_sp.getInt("quicach"));
					
					
					sp = new Sanpham(param);
					pxk_splist.add(sp);
				}
				rsPxk_sp.close();
			} 
			catch(Exception e) {System.out.println(e.toString());}
			finally{try {
				if(rsPxk_sp != null)
					rsPxk_sp.close();
			} catch (Exception e2) {
				System.out.println(e2.toString());
			}}
		}
		this.pxk_spList= pxk_splist;
	}
	
	private void createPxk_SpkmList(String pxkId, dbutils db)
	{
		List<ISanpham> pxk_spkmlist = new ArrayList<ISanpham>();
		String query = "select c.ten as khoTen, d.ten as kbhTen, e.scheme + '-' + e.diengiai as ctkmTen, a.sanpham_fk as spId, " +
				"isnull(b.barcode,'') as barcode,soluong1 as quicach," +
				" b.ma as spMa, b.ten as spTen, sum(a.soluong) as soluong from phieuxuatkho_spkm a inner " +
				" join sanpham b on a.sanpham_fk = b.pk_seq inner join kho c on a.kho_fk = c.pk_seq ";
		query += "inner join kenhbanhang d on a.kbh_fk = d.pk_seq inner join ctkhuyenmai e  " +
				"on a.scheme = e.pk_seq " +
				" left join quycach q on q.sanpham_fk = a.sanpham_fk and q.dvdl2_fk=100018 " +
				" left join donvidoluong  d2 on d2.pk_Seq = dvdl2_fk " +
				" left join donvidoluong d1 on d1.pk_seq = dvdl1_fk" +
				" where a.pxk_fk = '" + pxkId + "' group by c.ten, d.ten, " +
						" e.scheme + '-' + e.diengiai, a.sanpham_fk, b.ma, b.ten ,b.barcode,soluong1 ";
		System.out.println("Get San Pham Khuyen Mai : "+query);
		ResultSet rsPxk_spkm = db.get(query);
		if(rsPxk_spkm != null)
		{	
			try
			{
				while(rsPxk_spkm.next())
				{
					String[] param = new String[9];
					ISanpham sp = null;
					
					param[0] = rsPxk_spkm.getString("spId");					
					param[1] = rsPxk_spkm.getString("spMa");		
					param[2] = rsPxk_spkm.getString("spTen");
					param[3] = rsPxk_spkm.getString("soluong");
					
					//luu kho
					param[4] = "";
					if(rsPxk_spkm.getString("khoTen") != null)
						param[4] = rsPxk_spkm.getString("khoTen");
					
					//luu kenh ban hang
					param[5] = "";
					if(rsPxk_spkm.getString("kbhTen") != null)
						param[5] = rsPxk_spkm.getString("kbhTen");
					System.out.println("Kenh ban hang : "+param[5]);	
					//luu ten ctkm
					param[6] = "";
					if(rsPxk_spkm.getString("ctkmTen") != null)
						param[6] = rsPxk_spkm.getString("ctkmTen");
					
					param[7] = "";
					param[8]=rsPxk_spkm.getString("barcode");
					param[9]= String.valueOf(rsPxk_spkm.getInt("quicach"));
					sp = new Sanpham(param);
					pxk_spkmlist.add(sp);
				}
				rsPxk_spkm.close();
			} 
			catch(Exception e) {}
			finally{try {
				if( rsPxk_spkm != null)
					rsPxk_spkm.close();
			} catch (Exception e2) {
				
			}}
		}
		this.pxk_spkmList = pxk_spkmlist;
	}
	
	private void createPxk_TienkmList(String pxkId, dbutils db) 
	{
		List<ISanpham> pxk_tienkmlist = new ArrayList<ISanpham>();
		String query = "select b.scheme + '-' + b.diengiai as ctkmTen, sum(a.tonggiatri) as tonggiatri from phieuxuatkho_tienkm a inner join ctkhuyenmai b on a.scheme = b.pk_seq ";
		query += " where a.pxk_fk = '" + pxkId + "' group by b.scheme + '-' + b.diengiai";
		
		ResultSet rsPxk_spkm = db.get(query);
		if(rsPxk_spkm != null)
		{	
			try
			{
				while(rsPxk_spkm.next())
				{
					String[] param = new String[8];
					ISanpham sp = null;
					
					param[0] = "";					
					param[1] = "";		
					param[2] = "";
					param[3] = "";		
					param[4] = "";
					param[5] = "";
					
					//luu ten ctkm
					param[6] = "";
					if(rsPxk_spkm.getString("ctkmTen") != null)
						param[6] = rsPxk_spkm.getString("ctkmTen");
					
					param[7] = "";
					if(rsPxk_spkm.getString("tonggiatri") != null)
						param[7] = rsPxk_spkm.getString("tonggiatri");
					
					sp = new Sanpham(param);
					pxk_tienkmlist.add(sp);
				}
				rsPxk_spkm.close();
			} 
			catch(Exception e) {}
			finally{try {
				if(rsPxk_spkm != null)
					rsPxk_spkm.close();
			} catch (Exception e2) {
				
			}}
		}
		this.pxk_tienkmList = pxk_tienkmlist;
	}

	
	public ResultSet getSpThieuRs() {
		
		return this.spThieu;
	}

	
	public void setSpThieuRs(ResultSet spThieuRs) {
		
		this.spThieu = spThieuRs;
	}

}
