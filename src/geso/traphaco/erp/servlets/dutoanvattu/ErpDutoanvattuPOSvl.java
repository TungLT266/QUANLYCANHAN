package geso.traphaco.erp.servlets.dutoanvattu;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;
import geso.traphaco.erp.beans.dutoanvattu.IErpDutoanvattu;
import geso.traphaco.erp.beans.dutoanvattu.IErpDutoanvattuList;
import geso.traphaco.erp.beans.dutoanvattu.IKho;
import geso.traphaco.erp.beans.dutoanvattu.INgaynhan;
import geso.traphaco.erp.beans.dutoanvattu.INhacungcap;
import geso.traphaco.erp.beans.dutoanvattu.ISanpham;
import geso.traphaco.erp.beans.dutoanvattu.imp.ErpDutoanvattuList;
import geso.traphaco.erp.beans.dutoanvattu.imp.ErpDutoanvattu;
import geso.traphaco.erp.beans.dutoanvattu.imp.Ngaynhan;
import geso.traphaco.erp.beans.dutoanvattu.imp.Sanpham;
import geso.traphaco.erp.beans.dutoanvattu.imp.Nhacungcap;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class ErpDutoanvattuPOSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public ErpDutoanvattuPOSvl()
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
			IErpDutoanvattu dmhBean = new ErpDutoanvattu(id);
			dmhBean.setCongtyId((String)session.getAttribute("congtyId"));
			dmhBean.setUserId(userId); // phai co UserId truoc khi Init
			
			dmhBean.init(true);
			
			nextJSP = "/TraphacoHYERP/pages/Erp/ErpDuToanVatTuPO.jsp";
			
			System.out.println("lhhId:"+dmhBean.getLoaihanghoa());
			session.setAttribute("lhhId", dmhBean.getLoaihanghoa());
			session.setAttribute("lspId", dmhBean.getLoaispId());
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
			
			ErpDutoanvattu dmhBean;
			Utility util = new Utility();
			String id = util.antiSQLInspection(request.getParameter("id"));
			System.out.println("ID" + id);
			
			dmhBean = new ErpDutoanvattu(id);
				
			String contyId = (String)session.getAttribute("congtyId");
			session.setAttribute("ctyId", contyId);
			dmhBean.setCongtyId(contyId);
			dmhBean.setUserId(userId);
			
			String action = util.antiSQLInspection(request.getParameter("action"));

			
			String ngaydutoan = util.antiSQLInspection(request.getParameter("ngaymuahang"));
			if (ngaydutoan == null || ngaydutoan == "")
				ngaydutoan = this.getDateTime();
			dmhBean.setNgaydutoan(ngaydutoan);			
			
			String dvth = util.antiSQLInspection(request.getParameter("dvthId"));
			if (dvth == null)
				dvth = "";
			dmhBean.setDvthId(dvth);
			
			String dnmhId = util.antiSQLInspection(request.getParameter("dnmhId"));
			if (dnmhId == null)
				dnmhId = "";
			dmhBean.setDnmhId(dnmhId);
			
			String timnccId = util.antiSQLInspection(request.getParameter("timnccId"));
			if (timnccId == null)
				timnccId = "";
			dmhBean.setTimNCCId(timnccId);

			String loaihh = util.antiSQLInspection(request.getParameter("loaihh"));
			if (loaihh == null)
				loaihh = "";

			System.out.println("lhhId::"+loaihh);
			session.setAttribute("lhhId", loaihh);
			dmhBean.setLoaihanghoa(loaihh);
			 
			
			String loaisp = util.antiSQLInspection(request.getParameter("loaisp"));
			if (loaisp == null)
				loaisp = "";
			session.setAttribute("lspId", loaisp);
			dmhBean.setLoaispId(loaisp);
			
			String NguonGocHH = util.antiSQLInspection(request.getParameter("nguongoc"));
			if (NguonGocHH == null)
				NguonGocHH = "";
			dmhBean.setNguonGocHH(NguonGocHH);
						
			
			String ghichu = util.antiSQLInspection(request.getParameter("ghichu"));
			if (ghichu == null)
				ghichu = "";
			dmhBean.setGhiChu(ghichu);
			
			String tiente_fk = util.antiSQLInspection(request.getParameter("tiente_fk"));
			if (tiente_fk == null) tiente_fk = "";
			dmhBean.setTienTe_FK(tiente_fk);
			
			String tigia = util.antiSQLInspection(request.getParameter("tigia"));
			if (tigia == null) tigia = "1";
			dmhBean.setTyGiaQuyDoi(Float.parseFloat(tigia));
									
			String[] spid = request.getParameterValues("idsp");
			String[] spma = request.getParameterValues("masp");
			String[] spten = request.getParameterValues("tensp");
			String[] nccid = request.getParameterValues("nccId");
			List<ISanpham> spList = new ArrayList<ISanpham>();
			
			ISanpham sp = null;
			
			if(spid != null)
			{
				for(int j = 0; j < spid.length; j ++)
				{
					if(spid[j].trim().length() > 0)
					{
						sp = new Sanpham();
						sp.setId(spid[j]);
						sp.setMasanpham(spma[j]);
						sp.setTensanpham(spten[j]);
						System.out.println("[ncc] " + nccid[j]);
						sp.setNccId(nccid[j]);					
												
						spList.add(sp);
					}
				}
				dmhBean.setSanphamList(spList);
			}
			
			
			if (action.equals("save") )
			{		
				if (!dmhBean.createPO())
				{
					dmhBean.createRs(true);
					session.setAttribute("dmhBean", dmhBean);
					String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDuToanVatTuPO.jsp";
					response.sendRedirect(nextJSP);
					return;
				}
				else
				{
					IErpDutoanvattuList obj = new ErpDutoanvattuList();
					obj.setCongtyId((String)session.getAttribute("congtyId"));
					obj.setUserId(userId);
					obj.init("");
					session.setAttribute("obj", obj);
					String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDuToanVatTuList.jsp";
					response.sendRedirect(nextJSP);
					return;
				}
			}
			else
			{				
				dmhBean.createRs(true);
								
				System.out.println("lhhId::"+loaihh);
				session.setAttribute("lhhId", dmhBean.getLoaihanghoa());
				
				String nextJSP;
				
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpDuToanVatTuPO.jsp";
				
				session.setAttribute("dmhBean", dmhBean);
				response.sendRedirect(nextJSP);
			}
		}
	}
	
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
}
