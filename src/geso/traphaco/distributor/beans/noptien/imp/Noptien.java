package geso.traphaco.distributor.beans.noptien.imp;

import geso.traphaco.distributor.beans.noptien.INoptien;
import geso.traphaco.distributor.db.sql.dbutils;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

public class Noptien implements INoptien, Serializable
{
	private static final long serialVersionUID = -9217977546733610214L;

	String userId;
	String id;
	String ten;
	String ngaynop;

	String doituongId;
	
	String diachi;
	
	String trangthai;
	String ngaytao;
	String nguoitao;
	String ngaysua;
	String nguoisua;
	String msg;
	
	String vekhoan;
	String nguoinop;
	String soin;
	String soCTgoc;
	
	String nppId;
	String nppTen;
	String sitecode; 

	ResultSet NvbhRS;
	String nvbhId;
	
	ResultSet NvgnRs;
	String nvgnId;
	
	ResultSet KhRs;
	String khId;
	
	ResultSet DtRs;
	String dtId;
	
	double sotien;
	dbutils db;
	
	public Noptien()
	{
		this.id = "";
		this.trangthai = "1";
		this.ngaytao = "";
		this.nguoitao = "";
		this.ngaysua = "";
		this.nguoisua = "";
		this.doituongId ="";
		this.ngaynop="";
		this.khId = "";
		this.nvgnId = "";
		this.nvbhId = "";
		this.sotien= 0;
		
		this.diachi= "";
		this.nguoinop= "";
		this.vekhoan= "Thu tiền hàng";
		this.soin="";
		this.soCTgoc= "01";
		
		this.msg = "";
		this.db = new dbutils();		
	}
	
	public Noptien(String id)
	{
		this.id = id;
		this.ten = "";
		this.ngaynop="";
		this.trangthai = "1";
		this.ngaytao = "";
		this.nguoitao = "";
		this.ngaysua = "";
		this.nguoisua = "";
		this.sotien= 0;
		this.doituongId ="";
		this.khId = "";
		this.nvbhId = "";
		this.nvgnId = "";

		this.diachi= "";
		
		this.nguoinop= "";
		this.vekhoan= "Thu tiền hàng";
		this.soin="";
		this.soCTgoc= "01";
		
		this.msg = "";
		this.db = new dbutils();
		
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
	

	
	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
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

	public String getNppId() 
	{
		return this.nppId;
	}

	public void setNppId(String nppId) 
	{
		this.nppId = nppId;
	}
	
	public String getNppTen() 
	{
		return this.nppTen;
	}

	public void setNppTen(String nppTen) 
	{
		this.nppTen = nppTen;
	}
	
	public String getSitecode() 
	{
		return this.sitecode;
	}

	public void setSitecode(String sitecode) 
	{
		this.sitecode = sitecode;
	}


	


	
	
	private void getNppInfo()
	{		
		//Phien ban moi
		geso.traphaco.distributor.util.Utility util=new geso.traphaco.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();
		this.sitecode=util.getSitecode();
	}

	public void createRS()
	{
		this.getNppInfo();
		this.createNvgnRS();
		this.createNvbhRS();
		this.createKhRS();
		this.createDtRS();
		
	}
	
	private void createDtRS() 
	{
		this.DtRs =  this.db.get("select pk_seq, CAST(pk_seq as nvarchar(20)) + '-' + ten as Ten from NHAPHANPHOI where trangthai = '1' and loainpp = '4' and and tructhuoc_fk = '" + this.nppId + "' ");
		
	}

	public void createNvgnRS()
	{
		this.NvgnRs =  this.db.get("select pk_seq, CAST(pk_seq as nvarchar(20)) + '-' + ten as Ten from NHANVIENGIAONHAN where trangthai = '1' and npp_fk ='"+ this.nppId +"' ");
	}
	
	public void createNvbhRS()
	{
		String sql = " SELECT 	B.PK_SEQ , CAST(B.pk_seq as nvarchar(20)) + ' - ' + B.ten as Ten " +
		  " FROM 	DAIDIENKINHDOANH_NPP A INNER JOIN DAIDIENKINHDOANH B ON A.DDKD_FK= B.PK_SEQ " +
		  " WHERE  	A.npp_fk ='"+ this.nppId +"' AND B.TRANGTHAI = '1' ";
		
		this.NvbhRS =  this.db.get(sql);
		System.out.println("TDV"+sql);
	}
	
	public void createKhRS()
	{
		this.KhRs =  this.db.get( "select pk_seq, isnull(maFAST,'') + '-' + ten as Ten from KHACHHANG where trangthai = '1' and npp_fk ='"+ this.nppId +"' ");

	}
	public boolean CreateKh() 
	{		
		this.getNppInfo();
		
		if (this.ngaynop.equals(""))
		{
			this.msg= "Vui lòng chọn ngày nộp tiền";
			return false;
		}

		if (this.sotien <= 0)
		{
			this.msg= "Vui lòng nhập số tiền";
			return false;
		}

		if (nvgnId.trim().length() <= 0 && nvbhId.trim().length() <= 0 && khId.trim().length() <= 0 )
		{
			this.msg= "Vui lòng chọn thông tin của đối tượng";
			return false;
		}
				
		try
		{
			this.db.getConnection().setAutoCommit(false);
			
		this.ngaytao = getDateTime();
		this.nguoisua = this.userId;
				
		String query = "";
	
		if(this.soin.trim().length() > 0)
		{
			// Kiểm tra Số in có bị trùng không
			
			//query ="select count(*) as dem from NOPTIEN where SOIN ='"+this.soin.trim()+"' and trangthai != '2' and npp_fk = '"+ this.nppId +"' and MONTH(NGAYNOP) = MONTH('"+(this.ngaynop)+"')";
			
			query = " select count(*) as dem from NOPTIEN where SOIN = '"+ this.soin.trim() +"' and pk_seq != '" + this.id + "' and trangthai != '2' and npp_fk = '"+ this.nppId +"' ";
			ResultSet rsKT = db.get(query);
			int kt = 0;
			if(rsKT!= null)
			{
				while (rsKT.next())
				{
					kt = rsKT.getInt("dem");
				}
				rsKT.close();
			}

			System.out.println("Kiểm tra Số in có bị trùng không:"+ query);	
			//query = " select count(*) as dem from NOPTIEN where SOIN = '"+ this.soin.trim() +"' and trangthai != '2' and npp_fk = '"+ this.nppId +"'  ";
			
			if(rsKT!= null)
			{
				while (rsKT.next())
				{
					kt = rsKT.getInt("dem");
				}
				rsKT.close();
			}
			
			if(kt > 0)
			{
				query ="select PK_SEQ from NOPTIEN where soin='"+this.soin.trim()+"' and NPP_FK ='"+this.nppId+"' and MONTH(NGAYNOP) = MONTH('"+(this.ngaynop)+"')";
				
				ResultSet RSPhieuNopTien =db.get(query);
				
				String pnt="";
				if(RSPhieuNopTien!=null)
				{
					while(RSPhieuNopTien.next())
						pnt+=RSPhieuNopTien.getString("PK_SEQ")+", ";
					RSPhieuNopTien.close();						
				}
				
				this.msg= "Số phiếu in (" + this.soin.trim() + ") đã có trong hệ thống. Số in này nằm trong Phiếu nộp tiền "+pnt+". Vui lòng thay số in khác!";
				return false;
			}
		}
 
		if(this.nvbhId.trim().length() <=0 ) this.nvbhId= "NULL";
		if(this.nvgnId.trim().length() <=0 ) this.nvgnId= "NULL";
		if(this.khId.trim().length() <=0 ) this.khId= "NULL";
		if(this.dtId.trim().length() <=0 ) this.dtId= "NULL";

			query = "insert into NOPTIEN(NGAYNOP, NVGN_FK, NVBH_FK, KHACHHANG_FK, NPP_DAT_FK , ngaytao, ngaysua, nguoitao, nguoisua, TRANGTHAI, SOTIEN, NPP_FK, VEKHOAN, SOIN, SOCTGOC)";
			query = query + " values('"+ this.ngaynop +"', "+ this.nvgnId +", "+ this.nvbhId +", "+ this.khId +", "+ this.dtId +", '"+ this.ngaytao +"', '"+  this.ngaytao +"'," +
					" '"+ this.nguoisua +"', '"+ this.nguoisua +"', '0','"+ this.sotien +"','"+ this.nppId +"', N'"+ this.vekhoan +"','"+ this.soin +"', '"+ this.soCTgoc +"'   )";
				
		if (!db.update(query)){
			db.getConnection().rollback();
			this.msg = "Khong the luu vao table 'NOPTIEN' , " + query;
			return false;			
		}
		
			
			
			
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		}catch(Exception e)
		{
			db.update("rollback");
			this.msg="Khong the them phiếu nộp tiền,xay ra loi trong qua trinh tao moi,loi dong lenh sau :" +e.toString();
			return false;
		}
		return true;
	}



	public boolean UpdateKh() 
	{	
		this.getNppInfo();
		
		if (this.ngaynop.equals(""))
		{
			this.msg= "Vui lòng chọn ngày nộp tiền";
			return false;
		}

		if (this.sotien <= 0)
		{
			this.msg= "Vui lòng nhập số tiền";
			return false;
		}


		
		this.ngaysua = getDateTime();
		this.nguoisua = this.userId;
		// Update table "Khach Hang"
		String query="";

		
		try
		{
			this.db.getConnection().setAutoCommit(false);

			if(this.nvbhId.trim().length() <=0 ) this.nvbhId= "NULL";
			if(this.nvgnId.trim().length() <=0 ) this.nvgnId= "NULL";
			if(this.khId.trim().length() <=0 ) this.khId= "NULL";
			if(this.dtId.trim().length() <=0 ) this.dtId= "NULL";
			
			// Kiem tra trang thai hien tai cua Phieu thu
			query = " select trangthai from NOPTIEN where pk_seq= "+ this.id +" ";
			ResultSet rs = db.get(query);
			String trangthai="";
			if(rs!= null)
			{
				while(rs.next())
				{
					trangthai = rs.getString("trangthai");
				}rs.close();
					
			}
			if(trangthai.equals("2"))//Nếu phiếu nộp tiền ở trạng thái hủy, k cho phép được cập nhật
			{
				this.db.getConnection().rollback();
				this.msg = "Phiếu nộp tiền này đã xóa không cho phép cập nhật thông tin " ;
				return false;
			}
			else  // Phiếu nộp tiền ở trạng thái chưa xác nhận
			{
				
				if (nvgnId.trim().length() <= 0 && nvbhId.trim().length() <= 0 && khId.trim().length() <= 0 )
				{
					this.msg= "Vui lòng chọn thông tin của đối tượng";
					return false;
				}
				
				if(this.soin.trim().length() > 0)
				{
					// Kiểm tra Số in có bị trùng không
					query = " select count(*) as dem" +
							" from NOPTIEN" +
							" where RTRIM(LTRIM(SOIN)) = '"+ this.soin.trim() +"' and pk_seq != '" + this.id + "' and trangthai != '2' and npp_fk = '"+ this.nppId +"' and MONTH(NGAYNOP) = MONTH('"+(this.ngaynop)+"')  ";
					
					ResultSet rsKT = db.get(query);
					int kt = 0;
					if(rsKT!= null)
					{
						while (rsKT.next())
						{
							kt = rsKT.getInt("dem");
						}
						rsKT.close();
					}
					
					if(kt > 0)
					{
						query ="select PK_SEQ from NOPTIEN where soin='"+this.soin.trim()+"' and pk_seq != '" + this.id + "' and NPP_FK ='"+this.nppId+"' and MONTH(NGAYNOP) = MONTH('"+(this.ngaynop)+"')";
						
						ResultSet RSPhieuNopTien =db.get(query);
						
						String pnt="";
						if(RSPhieuNopTien!=null)
						{
							while(RSPhieuNopTien.next())
								pnt+=RSPhieuNopTien.getString("PK_SEQ")+", ";
							RSPhieuNopTien.close();						
						}
						
						this.msg= "Số phiếu in (" + this.soin.trim() + ") đã có trong hệ thống. Số in này nằm trong Phiếu nộp tiền "+pnt+". Vui lòng thay số in khác!";
						return false;
					}
				}
				
				// Kiểm tra Số tiền >= Số tiền (của phiếu nộp tiền đã sử dụng trong phiếu thu)
				query = " select SUM(isnull(sotiendatt,0)) as dasd " +
						" from ERP_THUTIENNPP a inner join ERP_THUTIENNPP_NOPTIEN b on a.pk_seq = b.thutiennpp_fk" +
						" where a.trangthai != 2 and b.noptien_fk = "+ this.id +" " +
						" group by b.noptien_fk  ";
				ResultSet rsKTTien = db.get(query);
				double SotienDSD = 0 ;
				if(rsKTTien!= null)
				{
					while(rsKTTien.next())
					{
						SotienDSD = rsKTTien.getDouble("dasd");
					}
					rsKTTien.close();
				}
				
				if(this.sotien - SotienDSD < 0)
				{
					this.db.getConnection().rollback();
					this.msg = "Số tiền bạn sửa < Số tiền của phiếu nộp này đã được dùng trong các phiếu thu. Vui lòng kiểm tra lại";
					return false;
				}
				
				query = "update NOPTIEN set NGAYNOP = '"+ this.ngaynop +"' , NVGN_FK = "+this.nvgnId +", NVBH_FK= "+ this.nvbhId +", KHACHHANG_FK= "+ this.khId +", NPP_DAT_FK= "+ this.dtId +"," +
				"  ngaysua = '"+ this.ngaysua +"', nguoisua = '"+ this.nguoisua +"',  SOTIEN= '"+ this.sotien +"', NPP_FK= '"+ this.nppId +"'," +
				"  VEKHOAN= N'"+ this.vekhoan +"' , SOCTGOC= '"+ this.soCTgoc +"', SOIN = '"+ this.soin +"' " +
				"where pk_seq = '" + this.id + "'" ;
				
				System.out.println("---LENH CAP NHAT: " + query );
				if(!this.db.update(query))
				{
					this.db.getConnection().rollback();
					this.msg = "Khong the cap nhat table 'NOPTIEN' , " + query;
					return false; 
				}
				
			}

						
						
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		}
		catch(Exception er)
		{
			this.msg="Loi trong qua trinh update,vui long thuc hien lai,neu khong duoc vui lien he voi admin GESO de duoc huong dan .Loi dong lenh :" +er.toString();
			this.db.update("rollback");
			er.printStackTrace();
			return false;
		}
		return true;
	}
	
	public void init() 
	{
		this.getNppInfo();
		String query = "select a.pk_seq , kh.pk_seq as khId, nvgn.pk_seq as nvgnId, ddkd.pk_seq as nvbhId, npp.ten as nppdatId,  " +
				       "       a.sotien, a.ngaynop," +
				       "       isnull(a.vekhoan, '') as vekhoan, isnull(a.soCTgoc,'') as soCTgoc, isnull(a.soin,'') as soin " +
				       "from NOPTIEN a left join KHACHHANG kh on a.khachhang_fk=kh.pk_seq " +
				       "     left join NHANVIENGIAONHAN nvgn on a.nvgn_fk= nvgn.pk_seq " +
				       "     left join DAIDIENKINHDOANH ddkd on a.nvbh_fk= ddkd.pk_seq " +
				       "     left join NHAPHANPHOI npp on a.npp_dat_fk= npp.pk_seq " +
				       " where a.npp_fk= '"+ this.nppId +"' and a.pk_seq ='"+ this.id +"'  ";
	      System.out.println("Query :"+query);
        ResultSet rs =  this.db.get(query);
        try{
            rs.next();        	
        	this.id = rs.getString("pk_seq");
        	this.ngaynop = rs.getString("ngaynop");
        	this.khId = rs.getString("khId")== null ? "" : rs.getString("khId");
        	this.nvbhId = rs.getString("nvbhId")== null ? "" : rs.getString("nvbhId");
        	this.nvgnId = rs.getString("nvgnId")== null ? "" : rs.getString("nvgnId");
        	this.dtId = rs.getString("nppdatId")== null ? "" : rs.getString("nppdatId");
        	this.sotien = rs.getDouble("sotien");
        	this.vekhoan =  rs.getString("vekhoan");
        	this.soCTgoc =  rs.getString("soCTgoc");
        	this.soin =  rs.getString("soin");
        	
        	// Doi tuong : 0 nhan vien giao nhan, 1 nhan vien ban hang, 2 khach hang
        	
        	if(this.khId.trim().length() > 0)
        	{
        		this.doituongId = "2";
        		
        	}else if(this.nvgnId.trim().length() > 0)
        	{
        		this.doituongId = "0";
        		
        	}else if (this.nvbhId.trim().length() > 0)
        	{
        		this.doituongId= "1";
        		
        	}else if (this.dtId.trim().length() > 0)
        	{
        		this.doituongId= "3";
        	}
        
       	}
        catch(Exception e){
        	e.printStackTrace();
        	this.msg="Loi Trong Qua Trinh Lay Du Lieu ."+ e.toString();
        }
        finally{try {
			if(rs != null)
				rs.close();
		} catch (Exception e2) {

		}}
       	createRS(); 
	}
	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}



	


	
	public void DBclose() {
		
		try {
			
			if(this.KhRs != null)
				this.KhRs.close();
			if(this.NvbhRS != null)
				this.NvbhRS.close();
			if(this.NvgnRs != null)
				this.NvgnRs.close();

			if(this.db != null)
				this.db.shutDown();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}



	

	@Override
	public ResultSet getNvbhRs() {
		// TODO Auto-generated method stub
		return this.NvbhRS;
	}

	@Override
	public void setNvbhRs(ResultSet nvbhRs) {
		this.NvbhRS= nvbhRs;
		
	}

	@Override
	public String getNvbhId() {
		// TODO Auto-generated method stub
		return this.nvbhId;
	}

	@Override
	public void setNvbhId(String nvbhId) {
		this.nvbhId= nvbhId;
		
	}

	@Override
	public ResultSet getKhRs() {
		// TODO Auto-generated method stub
		return this.KhRs;
	}

	@Override
	public void setKhRs(ResultSet KhRs) {
		this.KhRs= KhRs;
		
	}

	@Override
	public String getKhId() {
		// TODO Auto-generated method stub
		return this.khId;
	}

	@Override
	public void setKhId(String KhId) {
		this.khId= KhId;
		
	}

	@Override
	public ResultSet getNvgnRs() {
		// TODO Auto-generated method stub
		return this.NvgnRs;
	}

	@Override
	public void setNvgnRs(ResultSet nvgnRs) {
		this.NvgnRs= nvgnRs;
		
	}

	@Override
	public String getNvgnId() {
		// TODO Auto-generated method stub
		return this.nvgnId;
	}

	@Override
	public void setNvgnId(String nvgnId) {
		this.nvgnId= nvgnId;
		
	}

	@Override
	public String getNgaynop() {
		// TODO Auto-generated method stub
		return this.ngaynop;
	}

	@Override
	public void setNgaynop(String ngaynop) {
		this.ngaynop= ngaynop;
		
	}

	@Override
	public Double getSotien() {
		// TODO Auto-generated method stub
		return this.sotien;
	}

	@Override
	public void setSotien(Double sotien) {
		this.sotien= sotien;
		
	}

	@Override
	public String getDoituongId() {
		// TODO Auto-generated method stub
		return this.doituongId;
	}

	@Override
	public void setDoituongId(String doituongId) {
		this.doituongId = doituongId;
		
	}

	@Override
	public void initPdf() 
	{

		this.getNppInfo();
		NumberFormat formatter = new DecimalFormat("#,###,###"); 
		String query = " select a.pk_seq as tthdId, a.ngaynop, a.trangthai,isnull(a.vekhoan,'') as vekhoan, " +
					   "   isnull(sotien, 0) as sotien , isnull(a.soin,'') as soin , isnull(a.soCTgoc,'') as soCTgoc , " +
					   "   case when a.nvgn_fk is not null then nvgn.ten " +
					   "        when a.nvbh_fk is not null then ddkd.ten else kh.ten end as nguoinoptien, " +
					   "   case when a.nvgn_fk is not null then nvgn.diachi " +
					   "        when a.nvbh_fk is not null then ddkd.diachi else kh.diachi end as diachi " +
					   " from NOPTIEN a " +
					   "      left join NHANVIENGIAONHAN nvgn on a.nvgn_fk= nvgn.pk_seq" +
					   "	  left join DAIDIENKINHDOANH ddkd on a.nvbh_fk= ddkd.pk_seq" +
					   "      left join KHACHHANG kh on a.khachhang_fk= kh.pk_seq " +
					   " where a.pk_seq = '" + this.id + "' ";
		
		System.out.println("[ErpThutien.initPdf] query = " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					this.ngaynop = rs.getString("ngaynop");
					this.sotien =  rs.getDouble("sotien");
					this.nguoinop = rs.getString("nguoinoptien");
					this.soin = rs.getString("soin");
					this.soCTgoc = rs.getString("soCTgoc");
					this.vekhoan = rs.getString("vekhoan");
					this.diachi =  rs.getString("diachi");

				}
			} 
			catch (SQLException e) 
			{
				System.out.println("115..Exception: " + e.getMessage());
			}
		}
		
		
		
	
		
	}

	@Override
	public String getVekhoan() {
		// TODO Auto-generated method stub
		return this.vekhoan;
	}

	@Override
	public void setVekhoan(String vekhoan) {
		this.vekhoan= vekhoan;
	}

	@Override
	public String getSoin() {
		// TODO Auto-generated method stub
		return this.soin;
	}

	@Override
	public void setSoin(String soin) {
		this.soin = soin;
		
	}

	@Override
	public String getSoCTgoc() {
		// TODO Auto-generated method stub
		return this.soCTgoc;
	}

	@Override
	public void setSoCTgoc(String soCTgoc) {
		this.soCTgoc= soCTgoc;
		
	}

	@Override
	public String getNguoinop() {
		// TODO Auto-generated method stub
		return this.nguoinop;
	}

	@Override
	public void setNguoinop(String nguoinop) {
		this.nguoinop= nguoinop;
		
	}
	
	public String getDiachi()
	{
		return diachi;
	}
	
	public void setDiachi(String diachi)
	{
		this.diachi = diachi;
	}

	public ResultSet getDtRs() 
	{
		return this.DtRs;
	}

	public void setDtRs(ResultSet DtRs)
	{
		this.DtRs = DtRs;
	}

	public String getDtId() 
	{
		return this.dtId;
	}

	public void setDtId(String DtId)
	{
		this.dtId = DtId;
	}



	
	
}

