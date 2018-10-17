package geso.traphaco.center.beans.banggiablc.imp;

import geso.traphaco.center.beans.banggiablc.IBanggiablc;

import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;

import geso.traphaco.center.db.sql.*;
import geso.traphaco.erp.util.UtilitySyn;
public class Banggiablc implements IBanggiablc
{
	private static final long serialVersionUID = -9217977546733610214L;
	String userId;
	String id;
	String ten;
	String dvkd;
	String dvkdId;
	String trangthai;
	String ngaytao;
	String nguoitao;
	String ngaysua;
	String nguoisua;
	String msg;
	String donvi;
	dbutils db;
	ResultSet dvkdIds;
		
	ResultSet sanphamlist;
	ResultSet newsplist;
	String[] giablc; //luu thong tin cac gia ban le chuan thay doi
	String[] quycach;
	String tungay;
	
	public Banggiablc(String[] param)
	{
		this.id 		= param[0];
		this.ten 		= param[1];
		this.dvkd 		= param[2];
		this.trangthai 	= param[3];
		this.ngaytao 	= param[4];
		this.nguoitao 	= param[5];
		this.ngaysua 	= param[6];
		this.nguoisua 	= param[7];
		this.msg = "";
		this.dvkdId = "";
		this.db = new dbutils();
		this.donvi ="";
		createRS();
	}
	
	public Banggiablc()
	{
		this.id 		= "";
		this.ten 		= "";
		this.dvkd 		= "";
		this.trangthai 	= "";
		this.ngaytao 	= "";
		this.nguoitao 	= "";
		this.ngaysua 	= "";
		this.nguoisua 	= "";
		this.msg = "";
		this.dvkdId = "";
		this.tungay="";
		this.db = new dbutils();
		this.donvi = "";
		createRS();
	}

	public String getUserId() 
	{
		return this.userId;
	}
	
	public void setUserId(String userId) 
	{
		this.userId = userId;
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
	
	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}

	public String getTrangthai() 
	{
		return this.trangthai;
	}
	
	public void setDvkd(String dvkd) 
	{
		this.dvkd = dvkd;
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

	public String[] getGiablc() 
	{
		return this.giablc;
	}

	public void setGiablc(String[] giablc) 
	{
		this.giablc = giablc;
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

	public boolean CreateBgblc(HttpServletRequest request) 
	{
	
		try{
			db.getConnection().setAutoCommit(false);
			
			String query = "select count(pk_seq) as count from banggiabanlechuan where dvkd_fk='" + this.dvkdId + "' and tungay='"+this.tungay+"'";
			ResultSet rs = db.get(query);
			rs.next();
			if (!rs.getString("count").equals("0"))
			{
				this.msg = "Bảng giá bán chuẩn đã tồn tại";
				return false;
			}else
			{
				try
				{
					query = "insert into banggiabanlechuan values(N'" + this.ten + "','" + this.ngaytao + "','" + this.ngaysua + "','" + this.nguoitao + "','" + this.nguoisua + "','" + this.dvkdId + "', '1','"+this.tungay+"')";
					System.out.println("1. BGBLC : "+query);
					if (!(db.update(query))){
						this.msg = "Ms 1 "+query;
						db.update("rollback");							
						return false;		
					
					}
					query = "select IDENT_CURRENT('banggiabanlechuan')as bgblcId";				
					rs = db.get(query);
					rs.next();
					this.id = rs.getString("bgblcId");
					System.out.println(rs.getString("bgblcId"));
					createRS();
					rs = this.sanphamlist;				
					if (!(rs == null))
					{
						System.out.println("vao day nay");
						while(rs.next())
						{
							String gia = request.getParameter(rs.getString("id")).trim();
							if (gia.length()==0)
							{
								gia = "0";
							}	
							String quycach = request.getParameter("quycach_"+rs.getString("id")).trim();
							if (quycach.length()==0)
							{
								quycach = "0";
							}
							
							gia=gia.replaceAll(",", "");
							quycach.replaceAll(",", "");
							double  gia_chuan=Double.parseDouble(gia);
							double quy_cach=Double.parseDouble(quycach);
							if(quy_cach>0&&gia_chuan>0)
							{
								System.out.println("[Quycach]"+quycach+"[Gia]"+gia_chuan);
							}
							gia_chuan=gia_chuan/quy_cach;
							String command = "insert into banggiablc_sanpham(SANPHAM_FK,BGBLC_FK,GIABANLECHUAN) values('" + rs.getString("id") + "','" + this.id + "', '" + gia_chuan +"')";				
							System.out.println("2. BGBLC SP : "+command);
							if (!(db.update(command)))
							{
								this.msg = "Ms 1 "+command;
								db.update("rollback");							
								return false;		
							}
						}
					}
			}catch(Exception e){System.out.println("Loi nay : "+e.toString()); 
			
			db.update("rollback");							
			return false;		
			}
				//KHoa sua : bang gia ban le chuan se duoc thuc hien tao cho nha phan phoi,khi tạo ra bang gia ban le chuan cho ca nha phan phoi thuoc don vi kinh doanh nay
				//lay nha phan phoi trong bang NHAPP_NHACC_DONVIKD,neu nha phan phoi co bang gia thuoc dvkd nay thi ko tao nua
				//l
				String sql_getnpp=" select distinct npp_fk as manpp from NHAPP_NHACC_DONVIKD npp_dv "+
						 " inner join nhacungcap_dvkd ncc_dv on ncc_dv.pk_seq=npp_dv.ncc_dvkd_fk "+ 
						 " inner join nhaphanphoi npp on npp_dv.npp_fk= npp.pk_seq "+
						 " where ncc_dv.checked='1' and npp.trangthai='1'  and ncc_dv.dvkd_fk="+this.dvkdId+" and npp.pk_seq not in (select distinct npp_fk from BANGGIABANLENPP where ngaybatdau='"+this.tungay+"' and dvkd_fk="+this.dvkdId+" ) ";
				System.out.println ( "Lay Ra Nhung Nha PP Theo DVDK :"+ sql_getnpp );
				ResultSet rs_getnpp=db.get(sql_getnpp);
				//thuc hien cap nhat vao bang gia
				if(rs_getnpp!=null)
				{
					System.out.println ( "Lay Ra Nhung Nha PP Theo DVDK789 :"+ sql_getnpp );
					while(rs_getnpp.next())
					{
						String sql_insertbangia="insert into BANGGIABANLENPP(ngaybatdau,ten,ngaytao,ngaysua,nguoitao,nguoisua,dvkd_fk,npp_fk,BANGGIABANLECHUAN_FK) "+
						"  values('"+this.tungay+"',N'"+this.ten+"','"+this.ngaytao+"','"+this.ngaysua+"',"+this.nguoitao+","+this.nguoitao+","+this.dvkdId+","+rs_getnpp.getString("manpp")+",'"+this.id+"')";
						System.out.println("4. BANGGIABANLENPP : "+sql_insertbangia);
						if (!(db.update(sql_insertbangia)))
						{
							this.msg = "Ms 2 "+sql_insertbangia;
							db.update("rollback");							
							return false;		
						}
					}
				}
				
				String command=
						"INSERT INTO BGBANLENPP_SANPHAM(BGBANLENPP_FK,SANPHAM_FK,GIABANLENPP,GIABANLECHUAN) "+
						"SELECT    B.PK_SEQ as BGBANLENPP_FK ,A.SANPHAM_FK,A.GIABANLECHUAN,A.GIABANLECHUAN "+
						" FROM BANGGIABLC_SANPHAM A "+ 
						"INNER JOIN "+  
						"( "+
						"	 SELECT PK_SEQ FROM BANGGIABANLENPP "+
						" ) B ON 1=1 "+
						" AND  EXISTS "+ 
						" ( "+ 
						"	SELECT SANPHAM_FK "+ 
						"	FROM BANGGIABANLENPP C INNER JOIN BGBANLENPP_SANPHAM  D ON D.BGBANLENPP_FK=C.PK_SEQ "+ 
						"	 inner join banggiabanlechuan e on e.PK_SEQ=c.BANGGIABANLECHUAN_FK WHERE C.PK_SEQ=B.PK_SEQ AND D.SANPHAM_FK=A.SANPHAM_FK  and e.PK_SEQ= '"+this.id+"'  "+
					    " 	) ";
						if (db.updateReturnInt(command) <=0)
						{
							this.msg = "Loi Xay Ra Tong Qua Trinh Sua "+command;
							db.update("rollback");							
							return false;		
						}
				
				
			}
			if(rs!=null){
			   rs.close();
			}
			db.getConnection().commit();						
			db.getConnection().setAutoCommit(true);	
			
			//SYN QUA DMS
			UtilitySyn.SynData(db, "banggiabanlechuan", "banggiabanlechuan", "PK_SEQ", id, "0", false);
			UtilitySyn.SynData(db, "banggiablc_sanpham", "banggiablc_sanpham", "BGBLC_FK", id, "2", true);
			UtilitySyn.SynData(db, "banggiablc_sanpham", "banggiablc_sanpham", "BGBLC_FK", id, "0", false);
			
		}
		catch(Exception e)
		{
			System.out.println("Loi : "+e.toString());
			db.update("rollback");
		}
		
		return true;
	}

	public boolean UpdateBgblc(HttpServletRequest request) 
	{
		
		try{
			
     		db.getConnection().setAutoCommit(false);
     		String command="";
			if(!db.update("update banggiabanlechuan set tungay='"+this.tungay+"',ten = N'" + this.ten + "', ngaysua = '" + this.ngaysua + "', nguoisua = '" + this.nguoisua + "', trangthai = '" + this.trangthai + "', dvkd_fk='" + this.dvkdId + "' where pk_seq = '" + this.id + "'"))
			{
				db.update("rollback");
				this.msg="Khong The Chinh Sua Bang Gia ,Vui Long Kiem Tra Lai Cac Thong Tin";
				return false;
			}
			
			command="delete from banggiablc_sanpham where bgblc_fk='" + this.id + "'";
			 if(!db.update(command))
			 {
				 this.msg = "Vui lòng liên hệ Admin để kiểm tra "+command;
				db.update("rollback");
				return false;
			 }
			 command = " delete  from bgbanlenpp_sanpham where BGBANLENPP_FK in "+
			 " ( "+
			 "			select PK_SEQ from BANGGIABANLENPP where BANGGIABANLECHUAN_FK='"+this.id+"' "+
			 " )";
			 if(!db.update(command))
			 {
				 this.msg = "Vui lòng liên hệ Admin để kiểm tra "+command;
				db.update("rollback");
				return false;
			 }
			
			String[] spMa = request.getParameterValues("masanpham");
			String[] dongia = request.getParameterValues("dongia");

			if(dongia != null)
			{
				for(int i = 0; i < spMa.length; i++)
				{
					if(dongia[i].trim().length() > 0)
					{
						command = "insert into banggiablc_sanpham(SANPHAM_FK,BGBLC_FK,GIABANLECHUAN) " +
								  "values('" + spMa[i] + "','" + this.id + "', '" + dongia[i].trim().replaceAll(",", "") +"')";				
						System.out.println("2. BGBLC SP : "+command);
						if (!(db.update(command)))
						{
							this.msg = "Vui lòng liên hệ Admin để kiểm tra "+command;
							db.update("rollback");							
							return false;		
						}
					}
				}
			}

			String sql=" delete c from  BANGGIABANLENPP a inner join banggiabanlechuan b 	 "+
						" on a.BANGGIABANLECHUAN_FK=b.PK_SEQ "+
						" inner join BGBANLENPP_SANPHAM c on a.PK_SEQ=c.BGBANLENPP_FK "+
						" where b.PK_SEQ='"+this.id+"'" ;
			if (!db.update(sql))
			{
				this.msg = "Loi Xay Ra Tong Qua Trinh Sua "+sql;
				db.update("rollback");							
				return false;		
			}
			
			 sql="delete a from  BANGGIABANLENPP a inner join banggiabanlechuan b 	"+
				"	 on a.BANGGIABANLECHUAN_FK=b.PK_SEQ where b.PK_SEQ='"+this.id+"' ";
				
				if (!db.update(sql))
				{
					this.msg = "Loi Xay Ra Tong Qua Trinh Sua "+sql;
					db.update("rollback");							
					return false;		
				}
			
			String sql_getnpp=" select distinct npp_fk as manpp from NHAPP_NHACC_DONVIKD npp_dv "+
					 " inner join nhacungcap_dvkd ncc_dv on ncc_dv.pk_seq=npp_dv.ncc_dvkd_fk "+ 
					 " inner join nhaphanphoi npp on npp_dv.npp_fk= npp.pk_seq "+
					 " where ncc_dv.checked='1' and npp.trangthai='1' and  ncc_dv.dvkd_fk="+this.dvkdId+" and npp.pk_seq not in (select distinct npp_fk from BANGGIABANLENPP where  ngaybatdau='"+this.tungay+"' and dvkd_fk="+this.dvkdId+" ) ";
			System.out.println ( "Lay Ra Nhung Nha PP Theo DVDK :"+ sql_getnpp );
			ResultSet rs_getnpp=db.get(sql_getnpp);
			//thuc hien cap nhat vao bang gia
			if(rs_getnpp!=null)
			{
				while(rs_getnpp.next())
				{
					command="insert into BANGGIABANLENPP(ngaybatdau,ten,ngaytao,ngaysua,nguoitao,nguoisua,dvkd_fk,npp_fk,BANGGIABANLECHUAN_FK) "+
					"  values('"+this.tungay+"',N'"+this.ten+"','"+this.ngaytao+"','"+this.ngaysua+"',"+this.nguoitao+","+this.nguoitao+","+this.dvkdId+","+rs_getnpp.getString("manpp")+",'"+this.id+"')";
					System.out.println("4. BANGGIABANLENPP : "+command);
					if (!(db.update(command)))
					{
						this.msg = "Vui lòng liên hệ Admin để kiểm tra "+command;
						db.update("rollback");							
						return false;		
					}
				}
			}
			command=
			"INSERT INTO BGBANLENPP_SANPHAM(BGBANLENPP_FK,SANPHAM_FK,GIABANLENPP,GIABANLECHUAN) "+
			"SELECT    B.PK_SEQ as BGBANLENPP_FK ,A.SANPHAM_FK,A.GIABANLECHUAN,A.GIABANLECHUAN "+
			" FROM BANGGIABLC_SANPHAM A "+ 
			"INNER JOIN "+  
			"( "+
			"	 SELECT PK_SEQ FROM BANGGIABANLENPP "+
			" ) B ON 1=1 "+
			" AND  EXISTS "+ 
			" ( "+ 
			"	SELECT SANPHAM_FK "+ 
			"	FROM BANGGIABANLENPP C INNER JOIN BGBANLENPP_SANPHAM  D ON D.BGBANLENPP_FK=C.PK_SEQ "+ 
			"	 inner join banggiabanlechuan e on e.PK_SEQ=c.BANGGIABANLECHUAN_FK WHERE C.PK_SEQ=B.PK_SEQ AND D.SANPHAM_FK=A.SANPHAM_FK  and e.PK_SEQ= '"+this.id+"'  "+
		    " 	) ";
			if (!db.update(command) )
			{
				this.msg = "Loi Xay Ra Tong Qua Trinh Sua "+command;
				db.update("rollback");							
				return false;		
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
			//SYN QUA DMS
			UtilitySyn.SynData(db, "banggiabanlechuan", "banggiabanlechuan", "PK_SEQ", id, "1", false);
			UtilitySyn.SynData(db, "banggiablc_sanpham", "banggiablc_sanpham", "BGBLC_FK", id, "2", true);
			UtilitySyn.SynData(db, "banggiablc_sanpham", "banggiablc_sanpham", "BGBLC_FK", id, "0", false);
			
		}
		catch(Exception e)
		{
			this.msg="Loi Xay Ra Tong Qua Trinh Sua :"+ e.getMessage() ;
			e.printStackTrace();
			db.update("rollback");
			return false;
		}
		
		return true;
	}

	private void createDvkdRS(){  				
	//	this.dvkdIds  =  this.db.get("select donvikinhdoanh as dvkd, pk_seq as dvkdId from donvikinhdoanh where trangthai='1'");
		String sql = "select distinct a.donvikinhdoanh as dvkd, a.pk_seq as dvkdId from donvikinhdoanh a,nhacungcap_dvkd b where a.pk_seq = b.DVKD_fk and b.checked ='1' and a.trangthai ='1' order by a.donvikinhdoanh";
		this.dvkdIds  =  this.db.get(sql);
	}
	private void createSpRSNew(dbutils cn){
		if (this.id.length()>0)
		{
			if (this.dvkdId.length()==0)
			{			
				ResultSet rs = this.db.get("select dvkd_fk as dvkdId, ten, trangthai from banggiabanlechuan where pk_seq = '" + this.id + "'");
				try{
					rs.next();
					this.dvkdId = rs.getString("dvkdId");
					this.ten = rs.getString("ten");
					this.trangthai = rs.getString("trangthai");
				
					if(rs!=null){
						rs.close();
					}
				}catch(Exception e){}
			}
			String sql = "select a.pk_seq as id, a.ma, a.ten, c.giabanlechuan from sanpham a, donvikinhdoanh b, donvidoluong dvdl, banggiablc_sanpham c, banggiabanlechuan d where a.trangthai='1' and a.dvkd_fk = b.pk_seq and d.dvkd_fk = b.pk_seq and c.bgblc_fk = d.pk_seq and c.sanpham_fk = a.pk_seq and b.pk_seq = '"+ this.dvkdId +"' order by ma";
			this.sanphamlist = this.db.get(sql);
			System.out.println("sanphamlist : "+sql);
			this.newsplist = this.db.get("select a.pk_seq as id, a.ma, a.ten from sanpham a, donvikinhdoanh b where a.trangthai='1' and a.dvkd_fk = b.pk_seq and b.pk_seq = '"+ this.dvkdId + "' and a.pk_seq not in (select a.pk_seq as id from sanpham a, donvikinhdoanh b, banggiablc_sanpham c, banggiabanlechuan d where a.dvkd_fk = b.pk_seq and d.dvkd_fk = b.pk_seq and c.bgblc_fk = d.pk_seq and c.sanpham_fk = a.pk_seq and b.pk_seq = '" + this.dvkdId + "') order by ma ");
			
		}else{
			if (this.dvkdId.length() > 0){			
				this.sanphamlist = this.db.get("select a.pk_seq as id, a.ma, a.ten from sanpham a, donvikinhdoanh b where a.trangthai='1' and  a.dvkd_fk = b.pk_seq and b.pk_seq = '" + this.dvkdId + "' order by ma");
			}else{
				ResultSet rs = this.db.get("select pk_seq as dvkdId from donvikinhdoanh order by pk_seq");
				try{
					rs.next();
					this.dvkdId = rs.getString("dvkdId");
					this.ten = "";
					this.trangthai = "1";
					if(rs!=null){
						rs.close();
					}
				}catch(Exception e){}
				String sql = "select a.pk_seq as id, a.ma, a.ten,isnull( c.giabanlechuan,0) as giabanlechuan ,isnull((qc.SOLUONG1/nullif(qc.SOLUONG2,0)),0)  as Quycach, dv.DIENGIAI as donvi		from sanpham a  		inner join donvikinhdoanh b on a.DVKD_FK=b.PK_SEQ 		left join banggiablc_sanpham c on c.SANPHAM_FK=a.PK_SEQ 		left join  banggiabanlechuan d on d.PK_SEQ=c.BGBLC_FK  	inner join DONVIDOLUONG dv on dv.PK_SEQ = a.DVDL_FK	left join QUYCACH qc  on qc.SANPHAM_FK=a.PK_SEQ and qc.DVDL2_FK=100018 and qc.dvdl1_fk=a.dvdl_fk  	where    b.pk_seq = '100001'  order by ma"; 
				this.sanphamlist = this.db.get(sql);
				System.out.println("SP LIST 2 : "+sql);
			}
			
		}
	}
	private void createSpRS()
	{
		String sql ="";
		if (this.id.length()>0)
		{
			if (this.dvkdId.length()==0)
			{			
				ResultSet rs = this.db.get("select dvkd_fk as dvkdId, ten, trangthai from banggiabanlechuan where pk_seq = '" + this.id + "'");
				try
				{
					rs.next();
					this.dvkdId = rs.getString("dvkdId");
					this.ten = rs.getString("ten");
					this.trangthai = rs.getString("trangthai");
					if(rs!=null)
					{
						rs.close();
					}
				}catch(Exception e){}
			}
			 sql ="select a.pk_seq as id, a.ma, a.ten,isnull( c.giabanlechuan,0) as giabanlechuan " 
				 +",isnull((qc.SOLUONG1/nullif(qc.SOLUONG2,0)),0)  as Quycach, dv.DIENGIAI as donvi		"
				 +"	from ERP_SANPHAM a  		"
				 +"	inner join donvikinhdoanh b on a.DVKD_FK=b.PK_SEQ 		"
				 +"	left join banggiablc_sanpham c on c.SANPHAM_FK=a.PK_SEQ "		
				 +"	left join  banggiabanlechuan d on d.PK_SEQ=c.BGBLC_FK  	"
				 +"	inner join DONVIDOLUONG dv on dv.PK_SEQ = a.DVDL_FK	    "
				 +" left join QUYCACH qc  on qc.SANPHAM_FK=a.PK_SEQ and qc.DVDL2_FK=100018 and qc.dvdl1_fk=a.dvdl_fk  	"
				 +"	where    b.pk_seq = '100001'  and  c.BGBLC_FK="+this.id+" order by ma"; 
			this.sanphamlist = this.db.get(sql);
			System.out.println("sanphamlist khoi tao : "+sql);
		}else
		{
			if (this.dvkdId.length() > 0)
			{	
				sql=
				"	select a.pk_seq as id, a.ma, a.ten ,qc.SOLUONG1/qc.SOLUONG2 as QuyCach "+
				"	from sanpham a, donvikinhdoanh b ,QUYCACH qc  "+
				"	where a.trangthai='1' and  a.dvkd_fk = b.pk_seq "+
				"		and qc.SANPHAM_FK=a.PK_SEQ and qc.DVDL2_FK=100018 "+
				"		and b.pk_seq = '" + this.dvkdId + "' "+ 
				"	order by ma ";
				this.sanphamlist = this.db.get(sql);
			}else
			{
				ResultSet rs = this.db.get("select pk_seq as dvkdId from donvikinhdoanh order by pk_seq");
				try
				{
					rs.next();
					this.dvkdId = rs.getString("dvkdId");
					this.ten = "";
					this.trangthai = "1";
					if(rs!=null)
					{
						rs.close();
					}
				}catch(Exception e){}
				sql="select a.pk_seq as id, a.ma, a.ten,isnull( c.giabanlechuan,0) as giabanlechuan ,isnull((qc.SOLUONG1/nullif(qc.SOLUONG2,0)),0)  as Quycach, dv.DIENGIAI as donvi			from sanpham a  			inner join donvikinhdoanh b on a.DVKD_FK=b.PK_SEQ 			left join banggiablc_sanpham c on c.SANPHAM_FK=a.PK_SEQ 	left join  banggiabanlechuan d on d.PK_SEQ=c.BGBLC_FK  		inner join DONVIDOLUONG dv on dv.PK_SEQ = a.DVDL_FK	     left join QUYCACH qc  on qc.SANPHAM_FK=a.PK_SEQ and qc.DVDL2_FK=100018 and qc.dvdl1_fk=a.dvdl_fk  		where    b.pk_seq = '100001'  order by ma";
					this.sanphamlist = this.db.get(sql);
					System.out.println("SP LIST 2 : "+sql);
			}
		}
	}
	
	public void createRS()
	{
		createDvkdRS();
		createSpRS();
	}
	
	public void init()
	{
		ResultSet rs = this.db.get("select ten, trangthai, dvkd_fk as dvkdId,tungay from banggiabanlechuan where pk_seq='" + this.id + "'");
		try
		{
			if (rs != null)
			{
				rs.next();
				this.ten = rs.getString("ten");
				this.trangthai = rs.getString("trangthai");
				this.dvkdId = rs.getString("dvkdId");
				this.tungay=rs.getString("tungay");
				if(rs!=null){
					rs.close();
				}
			}
		}catch(Exception e){}
			
		createRS();
	}

	@Override
	public void DbClose() {
		try{
		if( dvkdIds!=null){
			dvkdIds.close();
		}
		if( newsplist!=null){
			newsplist.close();
		}
		if( sanphamlist!=null){
			sanphamlist.close();
		}
	}catch(Exception er){
		
	}
	}

	@Override
	public String[] getQuycach()
	{
		
		return this.quycach;
	}

	@Override
	public void setQuycach(String[] quycach)
	{
		this.quycach=quycach;
		
	}


	public String getDonViDoLuong() {
		
		return this.donvi;
	}


	public void setDonViDoLuong(String donvi) {
		this.donvi= donvi;
	}
	
	
	public String getTungay() {
		return tungay;
	}

	public void setTungay(String tungay) {
		this.tungay = tungay;
	}

}


