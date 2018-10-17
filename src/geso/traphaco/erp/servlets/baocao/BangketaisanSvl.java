package geso.traphaco.erp.servlets.baocao;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.bangketaisan.IBangketaisan;
import geso.traphaco.erp.beans.bangketaisan.imp.Bangketaisan;
import geso.traphaco.erp.db.sql.dbutils;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

import com.aspose.cells.BorderLineType;
import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.Color;
import com.aspose.cells.Style;


public class BangketaisanSvl extends HttpServlet 
{
	
	private static final long serialVersionUID = 1L;

	public BangketaisanSvl() {
        super();
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		Utility Ult = new Utility();
		HttpSession session = request.getSession();

		String querystring = request.getQueryString();
		
		String userId = Ult.getUserId(querystring);
		
		IBangketaisan obj = new Bangketaisan() ;
		obj.setuserId(userId);
		//obj.init();
		
		String ctyId = (String)session.getAttribute("congtyId");
		if(ctyId == null) ctyId = "100005";
		obj.setCtyId(ctyId);
		
		obj.createRs();
		
		session.setAttribute("obj", obj);

		String nextJSP = "/TraphacoHYERP/pages/Erp/Bangketaisan.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utility util=new Utility();
		   
		HttpSession session = request.getSession();
		
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		Utility cutil = (Utility) session.getAttribute("util");
		

		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}else{
		
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");

			OutputStream out = response.getOutputStream();
			IBangketaisan obj = new Bangketaisan();
		
			obj.setuserId(userId);		
				
			String tungay = util.antiSQLInspection(request.getParameter("tungay"));
			if (tungay == null)
				tungay = "";
			obj.setTungay(tungay);

			String denngay = util.antiSQLInspection(request.getParameter("denngay"));
			if (denngay == null)
				denngay = "";			
			obj.setDenngay(denngay);
			
			String loaits = util.antiSQLInspection(request.getParameter("loaits"));
			if (loaits == null)
				loaits = "";
			obj.setLoaitsId(loaits);
			
			String ttcp = util.antiSQLInspection(request.getParameter("ttcp"));
			if (ttcp == null)
				ttcp = "";
			obj.setTTCpId(ttcp);
			
			String action = request.getParameter("action");
		
			String nextJSP = "/TraphacoHYERP/pages/Erp/Soquytienmat.jsp";

			if (action.equals("tao")) {
				try {					
					ToExcel(response, obj);
					obj.DBClose();

				} catch (Exception ex) {
					obj.setMsg(ex.getMessage());
					session.setAttribute("obj", obj);
					response.sendRedirect(nextJSP);
					return;
				}
			}
		
			session.setAttribute("obj", obj);
			response.sendRedirect(nextJSP);
		}
	}

	private void ToExcel(HttpServletResponse response, IBangketaisan obj)throws Exception {
		OutputStream out = null;
		try{
			dbutils db = new dbutils();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=BangKeTaiSan.xls");
			WritableWorkbook w = jxl.Workbook.createWorkbook(response.getOutputStream());
			
			
			WritableSheet sheet = null;
			sheet = w.createSheet("Sheet1", 0);//ten sheet
			
			
			WritableFont cellTitle = new WritableFont(WritableFont.TIMES, 15); //định dạng cho tiêu đề
			cellTitle.setColour(Colour.BLACK);
			cellTitle.setBoldStyle(WritableFont.BOLD);
			
			WritableCellFormat celltieude = new WritableCellFormat(cellTitle); //tiêu đề
			celltieude.setAlignment(Alignment.CENTRE);
			
			WritableFont cellFontWhite = new WritableFont(WritableFont.TIMES, 13); //màu chữ tiêu đề
			cellFontWhite.setColour(Colour.WHITE);
			
			sheet.addCell(new Label(0, 1 , getDate())); 	
			
			sheet.addCell(new Label(0, 3, "BẢNG KÊ TÀI SẢN ", celltieude));	
			sheet.mergeCells(0, 3, 6, 3);// bắt đầu từ cột thứ 0, dòng thứ mấy , 7 cột để merger, 1 dòng để merger
			
			//ĐỊNH DẠNG TIÊU ĐỀ CỘT
			WritableCellFormat cellFormat = new WritableCellFormat(cellFontWhite);

			cellFormat.setBackground(jxl.format.Colour.GRAY_80);
			cellFormat.setWrap(true);
			cellFormat.setAlignment(Alignment.CENTRE);
			cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
			cellFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.WHITE);
			cellFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.WHITE);
			cellFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.WHITE);
			cellFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.WHITE);
			
			//TIÊU ĐỀ CỘT
			sheet.addCell(new Label(0, 5, "STT", cellFormat));			
			sheet.addCell(new Label(1, 5, "MÃ TÀI SẢN", cellFormat));			
			sheet.addCell(new Label(2, 5, "TÊN TÀI SẢN", cellFormat));
			sheet.addCell(new Label(3, 5,"NGÀY NHẬN", cellFormat));
			sheet.addCell(new Label(4, 5, "PHÒNG BAN SỞ HỮU", cellFormat));
			sheet.addCell(new Label(5, 5, "ĐVT", cellFormat));
			sheet.addCell(new Label(6, 5, "SỐ LƯỢNG", cellFormat));
			
			WritableFont cellFont = new WritableFont(WritableFont.TIMES, 13);// màu chữ cho báo cáoss
			cellFont.setColour(Colour.BLACK);	
			
			//ĐỘ RỘNG CỘT
			sheet.setColumnView(0, 10);
			sheet.setColumnView(1, 15);
			sheet.setColumnView(2, 30);
			sheet.setColumnView(3, 20);
			sheet.setColumnView(4, 25);
			sheet.setColumnView(5, 10);
			sheet.setColumnView(6, 20);
					
			
			//ĐỊNH DẠNG CHO BÁO CÁO
			WritableCellFormat cformat = new WritableCellFormat(cellFont);
			
			WritableCellFormat cformat3 = new WritableCellFormat(cellFont);
			cformat3.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cformat3.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cformat3.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cformat3.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
			
			Number number;
			Label label;
			
			NumberFormat dp3 = new NumberFormat("#,###,###");
			
			WritableCellFormat inFormat = new WritableCellFormat(dp3);
			inFormat.setFont(cellFont);
		
			inFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			inFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			inFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			inFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
			
			ResultSet rs = db.get(obj.init());
			int stt = 0;
			
			int j=6; 
			
			if(rs!=null)
			{
				while (rs.next())
				{
					stt++;	
					number = new Number(0, j, stt, cformat); sheet.addCell(number);
					
					label = new Label(1, j, rs.getString("MA"), cformat3); sheet.addCell(label);
					
					label = new Label(2, j, rs.getString("TEN"), cformat3); sheet.addCell(label);
					
					label = new Label(3, j, rs.getString("NGAYNHAN"), cformat3); sheet.addCell(label);
					
					label = new Label(4, j, rs.getString("TENTTCP"), cformat3); sheet.addCell(label);
					
					label = new Label(5, j, rs.getString("DONVI"), cformat3); sheet.addCell(label);
					
					number = new Number(6, j, rs.getDouble("SOLUONG"), inFormat);sheet.addCell(number);
					j++;
				}
			}
			
			if(stt==0){
				System.out.println("KHÔNG CÓ DỮ LIỆU TRONG THỜI GIAN NÀY");
				obj.setMsg("KHÔNG CÓ DỮ LIỆU TRONG THỜI GIAN NÀY");
			}
			
			w.write();
			w.close();
			rs.close();
			db.shutDown();
		}
		catch (Exception e)
		{
			System.out.println("Lỗi : " + e.getMessage());
			e.printStackTrace();
		} finally
		{
			if (out != null)
				out.close();

		}
		
		
	}

		private String getDate() 
		{
	        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	        Date date = new Date();
	        return dateFormat.format(date);	
		}
		
		 public Cell CreateBorderSetting(Cell cell) throws IOException
		    {
			
		        Style style;
		        style = cell.getStyle();

		        //Set border color
		        style.setBorderColor(BorderType.TOP, Color.BLACK);
		        style.setBorderColor(BorderType.BOTTOM, Color.BLACK);
		        style.setBorderColor(BorderType.LEFT, Color.BLACK);
		        style.setBorderColor(BorderType.RIGHT, Color.BLACK);

		        //Set the cell border type
		        style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
		        style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
		        style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
		        style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);

		        cell.setStyle(style);
		        return cell;
		    }

		 public Cell CreateBorderSetting2(Cell cell) throws IOException
		    {
			
		        Style style;
		        style = cell.getStyle();

		        //Set border color
		        style.setBorderColor(BorderType.TOP, Color.BLACK);
		        style.setBorderColor(BorderType.BOTTOM, Color.BLACK);
		        style.setBorderColor(BorderType.LEFT, Color.BLACK);
		        style.setBorderColor(BorderType.RIGHT, Color.BLACK);

		        //Set the cell border type
		        style.setBorderLine(BorderType.TOP, BorderLineType.DOTTED);
		        style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);
		        style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
		        style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);

		        cell.setStyle(style);
		        return cell;
		    }

}
