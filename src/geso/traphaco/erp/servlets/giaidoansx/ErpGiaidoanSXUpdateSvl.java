package geso.traphaco.erp.servlets.giaidoansx;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.giaidoansx.*;
import geso.traphaco.erp.beans.giaidoansx.imp.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpGiaidoanSXUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out; 
       
    public ErpGiaidoanSXUpdateSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		IErpGiaidoanSX csxBean = new ErpGiaidoanSX();
		this.out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    	    
	    String id = util.getId(querystring);
	    if(id == null )
	    	id = "";
	    
	    String ctyId = (String)session.getAttribute("congtyId");
	    csxBean.setCongtyId(ctyId);
	    csxBean.setId(id);
	    csxBean.setUserId(userId);
	    
	    csxBean.init();
        session.setAttribute("csxBean", csxBean);
        
        String nextJSP = "/TraphacoHYERP/pages/Erp/ErpGiaiDoanSXNew.jsp";
        if(querystring.indexOf("display") >= 0) {
        	nextJSP = "/TraphacoHYERP/pages/Erp/ErpGiaiDoanSXDisplay.jsp";
        }
        
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IErpGiaidoanSX obj = new ErpGiaidoanSX();

		Utility util = new Utility();
	    String id = util.antiSQLInspection(request.getParameter("id"));	
	    if(id == null)
	    	id = "";
	    obj.setId(id);
	    
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		obj.setUserId(userId);
		
		String ctyId = (String)session.getAttribute("congtyId");
		obj.setCongtyId(ctyId);
		
		String tbsx = request.getParameter("tbsx");
		
		String ma = util.antiSQLInspection(request.getParameter("ma"));
		if (ma == null)
			ma = "";
		obj.setMa(ma);
		
		String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
		if (diengiai == null)
			diengiai = "";
		obj.setDiengiai(diengiai);
		
		String solannhap = util.antiSQLInspection(request.getParameter("solannhap"));
		if (solannhap == null)
			solannhap = "";
		obj.setSolannhap(solannhap);
		
		String giaodiennhap = util.antiSQLInspection(request.getParameter("giaodiennhap"));
		obj.setGiaodiennhap(giaodiennhap);
		
		String[] loaisanphamList = request.getParameterValues("loaisanpham");
		String loaisanpham = "";
		if(loaisanphamList != null){
			for(int i = 0; i < loaisanphamList.length; i++){
				loaisanpham += loaisanphamList[i] + ",";
			}
		}
		if(loaisanpham.length() > 0){
			loaisanpham = loaisanpham.substring(0, loaisanpham.length() - 1);
			obj.setLoaisanpham(loaisanpham);
		}
		
		String isallbom = util.antiSQLInspection(request.getParameter("isallbom"));
		if (isallbom == null || isallbom.length() == 0)
			isallbom = "0";
		obj.setIsAllBom(isallbom);
		
		String soluongmau = util.antiSQLInspection(request.getParameter("soluongmau"));
		if (soluongmau == null)
			soluongmau = "";
		obj.setSoluongmau(soluongmau);
		
		String loaimauin = util.antiSQLInspection(request.getParameter("loaimauin"));
		obj.setLoaimauinId(loaimauin);
		
		String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
		if (trangthai == null || trangthai.length() == 0)
			trangthai = "0";
		obj.setTrangthai(trangthai);
		
		String[] tscdList = request.getParameterValues("tscdId");
		String[] thietbiSxList = request.getParameterValues("thietbisxid");
		String[] thongsochungList = request.getParameterValues("thongsochung");
		
		List<IErpGiaiDoanSXThongSo> thongsoList = new ArrayList<IErpGiaiDoanSXThongSo>();
		IErpGiaiDoanSXThongSo thongso;
		
		String[] tbsxDiengiaiList;
		String[] tbsxYeucauList;
		String[] tbsxTstuList;
		String[] tbsxTsdenList;
		String[] tbsxDvtList;
		
		List<ErpGiaiDoanSX_TS_ChiTiet> tbsxThongsoList;
		ErpGiaiDoanSX_TS_ChiTiet tbsxThongso;
		
		String tick;
		
		for(int i=0; i<tscdList.length; i++){
			thongso = new ErpGiaiDoanSXThongSo();
			
			thongso.setTscdId(tscdList[i]);
			thongso.setThietBiSXId(thietbiSxList[i]);
			thongso.setThongSoChung(thongsochungList[i]);
			
			if(tscdList[i].length() > 0){
				if(tbsx.equals("tbsx"+i)){
					if(thietbiSxList[i].length() > 0){
						thongso.createThietbisxThongso();
					}
				}else if(tbsx.equals("tscd"+i) && thietbiSxList[i].length() > 0){
					thongso.setThietBiSXId("");
				} else {
					tbsxDiengiaiList = request.getParameterValues("tbsxthongso_"+i);
					if(tbsxDiengiaiList != null){
						tbsxYeucauList = request.getParameterValues("tbsxyeucau_"+i);
						tbsxTstuList = request.getParameterValues("tbsxthongsotu_"+i);
						tbsxTsdenList = request.getParameterValues("tbsxthongsoden_"+i);
						tbsxDvtList = request.getParameterValues("tbsxdvt_"+i);
					
						tbsxThongsoList = new ArrayList<ErpGiaiDoanSX_TS_ChiTiet>();
						for(int k=0; k<tbsxDiengiaiList.length; k++){
							tbsxThongso = new ErpGiaiDoanSX_TS_ChiTiet();
							
							tbsxThongso.setDienGiai(tbsxDiengiaiList[k]);
							tbsxThongso.setYeucau(tbsxYeucauList[k]);
							tbsxThongso.setThongsoTu(tbsxTstuList[k]);
							tbsxThongso.setThongsoDen(tbsxTsdenList[k]);
							tbsxThongso.setDonVi(tbsxDvtList[k]);
							
							tick = util.antiSQLInspection(request.getParameter("tbsxtick_"+i+"_"+k));
							if (tick == null)
								tick = "0";
							tbsxThongso.setTick(tick);
							
							tbsxThongsoList.add(tbsxThongso);
						}
						thongso.setTbsxThongsoList(tbsxThongsoList);
					}
				}
			}
			thongso.init();
			
			thongsoList.add(thongso);
		}
		obj.setThongSoList(thongsoList);
		
 		String action = request.getParameter("action");
 		if(action.equals("save")) {
 			if(id == null || id.equals("")) {
	 			if (!obj.create()) {
	 				obj.createRs();
	 				
	 				session.setAttribute("csxBean", obj);  	
	 	    		session.setAttribute("userId", userId);
					String nextJSP = "/TraphacoHYERP/pages/Erp/ErpGiaiDoanSXNew.jsp";
					response.sendRedirect(nextJSP);
				} else {
					IErpGiaidoanSXList obj2 = new ErpGiaidoanSXList();
					obj2.setCongtyId(ctyId);
					obj2.setUserId(userId);
					obj2.init("");
					obj.DbClose();
					
					session.setAttribute("obj", obj2);
				    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpGiaiDoanSX.jsp";
					response.sendRedirect(nextJSP);
				}
 			} else {
 				if (!(obj.update())) {
 					obj.createRs();
 					
	 				session.setAttribute("csxBean", obj);  	
	 	    		session.setAttribute("userId", userId);
					String nextJSP = "/TraphacoHYERP/pages/Erp/ErpGiaiDoanSXNew.jsp";
					response.sendRedirect(nextJSP);
				} else {
					IErpGiaidoanSXList obj2 = new ErpGiaidoanSXList();
					obj2.setCongtyId(ctyId);
					obj2.setUserId(userId);
					obj2.init("");
					obj.DbClose();
					
					session.setAttribute("obj", obj2);
				    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpGiaiDoanSX.jsp";
					response.sendRedirect(nextJSP);
				}
 			}
	    } else {
	    	obj.createRs();
	    	
			session.setAttribute("userId", userId);
			session.setAttribute("csxBean", obj);
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpGiaiDoanSXNew.jsp";
			response.sendRedirect(nextJSP);
		}		
	}
}
