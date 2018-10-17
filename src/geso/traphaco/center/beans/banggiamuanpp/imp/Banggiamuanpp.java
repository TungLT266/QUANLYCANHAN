package geso.traphaco.center.beans.banggiamuanpp.imp;

import geso.traphaco.center.beans.banggiamuanpp.IBanggiamuanpp;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.http.HttpServletRequest;
import geso.traphaco.center.db.sql.*;
public class Banggiamuanpp implements IBanggiamuanpp
{
	private static final long serialVersionUID = -9217977546733610214L;
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
	dbutils db;
	String maspstr;
	ResultSet dvkdIds;
	ResultSet kenhIds;
	String[] spIds;
	String[] masp;
	String[] tensp;
	
	ResultSet sanphamlist;
	ResultSet newsplist;
	String[] giamuanpp;
	String[] giamuanpptuvanchuyen;
	String[] dv;
	String[] tthai; 
	String[] giathung;
	String[] giathungtvc;
	String[]  quycach;
	String tungay,denngay;
	
	String nppId;
	String nppTen;
	String sitecode;
	
	public Banggiamuanpp(String[] param)
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
		this.tungay="";
		this.denngay="";
		createRS();
	}
	
	public Banggiamuanpp()
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
		this.dvkdId = "";
		this.kenhId = "";
		this.tungay="";
		this.denngay="";
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
			rs.next();
			this.userTen = rs.getString("ten");
		}catch(Exception e){e.printStackTrace();}
			
	}
	
	public String getUserTen() 
	{
		
		return this.userTen;
	}
	
	public void setUserTen(String userTen) 
	{
		this.userTen = userTen;
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

	public String[] getGiamuanpp(){
		return this.giamuanpp;
	}
	
	public void setGiamuanpp(String[] giamuanpp){
		this.giamuanpp = giamuanpp;
	}
	
	public boolean CreateBgmuanpp(HttpServletRequest request) 
	{	
		try
		{
			if(checkExits(db))
			{
				this.msg = "Ngày bắt đầu áp dụng bảng giá không được phép nhỏ hơn từ ngày áp dụng của bảng giá đang có";
				return false;
			}
			
			this.db.getConnection().setAutoCommit(false);
			String command = 
					"INSERT INTO BANGGIAMUANPP(TEN,NGAYTAO,NGAYSUA,NGUOITAO,NGUOISUA,DVKD_FK,KENH_FK,TRANGTHAI,TUNGAY,DENNGAY)" +
					" values(N'" + this.ten + "','" + this.ngaytao + "','" + this.ngaysua + "','" + this.nguoitao + "','" + this.nguoisua + "','" + this.dvkdId + "','" + this.kenhId + "', '" + this.trangthai + "','"+this.tungay+"','"+this.denngay+"')";
			if(!this.db.update(command))
			{	
				this.db.update("rollback");
				this.msg = command;
				return false;
			}
			ResultSet tmp = this.db.get("select IDENT_CURRENT('banggiamuanpp') as bgmuanppId");
			tmp.next();
			
			String bgmuanppId = tmp.getString("bgmuanppId");				
			
			createSpArray();
			for(int i = 0; i < this.spIds.length; i++)
			{
				String gia = request.getParameter("gia" + this.spIds[i]);
				//System.out.println("[Gia]"+gia);
				if (gia.length()==0)
				{
					gia = "0";
				}
				String trangthai = request.getParameter("chbox" + this.spIds[i]);
				if (trangthai != null)
				{
					trangthai = "1";
				}else
				{
					trangthai = "0";
				}
				if (gia.equals("0"))
					trangthai="0";
					
				gia=gia.replaceAll(",", "");
					
				String gia_vanchuyen = request.getParameter("gia_vanchuyen" + this.spIds[i]);
				gia_vanchuyen=gia_vanchuyen.replaceAll(",", "");
				
				float quycach= Float.parseFloat(request.getParameter("quycach" + this.spIds[i]));
				float gia_mua=Float.parseFloat(gia)/quycach;
				float gia_van_chuyen=Float.parseFloat(gia_vanchuyen)/quycach;
				
				command = " insert into bgmuanpp_sanpham (bgmuanpp_fk,sanpham_fk,giamuanpp,trangthai,giamuanpp_tuvc) " +
						  " values('" + bgmuanppId + "', '" + this.spIds[i] + "', '" + gia_mua + "','" + trangthai + "',"+gia_van_chuyen+")";
				
//				System.out.println("Du Lieu : "+command);
				
				if(!this.db.update(command))
				{	
					this.db.update("rollback");
					this.msg = command;
					return false;
				}
			}
			tmp.close();
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		}
		catch(Exception e)
		{
			this.msg="Khong The Cap Nhat Bang Gia Nay,Vui Long Thu Lai .Loi :" + e.toString();
			this.db.update("rollback");
			e.printStackTrace();
			return false;
		}
		return true ;
	}

	public boolean UpdateBgmuanpp(HttpServletRequest request) 
	{
		try
		{
			this.db.getConnection().setAutoCommit(false);
			if(!checkHOPLE())
			{
				this.msg = "Bảng giá đã được áp dụng. Bạn không thể thay đổi.";
				return false;
			}
			
			//CHECK NPP DA CO NGAY AP DUNG TU NGAY ROI THI KHONG DUOC THAY DOI TU NGAY
			//CHECK XEM CO NPP NAO DUOC AP DUNG BANG GIA TRUNG TU NGAY CHUA
			String query = "select a.TUNGAY, c.MA, COUNT(a.PK_SEQ) as soBANGGIA  " +
							"from BANGGIAMUANPP a inner join BANGGIAMUANPP_NPP b on a.PK_SEQ = b.BANGGIAMUANPP_FK  " +
								"inner join NHAPHANPHOI c on b.NPP_FK = c.PK_SEQ  " +
							"where a.PK_SEQ != '" + this.id + "' and a.TUNGAY = ( select tungay from BANGGIAMUANPP where pk_seq = '" + this.id + "' ) and a.KENH_FK = '" + kenhId + "' " +
									"and a.DVKD_FK = '" + dvkdId + "' AND B.NPP_FK IN (SELECT NPP_FK FROM BANGGIAMUANPP_NPP WHERE BANGGIAMUANPP_FK='"+this.id+"' )  " +
							"group by a.TUNGAY, c.MA " +
							"having COUNT(a.PK_SEQ) >= 1 ";
			
			System.out.println("___CHECK NPP SELECTED: " + query);
			ResultSet rsNPP = db.get(query);
			String nppKhongHopLe = "";
			String tungay = "";
			
			if(rsNPP != null)
			{
				while(rsNPP.next())
				{
					nppKhongHopLe += rsNPP.getString("MA") + ",";
					System.out.println("____SO BG KHONG HOP LE: " + nppKhongHopLe);
					
					tungay = rsNPP.getString("TUNGAY");
				}
				rsNPP.close();
			}
			
			System.out.println("____NPP KHONG HOP LE: " + nppKhongHopLe);
			if(nppKhongHopLe.trim().length() > 0)
			{
				this.msg = "Các nhà phân phối ( " + nppKhongHopLe + " ) đã tồn tại trong bảng giá có từ ngày ( " + tungay + " ) ";
				System.out.println("ERROR: " + this.msg);
				db.getConnection().rollback();
				return false;
			}
			
			query = 
				"	 SELECT COUNT(*) AS SoDong "+    
				"	 FROM DONDATHANG DH "+ 
				"	 WHERE EXISTS "+
				"	 ( "+
				"		SELECT * FROM "+
				"		BANGGIAMUANPP A INNER JOIN BANGGIAMUANPP_NPP "+ 
				"		B ON A.PK_SEQ=B.BANGGIAMUANPP_FK  "+
				"		WHERE  B.NPP_FK=DH.NPP_FK "+
				"		AND A.KENH_FK=DH.KBH_FK AND A.DVKD_FK=DH.DVKD_FK "+
				"		AND A.TUNGAY<=DH.NGAYDAT AND DH.TRANGTHAI!=6 "+
				"		AND A.PK_SEQ='"+id+"' AND A.TUNGAY!='"+this.tungay+"' "+
				"	 ) " ;
			System.out.println("[DONDATHANG]" + query);
			int sodong = 0;
			ResultSet rs = db.get(query);
			while (rs.next())
			{
				sodong = rs.getInt("sodong");
			}
			if (rs != null)
				rs.close();
			if (sodong > 0)
			{
				this.msg = "Bảng giá đã được sử dụng trong đơn hàng ,vui lòng kiểm tra lại !";
				return false;
			}
			query = 
			" SELECT COUNT(*) AS SoDong "+    
			"  FROM DONHANG DH "+
			" WHERE EXISTS "+
			"  ( "+
			"	SELECT * FROM  "+
			"	BANGGIAMUANPP A INNER JOIN BANGGIAMUANPP_NPP "+ 
			"	B ON A.PK_SEQ=B.BANGGIAMUANPP_FK "+
			"	WHERE  B.NPP_FK=DH.NPP_FK "+
			"	AND A.KENH_FK=DH.KBH_FK "+
			"	AND A.TUNGAY<=DH.NGAYNHAP AND DH.TRANGTHAI!=2 "+
			"	AND A.PK_SEQ='"+id+"' AND A.TUNGAY!='"+this.tungay+"' "+
			"  ) ";
			
			rs = db.get(query);
			while (rs.next())
			{
				sodong = rs.getInt("sodong");
			}
			if (rs != null)
				rs.close();
			if (sodong > 0)
			{
				this.msg = "Bảng giá đã được sử dụng trong đơn hàng ,vui lòng kiểm tra lại !";
				return false;
			}
			
			
			String command="update banggiamuanpp set ten = N'" + this.ten + "', ngaysua = '" + this.ngaysua + "', nguoisua = '" + this.nguoisua + "', trangthai = '" + this.trangthai + "', dvkd_fk='" + this.dvkdId + "', kenh_fk = '"+ this.kenhId + "',TuNgay='"+this.tungay+"',DenNgay='"+this.denngay+"' where pk_seq = '" + this.id + "'";
			if(!db.update(command))
			{
				this.msg="Khong The Cap Nhat Bang Gia Nay,Vui Long Thu Lai .Loi :" + command;
				this.db.update("rollback");
				return false;
			}
			createSpArray();
			for(int i = 0; i < this.spIds.length; i++)
			{
				String gia = request.getParameter("gia" + this.spIds[i]);
				if (gia.length()==0)
				{
					gia = "0";
				}				
				String trangthai = request.getParameter("chbox" + this.spIds[i]);
				if (trangthai != null)
				{
					trangthai = "1";
				}else
				{
					trangthai = "0";
				}				
				if (gia.equals("0"))
					trangthai="0";
				gia=gia.replaceAll(",","");
				
				String gia_vanchuyen = request.getParameter("gia_vanchuyen" + this.spIds[i]);
				gia_vanchuyen=gia_vanchuyen.replaceAll(",", "");
				
				float quycach= Float.parseFloat(request.getParameter("quycach" + this.spIds[i]));
				float gia_mua=Float.parseFloat(gia)/quycach;
				float gia_van_chuyen=Float.parseFloat(gia_vanchuyen)/quycach;
				command = "insert into bgmuanpp_sanpham  (bgmuanpp_fk,sanpham_fk,giamuanpp,trangthai,giamuanpp_tuvc)  " +
				 		" values('" + this.id + "', '" + this.spIds[i] + "', '" + gia_mua + "','" + trangthai + "',"+gia_van_chuyen+")";
				if(!this.db.update(command))
				{				
					 command = "update bgmuanpp_sanpham set giamuanpp_tuvc="+gia_van_chuyen+",giamuanpp = '" + gia_mua + "', trangthai = '" + trangthai + "' where bgmuanpp_fk = '" + this.id + "' and sanpham_fk = '" + this.spIds[i] + "'";
					 if(!this.db.update(command))
					 {
						 this.db.update("rollback");
						 this.msg = command;
						 return false;
					 }					 
				}
				System.out.println("[Cap nhat gia]"+command);
			}
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		}
		catch(Exception  e)
		{
			this.msg="Khong The Cap Nhat Bang Gia Nay,Vui Long Thu Lai .Loi :" + e.toString();
			this.db.update("rollback");
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	private boolean checkHOPLE()
	{
		String query = " select count(*) as soDong " +
				       " from DONHANG a inner join DONHANG_SANPHAM b on a.pk_seq = b.donhang_fk " +
				       " where a.tungay >= '" + this.tungay + "'" +
				       		" and sanpham_fk in ( select SANPHAM_FK from BGMUANPP_SANPHAM where BGMUANPP_FK = '" + this.id + "' and GIAMUANPP != 0 ) " +
				       		" and a.npp_fk in ( select NPP_FK from BANGGIAMUANPP_NPP where BANGGIAMUANPP_FK = '" + this.id + "'  )  ";
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try
			{
				if(rs.next())
				{
					if( rs.getInt("soDong") >= 1 )
						return false;
				}
				rs.close();
			} 
			catch (Exception e) {e.printStackTrace(); }
		}
		
		return true;
		
	}

	private void createDvkdRS(){  				
		//this.dvkdIds  =  this.db.get("select donvikinhdoanh as dvkd, pk_seq as dvkdId from donvikinhdoanh where trangthai='1'");
		this.dvkdIds  =  this.db.get("select distinct a.donvikinhdoanh as dvkd, a.pk_seq as dvkdId from donvikinhdoanh a,nhacungcap_dvkd b where a.pk_seq = b.DVKD_fk and b.checked ='1' and a.trangthai ='1' order by a.donvikinhdoanh");
	}

	private void createKenhRS(){  				
		this.kenhIds  =  this.db.get("select diengiai as kenh, pk_seq as kenhId from kenhbanhang where trangthai='1'");
	}

	private void createSpRS()
	{
		getNppInfo();
		
		String query = "select d.ma, d.ten, e.donvi as donvi, c.trangthai, b.chietkhau, c.GIAMUANPP, c.GIAMUANPP * (1 - isnull(b.chietkhau, 0) / 100 ) as GIAMUA_SAUCK  " +
						"from BANGGIAMUANPP a inner join BANGGIAMUANPP_NPP b on a.pk_seq = b.BANGGIAMUANPP_FK " +
						"	inner join BGMUANPP_SANPHAM c on a.pk_seq = c.BGMUANPP_FK " +
						"	inner join SANPHAM d on c.sanpham_fk = d.pk_seq " +
						"	inner join DONVIDOLUONG e on d.DVDL_FK = e.pk_seq " +
						"where a.pk_seq = '" + this.id + "' and b.npp_fk = '" + this.nppId + "' ";
		this.sanphamlist = db.get(query);
	}
	
	private void createSpArray(){
		String query ="";
		Statement st;
		ResultSet rs;
		int count = 0;
		try{
		if (this.id.trim().length()>0){
			if (this.dvkdId.length()==0){			
				rs = this.db.get("select dvkd_fk as dvkdId, ten, kenh_fk as kenhId, trangthai from banggiamuanpp where pk_seq = '" + this.id + "'");
				try{
					rs.next();
					this.dvkdId = rs.getString("dvkdId");
					this.kenhId = rs.getString("kenhId");
					this.ten = rs.getString("ten");
					this.trangthai = rs.getString("trangthai");
					st = rs.getStatement();
					st.close();
					rs.close();
				}catch(Exception e){e.printStackTrace();}
			}
			//Dem  nhung san pham co trong bang gia
			query =  " select count(a.pk_seq) as num from sanpham a " +
					 " inner join bgmuanpp_sanpham c on a.pk_seq = c.sanpham_fk "+ 
					 " inner join banggiamuanpp d on  c.bgmuanpp_fk = d.pk_seq "+ 
					 " inner join   donvikinhdoanh b on  b.pk_seq= d.dvkd_fk "+
					 " left join  donvidoluong e on  a.dvdl_fk = e.pk_seq "+
					 " where d.pk_seq = '"+this.id+"'  ";
			System.out.println("Lay gia mua :" +query);
			
			rs = this.db.get(query);
			rs.next();
			count = Integer.valueOf(rs.getString("num")).intValue();
			st = rs.getStatement();
			st.close();
			rs.close();
			//Dem nhung san pham khong co trong bang gia nhung trang thai=1 sau do cong don san pham lai
			
		/*	query="select count(a.pk_seq) as num from sanpham a "+ 
			" left join donvidoluong c on  a.dvdl_fk = c.pk_seq "+
			" inner join donvikinhdoanh b on  a.dvkd_fk = b.pk_seq "+  
			" where b.pk_seq = '"+this.dvkdId+"' and   a.trangthai ='1' and "+
			" a.pk_seq not in ( select a.pk_seq from sanpham a, donvikinhdoanh b, bgmuanpp_sanpham c, banggiamuanpp d"+
			"  where a.pk_seq = c.sanpham_fk and b.pk_seq= d.dvkd_fk and c.bgmuanpp_fk = d.pk_seq "+ 
			"  and d.pk_seq = '"+this.id+"') ";	*/
			

			query =" select count( a.pk_seq) as num  from sanpham a "+ 
			" inner join donvikinhdoanh b on  a.dvkd_fk = b.pk_seq "+  
			" inner  join quycach qc on qc.sanpham_fk=a.pk_seq and qc.dvdl2_fk=100018   "+
			" left join donvidoluong c on  qc.dvdl2_fk = c.pk_seq "+
			" where b.pk_seq = '"+this.dvkdId+"' and   a.trangthai='1' and "+
			" a.pk_seq not in (select a.pk_seq from sanpham a, donvikinhdoanh b, bgmuanpp_sanpham c, banggiamuanpp d "+
			" where a.pk_seq = c.sanpham_fk and b.pk_seq= d.dvkd_fk and c.bgmuanpp_fk = d.pk_seq " +
			"  and d.pk_seq = '"+this.id+"')";
			
			
			System.out.println("\n Dem Gi Day  lay them san pham moi  : " +query);
			
			
			rs = this.db.get(query);
			rs.next();
			count = count + Integer.valueOf(rs.getString("num")).intValue();
			st = rs.getStatement();
			st.close();			
			rs.close();
			this.spIds = new String[count];
			this.masp = new String[count];
			this.tensp = new String[count];
			this.giamuanpp = new String[count];
			this.giamuanpptuvanchuyen=new String[count];
			this.quycach=new String[count];
			this.dv = new String[count];
			this.tthai = new String[count];
			
		 			query = " select a.pk_seq as id, a.ma, a.ten, c.giamuanpp, c.trangthai,ISNULL(GIAMUANPP_TUVC,0) AS GIAMUANPP_TUVC, isnull(e.donvi,'Chua xac dinh') as donvi, isnull( qc.soluong1/nullif(qc.soluong2,0),0) as quycach  from sanpham a "+
							" inner join   bgmuanpp_sanpham c on a.pk_seq = c.sanpham_fk "+
							" inner join  banggiamuanpp d on  c.bgmuanpp_fk = d.pk_seq "+ 
							" inner join donvikinhdoanh b on b.pk_seq= d.dvkd_fk "+
							" inner join quycach qc on qc.sanpham_fk=c.sanpham_fk and qc.dvdl2_fk=100018  " +
							" inner join donvidoluong e on e.pk_seq=qc.dvdl2_fk " +
							" where d.pk_seq = '"+this.id+"' order by a.ma ";
			
			System.out.println("Bang San Pham :"+query);
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
				this.giamuanpp[count] = rs.getString("giamuanpp");
				this.giamuanpptuvanchuyen[count]=rs.getString("giamuanpp_tuvc");
				
				this.tthai[count]= rs.getString("trangthai");
				this.dv[count]  = "THÙNG";
				this.quycach[count]=rs.getString("quycach");
				count++;
			}
			st = rs.getStatement();
		    st.close();
			rs.close();
			
		 
			query =" select a.pk_seq as id, a.ma, a.ten,isnull(c.donvi,'Chua xac dinh') as donvi,isnull( qc.soluong1/nullif(qc.soluong2,0),0) as quycach  from sanpham a "+ 
			" inner join donvikinhdoanh b on  a.dvkd_fk = b.pk_seq "+  
			" inner  join quycach qc on qc.sanpham_fk=a.pk_seq and qc.dvdl2_fk=100018   "+
			" left join donvidoluong c on  qc.dvdl2_fk = c.pk_seq "+
			" where b.pk_seq = '"+this.dvkdId+"' and   a.trangthai='1' and "+
			" a.pk_seq not in (select a.pk_seq from sanpham a, donvikinhdoanh b, bgmuanpp_sanpham c, banggiamuanpp d "+
			" where a.pk_seq = c.sanpham_fk and b.pk_seq= d.dvkd_fk and c.bgmuanpp_fk = d.pk_seq " +
			"  and d.pk_seq = '"+this.id+"')";
			System.out.println(" Bang San Pham  khong co trong bang gia :"+query);
			rs = this.db.get(query);
			while(rs.next())
			{
				this.spIds[count] = rs.getString("id");
				if (this.maspstr.length()==0){
					this.maspstr = "'" + this.spIds[count] + "'";
				}else{
					this.maspstr = this.maspstr + ",'" + this.spIds[count] + "'";
				}
				
				this.masp[count] = rs.getString("ma");
				this.tensp[count] = rs.getString("ten");
				this.giamuanpp[count] = "0";
				this.giamuanpptuvanchuyen[count]="0";
				this.tthai[count] = "0";
				this.dv[count]  = "THÙNG";
				this.quycach[count]=rs.getString("quycach");
				count++;
			}
			System.out.println(this.maspstr);
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
		 
			query = " select count(a.pk_seq) as num  from sanpham a inner join "+ 
			" donvikinhdoanh b on a.dvkd_fk = b.pk_seq  "+
			" inner join quycach qc on qc.sanpham_fk=a.pk_seq and qc.dvdl2_fk=100018   "+
 			" inner join  donvidoluong c on  qc.dvdl2_fk = c.pk_seq  " +
 			" where b.pk_seq = '"+this.dvkdId+"' and a.trangthai='1' ";
			System.out.println(" :" +query);
			rs = this.db.get(query);
			rs.next();
			
			count = Integer.valueOf(rs.getString("num")).intValue();
			this.spIds = new String[count];
			this.masp = new String[count];
			this.tensp = new String[count];
			this.giamuanpp = new String[count];
			this.giamuanpptuvanchuyen=new String[count];
			
			this.tthai = new String[count];	
			this.dv = new String[count];
			this.quycach=new String[count];
			st = rs.getStatement();
			st.close();		
			rs.close();
			
			//query = "select a.pk_seq as id, a.ma, a.ten, c.donvi from sanpham a, donvikinhdoanh b, donvidoluong c where a.dvkd_fk = b.pk_seq and a.dvdl_fk = c.pk_seq and b.pk_seq = '" + this.dvkdId + "' order by a.ma";
			query = " select a.pk_seq as id, a.ma, a.ten, isnull(c.donvi,'Chua xac dinh') as donvi,isnull( qc.soluong1/nullif(qc.soluong2,0),0) as quycach from sanpham a inner join "+ 
			" donvikinhdoanh b on a.dvkd_fk = b.pk_seq  "+
			" inner join quycach qc on qc.sanpham_fk=a.pk_seq and qc.dvdl2_fk=100018   "+
 			" inner join  donvidoluong c on  qc.dvdl2_fk= c.pk_seq  " +
 			" where b.pk_seq = '"+this.dvkdId+"' and a.trangthai='1' ";
			System.out.println(" :" +query);
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
				this.giamuanpp[count] = "0";
				this.giamuanpptuvanchuyen[count]="0";
				this.tthai[count] = "0";
				this.dv[count]  = rs.getString("donvi");
				this.quycach[count]=rs.getString("quycach");
				count++;
			}
			
			st = rs.getStatement();
			st.close();		
			rs.close();

		}
		}catch(Exception e){ 
			
			e.printStackTrace();
			
		}
	}

	public void createRS()
	{
		createDvkdRS();
		createKenhRS();
		//createSpArray();
     	createSpRS();
	}
	
	public void init()
	{
		String query = "select ten, dvkd_fk as dvkdId, kenh_fk as kenhId, trangthai,tungay,denngay from banggiamuanpp where pk_seq ='" + this.id + "'";
		ResultSet rs = this.db.get(query);
		try{		
			rs.next();
			this.ten = rs.getString("ten");
			this.dvkdId = rs.getString("dvkdId");
			this.kenhId = rs.getString("kenhId");
			this.trangthai = rs.getString("trangthai");
			this.tungay = rs.getString("tungay");
			this.denngay = rs.getString("denngay");
				
			rs.close();

		}
		catch(Exception e){e.printStackTrace();}
		
		createRS();
	}

	
	public void closeDB(){
		try{
			
			if(this.sanphamlist != null){
				
				this.sanphamlist.close();
			}

			
			
			this.db.shutDown();
		}catch(Exception e){e.printStackTrace();}
		finally
		{
			this.db.shutDown();
		}
		
	}

	@Override
	public String[] getGiamuanppTuvanchuyen() 
	{
		return this.giamuanpptuvanchuyen;
	}

	@Override
	public void setGiamuanppTuvanchuyen(String[] giamuanppTuvanchuyen) 
	{
		this.giamuanpptuvanchuyen=giamuanppTuvanchuyen;
	}
	

	public String[] getGiathung()
	{
		return giathung;
	}

	public void setGiathung(String[] giathung)
	{
		this.giathung = giathung;
	}

	public String[] getGiathungtvc()
	{
		return giathungtvc;
	}

	public void setGiathungtvc(String[] giathungtvc)
	{
		this.giathungtvc = giathungtvc;
	}


	
	public String[] getQuycach()
	{
		return quycach;
	}

	public void setQuycach(String[] quycach)
	{
		this.quycach = quycach;
	}
	
	public void setDenngay(String denngay) 
	{
		this.denngay = denngay;
	}
	
	public String getTungay() 
	{
		return tungay;
	}

	public void setTungay(String tungay) 
	{
		this.tungay = tungay;
	}

	public String getDenngay() 
	{
		return denngay;
	}
	
	private void getNppInfo()
	{		
		//Phien ban moi
		geso.traphaco.distributor.util.Utility util=new geso.traphaco.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();
		//this.dangnhap = util.getDangNhap();
		this.sitecode=util.getSitecode();
	}
	
	private boolean checkExits(dbutils db) 
	{
		String query="select count(*) as sodong from BANGGIAMUANPP where DVKD_FK="+this.dvkdId+" and KENH_FK="+this.kenhId+" " +
						"AND TUNGAY > '" + this.tungay + "'  ";
		
		if(this.id.length() > 0)
			query+= " and pk_seq != " + this.id +" ";
		
		ResultSet rs = db.get(query);
		int sodong = 0;
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					sodong = rs.getInt("sodong");
					rs.close();
				}
			} catch(Exception e) {e.printStackTrace(); sodong = 0; }
		}
		if(sodong > 0)
			return true;
		return false;

	}
	
}