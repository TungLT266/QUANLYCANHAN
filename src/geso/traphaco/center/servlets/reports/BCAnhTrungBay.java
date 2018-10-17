package geso.traphaco.center.servlets.reports;

import geso.traphaco.center.beans.stockintransit.IStockintransit;
import geso.traphaco.center.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.BorderLineType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;
import com.aspose.cells.Picture;


public class BCAnhTrungBay extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	private String setQuery(IStockintransit obj) 
	{
		
		if(obj.getCttbId().trim().length() <=0)
			return "";
		
		

		String query =   "\n	select  cttb.SCHEME,cttb.DIENGIAI as CTTB,v.TEN as Vung, tt.TEN as tinhthanh,npp.MaFAST as nppMa,npp.TEN as NPP -- " + 
						 "\n 			,ddkd.MANHANVIEN as tdvMa, ddkd.TEN as TDV, tbh.NGAYID,kh.maFAST as khMa, kh.TEN as KhachHang,isnull(anh.NgayGioTao,anh.ngay) as NgayChup,ANHCHUP  -- " + 
						 "\n 	from KHACHHANG_ANHCHUP anh -- " + 
						 "\n 	inner join KHACHHANG_TUYENBH khtbh on anh.KHACHHANG_FK = khtbh.KHACHHANG_FK -- " + 
						 "\n 	inner join TUYENBANHANG tbh on tbh.PK_SEQ = khtbh.TBH_FK -- " + 
						 "\n 	inner join DAIDIENKINHDOANH ddkd on tbh.DDKD_FK = ddkd.PK_SEQ -- " + 
						 "\n 	inner join KHACHHANG kh on kh.PK_SEQ = anh.KHACHHANG_FK -- " + 
						 "\n 	inner join NHAPHANPHOI npp on kh.NPP_FK = npp.PK_SEQ -- " + 
						 "\n 	inner join CTTRUNGBAY cttb on anh.cttb_fk= cttb.PK_SEQ -- " + 
						 "\n 	inner join TINHTHANH tt on tt.PK_SEQ = npp.TINHTHANH_FK -- " + 
						 "\n 	inner join VUNG v on v.PK_SEQ = tt.VUNG_FK -- " +
						 "\n 	where cttb.pk_seq = "+obj.getCttbId();
		if(obj.gettungay().length() >0 )
			query += " and anh.NGAY >= '"+obj.gettungay()+"'";
		if(obj.getdenngay().length() >0 )
			query += " and anh.NGAY <= '"+obj.getdenngay()+"'";
		
		if(obj.getkhId().length() >0 )
			query += " and kh.pk_seq="+obj.getkhId() ;
		else if(obj.getDdkd().length() >0 )
			query += " and ddkd.pk_seq="+obj.getDdkd() ;
		else if(obj.getnppId().length() >0 )
			query += " and npp.pk_seq="+obj.getnppId() ;
		else if(obj.getTinhthanhid().trim().length() >0)
			query += " and tt.pk_seq="+obj.getTinhthanhid() ;
		else if(obj.getvungId().trim().length() >0)
			query += " and v.pk_seq="+obj.getTinhthanhid() ;
			
		query +="\n order by cttb.PK_SEQ,v.PK_SEQ,tt.PK_SEQ,npp.PK_SEQ,ddkd.PK_SEQ,tbh.NGAYID,kh.PK_SEQ,NgayChup "; 
			

		System.out.println("____BC anh trung bay: " + query);
		return query;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();

		Utility util = new Utility();
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);


		String view=request.getParameter("view");
		if(view == null)
			view = "";
		
		obj.setLoaiMenu(view);

		obj.setuserId(userId);
		obj.init();	

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);

		String nextJSP = "/TraphacoHYERP/pages/Center/BCAnhTrungBay.jsp";
		response.sendRedirect(nextJSP);
	}


	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		Utility  util = new Utility();

		String userId = (String) session.getAttribute("userId");
		if (userId == null)
			userId = "";
		obj.setuserId(userId);

		String userTen = (String) session.getAttribute("userTen");
		obj.setuserTen(userTen);

		
		String view=request.getParameter("view");
		if(view == null)
			view = "";
		
		obj.setLoaiMenu(view);
		
		
		
		String vungId = util.antiSQLInspection(request.getParameter("vungId"));
		if (vungId == null)
			vungId = "";
		obj.setvungId(vungId);

		String tinhthanhId = util.antiSQLInspection(request.getParameter("tinhthanhId"));
		if (tinhthanhId == null)
			tinhthanhId = "";
		obj.setTinhthanhid(tinhthanhId);

		String nppId="";
		nppId = util.antiSQLInspection(request.getParameter("nppId"));
		if (nppId == null)
			nppId = "";
		obj.setnppId(nppId);
	
		System.out.println("nppId ="  + obj.getnppId());
		
		String ddkdId = util.antiSQLInspection(request.getParameter("ddkdId"));
		if (ddkdId == null)
			ddkdId = "";
		obj.setDdkd(ddkdId);

		String khId = util.antiSQLInspection(request.getParameter("khId"));
		if (khId == null)
			khId = "";
		obj.setkhId(khId);

		String tungay = util.antiSQLInspection(request.getParameter("tungay"));
		if (tungay == null)
			tungay = "";
		obj.settungay(tungay);

		String denngay = util.antiSQLInspection(request.getParameter("denngay"));
		if (denngay == null)
			denngay = "";
		obj.setdenngay(denngay);

		String cttbId = util.antiSQLInspection(request.getParameter("cttbId"));
		if (cttbId == null)
			cttbId = "";
		obj.setCttbId(cttbId);

		String action = request.getParameter("action");
		if (action.equals("tao")) 
		{
			try 
			{
				request.setCharacterEncoding("utf-8");

				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=BCAnhTrungBay.xlsm");

				OutputStream out = response.getOutputStream();

				String query = setQuery(obj);

				CreatePivotTable(out, obj, query);
				
				
			} 
			catch (Exception ex) 
			{
				request.getSession().setAttribute("errors", ex.getMessage());
			}
		}
		else
		{
			obj.init();
			if(action.equals("xemtrenweb"))
			{
				obj.setDataRs( setQuery(obj));
			}
			session.setAttribute("obj", obj);
			session.setAttribute("userId", userId);
			String nextJSP = "/TraphacoHYERP/pages/Center/BCAnhTrungBay.jsp";
			response.sendRedirect(nextJSP);

		}

	}

	private void CreatePivotTable(OutputStream out,IStockintransit obj, String query) throws Exception {
		try 
		{

			Workbook workbook = new Workbook();

			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			CreateHeader(workbook,obj); 
			FillData(workbook, query, obj);			
			workbook.save(out);

		} 
		catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("Error Message: " + ex.getMessage());
		}
	}

	private void CreateHeader(Workbook workbook,IStockintransit obj) 
	{
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);	    
		worksheet.setName("Sheet1");
		Cells cells = worksheet.getCells();	 

		cells.setRowHeight(0, 20.0f);	    
		Cell cell = cells.getCell("A1");	
		ReportAPI.getCellStyle(cell,Color.RED, true, 16, "Báo cáo ảnh trưng bày");
		cell = cells.getCell("A2");
		ReportAPI.getCellStyle(cell,Color.NAVY,true,10,"Từ ngày: " + obj.gettungay() + "  Đến ngày : " + obj.getdenngay());
		cell = cells.getCell("A3");
		ReportAPI.getCellStyle(cell,Color.NAVY,true,10,"Ngày tạo : " + this.getDateTime()); 
		cell = cells.getCell("A4");
		ReportAPI.getCellStyle(cell,Color.NAVY,true,10,"Người tạo : " + obj.getuserTen());


		int dongbatdauheader =7;
		int cotbatdau =0;

		cells.setRowHeight(dongbatdauheader, 50f);
		
		cell = cells.getCell(dongbatdauheader,cotbatdau++);cell.setValue("CTTB");		
		ReportAPI.setCellBackground(cell, Color.SILVER, BorderLineType.THIN, true, 0);
		
		cell = cells.getCell(dongbatdauheader,cotbatdau++);cell.setValue("MIỀN");		
		ReportAPI.setCellBackground(cell, Color.SILVER, BorderLineType.THIN, true, 0);

		cell = cells.getCell(dongbatdauheader,cotbatdau++);		cell.setValue("TỈNH THÀNH");
		ReportAPI.setCellBackground(cell, Color.SILVER, BorderLineType.THIN, true, 0);

		cell = cells.getCell(dongbatdauheader,cotbatdau++);		cell.setValue("MÃ CN/ĐT");	
		ReportAPI.setCellBackground(cell, Color.SILVER, BorderLineType.THIN, true, 0);

		cell = cells.getCell(dongbatdauheader,cotbatdau++);		cell.setValue("CHI NHÁNH ĐỐI TÁC");	
		ReportAPI.setCellBackground(cell, Color.SILVER, BorderLineType.THIN, true, 0);

		cell = cells.getCell(dongbatdauheader,cotbatdau++);		cell.setValue("MÃ TDV");	
		ReportAPI.setCellBackground(cell, Color.SILVER, BorderLineType.THIN, true, 0);

		
		cell = cells.getCell(dongbatdauheader,cotbatdau++);		cell.setValue("TRÌNH DƯỢC VIỆC");		
		ReportAPI.setCellBackground(cell, Color.SILVER, BorderLineType.THIN, true, 0);

		cell = cells.getCell(dongbatdauheader,cotbatdau++);		cell.setValue("TUYẾN");	
		ReportAPI.setCellBackground(cell, Color.SILVER, BorderLineType.THIN, true, 0);
		
		cell = cells.getCell(dongbatdauheader,cotbatdau++);		cell.setValue("MÃ KH");	
		ReportAPI.setCellBackground(cell, Color.SILVER, BorderLineType.THIN, true, 0);

		cell = cells.getCell(dongbatdauheader,cotbatdau++);		cell.setValue("KHÁCH HÀNG");		
		ReportAPI.setCellBackground(cell, Color.SILVER, BorderLineType.THIN, true, 0);

		cell = cells.getCell(dongbatdauheader,cotbatdau++);		cell.setValue("NGÀY CHỤP");		
		ReportAPI.setCellBackground(cell, Color.SILVER, BorderLineType.THIN, true, 0);
		cell = cells.getCell(dongbatdauheader,cotbatdau++);		cell.setValue("ẢNH CHỤP");  
		ReportAPI.setCellBackground(cell, Color.SILVER, BorderLineType.THIN, true, 0);

	}
	private void FillData(Workbook workbook, String query, IStockintransit obj) throws Exception
	{
		ResultSet rs = null;
		dbutils db = new dbutils();
		
		try
		{	
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			Cells cells = worksheet.getCells();

			for(int i=0;i<15;++i)
			{
				cells.setColumnWidth(i, 15.0f);	    	
			}	
			rs = db.get(query);
			int index = 8;
			Cell cell = null;

			while (rs.next())
			{
				int cotbatdau = 0;
				
		
				cell = cells.getCell(index,cotbatdau++);cell.setValue(rs.getString("CTTB"));	
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);

				cell = cells.getCell(index,cotbatdau++);cell.setValue(rs.getString("Vung"));	
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
				
				cell = cells.getCell(index,cotbatdau++);cell.setValue(rs.getString("tinhthanh"));	
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
				

				cell = cells.getCell(index,cotbatdau++);cell.setValue(rs.getString("nppMa"));	
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
				
				cell = cells.getCell(index,cotbatdau++);cell.setValue(rs.getString("NPP"));	
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
				
				cell = cells.getCell(index,cotbatdau++);cell.setValue(rs.getString("tdvMa"));	
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
				
				cell = cells.getCell(index,cotbatdau++);cell.setValue(rs.getString("TDV"));	
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
				

				cell = cells.getCell(index,cotbatdau++);cell.setValue(rs.getString("NGAYID"));	
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
				
				cell = cells.getCell(index,cotbatdau++);cell.setValue(rs.getString("khMa"));	
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);


				cell = cells.getCell(index,cotbatdau++);cell.setValue(rs.getString("KhachHang"));	
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);


				cell = cells.getCell(index,cotbatdau++);cell.setValue(rs.getString("NgayChup"));	
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);

				cells.setRowHeight(index, 50f);
				String img = rs.getString("ANHCHUP");
				String dir = getServletContext().getInitParameter("pathhinh");
				img=dir+img;
				
				//img=dir+"anhtest.jpg";
				
				try
				{
					cell = cells.getCell(index,cotbatdau);	
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
					int pictureIndex=worksheet.getPictures().add(index,cell.getColumnIndex(),index,cell.getColumnIndex(),img);
					Picture picture = worksheet.getPictures().get(pictureIndex);						
					picture.setWidth(100);
					picture.setHeight(50);


				} catch (Exception e)
				{
						//System.out.println("Exception: " + e.getMessage());
				}
				index++;

			}
			if(rs!=null){
				rs.close();
			}	

			ReportAPI.setHidden(workbook,14);

		}
		catch(Exception ex)
		{
			if(rs != null)
			{
				rs.close();
			}
			System.out.println("Error Here : "+ex.toString());
			

			ex.printStackTrace();

			throw new Exception(ex.getMessage()  );
		}
		finally
		{
			if(db != null)
				db.shutDown();
		}
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy : hh-mm-ss");
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

	public static Hashtable<String, String> getNgayDonHang(IStockintransit obj,dbutils db )
	{
		Hashtable<String, String> htb = new Hashtable<String, String>();
		String query= "   select distinct a.KHACHHANG_FK,a.DDKD_FK,(select    REPLACE((SELECT distinct xx.NGAYTAO as [data()]  FROM donhang xx----"  +  
		"\n inner join DKTRUNGBAY_DONHANG yy on xx.PK_SEQ = yy.DONHANG_FK inner join DANGKYTRUNGBAY zz on zz.PK_SEQ =yy.DKTRUNGBAY_FK----"  +  
		"\n where xx.KHACHHANG_FK=a.KHACHHANG_FK and zz.CTTRUNGBAY_FK = c.CTTRUNGBAY_FK  FOR XML PATH('')  ),' ',',') ) as ngaytao,c.CTTRUNGBAY_FK----"  +  
		"\n from donhang a inner join DKTRUNGBAY_DONHANG b on a.PK_SEQ=b.DONHANG_FK inner join DANGKYTRUNGBAY c on c.PK_SEQ= b.DKTRUNGBAY_FK ----"  +  
		"\n where  a.TRANGTHAI!=2----"  +  
		"\n and exists(select 1 from khachhang_anhchup ac where ac.KHACHHANG_FK = a.KHACHHANG_FK and a.DDKD_FK = ac.DDKD_FK and ac.CTTB_FK =  c.CTTRUNGB";

		if(obj.gettungay().length()>0)
		{
			query+= " and a.NGAYTAO>='"+obj.gettungay()+"' ";
		}
		if(obj.getdenngay().length()>0)
		{
			query+= " and a.NGAYTAO <='"+obj.getdenngay()+"' ";
		}

		ResultSet rs = db.get(query);
		try
		{
			while (rs.next())
			{
				htb.put(rs.getString("DDKD_FK")+" -- " + rs.getString("KHACHHANG_FK")+ " -- "  + rs.getString("CTTRUNGBAY_FK")  , rs.getString("ngaytao"));
			}
			rs.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		} 
		return htb;
	}

	
}
