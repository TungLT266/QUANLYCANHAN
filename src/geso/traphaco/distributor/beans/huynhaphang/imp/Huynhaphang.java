package geso.traphaco.distributor.beans.huynhaphang.imp;

import geso.traphaco.distributor.beans.huynhaphang.IHuynhaphang;
import geso.traphaco.distributor.beans.huynhaphang.ISanpham;
import geso.traphaco.distributor.beans.huynhaphang.imp.Sanpham;

import geso.traphaco.distributor.db.sql.*;
import geso.traphaco.distributor.util.*;
import java.sql.ResultSet;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;


public class Huynhaphang implements IHuynhaphang 
{	
	private static final long serialVersionUID = -9217977546733610214L;

	String userId;
	String id; //ma don hang
	String ngaygiaodich;
	
	String trangthai;
	String ngaytao;
	String nguoitao;
	String ngaysua;
	String nguoisua;
	String msg;
	String nppId;
	String nppTen;
	String SoChungtu;
	String NgayChungtu;
	String NhaphangId;
	String Khoid;
	String KBhid;
	

	List<ISanpham> splist;
	dbutils db;

	public Huynhaphang(String id)
	{
		this.NhaphangId="";
		this.id = id;
		this.ngaygiaodich = "";
		this.nppTen = "";
		this.trangthai = "";
		this.ngaytao = "";
		this.nguoitao = "";
		this.ngaysua = "";
		this.nguoisua = "";	
		this.msg = "";
		this.SoChungtu="";
		this.NgayChungtu="";
		db = new dbutils();	
		this.splist=new  ArrayList<ISanpham>();
		
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
	
	
	
	public String getNgaygiaodich() 
	{	
		return this.ngaygiaodich;
	}

	public void setNgaygiaodich(String ngaygiaodich) 
	{
		this.ngaygiaodich = ngaygiaodich;		
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
	
	public String getNguoitao() 
	{		
		return this.nguoitao;
	}
	
	public void setNguoitao(String nguoitao) 
	{
		this.nguoitao = nguoitao;		
	}

	public String getNgaysua() 
	{		
		return this.ngaysua;
	}
	
	public void setNgaysua(String ngaysua) 
	{
		this.ngaysua = ngaysua;	
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
	

	
	
	public List<ISanpham> getSpList()
	{	
		return this.splist;
	}
	
	public void setSpList(List<ISanpham> splist) 
	{
		this.splist = splist;
	}

	private void getNppInfo()
	{		
		Utility util=new Utility();
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();
		
	}
 
	public void createRS() 
	{
		try{
			this.splist.clear();
		 
			String sql=  " SELECT NH.DONVI AS DONVITINH, NH.NPP_FK, NH.SANPHAM_FK AS spid, " +
						 "  NH.NHAPHANGID,NH.KHO_FK,NH.KBH_FK ,  "+ 
						 "  NH.CHUNGTU,NH.NGAYCHUNGTU ,NH.MA,NH.TEN,NH.SOLO,NH.NGAYHETHAN,NH.SOLUONG ,  "+ 
						 "  (SELECT ISNULL(AVAILABLE,0) FROM NHAPP_KHO_CHITIET A WHERE A.KHO_FK=NH.KHO_FK AND A.KBH_FK=NH.KBH_FK      "+ 
						 "  AND A.NPP_FK=NH.NPP_FK AND A.SANPHAM_FK=NH.SANPHAM_FK AND NH.SOLO=A.SOLO )  AS SOLUONGKHOCT     "+ 
						 "  ,(SELECT ISNULL(AVAILABLE,0) FROM NHAPP_KHO B WHERE B.KHO_FK=NH.KHO_FK AND B.KBH_FK=NH.KBH_FK      "+ 
						 "  AND B.NPP_FK=NH.NPP_FK AND B.SANPHAM_FK=NH.SANPHAM_FK)  AS SOLUONGKHOTONG    "+ 
						 "  FROM (  "+ 
						 "  SELECT DVDL.DONVI ,NPP_FK,SP.PK_SEQ AS SANPHAM_FK,  NH.PK_SEQ AS NHAPHANGID,NHSP.KHO_FK,NH.KBH_FK ,  "+ 
						 "  NH.CHUNGTU,NH.NGAYCHUNGTU ,SP.MA,SP.TEN,SUM(NHSP.SOLUONG) AS SOLUONG ,NHSP.SOLO,NHSP.NGAYHETHAN   "+ 
						 "  FROM NHAPHANG NH       "+ 
						 "  INNER JOIN NHAPHANG_SP NHSP ON NH.PK_SEQ=NHSP.NHAPHANG_FK      "+ 
						 "  INNER JOIN SANPHAM SP ON SP.MA=NHSP.SANPHAM_FK  " +
						 "  INNER  JOIN DONVIDOLUONG  DVDL  ON DVDL.PK_SEQ=SP.DVDL_FK  " + 
						 "  WHERE ISNULL(NH.SOCHUNGTUDOI,NH.CHUNGTU)='"+this.SoChungtu+"' AND NH.TRANGTHAI=1 AND NH.NPP_FK='"+this.nppId+"'   AND NH.PK_SEQ   "+ 
						 "  NOT IN (SELECT NHAPHANG_FK FROM HUYNHAPHANG WHERE NHAPHANG_FK IS NOT NULL AND TRANGTHAI<>2)   "+ 
						 "  GROUP BY DVDL.DONVI,NPP_FK ,SP.PK_SEQ ,NH.PK_SEQ,NHSP.KHO_FK,NH.KBH_FK,NH.CHUNGTU,NH.NGAYCHUNGTU ,SP.MA,SP.TEN,  "+
						 "  NHSP.SOLO, NHSP.NGAYHETHAN   "+ 
						 "  ) AS NH ";
			
	 
		System.out.print(sql);
		ResultSet rs=db.get(sql);
		while(rs.next()){
			 			
			this.NgayChungtu=rs.getString("ngaychungtu");
			this.NhaphangId=rs.getString("NhaphangId");
			this.Khoid=rs.getString("kho_fk");
			this.KBhid=rs.getString("kbh_fk");
 
			if(rs.getDouble("soluong")  <= rs.getDouble("SOLUONGKHOCT") && rs.getDouble("soluong")  <= rs.getDouble("SOLUONGKHOTONG")){
				ISanpham sp=new Sanpham();
				sp.setId(rs.getString("spid"));
				sp.setMasanpham(rs.getString("ma"));
				sp.setTensanpham(rs.getString("ten"));
				sp.setSoLo(rs.getString("solo"));
				sp.setNgayhethan(rs.getString("ngayhethan"));
				sp.setDonvitinh(rs.getString("donvitinh"));
				sp.setSoluong(""+rs.getDouble("soluong"));
				sp.setISXUATKHO("0");
				this.splist.add(sp);
			}else{
		 
				ISanpham sp=new Sanpham();
				sp.setId(rs.getString("spid"));
				sp.setMasanpham(rs.getString("ma"));
				sp.setTensanpham(rs.getString("ten"));
				sp.setSoLo(rs.getString("solo"));
				sp.setNgayhethan(rs.getString("ngayhethan"));
				sp.setDonvitinh(rs.getString("donvitinh"));
				sp.setSoluong(""+rs.getDouble("soluong"));
				sp.setISXUATKHO("1");
				this.splist.add(sp);
			}			
		}
		}catch(Exception er){
			er.printStackTrace();
			
		}
		this.getNppInfo();
		
	}
	
	
	public void init() 
	{
		try{
			this.splist.clear();
			
		String sql=     " select sp.pk_seq as spid,nhsp.giagross,nhsp.gianet, nh.pk_seq as  " +
						" nhaphangid,nh.kho_fk,nh.kbh_fk , nhsp.DONVITINH, "+
						" nh.SOCHUNGTU,nh.NGAYCHOT,NHAPHANG.NGAYCHUNGTU ,sp.ma,sp.ten,nhsp.soluong,nhsp.solo,nhsp.ngayhethan ,nhsp.ISXUATKHO "+
						" from HUYNHAPHANG nh "+
						" inner join NHAPHANG on NHAPHANG.PK_SEQ=nh.NHAPHANG_FK "+
						" inner join HUYNHAPHANG_SP nhsp on nh.pk_seq=nhsp.HUYNHAPHANG_FK "+  
						" inner join sanpham sp on sp.PK_SEQ=nhsp.SANPHAM_FK       "+
						" where nh.PK_SEQ="+this.id;
		
		 
		ResultSet rs=db.get(sql);
		while(rs.next()){
			this.NgayChungtu=rs.getString("ngaychungtu");
			this.SoChungtu=rs.getString("SOCHUNGTU");
			
			this.NhaphangId=rs.getString("NhaphangId");
			this.Khoid=rs.getString("kho_fk");
			this.KBhid=rs.getString("kbh_fk");
				ISanpham sp=new Sanpham();
				sp.setId(rs.getString("spid"));
 
				sp.setMasanpham(rs.getString("ma"));
				sp.setTensanpham(rs.getString("ten"));
				sp.setSoLo(rs.getString("solo"));
				sp.setNgayhethan(rs.getString("ngayhethan"));
				sp.setDonvitinh(rs.getString("donvitinh"));
				sp.setSoluong(rs.getString("soluong"));
				sp.setISXUATKHO(rs.getString("ISXUATKHO"));
				this.splist.add(sp);
			
			
		}
		}catch(Exception er){
			er.printStackTrace();
			
		}
		this.getNppInfo();
		
	}
		

	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	public void DBclose()
	{
		try 
		{
			
			if(this.db != null)
				this.db.shutDown();
		} 
		catch(Exception e) {}
	}
	public boolean CreateDhtv() 
	{		
		dbutils db = new dbutils();
		this.ngaytao = getDateTime();
		this.nguoisua = this.userId;
		try 
		{
			
			this.createRS();
			
			db.getConnection().setAutoCommit(false);		
			
			String sql = "select Replace(convert(char(10), DATEADD(day, 1, cast(max(ngayks) as datetime)) , 102) , '.', '-' ) as ngay from khoasongay " +
					" where npp_fk= "+this.nppId+" ";
			System.out.println(sql);
			this.ngaygiaodich="";
			ResultSet rs=db.get(sql);
			if(rs.next()){
				this.ngaygiaodich=rs.getString("ngay");
			}
			rs.close();
			if(	this.ngaygiaodich.equals("")){
				this.msg="Chưa xác định ngày khóa sổ ,vui lòng kiểm tra lại";
				db.update("rollback");
				return false;
			}
			
			
			
			sql=" INSERT INTO HUYNHAPHANG  (NPP_FK,SOCHUNGTU,NHAPHANG_FK,KBH_FK,KHO_FK,NGAYTAO,NGUOITAO,NGAYSUA,NGUOISUA,NGAYCHOT,TRANGTHAI) "+ 
						   " VALUES  ('"+this.nppId+"','"+this.SoChungtu+"',"+this.NhaphangId+","+this.KBhid+","+this.Khoid+",'"+this.ngaytao+"','"+this.userId+"','"+this.ngaysua+"','"+this.userId+"','"+this.ngaygiaodich+"','0') ";
				
				if(!db.update(sql)){
					db.update("rollback");
					this.msg = "Loi khi cap nhat bang "+sql;
					return false; 
				}
					
				
				sql = "select IDENT_CURRENT('HUYNHAPHANG') as dhId";
				
				ResultSet rsDh = this.db.get(sql);
				rsDh.next();
				this.id = rsDh.getString("dhId");
				rsDh.close();
				
				
				for(int i=0;i<this.splist.size();i++){
					ISanpham sp=this.splist.get(i);
					sql="INSERT INTO  HUYNHAPHANG_SP (HUYNHAPHANG_FK,SANPHAM_FK,MA,SOLUONG,SOLO,NGAYHETHAN,ISXUATKHO,DONVITINH)  " +
							" VALUES ('"+this.id+"','"+sp.getId()+"','"+sp.getMasanpham()+"','"+sp.getSoluong()+"','"+sp.getSoLo()+"','"+sp.getNgayhethan()+"' ,'"+sp.getISXUATKHO()+"',N'"+sp.getDonvitinh()+"') ";
					
					if(!db.update(sql)){
						db.update("rollback");
						this.msg = "Loi khi cap nhat bang "+sql;
						return false; 
					}
					if(sp.getISXUATKHO().trim().equals("0"))
					{
						sql=" Update nhapp_kho set booked=booked+"+sp.getSoluong()+" ,available=available -"+sp.getSoluong()+"  where  " +
						" sanpham_fk="+sp.getId()+" and kho_fk="+this.Khoid+" and kbh_fk="+this.KBhid+"  and npp_fk="+this.nppId+"";
				
						 
						if(!db.update(sql)){
							db.update("rollback");
							this.msg = "Loi khi cap nhat bang "+sql;
							return false; 
						}
						sql=" Update nhapp_kho_chitiet set booked=booked+"+sp.getSoluong()+" ,available=available -"+sp.getSoluong()+"   where  " +
						" sanpham_fk="+sp.getId()+" and kho_fk="+this.Khoid+" and kbh_fk="+this.KBhid+"  and npp_fk="+this.nppId+" AND SOLO='"+sp.getSoLo()+"'";
						if(!db.update(sql)){
							db.update("rollback");
							this.msg = "Loi khi cap nhat bang "+sql;
							return false; 
						}
						
					}
				}
				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
		}
		catch(Exception e1) {
			e1.printStackTrace();
			db.update("rollback");
			this.msg = "Loi khi cap nhat bang "+e1.toString();
			return false; 
		}
		return true;
	}

	@Override
	public String getSochungtu() {
		// TODO Auto-generated method stub
		return this.SoChungtu;
	}

	@Override
	public void setSochungtu(String sochungtu) {
		// TODO Auto-generated method stub
		this.SoChungtu=sochungtu;
		
	}

	@Override
	public String getNgaychungtu() {
		// TODO Auto-generated method stub
		return this.NgayChungtu;
	}

	@Override
	public void setNgaychungtu(String ngaychungtu) {
		// TODO Auto-generated method stub
		this.NgayChungtu=ngaychungtu;
	}

	@Override
	public String getNhaphangId() {
		// TODO Auto-generated method stub
		return this.NhaphangId;
	}

	@Override
	public void setNhaphangId(String NhaphangId) {
		// TODO Auto-generated method stub
		this.NhaphangId=NhaphangId;
		
	}

	@Override
	public String getKhoId() {
		// TODO Auto-generated method stub
		return this.Khoid;
	}

	@Override
	public void setkhoId(String _KhoId) {
		// TODO Auto-generated method stub
		this.Khoid=_KhoId;
		
	}

	@Override
	public String getKBHId() {
		// TODO Auto-generated method stub
		return this.KBhid;
	}

	@Override
	public void setKBHId(String _KbhId) {
		// TODO Auto-generated method stub
		this.KBhid=_KbhId;
		
	}
	
}
