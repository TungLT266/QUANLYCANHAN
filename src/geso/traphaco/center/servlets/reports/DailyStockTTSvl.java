package geso.traphaco.center.servlets.reports;

import geso.traphaco.center.beans.stockintransit.IStockintransit;
import geso.traphaco.center.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Font;
import com.aspose.cells.Style;

import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;
import com.itextpdf.text.Document;

public class DailyStockTTSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
  
    public DailyStockTTSvl() {
        super();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		Utility util = new Utility();
		HttpSession session = request.getSession();
		String userTen = (String) session.getAttribute("userTen");
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		
		IStockintransit obj = new Stockintransit();	
		obj.settungay("");
		obj.setdenngay("");
		obj.setMsg("");
		obj.setnppId("");
		obj.setuserId(userId);
		obj.init();
		
		session.setAttribute("obj", obj);
		session.setAttribute("userTen", userTen);
		String nextJSP = "/TraphacoHYERP/pages/Center/rp_DailyStock_center.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		OutputStream out = response.getOutputStream();
		IStockintransit obj = new Stockintransit();	
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		Utility util = new Utility();
		boolean bfasle = true;
		try
		{	
			obj.setuserTen((String) session.getAttribute("userTen")!=null?
					(String) session.getAttribute("userTen"):"");
			
			obj.settungay(util.antiSQLInspection(request.getParameter("Sdays"))!=null?
					util.antiSQLInspection(request.getParameter("Sdays")):"");
			
			obj.setdenngay(util.antiSQLInspection(request.getParameter("Edays"))!=null?
					util.antiSQLInspection(request.getParameter("Edays")):"");
			
			obj.setuserId(util.antiSQLInspection(request.getParameter("userId"))!=null?
				util.antiSQLInspection(request.getParameter("userId")):"");
			
			obj.setnppId(util.antiSQLInspection(request.getParameter("nppId"))!=null?
					util.antiSQLInspection(request.getParameter("nppId")):"");
			
			obj.setkenhId(util.antiSQLInspection(request.getParameter("kenhId"))!=null?
					util.antiSQLInspection(request.getParameter("kenhId")):"");
			
			obj.setdvkdId(util.antiSQLInspection(request.getParameter("dvkdId"))!=null?
					util.antiSQLInspection(request.getParameter("dvkdId")):"");
			
			obj.setvungId(util.antiSQLInspection(request.getParameter("vungId"))!=null?
					util.antiSQLInspection(request.getParameter("vungId")):"");
			
			obj.setnhanhangId(util.antiSQLInspection(request.getParameter("nhanhangId"))!=null?
					util.antiSQLInspection(request.getParameter("nhanhangId")):"");
			
			obj.setkhuvucId(util.antiSQLInspection(request.getParameter("khuvucId"))!=null?
					util.antiSQLInspection(request.getParameter("khuvucId")):"");
			
			obj.setchungloaiId(util.antiSQLInspection(request.getParameter("chungloaiId"))!=null?
					util.antiSQLInspection(request.getParameter("chungloaiId")):"");
			
			String tuthang = request.getParameter("tuthang");
			if(tuthang == null)
				tuthang = "";
			
			obj.setFromMonth( (tuthang.length()>1? tuthang:"0"+tuthang ));
		
			
			String denthang = request.getParameter("denthang");
			if(denthang == null)
				denthang = "";
			obj.setToMonth((denthang.length()>1? denthang:"0"+denthang ));
		
			
			String tunam = request.getParameter("tunam");
			if(tunam == null)
				tunam = "";
			obj.setFromYear(tunam);
			
			String dennam = request.getParameter("dennam");
			if(dennam == null)
				dennam = "";
			obj.setToYear(dennam);
			
			
			obj.init();
			session.setAttribute("obj", obj);
			
			String action = util.antiSQLInspection(request.getParameter("action"));
			if(action.equals("taomoi"))
			{
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "Attachment; filename=TonKhoThang.xlsm");
		        CreatePivotTable(out, response, request, obj, bfasle);
			} else if(action.equals("toPdf"))
			{
				String query = getQuery(obj);
				response.setContentType("application/pdf");
				Document document = new Document();
				ServletOutputStream outstream = response.getOutputStream();
				try
				{
					obj.TonKhoHangNgayPdf(document, outstream, obj,query);
				} catch (SQLException e)
				{
					e.printStackTrace();
				}
			}
			response.sendRedirect("/TraphacoHYERP/pages/Center/rp_DailyStock_center.jsp");
			
		}
	    catch (Exception ex)
	    {
	        ex.printStackTrace();
	        response.setContentType("text/html");
	        PrintWriter writer = new PrintWriter(out);
	
	        writer.println("<html>");
	        writer.println("<head>");
	        writer.println("<title>sorry</title>");
	        writer.println("</head>");
	        writer.println("<body>");
	        writer.println("<h1>Xin loi, khong the tao pivot table...</h1>");
	        ex.printStackTrace(writer);
	        writer.println("</body>");
	        writer.println("</html>");
	        writer.close();
	    }
	}
	private void CreatePivotTable(OutputStream out,HttpServletResponse response,HttpServletRequest request,IStockintransit obj, boolean bfasle) throws IOException
    {    
		String chuoi=getServletContext().getInitParameter("path") + "\\TonKhoHangNgayTT.xlsm";
		
		FileInputStream fstream = new FileInputStream(chuoi);
		Workbook workbook = new Workbook();

		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
	     
	    CreateStaticHeader(workbook, obj.gettungay(),obj.getdenngay(),obj.getuserTen(), obj);
	     //Buoc3 
	    CreateStaticData(workbook,obj,bfasle);
	    workbook.save(out);
	    fstream.close();
   }
	
	private void CreateStaticHeader(Workbook workbook, String tungay, String denngay, String UserName, IStockintransit obj) 
	{
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		worksheet.setName("Sheet1");

		Cells cells = worksheet.getCells();

		cells.setRowHeight(0, 20.0f);
		Cell cell = cells.getCell("A1");
	    cell.setValue("TỒN KHO THÁNG");   	
	    
	    cells.setRowHeight(2, 20.0f);
	    cell = cells.getCell("A3"); 
	    getCellStyle(workbook,"A3",Color.NAVY,true,9);	    
	    cell.setValue("Từ tháng: " + obj.getFromMonth());
	    
	    
	    cells.setRowHeight(3, 20.0f);
	    cell = cells.getCell("B3"); getCellStyle(workbook,"B3",Color.NAVY,true,9);	        
	    cell.setValue("Đến tháng: " + obj.getToMonth());    
	    
	    cells.setRowHeight(4, 20.0f);
	    cell = cells.getCell("A4");getCellStyle(workbook,"A4",Color.NAVY,true,9);
	    cell.setValue("Ngày báo cáo: " + this.getDateTime());
	    
	    cells.setRowHeight(5, 20.0f);
	    cell = cells.getCell("A5");getCellStyle(workbook,"A5",Color.NAVY,true,9);
	    cell.setValue("Được tạo bởi:  " + UserName);
			    
	    cell = cells.getCell("EA1"); cell.setValue("KenhBanHang");
	    cell = cells.getCell("EB1"); cell.setValue("DonViKinhDoanh");
	    cell = cells.getCell("EC1"); cell.setValue("ChiNhanh");
	    cell = cells.getCell("ED1"); cell.setValue("Vung");
	    cell = cells.getCell("EE1"); cell.setValue("MaCN/DT");
	    cell = cells.getCell("EF1"); cell.setValue("CN/DT"); //6
	    cell = cells.getCell("EG1"); cell.setValue("NhanHang");
	    cell = cells.getCell("EH1"); cell.setValue("ChungLoai");
	    cell = cells.getCell("EI1"); cell.setValue("MaSanPham");
	    cell = cells.getCell("EJ1"); cell.setValue("TenSanPham"); //9
	    cell = cells.getCell("EK1"); cell.setValue("Kho");  //10
	    cell = cells.getCell("EL1"); cell.setValue("SoLuong");	//11  
	    cell = cells.getCell("EM1"); cell.setValue("SoLuongKien");	//11
	    cell = cells.getCell("EN1"); cell.setValue("SoTien");
	    cell = cells.getCell("EO1"); cell.setValue("SoLo");
	    cell = cells.getCell("EP1"); cell.setValue("Date");
	}
	
	private void CreateStaticData(Workbook workbook, IStockintransit obj, boolean bfasle) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
	    dbutils db = new dbutils();

	    String sql =getQuery(obj);
		 ResultSet rs = db.get(sql);
		 int i = 2;

		if(rs != null)
		{
			try 
			{
				for(int j = 1; j <= 7; j++)
					cells.setRowHeight(j, 14.0f);
				Cell cell = null;
				
				while(rs.next())
				{ 
					String Channel = rs.getString("Chanel");
					String Region = rs.getString("Region");
					String Area = rs.getString("Area");				
					String Distributor = rs.getString("Distributor");
					String DistributorCode = rs.getString("Distcode");			
					String Sku = rs.getString("SKU");
					String SkuCode = rs.getString("SKUcode");
					String Warehouse = rs.getString("Warehouse");
					String Date= rs.getString("Date");
					
					double Quantity=rs.getDouble("Quatity");	
					double Piece = rs.getDouble("Piece");
					double Amount =rs.getDouble("Amount");
					
					String BusinessUnit = "HPC";
					if(rs.getString("BusinessUnit") != null)
						BusinessUnit = rs.getString("BusinessUnit");
					
					String Brands = rs.getString("Brands")==null?"":rs.getString("Brands");
					String Category = rs.getString("Category")==null?"":rs.getString("Category");
					
					String SoLo=rs.getString("SoLo")==null?"":rs.getString("SoLo");
					
					int qc1=rs.getInt("qc1");
					int qc2=rs.getInt("qc2");
					double slThg=0;
					try
					{
						slThg=Piece/qc1/qc2;
					}catch(Exception e)
					{
						e.printStackTrace();
					}
					cell = cells.getCell("EA" + Integer.toString(i)); cell.setValue(Channel); 
					cell = cells.getCell("EB" + Integer.toString(i)); cell.setValue(BusinessUnit); 
					cell = cells.getCell("EC" + Integer.toString(i)); cell.setValue(Region); 
					cell = cells.getCell("ED" + Integer.toString(i)); cell.setValue(Area);   
					cell = cells.getCell("EE" + Integer.toString(i)); cell.setValue(DistributorCode); 
					cell = cells.getCell("EF" + Integer.toString(i)); cell.setValue(Distributor); 
					cell = cells.getCell("EG" + Integer.toString(i)); cell.setValue(Brands); 
					cell = cells.getCell("EH" + Integer.toString(i)); cell.setValue(Category); 
					cell = cells.getCell("EI" + Integer.toString(i)); cell.setValue(SkuCode);
					cell = cells.getCell("EJ" + Integer.toString(i)); cell.setValue(Sku); 
					cell = cells.getCell("EK" + Integer.toString(i)); cell.setValue(Warehouse); 
					cell = cells.getCell("EL" + Integer.toString(i)); cell.setValue(Piece); 
					cell = cells.getCell("EM" + Integer.toString(i)); cell.setValue(slThg); 
					cell = cells.getCell("EN" + Integer.toString(i)); cell.setValue(Amount); 
					cell = cells.getCell("EO" + Integer.toString(i)); cell.setValue(SoLo);	
					cell = cells.getCell("EP" + Integer.toString(i)); cell.setValue(Date);	
					i++;
				}
			if(rs!= null)
				rs.close();
			db.shutDown();
	 	    bfasle=true;
			}
			catch (Exception e)
			{
				bfasle=false;
				e.printStackTrace(); 
				System.out.println("Error : loi daily stock : " +e.toString());
			}
		}
		else
		{
			bfasle=false;
		}
	}
	
	
	public String getQuery(IStockintransit obj)
	{
		
		String sql=  " SELECT ISNULL(D.TEN, 'CHUA XAC DINH') AS CHANEL, H.TEN AS REGION,  F.TEN AS AREA,  "+
					 " E.TEN AS DISTRIBUTOR, E.SITECODE AS DISTCODE,   B.TEN AS SKU, B.MA AS SKUCODE, CAST(A.NAM AS VARCHAR(4)) + '-' + CASE WHEN A.THANG<10 THEN '0' END  + CAST( A.THANG AS VARCHAR(2)) AS DATE, "+
					 " C.TEN AS WAREHOUSE, G.TEN AS PROVINCE,  I.DONVIKINHDOANH AS BUSINESSUNIT, K.TEN AS BRANDS,  "+
					 " J.TEN AS CATEGORY, A.SOLUONG AS PIECE,  CASE WHEN A.SOLUONG IS NULL THEN 0 ELSE A.SOLUONG/ISNULL(QC.SOLUONG1,1) END AS QUATITY, "+
					 " CASE WHEN A.SOLUONG * NPPK.GIAMUA IS NULL THEN 0 ELSE A.SOLUONG * NPPK.GIAMUA END AS AMOUNT,QC.SOLUONG1 AS QC1,QC.SOLUONG2 AS QC2,A.SOLO "+
					 " FROM ( SELECT * FROM TONKHOTHANG_CHITIET  WHERE ((THANG >='" + obj.getFromMonth() + "' and Nam >='"+obj.getFromYear()+"' ) or ( Thang <= '" + obj.getToMonth() + "' and nam  <='"+obj.getToYear()+"'))  AND SOLUONG > 0 ) A  "+
					 " INNER JOIN SANPHAM B ON A.SANPHAM_FK = B.PK_SEQ INNER JOIN KHO C ON A.KHO_FK = C.PK_SEQ   "+
					 " INNER JOIN KENHBANHANG D ON A.KBH_FK = D.PK_SEQ	INNER JOIN NHAPHANPHOI E ON A.NPP_FK = E.PK_SEQ  "+
					 " INNER JOIN KHUVUC F ON E.KHUVUC_FK = F.PK_SEQ LEFT JOIN TINHTHANH G ON E.TINHTHANH_FK = G.PK_SEQ  "+
					 " INNER JOIN VUNG H ON F.VUNG_FK = H.PK_SEQ INNER JOIN DONVIKINHDOANH I ON B.DVKD_FK = I.PK_SEQ  "+
					 " LEFT JOIN CHUNGLOAI J ON B.CHUNGLOAI_FK = J.PK_SEQ LEFT JOIN NHANHANG K ON B.NHANHANG_FK = K.PK_SEQ   "+
					 " LEFT JOIN QUYCACH QC ON A.SANPHAM_FK = QC.SANPHAM_FK AND QC.DVDL2_FK=100018 and qc.dvdl1_fk=b.dvdl_fk  "+
					 " LEFT JOIN(  "+
					 " 	SELECT B.KENH_FK,C.NPP_FK  ,D.SANPHAM_FK, D.GIAMUANPP AS GIAMUA  "+
					 " 	FROM BANGGIAMUANPP B   "+
					 " 	INNER JOIN BANGGIAMUANPP_NPP C ON B.PK_SEQ=C.BANGGIAMUANPP_FK    "+
					 " 	INNER JOIN BGMUANPP_SANPHAM D ON B.PK_SEQ=D.BGMUANPP_FK  "+
					 " 	WHERE  B.TUNGAY <='" + getDate() + "' AND   "+
					 " 	B.PK_SEQ =    "+
					 " 	(     "+
					 " 	SELECT TOP(1) BG.PK_SEQ FROM BANGGIAMUANPP BG  "+
					 " 	INNER JOIN BANGGIAMUANPP_NPP BGNPP ON BG.PK_SEQ=BGNPP.BANGGIAMUANPP_FK    "+
					 " 	WHERE BG.TUNGAY <= '" + getDate() + "' AND BGNPP.NPP_FK = C.NPP_FK AND BG.KENH_FK=B.KENH_FK "+
					 " 	ORDER BY TUNGAY DESC   "+
					 " 	)  "+
					 "  "+
					 " )NPPK ON  NPPK.SANPHAM_FK=B.PK_SEQ  AND NPPK.NPP_FK =A.NPP_FK   AND A.KBH_FK=NPPK.KENH_FK "+
					 " WHERE 1=1";
	    
		geso.traphaco.center.util.Utility Uti_center = new geso.traphaco.center.util.Utility();
		
		if (obj.getkenhId().length() > 0)
			sql = sql + " and d.pk_seq ='" + obj.getkenhId() + "'";
		else
			sql = sql + " and d.pk_seq in " + Uti_center.quyen_kenh(obj.getuserId());
		
		if (obj.getvungId().length() > 0)
			sql = sql + " and h.pk_seq ='" + obj.getvungId() + "'";
		if (obj.getkhuvucId().length() > 0)
			sql = sql + " and f.pk_seq ='" + obj.getkhuvucId() + "'";
		if (obj.getdvkdId().length() > 0)
			sql = sql + " and i.PK_SEQ ='" + obj.getdvkdId() + "'";
		
		if(obj.getnppId().length() > 0)
			sql = sql + " and e.pk_seq ='" + obj.getnppId() + "'";
		else
			sql = sql + " and e.pk_seq in " + Uti_center.quyen_npp(obj.getuserId());
		
		if (obj.getnhanhangId().length() > 0)
			sql = sql + " and k.pk_seq ='" + obj.getnhanhangId() + "'";
		if (obj.getchungloaiId().length() > 0)
			sql = sql + " and j.pk_seq ='" + obj.getchungloaiId() + "'";
		
		sql  =sql + " ";
		 System.out.print("Ton kho ngay: " + sql + "\n");
		 return sql;
	}
	
	
	private void getCellStyle(Workbook workbook, String a, Color mau, Boolean dam, int size)
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    Cells cells = worksheet.getCells();
		Style style;
		Cell cell = cells.getCell(a); 
		style = cell.getStyle();
        Font font1 = new Font();
        font1.setColor(mau);
        font1.setBold(dam);
        font1.setSize(size);
        style.setFont(font1);
        cell.setStyle(style);
	}
	
	private void setAn(Workbook workbook, int i)
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    Cells cells = worksheet.getCells();
	    for(int j = 130; j <= i; j++)
	    { 
	    	cells.hideColumn(j);
	    }
		
	}
	
	private String getDate()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy :  hh-mm-ss");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	private String getPiVotName()
	{
		String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
		Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
	    String name = sdf.format(cal.getTime());
	    name = name.replaceAll("-", "");
	    name = name.replaceAll(" ", "_");
	    name = name.replaceAll(":", "");
	    return "_" + name;
	    
	}

}
