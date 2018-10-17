package geso.traphaco.erp.servlets.reports;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.baocaotaisancodinh.IBcTaisancodinh;
import geso.traphaco.erp.beans.baocaotaisancodinh.imp.BcTaisancodinh;
import geso.traphaco.erp.db.sql.dbutils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
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
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class ErpBCTheoDoiTaiSanCoDinh extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	public ErpBCTheoDoiTaiSanCoDinh() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		Utility Ult = new Utility();
		HttpSession session = request.getSession();

		String ctyId = (String)session.getAttribute("congtyId");
		if(ctyId == null) ctyId = "100000";

		String querystring = request.getQueryString();
		String userId = Ult.getUserId(querystring);
		
		IBcTaisancodinh obj = new BcTaisancodinh();
		obj.setCtyId(ctyId);
		obj.setuserId(userId);
//		obj.init_TheodoiTSCD();
		
		session.setAttribute("obj", obj);

		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCTheoDoiTaiSanCoDinh.jsp";
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
			IBcTaisancodinh obj = new BcTaisancodinh();
		
			String congTyId = (String)session.getAttribute("congtyId");
			if(congTyId == null) congTyId = "100000";
			
			obj.setuserId(userId);		
			
			String nam = util.antiSQLInspection(request.getParameter("nam"));
			if (nam == null)
				nam = "";
			obj.setNam(nam);
			
			String thang = util.antiSQLInspection(request.getParameter("thang"));
			if (thang == null)
				thang = "";
			
			String loai = util.antiSQLInspection(request.getParameter("loai"));
			if (loai == null)
				loai = "";
			obj.setLoai(loai);
				
			obj.init_TheodoiTSCD();
			
			String action = request.getParameter("action");
		
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCTheoDoiTaiSanCoDinh.jsp";

			if (action.equals("tao")) {
				try {
					response.setContentType("application/xlsm");
					if(obj.getLoai().equals("1"))
					{
						response.setHeader("Content-Disposition", "attachment; filename=BCTheoDoiTaiSanCoDinh_" + getDate() + ".xlsm");
					}
					else
					{
						response.setHeader("Content-Disposition", "attachment; filename=BCTheoDoiTaiSanCoDinh_" + getDate() + ".xlsm");
					}
					CreateReport(out, congTyId, thang, nam);
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

	private void CreateReport(OutputStream out, String congTyId, String thang, String nam)throws Exception {
		try{
			FileInputStream fstream = null;
			Workbook workbook = new Workbook();
			
			fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\BCTheoDoiTaiSanCoDinh.xlsm");
			
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			worksheet.setName("Sheet1");
			
			Cells cells = worksheet.getCells();
			dbutils db = new dbutils();
			
			String query = "select TEN, DIACHI, MASOTHUE from ERP_CONGTY where TRANGTHAI = 1 and PK_SEQ = " + congTyId;
			ResultSet ctRs = db.get(query);
			
			if (ctRs != null)
			{
				if (ctRs.next())
				{
					String ten = ctRs.getString("TEN");
					Cell tenCell = cells.getCell(2, 1);
					tenCell.setValue("Công ty: " + ten);
					
					String diaChi = ctRs.getString("DIACHI");
					Cell diaChiCell = cells.getCell(3, 1);
					diaChiCell.setValue("Địa chỉ: " + diaChi);
					
					String mst = ctRs.getString("MASOTHUE");
					Cell mstCell = cells.getCell(4, 1);
					mstCell.setValue("Mã số thuế: " + mst);
				}
				ctRs.close();
			}
			
			String [] param = {congTyId, nam, thang};
			ResultSet rs = db.getRsByPro("GetBaoCaoTSCD", param);
			
			 
			ResultSetMetaData rsmd = rs.getMetaData();
			int socottrongSql = rsmd.getColumnCount();
			
			int countRow = 7;

			Cell cell1 = cells.getCell(3, 8);
			cell1.setValue("Tháng " + thang + " Năm " + nam);
			
			int stt = 0;
			while(rs.next())
			{
				stt++;
				for(int i = 2;i <= socottrongSql ; i ++)
				{
					Cell cell = cells.getCell(countRow, i - 1);
					if(rsmd.getColumnType(i) == Types.DOUBLE || rsmd.getColumnType(i) == Types.INTEGER || rsmd.getColumnType(i) == Types.DECIMAL)
					{
						cell.setValue(rs.getDouble(i));
						cell = CreateBorderSetting2(cell);
					}
					else
					{
						cell.setValue(rs.getString(i));
						cell = CreateBorderSetting2(cell);
					}
				}
				++countRow;
			}
			
			for(int i = 1;i <=socottrongSql ; i ++)
			{
				CreateBorderSetting3(cells.getCell(countRow - 1, i-1));
			}
			
			workbook.save(out);
			fstream.close();
		}catch(Exception ex){
			ex.printStackTrace();
			throw new Exception("Khong tao duoc bao cao:" + ex.toString());
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

	public Cell CreateBorderSetting3(Cell cell) throws IOException
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
        style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
        style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
        style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);

        cell.setStyle(style);
        return cell;
    }

	private void setStyleColor1(Cells cells ,Cell cell)
	{
		Cell cell1 = cells.getCell("Z1");
		Style style;	
		style = cell1.getStyle();
        style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
        style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
        cell.setStyle(style);
	}
			
	private void setStyleColor2(Cells cells ,Cell cell)
	{
		Cell cell1 = cells.getCell("AA1");
			Style style;	
			style = cell1.getStyle();
	        style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
	        style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
	        cell.setStyle(style);
	}		 
}