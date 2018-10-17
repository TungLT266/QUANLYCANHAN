package geso.traphaco.erp.servlets.buttoantonghop;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.GiuDieuKienLoc;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.buttoantonghop.imp.ErpButToanTongHop;
import geso.traphaco.erp.beans.buttoantonghop.imp.ErpButToanTongHopList;
import geso.traphaco.erp.beans.buttoantonghop.IErpButToanTongHop;
import geso.traphaco.erp.beans.buttoantonghop.IErpButToanTongHopList;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpButToanTongHopUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public ErpButToanTongHopUpdateSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();

		Utility util = new Utility();
		out = response.getWriter();

		String querystring = request.getQueryString();
		String action = util.getAction(querystring);
		out.println(action);

		String hdId = util.getId(querystring);

		String userId = util.getUserId(querystring);
		
		String nppId = util.getIdNhapp(userId);
	    if(nppId == null)
	    	nppId = "";

		String npp_duocchon_id = session.getAttribute("npp_duocchon_id") == null ? "" : session.getAttribute("npp_duocchon_id").toString();
		    
		if (action.equals("update"))
		{
			IErpButToanTongHop btthBean = new ErpButToanTongHop(hdId);
			btthBean.setCongtyId((String)session.getAttribute("congtyId"));
			btthBean.setUserId(userId);
			btthBean.setnppId(util.getIdNhapp(userId));
			btthBean.setNpp_duocchon_id(npp_duocchon_id);
			btthBean.Init();
			session.setAttribute("btthBean", btthBean);
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpButToanTongHopUpdate.jsp";
			response.sendRedirect(nextJSP);
		} else if (action.equals("copy")) {
			IErpButToanTongHop btthBean = new ErpButToanTongHop(hdId);
			btthBean.setCongtyId((String)session.getAttribute("congtyId"));
			btthBean.setUserId(userId);
			btthBean.setnppId(util.getIdNhapp(userId));
			btthBean.setNpp_duocchon_id(npp_duocchon_id);
			btthBean.Init();
			btthBean.setId(null);
			btthBean.setSoChungTu("");
			session.setAttribute("btthBean", btthBean);
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpButToanTongHopNew.jsp";
			response.sendRedirect(nextJSP);
		}
		else if (action.equals("display"))
		{
			IErpButToanTongHop btthBean = new ErpButToanTongHop(hdId);
			btthBean.setCongtyId((String)session.getAttribute("congtyId"));
			btthBean.setUserId(userId);
			btthBean.setnppId(util.getIdNhapp(userId));
			btthBean.setNpp_duocchon_id(npp_duocchon_id);
			btthBean.Init();
			session.setAttribute("btthBean", btthBean);
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpButToanTongHopDisplay.jsp";
			response.sendRedirect(nextJSP);
		}

	}
	//lấy số hiệu tài khoản
	public String getSoHieu( String pk, dbutils db){
		String soHieu = "";
		String query = "select PK_SEQ, SOHIEUTAIKHOAN from erp_taikhoankt";
		ResultSet rs = db.get(query);
		if(rs != null){
			try {
				while(rs.next()){
					if(rs.getString("PK_SEQ").equals(pk)){
						soHieu = rs.getString("SOHIEUTAIKHOAN");
					}
				}
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return soHieu;
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		HttpSession session = request.getSession();
		String ServerletName = this.getServletName();
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
			
			String npp_duocchon_id = session.getAttribute("npp_duocchon_id") == null ? "" : session.getAttribute("npp_duocchon_id").toString();
			
			IErpButToanTongHop btthBean = new ErpButToanTongHop();
			btthBean.setCongtyId((String)session.getAttribute("congtyId"));
			
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");

			Utility util = new Utility();
			session.setAttribute("util", util);

			String id = util.antiSQLInspection(request.getParameter("id"));
			btthBean.setUserId(userId);
			session.setAttribute("userId", userId);
			session.setAttribute("userTen", userTen);

			String action = request.getParameter("action");
			btthBean.setId(id);			
			String nppId = util.getIdNhapp(userId);
		    if(nppId == null)
		    	nppId = "";
		    
			String nextJSP = "";
			String NgayButToan=util.antiSQLInspection(request.getParameter("NgayButToan"));
			btthBean.setNgayButToan(NgayButToan);
			String DienGiai=util.antiSQLInspection(request.getParameter("DienGiai"));
			btthBean.setDienGiai(DienGiai);
			
			String sochungtu=util.antiSQLInspection(request.getParameter("SoChungTu"));
			btthBean.setSoChungTu(sochungtu.toUpperCase());
			
			String[] TaiKhoanNoIds = request.getParameterValues("TaiKhoanNoIds");
			btthBean.setTaiKhoanNoIds(TaiKhoanNoIds);

			int c = 0;
			
			if(TaiKhoanNoIds != null){
				for (int i=0; i < TaiKhoanNoIds.length; i++) {
					if (TaiKhoanNoIds[i].trim().length() > 0 && !TaiKhoanNoIds[i].trim().equals("-1")) {
						c++;
					}
				}
			}

			String[] TaiKhoanCoIds = request.getParameterValues("TaiKhoanCoIds");
			btthBean.setTaiKhoanCoIds(TaiKhoanCoIds);
			
			/*if(TaiKhoanCoIds != null){
				c = Math.max(c, TaiKhoanCoIds.length);
			}*/
			System.out.println("coutn is: " + c);
			btthBean.setCount(c);
			
			String[] Sotien = request.getParameterValues("Sotien");
			if(Sotien != null){
				for(int i = 0; i < Sotien.length; i++){
					if(Sotien[i].length() == 0) Sotien[i] = "0";
				}
			}
			String[] SotienNT = request.getParameterValues("SotienNT");
			if(SotienNT != null){
				for(int i = 0; i < SotienNT.length; i++){
					if(SotienNT[i].length() == 0) SotienNT[i] = "0";
				}
			}
			btthBean.setSotien(Sotien);
			btthBean.setSoTienNT(SotienNT);
			
			String[] dc_coIds = request.getParameterValues("dcCoIds");
			btthBean.setDungcho_Co(dc_coIds);
			
			String[] dc_noIds = request.getParameterValues("dcNoIds");
			btthBean.setDungcho_No(dc_noIds);
			
			String[] dc_coTens = request.getParameterValues("dcCoTens");
			btthBean.setDungcho_CoTen(dc_coTens);
			
			String[] dc_noTens = request.getParameterValues("dcNoTens");
			btthBean.setDungcho_NoTen(dc_noTens);
			
			String[] TtcpCoIds = request.getParameterValues("TtcpCoIds");
			btthBean.setTtcpCoIds(TtcpCoIds);
			
			String[] TtcpNoIds = request.getParameterValues("TtcpNoIds");
			btthBean.setTtcpNoIds(TtcpNoIds);
			
			String[] kbhIds = request.getParameterValues("kbhIds");
			btthBean.setKbhIds(kbhIds);
			
			String[] mavvIds = request.getParameterValues("mavvIds");
			btthBean.setMavvIds(mavvIds);
			
			String[] diabanIds = request.getParameterValues("diabanIds");
			btthBean.setDiabanIds(diabanIds);
			
			String[] tinhthanhIds = request.getParameterValues("tinhthanhIds");
			btthBean.setTinhthanhIds(tinhthanhIds);
			
			String[] benhvienIds = request.getParameterValues("benhvienIds");
			btthBean.setBenhvienIds(benhvienIds);
			
			String[] sanphamIds = request.getParameterValues("sanphamIds");
			btthBean.setSanphamIds(sanphamIds);
			
			String[] dg = request.getParameterValues("dg");
			btthBean.setDg(dg);
				
			btthBean.setnppId(util.getIdNhapp(userId));
			btthBean.setNpp_duocchon_id(npp_duocchon_id);
			
			String[] pk_seq = request.getParameterValues("pk_seq");
			btthBean.setPKSEQIds(pk_seq);
			
			btthBean.createRs();
			
			Hashtable<String, String> btth_mauhd = new Hashtable<String, String>();
			Hashtable<String, String> btth_kyhieuhd = new Hashtable<String, String>();
			Hashtable<String, String> btth_msthd = new Hashtable<String, String>();
			Hashtable<String, String> btth_ngayhd = new Hashtable<String, String>();
			Hashtable<String, String> btth_sohd = new Hashtable<String, String>();
			Hashtable<String, String> btth_tenNCChd = new Hashtable<String, String>();
			Hashtable<String, String> btth_diachi = new Hashtable<String, String>();
			Hashtable<String, String> btth_tienhanghd = new Hashtable<String, String>();
			Hashtable<String, String> btth_thuesuathd = new Hashtable<String, String>();
			Hashtable<String, String> btth_tienthuehd = new Hashtable<String, String>();
			Hashtable<String, String> btth_ghichuhd = new Hashtable<String, String>();
			Hashtable<String, String> btth_conghd = new Hashtable<String, String>();
			
			if(pk_seq != null)
			{
				for(int i = 0; i < c; i++){
					
					for(int n = 0; n < 30 ; n++ )
					{
						String temID = pk_seq[i] + "__" + n;						
						String[] _hdMAU = request.getParameterValues(temID + "_hdMAU");
			    		String[] _hdKYHIEU = request.getParameterValues(temID + "_hdKYHIEU");
			    		String[] _hdSOHOADON = request.getParameterValues(temID + "_hdSOHOADON");
			    		String[] _hdNGAYHD = request.getParameterValues(temID + "_hdNGAYHD");
			    		String[] _hdTENNCC = request.getParameterValues(temID + "_hdTENNCC");
			    		String[] _hdMST = request.getParameterValues(temID + "_hdMST");
			    		String[] _hdTIENHANG = request.getParameterValues(temID + "_hdTIENHANG");
			    		String[] _hdTIENTHUE = request.getParameterValues(temID + "_hdTIENTHUE");
			    		String[] _hdTHUESUAT = request.getParameterValues(temID + "_hdTHUESUAT");
			    		String[] _hdGHICHU = request.getParameterValues(temID + "_hdGHICHU");
			    		String[] _hdCONG = request.getParameterValues(temID + "_hdCONG");
			    		String[] _diachi = request.getParameterValues(temID + "_hdDIACHI");
			    		
						btth_mauhd.put( pk_seq[i] + "__" + n, _hdMAU[0] );	
						btth_kyhieuhd.put( pk_seq[i] + "__" + n, _hdKYHIEU[0] );
						btth_msthd.put( pk_seq[i] + "__" + n, _hdMST[0] );
						btth_ngayhd.put( pk_seq[i] + "__" + n, _hdNGAYHD[0] );
						btth_sohd.put( pk_seq[i] + "__" + n, _hdSOHOADON[0] );
						btth_tenNCChd.put( pk_seq[i] + "__" + n, _hdTENNCC[0] );
						btth_diachi.put(pk_seq[i] + "__" + n, _diachi[0] == null?"":_diachi[0]);
						btth_tienhanghd.put( pk_seq[i] + "__" + n, _hdTIENHANG[0].replace(",", "") );
						btth_thuesuathd.put( pk_seq[i] + "__" + n , _hdTHUESUAT[0].replace(",", "") );
						btth_tienthuehd.put( pk_seq[i] + "__" + n , _hdTIENTHUE[0].replace(",", "") );
						System.out.println("tienThue:" +btth_tienthuehd.get(pk_seq[i] + "__" + n) );
						btth_ghichuhd.put( pk_seq[i] + "__" + n, _hdGHICHU[0] == null?"":_hdGHICHU[0] );
						btth_conghd.put(pk_seq[i] + "__" + n, _hdCONG[0].replace(",", ""));
					}
					
				}
			}
			
			btthBean.setBtth_Mauhd( btth_mauhd );
			
			btthBean.setBtth_Kyhieuhd(btth_kyhieuhd);
			
			btthBean.setBtth_MSThd( btth_msthd );
			
			btthBean.setBtth_Ngayhd( btth_ngayhd );
			
			btthBean.setBtth_Sohd( btth_sohd );
			
			btthBean.setBtth_TenNCChd( btth_tenNCChd );
			
			btthBean.setBtth_Thuesuathd( btth_thuesuathd );
			
			btthBean.setBtth_Tienhanghd( btth_tienhanghd );
			
			btthBean.setBtth_Tienthuehd( btth_tienthuehd );
			
			btthBean.setBtth_Ghichuhd( btth_ghichuhd );
			
			btthBean.setBtth_Conghd(btth_conghd);
			
			btthBean.setBtth_diachi(btth_diachi);
			dbutils db = new dbutils();
			
			//phần tỉ giá và tiền tê//
			String tiGia ="1";
			String tienTe = util.antiSQLInspection(request.getParameter("idTienTe"));
			
			if(tienTe == null){
				tienTe = "";
			}
			btthBean.setTienTe(tienTe);
			if(tienTe.equals("100000")){
				//nếu là việt nam đồng, mặc định tỉ giá là 1
				btthBean.setTiGia(1);
			}else{
				tiGia = util.antiSQLInspection(request.getParameter("tiGia"));
				if(!tiGia.equals("0")){
					btthBean.setTiGia(Double.parseDouble(tiGia.replace(",", "")));
				}
				else btthBean.setTiGia(0);
			}
			//-----------------------//
			
			
			// PHẦN POPUP SẢN PHẨM //
			Hashtable<String, String> sp_pk = new Hashtable<String, String>();
			Hashtable<String, String> stt = new Hashtable<String, String>();
			Hashtable<String, Double> sp_phanTram = new Hashtable<String, Double>();
			Hashtable<String, Double> sp_tienViet = new Hashtable<String, Double>();
			Hashtable<String, Double> sp_tienNT = new Hashtable<String, Double>();
			
//			double tongTien = 0;
			if(pk_seq != null)
			{
				for(int i = 0; i < c; i++){
					String sohieutaikhoanco = getSoHieu(btthBean.getTaiKhoanCoIds()[i],db );
					String sohieutaikhoanno = getSoHieu(btthBean.getTaiKhoanNoIds()[i], db);
					if(sohieutaikhoanco.indexOf("64")>=0  || sohieutaikhoanno.indexOf("64")>=0 ){
						//1. lấy giá trị
						String temID = pk_seq[i];						
						String[] spPK_SEQ = request.getParameterValues(temID + "_spPK_Seq");
			    		String[] spPhanTram = request.getParameterValues(temID + "_spPhanTram");
			    	//2.lọc giá trị
			    		if(spPK_SEQ != null){
			    			int demz = 0;
				    		for(int z= 0; z< spPK_SEQ.length; z++){
				    			if(!spPhanTram[z].equals("0")){
				    				demz ++;
				    			}
				    		}
				    		String[] locSP_PKSEQ = new String[demz];
				    		String[] locSP_PhanTram = new String[demz];
				    		Double[] locTienViet = new Double[demz];
				    		Double[] locTienNT = new Double[demz];
				    		int dem = 0;
				    		double tongNT = 0;
				    		double tongVN = 0;
				    		if(spPK_SEQ != null){
				    			for(int k = 0; k < spPK_SEQ.length; k++){
									if(!spPhanTram[k].equals("0")){
										locSP_PKSEQ[dem] = spPK_SEQ[k];
										locSP_PhanTram[dem] = spPhanTram[k];
										//3 tính tiền
										System.out.println("tiền đinh dạng: " + Double.parseDouble(Sotien[i].replaceAll(",", "")));
										if( k == spPK_SEQ.length - 1){
											if(!tienTe.equals("100000")){
												locTienNT[dem] = Double.parseDouble(SotienNT[i].replaceAll(",", "")) - tongNT;
												
											}else{
												locTienNT[dem] = 0.0;
											}
											locTienViet[dem] = Double.parseDouble(Sotien[i].replaceAll(",", "")) - tongVN;
											
											
										}else{
											if(!tienTe.equals("100000") && SotienNT != null && spPhanTram != null){
												locTienNT[dem] = (double)Math.round((Double.parseDouble(SotienNT[i].replaceAll(",", ""))*Double.parseDouble(spPhanTram[k].replaceAll(",", ""))/100)*1000.0)/1000.0;
												tongNT = tongNT + locTienNT[dem];
											}
											else{
												locTienNT[dem] = 0.0;
											}
											locTienViet[dem] = (double)Math.round((Double.parseDouble(Sotien[i].replaceAll(",", ""))*Double.parseDouble(spPhanTram[k].replaceAll(",","")))/100);
											tongVN = tongVN + locTienViet[dem];
										}
										
										dem ++;
									}
				    			}
								for(int t =0; t< locSP_PKSEQ.length; t++){
										//lưu lại hashtable
										sp_pk.put( pk_seq[i]+"_"+ locSP_PKSEQ[t], locSP_PKSEQ[t] );	
										stt.put( pk_seq[i]+"_"+locSP_PKSEQ[t], String.valueOf(i) );
										sp_phanTram.put( pk_seq[i]+"_"+ locSP_PKSEQ[t], Double.parseDouble(locSP_PhanTram[t].replaceAll(",", "")) );
										sp_tienViet.put( pk_seq[i]+"_"+ locSP_PKSEQ[t], locTienViet[t] );
										sp_tienNT.put( pk_seq[i]+"_"+ locSP_PKSEQ[t], locTienNT[t] );
									}
				    			}
			    		
								
							}
					}
				
				    
			    }		
			}
	
			
			btthBean.setStt(stt);
			btthBean.setMaSanPham(sp_pk);
			btthBean.setTienNT(sp_tienNT);
			btthBean.setTienViet(sp_tienViet);
			btthBean.setPhanTram(sp_phanTram);
			
			//---------------------//
			
			if (id == null)
			{
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpButToanTongHopNew.jsp";
			} else
			{
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpButToanTongHopUpdate.jsp";
			}
			if (action.equals("save"))
			{
				if (id == null)
				{
					if (!(btthBean.Save()))
					{
						session.setAttribute("btthBean", btthBean);
						response.sendRedirect(nextJSP);
					}
					else
					{
						IErpButToanTongHopList btthList = new ErpButToanTongHopList();
						btthList.setCongtyId((String)session.getAttribute("congtyId"));
						btthList.setUserId(userId);
						btthBean.setnppId(util.getIdNhapp(userId));
						btthBean.setNpp_duocchon_id(npp_duocchon_id);
			    	    String searchQuery=util.getSearchFromHM(userId,ServerletName, session);
						geso.traphaco.center.util.GiuDieuKienLoc.setParamsToOject(btthList, searchQuery);
						btthList.init();
						session.setAttribute("btthList", btthList);
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpButToanTongHop.jsp";
						response.sendRedirect(nextJSP);
					}
				} 
				else
				{
					
				}
			}else if(action.equals("update")){
				if (!(btthBean.Edit()))
				{
					session.setAttribute("btthBean", btthBean);
					response.sendRedirect(nextJSP);
				} 
				else
				{
					IErpButToanTongHopList btthList = new ErpButToanTongHopList();
					btthList.setCongtyId((String)session.getAttribute("congtyId"));
					btthList.setUserId(userId);
					btthBean.setnppId(util.getIdNhapp(userId));
					btthBean.setNpp_duocchon_id(npp_duocchon_id);
		    	    String searchQuery=util.getSearchFromHM(userId,ServerletName, session);
					geso.traphaco.center.util.GiuDieuKienLoc.setParamsToOject(btthList, searchQuery);
					btthList.init();
					session.setAttribute("btthList", btthList);
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpButToanTongHop.jsp";
					response.sendRedirect(nextJSP);
				}
			}
			else if(action.equals("savesauchot"))
			{
				if(id != null)
				{
					if (!(btthBean.Editsauchot()))
					{
						session.setAttribute("btthBean", btthBean);
						response.sendRedirect(nextJSP);
					} 
					else
					{
						IErpButToanTongHopList btthList = new ErpButToanTongHopList();
						btthList.setCongtyId((String)session.getAttribute("congtyId"));
						btthList.setUserId(userId);
						btthList.setnppId(util.getIdNhapp(userId));
						btthList.setNpp_duocchon_id(npp_duocchon_id);
			    	    String searchQuery=util.getSearchFromHM(userId,ServerletName, session);
						geso.traphaco.center.util.GiuDieuKienLoc.setParamsToOject(btthList, searchQuery);
						btthList.init();
						session.setAttribute("btthList", btthList);
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpButToanTongHop.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			}
			else{
				btthBean.createRs();
				String querySearch = GiuDieuKienLoc.createParams(btthBean);
			    util.setSearchToHM(userId, session, ServerletName, querySearch);
				session.setAttribute("btthBean", btthBean);
				response.sendRedirect(nextJSP);
				
			}
		}
	}
}
