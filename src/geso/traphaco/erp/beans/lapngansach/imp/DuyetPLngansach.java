package geso.traphaco.erp.beans.lapngansach.imp;

import geso.traphaco.erp.beans.lapngansach.IDuyetPLngansach;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.*;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.aspose.cells.BorderLineType;
import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.Color;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.BorderLineType;
import com.aspose.cells.BorderType;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.PatternType;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;
import com.aspose.cells.Font;
import java.text.DecimalFormat; 
import java.text.NumberFormat;

public class DuyetPLngansach implements IDuyetPLngansach
{
	String userId;
	String Id;
	String trangthai;
	
	String year;
	String month;

	String ngay;
	ResultSet namlist;
	
	String ctyId;
	String cty;
	
	String nam;
	String namtruoc;
	
	String hieuluc;
	
	String msg;
	String view;
	
	dbutils db;
	Utility util;
	
	NumberFormat formatter = new DecimalFormat("#,###,###");
	
	String query_ptDS = "";
	Hashtable<String, String[]> sellingprice_ht = new Hashtable<String, String[]>();
	Hashtable<String, String[]> amount_ht = new Hashtable<String, String[]>();
	
	int row_SALES_VOLUME;
	int row_TOTAL_AMOUNT;
	int row_MATERIALS_CONSUMED;
	int row_DIRECT_LABOUR_LA;
	int row_PRODUCTION_OVERHEAD_LA;
	int row_LA;
	
	int row_PA;
	int row_SALES_VOLUME_PA;
	int row_SELLING_PRICE_NET_PA;
	int row_TOTAL_SALES_AMOUNT_PA;
	int row_SALES_AMOUNT;
	int row_MATERIALS_CONSUMED_PA;
	int row_DIRECT_LABOUR_PA;
	int row_PRODUCTION_OVERHEAD_PA;
	String[][] amount, amount_PA = null;
	String[][] volume, volume_PA = null;


	public DuyetPLngansach(){
		this.userId = "";
		this.ngay = this.getDateTime();
		this.month = Integer.toString(Integer.parseInt(getDateTime().substring(5, 7)));
		System.out.println(this.month);
		
		this.year = getDateTime().substring(0, 4);

		this.ctyId = "";
		this.cty = "";
		this.nam = "" + (Integer.parseInt(this.getDateTime().substring(0, 4)));
		this.hieuluc = "";
		this.msg = "";
		this.trangthai = "";
		this.db = new dbutils();
		this.util=new Utility();
	}
	
	public int getRow_PA(){
		return this.row_PA;
	}

	public int getRow_SALES_VOLUME_PA(){
		return this.row_SALES_VOLUME_PA;
	}
	
	public int getRow_SELLING_PRICE_NET_PA(){
		return this.row_SELLING_PRICE_NET_PA;
	}
	
	public int getRow_TOTAL_SALES_AMOUNT_PA(){
		return this.row_TOTAL_SALES_AMOUNT_PA;
	}

	public int getRow_SALES_AMOUNT(){
		return this.row_SALES_AMOUNT;
	}

	public int getRow_MATERIALS_CONSUMED_PA(){
		return this.row_MATERIALS_CONSUMED_PA;
	}
	
	public int getRow_DIRECT_LABOUR_PA(){
		return this.row_DIRECT_LABOUR_PA;
	}

	public int getRow_PRODUCTION_OVERHEAD_PA(){
		return this.row_PRODUCTION_OVERHEAD_PA;
	}
	
//-----
	public int getRow_SALES_VOLUME(){
		return this.row_SALES_VOLUME;
	}
	
	public int getRow_TOTAL_AMOUNT(){
		return this.row_TOTAL_AMOUNT;
	}


	public int getRow_MATERIALS_CONSUMED_LA(){
		return this.row_MATERIALS_CONSUMED;
	}
	
	public int getRow_DIRECT_LABOUR_LA(){
		return this.row_DIRECT_LABOUR_LA;
	}
	
	public int getRow_PRODUCTION_OVERHEAD_LA(){
		return this.row_PRODUCTION_OVERHEAD_LA;
	}

	public int getRow_LA(){
		return this.row_LA;
	}

	public String getId(){
		return this.Id;
	}
	
	public void setId(String Id){
		this.Id = Id;
	}

	public String getTrangthai(){
		return this.trangthai;
	}
	
	public void setTrangthai(String trangthai){
		this.trangthai = trangthai;
	}

	
	public String getUserId(){
		return this.userId;
	}
	
	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getNgay(){
		return this.ngay;
	}
	
	public void setNgay(String ngay){
		this.ngay = ngay;
	}
	
	public String getCtyId(){
		return this.ctyId;
	}
	
	public void setCtyId(String ctyId){
		this.ctyId = ctyId;
	}

	public String getCty(){
		return this.cty;
	}
	

	public void setCty(String cty){
		this.cty = cty;
	}

	public String getView(){
		return this.view;
	}
	
	public void setView(String view){
		this.view = view;
	}
	

	public String getNam(){
		return this.nam;
	}
	
	public void setNam(String nam){
		this.nam = nam;
	}

	public String getNamtruoc(){
		return this.namtruoc;
	}
	
	public void setNamtruoc(String namtruoc){
		this.namtruoc = namtruoc;
	}

	public String getMsg(){
		return this.msg;
	}

	public void setMsg(String msg){
		this.msg = msg;
	}
		
	
	public void init(){
	
	}
	

	public ResultSet getNamlist(){
		return this.namlist;
	}
	
	public void setNamlist(ResultSet namlist){
		this.namlist = namlist;
	}


	private void createNamList(){
		int nam = Integer.parseInt(this.getDateTime().substring(0, 4));
		
		String query = "";
		for(int i = nam - 1; i <= nam + 1; i++)
		{
			query += "select " + i + " as nam, N'Năm " + i + "' as namTen ";
			if(i < nam + 1)
			{
				query += " union all ";
			}
		}
		
		this.namlist = this.db.get(query);

	}
	
		private String[] prepareData(String[] data, String query){
		for(int i = 0; i < 12 ; i++){
			
			data[i] = "0";
			
		}
		
		ResultSet rs = this.db.get(query);
		
		if(rs != null){
			try{
				while(rs.next()){
					data[Integer.parseInt(rs.getString("THANG")) - 1] = "" + Math.round(Double.parseDouble(rs.getString("NUM")));
					//System.out.println("i = " + (Integer.parseInt(rs.getString("THANG")) - 1) + " - value = " +  Math.round(Double.parseDouble(rs.getString("NUM"))));
				}
				rs.close();
			}catch(java.sql.SQLException e){}
		}
		
		
		return data;
	}
	
	private void prepareData_Sales(int count, String query){
			for(int n = 0; n < count; n++){
				this.volume[n][0] = "";
				this.amount[n][0] = "";
				
				for(int i = 1; i <= 12; i++){
					
					this.volume[n][i] = "0";
					this.amount[n][i] = "0";
					
				}
			}
			
			ResultSet rs = this.db.get(query);
			
			if(rs != null){
				try{
					String ma = "";
					String ma_bk = "";
					int n = -1;
					while(rs.next()){
						ma = rs.getString("MA");
						if(!ma.equals(ma_bk)){
							n++;
							ma_bk = ma;
							this.volume[n][0] = ma;
							this.amount[n][0] = ma;
							this.volume[n][Integer.parseInt(rs.getString("THANG"))] = "" + Math.round(Double.parseDouble(rs.getString("SOLUONG")));
							this.amount[n][Integer.parseInt(rs.getString("THANG"))] = "" + Math.round(Double.parseDouble(rs.getString("THANHTIEN")));

						}else{
							this.volume[n][Integer.parseInt(rs.getString("THANG"))] = "" + Math.round(Double.parseDouble(rs.getString("SOLUONG")));
							this.amount[n][Integer.parseInt(rs.getString("THANG"))] = "" + Math.round(Double.parseDouble(rs.getString("THANHTIEN")));
						}
					}
					rs.close();
				}catch(java.sql.SQLException e){}
			}
			
			
	}
		
	private void prepareData_Sales_PA(int count, String query){
		for(int n = 0; n < count; n++){
			this.volume_PA[n][0] = "";
			this.amount_PA[n][0] = "";
			
			for(int i = 1; i <= 12; i++){
				
				this.volume_PA[n][i] = "0";
				this.amount_PA[n][i] = "0";
				
			}
		}
		
		ResultSet rs = this.db.get(query);
		
		if(rs != null){
			try{
				String ma = "";
				String ma_bk = "";
				int n = -1;
				while(rs.next()){
					ma = rs.getString("MA");
					if(!ma.equals(ma_bk)){
						n++;
						ma_bk = ma;
						this.volume_PA[n][0] = ma;
						this.amount_PA[n][0] = ma;
						
						this.volume_PA[n][Integer.parseInt(rs.getString("THANG"))] = "" + Math.round(Double.parseDouble(rs.getString("TRONGLUONG")));
						this.amount_PA[n][Integer.parseInt(rs.getString("THANG"))] = "" + Math.round(Double.parseDouble(rs.getString("THANHTIEN")));

					}else{
						this.volume_PA[n][Integer.parseInt(rs.getString("THANG"))] = "" + Math.round(Double.parseDouble(rs.getString("TRONGLUONG")));
						this.amount_PA[n][Integer.parseInt(rs.getString("THANG"))] = "" + Math.round(Double.parseDouble(rs.getString("THANHTIEN")));
					}
				}
				rs.close();
			}catch(java.sql.SQLException e){}
		}
		
		
}
		
	private String[] prepareData_2(String[] data, String query, String MA){
		String[] tmp1 = new String[12];
		String[] tmp2 = new String[12];
		
		for(int i = 0; i < 12; i++){
			
			data[i] = "0";
			tmp1[i] = "0";
			tmp2[i] = "0";
		}
		
		ResultSet rs = this.db.get(query);
		
		if(rs != null){
			try{
				while(rs.next()){
					data[Integer.parseInt(rs.getString("THANG")) - 1] = "" + Math.round(Double.parseDouble(rs.getString("NUM")));
					tmp1[Integer.parseInt(rs.getString("THANG")) - 1] = "" + Math.round(Double.parseDouble(rs.getString("THANHTIEN")));
					tmp2[Integer.parseInt(rs.getString("THANG")) - 1] = "" + Math.round(Double.parseDouble(rs.getString("SELLINGPRICE")));
					//System.out.println("i = " + (Integer.parseInt(rs.getString("THANG")) - 1) + " - value = " +  Math.round(Double.parseDouble(rs.getString("NUM"))));
				}
				rs.close();
			}catch(java.sql.SQLException e){}
		}
		this.amount_ht.put(MA, tmp1);
		this.sellingprice_ht.put(MA, tmp2);
		
		return data;
	}

	private String[] prepareData_3(String[] data, String query, String MA){
		String[] tmp1 = new String[13];
		String[] tmp2 = new String[13];
		
		for(int i = 0; i <= 12 ; i++){
			
			data[i] = "0";
			tmp1[i] = "0";
			tmp2[i] = "0";
		}
		
		ResultSet rs = this.db.get(query);
		double total_volume_kg = 0;
		double total_thanhtien = 0;
		
		if(rs != null){
			try{
				while(rs.next()){
					total_volume_kg += Double.parseDouble(rs.getString("NUM"));
					total_thanhtien += Double.parseDouble(rs.getString("THANHTIEN"));
					
					data[Integer.parseInt(rs.getString("THANG")) - 1] = "" + Double.parseDouble(rs.getString("NUM"));
					tmp1[Integer.parseInt(rs.getString("THANG")) - 1] = "" + Math.round(Double.parseDouble(rs.getString("THANHTIEN")));
					tmp2[Integer.parseInt(rs.getString("THANG")) - 1] = "" + Math.round(Double.parseDouble(rs.getString("SELLINGPRICE")));
					//System.out.println("i = " + (Integer.parseInt(rs.getString("THANG")) - 1) + " - value = " +  Math.round(Double.parseDouble(rs.getString("NUM"))));
				}
				data[12] = "" + total_volume_kg;
				tmp1[12] = "" + total_thanhtien;
				tmp2[12] = "" + (total_thanhtien/total_volume_kg);
				rs.close();
			}catch(java.sql.SQLException e){}
		}
		this.amount_ht.put(MA, tmp1);
		this.sellingprice_ht.put(MA, tmp2);
		
		return data;
	}
	
	public String[] getDanhSachMaketoan_LA(){
		String[] tmp = null;
		try{   
			String query = 	"SELECT COUNT(DISTINCT MKT.MA) AS NUM \n " +
							"FROM ERP_SANPHAM SP \n " +
							"INNER JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ = SP.DVKD_FK \n " +
							"INNER JOIN ERP_MAKETOAN MKT ON MKT.PK_SEQ = SP.MAKETOAN_FK \n " +
							"WHERE DVKD.PK_SEQ =  100000 AND SP.TRANGTHAI = 1 ";
		   
		   ResultSet rs = this.db.get(query);
		   
		   rs.next();
		   
		   if(rs.getInt("NUM") > 0){
			   tmp = new String[rs.getInt("NUM")];

		   }
		   rs.close();
		   
		   query = 	"SELECT DISTINCT MKT.MA \n " +
		   			"FROM ERP_SANPHAM SP \n " +
		   			"INNER JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ = SP.DVKD_FK \n " +
		   			"INNER JOIN ERP_MAKETOAN MKT ON MKT.PK_SEQ = SP.MAKETOAN_FK \n " +
		   			"WHERE DVKD.PK_SEQ =  100000 AND SP.TRANGTHAI = 1 ORDER BY MA ";
		   rs = this.db.get(query);
		   
		   int i = 0;
		   while(rs.next()){
			   tmp[i] = rs.getString("MA");
			   i++;
		   }
		   rs.close();
		}catch(java.sql.SQLException e){}
		   
		return tmp;
	}
	
	public String[] getDanhSachMaketoan_PA(){
		String[] tmp = null;
		try{   
			String query = 	"SELECT COUNT(DISTINCT MKT.MA) AS NUM \n " +
							"FROM ERP_SANPHAM SP \n " +
							"INNER JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ = SP.DVKD_FK \n " +
							"INNER JOIN ERP_MAKETOAN MKT ON MKT.PK_SEQ = SP.MAKETOAN_FK \n " +
							"WHERE DVKD.PK_SEQ =  100004 AND SP.TRANGTHAI = 1 ";
		   
		   ResultSet rs = this.db.get(query);
		   
		   rs.next();
		   
		   if(rs.getInt("NUM") > 0){
			   tmp = new String[rs.getInt("NUM")];
			   

		   }
		   rs.close();
		   
		   query = 	"SELECT DISTINCT MKT.MA \n " +
		   			"FROM ERP_SANPHAM SP \n " +
		   			"INNER JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ = SP.DVKD_FK \n " +
		   			"INNER JOIN ERP_MAKETOAN MKT ON MKT.PK_SEQ = SP.MAKETOAN_FK \n " +
		   			"WHERE DVKD.PK_SEQ =  100004 AND SP.TRANGTHAI = 1 ORDER BY MA ";
		   rs = this.db.get(query);
		   
		   int i = 0;
		   while(rs.next()){
			   tmp[i] = rs.getString("MA");
			   i++;
		   }
		   rs.close();
		}catch(java.sql.SQLException e){}
		   
		return tmp;
	}

	public String[] PL_SalesAmount_Total_Budget(){
		String[] data = new String[13];
		String query =  "SELECT THANG, SUM(NUM) AS NUM \n " +
						"FROM( \n " +
						"	SELECT SUBSTRING(HD.NGAYXUATHD, 6, 2) AS THANG, SUM(HD_SP.SOLUONG*HD_SP.DONGIA) AS NUM \n " +
						"	FROM ERP_HOADON HD  \n " +
						"	INNER JOIN ERP_HOADON_SP HD_SP ON HD_SP.HOADON_FK = HD.PK_SEQ  \n  " +
						"	WHERE HD.CONGTY_FK = " + this.ctyId + " AND SUBSTRING(HD.NGAYXUATHD, 1, 4) = '" + this.year + "'  \n " +
						"	AND HD.TRANGTHAI NOT IN (0, 2) AND HD.LOAIHD = 1  \n " + 
						"	AND HD_SP. IN ( SELECT PK_SEQ from ERP_SANPHAM WHERE DVKD_FK IN (100000, 100004))  \n " + 
						"	GROUP BY SUBSTRING(HD.NGAYXUATHD, 6, 2)  \n " +

						"	UNION ALL \n " +

						"	SELECT SUBSTRING(HD.NGAYXUATHD, 6, 2) AS THANG, (-1)*SUM(HD_SP.SOLUONG*HD_SP.DONGIA) AS NUM \n " +
						"	FROM ERP_HOADON HD  \n " +
						"	INNER JOIN ERP_HOADON_SP HD_SP ON HD_SP.HOADON_FK = HD.PK_SEQ  \n  " +
						"	WHERE HD.CONGTY_FK = " + this.ctyId + " AND SUBSTRING(HD.NGAYXUATHD, 1, 4) = '" + this.year + "'  \n " +
						"	AND HD.TRANGTHAI NOT IN (0, 2) AND HD.LOAIHD = 2  \n " + 
						"	AND HD_SP. IN ( SELECT PK_SEQ from ERP_SANPHAM WHERE DVKD_FK IN (100000, 100004))  \n " + 
						"	GROUP BY SUBSTRING(HD.NGAYXUATHD, 6, 2)  \n " +

						"	UNION ALL \n " +

						"	SELECT SUBSTRING(HD.NGAYHOADON, 6, 2) AS THANG, SUM(HD.BVAT) AS NUM   \n " +
						"	FROM ERP_HOADONPHELIEU HD    \n " +

						"	WHERE HD.CONGTY_FK = " + this.ctyId + " AND SUBSTRING(HD.NGAYHOADON, 1, 4) = '" + this.year + "'    \n " +
						"	AND HD.TRANGTHAI = 1  AND DVKD_FK IN (100000, 100004) \n " +
						"	GROUP BY SUBSTRING(HD.NGAYHOADON, 6, 2)    \n " +

						")SALESAMOUNT \n " +
						"GROUP BY SALESAMOUNT.THANG \n " +
						"ORDER BY SALESAMOUNT.THANG ";
						
				
		System.out.println("3.SalesAmount: " + query);
		data = prepareData(data, query);
		return data;
	}

	public String[] PL_SalesAmount_LA_Total_Budget(){
		String[] data = new String[13];
		String query =  "SELECT THANG, SUM(NUM) AS NUM \n " +
						"FROM( \n " +
						"	SELECT SUBSTRING(HD.NGAYXUATHD, 6, 2) AS THANG, SUM(HD_SP.SOLUONG*HD_SP.DONGIA) AS NUM \n " +
						"	FROM ERP_HOADON HD  \n " +
						"	INNER JOIN ERP_HOADON_SP HD_SP ON HD_SP.HOADON_FK = HD.PK_SEQ  \n  " +
						"	WHERE HD.CONGTY_FK = " + this.ctyId + " AND SUBSTRING(HD.NGAYXUATHD, 1, 4) = '" + this.year + "'  \n " +
						"	AND HD.TRANGTHAI NOT IN (0, 2) AND HD.LOAIHD = 1  \n " + 
						"	AND HD_SP. IN ( SELECT PK_SEQ from ERP_SANPHAM WHERE DVKD_FK = 100000)  \n " + 
						"	GROUP BY SUBSTRING(HD.NGAYXUATHD, 6, 2)  \n " +

						"	UNION ALL \n " +

						"	SELECT SUBSTRING(HD.NGAYXUATHD, 6, 2) AS THANG, (-1)*SUM(HD_SP.SOLUONG*HD_SP.DONGIA) AS NUM \n " +
						"	FROM ERP_HOADON HD  \n " +
						"	INNER JOIN ERP_HOADON_SP HD_SP ON HD_SP.HOADON_FK = HD.PK_SEQ  \n  " +
						"	WHERE HD.CONGTY_FK = " + this.ctyId + " AND SUBSTRING(HD.NGAYXUATHD, 1, 4) = '" + this.year + "'  \n " +
						"	AND HD.TRANGTHAI NOT IN (0, 2) AND HD.LOAIHD = 2  \n " + 
						"	AND HD_SP. IN ( SELECT PK_SEQ from ERP_SANPHAM WHERE DVKD_FK = 100000)  \n " + 
						"	GROUP BY SUBSTRING(HD.NGAYXUATHD, 6, 2)  \n " +

						"	UNION ALL \n " +

						"	SELECT SUBSTRING(HD.NGAYHOADON, 6, 2) AS THANG, SUM(HD.BVAT) AS NUM   \n " +
						"	FROM ERP_HOADONPHELIEU HD    \n " +

						"	WHERE HD.CONGTY_FK = " + this.ctyId + " AND SUBSTRING(HD.NGAYHOADON, 1, 4) = '" + this.year + "'    \n " +
						"	AND HD.TRANGTHAI = 1  AND DVKD_FK = 100000 \n " +
						"	GROUP BY SUBSTRING(HD.NGAYHOADON, 6, 2)    \n " +

						")SALESAMOUNT \n " +
						"GROUP BY SALESAMOUNT.THANG \n " +
						"ORDER BY SALESAMOUNT.THANG ";
		
		System.out.println(query);
		data = prepareData(data, query);
		return data;
	}


	public String[] PL_SalesAmount_PA_Total_Budget(){
		String[] data = new String[13];
		String query =  "SELECT THANG, SUM(NUM) AS NUM \n " +
						"FROM( \n " +
						"	SELECT SUBSTRING(HD.NGAYXUATHD, 6, 2) AS THANG, SUM(HD_SP.SOLUONG*HD_SP.DONGIA) AS NUM \n " +
						"	FROM ERP_HOADON HD  \n " +
						"	INNER JOIN ERP_HOADON_SP HD_SP ON HD_SP.HOADON_FK = HD.PK_SEQ  \n  " +
						"	WHERE HD.CONGTY_FK = " + this.ctyId + " AND SUBSTRING(HD.NGAYXUATHD, 1, 4) = '" + this.year + "'  \n " +
						"	AND HD.TRANGTHAI NOT IN (0, 2) AND HD.LOAIHD = 1  \n " + 
						"	AND HD_SP. IN ( SELECT PK_SEQ from ERP_SANPHAM WHERE DVKD_FK = 100004)  \n " + 
						"	GROUP BY SUBSTRING(HD.NGAYXUATHD, 6, 2)  \n " +

						"	UNION ALL \n " +

						"	SELECT SUBSTRING(HD.NGAYXUATHD, 6, 2) AS THANG, (-1)*SUM(HD_SP.SOLUONG*HD_SP.DONGIA) AS NUM \n " +
						"	FROM ERP_HOADON HD  \n " +
						"	INNER JOIN ERP_HOADON_SP HD_SP ON HD_SP.HOADON_FK = HD.PK_SEQ  \n  " +
						"	WHERE HD.CONGTY_FK = " + this.ctyId + " AND SUBSTRING(HD.NGAYXUATHD, 1, 4) = '" + this.year + "'  \n " +
						"	AND HD.TRANGTHAI NOT IN (0, 2) AND HD.LOAIHD = 2  \n " + 
						"	AND HD_SP. IN ( SELECT PK_SEQ from ERP_SANPHAM WHERE DVKD_FK = 100004)  \n " + 
						"	GROUP BY SUBSTRING(HD.NGAYXUATHD, 6, 2)  \n " +

						"	UNION ALL \n " +

						"	SELECT SUBSTRING(HD.NGAYHOADON, 6, 2) AS THANG, SUM(HD.BVAT) AS NUM   \n " +
						"	FROM ERP_HOADONPHELIEU HD    \n " +

						"	WHERE HD.CONGTY_FK = " + this.ctyId + " AND SUBSTRING(HD.NGAYHOADON, 1, 4) = '" + this.year + "'    \n " +
						"	AND HD.TRANGTHAI = 1  AND DVKD_FK = 100004 \n " +
						"	GROUP BY SUBSTRING(HD.NGAYHOADON, 6, 2)    \n " +

						")SALESAMOUNT \n " +
						"GROUP BY SALESAMOUNT.THANG \n " +
						"ORDER BY SALESAMOUNT.THANG ";
		
		System.out.println(query);
		data = prepareData(data, query);
		return data;
	}


	
	public String[] PL_SalesAmount_LA_Return_Budget(){
		String[] data = new String[13];
		String tungay = this.year + "-01-01";
		String denngay = this.year + "-" + (this.month.length() > 1?this.month:"0" + this.month) + "-31";
		
		String query = 	"SELECT THANG, (-1)*SUM(TONGGIATRI) AS NUM \n " +
						"FROM  \n " +
						"(  \n " +
//Nợ 511100/Có 521100 + Nợ 511100/Có 531100 + Nợ 511100/Có 532100  + Nợ 511800/Có 521800						
						"	SELECT SUBSTRING(NGAYCHUNGTU, 6, 2) AS THANG, ISNULL(SUM(TONGGIATRI), 0) AS TONGGIATRI \n " +
						"	FROM ERP_PHATSINHKETOAN  \n " +
						"	WHERE NGAYCHUNGTU >= '" + tungay + "' AND NGAYCHUNGTU <= '" + denngay + "'  \n " +
						"	AND TAIKHOAN_FK = (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '511100' AND CONGTY_FK = " + this.ctyId + ")  \n " +
						"	AND TAIKHOANDOIUNG_FK = (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '521100' AND CONGTY_FK = " + this.ctyId + " )  \n " +
						"	GROUP BY NGAYCHUNGTU  \n " +

						"	UNION ALL  \n " +
						"	SELECT SUBSTRING(NGAYCHUNGTU, 6, 2) AS THANG, ISNULL(SUM(TONGGIATRI), 0) AS TONGGIATRI  \n " +
						"	FROM ERP_PHATSINHKETOAN \n " +
						"	WHERE NGAYCHUNGTU >= '" + tungay + "' AND NGAYCHUNGTU <= '" + denngay + "'  \n " +
						"	AND TAIKHOAN_FK = (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '511100' AND CONGTY_FK = " + this.ctyId + ")  \n " +
						"	AND TAIKHOANDOIUNG_FK = (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '531100' AND CONGTY_FK = " + this.ctyId + ")  \n " +
						"	GROUP BY NGAYCHUNGTU  \n " +

						"	UNION ALL \n " +
						"	SELECT  SUBSTRING(NGAYCHUNGTU, 6, 2) AS THANG, ISNULL(SUM(TONGGIATRI), 0) AS TONGGIATRI \n " +
						"	FROM ERP_PHATSINHKETOAN \n " +
						"	WHERE NGAYCHUNGTU >= '" + tungay + "' AND NGAYCHUNGTU <= '" + denngay + "' \n " +
						"	AND TAIKHOAN_FK = (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '511100' AND CONGTY_FK = " + this.ctyId + ") \n " +
						"	AND TAIKHOANDOIUNG_FK = (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '532100' AND CONGTY_FK = " + this.ctyId + ") \n " +
						"	GROUP BY NGAYCHUNGTU \n " +

						"	UNION ALL \n " +
						"	SELECT  SUBSTRING(NGAYCHUNGTU, 6, 2) AS THANG, ISNULL(SUM(TONGGIATRI), 0) AS TONGGIATRI \n " +
						"	FROM ERP_PHATSINHKETOAN \n " +
						"	WHERE NGAYCHUNGTU >= '" + tungay + "' AND NGAYCHUNGTU <= '" + denngay + "' \n " +
						"	AND TAIKHOAN_FK = (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '511100' AND CONGTY_FK = " + this.ctyId + ") \n " +
						"	AND TAIKHOANDOIUNG_FK = (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '532100' AND CONGTY_FK = " + this.ctyId + ") \n " +
						"	GROUP BY NGAYCHUNGTU \n " +

//--- Nợ 521100/Có 511100 - Nợ 531100/Có 511100 - Nợ 532100/Có 511100 - Nợ 521800/Có 511800
						"	UNION ALL \n " +
						"	SELECT  SUBSTRING(NGAYCHUNGTU, 6, 2) AS THANG, (-1)*ISNULL(SUM(TONGGIATRI), 0) AS TONGGIATRI \n " +
						"	FROM ERP_PHATSINHKETOAN \n " +
						"	WHERE NGAYCHUNGTU >= '" + tungay + "' AND NGAYCHUNGTU <= '" + denngay + "' \n " +
						"	AND TAIKHOAN_FK = (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '521100' AND CONGTY_FK = " + this.ctyId + ") \n " +
						"	AND TAIKHOANDOIUNG_FK = (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '511100' AND CONGTY_FK = " + this.ctyId + ") \n " +
						"	GROUP BY NGAYCHUNGTU \n " +

						"	UNION ALL \n " +
						"	SELECT  SUBSTRING(NGAYCHUNGTU, 6, 2) AS THANG, (-1)*ISNULL(SUM(TONGGIATRI), 0) AS TONGGIATRI \n " +
						"	FROM ERP_PHATSINHKETOAN \n " +
						"	WHERE NGAYCHUNGTU >= '" + tungay + "' AND NGAYCHUNGTU <= '" + denngay + "' \n " +
						"	AND TAIKHOAN_FK = (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '531100' AND CONGTY_FK = " + this.ctyId + ") \n " +
						"	AND TAIKHOANDOIUNG_FK = (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '511100' AND CONGTY_FK = " + this.ctyId + ") \n " +
						"	GROUP BY NGAYCHUNGTU \n " +

						"	UNION ALL \n " +
						"	SELECT  SUBSTRING(NGAYCHUNGTU, 6, 2) AS THANG, (-1)*ISNULL(SUM(TONGGIATRI), 0) AS TONGGIATRI \n " +
						"	FROM ERP_PHATSINHKETOAN \n " +
						"	WHERE NGAYCHUNGTU >= '" + tungay + "' AND NGAYCHUNGTU <= '" + denngay + "' \n " +
						"	AND TAIKHOAN_FK = (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '532100' AND CONGTY_FK = " + this.ctyId + ") \n " +
						"	AND TAIKHOANDOIUNG_FK = (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '511100' AND CONGTY_FK = " + this.ctyId + ") \n " +
						"	GROUP BY NGAYCHUNGTU \n " +

						"	UNION ALL \n " +
						"	SELECT  SUBSTRING(NGAYCHUNGTU, 6, 2) AS THANG, (-1)*ISNULL(SUM(TONGGIATRI), 0) AS TONGGIATRI \n " +
						"	FROM ERP_PHATSINHKETOAN \n " +
						"	WHERE NGAYCHUNGTU >= '" + tungay + "' AND NGAYCHUNGTU <= '" + denngay + "' \n " +
						"	AND TAIKHOAN_FK = (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '521800' AND CONGTY_FK = " + this.ctyId + ") \n " +
						"	AND TAIKHOANDOIUNG_FK = (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '511800' AND CONGTY_FK = " + this.ctyId + ") \n " +
						"	GROUP BY NGAYCHUNGTU \n " +

						")DATA \n " +
						"GROUP BY THANG \n ";
/*			"SELECT THANG, SUM(THANHTIEN) AS NUM \n " +
			"FROM  \n " +
			"( \n " +
			"	SELECT SUBSTRING(NGAYXUAT, 6, 2) AS THANG,  \n " +
			"	THANHTIEN \n " +
			"	FROM \n " +
			"	(  \n " +
	 		"		SELECT  type,   \n " +
	  		"		CASE WHEN A.LSP IN (100013,100014,100015,100016,100017) THEN 'NL'   \n " +
	  		"		ELSE    \n " +
	  		"			CASE WHEN A.MA = 100000 THEN 'N'    \n " +
	  		"			ELSE CASE WHEN A.MA = 100004 THEN 'L'    \n " +
	  		"			ELSE CASE WHEN A.MA = 100005 THEN 'SPM'    \n " +
	  		"			ELSE CASE WHEN A.MA = 100007 THEN 'K'   \n " +
	  		"			ELSE CASE WHEN A.MA = 100008 THEN 'GC'   \n " +
	  		"			ELSE 'K'    \n " +
	  		"		END END END END END   \n " +
	  		"		END AS DVKD,    \n " +
	  		
	  		"		CASE WHEN A.MA1 = 100000 THEN N'Nhôm'    \n " +
	  		"		WHEN A.MA1 = 100004 THEN N'Lõi'    \n " +
	  		" 		WHEN A.MA1= 100005 THEN N'SPM'    \n " +
	  		"		ELSE N'Khác'    \n " +
	  		"		END   as donvikinhdoanh,   \n " +
	  		"		A.LSP,     \n " +
	  		"		A.NGAYXUAT, A.SPTEN,     \n " +
	  		"		A.SOLUONG,    \n " +
	  		"		A.THANHTIEN, A.,     \n " +
	  		"		A.MKT_SP,     \n " +
	  		"		A.DVDL, A.TRONGLUONG_QC  \n " +
	  		"		FROM     \n " +
	  		"		(   \n " +
	 			
// Lấy các chiết khấu trong hóa đơn tài chính
			" 		SELECT 'CHIETKHAU' AS TYPE, ( SELECT TOP  1  CASE WHEN  DH.LOAIDONHANG ='2' THEN 100008 ELSE SP.DVKD_FK END    \n " +
			" 		FROM ERP_DONDATHANG DH  INNER JOIN ERP_XUATKHO XK ON XK.DONDATHANG_FK= DH.PK_SEQ   				 \n " +
			" 		INNER JOIN ERP_HOADON_XUATKHO HDXK ON HDXK.XUATKHO_FK=XK.PK_SEQ WHERE HDXK.hoadon_fk= HD.PK_SEQ  \n " +
			" 		) AS MA  , SP.DVKD_FK AS MA1 , LSP.PK_SEQ AS LSP,    \n " +
			" 		HD.NGAYXUATHD AS NGAYXUAT,   \n " +
			 		
			" 		SP.TEN AS SPTEN,    \n " +
			 		
			"		HD_SP.DONVITINH AS DVT,    \n " +
			" 		0 AS SOLUONG,   \n " +
			" 		(-1)*HD_SP.SOLUONG*HD_SP.DONGIA*(THANHTIEN.THANHTIEN*HD.CHIETKHAU/100)/THANHTIEN.THANHTIEN AS THANHTIEN, \n " +
			 
			" 		'' AS , '' AS MKT_SP,   \n " +
			"		HD.LOAIHD,    \n " +
			"		isnull(HD_SP.DVDL_FK,0) AS DVDL,  \n " +
			"		CASE  WHEN HD_SP.DVDL_FK =100003 THEN 1     \n " +
			" 		WHEN QC2.DVDL1_FK IS NOT NULL THEN   ISNULL( CAST(QC2.SOLUONG2 AS FLOAT ), 0)  / ISNULL( CAST(QC2.SOLUONG1 AS FLOAT) , 1)    \n " +
			"		WHEN   QC3.DVDL1_FK IS NOT NULL THEN   ISNULL(CAST(QC3.SOLUONG1 AS FLOAT) , 0)  / ISNULL( CAST(QC3.SOLUONG2 AS FLOAT ), 1)   \n " +
			" 		WHEN QC4.DVDL2_FK IS NOT NULL THEN (   ISNULL( CAST(QC4.SOLUONG2 AS FLOAT) , 0)  / ISNULL(CAST(QC4.SOLUONG1 AS FLOAT), 1) ) *  CAST(D.SOLUONG1 AS FLOAT)/ CAST(D.SOLUONG2 AS FLOAT)   \n " +  
			" 		ELSE 0  END AS TRONGLUONG_QC   \n " +
			" 		FROM ERP_HOADON HD    \n " +
			" 		INNER JOIN ERP_HOADON_SP HD_SP ON HD.PK_SEQ=HD_SP.HOADON_FK     \n " +
			" 		INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ= HD_SP.     \n " +
			"		INNER JOIN  \n " +
			"		( \n " +
			"			SELECT HOADON_FK, SUM(SOLUONG*DONGIA) AS THANHTIEN \n " +
			"			FROM ERP_HOADON_SP \n " +
			"			GROUP BY HOADON_FK \n " +
			"		)THANHTIEN ON THANHTIEN.HOADON_FK = HD.PK_SEQ \n " +

			" 		LEFT JOIN ERP_MAKETOAN MKT ON SP.MAKETOAN_FK= MKT.PK_SEQ     \n " +
			" 		LEFT JOIN ERP_LOAISANPHAM LSP ON SP.LOAI= LSP.PK_SEQ     \n " +
			"  		LEFT JOIN QUYCACH D ON HD_SP. = d.  AND HD_SP.DVDL_FK=D.DVDL2_FK   		  \n " +
			"  		LEFT JOIN QUYCACH QC2 ON QC2.=SP.PK_SEQ AND QC2.DVDL2_FK=100003  AND QC2.DVDL1_FK=HD_SP.DVDL_FK    \n " +		
			"  		LEFT JOIN QUYCACH QC3 ON QC3.=SP.PK_SEQ AND QC3.DVDL1_FK=100003  AND QC3.DVDL2_FK=HD_SP.DVDL_FK  	  \n " +	
			"  		LEFT JOIN QUYCACH QC4 ON QC4.=SP.PK_SEQ AND QC4.DVDL2_FK= 100003 		  \n " +
			"  		LEFT JOIN DONVIDOLUONG C ON HD_SP.DVDL_FK = C.PK_SEQ     \n " +
			" 		INNER JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ = SP.DVKD_FK     \n " +
			" 		LEFT JOIN QUYCACH QC ON HD_SP.DVDL_FK=QC.DVDL2_FK AND HD_SP.=QC.   \n " +
			" 		WHERE HD.TRANGTHAI NOT IN (0, 2) and isnull(is_hangmau, 0)<> 1 AND HD.LOAIHD = 1 AND HD.CHIETKHAU > 0 \n " +
			"		AND HD.NGAYXUATHD >= '" + tungay + "' AND HD.NGAYXUATHD <= '" + denngay + "'  \n " +

// Hủy hóa đơn có chiếu khấu
			"  UNION ALL 		 \n " +
			"  SELECT 'HUYCHIETKHAU' AS TYPE,  SP.DVKD_FK, SP.DVKD_FK AS MA, LSP.PK_SEQ AS LSP,    \n " +
			"		HUYCT.ngayhuy AS NGAYXUAT,  \n " +
			" 		CASE WHEN HD.LOAIHD = 1 THEN SP.TEN ELSE SP.TEN + N' - (HỦY HÓA ĐƠN HÀNG TRẢ VỀ)' END  AS SPTEN,    \n " +
			"		HD_SP.DONVITINH AS DVT,   \n " +
			" 		0 AS SOLUONG,  \n " +
			" 		HD_SP.SOLUONG*HD_SP.DONGIA*(THANHTIEN.THANHTIEN*HD.CHIETKHAU/100)/THANHTIEN.THANHTIEN AS THANHTIEN, \n " +
		 	  
			" 		SP.MA AS , ISNULL(MKT.MA,'') AS MKT_SP, HD.LOAIHD,   \n " +
			" 		isnull(HD_SP.DVDL_FK,0) AS DVDL,  \n " +
			 		 
			" 		 CASE  WHEN HD_SP.DVDL_FK =100003 THEN 1    \n " +
			" 		 WHEN QC2.DVDL1_FK IS NOT NULL THEN   ISNULL( CAST(QC2.SOLUONG2 AS FLOAT ), 0)  / ISNULL( CAST(QC2.SOLUONG1 AS FLOAT) , 1)  \n " + 
			"		 WHEN   QC3.DVDL1_FK IS NOT NULL THEN   ISNULL(CAST(QC3.SOLUONG1 AS FLOAT) , 0)  / ISNULL( CAST(QC3.SOLUONG2 AS FLOAT ), 1)  \n " +
			" 		 WHEN QC4.DVDL2_FK IS NOT NULL THEN (   ISNULL( CAST(QC4.SOLUONG2 AS FLOAT) , 0)  / ISNULL(CAST(QC4.SOLUONG1 AS FLOAT), 1) ) *  CAST(D.SOLUONG1 AS FLOAT)/ CAST(D.SOLUONG2 AS FLOAT)  \n " + 
			"		 ELSE 0  END AS TRONGLUONG_QC \n " +
					 
			" 		FROM ERP_HOADON HD   \n " +
			"  		INNER JOIN ERP_HUYCHUNGTUBANHANG HUYCT ON HUYCT.SOCHUNGTU=HD.PK_SEQ  \n " +
			" 		INNER JOIN ERP_HOADON_SP HD_SP ON HD.PK_SEQ=HD_SP.HOADON_FK    \n " +
			" 		INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ= HD_SP.    \n " +
			"		INNER JOIN  \n " +
			"		( \n " +
			"			SELECT HOADON_FK, SUM(SOLUONG*DONGIA) AS THANHTIEN \n " +
			"			FROM ERP_HOADON_SP \n " +
			"			GROUP BY HOADON_FK \n " +
			"		)THANHTIEN ON THANHTIEN.HOADON_FK = HD.PK_SEQ \n " +

			" 		LEFT JOIN ERP_MAKETOAN MKT ON SP.MAKETOAN_FK= MKT.PK_SEQ    \n " +
			" 		LEFT JOIN ERP_LOAISANPHAM LSP ON SP.LOAI= LSP.PK_SEQ    \n " +
			"		LEFT JOIN QUYCACH D ON HD_SP. = d.  AND HD_SP.DVDL_FK=D.DVDL2_FK   		 \n " +
			"		LEFT JOIN QUYCACH QC2 ON QC2.=SP.PK_SEQ AND QC2.DVDL2_FK=100003  AND QC2.DVDL1_FK=HD_SP.DVDL_FK  		  \n " +
			"		LEFT JOIN QUYCACH QC3 ON QC3.=SP.PK_SEQ AND QC3.DVDL1_FK=100003  AND QC3.DVDL2_FK=HD_SP.DVDL_FK  		 \n " +
			"		LEFT JOIN QUYCACH QC4 ON QC4.=SP.PK_SEQ AND QC4.DVDL2_FK= 100003 		 \n " +
			"		LEFT JOIN DONVIDOLUONG C ON HD_SP.DVDL_FK = C.PK_SEQ    \n " +
			" 		INNER JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ = SP.DVKD_FK    \n " +
			" 		LEFT JOIN QUYCACH QC ON HD_SP.DVDL_FK=QC.DVDL2_FK AND HD_SP.=QC.  \n " +
			" 		WHERE HD.TRANGTHAI NOT IN (0, 2) and isnull(is_hangmau, 0)<> 1   \n " +
			" 		AND  HUYCT.LOAICHUNGTU IN ('HOADON_KHTRA') AND 	    HUYCT.huykhackyketoan='1' and HUYCT.TRANGTHAI='1' AND HD.LOAIHD = 1 AND HD.CHIETKHAU > 0   \n " +
			" 		AND  HUYCT.ngayhuy >= '" + tungay + "' AND HUYCT.ngayhuy <= '" + denngay + "' \n " +


// Lấy các hoá đơn hàng trả về
			" UNION ALL \n " +

			" 		SELECT 'HDTRAVE' AS TYPE, ( SELECT TOP  1  CASE WHEN  DH.LOAIDONHANG ='2' THEN 100008 ELSE SP.DVKD_FK END    \n " +
			" 		FROM ERP_DONDATHANG DH  INNER JOIN ERP_XUATKHO XK ON XK.DONDATHANG_FK= DH.PK_SEQ   				 \n " +
			" 		INNER JOIN ERP_HOADON_XUATKHO HDXK ON HDXK.XUATKHO_FK=XK.PK_SEQ WHERE HDXK.hoadon_fk= HD.PK_SEQ  \n " +
			" 		) AS MA  , SP.DVKD_FK AS MA1 , LSP.PK_SEQ AS LSP,    \n " +
			" 		HD.NGAYXUATHD AS NGAYXUAT,   \n " +
			 		
			" 		SP.TEN AS SPTEN,    \n " +
			 		
			"		HD_SP.DONVITINH AS DVT,    \n " +
			" 		(-1)*HD_SP.SOLUONG AS SOLUONG,   \n " +
			" 		(-1)*ISNULL(ISNULL(HD_SP.DONGIAVIET,HD_SP.DONGIA)* HD_SP.SOLUONG,'0') AS THANHTIEN,   \n " +
			 
			" 		SP.MA AS , ISNULL(MKT.MA,'') AS MKT_SP,   \n " +
			"		HD.LOAIHD,    \n " +
			"		isnull(HD_SP.DVDL_FK,0) AS DVDL,  \n " +
			"		CASE  WHEN HD_SP.DVDL_FK =100003 THEN 1     \n " +
			" 		WHEN QC2.DVDL1_FK IS NOT NULL THEN   ISNULL( CAST(QC2.SOLUONG2 AS FLOAT ), 0)  / ISNULL( CAST(QC2.SOLUONG1 AS FLOAT) , 1)    \n " +
			"		WHEN   QC3.DVDL1_FK IS NOT NULL THEN   ISNULL(CAST(QC3.SOLUONG1 AS FLOAT) , 0)  / ISNULL( CAST(QC3.SOLUONG2 AS FLOAT ), 1)   \n " +
			" 		WHEN QC4.DVDL2_FK IS NOT NULL THEN (   ISNULL( CAST(QC4.SOLUONG2 AS FLOAT) , 0)  / ISNULL(CAST(QC4.SOLUONG1 AS FLOAT), 1) ) *  CAST(D.SOLUONG1 AS FLOAT)/ CAST(D.SOLUONG2 AS FLOAT)   \n " +  
			" 		ELSE 0  END AS TRONGLUONG_QC   \n " +
			" 		FROM ERP_HOADON HD    \n " +
			" 		INNER JOIN ERP_HOADON_SP HD_SP ON HD.PK_SEQ=HD_SP.HOADON_FK     \n " +
			" 		INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ= HD_SP.     \n " +
			" 		LEFT JOIN ERP_MAKETOAN MKT ON SP.MAKETOAN_FK= MKT.PK_SEQ     \n " +
			" 		LEFT JOIN ERP_LOAISANPHAM LSP ON SP.LOAI= LSP.PK_SEQ     \n " +
			"  		LEFT JOIN QUYCACH D ON HD_SP. = d.  AND HD_SP.DVDL_FK=D.DVDL2_FK   		  \n " +
			"  		LEFT JOIN QUYCACH QC2 ON QC2.=SP.PK_SEQ AND QC2.DVDL2_FK=100003  AND QC2.DVDL1_FK=HD_SP.DVDL_FK    \n " +		
			"  		LEFT JOIN QUYCACH QC3 ON QC3.=SP.PK_SEQ AND QC3.DVDL1_FK=100003  AND QC3.DVDL2_FK=HD_SP.DVDL_FK  	  \n " +	
			"  		LEFT JOIN QUYCACH QC4 ON QC4.=SP.PK_SEQ AND QC4.DVDL2_FK= 100003 		  \n " +
			"  		LEFT JOIN DONVIDOLUONG C ON HD_SP.DVDL_FK = C.PK_SEQ     \n " +
			" 		INNER JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ = SP.DVKD_FK     \n " +
			" 		LEFT JOIN QUYCACH QC ON HD_SP.DVDL_FK=QC.DVDL2_FK AND HD_SP.=QC.   \n " +
			" 		WHERE HD.TRANGTHAI NOT IN (0, 2) and isnull(is_hangmau, 0)<> 1 AND HD.LOAIHD = 2   \n " +
			"		AND HD.NGAYXUATHD >= '" + tungay + "' AND HD.NGAYXUATHD <= '" + denngay + "'  \n " +

// Hủy hóa đơn hàng trả về
			"  UNION ALL 		 \n " +
			"  SELECT 'HUYHDTRAVE' AS TYPE,  SP.DVKD_FK, SP.DVKD_FK AS MA, LSP.PK_SEQ AS LSP,    \n " +
			"		HUYCT.ngayhuy AS NGAYXUAT,  \n " +
			" 		CASE WHEN HD.LOAIHD = 1 THEN SP.TEN ELSE SP.TEN + N' - (HỦY HÓA ĐƠN HÀNG TRẢ VỀ)' END  AS SPTEN,    \n " +
			"		HD_SP.DONVITINH AS DVT,   \n " +
			" 		HD_SP.SOLUONG AS SOLUONG,  \n " +
			" 		ISNULL(HD_SP.DONGIAVIET* HD_SP.SOLUONG,'0') AS THANHTIEN,  \n " +
			 	  
			" 		SP.MA AS , ISNULL(MKT.MA,'') AS MKT_SP, HD.LOAIHD,   \n " +
			" 		isnull(HD_SP.DVDL_FK,0) AS DVDL,  \n " +
			 		 
			" 		 CASE  WHEN HD_SP.DVDL_FK =100003 THEN 1    \n " +
			" 		 WHEN QC2.DVDL1_FK IS NOT NULL THEN   ISNULL( CAST(QC2.SOLUONG2 AS FLOAT ), 0)  / ISNULL( CAST(QC2.SOLUONG1 AS FLOAT) , 1)  \n " + 
			"		 WHEN   QC3.DVDL1_FK IS NOT NULL THEN   ISNULL(CAST(QC3.SOLUONG1 AS FLOAT) , 0)  / ISNULL( CAST(QC3.SOLUONG2 AS FLOAT ), 1)  \n " +
			" 		 WHEN QC4.DVDL2_FK IS NOT NULL THEN (   ISNULL( CAST(QC4.SOLUONG2 AS FLOAT) , 0)  / ISNULL(CAST(QC4.SOLUONG1 AS FLOAT), 1) ) *  CAST(D.SOLUONG1 AS FLOAT)/ CAST(D.SOLUONG2 AS FLOAT)  \n " + 
			"		 ELSE 0  END AS TRONGLUONG_QC \n " +
					 
			" 		FROM ERP_HOADON HD   \n " +
			"  		INNER JOIN ERP_HUYCHUNGTUBANHANG HUYCT ON HUYCT.SOCHUNGTU=HD.PK_SEQ  \n " +
			" 		INNER JOIN ERP_HOADON_SP HD_SP ON HD.PK_SEQ=HD_SP.HOADON_FK    \n " +
			" 		INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ= HD_SP.    \n " +
			" 		LEFT JOIN ERP_MAKETOAN MKT ON SP.MAKETOAN_FK= MKT.PK_SEQ    \n " +
			" 		LEFT JOIN ERP_LOAISANPHAM LSP ON SP.LOAI= LSP.PK_SEQ    \n " +
			"		LEFT JOIN QUYCACH D ON HD_SP. = d.  AND HD_SP.DVDL_FK=D.DVDL2_FK   		 \n " +
			"		LEFT JOIN QUYCACH QC2 ON QC2.=SP.PK_SEQ AND QC2.DVDL2_FK=100003  AND QC2.DVDL1_FK=HD_SP.DVDL_FK  		  \n " +
			"		LEFT JOIN QUYCACH QC3 ON QC3.=SP.PK_SEQ AND QC3.DVDL1_FK=100003  AND QC3.DVDL2_FK=HD_SP.DVDL_FK  		 \n " +
			"		LEFT JOIN QUYCACH QC4 ON QC4.=SP.PK_SEQ AND QC4.DVDL2_FK= 100003 		 \n " +
			"		LEFT JOIN DONVIDOLUONG C ON HD_SP.DVDL_FK = C.PK_SEQ    \n " +
			" 		INNER JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ = SP.DVKD_FK    \n " +
			" 		LEFT JOIN QUYCACH QC ON HD_SP.DVDL_FK=QC.DVDL2_FK AND HD_SP.=QC.  \n " +
			" 		WHERE HD.TRANGTHAI NOT IN (0, 2) and isnull(is_hangmau, 0)<> 1   \n " +
			" 		AND  HUYCT.LOAICHUNGTU IN ('HOADON_KHTRA') AND 	    HUYCT.huykhackyketoan='1' and HUYCT.TRANGTHAI='1'  AND HD.LOAIHD = 2   \n " +
			" 		AND  HUYCT.ngayhuy >= '" + tungay + "' AND HUYCT.ngayhuy <= '" + denngay + "' \n " +

//  lấy tổng tiền trước thuế của loại hoá đơn chiết khấu trong hoá đơn khác
			" UNION ALL   \n " +
			" 		SELECT  'HDKHAC' AS TYPE,  \n " +
			" 		CASE WHEN HDPL.DVKD_FK IS NOT NULL AND (HDPL.DOANHTHU_FK = 100000 OR HDPL.DOANHTHU_FK = 400000) THEN HDPL.DVKD_FK   \n " +
			" 		ELSE   \n " +
			" 		CASE WHEN HDPL.DVKD_FK <> 110000 AND HDPL.DOANHTHU_FK = 100004 THEN 100008  \n " +
			" 		ELSE 100007 END   \n " +
			" 		END AS MA1,    \n " +
			 		
			" 		ISNULL(HDPL.DVKD_FK,100007) AS MA,   \n " +
			" 		'1' AS LSP, NGAYHOADON AS NGAYXUAT, ISNULL(HDPL_SP.DIENGIAI,'') AS SPTEN,   \n " +
			"		ISNULL(HDPL_SP.DONVITINH,'') AS DVT,    \n " +
			" 		HDPL_SP.SOLUONG AS SOLUONG, ISNULL(HDPL_SP.THANHTIEN,'0') AS THANHTIEN,   \n " +
			  
			" 		'0' AS , ''  AS MKT_SP,    \n " +
			" 		'1' AS LOAIHD,    \n " +
			" 		'0' AS DVDL,  \n " +
			 		
			" 		'0' as TRONGLUONG_QC \n " +
			 		 
			" 		FROM ERP_HOADONPHELIEU HDPL    \n " +
			" 		INNER JOIN ERP_HOADONPHELIEU_SANPHAM HDPL_SP ON HDPL.PK_SEQ=HDPL_SP.HOADONPHELIEU_FK   \n " +
			" 		LEFT JOIN ERP_TRUNGTAMDOANHTHU TTDT ON HDPL.TRUNGTAMDOANHTHU_FK=TTDT.PK_SEQ   \n " +
			" 		WHERE HDPL.TRANGTHAI NOT IN (0, 2)  \n " +
			" 		AND  HDPL.NGAYHOADON >= '" + tungay + "' AND HDPL.NGAYHOADON <= '" + denngay + "' AND HDPL.DOANHTHU_FK = 400000 \n " +

//Hủy hóa đon chiết khấu trong hóa đơn khác
			" 		UNION ALL   \n " +
			" 		SELECT  'HUY_HDKHAC' AS TYPE,  \n " +
			" 		CASE WHEN HDPL.DVKD_FK IS NOT NULL AND (HDPL.DOANHTHU_FK = 100000 OR HDPL.DOANHTHU_FK = 400000) THEN HDPL.DVKD_FK   \n " +
			" 		ELSE   \n " +
			" 		CASE WHEN HDPL.DVKD_FK <> 110000 AND HDPL.DOANHTHU_FK = 100004 THEN 100008  \n " +
			" 		ELSE 100007 END   \n " +
			" 		END AS MA1, '1' AS LSP, \n " +
			" 		ISNULL(HDPL.DVKD_FK,100007)  AS MA,   \n " +
			" 		HUYCT.NGAYHUY AS NGAYXUAT, ISNULL(HDPL_SP.DIENGIAI,'') + N'- (HỦY HÓA ĐƠN KHÁC)' AS SPTEN,   \n " +
			"		ISNULL(HDPL_SP.DONVITINH,'') AS DVT,    \n " +
			" 		 (-1)* HDPL_SP.SOLUONG AS SOLUONG, (-1) * ISNULL(HDPL_SP.SOLUONG*HDPL_SP.DONGIA,'0') AS THANHTIEN,   \n " +
			 		  
			" 		'0' AS , ''  AS MKT_SP, '3' AS LOAIHD,    \n " +
			" 		'0' AS DVDL, '0' as TRONGLUONG_QC \n " +
			 		 
			" 		FROM ERP_HOADONPHELIEU HDPL    \n " +
			"  		INNER JOIN ERP_HUYCHUNGTUBANHANG HUYCT ON HUYCT.SOCHUNGTU=HDPL.PK_SEQ  \n " +
			" 		INNER JOIN ERP_HOADONPHELIEU_SANPHAM HDPL_SP ON HDPL.PK_SEQ=HDPL_SP.HOADONPHELIEU_FK   \n " +
			" 		LEFT JOIN ERP_TRUNGTAMDOANHTHU TTDT ON HDPL.TRUNGTAMDOANHTHU_FK=TTDT.PK_SEQ   \n " +
			" 		WHERE  HUYCT.LOAICHUNGTU IN ('HOADONKHAC') AND 	    HUYCT.huykhackyketoan='1'  \n " +
			" 		and HUYCT.TRANGTHAI='1'  \n " +
			" 		AND  HUYCT.ngayhuy >= '" + tungay + "' AND HUYCT.ngayhuy <= '" + denngay + "'  AND HDPL.DOANHTHU_FK = 400000  \n " +

// Hóa đơn giảm giá
			" 		UNION all    \n " +

			" 		SELECT  'HDGG' AS TYPE ,   \n " +
			" 		ISNULL(GGHB.DVKD_FK, 100007) AS MA1,   \n " +
			" 		ISNULL(GGHB.DVKD_FK, 100007) AS MA,    \n " +
			" 		'1' AS LSP, GGHB.NGAYHOADON AS NGAYXUAT, GGHB.DIENGIAI AS SPTEN,    \n " +
			"		'' AS DVT, '0' AS SOLUONG,     \n " +
			"		CASE WHEN GGHB.TIENTE_FK = '100000' " +
			"		THEN gghd.SOTIENTANGGIAM " +
			"		ELSE (gghd.SOTIENTANGGIAM* ISNULL(GGHB.TIGIA,0) ) END AS THANHTIEN,    \n " +
			  		
			"  		'0' AS , ''  AS MKT_SP,   \n " +
			" 		'10' AS LOAIHD , '0' AS DVDL,   \n " +
			" 		'0' as TRONGLUONG_QC  \n " +
			 		
			"  		FROM  ERP_GIAMGIAHANGBAN GGHB    \n " +
			" 		INNER JOIN    \n " +
			" 		(    \n " +
			" 			SELECT GIAMGIA_FK, SUM(SOTIENTANGGIAM) AS TIENTG    \n " +
			" 			FROM ERP_GIAMGIAHANGBAN_HOADON    \n " +
			"			WHERE SOTIENTANGGIAM < 0   \n " +
			" 			GROUP BY GIAMGIA_FK    \n " +
			" 		) GGHB_HD ON GGHB.PK_SEQ = GGHB_HD.GIAMGIA_FK    \n " +
			" 		LEFT JOIN ERP_GIAMGIAHANGBAN_HOADON gghd on gghb.pk_seq= gghd.giamgia_fk    \n " + 
			" 		WHERE GGHB.TRANGTHAI NOT IN (0, 2)  \n " +
			" 		AND GGHB.NGAYHOADON >= '" + tungay + "' AND GGHB.NGAYHOADON <= '" + denngay + "'   \n " +

// Hủy hóa đơn giảm giá
			" 		UNION all   \n " +
			 		
			" 		SELECT  'HUY-HDGG' AS TYPE ,  \n " +
			" 		ISNULL(GGHB.DVKD_FK, 100007) AS MA1, \n " +
			" 		ISNULL(GGHB.DVKD_FK, 100007) AS MA,   \n " +
			" 		'1' AS LSP, GGHB.NGAYHOADON AS NGAYXUAT, GGHB.DIENGIAI +N'- HỦY HÓA ĐƠN GIẢM TĂNG GIÁ HÀNG BÁN' AS SPTEN,   \n " +
			"		'' AS DVT, 0 AS SOLUONG,  \n " +
			"		(-1)*  CASE WHEN GGHB.TIENTE_FK = '100000' THEN gghd.SOTIENTANGGIAM ELSE (gghd.SOTIENTANGGIAM* ISNULL(GGHB.TIGIA,0) ) END AS THANHTIEN, \n " +  
			  		
			"  		'0' AS , ''  AS MKT_SP,  \n " +
			" 		'3' AS LOAIHD, '0' AS DVDL,  \n " +
			" 		'0' as TRONGLUONG_QC \n " +
			 		
			"  		FROM  ERP_GIAMGIAHANGBAN GGHB   \n " +
			"  		INNER JOIN ERP_HUYCHUNGTUBANHANG HUYCT ON HUYCT.SOCHUNGTU=GGHB.PK_SEQ  \n " +
			" 		INNER JOIN   \n " +
			" 		(   \n " +
			" 			SELECT GIAMGIA_FK, SUM(SOTIENTANGGIAM) AS TIENTG   \n " +
			" 			FROM ERP_GIAMGIAHANGBAN_HOADON   \n " +
			"			WHERE SOTIENTANGGIAM < 0   \n " +
			" 			GROUP BY GIAMGIA_FK   \n " +
			" 		) GGHB_HD ON GGHB.PK_SEQ = GGHB_HD.GIAMGIA_FK   \n " +
			" 		LEFT JOIN ERP_GIAMGIAHANGBAN_HOADON gghd on gghb.pk_seq= gghd.giamgia_fk    \n " +
			" 		WHERE  HUYCT.LOAICHUNGTU IN ('TANGGIAMGIA') AND  HUYCT.huykhackyketoan='1' AND HUYCT.TRANGTHAI='1'  \n " +
			" 		and HUYCT.ngayhuy >= '" + tungay + "' AND HUYCT.ngayhuy <= '" + denngay + "' \n " +
			" 	) A WHERE 1 = 1   \n " +
			"  )B WHERE 1 = 1 AND B.DVKD <> 'L' \n " +
			" )DATA  \n " +
			" GROUP BY DATA.THANG \n " +
			" ORDER BY DATA.THANG \n " ;*/

		System.out.println("SalesAmount_LA_Return: " + query);
		data = prepareData(data, query);

		return data;
	}
	


	public String[] PL_SalesAmount_PA_Return_Budget(){
		String[] data = new String[13];
		String tungay = this.year + "-01-01";
		String denngay = this.year + "-" + (this.month.length() > 1?this.month:"0" + this.month) + "-31";
		String query = 	"SELECT THANG, (-1)*SUM(TONGGIATRI) AS NUM \n " +
						"FROM  \n " +
						"(  \n " +
//Nợ 5100/Có 5200 + Nợ 5100/Có 5300 + Nợ 5100/Có 532200						
						"	SELECT SUBSTRING(NGAYCHUNGTU, 6, 2) AS THANG, ISNULL(SUM(TONGGIATRI), 0) AS TONGGIATRI \n " +
						"	FROM ERP_PHATSINHKETOAN  \n " +
						"	WHERE NGAYCHUNGTU >= '" + tungay + "' AND NGAYCHUNGTU <= '" + denngay + "'  \n " +
						"	AND TAIKHOAN_FK = (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '5100' AND CONGTY_FK = " + this.ctyId + ")  \n " +
						"	AND TAIKHOANDOIUNG_FK = (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '5200' AND CONGTY_FK = " + this.ctyId + " )  \n " +
						"	GROUP BY NGAYCHUNGTU  \n " +

						"	UNION ALL  \n " +
						"	SELECT SUBSTRING(NGAYCHUNGTU, 6, 2) AS THANG, ISNULL(SUM(TONGGIATRI), 0) AS TONGGIATRI  \n " +
						"	FROM ERP_PHATSINHKETOAN \n " +
						"	WHERE NGAYCHUNGTU >= '" + tungay + "' AND NGAYCHUNGTU <= '" + denngay + "'  \n " +
						"	AND TAIKHOAN_FK = (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '5100' AND CONGTY_FK = " + this.ctyId + ")  \n " +
						"	AND TAIKHOANDOIUNG_FK = (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '5300' AND CONGTY_FK = " + this.ctyId + ")  \n " +
						"	GROUP BY NGAYCHUNGTU  \n " +

						"	UNION ALL \n " +
						"	SELECT  SUBSTRING(NGAYCHUNGTU, 6, 2) AS THANG, ISNULL(SUM(TONGGIATRI), 0) AS TONGGIATRI \n " +
						"	FROM ERP_PHATSINHKETOAN \n " +
						"	WHERE NGAYCHUNGTU >= '" + tungay + "' AND NGAYCHUNGTU <= '" + denngay + "' \n " +
						"	AND TAIKHOAN_FK = (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '5100' AND CONGTY_FK = " + this.ctyId + ") \n " +
						"	AND TAIKHOANDOIUNG_FK = (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '532200' AND CONGTY_FK = " + this.ctyId + ") \n " +
						"	GROUP BY NGAYCHUNGTU \n " +

//- Nợ 5200/Có 5100 - Nợ 5300/Có 5100 - Nợ 532200/Có 5100
						"	UNION ALL \n " +
						"	SELECT  SUBSTRING(NGAYCHUNGTU, 6, 2) AS THANG, (-1)*ISNULL(SUM(TONGGIATRI), 0) AS TONGGIATRI \n " +
						"	FROM ERP_PHATSINHKETOAN \n " +
						"	WHERE NGAYCHUNGTU >= '" + tungay + "' AND NGAYCHUNGTU <= '" + denngay + "' \n " +
						"	AND TAIKHOAN_FK = (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '5200' AND CONGTY_FK = " + this.ctyId + ") \n " +
						"	AND TAIKHOANDOIUNG_FK = (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '5100' AND CONGTY_FK = " + this.ctyId + ") \n " +
						"	GROUP BY NGAYCHUNGTU \n " +

						"	UNION ALL \n " +
						"	SELECT  SUBSTRING(NGAYCHUNGTU, 6, 2) AS THANG, (-1)*ISNULL(SUM(TONGGIATRI), 0) AS TONGGIATRI \n " +
						"	FROM ERP_PHATSINHKETOAN \n " +
						"	WHERE NGAYCHUNGTU >= '" + tungay + "' AND NGAYCHUNGTU <= '" + denngay + "' \n " +
						"	AND TAIKHOAN_FK = (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '5300' AND CONGTY_FK = " + this.ctyId + ") \n " +
						"	AND TAIKHOANDOIUNG_FK = (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '5100' AND CONGTY_FK = " + this.ctyId + ") \n " +
						"	GROUP BY NGAYCHUNGTU \n " +

						"	UNION ALL \n " +
						"	SELECT  SUBSTRING(NGAYCHUNGTU, 6, 2) AS THANG, (-1)*ISNULL(SUM(TONGGIATRI), 0) AS TONGGIATRI \n " +
						"	FROM ERP_PHATSINHKETOAN \n " +
						"	WHERE NGAYCHUNGTU >= '" + tungay + "' AND NGAYCHUNGTU <= '" + denngay + "' \n " +
						"	AND TAIKHOAN_FK = (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '532200' AND CONGTY_FK = " + this.ctyId + ") \n " +
						"	AND TAIKHOANDOIUNG_FK = (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '5100' AND CONGTY_FK = " + this.ctyId + ") \n " +
						"	GROUP BY NGAYCHUNGTU \n " +

						")DATA \n " +
						"GROUP BY THANG \n " +
						"ORDER BY THANG \n ";
		
/*		String query = 	
			"SELECT THANG, SUM(THANHTIEN) AS NUM \n " +
			"FROM  \n " +
			"( \n " +
			"	SELECT SUBSTRING(NGAYXUAT, 6, 2) AS THANG,  \n " +
			"	THANHTIEN \n " +
			"	FROM \n " +
			"	(  \n " +
			" 		SELECT  type,  \n " +
			" 		CASE WHEN A.LSP IN (100013,100014,100015,100016,100017) THEN 'NL'  \n " +
			" 		ELSE   \n " +
			" 			 CASE WHEN A.MA = 100000 THEN 'N'   \n " +
			" 		ELSE CASE WHEN A.MA = 100004 THEN 'L'   \n " +
			" 		ELSE CASE WHEN A.MA = 100005 THEN 'SPM'   \n " +
			" 		ELSE CASE WHEN A.MA = 100007 THEN 'K'  \n " +
			" 		ELSE CASE WHEN A.MA = 100008 THEN 'GC'  \n " +
			" 		ELSE 'K'   \n " +
			" 		END END END END END  \n " +
			" 		END AS DVKD,    \n " +
			 	
			" 		CASE WHEN A.MA1 = 100000 THEN N'Nhôm'   \n " +
			" 		  WHEN A.MA1 = 100004 THEN N'Lõi'   \n " +
			" 		  WHEN A.MA1= 100005 THEN N'SPM'   \n " +
			" 		ELSE N'Khác'   \n " +
			" 		END   as donvikinhdoanh,  \n " +
			" 	A.LSP,    \n " +
			" 	A.NGAYXUAT, A.SPTEN,    \n " +
			" 	A.SOLUONG,   \n " +
			"	A.THANHTIEN, A.,    \n " +
			"    A.MKT_SP,    \n " +
			"	A.DVDL, A.TRONGLUONG_QC \n " +
			" 	FROM    \n " +
			" 	(  \n " +

			
// Lấy các chiết khấu trong hóa đơn tài chính
			" 		SELECT 'CHIETKHAU' AS TYPE, ( SELECT TOP  1  CASE WHEN  DH.LOAIDONHANG ='2' THEN 100008 ELSE SP.DVKD_FK END    \n " +
			" 		FROM ERP_DONDATHANG DH  INNER JOIN ERP_XUATKHO XK ON XK.DONDATHANG_FK= DH.PK_SEQ   				 \n " +
			" 		INNER JOIN ERP_HOADON_XUATKHO HDXK ON HDXK.XUATKHO_FK=XK.PK_SEQ WHERE HDXK.hoadon_fk= HD.PK_SEQ  \n " +
			" 		) AS MA  , SP.DVKD_FK AS MA1 , LSP.PK_SEQ AS LSP,    \n " +
			" 		HD.NGAYXUATHD AS NGAYXUAT,   \n " +
			 		
			" 		SP.TEN AS SPTEN,    \n " +
			 		
			"		HD_SP.DONVITINH AS DVT,    \n " +
			" 		0 AS SOLUONG,   \n " +
			" 		(-1)*HD_SP.SOLUONG*HD_SP.DONGIA*(THANHTIEN.THANHTIEN*HD.CHIETKHAU/100)/THANHTIEN.THANHTIEN AS THANHTIEN, \n " +
			 
			" 		'' AS , '' AS MKT_SP,   \n " +
			"		HD.LOAIHD,    \n " +
			"		isnull(HD_SP.DVDL_FK,0) AS DVDL,  \n " +
			"		CASE  WHEN HD_SP.DVDL_FK =100003 THEN 1     \n " +
			" 		WHEN QC2.DVDL1_FK IS NOT NULL THEN   ISNULL( CAST(QC2.SOLUONG2 AS FLOAT ), 0)  / ISNULL( CAST(QC2.SOLUONG1 AS FLOAT) , 1)    \n " +
			"		WHEN   QC3.DVDL1_FK IS NOT NULL THEN   ISNULL(CAST(QC3.SOLUONG1 AS FLOAT) , 0)  / ISNULL( CAST(QC3.SOLUONG2 AS FLOAT ), 1)   \n " +
			" 		WHEN QC4.DVDL2_FK IS NOT NULL THEN (   ISNULL( CAST(QC4.SOLUONG2 AS FLOAT) , 0)  / ISNULL(CAST(QC4.SOLUONG1 AS FLOAT), 1) ) *  CAST(D.SOLUONG1 AS FLOAT)/ CAST(D.SOLUONG2 AS FLOAT)   \n " +  
			" 		ELSE 0  END AS TRONGLUONG_QC   \n " +
			" 		FROM ERP_HOADON HD    \n " +
			" 		INNER JOIN ERP_HOADON_SP HD_SP ON HD.PK_SEQ=HD_SP.HOADON_FK     \n " +
			" 		INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ= HD_SP.     \n " +
			"		INNER JOIN  \n " +
			"		( \n " +
			"			SELECT HOADON_FK, SUM(SOLUONG*DONGIA) AS THANHTIEN \n " +
			"			FROM ERP_HOADON_SP \n " +
			"			GROUP BY HOADON_FK \n " +
			"		)THANHTIEN ON THANHTIEN.HOADON_FK = HD.PK_SEQ \n " +

			" 		LEFT JOIN ERP_MAKETOAN MKT ON SP.MAKETOAN_FK= MKT.PK_SEQ     \n " +
			" 		LEFT JOIN ERP_LOAISANPHAM LSP ON SP.LOAI= LSP.PK_SEQ     \n " +
			"  		LEFT JOIN QUYCACH D ON HD_SP. = d.  AND HD_SP.DVDL_FK=D.DVDL2_FK   		  \n " +
			"  		LEFT JOIN QUYCACH QC2 ON QC2.=SP.PK_SEQ AND QC2.DVDL2_FK=100003  AND QC2.DVDL1_FK=HD_SP.DVDL_FK    \n " +		
			"  		LEFT JOIN QUYCACH QC3 ON QC3.=SP.PK_SEQ AND QC3.DVDL1_FK=100003  AND QC3.DVDL2_FK=HD_SP.DVDL_FK  	  \n " +	
			"  		LEFT JOIN QUYCACH QC4 ON QC4.=SP.PK_SEQ AND QC4.DVDL2_FK= 100003 		  \n " +
			"  		LEFT JOIN DONVIDOLUONG C ON HD_SP.DVDL_FK = C.PK_SEQ     \n " +
			" 		INNER JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ = SP.DVKD_FK     \n " +
			" 		LEFT JOIN QUYCACH QC ON HD_SP.DVDL_FK=QC.DVDL2_FK AND HD_SP.=QC.   \n " +
			" 		WHERE HD.TRANGTHAI NOT IN (0, 2) and isnull(is_hangmau, 0)<> 1 AND HD.LOAIHD = 1 AND HD.CHIETKHAU > 0 \n " +
			"		AND HD.NGAYXUATHD >= '" + tungay + "' AND HD.NGAYXUATHD <= '" + denngay + "'  \n " +

// Hủy hóa đơn có chiếu khấu
			"  UNION ALL 		 \n " +
			"  SELECT 'HUYCHIETKHAU' AS TYPE,  SP.DVKD_FK, SP.DVKD_FK AS MA, LSP.PK_SEQ AS LSP,    \n " +
			"		HUYCT.ngayhuy AS NGAYXUAT,  \n " +
			" 		CASE WHEN HD.LOAIHD = 1 THEN SP.TEN ELSE SP.TEN + N' - (HỦY HÓA ĐƠN HÀNG TRẢ VỀ)' END  AS SPTEN,    \n " +
			"		HD_SP.DONVITINH AS DVT,   \n " +
			" 		0 AS SOLUONG,  \n " +
			" 		HD_SP.SOLUONG*HD_SP.DONGIA*(THANHTIEN.THANHTIEN*HD.CHIETKHAU/100)/THANHTIEN.THANHTIEN AS THANHTIEN, \n " +
		 	  
			" 		SP.MA AS , ISNULL(MKT.MA,'') AS MKT_SP, HD.LOAIHD,   \n " +
			" 		isnull(HD_SP.DVDL_FK,0) AS DVDL,  \n " +
			 		 
			" 		 CASE  WHEN HD_SP.DVDL_FK =100003 THEN 1    \n " +
			" 		 WHEN QC2.DVDL1_FK IS NOT NULL THEN   ISNULL( CAST(QC2.SOLUONG2 AS FLOAT ), 0)  / ISNULL( CAST(QC2.SOLUONG1 AS FLOAT) , 1)  \n " + 
			"		 WHEN   QC3.DVDL1_FK IS NOT NULL THEN   ISNULL(CAST(QC3.SOLUONG1 AS FLOAT) , 0)  / ISNULL( CAST(QC3.SOLUONG2 AS FLOAT ), 1)  \n " +
			" 		 WHEN QC4.DVDL2_FK IS NOT NULL THEN (   ISNULL( CAST(QC4.SOLUONG2 AS FLOAT) , 0)  / ISNULL(CAST(QC4.SOLUONG1 AS FLOAT), 1) ) *  CAST(D.SOLUONG1 AS FLOAT)/ CAST(D.SOLUONG2 AS FLOAT)  \n " + 
			"		 ELSE 0  END AS TRONGLUONG_QC \n " +
					 
			" 		FROM ERP_HOADON HD   \n " +
			"  		INNER JOIN ERP_HUYCHUNGTUBANHANG HUYCT ON HUYCT.SOCHUNGTU=HD.PK_SEQ  \n " +
			" 		INNER JOIN ERP_HOADON_SP HD_SP ON HD.PK_SEQ=HD_SP.HOADON_FK    \n " +
			" 		INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ= HD_SP.    \n " +
			"		INNER JOIN  \n " +
			"		( \n " +
			"			SELECT HOADON_FK, SUM(SOLUONG*DONGIA) AS THANHTIEN \n " +
			"			FROM ERP_HOADON_SP \n " +
			"			GROUP BY HOADON_FK \n " +
			"		)THANHTIEN ON THANHTIEN.HOADON_FK = HD.PK_SEQ \n " +

			" 		LEFT JOIN ERP_MAKETOAN MKT ON SP.MAKETOAN_FK= MKT.PK_SEQ    \n " +
			" 		LEFT JOIN ERP_LOAISANPHAM LSP ON SP.LOAI= LSP.PK_SEQ    \n " +
			"		LEFT JOIN QUYCACH D ON HD_SP. = d.  AND HD_SP.DVDL_FK=D.DVDL2_FK   		 \n " +
			"		LEFT JOIN QUYCACH QC2 ON QC2.=SP.PK_SEQ AND QC2.DVDL2_FK=100003  AND QC2.DVDL1_FK=HD_SP.DVDL_FK  		  \n " +
			"		LEFT JOIN QUYCACH QC3 ON QC3.=SP.PK_SEQ AND QC3.DVDL1_FK=100003  AND QC3.DVDL2_FK=HD_SP.DVDL_FK  		 \n " +
			"		LEFT JOIN QUYCACH QC4 ON QC4.=SP.PK_SEQ AND QC4.DVDL2_FK= 100003 		 \n " +
			"		LEFT JOIN DONVIDOLUONG C ON HD_SP.DVDL_FK = C.PK_SEQ    \n " +
			" 		INNER JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ = SP.DVKD_FK    \n " +
			" 		LEFT JOIN QUYCACH QC ON HD_SP.DVDL_FK=QC.DVDL2_FK AND HD_SP.=QC.  \n " +
			" 		WHERE HD.TRANGTHAI NOT IN (0, 2) and isnull(is_hangmau, 0)<> 1   \n " +
			" 		AND  HUYCT.LOAICHUNGTU IN ('HOADON_KHTRA') AND 	    HUYCT.huykhackyketoan='1' and HUYCT.TRANGTHAI='1' AND HD.LOAIHD = 1 AND HD.CHIETKHAU > 0   \n " +
			" 		AND  HUYCT.ngayhuy >= '" + tungay + "' AND HUYCT.ngayhuy <= '" + denngay + "' \n " +


// Lấy các hoá đơn hàng trả về
			" UNION ALL \n " +
			" 		SELECT 'HDTRAVE' AS TYPE, ( SELECT TOP  1  CASE WHEN  DH.LOAIDONHANG ='2' THEN 100008 ELSE SP.DVKD_FK END    \n " +
			" 		FROM ERP_DONDATHANG DH  INNER JOIN ERP_XUATKHO XK ON XK.DONDATHANG_FK= DH.PK_SEQ   				 \n " +
			" 		INNER JOIN ERP_HOADON_XUATKHO HDXK ON HDXK.XUATKHO_FK=XK.PK_SEQ WHERE HDXK.hoadon_fk= HD.PK_SEQ  \n " +
			" 		) AS MA  , SP.DVKD_FK AS MA1 , LSP.PK_SEQ AS LSP,    \n " +
			" 		HD.NGAYXUATHD AS NGAYXUAT,   \n " +
			 		
			" 		SP.TEN AS SPTEN,    \n " +
			 		
			"		HD_SP.DONVITINH AS DVT,    \n " +
			" 		(-1)*HD_SP.SOLUONG AS SOLUONG,   \n " +
			" 		(-1)*ISNULL(ISNULL(HD_SP.DONGIAVIET,HD_SP.DONGIA)* HD_SP.SOLUONG,'0') AS THANHTIEN,   \n " +
			 
			" 		SP.MA AS , ISNULL(MKT.MA,'') AS MKT_SP,   \n " +
			"		HD.LOAIHD,    \n " +
			"		isnull(HD_SP.DVDL_FK,0) AS DVDL,  \n " +
			"		CASE  WHEN HD_SP.DVDL_FK =100003 THEN 1     \n " +
			" 		WHEN QC2.DVDL1_FK IS NOT NULL THEN   ISNULL( CAST(QC2.SOLUONG2 AS FLOAT ), 0)  / ISNULL( CAST(QC2.SOLUONG1 AS FLOAT) , 1)    \n " +
			"		WHEN   QC3.DVDL1_FK IS NOT NULL THEN   ISNULL(CAST(QC3.SOLUONG1 AS FLOAT) , 0)  / ISNULL( CAST(QC3.SOLUONG2 AS FLOAT ), 1)   \n " +
			" 		WHEN QC4.DVDL2_FK IS NOT NULL THEN (   ISNULL( CAST(QC4.SOLUONG2 AS FLOAT) , 0)  / ISNULL(CAST(QC4.SOLUONG1 AS FLOAT), 1) ) *  CAST(D.SOLUONG1 AS FLOAT)/ CAST(D.SOLUONG2 AS FLOAT)   \n " +  
			" 		ELSE 0  END AS TRONGLUONG_QC   \n " +
			" 		FROM ERP_HOADON HD    \n " +
			" 		INNER JOIN ERP_HOADON_SP HD_SP ON HD.PK_SEQ=HD_SP.HOADON_FK     \n " +
			" 		INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ= HD_SP.     \n " +
			" 		LEFT JOIN ERP_MAKETOAN MKT ON SP.MAKETOAN_FK= MKT.PK_SEQ     \n " +
			" 		LEFT JOIN ERP_LOAISANPHAM LSP ON SP.LOAI= LSP.PK_SEQ     \n " +
			"  		LEFT JOIN QUYCACH D ON HD_SP. = d.  AND HD_SP.DVDL_FK=D.DVDL2_FK   		  \n " +
			"  		LEFT JOIN QUYCACH QC2 ON QC2.=SP.PK_SEQ AND QC2.DVDL2_FK=100003  AND QC2.DVDL1_FK=HD_SP.DVDL_FK    \n " +		
			"  		LEFT JOIN QUYCACH QC3 ON QC3.=SP.PK_SEQ AND QC3.DVDL1_FK=100003  AND QC3.DVDL2_FK=HD_SP.DVDL_FK  	  \n " +	
			"  		LEFT JOIN QUYCACH QC4 ON QC4.=SP.PK_SEQ AND QC4.DVDL2_FK= 100003 		  \n " +
			"  		LEFT JOIN DONVIDOLUONG C ON HD_SP.DVDL_FK = C.PK_SEQ     \n " +
			" 		INNER JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ = SP.DVKD_FK     \n " +
			" 		LEFT JOIN QUYCACH QC ON HD_SP.DVDL_FK=QC.DVDL2_FK AND HD_SP.=QC.   \n " +
			" 		WHERE HD.TRANGTHAI NOT IN (0, 2) and isnull(is_hangmau, 0)<> 1 AND HD.LOAIHD = 2   \n " +
			"		AND HD.NGAYXUATHD >= '" + tungay + "' AND HD.NGAYXUATHD <= '" + denngay + "'  \n " +

// Hủy hóa đơn hàng trả về
			"  UNION ALL 		 \n " +
			"  SELECT 'HUYHDTRAVE' AS TYPE,  SP.DVKD_FK, SP.DVKD_FK AS MA, LSP.PK_SEQ AS LSP,    \n " +
			"		HUYCT.ngayhuy AS NGAYXUAT,  \n " +
			" 		CASE WHEN HD.LOAIHD = 1 THEN SP.TEN ELSE SP.TEN + N' - (HỦY HÓA ĐƠN HÀNG TRẢ VỀ)' END  AS SPTEN,    \n " +
			"		HD_SP.DONVITINH AS DVT,   \n " +
			" 		HD_SP.SOLUONG AS SOLUONG,  \n " +
			" 		ISNULL(HD_SP.DONGIAVIET* HD_SP.SOLUONG,'0') AS THANHTIEN,  \n " +
			 	  
			" 		SP.MA AS , ISNULL(MKT.MA,'') AS MKT_SP, HD.LOAIHD,   \n " +
			" 		isnull(HD_SP.DVDL_FK,0) AS DVDL,  \n " +
			 		 
			" 		 CASE  WHEN HD_SP.DVDL_FK =100003 THEN 1    \n " +
			" 		 WHEN QC2.DVDL1_FK IS NOT NULL THEN   ISNULL( CAST(QC2.SOLUONG2 AS FLOAT ), 0)  / ISNULL( CAST(QC2.SOLUONG1 AS FLOAT) , 1)  \n " + 
			"		 WHEN   QC3.DVDL1_FK IS NOT NULL THEN   ISNULL(CAST(QC3.SOLUONG1 AS FLOAT) , 0)  / ISNULL( CAST(QC3.SOLUONG2 AS FLOAT ), 1)  \n " +
			" 		 WHEN QC4.DVDL2_FK IS NOT NULL THEN (   ISNULL( CAST(QC4.SOLUONG2 AS FLOAT) , 0)  / ISNULL(CAST(QC4.SOLUONG1 AS FLOAT), 1) ) *  CAST(D.SOLUONG1 AS FLOAT)/ CAST(D.SOLUONG2 AS FLOAT)  \n " + 
			"		 ELSE 0  END AS TRONGLUONG_QC \n " +
					 
			" 		FROM ERP_HOADON HD   \n " +
			"  		INNER JOIN ERP_HUYCHUNGTUBANHANG HUYCT ON HUYCT.SOCHUNGTU=HD.PK_SEQ  \n " +
			" 		INNER JOIN ERP_HOADON_SP HD_SP ON HD.PK_SEQ=HD_SP.HOADON_FK    \n " +
			" 		INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ= HD_SP.    \n " +
			" 		LEFT JOIN ERP_MAKETOAN MKT ON SP.MAKETOAN_FK= MKT.PK_SEQ    \n " +
			" 		LEFT JOIN ERP_LOAISANPHAM LSP ON SP.LOAI= LSP.PK_SEQ    \n " +
			"		LEFT JOIN QUYCACH D ON HD_SP. = d.  AND HD_SP.DVDL_FK=D.DVDL2_FK   		 \n " +
			"		LEFT JOIN QUYCACH QC2 ON QC2.=SP.PK_SEQ AND QC2.DVDL2_FK=100003  AND QC2.DVDL1_FK=HD_SP.DVDL_FK  		  \n " +
			"		LEFT JOIN QUYCACH QC3 ON QC3.=SP.PK_SEQ AND QC3.DVDL1_FK=100003  AND QC3.DVDL2_FK=HD_SP.DVDL_FK  		 \n " +
			"		LEFT JOIN QUYCACH QC4 ON QC4.=SP.PK_SEQ AND QC4.DVDL2_FK= 100003 		 \n " +
			"		LEFT JOIN DONVIDOLUONG C ON HD_SP.DVDL_FK = C.PK_SEQ    \n " +
			" 		INNER JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ = SP.DVKD_FK    \n " +
			" 		LEFT JOIN QUYCACH QC ON HD_SP.DVDL_FK=QC.DVDL2_FK AND HD_SP.=QC.  \n " +
			" 		WHERE HD.TRANGTHAI NOT IN (0, 2) and isnull(is_hangmau, 0)<> 1   \n " +
			" 		AND  HUYCT.LOAICHUNGTU IN ('HOADON_KHTRA') AND 	    HUYCT.huykhackyketoan='1' and HUYCT.TRANGTHAI='1'  AND HD.LOAIHD = 2   \n " +
			" 		AND  HUYCT.ngayhuy >= '" + tungay + "' AND HUYCT.ngayhuy <= '" + denngay + "' \n " +

//  lấy tổng tiền trước thuế của loại hoá đơn chiết khấu trong hoá đơn khác
			" UNION ALL   \n " +
			" 		SELECT  'HDKHAC' AS TYPE,  \n " +
			" 		CASE WHEN HDPL.DVKD_FK IS NOT NULL AND (HDPL.DOANHTHU_FK = 100000 OR HDPL.DOANHTHU_FK = 400000) THEN HDPL.DVKD_FK   \n " +
			" 		ELSE   \n " +
			" 		CASE WHEN HDPL.DVKD_FK <> 110000 AND HDPL.DOANHTHU_FK = 100004 THEN 100008  \n " +
			" 		ELSE 100007 END   \n " +
			" 		END AS MA1,    \n " +
			 		
			" 		ISNULL(HDPL.DVKD_FK,100007) AS MA,   \n " +
			" 		'1' AS LSP, NGAYHOADON AS NGAYXUAT, ISNULL(HDPL_SP.DIENGIAI,'') AS SPTEN,   \n " +
			"		ISNULL(HDPL_SP.DONVITINH,'') AS DVT,    \n " +
			" 		HDPL_SP.SOLUONG AS SOLUONG, ISNULL(HDPL_SP.THANHTIEN,'0') AS THANHTIEN,   \n " +
			  
			" 		'0' AS , ''  AS MKT_SP,    \n " +
			" 		'1' AS LOAIHD,    \n " +
			" 		'0' AS DVDL,  \n " +
			 		
			" 		'0' as TRONGLUONG_QC \n " +
			 		 
			" 		FROM ERP_HOADONPHELIEU HDPL    \n " +
			" 		INNER JOIN ERP_HOADONPHELIEU_SANPHAM HDPL_SP ON HDPL.PK_SEQ=HDPL_SP.HOADONPHELIEU_FK   \n " +
			" 		LEFT JOIN ERP_TRUNGTAMDOANHTHU TTDT ON HDPL.TRUNGTAMDOANHTHU_FK=TTDT.PK_SEQ   \n " +
			" 		WHERE HDPL.TRANGTHAI NOT IN (0, 2)  \n " +
			" 		AND  HDPL.NGAYHOADON >= '" + tungay + "' AND HDPL.NGAYHOADON <= '" + denngay + "' AND HDPL.DOANHTHU_FK = 400000 \n " +

//Hủy hóa đon chiết khấu trong hóa đơn khác
			" 		UNION ALL   \n " +
			" 		SELECT  'HUY_HDKHAC' AS TYPE,  \n " +
			" 		CASE WHEN HDPL.DVKD_FK IS NOT NULL AND (HDPL.DOANHTHU_FK = 100000 OR HDPL.DOANHTHU_FK = 400000) THEN HDPL.DVKD_FK   \n " +
			" 		ELSE   \n " +
			" 		CASE WHEN HDPL.DVKD_FK <> 110000 AND HDPL.DOANHTHU_FK = 100004 THEN 100008  \n " +
			" 		ELSE 100007 END   \n " +
			" 		END AS MA1, '1' AS LSP, \n " +
			" 		ISNULL(HDPL.DVKD_FK,100007)  AS MA,   \n " +
			" 		HUYCT.NGAYHUY AS NGAYXUAT, ISNULL(HDPL_SP.DIENGIAI,'') + N'- (HỦY HÓA ĐƠN KHÁC)' AS SPTEN,   \n " +
			"		ISNULL(HDPL_SP.DONVITINH,'') AS DVT,    \n " +
			" 		 (-1)* HDPL_SP.SOLUONG AS SOLUONG, (-1) * ISNULL(HDPL_SP.SOLUONG*HDPL_SP.DONGIA,'0') AS THANHTIEN,   \n " +
			 		  
			" 		'0' AS , ''  AS MKT_SP, '3' AS LOAIHD,    \n " +
			" 		'0' AS DVDL, '0' as TRONGLUONG_QC \n " +
			 		 
			" 		FROM ERP_HOADONPHELIEU HDPL    \n " +
			"  		INNER JOIN ERP_HUYCHUNGTUBANHANG HUYCT ON HUYCT.SOCHUNGTU=HDPL.PK_SEQ  \n " +
			" 		INNER JOIN ERP_HOADONPHELIEU_SANPHAM HDPL_SP ON HDPL.PK_SEQ=HDPL_SP.HOADONPHELIEU_FK   \n " +
			" 		LEFT JOIN ERP_TRUNGTAMDOANHTHU TTDT ON HDPL.TRUNGTAMDOANHTHU_FK=TTDT.PK_SEQ   \n " +
			" 		WHERE  HUYCT.LOAICHUNGTU IN ('HOADONKHAC') AND 	    HUYCT.huykhackyketoan='1'  \n " +
			" 		and HUYCT.TRANGTHAI='1'  \n " +
			" 		AND  HUYCT.ngayhuy >= '" + tungay + "' AND HUYCT.ngayhuy <= '" + denngay + "'  AND HDPL.DOANHTHU_FK = 400000  \n " +

// Hóa đơn giảm giá
			" 		UNION all    \n " +

			" 		SELECT  'HDGG' AS TYPE ,   \n " +
			" 		ISNULL(GGHB.DVKD_FK, 100007) AS MA1,   \n " +
			" 		ISNULL(GGHB.DVKD_FK, 100007) AS MA,    \n " +
			" 		'1' AS LSP, GGHB.NGAYHOADON AS NGAYXUAT, GGHB.DIENGIAI AS SPTEN,    \n " +
			"		'' AS DVT, '0' AS SOLUONG,     \n " +
			"		CASE WHEN GGHB.TIENTE_FK = '100000' " +
			"		THEN gghd.SOTIENTANGGIAM " +
			"		ELSE (gghd.SOTIENTANGGIAM* ISNULL(GGHB.TIGIA,0) ) END AS THANHTIEN,    \n " +
			  		
			"  		'0' AS , ''  AS MKT_SP,   \n " +
			" 		'10' AS LOAIHD , '0' AS DVDL,   \n " +
			" 		'0' as TRONGLUONG_QC  \n " +
			 		
			"  		FROM  ERP_GIAMGIAHANGBAN GGHB    \n " +
			" 		INNER JOIN    \n " +
			" 		(    \n " +
			" 			SELECT GIAMGIA_FK, SUM(SOTIENTANGGIAM) AS TIENTG    \n " +
			" 			FROM ERP_GIAMGIAHANGBAN_HOADON    \n " +
			"			WHERE SOTIENTANGGIAM < 0   \n " +
			" 			GROUP BY GIAMGIA_FK    \n " +
			" 		) GGHB_HD ON GGHB.PK_SEQ = GGHB_HD.GIAMGIA_FK    \n " +
			" 		LEFT JOIN ERP_GIAMGIAHANGBAN_HOADON gghd on gghb.pk_seq= gghd.giamgia_fk    \n " + 
			" 		WHERE GGHB.TRANGTHAI NOT IN (0, 2)  \n " +
			" 		AND GGHB.NGAYHOADON >= '" + tungay + "' AND GGHB.NGAYHOADON <= '" + denngay + "'   \n " +

// Hủy hóa đơn giảm giá
			" 		UNION all   \n " +
			 		
			" 		SELECT  'HUY-HDGG' AS TYPE ,  \n " +
			" 		ISNULL(GGHB.DVKD_FK, 100007) AS MA1, \n " +
			" 		ISNULL(GGHB.DVKD_FK, 100007) AS MA,   \n " +
			" 		'1' AS LSP, GGHB.NGAYHOADON AS NGAYXUAT, GGHB.DIENGIAI +N'- HỦY HÓA ĐƠN GIẢM TĂNG GIÁ HÀNG BÁN' AS SPTEN,   \n " +
			"		'' AS DVT, 0 AS SOLUONG,  \n " +
			"		(-1)*  CASE WHEN GGHB.TIENTE_FK = '100000' THEN gghd.SOTIENTANGGIAM ELSE (gghd.SOTIENTANGGIAM* ISNULL(GGHB.TIGIA,0) ) END AS THANHTIEN, \n " +  
			  		
			"  		'0' AS , ''  AS MKT_SP,  \n " +
			" 		'3' AS LOAIHD, '0' AS DVDL,  \n " +
			" 		'0' as TRONGLUONG_QC \n " +
			 		
			"  		FROM  ERP_GIAMGIAHANGBAN GGHB   \n " +
			"  		INNER JOIN ERP_HUYCHUNGTUBANHANG HUYCT ON HUYCT.SOCHUNGTU=GGHB.PK_SEQ  \n " +
			" 		INNER JOIN   \n " +
			" 		(   \n " +
			" 			SELECT GIAMGIA_FK, SUM(SOTIENTANGGIAM) AS TIENTG   \n " +
			" 			FROM ERP_GIAMGIAHANGBAN_HOADON   \n " +
			"			WHERE SOTIENTANGGIAM < 0   \n " +
			" 			GROUP BY GIAMGIA_FK   \n " +
			" 		) GGHB_HD ON GGHB.PK_SEQ = GGHB_HD.GIAMGIA_FK   \n " +
			" 		LEFT JOIN ERP_GIAMGIAHANGBAN_HOADON gghd on gghb.pk_seq= gghd.giamgia_fk    \n " +
			" 		WHERE  HUYCT.LOAICHUNGTU IN ('TANGGIAMGIA') AND  HUYCT.huykhackyketoan='1' AND HUYCT.TRANGTHAI='1'  \n " +
			" 		and HUYCT.ngayhuy >= '" + tungay + "' AND HUYCT.ngayhuy <= '" + denngay + "' \n " +
			" 	) A WHERE 1 = 1   \n " +
			"  )B WHERE 1 = 1 AND B.DVKD = 'L' \n " +
			" )DATA  \n " +
			" GROUP BY DATA.THANG \n " +
			" ORDER BY DATA.THANG \n " ;
*/
		System.out.println("SalesAmount_PA_Return: " + query);
		data = prepareData(data, query);

		return data;
	}
	
	public String[] PL_SalesAmount_Others_Budget(){
		String[] data = new String[13];
		
		String query =  
					"SELECT THANG, SUM(NUM) AS NUM FROM ( \n " +
						"SELECT SUBSTRING(HD.NGAYXUATHD, 6, 2) AS THANG, SUM(HD_SP.TIENAVAT) AS NUM \n " +
						"FROM ERP_HOADON HD  \n " +
						"INNER JOIN ERP_HOADON_SP HD_SP ON HD_SP.HOADON_FK = HD.PK_SEQ  \n " +
						"WHERE HD.CONGTY_FK = " + this.ctyId + " AND SUBSTRING(HD.NGAYXUATHD, 1, 4) = '" + this.year + "'  \n " +
						"AND HD.TRANGTHAI NOT IN (0, 2) AND HD.LOAIHD = 1 \n " + 
						"AND HD_SP. NOT IN ( SELECT PK_SEQ from ERP_SANPHAM WHERE DVKD_FK IN (100000, 100004))  \n " + 
						"GROUP BY SUBSTRING(HD.NGAYXUATHD, 6, 2)  \n " +
						
						"UNION ALL \n " +
						
						"SELECT SUBSTRING(HD.NGAYHOADON, 6, 2) AS THANG, SUM(HD.AVAT) AS NUM   \n " +
						"FROM ERP_HOADONPHELIEU HD    \n " +
						  
						"WHERE HD.CONGTY_FK = " + this.ctyId + " AND SUBSTRING(HD.NGAYHOADON, 1, 4) = '" + this.year + "'    \n " +
						"AND HD.TRANGTHAI = 1  \n " +
						"GROUP BY SUBSTRING(HD.NGAYHOADON, 6, 2)    \n " +
					")OTHERS GROUP BY OTHERS.THANG ";
		
		System.out.println("SalesAmount_Others: " + query);
		data = prepareData(data, query);

		return data;
	}
	
	public String[] PL_SalesAmount_Others_Return_Budget(){
		String[] data = new String[13];
		
		String query =  					
						"SELECT SUBSTRING(HD.NGAYXUATHD, 6, 2) AS THANG, (-1)*SUM(HD_SP.TIENAVAT) AS NUM \n " +
						"FROM ERP_HOADON HD  \n " +
						"INNER JOIN ERP_HOADON_SP HD_SP ON HD_SP.HOADON_FK = HD.PK_SEQ  \n " +
						"WHERE HD.CONGTY_FK = " + this.ctyId + " AND SUBSTRING(HD.NGAYXUATHD, 1, 4) = '" + this.year + "'  \n " +
						"AND HD.TRANGTHAI NOT IN (0, 2) AND HD.LOAIHD = 2 AND HD_SP. NOT IN ( SELECT PK_SEQ from ERP_SANPHAM WHERE DVKD_FK IN (100000, 100004))  \n " + 
						"GROUP BY SUBSTRING(HD.NGAYXUATHD, 6, 2)  \n " +
						"ORDER BY SUBSTRING(HD.NGAYXUATHD, 6, 2) ";
		
		System.out.println("SalesAmount_Return_Others_NewProduct: " + query);
		data = prepareData(data, query);

		return data;
	}

	
	public String[] PL_MaterialConsume_Budget(){
		String[] data = new String[13];
		
		String query =  ""; 
		
		System.out.println("4.MATERIALS CONSUMED: " + query);
		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_MaterialConsume_Paper_Budget(){
		String[] data = new String[13];
		
		String query =  ""; 
		
		System.out.println("4.MATERIALS CONSUMED: " + query);
		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_MaterialConsume_Foil_Budget(){
		String[] data = new String[13];
		
		String query =  ""; 
		
		System.out.println("4.MATERIALS CONSUMED: " + query);
		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_MaterialConsume_Glue_Budget(){
		String[] data = new String[13];
		
		String query =  ""; 
		
		System.out.println("4.MATERIALS CONSUMED: " + query);
		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_MaterialConsume_Lacquer_Budget(){
		String[] data = new String[13];
		
		String query =  ""; 
		
		System.out.println("4.MATERIALS CONSUMED: " + query);
		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_MaterialConsume_Submaterial_Budget(){
		String[] data = new String[13];
		
		String query =  ""; 
		
		System.out.println("4.MATERIALS CONSUMED: " + query);
		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_MaterialConsume_LA_Budget(){
		String[] data = new String[13];
		
		String query =  ""; 
		
		System.out.println("4.MATERIALS CONSUMED_LA: " + query);
		data = prepareData(data, query);
		return data;
	}

	
	public String[] PL_MaterialConsume_PA_Budget(){
		String[] data = new String[13];
		
		String query =  ""; 
		
		System.out.println("4.MATERIALS CONSUMED_LA: " + query);
		data = prepareData(data, query);
		return data;
	}

	public String[] PL_MaterialConsume_NL_Budget(){
		String[] data = new String[13];
		
		String query = "SELECT NL.THANG, SUM(NL.SOLUONG*BGNL_NL.GIAMUA) AS NUM \n " +
						"FROM \n " +
						"( \n " +
						"	SELECT NS_DBSP.THANG, NS_DBSP.NAM, NS_DBSP.SANPHAM_FK, DMVT_VT.VATTU_FK, NS_DBSP.DUKIENBAN*DMVT_VT.SOLUONG /DMVT.SOLUONGCHUAN AS SOLUONG \n " +
						"	FROM ERP_LAPNGANSACH_DUBAOSANPHAM NS_DBSP \n " +
						"	INNER JOIN ERP_DANHMUCVATTU DMVT ON DMVT.SANPHAM_FK = NS_DBSP.SANPHAM_FK  \n " +
						"	INNER JOIN ERP_DANHMUCVATTU_VATTU DMVT_VT ON DMVT_VT.DANHMUCVT_FK = DMVT.PK_SEQ \n " +
						"	WHERE NS_DBSP.LAPNGANSACH_FK = " + this.Id + "  \n " +
						")NL \n " +
						"INNER JOIN ERP_LNSBGNGUYENLIEU_NGUYENLIEU BGNL_NL ON BGNL_NL.SANPHAM_FK = NL.VATTU_FK AND BGNL_NL.THANG = NL.THANG \n " +
						"INNER JOIN ERP_LNSBANGGIANGUYENLIEU BGNL ON BGNL.PK_SEQ = BGNL_NL.BGNGUYENLIEU_FK AND BGNL.NAM = NL.NAM \n " +
						"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = BGNL_NL.SANPHAM_FK \n " +
						"INNER JOIN ERP_LOAISANPHAM LSP ON LSP.PK_SEQ = SP.LOAISANPHAM_FK \n " +
						"WHERE LSP.PK_SEQ = 100000 \n " +
						"GROUP BY NL.THANG \n " ;
		
		System.out.println("4.MATERIALS NL : " + query);
		data = prepareData(data, query);
		return data;
	}

	public String[] PL_MaterialConsume_VLP_Budget(){
		String[] data = new String[13];
	
		String query = "SELECT NL.THANG, SUM(NL.SOLUONG*BGNL_NL.GIAMUA) AS NUM \n " +
						"FROM \n " +
						"( \n " +
						"	SELECT NS_DBSP.THANG, NS_DBSP.NAM, NS_DBSP.SANPHAM_FK, DMVT_VT.VATTU_FK, NS_DBSP.DUKIENBAN*DMVT_VT.SOLUONG /DMVT.SOLUONGCHUAN AS SOLUONG \n " +
						"	FROM ERP_LAPNGANSACH_DUBAOSANPHAM NS_DBSP \n " +
						"	INNER JOIN ERP_DANHMUCVATTU DMVT ON DMVT.SANPHAM_FK = NS_DBSP.SANPHAM_FK  \n " +
						"	INNER JOIN ERP_DANHMUCVATTU_VATTU DMVT_VT ON DMVT_VT.DANHMUCVT_FK = DMVT.PK_SEQ \n " +
						"	WHERE NS_DBSP.LAPNGANSACH_FK = " + this.Id + "  \n " +
						")NL \n " +
						"INNER JOIN ERP_LNSBGNGUYENLIEU_NGUYENLIEU BGNL_NL ON BGNL_NL.SANPHAM_FK = NL.VATTU_FK AND BGNL_NL.THANG = NL.THANG \n " +
						"INNER JOIN ERP_LNSBANGGIANGUYENLIEU BGNL ON BGNL.PK_SEQ = BGNL_NL.BGNGUYENLIEU_FK AND BGNL.NAM = NL.NAM \n " +
						"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = BGNL_NL.SANPHAM_FK \n " +
						"INNER JOIN ERP_LOAISANPHAM LSP ON LSP.PK_SEQ = SP.LOAISANPHAM_FK \n " +
						"WHERE LSP.PK_SEQ = 100008 \n " +
						"GROUP BY NL.THANG \n " ;
				
		System.out.println("4.MATERIALS VLP : " + query);
		data = prepareData(data, query);
		return data;
	}


	public String[] PL_MaterialConsume_BB_Budget(){
		String[] data = new String[13];
		
		String query = "SELECT NL.THANG, SUM(NL.SOLUONG*BGNL_NL.GIAMUA) AS NUM \n " +
						"FROM \n " +
						"( \n " +
						"	SELECT NS_DBSP.THANG, NS_DBSP.NAM, NS_DBSP.SANPHAM_FK, DMVT_VT.VATTU_FK, NS_DBSP.DUKIENBAN*DMVT_VT.SOLUONG /DMVT.SOLUONGCHUAN AS SOLUONG \n " +
						"	FROM ERP_LAPNGANSACH_DUBAOSANPHAM NS_DBSP \n " +
						"	INNER JOIN ERP_DANHMUCVATTU DMVT ON DMVT.SANPHAM_FK = NS_DBSP.SANPHAM_FK  \n " +
						"	INNER JOIN ERP_DANHMUCVATTU_VATTU DMVT_VT ON DMVT_VT.DANHMUCVT_FK = DMVT.PK_SEQ \n " +
						"	WHERE NS_DBSP.LAPNGANSACH_FK = " + this.Id + "  \n " +
						")NL \n " +
						"INNER JOIN ERP_LNSBGNGUYENLIEU_NGUYENLIEU BGNL_NL ON BGNL_NL.SANPHAM_FK = NL.VATTU_FK AND BGNL_NL.THANG = NL.THANG \n " +
						"INNER JOIN ERP_LNSBANGGIANGUYENLIEU BGNL ON BGNL.PK_SEQ = BGNL_NL.BGNGUYENLIEU_FK AND BGNL.NAM = NL.NAM \n " +
						"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = BGNL_NL.SANPHAM_FK \n " +
						"INNER JOIN ERP_LOAISANPHAM LSP ON LSP.PK_SEQ = SP.LOAISANPHAM_FK \n " +
						"WHERE LSP.PK_SEQ = 100013 \n " +
						"GROUP BY NL.THANG \n " ;
				
		System.out.println("4.MATERIALS BB : " + query);
		data = prepareData(data, query);
		return data;
	}

	public String[] PL_DirectLabour_Budget(){
		String[] data = new String[13];
			
		String query = 	
						"";				
		
		data = prepareData(data, query);
		return data;
	}

	public String[] PL_DirectLabour_LA_Budget(){
		String[] data = new String[13];
			
		String query = 	
						"";				
		
		data = prepareData(data, query);
		return data;
	}

	public String[] PL_DirectLabour_PA_Budget(){
		String[] data = new String[13];
			
		String query = 	
						"";				
		
		data = prepareData(data, query);
		return data;
	}

	public String[] PL_WorkerSalary_Budget(){
		String[] data = new String[13];
	
		String query = 	
						"";				
		
		data = prepareData(data, query);
		return data;
	}

	public String[] PL_WorkerSalary_LA_Budget(){
		String[] data = new String[12];
	
		String query = 	
						"SELECT ISNULL(CP.T1, 0) AS T1, ISNULL(CP.T2, 0) AS T2, ISNULL(CP.T3, 0) AS T3, ISNULL(CP.T4, 0) AS T4, \n " +
						"ISNULL(CP.T5, 0) AS T5, ISNULL(CP.T6, 0) AS T6, ISNULL(CP.T7, 0) AS T7, ISNULL(CP.T8, 0) AS T8,  \n " +
						"ISNULL(CP.T9, 0) AS T9, ISNULL(CP.T10, 0) AS T10, ISNULL(CP.T11, 0) AS T11, ISNULL(CP.T12, 0) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " +
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100052 \n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		System.out.println(query);
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				rs.next();
				for(int i = 0; i < 12 ; i++){
					data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
				}
				rs.close();
			}catch(java.sql.SQLException e){}
		}else{
			for(int i = 0; i < 12; i++){
				data[i] = "0";
			}
		}
		
		return data;
	}

	public String[] PL_WorkerSalary_PA_Budget(){
		String[] data = new String[12];
		
		String query = 	
						"SELECT ISNULL(CP.T1, 0) AS T1, ISNULL(CP.T2, 0) AS T2, ISNULL(CP.T3, 0) AS T3, ISNULL(CP.T4, 0) AS T4, \n " +
						"ISNULL(CP.T5, 0) AS T5, ISNULL(CP.T6, 0) AS T6, ISNULL(CP.T7, 0) AS T7, ISNULL(CP.T8, 0) AS T8,  \n " +
						"ISNULL(CP.T9, 0) AS T9, ISNULL(CP.T10, 0) AS T10, ISNULL(CP.T11, 0) AS T11, ISNULL(CP.T12, 0) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " +
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100053 \n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		System.out.println(query);
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				rs.next();
				for(int i = 0; i < 12; i++){
					data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
				}
				rs.close();
			}catch(java.sql.SQLException e){}
		}else{
			for(int i = 0; i < 12 ; i++){
				data[i] = "0";
			}
		}
		
		return data;
	}

	public String[] PL_OvertimeWorkers_Budget(){
		String[] data = new String[13];
	
		String query = 	
						"";
		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_OvertimeWorkers_LA_Budget(){
		String[] data = new String[13];
		
		String query = 	
						"SELECT ISNULL(CP.T1, 0) AS T1, ISNULL(CP.T2, 0) AS T2, ISNULL(CP.T3, 0) AS T3, ISNULL(CP.T4, 0) AS T4, \n " +
						"ISNULL(CP.T5, 0) AS T5, ISNULL(CP.T6, 0) AS T6, ISNULL(CP.T7, 0) AS T7, ISNULL(CP.T8, 0) AS T8,  \n " +
						"ISNULL(CP.T9, 0) AS T9, ISNULL(CP.T10, 0) AS T10, ISNULL(CP.T11, 0) AS T11, ISNULL(CP.T12, 0) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " +
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100050 \n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				rs.next();
				for(int i = 0; i < 12 ; i++){
					data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
				}
				rs.close();
			}catch(java.sql.SQLException e){}
		}else{
			for(int i = 0; i < 12; i++){
				data[i] = "0";
			}
		}
		
		return data;
	}

	public String[] PL_OvertimeWorkers_PA_Budget(){
		String[] data = new String[13];
		
		String query = 	
						"SELECT ISNULL(CP.T1, 0) AS T1, ISNULL(CP.T2, 0) AS T2, ISNULL(CP.T3, 0) AS T3, ISNULL(CP.T4, 0) AS T4, \n " +
						"ISNULL(CP.T5, 0) AS T5, ISNULL(CP.T6, 0) AS T6, ISNULL(CP.T7, 0) AS T7, ISNULL(CP.T8, 0) AS T8,  \n " +
						"ISNULL(CP.T9, 0) AS T9, ISNULL(CP.T10, 0) AS T10, ISNULL(CP.T11, 0) AS T11, ISNULL(CP.T12, 0) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " +
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100051 \n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				rs.next();
				for(int i = 0; i < 12; i++){
					data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
				}
				rs.close();
			}catch(java.sql.SQLException e){}
		}else{
			for(int i = 0; i < 12; i++){
				data[i] = "0";
			}
		}
		
		return data;
	}

	public String[] PL_AmortizedWorkers_Budget(){
		String[] data = new String[13];
	
		String query = 	"";				

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_AmortizedWorkers_LA_Budget(){
		String[] data = new String[13];
		
		String query = 	
						"SELECT ISNULL(CP.T1, 0) AS T1, ISNULL(CP.T2, 0) AS T2, ISNULL(CP.T3, 0) AS T3, ISNULL(CP.T4, 0) AS T4, \n " +
						"ISNULL(CP.T5, 0) AS T5, ISNULL(CP.T6, 0) AS T6, ISNULL(CP.T7, 0) AS T7, ISNULL(CP.T8, 0) AS T8,  \n " +
						"ISNULL(CP.T9, 0) AS T9, ISNULL(CP.T10, 0) AS T10, ISNULL(CP.T11, 0) AS T11, ISNULL(CP.T12, 0) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " +
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100048 \n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				rs.next();
				for(int i = 0; i < 12; i++){
					data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
				}
				rs.close();
			}catch(java.sql.SQLException e){}
		}else{
			for(int i = 0; i < 12; i++){
				data[i] = "0";
			}
		}
		
		return data;
	}

	public String[] PL_AmortizedWorkers_PA_Budget(){
		String[] data = new String[13];
		
		String query = 	
						"SELECT ISNULL(CP.T1, 0) AS T1, ISNULL(CP.T2, 0) AS T2, ISNULL(CP.T3, 0) AS T3, ISNULL(CP.T4, 0) AS T4, \n " +
						"ISNULL(CP.T5, 0) AS T5, ISNULL(CP.T6, 0) AS T6, ISNULL(CP.T7, 0) AS T7, ISNULL(CP.T8, 0) AS T8,  \n " +
						"ISNULL(CP.T9, 0) AS T9, ISNULL(CP.T10, 0) AS T10, ISNULL(CP.T11, 0) AS T11, ISNULL(CP.T12, 0) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " +
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100049 \n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				rs.next();
				for(int i = 0; i < 12; i++){
					data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
				}
				rs.close();
			}catch(java.sql.SQLException e){}
		}else{
			for(int i = 0; i < 12; i++){
				data[i] = "0";
			}
		}
		
		return data;
	}

	public String[] PL_SocialInsurance_Budget(){
		String[] data = new String[13];
	
		String query = 	"";
		return data;
	}
	
	public String[] PL_SocialInsurance_LA_Budget(){
		String[] data = new String[13];
		
		String query = 	
						"SELECT ISNULL(CP.T1, 0) AS T1, ISNULL(CP.T2, 0) AS T2, ISNULL(CP.T3, 0) AS T3, ISNULL(CP.T4, 0) AS T4, \n " +
						"ISNULL(CP.T5, 0) AS T5, ISNULL(CP.T6, 0) AS T6, ISNULL(CP.T7, 0) AS T7, ISNULL(CP.T8, 0) AS T8,  \n " +
						"ISNULL(CP.T9, 0) AS T9, ISNULL(CP.T10, 0) AS T10, ISNULL(CP.T11, 0) AS T11, ISNULL(CP.T12, 0) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " +
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100046 \n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				rs.next();
				for(int i = 0; i < 12; i++){
					data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
				}
				rs.close();
			}catch(java.sql.SQLException e){}
		}else{
			for(int i = 0; i < 12 ; i++){
				data[i] = "0";
			}
		}
		
		return data;
	}

	public String[] PL_SocialInsurance_PA_Budget(){
		String[] data = new String[13];
		
		String query = 	
						"SELECT ISNULL(CP.T1, 0) AS T1, ISNULL(CP.T2, 0) AS T2, ISNULL(CP.T3, 0) AS T3, ISNULL(CP.T4, 0) AS T4, \n " +
						"ISNULL(CP.T5, 0) AS T5, ISNULL(CP.T6, 0) AS T6, ISNULL(CP.T7, 0) AS T7, ISNULL(CP.T8, 0) AS T8,  \n " +
						"ISNULL(CP.T9, 0) AS T9, ISNULL(CP.T10, 0) AS T10, ISNULL(CP.T11, 0) AS T11, ISNULL(CP.T12, 0) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " +
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100047 \n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				rs.next();
				for(int i = 0; i < 12; i++){
					data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
				}
				rs.close();
			}catch(java.sql.SQLException e){}
		}else{
			for(int i = 0; i < 12; i++){
				data[i] = "0";
			}
		}
		
		return data;
	}

	public String[] PL_HealthInsurance_Budget(){
		String[] data = new String[13];
		
		String query = 	"";		
		return data;
	}
	

	public String[] PL_HealthInsurance_LA_Budget(){
		String[] data = new String[13];
		
		String query = 	
						"SELECT ISNULL(CP.T1, 0) AS T1, ISNULL(CP.T2, 0) AS T2, ISNULL(CP.T3, 0) AS T3, ISNULL(CP.T4, 0) AS T4, \n " +
						"ISNULL(CP.T5, 0) AS T5, ISNULL(CP.T6, 0) AS T6, ISNULL(CP.T7, 0) AS T7, ISNULL(CP.T8, 0) AS T8,  \n " +
						"ISNULL(CP.T9, 0) AS T9, ISNULL(CP.T10, 0) AS T10, ISNULL(CP.T11, 0) AS T11, ISNULL(CP.T12, 0) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " +
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100044 \n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				rs.next();
				for(int i = 0; i < 12; i++){
					data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
				}
				rs.close();
			}catch(java.sql.SQLException e){}
		}else{
			for(int i = 0; i < 12; i++){
				data[i] = "0";
			}
		}
		
		return data;
	}


	public String[] PL_HealthInsurance_PA_Budget(){
		String[] data = new String[13];
		
		String query = 	
						"SELECT ISNULL(CP.T1, 0) AS T1, ISNULL(CP.T2, 0) AS T2, ISNULL(CP.T3, 0) AS T3, ISNULL(CP.T4, 0) AS T4, \n " +
						"ISNULL(CP.T5, 0) AS T5, ISNULL(CP.T6, 0) AS T6, ISNULL(CP.T7, 0) AS T7, ISNULL(CP.T8, 0) AS T8,  \n " +
						"ISNULL(CP.T9, 0) AS T9, ISNULL(CP.T10, 0) AS T10, ISNULL(CP.T11, 0) AS T11, ISNULL(CP.T12, 0) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " +
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100045 \n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				rs.next();
				for(int i = 0; i < 12; i++){
					data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
				}
				rs.close();
			}catch(java.sql.SQLException e){}
		}else{
			for(int i = 0; i < 12; i++){
				data[i] = "0";
			}
		}
		
		return data;
	}

	public String[] PL_ProductionOverhead_Budget(){
		String[] data = new String[13];
		
		String query = 	"";

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_ProductionOverhead_LA_Budget(){
		String[] data = new String[13];
		
		String query = 	"";

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_ProductionOverhead_PA_Budget(){
		String[] data = new String[13];
		
		String query = 	"";

		data = prepareData(data, query);
		return data;
	}
	public String[] PL_WorkerAllowances_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		
		String query = "";		
		return data;
	}
	
	public String[] PL_DBCL_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	"SELECT SUM(ISNULL(CP.T1, 0)) AS T1, SUM(ISNULL(CP.T2, 0)) AS T2, SUM(ISNULL(CP.T3, 0)) AS T3, SUM(ISNULL(CP.T4, 0)) AS T4, \n " +
						"SUM(ISNULL(CP.T5, 0)) AS T5, SUM(ISNULL(CP.T6, 0)) AS T6, SUM(ISNULL(CP.T7, 0)) AS T7, SUM(ISNULL(CP.T8, 0)) AS T8,  \n " +
						"SUM(ISNULL(CP.T9, 0)) AS T9, SUM(ISNULL(CP.T10, 0)) AS T10, SUM(ISNULL(CP.T11, 0)) AS T11, SUM(ISNULL(CP.T12, 0)) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK\n " + 
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " " +
						"AND CP.CHIPHI_FK IN " +
						"(SELECT PK_SEQ FROM ERP_NHOMCHIPHI WHERE TTCHIPHI_FK IN " +
						"  ( SELECT PK_SEQ FROM ERP_TRUNGTAMCHIPHI WHERE MA = 'DBCL'))\n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
						
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
				rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		
		return data;
	}

	public String[] PL_WorkerAllowances_PA_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	
						"SELECT ISNULL(CP.T1, 0) AS T1, ISNULL(CP.T2, 0) AS T2, ISNULL(CP.T3, 0) AS T3, ISNULL(CP.T4, 0) AS T4, \n " +
						"ISNULL(CP.T5, 0) AS T5, ISNULL(CP.T6, 0) AS T6, ISNULL(CP.T7, 0) AS T7, ISNULL(CP.T8, 0) AS T8,  \n " +
						"ISNULL(CP.T9, 0) AS T9, ISNULL(CP.T10, 0) AS T10, ISNULL(CP.T11, 0) AS T11, ISNULL(CP.T12, 0) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " +
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100058 \n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
				rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		
		return data;
	}

	public String[] PL_WorkerBonus_Budget(){
		String[] data = new String[13];
		
		String query = 	
			"";
		return data;
	}
	
	public String[] PL_KTCL_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	"SELECT SUM(ISNULL(CP.T1, 0)) AS T1, SUM(ISNULL(CP.T2, 0)) AS T2, SUM(ISNULL(CP.T3, 0)) AS T3, SUM(ISNULL(CP.T4, 0)) AS T4, \n " +
						"SUM(ISNULL(CP.T5, 0)) AS T5, SUM(ISNULL(CP.T6, 0)) AS T6, SUM(ISNULL(CP.T7, 0)) AS T7, SUM(ISNULL(CP.T8, 0)) AS T8,  \n " +
						"SUM(ISNULL(CP.T9, 0)) AS T9, SUM(ISNULL(CP.T10, 0)) AS T10, SUM(ISNULL(CP.T11, 0)) AS T11, SUM(ISNULL(CP.T12, 0)) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK\n " + 
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " " +
						"AND CP.CHIPHI_FK IN " +
						"(SELECT PK_SEQ FROM ERP_NHOMCHIPHI WHERE TTCHIPHI_FK IN " +
						"  ( SELECT PK_SEQ FROM ERP_TRUNGTAMCHIPHI WHERE MA = 'KTCL'))\n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
				rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		
		return data;
		
	}

	public String[] PL_WorkerBonus_PA_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	
						"SELECT ISNULL(CP.T1, 0) AS T1, ISNULL(CP.T2, 0) AS T2, ISNULL(CP.T3, 0) AS T3, ISNULL(CP.T4, 0) AS T4, \n " +
						"ISNULL(CP.T5, 0) AS T5, ISNULL(CP.T6, 0) AS T6, ISNULL(CP.T7, 0) AS T7, ISNULL(CP.T8, 0) AS T8,  \n " +
						"ISNULL(CP.T9, 0) AS T9, ISNULL(CP.T10, 0) AS T10, ISNULL(CP.T11, 0) AS T11, ISNULL(CP.T12, 0) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " +
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100065 \n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
				rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		
		return data;
	}

	public String[] PL_DepreciationBuilding_Production_Budget(){
		String[] data = new String[13];
		
		String query = 	"SELECT THANG, SUM(NGANSACH) AS NUM \n " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH \n " +
						"WHERE NAM = '" + this.year + "' AND TTCP_FK IN (SELECT TTCHIPHI_FK FROM ERP_NHOMCHIPHI WHERE PK_SEQ = 100225) \n " +
						"GROUP BY THANG \n " +
						"ORDER BY THANG ";

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_KKH_Budget(){
		String[] data = new String[13];

		String query = 	"SELECT SUM(ISNULL(CP.T1, 0)) AS T1, SUM(ISNULL(CP.T2, 0)) AS T2, SUM(ISNULL(CP.T3, 0)) AS T3, SUM(ISNULL(CP.T4, 0)) AS T4, \n " +
						"SUM(ISNULL(CP.T5, 0)) AS T5, SUM(ISNULL(CP.T6, 0)) AS T6, SUM(ISNULL(CP.T7, 0)) AS T7, SUM(ISNULL(CP.T8, 0)) AS T8,  \n " +
						"SUM(ISNULL(CP.T9, 0)) AS T9, SUM(ISNULL(CP.T10, 0)) AS T10, SUM(ISNULL(CP.T11, 0)) AS T11, SUM(ISNULL(CP.T12, 0)) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK\n " + 
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " " +
						"AND CP.CHIPHI_FK IN " +
						"(SELECT PK_SEQ FROM ERP_NHOMCHIPHI WHERE TTCHIPHI_FK IN " +
						"  ( SELECT PK_SEQ FROM ERP_TRUNGTAMCHIPHI WHERE MA = 'KKH'))\n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_DepreciationBuilding_Production_PA_Budget(){
		String[] data = new String[13];
		
		String query = 	"SELECT THANG, SUM(NGANSACH) AS NUM \n " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH \n " +
						"WHERE NAM = '" + this.year + "' AND TTCP_FK IN (SELECT TTCHIPHI_FK FROM ERP_NHOMCHIPHI WHERE PK_SEQ = 100060) \n " +
						"GROUP BY THANG \n " +
						"ORDER BY THANG ";

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_DepreciationMachine_Budget(){
		String[] data = new String[13];
		
		String query = 	"SELECT THANG, SUM(NGANSACH) AS NUM \n " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH \n " +
						"WHERE NAM = '" + this.year + "' AND TTCP_FK IN (SELECT TTCHIPHI_FK FROM ERP_NHOMCHIPHI WHERE PK_SEQ = 100226) \n " +
						"GROUP BY THANG \n " +
						"ORDER BY THANG ";

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_KXNK_Budget(){
		String[] data = new String[13];

		String query = 	"SELECT SUM(ISNULL(CP.T1, 0)) AS T1, SUM(ISNULL(CP.T2, 0)) AS T2, SUM(ISNULL(CP.T3, 0)) AS T3, SUM(ISNULL(CP.T4, 0)) AS T4, \n " +
						"SUM(ISNULL(CP.T5, 0)) AS T5, SUM(ISNULL(CP.T6, 0)) AS T6, SUM(ISNULL(CP.T7, 0)) AS T7, SUM(ISNULL(CP.T8, 0)) AS T8,  \n " +
						"SUM(ISNULL(CP.T9, 0)) AS T9, SUM(ISNULL(CP.T10, 0)) AS T10, SUM(ISNULL(CP.T11, 0)) AS T11, SUM(ISNULL(CP.T12, 0)) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK\n " + 
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " " +
						"AND CP.CHIPHI_FK IN " +
						"(SELECT PK_SEQ FROM ERP_NHOMCHIPHI WHERE TTCHIPHI_FK IN " +
						"  ( SELECT PK_SEQ FROM ERP_TRUNGTAMCHIPHI WHERE MA = 'KXNK'))\n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_DepreciationMachine_PA_Budget(){
		String[] data = new String[13];
		
		String query = 	"SELECT THANG, SUM(NGANSACH) AS NUM \n " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH \n " +
						"WHERE NAM = '" + this.year + "' AND TTCP_FK IN (SELECT TTCHIPHI_FK FROM ERP_NHOMCHIPHI WHERE PK_SEQ = 100218) \n " +
						"GROUP BY THANG \n " +
						"ORDER BY THANG ";

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_DepreciationMotorVehicle_Production_Budget(){
		String[] data = new String[13];
		
		String query = 	"SELECT THANG, SUM(NGANSACH) AS NUM \n " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH \n " +
						"WHERE NAM = '" + this.year + "' AND TTCP_FK IN (SELECT TTCHIPHI_FK FROM ERP_NHOMCHIPHI WHERE PK_SEQ = 100227) \n " +
						"GROUP BY THANG \n " +
						"ORDER BY THANG ";

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_QLSX_Budget(){
		String[] data = new String[13];

		String query = 	"SELECT SUM(ISNULL(CP.T1, 0)) AS T1, SUM(ISNULL(CP.T2, 0)) AS T2, SUM(ISNULL(CP.T3, 0)) AS T3, SUM(ISNULL(CP.T4, 0)) AS T4, \n " +
						"SUM(ISNULL(CP.T5, 0)) AS T5, SUM(ISNULL(CP.T6, 0)) AS T6, SUM(ISNULL(CP.T7, 0)) AS T7, SUM(ISNULL(CP.T8, 0)) AS T8,  \n " +
						"SUM(ISNULL(CP.T9, 0)) AS T9, SUM(ISNULL(CP.T10, 0)) AS T10, SUM(ISNULL(CP.T11, 0)) AS T11, SUM(ISNULL(CP.T12, 0)) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK\n " + 
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " " +
						"AND CP.CHIPHI_FK IN " +
						"(SELECT PK_SEQ FROM ERP_NHOMCHIPHI WHERE TTCHIPHI_FK IN " +
						"  ( SELECT PK_SEQ FROM ERP_TRUNGTAMCHIPHI WHERE MA = 'QLSX'))\n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		
		data = prepareData(data, query);
		return data;
	}

	public String[] PL_DepreciationMotorVehicle_Production_PA_Budget(){
		String[] data = new String[13];
		
		String query = 	"SELECT THANG, SUM(NGANSACH) AS NUM \n " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH \n " +
						"WHERE NAM = '" + this.year + "' AND TTCP_FK IN (SELECT TTCHIPHI_FK FROM ERP_NHOMCHIPHI WHERE PK_SEQ = 100219) \n " +
						"GROUP BY THANG \n " +
						"ORDER BY THANG ";

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_RentalCharge_Machinery_Budget(){
		String[] data = new String[13];

		String query = 	"";
		return data;
	}
	
	public String[] PL_TVTN_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	"SELECT SUM(ISNULL(CP.T1, 0)) AS T1, SUM(ISNULL(CP.T2, 0)) AS T2, SUM(ISNULL(CP.T3, 0)) AS T3, SUM(ISNULL(CP.T4, 0)) AS T4, \n " +
						"SUM(ISNULL(CP.T5, 0)) AS T5, SUM(ISNULL(CP.T6, 0)) AS T6, SUM(ISNULL(CP.T7, 0)) AS T7, SUM(ISNULL(CP.T8, 0)) AS T8,  \n " +
						"SUM(ISNULL(CP.T9, 0)) AS T9, SUM(ISNULL(CP.T10, 0)) AS T10, SUM(ISNULL(CP.T11, 0)) AS T11, SUM(ISNULL(CP.T12, 0)) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK\n " + 
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " " +
						"AND CP.CHIPHI_FK IN " +
						"(SELECT PK_SEQ FROM ERP_NHOMCHIPHI WHERE TTCHIPHI_FK IN " +
						"  ( SELECT PK_SEQ FROM ERP_TRUNGTAMCHIPHI WHERE MA = 'TVTN'))\n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
				rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		
		return data;
	}

	public String[] PL_RentalCharge_Machinery_PA_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	
						"SELECT ISNULL(CP.T1, 0) AS T1, ISNULL(CP.T2, 0) AS T2, ISNULL(CP.T3, 0) AS T3, ISNULL(CP.T4, 0) AS T4, \n " +
						"ISNULL(CP.T5, 0) AS T5, ISNULL(CP.T6, 0) AS T6, ISNULL(CP.T7, 0) AS T7, ISNULL(CP.T8, 0) AS T8,  \n " +
						"ISNULL(CP.T9, 0) AS T9, ISNULL(CP.T10, 0) AS T10, ISNULL(CP.T11, 0) AS T11, ISNULL(CP.T12, 0) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " +
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100069 \n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
				rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		
		return data;
	}

	public String[] PL_ElectricityWater_Budget(){
		String[] data = new String[13];

		String query = 	"";
		return data;
	}
	
	public String[] PL_TMNM_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	"SELECT SUM(ISNULL(CP.T1, 0)) AS T1, SUM(ISNULL(CP.T2, 0)) AS T2, SUM(ISNULL(CP.T3, 0)) AS T3, SUM(ISNULL(CP.T4, 0)) AS T4, \n " +
						"SUM(ISNULL(CP.T5, 0)) AS T5, SUM(ISNULL(CP.T6, 0)) AS T6, SUM(ISNULL(CP.T7, 0)) AS T7, SUM(ISNULL(CP.T8, 0)) AS T8,  \n " +
						"SUM(ISNULL(CP.T9, 0)) AS T9, SUM(ISNULL(CP.T10, 0)) AS T10, SUM(ISNULL(CP.T11, 0)) AS T11, SUM(ISNULL(CP.T12, 0)) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK\n " + 
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " " +
						"AND CP.CHIPHI_FK IN " +
						"(SELECT PK_SEQ FROM ERP_NHOMCHIPHI WHERE TTCHIPHI_FK IN " +
						"  ( SELECT PK_SEQ FROM ERP_TRUNGTAMCHIPHI WHERE MA = 'TMNM'))\n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;

		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
				rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		
		return data;
	}
	
	public String[] PL_ElectricityWater_PA_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	
						"SELECT ISNULL(CP.T1, 0) AS T1, ISNULL(CP.T2, 0) AS T2, ISNULL(CP.T3, 0) AS T3, ISNULL(CP.T4, 0) AS T4, \n " +
						"ISNULL(CP.T5, 0) AS T5, ISNULL(CP.T6, 0) AS T6, ISNULL(CP.T7, 0) AS T7, ISNULL(CP.T8, 0) AS T8,  \n " +
						"ISNULL(CP.T9, 0) AS T9, ISNULL(CP.T10, 0) AS T10, ISNULL(CP.T11, 0) AS T11, ISNULL(CP.T12, 0) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " +
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100071 \n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
				rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		
		return data;
	}
	
	public String[] PL_FactoryExpenses_Budget(){
		String[] data = new String[13];

		String query = 	"";
		return data;
	}

	public String[] PL_TY_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	"SELECT SUM(ISNULL(CP.T1, 0)) AS T1, SUM(ISNULL(CP.T2, 0)) AS T2, SUM(ISNULL(CP.T3, 0)) AS T3, SUM(ISNULL(CP.T4, 0)) AS T4, \n " +
						"SUM(ISNULL(CP.T5, 0)) AS T5, SUM(ISNULL(CP.T6, 0)) AS T6, SUM(ISNULL(CP.T7, 0)) AS T7, SUM(ISNULL(CP.T8, 0)) AS T8,  \n " +
						"SUM(ISNULL(CP.T9, 0)) AS T9, SUM(ISNULL(CP.T10, 0)) AS T10, SUM(ISNULL(CP.T11, 0)) AS T11, SUM(ISNULL(CP.T12, 0)) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK\n " + 
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " " +
						"AND CP.CHIPHI_FK IN " +
						"(SELECT PK_SEQ FROM ERP_NHOMCHIPHI WHERE TTCHIPHI_FK IN " +
						"  ( SELECT PK_SEQ FROM ERP_TRUNGTAMCHIPHI WHERE MA = 'TY'))\n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;

		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
				rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		
		return data;
	}

	public String[] PL_FactoryExpenses_PA_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	
						"SELECT ISNULL(CP.T1, 0) AS T1, ISNULL(CP.T2, 0) AS T2, ISNULL(CP.T3, 0) AS T3, ISNULL(CP.T4, 0) AS T4, \n " +
						"ISNULL(CP.T5, 0) AS T5, ISNULL(CP.T6, 0) AS T6, ISNULL(CP.T7, 0) AS T7, ISNULL(CP.T8, 0) AS T8,  \n " +
						"ISNULL(CP.T9, 0) AS T9, ISNULL(CP.T10, 0) AS T10, ISNULL(CP.T11, 0) AS T11, ISNULL(CP.T12, 0) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " +
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100073 \n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
				rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		
		return data;
	}

	public String[] PL_Printing_Stationery_Budget(){
		String[] data = new String[13];	
		String query = 	"";
		return data;
	}
	
	public String[] PL_DG_Budget(){
		String[] data = new String[12];	
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}
		

		String query = 	"SELECT SUM(ISNULL(CP.T1, 0)) AS T1, SUM(ISNULL(CP.T2, 0)) AS T2, SUM(ISNULL(CP.T3, 0)) AS T3, SUM(ISNULL(CP.T4, 0)) AS T4, \n " +
						"SUM(ISNULL(CP.T5, 0)) AS T5, SUM(ISNULL(CP.T6, 0)) AS T6, SUM(ISNULL(CP.T7, 0)) AS T7, SUM(ISNULL(CP.T8, 0)) AS T8,  \n " +
						"SUM(ISNULL(CP.T9, 0)) AS T9, SUM(ISNULL(CP.T10, 0)) AS T10, SUM(ISNULL(CP.T11, 0)) AS T11, SUM(ISNULL(CP.T12, 0)) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK\n " + 
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " " +
						"AND CP.CHIPHI_FK IN " +
						"(SELECT PK_SEQ FROM ERP_NHOMCHIPHI WHERE TTCHIPHI_FK IN " +
						"  ( SELECT PK_SEQ FROM ERP_TRUNGTAMCHIPHI WHERE MA = 'DG'))\n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;		System.out.println(query);

		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
					rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		
		return data;
	}

	public String[] PL_Printing_Stationery_PA_Budget(){
		String[] data = new String[12];	
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}
		
		String query = 	
						"SELECT ISNULL(CP.T1, 0) AS T1, ISNULL(CP.T2, 0) AS T2, ISNULL(CP.T3, 0) AS T3, ISNULL(CP.T4, 0) AS T4, \n " +
						"ISNULL(CP.T5, 0) AS T5, ISNULL(CP.T6, 0) AS T6, ISNULL(CP.T7, 0) AS T7, ISNULL(CP.T8, 0) AS T8,  \n " +
						"ISNULL(CP.T9, 0) AS T9, ISNULL(CP.T10, 0) AS T10, ISNULL(CP.T11, 0) AS T11, ISNULL(CP.T12, 0) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " +
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100075 \n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		System.out.println(query);

		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
					rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		
		return data;
	}

	public String[] PL_Workers_Meals_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	
						"SELECT ISNULL(CP.T1, 0) AS T1, ISNULL(CP.T2, 0) AS T2, ISNULL(CP.T3, 0) AS T3, ISNULL(CP.T4, 0) AS T4, \n " +
						"ISNULL(CP.T5, 0) AS T5, ISNULL(CP.T6, 0) AS T6, ISNULL(CP.T7, 0) AS T7, ISNULL(CP.T8, 0) AS T8,  \n " +
						"ISNULL(CP.T9, 0) AS T9, ISNULL(CP.T10, 0) AS T10, ISNULL(CP.T11, 0) AS T11, ISNULL(CP.T12, 0) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " +
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100231 \n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		System.out.println(query);
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
					rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		return data;
	}
	
	public String[] PL_SXC_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	"SELECT SUM(ISNULL(CP.T1, 0)) AS T1, SUM(ISNULL(CP.T2, 0)) AS T2, SUM(ISNULL(CP.T3, 0)) AS T3, SUM(ISNULL(CP.T4, 0)) AS T4, \n " +
						"SUM(ISNULL(CP.T5, 0)) AS T5, SUM(ISNULL(CP.T6, 0)) AS T6, SUM(ISNULL(CP.T7, 0)) AS T7, SUM(ISNULL(CP.T8, 0)) AS T8,  \n " +
						"SUM(ISNULL(CP.T9, 0)) AS T9, SUM(ISNULL(CP.T10, 0)) AS T10, SUM(ISNULL(CP.T11, 0)) AS T11, SUM(ISNULL(CP.T12, 0)) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK\n " + 
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " " +
						"AND CP.CHIPHI_FK IN " +
						"(SELECT PK_SEQ FROM ERP_NHOMCHIPHI WHERE TTCHIPHI_FK IN " +
						"  ( SELECT PK_SEQ FROM ERP_TRUNGTAMCHIPHI WHERE MA = 'SXC'))\n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;		System.out.println(query);

		System.out.println(query);
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
					rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		return data;
	}

	
	public String[] PL_Workers_Meals_PA_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	
						"SELECT ISNULL(CP.T1, 0) AS T1, ISNULL(CP.T2, 0) AS T2, ISNULL(CP.T3, 0) AS T3, ISNULL(CP.T4, 0) AS T4, \n " +
						"ISNULL(CP.T5, 0) AS T5, ISNULL(CP.T6, 0) AS T6, ISNULL(CP.T7, 0) AS T7, ISNULL(CP.T8, 0) AS T8,  \n " +
						"ISNULL(CP.T9, 0) AS T9, ISNULL(CP.T10, 0) AS T10, ISNULL(CP.T11, 0) AS T11, ISNULL(CP.T12, 0) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " +
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100077 \n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		System.out.println(query);
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
					rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		return data;
	}

	public String[] PL_Upkeep_Motorvehicle_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = "";
		return data;
	}
	
	public String[] PL_Upkeep_Motorvehicle_LA_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	
						"SELECT ISNULL(CP.T1, 0) AS T1, ISNULL(CP.T2, 0) AS T2, ISNULL(CP.T3, 0) AS T3, ISNULL(CP.T4, 0) AS T4, \n " +
						"ISNULL(CP.T5, 0) AS T5, ISNULL(CP.T6, 0) AS T6, ISNULL(CP.T7, 0) AS T7, ISNULL(CP.T8, 0) AS T8,  \n " +
						"ISNULL(CP.T9, 0) AS T9, ISNULL(CP.T10, 0) AS T10, ISNULL(CP.T11, 0) AS T11, ISNULL(CP.T12, 0) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " +
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100078 \n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
				rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		
		return data;
	}

	public String[] PL_Upkeep_Motorvehicle_PA_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	
						"SELECT ISNULL(CP.T1, 0) AS T1, ISNULL(CP.T2, 0) AS T2, ISNULL(CP.T3, 0) AS T3, ISNULL(CP.T4, 0) AS T4, \n " +
						"ISNULL(CP.T5, 0) AS T5, ISNULL(CP.T6, 0) AS T6, ISNULL(CP.T7, 0) AS T7, ISNULL(CP.T8, 0) AS T8,  \n " +
						"ISNULL(CP.T9, 0) AS T9, ISNULL(CP.T10, 0) AS T10, ISNULL(CP.T11, 0) AS T11, ISNULL(CP.T12, 0) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " +
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100079 \n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
				rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		
		return data;
	}

	public String[] PL_Upkeep_Machinery_Budget(){
		String[] data = new String[13];

		String query = 	"";
		return data;
	}
	

	public String[] PL_Upkeep_Machinery_LA_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	
						"SELECT ISNULL(CP.T1, 0) AS T1, ISNULL(CP.T2, 0) AS T2, ISNULL(CP.T3, 0) AS T3, ISNULL(CP.T4, 0) AS T4, \n " +
						"ISNULL(CP.T5, 0) AS T5, ISNULL(CP.T6, 0) AS T6, ISNULL(CP.T7, 0) AS T7, ISNULL(CP.T8, 0) AS T8,  \n " +
						"ISNULL(CP.T9, 0) AS T9, ISNULL(CP.T10, 0) AS T10, ISNULL(CP.T11, 0) AS T11, ISNULL(CP.T12, 0) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " +
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100080 \n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
				rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		
		return data;
	}


	public String[] PL_Upkeep_Machinery_PA_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	
						"SELECT ISNULL(CP.T1, 0) AS T1, ISNULL(CP.T2, 0) AS T2, ISNULL(CP.T3, 0) AS T3, ISNULL(CP.T4, 0) AS T4, \n " +
						"ISNULL(CP.T5, 0) AS T5, ISNULL(CP.T6, 0) AS T6, ISNULL(CP.T7, 0) AS T7, ISNULL(CP.T8, 0) AS T8,  \n " +
						"ISNULL(CP.T9, 0) AS T9, ISNULL(CP.T10, 0) AS T10, ISNULL(CP.T11, 0) AS T11, ISNULL(CP.T12, 0) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " +
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100081 \n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
				rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		
		return data;
	}

	public String[] PL_TrainingCost_Budget(){
		String[] data = new String[13];	
		String query = 	"";
		return data;
	}
	
	public String[] PL_TrainingCost_LA_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	
						"SELECT ISNULL(CP.T1, 0) AS T1, ISNULL(CP.T2, 0) AS T2, ISNULL(CP.T3, 0) AS T3, ISNULL(CP.T4, 0) AS T4, \n " +
						"ISNULL(CP.T5, 0) AS T5, ISNULL(CP.T6, 0) AS T6, ISNULL(CP.T7, 0) AS T7, ISNULL(CP.T8, 0) AS T8,  \n " +
						"ISNULL(CP.T9, 0) AS T9, ISNULL(CP.T10, 0) AS T10, ISNULL(CP.T11, 0) AS T11, ISNULL(CP.T12, 0) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " +
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100082 \n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
				rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		
		return data;
	}

	public String[] PL_TrainingCost_PA_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	
						"SELECT ISNULL(CP.T1, 0) AS T1, ISNULL(CP.T2, 0) AS T2, ISNULL(CP.T3, 0) AS T3, ISNULL(CP.T4, 0) AS T4, \n " +
						"ISNULL(CP.T5, 0) AS T5, ISNULL(CP.T6, 0) AS T6, ISNULL(CP.T7, 0) AS T7, ISNULL(CP.T8, 0) AS T8,  \n " +
						"ISNULL(CP.T9, 0) AS T9, ISNULL(CP.T10, 0) AS T10, ISNULL(CP.T11, 0) AS T11, ISNULL(CP.T12, 0) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " +
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100083 \n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
				rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		
		return data;
	}

	public String[] PL_Welfare_Budget(){
		String[] data = new String[13];
		String query = 	"";
		return data;
	}
	
	public String[] PL_Welfare_LA_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	
						"SELECT ISNULL(CP.T1, 0) AS T1, ISNULL(CP.T2, 0) AS T2, ISNULL(CP.T3, 0) AS T3, ISNULL(CP.T4, 0) AS T4, \n " +
						"ISNULL(CP.T5, 0) AS T5, ISNULL(CP.T6, 0) AS T6, ISNULL(CP.T7, 0) AS T7, ISNULL(CP.T8, 0) AS T8,  \n " +
						"ISNULL(CP.T9, 0) AS T9, ISNULL(CP.T10, 0) AS T10, ISNULL(CP.T11, 0) AS T11, ISNULL(CP.T12, 0) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " +
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100084 \n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
				rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		
		return data;
	}

	public String[] PL_Welfare_PA_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	
						"SELECT ISNULL(CP.T1, 0) AS T1, ISNULL(CP.T2, 0) AS T2, ISNULL(CP.T3, 0) AS T3, ISNULL(CP.T4, 0) AS T4, \n " +
						"ISNULL(CP.T5, 0) AS T5, ISNULL(CP.T6, 0) AS T6, ISNULL(CP.T7, 0) AS T7, ISNULL(CP.T8, 0) AS T8,  \n " +
						"ISNULL(CP.T9, 0) AS T9, ISNULL(CP.T10, 0) AS T10, ISNULL(CP.T11, 0) AS T11, ISNULL(CP.T12, 0) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " +
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100085 \n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
				rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		
		return data;
	}

	public String[] PL_InsuranceA_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}
		
		String query = "";
		return data;
	}
		
	public String[] PL_InsuranceA_LA_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	
						"SELECT ISNULL(CP.T1, 0) AS T1, ISNULL(CP.T2, 0) AS T2, ISNULL(CP.T3, 0) AS T3, ISNULL(CP.T4, 0) AS T4, \n " +
						"ISNULL(CP.T5, 0) AS T5, ISNULL(CP.T6, 0) AS T6, ISNULL(CP.T7, 0) AS T7, ISNULL(CP.T8, 0) AS T8,  \n " +
						"ISNULL(CP.T9, 0) AS T9, ISNULL(CP.T10, 0) AS T10, ISNULL(CP.T11, 0) AS T11, ISNULL(CP.T12, 0) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " +
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100086 \n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
				rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		
		return data;
	}

	public String[] PL_InsuranceA_PA_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	
						"SELECT ISNULL(CP.T1, 0) AS T1, ISNULL(CP.T2, 0) AS T2, ISNULL(CP.T3, 0) AS T3, ISNULL(CP.T4, 0) AS T4, \n " +
						"ISNULL(CP.T5, 0) AS T5, ISNULL(CP.T6, 0) AS T6, ISNULL(CP.T7, 0) AS T7, ISNULL(CP.T8, 0) AS T8,  \n " +
						"ISNULL(CP.T9, 0) AS T9, ISNULL(CP.T10, 0) AS T10, ISNULL(CP.T11, 0) AS T11, ISNULL(CP.T12, 0) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " +
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100087 \n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
				rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		
		return data;
	}

	public String[] PL_UpkeepFactory_Budget(){
		String[] data = new String[13];
		String query = 	"";		return data;
	}
	

	public String[] PL_UpkeepFactory_LA_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	
						"SELECT ISNULL(CP.T1, 0) AS T1, ISNULL(CP.T2, 0) AS T2, ISNULL(CP.T3, 0) AS T3, ISNULL(CP.T4, 0) AS T4, \n " +
						"ISNULL(CP.T5, 0) AS T5, ISNULL(CP.T6, 0) AS T6, ISNULL(CP.T7, 0) AS T7, ISNULL(CP.T8, 0) AS T8,  \n " +
						"ISNULL(CP.T9, 0) AS T9, ISNULL(CP.T10, 0) AS T10, ISNULL(CP.T11, 0) AS T11, ISNULL(CP.T12, 0) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " +
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100088 \n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
				rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		
		return data;
	}


	public String[] PL_UpkeepFactory_PA_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	
						"SELECT ISNULL(CP.T1, 0) AS T1, ISNULL(CP.T2, 0) AS T2, ISNULL(CP.T3, 0) AS T3, ISNULL(CP.T4, 0) AS T4, \n " +
						"ISNULL(CP.T5, 0) AS T5, ISNULL(CP.T6, 0) AS T6, ISNULL(CP.T7, 0) AS T7, ISNULL(CP.T8, 0) AS T8,  \n " +
						"ISNULL(CP.T9, 0) AS T9, ISNULL(CP.T10, 0) AS T10, ISNULL(CP.T11, 0) AS T11, ISNULL(CP.T12, 0) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " +
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100089 \n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
				rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		
		return data;
	}

	public String[] PL_Workers_Uniform_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = "";
		return data;
		
	}
	

	public String[] PL_Workers_Uniform_LA_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	
						"SELECT ISNULL(CP.T1, 0) AS T1, ISNULL(CP.T2, 0) AS T2, ISNULL(CP.T3, 0) AS T3, ISNULL(CP.T4, 0) AS T4, \n " +
						"ISNULL(CP.T5, 0) AS T5, ISNULL(CP.T6, 0) AS T6, ISNULL(CP.T7, 0) AS T7, ISNULL(CP.T8, 0) AS T8,  \n " +
						"ISNULL(CP.T9, 0) AS T9, ISNULL(CP.T10, 0) AS T10, ISNULL(CP.T11, 0) AS T11, ISNULL(CP.T12, 0) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " +
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100090 \n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
				rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		
		return data;
		
	}


	public String[] PL_Workers_Uniform_PA_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	
						"SELECT ISNULL(CP.T1, 0) AS T1, ISNULL(CP.T2, 0) AS T2, ISNULL(CP.T3, 0) AS T3, ISNULL(CP.T4, 0) AS T4, \n " +
						"ISNULL(CP.T5, 0) AS T5, ISNULL(CP.T6, 0) AS T6, ISNULL(CP.T7, 0) AS T7, ISNULL(CP.T8, 0) AS T8,  \n " +
						"ISNULL(CP.T9, 0) AS T9, ISNULL(CP.T10, 0) AS T10, ISNULL(CP.T11, 0) AS T11, ISNULL(CP.T12, 0) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " +
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100091 \n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
				rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		
		return data;
	}

	public String[] PL_LandRental_Budget(){
		String[] data = new String[13];

		String query = 	"";
		return data;
	}
	

	public String[] PL_LandRental_LA_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	
						"SELECT ISNULL(CP.T1, 0) AS T1, ISNULL(CP.T2, 0) AS T2, ISNULL(CP.T3, 0) AS T3, ISNULL(CP.T4, 0) AS T4, \n " +
						"ISNULL(CP.T5, 0) AS T5, ISNULL(CP.T6, 0) AS T6, ISNULL(CP.T7, 0) AS T7, ISNULL(CP.T8, 0) AS T8,  \n " +
						"ISNULL(CP.T9, 0) AS T9, ISNULL(CP.T10, 0) AS T10, ISNULL(CP.T11, 0) AS T11, ISNULL(CP.T12, 0) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " +
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100092 \n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
				rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		
		return data;
	}


	public String[] PL_LandRental_PA_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	
						"SELECT ISNULL(CP.T1, 0) AS T1, ISNULL(CP.T2, 0) AS T2, ISNULL(CP.T3, 0) AS T3, ISNULL(CP.T4, 0) AS T4, \n " +
						"ISNULL(CP.T5, 0) AS T5, ISNULL(CP.T6, 0) AS T6, ISNULL(CP.T7, 0) AS T7, ISNULL(CP.T8, 0) AS T8,  \n " +
						"ISNULL(CP.T9, 0) AS T9, ISNULL(CP.T10, 0) AS T10, ISNULL(CP.T11, 0) AS T11, ISNULL(CP.T12, 0) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " +
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100220 \n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
				rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		
		return data;
	}

	public String[] PL_Machinery_Maintenance_Budget(){
		String[] data = new String[13];

		String query = 	"";
		return data;
	}

	public String[] PL_Machinery_Maintenance_LA_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	
						"SELECT ISNULL(CP.T1, 0) AS T1, ISNULL(CP.T2, 0) AS T2, ISNULL(CP.T3, 0) AS T3, ISNULL(CP.T4, 0) AS T4, \n " +
						"ISNULL(CP.T5, 0) AS T5, ISNULL(CP.T6, 0) AS T6, ISNULL(CP.T7, 0) AS T7, ISNULL(CP.T8, 0) AS T8,  \n " +
						"ISNULL(CP.T9, 0) AS T9, ISNULL(CP.T10, 0) AS T10, ISNULL(CP.T11, 0) AS T11, ISNULL(CP.T12, 0) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " +
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100273 \n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
				rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		
		return data;
	}
	
	public String[] PL_Machinery_Maintenance_PA_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	
						"SELECT ISNULL(CP.T1, 0) AS T1, ISNULL(CP.T2, 0) AS T2, ISNULL(CP.T3, 0) AS T3, ISNULL(CP.T4, 0) AS T4, \n " +
						"ISNULL(CP.T5, 0) AS T5, ISNULL(CP.T6, 0) AS T6, ISNULL(CP.T7, 0) AS T7, ISNULL(CP.T8, 0) AS T8,  \n " +
						"ISNULL(CP.T9, 0) AS T9, ISNULL(CP.T10, 0) AS T10, ISNULL(CP.T11, 0) AS T11, ISNULL(CP.T12, 0) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " +
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100274 \n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
				rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		
		return data;
	}


	public String[] PL_Machinery_Overhaul_Budget(){
		String[] data = new String[13];

		String query = 	
					"";


		data = prepareData(data, query);
		return data;
	}


		public String[] PL_Machinery_Overhaul_LA_Budget(){
			String[] data = new String[12];
			for(int i = 0; i < 12; i++){
				data[i] = "0";
			}

			String query = 	
							"SELECT ISNULL(CP.T1, 0) AS T1, ISNULL(CP.T2, 0) AS T2, ISNULL(CP.T3, 0) AS T3, ISNULL(CP.T4, 0) AS T4, \n " +
							"ISNULL(CP.T5, 0) AS T5, ISNULL(CP.T6, 0) AS T6, ISNULL(CP.T7, 0) AS T7, ISNULL(CP.T8, 0) AS T8,  \n " +
							"ISNULL(CP.T9, 0) AS T9, ISNULL(CP.T10, 0) AS T10, ISNULL(CP.T11, 0) AS T11, ISNULL(CP.T12, 0) AS T12 \n " +
							"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
							"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " +
							"WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100280 \n " +
							"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
			ResultSet rs = this.db.get(query);
			if(rs != null){
				try{
					if(rs.next()){
						for(int i = 0; i < 12; i++){
							data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
						}
					rs.close();
					}
				}catch(java.sql.SQLException e){}
			}
			
			return data;
		}


		public String[] PL_Machinery_Overhaul_PA_Budget(){
			String[] data = new String[12];
			for(int i = 0; i < 12; i++){
				data[i] = "0";
			}

			String query = 	
							"SELECT ISNULL(CP.T1, 0) AS T1, ISNULL(CP.T2, 0) AS T2, ISNULL(CP.T3, 0) AS T3, ISNULL(CP.T4, 0) AS T4, \n " +
							"ISNULL(CP.T5, 0) AS T5, ISNULL(CP.T6, 0) AS T6, ISNULL(CP.T7, 0) AS T7, ISNULL(CP.T8, 0) AS T8,  \n " +
							"ISNULL(CP.T9, 0) AS T9, ISNULL(CP.T10, 0) AS T10, ISNULL(CP.T11, 0) AS T11, ISNULL(CP.T12, 0) AS T12 \n " +
							"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
							"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " +
							"WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100281 \n " +
							"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
			ResultSet rs = this.db.get(query);
			if(rs != null){
				try{
					if(rs.next()){
						for(int i = 0; i < 12; i++){
							data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
						}
					rs.close();
					}
				}catch(java.sql.SQLException e){}
			}
			
			return data;
	}

	public String[] PL_Production_Equipments_Budget(){
		String[] data = new String[13];
		String query = 	
					"";


		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_Production_Equipments_LA_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	
						"SELECT ISNULL(CP.T1, 0) AS T1, ISNULL(CP.T2, 0) AS T2, ISNULL(CP.T3, 0) AS T3, ISNULL(CP.T4, 0) AS T4, \n " +
						"ISNULL(CP.T5, 0) AS T5, ISNULL(CP.T6, 0) AS T6, ISNULL(CP.T7, 0) AS T7, ISNULL(CP.T8, 0) AS T8,  \n " +
						"ISNULL(CP.T9, 0) AS T9, ISNULL(CP.T10, 0) AS T10, ISNULL(CP.T11, 0) AS T11, ISNULL(CP.T12, 0) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " +
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100276 \n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
				rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		
		return data;
	}


	public String[] PL_Production_Equipments_PA_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	
						"SELECT ISNULL(CP.T1, 0) AS T1, ISNULL(CP.T2, 0) AS T2, ISNULL(CP.T3, 0) AS T3, ISNULL(CP.T4, 0) AS T4, \n " +
						"ISNULL(CP.T5, 0) AS T5, ISNULL(CP.T6, 0) AS T6, ISNULL(CP.T7, 0) AS T7, ISNULL(CP.T8, 0) AS T8,  \n " +
						"ISNULL(CP.T9, 0) AS T9, ISNULL(CP.T10, 0) AS T10, ISNULL(CP.T11, 0) AS T11, ISNULL(CP.T12, 0) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " +
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100278 \n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
				rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		
		return data;
	}

			
	public String[] PL_Oil_Machine_Budget(){
		String[] data = new String[13];

		String query = 	"";


		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_Oil_Machine_LA_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	
						"SELECT ISNULL(CP.T1, 0) AS T1, ISNULL(CP.T2, 0) AS T2, ISNULL(CP.T3, 0) AS T3, ISNULL(CP.T4, 0) AS T4, \n " +
						"ISNULL(CP.T5, 0) AS T5, ISNULL(CP.T6, 0) AS T6, ISNULL(CP.T7, 0) AS T7, ISNULL(CP.T8, 0) AS T8,  \n " +
						"ISNULL(CP.T9, 0) AS T9, ISNULL(CP.T10, 0) AS T10, ISNULL(CP.T11, 0) AS T11, ISNULL(CP.T12, 0) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " +
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100096 \n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
				rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		
		return data;
	}

	
	public String[] PL_Oil_Machine_PA_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	
						"SELECT ISNULL(CP.T1, 0) AS T1, ISNULL(CP.T2, 0) AS T2, ISNULL(CP.T3, 0) AS T3, ISNULL(CP.T4, 0) AS T4, \n " +
						"ISNULL(CP.T5, 0) AS T5, ISNULL(CP.T6, 0) AS T6, ISNULL(CP.T7, 0) AS T7, ISNULL(CP.T8, 0) AS T8,  \n " +
						"ISNULL(CP.T9, 0) AS T9, ISNULL(CP.T10, 0) AS T10, ISNULL(CP.T11, 0) AS T11, ISNULL(CP.T12, 0) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " +
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100097 \n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
				rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		
		return data;
	}

	public String[] PL_IndirectLabourSalary_Budget(){
		String[] data = new String[13];

		String query = 	"";
		return data;
	}
	

	public String[] PL_IndirectLabourSalary_LA_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	
						"SELECT ISNULL(CP.T1, 0) AS T1, ISNULL(CP.T2, 0) AS T2, ISNULL(CP.T3, 0) AS T3, ISNULL(CP.T4, 0) AS T4, \n " +
						"ISNULL(CP.T5, 0) AS T5, ISNULL(CP.T6, 0) AS T6, ISNULL(CP.T7, 0) AS T7, ISNULL(CP.T8, 0) AS T8,  \n " +
						"ISNULL(CP.T9, 0) AS T9, ISNULL(CP.T10, 0) AS T10, ISNULL(CP.T11, 0) AS T11, ISNULL(CP.T12, 0) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " +
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100098 \n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
				rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		
		return data;
	}
	

	public String[] PL_IndirectLabourSalary_PA_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	
						"SELECT ISNULL(CP.T1, 0) AS T1, ISNULL(CP.T2, 0) AS T2, ISNULL(CP.T3, 0) AS T3, ISNULL(CP.T4, 0) AS T4, \n " +
						"ISNULL(CP.T5, 0) AS T5, ISNULL(CP.T6, 0) AS T6, ISNULL(CP.T7, 0) AS T7, ISNULL(CP.T8, 0) AS T8,  \n " +
						"ISNULL(CP.T9, 0) AS T9, ISNULL(CP.T10, 0) AS T10, ISNULL(CP.T11, 0) AS T11, ISNULL(CP.T12, 0) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " +
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100099 \n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
				rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		
		return data;
	}
	

	
	public String[] PL_Indirect_13th_LabourSalary_Budget(){
		String[] data = new String[13];

		String query = 	"";
		return data;
	}
	
	
	public String[] PL_Indirect_13th_LabourSalary_LA_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	
						"SELECT ISNULL(CP.T1, 0) AS T1, ISNULL(CP.T2, 0) AS T2, ISNULL(CP.T3, 0) AS T3, ISNULL(CP.T4, 0) AS T4, \n " +
						"ISNULL(CP.T5, 0) AS T5, ISNULL(CP.T6, 0) AS T6, ISNULL(CP.T7, 0) AS T7, ISNULL(CP.T8, 0) AS T8,  \n " +
						"ISNULL(CP.T9, 0) AS T9, ISNULL(CP.T10, 0) AS T10, ISNULL(CP.T11, 0) AS T11, ISNULL(CP.T12, 0) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " +
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100100 \n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
				rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		
		return data;
	}
	
	
	public String[] PL_Indirect_13th_LabourSalary_PA_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	
						"SELECT ISNULL(CP.T1, 0) AS T1, ISNULL(CP.T2, 0) AS T2, ISNULL(CP.T3, 0) AS T3, ISNULL(CP.T4, 0) AS T4, \n " +
						"ISNULL(CP.T5, 0) AS T5, ISNULL(CP.T6, 0) AS T6, ISNULL(CP.T7, 0) AS T7, ISNULL(CP.T8, 0) AS T8,  \n " +
						"ISNULL(CP.T9, 0) AS T9, ISNULL(CP.T10, 0) AS T10, ISNULL(CP.T11, 0) AS T11, ISNULL(CP.T12, 0) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " +
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100101 \n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
				rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		
		return data;
	}
	
	public String[] PL_Indirect_LabourBonus_Budget(){
		String[] data = new String[13];

		String query = 	"";

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_Indirect_LabourBonus_LA_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	
						"SELECT ISNULL(CP.T1, 0) AS T1, ISNULL(CP.T2, 0) AS T2, ISNULL(CP.T3, 0) AS T3, ISNULL(CP.T4, 0) AS T4, \n " +
						"ISNULL(CP.T5, 0) AS T5, ISNULL(CP.T6, 0) AS T6, ISNULL(CP.T7, 0) AS T7, ISNULL(CP.T8, 0) AS T8,  \n " +
						"ISNULL(CP.T9, 0) AS T9, ISNULL(CP.T10, 0) AS T10, ISNULL(CP.T11, 0) AS T11, ISNULL(CP.T12, 0) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " +
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100282 \n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
				rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		
		return data;
	}
	
	
	public String[] PL_Indirect_LabourBonus_PA_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	
						"SELECT ISNULL(CP.T1, 0) AS T1, ISNULL(CP.T2, 0) AS T2, ISNULL(CP.T3, 0) AS T3, ISNULL(CP.T4, 0) AS T4, \n " +
						"ISNULL(CP.T5, 0) AS T5, ISNULL(CP.T6, 0) AS T6, ISNULL(CP.T7, 0) AS T7, ISNULL(CP.T8, 0) AS T8,  \n " +
						"ISNULL(CP.T9, 0) AS T9, ISNULL(CP.T10, 0) AS T10, ISNULL(CP.T11, 0) AS T11, ISNULL(CP.T12, 0) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " +
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100283 \n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
				rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		
		return data;
	}
	

	public String[] PL_Outsourced_Printing_Expense_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = "";
		return data;
	}
	
	
	public String[] PL_SellingExpenses_Budget(){
		String[] data = new String[13];

		String query = 	"";
		return data;
	}
		
	
	public String[] PL_SellingExpenses_LA_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	
						"SELECT ISNULL(CP.T1, 0) AS T1, ISNULL(CP.T2, 0) AS T2, ISNULL(CP.T3, 0) AS T3, ISNULL(CP.T4, 0) AS T4, \n " +
						"ISNULL(CP.T5, 0) AS T5, ISNULL(CP.T6, 0) AS T6, ISNULL(CP.T7, 0) AS T7, ISNULL(CP.T8, 0) AS T8,  \n " +
						"ISNULL(CP.T9, 0) AS T9, ISNULL(CP.T10, 0) AS T10, ISNULL(CP.T11, 0) AS T11, ISNULL(CP.T12, 0) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " +
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK IN (100122, 100124, 100126) \n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
				rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		
		return data;
	}
		
	
	public String[] PL_SellingExpenses_PA_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	
						"SELECT ISNULL(CP.T1, 0) AS T1, ISNULL(CP.T2, 0) AS T2, ISNULL(CP.T3, 0) AS T3, ISNULL(CP.T4, 0) AS T4, \n " +
						"ISNULL(CP.T5, 0) AS T5, ISNULL(CP.T6, 0) AS T6, ISNULL(CP.T7, 0) AS T7, ISNULL(CP.T8, 0) AS T8,  \n " +
						"ISNULL(CP.T9, 0) AS T9, ISNULL(CP.T10, 0) AS T10, ISNULL(CP.T11, 0) AS T11, ISNULL(CP.T12, 0) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " +
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK IN (100123, 100125, 100127) \n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
				rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		
		return data;
	}
		

	public String[] PL_Transportation_Budget(){
		String[] data = new String[13];

		String query = 	"";
		return data;
	}


	public String[] PL_VPMB_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}
	
		String query = 	"SELECT SUM(ISNULL(CP.T1, 0)) AS T1, SUM(ISNULL(CP.T2, 0)) AS T2, SUM(ISNULL(CP.T3, 0)) AS T3, SUM(ISNULL(CP.T4, 0)) AS T4, \n " +
						"SUM(ISNULL(CP.T5, 0)) AS T5, SUM(ISNULL(CP.T6, 0)) AS T6, SUM(ISNULL(CP.T7, 0)) AS T7, SUM(ISNULL(CP.T8, 0)) AS T8,  \n " +
						"SUM(ISNULL(CP.T9, 0)) AS T9, SUM(ISNULL(CP.T10, 0)) AS T10, SUM(ISNULL(CP.T11, 0)) AS T11, SUM(ISNULL(CP.T12, 0)) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK\n " + 
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " " +
						"AND CP.CHIPHI_FK IN " +
						"(SELECT PK_SEQ FROM ERP_NHOMCHIPHI WHERE TTCHIPHI_FK IN " +
						"  ( SELECT PK_SEQ FROM ERP_TRUNGTAMCHIPHI WHERE MA = 'VPMB'))\n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;;

		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
				rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		
		return data;
	}


	public String[] PL_Transportation_PA_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	
						"SELECT ISNULL(CP.T1, 0) AS T1, ISNULL(CP.T2, 0) AS T2, ISNULL(CP.T3, 0) AS T3, ISNULL(CP.T4, 0) AS T4, \n " +
						"ISNULL(CP.T5, 0) AS T5, ISNULL(CP.T6, 0) AS T6, ISNULL(CP.T7, 0) AS T7, ISNULL(CP.T8, 0) AS T8,  \n " +
						"ISNULL(CP.T9, 0) AS T9, ISNULL(CP.T10, 0) AS T10, ISNULL(CP.T11, 0) AS T11, ISNULL(CP.T12, 0) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " +
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100123 \n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
				rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		
		
		return data;
	}


	public String[] PL_Commission_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}


		String query = "";
		return data;
	}


	public String[] PL_CTMB_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}
		
		String query = 	"SELECT SUM(ISNULL(CP.T1, 0)) AS T1, SUM(ISNULL(CP.T2, 0)) AS T2, SUM(ISNULL(CP.T3, 0)) AS T3, SUM(ISNULL(CP.T4, 0)) AS T4, \n " +
						"SUM(ISNULL(CP.T5, 0)) AS T5, SUM(ISNULL(CP.T6, 0)) AS T6, SUM(ISNULL(CP.T7, 0)) AS T7, SUM(ISNULL(CP.T8, 0)) AS T8,  \n " +
						"SUM(ISNULL(CP.T9, 0)) AS T9, SUM(ISNULL(CP.T10, 0)) AS T10, SUM(ISNULL(CP.T11, 0)) AS T11, SUM(ISNULL(CP.T12, 0)) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK\n " + 
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " " +
						"AND CP.CHIPHI_FK IN " +
						"(SELECT PK_SEQ FROM ERP_NHOMCHIPHI WHERE TTCHIPHI_FK IN " +
						"  ( SELECT PK_SEQ FROM ERP_TRUNGTAMCHIPHI WHERE MA = 'CTMB'))\n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;;

		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
				rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		
		return data;
	}


	public String[] PL_Commission_PA_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	
						"SELECT ISNULL(CP.T1, 0) AS T1, ISNULL(CP.T2, 0) AS T2, ISNULL(CP.T3, 0) AS T3, ISNULL(CP.T4, 0) AS T4, \n " +
						"ISNULL(CP.T5, 0) AS T5, ISNULL(CP.T6, 0) AS T6, ISNULL(CP.T7, 0) AS T7, ISNULL(CP.T8, 0) AS T8,  \n " +
						"ISNULL(CP.T9, 0) AS T9, ISNULL(CP.T10, 0) AS T10, ISNULL(CP.T11, 0) AS T11, ISNULL(CP.T12, 0) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " +
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100125 \n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
				rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		
		return data;
	}


	
	public String[] PL_PromotionExpenses_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	"";
		return data;
	}

	public String[] PL_HN_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}
		
		String query = 	"SELECT SUM(ISNULL(CP.T1, 0)) AS T1, SUM(ISNULL(CP.T2, 0)) AS T2, SUM(ISNULL(CP.T3, 0)) AS T3, SUM(ISNULL(CP.T4, 0)) AS T4, \n " +
						"SUM(ISNULL(CP.T5, 0)) AS T5, SUM(ISNULL(CP.T6, 0)) AS T6, SUM(ISNULL(CP.T7, 0)) AS T7, SUM(ISNULL(CP.T8, 0)) AS T8,  \n " +
						"SUM(ISNULL(CP.T9, 0)) AS T9, SUM(ISNULL(CP.T10, 0)) AS T10, SUM(ISNULL(CP.T11, 0)) AS T11, SUM(ISNULL(CP.T12, 0)) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK\n " + 
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " " +
						"AND CP.CHIPHI_FK IN " +
						"(SELECT PK_SEQ FROM ERP_NHOMCHIPHI WHERE TTCHIPHI_FK IN " +
						"  ( SELECT PK_SEQ FROM ERP_TRUNGTAMCHIPHI WHERE MA = 'HN'))\n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;;

		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
				rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		
		return data;
	}

	public String[] PL_ND_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}
		
		String query = 	"SELECT SUM(ISNULL(CP.T1, 0)) AS T1, SUM(ISNULL(CP.T2, 0)) AS T2, SUM(ISNULL(CP.T3, 0)) AS T3, SUM(ISNULL(CP.T4, 0)) AS T4, \n " +
						"SUM(ISNULL(CP.T5, 0)) AS T5, SUM(ISNULL(CP.T6, 0)) AS T6, SUM(ISNULL(CP.T7, 0)) AS T7, SUM(ISNULL(CP.T8, 0)) AS T8,  \n " +
						"SUM(ISNULL(CP.T9, 0)) AS T9, SUM(ISNULL(CP.T10, 0)) AS T10, SUM(ISNULL(CP.T11, 0)) AS T11, SUM(ISNULL(CP.T12, 0)) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK\n " + 
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " " +
						"AND CP.CHIPHI_FK IN " +
						"(SELECT PK_SEQ FROM ERP_NHOMCHIPHI WHERE TTCHIPHI_FK IN " +
						"  ( SELECT PK_SEQ FROM ERP_TRUNGTAMCHIPHI WHERE MA = N'NĐ'))\n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;;

		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
				rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		
		return data;
	}

	public String[] PL_HP_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}
		
		String query = 	"SELECT SUM(ISNULL(CP.T1, 0)) AS T1, SUM(ISNULL(CP.T2, 0)) AS T2, SUM(ISNULL(CP.T3, 0)) AS T3, SUM(ISNULL(CP.T4, 0)) AS T4, \n " +
						"SUM(ISNULL(CP.T5, 0)) AS T5, SUM(ISNULL(CP.T6, 0)) AS T6, SUM(ISNULL(CP.T7, 0)) AS T7, SUM(ISNULL(CP.T8, 0)) AS T8,  \n " +
						"SUM(ISNULL(CP.T9, 0)) AS T9, SUM(ISNULL(CP.T10, 0)) AS T10, SUM(ISNULL(CP.T11, 0)) AS T11, SUM(ISNULL(CP.T12, 0)) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK\n " + 
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " " +
						"AND CP.CHIPHI_FK IN " +
						"(SELECT PK_SEQ FROM ERP_NHOMCHIPHI WHERE TTCHIPHI_FK IN " +
						"  ( SELECT PK_SEQ FROM ERP_TRUNGTAMCHIPHI WHERE MA = N'HP'))\n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;;

		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
				rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		
		return data;
	}

	public String[] PL_PromotionExpenses_PA_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	
						"SELECT ISNULL(CP.T1, 0) AS T1, ISNULL(CP.T2, 0) AS T2, ISNULL(CP.T3, 0) AS T3, ISNULL(CP.T4, 0) AS T4, \n " +
						"ISNULL(CP.T5, 0) AS T5, ISNULL(CP.T6, 0) AS T6, ISNULL(CP.T7, 0) AS T7, ISNULL(CP.T8, 0) AS T8,  \n " +
						"ISNULL(CP.T9, 0) AS T9, ISNULL(CP.T10, 0) AS T10, ISNULL(CP.T11, 0) AS T11, ISNULL(CP.T12, 0) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " +
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100127 \n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
				rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		
		return data;
	}

	public String[] PL_AdminOverhead_Budget(){
		String[] data = new String[13];

		String query = 	"";
		return data;
	}
	
	public String[] PL_AdminOverhead_LA_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	
						"SELECT ISNULL(CP.T1, 0) AS T1, ISNULL(CP.T2, 0) AS T2, ISNULL(CP.T3, 0) AS T3, ISNULL(CP.T4, 0) AS T4, \n " +
						"ISNULL(CP.T5, 0) AS T5, ISNULL(CP.T6, 0) AS T6, ISNULL(CP.T7, 0) AS T7, ISNULL(CP.T8, 0) AS T8,  \n " +
						"ISNULL(CP.T9, 0) AS T9, ISNULL(CP.T10, 0) AS T10, ISNULL(CP.T11, 0) AS T11, ISNULL(CP.T12, 0) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " +
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100255 \n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
				rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		
		return data;
		
	}

	public String[] PL_AdminOverhead_PA_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	
						"SELECT ISNULL(CP.T1, 0) AS T1, ISNULL(CP.T2, 0) AS T2, ISNULL(CP.T3, 0) AS T3, ISNULL(CP.T4, 0) AS T4, \n " +
						"ISNULL(CP.T5, 0) AS T5, ISNULL(CP.T6, 0) AS T6, ISNULL(CP.T7, 0) AS T7, ISNULL(CP.T8, 0) AS T8,  \n " +
						"ISNULL(CP.T9, 0) AS T9, ISNULL(CP.T10, 0) AS T10, ISNULL(CP.T11, 0) AS T11, ISNULL(CP.T12, 0) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " +
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100256 \n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
				rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		
		return data;
	}

	public String[] PL_Staff_Allowances_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	
						"";
		
		return data;
	}

	public String[] PL_BTGD_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}
		
		String query = 	"SELECT SUM(ISNULL(CP.T1, 0)) AS T1, SUM(ISNULL(CP.T2, 0)) AS T2, SUM(ISNULL(CP.T3, 0)) AS T3, SUM(ISNULL(CP.T4, 0)) AS T4, \n " +
						"SUM(ISNULL(CP.T5, 0)) AS T5, SUM(ISNULL(CP.T6, 0)) AS T6, SUM(ISNULL(CP.T7, 0)) AS T7, SUM(ISNULL(CP.T8, 0)) AS T8,  \n " +
						"SUM(ISNULL(CP.T9, 0)) AS T9, SUM(ISNULL(CP.T10, 0)) AS T10, SUM(ISNULL(CP.T11, 0)) AS T11, SUM(ISNULL(CP.T12, 0)) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK\n " + 
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " " +
						"AND CP.CHIPHI_FK IN " +
						"(SELECT PK_SEQ FROM ERP_NHOMCHIPHI WHERE TTCHIPHI_FK IN " +
						"  ( SELECT PK_SEQ FROM ERP_TRUNGTAMCHIPHI WHERE MA = 'BTGD'))\n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;;

		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
				rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		
		return data;
	}

	public String[] PL_Staff_Allowances_PA_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	
						"SELECT ISNULL(CP.T1, 0) AS T1, ISNULL(CP.T2, 0) AS T2, ISNULL(CP.T3, 0) AS T3, ISNULL(CP.T4, 0) AS T4, \n " +
						"ISNULL(CP.T5, 0) AS T5, ISNULL(CP.T6, 0) AS T6, ISNULL(CP.T7, 0) AS T7, ISNULL(CP.T8, 0) AS T8,  \n " +
						"ISNULL(CP.T9, 0) AS T9, ISNULL(CP.T10, 0) AS T10, ISNULL(CP.T11, 0) AS T11, ISNULL(CP.T12, 0) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " +
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100256 \n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
				rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		
		return data;
	}

// CHƯA CÓ KHOẢN MỤC CHI PHÍ BANK CHARGES CHO NHÔM VÀ LÕI
// NHẬP LIỆU VÀO TỔNG CÔNG TY VÀ PHÂN BỔ XUỐNG LÕI, NHÔM THEO %DOANH SỐ
	public String[] PL_BankCharges_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	
						"";
		
		return data;
	}



// CHƯA CÓ KHOẢN MỤC CHI PHÍ BANK CHARGES CHO NHÔM VÀ LÕI
	public String[] PL_TCCB_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}
		
		String query = 	"SELECT SUM(ISNULL(CP.T1, 0)) AS T1, SUM(ISNULL(CP.T2, 0)) AS T2, SUM(ISNULL(CP.T3, 0)) AS T3, SUM(ISNULL(CP.T4, 0)) AS T4, \n " +
						"SUM(ISNULL(CP.T5, 0)) AS T5, SUM(ISNULL(CP.T6, 0)) AS T6, SUM(ISNULL(CP.T7, 0)) AS T7, SUM(ISNULL(CP.T8, 0)) AS T8,  \n " +
						"SUM(ISNULL(CP.T9, 0)) AS T9, SUM(ISNULL(CP.T10, 0)) AS T10, SUM(ISNULL(CP.T11, 0)) AS T11, SUM(ISNULL(CP.T12, 0)) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK\n " + 
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " " +
						"AND CP.CHIPHI_FK IN " +
						"(SELECT PK_SEQ FROM ERP_NHOMCHIPHI WHERE TTCHIPHI_FK IN " +
						"  ( SELECT PK_SEQ FROM ERP_TRUNGTAMCHIPHI WHERE MA = 'TCCB'))\n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;;

		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
				rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		
		return data;
	}


	// CHƯA CÓ KHOẢN MỤC CHI PHÍ BANK CHARGES CHO NHÔM VÀ LÕI
	public String[] PL_BankCharges_PA_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	
						"";
		
		return data;
	}

	public String[] PL_StaffsBonus_Budget(){
		String[] data = new String[13];

		String query = 	
					"";
		return data;
	}


	public String[] PL_HCQT_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}
		
		String query = 	"SELECT SUM(ISNULL(CP.T1, 0)) AS T1, SUM(ISNULL(CP.T2, 0)) AS T2, SUM(ISNULL(CP.T3, 0)) AS T3, SUM(ISNULL(CP.T4, 0)) AS T4, \n " +
						"SUM(ISNULL(CP.T5, 0)) AS T5, SUM(ISNULL(CP.T6, 0)) AS T6, SUM(ISNULL(CP.T7, 0)) AS T7, SUM(ISNULL(CP.T8, 0)) AS T8,  \n " +
						"SUM(ISNULL(CP.T9, 0)) AS T9, SUM(ISNULL(CP.T10, 0)) AS T10, SUM(ISNULL(CP.T11, 0)) AS T11, SUM(ISNULL(CP.T12, 0)) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK\n " + 
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " " +
						"AND CP.CHIPHI_FK IN " +
						"(SELECT PK_SEQ FROM ERP_NHOMCHIPHI WHERE TTCHIPHI_FK IN " +
						"  ( SELECT PK_SEQ FROM ERP_TRUNGTAMCHIPHI WHERE MA = 'HCQT'))\n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
				rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		
		return data;
	}

	public String[] PL_StaffsBonus_PA_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	
						"SELECT ISNULL(CP.T1, 0) AS T1, ISNULL(CP.T2, 0) AS T2, ISNULL(CP.T3, 0) AS T3, ISNULL(CP.T4, 0) AS T4, \n " +
						"ISNULL(CP.T5, 0) AS T5, ISNULL(CP.T6, 0) AS T6, ISNULL(CP.T7, 0) AS T7, ISNULL(CP.T8, 0) AS T8,  \n " +
						"ISNULL(CP.T9, 0) AS T9, ISNULL(CP.T10, 0) AS T10, ISNULL(CP.T11, 0) AS T11, ISNULL(CP.T12, 0) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " +
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100258 \n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
				rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		
		return data;
	}

	public String[] PL_Depreciation_Building_Office_Budget(){
		String[] data = new String[12];
		
		String query = 	"SELECT THANG, SUM(NGANSACH) AS NUM \n " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH \n " +
						"WHERE NAM = '" + this.year + "' AND TTCP_FK IN (SELECT TTCHIPHI_FK FROM ERP_NHOMCHIPHI WHERE PK_SEQ IN(100225)) \n " +
						"GROUP BY THANG \n " +
						"ORDER BY THANG ";

		data = prepareData(data, query);
		return data;
	}


	public String[] PL_TCKT_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}
		
		String query = 	"SELECT SUM(ISNULL(CP.T1, 0)) AS T1, SUM(ISNULL(CP.T2, 0)) AS T2, SUM(ISNULL(CP.T3, 0)) AS T3, SUM(ISNULL(CP.T4, 0)) AS T4, \n " +
						"SUM(ISNULL(CP.T5, 0)) AS T5, SUM(ISNULL(CP.T6, 0)) AS T6, SUM(ISNULL(CP.T7, 0)) AS T7, SUM(ISNULL(CP.T8, 0)) AS T8,  \n " +
						"SUM(ISNULL(CP.T9, 0)) AS T9, SUM(ISNULL(CP.T10, 0)) AS T10, SUM(ISNULL(CP.T11, 0)) AS T11, SUM(ISNULL(CP.T12, 0)) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK\n " + 
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " " +
						"AND CP.CHIPHI_FK IN " +
						"(SELECT PK_SEQ FROM ERP_NHOMCHIPHI WHERE TTCHIPHI_FK IN " +
						"  ( SELECT PK_SEQ FROM ERP_TRUNGTAMCHIPHI WHERE MA = 'TCKT'))\n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
				rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		
		return data;
	}


	public String[] PL_Depreciation_Building_Office_PA_Budget(){
		String[] data = new String[13];
		
		String query = 	"SELECT TTCP.THANG, SUM(TTCP.NGANSACH)*PTDS.PTDS_PA AS NUM \n " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH TTCP \n " +
						"LEFT JOIN ( \n " +
						"" + this.query_ptDS + " \n " +
						")PTDS ON PTDS.THANG = TTCP.THANG \n " +
						
						"WHERE TTCP.NAM = '" + this.year + "' AND TTCP.TTCP_FK IN (SELECT TTCHIPHI_FK FROM ERP_NHOMCHIPHI WHERE PK_SEQ IN(100225)) \n " +

						"GROUP BY TTCP.THANG, PTDS.PTDS_PA \n " +
						"ORDER BY TTCP.THANG ";


		data = prepareData(data, query);
		return data;
	}

//CHƯA CÓ KHOẢN MỤC CHI PHÍ DEPRECIATION OF OFFICE EQUIPMENT CHO LÕI VÀ NHÔM

	public String[] PL_Depreciation_Equipment_Office_Budget(){
		String[] data = new String[13];
		
		String query = 	"SELECT THANG, SUM(NGANSACH) AS NUM \n " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH \n " +
						"WHERE NAM = '" + this.year + "' AND TTCP_FK IN (SELECT TTCHIPHI_FK FROM ERP_NHOMCHIPHI WHERE PK_SEQ IN(100260)) \n " +
						"GROUP BY THANG \n " +
						"ORDER BY THANG ";

		data = prepareData(data, query);
		return data;
	}
	


	public String[] PL_QTRR_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}
		
		String query = 	"SELECT SUM(ISNULL(CP.T1, 0)) AS T1, SUM(ISNULL(CP.T2, 0)) AS T2, SUM(ISNULL(CP.T3, 0)) AS T3, SUM(ISNULL(CP.T4, 0)) AS T4, \n " +
						"SUM(ISNULL(CP.T5, 0)) AS T5, SUM(ISNULL(CP.T6, 0)) AS T6, SUM(ISNULL(CP.T7, 0)) AS T7, SUM(ISNULL(CP.T8, 0)) AS T8,  \n " +
						"SUM(ISNULL(CP.T9, 0)) AS T9, SUM(ISNULL(CP.T10, 0)) AS T10, SUM(ISNULL(CP.T11, 0)) AS T11, SUM(ISNULL(CP.T12, 0)) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK\n " + 
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " " +
						"AND CP.CHIPHI_FK IN " +
						"(SELECT PK_SEQ FROM ERP_NHOMCHIPHI WHERE TTCHIPHI_FK IN " +
						"  ( SELECT PK_SEQ FROM ERP_TRUNGTAMCHIPHI WHERE MA = 'QTRR'))\n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
				rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		
		return data;
	}
	

	public String[] PL_Depreciation_Equipment_Office_PA_Budget(){
		String[] data = new String[13];
		
		String query = 	"SELECT TTCP.THANG, SUM(TTCP.NGANSACH)*PTDS.PTDS_PA AS NUM \n " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH TTCP \n " +
						"LEFT JOIN ( \n " +
						"" + this.query_ptDS + " \n " +
						")PTDS ON PTDS.THANG = TTCP.THANG \n " +
						
						"WHERE TTCP.NAM = '" + this.year + "' AND TTCP.TTCP_FK IN (SELECT TTCHIPHI_FK FROM ERP_NHOMCHIPHI WHERE PK_SEQ IN(100260)) \n " +

						"GROUP BY TTCP.THANG, PTDS.PTDS_PA \n " +
						"ORDER BY TTCP.THANG ";

		data = prepareData(data, query);
		return data;
	}
	

//CHƯA CÓ KHOẢN MỤC CHI PHÍ DEPRECIATION OF OFFICE EQUIPMENT CHO LÕI VÀ NHÔM
	public String[] PL_Depreciation_Motor_Office_Budget(){
		String[] data = new String[13];
		
		String query = 	"SELECT THANG, SUM(NGANSACH) AS NUM \n " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH \n " +
						"WHERE NAM = '" + this.year + "' AND TTCP_FK IN (SELECT TTCHIPHI_FK FROM ERP_NHOMCHIPHI WHERE PK_SEQ IN(100261)) \n " +
						"GROUP BY THANG \n " +
						"ORDER BY THANG ";

		data = prepareData(data, query);
		return data;
	}	
	

	//CHƯA CÓ KHOẢN MỤC CHI PHÍ DEPRECIATION OF OFFICE EQUIPMENT CHO LÕI VÀ NHÔM
		public String[] PL_MKT_Budget(){
			String[] data = new String[12];
			for(int i = 0; i < 12; i++){
				data[i] = "0";
			}
			
			String query = 	"SELECT SUM(ISNULL(CP.T1, 0)) AS T1, SUM(ISNULL(CP.T2, 0)) AS T2, SUM(ISNULL(CP.T3, 0)) AS T3, SUM(ISNULL(CP.T4, 0)) AS T4, \n " +
							"SUM(ISNULL(CP.T5, 0)) AS T5, SUM(ISNULL(CP.T6, 0)) AS T6, SUM(ISNULL(CP.T7, 0)) AS T7, SUM(ISNULL(CP.T8, 0)) AS T8,  \n " +
							"SUM(ISNULL(CP.T9, 0)) AS T9, SUM(ISNULL(CP.T10, 0)) AS T10, SUM(ISNULL(CP.T11, 0)) AS T11, SUM(ISNULL(CP.T12, 0)) AS T12 \n " +
							"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
							"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK\n " + 
							"WHERE LNS.CONGTY_FK = " + this.ctyId + " " +
							"AND CP.CHIPHI_FK IN " +
							"(SELECT PK_SEQ FROM ERP_NHOMCHIPHI WHERE TTCHIPHI_FK IN " +
							"  ( SELECT PK_SEQ FROM ERP_TRUNGTAMCHIPHI WHERE MA = 'MKT'))\n " +
							"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
			ResultSet rs = this.db.get(query);
			if(rs != null){
				try{
					if(rs.next()){
						for(int i = 0; i < 12; i++){
							data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
						}
					rs.close();
					}
				}catch(java.sql.SQLException e){}
			}
			
			return data;
		}	


		//CHƯA CÓ KHOẢN MỤC CHI PHÍ DEPRECIATION OF OFFICE EQUIPMENT CHO LÕI VÀ NHÔM
			public String[] PL_Depreciation_Motor_Office_PA_Budget(){
				String[] data = new String[13];
				
				String query = 	"SELECT TTCP.THANG, SUM(TTCP.NGANSACH)*PTDS.PTDS_PA AS NUM \n " +
								"FROM ERP_TRUNGTAMCHIPHI_NGANSACH TTCP \n " +
								"LEFT JOIN ( \n " +
								"" + this.query_ptDS + " \n " +
								")PTDS ON PTDS.THANG = TTCP.THANG \n " +
								
								"WHERE TTCP.NAM = '" + this.year + "' AND TTCP.TTCP_FK IN (SELECT TTCHIPHI_FK FROM ERP_NHOMCHIPHI WHERE PK_SEQ IN(100261)) \n " +

								"GROUP BY TTCP.THANG, PTDS.PTDS_PA \n " +
								
								"ORDER BY TTCP.THANG ";
				
				data = prepareData(data, query);
				return data;
			}	

			
		public String[] PL_Phone_Fax_Electricity_Office_Budget(){
			String[] data = new String[12];
			for(int i = 0; i < 12; i++){
				data[i] = "0";
			}

			
		return data;
	}	

		
		public String[] PL_NCPT_Budget(){
			String[] data = new String[12];
			for(int i = 0; i < 12; i++){
				data[i] = "0";
			}
			
			String query = 	"SELECT SUM(ISNULL(CP.T1, 0)) AS T1, SUM(ISNULL(CP.T2, 0)) AS T2, SUM(ISNULL(CP.T3, 0)) AS T3, SUM(ISNULL(CP.T4, 0)) AS T4, \n " +
							"SUM(ISNULL(CP.T5, 0)) AS T5, SUM(ISNULL(CP.T6, 0)) AS T6, SUM(ISNULL(CP.T7, 0)) AS T7, SUM(ISNULL(CP.T8, 0)) AS T8,  \n " +
							"SUM(ISNULL(CP.T9, 0)) AS T9, SUM(ISNULL(CP.T10, 0)) AS T10, SUM(ISNULL(CP.T11, 0)) AS T11, SUM(ISNULL(CP.T12, 0)) AS T12 \n " +
							"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
							"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK\n " + 
							"WHERE LNS.CONGTY_FK = " + this.ctyId + " " +
							"AND CP.CHIPHI_FK IN " +
							"(SELECT PK_SEQ FROM ERP_NHOMCHIPHI WHERE TTCHIPHI_FK IN " +
							"  ( SELECT PK_SEQ FROM ERP_TRUNGTAMCHIPHI WHERE MA = 'NCPT'))\n " +
							"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
			ResultSet rs = this.db.get(query);
			if(rs != null){
				try{
					if(rs.next()){
						for(int i = 0; i < 12; i++){
							data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
						}
					rs.close();
					}
				}catch(java.sql.SQLException e){}
			}
			
			return data;
		}	

		
		public String[] PL_Phone_Fax_Electricity_Office_PA_Budget(){
			
		String[] data = new String[12];

		String query = 	
					"SELECT DATA.THANG, SUM(NUM)*PTDS.PTDS_PA AS NUM FROM ( \n " +
						"	SELECT 1 AS THANG, ISNULL(CP.T1, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100182  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 2 AS THANG, ISNULL(CP.T2, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100182  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 3 AS THANG, ISNULL(CP.T3, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100182  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 4 AS THANG, ISNULL(CP.T4, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100182  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 5 AS THANG, ISNULL(CP.T5, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100182  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 6 AS THANG, ISNULL(CP.T6, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100182  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 7 AS THANG, ISNULL(CP.T7, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100182  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 8 AS THANG, ISNULL(CP.T8, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100182  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 9 AS THANG, ISNULL(CP.T9, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100182  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 10 AS THANG, ISNULL(CP.T10, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100182  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 11 AS THANG, ISNULL(CP.T11, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100182  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 12 AS THANG, ISNULL(CP.T12, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100182  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
					
					")DATA \n " +
					"LEFT JOIN ( \n " +
					
					"" + this.query_ptDS + "\n " +
					
					")PTDS ON PTDS.THANG = DATA.THANG \n " +
					"GROUP BY DATA.THANG, PTDS.PTDS_PA \n " +

					"ORDER BY DATA.THANG \n ";


		data = prepareData(data, query);
		return data;
	}	


	public String[] PL_PublicRelations_Budget(){
		String[] data = new String[13];

		String query = 	
					"";
		return data;
	}	
	

	public String[] PL_XNK_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}
		
		String query = 	"SELECT SUM(ISNULL(CP.T1, 0)) AS T1, SUM(ISNULL(CP.T2, 0)) AS T2, SUM(ISNULL(CP.T3, 0)) AS T3, SUM(ISNULL(CP.T4, 0)) AS T4, \n " +
						"SUM(ISNULL(CP.T5, 0)) AS T5, SUM(ISNULL(CP.T6, 0)) AS T6, SUM(ISNULL(CP.T7, 0)) AS T7, SUM(ISNULL(CP.T8, 0)) AS T8,  \n " +
						"SUM(ISNULL(CP.T9, 0)) AS T9, SUM(ISNULL(CP.T10, 0)) AS T10, SUM(ISNULL(CP.T11, 0)) AS T11, SUM(ISNULL(CP.T12, 0)) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK\n " + 
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " " +
						"AND CP.CHIPHI_FK IN " +
						"(SELECT PK_SEQ FROM ERP_NHOMCHIPHI WHERE TTCHIPHI_FK IN " +
						"  ( SELECT PK_SEQ FROM ERP_TRUNGTAMCHIPHI WHERE MA = 'XNK'))\n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
				rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		
		return data;
	}	


	public String[] PL_PublicRelations_PA_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	
						"SELECT ISNULL(CP.T1, 0) AS T1, ISNULL(CP.T2, 0) AS T2, ISNULL(CP.T3, 0) AS T3, ISNULL(CP.T4, 0) AS T4, \n " +
						"ISNULL(CP.T5, 0) AS T5, ISNULL(CP.T6, 0) AS T6, ISNULL(CP.T7, 0) AS T7, ISNULL(CP.T8, 0) AS T8,  \n " +
						"ISNULL(CP.T9, 0) AS T9, ISNULL(CP.T10, 0) AS T10, ISNULL(CP.T11, 0) AS T11, ISNULL(CP.T12, 0) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " +
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100263 \n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
				rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		return data;
	}	

	public String[] PL_Entertainments_Budget(){
		String[] data = new String[13];

		String query = 	"";
		return data;
	}	
		

	public String[] PL_KH_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}
		
		String query = 	"SELECT SUM(ISNULL(CP.T1, 0)) AS T1, SUM(ISNULL(CP.T2, 0)) AS T2, SUM(ISNULL(CP.T3, 0)) AS T3, SUM(ISNULL(CP.T4, 0)) AS T4, \n " +
						"SUM(ISNULL(CP.T5, 0)) AS T5, SUM(ISNULL(CP.T6, 0)) AS T6, SUM(ISNULL(CP.T7, 0)) AS T7, SUM(ISNULL(CP.T8, 0)) AS T8,  \n " +
						"SUM(ISNULL(CP.T9, 0)) AS T9, SUM(ISNULL(CP.T10, 0)) AS T10, SUM(ISNULL(CP.T11, 0)) AS T11, SUM(ISNULL(CP.T12, 0)) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK\n " + 
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " " +
						"AND CP.CHIPHI_FK IN " +
						"(SELECT PK_SEQ FROM ERP_NHOMCHIPHI WHERE TTCHIPHI_FK IN " +
						"  ( SELECT PK_SEQ FROM ERP_TRUNGTAMCHIPHI WHERE MA = 'KH'))\n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
				rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		
		return data;
	}	
		

	public String[] PL_Entertainments_PA_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	
						"SELECT ISNULL(CP.T1, 0) AS T1, ISNULL(CP.T2, 0) AS T2, ISNULL(CP.T3, 0) AS T3, ISNULL(CP.T4, 0) AS T4, \n " +
						"ISNULL(CP.T5, 0) AS T5, ISNULL(CP.T6, 0) AS T6, ISNULL(CP.T7, 0) AS T7, ISNULL(CP.T8, 0) AS T8,  \n " +
						"ISNULL(CP.T9, 0) AS T9, ISNULL(CP.T10, 0) AS T10, ISNULL(CP.T11, 0) AS T11, ISNULL(CP.T12, 0) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " +
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100265 \n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
				rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		return data;
	}	
		

// CHƯA CÓ KHOẢN MỤC CHI PHÍ "OFFICE EXPENSES" CHO LÕI VÀ NHÔM
	public String[] PL_OfficeExpenses_Budget(){
		String[] data = new String[13];

		String query = 	
					"";
		return data;
	}	
	

	// CHƯA CÓ KHOẢN MỤC CHI PHÍ "OFFICE EXPENSES" CHO LÕI VÀ NHÔM
		public String[] PL_DA_Budget(){
			String[] data = new String[12];
			for(int i = 0; i < 12; i++){
				data[i] = "0";
			}
			
			String query = 	"SELECT SUM(ISNULL(CP.T1, 0)) AS T1, SUM(ISNULL(CP.T2, 0)) AS T2, SUM(ISNULL(CP.T3, 0)) AS T3, SUM(ISNULL(CP.T4, 0)) AS T4, \n " +
							"SUM(ISNULL(CP.T5, 0)) AS T5, SUM(ISNULL(CP.T6, 0)) AS T6, SUM(ISNULL(CP.T7, 0)) AS T7, SUM(ISNULL(CP.T8, 0)) AS T8,  \n " +
							"SUM(ISNULL(CP.T9, 0)) AS T9, SUM(ISNULL(CP.T10, 0)) AS T10, SUM(ISNULL(CP.T11, 0)) AS T11, SUM(ISNULL(CP.T12, 0)) AS T12 \n " +
							"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
							"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK\n " + 
							"WHERE LNS.CONGTY_FK = " + this.ctyId + " " +
							"AND CP.CHIPHI_FK IN " +
							"(SELECT PK_SEQ FROM ERP_NHOMCHIPHI WHERE TTCHIPHI_FK IN " +
							"  ( SELECT PK_SEQ FROM ERP_TRUNGTAMCHIPHI WHERE MA = 'DA'))\n " +
							"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
			ResultSet rs = this.db.get(query);
			if(rs != null){
				try{
					if(rs.next()){
						for(int i = 0; i < 12; i++){
							data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
						}
					rs.close();
					}
				}catch(java.sql.SQLException e){}
			}
			
			return data;
		}	
		

		// CHƯA CÓ KHOẢN MỤC CHI PHÍ "OFFICE EXPENSES" CHO LÕI VÀ NHÔM
			public String[] PL_OfficeExpenses_PA_Budget(){
				
				String[] data = new String[12];

				String query = 	
							"SELECT DATA.THANG, SUM(NUM)*PTDS.PTDS_PA AS NUM FROM ( \n " +
								"	SELECT 1 AS THANG, ISNULL(CP.T1, 0) AS NUM \n " +
								"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
								"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
								"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100185  \n " +
								"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
								
								"	UNION ALL \n " +
								"	SELECT 2 AS THANG, ISNULL(CP.T2, 0) AS NUM \n " +
								"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
								"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
								"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100185  \n " +
								"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
								
								"	UNION ALL \n " +
								"	SELECT 3 AS THANG, ISNULL(CP.T3, 0) AS NUM \n " +
								"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
								"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
								"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100185  \n " +
								"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
								
								"	UNION ALL \n " +
								"	SELECT 4 AS THANG, ISNULL(CP.T4, 0) AS NUM \n " +
								"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
								"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
								"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100185  \n " +
								"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
								
								"	UNION ALL \n " +
								"	SELECT 5 AS THANG, ISNULL(CP.T5, 0) AS NUM \n " +
								"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
								"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
								"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100185  \n " +
								"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
								
								"	UNION ALL \n " +
								"	SELECT 6 AS THANG, ISNULL(CP.T6, 0) AS NUM \n " +
								"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
								"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
								"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100185  \n " +
								"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
								
								"	UNION ALL \n " +
								"	SELECT 7 AS THANG, ISNULL(CP.T7, 0) AS NUM \n " +
								"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
								"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
								"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100185  \n " +
								"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
								
								"	UNION ALL \n " +
								"	SELECT 8 AS THANG, ISNULL(CP.T8, 0) AS NUM \n " +
								"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
								"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
								"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100185  \n " +
								"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
								
								"	UNION ALL \n " +
								"	SELECT 9 AS THANG, ISNULL(CP.T9, 0) AS NUM \n " +
								"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
								"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
								"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100185  \n " +
								"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
								
								"	UNION ALL \n " +
								"	SELECT 10 AS THANG, ISNULL(CP.T10, 0) AS NUM \n " +
								"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
								"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
								"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100185  \n " +
								"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
								
								"	UNION ALL \n " +
								"	SELECT 11 AS THANG, ISNULL(CP.T11, 0) AS NUM \n " +
								"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
								"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
								"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100185  \n " +
								"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
								
								"	UNION ALL \n " +
								"	SELECT 12 AS THANG, ISNULL(CP.T12, 0) AS NUM \n " +
								"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
								"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
								"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100185  \n " +
								"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
							
							")DATA \n " +
							"LEFT JOIN ( \n " +
							
							"" + this.query_ptDS + "\n " +
							
							")PTDS ON PTDS.THANG = DATA.THANG \n " +
							"GROUP BY DATA.THANG, PTDS.PTDS_PA \n " +

							"ORDER BY DATA.THANG \n ";


				data = prepareData(data, query);
				return data;
			}	
			

// CHƯA CÓ KHOẢN MỤC CHI PHÍ "HANDLING CHARGE" CHO LÕI VÀ NHÔM
	public String[] PL_HandlingCharges_Budget(){
		String[] data = new String[13];

		String query = 	
					"";


		data = prepareData(data, query);
		return data;
	}	

	
	// CHƯA CÓ KHOẢN MỤC CHI PHÍ "HANDLING CHARGE" CHO LÕI VÀ NHÔM
		public String[] PL_PBC_Budget(){
			String[] data = new String[12];
			for(int i = 0; i < 12; i++){
				data[i] = "0";
			}
			
			String query = 	"SELECT SUM(ISNULL(CP.T1, 0)) AS T1, SUM(ISNULL(CP.T2, 0)) AS T2, SUM(ISNULL(CP.T3, 0)) AS T3, SUM(ISNULL(CP.T4, 0)) AS T4, \n " +
							"SUM(ISNULL(CP.T5, 0)) AS T5, SUM(ISNULL(CP.T6, 0)) AS T6, SUM(ISNULL(CP.T7, 0)) AS T7, SUM(ISNULL(CP.T8, 0)) AS T8,  \n " +
							"SUM(ISNULL(CP.T9, 0)) AS T9, SUM(ISNULL(CP.T10, 0)) AS T10, SUM(ISNULL(CP.T11, 0)) AS T11, SUM(ISNULL(CP.T12, 0)) AS T12 \n " +
							"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
							"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK\n " + 
							"WHERE LNS.CONGTY_FK = " + this.ctyId + " " +
							"AND CP.CHIPHI_FK IN " +
							"(SELECT PK_SEQ FROM ERP_NHOMCHIPHI WHERE TTCHIPHI_FK IN " +
							"  ( SELECT PK_SEQ FROM ERP_TRUNGTAMCHIPHI WHERE MA = 'PBC'))\n " +
							"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
			ResultSet rs = this.db.get(query);
			if(rs != null){
				try{
					if(rs.next()){
						for(int i = 0; i < 12; i++){
							data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
						}
					rs.close();
					}
				}catch(java.sql.SQLException e){}
			}
			
			return data;
		}	

		
		// CHƯA CÓ KHOẢN MỤC CHI PHÍ "HANDLING CHARGE" CHO LÕI VÀ NHÔM
			public String[] PL_HandlingCharges_PA_Budget(){
				
				String[] data = new String[12];

				String query = 	
							"SELECT DATA.THANG, SUM(NUM)*PTDS.PTDS_PA AS NUM FROM ( \n " +
								"	SELECT 1 AS THANG, ISNULL(CP.T1, 0) AS NUM \n " +
								"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
								"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
								"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100185  \n " +
								"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
								
								"	UNION ALL \n " +
								"	SELECT 2 AS THANG, ISNULL(CP.T2, 0) AS NUM \n " +
								"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
								"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
								"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100185  \n " +
								"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
								
								"	UNION ALL \n " +
								"	SELECT 3 AS THANG, ISNULL(CP.T3, 0) AS NUM \n " +
								"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
								"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
								"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100185  \n " +
								"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
								
								"	UNION ALL \n " +
								"	SELECT 4 AS THANG, ISNULL(CP.T4, 0) AS NUM \n " +
								"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
								"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
								"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100185  \n " +
								"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
								
								"	UNION ALL \n " +
								"	SELECT 5 AS THANG, ISNULL(CP.T5, 0) AS NUM \n " +
								"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
								"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
								"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100185  \n " +
								"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
								
								"	UNION ALL \n " +
								"	SELECT 6 AS THANG, ISNULL(CP.T6, 0) AS NUM \n " +
								"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
								"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
								"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100185  \n " +
								"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
								
								"	UNION ALL \n " +
								"	SELECT 7 AS THANG, ISNULL(CP.T7, 0) AS NUM \n " +
								"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
								"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
								"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100185  \n " +
								"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
								
								"	UNION ALL \n " +
								"	SELECT 8 AS THANG, ISNULL(CP.T8, 0) AS NUM \n " +
								"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
								"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
								"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100185  \n " +
								"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
								
								"	UNION ALL \n " +
								"	SELECT 9 AS THANG, ISNULL(CP.T9, 0) AS NUM \n " +
								"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
								"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
								"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100185  \n " +
								"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
								
								"	UNION ALL \n " +
								"	SELECT 10 AS THANG, ISNULL(CP.T10, 0) AS NUM \n " +
								"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
								"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
								"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100185  \n " +
								"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
								
								"	UNION ALL \n " +
								"	SELECT 11 AS THANG, ISNULL(CP.T11, 0) AS NUM \n " +
								"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
								"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
								"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100185  \n " +
								"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
								
								"	UNION ALL \n " +
								"	SELECT 12 AS THANG, ISNULL(CP.T12, 0) AS NUM \n " +
								"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
								"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
								"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100185  \n " +
								"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
							
							")DATA \n " +
							"LEFT JOIN ( \n " +
							
							"" + this.query_ptDS + "\n " +
							
							")PTDS ON PTDS.THANG = DATA.THANG \n " +
							"GROUP BY DATA.THANG, PTDS.PTDS_PA \n " +

							"ORDER BY DATA.THANG \n ";


				data = prepareData(data, query);
				return data;
			}	


	// CHƯA CÓ KHOẢN MỤC CHI PHÍ "PRINTING & STATIONERY (OFFICE)" CHO LÕI VÀ NHÔM
	public String[] PL_Printing_Stationery_Office_Budget(){
		String[] data = new String[13];

		String query = 	
					"";


		data = prepareData(data, query);
		return data;
	}	


	// CHƯA CÓ KHOẢN MỤC CHI PHÍ "PRINTING & STATIONERY (OFFICE)" CHO LÕI VÀ NHÔM
	public String[] PL_Printing_Stationery_Office_LA_Budget(){
		
		String[] data = new String[12];

		String query = 	
					"SELECT DATA.THANG, SUM(NUM)*PTDS.PTDS_LA AS NUM FROM ( \n " +
						"	SELECT 1 AS THANG, ISNULL(CP.T1, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100187  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 2 AS THANG, ISNULL(CP.T2, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100187  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 3 AS THANG, ISNULL(CP.T3, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100187  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 4 AS THANG, ISNULL(CP.T4, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100187  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 5 AS THANG, ISNULL(CP.T5, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100187  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 6 AS THANG, ISNULL(CP.T6, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100187  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 7 AS THANG, ISNULL(CP.T7, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100187  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 8 AS THANG, ISNULL(CP.T8, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100187  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 9 AS THANG, ISNULL(CP.T9, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100187  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 10 AS THANG, ISNULL(CP.T10, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100187  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 11 AS THANG, ISNULL(CP.T11, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100187  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 12 AS THANG, ISNULL(CP.T12, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100187  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
					
					")DATA \n " +
					"LEFT JOIN ( \n " +
					
					"" + this.query_ptDS + "\n " +
					
					")PTDS ON PTDS.THANG = DATA.THANG \n " +
					"GROUP BY DATA.THANG, PTDS.PTDS_LA \n " +

					"ORDER BY DATA.THANG \n ";


		data = prepareData(data, query);
		return data;
	}	


	// CHƯA CÓ KHOẢN MỤC CHI PHÍ "PRINTING & STATIONERY (OFFICE)" CHO LÕI VÀ NHÔM
	public String[] PL_Printing_Stationery_Office_PA_Budget(){
		
		String[] data = new String[12];

		String query = 	
					"SELECT DATA.THANG, SUM(NUM)*PTDS.PTDS_PA AS NUM FROM ( \n " +
						"	SELECT 1 AS THANG, ISNULL(CP.T1, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100187  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 2 AS THANG, ISNULL(CP.T2, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100187  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 3 AS THANG, ISNULL(CP.T3, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100187  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 4 AS THANG, ISNULL(CP.T4, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100187  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 5 AS THANG, ISNULL(CP.T5, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100187  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 6 AS THANG, ISNULL(CP.T6, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100187  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 7 AS THANG, ISNULL(CP.T7, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100187  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 8 AS THANG, ISNULL(CP.T8, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100187  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 9 AS THANG, ISNULL(CP.T9, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100187  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 10 AS THANG, ISNULL(CP.T10, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100187  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 11 AS THANG, ISNULL(CP.T11, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100187  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 12 AS THANG, ISNULL(CP.T12, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100187  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
					
					")DATA \n " +
					"LEFT JOIN ( \n " +
					
					"" + this.query_ptDS + "\n " +
					
					")PTDS ON PTDS.THANG = DATA.THANG \n " +
					"GROUP BY DATA.THANG, PTDS.PTDS_PA \n " +

					"ORDER BY DATA.THANG \n ";


		data = prepareData(data, query);
		return data;
	}	

	// CHƯA CÓ KHOẢN MỤC CHI PHÍ "TRAINING FEE" CHO LÕI VÀ NHÔM
	public String[] PL_TrainingFees_Budget(){
		String[] data = new String[13];

		String query = 	
					"";
		return data;
	}	


	// CHƯA CÓ KHOẢN MỤC CHI PHÍ "TRAINING FEE" CHO LÕI VÀ NHÔM
	public String[] PL_TrainingFees_LA_Budget(){
		
		String[] data = new String[12];

		String query = 	
					"SELECT DATA.THANG, SUM(NUM)*PTDS.PTDS_LA AS NUM FROM ( \n " +
						"	SELECT 1 AS THANG, ISNULL(CP.T1, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100188  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 2 AS THANG, ISNULL(CP.T2, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100188  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 3 AS THANG, ISNULL(CP.T3, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100188  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 4 AS THANG, ISNULL(CP.T4, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100188  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 5 AS THANG, ISNULL(CP.T5, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100188  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 6 AS THANG, ISNULL(CP.T6, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100188  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 7 AS THANG, ISNULL(CP.T7, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100188  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 8 AS THANG, ISNULL(CP.T8, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100188  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 9 AS THANG, ISNULL(CP.T9, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100188  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 10 AS THANG, ISNULL(CP.T10, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100188  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 11 AS THANG, ISNULL(CP.T11, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100188  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 12 AS THANG, ISNULL(CP.T12, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100188  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
					
					")DATA \n " +
					"LEFT JOIN ( \n " +
					
					"" + this.query_ptDS + "\n " +
					
					")PTDS ON PTDS.THANG = DATA.THANG \n " +
					"GROUP BY DATA.THANG, PTDS.PTDS_LA \n " +

					"ORDER BY DATA.THANG \n ";


		data = prepareData(data, query);
		return data;
	}	


	// CHƯA CÓ KHOẢN MỤC CHI PHÍ "TRAINING FEE" CHO LÕI VÀ NHÔM
	public String[] PL_TrainingFees_PA_Budget(){
		
		String[] data = new String[12];

		String query = 	
					"SELECT DATA.THANG, SUM(NUM)*PTDS.PTDS_PA AS NUM FROM ( \n " +
						"	SELECT 1 AS THANG, ISNULL(CP.T1, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100188  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 2 AS THANG, ISNULL(CP.T2, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100188  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 3 AS THANG, ISNULL(CP.T3, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100188  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 4 AS THANG, ISNULL(CP.T4, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100188  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 5 AS THANG, ISNULL(CP.T5, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100188  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 6 AS THANG, ISNULL(CP.T6, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100188  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 7 AS THANG, ISNULL(CP.T7, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100188  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 8 AS THANG, ISNULL(CP.T8, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100188  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 9 AS THANG, ISNULL(CP.T9, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100188  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 10 AS THANG, ISNULL(CP.T10, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100188  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 11 AS THANG, ISNULL(CP.T11, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100188  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 12 AS THANG, ISNULL(CP.T12, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100188  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
					
					")DATA \n " +
					"LEFT JOIN ( \n " +
					
					"" + this.query_ptDS + "\n " +
					
					")PTDS ON PTDS.THANG = DATA.THANG \n " +
					"GROUP BY DATA.THANG, PTDS.PTDS_PA \n " +

					"ORDER BY DATA.THANG \n ";


		data = prepareData(data, query);
		return data;
	}	


	public String[] PL_Trevelling_Budget(){
		String[] data = new String[13];

		String query = 	
					"";
		return data;
	}	


	public String[] PL_Trevelling_LA_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	
						"SELECT ISNULL(CP.T1, 0) AS T1, ISNULL(CP.T2, 0) AS T2, ISNULL(CP.T3, 0) AS T3, ISNULL(CP.T4, 0) AS T4, \n " +
						"ISNULL(CP.T5, 0) AS T5, ISNULL(CP.T6, 0) AS T6, ISNULL(CP.T7, 0) AS T7, ISNULL(CP.T8, 0) AS T8,  \n " +
						"ISNULL(CP.T9, 0) AS T9, ISNULL(CP.T10, 0) AS T10, ISNULL(CP.T11, 0) AS T11, ISNULL(CP.T12, 0) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " +
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100266 \n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
				rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		return data;
	}	


	public String[] PL_Trevelling_PA_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	
						"SELECT ISNULL(CP.T1, 0) AS T1, ISNULL(CP.T2, 0) AS T2, ISNULL(CP.T3, 0) AS T3, ISNULL(CP.T4, 0) AS T4, \n " +
						"ISNULL(CP.T5, 0) AS T5, ISNULL(CP.T6, 0) AS T6, ISNULL(CP.T7, 0) AS T7, ISNULL(CP.T8, 0) AS T8,  \n " +
						"ISNULL(CP.T9, 0) AS T9, ISNULL(CP.T10, 0) AS T10, ISNULL(CP.T11, 0) AS T11, ISNULL(CP.T12, 0) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " +
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100267 \n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
				rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		return data;
	}	

	public String[] PL_Upkeep_Motor_Vehicle_Office_Budget(){
		String[] data = new String[13];

		String query = 	
					"";

		return data;
	}	


	public String[] PL_Upkeep_Motor_Vehicle_Office_LA_Budget(){
		String[] data = new String[12];

		String query = 	
					"SELECT DATA.THANG, SUM(NUM)*PTDS.PTDS_LA AS NUM FROM ( \n " +
						"	SELECT 1 AS THANG, ISNULL(CP.T1, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100190  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 2 AS THANG, ISNULL(CP.T2, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100190  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 3 AS THANG, ISNULL(CP.T3, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100190  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 4 AS THANG, ISNULL(CP.T4, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100190  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 5 AS THANG, ISNULL(CP.T5, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100190  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 6 AS THANG, ISNULL(CP.T6, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100190  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 7 AS THANG, ISNULL(CP.T7, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100190  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 8 AS THANG, ISNULL(CP.T8, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100190  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 9 AS THANG, ISNULL(CP.T9, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100190  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 10 AS THANG, ISNULL(CP.T10, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100190  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 11 AS THANG, ISNULL(CP.T11, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100190  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 12 AS THANG, ISNULL(CP.T12, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100190  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
					
					")DATA \n " +
					"LEFT JOIN ( \n " +
					
					"" + this.query_ptDS + "\n " +
					
					")PTDS ON PTDS.THANG = DATA.THANG \n " +
					"GROUP BY DATA.THANG, PTDS.PTDS_LA \n " +

					"ORDER BY DATA.THANG \n ";


		data = prepareData(data, query);
		return data;
	}	
	

	public String[] PL_Upkeep_Motor_Vehicle_Office_PA_Budget(){
		String[] data = new String[12];

		String query = 	
					"SELECT DATA.THANG, SUM(NUM)*PTDS.PTDS_PA AS NUM FROM ( \n " +
						"	SELECT 1 AS THANG, ISNULL(CP.T1, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100190  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 2 AS THANG, ISNULL(CP.T2, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100190  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 3 AS THANG, ISNULL(CP.T3, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100190  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 4 AS THANG, ISNULL(CP.T4, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100190  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 5 AS THANG, ISNULL(CP.T5, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100190  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 6 AS THANG, ISNULL(CP.T6, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100190  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 7 AS THANG, ISNULL(CP.T7, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100190  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 8 AS THANG, ISNULL(CP.T8, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100190  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 9 AS THANG, ISNULL(CP.T9, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100190  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 10 AS THANG, ISNULL(CP.T10, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100190  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 11 AS THANG, ISNULL(CP.T11, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100190  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 12 AS THANG, ISNULL(CP.T12, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100190  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
					
					")DATA \n " +
					"LEFT JOIN ( \n " +
					
					"" + this.query_ptDS + "\n " +
					
					")PTDS ON PTDS.THANG = DATA.THANG \n " +
					"GROUP BY DATA.THANG, PTDS.PTDS_PA \n " +

					"ORDER BY DATA.THANG \n ";


		data = prepareData(data, query);
		return data;	}	
	

	public String[] PL_AuditingFees_Budget(){
		String[] data = new String[13];

		String query = 	"";
		return data;
	}	


	public String[] PL_AuditingFees_LA_Budget(){
		String[] data = new String[12];

		String query = 	
					"SELECT DATA.THANG, SUM(NUM)*PTDS.PTDS_LA AS NUM FROM ( \n " +
						"	SELECT 1 AS THANG, ISNULL(CP.T1, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100191  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 2 AS THANG, ISNULL(CP.T2, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100191  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 3 AS THANG, ISNULL(CP.T3, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100191  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 4 AS THANG, ISNULL(CP.T4, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100191  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 5 AS THANG, ISNULL(CP.T5, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100191  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 6 AS THANG, ISNULL(CP.T6, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100191  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 7 AS THANG, ISNULL(CP.T7, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100191  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 8 AS THANG, ISNULL(CP.T8, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100191  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 9 AS THANG, ISNULL(CP.T9, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100191  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 10 AS THANG, ISNULL(CP.T10, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100191  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 11 AS THANG, ISNULL(CP.T11, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100191  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 12 AS THANG, ISNULL(CP.T12, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100191  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
					
					")DATA \n " +
					"LEFT JOIN ( \n " +
					
					"" + this.query_ptDS + "\n " +
					
					")PTDS ON PTDS.THANG = DATA.THANG \n " +
					"GROUP BY DATA.THANG, PTDS.PTDS_LA \n " +

					"ORDER BY DATA.THANG \n ";


		data = prepareData(data, query);
		return data;	
	}	


	public String[] PL_AuditingFees_PA_Budget(){
		String[] data = new String[12];

		String query = 	
					"SELECT DATA.THANG, SUM(NUM)*PTDS.PTDS_PA AS NUM FROM ( \n " +
						"	SELECT 1 AS THANG, ISNULL(CP.T1, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100191  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 2 AS THANG, ISNULL(CP.T2, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100191  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 3 AS THANG, ISNULL(CP.T3, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100191  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 4 AS THANG, ISNULL(CP.T4, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100191  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 5 AS THANG, ISNULL(CP.T5, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100191  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 6 AS THANG, ISNULL(CP.T6, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100191  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 7 AS THANG, ISNULL(CP.T7, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100191  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 8 AS THANG, ISNULL(CP.T8, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100191  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 9 AS THANG, ISNULL(CP.T9, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100191  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 10 AS THANG, ISNULL(CP.T10, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100191  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 11 AS THANG, ISNULL(CP.T11, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100191  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 12 AS THANG, ISNULL(CP.T12, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100191  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
					
					")DATA \n " +
					"LEFT JOIN ( \n " +
					
					"" + this.query_ptDS + "\n " +
					
					")PTDS ON PTDS.THANG = DATA.THANG \n " +
					"GROUP BY DATA.THANG, PTDS.PTDS_PA \n " +

					"ORDER BY DATA.THANG \n ";


		data = prepareData(data, query);
		return data;	
	}	


	public String[] PL_Insurance_Office_Budget(){
		String[] data = new String[13];

		String query = 	
					"";


		data = prepareData(data, query);
		return data;
	}	


	public String[] PL_Insurance_Office_LA_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	
						"SELECT ISNULL(CP.T1, 0)*0.875 AS T1, ISNULL(CP.T2, 0)*0.875 AS T2, ISNULL(CP.T3, 0)*0.875 AS T3, ISNULL(CP.T4, 0)*0.875 AS T4, \n " +
						"ISNULL(CP.T5, 0)*0.875 AS T5, ISNULL(CP.T6, 0)*0.875 AS T6, ISNULL(CP.T7, 0)*0.875 AS T7, ISNULL(CP.T8, 0)*0.875 AS T8,  \n " +
						"ISNULL(CP.T9, 0)*0.875 AS T9, ISNULL(CP.T10, 0)*0.875 AS T10, ISNULL(CP.T11, 0)*0.875 AS T11, ISNULL(CP.T12, 0)*0.875 AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " +
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100192 \n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
				rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		return data;
	}	


	public String[] PL_Insurance_Office_PA_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	
						"SELECT ISNULL(CP.T1, 0)*0.5 AS T1, ISNULL(CP.T2, 0)*0.5 AS T2, ISNULL(CP.T3, 0)*0.5 AS T3, ISNULL(CP.T4, 0)*0.5 AS T4, \n " +
						"ISNULL(CP.T5, 0)*0.5 AS T5, ISNULL(CP.T6, 0)*0.5 AS T6, ISNULL(CP.T7, 0)*0.5 AS T7, ISNULL(CP.T8, 0)*0.5 AS T8,  \n " +
						"ISNULL(CP.T9, 0)*0.5 AS T9, ISNULL(CP.T10, 0)*0.5 AS T10, ISNULL(CP.T11, 0)*0.5 AS T11, ISNULL(CP.T12, 0)*0.5 AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " +
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100192 \n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
				rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		return data;
	}	


	public String[] PL_ProvisionExpenses_Budget(){
		String[] data = new String[13];

		String query = 	
					"";


		data = prepareData(data, query);
		return data;
	}	


	public String[] PL_ProvisionExpenses_LA_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	
						"SELECT ISNULL(CP.T1, 0) AS T1, ISNULL(CP.T2, 0) AS T2, ISNULL(CP.T3, 0) AS T3, ISNULL(CP.T4, 0) AS T4, \n " +
						"ISNULL(CP.T5, 0) AS T5, ISNULL(CP.T6, 0) AS T6, ISNULL(CP.T7, 0) AS T7, ISNULL(CP.T8, 0) AS T8,  \n " +
						"ISNULL(CP.T9, 0) AS T9, ISNULL(CP.T10, 0) AS T10, ISNULL(CP.T11, 0) AS T11, ISNULL(CP.T12, 0) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " +
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100268 \n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
				rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		return data;
		
	}	


	public String[] PL_ProvisionExpenses_PA_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	
						"SELECT ISNULL(CP.T1, 0) AS T1, ISNULL(CP.T2, 0) AS T2, ISNULL(CP.T3, 0) AS T3, ISNULL(CP.T4, 0) AS T4, \n " +
						"ISNULL(CP.T5, 0) AS T5, ISNULL(CP.T6, 0) AS T6, ISNULL(CP.T7, 0) AS T7, ISNULL(CP.T8, 0) AS T8,  \n " +
						"ISNULL(CP.T9, 0) AS T9, ISNULL(CP.T10, 0) AS T10, ISNULL(CP.T11, 0) AS T11, ISNULL(CP.T12, 0) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " +
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100269 \n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
				rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		return data;
	}	

	
	public String[] PL_LandRental_Office_Budget(){
		String[] data = new String[13];

		String query = 	
					"";

		return data;
	}	

	
	public String[] PL_LandRental_Office_LA_Budget(){
		String[] data = new String[12];

		String query = 	
					"SELECT DATA.THANG, SUM(NUM)*PTDS.PTDS_LA AS NUM FROM ( \n " +
						"	SELECT 1 AS THANG, ISNULL(CP.T1, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100193  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 2 AS THANG, ISNULL(CP.T2, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100193  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 3 AS THANG, ISNULL(CP.T3, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100193  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 4 AS THANG, ISNULL(CP.T4, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100193  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 5 AS THANG, ISNULL(CP.T5, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100193  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 6 AS THANG, ISNULL(CP.T6, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100193  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 7 AS THANG, ISNULL(CP.T7, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100193  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 8 AS THANG, ISNULL(CP.T8, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100193  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 9 AS THANG, ISNULL(CP.T9, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100193  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 10 AS THANG, ISNULL(CP.T10, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100193  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 11 AS THANG, ISNULL(CP.T11, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100193  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 12 AS THANG, ISNULL(CP.T12, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100193  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
					
					")DATA \n " +
					"LEFT JOIN ( \n " +
					
					"" + this.query_ptDS + "\n " +
					
					")PTDS ON PTDS.THANG = DATA.THANG \n " +
					"GROUP BY DATA.THANG, PTDS.PTDS_LA \n " +

					"ORDER BY DATA.THANG \n ";


		data = prepareData(data, query);
		return data;	
	}	

	
	public String[] PL_LandRental_Office_PA_Budget(){
		String[] data = new String[12];

		String query = 	
					"SELECT DATA.THANG, SUM(NUM)*PTDS.PTDS_PA AS NUM FROM ( \n " +
						"	SELECT 1 AS THANG, ISNULL(CP.T1, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100193  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 2 AS THANG, ISNULL(CP.T2, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100193  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 3 AS THANG, ISNULL(CP.T3, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100193  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 4 AS THANG, ISNULL(CP.T4, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100193  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 5 AS THANG, ISNULL(CP.T5, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100193  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 6 AS THANG, ISNULL(CP.T6, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100193  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 7 AS THANG, ISNULL(CP.T7, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100193  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 8 AS THANG, ISNULL(CP.T8, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100193  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 9 AS THANG, ISNULL(CP.T9, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100193  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 10 AS THANG, ISNULL(CP.T10, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100193  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 11 AS THANG, ISNULL(CP.T11, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100193  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 12 AS THANG, ISNULL(CP.T12, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100193  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
					
					")DATA \n " +
					"LEFT JOIN ( \n " +
					
					"" + this.query_ptDS + "\n " +
					
					")PTDS ON PTDS.THANG = DATA.THANG \n " +
					"GROUP BY DATA.THANG, PTDS.PTDS_PA \n " +

					"ORDER BY DATA.THANG \n ";


		data = prepareData(data, query);
		return data;	
	}	

	public String[] PL_RD_Expenses_Budget(){
		String[] data = new String[13];

		String query = 	
					"";


		data = prepareData(data, query);
		return data;
	}	

	// CHƯA CÓ TRUNG TÂM CHI PHÍ
	public String[] PL_RD_Expenses_LA_Budget(){
		String[] data = new String[12];

		String query = 	
					"SELECT DATA.THANG, SUM(NUM)*PTDS.PTDS_LA AS NUM FROM ( \n " +
						"	SELECT 1 AS THANG, ISNULL(CP.T1, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100194  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 2 AS THANG, ISNULL(CP.T2, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100194  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 3 AS THANG, ISNULL(CP.T3, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100194  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 4 AS THANG, ISNULL(CP.T4, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100194  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 5 AS THANG, ISNULL(CP.T5, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100194  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 6 AS THANG, ISNULL(CP.T6, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100194  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 7 AS THANG, ISNULL(CP.T7, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100194  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 8 AS THANG, ISNULL(CP.T8, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100194  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 9 AS THANG, ISNULL(CP.T9, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100194  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 10 AS THANG, ISNULL(CP.T10, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100194  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 11 AS THANG, ISNULL(CP.T11, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100194  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 12 AS THANG, ISNULL(CP.T12, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100194  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
					
					")DATA \n " +
					"LEFT JOIN ( \n " +
					
					"" + this.query_ptDS + "\n " +
					
					")PTDS ON PTDS.THANG = DATA.THANG \n " +
					"GROUP BY DATA.THANG, PTDS.PTDS_LA \n " +

					"ORDER BY DATA.THANG \n ";


		data = prepareData(data, query);
		return data;	
	}	

// CHƯA CÓ TRUNG TÂM CHI PHÍ
	public String[] PL_RD_Expenses_PA_Budget(){
		String[] data = new String[12];

		String query = 	
					"SELECT DATA.THANG, SUM(NUM)*PTDS.PTDS_PA AS NUM FROM ( \n " +
						"	SELECT 1 AS THANG, ISNULL(CP.T1, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100194  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 2 AS THANG, ISNULL(CP.T2, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100194  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 3 AS THANG, ISNULL(CP.T3, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100194  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 4 AS THANG, ISNULL(CP.T4, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100194  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 5 AS THANG, ISNULL(CP.T5, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100194  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 6 AS THANG, ISNULL(CP.T6, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100194  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 7 AS THANG, ISNULL(CP.T7, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100194  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 8 AS THANG, ISNULL(CP.T8, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100194  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 9 AS THANG, ISNULL(CP.T9, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100194  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 10 AS THANG, ISNULL(CP.T10, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100194  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 11 AS THANG, ISNULL(CP.T11, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100194  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 12 AS THANG, ISNULL(CP.T12, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100194  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
					
					")DATA \n " +
					"LEFT JOIN ( \n " +
					
					"" + this.query_ptDS + "\n " +
					
					")PTDS ON PTDS.THANG = DATA.THANG \n " +
					"GROUP BY DATA.THANG, PTDS.PTDS_PA \n " +

					"ORDER BY DATA.THANG \n ";


		data = prepareData(data, query);
		return data;	
	}	

	public String[] PL_ManagementFees_Budget(){
		String[] data = new String[13];

		String query = 	
					"";


		data = prepareData(data, query);
		return data;
	}	


	public String[] PL_ManagementFees_LA_Budget(){
		String[] data = new String[12];

		String query = 	
					"SELECT DATA.THANG, SUM(NUM)*0.9 AS NUM FROM ( \n " +
						"	SELECT 1 AS THANG, ISNULL(CP.T1, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100195  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 2 AS THANG, ISNULL(CP.T2, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100195  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 3 AS THANG, ISNULL(CP.T3, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100195  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 4 AS THANG, ISNULL(CP.T4, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100195  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 5 AS THANG, ISNULL(CP.T5, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100195  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 6 AS THANG, ISNULL(CP.T6, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100195  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 7 AS THANG, ISNULL(CP.T7, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100195  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 8 AS THANG, ISNULL(CP.T8, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100195  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 9 AS THANG, ISNULL(CP.T9, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100195  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 10 AS THANG, ISNULL(CP.T10, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100195  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 11 AS THANG, ISNULL(CP.T11, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100195  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 12 AS THANG, ISNULL(CP.T12, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100195  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
					
					")DATA \n " +
					"GROUP BY DATA.THANG \n " +

					"ORDER BY DATA.THANG \n ";


		data = prepareData(data, query);
		return data;
	}	


	public String[] PL_ManagementFees_PA_Budget(){
		String[] data = new String[12];

		String query = 	
					"SELECT DATA.THANG, SUM(NUM)*0.1 AS NUM FROM ( \n " +
						"	SELECT 1 AS THANG, ISNULL(CP.T1, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100195  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 2 AS THANG, ISNULL(CP.T2, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100195  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 3 AS THANG, ISNULL(CP.T3, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100195  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 4 AS THANG, ISNULL(CP.T4, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100195  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 5 AS THANG, ISNULL(CP.T5, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100195  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 6 AS THANG, ISNULL(CP.T6, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100195  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 7 AS THANG, ISNULL(CP.T7, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100195  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 8 AS THANG, ISNULL(CP.T8, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100195  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 9 AS THANG, ISNULL(CP.T9, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100195  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 10 AS THANG, ISNULL(CP.T10, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100195  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 11 AS THANG, ISNULL(CP.T11, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100195  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 12 AS THANG, ISNULL(CP.T12, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100195  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
					
					")DATA \n " +
					"GROUP BY DATA.THANG \n " +

					"ORDER BY DATA.THANG \n ";


		data = prepareData(data, query);
		return data;	
	}	


	public String[] PL_AdminSalary_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}


		String query = "";
		return data;
	}	

	public String[] PL_Staff_Salary_Budget(){
		String[] data = new String[13];

		String query = 	
					"SELECT THANG, SUM(NUM) AS NUM FROM ( \n " +
						"SELECT SUBSTRING(NH.NGAYNHAN, 6, 2) AS THANG, SUM(NH_SP.DONGIAVIET*NH_SP.SOLUONGNHAN) AS NUM  \n " +
						"FROM ERP_NHANHANG NH  \n " +
						"INNER JOIN ERP_NHANHANG_SANPHAM NH_SP ON NH_SP.NHANHANG_FK = NH.PK_SEQ  \n " +
						"WHERE NH.TRANGTHAI IN (1, 2) AND NH_SP.CHIPHI_FK IN (100203) \n " +
						"AND SUBSTRING(NH.NGAYNHAN, 1, 4) = '" + this.year + "' \n " +
						"GROUP BY SUBSTRING(NH.NGAYNHAN, 6, 2)  \n " +

						"UNION ALL  \n " +
						"SELECT SUBSTRING(MH.NGAYMUA, 6, 2) AS THANG, SUM(MH_SP.DONGIAVIET*MH_SP.SOLUONG) AS NUM \n " +
						"FROM ERP_MUAHANG MH \n " +
						"INNER JOIN ERP_MUAHANG_SP MH_SP ON MH_SP.MUAHANG_FK = MH.PK_SEQ \n " +
		 

						"WHERE YEAR(MH.NGAYMUA) = '" + this.year + "' AND MH.TRANGTHAI IN (1, 2) \n " +
						"AND ISNULL(CHIPHI_FK, 0) IN (100203)  \n " +
						"GROUP BY MH.NGAYMUA, ISNULL(CHIPHI_FK, 0), MH.PK_SEQ \n " +
					")DATA \n " +
					"GROUP BY DATA.THANG \n " +
					"ORDER BY DATA.THANG \n ";


		data = prepareData(data, query);
		return data;
	}	


	public String[] PL_Staff_Salary_LA_Budget(){
		String[] data = new String[12];

		String query = 	
					"SELECT DATA.THANG, SUM(NUM)*0.918 AS NUM FROM ( \n " +
						"	SELECT 1 AS THANG, ISNULL(CP.T1, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100203  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 2 AS THANG, ISNULL(CP.T2, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100203  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 3 AS THANG, ISNULL(CP.T3, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100203  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 4 AS THANG, ISNULL(CP.T4, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100203  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 5 AS THANG, ISNULL(CP.T5, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100203  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 6 AS THANG, ISNULL(CP.T6, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100203  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 7 AS THANG, ISNULL(CP.T7, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100203  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 8 AS THANG, ISNULL(CP.T8, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100203  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 9 AS THANG, ISNULL(CP.T9, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100203  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 10 AS THANG, ISNULL(CP.T10, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100203  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 11 AS THANG, ISNULL(CP.T11, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100203  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 12 AS THANG, ISNULL(CP.T12, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100203  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
					
					")DATA \n " +
					"GROUP BY DATA.THANG \n " +

					"ORDER BY DATA.THANG \n ";


		data = prepareData(data, query);
		return data;
		
	}	


	public String[] PL_Staff_Salary_PA_Budget(){
		String[] data = new String[12];

		String query = 	
					"SELECT DATA.THANG, SUM(NUM)*0.082 AS NUM FROM ( \n " +
						"	SELECT 1 AS THANG, ISNULL(CP.T1, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100203  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 2 AS THANG, ISNULL(CP.T2, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100203  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 3 AS THANG, ISNULL(CP.T3, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100203  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 4 AS THANG, ISNULL(CP.T4, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100203  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 5 AS THANG, ISNULL(CP.T5, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100203  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 6 AS THANG, ISNULL(CP.T6, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100203  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 7 AS THANG, ISNULL(CP.T7, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100203  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 8 AS THANG, ISNULL(CP.T8, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100203  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 9 AS THANG, ISNULL(CP.T9, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100203  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 10 AS THANG, ISNULL(CP.T10, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100203  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 11 AS THANG, ISNULL(CP.T11, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100203  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 12 AS THANG, ISNULL(CP.T12, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100203  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
					
					")DATA \n " +
					"GROUP BY DATA.THANG \n " +

					"ORDER BY DATA.THANG \n ";


		data = prepareData(data, query);
		return data;
	}	


	public String[] PL_StaffOvertime_Budget(){
		String[] data = new String[13];

		String query = 	
					"SELECT THANG, SUM(NUM) AS NUM FROM ( \n " +
						"SELECT SUBSTRING(NH.NGAYNHAN, 6, 2) AS THANG, SUM(NH_SP.DONGIAVIET*NH_SP.SOLUONGNHAN) AS NUM  \n " +
						"FROM ERP_NHANHANG NH  \n " +
						"INNER JOIN ERP_NHANHANG_SANPHAM NH_SP ON NH_SP.NHANHANG_FK = NH.PK_SEQ  \n " +
						"WHERE NH.TRANGTHAI IN (1, 2) AND NH_SP.CHIPHI_FK IN (100197) \n " +
						"AND SUBSTRING(NH.NGAYNHAN, 1, 4) = '" + this.year + "' \n " +
						"GROUP BY SUBSTRING(NH.NGAYNHAN, 6, 2)  \n " +

						"UNION ALL  \n " +
						"SELECT SUBSTRING(MH.NGAYMUA, 6, 2) AS THANG, SUM(MH_SP.DONGIAVIET*MH_SP.SOLUONG) AS NUM \n " +
						"FROM ERP_MUAHANG MH \n " +
						"INNER JOIN ERP_MUAHANG_SP MH_SP ON MH_SP.MUAHANG_FK = MH.PK_SEQ \n " +
		 

						"WHERE YEAR(MH.NGAYMUA) = '" + this.year + "' AND MH.TRANGTHAI IN (1, 2) \n " +
						"AND ISNULL(CHIPHI_FK, 0) IN (100197)  \n " +
						"GROUP BY MH.NGAYMUA, ISNULL(CHIPHI_FK, 0), MH.PK_SEQ \n " +
					")DATA \n " +
					"GROUP BY DATA.THANG \n " +
					"ORDER BY DATA.THANG \n ";


		data = prepareData(data, query);
		return data;
	}	


	public String[] PL_StaffOvertime_LA_Budget(){
		String[] data = new String[12];

		String query = 	
					"SELECT DATA.THANG, SUM(NUM)*0.918 AS NUM FROM ( \n " +
						"	SELECT 1 AS THANG, ISNULL(CP.T1, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100197  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 2 AS THANG, ISNULL(CP.T2, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100197  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 3 AS THANG, ISNULL(CP.T3, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100197  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 4 AS THANG, ISNULL(CP.T4, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100197  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 5 AS THANG, ISNULL(CP.T5, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100197  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 6 AS THANG, ISNULL(CP.T6, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100197  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 7 AS THANG, ISNULL(CP.T7, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100197  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 8 AS THANG, ISNULL(CP.T8, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100197  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 9 AS THANG, ISNULL(CP.T9, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100197  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 10 AS THANG, ISNULL(CP.T10, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100197  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 11 AS THANG, ISNULL(CP.T11, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100197  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 12 AS THANG, ISNULL(CP.T12, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100197  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
					
					")DATA \n " +
					"GROUP BY DATA.THANG \n " +

					"ORDER BY DATA.THANG \n ";


		data = prepareData(data, query);
		return data;

	}	


	public String[] PL_StaffOvertime_PA_Budget(){
		String[] data = new String[12];

		String query = 	
					"SELECT DATA.THANG, SUM(NUM)*0.082 AS NUM FROM ( \n " +
						"	SELECT 1 AS THANG, ISNULL(CP.T1, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100197  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 2 AS THANG, ISNULL(CP.T2, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100197  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 3 AS THANG, ISNULL(CP.T3, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100197  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 4 AS THANG, ISNULL(CP.T4, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100197  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 5 AS THANG, ISNULL(CP.T5, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100197  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 6 AS THANG, ISNULL(CP.T6, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100197  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 7 AS THANG, ISNULL(CP.T7, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100197  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 8 AS THANG, ISNULL(CP.T8, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100197  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 9 AS THANG, ISNULL(CP.T9, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100197  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 10 AS THANG, ISNULL(CP.T10, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100197  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 11 AS THANG, ISNULL(CP.T11, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100197  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 12 AS THANG, ISNULL(CP.T12, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100197  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
					
					")DATA \n " +
					"GROUP BY DATA.THANG \n " +

					"ORDER BY DATA.THANG \n ";


		data = prepareData(data, query);
		return data;
	}	


	public String[] PL_Staff_Amortized_13th_Salary_Budget(){
		String[] data = new String[13];

		String query = 	
					"SELECT THANG, SUM(NUM) AS NUM FROM ( \n " +
						"SELECT SUBSTRING(NH.NGAYNHAN, 6, 2) AS THANG, SUM(NH_SP.DONGIAVIET*NH_SP.SOLUONGNHAN) AS NUM  \n " +
						"FROM ERP_NHANHANG NH  \n " +
						"INNER JOIN ERP_NHANHANG_SANPHAM NH_SP ON NH_SP.NHANHANG_FK = NH.PK_SEQ  \n " +
						"WHERE NH.TRANGTHAI IN (1, 2) AND NH_SP.CHIPHI_FK IN (100198) \n " +
						"AND SUBSTRING(NH.NGAYNHAN, 1, 4) = '" + this.year + "' \n " +
						"GROUP BY SUBSTRING(NH.NGAYNHAN, 6, 2)  \n " +

						"UNION ALL  \n " +
						"SELECT SUBSTRING(MH.NGAYMUA, 6, 2) AS THANG, SUM(MH_SP.DONGIAVIET*MH_SP.SOLUONG) AS NUM \n " +
						"FROM ERP_MUAHANG MH \n " +
						"INNER JOIN ERP_MUAHANG_SP MH_SP ON MH_SP.MUAHANG_FK = MH.PK_SEQ \n " +
		 

						"WHERE YEAR(MH.NGAYMUA) = '" + this.year + "' AND MH.TRANGTHAI IN (1, 2) \n " +
						"AND ISNULL(CHIPHI_FK, 0) IN (100198)  \n " +
						"GROUP BY MH.NGAYMUA, ISNULL(CHIPHI_FK, 0), MH.PK_SEQ \n " +
					")DATA \n " +
					"GROUP BY DATA.THANG \n " +
					"ORDER BY DATA.THANG \n ";


		data = prepareData(data, query);
		return data;
	}	


	public String[] PL_Staff_Amortized_13th_Salary_LA_Budget(){
		String[] data = new String[12];

		String query = 	
					"SELECT DATA.THANG, SUM(NUM)*0.918 AS NUM FROM ( \n " +
						"	SELECT 1 AS THANG, ISNULL(CP.T1, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100198  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 2 AS THANG, ISNULL(CP.T2, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100198  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 3 AS THANG, ISNULL(CP.T3, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100198  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 4 AS THANG, ISNULL(CP.T4, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100198  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 5 AS THANG, ISNULL(CP.T5, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100198  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 6 AS THANG, ISNULL(CP.T6, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100198  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 7 AS THANG, ISNULL(CP.T7, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100198  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 8 AS THANG, ISNULL(CP.T8, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100198  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 9 AS THANG, ISNULL(CP.T9, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100198  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 10 AS THANG, ISNULL(CP.T10, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100198  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 11 AS THANG, ISNULL(CP.T11, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100198  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 12 AS THANG, ISNULL(CP.T12, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100198  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
					
					")DATA \n " +
					"GROUP BY DATA.THANG \n " +

					"ORDER BY DATA.THANG \n ";


		data = prepareData(data, query);
		return data;
	}	


	public String[] PL_Staff_Amortized_13th_Salary_PA_Budget(){
		String[] data = new String[12];

		String query = 	
					"SELECT DATA.THANG, SUM(NUM)*0.082 AS NUM FROM ( \n " +
						"	SELECT 1 AS THANG, ISNULL(CP.T1, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100198  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 2 AS THANG, ISNULL(CP.T2, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100198  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 3 AS THANG, ISNULL(CP.T3, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100198  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 4 AS THANG, ISNULL(CP.T4, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100198  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 5 AS THANG, ISNULL(CP.T5, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100198  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 6 AS THANG, ISNULL(CP.T6, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100198  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 7 AS THANG, ISNULL(CP.T7, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100198  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 8 AS THANG, ISNULL(CP.T8, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100198  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 9 AS THANG, ISNULL(CP.T9, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100198  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 10 AS THANG, ISNULL(CP.T10, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100198  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 11 AS THANG, ISNULL(CP.T11, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100198  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 12 AS THANG, ISNULL(CP.T12, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100198  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
					
					")DATA \n " +
					"GROUP BY DATA.THANG \n " +

					"ORDER BY DATA.THANG \n ";


		data = prepareData(data, query);
		return data;
	}	


	public String[] PL_Staff_SocialInsurance_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}


		String query = 	
					"";


		data = prepareData(data, query);
		return data;
	}	


	public String[] PL_Staff_SocialInsurance_LA_Budget(){
		String[] data = new String[12];

		String query = 	
					"SELECT DATA.THANG, SUM(NUM)*0.918 AS NUM FROM ( \n " +
						"	SELECT 1 AS THANG, ISNULL(CP.T1, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100199  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 2 AS THANG, ISNULL(CP.T2, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100199  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 3 AS THANG, ISNULL(CP.T3, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100199  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 4 AS THANG, ISNULL(CP.T4, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100199  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 5 AS THANG, ISNULL(CP.T5, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100199  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 6 AS THANG, ISNULL(CP.T6, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100199  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 7 AS THANG, ISNULL(CP.T7, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100199  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 8 AS THANG, ISNULL(CP.T8, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100199  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 9 AS THANG, ISNULL(CP.T9, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100199  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 10 AS THANG, ISNULL(CP.T10, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100199  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 11 AS THANG, ISNULL(CP.T11, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100199  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 12 AS THANG, ISNULL(CP.T12, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100199  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
					
					")DATA \n " +
					"GROUP BY DATA.THANG \n " +

					"ORDER BY DATA.THANG \n ";


		data = prepareData(data, query);
		return data;
	}	


	public String[] PL_Staff_SocialInsurance_PA_Budget(){
		String[] data = new String[12];

		String query = 	
					"SELECT DATA.THANG, SUM(NUM)*0.082 AS NUM FROM ( \n " +
						"	SELECT 1 AS THANG, ISNULL(CP.T1, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100199  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 2 AS THANG, ISNULL(CP.T2, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100199  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 3 AS THANG, ISNULL(CP.T3, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100199  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 4 AS THANG, ISNULL(CP.T4, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100199  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 5 AS THANG, ISNULL(CP.T5, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100199  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 6 AS THANG, ISNULL(CP.T6, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100199  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 7 AS THANG, ISNULL(CP.T7, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100199  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 8 AS THANG, ISNULL(CP.T8, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100199  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 9 AS THANG, ISNULL(CP.T9, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100199  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 10 AS THANG, ISNULL(CP.T10, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100199  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 11 AS THANG, ISNULL(CP.T11, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100199  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 12 AS THANG, ISNULL(CP.T12, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100199  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
					
					")DATA \n " +
					"GROUP BY DATA.THANG \n " +

					"ORDER BY DATA.THANG \n ";


		data = prepareData(data, query);
		return data;
	}	

	public String[] PL_Staff_HealthInsurance_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}


		String query = 	
					"";
		return data;
	}	


	public String[] PL_Staff_HealthInsurance_LA_Budget(){
		String[] data = new String[12];

		String query = 	
					"SELECT DATA.THANG, SUM(NUM)*0.918 AS NUM FROM ( \n " +
						"	SELECT 1 AS THANG, ISNULL(CP.T1, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100200  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 2 AS THANG, ISNULL(CP.T2, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100200  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 3 AS THANG, ISNULL(CP.T3, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100200  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 4 AS THANG, ISNULL(CP.T4, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100200  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 5 AS THANG, ISNULL(CP.T5, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100200  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 6 AS THANG, ISNULL(CP.T6, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100200  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 7 AS THANG, ISNULL(CP.T7, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100200  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 8 AS THANG, ISNULL(CP.T8, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100200  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 9 AS THANG, ISNULL(CP.T9, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100200  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 10 AS THANG, ISNULL(CP.T10, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100200  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 11 AS THANG, ISNULL(CP.T11, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100200  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 12 AS THANG, ISNULL(CP.T12, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100200  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
					
					")DATA \n " +
					"GROUP BY DATA.THANG \n " +

					"ORDER BY DATA.THANG \n ";


		data = prepareData(data, query);
		return data;
	}	


	public String[] PL_Staff_HealthInsurance_PA_Budget(){
		String[] data = new String[12];

		String query = 	
					"SELECT DATA.THANG, SUM(NUM)*0.082 AS NUM FROM ( \n " +
						"	SELECT 1 AS THANG, ISNULL(CP.T1, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100200  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 2 AS THANG, ISNULL(CP.T2, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100200  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 3 AS THANG, ISNULL(CP.T3, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100200  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 4 AS THANG, ISNULL(CP.T4, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100200  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 5 AS THANG, ISNULL(CP.T5, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100200  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 6 AS THANG, ISNULL(CP.T6, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100200  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 7 AS THANG, ISNULL(CP.T7, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100200  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 8 AS THANG, ISNULL(CP.T8, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100200  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 9 AS THANG, ISNULL(CP.T9, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100200  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 10 AS THANG, ISNULL(CP.T10, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100200  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 11 AS THANG, ISNULL(CP.T11, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100200  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
						
						"	UNION ALL \n " +
						"	SELECT 12 AS THANG, ISNULL(CP.T12, 0) AS NUM \n " +
						"	FROM ERP_LAPNGANSACH_CHIPHI CP  \n " +
						"	INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " + 
						"	WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100200  \n " +
						"	AND CP.LAPNGANSACH_FK = " + this.Id + " \n " +
					
					")DATA \n " +
					"GROUP BY DATA.THANG \n " +

					"ORDER BY DATA.THANG \n ";


		data = prepareData(data, query);
		return data;
	}	


	public String[] PL_Financial_Activity_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	
					"";

		return data;
	}	

	public String[] PL_Interest_Income_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	
					"";
		return data;
	}	


	public String[] PL_Interest_Income_LA_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	
					"";
		return data;
	}	


	public String[] PL_Interest_Income_PA_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	
					"";
		return data;
	}	


	public String[] PL_Gain_Exchange_Reserve_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	
					"";
		return data;
	}	


	public String[] PL_Gain_Exchange_Reserve_LA_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	
					"";
		return data;
	}	


	public String[] PL_Gain_Exchange_Reserve_PA_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}


		String query = 	
					" \n ";
		return data;
	}	


	public String[] PL_LoanInterest_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	
					"";


		data = prepareData(data, query);
		return data;
	}	


	public String[] PL_LoanInterest_LA_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	
						"SELECT ISNULL(CP.T1, 0) AS T1, ISNULL(CP.T2, 0) AS T2, ISNULL(CP.T3, 0) AS T3, ISNULL(CP.T4, 0) AS T4, \n " +
						"ISNULL(CP.T5, 0) AS T5, ISNULL(CP.T6, 0) AS T6, ISNULL(CP.T7, 0) AS T7, ISNULL(CP.T8, 0) AS T8,  \n " +
						"ISNULL(CP.T9, 0) AS T9, ISNULL(CP.T10, 0) AS T10, ISNULL(CP.T11, 0) AS T11, ISNULL(CP.T12, 0) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " +
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100110 \n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
				rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		return data;
		
	}	


	public String[] PL_LoanInterest_PA_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	
						"SELECT ISNULL(CP.T1, 0) AS T1, ISNULL(CP.T2, 0) AS T2, ISNULL(CP.T3, 0) AS T3, ISNULL(CP.T4, 0) AS T4, \n " +
						"ISNULL(CP.T5, 0) AS T5, ISNULL(CP.T6, 0) AS T6, ISNULL(CP.T7, 0) AS T7, ISNULL(CP.T8, 0) AS T8,  \n " +
						"ISNULL(CP.T9, 0) AS T9, ISNULL(CP.T10, 0) AS T10, ISNULL(CP.T11, 0) AS T11, ISNULL(CP.T12, 0) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " +
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100111 \n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
				rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		return data;
		
	}	


	public String[] PL_Loss_Exchange_Reserve_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	
					"";
		return data;
	}	


	public String[] PL_Loss_Exchange_Reserve_LA_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	
						"SELECT ISNULL(CP.T1, 0) AS T1, ISNULL(CP.T2, 0) AS T2, ISNULL(CP.T3, 0) AS T3, ISNULL(CP.T4, 0) AS T4, \n " +
						"ISNULL(CP.T5, 0) AS T5, ISNULL(CP.T6, 0) AS T6, ISNULL(CP.T7, 0) AS T7, ISNULL(CP.T8, 0) AS T8,  \n " +
						"ISNULL(CP.T9, 0) AS T9, ISNULL(CP.T10, 0) AS T10, ISNULL(CP.T11, 0) AS T11, ISNULL(CP.T12, 0) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " +
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100112 \n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
				rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		return data;
		
	}	


	public String[] PL_Loss_Exchange_Reserve_PA_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	
						"SELECT ISNULL(CP.T1, 0) AS T1, ISNULL(CP.T2, 0) AS T2, ISNULL(CP.T3, 0) AS T3, ISNULL(CP.T4, 0) AS T4, \n " +
						"ISNULL(CP.T5, 0) AS T5, ISNULL(CP.T6, 0) AS T6, ISNULL(CP.T7, 0) AS T7, ISNULL(CP.T8, 0) AS T8,  \n " +
						"ISNULL(CP.T9, 0) AS T9, ISNULL(CP.T10, 0) AS T10, ISNULL(CP.T11, 0) AS T11, ISNULL(CP.T12, 0) AS T12 \n " +
						"FROM ERP_LAPNGANSACH_CHIPHI CP \n " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = CP.LAPNGANSACH_FK \n " +
						"WHERE LNS.CONGTY_FK = " + this.ctyId + " AND CP.CHIPHI_FK = 100113 \n " +
						"AND LNS.NAM = " + this.year + " AND CP.LAPNGANSACH_FK = " + this.Id + " \n " ;
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				if(rs.next()){
					for(int i = 0; i < 12; i++){
						data[i] = rs.getString("T" + (i + 1)) == null?"0":rs.getString("T" + (i + 1));
					}
				rs.close();
				}
			}catch(java.sql.SQLException e){}
		}
		return data;
		
	}	
	
	public String[] PL_OtherActivity_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}
		String query = 	
					"";
		return data;
	}	

	
	public String[] PL_OtherActivity_LA_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	
					"";
		return data;
	}	

	
	public String[] PL_OtherActivity_PA_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	
					"";

		return data;
	}	


	public String[] PL_Other_Income_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	
					" " ;
		return data;
	}	


	public String[] PL_Other_Income_LA_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	
					"";
		return data;
	}	

	public String[] PL_Other_Income_PA_Budget(){
		String[] data = new String[12];
		for(int i = 0; i < 12; i++){
			data[i] = "0";
		}

		String query = 	
					"";
		return data;
	}	

	public String[] PL_SalesVolumn_LA_Budget(){
		String[] data = new String[13];
		String tungay = this.year + "-01-01";
		String denngay = this.year + "-" + (this.month.length() > 1?this.month:"0" + this.month) + "-31";
		
		String query = 	
			"SELECT THANG, SUM(TRONGLUONG) AS NUM \n " +
			"FROM  \n " +
			"( \n " +
			"	SELECT SUBSTRING(NGAYXUAT, 6, 2) AS THANG,  \n " +
			"	SOLUONG*TRONGLUONG_QC AS TRONGLUONG \n " +
			"	FROM \n " +
			"	(  \n " +
			" 		SELECT  type,  \n " +
			" 		CASE WHEN A.LSP IN (100013,100014,100015,100016,100017) THEN 'NL'  \n " +
			" 		ELSE   \n " +
			" 			 CASE WHEN A.MA = 100000 THEN 'N'   \n " +
			" 		ELSE CASE WHEN A.MA = 100004 THEN 'L'   \n " +
			" 		ELSE CASE WHEN A.MA = 100005 THEN 'SPM'   \n " +
			" 		ELSE CASE WHEN A.MA = 100007 THEN 'K'  \n " +
			" 		ELSE CASE WHEN A.MA = 100008 THEN 'GC'  \n " +
			" 		ELSE 'K'   \n " +
			" 		END END END END END  \n " +
			" 		END AS DVKD,    \n " +
			 	
			" 		CASE WHEN A.MA1 = 100000 THEN N'Nhôm'   \n " +
			" 		  WHEN A.MA1 = 100004 THEN N'Lõi'   \n " +
			" 		  WHEN A.MA1= 100005 THEN N'SPM'   \n " +
			" 		ELSE N'Khác'   \n " +
			" 		END   as donvikinhdoanh,  \n " +
			" 	A.LSP,    \n " +
			" 	A.NGAYXUAT, A.SPTEN,    \n " +
			" 	A.SOLUONG,   \n " +
			"	A.THANHTIEN, A.,    \n " +
			"    A.MKT_SP,    \n " +
			"	A.DVDL, A.TRONGLUONG_QC \n " +
			" 	FROM    \n " +
			" 	(  \n " +

			" 		SELECT 'HDTC' AS TYPE,  \n " +
			" 		( SELECT TOP  1  CASE WHEN  DH.LOAIDONHANG ='2' THEN 100008 ELSE SP.DVKD_FK END    \n " +
			" 		FROM ERP_DONDATHANG DH  INNER JOIN ERP_XUATKHO XK ON XK.DONDATHANG_FK= DH.PK_SEQ   			 \n " +	
			" 		INNER JOIN ERP_HOADON_XUATKHO HDXK ON HDXK.XUATKHO_FK=XK.PK_SEQ WHERE HDXK.hoadon_fk= HD.PK_SEQ  \n " +
			" 		)AS MA,  \n " +
			 		
			" 		SP.DVKD_FK AS MA1 , LSP.PK_SEQ AS LSP,    \n " +
			" 		HD.NGAYXUATHD AS NGAYXUAT,   \n " +
			 		
			" 		SP.TEN AS SPTEN,    \n " +
			 		
			"		HD_SP.DONVITINH AS DVT,   \n " +
			" 		HD_SP.SOLUONG AS SOLUONG,  \n " +
			" 		ISNULL(ISNULL(HD_SP.DONGIAVIET,HD_SP.DONGIA)* HD_SP.SOLUONG,'0') AS THANHTIEN,  \n " +
			 
			" 		SP.MA AS , ISNULL(MKT.MA,'') AS MKT_SP,  \n " +
			"		HD.LOAIHD,   \n " +
			"		isnull(HD_SP.DVDL_FK,0) AS DVDL, \n " +
			"		CASE  WHEN HD_SP.DVDL_FK =100003 THEN 1    \n " +
			" 		WHEN QC2.DVDL1_FK IS NOT NULL THEN   ISNULL( CAST(QC2.SOLUONG2 AS FLOAT ), 0)  / ISNULL( CAST(QC2.SOLUONG1 AS FLOAT) , 1)   \n " +
			"		WHEN   QC3.DVDL1_FK IS NOT NULL THEN   ISNULL(CAST(QC3.SOLUONG1 AS FLOAT) , 0)  / ISNULL( CAST(QC3.SOLUONG2 AS FLOAT ), 1)  \n " +
			" 		WHEN QC4.DVDL2_FK IS NOT NULL THEN (   ISNULL( CAST(QC4.SOLUONG2 AS FLOAT) , 0)  / ISNULL(CAST(QC4.SOLUONG1 AS FLOAT), 1) ) *  CAST(D.SOLUONG1 AS FLOAT)/ CAST(D.SOLUONG2 AS FLOAT)  \n " + 
			" 		ELSE 0  END AS TRONGLUONG_QC  \n " +
			 		
			" 		FROM ERP_HOADON HD   \n " +
			" 		INNER JOIN ERP_HOADON_SP HD_SP ON HD.PK_SEQ=HD_SP.HOADON_FK    \n " +
			" 		INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ= HD_SP.    \n " +
			" 		LEFT JOIN ERP_MAKETOAN MKT ON SP.MAKETOAN_FK= MKT.PK_SEQ    \n " +
			" 		LEFT JOIN ERP_LOAISANPHAM LSP ON SP.LOAI= LSP.PK_SEQ    \n " +
			"  		LEFT JOIN QUYCACH D ON HD_SP. = d.  AND HD_SP.DVDL_FK=D.DVDL2_FK   		 \n " +
			"  		LEFT JOIN QUYCACH QC2 ON QC2.=SP.PK_SEQ AND QC2.DVDL2_FK=100003  AND QC2.DVDL1_FK=HD_SP.DVDL_FK  		 \n " +
			"  		LEFT JOIN QUYCACH QC3 ON QC3.=SP.PK_SEQ AND QC3.DVDL1_FK=100003  AND QC3.DVDL2_FK=HD_SP.DVDL_FK  		 \n " +
			"  		LEFT JOIN QUYCACH QC4 ON QC4.=SP.PK_SEQ AND QC4.DVDL2_FK= 100003 		 \n " +
			"  		LEFT JOIN DONVIDOLUONG C ON HD_SP.DVDL_FK = C.PK_SEQ    \n " +
			" 		INNER JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ = SP.DVKD_FK    \n " +
			" 		LEFT JOIN QUYCACH QC ON HD_SP.DVDL_FK=QC.DVDL2_FK AND HD_SP.=QC.  \n " +
			" 		WHERE HD.TRANGTHAI NOT IN (0, 2) and isnull(is_hangmau, 0)<> 1 AND HD.LOAIHD = 1  \n " +
			" 		AND HD.NGAYXUATHD >= '" + tungay + "' AND HD.NGAYXUATHD <= '" + denngay + "' " +
			
			"  UNION ALL \n " +

			" 		SELECT 'HDTC' AS TYPE, ( SELECT TOP  1  CASE WHEN  DH.LOAIDONHANG ='2' THEN 100008 ELSE SP.DVKD_FK END    \n " +
			" 		FROM ERP_DONDATHANG DH  INNER JOIN ERP_XUATKHO XK ON XK.DONDATHANG_FK= DH.PK_SEQ   				 \n " +
			" 		INNER JOIN ERP_HOADON_XUATKHO HDXK ON HDXK.XUATKHO_FK=XK.PK_SEQ WHERE HDXK.hoadon_fk= HD.PK_SEQ  \n " +
			" 		) AS MA  , SP.DVKD_FK AS MA1 , LSP.PK_SEQ AS LSP,    \n " +
			" 		HD.NGAYXUATHD AS NGAYXUAT,   \n " +
			 		
			" 		SP.TEN AS SPTEN,    \n " +
			 		
			"		HD_SP.DONVITINH AS DVT,    \n " +
			" 		(-1)*HD_SP.SOLUONG AS SOLUONG,   \n " +
			" 		(-1)*ISNULL(ISNULL(HD_SP.DONGIAVIET,HD_SP.DONGIA)* HD_SP.SOLUONG,'0') AS THANHTIEN,   \n " +
			 
			" 		SP.MA AS , ISNULL(MKT.MA,'') AS MKT_SP,   \n " +
			"		HD.LOAIHD,    \n " +
			"		isnull(HD_SP.DVDL_FK,0) AS DVDL,  \n " +
			"		CASE  WHEN HD_SP.DVDL_FK =100003 THEN 1     \n " +
			" 		WHEN QC2.DVDL1_FK IS NOT NULL THEN   ISNULL( CAST(QC2.SOLUONG2 AS FLOAT ), 0)  / ISNULL( CAST(QC2.SOLUONG1 AS FLOAT) , 1)    \n " +
			"		WHEN   QC3.DVDL1_FK IS NOT NULL THEN   ISNULL(CAST(QC3.SOLUONG1 AS FLOAT) , 0)  / ISNULL( CAST(QC3.SOLUONG2 AS FLOAT ), 1)   \n " +
			" 		WHEN QC4.DVDL2_FK IS NOT NULL THEN (   ISNULL( CAST(QC4.SOLUONG2 AS FLOAT) , 0)  / ISNULL(CAST(QC4.SOLUONG1 AS FLOAT), 1) ) *  CAST(D.SOLUONG1 AS FLOAT)/ CAST(D.SOLUONG2 AS FLOAT)   \n " +  
			" 		ELSE 0  END AS TRONGLUONG_QC   \n " +
			" 		FROM ERP_HOADON HD    \n " +
			" 		INNER JOIN ERP_HOADON_SP HD_SP ON HD.PK_SEQ=HD_SP.HOADON_FK     \n " +
			" 		INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ= HD_SP.     \n " +
			" 		LEFT JOIN ERP_MAKETOAN MKT ON SP.MAKETOAN_FK= MKT.PK_SEQ     \n " +
			" 		LEFT JOIN ERP_LOAISANPHAM LSP ON SP.LOAI= LSP.PK_SEQ     \n " +
			"  		LEFT JOIN QUYCACH D ON HD_SP. = d.  AND HD_SP.DVDL_FK=D.DVDL2_FK   		  \n " +
			"  		LEFT JOIN QUYCACH QC2 ON QC2.=SP.PK_SEQ AND QC2.DVDL2_FK=100003  AND QC2.DVDL1_FK=HD_SP.DVDL_FK    \n " +		
			"  		LEFT JOIN QUYCACH QC3 ON QC3.=SP.PK_SEQ AND QC3.DVDL1_FK=100003  AND QC3.DVDL2_FK=HD_SP.DVDL_FK  	  \n " +	
			"  		LEFT JOIN QUYCACH QC4 ON QC4.=SP.PK_SEQ AND QC4.DVDL2_FK= 100003 		  \n " +
			"  		LEFT JOIN DONVIDOLUONG C ON HD_SP.DVDL_FK = C.PK_SEQ     \n " +
			" 		INNER JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ = SP.DVKD_FK     \n " +
			" 		LEFT JOIN QUYCACH QC ON HD_SP.DVDL_FK=QC.DVDL2_FK AND HD_SP.=QC.   \n " +
			" 		WHERE HD.TRANGTHAI NOT IN (0, 2) and isnull(is_hangmau, 0)<> 1 AND HD.LOAIHD = 2   \n " +
			"		AND HD.NGAYXUATHD >= '" + tungay + "' AND HD.NGAYXUATHD <= '" + denngay + "'  \n " +

			"  UNION ALL   \n " +
			"		SELECT 'HUYHDTC' AS TYPE, ( SELECT TOP  1  CASE WHEN  DH.LOAIDONHANG ='2' THEN 100008 ELSE SP.DVKD_FK END     \n " +
			"			FROM ERP_DONDATHANG DH    \n " +
			"			INNER JOIN ERP_XUATKHO XK ON XK.DONDATHANG_FK= DH.PK_SEQ   				  \n " +
			"			INNER JOIN ERP_HOADON_XUATKHO HDXK ON HDXK.XUATKHO_FK=XK.PK_SEQ WHERE HDXK.hoadon_fk= HD.PK_SEQ   \n " +
			"		) AS MA ,  SP.DVKD_FK AS MA, LSP.PK_SEQ AS LSP,   \n " +
			"		HUYHD.NGAYTAO AS NGAYXUAT,   \n " +
			"		SP.TEN + N' - (HỦY HÓA ĐƠN TÀI CHÍNH)' AS SPTEN,      \n " +
			"		HD_SP.DONVITINH AS DVT,     \n " +
			"		(-1)* HD_SP.SOLUONG AS SOLUONG,   \n " +
			"		ISNULL(HD_SP.DONGIAVIET* HD_SP.SOLUONG,'0')*(-1) AS THANHTIEN,    \n " +
			"		SP.MA AS ,ISNULL(MKT.MA,'') AS MKT_SP,   \n " +
			"		'3' AS LOAIHD,     \n " +
			"		isnull(HD_SP.DVDL_FK,0) AS DVDL,   \n " +
			"		CASE  WHEN HD_SP.DVDL_FK = 100003   THEN 1.0      \n " +
			"		WHEN QC2.DVDL1_FK IS NOT NULL and cast(QC2.SOLUONG1 as float) > 0   \n " +
			"		THEN  ISNULL( cast(QC2.SOLUONG2 as float), 0)  /  ISNULL( cast(QC2.SOLUONG1 as float), 1)   \n " +
			"		WHEN   QC3.DVDL1_FK IS NOT NULL  and cast(QC3.SOLUONG2 as float) >0      \n " +
			"		THEN  ISNULL(cast(QC3.SOLUONG1 as float ), 0)  / ISNULL(cast(QC3.SOLUONG2 as float), 1)   \n " +
			"		WHEN QC4.DVDL2_FK IS NOT NULL     \n " +
			"		THEN (   ISNULL(cast(QC4.SOLUONG2 as float), 0)  / ISNULL(cast(QC4.SOLUONG1 as float), 1) ) *D.SOLUONG1/D.SOLUONG2   \n " +
			"		ELSE 0  END AS TRONGLUONG_QC  \n " +
			"		FROM ERP_HUYHOADONTAICHINH HUYHD    \n " +
			"		INNER JOIN ERP_HOADON HD ON HD.PK_SEQ = HUYHD.HOADON_FK   \n " +
			"		INNER JOIN ERP_HOADON_SP HD_SP ON HD.PK_SEQ=HD_SP.HOADON_FK   \n " +
			"		INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ= HD_SP.      \n " +
			"		LEFT JOIN ERP_MAKETOAN MKT ON SP.MAKETOAN_FK= MKT.PK_SEQ      \n " +
			"		LEFT JOIN ERP_LOAISANPHAM LSP ON SP.LOAI= LSP.PK_SEQ      \n " +
			"		LEFT JOIN QUYCACH d on HD_SP. = d.  and HD_SP.dvdl_fk = d.dvdl2_fk  and d.dvdl1_fk=SP.dvdl_fk   \n " +
			"		LEFT JOIN QUYCACH QC2 ON QC2.=SP.PK_SEQ AND QC2.DVDL2_FK=100003  AND QC2.DVDL1_FK=HD_SP.DVDL_FK      \n " +
			"		LEFT JOIN QUYCACH QC3 ON QC3.=SP.PK_SEQ AND QC3.DVDL1_FK=100003  AND QC3.DVDL2_FK=HD_SP.DVDL_FK    \n " +
			"		LEFT JOIN QUYCACH QC4 ON QC4.=SP.PK_SEQ AND QC4.DVDL2_FK= 100003     \n " +
			"		LEFT JOIN DONVIDOLUONG C ON HD_SP.DVDL_FK = C.PK_SEQ      \n " +
			"		INNER JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ = SP.DVKD_FK      \n " +
			"		LEFT JOIN QUYCACH QC ON HD_SP.DVDL_FK=QC.DVDL2_FK AND HD_SP.=QC.   \n " +
			"		WHERE HUYHD.TRANGTHAI NOT IN (0, 2) and isnull(is_hangmau, 0)<> 1   \n " +
			"		AND  HUYHD.NGAYTAO >= '" + tungay + "' AND HUYHD.NGAYTAO <= '" + denngay + "'  \n " +

			"  UNION ALL 		 \n " +
			"  SELECT 'HUYHDTRAVE' AS TYPE,  SP.DVKD_FK, SP.DVKD_FK AS MA, LSP.PK_SEQ AS LSP,    \n " +
			"		HUYCT.ngayhuy AS NGAYXUAT,  \n " +
			" 		CASE WHEN HD.LOAIHD = 1 THEN SP.TEN ELSE SP.TEN + N' - (H?Y HÓA ??N HÀNG TR? V?)' END  AS SPTEN,    \n " +
			"		HD_SP.DONVITINH AS DVT,   \n " +
			" 		(-1)*  HD_SP.SOLUONG AS SOLUONG,  \n " +
			" 		(-1)*ISNULL(HD_SP.DONGIAVIET* HD_SP.SOLUONG,'0') AS THANHTIEN,  \n " +
			 	  
			" 		SP.MA AS , ISNULL(MKT.MA,'') AS MKT_SP, HD.LOAIHD,   \n " +
			" 		isnull(HD_SP.DVDL_FK,0) AS DVDL,  \n " +
			 		 
			" 		(-1)*  CASE  WHEN HD_SP.DVDL_FK =100003 THEN 1    \n " +
			" 		 WHEN QC2.DVDL1_FK IS NOT NULL THEN   ISNULL( CAST(QC2.SOLUONG2 AS FLOAT ), 0)  / ISNULL( CAST(QC2.SOLUONG1 AS FLOAT) , 1)  \n " + 
			"		 WHEN   QC3.DVDL1_FK IS NOT NULL THEN   ISNULL(CAST(QC3.SOLUONG1 AS FLOAT) , 0)  / ISNULL( CAST(QC3.SOLUONG2 AS FLOAT ), 1)  \n " +
			" 		 WHEN QC4.DVDL2_FK IS NOT NULL THEN (   ISNULL( CAST(QC4.SOLUONG2 AS FLOAT) , 0)  / ISNULL(CAST(QC4.SOLUONG1 AS FLOAT), 1) ) *  CAST(D.SOLUONG1 AS FLOAT)/ CAST(D.SOLUONG2 AS FLOAT)  \n " + 
			"		 ELSE 0  END AS TRONGLUONG_QC \n " +
					 
			" 		FROM ERP_HOADON HD   \n " +
			"  		INNER JOIN ERP_HUYCHUNGTUBANHANG HUYCT ON HUYCT.SOCHUNGTU=HD.PK_SEQ  \n " +
			" 		INNER JOIN ERP_HOADON_SP HD_SP ON HD.PK_SEQ=HD_SP.HOADON_FK    \n " +
			" 		INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ= HD_SP.    \n " +
			" 		LEFT JOIN ERP_MAKETOAN MKT ON SP.MAKETOAN_FK= MKT.PK_SEQ    \n " +
			" 		LEFT JOIN ERP_LOAISANPHAM LSP ON SP.LOAI= LSP.PK_SEQ    \n " +
			"		LEFT JOIN QUYCACH D ON HD_SP. = d.  AND HD_SP.DVDL_FK=D.DVDL2_FK   		 \n " +
			"		LEFT JOIN QUYCACH QC2 ON QC2.=SP.PK_SEQ AND QC2.DVDL2_FK=100003  AND QC2.DVDL1_FK=HD_SP.DVDL_FK  		  \n " +
			"		LEFT JOIN QUYCACH QC3 ON QC3.=SP.PK_SEQ AND QC3.DVDL1_FK=100003  AND QC3.DVDL2_FK=HD_SP.DVDL_FK  		 \n " +
			"		LEFT JOIN QUYCACH QC4 ON QC4.=SP.PK_SEQ AND QC4.DVDL2_FK= 100003 		 \n " +
			"		LEFT JOIN DONVIDOLUONG C ON HD_SP.DVDL_FK = C.PK_SEQ    \n " +
			" 		INNER JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ = SP.DVKD_FK    \n " +
			" 		LEFT JOIN QUYCACH QC ON HD_SP.DVDL_FK=QC.DVDL2_FK AND HD_SP.=QC.  \n " +
			" 		WHERE HD.TRANGTHAI NOT IN (0, 2) and isnull(is_hangmau, 0)<> 1   \n " +
			" 		AND  HUYCT.LOAICHUNGTU IN ('HOADON_KHTRA') AND 	    HUYCT.huykhackyketoan='1' and HUYCT.TRANGTHAI='1'   \n " +
			" 		AND  HUYCT.ngayhuy >= '" + tungay + "' AND HUYCT.ngayhuy <= '" + denngay + "' \n " +
			 		
			" 		UNION ALL   \n " +
			" 		SELECT  'HDKHAC' AS TYPE,  \n " +
			" 		CASE WHEN HDPL.DVKD_FK IS NOT NULL AND (HDPL.DOANHTHU_FK = 100000 OR HDPL.DOANHTHU_FK = 400000) THEN HDPL.DVKD_FK   \n " +
			" 		ELSE   \n " +
			" 		CASE WHEN HDPL.DVKD_FK <> 110000 AND HDPL.DOANHTHU_FK = 100004 THEN 100008  \n " +
			" 		ELSE 100007 END   \n " +
			" 		END AS MA1,    \n " +
			 		
			" 		ISNULL(HDPL.DVKD_FK,100007) AS MA,   \n " +
			" 		'1' AS LSP, NGAYHOADON AS NGAYXUAT, ISNULL(HDPL_SP.DIENGIAI,'') AS SPTEN,   \n " +
			"		ISNULL(HDPL_SP.DONVITINH,'') AS DVT,    \n " +
			" 		HDPL_SP.SOLUONG AS SOLUONG, ISNULL(HDPL_SP.SOLUONG*HDPL_SP.DONGIA,'0') AS THANHTIEN,   \n " +
			  
			" 		'0' AS , ''  AS MKT_SP,    \n " +
			" 		'1' AS LOAIHD,    \n " +
			" 		'0' AS DVDL,  \n " +
			 		
			" 		'0' as TRONGLUONG_QC \n " +
			 		 
			" 		FROM ERP_HOADONPHELIEU HDPL    \n " +
			" 		INNER JOIN ERP_HOADONPHELIEU_SANPHAM HDPL_SP ON HDPL.PK_SEQ=HDPL_SP.HOADONPHELIEU_FK   \n " +
			" 		LEFT JOIN ERP_TRUNGTAMDOANHTHU TTDT ON HDPL.TRUNGTAMDOANHTHU_FK=TTDT.PK_SEQ   \n " +
			" 		WHERE HDPL.TRANGTHAI NOT IN (0, 2)  \n " +
			" 		AND  HDPL.NGAYHOADON >= '" + tungay + "' AND HDPL.NGAYHOADON <= '" + denngay + "' \n " +
			 
			" 		UNION ALL   \n " +
			" 		SELECT  'HUY_HDKHAC' AS TYPE,  \n " +
			" 		CASE WHEN HDPL.DVKD_FK IS NOT NULL AND (HDPL.DOANHTHU_FK = 100000 OR HDPL.DOANHTHU_FK = 400000) THEN HDPL.DVKD_FK   \n " +
			" 		ELSE   \n " +
			" 		CASE WHEN HDPL.DVKD_FK <> 110000 AND HDPL.DOANHTHU_FK = 100004 THEN 100008  \n " +
			" 		ELSE 100007 END   \n " +
			" 		END AS MA1, '1' AS LSP, \n " +
			" 		ISNULL(HDPL.DVKD_FK,100007)  AS MA,   \n " +
			" 		HUYCT.NGAYHUY AS NGAYXUAT, ISNULL(HDPL_SP.DIENGIAI,'') + N'- (HỦY HÓA ĐƠN KHÁC)' AS SPTEN,   \n " +
			"		ISNULL(HDPL_SP.DONVITINH,'') AS DVT,    \n " +
			" 		 (-1)* HDPL_SP.SOLUONG AS SOLUONG, (-1) * ISNULL(HDPL_SP.SOLUONG*HDPL_SP.DONGIA,'0') AS THANHTIEN,   \n " +
			 		  
			" 		'0' AS , ''  AS MKT_SP, '3' AS LOAIHD,    \n " +
			" 		'0' AS DVDL, '0' as TRONGLUONG_QC \n " +
			 		 
			" 		FROM ERP_HOADONPHELIEU HDPL    \n " +
			"  		INNER JOIN ERP_HUYCHUNGTUBANHANG HUYCT ON HUYCT.SOCHUNGTU=HDPL.PK_SEQ  \n " +
			" 		INNER JOIN ERP_HOADONPHELIEU_SANPHAM HDPL_SP ON HDPL.PK_SEQ=HDPL_SP.HOADONPHELIEU_FK   \n " +
			" 		LEFT JOIN ERP_TRUNGTAMDOANHTHU TTDT ON HDPL.TRUNGTAMDOANHTHU_FK=TTDT.PK_SEQ   \n " +
			" 		WHERE  HUYCT.LOAICHUNGTU IN ('HOADONKHAC') AND 	    HUYCT.huykhackyketoan='1'  \n " +
			" 		and HUYCT.TRANGTHAI='1'  \n " +
			" 		AND  HUYCT.ngayhuy >= '" + tungay + "' AND HUYCT.ngayhuy <= '" + denngay + "' \n " +

			" 		UNION all    \n " +

			" 		SELECT  'HDGG' AS TYPE ,   \n " +
			" 		ISNULL(GGHB.DVKD_FK, 100007) AS MA1,   \n " +
			" 		ISNULL(GGHB.DVKD_FK, 100007) AS MA,    \n " +
			" 		'1' AS LSP, GGHB.NGAYHOADON AS NGAYXUAT, GGHB.DIENGIAI AS SPTEN,    \n " +
			"		'' AS DVT, '0' AS SOLUONG,     \n " +
			"		CASE WHEN GGHB.TIENTE_FK = '100000' " +
			"		THEN gghd.SOTIENTANGGIAM " +
			"		ELSE (gghd.SOTIENTANGGIAM* ISNULL(GGHB.TIGIA,0) ) END AS THANHTIEN,    \n " +
			  		
			"  		'0' AS , ''  AS MKT_SP,   \n " +
			" 		'10' AS LOAIHD , '0' AS DVDL,   \n " +
			" 		'0' as TRONGLUONG_QC  \n " +
			 		
			"  		FROM  ERP_GIAMGIAHANGBAN GGHB    \n " +
			" 		INNER JOIN    \n " +
			" 		(    \n " +
			" 			SELECT GIAMGIA_FK, SUM(SOTIENTANGGIAM) AS TIENTG    \n " +
			" 			FROM ERP_GIAMGIAHANGBAN_HOADON    \n " +
			" 			GROUP BY GIAMGIA_FK    \n " +
			" 		) GGHB_HD ON GGHB.PK_SEQ = GGHB_HD.GIAMGIA_FK    \n " +
			" 		LEFT JOIN ERP_GIAMGIAHANGBAN_HOADON gghd on gghb.pk_seq= gghd.giamgia_fk    \n " + 
			" 		WHERE GGHB.TRANGTHAI NOT IN (0, 2)  \n " +
			" 		AND GGHB.NGAYHOADON >= '" + tungay + "' AND GGHB.NGAYHOADON <= '" + denngay + "'   \n " +

			" 		UNION all   \n " +
			 		
			" 		SELECT  'HUY-HDGG' AS TYPE ,  \n " +
			" 		ISNULL(GGHB.DVKD_FK, 100007) AS MA1, \n " +
			" 		ISNULL(GGHB.DVKD_FK, 100007) AS MA,   \n " +
			" 		'1' AS LSP, GGHB.NGAYHOADON AS NGAYXUAT, GGHB.DIENGIAI +N'- HỦY HÓA ĐƠN GIẢM TĂNG GIÁ HÀNG BÁN' AS SPTEN,   \n " +
			"		'' AS DVT, 0 AS SOLUONG,  \n " +
			"		(-1)*  CASE WHEN GGHB.TIENTE_FK = '100000' THEN gghd.SOTIENTANGGIAM ELSE (gghd.SOTIENTANGGIAM* ISNULL(GGHB.TIGIA,0) ) END AS THANHTIEN, \n " +  
			  		
			"  		'0' AS , ''  AS MKT_SP,  \n " +
			" 		'3' AS LOAIHD, '0' AS DVDL,  \n " +
			" 		'0' as TRONGLUONG_QC \n " +
			 		
			"  		FROM  ERP_GIAMGIAHANGBAN GGHB   \n " +
			"  		INNER JOIN ERP_HUYCHUNGTUBANHANG HUYCT ON HUYCT.SOCHUNGTU=GGHB.PK_SEQ  \n " +
			" 		INNER JOIN   \n " +
			" 		(   \n " +
			" 			SELECT GIAMGIA_FK, SUM(SOTIENTANGGIAM) AS TIENTG   \n " +
			" 			FROM ERP_GIAMGIAHANGBAN_HOADON   \n " +
			" 			GROUP BY GIAMGIA_FK   \n " +
			" 		) GGHB_HD ON GGHB.PK_SEQ = GGHB_HD.GIAMGIA_FK   \n " +
			" 		LEFT JOIN ERP_GIAMGIAHANGBAN_HOADON gghd on gghb.pk_seq= gghd.giamgia_fk    \n " +
			" 		WHERE  HUYCT.LOAICHUNGTU IN ('TANGGIAMGIA') AND  HUYCT.huykhackyketoan='1' AND HUYCT.TRANGTHAI='1'  \n " +
			" 		and HUYCT.ngayhuy >= '" + tungay + "' AND HUYCT.ngayhuy <= '" + denngay + "' \n " +
			" 	) A WHERE 1 = 1   \n " +
			"  )B WHERE 1 = 1 AND B.DVKD <> 'L' \n " +
			" )DATA  \n " +
			" GROUP BY DATA.THANG \n " +
			" ORDER BY DATA.THANG \n " ;
			 
			 System.out.println(query);

		data = prepareData(data, query);
		return data;
	}	


	public String[] PL_SalesVolumn_PA_Budget(){
		String[] data = new String[13];
		String tungay = this.year + "-01-01";
		String denngay = this.year + "-" + (this.month.length() > 1?this.month:"0" + this.month) + "-31";
		
		String query = 	
			"SELECT THANG, SUM(TRONGLUONG) AS NUM \n " +
			"FROM  \n " +
			"( \n " +
			"	SELECT SUBSTRING(NGAYXUAT, 6, 2) AS THANG,  \n " +
			"	SOLUONG*TRONGLUONG_QC AS TRONGLUONG \n " +
			"	FROM \n " +
			"	(  \n " +
			" 		SELECT  type,  \n " +
			" 		CASE WHEN A.LSP IN (100013,100014,100015,100016,100017) THEN 'NL'  \n " +
			" 		ELSE   \n " +
			" 			 CASE WHEN A.MA = 100000 THEN 'N'   \n " +
			" 		ELSE CASE WHEN A.MA = 100004 THEN 'L'   \n " +
			" 		ELSE CASE WHEN A.MA = 100005 THEN 'SPM'   \n " +
			" 		ELSE CASE WHEN A.MA = 100007 THEN 'K'  \n " +
			" 		ELSE CASE WHEN A.MA = 100008 THEN 'GC'  \n " +
			" 		ELSE 'K'   \n " +
			" 		END END END END END  \n " +
			" 		END AS DVKD,    \n " +
			 	
			" 		CASE WHEN A.MA1 = 100000 THEN N'Nhôm'   \n " +
			" 		  WHEN A.MA1 = 100004 THEN N'Lõi'   \n " +
			" 		  WHEN A.MA1= 100005 THEN N'SPM'   \n " +
			" 		ELSE N'Khác'   \n " +
			" 		END   as donvikinhdoanh,  \n " +
			" 	A.LSP,    \n " +
			" 	A.NGAYXUAT, A.SPTEN,    \n " +
			" 	A.SOLUONG,   \n " +
			"	A.THANHTIEN, A.,    \n " +
			"    A.MKT_SP,    \n " +
			"	A.DVDL, A.TRONGLUONG_QC \n " +
			" 	FROM    \n " +
			" 	(  \n " +

			" 		SELECT 'HDTC' AS TYPE,  \n " +
			" 		( SELECT TOP  1  CASE WHEN  DH.LOAIDONHANG ='2' THEN 100008 ELSE SP.DVKD_FK END    \n " +
			" 		FROM ERP_DONDATHANG DH  INNER JOIN ERP_XUATKHO XK ON XK.DONDATHANG_FK= DH.PK_SEQ   			 \n " +	
			" 		INNER JOIN ERP_HOADON_XUATKHO HDXK ON HDXK.XUATKHO_FK=XK.PK_SEQ WHERE HDXK.hoadon_fk= HD.PK_SEQ  \n " +
			" 		)AS MA,  \n " +
			 		
			" 		SP.DVKD_FK AS MA1 , LSP.PK_SEQ AS LSP,    \n " +
			" 		HD.NGAYXUATHD AS NGAYXUAT,   \n " +
			 		
			" 		SP.TEN AS SPTEN,    \n " +
			 		
			"		HD_SP.DONVITINH AS DVT,   \n " +
			" 		HD_SP.SOLUONG AS SOLUONG,  \n " +
			" 		ISNULL(ISNULL(HD_SP.DONGIAVIET,HD_SP.DONGIA)* HD_SP.SOLUONG,'0') AS THANHTIEN,  \n " +
			 
			" 		SP.MA AS , ISNULL(MKT.MA,'') AS MKT_SP,  \n " +
			"		HD.LOAIHD,   \n " +
			"		isnull(HD_SP.DVDL_FK,0) AS DVDL, \n " +
			"		CASE  WHEN HD_SP.DVDL_FK =100003 THEN 1    \n " +
			" 		WHEN QC2.DVDL1_FK IS NOT NULL THEN   ISNULL( CAST(QC2.SOLUONG2 AS FLOAT ), 0)  / ISNULL( CAST(QC2.SOLUONG1 AS FLOAT) , 1)   \n " +
			"		WHEN   QC3.DVDL1_FK IS NOT NULL THEN   ISNULL(CAST(QC3.SOLUONG1 AS FLOAT) , 0)  / ISNULL( CAST(QC3.SOLUONG2 AS FLOAT ), 1)  \n " +
			" 		WHEN QC4.DVDL2_FK IS NOT NULL THEN (   ISNULL( CAST(QC4.SOLUONG2 AS FLOAT) , 0)  / ISNULL(CAST(QC4.SOLUONG1 AS FLOAT), 1) ) *  CAST(D.SOLUONG1 AS FLOAT)/ CAST(D.SOLUONG2 AS FLOAT)  \n " + 
			" 		ELSE 0  END AS TRONGLUONG_QC  \n " +
			 		
			" 		FROM ERP_HOADON HD   \n " +
			" 		INNER JOIN ERP_HOADON_SP HD_SP ON HD.PK_SEQ=HD_SP.HOADON_FK    \n " +
			" 		INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ= HD_SP.    \n " +
			" 		LEFT JOIN ERP_MAKETOAN MKT ON SP.MAKETOAN_FK= MKT.PK_SEQ    \n " +
			" 		LEFT JOIN ERP_LOAISANPHAM LSP ON SP.LOAI= LSP.PK_SEQ    \n " +
			"  		LEFT JOIN QUYCACH D ON HD_SP. = d.  AND HD_SP.DVDL_FK=D.DVDL2_FK   		 \n " +
			"  		LEFT JOIN QUYCACH QC2 ON QC2.=SP.PK_SEQ AND QC2.DVDL2_FK=100003  AND QC2.DVDL1_FK=HD_SP.DVDL_FK  		 \n " +
			"  		LEFT JOIN QUYCACH QC3 ON QC3.=SP.PK_SEQ AND QC3.DVDL1_FK=100003  AND QC3.DVDL2_FK=HD_SP.DVDL_FK  		 \n " +
			"  		LEFT JOIN QUYCACH QC4 ON QC4.=SP.PK_SEQ AND QC4.DVDL2_FK= 100003 		 \n " +
			"  		LEFT JOIN DONVIDOLUONG C ON HD_SP.DVDL_FK = C.PK_SEQ    \n " +
			" 		INNER JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ = SP.DVKD_FK    \n " +
			" 		LEFT JOIN QUYCACH QC ON HD_SP.DVDL_FK=QC.DVDL2_FK AND HD_SP.=QC.  \n " +
			" 		WHERE HD.TRANGTHAI NOT IN (0, 2) and isnull(is_hangmau, 0)<> 1 AND HD.LOAIHD = 1  \n " +
			" 		AND HD.NGAYXUATHD >= '" + tungay + "' AND HD.NGAYXUATHD <= '" + denngay + "' " +
			
			"  UNION ALL \n " +

			" 		SELECT 'HDTC' AS TYPE, ( SELECT TOP  1  CASE WHEN  DH.LOAIDONHANG ='2' THEN 100008 ELSE SP.DVKD_FK END    \n " +
			" 		FROM ERP_DONDATHANG DH  INNER JOIN ERP_XUATKHO XK ON XK.DONDATHANG_FK= DH.PK_SEQ   				 \n " +
			" 		INNER JOIN ERP_HOADON_XUATKHO HDXK ON HDXK.XUATKHO_FK=XK.PK_SEQ WHERE HDXK.hoadon_fk= HD.PK_SEQ  \n " +
			" 		) AS MA  , SP.DVKD_FK AS MA1 , LSP.PK_SEQ AS LSP,    \n " +
			" 		HD.NGAYXUATHD AS NGAYXUAT,   \n " +
			 		
			" 		SP.TEN AS SPTEN,    \n " +
			 		
			"		HD_SP.DONVITINH AS DVT,    \n " +
			" 		(-1)*HD_SP.SOLUONG AS SOLUONG,   \n " +
			" 		(-1)*ISNULL(ISNULL(HD_SP.DONGIAVIET,HD_SP.DONGIA)* HD_SP.SOLUONG,'0') AS THANHTIEN,   \n " +
			 
			" 		SP.MA AS , ISNULL(MKT.MA,'') AS MKT_SP,   \n " +
			"		HD.LOAIHD,    \n " +
			"		isnull(HD_SP.DVDL_FK,0) AS DVDL,  \n " +
			"		CASE  WHEN HD_SP.DVDL_FK =100003 THEN 1     \n " +
			" 		WHEN QC2.DVDL1_FK IS NOT NULL THEN   ISNULL( CAST(QC2.SOLUONG2 AS FLOAT ), 0)  / ISNULL( CAST(QC2.SOLUONG1 AS FLOAT) , 1)    \n " +
			"		WHEN   QC3.DVDL1_FK IS NOT NULL THEN   ISNULL(CAST(QC3.SOLUONG1 AS FLOAT) , 0)  / ISNULL( CAST(QC3.SOLUONG2 AS FLOAT ), 1)   \n " +
			" 		WHEN QC4.DVDL2_FK IS NOT NULL THEN (   ISNULL( CAST(QC4.SOLUONG2 AS FLOAT) , 0)  / ISNULL(CAST(QC4.SOLUONG1 AS FLOAT), 1) ) *  CAST(D.SOLUONG1 AS FLOAT)/ CAST(D.SOLUONG2 AS FLOAT)   \n " +  
			" 		ELSE 0  END AS TRONGLUONG_QC   \n " +
			" 		FROM ERP_HOADON HD    \n " +
			" 		INNER JOIN ERP_HOADON_SP HD_SP ON HD.PK_SEQ=HD_SP.HOADON_FK     \n " +
			" 		INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ= HD_SP.     \n " +
			" 		LEFT JOIN ERP_MAKETOAN MKT ON SP.MAKETOAN_FK= MKT.PK_SEQ     \n " +
			" 		LEFT JOIN ERP_LOAISANPHAM LSP ON SP.LOAI= LSP.PK_SEQ     \n " +
			"  		LEFT JOIN QUYCACH D ON HD_SP. = d.  AND HD_SP.DVDL_FK=D.DVDL2_FK   		  \n " +
			"  		LEFT JOIN QUYCACH QC2 ON QC2.=SP.PK_SEQ AND QC2.DVDL2_FK=100003  AND QC2.DVDL1_FK=HD_SP.DVDL_FK    \n " +		
			"  		LEFT JOIN QUYCACH QC3 ON QC3.=SP.PK_SEQ AND QC3.DVDL1_FK=100003  AND QC3.DVDL2_FK=HD_SP.DVDL_FK  	  \n " +	
			"  		LEFT JOIN QUYCACH QC4 ON QC4.=SP.PK_SEQ AND QC4.DVDL2_FK= 100003 		  \n " +
			"  		LEFT JOIN DONVIDOLUONG C ON HD_SP.DVDL_FK = C.PK_SEQ     \n " +
			" 		INNER JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ = SP.DVKD_FK     \n " +
			" 		LEFT JOIN QUYCACH QC ON HD_SP.DVDL_FK=QC.DVDL2_FK AND HD_SP.=QC.   \n " +
			" 		WHERE HD.TRANGTHAI NOT IN (0, 2) and isnull(is_hangmau, 0)<> 1 AND HD.LOAIHD = 2   \n " +
			"		AND HD.NGAYXUATHD >= '" + tungay + "' AND HD.NGAYXUATHD <= '" + denngay + "'  \n " +

			"  UNION ALL   \n " +
			"		SELECT 'HUYHDTC' AS TYPE, ( SELECT TOP  1  CASE WHEN  DH.LOAIDONHANG ='2' THEN 100008 ELSE SP.DVKD_FK END     \n " +
			"			FROM ERP_DONDATHANG DH    \n " +
			"			INNER JOIN ERP_XUATKHO XK ON XK.DONDATHANG_FK= DH.PK_SEQ   				  \n " +
			"			INNER JOIN ERP_HOADON_XUATKHO HDXK ON HDXK.XUATKHO_FK=XK.PK_SEQ WHERE HDXK.hoadon_fk= HD.PK_SEQ   \n " +
			"		) AS MA ,  SP.DVKD_FK AS MA, LSP.PK_SEQ AS LSP,   \n " +
			"		HUYHD.NGAYTAO AS NGAYXUAT,   \n " +
			"		SP.TEN + N' - (HỦY HÓA ĐƠN TÀI CHÍNH)' AS SPTEN,      \n " +
			"		HD_SP.DONVITINH AS DVT,     \n " +
			"		(-1)* HD_SP.SOLUONG AS SOLUONG,   \n " +
			"		ISNULL(HD_SP.DONGIAVIET* HD_SP.SOLUONG,'0')*(-1) AS THANHTIEN,    \n " +
			"		SP.MA AS ,ISNULL(MKT.MA,'') AS MKT_SP,   \n " +
			"		'3' AS LOAIHD,     \n " +
			"		isnull(HD_SP.DVDL_FK,0) AS DVDL,   \n " +
			"		CASE  WHEN HD_SP.DVDL_FK = 100003   THEN 1.0      \n " +
			"		WHEN QC2.DVDL1_FK IS NOT NULL and cast(QC2.SOLUONG1 as float) > 0   \n " +
			"		THEN  ISNULL( cast(QC2.SOLUONG2 as float), 0)  /  ISNULL( cast(QC2.SOLUONG1 as float), 1)   \n " +
			"		WHEN   QC3.DVDL1_FK IS NOT NULL  and cast(QC3.SOLUONG2 as float) >0      \n " +
			"		THEN  ISNULL(cast(QC3.SOLUONG1 as float ), 0)  / ISNULL(cast(QC3.SOLUONG2 as float), 1)   \n " +
			"		WHEN QC4.DVDL2_FK IS NOT NULL     \n " +
			"		THEN (   ISNULL(cast(QC4.SOLUONG2 as float), 0)  / ISNULL(cast(QC4.SOLUONG1 as float), 1) ) *D.SOLUONG1/D.SOLUONG2   \n " +
			"		ELSE 0  END AS TRONGLUONG_QC  \n " +
			"		FROM ERP_HUYHOADONTAICHINH HUYHD    \n " +
			"		INNER JOIN ERP_HOADON HD ON HD.PK_SEQ = HUYHD.HOADON_FK   \n " +
			"		INNER JOIN ERP_HOADON_SP HD_SP ON HD.PK_SEQ=HD_SP.HOADON_FK   \n " +
			"		INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ= HD_SP.      \n " +
			"		LEFT JOIN ERP_MAKETOAN MKT ON SP.MAKETOAN_FK= MKT.PK_SEQ      \n " +
			"		LEFT JOIN ERP_LOAISANPHAM LSP ON SP.LOAI= LSP.PK_SEQ      \n " +
			"		LEFT JOIN QUYCACH d on HD_SP. = d.  and HD_SP.dvdl_fk = d.dvdl2_fk  and d.dvdl1_fk=SP.dvdl_fk   \n " +
			"		LEFT JOIN QUYCACH QC2 ON QC2.=SP.PK_SEQ AND QC2.DVDL2_FK=100003  AND QC2.DVDL1_FK=HD_SP.DVDL_FK      \n " +
			"		LEFT JOIN QUYCACH QC3 ON QC3.=SP.PK_SEQ AND QC3.DVDL1_FK=100003  AND QC3.DVDL2_FK=HD_SP.DVDL_FK    \n " +
			"		LEFT JOIN QUYCACH QC4 ON QC4.=SP.PK_SEQ AND QC4.DVDL2_FK= 100003     \n " +
			"		LEFT JOIN DONVIDOLUONG C ON HD_SP.DVDL_FK = C.PK_SEQ      \n " +
			"		INNER JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ = SP.DVKD_FK      \n " +
			"		LEFT JOIN QUYCACH QC ON HD_SP.DVDL_FK=QC.DVDL2_FK AND HD_SP.=QC.   \n " +
			"		WHERE HUYHD.TRANGTHAI NOT IN (0, 2) and isnull(is_hangmau, 0)<> 1   \n " +
			"		AND  HUYHD.NGAYTAO >= '" + tungay + "' AND HUYHD.NGAYTAO <= '" + denngay + "'  \n " +

			"  UNION ALL 		 \n " +
			"  SELECT 'HUYHDTRAVE' AS TYPE,  SP.DVKD_FK, SP.DVKD_FK AS MA, LSP.PK_SEQ AS LSP,    \n " +
			"		HUYCT.ngayhuy AS NGAYXUAT,  \n " +
			" 		CASE WHEN HD.LOAIHD = 1 THEN SP.TEN ELSE SP.TEN + N' - (H?Y HÓA ??N HÀNG TR? V?)' END  AS SPTEN,    \n " +
			"		HD_SP.DONVITINH AS DVT,   \n " +
			" 		(-1)*  HD_SP.SOLUONG AS SOLUONG,  \n " +
			" 		(-1)*ISNULL(HD_SP.DONGIAVIET* HD_SP.SOLUONG,'0') AS THANHTIEN,  \n " +
			 	  
			" 		SP.MA AS , ISNULL(MKT.MA,'') AS MKT_SP, HD.LOAIHD,   \n " +
			" 		isnull(HD_SP.DVDL_FK,0) AS DVDL,  \n " +
			 		 
			" 		(-1)*  CASE  WHEN HD_SP.DVDL_FK =100003 THEN 1    \n " +
			" 		 WHEN QC2.DVDL1_FK IS NOT NULL THEN   ISNULL( CAST(QC2.SOLUONG2 AS FLOAT ), 0)  / ISNULL( CAST(QC2.SOLUONG1 AS FLOAT) , 1)  \n " + 
			"		 WHEN   QC3.DVDL1_FK IS NOT NULL THEN   ISNULL(CAST(QC3.SOLUONG1 AS FLOAT) , 0)  / ISNULL( CAST(QC3.SOLUONG2 AS FLOAT ), 1)  \n " +
			" 		 WHEN QC4.DVDL2_FK IS NOT NULL THEN (   ISNULL( CAST(QC4.SOLUONG2 AS FLOAT) , 0)  / ISNULL(CAST(QC4.SOLUONG1 AS FLOAT), 1) ) *  CAST(D.SOLUONG1 AS FLOAT)/ CAST(D.SOLUONG2 AS FLOAT)  \n " + 
			"		 ELSE 0  END AS TRONGLUONG_QC \n " +
					 
			" 		FROM ERP_HOADON HD   \n " +
			"  		INNER JOIN ERP_HUYCHUNGTUBANHANG HUYCT ON HUYCT.SOCHUNGTU=HD.PK_SEQ  \n " +
			" 		INNER JOIN ERP_HOADON_SP HD_SP ON HD.PK_SEQ=HD_SP.HOADON_FK    \n " +
			" 		INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ= HD_SP.    \n " +
			" 		LEFT JOIN ERP_MAKETOAN MKT ON SP.MAKETOAN_FK= MKT.PK_SEQ    \n " +
			" 		LEFT JOIN ERP_LOAISANPHAM LSP ON SP.LOAI= LSP.PK_SEQ    \n " +
			"		LEFT JOIN QUYCACH D ON HD_SP. = d.  AND HD_SP.DVDL_FK=D.DVDL2_FK   		 \n " +
			"		LEFT JOIN QUYCACH QC2 ON QC2.=SP.PK_SEQ AND QC2.DVDL2_FK=100003  AND QC2.DVDL1_FK=HD_SP.DVDL_FK  		  \n " +
			"		LEFT JOIN QUYCACH QC3 ON QC3.=SP.PK_SEQ AND QC3.DVDL1_FK=100003  AND QC3.DVDL2_FK=HD_SP.DVDL_FK  		 \n " +
			"		LEFT JOIN QUYCACH QC4 ON QC4.=SP.PK_SEQ AND QC4.DVDL2_FK= 100003 		 \n " +
			"		LEFT JOIN DONVIDOLUONG C ON HD_SP.DVDL_FK = C.PK_SEQ    \n " +
			" 		INNER JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ = SP.DVKD_FK    \n " +
			" 		LEFT JOIN QUYCACH QC ON HD_SP.DVDL_FK=QC.DVDL2_FK AND HD_SP.=QC.  \n " +
			" 		WHERE HD.TRANGTHAI NOT IN (0, 2) and isnull(is_hangmau, 0)<> 1   \n " +
			" 		AND  HUYCT.LOAICHUNGTU IN ('HOADON_KHTRA') AND 	    HUYCT.huykhackyketoan='1' and HUYCT.TRANGTHAI='1'   \n " +
			" 		AND  HUYCT.ngayhuy >= '" + tungay + "' AND HUYCT.ngayhuy <= '" + denngay + "' \n " +
			 		
			" 		UNION ALL   \n " +
			" 		SELECT  'HDKHAC' AS TYPE,  \n " +
			" 		CASE WHEN HDPL.DVKD_FK IS NOT NULL AND (HDPL.DOANHTHU_FK = 100000 OR HDPL.DOANHTHU_FK = 400000) THEN HDPL.DVKD_FK   \n " +
			" 		ELSE   \n " +
			" 		CASE WHEN HDPL.DVKD_FK <> 110000 AND HDPL.DOANHTHU_FK = 100004 THEN 100008  \n " +
			" 		ELSE 100007 END   \n " +
			" 		END AS MA1,    \n " +
			 		
			" 		ISNULL(HDPL.DVKD_FK,100007) AS MA,   \n " +
			" 		'1' AS LSP, NGAYHOADON AS NGAYXUAT, ISNULL(HDPL_SP.DIENGIAI,'') AS SPTEN,   \n " +
			"		ISNULL(HDPL_SP.DONVITINH,'') AS DVT,    \n " +
			" 		HDPL_SP.SOLUONG AS SOLUONG, ISNULL(HDPL_SP.SOLUONG*HDPL_SP.DONGIA,'0') AS THANHTIEN,   \n " +
			  
			" 		'0' AS , ''  AS MKT_SP,    \n " +
			" 		'1' AS LOAIHD,    \n " +
			" 		'0' AS DVDL,  \n " +
			 		
			" 		'0' as TRONGLUONG_QC \n " +
			 		 
			" 		FROM ERP_HOADONPHELIEU HDPL    \n " +
			" 		INNER JOIN ERP_HOADONPHELIEU_SANPHAM HDPL_SP ON HDPL.PK_SEQ=HDPL_SP.HOADONPHELIEU_FK   \n " +
			" 		LEFT JOIN ERP_TRUNGTAMDOANHTHU TTDT ON HDPL.TRUNGTAMDOANHTHU_FK=TTDT.PK_SEQ   \n " +
			" 		WHERE HDPL.TRANGTHAI NOT IN (0, 2)  \n " +
			" 		AND  HDPL.NGAYHOADON >= '" + tungay + "' AND HDPL.NGAYHOADON <= '" + denngay + "' \n " +
			 
			" 		UNION ALL   \n " +
			" 		SELECT  'HUY_HDKHAC' AS TYPE,  \n " +
			" 		CASE WHEN HDPL.DVKD_FK IS NOT NULL AND (HDPL.DOANHTHU_FK = 100000 OR HDPL.DOANHTHU_FK = 400000) THEN HDPL.DVKD_FK   \n " +
			" 		ELSE   \n " +
			" 		CASE WHEN HDPL.DVKD_FK <> 110000 AND HDPL.DOANHTHU_FK = 100004 THEN 100008  \n " +
			" 		ELSE 100007 END   \n " +
			" 		END AS MA1, '1' AS LSP, \n " +
			" 		ISNULL(HDPL.DVKD_FK,100007)  AS MA,   \n " +
			" 		HUYCT.NGAYHUY AS NGAYXUAT, ISNULL(HDPL_SP.DIENGIAI,'') + N'- (HỦY HÓA ĐƠN KHÁC)' AS SPTEN,   \n " +
			"		ISNULL(HDPL_SP.DONVITINH,'') AS DVT,    \n " +
			" 		 (-1)* HDPL_SP.SOLUONG AS SOLUONG, (-1) * ISNULL(HDPL_SP.SOLUONG*HDPL_SP.DONGIA,'0') AS THANHTIEN,   \n " +
			 		  
			" 		'0' AS , ''  AS MKT_SP, '3' AS LOAIHD,    \n " +
			" 		'0' AS DVDL, '0' as TRONGLUONG_QC \n " +
			 		 
			" 		FROM ERP_HOADONPHELIEU HDPL    \n " +
			"  		INNER JOIN ERP_HUYCHUNGTUBANHANG HUYCT ON HUYCT.SOCHUNGTU=HDPL.PK_SEQ  \n " +
			" 		INNER JOIN ERP_HOADONPHELIEU_SANPHAM HDPL_SP ON HDPL.PK_SEQ=HDPL_SP.HOADONPHELIEU_FK   \n " +
			" 		LEFT JOIN ERP_TRUNGTAMDOANHTHU TTDT ON HDPL.TRUNGTAMDOANHTHU_FK=TTDT.PK_SEQ   \n " +
			" 		WHERE  HUYCT.LOAICHUNGTU IN ('HOADONKHAC') AND 	    HUYCT.huykhackyketoan='1'  \n " +
			" 		and HUYCT.TRANGTHAI='1'  \n " +
			" 		AND  HUYCT.ngayhuy >= '" + tungay + "' AND HUYCT.ngayhuy <= '" + denngay + "' \n " +

			" 		UNION all    \n " +

			" 		SELECT  'HDGG' AS TYPE ,   \n " +
			" 		ISNULL(GGHB.DVKD_FK, 100007) AS MA1,   \n " +
			" 		ISNULL(GGHB.DVKD_FK, 100007) AS MA,    \n " +
			" 		'1' AS LSP, GGHB.NGAYHOADON AS NGAYXUAT, GGHB.DIENGIAI AS SPTEN,    \n " +
			"		'' AS DVT, '0' AS SOLUONG,     \n " +
			"		CASE WHEN GGHB.TIENTE_FK = '100000' " +
			"		THEN gghd.SOTIENTANGGIAM " +
			"		ELSE (gghd.SOTIENTANGGIAM* ISNULL(GGHB.TIGIA,0) ) END AS THANHTIEN,    \n " +
			  		
			"  		'0' AS , ''  AS MKT_SP,   \n " +
			" 		'10' AS LOAIHD , '0' AS DVDL,   \n " +
			" 		'0' as TRONGLUONG_QC  \n " +
			 		
			"  		FROM  ERP_GIAMGIAHANGBAN GGHB    \n " +
			" 		INNER JOIN    \n " +
			" 		(    \n " +
			" 			SELECT GIAMGIA_FK, SUM(SOTIENTANGGIAM) AS TIENTG    \n " +
			" 			FROM ERP_GIAMGIAHANGBAN_HOADON    \n " +
			" 			GROUP BY GIAMGIA_FK    \n " +
			" 		) GGHB_HD ON GGHB.PK_SEQ = GGHB_HD.GIAMGIA_FK    \n " +
			" 		LEFT JOIN ERP_GIAMGIAHANGBAN_HOADON gghd on gghb.pk_seq= gghd.giamgia_fk    \n " + 
			" 		WHERE GGHB.TRANGTHAI NOT IN (0, 2)  \n " +
			" 		AND GGHB.NGAYHOADON >= '" + tungay + "' AND GGHB.NGAYHOADON <= '" + denngay + "'   \n " +

			" 		UNION all   \n " +
			 		
			" 		SELECT  'HUY-HDGG' AS TYPE ,  \n " +
			" 		ISNULL(GGHB.DVKD_FK, 100007) AS MA1, \n " +
			" 		ISNULL(GGHB.DVKD_FK, 100007) AS MA,   \n " +
			" 		'1' AS LSP, GGHB.NGAYHOADON AS NGAYXUAT, GGHB.DIENGIAI +N'- HỦY HÓA ĐƠN GIẢM TĂNG GIÁ HÀNG BÁN' AS SPTEN,   \n " +
			"		'' AS DVT, 0 AS SOLUONG,  \n " +
			"		(-1)*  CASE WHEN GGHB.TIENTE_FK = '100000' THEN gghd.SOTIENTANGGIAM ELSE (gghd.SOTIENTANGGIAM* ISNULL(GGHB.TIGIA,0) ) END AS THANHTIEN, \n " +  
			  		
			"  		'0' AS , ''  AS MKT_SP,  \n " +
			" 		'3' AS LOAIHD, '0' AS DVDL,  \n " +
			" 		'0' as TRONGLUONG_QC \n " +
			 		
			"  		FROM  ERP_GIAMGIAHANGBAN GGHB   \n " +
			"  		INNER JOIN ERP_HUYCHUNGTUBANHANG HUYCT ON HUYCT.SOCHUNGTU=GGHB.PK_SEQ  \n " +
			" 		INNER JOIN   \n " +
			" 		(   \n " +
			" 			SELECT GIAMGIA_FK, SUM(SOTIENTANGGIAM) AS TIENTG   \n " +
			" 			FROM ERP_GIAMGIAHANGBAN_HOADON   \n " +
			" 			GROUP BY GIAMGIA_FK   \n " +
			" 		) GGHB_HD ON GGHB.PK_SEQ = GGHB_HD.GIAMGIA_FK   \n " +
			" 		LEFT JOIN ERP_GIAMGIAHANGBAN_HOADON gghd on gghb.pk_seq= gghd.giamgia_fk    \n " +
			" 		WHERE  HUYCT.LOAICHUNGTU IN ('TANGGIAMGIA') AND  HUYCT.huykhackyketoan='1' AND HUYCT.TRANGTHAI='1'  \n " +
			" 		and HUYCT.ngayhuy >= '" + tungay + "' AND HUYCT.ngayhuy <= '" + denngay + "' \n " +
			" 	) A WHERE 1 = 1   \n " +
			"  )B WHERE 1 = 1 AND B.DVKD = 'L' \n " +
			" )DATA  \n " +
			" GROUP BY DATA.THANG \n " +
			" ORDER BY DATA.THANG \n " ;
		System.out.println(query);

		data = prepareData(data, query);
		return data;
	}	
	
/*	public String[][] PL_SalesVolumn_LA_Budget_Details(){
		String[][] data;
		try{
		String query =  " select count(A.MA) as NUM " +
						" from " +
						" ( " +
						" 	SELECT distinct LNS_DBSP.MA " +
						" 	FROM ERP_LAPNGANSACH_DUBAO LNS_DB  " +
						" 	INNER JOIN ERP_LAPNGANSACH_DUBAOSANPHAM LNS_DBSP ON LNS_DBSP.DUBAO_FK = LNS_DB.PK_SEQ  " +
						" 	WHERE	LNS_DB.LAPNGANSACH_FK =  '" + this.Id + "'  AND LNS_DB.CONGTY_FK =  '" + this.ctyId + "'  " +
						" 			and LNS_DBSP.MA in ( select DISTINCT MA from ERP_SANPHAM where DVKD_FK = '100000' and LOAI = '100005' )    " +
						
						" 	GROUP BY LNS_DB.DVKD_FK, LNS_DBSP.THANG, LNS_DBSP.MA " +
						" ) " +
						" A ";
		
		System.out.println("4." + query);
		ResultSet rs = this.db.get(query);
		
		rs.next();
		
		int count = rs.getInt("NUM");
		System.out.println("count: " + count);
		rs.close();
			
		query = "SELECT LNS_DBSP.MA AS MKTID, LNS_DBSP.MA as MA, LNS_DB.DVKD_FK, LNS_DBSP.THANG, ISNULL(SUM(LNS_DBSP.DUKIENBAN), 0) AS NUM  " +
				" FROM ERP_LAPNGANSACH_DUBAO LNS_DB  " +
				" INNER JOIN ERP_LAPNGANSACH_DUBAOSANPHAM LNS_DBSP ON LNS_DBSP.DUBAO_FK = LNS_DB.PK_SEQ  " +
				" WHERE	LNS_DB.LAPNGANSACH_FK =  '" + this.Id + "'  AND LNS_DB.CONGTY_FK =  '100005'  " +
				" 		and LNS_DBSP.MA in ( select DISTINCT MA from ERP_SANPHAM where DVKD_FK = '100000' and LOAI = '100005' )    " +
				" GROUP BY LNS_DB.DVKD_FK, LNS_DBSP.THANG, LNS_DBSP.MA " +
				" ORDER BY LNS_DBSP.MA";
		
		System.out.println("5.PL_SalesVolumn_LA_Details: " + query);
		data = prepareData_MKT(count, query);
		return data;
		
		}catch(java.sql.SQLException e){ return null;}
	
	}

	public String[][] PL_SalesVolumn_PA_Details(){
		String[][] data;
		try{
		String query = 	"SELECT COUNT(A.MA) AS NUM FROM " +
						"(SELECT DISTINCT MKT.MA " +
						"FROM ERP_LAPNGANSACH_DUBAO LNS_DB " +
						"INNER JOIN ERP_LAPNGANSACH_DUBAOSANPHAM LNS_DBSP ON LNS_DBSP.DUBAO_FK = LNS_DB.PK_SEQ " +
						"INNER JOIN ERP_SANPHAM SP ON SP.MA = LNS_DBSP.MA AND SP.DVKD_FK = LNS_DB.DVKD_FK " +
						"INNER JOIN ERP_MAKETOAN MKT ON MKT.PK_SEQ = SP.MAKETOAN_FK " +
						"WHERE	LNS_DB.LAPNGANSACH_FK = " + this.Id + " AND LNS_DB.DVKD_FK = '100004' AND SP.LOAI = '100005' " +
						"AND LNS_DB.CONGTY_FK = " + this.ctyId + "  " +
						"GROUP BY LNS_DB.DVKD_FK,  MKT.PK_SEQ, MKT.MA, LNS_DBSP.THANG " +
						")A ";
		
		System.out.println("COUNT-PA:" + query);
		ResultSet rs = this.db.get(query);
		
		rs.next();
		
		int count = rs.getInt("NUM");
		System.out.println("count: " + count);
		rs.close();
		
		query = 	"SELECT MKT.PK_SEQ AS MKTID, MKT.MA, LNS_DB.DVKD_FK, LNS_DBSP.THANG, ISNULL(SUM(LNS_DBSP.DUKIENBAN), 0) AS NUM " +
					"FROM ERP_LAPNGANSACH_DUBAO LNS_DB " +
					"INNER JOIN ERP_LAPNGANSACH_DUBAOSANPHAM LNS_DBSP ON LNS_DBSP.DUBAO_FK = LNS_DB.PK_SEQ " +
					"INNER JOIN ERP_SANPHAM SP ON SP.MA = LNS_DBSP.MA AND SP.DVKD_FK = LNS_DB.DVKD_FK " +
					"INNER JOIN ERP_MAKETOAN MKT ON MKT.PK_SEQ = SP.MAKETOAN_FK " +
					"WHERE	LNS_DB.LAPNGANSACH_FK = " + this.Id + " AND LNS_DB.DVKD_FK = '100004' AND SP.LOAI = '100005' " +
					"AND LNS_DB.CONGTY_FK = " + this.ctyId + "  " +
					"GROUP BY LNS_DB.DVKD_FK,  MKT.PK_SEQ, MKT.MA, LNS_DBSP.THANG " +
					"ORDER BY MKT.MA ";
		
		data = prepareData_MKT(count, query);
		return data;
		
		}catch(java.sql.SQLException e){ return null;}
	
	}
*/
	
	public String[] PL_SalesVolumn_LA_Budget_Detail(String MA){
		String[] data = new String[13];

		String query = 	
						"SELECT	DUBAOSP.THANG, SUM(DUBAOSP.DUKIENBAN) AS NUM \n " +			 
						"FROM ERP_LAPNGANSACH_DUBAO DUBAO   \n " +
						"INNER JOIN ERP_LAPNGANSACH_DUBAOSANPHAM DUBAOSP on DUBAO.PK_SEQ = DUBAOSP.DUBAO_FK \n " +  
						"INNER JOIN ERP_KHACHHANG KH ON KH.PK_SEQ = DUBAOSP.KHACHHANG_FK   \n " +
						"INNER JOIN ERP_SANPHAM SP ON SP.MA = DUBAOSP.MA   \n " +
						"WHERE DUBAO.LAPNGANSACH_FK = " + this.Id + " AND DUBAOSP.NAM = " + this.nam + " \n " +
						"AND DUBAO.CONGTY_FK = " + this.ctyId + " AND DUBAOSP.MA = N'" + MA + "'  \n " +
						"AND DUBAO.DVKD_FK = 100000 \n " +
						"GROUP BY THANG" ;
		System.out.println(query);

		data = prepareData(data, query);
		return data;
	}	

	public String[] PL_SalesVolumn_PA_Budget_Detail(String MA){
		String[] data = new String[13];

		String query = 	
						"SELECT	DUBAOSP.THANG, SUM(DUBAOSP.DUKIENBAN) AS NUM \n " +			 
						"FROM ERP_LAPNGANSACH_DUBAO DUBAO   \n " +
						"INNER JOIN ERP_LAPNGANSACH_DUBAOSANPHAM DUBAOSP on DUBAO.PK_SEQ = DUBAOSP.DUBAO_FK \n " +  
						"INNER JOIN ERP_KHACHHANG KH ON KH.PK_SEQ = DUBAOSP.KHACHHANG_FK   \n " +
						"INNER JOIN ERP_SANPHAM SP ON SP.MA = DUBAOSP.MA   \n " +
						"WHERE DUBAO.LAPNGANSACH_FK = " + this.Id + " AND DUBAOSP.NAM = " + this.nam + " \n " +
						"AND DUBAO.CONGTY_FK = " + this.ctyId + " AND DUBAOSP.MA = N'" + MA + "'  \n " +
						"AND DUBAO.DVKD_FK = 100004 \n " +
						"GROUP BY THANG" ;
		System.out.println(query);

		data = prepareData(data, query);
		return data;
	}	

	public void PL_Sales_Budget_Detail(){
		
		int count = 0;
		String sql = 	"SELECT DB.MA, DB.THANG, SUM(DB.DUBAO) AS SOLUONG, SUM(DB.THANHTIEN) AS THANHTIEN, \n " +
						"CASE WHEN SUM(DB.DUBAO) > 0 THEN SUM(THANHTIEN)/ SUM(DB.DUBAO) \n " +
						"ELSE 0 END AS SELLINGPRICE \n " +
						"FROM \n " +
						"( \n " +
						"	SELECT DISTINCT SP.MA,  DUBAOSP.THANG, \n " +
						"	SUM(ISNULL(DUBAOSP.DUKIENBAN*BG_SP.GIABAN, 0)) AS THANHTIEN, SUM(ISNULL(DUBAOSP.DUKIENBAN,0)) AS DUBAO \n " +
						"	FROM ERP_LAPNGANSACH_DUBAO DUBAO    \n " +
						"	INNER JOIN ERP_LAPNGANSACH_DUBAOSANPHAM DUBAOSP on DUBAO.PK_SEQ = DUBAOSP.DUBAO_FK \n " +  
						"	INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = DUBAOSP.SANPHAM_FK    \n " +
						"	INNER JOIN ERP_LNSBGBAN_SANPHAM BG_SP ON BG_SP.SANPHAM_FK = DUBAOSP.SANPHAM_FK \n " + 
						"						AND BG_SP.THANG = DUBAOSP.THANG  \n " +
						"	INNER JOIN ERP_LNSBANGGIABAN BG ON BG.PK_SEQ = BG_SP.BGBAN_FK AND BG.NAM = DUBAOSP.NAM AND BG.KENH_FK = DUBAO.KBH_FK \n " +
						"	WHERE DUBAO.LAPNGANSACH_FK =  " + this.Id + "  \n " +
						"	AND DUBAO.CONGTY_FK =  " + this.ctyId + "  \n " +  
//						"	AND DUBAOSP.DUKIENBAN > 0 AND BG_SP.GIABAN > 0 \n " +  
						"	GROUP BY DUBAOSP.THANG, SP.MA \n " +  
						")DB \n " +  
						"GROUP BY DB.MA, DB.THANG \n " ;

		String query = 	
						"SELECT COUNT(DISTINCT MA) AS NUM \n " +
						"FROM \n " +
						"( \n " +
						sql +
						
						")DATA "; 
		ResultSet rs = this.db.get(query);
		try{
			if(rs != null){
				rs.next();
				count = rs.getInt("NUM");
				System.out.println("Count:" + count);
				rs.close();
			}
			
		}catch(java.sql.SQLException e){}
		System.out.println(query);

		if(count > 0){
			query = sql + 
					"ORDER BY DB.MA, DB.THANG ";

			this.volume = new String[count][13];
			this.amount = new String[count][13];
			
			prepareData_Sales(count, query);

		}else{
			this.volume = new String[1][13];
			this.amount = new String[1][13];
			this.volume[0][0] = "";
			this.amount[0][0] = "";

			for(int i = 1; i <= 12; i++){
				this.volume[0][i] = "0";
				this.amount[0][i] = "0";
			}
		}
	}	


	public void PL_Sales_PA_Budget_Detail(){
		
		int count = 0;
		String sql = 	"SELECT DB.MA, DB.THANG, SUM(DB.DUBAO*QUYDOI.QUYDOI) AS TRONGLUONG, SUM(THANHTIEN) AS THANHTIEN, \n " +
						"CASE WHEN SUM(DB.DUBAO*QUYDOI.QUYDOI) > 0 THEN SUM(THANHTIEN)/ SUM(DB.DUBAO*QUYDOI.QUYDOI) \n " +
						"ELSE 0 END AS SELLINGPRICE \n " +
						"FROM \n " +
						"( \n " +
						"	SELECT DISTINCT DUBAOSP.MA, DUBAOSP.DONVI, DUBAOSP.THANG, \n " +
						"	SUM(DUBAOSP.DUKIENBAN*BG_SP.GIABAN) AS THANHTIEN, SUM(DUBAOSP.DUKIENBAN) AS DUBAO \n " +
						"	FROM ERP_LAPNGANSACH_DUBAO DUBAO    \n " +
						"	INNER JOIN ERP_LAPNGANSACH_DUBAOSANPHAM DUBAOSP on DUBAO.PK_SEQ = DUBAOSP.DUBAO_FK \n " +  
						"	INNER JOIN ERP_SANPHAM SP ON SP.MA = DUBAOSP.MA    \n " +
						"	INNER JOIN ERP_KHACHHANG KH ON KH.PK_SEQ = DUBAOSP.KHACHHANG_FK \n " +
						"	INNER JOIN ERP_LNSBGBAN_SANPHAM BG_SP ON BG_SP.KHACHHANG_FK = KH.PK_SEQ AND BG_SP.MA = DUBAOSP.MA \n " + 
						"						AND BG_SP.DONVI = DUBAOSP.DONVI AND BG_SP.THANG = DUBAOSP.THANG AND BG_SP.NAM = DUBAOSP.NAM  \n " +
						"	WHERE DUBAO.LAPNGANSACH_FK =  " + this.Id + "  \n " +
						"	AND DUBAO.CONGTY_FK =  " + this.ctyId + "  \n " +  
						"	AND DUBAO.DVKD_FK = 100004   \n " +  
						"	AND DUBAOSP.DUKIENBAN > 0 AND BG_SP.GIABAN > 0 \n " +  
						"	GROUP BY DUBAOSP.THANG, DUBAOSP.MA, DUBAOSP.DONVI, DUBAOSP.KHACHHANG_FK \n " +  
						")DB \n " +  
						"INNER JOIN \n " +  
						"( \n " +  
						"	SELECT DISTINCT MA, DONVI, \n " +  
						"	CASE WHEN UPPER(DONVI) = 'KG' THEN 1 \n " +  
						"	ELSE  \n " +  
						"		CASE WHEN QC1.SOLUONG2 IS NOT NULL THEN CONVERT(FLOAT, QC1.SOLUONG2) \n " +  
						"		ELSE CASE WHEN QC2.SOLUONG1 IS NOT NULL THEN CONVERT(FLOAT, QC2.SOLUONG1) \n " +  
						"		END END  \n " +  
						"	 END AS QUYDOI \n " +  
							 
						"	FROM ERP_LAPNGANSACH_DUBAOSANPHAM DBSP \n " +  
						
						"	LEFT JOIN QUYCACH QC1 ON QC1.DVDL2_FK = 100003 AND QC1.  IN (SELECT PK_SEQ FROM ERP_SANPHAM WHERE MA = DBSP.MA AND DVDL_FK = (SELECT PK_SEQ FROM DONVIDOLUONG WHERE DONVI = DBSP.DONVI)) \n " +  
						"	LEFT JOIN QUYCACH QC2 ON QC2.DVDL1_FK = 100003 AND QC2.  IN (SELECT PK_SEQ FROM ERP_SANPHAM WHERE MA = DBSP.MA AND DVDL_FK = (SELECT PK_SEQ FROM DONVIDOLUONG WHERE DONVI = DBSP.DONVI)) \n " +  
						"	WHERE CONVERT(FLOAT, QC1.SOLUONG2) IS NOT NULL OR CONVERT(FLOAT, QC2.SOLUONG1) IS NOT NULL \n " +  
						")QUYDOI ON QUYDOI.MA = DB.MA AND QUYDOI.DONVI = DB.DONVI \n " +
						"GROUP BY DB.MA, DB.THANG \n " ;

		String query = 	
						"SELECT COUNT(DISTINCT MA) AS NUM \n " +
						"FROM \n " +
						"( \n " +
						sql +
						
						")DATA "; 
		ResultSet rs = this.db.get(query);
		try{
			if(rs != null){
				if(rs.next()){
					count = rs.getInt("NUM");
					System.out.println("Count:" + count);
					rs.close();
				}
			}
			
		}catch(java.sql.SQLException e){}
		System.out.println(query);

		if(count > 0){
			query = sql + 
					"ORDER BY DB.MA, DB.THANG ";

			this.volume_PA = new String[count][13];
			this.amount_PA = new String[count][13];
			
			prepareData_Sales_PA(count, query);

		}else{
			this.volume_PA = new String[1][13];
			this.amount_PA = new String[1][13];
			this.volume_PA[0][0] = "";
			this.amount_PA[0][0] = "";

			for(int i = 1; i <= 12; i++){
				this.volume_PA[0][i] = "0";
				this.amount_PA[0][i] = "0";
			}
		}
	}	

	public String[] PL_SalesAmount_PA_Budget_Detail(String MA){
		String[] data = new String[13];

		String query = 	
					"SELECT	DUBAOSP.THANG, SUM(DUBAOSP.DUKIENBAN) AS NUM \n " +			 
					"FROM ERP_LAPNGANSACH_DUBAO DUBAO   \n " +
					"INNER JOIN ERP_LAPNGANSACH_DUBAOSANPHAM DUBAOSP on DUBAO.PK_SEQ = DUBAOSP.DUBAO_FK \n " +  
					"INNER JOIN ERP_KHACHHANG KH ON KH.PK_SEQ = DUBAOSP.KHACHHANG_FK   \n " +
					"INNER JOIN ERP_SANPHAM SP ON SP.MA = DUBAOSP.MA   \n " +
					"WHERE DUBAO.LAPNGANSACH_FK = " + this.Id + " AND DUBAOSP.NAM = " + this.nam + " \n " +
					"AND DUBAO.CONGTY_FK = " + this.ctyId + " AND DUBAOSP.MA = N'" + MA + "'  \n " +
					"AND DUBAO.DVKD_FK = 100004 \n " +
					"GROUP BY THANG" ;
		System.out.println(query);

		data = prepareData(data, query);
		return data;
	}	


	public String[] PL_OtherExpenses_Budget(){
		String[] data = new String[13];

		String query = 	
					"SELECT THANG, SUM(NUM) AS NUM FROM ( \n " +
						"SELECT SUBSTRING(NH.NGAYNHAN, 6, 2) AS THANG, SUM(NH_SP.DONGIAVIET*NH_SP.SOLUONGNHAN) AS NUM  \n " +
						"FROM ERP_NHANHANG NH  \n " +
						"INNER JOIN ERP_NHANHANG_SANPHAM NH_SP ON NH_SP.NHANHANG_FK = NH.PK_SEQ  \n " +
						"WHERE NH.TRANGTHAI IN (1, 2) AND NH_SP.CHIPHI_FK IN (100118, 100119) \n " +
						"AND SUBSTRING(NH.NGAYNHAN, 1, 4) = '" + this.year + "' \n " +
						"GROUP BY SUBSTRING(NH.NGAYNHAN, 6, 2)  \n " +

						"UNION ALL  \n " +
						"SELECT SUBSTRING(MH.NGAYMUA, 6, 2) AS THANG, SUM(MH_SP.DONGIAVIET*MH_SP.SOLUONG) AS NUM \n " +
						"FROM ERP_MUAHANG MH \n " +
						"INNER JOIN ERP_MUAHANG_SP MH_SP ON MH_SP.MUAHANG_FK = MH.PK_SEQ \n " +
 

						"WHERE YEAR(MH.NGAYMUA) = '" + this.year + "' AND MH.TRANGTHAI IN (1, 2) \n " +
						"AND ISNULL(CHIPHI_FK, 0) IN (100118, 100119)  \n " +
						"GROUP BY MH.NGAYMUA, ISNULL(CHIPHI_FK, 0), MH.PK_SEQ \n " +
					")DATA \n " +
					"GROUP BY DATA.THANG \n " +
					"ORDER BY DATA.THANG \n ";


		data = prepareData(data, query);
		return data;
	}	
	

	public String[] PL_OtherExpenses_LA_Budget(){
		String[] data = new String[13];

		String query = 	
					"SELECT THANG, SUM(NUM) AS NUM FROM ( \n " +
						"SELECT SUBSTRING(NH.NGAYNHAN, 6, 2) AS THANG, SUM(NH_SP.DONGIAVIET*NH_SP.SOLUONGNHAN) AS NUM  \n " +
						"FROM ERP_NHANHANG NH  \n " +
						"INNER JOIN ERP_NHANHANG_SANPHAM NH_SP ON NH_SP.NHANHANG_FK = NH.PK_SEQ  \n " +
						"WHERE NH.TRANGTHAI IN (1, 2) AND NH_SP.CHIPHI_FK IN (100118) \n " +
						"AND SUBSTRING(NH.NGAYNHAN, 1, 4) = '" + this.year + "' \n " +
						"GROUP BY SUBSTRING(NH.NGAYNHAN, 6, 2)  \n " +

						"UNION ALL  \n " +
						"SELECT SUBSTRING(MH.NGAYMUA, 6, 2) AS THANG, SUM(MH_SP.DONGIAVIET*MH_SP.SOLUONG) AS NUM \n " +
						"FROM ERP_MUAHANG MH \n " +
						"INNER JOIN ERP_MUAHANG_SP MH_SP ON MH_SP.MUAHANG_FK = MH.PK_SEQ \n " +
						 

						"WHERE YEAR(MH.NGAYMUA) = '" + this.year + "' AND MH.TRANGTHAI IN (1, 2) \n " +
						"AND ISNULL(CHIPHI_FK, 0) IN (100118)  \n " +
						"GROUP BY MH.NGAYMUA, ISNULL(CHIPHI_FK, 0), MH.PK_SEQ \n " +
					")DATA \n " +
					"GROUP BY DATA.THANG \n " +
					"ORDER BY DATA.THANG \n ";


		data = prepareData(data, query);
		return data;
	}	


	public String[] PL_OtherExpenses_PA_Budget(){
		String[] data = new String[13];

		String query = 	
					"SELECT THANG, SUM(NUM) AS NUM FROM ( \n " +
						"SELECT SUBSTRING(NH.NGAYNHAN, 6, 2) AS THANG, SUM(NH_SP.DONGIAVIET*NH_SP.SOLUONGNHAN) AS NUM  \n " +
						"FROM ERP_NHANHANG NH  \n " +
						"INNER JOIN ERP_NHANHANG_SANPHAM NH_SP ON NH_SP.NHANHANG_FK = NH.PK_SEQ  \n " +
						"WHERE NH.TRANGTHAI IN (1, 2) AND NH_SP.CHIPHI_FK IN (100119) \n " +
						"AND SUBSTRING(NH.NGAYNHAN, 1, 4) = '" + this.year + "' \n " +
						"GROUP BY SUBSTRING(NH.NGAYNHAN, 6, 2)  \n " +

						"UNION ALL  \n " +
						"SELECT SUBSTRING(MH.NGAYMUA, 6, 2) AS THANG, SUM(MH_SP.DONGIAVIET*MH_SP.SOLUONG) AS NUM \n " +
						"FROM ERP_MUAHANG MH \n " +
						"INNER JOIN ERP_MUAHANG_SP MH_SP ON MH_SP.MUAHANG_FK = MH.PK_SEQ \n " +
 

						"WHERE YEAR(MH.NGAYMUA) = '" + this.year + "' AND MH.TRANGTHAI IN (1, 2) \n " +
						"AND ISNULL(CHIPHI_FK, 0) IN (100119)  \n " +
						"GROUP BY MH.NGAYMUA, ISNULL(CHIPHI_FK, 0), MH.PK_SEQ \n " +
					")DATA \n " +
					"GROUP BY DATA.THANG \n " +
					"ORDER BY DATA.THANG \n ";


		data = prepareData(data, query);
		return data;
	}	



	public String[] PL_DirectLabour_LA(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE TTCP_FK = 100414 AND NS.LAPNGANSACH_FK = " + this.Id + " " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_DirectLabour_PA(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100415 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_WorkerSalary(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100540 " +
						"ORDER BY THANG ASC" ;
		
		System.out.println("---PL_WorkerSalary: " + query);
		data = prepareData(data, query);
		return data;
	}

	public String[] PL_WorkerSalary_LA(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 1004 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_WorkerSalary_PA(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100413 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_OvertimeWorkers(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100541 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_OvertimeWorkers_LA(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100410 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_OvertimeWorkers_PA(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100411" +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_AmortizedWorkers(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100542" +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_AmortizedWorkers_LA(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100408 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_AmortizedWorkers_PA(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100409 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_SocialInsurance(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100543 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_SocialInsurance_LA(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100406 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_SocialInsurance_PA(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100407 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_HealthInsurance(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100544 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_HealthInsurance_LA(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100404 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_HealthInsurance_PA(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100405 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_ProductionOverhead(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100561 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_ProductionOverhead_LA(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100423 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_ProductionOverhead_PA(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100424 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_WorkerAllowances(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100562 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_WorkerAllowances_LA(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100416 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_WorkerAllowances_PA(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100418 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_WorkerBonus(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100563 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_WorkerBonus_LA(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100417 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_WorkerBonus_PA(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100425 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_DepreciationBuilding_Production(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100548 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_DepreciationBuilding_Production_LA(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100419 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_DepreciationBuilding_Production_PA(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100420 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_DepreciationMachine(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100564 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_DepreciationMachine_LA(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100421 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_DepreciationMachine_PA(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100422 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_DepreciationMotorVehicle_Production(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100565 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_DepreciationMotorVehicle_Production_LA(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100426 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_DepreciationMotorVehicle_Production_PA(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100427 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_RentalCharge_Machinery(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100566 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_RentalCharge_Machinery_LA(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100428 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_RentalCharge_Machinery_PA(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100429 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_ElectricityWater(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100567 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_ElectricityWater_LA(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100430 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_ElectricityWater_PA(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100431 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_FactoryExpenses(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100568 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_FactoryExpenses_LA(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100432 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_FactoryExpenses_PA(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100433 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_Printing_Stationery(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100569 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_Printing_Stationery_LA(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100434 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_Printing_Stationery_PA(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100435 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_Workers_Meals(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100570 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_Workers_Meals_LA(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100436 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_Workers_Meals_PA(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100437 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_Upkeep_Motorvehicle(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100571 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_Upkeep_Motorvehicle_LA(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100438 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_Upkeep_Motorvehicle_PA(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100439 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_Upkeep_Machinery(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100572 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_Upkeep_Machinery_LA(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100440 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_Upkeep_Machinery_PA(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100441 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_TrainingCost(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100573 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_TrainingCost_LA(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100442 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_TrainingCost_PA(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100443 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_Welfare(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100574 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_Welfare_LA(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100444 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_Welfare_PA(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100445 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_InsuranceA(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100575 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_InsuranceA_LA(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100446 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_InsuranceA_PA(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100447 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_UpkeepFactory(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100576 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_UpkeepFactory_LA(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100448 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_UpkeepFactory_PA(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100449 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_Workers_Uniform(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100577 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
		
	public String[] PL_Workers_Uniform_LA(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100450 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_Workers_Uniform_PA(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100451 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_LandRental(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100578 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_LandRental_LA(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100624 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_LandRental_PA(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100625 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_Tool_Accessories(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100579 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_Tool_Accessories_LA(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100454 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_Tool_Accessories_PA(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100455 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_Oil_Machine(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100580 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_Oil_Machine_LA(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100456 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_Oil_Machine_PA(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100457 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_IndirectLabourSalary(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100581 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_IndirectLabourSalary_LA(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100458 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_IndirectLabourSalary_PA(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100459 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_Indirect_13th_LabourSalary(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100582 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_Indirect_13th_LabourSalary_LA(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100460 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_Indirect_13th_LabourSalary_PA(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100461 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_Indirect_LabourBonus(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100583 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	public String[] PL_Indirect_LabourBonus_LA(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100462 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_Indirect_LabourBonus_PA(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100463 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_SellingExpenses(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100589 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_SellingExpenses_LA(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100480 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_SellingExpenses_PA(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100481 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_Transportation(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100584 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_Transportation_LA(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100482 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}
	
	
	public String[] PL_Transportation_PA(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100483 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}

	public String[] PL_Commission(){
		String[] data = new String[13];
	
		String query = 	"SELECT THANG, NGANSACH AS NUM " +
						"FROM ERP_TRUNGTAMCHIPHI_NGANSACH NS " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NS.TTCP_FK " +
						"WHERE NS.LAPNGANSACH_FK = " + this.Id + "  AND  TTCP_FK = 100585 " +
						"ORDER BY THANG ASC" ;

		data = prepareData(data, query);
		return data;
	}


	public void setMonth(String month) {

		this.month = month;
	}

	public String getMonth() {
		if(this.month.length() >0){
			return this.month;	
		}else{
			return this.getDateTime().substring(5, 7);
		}
		
	}
	
	public void setYear(String year) {

		this.year = year;
	}

	public String getYear() {
		if(this.year.length()>0){
			return this.year;	
		}else{
			return this.getDateTime().substring(0, 4);
		}
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	private double ptPhanboDoanhSo(String xuong){
		double temp = 0f;
		if(xuong.equals("LA")){
			temp = 0.7;
		}else{
			temp = (1 - 0.7);
		}
		return temp;
	}
	
	public String[] getSellingPriceHashTable(String MA){
		return (String[]) this.sellingprice_ht.get(MA);
	}
	
	public String[] getAmountHashTable(String MA){
		return (String[]) this.amount_ht.get(MA);
	}

	
	public String[] PL_WIP_Opening_LA(){
		String[] data_thisYear =  new String[13];
		for(int i = 0; i < 13; i++){
			data_thisYear[i] = "0";
		}
		return data_thisYear;
	}
	
	public String[] PL_WIP_Closing_LA(){
		String[] data_thisYear =  new String[13];
		
		for(int i = 0; i < 13; i++){
			data_thisYear[i] = "0";
		}
		
		return data_thisYear;
	}

	public String[] PL_FinishGoods_Opening_LA(){
		String[] data_thisYear =  new String[13];
		for(int i = 0; i < 13; i++){
			data_thisYear[i] = "0";
		}
		
		return data_thisYear;
	}
	
	public String[] PL_FinishGoods_Closing_LA(){
		String[] data_thisYear =  new String[13];
		
		for(int i = 0; i < 13; i++){
			data_thisYear[i] = "0";
		}
		
		return data_thisYear;
	}

	public String[] PL_WIP_Opening_PA(){
		String[] data_thisYear =  new String[13];
		for(int i = 0; i < 13; i++){
			data_thisYear[i] = "0";
		}
		
		return data_thisYear;
	}
	
	public String[] PL_WIP_Closing_PA(){
		String[] data_thisYear =  new String[13];
		for(int i = 0; i < 13; i++){
			data_thisYear[i] = "0";
		}
		
		return data_thisYear;
	}

	public String[] PL_FinishGoods_Opening_PA(){
		String[] data_thisYear =  new String[13];
		for(int i = 0; i < 13; i++){
			data_thisYear[i] = "0";
		}
		return data_thisYear;
	}
	
	public String[] PL_FinishGoods_Closing_PA(){
		String[] data_thisYear =  new String[13];
		for(int i = 0; i < 13; i++){
			data_thisYear[i] = "0";
		}
		return data_thisYear;
	}

	public String[] PL_CostOfMaterial_LA(){
		String[] data_thisYear =  new String[13];
		for(int i = 0; i < 13; i++){
			data_thisYear[i] = "0";
		}
		
		return data_thisYear;
	}

	public String[] PL_CostOfMaterial_PA(){
		String[] data_thisYear =  new String[13];
		for(int i = 0; i < 13; i++){
			data_thisYear[i] = "0";
		}
		
		return data_thisYear;
	}

	public String[] PL_ManufacturingCost_LA(){
		String[] data_thisYear =  new String[13];
		for(int i = 0; i < 13; i++){
			data_thisYear[i] = "0";
		}
		return data_thisYear;
	}

	public String[] PL_ManufacturingCost_PA(){
		String[] data_thisYear =  new String[13];
		
		for(int i = 0; i < 13; i++){
			data_thisYear[i] = "0";
		}
		
		return data_thisYear;
	}

	public void SetCellFormula_Month(int i, Cell cell, String item)
	{
		if(i == 0){
		   cell.setFormula("=IF($" + item + "<>0,SUMIF('P&L Lamination'!$A$4:$A$1000,'Summary P&L'!$" + item + ",'P&L Lamination'!B$4:B$1000)+SUMIF('P&L Paper Core'!$A$4:$A$1000,'Summary P&L'!$" + item + ",'P&L Paper Core'!B$4:B$1000),0)");
		}
		   
		if(i == 1){
		   cell.setFormula("=IF($" + item + "<>0,SUMIF('P&L Lamination'!$A$4:$A$1000,'Summary P&L'!$" + item + ",'P&L Lamination'!C$4:C$1000)+SUMIF('P&L Paper Core'!$A$4:$A$1000,'Summary P&L'!$" + item + ",'P&L Paper Core'!C$4:C$1000),0)");
		}

		if(i == 2){
		   cell.setFormula("=IF($" + item + "<>0,SUMIF('P&L Lamination'!$A$4:$A$1000,'Summary P&L'!$" + item + ",'P&L Lamination'!D$4:D$1000)+SUMIF('P&L Paper Core'!$A$4:$A$1000,'Summary P&L'!$" + item + ",'P&L Paper Core'!D$4:D$1000),0)");
		}

		if(i == 3){
		   cell.setFormula("=IF($" + item + "<>0,SUMIF('P&L Lamination'!$A$4:$A$1000,'Summary P&L'!$" + item + ",'P&L Lamination'!E$4:E$1000)+SUMIF('P&L Paper Core'!$A$4:$A$1000,'Summary P&L'!$" + item + ",'P&L Paper Core'!E$4:E$1000),0)");
		}

		if(i == 4){
		   cell.setFormula("=IF($" + item + "<>0,SUMIF('P&L Lamination'!$A$4:$A$1000,'Summary P&L'!$" + item + ",'P&L Lamination'!F$4:F$1000)+SUMIF('P&L Paper Core'!$A$4:$A$1000,'Summary P&L'!$" + item + ",'P&L Paper Core'!F$4:F$1000),0)");
		}

		if(i == 5){
		   cell.setFormula("=IF($" + item + "<>0,SUMIF('P&L Lamination'!$A$4:$A$1000,'Summary P&L'!$" + item + ",'P&L Lamination'!G$4:G$1000)+SUMIF('P&L Paper Core'!$A$4:$A$1000,'Summary P&L'!$" + item + ",'P&L Paper Core'!G$4:G$1000),0)");
		}

		if(i == 6){
		   cell.setFormula("=IF($" + item + "<>0,SUMIF('P&L Lamination'!$A$4:$A$1000,'Summary P&L'!$" + item + ",'P&L Lamination'!H$4:H$1000)+SUMIF('P&L Paper Core'!$A$4:$A$1000,'Summary P&L'!$" + item + ",'P&L Paper Core'!H$4:H$1000),0)");
		}

		if(i == 7){
		   cell.setFormula("=IF($" + item + "<>0,SUMIF('P&L Lamination'!$A$4:$A$1000,'Summary P&L'!$" + item + ",'P&L Lamination'!I$4:I$1000)+SUMIF('P&L Paper Core'!$A$4:$A$1000,'Summary P&L'!$" + item + ",'P&L Paper Core'!I$4:I$1000),0)");
		}

		if(i == 8){
		   cell.setFormula("=IF($" + item + "<>0,SUMIF('P&L Lamination'!$A$4:$A$1000,'Summary P&L'!$" + item + ",'P&L Lamination'!J$4:J$1000)+SUMIF('P&L Paper Core'!$A$4:$A$1000,'Summary P&L'!$" + item + ",'P&L Paper Core'!J$4:J$1000),0)");
		}

		if(i == 9){
		   cell.setFormula("=IF($" + item + "<>0,SUMIF('P&L Lamination'!$A$4:$A$1000,'Summary P&L'!$" + item + ",'P&L Lamination'!K$4:K$1000)+SUMIF('P&L Paper Core'!$A$4:$A$1000,'Summary P&L'!$" + item + ",'P&L Paper Core'!K$4:K$1000),0)");
		}
		
		if(i == 10){
		   cell.setFormula("=IF($" + item + "<>0,SUMIF('P&L Lamination'!$A$4:$A$1000,'Summary P&L'!$" + item + ",'P&L Lamination'!L$4:L$1000)+SUMIF('P&L Paper Core'!$A$4:$A$1000,'Summary P&L'!$" + item + ",'P&L Paper Core'!L$4:L$1000),0)");
		}

		if(i == 11){
		   cell.setFormula("=IF($" + item + "<>0,SUMIF('P&L Lamination'!$A$4:$A$1000,'Summary P&L'!$" + item + ",'P&L Lamination'!M$4:M$1000)+SUMIF('P&L Paper Core'!$A$4:$A$1000,'Summary P&L'!$" + item + ",'P&L Paper Core'!M$4:M$1000),0)");
		}

	}	
	
	public void SetCellFormula_Total(int i, Cell cell, String item)
	{
		   cell.setFormula("=IF($" + item + "<>0,SUMIF('P&L Lamination'!$A$4:$A$1000,'Summary P&L'!$" + item + ",'P&L Lamination'!M$4:M$1000)+SUMIF('P&L Paper Core'!$A$4:$A$1000,'Summary P&L'!$" + item + ",'P&L Paper Core'!M$4:M$1000), 0)");
		
	}
	
	
	public void setValue_Part_1_Budget(Cells cells, Cell cell, int k, Style style)
	{

		   Font font;
		   int m = k;
		   cell = cells.getCell("A" + m);		   
		   this.row_SALES_VOLUME = m;

		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 16, "DOANH SỐ (SỐ LƯỢNG)");		   		   
		   cells.merge(3, 0 , 4, 0);
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   font = style.getFont();
		   font.setUnderline(1);
		   style.setFont(font);
		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   String[][] data = null; 
	   	   this.PL_Sales_Budget_Detail();
		   		   
		   m++;
		   System.out.println("Volume.length = " + this.volume.length);
		   for (int n = m; n < this.volume.length + m; n++){
			   cell = cells.getCell("A" + n);		   
			   ReportAPI.getCellStyle(cell,Color.BLACK, false, 12 , this.volume[n - m][0]);		   		   
			   cells.merge(3, 0 , 4, 0);
		   
			   style = cell.getStyle();
			   style.setHAlignment(TextAlignmentType.LEFT);
			   font = style.getFont();
			   font.setUnderline(1);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   	   cell.setStyle(style);

		   	   for(int i = 1; i <= 13; i++){
		   		   
	   			   cell = cells.getCell(n-1, i);
		   		   
		   		   style = cell.getStyle();
			   
		   		   style.setHAlignment(TextAlignmentType.RIGHT);
		   		   style.setNumber(4);
		   		   font = style.getFont();
		   		   font.setBold(false);
		   		   font.setSize(14);
		   		   font.setColor(Color.BLACK);
		   		   style.setFont(font);
		   		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);
		   		   cell.setStyle(style);
			   
		   		   if(i <= 12){
		   			   cell.setValue(Double.parseDouble(this.volume[n-m][i]));
		   			   
		   		   }else{			   
					 
					   cell.setFormula("=SUM(B" + n + ":M" + n + ")");
					 
		   		   }
			   
		   	   }
		   }

		   m = m + this.volume.length;
		   
		   for(int i = 1; i <= 13; i++){
			   
			   cell = cells.getCell(row_SALES_VOLUME - 1, i);
		  
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(4);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(14);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   

			   if(i <= 12){
				   if (i == 1) cell.setFormula("=SUM(B" + (this.row_SALES_VOLUME + 1) + ":B" + (m-1) + ")");
				   if (i == 2) cell.setFormula("=SUM(C" + (this.row_SALES_VOLUME + 1)  + ":C" + (m-1) + ")");
				   if (i == 3) cell.setFormula("=SUM(D" + (this.row_SALES_VOLUME + 1)  + ":D" + (m-1) + ")");
				   if (i == 4) cell.setFormula("=SUM(E" + (this.row_SALES_VOLUME + 1)  + ":E" + (m-1) + ")");
				   if (i == 5) cell.setFormula("=SUM(F" + (this.row_SALES_VOLUME + 1)  + ":F" + (m-1) + ")");
				   if (i == 6) cell.setFormula("=SUM(G" + (this.row_SALES_VOLUME + 1)  + ":G" + (m-1) + ")");
				   if (i == 7) cell.setFormula("=SUM(H" + (this.row_SALES_VOLUME + 1)  + ":H" + (m-1) + ")");
				   if (i == 8) cell.setFormula("=SUM(I" + (this.row_SALES_VOLUME + 1)  + ":I" + (m-1) + ")");
				   if (i == 9) cell.setFormula("=SUM(J" + (this.row_SALES_VOLUME + 1)  + ":J" + (m-1) + ")");
				   if (i == 10) cell.setFormula("=SUM(K" + (this.row_SALES_VOLUME + 1)  + ":K" + (m-1) + ")");
				   if (i == 11) cell.setFormula("=SUM(L" + (this.row_SALES_VOLUME + 1)  + ":L" + (m-1) + ")");
				   if (i == 12) cell.setFormula("=SUM(M" + (this.row_SALES_VOLUME + 1)  + ":M" + (m-1) + ")");
			   }else{
				   cell.setFormula("=SUM(B" + this.row_SALES_VOLUME + ":M" + this.row_SALES_VOLUME + ")");
			   }
			   
		   }

		   m = m + 9;
		   this.row_TOTAL_AMOUNT = m;
		   
		   cell = cells.getCell("A" + m);		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 16, "TỔNG DOANH SỐ (VND)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   font = style.getFont();
		   font.setUnderline(1);
		   style.setFont(font);
		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);

		   m++;
		   for (int n = m; n < this.amount.length + m; n++){
			   cell = cells.getCell("A" + n);		   
			   ReportAPI.getCellStyle(cell,Color.BLACK, false, 12, this.amount[n-m][0]);		   		   
		   
			   style = cell.getStyle();
			   style.setHAlignment(TextAlignmentType.LEFT);
			   font = style.getFont();
			   font.setUnderline(1);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   	   cell.setStyle(style);
		   	
		   	   
		   	   for(int i = 1; i <= 13; i++){

	   			   cell = cells.getCell(n-1, i);

		   		   style = cell.getStyle();
			   
		   		   style.setHAlignment(TextAlignmentType.RIGHT);
		   		   style.setNumber(3);
		   		   font = style.getFont();
		   		   font.setBold(false);
		   		   font.setSize(14);
		   		   font.setColor(Color.BLACK);
		   		   style.setFont(font);
		   		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);
		   		   cell.setStyle(style);
			   
		   		   if(i <= 12 ){
		   			   cell.setValue(Double.parseDouble(this.amount[n-m][i]));
		   		   }else{			   
					   cell.setFormula("=SUM(B" + n + ":M" + n + ")");
		   		   }
			   
		   	   }
		   }

		   m = m + this.amount.length;
		   for(int i = 1; i <= 13; i++){
			   
			   cell = cells.getCell(this.row_TOTAL_AMOUNT - 1, i );

			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(14);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i <= 12){
				   if (i == 1) cell.setFormula("=SUM(B" + (this.row_TOTAL_AMOUNT + 1) + ":B" + (m - 1) +")");
				   if (i == 2) cell.setFormula("=SUM(C" + (this.row_TOTAL_AMOUNT + 1) + ":C" + (m - 1) +")");
				   if (i == 3) cell.setFormula("=SUM(D" + (this.row_TOTAL_AMOUNT + 1) + ":D" + (m - 1) +")");
				   if (i == 4) cell.setFormula("=SUM(E" + (this.row_TOTAL_AMOUNT + 1) + ":E" + (m - 1) +")");
				   if (i == 5) cell.setFormula("=SUM(F" + (this.row_TOTAL_AMOUNT + 1) + ":F" + (m - 1) +")");
				   if (i == 6) cell.setFormula("=SUM(G" + (this.row_TOTAL_AMOUNT + 1) + ":G" + (m - 1) +")");
				   if (i == 7) cell.setFormula("=SUM(H" + (this.row_TOTAL_AMOUNT + 1) + ":H" + (m - 1) +")");
				   if (i == 8) cell.setFormula("=SUM(I" + (this.row_TOTAL_AMOUNT + 1) + ":I" + (m - 1) +")");
				   if (i == 9) cell.setFormula("=SUM(J" + (this.row_TOTAL_AMOUNT + 1) + ":J" + (m - 1) +")");
				   if (i == 10) cell.setFormula("=SUM(K" + (this.row_TOTAL_AMOUNT + 1) + ":K" + (m - 1) +")");
				   if (i == 11) cell.setFormula("=SUM(L" + (this.row_TOTAL_AMOUNT + 1) + ":L" + (m - 1) +")");
				   if (i == 12) cell.setFormula("=SUM(M" + (this.row_TOTAL_AMOUNT + 1) + ":M" + (m - 1) +")");
			   }else{
				   cell.setFormula("=SUM(B" + (this.row_TOTAL_AMOUNT) + ":M" + (this.row_TOTAL_AMOUNT) + ")");
			   }
			   
		   }


		   m = m + 2;
		   this.row_MATERIALS_CONSUMED = m;
		   cell = cells.getCell("A" + m);		   
		   ReportAPI.getCellStyle(cell,Color.MAGENTA, true, 12, "CHI PHÍ NGUYÊN VẬT LIỆU");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   font = style.getFont();
		   style.setFont(font);
		   cell.setStyle(style);
		   		   
		   m++;
		   cell = cells.getCell("A" + m);		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Nguyên liệu");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
		   style.setFont(font);
		   cell.setStyle(style);
		   
		   String[] data_1Row = this.PL_MaterialConsume_NL_Budget();
		   
		   for(int i = 0; i <= 12 ; i++){			   
			   
   			   cell = cells.getCell(m-1, i + 1);

			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);			   
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data_1Row[i]));
			   }else{			   
				   cell.setFormula("=SUM(B" + m + ":M" + m + ")");
				   
			   }

		   }

		   m++;
		   cell = cells.getCell("A" + m);		   
		   ReportAPI.getCellStyle(cell, Color.BLACK, true, 12, "Vật liệu phụ");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
		   style.setFont(font);
		   cell.setStyle(style);
		   
		   data_1Row = this.PL_MaterialConsume_VLP_Budget();
		   for(int i = 0; i <= 12; i++){
   			   cell = cells.getCell(m-1, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   cell.setStyle(style);

			   if(i < 12 ){
				   cell.setValue(Double.parseDouble(data_1Row[i]));
			   }else{			   
				   cell.setFormula("=SUM(B" + m + ":M" + m + ")");
				   
			   }
			   
		   }

		   m++;
		   cell = cells.getCell("A" + m);		   
		   ReportAPI.getCellStyle(cell, Color.BLACK, true, 12, "Bao bì");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();

		   style.setFont(font);
		   
		   cell.setStyle(style);
		   
		   data_1Row = this.PL_MaterialConsume_BB_Budget();
		   
		   
		   for(int i = 0; i <= 12; i++){
   			   cell = cells.getCell(m-1, i + 1);

   			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data_1Row[i]));
			   }else{			   
				   cell.setFormula("=SUM(B" + m + ":M" + m + ")");
				   
			   }
			   
		   }

//Bây giờ mới xử lý tổng số MATERIAL CONSUMED
		   for(int i = 0; i <= 12; i++){
			   cell = cells.getCell(row_MATERIALS_CONSUMED - 1, i + 1);

			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   if (i == 0) cell.setFormula("=SUM(B" + (row_MATERIALS_CONSUMED + 1) + ":B" + m + ")");
				   if (i == 1) cell.setFormula("=SUM(C" + (row_MATERIALS_CONSUMED + 1) + ":C" + m + ")");
				   if (i == 2) cell.setFormula("=SUM(D" + (row_MATERIALS_CONSUMED + 1) + ":D" + m + ")");
				   if (i == 3) cell.setFormula("=SUM(E" + (row_MATERIALS_CONSUMED + 1) + ":E" + m + ")");
				   if (i == 4) cell.setFormula("=SUM(F" + (row_MATERIALS_CONSUMED + 1) + ":F" + m + ")");
				   if (i == 5) cell.setFormula("=SUM(G" + (row_MATERIALS_CONSUMED + 1) + ":G" + m + ")");
				   if (i == 6) cell.setFormula("=SUM(H" + (row_MATERIALS_CONSUMED + 1) + ":H" + m + ")");
				   if (i == 7) cell.setFormula("=SUM(I" + (row_MATERIALS_CONSUMED + 1) + ":I" + m + ")");
				   if (i == 8) cell.setFormula("=SUM(J" + (row_MATERIALS_CONSUMED + 1) + ":J" + m + ")");
				   if (i == 9) cell.setFormula("=SUM(K" + (row_MATERIALS_CONSUMED + 1) + ":K" + m + ")");
				   if (i == 10) cell.setFormula("=SUM(L" + (row_MATERIALS_CONSUMED + 1) + ":L" + m + ")");
				   if (i == 11) cell.setFormula("=SUM(M" + (row_MATERIALS_CONSUMED + 1) + ":M" + m + ")");
				   if (i == 12) cell.setFormula("=SUM(N" + (row_MATERIALS_CONSUMED + 1) + ":N" + m + ")");
			   }else{			   
				   cell.setFormula("=SUM(B" + row_MATERIALS_CONSUMED + ":M" + row_MATERIALS_CONSUMED + ")");
				   
			   }
			   
		   }
		   
		   m = m + 2;
		   this.row_PRODUCTION_OVERHEAD_LA = m;
		   
		   cell = cells.getCell("A" + m);		   
		   ReportAPI.getCellStyle(cell, Color.MAGENTA, true, 12, "CHI PHÍ SẢN XUẤT");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);

		   font = style.getFont();
		   style.setFont(font);
		   cell.setStyle(style);

		   m++;
		   cell = cells.getCell("A" + m);		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Phòng đảm bảo chất lượng");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();

		   style.setFont(font);
		   cell.setStyle(style);
		   
		   data_1Row = this.PL_DBCL_Budget();
		   
		   for(int i = 0; i <= 12; i++){
   			   cell = cells.getCell(m-1, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data_1Row[i]));
			   }else{			   
				   cell.setFormula("=SUM(B" + m + ":M" + m + ")");
				   
			   }

		   }
		   
		   m++;
		   cell = cells.getCell("A" + m);		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Phòng kiểm tra chất lượng");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
		   style.setFont(font);
		   cell.setStyle(style);
		   
		   data_1Row = this.PL_KTCL_Budget();
		   
		   for(int i = 0; i <= 12; i++){
   			   cell = cells.getCell(m-1, i + 1);
   			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);

			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data_1Row[i]));
			   }else{			   
				   cell.setFormula("=SUM(B" + m + ":M" + m + ")");
			   }
			   
		   }

		   m++;
		   cell = cells.getCell("A" + m);		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Kho kế hoạch");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
		   style.setFont(font);
		   cell.setStyle(style);
		   
		   data_1Row = this.PL_KKH_Budget();
		   
		   for(int i = 0; i <= 12; i++){
   			   cell = cells.getCell(m-1, i + 1);

   			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data_1Row[i]));
			   }else{			   
				   cell.setFormula("=SUM(B" + m + ":M" + m + ")");
				   
			   }
			   
		   }

		   m++;
		   cell = cells.getCell("A" + m);		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Kho xuất nhập khẩu");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
		   style.setFont(font);
		   cell.setStyle(style);
		   
		   data_1Row = this.PL_KXNK_Budget();
		   
		   for(int i = 0; i <= 12; i++){
   			   cell = cells.getCell(m-1, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data_1Row[i]));
			   }else{			   
				   cell.setFormula("=SUM(B" + m + ":M" + m + ")");
			   }
			   
		   }

		   m++;
		   cell = cells.getCell("A" + m);		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Quản lý sản xuất");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();

		   style.setFont(font);
		   cell.setStyle(style);
		   
		   data_1Row = this.PL_QLSX_Budget();
		   		   
		   for(int i = 0; i <= 12; i++){
   			   cell = cells.getCell(m-1, i + 1);

			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data_1Row[i]));
			   }else{			   
				   cell.setFormula("=SUM(B" + m + ":M" + m + ")");
				   
			   }
			   
		   }

		   m++;
		   cell = cells.getCell("A" + m);		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Phân xưởng thuốc viên, thuốc nước");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();

		   style.setFont(font);
		   cell.setStyle(style);
		   
		   data_1Row = this.PL_TVTN_Budget();
		   
		   for(int i = 0; i <= 12; i++){		   
   			   cell = cells.getCell(m-1, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data_1Row[i]));
			   }else{			   
				   cell.setFormula("=SUM(B" + m + ":M" + m + ")");
				   
			   }

		   }

		   m++;
		   cell = cells.getCell("A" + m);		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Phân xưởng thuốc mỡ, nang mềm");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
		   style.setFont(font);
		   cell.setStyle(style);
		   
		   data_1Row = this.PL_TMNM_Budget();

		   for(int i = 0; i <= 12; i++){
   			   cell = cells.getCell(m-1, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);

			   cell.setStyle(style);
			   
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data_1Row[i]));
			   }else{			   
				   cell.setFormula("=SUM(B" + m + ":M" + m + ")");
				   
			   }

		   }

		   m++;
		   cell = cells.getCell("A" + m);		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Phân xưởng tây y");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();

		   style.setFont(font);
		   cell.setStyle(style);
		   
		   data_1Row = this.PL_TY_Budget();

		   for(int i = 0; i <= 12; i++){
   			   cell = cells.getCell(m-1, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data_1Row[i]));
			   }else{			   
				   cell.setFormula("=SUM(B" + m + ":M" + m + ")");
				   
			   }

		   }

		   m++;
		   cell = cells.getCell("A" + m);		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Phân xưởng đóng gói");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();

		   style.setFont(font);
		   cell.setStyle(style);
		   
		   data_1Row = this.PL_DG_Budget();
		   
		   for(int i = 0; i <= 12; i++){
   			   cell = cells.getCell(m-1, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);

			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data_1Row[i]));
			   }else{			   
				   cell.setFormula("=SUM(B" + m + ":M" + m + ")");
				   
			   }

		   }

		   m++;
		   cell = cells.getCell("A" + m);		   
	   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Phân xưởng sản xuất chung");		   		   

		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();

		   style.setFont(font);
		   cell.setStyle(style);
		   
		   data_1Row = this.PL_SXC_Budget();
		   
		   for(int i = 0; i <= 12; i++){
   			   cell = cells.getCell(m-1, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);

			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data_1Row[i]));
			   }else{			   
				   cell.setFormula("=SUM(B" + m + ":M" + m + ")");
				   
			   }

		   }
		   
		   for(int i = 0; i <= 12 ; i++){
			   cell = cells.getCell(row_PRODUCTION_OVERHEAD_LA-1, i + 1);

			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12 ){
				   if (i == 0) cell.setFormula("=SUM(B" + (row_PRODUCTION_OVERHEAD_LA + 1)+ ":B" + m + ")");
				   if (i == 1) cell.setFormula("=SUM(C" + (row_PRODUCTION_OVERHEAD_LA + 1) + ":C" + m + ")");
				   if (i == 2) cell.setFormula("=SUM(D" + (row_PRODUCTION_OVERHEAD_LA + 1) + ":D" + m + ")");
				   if (i == 3) cell.setFormula("=SUM(E" + (row_PRODUCTION_OVERHEAD_LA + 1) + ":E" + m + ")");
				   if (i == 4) cell.setFormula("=SUM(F" + (row_PRODUCTION_OVERHEAD_LA + 1) + ":F" + m + ")");
				   if (i == 5) cell.setFormula("=SUM(G" + (row_PRODUCTION_OVERHEAD_LA + 1) + ":G" + m + ")");
				   if (i == 6) cell.setFormula("=SUM(H" + (row_PRODUCTION_OVERHEAD_LA + 1) + ":H" + m + ")");
				   if (i == 7) cell.setFormula("=SUM(I" + (row_PRODUCTION_OVERHEAD_LA + 1) + ":I" + m + ")");
				   if (i == 8) cell.setFormula("=SUM(J" + (row_PRODUCTION_OVERHEAD_LA + 1) + ":J" + m + ")");
				   if (i == 9) cell.setFormula("=SUM(K" + (row_PRODUCTION_OVERHEAD_LA + 1) + ":K" + m + ")");
				   if (i == 10) cell.setFormula("=SUM(L" + (row_PRODUCTION_OVERHEAD_LA + 1) + ":L" + m + ")");
				   if (i == 11) cell.setFormula("=SUM(M" + (row_PRODUCTION_OVERHEAD_LA + 1) + ":M" + m + ")");
			   }else{
				   cell.setFormula("=SUM(B" + row_PRODUCTION_OVERHEAD_LA + ":M" + row_PRODUCTION_OVERHEAD_LA + ")");

			   }
		   }
		   this.row_LA = m; 
	}

	
	private String[][] prepareData_MKT(int count, String query)
	{
		String[][] data = new String[count][13];
		ResultSet rs = this.db.get(query);
		
		try 
		{
			int j = -1;
			String maSP = "";
			while(rs.next())
			{
				
				if(!maSP.equals(rs.getString("MA")))
				{
					maSP = rs.getString("MA");
					j++;
				}
				
				data[j][0] = maSP;
				data[j][rs.getInt("THANG")] = "" + Math.round(Double.parseDouble(rs.getString("NUM")));
				
			}
			
			rs.close();
			System.out.println("Chieu dai của array: " + j);
		} 
		catch (Exception e) {}
		
		return data;
	}
	
	public void closeDB(){
		try{
	
			if(this.namlist != null) this.namlist.close();
			this.db.shutDown();
		}catch(java.sql.SQLException e){}
	}
}
