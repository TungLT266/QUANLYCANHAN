package geso.traphaco.center.servlets.duyettratrungbay;

import geso.traphaco.center.beans.duyettratrungbay.IDuyettratrungbay;
import geso.traphaco.center.beans.duyettratrungbay.imp.Duyettratrungbay;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class DuyettratrungbaySvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public DuyettratrungbaySvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		Utility util = new Utility();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IDuyettratrungbay dttbBean = new Duyettratrungbay();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		dttbBean.setUserId(userId);
		dttbBean.init();
		session.setAttribute("dttbBean", dttbBean);
		session.setAttribute("schemeId", "0");
		session.setAttribute("userId", userId);
		String nextJSP = "/TraphacoHYERP/pages/Center/DuyetTraTrungBay.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		String contentType = request.getContentType();
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		IDuyettratrungbay dttbBean = new Duyettratrungbay();
		String nextJSP = "/TraphacoHYERP/pages/Center/DuyetTraTrungBay.jsp";

		Utility util = new Utility();
		dttbBean.setUserId(userId);
		if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0))
		{
			String filename = SaveExcel(request);
			if (filename.length() > 0)
			{
				System.out.println("File name: " + filename);
				String schemeId = (String) session.getAttribute("schemeId");
				String lantt = (String) session.getAttribute("lantt");
				System.out.println("Lan thanh toan thu: " + lantt);
				System.out.println("SchemeId: " + schemeId);
				if (lantt == null)
				{
					dttbBean.setSchemeId(schemeId);
					dttbBean.getLanthanhtoan();
					lantt = "" + dttbBean.getLantt();
				}
				readExcel(filename, schemeId, lantt);
			}
		} 
		else
		{
			String schemeId = util.antiSQLInspection(request.getParameter("schemeId"));
			dttbBean.setSchemeId(schemeId);
			session.setAttribute("schemeId", dttbBean.getSchemeId());
			
			String lantt = util.antiSQLInspection(request.getParameter("lantt"));
			session.setAttribute("lantt", lantt);
			/*if (lantt == null)
			{
				dttbBean.getLanthanhtoan();
			} 
			else
			{
				dttbBean.setLantt(Integer.parseInt(lantt));
			}*/
			if(lantt != null)
				dttbBean.setLantt(Integer.parseInt(lantt));
			
			String vungId = util.antiSQLInspection(request.getParameter("vungId"));
			dttbBean.setVungId(vungId);

			String kvId = util.antiSQLInspection(request.getParameter("kvId"));
			dttbBean.setKvId(kvId);

			String nppId = util.antiSQLInspection(request.getParameter("nppId"));
			dttbBean.setNppId(nppId);

			String[] khIds = request.getParameterValues("khIds");
			dttbBean.setKhIds(khIds);

			if (request.getParameter("action").equals("save"))
			{
				System.out.println("___LUU DUYET TRUNG BAY...");
				dttbBean.Luutratb(request);
			}

			if (request.getParameter("action").equals("chot"))
			{
				System.out.println("___CHOT DUYET TRUNG BAY...");
				dttbBean.Chot(request);
			}
		}
		
		dttbBean.init();
		session.setAttribute("dttbBean", dttbBean);
		session.setAttribute("schemeId", dttbBean.getSchemeId());
		session.setAttribute("userId", userId);
		response.sendRedirect(nextJSP);
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

		System.out.println("File la: " + file + "\n");

		// for saving the file name
		// Ben tren da gan schemeId roi.
		/*
		 * String Id = file.substring(file.indexOf("schemeId"));
		 * System.out.println("Gia tri o buoc save la: " + Id); Id =
		 * Id.substring(Id.indexOf("\n")); Id = Id.substring(0, 10);
		 * this.schemeId = Id.trim();
		 */

		String saveFile = file.substring(file.indexOf("filename=\"") + 10);
		saveFile = saveFile.substring(0, saveFile.indexOf("\n"));
		saveFile = saveFile.substring(saveFile.lastIndexOf("\\") + 1, saveFile.indexOf("\""));
		int lastIndex = contentType.lastIndexOf("=");
		String boundary = contentType.substring(lastIndex + 1, contentType.length());
		int pos;
		// extracting the index of file
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
			FileOutputStream fileOut = new FileOutputStream("C:\\upload\\excel\\" + getDateTime() + "-" + saveFile);
			fileOut.write(dataBytes, startPos, (endPos - startPos));
			fileOut.flush();
			fileOut.close();
			return ("C:\\upload\\excel\\" + getDateTime() + "-" + saveFile);
		} 
		else
		{
			return "";
		}
	}

	private void readExcel(String filename, String schemeId, String lanttValue) throws ServletException, IOException
	{
		File file = new File(filename);
		Workbook workbook;
		dbutils db = new dbutils();
		try
		{
			workbook = Workbook.getWorkbook(file);
			Sheet sheet = workbook.getSheet(0);
			int lantt = Integer.parseInt(lanttValue);
			if (lantt > 0)
			{
				System.out.println("So lan: " + lantt);
				String sql = "";
				int indexColCustomerKey = 0;
				int indexColApprovalPay = 0;
				int indexRow = 6;

				indexColCustomerKey = 0;
				indexColApprovalPay = 4;
				for (int i = indexRow; i < sheet.getRows(); ++i)
				{
					Cell[] cells = sheet.getRow(i);
					String CustomerKey = cells[indexColCustomerKey].getContents();
					String ApprovalNumber = cells[indexColApprovalPay].getContents();
					
					System.out.println("----CustomerKey: " + CustomerKey + "  -- ApprovalNumber: " + ApprovalNumber);
					
					if(CustomerKey.trim().length() > 0 && ApprovalNumber.trim().length() > 0)
					{
						sql = "update denghitratb_khachhang set xuatduyet = " + ApprovalNumber
								+ " where khachhang_fk = '" + CustomerKey + "'  "
								+ " and denghitratb_fk in (select pk_seq from denghitratrungbay where cttrungbay_fk='" + schemeId + "' and lanthanhtoan='" + lantt + "')";
						
						System.out.println("Query: " + sql);
						db.update(sql);
					}
				}
			}
		} catch (Exception ex)
		{
			throw new IOException("Khong the doc file Excel" + ex.toString());
		}
	}

	public String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

}
