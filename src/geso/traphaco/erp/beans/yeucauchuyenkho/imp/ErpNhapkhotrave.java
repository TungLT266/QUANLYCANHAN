package geso.traphaco.erp.beans.yeucauchuyenkho.imp;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.yeucauchuyenkho.IErpNhapkhotrave;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
 
public class ErpNhapkhotrave implements IErpNhapkhotrave
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

	String[] spSTT;
	String[] spId;
	String[] spMa;
	String[] spTen;
	String[] spDonvi;
	String[] spSoluong;
	String[] spGianhap;
	String[] spVat;
	String[] spTienVat;
	String[] spSolo;
	String[] spHansudung;
	String[] spNgaysanxuat;
	String[] spNgayhethan;
	
	Hashtable<String, String> sp_chitiet;
	
	String xuatcho;
	String khId;
	ResultSet khRs;
	
	dbutils db;
	
	public ErpNhapkhotrave()
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
		this.db = new dbutils();
	}
	
	public ErpNhapkhotrave(String id)
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

		this.sp_chitiet = new Hashtable<String, String>();
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
		
			String query = " insert ERP_DONTRAHANG(ngaytra, ghichu, trangthai, xuatcho, doituongId,  ngaytao, nguoitao, ngaysua, nguoisua) " +
						   " values('" + this.tungay + "', N'" + this.ghichu + "', '0', " + this.xuatcho + ", '" + this.khId + "', '" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "' )";
			
			System.out.println("1.Insert NK: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_DONTRAHANG " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "select IDENT_CURRENT('ERP_DONTRAHANG') as id ";
			ResultSet rs = db.get(query);
			rs.next();
			this.id = rs.getString("id");
			rs.close();
			
			if( this.sp_chitiet != null )
			{
				Enumeration<String> keys = this.sp_chitiet.keys(); 
				while( keys.hasMoreElements() )
				{
					String key = keys.nextElement();
					String[] arr = key.split("__");
					
					query = "insert ERP_DONTRAHANG_SANPHAM( dontrahang_fk, SANPHAM_FK, DVDL_FK, soluong, solo, ngayhethan ) " +
							"select '" + this.id + "', PK_SEQ, DVDL_FK, '" + this.sp_chitiet.get(key).replaceAll(",", "") + "', N'" + arr[1] + "', '" + arr[2] + "' " +
							"from SANPHAM where MA = '" + arr[0] + "' ";
					
					System.out.println("1.Insert PB - SP: " + query);
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi ERP_DONTRAHANG_SANPHAM: " + query;
						db.getConnection().rollback();
						return false;
					}
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

	public boolean updateNK() 
	{
		if(this.tungay.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn ngày nhập kho";
			return false;
		}
		
		if(this.sohoadon.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập số hóa đơn";
			return false;
		}
		
		if(this.khoNhanId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn kho nhận";
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
			
			String query =	" Update ERP_DONTRAHANG set kyhieu = '" + this.kyhieu + "', sohoadon = '" + this.sohoadon + "', khott_fk = '" + this.khoNhanId + "', ngaynhapkho = '" + this.tungay + "', ghichu = N'" + this.ghichu + "', " +
							" xuatcho = " + this.xuatcho + ", doituongId = '" + this.khId + "', ngaysua = '" + getDateTime() + "', nguoisua = '" + this.userId + "' " + 
							" where pk_seq = '" + this.id + "' ";
		
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_DONTRAHANG " + query;
				db.getConnection().rollback();
				return false;
			}
				
			//Không xóa chỉ cập nhật thông tin
			/*query = "delete ERP_DONTRAHANG_SANPHAM where dontrahang_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_DONTRAHANG_SANPHAM " + query;
				db.getConnection().rollback();
				return false;
			}*/
			
			if( this.sp_chitiet != null )
			{
				Enumeration<String> keys = this.sp_chitiet.keys(); 
				while( keys.hasMoreElements() )
				{
					String key = keys.nextElement();
					String _soluongCT = "0"; 
					if(this.sp_chitiet.get(key) != null)
					{
						_soluongCT = this.sp_chitiet.get(key).replaceAll(",", "");
					}
					
					String[] _sp = Utility.mySplit(key, "__");
					
					String solo = _sp[1];
					String ngayhethan = _sp[2];

					String MAME = _sp[3];
					String MATHUNG = _sp[4];
					String bin_fk = _sp[5];
					String MAPHIEU = _sp[6];
					String phieudt = _sp[7];
					String phieueo = _sp[8];
					String MARQ = _sp[9];
					String HAMLUONG = _sp[10];
					String HAMAM = _sp[11];
					
					/*query = "insert ERP_DONTRAHANG_SANPHAM( dontrahang_fk, SANPHAM_FK, solo, ngayhethan, MAME, MATHUNG, bin_fk, MAPHIEU, phieudt, phieueo, MARQ, HAMLUONG, HAMAM,  soluong ) " +
							"select '" + this.id + "', pk_seq, N'" + solo + "', N'" + ngayhethan + "', '" + MAME + "', '" + MATHUNG + "', ( select PK_SEQ from ERP_BIN where KHOTT_FK = '" + this.khoNhanId + "' and MA = N'" + bin_fk + "' ), " + 
							" 	'" + MAPHIEU + "', '" + phieudt + "', '" + phieueo + "', '" + MARQ + "', '" + HAMLUONG + "', '" + HAMAM + "', '" + _soluongCT.replaceAll(",", "") + "' " +
							"from ERP_SANPHAM where MA = '" + _sp[0] + "' ";*/
					
					query = " update ERP_DONTRAHANG_SANPHAM set bin_fk = ( select PK_SEQ from ERP_BIN where KHOTT_FK = '" + this.khoNhanId + "' and MA = N'" + bin_fk + "' ),  " +
							"	MAME = '" + MAME + "', MATHUNG = '" + MATHUNG + "', MAPHIEU = '" + MAPHIEU + "', " + 
							"	phieudt = '" + phieudt + "', phieueo = '" + phieueo + "', MARQ = '" + MARQ + "', HAMLUONG = '" + HAMLUONG + "', HAMAM = '" + HAMAM + "'	" +
							" where dontrahang_fk = '" + this.id + "' and SANPHAM_FK = ( select pk_seq from ERP_SANPHAM where ma = '" + _sp[0] + "' ) and solo = '" + solo + "' and ngayhethan = '" + ngayhethan + "'  ";
					
					System.out.println("1.Insert TH - SP: " + query);
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi ERP_DONTRAHANG_SANPHAM: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
				
				/*for( int i = 0; i < this.spMa.length; i++ )
				{
					String _dongia = "0";
					if( this.spGianhap[i].trim().length() > 0 )
						_dongia = this.spGianhap[i].replaceAll(",", "");
					
					String _vat = "0";
					if( this.spVat[i].trim().length() > 0 )
						_vat = this.spVat[i].replaceAll(",", "");
					
					String _tienVat = "0";
					if( this.spTienVat[i].trim().length() > 0 )
						_tienVat = this.spTienVat[i].replaceAll(",", "");
					
					query = " update ERP_DONTRAHANG_SANPHAM set dongia = '" + _dongia + "', vat = '" + _vat + "', tienVAT = '" + _tienVat + "' " + 
							" where dontrahang_fk = '" + this.id + "' and SANPHAM_FK = ( select pk_seq from ERP_SANPHAM where ma = '" + this.spMa[i] + "' )  ";
					
					System.out.println("2.Cap nhat DG: " + query);
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi ERP_DONTRAHANG_SANPHAM: " + query;
						db.getConnection().rollback();
						return false;
					}
				}*/
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
		String query = "select PK_SEQ, MA + ' - ' + TEN as TEN from ERP_KHOTT  WHERE trangthai = '1' AND PK_SEQ IN "+util.quyen_khoTT(userId)+" ";
		
		this.khoNhanRs = db.get(query);
		
		//this.dvtRs = db.getScrol("select PK_SEQ, DONVI from DONVIDOLUONG where trangthai = '1' ");
		
		query = "select PK_SEQ, MA + ' - ' + TEN as TEN from NHAPHANPHOI where TRANGTHAI = '1' AND isKHACHHANG = '1'  AND PK_SEQ != '1' order by MA + ' - ' + TEN asc ";
		if(this.xuatcho.equals("1"))
			query = "select PK_SEQ, MA + ' - ' + TEN as TEN from NHAPHANPHOI where TRANGTHAI = '1' AND isKHACHHANG = '1'  AND PK_SEQ != '1' order by MA + ' - ' + TEN asc ";
		this.khRs = db.get(query);
		
	}

	private void createChuyenKho_SanPham() 
	{
		String query =  "select b.MA, b.TEN, DV.donvi, sum( a.soluong ) as soluong, avg ( a.dongia ) as dongia, avg( isnull(a.vat, 0) ) as vat, sum( isnull(a.tienVAT, 0) ) as tienVAT " +
						" from ERP_DONTRAHANG_SANPHAM a inner Join ERP_SanPham b on a.SANPHAM_FK = b.PK_SEQ    " +
						" 		INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = b.DVDL_FK       " +
						"where a.dontrahang_fk = '" + this.id + "' group by b.MA, b.TEN, DV.donvi ";
		
		System.out.println("---INIT SP: " + query);
		ResultSet rs = db.get(query);
		
		NumberFormat formater = new DecimalFormat("##,###,###.####");
		if(rs != null)
		{
			try 
			{
				String spMA = "";
				String spTEN = "";
				String spDONVI = "";
				String spDONGIA = "";
				String spVAT = "";
				String spTienVAT = "";
				
				while(rs.next())
				{
					if(!spMA.contains(rs.getString("MA")) )
					{
						spMA += rs.getString("MA") + "__";
						spTEN += rs.getString("TEN") + "__";
						spDONVI += rs.getString("DONVI") + "__";
						spDONGIA += formater.format(rs.getDouble("dongia")) + "__";
						spVAT += rs.getString("VAT") + "__";
						spTienVAT += formater.format(rs.getDouble("tienVAT")) + "__"; 
					}
				}
				rs.close();
				
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
					
					spTienVAT = spTienVAT.substring(0, spTienVAT.length() - 2);
					this.spTienVat = spTienVAT.split("__");

				}
				
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				System.out.println("115.Exception: " + e.getMessage());
			}
		}
		
		
		query = "select b.MA, b.TEN, DV.donvi, a.soluong, a.dongia, isnull(a.vat, 0) as vat, a.solo, isnull(a.ngayhethan, ' ') as ngayhethan,    " +
				" 	a.MAME, a.MATHUNG, a.MAPHIEU, marq, hamluong, hamam, soluong, ' ' as scheme, " +
				"	isnull( ( select MA from ERP_BIN where pk_seq = a.bin_fk ) , '' ) as vitriTEN, isnull(a.phieudt, '') as phieudt, isnull(a.phieueo, '') as phieueo " +
				" from ERP_DONTRAHANG_SANPHAM a inner Join ERP_SanPham b on a.SANPHAM_FK = b.PK_SEQ    " +
				" 		INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = b.DVDL_FK       " +
				"where a.dontrahang_fk = '" + this.id + "' order by a.stt asc ";
		
		System.out.println("---INIT SP CHI TIET: " + query);
		rs = db.get(query);
		
		if(rs != null)
		{
			try 
			{
				Hashtable<String, String> sp_chitiet = new Hashtable<String, String>();
				while(rs.next())
				{
					String key = rs.getString("MA") + "__" + rs.getString("solo").trim()+ "__" + rs.getString("ngayhethan") 
							+ "__" + rs.getString("MAME") + "__" + rs.getString("MATHUNG") + "__" + rs.getString("vitriTEN")
							+ "__" + rs.getString("MAPHIEU").trim() + "__" + rs.getString("phieudt").trim() + "__" + rs.getString("phieueo").trim()
							+ "__" + rs.getString("marq").trim() + "__" + rs.getString("hamluong") + "__" + rs.getString("hamam");
					
					
					System.out.println("::: KEY BEAN: " + key );
					sp_chitiet.put(key, rs.getString("soluong"));
					
				}
				rs.close();
			
				this.sp_chitiet = sp_chitiet;
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
		String query = "select isnull(ngaynhapkho, '') as ngaynhapkho, ngaytra, ISNULL(ghichu, '') as ghichu, xuatcho, doituongId, trangthai, khott_fk, isnull(kyhieu, '') as kyhieu, isnull( sohoadon, '' ) as sohoadon " +
						"from ERP_DONTRAHANG where pk_seq = '" + this.id + "'";
		System.out.println("____INIT NHAP KHO: " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					this.tungay = rs.getString("ngaynhapkho");
					this.denngay = rs.getString("ngaytra");
					this.ghichu = rs.getString("ghichu");
					this.xuatcho = rs.getString("xuatcho");
					this.khId = rs.getString("doituongId");
					this.trangthai = rs.getString("trangthai");
					this.khoNhanId = rs.getString("khott_fk") == null ? "" : rs.getString("khott_fk");
					this.kyhieu = rs.getString("kyhieu");
					this.sohoadon = rs.getString("sohoadon");
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
						" from ERP_HANGCHOPHANBO a inner Join SanPham b on a.SANPHAM_FK = b.PK_SEQ    " +
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
							"from SANPHAM where MA = '" + arr[0] + "' ";
					
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
						   " where KHOTT_FK in (100023,100058) and SANPHAM_FK = ( select pk_seq from SANPHAM where ma = '" + spMa + "' ) " + 
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
	
	public String[] getSpTienVat() {

		return this.spTienVat;
	}

	public void setSpTienVat(String[] spTienVat) {
		
		this.spTienVat = spTienVat;
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

	
	public String[] getSpSTT() {

		return this.spSTT;
	}


	public void setSpTT(String[] spSTT) {
		
		this.spSTT = spSTT;
	}

	 
}
