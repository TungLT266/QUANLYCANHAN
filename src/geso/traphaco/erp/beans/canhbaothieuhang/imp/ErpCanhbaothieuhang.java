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
import geso.traphaco.erp.beans.canhbaothieuhang.IErpCanhbaothieuhang;

public class ErpCanhbaothieuhang implements IErpCanhbaothieuhang 
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
	String tungay = "";
	String denngay = "";
	
	String spMa;
	String maKH;
	String So;
	String quyCach;
	
	String msg;
	String viewMode;
	
	ResultSet canhbaoRs;
	List<ICanhbao> canhbaoList;
	
	dbutils db;
	
	public ErpCanhbaothieuhang()
	{
		this.userId = "";

		this.thangId = "";
		this.namId = "";
		this.loaispId = "";
		this.spId = "";
		this.msg = "";
		this.viewMode = "0";  //0 view Detail, 1 view Total
		this.tungay = "";
		this.denngay = "";
		
		this.db = new dbutils();
		this.canhbaoList = new ArrayList<ICanhbao>();
	}
	

	public String getTuNgay() {
		
		return this.tungay;
	}

	
	public void setTuNgay(String tungay) {
		
		this.tungay = tungay;
	}

	
	public String getDenNgay() {
		
		return this.denngay;
	}

	
	public void setDenNgay(String denngay) {
		
		this.denngay = denngay;
	}
	
	
	public String getMaKH() {
		return maKH!=null?maKH:"";
	}

	public void setMaKH(String maKH) {
		this.maKH = maKH;
	}

	public String getSo() {
		return So!=null?So:"";
	}

	public void setSo(String so) {
		So = so;
	}
	
	public String getSpMa() {
		return this.spMa!=null?this.spMa:"";
	}

	public void setSpMa(String spMa) {
		this.spMa = spMa;
	}
	

	public String getQuyCach() {
		return quyCach!=null?quyCach:"";
	}

	public void setQuyCach(String quyCach) {
		this.quyCach = quyCach;
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
		
		if(viewMode.equals("0")) //Xem chi tiet
		{
			query = " SELECT '1.INV' AS ID, '-1' AS chungtu, '' AS khMa,  '' as khTen, SP.MA as spMa, SP.PK_SEQ AS SPID, SP.TEN + ' ' + SP.QUYCACH as spTen, '" + getDateTime() + "' AS NGAY, SUM(KHO_SP.AVAILABLE) AS SoLuong      " +
					" FROM ERP_KHOTT_SANPHAM KHO_SP    " +
						" INNER JOIN ERP_SANPHAM SP on KHO_SP.SANPHAM_FK = SP.PK_SEQ    " +
					" WHERE KHO_SP.SANPHAM_FK in ( select pk_seq from ERP_SANPHAM where LOAISANPHAM_FK in ( '100005', '100006', '100007') ) " +
					"  AND SP.MA LIKE '%" + this.getSpMa() + "%'" +
					"  AND SP.QUYCACH LIKE '%" + this.getQuyCach() + "%'"+
					" GROUP BY	SP.PK_SEQ, SP.MA,	SP.TEN,	SP.QUYCACH   " +
				" union " +
					" select '3.SO' as ID, a.PK_SEQ as soId, d.Ma as khMa, d.Ten AS khTen, c.MA as spMa, c.PK_SEQ AS spId, c.TEN + ' ' + c.QUYCACH as spTen, b.ngaydukiengiao, b.SOLUONG  " +
					" from ERP_DONDATHANG a inner join ERP_DONDATHANG_SP b on a.PK_SEQ = b.DONDATHANG_FK  " +
						" inner join ERP_SANPHAM c on b.SANPHAM_FK = c.PK_SEQ  " +
						" inner join ERP_KHACHHANG d on a.KHACHHANG_FK = d.PK_SEQ  " +
					" where a.TRANGTHAI <= 3"+// and b.ngaydukiengiao >= '" + getDateTime() + "'  ";
					String.format("  AND c.MA LIKE '%%%s%%'",this.getSpMa())+
					String.format("  AND d.MA LIKE '%%%s%%'",this.getMaKH())+
					String.format("  AND c.QUYCACH LIKE '%%%s%%'",this.getQuyCach());
			if(this.getSo().trim().length()>0)
			{
				query += String.format(" AND a.PK_SEQ LIKE '%%%s%%'", this.getSo().trim());
			}
				if(this.thangId.trim().length() > 0 && this.namId.trim().length() > 0)
				{
					query += " and b.ngaydukiengiao >= '" + this.namId + "-" + this.thangId + "-01' " +
								" and b.ngaydukiengiao <= '" + this.namId + "-" + this.thangId + "-" + LastDayOfMonth(Integer.parseInt(this.thangId), Integer.parseInt(this.namId)) + "' ";
				}else if(this.namId.trim().length() > 0)
				{
					query += "  and b.ngaydukiengiao >= '" + this.namId + "-01-01' " +
					" and b.ngaydukiengiao <= '" + this.namId +"-12-31' ";
				}
				
				if(this.getTuNgay().trim().length()>0)
				{
					query += String.format("  and b.ngaydukiengiao >= '%s'", this.getTuNgay().trim());
				}
				
				if(this.getDenNgay().trim().length()>0)
				{
					query += String.format("  and b.ngaydukiengiao <= '%s'", this.getDenNgay().trim());
				}
			
			/*query += " union  " +
					"select '2.PRO' as ID, a.PK_SEQ as chungtu, '' as khTen, b.PK_SEQ as spId, b.TEN as spTen, a.NGAYDUKIENHT, a.SOLUONG  " +
					"from ERP_LENHSANXUAT a inner join ERP_SANPHAM b on a.SANPHAM_FK = b.PK_SEQ  " +
					"where a.NGAYDUKIENHT >= '" + getDateTime() + "'  ";*/
			query += " union  " +
					" select '2.PRO' as ID, a.PK_SEQ AS chungtu, '' AS khMa,  '' as khTen, c.MA as spMa, c.PK_SEQ AS spId, c.TEN + ' ' + c.QUYCACH as spTen, a.NGAYDUKIENHT, b.SOLUONG   " +
					" from ERP_LENHSANXUAT_GIAY a inner join ERP_LENHSANXUAT_SANPHAM b on a.pk_seq = b.lenhsanxuat_fk   " +
					" inner join ERP_SANPHAM c on b.SANPHAM_FK = c.PK_SEQ    " +
					" where a.trangthai != '4'" +
					String.format("  AND c.MA LIKE '%%%s%%'",this.getSpMa())+
					String.format("  AND c.QUYCACH LIKE '%%%s%%'",this.getQuyCach());// and a.NGAYDUKIENHT >= '" + getDateTime() + "'";	
			if(this.getSo().trim().length()>0)
			{
				query += String.format(" AND a.PK_SEQ LIKE '%%%s%%'", this.getSo().trim());
			}
			if(this.thangId.trim().length() > 0 && this.namId.trim().length() > 0)
			{
				query += "  and a.NGAYDUKIENHT >= '" + this.namId + "-" + this.thangId + "-01' " +
								" and a.NGAYDUKIENHT <= '" + this.namId + "-" + this.thangId + "-" + LastDayOfMonth(Integer.parseInt(this.thangId), Integer.parseInt(this.namId)) + "' ";
			}
			else if(this.namId.trim().length() > 0)
			{
				query += "  and a.NGAYDUKIENHT >= '" + this.namId + "-01-01' " +
				" and a.NGAYDUKIENHT <= '" + this.namId +"-12-31' ";
			}
			
			if(this.getTuNgay().trim().length()>0)
			{
				query += String.format("  and a.NGAYDUKIENHT >= '%s'", this.getTuNgay().trim());
			}
			
			if(this.getDenNgay().trim().length()>0)
			{
				query += String.format("  and a.NGAYDUKIENHT <= '%s'", this.getDenNgay().trim());
			}
			
			query += " order by SPID ASC, NGAY ASC, ID ASC";
			
			System.out.println("1.Canh bao thieu hang: " + query);
			
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
							//System.out.println("1.INV: Ton dau: " + tondau);
						}
						else
						{
							if(rsCanhbao.getString("ID").equals("2.PRO"))
							{
								tondau += rsCanhbao.getDouble("SoLuong");
								//System.out.println("3.PRO: Ton dau: " + tondau);
							}
							else
							{
								if(rsCanhbao.getString("ID").equals("3.SO"))
								{
									tondau -= rsCanhbao.getDouble("SoLuong");
									//System.out.println("2.SO: Ton dau: " + tondau);
									
									if(tondau < 0)
									{
										ICanhbao canhbao = new Canhbao();
										canhbao.setChungtu(rsCanhbao.getString("chungtu"));
										canhbao.setMaKH(rsCanhbao.getString("khMa"));
										canhbao.setKhachhang(rsCanhbao.getString("khTen"));
										canhbao.setSanPhamMa(rsCanhbao.getString("spMa"));
										canhbao.setSanphamId(rsCanhbao.getString("spId"));
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
					System.out.println("__Exception: " + e.getMessage());
				}
				
			}
		}
		else  //Xem tong the, cho phep tao lenh san xuat hoac chinh sua ngay gio
		{
			query = "SELECT '1.INV' AS ID, SP.MA as spMa, SP.PK_SEQ AS SPID, SP.TEN + ' ' + SP.QUYCACH as spTen, '' AS NGAY, SUM(KHO_SP.AVAILABLE) AS SoLuong       " +
					"FROM ERP_KHOTT_SANPHAM KHO_SP      " +
					"INNER JOIN ERP_SANPHAM SP on KHO_SP.SANPHAM_FK = SP.PK_SEQ      " +
					"WHERE KHO_SP.SANPHAM_FK in ( select pk_seq from ERP_SANPHAM where LOAISANPHAM_FK in ( '100005', '100006', '100007') )   " +
					String.format("  AND SP.MA LIKE '%%%s%%'",this.getSpMa())+
					"GROUP BY	SP.PK_SEQ, SP.MA,	SP.TEN,	SP.QUYCACH     " +
					
				"union   " +
					"select '3.SO' as ID, c.MA as spMa, c.PK_SEQ AS spId, c.TEN + ' ' + c.QUYCACH as spTen, b.ngaydukiengiao, sum(b.SOLUONG) as soluong    " +
					"from ERP_DONDATHANG a inner join ERP_DONDATHANG_SP b on a.PK_SEQ = b.DONDATHANG_FK    " +
						"inner join ERP_SANPHAM c on b.SANPHAM_FK = c.PK_SEQ    " +
					"where a.TRANGTHAI <= 3" +
					String.format("  AND c.MA LIKE '%%%s%%'",this.getSpMa()); //and b.ngaydukiengiao >= '" + this.getDateTime() + "'  ";
			
			if(this.getSo().trim().length()>0)
			{
				query += String.format(" AND a.PK_SEQ LIKE '%%%s%%'", this.getSo().trim());
			}
			if(this.thangId.trim().length() > 0 && this.namId.trim().length() > 0)
			{
				query += "  and b.ngaydukiengiao >= '" + this.namId + "-" + this.thangId + "-01' " +
								" and b.ngaydukiengiao <= '" + this.namId + "-" + this.thangId + "-" + LastDayOfMonth(Integer.parseInt(this.thangId), Integer.parseInt(this.namId)) + "' ";
			}else if(this.namId.trim().length() > 0)
			{
				query += "  and b.ngaydukiengiao >= '" + this.namId + "-01-01' " +
				" and b.ngaydukiengiao <= '" + this.namId +"-12-31' ";
			}
			
			
			if(this.getTuNgay().trim().length()>0)
			{
				query += String.format("  and b.ngaydukiengiao >= '%s' ", this.getTuNgay().trim());
			}
			
			if(this.getDenNgay().trim().length()>0)
			{
				query += String.format("  and b.ngaydukiengiao <= '%s' ", this.getDenNgay().trim());
			}
			
			
			query += " group by c.PK_SEQ, c.MA , c.TEN, c.QUYCACH, b.ngaydukiengiao " +
				" union    " +
					" select '2.PRO' as ID, c.MA as spMa, c.PK_SEQ AS spId, c.TEN as spTen, a.NGAYDUKIENHT, sum(b.SOLUONG) as SoLuong   " +
					" from ERP_LENHSANXUAT_GIAY a inner join ERP_LENHSANXUAT_SANPHAM b on a.pk_seq = b.lenhsanxuat_fk  " +
					" inner join ERP_SANPHAM c on b.SANPHAM_FK = c.PK_SEQ   " +
					" where a.trangthai != 4 " +
					String.format("  AND c.MA LIKE '%%%s%%'",this.getSpMa());//and a.NGAYDUKIENHT >= '" + this.getDateTime() + "'  ";
			
			if(this.thangId.trim().length() > 0 && this.namId.trim().length() > 0)
			{
				query += "  and a.NGAYDUKIENHT >= '" + this.namId + "-" + this.thangId + "-01' " +
							" and a.NGAYDUKIENHT <= '" + this.namId + "-" + this.thangId + "-" + LastDayOfMonth(Integer.parseInt(this.thangId), Integer.parseInt(this.namId)) + "' ";
			}else if(this.namId.trim().length() > 0)
			{
				query += "  and a.NGAYDUKIENHT >= '" + this.namId + "-01-01' " +
				" and a.NGAYDUKIENHT <= '" + this.namId +"-12-31' ";
			}
			
			if(this.getTuNgay().trim().length()>0)
			{
				query += String.format("  and a.NGAYDUKIENHT >= '%s'", this.getTuNgay().trim());
			}
			
			if(this.getDenNgay().trim().length()>0)
			{
				query += String.format("  and a.NGAYDUKIENHT <= '%s'", this.getDenNgay().trim());
			}
			
			query += " group by c.PK_SEQ, c.MA, c.TEN, c.QUYCACH, a.NGAYDUKIENHT  " +
				" order by SPID ASC, NGAY ASC, ID ASC";
			
			System.out.println("1.Canh bao thieu hang tong quan: " + query);
			
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
							//System.out.println("1.INV: Ton dau: " + tondau);
						}
						else
						{
							if(rsCanhbao.getString("ID").equals("2.PRO"))
							{
								tondau += rsCanhbao.getDouble("SoLuong");
								//System.out.println("3.PRO: Ton dau: " + tondau);
							}
							else
							{
								if(rsCanhbao.getString("ID").equals("3.SO"))
								{
									tondau -= rsCanhbao.getDouble("SoLuong");
									//System.out.println("2.SO: Ton dau: " + tondau);
									
									if(tondau < 0)
									{
										ICanhbao canhbao = new Canhbao();
										//canhbao.setChungtu(rsCanhbao.getString("chungtu"));
										//canhbao.setKhachhang(rsCanhbao.getString("khTen"));
										canhbao.setSanPhamMa(rsCanhbao.getString("spMa"));
										canhbao.setSanphamId(rsCanhbao.getString("spId"));
										canhbao.setSanpham(rsCanhbao.getString("spTen"));
										canhbao.setNgaygiao(rsCanhbao.getString("ngay"));
										canhbao.setSoluonggiao(formater.format(rsCanhbao.getDouble("soluong")));
										
										double soluongThieu = Math.min(rsCanhbao.getDouble("SoLuong"), Math.abs(tondau));
										canhbao.setThieu(formater.format(soluongThieu));
										
										/*query = "select  a.PK_SEQ as chungtu, a.NGAYDUKIENHT, a.SOLUONG   " +
												"from ERP_LENHSANXUAT a inner join ERP_SANPHAM b on a.SANPHAM_FK = b.PK_SEQ   " +
												"where a.NGAYDUKIENHT >= '" + rsCanhbao.getString("ngay") + "'  and a.SANPHAM_FK = '" + rsCanhbao.getString("SPID") + "'";*/
//										
//										query = " select  a.PK_SEQ AS chungtu, '' AS khMa,  a.NGAYDUKIENHT, b.SOLUONG    " +
//												" from ERP_LENHSANXUAT_GIAY a inner join ERP_LENHSANXUAT_SANPHAM b on a.PK_SEQ = b.LENHSANXUAT_FK  " +
//													" inner join ERP_SANPHAM c on b.SANPHAM_FK = c.PK_SEQ    " +
//												" where a.NGAYDUKIENHT >= '" + rsCanhbao.getString("ngay") + "'  and b.SANPHAM_FK = '" + rsCanhbao.getString("SPID") + "' " +
//											//	String.format(" AND A.PK_SEQ = '%s'", this.getspIds()) +
//												" order by a.NGAYDUKIENHT asc ";
//										
//										System.out.println("__Lay LSX: " + query);
//										ResultSet rsLsx = db.getScrol(query);
//										if(rsLsx != null)
//										{
//											canhbao.setLsxRs(rsLsx);
//										}
										
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
					System.out.println("__Exception canh bao: " + e.getMessage());
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
			if(this.thangRs != null)
				this.thangRs.close();
			if(this.namRs != null)
				this.namRs.close();
			if(this.loaispRs != null)
				this.loaispRs.close();
			if(this.sanphamRs != null)
				this.sanphamRs.close();
			if(this.canhbaoRs != null)
				this.canhbaoRs.close();
			this.db.shutDown();
		} 
		catch (SQLException e) {
			e.printStackTrace();
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
	

}
