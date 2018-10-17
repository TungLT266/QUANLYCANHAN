package geso.traphaco.distributor.servlets.xuatkho;

import geso.traphaco.distributor.beans.xuatkho.IErpImportdonhangETCList;
import geso.traphaco.distributor.beans.xuatkho.imp.ErpImportdonhangETCList;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Cell;
import jxl.CellView;
import jxl.Sheet;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ErpUngkhuyenmaiTDVSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpUngkhuyenmaiTDVSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpImportdonhangETCList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    HttpSession session = request.getSession();	    
	    String npp_duocchon_id = session.getAttribute("npp_duocchon_id") == null ? "" : session.getAttribute("npp_duocchon_id").toString();
	    String tdv_dangnhap_id = session.getAttribute("tdv_dangnhap_id") == null ? "" : session.getAttribute("tdv_dangnhap_id").toString();
	    
	    Utility util = new Utility();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    
    	String lsxId = util.getId(querystring);
	    obj = new ErpImportdonhangETCList();
	    
	    String nppId = request.getParameter("nppId");
	    if(nppId == null)
	    	nppId = "";
	    obj.setNppId(nppId);
	    
	    obj.setUserId(userId);
	    obj.setTdv_dangnhap_id(tdv_dangnhap_id);
		obj.setNpp_duocchon_id(npp_duocchon_id);
	    obj.init("");
	    
		session.setAttribute("obj", obj);
			
		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpUngKhuyenMaiTDV.jsp";
		response.sendRedirect(nextJSP);
	    
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    String action = request.getParameter("action");
	    if (action == null)
	    {
	    	action = "";
	    }
	    
		IErpImportdonhangETCList obj = new ErpImportdonhangETCList();
		
		String loaidonhang = request.getParameter("loaidonhang");
	    if(loaidonhang == null)
	    	loaidonhang = "";
		obj.setLoaidonhang(loaidonhang);
	 
	    Utility util = new Utility();
	    
		HttpSession session = request.getSession();
		String npp_duocchon_id = session.getAttribute("npp_duocchon_id") == null ? "" : session.getAttribute("npp_duocchon_id").toString();
	    String tdv_dangnhap_id = session.getAttribute("tdv_dangnhap_id") == null ? "" : session.getAttribute("tdv_dangnhap_id").toString();
	    String userId = util.antiSQLInspection(request.getParameter("userId")); 
	    
	    obj.setTdv_dangnhap_id(tdv_dangnhap_id);
		obj.setNpp_duocchon_id(npp_duocchon_id);
	    obj.setUserId(userId);
	    
	    String nppId = request.getParameter("nppId");
	    if(nppId == null)
	    	nppId = "";
	    obj.setNppId(nppId);
	    
	    String khId = request.getParameter("khId");
	    if(khId == null)
	    	khId = "";
	    obj.setNppTen(khId);
	    
	    String tungay = request.getParameter("tungay");
	    if(tungay == null)
	    	tungay = "";
	    obj.setTungay(tungay);
	    
	    String denngay = request.getParameter("denngay");
	    if(denngay == null)
	    	denngay = "";
	    obj.setDenngay(denngay);
	    
	    System.out.println("---NPP ID: " + nppId);
	    
		String condition = "";
		if( tungay.trim().length() > 0 )
			condition += " and a.NgayDonHang >= '" + tungay + "'  ";
		if( denngay.trim().length() > 0 )
			condition += " and a.NgayDonHang <= '" + denngay + "' ";
		
    	String query =   "select ddkd.maFAST, ddkd.TEN, XNT.scheme, sp.MA as spMa, sp.TEN as spTEN,  "+
		    			 "	cast( isnull(XNT.tongnhap, 0) as numeric(18, 0) ) as tongnhap, cast(  isnull(XNT.tongxuat, 0) as numeric(18, 0) ) as tongxuat, cast( ( isnull(XNT.tongnhap, 0) - isnull(XNT.tongxuat, 0) ) as numeric(18, 0) ) as toncuoi "+
		    			 "from DAIDIENKINHDOANH ddkd "+
		    			 "left join "+
		    			 "( "+
		    			 "	select isnull( NHAP.TDV_FK, XUAT.TDV_FK ) as TDV, ISNULL( NHAP.SCHEME, XUAT.SCHEME ) as scheme, ISNULL( NHAP.SANPHAM_FK, XUAT.SANPHAM_FK ) as SANPHAM_FK,  "+
		    			 "		ISNULL(nhap.tongnhap, 0) as tongnhap, ISNULL(xuat.tongxuat, 0) as tongxuat   "+
		    			 "	from "+
		    			 "	( "+
		    			 "		select a.DDKD_NHAN_FK as TDV_FK, b.SANPHAM_FK, c.SCHEME, SUM( b.SOLUONGYEUCAU ) as tongnhap  "+
		    			 "		from  ERP_YEUCAUCHUYENKHO a inner join ERP_YEUCAUCHUYENKHO_SANPHAM b on a.PK_SEQ = b.YEUCAUCHUYENKHO_FK "+
		    			 "				inner join CTKHUYENMAI c on b.CTKM_FK = c.PK_SEQ "+
		    			 "		where NOIDUNGXUAT_FK = '100006' and DDKD_NHAN_FK is not null and a.trangthai in ( 3, 4 )  "+
		    			 "		group by a.DDKD_NHAN_FK, b.SANPHAM_FK, c.SCHEME "+
		    			 "	) "+
		    			 "	NHAP full outer join "+
		    			 "	( "+
		    			 "		select a.DDKD_FK as TDV_FK, d.PK_SEQ as SANPHAM_FK, c.SCHEME, SUM( b.SOLUONG ) as tongxuat "+
		    			 "		from  DONHANG a inner join DONHANG_CTKM_TRAKM b on a.PK_SEQ = b.DONHANGID "+
		    			 "				inner join CTKHUYENMAI c on b.CTKMID = c.PK_SEQ "+
		    			 "				inner join SANPHAM d on b.SPMA = d.MA "+
		    			 "		where a.TRANGTHAI != '2' "+
		    			 "		group by a.DDKD_FK, d.PK_SEQ, c.SCHEME "+
		    			 "	) "+
		    			 "	XUAT on NHAP.TDV_FK = XUAT.TDV_FK and NHAP.SANPHAM_FK = XUAT.SANPHAM_FK "+
		    			 ") "+
		    			 "XNT on ddkd.PK_SEQ = XNT.TDV inner join SANPHAM sp on XNT.SANPHAM_FK = sp.PK_SEQ ";
    	System.out.println("::: LAY BC: " + query );
    	
    	this.XuatExcel(response, query, tungay, denngay);
		    
	}
	
	private void XuatExcel(HttpServletResponse response, String query, String tungay, String denngay) throws IOException
	{
		dbutils db = new dbutils();
		try
		{
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=UngKhuyenMaiTDV.xls");
			
			WritableWorkbook w = jxl.Workbook.createWorkbook(response.getOutputStream());
			WritableSheet sheet = w.createSheet("ỨNG KHUYẾN MẠI TDV", 0);
			
			sheet.addCell(new Label(0, 0, "UngKhuyenMai"));
			
			sheet.addCell(new Label(0, 1, "Từ ngày: "));
			sheet.addCell(new Label(1, 1, tungay));

			sheet.addCell(new Label(0, 2, "Đến ngày: "));
			sheet.addCell(new Label(1, 2, denngay));

			WritableFont cellFont = new WritableFont(WritableFont.TIMES, 12);
			cellFont.setColour(Colour.BLACK);

			WritableCellFormat cellFormat = new WritableCellFormat(cellFont);

			cellFormat.setBackground(jxl.format.Colour.GRAY_25);
			cellFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);

			sheet.addCell(new Label(0, 4, "Mã TDV", cellFormat));
			sheet.addCell(new Label(1, 4, "Tên TDV", cellFormat));
			sheet.addCell(new Label(2, 4, "Scheme", cellFormat));
			sheet.addCell(new Label(3, 4, "Mã sản phẩm", cellFormat));
			sheet.addCell(new Label(4, 4, "Tên sản phẩm", cellFormat));
			sheet.addCell(new Label(5, 4, "Tổng nhập", cellFormat));
			sheet.addCell(new Label(6, 4, "Tổng xuất", cellFormat));
			sheet.addCell(new Label(7, 4, "Tồn kho", cellFormat));
			
			// set style to cell data
			WritableCellFormat cellFormat2 = new WritableCellFormat();

			cellFormat2.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
			
			
			ResultSet rs = db.get(query);
			
			int j = 5;
			while (rs.next())
			{
				Label label;

				label = new Label(0, j, rs.getString("maFAST"), cellFormat2);
				sheet.addCell(label);

				label = new Label(1, j, rs.getString("TEN"), cellFormat2);
				sheet.addCell(label);

				label = new Label(2, j, rs.getString("SCHEME"), cellFormat2);
				sheet.addCell(label);

				label = new Label(3, j, rs.getString("spMa"), cellFormat2);
				sheet.addCell(label);

				label = new Label(4, j, rs.getString("spTen"), cellFormat2);
				sheet.addCell(label);

				label = new Label(5, j, rs.getString("tongnhap"), cellFormat2);
				sheet.addCell(label);

				label = new Label(6, j, rs.getString("tongxuat"), cellFormat2);
				sheet.addCell(label);

				label = new Label(7, j, rs.getString("toncuoi"), cellFormat2);
				sheet.addCell(label);
				
				j++;	
			}
			
			for(int x = 0; x <= 7; x++)
			{
			    CellView cell = sheet.getColumnView(x);
			    cell.setAutosize(true);
			    sheet.setColumnView(x, cell);
			}
			
			w.write();
			w.close();
		} 
		catch (Exception e)
		{
			System.out.println("Error Cac Ban : " + e.getMessage());
			e.printStackTrace();
		}
		finally
		{
			db.shutDown();
		}
	}
	
	public String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	
}
