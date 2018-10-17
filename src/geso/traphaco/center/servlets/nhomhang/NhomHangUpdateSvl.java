package geso.traphaco.center.servlets.nhomhang;


import geso.traphaco.center.beans.nhomhang.INhomHang;
import geso.traphaco.center.beans.nhomhang.INhomHangList;
import geso.traphaco.center.beans.nhomhang.imp.NhomHang;
import geso.traphaco.center.beans.nhomhang.imp.NhomHangList;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.db.sql.dbutils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;
@WebServlet("/NhomHangUpdateSvl")
public class NhomHangUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	public NhomHangUpdateSvl()
	{
		super();
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		OutputStream out = response.getOutputStream(); 
		INhomHang nhBean;

		Utility util = new Utility();
		HttpSession session = request.getSession();
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		System.out.println(userId);
		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));
		String id = util.getId(querystring);
		nhBean = new NhomHang(id);
		nhBean.init();
		nhBean.setUserId(userId);

		session.setAttribute("nhBean", nhBean);
		String nextJSP = "/TraphacoHYERP/pages/Center/NhomHangUpdate.jsp";
		if(querystring.indexOf("display") > 0)
	    {
	    	nextJSP = "/TraphacoHYERP/pages/Center/NhomHangDisplay.jsp";
	    }
		if(querystring.indexOf("excel") > 0)
	    {
			System.out.println("vo day");
			response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "Attachment; filename=ReportNhomhang.xlsm");
			 
			try
			{
				CreatePivotTable(out, response, request, nhBean); 
				
			} catch (Exception ex)
			{
				nhBean.setMsg(ex.getMessage());
				request.getSession().setAttribute("errors", ex.getMessage());
			 
				session.setAttribute("obj", nhBean);
				session.setAttribute("userId", userId);
				
				nextJSP = "/TraphacoHYERP/pages/Center/NhomHangDisplay.jsp";
				
				
			}
	    	
	    }
		response.sendRedirect(nextJSP);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();

		INhomHang nhBean;
		this.out = response.getWriter();
		Utility util = new Utility();

		String id = util.antiSQLInspection(request.getParameter("id"));
		if (id == null)
		{
			nhBean = new NhomHang("");
		} else
		{
			nhBean = new NhomHang(id);
		}

		String userId = util.antiSQLInspection(request.getParameter("userId"));
		nhBean.setUserId(userId);

		String ten = util.antiSQLInspection(request.getParameter("ten"));
		if (ten == null)
			ten = "";
		nhBean.setTen(ten);

		String ma = util.antiSQLInspection(request.getParameter("ma"));
		if (ma == null)
			ma = "";
		nhBean.setMa(ma);
		
		String spId="";
		
		String[] spIds = request.getParameterValues("spId");
		if (spIds != null)
		{
			int size = spIds.length;
			for (int i = 0; i < size; i++)
			{
				spId += spIds[i] + ",";
			}
			if (spId.length() > 0)
			{
				spId = spId.substring(0, spId.length() - 1);
			}
		}
		nhBean.setSpId(spId);
		
		
		
		boolean error = false;

		if (ma.trim().length() == 0)
		{
			nhBean.setMsg("Vui lòng nhập mã nhóm");
			error = true;
		}

		if (ten.trim().length() == 0)
		{
			nhBean.setMsg("Vui lòng nhập tên nhóm");
			error = true;
		}

		System.out.println("spIds: "+spIds.length +"-"+nhBean.getSpId());
		String action = request.getParameter("action");
		String nextJSP = "";
		if (!error)
		{
			if (action.equals("save"))
			{
				INhomHangList obj = new NhomHangList();
				if (id == null)
				{
					if (!nhBean.save())
					{
						session.setAttribute("nhBean", nhBean);
						nhBean.setUserId(userId);
						nextJSP = "/TraphacoHYERP/pages/Center/NhomHangNew.jsp";
						response.sendRedirect(nextJSP);
					} else
					{
						obj.setUserId(userId);
						obj.init();
						session.setAttribute("obj", obj);
						nextJSP = "/TraphacoHYERP/pages/Center/NhomHang.jsp";
						response.sendRedirect(nextJSP);
					}
				} else
				{
					if (!nhBean.edit())
					{
						session.setAttribute("nhBean", nhBean);
						nhBean.setUserId(userId);
						nextJSP = "/TraphacoHYERP/pages/Center/NhomHangUpdate.jsp";
						response.sendRedirect(nextJSP);
					} else
					{
						obj.setUserId(userId);
						obj.init();
						session.setAttribute("obj", obj);
						nextJSP = "/TraphacoHYERP/pages/Center/NhomHang.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			} else
			{
				nhBean.createRs();
				session.setAttribute("nhBean", nhBean);
				if (id == null)
				{
					nextJSP = "/TraphacoHYERP/pages/Center/NhomHangNew.jsp";
				} else
				{
					nextJSP = "/TraphacoHYERP/pages/Center/NhomHangUpdate.jsp";
				}
				response.sendRedirect(nextJSP);
			}

		} else
		{
			nhBean.createRs();
			session.setAttribute("nhBean", nhBean);
			if (id == null)
			{
				nextJSP = "/TraphacoHYERP/pages/Center/NhomHangNew.jsp";
			} else
			{
				nextJSP = "/TraphacoHYERP/pages/Center/NhomHangUpdate.jsp";
			}
			response.sendRedirect(nextJSP);
		}
	}

	private void CreatePivotTable(OutputStream out, HttpServletResponse response, HttpServletRequest request,  INhomHang obj) throws Exception
	{
		try
		{
			
			String strfstream = getServletContext().getInitParameter("path") + "\\Nhomhang.xlsx";
			 
			FileInputStream fstream = new FileInputStream(strfstream);
			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			Worksheets worksheets = workbook.getWorksheets();
	  		Worksheet worksheet_2 = worksheets.getSheet("sheet1");
	  		worksheet_2.setName("Nhomhang");
	  		 Cells cells = worksheet_2.getCells();
			   
	  		 	Cell	cell = cells.getCell("P1");
				Style style1=cell.getStyle();
				 
				dbutils db = new dbutils();
			
				String query = "select ROW_NUMBER() OVER (ORDER BY A.MA) AS 'SOTT' ,a.MA as spMA,a.TEN as spTEN,c.DONVI as spDonVi \n"
								+"from nhomhang_sanpham b \n"
								+"left join erp_sanpham a on a.PK_SEQ = b.SanPham_FK  \n"
								+"left join DONVIDOLUONG c on c.PK_SEQ=a.DVDL_FK  \n"
								+"where NhomHang_FK = '"+obj.getId()+"'  \n";
								//+" order by a.MA asc \n";
				ResultSet	rs = db.get(query);
		  		worksheets = workbook.getWorksheets();
		  		 
		  		this.TaoBaoCao(db,rs,worksheet_2, obj,"",style1);
		  		 
		  	 
			workbook.save(out);
			fstream.close();
		} catch (Exception ex)
		{
			ex.printStackTrace();
			throw new Exception("Error Message: " + ex.getMessage());
		}
	}
	public String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date date = new Date();
		return dateFormat.format(date);
	}
	private void TaoBaoCao(dbutils db,ResultSet rs,Worksheet worksheet, INhomHang obj, String diengiai, Style style12) 
	{
		  try{
			  
			   Cells cells = worksheet.getCells();
			   cells.setColumnWidth(0, 7.14f);
				cells.setColumnWidth(1, 16.29f);
				cells.setColumnWidth(2, 40.71f);
				cells.setColumnWidth(3, 8.57f);

				
			   
			   geso.traphaco.center.util.Utility uti = new geso.traphaco.center.util.Utility();
			    cells.setRowHeight(0, 50.0f);
			    String query = " select Ten from NhomHang where pk_seq = '"+obj.getId()+"' ";
			    String tennhomhang = "";
			    ResultSet rscongty  = db.get(query);
			    while(rscongty.next())
			    {
			    	tennhomhang = rscongty.getString("TEN");
			    }
			    rscongty.close();
			    Cell cell = cells.getCell("B4");
			    ReportAPI.getCellStyle(cell, Color.BLACK, true, 16, "BÁO CÁO NHÓM HÀNG",1);
			    cell = cells.getCell("B5");
			    ReportAPI.getCellStyle(cell, Color.BLACK, true, 16,"Nhóm hàng: "+ tennhomhang,0);
			    cell = cells.getCell("A7");
			    ReportAPI.getCellStyle(cell, Color.BLACK, true, 16, "Số TT",0);
			    cell = cells.getCell("B7");
			    ReportAPI.getCellStyle(cell, Color.BLACK, true, 16, "Mã hàng",0);
			    cell = cells.getCell("C7");
			    ReportAPI.getCellStyle(cell, Color.BLACK, true, 16, "Tên hàng",0);
			    cell = cells.getCell("D7");
			    ReportAPI.getCellStyle(cell, Color.BLACK, true, 16, "Đơn vị",0);
			   worksheet.setGridlinesVisible(false);
			   
			 
			   
			   ResultSetMetaData rsmd = rs.getMetaData();
			   int socottrongSql = rsmd.getColumnCount();
			   
			   int countRow = 7;

			  
			   
			   NumberFormat formatter = new DecimalFormat("#,###,###");
			   while(rs.next())
			   {
				   Color color_=Color.BLACK;
				     /* if(rs.getDouble(2) <rs.getDouble(1) &&  rs.getDouble(20) != 0  ){
				    	  color_=Color.RED;
				      }*/
			    for(int i = 1; i <= socottrongSql; i ++)
			    {
			     
			     cell = cells.getCell(countRow,i-1);
			     if(rsmd.getColumnType(i) == Types.DOUBLE || rsmd.getColumnType(i)==Types.NUMERIC || rsmd.getColumnType(i)==Types.INTEGER )
			     {
			    
			    	 //cell.setStyle(style1);
			    	 ReportAPI.getCellStyle_double(cell, "Times New Roman", color_, false, 9,  rs.getDouble(i));
			   
			     }
			     else
			     {
			    	 ReportAPI.getCellStyle(cell, "Times New Roman", color_, false, 9, rs.getString(i));
			   
			     }
			    }
			    ++countRow;
			   }
			   
			   if(rs!=null)rs.close();
			    

			 
			  }catch(Exception ex){
				  ex.printStackTrace();
				    
			  }
	}

}
