package geso.traphaco.erp.servlets.congdoansanxuat;

import geso.dms.center.util.Utility;
import geso.traphaco.erp.beans.congdoansanxuatgiay.IErpCongDoanSanXuatGiay;
import geso.traphaco.erp.beans.congdoansanxuatgiay.IErpCongDoanSanXuatGiayList;
import geso.traphaco.erp.beans.congdoansanxuatgiay.ITaisan;
import geso.traphaco.erp.beans.congdoansanxuatgiay.ITieuchikiemdinh;
import geso.traphaco.erp.beans.congdoansanxuatgiay.imp.ErpCongDoanSanXuatGiay;
import geso.traphaco.erp.beans.congdoansanxuatgiay.imp.ErpCongDoanSanXuatGiayList;
import geso.traphaco.erp.beans.congdoansanxuatgiay.imp.Taisan;
import geso.traphaco.erp.beans.congdoansanxuatgiay.imp.Tieuchikiemdinh;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

@WebServlet("/ErpCongDoanSanXuatGiayUpdateSvl")
public class ErpCongDoanSanXuatGiayUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;

	public ErpCongDoanSanXuatGiayUpdateSvl()
	{
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		IErpCongDoanSanXuatGiay cdsxBean;

		this.out = response.getWriter();
		Utility util = new Utility();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		String ctyId = (String) session.getAttribute("congtyId");

		out.println(userId);

		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));

		String id = util.getId(querystring);

		cdsxBean = new ErpCongDoanSanXuatGiay(id);
		cdsxBean.setCtyId(ctyId);

		cdsxBean.setId(id);
		cdsxBean.setUserId(userId);

		cdsxBean.init();
		cdsxBean.createRs();
		session.setAttribute("cdsxBean", cdsxBean);

		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpCongDoanSanXuatGiayUpdate.jsp";
		if (querystring.indexOf("display") >= 0)
		{
			nextJSP = "/TraphacoHYERP/pages/Erp/ErpCongDoanSanXuatGiayDisplay.jsp";
		}

		response.sendRedirect(nextJSP);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String ctyId = (String) session.getAttribute("congtyId");

		IErpCongDoanSanXuatGiay cdsxBean;

		Utility util = new Utility();
		String id = util.antiSQLInspection(request.getParameter("id"));
		if (id == null)
		{
			cdsxBean = new ErpCongDoanSanXuatGiay();
		} else
		{
			cdsxBean = new ErpCongDoanSanXuatGiay(id);
		}

		cdsxBean.setCtyId(ctyId);
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		cdsxBean.setUserId(userId);

		String SanXuat = util.antiSQLInspection(request.getParameter("SanXuat"));
		if (SanXuat == null)
			SanXuat = "0";
		else
			SanXuat = "1";
		cdsxBean.setSanXuat(SanXuat);

		String sanpham = util.antiSQLInspection(request.getParameter("sanpham"));
		String sanphamId = null;
		String sanphamMa = "";
		System.out.println("___San pham la_____ " + sanpham);
		if (sanpham != null && sanpham.split("_").length == 2)
		{
			sanphamId = sanpham.split("_")[0];
			if (sanphamId.equals("-1"))
				sanphamId = null;
			sanphamMa = sanpham.split("_")[1];
		} else
			sanpham = "";
		cdsxBean.setSpSelected(sanpham);
		cdsxBean.setSanPhamId(sanphamId);
		cdsxBean.setSanPhamMa(sanphamMa);
		String dmvtId = util.antiSQLInspection(request.getParameter("dmvtId"));
		if (dmvtId == null || dmvtId.trim().length() <= 0)
			dmvtId = "NULL";
		cdsxBean.setDmvtId(dmvtId);
		
		String dtsxId = util.antiSQLInspection(request.getParameter("dtsxId"));
		if(dtsxId == null) dtsxId = "";
		cdsxBean.setDaytruyenSXId(dtsxId);

		String dinhluong = util.antiSQLInspection(request.getParameter("dinhluong"));
		if (dinhluong == null)
			dinhluong = "0";
		cdsxBean.setDinhluong(dinhluong);

		String dinhtinh = util.antiSQLInspection(request.getParameter("dinhtinh"));
		if (dinhtinh == null)
			dinhtinh = "0";
		cdsxBean.setDinhtinh(dinhtinh);

		String kiemdinhchatluong = "0";
		if (dinhluong.equals("1") || dinhtinh.equals("1"))
			kiemdinhchatluong = "1";
		cdsxBean.setKiemdinhchatluong(kiemdinhchatluong);
				
		String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
		if (diengiai == null)
			diengiai = "";
		cdsxBean.setDiengiai(diengiai);
		
		String yckn = util.antiSQLInspection(request.getParameter("yckn"));
		if (yckn == null)
			yckn = "0";
		cdsxBean.setYckn(yckn);

		String sonhancong = util.antiSQLInspection(request.getParameter("sonhancong"));
		if (sonhancong == null)
			sonhancong = "";
		cdsxBean.setSonhancong(sonhancong);
		
		String[] loaihosoknList = request.getParameterValues("loaihosokn");
		String loaihosokn = "";
		if(loaihosoknList != null){
			for(int i = 0; i < loaihosoknList.length; i++){
				loaihosokn += loaihosoknList[i] + ",";
			}
		}
		if(loaihosokn.length() > 0){
			loaihosokn = loaihosokn.substring(0, loaihosokn.length() - 1);
			cdsxBean.setLhsknList(loaihosokn);
		}

		String nmIds = util.antiSQLInspection(request.getParameter("nmIds"));
		if (nmIds == null)
			nmIds = "";
		cdsxBean.setNhamayIds(nmIds);

		String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
		if (trangthai == null)
			trangthai = "0";
		cdsxBean.setTrangthai(trangthai);
		
		/*String loaimauinSxId = util.antiSQLInspection(request.getParameter("loaimauinSxId"));
		if (loaimauinSxId == null)
			loaimauinSxId = "";
		cdsxBean.setLoaimauinSxId(loaimauinSxId);*/
		
		

		String[] tbIds = request.getParameterValues("tbIds");
		if (tbIds != null)
		{
			String str = "";
			for (int i = 0; i < tbIds.length; i++)
			{
				str += tbIds[i] + ",";
			}
			if (str.trim().length() > 0)
			{
				str = str.substring(0, str.length() - 1);
				cdsxBean.setTbIds(str);
			}
		}

		String[] sttpb = request.getParameterValues("sttpb");
		cdsxBean.setSttpb(sttpb);
		String[] phongbanIds = request.getParameterValues("phongban");
		cdsxBean.setPhongbanId(phongbanIds);
		String[] giaidoanIds = request.getParameterValues("giaidoan");
		cdsxBean.setGiaidoanId(giaidoanIds);
		
		List<ITieuchikiemdinh> tckdList = new ArrayList<ITieuchikiemdinh>();
		if (kiemdinhchatluong.equals("1"))
		{
			String[] tieuchi = request.getParameterValues("tieuchi_dinhluong");
			String[] toantu = request.getParameterValues("toantu");
			String[] giatrichuan = request.getParameterValues("giatrichuan");
			String[] tieuchi_dinhtinh = request.getParameterValues("tieuchi_dinhtinh");
			if (tieuchi != null)
			{
				for (int i = 0; i < tieuchi.length; i++)
				{
					if (tieuchi[i] != "")
					{
						ITieuchikiemdinh tckd = new Tieuchikiemdinh();
						tckd.setTieuchi(tieuchi[i]);
						tckd.setToantu(toantu[i]);
						tckd.setGiatrichuan(giatrichuan[i]);
						tckdList.add(tckd);
					}
				}
				cdsxBean.setTieuchikiemdinhList(tckdList);
			}
			if(tieuchi_dinhtinh!=null)
			{
				cdsxBean.setTieuchi_Dinhtinh(tieuchi_dinhtinh);
			}
		}
		List<ITaisan> tsList = new ArrayList<ITaisan>();
		String[] taisanList = request.getParameterValues("pk_seq_taisan");
		if(taisanList != null){
			for(int i = 0 ; i < taisanList.length ; i++){
				if(taisanList[i] != ""){
					ITaisan tscd = new Taisan();
					tscd.setPk_seq(taisanList[i]);
					tsList.add(tscd);
				}
			}
			cdsxBean.setLts(tsList);
		}
		String action = request.getParameter("action");
		if("autoloadts".equals(action)){
			String mats=request.getParameter("term");
			ArrayList<String> result = new ArrayList<String>();
			List<ITaisan> taisan=cdsxBean.gettaisan(mats);
			Iterator dataList=taisan.iterator();
			if(dataList!=null){
				while(dataList.hasNext()){
					ITaisan ts = (Taisan)dataList.next();
					String kq = ts.getPk_seq() + "|" + ts.getMa() + "|" + ts.getTen();
					result.add(kq);
				}
			}
			String searchList = new Gson().toJson(result);
			response.getWriter().write(searchList);
			return ;
		}
		if (action.equals("save"))
		{
			if (id == null)
			{
				if (!(cdsxBean.createCumsanxuat()))
				{
					cdsxBean.createRs();
					session.setAttribute("cdsxBean", cdsxBean);
					session.setAttribute("userId", userId);

					String nextJSP = "/TraphacoHYERP/pages/Erp/ErpCongDoanSanXuatGiayNew.jsp";
					response.sendRedirect(nextJSP);
				} else
				{
					IErpCongDoanSanXuatGiayList obj = new ErpCongDoanSanXuatGiayList();
					obj.setCtyId(ctyId);
					obj.init("");
					cdsxBean.DbClose();
					session.setAttribute("cdsxList", obj);
					String nextJSP = "/TraphacoHYERP/pages/Erp/ErpCongDoanSanXuatGiay.jsp";
					response.sendRedirect(nextJSP);
				}
			} else
			{
				if (!(cdsxBean.updateCumsanxuat()))
				{
					cdsxBean.createRs();
					session.setAttribute("cdsxBean", cdsxBean);
					session.setAttribute("userId", userId);

					String nextJSP = "/TraphacoHYERP/pages/Erp/ErpCongDoanSanXuatGiayUpdate.jsp";
					response.sendRedirect(nextJSP);
				} else
				{
					IErpCongDoanSanXuatGiayList obj = new ErpCongDoanSanXuatGiayList();
					obj.setCtyId(ctyId);
					obj.init("");
					cdsxBean.DbClose();
					session.setAttribute("cdsxList", obj);
					String nextJSP = "/TraphacoHYERP/pages/Erp/ErpCongDoanSanXuatGiay.jsp";
					response.sendRedirect(nextJSP);
				}
			}
		} else
		{
			cdsxBean.createRs();
			session.setAttribute("userId", userId);
			session.setAttribute("cdsxBean", cdsxBean);
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpCongDoanSanXuatGiayNew.jsp";
			if (id != null)
			{
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpCongDoanSanXuatGiayUpdate.jsp";
			}

			response.sendRedirect(nextJSP);
		}
	}

}
