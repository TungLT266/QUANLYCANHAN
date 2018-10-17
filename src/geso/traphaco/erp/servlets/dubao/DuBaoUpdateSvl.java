package geso.erp.servlets.dubao;

import geso.dms.db.sql.dbutils;
import geso.dms.distributor.util.Utility;
import geso.erp.beans.dubaokinhdoanh.IDubaokinhdoanh;
import geso.erp.beans.dubaokinhdoanh.imp.Dubaokinhdoanh;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class DuBaoUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_DIRECTORY = "C:\\upload\\excel\\";

	public DuBaoUpdateSvl()
	{
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if (!cutil.check(userId, userTen, sum))
		{
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		} else
		{
			session.setMaxInactiveInterval(30000);

			Utility util = new Utility();

			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);
			String ctyId = (String)session.getAttribute("congtyId");
			
			if (userId.length() == 0)
				userId = util.antiSQLInspection(request.getParameter("userId"));
			String id = util.getId(querystring);
			Dubaokinhdoanh dbkdBean = new Dubaokinhdoanh(id);
			dbkdBean.setCtyId(ctyId);
			
			dbkdBean.setUserId(userId);
			String nextJSP;

			if (request.getQueryString().indexOf("display") >= 0)
			{
				dbkdBean.init();
				nextJSP = "/TraphacoHYERP/pages/Erp/Erp_DuBaoDisplay.jsp";
			} else
			{
				dbkdBean.init();
				nextJSP = "/TraphacoHYERP/pages/Erp/Erp_DuBaoUpdate.jsp";
			}
			session.setAttribute("dbkdBean", dbkdBean);
			response.sendRedirect(nextJSP);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IDubaokinhdoanh dbkdBean;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html ; charset=UTF-8");
		String contentType = request.getContentType();
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String ctyId = (String)session.getAttribute("congtyId");
		
		Utility util = new Utility();
		String nextJSP="";
		String action = "";
		//Khi upload file file phai dung MultipartRequest de lay parameter
		if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0))
		{
			MultipartRequest multi = new MultipartRequest(request, UPLOAD_DIRECTORY, 20000000, "UTF-8");
			action = util.antiSQLInspection(multi.getParameter("action"));
			if (action == null)
				action = "";
			System.out.println("Action Request encrypt" + action);
			String id = util.antiSQLInspection(multi.getParameter("id"));
			if (id == null)
			{
				dbkdBean = new Dubaokinhdoanh();
			} else
			{
				dbkdBean = new Dubaokinhdoanh(id);
			}
			
			dbkdBean.setCtyId(ctyId);
			dbkdBean.setUserId(userId);
			getRequestEcrypt(util, dbkdBean, multi);
			Enumeration files = multi.getFileNames();
			String filename = "";
			while (files.hasMoreElements())
			{
				String name = (String) files.nextElement();
				filename = multi.getFilesystemName(name);
				System.out.println("File  " + UPLOAD_DIRECTORY + filename);
			}
			if (filename!=null&&filename.length() > 0)
				this.readExcel(dbkdBean, UPLOAD_DIRECTORY + filename);
		} else
		{
			action = util.antiSQLInspection(request.getParameter("action"));
			if (action == null)
				action = "";
			System.out.println("Action " + action);
			String id = util.antiSQLInspection(request.getParameter("id"));
			if (id == null)
			{
				dbkdBean = new Dubaokinhdoanh();				
			} else
			{
				dbkdBean = new Dubaokinhdoanh(id);
			}
			
			dbkdBean.setCtyId(ctyId);
			dbkdBean.setUserId(userId);

			//Lấy thông tin 
			getRequest(util, dbkdBean, request);
		
			if (action.equals("new"))
			{
		
				if (!dbkdBean.CreateDubao(request))
				{
					dbkdBean.setMsg("Cập nhật dự báo kinh doanh không thành công");
				} else
				{
					dbkdBean.setMsg("Du lieu da duoc luu thanh cong");
				}
			} else
			{
				if (!dbkdBean.update(request))
				{
					dbkdBean.setMsg("Cập nhật dự báo kinh doanh không thành công");
				}
			}
		}
		dbkdBean.createRs();
		nextJSP = "/TraphacoHYERP/pages/Erp/Erp_DuBaoUpdate.jsp";
		session.setAttribute("dbkdBean", dbkdBean);
		response.sendRedirect(nextJSP);

	}

	public void readExcel(IDubaokinhdoanh obj, String fileName) throws IOException
	{
		// String inputFile = "C://File_Upload_SalesForecast.xls";
		File inputWorkbook = new File(fileName);
		Workbook w;
		try
		{
			dbutils db = new dbutils();

			w = Workbook.getWorkbook(inputWorkbook);
			Sheet sheet = w.getSheet(0);
			int sodong = sheet.getRows();
			int socot = sheet.getColumns();
			
			int sothang = socot - 4;
			System.out.println("So dong "+sodong+"socot "+socot+"So thang_" + sothang);

			for (int j = 1; j < socot; j++)
			{
				for (int i = 4; i < sodong; i++)
				{
					Cell cell = sheet.getCell(j, i);
					String dukienBan = "";
					String soNgayBan = "";
					String maSp = "";
					int thang = 0;
					int nam = 0;

					cell = sheet.getCell(j, 4);
					if (j >= 4 && !cell.getContents().equals(""))
					{
						thang = Integer.parseInt(cell.getContents().split("-")[0]);
						nam = Integer.parseInt(cell.getContents().split("-")[1]);
						// System.out.println("thang [ " + i + "][" + j + "]" +cell.getContents());
					}

					cell = sheet.getCell(j, 5);
					if (j >= 4 && !cell.getContents().equals(""))
					{
						soNgayBan = cell.getContents();
						// System.out.println("So Ngay ban [ " + i + "][" + j +"]" + cell.getContents());
					}
					cell = sheet.getCell(2, i);
					if (i >= 7 && !cell.getContents().equals(""))
					{
						maSp = cell.getContents();
						// System.out.println("SanPham [ " + i + "][" + j + "]" + cell.getContents());
					}

					if (j >= 4 && i >= 7)
					{
						cell = sheet.getCell(j, i);
						if (!cell.getContents().equals(""))
						{
							dukienBan = cell.getContents().replace(",", "");
						}
					}

					try
					{
						String query = "";
						if (dukienBan.trim().length() > 0 && maSp.trim().length() > 0 && thang > 0)
							query = "Update Erp_DuBaoSanPham Set DuKienBan='" + dukienBan + "',SoNgayBanHang=" + soNgayBan + " " +
									"WHERE SANPHAM_FK=(SELECT PK_SEQ FROM SANPHAM WHERE MA='" + maSp + "') AND THANG=" + thang + " and nam=" + nam + " and DUBAO_FK='" + obj.getId() + "'";
						if (query.length() > 0)
						{
							if (!db.update(query))
							{
								System.out.println("Loi khong the cap nhat  " + query);

							} else
							{
								//count++;
							//	System.out.println("Cap nhat thanh cong " + count + query);
							}
						}
					} catch (Exception e)
					{
						obj.setMsg("Loi doc file Excel" + e.getMessage());
					}
				}
			}
			db.shutDown();
		} catch (BiffException e)
		{
			obj.setMsg("Loi doc file Excel" + e.getMessage());
	
		}
	}

	public static void main(String[] args) throws IOException
	{

		DuBaoUpdateSvl db = new DuBaoUpdateSvl();
		IDubaokinhdoanh obj = new Dubaokinhdoanh();
		db.readExcel(obj, "C://File_Upload_SalesForecast.xls");
	}

	void getRequest(Utility util, IDubaokinhdoanh dbkdBean, HttpServletRequest request)
	{
		String chungloai = util.antiSQLInspection(request.getParameter("chungloai"));
		if (chungloai == null)
			chungloai = "";
		dbkdBean.setChungloai(chungloai);

		String nhanhang = util.antiSQLInspection(request.getParameter("nhanhang"));
		if (nhanhang == null)
			nhanhang = "";
		dbkdBean.setNhanhang(nhanhang);

		String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
		if (diengiai == null)
			diengiai = "";
		dbkdBean.setDiengiai(diengiai);
		
		String kho = util.antiSQLInspection(request.getParameter("kho"));
		if (kho == null)
			kho = "";
		dbkdBean.setKho(kho);
		String sothangdubao = util.antiSQLInspection(request.getParameter("sothangdubao"));

		if (sothangdubao == null)
			sothangdubao = "";
		dbkdBean.setSothangdubao(sothangdubao);

		String songaytonkho = util.antiSQLInspection(request.getParameter("ngaytonkho"));

		if (songaytonkho == null || songaytonkho.length() == 0)
			songaytonkho = "0";
		dbkdBean.setNgaytonkho(songaytonkho);

		String[] spIds = request.getParameterValues("spId");
		dbkdBean.setSanPhamIds(spIds);

		// String[] selectedSpIds = request.getParameterValues("spIds");
		// dbkdBean.setSelectedSpIds(selectedSpIds);

	}

	void getRequestEcrypt(Utility util, IDubaokinhdoanh dbkdBean, MultipartRequest request)
	{

		String chungloai = util.antiSQLInspection(request.getParameter("chungloai"));
		if (chungloai == null)
			chungloai = "";
		dbkdBean.setChungloai(chungloai);

		String nhanhang = util.antiSQLInspection(request.getParameter("nhanhang"));
		if (nhanhang == null)
			nhanhang = "";
		dbkdBean.setNhanhang(nhanhang);

		String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
		if (diengiai == null)
			diengiai = "";
		dbkdBean.setDiengiai(diengiai);

		/*
		 * String
		 * ngaydubao=util.antiSQLInspection(request.getParameter("ngaydubao"));
		 * if(ngaydubao==null) ngaydubao=""; dbkdBean.setNgaydubao(ngaydubao);
		 */

		String filename = request.getParameter("filename");
		if (filename == null)
			filename = "";

		String kho = util.antiSQLInspection(request.getParameter("kho"));
		if (kho== null)
			kho = "";
		dbkdBean.setKho(kho);
	

		String sothangdubao = util.antiSQLInspection(request.getParameter("sothangdubao"));

		if (sothangdubao == null)
			sothangdubao = "";
		dbkdBean.setSothangdubao(sothangdubao);

		String songaytonkho = util.antiSQLInspection(request.getParameter("ngaytonkho"));

		if (songaytonkho == null || songaytonkho.length() == 0)
			songaytonkho = "0";
		dbkdBean.setNgaytonkho(songaytonkho);

		String[] spIds = request.getParameterValues("spId");
		dbkdBean.setSanPhamIds(spIds);

		// String[] selectedSpIds = request.getParameterValues("spIds");
		// dbkdBean.setSelectedSpIds(selectedSpIds);
	}
}