package geso.traphaco.erp.servlets.timnhacc;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;
import geso.traphaco.erp.beans.timnhacc.ISanpham;
import geso.traphaco.erp.beans.timnhacc.imp.Sanpham;
import geso.traphaco.erp.beans.timnhacc.INhacungcap;
import geso.traphaco.erp.beans.timnhacc.ITimnhacc;
import geso.traphaco.erp.beans.timnhacc.ITimnhaccList;
import geso.traphaco.erp.beans.timnhacc.imp.Nhacungcap;
import geso.traphaco.erp.beans.timnhacc.imp.Timnhacc;
import geso.traphaco.erp.beans.timnhacc.imp.TimnhaccList;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class TimnhaccUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public TimnhaccUpdateSvl()
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
			ITimnhacc timnccBean = new Timnhacc(id);
			timnccBean.setCongtyId((String)session.getAttribute("congtyId"));
			timnccBean.setUserId(userId); // phai co UserId truoc khi Init
			timnccBean.init();
			
			String task = request.getParameter("task");
			if(task == null)
				task = "";

			
				
			if (request.getQueryString().indexOf("display") >= 0)
			{
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpTimNccDisplay.jsp";
			}
			else
			{
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpTimNccUpdate.jsp";
			}
			
			session.setAttribute("timnccBean", timnccBean);
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
			
			ITimnhacc timnccBean;
			Utility util = new Utility();
			String id = util.antiSQLInspection(request.getParameter("id"));
			System.out.println("ID" + id);
			if (id == null)
			{
				timnccBean = new Timnhacc("");
			}
			else
			{
				timnccBean = new Timnhacc(id);
			}
			String contyId = (String)session.getAttribute("congtyId");
			session.setAttribute("congtyId", contyId);
			timnccBean.setCongtyId(contyId);
			timnccBean.setUserId(userId);
			
			String dnmhId = util.antiSQLInspection(request.getParameter("dnmhId"));
			if (dnmhId == null)
				dnmhId = "";
			session.setAttribute("dnmhId", dnmhId);
			timnccBean.setDnmhId(dnmhId);
			System.out.println("dnmhId " + dnmhId);
			
			String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
			if (trangthai == null)
				trangthai = "";
			session.setAttribute("trangthai", trangthai);
			timnccBean.setTrangthai(trangthai);
			
			String loaihh = util.antiSQLInspection(request.getParameter("loaihh"));
			if (loaihh == null)
				loaihh = "0";
			timnccBean.setLoaihh(loaihh);
			
			String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
			if (diengiai == null)
				diengiai = "";
			session.setAttribute("diengiai", diengiai);
			timnccBean.setDiengiai(diengiai);
			
			String hinhthuc = util.antiSQLInspection(request.getParameter("hinhthuc"));
			if (hinhthuc == null)
				hinhthuc = "0";
			timnccBean.setHinhthuc(hinhthuc);
			
			String nguongochh = util.antiSQLInspection(request.getParameter("nguongochh"));
			if (nguongochh == null)
				nguongochh = "TN";
			timnccBean.setNguongochh(nguongochh);
			
			if(hinhthuc.equals("0"))
			{
				String[] sparr = request.getParameterValues("sanpham");
				String[] tensparr = request.getParameterValues("tensanpham");
				String[] diengiaisparr = request.getParameterValues("diengiaisanpham");
				if(sparr != null){
					List<ISanpham> lstsp = new ArrayList<ISanpham>();
					for (int i = 0; i < sparr.length; i++) {
						ISanpham sp = new Sanpham();
						sp.setId(sparr[i]);
						sp.setTensanpham(tensparr[i]);
						sp.setDiengiai(diengiaisparr[i]);
						
						System.out.println("sp Id = " + sparr[i] + ", tensp = " + tensparr[i]);
						
						String[] idncc = request.getParameterValues("idncc"+i);
						
						if(idncc != null)
						{
							List<INhacungcap> lstncc = new ArrayList<INhacungcap>();
							String[] mancc = request.getParameterValues("mancc"+i);
							String[] tenncc = request.getParameterValues("tenncc"+i);
							String[] tongluong = request.getParameterValues("tongluong"+i);
							String[] sopo = request.getParameterValues("sopo"+i);
							String[] chonncc = request.getParameterValues("chonncc"+i);
							
							int k = 0;
							for (int j = 0; j < idncc.length; j++) {
								INhacungcap ncc = new Nhacungcap();
								ncc.setId(idncc[j]);
								ncc.setMa(mancc[j]);
								ncc.setTen(tenncc[j]);
								ncc.setTongluong(tongluong[j]);
								ncc.setSopo(sopo[j]);
								if(chonncc != null)
									if(k < chonncc.length)
									{
										if(chonncc[k].trim().equals(idncc[j].trim()))
										{
											ncc.setChon(chonncc[k]);
											k++;
										}
									}
								lstncc.add(ncc);
							}
							sp.setNhacungcap(lstncc);
						}
						lstsp.add(sp);
					}
					timnccBean.setSpList(lstsp);
				}
			}
			else
			{
				String[] nccarr = request.getParameterValues("nccid");
				String nccids = "";
				if (nccarr != null)
				{
					for (int i = 0; i < nccarr.length; i++)
					{
						nccids += nccarr[i] + ",";
					}
					if (nccids.length() > 0)
						nccids = nccids.substring(0, nccids.length() - 1);
				}
				timnccBean.setNccIds(nccids);
			}
			
			String action = request.getParameter("action");
			
			if (action.equals("save") )
			{
				if(dnmhId == ""){
					timnccBean.setMessage("Vui lòng chọn đề nghị mua hàng");
					timnccBean.createRs();
					String nextJSP;
					if (id == null)
					{
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpTimNccNew.jsp";
					}
					else
					{
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpTimNccUpdate.jsp";
					}
					session.setAttribute("timnccBean", timnccBean);
					response.sendRedirect(nextJSP);				
					return;
				}

				if (id == null) // tao moi
				{
					if (!timnccBean.createTimNcc())
					{
						timnccBean.createRs();
						session.setAttribute("timnccBean", timnccBean);
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpTimNccNew.jsp";
						response.sendRedirect(nextJSP);
						return;
					}
					else
					{
						ITimnhaccList obj = new TimnhaccList();
						obj.setCongtyId((String)session.getAttribute("congtyId"));
						obj.setUserId(userId);
						String searchQuery=util.getSearchFromHM(userId,this.getServletName(), session);
						geso.traphaco.center.util.GiuDieuKienLoc.setParamsToOject(obj, searchQuery);
						obj.init("");
						session.setAttribute("obj", obj);
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpTimNcc.jsp";
						response.sendRedirect(nextJSP);
						return;
					}
				}
				else
				{
					if (!timnccBean.updateTimNcc())
					{
						timnccBean.createRs();
						session.setAttribute("timnccBean", timnccBean);
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpTimNccUpdate.jsp";
						response.sendRedirect(nextJSP);
						return;
					}
					else
					{
						ITimnhaccList obj = new TimnhaccList();
						obj.setCongtyId((String)session.getAttribute("congtyId"));
						obj.setUserId(userId);
						String searchQuery=util.getSearchFromHM(userId,this.getServletName(), session);
						geso.traphaco.center.util.GiuDieuKienLoc.setParamsToOject(obj, searchQuery);
						obj.init("");
						session.setAttribute("obj", obj);
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpTimNcc.jsp";
						response.sendRedirect(nextJSP);
						return;
					}
				}
			}
			else
			{
				String nextJSP;
				timnccBean.createRs();
				if (id == null)
				{
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpTimNccNew.jsp";
				}
				else
				{
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpTimNccUpdate.jsp";
				}
				session.setAttribute("timnccBean", timnccBean);
				response.sendRedirect(nextJSP);
			}
		}
	}
}
