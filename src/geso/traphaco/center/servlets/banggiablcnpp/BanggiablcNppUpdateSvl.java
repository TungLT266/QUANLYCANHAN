package geso.traphaco.center.servlets.banggiablcnpp;

import geso.traphaco.center.util.Utility;
import geso.traphaco.center.beans.banggiablcnpp.IBanggiablcNpp;
import geso.traphaco.center.beans.banggiablcnpp.IBanggiablcNppList;
import geso.traphaco.center.beans.banggiablcnpp.imp.BanggiablcNpp;
import geso.traphaco.center.beans.banggiablcnpp.imp.BanggiablcNppList;
import geso.traphaco.distributor.db.sql.dbutils;

import java.io.PrintWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.CellView;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class BanggiablcNppUpdateSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet 
{
   static final long serialVersionUID = 1L;
   
	public BanggiablcNppUpdateSvl() {
		super();
	}   	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		
		PrintWriter out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String id = util.getId(querystring);  	
	    out.println(id);
	    
	    IBanggiablcNpp bgBean = new BanggiablcNpp();
	    
        bgBean.setUserId(userId);
        bgBean.setId(id);
        bgBean.init();
        String nextJSP = "/TraphacoHYERP/pages/Center/BanggiablcNppUpdate.jsp";

        if(querystring.contains("update")){

            session.setAttribute("bgBean", bgBean);
        	response.sendRedirect(nextJSP);
        }
        else if(querystring.contains("copy")){
        	bgBean.setId("");
            session.setAttribute("bgBean", bgBean);
        	nextJSP = "/TraphacoHYERP/pages/Center/BanggiablcNppNew.jsp";
        	response.sendRedirect(nextJSP);
        }
	}  	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		IBanggiablcNpp bgBean = (IBanggiablcNpp) new BanggiablcNpp();
	    Utility util = new Utility();
	    
		String id =  util.antiSQLInspection(request.getParameter("id"));
	    if(id == null) 	
	    	id = "";
	    bgBean.setId(id);
	    
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		bgBean.setUserId(userId);

    	String bgTen = util.antiSQLInspection(request.getParameter("ten"));
		if (bgTen == null)
			bgTen = "";				
    	bgBean.setTen(bgTen);

		String dvkdId = util.antiSQLInspection(request.getParameter("dvkdId"));
		if(dvkdId == null)
			dvkdId = "";	
		bgBean.setDvkdId(dvkdId);
	    
		String bgId = util.antiSQLInspection(request.getParameter("bgId"));
		if(bgId == null)
			bgId = "";
		bgBean.setBanggiaId(bgId);
		
		String tungay = util.antiSQLInspection(request.getParameter("tungay"));
		if(tungay == null)
			tungay = "";
		bgBean.setTungay(tungay);
		
		String denngay = util.antiSQLInspection(request.getParameter("denngay"));
		if(denngay == null)
			denngay = "";
		bgBean.setDenngay(denngay);

		String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
    	if (trangthai == null)
    		trangthai = "0";
    	else
    		trangthai = "1";
    	bgBean.setTrangthai(trangthai);
    	
    	String ck = util.antiSQLInspection(request.getParameter("chietkhau"));
    	if (ck == null)
    		ck = "0";
    	bgBean.setCk(ck);
    	
    	String[] nppIds = request.getParameterValues("nppIds");
    	if(nppIds != null)
    	{
    		String nppId = "";
    		for(int i = 0; i < nppIds.length; i++)
    			nppId += nppIds[i] + ",";
    		
    		if(nppId.trim().length() > 0)
    		{
    			nppId = nppId.substring(0, nppId.length() - 1);
    			bgBean.setNppIds(nppId);
    		}
    	}
    	
    	String[] spIds = request.getParameterValues("spIds");
    	String[] ptchietkhau = request.getParameterValues("ptchietkhau");
    	String[] dongiaSAUCK = request.getParameterValues("dongiaSAUCK");
    	String[] dongiaTruocCK = request.getParameterValues("dongiaGOC");
    	
    	if(spIds != null )
    	{
    		Hashtable<String, String> sanphamCK = new Hashtable<String, String>();
    		Hashtable<String, String> sanphamDG_TruocCK = new Hashtable<String, String>();
    		Hashtable<String, String> sanphamDG_SauCK = new Hashtable<String, String>();
    		for(int i = 0; i < spIds.length; i++)
    		{
    			sanphamCK.put(spIds[i], ptchietkhau[i]);
    			sanphamDG_TruocCK.put(spIds[i], dongiaTruocCK[i]);
    			sanphamDG_SauCK.put(spIds[i], dongiaSAUCK[i]);
    		}
    		bgBean.setSanphamCK(sanphamCK);
    		bgBean.setSanphamDG_TruocCK(sanphamDG_TruocCK);
    		bgBean.setSanphamDG_SauCK(sanphamDG_SauCK);
    	}
		   	
		String action = request.getParameter("action");
		if(action.equals("save"))
		{
			if (id.length()==0)
			{
				if (!(bgBean.CreateBg()))
				{			
					bgBean.setUserId(userId);
					bgBean.createRS();
					session.setAttribute("bgBean", bgBean);
					String nextJSP = "/TraphacoHYERP/pages/Center/BanggiablcNppNew.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					IBanggiablcNppList obj = new BanggiablcNppList();
					obj.setUserId(userId);
					obj.init("");
					session.setAttribute("obj", obj);
						
					String nextJSP = "/TraphacoHYERP/pages/Center/BanggiablcNpp.jsp";
					response.sendRedirect(nextJSP);			    			    									
				}	
			}
			else
			{
				if (!(bgBean.UpdateBg()))
				{								
					bgBean.setUserId(userId);
					bgBean.setId(id);
					bgBean.init();
					session.setAttribute("bgBean", bgBean);
					String nextJSP = "/TraphacoHYERP/pages/Center/BanggiablcNppUpdate.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					IBanggiablcNppList obj = new BanggiablcNppList();
					obj.setUserId(userId);
					obj.init("");
					session.setAttribute("obj", obj);
					String nextJSP = "/TraphacoHYERP/pages/Center/BanggiablcNpp.jsp";
					response.sendRedirect(nextJSP);			    			    									
				}
			}
		}
		else if( action.equals("Excel") )
		{
	    	this.XuatExcel(response, "100000");
		}
		else
		{	
			String nextJSP;
			if (id.length()==0)
			{			
				bgBean.setUserId(userId);
				bgBean.createRS();
				session.setAttribute("bgBean", bgBean);
				nextJSP = "/TraphacoHYERP/pages/Center/BanggiablcNppNew.jsp";
			}
			else
			{
				bgBean.setUserId(userId);
				bgBean.setId(id);
				bgBean.init();
				session.setAttribute("bgBean", bgBean);

				nextJSP = "/TraphacoHYERP/pages/Center/BanggiablcNppUpdate.jsp";   						
			}
			response.sendRedirect(nextJSP);
		}
	}

	private void XuatExcel(HttpServletResponse response, String id) 
	{
		try
		{
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=BangGiaBan.xls");
			
			WritableWorkbook w = jxl.Workbook.createWorkbook(response.getOutputStream());
			WritableSheet sheet = w.createSheet("Đơn hàng ETC", 0);
			
			dbutils db = new dbutils();
			
			String tenbanggia = "";
			String dvkd = "";
			String nhomkenh = "";
			
			String query =   "select a.TEN, b.DONVIKINHDOANH, c.DIENGIAI as nhomkenh from BANGGIAMUA a inner join DONVIKINHDOANH b on a.DVKD_FK = b.PK_SEQ  "+
							 "	inner join NHOMKENH c on a.NHOMKENH_FK = c.PK_SEQ "+
							 "where a.PK_SEQ = '" + id + "' ";
			ResultSet rs = db.get(query);
			if( rs.next() )
			{
				tenbanggia = rs.getString("TEN");
				dvkd = rs.getString("DONVIKINHDOANH");
				nhomkenh = rs.getString("nhomkenh");
			}
			rs.close();
			
			sheet.addCell(new Label(0, 0, tenbanggia.toUpperCase()));
			
			sheet.addCell(new Label(0, 1, "Đơn vị kinh doanh: "));
			sheet.addCell(new Label(1, 1, dvkd));

			sheet.addCell(new Label(0, 2, "Nhóm kênh: "));
			sheet.addCell(new Label(1, 2, nhomkenh));

			WritableFont cellFont = new WritableFont(WritableFont.TIMES, 12);
			cellFont.setColour(Colour.BLACK);

			WritableCellFormat cellFormat = new WritableCellFormat(cellFont);

			cellFormat.setBackground(jxl.format.Colour.GRAY_25);
			cellFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);

			sheet.addCell(new Label(0, 4, "Mã sản phẩm", cellFormat));
			sheet.addCell(new Label(1, 4, "Tên sản phẩm", cellFormat));
			sheet.addCell(new Label(2, 4, "Đơn vị", cellFormat));
			sheet.addCell(new Label(3, 4, "Giá bán", cellFormat));
			
			// set style to cell data
			WritableCellFormat cellFormat2 = new WritableCellFormat();

			cellFormat2.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
			
			query =  "select b.MA_FAST, b.TEN, c.DONVI, a.GIAMUA  "+
					 "from BANGGIAMUA_SANPHAM a inner join SANPHAM b on a.SANPHAM_FK = b.PK_SEQ  "+
					 "		inner join DONVIDOLUONG c on b.DVDL_FK = c.PK_SEQ "+
					 "where a.BANGGIAMUA_FK = '" + id + "' "+
					 "order by b.TEN asc ";
			rs = db.get(query);
			
			int j = 5;
			while (rs.next())
			{
				Label label;

				label = new Label(0, j, rs.getString("MA_FAST"), cellFormat2);
				sheet.addCell(label);

				label = new Label(1, j, rs.getString("TEN"), cellFormat2);
				sheet.addCell(label);

				label = new Label(2, j, rs.getString("DONVI"), cellFormat2);
				sheet.addCell(label);

				label = new Label(3, j, rs.getString("GIAMUA"), cellFormat2);
				sheet.addCell(label);

				j++;	
			}
			
			for(int x = 0; x <= 12; x++)
			{
			    CellView cell = sheet.getColumnView(x);
			    cell.setAutosize(true);
			    sheet.setColumnView(x, cell);
			}
			
			db.shutDown();
			w.write();
			w.close();
		} 
		catch (Exception e)
		{
			System.out.println("Error Cac Ban : " + e.getMessage());
			e.printStackTrace();
		}
	}
	

}