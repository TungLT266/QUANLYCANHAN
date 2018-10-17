package geso.traphaco.erp.beans.bosanpham.imp;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.beans.bosanpham.IBosanpham;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

public class Bosanpham implements IBosanpham {
	String Id;
	String ctyId;
	String ngay;
	String khoId;
	String spId1;
	String loaisp;
	String spTen1;
	String soluong1;
	String tonggiatri1;
	String tongsoluong2;
	String userId;
	String lspId;
	String[] spId2;
	String[] spTen2;
	String[] soluong2;
	String[] tonggiatri2;
	
	String tongsoluong;
	String tongsotien;
	
	String msg;
	dbutils db;

	ResultSet khoRs;
	
	public Bosanpham()
	{   
		this.ctyId = "";
		this.Id ="";
		this.ngay = "";
		this.khoId = "";
		this.spId1 = "";
		this.spTen1 = "";
		this.lspId = "";
		this.soluong1 = "0";
		this.tonggiatri1 = "0";
		this.spId2 = new String[5];
		this.spTen2 = new String[5];
		this.tonggiatri2 = new String[5];
		this.soluong2 = new String[5];
		
		this.tongsoluong = "";
		this.tongsotien = "";
		this.msg="";
		this.db = new dbutils();
	}
	public Bosanpham(String Id)
	{   
		this.Id = Id;
		this.ctyId = "";
		this.ngay = "";
		this.khoId = "";
		this.spId1 = "";
		this.spTen1 = "";
		this.soluong1 = "0";
		this.lspId = "";
		this.tonggiatri1 = "0";		
		this.spId2 = new String[5];
		this.spTen2 = new String[5];
		this.tonggiatri2 = new String[5];
		this.soluong2 = new String[5];
		
		this.tongsoluong = "";
		this.tongsotien = "";
		this.msg="";
		this.db = new dbutils();
	}

	public void setId(String Id) {
		this.Id = Id;
		
	}
	
	public String getId() {
		
		return this.Id;
	}

	public void setCtyId(String ctyId) {
		this.ctyId = ctyId;
		
	}

	public String getCtyId() {
		return this.ctyId;
	}

	public void setNgay(String ngay) {
		this.ngay = ngay;
		
	}

	public String getNgay() {
		return this.ngay;
	}
	
	public void setKhoId(String khoId) {
		this.khoId = khoId;
		
	}

	public String getKhoId() {
		return this.khoId;
	}

	public void setLspId(String lspId) {
		this.lspId = lspId;
		
	}

	public String getLspId() {
		return this.lspId;
	}

	public void setSpId1(String spId1)
	{
		System.out.println("___ID S{: " + spId1);
		if(spId1.length() > 0)
		{
			try
			{
				String query = 	"SELECT SP.PK_SEQ AS SPID, LSP.MA AS LSP, LSP.PK_SEQ AS LSPID " +
								"FROM ERP_SANPHAM SP " +
								"INNER JOIN ERP_LOAISANPHAM LSP ON LSP.PK_SEQ = SP.LOAISANPHAM_FK " +
								"WHERE SP.PK_SEQ = '" + spId1 + "' ";
				
				System.out.println("___LAY ID: " + query);
				ResultSet rs = this.db.get(query);
				if (rs != null)
				{
					if(rs.next())
					{
						this.spId1 = rs.getString("SPID");
						this.loaisp = rs.getString("LSP");
						this.lspId = rs.getString("LSPID");
					}
					rs.close();
				}
			}
			catch(Exception e)
			{
				System.out.println("__Exception lay SP ID: " + e.getMessage());
			}
		}
	}

	public String getSpId1() {
		return this.spId1;
	}
	
	public void setSpTen1(String spTen1) {
		this.spTen1 = spTen1;
		
	}

	public String getSpTen1() {
		return this.spTen1;
	}

	public void setSoluong1(String soluong1) {
		this.soluong1 = soluong1;
	}

	public String getSoluong1() {
		return this.soluong1;
	}

	
	public void setTonggiatri1(String tonggiatri1) {
		this.tonggiatri1 = tonggiatri1;
		
	}

	public String getTonggiatri1() {
		return this.tonggiatri1;
	}
	
	public void setSpId2(String[] spId2) {
		this.spId2 = spId2;
		
	}

	public String[] getSpId2() {
		return this.spId2;
	}
	
	public void setSpTen2(String[] spTen2) {
		this.spTen2 = spTen2;
		
	}

	public String[] getSpTen2() {
		return this.spTen2;
	}

	public void setSoluong2(String[] soluong2) {
		this.soluong2 = soluong2;
		
	}

	public String[] getSoluong2() {
		return this.soluong2;
	}

	
	public void setTonggiatri2(String[] tonggiatri2) {
		this.tonggiatri2 = tonggiatri2;
		
	}

	public String[] getTonggiatri2() {
		return this.tonggiatri2;
	}

	public void setKhoRs(ResultSet khoRs) {
		
		this.khoRs = khoRs;
	}
	
	public ResultSet getKhoRs() {
		
		return this.db.get("SELECT PK_SEQ AS KHOID, TEN AS KHO FROM ERP_KHOTT WHERE TRANGTHAI = 1");
	}
	

	public void setUserId(String userId) {
		
		this.userId = userId;
	}
	
	public String getUserId() {
		
		return this.userId;
	}

	public void init() {
		
		if(this.Id.length()>0)
		{
			try{
				String query = 	"SELECT XHDB.SANPHAM_FK, " +
								"ISNULL(SP.MA, SP.MA) + ' - ' + isnull(SP.TEN,'') + ' ' + CONVERT(VARCHAR, isnull(SP.DAI,0)) + 'X' + CONVERT(VARCHAR,isnull(SP.RONG,0)) + 'X' + CONVERT(VARCHAR,isnull(SP.DINHLUONG,0)) + 'X' + isnull(SP.MAU,'') + ' [' + isnull(DVDL.DONVI, 'NA') + '][' + cast(SP.pk_seq as varchar(10)) + ']' AS TEN, " +
								"XHDB.SOLUONG " +
								"FROM ERP_XUATHANGDEBO XHDB " +
								"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = XHDB.SANPHAM_FK " +
								"INNER JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ = SP.DVDL_FK " +
								"WHERE XHDB.BOSANPHAM_FK = " + this.Id + "";
				
				System.out.println("INIT XUAT HANG BO: " + query);
				ResultSet rs = this.db.get(query);
				int i = 0;
				if (rs != null)
				{
					while(rs.next()){
						this.spId2[i] = rs.getString("SANPHAM_FK");
						this.spTen2[i] = rs.getString("TEN");
						this.soluong2[i] = rs.getString("SOLUONG");
						i++;
					}
					rs.close();
				}
				query = "SELECT BSP.NGAY, BSP.KHO_FK, BSP.SANPHAM_FK, " +
						"ISNULL(SP.MA, SP.MA) + ' - ' + isnull(SP.TEN,'') + ' ' + CONVERT(VARCHAR, isnull(SP.DAI,0)) + 'X' + CONVERT(VARCHAR,isnull(SP.RONG,0)) + 'X' + CONVERT(VARCHAR,isnull(SP.DINHLUONG,0)) + 'X' + isnull(SP.MAU,'') + ' [' + isnull(DVDL.DONVI, 'NA') + '][' + cast(SP.pk_seq as varchar(10)) + ']' AS TEN," +
						" BSP.SOLUONG " +
						"FROM ERP_BOSANPHAM BSP " +
						"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = BSP.SANPHAM_FK " +
						"INNER JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ = SP.DVDL_FK " +
						"WHERE BSP.PK_SEQ = " + Id + "";
				System.out.println("INIT BO: " + query);
				rs = this.db.get(query);
				if (rs != null)
				{
					if (rs.next())
					{
						this.ngay = rs.getString("NGAY");
						this.khoId = rs.getString("KHO_FK");
						this.spTen1 = rs.getString("TEN");
						this.spId1 = rs.getString("SANPHAM_FK");
						this.soluong1 = rs.getString("SOLUONG");
					}
					rs.close();
				}
//				this.tonggiatri1 = rs.getString("TONGGIATRI");
				
				if(i < 4){
					for (int n = i; n < 5; n++){
						this.spId2[n] = "";
						this.spTen2[n] = "";
						this.tonggiatri2[n] = "0"; 
						this.soluong2[n] = "0";
						
					}
				}
			}catch(Exception e){ 
				e.printStackTrace();
			}
		}else{
			for (int n = 0; n < 5; n++){
				this.spId2[n] = "";
				this.spTen2[n] = "";
				this.soluong2[n] = "0";
				
			}
		
		}
		
	}
   
	
	
	public void setmsg(String msg) {
		
		this.msg = msg;
	}
	
	public String getmsg() {
		
		return this.msg;
	}
	
	public void Xoa()
	{
		try{
			
			this.db.getConnection().setAutoCommit(false);

			String query = 	" SELECT XHDB.SANPHAM_FK, XHDB.SOLUONG, XHDB.SOLO, BSP.KHO_FK " +
							" FROM ERP_XUATHANGDEBO XHDB " +
							" INNER JOIN ERP_BOSANPHAM BSP ON BSP.PK_SEQ = XHDB.BOSANPHAM_FK " +
							" WHERE BSP.PK_SEQ = " + this.Id + " ";

			ResultSet rs = this.db.get(query);

			while(rs.next()){
	
				query = " UPDATE ERP_KHOTT_SP_CHITIET SET SOLUONG = SOLUONG - " + rs.getString("SOLUONG") + " , BOOKED = BOOKED - " + rs.getString("SOLUONG") + "  " +
						" WHERE KHOTT_FK = '" + rs.getString("KHO_FK") + "' AND SANPHAM_FK = '" + rs.getString("SANPHAM_FK") + "' " +
						" AND  LTRIM(RTRIM(SOLO)) = '" + rs.getString("SOLO").trim() + "' ";
	
	
				if(!this.db.update(query)){
					this.msg="Error :"+query;
					this.db.getConnection().rollback();
				}
			
				query = "UPDATE ERP_KHOTT_SANPHAM SET SOLUONG = SOLUONG - " + rs.getString("SOLUONG") + " , BOOKED = BOOKED - " + rs.getString("SOLUONG") + "  " +
						"WHERE KHOTT_FK = '" + rs.getString("KHO_FK") + "' AND SANPHAM_FK = '" + rs.getString("SANPHAM_FK") + "' ";
			
				if(!this.db.update(query)){
					this.msg="Error :"+query;
					this.db.getConnection().rollback();
				}
			}
		
			query = "DELETE ERP_XUATHANGDEBO WHERE BOSANPHAM_FK = " + this.Id + "";		
			this.db.update(query);
		
			query = "DELETE ERP_BOSANPHAM WHERE PK_SEQ = " + this.Id + "";
			this.db.update(query);
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			
		}catch(Exception e){
			this.db.update("rollback");
			this.msg=e.getMessage();
			e.printStackTrace();
		}	
	}
	
	public boolean Hoantat()
	{
		try{
			
			this.db.getConnection().setAutoCommit(false);
			String query;

			String ngayhethan = "";
			query = " SELECT XHDB.SANPHAM_FK, XHDB.SOLUONG, XHDB.SOLO, BSP.KHO_FK " +
					" FROM ERP_XUATHANGDEBO XHDB " +
					" INNER JOIN ERP_BOSANPHAM BSP ON BSP.PK_SEQ = XHDB.BOSANPHAM_FK " +
					" WHERE BSP.PK_SEQ = " + this.Id + " ";
			
			ResultSet rs = this.db.get(query);
			
			while(rs.next()){
				
				query = " UPDATE ERP_KHOTT_SP_CHITIET SET SOLUONG = SOLUONG - " + rs.getString("SOLUONG") + " , BOOKED = BOOKED - " + rs.getString("SOLUONG") + "  " +
						" WHERE KHOTT_FK = '" + rs.getString("KHO_FK") + "' AND SANPHAM_FK = '" + rs.getString("SANPHAM_FK") + "' " +
						" AND  LTRIM(RTRIM(SOLO)) = '" + rs.getString("SOLO").trim() + "' ";
				
				
				if(!this.db.update(query)){
					this.msg="Error :"+query;
					this.db.getConnection().rollback();
					return false;
				}
				query = "UPDATE ERP_KHOTT_SANPHAM SET SOLUONG = SOLUONG - " + rs.getString("SOLUONG") + " , BOOKED = BOOKED - " + rs.getString("SOLUONG") + "  " +
						"WHERE KHOTT_FK = '" + rs.getString("KHO_FK") + "' AND SANPHAM_FK = '" + rs.getString("SANPHAM_FK") + "' ";
				if(!this.db.update(query)){
					this.msg="Error :"+query;
					this.db.getConnection().rollback();
					return false;
				}
			}
			
			query = " SELECT KHO_FK, SANPHAM_FK, " +
					" SOLUONG, SOLO, NGAY, '" + ngayhethan + "', '" + this.getDateTime() + "' " +
					" FROM ERP_BOSANPHAM " +
					" WHERE PK_SEQ = " + this.Id + " ";
			rs=this.db.get(query);
			while (rs.next()){
					 query="select sanpham_fk from erp_khott_sanpham where khott_fk="+rs.getString("kho_fk")+" and sanpham_fk="+rs.getString("sanpham_fk");
					 ResultSet rscheck=db.get(query);
					 if(rscheck.next()){
						 query=" update erp_khott_sanpham set soluong=soluong+"+rs.getString("soluong")+",available=available+"+rs.getString("soluong")+"  where khott_fk="+rs.getString("kho_fk")+" and sanpham_fk="+rs.getString("sanpham_fk");
						 
						 
					 }else{
						 query="INSERT INTO ERP_KHOTT_SANPHAM (KHOTT_FK,SANPHAM_FK,GIATON,SOLUONG,BOOKED,AVAILABLE,THANHTIEN) values  " +
						 		"("+rs.getString("kho_fk")+","+rs.getString("sanpham_fk")+", 0 ,"+rs.getString("soluong")+",0,"+rs.getString("soluong")+", 0)";	
						 
						 
					 }
					 
					 if(!this.db.update(query)){
							this.msg="Error :"+query;
							this.db.getConnection().rollback();
							return false;
						}
					 
					 
				
					query="INSERT INTO ERP_KHOTT_SP_CHITIET(KHOTT_FK, SANPHAM_FK, SOLUONG, BOOKED, AVAILABLE, SOLO, BIN, NGAYSANXUAT, NGAYHETHAN, NGAYNHAPKHO) values" +
							" ("+rs.getString("KHO_FK")+","+rs.getString("SANPHAM_FK")+","+rs.getString("SOLUONG")+",0,"+rs.getString("SOLUONG")+",'"+rs.getString("SOLO")+"',100000,'"+rs.getString("NGAY")+"','"+ngayhethan+"','"+this.getDateTime()+"')";
					
					System.out.println(query);
					if(!this.db.update(query)){
						this.msg="Error :"+query;
						this.db.getConnection().rollback();
						return false;
					}
					if (rscheck != null)
						rscheck.close();
			}

			query = "UPDATE ERP_BOSANPHAM SET TRANGTHAI = 1 WHERE PK_SEQ = " + this.Id + "";

			if(!this.db.update(query)){
				this.msg="Error :"+query;
				this.db.getConnection().rollback();
				return false;
			}
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			return true;
			
		}catch(Exception e){
			this.db.update("rollback");
			this.msg=e.getMessage();
			e.printStackTrace();
			return false;
		}
		
	}
	
	public void setSpId2(HttpServletRequest request)
	{
		Utility util = new Utility();
		//ResultSet rs;
		//String query;
		for(int i = 1; i < 5; i++)
		{
			spTen2[i-1] = util.antiSQLInspection(request.getParameter("spTen" + (i + 1)));
			if(spTen2[i-1] == null) 
				spTen2[i-1] = "";
			
	 	    if(spTen2[i-1].indexOf("][") > 0)
	 	    {
	 	    	try
 	    		{
	 	    		spId2[i-1] = spTen2[i-1].substring(spTen2[i-1].indexOf("][") + 2, spTen2[i-1].length() - 1 ).trim();
	 	    		
 	    			/*query = "SELECT PK_SEQ FROM ERP_SANPHAM WHERE PK_SEQ = '" + spId2[i-1] + "' ";
 	    			rs = this.db.get(query);
 	    			rs.next();
 	    			this.spId2[i-1] = rs.getString("PK_SEQ");
 	    			rs.close();*/
 	    		}
 	    		catch(Exception e)
 	    		{
 	    			spId2[i-1] = "";
 	    		}
	 	    }
	 	    else
	 	    {
	 	    	this.spId2[i-1] = "";
	 	    }
		}
	}
	
	public void setSoluong2(HttpServletRequest request){
		Utility util = new Utility();
		
		for(int i = 1; i < 5; i++){
			soluong2[i-1] = util.antiSQLInspection(request.getParameter("soluong" + (i + 1))).trim();
			if(soluong2[i-1] == null) soluong2[i-1] = "0";
		}
	}

	public boolean Luulai()
	{
		String query = "";
		
		String bspId = "";
		
		ResultSet rs;
		try
		{
			db.getConnection().setAutoCommit(false);
			System.out.println("____SP ID: " + this.spId1 + "  --- KHO ID: " + this.khoId + " --- ID: " + this.Id );
			if(this.spId1.length() > 0 && this.khoId.length() > 0)
			{
				// Neu da ton tai nghiep vu bo san pham roi
				if(this.Id.length() > 0)
				{
					bspId = this.Id;
					
					query = "UPDATE ERP_BOSANPHAM SET NGAY = '" + this.ngay + "', KHO_FK = " + this.khoId + "," +
						  	"SANPHAM_FK = " + this.spId1 + ", SOLUONG = " + this.soluong1 + ", " +
							"SOLO = '" + "BSP_" + this.spId1 + "_" + this.getTime() + "', TRANGTHAI = '0' " +
							"WHERE PK_SEQ = " + this.Id + "";
					
					System.out.println("Luu san pham bo: " + query);
					if(!this.db.update(query))
					{
						this.msg = "0.Loi bo SP: " + query;
						db.getConnection().rollback();
						return false;
					}
					
					//BI SAI CHO NAY, TRONG TRUONG HOP DOI SP2 thang bang SP khac, doi KHO_FK
					//for(int i = 0; i < this.spId2.length; i++)
					//{
						//if(this.spId2[i].length() > 0)
						//{
					
							// Buoc 1: huy di luong hang da booked
							query = " SELECT XHDB.SANPHAM_FK, XHDB.SOLUONG, XHDB.SOLO, BSP.KHO_FK " +
									" FROM ERP_XUATHANGDEBO XHDB " +
									" INNER JOIN ERP_BOSANPHAM BSP ON BSP.PK_SEQ = XHDB.BOSANPHAM_FK " +
									" WHERE BOSANPHAM_FK = " + this.Id;
					
							System.out.println("____Lay nhung san pham da booked: " + query);
							rs = this.db.get(query);
							if (rs != null)
							{
								while(rs.next())
								{								  
									query = "UPDATE ERP_KHOTT_SP_CHITIET SET BOOKED = BOOKED - " + rs.getString("SOLUONG") + " , " +
											"AVAILABLE = AVAILABLE + " + rs.getString("SOLUONG") + " " +
											"WHERE KHOTT_FK = '" + rs.getString("KHO_FK") + "' AND SANPHAM_FK = '" + rs.getString("SANPHAM_FK") + "' " +
											"AND SOLO = '" + rs.getString("SOLO") + "' "; 
									System.out.println("____CAP NHAT LAI KHO: " + query);
								
									if(!this.db.update(query))
									{
										this.msg = "1.Không thể cập nhật kho: " + query;
										db.getConnection().rollback();
										return false;
									}
							
									query = "UPDATE ERP_KHOTT_SANPHAM SET BOOKED = BOOKED - " + rs.getString("SOLUONG") + " , " +
											"AVAILABLE = AVAILABLE + " + rs.getString("SOLUONG") + " " +
											"WHERE KHOTT_FK = '" + rs.getString("KHO_FK") + "' AND SANPHAM_FK = '" + rs.getString("SANPHAM_FK") + "' " ;
						
									System.out.println("____UPDATE KHO: " + query);
							
									if(!this.db.update(query))
									{
										this.msg = "2.Không thể cập nhật kho: " + query;
										db.getConnection().rollback();
										return false;
									}
								}
						
								rs.close();
							}							
						//}
					//}
					
					query = "DELETE ERP_XUATHANGDEBO WHERE BOSANPHAM_FK = " + this.Id + " ";
					System.out.println("Xoa ERP_XUATHANGDEBO " + query);
					
					if(!this.db.update(query))
					{
						this.msg = "3.Không thể cập nhật: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
				else
				{
					query = "INSERT INTO ERP_BOSANPHAM(NGAY, KHO_FK, SANPHAM_FK, SOLO, SOLUONG, TRANGTHAI) " +
							"VALUES ('" + this.ngay + "', '" + this.khoId + "', '" + this.spId1 + "', '" + "BSP_" + this.spId1 + "_" + this.getTime() + "', '" + this.soluong1 + "', '0')" ;
					System.out.println("Insert ERP_BOSANPHAM: " + query);
				
					if(!this.db.update(query))
					{
						this.msg = "4.Không thể cập nhật: " + query;
						db.getConnection().rollback();
						return false;
					}
				
					query = "SELECT IDENT_CURRENT('ERP_BOSANPHAM') AS ID";
					rs = this.db.get(query); 
					if (rs != null)
					{
						if (rs.next())
						{
							//Chỗ này làm cho chương trình bị lỗi khi bên dưới lưu không được
							//this.Id = rs.getString("ID");
							bspId = rs.getString("ID");
						}
						rs.close();
					}
				}
				
				System.out.println("----ID LA: " + this.Id);
				if(this.spId2 != null)
				{
					for(int i = 0; i < this.spId2.length; i++)
					{
						if(this.spId2[i].trim().length() > 0 && this.soluong2[i].trim().length() > 0 )
						{
							//CHECK XEM AVAI CO DU KHONG
							query = "select b.MA, sum(AVAILABLE) as TOTAL " +
									"from ERP_KHOTT_SP_CHITIET a inner join ERP_SANPHAM b on a.sanpham_fk = b.pk_seq " +
									"where a.khott_fk = '" + this.khoId + "' and a.sanpham_fk = '" + this.spId2[i] + "' group by b.MA  ";
							double avaiTOTAL = 0;
							String MA = "";
							ResultSet rsAVAI = db.get(query);
							if(rsAVAI.next())
							{
								avaiTOTAL = rsAVAI.getDouble("TOTAL");
								MA = rsAVAI.getString("MA");
							}
							rsAVAI.close();
							
							if(avaiTOTAL < Double.parseDouble(this.soluong2[i]) )
							{
								this.msg = "Tồn kho của sản phẩm '" + MA + "' không đủ dùng làm hàng bó. Vui lòng điều chỉnh số lượng xuống nhỏ hơn hay bằng " + Math.round(avaiTOTAL);
								return false;
							}
							
							//CHECK TON KHO 
							query = "select SANPHAM_FK, isnull(AVAILABLE, 0) as AVAILABLE, SOLO, b.pk_seq as vtId, b.MA as vitri, c.diengiai as khu " +
									"from ERP_KHOTT_SP_CHITIET a left join ERP_VITRIKHO b on a.BIN = b.PK_SEQ " +
									"left join ERP_KHUVUCKHO c on b.KHU_FK = c.pk_seq  " +
									"where a.sanpham_fk = '" + this.spId2[i] + "' and a.AVAILABLE > 0 and a.khoTT_fk = '" + this.khoId + "'  " +
									"order by a.ngayhethan asc, a.AVAILABLE asc";
							
							System.out.println("2.Check soluong san pham: " + query);
							
							ResultSet rsSpDetail = db.get(query);
							if(rsSpDetail != null)
							{
								double tongluong = 0;
								double soluong = Double.parseDouble(this.soluong2[i]);
								boolean exit = false;
								
								while(rsSpDetail.next())
								{
									double avaiD = rsSpDetail.getInt("AVAILABLE");
									String soloD = rsSpDetail.getString("SOLO");

									if(avaiD > 0)
									{
										tongluong += avaiD;
										double slg = 0;
										
										
										if(tongluong < soluong)
											slg = avaiD;
										else
										{
											slg = soluong - (tongluong - avaiD);
											exit = true;
										}
										
										query = "UPDATE ERP_KHOTT_SP_CHITIET SET BOOKED = BOOKED + " + slg + " , AVAILABLE = AVAILABLE - " + slg + " " +
												"WHERE KHOTT_FK = '" + this.khoId + "' AND SANPHAM_FK = '" + this.spId2[i] + "' AND SOLO = '" + soloD + "' "; 
										System.out.println("--- CAP NHAT KHO CT: " + query);
										
										if(!this.db.update(query))
										{
											this.msg = "7.Không thể cập nhật: " + query;
											db.getConnection().rollback();
											return false;
										}
								
										query = "UPDATE ERP_KHOTT_SANPHAM SET BOOKED = BOOKED + " + slg + ", AVAILABLE = AVAILABLE - " + slg + " " +
												"WHERE KHOTT_FK = '" + this.khoId + "' AND SANPHAM_FK = '" + this.spId2[i] + "' " ;
										//System.out.println("--- CAP NHAT KHO TONG: " + query);
										
										if(!this.db.update(query))
										{
											this.msg = "8.Không thể cập nhật: " + query;
											db.getConnection().rollback();
											return false;
										}
										
										//Đủ số lượng, thoát
										if(exit)
										{
											break;
										}
										
									}
								}
								rsSpDetail.close();
							}
							
							query = "INSERT INTO ERP_XUATHANGDEBO(BOSANPHAM_FK, SANPHAM_FK, SOLO, SOLUONG) " +
									"VALUES (" + bspId + ", " + this.spId2[i] + ", 'SPB', '" + this.soluong2[i] + "')";
					
							System.out.println("--- CHEN XUAT HANG: " + query);
						
							if(!this.db.update(query))
							{
								this.msg = "6.Không thể cập nhật: " + query;
								db.getConnection().rollback();
								return false;
							}
						}
					}
				}	
			}
			else
			{	
				db.getConnection().rollback();
				this.msg = "Vui lòng chọn sản phẩm bó";
				return false;
			}
			db.getConnection().commit();
		}
		catch(Exception e)
		{
			this.msg="Lỗi : "+e.getMessage();
			db.update("rollback");
			e.printStackTrace();
			System.out.println("___Exception: " + e.getMessage());
		}
		
		return true;
	}
	
	public String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	

	private String getTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd:HH:mm:SS");	
        Date date = new Date();
        return dateFormat.format(date);	
	}

	
	public String getTotalLuong() {
		
		return this.tongsoluong;
	}
	
	public void setTotalLuong(String totalLuong) {
		
		this.tongsoluong = totalLuong;
	}
	
	public String getTotalTien() {
		
		return this.tongsotien;
	}
	
	public void setTotalTien(String totalTien) {
		
		this.tongsotien = totalTien;
	}

	public void DbClose() {
		
		try{
			if(this.khoRs != null) this.khoRs.close();
			
			db.shutDown();
		}catch(Exception er){
			er.printStackTrace();
		}
	}
	
}
