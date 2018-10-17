package geso.traphaco.center.servlets.phanbotrungbay;

import geso.traphaco.center.beans.phanbotrungbay.IPhanbotrungbay;
import geso.traphaco.center.beans.phanbotrungbay.imp.Phanbotrungbay;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class PhanbotrungbaySvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_DIRECTORY = "c:\\upload\\excel\\";

	public PhanbotrungbaySvl()
	{
		super();
	}

	private String schemeId;
	private Utility util = new Utility();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IPhanbotrungbay pbtbBean = new Phanbotrungbay();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);

		pbtbBean.init();
		session.setAttribute("pbtb", pbtbBean);
		session.setAttribute("schemeId", "0");
		session.setAttribute("userId", userId);
		String nextJSP = "/TraphacoHYERP/pages/Center/PhanBoTrungBay.jsp";
		response.sendRedirect(nextJSP);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		PrintWriter out = response.getWriter();
		String contentType = request.getContentType();
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		IPhanbotrungbay pbtbBean = new Phanbotrungbay();
		String action=request.getParameter("action");
		if(action==null)action="";
		if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0))
		{
			String filename = SaveExcel(request);
			if (filename.length() > 0)
			{

				String nppId = readExcel(filename);
				pbtbBean.setMessage(nppId);
				if (!nppId.equals("0"))
				{
					dbutils db = new dbutils();
					ResultSet rs = db.get("select ten from nhaphanphoi where ma='" + nppId + "'");
					try
					{
						if (rs != null)
						{
							while (rs.next())
							{
								rs.next();
								pbtbBean.setMessage("Khong the cap nhat, vi Nha Phan Phoi " + rs.getString("ten") + " da dung vuot muc ngan sach phan bo moi");
							}
						}
					} catch (Exception e)
					{
						e.printStackTrace();
					}
				}
				String schemeId = (String) session.getAttribute("schemeId");
				pbtbBean.setSchemeId(schemeId);
				pbtbBean.init();
				pbtbBean.setusedPro(this.getUsedPromotion(schemeId));
				session.setAttribute("pbtb", pbtbBean);
				session.setAttribute("schemeId", schemeId);
				session.setAttribute("userId", userId);
				String nextJSP = "/TraphacoHYERP/pages/Center/PhanBoTrungBay.jsp";
				response.sendRedirect(nextJSP);
			} else
			{
				pbtbBean.init();
				session.setAttribute("pbtb", pbtbBean);
				session.setAttribute("schemeId", "0");
				session.setAttribute("userId", userId);
				String nextJSP = "/TraphacoHYERP/pages/Center/PhanBoTrungBay.jsp";
				response.sendRedirect(nextJSP);
			}
		} else if(action.equals("save"))
		{

			String schemeId = request.getParameter("schemeId");
			pbtbBean.setSchemeId(schemeId);
			String vungId = request.getParameter("vungId");
			pbtbBean.setVungId(vungId);

			String kvId = request.getParameter("kvId");
			pbtbBean.setKvId(kvId);
						
			pbtbBean.save(request);
			
			pbtbBean.init();
			pbtbBean.setusedPro(this.getUsedPromotion(schemeId));
			session.setAttribute("pbtb", pbtbBean);
			session.setAttribute("schemeId", schemeId);
			session.setAttribute("userId", userId);
			String nextJSP = "/TraphacoHYERP/pages/Center/PhanBoTrungBay.jsp";
			response.sendRedirect(nextJSP);
		}else 
		{
			String schemeId = request.getParameter("schemeId");
			pbtbBean.setSchemeId(schemeId);
			String vungId = request.getParameter("vungId");
			pbtbBean.setVungId(vungId);

			String kvId = request.getParameter("kvId");
			pbtbBean.setKvId(kvId);

			pbtbBean.init();
			pbtbBean.setusedPro(this.getUsedPromotion(schemeId));
			session.setAttribute("pbtb", pbtbBean);
			session.setAttribute("schemeId", schemeId);
			session.setAttribute("userId", userId);
			String nextJSP = "/TraphacoHYERP/pages/Center/PhanBoTrungBay.jsp";
			response.sendRedirect(nextJSP);
		}
	}

	private String SaveExcel(HttpServletRequest request) throws ServletException, IOException
	{
		String contentType = request.getContentType();
		DataInputStream in = new DataInputStream(request.getInputStream());
		// we are taking the length of Content type data
		int formDataLength = request.getContentLength();
		byte dataBytes[] = new byte[formDataLength];
		int byteRead = 0;
		int totalBytesRead = 0;
		// this loop converting the uploaded file into byte code
		while (totalBytesRead < formDataLength)
		{
			byteRead = in.read(dataBytes, totalBytesRead, formDataLength);
			totalBytesRead += byteRead;
		}

		String file = new String(dataBytes);

		// for saving the file name
		String Id = file.substring(file.indexOf("schemeId"));
		Id = Id.substring(Id.indexOf("\n"));
		Id = Id.substring(0, 10);
		this.schemeId = Id.trim();

		String saveFile = file.substring(file.indexOf("filename=\"") + 10);
		saveFile = saveFile.substring(0, saveFile.indexOf("\n"));
		saveFile = saveFile.substring(saveFile.lastIndexOf("\\") + 1, saveFile.indexOf("\""));
		int lastIndex = contentType.lastIndexOf("=");
		String boundary = contentType.substring(lastIndex + 1, contentType.length());
		int pos;
		pos = file.indexOf("filename=\"");
		pos = file.indexOf("\n", pos) + 1;
		pos = file.indexOf("\n", pos) + 1;
		pos = file.indexOf("\n", pos) + 1;
		int boundaryLocation = file.indexOf(boundary, pos) - 4;
		int startPos = ((file.substring(0, pos)).getBytes()).length;
		int endPos = ((file.substring(0, boundaryLocation)).getBytes()).length;

		if (saveFile.length() > 0)
		{
			// creating a new file with the same name and writing the content in
			// new file
			FileOutputStream fileOut = new FileOutputStream(UPLOAD_DIRECTORY + "-" + saveFile);
			fileOut.write(dataBytes, startPos, (endPos - startPos));
			fileOut.flush();
			fileOut.close();
			return (UPLOAD_DIRECTORY + "-" + saveFile);
		} else
		{
			return "";
		}
	}

	private String readExcel(String filename) throws ServletException, IOException
	{
		File inputWorkbook = new File(filename);
		Workbook w;
		Hashtable ht = this.getNPP();
		Hashtable usedPro = this.getUsedPromotion(this.schemeId);
		dbutils db = new dbutils();
		try
		{
			w = Workbook.getWorkbook(inputWorkbook);
			// Get the first sheet
			Sheet sheet = w.getSheet(0);

			for (int i = 3; i < sheet.getRows(); i++)
			{
				Cell cell0 = sheet.getCell(0, i);
				Cell cell1 = sheet.getCell(1, i);
				Cell cell2 = sheet.getCell(2, i);
				Cell cell3 = sheet.getCell(3, i);
				if ((cell0.getContents().length() == 0 || cell0.getContents().contains("AREA")) & !cell0.getContents().contains("Total") & !cell1.getContents().contains("Ma NPP")
						& !cell0.getContents().contains("Grand"))
				{
					String nppId = (String) ht.get(cell1.getContents().trim());
					
					if (nppId == null)
					{
						System.out.println("[nppId]" + nppId);
						return "Mã nhà phân phối " + cell1.getContents() +  " không có trong hệ thống hoặc đã ngưng hoạt động !";
					}
					String sql = "";
					if (nppId != null)
					{
						if (usedPro.containsKey(nppId))
						{
							if (Float.valueOf(cell3.getContents()).floatValue() > Float.valueOf((String) usedPro.get(nppId)).floatValue())
							{
								sql = "INSERT INTO NHOMCTTRUNGBAY_NPP(NHOMCTTRUNGBAY_FK,NPP_FK,NGANSACH,dasudung) values('" + this.schemeId + "','" + nppId + "','" + cell3.getContents().replace(",", "") + "', 0)";
								if (!db.update(sql))
								{
									sql = "update NHOMCTTRUNGBAY_NPP set ngansach='" + cell3.getContents().replace(",", "") + "' where NHOMCTTRUNGBAY_FK='" + this.schemeId + "' and npp_fk='" + (String) ht.get(cell1.getContents())+ "'";
									db.update(sql);
								}
							} else
							{
								return nppId;
							}
						} else
						{
							sql = "insert into NHOMCTTRUNGBAY_NPP values('" + this.schemeId + "','" + nppId + "','" + cell3.getContents().replace(",", "") + "', 0)";
							if (!db.update(sql))
							{
								sql = "update NHOMCTTRUNGBAY_NPP set ngansach='" + cell3.getContents().replace(",", "") + "' where NHOMCTTRUNGBAY_FK='" + this.schemeId + "' and npp_fk='" + (String) ht.get(cell1.getContents())+ "'";
								db.update(sql);
							}
						}
					}
				}
			}
		} catch (BiffException e)
		{
			e.printStackTrace();
		}
		return "0";
	}

	private Hashtable<String, String> getNPP()
	{
		Hashtable<String, String> ht = new Hashtable<String, String>();
		dbutils db = new dbutils();
		if (db.getConnection() != null)
		{
			ResultSet rs = db.get("select pk_seq, ma from nhaphanphoi where trangthai='1'");
			if (rs != null)
			{
				try
				{
					while (rs.next())
					{
						if (rs.getString("ma") != null)
						{
							ht.put(rs.getString("ma"), rs.getString("pk_seq"));
						}
					}
				} catch (Exception e)
				{
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

	private Hashtable<String, String> getUsedPromotion(String schemeId)
	{
		Hashtable<String, String> ht = new Hashtable<String, String>();
		dbutils db = new dbutils();
		if (db.getConnection() != null)
		{
			String sql=
					" SELECT DN.NPP_FK AS NPPID, SUM(XUATDENGHI) AS AMOUNT "+  
					" FROM DENGHITRATRUNGBAY DN  "+
					"	INNER JOIN CTTRUNGBAY TB ON TB.PK_SEQ=DN.CTTRUNGBAY_FK  "+
					"	INNER JOIN NHOMCTTRUNGBAY NTB ON NTB.PK_SEQ=TB.NHOMCTTB_FK "+
					"	INNER JOIN DENGHITRATB_KHACHHANG TRA ON DN.PK_SEQ=TRA.DENGHITRATB_FK "+
					" WHERE DN.TRANGTHAI = 1  AND NTB.PK_SEQ='"+schemeId+"' "+ 
					"  GROUP BY NPP_FK ";
			ResultSet rs = db.get(sql);
			if (rs != null)
			{
				try
				{
					while (rs.next())
					{
						if (rs.getString("nppId") != null)
						{
							ht.put(rs.getString("nppId"), rs.getString("amount"));
						}
					}

				} catch (Exception e)
				{
				}
			} else
			{
				System.out.print("Error in getUsedPromotion/rs==null");
			}
			db.shutDown();
		} else
		{
			System.out.print("Error in getUsedPromotion/rs==null");
		}

		return ht;

	}

	public String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

}
