package geso.traphaco.erp.servlets.nhapkho;

import geso.dms.distributor.util.Utility;
import geso.traphaco.erp.beans.nhapkho.*;
import geso.traphaco.erp.beans.nhapkho.imp.*;

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

public class ErpNhapkhoUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	PrintWriter out;

	public ErpNhapkhoUpdateSvl()
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
			session.setMaxInactiveInterval(30000);

			this.out = response.getWriter();
			Utility util = new Utility();

			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);

			out.println(userId);

			if (userId.length() == 0)
				userId = util.antiSQLInspection(request.getParameter("userId"));

			String id = util.getId(querystring);
			IErpNhapkho nkBean = new ErpNhapkho(id);
			nkBean.setCongtyId((String)session.getAttribute("congtyId"));
			nkBean.setUserId(userId); // phai co UserId truoc khi Init
			nkBean.init();

			String nextJSP;

			if (request.getQueryString().indexOf("display") >= 0)
			{
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhapKhoDisplay.jsp";
			} else
			{
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhapKhoUpdate.jsp";
			}

			session.setAttribute("nkBean", nkBean);
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

			this.out = response.getWriter();
			IErpNhapkho nkBean;

			Utility util = new Utility();
			String id = util.antiSQLInspection(request.getParameter("id"));

			if (id == null)
			{
				nkBean = new ErpNhapkho("");
			} else
			{
				nkBean = new ErpNhapkho(id);
			}

			nkBean.setCongtyId((String)session.getAttribute("congtyId"));
			nkBean.setUserId(userId);

			String ngaygd = util.antiSQLInspection(request.getParameter("ngaynhapkho"));
			if (ngaygd == null || ngaygd == "")
				ngaygd = this.getDateTime();
			nkBean.setNgaynhapkho(ngaygd);

			String ngaychot = util.antiSQLInspection(request.getParameter("ngaychot"));
			if (ngaychot == null || ngaychot == "")
				ngaychot = this.getDateTime();				
	    	nkBean.setNgaychotNV(ngaychot);
	    	
	    	String sophieunhap = util.antiSQLInspection(request.getParameter("sophieunhap"));
			if (sophieunhap == null)
				sophieunhap = "";				
	    	nkBean.setSoPnh(sophieunhap);
	    	
	    	String sodontrahang = util.antiSQLInspection(request.getParameter("sodontrahang"));
			if (sodontrahang == null)
				sodontrahang = "";				
	    	nkBean.setSoDontrahang(sodontrahang);
	    	
	    	String solenhsanxuat = util.antiSQLInspection(request.getParameter("solenhsanxuat"));
			if (solenhsanxuat == null)
				solenhsanxuat = "";				
	    	nkBean.setSoLenhsx(solenhsanxuat);

			String noidungnhap = util.antiSQLInspection(request.getParameter("noidungnhap"));
			if (noidungnhap == null)
				noidungnhap = "";
			nkBean.setNdnId(noidungnhap);

			String loaihanghoa = request.getParameter("loaihh");
			if (loaihanghoa == null)
				loaihanghoa = "";
			nkBean.setLhhId(loaihanghoa);

			String khonhap = request.getParameter("khonhap");
			if (khonhap == null)
				khonhap = "";
			if (khonhap.length() > 0)
			{
				String[] arr = khonhap.split(" - "); // ma kho + ' - ' + quan ly // theo bean
				nkBean.setQuanLyBean(true);
				nkBean.setKhoId(arr[0]);
			}

			/*String Diem = request.getParameter("Diem");
			if (Diem == null)
				Diem = "0";
			nkBean.setDiem(Diem);*/

			// Luu lai san pham
			String[] mahangmua = request.getParameterValues("mahangmua");
			String[] diengiai = request.getParameterValues("diengiai");
			String[] soluongnhan = request.getParameterValues("soluongnhan");
			String[] soluongnhap = request.getParameterValues("soluongnhap");
			String[] solo = request.getParameterValues("solo");
			String[] ngayhethan = request.getParameterValues("ngayhethan");
			String[] dongia = request.getParameterValues("dongia");
			String[] dongiaViet = request.getParameterValues("dongiaViet");
			String[] tiente = request.getParameterValues("tiente");

			List<ISanpham> spList = new ArrayList<ISanpham>();

			if (mahangmua != null)
			{
				ISanpham sanpham = null;
				String[] param = new String[11];
				int m = 0;
				while (m < mahangmua.length)
				{
					if (mahangmua[m] != "")
					{
						param[0] = "";
						param[1] = mahangmua[m];
						param[2] = diengiai[m];
						param[3] = solo[m];
						param[4] = soluongnhan[m];
						param[5] = soluongnhap[m];

						sanpham = new Sanpham(param);
						sanpham.setDongia(dongia[m]);
						sanpham.setDongiaViet(dongiaViet[m]);
						sanpham.setTiente(tiente[m]);
						sanpham.setNgayhethan(ngayhethan[m]);

						if (nkBean.getQuanLyBean())
						{
							String tem = "";
							if(noidungnhap.equals("100004"))
							{
								tem = mahangmua[m] + ".";
							}
							else
							{
								tem = mahangmua[m] + "." + solo[m];
							}
							
							String[] soluong = request.getParameterValues(tem + ".soluong");
							String[] khu = request.getParameterValues(tem + ".khuvuc");
							String[] vitri = request.getParameterValues(tem + ".vitri");

							List<ISanphamCon> spConList = new ArrayList<ISanphamCon>();
							ISanphamCon spCon = null;
							int n = 0;
							while (n < soluong.length)
							{
								if (soluong[n] != "")
								{
									spCon = new SanphamCon(mahangmua[m], soluong[n], khu[n], vitri[n]);
									spConList.add(spCon);

									//System.out.println("Sp Con List: " + mahangmua[m] + " - " + soluong[n] + " - " + khu[n] + " - " + vitri[n]);
								}
								n++;
							}
							sanpham.setSpConList(spConList);
						}
						spList.add(sanpham);
					}
					m++;
				}
			}
			nkBean.setSpList(spList);

			String action = request.getParameter("action");

			if (action.equals("save"))
			{
				if (id == null) // tao moi
				{
					if (!nkBean.createNhapKho())
					{
						nkBean.createRs();

						session.setAttribute("nkBean", nkBean);
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhapKhoNew.jsp";
						response.sendRedirect(nextJSP);
					} 
					else
					{
						nkBean.updateDonnhanhang(sophieunhap, sodontrahang, solenhsanxuat);

						IErpNhapkhoList obj = new ErpNhapkhoList();
						obj.setCongtyId((String)session.getAttribute("congtyId"));
						obj.setUserId(userId);
						obj.init("");

						session.setAttribute("obj", obj);

						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhapKho.jsp";
						response.sendRedirect(nextJSP);
					}
				} 
				else
				{
					if (!nkBean.updateNhapKho())
					{
						nkBean.createRs();

						session.setAttribute("nkBean", nkBean);
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhapKhoUpdate.jsp";
						response.sendRedirect(nextJSP);
					} 
					else
					{
						nkBean.updateDonnhanhang(sophieunhap, sodontrahang, solenhsanxuat);

						IErpNhapkhoList obj = new ErpNhapkhoList();
						obj.setCongtyId((String)session.getAttribute("congtyId"));
						obj.setUserId(userId);
						obj.init("");

						session.setAttribute("obj", obj);

						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhapKho.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			}
			else
			{
				if (action.equals("changePO"))
				{
					nkBean.setSpList(new ArrayList<ISanpham>());
				}

				String nextJSP;
				nkBean.createRs();

				if (id == null)
				{
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhapKhoNew.jsp";
				} else
				{
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhapKhoUpdate.jsp";
				}

				session.setAttribute("nkBean", nkBean);
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
