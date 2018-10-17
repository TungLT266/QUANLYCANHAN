
package geso.traphaco.erp.beans.kiemdinhchatluong.giay.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
 



import geso.dms.center.util.Utility;
import geso.traphaco.center.beans.thongtinsanpham.ITieuchikiemdinh;
import geso.traphaco.center.beans.thongtinsanpham.imp.Tieuchikiemdinh;
import geso.traphaco.center.util.Utility_Kho;
 
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.beans.kiemdinhchatluong.giay.IErpKiemdinhchatluong;
import geso.traphaco.erp.beans.nhanhang.ISpDetail;
import geso.traphaco.erp.beans.nhanhang.imp.SpDetail;
import geso.traphaco.erp.util.Kho_Lib;
import geso.traphaco.erp.util.Library;
 

public class ErpKiemdinhchatluong implements IErpKiemdinhchatluong
{
	String userId;
	String ycId;
	String spId;
	String spTen;	
	String nkId;
	String solo;
	String ngaysanxuat;
	String ngaynhanhang;
	String thongtinbom;
	String SoLuongKiemDinh,soluongDat,datCl,nguoiduyet,soluongkhongdat;
	String congdoanId,lenhsanxuatId,congtyId,dinhluong,dinhtinh,trangthai,ngaykiem;
	String[] ketqua_dinhtinh,ghinhan_dinhtinh,tieuchi_dinhtinh,nguoisua_dinhtinh;
	String msg;
	boolean kiemdinhcongdoan;
	ResultSet rsYeuCauKiemDinh,rslenhsanxuat,rscongdoan;
	List<ITieuchikiemdinh> tckdList;
	String denghixuly = "";
	String KhoNhanId;
	ResultSet RsKhoNhan;
	String KhuvucId;
	ResultSet RsKhuvuc;
	String Soluongmau;
	
	ResultSet rsdvdl;
	String dvdlid;
	String IsCapdong;
	
	String kyhieukd;
	
	String Maphieu;
	
	String KhonhanhangdatId;
	ResultSet RsKhonhanhangdat;
	
	
	
	NumberFormat formatter = new DecimalFormat("#######.#####");
	boolean IsKhoNhanQl_khuvuc;
	
	dbutils db;
	Utility_Kho util_kho=new Utility_Kho();
	
	public ErpKiemdinhchatluong()
	{
		this.userId = "";
		this.ycId = "";
		this.spId = "";
		this.spTen = "";
		this.nkId = "";
		this.solo = "";
		this.thongtinbom="";
		this.SoLuongKiemDinh = "";
		this.soluongDat="";
		this.ngaysanxuat="";
		this.datCl="";
		this.msg = "";
		this.congtyId="";
		this.congdoanId="";
		this.lenhsanxuatId="";
		this.trangthai="";
		this.ngaykiem="";
		this.dinhluong="";
		this.Soluongmau="0";
		this.soluongkhongdat="0";
		this.ngaynhanhang="";
		this.dinhtinh="";
		this.KhuvucId="";
		this.KhoNhanId="";
		this.IsCapdong="";
		this.dvdlid="";
		this.IsKhoNhanQl_khuvuc=false;
		this.kyhieukd = "";
		this.tckdList = new ArrayList<ITieuchikiemdinh>();
		this.db = new dbutils();
	}
	
	public ErpKiemdinhchatluong(String id)
	{
		this.ycId = id;
		this.userId = "";
		this.spId = "";
		this.spTen = "";
		this.nkId = "";
		this.solo = "";
		this.SoLuongKiemDinh = "";
		this.soluongDat="";
		this.datCl="";
		this.thongtinbom="";
		this.ngaysanxuat="";
		this.IsCapdong="";
		this.ngaynhanhang="";
		this.msg = "";
		this.congtyId="";
		this.congdoanId="";
		this.lenhsanxuatId="";
		this.trangthai="";
		this.ngaykiem="";
		this.dinhluong="";
		this.dinhtinh="";
		this.KhuvucId="";
		this.KhoNhanId="";
		this.IsKhoNhanQl_khuvuc=false;
		this.Soluongmau="0";
		this.soluongkhongdat="0";
		this.dvdlid="";
		this.kyhieukd = "";
		this.tckdList = new ArrayList<ITieuchikiemdinh>();
		this.db = new dbutils();
	}
	
	public ErpKiemdinhchatluong(String lenhsanxuatId,String congdoanId)
	{
		this.ycId = "";
		this.lenhsanxuatId=lenhsanxuatId;
		this.congdoanId=congdoanId;
		this.userId = "";
		this.spId = "";
		this.spTen = "";
		this.nkId = "";
		this.solo = "";
		this.thongtinbom="";
		this.SoLuongKiemDinh = "";
		this.soluongDat="";
		this.datCl="";
		this.ngaysanxuat="";
		this.ngaynhanhang="";
		this.msg = "";
		this.congtyId="";
		this.trangthai="";
		this.ngaykiem="";
		this.dinhluong="";
		this.IsCapdong="";
		this.dinhtinh="";
		this.kiemdinhcongdoan=false;
		this.IsKhoNhanQl_khuvuc=false;
		this.KhuvucId="";
		this.KhoNhanId="";
		this.Soluongmau="0";
		this.soluongkhongdat="0";
		this.dvdlid="";
		this.kyhieukd = "";
		this.tckdList = new ArrayList<ITieuchikiemdinh>();
		this.db = new dbutils();
		this.createRs();
	}
	

	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}

	public String getYcId() 
	{
		return this.ycId;
	}

	public void setYcId(String ycId) 
	{
		this.ycId = ycId;
	}

	public String getSpId() 
	{
		return this.spId;
	}

	public void setSpId(String spId) 
	{
		this.spId = spId;
	}

	public String getSpTen() 
	{
		return this.spTen;
	}

	public void setSpTen(String spTen)
	{
		this.spTen = spTen;
	}

	public String getNhapkhoId() 
	{
		return this.nkId;
	}

	public void setNhapkhoId(String nkId)
	{
		this.nkId = nkId;
	}

	public String getSolo() 
	{
		return this.solo;
	}

	public void setSolo(String solo) 
	{
		this.solo = solo;
	}


	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}
	
	public void setTieuchikiemdinhList(List<ITieuchikiemdinh> list) 
	{
		this.tckdList = list;
	}

	public List<ITieuchikiemdinh> getTieuchikiemdinhList() 
	{
		return this.tckdList;
	}
	
	public boolean updateKiemdinh(HttpServletRequest request)
	{
		 
		if(!checkValid())
		{
			return false;
		}		
		try 
		{
			db.getConnection().setAutoCommit(false);
			 
			String query="Delete from Erp_YeuCauKiemDinh_TieuChi_Log where NguoiSua='"+this.userId+"' And NgaySua='"+getDateTime()+"' ";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật Erp_YeuCauKiemDinh_TieuChi_Log " + query;
				db.getConnection().commit();
				return false;
			}
			 query= " insert into Erp_YeuCauKiemDinh_TieuChi_Log(yeucaukiemdinh_fk, tieuchi, pheptoan, giatrichuan,GhiNhan,Dat,QuyetDinh,NguoiSua,NgaySua,DinhLuong,DinhTinh)" +
					" select t.yeucaukiemdinh_fk,t.tieuchi,t.pheptoan,t.giatrichuan,t.GhiNhan,t.Dat,t.Dat,'"+this.userId+"','"+getDateTime()+"',t.DinhLuong,t.DinhTinh from ERP_YeuCauKiemDinh k "+
					" inner join ERP_YeuCauKiemDinh_TieuChi t on t.yeucaukiemdinh_fk=k.pk_seq "+
					" where k.pk_seq='"+this.ycId+"'";
			 
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_YeuCauKiemDinh_TieuChi " + query;
				db.getConnection().commit();
				return false;
			}
			
			 query=" DELETE FROM ERP_YeuCauKiemDinh_TieuChi WHERE YEUCAUKIEMDINH_FK='"+this.ycId+"'";
			 if(!db.update(query))
				{
					this.msg = "Không thể cập nhật ERP_YeuCauKiemDinh_TieuChi " + query;
					db.getConnection().commit();
					return false;
				}
		 	 
			for(int i = 0; i < this.tckdList.size(); i++)
			{
				ITieuchikiemdinh tckd = this.tckdList.get(i);
				
				 query = " insert ERP_YeuCauKiemDinh_TieuChi(yeucaukiemdinh_fk, tieuchi, pheptoan, giatrichuan, diemdat, dat, trangthai,DinhLuong) " +
						 " values('" + this.ycId + "', N'" + tckd.getTieuchi() + "', '" + tckd.getToantu() + "', '" + tckd.getGiatrichuan() + "', " + (tckd.getDiemdat().length() > 0 ? tckd.getDiemdat():"NULL") + ", '" + tckd.getQuyetdinh() + "', N'" + tckd.getTrangthai() + "',1)";
				
				if(!db.update(query))
				{
					this.msg = " Không thể cập nhật ERP_YeuCauKiemDinh_TieuChi " + query;
					db.getConnection().commit();
					return false;
				}
				 
				
				query=" update Erp_YeuCauKiemDinh_TieuChi_Log set QuyetDinh='"+tckd.getQuyetdinh()+"' where yeucaukiemdinh_fk='"+this.ycId+"' and  tieuchi=N'"+tckd.getTieuchi()+"' and NgaySua='"+getDateTime()+"'  and NguoiSua='"+this.userId+"' And DinhLuong=1";
				if(!db.update(query))
				{
					this.msg = " Không thể cập nhật ERP_YeuCauKiemDinh_TieuChi " + query;
					db.getConnection().commit();
					return false;
				}
			}
			
			if(this.tieuchi_dinhtinh!=null&&this.tieuchi_dinhtinh.length>0)
			{
				for(int i=0;i<this.tieuchi_dinhtinh.length;i++)
				{	
					 query = " insert ERP_YeuCauKiemDinh_TieuChi(yeucaukiemdinh_fk, tieuchi, GhiNhan,Dat, trangthai,DinhTinh) " +
							 " select '" + this.ycId + "', N'" + this.tieuchi_dinhtinh[i] + "',N'"+this.ghinhan_dinhtinh[i]+"','"+this.ketqua_dinhtinh[i]+"',Case when '"+this.ketqua_dinhtinh[i]+"'='1' then N'Ðạt'else N'Không đạt' END,1"; 
			
					if(!db.update(query))
					{
						this.msg = "Không thể cập nhật ERP_YeuCauKiemDinh_TieuChi " + query;
						db.getConnection().commit();
						return false;
					}
					 
					query="update Erp_YeuCauKiemDinh_TieuChi_Log set QuyetDinh='"+this.ketqua_dinhtinh[i]+"' where yeucaukiemdinh_fk='"+this.ycId+"' and  tieuchi=N'"+this.tieuchi_dinhtinh[i]+"' and NgaySua='"+getDateTime()+"' and NguoiSua='"+this.userId+"' and DinhTinh=1";
					if(!db.update(query))
					{
						this.msg = "Không thể cập nhật ERP_YeuCauKiemDinh_TieuChi " + query;
						db.getConnection().commit();
						return false;
					}
				 
				}
			}
			// quy doi
			
			
			double soluongkiemdinhchuan=0;
			double soluongdatchuan=0;
			double soluongmauchuan=0;
			
			query=" SELECT SP.DVDL_FK,QC.SOLUONG1,QC.SOLUONG2 FROM ERP_SANPHAM SP "+
				 " LEFT JOIN QUYCACH QC ON QC.SANPHAM_FK=SP.PK_SEQ AND QC.DVDL1_FK=SP.DVDL_FK AND QC.DVDL2_FK="+this.dvdlid+"  WHERE SP.PK_SEQ="+this.spId;
			ResultSet rscheck=db.get(query);
			if(rscheck.next()){
				if(rscheck.getString("DVDL_FK").equals(this.dvdlid)){
					soluongkiemdinhchuan= Double.parseDouble(this.SoLuongKiemDinh.replaceAll(",", ""));
					soluongdatchuan= Double.parseDouble(this.soluongDat.replaceAll(",", ""));
					soluongmauchuan= Double.parseDouble(this.Soluongmau.replaceAll(",", ""));
					
				}else{
					soluongkiemdinhchuan= Double.parseDouble(this.SoLuongKiemDinh.replaceAll(",", ""))*rscheck.getDouble("SOLUONG1")/rscheck.getDouble("SOLUONG2") ;
					soluongdatchuan= Double.parseDouble(this.soluongDat.replaceAll(",", ""))*rscheck.getDouble("SOLUONG1")/rscheck.getDouble("SOLUONG2") ;
					soluongmauchuan= Double.parseDouble(this.Soluongmau.replaceAll(",", ""))*rscheck.getDouble("SOLUONG1")/rscheck.getDouble("SOLUONG2") ;
				}
			}else{
				this.msg = "2.Không thể xác định đơn vị  quy đổi của thành phẩm kiểm định" ;
				db.getConnection().rollback();
				return false;
			}
			
			query="select trangthai from ERP_YeuCauKiemDinh where pk_seq="+this.ycId;
			ResultSet rstt=db.get(query);
			String trangthai_="";
			if(rstt.next()){
				trangthai_=rstt.getString("trangthai");
			}
			
			if(trangthai_.equals("0")){
				query = "update ERP_YeuCauKiemDinh set KHONHANHANGDAT_FK= "+this.KhonhanhangdatId+" ,MAPHIEU='"+this.Maphieu+"', ISCAPDONG ='"+this.IsCapdong+"' , SOLUONGMAU= "+ soluongmauchuan+",SOLUONGDAT="+soluongdatchuan+", SOLUONGMAU_DVNHAP="+this.Soluongmau+",SOLUONGDAT_DVNHAP="+this.soluongDat+",nguoisua = '" + 
				this.userId + "', ngaysua = '" + getDateTime() + "',NgayKiem='"+this.ngaykiem+"', denghixuly = N'" + 
				this.denghixuly + "' ,KHUVUCKHO_FK = "+this.KhuvucId+",KHONHAN_FK="+this.KhoNhanId+" ,ngaynhan='"+this.ngaynhanhang+"', kyhieumau =N'"+this.kyhieukd+"'"+ //ngaysanxuat='"+this.ngaysanxuat+"',
				"  where pk_seq = '" + this.ycId + "'";
			}else{
				query = " update ERP_YeuCauKiemDinh set     nguoisua = '" + 
				this.userId + "', ngaysua = '" + getDateTime() + "', denghixuly = N'" + 
				this.denghixuly + "', kyhieumau = N'"+this.kyhieukd+"' "+  
				"  where pk_seq = '" + this.ycId + "'";
			}
			
			System.out.println(query);
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_YeuCauKiemDinh " + query;
				db.getConnection().commit();
				return false;
			}
			 
			db.getConnection().commit();
			db.getConnection().setAutoCommit(false);
		} 
		catch (Exception e) 
		{
		 
			e.printStackTrace();
			db.update("rollback");
			return false;
		}
		return true;
	}

	public void init() 
	{
		String query = 
			" select isnull(KHONHANHANGDAT_FK,0) as KHONHANHANGDAT_FK ,ISNULL(a.MAPHIEU,'') AS MAPHIEU , isnull(a.iscapdong,'0') as iscapdong ,a.DVDL_NHAP_FK, a.pk_seq, a.nhapkho_fk  as sonhapkho,a.ngaysanxuat,isnull(a.ngaynhan,'') as ngaynhan , c.pk_seq as spId, c.MA  + ' - ' + c.Ten +   ' (' + isnull(dvdl.diengiai, '') + ')' as spTen, a.SOLUONG_DVNHAP as SoLuongKiemDinh,isnull(a.SOLUONGDAT_DVNHAP,0) as soluongdat , a.solo, isnull(SOLUONGMAU_DVNHAP,0)  as soluongmau , "+
			" a.trangthai,a.LenhSanXuat_FK,a.CongDoan_FK,isnull(a.DatChatLuong,0) as DatChatLuong,Isnull(a.DinhLuong,0)as DinhLuong,isnull(a.DinhTinh,0) as DinhTinh,isnull(a.NgayKiem,'') as NgayKiem,isnull(d.Ten,'') as NguoiDuyet, isnull(a.denghixuly, '') as denghixuly, isnull(KYHIEUMAU,'') KYHIEUMAU "+  
			" from ERP_YeuCauKiemDinh a  " +
			" inner join ERP_SANPHAM c on a.sanpham_fk = c.PK_SEQ " +
			" left join nhanvien d on d.pk_seq=a.NguoiDuyet "+ 
			" left join donvidoluong dvdl on dvdl.pk_seq = c.dvdl_fk " +
			" where a.pk_seq = '"+this.ycId+"'";
		System.out.println("::::::::::>Init::"+query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					this.nkId = rs.getString("sonhapkho");
					this.spTen = rs.getString("spTen")==null?"":rs.getString("spTen");
					this.spId = rs.getString("spId")==null?"":rs.getString("spId");
					this.solo = rs.getString("solo");
					this.ngaysanxuat = rs.getString("ngaysanxuat");
					this.ngaynhanhang = rs.getString("ngaynhan");
					this.soluongDat = formatter.format(rs.getDouble("soluongDat"));
					this.SoLuongKiemDinh=formatter.format(rs.getDouble("soluongKiemDinh"));
					this.datCl=rs.getString("DatChatLuong");
					this.trangthai=rs.getString("TrangThai");
					this.ngaykiem=rs.getString("NgayKiem");
					this.congdoanId=rs.getString("CongDoan_FK");
					this.lenhsanxuatId=rs.getString("LenhSanXuat_FK");
					this.nguoiduyet=rs.getString("NguoiDuyet");
					this.dinhluong=rs.getString("DinhLuong");
					this.dinhtinh=rs.getString("DinhTinh");
					this.denghixuly = rs.getString("denghixuly");
					this.kyhieukd = rs.getString("KYHIEUMAU");
					this.Maphieu= rs.getString("Maphieu");
					if(this.Maphieu.equals("")){
						this.Maphieu=Library.createMaPhieuTuDong(spId,this.ycId,db);
					}
					this.dvdlid=(rs.getString("DVDL_NHAP_FK")==null?"":rs.getString("DVDL_NHAP_FK"));
					this.Soluongmau=formatter.format(rs.getDouble("soluongmau"));
					this.soluongkhongdat=formatter.format(rs.getDouble("soluongKiemDinh")- rs.getDouble("soluongmau")- rs.getDouble("soluongdat"));
					this.IsCapdong=rs.getString("iscapdong");
					this.KhonhanhangdatId=rs.getString("KHONHANHANGDAT_FK");
				}
				rs.close();
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		if(this.lenhsanxuatId.length() >0 )
		{
			query = "	SELECT DISTINCT ISNULL(VATTU.DIENGIAI,'')  + ' - ' + ISNULL(vanbanhuongdan,'') as thongtinBOM "+
					"	FROM ERP_LENHSANXUAT_GIAY A   "+
					"	INNER JOIN ERP_LENHSANXUAT_SANPHAM LSXSP ON LSXSP.LENHSANXUAT_FK=A.PK_SEQ"+
					"	inner join ERP_DANHMUCVATTU VATTU on LSXSP.DANHMUCVT_FK = VATTU.PK_SEQ"+
					"	WHERE A.PK_SEQ = '"+this.lenhsanxuatId+"'";
			System.out.println("GET INFO BOM"+query);
			rs = db.get(query);
			if(rs != null)
			{
				try 
				{
					if(rs.next())
					{
						this.thongtinbom=rs.getString("thongtinBOM");
					}
					rs.close();
				}
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		}
		createRs();
		if(this.dinhluong.equals("1"))
		{
			query = " select count(tieuchi) as sodong from ERP_YeuCauKiemDinh_TieuChi  " +
					" where yeucaukiemdinh_fk = '" + this.ycId + "' and DinhLuong=1";
			//System.out.println("1.Check: " + query);
			
			ResultSet rsCheck = db.get(query);
			boolean flag = false;
			 
				try 
				{
					if (rsCheck.next())
					{
						if (rsCheck.getString("sodong").equals("0"))
							flag = true;
						rsCheck.close();
					}
				} 
				catch (Exception e) {e.printStackTrace();}
 
			query= 	  " SELECT C.TIEUCHI,C.PHEPTOAN,C.GIATRICHUAN,'' AS DIEMDAT,'0' AS DAT,'0' AS QUYETDINH,'' AS TRANGTHAI,''  AS NGUOISUA "+  
					  " FROM ERP_CONGDOANSANXUAT_TIEUCHIKIEMDINH_GIAY C   "+
					  " WHERE C.CONGDOANSANXUAT_FK="+this.congdoanId+" AND ISNULL(C.DINHLUONG,'0') ='1' "; 
			rsCheck = db.get(query);
			try{
			if(rsCheck.next()){
				if (rsCheck.getString("sodong").equals("0"))
					flag = true;
				rsCheck.close();
			}
			rsCheck.close();
			}catch(Exception err){}
			
			if(flag)
			{
				query = " SELECT c.TieuChi,c.PhepToan,c.GiaTriChuan,'' as DiemDat,'0' as Dat,'0' as QuyetDinh,'' as TrangThai,''  as NguoiSua  " +
					  " from SANPHAM_TIEUCHIKIEMDINH c " +
					  " WHERE c.sanpham_fk="+this.spId+" and c.Loai=0  " +
					  " UNION  " +
					  " SELECT C.TIEUCHI,C.PHEPTOAN,C.GIATRICHUAN,'' AS DIEMDAT,'0' AS DAT,'0' AS QUYETDINH,'' AS TRANGTHAI,''  AS NGUOISUA "+  
					  " FROM ERP_CONGDOANSANXUAT_TIEUCHIKIEMDINH_GIAY C   "+
					  " WHERE C.CONGDOANSANXUAT_FK="+this.congdoanId+" AND ISNULL(C.DINHLUONG,0) =1 ";
			}
			else
			{
				query =" select a.TieuChi,a.PhepToan,a.GiaTriChuan, isnull( cast(a.DiemDat as nvarchar(10)),'') as diemdat , a.Dat,isnull(b.QuyetDinh,0)as QuyetDinh,isnull(a.TrangThai,'')as TrangThai,c.TEN + '--' +b.NgaySua  as NguoiSua from erp_yeucaukiemdinh_tieuchi a "+
					 " left join "+ 
					 " ( "+
					 "	select * "+
					 "	from Erp_YeuCauKiemDinh_TieuChi_Log "+
					 "	where NgayGio = (select max(NgayGio) from Erp_YeuCauKiemDinh_TieuChi_Log where YeuCauKiemDinh_FK ='"+this.ycId+"' and DinhLuong=1) and YeuCauKiemDinh_FK='"+this.ycId+"' and DinhLuong=1 "+
					 " ) "+
					 "  b on b.YeuCauKiemDinh_FK=a.yeucaukiemdinh_fk and a.tieuchi=b.TieuChi " +
					 "  left join NHANVIEN c on c.PK_SEQ=b.NguoiSua where a.YeuCauKiemDinh_FK='"+this.ycId+"' and a.DinhLuong=1";
			}
		    System.out.println("2.Khoi tao tieu chi dinh luong: " + query);
			
			List<ITieuchikiemdinh> tckdList = new ArrayList<ITieuchikiemdinh>();
		    rs = db.get(query);
 
				try
				{
					while (rs.next())
					{
						ITieuchikiemdinh tckd = new Tieuchikiemdinh();
						tckd.setTieuchi(rs.getString("tieuchi"));
						tckd.setToantu(rs.getString("pheptoan"));
						tckd.setGiatrichuan(rs.getString("giatrichuan"));
						 
							tckd.setDiemdat(rs.getString("diemdat"));
						tckd.setDat(rs.getString("dat"));
						tckd.setTrangthai(rs.getString("trangthai"));
						tckd.setQuyetdinh(rs.getString("QuyetDinh"));
						tckd.setNguoiSua(rs.getString("nguoisua"));
						tckdList.add(tckd);
					}rs.close();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				
				this.tckdList = tckdList;
	 
			if(this.tckdList.size() <= 0)
			{
				this.msg = "Sản phẩm ( " + spTen + " ) chua thiết lập tiêu chí kiểm định. Vui lòng kiểm tra lại dữ liệu nền." ;
			}
		}
		
		
		/************************************************************************
		 * 	Dinh tinh
		 * Kiem tra dinh tinh cua cong doan
		 * Kiem tra dinh tinh cua san pham
		 * 
		 **************************************************************************/
		if(this.dinhtinh.equals("1") )
		{
			query = " select count(c.TieuChi) as sodong "+
					" from "+ 
					" ERP_YeuCauKiemDinh_TieuChi c  "+ 
					" where c.YeuCauKiemDinh_FK='"+this.ycId+"'  and c.DinhTinh=1 ";
			//System.out.println("1.Check dinh tinh: " + query);
			 ResultSet rsCheck = db.get(query);
			 boolean flag = false;
			 int numb=0;
			 
				try 
				{
					if (rsCheck.next())
					{
						numb=rsCheck.getInt(1);
						if (numb==0){
							flag = true;
						}
						rsCheck.close();
					}
				} 
				catch (Exception e) { e.printStackTrace();}
			 
				if( flag){
						query = " select count(*) as sodong " +
				  				" from  SanPham_TieuChiKiemDinh cd where cd.sanpham_fk = '" + this.spId + "' and loai = '1' "; 
					
						rsCheck = db.get(query);
						if (rsCheck != null)
						{
							try 
							{
								if (rsCheck.next())
								{
									numb=rsCheck.getInt(1);
									rsCheck.close();
								}
							} 
							catch (Exception e) {e.printStackTrace();}
						}
				 
					
						query = " select count(*) as sodong " +
			  					" from  Erp_CongDoanSanXuat_TieuChiKiemDinh_Giay cd where " +
			  					" cd.CongDoanSanXuat_FK = '" +this.congdoanId + "' AND  ISNULL(DINHTINH,'0') ='1'  "; 
						rsCheck = db.get(query);
					 
							try 
							{
								if (rsCheck.next())
								{
									numb=numb+ rsCheck.getInt(1);
									rsCheck.close();
								}
							} 
							catch (Exception e) { e.printStackTrace();}
				} 
			 
				System.out.println("So dong: " + numb+"[query]"+query);
			this.tieuchi_dinhtinh=new String[numb];
			this.ketqua_dinhtinh=new String[numb];
			this.nguoisua_dinhtinh=new String[numb];
			this.ghinhan_dinhtinh=new String[numb];
			
			String sql;
			if(flag )
			{
				sql =" SELECT c.TieuChi, '' as ghinhan  ,'0' as QuyetDinh,'' as TrangThai,''  as NguoiSua  " +
					 " from SANPHAM_TIEUCHIKIEMDINH c " +
					 " WHERE c.sanpham_fk="+this.spId+" and c.Loai=1 "+
					 " Union all  SELECT c.TieuChi,'' as GhiNhan,0 as QuyetDinh,'' as TrangThai,''  as NguoiSua  " +
					 " FROM Erp_CongDoanSanXuat_TieuChiKiemDinh_Giay c  " +
					 " WHERE c.CongDoanSanXuat_FK= "+this.congdoanId+" and  ISNULL(C.DINHTINH,'0') ='1' ";
			}
			else
			{
				sql =" select a.TieuChi,isnull(a.GhiNhan,'') as GhiNhan,isnull(b.QuyetDinh,0)as QuyetDinh " +
					 " ,isnull(a.TrangThai,'')as TrangThai,c.TEN + '--' +b.NgaySua  as NguoiSua from erp_yeucaukiemdinh_tieuchi a "+
					 " left join "+ 
					 " ( "+
					 "	select * "+
					 "	from Erp_YeuCauKiemDinh_TieuChi_Log "+
					 "	where NgayGio = (select max(NgayGio) from Erp_YeuCauKiemDinh_TieuChi_Log where YeuCauKiemDinh_FK ='"+this.ycId+"') and YeuCauKiemDinh_FK='"+this.ycId+"' "+
					 "	) "+
					 " b on b.YeuCauKiemDinh_FK=a.yeucaukiemdinh_fk and a.tieuchi=b.TieuChi and b.DinhTinh=a.DinhTinh  " +
					 " left join NHANVIEN c on c.PK_SEQ=b.NguoiSua where a.YeuCauKiemDinh_FK='"+this.ycId+"' and a.DinhTinh=1 ";
			}
			System.out.println("2.::Tieu chi dinh tinh:::: " + sql);
			
			 rs = db.get(sql);
		    int i=0;   	    
			if ( numb>0)
			{
				try
				{
					while (rs.next())
					{
						this.tieuchi_dinhtinh[i]=rs.getString("TieuChi");
						this.ketqua_dinhtinh[i]=(rs.getString("QuyetDinh")==null?"0":"1");
						this.ghinhan_dinhtinh[i]=rs.getString("GhiNhan");
						this.nguoisua_dinhtinh[i]=rs.getString("NguoiSua");
						i++;
					}rs.close();
				}
				catch (Exception e)
				{
					//System.out.println("116.Loi.....: " + e.toString());
					e.printStackTrace();
				}
			}
			
			
		}
		 
	}

	
	public void DbClose()
	{
			try
			{
				if(rsYeuCauKiemDinh!=null)
				this.rsYeuCauKiemDinh.close();
				if(this.rscongdoan!=null){
					this.rscongdoan.close();
					
				}
				if(rsdvdl!=null){
					rsdvdl.close();
				}
				if(rslenhsanxuat!=null){
					rslenhsanxuat.close();
				}
				if(RsKhoNhan!=null){
					RsKhoNhan.close();
				}
				
				if(db!=null){
					db.shutDown();
				}
			} catch (SQLException e)
			{
				
				e.printStackTrace();
			}
		
		
	}
	
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}


	public String getCongdoanId()
	{

		return this.congdoanId;
	}


	public void setCongdoanId(String congdoanId)
	{
		this.congdoanId=congdoanId;
		
	}


	public String getLenhsanxuatId()
	{

		return this.lenhsanxuatId;
	}


	public void setLenhsanxuat(String lenhsanxuatId)
	{
		this.lenhsanxuatId=lenhsanxuatId;
		
	}


	public String getCongtyId()
	{

		return this.congtyId;
	}


	public void setCongtyId(String congtyId)
	{
		this.congtyId=congtyId;
		
	}
 
	private String createChuyenkho(String ngaychotnv,String khochuyen, String khonhan,
			List<ISpDetail> spdetail,List<ISpDetail> spdetail_xuat, String lydo,String trangthaisp,boolean ishangmau ) {
	 
		 try{
			 //100064	XC06	Chuyển kho nội bộ
			 
			 String  iskhonhan_qlkhuvuc=util_kho.getIsQuanLyKhuVuc(khonhan, this.db);
			 
			 
			 
			String query =  " insert ERP_CHUYENKHO( noidungxuat_fk, ngaychuyen, ngaynhan, ngaychot, lydo, trangthai, khoxuat_fk, khonhan_fk, trangthaisp, ngaytao, nguoitao, ngaysua, nguoisua, NCC_CHUYEN_FK, NCC_NHAN_FK,YCKD_FK ,ISHANGDIDUONG) " +
							" values(100064, '" + ngaychotnv + "', '" + ngaychotnv + "', '" + ngaychotnv + "', N'"+lydo+"',1, '" + khochuyen + "', " + khonhan + ", '"+trangthaisp+"', " +
							" '" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "', NULL, NULL,  "+this.ycId+",'1')";
			 
			if(!db.update(query))
			{
				return  "Không thể tạo phiếu chuyển kho từ kho tạm sang kho tổng: " + query;
			}
		
		 
			String ckId = "";
			query = "select IDENT_CURRENT('ERP_CHUYENKHO') as ckId";
			ResultSet rsCk = db.get(query);
			if (rsCk.next())
			{
				ckId = rsCk.getString("ckId");
			} else {
				return "Không thể tạo phiếu chuyển kho từ kho tạm sang kho tổng: " + query;
			 
			}
			rsCk.close();
			 
			for(int i = 0; i < spdetail_xuat.size(); i++) { 
				
				ISpDetail sp = spdetail_xuat.get(i);
				 
				query = " insert ERP_CHUYENKHO_SANPHAM(chuyenkho_fk, SANPHAM_FK, SOLUONGYEUCAU,SOLUONGXUAT,SOLUONGNHAN) " +
				"values( '" + ckId + "', '" + sp.getMa() + "', " + sp.getSoluong()+ "," + sp.getSoluong() + ","+sp.getSoluong()+" ) ";
		
				if(!db.update(query))
				{
					return  "Khong the tao moi ERP_CHUYENKHO_SANPHAM: " + query;
				}
				query=	" insert ERP_CHUYENKHO_SANPHAM_CHITIET( chuyenkho_fk, SANPHAM_FK,  solo, ngayhethan,  " +
						" ngaynhapkho, MAME, MATHUNG, MAPHIEU, MARQ, HAMLUONG, HAMAM, soluong,BIN_FK,PHIEUEO,MAPHIEUDINHTINH ) " +
						" VALUES ("+ckId+", "+sp.getMa()+",'"+sp.getSolo()+"','"+sp.getNgayHethan()+"','"+sp.getNgaynhapkho()+"','"+sp.getMame()+"' " +
				  		",'"+sp.getSothung()+"','"+sp.getMaphieu()+"' ,'"+sp.getMarq()+"','"+sp.getHamluong()+"','"+sp.getHamAm()+"' " +
				  		" ,"+sp.getSoluong()+",'"+sp.getkhuid()+"','"+sp.getPhieuEO()+"','"+sp.getMaphieudinhtinh()+"')";
				
				if(db.updateReturnInt(query)< 1)
				{
					return "Không thể cập nhật : " + query;
				}
				 
			    query=	"  SELECT AVAILABLE,sp.pk_seq,sp.ma+ ' - ' + sp.ten    as tensp FROM  ERP_KHOTT_SANPHAM  kho inner join erp_sanpham sp on sp.pk_seq=sanpham_fk " +
			    		"  WHERE KHOTT_FK="+khochuyen+" AND SANPHAM_FK="+sp.getMa()+"" ;
			    	
			    ResultSet rscheckkho=db.get(query);
			    double soluongton_=0;
			    String tensp="";
			   
			    if(rscheckkho.next()){
			    	soluongton_=rscheckkho.getDouble("AVAILABLE");
			    	tensp=rscheckkho.getString("pk_seq")+ "-" +rscheckkho.getString("tensp");
			    }
			    rscheckkho.close();
			    
			    if(soluongton_< Double.parseDouble(sp.getSoluong()) ){
			    	return " Không thể tạo chuyển kho cho sản phẩm [ "+tensp+" ]  số lượng chuyển : "+sp.getSoluong()+" ,do hàng ở kho sản xuất : "+soluongton_+" không còn đủ để chuyển, " +
			    		   " vui lòng kiểm tra báo cáo xuất nhập tồn chi tiết để biết rõ hàng đã chuyển từ chức năng nào để theo dõi ";
			    }
			    float soluongct=(-1)*Float.parseFloat(sp.getSoluong());
			    float booked=0;
			    float available=(-1)*Float.parseFloat(sp.getSoluong());
			     
			    
				Kho_Lib kholib=new Kho_Lib();
				kholib.setLoaichungtu("Erpkiemdinhchatluong.java 870 id chuyenkho : "+ckId);
				
				kholib.setNgaychungtu(ngaychotnv);
				kholib.setKhottId(khochuyen);
				kholib.setBinId("0");
				kholib.setSolo(sp.getSolo());
				kholib.setSanphamId(sp.getMa());
				kholib.setMame(sp.getMame());
				kholib.setMathung(sp.getSothung());
				kholib.setMaphieu(sp.getMaphieu());
				kholib.setMaphieudinhtinh(sp.getMaphieudinhtinh());
				kholib.setPhieuEo(sp.getPhieuEO());
				kholib.setNgayhethan(sp.getNgayHethan());
				kholib.setNgaysanxuat(sp.getNgaySx());
				kholib.setNgaynhapkho(sp.getNgaynhapkho());
				
				kholib.setBooked(booked);
				kholib.setSoluong(soluongct);
				kholib.setAvailable(available);
				kholib.setMARQ(sp.getMarq());
				kholib.setDoituongId("");
				kholib.setLoaidoituong("");
				kholib.setBinId(sp.getkhuid());
				kholib.setDongialo(0);
				kholib.setHamluong(sp.getHamluong());
				kholib.setHamam(sp.getHamAm());
				
				String msg1= util_kho.Update_Kho_Sp_Tra(db,kholib);
			    if( msg1.length() >0)
				{
					this.msg = msg1;
					db.getConnection().rollback();
					return this.msg;
				}
		 
			 
			}

			//GHI NHAN NHAN HANG
			query = "  insert ERP_CHUYENKHO_SANPHAM_CHITIET_NHANHANG( chuyenkho_fk, SANPHAM_FK, solo, ngayhethan, LOAI, scheme, ngaynhapkho, MARQ, hamluong, hamam, " + 
					"  	MAME, MATHUNG, MAPHIEU, phieudt, phieueo, MAPHIEUDINHTINH, soluong		 )  " + 
					"  select chuyenkho_fk, SANPHAM_FK, solo, ngayhethan, LOAI, scheme, ngaynhapkho, MARQ, hamluong, hamam, " + 
					"  		MAME, MATHUNG, MAPHIEU, phieudt, phieueo, MAPHIEUDINHTINH, " + 
					"  		  DT.soluong   as soluong  " + 
					"  from " + 
					"  ( " + 
					"  	select chuyenkho_fk, SANPHAM_FK, solo, ngayhethan, LOAI, scheme, ngaynhapkho, MARQ, hamluong, hamam, " + 
					"  	MAME, MATHUNG, MAPHIEU, phieudt, phieueo, MAPHIEUDINHTINH, SUM(soluong) as soluong " + 
					"  	from ERP_CHUYENKHO_SANPHAM_CHITIET  " + 
					"  	where chuyenkho_fk = '" + ckId + "' and chuyenkho_fk in ( select pk_seq from ERP_CHUYENKHO where pk_seq = '" + ckId + "' and khonhan_fk is not null )  " + 
					"  	group by chuyenkho_fk, SANPHAM_FK, solo, ngayhethan, LOAI, scheme, ngaynhapkho, MARQ, hamluong, hamam, " + 
					"  	MAME, MATHUNG, MAPHIEU, phieudt, phieueo, MAPHIEUDINHTINH " + 
					"  ) " + 
					"  DT  " + 
					"  order by DT.SANPHAM_FK asc, DT.soluong desc ";
			if( !db.update(query) )
			{
				this.msg = "Không thể cập nhật nhận hàng: "+query;
				db.getConnection().rollback();
				return this.msg;
			}
			
			query=" update erp_chuyenkho set trangthai=1 where pk_Seq= "+ckId;
			if(db.updateReturnInt(query) != 1)
			{
				return "Không thể cập nhật : " + query;
			}
			
			db.execProceduce2("CapNhatTooltip_CK", new String[] { ckId } );
			 
		 }catch(Exception er){
			 er.printStackTrace();
			 return er.getMessage();
		 }
		 return "";
	}
 
	public boolean duyetKiemDinh()
	{
		String	query="";
		String lydo="";
		if( this.spId.length() >0)
		{
		 
			/*// kiem tra sp nay nam trong kho nao
			query=  " select * from erp_khott where  pk_seq= 100021 ";
			ResultSet rskhotp=db.get(query);
			int k=0;
			try{
				while (rskhotp.next()){
					k=k+1;
					this.KhoNhanId=rskhotp.getString("pk_seq");
				}
				rskhotp.close();
			}catch(Exception err){
			}
			if(k==0){
				this.msg="Loại sản phẩm này chưa được  định được kho thành phẩm nào,vui lòng vào dữ liệu nền kho để xác định loại sản phẩm này vào kho.";
				return false;
			}
			
			if(k>1){
				this.msg="Thành phẩm này đang được định vào 2 kho thành phẩm khô và lạnh,vui lòng chỉ định cho sản phẩm này vào 1 kho (Khô hoặc lạnh) ";
				return false;	
			}
			*/
			
			if( (Double.parseDouble(soluongDat)+Double.parseDouble(Soluongmau)) >Double.parseDouble(SoLuongKiemDinh))
			{
				this.msg="Vui lòng coi lại số lượng (đạt+mẫu) > số lượng kiểm định ";
				return false;
			}
			try
			{
				 Utility util=new Utility();
				 this.db.getConnection().setAutoCommit(false);
				 
				 
				 query=" update erp_lenhsanxuat_giay set trangthai=5 where trangthai<5  and  pk_seq =(select lenhsanxuat_fk from ERP_YeuCauKiemDinh where pk_seq="+this.ycId+")";
				 if(!db.update(query))
				 {
					this.msg = "Không thể cập nhật ERP_YeuCauKiemDinh " + query;
					db.update("rollback");
					return false;
				 }
				 
				 String maphieu=this.Maphieu;
				 
				 query = " update ERP_YeuCauKiemDinh set KHONHANHANGDAT_FK="+this.KhonhanhangdatId+", maphieu=N'"+maphieu+"', KHONHAN_FK=(select khonhap from erp_nhapkho where pk_seq='"+this.nkId+"'), " +
						 " NGAYHETHONG=GETDATE(), GIOCHOT='"+util.getTime()+"' ,trangthai = '1',DatChatLuong='"+this.datCl+"',  nguoisua = '" + this.userId + "',NguoiDuyet='"+this.userId+"', ngaysua = '" + getDateTime() + "',NgayKiem='"+this.ngaykiem+"' where pk_seq = '" + this.ycId + "'";
				if(!db.update(query))
				{
					this.msg = "Không thể cập nhật ERP_YeuCauKiemDinh " + query;
					db.update("rollback");
					return false;
				}
				query=  " SELECT  A.NGAYSANXUAT , A.NGAYHETHAN ,A.SOLO ,A.NGAYKIEM ,A.NGAYNHAPKHO ,NK.KHONHAP  " +
						" ,A.SANPHAM_FK,A.SOLUONGDAT,a.soluongmau ,A.SOLUONG , A.SOLUONG- A.SOLUONGDAT- a.soluongmau AS  SOLUONGKHONGDAT,A.SOLO  ,NKSP.KHUVUCKHO_FK "+
						" FROM ERP_YEUCAUKIEMDINH A INNER JOIN ERP_NHAPKHO NK ON A.NHAPKHO_FK=NK.PK_SEQ "+  
						" INNER JOIN ERP_NHAPKHO_SANPHAM NKSP ON NKSP.SONHAPKHO_FK=NK.PK_SEQ AND NKSP.SANPHAM_FK=A.SANPHAM_FK  "+
				        " WHERE  NK.TRANGTHAI=1   AND NK.PK_SEQ= "+this.nkId+" AND A.PK_SEQ="+this.ycId;
				
				System.out.println(query);
				ResultSet rsdetail=db.get(query);
				String solo="";
				if(rsdetail.next()){
					
					
					String spid=rsdetail.getString("sanpham_fk");
					
					String ngaychungtu= rsdetail.getString("NGAYKIEM");
					
					String khochuyen=rsdetail.getString("KHONHAP");
					// giảm kho không có mã phiếu,tăng kho có mã phiếu
					solo=rsdetail.getString("solo");
					String NGAYHETHAN=rsdetail.getString("NGAYHETHAN");
					String NGAYSANXUAT=rsdetail.getString("NGAYSANXUAT");
					String NGAYNHAPKHO=rsdetail.getString("NGAYNHAPKHO");
					Kho_Lib kholib=new Kho_Lib();
					kholib.setNgaychungtu(ngaychungtu);
					kholib.setLoaichungtu("Erpkiemdinhchatluong.java 1029 id duyetkiemdinh  : "+this.ycId);
					
					kholib.setKhottId(khochuyen);
					
					kholib.setBinId("0");
					
					kholib.setSolo(solo);
					kholib.setSanphamId(spid);
					
					kholib.setMame("");
					kholib.setMathung("");
					kholib.setMaphieu("");
					
					kholib.setMaphieudinhtinh("");
					kholib.setPhieuEo("");
					
					kholib.setNgayhethan(NGAYHETHAN);
					kholib.setNgaysanxuat(NGAYSANXUAT);
					
					kholib.setNgaynhapkho(NGAYNHAPKHO);
					
					
					kholib.setAvailable(0);
					kholib.setMARQ("");
					kholib.setDoituongId("");
					kholib.setLoaidoituong("");
					kholib.setBinId("0");
					kholib.setHamluong("100");
					kholib.setHamam("0");
					
					
					//giảm kho không có mã phiếu
					kholib.setBooked(-1*rsdetail.getFloat("SOLUONG"));
					kholib.setSoluong(-1*rsdetail.getFloat("SOLUONG"));
					kholib.setAvailable(0);
					
					
					String msg1= util_kho.Update_Kho_Sp_Tra(db,kholib);
				    if( msg1.length() >0)
					{
						this.msg = msg1;
						db.getConnection().rollback();
						return false;
						
					}
				    
				    // thêm mã phiếu vào : tăng kho lên lại,chỉ có ngày nhập kho là thay đổi
				    kholib.setMaphieu(maphieu);
				    // ngày nhập kho là ngày kiểm định
				    kholib.setNgaynhapkho(ngaychungtu);
				    
				    kholib.setBooked(0);
					kholib.setSoluong(rsdetail.getFloat("SOLUONG"));
					kholib.setAvailable(rsdetail.getFloat("SOLUONG"));
					
					msg1= util_kho.Update_Kho_Sp_Tra(db,kholib);
				    if( msg1.length() >0)
					{
						this.msg = msg1;
						db.getConnection().rollback();
						return false;
						
					}
				     
				    //lay ghi chu
				    lydo="Chuyển hàng đạt từ kho SX. Lệnh sản xuất: "+ this.lenhsanxuatId+" - SP "+this.spTen+" - Số lô "+ solo;
						
					  if(rsdetail.getDouble("SOLUONGDAT") >0){
						  
						  // tao chuyển kho tự động 
						   
						   List<ISpDetail> spdetail_nhan = new ArrayList<ISpDetail>();
						   
						   List<ISpDetail> spdetail_xuat = new ArrayList<ISpDetail>();
						   
						   ISpDetail spxuat =new SpDetail();
						   
						   spxuat.setSolo(rsdetail.getString("solo"));
						   spxuat.setSoluong(rsdetail.getString("SOLUONGDAT"));
						   spxuat.setMa(rsdetail.getString("SANPHAM_FK"));
						   spxuat.setNgaynhapkho(ngaychungtu);
						   spxuat.setMaphieu(maphieu);
						   
						   spxuat.setMame("");
						   spxuat.setSothung("");
						   spxuat.setDongia(0);
						   spxuat.setHamAm("0");
						   spxuat.setHamluong("100");
						   spxuat.setMaphieudinhtinh("");
						   spxuat.setkhuid("0");
						   spxuat.setMarq("");
						   spxuat.setPhieuEO("");
						   spxuat.setNgayHethan(NGAYHETHAN);
						   spxuat.setNgaySx(NGAYSANXUAT);
						   spdetail_xuat.add(spxuat);
						    
						   String khonhan=this.KhoNhanId;
						   String trangthaisp="0";//hàng đạt
						   
						   // hang dat thi chuyen qua kho thanh pham 
						   
						   
						   msg1=this.createChuyenkho(this.ngaykiem , khochuyen, this.KhonhanhangdatId, spdetail_nhan,spdetail_xuat, lydo,trangthaisp,true);
						   if(msg1.length() >0){
							   db.update("rollback");
							   this.msg=msg1;
							   return false;
						   }
					  } 
					 
				}
			
				rsdetail.close();
				
				
				this.db.getConnection().commit();
				this.db.getConnection().setAutoCommit(true);
				
			} catch (Exception  er)
			{
				er.printStackTrace();
				db.update("rollback");
				return false;
			}
		}
		else
		{
			
					this.msg = "Không xác định sản phẩm của yêu cầu kiểm định";
					return false;
				 
		}
		return true;
	}

	
	public String getTrangThai()
	{

		return this.trangthai;
	}

	
	public void setTrangThai(String trangthai)
	{
		this.trangthai=trangthai;
	}

	
	public String getDatCl()
	{
		return this.datCl;
	}

	
	public void setDatCl(String datcl)
	{

		this.datCl=datcl;
	}

	public void createRs()
	{
 
		String query=" select a.pk_seq ,a.solo,cast(a.pk_seq as varchar(20)) + '-' + a.solo  as KiemDinh,sp.ma +' '+sp.ten  as spTen,sp.pk_seq as spId from " +
					 " Erp_YeuCauKiemDinh a inner join erp_Sanpham sp on sp.pk_seq=a.sanpham_fk where lenhsanxuat_Fk='"+this.lenhsanxuatId+"' "+(this.congdoanId.length() >2? " and a.congdoan_fk="+this.congdoanId:"");
		//System.out.println("Du lieu :"+ query);
		this.rsYeuCauKiemDinh =this.db.getScrol(query);
		 	
		query="select * from erp_congdoansanxuat_giay where pk_seq='"+this.congdoanId+"'";
		this.rscongdoan=this.db.get(query);
		
		query="select * from erp_lenhsanxuat_giay where pk_seq='"+this.lenhsanxuatId+"'";
		this.rslenhsanxuat=this.db.get(query);
		
		// FIX CỨNG
		query= "SELECT PK_SEQ,TEN FROM ERP_KHOTT  WHERE PK_SEQ= 100011";
		this.RsKhoNhan=db.get(query);
		this.KhoNhanId="100011";
		
		query="SELECT PK_SEQ,TEN FROM ERP_KHUVUCKHO WHERE KHOTT_FK="+this.KhoNhanId;
		this.RsKhuvuc=db.get(query);
		
		if(util_kho.getIsQuanLyKhuVuc(this.KhoNhanId,db).equals("1")){
			this.IsKhoNhanQl_khuvuc=true;
		}
		
		query=  "  select pk_seq,diengiai from donvidoluong dv " +
				"  where dv.pk_seq in ( select DVDL_NHAP_FK from ERP_YeuCauKiemDinh where pk_seq="+this.ycId+" )";
		this.rsdvdl=db.get(query);
		
		query= " select PK_SEQ,TEN from ERP_KHOTT where   ISKHONHANTP_DAT='1'  ";
		this.RsKhonhanhangdat=db.get(query);
		
		
	}

	
	public String getSoLuongKiemDinh()
	{

		return this.SoLuongKiemDinh;
	}

	
	public void setSoLuongKiemDinh(String soluong)
	{
		this.SoLuongKiemDinh=soluong;
		
	}

	
	public String getsoluongDat()
	{
		
		return this.soluongDat;
	}

	
	public void setsoluongDat(String soluongDat)
	{
		this.soluongDat=soluongDat;
		
	}

	
	public String getNgayKiem()
	{
		
		return this.ngaykiem;
	}

	
	public void setNgayKiem(String ngaykiem)
	{
		this.ngaykiem=ngaykiem;
	}

	
	public void setRsYeuCauKiemDinh(ResultSet rsYc)
	{
		this.rsYeuCauKiemDinh=rsYc;
		
	}

	
	public ResultSet getRsYeuCauKiemDinh()
	{
		return this.rsYeuCauKiemDinh;
	}

	
	public String getDinhluong()
	{
	
		return this.dinhluong;
	}

	
	public void setDinhluong(String dinhluong)
	{
		this.dinhluong=dinhluong;
	}

	
	public String getDinhtinh()
	{
	
		return this.dinhtinh;
	}

	
	public void setDinhtinh(String dinhtinh)
	{
	
		this.dinhtinh=dinhtinh;
	}

	
	public String[] getTieuchi_dinhtinh()
	{
	
		return this.tieuchi_dinhtinh;
	}

	
	public void setTieuchi_dinhtinh(String[] tieuchi_dinhtinh)
	{
		this.tieuchi_dinhtinh=tieuchi_dinhtinh;
	}

	
	public String[] getGhinhan_dinhtinh()
	{
		return this.ghinhan_dinhtinh;
	}

	
	public void setGhinhan_dinhtinh(String[] ghinhan_dinhtinh)
	{
		this.ghinhan_dinhtinh=ghinhan_dinhtinh;
	}

	
	public String[] getKetqua_dinhtinh()
	{
		return this.ketqua_dinhtinh;
	}

	
	public void setKetqua_dinhtinh(String[] ketqua_dinhtinh)
	{
		this.ketqua_dinhtinh=ketqua_dinhtinh;
	}

	
	public String[] getNguoiSua_dinhtinh()
	{
		return this.nguoisua_dinhtinh;
	}

	
	public void setNguoiSua_dinhtinh(String[] nguoisua_dinhtinh)
	{
		this.nguoisua_dinhtinh=nguoisua_dinhtinh;
	}
	
	public boolean checkValid()
	{
		double sldat=0;
		double slKdat=0;
		
		if(this.KhonhanhangdatId.equals("")){
			this.msg="Vui lòng chọn kho nhận thành phẩm đạt,khi duyệt kiểm định sẽ tạo ra phiếu chuyển kho tự động vào kho này ";
			return false;
			
		}
		 
		sldat=Double.parseDouble(soluongDat);
		slKdat=Double.parseDouble(SoLuongKiemDinh)-Double.parseDouble(soluongDat);
		if(Double.parseDouble(soluongDat)>Double.parseDouble(SoLuongKiemDinh))
		{
			this.msg="Vui lòng coi lại số lượng duyệt > Số lượng kiểm định ";
			return false;
		}
		if(this.ngaykiem.trim().length()<=0)
		{
			this.msg="Vui lòng nhập ngày kiểm định ";
			return false;
		}
		return true;
	}

	public boolean HuyKiemDinh()
	{ 
		return false;
	}
	
	public boolean CheckNghiepVu()
	{
		String query="select count(*) from erp_lenhsanxuat_giay a inner join erp_lenhsanxuat_congdoan_Giay b on b.LenhSANXUAT_FK=a.pk_Seq where b.CongDoan_fk !='"+this.congdoanId+"' and b.TinhTrang =1 ";
		ResultSet rs =this.db.get(query);
			try
			{
				if(rs!=null)
				while(rs.next())
				if(rs.getInt(1)>0)
				{
					this.msg="Đã có công đoạn sản xuất hoàn tất trong lệnh sản xuất,bạn phải hủy các nghiệp vụ của công đoạn tiếp theo trước khi xóa nhập kho !";
					return false;
				}rs.close();
				//System.out.println("Check cong doan trong lenh san xuat "+query);
				if(!kiemdinhcongdoan)
				{
					query="select count(*) from Erp_NhapKho a inner join Erp_YeuCauKiemDinh b on b.nhapkho_fk=a.PK_SEQ where b.Pk_Seq='"+this.ycId+"' and trangthai!=2 ";
					rs=this.db.get(query);
					if(rs!=null)
					while(rs.next())
					if(rs.getInt(1)>0)
					{
						this.msg="Đã có phiếu nhập kho này,bạn phải xóa nhập kho trước khi xóa phiếu kiểm định !";
						return false;
					}
					//System.out.println("Check kiem dinh chat luong "+query);
				}
			} catch (SQLException e)
			{
				e.printStackTrace();
				return false;
			}
			return true;
	}


	public String getNguoiduyet()
	{
		return this.nguoiduyet;
	}


	public void setNguoiduyet(String nguoiduyet)
	{
		this.nguoiduyet=nguoiduyet;
		
	}
	
	public boolean IsKiemDinhCongDoan()
	{
		String query=
		"select case when cd.kiemdinhchatluong =1 and sp.kiemtradinhluong=0 or sp.kiemtradinhtinh=1 then 1 else 0 end as kiemdinhcongdoan  "+
		"from Erp_KichBanSanXuat_CongDoanSanXuat_Giay kbcd inner join erp_congdoansanxuat_giay cd on kbcd.congdoansanxuat_fk=cd.pk_seq  "+
		"left join erp_sanpham sp on sp.pk_seq=kbcd.sanpham_fk  " +
		"where kbcd.KichBanSanXuat_FK=(select KichBanSanXuat_FK from ERP_LENHSANXUAT_GIAY where PK_SEQ='"+this.lenhsanxuatId+"') "+
		"and cd.PK_SEQ= (select CONGDOAN_FK from ERP_LENHSANXUAT_CONGDOAN_GIAY where LENHSANXUAT_FK='"+this.lenhsanxuatId+"' and CONGDOAN_FK='"+this.congdoanId+"')";
		ResultSet rs=this.db.get(query);
		try
		{
			while(rs.next())
			{
				this.kiemdinhcongdoan=true;	
			}
			rs.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return this.kiemdinhcongdoan;
	}

	
	public void setIsKiemDinhCongDoan(boolean kiemdinhcongdoan)
	{
		
		this.kiemdinhcongdoan=kiemdinhcongdoan;
	}

	
	public ResultSet getRsCongDoan()
	{
		
		return this.rscongdoan;
	}

	
	public void setRsCongDoan(ResultSet rsCongdoan)
	{
		
			this.rscongdoan= rsCongdoan;
	}

	
	public ResultSet getRsLenhsanxuat()
	{
		
		return this.rslenhsanxuat;
	}

	
	public void setRsLenhsanxuat(ResultSet rsLenhsanxuat)
	{
		this.rslenhsanxuat=rsLenhsanxuat;
		
	}

	
	public String getDeNghiXuLy() {
		return this.denghixuly;
	}

	
	public void setDeNghiXuLy(String denghixuly) {
		this.denghixuly = denghixuly;
	}

	
	public ResultSet getRsKhoNhan() {
		
		return this.RsKhoNhan;
	}

	
	public void setRsKhoNhan(ResultSet rskho) {
		
		this.RsKhoNhan=rskho;
	}

	
	public String getKhoNhanId() {
		
		return this.KhoNhanId;
	}

	
	public void setKhoNhanId(String _KhoNhanId) {
		
		this.KhoNhanId=_KhoNhanId;
	}

	
	public ResultSet getRsKhuVucKho() {
		
		return this.RsKhuvuc;
	}

	
	public void setRsKhuVuckho(ResultSet rsKhuVucKho) {
		
		this.RsKhuvuc=rsKhuVucKho;
	}

	
	public String getKhuvuckhoid() {
		
		return this.KhuvucId;
	}

	
	public void setKhuvuckhoid(String Khuvuckhoid) {
		
		this.KhuvucId=Khuvuckhoid;
	}

	
	public boolean GetIsQuanLyKhuVuc() {
		
		return this.IsKhoNhanQl_khuvuc;
	}

	
	public String getsoluongmau() {
		
		return this.Soluongmau;
	}

	
	public void setsoluongmau(String soluongmau) {
		
		this.Soluongmau=soluongmau;
	}

	
	public String getNgaySanXuat() {
		
		return this.ngaysanxuat;
	}

	
	public void setNgaySanXuat(String ngaysanxuat) {
		
		this.ngaysanxuat=ngaysanxuat;
	}

	
	public void setRsDvdl(ResultSet rs) {
		
		this.rsdvdl=rs;
	}

	
	public ResultSet getRsDvdl() {
		
		return rsdvdl;
	}

	
	public void setDvdlId(String Dvdlid) {
		
		this.dvdlid=Dvdlid;
	}

	
	public ResultSet getDvdlid() {
		
		return this.rsdvdl;
	}

	
	public String getsoluongkhongdat() {
		
		return this.soluongkhongdat;
	}

	
	public void setsoluongkhongdat(String soluonghong) {
		
		this.soluongkhongdat=soluonghong;
	}

	
	public String getNgayNhanHang() {
		
		return this.ngaynhanhang;
	}

	
	public void setNgayNhanHang(String ngaynhanhang) {
		
		this.ngaynhanhang=ngaynhanhang;
	}

	
	public String getThongTinBom() {
		
		return this.thongtinbom;
	}

	
	public void setThongTinBom(String thongtinbom) {
		this.thongtinbom=thongtinbom;
		
	}

	
	public void setIsCapDong(String iscapdong) {
	
		this.IsCapdong=iscapdong;
	}

	
	public String getIsCapDong() {
	
		return this.IsCapdong;
	}

	
	public void Hoantat() {
 
	}

	
	public String getKyhieukd() {
	
		return this.kyhieukd;
	}

	
	public void setKyhieukd(String kyhieukd) {
	
		this.kyhieukd = kyhieukd;
	}

	@Override
	public String getMaphieu() {
		// TODO Auto-generated method stub
		return this.Maphieu;
	}

	@Override
	public void setMaphieu(String Maphieu) {
		// TODO Auto-generated method stub
		this.Maphieu= Maphieu;
	}

	@Override
	public void setRsKhonhanhangdat(ResultSet rs) {
		// TODO Auto-generated method stub
		 this.RsKhonhanhangdat=rs;
	}

	@Override
	public ResultSet getRsKhonhanhangdat() {
		// TODO Auto-generated method stub
		return this.RsKhonhanhangdat;
	}

	@Override
	public void setKhonhanhangdatId(String Khochuyenhangdatid) {
		// TODO Auto-generated method stub
		this.KhonhanhangdatId=Khochuyenhangdatid;
	}

	@Override
	public String getKhonhanhangdatId() {
		// TODO Auto-generated method stub
		return this.KhonhanhangdatId;
	}

 
	
}
