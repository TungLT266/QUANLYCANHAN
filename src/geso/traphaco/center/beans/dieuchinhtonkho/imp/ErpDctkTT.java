package geso.traphaco.center.beans.dieuchinhtonkho.imp;

import geso.traphaco.center.beans.dieuchinhtonkho.IErpDctkTT;
import geso.traphaco.center.db.sql.dbutils;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

public class ErpDctkTT implements IErpDctkTT
{
	private static final long serialVersionUID = 1L;
	String userId;
	String id;
	
	String ngayyeucau;
	String ghichu;

	String msg;
	String trangthai;
	
	String khoNhanId;
	ResultSet khoNhanRs;

	ResultSet spRs;
	
	String[] spId;
	String[] spMa;
	String[] spTen;
	String[] spDonvi;
	String[] spSoluong;
	String[] spGianhap;
	String[] spSolo;
	String[] spTonkho;
	String[] spBooked;
	String[] spNgaysanxuat;
	String[] spNgayhethan;
	
	Hashtable<String, String> sp_chitiet;
	
	dbutils db;
	
	public ErpDctkTT()
	{
		this.id = "";
		this.ngayyeucau = getDateTime();
		this.ghichu = "";
		this.khoNhanId = "";
		this.msg = "";
		this.trangthai = "0";
		
		this.sp_chitiet = new Hashtable<String, String>();
		this.db = new dbutils();
	}
	
	public ErpDctkTT(String id)
	{
		this.id = id;
		this.ngayyeucau = getDateTime();
		this.ghichu = "";
		this.khoNhanId = "";
		this.msg = "";
		this.trangthai = "0";

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

	public String getNgayyeucau() 
	{
		return this.ngayyeucau;
	}

	public void setNgayyeucau(String ngayyeucau) 
	{
		this.ngayyeucau = ngayyeucau;
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
		if(this.ngayyeucau.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập ngày điều chỉnh";
			return false;
		}
		
		if( this.khoNhanId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn kho điều chỉnh";
			return false;
		}
		
		if( this.sp_chitiet.size() <= 0 )
		{
			this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm nhập";
			return false;
		}
		else
		{
			boolean coSP = false;
			for(int i = 0; i < spMa.length; i++)
			{
				if(spMa[i].trim().length() > 0 && spSoluong[i].trim().length() > 0 && spTonkho[i].trim().length() > 0 )
				{
					if(!spSoluong[i].trim().equals(spTonkho[i].trim()) )
					{
						coSP = true;
					}
				}
			}
			
			if(!coSP)
			{
				this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm nhập";
				return false;
			}
		}
		
		try
		{
			//THANG CHOT PHIEU XUAT KHO BUOC PHAI SAU THANG KS + 1
			String query = "select top(1) NAM as namMax, THANGKS as thangMax, " +
							"	'" + this.ngayyeucau + "' as ngaylapphieu " +
							"from ERP_KHOASOTHANG  " +
							"order by NAM desc, THANGKS desc ";
			System.out.println("1.Khoi tao thang: " + query);
			ResultSet rs = db.get(query);
			
			try
			{
				if(rs != null)
				{
					while(rs.next())
					{
						String thangHL = "";
						String namHL = "";
						
						String thangKs = rs.getString("thangMax");
						String namKs = rs.getString("namMax"); 
				
						String nam = rs.getString("ngaylapphieu").substring(0, 4);
						String thang = rs.getString("ngaylapphieu").substring(5, 7);
						
						if(thangKs.equals("12"))
						{
							thangHL = "1";
							namHL = Integer.toString(Integer.parseInt(namKs) + 1);
						}
						else
						{
							thangHL = Integer.toString(Integer.parseInt(thangKs) + 1);
							namHL = namKs;
						}
						
						if(thangHL.trim().length() < 2)
							thangHL = "0" + thangHL;
						
						if(	!thangHL.equals(thang) || !namHL.equals(nam) )
						{
							msg = "Bạn chỉ được phép chốt kiểm kê sau tháng khóa sổ ( " + thangKs + " ) 1 tháng";
							rs.close();
							return false;
						}
						
					}
					rs.close();
				}
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				this.msg = "Lỗi khi chốt xuất kiểm kê " + e.getMessage();
				return false;
			}
			
			
			db.getConnection().setAutoCommit(false);
		
			//CHECK SO LUOGN THUC TE KHONG DUOC NHO HON BOOKED NEU DANG CO
			for(int i = 0; i < spMa.length; i++)
			{
				if(spMa[i].trim().length() > 0 && spSoluong[i].trim().length() > 0 && spTonkho[i].trim().length() > 0 )
				{
					if(!spSoluong[i].trim().equals(spTonkho[i].trim()) )
					{
	        			//CHECK TON KHO THUC TE TRONG KHO
						String sql = "select SOLUONG, BOOKED, AVAILABLE " +
									 "from ERP_KHOTT_SP_CHITIET where khott_fk = '" + this.khoNhanId + "' and sanpham_fk = ( select pk_seq from SANPHAM where MA = '" + spMa[i] + "' ) and SOLO = '" + spSolo[i] + "' ";
						ResultSet rsTK = db.get(sql);
						if(rsTK.next())
						{
							double soluong = rsTK.getDouble("SOLUONG");
							double avai = rsTK.getDouble("AVAILABLE");
							double booked = rsTK.getDouble("BOOKED");
							
							double dieuchinh = Double.parseDouble(spSoluong[i].trim().replaceAll(",", ""));
							/*if(dieuchinh < soluong)
							{
								this.msg = "Tồn cuối sau điều chỉnh ( " + spSoluong[i].trim() + " ) của sản phẩm ( " + spMa[i] +  " ) với số lô ( " + spSolo[i] + " ) không được nhỏ hơn số lượng tồn trong kho ( " + soluong + " )   ";
								rsTK.close();
								db.getConnection().rollback();
								return false;
							}*/

							if(dieuchinh < booked)
							{
								this.msg = "Tồn cuối sau điều chỉnh ( " + spSoluong[i].trim() + " ) của sản phẩm ( " + spMa[i] +  " ) với số lô ( " + spSolo[i] + " ) không được nhỏ hơn số lượng BOOKED trong kho ( " + booked + " )   ";
								rsTK.close();
								db.getConnection().rollback();
								return false;
							}
						}
						rsTK.close();
					}
				}	
			}
			
			query = " insert ERP_DIEUCHINHTONKHO(ngaydieuchinh, ghichu, trangthai, khott_fk, ngaytao, nguoitao, ngaysua, nguoisua) " +
					" values('" + this.ngayyeucau + "', N'" + this.ghichu + "', '1', " + this.khoNhanId + ", '" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "' )";
			
			System.out.println("1.Insert NK: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_DIEUCHINHTONKHO " + query;
				db.getConnection().rollback();
				return false;
			}
			
			for(int i = 0; i < spMa.length; i++)
			{
				if(spMa[i].trim().length() > 0 && spSoluong[i].trim().length() > 0 && spTonkho[i].trim().length() > 0 )
				{
					if(!spSoluong[i].trim().equals(spTonkho[i].trim()) )
					{
						//CHECK TON KHO THUC TE TRONG KHO
						String sql = "select SANPHAM_FK, SOLUONG, BOOKED, AVAILABLE " +
									 "from ERP_KHOTT_SP_CHITIET where khott_fk = '" + this.khoNhanId + "' and sanpham_fk = ( select pk_seq from SANPHAM where MA = '" + spMa[i] + "' ) and SOLO = '" + spSolo[i] + "' ";
						System.out.println("2.Check TK: " + sql);
						ResultSet rsTK = db.get(sql);
						if(rsTK.next())
						{
							String sanpham_fk = rsTK.getString("SANPHAM_FK");
							double soluong = rsTK.getDouble("SOLUONG");
							double booked = rsTK.getDouble("BOOKED");
							
							double dieuchinh = Double.parseDouble(spSoluong[i].trim().replaceAll(",", ""));
							double chenhlech = dieuchinh - soluong;
							
							query = "insert ERP_DIEUCHINHTONKHO_SANPHAM( dieuchinh_fk, SANPHAM_FK, donvi, solo, ngaysanxuat, ngayhethan, tonkho, booked, tonthucte ) " +
									"select IDENT_CURRENT('ERP_DIEUCHINHTONKHO'), pk_seq, ( select DONVI from DONVIDOLUONG where pk_seq = a.DVDL_FK ), '" + this.spSolo[i] + "', '" + this.spNgaysanxuat[i] + "', '" + this.spNgayhethan[i] + "', '" + soluong + "', '" + booked + "', '" + this.spSoluong[i].replaceAll(",", "") + "' " +
									"from SANPHAM a where MA = '" + this.spMa[i].trim() + "' ";
							
							System.out.println("3.Insert NK - SP: " + query);
							if(!db.update(query))
							{
								this.msg = "Khong the tao moi ERP_DIEUCHINHTONKHO_SANPHAM: " + query;
								db.getConnection().rollback();
								return false;
							}
							
							query = " Update ERP_KHOTT_SANPHAM set soluong = soluong + '" + chenhlech + "', available = available + '" + chenhlech + "' " +
									" where khott_fk = '" + this.khoNhanId + "' and sanpham_fk = '" + sanpham_fk + "' ";
							if(!db.update(query))
							{
								this.msg = "Khong the cap nhat ERP_KHOTT_SANPHAM: " + query;
								db.getConnection().rollback();
								return false;
							}
							
							query = " Update ERP_KHOTT_SP_CHITIET set soluong = soluong + '" + chenhlech + "', available = available + '" + chenhlech + "' " +
									" where khott_fk = '" + this.khoNhanId + "' and sanpham_fk = '" + sanpham_fk + "' and solo = '" + spSolo[i] + "' ";
							if(!db.update(query))
							{
								this.msg = "Khong the cap nhat ERP_KHOTT_SP_CHITIET: " + query;
								db.getConnection().rollback();
								return false;
							}
							
						}
						rsTK.close();

					}
				}	
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			db.update("rollback");
			e.printStackTrace();
			this.msg = "Exception: " + e.getMessage();
			return false;
		}
		
		return true;
	}

	public boolean updateNK() 
	{
		if(this.ngayyeucau.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập ngày nhập kho";
			return false;
		}
		
		if( this.khoNhanId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn kho nhận";
			return false;
		}
		
		if(spMa == null)
		{
			this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm nhập";
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
			for(int i = 0; i < spMa.length; i++)
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
			}	
		}
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String khonhan_fk = this.khoNhanId.trim().length() <= 0 ? "null" : this.khoNhanId.trim();
			String query =	" Update ERP_NHAPKHO set ngaynhap = '" + this.ngayyeucau + "', ghichu = N'" + this.ghichu + "', " +
							" khonhap_fk = " + khonhan_fk + ", ngaysua = '" + getDateTime() + "', nguoisua = '" + this.userId + "' " + 
							" where pk_seq = '" + this.id + "' ";
		
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_CHUYENKHO " + query;
				db.getConnection().rollback();
				return false;
			}
						
			query = "delete ERP_NHAPKHO_SANPHAM where nhapkho_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_NHAPKHO_SANPHAM " + query;
				db.getConnection().rollback();
				return false;
			}
			
			for(int i = 0; i < spMa.length; i++)
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
                			
                			if(_ct[0].trim().length() > 0 && _ct[1].trim().length() > 0 && _ct[2].trim().length() > 0 )
                			{
	                			query = "insert ERP_NHAPKHO_SANPHAM( nhapkho_fk, SANPHAM_FK, DVDL_FK, soluong, gianhap, solo, ngaysanxuat, ngayhethan ) " +
										"select '" + this.id + "', pk_seq, '100018', '" + _ct[0].trim().replaceAll(",", "") + "', '0', N'" + _ct[1].trim() + "', '" + _ct[2].trim() + "', convert(varchar(10), DATEADD(dd, isnull(hansudung, 0), '" + _ct[2].trim() + "'), 120) " +
										"from SANPHAM where MA = '" + spMa[i].trim() + "' ";
								
								System.out.println("1.Insert NK - SP: " + query);
								if(!db.update(query))
								{
									this.msg = "Khong the tao moi ERP_NHAPKHO_SANPHAM: " + query;
									db.getConnection().rollback();
									return false;
								}
                			}
                			
                		}
                	}
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			db.update("rollback");
			this.msg = "Exception: " + e.getMessage();
			return false;
		}
		
		return true;
	}


	public void createRs() 
	{
		String query = "select PK_SEQ, TEN from ERP_KHOTT";
		this.khoNhanRs = db.get(query);
		
		if( this.khoNhanId.trim().length() > 0 && this.id.trim().length() <= 0  )
		{
			query = " 	select b.ma, b.ten, c.donvi, a.solo, a.ngaysanxuat, a.ngayhethan, a.soluong, a.booked  " +
					"	from ERP_KHOTT_SP_CHITIET a inner join SANPHAM b on a.sanpham_fk = b.pk_seq " +
					"		inner join DONVIDOLUONG c on b.DVDL_FK = c.pk_seq " +
					"	where a.khoTT_FK = '" + this.khoNhanId + "' " +
					"	order by ma asc, ngayhethan asc ";
			System.out.println("---INIT SP: " + query);
			
			this.spRs = db.get(query);
			
			if(this.sp_chitiet.size() <= 0 )
			{
				ResultSet spRs = db.get(query);
				
				NumberFormat formater = new DecimalFormat("##,###,###.##");
				if(spRs != null)
				{
					try 
					{
						/*String spMA = "";
						String spTEN = "";
						String spDONVI = "";
						String spSOLO = "";
						String spNGAYSANXUAT = "";
						String spNGAYHETHAN = "";
						String spTONKHO = "";
						String spTONTHUCTE = "";*/
						
						while(spRs.next())
						{
							/*spMA += spRs.getString("MA") + "__";
							spTEN += spRs.getString("TEN") + "__";
							spDONVI += spRs.getString("DONVI") + "__";
							spSOLO += spRs.getString("solo") + "__";
							spNGAYSANXUAT += spRs.getString("ngaysanxuat") + "__";
							spNGAYHETHAN += spRs.getString("ngayhethan") + "__";
							spTONKHO += formater.format(spRs.getDouble("soluong")) + "__";
							spTONTHUCTE += formater.format(spRs.getDouble("soluong")) + "__";*/
							
							this.sp_chitiet.put(spRs.getString("MA") + "__" + spRs.getString("solo"), formater.format(spRs.getDouble("soluong")));
							
						}
						spRs.close();
						
						/*if(spMA.trim().length() > 0)
						{
							spMA = spMA.substring(0, spMA.length() - 2);
							this.spMa = spMA.split("__");
							
							spTEN = spTEN.substring(0, spTEN.length() - 2);
							this.spTen = spTEN.split("__");
							
							spDONVI = spDONVI.substring(0, spDONVI.length() - 2);
							this.spDonvi = spDONVI.split("__");
							
							spSOLO = spSOLO.substring(0, spSOLO.length() - 2);
							this.spSolo = spSOLO.split("__");
							
							spNGAYSANXUAT = spNGAYSANXUAT.substring(0, spNGAYSANXUAT.length() - 2);
							this.spNgaysanxuat = spNGAYSANXUAT.split("__");
							
							spNGAYHETHAN = spNGAYHETHAN.substring(0, spNGAYHETHAN.length() - 2);
							this.spNgayhethan = spNGAYHETHAN.split("__");
							
							spTONKHO = spTONKHO.substring(0, spTONKHO.length() - 2);
							this.spTonkho = spTONKHO.split("__");
							
							spTONTHUCTE = spTONTHUCTE.substring(0, spTONTHUCTE.length() - 2);
							this.spSoluong = spTONTHUCTE.split("__");
						}*/
					} 
					catch (Exception e) 
					{
						System.out.println("115.Exception: " + e.getMessage());
					}
				}
			}
		}
		
	}

	private void createChuyenKho_SanPham() 
	{
		String query =  " select b.ma, b.ten, a.donvi, a.solo, a.ngaysanxuat, a.ngayhethan, a.tonkho, a.booked, a.tonthucte " +
						" from ERP_DIEUCHINHTONKHO_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.pk_seq " +
						" where a.dieuchinh_fk = '" + this.id + "' ";
		
		System.out.println("---INIT SP: " + query);
		
		this.spRs = db.get(query);
		
	}

	public void init() 
	{
		String query = "select ngaydieuchinh, ISNULL(ghichu, '') as ghichu, khott_fk, trangthai " +
						"from ERP_DIEUCHINHTONKHO where pk_seq = '" + this.id + "'";
		System.out.println("____INIT NHAP KHO: " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					this.ngayyeucau = rs.getString("ngaydieuchinh");
					this.ghichu = rs.getString("ghichu");
					this.khoNhanId = rs.getString("khott_fk");
					this.trangthai = rs.getString("trangthai");
				}
				rs.close();
			} 
			catch (Exception e) 
			{
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

	
	public Hashtable<String, String> getSp_Chitiet() {
		
		return this.sp_chitiet;
	}

	
	public void setSp_Chitiet(Hashtable<String, String> sp_chitiet) {
		
		this.sp_chitiet = sp_chitiet;
	}

	
	public String[] getSpTonkho() {

		return this.spTonkho;
	}


	public void setSpTonkho(String[] spTonkho) {
		
		this.spTonkho = spTonkho;
	}

	
	public String[] getSpBooked() {

		return this.spBooked;
	}


	public void setSpBooked(String[] spBooked) {
		
		this.spBooked = spBooked;
	}

	
	public ResultSet getSanphamRs() {

		return this.spRs;
	}


	public void setSanphamRs(ResultSet spRs) {
		
		this.spRs = spRs;
	}

	
	
}
