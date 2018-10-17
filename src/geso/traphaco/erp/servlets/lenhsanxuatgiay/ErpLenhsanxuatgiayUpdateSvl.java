package geso.traphaco.erp.servlets.lenhsanxuatgiay;
 
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.Utility;	
import geso.traphaco.erp.beans.danhmucvattu.IDanhmucvattu_SP;
import geso.traphaco.erp.beans.danhmucvattu.imp.Danhmucvattu_SP;
import geso.traphaco.erp.beans.kiemdinhchatluong.giay.IErpKiemdinhchatluong;
import geso.traphaco.erp.beans.kiemdinhchatluong.giay.imp.ErpKiemdinhchatluong;
import geso.traphaco.erp.beans.lapkehoach.IErpDinhmuc;
import geso.traphaco.erp.beans.lapkehoach.imp.ErpDinhmuc;
import geso.traphaco.erp.beans.lenhsanxuatgiay.IErpLenhsanxuat;
import geso.traphaco.erp.beans.lenhsanxuatgiay.IErpLenhsanxuatList;
import geso.traphaco.erp.beans.lenhsanxuatgiay.ILenhSXCongDoan;
import geso.traphaco.erp.beans.lenhsanxuatgiay.ISanPhamSanXuat;
import geso.traphaco.erp.beans.lenhsanxuatgiay.ISpSanxuatChitiet;
import geso.traphaco.erp.beans.lenhsanxuatgiay.imp.ErpLenhsanxuat;
import geso.traphaco.erp.beans.lenhsanxuatgiay.imp.ErpLenhsanxuatList;
import geso.traphaco.erp.beans.lenhsanxuatgiay.imp.LenhSXCongDoan;
import geso.traphaco.erp.beans.lenhsanxuatgiay.imp.SanPhamSanXuat;
import geso.traphaco.erp.beans.lenhsanxuatgiay.imp.SpSanxuatChitiet;
import geso.traphaco.erp.beans.nhapkho.giay.IErpNhapKhoLsx;
import geso.traphaco.erp.beans.nhapkho.giay.imp.ErpNhapKhoLsx;
 
import geso.traphaco.erp.servlets.mauinlenhsanxuat.ErpGiaiDoanCanChiaMauA1;
import groovyjarjarasm.asm.commons.Method;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
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

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;



public class ErpLenhsanxuatgiayUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

 
	
	public ErpLenhsanxuatgiayUpdateSvl()
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
		if (!cutil.check(userId, userTen, sum))
		{
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		} else
		{
			session.setMaxInactiveInterval(30000);
			 OutputStream out = response.getOutputStream();
			Utility util = new Utility();
			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);
			if(userId.length() == 0)
				userId = util.antiSQLInspection(request.getParameter("userId"));
			String id = util.getId(querystring);
			IErpLenhsanxuat lsxBean = new ErpLenhsanxuat(id);
			lsxBean.setCongtyId((String) session.getAttribute("congtyId"));
			lsxBean.setUserId(userId); // phai co UserId truoc khi Init
			lsxBean.setNppId((String)session.getAttribute("nppId"));
			
			String nextJSP;
			if (request.getQueryString().indexOf("display") >= 0)
			{
				lsxBean.init();
				lsxBean.Init_congdoan();
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpLenhSanXuatGiayDisplay.jsp";
			} else
			{
				lsxBean.init();
			
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpLenhSanXuatGiayNew.jsp";
			}
			
			session.setAttribute("lsxBean", lsxBean);
			session.setAttribute("tensudung", "2");
			session.setAttribute("spma", lsxBean.getSpma());
			session.setAttribute("dvkdid", lsxBean.getDvkdId());
			session.setAttribute("kbsxId",lsxBean.getKbsxId());
			response.sendRedirect(nextJSP);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		 OutputStream out = response.getOutputStream();
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");

		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if (!cutil.check(userId, userTen, sum))
		
		{
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		} else
		{
			
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			session.setMaxInactiveInterval(30000);

			 
			IErpLenhsanxuat lsxBean;

			Utility util = new Utility();
			String id = util.antiSQLInspection(request.getParameter("id"));
			if (id == null)
			{
				lsxBean = new ErpLenhsanxuat("");
			} else
			{
				lsxBean = new ErpLenhsanxuat(id);
			}

			lsxBean.setCongtyId((String) session.getAttribute("congtyId"));
			lsxBean.setNppId((String) session.getAttribute("nppId"));
			
			lsxBean.setUserId(userId);

			String PO = util.antiSQLInspection(request.getParameter("PO"));
			if (PO == null)
				PO = "";
			lsxBean.setPlaintOrder(PO);
			
			String solsxnew = util.antiSQLInspection(request.getParameter("solsxnew"));
			if(solsxnew == null)
				solsxnew = "";
			lsxBean.setSolsxNew(solsxnew);

			String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
			if (trangthai == null)
				trangthai = "0";
			lsxBean.setTrangthai(trangthai);
			
			String dvkdid = util.antiSQLInspection(request.getParameter("dvkdid"));
			if (dvkdid == null)
				dvkdid = "0";
			lsxBean.setDvkdId(dvkdid);
			session.setAttribute("dvkdid", dvkdid);
			
			
			String cdidcurrent = util.antiSQLInspection(request.getParameter("cdidcurrent"));
			if (cdidcurrent == null)
				cdidcurrent = "";
			lsxBean.setCongDoanCurrent(cdidcurrent);
			
			String soluongme = util.antiSQLInspection(request.getParameter("soluongme"));
			if (soluongme == null)
				soluongme = "0";
			lsxBean.setSome(soluongme);
			
			
			//System.out.println("congdoanCurrent: "+cdidcurrent);
			
			String ngaysanxuat = util.antiSQLInspection(request.getParameter("ngaybatdau"));
			if (ngaysanxuat == null)
				ngaysanxuat = getDateTime();
			lsxBean.setNgaytao(ngaysanxuat);
 
			String ngaydukien = util.antiSQLInspection(request.getParameter("ngayketthuc"));
			if (ngaydukien == null)
				ngaydukien = "";
			lsxBean.setNgaydukien(ngaydukien);
			 
			String ghichu = util.antiSQLInspection(request.getParameter("ghichu"));
			if (ghichu == null)
				ghichu = "";
			lsxBean.setghichu(ghichu);
			
			String khottid = util.antiSQLInspection(request.getParameter("khottid"));
			if (khottid == null)
				khottid = "";
			lsxBean.setKhottId(khottid);
			session.setAttribute("khottid", khottid);
			
			 
			
			String spid = util.antiSQLInspection(request.getParameter("spid"));
			if (spid == null)
				spid = "";
			lsxBean.setSpId(spid);
 
			String khosxid = util.antiSQLInspection(request.getParameter("khosxid"));
			if (khosxid == null)
				khosxid = "";
			lsxBean.setKhoSXId(khosxid);
			
			
			
			String kbsxIds = util.antiSQLInspection(request.getParameter("kbsxIds"));
			if (kbsxIds == null)
				kbsxIds = "";
			lsxBean.setKbsxId(kbsxIds);
			session.setAttribute("kbsxId", kbsxIds);

			String nhamayId = util.antiSQLInspection(request.getParameter("nhamayid"));
			if (nhamayId == null)
				nhamayId = "";
			lsxBean.setNhamayId(nhamayId);

			String soluongsx = util.antiSQLInspection(request.getParameter("soluongsx"));
			if (soluongsx == null)
				soluongsx = "";
			lsxBean.setSoluong(soluongsx);
			
			
			String lohoiId = util.antiSQLInspection(request.getParameter("lohoiId"));
			if(lohoiId==null)
				lohoiId="";
			lsxBean.setLohoiId(lohoiId);
			
			
			String islsxcongnghe = util.antiSQLInspection(request.getParameter("islsxcongnghe"));
			if(islsxcongnghe==null)
				islsxcongnghe="0";
			lsxBean.setIsLsxCongNghe(islsxcongnghe);
			
			lsxBean.setIsLsxGiacong(lsxBean.getIsGiacong());
			
			
			/*PhatNguyen
			int soCongDoan = Integer.parseInt((String)request.getParameter("soCongDoan"));*/
			 
			String[] lsxcdid = request.getParameterValues("lsxcdid");
			String[] lsxcdthutu = request.getParameterValues("lsxcdthutu");
			String[] lsxcdphanxuong = request.getParameterValues("lsxcdphanxuong");
			String[] lsxcddiengiai = request.getParameterValues("lsxcddiengiai");
			String[] lsxcdsanpham = request.getParameterValues("lsxcdsanpham");
			String[] lsxcdsoluong = request.getParameterValues("lsxcdsoluong");
			String[] lsxcdkiemdinhcl = request.getParameterValues("lsxcdkiemdinhcl");
			String[] lsxcdnhamayid = request.getParameterValues("lsxcdnhamayid");
			String[] lsxcdbomid = request.getParameterValues("lsxcdBomId");
			String[] lsxcdBomTen = request.getParameterValues("lsxcdBomTen");
			
			String[] lsxcdspid = request.getParameterValues("lsxcdspid");
			String[] lsxcdspmasp = request.getParameterValues("lsxcdspmasp");
			
			String[] lsxcdKhosxId = request.getParameterValues("lsxcdKhosxId");
			String[] lsxcdngayyeucauthem = request.getParameterValues("lsxcdngayyeucauthem");
			
		 
			List<ILenhSXCongDoan> ListCongdoan = new ArrayList<ILenhSXCongDoan>();
			if (lsxcdid != null)
			{
				for (int i = 0; i < lsxcdid.length; i++)
				{
					String soluongnhap_String = "_soluongNhap_tab_"+i;
					String soluongxuat_String = "_soluongXuat_tab_"+i;
					String soluongdupham_String = "_soluongDuPham_tab_"+i;
					String soluongphepham_String = "_soluongPhePham_tab_"+i;
					//Lấy số lượng theo công đoạn
					String[] str_soluongNhap_tab_i = request.getParameterValues(soluongnhap_String);
					String[] str_soluongXuat_tab_i = request.getParameterValues(soluongxuat_String);
					String[] str_soluongDuPham_tab_i = request.getParameterValues(soluongdupham_String);
					String[] str_soluongPhePham_tab_i = request.getParameterValues(soluongphepham_String);
					
					ArrayList<String> soluongNhap_tab_i = toArrayList(str_soluongNhap_tab_i);
					ArrayList<String> soluongXuat_tab_i = toArrayList(str_soluongXuat_tab_i);
					ArrayList<String> soluongDuPham_tab_i = toArrayList(str_soluongDuPham_tab_i);
					ArrayList<String> soluongPhePham_tab_i = toArrayList(str_soluongPhePham_tab_i);
					
					
					System.out.println("Length of soluongNhap_tab_i :" +soluongNhap_tab_i.size());
					
					//end
					
					
					ILenhSXCongDoan cd = new LenhSXCongDoan();
					cd.setCongDoanId(lsxcdid[i]);
					cd.setPhanXuong(lsxcdphanxuong[i]);
					cd.setDiengiai(lsxcddiengiai[i]);
					cd.setSanPham(lsxcdsanpham[i]);
					cd.setSoLuong(lsxcdsoluong[i]);
					cd.SetKiemDinhCL(lsxcdkiemdinhcl[i]);
					cd.setNhaMayId(lsxcdnhamayid[i]);
					cd.setBomId(lsxcdbomid[i]);
					cd.setSpId(lsxcdspid[i]);
					cd.setThuTu(Float.parseFloat(lsxcdthutu[i]));
					cd.setMaSp(lsxcdspmasp[i]);
					cd.setBomTen(lsxcdBomTen[i]);
					
					cd.setKhoSXId(lsxcdKhosxId[i]);
					
					cd.setNGayYcThem(lsxcdngayyeucauthem[i]);
					
					
					///lưu các thông số theo công đoạn cái đã
					cd.setSoluongNhap(soluongNhap_tab_i);
					cd.setSoluongXuat(soluongXuat_tab_i);
					cd.setSoluongDuPham(soluongDuPham_tab_i);
					cd.setSoluongPhePham(soluongPhePham_tab_i);
					cd.setKhosxRs(lsxBean.getRskho(cd));
					
					
					//end
					 
					ListCongdoan.add(cd);

					if (request.getParameter("check" + lsxcdid[i]) != null)
					{
						cd.setTrangthai("1");
					} else
					{
						cd.setTrangthai("0");
					}
					if (request.getParameter("checkactive" + lsxcdid[i]) != null)
					{
						cd.setActive("1");
					} else
					{
						cd.setActive("0");
					}
					
					this.get_List_Danhsachvattu(lsxBean,cd,request,i);
					

				}
			}
			//System.out.println("Size nek : " + ListCongdoan.size());
			lsxBean.SetListCongDoan(ListCongdoan);
			 
			//DOI VOI SAN PHAM DE NGHI CUA LOI

			
			///lsxBean.setListDanhMuc(dmvt);
			
			String chophepTT = util.antiSQLInspection(request.getParameter("chophepTT"));
			if (chophepTT == null)
				chophepTT = "0";
			lsxBean.setChophepTT(chophepTT);
			
			String viewBom = util.antiSQLInspection(request.getParameter("viewBom"));
			if (viewBom == null)
				viewBom = "0";
			lsxBean.setViewBom(viewBom);
		 
			
			String ngayycthem = util.antiSQLInspection(request.getParameter("ngayycthem"));
			if(ngayycthem==null)
				ngayycthem="";
			lsxBean.setNgayYCThenNL(ngayycthem);
			 
			System.out.println("soluongsx : "+soluongsx);
			
		    
			String[] dmId = request.getParameterValues("dmid");
			String[] dmSoluong = request.getParameterValues("dmsoluong");
			List<IErpDinhmuc> dmlst = new ArrayList<IErpDinhmuc>();
			if(dmId != null){ //quan tâm đến thành tiền
				for(int i=0; i<dmId.length;i++){
					IErpDinhmuc dm = new ErpDinhmuc();
					if(dmSoluong[i].replace(",", "") != "" && Double.parseDouble(dmSoluong[i].replace(",", "")) > 0){
						dm.setId(dmId[i]);
						dm.setSoluong(Double.parseDouble(dmSoluong[i].replace(",", "")));
						dmlst.add(dm);
					}
				}
			}
			lsxBean.setDinhmucList(dmlst);
			 
			String action = request.getParameter("action");
			 
			  
			if (action.equals("save") || action.equals("Save_NLCongDoan") )
			{
				
				boolean bien;
				
				if (id == null || id.length()==0 )
				{
					bien = lsxBean.createLsx() ;
				 
				} else
				{
					bien = lsxBean.updateLsx() ;
					
					if(bien && action.equals("Save_NLCongDoan")){
						bien= lsxBean.update_CongDoan();
					}
				}
		 
				if (!bien || action.equals("Save_NLCongDoan"))
				{
					lsxBean.createRs();
					
					session.setAttribute("lsxBean", lsxBean);
					String nextJSP = "/TraphacoHYERP/pages/Erp/ErpLenhSanXuatGiayNew.jsp";
					response.sendRedirect(nextJSP);
				} else
				{
					IErpLenhsanxuatList obj = new ErpLenhsanxuatList();
					obj.setCongtyId((String) session.getAttribute("congtyId"));
					obj.setNppId((String) session.getAttribute("nppId"));
					
					obj.setUserId(userId);
					obj.init("");
					session.setAttribute("obj", obj);
					session.setAttribute("userId", userId);

					response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpLenhSanXuatGiay.jsp");
				 }
				 
			} 
			 else if(action.equals("LuuCongDoanNhapXuat")){
				
				if (!lsxBean.LuuCongDoanNhapXuat())
				{
					lsxBean.createRs();
					
					session.setAttribute("lsxBean", lsxBean);
					String nextJSP = "/TraphacoHYERP/pages/Erp/ErpLenhSanXuatGiayNew.jsp";
					response.sendRedirect(nextJSP);
				} else
				{
					IErpLenhsanxuatList obj = new ErpLenhsanxuatList();
					obj.setCongtyId((String) session.getAttribute("congtyId"));
					obj.setNppId((String) session.getAttribute("nppId"));
					
					obj.setUserId(userId);
					obj.init("");
					session.setAttribute("obj", obj);
					session.setAttribute("userId", userId);

					response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpLenhSanXuatGiay.jsp");
				}
			}
		     
			else if ( action.equals("Yeucau_NLCongDoan")){
				
				lsxBean.createRs();
				boolean bien= lsxBean.yeucauNguyenlieu_TheoCongdoan();
				if(bien){
					// nếu yêu cầu thành công thì reload lại
					lsxBean.ReLoad_CongDoan(true);
					lsxBean.setMsg("Đã tạo yêu cầu nguyên liệu thành công");
				}
				session.setAttribute("lsxBean", lsxBean);
				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpLenhSanXuatGiayNew.jsp";
				response.sendRedirect(nextJSP);
			}else if(action.equals("PrintThongtinSX")){
				System.out.println("=============================== In Thông Tin chung");
				this.PrintThongtinchungSx(response,id,cdidcurrent,out,request);
			}
			else if(action.equals("PrintLsxNangcao")){
				//vao day
				System.out.println("PrintLsxNangcao");
				
				this.PrintLsxNangcao(response,id,cdidcurrent,out,request);
				
			}
		
			else
			{
				lsxBean.createRs();
				if (action.equals("Reload_NLCongDoan") || action.equals("view_NLCongDoan") ){
					boolean is_reload=true;
					if(!action.equals("Reload_NLCongDoan")){
						is_reload=false;
					}
					lsxBean.ReLoad_CongDoan(is_reload);
					 
				}else{
					lsxBean.ChangeSpOrKichBan();
				}
				
				// reload kịch bản
			  
				String task = request.getParameter("actionTask");
				if (task == null)
					task = "";
				System.out.println("action task: " + task);

				session.setAttribute("lsxBean", lsxBean);

				String nextJSP = "";

			 	nextJSP = "/TraphacoHYERP/pages/Erp/ErpLenhSanXuatGiayNew.jsp";
 
				response.sendRedirect(nextJSP);
			}
				
			
		}
	}

	 

	private void PrintThongtinchungSx(HttpServletResponse response, String id,String cdidcurrent, OutputStream out, HttpServletRequest request) {
		try
	    {
			response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "attachment; filename=ThongTinChungSX.xlsm");
	        
			FileInputStream fstream = null;
			Workbook workbook = new Workbook();		
			 
			fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\FORM_IN_LSX_NANGCAO_V1.xlsm");
			 
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
	    
			
		    CreateThongTinChungSX(workbook,id, cdidcurrent);
		     //Saving the Excel file
		     workbook.save(out);
		     
		     fstream.close();
	    }
	    catch (Exception ex)
	    {
	    	ex.printStackTrace();
	       
	    }
		
	}
	private void CreateThongTinChungSX(Workbook workbook,  String id, String cdidcurrent) throws InstantiationException, IllegalAccessException 
	{
		try {
			String pathImg=getServletContext().getInitParameter("pathPDF");
			
			Worksheets worksheets = workbook.getWorksheets();
		    Worksheet worksheet = worksheets.getSheet(0);
		    Cells cells = worksheet.getCells();
		    /*Fix lại trang in */
		    worksheet.autoFitColumns();
		    worksheet.autoFitRows();
		    worksheet.getPageSetup().setZoom(100);
			dbutils db = new dbutils();
			
			Object dongbatdau=0;
			int sodonghientai=0;
			
			// ve header
			sodonghientai=createHeader(0,id, cdidcurrent, "0",   db,  worksheet,  cells,   pathImg);
			System.out.println(" dong tu header: "+ sodonghientai);
			sodonghientai++;
			String gdid="";
			String tengd="";
			String str = "ErpThongTinChungSX";    
				try {
					Class cls = Class .forName("geso.traphaco.erp.servlets.mauinlenhsanxuat."+str );
					Object obj= cls.newInstance();
		        	Class<?>[] paramTypes = {int.class, String.class, String.class, String.class, String.class,dbutils.class,  Worksheet.class, Cells.class ,String.class};
					java.lang.reflect.Method getCreate;
					// ten ham 
					getCreate = obj.getClass().getMethod("Create", paramTypes);
					dongbatdau=getCreate.invoke(obj, sodonghientai, id,cdidcurrent,  gdid,tengd,  db,  worksheet, cells,pathImg); 
				} catch ( NoSuchMethodException |SecurityException | IllegalArgumentException | InvocationTargetException | ClassNotFoundException e) {
					e.printStackTrace();
				}
			
			sodonghientai=Integer.parseInt(dongbatdau.toString());
			db.shutDown();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	

	private void PrintLsxNangcao(HttpServletResponse response, String id, String cdidcurrent, OutputStream out, HttpServletRequest request)  {
		// TODO Auto-generated method stub

		try
	    {
			response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "attachment; filename=DanhSachLenhSanXuat.xlsm");
	        
			FileInputStream fstream = null;
			Workbook workbook = new Workbook();		
			 
			fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\FORM_IN_LSX_NANGCAO_V1.xlsm");
			 
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
	    
			
		    CreateStaticData(workbook,id, cdidcurrent);
		    //CreateStaticData1(workbook,id, cdidcurrent);
		     
		
		     //Saving the Excel file
		     workbook.save(out);
		     
		     fstream.close();
	    }
	    catch (Exception ex)
	    {
	    	ex.printStackTrace();
	       
	    }
	
	}
	
	private int createHeader(int rowstart, String lsxid,String cdidcurrent, String gdid,  dbutils db, Worksheet worksheet, Cells cells,  String pathImg){
		Object dongbatdau=0;
		int sodonghientai=0;
		try {
			
			// ten class
			String str = "ErpHeaderMauIn";    
			try {
				Class cls = Class .forName("geso.traphaco.erp.servlets.mauinlenhsanxuat."+str );
				Object obj= cls.newInstance();
	        	Class<?>[] paramTypes = {int.class,String.class, String.class, String.class, String.class,dbutils.class,  Worksheet.class, Cells.class ,String.class};
				java.lang.reflect.Method getCreate;
				// ten ham 
				getCreate = obj.getClass().getMethod("Create", paramTypes);
				System.out.println("path pathImg: "+pathImg );
				dongbatdau=getCreate.invoke(obj, 0,lsxid, cdidcurrent ,"0","header",  db,  worksheet, cells,pathImg); 
			} catch ( NoSuchMethodException |SecurityException | IllegalArgumentException | InvocationTargetException | ClassNotFoundException e) {
				e.printStackTrace();
			}
			
			sodonghientai=Integer.parseInt(dongbatdau.toString()) ;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return sodonghientai;
	}
	

	private void CreateStaticData1(Workbook workbook,  String id, String cdidcurrent) throws InstantiationException, IllegalAccessException 
	{
		try {
			cdidcurrent="100096";
			System.out.println("=============================cdidcurrent:"+cdidcurrent);
			/*String pathImg=getServletContext().getInitParameter("pathImg_Apose");*/
			String pathImg=getServletContext().getInitParameter("pathPDF");
			
			Worksheets worksheets = workbook.getWorksheets();
		    Worksheet worksheet = worksheets.getSheet(0);
		    Cells cells = worksheet.getCells();
		    /*Fix lại trang in */
		    worksheet.autoFitColumns();
		    worksheet.autoFitRows();
		    worksheet.getPageSetup().setZoom(100);
			dbutils db = new dbutils();
			 
			
			//this.create_In_GiaiDoan(i,gdid,tengd,db,some,worksheet, cells ,socot) ;
			/*ErpGiaiDoanCanChiaMauA1 obj= new ErpGiaiDoanCanChiaMauA1();
			obj.Create(i, gdid, tengd, db, some, worksheet, cells, socot);*/
			
			
			Object dongbatdau=0;
			int sodonghientai=0;
			
			// ve header
			sodonghientai=createHeader(0,id, cdidcurrent, "0",   db,  worksheet,  cells,   pathImg);
			System.out.println(" dong tu header: "+ sodonghientai);
			sodonghientai++;
			
			// ve tug giai doan
			String query="select GiaiDoan_FK,GD.TEN, isnull(MAU.CLASS,'')  as maugiadoan from Erp_CongDoanSanXuat_GIAIDOAN_Giay  CD inner join ERP_GIAIDOANSX GD ON CD.GiaiDoan_FK=GD.PK_SEQ "+
					" \n LEFT JOIN LOAIMAUIN_SANXUAT MAU ON MAU.PK_SEQ= GD.LOAIMAUINSX_FK  "+
					" \n where CD.CongDoanSanXuat_FK="+ cdidcurrent+" and CD.GiaiDoan_FK is not null and CD.GiaiDoan_FK > 0";
			System.out.println(" lay giai doan: "+ query);
			ResultSet rs= db.get(query);
			if(rs!= null){
				while(rs.next())
				{
					String maugiadoan = rs.getString("maugiadoan");
					String gdid = rs.getString("GiaiDoan_FK");
					String tengd= rs.getString("TEN");
					
					//String str = "ErpGiaiDoanCanChiaMauA1";    
					if(maugiadoan.trim().length()>0){
						try {
							Class cls = Class .forName("geso.traphaco.erp.servlets.mauinlenhsanxuat."+maugiadoan );
							Object obj= cls.newInstance();
				        	Class<?>[] paramTypes = {int.class, String.class, String.class, String.class, String.class,dbutils.class,  Worksheet.class, Cells.class ,String.class};
							java.lang.reflect.Method getCreate;
							// ten ham 
							getCreate = obj.getClass().getMethod("Create", paramTypes);
							dongbatdau=getCreate.invoke(obj, sodonghientai, id,cdidcurrent,  gdid,tengd,  db,  worksheet, cells,pathImg); 
						} catch ( NoSuchMethodException |SecurityException | IllegalArgumentException | InvocationTargetException | ClassNotFoundException e) {
							e.printStackTrace();
						}
					}
					
					sodonghientai=Integer.parseInt(dongbatdau.toString()) ;
					System.out.println("so dong gia doan : "+tengd +" : " + sodonghientai);
				}
			}
		
			db.shutDown();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	 
	}
	
	
	private void CreateStaticData(Workbook workbook,  String id, String cdidcurrent) throws InstantiationException, IllegalAccessException 
	{
		try {
			System.out.println("=============================cdidcurrent:"+cdidcurrent);
			/*String pathImg=getServletContext().getInitParameter("pathImg_Apose");*/
			String pathImg=getServletContext().getInitParameter("pathPDF");
			
			Worksheets worksheets = workbook.getWorksheets();
		    Worksheet worksheet = worksheets.getSheet(0);
		    Cells cells = worksheet.getCells();
		    /*Fix lại trang in */
		    worksheet.autoFitColumns();
		    worksheet.autoFitRows();
		    worksheet.getPageSetup().setZoom(100);
			dbutils db = new dbutils();
			 
			/*
			//this.create_In_GiaiDoan(i,gdid,tengd,db,some,worksheet, cells ,socot) ;
			ErpGiaiDoanCanChiaMauA1 obj= new ErpGiaiDoanCanChiaMauA1();
			obj.Create(i, gdid, tengd, db, some, worksheet, cells, socot);*/
			
			
			Object dongbatdau=0;
			int sodonghientai=0;
			
			// ve header
			sodonghientai=createHeader(0,id, cdidcurrent, "0",   db,  worksheet,  cells,   pathImg);
			System.out.println(" dong tu header: "+ sodonghientai);
			sodonghientai++;
			
			// ve tug giai doan
			String query="select GiaiDoan_FK,GD.TEN, isnull(MAU.CLASS,'')  as maugiadoan from Erp_CongDoanSanXuat_GIAIDOAN_Giay  CD inner join ERP_GIAIDOANSX GD ON CD.GiaiDoan_FK=GD.PK_SEQ "+
					" \n LEFT JOIN LOAIMAUIN_SANXUAT MAU ON MAU.PK_SEQ= GD.LOAIMAUINSX_FK  "+
					" \n where CD.CongDoanSanXuat_FK="+ cdidcurrent+" and CD.GiaiDoan_FK is not null and CD.GiaiDoan_FK > 0";
			System.out.println(" lay giai doan: "+ query);
			ResultSet rs= db.get(query);
			if(rs!= null){
				while(rs.next())
				{
					String maugiadoan = rs.getString("maugiadoan");
					String gdid = rs.getString("GiaiDoan_FK");
					String tengd= rs.getString("TEN");
					
					//String str = "ErpGiaiDoanCanChiaMauA1";    
					if(maugiadoan.trim().length()>0){
						try {
							Class cls = Class .forName("geso.traphaco.erp.servlets.mauinlenhsanxuat."+maugiadoan );
							Object obj= cls.newInstance();
				        	Class<?>[] paramTypes = {int.class, String.class, String.class, String.class, String.class,dbutils.class,  Worksheet.class, Cells.class ,String.class};
							java.lang.reflect.Method getCreate;
							// ten ham 
							getCreate = obj.getClass().getMethod("Create", paramTypes);
							dongbatdau=getCreate.invoke(obj, sodonghientai, id,cdidcurrent,  gdid,tengd,  db,  worksheet, cells,pathImg); 
						} catch ( NoSuchMethodException |SecurityException | IllegalArgumentException | InvocationTargetException | ClassNotFoundException e) {
							e.printStackTrace();
						}
					}
					
					sodonghientai=Integer.parseInt(dongbatdau.toString()) ;
					System.out.println("so dong gia doan : "+tengd +" : " + sodonghientai);
				}
			}
		
			db.shutDown();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	 
	}
	
	public static void main (String [] a){
	}
	
	private void create_In_GiaiDoan(int i, String gdid, String tengd,
			dbutils db, int some, Worksheet worksheet, Cells cells ,int socot) {
		// TODO Auto-generated method stub
		
		
		ReportAPI.mergeCells(worksheet, i, i, 0, socot);
		Cell cell = cells.getCell("A" + Integer.toString(i+1));
		cell.setValue(tengd);
		i++;
		
		ReportAPI.mergeCells(worksheet, i, i+1, 0, 4);
		  cell = cells.getCell("A" + Integer.toString(i+1));
		cell.setValue("Tên nguyên liệu");
		
		ReportAPI.mergeCells(worksheet, i, i+1, 5,6);
		  cell = cells.getCell(i , 5);
		cell.setValue("Đơn vị tính");
		ReportAPI.mergeCells(worksheet, i, i+1, 7,14);
		  cell = cells.getCell("H" + Integer.toString(i+1));
		cell.setValue("KL yêu cầu");
		ReportAPI.mergeCells(worksheet, i, i,15,30);
		  cell = cells.getCell("P" + Integer.toString(i+1));
		cell.setValue("KL thực tế");
		
		// in số mẻ
		// 16 là số cột để cho phần số mẻ
		int tongso_cot= 16;
		
		 String mang[] =this.getMangPhanBoMe(some ,tongso_cot);
		 int cotdautien=15;
		 for(int k=0;k<mang.length ;k++){
			 ReportAPI.mergeCells(worksheet, i+1, i+1,cotdautien,cotdautien +Integer.parseInt(mang[k])-1);
			 cell = cells.getCell(i+1,cotdautien);
			 cell.setValue("Mẫu "+ (k+1));
			
			cotdautien=cotdautien + Integer.parseInt(mang[k]);
			
		 }
		
		ReportAPI.mergeCells(worksheet, i, i+1,31,35);
		  cell = cells.getCell(i,31);
		cell.setValue("Loại TB");
		
		ReportAPI.mergeCells(worksheet, i, i+1,36,40);
		  cell = cells.getCell(i,36);
		cell.setValue("Mã TB");
		
		ReportAPI.mergeCells(worksheet, i, i+1,41,43);
		  cell = cells.getCell(i,41);
		cell.setValue("TB Khác");
			
	}
	
	

	
	private String[] getMangPhanBoMe(int some, int tongso_cot) {
		// TODO Auto-generated method stub
		
		String[] mang=new String[some];
		
		System.out.println("tongso_cot: "+tongso_cot);
		int so_o_merce=tongso_cot/some;
		System.out.println("so_o_merce: "+so_o_merce);
		int so_o_du= tongso_cot- so_o_merce* some;
		System.out.println("so_o_du :" +so_o_du); 
		
		for(int i=0;i<mang.length;i++){
			if(so_o_du >0){
					mang[i]= (so_o_merce +1)+"";
					so_o_du--;
			}else {
				mang[i]= (so_o_merce)+"";
			}
		}
		return mang;
		
	}

	private void get_List_Danhsachvattu(IErpLenhsanxuat lsxBean,
			ILenhSXCongDoan cd, HttpServletRequest request,int i) {
		// TODO Auto-generated method stub
		try{
			
			List<IDanhmucvattu_SP> dmvt = new ArrayList<IDanhmucvattu_SP>();
			
			String[] bomid1 = request.getParameterValues("bomid1_"+i);
			String[] congdoanid1 = request.getParameterValues("congdoanid1_"+i);
			String[] congdoanten1 = request.getParameterValues("congdoanten1_"+i);
		
			String[] mavattu1 = request.getParameterValues("mavattu1_"+i);
			String[] idvt = request.getParameterValues("idvt1_"+i);
			String[] tenvattu1 = request.getParameterValues("tenvattu1_"+i);
			String[] donvitinh1 = request.getParameterValues("donvitinh1_"+i);
			String[] kho1 = request.getParameterValues("kho1_"+i);
			 
			String[] soluongchuan = request.getParameterValues("soluongchuan_"+i);
			String[] soluongdayc = request.getParameterValues("soluongdayc_"+i);
			String[] soluong_ = request.getParameterValues("soluong_"+i);
			
			
			String[] isTINHHAMAM = request.getParameterValues("isTINHHAMAM_"+i);
			String[] isTINHHAMLUONG = request.getParameterValues("isTINHHAMLUONG_"+i);
			
			String[] HAMLUONG = request.getParameterValues("hamluong_"+i);
			String[] HAMAM = request.getParameterValues("hamam_"+i);
			
		
			
			
			String[] chon_ = request.getParameterValues("chon__"+i);
			
			
			String[] IsNLTieuHao =request.getParameterValues("IsNLTieuHao_"+i);
			
			if (bomid1 != null)
			{
				 System.out.println("bomleng "+bomid1.length);
				for (int m = 0; m < bomid1.length; m++)
				{
					IDanhmucvattu_SP spvt = new Danhmucvattu_SP();
					spvt.setMaVatTu(mavattu1[m]);
					spvt.setBomId(bomid1[m]);
					spvt.setChon(chon_[m]);
					
					spvt.setCongDoanId(congdoanid1[m]);
					spvt.setTenCongDoan(congdoanten1[m]);
					spvt.setIdVT(idvt[m]);
					spvt.setTenVatTu(tenvattu1[m]);
					spvt.setDvtVT(donvitinh1[m]);
					spvt.SetKhoid(kho1[m]);
					spvt.setIsNLTieuHao(IsNLTieuHao[m]);
					spvt.setSoLuong(soluong_[m].replaceAll(",",""));
					spvt.setSoLuongChuan(soluongchuan[m].replaceAll(",",""));
					spvt.setSoluongDaYC(soluongdayc[m].replaceAll(",",""));
					
					
					spvt.setIsTinhHA(isTINHHAMAM[m]);
					spvt.setIsTinhHL(isTINHHAMLUONG[m]);
					spvt.setHamam(HAMAM[m]);
					spvt.setHamluong(HAMLUONG[m]);
					
					String[] _khoid =request.getParameterValues(i+"_"+m+"_khoid");
					String[] _spId =request.getParameterValues(i+"_"+m+"_spId");
					String[] _spIdThayThe =request.getParameterValues(i+"_"+m+"_spIdThayThe");
					String[] _KhuvucId =request.getParameterValues(i+"_"+m+"_KhuvucId");
					String[] _MARQUETTE =request.getParameterValues(i+"_"+m+"_MARQUETTE");
					String[] _khoten =request.getParameterValues(i+"_"+m+"_khoten");
					String[] _MARQUETTE_FK =request.getParameterValues(i+"_"+m+"_MARQUETTE_FK");
					String[] _MASP =request.getParameterValues(i+"_"+m+"_MASP");
					String[] _SOLO =request.getParameterValues(i+"_"+m+"_SOLO");
					String[] _MATHUNG =request.getParameterValues(i+"_"+m+"_MATHUNG");
					String[] _MAME =request.getParameterValues(i+"_"+m+"_MAME");
					String[] _HALUONG =request.getParameterValues(i+"_"+m+"_HALUONG");
					String[] _HAMAM =request.getParameterValues(i+"_"+m+"_HAMAM");
					String[] _NGAYHETHAN =request.getParameterValues(i+"_"+m+"_NGAYHETHAN");
					String[] _NGAYNHAPKHO =request.getParameterValues(i+"_"+m+"_NGAYNHAPKHO");
					
					
					String[] _MAPHIEU =request.getParameterValues(i+"_"+m+"_MAPHIEU");
					String[] _MAPHIEU_TINHTINH =request.getParameterValues(i+"_"+m+"_MAPHIEU_TINHTINH");
					String[] _MAPHIEU_EO =request.getParameterValues(i+"_"+m+"_MAPHIEU_EO");
					String[] _khuvuckhoTen =request.getParameterValues(i+"_"+m+"_khuvuckhoTen");
					
					String[] _Soluongton =request.getParameterValues(i+"_"+m+"_Soluongton");
					String[] _Soluongtonthute =request.getParameterValues(i+"_"+m+"_Soluongtonthute");
					String[] _Soluongdexuat =request.getParameterValues(i+"_"+m+"_Soluongdexuat");
					String[] _binid =request.getParameterValues(i+"_"+m+"_binid");
					String[] _bin =request.getParameterValues(i+"_"+m+"_bin");
					String[] _doituongid =request.getParameterValues(i+"_"+m+"_doituongid");
					
					String[] _nsx_fk = request.getParameterValues(i+"_"+m+"_nsx_fk");
					String[] _MANSX = request.getParameterValues(i+"_"+m+"_MANSX");
					
					List<ISpSanxuatChitiet> listct=new ArrayList<ISpSanxuatChitiet>();
					
					if(_spId !=null){
						for(int t=0;t<_spId.length;t++){
							ISpSanxuatChitiet sp=new SpSanxuatChitiet();
							
							sp.setKhoId(_khoid[t]);
							sp.setIdSp(_spId[t]);
							sp.setBin(_bin[t]);
							sp.setBinId(_binid[t]);
							
							sp.setIdSpThayThe(_spIdThayThe[t]);
							sp.setkhuvuckhoId(_KhuvucId[t]);
							sp.setMARQUETTE(_MARQUETTE[t]);
							sp.setKhoTen(_khoten[t]);
							sp.setMARQUETTE_FK(_MARQUETTE_FK[t]);
							sp.setMaSp(_MASP[t]);
							sp.setSolo(_SOLO[t]);
							sp.setMATHUNG(_MATHUNG[t]);
							sp.setMAME(_MAME[t]);
							sp.setHAMLUONG(_HALUONG[t]);
							sp.setHAMAM(_HAMAM[t]);
							 
							sp.setNGAYHETHAN(_NGAYHETHAN[t]);
							sp.setNGAYNHAPKHO(_NGAYNHAPKHO[t]);
							
							sp.setkhuvuckhoTen(_khuvuckhoTen[t]);
							sp.setSoluongton(_Soluongton[t]);
							
							sp.setSoluongtonthute(_Soluongtonthute[t]);
							sp.setSoluong(_Soluongdexuat[t]);
							sp.setMAPHIEU(_MAPHIEU[t]);
							sp.setMAPHIEU_DINHTINH(_MAPHIEU_TINHTINH[t]);
							sp.setMAPHIEU_EO(_MAPHIEU_EO[t]);
							sp.setMANSX(_MANSX[t]);
							sp.setNSX_FK(_nsx_fk[t]);
							
							if(_doituongid!=null){
								sp.setDoituongId(_doituongid[t]);
							}
							listct.add(sp);
							
						}
					}
					spvt.setListCTkho(listct);
					
					dmvt.add(spvt);
					
				}
			}
			cd.setListDanhMuc(dmvt);
			
		}catch(Exception er){
			er.printStackTrace();
		}
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	private ArrayList<String> toArrayList(String[] array){
		ArrayList<String> arrayList = new ArrayList<String>();
			if(array!=null){
			for(String item : array){
				arrayList.add(item);
			}
		}
		return arrayList;
	}

}
