package geso.traphaco.erp.servlets.lenhsanxuatgiay;
 
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
 
import geso.traphaco.erp.beans.danhmucvattu.IDanhmucvattu_SP;
import geso.traphaco.erp.beans.danhmucvattu.imp.Danhmucvattu_SP;
import geso.traphaco.erp.beans.lenhsanxuatgiay.IErpLenhsanxuat;
import geso.traphaco.erp.beans.lenhsanxuatgiay.IErpTieuhaonguyenlieu;
import geso.traphaco.erp.beans.lenhsanxuatgiay.IErpTieuhaonguyenlieulist;
import geso.traphaco.erp.beans.lenhsanxuatgiay.imp.ErpLenhsanxuat;
import geso.traphaco.erp.beans.lenhsanxuatgiay.imp.ErpTieuhaonguyenlieu;
import geso.traphaco.erp.beans.lenhsanxuatgiay.imp.ErpTieuhaonguyenlieuList;
import geso.traphaco.erp.beans.phieuxuatkho.ISpDetail;
import geso.traphaco.erp.beans.phieuxuatkho.imp.SpDetail;
import geso.traphaco.erp.beans.sohoadon.imp.Sohoadon;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpLenhsanxuatgiayActionSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
	PrintWriter out;
    public ErpLenhsanxuatgiayActionSvl() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		Utility cutil = (Utility) session.getAttribute("util");
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
		    
		    
    		
		    IErpTieuhaonguyenlieu lsxBean = new  ErpTieuhaonguyenlieu(id);
		    lsxBean.setCongtyId((String) session.getAttribute("congtyId"));
		    lsxBean.setNppId((String) session.getAttribute("nppId"));
		    
		    lsxBean.setTieuHaoId(id);
		    lsxBean.checkTieuHaoLsx();
		    
		    lsxBean.setUserId(userId);
		    lsxBean.createLoaisanphamRs();
			session.setAttribute("lsxBean", lsxBean);
			session.setAttribute("nhamayid", lsxBean.getNhamayId());
			lsxBean.CreateRs_tieuhao();
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpLenhSanXuatGiayTieuHao.jsp";
			response.sendRedirect(nextJSP);
			
			
		}		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
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
			this.out = response.getWriter();
			
			String task = request.getParameter("task");
			if(task == null)
				task = "";
			
			System.out.println("task "+ task);
			if(task.equals("tieuHao"))
			{
				this.tieuHaoNL(userId, session, request, response);
			}else if(task.equals("capnhathoso")){
				this.CapnhatHoso(userId, session, request, response);
			}
			else if(task.equals("capnhattongketlo")){
				this.capnhattongketlo(userId, session, request, response);
			}
			
			
		}
			
	}
	

	private void capnhattongketlo(String userId, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		IErpLenhsanxuat lsxBean;
		NumberFormat formatter = new DecimalFormat("#######.######");
		
		Utility util = new Utility();	
		String id = util.antiSQLInspection(request.getParameter("id"));
	    if(id == null)
	    {  	
	    	lsxBean = new ErpLenhsanxuat("");
	    }
	    else
	    {
	    	lsxBean = new ErpLenhsanxuat(id);
	    }
	    
	    lsxBean.setUserId(userId);
	    lsxBean.setCongtyId((String) session.getAttribute("congtyId"));
	    
	    
	    String NDHL = util.antiSQLInspection(request.getParameter("NDHL"));
		if (NDHL == null)
			NDHL = "";				
		lsxBean.setNDHL(NDHL);
		
	    String phache = util.antiSQLInspection(request.getParameter("phache"));
		if (phache == null)
			phache = "";				
		lsxBean.setPHACHE(phache);
		
		
	    String ykienkhac = util.antiSQLInspection(request.getParameter("ykienkhac"));
		if (ykienkhac == null)
			ykienkhac = "";				
		lsxBean.setYKIENKHAC(ykienkhac);
	    
	    String ghichutiendo = util.antiSQLInspection(request.getParameter("ghichutiendo"));
		if (ghichutiendo == null)
			ghichutiendo = "";				
		lsxBean.setGHICHU_TIENDO(ghichutiendo);
		
		  String HIEUSUAT_SX = util.antiSQLInspection(request.getParameter("HIEUSUAT_SX"));
			if (HIEUSUAT_SX == null)
				HIEUSUAT_SX = "";				
			lsxBean.setHIEUSUAT_SX(HIEUSUAT_SX);
			
			  String HIEUSUAT_DG = util.antiSQLInspection(request.getParameter("HIEUSUAT_DG"));
		if (HIEUSUAT_DG == null)
			HIEUSUAT_DG = "";				
		lsxBean.setHIEUSUAT_DG(HIEUSUAT_DG);
		  String TOANCHANG = util.antiSQLInspection(request.getParameter("TOANCHANG"));
			if (TOANCHANG == null)
				TOANCHANG = "";				
			lsxBean.setTOANCHANG(TOANCHANG);
			String ngaybatdausx = util.antiSQLInspection(request.getParameter("ngaybatdausanxuat"));
			if (ngaybatdausx == null)
				ngaybatdausx = "";				
			lsxBean.setNGAYBATDAUSX(ngaybatdausx);	
		String ngayhoanthanh = util.antiSQLInspection(request.getParameter("ngayhoanthanh"));
			if (ngayhoanthanh == null)
				ngayhoanthanh = "";				
			lsxBean.setNGAYHOANTHANH(ngayhoanthanh);	
		
		String songaychamsovoilenh = util.antiSQLInspection(request.getParameter("songaychamsovoilenh"));
			if (songaychamsovoilenh == null)
				songaychamsovoilenh = "";				
			lsxBean.setSONGAYCHAMSOVOILENH(songaychamsovoilenh);	
			
		String soluongnhapkho = util.antiSQLInspection(request.getParameter("soluongnhapkho"));
			if (soluongnhapkho == null)
				soluongnhapkho = "";				
			lsxBean.setSOLUONGNHAPKHO(soluongnhapkho);	
		String soluonglaymau = util.antiSQLInspection(request.getParameter("soluonglaymau"));
			if (soluonglaymau == null)
				soluonglaymau = "";				
			lsxBean.setSOLUONGLAYMAU(soluonglaymau);	
			
			
			
		
		String[] mangvalue = request.getParameterValues("mangvalue_tkl");
		String strmang="";
		if(mangvalue!=null){
			for(int i=0;i<mangvalue.length;i++){
				strmang+=(strmang.length()>0?",":"")+mangvalue[i];
			}
		}
		lsxBean.setStrCheck_ValueTKL(strmang);
		
		dbutils db=new dbutils();
		
		String query="delete LENHSANXUAT_TONGKETLO where lenhsanxuat_fk="+id;
		db.update(query);
		

		query=	"	insert into LENHSANXUAT_TONGKETLO(LENHSANXUAT_FK,SOLUONGNHAPKHO,SOLUONGLAYMAU,HIEUSUAT_SX ,HIEUSUAT_DG  ,	TOANCHANG  ,YKIENKHAC ,NDHL ,PHACHE , " +
				"	CHECKCHON) values " +
				"	 ("+id+","+soluongnhapkho+","+soluonglaymau+",N'"+HIEUSUAT_SX+"',N'"+HIEUSUAT_DG+"',N'"+TOANCHANG+"',N'"+ykienkhac+"',N'"+NDHL+"',N'"+phache+"','"+strmang+"')";
		System.out.println("query: "+query);
		db.update(query);
		
	    query="delete ERP_LENHSANXUAT_TONGKETLO_TIENDO where lenhsanxuat_fk="+id;
		db.update(query);
		query=	"	insert into ERP_LENHSANXUAT_TONGKETLO_TIENDO(LENHSANXUAT_FK ,NGAYBATDAUSX ,NGAYHOANTHANH ,SONGAYCHAMSOVOILENH ,GHICHUTIENDO) values " +
				"	 ("+id+",N'"+ngaybatdausx+"',N'"+ngayhoanthanh+"',N'"+songaychamsovoilenh+"',N'"+ghichutiendo+"')";
		System.out.println("query: "+query);
		db.update(query);

		
		 
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpLenhSanXuatGiayDisplay.jsp";
		lsxBean.init();
		session.setAttribute("lsxBean", lsxBean);
		session.setAttribute("tensudung", "2");
		session.setAttribute("spma", lsxBean.getSpma());
		session.setAttribute("dvkdid", lsxBean.getDvkdId());
		session.setAttribute("kbsxId",lsxBean.getKbsxId());
		try {
			response.sendRedirect(nextJSP);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void CapnhatHoso(String userId, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		IErpLenhsanxuat lsxBean;
		NumberFormat formatter = new DecimalFormat("#######.######");
		
		Utility util = new Utility();	
		String id = util.antiSQLInspection(request.getParameter("id"));
	    if(id == null)
	    {  	
	    	lsxBean = new ErpLenhsanxuat("");
	    }
	    else
	    {
	    	lsxBean = new ErpLenhsanxuat(id);
	    }
	    
	    lsxBean.setUserId(userId);
	    lsxBean.setCongtyId((String) session.getAttribute("congtyId"));
	    
	    
	    String Sohoso = util.antiSQLInspection(request.getParameter("Sohoso"));
		if (Sohoso == null)
			Sohoso = "";				
		lsxBean.setSohoso(Sohoso);
		
	    String dmvt = util.antiSQLInspection(request.getParameter("dmvt"));
		if (dmvt == null)
			dmvt = "";				
		lsxBean.setDmvt(dmvt);
	    String NSX = util.antiSQLInspection(request.getParameter("NSX"));
		if (NSX == null)
			NSX = "";				
		lsxBean.setNSX(NSX);
	    
	    String HD = util.antiSQLInspection(request.getParameter("HD"));
		if (HD == null)
			HD = "";				
		lsxBean.setHD(HD);
		
		String QTSX = util.antiSQLInspection(request.getParameter("qtsx"));
			if (QTSX == null)
				QTSX = "";				
			lsxBean.setQTSX(QTSX);
		String TCKT = util.antiSQLInspection(request.getParameter("tckt"));
				if (TCKT == null)
					TCKT = "";				
				lsxBean.setTCKT(TCKT);
		String QUYETDINHLUUHANH = util.antiSQLInspection(request.getParameter("quyetdinhluuhanh"));
				if (QUYETDINHLUUHANH == null)
					QUYETDINHLUUHANH = "";				
				lsxBean.setQUYETDINHLUUHANH(QUYETDINHLUUHANH);
		
		String soLenhSanXuat = util.antiSQLInspection(request.getParameter("soLenhSanXuat"));
				if (soLenhSanXuat == null)
					soLenhSanXuat = "";				
				lsxBean.setSoLenhSanXuat(soLenhSanXuat);
		String SDK = util.antiSQLInspection(request.getParameter("SDK"));
				if (SDK == null)
					SDK = "";				
				lsxBean.setSDK("SDK");
		String canCuLamLenh = util.antiSQLInspection(request.getParameter("canCuLamLenh"));
				if (canCuLamLenh == null)
					canCuLamLenh = "";				
				lsxBean.setCanCuLamLenh("canCuLamLenh");
				
	
		String[] mangvalue = request.getParameterValues("mangvalue");
		String strmang="";
		if(mangvalue!=null){
			for(int i=0;i<mangvalue.length;i++){
				strmang+=(strmang.length()>0?",":"")+mangvalue[i];
			}
		}
		lsxBean.setStrCheck_Value(strmang);
		
		dbutils db=new dbutils();
		
		String query="delete ERP_LENHSANXUAT_HOSOLO where lenhsanxuat_fk="+id;
		db.update(query);
		query="INSERT INTO ERP_LENHSANXUAT_HOSOLO(LENHSANXUAT_FK ,soLenhSanXuat,SDK,CANCULAMLENH,SOHOSO , dmvt   ,NSX , HD,QTSX,TCKT,QUYETDINHLUUHANH )" +
				" values ("+id+",N'"+soLenhSanXuat+"',N'"+SDK+"',N'"+canCuLamLenh+"',N'"+Sohoso+"',N'"+dmvt+"',N'"+NSX+"',N'"+HD+"',N'"+QTSX+"',N'"+TCKT+"',N'"+QUYETDINHLUUHANH+ "') ";
		System.out.println("CAU QUERY LÀ :"+query);
		
		db.update(query);
		
		query="delete LENHSANXUAT_PHIEUKIEMNGHIEM where lenhsanxuat_fk="+id;
		db.update(query);
 
		String[] SOTT = request.getParameterValues("SOTT_");
		String[] NOIDUNG = request.getParameterValues("NOIDUNG_");
		String[] SOPKN = request.getParameterValues("SOPKN_");
		String[] NGAY = request.getParameterValues("NGAY_");
		String[] KETQUA = request.getParameterValues("KETQUA_");
		String[] GHICHU = request.getParameterValues("GHICHU_");
		
		for(int i=0;i<SOTT.length;i++){
			
			String KETQUA_=request.getParameter("KETQUA_"+i);
			if(KETQUA_==null){
				KETQUA_="0";
			}else{
				KETQUA_="1";
			}
			query="insert into LENHSANXUAT_PHIEUKIEMNGHIEM(LENHSANXUAT_FK ,	SOTT,	NOIDUNG ,	SOPKN ,	NGAY ,	KETQUA  ,	GHICHU ) VALUES " +
					"('"+id+"','"+SOTT[i]+"',N'"+NOIDUNG[i]+"','"+SOPKN[i]+"','"+NGAY[i]+"','"+KETQUA_+"','"+GHICHU[i]+"')";
			db.update(query);
			
		}
		
		 
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpLenhSanXuatGiayDisplay.jsp";
		lsxBean.init();
		session.setAttribute("lsxBean", lsxBean);
		session.setAttribute("tensudung", "2");
		session.setAttribute("spma", lsxBean.getSpma());
		session.setAttribute("dvkdid", lsxBean.getDvkdId());
		session.setAttribute("kbsxId",lsxBean.getKbsxId());
		try {
			response.sendRedirect(nextJSP);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void tieuHaoNL(String userId, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		IErpTieuhaonguyenlieu lsxBean;
		NumberFormat formatter = new DecimalFormat("#######.######");
		
		Utility util = new Utility();	
		String id = util.antiSQLInspection(request.getParameter("id"));
	    if(id == null)
	    {  	
	    	lsxBean = new ErpTieuhaonguyenlieu("");
	    }
	    else
	    {
	    	lsxBean = new ErpTieuhaonguyenlieu(id);
	    }
	    lsxBean.setTieuHaoId(id);
	    
	    lsxBean.setUserId(userId);
	    lsxBean.setCongtyId((String) session.getAttribute("congtyId"));
	    lsxBean.setNppId((String)session.getAttribute("nppId"));
  
	    lsxBean.setCongtyId((String)session.getAttribute("congtyId"));
	    
	    String CdsxId = util.antiSQLInspection(request.getParameter("CdsxId"));
		if (CdsxId == null)
			CdsxId = "";				
		lsxBean.setCDSXId(CdsxId);
		
	    String ngaytieuhao = util.antiSQLInspection(request.getParameter("ngaytieuhao"));
		if (ngaytieuhao == null)
			ngaytieuhao = "";				
		lsxBean.setNgaytieuhao(ngaytieuhao);
 
		String idcongdoan = util.antiSQLInspection(request.getParameter("idcongdoan"));
		if (idcongdoan == null)
			idcongdoan = "";				
		lsxBean.setCongDoanCurrent(idcongdoan);
			
    	String soluongsx = util.antiSQLInspection(request.getParameter("soluongsx"));
		if (soluongsx == null)
			soluongsx = "";				
		lsxBean.setSoluong(soluongsx);
		
		String soluongchuan = util.antiSQLInspection(request.getParameter("soluongchuan"));
		if (soluongchuan == null)
			soluongchuan = "";
		lsxBean.setSoluongchuan(soluongchuan);
		
		
		String loaisanphamTH = util.antiSQLInspection(request.getParameter("loaisanpham"));
		if (loaisanphamTH == null)
			loaisanphamTH = "";
		lsxBean.setLoaisanphamTH(loaisanphamTH);
		
		String khoanmucchiphi = util.antiSQLInspection(request.getParameter("khoanmucchiphi"));
		if (khoanmucchiphi == null)
			khoanmucchiphi = "";
		lsxBean.setKhoanmucchiphi(khoanmucchiphi);
		
		
		String solsx = util.antiSQLInspection(request.getParameter("lsxId"));
		if (solsx == null)
			solsx = "";
		lsxBean.setLsxIds(solsx);
		 
		 
		String[] sophieunhap = request.getParameterValues("sophieunhap");
		 
		String chuoiphieunhap="0";
		if(sophieunhap!=null){
			for(int i=0;i<sophieunhap.length; i++) {
					
				chuoiphieunhap=chuoiphieunhap+","+sophieunhap[i];
			}
		}
		lsxBean.setNhapkhoId(chuoiphieunhap);
		System.out.println("phieu nhap kho "+chuoiphieunhap);
		String khoTieuhao_fk = "";
		String msg = "";
	    String action = request.getParameter("action");
		if(!action.equals("reloadtieuhao")) {
		
			dbutils db=new dbutils();
		 
			String query = " select khosanxuat_fk from erp_lenhsanxuat_congdoan_giay where congdoan_fk="+CdsxId+" and lenhsanxuat_fk="+lsxBean.getLsxIds() ;

			 
			ResultSet rsKho = db.get(query);
			if(rsKho != null)
			{
				try 
				{
					if(rsKho.next())
					{
						khoTieuhao_fk = rsKho.getString("khosanxuat_fk");
					}
					rsKho.close();
				} 
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			String[] idvattu = request.getParameterValues("idvattu");
			String[] mavt = request.getParameterValues("mavattu");
			String[] tenvt = request.getParameterValues("tenvattu");
			String[] donvitinh = request.getParameterValues("donvitinh");
			String[] soluong = request.getParameterValues("soluongDM");
			String[] soluongTHThucte = request.getParameterValues("thucte");
			String[] loai =request.getParameterValues("loai");
			
			List<IDanhmucvattu_SP> spList = new ArrayList<IDanhmucvattu_SP>();
		 
				if(mavt != null)
				{	
					for(int m = 0; m < mavt.length; m++)
					{	
						if(mavt[m] != "")
						{	
							if( idvattu[m].trim().length() >  0)
							{	
								IDanhmucvattu_SP sanpham = null;
								sanpham = new Danhmucvattu_SP();
								sanpham.setIdVT(idvattu[m]);
								
								sanpham.setMaVatTu(mavt[m]);
								sanpham.setTenVatTu(tenvt[m]);
								sanpham.setDvtVT(donvitinh[m]);
								
								sanpham.setSoLuong(soluong[m]);
								sanpham.setCongDoanId(idcongdoan);
								sanpham.setLoai(loai[m]);
								 
									List<ISpDetail> spDetail = new ArrayList<ISpDetail>();
									double totalsoluongnhan=0;
									String[] solo = request.getParameterValues(sanpham.getIdVT ()+".solo");
									String[] soluongton = request.getParameterValues(sanpham.getIdVT()+".soluongton");
									String[] slxuatlo = request.getParameterValues(sanpham.getIdVT()+".soluong");
									String[] khuid = request.getParameterValues(sanpham.getIdVT()+".khuid");
									String[] khuten = request.getParameterValues(sanpham.getIdVT()+".khuvucten");
									String[] donvi = request.getParameterValues(sanpham.getIdVT()+".donvi");
									String[] ngaybatdau = request.getParameterValues(sanpham.getIdVT()+".ngaybatdau1");
									String[] MATHUNG = request.getParameterValues(sanpham.getIdVT()+".MATHUNG");
									String[] MAME = request.getParameterValues(sanpham.getIdVT()+".MAME");
									String[] NGAYNHAPKHO = request.getParameterValues(sanpham.getIdVT()+".NGAYNHAPKHO");
									String[] NGAYHETHAN = request.getParameterValues(sanpham.getIdVT()+".NGAYHETHAN");
									
									String[] MARQ = request.getParameterValues(sanpham.getIdVT()+".MARQ");
									String[] vitriid = request.getParameterValues(sanpham.getIdVT()+".vitriid");
									String[] vitri = request.getParameterValues(sanpham.getIdVT()+".vitri");
									String[] HAMLUONG = request.getParameterValues(sanpham.getIdVT()+".HAMLUONG");
									String[] HAMAM = request.getParameterValues(sanpham.getIdVT()+".HAMAM");
									String[] MAPHIEU = request.getParameterValues(sanpham.getIdVT()+".MAPHIEU");
									String[] PHIEUEO = request.getParameterValues(sanpham.getIdVT()+".PHIEUEO");
									
									String[] MAPHIEUDINHTINH = request.getParameterValues(sanpham.getIdVT()+".MAPHIEUDINHTINH");
									String[] nsx_fk = request.getParameterValues(sanpham.getIdVT()+".nsx_fk");
									String[] NHASANXUAT = request.getParameterValues(sanpham.getIdVT()+".NHASANXUAT");
									
									if(solo!=null){
										for(int k=0;k<solo.length ;k++){
											ISpDetail	spCon = new  SpDetail();
											spCon.setVitri(vitri[k]);
											spCon.setVitriId(vitriid[k]);
											double soluongxuatlo=0;
											try{
											  soluongxuatlo=Double.parseDouble(slxuatlo[k]);
											}catch(Exception err){
											}
											if(soluongton!=null){
												spCon.setSoluongton(soluongton[k]);
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
											spCon.setNgaynhapkho(NGAYNHAPKHO[k].trim());
											spCon.setMathung(MATHUNG[k]);
											spCon.setMame(MAME[k]);
											spCon.setMarq(MARQ[k]);
											  
											spCon.setMaphieu(MAPHIEU[k]);
											spCon.setPHIEUEO(PHIEUEO[k]);
											spCon.setMAPHIEUDINHTINH(MAPHIEUDINHTINH[k]);
											
											spCon.setSoluong(soluongxuatlo+"");
											
											spCon.setDvt(donvi[k]);
											
											spCon.setHamluong(HAMLUONG[k]);
											spCon.setHamam(HAMAM[k]);
											spCon.setNgayhethan(NGAYHETHAN[k]); 
											spCon.setNSX_FK(nsx_fk[k]);
											spCon.setMaNSX(NHASANXUAT[k]);
											
											spDetail.add(spCon);
											  
											totalsoluongnhan=totalsoluongnhan+soluongxuatlo;
											 
										}
									}else{ }
									
									sanpham.setSoLuongTHThucTe(totalsoluongnhan+"");
									sanpham.setSpDetailList(spDetail);
								 
								spList.add(sanpham);
								
								}
								
								 											
							}
						}				
					}
				lsxBean.setListDanhMuc(spList);
		 
			
			if(ngaytieuhao.trim().length() <= 0)
			{
				msg="Vui lòng chọn ngày tiêu hao";
			}
			db.shutDown();
			lsxBean.setMsg(msg);
		}	
 
	    if(action.equals("save"))
		{	
	    	boolean bien=false;
	    	if(id !=null && id.length()>0){
	    		bien=lsxBean.update_tieuhao();
	    	}else{
	    		bien=lsxBean.tieuhaoLsx();
	    	}
	    	
			if(!bien)
			{
				lsxBean.createLoaisanphamRs();
				session.setAttribute("lsxBean", lsxBean);
				session.setAttribute("nhamayid", lsxBean.getNhamayId());
				lsxBean.CreateRs_tieuhao();
				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpLenhSanXuatGiayTieuHao.jsp";
				response.sendRedirect(nextJSP);
			}
			else
			{
				 IErpTieuhaonguyenlieulist obj = new ErpTieuhaonguyenlieuList();
				 
				    obj.SetCtyId((String)session.getAttribute("congtyId"));
					obj.setNppId((String)session.getAttribute("nppId"));
					obj.setUserId(userId);
			    	obj.Init("");
			    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
			    	session.setAttribute("obj", obj);
 
		    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpTieuHaoNguyenLieuGiay.jsp");	
			}
		} 
	    else{
			lsxBean.CreateRs_tieuhao();
			if(action.equals("reloadtieuhao")){
				lsxBean.init_tieuhao_lsx();
			}
			session.setAttribute("lsxBean", lsxBean);
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpLenhSanXuatGiayTieuHao.jsp";
			response.sendRedirect(nextJSP);
		}
	    
	}
 

}
