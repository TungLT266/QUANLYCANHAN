package geso.traphaco.erp.beans.timnhacc.imp;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.timnhacc.INhacungcap;
import geso.traphaco.erp.beans.timnhacc.ISanpham;
import geso.traphaco.erp.beans.timnhacc.ITimnhacc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Timnhacc implements ITimnhacc{
	
	String congtyId;
	String nppId;
	String userId;
	String id;
	String nccId;
	ResultSet nccRs;
	String spId;
	List<ISanpham> spList;
	String trangthai;
	String diengiai;
	String dnmhId;
	ResultSet dnmhRs;
	String ngaytao;
	String nguoitao;
	String ngaysua;
	String nguoisua;
	dbutils db;
	Utility util;
	String active;
	String msg;
	String loaihanghoa;
	String hinhthuc;
	String nccids;
	String nguongochh;
	
	public Timnhacc(String[] param)
	{
		this.db = new dbutils();
		this.id = param[0];
		this.dnmhId = param[1];
		this.ngaytao = param[2];
		this.nguoitao = param[3];
		this.ngaysua = param[4];
		this.nguoisua = param[5];
		this.diengiai = param[6];
		this.trangthai = param[7];
		this.msg = "";
		this.active = "0";
		this.loaihanghoa = "0";
		this.hinhthuc = "0";
		this.nccids = "";
		this.nguongochh = "";
		this.util=new Utility();
	}
	public Timnhacc(String id) {
		this.congtyId = "";
		this.nppId = "";
		this.userId = "";
		this.id = id;
		this.nccId = "";
		this.dnmhId = "";
		this.trangthai = "0";
		this.diengiai = "";
		this.ngaytao = "";
		this.nguoitao = "";
		this.ngaysua = "";
		this.nguoisua = "";
		this.msg = "";
		this.active = "0";
		this.loaihanghoa = "0";
		this.hinhthuc = "0";
		this.nccids = "";
		this.nguongochh = "";
		this.util=new Utility();
		this.db = new dbutils();
		/*if(id.length() > 0)
			this.init();*/
	}
	
	@Override
	public String getCongtyId() {
		
		return this.congtyId;
	}
	@Override
	public void setCongtyId(String congtyId) {
		
		this.congtyId = congtyId;
	}
	@Override
	public String getUserId() {
		
		return this.userId;
	}
	@Override
	public void setUserId(String userId) {
		
		this.userId = userId;
	}
	@Override
	public String getId() {
		
		return this.id;
	}
	@Override
	public void setId(String id) {
		
		this.id = id;
	}
	
	@Override
	public String getTrangthai() {
		
		return this.trangthai;
	}
	@Override
	public void setTrangthai(String trangthai) {
		
		this.trangthai = trangthai;
	}
	@Override
	public String getDiengiai() {
		
		return this.diengiai;
	}
	@Override
	public void setDiengiai(String diengiai) {
		
		this.diengiai = diengiai;
	}
	
	public void createRs() {
		
		String query = "select pk_seq, sodenghi from erp_denghimuahang where  trangthai in (0, 1) and istruongbpduyet = 1 and PK_SEQ not in (select DENGHIMUA_FK from ERP_TIMNCC  ";
		if(this.id.trim().length() > 0) {
			query += " where pk_seq != "+ this.id +" ";
		}
		query += " ) order by pk_Seq desc ";
		System.out.println("[De nghi mua hang RS] " + query);
		this.dnmhRs = db.get(query);
		
		if(dnmhId.trim().length() > 0)
		{
			System.out.println("vao day roi !!!!!!!");
			
			try
			{
				query = "select loaihanghoa_fk, nguongochh from erp_denghimuahang where pk_seq = '"+this.dnmhId+"'";
				ResultSet rs = db.get(query);
				if(rs != null)
				{
					while(rs.next())
					{
						this.loaihanghoa = rs.getString("loaihanghoa_fk");
						this.nguongochh = rs.getString("nguongochh");
					}
				}
				String tk = "100098";
				if(this.nguongochh.equals("TN"))
				{
					tk = "100097";
				}
				query = "select pk_seq, ma, ten from erp_nhacungcap where trangthai = 1 and duyet = '1' and TAIKHOAN_FK = '"+tk+"' and congty_fk = "+ this.congtyId +" ";
				
				System.out.println("nccRs " + query);
				this.nccRs = db.get(query);
				
				
				if(this.loaihanghoa.trim().equals("0"))
				{
					query = "select a.pk_seq, a.ma, a.ten , b.diengiai as diengiai_dnmhsp   from erp_sanpham a, erp_denghimuahang_sp b where a.pk_seq = b.sanpham_fk and b.denghi_fk = '" + this.dnmhId +"'";
				}
				else if(this.loaihanghoa.trim().equals("1"))
				{
					query = "select a.pk_seq, a.ma, a.ten as ten,  b.diengiai as diengiai_dnmhsp  from erp_masclon a, erp_denghimuahang_sp b where a.pk_seq = b.taisan_fk and b.denghi_fk = '" + this.dnmhId +"'";
				}
				else if(this.loaihanghoa.trim().equals("2"))
				{
					System.out.println("vao chi phi: " + query);
					query = "select a.pk_seq, a.ten as ma, a.diengiai as ten, b.diengiai as diengiai_dnmhsp from erp_nhomchiphi a, erp_denghimuahang_sp b where a.pk_seq = b.chiphi_fk and b.denghi_fk = '" + this.dnmhId +"'";
				}
				else
				{
					query = "select a.pk_seq, a.ma, a.diengiai as ten, b.diengiai as diengiai_dnmhsp  from ERP_CONGCUDUNGCU a, erp_denghimuahang_sp b where a.pk_seq = b.ccdc_fk and b.denghi_fk = '" + this.dnmhId +"'";
				}
					
				System.out.println("erp_denghimuahang_sp: " + query);
				rs = db.get(query);
				if(rs != null)
				{
					List<ISanpham> lstSP = new ArrayList<ISanpham>();
				
					while (rs.next())
					{
						ISanpham sp = new Sanpham();
						sp.setId(rs.getString("pk_seq"));
						sp.setMasanpham(rs.getString("ma"));
						sp.setTensanpham(rs.getString("ten"));
						sp.setDiengiai(rs.getString("diengiai_dnmhsp"));
						
						if(this.loaihanghoa.trim().equals("0"))
						{
							query = " select a.PK_SEQ, a.MA, a.TEN, a.TEN_NGUOILIENHE, a.DIACHI, a.DIENTHOAI, (select REPLACE((sELECT distinct cast ( c.DONGIA as VARCHAR(100)) as [data()] FROM ERP_MUAHANG b inner join ERP_MUAHANG_SP c on b.PK_SEQ = c.MUAHANG_FK and c.SANPHAM_FK = '"+sp.getId()+"' where b.TRANGTHAI <> 3 and b.NHACUNGCAP_FK = a.PK_SEQ FOR XML PATH('') ) ,' ',', ')) as dongia, "
									+ " (select isnull(sum(c.SOLUONG), 0) as TONGLUONG from ERP_MUAHANG b inner join ERP_MUAHANG_SP c on b.PK_SEQ = c.MUAHANG_FK and c.SANPHAM_FK = '"+sp.getId()+"' where b.TRANGTHAI <> 3 and b.NHACUNGCAP_FK = a.PK_SEQ) as tongluong "+ 
									" from ERP_NHACUNGCAP a left join (select top 1 * from ERP_DANHGIANCC where SP_FK = '"+sp.getId()+"' order by PK_SEQ desc) as d on a.PK_SEQ = d.NCC_FK "+
									" where a.trangthai = '1' and a.congty_fk = "+ this.congtyId +" and a.TAIKHOAN_FK = '"+tk+"' ";
						}
						else if(this.loaihanghoa.trim().equals("1"))
						{
							query = " select a.PK_SEQ, a.MA, a.TEN, a.TEN_NGUOILIENHE, a.DIACHI, a.DIENTHOAI, (select REPLACE((sELECT distinct cast ( c.DONGIA as VARCHAR(100)) as [data()] FROM ERP_MUAHANG b inner join ERP_MUAHANG_SP c on b.PK_SEQ = c.MUAHANG_FK and c.TAISAN_FK = '"+sp.getId()+"' where b.TRANGTHAI <> 3 and b.NHACUNGCAP_FK = a.PK_SEQ FOR XML PATH('') ) ,' ',', ')) as dongia, "
									+ " (select isnull(sum(c.SOLUONG), 0) as TONGLUONG from ERP_MUAHANG b inner join ERP_MUAHANG_SP c on b.PK_SEQ = c.MUAHANG_FK and c.TAISAN_FK = '"+sp.getId()+"' where b.TRANGTHAI <> 3 and b.NHACUNGCAP_FK = a.PK_SEQ) as tongluong "+
									" from ERP_NHACUNGCAP a " /*left join (select top 1 * from ERP_DANHGIANCC where TAISAN_FK = '"+sp.getId()+"' order by PK_SEQ desc) as d on a.PK_SEQ = d.NCC_FK "*/+
									" where a.trangthai = '1' and a.congty_fk = "+ this.congtyId +" and a.TAIKHOAN_FK = '"+tk+"' ";
						}
						else if(this.loaihanghoa.trim().equals("2"))
						{
							query = " select a.PK_SEQ, a.MA, a.TEN, a.TEN_NGUOILIENHE, a.DIACHI, a.DIENTHOAI, (select REPLACE((sELECT distinct cast ( c.DONGIA as VARCHAR(100)) as [data()] FROM ERP_MUAHANG b inner join ERP_MUAHANG_SP c on b.PK_SEQ = c.MUAHANG_FK and c.CHIPHI_FK = '"+sp.getId()+"' where b.TRANGTHAI <> 3 and b.NHACUNGCAP_FK = a.PK_SEQ FOR XML PATH('') ) ,' ',', ')) as dongia, "
									+ " (select isnull(sum(c.SOLUONG), 0) as TONGLUONG from ERP_MUAHANG b inner join ERP_MUAHANG_SP c on b.PK_SEQ = c.MUAHANG_FK and c.CHIPHI_FK = '"+sp.getId()+"' where b.TRANGTHAI <> 3 and b.NHACUNGCAP_FK = a.PK_SEQ) as tongluong "+
									" from ERP_NHACUNGCAP a " /*left join (select top 1 * from ERP_DANHGIANCC where CHIPHI_FK = '"+sp.getId()+"' order by PK_SEQ desc) as d on a.PK_SEQ = d.NCC_FK "*/+
									" where a.trangthai = '1' and a.congty_fk = "+ this.congtyId +" and a.TAIKHOAN_FK = '"+tk+"' ";
						}
						else
						{
							query = " select a.PK_SEQ, a.MA, a.TEN, a.TEN_NGUOILIENHE, a.DIACHI, a.DIENTHOAI, (select REPLACE((sELECT distinct cast ( c.DONGIA as VARCHAR(100)) as [data()] FROM ERP_MUAHANG b inner join ERP_MUAHANG_SP c on b.PK_SEQ = c.MUAHANG_FK and c.CCDC_FK = '"+sp.getId()+"' where b.TRANGTHAI <> 3 and b.NHACUNGCAP_FK = a.PK_SEQ FOR XML PATH('') ) ,' ',', ')) as dongia, "
									+ " (select isnull(sum(c.SOLUONG), 0) as TONGLUONG from ERP_MUAHANG b inner join ERP_MUAHANG_SP c on b.PK_SEQ = c.MUAHANG_FK and c.CCDC_FK = '"+sp.getId()+"' where b.TRANGTHAI <> 3 and b.NHACUNGCAP_FK = a.PK_SEQ) as tongluong "+ 
									" from ERP_NHACUNGCAP a " /*left join (select top 1 * from ERP_DANHGIANCC where CCDC_FK = '"+sp.getId()+"' order by PK_SEQ desc) as d on a.PK_SEQ = d.NCC_FK "*/+
									" where a.trangthai = '1' and a.congty_fk = "+ this.congtyId +" and a.TAIKHOAN_FK = '"+tk+"' ";
						}
						if(this.id.trim().length() > 0)
						{
							System.out.println("cong ty " + this.congtyId);
							if(this.loaihanghoa.trim().equals("0"))
							{
								query = " select a.PK_SEQ, a.MA, a.TEN, a.TEN_NGUOILIENHE, a.DIACHI, a.DIENTHOAI, (select REPLACE((sELECT distinct cast ( c.DONGIA as VARCHAR(100)) as [data()] FROM ERP_MUAHANG b inner join ERP_MUAHANG_SP c on b.PK_SEQ = c.MUAHANG_FK and c.SANPHAM_FK = '"+sp.getId()+"' where b.TRANGTHAI <> 3 and b.NHACUNGCAP_FK = a.PK_SEQ FOR XML PATH('') ) ,' ',', ')) as dongia, "
										+ " (select isnull(sum(c.SOLUONG), 0) as TONGLUONG from ERP_MUAHANG b inner join ERP_MUAHANG_SP c on b.PK_SEQ = c.MUAHANG_FK and c.SANPHAM_FK = '"+sp.getId()+"' where b.TRANGTHAI <> 3 and b.NHACUNGCAP_FK = a.PK_SEQ) as tongluong, e.ncc_fk  "+ 
										" from ERP_NHACUNGCAP a left join (select top 1 * from ERP_DANHGIANCC where SP_FK = '"+sp.getId()+"' order by PK_SEQ desc) as d on a.PK_SEQ = d.NCC_FK "+ 
										" left join ERP_TIMNCC_NCC e on a.PK_SEQ = e.NCC_FK and e.sp_fk = '"+sp.getId()+"'";
							}
							else if(this.loaihanghoa.trim().equals("1"))
							{
								query = " select a.PK_SEQ, a.MA, a.TEN, a.TEN_NGUOILIENHE, a.DIACHI, a.DIENTHOAI, (select REPLACE((sELECT distinct cast ( c.DONGIA as VARCHAR(100)) as [data()] FROM ERP_MUAHANG b inner join ERP_MUAHANG_SP c on b.PK_SEQ = c.MUAHANG_FK and c.TAISAN_FK = '"+sp.getId()+"' where b.TRANGTHAI <> 3 and b.NHACUNGCAP_FK = a.PK_SEQ FOR XML PATH('') ) ,' ',', ')) as dongia, "
										+ " (select isnull(sum(c.SOLUONG), 0) as TONGLUONG from ERP_MUAHANG b inner join ERP_MUAHANG_SP c on b.PK_SEQ = c.MUAHANG_FK and c.TAISAN_FK = '"+sp.getId()+"' where b.TRANGTHAI <> 3 and b.NHACUNGCAP_FK = a.PK_SEQ) as tongluong, e.ncc_fk "+ 
										" from ERP_NHACUNGCAP a "/*left join (select top 1 * from ERP_DANHGIANCC where TAISAN_FK = '"+sp.getId()+"' order by PK_SEQ desc) as d on a.PK_SEQ = d.NCC_FK "*/+ 
										" left join ERP_TIMNCC_NCC e on a.PK_SEQ = e.NCC_FK and e.TAISAN_FK = '"+sp.getId()+"'";
							}
							else if(this.loaihanghoa.trim().equals("2"))
							{
								query = " select a.PK_SEQ, a.MA, a.TEN, a.TEN_NGUOILIENHE, a.DIACHI, a.DIENTHOAI, (select REPLACE((sELECT distinct cast ( c.DONGIA as VARCHAR(100)) as [data()] FROM ERP_MUAHANG b inner join ERP_MUAHANG_SP c on b.PK_SEQ = c.MUAHANG_FK and c.CHIPHI_FK = '"+sp.getId()+"' where b.TRANGTHAI <> 3 and b.NHACUNGCAP_FK = a.PK_SEQ FOR XML PATH('') ) ,' ',', ')) as dongia, "
										+ " (select isnull(sum(c.SOLUONG), 0) as TONGLUONG from ERP_MUAHANG b inner join ERP_MUAHANG_SP c on b.PK_SEQ = c.MUAHANG_FK and c.CHIPHI_FK = '"+sp.getId()+"' where b.TRANGTHAI <> 3 and b.NHACUNGCAP_FK = a.PK_SEQ) as tongluong, e.ncc_fk "+ 
										" from ERP_NHACUNGCAP a "/*left join (select top 1 * from ERP_DANHGIANCC where CHIPHI_FK = '"+sp.getId()+"' order by PK_SEQ desc) as d on a.PK_SEQ = d.NCC_FK "*/+ 
										" left join ERP_TIMNCC_NCC e on a.PK_SEQ = e.NCC_FK and e.CHIPHI_FK = '"+sp.getId()+"'";
							}
							else
							{
								query = " select a.PK_SEQ, a.MA, a.TEN, a.TEN_NGUOILIENHE, a.DIACHI, a.DIENTHOAI, (select REPLACE((sELECT distinct cast ( c.DONGIA as VARCHAR(100)) as [data()] FROM ERP_MUAHANG b inner join ERP_MUAHANG_SP c on b.PK_SEQ = c.MUAHANG_FK and c.CCDC_FK = '"+sp.getId()+"' where b.TRANGTHAI <> 3 and b.NHACUNGCAP_FK = a.PK_SEQ FOR XML PATH('') ) ,' ',', ')) as dongia, "
										+ " (select isnull(sum(c.SOLUONG), 0) as TONGLUONG from ERP_MUAHANG b inner join ERP_MUAHANG_SP c on b.PK_SEQ = c.MUAHANG_FK and c.CCDC_FK = '"+sp.getId()+"' where b.TRANGTHAI <> 3 and b.NHACUNGCAP_FK = a.PK_SEQ) as tongluong, e.ncc_fk "+ 
										" from ERP_NHACUNGCAP a " /*left join (select top 1 * from ERP_DANHGIANCC where CCDC_FK = '"+sp.getId()+"' order by PK_SEQ desc) as d on a.PK_SEQ = d.NCC_FK "*/+ 
										" left join ERP_TIMNCC_NCC e on a.PK_SEQ = e.NCC_FK and e.CCDC_FK = '"+sp.getId()+"'";
							}
							query += " and e.TIMNCC_FK = '"+ this.id +"' where a.trangthai = '1' and a.congty_fk = "+ this.congtyId +" and a.TAIKHOAN_FK = '"+tk+"' ";
						}
						
						System.out.println(" query tim ncc: "+ query +" \n");
						
						NumberFormat formatter = new DecimalFormat("#,###,###");
						System.out.println("thong tin chi tiet sp ncc " + query);
						ResultSet rsncc = db.get(query);
						
						/*List<INhacungcap> lstncc = new ArrayList<INhacungcap>();
						if(rsncc != null)
						{
							while(rsncc.next())
							{
								INhacungcap ncc = new Nhacungcap();
								ncc.setId(rsncc.getString("pk_seq"));
								ncc.setMa(rsncc.getString("ma")==null?"":rsncc.getString("ma"));
								ncc.setTen(rsncc.getString("ten")==null?"":rsncc.getString("ten"));
								ncc.setNguoilienhe(rsncc.getString("TEN_NGUOILIENHE"));
								ncc.setDiachi(rsncc.getString("diachi"));
								ncc.setDienthoai(rsncc.getString("dienthoai"));
								//ncc.setSopo(rsncc.getString("sopo")==null?"":rsncc.getString("sopo"));
								String dongia = rsncc.getString("dongia")==null?"":rsncc.getString("dongia");
								String[] dgarr = dongia.trim().split(",");
								//System.out.println("dongia "+dongia+", dongia length " + dgarr.length);
								dongia = "";
								for (int i = 0; i < dgarr.length; i++) {
									if(dgarr[i].trim().length() > 0)
									{
										Double dg = Double.parseDouble(dgarr[i]);
										dongia += formatter.format(dg) + ", ";
									}
								}
								if (dongia.length() > 0)
								{
									dongia = dongia.substring(0, dongia.length() - 2);
								}
								ncc.setDongia(dongia);
								ncc.setTongluong(rsncc.getString("tongluong")==null?"":rsncc.getString("tongluong"));
								
								if(this.id.trim().length() > 0)
									ncc.setChon(rsncc.getString("ncc_fk")==null?"":rsncc.getString("ncc_fk"));
								else
									ncc.setChon("");
								lstncc.add(ncc);
							}
						}
						sp.setNhacungcap(lstncc);*/
						
						sp.setRsNcc(rsncc);
						
						System.out.println("SP pk_seq " + sp.getId() + "; ten " + sp.getTensanpham() + "; ncc " + sp.getNhacungcap());
						lstSP.add(sp);
					}
					this.spList = lstSP;
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	public void init() {
		
		String query = "select a.pk_seq, a.diengiai, a.trangthai, a.ngaytao, b.ten as nguoitao, a.ngaysua, c.ten as nguoisua, a.denghimua_fk, a.hinhthuc, a.nguongochh "+
				" from erp_timncc a, nhanvien b, nhanvien c "+
				" where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq and a.pk_seq = '"+ this.id + "'"; 
		System.out.println("Cau init "+query);
		ResultSet rs =  this.db.get(query);
		try
        {
            while(rs.next())
			{
	        	this.id = rs.getString("pk_seq");
	        	this.diengiai = rs.getString("diengiai")==null?"":rs.getString("diengiai");
	        	this.trangthai = rs.getString("trangthai")==null?"0":rs.getString("trangthai");
	        	this.ngaytao = rs.getString("ngaytao");
	        	this.nguoitao = rs.getString("nguoitao");
	        	this.ngaysua = rs.getString("ngaysua");
	        	this.nguoisua = rs.getString("nguoisua");
	        	this.dnmhId = rs.getString("denghimua_fk");
	        	this.hinhthuc = rs.getString("hinhthuc");
	        	this.nguongochh = rs.getString("nguongochh");
	        	System.out.println("de nghi mua hang id " + this.dnmhId);
			}
			rs.close();
       	}
        catch (java.sql.SQLException e){
        	e.printStackTrace();
        }
		
		query = "select distinct ncc_fk from erp_timncc_ncc where timncc_fk = "+this.id;
		rs =  this.db.get(query);
		try
        {
            while(rs.next())
			{
	        	this.nccids += rs.getString("ncc_fk") + ",";
	        	System.out.println("nccids " + this.nccids);
			}
			rs.close();
       	}
        catch (java.sql.SQLException e){
        	e.printStackTrace();
        }
		
		this.createRs();
	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	public boolean createTimNcc()
	{
		try
		{
			this.nppId = util.getIdNhapp(userId);
			if(this.dnmhId.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn đề nghị đặt hàng";
				return false;
			}
			
			db.getConnection().setAutoCommit(false);
			
			String query = " update ERP_DENGHIMUAHANG set trangthai = '1' where pk_seq = "+ this.dnmhId;
			System.out.println("[denghimuahang]: " + query);
			if(!db.update(query))
			{
				this.msg = "Khong the tao moi erp_timncc: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			if(this.nppId == null || this.nppId.trim().length() <= 0) this.nppId = "NULL";
				query = "insert into erp_timncc(trangthai, ngaytao, nguoitao, ngaysua, nguoisua, diengiai, DENGHIMUA_FK, congty_fk, npp_fk, hinhthuc, nguongochh) " +
						"values(0, '" + this.getDateTime() + "', '" + this.userId + "', '" + this.getDateTime() + "', '" + this.userId + "', N'" 
						+ this.diengiai + "', '" + this.dnmhId + "', '"+this.congtyId+"', "+this.nppId+", '"+this.hinhthuc+"', '"+this.nguongochh+"' )";
			
			System.out.println("1.Insert: " + query);
			if(!db.update(query))
			{
				this.msg = "Khong the tao moi erp_timncc: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "select IDENT_CURRENT('erp_timncc') as Id";
			ResultSet rs = db.get(query);
			rs.next();
			this.id = rs.getString("Id");
			rs.close();
			
			if(this.hinhthuc.equals("0") && this.spList != null)
			{
				for (int i = 0; i < this.spList.size(); i++) {
					ISanpham sp = this.spList.get(i);
					List<INhacungcap> lstncc = sp.getNhacungcap(); 
					for (int j = 0; j < lstncc.size(); j++) {
						INhacungcap ncc = lstncc.get(j);
						if(ncc.getChon().trim().equals(ncc.getId().trim()))
						{
							if(this.loaihanghoa.trim().equals("0"))
							{
								query = "insert into erp_timncc_ncc(TIMNCC_FK, NCC_FK, sp_fk, diengiai) " +
									"values('"+ this.id + "', '" + ncc.getId() + "', '" + sp.getId() +"', N'" + sp.getDiengiai() + "' )";
							}
							else if(this.loaihanghoa.trim().equals("1"))
							{
								query = "insert into erp_timncc_ncc(TIMNCC_FK, NCC_FK, taisan_fk, diengiai) " +
										"values('"+ this.id + "', '" + ncc.getId() + "', '" + sp.getId() +"', N'" + sp.getDiengiai() + "' )";
							}
							else if(this.loaihanghoa.trim().equals("2"))
							{
								query = "insert into erp_timncc_ncc(TIMNCC_FK, NCC_FK, chiphi_fk, diengiai) " +
										"values('"+ this.id + "', '" + ncc.getId() + "', '" + sp.getId() +"', N'" + sp.getDiengiai() + "' )";
							}
							else
							{
								query = "insert into erp_timncc_ncc(TIMNCC_FK, NCC_FK, ccdc_fk, diengiai) " +
										"values('"+ this.id + "', '" + ncc.getId() + "', '" + sp.getId() +"', N'" + sp.getDiengiai() + "' )";
							}
							System.out.println("1.Insert: " + query);
							if(!db.update(query))
							{
								this.msg = "Khong the tao moi erp_timncc_ncc: " + query;
								db.getConnection().rollback();
								return false;
							}
						}
					}
				}
			}
			else
			{
				String sql = "";
				if(this.loaihanghoa.trim().equals("0"))
				{
					query = "select sanpham_fk,diengiai  from erp_denghimuahang_sp where denghi_fk = "+this.dnmhId;
					rs = db.get(query);
					while(rs.next())
					{
						sql = "insert into erp_timncc_ncc(TIMNCC_FK, NCC_FK, sp_fk,DIENGIAI) " +
							  "select '"+ this.id + "', pk_seq, "+rs.getString("sanpham_fk")+",N'"+rs.getString("DIENGIAI")+"'  from erp_nhacungcap where pk_seq in ("+this.nccids+")";
						System.out.println("1.Insert: " + sql);
						if(!db.update(sql))
						{
							this.msg = "Khong the tao moi erp_timncc_ncc: " + sql;
							db.getConnection().rollback();
							return false;
						}
					}
					rs.close();
				}
				else if(this.loaihanghoa.trim().equals("1"))
				{
					query = "select taisan_fk,DIENGIAI from erp_denghimuahang_sp where denghi_fk = "+this.dnmhId;
					rs = db.get(query);
					while(rs.next())
					{
						sql = "insert into erp_timncc_ncc(TIMNCC_FK, NCC_FK, taisan_fk,DIENGIAI) " +
							  "select '"+ this.id + "', pk_seq, "+rs.getString("taisan_fk")+",N'"+rs.getString("DIENGIAI")+"' from erp_nhacungcap where pk_seq in ("+this.nccids+")";
						System.out.println("1.Insert: " + sql);
						if(!db.update(sql))
						{
							this.msg = "Khong the tao moi erp_timncc_ncc: " + sql;
							db.getConnection().rollback();
							return false;
						}
					}
					rs.close();
				}
				else if(this.loaihanghoa.trim().equals("2"))
				{
					query = "select chiphi_fk,DIENGIAI from erp_denghimuahang_sp where denghi_fk = "+this.dnmhId;
					rs = db.get(query);
					while(rs.next())
					{
						sql = "insert into erp_timncc_ncc(TIMNCC_FK, NCC_FK, chiphi_fk,diengiai) " +
							  "select '"+ this.id + "', pk_seq, "+rs.getString("chiphi_fk")+" ,N'"+rs.getString("DIENGIAI")+"' from erp_nhacungcap where pk_seq in ("+this.nccids+")";
						System.out.println("1.Insert: " + sql);
						if(!db.update(sql))
						{
							this.msg = "Khong the tao moi erp_timncc_ncc: " + sql;
							db.getConnection().rollback();
							return false;
						}
					}
					rs.close();
				}
				else
				{
					query = "select ccdc_fk,DIENGIAI  from erp_denghimuahang_sp where denghi_fk = "+this.dnmhId;
					rs = db.get(query);
					while(rs.next())
					{
						sql = "insert into erp_timncc_ncc(TIMNCC_FK, NCC_FK, ccdc_fk,DIENGIAI) " +
							  "select '"+ this.id + "', pk_seq, "+rs.getString("ccdc_fk")+",N'"+rs.getString("DIENGIAI")+"' from erp_nhacungcap where pk_seq in ("+this.nccids+")";
						System.out.println("1.Insert: " + sql);
						if(!db.update(sql))
						{
							this.msg = "Khong the tao moi erp_timncc_ncc: " + sql;
							db.getConnection().rollback();
							return false;
						}
					}
					rs.close();
				}
				
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e)
		{
			try 
			{
				db.getConnection().rollback();
				e.printStackTrace();
				
				this.msg = "Lỗi khi tạo mới tìm nhà cung cấp: " + e.getMessage();
			} 
			catch (SQLException e1) {}
			
			return false;
		}
		return true;
	}
	
	public boolean updateTimNcc()
	{
		try
		{
			this.nppId = util.getIdNhapp(userId);
			if(this.dnmhId.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn đề nghị đặt hàng";
				return false;
			}
			
			db.getConnection().setAutoCommit(false);
			
			String query = "update erp_timncc set ngaysua = '"+this.getDateTime()+"', nguoisua = '"+this.userId+"', hinhthuc = '"+this.hinhthuc+"', "
					+ "diengiai = N'"+this.diengiai+"', DENGHIMUA_FK = '"+this.dnmhId + "', nguongochh = '"+this.nguongochh+"' where pk_seq = '"+this.id+"'";
			
			System.out.println("2.Update: " + query);
			if(!db.update(query))
			{
				this.msg = "Khong the cập nhật erp_timncc: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query="delete from erp_timncc_ncc where TIMNCC_FK="+this.id +" ";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật erp_timncc_ncc: " + query;
				this.db.getConnection().rollback();
				return false;
			}
			
			if(this.hinhthuc.equals("0") && this.spList != null)
			{
				for (int i = 0; i < this.spList.size(); i++) {
					ISanpham sp = this.spList.get(i);
					List<INhacungcap> lstncc = sp.getNhacungcap(); 
					for (int j = 0; j < lstncc.size(); j++) {
						INhacungcap ncc = lstncc.get(j);
						if(ncc.getChon().trim().equals(ncc.getId().trim()))
						{
							if(this.loaihanghoa.trim().equals("0"))
							{
								query = "insert into erp_timncc_ncc(TIMNCC_FK, NCC_FK, sp_fk,diengiai) " +
									"values('"+ this.id + "', '" + ncc.getId() + "', '" + sp.getId()+"', N'" + sp.getDiengiai() + "' )";
							}
							else if(this.loaihanghoa.trim().equals("1"))
							{
								query = "insert into erp_timncc_ncc(TIMNCC_FK, NCC_FK, taisan_fk ,diengiai) " +
										"values('"+ this.id + "', '" + ncc.getId() + "', '" + sp.getId() +"', N'" + sp.getDiengiai() + "')";
							}
							else if(this.loaihanghoa.trim().equals("2"))
							{
								query = "insert into erp_timncc_ncc(TIMNCC_FK, NCC_FK, chiphi_fk ,diengiai) " +
										"values('"+ this.id + "', '" + ncc.getId() + "', '" + sp.getId() +"', N'" + sp.getDiengiai() + "' )";
							}
							else
							{
								query = "insert into erp_timncc_ncc(TIMNCC_FK, NCC_FK, ccdc_fk, ,diengiai) " +
										"values('"+ this.id + "', '" + ncc.getId() + "', '" + sp.getId() +"', N'" + sp.getDiengiai() + "' )";
							}
							
							System.out.println("1.Insert: " + query);
							if(!db.update(query))
							{
								this.msg = "Khong the cap nhat erp_timncc_ncc: " + query;
								db.getConnection().rollback();
								return false;
							}
						}
					}
				}
			}
			else
			{
				String sql = "";
				ResultSet rs;
				if(this.loaihanghoa.trim().equals("0"))
				{
					query = "select sanpham_fk,DIENGIAI from erp_denghimuahang_sp where denghi_fk = "+this.dnmhId;
					rs = db.get(query);
					while(rs.next())
					{
						sql = "insert into erp_timncc_ncc(TIMNCC_FK, NCC_FK, sp_fk,diengiai) " +
							  "select '"+ this.id + "', pk_seq, "+rs.getString("sanpham_fk")+",N'"+rs.getString("DIENGIAI")+"' from erp_nhacungcap where pk_seq in ("+this.nccids+")";
						System.out.println("1.Insert: " + sql);
						if(!db.update(sql))
						{
							this.msg = "Khong the tao moi erp_timncc_ncc: " + sql;
							db.getConnection().rollback();
							return false;
						}
					}
					rs.close();
				}
				else if(this.loaihanghoa.trim().equals("1"))
				{
					query = "select taisan_fk,DIENGIAI from erp_denghimuahang_sp where denghi_fk = "+this.dnmhId;
					rs = db.get(query);
					while(rs.next())
					{
						sql = "insert into erp_timncc_ncc(TIMNCC_FK, NCC_FK, taisan_fk,diengiai) " +
							  "select '"+ this.id + "', pk_seq, "+rs.getString("taisan_fk")+",N'"+rs.getString("DIENGIAI")+"' from erp_nhacungcap where pk_seq in ("+this.nccids+")";
						System.out.println("1.Insert: " + sql);
						if(!db.update(sql))
						{
							this.msg = "Khong the tao moi erp_timncc_ncc: " + sql;
							db.getConnection().rollback();
							return false;
						}
					}
					rs.close();
				}
				else if(this.loaihanghoa.trim().equals("2"))
				{
					query = "select chiphi_fk,DIENGIAI from erp_denghimuahang_sp where denghi_fk = "+this.dnmhId;
					rs = db.get(query);
					while(rs.next())
					{
						sql = "insert into erp_timncc_ncc(TIMNCC_FK, NCC_FK, chiphi_fk,diengiai) " +
							  "select '"+ this.id + "', pk_seq, "+rs.getString("chiphi_fk")+",N'"+rs.getString("DIENGIAI")+"' from erp_nhacungcap where pk_seq in ("+this.nccids+")";
						System.out.println("1.Insert: " + sql);
						if(!db.update(sql))
						{
							this.msg = "Khong the tao moi erp_timncc_ncc: " + sql;
							db.getConnection().rollback();
							return false;
						}
					}
					rs.close();
				}
				else
				{
					query = "select ccdc_fk,DIENGIAI from erp_denghimuahang_sp where denghi_fk = "+this.dnmhId;
					rs = db.get(query);
					while(rs.next())
					{
						sql = "insert into erp_timncc_ncc(TIMNCC_FK, NCC_FK, ccdc_fk,diengiai) " +
							  "select '"+ this.id + "', pk_seq, "+rs.getString("ccdc_fk")+",N'"+rs.getString("DIENGIAI")+"' from erp_nhacungcap where pk_seq in ("+this.nccids+")";
						System.out.println("1.Insert: " + sql);
						if(!db.update(sql))
						{
							this.msg = "Khong the tao moi erp_timncc_ncc: " + sql;
							db.getConnection().rollback();
							return false;
						}
					}
					rs.close();
				}
				
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e)
		{
			try 
			{
				db.getConnection().rollback();
				e.printStackTrace();
				
				this.msg = "Lỗi khi cập nhật tìm nhà cung cấp: " + e.getMessage();
			} 
			catch (SQLException e1) {}
			
			return false;
		}
		return true;
	}
	
	@Override
	public void close() {
		
		try{
			
			if(dnmhRs!=null){
				dnmhRs.close();
			}
			if(nccRs!=null){
				nccRs.close();
			}
		}catch(Exception er){
			er.printStackTrace();
		}
		db.shutDown();
	}

	@Override
	public String getDnmhId() {
		return this.dnmhId;
	}

	@Override
	public void setDnmhId(String dnmhId) {
		
		this.dnmhId = dnmhId;
	}

	@Override
	public ResultSet getDnmhRs() {
		
		return this.dnmhRs;
	}

	@Override
	public void setDnmhRs(ResultSet dnmhRs) {
		
		this.dnmhRs = dnmhRs;
	}

	@Override
	public String getNgaytao() {
		
		return this.ngaytao;
	}

	@Override
	public void setNgaytao(String ngaytao) {
		
		this.ngaytao = ngaytao;
	}

	@Override
	public String getNgaysua() {
		
		return this.ngaysua;
	}

	@Override
	public void setNgaysua(String ngaysua) {
		
		this.ngaysua = ngaysua;
	}

	@Override
	public String getNguoitao() {
		
		return this.nguoitao;
	}

	@Override
	public void setNguoitao(String nguoitao) {
		
		this.nguoitao = nguoitao;
	}

	@Override
	public String getNguoisua() {
		
		return this.nguoisua;
	}

	@Override
	public void setNguoisua(String nguoisua) {
		
		this.nguoisua = nguoisua;
	}

	@Override
	public String getMessage() {
		
		return this.msg;
	}

	@Override
	public void setMessage(String msg) {
		
		this.msg = msg;
	}
	
	public void setActive(String active) 
	{
		this.active = active;
	}

	public String getActive()
	{
		return this.active;
	}
	@Override
	public String getSpId() {
		
		return this.spId;
	}
	@Override
	public void setSpId(String spId) {
		
		this.spId = spId;
	}
	@Override
	public List<ISanpham> getSpList() {
		
		return this.spList;
	}
	@Override
	public void setSpList(List<ISanpham> spList) {
		
		this.spList = spList;
	}
	@Override
	public void setLoaihh(String loaihh) {
		
		this.loaihanghoa = loaihh;
	}
	@Override
	public String getLoaihh() {
		
		return this.loaihanghoa;
	}
	@Override
	public void setHinhthuc(String hinhthuc) {
		this.hinhthuc = hinhthuc;
	}
	@Override
	public String getHinhthuc() {
		return this.hinhthuc;
	}
	@Override
	public void setNccIds(String nccids) {
		this.nccids = nccids;
	}
	@Override
	public String getNccIds() {
		return this.nccids;
	}
	@Override
	public ResultSet getNccRs() {
		return this.nccRs;
	}
	@Override
	public void setNccRs(ResultSet nccRs) {
		this.nccRs = nccRs;
	}
	@Override
	public void setNguongochh(String nguongochh) {
		this.nguongochh = nguongochh;
	}
	@Override
	public String getNguongochh() {
		return this.nguongochh;
	}
}
