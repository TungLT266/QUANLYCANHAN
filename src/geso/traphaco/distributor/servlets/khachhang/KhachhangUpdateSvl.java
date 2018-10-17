package geso.traphaco.distributor.servlets.khachhang;

import geso.traphaco.distributor.beans.khachhang.*;
import geso.traphaco.distributor.beans.khachhang.imp.*;

import geso.traphaco.distributor.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class KhachhangUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public KhachhangUpdateSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if (!cutil.check(userId, userTen, sum))
		{
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		} else
		{
			IKhachhang khBean;
			PrintWriter out;

			out = response.getWriter();
			Utility util = new Utility();

			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);

			out.println(userId);

			if (userId.length() == 0)
				userId = util.antiSQLInspection(request.getParameter("userId"));

			String id = util.getId(querystring);

			khBean = new Khachhang(id);
			khBean.setUserId(userId);
			khBean.init();
			session.setAttribute("khBean", khBean);
			String nextJSP = "/TraphacoHYERP/pages/Distributor/KhachHangUpdate.jsp";
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
		} else
		{

			IKhachhang khBean;
			PrintWriter out;

			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");

			out = response.getWriter();
			Utility util = new Utility();
			String id = util.antiSQLInspection(request.getParameter("id"));
			
			if (id == null)
			{
				khBean = new Khachhang("");
			} else
			{
				khBean = new Khachhang(id);
			}

			userId = util.antiSQLInspection(request.getParameter("userId"));
			khBean.setUserId(userId);

			String khTen = util.antiSQLInspection(request.getParameter("khTen"));
			if (khTen == null)
				khTen = "";
			khBean.setTen(khTen); 

			String tenkyhd = util.antiSQLInspection(request.getParameter("tenkyhd"));
			if (tenkyhd == null)
				tenkyhd = "";
			khBean.setTenKyHd(tenkyhd);
						
			String maFAST = util.antiSQLInspection(request.getParameter("maFAST"));
			if (maFAST == null)
				maFAST = "";
			khBean.MaFAST(maFAST);
			
			String thanhtoan = util.antiSQLInspection(request.getParameter("thanhtoan"));
			if (thanhtoan == null)
				thanhtoan = "";
			khBean.setThanhtoan(thanhtoan);
			
			String thanhtoanQUY = util.antiSQLInspection(request.getParameter("thanhtoanQUY"));
			if (thanhtoanQUY == null)
				thanhtoanQUY = "";
			khBean.setThanhtoanQuy(thanhtoanQUY);
			
			String nppId = util.antiSQLInspection(request.getParameter("nppId"));
			if (nppId == null)
				nppId = "";
			khBean.setNppId(nppId);
			
			String thoihanno = util.antiSQLInspection(request.getParameter("thoihanno"));
			if (thoihanno == null)
				thoihanno = "";
			khBean.setThoihanno(thoihanno);
			
			System.out.println("thoihanno:"+thoihanno);

			String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
			if (trangthai == null)
				trangthai = "0";
			else
				trangthai = "1";
			khBean.setTrangthai(trangthai);
			
			String type = util.antiSQLInspection(request.getParameter("type"));
			if (type == null)
				type = "0";
			khBean.settype(type);

			String diachi = request.getParameter("diachi");
			if (diachi == null)
				diachi = "";
			khBean.setDiachi(diachi);
			
			
			String hopdong = request.getParameter("hopdong")==null?"":request.getParameter("hopdong").trim();
			khBean.setHopdong(hopdong);
			
			
			String dtId = request.getParameter("dtId")==null?"":request.getParameter("dtId").trim();
			khBean.setDtId(dtId);
						
			String tpId = request.getParameter("tpId");
			if (tpId == null)
				tpId = "";
			khBean.setTpId(tpId);

			String qhId = request.getParameter("qhId");
			if (qhId == null)
				qhId = "";
			khBean.setQhId(qhId);

	//		String ghcnId = request.getParameter("ghcnTen");
		//	if (ghcnId.length() == 0)
	//			ghcnId = "";
	//		khBean.setGhcnId(ghcnId);

			String mckId = request.getParameter("mckTen");
			if (mckId == null)
				mckId = "";
			khBean.setMckId(mckId);

			// System.out.println("chietkhau :" + mckId);
			String dienthoai = request.getParameter("dienthoai");
			if (dienthoai == null)
				dienthoai = "";
			khBean.setSodienthoai(dienthoai);

			String masothue = request.getParameter("masothue");
			if (masothue == null)
				masothue = "";
			khBean.setMasothue(masothue);

			String kbhId = request.getParameter("kbhTen");
			if (kbhId == null)
				kbhId = "";
			khBean.setKbhId(kbhId);

			

			String lchId = request.getParameter("lchTen");
			if (lchId == null)
				lchId = "";
			khBean.setLchId(lchId);

			String hchId = request.getParameter("hangcuahangId");
			if (hchId == null)
				hchId = "";
			khBean.setHchId(hchId);

			String vtchId = request.getParameter("vtchTen");
			if (vtchId == null)
				vtchId = "";
			khBean.setVtId(vtchId);
			
			String diadiemId = request.getParameter("diadiemId");
			if (diadiemId == null)
				diadiemId = "";
			khBean.setDiadiemId(diadiemId);
			
			String xuatkhau = request.getParameter("xuatkhau");
			if (xuatkhau == null)
				xuatkhau = "0";
			khBean.setXuatkhau(xuatkhau);
			
			String kokyhopdong = request.getParameter("kokyhopdong");
			if (kokyhopdong == null)
				kokyhopdong = "0";
			khBean.setKhongkyhd(kokyhopdong);
			
			String chucuahieu = request.getParameter("chucuahieu");
			if (chucuahieu == null)
				chucuahieu = "";
			khBean.setChucuahieu(chucuahieu); 
			
			String khoId = request.getParameter("khoId");
			if (khoId == null)
				khoId = "";
			
			khBean.setkhoId(khoId);
			
			System.out.println("kho: "+khoId);
			String mauhoadon = request.getParameter("mauhoadon");
			if (mauhoadon == null)
				mauhoadon = "";
						
			khBean.setmauhd(mauhoadon);
			
			String ptCHIETKHAU = request.getParameter("ptCHIETKHAU");
			if (ptCHIETKHAU == null)
				ptCHIETKHAU = "";
			khBean.setPT_Chietkhau(ptCHIETKHAU);
			
			String mst_canhan = request.getParameter("mst_canhan")==null?"":request.getParameter("mst_canhan").trim();
			khBean.setMst(mst_canhan);
			
			String ngaysinh = request.getParameter("ngaysinh")==null?"":request.getParameter("ngaysinh").trim();
			khBean.setNgaysinh(ngaysinh);
			
			String cmnd = request.getParameter("cmnd")==null?"":request.getParameter("cmnd").trim();
			khBean.setCmnd(cmnd);
			
			String dungmau = request.getParameter("dungmau")==null?"0":request.getParameter("dungmau").trim();
			System.out.println("mau dung la "+dungmau);
			khBean.setDungmau(dungmau);
			
			
			String ngaykyhd = request.getParameter("ngaykyhd")==null?"":request.getParameter("ngaykyhd").trim();
			khBean.setNgaykyHd(ngaykyhd);
		

			String[] tbhId = request.getParameterValues("tbhId");
			String str = "";
			if (tbhId != null)
			{
				for (int i = 0; i < tbhId.length; i++)
					str += tbhId[i] + ",";
				if (str.length() > 0)
					str = str.substring(0, str.length() - 1);
			}
			khBean.setTbhId(str);
			
			String[] ddkdId = request.getParameterValues("ddkdId");
			str = "";
			if (ddkdId != null)
			{
				
				for (int i = 0; i < ddkdId.length; i++)
				{
					str += ddkdId[i] + ",";
					System.out.println(ddkdId.length+"___________"+str);
				}
				if (str.length() > 0)
					str = str.substring(0, str.length() - 1);
			}
			System.out.println("___________"+str);
			khBean.setDdkdId(str);
			
			
			String[] nvgnId = request.getParameterValues("nvgnTen");
			str="";
			if (nvgnId != null)
			{
				for (int i = 0; i < nvgnId.length; i++)
					str += nvgnId[i] + ",";
				if (str.length() > 0)
					str = str.substring(0, str.length() - 1);
			}
			khBean.setNvgnId(str);
			
			String cokhuyenmai = util.antiSQLInspection(request.getParameter("cokhuyenmai"));
			if (cokhuyenmai == null)
				cokhuyenmai = "0";
			khBean.setCokhuyenmai(cokhuyenmai);
			
			
			String[] nkh_khIds = request.getParameterValues("nkh_khList");
			khBean.setNkh_KhIds(nkh_khIds);

			String ngaysua = getDateTime();
			khBean.setNgaysua(ngaysua);

			boolean error = false;
		
			if (kbhId.equals(""))
			{
				khBean.setMessage("Vui lòng chọn kênh bán hàng");
				error = true;
			}

			if (diachi.trim().length() == 0)
			{
				khBean.setMessage("Vui lòng nhập địa chỉ");
				error = true;
			}

			if (khTen.trim().length() == 0)
			{
				khBean.setMessage("Vui lòng nhập tên khách hàng");
				error = true;
			}

			if (tpId.trim().length() == 0)
			{
				khBean.setMessage("Vui lòng nhập tỉnh/thành phố");
				error = true;
			}

			if (tpId.trim().length() != 0 && qhId.trim().length() == 0)
			{
				khBean.setMessage("Vui lòng nhập quận/huyện");
				error = true;
			}
			
			if (maFAST.trim().length() == 0)
			{
				khBean.setMessage("Vui lòng nhập mã FAST");
				error = true;
			}
			
			String action = request.getParameter("action");
			if (!error)
			{
				if (action.equals("save"))
				{
					if (id == null)
					{						
						if (!(khBean.CreateKh(request)))
						{
							khBean.createRS();
							session.setAttribute("khBean", khBean);
							String nextJSP = "/TraphacoHYERP/pages/Distributor/KhachHangNew.jsp";
							response.sendRedirect(nextJSP);
						} else
						{
							IKhachhangList obj = new KhachhangList();
							obj.setUserId(userId);
							obj.init("");
							session.setAttribute("obj", obj);
							String nextJSP = "/TraphacoHYERP/pages/Distributor/KhachHang.jsp";
							response.sendRedirect(nextJSP);
						}

					} 
					else
					{
						if (!(khBean.UpdateKh(request)))
						{
							khBean.createRS();
							session.setAttribute("khBean", khBean);
							String nextJSP = "/TraphacoHYERP/pages/Distributor/KhachHangUpdate.jsp";
							response.sendRedirect(nextJSP);
						} 
						else
						{
							IKhachhangList obj = new KhachhangList();
							obj.setUserId(userId);
							obj.init("");
							session.setAttribute("obj", obj);
							String nextJSP = "/TraphacoHYERP/pages/Distributor/KhachHang.jsp";
							response.sendRedirect(nextJSP);
						}
					}
				} 
				else
				{
					khBean.setUserId(userId);
					khBean.createRS();

					session.setAttribute("khBean", khBean);

					String nextJSP;
					if (id == null)
					{
						nextJSP = "/TraphacoHYERP/pages/Distributor/KhachHangNew.jsp";
					} else
					{
						nextJSP = "/TraphacoHYERP/pages/Distributor/KhachHangUpdate.jsp";
					}
					response.sendRedirect(nextJSP);
				}
			} 
			else
			{
				khBean.setUserId(userId);
				khBean.createRS();

				session.setAttribute("khBean", khBean);

				String nextJSP;
				if (id == null)
				{
					nextJSP = "/TraphacoHYERP/pages/Distributor/KhachHangNew.jsp";
				} else
				{
					nextJSP = "/TraphacoHYERP/pages/Distributor/KhachHangUpdate.jsp";
				}
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
