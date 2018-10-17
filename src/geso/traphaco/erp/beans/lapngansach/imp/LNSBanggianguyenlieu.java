package geso.traphaco.erp.beans.lapngansach.imp;

import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import geso.traphaco.erp.beans.lapngansach.ILNSBanggianguyenlieu;
import geso.traphaco.erp.db.sql.*;
public class LNSBanggianguyenlieu implements ILNSBanggianguyenlieu
{
	private static final long serialVersionUID = -9217977546733610214L;
	String ctyTen;
	String ctyId;
	String userId;
	String userTen;
	String id;
	String ten;
	String trangthai;
	String ngaytao;
	String nguoitao;
	String ngaysua;
	String nguoisua;
	String msg;
	String nam;
	dbutils db;
	String[] spIds;
	
	String[] giamua;
	String[] dv;
	String[][] data;
	int count;
	
	public LNSBanggianguyenlieu(String Id)
	{	
		this.db = new dbutils();
		this.id 		= Id;
		this.ten 		= "";
		this.trangthai 	= "1";
		this.ngaytao 	= "";
		this.nguoitao 	= "";
		this.ngaysua 	= "";
		this.nguoisua 	= "";
		this.msg = "";
		this.nam = "";
		this.count = 0;
		
		
	}
	
	public LNSBanggianguyenlieu()
	{	
		this.db = new dbutils();
		this.id 		= "";
		this.ten 		= "";
		this.trangthai 	= "1";
		this.ngaytao 	= "";
		this.nguoitao 	= "";
		this.ngaysua 	= "";
		this.nguoisua 	= "";
		this.msg = "";
		this.nam = "";
		this.count = 0;
		
		
	}
	
	public String getUserId() 
	{
		return this.userId;
	}
	
	public void setUserId(String userId) 
	{
		this.userId = userId;
		this.userTen = "Nobody";
		ResultSet rs = this.db.get("select ten from nhanvien where pk_seq ='" + this.userId + "'");
		try{
			rs.next();
			this.userTen = rs.getString("ten");
		}catch(java.sql.SQLException e){}
			
	}
	
	public String getUserTen() 
	{
		
		return this.userTen;
	}
	
	public void setUserTen(String userTen) 
	{
		this.userTen = userTen;
	}

	public String getCtyId()
	{
		return this.ctyId;
	}
	
	public void setCtyId(String ctyId)
	{
		this.ctyId = ctyId;
	}
	
	public String getCtyTen()
	{
		return this.ctyTen;
	}
	
	public void setCtyTen(String ctyTen)
	{
		this.ctyTen = ctyTen;
	}
	
	public String getId() 
	{
		return this.id;
	}
	
	public void setId(String id) 
	{
		this.id = id;
		
	}

	public String getTen() 
	{
		return this.ten;
	}

	public void setTen(String ten) 
	{
		this.ten = ten;
	}
	
	public String getNam() 
	{
		return this.nam;
	}

	public void setNam(String nam) 
	{
		this.nam = nam;
	}
	

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}

	public String getTrangthai() 
	{
		return this.trangthai;
	}
	
	public String getNgaytao()
	{
		return this.ngaytao;
		
	}

	public void setNgaytao(String ngaytao)
	{
		this.ngaytao = ngaytao;
	}
	
	public String getNgaysua()
	{
		return this.ngaysua;
		
	}

	public void setNgaysua(String ngaysua)
	{
		this.ngaysua = ngaysua;
	}
	
	public String getNguoitao()
	{
		return this.nguoitao;
	}
	
	public void setNguoitao(String nguoitao)
	{
		this.nguoitao = nguoitao;
	}

	public String getNguoisua()
	{
		return this.nguoisua;
	}

	public void setNguoisua(String nguoisua)
	{
		this.nguoisua = nguoisua;
	}
	
	public String getMessage() 
	{
		return this.msg;
	}
	
	public void setMessage(String msg) 
	{
		this.msg = msg;
	}
	

	public String[] getSpIds() 
	{
		return this.spIds;
	}

	public void setSpIds(String[] spIds) 
	{
		this.spIds = spIds;
	}


	public String[] getDonvi() 
	{
		return this.dv;
	}

	public void setDonvi(String[] dv) 
	{
		this.dv = dv;
	}

	public String[] getGiamua(){
		return this.giamua;
	}
	
	public void setGiamua(String[] giamua){
		this.giamua = giamua;
	}
	

	public boolean CreateBgnguyenlieu(HttpServletRequest request) 
	{	
		try{
			this.db.getConnection().setAutoCommit(false);
		
			String query;
			String nam = request.getParameter("nam");
			query = "insert into ERP_LNSBANGGIANGUYENLIEU(ten, ngaytao, ngaysua, nguoitao, nguoisua, trangthai, tinhtrang, congty_fk, nam) " +
					"values(N'" + this.ten + "','" + this.ngaytao + "','" + this.ngaysua + "','" + this.nguoitao + "', " +
					"'" + this.nguoisua + "', '" + this.trangthai + "', '1', '" + this.ctyId + "', '" + nam + "')";
			System.out.println(query);
				
			if(!this.db.update(query)){				
				this.msg = query;
				db.getConnection().rollback();
				return false;
			}

			ResultSet tmp = this.db.get("select IDENT_CURRENT('ERP_LNSBANGGIANGUYENLIEU') as bgnlId");
			tmp.next();
				
			String bgnlId = tmp.getString("bgnlId");				
			tmp.close();
			//createSpArray();
			this.spIds = request.getParameterValues("spId");
			
			for(int i = 0; i < this.spIds.length; i++){
				for(int j = 0; j < 12; j++){
					String gia = request.getParameter("gia_" + this.spIds[i] + "_" +  j  );
					if (gia.length()==0){
						gia = "0";
					}
							
					gia=gia.replaceAll(",", "");
					
					
					query = "insert into ERP_LNSBGNGUYENLIEU_NGUYENLIEU(bgnguyenlieu_fk, sanpham_fk, ten, giamua, trangthai, thang) " +
							  "values('" + bgnlId + "', '" + this.spIds[i] + "', (SELECT TEN FROM ERP_SANPHAM WHERE PK_SEQ = " + this.spIds[i] + " ), " + gia.replaceAll(",", "") + ",'1', " + (j + 1) + ")";
					System.out.println(query);
				
					if(!this.db.update(query)){				
						this.msg = query;
						db.getConnection().rollback();
						return false;
					}
				}
			}
				
//			}
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		}catch(Exception e){
			this.msg="Khong The Cap Nhat Bang Gia Nay,Vui Long Thu Lai .Loi :" + e.toString();
			this.db.update("rollback");
			return false;
			}
		
		return true ;
	}

	public boolean UpdateBgnguyenlieu(HttpServletRequest request) 
	{
		try{
			this.db.getConnection().setAutoCommit(false);
			String command=	" update ERP_LNSBANGGIANGUYENLIEU set ten = N'" + this.ten + "', ngaysua = '" + this.ngaysua + "', " +
							" nguoisua = '" + this.nguoisua + "' " +
							" where pk_seq = '" + this.id + "'";
			if(!db.update(command)){
				this.msg="Khong The Cap Nhat Bang Gia Nay,Vui Long Thu Lai .Loi :" + command;
				this.db.update("rollback");
				return false;
			}
			this.spIds = request.getParameterValues("spId");

			//createSpArray();
			for(int i = 0; i < this.spIds.length; i++){
				for(int j = 0; j < 12; j++){
					String gia = request.getParameter("gia_" + this.spIds[i] + "_" +  j  );
					if (gia.length()==0){
						gia = "0";
					}

				//System.out.println("Gia Bi Null la :--------"+gia);
						if (gia.length()==0){
							gia = "0";
						}
						gia=gia.replaceAll(",", "");
						if(Double.parseDouble(gia) > 0){
							command =  "update ERP_LNSBGNGUYENLIEU_NGUYENLIEU set GIAMUA = '" + gia.replaceAll(",", "") + "' " +
					 					"where BGNGUYENLIEU_FK = '" + this.id + "' and sanpham_fk = '" + this.spIds[i] + "' and thang = " + (j + 1) + " ";
							System.out.println(command);	 
							if(!this.db.update(command)){
								this.msg = command;
								db.getConnection().rollback();
								return false;
							}
						}
					
				}
			
			}
			this.db.getConnection().commit();
		}catch(Exception  e){
			this.msg="Khong The Cap Nhat Bang Gia Nay,Vui Long Thu Lai .Loi :" + e.toString();
			this.db.update("rollback");
			return false;
			}
		return true;
	}

	public void init()
	{
		String query = "";
	
		if(this.id.length() > 0){
			query = "select ten, trangthai from ERP_LNSBANGGIANGUYENLIEU where pk_seq ='" + this.id + "'";
			ResultSet rs = this.db.get(query);
			try{		
				rs.next();
				this.ten = rs.getString("ten");
				this.trangthai = rs.getString("trangthai");
			
				Statement st = rs.getStatement();
				st.close();		
				rs.close();	
			}
			catch(java.sql.SQLException e){}
		
		//isBlock();
		
		//INIT SAN PHAM
		
			query = "SELECT COUNT(*) AS NUM FROM ( " +
					"SELECT	DISTINCT BGSP.SANPHAM_FK, THANG   " +
					"FROM ERP_LNSBANGGIANGUYENLIEU BG  " +
					"INNER JOIN ERP_LNSBGNGUYENLIEU_NGUYENLIEU BGSP on BG.PK_SEQ = BGSP.BGNGUYENLIEU_FK " +
					"WHERE BG.PK_SEQ = '" + this.id + "' " +
					"AND BG.CONGTY_FK = '" + this.ctyId + "' ) A " ;
		}else{
			
				query =  "SELECT COUNT(*)*12 AS NUM " +
						 "FROM ERP_SANPHAM WHERE TRANGTHAI = 1 AND LOAISANPHAM_FK IN (100000, 100008, 100013) " ;
						 
		
		}
		
		System.out.println("Cau query : " + query);
		
		ResultSet rs = this.db.get(query);
		int count = 0;
		try
		{
			if( rs.next() )
			{
				count = rs.getInt("NUM");
				this.count = count*3 ;
				rs.close();
				
				String[][] data = new String[count*3 ][12];
				System.out.println("COUNT TONG: " + this.count);
				for(int i=0; i < 12; i++)
				{
					if(this.id.length() > 0){
						query =	"SELECT	DISTINCT BGSP.SANPHAM_FK AS SPID, BGSP.TEN AS SPTEN, DVDL.DONVI, BGSP.GIAMUA  " +
								"FROM ERP_LNSBANGGIANGUYENLIEU BG  " +
								"INNER JOIN ERP_LNSBGNGUYENLIEU_NGUYENLIEU BGSP on BG.PK_SEQ = BGSP.BGNGUYENLIEU_FK 	 " +
								"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = BGSP.SANPHAM_FK " +
								"INNER JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ = SP.DVDL_FK " + 
								"WHERE BG.PK_SEQ = '" + this.id + "' AND BGSP.THANG = '" + (i + 1) + "'   " +
								"AND BG.CONGTY_FK = '" + this.ctyId + "' " +
								" ORDER BY SPTEN ";
					}else{
				
						query = "SELECT SP.PK_SEQ AS SPID, SP.TEN AS SPTEN, DVDL.DONVI, 0 AS GIAMUA " +
								"FROM ERP_SANPHAM SP " +
								"INNER JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ = SP.DVDL_FK " + 
								"WHERE SP.TRANGTHAI = 1 AND SP.LOAISANPHAM_FK IN (100000, 100008, 100013) " +
								" ORDER BY SPTEN ";
								
					}
						//System.out.println("INIT SANPHAM THEO THANG: " + query);
					rs = this.db.get(query);
					count = 0;
						
					while(rs.next())
					{
							//count = 0 - THANG, 1 - THANG, 2 - THANG
		
							data[count++][i] = rs.getString("SPID")+ ";" + rs.getString("SPTEN") ;
//							System.out.println("COUNT 2: " + ( count - 1 ) + " -- I: " + i + "  -- DATA: " + data[count-1][i] );
							
							data[count++][i] = rs.getString("DONVI") ;
							
							data[count++][i] = rs.getString("GIAMUA") ;
		
						}
						rs.close();
						System.out.println("Thành công!");
					}		
					
					this.data = data;
				}
		}
		catch(Exception e)
		{
			System.out.println("LOI ROI : " + e.getMessage());
			e.printStackTrace();
		}
		
	}

	public void closeDB(){
		this.db.shutDown();
	
	}
	

	public void Chot(){
		String query = "UPDATE ERP_LNSBANGGIABAN SET TINHTRANG ='2' WHERE PK_SEQ = '" + this.id + "'";
		
		this.db.update(query);
	}
	
	public String[][] getData() {
		return this.data;
	}
	
	public void setData(String[][] data) {
		this.data = data;
	}
	
	public int getCount() {
		
		return this.count;
	}

	
	public void setCount(int count) 
	{
		
		this.count = count;
		
	}
	public String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
}


