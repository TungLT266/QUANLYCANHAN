package geso.traphaco.center.servlets.tonkhostore;

import geso.traphaco.center.beans.upload.IUpload;
import geso.traphaco.center.beans.upload.imp.Upload;
import geso.traphaco.center.db.sql.dbutils;

import geso.traphaco.distributor.util.Utility;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;


@WebServlet("/TonKhoStoreSvl")
public class TonKhoStoreSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public TonKhoStoreSvl()
	{
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IUpload obj = new Upload();

		String querystring = request.getQueryString();
		Utility util = new Utility();
		String userId = util.getUserId(querystring);
		obj.setUserId(userId);

		String id = util.getId(querystring);
		obj.setId(id);
		
		String action = util.getAction(querystring);
		if (action.equals("excel"))
		{
			obj.setMsg(Excel(response,obj));
		}
		obj.createRs_Tonkho();
		session.setAttribute("obj", obj);
		String nextJSP = "/TraphacoHYERP/pages/Center/TonKhoStore.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IUpload obj = null;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		Utility util = new Utility();
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		if (userId == null)
		{
			userId = "";
		}
		System.out.println("[TonKhoStoreSvl.doPost] userId = " + userId);

		String action = request.getParameter("action");
		if (action == null)
		{
			action = "";
		}
		System.out.println("[TonKhoStoreSvl.doPost] action = " + action);
		obj = new Upload();
		String thang = request.getParameter("thang");
		if (thang == null)
			thang = "";
		obj.setThang(thang);

		String nam = request.getParameter("nam");
		if (nam == null)
			nam = "";
		obj.setNam(nam);

		if (action.equals("Tao moi"))
		{
			IUpload bean = (IUpload) new Upload("");
			bean.setUserId(userId);
			session.setAttribute("spnppStr", "");
			session.setAttribute("uploadBean", bean);
			String nextJSP = "/TraphacoHYERP/pages/Center/TonKhoStoreNew.jsp";
			response.sendRedirect(nextJSP);
			System.out.print("page___"+nextJSP);
		} else
		{
			obj.createRs_Tonkho();
			session.setAttribute("userId", userId);
			session.setAttribute("obj", obj);
			String nextJSP = "/TraphacoHYERP/pages/Center/TonKhoStore.jsp";
			response.sendRedirect(nextJSP);
		}
	}
	
	private String Excel(HttpServletResponse response,IUpload  obj) throws IOException 
	{
		OutputStream out = null;
		try {
		   response.setContentType("application/vnd.ms-excel");
		   response.setHeader
		     ("Content-Disposition", "attachment; filename=TonKho_" +getDateTime()+".xls");
		   WritableWorkbook w = 
			jxl.Workbook.createWorkbook(response.getOutputStream());
		   WritableSheet sheet = w.createSheet("TonKho", 0);
		   dbutils db=new dbutils();
		   String sql=
		  " select   a.PK_SEQ as TonKhoId,d.MA as spMa,d.pk_seq as spId,d.TEN as spTen,c.pk_seq as nppId,c.MA as nppMa,c.TEN as nppTen,a.ThoiGian,b.TonDau,b.Nhap,b.Ban,b.TonCuoi \n"+ 
		  " from TonKho a inner join TonKho_NPP b on b.TonKho_FK=a.PK_SEQ \n"+
		  " inner join NHAPHANPHOI c  on c.PK_SEQ=b.NPP_FK and c.pk_seq in(select npp_fk from phamvihoatdong where nhanvien_fk='"+obj.getUserId()+"') \n"+
		  " inner join SANPHAM d on d.PK_SEQ=b.SanPham_FK \n"+
		  " where a.PK_SEQ='"+obj.getId()+"' \n"+
		  " order by d.pk_seq,c.pk_seq " ; 
		  System.out.println("1.Khoi tao upload : "+sql);
		  
		   	WritableFont cellFont = new WritableFont(WritableFont.ARIAL, 10);
		    cellFont.setColour(Colour.BLACK);		
		    cellFont.setBoldStyle(jxl.write.WritableFont.BOLD);
		    WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
		    cellFormat.setBackground(Colour.WHITE);
		    cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
		    
		    WritableFont fBold = new WritableFont(WritableFont.ARIAL, 10);
		    fBold.setColour(Colour.RED);
		    fBold.setBoldStyle(jxl.write.WritableFont.BOLD);
		    WritableCellFormat fmBold = new WritableCellFormat(fBold);
		    fmBold.setBorder(Border.ALL, BorderLineStyle.THIN);
		    
		    
		   sheet.addCell(new Label(0, 1, "STT",cellFormat));
		   sheet.addCell(new Label(1, 1, "Mã Sản phẩm",cellFormat));
		   sheet.addCell(new Label(2, 1, "Tên sản phẩm",cellFormat));
		   		 
		   ResultSet rs=db.get(sql);
		   Label label ;
		   jxl.write.Number number;
		   int row=1;
		   int col=0;
		   int stt=0;
		   String nppIdPrev="";
		   String spPrev="";
		   if(rs!=null)
		   {
			   while (rs.next())
			   {
				   String nppId=rs.getString("nppId");
				   String nppMa=rs.getString("nppMa");
				   String nppTen=rs.getString("nppTen");
				   String spId=rs.getString("spId");
				   String spMa=rs.getString("spMa");
				   String spTen=rs.getString("spTen");
				   
				   float tondau=rs.getFloat("tondau");
				   float nhap=rs.getFloat("Nhap");
				   float ban=rs.getFloat("ban");
				   float toncuoi=rs.getFloat("toncuoi");
				   if(!spPrev.equals(spId))
				   {
					   col=0;
					   row++;
					   stt++;
					   spPrev=spId;			
				   }
				   number = new jxl.write.Number(0, row, stt); sheet.addCell(number);
				   sheet.addCell(new Label(1, row, spMa));
				   sheet.addCell(new Label(2, row, spTen));
				   number = new jxl.write.Number(3+col*4, row, tondau); sheet.addCell(number);
				   number = new jxl.write.Number(4+col*4, row, nhap); sheet.addCell(number);
				   number = new jxl.write.Number(5+col*4, row, ban); sheet.addCell(number);
				   number = new jxl.write.Number(6+col*4, row, toncuoi); sheet.addCell(number);
				   if(!nppIdPrev.equals(nppId))
				   {
					   nppIdPrev=nppId;
					   sheet.addCell(new Label(3+col*4, 0, nppMa,fmBold));
					   sheet.addCell(new Label(4+col*4, 0, nppTen,fmBold));
					   sheet.addCell(new Label(3+col*4, 1, "Tồn đầu",format(cellFont,Colour.BLACK,Colour.WHITE,Border.ALL,BorderLineStyle.THIN)));
					   sheet.addCell(new Label(4+col*4, 1, "Nhập ",format(cellFont,Colour.BLACK,Colour.WHITE,Border.ALL,BorderLineStyle.THIN)));
					   sheet.addCell(new Label(5+col*4, 1, "Bán",format(cellFont,Colour.BLACK,Colour.WHITE,Border.ALL,BorderLineStyle.THIN)));
					   sheet.addCell(new Label(6+col*4, 1, "Tồn cuối",format(cellFont,Colour.BLACK,Colour.WHITE,Border.ALL,BorderLineStyle.THIN)));
					   col++;
				   }
				}
		   }
		  // sheetAutoFitColumns(sheet);
			   w.write();
			   w.close();
		   }
		  catch (Exception e) 
		  {
			  e.printStackTrace();
			  return "Không có báo cáo trong thời gian đã chọn "+e.getMessage();
		  }
		return "";
	}
	
	private WritableCellFormat format(WritableFont font,Colour color,Colour background,Border boder,BorderLineStyle lineStyle)
	{
		WritableFont cellFont = new WritableFont(font);
		WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
	    try
		{
			cellFont.setColour(color);
			cellFormat = new WritableCellFormat(cellFont);
		    cellFormat.setBackground(background);
		    cellFormat.setBorder(boder, lineStyle);
		} catch (WriteException e)
		{
			e.printStackTrace();
		}		    
	    return cellFormat;
	    
	}
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	
	
}
