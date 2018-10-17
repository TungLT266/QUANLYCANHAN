package geso.traphaco.erp.servlets.hosokiemnghiem;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.hosokiemnghiem.IErpHoSoKiemNghiemList;
import geso.traphaco.erp.beans.hosokiemnghiem.IHoSoKiemNghiem;
import geso.traphaco.erp.beans.hosokiemnghiem.imp.ErpHoSoKiemNghiemList;
import geso.traphaco.erp.beans.hosokiemnghiem.imp.HoSoKiemNghiem;
import geso.traphaco.erp.beans.hosokiemnghiemchitiet.IHoSoKiemNghiemChiTiet;
import geso.traphaco.erp.beans.hosokiemnghiemchitiet.imp.HoSoKiemNghiemChiTiet;
import geso.traphaco.erp.beans.hosokiemnghiemthietbi.IHoSoKiemNghiemThietBi;
import geso.traphaco.erp.beans.hosokiemnghiemthietbi.imp.HoSoKiemNghiemThietBi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ErpHoSoKiemNghiemUpdateSvl
 */
@WebServlet("/ErpHoSoKiemNghiemUpdateSvl")
public class ErpHoSoKiemNghiemUpdateSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ErpHoSoKiemNghiemUpdateSvl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		Utility util = new Utility();

		String querystring = request.getQueryString();
		String userid = util.getUserId(querystring);
		System.out.println("User id is " + userid);

		String id = util.getId(querystring);
		System.out.println("ID is " + id);
		
		IHoSoKiemNghiem tcknBean =new HoSoKiemNghiem();
		
		tcknBean.setPk_seq(id);

		if (userid.length() == 0)
			userid = util.getUserId(querystring);
		String ctyId = (String)session.getAttribute("congtyId");
		tcknBean.setCongtyId(ctyId);
		
		String nppId = util.getIdNhapp(userid);
	    if(nppId == null)
	    	nppId = "";
	    tcknBean.setNppId(nppId);
	    
	    String action=Utility.getParameter(querystring, "update");
	    if(action==null)action="";
		if(querystring.contains("update")){
			tcknBean.creates();
		    tcknBean.init();
			session.setAttribute("tcknBean", tcknBean);
			response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpHoSoKiemNghiemUpdate.jsp");
		}
		else if(querystring.contains("tao moi")){
			tcknBean.creates();
		    tcknBean.init();
			session.setAttribute("tcknBean", tcknBean);
			response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpHoSoKiemNghiemNew.jsp");
		}
		else if(querystring.contains("display")){
			tcknBean.creates();
		    tcknBean.init();
			session.setAttribute("tcknBean", tcknBean);
			response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpHoSoKiemNghiemDisplay.jsp");
		}
		
		else{
			IErpHoSoKiemNghiemList obj= new ErpHoSoKiemNghiemList();
		    obj.init();
		    obj.creates();
			session.setAttribute("obj", obj);
			response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpHoSoKiemNghiem.jsp");
		}
	   
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    HttpSession session=request.getSession();
		Utility utility=new Utility();
		
		IHoSoKiemNghiem obj=new HoSoKiemNghiem();
		String ngaychungtu=utility.antiSQLInspection(request.getParameter("ngaychungtu"));
		obj.setNgayChungTu(ngaychungtu);
		
		String maphongban=utility.antiSQLInspection(request.getParameter("phongbanth"));
		obj.setMaPhongBan(maphongban);
		
		String masokn=utility.antiSQLInspection(request.getParameter("makiemnghiem"));
		obj.setMaSoKN(masokn);
		
		String sophieukn=utility.antiSQLInspection(request.getParameter("sophieukiemnghiem"));
		obj.setSoPhieuKN(sophieukn);
		
		String thoigiangiao=utility.antiSQLInspection(request.getParameter("thoigiangiaomau"));
		obj.setThoiGianGiaoMau(thoigiangiao);
		
		String nguoiguimau=utility.antiSQLInspection(request.getParameter("nguoiguimau"));
		obj.setNguoiGuiMau(nguoiguimau);
		
		String yclm=utility.antiSQLInspection(request.getParameter("yeucaulaymau"));
		obj.setMaPhieuYeuCauLayMau(yclm);
		
		String masp=utility.antiSQLInspection(request.getParameter("sanphamkn"));
		obj.setMaSanPham(masp);
		
		String tckn=utility.antiSQLInspection(request.getParameter("tieuchuankiemnghiem"));
		obj.setMaTieuChuanKiemNghiem(tckn);
		
		String ngaygiaohang=utility.antiSQLInspection(request.getParameter("ngaygiaohang"));
		obj.setNgaygiaohang(ngaygiaohang);
		
		String loaikiemtra=utility.antiSQLInspection(request.getParameter("loaikiemtra"));
		obj.setLoaiKiemTra(loaikiemtra);
		
		String mamaukn=utility.antiSQLInspection(request.getParameter("loaimaukiemnghiem"));
		obj.setMaLoaiMauKN(mamaukn);
		
		String thoidiemlaymau=utility.antiSQLInspection(request.getParameter("thoidiemlaymau"));
		obj.setThoiDiemLayMau(thoidiemlaymau);
		
		String diengiaichungtu=utility.antiSQLInspection(request.getParameter("diengiaichungtu"));
		obj.setDienGiai(diengiaichungtu);
		
		String trangthai=utility.antiSQLInspection(request.getParameter("trangthai"));
		if(trangthai==null){
			obj.setTrangThai("0");
		}
		else obj.setTrangThai("1");
		
		//obj.creates();
		
		List<IHoSoKiemNghiemChiTiet> listCt=new ArrayList<IHoSoKiemNghiemChiTiet>();
		String[] mayeucaukythuatct=request.getParameterValues("mayeucaukythuatct");
		String[] tsyc=request.getParameterValues("tsyc");
		String[] tsycd=request.getParameterValues("tsycd");
		String[] sottctkn=request.getParameterValues("sottctkn");
		if(mayeucaukythuatct!=null)
		for (int i = 0; i < mayeucaukythuatct.length; i++) {
			IHoSoKiemNghiemChiTiet ct=new HoSoKiemNghiemChiTiet();
			ct.setSoTT(sottctkn[i]);
			ct.setThongSoYeuCau(tsyc[i]);
			ct.setThongSoYeuCauDen(tsycd[i]);
			String knv=(String)request.getParameter("kiemnghiemvien_"+i);
			ct.setMaKiemNghiemVien(knv);
			System.out.println("mayeucaukithuatct: " + mayeucaukythuatct[i]);
			ct.setMaYCKT(mayeucaukythuatct[i]);
			listCt.add(ct);
		}
		obj.setListCT(listCt);
		
		List<IHoSoKiemNghiemThietBi> listTB=new ArrayList<IHoSoKiemNghiemThietBi>();
		String[] mathietbi=request.getParameterValues("mathietbi");
		String[] tentb=request.getParameterValues("tenthietbi");
		String[] ghichu=request.getParameterValues("ghichu");
		String[] sotttb=request.getParameterValues("sotttb");
		String[] ndg=request.getParameterValues("ngaydanhgia");
		String[] nkn=request.getParameterValues("ngaykiemdinh");
		if(mathietbi!=null)
		for (int u = 0; u < mathietbi.length; u++) {
			IHoSoKiemNghiemThietBi tb=new HoSoKiemNghiemThietBi();
			tb.setSoTT(sotttb[u]);
			tb.setMaThietBi(mathietbi[u]);
			
			tb.setGhiChu(ghichu[u]);
			tb.setNgayDanhGia(ndg[u]);
			tb.setNgayDanhGiaKeTiep(nkn[u]);
			listTB.add(tb);
		}
		obj.setListTB(listTB);
		
		String action=request.getParameter("action");
		
		String userId=request.getParameter("userId");
		obj.setUserId(userId);
		
		String userTen=request.getParameter("userTen");
		String pk_seq=request.getParameter("pk_seq");
		if (pk_seq==null)pk_seq="";
		obj.setPk_seq(pk_seq);
		
		if(action==null)action="";
		if(action.equals("taomoi")){
			if (!obj.save())
			{
				obj.creates();
				obj.init();
				session.setAttribute("userId", userId);
				session.setAttribute("obj", obj);
				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoSoKiemNghiemNew.jsp";
				response.sendRedirect(nextJSP);
			} 
			else
			{
				obj.DBClose();
				IErpHoSoKiemNghiemList cnList = new ErpHoSoKiemNghiemList();
				cnList.setUserId(userId);
			    cnList.init();
				session.setAttribute("obj", cnList);
				String nextJSP="/TraphacoHYERP/pages/Erp/ErpHoSoKiemNghiem.jsp";
				response.sendRedirect(nextJSP);
				
			}
		}
		else if (action.equals("update")) {
			if (!obj.update())
			{
				obj.creates();
				obj.init();
				session.setAttribute("userId", userId);
				session.setAttribute("obj", obj);
				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoSoKiemNghiemUpdate.jsp";
				response.sendRedirect(nextJSP);
			} 
			else
			{
				obj.DBClose();
				IErpHoSoKiemNghiemList cnList = new ErpHoSoKiemNghiemList();
				cnList.setUserId(userId);
			    cnList.init();
				session.setAttribute("obj", cnList);
				String nextJSP="/TraphacoHYERP/pages/Erp/ErpHoSoKiemNghiem.jsp";
				response.sendRedirect(nextJSP);
			}
			
		}
		
		else if (action.equals("timtaomoi")) {
			/*String maYCLM=request.getParameter("yeucaulaymau");
			String maTCKN=request.getParameter("tieuchuankiemnghiem");
			IHoSoKiemNghiem obj1=new HoSoKiemNghiem();
		
			obj1.setUserId(userId);
			obj1.setMaPhieuYeuCauLayMau(maYCLM);
			obj1.setMaTieuChuanKiemNghiem(maTCKN);*/
			//obj.creates();
			//obj.init();
			obj.creates();
			session.setAttribute("obj", obj);
			String nextJSP="/TraphacoHYERP/pages/Erp/ErpHoSoKiemNghiemNew.jsp"; 
			response.sendRedirect(nextJSP);
		}
		else if (action.equals("submitloaimau")) {
			obj.createsloaimau();
			session.setAttribute("obj", obj);
			String nextJSP="/TraphacoHYERP/pages/Erp/ErpHoSoKiemNghiemNew.jsp"; 
			response.sendRedirect(nextJSP);
		}
		else{
			
			
		}
		/*request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();

		Utility util = new Utility();

		IErpHoSoKiemNghiem tcknBean = new ErpHoSoKiemNghiem();
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
				if (!tcknBean.createHSKN())
				{
					tcknBean.createRs();
					session.setAttribute("userId", userId);
					session.setAttribute("tcknBean", tcknBean);
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoSoKiemNghiemNew.jsp";
					response.sendRedirect(nextJSP);
				} 
				else
				{
					tcknBean.DBclose();
					IErpHoSoKiemNghiemList cnList = new ErpHoSoKiemNghiemList();
					cnList.setUserId(userId);
				    cnList.init();
					session.setAttribute("obj", cnList);
					nextJSP="/TraphacoHYERP/pages/Erp/ErpHoSoKiemNghiem.jsp";
					response.sendRedirect(nextJSP);
				}
			}
			else
			{
				if (!tcknBean.UpdateHSKN())
				{
					tcknBean.createRs();
					session.setAttribute("userId", userId);
					session.setAttribute("tcknBean", tcknBean);
					nextJSP="/TraphacoHYERP/pages/Erp/ErpHoSoKiemNghiemUpdate.jsp";
					response.sendRedirect(nextJSP);
				} 
				else
				{
					tcknBean.DBclose();
					IErpHoSoKiemNghiemList cnList = new ErpHoSoKiemNghiemList();
					cnList.setUserId(userId);
				    cnList.init();
					session.setAttribute("obj", cnList);
					nextJSP="/TraphacoHYERP/pages/Erp/ErpHoSoKiemNghiem.jsp";
					response.sendRedirect(nextJSP);
				}
			}
		} else {
			
			tcknBean.createRs();
			session.setAttribute("userId", userId);
			session.setAttribute("tcknBean", tcknBean);
			if(id == null || id.trim().length()==0)
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoSoKiemNghiemNew.jsp";
			else
				nextJSP="/TraphacoHYERP/pages/Erp/ErpHoSoKiemNghiemUpdate.jsp";
			
			response.sendRedirect(nextJSP);

		}*/
	}
}
