package geso.traphaco.center.servlets.reports;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.beans.hoadontaichinh.IBCHoadonbanra;
import geso.traphaco.center.beans.hoadontaichinh.imp.BCHoadonbanra;
import geso.traphaco.center.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
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
import com.aspose.cells.Font;
import com.aspose.cells.HorizontalAlignmentType;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class BCDoanhsoTTSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public BCDoanhsoTTSvl() {
        super();
    }
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IBCHoadonbanra obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    	   
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String loaihoadon = request.getParameter("loaihoadon");
	    if(loaihoadon == null)
	    	loaihoadon = "0";
	    
	    obj = new BCHoadonbanra();
	    obj.setUserId(userId);
	    obj.setLoaidonhang(loaihoadon);
	    obj.createRs();
	    obj.search_NPP();
	    
	    String view = request.getParameter("view");
		if(view == null)
			view = "";
		obj.setView(view);
	    
	    String nextJSP = "";

		nextJSP = "/TraphacoHYERP/pages/Center/BCDoanhSo.jsp";
		session.setAttribute("obj", obj);
		response.sendRedirect(nextJSP);
	    
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    Utility util = new Utility();
	    String userId = util.antiSQLInspection(request.getParameter("userId")); 
	    HttpSession session = request.getSession();
	    
	    OutputStream out = response.getOutputStream();
	    
	    String action = request.getParameter("action");
	    if (action == null)
	    	action = "";
	    
	    IBCHoadonbanra obj = new BCHoadonbanra();
	    obj.setUserId(userId);
	    
	    String tungay = request.getParameter("tungay");
	    if(tungay == null)
	    	tungay = "";
	    obj.setTungay(tungay);
	    
	    String denngay = request.getParameter("denngay");
	    if(denngay == null)
	    	denngay = "";
	    obj.setDenngay(denngay);
	    
	    String khId = request.getParameter("khId");
	    if(khId == null)
	    	khId = "";
	    obj.setKhTen(khId);
	    
	    String loaihoadon = request.getParameter("loaihoadon");
	    if(loaihoadon == null)
	    	loaihoadon = "0";
	    obj.setLoaidonhang(loaihoadon);

	    String nvbhId = request.getParameter("nvbhId");
	    if(nvbhId == null)
	    	nvbhId = "";
	    obj.setNvbhId(nvbhId);
	  	
  
	    String view = request.getParameter("view");
		if(view == null)
			view = "";
		obj.setView(view);
		System.out.println("VIEW LA: " + view);
		
		
		String npp="";
		if(view.length()>0  )
		{
			 npp=  request.getParameter("npp");
  		 if(npp==null)
  			 npp="";
		}
		else 
		{
			npp=util.getIdNhapp(userId);
		}
		obj.setNppId(npp);
		
		 String vung = request.getParameter("vung");
		 if(vung==null)
			 vung="";
		 obj.setVung(vung);
		 
		 String khuvuc = request.getParameter("khuvuc");
		 if(khuvuc==null)
			 khuvuc="";
		 obj.setKhuvuc(khuvuc);
		 
		 String cn1 = request.getParameter("cn1");
		 if(cn1==null)
			 cn1="";
		 obj.setCn(cn1);
		 
			obj.setMuclay(util.antiSQLInspection(request.getParameter("type")) != null ? util.antiSQLInspection(request.getParameter("type")) : "");
			
			
		
			
			obj.setTdvId(util.antiSQLInspection(request.getParameter("ddkdId")) != null ? util.antiSQLInspection(request.getParameter("ddkdId")) : "");
			obj.setKhoId(util.antiSQLInspection(request.getParameter("khoId")) != null ? util.antiSQLInspection(request.getParameter("khoId")) : "");
			obj.setMucCN_DT(util.antiSQLInspection(request.getParameter("cndt")) != null ? util.antiSQLInspection(request.getParameter("cndt")) : "0");
			obj.setMuc_KhachHang(util.antiSQLInspection(request.getParameter("kh")) != null ? util.antiSQLInspection(request.getParameter("kh")) : "0");
			
			
			System.out.println("____cndt"+obj.getMucCN_DT()+"___KH"+obj.getMuc_KhachHang());
			
			obj.createRs();			
			String search_ETC = getSearchQuery_ETC(request, (IBCHoadonbanra) obj, view);
		  String search_OTC = getSearchQuery_OTC(request, (IBCHoadonbanra) obj, view);   	
		  String search_KM = getSearchQuery_KM(request, (IBCHoadonbanra) obj, view);
		  String search_HM = getSearchQuery_HM(request, (IBCHoadonbanra) obj, view);
		//  String search_HDKHAC = getSearchQuery_HDKHAC(request, (IBCHoadonbanra) obj, view);  
		  
		String totalQuery = getTotal_Query(request, (IBCHoadonbanra) obj, view);
			
		System.out.println("__ETC_Total__"+search_ETC);
  	//	System.out.println("__OTC_Total__"+search_OTC);
  //		System.out.println("__KM_Total__"+search_KM);
		
  		//System.out.println("__HDKHAC_Total__"+search_HDKHAC);
  		
	    if (action.equals("excel") && ( tungay.trim().length() > 0 && denngay.trim().length() > 0 ) )
	    {
	    	try
		    { 
					response.setContentType("application/xlsm");
					response.setHeader("Content-Disposition", "attachment; filename=BaoCaoDoanhSoTT.xlsm");
					FileInputStream fstream ;
					obj.setMuclay("0");
					if(obj.getMuclay().equals("0"))
					{
						 fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\BCDoanhSoTT.xlsm");
					}
					else 
					{
						 fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\BCDoanhSo_DetailTT.xlsm");
					}
					Workbook workbook = new Workbook();
					workbook.open(fstream);
					workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
				
					CreateStaticHeader(workbook, obj);
					System.out.println("muc lay la "+obj.getMuclay());
					if(obj.getMuclay().equals("0"))
					{
						/*if(obj.getMuc_KhachHang().equals("1"))
						{
							CreateStaticData_KM(workbook, obj, view,search_KM);
							CreateStaticData_OTC(workbook, obj, view,search_OTC);
							CreateStaticData_HDKHAC(workbook, obj, view,search_HDKHAC);
						}*/
					  CreateStaticData_ETC(workbook, obj, view,search_ETC);
					  CreateStaticData_OTC(workbook, obj, view,search_OTC);
					  CreateStaticData_KM(workbook, obj, view,search_KM);
					  CreateStaticData_HM(workbook, obj, view,search_HM);
					}
					else 
					{
						/*if(obj.getMuc_KhachHang().equals("1"))
						{
							CreateStaticData_KM_Detail(workbook, obj, view,search_KM);
							CreateStaticData_OTC_Detail(workbook, obj, view,search_OTC);
							CreateStaticData_HDKHAC_Detail(workbook, obj, view,search_HDKHAC);
						}*/
						CreateStaticData_ETC_Detail(workbook, obj, view,search_ETC);
					}			    
			    workbook.save(out);
			    fstream.close();
		    }
		    catch (Exception ex)
		    {
		    	ex.printStackTrace();
		        obj.setMsg("Khong the tao pivot.");
		    }
	    	
			obj.setUserId(userId);
			
	    	session.setAttribute("obj", obj);
    		session.setAttribute("userId", userId);
    		String nextJSP = "";
    		
			nextJSP = "/TraphacoHYERP/pages/Center/BCDoanhSo.jsp";
    		response.sendRedirect(nextJSP); 
	    }
	    
	    else if(action.equals("search")){
	    	
	    	obj.setUserId(userId);
			
	    	session.setAttribute("obj", obj);
    		session.setAttribute("userId", userId);
    		obj.setNppId(npp);
    		
    		obj.search_NPP();
    		
    		obj.searchQuery_ETC(search_ETC);
    	/*	obj.searchQuery_OTC(search_OTC);
    		obj.searchQuery_KM(search_KM);
    		obj.searchQuery_HDKhac(search_HDKHAC);
    	*/	
    		
    		obj.setTotal_Query(totalQuery);
    		String nextJSP = "";
    		nextJSP = "/TraphacoHYERP/pages/Center/BCDoanhSo.jsp";
    		response.sendRedirect(nextJSP); 
	    	
	    }
	    else
	    {
			session.setAttribute("obj", obj);
			String nextJSP = "";
			
			
			obj.setMsg("Bạn phải chọn khoảng thời gian lấy báo cáo");
			nextJSP = "/TraphacoHYERP/pages/Center/BCDoanhSo.jsp";
			
			response.sendRedirect(nextJSP);  
	    }
	}
	
	private void CreateStaticData_HDKHAC_Detail(Workbook workbook, IBCHoadonbanra obj, String view, String search_HDKHAC)
	  {
			Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(3);
	    Cells cells = worksheet.getCells();
	    String query=search_HDKHAC;
	    
	    dbutils db = new dbutils();
	    ResultSet hdRs = db.get(query);

		Cell cell_mau = cells.getCell("A9");
		Cell cell = null;
		
		Style style;
		Font font2 = new Font();
		font2.setName("Times New Roman");				
		font2.setSize(8);
		
		Font font3 = new Font();
		font3.setName("Times New Roman");				
		font3.setSize(8);
		font3.setBold(true);
		
		int i = 10;
		if(hdRs != null)
		{
			try 
			{
				int stt = 1;
				double totalTRUOCVAT = 0;
				double totalVAT = 0;
				
				while(hdRs.next())
				{
					totalTRUOCVAT += Math.round(hdRs.getDouble("DOANHTHU"));
					totalVAT += hdRs.getDouble("VAT");
					
					double thueVAT = hdRs.getDouble("VAT");
					
					double AVAT = hdRs.getDouble("AVAT");

	        cell = cells.getCell("A" + Integer.toString(i));	cell.setValue(stt); 			style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
	        cell = cells.getCell("B" + Integer.toString(i));	cell.setValue( hdRs.getString("ddkdTEN")==null?"":hdRs.getString("ddkdTEN")  ); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
	        cell = cells.getCell("C" + Integer.toString(i));	cell.setValue(hdRs.getString("MA")); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("D" + Integer.toString(i));	cell.setValue(hdRs.getString("TEN")); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("E" + Integer.toString(i));	cell.setValue(hdRs.getString("DONVI")); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);		
					cell = cells.getCell("F" + Integer.toString(i));	cell.setValue(hdRs.getDouble("SOLUONG")); 	style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
					
					
					cell = cells.getCell("G" + Integer.toString(i));	cell.setValue(hdRs.getDouble("DONGIA")); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					style.setCustom("#,##0.0000");
					cell.setStyle(style);
					
					cell = cells.getCell("H" + Integer.toString(i));	cell.setValue(Math.round(hdRs.getDouble("DOANHTHU"))); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
					
					cell = cells.getCell("I" + Integer.toString(i));	cell.setValue(thueVAT); 			style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
					
					cell = cells.getCell("J" + Integer.toString(i));	cell.setValue(AVAT); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
					

					i++;
					stt ++;
				}
				hdRs.close();
				
				
				
				//THEM DONG TONG CONG
				cell = cells.getCell("A" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
				cell = cells.getCell("B" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
				cell = cells.getCell("C" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
	      cell = cells.getCell("D" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);		
				cell = cells.getCell("E" + Integer.toString(i));	cell.setValue(""); 	style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
				cell = cells.getCell("F" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
				cell = cells.getCell("G" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
				cell = cells.getCell("H" + Integer.toString(i));	cell.setValue(totalTRUOCVAT); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
				
				cell = cells.getCell("I" + Integer.toString(i));	cell.setValue(totalVAT); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
				
				cell = cells.getCell("J" + Integer.toString(i));	cell.setValue(totalTRUOCVAT + totalVAT); 			style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
				
				i++;
				
			}
			catch (Exception e)
			{ 
				e.printStackTrace(); 
			}
		}
			
			
	  }

	private void CreateStaticData_OTC_Detail(Workbook workbook, IBCHoadonbanra obj, String view, String search_OTC)
  {
		Worksheets worksheets = workbook.getWorksheets();
    Worksheet worksheet = worksheets.getSheet(2);
    Cells cells = worksheet.getCells();
    String query=search_OTC;
    
    dbutils db = new dbutils();
    ResultSet hdRs = db.get(query);

	Cell cell_mau = cells.getCell("A9");
	Cell cell = null;
	
	Style style;
	Font font2 = new Font();
	font2.setName("Times New Roman");				
	font2.setSize(8);
	
	Font font3 = new Font();
	font3.setName("Times New Roman");				
	font3.setSize(8);
	font3.setBold(true);
	
	int i = 10;
	if(hdRs != null)
	{
		try 
		{
			int stt = 1;
			double totalTRUOCVAT = 0;
			double totalVAT = 0;
			
			while(hdRs.next())
			{
				totalTRUOCVAT += Math.round(hdRs.getDouble("DOANHTHU"));
				totalVAT += hdRs.getDouble("VAT");
				
				double thueVAT = hdRs.getDouble("VAT");
				
				double AVAT = hdRs.getDouble("AVAT");

        cell = cells.getCell("A" + Integer.toString(i));	cell.setValue(stt); 			style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
        cell = cells.getCell("B" + Integer.toString(i));	cell.setValue( hdRs.getString("ddkdTEN")==null?"":hdRs.getString("ddkdTEN")  ); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
        cell = cells.getCell("C" + Integer.toString(i));	cell.setValue(hdRs.getString("MA")); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
				cell = cells.getCell("D" + Integer.toString(i));	cell.setValue(hdRs.getString("TEN")); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
				cell = cells.getCell("E" + Integer.toString(i));	cell.setValue(hdRs.getString("DONVI")); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);		
				cell = cells.getCell("F" + Integer.toString(i));	cell.setValue(hdRs.getDouble("SOLUONG")); 	style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
				
				
				cell = cells.getCell("G" + Integer.toString(i));	cell.setValue(hdRs.getDouble("DONGIA")); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
				style.setCustom("#,##0.0000");
				cell.setStyle(style);
				
				cell = cells.getCell("H" + Integer.toString(i));	cell.setValue(Math.round(hdRs.getDouble("DOANHTHU"))); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
				
				cell = cells.getCell("I" + Integer.toString(i));	cell.setValue(thueVAT); 			style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
				
				cell = cells.getCell("J" + Integer.toString(i));	cell.setValue(AVAT); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
				

				i++;
				stt ++;
			}
			hdRs.close();
			
			
			String condition="";	
			String condition1="";
			if(obj.getNppId().length()>0 )
			{
				condition+=" and NPP_FK = '" + obj.getNppId() + "' ";
 				condition1 += "and a.NPP_FK=(select TRUCTHUOC_FK from NHAPHANPHOI where PK_SEQ='"+obj.getNppId()+"' and a.KHACHHANG_FK in (select pk_seq from KHACHHANG where TrucThuoc_FK='"+obj.getNppId()+"'))";
			}
			if(obj.getVung().trim().length() > 0)
			{
				condition += " and npp_fk in (Select pk_Seq  from nhaphanphoi where  khuvuc_Fk in (Select pk_Seq from khuvuc where vung_Fk='"+obj.getVung()+"' ) ) ";
				condition1 += " and npp_fk in (Select pk_Seq  from nhaphanphoi where  khuvuc_Fk in (Select pk_Seq from khuvuc where vung_Fk='"+obj.getVung()+"' ) ) ";
			}
			
			if(obj.getKhuvuc().trim().length() > 0)
			{
				condition += " and npp_fk in (Select pk_Seq from nhaphanphoi where khuvuc_Fk='"+obj.getKhuvuc()+"')";
				condition1 += " and npp_fk in (Select pk_Seq from nhaphanphoi where khuvuc_Fk='"+obj.getKhuvuc()+"')";
 			}
			
			if(obj.getTungay().length()>0)
			{
				condition+= "and ngayxuatHD >= '" + obj.getTungay() + "'";
				condition1+= "and ngayxuatHD >= '" + obj.getTungay() + "'";
			}
			if(obj.getDenngay().length()>0)
			{
				condition+= "and ngayxuatHD <= '" + obj.getDenngay() + "' ";
				condition1+= "and ngayxuatHD <= '" + obj.getDenngay() + "' ";
			}
			
			Utility util = new Utility();
			if(view.length()>0)
			{
				query += " and npp_Fk in "+util.quyen_npp(obj.getUserId() ) +" ";
			}
			if(obj.getTdvId().length()>0)
			{
				//condition += " and a.pk_seq  in (select hoadon_Fk from hoadon_ddh where DDH_FK  in (select pk_seq from donhang  where ddkd_fk='"+obj.getTdvId()+"') )  ";
				condition +=
						 " and a.khachhang_Fk in   	( select cc.khachhang_Fk from DAIDIENKINHDOANH aa inner join TUYENBANHANG bb on bb.DDKD_FK=aa.PK_SEQ   \n "+      
						 "   		inner join KHACHHANG_TUYENBH cc on cc.TBH_FK=bb.PK_SEQ  " +
						 "          where aa.pk_Seq='"+obj.getTdvId()+"'      \n "+      
						 "   	) ";
			}
			
			
			
				query=
					"	select loai.ma as MaLoai,ck.diengiai,ck.thuevat, sum(ROUND(ck.ChietKhau,0)) as ChietKhau, sum(ROUND(  ROUND( ck.ChietKhau ,0 )*(ck.thueVAT/100 ),0))   	as VAT,sum(ROUND(  ROUND( ck.ChietKhau ,0 )*(1+ck.thueVAT/100 ),0))   	as AVAT   "+ 
					"	from hoadon_chietkhau ck left join loaick loai on loai.ma=ck.diengiai " +
					"   where isnull(hienthi,0)=1 and hoadon_fk in  (select pk_seq from hoadon A where   trangthai not in ( 1 , 3, 5 ) and isnull(loaihoadon, '0') = '0'   "+condition+" )   "+
					"	group by loai.ma,ck.diengiai,ck.thuevat " ;
				
				if(obj.getCn().equals("1")){
					query+= " union  all "+
							"	select loai.ma as MaLoai,ck.diengiai,ck.thuevat, sum(ROUND(ck.ChietKhau,0)) as ChietKhau, sum(ROUND(  ROUND( ck.ChietKhau ,0 )*(ck.thueVAT/100 ),0))   	as VAT,sum(ROUND(  ROUND( ck.ChietKhau ,0 )*(1+ck.thueVAT/100 ),0))   	as AVAT   "+ 
							"	from hoadon_chietkhau ck left join loaick loai on loai.ma=ck.diengiai " +
							"   where isnull(hienthi,0)=1 and hoadon_fk in  (select pk_seq from hoadon A where   trangthai not in ( 1 , 3, 5 ) and isnull(loaihoadon, '0') = '0'   "+condition+" )   "+
							"	group by loai.ma,ck.diengiai,ck.thuevat " ;
				}
			System.out.println("-----LAY CHIET KHAU: " + query);
			ResultSet rsCK = db.get(query);
			if(rsCK != null)
			{
				while(rsCK.next())
				{
					double ck_truocVAT =Math.round( -1 * rsCK.getDouble("chietkhau"));
					double ck_VAT =-1 * rsCK.getDouble("VAT");
					double ck_sauVAT =-1 * rsCK.getDouble("AVAT");
					
					totalTRUOCVAT += ck_truocVAT;
					totalVAT += ck_VAT;
					
					cell = cells.getCell("A" + Integer.toString(i));	cell.setValue(stt); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
					
					cell = cells.getCell("B" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
					
					cell = cells.getCell("C" + Integer.toString(i));	cell.setValue(rsCK.getString("maloai")); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
	        cell = cells.getCell("D" + Integer.toString(i));	cell.setValue(rsCK.getString("diengiai")); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);		
					cell = cells.getCell("E" + Integer.toString(i));	cell.setValue(" "); 	style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
					cell = cells.getCell("F" + Integer.toString(i));	cell.setValue(0); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					cell = cells.getCell("G" + Integer.toString(i));	cell.setValue(0); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					cell = cells.getCell("H" + Integer.toString(i));	cell.setValue(ck_truocVAT); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
					
					cell = cells.getCell("I" + Integer.toString(i));	cell.setValue(ck_VAT); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
					
					cell = cells.getCell("J" + Integer.toString(i));	cell.setValue(ck_sauVAT); 			style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
					
					
					stt++;
					i++;
				}
				rsCK.close();
			}
			
			//THEM DONG TONG CONG
			cell = cells.getCell("A" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
			cell = cells.getCell("B" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
			cell = cells.getCell("C" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
      cell = cells.getCell("D" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);		
			cell = cells.getCell("E" + Integer.toString(i));	cell.setValue(""); 	style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
			cell = cells.getCell("F" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
			cell = cells.getCell("G" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
			cell = cells.getCell("H" + Integer.toString(i));	cell.setValue(totalTRUOCVAT); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
			ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
			
			cell = cells.getCell("I" + Integer.toString(i));	cell.setValue(totalVAT); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
			ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
			
			cell = cells.getCell("J" + Integer.toString(i));	cell.setValue(totalTRUOCVAT + totalVAT); 			style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
			ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
			
			i++;
			
		}
		catch (Exception e)
		{ 
			e.printStackTrace(); 
		}
	}
		
		
  }

	private void CreateStaticData_ETC_Detail(Workbook workbook, IBCHoadonbanra obj, String view, String search_ETC)
  {
		Worksheets worksheets = workbook.getWorksheets();
    Worksheet worksheet = worksheets.getSheet(1);
    Cells cells = worksheet.getCells();

    String query = search_ETC;
    dbutils db = new dbutils();
    ResultSet hdRs = db.get(query);
	
    Cell cell_mau = cells.getCell("A9");
    Cell cell = null;
	
		Style style;
		Font font2 = new Font();
		font2.setName("Times New Roman");				
		font2.setSize(8);
		
		Font font3 = new Font();
		font3.setName("Times New Roman");				
		font3.setSize(8);
		font3.setBold(true);
		
		int i = 10;
		if(hdRs != null)
		{
			try 
			{
				int stt = 1;
				double totalTRUOCVAT = 0;
				double totalVAT = 0;
				
				while(hdRs.next())
				{
					totalTRUOCVAT += Math.round(hdRs.getDouble("DOANHTHU"));
					totalVAT += hdRs.getDouble("VAT");
					
					double	thueVAT= hdRs.getDouble("VAT");
					double	AVAT= hdRs.getDouble("AVAT");
	
	        cell = cells.getCell("A" + Integer.toString(i));	cell.setValue(stt); 			style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
	        cell = cells.getCell("B" + Integer.toString(i));	cell.setValue(hdRs.getString("ddkdTEN")==null?"":hdRs.getString("ddkdTEN")); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("C" + Integer.toString(i));	cell.setValue(hdRs.getString("MA")); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("D" + Integer.toString(i));	cell.setValue(hdRs.getString("TEN")); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("E" + Integer.toString(i));	cell.setValue(hdRs.getString("DONVI")); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);		
					cell = cells.getCell("F" + Integer.toString(i));	cell.setValue(hdRs.getDouble("SOLUONG")); 	style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
					
					
					cell = cells.getCell("G" + Integer.toString(i));	cell.setValue(hdRs.getDouble("DONGIA")); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					style.setCustom("#,##0.0000");
					cell.setStyle(style);
					
					
					cell = cells.getCell("H" + Integer.toString(i));	cell.setValue(Math.round(hdRs.getDouble("DOANHTHU"))); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
					
					cell = cells.getCell("I" + Integer.toString(i));	cell.setValue(thueVAT); 			style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
					
					cell = cells.getCell("J" + Integer.toString(i));	cell.setValue( AVAT); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
					
	
					i++;
					stt ++;
				}
				hdRs.close();
				
				
				String condition = " and isnull(LoaiHoaDon,0)=0 ";
				String condition1 =" and isnull(LoaiHoaDon,0)=0  ";
				if(obj.getVung().trim().length() > 0)
				{
			    	condition += " and a.npp_fk in (Select pk_Seq  from nhaphanphoi where  khuvuc_Fk in (Select pk_Seq from khuvuc where vung_Fk='"+obj.getVung()+"' ) ) ";
			    	condition1 += " and a.npp_fk in (Select pk_Seq  from nhaphanphoi where  khuvuc_Fk in (Select pk_Seq from khuvuc where vung_Fk='"+obj.getVung()+"' ) ) ";
				}
				
				if(obj.getKhuvuc().trim().length() > 0)
				{
					condition += " and a.npp_fk in (Select pk_Seq from nhaphanphoi where khuvuc_Fk='"+obj.getKhuvuc()+"') ";
					condition1 += " and a.npp_fk in (Select pk_Seq from nhaphanphoi where khuvuc_Fk='"+obj.getKhuvuc()+"') ";
				}
				
				if(obj.getNppId().length()>0)
				{
					condition += " and a.npp_Fk = '" + obj.getNppId() + "' ";
	 				condition1 += "and a.NPP_FK=(select TRUCTHUOC_FK from NHAPHANPHOI where PK_SEQ='"+obj.getNppId()+"' and a.KHACHHANG_FK in (select pk_seq from KHACHHANG where TrucThuoc_FK='"+obj.getNppId()+"'))";
				}
				
				Utility util = new Utility();
				if(view.length()>0)
				{
					condition += " and a.npp_Fk in "+util.quyen_npp(obj.getUserId() ) +" ";
					condition1 += " and a.npp_Fk in "+util.quyen_npp(obj.getUserId() ) +" ";
				}
				
				if(obj.getTdvId().length()>0)
				{
				//	condition += " and a.pk_seq  in (select HOADONNPP_FK from ERP_HOADONNPP_DDH where DDH_FK  in (select pk_seq from ERP_DONDATHANGNPP  where ddkd_fk='"+obj.getTdvId()+"') )  ";
				//	condition1 += " and a.pk_seq  in (select HOADONNPP_FK from ERP_HOADONNPP_DDH where DDH_FK  in (select pk_seq from ERP_DONDATHANGNPP  where ddkd_fk='"+obj.getTdvId()+"') )  ";
			
					condition +=
							 " and a.khachhang_Fk in   	( select cc.khachhang_Fk from DAIDIENKINHDOANH aa inner join TUYENBANHANG bb on bb.DDKD_FK=aa.PK_SEQ   \n "+      
							 "   		inner join KHACHHANG_TUYENBH cc on cc.TBH_FK=bb.PK_SEQ  " +
							 "          where aa.pk_Seq='"+obj.getTdvId()+"'      \n "+      
							 "   	) ";
					condition1 +=
							 " and a.khachhang_Fk in   	( select cc.khachhang_Fk from DAIDIENKINHDOANH aa inner join TUYENBANHANG bb on bb.DDKD_FK=aa.PK_SEQ   \n "+      
							 "   		inner join KHACHHANG_TUYENBH cc on cc.TBH_FK=bb.PK_SEQ  " +
							 "          where aa.pk_Seq='"+obj.getTdvId()+"'      \n "+      
							 "   	) ";
				}
				
				if(obj.getTungay().length()>0)
				{
					condition+= "and ngayxuatHD >= '" + obj.getTungay() + "'";
					condition1+= "and ngayxuatHD >= '" + obj.getTungay() + "'";
				}
				if(obj.getDenngay().length()>0)
				{
					condition+= "and ngayxuatHD <= '" + obj.getDenngay() + "' ";
					condition1+= "and ngayxuatHD <= '" + obj.getDenngay() + "' ";
				}
				
				if(obj.getMuc_KhachHang().equals("1")&&obj.getMucCN_DT().equals("0"))
				{
					condition += 	 " and  a.KhachHang_fk is not null ";
					condition1 += 	 " and  a.KhachHang_fk is not null ";
				}
				if(obj.getMucCN_DT().equals("1")&&obj.getMuc_KhachHang().equals("0"))
				{
					condition += 	 " and  a.npp_dat_fk is not null ";
					condition1 += 	 " and  a.npp_dat_fk is not null ";
				}
				
				
				 query=
					"	select  'CN5' as Ma,'CN5' as TEN,''  as DonVi ,0 as SoLuong,0 as DonGia,sum(ROUND(b.CHIETKHAU,0)) as DOANHTHU ,b.VAT as thueVAT , "+
					"		SUM (ROUND(   ROUND ( B.soluong * b.dongia,0) * B.vat/100.0,0 )) as VAT,  "+  		 		
					"		sum(ROUND( ( ROUND (soluong * dongia , 0 ) )   * (1+ b.vat/100.0),0 )) as AVAT "+    		
					"	from ERP_HOADONNPP  a inner join ERP_HOADONNPP_SP b on b.HOADON_FK=a.PK_SEQ  "+
					"	where a.trangthai not in (1,3,5) "+condition+" and b.VAT='5' "+
					"	group by b.VAT" +
					" having sum(b.CHIETKHAU)>0    "+
					" 	union all  "+
					"	select  'CN10' as Ma,'CN5' as TEN,''  as DonVi ,0 as SoLuong,0 as DonGia,sum(ROUND(b.CHIETKHAU,0)) as DOANHTHU ,b.VAT as thueVAT, "+ 
					"		SUM (ROUND(   ROUND ( B.soluong * b.dongia,0) * B.vat/100.0,0 )) as VAT,  "+  		 		
					"		sum(ROUND( ( ROUND (soluong * dongia , 0 ) )   * (1+ b.vat/100.0),0 )) as AVAT "+
					"	from ERP_HOADONNPP  a inner join ERP_HOADONNPP_SP b on b.HOADON_FK=a.PK_SEQ  "+
					"	where a.trangthai not in (1,3,5) "+condition+" and b.VAT='10' "+
					"	group by b.VAT  " +
					"having sum(b.CHIETKHAU)>0  ";
				 
				 if(obj.getCn().equals("1"))
				 {
					 query+= 	" union all "+
								"	select  'CN5' as Ma,'CN5' as TEN,''  as DonVi ,0 as SoLuong,0 as DonGia,sum(ROUND(b.CHIETKHAU,0)) as DOANHTHU ,b.VAT as thueVAT , "+
								"		SUM (ROUND(   ROUND ( B.soluong * b.dongia,0) * B.vat/100.0,0 )) as VAT,  "+  		 		
								"		sum(ROUND( ( ROUND (soluong * dongia , 0 ) )   * (1+ b.vat/100.0),0 )) as AVAT "+    		
								"	from ERP_HOADONNPP  a inner join ERP_HOADONNPP_SP b on b.HOADON_FK=a.PK_SEQ  "+
								"	where a.trangthai not in (1,3,5) "+condition1+" and b.VAT='5' "+
								"	group by b.VAT" +
								" having sum(b.CHIETKHAU)>0    "+
								" 	union all  "+
								"	select  'CN10' as Ma,'CN5' as TEN,''  as DonVi ,0 as SoLuong,0 as DonGia,sum(ROUND(b.CHIETKHAU,0)) as DOANHTHU ,b.VAT as thueVAT, "+ 
								"		SUM (ROUND(   ROUND ( B.soluong * b.dongia,0) * B.vat/100.0,0 )) as VAT,  "+  		 		
								"		sum(ROUND( ( ROUND (soluong * dongia , 0 ) )   * (1+ b.vat/100.0),0 )) as AVAT "+
								"	from ERP_HOADONNPP  a inner join ERP_HOADONNPP_SP b on b.HOADON_FK=a.PK_SEQ  "+
								"	where a.trangthai not in (1,3,5) "+condition1+" and b.VAT='10' "+
								"	group by b.VAT  " +
								"having sum(b.CHIETKHAU)>0  ";
				 }
				hdRs = db.get(query);
				
				System.out.println(":::"+query);
				
				while(hdRs.next())
				{
				
					double ck_truocVAT = Math.round( -1 * hdRs.getDouble("DOANHTHU"));
					double ck_VAT =-1 * hdRs.getDouble("VAT");
					double ck_sauVAT =-1 * hdRs.getDouble("AVAT");
					
					totalTRUOCVAT += ck_truocVAT;
					totalVAT += ck_VAT;

          cell = cells.getCell("A" + Integer.toString(i));	cell.setValue(stt); style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
          cell = cells.getCell("A" + Integer.toString(i));	cell.setValue(""); style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
					cell = cells.getCell("C" + Integer.toString(i));	cell.setValue(hdRs.getString("MA")); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("D" + Integer.toString(i));	cell.setValue(hdRs.getString("TEN")); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("E" + Integer.toString(i));	cell.setValue(hdRs.getString("DONVI")); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);		
					cell = cells.getCell("F" + Integer.toString(i));	cell.setValue(hdRs.getDouble("SOLUONG")); 	style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
					
					
					cell = cells.getCell("G" + Integer.toString(i));	cell.setValue(hdRs.getDouble("DONGIA")); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					style.setCustom("#,##0.0000");
					cell.setStyle(style);
					
					
					cell = cells.getCell("H" + Integer.toString(i));	cell.setValue(ck_truocVAT); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
					
					cell = cells.getCell("I" + Integer.toString(i));	cell.setValue(ck_VAT); 			style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
					
					cell = cells.getCell("J" + Integer.toString(i));	cell.setValue(ck_sauVAT); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
					
					i++;
					stt ++;
					
				}
				
				
				//THEM DONG TONG CONG
				cell = cells.getCell("A" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
				cell = cells.getCell("B" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
				cell = cells.getCell("C" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
	      cell = cells.getCell("D" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);		
				cell = cells.getCell("E" + Integer.toString(i));	cell.setValue(""); 	style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
				cell = cells.getCell("F" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
				cell = cells.getCell("G" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
				cell = cells.getCell("H" + Integer.toString(i));	cell.setValue(totalTRUOCVAT); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);

				cell = cells.getCell("I" + Integer.toString(i));	cell.setValue(totalVAT); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
				cell = cells.getCell("J" + Integer.toString(i));	cell.setValue(totalTRUOCVAT + totalVAT); 			style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
				
				i++;
				
			}
			catch (Exception e)
			{ 
				e.printStackTrace(); 
			}
		}
  }

	private void CreateStaticData_KM_Detail(Workbook workbook, IBCHoadonbanra obj, String view, String search_KM)
  {
		Worksheets worksheets = workbook.getWorksheets();
    Worksheet worksheet = worksheets.getSheet(0);
    Cells cells = worksheet.getCells();
   
    String query = search_KM;
    
    dbutils db = new dbutils();
    ResultSet hdRs = db.get(query);

	Cell cell_mau = cells.getCell("A10");
	Cell cell = null;
	
	Style style;
	Font font2 = new Font();
	font2.setName("Times New Roman");				
	font2.setSize(8);
	
	Font font3 = new Font();
	font3.setName("Times New Roman");				
	font3.setSize(8);
	font3.setBold(true);
	
	int i = 11;
	if(hdRs != null)
	{
		try 
		{
			int stt = 1;
			double totalTRUOCVAT = 0;
			double totalVAT = 0;
			
			while(hdRs.next())
			{
				totalTRUOCVAT += Math.round(hdRs.getDouble("DOANHTHU"));
				totalVAT += Math.round(hdRs.getDouble("thuexuat") * hdRs.getDouble("DOANHTHU") / 100);
				
				double thueVAT = Math.round(hdRs.getDouble("thuexuat") * hdRs.getDouble("DOANHTHU") / 100);

        cell = cells.getCell("A" + Integer.toString(i));	cell.setValue(stt); 			style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
        cell = cells.getCell("B" + Integer.toString(i));	cell.setValue( hdRs.getString("ddkdTen") ==null?"":hdRs.getString("ddkdTen") ); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
				cell = cells.getCell("C" + Integer.toString(i));	cell.setValue(hdRs.getString("MA")); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
				cell = cells.getCell("D" + Integer.toString(i));	cell.setValue(hdRs.getString("TEN")); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
				cell = cells.getCell("E" + Integer.toString(i));	cell.setValue(hdRs.getString("DONVI")); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);		
				cell = cells.getCell("F" + Integer.toString(i));	cell.setValue(hdRs.getDouble("SOLUONG")); 	style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
				
				cell = cells.getCell("G" + Integer.toString(i));	cell.setValue(hdRs.getDouble("DONGIA")); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
				style.setCustom("#,##0.0000");
				cell.setStyle(style);

				cell = cells.getCell("H" + Integer.toString(i));	cell.setValue( Math.round( hdRs.getDouble("DOANHTHU") )); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
				
				cell = cells.getCell("I" + Integer.toString(i));	cell.setValue(thueVAT); 			style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
				
				cell = cells.getCell("J" + Integer.toString(i));	cell.setValue(Math.round(hdRs.getDouble("DOANHTHU") + thueVAT)); 		
				style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);					
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
				

				i++;
				stt ++;
			}
			hdRs.close();
			
			//THEM DONG TONG CONG
			cell = cells.getCell("A" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
			cell = cells.getCell("B" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
			cell = cells.getCell("C" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
      cell = cells.getCell("D" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);		
			cell = cells.getCell("E" + Integer.toString(i));	cell.setValue(""); 	style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
			cell = cells.getCell("F" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
			cell = cells.getCell("G" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
			cell = cells.getCell("H" + Integer.toString(i));	cell.setValue(totalTRUOCVAT); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
			ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
						
			cell = cells.getCell("I" + Integer.toString(i));	cell.setValue(totalVAT); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
			ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
			
			cell = cells.getCell("J" + Integer.toString(i));	cell.setValue(totalTRUOCVAT + totalVAT); 			style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
			ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
			
			i++;
			
		}
		catch (Exception e)
		{ 
			e.printStackTrace(); 
		}
	}
	  
  }

	
	
	
	private String getSearchQuery_ETC(HttpServletRequest request, IBCHoadonbanra obj, String view)
	{
		String tungay = request.getParameter("tungay");
	    if(tungay == null)
	    	tungay = "";
	    obj.setTungay(tungay);
    
	    String denngay = request.getParameter("denngay");
	    if(denngay == null)
	    	denngay = "";
	    obj.setDenngay(denngay);		    
		
		String condition = " and isnull(a.loaihoadon,0)=0 and a.kbh_fk=100052";
		String condition1 = "";
		
		
		Utility util = new Utility();
		
		String sql = 
						"select '' as ddkdTen ,  MA, TEN, DONVI, sum(soluong) as soluong, cast( ( sum(round( soluong * dongia,0) ) / sum(soluong) ) as numeric(18, 4) ) as dongia, "+ 			
						"					cast( (	sum( round( soluong * dongia,0 ) )) as numeric(18, 0) ) as doanhthu, avg(thuexuat) as thuexuat  ,   "+
						"  sum( round( round(soluong *DonGia,0) *(1+thuexuat/100),0) ) as AVAT   ,  "+
						" sum( round( round(soluong *DonGia,0) *(thuexuat/100),0) ) as VAT "+
				    "from  " +
				    "( " +
				    "	select d.MA, d.TEN, e.DONVI,  " +
				    "			case     when c.donvitinh = e.donvi then c.soluong     " +
				    "						  else c.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK )   end  as soluong,  " +
				    "			case     when c.donvitinh = e.donvi then c.dongia      " +
				    "						  else c.dongia * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK )   end  as dongia, c.vat as thuexuat,c.ChietKhau     " +
				    "	from ERP_HOADON a       " +
				    " \n   inner join ERP_HOADON_ddh dh on a.pk_seq = dh.hoadon_fk   "+
				    "\n    inner join ERP_dondathang ddh on ddh.pk_seq = dh.ddh_fk   "+
				    "		inner join ERP_HOADON_SP c on a.pk_seq = c.hoadon_fk      " +
				    "		inner join ERP_SANPHAM d on c.sanpham_fk = d.pk_seq   " +
				    "		inner join DONVIDOLUONG e on d.DVDL_FK = e.pk_seq   " +
				    "	where 1=1  and c.SOLUONG > 0  and a.trangthai not in ( 1 , 3, 5 )  and a.ngayxuatHD >= '" + obj.getTungay() + "'  " +
				    		"and  ddh.loaidonhang = 0 and isnull(isETC,0)=0 and  a.ngayxuatHD <= '" + obj.getDenngay() + "' " + condition +
				    ") " +
				    "ETC group by MA, TEN, DONVI  ";
		System.out.println("::::::::::::::::::::::::::"+sql);
		sql+=" order by MA  ";
		
	
		
		System.out.println("_______"+sql);
		return sql;
	}
	
	private String getSearchQuery_OTC(HttpServletRequest request, IBCHoadonbanra obj, String view)
	{
		String tungay = request.getParameter("tungay");
	    if(tungay == null)
	    	tungay = "";
	    obj.setTungay(tungay);
    
	    String denngay = request.getParameter("denngay");
	    if(denngay == null)
	    	denngay = "";
	    obj.setDenngay(denngay);		    
		
		String condition = " and isnull(a.loaihoadon,0)=0 and a.kbh_fk=100025";
		String condition1 = "";
		
		
		Utility util = new Utility();
		
		String sql = 
						"\n select '' as ddkdTen ,  MA, TEN, DONVI, sum(soluong) as soluong, cast( ( sum(round( soluong * dongia,0) ) / sum(soluong) ) as numeric(18, 4) ) as dongia, "+ 			
						"\n					cast( (	sum( round( soluong * dongia,0 ) )) as numeric(18, 0) ) as doanhthu, avg(thuexuat) as thuexuat  ,   "+
						"\n  sum( round( round(soluong *DonGia,0) *(1+thuexuat/100),0) ) as AVAT   ,  "+
						"\n sum( round( round(soluong *DonGia,0) *(thuexuat/100),0) ) as VAT "+
				    "\n from  " +
				    "\n ( " +
				    "\n	select d.MA, d.TEN, e.DONVI,  " +
				    "\n			case     when c.donvitinh = e.donvi then c.soluong     " +
				    "\n						  else c.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK )   end  as soluong,  " +
				    "\n			case     when c.donvitinh = e.donvi then c.dongia      " +
				    "\n						  else c.dongia * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK )   end  as dongia, c.vat as thuexuat,c.ChietKhau     " +
				    "\n	from ERP_HOADON a       " +
				    " \n   inner join ERP_HOADON_ddh dh on a.pk_seq = dh.hoadon_fk   "+
				    "\n    inner join ERP_dondathang ddh on ddh.pk_seq = dh.ddh_fk   "+
				    "\n		inner join ERP_HOADON_SP c on a.pk_seq = c.hoadon_fk      " +
				    "\n		inner join ERP_SANPHAM d on c.sanpham_fk = d.pk_seq   " +
				    "\n		inner join DONVIDOLUONG e on d.DVDL_FK = e.pk_seq   " +
				    "\n	where 1=1 and ddh.loaidonhang = 0 and isnull(isETC,0)=0 and c.SOLUONG > 0  and a.trangthai not in ( 1 , 3, 5 )  and a.ngayxuatHD >= '" + obj.getTungay() + "'  " +
				    		"\n and a.ngayxuatHD <= '" + obj.getDenngay() + "' " + condition +
				    "\n ) " +
				    "\n ETC group by MA, TEN, DONVI  ";
		System.out.println("::::::::::::::::::::::::::"+sql);
		sql+=" order by MA  ";
		
	
		
		System.out.println("_______OTC: "+sql);
		return sql;
	}
	
	
	private String getSearchQuery_KM(HttpServletRequest request, IBCHoadonbanra obj, String view)
	{
		String tungay = request.getParameter("tungay");
	    if(tungay == null)
	    	tungay = "";
	    obj.setTungay(tungay);
    
	    String denngay = request.getParameter("denngay");
	    if(denngay == null)
	    	denngay = "";
	    obj.setDenngay(denngay);		    
		
		String condition = " and isnull(a.loaihoadon,0)=1 and isnull(ddh.ishm,0)=0 ";
		String condition1 = "";
		
		
		Utility util = new Utility();
		
		String sql = 
						"select '' as ddkdTen ,  MA, TEN, DONVI, sum(soluong) as soluong, cast( ( sum(round( soluong * dongia,0) ) / sum(soluong) ) as numeric(18, 4) ) as dongia, "+ 			
						"\n					cast( (	sum( round( soluong * dongia,0 ) )) as numeric(18, 0) ) as doanhthu, avg(thuexuat) as thuexuat  ,   "+
						"\n  sum( round( round(soluong *DonGia,0) *(1+thuexuat/100),0) ) as AVAT   ,  "+
						"\n sum( round( round(soluong *DonGia,0) *(thuexuat/100),0) ) as VAT "+
				    "\n from  " +
				    "\n ( " +
				    "\n	select d.MA, d.TEN, e.DONVI,  " +
				    "\n			case     when c.donvitinh = e.donvi then c.soluong     " +
				    "\n						  else c.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK )   end  as soluong,  " +
				    "\n			case     when c.donvitinh = e.donvi then c.dongia      " +
				    "\n						  else c.dongia * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK )   end  as dongia, c.vat as thuexuat,c.ChietKhau     " +
				    "\n	from ERP_HOADON a       " +
				    " \n   inner join ERP_HOADON_ddh dh on a.pk_seq = dh.hoadon_fk   "+
				    "\n    inner join ERP_dondathang ddh on ddh.pk_seq = dh.ddh_fk   "+
				    "\n		inner join ERP_HOADON_SP c on a.pk_seq = c.hoadon_fk      " +
				    "\n		inner join ERP_SANPHAM d on c.sanpham_fk = d.pk_seq   " +
				    "\n		inner join DONVIDOLUONG e on d.DVDL_FK = e.pk_seq   " +
				    "\n	where 1=1  and ddh.loaidonhang = 4 and c.SOLUONG > 0  and a.trangthai not in ( 1 , 3, 5 )  and a.ngayxuatHD >= '" + obj.getTungay() + "'  " +
				     "\n and a.ngayxuatHD <= '" + obj.getDenngay() + "' " + condition +
				    "\n ) " +
				    "\n ETC group by MA, TEN, DONVI  ";
		System.out.println(":::::::::::::::::::::::::: HM"+sql);
		sql+=" order by MA  ";
		
	
		
		System.out.println("_______"+sql);
		return sql;
	}
	
	private String getSearchQuery_HM(HttpServletRequest request, IBCHoadonbanra obj, String view)
	{
		String tungay = request.getParameter("tungay");
	    if(tungay == null)
	    	tungay = "";
	    obj.setTungay(tungay);
    
	    String denngay = request.getParameter("denngay");
	    if(denngay == null)
	    	denngay = "";
	    obj.setDenngay(denngay);		    
		
		String condition = " and isnull(a.loaihoadon,0)=1 and isnull(ddh.ishm,0)=1 ";
		String condition1 = "";
		
		
		Utility util = new Utility();
		
		String sql = 
						"select '' as ddkdTen ,  MA, TEN, DONVI, sum(soluong) as soluong, cast( ( sum(round( soluong * dongia,0) ) / sum(soluong) ) as numeric(18, 4) ) as dongia, "+ 			
						"					cast( (	sum( round( soluong * dongia,0 ) )) as numeric(18, 0) ) as doanhthu, avg(thuexuat) as thuexuat  ,   "+
						"  sum( round( round(soluong *DonGia,0) *(1+thuexuat/100),0) ) as AVAT   ,  "+
						" sum( round( round(soluong *DonGia,0) *(thuexuat/100),0) ) as VAT "+
				    "from  " +
				    "( " +
				    "	select d.MA, d.TEN, e.DONVI,  " +
				    "			case     when c.donvitinh = e.donvi then c.soluong     " +
				    "						  else c.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK )   end  as soluong,  " +
				    "			case     when c.donvitinh = e.donvi then c.dongia      " +
				    "						  else c.dongia * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK )   end  as dongia, c.vat as thuexuat,c.ChietKhau     " +
				    "	from ERP_HOADON a       " +
				    "    inner join ERP_HOADON_ddh dh on a.pk_seq = dh.hoadon_fk   "+
				    "    inner join ERP_dondathang ddh on ddh.pk_seq = dh.ddh_fk   "+
				    "		inner join ERP_HOADON_SP c on a.pk_seq = c.hoadon_fk      " +
				    "		inner join ERP_SANPHAM d on c.sanpham_fk = d.pk_seq   " +
				    "		inner join DONVIDOLUONG e on d.DVDL_FK = e.pk_seq   " +
				    "	where 1=1  and c.SOLUONG > 0 and ddh.loaidonhang = 4 and a.trangthai not in ( 1 , 3, 5 )  and a.ngayxuatHD >= '" + obj.getTungay() + "'  " +
				    		"and a.ngayxuatHD <= '" + obj.getDenngay() + "' " + condition +
				    ") " +
				    "ETC group by MA, TEN, DONVI  ";
		System.out.println("::::::::::::::::::::::::::"+sql);
		sql+=" order by MA  ";
		
	
		
		System.out.println("_______"+sql);
		return sql;
	}
	
	
	private String getTotal_Query(HttpServletRequest request, IBCHoadonbanra obj, String view)
	{
		 String tungay = request.getParameter("tungay");
	    if(tungay == null)
	    	tungay = "";
	    obj.setTungay(tungay);
	    
	    String denngay = request.getParameter("denngay");
	    if(denngay == null)
	    	denngay = "";
	    obj.setDenngay(denngay);		    
	    String sql="";
		 
	    String condition ="";
	    String condition1="";
	    String conditionHDKhac= "";
	    
	  	Utility util = new Utility();
			
		/*	if(obj.getVung().trim().length() > 0)
			{
				condition += "and a.npp_fk in (Select pk_Seq  from nhaphanphoi where  khuvuc_Fk in (Select pk_Seq from khuvuc where vung_Fk='"+obj.getVung()+"' ) ) ";
				condition1 += "and a.npp_fk in (Select pk_Seq  from nhaphanphoi where  khuvuc_Fk in (Select pk_Seq from khuvuc where vung_Fk='"+obj.getVung()+"' ) ) ";
				conditionHDKhac += "and a.npp_fk in (Select pk_Seq  from nhaphanphoi where  khuvuc_Fk in (Select pk_Seq from khuvuc where vung_Fk='"+obj.getVung()+"' ) ) ";
			}
			
			if(obj.getKhuvuc().trim().length() > 0)
			{
				condition += " and a.npp_fk in (Select pk_Seq from nhaphanphoi where khuvuc_Fk='"+obj.getKhuvuc()+"')";
				condition1 += " and a.npp_fk in (Select pk_Seq from nhaphanphoi where khuvuc_Fk='"+obj.getKhuvuc()+"')";
				conditionHDKhac += " and a.npp_fk in (Select pk_Seq from nhaphanphoi where khuvuc_Fk='"+obj.getKhuvuc()+"')";
			}
			
			if(obj.getNppId().trim().length() > 0)
			{
				condition += " and a.npp_fk ='"+obj.getNppId()+"' ";
				condition1 += "and a.NPP_FK=(select TRUCTHUOC_FK from NHAPHANPHOI where PK_SEQ='"+obj.getNppId()+"' and a.KHACHHANG_FK in (select pk_seq from KHACHHANG where TrucThuoc_FK='"+obj.getNppId()+"'))";	
				conditionHDKhac += " and a.npp_fk ='"+obj.getNppId()+"' ";
			}

			if(view.length()>0)
			{
				condition += " and a.npp_Fk in "+util.quyen_npp(obj.getUserId() ) +" ";
				condition1 += " and a.npp_Fk in "+util.quyen_npp(obj.getUserId() ) +" ";
				conditionHDKhac += " and a.npp_Fk in "+util.quyen_npp(obj.getUserId() ) +" ";
			}
			
		
			
			String		condition_ETC="";
			String		condition_OTC="";
			String		condition_OTC1="";
			String		condition_ETC1="";
			String      condition_HDKhac="";
			
			if(obj.getTdvId().length()>0)
			{
				//condition_ETC = " and a.pk_seq  in (select HOADONNPP_FK from ERP_HOADONNPP_DDH where DDH_FK  in (select pk_seq from ERP_DONDATHANGNPP  where ddkd_fk='"+obj.getTdvId()+"') )  ";
			//	condition_ETC1 = " and a.pk_seq  in (select HOADONNPP_FK from ERP_HOADONNPP_DDH where DDH_FK  in (select pk_seq from ERP_DONDATHANGNPP  where ddkd_fk='"+obj.getTdvId()+"') )  ";
				condition_ETC +=
						 " and a.khachhang_Fk in   	( select cc.khachhang_Fk from DAIDIENKINHDOANH aa inner join TUYENBANHANG bb on bb.DDKD_FK=aa.PK_SEQ   \n "+      
						 "   		inner join KHACHHANG_TUYENBH cc on cc.TBH_FK=bb.PK_SEQ  " +
						 "          where aa.pk_Seq='"+obj.getTdvId()+"'      \n "+      
						 "   	) ";
				condition_ETC1 +=
						 " and a.khachhang_Fk in   	( select cc.khachhang_Fk from DAIDIENKINHDOANH aa inner join TUYENBANHANG bb on bb.DDKD_FK=aa.PK_SEQ   \n "+      
						 "   		inner join KHACHHANG_TUYENBH cc on cc.TBH_FK=bb.PK_SEQ  " +
						 "          where aa.pk_Seq='"+obj.getTdvId()+"'      \n "+      
						 "   	) ";
				condition_HDKhac +=
					 " and a.khachhang_Fk in   	( select cc.khachhang_Fk from DAIDIENKINHDOANH aa inner join TUYENBANHANG bb on bb.DDKD_FK=aa.PK_SEQ   \n "+      
					 "   		inner join KHACHHANG_TUYENBH cc on cc.TBH_FK=bb.PK_SEQ  " +
					 "          where aa.pk_Seq='"+obj.getTdvId()+"'      \n "+      
					 "   	) ";
			
			}
			
			if(obj.getTdvId().length()>0)
			{
				//condition_OTC = 	condition += " and a.pk_seq  in (select hoadon_Fk from hoadon_ddh where DDH_FK  in (select pk_seq from donhang  where ddkd_fk='"+obj.getTdvId()+"') )  ";
				//condition_OTC1 = 	condition += " and a.pk_seq  in (select hoadon_Fk from hoadon_ddh where DDH_FK  in (select pk_seq from donhang  where ddkd_fk='"+obj.getTdvId()+"') )  ";
				condition_OTC +=
						 " and a.khachhang_Fk in   	( select cc.khachhang_Fk from DAIDIENKINHDOANH aa inner join TUYENBANHANG bb on bb.DDKD_FK=aa.PK_SEQ   \n "+      
						 "   		inner join KHACHHANG_TUYENBH cc on cc.TBH_FK=bb.PK_SEQ  " +
						 "          where aa.pk_Seq='"+obj.getTdvId()+"'      \n "+      
						 "   	) ";
				condition_OTC1 +=
						 " and a.khachhang_Fk in   	( select cc.khachhang_Fk from DAIDIENKINHDOANH aa inner join TUYENBANHANG bb on bb.DDKD_FK=aa.PK_SEQ   \n "+      
						 "   		inner join KHACHHANG_TUYENBH cc on cc.TBH_FK=bb.PK_SEQ  " +
						 "          where aa.pk_Seq='"+obj.getTdvId()+"'      \n "+      
						 "   	) ";
		
			
			}
			
			if(obj.getKhoId().length()>0)
			{
				condition_OTC += " and a.pk_seq  in (select hoadon_Fk from hoadon_ddh where DDH_FK  in (select pk_seq from donhang  where kho_Fk='"+obj.getKhoId()+"') )  ";
				condition_OTC1 += " and a.pk_seq  in (select hoadon_Fk from hoadon_ddh where DDH_FK  in (select pk_seq from donhang  where kho_Fk='"+obj.getKhoId()+"') )  ";
			}
			
			if(obj.getKhoId().length()>0)
			{
				condition_ETC += " and a.pk_seq  in (select HOADONNPP_FK from ERP_HOADONNPP_DDH where DDH_FK  in (select pk_seq from ERP_DONDATHANGNPP  where kho_fk='"+obj.getKhoId()+"') ) ";
				condition_ETC1 += " and a.pk_seq  in (select HOADONNPP_FK from ERP_HOADONNPP_DDH where DDH_FK  in (select pk_seq from ERP_DONDATHANGNPP  where kho_fk='"+obj.getKhoId()+"') ) ";
			}
			
			if(obj.getMuc_KhachHang().equals("1")&&obj.getMucCN_DT().equals("0"))
			{
				condition_ETC += 	 " and  a.KhachHang_fk is not null ";
				condition_ETC1 += 	 " and  a.KhachHang_fk is not null ";
				condition_HDKhac += 	 " and  a.KhachHang_fk is not null ";
			}
			if(obj.getMucCN_DT().equals("1")&&obj.getMuc_KhachHang().equals("0"))
			{
				condition_ETC += 	 " and  a.npp_dat_fk is not null ";
				condition_ETC1 += 	 " and  a.npp_dat_fk is not null ";
			}
			*/
			
	sql=	"	select SUM(VAT) as VAT,SUM(BVAT) as BVAT,SUM(AVAT) as AVAT  from   ("+
			"	select SUM(VAT) as VAT,SUM(BVAT) as BVAT,SUM(AVAT) as AVAT   \n"+
			"	from   \n"+
			"	(   \n"+
			"		select   \n"+
			"			sum(soluong) as soluong,  ( sum( soluong * dongia ) / sum(soluong) ) as dongia,   "+ 
		  " 		sum( ROUND(soluong * dongia,0 ))-sum( ROUND(ck,0 )) as BVAT,   		  \n"+
		  " 		sum(ROUND(( ROUND (soluong * dongia , 0 ) ) *( thuexuat/100.0),0 ))- sum(ROUND(( ROUND (ck , 0 ) ) *( thuexuat/100.0),0 ))as VAT, \n"+    		
			"   	sum(ROUND( ( ROUND (soluong * dongia , 0 ) )   * (1+ thuexuat/100.0),0 ))- sum(ROUND(( ROUND (ck , 0 ) ) *( 1+ thuexuat/100.0),0 )) as AVAT \n"+   					
			"		from (   \n"+
			"				select d.MA, d.TEN, e.DONVI,  \n"+ 
			"					case when c.donvitinh = e.donvi then c.soluong else c.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as soluong,  \n"+ 
			"					case when c.donvitinh = e.donvi then c.dongia else c.dongia * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as dongia, c.vat as thuexuat,ROUND( ISNULL(c.ChietKhau ,0), 0) as ck  \n"+ 
			"			from ERP_HOADON a   \n"+
			"				inner join ERP_HOADON_SP c on a.pk_seq = c.hoadon_fk   \n"+ 
			"				inner join ERP_SANPHAM d on c.sanpham_fk = d.pk_seq   \n"+
			"				inner join DONVIDOLUONG e on d.DVDL_FK = e.pk_seq   \n"+
			"				where 1=1 and c.SOLUONG > 0 and a.trangthai not in ( 1 , 3, 5 ) and a.NgayXuatHD>='"+tungay+"' and a.NgayXuatHD<='"+denngay+"'  \n"+ 
			"		) ETC  \n"+ 
			")as total "; 
			System.out.println("total 1"+sql);
	
	sql+= ") as t";
		System.out.println("___SUM_QUERY___: " + sql);
		return sql;
	}
	

	private void CreateStaticHeader(Workbook workbook, IBCHoadonbanra obj) 
	{
		Worksheets worksheets = workbook.getWorksheets();
		
		
		/***************************SHEET 1 - KM ***********************************/
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    Cells cells = worksheet.getCells();
	    Style style;
	   	    
	    //LẤY THÔNG TIN NPP
	    dbutils db = new dbutils();
	    String ten = "";
	    String diachi = "";
	    String dienthoai = "";
	    String fax = "";
	    
	    if(obj.getNppId().length()>0)
	    {
		    String query = "select ten, diachi, dienthoai, fax from NHAPHANPHOI where pk_seq = '" + obj.getNppId() + "'";
		    ResultSet rs = db.get(query);
		    
		  
				    if(rs != null)
				    {
				    	try 
				    	{
				    		if(rs.next())
				    		{
					    		ten = rs.getString("ten");
					    		diachi = rs.getString("diachi");
					    		dienthoai = rs.getString("dienthoai");
					    		fax = rs.getString("fax");
				    		}
				    		
							rs.close();
						} 
				    	catch (Exception e) { e.printStackTrace(); }
				    }
	    }
	    Cell cell = cells.getCell("D1"); 
	    cell.setValue(ten.toUpperCase());	    
	    style = cell.getStyle();
		Font font2 = new Font();	
		font2.setName("Times New Roman");
		font2.setSize(11);
		font2.setBold(false);
		style.setFont(font2);
		style.setHAlignment(TextAlignmentType.CENTER);					
		cell.setStyle(style);
		
		
		font2 = new Font();	
		font2.setName("Times New Roman");
		font2.setSize(12);
		font2.setBold(false);
		style.setFont(font2);
		style.setHAlignment(TextAlignmentType.CENTER);					
		cell.setStyle(style);
		
		cell = cells.getCell("D2");
	    //cell.setValue("Địa chỉ: " + diachi);
	    style = cell.getStyle();
	    style.setFont(font2);
		cell.setStyle(style);
		
		font2 = new Font();	
		font2.setName("Times New Roman");
		font2.setSize(12);
		font2.setBold(false);
		style.setFont(font2);
		style.setHAlignment(TextAlignmentType.CENTER);					
		cell.setStyle(style);
		
		cell = cells.getCell("D3");
	    //ell.setValue("Tel: " + dienthoai + " - Fax: " + fax );
	    style = cell.getStyle();
	    style.setFont(font2);
		cell.setStyle(style);
		
		
		/*font2 = new Font();	
		font2.setName("Times New Roman");
		font2.setSize(16);
		font2.setBold(true);
		style.setFont(font2);
		style.setHAlignment(TextAlignmentType.CENTER);					
		cell.setStyle(style);
		
		cell = cells.getCell("A6");
	    cell.setValue("BÁO CÁO BÁN HÀNG CHI TIẾT THEO MẶT HÀNG - OTC");
	    style = cell.getStyle();
	    style.setFont(font2);
		cell.setStyle(style);*/
		
		font2 = new Font();	
		font2.setName("Times New Roman");
		font2.setBold(false);
		font2.setSize(12);
	   
	    cell = cells.getCell("A8");
	    cell.setValue("Từ ngày: " + getFormatDate(obj.getTungay()) + " đến ngày: " + getFormatDate(obj.getDenngay()) );
	    style = cell.getStyle();
	    style.setFont(font2);
		cell.setStyle(style);
	    
	    
	    /***************************END SHEET 1 - KM ***********************************/
	    
	    
	    /***************************SHEET 1 - ETC ***********************************/
	    Worksheet worksheet2 = worksheets.getSheet(1);
	    cells = worksheet2.getCells();
	    
	    cell = cells.getCell("D1"); 
	    cell.setValue(ten.toUpperCase());	    
	    style = cell.getStyle();
		font2 = new Font();	
		font2.setName("Times New Roman");
		font2.setSize(11);
		font2.setBold(false);
		style.setFont(font2);
		style.setHAlignment(TextAlignmentType.CENTER);					
		cell.setStyle(style);
		
		
		font2 = new Font();	
		font2.setName("Times New Roman");
		font2.setSize(12);
		font2.setBold(false);
		style.setFont(font2);
		style.setHAlignment(TextAlignmentType.CENTER);					
		cell.setStyle(style);
		
		cell = cells.getCell("D2");
	  //  cell.setValue("Địa chỉ: " + diachi);
	    style = cell.getStyle();
	    style.setFont(font2);
		cell.setStyle(style);
		
		font2 = new Font();	
		font2.setName("Times New Roman");
		font2.setSize(12);
		font2.setBold(false);
		style.setFont(font2);
		style.setHAlignment(TextAlignmentType.CENTER);					
		cell.setStyle(style);
		
		cell = cells.getCell("D3");
	  //  cell.setValue("Tel: " + dienthoai + " - Fax: " + fax );
	    style = cell.getStyle();
	    style.setFont(font2);
		cell.setStyle(style);
		

	   
	    cell = cells.getCell("A7");
	    cell.setValue("Từ ngày: " + getFormatDate(obj.getTungay()) + " đến ngày: " + getFormatDate(obj.getDenngay()) );
	    style = cell.getStyle();
	    style.setFont(font2);
		cell.setStyle(style);
	    
	    
	    /***************************END SHEET 2 - ETC ***********************************/
	    
		
		Worksheet  worksheet3 = worksheets.getSheet(2);
	    cells = worksheet3.getCells();
	    
	    cell = cells.getCell("D1"); 
	    cell.setValue(ten.toUpperCase());	    
	    style = cell.getStyle();
		font2 = new Font();	
		font2.setName("Times New Roman");
		font2.setSize(11);
		font2.setBold(false);
		style.setFont(font2);
		style.setHAlignment(TextAlignmentType.CENTER);					
		cell.setStyle(style);
		
		
		font2 = new Font();	
		font2.setName("Times New Roman");
		font2.setSize(12);
		font2.setBold(false);
		style.setFont(font2);
		style.setHAlignment(TextAlignmentType.CENTER);					
		cell.setStyle(style);
		
		cell = cells.getCell("D2");
	   // cell.setValue("Địa chỉ: " + diachi);
	    style = cell.getStyle();
	    style.setFont(font2);
		cell.setStyle(style);
		
		font2 = new Font();	
		font2.setName("Times New Roman");
		font2.setSize(12);
		font2.setBold(false);
		style.setFont(font2);
		style.setHAlignment(TextAlignmentType.CENTER);					
		cell.setStyle(style);
		
		cell = cells.getCell("D3");
	   // cell.setValue("Tel: " + dienthoai + " - Fax: " + fax );
	    style = cell.getStyle();
	    style.setFont(font2);
		cell.setStyle(style);
		

	   
	    cell = cells.getCell("A7");
	    cell.setValue("Từ ngày: " + getFormatDate(obj.getTungay()) + " đến ngày: " + getFormatDate(obj.getDenngay()) );
	    style = cell.getStyle();
	    style.setFont(font2);
		cell.setStyle(style);
	    
		
		
	    
		Worksheet  worksheet4 = worksheets.getSheet(3);
	    cells = worksheet4.getCells();
	    
	    cell = cells.getCell("D1"); 
	    cell.setValue(ten.toUpperCase());	    
	    style = cell.getStyle();
		font2 = new Font();	
		font2.setName("Times New Roman");
		font2.setSize(11);
		font2.setBold(false);
		style.setFont(font2);
		style.setHAlignment(TextAlignmentType.CENTER);					
		cell.setStyle(style);
		
		
		font2 = new Font();	
		font2.setName("Times New Roman");
		font2.setSize(12);
		font2.setBold(false);
		style.setFont(font2);
		style.setHAlignment(TextAlignmentType.CENTER);					
		cell.setStyle(style);
		
		cell = cells.getCell("D2");
	//    cell.setValue("Địa chỉ: " + diachi);
	    style = cell.getStyle();
	    style.setFont(font2);
		cell.setStyle(style);
		
		font2 = new Font();	
		font2.setName("Times New Roman");
		font2.setSize(12);
		font2.setBold(false);
		style.setFont(font2);
		style.setHAlignment(TextAlignmentType.CENTER);					
		cell.setStyle(style);
		
		cell = cells.getCell("D3");
	 //   cell.setValue("Tel: " + dienthoai + " - Fax: " + fax );
	    style = cell.getStyle();
	    style.setFont(font2);
		cell.setStyle(style);
		

	   
	    cell = cells.getCell("A7");
	    cell.setValue("Từ ngày: " + getFormatDate(obj.getTungay()) + " đến ngày: " + getFormatDate(obj.getDenngay()) );
	    style = cell.getStyle();
	    style.setFont(font2);
		cell.setStyle(style);
	    
	    
	    
	}

	private void CreateStaticData_KM(Workbook workbook, IBCHoadonbanra obj, String view, String search_KM ) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet("KM");
	    Cells cells = worksheet.getCells();
	   
	    String query = search_KM;
	    
	    dbutils db = new dbutils();
	    ResultSet hdRs = db.get(query);

		Cell cell_mau = cells.getCell("A10");
		Cell cell = null;
		
		Style style;
		Font font2 = new Font();
		font2.setName("Times New Roman");				
		font2.setSize(8);
		
		Font font3 = new Font();
		font3.setName("Times New Roman");				
		font3.setSize(8);
		font3.setBold(true);
		
		int i = 11;
		if(hdRs != null)
		{
			try 
			{
				int stt = 1;
				double totalTRUOCVAT = 0;
				double totalVAT = 0;
				
				while(hdRs.next())
				{
					totalTRUOCVAT += 0;
					totalVAT +=0;
					
					double thueVAT = 0;

					cell = cells.getCell("A" + Integer.toString(i));	cell.setValue(stt); 			style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
					cell = cells.getCell("B" + Integer.toString(i));	cell.setValue(hdRs.getString("MA")); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("C" + Integer.toString(i));	cell.setValue(hdRs.getString("TEN")); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("D" + Integer.toString(i));	cell.setValue(hdRs.getString("DONVI")); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);		
					cell = cells.getCell("E" + Integer.toString(i));	cell.setValue(hdRs.getDouble("SOLUONG")); 	style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
					
					cell = cells.getCell("F" + Integer.toString(i));	cell.setValue(0); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					style.setCustom("#,##0.0000");
					cell.setStyle(style);

					cell = cells.getCell("G" + Integer.toString(i));	cell.setValue(0); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
					
					cell = cells.getCell("H" + Integer.toString(i));	cell.setValue(thueVAT); 			style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
					
					cell = cells.getCell("I" + Integer.toString(i));	cell.setValue(0); 		
					style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);					
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
					

					i++;
					stt ++;
				}
				hdRs.close();
				
				
				//THEM DONG TONG CONG
				cell = cells.getCell("A" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
				cell = cells.getCell("B" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
        		cell = cells.getCell("C" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);		
				cell = cells.getCell("D" + Integer.toString(i));	cell.setValue(""); 	style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
				cell = cells.getCell("E" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
				cell = cells.getCell("F" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
				cell = cells.getCell("G" + Integer.toString(i));	cell.setValue(totalTRUOCVAT); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
							
				cell = cells.getCell("H" + Integer.toString(i));	cell.setValue(totalVAT); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
				
				cell = cells.getCell("I" + Integer.toString(i));	cell.setValue(totalTRUOCVAT + totalVAT); 			style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
				
				i++;
				
			}
			catch (Exception e)
			{ 
				e.printStackTrace(); 
			}
		}
	}
	
	private void CreateStaticData_HM(Workbook workbook, IBCHoadonbanra obj, String view, String search_KM ) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet("HM");
	    Cells cells = worksheet.getCells();
	   
	    String query = search_KM;
	    
	    dbutils db = new dbutils();
	    ResultSet hdRs = db.get(query);

		Cell cell_mau = cells.getCell("A10");
		Cell cell = null;
		
		Style style;
		Font font2 = new Font();
		font2.setName("Times New Roman");				
		font2.setSize(8);
		
		Font font3 = new Font();
		font3.setName("Times New Roman");				
		font3.setSize(8);
		font3.setBold(true);
		
		int i = 11;
		if(hdRs != null)
		{
			try 
			{
				int stt = 1;
				double totalTRUOCVAT = 0;
				double totalVAT = 0;
				
				while(hdRs.next())
				{
					totalTRUOCVAT += Math.round(hdRs.getDouble("DOANHTHU"));
					totalVAT += Math.round(hdRs.getDouble("thuexuat") * hdRs.getDouble("DOANHTHU") / 100);
					
					double thueVAT = Math.round(hdRs.getDouble("thuexuat") * hdRs.getDouble("DOANHTHU") / 100);

					cell = cells.getCell("A" + Integer.toString(i));	cell.setValue(stt); 			style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
					cell = cells.getCell("B" + Integer.toString(i));	cell.setValue(hdRs.getString("MA")); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("C" + Integer.toString(i));	cell.setValue(hdRs.getString("TEN")); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("D" + Integer.toString(i));	cell.setValue(hdRs.getString("DONVI")); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);		
					cell = cells.getCell("E" + Integer.toString(i));	cell.setValue(hdRs.getDouble("SOLUONG")); 	style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
					
					cell = cells.getCell("F" + Integer.toString(i));	cell.setValue(hdRs.getDouble("DONGIA")); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					style.setCustom("#,##0.0000");
					cell.setStyle(style);

					cell = cells.getCell("G" + Integer.toString(i));	cell.setValue(hdRs.getDouble("DOANHTHU")); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
					
					cell = cells.getCell("H" + Integer.toString(i));	cell.setValue(thueVAT); 			style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
					
					cell = cells.getCell("I" + Integer.toString(i));	cell.setValue(hdRs.getDouble("DOANHTHU") + thueVAT); 		
					style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);					
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
					

					i++;
					stt ++;
				}
				hdRs.close();
				
				
				//THEM DONG TONG CONG
				cell = cells.getCell("A" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
				cell = cells.getCell("B" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
        		cell = cells.getCell("C" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);		
				cell = cells.getCell("D" + Integer.toString(i));	cell.setValue(""); 	style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
				cell = cells.getCell("E" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
				cell = cells.getCell("F" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
				cell = cells.getCell("G" + Integer.toString(i));	cell.setValue(totalTRUOCVAT); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
							
				cell = cells.getCell("H" + Integer.toString(i));	cell.setValue(totalVAT); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
				
				cell = cells.getCell("I" + Integer.toString(i));	cell.setValue(totalTRUOCVAT + totalVAT); 			style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
				
				i++;
				
			}
			catch (Exception e)
			{ 
				e.printStackTrace(); 
			}
		}
	}
	
	
	private void CreateStaticData_HDKHAC(Workbook workbook, IBCHoadonbanra obj, String view, String search_HDKhac ) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(3);
	    Cells cells = worksheet.getCells();
	    String query=search_HDKhac;
	    
	    dbutils db = new dbutils();
	    ResultSet hdRs = db.get(query);

		Cell cell_mau = cells.getCell("A9");
		Cell cell = null;
		
		Style style;
		Font font2 = new Font();
		font2.setName("Times New Roman");				
		font2.setSize(8);
		
		Font font3 = new Font();
		font3.setName("Times New Roman");				
		font3.setSize(8);
		font3.setBold(true);
		
		int i = 10;
		if(hdRs != null)
		{
			try 
			{
				int stt = 1;
				double totalTRUOCVAT = 0;
				double totalVAT = 0;
				
				while(hdRs.next())
				{
					totalTRUOCVAT += Math.round(hdRs.getDouble("DOANHTHU"));
					totalVAT += hdRs.getDouble("VAT");

          cell = cells.getCell("A" + Integer.toString(i));	cell.setValue(stt); 			style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
					cell = cells.getCell("B" + Integer.toString(i));	cell.setValue(hdRs.getString("MA")); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("C" + Integer.toString(i));	cell.setValue(hdRs.getString("TEN")); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("D" + Integer.toString(i));	cell.setValue(hdRs.getString("DONVI")); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);		
					cell = cells.getCell("E" + Integer.toString(i));	cell.setValue(hdRs.getDouble("SOLUONG")); 	style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
					
					
					cell = cells.getCell("F" + Integer.toString(i));	cell.setValue(hdRs.getDouble("DONGIA")); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					style.setCustom("#,##0.0000");
					cell.setStyle(style);
					
					cell = cells.getCell("G" + Integer.toString(i));	cell.setValue(Math.round(hdRs.getDouble("DOANHTHU"))); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
					
					cell = cells.getCell("H" + Integer.toString(i));	cell.setValue(hdRs.getDouble("VAT")); 			style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
					
					cell = cells.getCell("I" + Integer.toString(i));	cell.setValue( hdRs.getDouble("AVAT")  ); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
					

					i++;
					stt ++;
				}
				hdRs.close();
				
				
				String condition="";
				String condition1="";
				if(obj.getNppId().length()>0 )
				{
					condition+=" and NPP_FK = '" + obj.getNppId() + "' ";
	 				condition1 += "and a.NPP_FK=(select TRUCTHUOC_FK from NHAPHANPHOI where PK_SEQ='"+obj.getNppId()+"' and a.KHACHHANG_FK in (select pk_seq from KHACHHANG where TrucThuoc_FK='"+obj.getNppId()+"'))";	

				}
				if(obj.getVung().trim().length() > 0)
				{
					condition += " and npp_fk in (Select pk_Seq  from nhaphanphoi where  khuvuc_Fk in (Select pk_Seq from khuvuc where vung_Fk='"+obj.getVung()+"' ) ) ";
					condition1 += " and npp_fk in (Select pk_Seq  from nhaphanphoi where  khuvuc_Fk in (Select pk_Seq from khuvuc where vung_Fk='"+obj.getVung()+"' ) ) ";
				}
				
				if(obj.getKhuvuc().trim().length() > 0)
				{
					condition += " and npp_fk in (Select pk_Seq from nhaphanphoi where khuvuc_Fk='"+obj.getKhuvuc()+"')";
					condition1 += " and npp_fk in (Select pk_Seq from nhaphanphoi where khuvuc_Fk='"+obj.getKhuvuc()+"')";
					 
	 			}
				
				if(obj.getTungay().length()>0)
				{
					condition+= "and ngayxuatHD >= '" + obj.getTungay() + "'";
					condition1+= "and ngayxuatHD >= '" + obj.getTungay() + "'";
				}
				if(obj.getDenngay().length()>0)
				{
					condition+= "and ngayxuatHD <= '" + obj.getDenngay() + "' ";
					condition1+= "and ngayxuatHD <= '" + obj.getDenngay() + "' ";
				}
				
				if(obj.getTdvId().length()>0)
				{
					//condition += " and pk_seq  in (select hoadon_Fk from hoadon_ddh where DDH_FK  in (select pk_seq from donhang  where ddkd_fk='"+obj.getTdvId()+"') )  ";
					//condition1 += " and pk_seq  in (select hoadon_Fk from hoadon_ddh where DDH_FK  in (select pk_seq from donhang  where ddkd_fk='"+obj.getTdvId()+"') )  ";
					condition +=
							 " and a.khachhang_Fk in   	( select cc.khachhang_Fk from DAIDIENKINHDOANH aa inner join TUYENBANHANG bb on bb.DDKD_FK=aa.PK_SEQ   \n "+      
							 "   		inner join KHACHHANG_TUYENBH cc on cc.TBH_FK=bb.PK_SEQ  " +
							 "          where aa.pk_Seq='"+obj.getTdvId()+"'      \n "+      
							 "   	) ";
					condition1 +=
							 " and a.khachhang_Fk in   	( select cc.khachhang_Fk from DAIDIENKINHDOANH aa inner join TUYENBANHANG bb on bb.DDKD_FK=aa.PK_SEQ   \n "+      
							 "   		inner join KHACHHANG_TUYENBH cc on cc.TBH_FK=bb.PK_SEQ  " +
							 "          where aa.pk_Seq='"+obj.getTdvId()+"'      \n "+      
							 "   	) ";
		
				}
				
				if(obj.getKhoId().length()>0)
				{
					condition += " and pk_seq  in (select hoadon_Fk from hoadon_ddh where DDH_FK  in (select pk_seq from donhang  where kho_fk='"+obj.getKhoId()+"') )  ";
					condition1 += " and pk_seq  in (select hoadon_Fk from hoadon_ddh where DDH_FK  in (select pk_seq from donhang  where kho_fk='"+obj.getKhoId()+"') )  ";
				}
				
				
				Utility util = new Utility();
				if(view.length()>0)
				{
					query += " and npp_Fk in "+util.quyen_npp(obj.getUserId() ) +" ";
				}
			
				
				//THEM DONG TONG CONG
				cell = cells.getCell("A" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
				cell = cells.getCell("B" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
				cell = cells.getCell("C" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);		
				cell = cells.getCell("D" + Integer.toString(i));	cell.setValue(""); 	style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
				cell = cells.getCell("E" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
				cell = cells.getCell("F" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
				cell = cells.getCell("G" + Integer.toString(i));	cell.setValue(Math.round(totalTRUOCVAT)); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
				
				cell = cells.getCell("H" + Integer.toString(i));	cell.setValue( Math.round( totalVAT)); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
				
				cell = cells.getCell("I" + Integer.toString(i));	cell.setValue(Math.round( totalTRUOCVAT + totalVAT )); 			style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
				
				i++;
				
			}
			catch (Exception e)
			{ 
				e.printStackTrace(); 
			}
		}
	}
	
	private String getSearchQuery_HDKHAC(HttpServletRequest request, IBCHoadonbanra obj, String view)
	{
		 String tungay = request.getParameter("tungay");
		    if(tungay == null)
		    	tungay = "";
		    obj.setTungay(tungay);
		    
		    String denngay = request.getParameter("denngay");
		    if(denngay == null)
		    	denngay = "";
		    obj.setDenngay(denngay);		    
		    String sql="";
			 
		    String condition ="";
		    
		  	Utility util = new Utility();
				
				if(obj.getVung().trim().length() > 0)
	 			{
					condition += "and a.npp_fk in (Select pk_Seq  from nhaphanphoi where  khuvuc_Fk in (Select pk_Seq from khuvuc where vung_Fk='"+obj.getVung()+"' ) ) ";
					
				 }
				
	 			if(obj.getKhuvuc().trim().length() > 0)
	 			{
	 				condition += " and a.npp_fk in (Select pk_Seq from nhaphanphoi where khuvuc_Fk='"+obj.getKhuvuc()+"')";
	 				
	 			}
	 			
	 			if(obj.getNppId().trim().length() > 0)
	 			{
	 					
	 				condition += " and a.npp_fk ='"+obj.getNppId()+"' ";
	 			}

	 			if(view.length()>0)
				{
					condition += " and a.npp_Fk in "+util.quyen_npp(obj.getUserId() ) +" ";
					
				}
	 			
	 			if(obj.getTdvId().length()>0)
				{
					//condition += " and a.pk_seq  in (select hoadon_Fk from hoadon_ddh where DDH_FK  in (select pk_seq from donhang  where ddkd_fk='"+obj.getTdvId()+"') )  ";
				//	condition1 += " and a.pk_seq  in (select hoadon_Fk from hoadon_ddh where DDH_FK  in (select pk_seq from donhang  where ddkd_fk='"+obj.getTdvId()+"') )  ";
					condition +=
							 " and a.khachhang_Fk in   	( select cc.khachhang_Fk from DAIDIENKINHDOANH aa inner join TUYENBANHANG bb on bb.DDKD_FK=aa.PK_SEQ   \n "+      
							 "   		inner join KHACHHANG_TUYENBH cc on cc.TBH_FK=bb.PK_SEQ  " +
							 "          where aa.pk_Seq='"+obj.getTdvId()+"'      \n "+      
							 "   	) ";
				
		
				}
	 			
	 			if(obj.getKhoId().length()>0)
				{
					condition += " and a.pk_seq  in (select hoadon_Fk from hoadon_ddh where DDH_FK  in (select pk_seq from donhang  where kho_Fk='"+obj.getKhoId()+"') )  ";
					
				}
	 			 			
	 			
		sql= "select    '' as ddkdTen, c.diengiai MA, c.diengiai TEN, c.donvitinh DONVI, c.soluong as soluong, c.DonGia  as dongia, round(c.soluong * c.DonGia,0) as doanhthu, c.thuevat as thuexuat, c.thanhtien  as AVAT,    " +
			" c.vat as VAT "+
	   		"from ERP_HOADONPHELIEU a      " +
	   		"	inner join erp_hoadonphelieu_sanpham c on a.pk_seq = c.hoadonphelieu_fk     " +
	   		"where c.SoLuong>0   and a.trangthai = 1 and a.ngayhoadon >= '" + tungay + "'  and a.ngayhoadon <= '" + denngay + "' "+condition+" ";
		 		
		
		if(obj.getMuclay().equals("1"))
		{
				sql= "select    '' as ddkdTen, c.diengiai MA, c.diengiai TEN, c.donvitinh DONVI, c.soluong as soluong, c.DonGia  as dongia, round(c.soluong * c.DonGia,0) as doanhthu, c.thuevat as thuexuat, c.thanhtien  as AVAT,    " +
				" c.vat as VAT "+
		   		"from ERP_HOADONPHELIEU a      " +
		   		"	inner join erp_hoadonphelieu_sanpham c on a.pk_seq = c.hoadonphelieu_fk     " +
		   		"where c.SoLuong>0   and a.trangthai = 1 and a.ngayhoadon >= '" + tungay + "'  and a.ngayhoadon <= '" + denngay + "' "+condition+" ";
			
		}
		System.out.println("---LAY HDKHAC: " + sql );
			return sql;
	}
	
	private void CreateStaticData_ETC(Workbook workbook, IBCHoadonbanra obj, String view, String search_ETC ) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet("ETC");
	    Cells cells = worksheet.getCells();

	    String query = search_ETC;
	    dbutils db = new dbutils();
	    ResultSet hdRs = db.get(query);
		
	    Cell cell_mau = cells.getCell("A9");
	    Cell cell = null;
		
		Style style;
		Font font2 = new Font();
		font2.setName("Times New Roman");				
		font2.setSize(8);
		
		Font font3 = new Font();
		font3.setName("Times New Roman");				
		font3.setSize(8);
		font3.setBold(true);
		
		int i = 10;
		if(hdRs != null)
		{
			try 
			{
				int stt = 1;
				double totalTRUOCVAT = 0;
				double totalVAT = 0;
				
				while(hdRs.next())
				{
					totalTRUOCVAT += Math.round(hdRs.getDouble("DOANHTHU"));
					totalVAT += hdRs.getDouble("VAT");

					cell = cells.getCell("A" + Integer.toString(i));	cell.setValue(stt); 			style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
					cell = cells.getCell("B" + Integer.toString(i));	cell.setValue(hdRs.getString("MA")); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("C" + Integer.toString(i));	cell.setValue(hdRs.getString("TEN")); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("D" + Integer.toString(i));	cell.setValue(hdRs.getString("DONVI")); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);		
					cell = cells.getCell("E" + Integer.toString(i));	cell.setValue(hdRs.getDouble("SOLUONG")); 	style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
					
					
					cell = cells.getCell("F" + Integer.toString(i));	cell.setValue(hdRs.getDouble("DONGIA")); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					style.setCustom("#,##0.0000");
					cell.setStyle(style);
					
					
					cell = cells.getCell("G" + Integer.toString(i));	cell.setValue(Math.round(hdRs.getDouble("DOANHTHU"))); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
					
					cell = cells.getCell("H" + Integer.toString(i));	cell.setValue(Math.round(hdRs.getDouble("VAT"))); 			style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
					
					cell = cells.getCell("I" + Integer.toString(i));	cell.setValue(hdRs.getDouble("AVAT")  ); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
					

					i++;
					stt ++;
				}
				hdRs.close();
				
				
				String condition = "";
				String condition1="";
				if(obj.getVung().trim().length() > 0)
				{
			    	condition += " and a.npp_fk in (Select pk_Seq  from nhaphanphoi where  khuvuc_Fk in (Select pk_Seq from khuvuc where vung_Fk='"+obj.getVung()+"' ) ) ";
			    	condition1 += " and a.npp_fk in (Select pk_Seq  from nhaphanphoi where  khuvuc_Fk in (Select pk_Seq from khuvuc where vung_Fk='"+obj.getVung()+"' ) ) ";
			    }
				
				if(obj.getKhuvuc().trim().length() > 0)
				{
					condition += " and a.npp_fk in (Select pk_Seq from nhaphanphoi where khuvuc_Fk='"+obj.getKhuvuc()+"') ";
					condition1 += " and a.npp_fk in (Select pk_Seq from nhaphanphoi where khuvuc_Fk='"+obj.getKhuvuc()+"') ";
				}
				
				if(obj.getNppId().length()>0)
				{
					condition += " and a.npp_Fk = '" + obj.getNppId() + "' ";
	 				condition1 += "and a.NPP_FK=(select TRUCTHUOC_FK from NHAPHANPHOI where PK_SEQ='"+obj.getNppId()+"' and a.KHACHHANG_FK in (select pk_seq from KHACHHANG where TrucThuoc_FK='"+obj.getNppId()+"'))";
				}
				
				Utility util = new Utility();
				if(view.length()>0)
				{
					condition += " and a.npp_Fk in "+util.quyen_npp(obj.getUserId() ) +" ";
					condition1 += " and a.npp_Fk in "+util.quyen_npp(obj.getUserId() ) +" ";
								}
				
				if(obj.getTdvId().length()>0)
				{
					//condition += " and a.pk_seq  in (select HOADONNPP_FK from ERP_HOADONNPP_DDH where DDH_FK  in (select pk_seq from ERP_DONDATHANGNPP  where ddkd_fk='"+obj.getTdvId()+"') )  ";
					//condition1 += " and a.pk_seq  in (select HOADONNPP_FK from ERP_HOADONNPP_DDH where DDH_FK  in (select pk_seq from ERP_DONDATHANGNPP  where ddkd_fk='"+obj.getTdvId()+"') )  ";
					condition +=
							 " and a.khachhang_Fk in   	( select cc.khachhang_Fk from DAIDIENKINHDOANH aa inner join TUYENBANHANG bb on bb.DDKD_FK=aa.PK_SEQ   \n "+      
							 "   		inner join KHACHHANG_TUYENBH cc on cc.TBH_FK=bb.PK_SEQ  " +
							 "          where aa.pk_Seq='"+obj.getTdvId()+"'      \n "+      
							 "   	) ";
		
					condition1 +=
							 " and a.khachhang_Fk in   	( select cc.khachhang_Fk from DAIDIENKINHDOANH aa inner join TUYENBANHANG bb on bb.DDKD_FK=aa.PK_SEQ   \n "+      
							 "   		inner join KHACHHANG_TUYENBH cc on cc.TBH_FK=bb.PK_SEQ  " +
							 "          where aa.pk_Seq='"+obj.getTdvId()+"'      \n "+      
							 "   	) ";
		
			
				}
				
				if(obj.getKhoId().length()>0)
				{
					condition += " and a.pk_seq  in (select HOADONNPP_FK from ERP_HOADONNPP_DDH where DDH_FK  in (select pk_seq from ERP_DONDATHANGNPP  where kho_fk='"+obj.getKhoId()+"') )  ";
					condition1 += " and a.pk_seq  in (select HOADONNPP_FK from ERP_HOADONNPP_DDH where DDH_FK  in (select pk_seq from ERP_DONDATHANGNPP  where kho_fk='"+obj.getKhoId()+"') )  ";
				}
				
				if(obj.getTungay().length()>0)
				{
					condition+= "and ngayxuatHD >= '" + obj.getTungay() + "'";
					condition1+= "and ngayxuatHD >= '" + obj.getTungay() + "'";
				}
				if(obj.getDenngay().length()>0)
				{
					condition+= "and ngayxuatHD <= '" + obj.getDenngay() + "' ";
					condition1+= "and ngayxuatHD <= '" + obj.getDenngay() + "' ";
				}
				
				if(obj.getMuc_KhachHang().equals("1")&&obj.getMucCN_DT().equals("0"))
				{
					condition += 	 " and  a.KhachHang_fk is not null ";
					condition1 += 	 " and  a.KhachHang_fk is not null ";
								}
				if(obj.getMucCN_DT().equals("1")&&obj.getMuc_KhachHang().equals("0"))
				{
					condition += 	 " and  a.npp_dat_fk is not null ";
					condition1 += 	 " and  a.npp_dat_fk is not null ";
				}
				
				
				 query=
					"	select  'CK' as Ma,N'Chiết Khấu' as TEN,''  as DonVi ,0 as SoLuong,0 as DonGia,sum(ROUND(b.CHIETKHAU,0)) as BVAT ," +
					"		 sum(ROUND(( ROUND (B.CHIETKHAU , 0 ) ) *( b.VAT/100.0),0 )) as VAT,sum(ROUND(( ROUND (b.CHIETKHAU , 0 ) ) *(1+ b.VAT/100.0),0 )) as BVAT "+
					"	from ERP_HOADON  a inner join ERP_HOADON_SP b on b.HOADON_FK=a.PK_SEQ  "+
					"	where a.trangthai not in (1,3,5) "+condition+""+
					"  having sum(b.CHIETKHAU)>0    ";
				
				 
				hdRs = db.get(query);
				
				System.out.println(":::"+query);
				
				while(hdRs.next())
				{
					double ck_truocVAT =  -1 * hdRs.getDouble("BVAT");
					double ck_VAT = hdRs.getDouble("VAT");
					double ck_sauVAT =  hdRs.getDouble("BVAT");
					
					totalTRUOCVAT += ck_truocVAT;
					totalVAT += ck_VAT;

					cell = cells.getCell("A" + Integer.toString(i));	cell.setValue(stt); style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
					cell = cells.getCell("B" + Integer.toString(i));	cell.setValue(hdRs.getString("MA")); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("C" + Integer.toString(i));	cell.setValue(hdRs.getString("TEN")); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("D" + Integer.toString(i));	cell.setValue(hdRs.getString("DONVI")); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);		
					cell = cells.getCell("E" + Integer.toString(i));	cell.setValue(hdRs.getDouble("SOLUONG")); 	style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
					
					
					cell = cells.getCell("F" + Integer.toString(i));	cell.setValue( hdRs.getDouble("DONGIA") ); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					style.setCustom("#,##0.0000");
					cell.setStyle(style);
					
					
					cell = cells.getCell("G" + Integer.toString(i));	cell.setValue(  Math.round(ck_truocVAT)); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
					
					cell = cells.getCell("H" + Integer.toString(i));	cell.setValue( Math.round(  ck_VAT)); 			style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
					
					cell = cells.getCell("I" + Integer.toString(i));	cell.setValue( Math.round( ck_sauVAT ) ); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
					
					i++;
					stt ++;
					
				}
				
				
				//THEM DONG TONG CONG
				cell = cells.getCell("A" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
				cell = cells.getCell("B" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
        		cell = cells.getCell("C" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);		
				cell = cells.getCell("D" + Integer.toString(i));	cell.setValue(""); 	style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
				cell = cells.getCell("E" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
				cell = cells.getCell("F" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
				cell = cells.getCell("G" + Integer.toString(i));	cell.setValue( Math.round( totalTRUOCVAT)); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
				
				cell = cells.getCell("H" + Integer.toString(i));	cell.setValue( Math.round(totalVAT)); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
				
				cell = cells.getCell("I" + Integer.toString(i));	cell.setValue(Math.round(totalTRUOCVAT + totalVAT)) ; 			style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
				
				i++;
			}
			catch (Exception e)
			{ 
				e.printStackTrace(); 
			}
		}
	}
	
	
	private void CreateStaticData_OTC(Workbook workbook, IBCHoadonbanra obj, String view, String search_ETC ) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet("OTC");
	    Cells cells = worksheet.getCells();

	    String query = search_ETC;
	    dbutils db = new dbutils();
	    ResultSet hdRs = db.get(query);
		
	    Cell cell_mau = cells.getCell("A9");
	    Cell cell = null;
		
		Style style;
		Font font2 = new Font();
		font2.setName("Times New Roman");				
		font2.setSize(8);
		
		Font font3 = new Font();
		font3.setName("Times New Roman");				
		font3.setSize(8);
		font3.setBold(true);
		
		int i = 10;
		if(hdRs != null)
		{
			try 
			{
				int stt = 1;
				double totalTRUOCVAT = 0;
				double totalVAT = 0;
				
				while(hdRs.next())
				{
					totalTRUOCVAT += Math.round(hdRs.getDouble("DOANHTHU"));
					totalVAT += hdRs.getDouble("VAT");

					cell = cells.getCell("A" + Integer.toString(i));	cell.setValue(stt); 			style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
					cell = cells.getCell("B" + Integer.toString(i));	cell.setValue(hdRs.getString("MA")); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("C" + Integer.toString(i));	cell.setValue(hdRs.getString("TEN")); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("D" + Integer.toString(i));	cell.setValue(hdRs.getString("DONVI")); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);		
					cell = cells.getCell("E" + Integer.toString(i));	cell.setValue(hdRs.getDouble("SOLUONG")); 	style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
					
					
					cell = cells.getCell("F" + Integer.toString(i));	cell.setValue(hdRs.getDouble("DONGIA")); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					style.setCustom("#,##0.0000");
					cell.setStyle(style);
					
					
					cell = cells.getCell("G" + Integer.toString(i));	cell.setValue(Math.round(hdRs.getDouble("DOANHTHU"))); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
					
					cell = cells.getCell("H" + Integer.toString(i));	cell.setValue(Math.round(hdRs.getDouble("VAT"))); 			style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
					
					cell = cells.getCell("I" + Integer.toString(i));	cell.setValue(hdRs.getDouble("AVAT")  ); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
					

					i++;
					stt ++;
				}
				hdRs.close();
				
				
				String condition = "";
				String condition1="";
				if(obj.getVung().trim().length() > 0)
				{
			    	condition += " and a.npp_fk in (Select pk_Seq  from nhaphanphoi where  khuvuc_Fk in (Select pk_Seq from khuvuc where vung_Fk='"+obj.getVung()+"' ) ) ";
			    	condition1 += " and a.npp_fk in (Select pk_Seq  from nhaphanphoi where  khuvuc_Fk in (Select pk_Seq from khuvuc where vung_Fk='"+obj.getVung()+"' ) ) ";
			    }
				
				if(obj.getKhuvuc().trim().length() > 0)
				{
					condition += " and a.npp_fk in (Select pk_Seq from nhaphanphoi where khuvuc_Fk='"+obj.getKhuvuc()+"') ";
					condition1 += " and a.npp_fk in (Select pk_Seq from nhaphanphoi where khuvuc_Fk='"+obj.getKhuvuc()+"') ";
				}
				
				if(obj.getNppId().length()>0)
				{
					condition += " and a.npp_Fk = '" + obj.getNppId() + "' ";
	 				condition1 += "and a.NPP_FK=(select TRUCTHUOC_FK from NHAPHANPHOI where PK_SEQ='"+obj.getNppId()+"' and a.KHACHHANG_FK in (select pk_seq from KHACHHANG where TrucThuoc_FK='"+obj.getNppId()+"'))";
				}
				
				Utility util = new Utility();
				if(view.length()>0)
				{
					condition += " and a.npp_Fk in "+util.quyen_npp(obj.getUserId() ) +" ";
					condition1 += " and a.npp_Fk in "+util.quyen_npp(obj.getUserId() ) +" ";
								}
				
				if(obj.getTdvId().length()>0)
				{
					//condition += " and a.pk_seq  in (select HOADONNPP_FK from ERP_HOADONNPP_DDH where DDH_FK  in (select pk_seq from ERP_DONDATHANGNPP  where ddkd_fk='"+obj.getTdvId()+"') )  ";
					//condition1 += " and a.pk_seq  in (select HOADONNPP_FK from ERP_HOADONNPP_DDH where DDH_FK  in (select pk_seq from ERP_DONDATHANGNPP  where ddkd_fk='"+obj.getTdvId()+"') )  ";
					condition +=
							 " and a.khachhang_Fk in   	( select cc.khachhang_Fk from DAIDIENKINHDOANH aa inner join TUYENBANHANG bb on bb.DDKD_FK=aa.PK_SEQ   \n "+      
							 "   		inner join KHACHHANG_TUYENBH cc on cc.TBH_FK=bb.PK_SEQ  " +
							 "          where aa.pk_Seq='"+obj.getTdvId()+"'      \n "+      
							 "   	) ";
		
					condition1 +=
							 " and a.khachhang_Fk in   	( select cc.khachhang_Fk from DAIDIENKINHDOANH aa inner join TUYENBANHANG bb on bb.DDKD_FK=aa.PK_SEQ   \n "+      
							 "   		inner join KHACHHANG_TUYENBH cc on cc.TBH_FK=bb.PK_SEQ  " +
							 "          where aa.pk_Seq='"+obj.getTdvId()+"'      \n "+      
							 "   	) ";
		
			
				}
				
				if(obj.getKhoId().length()>0)
				{
					condition += " and a.pk_seq  in (select HOADONNPP_FK from ERP_HOADONNPP_DDH where DDH_FK  in (select pk_seq from ERP_DONDATHANGNPP  where kho_fk='"+obj.getKhoId()+"') )  ";
					condition1 += " and a.pk_seq  in (select HOADONNPP_FK from ERP_HOADONNPP_DDH where DDH_FK  in (select pk_seq from ERP_DONDATHANGNPP  where kho_fk='"+obj.getKhoId()+"') )  ";
				}
				
				if(obj.getTungay().length()>0)
				{
					condition+= "and ngayxuatHD >= '" + obj.getTungay() + "'";
					condition1+= "and ngayxuatHD >= '" + obj.getTungay() + "'";
				}
				if(obj.getDenngay().length()>0)
				{
					condition+= "and ngayxuatHD <= '" + obj.getDenngay() + "' ";
					condition1+= "and ngayxuatHD <= '" + obj.getDenngay() + "' ";
				}
				
				if(obj.getMuc_KhachHang().equals("1")&&obj.getMucCN_DT().equals("0"))
				{
					condition += 	 " and  a.KhachHang_fk is not null ";
					condition1 += 	 " and  a.KhachHang_fk is not null ";
								}
				if(obj.getMucCN_DT().equals("1")&&obj.getMuc_KhachHang().equals("0"))
				{
					condition += 	 " and  a.npp_dat_fk is not null ";
					condition1 += 	 " and  a.npp_dat_fk is not null ";
				}
				
				
				 query=
					"	select  'CK' as Ma,N'Chiết Khấu' as TEN,''  as DonVi ,0 as SoLuong,0 as DonGia,sum(ROUND(b.CHIETKHAU,0)) as BVAT ," +
					"		 sum(ROUND(( ROUND (B.CHIETKHAU , 0 ) ) *( b.VAT/100.0),0 )) as VAT,sum(ROUND(( ROUND (b.CHIETKHAU , 0 ) ) *(1+ b.VAT/100.0),0 )) as BVAT "+
					"	from ERP_HOADON  a inner join ERP_HOADON_SP b on b.HOADON_FK=a.PK_SEQ  "+
					"	where a.trangthai not in (1,3,5) "+condition+""+
					"  having sum(b.CHIETKHAU)>0    ";
				
				 
				hdRs = db.get(query);
				
				System.out.println(":::"+query);
				
				while(hdRs.next())
				{
					double ck_truocVAT =  -1 * hdRs.getDouble("BVAT");
					double ck_VAT = hdRs.getDouble("VAT");
					double ck_sauVAT =  hdRs.getDouble("BVAT");
					
					totalTRUOCVAT += ck_truocVAT;
					totalVAT += ck_VAT;

					cell = cells.getCell("A" + Integer.toString(i));	cell.setValue(stt); style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
					cell = cells.getCell("B" + Integer.toString(i));	cell.setValue(hdRs.getString("MA")); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("C" + Integer.toString(i));	cell.setValue(hdRs.getString("TEN")); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("D" + Integer.toString(i));	cell.setValue(hdRs.getString("DONVI")); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);		
					cell = cells.getCell("E" + Integer.toString(i));	cell.setValue(hdRs.getDouble("SOLUONG")); 	style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
					
					
					cell = cells.getCell("F" + Integer.toString(i));	cell.setValue( hdRs.getDouble("DONGIA") ); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					style.setCustom("#,##0.0000");
					cell.setStyle(style);
					
					
					cell = cells.getCell("G" + Integer.toString(i));	cell.setValue(  Math.round(ck_truocVAT)); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
					
					cell = cells.getCell("H" + Integer.toString(i));	cell.setValue( Math.round(  ck_VAT)); 			style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
					
					cell = cells.getCell("I" + Integer.toString(i));	cell.setValue( Math.round( ck_sauVAT ) ); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
					
					i++;
					stt ++;
					
				}
				
				
				//THEM DONG TONG CONG
				cell = cells.getCell("A" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
				cell = cells.getCell("B" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
        		cell = cells.getCell("C" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);		
				cell = cells.getCell("D" + Integer.toString(i));	cell.setValue(""); 	style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
				cell = cells.getCell("E" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
				cell = cells.getCell("F" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
				cell = cells.getCell("G" + Integer.toString(i));	cell.setValue( Math.round( totalTRUOCVAT)); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
				
				cell = cells.getCell("H" + Integer.toString(i));	cell.setValue( Math.round(totalVAT)); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
				
				cell = cells.getCell("I" + Integer.toString(i));	cell.setValue(Math.round(totalTRUOCVAT + totalVAT)) ; 			style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
				
				i++;
			}
			catch (Exception e)
			{ 
				e.printStackTrace(); 
			}
		}
	}
	

	private String getFormatDate(String date) 
	{
		String[] arr = date.split("-");

        return arr[2] + "/" + arr[1] + "/" + arr[0];	
	}
	
	private void setCellBorderStyle(Cell cell, short alignment) 
	{
		Style style = cell.getStyle();
		//style.setHAlignment(HorizontalAlignmentType.CENTRED);
		style.setHAlignment(alignment);
		/*style.setBorderLine(BorderType.TOP, 1);
		style.setBorderLine(BorderType.RIGHT, 1);
		style.setBorderLine(BorderType.BOTTOM, 1);
		style.setBorderLine(BorderType.LEFT, 1);*/
		cell.setStyle(style);
	}
	

}
