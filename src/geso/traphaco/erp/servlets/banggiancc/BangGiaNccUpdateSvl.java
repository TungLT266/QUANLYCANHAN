package geso.traphaco.erp.servlets.banggiancc;

import geso.traphaco.center.util.GiuDieuKienLoc;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.banggiancc.IBangGiaNcc;
import geso.traphaco.erp.beans.banggiancc.IBangGiaNccList;
import geso.traphaco.erp.beans.banggiancc.IBangGiaNcc_Sp;
import geso.traphaco.erp.beans.banggiancc.imp.BangGiaNcc;
import geso.traphaco.erp.beans.banggiancc.imp.BangGiaNccList;
import geso.traphaco.erp.beans.banggiancc.imp.BangGiaNcc_Sp;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/BangGiaNccUpdateSvl")
public class BangGiaNccUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	public BangGiaNccUpdateSvl()
	{
		super();
		
	}
	/*
	IBangGiaNccList obj = new BangGiaNccList();
	obj.setUserId(userId);
	obj.init("");
	session.setAttribute("obj", obj);
	String nextJSP = "/TraphacoHYERP/pages/Erp/BangGiaNcc.jsp";
	response.sendRedirect(nextJSP);*/
	
	 public  void VeTrangTong (HttpSession session, HttpServletResponse response,String userId,String ctyId) throws IOException
	{
    	String query = "";
    	IBangGiaNccList obj = null;
    	boolean isExit = false;
		if(session.getAttribute("backAttribute")!= null)
		{
			try {
				if (session.getAttribute("backAttribute").getClass().equals(BangGiaNccList.class))
				{
					isExit = true;
					obj = (IBangGiaNccList)session.getAttribute("backAttribute");
					obj.NewDbUtil();
					BangGiaNccSvl x = new BangGiaNccSvl();
					query =  x.getSearchQuery(null,obj);
					x.destroy();
					session.removeAttribute("backAttribute");
					session.setAttribute("backAttribute", null);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if (isExit == false)
		{
		   obj = new BangGiaNccList();
		}
		obj.setUserId(userId);
		System.out.println("query search = "+ query);
		obj.init(query);
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		session.setAttribute("congtyId", ctyId);
		String nextJSP = "/TraphacoHYERP/pages/Erp/BangGiaNcc.jsp";
		response.sendRedirect(nextJSP);

	 }
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		IBangGiaNcc csxBean;
		
		this.out = response.getWriter();
		Utility util = new Utility();
		
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		
		out.println(userId);
		
		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));
		
		String id = util.getId(querystring);
		
		csxBean = new BangGiaNcc(id);
		
		csxBean.setId(id);
		csxBean.setUserId(userId);
		
		csxBean.init();
		session.setAttribute("csxBean", csxBean);
		
		String nextJSP = "/TraphacoHYERP/pages/Erp/BangGiaNccUpdate.jsp";
		if (querystring.indexOf("display") >= 0)
		{
			nextJSP = "/TraphacoHYERP/pages/Erp/BangGiaNccDisplay.jsp";
		}
		
		response.sendRedirect(nextJSP);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		
		Utility util = new Utility();
		String action = request.getParameter("action");
		String ctyId = (String)session.getAttribute("congtyId");
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		if(action.equals("back"))
		{
			VeTrangTong(session, response, userId, ctyId);
			return;
		}
		
		IBangGiaNcc csxBean;
		String id = util.antiSQLInspection(request.getParameter("id"));
		if (id == null)
		{
			csxBean = new BangGiaNcc();
		} else
		{
			csxBean = new BangGiaNcc(id);
		}
		
		csxBean.setUserId(userId);
		
		String ten = util.antiSQLInspection(request.getParameter("ten"));
		if (ten == null)
			ten = "";
		csxBean.setTen(ten);
		
		String dvkdId = util.antiSQLInspection(request.getParameter("dvkdId"));
		if (dvkdId == null)
			dvkdId = "";
		csxBean.setDvkdId(dvkdId);
		
		String nhomkenhId = util.antiSQLInspection(request.getParameter("nhomkenhId"));
		if (nhomkenhId == null)
			nhomkenhId = "";
		csxBean.setNhomkenhId(nhomkenhId);
		
		String kbhId = util.antiSQLInspection(request.getParameter("kbhId"));
		if (kbhId == null)
			kbhId = "";
		csxBean.setKbhId(kbhId);

		String tungay = util.antiSQLInspection(request.getParameter("tungay"));
		if (tungay == null)
			tungay = "";
		csxBean.setTuNgay(tungay);
		
		
		String chietkhau = util.antiSQLInspection(request.getParameter("chietkhau"));
		if (chietkhau == null)
			chietkhau = "";
		csxBean.setChietKhau(chietkhau.trim().length()<=0?"0":chietkhau.replaceAll(",","") );
		
		String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
		if (trangthai == null)
			trangthai = "0";
		csxBean.setTrangthai(trangthai);
		
		String nccId = util.antiSQLInspection(Doisangchuoi(request.getParameterValues("nccId")));
		if (nccId == null)
			nccId = "";
		csxBean.setNccId(nccId);
		
		String[] nppId = request.getParameterValues("nppId");
		String[] nppIdCks = request.getParameterValues("nppIdck");
		String[] nppChietkhaus = request.getParameterValues("chietkhauNPP");
		String nppIds = this.Doisangchuoi(nppId);
		csxBean.setNppId(nppIds);
		
		csxBean.setNppIdCks(nppIdCks);
		csxBean.setNppChietKhaus(nppChietkhaus);
		
		String[] spId = request.getParameterValues("spId");
		String[] spMa = request.getParameterValues("spMa");
		String[] spTen = request.getParameterValues("spTen");
		String[] giaban = request.getParameterValues("giaban");
		String[] donvi = request.getParameterValues("donvi");
		String[] chonban = request.getParameterValues("chonban");
		String spChonbanIds = "";
		if (chonban != null)
		{
			for (int i = 0; i < chonban.length; i++)
			{
				spChonbanIds += chonban[i] + ",";
			}
			
			if (spChonbanIds.trim().length() > 0)
			{
				spChonbanIds = spChonbanIds.substring(0, spChonbanIds.length() - 1);
			}
		}
		
		List<IBangGiaNcc_Sp> spList = new ArrayList<IBangGiaNcc_Sp>();
		
		if (spId != null)
		{
			for (int i = 0; i < spId.length; i++)
			{
				IBangGiaNcc_Sp sp = new BangGiaNcc_Sp();
				sp.setIdsp(spId[i]);
				sp.setMasp(spMa[i]);
				sp.setTensp(spTen[i]);
				sp.setGiaban(giaban[i].replaceAll(",", ""));
				sp.setDonvi(donvi[i]);
				if (spChonbanIds.indexOf(spId[i]) >= 0)
					sp.setChonban("1");
				spList.add(sp);
			}
		}
		csxBean.setSpList(spList);
		
	
		if (action.equals("save"))
		{
			if (id == null)
			{
				if (!(csxBean.createBanggia()))
				{
					csxBean.createRs();
					session.setAttribute("csxBean", csxBean);
					session.setAttribute("userId", userId);
					
					String nextJSP = "/TraphacoHYERP/pages/Erp/BangGiaNccNew.jsp";
					response.sendRedirect(nextJSP);
				} else
				{
					IBangGiaNccList obj;
					    
					obj = new BangGiaNccList();
				    obj.setUserId(userId);
				    GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
			    	obj.init("");
			    	
				    
			    	
			    	session.setAttribute("obj", obj);  	
		    		session.setAttribute("userId", userId);
				
		    		response.sendRedirect("/TraphacoHYERP/pages/Erp/BangGiaNcc.jsp");	
				}
			} else
			{
				if (!(csxBean.updateBanggia()))
				{
					csxBean.createRs();
					session.setAttribute("csxBean", csxBean);
					session.setAttribute("userId", userId);
					
					String nextJSP = "/TraphacoHYERP/pages/Erp/BangGiaNccUpdate.jsp";
					response.sendRedirect(nextJSP);
				} else
				{
					IBangGiaNccList obj;
				    
					obj = new BangGiaNccList();
				    obj.setUserId(userId);
				    GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
			    	obj.init("");
			    	
				    
			    	
			    	session.setAttribute("obj", obj);  	
		    		session.setAttribute("userId", userId);
				
		    		response.sendRedirect("/TraphacoHYERP/pages/Erp/BangGiaNcc.jsp");	
				}
			}
		} else
		{
			if (action.equals("changeKhachHang"))
				csxBean.setSpList(new ArrayList<IBangGiaNcc_Sp>());
			
			csxBean.createRs();
			session.setAttribute("userId", userId);
			session.setAttribute("csxBean", csxBean);
			String nextJSP = "/TraphacoHYERP/pages/Erp/BangGiaNccNew.jsp";
			if (id != null)
			{
				nextJSP = "/TraphacoHYERP/pages/Erp/BangGiaNccUpdate.jsp";
			}
			response.sendRedirect(nextJSP);
		}
	}
	
	private String Doisangchuoi(String[] checknpp)
	{
		// TODO Auto-generated method stub
		String str = "";
		if (checknpp != null)
		{
			for (int i = 0; i < checknpp.length; i++)
			{
				if (i == 0)
				{
					str = checknpp[i];
				} else
				{
					str = str + "," + checknpp[i];
				}
			}
		}
		return str;
		
	}
	
}
