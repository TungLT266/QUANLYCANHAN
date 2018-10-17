package geso.traphaco.erp.beans.banggiaban.imp;

import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import geso.traphaco.erp.beans.banggiaban.IBanggiaban;
import geso.traphaco.erp.db.sql.*;
public class Banggiaban implements IBanggiaban
{
	private static final long serialVersionUID = -9217977546733610214L;
	String ctyTen;
	String ctyId;
	String userId;
	String userTen;
	String id;
	String ten;
	String dvkd;
	String dvkdId;
	String kenh;
	String kenhId;
	String trangthai;
	String ngaytao;
	String nguoitao;
	String ngaysua;
	String nguoisua;
	String msg;
	String block;
	dbutils db;
	String maspstr;
	ResultSet dvkdIds;
	ResultSet kenhIds;
	String[] spIds;
	String[] masp;
	String[] tensp;
	String[] tensp2;
	
	ResultSet sanphamlist;
	ResultSet newsplist;
	String[] giaban;
	String[] giamoi;
	String[] dv;
	String[] tthai; 
	ResultSet LspRs;
	String lspstr;
	
	
	public Banggiaban(String[] param)
	{
		this.db = new dbutils();
		this.id 		= param[0];
		this.ten 		= param[1];
		this.dvkd 		= param[2];
		this.kenh		= param[3];
		this.trangthai 	= param[4];
		this.ngaytao 	= param[5];
		this.nguoitao 	= param[6];
		this.ngaysua 	= param[7];
		this.nguoisua 	= param[8];
		this.msg = "";
		this.dvkdId = "";
		this.kenhId = "";
		createRS();
	}
	
	public Banggiaban()
	{	
		this.db = new dbutils();
		this.id 		= "";
		this.ten 		= "";
		this.dvkd 		= "";
		this.kenh		= "";
		this.trangthai 	= "1";
		this.ngaytao 	= "";
		this.nguoitao 	= "";
		this.ngaysua 	= "";
		this.nguoisua 	= "";
		this.msg = "";
		this.block = "0";
		this.dvkdId = "";
		this.kenhId = "";
		createRS();
		
	}
	
	public Banggiaban(String ctyId)
	{	
		this.db = new dbutils();
		this.id 		= "";
		this.ctyId = ctyId;
		this.ten 		= "";
		this.dvkd 		= "";
		this.kenh		= "";
		this.trangthai 	= "1";
		this.ngaytao 	= "";
		this.nguoitao 	= "";
		this.ngaysua 	= "";
		this.nguoisua 	= "";
		this.msg = "";
		this.block = "0";
		this.dvkdId = "";
		this.kenhId = "";
		createRS();
		
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
			if (rs != null)
			{
				if (rs.next())
					this.userTen = rs.getString("ten");
				rs.close();
			}
		}catch(java.sql.SQLException e){
			e.printStackTrace();
		}
			
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
		this.isBlock();
	}

	public String getTen() 
	{
		return this.ten;
	}

	public void setTen(String ten) 
	{
		this.ten = ten;
	}
	
	public String getDvkdId() 
	{
		return this.dvkdId;
	}

	public void setDvkdId(String dvkdId) 
	{
		this.dvkdId = dvkdId;
	}
	
	public String getDvkd() 
	{
		return this.dvkd;
	}
	
	public void setDvkd(String dvkd) 
	{
		this.dvkd = dvkd;
	}

	public String getKenhId() 
	{
		return this.kenhId;
	}

	public void setKenhId(String kenhId) 
	{
		this.kenhId = kenhId;
	}
	
	public String getKenh() 
	{
		return this.kenh;
	}
	
	public void setKenh(String kenh) 
	{
		this.kenh = kenh;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}

	public String getTrangthai() 
	{
		return this.trangthai;
	}
	
	public void setBlock(String block) 
	{
		this.block = block;
	}

	public String getBlock() 
	{
		return this.block;
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
	
	public ResultSet getDvkdIds() 
	{
		return this.dvkdIds;
	}

	public void setDvkdIds(ResultSet dvkdIds) 
	{
		this.dvkdIds = dvkdIds;
	}

	public ResultSet getKenhIds() 
	{
		return this.kenhIds;
	}

	public void setKenhIds(ResultSet kenhIds) 
	{
		this.kenhIds = kenhIds;
	}

	public String[] getSpIds() 
	{
		return this.spIds;
	}

	public void setSpIds(String[] spIds) 
	{
		this.spIds = spIds;
	}

	public String[] getMasp() 
	{
		return this.masp;
	}

	public void setMasp(String[] masp) 
	{
		this.masp = masp;
	}

	public String[] getTensp() 
	{
		return this.tensp;
	}

	public void setTensp(String[] tensp) 
	{
		this.tensp = tensp;
	}

	public String[] getTensp2() 
	{
		return this.tensp2;
	}

	public void setTensp2(String[] tensp2) 
	{
		this.tensp2 = tensp2;
	}

	public String[] getTthai() 
	{
		return this.tthai;
	}

	public void setTthai(String[] tthai) 
	{
		this.tthai = tthai;
	}

	public String[] getDonvi() 
	{
		return this.dv;
	}

	public void setDonvi(String[] dv) 
	{
		this.dv = dv;
	}

	public String getMaspstr() 
	{
		return this.maspstr;
	}
	
	public void setMaspstr(String maspstr) 
	{
		this.maspstr = maspstr;
	}
	
	public ResultSet getSanPhamList() 
	{
		return this.sanphamlist;
	}

	public void setSanPhamList(ResultSet sanphamlist) 
	{
		this.sanphamlist = sanphamlist;
	}

	public ResultSet getNewSanPhamList() 
	{
		return this.newsplist;
	}

	public void setNewSanPhamList(ResultSet newsplist) 
	{
		this.newsplist = newsplist;
	}

	public String[] getGiaban(){
		return this.giaban;
	}
	
	public void setGiaban(String[] giaban){
		this.giaban = giaban;
	}
	
	public String[] getGiamoi(){
		return this.giamoi;
	}
	
	public void setGiamoi(String[] giamoi){
		this.giamoi = giamoi;
	}

	public boolean CreateBgban(HttpServletRequest request) 
	{	
		try{
			this.db.getConnection().setAutoCommit(false);
		
			String query;
		
				String command = "insert into ERP_BANGGIABAN(ten, ngaytao, ngaysua, nguoitao, nguoisua, dvkd_fk, kenh_fk, trangthai, congty_fk) " +
								"values(N'" + this.ten + "','" + this.ngaytao + "','" + this.ngaysua + "','" + this.nguoitao + "', " +
										"'" + this.nguoisua + "','" + this.dvkdId + "','" + this.kenhId + "', '" + this.trangthai + "', '" + this.ctyId + "')";

				if(!this.db.update(command)){				
					this.msg = command;
					db.getConnection().rollback();
					return false;
				}

				ResultSet tmp = this.db.get("select IDENT_CURRENT('ERP_BANGGIABAN') as bgbanId");
				tmp.next();
				
				String bgbanId = tmp.getString("bgbanId");				
				
				createSpArray();
				for(int i = 0; i < this.spIds.length; i++){
					String gia = request.getParameter("gia" + this.spIds[i]);
					if (gia.length()==0){
						gia = "0";
					}
					
					String trangthai = request.getParameter("chbox" + this.spIds[i]);
					if (trangthai != null){
						trangthai = "1";
					}else{
						trangthai = "0";
					}
					if (gia.equals("0"))
						trangthai="0";
					
					command = "insert into ERP_BGBAN_SANPHAM(bgban_fk, sanpham_fk, ten, giaban, trangthai, giamoi) " +
							 "values('" + bgbanId + "', '" + this.spIds[i] + "', '" + this.tensp2[i] + "', '" + gia + "','" + trangthai + "', '" + gia + "')";
					if(!this.db.update(command)){				
						this.msg = command;
						db.getConnection().rollback();
						return false;
					}
				}
				tmp.close();
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

	public boolean UpdateBgban(HttpServletRequest request) 
	{
		try{
			this.db.getConnection().setAutoCommit(false);
			String command=	"update ERP_BANGGIABAN set ten = N'" + this.ten + "', ngaysua = '" + this.ngaysua + "', nguoisua = '" + this.nguoisua + "', " +
							"trangthai = '" + this.trangthai + "', " +
							"dvkd_fk='" + this.dvkdId + "', kenh_fk = '"+ this.kenhId + "' where pk_seq = '" + this.id + "'";
			if(!db.update(command)){
				this.msg="Khong The Cap Nhat Bang Gia Nay,Vui Long Thu Lai .Loi :" + command;
				this.db.update("rollback");
				return false;
			}
			createSpArray();
			for(int i = 0; i < this.spIds.length; i++){
				String gia = request.getParameter("gia" + this.spIds[i]);
				String ten = request.getParameter("ten" + this.spIds[i]);
				//System.out.println("Gia Bi Null la :--------"+gia);
				if (gia.length()==0){
					gia = "0";
				}
				
				String trangthai = request.getParameter("chbox" + this.spIds[i]);
				if (trangthai != null){
					trangthai = "1";
				}else{
					trangthai = "0";
				}
				
				if (gia.equals("0"))
					trangthai="0";
				
				 command = "insert into ERP_BGBAN_SANPHAM(bgban_fk, sanpham_fk, ten, giaban, trangthai, giamoi) " +
				 		  "values('" + this.id + "', '" + this.spIds[i] + "', '" + ten + "', '" + gia + "','" + trangthai + "', '" + gia + "')";
				 System.out.println(command);
				if(!this.db.update(command)){				
					 command = "update ERP_BGBAN_SANPHAM set GIABAN = '" + gia + "', trangthai = '" + trangthai + "', ten = '" + ten + "' where BGBAN_FK = '" + this.id + "' and sanpham_fk = '" + this.spIds[i] + "'";
					 System.out.println(command);	 
					 if(!this.db.update(command)){
						 this.msg = command;
						 db.getConnection().rollback();
						 return false;
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


	public boolean ChangeBgban(HttpServletRequest request) 
	{
		try{
			this.db.getConnection().setAutoCommit(false);
			String command=	"update ERP_BANGGIABAN set ten = N'" + this.ten + "', ngaysua = '" + this.ngaysua + "', " +
							"nguoisua = '" + this.nguoisua + "', trangthai = '" + this.trangthai + "' where pk_seq = '" + this.id + "'";
			
			System.out.println("command1:" + command);
			
			if(!db.update(command)){
				this.msg="Khong The Cap Nhat Bang Gia Nay,Vui Long Thu Lai .Loi :" + command;
				this.db.update("rollback");
				return false;
			}
			createSpArray();

			command = 	"INSERT INTO ERP_SUAGIABAN (BGBAN_FK, NGAYTAO, NGUOITAO, TINHTRANG) " +
						"VALUES ( '" + this.id + "', '" + this.getDateTime() + "', '" + this.userId + "', '1')";
			System.out.println("command2:" + command);
			String suagiaId = "";
			
			if(this.db.update(command)){

				ResultSet tmp = this.db.get("select IDENT_CURRENT('ERP_SUAGIABAN') as suagiaId");
				try {
					if (tmp != null)
					{
						if (tmp.next())
						{
							suagiaId = tmp.getString("suagiaId");
						}
							System.out.println("SuagiaId " + suagiaId) ;
						tmp.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			
			int count = 0;
			for(int i = 0; i < this.spIds.length; i++){
				String giacu = request.getParameter("giacu" + this.spIds[i]);
				String giamoi = request.getParameter("giamoi" + this.spIds[i]);
				String tenmoi = request.getParameter("ten" + this.spIds[i]);
				
				System.out.println(tenmoi);
				if (giamoi.length()==0){
					giamoi = "0";
				}
				
				String trangthai = request.getParameter("chbox" + this.spIds[i]);
				if (trangthai != null){
					trangthai = "1";
				}else{
					trangthai = "0";
				}
				
				if (giamoi.equals("0") & trangthai.equals("1"))	trangthai="0";
				
				if(trangthai.equals("1")){ // chon ban va co gia moi > 0 
					if (!giacu.equals(giamoi) & !giamoi.equals("0")){
						count++;
						command = 	"insert into ERP_BGBAN_SANPHAM (BGBAN_FK, SANPHAM_FK, GIABAN, TRANGTHAI, GIAMOI) " +
 						"values('" + this.id + "', '" + this.spIds[i] + "', '" + giacu + "','" + trangthai + "', '" + giamoi + "' )";
						
						System.out.println("command3:" + command);
						
						if(!this.db.update(command)){				
							command = 	"update ERP_BGBAN_SANPHAM set GIABAN = '" + giacu + "', giamoi = '" + giamoi + "',  " +
							"trangthai = '" + trangthai + "' where BGBAN_FK = '" + this.id + "' " +
							"and sanpham_fk = '" + this.spIds[i] + "'";
				
							System.out.println("command4:" + command);
							
							if(!this.db.update(command)){
								this.msg = command;
								return false;
							}
						}					
						
						command =  	"INSERT INTO ERP_SUABGBAN_SANPHAM (SUABGBAN_FK, SANPHAM_FK, GIABANCU, GIABANMOI) " +
									"VALUES('" + suagiaId + "', '" + this.spIds[i] + "', '"+ giacu + "', '" + giamoi + "')";
						
						System.out.println("command5:" + command);
						this.db.update(command);		
						
						
					}else if(!giamoi.equals("0")){
						command = 	"update ERP_BGBAN_SANPHAM set TEN = N'" + tenmoi + "' where BGBAN_FK = '" + this.id + "' " +
									"and sanpham_fk = '" + this.spIds[i] + "'";
			
						System.out.println("command4:" + command);
						
						if(!this.db.update(command)){
							this.msg = command;
							return false;
						}
					
					}else if(giamoi.equals("0")){
						this.msg = "Vui lòng định giá cho sản phẩm được chọn bán";
						return false;
					}
					
				}
			
			}

			if(count == 0) {
				this.db.update("DELETE FROM ERP_SUAGIABAN WHERE PK_SEQ = '" + suagiaId + "'");
				System.out.println("I am here");
			}

			this.db.getConnection().commit();
		}catch(Exception  e){
			this.msg="Khong The Cap Nhat Bang Gia Nay,Vui Long Thu Lai .Loi :" + e.toString();
			this.db.update("rollback");
			return false;
			}
		return true;
	}

	private void createDvkdRS()
	{  				
		//this.dvkdIds  =  this.db.get("select donvikinhdoanh as dvkd, pk_seq as dvkdId from donvikinhdoanh where trangthai='1'");
		
		String query = "select distinct a.donvikinhdoanh as dvkd, a.pk_seq as dvkdId from donvikinhdoanh a," +
					 "erp_congty_dvkd b where a.pk_seq = b.DVKD_fk and b.checked ='1' and a.trangthai = '1' and b.congty_fk = '" + this.ctyId + "' " +
					 "order by a.donvikinhdoanh";
		
		System.out.println("Lay DVKD: " + query);
		
		this.dvkdIds  =  this.db.get(query);
	}

	private void createKenhRS(){  				
		this.kenhIds  =  this.db.get("select diengiai as kenh, pk_seq as kenhId from kenhbanhang where trangthai='1'");
	}

	private void createSpRS(){
		if (this.id.trim().length()>0){
			if (this.dvkdId.length()==0){			
				ResultSet rs = db.get("select dvkd_fk as dvkdId, ten, kenh_fk as kenhId, trangthai from ERP_BANGGIABAN where pk_seq = '" + this.id + "'");
				try{
					if (rs != null)
					{
						if (rs.next())
						{
							this.dvkdId = rs.getString("dvkdId");
							this.kenhId = rs.getString("kenhId");
							this.ten = rs.getString("ten");
							this.trangthai = rs.getString("trangthai");
						}
						rs.close();
					}
				}catch(java.sql.SQLException e){
					e.printStackTrace();
				}
				
			}
			
			this.sanphamlist = this.db.get( "select a.pk_seq as id, a.ma, a.ten, c.ten as ten2, c.GIABAN, c.GIAMOI " +
											"from ERP_SanPham a, donvikinhdoanh b, ERP_BGBAN_SANPHAM c, " +
											"ERP_BANGGIABAN d where a.pk_seq = c.sanpham_fk and b.pk_seq= d.dvkd_fk " +
											"and c.BGBAN_FK = d.pk_seq and d.pk_seq = '"+ this.id + "' order by ma");

			this.newsplist = this.db.get("select a.pk_seq as id, a.ma, a.ten from ERP_SanPham a, donvikinhdoanh b " +
										 "where a.dvkd_fk = b.pk_seq and b.pk_seq = '"+ this.dvkdId + "' " +
										 "and a.pk_seq not in " +
										 "(select a.pk_seq as id from ERP_SanPham a, donvikinhdoanh b, ERP_BGBAN_SANPHAM c, " +
										 "ERP_BANGGIABAN d where a.pk_seq = c.sanpham_fk and b.pk_seq= d.dvkd_fk and c.BGBAN_FK = d.pk_seq " +
										 "and d.pk_seq = '"+ this.id + "') order by ma ");
			
		}else{
			if (this.dvkdId.length() > 0){			
				this.sanphamlist = this.db.get( "select a.pk_seq as id, a.ma, a.ten from ERP_SanPham a, donvikinhdoanh b " +
												"where a.dvkd_fk = b.pk_seq and b.pk_seq = '" + this.dvkdId + "' order by ma");
			}else{
				ResultSet rs = this.db.get("select pk_seq as dvkdId from donvikinhdoanh order by pk_seq");
				try{
					rs.next();
					this.dvkdId = rs.getString("dvkdId");
					this.ten = "";
					this.trangthai = "1";
					rs.close();
				}catch(java.sql.SQLException e){
					e.printStackTrace();
				}
				this.sanphamlist = this.db.get("select a.pk_seq as id, a.ma, a.ten from ERP_SanPham a, donvikinhdoanh b " +
											   "where a.dvkd_fk = b.pk_seq and b.pk_seq = '" + this.dvkdId + "' order by ma");
			}
			
		}
	}
	
	private void createSpArray(){
		String query ="";
		Statement st;
		ResultSet rs;
		int count = 0;
		try{
		if (this.id.trim().length()>0){
			if (this.dvkdId.length()==0){			
				rs = this.db.get("select dvkd_fk as dvkdId, ten, kenh_fk as kenhId, trangthai from ERP_BANGGIABAN where pk_seq = '" + this.id + "'");
				try{
					rs.next();
					this.dvkdId = rs.getString("dvkdId");
					this.kenhId = rs.getString("kenhId");
					this.ten = rs.getString("ten");
					this.trangthai = rs.getString("trangthai");
					st = rs.getStatement();
					st.close();
					rs.close();
				}catch(java.sql.SQLException e){}
			}
			//Dem  nhung san pham co trong bang gia
			query = " select count(a.pk_seq) as num from ERP_SanPham a " +
					" inner join ERP_BGBAN_SANPHAM c on a.pk_seq = c.sanpham_fk "+ 
					" inner join ERP_BANGGIABAN d on  c.BGBAN_FK = d.pk_seq "+ 
					" inner join   donvikinhdoanh b on  b.pk_seq= d.dvkd_fk "+
					" left join  donvidoluong e on  a.dvdl_fk = e.pk_seq "+
					" where d.pk_seq = '"+this.id+"'  ";
			System.out.println("query1:" + query);
			
			rs = this.db.get(query);
			rs.next();
			count = Integer.valueOf(rs.getString("num")).intValue();
			st = rs.getStatement();
			st.close();
			rs.close();
			//Dem nhung san pham khong co trong bang gia nhung trang thai=1 sau do cong don san pham lai
			
			query="select count(a.pk_seq) as num from ERP_SanPham a "+ 
			" left join donvidoluong c on  a.dvdl_fk = c.pk_seq "+
			" inner join donvikinhdoanh b on  a.dvkd_fk = b.pk_seq "+  
			" where b.pk_seq = '"+this.dvkdId+"' and   a.trangthai ='1' and "+
			" a.pk_seq not in " +
			"(select a.pk_seq from ERP_SanPham a, donvikinhdoanh b, ERP_BGBAN_SANPHAM c, ERP_BANGGIABAN d"+
			"  where a.pk_seq = c.sanpham_fk and b.pk_seq= d.dvkd_fk and c.BGBAN_FK = d.pk_seq "+ 
			"  and d.pk_seq = '"+this.id+"') ";	
				//System.out.println("Dem Gi Day : " +query);
			
			System.out.println("query2:" + query);
			rs = this.db.get(query);
			rs.next();
			count = count + Integer.valueOf(rs.getString("num")).intValue();
			st = rs.getStatement();
			st.close();			
			rs.close();
			this.spIds = new String[count];
			this.masp = new String[count];
			this.tensp = new String[count];
			this.tensp2 = new String[count];
			this.giaban = new String[count];
			this.giamoi = new String[count];
			this.dv = new String[count];
			this.tthai = new String[count];
			
			query = "select a.pk_seq as id, a.ma, a.ten + ' ' + a.quycach as ten, c.ten as ten2, c.GIABAN, ISNULL(c.GIAMOI, c.GIABAN) AS GIAMOI, c.trangthai, isnull(e.donvi,'Chua xac dinh') as donvi  " +
					"from ERP_SanPham a "+
					" inner join   ERP_BGBAN_SANPHAM c on a.pk_seq = c.sanpham_fk "+
					" inner join  ERP_BANGGIABAN d on  c.BGBAN_FK = d.pk_seq "+ 
					" inner join donvikinhdoanh b on b.pk_seq= d.dvkd_fk "+
					" left join donvidoluong e on e.pk_seq=a.dvdl_fk "+
					" where d.pk_seq = '"+this.id+"' order by a.ma ";
			
			//System.out.println("Bang San Pham :"+query);
			System.out.println("query3:" + query);
			count = 0;
			rs = this.db.get(query);
			maspstr = "";
			while(rs.next()){
				this.spIds[count] = rs.getString("id");
				if (this.maspstr.length()==0){
					this.maspstr = "'" + this.spIds[count] + "'";
				}else{
					this.maspstr = this.maspstr + ",'" + this.spIds[count] + "'";
				}
				
				this.masp[count] = rs.getString("ma");
				this.tensp[count] = rs.getString("ten");
				this.tensp2[count] = rs.getString("ten2");
				this.giaban[count] = rs.getString("GIABAN");
				this.giamoi[count] = rs.getString("GIAMOI");
				this.tthai[count]= rs.getString("trangthai");
				this.dv[count] = rs.getString("donvi");
				count++;
			}
			st = rs.getStatement();
		    st.close();
			rs.close();
			
			query ="select a.pk_seq as id, a.ma, a.ten + ' ' + a.quycach as ten, a.ten2 as ten2, isnull(c.donvi,'Chua xac dinh') as donvi  " +
					"from ERP_SanPham a "+ 
					" left join donvidoluong c on  a.dvdl_fk = c.pk_seq "+
					" inner join donvikinhdoanh b on  a.dvkd_fk = b.pk_seq "+  
					" where b.pk_seq = '"+this.dvkdId+"' and   a.trangthai='1' and "+
					" a.pk_seq not in (select a.pk_seq from ERP_SanPham a, donvikinhdoanh b, ERP_BGBAN_SANPHAM c, ERP_BANGGIABAN d "+
					" where a.pk_seq = c.sanpham_fk and b.pk_seq= d.dvkd_fk and c.BGBAN_FK = d.pk_seq " +
					"  and d.pk_seq = '"+this.id+"') order by a.ma";
			
			//System.out.println("Bang San Pham  khong co trong bang gia :"+query);
			
			System.out.println("query4:" + query);
			rs = this.db.get(query);
			
			while(rs.next()){
				this.spIds[count] = rs.getString("id");
				if (this.maspstr.length()==0){
					this.maspstr = "'" + this.spIds[count] + "'";
				}else{
					this.maspstr = this.maspstr + ",'" + this.spIds[count] + "'";
				}
				
				this.masp[count] = rs.getString("ma");
				this.tensp[count] = rs.getString("ten");
				this.tensp2[count] = rs.getString("ten2");
				this.giaban[count] = "0";
				this.giamoi[count] = "0";
				this.tthai[count] = "0";
				this.dv[count] =  rs.getString("donvi");
				count++;
			}
			//System.out.println(this.maspstr);
			st = rs.getStatement();
			st.close();		
			rs.close();
			
		}else{
			
			if (this.dvkdId.length() == 0){			
				rs = this.db.get("select pk_seq as dvkdId from donvikinhdoanh order by pk_seq");
				rs.next();
				this.dvkdId = rs.getString("dvkdId");
				this.ten = "";
				this.trangthai = "1";

				st = rs.getStatement();
				st.close();		
				rs.close();

			}
			query = "select count(a.pk_seq) as num from ERP_SanPham a inner join "+ 
			" donvikinhdoanh b on a.dvkd_fk = b.pk_seq  "+
 			" left join  donvidoluong c on  a.dvdl_fk = c.pk_seq  where b.pk_seq = '"+this.dvkdId+"'  and a.trangthai='1'";
			System.out.println("query5:" + query);
			rs = this.db.get(query);
			rs.next();
			
			count = Integer.valueOf(rs.getString("num")).intValue();
			this.spIds = new String[count];
			this.masp = new String[count];
			this.tensp = new String[count];
			this.tensp2 = new String[count];			
			this.giaban = new String[count];
			this.giamoi = new String[count];
			this.tthai = new String[count];	
			this.dv = new String[count];

			st = rs.getStatement();
			st.close();		
			rs.close();
			
			query = "select a.pk_seq as id, a.ma, a.ten + ' ' + a.quycach as ten, a.ten2 as ten2, isnull(c.donvi,'Chua xac dinh') as donvi " +
					"from ERP_SanPham a inner join "+ 
					" donvikinhdoanh b on a.dvkd_fk = b.pk_seq  "+
					" left join  donvidoluong c on  a.dvdl_fk = c.pk_seq  where b.pk_seq = '"+this.dvkdId+"' and a.trangthai='1' order by a.ma";

			System.out.println("Query San Pham: " + query);
			rs = this.db.get(query);
			count = 0;
			maspstr = "";
			while(rs.next()){
				this.spIds[count] = rs.getString("id");
				if (this.maspstr.length()==0){
					this.maspstr = "'" + this.spIds[count] + "'";
				}else{
					this.maspstr = this.maspstr + ",'" + this.spIds[count] + "'";
				}
					
				this.masp[count] = rs.getString("ma");
				this.tensp[count] = rs.getString("ten");
				this.tensp2[count] = rs.getString("ten2");
				this.giaban[count] = "0";
				this.giamoi[count] = "0";
				this.tthai[count] = "0";
				this.dv[count]  = rs.getString("donvi");
				count++;
			}
			
			st = rs.getStatement();
			st.close();		
			rs.close();

		}
		}catch(java.sql.SQLException e){}
	}

	public void createRS(){
		createDvkdRS();
		createKenhRS();
		createSpArray();
     	//createSpRS();
		
	}
	
	public void init(){
		String query = "select ten, dvkd_fk as dvkdId, kenh_fk as kenhId, trangthai from ERP_BANGGIABAN where pk_seq ='" + this.id + "'";
		ResultSet rs = this.db.get(query);
		try{		
			rs.next();
			this.ten = rs.getString("ten");
			this.dvkdId = rs.getString("dvkdId");
			this.kenhId = rs.getString("kenhId");
			this.trangthai = rs.getString("trangthai");
			
			Statement st = rs.getStatement();
			st.close();		
			rs.close();
			
		}catch(java.sql.SQLException e){
			e.printStackTrace();
		}
		createRS();
		isBlock();
	}

	private void isBlock(){
		
		String query = "SELECT COUNT(*) AS NUM FROM ERP_SUAGIABAN WHERE TINHTRANG = '1' ";
		if(this.id.trim().length() > 0)
			query += " and BGBAN_FK = '" + this.id + "'  ";
		
		System.out.println("isBlock:" + query);
		
		ResultSet rs = this.db.get(query);
		try{
			if (rs != null)
			{
				rs.next();
				if(!rs.getString("NUM").equals("0")){
					this.block = "1";
				}
				rs.close();
			}
		}catch(java.sql.SQLException e){
			e.printStackTrace();
		}

	}
	
	public void closeDB(){
		try{
			Statement st;
			if(this.sanphamlist != null){
				st = this.sanphamlist.getStatement();
				st.close();		
				this.sanphamlist.close();
			}

			if(this.newsplist != null){
				st = this.newsplist.getStatement();
				st.close();		
				this.newsplist.close();
			}
			
			if(this.dvkdIds != null){
				st = this.dvkdIds.getStatement();
				st.close();		
				this.dvkdIds.close();
			}
			
			if(this.kenhIds != null){
				st = this.kenhIds.getStatement();
				st.close();		
				this.kenhIds.close();
			}
			
			if(this.LspRs != null){
				st = this.LspRs.getStatement();
				st.close();		
				this.LspRs.close();
			}
			if (this.db != null)
				this.db.shutDown();
		}catch(java.sql.SQLException e){
			e.printStackTrace();
		}

	}
	
	private String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public void Chot(){
		String query = "UPDATE ERP_BANGGIABAN SET TINHTRANG ='2' WHERE PK_SEQ = '" + this.id + "'";
		
		this.db.update(query);
	}

	@Override
	public ResultSet getLspRs() {
		// TODO Auto-generated method stub
		return this.LspRs;
	}

	@Override
	public void setLspRs(ResultSet rs) {
		// TODO Auto-generated method stub
		this.LspRs=rs;
	}

	@Override
	public String getLspstr() {
		// TODO Auto-generated method stub
		return this.lspstr;
	}

	@Override
	public void setLspstr(String Lspstr) {
		// TODO Auto-generated method stub
		 this.lspstr=Lspstr;
	}
	
}


