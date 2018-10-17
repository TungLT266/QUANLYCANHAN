package geso.traphaco.erp.beans.shiphang.imp;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.shiphang.IErpShiphang;
import geso.traphaco.erp.beans.shiphang.ISanpham;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ErpShiphang implements IErpShiphang
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
	
	public ErpShiphang()
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
	
	public ErpShiphang(String id)
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
	{System.out.println("congty "+this.congtyId);
		String sql="select pk_seq, ten from ERP_DONVITHUCHIEN where trangthai = '1'  ";		
				//" and congty_fk = '" + this.congtyId + "' and pk_seq in " + this.util.quyen_donvithuchien(this.userId);;
		this.dvthRs = db.get(sql);
		
		if(this.dvthId.trim().length() > 0)
		{
			sql = "select PK_SEQ, (SOPO + ' - ' +  NGAYMUA) TEN  from ERP_MUAHANG  where TRANGTHAI in (1, 2) AND LOAI = 0 and DONVITHUCHIEN_FK = "+ this.dvthId +" "        
				  + " and PK_SEQ not in (select muahang_fk from ERP_SHIPHANG where TRANGTHAI <> 2) "
				  + " union";
			sql += " select PK_SEQ, (SOPO + ' - ' +  NGAYMUA) TEN "
			    + " from ERP_MUAHANG "
			    + " where TRANGTHAI in (1, 2) AND LOAI = 0 and DONVITHUCHIEN_FK = "+ this.dvthId +" "
			    + "       and PK_SEQ in (select data.muahang_fk from ( select a.MUAHANG_FK, b.SANPHAM_FK, sum(b.SOLUONG) as soluong, c.soluong as SOLUONGDAT " 
			    + " from ERP_SHIPHANG a inner join ERP_SHIPHANG_SANPHAM b on a.PK_SEQ = b.SHIPHANG_FK "
			    + " inner join ERP_MUAHANG_SP c on b.sanpham_fk = c.SANPHAM_FK and a.MUAHANG_FK = c.MUAHANG_FK " 
			    + " where trangthai != '2' "
			    + " group by a.MUAHANG_FK, b.SANPHAM_FK, c.SOLUONG) as data where data.soluong < data.SOLUONGDAT)";
			
			if(this.id.trim().length() > 0){
				sql += 	" union " +
						" select PK_SEQ, (SOPO + ' - ' +  NGAYMUA) TEN  from ERP_MUAHANG  where TRANGTHAI = 1 AND LOAI = 0 and DONVITHUCHIEN_FK = "+ this.dvthId +" "+  
						" and PK_SEQ in (select muahang_fk from ERP_SHIPHANG where PK_SEQ = "+this.id+")";
			}
			
			System.out.println("ds po "+sql);
			this.poRs = db.get(sql);
			
		}
		
		sql = "select PK_SEQ, ma + ', ' + TEN as ten  from ERP_NHACUNGCAP where TRANGTHAI = 1 and duyet = '1'  and LOAINCC = '100002' and congty_fk = "+ this.congtyId +"  ";
		System.out.println("ncc "+sql);
		this.nccRs = db.get(sql);
		
		if(this.id.trim().length() <= 0 && this.poId.trim().length() > 0)
		{
			String	query1 = "SELECT mh.PK_SEQ as MUAHANGID, sp.PK_SEQ as SPID, sp.MA as MASP, sp.TEN as TENSP, mhsp.DONVI, mhsp.SOLUONG, mhsp.DONGIA, "
						   + " isnull(mhsp.dungsai, 0) as dungsai, MHSP.THANHTIEN, '' as NGAYNHAN  "
					       + "FROM ERP_MUAHANG mh INNER JOIN ERP_MUAHANG_SP mhsp on mh.PK_SEQ = mhsp.MUAHANG_FK"
					       + "     INNER JOIN ERP_SANPHAM sp on mhsp.SANPHAM_FK = sp.PK_SEQ  "
					       + "WHERE mh.PK_SEQ = "+ this.poId +" ";
					 				
				System.out.println("ds sanpham :"+ query1);
				
				ResultSet rs = db.get(query1);
				List<ISanpham> spList = new ArrayList<ISanpham>();
				
				NumberFormat formatter = new DecimalFormat("#,###,###.###");
				
				if(rs!=null){
					ISanpham sp = null;
					try{
						while(rs.next()){
							double soluongdaship = 0;
							sp= new Sanpham();
					
							sp.setMuahang_fk(rs.getString("MUAHANGID"));
							sp.setMa(rs.getString("MASP"));
							sp.setId(rs.getString("SPID"));
							sp.setDiengiai(rs.getString("TENSP"));
							sp.setDungsai(formatter.format(rs.getDouble("dungsai")));
							sp.setSoluongdat(formatter.format(rs.getDouble("SOLUONG")));
							sp.setDvdl(rs.getString("DONVI"));
							sp.setNgaynhandukien(rs.getString("NGAYNHAN"));						
							sp.setDongia(formatter.format(rs.getDouble("DONGIA")));
							sp.setthanhtien(formatter.format(rs.getDouble("THANHTIEN")));
							
							String query = "select sum(b.soluong) as soluong from ERP_SHIPHANG a inner join ERP_SHIPHANG_SANPHAM b on a.PK_SEQ = b.SHIPHANG_FK "
									+ "where a.MUAHANG_FK = " + this.poId + " and b.sanpham_fk = "+sp.getId();
							System.out.println("lay sl da ship "+query);
							ResultSet rsship = db.get(query);
							if(rsship != null)
							{
								while(rsship.next())
								{
									soluongdaship += rsship.getDouble("soluong");
								}
							}
							double soluongcl = rs.getDouble("SOLUONG") - soluongdaship;
							sp.setSoluongdaship(formatter.format(soluongdaship));
							sp.setSoluongship(formatter.format(soluongcl));
							
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
		String query =  " SELECT a.PK_SEQ as shId, a.trangthai, a.NGAYCHUNGTU, isnull(a.GHICHU,'') GHICHU, b.pk_seq as dvthId, b.TEN as dvthTen,   \n" +
						" 	     a.NCC_FK, a.muahang_fk as muahang_fk, c.sopo as sopo  \n" +
						" FROM ERP_SHIPHANG a inner join ERP_DONVITHUCHIEN b on a.DONVITHUCHIEN_FK = b.PK_SEQ \n" +
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
					this.ngaychungtu = rs.getString("NGAYCHUNGTU");
					this.trangthai = rs.getString("TRANGTHAI");
					this.dvthId = rs.getString("dvthId");
					this.poId = rs.getString("muahang_fk")== null? "" : rs.getString("muahang_fk");

					this.trangthai = rs.getString("trangthai");
					this.nccId = rs.getString("NCC_FK") == null ? "" : rs.getString("NCC_FK");
					this.ghichu = rs.getString("GHICHU");
					
				}
				rs.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
				
		this.initSanPham();
		
		this.createRs();
	}
	
	private void initSanPham()
	{
		
	
		NumberFormat formatter = new DecimalFormat("#,###,###.##"); 
		
		String query = 			" SELECT  shsp.SANPHAM_FK spId, isnull(sp.MA, isnull(sp.ma, ''))  spMa, isnull(sp.Ten1, sp.Ten)   spTen, " +
								" shsp.NGAYNHAN, shsp.DONGIA, mhsp.SOLUONG as soluongdat, shsp.SOLUONG, shsp.THANHTIEN, isnull(shsp.DonVi, 'NA') as donvi, isnull(shsp.dungsai, 0) dungsai    " +
							    " FROM ERP_SHIPHANG_SANPHAM shsp inner join erp_shiphang sh on shsp.shiphang_fk = sh.pk_seq    " +
							  	" INNER join ERP_SANPHAM sp on shsp.SANPHAM_FK = sp.PK_SEQ inner join ERP_MUAHANG_SP mhsp on shsp.sanpham_fk = mhsp.SANPHAM_FK "+
							  	" and sh.MUAHANG_FK = mhsp.MUAHANG_FK   " +							  
							    " WHERE shsp.SHIPHANG_FK = '" + this.id + "'";
		
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
					String ngaynhandk = rsSp.getString("NGAYNHAN");
					
					String soluongdat = formatter.format(rsSp.getDouble("SOLUONGdat"));
					String dungsai = formatter.format(rsSp.getDouble("dungsai"));
					String dvdl = rsSp.getString("DonVi");
					String dongia = formatter.format(rsSp.getDouble("DONGIA"));
					
					String thanhtien = formatter.format(rsSp.getDouble("THANHTIEN"));
					
					sanpham = new Sanpham(spId, spMa, spTen, "", "", ngaynhandk, soluongdat, dvdl);
					
					query = "select b.soluong from ERP_SHIPHANG a inner join ERP_SHIPHANG_SANPHAM b on a.PK_SEQ = b.SHIPHANG_FK where a.trangthai = '1' "
							+ "and a.MUAHANG_FK = " + this.poId + " and b.sanpham_fk = "+spId;
					double soluongdaship = 0;
					ResultSet rsship = db.get(query);
					if(rsship != null)
					{
						while(rsship.next())
						{
							soluongdaship += rsship.getDouble("soluong");
						}
					}
					
					sanpham.setDungsai(dungsai);
					sanpham.setSoluongdaship(formatter.format(soluongdaship));
					sanpham.setSoluongship(formatter.format(rsSp.getDouble("SOLUONG")));
					
					sanpham.setDongia(dongia);
					sanpham.setthanhtien(thanhtien);
									
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
				e.printStackTrace();
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
	
	
	public boolean createShipHang()
	{
		String ngaytao = this.getDateTime();
		
		if(this.ngaychungtu.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập ngày chứng từ";
			return false;
		}
		
		if(this.nccId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn nhà cung cấp";
			return false;
		}
		
		if (this.spList.size() <= 0)
		{
			this.msg = "Không có sản phẩm nào để ship hàng, vui lòng kiểm tra lại";
			return false;
		}else
		{
			for (int i = 0; i < spList.size(); i++)
			{
				ISanpham sp = spList.get(i);

				if (sp.getNgaynhandukien().trim().length() <= 0) 
				{
					this.msg = "Vui lòng nhập ngày nhận cho sản phẩm " + sp.getMa() + " - " + sp.getDiengiai() ;
					return false;
				}
			}
		}
		
		String query = "";
			
		try
		{
			db.getConnection().setAutoCommit(false);

		
			String nppId= util.getIdNhapp(this.userId);
					
								
				query = "insert ERP_SHIPHANG(NGAYCHUNGTU,GHICHU, DONVITHUCHIEN_FK, MUAHANG_FK,  NGAYTAO, NGAYSUA, NGUOITAO, NGUOISUA, TRANGTHAI, CONGTY_FK, NPP_FK, NCC_FK) " +
					    "values('" +this.ngaychungtu +"', N'" +this.ghichu +"', '" +this.dvthId +"', "+ this.poId +", '" + ngaytao +"', '" +ngaytao +"', '" +this.userId + "', '" +this.userId +"', '0', '" + this.congtyId + "', "+ nppId +", "+ this.nccId +")";
				System.out.println(" Câu insert ERP_SHIPHANG :"+ query);
				
				if (!db.update(query))
				{
					this.msg = "Khong the tao moi Ship hang: " + query;
					db.getConnection().rollback();
					return false;
				}
			
			String shCurrent = "";
			query = "select IDENT_CURRENT('ERP_SHIPHANG') as shId";
			
			ResultSet rs1=  db.get(query);
			
			if(rs1!=null){
				try{
					while(rs1.next()){
						this.id = rs1.getString("shId");
					}
				}catch(Exception e){
					e.printStackTrace();
				}	
			}
			
			ResultSet rsNh = db.get(query);
			if (rsNh.next())
			{
				shCurrent = rsNh.getString("shId");
				rsNh.close();
			}
			
			if (this.spList.size() > 0)
			{
				for (int i = 0; i < spList.size(); i++)
				{
					ISanpham sp = spList.get(i);
					
					double thanhtien = Double.parseDouble(sp.getSoluongdat().replaceAll(",", ""))* Double.parseDouble(sp.getDongia().replaceAll(",", ""));
					double soluongdat = Double.parseDouble(sp.getSoluongdat().replaceAll(",", ""));
					double soluongdaship = Double.parseDouble(sp.getSoluongdaship().replaceAll(",", ""));
					double soluongship = Double.parseDouble(sp.getSoluongship().replaceAll(",", ""));
					double dungsai = Double.parseDouble(sp.getDungsai().replaceAll(",", ""));
					
					if (sp.getNgaynhandukien() != "") // chi luu nhung san pham có chọn ngày nhận
					{				
						if((soluongdat + soluongdat * dungsai / 100) < (soluongdaship + soluongship))
						{
							this.msg = "Số lượng ship không được vượt quá số lượng đặt + dung sai ";
							System.out.println(this.msg);
							db.getConnection().rollback();
							return false;
						}
						query = "insert ERP_SHIPHANG_SANPHAM(SHIPHANG_FK, SANPHAM_FK, DIENGIAI, DONVI, SOLUONGDAT, SOLUONG, DONGIA, THANHTIEN, NGAYNHAN, dungsai ) " +
							   "values('" + shCurrent + "', " + sp.getId() + ", N'" + sp.getDiengiai() + "', N'" + sp.getDvdl() + "','" + sp.getSoluongdat().replaceAll(",", "") + "','" + sp.getSoluongship().replaceAll(",", "") + "'  " +
								", " + Double.parseDouble(sp.getDongia().replaceAll(",", "")) + ", "+ thanhtien +", '" + sp.getNgaynhandukien() + "', "+Double.parseDouble(sp.getDungsai().replaceAll(",", ""))+")";

					
						if (!db.update(query))
						{
							this.msg = "Khong the tao moi ERP_SHIPHANG_SANPHAM: " + query;
							System.out.println(this.msg);
							db.getConnection().rollback();
							return false;
						}					

					}
				}
			}
			
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
	
	
	public boolean updateShipHang()
	{
		if(this.ngaychungtu.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập ngày chứng từ";
			return false;
		}
		
		if(this.nccId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn nhà cung cấp";
			return false;
		}
		
		if (this.spList.size() <= 0)
		{
			this.msg = "Không có sản phẩm nào để ship hàng, vui lòng kiểm tra lại";
			return false;
		}else
		{
			for (int i = 0; i < spList.size(); i++)
			{
				ISanpham sp = spList.get(i);

				if (sp.getNgaynhandukien().trim().length() <= 0) 
				{
					this.msg = "Vui lòng nhập ngày nhận cho sản phẩm " + sp.getMa() + " - " + sp.getDiengiai() ;
					return false;
				}
			}
		}
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query = ""; 
//			String muahang_fk = "null";
//			String trahang_fk = "null";
			
		
			String nppId= util.getIdNhapp(this.userId);

				query = "update ERP_SHIPHANG set NGAYCHUNGTU = '" + this.ngaychungtu + "',GHICHU = N'" + this.ghichu + "', " + "DONVITHUCHIEN_FK = '" + this.dvthId + "'," +
				"   MUAHANG_FK = " +this.poId + ", NGAYSUA = '" + this.getDateTime() + "', " + "NGUOISUA = '" + this.userId + "', NCC_FK = " + this.nccId + ", CONGTY_FK = "+ this.congtyId +", NPP_FK = "+ nppId +" " +
				"   where pk_seq = '" + this.id + "'";
			
			  System.out.println("Query update: " + query);
			
			if (!db.update(query))
			{
				this.msg = "Khong the tao moi Ship hang: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_SHIPHANG_SANPHAM where shiphang_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat ERP_SHIPHANG_SANPHAM: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			
			
			if (this.spList.size() > 0)
			{
				for (int i = 0; i < spList.size(); i++)
				{
					ISanpham sp = spList.get(i);
					
					double thanhtien = Double.parseDouble(sp.getSoluongdat().replaceAll(",", ""))* Double.parseDouble(sp.getDongia().replaceAll(",", ""));
					double soluongdat = Double.parseDouble(sp.getSoluongdat().replaceAll(",", ""));
					double soluongdaship = Double.parseDouble(sp.getSoluongdaship().replaceAll(",", ""));
					double soluongship = Double.parseDouble(sp.getSoluongship().replaceAll(",", ""));
					double dungsai = Double.parseDouble(sp.getDungsai().replaceAll(",", ""));
					
					if (sp.getNgaynhandukien() != "") // chi luu nhung san pham có chọn ngày nhận
					{	
						if((soluongdat + soluongdat * dungsai / 100) < (soluongdaship + soluongship))
						{
							this.msg = "Số lượng ship không được vượt quá số lượng đặt + dung sai ";
							System.out.println(this.msg);
							db.getConnection().rollback();
							return false;
						}
						query = "insert ERP_SHIPHANG_SANPHAM(SHIPHANG_FK, SANPHAM_FK, DIENGIAI, DONVI, SOLUONGDAT,SOLUONG, DONGIA, THANHTIEN, NGAYNHAN, dungsai ) " +
							   "values('" + this.id + "', " + sp.getId() + ", N'" + sp.getDiengiai() + "', N'" + sp.getDvdl() + "','" + sp.getSoluongdat().replaceAll(",", "") + "','" + sp.getSoluongship().replaceAll(",", "") + "'  " +
								", " + Double.parseDouble(sp.getDongia().replaceAll(",", "")) + ", "+ thanhtien +", '" + sp.getNgaynhandukien() + "', "+Double.parseDouble(sp.getDungsai().replaceAll(",", ""))+")";
						System.out.println("[ERP_SHIPHANG_SANPHAM] "+query);
						if (!db.update(query))
						{
							this.msg = "Khong the tao moi ERP_NHANHANG_SANPHAM: " + query;
							System.out.println(this.msg);
							db.getConnection().rollback();
							return false;
						}
					}
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;
		}
		catch (Exception e)
		{
			this.msg="Lỗi không thể cập nhật nhận hàng: "+e.getMessage();
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
			er.printStackTrace();
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

}
