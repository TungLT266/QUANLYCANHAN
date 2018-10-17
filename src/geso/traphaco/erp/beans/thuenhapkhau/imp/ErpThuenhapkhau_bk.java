package geso.traphaco.erp.beans.thuenhapkhau.imp;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.beans.thuenhapkhau.IErpThuenhapkhau_bk;

public class ErpThuenhapkhau_bk implements IErpThuenhapkhau_bk
{
	String userId;
	String congtyId;
	String id;
	
	String cqt;
	String cqtId;
	
	String ptVAT;
	String VAT;
	
	String ngaynhap;
	String ngaychungtu;
	String sochungtu;

	String diengiai;
	
	String pnkId;
	ResultSet pnkRs;
	
	String nccId;
	String ncc;

	ResultSet tnkRs;

	String msg;
	String[] spId;
	String[] soluong;
	String[] dongia;
	String[] thuesuat;
	String[] thue;
	
	Hashtable<String, String> tsht;
	Hashtable<String, String> tht;
	
	dbutils db;
	
	public ErpThuenhapkhau()
	{
		this.userId = "";
		this.id = "";

		this.cqt = "";
		this.cqtId = "";		
		this.ptVAT = "0";
		this.VAT = "0";
		
		this.diengiai = "";
		this.ngaynhap = "";
		this.ngaychungtu = "";
		this.sochungtu = "";

		this.pnkId = "";
		this.nccId = "";
		this.ncc = "";
		this.tsht = new Hashtable();
		this.tht = new Hashtable();
		this.msg = "";
		this.db = new dbutils();
	}
	
	public ErpThuenhapkhau(String id)
	{
		this.userId = "";
		this.id = id;

		this.cqt = "";
		this.cqtId = "";		
		this.ptVAT = "0";
		this.VAT = "0";

		this.diengiai = "";
		this.ngaynhap = "";
		this.ngaychungtu = "";
		this.sochungtu = "";

		this.pnkId = "";
		this.nccId = "";
		this.ncc = "";
		this.tsht = new Hashtable();
		this.tht = new Hashtable();

		this.msg = "";
		this.db = new dbutils();
	}
	
	public String getId() 
	{
		return this.id;
	}

	public void setId(String Id) 
	{
		this.id = Id;
	}
	
	public String getCqt() 
	{
		return this.cqt;
	}

	public void setCqt(String cqt) 
	{
		this.cqt = cqt;
	}

	public String getCqtId() 
	{
		return this.cqtId;
	}

	public void setCqtId(String cqtId) 
	{
		this.cqtId = cqtId;
	}

	public String getPtVAT() 
	{
		return this.ptVAT;
	}

	public void setPtVAT(String ptVAT) 
	{
		this.ptVAT = ptVAT;
	}

	public String getVAT() 
	{
		return this.VAT;
	}

	public void setVAT(String VAT) 
	{
		this.VAT = VAT;
	}

	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;	
	}

	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	public String getNcc() 
	{
		return this.ncc;
	}

	public void setNcc(String ncc) 
	{
		this.ncc = ncc;
	}

	public String getNccId() 
	{
		return this.nccId;
	}

	public void setNccId(String nccId) 
	{
		this.nccId = nccId;
	}

	public Hashtable getThuesuatHashtable() 
	{
		return this.tsht;
	}

	public void setThuesuatHashtable(Hashtable tsht) 
	{
		this.tsht = tsht;
	}

	public Hashtable getThueHashtable() 
	{
		return this.tht;
	}

	public void setThueHashtable(Hashtable tht) 
	{
		this.tht = tht;
	}

	public String[] getSpId() 
	{
		return this.spId;
	}

	public void setSpId(String[] spId) 
	{
		this.spId = spId;
	}

	public String[] getSoluong() 
	{
		return this.soluong;
	}

	public void setSoluong(String[] soluong) 
	{
		this.soluong = soluong;
	}

	public String[] getDongia() 
	{
		return this.dongia;
	}

	public void setDongia(String[] dongia) 
	{
		this.dongia = dongia;
	}

	public String[] getThuesuat() 
	{
		return this.thuesuat;
	}

	public void setThuesuat(String[] thuesuat) 
	{
		this.thuesuat = thuesuat;
	}

	public String[] getThue() 
	{
		return this.thue;
	}

	public void setThue(String[] thue) 
	{
		this.thue = thue;
	}

	public void init() 
	{
		String query = 	"SELECT TNK.PK_SEQ AS TNKID, TNK.DIENGIAI, TNK.COQUANTHUE_FK AS CQTID, " +
						"CONVERT(VARCHAR, CQT.PK_SEQ) + ' - ' + CQT.MA + ', ' + CQT.TEN AS CQTTEN, " +
						"TNK.PTVAT, TNK.VAT, TNK.NGAY, NH.PK_SEQ AS PHIEUNHAP, TNK.TRANGTHAI, " +
						"NV1.TEN AS NGUOITAO, TNK.NGAYTAO, NV2.TEN AS NGUOISUA, TNK.NGAYSUA, " +
						"NCC.PK_SEQ AS NCCID, CONVERT(VARCHAR, NCC.PK_SEQ) + ' - ' + NCC.MA + ', ' + NCC.TEN AS NCCTEN, " +
						"ISNULL(TNK.NGAYCHUNGTU, '') AS NGAYCHUNGTU, TNK.SOCHUNGTU " +
						"FROM ERP_THUENHAPKHAU TNK	" +				
						"INNER JOIN ERP_NHACUNGCAP NCC ON NCC.PK_SEQ = TNK.NCC_FK " +
						"INNER JOIN ERP_NHACUNGCAP CQT ON CQT.PK_SEQ = TNK.COQUANTHUE_FK " +
						"INNER JOIN ERP_NHANHANG NH ON NH.PK_SEQ = TNK.PHIEUNHAPKHO_FK " +
						"INNER JOIN NHANVIEN NV1 ON NV1.PK_SEQ = TNK.NGUOITAO " +
						"INNER JOIN NHANVIEN NV2 ON NV2.PK_SEQ = TNK.NGUOISUA WHERE TNK.PK_SEQ = '" + this.id + "'";
		System.out.println("Init: " + query);
		
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					this.ngaynhap = rs.getString("NGAY");
					this.cqtId = rs.getString("CQTID");
					this.cqt = rs.getString("CQTTEN");
					this.nccId =  rs.getString("NCCID");
					this.ncc = rs.getString("NCCTEN");
					this.VAT = rs.getString("VAT");
					this.ptVAT = rs.getString("PTVAT");
					this.pnkId = rs.getString("PHIEUNHAP");
					this.diengiai = rs.getString("DIENGIAI");
					this.ngaychungtu = rs.getString("NGAYCHUNGTU");
					this.sochungtu = rs.getString("SOCHUNGTU") == null ? "" : rs.getString("SOCHUNGTU");

				}
				rs.close();
			} 
			catch (Exception e) 
			{
				System.out.println("__Exception: " + e.getMessage());
			}
		}
		
		this.createRs();
		
	}
	
	public void createRs() 
	{
		String query = "";
		if(this.id.length() > 0 ){
			query = "SELECT SANPHAM_FK, THUESUAT, THUENHAPKHAU FROM ERP_THUENHAPKHAU_CHITIET WHERE THUENHAPKHAU_FK = " + this.id + "";
			System.out.println(query);
			
			ResultSet rs = this.db.get(query);
			try{
				while(rs.next()){
					tsht.put(rs.getString("SANPHAM_FK"), rs.getString("THUESUAT"));
					tht.put(rs.getString("SANPHAM_FK"), rs.getString("THUENHAPKHAU"));
				}
				rs.close();
			}catch(java.sql.SQLException e){
				
			}
		}else{
			if(this.pnkId.length() > 0){
				query = "SELECT SANPHAM_FK FROM ERP_NHANHANG_SANPHAM WHERE NHANHANG_FK = " + this.pnkId + "";
				System.out.println(query);
				
				ResultSet rs = this.db.get(query);
				try{
					while(rs.next()){
						tsht.put(rs.getString("SANPHAM_FK"), "0");
						tht.put(rs.getString("SANPHAM_FK"), "0");
					}
					rs.close();
				}catch(java.sql.SQLException e){}			
			}
		}

		if(this.nccId.length() > 0){
			query 		=	"SELECT	NH.PK_SEQ AS NHID, '[' + CONVERT(VARCHAR, NH.PK_SEQ) + '] ' + '[' + NH.NGAYNHAN + '] " +
							"[PO:' + CONVERT(VARCHAR, MH.PK_SEQ) + ']' AS NH " +
							"FROM ERP_NHANHANG NH " +
							"INNER JOIN ERP_MUAHANG MH ON MH.PK_SEQ = NH.MUAHANG_FK AND MH.NGUONGOCHH = 'NN' " +
							"INNER JOIN ERP_NHACUNGCAP NCC ON NCC.PK_SEQ = MH.NHACUNGCAP_FK " +
							"WHERE NCC.PK_SEQ = " + this.nccId + " AND NH.TRANGTHAI = '1' AND NH.CONGTY_FK = " + this.congtyId + " " +
							"AND NH.PK_SEQ NOT IN (" +
							"SELECT PHIEUNHAPKHO_FK FROM ERP_THUENHAPKHAU WHERE CONGTY_FK = '" + this.congtyId + "' " ;
			
			if(this.id.length() > 0) 
				query = query + "AND PK_SEQ <> " + this.id + ") ";
			else
				query = query + ")";
		
			System.out.println("Lay nhan hang: " + query);
			this.pnkRs = db.get(query);
		}
		
		if(this.pnkId.length() > 0){
			query 		=	"SELECT	NH.PK_SEQ AS NHID, SP.PK_SEQ AS SPID, SP.MA + ' - ' + SP.TEN AS SANPHAM, " + 
							"NHSP.SOLUONGNHAN, NHSP.DONGIAVIET, NHSP.SOLUONGNHAN*NHSP.DONGIAVIET AS THANHTIEN " +
							"FROM ERP_NHANHANG NH " +					
							"INNER JOIN ERP_NHANHANG_SANPHAM NHSP ON NH.PK_SEQ = NHSP.NHANHANG_FK " +
							"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = NHSP.SANPHAM_FK " +
							"INNER JOIN ERP_MUAHANG MH ON MH.PK_SEQ = NH.MUAHANG_FK	AND MH.NGUONGOCHH = 'NN' " +				
							"INNER JOIN ERP_NHACUNGCAP NCC ON NCC.PK_SEQ = MH.NHACUNGCAP_FK " +
							"WHERE NH.PK_SEQ = '" + this.pnkId + "'";
		
			System.out.println("Lay nhan hang: " + query);
			this.tnkRs = db.get(query);
		}
				
	}
	
	public boolean Create()
	{	
		try 
		{

			db.getConnection().setAutoCommit(false);
			
			if(this.cqtId.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn cơ quan thuế";
				return false;
			}

			if(this.nccId.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn nhà cung cấp";
				return false;
			}

			if(this.pnkId.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn phiếu nhập kho";
				return false;
			}
			
			if(this.ngaynhap.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn ngày nhập";
				return false;
			}
			
			if(this.diengiai.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn diễn giải";
				return false;
			}

			String query = "insert ERP_THUENHAPKHAU(NGAYCHUNGTU, SOCHUNGTU, DIENGIAI, NCC_FK, PHIEUNHAPKHO_FK, NGAY, NGAYTAO, NGUOITAO,  NGAYSUA, NGUOISUA,  TRANGTHAI, CONGTY_FK, COQUANTHUE_FK, PTVAT, VAT) " +
							"values('" + this.ngaychungtu + "', N'" + this.sochungtu + "', N'" + this.diengiai + "', '" + this.nccId + "', '" + this.pnkId + "', '" + this.ngaynhap + "', " +
							"'" + this.getDateTime() + "', '" + this.userId + "', '" + this.getDateTime() + "', '" + this.userId + "', '0', '" + this.congtyId + "', " +
							"'" + this.cqtId + "', '" + this.ptVAT.replace(",", "") + "', '" + this.VAT.replace(",", "") + "')";
			
			System.out.println("___1.Insert: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_THUENHAPKHAU " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "select SCOPE_IDENTITY() as tnkId";
			ResultSet rs = db.get(query);
			if (rs.next())
			{
				this.id = rs.getString("tnkId");
				rs.close();
			}
			
			
			for(int i = 0; i < this.spId.length; i++)
			{
					this.thue[i] = "" + (Double.parseDouble(this.soluong[i].replace(",", ""))*Double.parseDouble(this.dongia[i].replace(",", ""))*Double.parseDouble(this.thuesuat[i]))/100; 
					query = "insert ERP_THUENHAPKHAU_CHITIET(THUENHAPKHAU_FK, SANPHAM_FK, SOLUONG, DONGIA, THANHTIEN, THUESUAT, THUENHAPKHAU)  " +
							"values('" + this.id + "', N'" + this.spId[i] + "', " + this.soluong[i].replace(",", "") + ", " + this.dongia[i].replace(",", "") + ", " +
									"" + Double.parseDouble(this.soluong[i].replace(",", ""))*Double.parseDouble(this.dongia[i].replace(",", "")) + ", " +
									"" + this.thuesuat[i].replaceAll(",", "") + ", " +
									"" + this.thue[i].replaceAll(",", "") + " )";
					
					System.out.println("2.___Lay chi tiet: " + query);
					if(!db.update(query))
					{
						this.msg = "Không thể tạo mới ERP_THUENHAPKHAU_CHITIET " + query;
						db.getConnection().rollback();
						return false;
					}
					
			}
			
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			this.msg = "Loi: " + e.getMessage();
			try 
			{
				db.getConnection().rollback();
			} 
			catch (java.sql.SQLException e1) {}
			return false;
		}
		
		return true;
	}
	
	public boolean Update() 
	{
		try 
		{
			db.getConnection().setAutoCommit(false);

			if(this.cqtId.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn cơ quan thuế";
				return false;
			}

			
			if(this.nccId.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn nhà cung cấp";
				return false;
			}

			if(this.pnkId.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn phiếu nhập kho";
				return false;
			}
			
			if(this.ngaynhap.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn ngày nhập";
				return false;
			}
			
			if(this.diengiai.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn diễn giải";
				return false;
			}

			String query = "update ERP_THUENHAPKHAU set NGAYCHUNGTU = '" + this.ngaychungtu + "', SOCHUNGTU = N'" + this.sochungtu + "', DIENGIAI = N'" + this.diengiai + "', NCC_FK = '" + this.nccId + "', " +
							"COQUANTHUE_FK = '" + this.cqtId + "', PTVAT = " + this.ptVAT.replace(",", "") + ", VAT = " + this.VAT.replace(",", "") + ", " +
							"PHIEUNHAPKHO_FK = '" + this.pnkId + "', NGAY = '" + this.ngaynhap + "', NGAYTAO = '" + this.getDateTime() + "', " +
							"NGUOITAO = '" + this.userId + "',  NGAYSUA = '" + this.getDateTime() + "', NGUOISUA = '" + this.userId + "' " +
							"WHERE PK_SEQ = '" + this.id + "' " ;
			
			System.out.println("___update: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_THUENHAPKHAU " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "DELETE ERP_THUENHAPKHAU_CHITIET WHERE THUENHAPKHAU_FK = " + this.id + "";
			this.db.update(query);
			
			for(int i = 0; i < this.spId.length; i++)
			{
//					this.thue[i] = "" + (Double.parseDouble(this.soluong[i].replace(",", ""))*Double.parseDouble(this.dongia[i].replace(",", ""))*Double.parseDouble(this.thuesuat[i]))/100;
					query = "insert ERP_THUENHAPKHAU_CHITIET(THUENHAPKHAU_FK, SANPHAM_FK, SOLUONG, DONGIA, THANHTIEN, THUESUAT, THUENHAPKHAU)  " +
							"values('" + this.id + "', N'" + this.spId[i] + "', " + this.soluong[i].replace(",", "") + ", " + this.dongia[i].replace(",", "") + ", " +
									"" + Double.parseDouble(this.soluong[i].replace(",", ""))*Double.parseDouble(this.dongia[i].replace(",", "")) + ", " +
									"" + this.thuesuat[i].replaceAll(",", "") + ", " +
									"" + this.thue[i].replaceAll(",", "") + ")";
					
					System.out.println("2.___Lay chi tiet: " + query);
					if(!db.update(query))
					{
						this.msg = "Không thể tạo mới ERP_THUENHAPKHAU_CHITIET " + query;
						db.getConnection().rollback();
						return false;
					}
					
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			this.msg = "Loi: " + e.getMessage();
			try 
			{
				db.getConnection().rollback();
			} 
			catch (java.sql.SQLException e1) {}
			return false;
		}
		
		return true;
	}
	
	public boolean UpdateVAT() 
	{
		try 
		{
			db.getConnection().setAutoCommit(false);

			String query = "update ERP_THUENHAPKHAU set PTVAT = " + this.ptVAT.replace(",", "") + ", VAT = " + this.VAT.replace(",", "") + ", " +
							"NGAYSUA = '" + this.getDateTime() + "', NGUOISUA = '" + this.userId + "' " +
							"WHERE PK_SEQ = '" + this.id + "' " ;
			
			System.out.println("___update: " + query);
			if(!db.update(query))
			{
				this.msg = "KhÃ´ng thá»ƒ cáº­p nháº­t VAT ERP_THUENHAPKHAU " + query;
				db.getConnection().rollback();
				return false;
			}
						
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			this.msg = "Loi: " + e.getMessage();
			try 
			{
				db.getConnection().rollback();
			} 
			catch (java.sql.SQLException e1) {}
			return false;
		}
		
		return true;
	}
	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	public String getCongtyId() 
	{
		return this.congtyId;
	}

	public void setCongtyId(String congtyId) 
	{
		this.congtyId = congtyId;
	}

	
	public String getDiengiai() {
		
		return this.diengiai;
	}

	
	public void setDiengiai(String diengiai) {
		
		this.diengiai = diengiai;
	}

	
	public String getNgaynhap() {
		
		return this.ngaynhap;
	}

	
	public void setNgaynhap(String tungay) {
		
		this.ngaynhap = tungay;
	}

	public String getNgaychungtu() {
		
		return this.ngaychungtu;
	}

	
	public void setNgaychungtu(String ngaychungtu) {
		
		this.ngaychungtu = ngaychungtu;
	}
	
	public String getSochungtu() {
		
		return this.sochungtu;
	}

	
	public void setSochungtu(String sochungtu) {
		
		this.sochungtu = sochungtu;
	}
	
	public String getPnkId() {
		
		return this.pnkId;
	}

	
	public void setPnkId(String khId) {
		
		this.pnkId = khId;
	}

	
	public ResultSet getPhieunhapRs() {
		
		return this.pnkRs;
	}

	
	public void setPhieunhapRs(ResultSet pnRs) {
		
		this.pnkRs = pnRs;
	}

	public ResultSet getTnkRs() {
		
		return this.tnkRs;
	}

	
	public void setTnkRs(ResultSet tnkRs) {
		
		this.tnkRs = tnkRs;
	}

	public void DbClose() 
	{
		try 
		{
			this.db.shutDown();
		} 
		catch (Exception e) {}
		
	}

}
