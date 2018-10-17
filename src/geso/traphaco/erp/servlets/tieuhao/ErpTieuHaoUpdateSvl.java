package geso.traphaco.erp.servlets.tieuhao;
  
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.GiuDieuKienLoc;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.tieuhao.*;
import geso.traphaco.erp.beans.tieuhao.imp.*;
 

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
 
import java.util.ArrayList;
 
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpTieuHaoUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	PrintWriter out;
	
	public ErpTieuHaoUpdateSvl()
	{
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		 
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
	    String ctyId = (String)session.getAttribute("congtyId");
		String sum = (String) session.getAttribute("sum");
		 Utility cutil = (Utility) session.getAttribute("util");
		if (!cutil.check(userId, userTen, sum))
		{
			response.sendRedirect("/SalesUp/index.jsp");
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
			IErpTieuHao tieuhaoBean = new ErpTieuHao(id);
			tieuhaoBean.setCongtyId(ctyId);
			tieuhaoBean.setUserId(userId); // phai co UserId truoc khi Init
			tieuhaoBean.init();
			session.setAttribute("nccId", tieuhaoBean.getnccid());				
			String nextJSP;
			
			if (request.getQueryString().indexOf("update") >= 0)
			{
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpTieuHaoUpdate.jsp";
			}
			else
			{
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpTieuHaoDisplay.jsp";
			}
			
			session.setAttribute("tieuhaoBean", tieuhaoBean);			
			response.sendRedirect(nextJSP);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		System.out.println("\n[ErpTieuHaoUpdateSvl.doPost] begin...");
		
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
	    String ctyId = (String)session.getAttribute("congtyId");
		
		String sum = (String) session.getAttribute("sum");
		 Utility cutil = (Utility) session.getAttribute("util");
		if (!cutil.check(userId, userTen, sum))
		{
			response.sendRedirect("/SalesUp/index.jsp");
		}
		else
		{
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			
			session.setMaxInactiveInterval(30000);
			
			this.out = response.getWriter();
			IErpTieuHao tieuhaoBean;
			
			Utility util = new Utility();
			String id = util.antiSQLInspection(request.getParameter("id"));
			
			if (id != null)
			{
				tieuhaoBean = new ErpTieuHao(id);
			
				tieuhaoBean.setCongtyId((String)session.getAttribute("congtyId"));
				tieuhaoBean.setUserId(userId);

				
				String manhanhang = util.antiSQLInspection(request.getParameter("manhanhang"));
				if (manhanhang == null) manhanhang = ""; else manhanhang = manhanhang.trim();
				tieuhaoBean.setManhanhang(manhanhang);
				
				String ngaytieuhao = util.antiSQLInspection(request.getParameter("ngaytieuhao"));
				if (ngaytieuhao == null) ngaytieuhao = ""; else ngaytieuhao = ngaytieuhao.trim();
				tieuhaoBean.setNgaytieuhao(ngaytieuhao);
				
				String ngaychot = util.antiSQLInspection(request.getParameter("ngaychot"));
				if (ngaychot == null) ngaychot = ""; else ngaychot = ngaychot.trim();
				tieuhaoBean.setNgaychot(ngaychot);
				
				String sanphamId = util.antiSQLInspection(request.getParameter("sanphamId"));
				if (sanphamId == null) sanphamId = ""; else sanphamId = sanphamId.trim();
				tieuhaoBean.setSanphamId(sanphamId);
				
				String sanphamMa = util.antiSQLInspection(request.getParameter("sanphamMa"));
				if (sanphamMa == null) sanphamMa = ""; else sanphamMa = sanphamMa.trim();
				tieuhaoBean.setSanphamMa(sanphamMa);
				
				String sanphamTen = util.antiSQLInspection(request.getParameter("sanphamTen"));
				if (sanphamTen == null) sanphamTen = ""; else sanphamTen = sanphamTen.trim();
				tieuhaoBean.setSanphamTen(sanphamTen);
				
				String soluong = util.antiSQLInspection(request.getParameter("soluong"));
				if (soluong == null) soluong = ""; else soluong = soluong.trim();
				tieuhaoBean.setSoLuong(soluong);
				
				String ghichu = util.antiSQLInspection(request.getParameter("ghichu"));
				if (ghichu == null) ghichu = ""; 
				tieuhaoBean.setGhichu(ghichu);
				
				String action = request.getParameter("action");
				// Luu lai san pham
				String[] vattuIds = request.getParameterValues("vattuId");
				String[] vattuMas = request.getParameterValues("vattuMa");
				String[] vattuTens = request.getParameterValues("vattuTen");
				String[] vattuSoLuongChuans = request.getParameterValues("vattuSoLuongChuan");
				String[] soluongdatieuhao = request.getParameterValues("soluongdatieuhao");
				
				
				String[] vattuSoLuongThucTes = request.getParameterValues("vattuSoLuongThucTe");
				
				String sql = " select a.NGAYCHOT, c.LOAINHACUNGCAP_FK, khoNL_Nho_GC, b.nhacungcap_fk , d.khachhang_fk " +
							 "	 from ERP_NHANHANG a left join ERP_MUAHANG b on a.MUAHANG_FK = b.PK_SEQ " +
							 "	left join ERP_NHACUNGCAP c on b.NHACUNGCAP_FK = c.PK_SEQ" +
							 "  left join DonTraHang d on a.trahang_fk = d.pk_seq   " +
							 " where a.pk_seq = (select nhanhang_fk from erp_tieuhao where pk_seq="+id+" )";
				///System.out.println(sql);
				
				dbutils db=new dbutils();
				String ngaychotNV = "";
				String loaiNCC = "";
				String khoNL_Nho_GC = "100055";
				String nccId = "";
				String khId = "";
				
				
				ResultSet rskho = db.get(sql);
				try{
				 	if(rskho.next())
					{
						ngaychotNV = rskho.getString("ngaychot");
						loaiNCC = rskho.getString("LOAINHACUNGCAP_FK") == null ? "" : rskho.getString("LOAINHACUNGCAP_FK");
					 
						nccId = rskho.getString("nhacungcap_fk") == null ? "" : rskho.getString("nhacungcap_fk");
						khId = rskho.getString("khachhang_fk") == null ? "" : rskho.getString("khachhang_fk");
						rskho.close();
					}
				}catch(Exception er){
					er.printStackTrace();
				}
				session.setAttribute("nccId", nccId);			
				List<ISanpham> spList = new ArrayList<ISanpham>();
				
				if (vattuIds != null)
				{
					ISanpham sanpham = null;
					int m = 0;
					while (m < vattuIds.length)
					{ 
						if(vattuMas[m].trim().length() > 0) {
							
									sanpham = new Sanpham();
									sanpham.setTieuHaoId(id);
									if(vattuIds[m]!=null) {
										sanpham.setId(vattuIds[m]);
									}
									if(vattuMas[m]!=null) {
										sanpham.setMa(vattuMas[m]);
									}
									if(vattuTens[m]!=null) {
										sanpham.setTen(vattuTens[m]);
									}
									
									double soluongchuan=0;
									try{
										soluongchuan= Double.valueOf(vattuSoLuongChuans[m]);
									}catch(Exception err){
										
									}
									double soluongdatieuhao1=0;
									try{
										soluongdatieuhao1= Double.valueOf(soluongdatieuhao[m]);
									}catch(Exception err){
										
									}
									sanpham.setSoLuongDaTieuHao(soluongdatieuhao1);
									sanpham.setSoLuongChuan(soluongchuan);
								 
									double slthucte=0;
									try{
										slthucte= Double.valueOf(vattuSoLuongThucTes[m]);
									}catch(Exception err){
										
									}
									if(vattuSoLuongThucTes[m]!=null) {
										sanpham.setSoLuongThucTe(slthucte);
									}
									
									double soluongthuctenew=0;
									
									List<ISanphamLo> listlo=new ArrayList<ISanphamLo>();
									
									String[] solo = request.getParameterValues(vattuMas[m]+".solo");
									String[] ngayhethan = request.getParameterValues(vattuMas[m]+".ngayhethan");
									 
									String[] soluonglo = request.getParameterValues(vattuMas[m]+".soluong");
									String[] vitri = request.getParameterValues(vattuMas[m]+".vitri");
									String[] vitriid = request.getParameterValues(vattuMas[m]+".vitriid");
									String[] mathung = request.getParameterValues(vattuMas[m]+".mathung");
									String[] mame = request.getParameterValues(vattuMas[m]+".mame");
									String[] maphieu = request.getParameterValues(vattuMas[m]+".maphieu");
									String[] phieueo = request.getParameterValues(vattuMas[m]+".phieueo");
									String[] maphieudinhtinh = request.getParameterValues(vattuMas[m]+".maphieudinhtinh");
									
									String[] MARQ = request.getParameterValues(vattuMas[m]+".MARQ");
									String[] MARQid = request.getParameterValues(vattuMas[m]+".MARQid");
									
									String[] hamam = request.getParameterValues(vattuMas[m]+".hamam");
									String[] hamluong = request.getParameterValues(vattuMas[m]+".hamluong");
									String[] ngaynhapkho = request.getParameterValues(vattuMas[m]+".ngaynhapkho");
									 
									
									
									if(! action.equals("reload")){
										if(solo!=null){
											for(int k=0;k<solo.length;k++ ) {
												
												ISanphamLo splo=new SanphamLo();
												 splo.setsolo(solo[k]);
												 splo.setVitri(vitri[k]);
												 splo.setVitriId(vitriid[k]);
												 splo.setMathung(mathung[k]);
												 splo.setMame(mame[k]);
												 splo.setMaphieu(maphieu[k]);
												 splo.setPHIEUEO(phieueo[k]);
												 splo.setMAPHIEUDINHTINH(maphieudinhtinh[k]);
												 splo.setMARQ(MARQ[k]);
												 splo.setIDMARQUETTE(MARQid[k]);
												 splo.setNgayhethan(ngayhethan[k]);
												 splo.setngaynhapkho(ngaynhapkho[k]);
												 splo.setHamam(hamam[k]);
												 splo.setHamluong(hamluong[k]);
												 
												 double sllo=0;
												 try{
													 sllo=Double.parseDouble(soluonglo[k].replaceAll(",", ""));
												 }catch(Exception er){
												 }
												 if(sllo >0) {
													 soluongthuctenew=soluongthuctenew+sllo;
													 splo.setSoLuong(sllo);
													 listlo.add(splo);
												 }
												 
											}
										}
									
									} 
									sanpham.setSpList(listlo);
									
									spList.add(sanpham);
						}
						m++;
					}
				}
				 
				tieuhaoBean.setSpList(spList);
				
			
				
				if (action.equals("save"))
				{
					if (id != null) // update
					{
						if (!tieuhaoBean.update())
						{
							tieuhaoBean.init();
							session.setAttribute("tieuhaoBean", tieuhaoBean);
							String nextJSP = "/TraphacoHYERP/pages/Erp/ErpTieuHaoUpdate.jsp";
							response.sendRedirect(nextJSP);
							return;
						}
					}
				}else if (action.equals("reload")){
					tieuhaoBean.createRs();
					tieuhaoBean.init();
					
					session.setAttribute("tieuhaoBean", tieuhaoBean);
					String nextJSP = "/TraphacoHYERP/pages/Erp/ErpTieuHaoUpdate.jsp";
					response.sendRedirect(nextJSP);
					return;
				}

				IErpTieuHaoList obj = new ErpTieuHaoList();
				obj.setCongtyId((String)session.getAttribute("congtyId"));
				obj.setUserId(userId);
				String searchQuery=util.getSearchFromHM(userId,this.getServletName(), session);
				System.out.println("Search Condition :"+searchQuery);
				GiuDieuKienLoc.setParamsToOject(obj, searchQuery);
				
				obj.init("");
				
				session.setAttribute("obj", obj);
				
				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpTieuHao.jsp";
				response.sendRedirect(nextJSP);
			}
		}
	}
}
