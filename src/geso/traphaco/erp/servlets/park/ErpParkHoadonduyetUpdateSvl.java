package geso.traphaco.erp.servlets.park;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.park.IErpHoadon;
import geso.traphaco.erp.beans.park.IErpPark;
import geso.traphaco.erp.beans.park.IErpParkList;
import geso.traphaco.erp.beans.park.IErpHoadonSp;
import geso.traphaco.erp.beans.park.imp.ErpHoadon;
import geso.traphaco.erp.beans.park.imp.ErpPark;
import geso.traphaco.erp.beans.park.imp.ErpParkList;
import geso.traphaco.erp.beans.park.imp.ErpHoadonSp;

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

public class ErpParkHoadonduyetUpdateSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	PrintWriter out;
    public ErpParkHoadonduyetUpdateSvl() {
        super();
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}
		else
		{
			session.setMaxInactiveInterval(30000);
		
			this.out = response.getWriter();
			Utility util = new Utility();
			
	    	String querystring = request.getQueryString();
		    userId = util.getUserId(querystring);
		    
		    if (userId.length()==0)
		    	userId = util.antiSQLInspection(request.getParameter("userId")); 	
		    String id = util.getId(querystring);  	
			IErpPark pBean = new ErpPark(id);
			
	        pBean.setUserId(userId); //phai co UserId truoc khi Init
	        pBean.setCtyId((String)session.getAttribute("congtyId"));
	        pBean.setnppdangnhap(util.getIdNhapp(userId));
	        
	        String loaihd = util.antiSQLInspection(request.getParameter("loaihd"));
	        pBean.setLoaihd(loaihd);	    	
        	pBean.init();
        	
	        String nextJSP;
	        
	        if(request.getQueryString().indexOf("display") >= 0 ) 
	        {
	        	nextJSP = "/TraphacoHYERP/pages/Erp/ErpParkHoaDonDuyetDisplay.jsp";
	        }
	        else
	        {
	        	nextJSP = "/TraphacoHYERP/pages/Erp/ErpParkHoaDonDuyetUpdate.jsp";
	        }
	        
	        session.setAttribute("pBean", pBean);
	        session.setAttribute("maduyet", id);
	        session.setAttribute("nccduyet", pBean.getNccId());
	        response.sendRedirect(nextJSP);
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen"); 
		
	    String ServerletName = this.getServletName();
		
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}
		else
		{
			request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
			
			session.setMaxInactiveInterval(30000);
			
			this.out = response.getWriter();
			IErpPark pBean;
			
			Utility util = new Utility();
			String id = util.antiSQLInspection(request.getParameter("id"));
			
			System.out.println("so Id " + id);
			
		    if(id == null)
		    {  	
		    	pBean = new ErpPark("");
		    }
		    else
		    {
		    	pBean = new ErpPark(id);
		    }
	
		    pBean.setUserId(userId);
		    pBean.setCtyId((String)session.getAttribute("congtyId"));
	        pBean.setnppdangnhap(util.getIdNhapp(userId));
	        String action = request.getParameter("action");
		    
	        String loaihd = util.antiSQLInspection(request.getParameter("loaihd"));
	        pBean.setLoaihd(loaihd);	
        	
	        String nccId = util.antiSQLInspection(request.getParameter("nccId"));
		    if(nccId == null)
		    	nccId = "";
		    pBean.setNccId(nccId);

		    String ngayghinhan = util.antiSQLInspection(request.getParameter("ngayghinhan"));
			if (ngayghinhan == null || ngayghinhan == "")
				ngayghinhan = getDateTime();		
	    	pBean.setNgayghinhan(ngayghinhan);
	    		    	
	    	String ttId = util.antiSQLInspection(request.getParameter("ttId"));
			if (ttId == null ){
				ttId = "";		
			}
	    	pBean.setTtId(ttId);
	    	
	    	String tongsoluong = util.antiSQLInspection(request.getParameter("tongsoluong"));
			if (tongsoluong == null || tongsoluong == "")
				tongsoluong = "0";		
	    	pBean.setTongsoluong(tongsoluong.replaceAll(",", ""));
	    	
	    	String tigia = util.antiSQLInspection(request.getParameter("tigia"));
			if (tigia == null)
				tigia = "";		
	    	pBean.setTigia(tigia.replaceAll(",", ""));
	    	
	    	// NCC THAY THÊ
	    	 String nccTTId = util.antiSQLInspection(request.getParameter("nccTTId"));
			    pBean.setNCCThayTheId(nccTTId);
			    
		    String nccTTTen = util.antiSQLInspection(request.getParameter("nccTTTen"));
		    if(nccTTTen == null)
		    	nccTTTen = "";
		    pBean.setNCCThayTheTen(nccTTTen);
		    
		    String nccTTMST = util.antiSQLInspection(request.getParameter("nccTTMST"));
		    if(nccTTMST == null)
		    	nccTTMST = "";
		    pBean.setNCCThayTheMST(nccTTMST);
		    
		    String nccTTDiachi = util.antiSQLInspection(request.getParameter("nccTTDiachi"));
		    if(nccTTDiachi == null)
		    	nccTTDiachi = "";
		    pBean.setNCCThayTheDiaChi(nccTTDiachi);
		    
		    String loaidonmuahangId = util.antiSQLInspection(request.getParameter("loaidonmuahangId"));
		    if(loaidonmuahangId == null)
		    	loaidonmuahangId = "";
		    pBean.setLoaidonmh(loaidonmuahangId);
	    	
	    	String[] poNKIDs = request.getParameterValues("poNKID");
	    	String poNKID = "";
			if (poNKIDs != null )
			{
				for(int i = 0; i < poNKIDs.length; i++)
					poNKID += poNKIDs[i] + ",";
				
				if(poNKID.trim().length() > 0)
				{
					poNKID = poNKID.substring(0, poNKID.length() - 1);
				}
			}	
			
			pBean.setPoNkIds(poNKID);
			
		    List<IErpHoadon> hdList = new ArrayList<IErpHoadon>();
		    IErpHoadon hoadon = null;	
		    		  
		    	for(int i = 0; i < 10; i++)
		    	{
		    		String mauhoadon = util.antiSQLInspection(request.getParameter("mauhoadon" + i));
		    		String mausohoadon = util.antiSQLInspection(request.getParameter("mausohoadon" + i));
		    		String sohoadon = util.antiSQLInspection(request.getParameter("sohoadon" + i));
		    		String hoadonpk_seq = util.antiSQLInspection(request.getParameter("pk_seq" + i));
		    		String kyhieuhoadon = util.antiSQLInspection(request.getParameter("kyhieuhoadon" + i));
		    		String ngayhoadon = util.antiSQLInspection(request.getParameter("ngayhoadon" + i));
		    		String sotienchuathue = util.antiSQLInspection(request.getParameter("sotienchuathue" + i));
		    		String chietkhau = util.antiSQLInspection(request.getParameter("chietkhau" + i));
		    		String thuesuat = util.antiSQLInspection(request.getParameter("thuesuat" + i));
		    		String vat = util.antiSQLInspection(request.getParameter("vat" + i));
		    		String tongcong = util.antiSQLInspection(request.getParameter("tongcong" + i));
		    		
		    		List<IErpHoadonSp> spList = new ArrayList<IErpHoadonSp>();
		    		
		    		if(sohoadon != null)
		    		{				    
		    			if(thuesuat == null)
		    				thuesuat = "";
			    	
		    			if(chietkhau == null)
		    				chietkhau = "";
			    	
		    			if(vat == null)
		    				vat="";
		    			vat = vat.replaceAll(",","");
		    			

	    				System.out.println("sohoadon:"+	sohoadon+"kyhieuhoadon: "+kyhieuhoadon);
			    	
		    			if(sohoadon != "" && kyhieuhoadon != "" && ngayhoadon != ""  && ngayghinhan != "")
		    			{
		    				hoadon = new ErpHoadon();
		    				System.out.println("vaoday");
		    				hoadon.setId(hoadonpk_seq);
		    				hoadon.setChieckhau(chietkhau.replaceAll(",", ""));
		    				hoadon.setMauhoadon(mauhoadon);
		    				hoadon.setMausohoadon(mausohoadon);
		    				hoadon.setKyhieu(kyhieuhoadon);
		    				hoadon.setNgayhoadon(ngayhoadon);
		    				hoadon.setSohoadon(sohoadon);
		    				hoadon.setVAT(vat);
		    				hoadon.setThuesuat(thuesuat);
		    				hoadon.setSotienbvat(sotienchuathue.replaceAll(",", ""));
		    				hoadon.setSotienavat(tongcong.replaceAll(",", ""));	
		    				hoadon.setChieckhau(chietkhau.replaceAll(",", ""));
		    				
			    		
		    				String[] poId = request.getParameterValues("sohoadon"+i+".poId");
		    				String[] poTen = request.getParameterValues("sohoadon"+i+".soPO");
		    				String[] loai = request.getParameterValues("sohoadon"+i+".loai");
		    				String[] spId = request.getParameterValues("sohoadon"+i+".mahang");
		    				String[] spma = request.getParameterValues("sohoadon"+i+".ma");
		    				String[] tenhang = request.getParameterValues("sohoadon"+i+".tenhang");
		    				String[] dungsai = request.getParameterValues("sohoadon"+i+".dungsai");
		    				String[] donvi = request.getParameterValues("sohoadon"+i+".donvi");
		    				String[] soluong = request.getParameterValues("sohoadon"+i+".soluongdat");
		    				String[] soluonghd = request.getParameterValues("sohoadon"+i+".soluong"); // SỐ LƯỢNG HÓA ĐƠN ĐẶT
		    				String[] soluongRaHD = request.getParameterValues("sohoadon"+i+".soluongRaHD");
		    				String[] dongia = request.getParameterValues("sohoadon"+i+".dongia");
		    				String[] thanhtien = request.getParameterValues("sohoadon"+i+".thanhtien");
		    				String[] spthuesuat = request.getParameterValues("sohoadon"+i+".spthuesuat");
		    				String[] sptienvat = request.getParameterValues("sohoadon"+i+".sptienvat");
		    				String[] spsolo = request.getParameterValues("sohoadon"+i+".solo");
		    				//String[] chon = request.getParameterValues("sohoadon"+i+".thanhtien");
		    				
		    				//String[] hoantat = request.getParameterValues("sohoadon"+i+".checkHT");
		    				
		    				
		        				for(int m= 0; m < poId.length; m++)
		        				{
		        					if(poId[m].trim().length() > 0)
		        					{		
		        						System.out.println("Size "+poId[m]);
		        						if(soluonghd[m]== null)
		        							soluonghd[m] = "0";		        						
		        							        						
		        						if(Double.parseDouble(soluonghd[m].replaceAll(",", ""))>0)
		        						{
				        					IErpHoadonSp sp = new ErpHoadonSp(poId[m], poTen[m], spId[m], tenhang[m], soluong[m].replaceAll(",", ""), dongia[m].replaceAll(",", ""),
			        						           thanhtien[m].replaceAll(",", ""), donvi[m], loai[m], "", "");
				        								        				
				        					sp.setSanphamMa(spma[m]);
				        					sp.setDungsai(dungsai[m]);
				        					sp.setSoluonghd(soluonghd[m].replaceAll(",", ""));
				        					sp.setSoluongRaHD(soluongRaHD[m].replaceAll(",", ""));
				        					sp.setSolo(spsolo[m]);
				        					//sp.setNgaynhan(ngaynhandk[m]);
				        					sp.setVAT(spthuesuat[m].replaceAll(",", ""));
				        					sp.setTienVat(sptienvat[m].replaceAll(",", ""));
				        					spList.add(sp);
		        						}
		        					}
		        					
		        				}
		        				
		        				hoadon.setPnkList(spList);	
		        			hdList.add(hoadon);
		    			}
		    			
		    		}
		    	}
		    	pBean.setHdList(hdList);
		    
		    
			if(action.equals("save"))
			{	
				if(!pBean.updatePark())
				{
					//pBean.createRs();
					pBean.init();
					session.setAttribute("pBean", pBean);
    				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpParkHoaDonDuyetUpdate.jsp";
    				response.sendRedirect(nextJSP);
				}
				else
				{
					IErpParkList obj = new ErpParkList();
				    obj.setUserId(userId);
				    obj.setDuyet(1);
				    obj.setCongtyId((String)session.getAttribute("congtyId"));
				    obj.setnppdangnhap(util.getIdNhapp(userId));
				    String search = this.getSearchQuery(request, obj);	
				    obj.init(search);
					session.setAttribute("obj", obj);							
					String nextJSP = "/TraphacoHYERP/pages/Erp/ErpParkHoaDonDuyet.jsp";
					response.sendRedirect(nextJSP);
				}
			}
			else if(action.equals("savehd"))
			{	
				if(!pBean.updatePark_Duyet())
				{
					//pBean.createRs();
					pBean.init();
					session.setAttribute("pBean", pBean);
    				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpParkHoaDonDuyetUpdate.jsp";
    				response.sendRedirect(nextJSP);
				}
				else
				{
					IErpParkList obj = new ErpParkList();
				    obj.setUserId(userId);
				    obj.setDuyet(1);
				    obj.setCongtyId((String)session.getAttribute("congtyId"));
				    obj.setnppdangnhap(util.getIdNhapp(userId));
				    String search = this.getSearchQuery(request, obj);	
				    
				    String searchQuery=util.getSearchFromHM(userId,ServerletName, session);
					geso.traphaco.center.util.GiuDieuKienLoc.setParamsToOject(obj, searchQuery);
				    
				    obj.init(search);
					session.setAttribute("obj", obj);							
					String nextJSP = "/TraphacoHYERP/pages/Erp/ErpParkHoaDonDuyet.jsp";
					response.sendRedirect(nextJSP);
				}
				System.out.println("asdsadsaasd"+action);
				 if(action.equals("chot"))
				    {
					 	
							geso.traphaco.erp.beans.park.IErpPark pBeanDuyet = new geso.traphaco.erp.beans.park.imp.ErpPark(id);
							pBean.setCtyId((String)session.getAttribute("congtyId"));
							pBean.setUserId(userId);
							pBean.setnppdangnhap(util.getIdNhapp(userId));
							pBeanDuyet.init();
				    		pBeanDuyet.Duyet();	
				    		pBean.setMsg(pBeanDuyet.getMsg());
				    		session.setAttribute("pBean", pBean);
							String nextJSP = "/TraphacoHYERP/pages/Erp/ErpParkHoaDonDuyet.jsp";
							response.sendRedirect(nextJSP);
				    }
			}
			 
				
		}
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpParkList obj)
	{
		String query =   			
				" SELECT A.PK_SEQ , A.NGAYGHINHAN, NCC.TEN AS TENNHACUNGCAP, A.TRANGTHAI ,     	NV.TEN AS TENNV,A.NGAYSUA,A.NGAYTAO,NV.PK_SEQ AS MANV,NV2.TEN AS \n" +  
				"  		 TENNVS,NV2.PK_SEQ AS MANVS, A.PREFIX     	, HOANTAT.SODEM  AS DAHOANTAT , CASE WHEN THANHTOAN.PARK_FK IS NULL THEN 0 ELSE 1 END AS DACOTHANHTOAN, ISNULL(HDNCC.SOHOADON,'') AS SOHOADON, HDNCC.LOAIHD	, \n" +
				"		(SELECT COUNT(PK_SEQ) FROM ERP_NHANHANG WHERE HDNCC_FK = HDNCC.PK_SEQ  AND TRANGTHAI NOT IN (3) ) DANHANHANG, NCC.PK_SEQ NCCID, HDNCC.PK_SEQ HOADONID, \n"+
				" ( SELECT CASE WHEN HDMH1.SANPHAM_FK IS NOT NULL THEN 0 WHEN HDMH1.TAISAN_FK IS NOT NULL THEN 1 WHEN HDMH1.CCDC_FK IS NOT NULL THEN 3 ELSE 2 END LOAIHH \n"+
				" FROM \n"+
				" (SELECT TOP (1) * " +
				"  FROM ERP_HOADONNCC_DONMUAHANG HDMH " +
				"  WHERE HDMH.HOADONNCC_FK = HDNCC.PK_SEQ ) HDMH1 ) LOAIHH \n"+
				
				" FROM 	 ERP_PARK A   	INNER JOIN ERP_NHACUNGCAP NCC ON NCC.PK_SEQ = A.NCC_FK   	\n" +
				"		 INNER JOIN NHANVIEN NV ON A.NGUOITAO = NV.PK_SEQ   \n" +  
				"  		 INNER JOIN NHANVIEN NV2 ON A.NGUOISUA = NV2.PK_SEQ	     \n" +  
				"  		 LEFT JOIN 	(   \n" +  
				"   				 SELECT A.PARK_FK , (DEMA - ISNULL (DEMB,0)) AS SODEM \n" +
				"					 FROM 	(SELECT PARK_FK , COUNT(PARK_FK) AS DEMA  	\n" +
				"							 FROM ERP_HOADONNCC   \n" +
				"							 WHERE CONGTY_FK = "+obj.getCongtyId() +
				"   				 		 GROUP BY PARK_FK) AS A  	\n" +
				"							 LEFT JOIN   	\n" +
				"							(SELECT PARK_FK , COUNT(PARK_FK) AS DEMB  	\n" +
				"							 FROM ERP_HOADONNCC  	 \n" +  
				"   				 		 WHERE TRANGTHAI = 2 AND CONGTY_FK = "+obj.getCongtyId()+
				"							 GROUP BY PARK_FK) AS B ON B.PARK_FK = A.PARK_FK  \n" +  
				"  					) HOANTAT ON HOANTAT.PARK_FK = A.PK_SEQ   	 \n" +  
				"  		 LEFT JOIN 	(  \n" +  
				"   				SELECT 	DISTINCT HD.PARK_FK 		\n"+
				"   				FROM 	ERP_THANHTOANHOADON_HOADON TT		\n"+   
				"   					 	INNER JOIN ERP_HOADONNCC HD ON HD.PK_SEQ = TT.HOADON_FK AND TT.LOAIHD = 0 \n"+
				"   				WHERE 	TT.THANHTOANHD_FK IN (SELECT PK_SEQ FROM ERP_THANHTOANHOADON WHERE TRANGTHAI != 2 AND CONGTY_FK = "+obj.getCongtyId()+" and NPP_FK = "+obj.getnppdangnhap()+" )  \n"+
				"							AND HD.CONGTY_FK = "+obj.getCongtyId()+
				"  					) THANHTOAN ON THANHTOAN.PARK_FK = A.PK_SEQ " +
				" INNER JOIN ERP_HOADONNCC HDNCC ON A.PK_SEQ=HDNCC.PARK_FK "+
				" where a.pk_seq > 0  and a.trangthai >= 1 AND HDNCC.CONGTY_FK ="+obj.getCongtyId()+ " \n";		
		
		String ngayghinhan = request.getParameter("ngayghinhan");
		if(ngayghinhan == null)
			ngayghinhan = "";
		obj.setNgayghinhan(ngayghinhan);
		
		String nhacungcap = request.getParameter("nhacungcap");
		if(nhacungcap == null)
			nhacungcap = "";
		obj.setNcc(nhacungcap);
		
		String loaihang = request.getParameter("loaihang");
		if(loaihang == null)
			loaihang = "";
		obj.setLoaihang(loaihang);
		
		String sopark = request.getParameter("sopark");
		if(sopark == null)
			sopark = "";
		obj.setId(sopark);
		
		String sohoadon = request.getParameter("sohoadon");
		if(sohoadon == null) sohoadon = ""; else sohoadon = sohoadon.trim();
		obj.setSOHOADON(sohoadon);
		
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null) 
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		if(ngayghinhan.length() > 0)
			query += " and a.ngayghinhan = '" + ngayghinhan + "'";
		
		if(nhacungcap.length() > 0)
			query += " and a.ncc_fk = '" + nhacungcap + "'";
		
		if(loaihang.length() > 0)
			query += " and a.LOAIHD = '" + loaihang + "'";
		if(sopark.length() > 0)
			query += " and (('170'+ cast(a.pk_seq as nvarchar(50)) like '%" + sopark + "%' ))";
		if(sohoadon.length() > 0) {
			query += " and a.pk_seq in (select park_fk from ERP_HOADONNCC where sohoadon like N'%" + sohoadon + "%') ";
		}
		if(trangthai.length()>0)
		{
			query+= " and a.trangthai = "+trangthai;
		}

		return query;
		
		
		
	}
	
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

}
