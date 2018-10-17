package geso.traphaco.erp.servlets.thutien;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.GiuDieuKienLoc;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.thutien.IErpThutien;
import geso.traphaco.erp.beans.thutien.IErpThutienList;
import geso.traphaco.erp.beans.thutien.imp.ErpThutien;
import geso.traphaco.erp.beans.thutien.imp.ErpThutienList;

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
import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;


public class ErpThutienSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpThutienSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpThutienList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    
	    String tthdId = util.getId(querystring);
	    
	   
	    
	    
	    if (action.equals("delete"))
	    {	
	    	IErpThutien tthd = new ErpThutien(tthdId) ;
    		tthd.setCtyId(ctyId);
    		tthd.setnppdangnhap(util.getIdNhapp(userId));
    		
		    obj = new ErpThutienList();
		    obj.setCtyId(ctyId);
		    obj.setnppdangnhap(util.getIdNhapp(userId));
		    obj.setUserId(userId);
		    
		    obj.setLoainhanvien(session.getAttribute("loainhanvien"));
		    obj.setDoituongId(session.getAttribute("doituongId"));
    		
    		String searchQuery=util.getSearchFromHM(userId,this.getServletName(), session);
    		GiuDieuKienLoc.setParamsToOject(obj, searchQuery);
    		
    		if(!tthd.Delete(userId))
    		{
    			obj.setmsg(tthd.getMsg());
    		}	    	
    		
    		
    		obj.init("");
		    
			session.setAttribute("obj", obj);
					
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpThuTien.jsp";
			response.sendRedirect(nextJSP);
			return;
    		
	    }
	    else if(action.equals("chot"))
	    {
	    		IErpThutien tthd = new ErpThutien(tthdId) ;
	    		tthd.setCtyId(ctyId);
	    		tthd.setnppdangnhap(util.getIdNhapp(userId));
	    		
	    		obj = new ErpThutienList();
	 		    obj.setCtyId(ctyId);
	 		    obj.setnppdangnhap(util.getIdNhapp(userId));
	 		    obj.setUserId(userId);
	 		    
	 		    obj.setLoainhanvien(session.getAttribute("loainhanvien"));
	 		    obj.setDoituongId(session.getAttribute("doituongId"));
	    		
	    		String searchQuery=util.getSearchFromHM(userId,this.getServletName(), session);
	    		GiuDieuKienLoc.setParamsToOject(obj, searchQuery);
	    		
	    		if(!tthd.chotTTHD(userId))
	    		{
	    			obj.setmsg(tthd.getMsg());
	    		}	    	
	    		
	    		
	    		obj.init("");
    		    
    			session.setAttribute("obj", obj);
    					
    			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpThuTien.jsp";
    			response.sendRedirect(nextJSP);
    			return;
	    					
	    }    else if(action.equals("unchot"))
	    {
    		IErpThutien tthd = new ErpThutien(tthdId) ;
    		tthd.setCtyId(ctyId);
    		tthd.setnppdangnhap(util.getIdNhapp(userId));
    		System.out.println("1.2");
    		
    		obj = new ErpThutienList();
 		    obj.setCtyId(ctyId);
 		    obj.setnppdangnhap(util.getIdNhapp(userId));
 		    obj.setUserId(userId);
 		    
 		    obj.setLoainhanvien(session.getAttribute("loainhanvien"));
 		    obj.setDoituongId(session.getAttribute("doituongId"));
 		    
    		String searchQuery=util.getSearchFromHM(userId,this.getServletName(), session);
    		GiuDieuKienLoc.setParamsToOject(obj, searchQuery);   
    		
    		if(!tthd.BochotTTHD(userId))
    		{
    			obj.setmsg(tthd.getMsg());
    		}	  
    		   obj.init("");
    			session.setAttribute("obj", obj);
    			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpThuTien.jsp";
    			response.sendRedirect(nextJSP);
    			return;
    }

	    else{
	    
	    obj = new ErpThutienList();
	    obj.setCtyId(ctyId);
	    obj.setnppdangnhap(util.getIdNhapp(userId));
	    obj.setUserId(userId);
	    
	    obj.setLoainhanvien(session.getAttribute("loainhanvien"));
	    obj.setDoituongId(session.getAttribute("doituongId"));
	    obj.init("");

	    util.setSearchToHM(userId, session, this.getServletName(), "");

		session.setAttribute("obj", obj);
				
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpThuTien.jsp";
		response.sendRedirect(nextJSP);
	    return;
	    }
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpThutienList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    OutputStream out = response.getOutputStream();
	    
	    String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }	    
	    
	    Utility util = new Utility();
	    
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(request.getParameter("userId")); 
	    String ctyId = (String)session.getAttribute("congtyId");
	   
	    
	    String chungtu=util.antiSQLInspection(request.getParameter("chungtu"));
		if (chungtu == null)
			chungtu = "";
		
		String trangthaiphieu=util.antiSQLInspection(request.getParameter("trangthai"));
		if (trangthaiphieu == null)
			trangthaiphieu = "";	
		
	    if(action.equals("Tao moi"))
	    {
	    	IErpThutien tthdBean = new ErpThutien();
	    	tthdBean.setCtyId(ctyId);
	    	tthdBean.setUserId(userId);
	    	tthdBean.setnppdangnhap(util.getIdNhapp(userId));
	    	tthdBean.setLoainhanvien(session.getAttribute("loainhanvien"));
	    	tthdBean.setDoituongIddn(session.getAttribute("doituongId"));
	    	tthdBean.taoMoiSoChungTu();
	    	tthdBean.createRs();
	      	session.setAttribute("queryNpp", tthdBean.getQueryNpp());
	    	session.setAttribute("tthdBean", tthdBean);

    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpThuTienNew.jsp";
    		response.sendRedirect(nextJSP);
    		return;
	    }
	    else
	    {
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
	    		obj = new ErpThutienList();
	    		obj.setCtyId(ctyId);
	    		obj.setnppdangnhap(util.getIdNhapp(userId));
	    		obj.setLoainhanvien(session.getAttribute("loainhanvien"));
	    		obj.setDoituongId(session.getAttribute("doituongId"));
		    	
		    	 this.getSearchQuery(request, obj);
		    	
		    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
		    	obj.setUserId(userId);
		    	obj.init("");
		    	
		    	String querySearch = GiuDieuKienLoc.createParams(obj);
				util.setSearchToHM(userId, session,this.getServletName(), querySearch);
				
		    	
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpThuTien.jsp");	
		    	return;
		    }
	    	else if (action.equals("excel"))
	 	    {
	    		obj = (IErpThutienList) new ErpThutienList();	 	    	
	 		    obj.setCtyId(ctyId);	 
	 		    obj.setnppdangnhap(util.getIdNhapp(userId));
	 		    obj.setLoainhanvien(session.getAttribute("loainhanvien"));
	    		obj.setDoituongId(session.getAttribute("doituongId"));
	    		
	 		   // String search = getSearchQuery(request, obj);
	    		
	    		try
				{
					//response.setContentType("application/vnd.ms-excel");
					//response.setHeader("Content-Disposition", "attachment; filename=DanhsachKhachhang.xls");

					response.setContentType("application/xlsm");
					response.setHeader("Content-Disposition", "attachment; filename=BCThuTien.xlsm");
			        
			        Workbook workbook = new Workbook();
			    	
					FileInputStream fstream = null;
					String chuoi = getServletContext().getInitParameter("path") + "\\BCThuTien.xlsm";
					
					fstream = new FileInputStream(chuoi);		
					workbook.open(fstream);
					workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
					
					String querythutien = getQueryExcelThuTien(request, "", "", obj); 
	 				String querythutienhds = getQueryExcelThuTienHoaDon(request, "", "", obj); 
					
					CreateStaticData(workbook, querythutien, querythutienhds, obj );
					workbook.save(out);
					
					fstream.close();
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
				}

				obj.setUserId(userId);


				session.setAttribute("obj", obj);

				session.setAttribute("userId", userId);
				return;	 	    		
	 	    }
	    	 else if(action.equals("chot"))
	 	    {	    		
	    			
	 	    		IErpThutien tthd = new ErpThutien(chungtu) ;
	 	    		
	 	    		obj = new ErpThutienList();
 	    		    obj.setUserId(userId);
 	    		    obj.setCtyId(ctyId);
 	    		    obj.setnppdangnhap(util.getIdNhapp(userId));
 	    		    obj.setLoainhanvien(session.getAttribute("loainhanvien"));
 		    		obj.setDoituongId(session.getAttribute("doituongId"));
	 				
	 	    	
	 	    		
	 	    		String searchQuery=util.getSearchFromHM(userId,this.getServletName(), session);
	 	    		geso.traphaco.center.util.GiuDieuKienLoc.setParamsToOject(obj, searchQuery);
	 	    		
	 	    		if(!tthd.chotTTHD(userId))
	 	    		{
	 	    			obj.setmsg(tthd.getMsg());
	 	    		}
	 	    		
 	    		    obj.init("");
 	    		     	    		   
 	    			session.setAttribute("obj", obj);
 	    					
 	    			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpThuTien.jsp";
 	    			response.sendRedirect(nextJSP);  
 	    			return;
	 	    	
	 	    }
	    	 else if (action.equals("delete"))
	 	    {		 	    		
	    		 IErpThutien tthd = new ErpThutien(chungtu) ;
	 	    		
	 	    		obj = new ErpThutienList();
	    		    obj.setUserId(userId);
	    		    obj.setCtyId(ctyId);
	    		    obj.setnppdangnhap(util.getIdNhapp(userId));
	    		    obj.setLoainhanvien(session.getAttribute("loainhanvien"));
 		    		obj.setDoituongId(session.getAttribute("doituongId"));
	 				
	 	    	
	 	      		String searchQuery=util.getSearchFromHM(userId,this.getServletName(), session);
	 	    		geso.traphaco.center.util.GiuDieuKienLoc.setParamsToOject(obj, searchQuery);
	 	    		
	 	    		if(!tthd.Delete(userId))
	 	    		{
	 	    			obj.setmsg(tthd.getMsg());
	 	    		}
	    		    obj.init("");
	    		     	    		   
	    			session.setAttribute("obj", obj);
	    					
	    			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpThuTien.jsp";
	    			response.sendRedirect(nextJSP); 
	    			return;
	 	    }
	    	else
	    	{
		    	obj = new ErpThutienList();
		    	obj.setsochungtu(chungtu);
		    	obj.setTrangthai(trangthaiphieu);
		    	System.out.println("trangthaiphieu: " + trangthaiphieu);
		    	obj.setCtyId(ctyId);
		    	obj.setUserId(userId);
				obj.setnppdangnhap(util.getIdNhapp(userId));
				obj.setLoainhanvien(session.getAttribute("loainhanvien"));
		    	obj.setDoituongId(session.getAttribute("doituongId"));
		    	
		 		 System.out.println("1.1" + obj.getTusotien());
		    	this.getSearchQuery(request, obj);
//		   		String searchQuery=util.getSearchFromHM(userId,this.getServletName(), session);
// 	    		geso.traphaco.center.util.GiuDieuKienLoc.setParamsToOject(obj, searchQuery);
		    	
		    	 
		    	obj.init("");
		    	String querySearch = GiuDieuKienLoc.createParams(obj);
				util.setSearchToHM(userId, session,this.getServletName(), querySearch);
				  
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
		
	    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpThuTien.jsp");
	    		return;
		    	 }
	    	
	    	}
	    }
	
	
	private boolean CreatePivotTable(OutputStream out,HttpServletResponse response,HttpServletRequest request,IErpThutienList obj, String querythutien, String querythutienhd) throws Exception 
	{
		boolean isFillData = false;
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();
		
		fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\BcThuTien.xlsm");
		
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

		CreateStaticData(workbook, querythutien, querythutienhd, obj );
		   
		if (!isFillData)
		{
			if(fstream != null)
				fstream.close();
			return false;
		}
		
		workbook.save(out);
		fstream.close();
		return true;	
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
	
	private void CreateStaticData(Workbook workbook, String query , String query2, IErpThutienList obj) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
	    
		dbutils db = new dbutils();
		
		Cell cell = null;
		
		cells.setRowHeight(0, 20.0f);
		cell = cells.getCell("A1");			
		cell.setValue("Đơn vị: " + obj.getCtyTen());

		cells.setRowHeight(1, 20.0f);
		cell = cells.getCell("A2");
		cell.setValue("Địa chỉ: " + obj.getDiachi());
		
		cells.setRowHeight(2, 20.0f);
		cell = cells.getCell("A3");
		cell.setValue("Mã số thuế: " + obj.getMasothue()); 
		
		ResultSet rs = db.get(query);
				
		int i = 8;
		if(rs != null)
		{
			try 
			{				
				while(rs.next())
				{	
					cell = cells.getCell("A" + Integer.toString(i));
					cell.setValue(rs.getString("machungtu"));
					cell = CreateBorderSetting(cell);
					cell = CreateBorderSetting(cells.getCell("Z1"));
					
					cell = cells.getCell("B" + Integer.toString(i));
					cell.setValue(rs.getString("trangthai"));
					cell = CreateBorderSetting(cell);
					cell = CreateBorderSetting(cells.getCell("Z1"));
					
					cell = cells.getCell("C" + Integer.toString(i));
					cell.setValue(rs.getString("mafast"));
					cell = CreateBorderSetting(cell);
					cell = CreateBorderSetting(cells.getCell("Z1"));
					
					cell = cells.getCell("D" + Integer.toString(i));
					cell.setValue(rs.getString("tenkh"));
					cell = CreateBorderSetting(cell);
					cell = CreateBorderSetting(cells.getCell("Z1"));
					
					cell = cells.getCell("E" + Integer.toString(i));
					cell.setValue(rs.getString("kbh_fk"));
					cell = CreateBorderSetting(cell);
					cell = CreateBorderSetting(cells.getCell("Z1"));
					
					cell = cells.getCell("F" + Integer.toString(i));
					cell.setValue(rs.getString("SOHOADON"));
					cell = CreateBorderSetting(cell);
					cell = CreateBorderSetting(cells.getCell("Z1"));
					
					cell = cells.getCell("G" + Integer.toString(i));
					cell.setValue(rs.getString("NGAYXUATHD"));
					cell = CreateBorderSetting(cell);
					cell = CreateBorderSetting(cells.getCell("Z1"));
										
					cell = cells.getCell("H" + Integer.toString(i));
					cell.setValue(rs.getDouble("tongtienavat"));
					cell = CreateBorderSetting(cell);
					cell = CreateBorderSetting(cells.getCell("Z1"));
					
					cell = cells.getCell("I" + Integer.toString(i));
					cell.setValue(rs.getDouble("thucthu"));
					cell = CreateBorderSetting(cell);
					cell = CreateBorderSetting(cells.getCell("Z1"));
					
					cell = cells.getCell("J" + Integer.toString(i));
					cell.setValue(rs.getString("xoachenhlech"));
					cell = CreateBorderSetting(cell);
					cell = CreateBorderSetting(cells.getCell("Z1"));
					
					cell = cells.getCell("K" + Integer.toString(i));
					cell.setValue(rs.getDouble("tienchenhlech"));
					cell = CreateBorderSetting(cell);
					cell = CreateBorderSetting(cells.getCell("Z1"));
					
					cell = cells.getCell("L" + Integer.toString(i));
					cell.setValue(rs.getString("tencp"));
					cell = CreateBorderSetting(cell);
					cell = CreateBorderSetting(cells.getCell("Z1"));
														 					
					i++;
				}
				rs.close();
			}
			catch (Exception e){ e.printStackTrace(); }
		}
		
		
		worksheet = worksheets.getSheet(1);
	    Cells cell1s = worksheet.getCells();	
	    
	    cell1s.setRowHeight(0, 20.0f);
		cell = cell1s.getCell("A1");			
		cell.setValue("Đơn vị: " + obj.getCtyTen());

		cell1s.setRowHeight(1, 20.0f);
		cell = cell1s.getCell("A2");
		cell.setValue("Địa chỉ: " + obj.getDiachi());
		
		cell1s.setRowHeight(2, 20.0f);
		cell = cell1s.getCell("A3");
		cell.setValue("Mã số thuế: " + obj.getMasothue()); 
		
		
		rs = db.get(query2);
				
		i = 8;
		if(rs != null)
		{
			try 
			{				
				cell = null;
				while(rs.next())
				{					
					
					cell = cell1s.getCell("A" + Integer.toString(i));
					cell.setValue(rs.getString("machungtu"));
					cell = CreateBorderSetting(cell);
					cell = CreateBorderSetting(cells.getCell("Z1"));
					
					cell = cell1s.getCell("B" + Integer.toString(i));
					cell.setValue(rs.getString("trangthai"));
					cell = CreateBorderSetting(cell);
					cell = CreateBorderSetting(cells.getCell("Z1"));
					
					cell = cell1s.getCell("C" + Integer.toString(i));
					cell.setValue(rs.getString("mafast"));
					cell = CreateBorderSetting(cell);
					cell = CreateBorderSetting(cells.getCell("Z1"));
					
					cell = cell1s.getCell("D" + Integer.toString(i));
					cell.setValue(rs.getString("tenKH"));
					cell = CreateBorderSetting(cell);
					cell = CreateBorderSetting(cells.getCell("Z1"));
					
					cell = cell1s.getCell("E" + Integer.toString(i));
					cell.setValue(rs.getString("KBH_FK"));
					cell = CreateBorderSetting(cell);
					cell = CreateBorderSetting(cells.getCell("Z1"));
					
					cell = cell1s.getCell("F" + Integer.toString(i));
					cell.setValue(rs.getString("sohoadon"));
					cell = CreateBorderSetting(cell);
					cell = CreateBorderSetting(cells.getCell("Z1"));
				
					cell = cell1s.getCell("G" + Integer.toString(i));
					cell.setValue(rs.getString("ngayxuathd"));
					cell = CreateBorderSetting(cell);
					cell = CreateBorderSetting(cells.getCell("Z1"));
					
					cell = cell1s.getCell("H" + Integer.toString(i));
					cell.setValue(rs.getString("MASP"));
					cell = CreateBorderSetting(cell);
					cell = CreateBorderSetting(cells.getCell("Z1"));
					
					cell = cell1s.getCell("I" + Integer.toString(i));
					cell.setValue(rs.getDouble("tienavat"));
					cell = CreateBorderSetting(cell);
					cell = CreateBorderSetting(cells.getCell("Z1"));
					
					cell = cell1s.getCell("J" + Integer.toString(i));
					cell.setValue(rs.getDouble("thanhtoan"));
					cell = CreateBorderSetting(cell);
					cell = CreateBorderSetting(cells.getCell("Z1"));
					
					cell = cell1s.getCell("K" + Integer.toString(i));
					cell.setValue(rs.getDouble("ptchietkhau"));
					cell = CreateBorderSetting(cell);
					cell = CreateBorderSetting(cells.getCell("Z1"));
									 
					cell = cell1s.getCell("L" + Integer.toString(i));
					cell.setValue(rs.getDouble("tienck"));
					cell = CreateBorderSetting(cell);
					cell = CreateBorderSetting(cells.getCell("Z1"));
					
					cell = cell1s.getCell("M" + Integer.toString(i));
					cell.setValue(rs.getDouble("thucthu"));
					cell = CreateBorderSetting(cell);
					cell = CreateBorderSetting(cells.getCell("Z1"));
					
					i++;
				}
				rs.close();
			}
			catch (Exception e){ e.printStackTrace(); }
		}
	 
	}
	
	
	private String getQueryExcelThuTien(HttpServletRequest request, String pages, String soDong, IErpThutienList obj)
	{    	
		// HÓA ĐƠN TÀI CHÍNH
    	String query = " SELECT A.machungtu, case A.TRANGTHAI when 0 then N'Chưa chốt' when 1 then N'Đã chốt' when 2 then N'Đã xóa' end AS TRANGTHAI,ISNULL(ISNULL(D.PK_SEQ, E.PK_SEQ), F.PK_SEQ) KHACHHANG_FK, \n"+
				       " CASE WHEN C.KHACHHANG_FK IS NOT NULL THEN D.maFAST WHEN  C.NPP_DAT_FK IS NOT NULL THEN E.MAFAST \n"+
				       " ELSE F.MA END MAFAST, \n"+
				       " CASE WHEN C.KHACHHANG_FK IS NOT NULL THEN D.TEN WHEN  C.NPP_DAT_FK IS NOT NULL THEN E.TEN \n"+
				       " ELSE F.TEN END TENKH, \n"+
				       " G.TEN KBH_FK, C.SOHOADON , C.NGAYXUATHD, C.tongtienavat, isnull(b.SOTIENTT,0) thucthu, case ISNULL(xoachenhlech, 0) when 0 then N'Không' else N'Có' end xoachenhlech, \n"+
				       " ISNULL(tienchenhlech,0) tienchenhlech, \n"+
				       " isnull((select cast(B.macp as nvarchar(50)) from erp_nhomchiphi nhomcp where B.macp = nhomcp.pk_seq ),'') macp, \n"+
				       " isnull((select nhomcp.ten from erp_nhomchiphi nhomcp where B.macp = nhomcp.pk_seq ),'') tencp \n"+
				       " FROM ERP_THUTIEN A INNER JOIN ERP_THUTIEN_HOADON B ON A.PK_SEQ = B.THUTIEN_FK \n"+
				       " INNER JOIN ERP_HOADONNPP C ON B.HOADON_FK = C.PK_SEQ \n"+
				       " LEFT JOIN KHACHHANG D ON C.KHACHHANG_FK = D.PK_SEQ \n"+
				       " LEFT JOIN NHAPHANPHOI E ON C.NPP_DAT_FK = E.PK_SEQ \n"+
				       " LEFT JOIN ERP_NHANVIEN F ON C.nhanvien_fk = F.PK_SEQ \n"+
				       " LEFT JOIN KENHBANHANG G ON C.KBH_FK = G.PK_SEQ \n"+
				       " WHERE B.LOAIHOADON = 0 AND A.HTTT_FK = 100000 AND A.CONGTY_FK = "+obj.getCtyId();
    	
    	System.out.println(query);
    	return query;
    	
	}	
	
	private String getQueryExcelThuTienHoaDon(HttpServletRequest request, String pages, String soDong, IErpThutienList obj)
	{
		// HÓA ĐƠN TÀI CHÍNH
    	String query = " select A.machungtu, case A.TRANGTHAI when 0 then N'Chưa chốt' when 1 then N'Đã chốt' when 2 then N'Đã xóa' end AS TRANGTHAI , ISNULL(ISNULL(D.PK_SEQ, E.PK_SEQ), F.PK_SEQ) KHACHHANG_FK, \n"+
				       " CASE WHEN C.KHACHHANG_FK IS NOT NULL THEN D.maFAST WHEN  C.NPP_DAT_FK IS NOT NULL THEN E.MAFAST \n"+
				       " ELSE F.MA END MAFAST, \n"+
				       " CASE WHEN C.KHACHHANG_FK IS NOT NULL THEN D.TEN WHEN  C.NPP_DAT_FK IS NOT NULL THEN E.TEN \n"+
				       " ELSE F.TEN END TENKH, \n"+
				       " G.TEN KBH_FK, C.SOHOADON, C.NGAYXUATHD, H.TIENAVAT , B.thanhtoan, isnull(B.ptchietkhau,0) ptchietkhau, isnull(B.tienck,0) tienck, isnull(B.thucthu, B.thanhtoan) thucthu, SP.MA_FAST MASP \n"+
				       " from ERP_THUTIEN A INNER JOIN ERP_THUTIEN_HOADON_SP B ON A.PK_SEQ = B.thutien_fk \n"+
				       " INNER JOIN ERP_HOADONNPP_SP H ON B.hoadon_fk = H.HOADON_FK AND B.sanpham_fk = H.SANPHAM_FK \n"+
				       " INNER JOIN ERP_HOADONNPP C ON B.hoadon_fk = C.PK_SEQ \n"+
				       " LEFT JOIN KHACHHANG D ON C.KHACHHANG_FK = D.PK_SEQ \n"+
				       " LEFT JOIN NHAPHANPHOI E ON C.NPP_DAT_FK = E.PK_SEQ \n"+
				       " LEFT JOIN ERP_NHANVIEN F ON C.nhanvien_fk = F.PK_SEQ \n"+
				       " LEFT JOIN KENHBANHANG G ON C.KBH_FK = G.PK_SEQ \n"+
				       " LEFT JOIN SANPHAM SP ON B.sanpham_fk = SP.PK_SEQ \n"+
				       " WHERE B.LOAIHOADON = 0 AND LEN(H.SCHEME) <= 0 AND A.HTTT_FK = 100000 AND A.CONGTY_FK = "+obj.getCtyId();

			System.out.println(query);
			return query;

	}	
	
	
	private void getSearchQuery(HttpServletRequest request, IErpThutienList obj)
	{
		Utility util = new Utility();
	

		String tungay = util.antiSQLInspection(request.getParameter("tungay"));
		if(tungay == null)
			tungay = "";
		obj.setTungay(tungay);
		
		String denngay = util.antiSQLInspection(request.getParameter("denngay"));
		if(denngay == null)
			denngay = "";
		obj.setDenngay(denngay);
		
		String ghichu = util.antiSQLInspection(request.getParameter("ghichu"));
		if(ghichu == null)
			ghichu = "";
		obj.setGhichu(ghichu);
		
		String khachhang = util.antiSQLInspection(request.getParameter("khachhang"));
		if(khachhang == null)
			khachhang = "";
		obj.setKhId(khachhang);
		
		String nccId = util.antiSQLInspection(request.getParameter("nppId"));
		if(nccId == null)
			nccId = "";
		obj.setNccId(nccId);
		
		String nvgnId = util.antiSQLInspection(request.getParameter("nvgnId"));
		if(nvgnId == null)
			nvgnId = "";
		obj.setNvgnId(nvgnId);
		
		String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);		
		
		String sochungtu = util.antiSQLInspection(request.getParameter("sochungtu"));
		if(sochungtu == null)
			sochungtu = "";
		obj.setsochungtu(sochungtu);
		
		String sohoadon = util.antiSQLInspection(request.getParameter("sohoadon"));
		if(sohoadon == null)
			sohoadon = "";
		obj.setsohoadon(sohoadon);
		
		String nguoisua = util.antiSQLInspection(request.getParameter("nguoisua"));
		if(nguoisua == null)
			nguoisua = "";
		obj.setNguoisuaId(nguoisua);
				
		String nvId = util.antiSQLInspection(request.getParameter("nvId"));
		if(nvId == null)
			nvId = "";
		obj.setNvId(nvId);
		
		String kbhId = util.antiSQLInspection(request.getParameter("kbhId"));
		if(kbhId == null)
			kbhId = "";
		obj.setKbhId(kbhId);		
		
		String khohhid = util.antiSQLInspection(request.getParameter("khohhid"));
		if(khohhid == null)
			khohhid = "";
		obj.setKhohangId(khohhid);	
		
		
		/*String sobangke=util.antiSQLInspection(request.getParameter("sobangke"));
		if (sobangke == null)
			sobangke = "";
		obj.setsobangke(sobangke);*/
		String maChungTu = util.antiSQLInspection(request.getParameter("maChungTu"));
		if(maChungTu == null)
			maChungTu = "";
		obj.setMaChungTu(maChungTu);
		
		String tusotien = util.antiSQLInspection(request.getParameter("tusotien"));
 		if(tusotien == null)
 			tusotien = "";
 		obj.setTusotien(tusotien.replaceAll(",", ""));
 			
 		String densotien = util.antiSQLInspection(request.getParameter("densotien"));
 		if(densotien == null)
 			densotien = "";
 		obj.setDensotien(densotien.replaceAll(",", ""));
		
		
		
	}
}