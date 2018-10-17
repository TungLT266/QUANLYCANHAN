package geso.traphaco.distributor.beans.nhaphang.imp;

import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.distributor.util.FixData;
import geso.traphaco.center.util.SendMail;
import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.beans.nhaphang.INhaphang;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Nhaphang implements INhaphang
{
	String userId;
	String id;
	
	String nppId;
	
	String ngayyeucau;
	String ngaynhan;
	String sochungtu;
	String ghichu;

	String msg;
	String trangthai;

	String ddhId;
	ResultSet ddhRs;
	

	String khonhanId;
	ResultSet khonhanRs;

	String[] spPK_SEQ;
	String[] spId;
	String[] spMa;
	String[] spTen;
	String[] spDonvi;
	String[] spSolo;
	String[] spTonkho;
	String[] spXuat;
	String[] spSoluong;
	String[] spDongia;
	String[] spLoai;
	String[] spSCheme;
	String[] spvat;
	String[] spchietkhau;
	public String[] getSpchietkhau()
	{
		return spchietkhau;
	}

	public void setSpchietkhau(String[] spchietkhau)
	{
		this.spchietkhau = spchietkhau;
	}

	public String[] getSpvat()
	{
		return spvat;
	}

	public void setSpvat(String[] spvat)
	{
		this.spvat = spvat;
	}
	dbutils db;
	
	public Nhaphang()
	{
		this.id = "";
		this.ngayyeucau = getDateTime();
		this.ghichu = "";
		this.msg = "";
		this.trangthai = "0";
		this.ddhId = "";
		this.ngaynhan = "";
		this.sochungtu = "";
		this.khonhanId = "";
		this.loaiDh="";
		this.db = new dbutils();
	}
	
	public Nhaphang(String id)
	{
		this.id = id;
		this.ngayyeucau = getDateTime();
		this.ghichu = "";
		this.msg = "";
		this.trangthai = "0";
		this.ddhId = "";
		this.ngaynhan = "";
		this.sochungtu = "";
		this.khonhanId = "";
		this.loaiDh="";
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

	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	
	public void createRs() 
	{
		String	query = "select PK_SEQ, CAST(pk_seq as varchar(10)) + ' / ' + NgayDonHang as ten " +
						"from ERP_DONDATHANG where TRANGTHAI in (2, 4) and PK_SEQ in ( select ddh_fk from NHAPHANG_DDH where nhaphang_fk = '" + this.id + "' )  ";
		this.ddhRs = db.get(query);
		Utility util = new Utility();
		if(this.id.trim().length() > 0 )
		{
			query = "declare @tb table  ";
				query+="( ";
				query+="sanpham_fk numeric(18,0), ";
			query += "dongia float,thuevat float,chietkhau float ";
					query+=") ";
			
					query+="declare @chuyenkho_fk int;";
			query += "set @chuyenkho_fk=(select CHUYENKHO_FK from NHAPHANG where PK_SEQ=" + this.id + ") ";
			
					query+="declare @ycxkfk int;";
			query += "set @ycxkfk=(select YCXK_FK  from NHAPHANG where PK_SEQ=" + this.id + ") ";
			
					query+="declare @ycxknppfk int;";
			query += "set @ycxknppfk=(select YCXKNPP_FK  from NHAPHANG where PK_SEQ=" + this.id + ") ";
			
					query+="declare @cknppfk int;";
			query += "set @cknppfk=(select CHUYENKHONPP_FK  from NHAPHANG where PK_SEQ=" + this.id + ") ";
			
			query += "if(@chuyenkho_fk is not null) ";
			query += "begin ";
			query += "insert into @tb(sanpham_fk, dongia,thuevat,chietkhau)";
			query += "select sanpham_fk,dongia,0,0 from ERP_CHUYENKHO_SANPHAM where chuyenkho_fk=@chuyenkho_fk  ";
			query += "end ";
			query += " if(@ycxkfk is not null) ";
			query += "begin ";
			query += "insert into @tb(sanpham_fk, dongia,thuevat,chietkhau) ";
			query += "    select sanpham_fk,dongia,thueVAT,chietkhau from ERP_DONDATHANG_SANPHAM where dondathang_fk in  ( select ddh_fk from ERP_YCXUATKHO_DDH where ycxk_fk=@ycxkfk) ";
			query += "	end ";
			query += "if(@ycxknppfk is not null) ";
			query += "begin ";
			query += "insert into @tb(sanpham_fk, dongia,thuevat,chietkhau) ";
			query += " select sanpham_fk,dongia,thueVAT,chietkhau from ERP_DONDATHANGNPP_SANPHAM where dondathang_fk in  ( select ddh_fk from ERP_YCXUATKHONPP_DDH where ycxk_fk=@ycxknppfk)";
			query += "end ";
			query += "if(@cknppfk is not null) ";
			query += "begin ";
			
			query += "insert into @tb(sanpham_fk, dongia,thuevat,chietkhau) ";
			query += "select sanpham_fk,dongia,0,0 from ERP_CHUYENKHONPP_SANPHAM where chuyenkho_fk =@cknppfk ";
			query += "end ";
			
					query += "select * from @tb ";
			
					query += "inner join ";
			query += "(select b.PK_SEQ, b.MA, b.TEN, c.DONVI, dongia, isnull(a.SOLO, 'NA') as SOLO, a.SOLUONG,";
			query += "ISNULL(soluongNHAN, a.SOLUONG) as soluongNHAN, isnull(a.loai, 0) as loai,";
			query += " isnull(a.SCHEME, '') as SCHEME,isnull(a.NgayHetHan,'') as spNGAYHETHAN, a.ID  ";
			query += "from NHAPHANG_SP a inner join SANPHAM b on a.SANPHAM_FK = b.PK_SEQ 		";
			query += "inner join DONVIDOLUONG c on b.DVDL_FK = c.PK_SEQ  where a.NHAPHANG_FK = '" + this.id + "') b on b.PK_SEQ = sanpham_fk  ";
			
			/*
			 * query =
			 * "  select b.PK_SEQ, b.MA, b.TEN, c.DONVI, isnull(a.DONGIA, 0) as DONGIA, isnull(a.SOLO, 'NA') as SOLO, a.SOLUONG, ISNULL(soluongNHAN, a.SOLUONG) as soluongNHAN, isnull(a.loai, 0) as loai, isnull(a.SCHEME, '') as SCHEME,isnull(a.NgayHetHan,'') as spNGAYHETHAN   "
			 * +
			 * "  from NHAPHANG_SP a inner join SANPHAM b on a.SANPHAM_FK = b.PK_SEQ "
			 * + "		inner join DONVIDOLUONG c on b.DVDL_FK = c.PK_SEQ " +
			 * "  where a.NHAPHANG_FK = '" + this.id + "' ";
			 */
			
			System.out.println("---INIT NK: " + query);
			ResultSet spRs = db.get(query);
			NumberFormat formater = new DecimalFormat("##,###,###");
			
			if(spRs != null)
			{
				try 
				{
					String spPK_SEQ = "";
					String spID = "";
					String spMA = "";
					String spTEN = "";
					String spDONVI = "";
					String spDONGIA = "";
					String spVAT = "";
					String spchietkhau = "";
					String spSOLO = "";
					String spXUAT = "";
					String spSOLUONG = "";
					String spLOAI = "";
					String spSCHEME = "";
					String spNGAYHETHAN = "";
					
					while(spRs.next())
					{
						spPK_SEQ += spRs.getString("ID") + "__";
						spID += spRs.getString("PK_SEQ") + "__";
						spMA += spRs.getString("MA") + "__";
						spTEN += spRs.getString("TEN") + "__";
						spDONVI += spRs.getString("DONVI") + "__";
						spDONGIA += spRs.getDouble("DONGIA") + "__";
						spXUAT += formater.format(spRs.getDouble("SOLUONG")) + "__";
						spSOLUONG += formater.format(spRs.getDouble("soluongNHAN")) + "__";
						spchietkhau += formater.format(spRs.getDouble("chietkhau")) + "__";
						spVAT += formater.format(spRs.getDouble("thuevat")) + "__";
						spLOAI += spRs.getString("LOAI") + "__";
						
						if(spRs.getString("SOLO").trim().length() > 0)
							spSOLO += spRs.getString("SOLO") + "__";
						else 
							spSOLO += " __";
						
						if(spRs.getString("spNGAYHETHAN").trim().length() > 0)
							spNGAYHETHAN += spRs.getString("spNGAYHETHAN") + "__";
						else 
							spNGAYHETHAN += " __";
										
						if(spRs.getString("SCHEME").trim().length() > 0)
							spSCHEME += spRs.getString("SCHEME") + "__";
						else
							spSCHEME += " __";
						
					}
					spRs.close();
					
					if(spMA.trim().length() > 0)
					{
						spPK_SEQ = spPK_SEQ.substring(0, spPK_SEQ.length() - 2);
						this.spPK_SEQ = spPK_SEQ.split("__");
						
						spID = spID.substring(0, spID.length() - 2);
						this.spId = spID.split("__");
						
						spMA = spMA.substring(0, spMA.length() - 2);
						this.spMa = spMA.split("__");
						
						spTEN = spTEN.substring(0, spTEN.length() - 2);
						this.spTen = spTEN.split("__");
						
						spDONVI = spDONVI.substring(0, spDONVI.length() - 2);
						this.spDonvi = spDONVI.split("__");
						
						spDONGIA = spDONGIA.substring(0, spDONGIA.length() - 2);
						this.spDongia = spDONGIA.split("__");
						
						spSOLO = spSOLO.substring(0, spSOLO.length() - 2);
						this.spSolo = spSOLO.split("__");
						
						spXUAT = spXUAT.substring(0, spXUAT.length() - 2);
						this.spXuat = spXUAT.split("__");
						
						spSOLUONG = spSOLUONG.substring(0, spSOLUONG.length() - 2);
						this.spSoluong = spSOLUONG.split("__");

						spLOAI = spLOAI.substring(0, spLOAI.length() - 2);
						this.spLoai = spLOAI.split("__");
						
						spSCHEME = spSCHEME.substring(0, spSCHEME.length() - 2);
						this.spSCheme = spSCHEME.split("__");
						
						spNGAYHETHAN = spNGAYHETHAN.substring(0, spNGAYHETHAN.length() - 2);
						this.spNgayHetHan = spNGAYHETHAN.split("__");
						
						spVAT = spVAT.substring(0, spVAT.length() - 2);
						this.spvat = spVAT.split("__");
						spchietkhau = spchietkhau.substring(0, spchietkhau.length() - 2);
						this.spchietkhau = spchietkhau.split("__");
					}
					
				} 
				catch (Exception e) {e.printStackTrace(); System.out.println("EXCEPTION SP: " + e.getMessage() ); }
				
			}
			
		}
		
		this.khonhanRs = db.get("select pk_seq, ten from KHO where pk_seq in " + util.quyen_kho(this.userId)+" order by pk_seq asc");
		
		
		this.getNppInfo();
	}

	private void getNppInfo()
	{		
		//Phien ban moi
		geso.traphaco.distributor.util.Utility util=new geso.traphaco.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
	}
	
	public void init() 
	{
		String query = 
				"select SoChungTu,ngaychungtu as ngayyeucau, isnull(ghichu,'') as ghichu, npp_fk, isnull(ngaynhan, '') as ngaynhan, trangthai, isnull(ycxk_fk, chuyenkho_fk) as ycxk_fk, isnull(kho_fk, 100000) as kho_fk " +
				"	,	(	select top(1) isnull(a.loaidonhang,b.LoaiDonHang) as loaidh  from ERP_DONDATHANG a inner join ERP_CHUYENKHO b on b.ddh_fk=a.PK_SEQ  "+
				"		where b.PK_SEQ=NHAPHANG.CHUYENKHO_FK and b.trangthai=1 "+
				" )as LoaiDH  "+
			  "from NHAPHANG where pk_seq = '" + this.id + "'";
		System.out.println("____INIT NHAP KHO: " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					this.ngayyeucau = rs.getString("ngayyeucau");
					this.ghichu = rs.getString("ghichu");
					this.ngaynhan = rs.getString("ngaynhan");
					String loaidh=rs.getString("loaidh")==null?"0":rs.getString("loaidh");
					if(loaidh.equals("4"))
						this.khonhanId="100001";
					else
					this.khonhanId = rs.getString("kho_fk");
					System.out.println("da vao day"+this.khonhanId);
					if(rs.getString("trangthai").equals("1"))
						this.sochungtu = rs.getString("SoChungTu");
					
					this.loaiDh=rs.getString("loaiDh")==null?"":rs.getString("loaiDh");
					
				}
				rs.close();
				
				//INIT DDH
				query = "select ddh_fk from NHAPHANG_DDH where nhaphang_fk = '" + this.id + "' ";
				rs = db.get(query);
				System.out.println("quer __"+query);
				String ddhID = "";
				while(rs.next())
				{
					ddhID += rs.getString("ddh_fk") + ",";
				}
				rs.close();
				
				if(ddhID.trim().length() > 0)
				{
					this.ddhId = ddhID.substring(0, ddhID.length() - 1);
				}
				
			} 
			catch (Exception e) 
			{
				System.out.println("---LOI INIT: " + e.getMessage());
			}
		}

		this.createRs();
	
	}

	public void DBclose() {
		
		try{
			
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
	
	public String getDondathangId() {
		
		return this.ddhId;
	}

	
	public void setDondathangId(String kbhId) {
		
		this.ddhId = kbhId;
	}

	
	public ResultSet getDondathangRs() {
		
		return this.ddhRs;
	}

	
	public void setDondathangRs(ResultSet ddhRs) {
		
		this.ddhRs = ddhRs;
	}
	

	public String[] getSpTonKho() {
		
		return this.spTonkho;
	}

	
	public void setSpTonKho(String[] spTonkho) {
		
		this.spTonkho = spTonkho; 
	}

	
	public boolean update() 
	{
		if(this.ngaynhan.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn ngày nhận hàng";
			return false;
		}
		
		if(spId == null)
		{
			this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm nhập kho";
			return false;
		}
		else
		{
			boolean coSP = false;
			for(int i = 0; i < spId.length; i++)
			{
				if( spSoluong[i].trim().length() > 0 )
				{
					if(Double.parseDouble(spSoluong[i].trim().replaceAll(",", "")) > 0)
						coSP = true;
					
					if( Double.parseDouble(spSoluong[i].trim().replaceAll(",", "")) >  Double.parseDouble(spXuat[i].trim().replaceAll(",", "")) )
					{
						this.msg = "Số lương nhận của sản phẩm (" + spTen[i] + ") không được vượt quá số lượng xuất ";
						return false;
					}
				}
			}
			
			if(!coSP)
			{
				this.msg = "Không có sản phẩm nào được nhập số lượng nhận. Vui lòng kiểm tra lại.";
				return false;
			}	
		}
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query = " Update NHAPHANG set ngaynhan = '" + this.ngaynhan + "', ngaysua = '" + this.getDateTime() + "', nguoisua = '" + this.userId + "', kho_fk = '" + this.khonhanId + "' where pk_seq = '" + this.id + "' ";
			
			System.out.println("1.Update NHAPHANG: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật NHAPHANG " + query;
				db.getConnection().rollback();
				return false;
			}
			
			for(int i = 0; i < spId.length; i++)
			{
				String soluongNHAN = "0";
				if(spSoluong[i].trim().replaceAll(",", "").length() > 0)
					soluongNHAN = spSoluong[i].trim().replaceAll(",", "");
				
				query = "UPDATE NHAPHANG_SP set soluongNHAN = '" + soluongNHAN + "' " +
						"where nhaphang_fk = '" + this.id + "' and sanpham_fk = '" + spId[i] + "' and loai = '" + spLoai[i] + "' and solo = '" + spSolo[i] + "'  and NgayHetHan='"+spNgayHetHan[i]+"' ";
				
				System.out.println("3.Update NHAPHANG_SP: " + query);
				if(db.updateReturnInt(query)!=1)
				{
					this.msg = "Khong the cap nhat NHAPHANG_SP: " + query;
					db.getConnection().rollback();
					return false;
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
	
	
	public boolean chot() 
	{
		getNppInfo();
		
		Utility util = new Utility();
		msg= util.Check_Huy_NghiepVu_KhoaSo("NhapHang", this.id, "NgayNhan", db);
		if(msg.length()>0)
			return false;
		
		if(this.ngaynhan.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn ngày nhận hàng";
			return false;
		}
		
		/*if(this.sochungtu.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập số chứng từ";
			return false;
		}*/
		
		if(spId == null)
		{
			this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm nhập kho";
			return false;
		}
		else
		{
			boolean coSP = false;
			for(int i = 0; i < spId.length; i++)
			{
				if( spSoluong[i].trim().length() > 0 )
				{
					if(spSoluong[i].trim().replaceAll(",", "").length() > 0)
						coSP = true;
					
					if( Double.parseDouble(spSoluong[i].trim().replaceAll(",", "")) >  Double.parseDouble(spXuat[i].trim().replaceAll(",", "")) )
					{
						this.msg = "Số lương nhận của sản phẩm (" + spTen[i] + ") không được vượt quá số lượng xuất ";
						return false;
					}
				}
			}
			
			if(!coSP)
			{
				this.msg = "Không có sản phẩm nào được nhập số lượng nhận. Vui lòng kiểm tra lại.";
				return false;
			}	
		}
		
		try
		{
			//CHECK SO CHUNG TU
			
			String ycxk_fk = "";
			String npp_fk = "";
			String spIds = "";
			
			/*String query =
			"		select case   "+ 
			"			when YCXK_FK IS not null then CAST( YCXK_FK   AS VARCHAR(50) )  "+
			"			when CHUYENKHO_FK IS not null then (SELECT SoChungTu from ERP_CHUYENKHO where PK_SEQ=NHAPHANG.CHUYENKHO_FK)  "+
			"			when YCXKNPP_FK IS not null then  "+
			"	 "+
			"		(     "+
			"				SELECT top(1) SOHOADON FROM ERP_HOADONNPP_DDH A    "+
			"					INNER JOIN  ERP_YCXUATKHONPP_DDH B ON A.DDH_FK=B.DDH_FK   "+
			"					INNER JOIN ERP_HOADONNPP C ON C.PK_SEQ=A.HOADONNPP_FK     "+
			"				WHERE C.TRANGTHAI IN (2,4)	AND B.YCXK_FK=NHAPHANG.YCXKNPP_FK  "+   
			"			)  "+  
			"		when CHUYENKHONPP_FK IS NOT NULL THEN (SELECT SoChungTu from ERP_CHUYENKHONPP where pk_seq=NHAPHANG.CHUYENKHONPP_FK) end  as SoChungTu  "+
			"		,YCXK_FK,CHUYENKHO_FK,YCXKNPP_FK,CHUYENKHONPP_FK, NPP_FK  "+
			"	from NHAPHANG  " +
			" where pk_Seq='"+this.id+"'   ";*/
			
			String query = "select npp_fk, sochungtu from NHAPHANG where pk_seq = '" + this.id + "' ";
			ResultSet rs = db.get(query);
		
			if(rs.next())
			{
				ycxk_fk = rs.getString("SoChungTu")==null ? "": rs.getString("SoChungTu");
				npp_fk = rs.getString("NPP_FK");
			}
			
			if(rs != null)
			{				
				rs.close();	
			}
			
			if(ycxk_fk.length()<=0)
			{
				this.msg = "Số chứng từ chưa phát sinh!Vui lòng liên hệ TT!";
				return false;
			}
			
			/*if(!this.sochungtu.trim().equals(ycxk_fk.trim()) )
			{
				this.msg = "Số chứng từ không hợp lệ";
				return false;
			}*/


			db.getConnection().setAutoCommit(false);

			// HÀNG KHUYẾN MẠI THÌ VẪN VÔ KHO LÚC TẠO NHẬN HÀNG
			query = " UPDATE NHAPHANG_SP SET KHONHAN_FK = '" + this.khonhanId + "' "+
		    		" WHERE NHAPHANG_fK = '" + this.id + "' and isnull(SCHEME,'') = ''   " ;
			
			System.out.println(":::> " + query);
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat NHAPHANG_SP: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			// NHập vô kho nhận được chọn
			query = " UPDATE NHAPHANG_SP SET KHONHAN_FK = '" + this.khonhanId + "' "+
		    		" WHERE NHAPHANG_fK = '" + this.id + "' and KHONHAN_FK IS NULL " ;
			
			System.out.println(":::CAP NHAT KHO NHAN: " + query);
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat NHAPHANG_SP: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			//NẾU LÔ NA THÌ PHẢI CHO VỀ NGÀY HẾT HẠN 2030-12-31
			query = "UPDATE NHAPHANG_SP set ngayhethan = '2030-12-31' " +
					"where nhaphang_fk = '" + this.id + "' and solo = 'NA' ";
			
			System.out.println(":::"+query);
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat NHAPHANG_SP: " + query;
				db.getConnection().rollback();
				return false;
			}

			for(int i = 0; i < spId.length; i++)
			{
				String soluongNHAN = "0";
				if(spSoluong[i].trim().replaceAll(",", "").length() > 0)
					soluongNHAN = spSoluong[i].trim().replaceAll(",", "");
				
				if(spSolo[i].trim().equals("NA"))
					spNgayHetHan[i] = "2030-12-31";
				
				/*query = "UPDATE NHAPHANG_SP set soluongNHAN = '" + soluongNHAN + "' " +
						"where nhaphang_fk = '" + this.id + "' and sanpham_fk = '" + spId[i] + "' and isnull(loai,0) = '" + spLoai[i] + "' and solo = '" + spSolo[i] + "' and isnull(scheme,'') = N'" + spSCheme[i] + "' and NgayHetHan='"+spNgayHetHan[i]+"' ";*/
				
				query = "UPDATE NHAPHANG_SP set soluongNHAN = '" + soluongNHAN + "' " +
						"where nhaphang_fk = '" + this.id + "' and ID = '" + spPK_SEQ[i] + "' ";
				
				System.out.println("2.Update NHAPHANG_SP: " + query);
				if(db.updateReturnInt(query) != 1)
				{
					this.msg = "Khong the cap nhat NHAPHANG_SP: " + query;
					db.getConnection().rollback();
					return false;
				}
				
				//TANG KHO
				String kho_fk = "";
				if(spLoai[i].equals("1"))  //SPKM
					kho_fk = " select khonhan_fk from NHAPHANG_SP where nhaphang_fk = '" + this.id + "' and sanpham_fk = '" + spId[i] + "' and solo = '" + spSolo[i] + "' and isnull(scheme,'') = '" + spSCheme[i] + "' and NgayHetHan='"+spNgayHetHan[i]+"' AND ID = '" + spPK_SEQ[i] + "' ";
				else
					kho_fk = " select khonhan_fk from NHAPHANG_SP where nhaphang_fk = '" + this.id + "' and sanpham_fk = '" + spId[i] + "' and solo = '" + spSolo[i] + "' and isnull(loai,0) = '0'  and NgayHetHan='"+spNgayHetHan[i]+"' AND ID = '" + spPK_SEQ[i] + "'  ";
				
				/*System.out.println("-------QUYERY KHO:: " + kho_fk );
				ResultSet rsKHO = db.get(kho_fk);
				if(rsKHO.next())
				{
					System.out.println("-----KHO LAY DUOC::: " + rsKHO.getString("khonhan_fk"));
				}*/
				
				int exits = 0;
				query = "select COUNT(*) as soDONG " +
						"from NHAPP_KHO where KHO_FK in ( " + kho_fk + " ) and NPP_FK = ( select NPP_FK from NHAPHANG where PK_SEQ = '" + this.id + "' )" +
						" and SANPHAM_FK = '" + spId[i] + "' and NHOMKENH_FK = ( select NHOMKENH_FK from NHAPHANG where PK_SEQ = '" + this.id + "' )  ";
				System.out.println("--CHECK EXITS: " + query);
				
				ResultSet rsCHECK = db.get(query);
				if(rsCHECK.next())
				{
					exits = rsCHECK.getInt("soDONG");
				}
				rsCHECK.close();
				
				System.out.println("EXITS::: " + exits);
				if(exits <= 0)
				{
					query = "insert NHAPP_KHO(KHO_FK, NPP_FK, SANPHAM_FK, SOLUONG, BOOKED, AVAILABLE, NHOMKENH_FK) " +
							"select '"+this.khonhanId+"','"+this.nppId+"','"+spId[i]+"',0,0,0, ( select NHOMKENH_FK from NHAPHANG where PK_SEQ = '" + this.id + "' )  ";
					System.out.println("1.insert NHAPP_KHO: " + query);
					if(db.updateReturnInt(query)!=1)
					{
						this.msg = "1.Khong the tao moi NHAPP_KHO: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
				else
				{
					query = "update NHAPP_KHO set SOLUONG = SOLUONG + '" + soluongNHAN + "', AVAILABLE = AVAILABLE + '" + soluongNHAN + "', soluongNHAP_SAUNXT = isnull(soluongNHAP_SAUNXT, 0) + '" + soluongNHAN + "' " +
							"where KHO_FK in ( " + kho_fk + " ) and NPP_FK = ( select NPP_FK from NHAPHANG where PK_SEQ = '" + this.id + "' ) and SANPHAM_FK = '" + spId[i] + "' " +
									" and NHOMKENH_FK = ( select NHOMKENH_FK from NHAPHANG where PK_SEQ = '" + this.id + "' ) ";
					
					System.out.println("1.cap nhat NHAPP_KHO: " + query);
					if(db.updateReturnInt(query)!=1)
					{
						this.msg = "Khong the cap nhat NHAPP_KHO: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
				
				//CHỈ TĂNG KHO KHI NHỮNG NHÀ ÂM TỒN KHO CÓ TỔNG SỐ NHẬP >= SỐ BỊ ÂM
				/*query = "select soluongNXT, soluongNHAP_SAUNXT, daxulyAMKHO, a.npp_fk, " +
						" 	isnull( ( select count(*) from NPP_DUNGLO where pk_seq = a.npp_fk ) , 0 ) as codungLO  " +
						"from NHAPP_KHO a " +
						"where soluongNXT < 0 and daxulyAMKHO = 0 and npp_fk = ( select NPP_FK from NHAPHANG where PK_SEQ = '" + this.id + "' )  " +
								" and sanpham_fk = '" + spId[i] + "' and kho_fk in ( " + kho_fk + " ) and kbh_fk = ( select KBH_FK from NHAPHANG where PK_SEQ = '" + this.id + "' ) ";*/
				query = "select a.sanpham_fk  " +
						"from NHAPP_KHO a " +
						"where soluongNXT < 0 and npp_fk = ( select NPP_FK from NHAPHANG where PK_SEQ = '" + this.id + "' )  " +
								" and sanpham_fk = '" + spId[i] + "' and kho_fk in ( " + kho_fk + " ) and kbh_fk = ( select KBH_FK from NHAPHANG where PK_SEQ = '" + this.id + "' ) ";
				
				System.out.println("---CHECK NHA AM TON KHO: " + query);
				boolean daxulyAMKHO = true;

				ResultSet rsCHECK_NXT = db.get(query);
				if(rsCHECK_NXT != null)
				{
					if(rsCHECK_NXT.next())
					{
						spIds += rsCHECK_NXT.getString("sanpham_fk") + ",";
					}
					rsCHECK_NXT.close();
				}
				
				System.out.println("+++++++++++ SP: " + spId[i] + "  -- DA XU LY TON KHO: " + daxulyAMKHO );
				
				
				//CHECK XEM KHO CHI TIET DA CO CHUA, NEU CHUA CO THI INSERT
				exits = 0;
				if( !spSolo[i].trim().equals("NA") )
				{
					query = "select COUNT(*) as soDONG " +
							"from NHAPP_KHO_CHITIET where KHO_FK in ( " + kho_fk + " ) and NPP_FK = ( select NPP_FK from NHAPHANG where PK_SEQ = '" + this.id + "' )" +
							" and SANPHAM_FK = '" + spId[i] + "' and NHOMKENH_FK = ( select NHOMKENH_FK from NHAPHANG where PK_SEQ = '" + this.id + "' ) and SOLO = '" + spSolo[i] + "' and NgayHetHan='"+spNgayHetHan[i]+"'  ";
				}
				else //Lô NA không quan tâm ngày hết hạn
				{
					query = "select COUNT(*) as soDONG " +
							"from NHAPP_KHO_CHITIET where KHO_FK in ( " + kho_fk + " ) and NPP_FK = ( select NPP_FK from NHAPHANG where PK_SEQ = '" + this.id + "' )" +
							" and SANPHAM_FK = '" + spId[i] + "' and NHOMKENH_FK = ( select NHOMKENH_FK from NHAPHANG where PK_SEQ = '" + this.id + "' ) and SOLO = '" + spSolo[i] + "'   ";
				}
				
				System.out.println("CHECK EXITS: " + query);
				
				rsCHECK = db.get(query);
				{
					if(rsCHECK.next())
					{
						exits = rsCHECK.getInt("soDONG");
					}
					rsCHECK.close();
				}
					
				if(exits <= 0)
				{
					query = "insert NHAPP_KHO_CHITIET(KHO_FK, NPP_FK, SANPHAM_FK, SOLUONG, BOOKED, AVAILABLE, KBH_FK, NHOMKENH_FK, SOLO, NGAYHETHAN) " +
							"select ( " + kho_fk + " ), b.NPP_FK, a.SANPHAM_FK, a.soluongNHAN, 0, a.soluongNHAN, '100025', b.NHOMKENH_FK, a.SOLO, '" + spNgayHetHan[i] + "'  " +
							"from NHAPHANG_SP a inner join NHAPHANG b on a.NHAPHANG_FK = b.PK_SEQ  " +
							"where b.PK_SEQ = '" + this.id + "' and a.ID = '" + spPK_SEQ[i] + "' and SANPHAM_FK = '" + spId[i] + "' and SOLO = '" + spSolo[i] + "' and isnull(LOAI,0) = '" + spLoai[i] + "' and isnull(SCHEME,'') = '" + spSCheme[i] + "' and NgayHetHan='"+spNgayHetHan[i]+"' ";
				}
				else
				{
					query = " update CT  " +
							" set CT.SOLUONG = CT.SOLUONG + NH.soluongNHAN, " +
							" 			  CT.AVAILABLE = CT.AVAILABLE + NH.soluongNHAN, " +
							" 			  CT.NGAYHETHAN = NH.NGAYHETHAN " +
							" from " +
							" ( " +
								" select ( " + kho_fk + " ) as kho_fk, b.NPP_FK, a.SANPHAM_FK, a.soluongNHAN, b.NHOMKENH_FK, a.SOLO, NgayHetHan " +
								" from NHAPHANG_SP a inner join NHAPHANG b on a.NHAPHANG_FK = b.PK_SEQ " +
								" where a.ID = '" + spPK_SEQ[i] + "' and b.PK_SEQ = '" + this.id + "' and SANPHAM_FK = '" + spId[i] + "' and SOLO = '" + spSolo[i] + "' and isnull(LOAI,0) = '" + spLoai[i] + "'"
										+ " and isnull(SCHEME,'') = '" + spSCheme[i] + "' and NgayHetHan = '" + spNgayHetHan[i] + "'  " +
							" ) " +
							" NH inner join NHAPP_KHO_CHITIET CT on NH.kho_fk = CT.KHO_FK and NH.NPP_FK = CT.NPP_FK and NH.NHOMKENH_FK = CT.NHOMKENH_FK and NH.SOLO = CT.SOLO and NH.SANPHAM_FK = CT.sanpham_fk  ";
					
					if(!spSolo[i].equals("NA"))
						query += " and  nh.NgayHetHan = ct.NgayHetHan " ;
				}
				
				System.out.println("4.cap nhat NHAPP_KHO_CHITIET: " + query);
				if(db.updateReturnInt(query) != 1)
				{
					this.msg = "Khong the cap nhat NHAPP_KHO_CHITIET: " + query;
					db.getConnection().rollback();
					return false;
				}	
			}

			query = " Update NHAPHANG set SoChungTu='" + this.sochungtu + "', ngaynhan = '" + this.ngaynhan + "', trangthai = '1', kho_fk = '" + this.khonhanId + "', ngaysua = '" + this.getDateTime() + "', nguoisua = '" + this.userId + "' " + 
					" where pk_seq = '" + this.id+ "' and trangthai=0 ";

			System.out.println("1.Update NHAPHANG: " + query);
			if (db.updateReturnInt(query) != 1)
			{
				this.msg = "Không thể cập nhật NHAPHANG " + query;
				db.getConnection().rollback();
				return false;
			}
			
			msg = util.Check_Kho_Tong_VS_KhoCT(npp_fk, db);
			if(msg.length()>0)
			{
				db.getConnection().rollback();
				return false;
			}
			
			//KHI CÓ NHẬP HÀNG SẼ FIX TỰ ĐỘNG NHỮNG NHÀ ÂM TỒN KHO
			
			/*db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
			//Xứ lý những sản phẩm âm kho khi có nhập hàng --> bat buoc phai commit TRAN mới thực hiện xử lý tiếp
			//////chỗ này chỉ nên xử lý những SP có trong nhập hàng, nếu không những SP khác cũng sẽ sinh ra các đổi số lô nếu có không cần thiết
			if(spIds.trim().length() > 0)
			{
				spIds = spIds.substring(0, spIds.length() - 1);
				
				FixData fixed = new FixData();
				String msgERROR = fixed.ProcessDATA(npp_fk, spIds, db, "0");
				
				if(msgERROR.trim().length() > 0)
				{
					System.out.println("---SENDDDDDDDDDD MAILLLLLLLL");
					SendMail mail = new SendMail();
					
					String msg = "Hệ thống đã chạy chế độ tự sửa lỗi tồn kho. Nhưng gặp lỗi khi chạy của NPP ( " + npp_fk + " ). Vui lòng kiểm tra và xử lý gấp.";
					mail.postMailHTML("vudq@geso.us,xuantvt@geso.us,taiba@geso.us,hienttd@geso.us", "haind@geso.us,luonghv@geso.us", "Traphaco chạy tồn kho tự động ", msg);
				}
			}*/
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			db.update("rollback");
			this.msg = "Exception: " + e.getMessage();
			e.printStackTrace();
			return false;
		}
		return true;
	}


	public boolean MTVChot( dbutils db, String id ) 
	{
		this.id = id;
		
		//DL PP KHONG PHAN BIET KHO HANG BAN VA NHOM KENH
		this.khonhanId = "100000";
		this.msg = "";
		
		String query = "";
		Utility util = new Utility();
		
		try
		{
			// HÀNG KHUYẾN MẠI THÌ VẪN VÔ KHO LÚC TẠO NHẬN HÀNG
			query = " UPDATE NHAPHANG_SP SET KHONHAN_FK = '" + this.khonhanId + "', soluongNHAN = soluong "+
		    		" WHERE NHAPHANG_fK = '" + this.id + "' and ( isnull(SCHEME,'') = '' or KHONHAN_FK IS NULL  )  " ;
			
			System.out.println("::: " + query);
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat NHAPHANG_SP: " + query;
				return false;
			}

			/*query = "select a.nhomkenh_fk, a.npp_fk, b.ID, b.scheme, b.SANPHAM_FK, b.soluongNHAN, b.SOLO, b.NGAYHETHAN, b.loai " +
					"	from NHAPHANG a inner join NHAPHANG_SP b on a.PK_SEQ = b.NHAPHANG_FK " +
					"	where a.PK_SEQ = '" + this.id + "' ";*/
			query = "select 100000 as nhomkenh_fk, a.npp_fk, b.ID, b.scheme, b.SANPHAM_FK, b.soluongNHAN, b.SOLO, b.NGAYHETHAN, b.loai " +
					"	from NHAPHANG a inner join NHAPHANG_SP b on a.PK_SEQ = b.NHAPHANG_FK " +
					"	where a.PK_SEQ = '" + this.id + "' ";
			ResultSet rs = db.get(query);
			while( rs.next() )
			{
				String sanphamId = rs.getString("SANPHAM_FK");
				String soluongNHAN = rs.getString("soluongNHAN");
				String solo = rs.getString("SOLO");
				String ngayhethan = rs.getString("NGAYHETHAN");
				String loai = rs.getString("loai");
				String pk_seqId = rs.getString("ID");
				String scheme = rs.getString("scheme");
				this.nppId = rs.getString("npp_fk");
				String nhomkenh_fk = rs.getString("nhomkenh_fk");
				
				//TANG KHO
				String kho_fk = "";
				/*if(loai.equals("1"))  //SPKM
					kho_fk = " select khonhan_fk from NHAPHANG_SP where nhaphang_fk = '" + this.id + "' and sanpham_fk = '" + sanphamId + "' and solo = '" + solo + "' and isnull(scheme,'') = '" + scheme + "' and NgayHetHan='" + ngayhethan + "' AND ID = '" + pk_seqId + "' ";
				else
					kho_fk = " select khonhan_fk from NHAPHANG_SP where nhaphang_fk = '" + this.id + "' and sanpham_fk = '" + sanphamId + "' and solo = '" + solo + "' and isnull(loai,0) = '0'  and NgayHetHan='" + ngayhethan + "' AND ID = '" + pk_seqId + "'  ";*/

				kho_fk = this.khonhanId;
				nhomkenh_fk = "100000";
				
				int exits = 0;
				query = "select COUNT(*) as soDONG " +
						"from NHAPP_KHO where KHO_FK in ( " + kho_fk + " ) and NPP_FK = ( select NPP_FK from NHAPHANG where PK_SEQ = '" + this.id + "' )" +
						//" and SANPHAM_FK = '" + sanphamId + "' and NHOMKENH_FK = ( select NHOMKENH_FK from NHAPHANG where PK_SEQ = '" + this.id + "' )  ";
						" and SANPHAM_FK = '" + sanphamId + "' and NHOMKENH_FK = ( " + nhomkenh_fk + " )  ";
				System.out.println("--CHECK EXITS: " + query);
				
				ResultSet rsCHECK = db.get(query);
				if(rsCHECK.next())
				{
					exits = rsCHECK.getInt("soDONG");
				}
				rsCHECK.close();
				
				System.out.println("EXITS::: " + exits);
				if(exits <= 0)
				{
					query = "insert NHAPP_KHO(KHO_FK, NPP_FK, SANPHAM_FK, SOLUONG, BOOKED, AVAILABLE, NHOMKENH_FK) " +
							"select '"+this.khonhanId+"','"+this.nppId+"','" + sanphamId + "', 0, 0, 0, '" + nhomkenh_fk + "'  ";
					System.out.println("1.insert NHAPP_KHO: " + query);
					if(db.updateReturnInt(query)!=1)
					{
						this.msg = "1.Khong the tao moi NHAPP_KHO: " + query;
						return false;
					}
				}
				else
				{
					query = "update NHAPP_KHO set SOLUONG = SOLUONG + '" + soluongNHAN + "', AVAILABLE = AVAILABLE + '" + soluongNHAN + "', soluongNHAP_SAUNXT = isnull(soluongNHAP_SAUNXT, 0) + '" + soluongNHAN + "' " +
							"where KHO_FK in ( " + kho_fk + " ) and NPP_FK = ( select NPP_FK from NHAPHANG where PK_SEQ = '" + this.id + "' ) and SANPHAM_FK = '" + sanphamId + "' " +
									" and NHOMKENH_FK = ( " + nhomkenh_fk + " ) ";
					
					System.out.println("1.cap nhat NHAPP_KHO: " + query);
					if(db.updateReturnInt(query)!=1)
					{
						this.msg = "Khong the cap nhat NHAPP_KHO: " + query;
						return false;
					}
				}
				
				//CHECK XEM KHO CHI TIET DA CO CHUA, NEU CHUA CO THI INSERT
				exits = 0;
				if( !solo.trim().equals("NA") )
				{
					query = "select COUNT(*) as soDONG " +
							"from NHAPP_KHO_CHITIET where KHO_FK in ( " + kho_fk + " ) and NPP_FK = ( select NPP_FK from NHAPHANG where PK_SEQ = '" + this.id + "' )" +
							" and SANPHAM_FK = '" + sanphamId + "' and NHOMKENH_FK = ( " + nhomkenh_fk + " ) and SOLO = '" + solo + "' and NgayHetHan='" + ngayhethan + "'  ";
				}
				else //Lô NA không quan tâm ngày hết hạn
				{
					query = "select COUNT(*) as soDONG " +
							"from NHAPP_KHO_CHITIET where KHO_FK in ( " + kho_fk + " ) and NPP_FK = ( select NPP_FK from NHAPHANG where PK_SEQ = '" + this.id + "' )" +
							" and SANPHAM_FK = '" + sanphamId + "' and NHOMKENH_FK = ( " + nhomkenh_fk + " ) and SOLO = '" + solo + "'   ";
				}
				
				System.out.println("CHECK EXITS: " + query);
				
				rsCHECK = db.get(query);
				{
					if(rsCHECK.next())
					{
						exits = rsCHECK.getInt("soDONG");
					}
					rsCHECK.close();
				}
					
				if(exits <= 0)
				{
					query = "insert NHAPP_KHO_CHITIET(KHO_FK, NPP_FK, SANPHAM_FK, SOLUONG, BOOKED, AVAILABLE, KBH_FK, NHOMKENH_FK, SOLO, NGAYHETHAN) " +
							"select ( " + kho_fk + " ), b.NPP_FK, a.SANPHAM_FK, a.soluongNHAN, 0, a.soluongNHAN, '100025', '" + nhomkenh_fk + "' NHOMKENH_FK, a.SOLO, '" + ngayhethan + "'  " +
							"from NHAPHANG_SP a inner join NHAPHANG b on a.NHAPHANG_FK = b.PK_SEQ  " +
							"where b.PK_SEQ = '" + this.id + "' and a.ID = '" + pk_seqId + "' and SANPHAM_FK = '" + sanphamId + "' and SOLO = '" + solo + "' and isnull(LOAI,0) = '" + loai + "' and isnull(SCHEME,'') = '" + scheme + "' and NgayHetHan='" + ngayhethan + "' ";
				}
				else
				{
					query = " update CT  " +
							" set CT.SOLUONG = CT.SOLUONG + NH.soluongNHAN, " +
							" 			  CT.AVAILABLE = CT.AVAILABLE + NH.soluongNHAN, " +
							" 			  CT.NGAYHETHAN = NH.NGAYHETHAN " +
							" from " +
							" ( " +
								" select ( " + kho_fk + " ) as kho_fk, b.NPP_FK, a.SANPHAM_FK, a.soluongNHAN, '" + nhomkenh_fk + "' NHOMKENH_FK, a.SOLO, NgayHetHan " +
								" from NHAPHANG_SP a inner join NHAPHANG b on a.NHAPHANG_FK = b.PK_SEQ " +
								" where a.ID = '" + pk_seqId + "' and b.PK_SEQ = '" + this.id + "' and SANPHAM_FK = '" + sanphamId + "' and SOLO = '" + solo + "' and isnull(LOAI,0) = '" + loai + "'"
										+ " and isnull(SCHEME,'') = '" + scheme + "' and NgayHetHan = '" + ngayhethan + "'  " +
							" ) " +
							" NH inner join NHAPP_KHO_CHITIET CT on NH.kho_fk = CT.KHO_FK and NH.NPP_FK = CT.NPP_FK and NH.NHOMKENH_FK = CT.NHOMKENH_FK and NH.SOLO = CT.SOLO and NH.SANPHAM_FK = CT.sanpham_fk  ";
					
					if(!solo.equals("NA"))
						query += " and  nh.NgayHetHan = ct.NgayHetHan " ;
				}
				
				System.out.println("4.cap nhat NHAPP_KHO_CHITIET: " + query);
				if(db.updateReturnInt(query) != 1)
				{
					this.msg = "Khong the cap nhat NHAPP_KHO_CHITIET: " + query;
					return false;
				}	
			}

			msg = util.Check_Kho_Tong_VS_KhoCT(this.nppId, db);
			if(msg.length()>0)
			{
				return false;
			}
			
		} 
		catch (Exception e) 
		{
			this.msg = "Exception: " + e.getMessage();
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public boolean MTVMoChot( dbutils db, String id, String hdId ) 
	{
		this.id = id;
		
		//DL PP KHONG PHAN BIET KHO HANG BAN VA NHOM KENH
		this.khonhanId = "100000";
		this.msg = "";
		
		String query = "";
		Utility util = new Utility();
		
		try
		{
			query = "select 100000 as nhomkenh_fk, a.npp_fk, b.ID, b.scheme, b.SANPHAM_FK, b.soluongNHAN, b.SOLO, b.NGAYHETHAN, b.loai, " +
					"( select ten from SANPHAM where pk_seq = b.sanpham_fk ) as spTEN	" +
					"from NHAPHANG a inner join NHAPHANG_SP b on a.PK_SEQ = b.NHAPHANG_FK " +
					"where a.PK_SEQ = '" + this.id + "' ";
			ResultSet rs = db.get(query);
			while( rs.next() )
			{
				String sanphamId = rs.getString("SANPHAM_FK");
				String soluongNHAN = rs.getString("soluongNHAN");
				String solo = rs.getString("SOLO");
				String ngayhethan = rs.getString("NGAYHETHAN");
				String loai = rs.getString("loai");
				String pk_seqId = rs.getString("ID");
				String scheme = rs.getString("scheme");
				this.nppId = rs.getString("npp_fk");
				String nhomkenh_fk = rs.getString("nhomkenh_fk");
				
				//TANG KHO
				String kho_fk = "";
				kho_fk = this.khonhanId;
				nhomkenh_fk = "100000";
				
				double avai = 0;
				query = "select AVAILABLE " +
						"from NHAPP_KHO where KHO_FK in ( " + kho_fk + " ) and NPP_FK = '" + this.nppId + "' " +
						" and SANPHAM_FK = '" + sanphamId + "' and NHOMKENH_FK = ( " + nhomkenh_fk + " )  ";
				System.out.println("--CHECK EXITS: " + query);
				
				ResultSet rsCHECK = db.get(query);
				if(rsCHECK.next())
				{
					avai = rsCHECK.getDouble("AVAILABLE");
				}
				rsCHECK.close();
				
				if( avai < Double.parseDouble(soluongNHAN) )
				{
					this.msg = "Sản phẩm ( " + rs.getString("spTEN") + " ) của hóa đơn ( " + hdId + " ) trong kho chỉ còn tối đa " + avai + " , không đủ để hủy nhập hàng với số lượng ( " + soluongNHAN + " ) ";
					return false;
				}
				
				query = "update NHAPP_KHO set SOLUONG = SOLUONG - '" + soluongNHAN + "', AVAILABLE = AVAILABLE - '" + soluongNHAN + "' " +
						"where KHO_FK in ( " + kho_fk + " ) and NPP_FK = ( select NPP_FK from NHAPHANG where PK_SEQ = '" + this.id + "' ) and SANPHAM_FK = '" + sanphamId + "' " +
								" and NHOMKENH_FK = ( " + nhomkenh_fk + " ) ";
				
				System.out.println("1.cap nhat NHAPP_KHO: " + query);
				if(db.updateReturnInt(query)!=1)
				{
					this.msg = "Khong the cap nhat NHAPP_KHO: " + query;
					return false;
				}
				
				query = " update CT  " +
						" set CT.SOLUONG = CT.SOLUONG - NH.soluongNHAN, " +
						" 			  CT.AVAILABLE = CT.AVAILABLE - NH.soluongNHAN, " +
						" 			  CT.NGAYHETHAN = NH.NGAYHETHAN " +
						" from " +
						" ( " +
							" select ( " + kho_fk + " ) as kho_fk, b.NPP_FK, a.SANPHAM_FK, a.soluongNHAN, '" + nhomkenh_fk + "' NHOMKENH_FK, a.SOLO, NgayHetHan " +
							" from NHAPHANG_SP a inner join NHAPHANG b on a.NHAPHANG_FK = b.PK_SEQ " +
							" where a.ID = '" + pk_seqId + "' and b.PK_SEQ = '" + this.id + "' and SANPHAM_FK = '" + sanphamId + "' and SOLO = '" + solo + "' and isnull(LOAI,0) = '" + loai + "'"
									+ " and isnull(SCHEME,'') = '" + scheme + "' and NgayHetHan = '" + ngayhethan + "'  " +
						" ) " +
						" NH inner join NHAPP_KHO_CHITIET CT on NH.kho_fk = CT.KHO_FK and NH.NPP_FK = CT.NPP_FK and NH.NHOMKENH_FK = CT.NHOMKENH_FK and NH.SOLO = CT.SOLO and NH.SANPHAM_FK = CT.sanpham_fk  ";
				
				if(!solo.equals("NA"))
					query += " and  nh.NgayHetHan = ct.NgayHetHan " ;
				
				System.out.println("2.cap nhat NHAPP_KHO_CHITIET: " + query);
				if(db.updateReturnInt(query)!=1)
				{
					this.msg = "Khong the cap nhat NHAPP_KHO_CHITIET: " + query;
					return false;
				}
			}

			msg = util.Check_Kho_Tong_VS_KhoCT(this.nppId, db);
			if(msg.length()>0)
			{
				return false;
			}
			
		} 
		catch (Exception e) 
		{
			this.msg = "Exception: " + e.getMessage();
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	
	public String[] getSpLoai() {

		return this.spLoai;
	}


	public void setSpLoai(String[] spLoai) {

		this.spLoai = spLoai;
	}

	
	public String[] getSpScheme() {
		
		return this.spSCheme;
	}

	
	public void setSpScheme(String[] spScheme) {
		
		this.spSCheme = spScheme;
	}


	public String[] getSpSolo() {
		
		return this.spSolo;
	}

	
	public void setSpSolo(String[] spSolo) {
		
		this.spSolo = spSolo;
	}

	
	public String[] getSpXuat() {
		
		return this.spXuat;
	}

	
	public void setSpXuat(String[] spXuat) {
		
		this.spXuat = spXuat;
	}

	
	public String[] getSpDongia() {
		
		return this.spDongia;
	}

	
	public void setSpDongia(String[] spDongia) {
		
		this.spDongia = spDongia;
	}

	
	public boolean create() {
		
		return false;
	}

	
	public String getNgaynhap() {
		
		return this.ngaynhan;
	}

	
	public void setNgaynhap(String ngaynhap) {
		
		this.ngaynhan = ngaynhap;
	}

	
	public String getSochungtu() {
		
		return this.sochungtu;
	}

	
	public void setSOchungtu(String sochungtu) {
		
		this.sochungtu = sochungtu;
	}

	
	public String getKhonhanId() {
		
		return this.khonhanId;
	}

	
	public void setKhonhanId(String khonhanId) {
		
		this.khonhanId = khonhanId;
	}

	
	public ResultSet getKhonhanRs() {
		
		return this.khonhanRs;
	}

	
	public void setKhonhanRs(ResultSet khonhanRs) {
		
		this.khonhanRs = khonhanRs;
	}
	String[] spNgayHetHan;

	public String[] getSpNgayHetHan()
  {
  	return spNgayHetHan;
  }

	public void setSpNgayHetHan(String[] ngayHetHan)
  {
  	this.spNgayHetHan = ngayHetHan;
  }
		
	public String getNppId() 
	{
		return this.nppId;
	}

	public void setNppId(String nppId) 
	{
		this.nppId = nppId;
	}
	String loaiDh;
	public String getLoaiDh()
  {
  	return loaiDh;
  }

	public void setLoaiDh(String loaiDh)
  {
  	this.loaiDh = loaiDh;
  }

	@Override
	public String[] getPK_SEQ() {
		// TODO Auto-generated method stub
		return this.spPK_SEQ;
	}

	@Override
	public void setPK_SEQ(String[] pk_seq) {
		this.spPK_SEQ = pk_seq;
	}
	
}
