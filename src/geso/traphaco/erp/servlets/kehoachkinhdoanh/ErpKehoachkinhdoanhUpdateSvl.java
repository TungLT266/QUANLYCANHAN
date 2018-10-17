package geso.traphaco.erp.servlets.kehoachkinhdoanh;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.GiuDieuKienLoc;
import geso.traphaco.distributor.util.Utility;
import geso.traphaco.erp.beans.kehoachkinhdoanh.IErpKehoachkinhdoanh;
import geso.traphaco.erp.beans.kehoachkinhdoanh.IErpKehoachkinhdoanhList;
import geso.traphaco.erp.beans.kehoachkinhdoanh.IKenhbanhang;
import geso.traphaco.erp.beans.kehoachkinhdoanh.ISanpham;
import geso.traphaco.erp.beans.kehoachkinhdoanh.imp.ErpKehoachkinhdoanh;
import geso.traphaco.erp.beans.kehoachkinhdoanh.imp.ErpKehoachkinhdoanhList;
import geso.traphaco.erp.beans.kehoachkinhdoanh.imp.Kenhbanhang;
import geso.traphaco.erp.beans.kehoachkinhdoanh.imp.Sanpham;
import geso.traphaco.erp.beans.timnhacc.INhacungcap;
import geso.traphaco.erp.beans.timnhacc.imp.Nhacungcap;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Sheet;
import jxl.Workbook;
import T.IK;

import com.oreilly.servlet.MultipartRequest;

public class ErpKehoachkinhdoanhUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public ErpKehoachkinhdoanhUpdateSvl()
	{
		super();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String nextJSP;
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if (!cutil.check(userId, userTen, sum))
		{
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}
		else
		{
			session.setMaxInactiveInterval(30000);

			Utility util = new Utility();
			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);

			if (userId.length() == 0)
				userId = util.antiSQLInspection(request.getParameter("userId"));
			
			String id = util.getId(querystring);
			IErpKehoachkinhdoanh dmhBean = new ErpKehoachkinhdoanh(id);
			dmhBean.setCongtyId((String)session.getAttribute("congtyId"));
			dmhBean.setUserId(userId); // phai co UserId truoc khi Init
			
				dmhBean.init();
				
				if (request.getQueryString().indexOf("display") >= 0)
				{
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpKeHoachKinhDoanhDisplay.jsp";
				}
				else
				{
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpKeHoachKinhDoanhUpdate.jsp";
				}
				
				session.setAttribute("dmhBean", dmhBean);
				response.sendRedirect(nextJSP);
			
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if (!cutil.check(userId, userTen, sum))
		{
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}
		else
		{
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			session.setMaxInactiveInterval(30000);
			Utility util = new Utility();
			
			IErpKehoachkinhdoanh dmhBean;
			NumberFormat formatter = new DecimalFormat("#,###,###.###");
			
			String action = null;
			String id = null;
			String[] spId = null;
			String[] spMa = null;
			String[] spTen = null;
			String[] spThang1 = null;
			String[] spThang2 = null;
			String[] spThang3 = null;
			String[] spThang4 = null;
			String[] spThang5 = null;
			String[] spThang6 = null;
			String[] spThang7 = null;
			String[] spThang8 = null;
			String[] spThang9 = null;
			String[] spThang10 = null;
			String[] spThang11 = null;
			String[] spThang12 = null;

			String contentType = request.getContentType();
			if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0))
			{
				MultipartRequest multi = new MultipartRequest(request, "C:\\java-tomcat\\data\\", 20000000, "UTF-8");
				id = util.antiSQLInspection(multi.getParameter("id"));
				action = multi.getParameter("action");
				System.out.println("ID" + id);
				if (id == null)
				{
					dmhBean = new ErpKehoachkinhdoanh("");
				}
				else
				{
					dmhBean = new ErpKehoachkinhdoanh(id);
				}
				String contyId = (String)session.getAttribute("congtyId");
				
				dmhBean.setCongtyId(contyId);
				dmhBean.setUserId(userId);
							
				String diengiai = multi.getParameter("diengiai");
				if(diengiai == null)
					diengiai = "";
				dmhBean.setDiengiai(diengiai);
			
				String loai = multi.getParameter("loai");
				if(loai == null)
					loai = "";
				dmhBean.setLoai(loai);
				
				Enumeration files = multi.getFileNames();
				String filenameu = "";
				while (files.hasMoreElements())
				{
					String name = (String) files.nextElement();
					filenameu = multi.getFilesystemName(name);
	
				}
				String filename = "C:\\java-tomcat\\data\\" + filenameu;
	
				if (filename.length() > 0)
				{
					dbutils db = new dbutils();
					try
					{
						File file = new File(filename);
						Workbook workbook;
						workbook = Workbook.getWorkbook(file);
						Sheet sheet = workbook.getSheet(0);
						int indexRow = 3;
						int sodong = sheet.getRows();
						String sql_TABLE = "";
						int index=0;
						List<IKenhbanhang> kbhListMN = new ArrayList<IKenhbanhang>();
						for (int i = 0; i < 5; i++)
						{
							IKenhbanhang kbh = new Kenhbanhang();
							if(i == 0)
							{
								kbh.setId("100025");
								kbh.setTenkenh("TPC");
							}
							else if(i == 1)
							{
								kbh.setId("100052");
								kbh.setTenkenh("CLC");
							}
							else if(i == 2)
							{
								kbh.setId("100054");
								kbh.setTenkenh("SPC");
							}
							else if(i == 3)
							{
								kbh.setId("100056");
								kbh.setTenkenh("INS");
							}
							else 
							{
								kbh.setId("100057");
								kbh.setTenkenh("SI");
							}
							sql_TABLE = "";
							index=0;
							List<ISanpham> lstSP = new ArrayList<ISanpham>();
							for (int row=indexRow; row < sodong ; row++)
							{
								
								String _spMa =getValue(sheet, 0,row);
								String _spTen =getValue(sheet, 1,row);
								String _spThang1 =getValue(sheet, 2 + i,row);
								String _spThang2 =getValue(sheet, 7 + i,row);
								String _spThang3 =getValue(sheet, 12 + i,row);
								String _spThang4 =getValue(sheet, 17 + i,row);
								String _spThang5 =getValue(sheet, 22 + i,row);
								String _spThang6 =getValue(sheet, 27 + i,row);
								String _spThang7 =getValue(sheet, 32 + i,row);
								String _spThang8 =getValue(sheet, 37 + i,row);
								String _spThang9 =getValue(sheet, 42 + i,row);
								String _spThang10 =getValue(sheet, 47 + i,row);
								String _spThang11 =getValue(sheet, 52 + i,row);
								String _spThang12 =getValue(sheet, 57 + i,row);
								
								if(index!=0)
								{
									sql_TABLE+=" union all ";							
								}
								sql_TABLE +="\n  select '"+_spMa+"' as spMa, N'"+_spTen+"' as spTen, "+_spThang1+" as spthang1, "+_spThang2+" as spthang2"
										+ ", "+_spThang3+" as spthang3, "+_spThang4+" as spthang4, "+_spThang5+" as spthang5, "+_spThang6+" as spthang6"
										+ ", "+_spThang7+" as spthang7, "+_spThang8+" as spthang8, "+_spThang9+" as spthang9, "+_spThang10+" as spthang10"
										+ ", "+_spThang11+" as spthang11, "+_spThang12+" as spthang12 ";
								index++;
							}
		
							db.getConnection().setAutoCommit(false);
							String query = "";
							String msg = "";
							query = "";
		
							query = "SELECT * FROM (" + sql_TABLE + ") as data " + " WHERE spMa NOT IN " + " ( " + "	SELECT MA_FAST FROM SANPHAM " + " )";
							ResultSet rs = db.get(query);
							while (rs.next())
							{
								msg += "Sản phẩm " + rs.getString("spMa") + " không có trong hệ thống ! \n";
								System.out.println("[ErpKehoachkinhdoanhUpdateSvl]"+query);
							}
							if (rs != null)
								rs.close();
		
							dmhBean.setMsg(msg);
		
							if (msg.length() <= 0)
							{
								query = "SELECT * FROM (" + sql_TABLE + ") as data inner join SANPHAM b on data.spMa = b.MA_FAST";
		
								System.out.println("[ErpKehoachkinhdoanhUpdateSvl]"+query);
								rs = db.get(query);
								String spID = "";
								String spMA = "";
								String spTEN = "";
								String spTHANG1 = "";
								String spTHANG2 = "";
								String spTHANG3 = "";
								String spTHANG4 = "";
								String spTHANG5 = "";
								String spTHANG6 = "";
								String spTHANG7 = "";
								String spTHANG8 = "";
								String spTHANG9 = "";
								String spTHANG10 = "";
								String spTHANG11 = "";
								String spTHANG12 = "";
		
								while (rs.next())
								{
									spID += rs.getString("pk_seq") + "__";
									spMA += rs.getString("SPMA") + "__";
									spTEN += rs.getString("TEN") + "__";
									spTHANG1 += rs.getString("spthang1")==null?"0__":rs.getString("spthang1") + "__";
									spTHANG2 += rs.getString("spthang2")==null?"0__":rs.getString("spthang2") + "__";
									spTHANG3 += rs.getString("spthang3")==null?"0__":rs.getString("spthang3") + "__";
									spTHANG4 += rs.getString("spthang4")==null?"0__":rs.getString("spthang4") + "__";
									spTHANG5 += rs.getString("spthang5")==null?"0__":rs.getString("spthang5") + "__";
									spTHANG6 += rs.getString("spthang6")==null?"0__":rs.getString("spthang6") + "__";
									spTHANG7 += rs.getString("spthang7")==null?"0__":rs.getString("spthang7") + "__";
									spTHANG8 += rs.getString("spthang8")==null?"0__":rs.getString("spthang8") + "__";
									spTHANG9 += rs.getString("spthang9")==null?"0__":rs.getString("spthang9") + "__";
									spTHANG10 += rs.getString("spthang10")==null?"0__":rs.getString("spthang10") + "__";
									spTHANG11 += rs.getString("spthang11")==null?"0__":rs.getString("spthang11") + "__";
									spTHANG12 += rs.getString("spthang12")==null?"0__":rs.getString("spthang12") + "__";
								}
		
								if (spMA.trim().length() > 0)
								{
									spID = spID.substring(0, spID.length() - 2);
									spId = spID.split("__");
									
									spMA = spMA.substring(0, spMA.length() - 2);
									spMa = spMA.split("__");
		
									spTEN = spTEN.substring(0, spTEN.length() - 2);
									spTen = spTEN.split("__");
		
									spTHANG1 = spTHANG1.substring(0, spTHANG1.length() - 2);
									spThang1 = spTHANG1.split("__");
		
									spTHANG2 = spTHANG2.substring(0, spTHANG2.length() - 2);
									spThang2 = spTHANG2.split("__");
									
									spTHANG3 = spTHANG3.substring(0, spTHANG3.length() - 2);
									spThang3 = spTHANG3.split("__");
									
									spTHANG4 = spTHANG4.substring(0, spTHANG4.length() - 2);
									spThang4 = spTHANG4.split("__");
									
									spTHANG5 = spTHANG5.substring(0, spTHANG5.length() - 2);
									spThang5 = spTHANG5.split("__");
									
									spTHANG6 = spTHANG6.substring(0, spTHANG6.length() - 2);
									spThang6 = spTHANG6.split("__");
									
									spTHANG7 = spTHANG7.substring(0, spTHANG7.length() - 2);
									spThang7 = spTHANG7.split("__");
									
									spTHANG8 = spTHANG8.substring(0, spTHANG8.length() - 2);
									spThang8 = spTHANG8.split("__");
									
									spTHANG9 = spTHANG9.substring(0, spTHANG9.length() - 2);
									spThang9 = spTHANG9.split("__");
									
									spTHANG10 = spTHANG10.substring(0, spTHANG10.length() - 2);
									spThang10 = spTHANG10.split("__");
									
									spTHANG11 = spTHANG11.substring(0, spTHANG11.length() - 2);
									spThang11 = spTHANG11.split("__");
									
									spTHANG12 = spTHANG12.substring(0, spTHANG12.length() - 2);
									spThang12 = spTHANG12.split("__");
		
								}
								
								for (int j = 0; j < spMa.length; j++)
								{
									ISanpham sp = new Sanpham();
									sp.setId(spId[j]);
									sp.setMasanpham(spMa[j]);
									sp.setTensanpham(spTen[j]);
									sp.setThang1(spThang1[j]);
									sp.setThang2(spThang2[j]);
									sp.setThang3(spThang3[j]);
									sp.setThang4(spThang4[j]);
									sp.setThang5(spThang5[j]);
									sp.setThang6(spThang6[j]);
									sp.setThang7(spThang7[j]);
									sp.setThang8(spThang8[j]);
									sp.setThang9(spThang9[j]);
									sp.setThang10(spThang10[j]);
									sp.setThang11(spThang11[j]);
									sp.setThang12(spThang12[j]);
									System.out.println("ma " +sp.getMasanpham()+ ", ten " + sp.getTensanpham()+ ", t1 " + sp.getThang1()+ ", t2 " + sp.getThang2()+ ", t3 " + sp.getThang3()+ ", t4 " + sp.getThang4()+ ", t5 " + sp.getThang5()+ ", t6 " + sp.getThang6()+ ", t7 " + sp.getThang7()+ ", t8 " + sp.getThang8()+ ", t9 " + sp.getThang9()+ ", t10 " + sp.getThang10()+ ", t11 " + sp.getThang11()+ ", t12 " + sp.getThang12());
									lstSP.add(sp);
								}
							}
							kbh.setSanpham(lstSP);
							kbhListMN.add(kbh);
							if (rs != null)
								rs.close();
							db.getConnection().commit();
							db.getConnection().setAutoCommit(true);
						}
						dmhBean.setKbhListMN(kbhListMN);
						sheet = workbook.getSheet(1);
						indexRow = 3;
						sodong = sheet.getRows();
						
						List<IKenhbanhang> kbhListMB = new ArrayList<IKenhbanhang>();
						for (int i = 0; i < 5; i++)
						{
							IKenhbanhang kbh = new Kenhbanhang();
							if(i == 0)
							{
								kbh.setId("100025");
								kbh.setTenkenh("TPC");
							}
							else if(i == 1)
							{
								kbh.setId("100052");
								kbh.setTenkenh("CLC");
							}
							else if(i == 2)
							{
								kbh.setId("100054");
								kbh.setTenkenh("SPC");
							}
							else if(i == 3)
							{
								kbh.setId("100056");
								kbh.setTenkenh("INS");
							}
							else 
							{
								kbh.setId("100057");
								kbh.setTenkenh("SI");
							}
							sql_TABLE = "";
							index=0;
							List<ISanpham> lstSP = new ArrayList<ISanpham>();
							for (int row=indexRow; row < sodong ; row++)
							{
								
								String _spMa =getValue(sheet, 0,row);
								String _spTen =getValue(sheet, 1,row);
								String _spThang1 =getValue(sheet, 2 + i,row);
								String _spThang2 =getValue(sheet, 7 + i,row);
								String _spThang3 =getValue(sheet, 12 + i,row);
								String _spThang4 =getValue(sheet, 17 + i,row);
								String _spThang5 =getValue(sheet, 22 + i,row);
								String _spThang6 =getValue(sheet, 27 + i,row);
								String _spThang7 =getValue(sheet, 32 + i,row);
								String _spThang8 =getValue(sheet, 37 + i,row);
								String _spThang9 =getValue(sheet, 42 + i,row);
								String _spThang10 =getValue(sheet, 47 + i,row);
								String _spThang11 =getValue(sheet, 52 + i,row);
								String _spThang12 =getValue(sheet, 57 + i,row);
								
								if(index!=0)
								{
									sql_TABLE+=" union all ";							
								}
								sql_TABLE +="\n  select '"+_spMa+"' as spMa, N'"+_spTen+"' as spTen, "+_spThang1+" as spthang1, "+_spThang2+" as spthang2"
										+ ", "+_spThang3+" as spthang3, "+_spThang4+" as spthang4, "+_spThang5+" as spthang5, "+_spThang6+" as spthang6"
										+ ", "+_spThang7+" as spthang7, "+_spThang8+" as spthang8, "+_spThang9+" as spthang9, "+_spThang10+" as spthang10"
										+ ", "+_spThang11+" as spthang11, "+_spThang12+" as spthang12 ";
								index++;
							}
		
							db.getConnection().setAutoCommit(false);
							String query = "";
							String msg = "";
							query = "";
		
							query = "SELECT * FROM (" + sql_TABLE + ") as data " + " WHERE spMa NOT IN " + " ( " + "	SELECT MA_FAST FROM SANPHAM " + " )";
							ResultSet rs = db.get(query);
							while (rs.next())
							{
								msg += "Sản phẩm " + rs.getString("spMa") + " không có trong hệ thống ! \n";
								System.out.println("[ErpKehoachkinhdoanhUpdateSvl]"+query);
							}
							if (rs != null)
								rs.close();
		
							dmhBean.setMsg(msg);
		
							if (msg.length() <= 0)
							{
								query = "SELECT * FROM (" + sql_TABLE + ") as data inner join SANPHAM b on data.spMa = b.MA_FAST ";
		
								System.out.println("[ErpKehoachkinhdoanhUpdateSvl]"+query);
								rs = db.get(query);
								String spID = "";
								String spMA = "";
								String spTEN = "";
								String spTHANG1 = "";
								String spTHANG2 = "";
								String spTHANG3 = "";
								String spTHANG4 = "";
								String spTHANG5 = "";
								String spTHANG6 = "";
								String spTHANG7 = "";
								String spTHANG8 = "";
								String spTHANG9 = "";
								String spTHANG10 = "";
								String spTHANG11 = "";
								String spTHANG12 = "";
		
								while (rs.next())
								{
									spID += rs.getString("pk_seq") + "__";
									spMA += rs.getString("SPMA") + "__";
									spTEN += rs.getString("TEN") + "__";
									spTHANG1 += rs.getString("spthang1")==null?"0__":rs.getString("spthang1") + "__";
									spTHANG2 += rs.getString("spthang2")==null?"0__":rs.getString("spthang2") + "__";
									spTHANG3 += rs.getString("spthang3")==null?"0__":rs.getString("spthang3") + "__";
									spTHANG4 += rs.getString("spthang4")==null?"0__":rs.getString("spthang4") + "__";
									spTHANG5 += rs.getString("spthang5")==null?"0__":rs.getString("spthang5") + "__";
									spTHANG6 += rs.getString("spthang6")==null?"0__":rs.getString("spthang6") + "__";
									spTHANG7 += rs.getString("spthang7")==null?"0__":rs.getString("spthang7") + "__";
									spTHANG8 += rs.getString("spthang8")==null?"0__":rs.getString("spthang8") + "__";
									spTHANG9 += rs.getString("spthang9")==null?"0__":rs.getString("spthang9") + "__";
									spTHANG10 += rs.getString("spthang10")==null?"0__":rs.getString("spthang10") + "__";
									spTHANG11 += rs.getString("spthang11")==null?"0__":rs.getString("spthang11") + "__";
									spTHANG12 += rs.getString("spthang12")==null?"0__":rs.getString("spthang12") + "__";
								}
		
								if (spMA.trim().length() > 0)
								{
									spID = spID.substring(0, spID.length() - 2);
									spId = spID.split("__");
									
									spMA = spMA.substring(0, spMA.length() - 2);
									spMa = spMA.split("__");
		
									spTEN = spTEN.substring(0, spTEN.length() - 2);
									spTen = spTEN.split("__");
		
									spTHANG1 = spTHANG1.substring(0, spTHANG1.length() - 2);
									spThang1 = spTHANG1.split("__");
		
									spTHANG2 = spTHANG2.substring(0, spTHANG2.length() - 2);
									spThang2 = spTHANG2.split("__");
									
									spTHANG3 = spTHANG3.substring(0, spTHANG3.length() - 2);
									spThang3 = spTHANG3.split("__");
									
									spTHANG4 = spTHANG4.substring(0, spTHANG4.length() - 2);
									spThang4 = spTHANG4.split("__");
									
									spTHANG5 = spTHANG5.substring(0, spTHANG5.length() - 2);
									spThang5 = spTHANG5.split("__");
									
									spTHANG6 = spTHANG6.substring(0, spTHANG6.length() - 2);
									spThang6 = spTHANG6.split("__");
									
									spTHANG7 = spTHANG7.substring(0, spTHANG7.length() - 2);
									spThang7 = spTHANG7.split("__");
									
									spTHANG8 = spTHANG8.substring(0, spTHANG8.length() - 2);
									spThang8 = spTHANG8.split("__");
									
									spTHANG9 = spTHANG9.substring(0, spTHANG9.length() - 2);
									spThang9 = spTHANG9.split("__");
									
									spTHANG10 = spTHANG10.substring(0, spTHANG10.length() - 2);
									spThang10 = spTHANG10.split("__");
									
									spTHANG11 = spTHANG11.substring(0, spTHANG11.length() - 2);
									spThang11 = spTHANG11.split("__");
									
									spTHANG12 = spTHANG12.substring(0, spTHANG12.length() - 2);
									spThang12 = spTHANG12.split("__");
		
								}
								
								for (int j = 0; j < spMa.length; j++)
								{
									ISanpham sp = new Sanpham();
									sp.setId(spId[j]);
									sp.setMasanpham(spMa[j]);
									sp.setTensanpham(spTen[j]);
									sp.setThang1(spThang1[j]);
									sp.setThang2(spThang2[j]);
									sp.setThang3(spThang3[j]);
									sp.setThang4(spThang4[j]);
									sp.setThang5(spThang5[j]);
									sp.setThang6(spThang6[j]);
									sp.setThang7(spThang7[j]);
									sp.setThang8(spThang8[j]);
									sp.setThang9(spThang9[j]);
									sp.setThang10(spThang10[j]);
									sp.setThang11(spThang11[j]);
									sp.setThang12(spThang12[j]);
									System.out.println("ma " +sp.getMasanpham()+ ", ten " + sp.getTensanpham()+ ", t1 " + sp.getThang1()+ ", t2 " + sp.getThang2()+ ", t3 " + sp.getThang3()+ ", t4 " + sp.getThang4()+ ", t5 " + sp.getThang5()+ ", t6 " + sp.getThang6()+ ", t7 " + sp.getThang7()+ ", t8 " + sp.getThang8()+ ", t9 " + sp.getThang9()+ ", t10 " + sp.getThang10()+ ", t11 " + sp.getThang11()+ ", t12 " + sp.getThang12());
									lstSP.add(sp);
								}
							}
							kbh.setSanpham(lstSP);
							kbhListMB.add(kbh);
							if (rs != null)
								rs.close();
							db.getConnection().commit();
							db.getConnection().setAutoCommit(true);
						}
						dmhBean.setKbhListMB(kbhListMB);
					} catch (Exception e)
					{
						e.printStackTrace();
					} finally
					{
						db.shutDown();
					}
				}
			}
			else 
			{
				id = util.antiSQLInspection(request.getParameter("id"));
				action = request.getParameter("action");
				String contyId = (String)session.getAttribute("congtyId");
				
				if (id == null)
				{
					dmhBean = new ErpKehoachkinhdoanh("");
				}
				else
				{
					dmhBean = new ErpKehoachkinhdoanh(id);
				}
				
				dmhBean.setCongtyId(contyId);
				dmhBean.setUserId(userId);
							
				String diengiai = request.getParameter("diengiai");
				if(diengiai == null)
					diengiai = "";
				dmhBean.setDiengiai(diengiai);
				
				String loai = request.getParameter("loai");
				if(loai == null)
					loai = "";
				dmhBean.setLoai(loai);
				
				String[] kbhid = request.getParameterValues("kenhbanhang");
				String[] tenkbh = request.getParameterValues("tenkbh");
				if(kbhid != null){
					List<IKenhbanhang> lstkbhMB = new ArrayList<IKenhbanhang>();
					List<IKenhbanhang> lstkbhMN = new ArrayList<IKenhbanhang>();
					
					for (int i = 0; i < kbhid.length; i++) {
						System.out.println("kbh id " + kbhid[i]);
						IKenhbanhang k = new Kenhbanhang();
						k.setId(kbhid[i]);
						k.setTenkenh(tenkbh[i]);
						String[] idsp = request.getParameterValues("idsp"+i);
						if(idsp != null)
						{
							List<ISanpham> lstsp = new ArrayList<ISanpham>();
							String[] masp = request.getParameterValues("masp"+i);
							String[] tensp = request.getParameterValues("tensp"+i);
							String[] t1 = request.getParameterValues("thang1"+i);
							String[] t2 = request.getParameterValues("thang2"+i);
							String[] t3 = request.getParameterValues("thang3"+i);
							String[] t4 = request.getParameterValues("thang4"+i);
							String[] t5 = request.getParameterValues("thang5"+i);
							String[] t6 = request.getParameterValues("thang6"+i);
							String[] t7 = request.getParameterValues("thang7"+i);
							String[] t8 = request.getParameterValues("thang8"+i);
							String[] t9 = request.getParameterValues("thang9"+i);
							String[] t10 = request.getParameterValues("thang10"+i);
							String[] t11 = request.getParameterValues("thang11"+i);
							String[] t12 = request.getParameterValues("thang12"+i);
							for (int j = 0; j < idsp.length; j++) {
								
								ISanpham sp = new Sanpham();
								sp.setId(idsp[j]);
								//System.out.println("sp id " + idsp[j]);
								sp.setMasanpham(masp[j]);
								sp.setTensanpham(tensp[j]);
								sp.setThang1(t1[j]);
								sp.setThang2(t2[j]);
								sp.setThang3(t3[j]);
								sp.setThang4(t4[j]);
								sp.setThang5(t5[j]);
								sp.setThang6(t6[j]);
								sp.setThang7(t7[j]);
								sp.setThang8(t8[j]);
								sp.setThang9(t9[j]);
								sp.setThang10(t10[j]);
								sp.setThang11(t11[j]);
								sp.setThang12(t12[j]);
								lstsp.add(sp);
							}
							k.setSanpham(lstsp);
						}
						if(i < 5)
						{
							System.out.println("kenh ban hang MB " + k.getTenkenh());
							lstkbhMB.add(k);
						}
						else
						{
							System.out.println("kenh ban hang MN " + k.getTenkenh());
							lstkbhMN.add(k);
						}
					}
					dmhBean.setKbhListMB(lstkbhMB);
					dmhBean.setKbhListMN(lstkbhMN);
				}
			}
			if(action.equals("save"))
			{
				String loai = request.getParameter("loai");
				if(loai == null)
					loai = "";
				
				if (id == null) // tao moi
				{
					if (!dmhBean.createKhkd())
					{
						dmhBean.createRs();
						session.setAttribute("dmhBean", dmhBean);
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKeHoachKinhDoanhNew.jsp";
						response.sendRedirect(nextJSP);
						return;
					}
					else
					{
						IErpKehoachkinhdoanhList obj = new ErpKehoachkinhdoanhList();
						obj.setCongtyId((String)session.getAttribute("congtyId"));
						obj.setUserId(userId);
						obj.setLoai(loai);
						GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
						obj.init("");
						session.setAttribute("obj", obj);
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKeHoachKinhDoanh.jsp";
						response.sendRedirect(nextJSP);
						return;
					}
				}
				else
				{
					if (!dmhBean.updateKhkd())
					{
						dmhBean.createRs();
						session.setAttribute("dmhBean", dmhBean);
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKeHoachKinhDoanhUpdate.jsp";
						response.sendRedirect(nextJSP);
						return;
					}
					else
					{
						IErpKehoachkinhdoanhList obj = new ErpKehoachkinhdoanhList();
						obj.setCongtyId((String)session.getAttribute("congtyId"));
						obj.setUserId(userId);
						obj.setLoai(loai);
						GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
						obj.init("");
						session.setAttribute("obj", obj);
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKeHoachKinhDoanh.jsp";
						response.sendRedirect(nextJSP);
						return;
					}
				}
			}
			else
			{	
				dmhBean.createRs();
				
				String nextJSP;
				if (id == null)
				{
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpKeHoachKinhDoanhNew.jsp";
				}
				else
				{
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpKeHoachKinhDoanhUpdate.jsp";
				}
				session.setAttribute("dmhBean", dmhBean);
				response.sendRedirect(nextJSP);
			
			}
		}
	}
	
	String getValue(Sheet sheet,int col,int row)
	{
		String t = sheet.getCell(col,row).getContents().trim().replaceAll(",", "");
		if(t == "")
			t = null;
		return t;
	}
	
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	private String getEnDateTime(String date) {
		if(date.length() == 10) {
			String ngay = date.substring(8, 10);
			String thang = date.substring(5, 7);
			String nam = date.substring(0, 4);
			
			thang = thang.equals("01") ? "Jan" : thang.equals("02") ? "Feb" : thang.equals("03") ? "Mar" : thang.equals("04") ? "Apr" : thang.equals("05") ? "May" : thang.equals("06") ? "Jun" : thang.equals("07") ? "Jul" : thang.equals("08") ? "Aug" : thang.equals("09") ? "Sep" : thang.equals("10") ? "Oct" : thang.equals("11") ? "Nov" : thang.equals("12") ? "Dec" : " ";
			return thang + " " + ngay + ", " + nam;
		} else {
			return date;
		} 
	}
	
	private String getVnDateTime(String date) {
		if(date.length() == 10) {
			String ngay = date.substring(8, 10);
			String thang = date.substring(5, 7);
			String nam = date.substring(0, 4);
			return ngay + "-" + thang + "-" + nam;
		} else {
			return date;
		}
	}	
}
