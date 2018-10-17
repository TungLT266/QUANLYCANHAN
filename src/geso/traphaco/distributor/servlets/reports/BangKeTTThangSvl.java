package geso.traphaco.distributor.servlets.reports;


import geso.traphaco.center.beans.doctien.doctienrachu;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.distributor.beans.reports.IBangKeTTThang;
import geso.traphaco.distributor.beans.reports.imp.BangKeTTThang;
import geso.traphaco.distributor.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.BorderLineType;
import com.aspose.cells.BorderType;
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

public class BangKeTTThangSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	Utility util=new Utility();
       
    public BangKeTTThangSvl() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
	//	PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		IBangKeTTThang obj = new BangKeTTThang();	
		
		String userTen = (String)session.getAttribute("userTen");
		String querystring=request.getQueryString();
		String userId = util.getUserId(querystring);
		String nppID = util.getIdNhapp(userId);
		
		String loai = request.getParameter("loai");
		String tt = request.getParameter("tt");
		if(tt==null)
			tt="";
		
	    obj.setUserId(userId);
	    obj.setNppID(nppID);
	    obj.setLoai(loai);
	    obj.setTt(tt);
	    
	    obj.init();	    
		
	    session.setAttribute("obj", obj);
		session.setAttribute("userTen", userTen);
		session.setAttribute("userId", userId);
		session.setAttribute("nppId", nppID);
		session.setAttribute("loai", loai);
		session.setAttribute("nam", "");
 		session.setAttribute("thang","");
    	session.setAttribute("loi", "");
    	session.setAttribute("tt", tt);
		
		String nextJSP = "/TraphacoHYERP/pages/Distributor/BangKeTTThang.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
	
		HttpSession session = request.getSession();
        
        String action = request.getParameter("action");
        
    	OutputStream out = response.getOutputStream(); 
		String userTen = (String)session.getAttribute("userTen");
		
		if(action.equals("taomoi"))
        {
	    	response.setContentType("application/vnd.ms-excel");	    	
	        response.setHeader("Content-Disposition", "attachment; filename=BangKeTTThang.xlsm");
        }
        
        BangKeTTThang obj = new BangKeTTThang();
        
        String userId = (String) session.getAttribute("userId");
        obj.setUserId(userId);
        
        String nppId = (String) session.getAttribute("nppId");
        obj.setNppID(nppId);
          
        
        String nam = util.antiSQLInspection(request.getParameter("nam"));
        if(nam == null)
        {
        	nam = "";
        }
        obj.setNam(nam);
        
        String thang = util.antiSQLInspection(request.getParameter("thang"));
        if(thang == null)
        {
        	thang = "";
        }
        obj.setThang(thang);
       
        
        String tdvId = util.antiSQLInspection(request.getParameter("tdvId"));
        if(tdvId == null)
        {
        	tdvId = "";
        }
        obj.setTdvId(tdvId);
        
        String quy = util.antiSQLInspection(request.getParameter("quy"));
        if(quy == null)
        {
        	quy = "";
        }
        obj.setQuy(quy);   
        
        String loai = util.antiSQLInspection(request.getParameter("loai"));
        if(loai == null)
        {
        	loai = "";
        }
        obj.setLoai(loai);  
        
        String tt = util.antiSQLInspection(request.getParameter("tt"));
        if(tt == null)
        {
        	tt = "";
        }
        obj.setTt(tt);  
        
        String chinhanhId = util.antiSQLInspection(request.getParameter("chinhanhId"));
        if(chinhanhId == null)
        {
        	chinhanhId = "";
        }
        obj.setChinhanhId(chinhanhId);
     	     
		
		if(obj.getQuy().equals("1"))
		{
			obj.setTuthang("01");
			obj.setDenthang("03");
			obj.setT1("1");
			obj.setT2("2");
			obj.setT3("3");
		}else if(obj.getQuy().equals("2"))
		{
			obj.setTuthang("04");
			obj.setDenthang("06");
			obj.setT1("4");
			obj.setT2("5");
			obj.setT3("6");
		}
		else if(obj.getQuy().equals("3"))
		{
			obj.setTuthang("07");
			obj.setDenthang("09");
			obj.setT1("7");
			obj.setT2("8");
			obj.setT3("9");
		}
		else if(obj.getQuy().equals("4"))
		{
			obj.setTuthang("10");
			obj.setDenthang("12");
			obj.setT1("10");
			obj.setT2("11");
			obj.setT3("12");
		}
		
		if(action.equals("taomoi"))
        {
			if(obj.getLoai().equals("0")){
				response.setHeader("Content-Disposition", "attachment; filename=BangKeTTThang.xlsm");
			}
			else if(obj.getLoai().equals("1")){ 
				response.setHeader("Content-Disposition", "attachment; filename=BangKeCKQuy.xlsm");
			}
			else if(obj.getLoai().equals("2")){ 
				response.setHeader("Content-Disposition", "attachment; filename=BangKeQuyUngHo.xlsm");
			}
			else if(obj.getLoai().equals("3")){ 
				response.setHeader("Content-Disposition", "attachment; filename=BangKeTTThangDT.xlsm");
			}
			else if(obj.getLoai().equals("4")){ 
				response.setHeader("Content-Disposition", "attachment; filename=BangKeTTQuyDT.xlsm");
			}
			else if(obj.getLoai().equals("5")){ 
				response.setHeader("Content-Disposition", "attachment; filename=XacNhanDSThangDT.xlsm");
			}
			else if(obj.getLoai().equals("6")){ 
				response.setHeader("Content-Disposition", "attachment; filename=XacNhanDSQuyDT.xlsm");
			}
        }
	        
        obj.init();	
        
      
        if(action.equals("taomoi"))
        {
        	//return;
        	CreatePivotTable(out,response,request, userId , userTen, thang, nam ,quy,  obj.getNppTen(), obj.getTdvTen() , obj);
        }
        else {
        	session.setAttribute("obj", obj);
			session.setAttribute("userId", userId);
        	String	nextJSP = "/TraphacoHYERP/pages/Distributor/BangKeTTThang.jsp";
			response.sendRedirect(nextJSP);
		}
	}

	private void CreatePivotTable(OutputStream out,
			HttpServletResponse response, HttpServletRequest request,
			String userId, String userTen, String thang, String nam, String quy, String nppTen, String tdvTen , IBangKeTTThang obj) throws IOException {    
	     
	     
	     try
			{
				String chuoi = getServletContext().getInitParameter("path") + "\\BangKeTTThang.xlsm";
				if(obj.getLoai().equals("0")){
					chuoi = getServletContext().getInitParameter("path") + "\\BangKeTTThang.xlsm";
				}
				else if(obj.getLoai().equals("1")){ 
					chuoi = getServletContext().getInitParameter("path") + "\\BangKeCKQuy.xls";
				}
				else if(obj.getLoai().equals("2")){ 
					chuoi = getServletContext().getInitParameter("path") + "\\BangKeQuyUngHo.xlsm";
				}
				else if(obj.getLoai().equals("3")){ 
					chuoi = getServletContext().getInitParameter("path") + "\\BangKeTTThangDT.xlsm";
				}
				else if(obj.getLoai().equals("4")){ 
					chuoi = getServletContext().getInitParameter("path") + "\\BangKeTTQuyDT.xlsm";
				}
				else if(obj.getLoai().equals("5")){ 
					chuoi = getServletContext().getInitParameter("path") + "\\XacNhanDSThangDT.xlsm";
				}
				else if(obj.getLoai().equals("6")){ 
					chuoi = getServletContext().getInitParameter("path") + "\\XacNhanDSQuyDT.xlsm";
				}
				FileInputStream fstream = new FileInputStream(chuoi);
				Workbook workbook = new Workbook();
				workbook.open(fstream);
				workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			     
			    if(obj.getLoai().equals("0")){
					CreateStaticHeader0(workbook, userTen, thang, nam , nppTen, tdvTen);			 
				    CreateStaticData0(workbook, userId, thang, nam , nppTen, tdvTen , obj);
				}
				else if(obj.getLoai().equals("1")){ 
					CreateStaticHeader1(workbook, userTen, quy, nam , nppTen, tdvTen);			 
				    CreateStaticData1(workbook, userId, quy, nam , nppTen, tdvTen , obj);
				}
				else if(obj.getLoai().equals("2")){ 
					CreateStaticHeader2(workbook, userTen, quy, nam , nppTen, tdvTen);			 
				    CreateStaticData2(workbook, userId, quy, nam , nppTen, tdvTen , obj);
				}
				else if(obj.getLoai().equals("3")){ 
					CreateStaticHeader3(workbook, userTen, thang, nam , nppTen);			 
				    CreateStaticData3(workbook, userId, thang, nam , nppTen , obj);
				}
				else if(obj.getLoai().equals("4")){ 
					CreateStaticHeader4(workbook, userTen, quy, nam , nppTen);			 
				    CreateStaticData4(workbook, userId, quy, nam , nppTen , obj);
				}
				else if(obj.getLoai().equals("5")){ 
					CreateStaticHeader5(workbook, userTen, thang, nam , nppTen);			 
				    CreateStaticData5(workbook, userId, thang, nam , nppTen , obj);
				}
				else if(obj.getLoai().equals("6")){ 
					CreateStaticHeader6(workbook, userTen, quy, nam , nppTen , obj);			 
				    CreateStaticData6(workbook, userId, quy, nam , nppTen , obj);
				}
				
				workbook.save(out);
				fstream.close();
			} catch (Exception ex)
			{
				ex.printStackTrace();

			}
	     
	}

	private void CreateStaticHeader0(Workbook workbook, String userTen, String thang, String nam, String nppTen , String tdvTen) {
		// TODO Auto-generated method stub
		
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);

		Cells cells = worksheet.getCells();		

	    Cell cell = cells.getCell("A5");  
	    cell.setValue("Chi nhánh/khu vực: " + nppTen);   		      
	    
	    cell = cells.getCell("A6");  
	    cell.setValue("Trình dược viên: " + tdvTen);   		      

	    cell = cells.getCell("A7");
	    cell.setValue("BẢNG KÊ THANH TOÁN THÁNG " + thang + "/ " + nam);
	     		     
	    worksheet.setName("Bảng kê thanh toán theo thág");
	}
	
	private void CreateStaticData0(Workbook workbook, String userId,
			String thang, String nam ,String nppTen,String tdvTen , IBangKeTTThang obj) {
		// TODO Auto-generated method stub
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();

    	ResultSet rs = obj.getRsBangKeTTThang();
    	
    	if(rs != null)
    	{
    		try {
    			
				int i = 11;
				Cell cell = null;
				
				double tongds = 0;
				double tongtienck = 0;
				
				int so = 1;
				Font f = new Font();
				Style style ;
				
    			while (rs.next()) 
    			{    		
					String tenkh = rs.getString("tenkh");
    				String makh = rs.getString("mafast");
    				String diachi = rs.getString("diachi");
    				String xuatkhau = rs.getString("xuatkhau");
    				String duyet = rs.getString("duyet");
    				  			
    				
    				String phanloai = "";
    				if(xuatkhau.equals("0"))
    				{
    					phanloai = "BL";
    				}
    				else if (xuatkhau.equals("0"))
    				{
    					phanloai = "BB";
    				}
    				else if (xuatkhau.equals("0"))
    				{
    					phanloai = "BLBB";
    				}
    				else
    				{
    					phanloai = "BBBL";
    				}
    				 
    				
    				double ck = 0;
    				if(duyet.equals("Đạt")){
    					if(phanloai.equals("BL") || phanloai.equals("BLBB"))
    					{
    						ck = 0.08;
    					}
    					else
    					{
    						ck = 0.06;
    					}
    				}
    				else
    				{
    					ck = 0;
    				}
    				
    				
    				double doanhso = 0;
    				try{
    					doanhso = rs.getDouble("doanhso");
    				
    				}catch(Exception er){
    					
    				}
    				
    				double tienck = 0;
    				tienck = doanhso*ck;
    				    				
					cell = cells.getCell("A" + Integer.toString(i)); cell.setValue(so);			
					CreateBorderSetting(workbook,"A" + Integer.toString(i));
					
					cell = cells.getCell("B" + Integer.toString(i)); cell.setValue(makh);		
					CreateBorderSetting(workbook,"B" + Integer.toString(i));
					
					cell = cells.getCell("D" + Integer.toString(i)); cell.setValue(phanloai);	
					CreateBorderSetting(workbook,"D" + Integer.toString(i));  
					style = cell.getStyle();
					f.setBold(true);
					f.setColor(Color.BLACK);
					style.setFont(f);
					cell.setStyle(style);
					
					cell = cells.getCell("E" + Integer.toString(i)); cell.setValue(tenkh);	
					CreateBorderSetting(workbook,"E" + Integer.toString(i));
					
					cell = cells.getCell("F" + Integer.toString(i)); cell.setValue(diachi);		
					CreateBorderSetting(workbook,"F" + Integer.toString(i));
					 
					cell = cells.getCell("G" + Integer.toString(i)); cell.setValue(doanhso);		
					CreateBorderSetting(workbook,"G" + Integer.toString(i));
					 
					cell = cells.getCell("H" + Integer.toString(i)); cell.setValue(duyet);		
					CreateBorderSetting(workbook,"H" + Integer.toString(i));
					
					cell = cells.getCell("I" + Integer.toString(i)); cell.setValue(ck);
					CreateBorderSetting(workbook,"I" + Integer.toString(i));
					
					cell = cells.getCell("J" + Integer.toString(i)); cell.setValue(tienck);		
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
	
					tongds += doanhso;
					tongtienck += tienck;
					
					so++;
					 
					i++;
				}
    					
    			
    			 cell = cells.getCell("A" + Integer.toString(i)); cell.setValue("");
				 CreateBorderSetting(workbook,"A" + Integer.toString(i));
				 cell = cells.getCell("B" + Integer.toString(i)); cell.setValue("");
				 CreateBorderSetting(workbook,"B" + Integer.toString(i));
				 
				 cell = cells.getCell("C" + Integer.toString(i)); cell.setValue("");
				 CreateBorderSetting(workbook,"C" + Integer.toString(i));
				 
				 cell = cells.getCell("D" + Integer.toString(i)); cell.setValue("");
				 CreateBorderSetting(workbook,"D" + Integer.toString(i));
				 
				
				 cell = cells.getCell("E" + Integer.toString(i)); cell.setValue("Tổng");
				 CreateBorderSetting(workbook,"E" + Integer.toString(i));
				 style = cell.getStyle();
				 f.setColor(Color.BLACK);				
				 style.setFont(f);
				 cell.setStyle(style);
				 
				 
				 cell = cells.getCell("F" + Integer.toString(i)); cell.setValue("");
				 CreateBorderSetting(workbook,"F" + Integer.toString(i));
				 
				 
				 cell = cells.getCell("G" + Integer.toString(i)); cell.setValue(tongds);
				 ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 3);
					
				/* CreateBorderSetting(workbook,"G" + Integer.toString(i));
				 style = cell.getStyle();
				 f.setColor(Color.BLACK);
				 style.setFont(f);
				 cell.setStyle(style);*/
				 
				
		
				 
				 cell = cells.getCell("H" + Integer.toString(i)); cell.setValue("");
				 CreateBorderSetting(workbook,"H" + Integer.toString(i));
				 
				 cell = cells.getCell("I" + Integer.toString(i)); cell.setValue("");
				 CreateBorderSetting(workbook,"I" + Integer.toString(i));
				 
				 cell = cells.getCell("J" + Integer.toString(i)); cell.setValue(tongtienck);
				 ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 3);
					
				/* CreateBorderSetting(workbook,"J" + Integer.toString(i));
				 style = cell.getStyle();
				 f.setColor(Color.BLACK);
				 style.setFont(f);
				 cell.setStyle(style);*/
    			
				 
				 i++;
				 
				 // Tien bang chu
				 doctienrachu doctien = new doctienrachu();
			     String tien = doctien.docTien(Math.round(tongtienck));		   
			     //Viết hoa ký tự đầu tiên
			     String TienIN = (tien.substring(0,1)).toUpperCase() + tien.substring(1);
				 
				 cell = cells.getCell("D" + Integer.toString(i)); cell.setValue("Bằng chữ: " + TienIN);
				 style = cell.getStyle();
				 f.setColor(Color.BLACK);
				 f.setBold(false);
				 style.setFont(f);
				 cell.setStyle(style);

				 i+=3;
				 
				 cell = cells.getCell("A" + Integer.toString(i)); cell.setValue("Cán bộ kiểm tra");				 
				 style = cell.getStyle();
				 f.setBold(true);
				 f.setColor(Color.BLACK);
				 style.setFont(f);
				 cell.setStyle(style);
				 
				 cell = cells.getCell("E" + Integer.toString(i)); cell.setValue("Giám sát bán hàng/TDV");
				 cell.setStyle(style);
				 
				 cell = cells.getCell("G" + Integer.toString(i)); cell.setValue("Giám đốc CN/PT khu vực");
				 cell.setStyle(style);

				 cell = cells.getCell("I" + Integer.toString(i)); cell.setValue("Phòng kinh doanh");
				 cell.setStyle(style);


				 
				
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("Lỗi dữ liệu");
			}
    	}
		
	}

	private void CreateStaticHeader1(Workbook workbook, String userTen, String quy, String nam, String nppTen , String tdvTen) {
		// TODO Auto-generated method stub
		
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);

		Cells cells = worksheet.getCells();		

	    Cell cell = cells.getCell("B4");  
	    cell.setValue("Chi nhánh/khu vực: " + nppTen);   		      
	    
	    cell = cells.getCell("B5");  
	    cell.setValue("Trình dược viên: " + tdvTen);   		      

	    cell = cells.getCell("A6");
	    cell.setValue("BẢNG KÊ THANH TOÁN QUÝ " + quy + "/ " + nam);
	     		     
	    worksheet.setName("Bảng kê ck theo quý");
	}

	private void CreateStaticData1(Workbook workbook, String userId, String quy, String nam ,String nppTen,String tdvTen , IBangKeTTThang obj) 
	{
		// TODO Auto-generated method stub
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();

    	ResultSet hdRs = obj.getRsBangKeTTThang();
    	Cell cell = null;
		
		Style style;
		Font font2 = new Font();	
		font2.setName("Times New Roman");
		font2.setSize(12);
		
		Font font3 = new Font();
		font3.setName("Times New Roman");
		font3.setSize(12);
		font3.setBold(true);
    	
		int i = 11;
		
    	if(hdRs != null)
    	{
    		try 
    		{
    			int stt = 1;
				double totalCK_BGHH = 0;
				double totalCK_XANH = 0;
				
				double total_DoanhSo = 0;
				double total_DoanhThu = 0;
				
				double total_XANH = 0;
				double total_HHBG = 0;
				double total_KHAC = 0;
				
    			while (hdRs.next()) 
    			{    		    				
    				double DOANHTHU = hdRs.getDouble("tienthuve");
					total_DoanhThu += DOANHTHU;
					
					double TONGTIEN_HHBG = hdRs.getDouble("DS_HHBG");
					total_HHBG += TONGTIEN_HHBG;
					
					double TONGTIEN_XANH = hdRs.getDouble("DS_XANH");
					total_XANH += TONGTIEN_XANH;
					
					double TONGTIEN_KHAC = hdRs.getDouble("DS_KHAC");
					total_KHAC += TONGTIEN_KHAC;
					
					double TONGTIEN = TONGTIEN_HHBG + TONGTIEN_XANH + TONGTIEN_KHAC;
					total_DoanhSo += TONGTIEN;
					
					double ptHHBG = hdRs.getDouble("PT_HHBG");
					double CK_HHBG = 0;
					double PT_THUONG_HHBG = 0;
					if(ptHHBG >= 45)
					{
						CK_HHBG = 2 * DOANHTHU / 100;
						PT_THUONG_HHBG = 2;
					}
					
					double ptXANH = hdRs.getDouble("PT_XANH");
					double CK_XANH = 0;
					double PT_THUONG_XANH = 0;
					if( ptXANH >= 15 && ptXANH < 25 )
					{
						CK_XANH = 2 * DOANHTHU / 100;
						PT_THUONG_XANH = 2;
					}
					else
					{
						if(ptXANH >= 25)
						{
							CK_XANH = 3 * DOANHTHU / 100;
							PT_THUONG_XANH = 3;
						}
					}
					
					double TILE_HHBG = TONGTIEN_HHBG / TONGTIEN;
					double TILE_XANH = TONGTIEN_XANH / TONGTIEN;
					
					totalCK_BGHH += CK_HHBG;
					totalCK_XANH += CK_XANH;
					
					cell = cells.getCell("A" + Integer.toString(i));	cell.setValue(stt); 	    style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);			 				
					cell = cells.getCell("B" + Integer.toString(i));	cell.setValue(hdRs.getString("MAFAST")); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("C" + Integer.toString(i));	cell.setValue("BL"); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);			
					cell = cells.getCell("D" + Integer.toString(i));	cell.setValue(hdRs.getString("khTEN")); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					
					cell = cells.getCell("E" + Integer.toString(i));	cell.setValue(TONGTIEN); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					
					cell = cells.getCell("F" + Integer.toString(i));	cell.setValue(TONGTIEN_HHBG); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);	
					cell = cells.getCell("G" + Integer.toString(i));	cell.setValue(ptHHBG); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);	
					cell = cells.getCell("H" + Integer.toString(i));	cell.setValue(PT_THUONG_HHBG / 100);  style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);	 
					
					cell = cells.getCell("I" + Integer.toString(i));	cell.setValue(TONGTIEN_XANH); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);		
					cell = cells.getCell("J" + Integer.toString(i));	cell.setValue(ptXANH); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);	
					cell = cells.getCell("K" + Integer.toString(i));	cell.setValue(PT_THUONG_XANH / 100);  style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);	 
					
					cell = cells.getCell("L" + Integer.toString(i));	cell.setValue( ( PT_THUONG_HHBG + PT_THUONG_XANH ) / 100); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);		 
					cell = cells.getCell("M" + Integer.toString(i));	cell.setValue(DOANHTHU); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);		
					cell = cells.getCell("N" + Integer.toString(i));	cell.setValue(CK_HHBG + CK_HHBG); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);		
					
					i++;
					stt ++;
					
				}
    				
    			
    			 //cells.merge(i-1, 0, i-1, 3);
    			 cell = cells.getCell("A" + Integer.toString(i)); cell.setValue(""); style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle2(cell, HorizontalAlignmentType.RIGHT);
				 cell = cells.getCell("B" + Integer.toString(i)); cell.setValue(""); style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle2(cell, HorizontalAlignmentType.RIGHT);
				 cell = cells.getCell("C" + Integer.toString(i)); cell.setValue(""); style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle2(cell, HorizontalAlignmentType.RIGHT);

    			 cell = cells.getCell("D" + Integer.toString(i)); cell.setValue("Tổng"); style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle2(cell, HorizontalAlignmentType.LEFT);
				 cell = cells.getCell("E" + Integer.toString(i)); cell.setValue(total_DoanhSo); style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle2(cell, HorizontalAlignmentType.RIGHT);

				 cell = cells.getCell("F" + Integer.toString(i)); cell.setValue(total_HHBG); style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle2(cell, HorizontalAlignmentType.RIGHT);

				 cell = cells.getCell("G" + Integer.toString(i)); cell.setValue(""); style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle2(cell, HorizontalAlignmentType.RIGHT);
				 cell = cells.getCell("H" + Integer.toString(i)); cell.setValue(""); style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle2(cell, HorizontalAlignmentType.RIGHT);
				 
				 cell = cells.getCell("I" + Integer.toString(i)); cell.setValue(total_XANH); style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle2(cell, HorizontalAlignmentType.RIGHT);
				 
				 cell = cells.getCell("J" + Integer.toString(i)); cell.setValue(""); style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle2(cell, HorizontalAlignmentType.RIGHT);
				 cell = cells.getCell("K" + Integer.toString(i)); cell.setValue(""); style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle2(cell, HorizontalAlignmentType.RIGHT);
				 cell = cells.getCell("L" + Integer.toString(i)); cell.setValue(""); style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle2(cell, HorizontalAlignmentType.RIGHT);

				 cell = cells.getCell("M" + Integer.toString(i)); cell.setValue(total_DoanhThu); style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle2(cell, HorizontalAlignmentType.RIGHT);
				 cell = cells.getCell("N" + Integer.toString(i)); cell.setValue(totalCK_XANH + totalCK_BGHH); style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle2(cell, HorizontalAlignmentType.RIGHT);
				 i++;
				 
				 // Tien bang chu
				 doctienrachu doctien = new doctienrachu();
			     String tien = doctien.docTien(Math.round(totalCK_XANH + totalCK_BGHH));		   
			     //Viết hoa ký tự đầu tiên
			     String TienIN = (tien.substring(0,1)).toUpperCase() + tien.substring(1);
				 
			     cell = cells.getCell("A" + Integer.toString(i)); cell.setValue(""); style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle2(cell, HorizontalAlignmentType.CENTRED);
				 cell = cells.getCell("B" + Integer.toString(i)); cell.setValue(""); style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle2(cell, HorizontalAlignmentType.CENTRED);
				 cell = cells.getCell("C" + Integer.toString(i)); cell.setValue(""); style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle2(cell, HorizontalAlignmentType.CENTRED);
				 
				 cells.merge(i-1, 3, i-1, 13);
				 cell = cells.getCell("D" + Integer.toString(i)); cell.setValue("Bằng chữ: " + TienIN); style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle2(cell, HorizontalAlignmentType.LEFT);
				 
				 cell = cells.getCell("E" + Integer.toString(i)); cell.setValue(""); style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle2(cell, HorizontalAlignmentType.RIGHT);
				 cell = cells.getCell("F" + Integer.toString(i)); cell.setValue(""); style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle2(cell, HorizontalAlignmentType.RIGHT);
				 cell = cells.getCell("G" + Integer.toString(i)); cell.setValue(""); style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle2(cell, HorizontalAlignmentType.RIGHT);
				 cell = cells.getCell("H" + Integer.toString(i)); cell.setValue(""); style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle2(cell, HorizontalAlignmentType.RIGHT);
				 cell = cells.getCell("I" + Integer.toString(i)); cell.setValue(""); style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle2(cell, HorizontalAlignmentType.RIGHT);
				 cell = cells.getCell("J" + Integer.toString(i)); cell.setValue(""); style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle2(cell, HorizontalAlignmentType.RIGHT);
				 cell = cells.getCell("K" + Integer.toString(i)); cell.setValue(""); style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle2(cell, HorizontalAlignmentType.RIGHT);
				 cell = cells.getCell("L" + Integer.toString(i)); cell.setValue(""); style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle2(cell, HorizontalAlignmentType.RIGHT);
				 cell = cells.getCell("M" + Integer.toString(i)); cell.setValue(""); style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle2(cell, HorizontalAlignmentType.RIGHT);
				 cell = cells.getCell("N" + Integer.toString(i)); cell.setValue(""); style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle2(cell, HorizontalAlignmentType.RIGHT);
				 
				 
				 i += 3;
				 
				 cell = cells.getCell("C" + Integer.toString(i)); cell.setValue("Cán bộ kiểm tra");				 
				 style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); //setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
				 
				 cell = cells.getCell("E" + Integer.toString(i)); cell.setValue("Giám sát bán hàng/TDV");
				 style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); //setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
				 
				 cell = cells.getCell("H" + Integer.toString(i)); cell.setValue("Giám đốc CN/PT khu vực");
				 style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); //setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);

				 cell = cells.getCell("M" + Integer.toString(i)); cell.setValue("Phòng kinh doanh");
				 style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); //setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
				
			} 
    		catch (Exception e) 
    		{
				// TODO: handle exception
				e.printStackTrace();
				System.out.println("Lỗi dữ liệu");
			}
    	}
		
	}
		
	private void CreateStaticHeader2(Workbook workbook, String userTen, String quy, String nam, String nppTen , String tdvTen) {
		// TODO Auto-generated method stub
		
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(1);

		Cells cells = worksheet.getCells();		

	    Cell cell = cells.getCell("A3");  
	    cell.setValue("Chi nhánh/khu vực: " + nppTen);   		      
	    
	    cell = cells.getCell("A4");  
	    cell.setValue("Trình dược viên: " + tdvTen);   		      

	    cell = cells.getCell("A5");
	    cell.setValue("BẢNG KÊ THANH TOÁN QUÝ " + quy + "/ " + nam);
	    
	     worksheet = worksheets.getSheet(5);

		 cells = worksheet.getCells();		

	     cell = cells.getCell("A3");  
	    cell.setValue("Chi nhánh/khu vực: " + nppTen);   		      
	    
	   // cell = cells.getCell("A4");  
	  //  cell.setValue("Trình dược viên: " + tdvTen);   		      

	    cell = cells.getCell("A5");
	    cell.setValue("BẢNG KÊ DOANH SỐ QUÝ " + quy + "/ " + nam);
	     		     
	    
	   // worksheet.setName("Bảng kê quý ủng hộ");
	}

	private void CreateStaticData2(Workbook workbook, String userId,
			String quy, String nam ,String nppTen,String tdvTen , IBangKeTTThang obj) {
		// TODO Auto-generated method stub
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(1);
	    Cells cells = worksheet.getCells();

    	ResultSet rs = obj.getRsBangKeTTThang();
    	
    	if(rs != null)
    	{
    		try {
    			
				int i = 10;
				Cell cell = null;
				
				double tongds = 0;
				double tongtienck = 0;
				int tongsolankt =0;
				double tongtienthue = 0;
				double tongtiensauthue = 0;
				
				int so = 1;
				Font f = new Font();
				Style style ;
				
    			while (rs.next()) 
    			{    		
    				int flag=0;
					String tenkh = rs.getString("khten");
					
    				String cmnd = rs.getString("cmnd");
    				 String mahd= rs.getString("mahd");
    				 String diachi=rs.getString("diachi");
    				double doanhsoboga = 0;
    				try{
    					doanhsoboga = rs.getDouble("DS_HHBG");
    				
    				}catch(Exception er){
    					
    				}
    				
    				double doanhsoxanh = 0;
    				try{
    					doanhsoxanh = rs.getDouble("DS_XANH");
    				
    				}catch(Exception er){
    					
    				}
    				
    				double doanhsokhac = 0;
    				try{
    					doanhsokhac = rs.getDouble("DS_KHAC");
    				
    				}catch(Exception er){
    					
    				}
    				double doanhso1=rs.getDouble("t1");
    				double doanhso2=rs.getDouble("t2");
    				double doanhso3=rs.getDouble("t3");
    	    		double doanhso = doanhso1+doanhso2+doanhso3;
    				double tongdoanhso=doanhso1+doanhso2+doanhso3;
    				double ptHHBG = doanhsoboga / doanhso * 100;
    				
    				double ptXANH = doanhsoxanh / doanhso * 100;
    				double ptthuongBG=0;
    				double ptthuongxanh=0;
    				
    				if(obj.getNppID().equals("100010")||obj.getNppID().equals("106211"))
					{
					//	System.out.println("[BT]"+obj.getNppId());
						if( ( 15 <=ptXANH && ptXANH<25 ) && ptHHBG <=30)
						{
							ptthuongxanh=0.02;
							flag=1;
						}
						else if( 25<=ptXANH && ptHHBG <=30)
						{
							ptthuongxanh=0.03;
							flag=1;
						}
						if( ptHHBG <=30)
						{
							ptthuongBG=0.02;
							flag=1;
						}	
					}else 
					{
					
					 if( ( 15 <=ptXANH && ptXANH<25 ) && ptHHBG <=60)
						{
							ptthuongxanh=0.02;
							flag=1;
						}
						else if( 25<=ptXANH && ptHHBG <=60)
						{
							ptthuongxanh=0.03;
							flag=1;
						}
					 if(40<=ptHHBG && ptHHBG <=60)
						{
						 ptthuongBG=0.02;
						 flag=1;
						}	
					 
					}
    				
    				
    				double pttongthuong=ptthuongBG+ptthuongxanh;
    				
    				double thanhtien = tongdoanhso * pttongthuong;
    				
    				double thue=0;
    				if(thanhtien >= 2000000)
    				{
    					thue = thanhtien * 0.1;
    				}
    				
    				double thuclanh = thanhtien - thue;	
    				if(flag==1)
    				{
					cell = cells.getCell("A" + Integer.toString(i)); cell.setValue(so);			
					CreateBorderSetting(workbook,"A" + Integer.toString(i));
					
					cell = cells.getCell("B" + Integer.toString(i)); cell.setValue(mahd);		
					CreateBorderSetting(workbook,"B" + Integer.toString(i));
				
					cell = cells.getCell("C" + Integer.toString(i)); cell.setValue(tenkh);		
					CreateBorderSetting(workbook,"C" + Integer.toString(i));
				
					
					cell = cells.getCell("D" + Integer.toString(i)); cell.setValue(diachi);	
					CreateBorderSetting(workbook,"D" + Integer.toString(i));  
										
					cell = cells.getCell("E" + Integer.toString(i)); cell.setValue(cmnd);	
					CreateBorderSetting(workbook,"E" + Integer.toString(i));
			
					 
					cell = cells.getCell("F" + Integer.toString(i)); cell.setValue(tongdoanhso);		
					CreateBorderSetting(workbook,"F" + Integer.toString(i));
					 
					cell = cells.getCell("G" + Integer.toString(i)); cell.setValue(pttongthuong);		
					CreateBorderSetting(workbook,"G" + Integer.toString(i));
					
					cell = cells.getCell("H" + Integer.toString(i)); cell.setValue(thanhtien);		
					CreateBorderSetting(workbook,"H" + Integer.toString(i));
					
					cell = cells.getCell("I" + Integer.toString(i)); cell.setValue(thue);		
					CreateBorderSetting(workbook,"I" + Integer.toString(i));
					
					cell = cells.getCell("J" + Integer.toString(i)); cell.setValue(thuclanh);		
					CreateBorderSetting(workbook,"J" + Integer.toString(i));
					
					cell = cells.getCell("K" + Integer.toString(i)); cell.setValue("");		
					CreateBorderSetting(workbook,"K" + Integer.toString(i));
					
					
					tongds += tongdoanhso;
					tongtienthue += thue;
					tongtiensauthue += thuclanh;
					tongtienck+=thanhtien;
					so++;
					 
					i++;
    				}
				}
    					
    			
    			cell = cells.getCell("A" + Integer.toString(i)); cell.setValue("");			
				CreateBorderSetting(workbook,"A" + Integer.toString(i));
				
				cell = cells.getCell("B" + Integer.toString(i)); cell.setValue("");		
				CreateBorderSetting(workbook,"B" + Integer.toString(i));
				
				cell = cells.getCell("C" + Integer.toString(i)); cell.setValue("Tổng");		
				CreateBorderSetting(workbook,"C" + Integer.toString(i));
			
				
				cell = cells.getCell("D" + Integer.toString(i)); cell.setValue("");	
				CreateBorderSetting(workbook,"D" + Integer.toString(i));  
									
				cell = cells.getCell("E" + Integer.toString(i)); cell.setValue("");	
				CreateBorderSetting(workbook,"E" + Integer.toString(i));
			
				 
				cell = cells.getCell("F" + Integer.toString(i)); cell.setValue(tongds);		
				CreateBorderSetting(workbook,"F" + Integer.toString(i));
				 
				cell = cells.getCell("G" + Integer.toString(i)); cell.setValue("");		
				CreateBorderSetting(workbook,"G" + Integer.toString(i));
				
				cell = cells.getCell("H" + Integer.toString(i)); cell.setValue(tongtienck);		
				CreateBorderSetting(workbook,"H" + Integer.toString(i));
				
				cell = cells.getCell("I" + Integer.toString(i)); cell.setValue(tongtienthue	);		
				CreateBorderSetting(workbook,"I" + Integer.toString(i));
				
				cell = cells.getCell("J" + Integer.toString(i)); cell.setValue(tongtiensauthue);		
				CreateBorderSetting(workbook,"J" + Integer.toString(i));
				
				cell = cells.getCell("K" + Integer.toString(i)); cell.setValue("");		
				CreateBorderSetting(workbook,"K" + Integer.toString(i));
				
			
				
				 i++;
				 cell = cells.getCell("B" + Integer.toString(i)); cell.setValue("Ghi chú:             Mẫu bảng trên áp dụng nếu khách hàng đạt thưởng ≥ 2.000.000 đồng (lớn hơn  hoặc bằng hai triệu đồng)");
				 style = cell.getStyle();
				 f.setColor(Color.BLACK);
				 f.setBold(false);
				 style.setFont(f);
				 cell.setStyle(style);

				 i++;
				 cell = cells.getCell("J" + Integer.toString(i)); cell.setValue("…, ngày… tháng …  năm ");
				 cell.setStyle(style);
				 
				 i++;
				 
			
	
				 cell = cells.getCell("A" + Integer.toString(i)); cell.setValue("Người lập bảng");				 
				 style = cell.getStyle();
				 f.setBold(true);
				 f.setColor(Color.BLACK);
				 style.setFont(f);
				 cell.setStyle(style);
				 
				 cell = cells.getCell("D" + Integer.toString(i)); cell.setValue("Trình dược viên");
				 cell.setStyle(style);
				 
				 
				 
				 cell = cells.getCell("F" + Integer.toString(i)); cell.setValue("Giám đốc chi nhánh/Phụ trách khu vực");
				 cell.setStyle(style);

				 cell = cells.getCell("J" + Integer.toString(i)); cell.setValue("Phòng Kinh doanh/CNMT/CNMN	");
				 cell.setStyle(style);
				 
				
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("Lỗi dữ liệu");
			}
    		
    		try{
    			

    		     worksheet = worksheets.getSheet(5);
    		     cells = worksheet.getCells();

				int i = 8;
				Cell cell = null;
				
				double tongds = 0;
				double tongtienck = 0;
				int tongsolankt =0;
				double tongtienthue = 0;
				double tongtiensauthue = 0;
				double tongds1=0;
				double tongds2=0;
				double tongds3=0;
				int so = 1;
				Font f = new Font();
				Style style ;
				rs.beforeFirst();
    			while (rs.next()) 
    			{    		
    				int flag=0;
					String tenkh = rs.getString("khten");
					
    				String cmnd = rs.getString("cmnd");
    				 String mafast= rs.getString("maFast");
    				 String diachi=rs.getString("diachi");
    				double doanhsoboga = 0;
    				try{
    					doanhsoboga = rs.getDouble("DS_HHBG");
    				
    				}catch(Exception er){
    					
    				}
    				
    				double doanhsoxanh = 0;
    				try{
    					doanhsoxanh = rs.getDouble("DS_XANH");
    				
    				}catch(Exception er){
    					
    				}
    				
    				double doanhsokhac = 0;
    				try{
    					doanhsokhac = rs.getDouble("DS_KHAC");
    				
    				}catch(Exception er){
    					
    				}
    				double doanhso1=rs.getDouble("t1");
    				double doanhso2=rs.getDouble("t2");
    				double doanhso3=rs.getDouble("t3");
    	    		double doanhso = doanhso1+doanhso2+doanhso3;
    				double tongdoanhso=doanhso1+doanhso2+doanhso3;
    				double ptHHBG = doanhsoboga / doanhso * 100;
    				
    				double ptXANH = doanhsoxanh / doanhso * 100;
    				double ptthuongBG=0;
    				double ptthuongxanh=0;
    				
    				if(obj.getNppID().equals("100010")||obj.getNppID().equals("106211"))
    				{
    					System.out.println("[BT]"+obj.getNppID());
    					if( ( 15 <=ptXANH && ptXANH<25 ) && ptHHBG <=30)
    					{
    						flag=1;
    						ptthuongxanh=0.02;
    					}
    					else if( 25<=ptXANH && ptHHBG <=30)
    					{
    						flag=1;
    						ptthuongxanh=0.03;
    					}
    					if(40<=ptHHBG && ptHHBG <=60)
    					{
    						flag=1;
    						ptthuongBG=0.02;
    					}	
    				}else 
    				{
    					//System.out.println("[###]"+obj.getNppID());
    					
    				 if( ( 15 <=ptXANH && ptXANH<25 ) && ptHHBG <=60)
    					{
    					 flag=1;
    					 ptthuongxanh=0.02;
    					}
    					else if( 25<=ptXANH && ptHHBG <=60)
    					{
    						flag=1;
    						ptthuongxanh=0.03;
    					}
    				 if(40<=ptHHBG && ptHHBG <=60)
    					{
    					 flag=1;
    					 ptthuongBG=0.02;
    					}	
    				 
    				}
    				
    				
    				double pttongthuong=ptthuongBG+ptthuongxanh;
    				
    				double thanhtien = tongdoanhso * pttongthuong;
    				
    				double thue=0;
    				if(thanhtien >= 2000000)
    				{
    					thue = thanhtien * 0.1;
    				}
    				
    				double thuclanh = thanhtien - thue;	
    			//	System.out.println("__________________________________o:"+doanhso1);
					cell = cells.getCell("A" + Integer.toString(i)); cell.setValue(so);			
					CreateBorderSetting(workbook,"A" + Integer.toString(i));
					
					cell = cells.getCell("B" + Integer.toString(i)); cell.setValue(mafast);		
					CreateBorderSetting(workbook,"B" + Integer.toString(i));
					
					cell = cells.getCell("C" + Integer.toString(i)); cell.setValue(tenkh);		
					CreateBorderSetting(workbook,"C" + Integer.toString(i));
								
					cell = cells.getCell("D" + Integer.toString(i)); cell.setValue(doanhso1);	
					CreateBorderSetting(workbook,"D" + Integer.toString(i));
				
					 
					cell = cells.getCell("E" + Integer.toString(i)); cell.setValue(doanhso2);		
					CreateBorderSetting(workbook,"E" + Integer.toString(i));
					 
					cell = cells.getCell("F" + Integer.toString(i)); cell.setValue(doanhso3);		
					CreateBorderSetting(workbook,"F" + Integer.toString(i));
					
					cell = cells.getCell("G" + Integer.toString(i)); cell.setValue(doanhso);		
					CreateBorderSetting(workbook,"G" + Integer.toString(i));
					
					cell = cells.getCell("H" + Integer.toString(i)); cell.setValue(doanhso);		
					CreateBorderSetting(workbook,"H" + Integer.toString(i));
				
					
					tongds += tongdoanhso;
					tongtienthue += thue;
					tongtiensauthue += thuclanh;
					tongtienck+=thanhtien;
					tongds1+=doanhso1;
					tongds2+=doanhso2;
					tongds3+=doanhso3;
					so++;
					 
					i++;
				}
    		
    			cell = cells.getCell("A" + Integer.toString(i)); cell.setValue("");			
				CreateBorderSetting(workbook,"A" + Integer.toString(i));
				
				cell = cells.getCell("B" + Integer.toString(i)); cell.setValue("");		
				CreateBorderSetting(workbook,"B" + Integer.toString(i));
				
				cell = cells.getCell("C" + Integer.toString(i)); cell.setValue("Tổng");		
				CreateBorderSetting(workbook,"C" + Integer.toString(i));
							
				cell = cells.getCell("D" + Integer.toString(i)); cell.setValue(tongds1);	
				CreateBorderSetting(workbook,"D" + Integer.toString(i));
			
				 
				cell = cells.getCell("E" + Integer.toString(i)); cell.setValue(tongds2);		
				CreateBorderSetting(workbook,"E" + Integer.toString(i));
				 
				cell = cells.getCell("F" + Integer.toString(i)); cell.setValue(tongds3);		
				CreateBorderSetting(workbook,"F" + Integer.toString(i));
				
				cell = cells.getCell("G" + Integer.toString(i)); cell.setValue(tongds);		
				CreateBorderSetting(workbook,"G" + Integer.toString(i));
				
				cell = cells.getCell("H" + Integer.toString(i)); cell.setValue(tongds);		
				CreateBorderSetting(workbook,"H" + Integer.toString(i));
			
    			i++;
    			cell = cells.getCell("F" + Integer.toString(i)); cell.setValue("…, ngày … tháng … năm ");			
				CreateBorderSetting(workbook,"F" + Integer.toString(i));
			
    			i++;
    			cell = cells.getCell("B" + Integer.toString(i)); cell.setValue("Người lập bảng");			
				CreateBorderSetting(workbook,"B" + Integer.toString(i));
				
				cell = cells.getCell("F" + Integer.toString(i)); cell.setValue("Giám đốc chi nhánh/Phụ trách khu vực");			
				CreateBorderSetting(workbook,"F" + Integer.toString(i));
				
    			
			
    		} catch(Exception e){
    			e.printStackTrace();
    		}
    	}
		
	}
	
	private void CreateStaticHeader3(Workbook workbook, String userTen, String thang, String nam, String nppTen) {
		// TODO Auto-generated method stub
		
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);

		Cells cells = worksheet.getCells();		

	    Cell cell = cells.getCell("A3");  
	    cell.setValue("Đại lý Traphaco tại: " + nppTen);   		            

	    cell = cells.getCell("A4");
	    cell.setValue("BẢNG KÊ THANH TOÁN THÁNG " + thang + "/ " + nam);
	     	
	    worksheet = worksheets.getSheet(1);
	     cells = worksheet.getCells();		

	     cell = cells.getCell("A3");  
	    cell.setValue("Đại lý Traphaco tại: " + nppTen);   		            

	    cell = cells.getCell("A4");
	    cell.setValue("XÁC NHẬN DOANH SỐ THÁNG" + thang + "/ " + nam);
	     		     
	}

	private void CreateStaticData3(Workbook workbook, String userId,
			String thang, String nam ,String nppTen, IBangKeTTThang obj) {
		// TODO Auto-generated method stub
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();

    	ResultSet rs = obj.getRsBangKeTTThang();
    	
    	if(rs != null)
    	{
    		try {
    			
				int i = 7;
				Cell cell = null;
				
				double tongds = 0;
				double tongtienck = 0;
				
				int so = 1;
				Font f = new Font();
				Style style ;
				double tongthanhtie=0;
				double tongthuclanh=0;
    			while (rs.next()) 
    			{    		
					String tenkh = rs.getString("khten");
    				String diachi = rs.getString("khdiachi");
    				String mahd=rs.getString("mahd");	  			
    							
    				double ck =(rs.getString("ptchietkhau")==null?0:rs.getDouble("ptchietkhau"))/100;   				
    				double doanhso = 0;
    				try{
    					doanhso = rs.getDouble("tongds");
    				
    				}catch(Exception er){
    					
    				}
    				
    				double tienck = 0;
    				tienck = doanhso*ck;
    				
    				double thue = 0;
    				if(tienck >= 2000000)
    				{
    					thue = 0.1;
    				}
    				double tienthue = 0;
    				tienthue = tienck * thue;
    				double thanhtien=0;
    				thanhtien=doanhso*ck;
    				double thuclanh = 0;
    				thuclanh = tienck - tienthue;
    				    				
					cell = cells.getCell("A" + Integer.toString(i)); cell.setValue(so);			
					CreateBorderSetting(workbook,"A" + Integer.toString(i));
					
					cell = cells.getCell("B" + Integer.toString(i)); cell.setValue(mahd);		
					CreateBorderSetting(workbook,"B" + Integer.toString(i));
					
					cell = cells.getCell("C" + Integer.toString(i)); cell.setValue(tenkh);		
					CreateBorderSetting(workbook,"C" + Integer.toString(i));
					
					
					cell = cells.getCell("D" + Integer.toString(i)); cell.setValue(diachi);	
					CreateBorderSetting(workbook,"D" + Integer.toString(i));
					
					cell = cells.getCell("E" + Integer.toString(i)); cell.setValue("");	
					CreateBorderSetting(workbook,"E" + Integer.toString(i));
				
					
					cell = cells.getCell("F" + Integer.toString(i)); cell.setValue(doanhso);		
					CreateBorderSetting(workbook,"F" + Integer.toString(i));
					 
					cell = cells.getCell("G" + Integer.toString(i)); cell.setValue(ck==0?"	":"ĐẠT");		
					CreateBorderSetting(workbook,"G" + Integer.toString(i));
					 
					cell = cells.getCell("H" + Integer.toString(i)); cell.setValue(ck==0?"FALSE":ck);		
					CreateBorderSetting(workbook,"H" + Integer.toString(i));
				
					cell = cells.getCell("I" + Integer.toString(i)); cell.setValue(thanhtien);		
					CreateBorderSetting(workbook,"I" + Integer.toString(i));
				
					cell = cells.getCell("J" + Integer.toString(i)); cell.setValue(tienthue);		
					CreateBorderSetting(workbook,"J" + Integer.toString(i));
					
					cell = cells.getCell("K" + Integer.toString(i)); cell.setValue(thuclanh);		
					CreateBorderSetting(workbook,"K" + Integer.toString(i));
					
					cell = cells.getCell("L" + Integer.toString(i)); cell.setValue("");		
					CreateBorderSetting(workbook,"L" + Integer.toString(i));
				
					tongds += doanhso;
					tongthanhtie += thanhtien;
					tongthuclanh+=thuclanh;
					
					so++;
					 
					i++;
				}
    			/////////////////////////////////////////////////////////////////
    			
    			cell = cells.getCell("A" + Integer.toString(i)); cell.setValue(so);			
				CreateBorderSetting(workbook,"A" + Integer.toString(i));
				
				cell = cells.getCell("B" + Integer.toString(i)); cell.setValue("");		
				CreateBorderSetting(workbook,"B" + Integer.toString(i));
				
				cell = cells.getCell("C" + Integer.toString(i)); cell.setValue("");		
				CreateBorderSetting(workbook,"C" + Integer.toString(i));
				
				
				cell = cells.getCell("D" + Integer.toString(i)); cell.setValue("");	
				CreateBorderSetting(workbook,"D" + Integer.toString(i));
				
				cell = cells.getCell("E" + Integer.toString(i)); cell.setValue("");	
				CreateBorderSetting(workbook,"E" + Integer.toString(i));
			
				
				cell = cells.getCell("F" + Integer.toString(i)); cell.setValue("");		
				CreateBorderSetting(workbook,"F" + Integer.toString(i));
				 
				cell = cells.getCell("G" + Integer.toString(i)); cell.setValue("");		
				CreateBorderSetting(workbook,"G" + Integer.toString(i));
				 
				cell = cells.getCell("H" + Integer.toString(i)); cell.setValue("");		
				CreateBorderSetting(workbook,"H" + Integer.toString(i));
			
				cell = cells.getCell("I" + Integer.toString(i)); cell.setValue("");		
				CreateBorderSetting(workbook,"I" + Integer.toString(i));
			
				cell = cells.getCell("J" + Integer.toString(i)); cell.setValue("");		
				CreateBorderSetting(workbook,"J" + Integer.toString(i));
				
				cell = cells.getCell("L" + Integer.toString(i)); cell.setValue("");		
				CreateBorderSetting(workbook,"L" + Integer.toString(i));
				
				cell = cells.getCell("K" + Integer.toString(i)); cell.setValue("");		
				CreateBorderSetting(workbook,"K" + Integer.toString(i));
    			
   			 cell = cells.getCell("D" + Integer.toString(i)); cell.setValue("Tổng");
				 CreateBorderSetting(workbook,"C" + Integer.toString(i));
				 style = cell.getStyle();
				 f.setBold(true);
				 f.setColor(Color.BLACK);				
				 style.setFont(f);
				 cell.setStyle(style);
				 
				 cell = cells.getCell("F" + Integer.toString(i)); cell.setValue(tongds);
				 CreateBorderSetting(workbook,"F" + Integer.toString(i));
				 style = cell.getStyle();
				 f.setColor(Color.BLACK);
				 style.setFont(f);
				 cell.setStyle(style);
				 
				 cell = cells.getCell("I" + Integer.toString(i)); cell.setValue(tongthanhtie);
				 CreateBorderSetting(workbook,"I" + Integer.toString(i));
				 style = cell.getStyle();
				 f.setColor(Color.BLACK);
				 style.setFont(f);
				 cell.setStyle(style);

				 cell = cells.getCell("K" + Integer.toString(i)); cell.setValue(tongthuclanh);
				 CreateBorderSetting(workbook,"K" + Integer.toString(i));
				 style = cell.getStyle();
				 f.setColor(Color.BLACK);
				 style.setFont(f);
				 cell.setStyle(style);
				 
				 cell = cells.getCell("L" + Integer.toString(i)); cell.setValue("");
				 CreateBorderSetting(workbook,"L" + Integer.toString(i));
				 style = cell.getStyle();
				 f.setColor(Color.BLACK);
				 style.setFont(f);
				 cell.setStyle(style);
				 
		
				 i+=2;
				 cell = cells.getCell("K" + Integer.toString(i)); cell.setValue(" …, ngày … tháng … năm ");
				// cell.setStyle(style);
				 i++;
				 
				 cell = cells.getCell("C" + Integer.toString(i)); cell.setValue("Trình Dược viên");				 
				 style = cell.getStyle();
				 f.setBold(true);
				 f.setColor(Color.BLACK);
				 style.setFont(f);
				 cell.setStyle(style);
				 
				 cell = cells.getCell("D" + Integer.toString(i)); cell.setValue("Phụ trách tỉnh");				 
				 style = cell.getStyle();
				 f.setBold(true);
				 f.setColor(Color.BLACK);
				 style.setFont(f);
				 cell.setStyle(style);
				 
				 cell = cells.getCell("G" + Integer.toString(i)); cell.setValue(" BP. kiểm tra");
				 cell.setStyle(style);
				 
				 cell = cells.getCell("J" + Integer.toString(i)); cell.setValue(" Phòng kinh doanh/CNMT/CNMN");
				 cell.setStyle(style);
    			
    			
				 
				
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("Lỗi dữ liệu");
			}
    	}
    	
    	
    	
    	
    	/////////////////////////
    	
    	
         worksheet = worksheets.getSheet(1);
	     cells = worksheet.getCells();
    
		
	    		try {
	    			rs.beforeFirst();
					int i = 7;
					Cell cell = null;
					
					double tongds = 0;
					double tongtienck = 0;
					
					int so = 1;
					Font f = new Font();
					Style style ;
					
	    			while (rs.next()) 
	    			{    		
	    				String tenkh = rs.getString("khten");
	    				String diachi = rs.getString("khdiachi");
	    				String mahd=rs.getString("mahd");	  			
	    				String makh=rs.getString("khma");		
	    				double ck =(rs.getString("ptchietkhau")==null?0:rs.getDouble("ptchietkhau"))/100;   				
	    				double doanhso = 0;
	    				try{
	    					doanhso = rs.getDouble("tongds");
	    				
	    				}catch(Exception er){
	    					
	    				}
	    				
	    				double tienck = 0;
	    				tienck = doanhso*ck;
	    				
	    				double thue = 0;
	    				if(tienck >= 2000000)
	    				{
	    					thue = 0.1;
	    				}
	    				double tienthue = 0;
	    				tienthue = tienck * thue;
	    				double thanhtien=0;
	    				thanhtien=doanhso*ck;
	    				double thuclanh = 0;
	    				thuclanh = tienck - tienthue;
	    				    				
						cell = cells.getCell("A" + Integer.toString(i)); cell.setValue(so);			
						CreateBorderSetting(workbook,"A" + Integer.toString(i));
						
						cell = cells.getCell("B" + Integer.toString(i)); cell.setValue(makh);		
						CreateBorderSetting(workbook,"B" + Integer.toString(i));
						
						cell = cells.getCell("C" + Integer.toString(i)); cell.setValue(tenkh);		
						CreateBorderSetting(workbook,"C" + Integer.toString(i));
						
						
						cell = cells.getCell("D" + Integer.toString(i)); cell.setValue(diachi);	
						CreateBorderSetting(workbook,"D" + Integer.toString(i));
						
						cell = cells.getCell("E" + Integer.toString(i)); cell.setValue(doanhso);	
						CreateBorderSetting(workbook,"E" + Integer.toString(i));
						
						
						tongds += doanhso;
						tongtienck += tienck;
						
						so++;
						 
						i++;
					}
	    				
	    			cell = cells.getCell("A" + Integer.toString(i)); cell.setValue("");			
					CreateBorderSetting(workbook,"A" + Integer.toString(i));
					
					cell = cells.getCell("B" + Integer.toString(i)); cell.setValue("");		
					CreateBorderSetting(workbook,"B" + Integer.toString(i));
					
					cell = cells.getCell("C" + Integer.toString(i)); cell.setValue("");		
					CreateBorderSetting(workbook,"C" + Integer.toString(i));
					
					
					cell = cells.getCell("D" + Integer.toString(i)); cell.setValue("");	
					CreateBorderSetting(workbook,"D" + Integer.toString(i));
					
					cell = cells.getCell("E" + Integer.toString(i)); cell.setValue("");	
					CreateBorderSetting(workbook,"E" + Integer.toString(i));
			
	    			
					 cell = cells.getCell("D" + Integer.toString(i)); cell.setValue("Tổng");
					 CreateBorderSetting(workbook,"C" + Integer.toString(i));
					 style = cell.getStyle();
					 f.setBold(true);
					 f.setColor(Color.BLACK);				
					 style.setFont(f);
					 cell.setStyle(style);
					 
					 cell = cells.getCell("E" + Integer.toString(i)); cell.setValue(tongds);
					 CreateBorderSetting(workbook,"F" + Integer.toString(i));
					 style = cell.getStyle();
					 f.setColor(Color.BLACK);
					 style.setFont(f);
					 cell.setStyle(style);



					 i++;
					 cell = cells.getCell("D" + Integer.toString(i)); cell.setValue(" …, ngày … tháng … năm ");
					 cell.setStyle(style);
					 i++;
					 
					 
					 cell = cells.getCell("A" + Integer.toString(i)); cell.setValue("Phụ trách tỉnh");				 
					 style = cell.getStyle();
					 f.setBold(true);
					 f.setColor(Color.BLACK);
					 style.setFont(f);
					 cell.setStyle(style);
					
					 cell = cells.getCell("D" + Integer.toString(i)); cell.setValue(" Phòng kinh doanh/CNMT/CNMN");
					 cell.setStyle(style);
					 
					
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("Lỗi dữ liệu");
				}
	    	
		
    	
		
	}
	
	private void CreateStaticHeader4(Workbook workbook, String userTen, String quy, String nam, String nppTen) {
		// TODO Auto-generated method stub
		
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);

		Cells cells = worksheet.getCells();		

	    Cell cell = cells.getCell("A3");  
	    cell.setValue("Đại lý Traphaco tại: " + nppTen);   		            

	    cell = cells.getCell("A4");
	    cell.setValue("                                             BẢNG KÊ THANH TOÁN QUÝ "+quy+"/ " +nam);
	     
	    worksheet = worksheets.getSheet(1);

		 cells = worksheet.getCells();		

	     cell = cells.getCell("A4");  
	    cell.setValue("Đại lý Traphaco tại: " + nppTen);   		            

	    cell = cells.getCell("A5");
	    cell.setValue("XÁC NHẬN DOANH SỐ QUÝ VÀ DOANH SỐ NHÓM SẢN PHẨM");
	     
	    cell = cells.getCell("A6");
	    cell.setValue( "Quý "+quy+"/ " +nam);
	 
	    worksheet.setName("Bảng kê thanh toán theo QUÝ");
	}
	
	private void CreateStaticData4(Workbook workbook, String userId,
			String quy, String nam ,String nppTen, IBangKeTTThang obj) {
		// TODO Auto-generated method stub
		ResultSet rs = obj.getRsBangKeTTThang();
		Worksheets worksheets = workbook.getWorksheets();
		// sheet 1 cua doanh so ck quy doi tac
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();

    	
    	
    	
    
    	
    	System.out.println("[CreateStaticData4]");
    	{
    		try {
    			
				int i = 8;
				Cell cell = null;
				
				
			
				double tongds = 0;
				double tongthanhtien = 0;
				double tongdsboga = 0;
				double tongdsxanh = 0;
				
				
				int so = 1;
				Font f = new Font();
				Style style ;
				
    			while (rs.next()) 
    			{    		
    				int flag=0;
					String tenkh = rs.getString("khten");
					
    				String cmnd = rs.getString("cmnd");
    				 String mahd= rs.getString("mahd");
    				 String diachi=rs.getString("diachi");
    				double doanhsoboga = 0;
    				try{
    					doanhsoboga = rs.getDouble("DS_HHBG");
    				
    				}catch(Exception er){
    					
    				}
    				
    				double doanhsoxanh = 0;
    				try{
    					doanhsoxanh = rs.getDouble("DS_XANH");
    				
    				}catch(Exception er){
    					
    				}
    				
    				double doanhsokhac = 0;
    				try{
    					doanhsokhac = rs.getDouble("DS_KHAC");
    				
    				}catch(Exception er){
    					
    				}
    				double doanhso1=rs.getDouble("t1");
    				double doanhso2=rs.getDouble("t2");
    				double doanhso3=rs.getDouble("t3");
    	    		double doanhso = doanhsoboga + doanhsoxanh + doanhsokhac;
    				double tongdoanhso=doanhso1+doanhso2+doanhso3;
    				double ptHHBG = doanhsoboga / doanhso * 100;
    				
    				double ptXANH = doanhsoxanh / doanhso * 100;
    				double ptthuongBG=0;
    				double ptthuongxanh=0;
    				
    				if(obj.getNppID().equals("100010")||obj.getNppID().equals("106211"))
					{
					//	System.out.println("[BT]"+obj.getNppId());
						if( ( 15 <=ptXANH && ptXANH<25 ) && ptHHBG <=30)
						{
							ptthuongxanh=0.02;
							flag=1;
						}
						else if( 25<=ptXANH && ptHHBG <=30)
						{
							ptthuongxanh=0.03;
							flag=1;
						}
						if( ptHHBG <=30)
						{
							ptthuongBG=0.02;
							flag=1;
						}	
					}else 
					{
					
					 if( ( 15 <=ptXANH && ptXANH<25 ) && ptHHBG <=60)
						{
							ptthuongxanh=0.02;
							flag=1;
						}
						else if( 25<=ptXANH && ptHHBG <=60)
						{
							ptthuongxanh=0.03;
							flag=1;
						}
					 if(40<=ptHHBG && ptHHBG <=60)
						{
						 ptthuongBG=0.02;
						 flag=1;
						}	
					 
					}
    				
    				
    				double pttongthuong=ptthuongBG+ptthuongxanh;
    				
    				double thanhtien = tongdoanhso * pttongthuong;
    				
    				double thue=0;
    				if(thanhtien >= 2000000)
    				{
    					thue = thanhtien * 0.1;
    				}
    				
    				double thuclanh = thanhtien - thue;	
    				
    				if(flag==1)
    				{
					cell = cells.getCell("A" + Integer.toString(i)); cell.setValue(so);			
					CreateBorderSetting(workbook,"A" + Integer.toString(i));
					
					cell = cells.getCell("B" + Integer.toString(i)); cell.setValue(mahd);		
					CreateBorderSetting(workbook,"B" + Integer.toString(i));
					
					cell = cells.getCell("C" + Integer.toString(i)); cell.setValue(tenkh);	
					CreateBorderSetting(workbook,"C" + Integer.toString(i));
					
					cell = cells.getCell("D" + Integer.toString(i)); cell.setValue(diachi);		
					CreateBorderSetting(workbook,"D" + Integer.toString(i));
					 
					cell = cells.getCell("E" + Integer.toString(i)); cell.setValue(doanhso1);		
					CreateBorderSetting(workbook,"E" + Integer.toString(i));
					 					
					cell = cells.getCell("F" + Integer.toString(i)); cell.setValue(doanhso2);		
					CreateBorderSetting(workbook,"F" + Integer.toString(i));
					
					cell = cells.getCell("G" + Integer.toString(i)); cell.setValue(doanhso3);		
					CreateBorderSetting(workbook,"G" + Integer.toString(i));
					
					cell = cells.getCell("H" + Integer.toString(i)); cell.setValue(cmnd);		
					CreateBorderSetting(workbook,"H" + Integer.toString(i));
					
					cell = cells.getCell("I" + Integer.toString(i)); cell.setValue(tongdoanhso);		
					CreateBorderSetting(workbook,"I" + Integer.toString(i));
					
					cell = cells.getCell("J" + Integer.toString(i)); cell.setValue(doanhsoboga);		
					CreateBorderSetting(workbook,"J" + Integer.toString(i));
					
					cell = cells.getCell("K" + Integer.toString(i)); cell.setValue(ptHHBG);		
					CreateBorderSetting(workbook,"K" + Integer.toString(i));
					
					cell = cells.getCell("L" + Integer.toString(i)); cell.setValue(ptthuongBG);		
					CreateBorderSetting(workbook,"L" + Integer.toString(i));
					
					cell = cells.getCell("M" + Integer.toString(i)); cell.setValue(doanhsoxanh);		
					CreateBorderSetting(workbook,"M" + Integer.toString(i));
					
					cell = cells.getCell("N" + Integer.toString(i)); cell.setValue(ptXANH);		
					CreateBorderSetting(workbook,"N" + Integer.toString(i));
					
					cell = cells.getCell("O" + Integer.toString(i)); cell.setValue(ptthuongxanh);		
					CreateBorderSetting(workbook,"O" + Integer.toString(i));
					
					cell = cells.getCell("P" + Integer.toString(i)); cell.setValue(pttongthuong);		
					CreateBorderSetting(workbook,"P" + Integer.toString(i));
					
					cell = cells.getCell("Q" + Integer.toString(i)); cell.setValue(thanhtien);		
					CreateBorderSetting(workbook,"Q" + Integer.toString(i));
					
					cell = cells.getCell("R" + Integer.toString(i)); cell.setValue(thue);		
					CreateBorderSetting(workbook,"R" + Integer.toString(i));
					
					cell = cells.getCell("S" + Integer.toString(i)); cell.setValue(thuclanh);		
					CreateBorderSetting(workbook,"S" + Integer.toString(i));
					
					cell = cells.getCell("T" + Integer.toString(i)); cell.setValue("");		
					CreateBorderSetting(workbook,"T" + Integer.toString(i));
	
					tongds += tongdoanhso;
					tongdsboga +=doanhsoboga;
					tongdsxanh += doanhsoxanh;
					tongthanhtien +=thanhtien;

					so++;
					 
					i++;
    				   
				}
    				
    			}
    			
    			cell = cells.getCell("A" + Integer.toString(i)); cell.setValue("");			
				CreateBorderSetting(workbook,"A" + Integer.toString(i));
				
				cell = cells.getCell("B" + Integer.toString(i)); cell.setValue("");		
				CreateBorderSetting(workbook,"B" + Integer.toString(i));
				
				cell = cells.getCell("C" + Integer.toString(i)); cell.setValue("");	
				CreateBorderSetting(workbook,"C" + Integer.toString(i));
				
				cell = cells.getCell("D" + Integer.toString(i)); cell.setValue("");		
				CreateBorderSetting(workbook,"D" + Integer.toString(i));
				 
				cell = cells.getCell("E" + Integer.toString(i)); cell.setValue("");		
				CreateBorderSetting(workbook,"E" + Integer.toString(i));
				 					
				cell = cells.getCell("F" + Integer.toString(i)); cell.setValue("");		
				CreateBorderSetting(workbook,"F" + Integer.toString(i));
				
				cell = cells.getCell("G" + Integer.toString(i)); cell.setValue("");		
				CreateBorderSetting(workbook,"G" + Integer.toString(i));
				
				cell = cells.getCell("H" + Integer.toString(i)); cell.setValue("");		
				CreateBorderSetting(workbook,"H" + Integer.toString(i));
				
				cell = cells.getCell("I" + Integer.toString(i)); cell.setValue("");		
				CreateBorderSetting(workbook,"I" + Integer.toString(i));
				
				cell = cells.getCell("J" + Integer.toString(i)); cell.setValue("");		
				CreateBorderSetting(workbook,"J" + Integer.toString(i));
				
				cell = cells.getCell("K" + Integer.toString(i)); cell.setValue("");		
				CreateBorderSetting(workbook,"K" + Integer.toString(i));
				
				cell = cells.getCell("L" + Integer.toString(i)); cell.setValue("");		
				CreateBorderSetting(workbook,"L" + Integer.toString(i));
				
				cell = cells.getCell("M" + Integer.toString(i)); cell.setValue("");		
				CreateBorderSetting(workbook,"M" + Integer.toString(i));
				
				cell = cells.getCell("N" + Integer.toString(i)); cell.setValue("");		
				CreateBorderSetting(workbook,"N" + Integer.toString(i));
				
				cell = cells.getCell("O" + Integer.toString(i)); cell.setValue("");		
				CreateBorderSetting(workbook,"O" + Integer.toString(i));
				
				cell = cells.getCell("P" + Integer.toString(i)); cell.setValue("");		
				CreateBorderSetting(workbook,"P" + Integer.toString(i));
				
				cell = cells.getCell("Q" + Integer.toString(i)); cell.setValue("");		
				CreateBorderSetting(workbook,"Q" + Integer.toString(i));
				
				cell = cells.getCell("R" + Integer.toString(i)); cell.setValue("");		
				CreateBorderSetting(workbook,"R" + Integer.toString(i));
				
				cell = cells.getCell("S" + Integer.toString(i)); cell.setValue("");		
				CreateBorderSetting(workbook,"S" + Integer.toString(i));
				
				cell = cells.getCell("T" + Integer.toString(i)); cell.setValue("");		
				CreateBorderSetting(workbook,"T" + Integer.toString(i));

	
    			
    			
    			 cell = cells.getCell("D" + Integer.toString(i)); cell.setValue("Tổng");
				// CreateBorderSetting(workbook,"D" + Integer.toString(i));
				 style = cell.getStyle();
				 f.setBold(true);
				 f.setColor(Color.BLACK);				
				 style.setFont(f);
				 cell.setStyle(style);
				 
				 cell = cells.getCell("I" + Integer.toString(i)); cell.setValue(tongds);
				// CreateBorderSetting(workbook,"I" + Integer.toString(i));
				 style = cell.getStyle();
				 f.setColor(Color.BLACK);
				 style.setFont(f);
				 cell.setStyle(style);
				 
				 cell = cells.getCell("J" + Integer.toString(i)); cell.setValue(tongdsboga);
				// CreateBorderSetting(workbook,"J" + Integer.toString(i));
				 style = cell.getStyle();
				 f.setColor(Color.BLACK);
				 style.setFont(f);
				 cell.setStyle(style);
				 
				 cell = cells.getCell("M" + Integer.toString(i)); cell.setValue(tongdsxanh);
				// CreateBorderSetting(workbook,"M" + Integer.toString(i));
				 style = cell.getStyle();
				 f.setColor(Color.BLACK);
				 style.setFont(f);
				 cell.setStyle(style);
				 
				 cell = cells.getCell("Q" + Integer.toString(i)); cell.setValue(tongthanhtien);
				// CreateBorderSetting(workbook,"Q" + Integer.toString(i));
				 style = cell.getStyle();
				 f.setColor(Color.BLACK);
				 style.setFont(f);
				 cell.setStyle(style);

				 
				 
				 
				 
				 
				 i+=2;
				 
				// Tien bang chu
				 doctienrachu doctien = new doctienrachu();
			     String tien = doctien.docTien(Math.round(tongthanhtien));		   
			     //Viết hoa ký tự đầu tiên
			     String TienIN = (tien.substring(0,1)).toUpperCase() + tien.substring(1);
				 
				 cell = cells.getCell("D" + Integer.toString(i)); cell.setValue("Bằng chữ: " + TienIN);
				 style = cell.getStyle();
				 f.setColor(Color.BLACK);
				// f.setBold(false);
				 style.setFont(f);
				 cell.setStyle(style);

				 i++;
		
				 
				 cell = cells.getCell("D" + Integer.toString(i)); cell.setValue("Ghi chú:             Mẫu bảng trên áp dụng nếu khách hàng đạt thưởng ≥ 2.000.000 đồng (lớn hơn  hoặc bằng hai triệu đồng)");
				 style = cell.getStyle();
				 f.setColor(Color.BLACK);
				 f.setBold(false);
				 style.setFont(f);
				 cell.setStyle(style);


				 i++;
				 
				 cell = cells.getCell("D" + Integer.toString(i)); cell.setValue("              Nếu dưới hai triệu đồng thì khách hàng không phải chịu thuế 10%.");
				 style = cell.getStyle();
				 f.setColor(Color.BLACK);
				 f.setBold(false);
				 style.setFont(f);
				 cell.setStyle(style);

				 i++;
				 cell = cells.getCell("S" + Integer.toString(i)); cell.setValue(" …, ngày … tháng … năm ");
				 cell.setStyle(style);
				 i++;
				 
				 cell = cells.getCell("B" + Integer.toString(i)); cell.setValue("Trình Dược viên");				 
				 style = cell.getStyle();
				 f.setBold(true);
				 f.setColor(Color.BLACK);
				 style.setFont(f);
				 cell.setStyle(style);
				 
				 cell = cells.getCell("J" + Integer.toString(i)); cell.setValue("Phụ trách tỉnh");				 
				 style = cell.getStyle();
				 f.setBold(true);
				 f.setColor(Color.BLACK);
				 style.setFont(f);
				 cell.setStyle(style);
				 
				 cell = cells.getCell("M" + Integer.toString(i)); cell.setValue(" BP. kiểm tra");
				 cell.setStyle(style);
				 
				 cell = cells.getCell("S" + Integer.toString(i)); cell.setValue(" TP.Kinh doanh/GĐ Chi nhánh C1");
				 cell.setStyle(style);
				 
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				System.out.println("Lỗi dữ liệu");
			}
    	}
    	
    	  Worksheet worksheet1 = worksheets.getSheet(1);
  	    Cells cells1 = worksheet1.getCells();


		try {
			
			int i = 9;
			Cell cell = null;
			
			
			
			double tongds = 0;
			double tongthanhtien = 0;
			double tongdsboga = 0;
			double tongdsxanh = 0;
			double tongd1 = 0;
			double tongds2 = 0;
			double tongds3 = 0;
			
			
			int so = 1;
			Font f = new Font();
			Style style ;
			rs.beforeFirst();
			while (rs.next()) 
			{    		
				int flag=0;
				String tenkh = rs.getString("khten");
				
				String cmnd = rs.getString("cmnd");
				 String mahd= rs.getString("mahd");
				 String diachi=rs.getString("diachi");
				double doanhsoboga = 0;
				try{
					doanhsoboga = rs.getDouble("DS_HHBG");
				
				}catch(Exception er){
					
				}
				
				double doanhsoxanh = 0;
				try{
					doanhsoxanh = rs.getDouble("DS_XANH");
				
				}catch(Exception er){
					
				}
				
				double doanhsokhac = 0;
				try{
					doanhsokhac = rs.getDouble("DS_KHAC");
				
				}catch(Exception er){
					
				}
				double doanhso1=rs.getDouble("t1");
				double doanhso2=rs.getDouble("t2");
				double doanhso3=rs.getDouble("t3");
	    		double doanhso = doanhsoboga + doanhsoxanh + doanhsokhac;
				double tongdoanhso=doanhso1+doanhso2+doanhso3;
				double ptHHBG = doanhsoboga / doanhso * 100;
				
				double ptXANH = doanhsoxanh / doanhso * 100;
				double ptthuongBG=0;
				double ptthuongxanh=0;
				
				if(obj.getNppID().equals("100010")||obj.getNppID().equals("106211"))
				{
					System.out.println("[BT]"+obj.getNppID());
					if( ( 15 <=ptXANH && ptXANH<25 ) && ptHHBG <=30)
					{
						ptthuongxanh=0.02;
					}
					else if( 25<=ptXANH && ptHHBG <=30)
					{
						ptthuongxanh=0.03;
					}
					if(40<=ptHHBG && ptHHBG <=60)
					{
						ptthuongBG=0.02;
					}	
				}else 
				{
					System.out.println("[###]"+obj.getNppID());
					
				 if( ( 15 <=ptXANH && ptXANH<25 ) && ptHHBG <=60)
					{
					 ptthuongxanh=0.02;
					}
					else if( 25<=ptXANH && ptHHBG <=60)
					{
						ptthuongxanh=0.03;
					}
				 if(40<=ptHHBG && ptHHBG <=60)
					{
					 ptthuongBG=0.02;
					}	
				 
				}
				
				if(ptHHBG < 60 && ptXANH >=40)
				{
					flag=1;
					ptthuongBG=0.02;
				}
				if(ptXANH>=25 && ptHHBG<60 )
				{
					flag=1;
					ptthuongxanh=0.03;
				}
				if(ptXANH<25 && ptHHBG<60 && ptXANH>=15 )
				{
					flag=1;
					ptthuongxanh=0.02;
				}
				double pttongthuong=ptthuongBG+ptthuongxanh;
				
				double thanhtien = doanhso * pttongthuong;
				
				double thue=0;
				if(thanhtien >= 2000000)
				{
					thue = thanhtien * 0.1;
				}
				
				double thuclanh = thanhtien - thue;	
				
				
				
				   if(flag==1 )
				   {
					   tongd1 += doanhso1;
						 tongds2 += doanhso2;
						 tongds3 += doanhso3;
				cell = cells1.getCell("A" + Integer.toString(i)); cell.setValue(so);			
				CreateBorderSetting(workbook,"A" + Integer.toString(i));
				
				cell = cells1.getCell("B" + Integer.toString(i)); cell.setValue(mahd);		
				CreateBorderSetting(workbook,"B" + Integer.toString(i));
				
				cell = cells1.getCell("C" + Integer.toString(i)); cell.setValue("");	
				CreateBorderSetting(workbook,"C" + Integer.toString(i));
				
				cell = cells1.getCell("D" + Integer.toString(i)); cell.setValue(tenkh);		
				CreateBorderSetting(workbook,"D" + Integer.toString(i));
				 
				cell = cells1.getCell("E" + Integer.toString(i)); cell.setValue(cmnd);		
				CreateBorderSetting(workbook,"E" + Integer.toString(i));
				 					
				cell = cells1.getCell("F" + Integer.toString(i)); cell.setValue(diachi);		
				CreateBorderSetting(workbook,"F" + Integer.toString(i));
				
				cell = cells1.getCell("G" + Integer.toString(i)); cell.setValue(doanhso1);		
				CreateBorderSetting(workbook,"G" + Integer.toString(i));
				
				cell = cells1.getCell("H" + Integer.toString(i)); cell.setValue(doanhso2);		
				CreateBorderSetting(workbook,"H" + Integer.toString(i));
				
				cell = cells1.getCell("I" + Integer.toString(i)); cell.setValue(doanhso3);		
				CreateBorderSetting(workbook,"I" + Integer.toString(i));
				
				cell = cells1.getCell("J" + Integer.toString(i)); cell.setValue(doanhso);		
				CreateBorderSetting(workbook,"J" + Integer.toString(i));
				
				cell = cells1.getCell("K" + Integer.toString(i)); cell.setValue(doanhsoboga);		
				CreateBorderSetting(workbook,"K" + Integer.toString(i));
		
				cell = cells1.getCell("L" + Integer.toString(i)); cell.setValue(doanhsoxanh);		
				CreateBorderSetting(workbook,"L" + Integer.toString(i));
			

				tongds += tongdoanhso;
				tongdsboga +=doanhsoboga;
				tongdsxanh += doanhsoxanh;
				tongthanhtien +=thanhtien;

				so++;
				 
				i++;
				   }
			}
						
			cell = cells1.getCell("A" + Integer.toString(i)); cell.setValue("");			
			CreateBorderSetting(workbook,"A" + Integer.toString(i));
			
			cell = cells1.getCell("B" + Integer.toString(i)); cell.setValue("");		
			CreateBorderSetting(workbook,"B" + Integer.toString(i));
			
			cell = cells1.getCell("C" + Integer.toString(i)); cell.setValue("");	
			CreateBorderSetting(workbook,"C" + Integer.toString(i));
			
			cell = cells1.getCell("D" + Integer.toString(i)); cell.setValue("Tổng");		
			CreateBorderSetting(workbook,"D" + Integer.toString(i));
			 
			cell = cells1.getCell("E" + Integer.toString(i)); cell.setValue("");		
			CreateBorderSetting(workbook,"E" + Integer.toString(i));
			 					
			cell = cells1.getCell("F" + Integer.toString(i)); cell.setValue("");		
			CreateBorderSetting(workbook,"F" + Integer.toString(i));
			
			cell = cells1.getCell("G" + Integer.toString(i)); cell.setValue(tongd1);		
			CreateBorderSetting(workbook,"G" + Integer.toString(i));
			
			cell = cells1.getCell("H" + Integer.toString(i)); cell.setValue(tongds2);		
			CreateBorderSetting(workbook,"H" + Integer.toString(i));
			
			cell = cells1.getCell("I" + Integer.toString(i)); cell.setValue(tongds3);		
			CreateBorderSetting(workbook,"I" + Integer.toString(i));
			
			cell = cells1.getCell("J" + Integer.toString(i)); cell.setValue(tongds);		
			CreateBorderSetting(workbook,"J" + Integer.toString(i));
			
			cell = cells1.getCell("K" + Integer.toString(i)); cell.setValue(tongdsboga);		
			CreateBorderSetting(workbook,"K" + Integer.toString(i));
	
			cell = cells1.getCell("L" + Integer.toString(i)); cell.setValue(tongdsxanh);		
			CreateBorderSetting(workbook,"L" + Integer.toString(i));
			
			i+=2;
			
			 cell = cells1.getCell("B" + Integer.toString(i)); cell.setValue("Phụ trách tỉnh");				 
			 style = cell.getStyle();
			 f.setBold(true);
			 f.setColor(Color.BLACK);
			 style.setFont(f);
			 cell.setStyle(style);	
			 
			 cell = cells1.getCell("J" + Integer.toString(i)); cell.setValue(" …, ngày … tháng … năm ");				 
			 style = cell.getStyle();
			 f.setBold(true);
			 f.setColor(Color.BLACK);
			 style.setFont(f);
			 cell.setStyle(style);	
			 i++;
			 cell = cells1.getCell("J" + Integer.toString(i)); cell.setValue(" Xác nhận của Công ty đối tác ");				 
			 style = cell.getStyle();
			 f.setBold(true);
			 f.setColor(Color.BLACK);
			 style.setFont(f);
			 cell.setStyle(style);	
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("Lỗi dữ liệu");
		}
	
		
	}
	
	private void CreateStaticHeader5(Workbook workbook, String userTen, String thang, String nam, String nppTen) {
		// TODO Auto-generated method stub
		
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);

		Cells cells = worksheet.getCells();		

	    Cell cell = cells.getCell("A4");  
	    cell.setValue("Đại lý Traphaco tại: " + nppTen);   		            

	    cell = cells.getCell("A5");
	    cell.setValue("BẢNG KÊ THANH TOÁN THÁNG " + thang + "/ " + nam);
	     		     
	    worksheet.setName("Xác nhận doanh số tháng đối tác");
	}
	
	
	private void CreateStaticData5(Workbook workbook, String userId,
			String thang, String nam ,String nppTen, IBangKeTTThang obj) {
		// TODO Auto-generated method stub
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();

    	ResultSet rs = obj.getRsBangKeTTThang();
    	
    
    	{
    		try {
    			
				int i = 9;
				Cell cell = null;
				
				double tongds = 0;
				double tongtienck = 0;
				
				int so = 1;
				Font f = new Font();
				Style style ;
				
    			while (rs.next()) 
    			{    		
					String tenkh = rs.getString("tenkh");
    				String makh = rs.getString("mafast");
    				String diachi = rs.getString("diachi");
    				String masothue = rs.getString("masothue");
    				  			
    				
    				
    				double doanhso = 0;
    				try{
    					doanhso = rs.getDouble("doanhso");
    				
    				}catch(Exception er){
    					
    				}
    				
    				    				
					cell = cells.getCell("A" + Integer.toString(i)); cell.setValue(so);			
					CreateBorderSetting(workbook,"A" + Integer.toString(i));
					
					cell = cells.getCell("B" + Integer.toString(i)); cell.setValue(makh);		
					CreateBorderSetting(workbook,"B" + Integer.toString(i));
					
					cell = cells.getCell("D" + Integer.toString(i)); cell.setValue(tenkh);	
					CreateBorderSetting(workbook,"D" + Integer.toString(i));
					
					cell = cells.getCell("F" + Integer.toString(i)); cell.setValue(masothue);		
					CreateBorderSetting(workbook,"F" + Integer.toString(i));
					 
					cell = cells.getCell("G" + Integer.toString(i)); cell.setValue(diachi);		
					CreateBorderSetting(workbook,"G" + Integer.toString(i));
					 					
					cell = cells.getCell("H" + Integer.toString(i)); cell.setValue(doanhso);		
					CreateBorderSetting(workbook,"H" + Integer.toString(i));
	
					tongds += doanhso;

					so++;
					 
					i++;
				}
    					
    			
    			 cell = cells.getCell("A" + Integer.toString(i)); cell.setValue("");
				 CreateBorderSetting(workbook,"A" + Integer.toString(i));
				 cell = cells.getCell("B" + Integer.toString(i)); cell.setValue("");
				 CreateBorderSetting(workbook,"B" + Integer.toString(i));
				 
				 cell = cells.getCell("F" + Integer.toString(i)); cell.setValue("");
				 CreateBorderSetting(workbook,"F" + Integer.toString(i));
				 cell = cells.getCell("G" + Integer.toString(i)); cell.setValue("");
				 CreateBorderSetting(workbook,"G" + Integer.toString(i));
	
    			
    			
    			 cell = cells.getCell("D" + Integer.toString(i)); cell.setValue("Tổng");
				 CreateBorderSetting(workbook,"D" + Integer.toString(i));
				 style = cell.getStyle();
				 f.setBold(true);
				 f.setColor(Color.BLACK);				
				 style.setFont(f);
				 cell.setStyle(style);
				 
				 cell = cells.getCell("H" + Integer.toString(i)); cell.setValue(tongds);
				 CreateBorderSetting(workbook,"H" + Integer.toString(i));
				 style = cell.getStyle();
				 f.setColor(Color.BLACK);
				 style.setFont(f);
				 cell.setStyle(style); 
				

				 i=i+3;
				 
				 cell = cells.getCell("A" + Integer.toString(i)); cell.setValue("Phụ trách tỉnh");				 
				 style = cell.getStyle();
				 f.setBold(true);
				 f.setColor(Color.BLACK);
				 style.setFont(f);
				 cell.setStyle(style);
				 
				 cell = cells.getCell("H" + Integer.toString(i)); cell.setValue(" Xác nhận của Công ty đối tác ");
				 cell.setStyle(style);
				 
				
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("Lỗi dữ liệu");
			}
    	}
		
	}
	
	private void CreateStaticHeader6(Workbook workbook, String userTen, String quy, String nam, String nppTen , IBangKeTTThang obj) {
		// TODO Auto-generated method stub
		
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);

		Cells cells = worksheet.getCells();		

	    Cell cell = cells.getCell("A4");  
	    cell.setValue("Đại lý Traphaco tại: " + nppTen);   		            

	    cell = cells.getCell("A7");
	    cell.setValue("Qúy "+ quy +" / "+nam);
	     		     
	    cell = cells.getCell("G9");
	    cell.setValue("Tháng " + obj.getT1() );
	    
	    cell = cells.getCell("H9");
	    cell.setValue("Tháng " + obj.getT2() );
	    
	    cell = cells.getCell("I9");
	    cell.setValue("Tháng " + obj.getT3() );
	    
	    worksheet.setName("Xác nhận doanh số Quý và nhóm");
	}
	
	private void CreateStaticData6(Workbook workbook, String userId,
			String quy, String nam ,String nppTen, IBangKeTTThang obj) {
		// TODO Auto-generated method stub
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();

    	ResultSet rs = obj.getRsBangKeTTThang();
    	
    	if(rs != null)
    	{
    		try {
    			
				int i = 10;
				Cell cell = null;
				
				double tongds = 0;
				double tongtienck = 0;
				
				int so = 1;
				Font f = new Font();
				Style style ;
				
    			while (rs.next()) 
    			{    		
					String tenkh = rs.getString("ten");
    				String makh = rs.getString("mafast");
    				String diachi = rs.getString("diachi");
    				String masothue = rs.getString("masothue");
    				  			
    				
    				
    				double doanhsot1 = 0;
    				try{
    					doanhsot1 = rs.getDouble("t1");
    				
    				}catch(Exception er){
    					
    				}
    				
    				double doanhsot2 = 0;
    				try{
    					doanhsot2 = rs.getDouble("t2");
    				
    				}catch(Exception er){
    					
    				}
    				
    				double doanhsot3 = 0;
    				try{
    					doanhsot3 = rs.getDouble("t3");
    				
    				}catch(Exception er){
    					
    				}
    				
    				double doanhso = doanhsot1 + doanhsot2 + doanhsot3;
    				
    				double doanhsoboga = 0;
    				try{
    					doanhsoboga = rs.getDouble("dsnhomhh");
    				
    				}catch(Exception er){
    					
    				}
    				
    				double doanhsoxanh = 0;
    				try{
    					doanhsoxanh = rs.getDouble("dsnhomxanh");
    				
    				}catch(Exception er){
    					
    				}
    				
    				    				
					cell = cells.getCell("A" + Integer.toString(i)); cell.setValue(so);			
					CreateBorderSetting(workbook,"A" + Integer.toString(i));
					
					cell = cells.getCell("B" + Integer.toString(i)); cell.setValue(makh);		
					CreateBorderSetting(workbook,"B" + Integer.toString(i));
					
					cell = cells.getCell("D" + Integer.toString(i)); cell.setValue(tenkh);	
					CreateBorderSetting(workbook,"D" + Integer.toString(i));
					
					cell = cells.getCell("E" + Integer.toString(i)); cell.setValue(masothue);		
					CreateBorderSetting(workbook,"E" + Integer.toString(i));
					 
					cell = cells.getCell("F" + Integer.toString(i)); cell.setValue(diachi);		
					CreateBorderSetting(workbook,"F" + Integer.toString(i));
					 					
					cell = cells.getCell("G" + Integer.toString(i)); cell.setValue(doanhsot1);		
					CreateBorderSetting(workbook,"G" + Integer.toString(i));
					
					cell = cells.getCell("H" + Integer.toString(i)); cell.setValue(doanhsot2);		
					CreateBorderSetting(workbook,"H" + Integer.toString(i));
					
					cell = cells.getCell("I" + Integer.toString(i)); cell.setValue(doanhsot3);		
					CreateBorderSetting(workbook,"I" + Integer.toString(i));
					
					cell = cells.getCell("J" + Integer.toString(i)); cell.setValue(doanhso);		
					CreateBorderSetting(workbook,"J" + Integer.toString(i));
					
					cell = cells.getCell("K" + Integer.toString(i)); cell.setValue(doanhsoboga);		
					CreateBorderSetting(workbook,"K" + Integer.toString(i));
					
					cell = cells.getCell("L" + Integer.toString(i)); cell.setValue(doanhsoxanh);		
					CreateBorderSetting(workbook,"L" + Integer.toString(i));
	
					tongds += doanhso;

					so++;
					 
					i++;
				}
    					
    			
    			 cell = cells.getCell("A" + Integer.toString(i)); cell.setValue("");
				 CreateBorderSetting(workbook,"A" + Integer.toString(i));
				 cell = cells.getCell("B" + Integer.toString(i)); cell.setValue("");
				 CreateBorderSetting(workbook,"B" + Integer.toString(i));
				 
				 cell = cells.getCell("F" + Integer.toString(i)); cell.setValue("");
				 CreateBorderSetting(workbook,"F" + Integer.toString(i));
				 cell = cells.getCell("G" + Integer.toString(i)); cell.setValue("");
				 CreateBorderSetting(workbook,"G" + Integer.toString(i));
				 
				 cell = cells.getCell("E" + Integer.toString(i)); cell.setValue("");
				 CreateBorderSetting(workbook,"E" + Integer.toString(i));
				 cell = cells.getCell("H" + Integer.toString(i)); cell.setValue("");
				 CreateBorderSetting(workbook,"H" + Integer.toString(i));
				 cell = cells.getCell("I" + Integer.toString(i)); cell.setValue("");
				 CreateBorderSetting(workbook,"I" + Integer.toString(i));
				 cell = cells.getCell("K" + Integer.toString(i)); cell.setValue("");
				 CreateBorderSetting(workbook,"K" + Integer.toString(i));
				 cell = cells.getCell("L" + Integer.toString(i)); cell.setValue("");
				 CreateBorderSetting(workbook,"L" + Integer.toString(i));
	
    			
    			
    			 cell = cells.getCell("D" + Integer.toString(i)); cell.setValue("Tổng");
				 CreateBorderSetting(workbook,"D" + Integer.toString(i));
				 style = cell.getStyle();
				 f.setBold(true);
				 f.setColor(Color.BLACK);				
				 style.setFont(f);
				 cell.setStyle(style);
				 
				 cell = cells.getCell("J" + Integer.toString(i)); cell.setValue(tongds);
				 CreateBorderSetting(workbook,"J" + Integer.toString(i));
				 style = cell.getStyle();
				 f.setColor(Color.BLACK);
				 style.setFont(f);
				 cell.setStyle(style);
				 

				 i=i+3;
				 
				 cell = cells.getCell("A" + Integer.toString(i)); cell.setValue("Phụ trách tỉnh");				 
				 style = cell.getStyle();
				 f.setBold(true);
				 f.setColor(Color.BLACK);
				 style.setFont(f);
				 cell.setStyle(style);
				 
				 cell = cells.getCell("K" + Integer.toString(i)); cell.setValue(" Xác nhận của Công ty đối tác ");
				 cell.setStyle(style);
				 
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				System.out.println("Lỗi dữ liệu");
			}
    	}
		
	}
	
	
	public void CreateBorderSetting(Workbook workbook,String fileName) throws IOException
    {
	 Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
        Cells cells = worksheet.getCells();
        Cell cell;
        Style style;

        cell = cells.getCell(fileName);
        style = cell.getStyle();

        //Set border color
        style.setBorderColor(BorderType.TOP, Color.BLACK);
        style.setBorderColor(BorderType.BOTTOM, Color.BLACK);
        style.setBorderColor(BorderType.LEFT, Color.BLACK);
        style.setBorderColor(BorderType.RIGHT, Color.BLACK);
        //style.setBorderColor(BorderType.DIAGONAL_DOWN, Color.BLUE);
        //style.setBorderColor(BorderType.DIAGONAL_UP, Color.BLUE);

        //Set the cell border type
        style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
        style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
        style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
        style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
        //style.setBorderLine(BorderType.DIAGONAL_DOWN, BorderLineType.DASHED);
        //style.setBorderLine(BorderType.DIAGONAL_UP, BorderLineType.DASHED);

        cell.setStyle(style);

       
    }
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy : hh-mm-ss");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	private void getCellStyle(Workbook workbook, String a, Color mau, Boolean dam, int size)
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    Cells cells = worksheet.getCells();
		Style style;
		Cell cell = cells.getCell(a); 
		 style = cell.getStyle();
		 style.setHAlignment(TextAlignmentType.CENTER);
	        Font font1 = new Font();
	        font1.setColor(mau);
	        font1.setBold(dam);
	        font1.setSize(size);
	        style.setFont(font1);
	        cell.setStyle(style);
	}
	
	private void setCellBorderStyle(Cell cell, short alignment) 
	{
		Style style = cell.getStyle();
		//style.setHAlignment(HorizontalAlignmentType.CENTRED);
		style.setHAlignment(alignment);
		style.setBorderLine(BorderType.TOP, 0);
		style.setBorderLine(BorderType.RIGHT, 1);
		style.setBorderLine(BorderType.BOTTOM, 1);
		style.setBorderLine(BorderType.LEFT, 1);
		cell.setStyle(style);
	}
	
	private void setCellBorderStyle2(Cell cell, short alignment) 
	{
		Style style = cell.getStyle();
		style.setHAlignment(alignment);
		style.setBorderLine(BorderType.TOP, 0);
		style.setBorderLine(BorderType.RIGHT, 1);
		style.setBorderLine(BorderType.BOTTOM, 1);
		style.setBorderLine(BorderType.LEFT, 1);
		cell.setStyle(style);
	}
	
	private void setCellBorderStyle1(Cell cell, short alignment) 
	{
		Style style = cell.getStyle();
		style.setHAlignment(alignment);
		style.setBorderLine(BorderType.TOP, 1);
		style.setBorderLine(BorderType.RIGHT, 1);
		style.setBorderLine(BorderType.BOTTOM, 1);
		style.setBorderLine(BorderType.LEFT, 1);
		cell.setStyle(style);
	}
	
	private void setHeaderCell(Cell cell) 
	{		
		Style style = cell.getStyle();		
		style.setHAlignment(HorizontalAlignmentType.CENTRED);
		style.setBorderLine(BorderType.BOTTOM, 1);
		style.setBorderLine(BorderType.LEFT, 1);
		style.setBorderLine(BorderType.TOP, 1);
		style.setBorderLine(BorderType.RIGHT, 1);
		cell.setStyle(style);
	}
	

}
