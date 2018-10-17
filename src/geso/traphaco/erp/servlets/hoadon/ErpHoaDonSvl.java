package geso.traphaco.erp.servlets.hoadon;

import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.beans.importdata.DatafromSMART;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.hoadon.IErpHoaDon;
import geso.traphaco.erp.beans.hoadon.IErpHoaDon_SP;
import geso.traphaco.erp.beans.hoadon.imp.ErpHoaDon;
import geso.traphaco.erp.beans.lenhsanxuatgiay.IErpYeucaunguyenlieuList;
import geso.traphaco.erp.beans.lenhsanxuatgiay.imp.ErpYeucaunguyenlieuList;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpHoaDonSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpHoaDonSvl() {
        super();
    }
    
    int tranghientai=1;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpHoaDon obj;
		String userId;

		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    out = response.getWriter();
	    
	    String querystring = request.getQueryString();
	    
	    System.out.println("câu query: "+ querystring);
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	    String ddhId = util.getId(querystring);
	    
	    userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    obj = new ErpHoaDon();
	    obj.createDvkdRs();
	    obj.setCongtyId((String)session.getAttribute("congtyId"));
	    out.println(userId);
	    
	    if(action.equals("chot"))
	    {		    	
	    	System.out.println("vào đây rồi nè!");
	    	String tungay ="";
		    try{
		    	tungay = util.getCat(querystring, 2, 1);
		    	System.out.println("tu ngay: "+ tungay);
		    }
		    catch(Exception er)
		    {
		    	tungay="";
		    }
		    System.out.println("tungay: "+ tungay);
		    
		   String denngay ="";
		    try{
		    	denngay = util.getCat(querystring, 3, 1);
		    	System.out.println("denngay: "+ denngay);
		    }
		    catch(Exception er)
		    {
		    	denngay="";
		    }
		    System.out.println("denngay: "+ denngay);
		    
		    String sohoadon ="";
		    try{
		    	sohoadon = util.getCat(querystring, 4, 1);
		    	System.out.println("sohoadon: "+ sohoadon);
		    }
		    catch(Exception er)
		    {
		    	sohoadon="";
		    }
		    System.out.println("sohoadon: "+ sohoadon);
		    
		   
		    String soxuatkho ="";
		    try{
		    	sohoadon = util.getCat(querystring, 5, 1);
		    	System.out.println("soxuatkho: "+ soxuatkho);
		    }
		    catch(Exception er)
		    {
		    	soxuatkho="";
		    }
		    System.out.println("soxuatkho: "+ soxuatkho);
		    
		   
		    String donvikinhdoanh ="";
		    try{
		    	donvikinhdoanh = util.getCat(querystring, 6, 1);
		    	System.out.println("donvikinhdoanh: "+ donvikinhdoanh);
		    }
		    catch(Exception er)
		    {
		    	donvikinhdoanh="";
		    }
		    System.out.println("donvikinhdoanh: "+ donvikinhdoanh);
		    
		    String khachhang ="";
		    try{
		    	khachhang = util.getCat(querystring, 7, 1);
		    	System.out.println("khachhang: "+ khachhang);
		    }
		    catch(Exception er)
		    {
		    	khachhang="";
		    }
		    
		    String trangthai ="";
		    try{
		    	trangthai = util.getCat(querystring, 8, 1);
		    	System.out.println("trangthai: "+ trangthai);
		    }
		    catch(Exception er)
		    {
		    	trangthai="";
		    }
		    
		    if(tungay!="")
		    	obj.setTungay(tungay);
		    
		    if(denngay!="")
		    	obj.setDenNgay(denngay);
		    
		    if(sohoadon!="")
		    	obj.SetSoHoaDon(sohoadon);
		    
		    if(soxuatkho!="")
		    	obj.setSoXuatKho(soxuatkho);
		    
		    if(donvikinhdoanh!="")
		    	obj.setDvkdId(donvikinhdoanh);
		    
		    if(khachhang!="")
		    	obj.setNppTen(khachhang);
		    
		    if(trangthai!="")
		    	obj.setTrangthai(trangthai);
		    
		    String query1 =
		    " SELECT HD.PK_SEQ,HD.NGAYXUATHD,HD.TRANGTHAI,HD.NGAYTAO ,HD.NGAYSUA,HD.KYHIEU,HD.SOHOADON,HD.HINHTHUCTT, "+
	 		"        NPP.TEN AS NPP, NT.TEN AS NGUOITAO,NS.TEN AS NGUOISUA ,HD.HOANTAT ,ISNULL(HD.TONGTIENAVAT,0) AS TONGTIENAVAT, "+
	 	 	"        ISNULL(( SELECT B.PREFIX + cast(B.PK_SEQ as varchar(20)) + ', ' AS [text()] \n"+
		 	"               FROM ERP_HOADON_XUATKHO A INNER JOIN ERP_XUATKHO B ON A.XUATKHO_FK = B.PK_SEQ \n"+
		 	"               WHERE A.HOADON_FK = HD.PK_SEQ AND B.TRANGTHAI not in (2,3) " +
		 	"               For XML PATH ('') ) \n"+
		 	"              , '') AS SOXUATKHO \n"+
	 		" FROM ERP_HOADON HD INNER JOIN ERP_KHACHHANG NPP ON NPP.PK_SEQ = HD.KhachHang_FK "+
			"                    INNER JOIN NHANVIEN NT ON NT.PK_SEQ = HD.NGUOITAO "+
			"                    INNER JOIN NHANVIEN NS ON NS.PK_SEQ = HD.NGUOISUA " +
			" WHERE LOAIHD <> 2 and HD.congty_fk = '" + obj.getCongtyId() + "' ";

		    if (tungay.length() > 0)
		    {
		    	query1 = query1 + " and HD.NGAYXUATHD >= '" + tungay + "'";	
		    }

		    if (denngay.length() > 0)
		    {
		    	query1 = query1 + " and HD.NGAYXUATHD <= '" + denngay+ "'";
		    }

		    if (sohoadon.length() > 0)
		    {
		    	query1 = query1 + " and ( HD.sohoadon like '%" + sohoadon+ "%' or cast( HD.PK_SEQ as varchar(10) ) like '%" + sohoadon + "%' )  ";
		    }

		    if(trangthai.length() > 0)
		    {
		    	query1 = query1 + " and HD.trangthai = '" + trangthai + "'";
		    }

		    if(khachhang.length() > 0)
		    {
		    	query1+=" and HD.khachhang_fk in ( " +
		    	"     SELECT PK_SEQ FROM ERP_KHACHHANG WHERE MA like N'%" + khachhang+ "%' OR TEN like N'%" + khachhang+ "%'" +
		    	" )";
		    }

		    if(trangthai.length() > 0)
		    {
		    	query1 = query1 + " and HD.trangthai = '" + trangthai + "'";
		    }

		    if(donvikinhdoanh.length() > 0)
		    {
		    	query1 = query1 + " and npp.pk_seq in ( select khachhang_fk from ERP_KHACHHANG_DVKD where dvkd_fk = " + donvikinhdoanh + " )";
		    }

		    if(soxuatkho.length() >0){
		    	query1 = query1 + " and HD.pk_seq in (select hoadon_fk from erp_hoadon_xuatkho where '160'+cast( xuatkho_fk  as varchar(10) ) like '%"+soxuatkho+"%' )";
		    }
		    	
		    System.out.println("caau query: "+ query1);		    
		   // obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    session.setAttribute("obj", obj);
		    
			//session.setAttribute("obj", obj);
		    
		    obj.setNxtApprSplitting(tranghientai);
	    	String msg=this.Chot(ddhId,userId);
	    	obj.setMessage(msg);
	    	obj.setListHoaDon(query1);
	    	
	    	session.setAttribute("congtyId", obj.getCongtyId());
		    
			//obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
					
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoaDon.jsp";
			response.sendRedirect(nextJSP);
			return;
	    	
	    	//
	    }
	    else if(action.equals("delete"))
	    {
	    	String msg= this.Xoa(ddhId,userId);
	    	obj.setMessage(msg);
	    }
	    else if(action.equals("HoanTat"))
	    {
	    	String msg= this.HoanTat(ddhId,userId);
	    	obj.setMessage(msg);
	    }
	    		
	    obj.setUserId(userId);
	    //System.out.println("_____Cong ty ID: " + obj.getCongtyId());
	    obj.setListHoaDon("");
	    
		session.setAttribute("obj", obj);
		session.setAttribute("congtyId", obj.getCongtyId());
		
		//obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
				
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoaDon.jsp";
		response.sendRedirect(nextJSP);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	 	request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    IErpHoaDon obj;
	    String userId;
	    Utility util = new Utility();
	    
	    HttpSession session = request.getSession();
	    
	    obj = new ErpHoaDon();
	    obj.createDvkdRs();
	    obj.setCongtyId((String)session.getAttribute("congtyId"));
		
	    userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = request.getParameter("action");
	    if (action == null)
	    	action = "";
		     
	    if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    {
	    	//obj = new ErpHoaDon();
	    	
	    	String search = getSearchQuery(request, obj, userId);   
	    	obj.setCongtyId((String)session.getAttribute("congtyId"));
	     	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
	     	tranghientai = Integer.parseInt(request.getParameter("nxtApprSplitting"));
    		//System.out.println("trang hien tai: "+ tranghientai);
	     	obj.setUserId(userId);
	     	obj.setListHoaDon(search);
        	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
	    	
        	session.setAttribute("obj", obj);
    	    		
        	String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoaDon.jsp";	    	
    	    response.sendRedirect(nextJSP);
	    }
	    else if(action.equals("new"))
	    {
	    	obj.DbClose();
	    	obj = new ErpHoaDon();	
	    	obj.setCongtyId((String)session.getAttribute("congtyId"));
	    	obj.CreateRs();
	    	session.setAttribute("obj", obj);
	    	String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoaDonNew.jsp";	 
	    	response.sendRedirect(nextJSP);
	    }
	    else
	    {   
	    	String search = getSearchQuery(request, obj, userId);   	
	    	obj.setUserId(userId);
	    	obj.setListHoaDon(search);
				
	    	session.setAttribute("obj", obj);
		    		
	    	String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoaDon.jsp";	    	
	    	out.println(search);
		    response.sendRedirect(nextJSP);
	    }

	}

	private String Xoa(String ddhId,String user) 
	{
		try{
				dbutils db = new dbutils();
				db.getConnection().setAutoCommit(false);
				
				//Xóa các sản phẩm trong hóa đơn không in được
				String query = "delete  ERP_HOADON_SP where HOADON_FK = '" + ddhId + "' and isnull(INRAHD, '1') != '1'";
				if(!db.update(query))
				{
					db.getConnection().rollback();
					return "Khong The Chot Hoa Don ,Loi Dong Lenh : " + query;
				}
				
				query = "update  Erp_HoaDon set trangthai='2', nguoisua='" + user + "' where pk_Seq = '" + ddhId + "'";
				if(!db.update(query))
				{
					db.update("rollback");
					return "Khong The Huy Hoa Don ,Loi Dong Lenh : "+ query;
				}
				
				query = " UPDATE ERP_XUATKHO SET TRANGTHAI= 1 WHERE PK_SEQ IN "+ 
						" ( "+
						" SELECT HD_XK.xuatkho_fk FROM ERP_HOADON_XUATKHO  "+
						" HD_XK INNER JOIN ERP_HOADON HD ON HD.PK_SEQ=HD_XK.HOADON_FK "+
						" WHERE  HD.PK_SEQ ="+ddhId+" and not exists "+ 
						" (SELECT HD_XK.* FROM ERP_HOADON_XUATKHO  "+
						" HD_XK1 INNER JOIN ERP_HOADON HD1 ON HD1.PK_SEQ=HD_XK1.HOADON_FK WHERE HD1.TRANGTHAI <> '2' "+
						" AND HD1.PK_SEQ <>"+ddhId+"  AND HD_XK.xuatkho_fk =HD_XK1.xuatkho_fk  )) ";
				//System.out.println(query);
				if(!db.update(query))
				{
					db.update("rollback");
					return "Khong The Huy Hoa Don ,Loi Dong Lenh : "+ query;
				}
				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
				
				db.shutDown();
				
				
		}catch(Exception er){
			
			er.printStackTrace();
			return er.getMessage();
		}
		return "";
	}
	 
	
	
	  public static void main ( String args [  ]  )   {
		 
	    }
	
	private String Chot(String ddhId, String user) 
	{
		IErpHoaDon dhBean = new ErpHoaDon(ddhId);
		//dhBean.init(ddhId, false);
		dbutils db = new dbutils();
		String msg = "";
				
		try
		{
			db.getConnection().setAutoCommit(false);
			
			//Xóa các sản phẩm trong hóa đơn không in được
			String query = "delete  ERP_HOADON_SP where HOADON_FK = '" + ddhId + "' and isnull(INRAHD, '1') != '1'";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Khong The Chot Hoa Don ,Loi Dong Lenh : " + query;
			}
			
			query = "update  Erp_HoaDon set trangthai = '1', nguoisua = '" + user + "' where pk_Seq = '" + ddhId + "'";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Khong The Chot Hoa Don ,Loi Dong Lenh : " + query;
			}
			
			query = " update Erp_dondathang set trangthai = '5', tinhtrang = '2' " +
					" where pk_seq in ( select ddh_fk from erp_hoadon_ddh where hoadon_fk = '" + ddhId + "'  " +
					"  union select dondathang_fk from ERP_XUATKHO where pk_seq in  " +
					" ( select xuatkho_fk from ERP_HOADON_XUATKHO where hoadon_fk = '" + ddhId + "' ) )";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Khong The Chot Hoa Don ,Loi Dong Lenh : " + query;
			}
			
			query="select xuatkho_fk from erp_hoadon_xuatkho where hoadon_fk= "+ddhId;
			ResultSet rs_pxk=db.get(query);
			while (rs_pxk.next()){
				String pxkId=rs_pxk.getString("xuatkho_fk");
				query=" SELECT A.SANPHAM_FK, A.MA, A.TENSP, A.TEN1, A.TEN2, A.TEN3, A.DONGIA, A.SCHEME, A.QUYDOI, A.DONVI, DVDLCHUAN, A.GHICHUSP, "  + 
				 " A.QUYCACH, A.DAI, A.RONG, A.DVDL_DAI, A.DVDL_RONG, A.DINHLUONG, A.DVDL_DINHLUONG, A.TRONGLUONG, SUM(A.SOLUONG) AS SOLUONG "  + 
				 " FROM "  + 
				 " (  "  + 
				 " SELECT XUAT.SANPHAM_FK, SP.MA, SP.TEN + ' ' + SP.QUYCACH AS TENSP, ISNULL(SP.TEN, '') AS TEN1, ISNULL(SP.TEN1, ISNULL(SP.TEN, '')) AS TEN2, "  + 
				 " ISNULL(SP.TEN2, ISNULL(SP.TEN, '')) AS TEN3, XUAT.DONGIA,  	SP.DAI,SP.RONG,SP.DVDL_DAI,SP.DVDL_RONG,SP.DINHLUONG,SP.DVDL_DINHLUONG,SP.TRONGLUONG, "  + 
				 " CASE WHEN XK_SP.DVDL_FK = XUAT.DVDL_FK THEN XK_SP.SOLUONG  	ELSE XK_SP.SOLUONG * ISNULL(QC.SOLUONG2, 1) / ISNULL(QC.SOLUONG1, 1) END AS SOLUONG, "  + 
				 " ISNULL(DV.DONVI, '') AS DONVI, '' AS SCHEME, '1' AS QUYDOI, ISNULL(XK_SP.GHICHU, '') AS GHICHUSP, ISNULL(SP.QUYCACH, '') AS QUYCACH, "  + 
				 " SP.DVDL_FK AS DVDLCHUAN "  + 
				 " FROM  (  "  + 
				 " "  + 
				 " SELECT DISTINCT XK.PK_SEQ AS XKID, DH_SP.SANPHAM_FK, DH_SP.DONGIA, DH.TIENTE_FK, " +
				 " case when isnull(DH_SP.DONGIA,0) <>0 then DH_SP.DONGIAVIET   / DH_SP.DONGIA   else 0  end AS QUYDOI , DH_SP.DVDL_FK    FROM ERP_XUATKHO XK INNER JOIN ERP_DONDATHANG DH ON XK.DONDATHANG_FK = DH.PK_SEQ   "  + 
				 " INNER JOIN ERP_DONDATHANG_SP DH_SP ON DH.PK_SEQ = DH_SP.DONDATHANG_FK   WHERE XK.PK_SEQ IN ( "+pxkId+" )   "  + 
				 " "  + 
				 " )   "  + 
				 " XUAT INNER JOIN ERP_XUATKHO_SANPHAM XK_SP ON XUAT.XKID = XK_SP.XUATKHO_FK AND XUAT.SANPHAM_FK = XK_SP.SANPHAM_FK  "  + 
				 " INNER JOIN ERP_SANPHAM SP ON XK_SP.SANPHAM_FK = SP.PK_SEQ  LEFT JOIN QUYCACH QC ON XK_SP.SANPHAM_FK = QC.SANPHAM_FK "  + 
				 " AND XUAT.DVDL_FK = QC.DVDL2_FK   INNER JOIN DONVIDOLUONG DV ON XUAT.DVDL_FK = DV.PK_SEQ  WHERE XK_SP.SOLUONG >0 and   XUAT.SANPHAM_FK NOT IN  "  + 
				 " (	SELECT DISTINCT SANPHAM_FK FROM ERP_HOADON_XUATKHO HDDDH 	INNER JOIN ERP_HOADON HD ON HD.PK_SEQ = HDDDH.HOADON_FK  "  + 
				 " INNER JOIN ERP_HOADON_SP HDSP ON HD.PK_SEQ = HDSP.HOADON_FK 	WHERE HD.TRANGTHAI != '2' AND ISNULL(HDSP.INRAHD, '1') = 1   "  + 
				 " AND HDDDH.XUATKHO_FK= XK_SP.XUATKHO_FK   ) ) A GROUP BY A.SANPHAM_FK, A.MA, A.TENSP, A.TEN1, A.TEN2, A.TEN3, A.DONGIA, A.SCHEME "  + 
				 " , A.QUYDOI, A.DONVI, DVDLCHUAN, A.GHICHUSP, A.QUYCACH, A.DAI, A.RONG, A.DVDL_DAI, A.DVDL_RONG, A.DINHLUONG, A.DVDL_DINHLUONG,  "  + 
				 " A.TRONGLUONG";
				System.out.println("Du Lieu Check Hoan tat: "+query);
				ResultSet rscheck=db.get(query);
				if(!rscheck.next()){
					// Hoàn tất hóa đơn
					query="Update erp_xuatkho set trangthai=5  where pk_Seq="+pxkId;
	
					if(!db.update(query))
					{
						db.getConnection().rollback();
						return "Khong The Chot Hoa Don ,Loi Dong Lenh : " + query;
					}
				}
				rscheck.close();
			}
			rs_pxk.close();
			ResultSet rsHd = db.get("select isnull(XUATTHEO, '') as xuatTheo from ERP_HOADON where PK_SEQ = '" + ddhId + "'");
			String xuatTheo = "";
			if(rsHd.next())
			{
				xuatTheo = rsHd.getString("xuatTheo");
				rsHd.close();
			}
			Utility util = new Utility();
			if(xuatTheo.equals("1"))  //CHI XUAT THEO XK MOI GHI NHAN CONG NO
			{
				String nam = dhBean.getNgayghinhanCN().substring(0, 4);
				String thang = dhBean.getNgayghinhanCN().substring(5, 7);
				
				//Chiet khau
				//double totalChietkhau = dhBean.getChietkhau() + dhBean.getTienkhuyenmai();
				double totalChietkhau = dhBean.getTienChietKhau() + dhBean.getTienkhuyenmai();
				if( totalChietkhau > 0)
				{
					String taikhoanktCo = "";
					String taikhoanktNo = "";
					
					/*query = "select distinct TAIKHOANKTCO, TAIKHOANKTNO from ERP_CAUHINHXUATHOADON " +
							"where NOIDUNGNHAP_FK = '" + noidungxuat_fk + "' and khoanmuc = N'Chiết khấu'";*/
					
					/*query = " select a.taikhoan_fk as TAIKHOANKTNO, case b.dvkd_fk when 100000 then ( select pk_seq from erp_taikhoankt where sohieutaikhoan = '521100' )  " +
								" else ( select pk_seq from erp_taikhoankt where sohieutaikhoan = '521200' ) end as TAIKHOANKTCO  " +
							"from erp_khachhang a, " +
							"( " +
								"select top(1) dvkd_fk from ERP_Dondathang where pk_seq in ( select ddh_fk from erp_hoadon_ddh where hoadon_fk = '" + ddhId + "' ) " +
							") b  " +
							"where a.pk_seq = ( select khachhang_fk from erp_hoadon where pk_seq = '" + ddhId + "' ) ";*/
					
					query = " select a.taikhoan_fk as TAIKHOANKTNO, case when c.loaisanpham_fk in (100013,100014,100015,100016,100017) then ( select pk_seq from erp_taikhoankt where sohieutaikhoan = '521800' ) " +
					"   else (case  when b.dvkd_fk = 100000  then ( select pk_seq from erp_taikhoankt where sohieutaikhoan = '521100' )  " +
					"   when b.dvkd_fk = 100005 then  ( select pk_seq from erp_taikhoankt where sohieutaikhoan = '521100' )  " +
					" 	 when b.dvkd_fk = 100004 then ( select pk_seq from erp_taikhoankt where sohieutaikhoan = '521200' ) end ) " +
							"									    end as TAIKHOANKTCO   " +
							" from erp_khachhang a, " +
							"( " +
							" select top(1) dvkd_fk, xk.pk_seq  from ERP_Dondathang dh inner join Erp_Xuatkho xk on dh.pk_seq = xk.dondathang_fk " +
							" where xk.pk_seq in ( select xuatkho_fk from erp_hoadon_xuatkho where hoadon_fk = '" + ddhId + "' ) " +
							" ) b, Erp_Sanpham c  " +
							" where a.pk_seq = ( select khachhang_fk from erp_hoadon where pk_seq = '" + ddhId + "' ) " +
							"      and c.pk_seq in (select sanpham_fk from Erp_Xuatkho_sanpham where xuatkho_fk = b.pk_seq )  ";
					
					//System.out.println("1.Query lay tai khoan: " + query);
					
					ResultSet tkRs = db.get(query);
					if(tkRs != null)
					{
						if(tkRs.next())
						{
							taikhoanktCo = tkRs.getString("TAIKHOANKTCO");
							taikhoanktNo = tkRs.getString("TAIKHOANKTNO");
							tkRs.close();
						}
						
						if(taikhoanktCo.trim().length() <= 0 || taikhoanktNo.trim().length() <= 0)
						{
							msg = "2.Khoản mục chiết khấu và nội dung nhận chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
							db.getConnection().rollback();
							return msg;
						}
						
						//Nguyen te khi xuat kho chinh la VND
						String tiente_fk = dhBean.getTienteId();
						
						
						//NEW KE TOAN
						double thanhtien = totalChietkhau;
						double thanhtienviet = totalChietkhau * Double.parseDouble(dhBean.getTyGiaQuyDoi());
						msg = util.Update_TaiKhoan( db, thang, nam, dhBean.getNgayghinhanCN(), dhBean.getNgayghinhanCN(), "Hóa đơn", ddhId, taikhoanktNo, taikhoanktCo, "", 
													Double.toString(thanhtienviet), Double.toString(thanhtienviet), "Chiết khấu", "", "Chiết khấu", "", "0", "", "", tiente_fk, "0", dhBean.getTyGiaQuyDoi(), Double.toString(thanhtienviet), Double.toString(thanhtien), "Chiết khấu" );
						if(msg.trim().length() > 0)
						{
							db.getConnection().rollback();
							return msg;
						}
						
					}
				}
				
				//System.out.println("__Vat la: " + dhBean.getVAT());
				if(dhBean.getTienVAT() > 0)
				{
					String taikhoanktCo = "";
					String taikhoanktNo = "";
					
					/*query = "select distinct TAIKHOANKTCO, TAIKHOANKTNO from ERP_CAUHINHXUATHOADON " +
							"where NOIDUNGNHAP_FK = '" + noidungxuat_fk + "' and khoanmuc = N'Thuế VAT'";*/
					
					query = " select a.taikhoan_fk as TAIKHOANKTNO, b.pk_seq as TAIKHOANKTCO  " +
							"from erp_khachhang a, erp_taikhoankt b   " +
							"where a.pk_seq = ( select khachhang_fk from erp_hoadon where pk_seq = '" + ddhId + "' ) and b.sohieutaikhoan = '333110' ";
					
					//System.out.println("5.Query lay tai khoan: " + query);
					
					ResultSet tkRs = db.get(query);
					if(tkRs != null)
					{
						if(tkRs.next())
						{
							taikhoanktCo = tkRs.getString("TAIKHOANKTCO");
							taikhoanktNo = tkRs.getString("TAIKHOANKTNO");
							tkRs.close();
						}
						
						if(taikhoanktCo.trim().length() <= 0 || taikhoanktNo.trim().length() <= 0)
						{
							msg = "6.Khoản mục VAT và nội dung nhận chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
							db.getConnection().rollback();
							return msg;
						}						
						//Nguyen te khi xuat kho chinh la VND
						String tiente_fk = dhBean.getTienteId();
						
						//NEW KE TOAN
						double thanhtien = dhBean.getTienVAT();
						double thanhtienviet= thanhtien * Double.parseDouble(dhBean.getTyGiaQuyDoi());
						msg = util.Update_TaiKhoan( db, thang, nam, dhBean.getNgayghinhanCN(), dhBean.getNgayghinhanCN(), "Hóa đơn", ddhId, taikhoanktNo, taikhoanktCo, "", 
													Double.toString(thanhtienviet) , Double.toString(thanhtienviet), "VAT", "", "VAT", "", "0", "", "", tiente_fk, "0",dhBean.getTyGiaQuyDoi(), Double.toString(thanhtienviet), Double.toString(thanhtien), "VAT" );
						if(msg.trim().length() > 0)
						{
							db.getConnection().rollback();
							return msg;
						}
						
					}
				}
				
				// TIỀN GIẢM GIÁ
				
				 if(dhBean.getTienkhuyenmai() > 0)
				 {

						String taikhoanktCo = "";
						String taikhoanktNo = "";					
						
						query = " select a.taikhoan_fk as TAIKHOANKTCO, " +
						"                (case when b.dvkd_fk in (100000,100005)  then ( select pk_seq from erp_taikhoankt where sohieutaikhoan = '532100' )  " +
						" 					   when b.dvkd_fk = 100004  then ( select pk_seq from erp_taikhoankt where sohieutaikhoan = '532200' ) end ) " +
						"						as TAIKHOANKTNO   " +
						" from erp_khachhang a, " +
						"( " +
						" select top(1) dvkd_fk, xk.pk_seq  from ERP_Dondathang dh inner join Erp_Xuatkho xk on dh.pk_seq = xk.dondathang_fk " +
						" where xk.pk_seq in ( select xuatkho_fk from erp_hoadon_xuatkho where hoadon_fk = '" + ddhId + "' ) " +
						" ) b, Erp_Sanpham c  " +
						" where a.pk_seq = ( select khachhang_fk from erp_hoadon where pk_seq = '" + ddhId + "' ) " +
						"      and c.pk_seq in (select sanpham_fk from Erp_Xuatkho_sanpham where xuatkho_fk = b.pk_seq )  ";
						
						ResultSet tkRs = db.get(query);
						if(tkRs != null)
						{
							if(tkRs.next())
							{
								taikhoanktCo = (tkRs.getString("TAIKHOANKTCO")==null?"":tkRs.getString("TAIKHOANKTCO") );
								taikhoanktNo = tkRs.getString("TAIKHOANKTNO")==null?"":tkRs.getString("TAIKHOANKTNO");
								tkRs.close();
							}
							
							if(taikhoanktCo.trim().length() <= 0 || taikhoanktNo.trim().length() <= 0)
							{
								msg = "6.Khoản mục TIỀN GIẢM GIÁ và nội dung nhận chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
								db.getConnection().rollback();
								return msg;
							}
							
							//Nguyen te khi xuat kho chinh la VND
							String tiente_fk = dhBean.getTienteId();
							
							//NEW KE TOAN
							double thanhtien = dhBean.getTienkhuyenmai();
							double thanhtienviet= thanhtien * Double.parseDouble(dhBean.getTyGiaQuyDoi());
							msg = util.Update_TaiKhoan( db, thang, nam, dhBean.getNgayghinhanCN(),dhBean.getNgayghinhanCN(), "Hóa đơn", ddhId, taikhoanktNo, taikhoanktCo, "", 
														Double.toString(thanhtienviet) , Double.toString(thanhtienviet), "GIAMGIA", "", "GIAMGIA", "", "0", "", "", tiente_fk, "0",dhBean.getTyGiaQuyDoi(), Double.toString(thanhtienviet), Double.toString(thanhtien), "GIAMGIA" );
							if(msg.trim().length() > 0)
							{
								db.getConnection().rollback();
								return msg;
							}
							
						}
					 
				 }
				
				// TIỀN VẬN CHUYỂN 
				
				 if(dhBean.getTienVanChuyen() > 0)
				 {

						String taikhoanktCo = "";
						String taikhoanktNo = "";					
						
						query = " select a.taikhoan_fk as TAIKHOANKTNO, " +
						"                (case when  b.dvkd_fk in (100000,100005)  then ( select pk_seq from erp_taikhoankt where sohieutaikhoan = '511100' )  " +
						" 					   when b.dvkd_fk = 100004  then ( select pk_seq from erp_taikhoankt where sohieutaikhoan = '511200' ) end ) " +
						"						as TAIKHOANKTCO   " +
						" from erp_khachhang a, " +
						"( " +
						" select top(1) dvkd_fk, xk.pk_seq  from ERP_Dondathang dh inner join Erp_Xuatkho xk on dh.pk_seq = xk.dondathang_fk " +
						" where xk.pk_seq in ( select xuatkho_fk from erp_hoadon_xuatkho where hoadon_fk = '" + ddhId + "' ) " +
						" ) b, Erp_Sanpham c  " +
						" where a.pk_seq = ( select khachhang_fk from erp_hoadon where pk_seq = '" + ddhId + "' ) " +
						"      and c.pk_seq in (select sanpham_fk from Erp_Xuatkho_sanpham where xuatkho_fk = b.pk_seq )  ";
						
						ResultSet tkRs = db.get(query);
						if(tkRs != null)
						{
							if(tkRs.next())
							{
								taikhoanktCo = tkRs.getString("TAIKHOANKTCO")==null?"": tkRs.getString("TAIKHOANKTCO");
								taikhoanktNo = tkRs.getString("TAIKHOANKTNO")==null?"":tkRs.getString("TAIKHOANKTNO");
								tkRs.close();
							}
							
							if(taikhoanktCo.trim().length() <= 0 || taikhoanktNo.trim().length() <= 0)
							{
								msg = "6.Khoản mục VANCHUYEN và nội dung nhận chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
								db.getConnection().rollback();
								return msg;
							}
							
							//Nguyen te khi xuat kho chinh la VND
							String tiente_fk = dhBean.getTienteId();
							
							//NEW KE TOAN
							double thanhtien = dhBean.getTienVanChuyen();
							double thanhtienviet= thanhtien * Double.parseDouble(dhBean.getTyGiaQuyDoi());
							msg = util.Update_TaiKhoan( db, thang, nam, dhBean.getNgayghinhanCN(),dhBean.getNgayghinhanCN(), "Hóa đơn", ddhId, taikhoanktNo, taikhoanktCo, "", 
														Double.toString(thanhtienviet) , Double.toString(thanhtienviet), "VANCHUYEN", "", "VANCHUYEN", "", "0", "", "", tiente_fk, "0",dhBean.getTyGiaQuyDoi(), Double.toString(thanhtienviet), Double.toString(thanhtien), "VANCHUYEN" );
							if(msg.trim().length() > 0)
							{
								db.getConnection().rollback();
								return msg;
							}
							
						}
					 
				 }
				 
				// TIỀN BẢO HIỂM
				
				 if(dhBean.getTienBaoHiem() > 0)
				 {

						String taikhoanktCo = "";
						String taikhoanktNo = "";					
						
						query = " select a.taikhoan_fk as TAIKHOANKTNO, " +
						"                (case when b.dvkd_fk in (100000,100005)  then ( select pk_seq from erp_taikhoankt where sohieutaikhoan = '511100' )  " +
						" 					   when b.dvkd_fk = 100004  then ( select pk_seq from erp_taikhoankt where sohieutaikhoan = '511200' ) end ) " +
						"						as TAIKHOANKTCO   " +
						" from erp_khachhang a, " +
						"( " +
						" select top(1) dvkd_fk, xk.pk_seq  from ERP_Dondathang dh inner join Erp_Xuatkho xk on dh.pk_seq = xk.dondathang_fk " +
						" where xk.pk_seq in ( select xuatkho_fk from erp_hoadon_xuatkho where hoadon_fk = '" + ddhId + "' ) " +
						" ) b, Erp_Sanpham c  " +
						" where a.pk_seq = ( select khachhang_fk from erp_hoadon where pk_seq = '" + ddhId + "' ) " +
						"      and c.pk_seq in (select sanpham_fk from Erp_Xuatkho_sanpham where xuatkho_fk = b.pk_seq )  ";
						
						ResultSet tkRs = db.get(query);
						if(tkRs != null)
						{
							if(tkRs.next())
							{
								taikhoanktCo = tkRs.getString("TAIKHOANKTCO");
								taikhoanktNo = tkRs.getString("TAIKHOANKTNO");
								tkRs.close();
							}
							
							if(taikhoanktCo.trim().length() <= 0 || taikhoanktNo.trim().length() <= 0)
							{
								msg = "6.Khoản mục BAOHIEM và nội dung nhận chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
								db.getConnection().rollback();
								return msg;
							}
							
							//Nguyen te khi xuat kho chinh la VND
							String tiente_fk = dhBean.getTienteId();
							
							//NEW KE TOAN
							double thanhtien = dhBean.getTienBaoHiem();
							double thanhtienviet= thanhtien * Double.parseDouble(dhBean.getTyGiaQuyDoi());
							msg = util.Update_TaiKhoan( db, thang, nam, dhBean.getNgayghinhanCN(),dhBean.getNgayghinhanCN(), "Hóa đơn", ddhId, taikhoanktNo, taikhoanktCo, "", 
														Double.toString(thanhtienviet) , Double.toString(thanhtienviet), "BAOHIEM", "", "BAOHIEM", "", "0", "", "", tiente_fk, "0",dhBean.getTyGiaQuyDoi(), Double.toString(thanhtienviet), Double.toString(thanhtien), "BAOHIEM" );
							if(msg.trim().length() > 0)
							{
								db.getConnection().rollback();
								return msg;
							}
							
						}
					 
				 }
				
				//Doanh so
				List<IErpHoaDon_SP> splist = dhBean.GetListSanPham();
				for(int i = 0; i < splist.size(); i++)
				{
					IErpHoaDon_SP sp = splist.get(i);
					
					String taikhoanktCo = "";
					String taikhoanktNo = "";
				
					/*query = "select TAIKHOANKTCO, TAIKHOANKTNO " +
							"from ERP_CAUHINHXUATHOADON  " +
							"where NOIDUNGNHAP_FK = '" + noidungxuat_fk + "' and LOAISANPHAM_FK = '" + sp.getLoaisp() + "' and khoanmuc = N'Doanh số' ";*/
					
					query = " select a.taikhoan_fk as TAIKHOANKTNO, case when b.loaisanpham_fk in (100013,100014,100015,100016,100017) then ( select pk_seq from erp_taikhoankt where sohieutaikhoan = '511800' ) " +
							"                                       else (case  when b.dvkd_fk = 100000  then ( select pk_seq from erp_taikhoankt where sohieutaikhoan = '511100' )  " +
							"                                                   when b.dvkd_fk = 100005  then ( select pk_seq from erp_taikhoankt where sohieutaikhoan = '511100' )" +
							" 									                when b.dvkd_fk = 100004  then  ( select pk_seq from erp_taikhoankt where sohieutaikhoan = '511200' ) end ) " +
							"									    end as TAIKHOANKTCO   " +
							"from erp_khachhang a, erp_sanpham b " +
							"where a.pk_seq = ( select khachhang_fk from erp_hoadon where pk_seq = '" + ddhId + "' ) and b.pk_seq = '" + sp.getIdSanPham() + "' ";
					
					//System.out.println("9.Query lay tai khoan: " + query);
					
					ResultSet tkRs = db.get(query);
					if(tkRs != null)
					{
						if(tkRs.next())
						{
							taikhoanktCo = tkRs.getString("TAIKHOANKTCO");
							taikhoanktNo = tkRs.getString("TAIKHOANKTNO");
							tkRs.close();
						}
						
						if(taikhoanktCo.trim().length() <= 0 || taikhoanktNo.trim().length() <= 0)
						{
							msg = "Khoản mục doanh số và nội dung nhập tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
							db.getConnection().rollback();
							return msg;
						}
						
						//Nguyen te khi xuat kho chinh la VND
						String tiente_fk = dhBean.getTienteId();
						
						double dongia = sp.getDonGia();
						double dongiaViet = sp.getDonGiaViet();
						
						
						//NEW KE TOAN
						double thanhtien = dongiaViet * sp.getSoLuong();
						msg = util.Update_TaiKhoan( db, thang, nam, dhBean.getNgayghinhanCN(), dhBean.getNgayghinhanCN(), "Hóa đơn", ddhId, taikhoanktNo, taikhoanktCo, "", 
													Double.toString(thanhtien), Double.toString(thanhtien), "Sản phẩm", sp.getIdSanPham(), "Sản phẩm", sp.getIdSanPham(), "0", Double.toString(sp.getSoLuong()), Double.toString(dongiaViet), tiente_fk, Double.toString(dongia), dhBean.getTyGiaQuyDoi(), dongiaViet + "*" + sp.getSoLuong(), dongia + "*" + sp.getSoLuong(), "Doanh số" );
						if(msg.trim().length() > 0)
						{
							db.getConnection().rollback();
							return msg;
						}
						
					}
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch(Exception er)
		{
			
			er.printStackTrace();
			db.update("rollback");
			return "12.Loi Khong The Cap nhat :"+ er.toString();	
		}
		
		db.shutDown();
		return "";
	}
	
	private String HoanTat(String ddhId,String user)
	{
		return "";
	}
	

	private String getSearchQuery(HttpServletRequest request, IErpHoaDon obj, String userId)
	{
	
		Utility util = new Utility();
		String sohoadon = util.antiSQLInspection(request.getParameter("sohoadon"));
    	if (sohoadon == null)
    		sohoadon = "";
    	obj.SetSoHoaDon(sohoadon);
    
    	String tungay = util.antiSQLInspection(request.getParameter("tungay"));
    	if (tungay == null)
    		tungay = "";    	
    	obj.setTungay(tungay);

    	String denngay = util.antiSQLInspection(request.getParameter("denngay"));
    	if (denngay == null)
    		denngay = "";    	
    	obj.setDenNgay(denngay);
    	   	
    	String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));   	
    	if (trangthai == null)
    		trangthai = "";    	
    	obj.setTrangthai(trangthai);
    		   	
    	String khachhang = util.antiSQLInspection(request.getParameter("khachhang"));   	
    	if (khachhang == null) khachhang = "";    	
    	obj.setNppTen(khachhang);

    	String dvkdId = util.antiSQLInspection(request.getParameter("dvkdId"));   	
    	if (dvkdId == null) dvkdId = ""; else dvkdId = dvkdId.trim();   	
    	obj.setDvkdId(dvkdId);
  
    	
    	String soxuatkho = util.antiSQLInspection(request.getParameter("soxuatkho"));   	
    	if (soxuatkho == null) soxuatkho = "";    	
    	obj.setSoXuatKho(soxuatkho);
    	    	
    	String query1 = " SELECT HD.PK_SEQ,HD.NGAYXUATHD,HD.TRANGTHAI,HD.NGAYTAO ,HD.NGAYSUA,HD.KYHIEU,HD.SOHOADON,HD.HINHTHUCTT, "+
				 		"        NPP.TEN AS NPP, NT.TEN AS NGUOITAO,NS.TEN AS NGUOISUA ,HD.HOANTAT ," +
				 		"        ISNULL(HD.TONGTIENAVAT,0) AS TONGTIENAVAT, "+
				 		"        ISNULL((SELECT B.PREFIX + cast(B.PK_SEQ as varchar(20)) + ', ' AS [text()] \n"+
					 	"               FROM ERP_HOADON_XUATKHO A INNER JOIN ERP_XUATKHO B ON A.XUATKHO_FK = B.PK_SEQ \n"+
					 	"               WHERE A.HOADON_FK = HD.PK_SEQ AND B.TRANGTHAI not in (2,3) " +
					 	"               For XML PATH ('') ) \n"+
					 	"              , '') AS SOXUATKHO \n"+
				 		" FROM ERP_HOADON HD INNER JOIN ERP_KHACHHANG NPP ON NPP.PK_SEQ = HD.KhachHang_FK "+
						"      INNER JOIN NHANVIEN NT ON NT.PK_SEQ = HD.NGUOITAO "+
						"      INNER JOIN NHANVIEN NS ON NS.PK_SEQ = HD.NGUOISUA " +
						" WHERE LOAIHD <> 2 and HD.congty_fk = '" + obj.getCongtyId() + "' ";
    	
    	if (tungay.length() > 0)
    	{
			query1 = query1 + " and HD.NGAYXUATHD >= '" + tungay + "'";	
    	}

    	if (denngay.length() > 0)
    	{
			query1 = query1 + " and HD.NGAYXUATHD <= '" + denngay+ "'";
    	}
    	
    	if (sohoadon.length() > 0)
    	{
			query1 = query1 + " and ( HD.sohoadon like '%" + sohoadon+ "%' or cast( HD.PK_SEQ as varchar(10) ) like '%" + sohoadon + "%' )  ";
    	}
    	
    	if(trangthai.length() > 0)
    	{
    		query1 = query1 + " and HD.trangthai = '" + trangthai + "'";
    	}
    	
    	if(khachhang.length() > 0)
    	{
    		query1+=" and HD.khachhang_fk in ( " +
    				"     SELECT PK_SEQ FROM ERP_KHACHHANG WHERE MA like N'%" + khachhang+ "%' OR TEN like N'%" + khachhang+ "%'" +
    				" )";
    	}
    	
    	if(trangthai.length() > 0)
    	{
    		query1 = query1 + " and HD.trangthai = '" + trangthai + "'";
    	}
    	
    	if(dvkdId.length() > 0)
    	{
    		query1 = query1 + " and npp.pk_seq in ( select khachhang_fk from ERP_KHACHHANG_DVKD where dvkd_fk = " + dvkdId + " )";
    	}
    	
    	if(soxuatkho.length() >0){
    		query1 = query1 + " and HD.pk_seq in (select hoadon_fk from erp_hoadon_xuatkho where '160'+cast( xuatkho_fk  as varchar(10) ) like '%"+soxuatkho+"%' )";
    	}
    	
    	System.out.println("Câu query search :"+query1);
    	return query1;    	
	}	
		

}
