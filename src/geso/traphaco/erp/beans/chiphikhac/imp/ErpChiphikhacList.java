package geso.traphaco.erp.beans.chiphikhac.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import geso.traphaco.erp.beans.chiphikhac.IErpChiphikhacList;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.DinhKhoanKeToan;
import geso.traphaco.center.util.IDinhKhoanKeToan;
import geso.traphaco.center.util.IThongTinHienThi;
import geso.traphaco.center.util.ThongTinHienThi;
import geso.traphaco.center.util.Utility;

public class ErpChiphikhacList implements IErpChiphikhacList 
{
	String userId;
	String congtyId;
	String ma;
	String diengiai;
	String trangthai;
	String ngaytao = "";
	String msg;
	String sohoadon;
	String nguoitaoid;
	String nppdangnhap;
	
	ResultSet cpkRs;
	ResultSet nguoitaoRs;
	
	List<IThongTinHienThi> hienthiList;
	
	dbutils db;
	
	public ErpChiphikhacList()
	{
		this.userId = "";

		this.ma = "";
		this.trangthai = "";
		this.diengiai = "";
		this.sohoadon="";
		this.msg = "";
		this.nguoitaoid="";
		this.nppdangnhap = "";
		this.db = new dbutils();
		this.hienthiList = new ArrayList<IThongTinHienThi>();
		
	}
	
	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;	
	}

	public String getMa() 
	{
		return this.ma;
	}

	public void setMa(String ma) 
	{
		this.ma = ma;
	}
	
	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}

	public String getDiengiai() 
	{
		return this.diengiai;
	}

	public void setDiengiai(String diengiai) 
	{
		this.diengiai = diengiai;
	}

	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}
	private String LayDuLieu(String id) {
		
		String query =  " select	N'Chi phí' as loaidoituongNO, e.PK_SEQ as madoituongNO,  " + 
						"		case a.loai when 0 then N'Nhà cung cấp' else N'Nhân viên' end as loaidoituongCO, " + 
						"		case a.loai when 0 then c.PK_SEQ else b.PK_SEQ end as madoituongCO, " + 
						"		a.ngay as ngayhoadon, d.TONGTIENCHUATHUE as DOANHSO, d.THUE,  " + 
						"		case a.loai when 0 then c.TAIKHOAN_FK else b.TAIKHOAN_FK end as taikhoanCO_DS, " + 
						"		case a.loai when 0 then c.TAIKHOAN_FK else b.TAIKHOAN_FK end as taikhoanCO_VAT, " + 
						"		e.TAIKHOAN_FK as taikhoanNO_DS, " + 
						"		( select PK_SEQ from ERP_TAIKHOANKT where SOHIEUTAIKHOAN = '13310000' AND CONGTY_FK = "+this.congtyId+" ) as taikhoanNO_VAT " + 
						" from ERP_CHIPHIKHAC a " +
						" left join ERP_NHANVIEN b on a.DOITUONG = b.PK_SEQ  " + 
						" left join ERP_NHACUNGCAP c on a.DOITUONG = c.PK_SEQ " + 
						" inner join ERP_CHIPHIKHAC_CHITIET d on a.PK_SEQ = d.CHIPHIKHAC_FK " + 
						" inner join ERP_NHOMCHIPHI e on d.NHOMCHIPHI_FK = e.PK_SEQ " + 
						" where a.PK_SEQ = '" + id + "' " ;
		
		String laytk = "";
		
		
		String taikhoanCO_DS = "";
		String taikhoanNO_DS = "";
		
		String taikhoanCO_VAT = "";
		String taikhoanNO_VAT = "";
		
		String loaidoituongCO = "";
		String madoituongCO = "";
		
		String loaidoituongNO = "";
		String madoituongNO = "";
		
		ResultSet rsTk = db.get(query);
		
		try{
			
			while(rsTk.next())
			{
				double totalDS = Math.round(rsTk.getDouble("DOANHSO"));
				double totalVAT = Math.round(rsTk.getDouble("THUE"));
				
				taikhoanCO_DS = rsTk.getString("taikhoanCO_DS");
				taikhoanNO_DS = rsTk.getString("taikhoanNO_DS");
				
				taikhoanCO_VAT = rsTk.getString("taikhoanCO_VAT");
				taikhoanNO_VAT = rsTk.getString("taikhoanNO_VAT");
				
				loaidoituongCO = rsTk.getString("loaidoituongCO");
				madoituongCO = rsTk.getString("madoituongCO");
				loaidoituongNO = rsTk.getString("loaidoituongNO");
				madoituongNO = rsTk.getString("madoituongNO");
	
				String ngayghinhan = rsTk.getString("ngayhoadon");
				String nam = ngayghinhan.substring(0, 4);
				String thang = ngayghinhan.substring(5, 7);
				
				String tiente_fk = "100000";
		
				if(totalDS > 0)
				{
					if(laytk.trim().length() > 0) laytk += "UNION ALL \n";
					
					laytk = "SELECT N'NỢ' NO_CO, "+id+" PK_SEQ, "+totalDS+" SOTIEN, (SELECT SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ = '"+taikhoanNO_DS+"' AND CONGTY_FK = "+congtyId+") SOHIEUTAIKHOAN, e.TEN DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 1 STT, 1 SAPXEP \n " +
							" from ERP_CHIPHIKHAC a " +
							" left join ERP_NHANVIEN b on a.DOITUONG = b.PK_SEQ  " + 
							" left join ERP_NHACUNGCAP c on a.DOITUONG = c.PK_SEQ " + 
							" inner join ERP_CHIPHIKHAC_CHITIET d on a.PK_SEQ = d.CHIPHIKHAC_FK " + 
							" inner join ERP_NHOMCHIPHI e on d.NHOMCHIPHI_FK = e.PK_SEQ " + 
							" where a.PK_SEQ = '" + id + "' " +
					
							"UNION ALL \n"+
					
							"SELECT N'CÓ' NO_CO, "+id+" PK_SEQ, "+totalDS+" SOTIEN, (SELECT SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ = '"+taikhoanCO_DS+"' AND CONGTY_FK = "+congtyId+") SOHIEUTAIKHOAN, " +
							" (case a.loai when 0 then c.TEN else b.TEN end ) DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 1 STT, 2 SAPXEP \n " +
							" from ERP_CHIPHIKHAC a " +
							" left join ERP_NHANVIEN b on a.DOITUONG = b.PK_SEQ  " + 
							" left join ERP_NHACUNGCAP c on a.DOITUONG = c.PK_SEQ " + 
							" inner join ERP_CHIPHIKHAC_CHITIET d on a.PK_SEQ = d.CHIPHIKHAC_FK " + 
							" inner join ERP_NHOMCHIPHI e on d.NHOMCHIPHI_FK = e.PK_SEQ " + 
							" where a.PK_SEQ = '" + id + "' " ;			
					
				}
				
				if(totalVAT > 0)
				{
					if(laytk.trim().length() > 0) laytk += "UNION ALL \n";
					
					laytk += "SELECT N'NỢ' NO_CO, "+id+" PK_SEQ, "+totalVAT+" SOTIEN, '13311000' SOHIEUTAIKHOAN, '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 2 STT, 1 SAPXEP \n " +
					" from ERP_CHIPHIKHAC a " +
					" left join ERP_NHANVIEN b on a.DOITUONG = b.PK_SEQ  " + 
					" left join ERP_NHACUNGCAP c on a.DOITUONG = c.PK_SEQ " + 
					" inner join ERP_CHIPHIKHAC_CHITIET d on a.PK_SEQ = d.CHIPHIKHAC_FK " + 
					" inner join ERP_NHOMCHIPHI e on d.NHOMCHIPHI_FK = e.PK_SEQ " + 
					" where a.PK_SEQ = '" + id + "' " +		
			
					"UNION ALL \n"+
			
					"SELECT N'CÓ' NO_CO, "+id+" PK_SEQ, "+totalVAT+" SOTIEN, (SELECT SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ = '"+taikhoanCO_DS+"' AND CONGTY_FK = "+this.congtyId+") SOHIEUTAIKHOAN, " +
					" (case a.loai when 0 then c.TEN else b.TEN end ) DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 2 STT, 2 SAPXEP \n " +
					" from ERP_CHIPHIKHAC a " +
					" left join ERP_NHANVIEN b on a.DOITUONG = b.PK_SEQ  " + 
					" left join ERP_NHACUNGCAP c on a.DOITUONG = c.PK_SEQ " + 
					" inner join ERP_CHIPHIKHAC_CHITIET d on a.PK_SEQ = d.CHIPHIKHAC_FK " + 
					" inner join ERP_NHOMCHIPHI e on d.NHOMCHIPHI_FK = e.PK_SEQ " + 
					" where a.PK_SEQ = '" + id + "' " ;			
										
				}
				
			}
			rsTk.close();
		db.getConnection().commit();
	} 
	catch (Exception e) 
	{
		e.printStackTrace();
	}
	
		if(laytk.trim().length()>0) 
			{
				laytk += " ORDER BY PK_SEQ, STT, SAPXEP ";
			}
		else {
			laytk = "SELECT '' NO_CO, '' PK_SEQ, '' NGAYHOADON, '0' SOTIEN, '' SOHIEUTAIKHOAN, '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 1 STT, 1 SAPXEP \n " +
					"FROM ERP_CHIPHIKHAC \n " +
					"WHERE PK_SEQ = '"+id+"'";
		}
		
		System.out.println(laytk);
		
		return laytk;
	}
	
	public void init(String query) 
	{
		this.getNppInfo();
		
		this.nguoitaoRs = db.get("select distinct NHANVIEN.PK_SEQ, NHANVIEN.TEN FROM  ERP_CHIPHIKHAC inner join NHANVIEN on ERP_CHIPHIKHAC.NGUOITAO = NHANVIEN.PK_SEQ WHERE ERP_CHIPHIKHAC.CONGTY_FK = "+this.congtyId+"");
		
		String sql = "";
		
		if(query.length() > 0)
			sql = query;
		else
		{	
			sql = 	"SELECT	DISTINCT CPK.PK_SEQ AS CPID, CPK.NGAY, CPK.DIENGIAI, CPK.TRANGTHAI, " + 
					"		CASE WHEN CPK.LOAI = 1 THEN ISNULL(NV.TEN, ' ') " +
					"		ELSE ISNULL(NCC.TEN, ' ') END AS DOITUONG, " +
					"		NV1.TEN AS NGUOITAO, NV2.TEN AS NGUOISUA " + 
					"FROM  ERP_CHIPHIKHAC CPK " +
					"		INNER JOIN NHANVIEN NV1 ON NV1.PK_SEQ = CPK.NGUOITAO " + 
					"		INNER JOIN NHANVIEN NV2 ON NV2.PK_SEQ = CPK.NGUOISUA " +
					"		LEFT JOIN ERP_NHACUNGCAP NCC ON NCC.PK_SEQ = CPK.DOITUONG " +
					"		LEFT JOIN ERP_NHANVIEN NV ON NV.PK_SEQ = CPK.DOITUONG " +
					"		INNER JOIN ERP_HOADONCHIPHIKHAC HDCPK ON CPK.PK_SEQ=HDCPK.CHIPHIKHAC_FK " +
					"WHERE CPK.CONGTY_FK = "+this.congtyId +
					" ORDER BY CPK.NGAY DESC";
		}
		
		System.out.println("__Chi phi : " + sql);
		//this.cpkRs = db.get(sql);
		
		ResultSet rs = db.get(sql);
		List<IThongTinHienThi> htList = new ArrayList<IThongTinHienThi>();
		
		try
		{
			if(rs!= null)
			{
				IThongTinHienThi ht = null;
				while(rs.next())
				{		
					ht = new ThongTinHienThi();
					String dk = LayDuLieu(rs.getString("CPID") );	
					
					ResultSet rsKT = db.get(dk);
					List<IDinhKhoanKeToan> ktList = new ArrayList<IDinhKhoanKeToan>();
						if(rsKT!= null)
						{
							IDinhKhoanKeToan kt = null;
							while(rsKT.next())
							{
								kt = new DinhKhoanKeToan(rsKT.getString("PK_SEQ"), rsKT.getString("NO_CO"),rsKT.getString("SOHIEUTAIKHOAN"),rsKT.getString("SOTIEN"),rsKT.getString("DOITUONG"),
										 rsKT.getString("TRUNGTAMCHIPHI"),rsKT.getString("TRUNGTAMDOANHTHU"), "");
								ktList.add(kt);
							}
							rsKT.close();
						}
												
						ht.setId(rs.getString("CPID"));
						ht.setNGAYCHUNGTU(rs.getString("NGAY"));
						ht.setDIENGIAI(rs.getString("DIENGIAI"));
						ht.setKhachhang(rs.getString("DOITUONG"));
						
						ht.setTRANGTHAI(rs.getString("TRANGTHAI"));
						ht.setNGUOITAO(rs.getString("NGUOITAO"));
						ht.setNGUOISUA(rs.getString("NGUOISUA"));
						
						ht.setLayDinhkhoanKT(ktList);
					
					htList.add(ht);								
				}
				rs.close();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		this.hienthiList = htList;
	}

	public void Chot(String Id)
	{
		this.db = new dbutils();
		
		Utility util = new Utility();
		
		try 
		{
			db.getConnection().setAutoCommit(false);
						
			String query =  " select N'Chi phí' as loaidoituongNO, e.PK_SEQ as madoituongNO,  " + 
							"		case a.loai when 0 then N'Nhà cung cấp' else N'Nhân viên' end as loaidoituongCO, " + 
							"		case a.loai when 0 then c.PK_SEQ else b.PK_SEQ end as madoituongCO, " + 
							"		a.ngay as ngayhoadon, d.TONGTIENCHUATHUE as DOANHSO, d.THUE,  " + 
							"		case a.loai when 0 then c.TAIKHOAN_FK else b.TAIKHOAN_FK end as taikhoanCO_DS, " + 
							"		case a.loai when 0 then c.TAIKHOAN_FK else b.TAIKHOAN_FK end as taikhoanCO_VAT, " + 
							"		e.TAIKHOAN_FK as taikhoanNO_DS, " + 
							"		( select PK_SEQ from ERP_TAIKHOANKT where SOHIEUTAIKHOAN = '13310000' AND CONGTY_FK = "+this.congtyId+" ) as taikhoanNO_VAT " + 
							" from 	ERP_CHIPHIKHAC a " +
							" 		left join ERP_NHANVIEN b on a.DOITUONG = b.PK_SEQ  " + 
							" 		left join ERP_NHACUNGCAP c on a.DOITUONG = c.PK_SEQ " + 
							" 		inner join ERP_CHIPHIKHAC_CHITIET d on a.PK_SEQ = d.CHIPHIKHAC_FK " + 
							" 		inner join ERP_NHOMCHIPHI e on d.NHOMCHIPHI_FK = e.PK_SEQ " + 
							" where a.PK_SEQ = '" + Id + "' " ;
 
			String taikhoanCO_DS = "";
			String taikhoanNO_DS = "";
			
			String taikhoanCO_VAT = "";
			String taikhoanNO_VAT = "";
			
			String loaidoituongCO = "";
			String madoituongCO = "";
			
			String loaidoituongNO = "";
			String madoituongNO = "";
			
			ResultSet rsTk = db.get(query);
			while(rsTk.next())
			{
				double totalDS = Math.round(rsTk.getDouble("DOANHSO"));
				double totalVAT = Math.round(rsTk.getDouble("THUE"));
				
				taikhoanCO_DS = rsTk.getString("taikhoanCO_DS");
				taikhoanNO_DS = rsTk.getString("taikhoanNO_DS");
				
				taikhoanCO_VAT = rsTk.getString("taikhoanCO_VAT");
				taikhoanNO_VAT = rsTk.getString("taikhoanNO_VAT");
				
				loaidoituongCO = rsTk.getString("loaidoituongCO");
				madoituongCO = rsTk.getString("madoituongCO");
				loaidoituongNO = rsTk.getString("loaidoituongNO");
				madoituongNO = rsTk.getString("madoituongNO");

				String ngayghinhan = rsTk.getString("ngayhoadon");
				String nam = ngayghinhan.substring(0, 4);
				String thang = ngayghinhan.substring(5, 7);
				
				String tiente_fk = "100000";
			
				if(totalDS > 0)
				{
					if(taikhoanCO_DS.trim().length() <= 0 || taikhoanNO_DS.trim().length() <= 0 )
					{
						msg = "Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
						db.getConnection().rollback();
						return;
					}
					
					msg = util.Update_TaiKhoan( db, thang, nam, ngayghinhan, ngayghinhan, "Chi phí khác", Id, taikhoanNO_DS, taikhoanCO_DS, "", 
									Double.toString(totalDS), Double.toString(totalDS), loaidoituongNO, madoituongNO, 
									loaidoituongCO, madoituongCO, "0", "", "", tiente_fk, "", "1", Double.toString(totalDS), Double.toString(totalDS), "Doanh số" );
					if(msg.trim().length() > 0)
					{
						db.getConnection().rollback();
						return;
					}
				}
				
				if(totalVAT > 0)
				{
					if(taikhoanCO_VAT.trim().length() <= 0 || taikhoanNO_VAT.trim().length() <= 0 )
					{
						msg = "Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
						db.getConnection().rollback();
						return;
					}
					
					msg = util.Update_TaiKhoan( db, thang, nam, ngayghinhan, ngayghinhan, "Chi phí khác", Id, taikhoanNO_VAT, taikhoanCO_VAT, "", 
						  Double.toString(totalVAT), Double.toString(totalVAT), loaidoituongNO, madoituongNO, "", "", "0", "", "", 
						  tiente_fk, "", "1", Double.toString(totalVAT), Double.toString(totalVAT), "VAT" );
					if(msg.trim().length() > 0)
					{
						db.getConnection().rollback();
						return;
					}
				}
				
			}
			rsTk.close();
			
			
			query = " UPDATE ERP_CHIPHIKHAC SET TRANGTHAI = '1' WHERE PK_SEQ = '" + Id + "'";
			if(!db.update(query))
			{
				this.msg = "Lỗi khi chốt: " + query;
				db.getConnection().rollback();
				return;
			}
			
			db.getConnection().commit();
		} 
		catch (Exception e) 
		{
			db.update("rollback");
			this.msg = "Lỗi khi chốt: " + e.getMessage();
			return;
		}
	}
	
	public void Xoa(String Id){
		
	 
		try{
				String	query =	"UPDATE ERP_CHIPHIKHAC SET TRANGTHAI = '2' WHERE PK_SEQ = '" + Id + "'";
				
				if(!this.db.update(query)){
					this.msg="Không thể thực hiện hủy chứng từ này";
					
				}
		 
		}catch(Exception e){
			this.msg=e.getMessage();
			e.printStackTrace();
			
		}
		
	}
	
	public void DbClose() 
	{
		try 
		{
			if(this.cpkRs != null)
				this.cpkRs.close();
			this.db.shutDown();
		} 
		catch (SQLException e) {}
	}

	public ResultSet getChiphikhacRs() 
	{
		return this.cpkRs;
	}

	public void setChiphikhacRs(ResultSet cpkRs) 
	{
		this.cpkRs = cpkRs;
	}

	public String getCongtyId() 
	{
		return this.congtyId;
	}

	public void setCongtyId(String congtyId) 
	{
		this.congtyId = congtyId;
	}


	public String getNgayTao() {
		return this.ngaytao;
	}


	public void setNgayTao(String ngaytao) {
		this.ngaytao = ngaytao;
	}


	public String getSoHoaDon() {
		
		return this.sohoadon;
	}


	public void setSoHoaDon(String sohoadon) {
		
		this.sohoadon=sohoadon;
	}

	
	public ResultSet getNguoiTaoRs() {
		
		return this.nguoitaoRs;
	}

	
	public void setNguoiTaoRs(ResultSet nguoitaoRs) {
		
		this.nguoitaoRs=nguoitaoRs;
	}

	
	public String getNguoiTao() {
		
		return this.nguoitaoid;
	}

	
	public void setNguoiTao(String nguoitaoid) {
		
		this.nguoitaoid=nguoitaoid;
	}

	
	public List<IThongTinHienThi> getHienthiList() 
	{
		return this.hienthiList;
	}

	public void setHienthiList(List<IThongTinHienThi> hienthiList) 
	{
		this.hienthiList = hienthiList;
	}

	
	public String getnppdangnhap() {
		
		return this.nppdangnhap;
	}

	
	public void setnppdangnhap(String nppdangnhap) {
		
		this.nppdangnhap = nppdangnhap;
	}
	
	private void getNppInfo()
	{		
		//Phien ban moi
		geso.traphaco.distributor.util.Utility util=new geso.traphaco.distributor.util.Utility();
		this.nppdangnhap=util.getIdNhapp(this.userId);
	}


}
