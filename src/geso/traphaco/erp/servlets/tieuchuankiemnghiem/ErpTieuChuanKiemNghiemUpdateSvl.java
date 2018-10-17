package geso.traphaco.erp.servlets.tieuchuankiemnghiem;

import geso.traphaco.center.util.Utility;

import java.io.IOException;
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

import geso.traphaco.erp.beans.erp_chinhanh.IErp_chinhanh;
import geso.traphaco.erp.beans.erp_chinhanh.IErp_chinhanhList;
import geso.traphaco.erp.beans.erp_chinhanh.imp.*;
import geso.traphaco.erp.beans.tieuchuankiemnghiem.IErpTieuChuanKiemNghiem;
import geso.traphaco.erp.beans.tieuchuankiemnghiem.IErpTieuChuanKiemNghiemList;
import geso.traphaco.erp.beans.tieuchuankiemnghiem.IItemLoader;
import geso.traphaco.erp.beans.tieuchuankiemnghiem.imp.ErpTieuChuanKiemNghiem;
import geso.traphaco.erp.beans.tieuchuankiemnghiem.imp.ErpTieuChuanKiemNghiemList;
import geso.traphaco.erp.beans.tieuchuankiemnghiem.imp.ItemLoader;


public class ErpTieuChuanKiemNghiemUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;


	public ErpTieuChuanKiemNghiemUpdateSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		Utility util = new Utility();

		String querystring = request.getQueryString();
		String userid = util.getUserId(querystring);
		System.out.println("User id is " + userid);

		String id = util.getId(querystring);
		System.out.println("ID is " + id);
		
		IErpTieuChuanKiemNghiem tcknBean = new ErpTieuChuanKiemNghiem();
		
		tcknBean.setId(id);

		if (userid.length() == 0)
			userid = util.getUserId(querystring);
		String ctyId = (String)session.getAttribute("congtyId");
		tcknBean.setCongtyId(ctyId);
		
		String nppId = util.getIdNhapp(userid);
	    if(nppId == null)
	    	nppId = "";
	    tcknBean.setNppId(nppId);
	    
	    tcknBean.createRs();
	    tcknBean.init();
	    
		session.setAttribute("tcknBean", tcknBean);
		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpTieuChuanKiemNghiemUpdate.jsp");
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();

		Utility util = new Utility();

		IErpTieuChuanKiemNghiem tcknBean = new ErpTieuChuanKiemNghiem();
		String nextJSP ="";
		
		String ctyId = (String)session.getAttribute("congtyId");
		tcknBean.setCongtyId(ctyId);
		
		String id = util.antiSQLInspection(request.getParameter("id"));
		if (id == null) id = "";
		tcknBean.setId(id);
		
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		if (userId == null) userId = "";
		tcknBean.setUserId(userId);
		
		String ma = util.antiSQLInspection(request.getParameter("ma"));
		if (ma == null) ma = "";
		tcknBean.setMa(ma);
		
		String ngaycap = util.antiSQLInspection(request.getParameter("ngaycap"));
		if (ngaycap == null) ngaycap = "";
		tcknBean.setNgaycap(ngaycap);
		
		String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
		if (diengiai == null) diengiai = "";
		tcknBean.setDienGiai(diengiai);
		
		String loaimauknId = util.antiSQLInspection(request.getParameter("loaimauknId"));
		if (loaimauknId == null) loaimauknId = "";
		tcknBean.setLoaimauknId(loaimauknId);
		
		String sanphamId = util.antiSQLInspection(request.getParameter("sanphamId"));
		if (sanphamId == null) sanphamId = "";
		tcknBean.setSanphamId(sanphamId);
		
		String bieumauhsId = "";
		String[] bieumauhsIds = request.getParameterValues("bieumauhsId");
		if (bieumauhsIds != null) {
			for (int index = 0; index < bieumauhsIds.length; index++) {
				if (bieumauhsId.trim().length()==0) {
					bieumauhsId += bieumauhsIds[index];
				} else {
					bieumauhsId += "," + bieumauhsIds[index];
				}
			}
			tcknBean.setBieumauhsId(bieumauhsId);
		}
		
		
		String hoatdong = util.antiSQLInspection(request.getParameter("hoatdong"));
		if (hoatdong == null) hoatdong = "0";
		tcknBean.setIshoatdong(hoatdong);
		
		List<IItemLoader> yeucauList = new ArrayList<IItemLoader>();
		List<IItemLoader> thietbiList = new ArrayList<IItemLoader>();
		
		String ycId="0";
		String tbId="0";
		String[] yeucauIDs = request.getParameterValues("yeucauID");
		String[] yeucauTens = request.getParameterValues("yeucauTen");
		if (yeucauIDs != null) {
			for (int index = 0; index < yeucauIDs.length; index++) {
				if(yeucauIDs[index].trim().length()>0) {
					ItemLoader yeucauItem = new ItemLoader();
					yeucauItem.setPk_seq(yeucauIDs[index]);
					yeucauItem.setTen(yeucauTens[index]);
					
					if(ycId.trim().length()==0) {
						ycId += yeucauIDs[index];
					} else {
						ycId += "," + yeucauIDs[index];
					}
					
					String[] ppTNIds = request.getParameterValues("ppTNId"+index);
					String[] ppTNTens = request.getParameterValues("ppTNTen"+index);
					String[] ppTNChons = request.getParameterValues("ppTNChon"+index);
					List<IItemLoader> ppTNList = new ArrayList<IItemLoader>();
					if (ppTNIds != null) {
						for (int index_1 = 0; index_1 < ppTNIds.length; index_1++) {
							if(ppTNIds[index_1].trim().length()>0) {
								ItemLoader ppTNItem = new ItemLoader();
								ppTNItem.setPk_seq(ppTNIds[index_1]);
								ppTNItem.setTen(ppTNTens[index_1]);
								ppTNItem.setChon(ppTNChons[index_1]);
								ppTNList.add(ppTNItem);
							}
						}
					}
					yeucauItem.setPpThuNghiemList(ppTNList);
					yeucauList.add(yeucauItem);
				}
			}
			tcknBean.setYeucauIDSS(ycId);
			tcknBean.setYeuCauKNList(yeucauList);
		}
		
		String[] thietbiIDs = request.getParameterValues("thietbiID");
		String[] thietbiMas = request.getParameterValues("thietbiMa");
		String[] thietbiTens = request.getParameterValues("thietbiTen");
		String[] thietbiGhichu = request.getParameterValues("thietbiGhichu");
		if (thietbiIDs != null) {
			for (int index = 0; index < thietbiIDs.length; index++) {
				if(thietbiIDs[index].trim().length()>0) {
					ItemLoader thietbiItem = new ItemLoader();
					thietbiItem.setPk_seq(thietbiIDs[index]);
					thietbiItem.setTen(thietbiTens[index]);
					thietbiItem.setMa(thietbiMas[index]);
					thietbiItem.setGhiChu(thietbiGhichu[index]);
					thietbiList.add(thietbiItem);
					
					if(tbId.trim().length()==0) {
						tbId += thietbiIDs[index];
					} else {
						tbId += "," + thietbiIDs[index];
					}
				}
			}
			tcknBean.setThietbiIDSS(tbId);
			tcknBean.setThietbiList(thietbiList);
		}
		
		String action = request.getParameter("action");

		if (action.equals("save"))
		{
			if (id == null || id.trim().length()==0)
			{
				if (!tcknBean.createTCKN())
				{
					tcknBean.createRs();
					session.setAttribute("userId", userId);
					session.setAttribute("tcknBean", tcknBean);
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpTieuChuanKiemNghiemNew.jsp";
					response.sendRedirect(nextJSP);
				} 
				else
				{
					tcknBean.DBclose();
					IErpTieuChuanKiemNghiemList cnList = new ErpTieuChuanKiemNghiemList();
					cnList.setUserId(userId);
				    cnList.init();
					session.setAttribute("obj", cnList);
					nextJSP="/TraphacoHYERP/pages/Erp/ErpTieuChuanKiemNghiem.jsp";
					response.sendRedirect(nextJSP);
				}
			}
			else
			{
				if (!tcknBean.UpdateTCKN())
				{
					tcknBean.createRs();
					session.setAttribute("userId", userId);
					session.setAttribute("tcknBean", tcknBean);
					nextJSP="/TraphacoHYERP/pages/Erp/ErpTieuChuanKiemNghiemUpdate.jsp";
					response.sendRedirect(nextJSP);
				} 
				else
				{
					tcknBean.DBclose();
					IErpTieuChuanKiemNghiemList cnList = new ErpTieuChuanKiemNghiemList();
					cnList.setUserId(userId);
				    cnList.init();
					session.setAttribute("obj", cnList);
					nextJSP="/TraphacoHYERP/pages/Erp/ErpTieuChuanKiemNghiem.jsp";
					response.sendRedirect(nextJSP);
				}
			}
		} else if (action.equals("loadPP")){
			
			String item = util.antiSQLInspection(request.getParameter("item"));
			if (item == null) item = "";
			tcknBean.setItem(item);
			
			tcknBean.createRs();
			tcknBean.loadPP(item);
			session.setAttribute("userId", userId);
			session.setAttribute("tcknBean", tcknBean);
			if(id == null || id.trim().length()==0)
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpTieuChuanKiemNghiemNew.jsp";
			else
				nextJSP="/TraphacoHYERP/pages/Erp/ErpTieuChuanKiemNghiemUpdate.jsp";
			
			response.sendRedirect(nextJSP);
		} else {
			
			tcknBean.createRs();
			session.setAttribute("userId", userId);
			session.setAttribute("tcknBean", tcknBean);
			if(id == null || id.trim().length()==0)
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpTieuChuanKiemNghiemNew.jsp";
			else
				nextJSP="/TraphacoHYERP/pages/Erp/ErpTieuChuanKiemNghiemUpdate.jsp";
			
			response.sendRedirect(nextJSP);

		}
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
}
