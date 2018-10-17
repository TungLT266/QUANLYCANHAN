package geso.traphaco.distributor.servlets.chitieunpp;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.beans.chitieunpp.IChitieuSKUInList;
import geso.traphaco.distributor.beans.chitieunpp.IChitieuSKUInTT;
import geso.traphaco.distributor.beans.chitieunpp.IChitieusku;
import geso.traphaco.distributor.beans.chitieunpp.imp.ChitieuSKUInList;
import geso.traphaco.distributor.beans.chitieunpp.imp.ChitieuSKUInTT;
import geso.traphaco.distributor.beans.chitieunpp.imp.ChitieuSku;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;



public class ChiTieuSKUInUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private PrintWriter out;

	public ChiTieuSKUInUpdateSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		IChitieuSKUInTT ctskuBean;
		HttpSession session = request.getSession();

		this.out = response.getWriter();
		Utility util = new Utility();

		String querystring = request.getQueryString();
		String action = util.getAction(querystring);
		String id = util.getId(querystring);
		String userId = (String) session.getAttribute("userId");

		String task = request.getParameter("view");
		if (task == null)
			task = "";

		ctskuBean = new ChitieuSKUInTT(id);
		ctskuBean.setUserId(userId);
		if (task.equals("NPP"))
		{
			ctskuBean.init();
		} else
		{
			ctskuBean.initnpp();
		}

		session.setAttribute("obj", ctskuBean);
		String nextJSP = "";
		System.out.print("action " + action);
		if (action.equals("xem"))
		{
			if (task.equals("NPP"))
			{
				nextJSP = "/TraphacoHYERP/pages/Distributor/ChiTieuSKUInDisplay.jsp";
			} else
				nextJSP = "/TraphacoHYERP/pages/Center/ChiTieuSKUInDisplayNpp.jsp";
		} else
		{
			nextJSP = "/TraphacoHYERP/pages/Center/ChiTieuSKUInUpdateNpp.jsp";
		}
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String contentType = request.getContentType();
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();

		IChitieuSKUInTT ctskuBean;
		Utility util = new Utility();
		String id = util.antiSQLInspection(request.getParameter("id"));
		if (id == null)
		{
			ctskuBean = new ChitieuSKUInTT();
		} else
		{
			ctskuBean = new ChitieuSKUInTT(id);
		}
		String userId = (String) session.getAttribute("userId");
		ctskuBean.setUserId(userId);
		ctskuBean.setNppIds(util.getIdNhapp(userId));
		String err = "";
		if ((contentType != null)
				&& (contentType.indexOf("multipart/form-data") >= 0))
		{

			String filename = SaveExcel(request, ctskuBean);

			if (filename.length() > 0)
			{
				if (!readExcel(filename, userId, ctskuBean))
				{

					String nextJSP = "";
					if (ctskuBean.getId().length() != 0)
					{
						nextJSP = "/TraphacoHYERP/pages/Center/ChiTieuSKUInUpdateNpp.jsp";

					} else
						nextJSP = "/TraphacoHYERP/pages/Center/ChiTieuSKUInNewNpp.jsp";
					ctskuBean
							.setMsg("Đọc file không thành công(file không đúng định dạng hoặc file sai vui lòng xem lại)!");
					ctskuBean.setUserId(userId);
					ctskuBean.createRs();
					session.setAttribute("obj", ctskuBean);
					response.sendRedirect(nextJSP);
					return;
				} else
				{
					String nextJSP = "";
					System.out
							.println("ctskuBean.getId() " + ctskuBean.getId());
					if (ctskuBean.getId().length() != 0)
					{
						nextJSP = "/TraphacoHYERP/pages/Center/ChiTieuSKUInUpdateNpp.jsp";

					} else
						nextJSP = "/TraphacoHYERP/pages/Center/ChiTieuSKUInNewNpp.jsp";

					int a = ctskuBean.getSpList().size();
					if (a == 0)
						ctskuBean.setMsg("Không có dữ liệu");
					else
						ctskuBean.setMsg("Đọc file thành công");
					ctskuBean.setUserId(userId);
					session.setAttribute("obj", ctskuBean);
					ctskuBean.createRs();
					response.sendRedirect(nextJSP);
					return;
				}
			} else
			{

				String nextJSP = "/TraphacoHYERP/pages/Center/ChiTieuSKUInNewNpp.jsp";
				ctskuBean.setMsg("Upload không thành công");
				ctskuBean.setUserId(userId);
				session.setAttribute("obj", ctskuBean);
				response.sendRedirect(nextJSP);
			}
		} else
		{
			String diengiai = util.antiSQLInspection(request
					.getParameter("diengiai"));
			if (diengiai == null)
				diengiai = "";
			ctskuBean.setDiengiai(diengiai);

			String thang = util
					.antiSQLInspection(request.getParameter("thang"));
			if (thang == null)
				thang = "";
			ctskuBean.setThang(thang);

			String nam = util.antiSQLInspection(request.getParameter("namxx"));
			if (nam == null)
				nam = "";
			ctskuBean.setNam(nam);

			String kenhbanhang = util.antiSQLInspection(request
					.getParameter("kenhbanhang"));
			if (kenhbanhang == null)
				kenhbanhang = "";
			ctskuBean.setKbhId(kenhbanhang);

			String donvikinhdoanh = util.antiSQLInspection(request
					.getParameter("donvikinhdoanh"));
			if (donvikinhdoanh == null)
				donvikinhdoanh = "";
			ctskuBean.setDvkdId(donvikinhdoanh);
			Hashtable<String, String> htpNpp = this.get_htpNpp();
			String[] masp = request.getParameterValues("masp");
			String[] npp = request.getParameterValues("npp");
			String[] tensp = request.getParameterValues("tensp");
			String[] soluong = request.getParameterValues("soluong");
			List<IChitieusku> ctList = new ArrayList<IChitieusku>();

			for (int i = 0; i < masp.length; i++)
			{
				if (htpNpp.containsKey(npp[i].trim()))
				{
					IChitieusku sp = null;
					sp = new ChitieuSku();
					sp.setNpp(npp[i]);
					sp.setMasanpham(masp[i]);
					sp.setTensanpham(tensp[i]);
					sp.setNppten(htpNpp.get(npp[i].trim()));
					// System.out.println("Ma san pham : " + masp[i]
					// +" . So Luong Am : "+soluong[i]);
					sp.setSoluong(soluong[i]);
					ctList.add(sp);

				} else
				{
					err = err + "," + npp[i];
				}
			}
			ctskuBean.setSpList(ctList);
		}

		if (err.length() > 0)
		{
			ctskuBean.setMsg("Không tồn tại mã nhà phân phối sau :" + err);
			if (id.length() == 0)
			{
				System.out.println("loi khi insert()");
				ctskuBean.setUserId(userId);
				ctskuBean.createRs();
				session.setAttribute("obj", ctskuBean);
				String nextJSP = "/TraphacoHYERP/pages/Center/ChiTieuSKUInNewNpp.jsp";

				response.sendRedirect(nextJSP);
			} else
			{
				ctskuBean.setUserId(userId);
				ctskuBean.createRs();
				session.setAttribute("obj", ctskuBean);
				String nextJSP = "/TraphacoHYERP/pages/Center/ChiTieuSKUInUpdateNpp.jsp";

				response.sendRedirect(nextJSP);
			}
			return;
		}

		String action = request.getParameter("action");
		if (action.equals("save"))
		{
			if (id.length() == 0)
			{
				if (!ctskuBean.createChiTieuSkuin())
				{
					System.out.println("loi khi insert()");
					ctskuBean.setUserId(userId);
					ctskuBean.createRs();
					session.setAttribute("obj", ctskuBean);
					String nextJSP = "/TraphacoHYERP/pages/Center/ChiTieuSKUInNewNpp.jsp";

					response.sendRedirect(nextJSP);
				} else
				{
					System.out.println("da cap nay duoc roi");
					IChitieuSKUInList obj = new ChitieuSKUInList();
					obj.setUserId(userId);
					obj.init("");
					session.setAttribute("obj", obj);
					String nextJSP = "/TraphacoHYERP/pages/Center/ChiTieuSKUInNpp.jsp";

					response.sendRedirect(nextJSP);
				}
			} else
			{
				if (!(ctskuBean.updateChiTieuSkuin()))
				{
					ctskuBean.setUserId(userId);
					ctskuBean.createRs();
					session.setAttribute("obj", ctskuBean);
					String nextJSP = "/TraphacoHYERP/pages/Center/ChiTieuSKUInUpdateNpp.jsp";

					response.sendRedirect(nextJSP);
				} else
				{
					IChitieuSKUInList obj = new ChitieuSKUInList();
					obj.setUserId(userId);
					obj.init("");
					session.setAttribute("obj", obj);
					String nextJSP = "/TraphacoHYERP/pages/Center/ChiTieuSKUInNpp.jsp";

					response.sendRedirect(nextJSP);
				}
			}
		}
	}

	private Hashtable<String, String> get_htpNpp()
	{
		Hashtable<String, String> htpnpp = new Hashtable<String, String>();
		try
		{
			dbutils db = new dbutils();
			String sql = "select pk_seq,ten from nhaphanphoi";
			ResultSet rs = db.get(sql);
			while (rs.next())
			{
				htpnpp.put(rs.getString("pk_seq").trim(), rs.getString("ten"));
			}
			rs.close();
			db.shutDown();
		} catch (Exception err)
		{
			System.out.println(err.toString());
		}
		return htpnpp;
	}

	public String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	private boolean readExcel(String filename, String userId,
			IChitieuSKUInTT nhaphang) throws ServletException, IOException
	{
		Hashtable<String, String> htpNpp = this.get_htpNpp();
		File file = new File(filename);
		Workbook workbook = null;
		int indexRow = 0;
		int j = 0;
		try
		{

			workbook = Workbook.getWorkbook(file);
			Sheet sheet = workbook.getSheet(0);
			boolean flat = false;
			List<IChitieusku> ctList = new ArrayList<IChitieusku>();
			for (int i = indexRow + 5; i < sheet.getRows(); ++i)
			{
				Cell[] cells = sheet.getRow(i);
				if (cells[j].getContents().equals("Total"))
				{
					flat = true;
				}
				if (flat)
					break;

				IChitieusku sp = null;

				sp = new ChitieuSku();
				sp.setNpp(cells[j].getContents());
				sp.setNppten(htpNpp.get(cells[j].getContents().toString()));
				sp.setMasanpham(cells[j + 1].getContents());
				sp.setTensanpham(cells[j + 2].getContents());
				double soluong = 0;
				try
				{
					soluong = Double.parseDouble(cells[j + 3].getContents()
							.replace(",", ""));
				} catch (Exception er)
				{

				}
				sp.setSoluong(Math.round(soluong) + "");
				ctList.add(sp);
				j = 0;

			}
			nhaphang.setSpList(ctList);

			workbook.close();
			return true;
		} catch (Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}

	private String SaveExcel(HttpServletRequest request,
			IChitieuSKUInTT nhaphang) throws ServletException, IOException
	{

		String contentType = request.getContentType();
		DataInputStream in = new DataInputStream(request.getInputStream());
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
		String file = new String(dataBytes, "UTF-8");
		String diengiai = file.substring(file.indexOf("diengiai"));// indexOf la
																	// lenh lay
																	// den vi
																	// tri
		diengiai = diengiai.substring(diengiai.indexOf("\n"));// vi tri xuong
																// dong
		diengiai = diengiai.substring(0, diengiai.indexOf("-")).trim();
		nhaphang.setDiengiai(diengiai);
		System.out.println("diengiai :" + diengiai);
		//
		String id = file.substring(file.indexOf("id"));// indexOf la lenh lay den vi tri
		id = id.substring(id.indexOf("\n"));// vi tri xuong dong
		id = id.substring(0, id.indexOf("-")).trim();
		if (id.length() != 0)
			nhaphang.setId(id);
		System.out.println("id :" + id);
		String thang = file.substring(file.indexOf("thang"));// indexOf la lenh
															
		thang = thang.substring(thang.indexOf("\n"));// vi tri xuong dong
		thang = thang.substring(0, thang.indexOf("-")).trim();
		nhaphang.setThang(thang);
		System.out.println("thang :" + nhaphang.getThang());
		String nam = file.substring(file.indexOf("namxx"));// indexOf la lenh
															// lay den vi tri
		nam = nam.substring(nam.indexOf("\n"));// vi tri xuong dong
		nam = nam.substring(0, nam.indexOf("-")).trim();

		nhaphang.setNam(nam);
		System.out.println("namxx :" + nhaphang.getNam());
		String kenhbanhang = file.substring(file.indexOf("kenhbanhang"));// indexOf
																		
		kenhbanhang = kenhbanhang.substring(kenhbanhang.indexOf("\n"));// vi tri
																
		kenhbanhang = kenhbanhang.substring(0, kenhbanhang.indexOf("-")).trim();
		nhaphang.setKbhId(kenhbanhang);
		System.out.println("kenhbanhang :" + nhaphang.getKbhId());
		String donvikinhdoanh = file.substring(file.indexOf("donvikinhdoanh"));// indexOf
																				
		donvikinhdoanh = donvikinhdoanh.substring(donvikinhdoanh.indexOf("\n"));// vi
																			
		donvikinhdoanh = donvikinhdoanh.substring(0,
				donvikinhdoanh.indexOf("-")).trim();

		nhaphang.setDvkdId(donvikinhdoanh);
		System.out.println("donvikinhdoanh :" + nhaphang.getDvkdId());
		//
		file = new String(dataBytes);
		String saveFile = file.substring(file.indexOf("filename=\"") + 10);
		saveFile = saveFile.substring(0, saveFile.indexOf("\n"));
		saveFile = saveFile.substring(saveFile.lastIndexOf("\\") + 1,
				saveFile.indexOf("\""));
		int lastIndex = contentType.lastIndexOf("=");
		String boundary = contentType.substring(lastIndex + 1,
				contentType.length());
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
			FileOutputStream fileOut = new FileOutputStream(
					"C:\\java-tomcat\\data\\" + getDateTime() + "-" + saveFile);
			fileOut.write(dataBytes, startPos, (endPos - startPos));
			fileOut.flush();
			fileOut.close();
			return ("C:\\java-tomcat\\data\\" + getDateTime() + "-" + saveFile);
		} else
		{
			return "";
		}
	}
}
