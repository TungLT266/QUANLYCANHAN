package geso.traphaco.erp.servlets.danhgiatigia;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.danhgiatigia.IDanhgiatigiaList;
import geso.traphaco.erp.beans.danhgiatigia.imp.DanhgiatigiaList;
import geso.traphaco.erp.db.sql.dbutils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Font;
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;


public class Erp_danhgiatigiaSvl extends HttpServlet {
	   static final long serialVersionUID = 1L;
	   
	   private Utility util = new Utility();

	   public Erp_danhgiatigiaSvl() {
			super();
		}   	
		
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		    request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
		    
		    PrintWriter out = response.getWriter();
		    HttpSession session = request.getSession();
		    
		    
		    IDanhgiatigiaList obj = (IDanhgiatigiaList) new DanhgiatigiaList();
		    	
		    String querystring = request.getQueryString();
		    String userId = util.getUserId(querystring);
		    String ctyId = (String)session.getAttribute("congtyId");
		    obj.setCtyId(ctyId);
		    
		    out.println(userId);
		    
		    if (userId.length()==0)
		    	userId = request.getParameter("userId");
		    
		    String action = util.getAction(querystring);
		    out.println(action);

		    obj.init();
		    
		    session.setAttribute("obj", obj);
		    session.setAttribute("userId", userId);
				
		    String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_Danhgiatigia.jsp";
		    response.sendRedirect(nextJSP);
			
		}  	

		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		    request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
		    
			HttpSession session = request.getSession();
		    Utility util = new Utility();

		    OutputStream out = response.getOutputStream();

		    IDanhgiatigiaList obj = (IDanhgiatigiaList) new DanhgiatigiaList();
		    String ctyId = (String)session.getAttribute("congtyId");
		    obj.setCtyId(ctyId);

		    String userId = util.antiSQLInspection(request.getParameter("userId"));
		    obj.setUserId(userId);
		    	    
		    String nam = util.antiSQLInspection(request.getParameter("nam"));
		    if(nam == null) nam = "";
		    obj.setNam(nam);
		    
		    String quy = util.antiSQLInspection(request.getParameter("quy"));
		    if(quy == null) quy = "";
		    obj.setQuy(quy);

		    String ghidao = util.antiSQLInspection(request.getParameter("ghidao"));
		    if(ghidao == null) ghidao = "0";
		    obj.setGhidao(ghidao);

		    String ngaychungtu = util.antiSQLInspection(request.getParameter("ngaychungtu"));
		    if(ngaychungtu == null) ngaychungtu = "";
		    obj.setNgaychungtu(ngaychungtu);

		    String[] ttIds = request.getParameterValues("ttIds");
		    obj.setTienteIds(ttIds);

		    String[] tigia = request.getParameterValues("tigia");
		    for(int i = 0 ; i< tigia.length; i ++)
		    {
		    	if (tigia[i].trim().length() == 0) tigia[i] = "0";
		    }
		    obj.setTigia(tigia);

		    userId = util.antiSQLInspection(request.getParameter("userId"));
			String action = request.getParameter("action");
			
			obj.setAction(action);
			System.out.println("action:" + action);
			if (action.equals("new"))
			{
				if(obj.checkTigia()){
					obj.init_new();
				}
				
				session.setAttribute("obj", obj);
				response.sendRedirect("pages/Erp/Erp_Danhgiatigia.jsp");
			}else if (action.equals("save"))
			{
				if(obj.checkTigia()){
					if(obj.Save()){
						obj.init();
					}else{
						obj.init_new();
					}
				}
				
				session.setAttribute("obj", obj);
			    session.setAttribute("userId", userId);
				response.sendRedirect("pages/Erp/Erp_Danhgiatigia.jsp");
			}else if (action.equals("chot"))
			{
		    	obj.Chot();
			    obj.init();
			    
			    session.setAttribute("obj", obj);
			    session.setAttribute("userId", userId);
					
			    String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_Danhgiatigia.jsp";
				response.sendRedirect(nextJSP);

		    }else if (action.equals("excel"))
			{
		    	try
			    {
					response.setContentType("application/xlsm");
					response.setHeader("Content-Disposition", "attachment; filename=DanhGiaTiGia.xlsm");

		    		FileInputStream fstream = null;
		    		Workbook workbook = new Workbook();
		    		
		    		fstream = new FileInputStream(getServletContext().getInitParameter("pathTemplate") + "\\DanhGiaTiGia.xlsm");
		    		workbook.open(fstream);
		    		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

		    		CreateHeader(workbook, obj);
				    CreateData(workbook, obj);
				    workbook.save(out);
				    fstream.close();
			    }
			    catch (Exception ex){ 
			    	ex.printStackTrace();
			    }
		    	
			    obj.init();
			    
			    session.setAttribute("obj", obj);
			    session.setAttribute("userId", userId);
					
			    String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_Danhgiatigia.jsp";
				response.sendRedirect(nextJSP);
				return;
		    }
			else{
			    obj.init();
			    
			    session.setAttribute("obj", obj);
			    session.setAttribute("userId", userId);
					
			    String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_Danhgiatigia.jsp";
				response.sendRedirect(nextJSP);
		    	
		    }
		}
		
		private void CreateHeader(Workbook workbook, IDanhgiatigiaList obj) 
		{
			String sql = "SELECT TEN, DIACHI FROM ERP_CONGTY WHERE PK_SEQ = " + obj.getCtyId() + " ";
			dbutils db = new dbutils();
			String congty = "";
			String diachi = "";
			System.out.println(sql);

			ResultSet rs = db.get(sql);
			try{
				rs.next();
				congty = rs.getString("TEN");
				diachi = rs.getString("DIACHI");
				rs.close();
			}catch(java.sql.SQLException e){}
			
			Worksheets worksheets = workbook.getWorksheets();
		    Worksheet worksheet = worksheets.getSheet(0);
		   	   
		    Cells cells = worksheet.getCells();
		   	    
		    Cell cell = cells.getCell("B5"); 
		    cell.setValue("QUÝ " + obj.getQuy() + " NĂM " + obj.getNam());
		   		    
		    worksheet.setName("Chi tiết đánh giá tỉ giá");
		}
		
		private void CreateData(Workbook workbook, IDanhgiatigiaList obj) 
		{
			Worksheets worksheets = workbook.getWorksheets();
		    Worksheet worksheet = worksheets.getSheet(0);
		    Cells cells = worksheet.getCells();
		    
			dbutils db = new dbutils();
			int row = 8;
			String query = "";
			Style style;		
			Font font = new Font();

			if(obj.getQuy().length() > 0 & obj.getNam().length() > 0)
			{
					
				query =	"SELECT SUBSTRING(TK.SOHIEUTAIKHOAN, 1, 3) AS SOHIEU, ISNULL(DGTG_CT.MADOITUONG, '') AS MADOITUONG, ISNULL(DGTG_CT.DOITUONG, '') AS DOITUONG, \n " + 
						"TT.MA AS TIENTE, SUM(SODUNGOAITE) AS SODUNGOAITE,  \n " +
						"SUM(SODU_TRUOC_DG) AS SODU_TRUOC_DG, SUM(SODU_SAU_DG) AS SODU_SAU_DG, SUM(CHENHLECH) AS CHENHLECH  \n " +
						"FROM ERP_DANHGIATIGIA_CHITIET DGTG_CT  \n " +
						"INNER JOIN ERP_DANHGIATIGIA DGTG ON DGTG.PK_SEQ = DGTG_CT.DANHGIATIGIA_FK  \n " +
						"INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = DGTG_CT.TAIKHOAN_FK \n " +
						"INNER JOIN ERP_TIENTE TT ON TT.PK_SEQ = DGTG_CT.TIENTE_FK  \n " +
						"WHERE DGTG.QUY = " + obj.getQuy() + " AND DGTG.NAM = " + obj.getNam() + " \n " +
						"GROUP BY SUBSTRING(TK.SOHIEUTAIKHOAN, 1, 3), ISNULL(DGTG_CT.MADOITUONG, ''), ISNULL(DGTG_CT.DOITUONG, ''), TT.MA \n " + 
						"ORDER BY SUBSTRING(TK.SOHIEUTAIKHOAN, 1, 3) ";
					
				ResultSet rs = db.get(query);
				Cell cell = null;
				
				if(rs != null)
				{	
					try{
						while(rs.next()){
							cell = cells.getCell("A" + Integer.toString(row));
							cell.setValue(rs.getString("SOHIEU"));
							
							cell = cells.getCell("B" + Integer.toString(row));
							cell.setValue(rs.getString("MADOITUONG"));
							
							cell = cells.getCell("C" + Integer.toString(row));
							cell.setValue(rs.getString("DOITUONG"));

							cell = cells.getCell("D" + Integer.toString(row));
							cell.setValue(rs.getString("TIENTE"));

							cell = cells.getCell("E" + Integer.toString(row));
							style = cell.getStyle();
							style.setNumber(3);
							cell.setStyle(style);
							cell.setValue(rs.getDouble("SODUNGOAITE"));
							
							
							cell = cells.getCell("F" + Integer.toString(row));
							style = cell.getStyle();
							style.setNumber(3);
							cell.setStyle(style);
							cell.setValue(rs.getDouble("SODU_TRUOC_DG"));
							
							cell = cells.getCell("G" + Integer.toString(row));
							style = cell.getStyle();
							style.setNumber(3);
							cell.setStyle(style);
							cell.setValue(rs.getDouble("SODU_SAU_DG"));

							cell = cells.getCell("H" + Integer.toString(row));
							style = cell.getStyle();
							style.setNumber(3);
							cell.setStyle(style);
							cell.setValue(rs.getDouble("CHENHLECH"));
							
							row++;
						}
						rs.close();
					}catch(Exception e){
						e.printStackTrace();
					}

				}
			}
		}
		
}
