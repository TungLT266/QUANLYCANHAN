package geso.traphaco.center.servlets.dutrungansach;

import geso.traphaco.center.beans.dutrungansach.IDutrungansach;
import geso.traphaco.center.beans.dutrungansach.imp.Dutrungansach;
import geso.traphaco.center.beans.phanbokhuyenmai.IPhanbokhuyenmai;
import geso.traphaco.center.beans.phanbokhuyenmai.imp.Phanbokhuyenmai;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import geso.traphaco.center.db.sql.*;
import geso.traphaco.center.util.Utility;

import java.sql.ResultSet;

import jxl.Cell;
import jxl.Sheet;

public class DutrungansachSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_DIRECTORY = "c:\\upload\\excel\\";

	public DutrungansachSvl()
	{
		super();
	}

	private Utility util = new Utility();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IDutrungansach pbkmBean = new Dutrungansach();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);

		pbkmBean.init();
		session.setAttribute("pbkm", pbkmBean);
		session.setAttribute("schemeId", "0");
		session.setAttribute("userId", userId);
		String nextJSP = "/TraphacoHYERP/pages/Center/DuTruNganSach.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String contentType = request.getContentType();

		String userId = (String) session.getAttribute("userId");
		IDutrungansach pbkmBean = new Dutrungansach();

		String action = request.getParameter("action");

		if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0))
		{
			MultipartRequest multi = new MultipartRequest(request, UPLOAD_DIRECTORY, 20000000, "UTF-8");

			userId = util.antiSQLInspection(multi.getParameter("userId"));
			session.setAttribute("userId", userId);

			Enumeration files = multi.getFileNames();
			String filename = "";
			System.out.println("__userId" + userId);
			while (files.hasMoreElements())
			{
				String name = (String) files.nextElement();
				filename = multi.getFilesystemName(name);
				System.out.println("File  " + UPLOAD_DIRECTORY + filename);
			}
			if (filename != null && filename.length() > 0)
				pbkmBean.setMessage(this.readExcel(UPLOAD_DIRECTORY + filename, pbkmBean));

			pbkmBean.init();
			session.setAttribute("pbkm", pbkmBean);
			session.setAttribute("userId", userId);
			String nextJSP = "/TraphacoHYERP/pages/Center/DuTruNganSach.jsp";
			response.sendRedirect(nextJSP);

		} 
//		else if (action.equals("phanbo"))
//		{
//			String vungId = util.antiSQLInspection(request.getParameter("vungId"));
//			pbkmBean.setVungId(vungId);
//
//			String kvId = util.antiSQLInspection(request.getParameter("khuvucId"));
//			pbkmBean.setKvId(kvId);
//
//			String tungay = util.antiSQLInspection(request.getParameter("tungay"));
//			pbkmBean.setTungay(tungay);
//
//			String denngay = util.antiSQLInspection(request.getParameter("denngay"));
//			pbkmBean.setDenngay(denngay);
//
//			String phanbo = util.antiSQLInspection(request.getParameter("phanbo"));
//			pbkmBean.setPhanbo(phanbo);
//			
//			String loaingansach = util.antiSQLInspection(request.getParameter("loaingansach"));
//			pbkmBean.setLoaingansach(loaingansach);
//
//			String nppId = request.getParameter("nppId");
//			if (nppId == null)
//				nppId = "";
//			pbkmBean.setNppId(nppId);
//
//			String ctkmId = "";
//			String[] ctkmIds = request.getParameterValues("ctkmId");
//			if (ctkmIds != null)
//			{
//				for (int i = 0; i < ctkmIds.length; i++)
//					ctkmId += ctkmIds[i] + ",";
//				if (ctkmId.length() > 0)
//					ctkmId = ctkmId.substring(0, ctkmId.length() - 1);
//			}
//			pbkmBean.setSchemeId(ctkmId);
//
//			dbutils db = new dbutils();
//			if (ctkmId.length() > 0)
//			{
//				try
//				{
//					String query =  "SELECT npp.NPP_FK, CTKM.CTKM_FK, 99999999999, 0, ddkd.pk_seq  " + 
//								"FROM CTKM_NPP NPP INNER JOIN CTKHUYENMAI CTKM  ON CTKM.PK_SEQ=NPP.CTKM_FK AND NPP.CHON=1 AND CTKM.CTKM_FK IN(" + ctkmId+ ")  AND CTKM.LOAINGANSACH=0 inner join  DAIDIENKINHDOANH ddkd on NPP.NPP_FK = ddkd.NPP_FK";
//					
//
//					System.out.println("[PhanBo]" + query);
//
//					ResultSet rs = db.get(query);
//					if (rs != null)
//					{
//						db.getConnection().setAutoCommit(false);
//						while (rs.next())
//						{
//							// so quy dinh khong gioi han ngan sach la
//							// 9999999999999
//							query = "INSERT INTO PHANBOKHUYENMAI(CTKM_FK,NPP_FK,NGANSACH,DASUDUNG,TINHTRANG, NVBH_FK) values('" + rs.getString("CTKM_FK") + "','" + rs.getString("npp_fk")
//									+ "','99999999999', 0, 0, '" + rs.getString("pk_seq") + "')";
//							if (!db.update(query))
//							{
//								query = "update phanbokhuyenmai set ngansach = '99999999999' where ctkm_fk = '" + rs.getString("CTKM_FK") + "' and npp_fk='" + rs.getString("npp_fk") + "'";
//								if (!db.update(query))
//								{
//									db.update("rollback");
//									pbkmBean.setMessage("Vui lòng liên hệ Admin " + query);
//								}
//							}
//						}
//						db.getConnection().commit();
//						db.getConnection().setAutoCommit(true);
//						if (rs != null)
//							rs.close();
//					
//						pbkmBean.setMessage("Phân bổ thành công !");
//					}
//				} 
//				catch (Exception e)
//				{
//					e.printStackTrace();
//					db.update("rollback");
//					pbkmBean.setMessage("Vui lòng liên hệ Admin " + e.getMessage());
//				}
//			}
//			pbkmBean.init();
//			session.setAttribute("pbkm", pbkmBean);
//			session.setAttribute("userId", userId);
//			String nextJSP = "/TraphacoHYERP/pages/Center/PhanBoKhuyenMai.jsp";
//			response.sendRedirect(nextJSP);
//		}
//		else
//		{
//			String vungId = util.antiSQLInspection(request.getParameter("vungId"));
//			pbkmBean.setVungId(vungId);
//
//			String kvId = util.antiSQLInspection(request.getParameter("khuvucId"));
//			pbkmBean.setKvId(kvId);
//
//			String tungay = util.antiSQLInspection(request.getParameter("tungay"));
//			pbkmBean.setTungay(tungay);
//
//			String denngay = util.antiSQLInspection(request.getParameter("denngay"));
//			pbkmBean.setDenngay(denngay);
//
//			String nppId = request.getParameter("nppId");
//			if (nppId == null)
//				nppId = "";
//			pbkmBean.setNppId(nppId);
//
//			String phanbo = util.antiSQLInspection(request.getParameter("phanbo"));
//			pbkmBean.setPhanbo(phanbo);
//			
//			String loaingansach = util.antiSQLInspection(request.getParameter("loaingansach"));
//			pbkmBean.setLoaingansach(loaingansach);
//			
//			String ctkmId = "";
//			String[] ctkmIds = request.getParameterValues("ctkmId");
//			if (ctkmIds != null)
//			{
//				for (int i = 0; i < ctkmIds.length; i++)
//					ctkmId += ctkmIds[i] + ",";
//				if (ctkmId.length() > 0)
//					ctkmId = ctkmId.substring(0, ctkmId.length() - 1);
//			}
//			pbkmBean.setSchemeId(ctkmId);
//			pbkmBean.init();
//			session.setAttribute("pbkm", pbkmBean);
//			session.setAttribute("userId", userId);
//			String nextJSP = "/TraphacoHYERP/pages/Center/Dutrungansach.jsp";
//			response.sendRedirect(nextJSP);
//		}
	}

	private String readExcel(String fileName, IDutrungansach pbkmBean)
	{
		dbutils db = new dbutils();
		try
		{
			File inputWorkbook = new File(fileName);
			jxl.Workbook w = null;
			Hashtable<String, String> htbCtkmId = getHtbCtkmId();
			Hashtable<String, String> htbVungId = getVung();
			w = jxl.Workbook.getWorkbook(inputWorkbook);
			jxl.Sheet sheet = w.getSheet(0);
			int sodong = sheet.getRows();
			String curscheme;
			db.getConnection().setAutoCommit(false);
			curscheme = getValue(sheet, 0, 3).trim();
			for (int i = 3; i < sodong; i++)
			{
						String scheme = getValue(sheet, 0, i).trim();
						if(scheme.trim().length()>0 && !scheme.equals(curscheme))
							curscheme = scheme;
						String vungten = getValue(sheet, 1, i).trim();
						String ngansach = getValue(sheet, 2, i).trim().replaceAll(",", "");
						
						String ctkmId = htbCtkmId.get(curscheme);
						String vungId = htbVungId.get(vungten);

						if (vungten.trim().length() > 0 && curscheme.length() > 0 && ngansach.trim().length() > 0 && !curscheme.contains("Total"))
						{
							if (vungId == null)
							{
								db.getConnection().rollback();
								System.out.println("1.MA VUNG LA: " + vungten + " [vungId null] ");
								return " Vùng " + vungten + " chưa được khai báo hoặc đã ngưng hoạt động !";
							}

							if (ctkmId == null)
							{
								db.getConnection().rollback();
								return " Scheme " + curscheme + " chưa được khai báo hoặc đã hết thời hạn !";
							}

							String sql = "INSERT INTO DUTRUNGANSACH(CTKM_FK, VUNG_FK, NGANSACH) " +
										 "select '" + ctkmId + "', '" + vungId + "', '" + ngansach + "'";
							System.out.println("[1.Dutrungansach]: " + sql);
							if (!db.update(sql))
							{
								
								sql = "UPDATE DUTRUNGANSACH  SET NGANSACH = '" + ngansach + "' "+
									  "WHERE CTKM_FK = '" + ctkmId + "' AND VUNG_FK = '" + vungId + "' " ;
								
								System.out.println("[2.PhanBoKm]: " + sql);
								if (!db.update(sql))
								{
									db.getConnection().rollback();
									return "Ngân sách dự trù mới của CTKM "+ curscheme + " lỗi. ";
								}
							}
						}
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			db.update("rollback");
			return "Vui lòng coi lại file " + e.getMessage();
		}
		return "";
	}

	private Hashtable<String, String> getHtbCtkmId()
	{
		Hashtable<String, String> ht = new Hashtable<String, String>();
		dbutils db = new dbutils();
		if (db.getConnection() != null)
		{
			// chi duoc lay nhung nhapp trong ctkm_npp tuong ung -- khong lay
			// het

			ResultSet rs = db.get("SELECT SCHEME,PK_SEQ FROM CTKHUYENMAI WHERE DENNGAY >='"+getDateTime()+"' ");
			if (rs != null)
			{
				try
				{
					while (rs.next())
					{
						if (rs.getString("scheme") != null)
						{
							ht.put(rs.getString("scheme"), rs.getString("pk_seq"));
						}
					}
					rs.close();
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			} else
			{
				System.out.print("Error here!");
			}
			db.shutDown();
		} else
		{
			System.out.print("Error here!");
		}
		return ht;
	}

	private Hashtable<String, String> getVung()
	{
		Hashtable<String, String> ht = new Hashtable<String, String>();
		dbutils db = new dbutils();
		if (db.getConnection() != null)
		{
			ResultSet rs = db.get("SELECT TEN, PK_SEQ FROM vung WHERE TRANGTHAI = '1' ");
			if (rs != null)
			{
				try
				{
					while (rs.next())
					{
						if (rs.getString("ten") != null)
						{
							ht.put(rs.getString("ten"), rs.getString("pk_seq"));
						}
					}
					rs.close();
				} 
				catch (Exception e)
				{
					e.printStackTrace();
				}
			} 
			
			db.shutDown();
		} 
		return ht;
	}

	public String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	private String getValue(Sheet sheet, int column, int row)
	{
		Cell cell;
		cell = sheet.getCell(column, row);
		try
		{
			return cell.getContents();
		} catch (Exception er)
		{
			return "0";
		}
	}

}
