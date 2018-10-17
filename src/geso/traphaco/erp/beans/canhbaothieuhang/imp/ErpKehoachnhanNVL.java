package geso.traphaco.erp.beans.canhbaothieuhang.imp;

import geso.traphaco.erp.beans.canhbaothieuhang.IErpKehoachnhanNVL;
import geso.traphaco.erp.db.sql.dbutils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ErpKehoachnhanNVL implements IErpKehoachnhanNVL
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
	String tungay;
	String denngay;
	String po;
	String ncc;
	String quyCach;
	String ngayNhan;
	
	String msg;
	
	ResultSet canhbaoRs;
	
	dbutils db;
	
	public String getPo() {
		return po!=null?po:"";
	}

	public void setPo(String po) {
		this.po = po;
	}

	public String getNcc() {
		return ncc!=null?ncc:"";
	}

	public void setNcc(String ncc) {
		this.ncc = ncc;
	}

	public String getQuyCach() {
		return quyCach!=null?quyCach:"";
	}

	public void setQuyCach(String quyCach) {
		this.quyCach = quyCach;
	}

	public String getNgayNhan() {
		return ngayNhan!=null?ngayNhan:"";
	}

	public void setNgayNhan(String ngayNhan) {
		this.ngayNhan = ngayNhan;
	}

	public ErpKehoachnhanNVL()
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
		
		
		//Ke hoach nhan NVL
		query ="SELECT X.PO, X.nccTen, X.spMa,X.ktra ,X.spTen,X.NGAYNHAN, SUM(X.SOLUONG) AS SOLUONG" +
				" FROM" +
				" ( " +
				" select a.PREFIX + CAST(a.PK_SEQ AS VARCHAR) AS PO, d.Ten as nccTen, c.ma as spMa, c.TEN as spTen, b.NGAYNHAN, b.soluong," +
				" case when Y.MUAHANG_FK = a.PK_SEQ or b.NGAYNHAN > GETDATE() then 1 else 0 end as ktra " +
				" " +
				" FROM ERP_MUAHANG a " +
				" left join (select distinct e.MUAHANG_FK from ERP_NHANHANG e  where e.MUAHANG_FK is not null) Y on a.PK_SEQ= Y.MUAHANG_FK" +
				" inner join ERP_MUAHANG_SP b on a.PK_SEQ = b.MUAHANG_FK" +
				" inner join ERP_SANPHAM c on b.SANPHAM_FK = c.PK_SEQ" +
				" inner join ERP_LOAISANPHAM lsp on lsp.PK_SEQ = c.LOAISANPHAM_FK " +
				" inner join ERP_NHACUNGCAP d on a.NHACUNGCAP_FK= d.PK_SEQ" +
				" where  a.trangthai not in (1, 2) ";
		//if(this.thangId.trim().length() > 0 && this.namId.trim().length() > 0)
			//query += "  and b.NGAYNHAN <= '" + this.namId + "-" + this.thangId + "-" + LastDayOfMonth(Integer.parseInt(this.thangId), Integer.parseInt(this.namId)) + "' ";
		
		if(this.tungay.trim().length() > 0)
		{
			query += " and b.NGAYNHAN >= '" + this.tungay + "' ";
		}
//		else //neu nguoi dung khong go gi thi search tu ngay hien tai
//		{
//			query += " and b.NGAYNHAN >= '" +this.getDateTime()  + "' ";
//			
//		}
		
		if(this.denngay.trim().length() > 0)
			query += " and b.NGAYNHAN <= '" + this.denngay + "' ";
		
		if(this.getNcc().trim().length()>0)
		{
			query += String.format("AND d.TEN LIKE N'%%%s%%'", this.getNcc());
		}
		
		if(this.getspIds().trim().length()>0)
		{
			query += String.format("AND c.MA LIKE N'%%%s%%'", this.getspIds());
		}
			
		
		query += " )X" ;
		if (this.getPo().trim().length()>0) {
			query += String.format(" WHERE X.PO LIKE '%%%s%%'", this.getPo());
		}
		
		query +=" group by X.PO,X.nccTen,X.spMa, X.spMa, X.spTen,X.ktra , X.NGAYNHAN" +
				" order by X.NgayNhan desc ";
		System.out.println("SQL:" + query);
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
		
		return this.spId!=null?spId:"";
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
