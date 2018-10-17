package geso.traphaco.erp.beans.huychungtuketoan.imp;
import geso.traphaco.center.util.Utility;

import geso.traphaco.erp.beans.huychungtuketoan.IErpHuychungtuketoan;
import geso.traphaco.erp.db.sql.dbutils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ErpHuychungtuketoan implements IErpHuychungtuketoan
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
	String dienGiaiCT;
	
	ResultSet sochungtuRequest;
	ResultSet sochungtuDetail;
	
	dbutils db;
	private Utility util;
		
	String loaict;
	
	public ErpHuychungtuketoan()
	{
		this.id = "";
		this.sochungtu = "";
		this.sochungtugoc ="";
		this.ttnccId = "";
		this.hdId = "";
		this.tieuhaoId = "";
		this.qcId = "";
		this.pnkId = "";
		this.pnhId = "";
		this.dmhId = "";
		this.Ngayghinhan="";
		this.msg = "";
		this.loaict = "";
		this.dienGiaiCT = "";
		this.util=new Utility();
		this.db = new dbutils();
	}
	
	public ErpHuychungtuketoan(String id)
	{
		this.id = id;
		this.sochungtu = "";
		this.sochungtugoc ="";
		this.ttnccId = "";
		this.hdId = "";
		this.tieuhaoId = "";
		this.qcId = "";
		this.pnkId = "";
		this.pnhId = "";
		this.Ngayghinhan="";
		this.dmhId = "";
		this.msg = "";
		this.loaict = "";
		this.dienGiaiCT = "";
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
		
		String query = "";
		
			System.out.println("Chạy vào 1 ");
		
			if(this.loaict.equals("1")){	// HÓA ĐƠN NCC
				query = 
					//NHỮNG PHIẾU CHI CÓ LIÊN QUAN ĐẾN HÓA ĐƠN NCC
					" SELECT distinct   A.PK_SEQ as  SOCHUNGTU, a.NGAYCHUNGTU NGAYCHUNGTU, case a.TRANGTHAI WHEN 0 THEN N'CHƯA CHỐT' ELSE N'ĐÃ CHỐT' END AS TRANGTHAI, \n"+
	        		" a.TIENTE_FK,a.SOTIENNT,a.SOTIENVND, a.NGAYHOADON as NGAYTAO,N'Điều chuyển tiền' LOAICHUNGTU,1 STT \n"+
					" FROM 		ERP_DIEUCHUYENTIEN a \n"+
					" WHERE 	a.TRANGTHAI IN (0,1) AND A.SOCHUNGTU = '"+ this.sochungtu+ "'";
					
				
						
				
		
			
			System.out.println("1.Requset nhan hang la: " + query);
			
			this.sochungtuRequest = db.get(query);
		
		}
				
	}

	public void init() 
	{		
		String query =
		"SELECT a.PK_SEQ,a.SOCHUNGTU,a.SOCHUNGTUGOC, a.NGAYGHINHAN NGAYCHUNGTU, b.TRANGTHAI, b.SOTIEN SOTIENTT, b.NGAYCHUNGTU, b.NGAYTAO, b.LOAICHUNGTU,a.LOAICHUNGTU CHUNGTUHUY, 1 STT,ISNULL(DIENGIAI,'') AS DIENGIAI " +
		"FROM 	ERP_HUYCHUNGTUDIEUCHUYEN a INNER JOIN ERP_HUYCHUNGTUDIEUCHUYEN_CHUNGTU b on a.PK_SEQ = b.HCTKT_FK " +
		"WHERE 	a.PK_SEQ ='"+this.id+"'";
	
		this.sochungtuRequest = db.get(query);
		
		ResultSet rs = db.get(query);
		if (rs != null)
		{
			try
			{
				while(rs.next())
				{
					this.Ngayghinhan = rs.getString("NGAYCHUNGTU");
					this.sochungtu = rs.getString("SOCHUNGTU");
					this.sochungtugoc = rs.getString("SOCHUNGTUGOC");
					this.loaict = rs.getString("CHUNGTUHUY");
					this.dienGiaiCT = rs.getString("DIENGIAI");
				}
				rs.close();			
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}		
	}
	
	public boolean createHct(String[] socthuy, String[] soct, String[] ngaychungtu, String[] trangthai,String[] loaichungtu, String[] ngaytao, String[] thutu, String[] sotientt)
	{
		
		if( this.Ngayghinhan.trim().length() <= 0 )
		{
			this.msg = "Bạn phải chọn ngày ghi nhận.";
			return false;
		}
		
		if( this.sochungtu.trim().length() < 0)
		{
			this.msg = "Số chứng từ hủy không hợp lệ. Vui lòng kiểm tra lại.";
			return false;
		}
		

		if(this.loaict.trim().length() <=0){
			this.msg = "Yêu cầu chọn loại chứng từ";
			return false;
		}
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			//LOAICHUNGTU = 1; ĐIỀU CHUYỂN TIỀN
			if(this.loaict.equals("1"))
			{
				//ĐIỀU CHUYỂN TIỀN 
			
				 String query =  
						"SELECT pk_seq  FROM ERP_DIEUCHUYENTIEN where sochungtu = '"+this.sochungtu +"' ";
						System.out.println(query);
						
						String dctid = "";
						ResultSet rsid = db.get(query);
						if(rsid != null)
						{
							if(rsid.next())
							{
							
								dctid = rsid.getString("pk_seq") == null ? "" : rsid.getString("pk_seq");
							}
							rsid.close();
						}
				  query =  "SELECT TRANGTHAI FROM ERP_DIEUCHUYENTIEN where sochungtu = '"+this.sochungtu +"' ";
				System.out.println(query);
				
				String _trangthai = "";
				ResultSet rs = db.get(query);
				if(rs != null)
				{
					if(rs.next())
					{
					
						_trangthai = rs.getString("trangthai") == null ? "" : rs.getString("trangthai");
					}
					rs.close();
				}
				
				
				
				if( ! ( _trangthai.equals("0") || _trangthai.equals("1") )  || ( _trangthai.trim().length() <= 0 ) )
				{
					this.msg = "Trạng thái hóa đơn này không hợp lệ, bạn chỉ có thể hủy những hóa đơn đã duyệt / hoàn tất ";
					return false;
				}

			
			
			 query = "INSERT ERP_HUYCHUNGTUDIEUCHUYEN (SOCHUNGTU, TRANGTHAI, NGAYTAO, NGUOITAO, NGAYSUA, NGUOISUA, LOAICHUNGTU, CONGTY_FK, NGAYGHINHAN,DIENGIAI ) \n " +
						   "VALUES ('"+dctid+"',0,'"+this.getDateTime()+"',"+this.userId+",'"+this.getDateTime()+"',"+this.userId+",'"+this.loaict+"',"+this.congtyId+",'"+this.Ngayghinhan+"',N'"+this.dienGiaiCT+"')";
			
			System.out.println("INSERT ERP_HUYCHUNGTUKETOAN: " + query);
			
			if(!db.update(query))
			{
				db.getConnection().rollback();
				this.msg = "Khong the tao moi ERP_HUYCHUNGTUKETOAN, " + query;
			 
				return false;
			}
			
			String hctdcCurrent = "";
			query = "select IDENT_CURRENT('ERP_HUYCHUNGTUDIEUCHUYEN') as hctdcId";
			
			ResultSet rsTthd = db.get(query);
			if (rsTthd != null)
			{
				if(rsTthd.next())
				{
					hctdcCurrent = rsTthd.getString("hctdcId");
				}
				rsTthd.close();
			}
			
			this.id = hctdcCurrent;
			
			String loaictinsert = "";
			
			if(socthuy != null)
			{
				for(int i = 0; i < socthuy.length; i++)
				{
					
					if(loaict.equals("1")) loaictinsert = "Điều chuyển tiền"; 
					
//					if(loaict.equals("3")) loaictinsert = "PARK Hóa đơn";

					
					query = "insert ERP_HUYCHUNGTUDIEUCHUYEN_CHUNGTU (HCTKT_FK, SOCHUNGTU, NGAYCHUNGTU, TRANGTHAI, LOAICHUNGTU, NGAYTAO, SOTIEN) " +
							"values('" + hctdcCurrent + "',  '" + soct[i] + "', '" + ngaychungtu[i] + "', N'" + trangthai[i] + "', N'"+loaictinsert+"', '" + ngaytao[i] + "',"+sotientt[i].replaceAll(",", "")+")";
					
					System.out.println("INSERT ERP_HUYCHUNGTUDIEUCHUYEN_CHUNGTU : " + query);
					
					if(!db.update(query))
					{
						db.getConnection().rollback();
						this.msg = "Khong the tao moi ERP_HUYCHUNGTUDIEUCHUYEN_CHUNGTU, " + query;
						 
						return false;
					}
										
					/*if(this.loaict.equals("2")){ //CẬP NHẬT TRẠNG THÁI HỦY CHO PHIẾU CHI
						query ="UPDATE ERP_THANHTOANHOADON SET TRANGTHAI = 2 WHERE PK_SEQ ='"+soct[i]+"'";
						
						if(!db.update(query))
						{
							db.getConnection().rollback();
							this.msg = "Khong the huy phieu chi ERP_THANHTOANHOADON, " + query;
							 
							return false;
						}
						
						//NHẢ KẾ TOÁN LẠI KHI HỦY PHIẾU THU TIỀN
						
						if(Revert_KeToan_HuyPhieuChi(soct[i])== false) return false;
					}				
					
					if(this.loaict.equals("3")){// CẬP NHẬT TRẠNG THÁI HỦY CHO HÓA ĐƠN NCC
						//HÓA ĐƠN NCC ĐƯỢC LƯU 2 BẢNG: ERP_PARK VÀ ERP_HOADONNCC
						
						query ="UPDATE ERP_PARK SET TRANGTHAI = 3 WHERE PK_SEQ ='"+soct[i]+"'";
						
						System.out.println(query);
						if(!db.update(query))
						{
							db.getConnection().rollback();
							this.msg = "Khong the huy hoa don NCC ERP_PARK, " + query;
							 
							return false;
						}
						
						query ="UPDATE ERP_HOADONNCC SET TRANGTHAI = 3 WHERE PK_SEQ ='"+soct[i]+"'";
						System.out.println(query);
						if(!db.update(query))
						{
							db.getConnection().rollback();
							this.msg = "Khong the huy hoa don NCC ERP_PARK, " + query;
							 
							return false;
						}
						
						if(Revert_KeToan_HuyHoaDonNCC(soct[i])==false) return false;
						
					}*/
										
				}
			}
						
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			}
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

	private boolean Revert_KeToan_Dieuchuyentien(String sochungtu)
	{
		sochungtu = sochungtu.substring(3, sochungtu.length());
		try{
			String colNAME = "";
			String tableNAME = "";
			
			colNAME = "ngayghinhan";
			tableNAME = "ERP_PARK";
			
			String query = " select " + colNAME + " as ngaynghiepvu from " + tableNAME + " where pk_seq = '" + sochungtu + "' ";
			System.out.println("____LAY NGAY NGHIEP VU: " + query);
			
			ResultSet ngayRS = db.get(query);
			String ngaynghiepvu = "";
			
			if (ngayRS != null)
			{
				if(ngayRS.next())
				{
					ngaynghiepvu = ngayRS.getString("ngaynghiepvu");
				}
				ngayRS.close();
			}
			
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
				catch (Exception e) {
					e.printStackTrace();
				}
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
				    "where LOAICHUNGTU = N'Duyệt hóa đơn' and SOCHUNGTU = '" + sochungtu + "' ";
			
			System.out.println("1.CHECK NO-CO: " + query);
						
			ResultSet rsPSKT = db.get(query);
			if (rsPSKT != null)
			{
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
				catch (Exception e) { 
					e.printStackTrace();
				}
			}
			
			//GHI NHAN LOG NGUOC LAI
			query = " INSERT ERP_PHATSINHKETOAN( NGAYCHUNGTU, NGAYGHINHAN, LOAICHUNGTU, SOCHUNGTU, TAIKHOAN_FK, TAIKHOANDOIUNG_FK, NOIDUNGNHAPXUAT_FK, NO, CO, DOITUONG, MADOITUONG, LOAIDOITUONG, " +
					"							 SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC )  " +
					" 							 SELECT NGAYCHUNGTU, '" + ngaynghiepvu + "', N'REVERT_' + LOAICHUNGTU as LOAICHUNGTU, SOCHUNGTU, TAIKHOAN_FK, TAIKHOANDOIUNG_FK, NOIDUNGNHAPXUAT_FK, CO*(-1), NO*(-1), DOITUONG, MADOITUONG, LOAIDOITUONG,  " +
					"							 SOLUONG, (-1)*isnull(DONGIA,0) DONGIA, TIENTEGOC_FK, isnull(DONGIANT,0)*(-1), TIGIA_FK, ISNULL(TONGGIATRI,0)*(-1), ISNULL(TONGGIATRINT,0)*(-1), KHOANMUC  " +
					" 							 FROM ERP_PHATSINHKETOAN  " +
					" 							 WHERE LOAICHUNGTU = N'Duyệt hóa đơn' and SOCHUNGTU = '" + sochungtu + "' ";
			
			System.out.println("____LAY NGAY NGHIEP VU: " + query);
			
			if(!db.update(query))
			{
				this.msg = "2.Lỗi REVERT: " + query;
				return false;
			}
			
			
		}
		catch(Exception e )
		{
			e.printStackTrace();
		}

		return true;
	}
	
	private boolean Revert_KeToan_HuyPhieuChi(String sochungtu)
	{	
		sochungtu = sochungtu.substring(3, sochungtu.length());
		try{
			
			String query = "";					
			
			String	colNAME = "ngayghinhan";;
			String	tableNAME = "ERP_THANHTOANHOADON";
					
			query = " SELECT " + colNAME + " as ngaynghiepvu from " + tableNAME + " where pk_seq = '" + sochungtu + "' ";
			System.out.println("____LAY NGAY NGHIEP VU: " + query);
			
			ResultSet ngayRS = db.get(query);
			String ngaynghiepvu = "";
			if (ngayRS != null)
			{
				if(ngayRS.next())
				{
					ngaynghiepvu = ngayRS.getString("ngaynghiepvu");
				}
				ngayRS.close();
			}
			
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
				catch (Exception e) {
					e.printStackTrace();
				}
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
					
			String	loaict = "Thanh toán hóa đơn";
			
			//GHI NHAN NGUOC LAI TAI KHOAN NO - CO
			query = "select SOCHUNGTU, TAIKHOAN_FK, TAIKHOANDOIUNG_FK, NO, CO, TIENTEGOC_FK, TONGGIATRINT  " +
				    "from ERP_PHATSINHKETOAN " +
				    "where LOAICHUNGTU = N'"+loaict+"' and SOCHUNGTU = '" + sochungtu + "' ";
			
			System.out.println("1.CHECK NO-CO: " + query);
					
					
			ResultSet rsPSKT = db.get(query);
			if (rsPSKT != null)
			{
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
				catch (Exception e) { 
					e.printStackTrace();
				}
			}
			
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
		
			// NEU checkThanhtoantuTV <= 0 
			loaict = "Trả khác";
			
			//GHI NHAN NGUOC LAI TAI KHOAN NO - CO
			query = "select SOCHUNGTU, TAIKHOAN_FK, TAIKHOANDOIUNG_FK, NO, CO, TIENTEGOC_FK, TONGGIATRINT  " +
				    "from ERP_PHATSINHKETOAN " +
				    "where LOAICHUNGTU = N'"+loaict+"' and SOCHUNGTU = '" + sochungtu + "' ";
			
			System.out.println("1.CHECK NO-CO: " + query);
							
							
			rsPSKT = db.get(query);
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
					
					
			// NEU checkThanhtoantuTV <= 0 
			loaict = "Thanh toán Thuế nhập khẩu";
			
			//GHI NHAN NGUOC LAI TAI KHOAN NO - CO
			query = "select SOCHUNGTU, TAIKHOAN_FK, TAIKHOANDOIUNG_FK, NO, CO, TIENTEGOC_FK, TONGGIATRINT  " +
				    "from ERP_PHATSINHKETOAN " +
				    "where LOAICHUNGTU = N'"+loaict+"' and SOCHUNGTU = '" + sochungtu + "' ";
			
			System.out.println("1.CHECK NO-CO: " + query);
					
					
			rsPSKT = db.get(query);
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
		
		if( this.sochungtu.trim().length() < 0)
		{
			this.msg = "Số chứng từ hủy không hợp lệ. Vui lòng kiểm tra lại.";
			return false;
		}
		

		if(this.loaict.trim().length() <=0){
			this.msg = "Yêu cầu chọn loại chứng từ";
			return false;
		}
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			//LOAICHUNGTU = 1; HỦY PHIẾU THU TIỀN
			//LOAICHUNGTU = 2; HỦY PHIẾU CHI
			//LOAICHUNGTU = 3; HỦY HÓA ĐƠN NCC			
			
			if(this.loaict.equals("3")){//NẾU HỦY HÓA ĐƠN NCC
				// KIỂM TRA HÓA ĐƠN NCC CÓ PHIẾU CHI CHƯA
				String prefix = this.sochungtu.substring(3, this.sochungtu.length());
				String query =  
				" SELECT count(*) as sodong,  \n " +
				"		( select trangthai from ERP_PARK where pk_seq = '" + prefix + "' ) as trangthai \n " +
				" FROM 	ERP_HOADONNCC a inner join ERP_THANHTOANHOADON_HOADON b on a.pk_seq = b.HOADON_FK \n " +
				"		inner join ERP_THANHTOANHOADON c on b.THANHTOANHD_FK = c.PK_SEQ  \n " +
				" WHERE a.park_fk = '" + prefix + "' and c.trangthai in (0,1) and b.LOAIHD = 0 \n ";
				
				System.out.println(query);
				int sodong = 0;
				String _trangthai = "";
				ResultSet rs = db.get(query);
				if(rs != null)
				{
					if(rs.next())
					{
						sodong = rs.getInt("sodong");
						_trangthai = rs.getString("trangthai") == null ? "" : rs.getString("trangthai");
					}
					rs.close();
				}
				
				if(sodong > 0)
				{
					this.msg = "Hóa đơn này đã có thanh toán hóa đơn, bạn phải xóa thanh toán hóa đơn trước";
					return false;
				}
				
				if( ! ( _trangthai.equals("1") || _trangthai.equals("2") )  || ( _trangthai.trim().length() <= 0 ) )
				{
					this.msg = "Trạng thái hóa đơn này không hợp lệ, bạn chỉ có thể hủy những hóa đơn đã duyệt / hoàn tất ";
					return false;
				}

			}
			
			String query = "UPDATE ERP_HUYCHUNGTUKETOAN SET SOCHUNGTU ='" +this.sochungtu+"',SOCHUNGTUGOC ='" +this.sochungtugoc+"', NGUOISUA = '"+this.userId+
					"', LOAICHUNGTU ='"+this.loaict+"', NGAYSUA ='"+this.getDateTime()+"', NGAYGHINHAN = '"+this.Ngayghinhan+"' " +
					", DIENGIAI = N'"+this.dienGiaiCT+"' " +
						   " WHERE PK_SEQ = '"+this.id+"'";
			
			System.out.println("UPDATE ERP_HUYCHUNGTUKETOAN: " + query);
			
			if(!db.update(query))
			{
				db.getConnection().rollback();
				this.msg = "Khong the UPDATE ERP_HUYCHUNGTUKETOAN, " + query;
			 
				return false;
			}
			
			//HỦY DỮ LIỆU TRONG BẢNG
			query = "DELETE ERP_HUYCHUNGTUKETOAN_CHUNGTU  WHERE HCTKT_FK = '"+this.id+"'";

			System.out.println("UPDATE ERP_HUYCHUNGTUKETOAN: " + query);

			if(!db.update(query))
			{
				db.getConnection().rollback();
				this.msg = "Khong the DELETE ERP_HUYCHUNGTUKETOAN_CHUNGTU, " + query;
			
				return false;
			}

			//CHÈN LẠI DỮ LIỆU MỚI
			String loaictinsert = "";
			
			if(socthuy != null)
			{
				for(int i = 0; i < socthuy.length; i++)
				{
					if(loaict.equals("2")) loaictinsert = "Thanh toán hóa đơn";
					
					if(loaict.equals("3")) loaictinsert = "PARK Hóa đơn";
					
					query = "insert ERP_HUYCHUNGTUKETOAN_CHUNGTU (HCTKT_FK, SOCHUNGTU, NGAYCHUNGTU, TRANGTHAI, LOAICHUNGTU, NGAYTAO, SOTIEN) " +
							"values('" + this.id + "',  '" + soct[i] + "', '" + ngaychungtu[i] + "', N'" + trangthai[i] + "', N'"+loaictinsert+"', '" + ngaytao[i] + "',"+sotientt[i].replaceAll(",", "")+")";
					
					System.out.println("INSERT ERP_HUYCHUNGTUKETOAN_CHUNGTU : " + query);
					
					if(!db.update(query))
					{
						db.getConnection().rollback();
						this.msg = "Khong the tao moi ERP_HUYCHUNGTUKETOAN_CHUNGTU, " + query;
						 
						return false;
					}
										
					/*if(this.loaict.equals("2")){ //CẬP NHẬT TRẠNG THÁI HỦY CHO PHIẾU CHI
						query ="UPDATE ERP_THANHTOANHOADON SET TRANGTHAI = 2 WHERE PK_SEQ ='"+soct[i]+"'";
						
						if(!db.update(query))
						{
							db.getConnection().rollback();
							this.msg = "Khong the huy phieu chi ERP_THANHTOANHOADON, " + query;
							 
							return false;
						}
						
						//NHẢ KẾ TOÁN LẠI KHI HỦY PHIẾU THU TIỀN
						
						if(Revert_KeToan_HuyPhieuChi(soct[i])== false) return false;
					}				
					
					if(this.loaict.equals("3")){// CẬP NHẬT TRẠNG THÁI HỦY CHO HÓA ĐƠN NCC
						//HÓA ĐƠN NCC ĐƯỢC LƯU 2 BẢNG: ERP_PARK VÀ ERP_HOADONNCC
						
						query ="UPDATE ERP_PARK SET TRANGTHAI = 3 WHERE PK_SEQ ='"+soct[i]+"'";
						
						System.out.println(query);
						if(!db.update(query))
						{
							db.getConnection().rollback();
							this.msg = "Khong the huy hoa don NCC ERP_PARK, " + query;
							 
							return false;
						}
						
						query ="UPDATE ERP_HOADONNCC SET TRANGTHAI = 3 WHERE PK_SEQ ='"+soct[i]+"'";
						System.out.println(query);
						if(!db.update(query))
						{
							db.getConnection().rollback();
							this.msg = "Khong the huy hoa don NCC ERP_PARK, " + query;
							 
							return false;
						}
						
						if(Revert_KeToan_HuyHoaDonNCC(soct[i])==false) return false;
						
					}*/
					
					
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
			if (this.sochungtuRequest != null)
				this.sochungtuRequest.close();
			if (this.sochungtuDetail != null)
				this.sochungtuDetail.close();
		}catch (Exception e) {
			e.printStackTrace();
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
	
	public String getLoaiCTId() {
		
		return this.loaict;
	}

	
	public void setLoaiCTId(String loaiCTId) {
		
		this.loaict = loaiCTId;
	}




	
	public String getSochungtugoc() 
	{
		return this.sochungtugoc;
	}

	public void setSochungtugoc(String sochungtugoc) 
	{
		this.sochungtugoc = sochungtugoc;
		
	}
	
	public String getDienGiaiCT(){
		return this.dienGiaiCT;
	}
	public void setDienGiaiCT(String dienGiaiCT){
		this.dienGiaiCT = dienGiaiCT;
	}

	@Override
	public String getLoaiHdId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLoaiHdId(String loaihdId) {
		// TODO Auto-generated method stub
		
	}
	

	

	
	
}
