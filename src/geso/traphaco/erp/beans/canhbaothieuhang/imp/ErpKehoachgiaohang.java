package geso.traphaco.erp.beans.canhbaothieuhang.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.beans.canhbaothieuhang.IErpKehoachgiaohang;

public class ErpKehoachgiaohang implements IErpKehoachgiaohang 
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
	
	
	String msg;
	String tungay = "";
	String denngay = "";



	String maKH;
	String So;
	String quyCach;
	
	ResultSet canhbaoRs;
	
	dbutils db;
	
	public ErpKehoachgiaohang()
	{
		this.userId = "";

		this.thangId = "";
		this.namId = "";
		this.loaispId = "";
		this.spId = "";
		this.msg = "";
		
		this.tungay = "";
		this.denngay = "";
		
		this.db = new dbutils();
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
		
		
		//Ke hoach giao hang
		query = " SELECT X.SO,X.khMa,X.khTen,X.spMa,X.spTen,X.ngaydukiengiao,SUM(X.SOLUONG) as SOLUONG " +
				"\n FROM" +
				"\n ( " +
				"\n select a.PREFIX + cast(a.PK_SEQ as varchar) as SO,d.Ma AS khMa, d.Ten as khTen, c.MA as spMa, c.TEN + ' ' + c.QUYCACH as spTen, b.ngaydukiengiao, b.SOLUONG " +
				"\n from ERP_DONDATHANG a inner join ERP_DONDATHANG_SP b on a.PK_SEQ = b.DONDATHANG_FK " +
				"\n inner join ERP_SANPHAM c on b.SANPHAM_FK = c.PK_SEQ " +
				"\n inner join ERP_KHACHHANG d on a.KHACHHANG_FK = d.PK_SEQ " +
				"\n where a.TRANGTHAI <= 3 ";
		
		//if(this.thangId.trim().length() > 0 && this.namId.trim().length() > 0)
			//query += "  and b.ngaydukiengiao <= '" + this.namId + "-" + this.thangId + "-" + LastDayOfMonth(Integer.parseInt(this.thangId), Integer.parseInt(this.namId)) + "' ";
		
		if(this.tungay.trim().length() > 0)
			query += "\n and b.ngaydukiengiao >= '" + this.tungay + "' ";
		
		if(this.denngay.trim().length() > 0)
			query += "\n and b.ngaydukiengiao <= '" + this.denngay + "' ";
		
		if(this.getMaKH().trim().length()>0)
		{
			query += String.format("AND d.Ma LIKE N'%%%s%%'", this.getMaKH());
		}
		
		if(this.getspIds().trim().length()>0)
		{
			query += String.format("AND c.MA LIKE N'%%%s%%'", this.getspIds());
		}
		
		if(this.getQuyCach().trim().length()>0)
		{
			query += String.format("AND c.QUYCACH LIKE N'%%%s%%'", this.getQuyCach());
		}
		
		
		query += "\n )X";
		if(this.getSo().trim().length()>0)
		{
			query += String.format("\n WHERE X.SO LIKE '%%%s%%'", this.getSo());	
		}
		
		query += "\n group by X.SO, X.khMa, X.khTen, X.spMa, X.spTen, X.ngaydukiengiao" +
				"\n order by X.ngaydukiengiao asc ";
		
		System.out.println("__KE HOACH GIAO HANG: " + query);
		this.canhbaoRs = db.get(query);
		
	}

	public void DbClose() 
	{
		try 
		{
			if(this.canhbaoRs != null)
				this.canhbaoRs.close();
			this.db.shutDown();
		} 
		catch (SQLException e) {}
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

}
