package geso.traphaco.erp.beans.huythutien.imp;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.huythutien.IErpHuythutien;
import geso.traphaco.center.db.sql.dbutils;

public class ErpHuythutien implements IErpHuythutien
{
	String congtyId;
	String userId;
	String id;
	String sochungtu;
	String sochungtugoc;
	String ttnccId;
	String hdId;
	String tieuhaoId;
	String qcId;
	String pnkId;
	String pnhId;
	String dmhId;
	String msg;
	String Ngayghinhan;
	String nppdangnhap;
	String dienGiaiCT;
	ResultSet sochungtuRequest;
	ResultSet sochungtuDetail;
	
	dbutils db;
	private Utility util;
	
	public ErpHuythutien()
	{
		this.id = "";
		this.sochungtu = "";
		this.ttnccId = "";
		this.hdId = "";
		this.tieuhaoId = "";
		this.qcId = "";
		this.pnkId = "";
		this.pnhId = "";
		this.dmhId = "";
		this.Ngayghinhan="";
		this.nppdangnhap = "";
		this.sochungtugoc = "";
		this.msg = "";
		this.dienGiaiCT = "";
		this.util=new Utility();
		this.db = new dbutils();
	}
	
	public ErpHuythutien(String id)
	{
		this.id = id;
		this.sochungtu = "";
		this.ttnccId = "";
		this.hdId = "";
		this.tieuhaoId = "";
		this.qcId = "";
		this.pnkId = "";
		this.pnhId = "";
		this.Ngayghinhan="";
		this.dmhId = "";
		this.msg = "";
		this.dienGiaiCT = "";
		this.nppdangnhap = "";
		this.sochungtugoc = "";
		this.util=new Utility();
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
	
	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getSoThanhtoan() 
	{
		return this.ttnccId;
	}

	public void setSoThanhtoan(String sothanhtoan) 
	{
		this.ttnccId = sothanhtoan;
	}

	public String getSoHoadon() 
	{
		return this.hdId;
	}

	public void setSoHoadon(String sohoadon) 
	{
		this.hdId = sohoadon;
	}

	public String getSophieunhapkho() 
	{
		return this.pnkId;
	}

	public void setSoPhieunhapkho(String sonhapkho)
	{
		this.pnkId = sonhapkho;
	}

	public String getSoPhieunhanhang() 
	{
		return this.pnhId;
	}

	public void setSoPhieunhanhang(String sonhanhang)
	{
		this.pnhId = sonhanhang;
	}

	public String getSoDonMuahang() 
	{
		return this.dmhId;
	}

	public void setSoDonmuahang(String somuahang) 
	{
		this.dmhId = somuahang;
	}
	
	public void createRs()
	{
		this.dmhId = "";
		this.pnhId = "";
		this.pnkId = "";
		this.hdId = "";
		this.ttnccId = "";
		
		if(this.sochungtugoc.length()>= 15 ){
			this.sochungtu = this.sochungtugoc.substring(9, this.sochungtugoc.length());
			
			String query =			
				" SELECT 	MACHUNGTU SOCHUNGTUGOC, a.NGAYCHUNGTU, case a.TRANGTHAI when 1 then N'Đã chốt' else N'Đã chốt' end as TRANGTHAI ,a.SOTIENTT, a.NGAYTAO, 1 STT \n"+
				" FROM 		ERP_THUTIEN a  \n"+
				" WHERE 	a.TRANGTHAI = 1 and a.PK_SEQ = '" +this.sochungtu+"' and a.congty_fk = "+this.congtyId+" \n "+
				"			and a.PK_SEQ NOT IN ( SELECT SOCHUNGTU FROM ERP_HUYCHUNGTUKETOAN WHERE TRANGTHAI IN ( 1 , 0) and LOAICHUNGTU = 1 and congty_fk = "+this.congtyId+" )"+
				" ORDER BY STT DESC \n"; 		
			
			System.out.println("1.Requset nhan hang la: " + query);
			
			this.sochungtuRequest = db.get(query);
		}		
	}
		
	private void createDMH() 
	{
		try
		{
			String requestNh = "";
			String requestTieuhao = "";
			String requestQC = "";
			String requestHDNCC = "";
			String requestTTHDNCC = "";
			
			requestNh = " select a.pk_seq, a.PREFIX + cast(a.pk_seq as varchar(20)) as SOCHUNGTU, a.NGAYNHAN as ngaychungtu, " +
						" case a.TRANGTHAI when 0 then N'Chưa chốt' when 1 then N'Đã chốt' else N'Hoàn tất' end as trangthai, " +
						" 	N'Nhận hàng' as LOAICHUNGTU, a.NGAYTAO, 1 as stt " +
						" from erp_nhanhang a inner join erp_donvithuchien b on a.donvithuchien_fk = b.pk_seq " +
						" where a.MUAHANG_FK = '" + this.dmhId + "' " +
						" and a.trangthai not in (3, 4) and a.congty_fk = '100005'";
			
			System.out.println("____Lay nhan hang: " + requestNh);
			
			requestQC = " select a.pk_seq, '200'+ cast(a.pk_seq as varchar(20)) as SOCHUNGTU, a.ngaykiem as ngaychungtu,  " +
						" case a.TRANGTHAI when 0 then N'Chờ kiểm' else N'Đã kiểm' end as trangthai,  " +
						"	N'Kiểm định chất lượng' as LOAICHUNGTU, a.NGAYTAO, 2 as stt  " +
						" from ERP_YeuCauKiemDinh a  " +
						" where a.nhanhang_fk in ( select pk_seq from ERP_NHANHANG where MUAHANG_FK = '" + this.dmhId + "' and trangthai in ( 0, 1 ) )  and a.trangthai in (0, 1)  ";
			
			System.out.println("____Lay QC: " + requestQC);

	 
			
			requestHDNCC =  " select a.pk_seq, a.PREFIX + cast(a.pk_seq as varchar(20)) as sochungtu, a.ngayghinhan as ngaychungtu,  " +
							"case a.trangthai when 0 then N'Chưa duyệt' when 1 then N'Đã duyệt' when 2 then N'Hoàn tất' else N'NA' end as trangthai, " +
							"	N'PARK Hóa đơn' as loaichungtu, a.ngaytao, 4 as stt   " +
							"from ERP_PARK a inner join ERP_HOADONNCC b on a.pk_seq = b.park_fk  " +
							"inner join ERP_HOADONNCC_PHIEUNHAP c on b.pk_seq = c.hoadonncc_fk  " +
							"where c.muahang_fk = '" + this.dmhId + "' and a.TRANGTHAI != 3 ";
		
			System.out.println("___Lay hoa don NCC: " + requestHDNCC);
			
			requestTTHDNCC =  " select a.pk_seq, a.PREFIX + cast(a.pk_seq as varchar(20)) as sochungtu, a.ngayghinhan as ngaychungtu,  " +
							  "case a.trangthai when 0 then N'Chưa duyệt' when 1 then N'Đã duyệt' else N'NA' end as trangthai, " +
							  "	N'Thanh toán Hóa đơn' as loaichungtu, a.ngaytao, 5 as stt   " +
							  "from ERP_THANHTOANHOADON a inner join ERP_THANHTOANHOADON_HOADON b on a.PK_SEQ = b.THANHTOANHD_FK  " +
							  	"inner join ERP_HOADONNCC c on b.HOADON_FK = c.pk_seq  " +
							  	"inner join ERP_HOADONNCC_PHIEUNHAP d on c.pk_seq = d.hoadonncc_fk   " +
							  "where d.muahang_fk = '" + this.dmhId + "' and a.TRANGTHAI not in ( 2, 3 ) ";
			
			System.out.println("___Lay thanh toan HoaDon: " + requestHDNCC);
				
			
			String requestMh = 	" select a.pk_seq, a.PREFIX + cast(a.pk_seq as varchar(20)) as SOCHUNGTU, a.NGAYMUA as ngaychungtu, " +
								" case a.TRANGTHAI when 1 then N'Đã chốt' else N'Hoàn tất' end as trangthai, N'Mua hàng' as LOAICHUNGTU, a.NGAYTAO, 0 as stt " +
								" from erp_muahang a inner join erp_donvithuchien b on a.donvithuchien_fk = b.pk_seq " +
								" where a.congty_fk = '100005' and a.trangthai in (1, 2) and a.PK_SEQ = '" + this.dmhId + "' ";
			
			if(requestTTHDNCC.length() > 0)
				requestMh += " union all " + requestTTHDNCC;
			
			if(requestHDNCC.length() > 0)
				requestMh += " union all " + requestHDNCC;
			
		 
			if(requestQC.length() > 0)
				requestMh += " union all " + requestQC;
			
			if(requestNh.length() > 0)
				requestMh += " union all " + requestNh;
			
			requestMh += " order by stt desc ";
			
			System.out.println("1.Requset la: " + requestMh);
			
			this.sochungtuRequest = db.get(requestMh);
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			System.out.println("Exception : " + e.getMessage());
		}
	}

	public void init() 
	{		
		String query =
		"SELECT a.PK_SEQ, a.SOCHUNGTU, a.NGAYGHINHAN, b.TRANGTHAI, b.SOTIEN SOTIENTT, b.NGAYCHUNGTU, b.NGAYTAO, a.SOCHUNGTUGOC ,1 STT,ISNULL(a.DIENGIAI,'') AS DIENGIAI " +
		"FROM 	ERP_HUYCHUNGTUKETOAN a INNER JOIN ERP_HUYCHUNGTUKETOAN_CHUNGTU b on a.PK_SEQ = b.HCTKT_FK " +
		"WHERE 	a.PK_SEQ ='"+this.id+"' ";
		
		System.out.println(query);
		
		this.sochungtuRequest = db.get(query);
		
		ResultSet rs = db.get(query);
		try
		{
			while(rs.next())
			{
				this.Ngayghinhan = rs.getString("NGAYGHINHAN");
				this.sochungtu = rs.getString("SOCHUNGTU");
				this.sochungtugoc = rs.getString("SOCHUNGTUGOC");
				this.dienGiaiCT = rs.getString("DIENGIAI");
			}
			rs.close();			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public boolean createHct(String[] socthuy, String[] soct, String[] ngaychungtu, String[] trangthai,String[] loaichungtu, String[] ngaytao, String[] thutu, String[] sotientt)
	{
		if( this.Ngayghinhan.trim().length() <= 0 )
		{
			this.msg = "Bạn phải chọn ngày ghi nhận.";
			return false;
		}
		
		if( socthuy == null)
		{
			this.msg = "Chưa có chứng từ hủy";
			return false;
		}
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			//LOAICHUNGTU = 1; HỦY PHIẾU THU TIỀN
			//LOAICHUNGTU = 2; HỦY PHIẾU CHI
			
			
			
			String prefix = this.sochungtugoc.substring(9, this.sochungtugoc.length());						
			
			String query = "INSERT ERP_HUYCHUNGTUKETOAN (SOCHUNGTU, TRANGTHAI, NGAYTAO, NGUOITAO, NGAYSUA, NGUOISUA, LOAICHUNGTU, CONGTY_FK, NGAYGHINHAN, SOCHUNGTUGOC,DIENGIAI) \n " +
						   "VALUES ('"+prefix+"',0,'"+this.getDateTime()+"',"+this.userId+",'"+this.getDateTime()+"',"+this.userId+",1,"+this.congtyId+",'"+this.Ngayghinhan+"', '"+this.sochungtugoc+"',N'"+this.dienGiaiCT+"')";
			
			System.out.println("INSERT ERP_HUYCHUNGTUKETOAN: " + query);
			
			if(!db.update(query))
			{
				db.getConnection().rollback();
				this.msg = "Khong the tao moi ERP_HUYCHUNGTUKETOAN, " + query;
			 
				return false;
			}
			
			String hctbhCurrent = "";
			query = "select IDENT_CURRENT('ERP_HUYCHUNGTUKETOAN') as hctbhId";
			
			ResultSet rsTthd = db.get(query);						
			if(rsTthd.next())
			{
				hctbhCurrent = rsTthd.getString("hctbhId");
				rsTthd.close();
			}
			
			this.id = hctbhCurrent;
			
			if(socthuy != null)
			{
				for(int i = 0; i < socthuy.length; i++)
				{
					query = "insert ERP_HUYCHUNGTUKETOAN_CHUNGTU (HCTKT_FK, SOCHUNGTU, NGAYCHUNGTU, TRANGTHAI, LOAICHUNGTU, NGAYTAO, SOTIEN) " +
							"values('" + hctbhCurrent + "',  '" + prefix+ "', '" + ngaychungtu[i] + "', N'" + trangthai[i] + "', N'Thu tiền', '" + ngaytao[i] + "',"+sotientt[i].replaceAll(",", "")+")";
					
					System.out.println("INSERT ERP_HUYCHUNGTUKETOAN_CHUNGTU : " + query);
					
					if(!db.update(query))
					{
						db.getConnection().rollback();
						this.msg = "Khong the tao moi ERP_HUYCHUNGTUKETOAN_CHUNGTU, " + query;
						 
						return false;
					}
				}
			}			
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
			return true;
		}
		catch (Exception e)
		{
			System.out.println("____LOI KHI HUY CHUNG TU THU TIEN: " + e.getMessage());
			try 
			{
				db.getConnection().rollback();
			} 
			catch (SQLException e1) {}
			return false;
		}
	}

	private boolean Revert_KeToan(String sochungtu)
	{	
		
		try{
		String query =
		"SELECT ISNULL(NHOMKHTT_FK,0) NHOMKHTT_ID, NGAYGHISO FROM ERP_THUTIEN WHERE PK_SEQ ='"+sochungtu+"' \n ";
		
		System.out.println(query);
		
		ResultSet NHOMKHTT= db.get(query);
		int nhomkhttId = 0;
		
		String ngayghiso = "";
		String loaict="";
		
		String colNAME = "";
		String tableNAME = "";
		
			if(NHOMKHTT!=null)
			{
				while(NHOMKHTT.next())
				{
					nhomkhttId = Integer.parseInt(NHOMKHTT.getString("NHOMKHTT_ID"));
					ngayghiso = NHOMKHTT.getString("NGAYGHISO");
				}
				NHOMKHTT.close();
			}
		
		
		if(nhomkhttId > 0)  // THU TIỀN THEO NHÓM KH
		  {
			loaict = "Thu tiền hóa đơn";
			colNAME = "ngaychungtu";;
			tableNAME = "ERP_THUTIEN";
			
			query = " SELECT " + colNAME + " as ngaynghiepvu from " + tableNAME + " where pk_seq = '" + sochungtu + "' ";
			System.out.println("____LAY NGAY NGHIEP VU: " + query);
			
			ResultSet ngayRS = db.get(query);
			String ngaynghiepvu = "";
			
			if(ngayRS.next())
			{
				ngaynghiepvu = ngayRS.getString("ngaynghiepvu");
			}
			ngayRS.close();
			
			String nam = ngaynghiepvu.substring(0, 4);
			String thang = ngaynghiepvu.substring(5, 7);
						
			//CHECK NGAY GHI NHAN REVERT CO HOP LE HAY KHONG (CHI DUOC THUC HIEN TRONG THANG KHOA SO CONG 1)
			query = "select THANGKS, NAM from ERP_KHOASOKETOAN order by NAM desc, THANGKS desc";
			System.out.println("1.CHECK NO-CO: " + query);
			
			String thangKS = "1";
			String namKS = "2013";
			ResultSet rsCheck = db.get(query);
			if(rsCheck != null)
			{
				try 
				{
					if(rsCheck.next())
					{
						thangKS = rsCheck.getString("THANGKS");
						namKS = rsCheck.getString("NAM");
					}
					rsCheck.close();
				} 
				catch (Exception e) {}
			}
			
			String thangHopLe = "";
			String namHopLe = "";
			if(Integer.parseInt(thangKS) == 12 )
			{
				thangHopLe =  "1";
				namHopLe = Integer.toString( Integer.parseInt(namKS)  + 1);
			}
			else
			{
				thangHopLe =  Integer.toString(Integer.parseInt(thangKS) + 1);
				namHopLe = namKS;
			}
			
			//GHI NHAN NGUOC LAI TAI KHOAN NO - CO
			query = "select SOCHUNGTU, TAIKHOAN_FK, TAIKHOANDOIUNG_FK, NO, CO, TIENTEGOC_FK, TONGGIATRINT  " +
				    "from ERP_PHATSINHKETOAN " +
				    "where LOAICHUNGTU = N'"+loaict+"' and SOCHUNGTU = '" + sochungtu + "' ";
			
			System.out.println("1.CHECK NO-CO: " + query);
			
			
			ResultSet rsPSKT = db.get(query);
			try 
			{
				while(rsPSKT.next())
				{
					String taikhoan_fk = rsPSKT.getString("TAIKHOAN_FK");
					String tiente_fk = rsPSKT.getString("TIENTEGOC_FK");
					double NO = rsPSKT.getDouble("NO");
					double CO = rsPSKT.getDouble("CO");
					double TONGGIATRINT = rsPSKT.getDouble("TONGGIATRINT");
					
					//NEU LA CO THI BAY GIO GHI GIAM CO LAI
					if( NO > 0 )
					{
						query = " update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND - " + NO + ", GIATRINONGUYENTE = GIATRINONGUYENTE - " + TONGGIATRINT + "  " +
								" where TAIKHOANKT_FK = '" + taikhoan_fk + "' and THANG = '" + Integer.parseInt(thang) + "' and NAM = '" + Integer.parseInt(nam) + "' and NGUYENTE_FK = '" + tiente_fk + "' ";
					}
					else
					{
						query = " update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND - " + CO + ", GIATRICONGUYENTE = GIATRICONGUYENTE - " + TONGGIATRINT + "  " +
								" where TAIKHOANKT_FK = '" + taikhoan_fk + "' and THANG = '" + Integer.parseInt(thang) + "' and NAM = '" + Integer.parseInt(nam) + "' and NGUYENTE_FK = '" + tiente_fk + "' ";
					}
					
					System.out.println("____LAY NGAY NGHIEP VU: " + query);
					
					if(!db.update(query))
					{
						this.msg = "1.Lỗi REVERT: " + query;
						return false;
					}
					
				}
				rsPSKT.close();
			} 
			catch (Exception e) { }
			
			//GHI NHAN LOG NGUOC LAI
			query = " INSERT ERP_PHATSINHKETOAN( NGAYCHUNGTU, NGAYGHINHAN, LOAICHUNGTU, SOCHUNGTU, TAIKHOAN_FK, TAIKHOANDOIUNG_FK, NOIDUNGNHAPXUAT_FK, NO, CO, DOITUONG, MADOITUONG, LOAIDOITUONG, " +
					"							 SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC )  " +
					" 							 SELECT NGAYCHUNGTU, '" + ngaynghiepvu + "', N'REVERT_' + LOAICHUNGTU as LOAICHUNGTU, SOCHUNGTU, TAIKHOAN_FK, TAIKHOANDOIUNG_FK, NOIDUNGNHAPXUAT_FK, CO*(-1), NO*(-1), DOITUONG, MADOITUONG, LOAIDOITUONG,  " +
					"							 SOLUONG, (-1)*isnull(DONGIA,0) DONGIA, TIENTEGOC_FK, isnull(DONGIANT,0)*(-1), TIGIA_FK, ISNULL(TONGGIATRI,0)*(-1), ISNULL(TONGGIATRINT,0)*(-1), KHOANMUC  " +
					" 							 FROM ERP_PHATSINHKETOAN  " +
					" 							 WHERE LOAICHUNGTU = N'"+loaict+"' and SOCHUNGTU = '" + sochungtu + "' ";
			
			System.out.println("____LAY NGAY NGHIEP VU: " + query);
			
			if(!db.update(query))
			{
				this.msg = "2.Lỗi REVERT: " + query;
				return false;
			}
			
		  }
			else{
				query = "	select tt.noidungtt_fk  from erp_thutien tt " +			
						"	where tt.pk_seq = '" + sochungtu + "'";
				System.out.println("___Check but toan: " + query);
		
			ResultSet psktRs = db.get(query);
			if(psktRs != null)
			{
				while(psktRs.next())
				{
					
				String noidungthutien = psktRs.getString("noidungtt_fk");
					
					
				if( noidungthutien.equals("100000")|| noidungthutien.equals("100001")) // THU TIEN HÓA ĐƠN && KHACH HANG TRA TRUOC
				{
					if(psktRs.getString("noidungtt_fk").equals("100000")) 
						loaict="Thu tiền theo hóa đơn";
					if(psktRs.getString("noidungtt_fk").equals("100001")) 
						loaict="Thu tiền KH trả trước";
					
					
					
					 colNAME = "ngaychungtu";;
					tableNAME = "ERP_THUTIEN";
					
					query = " SELECT " + colNAME + " as ngaynghiepvu from " + tableNAME + " where pk_seq = '" + sochungtu + "' ";
					System.out.println("____LAY NGAY NGHIEP VU: " + query);
					
					ResultSet ngayRS = db.get(query);
					String ngaynghiepvu = "";
					
					if(ngayRS.next())
					{
						ngaynghiepvu = ngayRS.getString("ngaynghiepvu");
					}
					ngayRS.close();
					
					String nam = ngaynghiepvu.substring(0, 4);
					String thang = ngaynghiepvu.substring(5, 7);
								
					//CHECK NGAY GHI NHAN REVERT CO HOP LE HAY KHONG (CHI DUOC THUC HIEN TRONG THANG KHOA SO CONG 1)
					query = "select THANGKS, NAM from ERP_KHOASOKETOAN order by NAM desc, THANGKS desc";
					System.out.println("1.CHECK NO-CO: " + query);
					
					String thangKS = "1";
					String namKS = "2013";
					ResultSet rsCheck = db.get(query);
					if(rsCheck != null)
					{
						try 
						{
							if(rsCheck.next())
							{
								thangKS = rsCheck.getString("THANGKS");
								namKS = rsCheck.getString("NAM");
							}
							rsCheck.close();
						} 
						catch (Exception e) {}
					}
					
					String thangHopLe = "";
					String namHopLe = "";
					if(Integer.parseInt(thangKS) == 12 )
					{
						thangHopLe =  "1";
						namHopLe = Integer.toString( Integer.parseInt(namKS)  + 1);
					}
					else
					{
						thangHopLe =  Integer.toString(Integer.parseInt(thangKS) + 1);
						namHopLe = namKS;
					}
					
					//GHI NHAN NGUOC LAI TAI KHOAN NO - CO
					query = "select SOCHUNGTU, TAIKHOAN_FK, TAIKHOANDOIUNG_FK, NO, CO, TIENTEGOC_FK, TONGGIATRINT  " +
						    "from ERP_PHATSINHKETOAN " +
						    "where LOAICHUNGTU = N'"+loaict+"' and SOCHUNGTU = '" + sochungtu + "' ";
					
					System.out.println("1.CHECK NO-CO: " + query);
					
					
					ResultSet rsPSKT = db.get(query);
					try 
					{
						while(rsPSKT.next())
						{
							String taikhoan_fk = rsPSKT.getString("TAIKHOAN_FK");
							String tiente_fk = rsPSKT.getString("TIENTEGOC_FK");
							double NO = rsPSKT.getDouble("NO");
							double CO = rsPSKT.getDouble("CO");
							double TONGGIATRINT = rsPSKT.getDouble("TONGGIATRINT");
							
							//NEU LA CO THI BAY GIO GHI GIAM CO LAI
							if( NO > 0 )
							{
								query = " update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND - " + NO + ", GIATRINONGUYENTE = GIATRINONGUYENTE - " + TONGGIATRINT + "  " +
										" where TAIKHOANKT_FK = '" + taikhoan_fk + "' and THANG = '" + Integer.parseInt(thang) + "' and NAM = '" + Integer.parseInt(nam) + "' and NGUYENTE_FK = '" + tiente_fk + "' ";
							}
							else
							{
								query = " update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND - " + CO + ", GIATRICONGUYENTE = GIATRICONGUYENTE - " + TONGGIATRINT + "  " +
										" where TAIKHOANKT_FK = '" + taikhoan_fk + "' and THANG = '" + Integer.parseInt(thang) + "' and NAM = '" + Integer.parseInt(nam) + "' and NGUYENTE_FK = '" + tiente_fk + "' ";
							}
							
							System.out.println("____LAY NGAY NGHIEP VU: " + query);
							
							if(!db.update(query))
							{
								this.msg = "1.Lỗi REVERT: " + query;
								return false;
							}
							
						}
						rsPSKT.close();
					} 
					catch (Exception e) { }
					
					//GHI NHAN LOG NGUOC LAI
					query = " INSERT ERP_PHATSINHKETOAN( NGAYCHUNGTU, NGAYGHINHAN, LOAICHUNGTU, SOCHUNGTU, TAIKHOAN_FK, TAIKHOANDOIUNG_FK, NOIDUNGNHAPXUAT_FK, NO, CO, DOITUONG, MADOITUONG, LOAIDOITUONG, " +
							"							 SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC )  " +
							" 							 SELECT NGAYCHUNGTU, '" + ngaynghiepvu + "', N'REVERT_' + LOAICHUNGTU as LOAICHUNGTU, SOCHUNGTU, TAIKHOAN_FK, TAIKHOANDOIUNG_FK, NOIDUNGNHAPXUAT_FK, CO*(-1), NO*(-1), DOITUONG, MADOITUONG, LOAIDOITUONG,  " +
							"							 SOLUONG, (-1)*isnull(DONGIA,0) DONGIA, TIENTEGOC_FK, isnull(DONGIANT,0)*(-1), TIGIA_FK, ISNULL(TONGGIATRI,0)*(-1), ISNULL(TONGGIATRINT,0)*(-1), KHOANMUC  " +
							" 							 FROM ERP_PHATSINHKETOAN  " +
							" 							 WHERE LOAICHUNGTU = N'"+loaict+"' and SOCHUNGTU = '" + sochungtu + "' ";
					
					System.out.println("____LAY NGAY NGHIEP VU: " + query);
					
					if(!db.update(query))
					{
						this.msg = "2.Lỗi REVERT: " + query;
						return false;
					}
					
					
			   }
				else if (noidungthutien.equals("100002"))// THU KHÁC
				{ 	
					loaict="Thu khác";
					
					colNAME = "ngaychungtu";;
					tableNAME = "ERP_THUTIEN";
					
					query = " SELECT " + colNAME + " as ngaynghiepvu from " + tableNAME + " where pk_seq = '" + sochungtu + "' ";
					System.out.println("____LAY NGAY NGHIEP VU: " + query);
					
					ResultSet ngayRS = db.get(query);
					String ngaynghiepvu = "";
					
					if(ngayRS.next())
					{
						ngaynghiepvu = ngayRS.getString("ngaynghiepvu");
					}
					ngayRS.close();
					
					String nam = ngaynghiepvu.substring(0, 4);
					String thang = ngaynghiepvu.substring(5, 7);
								
					//CHECK NGAY GHI NHAN REVERT CO HOP LE HAY KHONG (CHI DUOC THUC HIEN TRONG THANG KHOA SO CONG 1)
					query = "select THANGKS, NAM from ERP_KHOASOKETOAN order by NAM desc, THANGKS desc";
					System.out.println("1.CHECK NO-CO: " + query);
					
					String thangKS = "1";
					String namKS = "2013";
					ResultSet rsCheck = db.get(query);
					if(rsCheck != null)
					{
						try 
						{
							if(rsCheck.next())
							{
								thangKS = rsCheck.getString("THANGKS");
								namKS = rsCheck.getString("NAM");
							}
							rsCheck.close();
						} 
						catch (Exception e) {}
					}
					
					String thangHopLe = "";
					String namHopLe = "";
					if(Integer.parseInt(thangKS) == 12 )
					{
						thangHopLe =  "1";
						namHopLe = Integer.toString( Integer.parseInt(namKS)  + 1);
					}
					else
					{
						thangHopLe =  Integer.toString(Integer.parseInt(thangKS) + 1);
						namHopLe = namKS;
					}
					
					//GHI NHAN NGUOC LAI TAI KHOAN NO - CO
					query = "select SOCHUNGTU, TAIKHOAN_FK, TAIKHOANDOIUNG_FK, NO, CO, TIENTEGOC_FK, TONGGIATRINT  " +
						    "from ERP_PHATSINHKETOAN " +
						    "where LOAICHUNGTU = N'"+loaict+"' and SOCHUNGTU = '" + sochungtu + "' ";
					
					System.out.println("1.CHECK NO-CO: " + query);
					
					
					ResultSet rsPSKT = db.get(query);
					try 
					{
						while(rsPSKT.next())
						{
							String taikhoan_fk = rsPSKT.getString("TAIKHOAN_FK");
							String tiente_fk = rsPSKT.getString("TIENTEGOC_FK");
							double NO = rsPSKT.getDouble("NO");
							double CO = rsPSKT.getDouble("CO");
							double TONGGIATRINT = rsPSKT.getDouble("TONGGIATRINT");
							
							//NEU LA CO THI BAY GIO GHI GIAM CO LAI
							if( NO > 0 )
							{
								query = " update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND - " + NO + ", GIATRINONGUYENTE = GIATRINONGUYENTE - " + TONGGIATRINT + "  " +
										" where TAIKHOANKT_FK = '" + taikhoan_fk + "' and THANG = '" + Integer.parseInt(thang) + "' and NAM = '" + Integer.parseInt(nam) + "' and NGUYENTE_FK = '" + tiente_fk + "' ";
							}
							else
							{
								query = " update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND - " + CO + ", GIATRICONGUYENTE = GIATRICONGUYENTE - " + TONGGIATRINT + "  " +
										" where TAIKHOANKT_FK = '" + taikhoan_fk + "' and THANG = '" + Integer.parseInt(thang) + "' and NAM = '" + Integer.parseInt(nam) + "' and NGUYENTE_FK = '" + tiente_fk + "' ";
							}
							
							System.out.println("____LAY NGAY NGHIEP VU: " + query);
							
							if(!db.update(query))
							{
								this.msg = "1.Lỗi REVERT: " + query;
								return false;
							}
							
						}
						rsPSKT.close();
					} 
					catch (Exception e) { }
					
					//GHI NHAN LOG NGUOC LAI
					query = " INSERT ERP_PHATSINHKETOAN( NGAYCHUNGTU, NGAYGHINHAN, LOAICHUNGTU, SOCHUNGTU, TAIKHOAN_FK, TAIKHOANDOIUNG_FK, NOIDUNGNHAPXUAT_FK, NO, CO, DOITUONG, MADOITUONG, LOAIDOITUONG, " +
							"							 SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC )  " +
							" 							 SELECT NGAYCHUNGTU, '" + ngaynghiepvu + "', N'REVERT_' + LOAICHUNGTU as LOAICHUNGTU, SOCHUNGTU, TAIKHOAN_FK, TAIKHOANDOIUNG_FK, NOIDUNGNHAPXUAT_FK, CO*(-1), NO*(-1), DOITUONG, MADOITUONG, LOAIDOITUONG,  " +
							"							 SOLUONG, (-1)*isnull(DONGIA,0) DONGIA, TIENTEGOC_FK, isnull(DONGIANT,0)*(-1), TIGIA_FK, ISNULL(TONGGIATRI,0)*(-1), ISNULL(TONGGIATRINT,0)*(-1), KHOANMUC  " +
							" 							 FROM ERP_PHATSINHKETOAN  " +
							" 							 WHERE LOAICHUNGTU = N'"+loaict+"' and SOCHUNGTU = '" + sochungtu + "' ";
					
					System.out.println("____LAY NGAY NGHIEP VU: " + query);
					
					if(!db.update(query))
					{
						this.msg = "2.Lỗi REVERT: " + query;
						return false;
					}
								
				}
				else if (noidungthutien.equals("100003"))// THU HOI TAM UNG
				{
				 	
					loaict="Thu tiền_Thu hồi tạm ứng";
					
				
					colNAME = "ngaychungtu";
					tableNAME = "ERP_THUTIEN";
					
					query = " SELECT " + colNAME + " as ngaynghiepvu from " + tableNAME + " where pk_seq = '" + sochungtu + "' ";
					System.out.println("____LAY NGAY NGHIEP VU: " + query);
					
					ResultSet ngayRS = db.get(query);
					String ngaynghiepvu = "";
					
					if(ngayRS.next())
					{
						ngaynghiepvu = ngayRS.getString("ngaynghiepvu");
					}
					ngayRS.close();
					
					String nam = ngaynghiepvu.substring(0, 4);
					String thang = ngaynghiepvu.substring(5, 7);
								
					//CHECK NGAY GHI NHAN REVERT CO HOP LE HAY KHONG (CHI DUOC THUC HIEN TRONG THANG KHOA SO CONG 1)
					query = "select THANGKS, NAM from ERP_KHOASOKETOAN order by NAM desc, THANGKS desc";
					System.out.println("1.CHECK NO-CO: " + query);
					
					String thangKS = "1";
					String namKS = "2013";
					ResultSet rsCheck = db.get(query);
					if(rsCheck != null)
					{
						try 
						{
							if(rsCheck.next())
							{
								thangKS = rsCheck.getString("THANGKS");
								namKS = rsCheck.getString("NAM");
							}
							rsCheck.close();
						} 
						catch (Exception e) {}
					}
					
					String thangHopLe = "";
					String namHopLe = "";
					if(Integer.parseInt(thangKS) == 12 )
					{
						thangHopLe =  "1";
						namHopLe = Integer.toString( Integer.parseInt(namKS)  + 1);
					}
					else
					{
						thangHopLe =  Integer.toString(Integer.parseInt(thangKS) + 1);
						namHopLe = namKS;
					}
					
					//GHI NHAN NGUOC LAI TAI KHOAN NO - CO
					query = "select SOCHUNGTU, TAIKHOAN_FK, TAIKHOANDOIUNG_FK, NO, CO, TIENTEGOC_FK, TONGGIATRINT  " +
						    "from ERP_PHATSINHKETOAN " +
						    "where LOAICHUNGTU = N'"+loaict+"' and SOCHUNGTU = '" + sochungtu + "' ";
					
					System.out.println("1.CHECK NO-CO: " + query);
					
					
					ResultSet rsPSKT = db.get(query);
					try 
					{
						while(rsPSKT.next())
						{
							String taikhoan_fk = rsPSKT.getString("TAIKHOAN_FK");
							String tiente_fk = rsPSKT.getString("TIENTEGOC_FK");
							double NO = rsPSKT.getDouble("NO");
							double CO = rsPSKT.getDouble("CO");
							double TONGGIATRINT = rsPSKT.getDouble("TONGGIATRINT");
							
							//NEU LA CO THI BAY GIO GHI GIAM CO LAI
							if( NO > 0 )
							{
								query = " update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND - " + NO + ", GIATRINONGUYENTE = GIATRINONGUYENTE - " + TONGGIATRINT + "  " +
										" where TAIKHOANKT_FK = '" + taikhoan_fk + "' and THANG = '" + Integer.parseInt(thang) + "' and NAM = '" + Integer.parseInt(nam) + "' and NGUYENTE_FK = '" + tiente_fk + "' ";
							}
							else
							{
								query = " update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND - " + CO + ", GIATRICONGUYENTE = GIATRICONGUYENTE - " + TONGGIATRINT + "  " +
										" where TAIKHOANKT_FK = '" + taikhoan_fk + "' and THANG = '" + Integer.parseInt(thang) + "' and NAM = '" + Integer.parseInt(nam) + "' and NGUYENTE_FK = '" + tiente_fk + "' ";
							}
							
							System.out.println("____LAY NGAY NGHIEP VU: " + query);
							
							if(!db.update(query))
							{
								this.msg = "1.Lỗi REVERT: " + query;
								return false;
							}
							
						}
						rsPSKT.close();
					} 
					catch (Exception e) { }
					
					//GHI NHAN LOG NGUOC LAI
					query = " INSERT ERP_PHATSINHKETOAN( NGAYCHUNGTU, NGAYGHINHAN, LOAICHUNGTU, SOCHUNGTU, TAIKHOAN_FK, TAIKHOANDOIUNG_FK, NOIDUNGNHAPXUAT_FK, NO, CO, DOITUONG, MADOITUONG, LOAIDOITUONG, " +
							"							 SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC )  " +
							" 							 SELECT NGAYCHUNGTU, '" + ngaynghiepvu + "', N'REVERT_' + LOAICHUNGTU as LOAICHUNGTU, SOCHUNGTU, TAIKHOAN_FK, TAIKHOANDOIUNG_FK, NOIDUNGNHAPXUAT_FK, CO*(-1), NO*(-1), DOITUONG, MADOITUONG, LOAIDOITUONG,  " +
							"							 SOLUONG, (-1)*isnull(DONGIA,0) DONGIA, TIENTEGOC_FK, isnull(DONGIANT,0)*(-1), TIGIA_FK, ISNULL(TONGGIATRI,0)*(-1), ISNULL(TONGGIATRINT,0)*(-1), KHOANMUC  " +
							" 							 FROM ERP_PHATSINHKETOAN  " +
							" 							 WHERE LOAICHUNGTU = N'"+loaict+"' and SOCHUNGTU = '" + sochungtu + "' ";
					
					System.out.println("____LAY NGAY NGHIEP VU: " + query);
					
					if(!db.update(query))
					{
						this.msg = "2.Lỗi REVERT: " + query;
						return false;
					}
					
				}
			  }
				psktRs.close();
			}
		
	  }
			
		}
		catch(Exception e )
		{
			e.printStackTrace();
		}

		return true;
	}
	
	
	public boolean updateHct(String[] socthuy, String[] soct, String[] ngaychungtu, String[] trangthai,String[] loaichungtu, String[] ngaytao, String[] thutu, String[] sotientt)
	{
		if( this.Ngayghinhan.trim().length() <= 0 )
		{
			this.msg = "Bạn phải chọn ngày ghi nhận.";
			return false;
		}
		
		if( socthuy == null)
		{
			this.msg = "Chưa có chứng từ hủy";
			return false;
		}
		
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			//LOAICHUNGTU = 1; HỦY PHIẾU THU TIỀN
			//LOAICHUNGTU = 2; HỦY PHIẾU CHI
			
			String prefix = this.sochungtugoc.substring(9, this.sochungtugoc.length());
			
			String query = "	UPDATE ERP_HUYCHUNGTUKETOAN " +
						   "	SET SOCHUNGTU = '"+prefix+"', NGUOISUA ='"+this.userId+"', NGAYGHINHAN = '"+this.Ngayghinhan+"', SOCHUNGTUGOC = '" +this.sochungtugoc+"',"+
						   "	DIENGIAI = N'"+this.dienGiaiCT+"'"+
						   " 	WHERE PK_SEQ ='"+this.id+"' and trangthai=0 ";
			
			System.out.println("INSERT ERP_HUYCHUNGTUKETOAN: " + query);
			
			if(db.updateReturnInt(query)!=1)
			{
				db.getConnection().rollback();
				this.msg = "Khong the tao moi ERP_HUYCHUNGTUKETOAN, " + query;
			 
				return false;
			}
			
			query = "DELETE ERP_HUYCHUNGTUKETOAN_CHUNGTU  WHERE HCTKT_FK = '"+this.id+"'";

			System.out.println("UPDATE ERP_HUYCHUNGTUKETOAN: " + query);

			if(!db.update(query))
			{
				db.getConnection().rollback();
				this.msg = "Khong the DELETE ERP_HUYCHUNGTUKETOAN_CHUNGTU, " + query;
			
				return false;
			}
			
			
			if(socthuy != null)
			{
				for(int i = 0; i < socthuy.length; i++)
				{
					query = "insert ERP_HUYCHUNGTUKETOAN_CHUNGTU (HCTKT_FK, SOCHUNGTU, NGAYCHUNGTU, TRANGTHAI, LOAICHUNGTU, NGAYTAO, SOTIEN) " +
							"values('" + this.id + "',  '" + prefix + "', '" + ngaychungtu[i] + "', N'" + trangthai[i] + "', N'Thu tiền', '" + ngaytao[i] + "',"+sotientt[i].replaceAll(",", "")+")";
					
					System.out.println("INSERT ERP_HUYCHUNGTUKETOAN_CHUNGTU : " + query);
					
					if(!db.update(query))
					{
						db.getConnection().rollback();
						this.msg = "Khong the tao moi ERP_HUYCHUNGTUKETOAN_CHUNGTU, " + query;
						 
						return false;
					}
				}
			}
			
			
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
			return true;
		}
		catch (Exception e)
		{
			System.out.println("____LOI KHI HUY CHUNG TU THU TIEN: " + e.getMessage());
			try 
			{
				db.getConnection().rollback();
			} 
			catch (SQLException e1) {}
			return false;
		}
	}

	
	public String getMsg()
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	public String getSochungtu() 
	{
		return this.sochungtu;
	}

	public void setSochungtu(String sochungtu) 
	{
		this.sochungtu = sochungtu;
	}

	public ResultSet getSochungtuDetail() 
	{
		return this.sochungtuDetail;
	}

	public void setSochugntuDetail(ResultSet soctdetail) 
	{
		this.sochungtuDetail = soctdetail;
	}

	public ResultSet getSochungtuRequest() 
	{
		return this.sochungtuRequest;
	}

	public void setSochungtuRequest(ResultSet soctRequest) 
	{
		this.sochungtuRequest = soctRequest;
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

	
	public void DbClose() {
		
		try{
			this.db.shutDown();
		}catch (Exception e) {
			
		}
	}

	
	public String getNgayghinhan() {
		
		return this.Ngayghinhan;
	}

	
	public void setNgayghinhan(String Ngayghinhan) {
		
		this.Ngayghinhan=Ngayghinhan;
	}

	
	public String getSophieutieuhao() {
		
		return this.tieuhaoId;
	}

	
	public void setSoPhieutieuhao(String sotieuhao) {
		
		this.tieuhaoId = sotieuhao;
	}

	
	public String getSophieukiemdinh() {
		
		return this.qcId;
	}

	
	public void setSoPhieukiemdinh(String sokiemdinh) {
		
		this.qcId = sokiemdinh;
	}

	
	public String getnppdangnhap() {
		
		return this.nppdangnhap;
	}

	
	public void setnppdangnhap(String nppdangnhap) {
		
		this.nppdangnhap = nppdangnhap;
	}

	
	public String getSochungtugoc() {
	
		return this.sochungtugoc;
	}

	
	public void setSochungtugoc(String sochungtugoc) {
	
		this.sochungtugoc = sochungtugoc;
	}
	public String getDienGiaiCT(){
		return this.dienGiaiCT;
	}
	public void setDienGiaiCT(String dienGiaiCT){
		this.dienGiaiCT = dienGiaiCT;
	}
	

	
	
}
