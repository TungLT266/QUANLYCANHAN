package geso.traphaco.erp.servlets.lenhsanxuat;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.doiquycach.IDoiquycach;
import geso.traphaco.erp.beans.doiquycach.ISpDoiquycach;
import geso.traphaco.erp.beans.doiquycach.imp.Doiquycach;
import geso.traphaco.erp.beans.doiquycach.imp.SpDoiquycach;
import geso.traphaco.erp.beans.lenhsanxuat.IErpChuyenkhoSX;
import geso.traphaco.erp.beans.lenhsanxuat.IErpChuyenkhoSXList;
import geso.traphaco.erp.beans.lenhsanxuat.IYeucau;
import geso.traphaco.erp.beans.lenhsanxuat.imp.ErpChuyenkhoSX;
import geso.traphaco.erp.beans.lenhsanxuat.imp.ErpChuyenkhoSXList;
import geso.traphaco.erp.beans.lenhsanxuat.imp.Yeucau;
import geso.traphaco.erp.beans.phieuxuatkho.ISanpham;
import geso.traphaco.erp.beans.phieuxuatkho.ISpDetail;
import geso.traphaco.erp.beans.phieuxuatkho.imp.SpDetail;
import geso.traphaco.erp.db.sql.dbutils;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class ErpChuyenkhoSXUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	private static float CM = 28.346457f;
       
	PrintWriter out;
    public ErpChuyenkhoSXUpdateSvl() 
    {
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
		
			Utility util = new Utility();
			
	    	String querystring = request.getQueryString();
		    userId = util.getUserId(querystring);
		    
		    if (userId.length()==0)
		    	userId = util.antiSQLInspection(request.getParameter("userId")); 
		    
		    String id = util.getId(querystring);
		    
 
		    IErpChuyenkhoSXList k = new ErpChuyenkhoSXList();
			
			String tungay ="";
			try{
			    tungay = util.getCat(querystring, 2, 1);
			    System.out.println("tu ngay: "+ tungay);
			}
			catch(Exception er)
			{
			    tungay="";
			}
 
		    IErpChuyenkhoSX lsxBean = new ErpChuyenkhoSX(id);
		    lsxBean.setUserId(userId); 
		    
		    String task = request.getParameter("task");
			if(task == null)
				task = "chuyenNL";
			lsxBean.settask(task);
			
		    String isnhanhang = request.getParameter("isnhanHang");
			if(isnhanhang == null)
				isnhanhang = "";
			lsxBean.setIsnhanHang(isnhanhang);
			
					
			String denngay="";
			try{
			    denngay = util.getCat(querystring, 3, 1);
				 
			}
			catch(Exception er)
			{
				denngay="";
			}
			
			String trangthai="";
			try{
			    	trangthai = util.getCat(querystring, 4, 1);
				     
			}
			catch(Exception er)
			{
					trangthai="";
			}
						
			String sophieu="";
			 try{
				 	sophieu = util.getCat(querystring, 5, 1);
				     
			 }
			 catch(Exception er)
			 {
				 sophieu="";
			 }
			 
			 String nguoitao="";
			 try{
				 	nguoitao = util.getCat(querystring, 6, 1);
			 
			 }
			 catch(Exception er)
			 {
				 nguoitao="";
			 }

			 String nguoisua="";
			 try{
				 	nguoisua = util.getCat(querystring, 7, 1);
				    
			 }
			 catch(Exception er)
			 {
				 nguoisua="";
			 }
			 
			 if(tungay!="")
					k.setTungayTao(tungay);
				
			 if(denngay!="")
					k.setDenngayTao(denngay);
				
			 if(trangthai!="")
					k.setTrangthai(trangthai);
			
			 if(sophieu!="")
					k.setSophieu(sophieu);
			 
			 if(nguoitao!="")
				 k.setNguoitao(nguoitao);
			 
			 if(nguoisua!="")
				 k.setNguoisua(nguoisua);
			 session.setAttribute("obj", k);
			 
			 //Hết chỉnh sửa
			 
	        String nextJSP = "";
        	if(request.getQueryString().indexOf("display") >= 0 ) 
	        {
        		lsxBean.initView();
	        	nextJSP = "/TraphacoHYERP/pages/Erp/ErpChuyenKhoSanXuatDisplay.jsp";
	        }
	        else if(request.getQueryString().indexOf("nhanhang") >= 0 ) 
        	{
        		lsxBean.initNhanhang();
        		nextJSP = "/TraphacoHYERP/pages/Erp/ErpChuyenKhoSanXuatNhanHang.jsp";
        	}
        	else if(request.getQueryString().indexOf("xuathang") >= 0 ) 
        	{
        		lsxBean.initXuathang();
        		nextJSP = "/TraphacoHYERP/pages/Erp/ErpChuyenKhoSanXuatXuatHang.jsp";
        	}
    		else if(request.getQueryString().indexOf("hoantat") >= 0 ) 
    		{
    			lsxBean.initView();
        		nextJSP = "/TraphacoHYERP/pages/Erp/ErpChuyenKhoSanXuatHoanTat.jsp";	
    		}
    		else if(request.getQueryString().indexOf("doiquycach") >= 0 ) {
    			
    			dbutils db = new dbutils();
    			
    			IDoiquycach obj = new Doiquycach();	
    			String chuyenkhoid=  request.getParameter("doiquycach");
    			
    			String kho_doiquycach="";
    			String query="";
    			
    			query= 	" select ck.KhoNhan_FK as kho_doiquycach, cksp.SANPHAM_FK as spId, sp.MA as spma, sp.TEN as spten, dvdl.DIENGIAI as dvdlten, cksp.SOLUONGXUAT as slxuat  \n"+
    					" from ERP_CHUYENKHO ck \n"+
    					" inner join ERP_CHUYENKHO_SANPHAM cksp on ck.PK_SEQ= cksp.CHUYENKHO_FK \n"+
    					" inner join ERP_SANPHAM sp on cksp.SANPHAM_FK=sp.PK_SEQ  \n"+
    					" inner join DONVIDOLUONG dvdl on sp.DVDL_FK = dvdl.PK_SEQ  \n"+
    				    " where ck.pk_seq="+chuyenkhoid;
    			int stt=0;
    			ResultSet rs = db.get(query);
    			 List<ISpDoiquycach> spDoiquycachlist = new ArrayList<ISpDoiquycach>();
    			 
    			 List<ISpDetail> spDetailList = new ArrayList<ISpDetail>();
    			 
    			 if(rs!=null){
	    			try{	 
	    				 while(rs.next()){
	    					 
	    					 if(stt < 1){
	    						 kho_doiquycach= rs.getString("kho_doiquycach");
	    					 }
	    					 
	    					 ISpDoiquycach spdqc=new SpDoiquycach();
	    					 
	    					 //ISpDetail sp_chitiet = new SpDetail();
	    					 
	    					 spdqc.setMa(rs.getString("spma"));
	    					 spdqc.setSpId1(rs.getString("spId"));
	    					 spdqc.setSpTen1(rs.getString("spten"));
	    					 spdqc.setdonvitinh(rs.getString("dvdlten"));
	    					 spdqc.setreload_sp("1");
	    					 spdqc.setSoluong1(rs.getDouble("slxuat"));
	    					 
	    					 query=	 " SELECT CKSP.SANPHAM_FK,CKSP.SOLO,CKSP.SOLUONG,ISNULL(KHU,0) AS KHUID,KHO.NGAYBATDAU, " +
		    					 	 " KHO.NGAYHETHAN,KHO.NGAYNHAPKHO,KHO.NGAYSANXUAT,KHO.SOLUONG AS SOLUONGTON "+
		    						 " FROM ERP_CHUYENKHO CK INNER JOIN ERP_CHUYENKHO_SP_NHANHANG   CKSP ON CK.PK_SEQ=CKSP.CHUYENKHO_FK "+
		    						 " INNER JOIN ERP_KHOTT_SP_CHITIET KHO ON KHO.SANPHAM_FK=CKSP.SANPHAM_FK AND KHO.KHOTT_FK=CK.KHONHAN_FK AND KHO.SOLO=CKSP.SOLO "+ 
		    						 " AND KHO.NGAYBATDAU=CKSP.NGAYBATDAU  "+
		    						 " WHERE CHUYENKHO_FK="+chuyenkhoid+" AND CKSP.SANPHAM_FK= "+rs.getString("spId");
	    					 
	    					 
	    					 ResultSet rsdetail=db.get(query);
	    					 while(rsdetail.next()){
	    						 ISpDetail sp_chitiet = new SpDetail();
	    						 
		    					 sp_chitiet.setSoluong(rsdetail.getString("SOLUONG"));
		    					 sp_chitiet.setId(rsdetail.getString("SANPHAM_FK"));
		    					 sp_chitiet.setSolo(rsdetail.getString("SOLO").trim());
		    					 sp_chitiet.setKhuId(rsdetail.getString("KHUID"));
		    					 sp_chitiet.setNgaybatdau(rsdetail.getString("ngaybatdau"));
		    					 sp_chitiet.setNgayhethan(rsdetail.getString("ngayhethan"));
		    					 sp_chitiet.setNgaysanxuat(rsdetail.getString("ngaysanxuat"));
		    					 sp_chitiet.setSoluongton(rsdetail.getString("SOLUONGTON"));
		    					 spDetailList.add(sp_chitiet);
		    					 
	    					 }
	    					 spdqc.setSpDetailList(spDetailList);
	    					  
	    					 spDoiquycachlist.add(spdqc);
	    					 
	    				 } rs.close();
	    			}catch(Exception e){e.printStackTrace();}
    			 }
    			 
    			 obj.setKhoId(kho_doiquycach);
    			 obj.setSpDoiquycachlist(spDoiquycachlist);
    			 obj.setSpDetailList(spDetailList);
    			 obj.set_xuatkho_doiquycach("1"); // set=1  khi tạo mới đổi quy cách thành công, thì ko cho đổi quy cách nữa 
    			 obj.set_xuatkhoId_DQC(chuyenkhoid);  // giữ lại số phiếu để cập nhật
    			 
    			 
    			 obj.init();
    			 //obj.Reload_Sp();
    			 obj.ReLoad_DonGia();
    			 lsxBean.DBclose();
    			 session.setAttribute("khoId", kho_doiquycach);
    			 session.setAttribute("obj", obj);
    			 nextJSP = "/TraphacoHYERP/pages/Erp/DoiquycachUpdate.jsp";
    			 db.shutDown();
    		}
    		else if(request.getQueryString().indexOf("print") >= 0 ) 
    		{
    			lsxBean.initPdf();
    			response.setContentType("application/pdf");
    			response.setHeader("Content-Disposition"," inline; filename=YeuCauXuatKho.pdf");
    			
    			ServletOutputStream outstream = response.getOutputStream();
    			
    			//System.out.println("[ErpChuyenkhoSXUpdateSvl] khoxuatId = " + lsxBean.getKhoXuatId());
    			if(lsxBean.getKhoXuatId().equals("100016")) {
    				this.CreatePdf_KhoVatTu(outstream, lsxBean);
    			} 
    			else if (lsxBean.getKhoXuatId().equals("100010") || lsxBean.getKhoXuatId().equals("100011")) {
    				this.CreatePdf_GiaCong(outstream, lsxBean);
    			}   
    			
    			//----- NẾU XUẤT TT-XN & TT-XL THÌ IN MẪU NÀY----//
    			else if(lsxBean.getNdxId().equals("100009") || lsxBean.getNdxId().equals("100021")){
    				
    				lsxBean.inPdf();
    				//System.out.println();
    				//System.out.println("noi dung xuat hgvhg: "+ lsxBean.getNdxId());
    				float CONVERT = 28.346457f;
    				float PAGE_LEFT = 1.0f*CONVERT, PAGE_RIGHT = 1.5f*CONVERT, PAGE_TOP = 1.0f*CONVERT, PAGE_BOTTOM = 0.0f*CONVERT; //cm
    				Rectangle a5 = new Rectangle(PageSize.A4.getWidth(), PageSize.A4.getHeight()/2);
    				Document document = new Document(a5, PAGE_LEFT, PAGE_RIGHT, PAGE_TOP, PAGE_BOTTOM);
    				
    				this.CreatePxk(document, outstream, lsxBean);
    			}    			
    			else {
    				//System.out.println(" hgvhg: "+ lsxBean.getNdxId());
    				this.CreatePdf_VatTu(outstream, lsxBean);
    			}    			
    		}
    		else
    		{
        		lsxBean.init();
        		
        		session.setAttribute("khochuyenIds", lsxBean.getKhoXuatId());
        		session.setAttribute("nccchuyenId", lsxBean.getNccChuyenIds());
			 
				
        		nextJSP = "/TraphacoHYERP/pages/Erp/ErpChuyenKhoSanXuatUpdate.jsp";
    		}
	        session.setAttribute("lsxBean", lsxBean);
	        response.sendRedirect(nextJSP);
		}		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
//		String userTen = (String) session.getAttribute("userTen"); 
		
//		String sum = (String) session.getAttribute("sum");
//		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		
		/*if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}
		else
		{*/
			request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
			
			session.setMaxInactiveInterval(30000);
			
			this.out = response.getWriter();
			IErpChuyenkhoSX lsxBean;
			
			Utility util = new Utility();	
			String id = util.antiSQLInspection(request.getParameter("id"));
		    if(id == null)
		    {  	
		    	lsxBean = new ErpChuyenkhoSX("");
		    }
		    else
		    {
		    	lsxBean = new ErpChuyenkhoSX(id);
		    }
	
		    lsxBean.setUserId(userId);
		    
		    String task = request.getParameter("task");
			if(task == null)
				task = "chuyenNL";
			lsxBean.settask(task);
			

		    String isnhanHang = request.getParameter("isnhanHang");
			if(isnhanHang == null)
				isnhanHang = "";
			lsxBean.setIsnhanHang(isnhanHang);
		    
		    String ngayyeucau = util.antiSQLInspection(request.getParameter("ngaychuyen"));
		    if(ngayyeucau == null || ngayyeucau.trim().length() <= 0)
		    	ngayyeucau = getDateTime();
		    lsxBean.setNgayyeucau(ngayyeucau);
		    
		    String lydo = util.antiSQLInspection(request.getParameter("lydo"));
		    if(lydo == null)
		    	lydo = "";
		    lsxBean.setLydoyeucau(lydo);
		    
 
		    
		    String yeucauchuyenkhoid = util.antiSQLInspection(request.getParameter("yeucauchuyenkhoid"));
		    if(yeucauchuyenkhoid == null)
		    	yeucauchuyenkhoid = "";
		    lsxBean.setYeucauchuyenkhoId(yeucauchuyenkhoid);
		    
		    
		    String ghichu = util.antiSQLInspection(request.getParameter("ghichu"));
		    if(ghichu == null)
		    	ghichu = "";
		    lsxBean.setGhichu(ghichu);
		    
		    
		    String IsChuyenHangSX = util.antiSQLInspection(request.getParameter("IsChuyenHangSX"));
		    if(IsChuyenHangSX == null)
		    	IsChuyenHangSX = "0";
		    lsxBean.setIsChuyenHangSX(IsChuyenHangSX);
		    
		    String nguoinhan = util.antiSQLInspection(request.getParameter("nguoinhan"));
		    if(nguoinhan == null)
		    	nguoinhan = "";
		    lsxBean.setNguoinhan(nguoinhan);
		    
		    
		    String noidungxuat = util.antiSQLInspection(request.getParameter("noidungxuat"));
			if (noidungxuat == null)
				noidungxuat = "";	
			lsxBean.setNdxId(noidungxuat);
	    	
		    String khoxuatId = util.antiSQLInspection(request.getParameter("khoxuatId"));
			if (khoxuatId == null)
				khoxuatId = "";				
			lsxBean.setKhoXuatId(khoxuatId);
			
			String trangthaisp = util.antiSQLInspection(request.getParameter("trangthaisp"));
			if(trangthaisp ==null){
				trangthaisp="0";
			}
			lsxBean.setTrangthaiSP(trangthaisp);
			
			String khonhapId = util.antiSQLInspection(request.getParameter("khonhapId"));
			if (khonhapId == null)
				khonhapId = "";				
			lsxBean.setKhoNhapId(khonhapId);
			
			String nccchuyenId = util.antiSQLInspection(request.getParameter("nccchuyenId"));
			if (nccchuyenId == null)
				nccchuyenId = "";				
			lsxBean.setNccChuyenIds(nccchuyenId);
			
			String nccnhanId = util.antiSQLInspection(request.getParameter("nccnhanId"));
			if (nccnhanId == null)
				nccnhanId = "";				
			lsxBean.setNccNhanIds(nccnhanId);
			
			String lsxid = util.antiSQLInspection(request.getParameter("lsxid"));
			if (lsxid == null)
				lsxid = "";				
			lsxBean.setLsxIds(lsxid);
			
			String kyhieu = util.antiSQLInspection(request.getParameter("kyhieu"));
			if (kyhieu == null)
				kyhieu = "";				
			lsxBean.setKyHieu(kyhieu);
			
			String sochungtu = util.antiSQLInspection(request.getParameter("sochungtu"));
			if (sochungtu == null)
				sochungtu = "";				
			lsxBean.setSochungtu(sochungtu);
			
			String lenhdieudong = util.antiSQLInspection(request.getParameter("lenhdieudong"));
			if (lenhdieudong == null)
				lenhdieudong = "";				
			lsxBean.setLenhdieudong(lenhdieudong);
			
			String ngaydieudong = util.antiSQLInspection(request.getParameter("ngaydieudong"));
			if (ngaydieudong == null)
				ngaydieudong = "";				
			lsxBean.setNgaydieudong(ngaydieudong);
			
			String nguoidieudong = util.antiSQLInspection(request.getParameter("nguoidieudong"));
			if (nguoidieudong == null)
				nguoidieudong = "";				
			lsxBean.setNguoidieudong(nguoidieudong);
			
			String veviec = util.antiSQLInspection(request.getParameter("veviec"));
			if (veviec == null)
				veviec = "";				
			lsxBean.setVeviec(veviec);
			
			String nguoivanchuyen = util.antiSQLInspection(request.getParameter("nguoivanchuyen"));
			if (nguoivanchuyen == null)
				nguoivanchuyen = "";				
			lsxBean.setNguoivanchuyen(nguoivanchuyen);
			
			String phuongtien = util.antiSQLInspection(request.getParameter("phuongtien"));
			if (phuongtien == null)
				phuongtien = "";				
			lsxBean.setPhuongtien(phuongtien);
			
			String hopdong = util.antiSQLInspection(request.getParameter("hopdong"));
			if (hopdong == null)
				hopdong = "";				
			lsxBean.setHopdong(hopdong);
			
			String tsddId = util.antiSQLInspection(request.getParameter("tsddId"));
			if (tsddId == null)
				tsddId = "";				
			lsxBean.setTsddId(tsddId);
			
			String[] idsp = request.getParameterValues("idsp");
			String[] masp = request.getParameterValues("masp");
			String[] tensp = request.getParameterValues("tensp");
			String[] dvt = request.getParameterValues("donvi");
			String[] tonkho = request.getParameterValues("tonhientai");
			String[] soluongyeucau = request.getParameterValues("soluongyeucau"); 
			String[] soluongNhan = request.getParameterValues("soluongnhan"); 
			String[] isreload = request.getParameterValues("isreload"); 
			NumberFormat formatter1 = new DecimalFormat("#,###,###.######");
			List<IYeucau> spList = new ArrayList<IYeucau>();
			if(masp != null)
			{	
				IYeucau yeucau = null;
				for(int m = 0; m < masp.length; m++)
				{	
					if(masp[m] != "")
					{	
						yeucau = new Yeucau();
						yeucau.setId(idsp[m]);
						yeucau.setMa(masp[m]);
						yeucau.setTen(tensp[m]);
						double soluongyeucau1=0;
						try{
							soluongyeucau1=Double.parseDouble(soluongyeucau[m].replaceAll(",",""));
						}catch(Exception err){}
						
						if(soluongyeucau!=null){
							yeucau.setSoluongYC(soluongyeucau1+"");
						}
						
						if(dvt != null){
							yeucau.setDonViTinh(dvt[m]);
						}
						if(tonkho != null){
							yeucau.setTonhientai(tonkho[m]);
						}
						 
						if(soluongNhan != null){
							yeucau.setSoluongNhan(soluongNhan[m].replaceAll(",",""));
						}
						
						List<ISpDetail> spConList = new ArrayList<ISpDetail>();
						double totalsoluongnhan=0;
						
						
						String isreload1="";
						try{
							isreload1 = isreload[m];
						}catch(Exception er){}
						if(isreload1.equals("1")) {
							
							String query="";
							dbutils db=new dbutils();
							if (khoxuatId != null && khoxuatId.trim().length() > 0)
								if(trangthaisp.equals("0")){
									query=" SELECT AVAILABLE as soluong,SOLO,NGAYHETHAN,isnull(NGAYBATDAU,'') as NGAYBATDAU , KHUVUCKHO_FK, KHO.BIN  ,KHU.TEN AS TENKHU  FROM ERP_KHOTT_SP_CHITIET KHO \n" +
										  " LEFT JOIN ERP_KHUVUCKHO KHU ON KHU.PK_SEQ=KHO.KHUVUCKHO_FK \n" +
										  " WHERE available >0 and kho.khott_fk="+khoxuatId+"  and  SANPHAM_FK="+yeucau.getId() +" order by NGAYHETHAN\n" ;
								}else{
									query=" SELECT AVAILABLE as soluong,SOLO,NGAYHETHAN,isnull(NGAYBATDAU,'') as NGAYBATDAU , KHUVUCKHO_FK, KHO.BIN  ,KHU.TEN AS TENKHU  FROM ERP_KHOTT_SP_CHITIET_TRANGTHAI KHO \n" +
										  " LEFT JOIN ERP_KHUVUCKHO KHU ON KHU.PK_SEQ=KHO.KHUVUCKHO_FK \n" +
										  " WHERE available >0 and kho.khott_fk="+khoxuatId+"  and  SANPHAM_FK="+yeucau.getId() +" AND KHO.TRANGTHAI='"+trangthaisp+"' order by NGAYHETHAN\n" ;
								}
							
							System.out.println("LAY KHO CHI \n"+query + "\n=============================================");
							
							
							ResultSet rsSpDetail = null;
							if (query.trim().length() > 0)
								rsSpDetail = db.get(query);
								
								ISpDetail spCon = null;
							 
								try {
									if (rsSpDetail != null)
									{
										while(rsSpDetail.next())
										{	
											spCon = new SpDetail();
											
										 
											spCon.setSoluongton(formatter1.format(rsSpDetail.getDouble("soluong")));
											spCon.setSolo(rsSpDetail.getString("solo"));
											
											spCon.setKhu(rsSpDetail.getString("TENKHU"));
											spCon.setKhuId(rsSpDetail.getString("KHUVUCKHO_FK"));
											spCon.setNgaybatdau(rsSpDetail.getString("NGAYBATDAU"));
										  
											
											spConList.add(spCon);
											
										}
										rsSpDetail.close();
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
								db.shutDown();
						
						}else{
							
						 
							ISpDetail spCon = null;
						 	totalsoluongnhan=0;
							String[] solo = request.getParameterValues(yeucau.getId()+"solo");
							String[] soluongton = request.getParameterValues(yeucau.getId()+"soluongton");
							String[] slxuatlo = request.getParameterValues(yeucau.getId()+"soluongxuat");
							String[] khuid = request.getParameterValues(yeucau.getId()+"khuvuc_id");
							String[] xk_id = request.getParameterValues(yeucau.getId()+"xk_id");
							String[] khuten = request.getParameterValues(yeucau.getId()+"khuvuc_ten");
							String[] ngaybatdau = request.getParameterValues(yeucau.getId()+"ngaynhapkho");
							
							if(solo!=null){
								for(int k=0;k<solo.length ;k++){
								spCon = new  SpDetail();
								 
									double soluongxuatlo=0;
									try{
									  soluongxuatlo=Double.parseDouble(slxuatlo[k].replaceAll(",", ""));
									}catch(Exception err){
									}
									if(soluongton!=null){
										spCon.setSoluongton(soluongton[k].replaceAll(",",""));
									}
									if(khuten!=null){
										spCon.setKhu(khuten[k]);
									}
									if(khuid!=null){
										spCon.setKhuId(khuid[k]);
									}else{
										spCon.setKhuId("");
									}
									spCon.setNgaybatdau(ngaybatdau[k]);
									 
									spCon.setSolo(solo[k]);
									if(xk_id!=null){
										spCon.setXk_Id(xk_id[k]);
									}
									spCon.setSoluong(soluongxuatlo+"");
									spConList.add(spCon);
									totalsoluongnhan=totalsoluongnhan+soluongxuatlo;
									 
								}
							}
						}
						yeucau.setSoluongchuyen(totalsoluongnhan+"");
						yeucau.setSpDetailList(spConList);	
						spList.add(yeucau);
						
						 
					}				
				}
			
				lsxBean.setSpList(spList);
			}	
			
			
		    String action = request.getParameter("action");
 
			if(action.equals("save"))
			{	
				if(id == null)
				{
					if(!lsxBean.createCK())
					{
						lsxBean.createRs();
	    		    	session.setAttribute("lsxBean", lsxBean);
	    				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpChuyenKhoSanXuatNew.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IErpChuyenkhoSXList obj = new ErpChuyenkhoSXList();
						obj.setUserId(userId);
						
						obj.settask(task);
						obj.setIsnhanHang(isnhanHang);
						obj.init("");
						lsxBean.DBclose();
				    	session.setAttribute("obj", obj);  	
			    		session.setAttribute("userId", userId);
			    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpChuyenKhoSanXuat.jsp");
					}
				}
				else
				{
					if(!lsxBean.updateCK())
					{
						lsxBean.createRs();
						session.setAttribute("lsxBean", lsxBean);
	    				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpChuyenKhoSanXuatUpdate.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IErpChuyenkhoSXList obj = new ErpChuyenkhoSXList();
					    obj.setUserId(userId);
					    obj.settask(task);
						obj.setIsnhanHang(isnhanHang);
					    obj.init("");
						lsxBean.DBclose();
						session.setAttribute("obj", obj);							
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpChuyenKhoSanXuat.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			}
			else
			{
				if(action.equals("nhanhang"))
				{
					if(!lsxBean.nhanHang())
					{
						lsxBean.initNhanhang();
						session.setAttribute("lsxBean", lsxBean);
	    				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpChuyenKhoSanXuatNhanHang.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						 
						IErpChuyenkhoSXList obj = new ErpChuyenkhoSXList();
						
					    obj.setUserId(userId);
					    obj.settask(task);
					    obj.setIsnhanHang("1");
						lsxBean.DBclose();
					    String trangthai1=request.getParameter("trangthai1");	
						obj.setTrangthai(trangthai1);						
						
						String tungay = util.antiSQLInspection(request.getParameter("tungay"));
						obj.setTungayTao(tungay);
						    						    
						String denngay=util.antiSQLInspection(request.getParameter("denngay"));
						obj.setDenngayTao(denngay);
						 						
						String sophieu=util.antiSQLInspection(request.getParameter("sophieu"));
						obj.setSophieu(sophieu);						
						
						String nguoitao=util.antiSQLInspection(request.getParameter("nguoitao"));
						obj.setNguoitao(nguoitao);
						
						String nguoisua=util.antiSQLInspection(request.getParameter("nguoisua"));
						obj.setNguoisua(nguoisua);
						
						 
						
					/*	String search =     " select ISNULL(YEUCAUCHUYENKHO_FK,0) AS YEUCAUCHUYENKHOID ,a.PK_SEQ, a.trangthai, a.ngaychuyen, a.noidungxuat_fk as ndxId, b.ma + ', ' + b.ten as noidungxuat, a.lydo, NV.TEN as nguoitao, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua   " +
											" from ERP_CHUYENKHO a inner join ERP_NOIDUNGNHAP b on a.noidungxuat_fk = b.pk_seq  " +
											" inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
											" inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ " +
											" where a.pk_seq > 0  ";
						*/
						String search=  " select  isnull(Yeucauchuyenkho_fk,0) as yeucauchuyenkhoid , a.PK_SEQ, a.trangthai, isnull(KHOTT.TEN,'') as khonhan, a.khonhan_fk, a.ngaychuyen, a.noidungxuat_fk as ndxId, b.ma + ', ' + b.ten as noidungxuat, a.lydo, NV.TEN as nguoitao, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua,   " +
										" isnull(is_doiquycach, 0) as is_doiquycach, isnull((select  cast(SOLENHSANXUAT as varchar) as solenhSX from ERP_NHAPKHO   where PK_SEQ=a.nhapkho_fk), '') as solenhsx,    \n"+
										" isnull((select lsx.NGAYBATDAU  \n"+
										" from ERP_NHAPKHO nk  \n"+
										" inner join ERP_LENHSANXUAT_GIAY lsx on nk.SOLENHSANXUAT= lsx.PK_SEQ and nk.pk_seq=a.nhapkho_fk ), '') as ngaysanxuat \n"+
										
										" from ERP_CHUYENKHO a inner join ERP_NOIDUNGNHAP b on a.noidungxuat_fk = b.pk_seq  " +
										" left join ERP_KHOTT KHOTT on a.khonhan_fk = KHOTT.PK_SEQ   " +
										" inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
										" inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ where a.pk_seq > 0 ";
						
						if(tungay.length() > 0)
							search += " and a.ngaychuyen >= '" + tungay + "'";
						
						if(denngay.length() > 0)
							search += " and a.ngaychuyen <= '" + denngay + "'";
					
						if(trangthai1.length() > 0)
							search += " and a.TrangThai = '" + trangthai1 + "'";
						
						if(sophieu.length() > 0){
							search += " and  cast( a.pk_seq as nvarchar(10))  like '%" + sophieu + "%'";
						}
						
						if(nguoitao.length() > 0){
							search += " and dbo.ftBoDau(NV.PK_SEQ) like N'%" +util.replaceAEIOU(nguoitao)+"%'";
						}
						if(nguoisua.length() > 0){
							search += " and dbo.ftBoDau(NV2.PK_SEQ) like N'%" +util.replaceAEIOU(nguoisua)+"%'";
						}
					    obj.init(search);
						session.setAttribute("obj", obj);							
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpChuyenKho_NhanHangList.jsp";
						response.sendRedirect(nextJSP);
					}
				}
				else
				{
					if(action.equals("xuathang"))
					{
						if(!lsxBean.xuatHang())
						{
							lsxBean.initXuathang();
							session.setAttribute("lsxBean", lsxBean);
		    				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpChuyenKhoSanXuatXuatHang.jsp";
		    				response.sendRedirect(nextJSP);
						}
						else
						{
							IErpChuyenkhoSXList obj = new ErpChuyenkhoSXList();
						    obj.setUserId(userId);
						    obj.settask(task);
						    obj.setIsnhanHang("2");
						    obj.init("");
							lsxBean.DBclose();
							session.setAttribute("obj", obj);							
							String nextJSP = "/TraphacoHYERP/pages/Erp/ErpChuyenKho_XuatHangList.jsp";
							response.sendRedirect(nextJSP);
						}
					}
					else
					{
						//Change Kho
						if(action.equals("changeKHO"))
						{
							lsxBean.setSpList(new ArrayList<IYeucau>());
						}
						
						session.setAttribute("khochuyenIds", khoxuatId);
						session.setAttribute("nccchuyenId", nccchuyenId);
						session.setAttribute("trangthaisp", trangthaisp);
						
						lsxBean.createRs();
						
						session.setAttribute("lsxBean", lsxBean);
						
						String nextJSP = "";
						
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpChuyenKhoSanXuatNew.jsp";
						if(id != null)
							nextJSP = "/TraphacoHYERP/pages/Erp/ErpChuyenKhoSanXuatUpdate.jsp";
						
						response.sendRedirect(nextJSP);
					}
				}
			}
		//}
	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	private Image getLogoImage() {
		String[] imageSources = {
			"C:\\Program Files\\Apache Software Foundation\\Tomcat 7.0\\webapps\\TraphacoERP\\pages\\images\\logoNewToYo.png",
			"C:\\Program Files (x86)\\Apache Software Foundation\\Tomcat 7.0\\webapps\\TraphacoERP\\pages\\images\\logoNewToYo.png",
			"D:\\project\\TraphacoERP\\TraphacoERP\\WebContent\\pages\\images\\logoNewToYo.png"
		}; 
		Image logoImage = null;
		
		for(int i = 0; i < imageSources.length; i++) {
			try {
				if(logoImage == null) {
					logoImage = Image.getInstance(imageSources[i]);
					System.out.println("[ErpChuyenkhoSXUpdateSvl.CreatePdf_KhoVatTu] imgSrc = " + imageSources[i]);
					break;
				}
			} catch (Exception e) {	
				System.out.println("[ErpChuyenkhoSXUpdateSvl.CreatePdf_KhoVatTu] error message = " + e.getMessage());
			}
		}
		return logoImage;
	}


	private void CreatePdf_KhoVatTu(ServletOutputStream outstream, IErpChuyenkhoSX lsxBean) throws IOException 
	{
		// Spide-Part
		Rectangle pageSize = new Rectangle(PageSize.A4.getWidth(), PageSize.A4.getHeight()/2);
		float PAGE_LEFT = 1.0f*CM, PAGE_RIGHT = 1.0f*CM, PAGE_TOP = 1.0f*CM, PAGE_BOTTOM = 0.0f*CM; //cm
		Document document = new Document(pageSize, PAGE_LEFT, PAGE_RIGHT, PAGE_TOP, PAGE_BOTTOM);
		
		int currentSpId = 0;
		int currentPage = 0;
		int stt = 1;
		int numSps = lsxBean.getSpList().size();
		int NUM_SANPHAM_PER_PAGE = 10;
		
		System.out.println("[ErpChuyenkhoSXUpdateSvl.CreatePdf_KhoVatTu] numSps " + numSps + "...");
		
		
		
		try
		{
			NumberFormat formatter = new DecimalFormat("#,###,###.##"); 
			
			PdfWriter.getInstance(document, outstream);
			document.open();

			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 10, Font.NORMAL);
			Font fontbold = new Font(bf, 11, Font.BOLD);
			Font fontHeader1 = new Font(bf, 16, Font.BOLD);
//			Font fontHeader2 = new Font(bf, 18, Font.BOLD);
			Font fontHeader3 = new Font(bf, 14, Font.BOLD);
			
			//SIZE 18
			float[] TABLE_HEADER_WIDTHS = {2.0f * CM, 12.0f * CM, 5.0f * CM};
			float[] TABLE_SANPHAM_WIDTHS = {1.0f * CM, 3.0f * CM, 5.0f * CM, 4.0f * CM, 1.5f * CM, 1.5f * CM, 1.5f * CM, 1.5f * CM, };
			float[] TABLE_FOOTER_WIDTHS = {4.5f * CM, 4.5f * CM, 4.5f * CM, 4.5f * CM};
			
			//Align
			int[] TABLE_SANPHAM_ALIGNS = {Element.ALIGN_CENTER, Element.ALIGN_LEFT, Element.ALIGN_LEFT, Element.ALIGN_LEFT, Element.ALIGN_CENTER, Element.ALIGN_CENTER, Element.ALIGN_LEFT, Element.ALIGN_LEFT};
			
			//Ve cac trang pdf
			do {
				currentPage++;
				System.out.println("[ErpChuyenkhoSXUpdateSvl.CreatePdf_KhoVatTu] Draw page " + currentPage + "...");
			
				//VẼ khung header (Logo Picture | Header Titles)
				PdfPTable headerTable = new PdfPTable(TABLE_HEADER_WIDTHS.length);
				headerTable.setWidths(TABLE_HEADER_WIDTHS);
				headerTable.setWidthPercentage(100);
				headerTable.getDefaultCell().setBorder(0);
				
				PdfPCell cell;
				
				Image logoImage = getLogoImage();
				if(logoImage != null) {
					System.out.println("[ErpPhieuxuatkhoPdfSvl.CreatePxk] Load Images Logo Thanh Cong....");
					logoImage.setBorder(0);
					logoImage.setAlignment(Element.ALIGN_CENTER);
					logoImage.scaleToFit(TABLE_HEADER_WIDTHS[0], TABLE_HEADER_WIDTHS[0]);
					logoImage.setAbsolutePosition(PAGE_LEFT, pageSize.getHeight() - PAGE_RIGHT - TABLE_HEADER_WIDTHS[0]);
					
					document.add(logoImage);
				}
				
				cell = new PdfPCell(new Paragraph(" ", font));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setFixedHeight(2.8f * CM);
				cell.setRowspan(3);
				headerTable.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("CÔNG TY TNHH BAO BÌ GIẤY NHÔM NEW TOYO", fontHeader1));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setPaddingLeft(0.5f * CM);
				cell.setColspan(2);
				headerTable.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("PHIẾU YÊU CẦU XUẤT PHỤ TÙNG VẬT TƯ", fontHeader3));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				headerTable.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("Số phiếu: " + lsxBean.getId() + "\nNgày xuất: " + lsxBean.getNgayyeucau(), fontbold));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setPaddingLeft(0.0f * CM);
				headerTable.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("Nội dung: " + lsxBean.getNdxId() + "\nBộ phận : " + lsxBean.getKhoNhapTen() + "           Nhà CC/GCông: " + lsxBean.getNccChuyenIds(), fontbold));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setPaddingLeft(1.0f * CM);
				cell.setColspan(2);
				headerTable.addCell(cell);
				
				document.add(headerTable);

				
				//SANPHAM
				PdfPTable sanPhamTable = new PdfPTable(TABLE_SANPHAM_WIDTHS.length);
				sanPhamTable.setWidths(TABLE_SANPHAM_WIDTHS);
				sanPhamTable.setWidthPercentage(100);
				sanPhamTable.setSpacingBefore(0.2f * CM);
				
				String[] spTitles = {"STT", "Mã vật tư", "Tên vật tư", "Đặc điểm - Quy cách", "ĐVT", "Số lượng", "Tên máy", "Ghi chú"};
				for(int i= 0; i < spTitles.length ; i++)
				{
					cell = new PdfPCell(new Paragraph(spTitles[i], fontbold));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setPaddingBottom(0.2f * CM);
					sanPhamTable.addCell(cell);			
				}
				
				PdfPCell cells = new PdfPCell();

				System.out.println("[ErpChuyenkhoSXUpdateSvl.CreatePdf_KhoVatTu] currentSpId = " + currentSpId);
				
				int currentLocalSpId = 0;
				
				while(currentSpId < numSps && currentLocalSpId < NUM_SANPHAM_PER_PAGE)
				{
					IYeucau sp = (IYeucau)lsxBean.getSpList().get(currentSpId);
					
					String[] arr = new String[] { 
						Integer.toString(stt),  
						sp.getMavattu(), 
						sp.getTen(), 
						sp.getSolo(), 
						sp.getDonViTinh(), 
						formatter.format(Float.parseFloat(sp.getSoluongYC())), 
						" ", 
						" " 
					};
					
					for(int j = 0; j < spTitles.length; j++)
					{
						cells = new PdfPCell(new Paragraph(arr[j], font));
						cells.setHorizontalAlignment(TABLE_SANPHAM_ALIGNS[j]);
						cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cells.setPadding(3.0f);
						
						sanPhamTable.addCell(cells);
					}

					currentLocalSpId++;
					currentSpId++;
					stt++;
				}
				
				document.add(sanPhamTable);
				
				//FOOTER
				PdfPTable tableFooter = new PdfPTable(TABLE_FOOTER_WIDTHS.length);
				tableFooter.setWidthPercentage(100);
				tableFooter.setHorizontalAlignment(Element.ALIGN_CENTER);
				tableFooter.setWidths(TABLE_FOOTER_WIDTHS);
				tableFooter.setSpacingBefore(0.2f * CM);
				
				//String[] footers = new String[] { "Thủ kho", "GĐ. Kỹ thuật", "Người nhận", "Nơi thay thế" };
				String[] footers = new String[] { "Thủ kho", "Trưởng bộ phận đề nghị", "Người đề nghị", "" };
				
				for(int j = 0; j < footers.length; j++) {
					cell = new PdfPCell(new Paragraph(footers[j], fontbold));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBorder(0);
					tableFooter.addCell(cell);
				}
				document.add(tableFooter);
				
				//Them trang moi neu con san pham chua in
				if(currentSpId < numSps) {
					document.newPage();
				}
			} while(currentSpId < numSps);

			document.close();
			
			lsxBean.DBclose();
		}
		catch(DocumentException e)
		{
			e.printStackTrace();
		}
	} 

	private void CreatePdf_GiaCong(ServletOutputStream outstream, IErpChuyenkhoSX lsxBean) throws IOException 
	{
		Rectangle pageSize = new Rectangle(PageSize.A3.getWidth(), PageSize.A3.getHeight()/2);
		float PAGE_LEFT = 2.0f*CM, PAGE_RIGHT = 1.5f*CM, PAGE_TOP = 1.0f*CM, PAGE_BOTTOM = 0.0f*CM; //cm
		Document document = new Document(pageSize, PAGE_LEFT, PAGE_RIGHT, PAGE_TOP, PAGE_BOTTOM);
		float imageWidth = 2.0f*CM;
		
		int currentSpId = 0;
		int currentPage = 0;
		int stt = 1;
		int numSps = lsxBean.getSpList().size();
		int NUM_SANPHAM_PER_PAGE = 7;
		
		long numPages = Math.round(Math.ceil((double)numSps/NUM_SANPHAM_PER_PAGE)) + 1;
		
		try
		{			
			NumberFormat formatter = new DecimalFormat("#,###,###"); 
			
			PdfWriter.getInstance(document, outstream);
			document.open();

			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 12, Font.NORMAL);
			Font fontbold = new Font(bf, 12, Font.BOLD);
			Font fontHeader = new Font(bf, 24, Font.BOLD);
			
			//SIZE 29
			float[] TABLE_HEADER_WIDTHS = {3.0f * CM, 20.0f * CM, 6.0f * CM};
			float[] TABLE_MIDDLE_WIDTHS = {23.0f * CM, 6.0f * CM};
			float[] TABLE_SANPHAM_WIDTHS = {2.0f * CM, 5.0f * CM, 7.0f * CM, 5.0f * CM, 2.5f * CM, 2.5f * CM, 2.5f * CM, 2.5f * CM, };
			float[] TABLE_FOOTER_WIDTHS = {7.0f * CM, 7.5f * CM, 7.0f * CM, 7.5f * CM, };

			//Align
			int[] TABLE_SANPHAM_ALIGNS = {Element.ALIGN_CENTER, Element.ALIGN_LEFT, Element.ALIGN_LEFT, Element.ALIGN_LEFT, Element.ALIGN_CENTER, Element.ALIGN_CENTER, Element.ALIGN_LEFT, Element.ALIGN_LEFT};
			
			//Ve cac trang pdf
			do {
				currentPage++;
				System.out.println("[ErpChuyenkhoSXUpdateSvl.CreatePdf_KhoVatTu] Draw page " + currentPage + "...");
			
				//VẼ khung header (Logo Picture | Header Titles)
				PdfPTable headerTable = new PdfPTable(TABLE_HEADER_WIDTHS.length);
				headerTable.setWidths(TABLE_HEADER_WIDTHS);
				headerTable.setWidthPercentage(100);
				
				PdfPCell cell;
				
				Image logoImage = getLogoImage();
				if(logoImage != null) {
					logoImage.setBorder(0);
					logoImage.setAlignment(Element.ALIGN_CENTER);
					logoImage.scaleToFit(imageWidth, imageWidth);
					float padding = (TABLE_HEADER_WIDTHS[0] - imageWidth)/2;
					logoImage.setAbsolutePosition(PAGE_LEFT + padding, pageSize.getHeight() - PAGE_TOP - imageWidth - padding);
					
					document.add(logoImage);
				}
				
				cell = new PdfPCell(new Paragraph(" ", font));
				cell.setFixedHeight(TABLE_HEADER_WIDTHS[0]);
				headerTable.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("PHIẾU YÊU CẦU XUẤT VẬT TƯ", fontHeader));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setRowspan(2);
				headerTable.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("Loại: Biểu mẫu\nMã số: BM-CNKT-007\nSoát xét: 02\nĐiều chỉnh: 00\nSố trang: " + currentPage + "/" + numPages, font));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setPaddingLeft(0.5f * CM);
				cell.setRowspan(2);
				headerTable.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("MPP: ", font));
				cell.setVerticalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingLeft(0.2f * CM);
				headerTable.addCell(cell);
				
				document.add(headerTable);
				
				
				//MIDDLE
				PdfPTable middleTable = new PdfPTable(TABLE_MIDDLE_WIDTHS.length);
				middleTable.setWidths(TABLE_MIDDLE_WIDTHS);
				middleTable.setWidthPercentage(100);
				middleTable.setSpacingBefore(0.5f * CM);
				
				cell = new PdfPCell(new Paragraph("Nội dung: " + lsxBean.getNdxId() + "\nBộ phận : " + lsxBean.getKhoNhapTen() + "\nNhà cung cấp/Gia công: " + lsxBean.getNccChuyenIds(), font));
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				cell.setBorder(0);
				cell.setPaddingLeft(1.0f*CM);
				middleTable.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("Số phiếu: " + lsxBean.getId() + "\nNgày: " + lsxBean.getNgayyeucau(), font));
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				cell.setBorder(0);
				middleTable.addCell(cell);
	
				document.add(middleTable);
				
				
				//SANPHAM
				PdfPTable sanPhamTable = new PdfPTable(TABLE_SANPHAM_WIDTHS.length);
				sanPhamTable.setWidths(TABLE_SANPHAM_WIDTHS);
				sanPhamTable.setWidthPercentage(100);
				sanPhamTable.setSpacingBefore(0.2f * CM);
				
				String[] spTitles = {"STT", "Mã vật tư", "Tên vật tư", "Đặc điểm - Quy cách", "ĐVT", "Số lượng", "Tên máy", "Ghi chú"};
				for(int i= 0; i < spTitles.length ; i++)
				{
					cell = new PdfPCell(new Paragraph(spTitles[i], fontbold));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setPaddingBottom(0.2f * CM);
					sanPhamTable.addCell(cell);			
				}
				
				PdfPCell cells = new PdfPCell();
	
				System.out.println("[ErpChuyenkhoSXUpdateSvl.CreatePdf_KhoVatTu] currentSpId = " + currentSpId);
				
				int currentLocalSpId = 0;
				
				while(currentSpId < numSps && currentLocalSpId < NUM_SANPHAM_PER_PAGE)
				{
					IYeucau sp = (IYeucau)lsxBean.getSpList().get(currentSpId);
					
					String[] arr = new String[] { 
						Integer.toString(stt),  
						sp.getMa(), 
						sp.getTen(), 
						sp.getSolo(), 
						sp.getDonViTinh(), 
						formatter.format(Float.parseFloat(sp.getSoluongYC())), 
						" ", 
						" " 
					};
					
					for(int j = 0; j < spTitles.length; j++)
					{
						cells = new PdfPCell(new Paragraph(arr[j], font));
						cells.setHorizontalAlignment(TABLE_SANPHAM_ALIGNS[j]);
						cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cells.setPadding(3.0f);
						
						sanPhamTable.addCell(cells);
					}
	
					currentLocalSpId++;
					currentSpId++;
					stt++;
				}
				
				document.add(sanPhamTable);
				
				
				//FOOTER
				PdfPTable footerTable = new PdfPTable(TABLE_FOOTER_WIDTHS.length);
				footerTable.setWidths(TABLE_FOOTER_WIDTHS);
				footerTable.setWidthPercentage(100);
				footerTable.setSpacingBefore(1.0f*CM);
				
				cell = new PdfPCell(new Paragraph("Thủ kho", fontbold));
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(0);
				footerTable.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("Giám đốc sản xuất", fontbold));
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(0);
				footerTable.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("Người nhận", fontbold));
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(0);
				footerTable.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("Nơi thay thế", fontbold));
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(0);
				footerTable.addCell(cell);
	
				document.add(footerTable);
			
				//Them trang moi neu con san pham chua in
				if(currentSpId < numSps) {
					document.newPage();
				}
			} while(currentSpId < numSps);
			
			
			//VẼ TRANG CUỐI
			document.newPage();
			
			//VẼ khung header (Logo Picture | Header Titles)
			PdfPTable headerTable = new PdfPTable(TABLE_HEADER_WIDTHS.length);
			headerTable.setWidths(TABLE_HEADER_WIDTHS);
			headerTable.setWidthPercentage(100);
			
			PdfPCell cell;
			
			Image logoImage = getLogoImage();
			if(logoImage != null) {
				logoImage.setBorder(0);
				logoImage.setAlignment(Element.ALIGN_CENTER);
				logoImage.scaleToFit(imageWidth, imageWidth);
				float padding = (TABLE_HEADER_WIDTHS[0] - imageWidth)/2;
				logoImage.setAbsolutePosition(PAGE_LEFT + padding, pageSize.getHeight() - PAGE_TOP - imageWidth - padding);
				
				document.add(logoImage);
			}
			
			cell = new PdfPCell(new Paragraph(" ", font));
			cell.setFixedHeight(TABLE_HEADER_WIDTHS[0]);
			headerTable.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("PHIẾU YÊU CẦU XUẤT VẬT TƯ", fontHeader));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setRowspan(2);
			headerTable.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Loại: Biểu mẫu\nMã số: BM-CNKT-007\nSoát xét: 02\nĐiều chỉnh: 00\nSố trang: " + numPages + "/" + numPages, font));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setPaddingLeft(0.5f * CM);
			cell.setRowspan(2);
			headerTable.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("MPP: ", font));
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			cell.setPaddingLeft(0.2f * CM);
			headerTable.addCell(cell);
			
			headerTable.setSpacingAfter(4.0f*CM);
			
			document.add(headerTable);
			
			
			//FOOTER
			TABLE_FOOTER_WIDTHS = new float[] {9.5f * CM, 10.0f * CM, 9.5f * CM, };
			PdfPTable footerTable = new PdfPTable(TABLE_FOOTER_WIDTHS.length);
			footerTable.setWidths(TABLE_FOOTER_WIDTHS);
			footerTable.setWidthPercentage(100);
			
			cell = new PdfPCell(new Paragraph("Giám đốc sản xuất", fontbold));
			cell.setVerticalAlignment(Element.ALIGN_TOP);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);
			footerTable.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Thủ kho", fontbold));
			cell.setVerticalAlignment(Element.ALIGN_TOP);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);
			footerTable.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Người nhận", fontbold));
			cell.setVerticalAlignment(Element.ALIGN_TOP);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);
			footerTable.addCell(cell);

			document.add(footerTable);
			
			

			document.close();
			
			lsxBean.DBclose();
		}
		/*try{
			
			//NumberFormat formatter = new DecimalFormat("#,###,###"); 
			//NumberFormat formatter2 = new DecimalFormat("#,###,##0.00000"); 
			NumberFormat formatter3 = new DecimalFormat("#,###,##0.00"); 
			
			PdfWriter.getInstance(document, outstream);
			document.open();
			//document.setPageSize(new Rectangle(210.0f, 297.0f));

			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 13, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);
			Font font9bold = new Font(bf, 9, Font.BOLD);
			Font font10bold = new Font(bf, 10, Font.BOLD);
			Font font11bold = new Font(bf, 11, Font.BOLD);
			Font font12bold = new Font(bf, 12, Font.BOLD);
			Font font13bold = new Font(bf, 13, Font.BOLD);
			Font font14bold = new Font(bf, 14, Font.BOLD);
			Font font15bold = new Font(bf, 15, Font.BOLD);
			Font font16bold = new Font(bf, 16, Font.BOLD);
			Font font9 = new Font(bf, 9, Font.NORMAL);
			Font font10 = new Font(bf, 10, Font.NORMAL);
			Font font11 = new Font(bf, 11, Font.NORMAL);
			Font font12 = new Font(bf, 12, Font.NORMAL);
			Font font13 = new Font(bf, 13, Font.NORMAL);
			Font font14 = new Font(bf, 14, Font.NORMAL);
			
			//SIZE
			float CONVERT = 28.346457f; // = 1cm
			//total width = 17.5cm
			float[] TABLE_HEADER_WIDTHS = {2.8f * CONVERT, 15.7f * CONVERT};
			float[] TABLE_MIDDLE_WIDTHS = {4.25f * CONVERT, 5.75f * CONVERT, 3.65f * CONVERT, 4.85f * CONVERT};
			float[] TABLE_SANPHAM_WIDTHS = {0.9f * CONVERT, 7.0f * CONVERT, 1.3f * CONVERT, 2.3f * CONVERT, 2.8f * CONVERT, 4.2f * CONVERT};
			float[] TABLE_FOOTER_WIDTHS = {4.3f * CONVERT, 7.8f * CONVERT, 7.4f * CONVERT};
			int SANPHAM_NUM_ROWS = 4;
			
			int numSPs = lsxBean.getSpList().size();
			int numPages = (int) Math.ceil((float)numSPs/SANPHAM_NUM_ROWS);
			System.out.println("[ErpPhieuxuatkhoPdfSvl.CreatePxk] splist.size = " + numSPs);
			System.out.println("[ErpPhieuxuatkhoPdfSvl.CreatePxk] num of pages = " + numPages);
			
			int currentSpId = 0;

			double totalTrongLuong = 0;
			double totalTheTich = 0;
			double totalSoluong=0;
			int totalthung=0;
			int totalle=0;
			String tongtrongluong = "";
			
			for(int pageIndex = 0; pageIndex < numPages; pageIndex++) {
			
				//VẼ khung header (Logo Picture | Header Titles)
				PdfPTable headerTable = new PdfPTable(2);
				headerTable.setWidths(TABLE_HEADER_WIDTHS);
				headerTable.setWidthPercentage(100);
				headerTable.getDefaultCell().setBorder(0);
				
				PdfPCell cell;
				
				String[] imageSources = {
					"C:\\Program Files\\Apache Software Foundation\\Tomcat 7.0\\webapps\\TraphacoERP\\pages\\images\\logoNewToYo.png",
					"C:\\Program Files (x86)\\Apache Software Foundation\\Tomcat 7.0\\webapps\\TraphacoERP\\pages\\images\\logoNewToYo.png",
					"D:\\project\\TraphacoERP\\TraphacoERP\\WebContent\\pages\\images\\logoNewToYo.png"
				}; 
				Image logoImage = null;
				
				for(int i = 0; i < imageSources.length; i++) {
					try {
						if(logoImage == null) {
							logoImage = Image.getInstance(imageSources[i]);
							System.out.println("[ErpPhieuxuatkhoPdfSvl.CreatePxk] imgSrc = " + imageSources[i]);
							break;
						}
					} catch (Exception e) {	}
				}
				if(logoImage != null) {
					System.out.println("[ErpPhieuxuatkhoPdfSvl.CreatePxk] Load Images Logo Thanh Cong....");
					logoImage.setBorder(Rectangle.NO_BORDER);
					logoImage.setAlignment(Element.ALIGN_CENTER);
					headerTable.addCell(logoImage);
					cell = new PdfPCell(logoImage);
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setFixedHeight(2.8f * CONVERT);
				} else {
					System.out.println("[ErpPhieuxuatkhoPdfSvl.CreatePxk] Khong load duoc hinh anh logo");
					cell = new PdfPCell(new Paragraph(" ", new Font(bf, 8, Font.NORMAL)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setFixedHeight(2.0f * CONVERT);
					headerTable.addCell(cell);
				}
				
				
				
				//Header Titles
				PdfPTable headerTitlesTable = new PdfPTable(1);
				headerTitlesTable.setWidths(new float[] {TABLE_HEADER_WIDTHS[1]});
				headerTitlesTable.setWidthPercentage(100);
				headerTitlesTable.getDefaultCell().setBorder(0);
				
				cell = new PdfPCell(new Paragraph("CÔNG TY TNHH BAO BÌ GIẤY NHÔM NEW TOYO", font13bold));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setFixedHeight(0.7f * CONVERT);
				cell.setPaddingLeft(0.7f * CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
				headerTitlesTable.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("NEW TOYO (VIETNAM) ALUMINIUM PAPER PACKAGING CO., LTD.", font11bold));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setFixedHeight(0.5f * CONVERT);
				headerTitlesTable.addCell(cell);
				
				//Empty Row
				cell = new PdfPCell(new Paragraph(" ", font10bold));
				cell.setBorder(0);
				cell.setFixedHeight(0.3f * CONVERT);
				headerTitlesTable.addCell(cell);			
				
				PdfPTable headerTitles2Table = new PdfPTable(3);
				float[] headerTitles2Widths = {TABLE_HEADER_WIDTHS[1]*1.8f/3.0f, TABLE_HEADER_WIDTHS[1]*0.6f/3.0f, TABLE_HEADER_WIDTHS[1]*0.6f/3.0f};
				headerTitles2Table.setWidths(headerTitles2Widths);
				headerTitles2Table.setWidthPercentage(100);
				
				PdfPCell cell2;
				cell2 = new PdfPCell(new Paragraph("PHIẾU XUẤT KHO", font16bold));
				cell2.setBorder(Rectangle.NO_BORDER);
				cell2.setFixedHeight(0.8f * CONVERT);
				cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				headerTitles2Table.addCell(cell2);
				
				cell2 = new PdfPCell(new Paragraph("Số phiếu/Ref No", font11));			
				cell2.setBorderWidthBottom(0);
				cell2.setBorderWidthTop(1);
				cell2.setBorderWidthLeft(1);
				cell2.setBorderWidthRight(0);
				cell2.setFixedHeight(0.8f * CONVERT);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				headerTitles2Table.addCell(cell2);
				
				if(lsxBean.getDvkd().equals("100004"))
				{
					cell2 = new PdfPCell(new Paragraph(": " + lsxBean.getId() + " /XXL", font11));	
				}
				else
				{
					cell2 = new PdfPCell(new Paragraph(": " + lsxBean.getId() + " /XXN", font11));	
				}
				cell2.setBorderWidthBottom(0);
				cell2.setBorderWidthTop(1);
				cell2.setBorderWidthLeft(0);
				cell2.setBorderWidthRight(1);
				cell2.setFixedHeight(0.8f * CONVERT);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				headerTitles2Table.addCell(cell2);
				
				cell2 = new PdfPCell(new Paragraph("(GOODS ISSUED NOTES)", font12bold));	
				cell2.setFixedHeight(0.6f * CONVERT);
				cell2.setBorder(Rectangle.NO_BORDER);
				cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				headerTitles2Table.addCell(cell2);
				
				cell2 = new PdfPCell(new Paragraph("Ngày/Date", font11));	
				cell2.setBorderWidthBottom(1);
				cell2.setBorderWidthTop(0);
				cell2.setBorderWidthLeft(1);
				cell2.setBorderWidthRight(0);
				cell2.setFixedHeight(0.6f * CONVERT);
				cell2.setVerticalAlignment(Element.ALIGN_TOP);
				headerTitles2Table.addCell(cell2);
				
				cell2 = new PdfPCell(new Paragraph(": " + getVnDateTime(lsxBean.getNgayxuatkho(),"/"), font11));	
				cell2.setBorderWidthBottom(1);
				cell2.setBorderWidthTop(0);
				cell2.setBorderWidthLeft(0);
				cell2.setBorderWidthRight(1);
				cell2.setFixedHeight(0.6f * CONVERT);
				cell2.setVerticalAlignment(Element.ALIGN_TOP);
				headerTitles2Table.addCell(cell2);
				
				headerTitlesTable.addCell(headerTitles2Table);			
				headerTable.addCell(headerTitlesTable);
	
				document.add(headerTable);
				
				
				//MIDDLE
				PdfPTable middleTable = new PdfPTable(TABLE_MIDDLE_WIDTHS.length);
				middleTable.setWidths(TABLE_MIDDLE_WIDTHS);
				middleTable.setWidthPercentage(100);
				
				cell = new PdfPCell(new Paragraph("Tên người nhận/Receiver", font11));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setBorderWidthLeft(1);
				cell.setBorderWidthTop(1);
				//cell.setFixedHeight(0.6f * CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				cell.setPaddingLeft(0.1f * CONVERT);
				middleTable.addCell(cell);
				
				cell = new PdfPCell(new Paragraph(":", font10bold)); //------------- TÊN NGƯỜI NHẬN--------------//
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setBorderWidthTop(1);
				//cell.setFixedHeight(0.6f * CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				//cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPaddingLeft(0.1f * CONVERT);
				middleTable.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("Bộ phận/Department", font11));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setBorderWidthTop(1);
				//cell.setFixedHeight(0.6f * CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				cell.setPaddingLeft(0.1f * CONVERT);
				middleTable.addCell(cell);
				
				cell = new PdfPCell(new Paragraph(": ", font10)); //Bộ phận (ko có) //---------BỘ PHẬN -----------//
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setBorderWidthTop(1);
				cell.setBorderWidthRight(1);
				//cell.setFixedHeight(0.6f * CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				cell.setPaddingLeft(0.1f * CONVERT);
				middleTable.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("Lý do xuất/For reason", font11));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setBorderWidthLeft(1);
				cell.setBorderWidthBottom(1);
				//cell.setFixedHeight(0.6f * CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				cell.setPaddingLeft(0.1f * CONVERT);
				middleTable.addCell(cell);
				
				cell = new PdfPCell(new Paragraph(lsxBean.getNdxId(), font11bold)); //---------  LÝ DO --------------------//
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setBorderWidthBottom(1);
				//cell.setFixedHeight(0.6f * CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				//cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPaddingLeft(0.1f * CONVERT);
				middleTable.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("Xuất tại kho/Issued at", font11));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setBorderWidthBottom(1);
				//cell.setFixedHeight(0.6f * CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				cell.setPaddingLeft(0.1f * CONVERT);
				middleTable.addCell(cell);
				
				cell = new PdfPCell(new Paragraph(": " + lsxBean.getXuattaikho(), font10)); //--------- LẤY TỪ "KHO CHUYỂN"---------//
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setBorderWidthBottom(1);
				cell.setBorderWidthRight(1);
				//cell.setFixedHeight(0.6f * CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				cell.setPaddingLeft(0.1f * CONVERT);
				middleTable.addCell(cell);
				
				document.add(middleTable);
				
				//SANPHAM--------------NHƯ <TH>--------------/
				
				
				PdfPTable sanPhamTable = new PdfPTable(TABLE_SANPHAM_WIDTHS.length);
				sanPhamTable.setWidths(TABLE_SANPHAM_WIDTHS);
				sanPhamTable.setWidthPercentage(100);
				sanPhamTable.setSpacingBefore(0.2f * CONVERT);
				
				String[] spTitles = {"STT\nNo", "Tên nhãn hiệu & quy cách\nName of material & size", "ĐVT\nUnit", "Số lượng\nQuantity", "Trọng lượng\nWeight", "Ghi chú\nRemarks"};			
				for(int i= 0; i < spTitles.length ; i++)
				{
					cell = new PdfPCell(new Paragraph(spTitles[i], font12bold));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setPaddingBottom(0.2f * CONVERT);
					sanPhamTable.addCell(cell);			
				}
				
				PdfPCell cells = new PdfPCell();
				
				
				//int maxLocalNumSp = numSPs - SANPHAM_NUM_ROWS >= 0 ? SANPHAM_NUM_ROWS : numSPs - SANPHAM_NUM_ROWS*pageIndex;
				int maxLocalNumSp = numSPs - currentSpId >= SANPHAM_NUM_ROWS ? SANPHAM_NUM_ROWS : numSPs - currentSpId;
				System.out.println("[ErpPhieuxuatkhoPdfSvl.CreatePxk] maxLocalNumSp = " + maxLocalNumSp);
				
				//---------------NHƯ <TD>--------------------------//
				
				for(int i = 0; i < maxLocalNumSp; i++)
				{
					if(i < pxkBean.getSpList().size())
					{
						ISanpham sp = (ISanpham)lsxBean.getSpList().get(currentSpId);
						
						double trongluong = Float.parseFloat(sp.getTrongluong().trim());
						//double khoiluong = Float.parseFloat(sp.getTrongluong().trim()) * Float.parseFloat(sp.getSoluong());
						//double tt = Float.parseFloat( sp.getThetich().trim() ) * Float.parseFloat(sp.getSoluong());
						//double tt = Integer.parseInt(sp.getSoluong());
						
						//String ghichu = currentSpId == 0 ? pxkBean.getGhichu() : " ";
						String tl = ""; // Nhom: trong luong kg ko in ra
						if(!sp.getDVT().equals("Kg"))
						{
							tl = formatter3.format((trongluong));
						}
						
						String[] arr = new String[]{
							Integer.toString(currentSpId+1),//STT 
							sp.getDiengiai(), //San pham
							sp.getDVT(), //Don vi tinh
							formatter3.format(Float.parseFloat(sp.getSoluong())), // So luong 
							tl,  //Trong luong
							sp.getGhiChu() //Ghi chu
						};
						
						totalSoluong += Float.parseFloat(formatter3.format(Float.parseFloat(sp.getSoluong())).replace(",",""));
						
						if(sp.getThung().length() > 0)
							totalthung += Float.parseFloat(sp.getThung());
						if(sp.getLe().length() > 0)
							totalle += Float.parseFloat(sp.getLe());
						
						if(sp.getTrongluong().length() > 0 && !sp.getDVT().equals("Kg"))
							totalTrongLuong += Double.parseDouble(formatter3.format(trongluong).replace(",",""));
						if(!sp.getDVT().equals("Kg"))
						{
							tongtrongluong = formatter3.format(totalTrongLuong);
						}
						if(sp.getThetich().length() > 0)
							totalTheTich += Float.parseFloat(sp.getThetich()) * Float.parseFloat(sp.getSoluong());
						
						for(int j = 0; j < spTitles.length; j++)
						{
							cells = new PdfPCell(new Paragraph(arr[j], font11));
							if(j == 1)
								cells.setHorizontalAlignment(Element.ALIGN_LEFT);
							else
							{
								cells.setHorizontalAlignment(Element.ALIGN_CENTER);
								if( j >= 5)
									cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
							}
							cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cells.setPadding(3.0f);
							
							sanPhamTable.addCell(cells);
						}
						currentSpId++;
					} else {
						for(int j = 0; j < 6; j++)
						{
							cells = new PdfPCell(new Paragraph(" ", new Font(bf, 8, Font.NORMAL)));
							cells.setPadding(3.0f);
							cells.setFixedHeight(0.4f*CONVERT);
							
							sanPhamTable.addCell(cells);
						}
					}
				}	
				
				if(pageIndex == numPages-1) {
					//TOTAL
					String[] arr = new String[]{"", "Tổng cộng/Total", "", formatter3.format(totalSoluong), tongtrongluong, "" };
					for(int j = 0; j < arr.length; j++)
					{
						cells = new PdfPCell(new Paragraph(arr[j], font11bold));
						cells.setHorizontalAlignment(Element.ALIGN_CENTER);
						cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cells.setFixedHeight(0.6f * CONVERT);
						//cells.setPaddingRight(0.5f * CONVERT);
						
						sanPhamTable.addCell(cells);
					}
				}
				
				document.add(sanPhamTable);
				
				//Table Footer			
				PdfPTable tableFooter = new PdfPTable(TABLE_FOOTER_WIDTHS.length);
				tableFooter.setWidthPercentage(100);
				tableFooter.setHorizontalAlignment(Element.ALIGN_CENTER);
				tableFooter.setWidths(TABLE_FOOTER_WIDTHS);
				tableFooter.setSpacingBefore(0.2f * CONVERT);
				
				PdfPCell cell11 = new PdfPCell(new Paragraph("Người nhận hàng", font12));
				cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
				PdfPCell cell12 = new PdfPCell(new Paragraph("Thủ kho", font12));
				cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
				PdfPCell cell13 = new PdfPCell(new Paragraph("Thủ trưởng đơn vị", font12));
				cell13.setHorizontalAlignment(Element.ALIGN_CENTER);
				
				cell11.setBorder(0);
				cell12.setBorder(0);
				cell13.setBorder(0);
				
				tableFooter.addCell(cell11);
				tableFooter.addCell(cell12);
				tableFooter.addCell(cell13);
				
				cell11 = new PdfPCell(new Paragraph("(Receive by)", font11));
				cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell12 = new PdfPCell(new Paragraph("(Store Keeper)", font11));
				cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell13 = new PdfPCell(new Paragraph("(Management Board)", font11));
				cell13.setHorizontalAlignment(Element.ALIGN_CENTER);
				
				cell11.setBorder(0);
				cell12.setBorder(0);
				cell13.setBorder(0);
				
				tableFooter.addCell(cell11);
				tableFooter.addCell(cell12);
				tableFooter.addCell(cell13);
				
				document.add(tableFooter);
				
				//Them trang moi neu con san pham chua in
				if(pageIndex < numPages-1) {
					document.newPage();
				}
			
			}
			
			document.close(); 
			
		 
		
			
		}*/
		catch(DocumentException e)
		{
			e.printStackTrace();
		}
	}

	private void CreatePdf_VatTu(ServletOutputStream outstream, IErpChuyenkhoSX lsxBean) throws IOException 
	{
		Rectangle pageSize = new Rectangle(PageSize.A3.getWidth(), PageSize.A3.getHeight()/2);
		float PAGE_LEFT = 2.0f*CM, PAGE_RIGHT = 1.5f*CM, PAGE_TOP = 1.0f*CM, PAGE_BOTTOM = 0.0f*CM; //cm
		Document document = new Document(pageSize, PAGE_LEFT, PAGE_RIGHT, PAGE_TOP, PAGE_BOTTOM);
		float imageWidth = 2.0f*CM;
		
		int currentSpId = 0;
		int currentPage = 0;
		int numSps = lsxBean.getSpList().size();
		int NUM_SANPHAM_PER_PAGE = 3;
		int NUM_SPCT_PER_PAGE = 10;
		
		//Phụ liệu
		int numSpPl = lsxBean.getSpChoNhapList().size();
		int NUM_SANPHAMPL_PER_PAGE = 10;
		
		long numPages = Math.round(Math.ceil((double)numSps/NUM_SANPHAM_PER_PAGE)) + Math.round(Math.ceil((double)numSpPl/NUM_SANPHAMPL_PER_PAGE));		
		
		try
		{
			NumberFormat formatter = new DecimalFormat("#,###,###.###"); 
			
			PdfWriter.getInstance(document, outstream);
			document.open();

			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font fontHeader = new Font(bf, 24, Font.BOLD);
			Font font = new Font(bf, 12, Font.NORMAL);
			Font fontbold = new Font(bf, 12, Font.BOLD);
			Font fontSmall = new Font(bf, 10, Font.NORMAL);
			Font fontSmallBold = new Font(bf, 10, Font.BOLD);
			
			//SIZE 27
			float[] TABLE_HEADER_WIDTHS = {3.0f * CM, 18.0f * CM, 6.0f * CM};
			float[] TABLE_MIDDLE_WIDTHS = {6.0f * CM, 15.0f * CM, 6.0f * CM };
			float[] TABLE_SANPHAMMALON_WIDTHS = {3.0f * CM, 17.0f * CM, 5.0f * CM, 2.0f * CM, };
			float[] TABLE_SANPHAMMACT_WIDTHS = {9.0f * CM, 9.0f * CM, 9.0f * CM, };
			float[] TABLE_SANPHAM_WIDTHS = {1.0f * CM, 4.5f * CM, 2.2f * CM, 1.3f * CM, };
			float[] TABLE_FOOTER_WIDTHS = {7.0f * CM, 7.5f * CM, 7.0f * CM, 7.5f * CM, };
			

			//Align
			int[] TABLE_SANPHAMMALON_ALIGNS = {Element.ALIGN_LEFT, Element.ALIGN_LEFT, Element.ALIGN_LEFT, Element.ALIGN_RIGHT, };
			int[] TABLE_SANPHAM_ALIGNS = {Element.ALIGN_CENTER, Element.ALIGN_LEFT, Element.ALIGN_CENTER, Element.ALIGN_LEFT, };
			
			//Font
			Font[] TABLE_SANPHAMMALON_FONTS = {fontbold, font, fontbold, font, };
			
			//Ve cac trang pdf
			if(numSps > 0)
			do {
				currentPage++;
			
				//VẼ khung header (Logo Picture | Header Titles)
				PdfPTable headerTable = new PdfPTable(TABLE_HEADER_WIDTHS.length);
				headerTable.setWidths(TABLE_HEADER_WIDTHS);
				headerTable.setWidthPercentage(100);
				
				PdfPCell cell;
				
				Image logoImage = getLogoImage();
				if(logoImage != null) {
					logoImage.setBorder(0);
					logoImage.setAlignment(Element.ALIGN_CENTER);
					logoImage.scaleToFit(imageWidth, imageWidth);
					float padding = (TABLE_HEADER_WIDTHS[0] - imageWidth)/2;
					logoImage.setAbsolutePosition(PAGE_LEFT + padding, pageSize.getHeight() - PAGE_TOP - imageWidth - padding);
					
					document.add(logoImage);
				}
				
				cell = new PdfPCell(new Paragraph(" ", font));
				cell.setFixedHeight(TABLE_HEADER_WIDTHS[0]);
				headerTable.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("PHIẾU YÊU CẦU XUẤT VẬT TƯ", fontHeader));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setRowspan(2);
				headerTable.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("Loại: Biểu mẫu\nMã số: BM-PXOG-001\nSoát xét: 02\nĐiều chỉnh: 00\nSố trang: " + currentPage + "/" + numPages, font));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setPaddingLeft(0.5f * CM);
				cell.setRowspan(2);
				headerTable.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("MPP: ", font));
				cell.setVerticalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingLeft(0.2f * CM);
				headerTable.addCell(cell);
				
				document.add(headerTable);
				
				
				//MIDDLE
				PdfPTable middleTable = new PdfPTable(TABLE_MIDDLE_WIDTHS.length);
				middleTable.setWidths(TABLE_MIDDLE_WIDTHS);
				middleTable.setWidthPercentage(100);
				middleTable.setSpacingBefore(0.5f * CM);
				
				cell = new PdfPCell(new Paragraph("Ngày: " + lsxBean.getNgayyeucau() + "\nSố phiếu: " + lsxBean.getId(), font));
				cell.setBorder(0);
				middleTable.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("Bộ phận : " + lsxBean.getKhoNhapTen() + "\nĐề nghị phòng: " + lsxBean.getKhoXuatTen(), fontbold));
				cell.setBorder(0);
				middleTable.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("Số P/O: ........\nSố tờ: ...........", font));
				cell.setBorder(0);
				middleTable.addCell(cell);
	
				document.add(middleTable);
				
				//SANPHAM MÃ LỚN
				PdfPTable sanPhamTable = new PdfPTable(TABLE_SANPHAMMALON_WIDTHS.length);
				sanPhamTable.setWidths(TABLE_SANPHAMMALON_WIDTHS);
				sanPhamTable.setWidthPercentage(100);
				sanPhamTable.setSpacingBefore(0.5f * CM);

				int _currentSpId = currentSpId, currentLocalSpId = 0;
				while (_currentSpId < numSps && currentLocalSpId < NUM_SANPHAM_PER_PAGE) 
				{
					IYeucau sp = (IYeucau)lsxBean.getSpList().get(_currentSpId);
					
					double slyc = 0;
					try { slyc = Float.parseFloat(sp.getSoluongYC()); } catch(Exception e) { }
					
					String[] arr = new String[] { 
						sp.getSolo(), 
						sp.getTen(), 
						"- SỐ LƯỢNG :",  
						formatter.format(slyc),
					};
					
					for(int j = 0; j < arr.length; j++)
					{
						cell = new PdfPCell(new Paragraph(arr[j], TABLE_SANPHAMMALON_FONTS[j]));
						cell.setHorizontalAlignment(TABLE_SANPHAMMALON_ALIGNS[j]);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setPadding(3.0f);
						
						cell.setBorderWidthRight(0);
						cell.setBorderWidthLeft(0);
						
						if(j == 0) {
							cell.setBorderWidthLeft(1);
						}
						else if(j == arr.length-1) {
							cell.setBorderWidthRight(1);
						}
						
						sanPhamTable.addCell(cell);
					}
	
					currentLocalSpId++;
					_currentSpId++;
				}
				
				document.add(sanPhamTable);
				
				//3 cột SANPHAM CT
				PdfPTable sanPhamCtTable = new PdfPTable(TABLE_SANPHAMMACT_WIDTHS.length);
				sanPhamCtTable.setWidths(TABLE_SANPHAMMACT_WIDTHS);
				sanPhamCtTable.setWidthPercentage(100);
				sanPhamCtTable.setSpacingBefore(0.5f * CM);
				currentLocalSpId = 0;
				
				PdfPCell cells;
				while (currentSpId < numSps && currentLocalSpId < NUM_SANPHAM_PER_PAGE) 
				{
					IYeucau sp = (IYeucau)lsxBean.getSpList().get(currentSpId);
					
					cells = new PdfPCell();
					cells.setBorder(0);
					cells.setPaddingLeft(5.0f);
					cells.setPaddingRight(5.0f);
					
					sanPhamTable = new PdfPTable(TABLE_SANPHAM_WIDTHS.length);
					sanPhamTable.setWidths(TABLE_SANPHAM_WIDTHS);
					sanPhamTable.setWidthPercentage(100);
					
					cell = new PdfPCell(new Paragraph("CHI TIẾT " + sp.getSolo().toUpperCase(), fontbold));
					cell.setColspan(TABLE_SANPHAM_WIDTHS.length);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					sanPhamTable.addCell(cell);
					
					String[] spTitles = {"STT", "MÃ SỐ", "TRỌNG LƯỢNG", "GHI CHÚ"};
					for(int i= 0; i < spTitles.length ; i++)
					{
						cell = new PdfPCell(new Paragraph(spTitles[i], fontSmallBold));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setPaddingBottom(0.2f * CM);
						sanPhamTable.addCell(cell);			
					}
					
					double tongTrongLuong = 0; 
					int ict = 0;
					//Các ô sản phẩm
					for(;ict < sp.getSpDetailList().size(); ict++) {
						if(ict == NUM_SPCT_PER_PAGE) break;
						
						ISpDetail spDetail = sp.getSpDetailList().get(ict);
						tongTrongLuong += Double.parseDouble(spDetail.getVitri());
						spTitles = new String[] {(ict+1) + "", spDetail.getMa(), spDetail.getDvt(), ""};

						for(int z= 0; z < spTitles.length ; z++)
						{
							cell = new PdfPCell(new Paragraph(spTitles[z], fontSmall));
							cell.setHorizontalAlignment(TABLE_SANPHAM_ALIGNS[z]);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setPadding(0.1f * CM);
							sanPhamTable.addCell(cell);
						}
					}
					//Ô trống
					for(;ict < NUM_SPCT_PER_PAGE; ict++) {
						spTitles = new String[] {" ", " ", " ", " "};

						for(int z= 0; z < spTitles.length ; z++)
						{
							cell = new PdfPCell(new Paragraph(spTitles[z], fontSmall));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setPadding(0.1f * CM);
							sanPhamTable.addCell(cell);
						}
					}
					
					//Ô tổng cộng
					spTitles = new String[] {"CỘNG ", formatter.format(tongTrongLuong), " "};

					for(int z= 0; z < spTitles.length ; z++)
					{
						cell = new PdfPCell(new Paragraph(spTitles[z], fontSmallBold));
						cell.setHorizontalAlignment(TABLE_SANPHAM_ALIGNS[z+1]);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setPadding(0.1f * CM);
						if(z==0) { cell.setColspan(2); }
						sanPhamTable.addCell(cell);
					}
					
					cells.addElement(sanPhamTable);
					sanPhamCtTable.addCell(cells);
	
					currentLocalSpId++;
					currentSpId++;
				}
				
				document.add(sanPhamCtTable);
				
				
				
				//FOOTER
				PdfPTable footerTable = new PdfPTable(TABLE_FOOTER_WIDTHS.length);
				footerTable.setWidths(TABLE_FOOTER_WIDTHS);
				footerTable.setWidthPercentage(100);
				footerTable.setSpacingBefore(0.5f*CM);
				
				cell = new PdfPCell(new Paragraph("Thủ kho", fontbold));
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(0);
				footerTable.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("Giám đốc sản xuất", fontbold));
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(0);
				footerTable.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("Người nhận", fontbold));
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(0);
				footerTable.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("Nơi thay thế", fontbold));
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(0);
				footerTable.addCell(cell);
	
				document.add(footerTable);
			
				//Them trang moi neu con san pham chua in
				if(currentSpId < numSps) {
					document.newPage();
				}
			} while(currentSpId < numSps);
			
			
			
			//CÁC TRANG VỀ SẢN PHẨM PHỤ LIỆU
			if(numSps > 0) {
				document.newPage();
			}

			float[] TABLE_MIDDLEPL_WIDTHS = {6.0f*CM, 21.0f*CM, };
			float[] TABLE_SANPHAMPL_WIDTHS = {2.0f*CM, 2.0f*CM, 5.0f*CM, 5.0f*CM, 4.0f*CM, 3.0f*CM, 3.0f*CM, 3.0f*CM, };
			int[] TABLE_SANPHAMPL_ALIGNS = {Element.ALIGN_CENTER, Element.ALIGN_CENTER, Element.ALIGN_LEFT, Element.ALIGN_LEFT, Element.ALIGN_LEFT, Element.ALIGN_CENTER, Element.ALIGN_RIGHT, Element.ALIGN_LEFT, };
			
			currentSpId = 0;
			if(numSpPl > 0) 
			do {
				currentPage++;
				
				//VẼ khung header (Logo Picture | Header Titles)
				PdfPTable headerTable = new PdfPTable(TABLE_HEADER_WIDTHS.length);
				headerTable.setWidths(TABLE_HEADER_WIDTHS);
				headerTable.setWidthPercentage(100);
				
				PdfPCell cell;
				
				Image logoImage = getLogoImage();
				if(logoImage != null) {
					logoImage.setBorder(0);
					logoImage.setAlignment(Element.ALIGN_CENTER);
					logoImage.scaleToFit(imageWidth, imageWidth);
					float padding = (TABLE_HEADER_WIDTHS[0] - imageWidth)/2;
					logoImage.setAbsolutePosition(PAGE_LEFT + padding, pageSize.getHeight() - PAGE_TOP - imageWidth - padding);
					
					document.add(logoImage);
				}
				
				cell = new PdfPCell(new Paragraph(" ", font));
				cell.setFixedHeight(TABLE_HEADER_WIDTHS[0]);
				headerTable.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("PHIẾU YÊU CẦU XUẤT PHỤ LIỆU", fontHeader));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setRowspan(2);
				headerTable.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("Loại: Biểu mẫu\nMã số: BM-PXOG-002\nSoát xét: 02\nĐiều chỉnh: 00\nSố trang: " + currentPage + "/" + numPages, font));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setPaddingLeft(0.5f * CM);
				cell.setRowspan(2);
				headerTable.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("MPP: ", font));
				cell.setVerticalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingLeft(0.2f * CM);
				headerTable.addCell(cell);
				
				document.add(headerTable);
				
				//MIDDLE
				PdfPTable middleTable = new PdfPTable(TABLE_MIDDLEPL_WIDTHS.length);
				middleTable.setWidths(TABLE_MIDDLEPL_WIDTHS);
				middleTable.setWidthPercentage(100);
				middleTable.setSpacingBefore(0.5f * CM);
				
				cell = new PdfPCell(new Paragraph("Ngày: " + lsxBean.getNgayyeucau() + "\nSố phiếu: " + lsxBean.getId(), font));
				cell.setBorder(0);
				middleTable.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("Bộ phận : " + lsxBean.getKhoNhapTen() + "\nĐề nghị phòng: " + lsxBean.getKhoXuatTen(), fontbold));
				cell.setBorder(0);
				middleTable.addCell(cell);
	
				document.add(middleTable);
				
				//SANPHAM
				PdfPTable sanPhamTable = new PdfPTable(TABLE_SANPHAMPL_WIDTHS.length);
				sanPhamTable.setWidths(TABLE_SANPHAMPL_WIDTHS);
				sanPhamTable.setWidthPercentage(100);
				sanPhamTable.setSpacingBefore(0.5f * CM);
				
				String[] spTitles = {"STT", "SỐ P/O SX", "MÃ SP", "TÊN PHỤ LIỆU", "QUY CÁCH", "Đ/V TÍNH", "SỐ LƯỢNG", "GHI CHÚ"};
				for(int i= 0; i < spTitles.length ; i++)
				{
					cell = new PdfPCell(new Paragraph(spTitles[i], fontSmallBold));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setPadding(0.2f * CM);
					sanPhamTable.addCell(cell);
				}

				int currentLocalSpId = 0;
				while (currentSpId < numSpPl && currentLocalSpId < NUM_SANPHAMPL_PER_PAGE) 
				{
					IYeucau sp = (IYeucau)lsxBean.getSpChoNhapList().get(currentSpId);
					
					double slyc = 0;
					try { slyc = Float.parseFloat(sp.getSoluongYC()); } catch(Exception e) { }
					
					String[] arr = new String[] { (currentSpId+1) + "", " ", sp.getMa(), sp.getTen(), sp.getSolo(), sp.getDonViTinh(), formatter.format(slyc), " " };
					
					for(int j = 0; j < arr.length; j++)
					{
						cell = new PdfPCell(new Paragraph(arr[j], font));
						cell.setHorizontalAlignment(TABLE_SANPHAMPL_ALIGNS[j]);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setPadding(3.0f);
						
						sanPhamTable.addCell(cell);
					}
	
					currentLocalSpId++;
					currentSpId++;
				}
				//Lấp đầy bằng các ô trống
				for(int i = currentLocalSpId; i < NUM_SPCT_PER_PAGE; i++) {
					String[] arr = new String[] { " ", " ", " ", " ", " ", " ", " ", " " };
					for(int j = 0; j < arr.length; j++)
					{
						cell = new PdfPCell(new Paragraph(arr[j], font));
						cell.setPadding(3.0f);
						sanPhamTable.addCell(cell);
					}
				}
				
				document.add(sanPhamTable);
				
				
				//FOOTER
				PdfPTable footerTable = new PdfPTable(TABLE_FOOTER_WIDTHS.length);
				footerTable.setWidths(TABLE_FOOTER_WIDTHS);
				footerTable.setWidthPercentage(100);
				footerTable.setSpacingBefore(0.5f*CM);
				
				cell = new PdfPCell(new Paragraph("Giám Đốc Sản Xuất", fontbold));
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(0);
				footerTable.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("Phòng ĐHSX", fontbold));
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(0);
				footerTable.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("Thủ Kho", fontbold));
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(0);
				footerTable.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("Người Nhận", fontbold));
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(0);
				footerTable.addCell(cell);
	
				document.add(footerTable);
			
				//Them trang moi neu con san pham chua in
				if(currentSpId < numSpPl) {
					document.newPage();
				}
				
				
			} while(currentSpId < numSpPl);
			

			document.close();
			
			lsxBean.DBclose();
		}
		catch(DocumentException e)
		{
			e.printStackTrace();
		}
	}
	
	
	//------------ ÁP DỤNG CHO 2 NX XUẤT:  XUẤT TIÊU THỤ - XƯỞNG NHÔM  & XUẤT TIÊU THỤ  XƯỞNG LÕI--------------------//
	
	
	private void CreatePxk(Document document, ServletOutputStream outstream, IErpChuyenkhoSX lsxBean) throws IOException
	{
		try
		{	
			
			
			//NumberFormat formatter = new DecimalFormat("#,###,###"); 
			//NumberFormat formatter2 = new DecimalFormat("#,###,##0.00000"); 
			NumberFormat formatter3 = new DecimalFormat("#,###,##0.000"); 
			
			
			PdfWriter.getInstance(document, outstream);
			document.open();
			//document.setPageSize(new Rectangle(210.0f, 297.0f));

			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
//			Font font = new Font(bf, 13, Font.BOLD);
//			Font font2 = new Font(bf, 8, Font.BOLD);
//			Font font9bold = new Font(bf, 9, Font.BOLD);
			Font font10bold = new Font(bf, 10, Font.BOLD);
			Font font11bold = new Font(bf, 11, Font.BOLD);
			Font font12bold = new Font(bf, 12, Font.BOLD);
			Font font13bold = new Font(bf, 13, Font.BOLD);
//			Font font14bold = new Font(bf, 14, Font.BOLD);
//			Font font15bold = new Font(bf, 15, Font.BOLD);
			Font font16bold = new Font(bf, 16, Font.BOLD);
//			Font font9 = new Font(bf, 9, Font.NORMAL);
			Font font10 = new Font(bf, 10, Font.NORMAL);
			Font font11 = new Font(bf, 11, Font.NORMAL);
			Font font12 = new Font(bf, 12, Font.NORMAL);
//			Font font13 = new Font(bf, 13, Font.NORMAL);
//			Font font14 = new Font(bf, 14, Font.NORMAL);
			
			//SIZE
			float CONVERT1 = 28.346457f; // = 1cm
			//total width = 17.5cm
			float[] TABLE_HEADER_WIDTHS = {4f * CONVERT1, 14.5f * CONVERT1};
			float[] TABLE_MIDDLE_WIDTHS = {4.25f * CONVERT1, 5.75f * CONVERT1, 3.65f * CONVERT1, 4.85f * CONVERT1};
			float[] TABLE_SANPHAM_WIDTHS = {0.9f * CONVERT1, 7.0f * CONVERT1, 1.3f * CONVERT1, 2.3f * CONVERT1, 2.8f * CONVERT1, 4.2f * CONVERT1};
			float[] TABLE_FOOTER_WIDTHS = {4.3f * CONVERT1, 7.8f * CONVERT1, 7.4f * CONVERT1};
			int SANPHAM_NUM_ROWS = 4;
			
			int numSPs = lsxBean.getSPList().size();
			
			System.out.println(numSPs);
			
			int numPages = (int) Math.ceil((float)numSPs/SANPHAM_NUM_ROWS);
			/*System.out.println("[ErpPhieuxuatkhoPdfSvl.CreatePxk] splist.size = " + numSPs);
			System.out.println("[ErpPhieuxuatkhoPdfSvl.CreatePxk] num of pages = " + numPages);
*/			
			int currentSpId = 0;

//			double totalTrongLuong = 0;
//			double totalTheTich = 0;
			double totalSoluong=0;
//			int totalthung=0;
//			int totalle=0;
			String tongtrongluong = "";
			
			for(int pageIndex = 0; pageIndex < numPages; pageIndex++) {
			
				//VẼ khung header (Logo Picture | Header Titles)
				PdfPTable headerTable = new PdfPTable(2);
				headerTable.setWidths(TABLE_HEADER_WIDTHS);
				headerTable.setWidthPercentage(100);
				headerTable.getDefaultCell().setBorder(0);
				
				PdfPCell cell;
				
				String[] imageSources = { 
					"C:\\Program Files\\Apache Software Foundation\\Tomcat 7.0\\webapps\\TraphacoERP\\pages\\images\\logoNewToYo.png",
					"C:\\Program Files (x86)\\Apache Software Foundation\\Tomcat 7.0\\webapps\\TraphacoERP\\pages\\images\\logoNewToYo.png",
					"D:\\Project\\NewToYo\\TraphacoERP\\WebContent\\pages\\images\\logoNewToYo.png",
					"C:\\Program Files (x86)\\Apache Software Foundation\\Tomcat 7.0\\webapps\\TraphacoERP\\pages\\images\\logoNewToYo.png"
				}; 
				Image logoImage = null;
				
				for(int i = 0; i < imageSources.length; i++) {
					try {
						if(logoImage == null) {
							logoImage = Image.getInstance(imageSources[i]);
							System.out.println("[ErpPhieuxuatkhoPdfSvl.CreatePxk] imgSrc = " + imageSources[i]);
							break;
						}
					} catch (Exception e) {	}
				}
				if(logoImage != null) {
					System.out.println("[ErpPhieuxuatkhoPdfSvl.CreatePxk] Load Images Logo Thanh Cong....");
					logoImage.setBorder(Rectangle.NO_BORDER);
					logoImage.setAlignment(Element.ALIGN_CENTER);
					headerTable.addCell(logoImage);
					/*cell = new PdfPCell(logoImage);
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setFixedHeight(2.8f * CONVERT);*/
				} else {
					System.out.println("[ErpPhieuxuatkhoPdfSvl.CreatePxk] Khong load duoc hinh anh logo");
					cell = new PdfPCell(new Paragraph(" ", new Font(bf, 8, Font.NORMAL)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setFixedHeight(3.5f * CONVERT1);
					headerTable.addCell(cell);
				}
				
				
				
				//Header Titles
				PdfPTable headerTitlesTable = new PdfPTable(1);
				headerTitlesTable.setWidths(new float[] {TABLE_HEADER_WIDTHS[1]});
				headerTitlesTable.setWidthPercentage(100);
				headerTitlesTable.getDefaultCell().setBorder(0);
				
				cell = new PdfPCell(new Paragraph("CÔNG TY TNHH BAO BÌ GIẤY NHÔM NEW TOYO", font13bold));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setFixedHeight(0.7f * CONVERT1);
				cell.setPaddingLeft(0.7f * CONVERT1);
				cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
				headerTitlesTable.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("NEW TOYO (VIETNAM) ALUMINIUM PAPER PACKAGING CO., LTD.", font11bold));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setFixedHeight(0.5f * CONVERT1);
				headerTitlesTable.addCell(cell);
				 
				//Empty Row
				cell = new PdfPCell(new Paragraph(" ", font10bold));
				cell.setBorder(0);
				cell.setFixedHeight(0.3f * CONVERT1);
				headerTitlesTable.addCell(cell);			
				
				PdfPTable headerTitles2Table = new PdfPTable(3);
				float[] headerTitles2Widths = {TABLE_HEADER_WIDTHS[1]*1.8f/3.0f, TABLE_HEADER_WIDTHS[1]*0.6f/3.0f, TABLE_HEADER_WIDTHS[1]*0.6f/3.0f};
				headerTitles2Table.setWidths(headerTitles2Widths);
				headerTitles2Table.setWidthPercentage(100);
				
				PdfPCell cell2;
				cell2 = new PdfPCell(new Paragraph("PHIẾU XUẤT KHO", font16bold));
				cell2.setBorder(Rectangle.NO_BORDER);
				cell2.setFixedHeight(0.8f * CONVERT1);
				cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				headerTitles2Table.addCell(cell2);
				
				cell2 = new PdfPCell(new Paragraph("Số phiếu/Ref No", font11));			
				cell2.setBorderWidthBottom(0);
				cell2.setBorderWidthTop(1);
				cell2.setBorderWidthLeft(1);
				cell2.setBorderWidthRight(0);
				cell2.setFixedHeight(0.8f * CONVERT1);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				headerTitles2Table.addCell(cell2);
				
				if(lsxBean.getNdxId().equals("100009"))
				{
					cell2 = new PdfPCell(new Paragraph(": " + lsxBean.getId() + " /XXN", font11));	
				}
				else
				{
					cell2 = new PdfPCell(new Paragraph(": " + lsxBean.getId() + " /XXL", font11));	
				}
				cell2.setBorderWidthBottom(0);
				cell2.setBorderWidthTop(1);
				cell2.setBorderWidthLeft(0);
				cell2.setBorderWidthRight(1);
				cell2.setFixedHeight(0.8f * CONVERT1);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				headerTitles2Table.addCell(cell2);
				
				cell2 = new PdfPCell(new Paragraph("(GOODS ISSUED NOTES)", font12bold));	
				cell2.setFixedHeight(0.6f * CONVERT1);
				cell2.setBorder(Rectangle.NO_BORDER);
				cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				headerTitles2Table.addCell(cell2);
				
				cell2 = new PdfPCell(new Paragraph("Ngày/Date", font11));	
				cell2.setBorderWidthBottom(1);
				cell2.setBorderWidthTop(0);
				cell2.setBorderWidthLeft(1);
				cell2.setBorderWidthRight(0);
				cell2.setFixedHeight(0.6f * CONVERT1);
				cell2.setVerticalAlignment(Element.ALIGN_TOP);
				headerTitles2Table.addCell(cell2);
				
				cell2 = new PdfPCell(new Paragraph(": " + lsxBean.getNgayyeucau(), font11));	
				cell2.setBorderWidthBottom(1);
				cell2.setBorderWidthTop(0);
				cell2.setBorderWidthLeft(0);
				cell2.setBorderWidthRight(1);
				cell2.setFixedHeight(0.6f * CONVERT1);
				cell2.setVerticalAlignment(Element.ALIGN_TOP);
				headerTitles2Table.addCell(cell2);
				
				headerTitlesTable.addCell(headerTitles2Table);			
				headerTable.addCell(headerTitlesTable);
	
				document.add(headerTable);
				
				
				//MIDDLE
				PdfPTable middleTable = new PdfPTable(TABLE_MIDDLE_WIDTHS.length);
				middleTable.setWidths(TABLE_MIDDLE_WIDTHS);
				middleTable.setWidthPercentage(100);
				
				cell = new PdfPCell(new Paragraph("Tên người nhận/Receiver:", font11)); 		//------ TÊN NGƯỜI NHẬN------//
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setBorderWidthLeft(1);
				cell.setBorderWidthTop(1);
				//cell.setFixedHeight(0.6f * CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				cell.setPaddingLeft(0.1f * CONVERT1);
				middleTable.addCell(cell);
				
				cell = new PdfPCell(new Paragraph(" ", font10bold));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setBorderWidthTop(1);
				//cell.setFixedHeight(0.6f * CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				//cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPaddingLeft(0.1f * CONVERT1);
				middleTable.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("Bộ phận/Department", font11)); //---------- TÊN BỘ PHẬN ----------//
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setBorderWidthTop(1);
				//cell.setFixedHeight(0.6f * CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				cell.setPaddingLeft(0.1f * CONVERT1);
				middleTable.addCell(cell);
				
				cell = new PdfPCell(new Paragraph(": ", font10)); //Bộ phận (ko có)
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setBorderWidthTop(1);
				cell.setBorderWidthRight(1);
				//cell.setFixedHeight(0.6f * CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				cell.setPaddingLeft(0.1f * CONVERT1);
				middleTable.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("Lý do xuất/For reason: ", font11));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setBorderWidthLeft(1);
				cell.setBorderWidthBottom(1);
				//cell.setFixedHeight(0.6f * CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				cell.setPaddingLeft(0.1f * CONVERT1);
				middleTable.addCell(cell);
				
				cell = new PdfPCell(new Paragraph(": " + lsxBean.getnoiDungXuat(), font11bold));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setBorderWidthBottom(1);
				//cell.setFixedHeight(0.6f * CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				//cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPaddingLeft(0.1f * CONVERT1);
				middleTable.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("Xuất tại kho/Issued at" , font11));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setBorderWidthBottom(1);
				//cell.setFixedHeight(0.6f * CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				cell.setPaddingLeft(0.05f * CONVERT1);
				middleTable.addCell(cell);
				
				cell = new PdfPCell(new Paragraph(":" +lsxBean.getKhoXuatTen(), font10));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setBorderWidthBottom(1);
				cell.setBorderWidthRight(1);
				//cell.setFixedHeight(0.6f * CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				cell.setPaddingLeft(0.1f * CONVERT1);
				middleTable.addCell(cell);
				     
				document.add(middleTable);
				
				//SANPHAM   
				PdfPTable sanPhamTable = new PdfPTable(TABLE_SANPHAM_WIDTHS.length);
				sanPhamTable.setWidths(TABLE_SANPHAM_WIDTHS);
				sanPhamTable.setWidthPercentage(100);
				sanPhamTable.setSpacingBefore(0.2f * CONVERT1);
				
				String[] spTitles = {"STT\nNo", "Tên nhãn hiệu & quy cách\nName of material & size", "ĐVT\nUnit", "Số lượng\nQuantity", "Trọng lượng\nWeight", "Ghi chú\nRemarks"};			
				for(int i= 0; i < spTitles.length ; i++)
				{
					cell = new PdfPCell(new Paragraph(spTitles[i], font12bold));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setPaddingBottom(0.2f * CONVERT1);
					sanPhamTable.addCell(cell);			
				}
				
				PdfPCell cells = new PdfPCell();
				
				
				//int maxLocalNumSp = numSPs - SANPHAM_NUM_ROWS >= 0 ? SANPHAM_NUM_ROWS : numSPs - SANPHAM_NUM_ROWS*pageIndex;
				int maxLocalNumSp = numSPs - currentSpId >= SANPHAM_NUM_ROWS ? SANPHAM_NUM_ROWS : numSPs - currentSpId;
				System.out.println("[ErpPhieuxuatkhoPdfSvl.CreatePxk] maxLocalNumSp = " + maxLocalNumSp);
				
				for(int i = 0; i < maxLocalNumSp; i++)
				{
					/*if(i < pxkBean.getSpList().size())
					{*/
						ISanpham sp = (ISanpham)lsxBean.getSPList().get(currentSpId);
						
					/*	double trongluong = Float.parseFloat(sp.getTrongluong().trim());*/
						//double khoiluong = Float.parseFloat(sp.getTrongluong().trim()) * Float.parseFloat(sp.getSoluong());
						//double tt = Float.parseFloat( sp.getThetich().trim() ) * Float.parseFloat(sp.getSoluong());
						//double tt = Integer.parseInt(sp.getSoluong());
						
						//String ghichu = currentSpId == 0 ? pxkBean.getGhichu() : " ";
						/*String tl = ""; // Nhom: trong luong kg ko in ra
						if(!sp.getTrongluong().equals("Kg"))
						{
							tl = formatter3.format((trongluong));
						}*/
						
						String[] arr = new String[]{
							Integer.toString(currentSpId+1),//STT 
							sp.getTen(), //San pham
							sp.getDVT(), //Don vi tinh
							formatter3.format(Float.parseFloat(sp.getSoluongYC())), // So luong 
							"", //Trong luong
							sp.getGhiChu() //Ghi chu
						};
						
						totalSoluong += Float.parseFloat(formatter3.format(Float.parseFloat(sp.getSoluongYC())).replace(",",""));
						
						/*if(sp.getThung().length() > 0)
							totalthung += Float.parseFloat(sp.getThung());
						if(sp.getLe().length() > 0)
							totalle += Float.parseFloat(sp.getLe());*/
						
					/*	if(sp.getTrongluong().length() > 0 && !sp.getDVT().equals("Kg"))
							totalTrongLuong += Double.parseDouble(formatter3.format(trongluong).replace(",",""));
						if(!sp.getDVT().equals("Kg"))
						{
							tongtrongluong = formatter3.format(totalTrongLuong);
						}*/
						/*if(sp.getThetich().length() > 0)
							totalTheTich += Float.parseFloat(sp.getThetich()) * Float.parseFloat(sp.getSoluong());*/
						
						for(int j = 0; j < spTitles.length; j++)
						{
							cells = new PdfPCell(new Paragraph(arr[j], font11));
							if(j == 1)
								cells.setHorizontalAlignment(Element.ALIGN_LEFT);
							else
							{
								cells.setHorizontalAlignment(Element.ALIGN_CENTER);
								if( j >= 5)
									cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
							}
							cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cells.setPadding(3.0f);
							
							sanPhamTable.addCell(cells);
						}
						currentSpId++;
					/*} else {
						for(int j = 0; j < 6; j++)
						{
							cells = new PdfPCell(new Paragraph(" ", new Font(bf, 8, Font.NORMAL)));
							cells.setPadding(3.0f);
							cells.setFixedHeight(0.4f*CONVERT);
							
							sanPhamTable.addCell(cells);
						}
					}*/
				}	
				
				if(pageIndex == numPages-1) {
					//TOTAL
					String[] arr = new String[]{"", "Tổng cộng/Total", "", formatter3.format(totalSoluong), tongtrongluong, "" };
					for(int j = 0; j < arr.length; j++)
					{
						cells = new PdfPCell(new Paragraph(arr[j], font11bold));
						cells.setHorizontalAlignment(Element.ALIGN_CENTER);
						cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cells.setFixedHeight(0.6f * CONVERT1);
						//cells.setPaddingRight(0.5f * CONVERT);
						
						sanPhamTable.addCell(cells);
					}
				}
				
				document.add(sanPhamTable);
				
				//Table Footer			
				PdfPTable tableFooter = new PdfPTable(TABLE_FOOTER_WIDTHS.length);
				tableFooter.setWidthPercentage(100);
				tableFooter.setHorizontalAlignment(Element.ALIGN_CENTER);
				tableFooter.setWidths(TABLE_FOOTER_WIDTHS);
				tableFooter.setSpacingBefore(0.2f * CONVERT1);
				
				PdfPCell cell11 = new PdfPCell(new Paragraph("Người nhận hàng", font12));
				cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
				PdfPCell cell12 = new PdfPCell(new Paragraph("Thủ kho", font12));
				cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
				PdfPCell cell13 = new PdfPCell(new Paragraph("Thủ trưởng đơn vị", font12));
				cell13.setHorizontalAlignment(Element.ALIGN_CENTER);
				
				cell11.setBorder(0);
				cell12.setBorder(0);
				cell13.setBorder(0);
				
				tableFooter.addCell(cell11);
				tableFooter.addCell(cell12);
				tableFooter.addCell(cell13);
				
				cell11 = new PdfPCell(new Paragraph("(Receive by)", font11));
				cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell12 = new PdfPCell(new Paragraph("(Store Keeper)", font11));
				cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell13 = new PdfPCell(new Paragraph("(Management Board)", font11));
				cell13.setHorizontalAlignment(Element.ALIGN_CENTER);
				
				cell11.setBorder(0);
				cell12.setBorder(0);
				cell13.setBorder(0);
				
				tableFooter.addCell(cell11);
				tableFooter.addCell(cell12);
				tableFooter.addCell(cell13);
				
				document.add(tableFooter);
				
				//Them trang moi neu con san pham chua in
				if(pageIndex < numPages-1) {
					document.newPage();
				}
			
			}
			
			document.close(); 
			
		 
		}
		catch(DocumentException e)
		{
			e.printStackTrace();
		}
	}
	private String getVnDateTime(String date, String split) {
		if(date.length() == 10) {
			String ngay = date.substring(8, 10);
			String thang = date.substring(5, 7);
			String nam = date.substring(0, 4);
			return ngay + split + thang + split + nam;
		} else {
			return "";
		}
	}
}
