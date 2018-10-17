package geso.traphaco.erp.beans.danhmucvattu.imp;

import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.beans.danhmucvattu.IDanhmucvattu;
import geso.traphaco.erp.beans.danhmucvattu.IDanhmucvattu_SP;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Danhmucvattu  extends Phan_Trang implements IDanhmucvattu, Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	dbutils db;
	
	String Id;
	String HieuLucTu;
	String HieuLucDen;
	String MaSpSx;
	String TenSpSx;
	String SoLuongSx;
	String NgayTao;
	String NguoiTao;
	String NgaySua;
	String NguoiSua;
	String Msg;
	String Dvt;
	String TrangThai;
	String userten;
	String userid;
	
	ResultSet dmlist;
	ResultSet RsMaSpSx;
	List<IDanhmucvattu_SP> listdanhmuc = new ArrayList<IDanhmucvattu_SP>();
	
	public Danhmucvattu()
	{
		db = new dbutils();
		Id = "";
		HieuLucTu = "";
		HieuLucDen = "";
		MaSpSx = "";
		TenSpSx = "";
		SoLuongSx = "";
		NgayTao = "";
		NguoiTao = "";
		NgaySua = "";
		NguoiSua = "";
		Msg = "";
		Dvt = "";
		TrangThai = "";
		userid = "";
	}
	
	public Danhmucvattu(String id)
	{
		db=new dbutils();
		this.Id=id;

		
		String sql = "select SANPHAM_FK,A.MA, soluongsx, hieuluctu, hieulucden, a.ten as tenspsx, a.dv as dvt "+
					" from erp_danhmucvattu b "+
					" inner join (select  sp.pk_seq,ma, ten,dv.donvi as dv from ERP_SanPham sp"+
					" inner join donvidoluong dv on dv.pk_seq = sp.dvdl_fk ) a on a.PK_SEQ = b.SANPHAM_FK where B.pk_seq = '"+this.Id+"' ";
		
		System.out.println("SQL: "+sql);
		ResultSet rs=db.get(sql);
		try
		{
			if (rs != null)
			{
				if(rs.next())
				{
					this.HieuLucTu = rs.getString("hieuluctu");
					this.HieuLucDen = rs.getString("hieulucden");
					this.MaSpSx = rs.getString("MA");
					this.TenSpSx = rs.getString("tenspsx"); 					 				
	 				this.SoLuongSx = rs.getString("soluongsx");
	 				this.Dvt = rs.getString("dvt");
	 				this.setMessage(""); 			
				}
				rs.close();
			}
			//Thuc hien lay thong tin don hang
		    	String sql_getdetail=" select danhmucvt_fk,sp.MA as masp, sp.ten as tenvattu, soluong, DVDL.donvi as donvitinh"+ 
			 			  " from erp_danhmucvattu_vattu vt"+
			 			  " inner join sanpham sp on sp.pk_seq = vt.SANPHAM_FK"+
			 			  " inner join DONVIDOLUONG DVDL ON DVDL.PK_sEQ=SP.DVDL_FK "+
			 				          "	where danhmucvt_fk ="+this.Id;
		    	System.out.println("Cau select : lines 95 " + sql_getdetail);
		    	
		    	rs=db.get(sql_getdetail);
		    	if(rs!=null)
		    	{
		    		while(rs.next()){
		    			
		    			IDanhmucvattu_SP sanpham=new Danhmucvattu_SP();	
		    			sanpham.setIdVT(rs.getString("danhmucvt_fk"));
		    			sanpham.setDvtVT(rs.getString("donvitinh"));		    			
		    			sanpham.setMaVatTu(rs.getString("masp"));
		    			sanpham.setTenVatTu(rs.getString("tenvattu"));		    			
		    			sanpham.setSoLuong(rs.getString("soluong"));
		    			
		    			this.listdanhmuc.add(sanpham);
		    		}
		    	}
		    	
			if(rs!=null){
				rs.close();
			}
		}catch(Exception er)
		{
			System.out.println("Error  here: Danhmucvattu.java line  : 113 "+er.toString());
		}
	}
	

	@Override
	public String getId()
	{
		return Id;
	}

	@Override
	public void setId(String id) {
		
		this.Id = id;
	}

	@Override
	public String getHieuLucTu() {
		
		return HieuLucTu;
	}

	@Override
	public void setHieuLucTu(String hieuluctu) {
		
		this.HieuLucTu = hieuluctu;
	}

	@Override
	public String getHieuLucDen() {
		
		return HieuLucDen;
	}

	@Override
	public void setHieuLucDen(String hieulucden) {
		
		this.HieuLucDen = hieulucden;
	}

	@Override
	public String getMaSpSxId() {
		
		return MaSpSx;
	}

	@Override
	public void setMaSpSxId(String maspsxid) {
		
		this.MaSpSx = maspsxid;
	}

	@Override
	public ResultSet getRsMaSpSx() {
		
		return RsMaSpSx;
	}

	@Override
	public void setRsMaSpSx(ResultSet rsmaspsx) {
		
		this.RsMaSpSx = rsmaspsx;
	}

	@Override
	public String getTenSpSx() {
		
		return TenSpSx;
	}

	@Override
	public void setTenSpSx(String tenspsx) {
		
		this.TenSpSx = tenspsx;
	}

	@Override
	public String getDvt() {
		
		return Dvt;
	}

	@Override
	public void setDvt(String dvt) {
		
		this.Dvt = dvt;
	}

	@Override
	public String getSoLuongSx() {
		
		return SoLuongSx;
	}

	@Override
	public void setSoLuongSx(String soluongsx) {
		
		this.SoLuongSx = soluongsx;
	}

	@Override
	public String getTrangThai() {
		
		return TrangThai;
	}

	@Override
	public void setTrangThai(String trangthai) {
		
		this.TrangThai = trangthai;
	}

	@Override
	public String getMessage() {
		
		return Msg;
	}

	@Override
	public void setMessage(String msg) {
		
		this.Msg = msg;
	}

	@Override
	public void setListDanhMuc(List<IDanhmucvattu_SP> list) {
		
		this.listdanhmuc = list;
	}

	@Override
	public List<IDanhmucvattu_SP> getListDanhMuc() {
		
		return this.listdanhmuc;
	}

	@Override
	public void init() {
	
			
		CreateDmList("");
	}

	@Override
	public void setUserTen(String userten) {
		
		this.userten=userten;
	}

	@Override
	public String getUserTen() {
		
		return this.userten;
	}

	@Override
	public boolean Save() {
		
		String sql="";
		try
		{
			if(this.NguoiTao==null || this.NguoiTao.equals("")){
				this.Msg="User Dang Nhap Tam Thoi Bi Log Vi Che Do Bao Mat, Vui Long Dang Nhap Lai De Thuc Hien Chuc Nang Nay";
				return false;
			}
			
			if(this.MaSpSx==null || this.MaSpSx.equals("")){
				this.Msg="Mã Sản Phẩm Sản Xuất Không Được Rỗng !";
				return false;
			}
			
			  db.getConnection().setAutoCommit(false);	
			  
			  sql = "select PK_SEQ from ERP_SanPham where ma='"+this.getMaSpSxId()+"'";
			  System.out.println(sql);
		        String SPID = "";
		        
		        ResultSet RS = db.get(sql);
		        if(RS!=null)
		        {
		              try 
		              {
		                    if(RS.next())
		                    {
		                    	SPID = RS.getString("PK_SEQ");
		                    	RS.close();
		                    }
		              } 
		              catch (SQLException e) 
		              {
		                    db.update("rollback");
		                    this.Msg="Lỗi trong lúc lấy PK sản phẩm : " + sql;  
		                    return false;
		              }
		        }
		        else
		        {
		              db.update("rollback");
		              this.Msg="Lỗi trong lúc lấy PK sản phẩm : " + sql;
		              return false;
		        }
	 			
		   sql = "INSERT INTO ERP_DANHMUCVATTU(HIEULUCTU, HIEULUCDEN, SANPHAM_FK, SOLUONGSX, NGUOITAO, NGUOISUA, NGAYTAO, NGAYSUA, TRANGTHAI) " +
		   		"VALUES ('"+this.HieuLucTu+"','"+this.HieuLucDen+"','"+SPID+"','"+this.SoLuongSx+"','"+this.NguoiTao+"','"+this.NguoiSua+"','"+this.NgayTao+"','"+this.NgaySua+"','0')";

			System.out.println("sql (Erp_danhmucvattu : 229) " + sql);  
			
		if(!db.update(sql)){
			db.update("rollback");
			this.Msg = "Khong The Cap Nhat ,Loi Tren Dong Lenh Sau :" + sql;
			return false;
		}	
		String query = "select IDENT_CURRENT('ERP_DANHMUCVATTU') as dmId";
		ResultSet rsDm = db.get(query);	
		if (rsDm != null)
		{
			try
			{
				if (rsDm.next())
					this.Id = rsDm.getString("dmId");
		     	rsDm.close();		     			     	
			}
			catch(Exception er){					
				db.update("rollback");
				this.setMessage("Loi  - Clasname :Danhmucvattu - line 319 : "+ er.toString());
			}
		}		
		//Save chi tiet don hang
		int count = 0;						
		while(count < this.listdanhmuc.size())
		{	System.out.println("size list danh muc : "+listdanhmuc.size());
			IDanhmucvattu_SP danhmuc = new Danhmucvattu_SP();
			danhmuc =listdanhmuc.get(count);
			
			
			String queryidsp = "select PK_SEQ from ERP_SanPham where ma='"+danhmuc.getMaVatTu()+"'";
            String idsp = "";
            
            ResultSet rsSp = db.get(queryidsp);
            if(rsSp!=null)
            {
                  try 
                  {
                        if(rsSp.next())
                        {
                              idsp = rsSp.getString("PK_SEQ");
                        }
                        rsSp.close();
                  } 
                  catch (SQLException e) 
                  {
                        db.update("rollback");
                        this.Msg="Lỗi trong lúc lấy PK sản phẩm : " + queryidsp;  
                        return false;
                  }
            }
            else
            {
                  db.update("rollback");
                  this.Msg="Lỗi trong lúc lấy PK sản phẩm : " + queryidsp;
                  return false;
            }

			
			
				String sql1=" insert into erp_danhmucvattu_vattu (danhmucvt_fk, SANPHAM_FK, soluong)" +
					  " values ('"+this.Id+"','"+idsp+"','"+danhmuc.getSoLuong()+"')";
				
					System.out.println("Cau lenh SQL insert erp_danhmucvattu_vattu " + sql1);										
					if(!db.update(sql1)){						
						this.Msg="Khong the luu ma san pham sx nay,Loi trong dong lenh sau : " + sql1;
						db.update("rollback");
						return false; }
			
			count++;		
		}
	 /*
	 * Chuong trinh 
	 */
		db.getConnection().commit();
		db.getConnection().setAutoCommit(true);
		}
		catch(Exception er)
		{
			db.update("rollback");
			this.setMessage( er.toString() + " Error : " +  sql);	
			return false;
		}
		return true;
	}

	@Override
	public boolean Edit(String _ischot) {
		
		return false;
	}

	@Override
	public boolean Delete() {
		
		return false;
	}

	@Override
	public void DBClose()
	{		
		try
		{		
			if (this.listdanhmuc != null)
				this.listdanhmuc.clear();
			if (this.dmlist != null)
				this.dmlist.close();
			if( RsMaSpSx != null)
			{
				RsMaSpSx.close();
			}
			if(db!=null)
			{
				db.shutDown();
			}
		}catch(Exception err){
			err.printStackTrace();
		}
	}

	@Override
	public void setUserid(String userid) {
		
		this.userid = userid;
	}

	@Override
	public String getUserid() {
		
		return userid;
	}

	@Override
	public void CreateDmList(String sql) {
		
		String query ="";
		if(sql.length()<= 0)
			/*query = "select * from erp_danhmucvattu";*/
			query = "select vt.pk_seq,sp.ma as masp, vt.sanpham_fk , soluongsx, hieuluctu, hieulucden, vt.ngaytao, nt.ten as nguoitao, " +
					"	vt.ngaysua, ns.ten as nguoisua, vt.trangthai"+
					" from erp_danhmucvattu vt inner join  nhanvien nt  on  vt.nguoitao = nt.pk_seq  " +
					" inner join nhanvien ns on vt.nguoisua = ns.pk_seq " +
					" inner join sanpham sp on sp.pk_Seq=vt.sanpham_fk " ;
					
		else query = sql; 
		
		System.out.println("query list "+ query);
	 this.dmlist=db.get(query);
	}

	@Override
	public ResultSet getDmList() 
	{
		return this.dmlist;
	}

	@Override
	public void setNgaytao(String ngaytao) 
	{	
		this.NgayTao = ngaytao;
	}

	@Override
	public String getNgaytao() 
	{	
		return NgayTao;
	}

	@Override
	public void setNgaysua(String ngaysua) 
	{		
		this.NgaySua = ngaysua;
	}

	@Override
	public String getNgaysua() 
	{		
		return NgaySua;
	}

	@Override
	public void setNguoitao(String nguoitao) 
	{		
		this.NguoiTao = nguoitao;
	}

	@Override
	public String getNguoitao() 
	{		
		return NguoiTao;
	}

	@Override
	public void setNguoisua(String nguoisua) 
	{		
		this.NguoiSua = nguoisua;
	}

	@Override
	public String getNguoisua() 
	{		
		return NguoiSua;
	}

	@Override
	public boolean Update()
	{
		String sql="";		
		try
		{
			if(this.NguoiTao==null || this.NguoiTao.equals("")){
				this.Msg="User Dang Nhap Tam Thoi Bi Log Vi Che Do Bao Mat, Vui Long Dang Nhap Lai De Thuc Hien Chuc Nang Nay";
				return false;
			}
			
			if(this.MaSpSx==null || this.MaSpSx.equals("")){
				this.Msg="Mã Sản Phẩm Sản Xuất Không Được Rỗng !";
			}
			
			  db.getConnection().setAutoCommit(false);					 
			  sql = "select PK_SEQ from ERP_SanPham where ma='"+this.getMaSpSxId()+"'";
			  System.out.println(sql);
	            String SPID = "";
	            
	            ResultSet RS = db.get(sql);
	            if(RS!=null)
	            {
	                  try 
	                  {
	                        if(RS.next())
	                        {
	                        	SPID = RS.getString("PK_SEQ");
	                        }
	                        RS.close();
	                  } 
	                  catch (SQLException e) 
	                  {
	                        db.update("rollback");
	                        this.Msg="Lỗi trong lúc lấy PK sản phẩm : " + sql;  
	                        return false;
	                  }
	            }
	            else
	            {
	                  db.update("rollback");
	                  this.Msg="Lỗi trong lúc lấy PK sản phẩm : " + sql;
	                  return false;
	            }
		    sql = "UPDATE ERP_DANHMUCVATTU SET HIEULUCTU = '"+this.HieuLucTu+"', HIEULUCDEN = '"+this.HieuLucDen+"', SANPHAM_FK = '"+SPID+"', SOLUONGSX = '"+this.SoLuongSx+"', NGUOITAO = '"+this.NguoiTao+"', NGUOISUA = '"+this.NguoiSua+"', NGAYTAO = '"+this.NgayTao+"', NGAYSUA = '"+this.NgaySua+"', TRANGTHAI = '0' " +
		    		"	where pk_seq = '"+this.Id+"'";

			System.out.println("sql (Erp_danhmucvattu : 229) " + sql);
			
		if(!db.update(sql)){
			db.update("rollback");
			this.Msg = "Khong The Cap Nhat ,Loi Tren Dong Lenh Sau :" + sql;
			return false;
		}	
		
		//xoa het chi tiet cu 
		 sql="DELETE FROM erp_danhmucvattu_vattu WHERE danhmucvt_fk = " + this.Id ;
		 System.out.println("Lenh xoa : "+sql);
		if(!db.update(sql))
		{
			db.update("rollback");
			this.Msg="Loi Tren dong lenh sau (522) :" + sql;
			return false;
		}
		
		//Luu chi tiet danh muc vat tu
		int count = 0;						
		while(count < this.listdanhmuc.size())
		{	
			/*System.out.println("size list danh muc : "+listdanhmuc.size());*/
			IDanhmucvattu_SP danhmuc = new Danhmucvattu_SP();
			danhmuc =listdanhmuc.get(count);
			sql = "select PK_SEQ from ERP_SanPham where ma='"+danhmuc.getMaVatTu()+"'";
            String idsp = "";
            
            ResultSet rsSp = db.get(sql);
            if(rsSp!=null)
            {
                  try 
                  {
                        if(rsSp.next())
                        {
                              idsp = rsSp.getString("PK_SEQ");
                        }
                        rsSp.close();
                  } 
                  catch (SQLException e) 
                  {
                        db.update("rollback");
                        this.Msg="Lỗi trong lúc lấy PK sản phẩm : " + sql;  
                        return false;
                  }
            }
            else
            {
                  db.update("rollback");
                  this.Msg="Lỗi trong lúc lấy PK sản phẩm : " + sql;
                  return false;
            }

			
			
            String sql1= " insert into erp_danhmucvattu_vattu (danhmucvt_fk, sanpham_fk, soluong)"+
            			 " values ('"+this.Id+"','"+idsp+"','"+danhmuc.getSoLuong()+"')";
				
			System.out.println("Cau lenh SQL insert erp_danhmucvattu_vattu " + sql1);										
			if(!db.update(sql1))
			{						
				this.Msg="Khong the luu ma san pham sx nay,Loi trong dong lenh sau : " + sql1;
				db.update("rollback");
				return false;
			}			
			count++;		
		}
	 /*
	 * Chuong trinh 
	 */
		db.getConnection().commit();
		db.getConnection().setAutoCommit(true);
		}
		catch(Exception er)
		{
			db.update("rollback");
			this.setMessage( er.toString() + " Error : " +  sql);	
			return false;
		}	
		return true;
	}
}