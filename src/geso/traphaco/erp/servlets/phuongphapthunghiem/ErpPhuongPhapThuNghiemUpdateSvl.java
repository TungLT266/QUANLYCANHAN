package geso.traphaco.erp.servlets.phuongphapthunghiem;

import geso.dms.center.util.Utility;
import geso.traphaco.erp.beans.phuongphapthunghiem.IPhuongPhapThuNghiem;
import geso.traphaco.erp.beans.phuongphapthunghiem.IPhuongPhapThuNghiemList;
import geso.traphaco.erp.beans.phuongphapthunghiem.imp.PhuongPhapThuNghiem;
import geso.traphaco.erp.beans.phuongphapthunghiem.imp.PhuongPhapThuNghiemList;
import geso.traphaco.erp.beans.phuongphapthunghiemthamso.IPhuongPhapThuNghiemTieuDeMau;
import geso.traphaco.erp.beans.phuongphapthunghiemthamso.PhuongPhapThuNghiemThamSo;
import geso.traphaco.erp.beans.phuongphapthunghiemthamso.imp.IPhuongPhapThuNghiemThamSo;
import geso.traphaco.erp.beans.phuongphapthunghiemthamso.imp.PhuongPhapThuNghiemTieuDeMau;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.groovy.tools.shell.commands.ExitCommand;

/**
 * Servlet implementation class ErpPhuongPhapThuNghiemUpdateSvl
 */
@WebServlet("/ErpPhuongPhapThuNghiemUpdateSvl")
public class ErpPhuongPhapThuNghiemUpdateSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ErpPhuongPhapThuNghiemUpdateSvl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		HttpSession session=request.getSession();
		Utility utility=new Utility();
		String querystring = request.getQueryString();
		String userId = utility.getUserId(querystring);
		String mapptn = utility.getId(querystring);
		String action=utility.getAction(querystring);
		
		IPhuongPhapThuNghiem obj=new PhuongPhapThuNghiem();
		obj.setUserId(userId);
		obj.setPK_SEQ(mapptn);
		
		if(action==null)action="";
		if(action.equals("update"))
		{
			obj.creates();
			obj.init();
			session.setAttribute("obj",obj);
			String nextJSP="/TraphacoHYERP/pages/Erp/ErpPhuongPhapThuNgiemUpdate.jsp";
			response.sendRedirect(nextJSP);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		Utility utility=new Utility();
		HttpSession session=request.getSession();
		IPhuongPhapThuNghiem obj=new PhuongPhapThuNghiem();
		String maPPTN;
		maPPTN=request.getParameter("pk_seq");
		if(maPPTN==null){maPPTN="";}
		obj.setPK_SEQ(maPPTN);
		
		String ma=utility.antiSQLInspection(request.getParameter("mapptn"));
		obj.setMaPPTN(ma);
		
		String userId=utility.antiSQLInspection(request.getParameter("userId"));
		obj.setUserId(userId);
		
		String tenvt=utility.antiSQLInspection(request.getParameter("tenvt"));
		obj.setTenPPTN(tenvt);
		
		String mota=utility.antiSQLInspection(request.getParameter("mota"));
		obj.setDienGiai(mota);
		
		String loaitieuchi=utility.antiSQLInspection(request.getParameter("loaitieuchi"));
		obj.setMaLoaiTieuChi(loaitieuchi);
		
		String yeucaukythuat=utility.antiSQLInspection(request.getParameter("yeucaukythuat"));
		obj.setMaYeuCauKyThuat(yeucaukythuat);
		
		String sltsnl=utility.antiSQLInspection(request.getParameter("sl"));
		if(sltsnl==null) sltsnl="";
		obj.setSoLuongTs(sltsnl);
		
		String slmau=utility.antiSQLInspection(request.getParameter("slmau"));
		if(slmau==null) slmau="";
		obj.setSoLuongMau(slmau);
		
//		String sanphamid=utility.antiSQLInspection(request.getParameter("sanphamid"));
//		obj.setMasanpham(sanphamid);
		
		String sanphamid = "";
		String[] sanphamids = request.getParameterValues("sanphamid");
		if (sanphamids != null) {
			for (int index = 0; index < sanphamids.length; index++) {
				if (sanphamid.trim().length()==0) {
					sanphamid += sanphamids[index];
				} else {
					sanphamid += "," + sanphamids[index];
				}
			}
			obj.setMasanpham(sanphamid);
		}
		
		
		String trangthai=utility.antiSQLInspection(request.getParameter("trangthai"));
		System.out.println("==================="+trangthai);
		if(trangthai!=null)
		{
			obj.setTrangThai("1");
		}
		else{
			obj.setTrangThai("0");
		}
		
		List<IPhuongPhapThuNghiemThamSo> listThamSo=new ArrayList<IPhuongPhapThuNghiemThamSo>();
		String[] popupThamSo=request.getParameterValues("popupThamSo");
		String[] kyhieuThamSo=request.getParameterValues("kyhieuThamSo");
		String[] congthuc=request.getParameterValues("congthuc");
		
		
		
		for (int i = 0; i < popupThamSo.length; i++) {
			IPhuongPhapThuNghiemThamSo ts=new PhuongPhapThuNghiemThamSo();
			ts.setPopupThamSo(popupThamSo[i]);
			ts.setKyhieuThamso(kyhieuThamSo[i]);
			
			if(congthuc[i]==null)congthuc[i]="";
			ts.setCongthuc(congthuc[i]);
			
			String dvt=(String)request.getParameter("dvdl_"+i);
			if(dvt==null)dvt="";
			ts.setMaDVDL(dvt);
			String loaitick=(String)request.getParameter("loaitick_"+i);
			if(loaitick == null)
			{
				ts.setLoaiTick("0");
			}else
			{
				ts.setLoaiTick("1");
			}
			
			
			String thamsogroup=(String)request.getParameter("thamsogroup_"+i);
			if(thamsogroup == null)
			{
				ts.setThamSoPopup("0");
			}else
			{
				ts.setThamSoPopup("1");;
			}
			String min=(String)request.getParameter("min_"+i);
			if(min == null)
			{
				ts.setMin("0");
			}else
			{
				ts.setMin("1");
			}
			String max=(String)request.getParameter("max_"+i);
			if(max == null)
			{
				ts.setMax("0");
			}else
			{
				ts.setMax("1");
			}
			String avg=(String)request.getParameter("avg_"+i);
			if(avg == null)
			{
				ts.setAvg("0");
			}else
			{
				ts.setAvg("1");
			}
			String sum=(String)request.getParameter("sum_"+i);
			if(sum == null)
			{
				ts.setSum("0");
			}else
			{
				ts.setSum("1");
			}
			
			listThamSo.add(ts);
			
		}
		obj.setListThamSo(listThamSo);
		
		
		
		List<IPhuongPhapThuNghiemTieuDeMau> listTieuDeMau=new ArrayList<IPhuongPhapThuNghiemTieuDeMau>();
		String[] tieudemau=request.getParameterValues("tieudemau");
		String[] stt=request.getParameterValues("sttTDM");
		for (int i = 0; i < tieudemau.length; i++) {
			IPhuongPhapThuNghiemTieuDeMau tdm=new PhuongPhapThuNghiemTieuDeMau();
			tdm.setTieudemau(tieudemau[i]);
			tdm.setStt(stt[i]);
			listTieuDeMau.add(tdm);
		}
		obj.setListTieuDeMau(listTieuDeMau);
		
		String action=request.getParameter("action");
		if(action==null)action="";
		if(action.equals("taomoi")){
			if(!obj.save())
			{
				obj.creates();
				session.setAttribute("obj", obj);
				
				String nextJSP="/TraphacoHYERP/pages/Erp/ErpPhuongPhapThuNgiemNew.jsp";
				response.sendRedirect(nextJSP);
			}
			else
			{
				IPhuongPhapThuNghiemList obj1=new PhuongPhapThuNghiemList();
				obj1.setUserId(userId);
				obj1.init();
				session.setAttribute("obj",obj1);
				String nextJSP="/TraphacoHYERP/pages/Erp/ErpPhuongPhapThuNghiemList.jsp";
				response.sendRedirect(nextJSP);
			}
		}else if (action.equals("capnhat")) {
			if(!obj.update())
			{
				obj.creates();
				session.setAttribute("obj", obj);
				String nextJSP="/TraphacoHYERP/pages/Erp/ErpPhuongPhapThuNgiemUpdate.jsp";
				response.sendRedirect(nextJSP);
			}
			else
			{
				IPhuongPhapThuNghiemList obj1=new PhuongPhapThuNghiemList();
				obj1.setUserId(userId);
				obj1.init();
				session.setAttribute("obj",obj1);
				String nextJSP="/TraphacoHYERP/pages/Erp/ErpPhuongPhapThuNghiemList.jsp";
				response.sendRedirect(nextJSP);
			}
		}
		
	}

}
