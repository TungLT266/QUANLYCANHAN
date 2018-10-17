package geso.traphaco.erp.servlets.taikhoankt;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.taikhoankt.ITaikhoankt;
import geso.traphaco.erp.beans.taikhoankt.ITaikhoanktList;
import geso.traphaco.erp.beans.taikhoankt.imp.Taikhoankt;
import geso.traphaco.erp.beans.taikhoankt.imp.TaikhoanktList;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class TaikhoanktUpdateSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	PrintWriter out;

	public TaikhoanktUpdateSvl() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session
				.getAttribute("util");
		if (!cutil.check(userId, userTen, sum)) {
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		} else {
			session.setMaxInactiveInterval(30000);

			this.out = response.getWriter();
			Utility util = new Utility();

			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);

			if (userId.length() == 0)
				userId = util.antiSQLInspection(request.getParameter("userId"));
			String id = util.getId(querystring);
			System.out.println("Id: " + id);

			ITaikhoankt tkktBean;
			String task = request.getParameter("task");
			if (task.equals("capnhat")) {
				tkktBean = new Taikhoankt(id);
				tkktBean.init();
				session.setAttribute("tkktBean", tkktBean);
				String nextJSP = "/TraphacoHYERP/pages/Erp/TaiKhoanKtUpdate.jsp";
				response.sendRedirect(nextJSP);
			}
			else if (task.equals("display")) {
				tkktBean = new Taikhoankt(id);
				tkktBean.init();
				session.setAttribute("tkktBean", tkktBean);
				String nextJSP = "/TraphacoHYERP/pages/Erp/TaiKhoanKtDisplay.jsp";
				response.sendRedirect(nextJSP);
			}
		}
	}

	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");

		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session
				.getAttribute("util");
		if (!cutil.check(userId, userTen, sum)) {
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		} else {
			ITaikhoankt tkktBean;
			Utility util = new Utility();
			String id = util.antiSQLInspection(request.getParameter("id"));
			if (id == null) {
				tkktBean = new Taikhoankt();
			} else {
				tkktBean = new Taikhoankt(id);
			}
			String action = request.getParameter("action");
			String LoaiTaiKhoanId = cutil.antiSQLInspection(request.getParameter("LoaiTaiKhoanId"));
			String loaiTKBaoCao = cutil.antiSQLInspection(request.getParameter("loaiTKBaoCao"));
			String SoHieuTaiKhoan = cutil.antiSQLInspection(request.getParameter("SoHieuTaiKhoan"));
			String TenTaiKhoan = cutil.antiSQLInspection(request.getParameter("TenTaiKhoan"));
			String TaiKhoanCoChiTiet = cutil.antiSQLInspection(request.getParameter("TaiKhoanCoChiTiet"));
			String TaiKhoanCoChiPhi = cutil.antiSQLInspection(request.getParameter("TaiKhoanCoChiPhi"));
			
			//String CongTyId = cutil.antiSQLInspection(request.getParameter("CongTyId"));
			
			String CongTyId = (String) session.getAttribute("congtyId");
			tkktBean.setCongTyId(CongTyId);
			
			String TrangThai = cutil.antiSQLInspection(request.getParameter("TrangThai"));
			String dungchokh = cutil.antiSQLInspection(request.getParameter("dungchokh"));
			
			if ( dungchokh != null) 
				tkktBean.setDungchokhachhang("1");
			else
				tkktBean.setDungchokhachhang("0");
			
			String dungchoncc = cutil.antiSQLInspection(request.getParameter("dungchoncc"));
			if ( dungchoncc != null) 
				tkktBean.setDungchonhacungcap("1");
			else
				tkktBean.setDungchonhacungcap("0");
			
			String dungchonv = cutil.antiSQLInspection(request.getParameter("dungchonv"));
			if ( dungchonv != null) 
				tkktBean.setDungchonhanvien("1");
			else
				tkktBean.setDungchonhanvien("0");
			
			String dungchomscl = cutil.antiSQLInspection(request.getParameter("dungchomscl"));
			if ( dungchomscl != null) 
				tkktBean.setDungchoMSCL("1");
			else
				tkktBean.setDungchoMSCL("0");
			
			String dungchocptt = cutil.antiSQLInspection(request.getParameter("dungchocptt"));
			if ( dungchocptt != null) 
				tkktBean.setDungchoCPTT("1");
			else
				tkktBean.setDungchoCPTT("0");

			String dungchonh = cutil.antiSQLInspection(request.getParameter("dungchonh"));
			if ( dungchonh != null) 
				tkktBean.setDungchonganhang("1");
			else
				tkktBean.setDungchonganhang("0");
			
			String dungchokho = cutil.antiSQLInspection(request.getParameter("dungchokho"));
			if ( dungchokho != null) 
				tkktBean.setDungchokho("1");
			else
				tkktBean.setDungchokho("0");

			String dungchots = cutil.antiSQLInspection(request.getParameter("dungchots"));
			if ( dungchots != null) 
				tkktBean.setDungchotaisan("1");
			else
				tkktBean.setDungchotaisan("0");
			
			String dungChoDanhMucDuAn = cutil.antiSQLInspection(request.getParameter("dungChoDanhMucDuAn"));
			if ( dungChoDanhMucDuAn != null) 
				tkktBean.setDungChoDanhMucDuAn("1");
			else
				tkktBean.setDungChoDanhMucDuAn("0");
			
			String dungChoDoiTuongKhac = cutil.antiSQLInspection(request.getParameter("dungChoDoiTuongKhac"));
			if ( dungChoDoiTuongKhac != null) 
				tkktBean.setDungChoDoiTuongKhac("1");
			else
				tkktBean.setDungChoDoiTuongKhac("0");
			
			if (TrangThai != null)
				TrangThai = "1";
			else
				TrangThai = "0";
			tkktBean.setTrangThai(TrangThai);

			tkktBean.setUserId(userId);

			if (LoaiTaiKhoanId != null)
				tkktBean.setLoaiTaiKhoanId(LoaiTaiKhoanId);
			
			if (loaiTKBaoCao != null)
				tkktBean.setLoaiTKBaoCao(loaiTKBaoCao);

			if (SoHieuTaiKhoan != null)
				tkktBean.setSoHieuTaiKhoan(SoHieuTaiKhoan);

			if (TenTaiKhoan != null)
				tkktBean.setTenTaiKhoan(TenTaiKhoan);

			if (TaiKhoanCoChiTiet != null)
				tkktBean.setTaiKhoanCoChiTiet("1");
			else
				tkktBean.setTaiKhoanCoChiTiet("0");

			if (TaiKhoanCoChiPhi != null)
				tkktBean.setTaiKhoanCoChiPhi("1");
			else
				tkktBean.setTaiKhoanCoChiPhi("0");
       
			/*if (CongTyId != null)
				tkktBean.setCongTyId(CongTyId);
			else
				tkktBean.setMsg("Vui lòng chọn Công ty");*/
			
			if (action.equals("Save")) {
				if (id.length() == 0) {
					if (tkktBean.CreateTaikhoankt()) {
						ITaikhoanktList tkktList = new TaikhoanktList();
						tkktList.setCongTyId(CongTyId);
						tkktList.init("");
						session.setAttribute("tkktList", tkktList);
						String nextJSP = "/TraphacoHYERP/pages/Erp/TaiKhoanKt.jsp";
						response.sendRedirect(nextJSP);
					} else {
						tkktBean.CreateRs();
						session.setAttribute("tkktBean", tkktBean);
						String nextJSP = "/TraphacoHYERP/pages/Erp/TaiKhoanKtNew.jsp";
						response.sendRedirect(nextJSP);
					}
				} else {
					tkktBean.setId(id);
					if (tkktBean.UpdateTaikhoankt()) {
						ITaikhoanktList tkktList = new TaikhoanktList();
						tkktList.setCongTyId(CongTyId);
						tkktList.init("");
						session.setAttribute("tkktList", tkktList);
						String nextJSP = "/TraphacoHYERP/pages/Erp/TaiKhoanKt.jsp";
						response.sendRedirect(nextJSP);
					} else {
						tkktBean.CreateRs();
						session.setAttribute("tkktBean", tkktBean);
						String nextJSP = "/TraphacoHYERP/pages/Erp/TaiKhoanKtUpdate.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			} else {
				if (id.length() == 0) {
					tkktBean.CreateRs();
					session.setAttribute("tkktBean", tkktBean);
					String nextJSP = "/TraphacoHYERP/pages/Erp/TaiKhoanKtNew.jsp";
					response.sendRedirect(nextJSP);
				} else {
					tkktBean.setId(id);
					tkktBean.CreateRs();
					session.setAttribute("tkktBean", tkktBean);
					String nextJSP = "/TraphacoHYERP/pages/Erp/TaiKhoanKtUpdate.jsp";
					response.sendRedirect(nextJSP);
				}
			}
		}
	}
}