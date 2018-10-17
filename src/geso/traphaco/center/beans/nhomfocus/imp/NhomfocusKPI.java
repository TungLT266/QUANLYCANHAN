package geso.traphaco.center.beans.nhomfocus.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import geso.traphaco.center.beans.nhomfocus.INhomfocusKPI;
import geso.traphaco.center.beans.nhomfocus.ISanpham;
import geso.traphaco.center.beans.nhomfocus.imp.*;
import geso.traphaco.center.db.sql.dbutils;

public class NhomfocusKPI implements INhomfocusKPI
{
	
	String UserID,ID,THANG,NAM,TRANGTHAI,DONVIKINHDOANH,KENHBANHANG,DOITUONG,MSG, diengiai;
	
	ResultSet rsNhomfocus,rsKenhbanhang,rsDvkd;
	
	String khuvucStr;
	String VungStr;
	ResultSet rsKhuvuc;
	ResultSet RsVung;
	
	List<ISanpham> spList;
	dbutils db;
	
	public NhomfocusKPI(String id)
	{
		this.db=new dbutils();
		this.UserID="";
		this.ID=id;
		this.THANG="";
		this.NAM="";
		this.TRANGTHAI="";
		this.DONVIKINHDOANH="";
		this.KENHBANHANG="";
		this.DOITUONG="";
		this.MSG="";
		this.VungStr="";
		this.khuvucStr="";
		this.diengiai = "";
		this.spList = new ArrayList<ISanpham>();
	}
	
	public NhomfocusKPI()
	{
		this.db=new dbutils();
		this.UserID="";
		this.ID="";
		this.THANG="";
		this.NAM="";
		this.TRANGTHAI="";
		this.DONVIKINHDOANH="";
		this.KENHBANHANG="";
		this.DOITUONG="";
		this.MSG="";
		this.VungStr="";
		this.khuvucStr="";
		this.diengiai = "";
		this.spList = new ArrayList<ISanpham>();
	}
	
	public String getUserId() 
	{	
		return this.UserID;
	}

	
	public void setUserId(String userId) 
	{
		this.UserID=userId;
	}

	
	public String getId()
	{
		
		return this.ID;
	}

	
	public void setId(String id) 
	{
		this.ID=id;
	}

	
	public String getThang()
	{
		
		return this.THANG;
	}

	
	public void setThang(String thang) 
	{
		this.THANG=thang;
	}

	
	public String getTrangthai() 
	{
		
		return this.TRANGTHAI;
	}

	
	public void setTrangthai(String trangthai) 
	{
		this.TRANGTHAI=trangthai;
	}

	
	public String getNam() 
	{
		
		return this.NAM;
	}

	
	public void setNam(String nam) 
	{
		this.NAM=nam;		
	}

	
	public String getDvkd() 
	{
		
		return this.DONVIKINHDOANH;
	}

	
	public void setDvkd(String donvikinhdoanh) 
	{
		this.DONVIKINHDOANH=donvikinhdoanh;
	}

	
	public String getKenhbanhang() 
	{
		
		return this.KENHBANHANG;
	}

	
	public void setKenhbanhang(String kenhbanhang) 
	{
		this.KENHBANHANG=kenhbanhang;
	}

	
	public String getDoituong() 
	{
		
		return this.DOITUONG;
	}

	
	public void setDoituong(String doituong) 
	{
		this.DOITUONG=doituong;
	}

	
	public List<ISanpham> getSanphamList() 
	{
		
		return this.spList;
	}

	
	public void setSanphamList(List<ISanpham> SanphamList)
	{
		this.spList=SanphamList;
		
	}

	public ResultSet getKenhbanhangList()
	{
		
		return this.rsKenhbanhang;
	}

	
	public void setKenhbanhangList(ResultSet Kenhbanhanglist) 
	{
		this.rsKenhbanhang=Kenhbanhanglist;
	}

	
	public void init()
	{
		String query = "select * from nhomfocuskpi where pk_seq = '" + this.ID + "'";
		System.out.println("Cau query la "+query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					this.ID = rs.getString("PK_SEQ");
					this.diengiai = rs.getString("diengiai");
					this.THANG=rs.getString("THANG");;
					this.NAM=rs.getString("NAM");;
					this.TRANGTHAI=rs.getString("TRANGTHAI");;
					this.DONVIKINHDOANH=rs.getString("DVKD_FK");
					this.KENHBANHANG=rs.getString("KBH_FK");
					this.DOITUONG=rs.getString("DOITUONG");
				}
				rs.close();
			} 
			catch (SQLException e) 
			{
				System.out.println("Loi roi" + e.toString());
			}
		}
		this.createRs();
	}

	public void createRsUpdate() 
	{
		
		String query="";
		query="Select * from donvikinhdoanh where trangthai='1'";
		this.rsDvkd=db.get(query);
		query="Select * from kenhbanhang where trangthai='1'";
		this.rsKenhbanhang=db.get(query);
	}

	public void createRs() 
	{
		try {
		String query="";
		query="Select * from donvikinhdoanh where trangthai='1'";
		this.rsDvkd=db.get(query);
		query="Select * from kenhbanhang where trangthai='1'";
		this.rsKenhbanhang=db.get(query);
		
		if(this.ID.length() >0)
		{
			query = "Select pk_seq,ten from vung left join nhomfocuskpi_vung b on vung.pk_seq=b.vung_fk  and nhomfocuskpi_fk="+ this.ID +" where trangthai='1'";
		}
		else
		{		
			query="Select pk_seq,ten  from vung where trangthai='1'";				
		}
		
		System.out.println("Vung : "+query);
		this.RsVung=db.get(query);
				
		query="Select pk_seq,ten from khuvuc where trangthai='1'";
		this.rsKhuvuc=db.get(query);
		
	
		List<ISanpham> spList = new ArrayList<ISanpham>();
		if(this.ID.length() > 0 &&  this.spList.size() <= 0)
		{
		       query="select a.pk_seq,a.trangthai,a.thang,a.nam,a.dvkd_fk,a.kbh_fk,sp.MA as MASANPHAM,sp.TEN as TENSANPHAM, b.phantram as phantram" +
		       		" from nhomfocuskpi a inner join nhomfocuskpi_sp b on a.pk_seq = b.nhomfocuskpi_fk" +
		       		" inner join sanpham sp on sp.pk_seq = b.sanpham_fk Where a.pk_seq='"+this.ID+"'";
			System.out.println("tk "+query);
			ResultSet rs=db.get(query);
			if(rs!=null)
			{
				
				
					while(rs.next())
					{  
						ISanpham spDetail=null;
						spDetail=new Sanpham();
						spDetail.setMasp(rs.getString("MASANPHAM"));
						spDetail.setTensp(rs.getString("TENSANPHAM"));
						spDetail.setPhantram(rs.getString("PHANTRAM"));
						spList.add(spDetail);
					}
					rs.close();
				
			}
			this.spList=spList;
			/*if(this.VungStr.length()==0){
				String sql="select vung_fk from nhomfocus_vung where nhomfocus_fk="+ this.ID;
				System.out.println("get data : "+sql);
				 rs=this.db.get(sql);
				 String chuoi="";
				if(rs!=null){
					while( rs.next()){
						chuoi=chuoi+","+rs.getString("vung_fk");
						
					}
					//chuoi=chuoi.substring(1, chuoi.length());
					this.VungStr=chuoi;
				}
			
			}*/	
			
			if(this.VungStr.length()==0){
			String sql="select vung_fk from nhomfocuskpi_vung where nhomfocuskpi_fk="+ this.ID;
			System.out.println("get data : "+sql);
			 rs=this.db.get(sql);
			 String chuoi="";
			if(rs!=null){
				while( rs.next()){
					chuoi=chuoi+","+rs.getString("vung_fk");
					
				}
				//chuoi=chuoi.substring(1, chuoi.length());
				this.VungStr=chuoi;
			}
		
		}
		}
		} 
		catch (Exception e) 
		{
			this.MSG="Lỗi trong quá trình khởi tạo dữ liệu RS.Loi :  "+e.toString();
		}
	}

	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	public boolean createNhomfocus(HttpServletRequest request) 
	{
		String ngaytao = this.getDateTime();
		int soluong=spList.size();
		if(this.diengiai.length() == 0 || this.NAM.length()==0 || this.THANG.length()==0 || this.DOITUONG.length()==0 || this.DONVIKINHDOANH.length()==0|| this.KENHBANHANG.length()==0 )
		{
			this.MSG="Vui lòng nhập đủ thông tin!";
			return false;
		}
		
		try 
		{
		
		if(this.VungStr.length()==0){
			this.MSG="Vui lòng chọn khu vực cho vùng!";
			return false;
		}
		
		String[] mangvung=this.VungStr.split(",");
		
		String sql="select vung_fk,v.ten from nhomfocuskpi_vung b  inner join vung v on b.vung_fk = v.pk_seq " +
				" inner  join nhomfocuskpi a on a.pk_seq = nhomfocuskpi_fk  where a.thang="+this.THANG+" and a.nam="+this.NAM+" and a.dvkd_fk="+this.DONVIKINHDOANH+" and a.kbh_fk="+this.KENHBANHANG+" and doituong='"+this.DOITUONG+"' and a.trangthai <>2 and vung_fk in("+this.VungStr+") ";
		
		System.out.println("cau Lenh Kiem Tra Trung Khuvuc : "+sql);
		
		String chuoi="";
		ResultSet rs=this.db.get(sql);
		if(rs!=null){
			while (rs.next())
			{
				chuoi=chuoi + rs.getString("ten") + ",";				
			}
			//chuoi = chuoi.substring(0, chuoi.length()-1);
		}			
		
		if(chuoi.length() >0){
			this.MSG="(Một số) vùng ["+chuoi+"] đã tồn tại trong nhóm focuskpi có cùng (tháng,năm, kênh ,đơn vị kinh doanh và đối tượng) khác. Vui lòng kiểm tra lại!";
			return false;
		}
		
			db.getConnection().setAutoCommit(false);		
			String query = "INSERT INTO NHOMFOCUSKPI(DIENGIAI, THANG, NAM,DVKD_FK,KBH_FK,DOITUONG, NGAYTAO, NGAYSUA, NGUOITAO, NGUOISUA, TRANGTHAI) " +
			"values('"+ this.diengiai +"', '" + this.THANG + "', '" + this.NAM+ "', '" + this.DONVIKINHDOANH + "', '"+ this.KENHBANHANG+"', '"+this.DOITUONG+"', '" + ngaytao + "', '" + ngaytao + "', '" + this.UserID + "', '" + this.UserID + "', '0')";
			System.out.println("1. insert nhomfocuskpi : " + query);
			if(!db.update(query))
			{
				this.MSG = "Không thể tạo nhomfocuskpi lỗi: " + query;
				db.getConnection().rollback();
				return false;
			}
			String parkCurrent = "";//Lay so PK_SEQ park vua insert
			parkCurrent = "select IDENT_CURRENT('NHOMFOCUSKPI') as parkId";
			
			ResultSet rsNh = db.get(parkCurrent);						
			if(rsNh.next())
			{
				parkCurrent = rsNh.getString("parkId");
				rsNh.close();
				this.ID=parkCurrent;
			}
			
			if(this.spList.size() > 0)
			{
				for(int i = 0; i < this.spList.size(); i++)
				{
							
					ISanpham cksp = spList.get(i);
					String queryidsp = "select PK_SEQ from sanpham where ma='"+ cksp.getMasp() +"'";
					String pk_seq="";
					ResultSet rsSp = db.get(queryidsp);
					if(rsSp!=null)
					{
						if(rsSp.next())
						{
							pk_seq = rsSp.getString("PK_SEQ");
							
							rsSp.close();
						}
					}
					else
					{
						this.MSG = "Lỗi trong lúc lấy sản phẩm : " + queryidsp;
						db.getConnection().rollback();
						return false;
					}
					
					 query = "INSERT INTO NHOMFOCUSKPI_SP(NHOMFOCUSKPI_FK , SANPHAM_FK, PHANTRAM) " +
					"values('" + this.ID + "', '" + pk_seq +"', '"+cksp.getPhantram() +"')";	
					 
					System.out.println("2. insert nhomfocuskpi_sp : " + query); 
					if(!db.update(query))
					{
						this.MSG = "Không thể tạo mới nhomfocuskpi_sp lỗi: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
			}
			
			for(int i=0;i<mangvung.length;i++)
			{			
				
				 /*query = "INSERT INTO NHOMFOCUSKPI_VUNG(NHOMFOCUSKPI_FK,vung_fk,chitieu) " +
					"values('" + this.ID + "', '" + mangvung[i] +"',"+chiteu+")";		
					if(!db.update(query))
					{
						this.MSG = "Không thể tạo mới nhomfocus_sp lỗi: " + query;
						db.getConnection().rollback();
						return false;
					}*/
				
				query = "INSERT INTO NHOMFOCUSKPI_VUNG(NHOMFOCUSKPI_FK, VUNG_FK) " +
						"values('" + this.ID + "', '" + mangvung[i] +"')";
				
				System.out.println("3. insert nhomfocuskpi_vung : " + query);
				if(!db.update(query))
				{
					this.MSG = "Không thể tạo mới nhomfocuskpi_sp lỗi: " + query;
					db.getConnection().rollback();
					return false;
				}
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;
		} 
		catch (Exception e) 
		{
			db.update("rollback");
			this.MSG="Đã có lỗi xảy ra không thể thêm ( "+e.toString()+" )";
			return false;
		}
	}

	
	public boolean updateNhomfocus(HttpServletRequest request) 
	{
		String ngaytao = this.getDateTime();
		if(this.diengiai.length() == 0 || this.NAM.length()==0 || this.THANG.length()==0 || this.DOITUONG.length()==0 || this.DONVIKINHDOANH.length()==0|| this.KENHBANHANG.length()==0 )
		{
			this.MSG="Vui lòng nhập đủ thông tin!";
			return false;
		}
		try 
		{
		
			if(this.VungStr.length()==0){
			this.MSG="Vui lòng chọn khu vực cho vùng!";
			return false;
		}
		
			String[] mangvung= this.VungStr.split(",");
			
			String sql="select vung_fk,v.ten from nhomfocuskpi_vung b  inner join vung v on b.vung_fk=v.pk_seq " +
					" inner  join nhomfocuskpi a on a.pk_seq=nhomfocuskpi_fk  where a.thang="+this.THANG+" and a.nam="+this.NAM+" and a.dvkd_fk="+this.DONVIKINHDOANH+" and a.kbh_fk="+this.KENHBANHANG+" and doituong='"+this.DOITUONG+"' " +
							" and  a.pk_seq <> "+this.ID+"  and a.trangthai <>2 and vung_fk in("+this.VungStr+") ";
			
			System.out.println("cau Lenh Kiem Tra Trung Khuvuc : "+sql);
			
			String chuoi="";
			ResultSet rs=this.db.get(sql);
			if(rs!=null){
				while (rs.next()){
					chuoi=chuoi+"," + rs.getString("ten");
					
				}
			}
			
			if(chuoi.length() >0){
				this.MSG="Một số vùng ["+chuoi+"] này đã tồn tại trong nhóm focuskpi có cùng ( tháng,năm, kênh ,đơn vị kinh doanh và đối tượng) khác.Vui lòng kiểm tra lại!";
				return false;
			}
			
			
			
			db.getConnection().setAutoCommit(false);
			//Chen vao bang chuyen kho san pham
			String query = "update nhomfocuskpi set diengiai = '"+ this.diengiai +"', thang='" + this.THANG + "',nam='" + this.NAM+ "',dvkd_fk= '" + this.DONVIKINHDOANH + "',kbh_fk= '"+ this.KENHBANHANG+"',doituong= '"+this.DOITUONG+"',ngaysua= '" + ngaytao + "', nguoisua='" + this.UserID + "' where pk_seq='"+this.ID+"'";
			System.out.println("query"+query);
			if(!db.update(query))
			{
				this.MSG = "Không thể chỉnh sửa nhomfocuskpi lỗi: " + query;
				db.getConnection().rollback();
				return false;
			}
			String parkCurrent = "delete nhomfocuskpi_sp where NHOMFOCUSKPI_FK='"+this.ID+"'";//Lay so PK_SEQ park vua insert
							
			if(!db.update(parkCurrent))
			{
				this.MSG = "Không thể chỉnh sửa nhomfocuskpi lỗi: " + parkCurrent;
				db.getConnection().rollback();
				return false;
			}
			if(this.spList.size() > 0)
			{
				for(int i = 0; i < this.spList.size(); i++)
				{
				
					///////////////////////////////////
					ISanpham cksp = spList.get(i);
					String queryidsp = "select PK_SEQ from sanpham where ma='"+ cksp.getMasp() +"'";
					String pk_seq="";
					ResultSet rsSp = db.get(queryidsp);
					if(rsSp!=null)
					{
						if(rsSp.next())
						{
							pk_seq = rsSp.getString("PK_SEQ");
							rsSp.close();
						}
					}
					else
					{
						this.MSG = "Lổi trong lúc lấy PK sản phẩm : " + queryidsp;
						db.getConnection().rollback();
						return false;
					}
					//Lay avai hien tai
					 query = "insert nhomfocuskpi_sp(NHOMFOCUSKPI_FK , SANPHAM_FK, PHANTRAM) " +
					"values('" + this.ID + "', '" + pk_seq +"', '"+ cksp.getPhantram() +"')";		
					if(!db.update(query))
					{
						this.MSG = "Không thể tạo mới nhomfocuskpi_sp lỗi: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
			}
			 
			/*query="delete nhomfocus_vung  where nhomfocus_fk="+this.ID;
			
			if(!db.update(query))
			{
				this.MSG = "Không thể tạo mới nhomfocus_vung lỗi: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			for(int i=0;i<mangvung.length;i++){
				double chiteu=0;
				try{
					
				  chiteu=Double.parseDouble(request.getParameter("CT"+mangvung[i]));
				  
				}catch(Exception er){
					
				}
				
				 query = "insert nhomfocus_vung(NHOMFOCUS_FK ,vung_fk,chitieu) " +
					"values('" + this.ID + "', '" + mangvung[i] +"',"+chiteu+")";		
					if(!db.update(query))
					{
						this.MSG = "Không thể tạo mới nhomfocus_sp lỗi: " + query;
						db.getConnection().rollback();
						return false;
					}
				
			}*/
			
			
			query="delete nhomfocuskpi_vung  where nhomfocuskpi_fk="+this.ID;
			
			if(!db.update(query))
			{
				this.MSG = "Không thể tạo mới nhomfocuskpi_vung lỗi: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			for(int i=0;i<mangvung.length;i++){
				double chiteu=0;
				try{
					
				  chiteu=Double.parseDouble(request.getParameter("CT"+mangvung[i]));
				  
				}catch(Exception er){
					
				}
				
				 query = "insert nhomfocuskpi_vung(NHOMFOCUSKPI_FK ,vung_fk) " +
					"values('" + this.ID + "', '" + mangvung[i] +"')";		
					if(!db.update(query))
					{
						this.MSG = "Không thể tạo mới nhomfocuskpi_sp lỗi: " + query;
						db.getConnection().rollback();
						return false;
					}
				
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;
		} 
		catch (Exception e) 
		{
			db.update("rollback");
			this.MSG="Đã có lỗi xảy ra không thể cập nhật ( "+e.getMessage()+" )";
			return false;
		}
	}

	
	public void close() 
	{
		
		try 
		{
			if(this.rsKenhbanhang != null)
				this.rsKenhbanhang.close();
			if(this.rsDvkd != null)
				this.rsDvkd.close();
			if(this.rsNhomfocus!= null)
				this.rsNhomfocus.close();
			if(this.spList!= null)
				this.spList.clear();
			if(this.rsKhuvuc!=null)
				this.rsKhuvuc.close();
			if(this.RsVung!=null)
				this.RsVung.close();
			
			this.db.shutDown();
		}
		catch (Exception e) 
		{
			
		}
	}

	public void setDvkdList(ResultSet Dvkdlist) 
	{
		this.rsDvkd=Dvkdlist;
	}

	public ResultSet getDvkdList() 
	{
		return this.rsDvkd;
	}

	public String getMsg() 
	{
		return this.MSG;
	}

	public void setMsg(String msg) 
	{
		this.MSG=msg;
	}

	@Override
	public ResultSet getkhuvuc() {
		// TODO Auto-generated method stub
		return this.rsKhuvuc;
	}

	@Override
	public ResultSet getVung() {
		// TODO Auto-generated method stub
		return this.RsVung;
	}

	@Override
	public String getKhuvucStr() {
		// TODO Auto-generated method stub
		return this.khuvucStr;
	}

	@Override
	public void setKhuvucStr(String khuvucstr) {
		// TODO Auto-generated method stub
		this.khuvucStr=khuvucstr;
	}

	@Override
	public String getVungStr() {
		// TODO Auto-generated method stub
		return this.VungStr;
	}

	@Override
	public void setVungStr(String Vungstr) {
		// TODO Auto-generated method stub
		this.VungStr=Vungstr;
	}

	@Override
	public String getDiengiai() {
		// TODO Auto-generated method stub
		return this.diengiai;
	}

	@Override
	public void setDiengiai(String dg) {
		// TODO Auto-generated method stub
		this.diengiai = dg;
	}

}
