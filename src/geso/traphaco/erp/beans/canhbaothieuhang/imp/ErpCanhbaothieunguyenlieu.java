package geso.traphaco.erp.beans.canhbaothieuhang.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.beans.canhbaothieuhang.ICanhbao;
import geso.traphaco.erp.beans.canhbaothieuhang.IErpCanhbaothieunguyenlieu;

public class ErpCanhbaothieunguyenlieu implements IErpCanhbaothieunguyenlieu
{
	String userId;
	String congtyId;
	
	ResultSet thangRs;
	String thangId;
	ResultSet namRs;
	String namId;
	ResultSet loaispRs;
	String loaispId;
	ResultSet sanphamRs;
	String spId;
	String viewMode;
	
	String msg;
	
	ResultSet nccRs;
	ResultSet canhbaoRs;
	List<ICanhbao> canhbaoList;
	
	dbutils db;
	
	public ErpCanhbaothieunguyenlieu()
	{
		this.userId = "";

		this.thangId = "";
		this.namId = "";
		this.loaispId = "";
		this.spId = "";
		this.msg = "";
		
		this.viewMode = "1"; 
		
		this.db = new dbutils();
		this.canhbaoList = new ArrayList<ICanhbao>();
	}
	
	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;	
	}

	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	public void init() 
	{
		String sqlNCC = "select PK_SEQ as nccId, MA + ' - ' + TEN as nccTen " +
						"from ERP_NHACUNGCAP where TRANGTHAI = '1'  and duyet = '1'\n" +
						"and PK_SEQ in ( select ncc_fk from NHANVIEN_NHACUNGCAP where nhanvien_fk = '" + this.userId + "' )";
		this.nccRs = db.getScrol(sqlNCC);

		String query = "";
		
		for(int i = 1; i <= 12; i++)
		{
			query += " select '" + ( i < 10 ? "0" + Integer.toString(i) : Integer.toString(i) ) + "' as thangId, N'Tháng " + Integer.toString(i) + "' as thangTen ";
			if(i < 12)
				query += " union ";
		}
		
		this.thangRs = db.get(query);
		//if(this.thangId.trim().length() <= 0)
			//this.thangId = this.getDateTime().split("-")[1];
		
		
		query = "";
		int nam = Integer.parseInt(this.getDateTime().split("-")[0]);
		
		for(int i = nam; i <= nam + 3; i++)
		{
			query += " select '" + Integer.toString(i) + "' as namId, '" + Integer.toString(i) + "' as namTen ";
			if(i < nam + 3)
				query += " union ";
		}
		
		this.namRs = db.get(query);
		if(this.namId.trim().length() <= 0)
			this.namId = this.getDateTime().split("-")[0];
		
		
		//Canh bao thieu nguyen lieu
		
		if(viewMode.equals("0"))
		{
			query = "SELECT '1.INV' AS ID, '-1' as chungtu, SP.PK_SEQ AS SPID, SP.TEN + ' ' + SP.QUYCACH as spTen, " +
					"'" + getDateTime() + "' AS NGAY, SUM(KHO_SP.AVAILABLE) AS SoLuong     " +
					"FROM ERP_KHOTT_SANPHAM KHO_SP    " +
					"INNER JOIN ERP_SANPHAM SP on KHO_SP.SANPHAM_FK = SP.PK_SEQ    " +
					"WHERE KHO_SP.SANPHAM_FK in ( select pk_seq from ERP_SANPHAM " +
					"where LOAISANPHAM_FK in ( '100039', '100046' ) ) " +
					"GROUP BY SP.PK_SEQ, SP.TEN, SP.QUYCACH   " +
				
					"union " +
					"select '2.PO' as ID, a.PK_SEQ as soId, c.PK_SEQ as spId, c.TEN + ' ' + c.QuyCach as spTen, b.NGAYNHAN,  " +
					"b.SOLUONG - ISNULL(  ( select SUM(ERP_NHANHANG_SANPHAM.SOLUONGNHAN)   " +
					"						from ERP_NHANHANG inner join ERP_NHANHANG_SANPHAM on ERP_NHANHANG.PK_SEQ = ERP_NHANHANG_SANPHAM.NHANHANG_FK  " +
					"						where TRANGTHAI in (1, 2) and ERP_NHANHANG.MUAHANG_FK = a.PK_SEQ  " +
					"						and ERP_NHANHANG_SANPHAM.SANPHAM_FK = b.SANPHAM_FK and ERP_NHANHANG_SANPHAM.NGAYNHANDUKIEN = b.NGAYNHAN ), 0 ) as SoLuong  " +
					"from ERP_MUAHANG a " +
					"inner join ERP_MUAHANG_SP b on a.PK_SEQ = b.MUAHANG_FK    " +
					"inner join ERP_SANPHAM c on b.SANPHAM_FK = c.PK_SEQ  " +
					"inner join ERP_LOAISANPHAM lsp on lsp.PK_SEQ = c.LOAISANPHAM_FK " +
					"where c.LOAISANPHAM_FK in ('100039', '100046') " +
					"and b.NGAYNHAN >= '" + this.getDateTime() + "'   ";
			
			if(this.thangId.trim().length() > 0 && this.namId.trim().length() > 0)
			{
				query += "  and b.NGAYNHAN >= '" + this.namId + "-" + this.thangId + "-01' " +
							"and b.NGAYNHAN <= '" + this.namId + "-" + this.thangId + "-" + LastDayOfMonth(Integer.parseInt(this.thangId), Integer.parseInt(this.namId)) + "' ";
			}
			
			query += " union " +
					 " select '3.PRO' as ID, LSX.PK_SEQ as chungtu, SP.PK_SEQ as spId, SP.TEN as spTen,  " +
					 " LSX.NGAYDUKIENHT, DM_VT.SOLUONG * LSX.SOLUONG / DM.SOLUONGCHUAN as soluong  " +
					 " from ERP_LENHSANXUAT_GIAY LSX " +
					 " inner join ERP_LENHSANXUAT_SANPHAM LSX_SP on LSX.PK_SEQ = LSX_SP.Lenhsanxuat_fk  " +
					 " inner join ERP_DANHMUCVATTU_VATTU DM_VT on b.DanhMucVT_FK = DM_VT.DANHMUCVT_FK  " +
					 " inner join ERP_SANPHAM SP on DM_VT.VATTU_FK = SP.PK_SEQ  " +
					 " inner join ERP_DANHMUCVATTU DM on DM_VT.DANHMUCVT_FK = DM.PK_SEQ   " +
					 " where LSX.TRANGTHAI != 7 AND LSX.NGAYDUKIENHT >= '" + getDateTime() + "'  ";
			
			if(this.thangId.trim().length() > 0 && this.namId.trim().length() > 0)
			{
				query += " and LSX.NGAYDUKIENHT >= '" + this.namId + "-" + this.thangId + "-01' " +
						 " and LSX.NGAYDUKIENHT <= '" + this.namId + "-" + this.thangId + "-" + LastDayOfMonth(Integer.parseInt(this.thangId), Integer.parseInt(this.namId)) + "' ";
			}
					
			
			query += "order by SPID ASC, NGAY ASC, ID ASC";
			
			System.out.println("1.Canh bao thieu NL CHI TIET: " + query);
			
			ResultSet rsCanhbao = db.get(query);
			if(rsCanhbao != null)
			{
				try 
				{
					List<ICanhbao> canhbaoList = new ArrayList<ICanhbao>();
					double tondau = 0;
					
					NumberFormat formater = new DecimalFormat("#,###,###");
					
					while(rsCanhbao.next())
					{
						//System.out.println("ID: " + rsCanhbao.getString("ID") + " - So Luong: " + rsCanhbao.getDouble("SoLuong") ) ;
						if(rsCanhbao.getString("ID").equals("1.INV"))
						{
							tondau = rsCanhbao.getDouble("SoLuong");
							//System.out.println("1.INV: San pham: " + rsCanhbao.getString("spId") + " -- Ton dau: " + tondau);
						}
						else
						{
							if(rsCanhbao.getString("ID").equals("2.PO"))
							{
								tondau += rsCanhbao.getDouble("SoLuong");
								//System.out.println("3.PRO: Ton dau: " + tondau);
							}
							else
							{
								if(rsCanhbao.getString("ID").equals("3.PRO"))
								{
									tondau -= rsCanhbao.getDouble("SoLuong");
									//System.out.println("2.SO: Ton dau: " + tondau);
									
									if(tondau < 0)
									{
										ICanhbao canhbao = new Canhbao();
										canhbao.setChungtu(rsCanhbao.getString("chungtu"));
										//canhbao.setKhachhang(rsCanhbao.getString("khTen"));
										canhbao.setSanpham(rsCanhbao.getString("spTen"));
										canhbao.setNgaygiao(rsCanhbao.getString("ngay"));
										canhbao.setSoluonggiao(formater.format(rsCanhbao.getDouble("soluong")));
										
										double soluongThieu = Math.min(rsCanhbao.getDouble("SoLuong"), Math.abs(tondau));
										canhbao.setThieu(formater.format(soluongThieu));
										
										canhbaoList.add(canhbao);
									}
								}
							}
						}
					}
					
					this.canhbaoList = canhbaoList;
					rsCanhbao.close();
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		}
		else
		{
			query = "SELECT '1.INV' AS ID, SP.MA as SPID, '" + getDateTime() + "' AS NGAY, SUM(KHO_SP.AVAILABLE) AS SoLuong      " +
					"FROM ERP_KHOTT_SANPHAM KHO_SP     " +
					"INNER JOIN ERP_SANPHAM SP on KHO_SP.SANPHAM_FK = SP.PK_SEQ     " +
					"WHERE KHO_SP.SANPHAM_FK in ( select pk_seq from ERP_SANPHAM" +
					" 							  where LOAISANPHAM_FK in ('100039', '100046') )   " +
					"GROUP BY SP.MA    " +
					
					"union  " +  
					
					"select '2.PO' as ID, c.MA as SPID, b.NGAYNHAN, sum(b.SOLUONG - ISNULL(nhanhang.SOLUONGNHAN, 0 ) ) as SoLuong    " +
					"from ERP_MUAHANG a inner join ERP_MUAHANG_SP b on a.PK_SEQ = b.MUAHANG_FK     " +
					"inner join ERP_SANPHAM c on b.SANPHAM_FK = c.PK_SEQ    " +
					"inner join ERP_LOAISANPHAM lsp on lsp.PK_SEQ = c.LOAISANPHAM_FK " +
					"left join " +
					"( " +
						"select a.MUAHANG_FK, b.NGAYNHANDUKIEN, b.SANPHAM_FK, b.SOLUONGNHAN   " +
						"from ERP_NHANHANG a inner join ERP_NHANHANG_SANPHAM b on a.PK_SEQ = b.NHANHANG_FK   " +
						"where TRANGTHAI in (1, 2) and b.SANPHAM_FK is not null and b.NGAYNHANDUKIEN >= '" + getDateTime() + "'  " +
					") " +
					"nhanhang on a.PK_SEQ = nhanhang.MUAHANG_FK and c.PK_SEQ = nhanhang.SANPHAM_FK   " +
					"where c.LOAISANPHAM_FK in ('100039', '100046') and b.NGAYNHAN >= '" + getDateTime() + "'   ";
			
			if(this.thangId.trim().length() > 0 && this.namId.trim().length() > 0)
			{
				query += "  and b.NGAYNHAN >= '" + this.namId + "-" + this.thangId + "-01' " +
							"and b.NGAYNHAN <= '" + this.namId + "-" + this.thangId + "-" + LastDayOfMonth(Integer.parseInt(this.thangId), Integer.parseInt(this.namId)) + "' ";
			}
			
			query += "group by c.MA, b.NGAYNHAN  " ;
			
			query += " union " +
			 		 " select '3.PRO' as ID, SP.MA as SPID, LSX.NGAYDUKIENHT AS NGAY,  " +
			 		 "  DM_VT.SOLUONG * LSX.SOLUONG / DM.SOLUONGCHUAN as soluong  " +
			 		 " from ERP_LENHSANXUAT_GIAY LSX " +
			 		 " inner join ERP_LENHSANXUAT_SANPHAM LSX_SP on LSX.PK_SEQ = LSX_SP.Lenhsanxuat_fk  " +
			 		 " inner join ERP_DANHMUCVATTU_VATTU DM_VT on LSX_SP.DanhMucVT_FK = DM_VT.DANHMUCVT_FK  " +
			 		 " inner join ERP_SANPHAM SP on DM_VT.VATTU_FK = SP.PK_SEQ  " +
			 		 " inner join ERP_DANHMUCVATTU DM on DM_VT.DANHMUCVT_FK = DM.PK_SEQ   " +
			 		 " where LSX.TRANGTHAI != 7 AND LSX.NGAYDUKIENHT >= '" + getDateTime() + "'  ";
	
			if(this.thangId.trim().length() > 0 && this.namId.trim().length() > 0)
			{
				query += " and LSX.NGAYDUKIENHT >= '" + this.namId + "-" + this.thangId + "-01' " +
				 		 " and LSX.NGAYDUKIENHT <= '" + this.namId + "-" + this.thangId + "-" + LastDayOfMonth(Integer.parseInt(this.thangId), Integer.parseInt(this.namId)) + "' ";
			}

			query +=" group by SP.MA, LSX.NGAYDUKIENHT, DM_VT.SOLUONG, LSX.SOLUONG, DM.SOLUONGCHUAN ";
				
			
			query += " order by SPID ASC, NGAY ASC, ID ASC";
			System.out.println("1.Canh bao thieu NL: " + query);
			
			ResultSet rsCanhbao = db.get(query);
			if(rsCanhbao != null)
			{
				try 
				{
					List<ICanhbao> canhbaoList = new ArrayList<ICanhbao>();
					double tondau = 0;
					
					NumberFormat formater = new DecimalFormat("#,###,###");
					
					while(rsCanhbao.next())
					{
						//System.out.println("ID: " + rsCanhbao.getString("ID") + " - So Luong: " + rsCanhbao.getDouble("SoLuong") ) ;
						if(rsCanhbao.getString("ID").equals("1.INV"))
						{
							tondau = rsCanhbao.getDouble("SoLuong");
							//System.out.println("1.INV: San pham: " + rsCanhbao.getString("spId") + " -- Ton dau: " + tondau);
						}
						else
						{
							if(rsCanhbao.getString("ID").equals("2.PO"))
							{
								tondau += rsCanhbao.getDouble("SoLuong");
								//System.out.println("3.PRO: Ton dau: " + tondau);
							}
							else
							{
								if(rsCanhbao.getString("ID").equals("3.PRO"))
								{
									tondau -= rsCanhbao.getDouble("SoLuong");
									//System.out.println("2.SO: Ton dau: " + tondau);
									
									if(tondau < 0)
									{
										ICanhbao canhbao = new Canhbao();
										//canhbao.setChungtu(rsCanhbao.getString("chungtu"));
										
										canhbao.setSanphamId(rsCanhbao.getString("spId"));
										canhbao.setSanpham(rsCanhbao.getString("spId"));
										canhbao.setNgaygiao(rsCanhbao.getString("ngay"));
										canhbao.setSoluonggiao(formater.format(rsCanhbao.getDouble("soluong")));
										
										double soluongThieu = Math.min(rsCanhbao.getDouble("SoLuong"), Math.abs(tondau));
										canhbao.setThieu(formater.format(soluongThieu));
										
																			
										query = "select a.PK_SEQ as chungtu, b.NGAYNHAN, sum(b.SOLUONG - ISNULL(nhanhang.SOLUONGNHAN, 0 ) ) as SoLuong   " +
												"from ERP_MUAHANG a inner join ERP_MUAHANG_SP b on a.PK_SEQ = b.MUAHANG_FK  " +
												"inner join ERP_SANPHAM c on b.sanpham_fk = c.pk_seq   " +
												"inner join ERP_LOAISANPHAM lsp on lsp.PK_SEQ = c.LOAISANPHAM_FK " +
												"left join ( " +
												"		select a.MUAHANG_FK, b.NGAYNHANDUKIEN, b.SANPHAM_FK, b.SOLUONGNHAN   from ERP_NHANHANG a  " +
												"		inner join ERP_NHANHANG_SANPHAM b on a.PK_SEQ = b.NHANHANG_FK    " +
												"		where TRANGTHAI in (1, 2) and b.SANPHAM_FK is not null  " +
												") nhanhang on a.PK_SEQ = nhanhang.MUAHANG_FK and c.PK_SEQ = nhanhang.SANPHAM_FK  " +
												"where c.LOAISANPHAM_FK in ('100039', '100046') " +
												"and c.ma = '" + rsCanhbao.getString("spId") + "'  " +
												"group by a.PK_SEQ, b.NGAYNHAN " +
												"HAVING sum(b.SOLUONG - ISNULL(nhanhang.SOLUONGNHAN, 0 ) ) > 0 " +
												"order by b.NGAYNHAN asc ";
										System.out.println("____LAY DANH SACH PO: " + query);
										
										ResultSet rsLsx = db.getScrol(query);
										if(rsLsx != null)
										{
											canhbao.setLsxRs(rsLsx);
										}
										
										canhbaoList.add(canhbao);
									}
								}
							}
						}
					}
					
					this.canhbaoList = canhbaoList;
					rsCanhbao.close();
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
				
			}
		}
		
	}

	public void DbClose() 
	{
		try 
		{
			if (this.canhbaoList != null)
				this.canhbaoList.clear();
			if (this.thangRs != null)
				this.thangRs.close();
			if (this.namRs != null)
				this.thangRs.close();
			if (this.loaispRs != null)
				this.thangRs.close();
			if (this.sanphamRs != null)
				this.thangRs.close();
			if (this.nccRs != null)
				this.thangRs.close();
			if(this.canhbaoRs != null)
				this.canhbaoRs.close();
			
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally
		{
			System.out.println("CLOSED");
			if(this.db!=null)
			this.db.shutDown();
		}
	}

	public String getCongtyId() 
	{
		return this.congtyId;
	}

	public void setCongtyId(String congtyId) 
	{
		this.congtyId = congtyId;
	}

	
	public ResultSet getThangRs() {
		
		return this.thangRs;
	}

	
	public void setThangRs(ResultSet thangRs) {
		
		this.thangRs = thangRs;
	}

	
	public String getThangId() {
		
		return this.thangId;
	}

	
	public void setThangId(String thangId) {
		
		this.thangId = thangId;
	}

	
	public ResultSet getNamRs() {
		
		return this.namRs;
	}

	
	public void setNamRs(ResultSet namRs) {
		
		this.namRs = namRs;
	}

	
	public String getNamId() {
		
		return this.namId;
	}

	
	public void setNamId(String namId) {
		
		this.namId = namId;
	}

	
	public ResultSet getLoaispRs() {
		
		return this.loaispRs;
	}

	
	public void setLoaispRs(ResultSet loaispRs) {
		
		this.loaispRs = loaispRs;
	}

	
	public String getLoaispId() {
		
		return this.loaispId;
	}

	
	public void setLoaiId(String loaispId) {
		
		this.loaispId = loaispId;
	}

	
	public ResultSet getSanphamRs() {
		
		return this.sanphamRs;
	}

	
	public void setSanphamRs(ResultSet spRs) {
		
		this.sanphamRs = spRs;
	}

	
	public String getspIds() {
		
		return this.spId;
	}

	
	public void setspIds(String spIds) {
		
		this.spId = spIds;
	}

	
	public ResultSet getCanhbaoRs() {
		
		return this.canhbaoRs;
	}

	
	public void setCanhbaoRs(ResultSet canhbaoRs) {
		
		this.canhbaoRs = canhbaoRs;
	}

	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	String LastDayOfMonth(int month, int year)
    {
        String ngay = "";
        switch (month)
        {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                {
                    ngay = "31";
                    break;
                }
            case 4:
            case 6:
            case 9:
            case 11:
                {
                    ngay = "30";
                    break;
                }
            case 2:
                {
                    if (year % 4 == 0)
                        ngay = "29";
                    else
                        ngay = "28";
                    break;
                }
        }

        return ngay;
    }
	
	public void setcanhbaoList(List<ICanhbao> cbList) {
		
		this.canhbaoList = cbList;
	}

	public List<ICanhbao> getCanhbaoList() {
		
		return this.canhbaoList;
	}
	
	public String getViewMode() 
	{
		return this.viewMode;
	}

	public void setViewMode(String viewMode) 
	{
		this.viewMode = viewMode;
	}

	public ResultSet getNccRs()
	{
		return this.nccRs;
	}

	public void setNccRs(ResultSet nccRs) 
	{
		this.nccRs = nccRs;
	}
	

}
