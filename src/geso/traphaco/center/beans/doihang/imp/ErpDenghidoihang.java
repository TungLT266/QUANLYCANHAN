package geso.traphaco.center.beans.doihang.imp;

import geso.traphaco.center.beans.doihang.IErpDenghidoihang;
import geso.traphaco.center.db.sql.dbutils;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

public class ErpDenghidoihang implements IErpDenghidoihang
{
	private static final long serialVersionUID = 1L;
	String userId;
	String id;
	
	String ngayyeucau;
	String ngaydenghi;
	String ghichu;

	String msg;
	String trangthai;
	
	String loaidonhang;  //0 đơn đặt hàng, 1 đơn hàng khuyến mại ứng hàng, 3 đơn hàng khuyến mại tích lũy, 4 đơn hàng trưng bày, 5 đơn hàng khác
	String chietkhau;
	String vat;
	
	String khoNhanId;
	ResultSet khoNhanRs;
	
	String nppId;
	ResultSet nppRs;
	
	String dvkdId;
	ResultSet dvkdRs;
	
	String kbhId;
	ResultSet kbhRs;
	
	ResultSet sanphamRs;
	ResultSet sanphamDOIRs;
	
	String tsdh;
	
	dbutils db;
	
	public ErpDenghidoihang()
	{
		this.id = "";
		this.ngayyeucau = getDateTime();
		this.ngaydenghi = "";
		this.ghichu = "";
		this.khoNhanId = "";
		this.nppId = "";
		this.msg = "";
		this.loaidonhang = "0";
		this.trangthai = "0";
		this.chietkhau = "0";
		this.vat = "10";
		this.dvkdId = "";
		this.kbhId = "";
		this.tsdh = "6";
		
		this.db = new dbutils();
	}
	
	public ErpDenghidoihang(String id)
	{
		this.id = id;
		this.ngayyeucau = getDateTime();
		this.ngaydenghi = "";
		this.ghichu = "";
		this.khoNhanId = "";
		this.nppId = "";
		this.msg = "";
		this.loaidonhang = "0";
		this.trangthai = "0";
		this.chietkhau = "0";
		this.vat = "10";
		this.dvkdId = "";
		this.kbhId = "";
		this.tsdh = "1";
		
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

	
	public boolean createNK(HttpServletRequest request) 
	{
		/*if(this.ngaydenghi.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày đề nghị";
			return false;
		}

		if( this.dvkdId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn đơn vị kinh doanh";
			return false;
		}
		
		if( this.kbhId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn kênh bán hàng";
			return false;
		}
		
		if( this.nppId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn nhà phân phối đổi hàng";
			return false;
		}
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String khonhan_fk = this.khoNhanId.trim().length() <= 0 ? "null" : this.khoNhanId.trim();
			String vat = this.vat.trim().length() <= 0 ? "0" : this.vat.trim();
			
			String query = " insert ERP_DeNghiDoiHang(ngaydenghi, ghichu, trangthai, dvkd_fk, kbh_fk, npp_fk, kho_fk, chietkhau, vat, ngaytao, nguoitao, ngaysua, nguoisua) " +
						   " values('" + this.ngaydenghi + "', N'" + this.ghichu + "', '0', '" + dvkdId + "', '" + kbhId + "', '" + nppId + "', " + khonhan_fk + ", '0', '" + vat + "', '" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "' )";
			
			System.out.println("1.Insert DDH: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_DeNghiDoiHang " + query;
				db.getConnection().rollback();
				return false;
			}
			
			//LAY ID
			ResultSet rsDDH = db.get("select IDENT_CURRENT('ERP_DeNghiDoiHang') as ID ");
			if(rsDDH.next())
			{
				this.id = rsDDH.getString("ID");
			}
			rsDDH.close();
			
			String[] spId = request.getParameterValues("spID");
			String[] spTEN = request.getParameterValues("spTEN");
			String[] spSOLO = request.getParameterValues("spSOLO");
			String[] spSOLUONG = request.getParameterValues("spSOLUONG");
			String[] spTONKHO = request.getParameterValues("spTONKHO");
			String[] spDONGIA = request.getParameterValues("spDONGIA");
			
			if( spId != null )
			{
				for(int i = 0; i < spId.length; i++ )
				{
					if(spSOLO[i].trim().length() > 0 && spSOLUONG[i].trim().length() > 0)
					{
						String[] soLO = spSOLO[i].split(" --- ");
						
						//CHECK TON KHO
						query = "select AVAILABLE " +
								"from NHAPP_KHO_CHITIET where SOLO = '" + soLO[0] + "' and KBH_FK = '" + this.kbhId + "' and SANPHAM_FK = '" + spId[i] + "' and KHO_FK = '" + this.khoNhanId + "' and NPP_FK = '" + this.nppId + "'  ";
						double avai = 0;
						ResultSet rsCHECK = db.get(query);
						if(rsCHECK.next())
						{
							avai = rsCHECK.getDouble("AVAILABLE");
						}
						rsCHECK.close();
						
						if(avai < Double.parseDouble(spSOLUONG[i].trim().replaceAll(",", "")))
						{
							this.msg = "Sản phẩm ( " + spTEN[i] + " ) với số lô ( " + soLO[0] + " ) với số lượng đề nghị ( " + spSOLUONG[i] + " ) không đủ tồn kho ( " + avai + " ) ";
							db.getConnection().rollback();
							return false;
						}
						
						query = "insert ERP_DENGHIDOIHANG_SANPHAM(denghidoihang_fk, sanpham_fk, dvdl_fk, solo, tonkho, denghi, dongia) " +
								"select '" + this.id + "', '" + spId[i] + "', DVDL_FK, '" + soLO[0] + "', '" + spTONKHO[i].replaceAll(",", "") + "', '" + spSOLUONG[i].replaceAll(",", "") + "', '" + spDONGIA[i].replaceAll(",", "") + "' from SANPHAM " +
								"where pk_seq = '" + spId[i] + "' ";
						
						System.out.println("--INSERT SP: " + query);
						if(!db.update(query))
						{
							this.msg = "Không thể tạo mới ERP_DENGHIDOIHANG_SANPHAM " + query;
							db.getConnection().rollback();
							return false;
						}
						
						//UPDATE KHO
						query = "update NHAPP_KHO_CHITIET set AVAILABLE = AVAILABLE - '" + spSOLUONG[i].replaceAll(",", "") + "', BOOKED = BOOKED + '" + spSOLUONG[i].replaceAll(",", "") + "' " +
								"where SOLO = '" + soLO[0] + "' and KBH_FK = '" + this.kbhId + "' and SANPHAM_FK = '" + spId[i] + "' and KHO_FK = '" + this.khoNhanId + "' and NPP_FK = '" + this.nppId + "'  ";
						if(!db.update(query))
						{
							this.msg = "Không thể cập nhật NHAPP_KHO_CHITIET " + query;
							db.getConnection().rollback();
							return false;
						}
						
						
						query = "update NHAPP_KHO set AVAILABLE = AVAILABLE - '" + spSOLUONG[i].replaceAll(",", "") + "', BOOKED = BOOKED + '" + spSOLUONG[i].replaceAll(",", "") + "' " +
								"where KBH_FK = '" + this.kbhId + "' and SANPHAM_FK = '" + spId[i] + "' and KHO_FK = '" + this.khoNhanId + "' and NPP_FK = '" + this.nppId + "'  ";
						if(!db.update(query))
						{
							this.msg = "Không thể cập nhật NHAPP_KHO " + query;
							db.getConnection().rollback();
							return false;
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
		*/
		
		return true;
	}

	public boolean updateNK(HttpServletRequest request) 
	{
		if(this.ngaydenghi.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày duyệt";
			return false;
		}
		
		String[] spId = request.getParameterValues("spID");
		String[] spSOLO = request.getParameterValues("spSOLO");
		String[] spDUYET = request.getParameterValues("spDUYET");
		String[] spIDDUYET = request.getParameterValues("spIDDUYET");
		
		if(spId == null)
		{
			this.msg = "Vui lòng kiểm tra lại thông tin sản phẩm duyệt";
			return false;
		}
		else
		{
			boolean coSP = false;
			for(int i = 0; i < spId.length; i++ )
			{
				if(spDUYET[i].trim().length() > 0)
				{
					if(Double.parseDouble(spDUYET[i].trim()) > 0)
						coSP = true;
				}
				
			}
			
			if(!coSP)
			{
				this.msg = "Không có sản phẩm nào được nhập số lượng / số tiền duyệt";
				return false;
			}
		}
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query = " update ERP_DeNghiDoiHang set ngayduyet = N'" + this.ngaydenghi + "', ghichu = N'" + this.ghichu + "', " +
						   "	ngaysua = '" + this.getDateTime() + "', nguoisua = '" + this.userId + "' " +
						   " where pk_seq = '" + this.id + "' ";
			System.out.println("1.update DDH: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_DeNghiDoiHang " + query;
				db.getConnection().rollback();
				return false;
			}
			
			if( spId != null )
			{
				for(int i = 0; i < spId.length; i++ )
				{
					if(spDUYET[i].trim().length() > 0)
					{
						String sp_IDDUYET = "NULL";
						if(spIDDUYET[i].trim().length() > 0)
							sp_IDDUYET = spIDDUYET[i].trim();
						
						query = "Update ERP_DENGHIDOIHANG_SANPHAM set DUYET = '" + spDUYET[i].trim().replaceAll(",", "") + "', " +
								"				SANPHAM_DUYET_FK = '" + sp_IDDUYET + "' " +
								"where denghidoihang_fk = '" + this.id + "' and sanpham_fk = '" + spId[i] + "' and SOLO = '" + spSOLO[i] + "' ";
						
						System.out.println("--UPDATE SP: " + query);
						if(!db.update(query))
						{
							this.msg = "Không thể cập nhật ERP_DENGHIDOIHANG_SANPHAM " + query;
							db.getConnection().rollback();
							return false;
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
		this.khoNhanRs = db.get("select PK_SEQ, TEN from KHO where trangthai = '1' and loaikho = '1' ");
		
		this.dvkdRs = db.get("select PK_SEQ, DONVIKINHDOANH as TEN from DONVIKINHDOANH where TRANGTHAI = '1' ");
		this.kbhRs = db.get("select PK_SEQ, DIENGIAI as TEN from KENHBANHANG where TRANGTHAI = '1' and PK_SEQ in ( select KBH_FK from NHAPP_KBH where NPP_FK = '" + this.nppId + "' ) ");
		
		String query = "select PK_SEQ, MA + ' - ' + TEN as TEN from NHAPHANPHOI where TRANGTHAI = '1'  ";
		this.nppRs = db.get(query);
		
		this.sanphamDOIRs = db.getScrol("select pk_seq, ten from SANPHAM where trangthai = '1' ");
		
		/*if(this.nppId.trim().length() > 0)
		{
			//INIT SAN PHAM
			query = " select a.PK_SEQ, a.MA, a.TEN, b.DONVI, BGIA.giamua as donGIA   " +
					" from SANPHAM a inner join DONVIDOLUONG b on a.DVDL_FK = b.PK_SEQ   " +
					" 	 left join   " +
					" 	 (  	 " +
					" 		 select a.PK_SEQ, a.DVDL_FK as dvCHUAN,  isnull( (  select GIAMUA_SAUCK  " +
					" 															from BGMUANPP_SANPHAM    					  " +
					" 															where SANPHAM_FK = a.pk_seq    and BGMUANPP_FK in ( select top(1) PK_SEQ from BANGGIAMUANPP bg inner join BANGGIAMUANPP_NPP bg_npp on bg.PK_SEQ = bg_npp.BANGGIAMUANPP_FK  where bg.TRANGTHAI = '1' and bg_npp.NPP_FK = '" + this.nppId + "' and bg.DVKD_FK = '" + this.dvkdId + "' and bg.KENH_FK = '" + this.kbhId + "'  order by bg.TUNGAY desc ) ), 0) as giamua    	 " +
					" 		 from SANPHAM a   " +
					" 	 )   " +
					" 	 BGIA on a.PK_SEQ = BGIA.PK_SEQ  " +
					" where a.pk_seq in ( select sanpham_fk from ERP_DENGHIDOIHANG_SANPHAM where denghidoihang_fk = '" + this.id + "' )  ";
			
			System.out.println("--INIT SAN PHAM: " + query);
			this.sanphamRs = db.get(query);
			
		}*/
		
	}

	public void init() 
	{
		String query = "select ngaydenghi, ISNULL(ghichu, '') as ghichu, dvkd_fk, kbh_fk, npp_fk, kho_fk, chietkhau, vat " +
						"from ERP_Denghidoihang where pk_seq = '" + this.id + "'";
		System.out.println("____INIT DNDH: " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					this.ngaydenghi = rs.getString("ngaydenghi");
					this.ghichu = rs.getString("ghichu");
					this.dvkdId = rs.getString("dvkd_fk");
					this.kbhId = rs.getString("kbh_fk");
					this.nppId = rs.getString("npp_fk");
					this.khoNhanId = rs.getString("kho_fk");
					this.chietkhau = rs.getString("chietkhau");
					this.vat = rs.getString("vat");
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				System.out.println("---LOI INIT: " + e.getMessage());
			}
			
			//INIT SO LUONG DAT
			query = "select b.pk_seq, b.MA, b.TEN, ( select DONVI from DONVIDOLUONG where pk_seq = b.DVDL_FK ) as donvi, a.denghi, a.solo, " +
					"	isnull(a.duyet, 0) as duyet, isnull(SANPHAM_DUYET_FK, -1) as SANPHAM_DUYET_FK  " +
					"from ERP_DeNghiDoiHang_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ   " +
					"	inner join ERP_DeNghiDoiHang c on a.denghidoihang_fk= c.pk_seq  " +
					"where a.denghidoihang_fk = '" + this.id + "' and denghi != 0 ";
			
			this.sanphamRs = db.get(query);
			
		}
		
		this.createRs();
		
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
	
	private String getDateTime() 
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

	public String getNppId() {
		
		return this.nppId;
	}

	
	public void setNppId(String nppId) {
		
		this.nppId = nppId;
	}

	
	public ResultSet getNppRs() {
		
		return this.nppRs;
	}

	
	public void setNppRs(ResultSet nppRs) {
		
		this.nppRs = nppRs;
	}

	
	public String getLoaidonhang() {
		
		return this.loaidonhang;
	}

	
	public void setLoaidonhang(String loaidonhang) {
		
		this.loaidonhang = loaidonhang;
	}

	
	public String getChietkhau() {
		
		return this.chietkhau;
	}

	
	public void setChietkhau(String chietkhau) {
		
		this.chietkhau = chietkhau;
	}

	
	public String getVat() {
		
		return this.vat;
	}

	
	public void setVat(String vat) {
		
		this.vat = vat;
	}

	
	public String getDvkdId() {
		
		return this.dvkdId;
	}

	
	public void setDvkdId(String dvkdId) {
		
		this.dvkdId = dvkdId;
	}

	
	public ResultSet getDvkdRs() {
		
		return this.dvkdRs;
	}

	
	public void setDvkdRs(ResultSet dvkdRs) {
		
		this.dvkdRs = dvkdRs;
	}

	
	public String getKbhId() {
		
		return this.kbhId;
	}

	
	public void setKbhId(String kbhId) {
		
		this.kbhId = kbhId;
	}

	
	public ResultSet getKbhRs() {
		
		return this.kbhRs;
	}

	
	public void setKbhRs(ResultSet kbhRs) {
		
		this.kbhRs = kbhRs;
	}

	public String getNgaydenghi() {
		
		return this.ngaydenghi;
	}

	public void setNgaydenghi(String ngaydenghi) {
		
		this.ngaydenghi = ngaydenghi;
	}
	

	public ResultSet getSanphamRs() {
		
		return this.sanphamRs;
	}

	
	public void setSanphamRs(ResultSet spRs) {
		
		this.sanphamRs = spRs;
	}
	
	
	public ResultSet getSanphamDoiRs() {

		return this.sanphamDOIRs;
	}


	public void setSanphamDoiRs(ResultSet spDoiRs) {

		this.sanphamDOIRs = spDoiRs;
	}


	public boolean duyetDH(HttpServletRequest request)
	{
		if(this.ngaydenghi.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày duyệt";
			return false;
		}
		
		String[] spId = request.getParameterValues("spID");
		String[] spSOLO = request.getParameterValues("spSOLO");
		String[] spDUYET = request.getParameterValues("spDUYET");
		String[] spIDDUYET = request.getParameterValues("spIDDUYET");
		
		if(spId == null)
		{
			this.msg = "Vui lòng kiểm tra lại thông tin sản phẩm duyệt";
			return false;
		}
		else
		{
			boolean coSP = false;
			for(int i = 0; i < spId.length; i++ )
			{
				if(spDUYET[i].trim().length() > 0)
				{
					if(Double.parseDouble(spDUYET[i].trim()) > 0)
						coSP = true;
				}
				
			}
			
			if(!coSP)
			{
				this.msg = "Không có sản phẩm nào được nhập số lượng / số tiền duyệt";
				return false;
			}
		}
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query = " update ERP_DeNghiDoiHang set trangthai = '2', ngayduyet = N'" + this.ngaydenghi + "', ghichu = N'" + this.ghichu + "', " +
						   "	ngaysua = '" + this.getDateTime() + "', nguoisua = '" + this.userId + "' " +
						   " where pk_seq = '" + this.id + "' ";
			System.out.println("1.update DDH: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_DeNghiDoiHang " + query;
				db.getConnection().rollback();
				return false;
			}
			
			if( spId != null )
			{
				for(int i = 0; i < spId.length; i++ )
				{
					if(spDUYET[i].trim().length() > 0)
					{
						String sp_IDDUYET = "NULL";
						if(spIDDUYET[i].trim().length() > 0)
							sp_IDDUYET = spIDDUYET[i].trim();
						
						query = "Update ERP_DENGHIDOIHANG_SANPHAM set DUYET = '" + spDUYET[i].trim().replaceAll(",", "") + "', " +
								"				SANPHAM_DUYET_FK = '" + sp_IDDUYET + "' " +
								"where denghidoihang_fk = '" + this.id + "' and sanpham_fk = '" + spId[i] + "' and SOLO = '" + spSOLO[i] + "' ";
						
						System.out.println("--UPDATE SP: " + query);
						if(!db.update(query))
						{
							this.msg = "Không thể cập nhật ERP_DENGHIDOIHANG_SANPHAM " + query;
							db.getConnection().rollback();
							return false;
						}
						
					}
				}
			}
			
			//TU DONG TAO RA 1 DON DAT HANG KHAC
			query = "select count(*) as soDONG " +
					"from ERP_DENGHIDOIHANG_SANPHAM where denghidoihang_fk = '" + this.id + "' and ISNULL(DUYET, 0) != 0 and SANPHAM_DUYET_FK is not null ";
			ResultSet rsCHECK = db.get(query);
			int soDONG = 0;
			if(rsCHECK.next())
			{
				soDONG = rsCHECK.getInt("soDONG");
			}
			rsCHECK.close();
			
			if(soDONG > 0)
			{
				query = " insert ERP_DonDatHang(ngaydonhang, ngaydenghi, loaidonhang, ghichu, trangthai, dvkd_fk, kbh_fk, npp_fk, kho_fk, chietkhau, vat, ngaytao, nguoitao, ngaysua, nguoisua, NOTE) " +
						" select ngaydenghi, ngaydenghi, 0, ghichu, 1 as trangthai, dvkd_fk, kbh_fk, npp_fk, (select KHOSAP from NHAPHANPHOI where PK_SEQ = a.NPP_FK) as KHO_FK, chietkhau, vat, ngaytao, nguoitao, ngaysua, nguoisua, N'Convert từ duyệt đổi hàng số  " + this.id + "'  " +
						" from ERP_DENGHIDOIHANG a where pk_seq = '" + this.id + "' ";
				
				System.out.println("1.Insert DDH: " + query);
				if(!db.update(query))
				{
					msg = "Không thể tạo mới ERP_DeNghiDatHang " + query;
					db.getConnection().rollback();
					return false;
				} 
				
				query = "insert ERP_DONDATHANG_SANPHAM(dondathang_fk, sanpham_fk, soluong, dongia, dvdl_fk)  " +
						"select IDENT_CURRENT('ERP_DONDATHANG'), sanpham_duyet_fk, duyet, dongia, dvdl_fk   " +
						"from ERP_DENGHIDOIHANG_SANPHAM  " +
						"where denghidoihang_fk = '" + this.id + "' and ISNULL(DUYET, 0) != 0 and SANPHAM_DUYET_FK is not null ";
				System.out.println("1.Insert DDH - SP: " + query);
				if(!db.update(query))
				{
					msg = "Không thể tạo mới ERP_DeNghiDatHang " + query;
					db.getConnection().rollback();
					return false;
				}
			}
			
			//TANG KHO LOI CUA TRUNG TAM
			
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


	
	
}
