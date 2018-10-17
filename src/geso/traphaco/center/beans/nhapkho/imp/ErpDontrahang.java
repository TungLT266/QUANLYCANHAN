package geso.traphaco.center.beans.nhapkho.imp;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.center.beans.nhapkho.IErpDontrahang;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.management.Query;

public class ErpDontrahang implements IErpDontrahang
{
	String userId;
	String id;
	
	String tungay;
	String denngay;
	
	String kyhieu;
	String sohoadon;
	String ghichu;

	String msg;
	String trangthai;
	
	String khoNhanId;
	ResultSet khoNhanRs;

	String[] spId;
	String[] spMa;
	String[] spTen;
	String[] spDonvi;
	String[] spSoluong;
	String[] spGianhap;
	String[] spVat;
	String[] spSolo;
	String[] spHansudung;
	String[] spNgaysanxuat;
	String[] spNgayhethan;
	
	Hashtable<String, String> sp_chitiet;
	
	String xuatcho;
	String khId;
	ResultSet khRs;
	
	dbutils db;
	String tongtienavat;
	String tongtienbvat;
	String tongtienvat;
	String[] tienvat;

	
	
	
	
	public ErpDontrahang()
	{
		this.id = "";
		this.tungay = getDateTime();
		this.denngay = "";
		this.ghichu = "";
		this.khoNhanId = "";
		this.msg = "";
		this.trangthai = "0";
		this.xuatcho = "0";
		this.khId = "";
		
		this.kyhieu = "";
		this.sohoadon = "";
		
		this.sp_chitiet = new Hashtable<String, String>();
		this.tongtienavat="0";
		this.tongtienbvat="0";
		this.tongtienvat="0";
		this.db = new dbutils();
	}
	
	public ErpDontrahang(String id)
	{
		this.id = id;
		this.tungay = getDateTime();
		this.denngay = "";
		this.ghichu = "";
		this.khoNhanId = "";
		this.msg = "";
		this.trangthai = "0";
		this.xuatcho = "0";
		this.khId = "";
		
		this.kyhieu = "";
		this.sohoadon = "";
		this.tongtienavat="0";
		this.tongtienbvat="0";
		this.tongtienvat="0";
		this.sp_chitiet = new Hashtable<String, String>();
		this.db = new dbutils();
	}
	public String[] getTienvat() {
		return tienvat;
	}

	public void setTienvat(String[] tienvat) {
		this.tienvat = tienvat;
	}

	
	public String getTongtienvat() {
		return tongtienvat;
	}

	public void setTongtienvat(String tongtienvat) {
		this.tongtienvat = tongtienvat;
	}

	public String getTongtienbvat() {
		return tongtienbvat;
	}

	public void setTongtienbvat(String tongtienbvat) {
		this.tongtienbvat = tongtienbvat;
	}

	public String getTongtienavat() {
		return tongtienavat;
	}

	public void setTongtienavat(String tongtienavat) {
		this.tongtienavat = tongtienavat;
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

	public void setId(String Id) 
	{
		this.id = Id;
	}

	public String getKhoNhapId()
	{
		return this.khoNhanId;
	}

	public void setKhoNhapId(String khonhaptt) 
	{
		this.khoNhanId = khonhaptt;
	}

	public ResultSet getKhoNhapRs() 
	{
		return this.khoNhanRs;
	}

	public void setKhoNHapRs(ResultSet khonhapRs) 
	{
		this.khoNhanRs = khonhapRs;
	}
	
	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	
	public boolean createNK() 
	{
		if(this.tungay.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn từ ngày";
			return false;
		}
		
		if( this.khId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn nhà phân phối / khách hàng";
			return false;
		}
		
		if(spMa == null)
		{
			this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm";
			return false;
		}
		else
		{
			boolean coSP = false;
			for(int i = 0; i < spMa.length; i++)
			{
				if( spMa[i].trim().length() > 0 )
				{
					if(spSoluong[i].trim().length() <= 0)
					{
						this.msg = "Bạn phải nhập số lượng của sản phẩm ( " + spTen[i] + " )";
						return false;
					}
					
					coSP = true;
				}
			}
			
			if(!coSP)
			{
				this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm";
				return false;
			}
			
			//CHECK TRUNG MA + TRUNG SO LO
			/*for(int i = 0; i < spMa.length; i++)
			{
				if(spMa[i].trim().length() > 0)
				{
					String ct = sp_chitiet.get(spMa[i]);
					if(ct != null)
                	{
                		String[] ctARR = ct.substring(0, ct.length() - 3).split("___");
                		for(int j = 0; j <  ctARR.length - 1; j++ )
                		{
                			for(int k = j + 1; k < ctARR.length; k++ )
                			{
                				if( ctARR[j].contains("__") && ctARR[j].contains("__") )
                				{
                					String[] _ct = ctARR[j].split("__"); 
                					String[] _ct2 = ctARR[k].split("__"); 
                    				
                    				if( _ct[1].trim().equals(_ct2[1].trim()) )
                        			{
    									this.msg = "Sản phẩm : " + spTen[i] + " , Với số lô (" + _ct2[1].trim() + ") đã bị trùng. Vui lòng kiểm tra lại ";
    									return false;
                        			}
                				}
                			}
                		}
                	}
				}
			}*/
		}
		
		try
		{
			db.getConnection().setAutoCommit(false);
		
			String query = " insert ERP_DONTRAHANG(tongtienavat,tongtienbvat,tongtienvat,kyhieu,sohoadon,ngaytra, ghichu, trangthai, xuatcho, doituongId,  ngaytao, nguoitao, ngaysua, nguoisua) " +
						   " values("+ this.tongtienavat +","+ this.tongtienbvat +","+ this.tongtienvat +",N'"+this.kyhieu+"','"+sohoadon+"','" + this.tungay + "', N'" + this.ghichu + "', '0', " + this.xuatcho + ", '" + this.khId + "', '" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "' )";
			
			System.out.println("1.Insert NK: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_DONTRAHANG " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "select SCOPE_IDENTITY() as id ";
			ResultSet rs = db.get(query);
			if(rs.next())
			{
				this.id = rs.getString("id");
			}
			
			rs.close();
			System.out.println("this.id: " + this.id);
			if( this.sp_chitiet != null )
			{
				Enumeration<String> keys = this.sp_chitiet.keys(); 
				while( keys.hasMoreElements() )
				{
					String key = keys.nextElement();
					String[] arr = key.split("__");
					
					query = "insert ERP_DONTRAHANG_SANPHAM( dontrahang_fk, SANPHAM_FK, DVDL_FK, soluong, solo, ngayhethan ) " +
							"select '" + this.id + "', PK_SEQ, DVDL_FK, '" + this.sp_chitiet.get(key).replaceAll(",", "") + "', N'" + arr[1] + "', '" + arr[2] + "' " +
							"from ERP_SANPHAM where MA = '" + arr[0] + "' ";
					
					System.out.println("1.Insert PB - SP: " + query);
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi ERP_DONTRAHANG_SANPHAM: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
				
				for( int i = 0; i < this.spMa.length; i++ )
				{
					String _dongia = "0";
					if( this.spGianhap[i].trim().length() > 0 )
						_dongia = this.spGianhap[i].replaceAll(",", "");
					
					String _vat = "0";
					if( this.spVat[i].trim().length() > 0 )
						_vat = this.spVat[i].replaceAll(",", "");
					String _tienvat="0";
					if( this.tienvat[i].trim().length() > 0 )
						_tienvat = this.tienvat[i].replaceAll(",", "");
					
					query = " update ERP_DONTRAHANG_SANPHAM set dongia = '" + _dongia + "', vat = '" + _vat + "',tienvat="+_tienvat+" " + 
							" where dontrahang_fk = '" + this.id + "' and SANPHAM_FK = ( select pk_seq from ERP_SANPHAM where ma = '" + this.spMa[i] + "' )  ";
					
					System.out.println("2.Cap nhat DG: " + query);
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi ERP_DONTRAHANG_SANPHAM: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
				
			}
			
			String msg=HOADON_CHUNGLOAI(db,  this.id);
			if(msg.trim().length()>0)
			{
				this.msg = "Khong the tao moi phan bo tien: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			db.update("rollback");
			this.msg = "Exception: " + e.getMessage();
			return false;
		}
		
		return true;
	}

	public boolean updateNK() 
	{
		if(this.tungay.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn từ ngày";
			return false;
		}
		
		if( this.khId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn nhà phân phối / khách hàng";
			return false;
		}
		
		if(spMa == null)
		{
			this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm";
			return false;
		}
		else
		{
			boolean coSP = false;
			for(int i = 0; i < spMa.length; i++)
			{
				if( spMa[i].trim().length() > 0 )
				{
					if(spSoluong[i].trim().length() <= 0)
					{
						this.msg = "Bạn phải nhập số lượng của sản phẩm ( " + spTen[i] + " )";
						return false;
					}
					
					coSP = true;
				}
			}
			
			if(!coSP)
			{
				this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm";
				return false;
			}
			
			//CHECK TRUNG MA + TRUNG SO LO
			/*for(int i = 0; i < spMa.length; i++)
			{
				if(spMa[i].trim().length() > 0)
				{
					String ct = sp_chitiet.get(spMa[i]);
					if(ct != null)
                	{
                		String[] ctARR = ct.substring(0, ct.length() - 3).split("___");
                		for(int j = 0; j <  ctARR.length - 1; j++ )
                		{
                			for(int k = j + 1; k < ctARR.length; k++ )
                			{
                				if( ctARR[j].contains("__") && ctARR[j].contains("__") )
                				{
                					String[] _ct = ctARR[j].split("__"); 
                					String[] _ct2 = ctARR[k].split("__"); 
                    				
                    				if( _ct[1].trim().equals(_ct2[1].trim()) )
                        			{
    									this.msg = "Sản phẩm : " + spTen[i] + " , Với số lô (" + _ct2[1].trim() + ") đã bị trùng. Vui lòng kiểm tra lại ";
    									return false;
                        			}
                				}
                			}
                		}
                	}
				}
			}*/
		}
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query =	" Update ERP_DONTRAHANG set tongtienavat="+this.tongtienavat+",tongtienbvat="+this.tongtienbvat+",tongtienvat="+this.tongtienvat+", sohoadon='"+this.sohoadon+"',kyhieu=N'"+this.kyhieu+"',ngaytra = '" + this.tungay + "', ghichu = N'" + this.ghichu + "', " +
							" xuatcho = " + this.xuatcho + ", doituongId = '" + this.khId + "', ngaysua = '" + getDateTime() + "', nguoisua = '" + this.userId + "' " + 
							" where pk_seq = '" + this.id + "' ";
		
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_DONTRAHANG " + query;
				db.getConnection().rollback();
				return false;
			}
				
			query = "delete ERP_DONTRAHANG_SANPHAM where dontrahang_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_DONTRAHANG_SANPHAM " + query;
				db.getConnection().rollback();
				return false;
			}
			
			if( this.sp_chitiet != null )
			{
				Enumeration<String> keys = this.sp_chitiet.keys(); 
				while( keys.hasMoreElements() )
				{
					String key = keys.nextElement();
					String[] arr = key.split("__");
					
					query = "insert ERP_DONTRAHANG_SANPHAM( dontrahang_fk, SANPHAM_FK, DVDL_FK, soluong, solo, ngayhethan ) " +
							"select '" + this.id + "', PK_SEQ, DVDL_FK, '" + this.sp_chitiet.get(key).replaceAll(",", "") + "', N'" + arr[1] + "', '" + arr[2] + "' " +
							"from ERP_SANPHAM where MA = '" + arr[0] + "' ";
					
					System.out.println("1.Insert PB - SP: " + query);
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi ERP_DONTRAHANG_SANPHAM: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
				
				for( int i = 0; i < this.spMa.length; i++ )
				{
					String _dongia = "0";
					if( this.spGianhap[i].trim().length() > 0 )
						_dongia = this.spGianhap[i].replaceAll(",", "");
					
					String _vat = "0";
					if( this.spVat[i].trim().length() > 0 )
						_vat = this.spVat[i].replaceAll(",", "");
					
					String _tienvat = "0";
					if( this.tienvat[i].trim().length() > 0 )
						_tienvat = this.tienvat[i].replaceAll(",", "");
					
					
					query = " update ERP_DONTRAHANG_SANPHAM set dongia = '" + _dongia + "', vat = '" + _vat + "',tienvat="+_tienvat+" " + 
							" where dontrahang_fk = '" + this.id + "' and SANPHAM_FK = ( select pk_seq from ERP_SANPHAM where ma = '" + this.spMa[i] + "' )  ";
					
					System.out.println("2.Cap nhat DG: " + query);
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi ERP_DONTRAHANG_SANPHAM: " + query;
						db.getConnection().rollback();
						return false;
					}
					
				}
				
				String msg=HOADON_CHUNGLOAI(db,  this.id);
				if(msg.trim().length()>0)
				{
					this.msg = "Khong the tao moi phan bo tien: " + query;
					db.getConnection().rollback();
					return false;
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			db.update("rollback");
			this.msg = "Exception: " + e.getMessage();
			return false;
		}
		
		return true;
	}


	public void createRs() 
	{
		Utility util = new Utility(); 
		String query = "select PK_SEQ, TEN from ERP_KHOTT  WHERE trangthai = '1' AND PK_SEQ IN "+util.quyen_khoTT(userId)+" ";
		
		this.khoNhanRs = db.get(query);
		
		//this.dvtRs = db.getScrol("select PK_SEQ, DONVI from DONVIDOLUONG where trangthai = '1' ");
		
		query = "select PK_SEQ, MA + ' - ' + TEN as TEN from NHAPHANPHOI where TRANGTHAI = '1' AND isKHACHHANG = '1'  AND PK_SEQ != '1' order by MA + ' - ' + TEN asc ";
		if(this.xuatcho.equals("1"))
			query = "select PK_SEQ, MA + ' - ' + TEN as TEN from NHAPHANPHOI where TRANGTHAI = '1' AND isKHACHHANG = '1'  AND PK_SEQ != '1' order by MA + ' - ' + TEN asc ";
		this.khRs = db.get(query);
		
	}

	private void createChuyenKho_SanPham() 
	{
		String query =  " select isnull(tienvat,0) tienvat,b.MA, b.TEN, DV.donvi, a.soluong, ISNULL(a.dongia,0) AS dongia, isnull(a.vat,0) as vat, a.solo, isnull(a.ngayhethan, ' ') as ngayhethan    " +
						" from ERP_DONTRAHANG_SANPHAM a inner Join ERP_SANPHAM b on a.SANPHAM_FK = b.PK_SEQ    " +
						" INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = a.DVDL_FK       " +
						" where a.dontrahang_fk = '" + this.id + "' ";
		
		System.out.println("---INIT SP: " + query);
		ResultSet spRs = db.get(query);
		
		NumberFormat formater = new DecimalFormat("##,###,###.####");
		if(spRs != null)
		{
			try 
			{
				String spMA = "";
				String spTEN = "";
				String spDONVI = "";
				String spDONGIA = "";
				String spVAT = "";
				String Spsoluong="";
				String Sptienvat="";
				double tienvatsp=0;
				Hashtable<String, String> sp_chitiet = new Hashtable<String, String>();
				while(spRs.next())
				{
					if(!spMA.contains(spRs.getString("MA")) )
					{
						spMA += spRs.getString("MA") + "__";
						spTEN += spRs.getString("TEN") + "__";
						spDONVI += spRs.getString("DONVI") + "__";
						spDONGIA += formater.format(spRs.getDouble("dongia")) + "__";
						spVAT += spRs.getString("VAT") + "__";
						Spsoluong+=spRs.getString("soluong") + "__";
						Sptienvat+=spRs.getString("tienvat")+ "__";
					}
					
					System.out.println("::: KEY BEAN: " + ( spRs.getString("MA") + "__" + spRs.getString("SOLO") + "__" + spRs.getString("ngayhethan") ) );
					sp_chitiet.put(spRs.getString("MA") + "__" + spRs.getString("SOLO") + "__" + spRs.getString("ngayhethan")+"__" +spRs.getString("tienvat"), spRs.getString("soluong"));
					
				}
				spRs.close();
				
				if(spMA.trim().length() > 0)
				{
					spMA = spMA.substring(0, spMA.length() - 2);
					this.spMa = spMA.split("__");
					
					spTEN = spTEN.substring(0, spTEN.length() - 2);
					this.spTen = spTEN.split("__");
					
					spDONVI = spDONVI.substring(0, spDONVI.length() - 2);
					this.spDonvi = spDONVI.split("__");
					
					spDONGIA = spDONGIA.substring(0, spDONGIA.length() - 2);
					this.spGianhap = spDONGIA.split("__");
					
					spVAT = spVAT.substring(0, spVAT.length() - 2);
					this.spVat = spVAT.split("__");
					
					Spsoluong = Spsoluong.substring(0, Spsoluong.length() - 2);
					this.spSoluong = Spsoluong.split("__");
					
					
					System.out.println("sp tien vat la "+Sptienvat);
					Sptienvat = Sptienvat.substring(0, Sptienvat.length() - 2);
					this.tienvat = Sptienvat.split("__");
					
					this.sp_chitiet = sp_chitiet;
				}
				
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				System.out.println("115.Exception: " + e.getMessage());
			}
		}
	}

	public void init() 
	{
		String query = "select isnull(tongtienavat,0) as  tongtienavat, isnull(tongtienbvat,0) as  tongtienbvat,isnull(tongtienvat,0)  tongtienvat,ngaytra, ISNULL(ghichu, '') as ghichu, xuatcho, doituongId, trangthai,isnull(kyhieu,'') kyhieu,isnull(sohoadon,'') sohoadon " +
						"from ERP_DONTRAHANG where pk_seq = '" + this.id + "'";
		System.out.println("____INIT NHAP KHO: " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					this.tungay = rs.getString("ngaytra");
					this.ghichu = rs.getString("ghichu");
					this.xuatcho = rs.getString("xuatcho");
					this.khId = rs.getString("doituongId");
					this.trangthai = rs.getString("trangthai");
					this.kyhieu=rs.getString("kyhieu");
					this.sohoadon=rs.getString("sohoadon");
					this.tongtienavat=rs.getString("tongtienavat");
					this.tongtienbvat=rs.getString("tongtienbvat");
					this.tongtienvat=rs.getString("tongtienvat");
				}
				rs.close();

			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				System.out.println("---LOI INIT: " + e.getMessage());
			}
		}
		
		this.createRs();
		
		this.createChuyenKho_SanPham();
		
	}

	public void DBclose() {
		
		try{
			
			if(khoNhanRs!=null){
				khoNhanRs.close();
			}
			
			this.db.shutDown();
			
		}catch(Exception er){
			
		}
	}
	
	public String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}

	public String getGhichu() {
		
		return this.ghichu;
	}

	public void setGhichu(String ghichu) {
		
		this.ghichu = ghichu;
	}

	
	public String[] getSpId() {
		
		return this.spId;
	}

	
	public void setSpId(String[] spId) {
		
		this.spId = spId;
	}

	
	public String[] getSpMa() {
		
		return this.spMa;
	}

	
	public void setSpMa(String[] spMa) {
		
		this.spMa = spMa;
	}

	
	public String[] getSpTen() {
		
		return this.spTen;
	}

	
	public void setSpTen(String[] spTen) {
		
		this.spTen = spTen;
	}

	
	public String[] getSpDonvi() {
		
		return this.spDonvi;
	}

	
	public void setSpDonvi(String[] spDonvi) {
		
		this.spDonvi = spDonvi;
	}

	
	public String[] getSpSoluong() {
		
		return this.spSoluong;
	}

	
	public void setSpSoluong(String[] spSoluong) {
		
		this.spSoluong = spSoluong;
	}

	
	public String[] getSpGianhap() {
		
		return this.spGianhap;
	}

	
	public void setSpGianhap(String[] spGianhap) {
		
		this.spGianhap = spGianhap;
	}

	
	public String[] getSpSolo() {
		
		return this.spSolo;
	}

	
	public void setSpSolo(String[] spSolo) {
		
		this.spSolo = spSolo;
	}

	
	public String[] getSpNgaysanxuat() {
		
		return this.spNgaysanxuat;
	}

	
	public void setSpNgaysanxuat(String[] spNgaysanxuat) {
		
		this.spNgaysanxuat = spNgaysanxuat;
	}

	
	public String[] getSpNgayhethan() {
		
		return this.spNgayhethan;
	}

	
	public void setSpNgayhethan(String[] spNgayhethan) {
		
		this.spNgayhethan = spNgayhethan;
	}

	
	public String[] getSpHansudung() {
		
		return this.spHansudung;
	}


	public void setSpHansudung(String[] spHansudung) {
		
		this.spHansudung = spHansudung;
	}

	
	public Hashtable<String, String> getSp_Chitiet() {
		
		return this.sp_chitiet;
	}

	
	public void setSp_Chitiet(Hashtable<String, String> sp_chitiet) {
		
		this.sp_chitiet = sp_chitiet;
	}
	
	ResultSet dvtRs;

	public ResultSet getDvtRs() {

		return this.dvtRs;
	}
	
	public void setDvtRs(ResultSet dvtRs) {
		
		this.dvtRs = dvtRs;
	}

	
	public String getTungay() {
		
		return this.tungay;
	}

	
	public void setTungay(String tungay) {
		
		this.tungay = tungay;
	}

	
	public String getDenngay() {
		
		return this.denngay;
	}

	
	public void setDenngay(String denngay) {
		
		this.denngay = denngay;
	}
	
	public String getXuatcho() {

		return this.xuatcho;
	}

	public void setXuatcho(String xuatcho) {
		
		this.xuatcho = xuatcho;
	}
	
	public String getKhId() 
	{
		return this.khId;
	}

	public void setKhId(String khId) 
	{
		this.khId = khId;
	}
	
	public ResultSet getKhRs() {
		
		return this.khRs;
	}

	public void setKhRs(ResultSet khRs) {
		
		this.khRs = khRs;
	}

	public void initChoPhanBo() 
	{
		String query =  "select b.MA, b.TEN, DV.donvi, a.soluong, a.gianhap, a.solo, isnull(a.ngaysanxuat, ' ') as ngaysanxuat, isnull(a.ngayhethan, ' ') as ngayhethan, isnull(b.hansudung, 0) as hansudung    " +
						" from ERP_HANGCHOPHANBO a inner Join ERP_SANPHAM b on a.SANPHAM_FK = b.PK_SEQ    " +
						" 		INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = a.DVDL_FK       " +
						"where 1 = 1 ";

		System.out.println("---INIT SP: " + query);
		ResultSet spRs = db.get(query);

		NumberFormat formater = new DecimalFormat("##,###,###.##");
		if(spRs != null)
		{
			try 
			{
				String spMA = "";
				String spTEN = "";
				String spDONVI = "";
				String spHANSUDUNG = "";

				Hashtable<String, String> sp_chitiet = new Hashtable<String, String>();
				while(spRs.next())
				{
					if(!spMA.contains(spRs.getString("MA")) && !spTEN.contains(spRs.getString("TEN")) )
					{
						spMA += spRs.getString("MA") + "__";
						spTEN += spRs.getString("TEN") + "__";
						spDONVI += spRs.getString("DONVI") + "__";
						spHANSUDUNG += spRs.getString("hansudung") + "__";
					}

					System.out.println("::: KEY BEAN: " + ( spRs.getString("MA") + "__" + spRs.getString("solo") ) );
					sp_chitiet.put(spRs.getString("MA") + "__" + spRs.getString("solo"), "1");
					
					/*String ct = sp_chitiet.get(spRs.getString("MA"));
					if(ct == null)
						ct = "";

					if(ct.trim().length() <= 0)
					{
						ct = formater.format(spRs.getDouble("SOLUONG")) + "__" + spRs.getString("SOLO") + "__" + spRs.getString("NGAYSANXUAT") + "___";
						sp_chitiet.put(spRs.getString("MA"), ct);
					}
					else
					{
						ct += formater.format(spRs.getDouble("SOLUONG")) + "__" + spRs.getString("SOLO") + "__" + spRs.getString("NGAYSANXUAT") + "___";
						sp_chitiet.put(spRs.getString("MA"), ct);
					}*/
				}
				spRs.close();

				if(spMA.trim().length() > 0)
				{
					spMA = spMA.substring(0, spMA.length() - 2);
					this.spMa = spMA.split("__");

					spTEN = spTEN.substring(0, spTEN.length() - 2);
					this.spTen = spTEN.split("__");

					spDONVI = spDONVI.substring(0, spDONVI.length() - 2);
					this.spDonvi = spDONVI.split("__");

					spHANSUDUNG = spHANSUDUNG.substring(0, spHANSUDUNG.length() - 2);
					this.spHansudung = spHANSUDUNG.split("__");

					this.sp_chitiet = sp_chitiet;
				}

			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				System.out.println("115.Exception: " + e.getMessage());
			}
		}
	}

	public boolean updateCHOPB() 
	{
		if(spMa == null)
		{
			this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm chờ phân bổ";
			return false;
		}
		else
		{
			//CHECK TRUNG MA + TRUNG SO LO
			/*for(int i = 0; i < spMa.length; i++)
			{
				if(spMa[i].trim().length() > 0)
				{
					String ct = sp_chitiet.get(spMa[i]);
					if(ct != null)
                	{
                		String[] ctARR = ct.substring(0, ct.length() - 3).split("___");
                		for(int j = 0; j <  ctARR.length - 1; j++ )
                		{
                			for(int k = j + 1; k < ctARR.length; k++ )
                			{
                				if( ctARR[j].contains("__") && ctARR[j].contains("__") )
                				{
                					String[] _ct = ctARR[j].split("__"); 
                					String[] _ct2 = ctARR[k].split("__"); 
                    				
                    				if( _ct[1].trim().equals(_ct2[1].trim()) )
                        			{
    									this.msg = "Sản phẩm : " + spTen[i] + " , Với số lô (" + _ct2[1].trim() + ") đã bị trùng. Vui lòng kiểm tra lại ";
    									return false;
                        			}
                				}
                			}
                		}
                	}
				}
			}*/
		}
		
		try
		{
			db.getConnection().setAutoCommit(false);
					
			String query = "delete ERP_HANGCHOPHANBO ";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_HANGCHOPHANBO " + query;
				db.getConnection().rollback();
				return false;
			}
			
			if( this.sp_chitiet != null )
			{
				Enumeration<String> keys = this.sp_chitiet.keys(); 
				while( keys.hasMoreElements() )
				{
					String key = keys.nextElement();
					String[] arr = key.split("__");
					
					query = "insert ERP_HANGCHOPHANBO( SANPHAM_FK, DVDL_FK, soluong, gianhap, solo, ngaysanxuat, ngayhethan ) " +
							"select pk_seq, DVDL_FK, '1', '0', N'" + arr[1] + "', '', '' " +
							"from ERP_SANPHAM where MA = '" + arr[0] + "' ";
					
					System.out.println("1.Insert PB - SP: " + query);
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi ERP_HANGCHOPHANBO: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
			}
			
			/*for(int i = 0; i < spMa.length; i++)
			{
				if(spMa[i].trim().length() > 0)
				{
					String ct = sp_chitiet.get(spMa[i]);
					if(ct != null)
                	{
                		String[] ctARR = ct.substring(0, ct.length() - 3).split("___");
                		for(int j = 0; j <  ctARR.length; j++ )
                		{
                			String[] _ct = ctARR[j].split("__"); 
                			
                			if(_ct[0].trim().length() > 0 && _ct[1].trim().length() > 0  )
                			{
	                			query = "insert ERP_HANGCHOPHANBO( SANPHAM_FK, DVDL_FK, soluong, gianhap, solo, ngaysanxuat, ngayhethan ) " +
										"select pk_seq, ISNULL( ( select pk_Seq from DONVIDOLUONG where donvi = N'" + spDonvi[i].trim() + "' ), DVDL_FK ), '" + _ct[0].trim().replaceAll(",", "") + "', '0', N'" + _ct[1].trim() + "', '', '' " +
										"from SANPHAM where MA = '" + spMa[i].trim() + "' ";
								
								System.out.println("1.Insert PB - SP: " + query);
								if(!db.update(query))
								{
									this.msg = "Khong the tao moi ERP_HANGCHOPHANBO: " + query;
									db.getConnection().rollback();
									return false;
								}
                			}
                		}
                	}
				}
			}*/
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			db.update("rollback");
			this.msg = "Exception: " + e.getMessage();
			return false;
		}
		
		return true;
	}

	public ResultSet getSoloTheoSp(String spMa, String donvi, String tongluong)
	{
		if( spMa.trim().length() > 0 )
		{
			String query = "select distinct SOLO, NGAYHETHAN, sum(AVAILABLE) as AVAILABLE " + 
						   " from ERP_KHOTT_SP_CHITIET " + 
						   " where khott_fk in (100023, 100058) and SANPHAM_FK = ( select pk_seq from ERP_SANPHAM where ma = '" + spMa + "' ) " + 
						   " group by SOLO, NGAYHETHAN order by SOLO ";
			
			//System.out.println("::: LAY SO LO: " + query);
			return db.get(query);
		}
		
		return null;
	}

	
	public String[] getSpVat() {

		return this.spVat;
	}

	public void setSpVat(String[] spVat) {
		
		this.spVat = spVat;
	}

	
	public String getKyhieuHD() {
		
		return this.kyhieu;
	}

	
	public void setKyhieuHd(String kyhieuHD) {
		
		this.kyhieu = kyhieuHD;
	}

	
	public String getSohoadon() {
		
		return this.sohoadon;
	}

	
	public void setSOhoadon(String sohoadon) {
		
		this.sohoadon = sohoadon;
	}

	
	public static String HOADON_CHUNGLOAI(dbutils db , String hdId)
	{
		try
		{
			String query=" WITH HDCH AS (select distinct a.dontrahang_fk,a.ngayhethan, a.solo, a.sanpham_fk,tienvat,a.soluong   "+
						 "	 from ERP_DONTRAHANG_SANPHAM a    "+
						 "	 where a.dontrahang_fk="+ hdId +") "+
						 "	 , hashSp as (select SUM(soluong) as sltong,SANPHAM_FK, COUNT( solo)sd from HDCH group by SANPHAM_FK    ) "+
						 "	 select *,Row_number()    OVER(  partition BY HDCH.sanpham_fk  ORDER BY solo) m  "+
						 "	 from HDCH   "+
						 "	 inner join  hashSp on HDCH.sanpham_fk = hashSp.sanpham_fk "+
						 "   order by HDCH.sanpham_fk,m ";
			ResultSet rs=db.get(query);
			
			while(rs.next())
			{
				
				if(rs.getInt("sd") < rs.getInt("m"))
				{
						return " error 1";
				}
				
				double tongtienvat=rs.getDouble("tienvat");
				if(rs.getInt("m") < rs.getInt("sd") ||( rs.getInt("m")==1 && rs.getInt("sd")==1) )
				{
					double tienvat=Math.round(tongtienvat * rs.getDouble("soluong") /rs.getDouble("sltong") *1.0);
					query="update ERP_DONTRAHANG_SANPHAM  set tienvat ="+tienvat+" where dontrahang_fk="+hdId+" and solo=N'"+rs.getString("solo")+"' and ngayhethan=N'"+rs.getString("ngayhethan")+"' and sanpham_fk="+rs.getString("sanpham_fk")+" ";
					if(db.updateReturnInt(query) <=0 )
						return " error 2";
				}
				else
				{
				
					String vatTruoc =  "  select sum( tienvat) from  ERP_DONTRAHANG_SANPHAM where dontrahang_fk="+hdId+" and sanpham_fk='"+rs.getString("sanpham_fk")+"' and   (  SOLO !='"+rs.getString("solo")+"' or  NGAYHETHAN !='"+rs.getString("ngayhethan")+"'  )   ";
					
					query="update ERP_DONTRAHANG_SANPHAM  set tienvat ="+tongtienvat+" - ("+vatTruoc+")"
							+ " where dontrahang_fk="+hdId+" and solo=N'"+rs.getString("solo")+"' "
									+ "and ngayhethan=N'"+rs.getString("ngayhethan")+"' and sanpham_fk="+rs.getString("sanpham_fk")+" ";
					if(db.updateReturnInt(query) <=0 )
						return " error 3";
					
				}
			
			}
			
			return"";
			
		}catch (Exception e) {
			e.printStackTrace();
			return "Lỗi :" + e.getMessage();
		}
		 
	}
	
	public static void main(String[] args) {
		dbutils db=new dbutils();
		
		HOADON_CHUNGLOAI(db,"100210");
	}
	
	
}
