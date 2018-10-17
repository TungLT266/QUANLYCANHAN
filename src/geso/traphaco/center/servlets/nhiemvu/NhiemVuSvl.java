package geso.traphaco.center.servlets.nhiemvu;

import geso.traphaco.center.beans.nhiemvu.*;
import geso.traphaco.center.beans.nhiemvu.imp.*;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

import java.io.IOException;
import java.io.OutputStream;
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

import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class NhiemVuSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	PrintWriter out;
	
    public NhiemVuSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out = response.getWriter();
	    	    
	    HttpSession session = request.getSession();

	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    System.out.println(action);
	    
	    String nvId = util.getId(querystring);
	    System.out.println("[NhiemVuSvl.doGet] nvId = " + nvId); 

	    if (action.equals("delete")) {
	    	Delete(nvId);
	    	out.print(nvId);
	    }
	    System.out.println("[NhiemVuSvl.doGet] ...");
	
	    INhiemVuList obj = new NhiemVuList();
	    obj.setRequestObj(request);
	    obj.setUserId(userId);
	    
	    obj.init("");
		session.setAttribute("obj", obj);
				
		String nextJSP = "/TraphacoHYERP/pages/Center/NhiemVu.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		INhiemVuList obj = new NhiemVuList();
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	   
	    
	    Utility util = new Utility();
	    
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    System.out.println("[NhiemVuSvl.doPost] action = " + action); 
	    obj.setRequestObj(request);
	    
	    if (action.equals("Tao moi"))
	    {
	    	INhiemVu bean = (INhiemVu) new NhiemVu("");
	    	bean.setUserId(userId);
	    	//bean.createRS();
	    	session.setAttribute("nvBean", bean);
    		String nextJSP = "/TraphacoHYERP/pages/Center/NhiemVuNew.jsp";
    		response.sendRedirect(nextJSP);
	    } else if (action.equals("excel"))
 		{
	    	obj = new NhiemVuList();
	    	obj.setRequestObj(request);
	    	String search = getSearchQuery(request,userId, obj);
 			ToExcel(response, search);
 		}
	    else 
	    {
	    	obj = new NhiemVuList();
	    	obj.setRequestObj(request);
	    	String search = getSearchQuery(request,userId, obj);
	    	obj.init(search);
			obj.setUserId(userId);
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
    		response.sendRedirect("/TraphacoHYERP/pages/Center/NhiemVu.jsp");    	
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request,String userId, INhiemVuList obj)
	{				
		
		Utility util = new Utility();
		
		String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
    	if ( diengiai == null) { diengiai = ""; }
    	obj.setDienGiai(diengiai);
    	
    	String tieuchi = util.antiSQLInspection(request.getParameter("tieuchi"));
    	if (tieuchi == null) { tieuchi = ""; }    	
    	obj.setTieuChi(tieuchi);
    	
    	String istudong = util.antiSQLInspection(request.getParameter("istudong"));
    	if (istudong == null) { istudong = ""; }    	
    	obj.setIsTuDong(istudong.equals("1"));
    	
    	String loaitieuchi = util.antiSQLInspection(request.getParameter("loaitieuchi"));
    	if (loaitieuchi == null) { loaitieuchi = ""; }    	
    	obj.setLoaiTieuChi(loaitieuchi);
    	
    	String doituong = util.antiSQLInspection(request.getParameter("doituong"));
    	if (doituong == null) { doituong = ""; }    	
    	obj.setDoiTuong(doituong);
    	
    	
    	Utility Ult = new Utility();
    	String query = "select ROW_NUMBER() OVER(ORDER BY a.pk_seq DESC) AS stt, a.PK_SEQ as id, a.tieuchi, a.diengiai, a.loaitieuchi, a.doituong, a.istudong, a.trangthai, a.ngaytao, b.ten as nguoitao, a.ngaysua, c.ten as nguoisua";
		query = query + " from NHIEMVU a inner join NHANVIEN b on a.NGUOITAO = b.PK_SEQ inner join NHANVIEN c on a.NGUOISUA = c.PK_SEQ where a.pk_seq > 0 ";
    	
    	if (tieuchi.length()>0){
			query = query + " and upper(dbo.ftBoDau(a.tieuchi)) like upper(N'%" + util.replaceAEIOU(tieuchi) + "%') ";			
    	}
		
    	if (diengiai.length()>0){
			query = query + " and upper(dbo.ftBoDau(a.diengiai)) like upper(N'%" + util.replaceAEIOU(diengiai) + "%') or upper(a.pk_seq) like upper('%" + util.replaceAEIOU(diengiai) + "%')";			
    	}
    	
    	if (istudong.length()>0){
			query = query + " and a.istudong = '"+ (obj.getIsTuDong() ? "1" : "0") +"') ";			
    	}
    	
    	if (loaitieuchi.length()>0){
			query = query + " and a.loaitieuchi = '" + loaitieuchi + " '";			
    	}
    	
    	if (doituong.length()>0){
			query = query + " and a.doituong = '" + doituong + " '";			
    	}
    	
    	out.println("[NhiemVuSvl.getSearchQuery] query = " + query);

    	return query;
	}

	
	private void Delete(String nvId) 
	{
		System.out.println("[NhiemVuSvl.Delete] begin...");
		INhiemVuList obj = new NhiemVuList();
		
		dbutils db = new dbutils();
    		
		try {
			db.getConnection().setAutoCommit(false);
			
			String str1 = "delete from nhiemvu where pk_seq='" + nvId + "'";
			
			if(!db.update(str1))
			{
				System.out.println("[NhiemVuSvl.Delete] Khong the xoa nhiem vu!");
				obj.setMessage("Không thể xóa nhiệm vụ này.");
				db.update("rollback");
				db.shutDown();
				return;
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
			
			System.out.println("[NhiemVuSvl.Delete] xoa nhiem vu ok!");
		}
		catch(Exception ex)
		{
			db.update("rollback");
			System.out.println("Khong the xoa nhiem vu!");
			obj.setMessage("Không thể xóa nhiệm vụ này (ex: " + ex.getMessage() + ")");
		}
	}
	
	private void ToExcel(HttpServletResponse response, String query) throws IOException
	{
		OutputStream out = null;
		try
		{
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=NhiemVu.xls");
			WritableWorkbook w = jxl.Workbook.createWorkbook(response.getOutputStream());

			int k = 0;
			int j = 5;

			WritableSheet sheet = null;

			WritableFont cellTitle = new WritableFont(WritableFont.TIMES, 14);
			cellTitle.setColour(Colour.BLACK);
			cellTitle.setBoldStyle(WritableFont.BOLD);

			sheet = w.createSheet("NhiemVu", k);
			sheet.addCell(new Label(0, 1, "Nhiệm vụ nhân viên : ", new WritableCellFormat(cellTitle)));

			sheet.addCell(new Label(0, 2, "Ngày tạo: "));
			sheet.addCell(new Label(1, 2, "" + getDateTime()));

			sheet.addCell(new Label(2, 4, "Đơn vị tiền tệ:VND"));

			WritableFont cellFont = new WritableFont(WritableFont.TIMES, 12);
			cellFont.setColour(Colour.BLACK);

			WritableCellFormat cellFormat = new WritableCellFormat(cellFont);

			cellFormat.setBackground(jxl.format.Colour.LIME);
			cellFormat.setWrap(true);
			cellFormat.setAlignment(Alignment.CENTRE);
			cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
			cellFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);

			WritableCellFormat cellFormatSpecical = new WritableCellFormat(cellFont);

			cellFormatSpecical.setBackground(jxl.format.Colour.LIGHT_ORANGE);
			cellFormatSpecical.setWrap(true);
			cellFormatSpecical.setAlignment(Alignment.CENTRE);
			cellFormatSpecical.setVerticalAlignment(VerticalAlignment.CENTRE);
			cellFormatSpecical.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormatSpecical.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormatSpecical.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormatSpecical.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);

			sheet.addCell(new Label(0, 4, "STT", cellFormat));
			sheet.addCell(new Label(1, 4, "MÃ NHIỆM VỤ", cellFormatSpecical));
			sheet.addCell(new Label(2, 4, "TIÊU CHÍ", cellFormatSpecical));
			sheet.addCell(new Label(3, 4, "DIỄN GIẢI", cellFormat));
			sheet.addCell(new Label(4, 4, "ĐỐI TƯỢNG", cellFormat));
			sheet.addCell(new Label(5, 4, "TỰ ĐỘNG", cellFormat));
			sheet.addCell(new Label(6, 4, "LOẠI TIÊU CHÍ", cellFormat));
			sheet.addCell(new Label(7, 4, "TRẠNG THÁI ", cellFormat));
			sheet.addCell(new Label(8, 4, "NGÀY TẠO", cellFormat));
			sheet.addCell(new Label(9, 4, "NGƯỜI TẠO", cellFormat));
			sheet.addCell(new Label(10, 4, "NGÀY SỬA", cellFormat));
			sheet.addCell(new Label(11, 4, "NGƯỜI SỬA", cellFormat));

			sheet.setRowView(100, 4);

			sheet.setColumnView(0, 10);
			sheet.setColumnView(1, 20);
			sheet.setColumnView(2, 30);
			sheet.setColumnView(3, 25);
			sheet.setColumnView(4, 20);
			sheet.setColumnView(5, 20);
			sheet.setColumnView(6, 15);
			sheet.setColumnView(7, 35);
			sheet.setColumnView(8, 15);
			sheet.setColumnView(9, 15);
			sheet.setColumnView(10, 15);
			sheet.setColumnView(11, 15);
			sheet.setColumnView(12, 15);
			sheet.setColumnView(13, 15);
			sheet.setColumnView(14, 60);
			dbutils db = new dbutils();

			ResultSet rs = db.get(query);

			WritableCellFormat cellFormat2 = new WritableCellFormat(new jxl.write.NumberFormat("#,###,###"));

			cellFormat2.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);

			WritableCellFormat cellFormat3 = new WritableCellFormat(new jxl.write.NumberFormat("#,###,###"));
			cellFormat3.setBackground(jxl.format.Colour.YELLOW);
			cellFormat3.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat3.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat3.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat3.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);

			WritableCellFormat cformat = null;
			Label label;
			Number number;
			int stt=0;
			while (rs.next())
			{
		
				cformat = cellFormat2 ;
				stt++;
				String ltc = rs.getString("loaitieuchi")==null?"":rs.getString("loaitieuchi");
				String dt=rs.getString("doituong");
            	if(dt.equals("SR")) {
            		ltc = ltc.equals("0") ? "Viếng thăm" : ltc.equals("1") ? "6 đh/ngày" : ltc.equals("2") ? "Mở 5 điểm bán 1 tháng" : ltc.equals("3") ? "Bán Ra cao hơn tháng trước" : "";
            	} else if(dt.equals("SS")) {
            		ltc = ltc.equals("0") ? "Báo cáo" : ltc.equals("1") ? "Giải phóng hàng cận date" : ltc.equals("2") ? "Hoàn thành chỉ tiêu SKU In" : ltc.equals("3") ? "Chỉ tiêu trọng tâm tháng" : ltc.equals("4") ? "Bán Ra cao hơn tháng trước" : ltc.equals("5") ? "Mua Vào cao hơn tháng trước" : "";
            	} else if(dt.equals("ASM")) {
            		ltc = ltc.equals("0") ? "Báo cáo" : ltc.equals("1") ? "Giải phóng hàng cận date" : ltc.equals("2") ? "Hỗ trợ" : ltc.equals("3") ? "Hoàn thành chỉ tiêu SKU In" : ltc.equals("4") ? "Mở thêm nhà phân phối" : ltc.equals("5") ? "Bán Ra cao hơn tháng trước" : ltc.equals("6") ? "Mua Vào cao hơn tháng trước" : "";
            	} else {
            		ltc = "";
            	}
				
				number = new Number(0, j, stt, cformat);sheet.addCell(number);
				label = new Label(1, j, rs.getString("id"), cformat);sheet.addCell(label);
				label = new Label(2, j, rs.getString("tieuchi"), cformat);sheet.addCell(label);
				label = new Label(3, j, rs.getString("diengiai"), cformat);sheet.addCell(label);
				label = new Label(4, j, rs.getString("doituong"), cformat);sheet.addCell(label);
				label = new Label(5, j, rs.getInt("istudong") == 0 ? "Không" :"Có", cformat);sheet.addCell(label);
				label = new Label(6, j,ltc, cformat);sheet.addCell(label);
				label = new Label(7, j, rs.getInt("trangthai") == 0 ? "Chờ xử lý" :rs.getInt("trangthai") == 1? "Đã chốt":"Đã hủy", cformat);sheet.addCell(label);
				label = new Label(8, j, rs.getString("ngaytao"), cformat);sheet.addCell(label);
				label = new Label(9, j, rs.getString("NguoiTao"), cformat);sheet.addCell(label);
				label = new Label(10, j, rs.getString("NgaySua"), cformat);sheet.addCell(label);
				label = new Label(11, j, rs.getString("NguoiSua"), cformat);sheet.addCell(label);

				j++;
			}
			w.write();
			w.close();
			rs.close();
			db.shutDown();
		} catch (Exception e)
		{
			System.out.println("Error Cac Ban : " + e.getMessage());
			e.printStackTrace();
		} finally
		{
			if (out != null)
				out.close();

		}
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	

}
