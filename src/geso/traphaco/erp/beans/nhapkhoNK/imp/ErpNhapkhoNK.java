package geso.traphaco.erp.beans.nhapkhoNK.imp;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.center.util.Utility_Kho;
import geso.traphaco.erp.beans.nhapkhoNK.IErpNhapkhoNK;
import geso.traphaco.erp.beans.nhapkhoNK.ISpDetail;
import geso.traphaco.erp.beans.shiphang.ISanpham;
import geso.traphaco.erp.beans.shiphang.imp.Sanpham;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ErpNhapkhoNK implements IErpNhapkhoNK
{
	String congtyId;
	String userId;
	String id;
	String ngaychungtu;
	String ngaychot;
	String dvthId;
	String ghichu;
	
	ResultSet dvthRs;

	String poId;
	ResultSet poRs;
	
	String nccId;
	ResultSet nccRs;

	String trangthai;
	String soluongPO;
	String soluongDaNhan;
	String tigia;

	String is_createRs="";
	
	List<ISanpham> spList;

	
	String msg;
	String pb;

	dbutils db;
	private Utility util;
	
	public ErpNhapkhoNK()
	{
		this.userId = "";
		this.id = "";
		this.ngaychungtu = "";
		this.ngaychot = "";

		this.dvthId = "";
		this.poId = "";

		this.trangthai = "";
		this.soluongPO = "";
		this.soluongDaNhan = "0";
		
		this.msg = "";
		this.nccId = "";

		this.pb="";
		this.tigia="1";
		this.is_createRs="";
		this.ghichu = "";
		
		this.spList = new ArrayList<ISanpham>();
		this.db = new dbutils();
		this.util=new Utility();
		
	}
	
	public ErpNhapkhoNK(String id)
	{
		this.userId = "";
		this.id = id;
		this.ngaychungtu = "";
		this.ngaychot = "";
		this.dvthId = "";
		this.poId = "";

		this.trangthai = "";
		this.soluongPO = "";
		this.soluongDaNhan = "0";


		this.msg = "";
		this.nccId = "";

		this.pb="";
		this.tigia="1";
		this.is_createRs="";
		this.ghichu = "";
		
		this.spList = new ArrayList<ISanpham>();
		this.db = new dbutils();
		this.util=new Utility();
	}
		
	
	public String  getTigia(){
		return this.tigia;
	}
	public void setTiia(String tigia){
		this.tigia = tigia;
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
	
	public String getNgaychungtu()
	{
		return this.ngaychungtu;
	}
	
	public void setNgaychungtu(String ngaychungtu)
	{
		this.ngaychungtu = ngaychungtu;
	}
	
	public String getDvthId()
	{
		return this.dvthId;
	}
	
	public void setDvthId(String dvthid)
	{
		this.dvthId = dvthid;
	}
	
	public ResultSet getDvthList()
	{
		return this.dvthRs;
	}
	
	public void setDvthList(ResultSet dvthlist)
	{
		this.dvthRs = dvthlist;
	}
	
	public String getTrangthai()
	{
		return this.trangthai;
	}
	
	public void setTrangthai(String trangthai)
	{
		this.trangthai = trangthai;
	}
	
	public String getDonmuahangId()
	{
		return this.poId;
	}
	
	public void setDonmuahangId(String dmhid)
	{
		this.poId = dmhid;
	}
	
	public ResultSet getDmhList()
	{
		return this.poRs;
	}
	
	public void setDmhList(ResultSet dmhlist)
	{
		this.poRs = dmhlist;
	}
	
	
	public String getTongSoluongPO()
	{
		return this.soluongPO;
	}
	
	public void setTongSoluongPO(String tongslgPO)
	{
		this.soluongPO = tongslgPO;
	}
	
	public String getTongSoluongDN()
	{
		return this.soluongDaNhan;
	}
	
	public void setTongSoluongDN(String tongslgDN)
	{
		this.soluongDaNhan = tongslgDN;
	}
	
	public List<ISanpham> getSpList()
	{
		return this.spList;
	}
	
	public void setSpList(List<ISanpham> spList)
	{
		this.spList = spList;
	}
	
	public String getMsg()
	{
		return this.msg;
	}
	
	public void setMsg(String msg)
	{
		this.msg = msg;
	}
	
	public void createRs()
	{
		String sql="select pk_seq, ten from ERP_NHACUNGCAP where trangthai = '1' and duyet = '1' and congty_fk = "+ this.congtyId +"  ";		
				//" and congty_fk = '" + this.congtyId + "' and pk_seq in " + this.util.quyen_donvithuchien(this.userId);;
		this.nccRs = db.get(sql);
		
		if(this.nccId.trim().length() > 0)
		{
			sql = " select mh.PK_SEQ, (SOPO + ' - ' +  NGAYMUA) TEN "
			    + " from ERP_MUAHANG mh inner join ERP_SHIPHANG sh on mh.PK_SEQ = sh.MUAHANG_FK "
			    + " where sh.TRANGTHAI = 1  and sh.NCC_FK = "+ this.nccId +" ";
			
			
			System.out.println("Câu lấy PO "+sql);
			this.poRs = db.get(sql);
			
		}

		
		if( this.id.trim().length() <= 0 && this.poId.trim().length() > 0)
		{
			String	query1 = "SELECT DISTINCT mh.PK_SEQ as MUAHANGID, sp.PK_SEQ as SPID, sp.MA as MASP, sp.TEN as TENSP, mhsp.DONVI, shsp.SOLUONG, '' as NGAYNHAN, "
					       + "mhsp.thuexuat as vat, mhsp.dongia, (select pk_seq from KHO where loai = 7) KHONHANID, (select ten from KHO where loai = 7)  KHONHANTEN, isnull(hansudung, 0) hansudung "
					       + "FROM ERP_MUAHANG mh INNER JOIN ERP_MUAHANG_SP mhsp on mh.PK_SEQ = mhsp.MUAHANG_FK "
					       +"      INNER JOIN ERP_SHIPHANG sh on sh.MUAHANG_FK = mh.PK_SEQ "
					       + "     INNER JOIN ERP_SHIPHANG_SANPHAM shsp on sh.PK_SEQ = shsp.SHIPHANG_FK and mhsp.SANPHAM_FK = shsp.SANPHAM_FK "
					       + "     INNER JOIN ERP_SANPHAM sp on shsp.SANPHAM_FK = sp.PK_SEQ  "
					       + "WHERE mh.PK_SEQ = "+ this.poId +" and shsp.SOLUONG > 0 ";
			
					 				
				System.out.println("ds sanpham :"+ query1);
				
				ResultSet rs = db.get(query1);
				List<ISanpham> spList = new ArrayList<ISanpham>();
				
				NumberFormat formatter = new DecimalFormat("#,###,###.###");
				
				if(rs!=null){
					ISanpham sp = null;
					try{
					while(rs.next()){
						sp= new Sanpham();
				
						sp.setMuahang_fk(rs.getString("MUAHANGID"));
						sp.setMa(rs.getString("MASP"));
						sp.setId(rs.getString("SPID"));
						sp.setDiengiai(rs.getString("TENSP"));
						sp.setSoluongdat(formatter.format(rs.getDouble("SOLUONG")));
						sp.setDongia(formatter.format(rs.getDouble("DONGIA")));
						sp.setSoluongnhan("");
						sp.setDvdl(rs.getString("DONVI"));
						sp.setNgaynhandukien(rs.getString("NGAYNHAN"));	
						sp.setKhonhanId(rs.getString("KHONHANID"));
						sp.setKhonhanTen(rs.getString("KHONHANTEN"));
						sp.setVat(rs.getString("vat"));
						sp.setHansudung(rs.getString("hansudung"));
						
						sp.setSongayluukho("");
						
						spList.add(sp);
						
					}rs.close();}catch(Exception e){
						e.printStackTrace();
					}	
				}
				
				this.spList = spList;
				
				
			}			
			
		
		
	}
	
	public void init()
	{
		String query =  " SELECT a.PK_SEQ as shId, a.trangthai, a.NGAYNHAP, isnull(a.GHICHU,'') GHICHU,    \n" +
						" 	     a.NCC_FK, a.muahang_fk as muahang_fk, c.sopo as sopo  \n" +
						" FROM ERP_NHAPKHONHAPKHAU a  \n" +
						"      inner join ERP_MUAHANG c on a.MUAHANG_FK = c.PK_SEQ \n" +
						" WHERE a.pk_seq = '" + this.id + "' ";
		
		System.out.println("Init : " + query);
		
		ResultSet rs = db.get(query);
		if (rs != null)
		{
			try
			{
				while (rs.next())
				{
					this.ngaychungtu = rs.getString("NGAYNHAP");
					this.trangthai = rs.getString("TRANGTHAI");
					this.poId = rs.getString("muahang_fk")== null? "" : rs.getString("muahang_fk");

					this.trangthai = rs.getString("trangthai");
					this.nccId = rs.getString("NCC_FK") == null ? "" : rs.getString("NCC_FK");
					this.ghichu = rs.getString("GHICHU");
					
				}
				rs.close();
			}
			catch (Exception e)
			{
				System.out.println("1.Exception: " + e.getMessage());
			}
		}
				

		this.initSanPham();
		
		this.createRs();
	}
	
	private void initSanPham()
	{
		
	
		NumberFormat formatter = new DecimalFormat("#,###,###.##"); 
		
		String query = " SELECT  nksp.SANPHAM_FK spId, isnull(sp.MA, isnull(sp.ma, ''))  spMa, isnull(sp.Ten1, sp.Ten)   spTen,  isnull(nksp.DonVi, 'NA') as donvi, \n"+ 
					"         nksp.dongia, nksp.vat, nksp.NGAYNHAP, nksp.SOLUONGNHAN, nksp.SOLUONG, nksp.NGAYNHAP, nksp.SONGAYLUUKHO, nksp.KHONHAN_FK, KHO.TEN KHONHANTEN,  \n"+ 
					"         (select SUM(SOLUONG) from ERP_SHIPHANG_SANPHAM where SHIPHANG_FK = nk.SHIPHANG_FK and SANPHAM_FK = nksp.SANPHAM_FK ) -  \n"+
					"          ISNULL((select SUM(b.SOLUONGNHAN) \n"+
					"                  from ERP_NHAPKHONHAPKHAU a inner join ERP_NHAPKHONHAPKHAU_SANPHAM b on b.NHAPKHO_FK = a.PK_SEQ \n"+
					"                  where  a.SHIPHANG_FK = nk.SHIPHANG_FK and a.TRANGTHAI !=2 and a.PK_SEQ!= '" + this.id + "'  and b.SANPHAM_FK = nksp.SANPHAM_FK \n"+
					"                  group by b.SANPHAM_FK) ,0 ) SOLUONGMAX, isnull(sp.hansudung, 0) as hansudung \n"+
				    " FROM ERP_NHAPKHONHAPKHAU_SANPHAM nksp   \n"+
				    " INNER JOIN ERP_NHAPKHONHAPKHAU nk ON nksp.NHAPKHO_FK = nk.PK_SEQ \n"+
					" INNER JOIN KHO ON nksp.KHONHAN_FK = KHO.PK_SEQ \n"+
				  	" INNER join ERP_SANPHAM sp on nksp.SANPHAM_FK = sp.PK_SEQ   \n"+ 							  
				    " WHERE nksp.NHAPKHO_FK = '" + this.id + "'";
		
		System.out.println("[ErpNhanhang_Giay.initSanPham] query = " + query);
		ResultSet rsSp = db.get(query);
		
		if (rsSp != null)
		{
//			NumberFormat formater = new DecimalFormat("#,###,###.##");
			
			try
			{
				ISanpham sanpham;
				List<ISanpham> spList = new ArrayList<ISanpham>();
				while (rsSp.next())
				{
					String spId = rsSp.getString("spId");
					String spMa = rsSp.getString("spMa");
					String spTen = rsSp.getString("spTen");
					String ngaynhandk = rsSp.getString("NGAYNHAP");
					
					String soluongdat = formatter.format(rsSp.getDouble("SOLUONG"));
					String dongia = formatter.format(rsSp.getDouble("DONGIA"));
					String dvdl = rsSp.getString("DonVi");
					String soluongnhan = formatter.format(rsSp.getDouble("SOLUONGNHAN"));	
					String soluongMaxNhan = formatter.format(rsSp.getDouble("SOLUONGMAX"));	
					String songayluukho = formatter.format(rsSp.getDouble("SONGAYLUUKHO"));
					String khonhanId = rsSp.getString("KHONHAN_FK");
					String khonhanTen = rsSp.getString("KHONHANTEN");
					String vat = rsSp.getString("vat");
					String hansudung = rsSp.getString("hansudung");
					
					sanpham = new Sanpham(spId, spMa, spTen, "", "", ngaynhandk, soluongdat, dvdl);
					
					sanpham.setSoluongnhan(soluongnhan);
					sanpham.setSoluongMaxNhan(soluongMaxNhan);
					sanpham.setSongayluukho(songayluukho);
					sanpham.setKhonhanId(khonhanId);
					sanpham.setKhonhanTen(khonhanTen);
					sanpham.setVat(vat);
					sanpham.setHansudung(hansudung);
					sanpham.setDongia(dongia);
					System.out.println("[dongia]"+dongia);
					List<ISpDetail> lstSPdt = new ArrayList<ISpDetail>();
					if(this.id.trim().length() > 0)
					{
						query = "select nksp.solo, nksp.soluong, nksp.ngaysx, nksp.ngayhethan from ERP_NHAPKHONHAPKHAU_SP_chitiet nksp " +
								  " WHERE nksp.nhapkho_fk = '" + this.id + "' and nksp.SANPHAM_FK = " + spId;
						ResultSet spdtRs = db.get(query);
						if (spdtRs != null)
							while (spdtRs.next())
							{
								ISpDetail spdt = new SpDetail();
								spdt.setSolo(spdtRs.getString("solo"));
								spdt.setSoluong(spdtRs.getString("soluong"));
								spdt.setNgaySx(spdtRs.getString("ngaysx"));
								spdt.setNgayHethan(spdtRs.getString("ngayhethan"));
								lstSPdt.add(spdt);
							}
					}
					sanpham.setSpDetail(lstSPdt);
					
					spList.add(sanpham);
				}
				rsSp.close();
				this.spList = spList;
			
			}
			catch (Exception e)
			{
				System.out.println("115.Exception: " + e.getMessage());
				e.printStackTrace();
				
			}
		}
	}
	
	public void initPdf(String spId)
	{
		String query =  " select isnull(c.LOAISANPHAM_FK,0) as LOAISANPHAM_FK, a.PK_SEQ as nhId, a.trangthai, a.loaihanghoa_fk, a.noidungnhan_fk, a.SOHOADON, a.NGAYNHAN, a.NGAYTAO, a.diengiai, b.pk_seq as dvthId, b.TEN as dvthTen, a.TRANGTHAI, a.NoiDungNhap_fk , a.NCC_KH_FK, " +
						" 	case when a.noidungnhan_fk = 100000 then  SOPO else d.Prefix + cast(d.PK_SEQ as varchar(10)) end as sochungtu,  " +
						"   case when a.noidungnhan_fk = 100000 then ncc.TEN else kh.TEN end as donviban " +
						" from erp_nhanhang a inner join ERP_DONVITHUCHIEN b on a.DONVITHUCHIEN_FK = b.PK_SEQ " +
						" left join ERP_MUAHANG c on a.MUAHANG_FK = c.PK_SEQ " +
						" left join DonTraHang d on a.TRAHANG_FK = d.PK_SEQ " +
						" left join Erp_Nhacungcap ncc on ncc.PK_SEQ=c.NHACUNGCAP_FK " +
						" left join ERP_KHACHHANG kh on kh.PK_SEQ=d.KHACHHANG_FK " +
						" where a.pk_seq = '" + this.id + "' and b.pk_seq in "+this.util.quyen_donvithuchien(this.userId);
		
		System.out.println("[ErpNhanhang_Giay.initPdf] Khoi tao nhan hang: " + query );
		ResultSet rs = db.get(query);
		if (rs != null)
		{
			try
			{
				while (rs.next())
				{				
					this.ngaychot = rs.getString("ngaytao"); //NGAY TAO
					this.ngaychungtu = rs.getString("ngaynhan");
					this.dvthId = rs.getString("donviban");
					this.poId = rs.getString("sochungtu");
					
					
				}
				rs.close();
			}
			catch (SQLException e)
			{
			}
		}
		
		this.initSanPhamPdf(spId);
	}
	
	
	private void initSanPhamPdf(String _spId)
	{	
 
		String query = " SELECT isnull(sp.loaisanpham_fk,0) as  loaisanpham_fk, CASE NH.LOAIHANGHOA_FK WHEN 0 THEN A.SANPHAM_FK WHEN 1 THEN TSCD.PK_SEQ ELSE NCP.PK_SEQ END AS SPID,  " +  
					   "  CASE NH.LOAIHANGHOA_FK WHEN 0 THEN ISNULL( CASE WHEN ( LEN(LTRIM(RTRIM(SP.MA))) <= 0 OR ( SP.MA IS NULL ) ) THEN SP.MA ELSE SP.MA END, '' )  WHEN 1 THEN TSCD.MA ELSE NCP.TEN END AS SPMA,   " +  
					   "  CASE NH.LOAIHANGHOA_FK WHEN 0 THEN ISNULL(SP.TEN1, ISNULL(SP.TEN, ''))  ELSE ISNULL(A.DIENGIAI, '') END AS SPTEN,  " +  
					   "  A.NGAYNHANDUKIEN, A.DUNGSAI, A.DONGIA, A.SOLUONGDAT, A.SOLUONGNHAN, ISNULL(SP.HANSUDUNG, '0') AS HANSUDUNG,  " +  
					   "  ISNULL(A.DONVI, 'NA') AS DONVI, A.TIENTE_FK, A.TYGIAQUYDOI, A.DONGIAVIET,  " +  
					   "  KHOTT.PK_SEQ AS KHOTTID, KHOTT.MA + ', ' + KHOTT.TEN AS KHOTEN, " +  
					   "  CASE WHEN ISNULL(QC.SOLUONG1,'0') = '0' THEN   " +  
					   "  A.SOLUONGNHAN ELSE A.SOLUONGNHAN *  ISNULL(QC.SOLUONG2,'0') /QC.SOLUONG1 END AS TRONGLUONG , " +  
					   "  CAST(ROUND(ISNULL(SP.THETICH, 0), 5) AS NUMERIC(10, 5)) AS THETICH  " +  
					   "  FROM ERP_NHANHANG_SANPHAM A INNER JOIN ERP_NHANHANG NH ON A.NHANHANG_FK = NH.PK_SEQ    " +  
					   "  LEFT JOIN ERP_SANPHAM SP ON A.SANPHAM_FK = SP.PK_SEQ    " +  
					   "  LEFT JOIN QUYCACH QC ON QC.SANPHAM_FK=SP.PK_SEQ AND SP.DVDL_FK=QC.DVDL1_FK AND QC.DVDL2_FK=100003   " +  
					   "  LEFT JOIN ERP_TAISANCODINH TSCD ON A.TAISAN_FK = TSCD.PK_SEQ         " +  
					   "  LEFT JOIN ERP_NHOMCHIPHI NCP ON A.CHIPHI_FK = NCP.PK_SEQ   " +  
					   "  LEFT JOIN ERP_KHOTT KHOTT ON A.KHONHAN = KHOTT.PK_SEQ   " +  
					   "  WHERE A.NHANHANG_FK = "+this.id+" AND A.SOLUONGNHAN >0 " ;
 
		if(_spId.length() > 0) {
			query += " and a.SANPHAM_FK in (" + _spId + ")";
		}
 
		ResultSet rsSp = db.get(query);
		
		if (rsSp != null)
		{
			try
			{
				ISanpham sanpham;
				List<ISanpham> spList = new ArrayList<ISanpham>();
				while (rsSp.next())
				{
					String spId = rsSp.getString("spId");
					//System.out.println("[ErpNhanhang_Giay.initSanPhamPdf] spId = " + spId);
					String spMa = rsSp.getString("spMa");
					
					
					//--- nếu sản phẩm là GLUE, ko in quy cách -----//
					String loaisp = rsSp.getString("loaisanpham_fk");
				 
					String spTen="";
					if(loaisp.equals("100015") || loaisp.equals("100016")){
						spTen = rsSp.getString("spten1");
					}
					else{
					 spTen = rsSp.getString("spTen");
					}
					
					
					String ngaynhandk = rsSp.getString("NGAYNHANDUKIEN");
					String soluongdat = rsSp.getString("SOLUONGDAT");
					String soluongnhan = rsSp.getString("SOLUONGNHAN");
					String hansudung = rsSp.getString("khoTen"); // luu ten kho
					String dvdl = rsSp.getString("DonVi");
					
					sanpham = new Sanpham(spId, spMa, spTen, soluongnhan, hansudung, ngaynhandk, soluongdat, dvdl);
					sanpham.setTrongluong( rsSp.getString("trongluong")  );
					sanpham.setThetich( rsSp.getString("THETICH") == null ? "0" : rsSp.getString("THETICH") );
					spList.add(sanpham);
				}
				rsSp.close();
				this.spList = spList;
			}
			catch (Exception e)
			{
				e.printStackTrace();
 
			}
		}
	}
	
	
	public boolean createNhapKho()
	{
		String ngaytao = this.getDateTime();
		
		if(this.ngaychungtu.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn ngày nhập";
			return false;
		}
		
		if (this.spList.size() <= 0)
		{
			this.msg = "Không có sản phẩm nào để nhập kho, vui lòng kiểm tra lại";
			return false;
		}else
		{
			for (int i = 0; i < spList.size(); i++)
			{
				ISanpham sp = spList.get(i);
				if(Double.parseDouble(sp.getSoluongnhan()) > Double.parseDouble(sp.getSoluongdat()) )
				{
					this.msg = "Số lượng nhận không được vượt quá số lượng đặt";
					return false;
				}
				
				if(Double.parseDouble(sp.getSoluongnhan()) > 0)
				{
					if(sp.getSongayluukho().trim().length() <= 0)
					{
						this.msg = "Vui lòng nhập số ngày lưu kho";
						return false;
					}
					if(sp.getNgaynhandukien().trim().length() <= 0)
					{
						this.msg = "Vui lòng nhập ngày nhập kho";
						return false;
					}
				}
				
				
			}
		}
		
		String query = "";
		
		try
		{
			db.getConnection().setAutoCommit(false);

		
			String nppId= util.getIdNhapp(this.userId);
					
								
				query = "insert ERP_NHAPKHONHAPKHAU(NGAYNHAP, GHICHU, MUAHANG_FK,  NGAYTAO, NGAYSUA, NGUOITAO, NGUOISUA, TRANGTHAI, CONGTY_FK, NPP_FK, NCC_FK) " +
					    "values('" +this.ngaychungtu +"', N'" +this.ghichu +"', "+ this.poId +", '" + ngaytao +"', '" +ngaytao +"', '" +this.userId + "', '" +this.userId +"', '0', '" + this.congtyId + "', "+ nppId +", "+ this.nccId +")";
				System.out.println(" Câu insert ERP_NHAPKHONHAPKHAU :"+ query);
				
				if (!db.update(query))
				{
					this.msg = "Khong the tao moi Ship hang: " + query;
					db.getConnection().rollback();
					return false;
				}
			
			String nkCurrent = "";
			query = "select IDENT_CURRENT('ERP_NHAPKHONHAPKHAU') as nkId";
			
			ResultSet rs1=  db.get(query);
			
			if(rs1!=null){
				try{
					while(rs1.next()){
						this.id = rs1.getString("nkId");
					}
				}catch(Exception e){
					e.printStackTrace();
				}	
			}
			
			ResultSet rsNh = db.get(query);
			if (rsNh.next())
			{
				nkCurrent = rsNh.getString("nkId");
				rsNh.close();
			}
			
			if (this.spList.size() > 0)
			{
				for (int i = 0; i < spList.size(); i++)
				{
					ISanpham sp = spList.get(i);
					List<ISpDetail> lstspdt = sp.getSpDetail();
					if(lstspdt.size() <= 0)
					{
						this.msg = "Khong the tao moi ERP_NHAPKHONHAPKHAU_SP_CHITIET: Chua nhap chi tiet nhap kho.";
						System.out.println(this.msg);
						db.getConnection().rollback();
						return false;
					}						
					query = "insert ERP_NHAPKHONHAPKHAU_SANPHAM(NHAPKHO_FK, SANPHAM_FK, DONVI,SOLUONG, SOLUONGNHAN, KHONHAN_FK, NGAYNHAP, SONGAYLUUKHO, vat, dongia) " +
						   "values('" + nkCurrent + "', " + sp.getId() + ", N'" + sp.getDvdl() + "','" + sp.getSoluongdat().replaceAll(",", "") + "'  " +
							", " + sp.getSoluongnhan().replaceAll(",", "") + ", "+ sp.getKhonhanId() +", '" + sp.getNgaynhandukien() + "', "+ sp.getSongayluukho().replaceAll(",", "")  +", "+ sp.getVat().replaceAll(",", "")  +", "+ sp.getDongia().replaceAll(",", "")  +")";

					if (!db.update(query))
					{
						this.msg = "Khong the tao moi ERP_NHAPKHONHAPKHAU_SANPHAM: " + query;
						System.out.println(this.msg);
						db.getConnection().rollback();
						return false;
					}					

					double soluongdat = Double.parseDouble(sp.getSoluongdat().replace(",", ""));
					double soluongchitiet = 0;
					
					for(int j = 0; j < lstspdt.size(); j++)
					{
						ISpDetail spdt = lstspdt.get(j);
						if(spdt.getSolo().trim().length() > 0)
						{
							query = "insert into ERP_NHAPKHONHAPKHO_SP_chitiet (nhapkho_fk, sanpham_fk, solo, soluong, ngaysx, ngayhethan, ngaynhap, khonhan_fk, vat, dongia, donvi)"
									+ "Values('"+this.id+"', '" + sp.getId() + "','" + spdt.getSolo().replace(",", "") + "','" + spdt.getSoluong().replace(",", "") + "','" + spdt.getNgaySx() + "','" + spdt.getNgayHethan() + "', '"+sp.getNgaynhandukien()+"', (select pk_seq from kho where loai = 7), "+ sp.getVat().replaceAll(",", "") +", "+ sp.getDongia().replaceAll(",", "") +", N'" + sp.getDvdl() + "')";
							System.out.println("insert ERP_NHAPKHONHAPKHO_SP_chitiet "+query);
							if (!db.update(query))
							{
								this.msg = "Khong the tao moi nhap kho nhap khau: " + query;
								System.out.println("3.Exception tai day: " + query);
								db.getConnection().rollback();
								return false;
							}
							
							soluongchitiet += Double.parseDouble(spdt.getSoluong().replace(",", ""));
						}
					}
					if (soluongdat < soluongchitiet)
					{
						this.msg = "Khong the tao moi nhap kho nhap khau: so luong chi tiet khong duoc lon hon so luong tong";
						System.out.println("3.Exception tai day: " + query);
						db.getConnection().rollback();
						return false;
					}
				}
			}
			db.execProceduce2("CapNhatTooltip_NKNNK", new String[] { this.id } );
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;
		}
		catch (Exception e)
		{
			this.msg="Lỗi tạo ship hàng: "+e.getMessage();
			e.printStackTrace();
			db.update("rollback");
			 
			return false;
		}
		
	}
	
	
	public boolean updateNhapKho()
	{

		if (this.spList.size() <= 0)
		{
			this.msg = "Không có sản phẩm nào để nhận hàng, vui lòng kiểm tra lại";
			return false;
		}
		else
		{
			for (int i = 0; i < spList.size(); i++)
			{
				ISanpham sp = spList.get(i);
				if(Double.parseDouble(sp.getSoluongnhan()) > Double.parseDouble(sp.getSoluongMaxNhan()) )
				{
					this.msg = "Số lượng nhận không được vượt quá số lượng ship hàng (" + sp.getMa() + " - " + sp.getDiengiai() + " )";
					return false;
				}
				
				if(Double.parseDouble(sp.getSoluongnhan()) > 0)
				{
					if(sp.getSongayluukho().trim().length() <= 0)
					{
						this.msg = "Vui lòng nhập số ngày lưu kho";
						return false;
					}
					if(sp.getNgaynhandukien().trim().length() <= 0)
					{
						this.msg = "Vui lòng nhập ngày nhập kho";
						return false;
					}
				}
			}
		}
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query = ""; 
			
		
			String nppId= util.getIdNhapp(this.userId);

				query = "update ERP_NHAPKHONHAPKHAU set NGAYNHAP = '" + this.ngaychungtu + "',GHICHU = N'" + this.ghichu + "', " +
				"   MUAHANG_FK = " +this.poId + ", NGAYSUA = '" + this.getDateTime() + "', " + "NGUOISUA = '" + this.userId + "', NCC_FK = " + this.nccId + ", CONGTY_FK = "+ this.congtyId +", NPP_FK = "+ nppId +" " +
				"   where pk_seq = '" + this.id + "'";
			
			  System.out.println("Query update: " + query);
			
			if (!db.update(query))
			{
				this.msg = "Khong the cập nhật nhập kho: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_NHAPKHONHAPKHAU_SANPHAM where nhapkho_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat ERP_NHAPKHONHAPKHAU_SANPHAM: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_NHAPKHONHAPKHAU_SP_CHITIET where nhapkho_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat ERP_NHAPKHONHAPKHAU_SP_CHITIET: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			if (this.spList.size() > 0)
			{
				for (int i = 0; i < spList.size(); i++)
				{
					ISanpham sp = spList.get(i);
					List<ISpDetail> lstspdt = sp.getSpDetail();
					if(lstspdt.size() <= 0)
					{
						this.msg = "Khong the tao moi ERP_NHAPKHONHAPKHAU_SP_CHITIET: Chua nhap chi tiet nhap kho.";
						System.out.println(this.msg);
						db.getConnection().rollback();
						return false;
					}
					query = "insert ERP_NHAPKHONHAPKHAU_SANPHAM(NHAPKHO_FK, SANPHAM_FK, DONVI,SOLUONG, SOLUONGNHAN, KHONHAN_FK, NGAYNHAP, SONGAYLUUKHO, vat, dongia) " +
							"values('" + this.id + "', " + sp.getId() + ",  N'" + sp.getDvdl() + "','" + sp.getSoluongdat().replaceAll(",", "") + "'  " +
							", " + sp.getSoluongnhan().replaceAll(",", "") + ", "+ sp.getKhonhanId() +", '" + sp.getNgaynhandukien() + "', "+ sp.getSongayluukho().replaceAll(",", "")  +", "+ sp.getVat().replaceAll(",", "") +", "+ sp.getDongia().replaceAll(",", "") +")";

					if (!db.update(query))
					{
						this.msg = "Khong the tao moi ERP_NHAPKHONHAPKHAU_SANPHAM: " + query;
						System.out.println(this.msg);
						db.getConnection().rollback();
						return false;
					}
					
					double soluongdat = Double.parseDouble(sp.getSoluongdat().replace(",", ""));
					double soluongchitiet = 0;
					
					for(int j = 0; j < lstspdt.size(); j++)
					{
						ISpDetail spdt = lstspdt.get(j);
						if(spdt.getSolo().trim().length() > 0)
						{
							query = "insert into ERP_NHAPKHONHAPKHAU_SP_CHITIET (nhapkho_fk, sanpham_fk, solo, soluong, ngaysx, ngayhethan, ngaynhap, khonhan_fk, vat, dongia, donvi)"
									+ "Values('"+this.id+"', '" + sp.getId() + "','" + spdt.getSolo().replace(",", "") + "','" + spdt.getSoluong().replace(",", "") + "','" + spdt.getNgaySx() + "','" + spdt.getNgayHethan() + "', '"+sp.getNgaynhandukien()+"', (select pk_seq from kho where loai = 7), "+ sp.getVat().replaceAll(",", "")  +", "+ sp.getDongia().replaceAll(",", "") +", N'" + sp.getDvdl() + "')";
							System.out.println("insert ERP_NHAPKHONHAPKHAU_SP_CHITIET "+query);
							if (!db.update(query))
							{
								this.msg = "Khong the tao moi nhap kho nhap khau: " + query;
								System.out.println("3.Exception tai day: " + query);
								db.getConnection().rollback();
								return false;
							}
							
							soluongchitiet += Double.parseDouble(spdt.getSoluong().replace(",", ""));
						}
					}
					if (soluongdat < soluongchitiet)
					{
						this.msg = "Khong the tao moi nhap kho nhap khau: so luong chi tiet khong duoc lon hon so luong tong";
						System.out.println("3.Exception tai day: " + query);
						db.getConnection().rollback();
						return false;
					}
				}
			}
			db.execProceduce2("CapNhatTooltip_NKNNK", new String[] { this.id } );
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;
		}
		catch (Exception e)
		{
			this.msg="Lỗi không thể cập nhật nhập kho: "+e.getMessage();
			e.printStackTrace();
			db.update("rollback");
			return false;
		}
	}

	
	public void close()
	{
		try{
		
			if(spList!=null)
			{
				spList.clear();
			}
			if(dvthRs!=null){
				dvthRs.close();
			}
			if(poRs!=null){
				poRs.close();
			}
			db.shutDown();
			
		}catch(Exception er){
			
		}
	}
	
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	

	public String getNgaychot() 
	{
		return this.ngaychot;
	}

	public void setNgaychot(String ngaychot)
	{
		this.ngaychot = ngaychot;
	}

	public String getCongtyId() 
	{
		return this.congtyId;
	}

	public void setCongtyId(String congtyId) 
	{
		this.congtyId = congtyId;
	}

	
	
	
	public void setNccId(String ndnId) {
		
		this.nccId = ndnId;
	}

	
	public String getNccId() {
		
		return this.nccId;
	}

	
	public ResultSet getNccList() {
		
		return this.nccRs;
	}

	
	public void setNccList(ResultSet nccList) {
		
		this.nccRs = nccList;
	}


	
	public String getPhongBan() {
		
		return this.pb;
	}

	
	public void setPhongBan(String phongban) {
		
		this.pb=phongban;
	}

	
	public String getGhichu() {
		
		return this.ghichu;
	}
	
	public void setGhichu(String ghichu) {
		
		this.ghichu=ghichu;
	}

	public String ChotNhapKho(String id, String nccId, String userId) 
	{
		String msg = "";
		String query = "";
		
		dbutils  db = new dbutils();
//		Utility util = new Utility();
		Utility_Kho util_kho=new Utility_Kho();
		
		
		try {
			
			db.getConnection().setAutoCommit(false);
			
			String ngaychotNV = this.getDateTime();
//			String npp_fk = util.getIdNhapp(userId);
			
			// Tăng kho của nhà cung cấp (loại kho = 7)
			
			query = "SELECT DISTINCT sp.SANPHAM_FK , mh.DONGIA, spct.solo, spct.SOLUONG, sp.NGAYNHAP, sp.SONGAYLUUKHO, sp.KHONHAN_FK, spct.ngaysx, spct.ngayhethan "
				  + "FROM ERP_NHAPKHONHAPKHAU_SP_CHITIET spct inner join ERP_NHAPKHONHAPKHAU_SANPHAM sp on spct.nhapkho_fk = sp.nhapkho_fk and spct.sanpham_fk = sp.sanpham_fk "
				  + "	  inner join ERP_NHAPKHONHAPKHAU nk on sp.NHAPKHO_FK = nk.PK_SEQ  "
				  + "     INNER JOIN ERP_MUAHANG_SP mh on nk.MUAHANG_FK = mh.MUAHANG_FK and sp.SANPHAM_FK = mh.SANPHAM_FK  "
				  + "WHERE sp.NHAPKHO_FK = "+ id +" and sp.SOLUONGNHAN > 0 ";
			System.out.println("Câu lấy SP "+query);
			ResultSet spRs = db.get(query);
			boolean kt = false;
			if(spRs!= null)
			{
				while(spRs.next())
				{
					kt = true;
					String spId = spRs.getString("SANPHAM_FK");
					String solo = spRs.getString("solo");
					double soluongnhan = spRs.getDouble("SOLUONG");
					double dongia = spRs.getDouble("DONGIA");
//					String ngaynhan = spRs.getString("NGAYNHAP");
					String khonhap = spRs.getString("KHONHAN_FK");
					String ngaysx = spRs.getString("ngaysx");
					String ngayhethan = spRs.getString("ngayhethan");
					
					 double booked=0;
					 double available = spRs.getDouble("SOLUONG");
					 
					 // Tăng kho nhà cung cấp
					 msg= util_kho.Update_NPP_Kho_Sp(ngaychotNV,"Nhập kho nhập khẩu",db,khonhap, spId, this.congtyId,"100000", soluongnhan,booked,available,dongia);
					 
					 if(msg.length() >0)
					 {
						db.getConnection().rollback();
						return msg;
					 }
					 
					 msg= util_kho.Update_NPP_Kho_Sp_NCC(ngaychotNV,"Nhập kho nhập khẩu",db,khonhap, spId, this.congtyId,"100000", soluongnhan,booked,available,dongia,nccId);
					 
					 if(msg.length() >0)
					 {
						db.getConnection().rollback();
						return msg;
					 }
					 
					 msg= util_kho.Update_NPP_Kho_Sp_Chitiet_NCC(ngaychotNV,"Nhập kho nhập khẩu",db,khonhap, spId, this.congtyId,"100000",nccId,solo, ngaysx, ngayhethan, ngaychotNV, soluongnhan,booked,available,dongia);
					 
					 if(msg.length() >0)
					 {
						db.getConnection().rollback();
						return msg;
					 }
					 				
				}spRs.close();
			}
			if(!kt)
			{
				msg = "Khong the chot nhap kho: khong co chi tiet nhap kho.";
				db.getConnection().rollback();
				return msg;
			}
			
			query = "update ERP_NHAPKHONHAPKHAU set TRANGTHAI = '1' , NGAYCHOT = '"+ this.getDateTime() +"' where PK_SEQ = "+ id +" ";
			System.out.println("Câu cập nhật NK "+query);
			if (!db.update(query))
			{
				msg = "Khong cập nhật ERP_NHAPKHONHAPKHAU: " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			query = "update ERP_MUAHANG set NHACUNGCAP_FK = "+ nccId +" where PK_SEQ = ( select MUAHANG_FK from ERP_NHAPKHONHAPKHAU where PK_SEQ = "+ id +" )";
			System.out.println("Câu cập nhật MH "+query);
			if (!db.update(query))
			{
				msg = "Khong cập nhật ERP_MUAHANG: " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			// Kiểm tra nếu SL nhập = SL ship hàng >> Cập nhật Ship hàng: hoàn tất
			query = "SELECT ISNULL(ct.SLSHIP,0) - "
				  + "       ISNULL((select SUM(SP.SOLUONGNHAN)  "
				  + "		        from ERP_NHAPKHONHAPKHAU NK inner join ERP_NHAPKHONHAPKHAU_SANPHAM SP ON NK.PK_SEQ = SP.NHAPKHO_FK "
				  + "		        where NK.TRANGTHAI = '1' AND NK.SHIPHANG_FK = sh.PK_SEQ),0) CONLAI "
				  + "FROM ERP_SHIPHANG sh inner join "
				  + "     (select SHIPHANG_FK, SUM(SOLUONG) SLSHIP from ERP_SHIPHANG_SANPHAM  group by SHIPHANG_FK ) ct"
				  + "     on sh.PK_SEQ = ct.SHIPHANG_FK "
				  + "WHERE sh.PK_SEQ in (select shiphang_fk from erp_nhapkhonhapkhau where pk_seq = "+ id +" ) ";
			ResultSet rsKT = db.get(query);
			int ishoantat = 0;
			if(rsKT.next())
			{
				if(rsKT.getInt("CONLAI") <= 0){
					ishoantat = 1;
				}
				
			}rsKT.close();
			
			if(ishoantat >= 1)
			{
				query = "update ERP_SHIPHANG set ISHOANTAT = '1'  where PK_SEQ = ( select shiphang_fk from erp_nhapkhonhapkhau where pk_seq = "+ id +" )";
				System.out.println("Câu cập nhật SH "+query);
				if (!db.update(query))
				{
					msg = "Khong cập nhật ERP_SHIPHANG: " + query;
					db.getConnection().rollback();
					return msg;
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			
			try {
				msg = "Xảy ra lỗi trong quá trình chốt ";
				db.getConnection().rollback();
				return msg;
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
		}
		
		return msg;
	}

	public String CreateNK(String shId) 
	{
		String msg ="";
		
		String query = "";
		
		// Kiem tra so luong con du de nhap kho NK khong
		query = "SELECT SP.PK_SEQ, ISNULL(SHSP.SOLUONG,0) - "
			  +	"     ISNULL((select SUM(CT.SOLUONGNHAN) "
			  + "            from ERP_NHAPKHONHAPKHAU NK inner join ERP_NHAPKHONHAPKHAU_SANPHAM CT ON NK.PK_SEQ = CT.NHAPKHO_FK "
			  + "            where SHSP.SANPHAM_FK = CT.SANPHAM_FK AND NK.TRANGTHAI != 2 AND NK.SHIPHANG_FK =  SHSP.SHIPHANG_FK   "
			  + "            group by CT.SANPHAM_FK ),0) SLCONLAI "
			  + "FROM ERP_SHIPHANG_SANPHAM SHSP INNER JOIN ERP_SANPHAM SP ON SHSP.SANPHAM_FK = SP.PK_SEQ "
			  + "WHERE SHIPHANG_FK = "+ shId +" "; 
		System.out.println("Câu KT số lượng còn lại "+query);
		ResultSet rsKT = db.get(query);
//		String spTen = "";
		int demsp = 0;
		int sodong_kthoa = 0;
		boolean isNK = true;
		try
		{
			if(rsKT!=null)
			{
				while(rsKT.next())
				{
					demsp ++;
					
					if(rsKT.getDouble("SLCONLAI") <= 0){
						isNK = false;
						sodong_kthoa ++;
					}
					
						
				}rsKT.close();
			}
			
		}catch(Exception e){
			e.printStackTrace();
			msg = "Xảy ra lỗi trong quá trình chuyển";
			return msg;
		}
		
			if(!isNK && demsp - sodong_kthoa <= 0)
			{
				msg = "Tất cả sản phẩm đã nhập kho nhà nhập khẩu đủ số lượng, không thể nhập tiếp. Vui lòng kiểm tra lại. ";
				return msg;
			}
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			// Lay TT tong
			query = "SELECT SH.MUAHANG_FK, SH.NPP_FK, SH.NCC_FK, SH.CONGTY_FK, SH.NGUOITAO "
				  + "FROM ERP_SHIPHANG SH "
				  + "WHERE SH.PK_SEQ = "+ shId +" ";
			ResultSet rsTT = db.get(query);
		
			while(rsTT.next())
			{
				
				query = "insert ERP_NHAPKHONHAPKHAU(SHIPHANG_FK, NGAYNHAP, GHICHU, MUAHANG_FK,  NGAYTAO, NGAYSUA, NGUOITAO, NGUOISUA, TRANGTHAI, CONGTY_FK, NPP_FK, NCC_FK) " +
					    "values("+ shId +", '" +this.getDateTime() +"', N'', "+ rsTT.getString("MUAHANG_FK") +", '" + this.getDateTime() +"', '" +this.getDateTime() +"', '" +rsTT.getString("NGUOITAO") + "', '" +rsTT.getString("NGUOITAO") +"', '0', '" + rsTT.getString("CONGTY_FK") + "', "+ rsTT.getString("NPP_FK") +", "+ rsTT.getString("NCC_FK") +")";
				System.out.println(" Câu insert ERP_NHAPKHONHAPKHAU :"+ query);
				
				if (!db.update(query))
				{
					msg = "Khong the tao moi Nhap kho: " + query;
					db.getConnection().rollback();
					return msg;
				}
			
				query = "select IDENT_CURRENT('ERP_NHAPKHONHAPKHAU') as nkId";
				
				ResultSet rs1=  db.get(query);
				
				if(rs1!=null){
					try{
						while(rs1.next()){
							this.id = rs1.getString("nkId");
						}
					}catch(Exception e){
						e.printStackTrace();
					}	
				}
			
			}
			rsTT.close();
								

			// Lay TT tong
			query = "SELECT SP.SANPHAM_FK, SP.DONVI, SP.SOLUONG, 0 AS SONGAYLUUKHO, (select pk_seq from KHO where loai = 7) as KHONHAN, c.thuexuat as vat, c.DONGIA, "
				  + "       SP.SOLUONG -  "
				  +	"       ISNULL((select SUM(CT.SOLUONGNHAN) "
				  + "              from ERP_NHAPKHONHAPKHAU NK inner join ERP_NHAPKHONHAPKHAU_SANPHAM CT ON NK.PK_SEQ = CT.NHAPKHO_FK "
				  + "              where SP.SANPHAM_FK = CT.SANPHAM_FK AND NK.TRANGTHAI != 2 AND NK.SHIPHANG_FK =  SP.SHIPHANG_FK   "
				  + "              group by CT.SANPHAM_FK ),0) SOLUONGNHAN "
				  + "FROM ERP_SHIPHANG_SANPHAM SP inner join erp_shiphang a on a.pk_seq = sp.shiphang_fk "
				  + "inner join erp_muahang b on a.muahang_fk = b.pk_seq inner join erp_muahang_sp c on b.pk_seq = c.muahang_fk and sp.sanpham_fk = c.sanpham_fk "
				  + "WHERE SP.SHIPHANG_FK = "+ shId +" "
				  + "      AND SP.SOLUONG -  "
				  +	"       ISNULL((select SUM(CT.SOLUONGNHAN) "
				  + "              from ERP_NHAPKHONHAPKHAU NK inner join ERP_NHAPKHONHAPKHAU_SANPHAM CT ON NK.PK_SEQ = CT.NHAPKHO_FK "
				  + "              where SP.SANPHAM_FK = CT.SANPHAM_FK AND NK.TRANGTHAI != 2 AND NK.SHIPHANG_FK =  SP.SHIPHANG_FK   "
				  + "              group by CT.SANPHAM_FK ),0) > 0 ";
			System.out.println("Câu lấy TT SP "+query);
			ResultSet rsCT = db.get(query);
			
			while(rsCT.next())
			{
				query = "insert ERP_NHAPKHONHAPKHAU_SANPHAM(NHAPKHO_FK, SANPHAM_FK, DONVI,SOLUONG, SOLUONGNHAN, KHONHAN_FK, NGAYNHAP, SONGAYLUUKHO, vat, dongia) " +
						   "values('" + this.id + "', " + rsCT.getString("SANPHAM_FK") + ",  N'" + rsCT.getString("DONVI") + "','" + rsCT.getString("SOLUONG") + "'  " +
							", " + rsCT.getString("SOLUONGNHAN") + ", "+ rsCT.getString("KHONHAN") +", '', "+ rsCT.getString("SONGAYLUUKHO")  +", "+ rsCT.getString("vat")  +", "+ rsCT.getDouble("dongia")  +")";
				System.out.println("Cau insert CT "+query);
				
				if (!db.update(query))
				{
					msg = "Khong the tao moi ERP_NHAPKHONHAPKHAU_SANPHAM: " + query;
					db.getConnection().rollback();
					return msg;
				}					

			}
			rsCT.close();
					
														
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return msg;
		}
		catch (Exception e)
		{
			this.msg="Lỗi tạo ship hàng: "+e.getMessage();
			e.printStackTrace();
			db.update("rollback");
			 
			return msg;
		}
				
	}

}
