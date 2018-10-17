package geso.traphaco.erp.beans.banggiaban.imp;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.beans.banggiaban.IBanggiaban_kh;

import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;
public class Banggiaban_kh implements IBanggiaban_kh
{
	private static final long serialVersionUID = -9217977546733610214L;
	String userId;
	String userTen;
	String id;
	String ten;
	String dvkdId;
	String kenhId;
	
	String trangthai;
	String ngaytao;
	String nguoitao;
	String ngaysua;
	String nguoisua;
	String msg;
	
	ResultSet dvkdIds;	
	ResultSet kenhIds;
	
	String[] khIds;	
	ResultSet khList;
	ResultSet khSelected;
	
	dbutils db;
	
	public Banggiaban_kh(String[] param)
	{
		this.db = 		new dbutils();
		this.id 		= param[0];
		this.ten 		= param[1];
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
	
	public Banggiaban_kh()
	{	
		this.db = 		new dbutils();
		this.id 		= "";
		this.ten 		= "";
		this.trangthai 	= "1";
		this.ngaytao 	= "";
		this.nguoitao 	= "";
		this.ngaysua 	= "";
		this.nguoisua 	= "";
		this.msg = "";
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
		if (rs != null)
		{
			try{
				if (rs.next())
					this.userTen = rs.getString("ten");
				rs.close();
			}catch(java.sql.SQLException e){
				e.printStackTrace();
			}
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
	
	public String getKenhId() 
	{
		return this.kenhId;
	}

	public void setKenhId(String kenhId) 
	{
		this.kenhId = kenhId;
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

	public String[] getkhIds() 
	{
		return this.khIds;
	}

	public void setkhIds(String[] khIds) 
	{
		this.khIds = khIds;
	}

	public boolean UpdateBgban(HttpServletRequest request) 
	{	
		
		try{
		
			this.db.getConnection().setAutoCommit(false);

			String query = 	"SELECT COUNT(B.KH_FK) AS NUM FROM ERP_BANGGIABAN A   " +
					"INNER JOIN  ERP_BANGGIABAN_KH B ON A.PK_SEQ = B.BANGGIABAN_FK  " +
					" WHERE   A.PK_SEQ =" + this.id +"";
			
			ResultSet rs = this.db.get(query);
			int num = 0;
			if (rs != null)
			{
				if (rs.next())
					num = new Integer(rs.getString("num")).intValue();
				rs.close();
			}
			
			if(num > 0){ //Buoc 1; xoa cac nha phan phoi khong duoc chon trong form dieu chuyen bang gia cho kh
				String[] tmp = new String[num];
				//lay ra danh sach cac nha pp dang co trong bang gia
				query = " select a.pk_seq as bgbanId, b.kh_fk as khId  " +
						" from ERP_BANGGIABAN a INNER JOIN   ERP_BANGGIABAN_KH b ON a.pk_seq = b.BANGGIABAN_FK " +
						" where   a.pk_seq ='" + this.id +"'";			
				rs = this.db.get(query);
				
				int i = 0;
				if (rs != null)
				{
					while(rs.next()){
							//Kiem tra nhung nha phan phoi nao co trong bang gia,va o ngoai form co cot check =null,khong the xoa het mot luot roi cap nhat lai, 
							//vi ngoai man hinh co chuc nang chon cac nhapp theo khu vuc,thi ta chi xet tren pham vi cac nha pp dang hien ra tren form
						if(request.getParameter("idnpp" + rs.getString("khId"))!= null){
							if (request.getParameter("chbox" + rs.getString("khId")) == null ){
								tmp[i] = rs.getString("khId");
								i++;
							}					
						}
					}	
					rs.close();
				}
				
				if(i > 0){
					for (int n = 0; n < i; n++){
					
						query="delete from ERP_BANGGIABAN_KH where BANGGIABAN_FK ='" + this.id + "' and KH_FK = '" + tmp[n] + "'";
						if(!this.db.update(query)){
							this.db.update("rollback");
							return false;
						}
						
					}
				}

			}
			
			rs = getKHList();
			if (rs != null)
			{
				while(rs.next()){
					if (request.getParameter("chbox" + rs.getString("khId")) != null ){
						query = "select count(b.kh_fk) as num from ERP_BANGGIABAN a, ERP_BANGGIABAN_KH b, ERP_KHACHHANG c " +
								"where a.pk_seq = b.BANGGIABAN_FK and b.KH_FK = c.pk_seq and a.dvkd_fk='"+ this.dvkdId + "' " +
								"and b.KH_FK ='" + rs.getString("khId") + "'";
						ResultSet rs2 = this.db.get(query);
						if (rs2 != null)
						{
							if (rs2.next())					
								if(rs2.getString("num").equals("0")){
									query= "insert into ERP_BANGGIABAN_KH values('" + this.id + "', '" + request.getParameter("chbox" + rs.getString("khId")) + "')";
									if(!this.db.update(query)){
										this.db.update("rollback");
										System.out.println("Error Banggiaban_kh - line 302: error"+ query);
										rs2.close();
										return false;
									}
								}
							rs2.close();
						}
						
					}					
				}
				rs.close();
			}
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			
		}catch(java.sql.SQLException e){
			this.db.update("rollback");
				System.out.println("Error Banggiaban_kh - line 307: error" + e.toString());
				return false;
		}
		
		return true;
	}

	private void createDvkdRS(){  				
		this.dvkdIds  =  this.db.get("select distinct a.donvikinhdoanh as dvkd, " +
						"a.pk_seq as dvkdId from donvikinhdoanh a," +
						"nhacungcap_dvkd b where a.pk_seq = b.DVKD_fk and b.checked ='1' and a.trangthai ='1' order by a.donvikinhdoanh");
	}

	private void createKenhRS(){  				
		this.kenhIds  =  this.db.get("select diengiai as kenh, pk_seq as kenhId from kenhbanhang where trangthai='1'");
	}
	
	private void createKHArray(){
		this.khSelected = this.db.get("");
		this.khList = this.db.get("");
	}

	public void createRS(){
		createDvkdRS();
		createKenhRS();
	}
	
	public void init(){
		String query = "select ten, dvkd_fk as dvkdId, kenh_fk as kenhId, trangthai from ERP_BANGGIABAN a where pk_seq ='" + this.id + "'" ;
		
		
		ResultSet rs = this.db.get(query);
		if (rs != null)
		{
			try{		
				if (rs.next())
				{
					this.ten = rs.getString("ten");
					this.dvkdId = rs.getString("dvkdId");
					this.kenhId = rs.getString("kenhId");
					this.trangthai = rs.getString("trangthai");
				}				
				Statement st = rs.getStatement();
				st.close();		
				rs.close();
			}catch(java.sql.SQLException e){
				e.printStackTrace();
			}
		}
		createRS();
	}

	public ResultSet  getKHList(){
		
		String query = "select distinct a.pk_seq as khId, a.ten as khTen, a.diachi " +
					   "from ERP_KHACHHANG a " +
					   "where  a.trangthai='1'  ";
					   
		System.out.println("get NPP : " +query);
		if(this.kenhId.length() > 0){
			query = query + " and a.KENHBANHANG_fk = '" + this.kenhId + "'";
		}
		
		//Khong lay nha phan phoi trong cac bang gia cung don vi  kinh doanh
		
		query = query + " and a.pk_seq not in " +
				"(" +
				"	select b.KH_FK from ERP_BANGGIABAN a, ERP_BANGGIABAN_KH b, ERP_KHACHHANG c " +
				"	where a.pk_seq = b.BANGGIABAN_FK and b.KH_FK = c.pk_seq  and a.dvkd_fk='"+this.dvkdId+"' " +
				")";
			
		
		System.out.println("Chuoi Get List Npp :banggiamua_npp:367 : "+ query);
		return this.db.get(query);
	}
	
	public ResultSet getKHSelected(){		
		String query = 	"select a.pk_seq as bgbanId, b.kh_fk as khId, " +
						"c.ten as khTen, c.diachi from ERP_BANGGIABAN a, ERP_BANGGIABAN_KH b, ERP_KHACHHANG c " +
						"where a.pk_seq = b.BANGGIABAN_FK and b.KH_FK = c.pk_seq and a.pk_seq ='" + this.id +"'";
		
		System.out.println("Chuoi Get List Selected: " + query);
		
		return this.db.get(query);
	}
	
	public String getKHString(){
		String khlist = "";
		try{
			ResultSet rs = getKHSelected();
			if (rs != null)
			{
				while (rs.next())
				{
					if (khlist.length()==0){
						khlist = khlist + "'" + rs.getString("khId") + "'";
					}else
					{
						khlist = khlist + ",'" +  rs.getString("khId") + "'";
					}
				}
				rs.close();
			}			
			
			rs = getKHList();
			if (rs != null)
			{
				while (rs.next())
				{
					if (khlist.length()==0){
						khlist = khlist + "'" + rs.getString("khId") + "'";
					}else{
						khlist = khlist + ",'" +  rs.getString("khId") + "'";
					}
				}
				rs.close();
			}
		}catch(java.sql.SQLException e){
			e.printStackTrace();
		}
		
		return khlist;
	}
	public void DBClose(){
		try {
			if (this.dvkdIds != null)
				this.dvkdIds.close();
			if (this.kenhIds != null)
				this.kenhIds.close();
			if (this.khList != null)
				this.khList.close();
			if (this.khSelected != null)
				this.khSelected.close();
			
			if (this.db != null)
				this.db.shutDown();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}


