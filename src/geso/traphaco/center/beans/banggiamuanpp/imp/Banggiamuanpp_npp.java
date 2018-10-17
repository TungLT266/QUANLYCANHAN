package geso.traphaco.center.beans.banggiamuanpp.imp;
import geso.traphaco.center.beans.banggiamuanpp.IBanggiamuanpp_npp;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.http.HttpServletRequest;
import geso.traphaco.center.db.sql.*;
public class Banggiamuanpp_npp implements IBanggiamuanpp_npp
{
	private static final long serialVersionUID = -9217977546733610214L;
	String userId;
	String userTen;
	String id;
	String ten;
	String dvkdId;
	String kenhId;
	String kvId;
	
	String trangthai;
	String ngaytao;
	String nguoitao;
	String ngaysua;
	String nguoisua;
	String msg;
	
	ResultSet dvkdIds;	
	ResultSet kenhIds;
	ResultSet kvIds;
	
	String[] nppIds;	
	ResultSet nppList;
	ResultSet nppSelected;
	
	dbutils db;
	
	public Banggiamuanpp_npp(String[] param)
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
	
	public Banggiamuanpp_npp()
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
		this.kvId = "";
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
	
	public String getKvId() 
	{
		return this.kvId;
	}

	public void setKvId(String kvId) 
	{
		this.kvId = kvId;
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

	public ResultSet getKhuvucIds() 
	{
		return this.kvIds;
	}

	public void setKhuvucIds(ResultSet kvIds) 
	{
		this.kvIds = kvIds;
	}

	public String[] getnppIds() 
	{
		return this.nppIds;
	}

	public void setnppIds(String[] nppIds) 
	{
		this.nppIds = nppIds;
	}

	public boolean UpdateBgmuanpp(HttpServletRequest request) 
	{	
		try
		{
			this.db.getConnection().setAutoCommit(false);
			
			//CHI CAP NHAT NHUNG NPP CO TREN GIAO DIEN FILTER THEO KHU VUC
			String[] nppChonIds = request.getParameterValues("nppChonIds");
			String[] nppIds = request.getParameterValues("nppIds");
			
			String kenhId = request.getParameter("kenhId");
			String dvkdId = request.getParameter("dvkdId");
			
			String nppChonId = "";
			for( int i = 0; i < nppChonIds.length; i++ )
			{
				nppChonId += nppChonIds[i] + ",";
			}
			if(nppChonId.trim().length() > 0)
				nppChonId = nppChonId.substring(0, nppChonId.length() - 1);
			
			System.out.println("NPP IDS: " + nppIds.length + " -- NPP CHON: " + nppChonId);
			if(nppIds != null)
			{
				String nppId = "";
				for( int i = 0; i < nppIds.length; i++ )
				{
					nppId += nppIds[i] + ",";
				}
				if(nppId.trim().length() > 0)
					nppId = nppId.substring(0, nppId.length() - 1);
				
				System.out.println("NPP ID: " + nppId);
				if(nppId.trim().length() > 0)
				{
					String query = "delete banggiamuanpp_npp where BANGGIAMUANPP_FK = '" + this.id + "' and npp_fk in ( " + nppId + " ) ";
					if(!db.update(query))
					{
						this.msg = "Lỗi khi cập nhật nhà phân phối";
						db.getConnection().rollback();
						return false;
					}
				}
				
				//INSERT NHUNG NPP DUOC CHON
				if(nppChonId.trim().length() > 0)
				{
					//CHECK XEM CO NPP NAO DUOC AP DUNG BANG GIA TRUNG TU NGAY CHUA
					String query = "select a.TUNGAY, c.MA, COUNT(a.PK_SEQ) as soBANGGIA  " +
									"from BANGGIAMUANPP a inner join BANGGIAMUANPP_NPP b on a.PK_SEQ = b.BANGGIAMUANPP_FK  " +
										"inner join NHAPHANPHOI c on b.NPP_FK = c.PK_SEQ  " +
									"where a.PK_SEQ != '" + this.id + "' and a.TUNGAY = ( select tungay from BANGGIAMUANPP where pk_seq = '" + this.id + "' ) and a.KENH_FK = '" + kenhId + "' " +
											"and a.DVKD_FK = '" + dvkdId + "' and c.pk_seq in ( " + nppChonId + " ) " +
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
					
					query = "INSERT banggiamuanpp_npp (BANGGIAMUANPP_FK, npp_fk) " +
							"select '" + this.id + "', pk_seq from NHAPHANPHOI where pk_seq in ( " + nppChonId + " ) ";
					if(!db.update(query))
					{
						this.msg = "Lỗi khi cập nhật nhà phân phối";
						db.getConnection().rollback();
						return false;
					}
				}
				
			}
			 
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			
		}
		catch(Exception e)
		{
			this.db.update("rollback");
			System.out.println("Error Banggiamuanpp_npp - line 307: error" + e.toString());
			return false;
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

	private void createKhuvucRS(){  				
		this.kvIds  =  this.db.get("select diengiai as kvTen, pk_seq as kvId from khuvuc where trangthai='1'");
	}

	
	private void createNppArray()
	{
		this.nppSelected = this.db.get("");
		this.nppList = this.db.get("");
	}

	public void createRS()
	{
		createDvkdRS();
		createKenhRS();
		createKhuvucRS();
	}
	
	public void init()
	{
		String query = "select ten, dvkd_fk as dvkdId, kenh_fk as kenhId, trangthai from banggiamuanpp a where pk_seq ='" + this.id + "'" ;
	
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

		}catch(Exception e){}
		
		createRS();
	}

	public ResultSet  getNppList(){
		
		String query = "select distinct a.ma as nppMa,a.pk_seq as nppId, a.ten as nppTen, a.diachi from nhaphanphoi a, nhapp_kbh b, nhapp_nhacc_donvikd c, nhacungcap_dvkd d where  a.trangthai='1' and d.checked='1'and  b.npp_fk = a.pk_seq and c.ncc_dvkd_fk = d.pk_seq and c.npp_fk = a.pk_seq";
		System.out.println("get NPP : " +query);
		if(this.kenhId.length() > 0){
			query = query + " and b.kbh_fk = '" + this.kenhId + "'";
		}
		
		if(this.dvkdId.length() > 0){
			query = query + " and d.dvkd_fk = '" + this.dvkdId + "'";
		}

		if (this.kvId.length() > 0){
			query = query + " and a.khuvuc_fk='" + this.kvId +"'";
		}
		//Khong lay nha phan phoi trong cac bang gia cung don vi  kinh doanh va kenh
		
		query = query + " and a.pk_seq not in (  select b.npp_fk " +
												"from banggiamuanpp a inner join banggiamuanpp_npp b on a.PK_SEQ = b.banggiamuanpp_fk " +
												"where a.pk_seq = '" + this.id + "' and a.dvkd_fk='" + this.dvkdId + "' and a.kenh_fk = '" + this.kenhId + "' )";
			
		
		System.out.println("Chuoi Get List Npp :banggiamua_npp:367 : "+ query);
		return this.db.get(query);
	}
	
	public ResultSet getNppSelected()
	{		
		String query = "select a.pk_seq as bgmuanppId, b.npp_fk as nppId, c.ten as nppTen,c.ma as nppMa, c.diachi " +
					   "from banggiamuanpp a, banggiamuanpp_npp b, nhaphanphoi c " +
					   "where a.pk_seq = b.banggiamuanpp_fk and b.npp_fk=c.pk_seq " +
					   "	and a.pk_seq ='" + this.id +"'";
		if (this.kvId.length() > 0){
			query = query + " and c.khuvuc_fk='" + this.kvId +"'";
		}
		
		System.out.println("NPP SELECTED: " +query);
		return this.db.get(query);
	}
	
	public String getNppString(){
		String npplist = "";
		try{
			ResultSet rs = getNppSelected();
			while (rs.next()){
				if (npplist.length()==0){
					npplist = npplist + "'" + rs.getString("nppId") + "'";
				}else{
					npplist = npplist + ",'" +  rs.getString("nppId") + "'";
				}
			}
			
			rs = getNppList();
			while (rs.next()){
				if (npplist.length()==0){
					npplist = npplist + "'" + rs.getString("nppId") + "'";
				}else{
					npplist = npplist + ",'" +  rs.getString("nppId") + "'";
				}
			}
			if(rs!=null){
				rs.close();
			}
		}catch(Exception e){}
		
		return npplist;
	}
	public void closeDB(){


	}

	@Override
	public void DbClose() {
		// TODO Auto-generated method stub
		try{
		if( dvkdIds!=null){
			dvkdIds.close();
		}
		if( kenhIds!=null){
			kenhIds.close();
		}
		if( kvIds!=null){
			kvIds.close();
		}
		if( nppList!=null){
			nppList.close();
		}
		if( nppSelected!=null){
			nppSelected.close();
		}
		if(db!=null){
			db.shutDown();
		}
		}catch(Exception er){
			
		}
		
	}
}


