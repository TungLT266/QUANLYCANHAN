package geso.erp.beans.lapkehoach.imp;

import geso.dms.center.util.Utility;
import geso.dms.db.sql.dbutils;
import geso.erp.beans.lapkehoach.IErpKehoachtongthe;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ErpKehoachtongthe implements IErpKehoachtongthe 
{
	String ctyId;
	String userId;
	String id;
	
	String ngaykehoach;
	
	String diengiai;
	String msg;
	
	ResultSet nhRs;
	String nhIds;
	ResultSet clRs;
	String clIds;
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
	
	dbutils db;
	
	public ErpKehoachtongthe()
	{
		this.ctyId = "";
		this.userId = "";
		this.id = "";
		this.ngaykehoach = "";
		this.diengiai = "";
		this.msg = "";
		this.thangstr = "";
		this.nhIds = "";
		this.clIds = "";
		this.spIds = "";
		this.khoId = "";			
		this.nglieu = "";
		this.db = new dbutils();
	}
	
	public ErpKehoachtongthe(String id)
	{
		this.ctyId = "";
		this.userId = "";
		this.id = id;
		this.ngaykehoach = "";
		this.diengiai = "";
		this.msg = "";
		this.thangstr = "";
		this.nhIds = "";
		this.clIds = "";
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
		String query = "select ngaylap, nhanhang_fk, chungloai_fk" +
						"from ERP_KeHoachTongThe where pk_seq = '" + this.id + "'";
		System.out.println("11.Init: " + query);
		
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				if (rs != null)
				{
					if(rs.next())
					{
						this.ngaykehoach = rs.getString("ngaylap");	
						
						//this.khoId = rs.getString("kho_fk");
						
						if(rs.getString("nhanhang_fk") != null)
							this.nhIds = rs.getString("nhanhang_fk");
						
						if(rs.getString("chungloai_fk") != null)
							this.clIds = rs.getString("chungloai_fk");
											
					}
					rs.close();
				}				
				
				query = "select distinct sanpham_fk from ERP_KeHoachTongThe_SanPham where kehoach_fk = '" + this.id + "'";
				rs = db.get(query);
				if (rs != null)
				{
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
				}
				
				query = "select distinct nam, thang from ERP_KeHoachTongThe_SanPham where kehoach_fk = '" + this.id + "'";
				rs = db.get(query);
				if (rs != null)
				{
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

	
	public ResultSet getNhRs() 
	{
		return this.nhRs;
	}

	public void setNhRs(ResultSet nhRs) 
	{
		this.nhRs = nhRs;
	}

	public String getNhIds() 
	{
		return this.nhIds;
	}

	public void setNhIds(String nhIds) 
	{
		this.nhIds = nhIds;	
	}

	public ResultSet getClRs()
	{
		return this.clRs;
	}

	public void setClRs(ResultSet clRs) 
	{
		this.clRs = clRs;
	}

	public String getClIds() 
	{
		return this.clIds;
	}

	public void setClIds(String clIds) 
	{
		this.clIds = clIds;
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
			
			String nh_fk = "null";

			if(this.nhIds.trim().length() > 0)
				nh_fk = this.nhIds.trim();
			
			String cl_fk = "null";
			
			if(this.clIds.trim().length() > 0)
				cl_fk = this.clIds.trim();
						
			String query =  "Insert ERP_KeHoachTongThe(ngaylap, nhanhang_fk, chungloai_fk, nguoitao, congty_fk) " +
							"values('" + this.ngaykehoach + "', " + nh_fk + ", " + cl_fk + ", '" + this.userId + "', " + this.ctyId + " )";
			
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_KeHoachTongThe " + query;
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
				}
				rsCurrent.close();
			}

			String th, nam;
			for(int i = 0; i < this.thang.length; i++){
				th = this.thang[i].split("-")[0];
				nam = this.thang[i].split("-")[1];

				if(this.spIds.trim().length() > 0)				
				{		
					query = "Insert ERP_KeHoachTongThe_SanPham(kehoach_fk, nam, thang, sanpham_fk) " +
						    "select " + this.id + " as kehoach_fk, " + nam +  " as nam, " + th + " as thang, " +
						    "pk_seq from erp_sanpham where pk_seq in ( " + this.spIds + " ) ";
				
					if(!db.update(query))
					{
						this.msg = "Không thể tạo mới ERP_KeHoachTongThe " + query;
						db.getConnection().rollback();
						return false;
					}

				}else{
					query = "Insert ERP_KeHoachTongThe_SanPham(kehoach_fk, nam, thang, sanpham_fk) " +
							"select " + this.id + " as kehoach_fk, " + nam +  " as nam, " + th + " as thang, " +
							"pk_seq from erp_sanpham where pk_seq in ( ";
					
					String str  = "";
					String tmp = "select pk_seq from erp_sanpham where trangthai = '1' ";

					if(this.nhIds.length() > 0){
						tmp = tmp + " and nhanhang_fk = '" + this.nhIds + "' ";
					}
				
					if(this.clIds.length() > 0){
						tmp = tmp + " and chungloai_fk = '" + this.clIds + "' ";
					}
							
					ResultSet rs = this.db.get(tmp);
					if (rs != null)
					{
						while (rs.next()){
							str = str + rs.getString("pk_seq") + ",";
						}
						rs.close();
					}
					
					str = str.substring(0, str.length()-1);
					this.spIds = str;
					System.out.println(str);
					
					query = query + str + ") ";
					if(!db.update(query))
					{
						this.msg = "Không thể thêm dòng vào ERP_KeHoachTongThe_Sanpham " + query;
						db.getConnection().rollback();
						return false;
					}
				}
			}
			
			// Tao lenh san xuat du kien (Planned Order) 
			if( !createLenhSanXuatDK() )
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

	
	private boolean createLenhSanXuatDK() 
	{
		//Insert LenhSanXuat		
		String[] spId = this.spIds.split(",");
		
		String ngayketthuc = "";
		int th ;
		int nam ;
		int ngayhientai;
		int thanghientai;
		int namhientai;
		double ton;
		double offset = 0;
		String query = "";
		
		try{
		for (int i = 0; i < spId.length; i++){   // Chạy cho từng sản phẩm
			
			for(int j = 0; j < this.thang.length; j++){  // Chạy cho từng tháng
				th = Integer.parseInt(this.thang[j].split("-")[0]);
				
				nam = Integer.parseInt(this.thang[j].split("-")[1]);
				
				ngayhientai = Integer.parseInt(this.getDateTime().substring(8, 10));
				
				thanghientai = Integer.parseInt(this.getDateTime().substring(5, 7));
				
				namhientai = Integer.parseInt(this.getDateTime().substring(0, 4));
				
				int ngaybatdau;
								
				if(namhientai == nam & thanghientai == th){
					
					ngaybatdau = ngayhientai/8;
					
					ngaybatdau = ngaybatdau*7 + 8;
													
					offset = this.getOffset(ngaybatdau, th, nam, spId[i]);
					
					System.out.println("offset: " + offset);
				}else{
					
					ngaybatdau = 1;
				}
				
				query = "DELETE FROM ERP_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU " +
						"WHERE LENHSANXUATDUKIEN_FK IN (" +
						"	SELECT PK_SEQ FROM ERP_LENHSANXUATDUKIEN " +
						"	WHERE SANPHAM_FK = '" + spId[i] + "' AND THANG = " + th + " AND NAM = " + nam + " )";
				System.out.println(query);
				
				this.db.update(query);
				
				query = "DELETE FROM ERP_LENHSANXUATDUKIEN WHERE SANPHAM_FK = '" + spId[i] + "' AND THANG = " + th + " AND NAM = " + nam + " ";
				
				System.out.println(query);
				
				this.db.update(query);
				
				System.out.println("San pham: " + spId[i] + " and Thang - Nam: " + th + " - " + nam);
				
				this.getInitData(spId[i], th, nam);   // Khởi tạo tồn kho, lotsize, tồn kho an toàn, yêu cầu
				
				this.tondau = getTondau(ngaybatdau, nam, th, spId[i], offset); // Tính tồn đầu cho tới thời điểm chạy MPS
				
				System.out.println("Ton dau: " + this.tondau + " ");
				
				if(th != Integer.parseInt(this.getDateTime().substring(5, 7))) // Vì giả sử rằng tháng đầu đã trừ tồn an tòan vào cuối tháng trước rồi 
					ton = this.tondau - this.tonantoan;				// Lấy tồn = tồn đầu - tồn an toàn, số lượng tồn còn lại phải đáp ứng được yêu cầu
				else
					ton = this.tondau;
				
				ton = Math.round(ton);
				System.out.println("Ton: " + ton + " ");				
				
				// Tao lenh san xuat du kien cho tung tuan
				for (int k = ngaybatdau; k < 29; ){			// Tạo PlnOrder cho từng tuần
					
					this.yeucau = Math.round(this.getDemand(k, th, nam, spId[i]));
									
					System.out.println("Yeu cau: " + this.yeucau);
					
					if(ton <= this.yeucau){					// Nếu tồn kho < yêu cầu -> tạo PlnOrder
						String tmp1 = "" + th;
						if(tmp1.length() == 1) tmp1 = "0" + tmp1;
					
						String tmp2 = "" + k;
						if(tmp2.length() == 1) tmp2 = "0" + k;
					
						query = "SELECT DATEADD(Day, -1, '" + nam + "-" + tmp1 + "-" + tmp2 + "') AS NGAY";
						System.out.println(query);
					
						ResultSet rs = this.db.get(query);
						if (rs != null)
						{
							if (rs.next())
							{
								ngayketthuc = rs.getString("NGAY").substring(0, 10);
								System.out.println("Ngay ket thuc:" + ngayketthuc);
							}
							rs.close();
						}
						
						if(this.yeucau - ton  <= this.lotsize  ){  // Số lượng của PlnOrder = lotsize 
							System.out.println("Ton truoc: " + ton + " ; Yeu cau: " + this.yeucau);
							
							query =    	"INSERT INTO ERP_LENHSANXUATDUKIEN " +
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
									query = "INSERT INTO ERP_LENHSANXUATDUKIEN " +
											"([CONGTY_FK], [KEHOACH_FK],[SANPHAM_FK],[SOLUONG],[NGAYBATDAU],[NGAYKETTHUC], [THANG],[NAM]," +
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
								
								query = "INSERT INTO ERP_LENHSANXUATDUKIEN " +
										"([CONGTY_FK], [KEHOACH_FK],[SANPHAM_FK],[SOLUONG],[NGAYBATDAU],[NGAYKETTHUC], [THANG],[NAM]," +
										"[NGAYTAO],[NGUOITAO],[NGAYSUA],[NGUOISUA],[TRANGTHAI]) VALUES "	+
										"(" + this.ctyId + ", " + this.id + ", '" + spId[i] + "', '" + (this.yeucau - ton)  + "', " + null + ", '" +  ngayketthuc + "', " +
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
					k = k + 7;
				}
				
			}
		
			}
		createMaterialDemand();

		}catch(java.sql.SQLException e){}
		return true;
	}
    
	private boolean createLenhSanXuatDK_MTO() 
	{
				
		String[] spId = this.spIds.split(",");
		
		int th ;
		int nam ;
		String query = "";
		ResultSet rs;
		try{
		for (int i = 0; i < spId.length; i++){   // Chạy cho từng sản phẩm
			
			for(int j = 0; j < this.thang.length; j++){  // Chạy cho từng tháng
				th = Integer.parseInt(this.thang[j].split("-")[0]);				
				nam = Integer.parseInt(this.thang[j].split("-")[1]);
								
				query = "SELECT YEUCAU FROM ERP_YEUCAUCUNGUNG WHERE SANPHAM_FK = '" + spId[i] + "' AND THANG = " + th + " AND NAM = " + nam + " ";
				rs = this.db.get(query);
				rs.next();
				
				String soluong = rs.getString("YEUCAU");
				
				rs.close();
				
				query = "SELECT COUNT(PK_SEQ) AS NUM FROM ERP_LENHSANXUATDUKIEN WHERE SANPHAM_FK = '" + spId[i] + "' AND THANG = " + th + " AND NAM = " + nam + " ";
				
				System.out.println(query);
				
				rs = this.db.get(query);
				if (rs != null)
				{
					if (rs.next())
					{
						if(rs.getString("NUM").equals("0")){
							query =    	"INSERT INTO ERP_LENHSANXUATDUKIEN " +
										"([CONGTY_FK], [SANPHAM_FK],[SOLUONG], [THANG],[NAM]," +
										"[NGAYTAO],[NGUOITAO],[NGAYSUA],[NGUOISUA],[TRANGTHAI]) VALUES "	+
										"(" + this.ctyId + ", '" + spId[i] + "', '" + soluong + "', " +
										"'" + th + "', '" + nam + "', '" + this.getDateTime() + "', '" + this.userId + "', " + 
										"'" + this.getDateTime() + "', '" + this.userId + "', '1' )" ;
							this.db.update(query);
							
						}else{
							query =		"UPDATE ERP_LENHSANXUATDUKIEN SET SOLUONG = '" + soluong + "' WHERE SANPHAM_FK = '" + spId[i] + "' AND THANG = " + th + " AND NAM = " + nam + " ";
							this.db.update(query);
						}			
					}					
					rs.close();
				}				
				
			}
		
		}
		createMaterialDemand();

		}catch(java.sql.SQLException e){}
		return true;
	}

	private void createMaterialDemand(){
		String query;
		int th;
		int nam;
		ResultSet SPrs;
		ResultSet NLrs;
		
		System.out.println("Bat dau thuc hien ");
		
		String[] spId = this.spIds.split(",");

		for (int i = 0; i < spId.length; i++){   // Chạy cho từng sản phẩm
			
			for(int j = 0; j < this.thang.length; j++){  // Chạy cho từng tháng
				th = Integer.parseInt(this.thang[j].split("-")[0]);
				
				nam = Integer.parseInt(this.thang[j].split("-")[1]);

				query = "SELECT PK_SEQ AS ID, SOLUONG FROM ERP_LENHSANXUATDUKIEN WHERE THANG = " + th + " AND NAM = " + nam + " AND SANPHAM_FK = " + spId[i] + " ";
				System.out.println(query);
				
				SPrs = this.db.get(query);
				
				try{
					if(SPrs != null){
						while(SPrs.next()){
							String id = SPrs.getString("ID");
							String soluong = SPrs.getString("SOLUONG");
							NLrs = getDanhmucvattu(spId[i], soluong);
				
							if(NLrs != null){
								while(NLrs.next()){
									query = "INSERT INTO ERP_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU (LENHSANXUATDUKIEN_FK, NGUYENLIEU_FK, SOLUONGCHUAN) " +
											"VALUES( " + id + ", " + NLrs.getString("VATTU_FK") + ", " + NLrs.getString("SOLUONG") + ")" ;
								
									System.out.println(query);
									this.db.update(query);
								}	
								NLrs.close();
							}
						}
						SPrs.close();
					}
					
				}catch(java.sql.SQLException e){
					e.printStackTrace();
				}
			}
		}
	}
	
	private ResultSet getDanhmucvattu(String spId, String soluong){
			
		 String query =	"SELECT CASE WHEN NL.CHOPHEPTHAYTHE = 1 THEN NLTHAYTHE ELSE VT.VATTU_FK END AS VATTU_FK, " + 
			 			"CASE WHEN NL.CHOPHEPTHAYTHE = 1 THEN NL.TILE*" + soluong + "*VT.SOLUONG/DM.SOLUONGCHUAN ELSE " + soluong + "*VT.SOLUONG/DM.SOLUONGCHUAN END AS SOLUONG " + 
			 			"FROM ERP_DANHMUCVATTU DM " +
			 			"INNER JOIN ERP_DANHMUCVATTU_VATTU VT ON VT.DANHMUCVT_FK = DM.PK_SEQ " + 
			 			"LEFT  JOIN ERP_NGUYENLIEU NL ON NL.SANPHAM_FK = VT.VATTU_FK " +
			 			"WHERE DM.SANPHAM_FK = " + spId + "";
		 System.out.println(query);
		 return this.db.get(query);
	}
	
	private void getInitData(String spId, int thang, int nam){
		try{
			String query = 	"SELECT SUM(KHO_SP.AVAILABLE)  AS TONKHO, SANXUAT_SP.SOLUONGSANXUAT AS LOTSIZE " + 
							"FROM ERP_KHOTT_SANPHAM KHO_SP " +
							"INNER JOIN ERP_KHOTT KHOTT ON KHOTT.PK_SEQ = KHO_SP.KHOTT_FK AND KHOTT.LOAI = 3 " +
							"INNER JOIN ERP_SANPHAM_SANXUAT SANXUAT_SP ON SANXUAT_SP.SANPHAM_FK = KHO_SP.SANPHAM_FK " + 
							"WHERE  KHO_SP.SANPHAM_FK = '" + spId + "' " +
							"GROUP BY SANXUAT_SP.SOLUONGSANXUAT ";

			System.out.println("Tinh ton kho va lot size: " + query);
			ResultSet rs = this.db.get(query);
			if (rs != null)
			{
				if (rs.next())
				{
					this.tonkho = Double.parseDouble(rs.getString("TONKHO"));
					this.lotsize = Double.parseDouble(rs.getString("LOTSIZE"));
				}
				rs.close();
			}			
			query		=	"SELECT DB_SP.SANPHAM_FK, SUM(DB_SP.TONKHOANTOAN) AS TONKHOANTOAN, DB_SP.THANG, DB_SP.NAM, " +
							"SUM(YEUCAU.TUAN1) AS TUAN1 " + 
							"FROM ERP_DUBAO DUBAO " +
							"INNER JOIN ERP_DUBAOSANPHAM DB_SP ON DB_SP.DUBAO_FK = DUBAO.PK_SEQ " + 
							"INNER JOIN ERP_YEUCAUCUNGUNG YEUCAU ON YEUCAU.KHOTT_FK = DUBAO.KHO_FK " +
							"AND YEUCAU.SANPHAM_FK = DB_SP.SANPHAM_FK AND YEUCAU.THANG = DB_SP.THANG " +
							"AND YEUCAU.NAM = DB_SP.NAM " +
							"INNER JOIN ERP_TAOYEUCAU TAOYEUCAU ON TAOYEUCAU.DUBAO_FK = DUBAO.PK_SEQ " + 
							"AND TAOYEUCAU.PK_SEQ = YEUCAU.TAOYEUCAU_FK " +
							"WHERE  DB_SP.SANPHAM_FK = '" + spId + "' AND DB_SP.THANG = '" + thang +  "' AND DB_SP.NAM = '" + nam +  "' " +
							"GROUP BY DB_SP.SANPHAM_FK, DB_SP.THANG, DB_SP.NAM ";

			System.out.println("Tinh ton kho an toan va yeu cau: " + query);
			
			rs = this.db.get(query);
			if (rs != null)
			{
				if (rs.next())
				{
					this.tonantoan = Double.parseDouble(rs.getString("TONKHOANTOAN"));
					System.out.println("Ton an toan: " + this.tonantoan);
				}				
				rs.close();
			}			
		}catch(java.sql.SQLException e){
			e.printStackTrace();
		}
	}
	
	private double getTondau(int ngaybatdau, int nam, int thang, String spId, double offset){
	try{
		double tondau = 0;
		String date = this.getDateTime();
		int n = Integer.parseInt(date.substring(0, 4));
		int th = Integer.parseInt(date.substring(5, 7));
		int ng = Integer.parseInt(date.substring(8,10));
		int t = 0;
		String query;

		if(ngaybatdau == 1 & thang != th){
			if(n == nam){
				query =	"SELECT	SOLUONGSANXUAT AS LOTSIZE,	" +
						"(SUM(KHO_SP.AVAILABLE)  + ISNULL(PLNORDER.PLNORDER, 0) + ISNULL(PROORDER.PROORDER, 0) - ISNULL(YEUCAU.YEUCAU, 0)) AS TONDAU " + 
						"FROM ERP_KHOTT_SANPHAM KHO_SP " +
						"INNER JOIN ERP_SANPHAM_SANXUAT SANXUAT_SP ON SANXUAT_SP.SANPHAM_FK = KHO_SP.SANPHAM_FK " + 
						"LEFT JOIN " +
						"( " +
						"	SELECT SUM(SOLUONG) AS PLNORDER, SANPHAM_FK " + 
						"	FROM ERP_LENHSANXUATDUKIEN " +
						"	WHERE THANG >= " + th + " AND THANG <= " + (thang - 1) + " AND NAM = " + nam + " " +
						"	AND SANPHAM_FK = '" + spId + "' " + 
						"	GROUP BY SANPHAM_FK " +
						")PLNORDER ON PLNORDER.SANPHAM_FK = KHO_SP.SANPHAM_FK " + 
						"LEFT JOIN(  " +
						"	SELECT SUM(SOLUONG) AS PROORDER, SANPHAM_FK " +
						"	FROM ERP_LENHSANXUAT " +
						"	WHERE SUBSTRING(NGAYDUKIENHT, 0, 5) = " + nam + " " + 
						"	AND CONVERT(INT, SUBSTRING(NGAYDUKIENHT, 6, 2)) >= " + th + " " +
						"	AND CONVERT(INT, SUBSTRING(NGAYDUKIENHT, 6, 2)) <= " + (thang - 1) + " " +
						"	AND SANPHAM_FK = '" + spId + "' AND DONDATHANG_FK IS NULL " +
						"	GROUP BY SANPHAM_FK " +
						")PROORDER ON PROORDER.SANPHAM_FK = KHO_SP.SANPHAM_FK " + 
						"LEFT JOIN( " + 
						"	SELECT SUM(TUAN1+TUAN2+TUAN3+TUAN4) AS YEUCAU ,SANPHAM_FK " +
						"	FROM ERP_YEUCAUCUNGUNG " +
						"	WHERE THANG >= " + th + " AND THANG <= " + (thang - 1) + " AND NAM = " + nam + " " +					
						"	AND SANPHAM_FK = '" + spId + "' " +
						"	GROUP BY SANPHAM_FK " +
						")YEUCAU ON YEUCAU.SANPHAM_FK = KHO_SP.SANPHAM_FK " + 
							"WHERE KHO_SP.SANPHAM_FK = '" + spId + "' " +
							"GROUP BY SOLUONGSANXUAT, PLNORDER.PLNORDER, PROORDER.PROORDER, YEUCAU.YEUCAU "; 

				System.out.println("Lay ton dau:" + query);
				ResultSet rs = this.db.get(query) ;
				if (rs != null)
				{
					if (rs.next())
						tondau = Double.parseDouble(rs.getString("TONDAU"));
					rs.close();
				}
				System.out.println("offset:" + offset);
				tondau = tondau + offset; // vi da tru ton cho tat ca yeu cau cua thang dau tien, nen phai cong lai phan yeu cau tru du

				System.out.println("ton dau: " + tondau);
							
				return tondau;
			}else if(nam == n + 1){
				query = "SELECT	SOLUONGSANXUAT AS LOTSIZE,	" + 
						"(SUM(KHO_SP.AVAILABLE) + ISNULL(PLNORDER.PLNORDER, 0) + ISNULL(PROORDER.PROORDER, 0) - ISNULL(YEUCAU.YEUCAU, 0)) AS TONDAU " +
						"FROM ERP_KHOTT_SANPHAM KHO_SP " +
						"INNER JOIN ERP_SANPHAM_SANXUAT SANXUAT_SP ON SANXUAT_SP.SANPHAM_FK = KHO_SP.SANPHAM_FK " + 
						"LEFT JOIN " +
						"( " +
							"SELECT SUM(SOLUONG) AS PLNORDER, SANPHAM_FK " +
							"FROM ERP_LENHSANXUATDUKIEN " +
							"WHERE THANG >= " + th + " AND THANG <= 12 AND NAM = " + n + "" +
							"AND SANPHAM_FK = '" + spId + "'  " +
							"GROUP BY SANPHAM_FK " +
						")PLNORDER ON PLNORDER.SANPHAM_FK = KHO_SP.SANPHAM_FK " +
						"LEFT JOIN( " +
							"SELECT SUM(SOLUONG) AS PROORDER, SANPHAM_FK " + 
							"FROM ERP_LENHSANXUAT " +
							"WHERE SUBSTRING(NGAYDUKIENHT, 0, 5) = " + n + "" + 
							"AND CONVERT(INT, SUBSTRING(NGAYDUKIENHT, 6, 2)) >= " + th + " " +
							"AND CONVERT(INT, SUBSTRING(NGAYDUKIENHT, 6, 2)) <= 12 " +
							"AND SANPHAM_FK = '" + spId + "' AND DONDATHANG_FK IS NULL " +
							"GROUP BY SANPHAM_FK " +
						")PROORDER ON PROORDER.SANPHAM_FK = KHO_SP.SANPHAM_FK " +
						"LEFT JOIN( " +
							"SELECT SUM(TUAN1+TUAN2+TUAN3+TUAN4) AS YEUCAU, SANPHAM_FK " + 
							"FROM ERP_YEUCAUCUNGUNG " +
							"WHERE THANG >= " + th + " AND THANG <= 12 AND NAM = " + n + "" +
							"AND SANPHAM_FK = '" + spId + "' "  +
							"GROUP BY SANPHAM_FK " +
							")YEUCAU ON YEUCAU.KHOTT_FK = KHO_SP.KHOTT_FK AND YEUCAU.SANPHAM_FK = KHO_SP.SANPHAM_FK " +
						"WHERE  KHO_SP.SANPHAM_FK = '" + spId + "' " +
						"GROUP BY SOLUONGSANXUAT, PLNORDER.PLNORDER, PROORDER.PROORDER, YEUCAU.YEUCAU ";
				
				System.out.println("Lay ton dau 1: " + query);
				ResultSet rs = this.db.get(query) ;
				if (rs != null)
				{
					if (rs.next())
						tondau = Double.parseDouble(rs.getString("TONDAU"));
					rs.close();
				}
				
				tondau = tondau + offset; // vi da tru ton cho tat ca yeu cau cua thang dau tien, nen phai cong lai phan yeu cau tru du
				
				query = "SELECT	SOLUONGSANXUAT AS LOTSIZE,	" + 
						"(" + tondau + "- ISNULL(TKANTOAN.TONKHOANTOAN,0)  + ISNULL(PLNORDER.PLNORDER, 0) + ISNULL(PROORDER.PROORDER, 0) - ISNULL(YEUCAU.YEUCAU, 0)) AS TONDAU " +
						"FROM ERP_KHOTT_SANPHAM KHO_SP " +
						"INNER JOIN ERP_SANPHAM_SANXUAT SANXUAT_SP ON SANXUAT_SP.SANPHAM_FK = KHO_SP.SANPHAM_FK " + 
						"LEFT JOIN " +
						"( " +
							"SELECT SUM(SOLUONG) AS PLNORDER, SANPHAM_FK " +
							"FROM ERP_LENHSANXUATDUKIEN " +
							"WHERE THANG >= 1 AND THANG <= " + (thang - 1) + " AND NAM = " + nam + "" +
							"AND SANPHAM_FK = '" + spId + "'  " +
							"GROUP BY SANPHAM_FK " +
						")PLNORDER ON PLNORDER.SANPHAM_FK = KHO_SP.SANPHAM_FK " +
						"LEFT JOIN( " +
							"SELECT SUM(SOLUONG) AS PROORDER, SANPHAM_FK " + 
							"FROM ERP_LENHSANXUAT " +
							"WHERE SUBSTRING(NGAYDUKIENHT, 0, 5) = " + nam + "" + 
							"AND CONVERT(INT, SUBSTRING(NGAYDUKIENHT, 6, 2)) >= 1 "  +
							"AND CONVERT(INT, SUBSTRING(NGAYDUKIENHT, 6, 2)) <= " + (thang - 1) + "" +
							"AND SANPHAM_FK = '" + spId + "' AND DONDATHANG_FK IS NULL " +
							"GROUP BY SANPHAM_FK " +
						")PROORDER ON PROORDER.SANPHAM_FK = KHO_SP.SANPHAM_FK " +
						"LEFT JOIN( " +
							"SELECT SUM(TUAN1+TUAN2+TUAN3+TUAN4) AS YEUCAU, SANPHAM_FK " + 
							"FROM ERP_YEUCAUCUNGUNG " +
							"WHERE THANG >= 1 AND THANG <= " + (thang - 1) + " AND NAM = " + nam + "" +
							"AND SANPHAM_FK = '" + spId + "' "  +
							"GROUP BY SANPHAM_FK " +
						")YEUCAU ON YEUCAU.SANPHAM_FK = KHO_SP.SANPHAM_FK " +
						"LEFT JOIN	" +
						"( " +
						"	SELECT DB_SP.SANPHAM_FK, SUM(DB_SP.TONKHOANTOAN) AS TONKHOANTOAN, DB_SP.THANG, DB_SP.NAM, SUM(YEUCAU.TUAN1) AS TUAN1 " + 
						"	FROM ERP_DUBAO DUBAO " +
						"	INNER JOIN ERP_DUBAOSANPHAM DB_SP ON DB_SP.DUBAO_FK = DUBAO.PK_SEQ " + 
						"	INNER JOIN ERP_YEUCAUCUNGUNG YEUCAU ON YEUCAU.KHOTT_FK = DUBAO.KHO_FK  " +
						"	AND YEUCAU.SANPHAM_FK = DB_SP.SANPHAM_FK AND YEUCAU.THANG = DB_SP.THANG  " +
						"	AND YEUCAU.NAM = DB_SP.NAM " +
						"	INNER JOIN ERP_TAOYEUCAU TAOYEUCAU ON TAOYEUCAU.DUBAO_FK = DUBAO.PK_SEQ " +  
						"	AND TAOYEUCAU.PK_SEQ = YEUCAU.TAOYEUCAU_FK " +
						"	WHERE DB_SP.SANPHAM_FK = '" + spId + "' " + 
						"	AND DB_SP.THANG = " + (thang - 1) + " AND DB_SP.NAM = " + nam + " " +
						"	GROUP BY DB_SP.SANPHAM_FK, DB_SP.THANG, DB_SP.NAM " +
						")TKANTOAN ON TKANTOAN.SANPHAM_FK = KHO_SP.SANPHAM_FK  " +					
						"WHERE KHO_SP.SANPHAM_FK = '" + spId + "'" ;
				
				System.out.println("Lay ton dau 2:" + query);
				rs = this.db.get(query) ;
				rs.next();
				tondau = Double.parseDouble(rs.getString("TONDAU"));
				
				rs.close();
				return tondau;
			}
		}else{
			String ngay;
			String thangstr;
			if (ngaybatdau - 1 < 10){ 
				ngay = "0" + (ngaybatdau - 1);
			}else{ 
				ngay = "" + (ngaybatdau - 1);
			}
			if(thang < 10 ) 
				thangstr = "0" + thang;
			else 
				thangstr = "" + thang;
			
			t = ng/8 + 1;
			
			query =	"SELECT	SOLUONGSANXUAT AS LOTSIZE,	" +
					"(SUM(KHO_SP.AVAILABLE)  + ISNULL(PLNORDER.PLNORDER, 0) + ISNULL(PROORDER.PROORDER, 0) - ISNULL(YEUCAU.YEUCAU, 0)) AS TONDAU " + 
					"FROM ERP_KHOTT_SANPHAM KHO_SP " +
					"INNER JOIN ERP_SANPHAM_SANXUAT SANXUAT_SP ON SANXUAT_SP.SANPHAM_FK = KHO_SP.SANPHAM_FK " + 
					"LEFT JOIN " +
					"( " +
					"	SELECT SUM(SOLUONG) AS PLNORDER, SANPHAM_FK " + 
					"	FROM ERP_LENHSANXUATDUKIEN " +
					"	WHERE  " +
					"	NGAYKETTHUC >= '" + this.getDateTime() + "' AND NGAYKETTHUC <= '" + nam + "-" + thangstr + "-" + ngay + "' " + 	
					"	AND SANPHAM_FK = '" + spId + "' " + 
					"	GROUP BY SANPHAM_FK " +
					")PLNORDER ON PLNORDER.SANPHAM_FK = KHO_SP.SANPHAM_FK " + 
					"LEFT JOIN(  " +
					"	SELECT SUM(SOLUONG) AS PROORDER, SANPHAM_FK " +
					"	FROM ERP_LENHSANXUAT " +
					"	WHERE " + 
					"	NGAYDUKIENHT >= '" + this.getDateTime() + "' AND NGAYDUKIENHT <= '" + nam + "-" + thangstr + "-" + ngay + "' " +
					"	AND SANPHAM_FK = '" + spId + "' AND DONDATHANG_FK IS NULL " +
					"	GROUP BY SANPHAM_FK " +
					")PROORDER ON PROORDER.SANPHAM_FK = KHO_SP.SANPHAM_FK " + 
					"LEFT JOIN( " + 
					"	SELECT SUM(TUAN1 + TUAN2 + TUAN3 + TUAN4) " ;
			if(t > 0){
				query = query  + "- " + t + "*TUAN1 AS YEUCAU " ; 
				
			}else{
				query = query  + "AS YEUCAU " ;
			}
			 			
			query = query		+		"	,SANPHAM_FK " +
						"	FROM ERP_YEUCAUCUNGUNG " +
						"	WHERE THANG >= " + th + " AND THANG <= " + (thang - 1) + " AND NAM = " + nam + " " +					
						"	AND SANPHAM_FK = '" + spId + "' " ;
			if(t > 0){					
				query = query  +	"	GROUP BY SANPHAM_FK, TUAN1 " ;
			}else{
				query = query  +	"	GROUP BY SANPHAM_FK " ;
			}
			query = query +	")YEUCAU ON YEUCAU.SANPHAM_FK = KHO_SP.SANPHAM_FK " + 
							"WHERE KHO_SP.SANPHAM_FK = '" + spId + "' " +
							"GROUP BY SOLUONGSANXUAT, PLNORDER.PLNORDER, PROORDER.PROORDER, YEUCAU.YEUCAU "; 

			System.out.println("Lay ton dau:" + query);
			ResultSet rs = this.db.get(query) ;
			if (rs != null)
			{
				if (rs.next())
					tondau = Double.parseDouble(rs.getString("TONDAU"));
				rs.close();
			}				

			System.out.println("ton dau: " + tondau);
						
			return tondau;
		}
	}catch(java.sql.SQLException e){
		e.printStackTrace();
	}
		return tondau;	
	}
	

	private double getDemand(int ngaybatdau, int thang, int nam, String spId){
		ResultSet rs;
		String th = "" + thang;
		if(thang < 10) th  = "0" + thang;
		
		String query = 	"SELECT (SUM(DEM1) ) AS DEM1, " +
						"		(SUM(DEM2) ) AS DEM2, " +
						"		(SUM(DEM3) ) AS DEM3, " +
						"		(SUM(DEM4) ) AS DEM4, SANPHAM_FK " +
						"FROM " +
						"( " +
						"	SELECT SUM(SOLUONG) AS PRO1, 0 AS DEM1, 0 AS PRO2, 0 AS DEM2, 0 AS PRO3, 0 AS DEM3, 0 AS PRO4, 0 AS DEM4, SANPHAM_FK " +
						"	FROM ERP_LENHSANXUAT " +
						"	WHERE NGAYDUKIENHT >= '" + nam + "-" + th + "-01' AND NGAYDUKIENHT < '" + nam + "-" + th + "-08' AND SANPHAM_FK = '" + spId + "' " +
						"	AND DONDATHANG_FK IS NULL " +	
						"	GROUP BY SANPHAM_FK	" +
			
						"UNION	" +
			
						"SELECT 0 AS PRO1, SUM(TUAN1) AS DEM1, 0 AS PRO2, 0 AS DEM2, 0 AS PRO3, 0 AS DEM3, 0 AS PRO4, 0 AS DEM4, SANPHAM_FK " +
						"FROM ERP_YEUCAUCUNGUNG " +
						"WHERE SANPHAM_FK = '" + spId + "' AND THANG = " + thang + " AND NAM = " + nam + " " +
						"GROUP BY SANPHAM_FK " +	
			
						"UNION " +
			
						"SELECT 0 AS PRO1, 0 AS DEM1, SUM(SOLUONG) AS PRO2, 0 AS DEM2, 0 AS PRO3, 0 AS DEM3, 0 AS PRO4, 0 AS DEM4, SANPHAM_FK " +
						"FROM ERP_LENHSANXUAT " +
						"WHERE NGAYDUKIENHT >= '" + nam + "-" + th + "-08' AND NGAYDUKIENHT < '" + nam + "-" + th + "-15' AND SANPHAM_FK = '" + spId + "' " +
						"	AND DONDATHANG_FK IS NULL " +
						"GROUP BY SANPHAM_FK " +
			
						"UNION " +
						"SELECT 0 AS PRO1, 0 AS DEM1, 0 AS PRO2, SUM(TUAN2) AS DEM2, 0 AS PRO3, 0 AS DEM3, 0 AS PRO4, 0 AS DEM4, SANPHAM_FK " +
						"FROM ERP_YEUCAUCUNGUNG " +
						"WHERE SANPHAM_FK = '" + spId + "' AND THANG = " + thang + " AND NAM = " + nam + " " +
						"GROUP BY SANPHAM_FK " +

						"UNION " +

						"SELECT 0 AS PRO1, 0 AS DEM1, 0 AS PRO2, 0 AS DEM2, SUM(SOLUONG) AS PRO3, 0 AS DEM3,0 AS PRO4, 0 AS DEM4, SANPHAM_FK " +
						"FROM ERP_LENHSANXUAT " +
						"WHERE NGAYDUKIENHT >= '" + nam + "-" + th + "-15' AND NGAYDUKIENHT < '" + nam + "-" + th + "-22' AND SANPHAM_FK = '" + spId + "' " +
						"	AND DONDATHANG_FK IS NULL " +
						"GROUP BY SANPHAM_FK " +
			
						"UNION " +
						"SELECT 0 AS PRO1, 0 AS DEM1, 0 AS PRO2, 0 AS DEM2, 0 AS PRO3, SUM(TUAN3) AS DEM3, 0 AS PRO4, 0 AS DEM4, SANPHAM_FK " +
						"FROM ERP_YEUCAUCUNGUNG " +
						"WHERE SANPHAM_FK = '" + spId + "' AND THANG = " + thang + " AND NAM = " + nam + " " +
						"GROUP BY SANPHAM_FK " +

						"UNION " +

						"SELECT 0 AS PRO1, 0 AS DEM1, 0 AS PRO2, 0 AS DEM2, 0 AS PRO3, 0 AS DEM3, SUM(SOLUONG) AS PRO4, 0 AS DEM4, SANPHAM_FK " +
						"FROM ERP_LENHSANXUAT " +
						"WHERE NGAYDUKIENHT >= '" + nam + "-" + th + "-22' AND NGAYDUKIENHT < '" + nam + "-" + th + "-31' AND SANPHAM_FK = '" + spId + "' " +
						"	AND DONDATHANG_FK IS NULL " +
						"GROUP BY SANPHAM_FK " +

						"UNION " +
						"SELECT 0 AS PRO1, 0 AS DEM1, 0 AS PRO2, 0 AS DEM2, 0 AS PRO3, 0 AS DEM3, 0 AS PRO4, SUM(TUAN4) AS DEM4, SANPHAM_FK " +
						"FROM ERP_YEUCAUCUNGUNG " +
						"WHERE SANPHAM_FK = '" + spId + "' AND THANG = " + thang + " AND NAM = " + nam + " " +
						"GROUP BY SANPHAM_FK " +

						")YEUCAU " +
						"GROUP BY SANPHAM_FK " ;
		
		System.out.println("Yeu cau: " + query);
		rs =  this.db.get(query);
		if (rs != null)
		{
			try{
				if (rs.next())
				{
					if(ngaybatdau == 1)  return Double.parseDouble(rs.getString("DEM1"));
					if(ngaybatdau == 8)  return Double.parseDouble(rs.getString("DEM2"));
					if(ngaybatdau == 15) return Double.parseDouble(rs.getString("DEM3"));
					if(ngaybatdau == 22) return Double.parseDouble(rs.getString("DEM4"));
				}
				rs.close();
			}catch(java.sql.SQLException e){
				e.printStackTrace();
			}
		}		
		return 0;
	}
	
	private double getOffset(int ngaybatdau, int thang, int nam, String spId){
		int tmp = ngaybatdau - 7;
		double offset = 0;
		System.out.println("ngaybd:" + ngaybatdau);
		
		while (tmp > 0){
			offset = offset + this.getDemand(tmp, thang, nam, spId);
			System.out.println("offset inside: " + offset);
			tmp = tmp - 7;
		}
		return offset;
	}
	
	public void createRs() 
	{
//		this.khoRs = this.db.get("select PK_SEQ,TEN,DIACHI  from ERP_KHOTT WHERE TRANGTHAI = '1'" );
		
		this.nhRs = db.get("select pk_seq, ten from nhanhang where trangthai = '1' and congty_fk = " + this.ctyId );
		
		String query = "select pk_seq, ten from chungloai where trangthai = '1'  and congty_fk = " + this.ctyId + " ";
		if(this.nhIds.trim().length() > 0)
			query += " and pk_seq in ( select cl_fk from nhanhang_chungloai where nh_fk = '" + this.nhIds + "' ) ";
		
		//System.out.println("1.CHung loai: " + query);
		this.clRs = this.db.get(query);		
			
		this.CreateRsThang();
		
		if( this.thang != null )
		{
			for(int i = 0; i < this.thang.length ; i++){
				this.thangstr = this.thangstr  + this.thang[i].trim() + "," ;
				
			}
		

			this.thangstr = this.thangstr.substring(0, this.thangstr.length() - 1);
			System.out.println("thangstr: " + this.thangstr);
		}	
		query = "select distinct b.pk_seq as spId, b.ma, b.ten from erp_yeucaucungung a " +
				"inner join erp_sanpham b on a.sanpham_fk = b.pk_seq  " +
				"where b.trangthai = '1' and b.congty_fk = " + this.ctyId + " ";
			
		System.out.println("san pham list: " + query);
			
		if(this.nhIds.trim().length() > 0)
			query += " and b.nhanhang_fk = '" + this.nhIds + "' ";
			
		if(this.clIds.trim().length() > 0)
			query += " and b.chungloai_fk = '" + this.clIds + "' ";
		
		System.out.println("3.San pham: " + query);
		query += " order by b.pk_seq ";
		
		this.spRs = db.get(query);
			
	}
	
	private void CreateRsThang()
	{
		String query = 	"SELECT DISTINCT THANG AS THANG, NAM AS NAM " +
						"FROM ERP_YEUCAUCUNGUNG YC " +
						"INNER JOIN ERP_DUBAO DUBAO ON DUBAO.PK_SEQ = YC.DUBAO_FK " +
						"WHERE DUBAO.CONGTY_FK = " + this.ctyId + " " +
						"ORDER BY NAM ASC, THANG ASC";

		this.thangRs	=	this.db.get(query);
	}
	
	public void DbClose() 
	{
		try 
		{
			if (this.spRs != null)
				this.spRs.close();
			if (this.khoRs != null)
				this.khoRs.close();
			if(this.nhRs != null)
				this.nhRs.close();
			if(this.clRs != null)
				this.clRs.close();
			if(this.thangRs != null)
				this.thangRs.close();
			this.db.shutDown();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
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
						"and SANPHAM_FK in ( select pk_seq from ERP_SANPHAM where TRANGTHAI = '1' )  " +
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
												"AND DH_SP.sanpham_fk in ( select PK_SEQ from ERP_SANPHAM where TRANGTHAI = '1' )     " +
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
					
//					double tondau = rsSp.getDouble("tondau");
					double tbBan = rsSp.getDouble("TBBan");
					double tonantoan = rsSp.getDouble("NGAYTONKHOANTOAN");
//					double soluongSX = rsSp.getDouble("SOLUONGSANXUAT");
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
							if (rsPending != null)
							{
								if(rsPending.next())
								{
									pending = rsPending.getDouble("Pending");
									ttTondau -= pending;
									ttReq += pending;
								}
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
//							double StockReq = 0;
							
							
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
