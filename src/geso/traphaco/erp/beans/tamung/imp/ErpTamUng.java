package geso.traphaco.erp.beans.tamung.imp;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.erp.beans.tamung.IErpTamUng;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ErpTamUng implements IErpTamUng
{

	String Id, UserId, Msg, NgayTamUng, SoTienTamUng, TienTeId, NhanVienId,duyettu,
			ThoiGianHoanUng, LyDoTamUng, HinhThucHoanUng,DoiTuongTamUng,NccId,TenHienThi, bophan = "" , nguoitao, PoId, nccId, congtyId, nppdangnhap, htttId, diachincc;
	ResultSet rsTienTe, rsPo, rsNcc, rsHttt, rsTtDuyet;
	dbutils db;
	
	String tigia;
	String soTienNT;

	public ErpTamUng(String Id)
	{
		this.Id = Id;
		this.UserId = "";
		this.Msg = "";
		this.NgayTamUng = "";
		this.SoTienTamUng = "0";
		this.TienTeId = "100000";
		this.NhanVienId = "";
		this.ThoiGianHoanUng = "";
		this.LyDoTamUng = "";
		this.HinhThucHoanUng = "";
		this.NccId="";
		this.DoiTuongTamUng="";
		this.TenHienThi="";
		this.PoId ="";
		this.nccId ="";
		this.nppdangnhap ="";
		this.congtyId = "";
		db =new dbutils();
		this.nguoitao = "";
		this.htttId ="";
		this.diachincc = "";
		this.duyettu = "";
		this.tigia = "1";
		this.soTienNT = "0";
		
	}

	public ErpTamUng()
	{
		this.Id = "";
		this.UserId = "";
		this.Msg = "";
		this.NgayTamUng = "";
		this.SoTienTamUng = "0";
		this.TienTeId = "100000";
		this.NhanVienId = "";
		this.ThoiGianHoanUng = "";
		this.LyDoTamUng = "";
		this.HinhThucHoanUng = "";
		this.NccId="";
		this.DoiTuongTamUng="";
		this.TenHienThi="";
		this.PoId ="";
		this.nccId ="";
		this.nppdangnhap ="";
		this.congtyId = "";
		db =new dbutils();
		this.nguoitao = "";
		this.htttId = "";
		this.diachincc = "";
		this.duyettu = "";
		this.tigia = "1";
		this.soTienNT = "0";
	}

	
	
	public String getNguoitao() {
		return nguoitao;
	}

	public void setNguoitao(String nguoitao) {
		this.nguoitao = nguoitao;
	}

	public String getSoTienNT() {
		return soTienNT;
	}

	public void setSoTienNT(String soTienNT) {
		this.soTienNT = soTienNT;
	}

	public String getTigia() {
		return tigia;
	}

	public void setTigia(String tigia) {
		this.tigia = tigia;
	}

	public String getId()
	{
		return this.Id;
	}

	public void setId(String Id)
	{
		this.Id = Id;
	}

	public String getNgayTamUng()
	{
		return this.NgayTamUng;
	}

	public void setNgayTamUng(String NgayTamUng)
	{
		this.NgayTamUng = NgayTamUng;
	}

	public String getNhanVienId()
	{
		return this.NhanVienId;
	}

	public void setNhanVienId(String NhanVienId)
	{
		this.NhanVienId = NhanVienId;
	}

	public String getSoTienTamUng()
	{

		return this.SoTienTamUng;
	}

	public void setSoTienTamUng(String SoTienTamUng)
	{
		this.SoTienTamUng = SoTienTamUng;
	}

	public String getTienTeId()
	{
		return this.TienTeId;
	}

	public void setTienTeId(String TienTeId)
	{
		this.TienTeId = TienTeId;
	}

	public String getThoiGianHoanUng()
	{
		return this.ThoiGianHoanUng;
	}

	public void setThoiGianHoanUng(String ThoiGianHoanUng)
	{
		this.ThoiGianHoanUng = ThoiGianHoanUng;
	}

	public String getUserId()
	{

		return this.UserId;
	}

	public void setUserId(String UserId)
	{
		this.UserId = UserId;
	}

	public String getMsg()
	{
		return this.Msg;
	}

	public void setMsg(String Msg)
	{
		this.Msg = Msg;

	}

	public ResultSet getRsTienTe()
	{

		return this.rsTienTe;
	}

	public void setRsTienTe(ResultSet rsTienTe)
	{
		this.rsTienTe = rsTienTe;
	}
	
	public String getTiGiaTheoTienTe(){
		
		
		if(this.TienTeId.equals("100000")){
			this.tigia = "1";
		}else{
			String query = 	"select top 1 TIGIAQUYDOI, DenNgay  " +
							"from ERP_TIGIA " +
							"where TIENTE_FK = " + this.TienTeId + " and ( '" + this.NgayTamUng + "' <= DenNgay and '" + this.NgayTamUng + "' >= TuNgay) " +
							"order by DenNgay desc";
			
			ResultSet rstigia = db.get(query);
			this.tigia = "1";
			
			if(rstigia != null){
				try {
					if(rstigia.next()){
						this.tigia = rstigia.getString("TIGIAQUYDOI");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return this.tigia;
	}
	
	public void Init()
	{
		this.getNppInfo();
		String query = 
				"SELECT NGAYTAMUNG, "+
				"CASE "+ 
				"	WHEN TU.NCC_FK IS NOT NULL THEN CAST(NCC.PK_SEQ  AS VARCHAR(18))+' -- '+NCC.MA + ','+NCC.TEN +' -- '+'NCC' "+ 
				"	WHEN TU.NHANVIEN_FK IS NOT NULL THEN CAST(NV.PK_SEQ AS VARCHAR(18)) +' -- '+NV.MA + ','+NV.TEN +' -- '+'NV' "+ 
				" END TENHIENTHI,isnull(TU.NCC_FK,0) NCC_FK ,TU.NHANVIEN_FK,SOTIENTAMUNG,LYDOTAMUNG,TU.TIENTE_FK,THOIGIANHOANUNG,HINHTHUCHOANUNG, " +
				"	CASE WHEN TU.NHANVIEN_FK IS NOT NULL THEN 1 ELSE 2 END DOITUONGTAMUNG , isnull(n.TEN, '') as nguoitao, muahang_fk dmhId, " +
				" TU.httt_fk htttId, isnull(TU.diachiNCC, '') diachiNCC, isnull(SOTIENTAMUNGNT,0) as SOTIENTAMUNGNT, ISNULL(TIGIA, 1) AS TIGIA  "+ 
				" FROM ERP_TAMUNG TU "+
				"	LEFT JOIN ERP_NHACUNGCAP NCC ON NCC.PK_SEQ=TU.NCC_FK "+
				"	LEFT JOIN ERP_NhanVien NV ON NV.pk_seq=TU.NHANVIEN_FK " +
				" 	LEFT JOIN NHANVIEN n on n.PK_SEQ = TU.NGUOITAO " +
				"	WHERE TU.PK_SEQ='"+this.Id+"' AND TU.CONGTY_FK = "+this.congtyId;
		
		ResultSet rs = this.db.get(query);
		
		System.out.println("Query init 1 "+query);
		if (rs != null)
		{
			try
			{
				while (rs.next())
				{
					this.NgayTamUng = rs.getString("NgayTamUng");
					this.NhanVienId = rs.getString("NhanVien_FK");
					this.nccId=rs.getString("NCC_FK");
					this.TenHienThi=rs.getString("TenHienThi");
					this.DoiTuongTamUng=rs.getString("DoiTuongTamUng");
					this.TienTeId = rs.getString("TienTe_FK");
					this.SoTienTamUng = rs.getString("SoTienTamUng");
					this.ThoiGianHoanUng = rs.getString("ThoiGianHoanUng");
					this.LyDoTamUng = rs.getString("LyDoTamUng");
					this.HinhThucHoanUng = rs.getString("HinhThucHoanUng");
					this.nguoitao = rs.getString("nguoitao");
					this.PoId = rs.getString("dmhId");
					this.htttId = rs.getString("htttId");
					this.diachincc = rs.getString("diachiNCC");
					this.soTienNT = rs.getString("SOTIENTAMUNGNT");
					this.tigia = rs.getString("TIGIA");
				}rs.close();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		createRs();
	}
	
	public void InitPDFNhanVien()
	{
		
		String query = 
				" SELECT NGAYTAMUNG, "+
				" 	isnull((CASE "+ 
				"		WHEN TU.NCC_FK IS NOT NULL THEN NCC.TEN "+ 
				"		WHEN TU.NHANVIEN_FK IS NOT NULL THEN NV.TEN "+ 
				" 	END),'') as TENHIENTHI, " +
				"	isnull(TU.NCC_FK,0) NCC_FK, TU.NHANVIEN_FK, isnull(SOTIENTAMUNG, 0) as SOTIENTAMUNG, isnull(LYDOTAMUNG, '') as LYDOTAMUNG, TU.TIENTE_FK, THOIGIANHOANUNG, HINHTHUCHOANUNG, " +
				" 	CASE WHEN TU.NHANVIEN_FK IS NOT NULL THEN 1 ELSE 2 END as DOITUONGTAMUNG, " +
				" 	ISNULL(DVTH.TEN, '') AS BOPHAN ,  isnull(n.TEN, '') as nguoitao"+ 
				" FROM ERP_TAMUNG TU "+
				" LEFT JOIN ERP_NHACUNGCAP NCC ON NCC.PK_SEQ = TU.NCC_FK "+
				" LEFT JOIN ERP_NhanVien NV ON NV.pk_seq = TU.NHANVIEN_FK  " +
				" LEFT JOIN ERP_DONVITHUCHIEN DVTH ON NV.DVTH_FK = DVTH.PK_SEQ " +
				" 	left join NHANVIEN n on n.PK_SEQ = TU.NGUOITAO " +
				" WHERE TU.PK_SEQ = "+this.Id+" ";
		ResultSet rs = this.db.get(query);
		System.out.println("Query init "+query);
		if (rs != null)
		{
			try
			{
				while (rs.next())
				{
					this.NgayTamUng = rs.getString("NgayTamUng");
					this.NhanVienId = rs.getString("NhanVien_FK");
					this.nccId=rs.getString("NCC_FK");
					this.TenHienThi=rs.getString("TenHienThi");
					this.DoiTuongTamUng=rs.getString("DoiTuongTamUng");
					this.TienTeId = rs.getString("TienTe_FK");
					this.SoTienTamUng = rs.getString("SoTienTamUng");
					this.ThoiGianHoanUng = rs.getString("ThoiGianHoanUng");
					this.LyDoTamUng = rs.getString("LyDoTamUng");
					this.HinhThucHoanUng = rs.getString("HinhThucHoanUng");
					this.bophan = rs.getString("bophan");
					this.nguoitao = rs.getString("nguoitao");
				}rs.close();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		createRs();
	}
	public void InitPDFNhaCungCap(){
		NumberFormat formatter = new DecimalFormat("#######"); 
		String query = 
				" SELECT NGAYTAMUNG, "+
				" 	isnull((CASE "+ 
				"		WHEN TU.NCC_FK IS NOT NULL THEN NCC.TEN "+ 
				"		WHEN TU.NHANVIEN_FK IS NOT NULL THEN NV.TEN "+ 
				" 	END),'') as TENHIENTHI, " +
				"	isnull(TU.NCC_FK,0) NCC_FK, TU.NHANVIEN_FK, isnull(SOTIENTAMUNG, 0) as SOTIENTAMUNG, isnull(SOTIENTAMUNGNT, 0) as SOTIENTAMUNGNT, isnull(LYDOTAMUNG, '') as LYDOTAMUNG, TU.TIENTE_FK, THOIGIANHOANUNG, HINHTHUCHOANUNG, " +
				" 	CASE WHEN TU.NHANVIEN_FK IS NOT NULL THEN 1 ELSE 2 END as DOITUONGTAMUNG, " +
				" 	ISNULL(DVTH.TEN, '') AS BOPHAN ,  isnull(n.TEN, '') as nguoitao,TU.HTTT_FK AS HTTT "+ 
				" FROM ERP_TAMUNG TU "+
				" LEFT JOIN ERP_NHACUNGCAP NCC ON NCC.PK_SEQ = TU.NCC_FK "+
				" LEFT JOIN ERP_NhanVien NV ON NV.pk_seq = TU.NHANVIEN_FK  " +
				" LEFT JOIN ERP_DONVITHUCHIEN DVTH ON NV.DVTH_FK = DVTH.PK_SEQ " +
				" 	left join NHANVIEN n on n.PK_SEQ = TU.NGUOITAO " +
				" WHERE TU.PK_SEQ = "+this.Id+" ";
		ResultSet rs = this.db.get(query);
		System.out.println("Query init "+query);
		if (rs != null)
		{
			try
			{
				while (rs.next())
				{
					this.NgayTamUng = rs.getString("NgayTamUng");
					this.NhanVienId = rs.getString("NhanVien_FK");
					this.nccId=rs.getString("NCC_FK");
					this.TenHienThi=rs.getString("TenHienThi");
					this.DoiTuongTamUng=rs.getString("DoiTuongTamUng");
					this.TienTeId = rs.getString("TienTe_FK");
					this.SoTienTamUng = formatter.format(rs.getDouble("SoTienTamUng"));
					this.ThoiGianHoanUng = rs.getString("ThoiGianHoanUng");
					this.LyDoTamUng = rs.getString("LyDoTamUng");
					this.HinhThucHoanUng = rs.getString("HinhThucHoanUng");
					this.htttId = rs.getString("HTTT");
					this.bophan = rs.getString("bophan");
					this.nguoitao = rs.getString("nguoitao");
					this.soTienNT  = rs.getString("SoTienTamUngNT");
				}rs.close();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		createRs();
	}

	public boolean Save()
	{
		
		String ngaytao=getDateTime();
		
		System.out.println("DoiTuongTamUng: "+ DoiTuongTamUng);
		System.out.println("ThoiGianHoanUng: "+ ThoiGianHoanUng);
		if(DoiTuongTamUng.equals("1")){
			if(this.ThoiGianHoanUng.trim().length()<=0){
				this.Msg ="Vui lòng chọn thời gian hoàn ứng";
				return false;
			}
			
			if(this.HinhThucHoanUng.trim().length()<=0){
				this.Msg ="Vui lòng nhập hình thức hoàn ứng";
				return false;
			}
			
			if(!CheckValid())
			{
				return false;
			}
		}
				
		String query="";
		try
		{
			this.db.getConnection().setAutoCommit(false);
			
			String ngaydenhantt = "";
			if(this.nccId.trim().length() > 0)
			{
				// TÍNH NGÀY ĐẾN HẠN THANH TOÁN (DÙNG TRONG PHIẾU CHI) : ngày hóa đơn(Hóa đơn NCC) + thời hạn nợ(DLN)
				query = "SELECT CONVERT(nvarchar(10), (dateadd(DAY, ISNULL(THOIHANNO,0), '" + this.NgayTamUng+ "')),120 ) as ngaydenhantt " +
						"FROM ERP_NHACUNGCAP " +
						"WHERE PK_SEQ = '"+ this.nccId +"'";
				
				System.out.println(query);
				ResultSet rsThoihanno = db.get(query);
				if(rsThoihanno!= null)
				{
					while(rsThoihanno.next())
					{
						ngaydenhantt = rsThoihanno.getString("ngaydenhantt") ;
					}
					rsThoihanno.close();
				}
			}
			
		
			 query="INSERT INTO ERP_TAMUNG ( NGAYTAMUNG,NCC_FK,NHANVIEN_FK,LYDOTAMUNG,SOTIENTAMUNG,TIENTE_FK,THOIGIANHOANUNG,HINHTHUCHOANUNG,TRANGTHAI,NGUOITAO,NGAYTAO,NGUOISUA,NGAYSUA, NGAYDENHANTT, muahang_fk, CONGTY_FK, HTTT_FK, diachiNCC, SOTIENTAMUNGNT, TIGIA) VALUES" +
					"('"+this.NgayTamUng+"',"+(this.nccId.trim().length()==0?null:this.nccId)+", "+(this.NhanVienId.trim().length()==0?null:this.NhanVienId)+", N'"+this.LyDoTamUng+"','"+this.SoTienTamUng+"'," +
					"'"+this.TienTeId+"','"+(this.ThoiGianHoanUng.trim().length()==0?"":this.ThoiGianHoanUng)+"',N'"+(this.HinhThucHoanUng.trim().length()==0?"":this.HinhThucHoanUng)+"',0,'"+this.UserId+"','"+ngaytao+"','"+this.UserId+"','"+ngaytao+"', '"+ ngaydenhantt +"'," +(this.PoId.trim().length()==0?null:this.PoId)+ ", "+this.congtyId+", " +
							""+this.htttId+", N'"+this.diachincc+"', '"+this.soTienNT+"', '"+this.tigia+"') ";
			
			 System.out.println(query);
			if(!this.db.update(query))
			{
				this.db.getConnection().rollback();
				this.Msg="Loi dong lenh sau "+query;
				return false;
			}
			
			/* //thêm chi tiết chi phí :
			
			query = "select SCOPE_IDENTITY() as TAMUNGId";
			ResultSet rsmh_sp = db.get(query);
			String mhspid="";
			if (rsmh_sp.next())
			{
				mhspid = rsmh_sp.getString("TAMUNGId");
			 
				rsmh_sp.close();
			}*/
						
			/*// QUY TRÌNH MỚI
			
			// Kiểm tra và chèn các cấp duyệt vào bảng ERP_DUYETTAMUNG
			
			//1. KIỂM TRA CẤP DUYỆT CỦA USER ĐĂNG NHẬP
			
			String nhanvien_fk = this.UserId;
			
			query = "\n SELECT distinct A.LOAICAP_FK, A.NHANVIEN_FK " +
					"\n FROM ( "+
					
					"\n 		SELECT A.LOAICAP_FK, A.NHANVIEN_FK  " +
					"\n 		FROM ERP_CAPQUANLY_CT A INNER JOIN ERP_CAPQUANLY B ON A.CAPQUANLY_FK = B.PK_SEQ " +
					"\n 		WHERE A.CONGTY_FK = "+this.congtyId+" AND B.TRANGTHAI = 1 AND A.NHANVIEN_FK = " + nhanvien_fk +
					
					"\n 		UNION ALL "+
					
					"\n 		SELECT A.LOAICAP_FK, NVQL_FK NHANVIEN_FK " +
					"\n 		FROM ERP_CAPQUANLY A WHERE A.CONGTY_FK = "+this.congtyId+" AND A.TRANGTHAI = 1 AND A.NVQL_FK = "+nhanvien_fk +
					
					"\n ) A  ";
			
			System.out.println(query);
			ResultSet RsKt = db.get(query);
			int stt = 0;
			String loaicap_fk = "";
			
			if(RsKt!=null)
			{
				while(RsKt.next())
				{
					stt++;
					loaicap_fk =  RsKt.getString("LOAICAP_FK");
				}
				RsKt.close();
			}
			
			// NẾU 1 NHÂN VIÊN CÓ 2 QUYỀN => BÁO LỖI
			
			if(stt == 0)
			{
				this.Msg = "Nhân viên này không có trong phân quyền tạo đề nghị tạm ứng. Yêu cầu tạo quyền cho nhân viên này";
				db.getConnection().rollback();
				return false;
			}
			
			if(stt > 1)
			{
				this.Msg = "Nhân viên này có 2 quyền. Vui lòng ktra lại";
				db.getConnection().rollback();
				return false;
			}
						
			// INSERT QUYỀN CỦA NHÂN VIÊN VỪA TẠO VÀO BẢNG DUYỆT ĐƠN HÀNG - TỰ DUYỆT
			
			query = " INSERT ERP_DUYETTAMUNG ( TAMUNG_FK, TRANGTHAI, NHANVIEN_FK, LOAICAP_FK, THUTU ) " +
					" VALUES ( "+mhspid+", 1 , "+nhanvien_fk+", "+loaicap_fk+", 0 ) ";
			
			if (!db.update(query))
			{
				this.Msg = "Khong the cap nhat ERP_DUYETTAMUNG: " + query;
				db.getConnection().rollback();
				return false;
			}*/
		
			
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		} catch (SQLException e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean Edit()
	{
		String ngaysua=getDateTime();
		String query="";
		
		if(DoiTuongTamUng.equals("1")){
			
			if(this.ThoiGianHoanUng.trim().length()<=0){
				this.Msg ="Vui lòng chọn thời gian hoàn ứng";
				return false;
			}
			
			if(this.HinhThucHoanUng.trim().length()<=0){
				this.Msg ="Vui lòng nhập hình thức hoàn ứng";
				return false;
			}
			
			if(!CheckValid())
			{
				return false;
			}
		}
		
		
		try
		{
			this.db.getConnection().setAutoCommit(false);
			
			String ngaydenhantt = "";
			
			if(this.nccId.trim().length() > 0)
			{
				// TÍNH NGÀY ĐẾN HẠN THANH TOÁN (DÙNG TRONG PHIẾU CHI) : ngày hóa đơn(Hóa đơn NCC) + thời hạn nợ(DLN)
				query = "SELECT CONVERT( nvarchar( 10 ), ( dateadd( DAY, ISNULL( THOIHANNO,0 ), '" + this.NgayTamUng+ "' ) ),120 ) as ngaydenhantt " +
						"FROM ERP_NHACUNGCAP " +
						"WHERE PK_SEQ = '"+ this.nccId +"'";
				
				ResultSet rsThoihanno = db.get(query);
				
				if(rsThoihanno!= null)
				{
					while(rsThoihanno.next())
					{
						ngaydenhantt = rsThoihanno.getString("ngaydenhantt") ;
					}
					rsThoihanno.close();
				}
			}
			
			 query=" UPDATE ERP_TAMUNG SET NHANVIEN_FK = "+(this.NhanVienId.trim().length()==0?null:this.NhanVienId)+", NCC_FK = "+(this.nccId.trim().length()==0?null:this.nccId)+"," +
			 	   " NGAYTAMUNG ='"+this.NgayTamUng+"',LYDOTAMUNG =N'"+this.LyDoTamUng+"',SOTIENTAMUNG='"+this.SoTienTamUng+"',TIENTE_FK='"+this.TienTeId+"'," +
			 	   " THOIGIANHOANUNG='"+(this.ThoiGianHoanUng.trim().length()==0?"":this.ThoiGianHoanUng)+"',HINHTHUCHOANUNG=N'"+(this.HinhThucHoanUng.trim().length()==0?"":this.HinhThucHoanUng)+"',NGUOISUA='"+this.UserId+"',NGAYSUA='"+ngaysua+"', NGAYDENHANTT ='"+ ngaydenhantt +"', muahang_fk = " + (this.PoId.trim().length()==0?null:this.PoId)+", diachiNCC = N'"+this.diachincc+"', " +
			 	   		"HTTT_FK = "+this.htttId+ ",SOTIENTAMUNGNT = '"+this.soTienNT+"', TIGIA='"+this.tigia+"'" +
			 	   " WHERE PK_SEQ='"+this.Id+"' and trangthai=0";
			 
			if(this.db.updateReturnInt(query)!=1)
			{
				this.db.getConnection().rollback();
				this.Msg="Loi dong lenh sau "+query;
				return false;
			}
			
			/*
			//1. KIỂM TRA CẤP DUYỆT CỦA USER ĐĂNG NHẬP
			
			String nhanvien_fk = this.UserId;
			
			query = "\n SELECT distinct A.LOAICAP_FK, A.NHANVIEN_FK, (SELECT THUTU FROM ERP_DUYETTAMUNG WHERE TAMUNG_FK = "+this.Id+" AND NHANVIEN_FK = "+this.UserId+") THUTU " +
					"\n FROM ( "+
			
					"\n 		SELECT A.LOAICAP_FK, A.NHANVIEN_FK  " +
					"\n 		FROM ERP_CAPQUANLY_CT A INNER JOIN ERP_CAPQUANLY B ON A.CAPQUANLY_FK = B.PK_SEQ " +
					"\n 		WHERE A.CONGTY_FK = "+this.congtyId+" AND B.TRANGTHAI = 1 AND A.NHANVIEN_FK = " + nhanvien_fk +
					
					"\n 		UNION ALL "+
					
					"\n 		SELECT A.LOAICAP_FK, NVQL_FK NHANVIEN_FK " +
					"\n 		FROM ERP_CAPQUANLY A WHERE A.CONGTY_FK = "+this.congtyId+" AND A.TRANGTHAI = 1 AND A.NVQL_FK = "+nhanvien_fk +
					
					"\n ) A  ";
			
			System.out.println(query);
			
			ResultSet RsKt = db.get(query);
			int stt = 0;
			String loaicap_fk = "";
			int thutu = 0;
			
			if(RsKt!=null)
			{
				while(RsKt.next())
				{
					stt++;
					loaicap_fk =  RsKt.getString("LOAICAP_FK");
					thutu = RsKt.getInt("THUTU");
				}
				RsKt.close();
			}
			
			if(stt == 0)
			{
				this.Msg = "Nhân viên này không có trong phân quyền tạo đề nghị tạm ứng. Yêu cầu tạo quyền cho nhân viên này";
				db.getConnection().rollback();
				return false;
			}
			
			// NẾU 1 NHÂN VIÊN CÓ 2 QUYỀN => BÁO LỖI
			if(stt > 1)
			{
				Msg = "Nhân viên này có 2 quyền. Vui lòng ktra lại";
				db.getConnection().rollback();
				return false;
			}
			
			// CẬP NHẬT QUYỀN CỦA USER TRONG ERP_DUYETMUAHANG
			
			query = " SELECT count(TAMUNG_FK) dem FROM ERP_DUYETTAMUNG WHERE NHANVIEN_FK = "+nhanvien_fk +" AND LOAICAP_FK = "+loaicap_fk+ " AND TAMUNG_FK = "+this.Id;
			
			System.out.println(query);
			RsKt = db.get(query);
			int count = 0;
			
			if(RsKt!=null)
			{
				while(RsKt.next())
				{
					stt++;
					count = RsKt.getInt("dem");
				}
				RsKt.close();
			}			
			
			if(count<=0){ // CHƯA CÓ TRONG BẢNG DUYỆT						
				query = " INSERT ERP_DUYETTAMUNG ( TAMUNG_FK, NHANVIEN_FK, LOAICAP_FK, TRANGTHAI, THUTU ) SELECT TAMUNG_FK, NHANVIEN_FK, LOAICAP_FK, 0 , THUTU  FROM ERP_DUYETTAMUNG_NHAP WHERE TAMUNG_FK = "+this.Id+" AND NHANVIEN_FK = "+nhanvien_fk;
						
				if (!db.update(query))
				{
					Msg = "Khong the cap nhat ERP_DUYETMUAHANG: " + query;
					db.getConnection().rollback();
					return false;
				}
			}	*/		
			
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		} catch (SQLException e)
		{
			e.printStackTrace();
			return false;
		}
		return true;	
	}

	public String Chot()
	{
		try {
			this.db.getConnection().setAutoCommit(false);
					
			if(this.UserId==null ||this.UserId.equals("")|| this.UserId.equals("null") )
				return "Vui lòng đăng nhập lại";
			
			String query=" UPDATE ERP_TAMUNG SET ISDACHOT = 1, NGUOISUA = '"+this.UserId+"', NGUOICHOT_FK = "+this.UserId+" WHERE PK_SEQ='"+this.Id+"'";
			
			System.out.println(query);
			if(!this.db.update(query))
				return "Không thể chốt tạm ứng này "+query;
			
			
			/*// KIỂM TRA LOẠI CẤP CỦA NHÂN VIÊN NÀY TRONG DUYET TẠM ỨNG
			
			query = "SELECT loaicap_fk FROM ERP_DUYETTAMUNG WHERE TAMUNG_FK = "+this.Id + " AND NHANVIEN_FK = "+this.UserId;
			ResultSet rs = db.get(query);
			
			String loaicap_fk = "";
			rs = db.get(query);
			if(rs != null)
			{
				if(rs.next())
				{
					loaicap_fk = rs.getString("loaicap_fk");
					rs.close();
				}
			}
						
			if(loaicap_fk.equals("10000")) // CẤP QUẢN LÝ TRỰC TIẾP
			{
				query = " UPDATE ERP_TAMUNG SET ISQLTT = 1 WHERE PK_SEQ = "+this.Id;	
				
				System.out.println("3.quanlycap : " + query);
				
				if (!db.update(query))
				{
					Msg = "Khong the cap nhat ERP_DUYETMUAHANG: " + query;
					db.getConnection().rollback();
					return Msg;
				}
				
			}
			else if(loaicap_fk.equals("10001")) // QUẢN LÝ CS
			{
				query = " UPDATE ERP_TAMUNG SET ISCS = 1 WHERE PK_SEQ = "+this.Id;		
				
				System.out.println("4.quanlycap : " + query);
				
				if (!db.update(query))
				{
					Msg = "Khong the cap nhat ERP_DUYETMUAHANG: " + query;
					db.getConnection().rollback();
					return Msg;
				}
			}
			else if(loaicap_fk.equals("10002")) //  QUẢN LÝ DUYET ĐNTT/ĐNTU
			{
				query = " UPDATE ERP_TAMUNG SET ISDUYETCHI = 1 , TRANGTHAI = 1 WHERE PK_SEQ = "+this.Id;		
				
				System.out.println("4.quanly duyệt chi : " + query);
				
				if (!db.update(query))
				{
					Msg = "Khong the cap nhat ERP_DUYETMUAHANG: " + query;
					db.getConnection().rollback();
					return Msg;
				}
			}
			else if(loaicap_fk.equals("10003")) //  QUẢN LÝ KTTH
			{
				query = " UPDATE ERP_TAMUNG SET ISKTTH = 1 WHERE PK_SEQ = "+this.Id;		
				
				System.out.println("4.quanly duyệt chi : " + query);
				
				if (!db.update(query))
				{
					Msg = "Khong the cap nhat ERP_DUYETMUAHANG: " + query;
					db.getConnection().rollback();
					return Msg;
				}
			}			
			else if(loaicap_fk.equals("10004")) //  QUẢN LÝ KTTH
			{
				query = " UPDATE ERP_TAMUNG SET ISKTT = 1, TRANGTHAI = 1, isDaChi = 1 WHERE PK_SEQ = "+this.Id;		
				
				System.out.println("4.quanly duyệt chi : " + query);
				
				if (!db.update(query))
				{
					Msg = "Khong the cap nhat ERP_DUYETMUAHANG: " + query;
					db.getConnection().rollback();
					return Msg;
				}
			}*/
			
			/*if(loaicap_fk.equals("10004"))
			{	
				//TỰ TẠO PHIẾU CHI || ỦY NHIỆM CHI
				
				query = " SELECT TU.NGAYTAMUNG, TU.NCC_FK, TU.NHANVIEN_FK, TU.SOTIENTAMUNG, TU.LYDOTAMUNG, TU.TIENTE_FK, TU.HTTT_FK HTTTID, TU.NGUOITAO " +
						" FROM ERP_TAMUNG TU " +
						" LEFT JOIN ERP_NHACUNGCAP NCC ON NCC.PK_SEQ = TU.NCC_FK "+
						" LEFT JOIN ERP_NHANVIEN NV ON NV.PK_SEQ = TU.NHANVIEN_FK "+
						" LEFT JOIN NHANVIEN N ON N.PK_SEQ = TU.NGUOITAO "+
						" WHERE TU.PK_SEQ = '"+this.Id+"' AND TU.CONGTY_FK = "+this.congtyId;
				System.out.println(query);
				ResultSet RsTamUng = db.get(query);
				
				String ngaytamung = "";
				String ncc_fk = "";
				String nhanvien_fk = "";
				double sotientamung = 0;
				String htttId = "";
				String tiente_fk = "";
				String nguoitao = "";
				
				if(RsTamUng!=null)
				{
					try 
					{
						while (RsTamUng.next())
						{
							ngaytamung = RsTamUng.getString("NGAYTAMUNG");
							ncc_fk = RsTamUng.getString("NCC_FK");
							nhanvien_fk =  RsTamUng.getString("NHANVIEN_FK");
							sotientamung = RsTamUng.getDouble("SOTIENTAMUNG");
							htttId = RsTamUng.getString("HTTTID");
							tiente_fk = RsTamUng.getString("tiente_fk");
							nguoitao = RsTamUng.getString("NGUOITAO");
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				double TienTamUng_VND =  0;
				double TienTamUng_NT = 0;
				double SoTienHD_VND = 0;
				double SoTienHD_NT = 0;
				
				if(tiente_fk.equals("100000"))
				{
					TienTamUng_VND = sotientamung;
					SoTienHD_VND  =  sotientamung;
					System.out.println("TienTamUng_VND");
				}
				else
				{
					TienTamUng_NT = sotientamung;
					SoTienHD_NT = sotientamung;
					System.out.println("TienTamUng_NT");
				}
				
				String prefix = "";
				if(htttId.equals("100000"))
					prefix = "DNPC";
				
				if(htttId.equals("100001"))
					prefix = "DNBN";
					
				query = 
					"Insert ERP_THANHTOANHOADON " +
					"( DVTH_FK, NGAYGHINHAN, NCC_FK ,NHANVIEN_FK, HTTT_FK, NGANHANG_FK, CHINHANH_FK, SOTAIKHOAN, NOIDUNGTT, " +
					"  SOTIENTT, SOTIENTTNT, SOTIENHD, SOTIENHDNT, PHINGANHANG, PHINGANHANGNT, VAT, VATNT, CHENHLECHVND, " +
					"  TRICHPHI, SOTAIKHOAN_TP, NGANHANG_TP_FK, CHINHANH_TP_FK, " +
					"  NGAYTAO, NGUOITAO, NGAYSUA, NGUOISUA, LOAITHANHTOAN, TIENTE_FK, TIGIA , THANHTOANTUTIENVAY, KHACHHANG_FK, CTKEMTHEO, CONGTY_FK, PREFIX " +
					") " +
					"values(NULL, '" + getDateTime() + "', " + ncc_fk + "," + " "+nhanvien_fk +", '" + htttId + "', " +
							" NULL, NULL , NULL , N'Thanh toán cho đề nghị tạm ứng số "+this.Id+"', " +
							"" + sotientamung + ", "+ TienTamUng_NT + ", " + SoTienHD_VND  + ", " + SoTienHD_NT + " , " +
							" 0 , 0 , 0 ,0, 0, 0, '', null , null , '"  + getDateTime() + "', '" + nguoitao + "', '" + getDateTime() + "', '" 
							+ nguoitao + "',0, " + tiente_fk + ", 1 , '0', null , N'', "+this.congtyId+", '"+prefix+ "')";
				
				System.out.println(query);
				
				if(!db.update(query))
				{
					this.Msg = "Khong the tao moi Phiếu Chi/UNC: " + query;
					System.out.println(this.Msg);
					db.getConnection().rollback();
					return this.Msg;
				}
						
				query = "select IDENT_CURRENT('ERP_THANHTOANHOADON') as tthdId";
				
				ResultSet rsTthd = db.get(query);	
				String tthdCurrent = "";
				if(rsTthd.next())
				{
					tthdCurrent = rsTthd.getString("tthdId");
					rsTthd.close();
				}
				
				// cập nhật mã chứng từ
				query = " update ERP_THANHTOANHOADON set machungtu =  Prefix + SUBSTRING(NGAYGHINHAN, 6, 2) + SUBSTRING(NGAYGHINHAN, 0, 5) + '-' + dbo.LaySoChungTu( " + tthdCurrent + " ) " + 
						" where pk_seq = '" + tthdCurrent + "' ";
				
				System.out.println("[ERP_THANHTOANHOADON] error message:" + query);
				
				if(!db.update(query))
				{
					this.Msg = "Khong the tao moi ERP_THANHTOANHOADON_HOADON: " + query;
					System.out.println(this.Msg);
					db.getConnection().rollback();
					return this.Msg;
				}		
				
				//TRONG BẢNG ERP_THANHTOANHOADON_HOADON LOAIHD = 1 LÀ ĐỂ NGHỊ TẠM ỨNG
				if(tiente_fk.equals("100000")){
					query = "Insert ERP_THANHTOANHOADON_HOADON(THANHTOANHD_FK, HOADON_FK, SOTIENTT, SOTIENAVAT, SOTIENNT, CONLAI, LOAIHD, SOHOADON) " +
							"values('" + tthdCurrent + "', '" + this.Id + "', '" + sotientamung + "', '" + sotientamung + "'," +
									" 0, 0 , '1', '"+ this.Id +"')";
								
				}else{
					query = "Insert ERP_THANHTOANHOADON_HOADON(THANHTOANHD_FK, HOADON_FK, SOTIENTT, SOTIENAVAT, SOTIENNT, CONLAI, LOAIHD, SOHOADON) " +
							"values('" + tthdCurrent + "', '" + this.Id + "', '" + sotientamung + "', '" + sotientamung + "', " + sotientamung + "," +
									" '0', '1',  '"+ this.Id +"')";																				
					
				}					
				
				System.out.println(query);
				
				if(!db.update(query))
				{
					this.Msg = "Khong the tao moi ERP_THANHTOANHOADON_HOADON: " + query;
					System.out.println(this.Msg);
					db.getConnection().rollback();
					return this.Msg;
				}				
				
				query = 
				"INSERT INTO ERP_THANHTOANHOADON_PHINGANHANG(THANHTOANHD_FK, MAHOADON, MAUHOADON, KYHIEU, SOHOADON, " +
				"NGAYHOADON, TENNCC, MST, TIENHANG, THUESUAT, TIENTHUE, NGANHANG_FK, CHINHANH_FK) " +
				"VALUES(" + tthdCurrent + ", N'', N'', N'', ''," +
				"'" + getDateTime() + "', N'', '', 0, 0, 0, " +
				"null, null)";
		
				System.out.println(query);
				
				if(!db.update(query))
				{
					this.Msg = "Khong the tao moi ERP_THANHTOANHOADON_HOADON: " + query;
					System.out.println(this.Msg);
					db.getConnection().rollback();
					return this.Msg;
				}
				
			}*/
			
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			db.shutDown();
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		return "";
	}   
	
	public String Delete()
	{
		if(this.UserId==null ||this.UserId.equals("")|| this.UserId.equals("null") )
			return "Vui lòng đăng nhập lại";
		String query="UPDATE ERP_TAMUNG SET TRANGTHAI = 2, NGUOISUA='"+this.UserId+"' WHERE PK_SEQ='"+this.Id+"' and trangthai=0";
		if(this.db.updateReturnInt(query)!=1)
			return "Không thể xóa tạm ứng này "+query;
		return "";
	}

	public void createRs()
	{
		this.getNppInfo();
		String query = "SELECT PK_SEQ,MA,TEN  FROM ERP_TIENTE WHERE TRANGTHAI=1";
		this.rsTienTe = this.db.get(query);
		
		String po = 
		" select a.PK_SEQ, cast(a.SOPO as nvarchar(50)) + ' - ' + a.ngaymua +'- HD:' +isnull(a.SOHOPDONG,'') as dmhId, ISNULL(tt.ma, 'NA') as tiente \n"+
		" from 	 erp_muahang a left join ERP_TIENTE tt on a.tiente_fk = tt.pk_seq  \n "+ 
		" WHERE A.trangthai != 4 AND a.CONGTY_FK = "+this.congtyId;
		
		System.out.println("nccId" + nccId);
		if(nccId.trim().length()>0){
			po += " and a.nhacungcap_fk='"+nccId+"'";
		}
		
		System.out.println("lay PO:"+po);
		this.rsPo = this.db.get(po);
		
		String ncc=
		" select PK_SEQ, (MA+' - '+TEN) as Ten from ERP_NHACUNGCAP where TRANGTHAI = 1 and duyet = '1' AND CONGTY_FK = "+this.congtyId;
		
		System.out.println(ncc);
		this.rsNcc = this.db.get(ncc);
		
		this.rsHttt = db.get("select pk_seq, ten from ERP_HINHTHUCTHANHTOAN where trangthai = '1' and PK_SEQ IN (100000,100001)");
		
		this.rsTtDuyet = db.get( " SELECT C.TEN CAP, B.TEN TENNV, CASE A.TRANGTHAI WHEN 0 THEN N'Chưa duyệt' WHEN 1 THEN N'Đã duyệt' WHEN 2 THEN N'Đã xóa' END TRANGTHAI, \n"+
								 " ISNULL(A.LYDOMODUYET, '') MODUYET, ISNULL(A.LYDOSUA, '') LYDOSUA, ISNULL(A.LYDOXOA, '') LYDOXOA \n"+
								 " FROM ERP_DUYETTAMUNG A INNER JOIN NHANVIEN B ON A.NHANVIEN_FK = B.PK_SEQ \n"+
								 " INNER JOIN ERP_LOAICAPQUANLY C ON C.PK_SEQ = A.LOAICAP_FK \n"+
								 " WHERE A.TAMUNG_FK = "+this.Id);
	}

	public void changeNCC()
	{
		String query = "select DIACHI diachincc, isnull(thoihanno, 0) as thoihanno, isnull(quanlycongno, 0) as quanlycongno from ERP_NhaCungCap where pk_seq = '" + nccId + "' ";
		System.out.println("lay dcncc: \n" + query + "\n------------------------");
		
		ResultSet rsNcc = db.get(query);
		try 
		{
			if(rsNcc.next())
			{
				this.diachincc = rsNcc.getString("diachincc");
			}
			rsNcc.close();
		}
		catch (SQLException e) { 
			e.printStackTrace();
		}
	}
	
	public void DBClose()
	{
		
			try
			{
				if(this.rsTienTe!=null)
				{
				this.rsTienTe.close();
				}
				if(this.db!=null)
					this.db.shutDown();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
	}
	public boolean CheckValid()
	{
		try{
    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        	Date ngatamung = sdf.parse(this.NgayTamUng);
        	Date ngaytra = sdf.parse(this.ThoiGianHoanUng);
        	System.out.println(sdf.format(ngatamung));
        	System.out.println(sdf.format(ngaytra));
        	if(ngaytra.compareTo(ngatamung)<0)
        	{
        		this.Msg="Thoi gian hoan ung phai lon hon ngay tam ung!";
        		System.out.println("Date1 is after Date2");
        		return false;
        	}
        	
    	}catch(ParseException ex){
    		ex.printStackTrace();
    		this.Msg="Khong dung dinh dang!";
    		return false;
    	}
		if(this.nccId.trim().length()<=0 && this.NhanVienId.trim().length()<=0)
		{
			this.Msg="Vui long nhap doi tuong ";
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

	
	public String getLyDoTamUng()
	{
		return this.LyDoTamUng;
	}

	
	public void setLyDoTamUng(String LyDoTamUng)
	{
		this.LyDoTamUng=LyDoTamUng;	
	}

	
	public String getHinhThucHoanUng()
	{
		
		return this.HinhThucHoanUng;
	}

	
	public void setHinhThucHoanUng(String hinhThucHoanUng)
	{
		this.HinhThucHoanUng=hinhThucHoanUng;
		
	}

	
	public String getNccId() 
	{
		return this.NccId;
	}

	
	public void setNccId(String NccId) 
	{
		this.NccId=NccId;
		
	}

	
	public String getDoiTuongTamUng() 
	{	
		return this.DoiTuongTamUng;
	}

	
	public void setDoiTuongTamUng(String DoiTuongTamUng) 
	{
		this.DoiTuongTamUng=DoiTuongTamUng;
		
	}

	
	public String getTenHienThi() {
	
		return this.TenHienThi;
	}

	
	public void setTenHienThi(String TenHienThi) {
		this.TenHienThi=TenHienThi;
		
	}

	
	public String getBoPhan() {
		return this.bophan;
	}

	
	public void setBoPhan(String bophan) {
		this.bophan = bophan;
	}

	
	public String getPoId() {
		
		return this.PoId;
	}

	
	public void setPoId(String PoId) {
		
		this.PoId = PoId;
	}

	
	public ResultSet getRsPo() {
		
		return this.rsPo;
	}

	
	public void setRsPo(ResultSet rsPo) {
		
		this.rsPo = rsPo;
	}

	
	public String getnccId() {
		
		return this.nccId;
	}

	
	public void setnccId(String nccId) {
		
		this.nccId = nccId;
	}

	
	public ResultSet getRsNcc() {
		
		return this.rsNcc;
	}

	
	public void setRsNcc(ResultSet rsNcc) {
		
		this.rsNcc = rsNcc;
	}

	
	public String getCongtyId() {
		
		return this.congtyId;
	}

	
	public void setCongtyId(String congtyId) {
		
		this.congtyId = congtyId;
	}

	
	public String getnppdangnhap() {
		
		return this.nppdangnhap;
	}

	
	public void setnppdangnhap(String nppdangnhap) {
		
		this.nppdangnhap = nppdangnhap;
	}
	
	private void getNppInfo()
	{		
		//Phien ban moi
		geso.traphaco.distributor.util.Utility util=new geso.traphaco.distributor.util.Utility();
		this.nppdangnhap=util.getIdNhapp(this.UserId);
	}

	
	public String getHtttId() {
		
		return this.htttId;
	}

	
	public void setHtttId(String Httt) {
		
		this.htttId = Httt;
	}

	
	public ResultSet getHtttRs() {
		
		return this.rsHttt;
	}

	
	public void setHtttRs(ResultSet HtttRs) {
		
		this.rsHttt = HtttRs;
	}

	
	public String getdiachincc() {
//		String diachi = "";
//		if(this.diachincc.trim().length() == 0 & this.NccId.length() > 0){
//			String query = "SELECT ISNULL(DIACHI, '') AS DIACHI FROM ERP_NHACUNGCAP WHERE PK_SEQ = " + this.NccId + "";
//			ResultSet rs = this.db.get(query);
//			try{
//				if (rs != null)
//				{
//					if (rs.next())
//						diachi = rs.getString("DIACHI");
//					rs.close();
//				}
//			}catch(java.sql.SQLException e){
//				e.printStackTrace();
//			}
//			this.diachincc = diachi;
//		}
	
		return this.diachincc;
	}

	
	public void setdiachincc(String diachincc) {
		
		this.diachincc = diachincc;
	}

	
	public ResultSet getTtDuyetRs() {
		
		return this.rsTtDuyet = db.get(  " SELECT C.TEN CAP, B.TEN TENNV, CASE A.TRANGTHAI WHEN 0 THEN N'Chưa duyệt' WHEN 1 THEN N'Đã duyệt' WHEN 2 THEN N'Đã xóa' END TRANGTHAI, \n"+
										 " ISNULL(A.LYDOMODUYET, '') MODUYET, ISNULL(A.LYDOSUA, '') LYDOSUA, ISNULL(A.LYDOXOA, '') LYDOXOA \n"+
										 " FROM ERP_DUYETTAMUNG A INNER JOIN NHANVIEN B ON A.NHANVIEN_FK = B.PK_SEQ \n"+
										 " INNER JOIN ERP_LOAICAPQUANLY C ON C.PK_SEQ = A.LOAICAP_FK \n"+
										 " WHERE A.TAMUNG_FK = "+this.Id +
										 " ORDER BY LOAICAP_FK ASC ");
		
	}

	
	public void setTtDuyetRs(ResultSet TtDuyetRs) {
		
		this.rsTtDuyet = TtDuyetRs;
	}

	
	public String getduyettu() {
		
		return this.duyettu;
	}

	
	public void setduyettu(String duyettu) {
		
		this.duyettu = duyettu;
	}


}
