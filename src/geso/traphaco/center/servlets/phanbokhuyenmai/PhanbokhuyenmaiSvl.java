package geso.traphaco.center.servlets.phanbokhuyenmai;

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

public class PhanbokhuyenmaiSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_DIRECTORY = "c:\\upload\\excel\\";

	public PhanbokhuyenmaiSvl()
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
		IPhanbokhuyenmai pbkmBean = new Phanbokhuyenmai();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);

		pbkmBean.init();
		session.setAttribute("pbkm", pbkmBean);
		session.setAttribute("schemeId", "0");
		session.setAttribute("userId", userId);
		String nextJSP = "/TraphacoHYERP/pages/Center/PhanBoKhuyenMai.jsp";
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
		IPhanbokhuyenmai pbkmBean = new Phanbokhuyenmai();

		String action = request.getParameter("action");

		if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0))
		{
			MultipartRequest multi = new MultipartRequest(request, UPLOAD_DIRECTORY, 20000000, "UTF-8");
			String ctkmId = "";
			String[] ctkmIds = multi.getParameterValues("ctkmId");
			if (ctkmIds != null)
			{
				for (int i = 0; i < ctkmIds.length; i++)
					ctkmId += ctkmIds[i] + ",";
				if (ctkmId.length() > 0)
					ctkmId = ctkmId.substring(0, ctkmId.length() - 1);
			}
			pbkmBean.setSchemeId(ctkmId);

			String vungId = util.antiSQLInspection(multi.getParameter("vungId"));
			pbkmBean.setVungId(vungId);

			String kvId = util.antiSQLInspection(multi.getParameter("khuvucId"));
			pbkmBean.setKvId(kvId);

			String tungay = util.antiSQLInspection(multi.getParameter("tungay"));
			pbkmBean.setTungay(tungay);

			String denngay = util.antiSQLInspection(multi.getParameter("denngay"));
			pbkmBean.setDenngay(denngay);
			
			
			String loaingansach = util.antiSQLInspection(multi.getParameter("loaingansach"));
			pbkmBean.setLoaingansach(loaingansach);

			String nppId = request.getParameter("nppId");
			if (nppId == null)
				nppId = "";
			pbkmBean.setNppId(nppId);

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
			String nextJSP = "/TraphacoHYERP/pages/Center/PhanBoKhuyenMai.jsp";
			response.sendRedirect(nextJSP);

		} 
		else if (action.equals("phanbo"))
		{
			String vungId = util.antiSQLInspection(request.getParameter("vungId"));
			pbkmBean.setVungId(vungId);

			String kvId = util.antiSQLInspection(request.getParameter("khuvucId"));
			pbkmBean.setKvId(kvId);

			String tungay = util.antiSQLInspection(request.getParameter("tungay"));
			pbkmBean.setTungay(tungay);

			String denngay = util.antiSQLInspection(request.getParameter("denngay"));
			pbkmBean.setDenngay(denngay);

			String phanbo = util.antiSQLInspection(request.getParameter("phanbo"));
			pbkmBean.setPhanbo(phanbo);
			
			String loaingansach = util.antiSQLInspection(request.getParameter("loaingansach"));
			pbkmBean.setLoaingansach(loaingansach);

			String nppId = request.getParameter("nppId");
			if (nppId == null)
				nppId = "";
			pbkmBean.setNppId(nppId);

			String ctkmId = "";
			String[] ctkmIds = request.getParameterValues("ctkmId");
			if (ctkmIds != null)
			{
				for (int i = 0; i < ctkmIds.length; i++)
					ctkmId += ctkmIds[i] + ",";
				if (ctkmId.length() > 0)
					ctkmId = ctkmId.substring(0, ctkmId.length() - 1);
			}
			pbkmBean.setSchemeId(ctkmId);

			dbutils db = new dbutils();
			if (ctkmId.length() > 0)
			{
				String query =  "SELECT NPP_FK, CTKM.PK_SEQ as CTKM_FK, 99999999999, 0, LEVEL_PHANBO  " + 
								"FROM CTKM_NPP NPP INNER JOIN CTKHUYENMAI CTKM  ON CTKM.PK_SEQ=NPP.CTKM_FK AND NPP.CHON=1 AND CTKM.PK_SEQ IN(" + ctkmId+ ")  AND CTKM.LOAINGANSACH=0 ";

				System.out.println("[PhanBo]" + query);

				ResultSet rs = db.get(query);
				if (rs != null)
				{
					try
					{
						db.getConnection().setAutoCommit(false);
						while (rs.next())
						{
							
							String sql = "select count(*) as sodong from PHANBOKHUYENMAI PB where ctkm_fk = '" + ctkmId + "' and npp_fk = '" + rs.getString("npp_fk") + "' ";
							
							ResultSet rsCHECK = db.get(sql);
							int soDONG = 0;
							if(rsCHECK != null)
							{
								if(rsCHECK.next())
									soDONG = rsCHECK.getInt("sodong");
							}
							rsCHECK.close();
							
							if(soDONG <= 0)
							{
								// so quy dinh khong gioi han ngan sach la 9999999999999
								query = "INSERT INTO PHANBOKHUYENMAI(CTKM_FK, NPP_FK, NGANSACH, DASUDUNG, TINHTRANG) " + 
										" values('" + rs.getString("CTKM_FK") + "','" + rs.getString("npp_fk") + "', '99999999999', 0, 0)";
							}
							else
							{
								query = "update phanbokhuyenmai set ngansach = '99999999999' " + 
										" where ctkm_fk = '" + rs.getString("CTKM_FK") + "' and npp_fk='" + rs.getString("npp_fk") + "'";
							}

							if (!db.update(query))
							{
								db.update("rollback");
								pbkmBean.setMessage("Vui lòng liên hệ Admin " + query);
							}
							
						}
						db.getConnection().commit();
						db.getConnection().setAutoCommit(true);
						if (rs != null)
							rs.close();
					} 
					catch (Exception e)
					{
						e.printStackTrace();
						db.update("rollback");
						pbkmBean.setMessage("Vui lòng liên hệ Admin " + e.getMessage());
					}
					pbkmBean.setMessage("Phân bổ thành công !");
				}
			}
			pbkmBean.init();
			session.setAttribute("pbkm", pbkmBean);
			session.setAttribute("userId", userId);
			String nextJSP = "/TraphacoHYERP/pages/Center/PhanBoKhuyenMai.jsp";
			response.sendRedirect(nextJSP);
		}
		else
		{
			String vungId = util.antiSQLInspection(request.getParameter("vungId"));
			pbkmBean.setVungId(vungId);

			String kvId = util.antiSQLInspection(request.getParameter("khuvucId"));
			pbkmBean.setKvId(kvId);

			String tungay = util.antiSQLInspection(request.getParameter("tungay"));
			pbkmBean.setTungay(tungay);

			String denngay = util.antiSQLInspection(request.getParameter("denngay"));
			pbkmBean.setDenngay(denngay);

			String nppId = request.getParameter("nppId");
			if (nppId == null)
				nppId = "";
			pbkmBean.setNppId(nppId);

			String phanbo = util.antiSQLInspection(request.getParameter("phanbo"));
			pbkmBean.setPhanbo(phanbo);
			
			String loaingansach = util.antiSQLInspection(request.getParameter("loaingansach"));
			pbkmBean.setLoaingansach(loaingansach);
			
			String ctkmId = "";
			String[] ctkmIds = request.getParameterValues("ctkmId");
			if (ctkmIds != null)
			{
				for (int i = 0; i < ctkmIds.length; i++)
					ctkmId += ctkmIds[i] + ",";
				if (ctkmId.length() > 0)
					ctkmId = ctkmId.substring(0, ctkmId.length() - 1);
			}
			pbkmBean.setSchemeId(ctkmId);
			
			pbkmBean.init();
			pbkmBean.createSchemeRS();
			pbkmBean.createPhanBoRs();
			session.setAttribute("pbkm", pbkmBean);
			session.setAttribute("userId", userId);
			String nextJSP = "/TraphacoHYERP/pages/Center/PhanBoKhuyenMai.jsp";
			response.sendRedirect(nextJSP);
		}
	}

	private String readExcel(String fileName, IPhanbokhuyenmai pbkmBean)
	{
		dbutils db = new dbutils();
		try
		{
			File inputWorkbook = new File(fileName);
			jxl.Workbook w = null;
			Hashtable<String, String> htbCtkmId = getHtbCtkmId();
			//Hashtable<String, String> htbNppId = getNPP();
			w = jxl.Workbook.getWorkbook(inputWorkbook);
			jxl.Sheet sheet = w.getSheet(0);
			int sodong = sheet.getRows();
			
			db.getConnection().setAutoCommit(false);
			for (int i = 3; i < sodong; i++)
			{
				String scheme = getValue(sheet, 0, i).trim();
				String nppMa = getValue(sheet, 2, i).trim();
				//String nppTen = getValue(sheet, 3, i).trim();
				String ngansach = getValue(sheet, 4, i).trim().replaceAll(",", "");
				String soxuattoida = getValue(sheet, 5, i).trim().replaceAll(",", "");
				if(soxuattoida.trim().length() <= 0)
					soxuattoida = "99999";
				
				//System.out.println("::: GET SCHEME: " + htbCtkmId.get(scheme) );
				if(htbCtkmId.get(scheme) != null )
				{
					System.out.println("-----SCHEME:: " + htbCtkmId.get(scheme) );
					String ctkmId = htbCtkmId.get(scheme).split("_")[0];
					String mucphanpho = htbCtkmId.get(scheme).split("_")[1];
					
					//String nppId = htbNppId.get(nppMa);
					//Hashtable<String, String> htbNpp_Ctkm = get_nhapp_duockhaibao(ctkmId);
	
					if (nppMa.trim().length() > 0 && ctkmId.trim().length() > 0 && ngansach.trim().length() > 0 && !scheme.contains("Total"))
					{
						String nppId = "NULL";
						String nvbhId = "NULL";
						String khId = "NULL";
						String tinhthanhId = "NULL";
						
						if(mucphanpho.equals("0"))
							nppId = " ( select pk_seq from NHAPHANPHOI where maFAST = '" + nppMa + "' or ma = '" + nppMa + "'  ) ";
						else if(mucphanpho.equals("1"))
							nvbhId = " ( select pk_seq from DAIDIENKINHDOANH where maFAST = '" + nppMa + "' ) ";
						else if(mucphanpho.equals("4"))
							tinhthanhId = " ( select pk_seq from TINHTHANH where dbo.ftBodau(TEN) like '%" + util.replaceAEIOU(nppMa) + "%' ) ";
						else
							khId = " ( select pk_seq from KHACHHANG where maFAST = '" + nppMa + "' or ma = '" + nppMa + "' ) ";
						
						String sql = "select count(*) as sodong from PHANBOKHUYENMAI PB where ctkm_fk = '" + ctkmId + "' ";
						if(mucphanpho.equals("0"))
							sql += " AND PB.NPP_FK = " + nppId + "  ";
						else if(mucphanpho.equals("1"))
							sql += " AND PB.NVBH_FK in " + nvbhId + "  ";
						else if(mucphanpho.equals("4"))
							sql += " AND PB.TINHTHANH_FK in " + tinhthanhId + "  ";
						else
							sql += " AND PB.KHACHHANG_FK in " + khId + "  ";
						
						//System.out.println("::: CHECK EXITS: " + sql );
						ResultSet rsCHECK = db.get(sql);
						int soDONG = 0;
						if(rsCHECK != null)
						{
							if(rsCHECK.next())
								soDONG = rsCHECK.getInt("sodong");
						}
						rsCHECK.close();
						
						if( soDONG <= 0 )
						{
							sql = "INSERT INTO PHANBOKHUYENMAI(CTKM_FK, NPP_FK, NVBH_FK, KHACHHANG_FK, TINHTHANH_FK, NGANSACH, SOXUATTOIDA, DASUDUNG, TINHTRANG) " +
								   "select '" + ctkmId + "', " + nppId + ", " + nvbhId + ", " + khId + ", " + tinhthanhId + ", '" + ngansach + "', '" + soxuattoida + "', 0, 0   ";
						}
						else
						{
							sql = "UPDATE PHANBOKHUYENMAI SET NGANSACH = '" + ngansach + "', SOXUATTOIDA = '" + soxuattoida + "' "+
									  "WHERE CTKM_FK = '" + ctkmId + "' " ;
								if(mucphanpho.equals("0"))
									sql += " AND NPP_FK = " + nppId + "  ";
								else if(mucphanpho.equals("1"))
									sql += " AND NVBH_FK in " + nvbhId + "  ";
								else if(mucphanpho.equals("4"))
									sql += " AND TINHTHANH_FK in " + tinhthanhId + "  ";
								else
									sql += " AND KHACHHANG_FK in " + khId + "  ";
						}
						
						System.out.println("[1.PhanBoKm]: " + sql);
						if (!db.update(sql))
						{
							db.getConnection().rollback();
							return "Vui lòng kiểm tra lại dữ liệu dòng : " + i;
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
			// chi duoc lay nhung nhapp trong ctkm_npp tuong ung -- khong lay het
			ResultSet rs = db.get("SELECT SCHEME, PK_SEQ, LEVEL_PHANBO FROM CTKHUYENMAI WHERE LOAINGANSACH = 1  ");
			if (rs != null)
			{
				try
				{
					while (rs.next())
					{
						if (rs.getString("scheme") != null)
						{
							ht.put(rs.getString("scheme"), rs.getString("pk_seq") + "_" + rs.getString("LEVEL_PHANBO") );
						}
					}
					rs.close();
				} 
				catch (Exception e)
				{
					e.printStackTrace();
				}
			} 
			else
			{
				System.out.print("Error here!");
			}
			db.shutDown();
		} 
		else
		{
			System.out.print("Error here!");
		}
		return ht;
	}

	private Hashtable<String, String> getNPP()
	{
		Hashtable<String, String> ht = new Hashtable<String, String>();
		dbutils db = new dbutils();
		if (db.getConnection() != null)
		{
			ResultSet rs = db.get("SELECT MA, TEN, PK_SEQ FROM NHAPHANPHOI WHERE TRANGTHAI = '1' and isKHACHHANG = '0' ");
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

	private Hashtable<String, String> getUsedPromotion(String schemeId)
	{
		Hashtable<String, String> ht = new Hashtable<String, String>();
		dbutils db = new dbutils();
		if (db.getConnection() != null)
		{
			// lay nha phan phoi da va dang su dung,trang thai don hang la 1
			ResultSet rs = db.get("select b.npp_fk as nppId, sum(a.tonggiatri) as amount from donhang_ctkm_trakm a, donhang b where a.donhangid=b.pk_seq and b.trangthai=1 and a.ctkmId = '" + schemeId
					+ "' group by b.npp_fk");
			// System.out.print("Cau lay du lieu: select b.npp_fk as nppId, sum(a.tonggiatri) as amount from donhang_ctkm_trakm a, donhang b where a.donhangid=b.pk_seq and b.trangthai=3 and a.ctkmId = '"+
			// schemeId +"' group by b.npp_fk");
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
