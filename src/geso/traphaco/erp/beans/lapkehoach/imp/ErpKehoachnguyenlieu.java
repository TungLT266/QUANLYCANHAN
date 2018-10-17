package geso.erp.beans.lapkehoach.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import geso.erp.beans.lapkehoach.IErpKehoachnguyenlieu;
import geso.erp.beans.lapkehoach.imp.ErpKehoachnguyenlieu;
import geso.dms.db.sql.dbutils;
import geso.dms.center.util.Utility;

public class ErpKehoachnguyenlieu implements IErpKehoachnguyenlieu
{
	String ctyId;
	String userId;
	String id;
	
	String ngaykehoach;
	
	String diengiai;
	String msg;
	
	String loaiId;
	
	ResultSet spRs;
	String spIds;
	
	ResultSet thangRs;
	
	String khoId;
	ResultSet khoRs;
	
	String[] thang;
	String thangstr;
	
	String nglieu;
	
	double yeucau ;
	double lotsize ;
	double tonantoan ;
	double tonkho ;
	double tondau ;
	double plnorder;
	int leadtime;
	
	dbutils db;
	
	public ErpKehoachnguyenlieu()
	{
		this.ctyId = "";
		this.userId = "";
		this.id = "";
		this.ngaykehoach = "";
		this.diengiai = "";
		this.msg = "";
		this.thangstr = "";
		this.loaiId = "";
		this.spIds = "";
		this.khoId = "";			
		this.nglieu = "";
		this.db = new dbutils();
	}
	
	public ErpKehoachnguyenlieu(String id)
	{
		this.ctyId = "";
		this.userId = "";
		this.id = id;
		this.ngaykehoach = "";
		this.diengiai = "";
		this.msg = "";
		this.thangstr = "";
		this.loaiId = "";
		this.spIds = "";
		this.khoId = "";
		this.nglieu = "";
		this.db = new dbutils();
	}
	
	public String getCtyId() 
	{
		return this.ctyId;
	}

	public void setCtyId(String ctyId) 
	{
		this.ctyId = ctyId;
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


	public String getNglieu() 
	{
		return this.nglieu;
	}

	public void setNglieu(String nglieu) 
	{
		this.nglieu = nglieu;	
	}
	
	public String getKho() 
	{
		return this.khoId;
	}

	public void setKho(String khoId) 
	{
		this.khoId = khoId;
	}

	public String getThangStr() 
	{
		return this.thangstr;
	}

	public String getDiengiai() 
	{
		return this.diengiai;
	}

	public void setDiengiai(String diengiai) 
	{
		this.diengiai = diengiai;
	}

	public String getLoaiId() 
	{
		return this.loaiId;
	}

	public void setLoaiId(String loaiId) 
	{
		this.loaiId = loaiId;
	}

	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	public void init() 
	{
		String query = "select ngaylap, loaisanpham_fk" +
						"from ERP_KeHoachNguyenLieu where pk_seq = '" + this.id + "'";
		System.out.println("11.Init: " + query);
		
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					this.ngaykehoach = rs.getString("ngaylap");	
					
					//this.khoId = rs.getString("kho_fk");
					
					if(rs.getString("loaisanpham_fk") != null)
						this.loaiId = rs.getString("loaisanpham_fk");
					
									
				}
				rs.close();
				
				
				query = "select distinct sanpham_fk from ERP_KeHoachNguyenLieu_SanPham where kehoach_fk = '" + this.id + "'";
				rs = db.get(query);
				if(rs != null)
				{
					String str = "";
					while(rs.next())
					{
						str += rs.getString("sanpham_fk") + ",";
					}
					if(str.trim().length() > 0)
					{
						str = str.substring(0, str.length() - 1);
						this.spIds = str;
					}
				}
				rs.close();
				
				query = "select distinct nam, thang from ERP_KeHoachNguyenLieu_SanPham where kehoach_fk = '" + this.id + "'";
				rs = db.get(query);
				if(rs != null)
				{
					this.thangstr = "";
					while(rs.next())
					{
						this.thangstr = this.thangstr + rs.getString("thang") + "-" + rs.getString("nam") + ",";
					}
					
					if(this.thangstr.trim().length() > 0)
					{
						this.thangstr = this.thangstr.substring(0, this.thangstr.length() - 1);
					}
				}
				rs.close();

			} 
			catch (Exception e) 
			{
				System.out.println("115.Error: " + e.getMessage());
			}
		}
		
		this.createRs();
	}
	
	
	public String getNgaykehoach() 
	{
		return this.ngaykehoach;
	}

	public void setNgaykehoach(String ngay) 
	{
		this.ngaykehoach = ngay;
	}

	public ResultSet getKhoRs() 
	{
		return this.khoRs;
	}

	public void setKhoRs(ResultSet khoRs) 
	{
		this.khoRs = khoRs;
	}

	
	public ResultSet getSpRs() 
	{
		return this.spRs;
	}

	public void setSpRs(ResultSet spRs)
	{
		this.spRs = spRs;	
	}

	public String getSpIds() 
	{
		return this.spIds;
	}

	public void setSpIds(String spIds) 
	{
		this.spIds = spIds;
	}

	public ResultSet getThangRs() 
	{	
		return this.thangRs;
	}

	public void setThangRs(ResultSet thangRs) 
	{
		this.thangRs = thangRs;
	}

	public String[] getThang()
	{
		return this.thang;
	}

	public void setThang(String[] thang) 
	{
		this.thang = thang;		
	}

	public boolean createKehoach()
	{	
		try 
		{
			if(this.ngaykehoach.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn ngày lập kế hoạch";
				return false;
			}
			
			if(this.thang.length == 0)
			{
				this.msg = "Vui lòng chọn tháng để lập kế hoạch";
				return false;
			}
			

			db.getConnection().setAutoCommit(false);
			
			String loaisanpham_fk = "null";

			if(this.loaiId.trim().length() > 0)
				loaisanpham_fk = this.loaiId.trim();
			
						
			String query =  "Insert ERP_KeHoachNguyenLieu(ngaylap, loaisanpham_fk, nguoitao, congty_fk) " +
							"values('" + this.ngaykehoach + "', " + loaisanpham_fk + ", '" + this.userId + "', " + this.ctyId + " )";
			
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_KeHoachNguyenLieu " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "select SCOPE_IDENTITY() as kehoach_fk";
			ResultSet rsCurrent = db.get(query);
			
			if(rsCurrent != null)
			{
				if(rsCurrent.next())
				{
					this.id = rsCurrent.getString("kehoach_fk");
					rsCurrent.close();
				}
			}

			String th, nam;
			for(int i = 0; i < this.thang.length; i++){
				th = this.thang[i].split("-")[0];
				nam = this.thang[i].split("-")[1];

				if(this.spIds.trim().length() > 0)				
				{		
					query = "Insert ERP_KeHoachNguyenLieu_SanPham(kehoach_fk, nam, thang, sanpham_fk) " +
						    "select " + this.id + " as kehoach_fk, " + nam +  " as nam, " + th + " as thang, " +
						    "pk_seq from erp_sanpham where pk_seq in ( " + this.spIds + " ) ";
				
					if(!db.update(query))
					{
						this.msg = "Không thể tạo mới ERP_KeHoachNguyenLieu_SanPham " + query;
						db.getConnection().rollback();
						return false;
					}

				}else{
					query = "Insert ERP_KeHoachNguyenLieu_SanPham(kehoach_fk, nam, thang, sanpham_fk) " +
							"select " + this.id + " as kehoach_fk, " + nam +  " as nam, " + th + " as thang, " +
							"pk_seq from erp_sanpham where pk_seq in ( ";
					
					String str  = "";
					String tmp = "select pk_seq from erp_sanpham where trangthai = '1' and congty_fk = " + this.ctyId + " ";

					if(this.loaiId.length() > 0){
						tmp = tmp + " and loaisanpham_fk = '" + this.loaiId + "' ";
					}
										
					ResultSet rs = this.db.get(tmp);
					while (rs.next()){
						str = str + rs.getString("pk_seq") + ",";
					}
					str = str.substring(0, str.length()-1);
					this.spIds = str;
					rs.close();
					System.out.println(str);
					
					query = query + str + ") ";
					if(!db.update(query))
					{
						this.msg = "Không thể thêm dòng vào ERP_KeHoachNguyenLieu_Sanpham " + query;
						db.getConnection().rollback();
						return false;
					}
				}
			}
			if( !this.createLenhMuaNguyeLieuDK() )
			{
				this.msg = "Không thể tạo mới kế hoạch tổng thể, vui lòng thử lại sau";
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
			catch (SQLException e1) {}
			return false;
		}
		
		return true;
	}

	
	private boolean createLenhMuaNguyeLieuDK() 
	{
		//Insert LenhSanXuat		
		String[] spId = this.spIds.split(",");
		
		String ngayketthuc = "";
		int th ;
		int nam ;
//		int ngayhientai;
		int thanghientai;
//		int namhientai;
		double ton;
//		double offset = 0;
		String query = "";
		
		try{
		for (int i = 0; i < spId.length; i++){   // Chạy cho từng sản phẩm
			
			for(int j = 0; j < this.thang.length; j++){  // Chạy cho từng tháng
				th = Integer.parseInt(this.thang[j].split("-")[0]);
				
				nam = Integer.parseInt(this.thang[j].split("-")[1]);
				
//				ngayhientai = Integer.parseInt(this.getDateTime().substring(8, 10));
				
				thanghientai = Integer.parseInt(this.getDateTime().substring(5, 7));
				
//				namhientai = Integer.parseInt(this.getDateTime().substring(0, 4));
							
				query = "DELETE FROM ERP_MUANGUYENLIEUDUKIEN " +
						"WHERE SANPHAM_FK = '" + spId[i] + "' AND THANG = " + th + " AND NAM = " + nam + " ";
				
				System.out.println(query);
				
				this.db.update(query);
				
				System.out.println("San pham: " + spId[i] + " and Thang - Nam: " + th + " - " + nam);
				
				this.getInitData(spId[i], th, nam);   // Khởi tạo tồn kho, lotsize, tồn kho an toàn, yêu cầu
				
				this.tondau = getTondau(nam, th, spId[i]); // Tính tồn đầu cho tới thời điểm chạy MPS
				
				System.out.println("Ton dau sua khi goi ham getTondau: " + this.tondau + " ");
				
				if(th != Integer.parseInt(this.getDateTime().substring(5, 7))) // Vì giả sử rằng tháng đầu đã trừ tồn an tòan vào cuối tháng trước rồi 
					ton = this.tondau - this.tonantoan;				// Lấy tồn = tồn đầu - tồn an toàn, số lượng tồn còn lại phải đáp ứng được yêu cầu
				else
					ton = this.tondau;
				
				System.out.println("Ton: " + ton + " ");				
				
				// Tao lenh san xuat du kien cho tung tuan
					
				this.yeucau = this.getDemand(th, nam, spId[i]);
									
				System.out.println("Yeu cau: " + this.yeucau);
					
				if(ton <= this.yeucau){					// Nếu tồn kho < yêu cầu -> tạo PlnOrder
					String tmp1 = "" + th;
					if(tmp1.length() == 1) tmp1 = "0" + tmp1 ;						
						if(th != thanghientai){
							query = "SELECT DATEADD(Day, -1, '" + nam + "-" + tmp1 + "-01' ) AS NGAY";
							System.out.println(query);
						
							ResultSet rs = this.db.get(query);
							rs.next();

							ngayketthuc = rs.getString("NGAY").substring(0, 10);
							rs.close();
						}else{
							ngayketthuc = this.getDateTime();
						}
						
						System.out.println("Ngay ket thuc:" + ngayketthuc);
						
					
						if(this.yeucau - ton  <= this.lotsize  ){  // Số lượng của PlnOrder = lotsize 
							System.out.println("Ton truoc: " + ton + " ; Yeu cau: " + this.yeucau);
							
							query =    	"INSERT INTO ERP_MUANGUYENLIEUDUKIEN " +
										"([CONGTY_FK], [KEHOACH_FK],[SANPHAM_FK],[SOLUONG],[NGAYBATDAU],[NGAYKETTHUC],[THANG],[NAM]," +
										"[NGAYTAO],[NGUOITAO],[NGAYSUA],[NGUOISUA],[TRANGTHAI]) VALUES "	+
										"(" + this.ctyId + ", " + this.id + ", '" + spId[i] + "', '" + this.lotsize + "', " + null + ", '" +  ngayketthuc + "', " +
										"'" + th + "', '" + nam + "', '" + this.getDateTime() + "', '" + this.userId + "', " + 
										"'" + this.getDateTime() + "', '" + this.userId + "', '1' )" ;
					
							System.out.println("truong hop yeucau <= this.lotsize: " + query);
							this.db.update(query);
							ton = ton + this.lotsize - this.yeucau;
							System.out.println("Ton sau: " + ton + " ; Yeu cau: " + this.yeucau);
						}else {
							if(this.lotsize > 0){   //Nếu lotsize > 0 và yêu cầu > lot size thì tạo nhiều PlnOrder cho đến khi đáp ứng được yêu cầu 
								long m = Math.round((this.yeucau - ton)/this.lotsize) + 1;
								if( m*this.lotsize + ton - this.yeucau > this.lotsize ) m = m - 1;
									
								System.out.println("Ton truoc: " + ton + " ; Yeu cau: " + this.yeucau);
								
								for(int n = 0; n < m; n ++){
									query =    	"INSERT INTO ERP_MUANGUYENLIEUDUKIEN " +
												"([CONGTY_FK], [KEHOACH_FK], [SANPHAM_FK],[SOLUONG],[NGAYBATDAU],[NGAYKETTHUC],[THANG],[NAM]," +
												"[NGAYTAO],[NGUOITAO],[NGAYSUA],[NGUOISUA],[TRANGTHAI]) VALUES "	+
												"(" + this.ctyId + ", " + this.id + ", '" + spId[i] + "', '" + this.lotsize + "', " + null + ", '" +  ngayketthuc + "', " +
												"'" + th + "', '" + nam + "', '" + this.getDateTime() + "', '" + this.userId + "', " + 
												"'" + this.getDateTime() + "', '" + this.userId + "', '1' )" ;
			
									System.out.println("lotsize > 0: " + query);
									ton = ton + this.lotsize;
									this.db.update(query);
								}
								
								ton = ton  - this.yeucau;
								System.out.println("Ton sau: " + ton + " ; Yeu cau: " + this.yeucau);
								
							}else{ 				//Nếu lotsize = 0 thì số lượng của PlnOrder bằng yêu cầu
								System.out.println("Ton truoc: " + ton + " ; Yeu cau: " + this.yeucau);
								query =    	"INSERT INTO ERP_MUANGUYENLIEUDUKIEN " +
											"([CONGTY_FK], [KEHOACH_FK], [SANPHAM_FK],[SOLUONG],[NGAYBATDAU],[NGAYKETTHUC],[THANG],[NAM]," +
											"[NGAYTAO],[NGUOITAO],[NGAYSUA],[NGUOISUA],[TRANGTHAI]) VALUES "	+
											"(" + this.ctyId + ", " + this.id + ", '" + spId[i] + "', '" + (this.yeucau - ton) + "', " + null + ", '" +  ngayketthuc + "', " +
											"'" + th + "', '" + nam + "', '" + this.getDateTime() + "', '" + this.userId + "', " + 
											"'" + this.getDateTime() + "', '" + this.userId + "', '1' )" ;
								
								System.out.println("Yeu cau - ton == lotsize: " + query);
								ton = ton + this.yeucau - ton;
								
								System.out.println("Ton sau: " + ton + " ; Yeu cau: " + this.yeucau);
								this.db.update(query);
								
							}
						}
					}else{ // Tồn đủ đáp ứng yeu cau -> o tạo PlnOrder
								System.out.println("Ton du dap ung yeu cau -> o tao PlnOrder: " + this.yeucau + " nho hon " + ton);
								System.out.println("Ton truoc: " + ton + " ; Yeu cau: " + this.yeucau);
								ton = ton - this.yeucau;
								System.out.println("Ton sau: " + ton + " ; Yeu cau: " + this.yeucau);
					}
			}
		
		}
		}catch(java.sql.SQLException e){}
		return true;
	}
    
	private void getInitData(String spId, int thang, int nam){
		try{
			String query =	"SELECT	SUM(KHO_SP.AVAILABLE)  AS TONKHO, " + 
							"CASE WHEN NL.CHOPHEPTHAYTHE = 1 AND NLTHAYTHE <> NULL " +
							"THEN THAYTHE.TONKHOANTOAN " +
							"ELSE NL.TONKHOANTOAN " +
							"END AS TONKHOANTOAN, " +  
			
							"CASE WHEN NL.CHOPHEPTHAYTHE = 1 AND NLTHAYTHE <> NULL " +
							"THEN THAYTHE.LUONGDATHANGTOITHIEU " +
							"ELSE NL.LUONGDATHANGTOITHIEU " +
							"END AS MINLOTSIZE, " +
							
							"CASE WHEN NL.CHOPHEPTHAYTHE = 1 AND NLTHAYTHE <> NULL " +
							"THEN THAYTHE.THOIGIANCHOGIAO " +
							"ELSE NL.THOIGIANCHOGIAO " +
							"END AS LEADTIME  " +
							
							"FROM ERP_KHOTT_SANPHAM KHO_SP " +  
							"INNER JOIN ERP_KHOTT KHOTT ON KHOTT.PK_SEQ = KHO_SP.KHOTT_FK AND KHOTT.LOAI = 2 " +
							"INNER JOIN ERP_NGUYENLIEU NL ON NL.SANPHAM_FK = KHO_SP.SANPHAM_FK   " +
							"LEFT JOIN ( " +
							"	SELECT SANPHAM_FK, TONKHOANTOAN, LUONGDATHANGTOITHIEU, THOIGIANCHOGIAO " +
							"	FROM ERP_NGUYENLIEU " +
							"	WHERE SANPHAM_FK IN (SELECT NLTHAYTHE FROM ERP_NGUYENLIEU WHERE SANPHAM_FK = '" + spId + "') " +
							")THAYTHE ON THAYTHE.SANPHAM_FK = KHO_SP.SANPHAM_FK " +
							"WHERE  KHO_SP.SANPHAM_FK = '" + spId + "' " + 
							"GROUP BY NL.LUONGDATHANGTOITHIEU,THAYTHE.LUONGDATHANGTOITHIEU,THAYTHE.THOIGIANCHOGIAO,NL.THOIGIANCHOGIAO, " + 
							"NL.CHOPHEPTHAYTHE,THAYTHE.TONKHOANTOAN, NL.TONKHOANTOAN  " ;

			System.out.println("Tinh ton kho va lot size: " + query);
			ResultSet rs = this.db.get(query);

			rs.next();
			this.tonkho = Double.parseDouble(rs.getString("TONKHO"));
			this.lotsize = Double.parseDouble(rs.getString("MINLOTSIZE"));
			this.tonantoan = Double.parseDouble(rs.getString("TONKHOANTOAN"));
			this.leadtime = Integer.parseInt(rs.getString("LEADTIME"));
			
			rs.close();
			System.out.println("Ton an toan: " + this.tonantoan);
			
		}catch(java.sql.SQLException e){}
	}
	
	private double getTondau(int nam, int thang, String spId){
	try{
		double tondau = 0;
		String date = this.getDateTime();
		String thanghientai = "";
		String thangtruoc = "";
		
		if(thang-1 < 10) thangtruoc = "0" + (thang-1);
		else thangtruoc = "" + (thang-1);
		
		if(thang < 10) thanghientai = "0" + (thang);
		else thanghientai = "" + (thang);

		String query = 	"SELECT " +
						"(SUM(KHO_SP.AVAILABLE)+ ISNULL(PLNORDER.PLNORDER, 0) + ISNULL(PO.PO, 0) - ISNULL(YEUCAU.YEUCAU, 0)) AS TONDAU " +  
						"FROM ERP_KHOTT_SANPHAM KHO_SP  " +
						"INNER JOIN ERP_KHOTT KHO ON KHO.PK_SEQ = KHO_SP.KHOTT_FK " +
						"LEFT JOIN  " +
						"(  " +
						"	SELECT ISNULL(SUM(SOLUONG),0) AS PLNORDER, SANPHAM_FK " +  
						"	FROM ERP_MUANGUYENLIEUDUKIEN " +
						"	WHERE THANG >= " + Integer.parseInt(this.getDateTime().substring(5, 7)) + " AND THANG <= " + (thang-1) + " AND NAM = " + nam + " " +
						"	AND SANPHAM_FK = '" + spId + "' " +  
						"	GROUP BY SANPHAM_FK  " +
						")PLNORDER ON PLNORDER.SANPHAM_FK = KHO_SP.SANPHAM_FK " +  
						"LEFT JOIN(  " +
						"	SELECT SUM(SOLUONG) AS PO, SANPHAM_FK " +   
						"	FROM ERP_MUAHANG_SP  " +
						"	WHERE NGAYNHAN >= '" + this.getDateTime() + "' AND NGAYNHAN <= '"+ nam + "-" + thangtruoc  + "-31' " +
						"	AND SANPHAM_FK = '" + spId + "' " +  
						"	GROUP BY SANPHAM_FK  " +
						")PO ON PO.SANPHAM_FK = KHO_SP.SANPHAM_FK " +  
						"LEFT JOIN(  " +
						"	SELECT SUM(NL.SOLUONGCHUAN) AS YEUCAU, NL.NGUYENLIEU_FK AS SANPHAM_FK " +
						"	FROM ERP_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU NL " +
						"	INNER JOIN ERP_LENHSANXUATDUKIEN LSXDK ON LSXDK.PK_SEQ = NL.LENHSANXUATDUKIEN_FK " +
						"	WHERE LSXDK.THANG >= " + Integer.parseInt(this.getDateTime().substring(5, 7)) + " AND LSXDK.THANG <= " + (thang - 1) + " AND LSXDK.NAM = "+ nam + " " +
						"	AND NL.NGUYENLIEU_FK = '" + spId + "' " +   
						"	GROUP BY NGUYENLIEU_FK  " +
						")YEUCAU ON YEUCAU.SANPHAM_FK = KHO_SP.SANPHAM_FK " +  
						"WHERE  KHO_SP.SANPHAM_FK = '" + spId + "'  AND KHO.LOAI = 2 " +
						"GROUP BY PLNORDER.PLNORDER, PO.PO, YEUCAU.YEUCAU ";
		
		System.out.println("Ton dau :" + query);
		ResultSet rs = this.db.get(query) ;
		rs.next();
		tondau = Double.parseDouble(rs.getString("TONDAU"));			
		rs.close();
		return tondau;
		
	}catch(java.sql.SQLException e){
		return 0;
	}
			
}
	

	private double getDemand(int thang, int nam, String spId){
		ResultSet rs;
		String th = "" + thang;
		if(thang < 10) th  = "0" + thang;
		String query;
		query = 	"SELECT " +
					"ISNULL(YEUCAU.YEUCAU, 0) - ISNULL(PO.PO,0) AS YEUCAU " +
					"FROM ERP_KHOTT_SANPHAM KHO_SP  " +
					"INNER JOIN ERP_KHOTT KHO ON KHO.PK_SEQ = KHO_SP.KHOTT_FK " +
					"LEFT JOIN(  " +
					"	SELECT SUM(SOLUONG) AS PO, SANPHAM_FK " +   
					"	FROM ERP_MUAHANG_SP  " +
					"	WHERE SUBSTRING(NGAYNHAN, 1, 4) = " + nam + " AND SUBSTRING(NGAYNHAN, 5, 2) = " + th + " " +
					"	AND SANPHAM_FK = '"+ spId + "' " +
					"	GROUP BY SANPHAM_FK  " +
					")PO ON PO.SANPHAM_FK = KHO_SP.SANPHAM_FK " +  
					"LEFT JOIN(  " +
					"	SELECT SUM(NL.SOLUONGCHUAN) AS YEUCAU, NL.NGUYENLIEU_FK AS SANPHAM_FK " +
					"	FROM ERP_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU NL " +
					"	INNER JOIN ERP_LENHSANXUATDUKIEN LSXDK ON LSXDK.PK_SEQ = NL.LENHSANXUATDUKIEN_FK " +
					"	WHERE LSXDK.THANG = " + thang + " AND LSXDK.NAM = "+ nam + " " +
					"	AND NL.NGUYENLIEU_FK = '"+ spId + "' " + 
					"	GROUP BY NGUYENLIEU_FK  " +
					")YEUCAU ON YEUCAU.SANPHAM_FK = KHO_SP.SANPHAM_FK " +  
					"WHERE  KHO_SP.SANPHAM_FK = '"+ spId + "' AND KHO.LOAI = 2 " +
					"GROUP BY PO.PO, YEUCAU.YEUCAU" ;

		System.out.println("Yeu cau: " + query);
		rs =  this.db.get(query);
		try{
			rs.next();
			double tmp = Double.parseDouble(rs.getString("YEUCAU"));			
			rs.close();
			return tmp;
		}catch(java.sql.SQLException e){}
		
		return 0;
	}
	
	
	public void createRs() 
	{
		this.CreateRsThang();
		
		if( this.thang != null )
		{
			for(int i = 0; i < this.thang.length ; i++){
				this.thangstr = this.thangstr  + this.thang[i].trim() + "," ;
				
			}
		

			this.thangstr = this.thangstr.substring(0, this.thangstr.length() - 1);
			System.out.println("thangstr: " + this.thangstr);
		}	
		String query = 	"select distinct b.pk_seq as spId, b.ma, b.ten from erp_lenhsanxuatdukien_yeucaunguyenlieu a " +
						"inner join erp_sanpham b on a.nguyenlieu_fk = b.pk_seq  " +
						"where b.trangthai = '1' and b.congty_fk = " + this.ctyId + " ";
			
		System.out.println("san pham list: " + query);
			
		if(this.loaiId.trim().length() > 0)
			query += " and b.loaisanpham_fk = '" + this.loaiId + "' ";
			
			
		System.out.println("3.San pham: " + query);
		this.spRs = db.get(query);
			
	}
	
	private void CreateRsThang()
	{
		String query = 	"SELECT DISTINCT SUBSTRING(NGAYKETTHUC, 6,2) AS THANG, SUBSTRING(NGAYKETTHUC, 1,4) AS NAM " +  
						"FROM ERP_LENHSANXUATDUKIEN  LSXDUKIEN " +
						"INNER JOIN ERP_KEHOACHTONGTHE KHTH ON KHTH.PK_SEQ = LSXDUKIEN.KEHOACH_FK  " +
						"WHERE KHTH.CONGTY_FK = " + this.ctyId + " " +
						"ORDER BY NAM ASC, THANG ASC ";
		this.thangRs	=	this.db.get(query);
	}
	
	public void DbClose() 
	{
		
		try 
		{
			if(spRs!=null){
				spRs.close();
			}
			if(khoRs!=null){
				khoRs.close();
			}
			
			if(this.thangRs != null)
				this.thangRs.close();
			this.db.shutDown();
		} 
		catch (SQLException e) {}
		
	}
	
	public String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	public static void main(String[] arg)
	{
		dbutils db = new dbutils();
		
		String query = "select '2012-12-20' as Ngay, tondau.KHOTT_FK, tondau.SANPHAM_FK, tondau.AVAILABLE as tondau, trungbinhban.TBBan, sanpham_sx.NGAYTONKHOANTOAN, sanpham_sx.SOLUONGSANXUAT,  yeucau.SANXUAT as YeuCau  " +
				"from  " +
				"(  " +
					"select KhoTT_FK, SANPHAM_FK, SANXUAT   " +
					"from ERP_YEUCAUCHITIET where THANG = '12' and nam = '2012'   " +
						"and SANPHAM_FK in ( select pk_seq from SANPHAM where TRANGTHAI = '1' )  " +
				")  " +
				"yeucau inner join   " +
				"(   " +
					"select KHOTT_FK, SANPHAM_FK, AVAILABLE from ERP_KHOTT_SANPHAM   " +
				")  " +
				"tondau on yeucau.khoTT_fk = tondau.KHOTT_FK and yeucau.SANPHAM_FK = tondau.SANPHAM_FK inner join " +
				"(  " +
					"SELECT NPP.KHOSAP as KHOTT_FK, DH_SP.SANPHAM_FK,    " +
						"SUM(DH_SP.SOLUONG) / DATEDIFF (day,  REPLACE( CONVERT ( VARCHAR(10), DATEADD(MONTH, -3, '2012-12-18' ), 111 ) , '/', '-') , '2012-12-18') as TBBan      " +
					"FROM DONHANG DH     " +
						"INNER JOIN DONHANG_SANPHAM DH_SP ON DH_SP.DONHANG_FK=DH.PK_SEQ    " +
						"INNER JOIN NHAPHANPHOI NPP on DH.NPP_FK = NPP.PK_SEQ   " +
					"WHERE  DH.NGAYNHAP >= REPLACE( CONVERT ( VARCHAR(10), DATEADD(MONTH, -3, '2012-12-18' ), 111 ) , '/', '-')     " +
						"AND DH.NGAYNHAP <= '2012-12-18' AND DH.TRANGTHAI = '1'     " +
						"AND DH.pk_seq not in ( select donhang_fk from DonHangTraVe      " +
												"where donhang_fk is not null and ngayduyet >= REPLACE( CONVERT ( VARCHAR(10), DATEADD(MONTH, -3, '2012-12-18' ), 111 ) , '/', '-') and ngayduyet <= '2012-12-18' )      " +
												"AND DH_SP.sanpham_fk in ( select PK_SEQ from SANPHAM where TRANGTHAI = '1' )     " +
					"GROUP BY NPP.KHOSAP, DH_SP.SANPHAM_FK   " +
					"having SUM(DH_SP.SOLUONG) / DATEDIFF (day,  REPLACE( CONVERT ( VARCHAR(10), DATEADD(MONTH, -3, '2012-12-18' ), 111 ) , '/', '-') , '2012-12-18') > 0  " +
				")  " +
				"trungbinhban on tondau.SANPHAM_FK = trungbinhban.SANPHAM_FK and tondau.KHOTT_FK = trungbinhban.KHOTT_FK   " +
				"inner join ERP_SANPHAM_SANXUAT sanpham_sx on trungbinhban.SANPHAM_FK = sanpham_sx.SANPHAM_FK " +
				"where tondau.SANPHAM_FK = '105841' ";
		
		System.out.println("___Thuc hien: " + query);
		
		Utility util = new Utility();
		
		ErpKehoachtongthe kehoach = new ErpKehoachtongthe();
		
		String ngayhientai = kehoach.getDateTime();
		//String ngayhientai = "2012-11-20";
		
		String nam = ngayhientai.substring(0, 4);
		String thang = ngayhientai.substring(5, 7);
		String ngay = ngayhientai.substring(8, 10);
		
		String cuoithang = util.LastDayOfMonth(Integer.parseInt(thang), Integer.parseInt(nam));
		
		String ngayconlai = "";
		if(Integer.parseInt(ngay) <= 1 )
			ngayconlai += "01" + ",";
		if(Integer.parseInt(ngay) <= 8 )
			ngayconlai += "08" + ",";
		if(Integer.parseInt(ngay) <= 15 )
			ngayconlai += "15" + ",";
		if(Integer.parseInt(ngay) <= 22 )
			ngayconlai += "22" + ",";
		//if(Integer.parseInt(ngay) <= 28 )
			//ngayconlai += "28" + ",";
		
		String ngaybatdau = "";
		if( Integer.parseInt(ngay) != 1 && Integer.parseInt(ngay) != 8 && Integer.parseInt(ngay) != 15 && Integer.parseInt(ngay) != 22 )
		{
			ngaybatdau = ngay;
			if(ngaybatdau.trim().length() < 2)
				ngaybatdau = "0" + ngay;
		}
		
		if( Integer.parseInt(ngay) > Integer.parseInt(cuoithang) )
			ngayconlai = cuoithang;
		
		if(ngaybatdau.trim().length() > 0)
		{
			ngayconlai = ngaybatdau + "," + ngayconlai;
		}
		
		if(ngayconlai.trim().length() > 0)
		{
			if(ngayconlai.indexOf(",") >= 0)
				ngayconlai = ngayconlai.substring(0, ngayconlai.length() - 1);
		}
		
		String[] tuan = ngayconlai.split(",");
		for(int i = 0; i < tuan.length; i ++)
		{
			tuan[i] = nam + "-" + thang + "-" + tuan[i];
		}
		
		System.out.println("Ngay Con Lai: " + ngayconlai);
		//System.out.println("Ngay cuoi thang: " + nam + "-" + thang + "-" + cuoithang);
		
		ResultSet rsSp = db.get(query);
		if(rsSp != null)
		{
			try 
			{
				while(rsSp.next())
				{
					String sanpham_fk = rsSp.getString("SANPHAM_FK");
					String khott_fk = rsSp.getString("KHOTT_FK");
					
					double tondau = rsSp.getDouble("tondau");
					double tbBan = rsSp.getDouble("TBBan");
					double tonantoan = rsSp.getDouble("NGAYTONKHOANTOAN");
					double soluongSX = rsSp.getDouble("SOLUONGSANXUAT");
					double yeucau = rsSp.getDouble("YeuCau");
					
					double ttTondau = rsSp.getDouble("tondau");
					
					if(tuan.length > 0)
					{
						for(int i = 0; i < tuan.length; i++)
						{
							//Pending
							double pending = 0;
							double ttReq = 0;
							
							String sqlPending = "";
							
							//System.out.println("++++ Tuan lenght: " + tuan.length + "  -- i la: " + i);
							if(i == 0)
							{
								sqlPending = "select isnull(sum(b.SOLUONG), 0) as Pending  " +
								 			"from DONDATHANG a inner join DONDATHANG_SP b on a.PK_SEQ = b.DONDATHANG_FK  " +
								 			"where SANPHAM_FK = '" + sanpham_fk + "' and KHOTT_FK = '" + khott_fk + "'  and NGAYDAT <= '" + tuan[i] + "'";
							}
							else
							{
								sqlPending = "select isnull(sum(b.SOLUONG), 0) as Pending  " +
											 "from DONDATHANG a inner join DONDATHANG_SP b on a.PK_SEQ = b.DONDATHANG_FK  " +
											 "where SANPHAM_FK = '" + sanpham_fk + "' and KHOTT_FK = '" + khott_fk + "'  and NGAYDAT <= '" + tuan[i] + "' and NGAYDAT > '" +  tuan[i - 1]+ "' ";
							}
							
							//System.out.println("-------------SQL Lay Pending (" + tuan[i] + "): " + sqlPending);
							
							ResultSet rsPending = db.get(sqlPending);
							if(rsPending.next())
							{
								pending = rsPending.getDouble("Pending");
								ttTondau -= pending;
								ttReq += pending;
							}
							if(rsPending!=null){
								rsPending.close();
							}
							
							if(pending > 0)
							{
								System.out.println("San pham: " + sanpham_fk + " ---- KhoTT: " + khott_fk + " __Lay Pending (" + tuan[i] + "): " + pending + "  - Ton kho: " + ttTondau);
							}
							
							//Demand
							double Demand = yeucau / 4;
							ttTondau -= Demand;
							ttReq += Demand;
							
							if(Demand > 0)
							{
								System.out.println("San pham: " + sanpham_fk + " ---- KhoTT: " + khott_fk + " __Lay Demand (" + tuan[i] + "): " + Demand + "  - Ton kho: " + ttTondau);
							}
							
							//StockReq
							double StockReq = 0;
							
							
							//
							if( ( ttReq > 0 ) && ( ( ttTondau / tbBan ) < tonantoan ) )
							{
								ttTondau += ttReq;
								
								System.out.println("San pham: " + sanpham_fk + " ---- KhoTT: " + khott_fk + " __Lay StockReq (" + tuan[i] + "): " + ttReq + "  - Ton kho: " + ttTondau);
								
								ttReq = 0;
								if( ( ttTondau / tbBan ) < tonantoan )
								{
									//Them 1 Request nua
									double request = ( tonantoan * tbBan ) - ttTondau;
									
									ttTondau += request;
									
									System.out.println("San pham: " + sanpham_fk + " ---- KhoTT: " + khott_fk + " __Lay StockReq22 (" + tuan[i] + "): " + request);
								}
							}
							
							/*while ( ( ttTondau / tbBan ) < tonantoan )
							{
								StockReq = soluongSX;
								
								//System.out.println("Ton dau (" + tuan[i] + "): " + ttTondau + ", TB ban: " + tbBan + " - Ton dau / Tb Ban: " + ( (ttTondau / tbBan) - tonantoan) + " , Ton an toan: " +  tonantoan);
								
								ttTondau += soluongSX;
								
								System.out.println("San pham: " + sanpham_fk + " __Lay StockReq (" + tuan[i] + "): " + StockReq);
							}*/
								
						}
					}
					
				}
				rsSp.close();
			} 
			catch (Exception e) 
			{
				System.out.println("115.Exception: " + e.getMessage());
			}
		}
	}

}
