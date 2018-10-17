package geso.traphaco.erp.beans.giamgiahangmua.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import geso.traphaco.center.util.DinhKhoanKeToan;
import geso.traphaco.center.util.IDinhKhoanKeToan;
import geso.traphaco.center.util.IThongTinHienThi;
import geso.traphaco.center.util.ThongTinHienThi;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.beans.giamgiahangmua.IErpGiamgiahangmuaList;

public class ErpGiamgiahangmuaList implements IErpGiamgiahangmuaList 
{
	String userId;
	String congtyId;
	String ma;
	String diengiai;
	String trangthai; 
	String msg;
	
	ResultSet giamgiaRs;
	
	dbutils db;
	List<IThongTinHienThi> hienthiList;
	
	public ErpGiamgiahangmuaList()
	{
		this.userId = "";

		this.ma = "";
		this.trangthai = "";
		this.diengiai = "";
		
		this.msg = "";
		this.hienthiList = new ArrayList<IThongTinHienThi>();
		
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

	public String getSohoadon(String Id) 
	{
		String soHD = "";
		String query = "SELECT HOADON_FK FROM  ERP_GIAMGIAHANGMUA_HOADON WHERE GIAMGIA_FK = " + Id + " ";
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				while(rs.next()){
					soHD = soHD + rs.getString("HOADON_FK") + ", ";
				}
				rs.close();
			}catch(java.sql.SQLException e){}
		}
		if(soHD.length() > 0){
			soHD = soHD.substring(0, soHD.length() - 2);
		}
		return soHD;
	}

	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	public void init(String query) 
	{
		String sql = "";
		
		if(query.length() > 0)
			sql = query;
		else
		{	
			sql = " select a.pk_seq, d.ten as nccTen, a.diengiai, " +
				  "		   a.trangthai, b.ten as nguoitao, a.ngaytao, c.ten as nguoisua, a.ngaysua      " +
				  " from ERP_GiamGiaHangMua a " +
				  " inner join NhanVien b on a.nguoitao = b.pk_seq      " +
				  " inner join nhanvien c on a.nguoisua = c.pk_seq " +
				  " inner join ERP_NhaCungCap d on a.ncc_fk = d.pk_seq   " +
				  " order by a.pk_seq desc ";
		}
		
		System.out.println("sql: " + sql);
		
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
					//LAY DINH KHOAN KE TOAN
					String dk = LayDuLieu(rs.getString("pk_seq") );
					ResultSet rsKT = db.get(dk);
					List<IDinhKhoanKeToan> ktList = new ArrayList<IDinhKhoanKeToan>();
					
					if(rsKT!= null)
					{
						IDinhKhoanKeToan kt = null;
						while(rsKT.next())
						{
							kt = new DinhKhoanKeToan(rsKT.getString("PK_SEQ"), rsKT.getString("NO_CO"),rsKT.getString("SOHIEUTAIKHOAN"),
										rsKT.getString("SOTIEN"),rsKT.getString("DOITUONG"),
										rsKT.getString("TRUNGTAMCHIPHI"),rsKT.getString("TRUNGTAMDOANHTHU"), "");
							System.out.println("Số tiền "+kt.getSotien());
							ktList.add(kt);
						}
						
						rsKT.close();
					}
					ht.setId(rs.getString("pk_seq"));
					ht.setDIENGIAI(rs.getString("diengiai"));
					ht.setKhachhang(rs.getString("nccTen"));
					ht.setTrangthai(rs.getString("trangthai"));
					ht.setNgaytao(rs.getString("ngaytao"));
					ht.setNguoitao(rs.getString("nguoitao"));
					ht.setNgaysua(rs.getString("ngaysua"));
					ht.setNguoisua(rs.getString("nguoisua"));
					
					ht.setLayDinhkhoanKT(ktList);
					
					htList.add(ht);		
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		this.hienthiList = htList;
	}
	
	private String LayDuLieu(String id) {
		
		String query=
		" select GG.ncc_fk ,GGSP.sanpham_fk, GGSP.sotien ,GGSP.sotien * GG.vat / 100 as vat, \n"+
		"(select TAIKHOAN_FK from ERP_NHACUNGCAP where pk_seq=GG.ncc_fk) as taikhoanNO, \n"+
		"( select TAIKHOANKT_FK from ERP_LOAISANPHAM where PK_SEQ= sp.LOAISANPHAM_FK ) as taikhoanCO_DS," +
		"(select pk_seq from ERP_TAIKHOANKT where SOHIEUTAIKHOAN = '13311000') as taikhoanCO_VAT \n"+
		"  from ERP_GiamGiaHangMua GG inner join erp_giamgiahangmua_sanpham GGSP on GG.pk_seq=GGSP.giamgiamua_fk \n"+
		"			                 inner join ERP_SANPHAM  SP on GGSP.sanpham_fk= SP.PK_SEQ \n"+
		" where GG.pk_seq= "+id+" ";
		

		String laytk="";
		
		ResultSet rsSp = db.get(query);
		try{
		if(rsSp != null)
		{
			String taikhoanktNo = "";
			String taikhoanktCo_DS = "";
			String taikhoanktCo_VAT = "";
			
			String doituong_no = "";
			String madoituong_no = "";
			String doituong_co = "";
			String madoituong_co = "";
			String sanpham = "";
			while(rsSp.next())
			{
				taikhoanktNo = rsSp.getString("taikhoanNO") == null ? "" : rsSp.getString("taikhoanNO") ;
				taikhoanktCo_DS = rsSp.getString("taikhoanCO_DS") == null ? "" : rsSp.getString("taikhoanCO_DS") ;
				taikhoanktCo_VAT = rsSp.getString("taikhoanCO_VAT") == null ? "" : rsSp.getString("taikhoanCO_VAT") ;
				
				sanpham = rsSp.getString("sanpham_fk");
				
				double sotienGIAM = Math.round(rsSp.getDouble("sotien"));
				if(sotienGIAM > 0)
				{										
					if(laytk.trim().length()>0) laytk+=" UNION ALL \n";
					
					laytk+=					
					" SELECT N'NỢ' NO_CO, PK_SEQ, "+sotienGIAM+" SOTIEN, (SELECT SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ ='"+taikhoanktNo+"') SOHIEUTAIKHOAN,  \n"+ 
					"		(SELECT MA+' - '+TEN from ERP_NHACUNGCAP where pk_seq=GG.ncc_fk) DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU,1 STT, 2 SAPXEP \n"+
					" from ERP_GiamGiaHangMua GG  \n"+
					" WHERE PK_SEQ = '"+id+"' \n"+

					" UNION ALL \n"+

					" SELECT N'CÓ' NO_CO, PK_SEQ , "+sotienGIAM+" SOTIEN, '13311000' SOHIEUTAIKHOAN, \n"+ 
					"	 '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU,2 STT, 2 SAPXEP \n"+
					" FROM ERP_GiamGiaHangMua \n"+
					" WHERE PK_SEQ = '"+id+"' \n";
				}
				
				double sotienGIAM_VAT = Math.round(rsSp.getDouble("vat"));
				if(sotienGIAM_VAT > 0)
				{
					if(laytk.trim().length()>0) laytk+=" UNION ALL \n";
					
					laytk+=					
					" SELECT N'NỢ' NO_CO, PK_SEQ, "+sotienGIAM_VAT+" SOTIEN, (SELECT SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ ='"+taikhoanktNo+"') SOHIEUTAIKHOAN,  \n"+ 
					"		(SELECT MA+' - '+TEN from ERP_NHACUNGCAP where pk_seq=GG.ncc_fk) DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU,3 STT, 2 SAPXEP \n"+
					" from ERP_GiamGiaHangMua GG  \n"+
					" WHERE PK_SEQ = '"+id+"' \n"+

					" UNION ALL \n"+

					" SELECT N'CÓ' NO_CO, PK_SEQ , "+sotienGIAM_VAT+" SOTIEN, '13311000' SOHIEUTAIKHOAN, \n"+ 
					"	   (SELECT MA + ' - '+ TEN FROM ERP_SANPHAM WHERE PK_SEQ = "+sanpham+") DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU,3 STT, 2 SAPXEP \n"+
					" FROM ERP_GiamGiaHangMua \n"+
					" WHERE PK_SEQ = '"+id+"' \n";
				}
			}
		}
		rsSp.close();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		if(query.trim().length()<=0){
			laytk = "SELECT NO_CO, '' PK_SEQ, '' NGAYHOADON, '' SOTIEN, '' SOHIEUTAIKHOAN, '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 1 STT, 1 SAPXEP \n " +
					"FROM ERP_THUENHAPKHAU \n " +
					"WHERE PK_SEQ = '"+id+"'";
		}
		else{
			laytk += " ORDER BY PK_SEQ, STT, SAPXEP \n";
		}
		return laytk;
	}
	public void DbClose() 
	{
		try 
		{
			if (this.hienthiList != null)
				this.hienthiList.clear(); 
			if(this.giamgiaRs != null)
				this.giamgiaRs.close();
			this.db.shutDown();
		} 
		catch (SQLException e) {}
	}

	public ResultSet getGiamgiaRs() 
	{
		return this.giamgiaRs;
	}

	public void setGiamgiaRs(ResultSet giamgiaRs) 
	{
		this.giamgiaRs = giamgiaRs;
	}

	public String getCongtyId() 
	{
		return this.congtyId;
	}

	public void setCongtyId(String congtyId) 
	{
		this.congtyId = congtyId;
	}

	public List<IThongTinHienThi> getHienthiList() {
		
		return this.hienthiList;
	}

	public void setHienthiList(List<IThongTinHienThi> hienthiList) {
		
		this.hienthiList = hienthiList;
	}

}
