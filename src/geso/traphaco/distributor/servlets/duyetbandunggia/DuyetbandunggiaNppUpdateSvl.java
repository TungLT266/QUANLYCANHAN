package geso.traphaco.distributor.servlets.duyetbandunggia;

import geso.traphaco.distributor.beans.duyetbandunggia.IDuyetbandunggiaNpp;
import geso.traphaco.distributor.beans.duyetbandunggia.IDuyetbandunggiaNppList;
import geso.traphaco.distributor.beans.duyetbandunggia.imp.DuyetbandunggiaNpp;
import geso.traphaco.distributor.beans.duyetbandunggia.imp.DuyetbandunggiaNppList;
import geso.traphaco.center.beans.upload.IUpload;
import geso.traphaco.center.beans.upload.imp.Upload;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.oreilly.servlet.MultipartRequest;
import com.sun.org.apache.xpath.internal.operations.Gte;

import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class DuyetbandunggiaNppUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out; 
	private static final String UPLOAD_DIRECTORY = "C:\\upload\\";	
    public DuyetbandunggiaNppUpdateSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		IDuyetbandunggiaNpp tctskuBean;
		
		this.out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    	    
	    String id = util.getId(querystring);
	   
	    tctskuBean = new DuyetbandunggiaNpp(id);
	    tctskuBean.setId(id);
	    tctskuBean.setUserId(userId);
	    
        String nextJSP = "";
        if(querystring.indexOf("display") >= 0)
        {
        	tctskuBean.init_display();
        	nextJSP = "/TraphacoHYERP/pages/Distributor/DuyetBanDungGiaNppDisplay.jsp";
        }
        else
        {
        	tctskuBean.init();
        	nextJSP = "/TraphacoHYERP/pages/Distributor/DuyetBanDungGiaNppUpdate.jsp";
        }
        
        session.setAttribute("tctskuBean", tctskuBean);
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IDuyetbandunggiaNpp tctskuBean;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		//this.out = response.getWriter();
		Utility util = new Utility();
		
	   	String id = util.antiSQLInspection(request.getParameter("id"));
	    if(id == null){  	
	    	tctskuBean = new DuyetbandunggiaNpp("");
	    }else{
	    	tctskuBean = new DuyetbandunggiaNpp(id);
	    }
	    
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		tctskuBean.setUserId(userId);
		
		String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
		if (diengiai == null)
			diengiai = "";
		tctskuBean.setDiengiai(diengiai);
		
		String thang = util.antiSQLInspection(request.getParameter("thang"));
		if (thang == null)
			thang = Integer.toString(Integer.parseInt(getDateTime().split("-")[1]));
		tctskuBean.setThang(thang);
		
		String loaict = util.antiSQLInspection(request.getParameter("loaict"));
		if (loaict == null)
			loaict = "0";
		tctskuBean.setLoaict(loaict);
		
		String quy = util.antiSQLInspection(request.getParameter("quy"));
		if (quy == null)
			quy = getQuyHienTai();
		tctskuBean.setQuy(quy);
		
		String nam = util.antiSQLInspection(request.getParameter("nam"));
		if (nam == null)
			nam = "";
		tctskuBean.setNam(nam);
		
		String nppId = util.antiSQLInspection(request.getParameter("nppId"));
		if (nppId == null)
			nppId = "";
		tctskuBean.setNppId(nppId);
		
		/*String[] nppIds = request.getParameterValues("nppIds");
		if(nppIds != null)
		{
			String nppId = "";
			for(int i = 0; i < nppIds.length; i++)
			{
				nppId += nppIds[i] + ",";
			}
			
			if(nppId.trim().length() > 0)
			{
				nppId = nppId.substring(0, nppId.length() - 1);
				tctskuBean.setNppIds(nppId);
			}
		}*/
		
		String[] khIds = request.getParameterValues("khIds");
		if(khIds != null)
		{
			String khId = "";
			for(int i = 0; i < khIds.length; i++)
			{
				khId += khIds[i] + ",";
			}
			
			if(khId.trim().length() > 0)
			{
				khId = khId.substring(0, khId.length() - 1);
				tctskuBean.setKhIds(khId);
			}
		}
		String[] kh_ckuhs = request.getParameterValues("kh_ckuh");
		if(kh_ckuhs != null)
		{
			String kh_ckuh = "";
			for(int i = 0; i < kh_ckuhs.length; i++)
			{
				kh_ckuh += kh_ckuhs[i] + ",";
			}
			
			if(kh_ckuh.trim().length() > 0)
			{
				kh_ckuh = kh_ckuh.substring(0, kh_ckuh.length() - 1);
				tctskuBean.setKh_ckuh(kh_ckuh);
			}
		}
		
		String[] kh_QuyIds = request.getParameterValues("kh_QuyIds");
		if(kh_QuyIds != null && (thang.equals("3")|| thang.equals("6") || thang.equals("9") || thang.equals("12") ) )
		{
			System.out.println("vao day rui "+ thang);
			String kh_QuyId = "";
			for(int i = 0; i < kh_QuyIds.length; i++)
			{
				kh_QuyId += kh_QuyIds[i] + ",";
			}
			
			if(kh_QuyId.trim().length() > 0)
			{
				kh_QuyId = kh_QuyId.substring(0, kh_QuyId.length() - 1);
				tctskuBean.setKh_QuyIds(kh_QuyId);
			}
		}
		
		//VUNG 
		String vungId = request.getParameter("vungId");
		if(vungId == null)
			vungId = "";
		tctskuBean.setVungIds(vungId);
		
		String kvId = request.getParameter("khuvucId");
		if(kvId == null)
			kvId = "";
		tctskuBean.setKhuvucIds(kvId);
		
		String duyetdunggia = request.getParameter("duyetdunggia");
		if(duyetdunggia == null)
			duyetdunggia = "0";
		tctskuBean.setDuyetdunggia(duyetdunggia);
		
		String khongtinhthuve = request.getParameter("khongtinhthuve");
		if(khongtinhthuve == null)
			khongtinhthuve = "1";
		tctskuBean.setKhongtinhtienthuve(khongtinhthuve);
		
		String khongtinhdoanhso = request.getParameter("khongtinhdoanhso");
		if(khongtinhdoanhso == null)
			khongtinhdoanhso = "";
		tctskuBean.setKhongtinhdoanhso(khongtinhdoanhso);
		
 		String action = request.getParameter("action");
 	
 		System.out.println("Action la: " + action);
 
 		String contentType = request.getContentType();
			if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0))
			{
				System.out.println("da vao upload file excell");
				
				IUpload obj =null;
				OutputStream out = response.getOutputStream();
				obj = new Upload();
				MultipartRequest multi = new MultipartRequest(request, UPLOAD_DIRECTORY, 20000000, "UTF-8");
				userId = util.antiSQLInspection(multi.getParameter("userId"));
				IUpload dmhBean = new Upload();
				dmhBean.setUserId(userId);
				dmhBean.setThang(multi.getParameter("thang"));
				dmhBean.setNam(multi.getParameter("nam"));
				Enumeration files = multi.getFileNames();
				String filename = "";
				System.out.println("__userId"+userId);
				while (files.hasMoreElements())
				{
					String name = (String) files.nextElement();
					filename = multi.getFilesystemName(name);
					System.out.println("File  " + UPLOAD_DIRECTORY + filename);
				}
				ArrayList<String> arr=new ArrayList<String>();
				if (filename != null && filename.length() > 0)
				{
					arr=this.readExcel(UPLOAD_DIRECTORY + filename, dmhBean);	
				}
		
				ArrayList<String> ckquy=arr;
				for(int i=0;i<ckquy.size();i++)
				{
					System.out.println("ck la "+ ckquy.get(i));
				}
				/*for(int i=0;i<arr.size();i++)
				{
					//System.out.println(arr.size()+"------"+i);
					if(arr.size()-1==i)
					{
						String[] part=arr.get(i).split("_");
						makh+="'"+part[0]+"'";
						ckquy[i]=part[1];
						System.out.println("1.-"+arr.size());
					}
					else
					{
				//	makh+="'"+arr.get(i)+"'"+",";
					String[] part=arr.get(i).split("_");
					makh+="'"+part[0]+"'"+",";;
					ckquy[i]=part[1];
					System.out.println("2.-"+arr.size());
					}
				}*/
				
				
				obj = new Upload();
				obj.setUserId(userId);
				if (dmhBean.getMsg().trim().length() <= 0 && filename != null)
				{
					obj.setMsg("Đọc file thành công ");
				} else if (filename == null)
				{
					obj.setMsg("Vui lòng chọn file ");
				} else
				{
					obj.setMsg(dmhBean.getMsg());
				}
				
				
				
				
				 id = util.antiSQLInspection(multi.getParameter("id"));
			    if(id == null){  	
			    	tctskuBean = new DuyetbandunggiaNpp("");
			    }else{
			    	tctskuBean = new DuyetbandunggiaNpp(id);
			    }
				tctskuBean.setCkquy(ckquy);
				//System.out.println("list makh: "+makh);
				tctskuBean.setCkquy(ckquy);
				 userId = util.antiSQLInspection(multi.getParameter("userId"));
				tctskuBean.setUserId(userId);
				
				 diengiai = util.antiSQLInspection(multi.getParameter("diengiai"));
				if (diengiai == null)
					diengiai = "";
				tctskuBean.setDiengiai(diengiai);
				
				 thang = util.antiSQLInspection(multi.getParameter("thang"));
				if (thang == null)
					thang = Integer.toString(Integer.parseInt(getDateTime().split("-")[1]));
				tctskuBean.setThang(thang);
				
				 loaict = util.antiSQLInspection(multi.getParameter("loaict"));
				if (loaict == null)
					loaict = "0";
				tctskuBean.setLoaict(loaict);
				
				 quy = util.antiSQLInspection(multi.getParameter("quy"));
				if (quy == null)
					quy = getQuyHienTai();
				tctskuBean.setQuy(quy);
				
				 nam = util.antiSQLInspection(multi.getParameter("nam"));
				if (nam == null)
					nam = "";
				tctskuBean.setNam(nam);
				
				 nppId = util.antiSQLInspection(multi.getParameter("nppId"));
				if (nppId == null)
					nppId = "";
				tctskuBean.setNppId(nppId);
				
				/*String[] nppIds = request.getParameterValues("nppIds");
				if(nppIds != null)
				{
					String nppId = "";
					for(int i = 0; i < nppIds.length; i++)
					{
						nppId += nppIds[i] + ",";
					}
					
					if(nppId.trim().length() > 0)
					{
						nppId = nppId.substring(0, nppId.length() - 1);
						tctskuBean.setNppIds(nppId);
					}
				}*/
				
				khIds = multi.getParameterValues("khIds");
				if(khIds != null)
				{
					String khId = "";
					for(int i = 0; i < khIds.length; i++)
					{
						khId += khIds[i] + ",";
					}
					
					if(khId.trim().length() > 0)
					{
						khId = khId.substring(0, khId.length() - 1);
						tctskuBean.setKhIds(khId);
					}
				}
				
				kh_ckuhs = request.getParameterValues("kh_ckuh");
				if(kh_ckuhs != null)
				{
					String kh_ckuh = "";
					for(int i = 0; i < kh_ckuhs.length; i++)
					{
						kh_ckuh += kh_ckuhs[i] + ",";
					}
					
					if(kh_ckuh.trim().length() > 0)
					{
						kh_ckuh = kh_ckuh.substring(0, kh_ckuh.length() - 1);
						tctskuBean.setKh_ckuh(kh_ckuh);
					}
				}
				
				 kh_QuyIds = multi.getParameterValues("kh_QuyIds");
				if(kh_QuyIds != null && (thang.equals("3")|| thang.equals("6") || !thang.equals("9") || !thang.equals("12") ) )
				{
					String kh_QuyId = "";
					for(int i = 0; i < kh_QuyIds.length; i++)
					{
						kh_QuyId += kh_QuyIds[i] + ",";
					}
					
					if(kh_QuyId.trim().length() > 0)
					{
						kh_QuyId = kh_QuyId.substring(0, kh_QuyId.length() - 1);
						tctskuBean.setKh_QuyIds(kh_QuyId);
					}
				}
				
				//VUNG 
				 vungId = multi.getParameter("vungId");
				if(vungId == null)
					vungId = "";
				tctskuBean.setVungIds(vungId);
				
				 kvId = multi.getParameter("khuvucId");
				if(kvId == null)
					kvId = "";
				tctskuBean.setKhuvucIds(kvId);
				
				 duyetdunggia = multi.getParameter("duyetdunggia");
				if(duyetdunggia == null)
					duyetdunggia = "0";
				tctskuBean.setDuyetdunggia(duyetdunggia);
				
				 khongtinhthuve = multi.getParameter("khongtinhthuve");
				if(khongtinhthuve == null)
					khongtinhthuve = "1";
				tctskuBean.setKhongtinhtienthuve(khongtinhthuve);
				
				 khongtinhdoanhso = multi.getParameter("khongtinhdoanhso");
				if(khongtinhdoanhso == null)
					khongtinhdoanhso = "";
				tctskuBean.setKhongtinhdoanhso(khongtinhdoanhso);
				
		 		 action = multi.getParameter("action");
		 	
		 		System.out.println("Action la: " + action);
				
				tctskuBean.setUserId(userId);	
		    	tctskuBean.createRs();
		    	session.setAttribute("userId", userId);
				session.setAttribute("tctskuBean", tctskuBean);
				if(id==null)
				{
				String nextJSP = "/TraphacoHYERP/pages/Distributor/DuyetBanDungGiaNppNew.jsp";
				response.sendRedirect(nextJSP);
				
				}
				else
				{
					String nextJSP = "/TraphacoHYERP/pages/Distributor/DuyetBanDungGiaNppUpdate.jsp";
    				response.sendRedirect(nextJSP);
				}
				return;
			}
 		
 		if(action.equals("save"))
 		{    
    		if (id == null )
    		{
    			if (!tctskuBean.createTctSKU())
    			{
    		    	tctskuBean.setUserId(userId);
    		    	
    		    	tctskuBean.createRs();
    		    	session.setAttribute("userId", userId);
    				session.setAttribute("tctskuBean", tctskuBean);
    				
    				String nextJSP = "/TraphacoHYERP/pages/Distributor/DuyetBanDungGiaNppNew.jsp";
    				response.sendRedirect(nextJSP);
    			}
    			else
    			{
    				IDuyetbandunggiaNppList obj = new DuyetbandunggiaNppList();
				    obj.setUserId(userId);
				    
				    obj.init("");
					session.setAttribute("obj", obj);
				    
				    String nextJSP = "/TraphacoHYERP/pages/Distributor/DuyetBanDungGiaNpp.jsp";
					response.sendRedirect(nextJSP);
    			}	
    		}
    		else
    		{
    			if (!(tctskuBean.updateTctSKU()))
    			{			
    		    	tctskuBean.setUserId(userId);
    		    	tctskuBean.createRs();
    		    	session.setAttribute("userId", userId);
    				session.setAttribute("tctskuBean", tctskuBean);
    				
    				String nextJSP = "/TraphacoHYERP/pages/Distributor/DuyetBanDungGiaNppUpdate.jsp";
    				response.sendRedirect(nextJSP);
    			}
				else
				{
					IDuyetbandunggiaNppList obj = new DuyetbandunggiaNppList();
				    obj.setUserId(userId);
				    
				    obj.init("");
					session.setAttribute("obj", obj);
				    
				    String nextJSP = "/TraphacoHYERP/pages/Distributor/DuyetBanDungGiaNpp.jsp";
					response.sendRedirect(nextJSP);
				}
    		}
	    }
 		else if(action.equals("export")){
 			ResultSet ex = tctskuBean.getExportRs();
 			if(ex != null)
    			ExportEx(response, ex, thang);
 		}
		else
		{		
			this.out = response.getWriter();
			tctskuBean.createRs();
			session.setAttribute("userId", userId);
			session.setAttribute("tctskuBean", tctskuBean);
			
			String nextJSP;
			if (id == null){
				nextJSP = "/TraphacoHYERP/pages/Distributor/DuyetBanDungGiaNppNew.jsp";
			}
			else{
				nextJSP = "/TraphacoHYERP/pages/Distributor/DuyetBanDungGiaNppUpdate.jsp";   						
			}
			response.sendRedirect(nextJSP);
		}
 	
 		
 		
 		
	}
	
	private ArrayList<String> readExcel(String fileName, IUpload upload)
	{
		dbutils db = new dbutils();
		ArrayList<String> arr=new ArrayList<String>();
		try 
		{
			InputStream ExcelFileToRead = new FileInputStream(fileName);
			XSSFWorkbook wb = new XSSFWorkbook(ExcelFileToRead);
			int sosheet=wb.getNumberOfSheets();
			for(int ii=0;ii<sosheet;ii++)
			{
				XSSFSheet sheet = wb.getSheetAt(ii);
				Row row;
				Row rowFixed;
				Iterator rows = sheet.rowIterator();
				int rowIndex=0;
				rowFixed=sheet.getRow(0);
			//	int socot = rowFixed.getLastCellNum()+2;
			
				System.out.println("[rows][soNpp]"+rowFixed.getLastCellNum());
				while (rows.hasNext()) 
				{
					row =(Row)rows.next();
					if(rowIndex>0)
					{
				
						String MaHd=getContent(row,0);
						String Makh=getContent(row,1);
						String Tenkh=getContent(row,2);
						String thoathang=getContent(row, 3);
						String thoaQuy=getContent(row, 4);
						String thoackuh=getContent(row, 5);
						if(MaHd.trim().length()<=0)
							MaHd="0";
						if(Makh.length()<=0)
							Makh="0";
						if(thoathang.length()<=0)
							thoathang="0";
						if(Tenkh.length()<=0)
							Tenkh="0";
						if(thoaQuy.length()<=0)
							thoaQuy="0";
						if(thoackuh.length()<=0)
							thoackuh="0";
						String node=MaHd+"_"+Makh+"_"+Tenkh;
						arr.add(Makh+"_"+thoaQuy+"_"+thoathang+"_"+thoackuh);
						System.out.println("[Makh]"+Makh+"[thoaqy]"+thoaQuy+"[thoathang]"+thoathang);
					}
					rowIndex++;
				}
			}
			upload.Stock();
		//return upload.getMsg();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return arr;
	}
	
	private static String getContent(org.apache.poi.ss.usermodel.Row row, int column)
	{
		org.apache.poi.ss.usermodel.Cell cell = row.getCell(column,Row.CREATE_NULL_AS_BLANK);
		return cell.toString();
	}
	
	private void ExportEx(HttpServletResponse response, ResultSet ex, String thang) throws IOException {
		System.out.print("\nEXPORT DUYET BAN DUNG GIA");
		//this.out = null;
		try
		{
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=DSKHKhongDuyetBanDungGia.xls");
			WritableWorkbook w = jxl.Workbook.createWorkbook(response.getOutputStream());

			int k = 0;
			int j = 4;

			WritableSheet sheet = null;

			WritableFont cellTitle = new WritableFont(WritableFont.TIMES, 15);
			cellTitle.setColour(Colour.BLACK);
			cellTitle.setBoldStyle(WritableFont.BOLD);
			
			WritableCellFormat celltieude = new WritableCellFormat(cellTitle);
			celltieude.setAlignment(Alignment.CENTRE);
			
			sheet = w.createSheet("DANHSACHKH", k);//ten sheet
			
			WritableFont cellFont = new WritableFont(WritableFont.TIMES, 13);
			cellFont.setColour(Colour.BLACK);
			

			NumberFormat dp3 = new NumberFormat("#,###,###,##");
			
			WritableCellFormat inFormat = new WritableCellFormat(dp3);
			inFormat.setFont(cellFont);
		
			inFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			inFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			inFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			inFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
			
			
			//WritableCellFormat cellFormatTD = new WritableCellFormat(cellFont);
			sheet.addCell(new Label(0, 0, "DANH SÁCH KHÁCH HÀNG KHÔNG DUYỆT CHIẾT KHẤU THÁNG", celltieude));	
			sheet.mergeCells(0, 0, 5, 0);
			sheet.addCell(new Label(0, 1, "THÁNG "+ thang, celltieude));	
			sheet.mergeCells(0, 1, 5, 1);
			
			WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
			cellFormat.setAlignment(Alignment.CENTRE);
			cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
			cellFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
			
			sheet.addCell(new Label(0, 3, "STT", cellFormat));
			sheet.addCell(new Label(1, 3, "Mã FAST", cellFormat));
			sheet.addCell(new Label(2, 3, "KHÁCH HÀNG", cellFormat));
			sheet.addCell(new Label(3, 3, "ĐỊA CHỈ", cellFormat));
			sheet.addCell(new Label(4, 3, "LOẠI KH", cellFormat));

			sheet.addCell(new Label(5, 3, "DOANH THU THÁNG " + thang, cellFormat));
			
			sheet.setColumnView(2, 50);
			sheet.setColumnView(3, 70);
			
			WritableCellFormat cformat = new WritableCellFormat(cellFont);
			cformat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cformat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cformat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cformat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
			
			Label label;
			while(ex.next()){
				label = new Label(0, j, ex.getString("row"), cformat);
				sheet.addCell(label);
				label = new Label(1, j, ex.getString("maFAST"), cformat);
				sheet.addCell(label);
				label = new Label(2, j, ex.getString("TEN"), cformat);
				sheet.addCell(label);
				label = new Label(3, j, ex.getString("DIACHI"), cformat);
				sheet.addCell(label);
				label = new Label(4, j, ex.getString("Ma"), cformat);
				sheet.addCell(label);
				Number number = new Number(5, j, ex.getDouble("DoanhSo"), inFormat);
				sheet.addCell(number);
				//label = new Label(5, j, ex.getString("DoanhSo"), cformat);
				//sheet.addCell(label);
				j ++;
			}
			w.write();
			w.close();
			ex.close();
		}
		catch (Exception e)
		{
			System.out.println("Lỗi : " + e.getMessage());
			e.printStackTrace();
		} finally
		{
			//if (out != null)
				//out.close();

		}
	}

	public String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	private String getQuyHienTai() 
	{
        int thang = Integer.parseInt(this.getDateTime().split("-")[1]);
        if(1 <= thang && thang <= 3)
        	return "1";
        else if(4 <= thang && thang <= 6)
        	return "2";
        else if(7 <= thang && thang <= 9)
        	return "3";
        else 
        	return "4";
	}

}
