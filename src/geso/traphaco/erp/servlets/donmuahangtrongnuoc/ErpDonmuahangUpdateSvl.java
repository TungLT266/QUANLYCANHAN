package geso.traphaco.erp.servlets.donmuahangtrongnuoc;
 
import geso.traphaco.erp.beans.donmuahangtrongnuoc.IErpDonmuahang;
import geso.traphaco.erp.beans.donmuahangtrongnuoc.IErpDonmuahangList;
import geso.traphaco.erp.beans.donmuahangtrongnuoc.ISanpham;
import geso.traphaco.erp.beans.donmuahangtrongnuoc.imp.ErpDonmuahang;
import geso.traphaco.erp.beans.donmuahangtrongnuoc.imp.ErpDonmuahangList;
import geso.traphaco.erp.beans.donmuahangtrongnuoc.imp.Sanpham;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpDonmuahangUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	public ErpDonmuahangUpdateSvl()
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
		geso.traphaco.center.util.*
		if (!cutil.check(userId, userTen, sum))
		{
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}
		else
		{
			session.setMaxInactiveInterval(30000);
			this.out = response.getWriter();
			Utility util = new Utility();
			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);
			out.println(userId);
			if (userId.length() == 0)
				userId = util.antiSQLInspection(request.getParameter("userId"));
			
			String id = util.getId(querystring);
			IErpDonmuahang dmhBean = new ErpDonmuahang(id);
			dmhBean.setCongtyId((String)session.getAttribute("congtyId"));
			dmhBean.setUserId(userId); // phai co UserId truoc khi Init
			dmhBean.init();
			
			if (request.getQueryString().indexOf("display") >= 0)
			{
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpDonMuaHangDisplay.jsp";
			}
			else
			{
				if (request.getQueryString().indexOf("hoantat") >= 0)
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpHTDonMuaHangDisplay.jsp";
				else
				{
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpDonMuaHangUpdate.jsp";
				}
			}
			
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
		geso.traphaco.center.util.*
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
			IErpDonmuahang dmhBean;
			Utility util = new Utility();
			String id = util.antiSQLInspection(request.getParameter("id"));
			System.out.println("ID" + id);
			if (id == null)
			{
				dmhBean = new ErpDonmuahang("");
			}
			else
			{
				dmhBean = new ErpDonmuahang(id);
			}
			
			dmhBean.setCongtyId((String)session.getAttribute("congtyId"));
			dmhBean.setUserId(userId);
			String ngaygd = util.antiSQLInspection(request.getParameter("ngaymuahang"));
			if (ngaygd == null || ngaygd == "")
				ngaygd = this.getDateTime();
			dmhBean.setNgaymuahang(ngaygd);
			
			String dvth = util.antiSQLInspection(request.getParameter("dvthId"));
			if (dvth == null)
				dvth = "";
			dmhBean.setDvthId(dvth);

			String loaihh = util.antiSQLInspection(request.getParameter("loaihh"));
			if (loaihh == null)
				loaihh = "";
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
			String ncc = util.antiSQLInspection(request.getParameter("nccId"));
			if (ncc == null)
				ncc = "";
			dmhBean.setNCC(ncc);
			
			String tiente_fk = util.antiSQLInspection(request.getParameter("tiente_fk"));
			if (tiente_fk == null)
			{
				tiente_fk = "";
			}
			
			if (tiente_fk.trim().length() > 0)
			{
				String[] arr = tiente_fk.split(" - ");
				
				dmhBean.setTyGiaQuyDoi(Float.parseFloat(arr[1]));
				dmhBean.setTienTe_FK(arr[0]);
			}
			
			String tongtientruocVAT = request.getParameter("BVAT");
			if (tongtientruocVAT == null)
				tongtientruocVAT = "0";
			else
				tongtientruocVAT = tongtientruocVAT.replaceAll(",", "");
			dmhBean.setTongtienchuaVat(tongtientruocVAT);
			

			String VAT = util.antiSQLInspection(request.getParameter("VAT"));
			if (VAT == null)
				VAT = "10";
			dmhBean.setVat(VAT);
			
			String tongtiensauVAT = request.getParameter("AVAT");
			if (tongtiensauVAT == null)
				tongtiensauVAT = "0";
			else
				tongtiensauVAT = tongtiensauVAT.replaceAll(",", "");
			dmhBean.setTongtiensauVat(tongtiensauVAT);
			
			String dungsaiTong = util.antiSQLInspection(request.getParameter("dungsai"));
			if (dungsaiTong == null)
				dungsaiTong = "";
			dmhBean.setDungsai(dungsaiTong);
			
			String ghichu = util.antiSQLInspection(request.getParameter("ghichu"));
			if (ghichu == null)
				ghichu = "NULL";
			dmhBean.setGhiChu(ghichu);

			String nguongoc = util.antiSQLInspection(request.getParameter("nguongoc"));
			dmhBean.setNguonGocHH(nguongoc);
			
			String[] duyetIds = request.getParameterValues("duyetIds");
			dmhBean.setDuyetIds(duyetIds);
			String[] idsp = request.getParameterValues("idsp");
			String[] masp = request.getParameterValues("masp");
			String[] mnlId = request.getParameterValues("mnlId");
			String[] tensp = request.getParameterValues("tensp");
			String[] soluong = request.getParameterValues("soluong");
			String[] donvitinh = request.getParameterValues("donvitinh");
			String[] dongia = request.getParameterValues("dongia");
			String[] thanhtien = request.getParameterValues("thanhtien");
			String[] thuenhapkhau = request.getParameterValues("thuenhapkhau");
			String[] phantramthue = request.getParameterValues("phantramthue");
			String[] ngaynhan = request.getParameterValues("ngaynhan");
			String[] khonhan = request.getParameterValues("khonhan");
			String[] nhomhang = request.getParameterValues("nhomhang");
			
			List<ISanpham> spList = new ArrayList<ISanpham>();
			
			ISanpham sp = null;
			for(int i = 0; i < masp.length; i++)
			{
				if(masp[i].trim().length() > 0)
				{
					sp = new Sanpham();
					sp.setPK_SEQ(idsp[i]);
					sp.setMNLId(mnlId[i]);
					sp.setMasanpham(masp[i]);
					sp.setTensanpham(tensp[i]);
					sp.setSoluong(soluong[i].replaceAll(",", ""));
					sp.setDonvitinh(donvitinh[i]);
					sp.setDongia(dongia[i].replaceAll(",", ""));
					sp.setThanhtien(thanhtien[i].replaceAll(",", ""));
					sp.setThueNhapKhau(thuenhapkhau[i].replaceAll(",", ""));
					sp.setPhanTramThue(phantramthue[i].replaceAll(",", ""));
					sp.setNgaynhan(ngaynhan[i]);
					sp.setKhonhan(khonhan[i]);
					sp.setNhomhang(nhomhang[i]);
					
					spList.add(sp);
				}
			}
			dmhBean.setSpList(spList);
			
			String action = request.getParameter("action");
			if (action.equals("save"))
			{
				if (id == null) // tao moi
				{
					if (!dmhBean.createDmh())
					{
						dmhBean.createRs();
						session.setAttribute("dmhBean", dmhBean);
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDonMuaHangNew.jsp";
						response.sendRedirect(nextJSP);
					}
					else
					{
						IErpDonmuahangList obj = new ErpDonmuahangList();
						obj.setCongtyId((String)session.getAttribute("congtyId"));
						obj.setUserId(userId);
						obj.init("");
						session.setAttribute("obj", obj);
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDonMuaHangList.jsp";
						response.sendRedirect(nextJSP);
					}
				}
				else
				{
					if (!dmhBean.updateDmh())
					{
						dmhBean.createRs();
						session.setAttribute("dmhBean", dmhBean);
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDonMuaHangUpdate.jsp";
						response.sendRedirect(nextJSP);
					}
					else
					{
						IErpDonmuahangList obj = new ErpDonmuahangList();
						obj.setCongtyId((String)session.getAttribute("congtyId"));
						obj.setUserId(userId);
						obj.init("");
						session.setAttribute("obj", obj);
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDonMuaHangList.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			}
			else
			{
				dmhBean.createRs();
				String nextJSP;
				if (id == null)
				{
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpDonMuaHangNew.jsp";
				}
				else
				{
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpDonMuaHangUpdate.jsp";
				}
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
