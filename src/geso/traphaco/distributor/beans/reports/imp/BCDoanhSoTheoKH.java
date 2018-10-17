package geso.traphaco.distributor.beans.reports.imp;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import geso.traphaco.distributor.beans.reports.IBCDoanhSoTheoKH;
import geso.traphaco.distributor.db.sql.dbutils;

public class BCDoanhSoTheoKH implements IBCDoanhSoTheoKH, Serializable {

	/**
   * 
   */
  private static final long serialVersionUID = 1L;
	private String userId;
	private ResultSet rsBCDoanhSoTheoKH;
	private ResultSet rsBCDoanhSoTheoKHGroup;
	private String nppID;	
	private ResultSet rsTDV , rsChiNhanh;
	
	private String tdvId , chinhanhId;
	
	private String nppTen, tdvTen;

	private String tungay, denngay;
	private String loai , tt;
	
	private String vungId, khuvucId;
	private ResultSet vungRs, khuvucRs;
	private ResultSet tinhthanhRs;
	private String TinhthanhId;

	public  BCDoanhSoTheoKH() {
		tungay = "";
		denngay = "";
		nppID ="";
		tdvId = "";
		nppTen = "";
		tdvTen = "";
		loai = "";
		tt="";
		chinhanhId="";
		this.cndt="1";
		this.kh="1";
		
	}
	
	@Override
	public void init() 
	{
		// TODO Auto-generated method stub
		dbutils db = new dbutils();
		geso.traphaco.center.util.Utility util = new geso.traphaco.center.util.Utility();	
		this.vungRs = db.get("select * from VUNG");
		this.tinhthanhRs = db.get("select * from TINHTHANH");
		if(this.vungId != null && this.vungId.length() > 0){
			this.tinhthanhRs = db.get("select * from TINHTHANH where VUNG_FK = '"+vungId+"' and pk_Seq in  "+util.Quyen_TinhThanh(this.userId));
		}
		
		if(tt.equals("1"))
		{
			if(chinhanhId.length()>0)
			{
				nppID = chinhanhId;
			}
			else
			{
				nppID = " select PK_SEQ  from NHAPHANPHOI where TRANGTHAI = 1 "+
				"AND PK_SEQ in ( select npp_fk from phamvihoatdong where nhanvien_fk ="+this.userId +") ";
				if(this.vungId != null && this.vungId.length() > 0)
					nppID += "and KHUVUC_FK in (select PK_SEQ from KHUVUC where VUNG_FK = '"+this.vungId +"')";
				if(this.TinhthanhId != null && this.TinhthanhId.length() > 0)
					nppID += "and TINHTHANH_FK = '"+this.TinhthanhId +"'";
			}
		}
		System.out.print("\nNPPID: " + nppID);
		String sql="";
		

		System.out.println("toi day 1");
		String condition="";
		if(tt.equals("1"))
		{
			condition+=" AND dh.NPP_FK in ( select npp_fk from phamvihoatdong where nhanvien_fk ="+this.userId +") ";
		}

		if(loai.equals("KH"))
		{ 
			if(nppID.length() > 0  && tungay.length() > 0 && denngay.length() > 0 )
			{	
				
				String tbName="nhomsanpham_sanpham";
				sql="select  VUNG_FK from KHUVUC where PK_SEQ IN (select KHUVUC_FK from NHAPHANPHOI where pk_Seq in ( "+this.nppID+" ) )";
				
				String  vungId="";
				ResultSet rs =db.get(sql);
				System.out.println("_______"+sql);
				try
        {
	        while(rs.next())
	        {
	        	vungId=	rs.getString("VUNG_FK")==null?"":rs.getString("VUNG_FK");
	        }
        } catch (SQLException e)
        {
	        e.printStackTrace();
        }
				if(vungId.equals("100002"))
				{
					tbName="nhomsanpham_miennam_sanpham";
				}
				else 
					if(vungId.equals("100003"))
					{
						tbName="nhomsanpham_mientrung_sanpham";
					}
				
				if(this.tdvId.length()>0)
				{
					/*condition+=" and dh.pk_seq in (select hoadon_fk from hoadon_ddh where ddh_Fk in (select pk_seq from donhang where ddkd_fk='"+this.tdvId+"'))";*/
					condition += " and dh.khachhang_fk in (select khachhang_fk from KHACHHANG_TUYENBH where tbh_fk in (select pk_Seq from tuyenbanhang where ddkd_Fk ='"+this.tdvId+"'))";
				}
				System.out.println("toi day 2");
				sql = "select t.khachhang_fk,t.npp_fk, npp.TEN as nppten, t.maFast , t.ten, t.masothue,	  	" +
					"	sum([100002]) as DsNhomxanh,sum([100003]) as Dsnhomhh,sum([100004]) as dsnhomkhac 	" +
					"	from      	" +
					"	(        	" +
					"	select kh.maFAST,kh.TEN, kh.MASOTHUE,dh.khachhang_fk,dh.npp_fk,nsp.nsp_fk,sp.dvkd_fk,    	" +
					"	( ROUND(   ROUND(dhsp.soluong*dhsp.dongia,0)*(1+dhsp.vat/100.0 ),0 )) as doanhso   	" +
					"	from HOADON dh " +
					"	inner join HOADON_SP dhsp on dhsp.hoadon_fk=dh.pk_seq       	" +
					"	inner join sanpham sp on sp.pk_Seq=dhsp.sanpham_fk    	" +
					"	inner join "+tbName+" nsp on nsp.sp_fk=dhsp.sanpham_fk 	" +
					"	inner join KHACHHANG kh on kh.PK_SEQ = dh.KHACHHANG_FK     	" +
					"	where dh.loaihoadon=0 and  dh.trangthai not in ( 1 , 3, 5 ) and  dh.ngayxuatHD   >= '"+tungay+"'       	" +
					"	and   dh.ngayxuatHD  <= '"+denngay+"' and dh.NPP_FK  in   ("+nppID+") "+ condition+" " +
					"	)as p pivot ( sum(doanhso)        	" +
					"	for nsp_fk  in  ([100002],[100003],[100004]) )as t   	" +
					"	inner join NHAPHANPHOI npp on npp.PK_SEQ = t.NPP_FK " +
					" WHERE 1=1 "+
					"	group by t.khachhang_fk,t.npp_fk, t.maFAST,t.TEN,t.MASOTHUE ,npp.TEN ";
				
				System.out.println("_______ vao 1"+sql);
				
				String group = "select db.npp_fk, db.nppten, sum(DsNhomxanh)as DsNhomxanh, sum (Dsnhomhh) as Dsnhomhh, sum(dsnhomkhac) as dsnhomkhac, tt.TEN AS TENTINH, v.TEN as VUNG"+
					"\nfrom (" +
					"\nselect t.khachhang_fk,t.npp_fk, npp.TEN as nppten, t.maFast , t.ten, t.masothue,	  	" +
					"\n	sum([100002]) as DsNhomxanh,sum([100003]) as Dsnhomhh,sum([100004]) as dsnhomkhac, npp.TINHTHANH_FK, npp.KHUVUC_FK 	" +
					"\n	from      	" +
					"\n	(        	" +
					"\n	select kh.maFAST,kh.TEN, kh.MASOTHUE,dh.khachhang_fk,dh.npp_fk,nsp.nsp_fk,sp.dvkd_fk,    	" +
					"\n	( ROUND(   ROUND(dhsp.soluong*dhsp.dongia,0)*(1+dhsp.vat/100.0 ),0 )) as doanhso   	" +
					"\n	from HOADON dh " +
					"\n	inner join HOADON_SP dhsp on dhsp.hoadon_fk=dh.pk_seq       	" +
					"\n	inner join sanpham sp on sp.pk_Seq=dhsp.sanpham_fk    	" +
					"\n	inner join "+tbName+" nsp on nsp.sp_fk=dhsp.sanpham_fk 	" +
					"\n	inner join KHACHHANG kh on kh.PK_SEQ = dh.KHACHHANG_FK     	" +
					"\n	where dh.loaihoadon=0 and  dh.trangthai not in ( 1 , 3, 5 ) and  dh.ngayxuatHD   >= '"+tungay+"'       	" +
					"\n	and   dh.ngayxuatHD  <= '"+denngay+"' and dh.NPP_FK  in   ("+nppID+") "+ condition+" " +
					"\n	)as p pivot ( sum(doanhso)        	" +
					"\n	for nsp_fk  in  ([100002],[100003],[100004]) )as t   	" +
					"\n	inner join NHAPHANPHOI npp on npp.PK_SEQ = t.NPP_FK " +
					"\n WHERE 1=1 "+
					"\n	group by t.khachhang_fk,t.npp_fk, t.maFAST,t.TEN,t.MASOTHUE ,npp.TEN, npp.TINHTHANH_FK, npp.KHUVUC_FK  " +
					"\n) as db " +
					"\ninner join TINHTHANH tt on db.TINHTHANH_FK = tt.PK_SEQ " +
					"\ninner join (select kv.PK_SEQ, v.TEN from KHUVUC kv join VUNG v on kv.VUNG_FK = v.PK_SEQ) v on v.PK_SEQ = db.KHUVUC_FK " +
					"\ngroup by db.npp_fk,  db.nppten , tt.TEN, v.TEN order by tt.Ten";
				
				System.out.println("\nBao cao dsKH :"+group);
				this.rsBCDoanhSoTheoKHGroup = db.get(group);
				this.rsBCDoanhSoTheoKH = db.get(sql);
			}
		}
		else if(loai.equals("LKH"))
		{ 
			
			condition ="";
			
			String conditionHDKhac = "";
			
			if(this.tungay.length()>0)
			{
				condition+=" and a.NgayXuatHD>='"+this.tungay+"' ";
				conditionHDKhac += " and hd.ngayhoadon >='"+this.tungay+"'";
			}
			if(this.denngay.length()>0)
			{
				condition+=" and a.NgayXuatHD <='"+this.denngay+"' ";
				conditionHDKhac += " and hd.ngayhoadon <='"+this.tungay+"'";
			}
			if(this.tdvId.length()>0)
			{
				/*condition+=" and dh.pk_seq in (select hoadon_fk from hoadon_ddh where ddh_Fk in (select pk_seq from donhang where ddkd_fk='"+this.tdvId+"'))";*/
				condition += " and a.khachhang_fk in (select khachhang_fk from KHACHHANG_TUYENBH where tbh_fk in (select pk_Seq from tuyenbanhang where ddkd_Fk ='"+this.tdvId+"'))";
			}
			
			if(nppID.length() > 0  && tungay.length() > 0 && denngay.length() > 0 )
			{	
				System.out.println("toi day 3");
				sql =
					"select t.npp_fk,  npp.ten, tt.TEN AS TENTINH, v.TEN as VUNG , 	\n" +
					"sum([0]) as BL,sum([1]) as BB,sum([2]) as BLBB , sum([3]) as BBBL	\n" +
					"from      \n	" +
					"(        \n	" +
					"			select NPP_FK,XuatKhau,SUM(DoanhSo) as DoanhSo \n"+
					"			from \n"+ 
					"			( \n"+
					"				select a.NPP_FK, \n"+
					"					( \n"+
					"						select LoaiKhachHang  \n"+
					"						from DONHANG aa inner join HOADON_DDH bb on bb.DDH_FK=aa.PK_SEQ \n"+
					"						where bb.HOADON_FK=a.PK_SEQ \n"+
					"					) as XuatKhau,round(round(b.SOLUONG*b.DONGIA,0)*(1+b.VAT/100),0) as DoanhSo \n"+
					"				from HOADON a inner join HOADON_SP b on b.HOADON_FK=a.PK_SEQ \n"+
					"				where a.TRANGTHAI not in (1,3,5) and isnull(a.LOAIHOADON,0)=0 \n"+
					"				"+condition+" and a.NPP_FK in ("+this.nppID+") \n"+
					"				union all \n"+
					"				select a.NPP_FK, \n"+
					"					( \n"+
					"						select LoaiKhachHang  \n"+
					"						from DONHANG aa inner join HOADON_DDH bb on bb.DDH_FK=aa.PK_SEQ \n"+
					"						where bb.HOADON_FK=a.PK_SEQ \n"+
					"					) as XuatKhau,(-1)*round(round(b.chietkhau,0)*(1+b.thueVAT/100),0) as DoanhSo \n"+
					"				from HOADON a inner join HOADON_CHIETKHAU b on b.HOADON_FK=a.PK_SEQ \n"+
					"				where a.TRANGTHAI not in (1,3,5) and isnull(a.LOAIHOADON,0)=0 and b.HienThi=1 \n"+
					"				"+condition+" and a.NPP_FK in ("+this.nppID+") \n"+
					
					" 				union all \n"+	
					
					" 				SELECT 	hd.npp_fk, kh.XuatKhau, isnull(hd.avat,0) as DOANHSO \n"+
					" 				FROM 	ERP_HoaDonPheLieu hd INNER JOIN KHACHHANG kh ON hd.khachhang_fk = kh.PK_SEQ \n"+
					" 				WHERE   hd.trangthai = 1  and hd.NPP_FK in ("+this.nppID+") \n"+conditionHDKhac+					
					"			)as data  \n"+
					"			group by NPP_FK,XuatKhau  \n"+
					")as p pivot ( sum(doanhso)        	\n" +
					"for xuatkhau  in  ([0],[1],[2],[3]) )as t   	\n" +
					"inner join NHAPHANPHOI npp on npp.PK_SEQ = t.NPP_FK \n" +
					"inner join TINHTHANH tt on npp.TINHTHANH_FK = tt.PK_SEQ \n" +
					"inner join (select kv.PK_SEQ, v.TEN from KHUVUC kv join VUNG v on kv.VUNG_FK = v.PK_SEQ) v on v.PK_SEQ = npp.KHUVUC_FK \n" +
					"group by t.npp_fk,  npp.ten , tt.TEN, v.TEN order by tt.ten asc";
			}
			System.out.println("Bao cao ds theo LKH :"+sql);
			this.rsBCDoanhSoTheoKH = db.get(sql);
		}
		
		System.out.println("tt" + tt);
		if(tt.equals("1"))
		{
			
			sql = "select * from NHAPHANPHOI where iskhachhang=0 and "+
						"PK_SEQ in ( select npp_fk from phamvihoatdong where nhanvien_fk ="+this.userId +") ";
			
			if(this.vungId != null && this.vungId.length() > 0)
				sql += " and KHUVUC_FK in (select PK_SEQ from KHUVUC where VUNG_FK = '"+this.vungId +"')";
			
			if(this.TinhthanhId != null && this.TinhthanhId.length() > 0)
				sql += " and TINHTHANH_FK = '"+this.TinhthanhId +"'";
			
			this.rsChiNhanh = db.get(sql);	
			if(chinhanhId.length() > 0 )
			{
				sql = "select * from DAIDIENKINHDOANH where pk_seq in (select ddkd_fk from daidienkinhdoanh_npp where npp_fk='"+this.chinhanhId+"')";
				System.out.println(sql);
				this.rsTDV= db.get(sql);
			}
			if(chinhanhId.length() > 0){
				sql = "select TEN from NHAPHANPHOI where PK_seq = " + chinhanhId;
				ResultSet rs = db.get(sql);
				System.out.println(sql);
				try
				{
				if(rs != null)
				{
					if(rs.next());
					{
						nppTen = rs.getString("TEN");
						rs.close();
					}
				}
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			if(tdvId.length() > 0){		
				sql = "select TEN from DAIDIENKINHDOANH where PK_seq = " + tdvId;
				ResultSet rs = db.get(sql);
				System.out.println(sql);
				try
				{
				if(rs != null)
				{
					if(rs.next());
					{
						tdvTen = rs.getString("TEN");
						rs.close();
					}
				}
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		else{
		
				
			if(nppID.length() > 0){
			sql = "select * from DAIDIENKINHDOANH where pk_seq in (select ddkd_fk from daidienkinhdoanh_npp where npp_fk='"+this.nppID+"')" ;
			this.rsTDV= db.get(sql);
			}
			
			if(nppID.length() > 0){
				sql = "select TEN from NHAPHANPHOI where PK_seq = " + nppID;
				ResultSet rs = db.get(sql);
				System.out.println(sql);
				try
				{
				if(rs != null)
				{
					if(rs.next());
					{
						nppTen = rs.getString("TEN");
						rs.close();
					}
				}
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			if(tdvId.length() > 0){		
				sql = "select TEN from DAIDIENKINHDOANH where PK_seq = " + tdvId;
				ResultSet rs = db.get(sql);
				System.out.println(sql);
				try
				{
				if(rs != null)
				{
					if(rs.next());
					{
						tdvTen = rs.getString("TEN");
						rs.close();
					}
				}
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		
	}
	
	

	@Override
	public void DBclose() {
		// TODO Auto-generated method stub
		
		try {
			if(rsBCDoanhSoTheoKH != null)
				rsBCDoanhSoTheoKH.close();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}

	@Override
	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String getUserId() {
		return userId;
	}



	public ResultSet getRsBCDoanhSoTheoKH() {
		return rsBCDoanhSoTheoKH;
	}


	public void setRsBCDoanhSoTheoKH(ResultSet rsBCDoanhSoTheoKH) {
		this.rsBCDoanhSoTheoKH = rsBCDoanhSoTheoKH;
	}


	public String getNppID() {
		return nppID;
	}


	public void setNppID(String nppID) {
		this.nppID = nppID;
	}


	public ResultSet getRsTDV() {
		return rsTDV;
	}


	public void setRsTDV(ResultSet rsTDV) {
		this.rsTDV = rsTDV;
	}


	public String getTdvId() {
		return tdvId;
	}

	public void setTdvId(String tdvId) {
		this.tdvId = tdvId;
	}



	public String getNppTen() {
		return nppTen;
	}



	public void setNppTen(String nppTen) {
		this.nppTen = nppTen;
	}



	public String getTdvTen() {
		return tdvTen;
	}



	public void setTdvTen(String tdvTen) {
		this.tdvTen = tdvTen;
	}


	public String getLoai() {
		return loai;
	}

	public void setLoai(String loai) {
		this.loai = loai;
	}

	public String getTungay() {
		return tungay;
	}

	public void setTungay(String tungay) {
		this.tungay = tungay;
	}

	public String getDenngay() {
		return denngay;
	}

	public void setDenngay(String denngay) {
		this.denngay = denngay;
	}


	public String getTt() {
		return tt;
	}

	public void setTt(String tt) {
		this.tt = tt;
	}

	public ResultSet getRsChiNhanh() {
		return rsChiNhanh;
	}

	public void setRsChiNhanh(ResultSet rsChiNhanh) {
		this.rsChiNhanh = rsChiNhanh;
	}

	public String getChinhanhId() {
		return chinhanhId;
	}

	public void setChinhanhId(String chinhanhId) {
		this.chinhanhId = chinhanhId;
	}

	@Override
	public ResultSet getVung() {
		return this.vungRs;
	}

	@Override
	public String getvungId() {
		return this.vungId;
	}

	@Override
	public ResultSet getKhuvuc() {
		return this.khuvucRs;
	}

	@Override
	public String getkhuvucId() {
		return this.khuvucId;
	}

	@Override
	public void setVungId(String vungId) {
		this.vungId = vungId;
	}

	@Override
	public void setKhuvucId(String khuvucId) {
		this.khuvucId = khuvucId;
	}

	@Override
	public ResultSet rsBCDoanhSoTheoKHGroup() {
		return this.rsBCDoanhSoTheoKHGroup;
	}

	@Override
	public ResultSet getTinhthanh() {
		return tinhthanhRs;
	}

	@Override
	public String getTinhthanhId() {
		return TinhthanhId;
	}

	@Override
	public void setTinhthanhId(String tinhthanhId) {
		this.TinhthanhId = tinhthanhId;
	}
	
	
	String cndt,kh;
	
	public String 	getMucCN_DT()
	{
	  return 	this.cndt;
	}
	
	public void setMucCN_DT(String cndt)
	{
		this.cndt=cndt;
	}
	
	public String getMuc_KhachHang()
	{
		return 	this.kh;
	}
	
	public void setMuc_KhachHang(String cndt)
	{
		this.kh=cndt;
	}
	

}
