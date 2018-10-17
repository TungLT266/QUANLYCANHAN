/**
 * Author : KHOAND 
 * class name : MuaHangNhaPPKhacSvl
 * Date : 2011-10-20
 */

package geso.traphaco.distributor.servlets.muahangnhappkhac;

import geso.traphaco.distributor.beans.donhangnhapp.IDonhangnpp;
import geso.traphaco.distributor.beans.donhangnhapp.ISanPhamDhNpp;
import geso.traphaco.distributor.beans.donhangnhapp.imp.DonHangNPP;
import geso.traphaco.distributor.beans.donhangnhapp.imp.SanPhamDhNpp;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
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


public class MuaHangNhaPPKhacSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MuaHangNhaPPKhacSvl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}else{
		
		IDonhangnpp obj;
		PrintWriter out; 
			
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    out  = response.getWriter();
	    
	    
	    Utility util = new Utility();
	    out = response.getWriter();
	    
	    String querystring = request.getQueryString();
	    userId = util.getUserId(querystring);
	    
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    String dhId = util.getId(querystring);
	    
	
		//obj.setUserId(userId);
		 //lay thong tin nha phan phoi
	    
	   
	    obj=new DonHangNPP(dhId);
		 obj.createRs_BenNhanHang();
	
	    
	      obj.set_NppId_Mua(util.getIdNhapp(userId));
	      obj.setTenNPPMua(util.getTenNhaPP());
	      
	    session.setAttribute("userId",userId);
	    		
		if(action.equals("display"))
		{
			
			session.setAttribute("obj", obj);	
			String	nextJSP = "/TraphacoHYERP/pages/Distributor/NhanHangMuaNhaPPDisplay.jsp";
			response.sendRedirect(nextJSP);
		}
		else if(action.equals("update")){

	
			    
			session.setAttribute("obj", obj);	
	        String	nextJSP = "/TraphacoHYERP/pages/Distributor/NhanMuaHangNhaPPUpdate.jsp";
	        response.sendRedirect(nextJSP);
		}
		else
		{

				session.setAttribute("obj", obj);
				obj.setUserId(userId);
				obj.setListNhaPPMua("");
			    String	nextJSP = "/TraphacoHYERP/pages/Distributor/NhanHangMuaNhaPP.jsp";
			    response.sendRedirect(nextJSP);
		}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//xu ly chot don hang
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}else{
	
		//dbutils db = new dbutils();//tao doi tuong ket noi csdl
		int loi=0; ///loi tang len khi chuong trinh co loi
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    Utility util = new Utility();
	    
	    userId = util.antiSQLInspection(request.getParameter("userId"));
		String madh=util.antiSQLInspection(request.getParameter("id"));//truong truong hop don hang sua thi moi co id,nguoc lai th� khong co
		String action=request.getParameter("action1");//action =new : che do them, nguoc lai la che do sua;
		IDonhangnpp dhbean=new DonHangNPP();
		dhbean.setUserId(userId);//sau khi set userid thi chuong trinh se set mancc,va ten ncc cho doi tuong dhbean(IDonhangnpp)
		dhbean.setId(madh);
		
		
		
	  
		
		
			  //System.out.println("ten dang nhap moi la ;" +userId );  
			  
			
		
		  if(action.equals("chot"))
		  {
			//khai bao nha phan phoi mua hang,va ban hang
				String nppId_mua = util.antiSQLInspection(request.getParameter("nppId_mua"));
				String nppId_ban = util.antiSQLInspection(request.getParameter("nppId_ban"));
				if (nppId_mua == null){
					nppId_mua = "";	
				}
				
				dhbean.set_NppId_Mua(nppId_mua);
				dhbean.setNppId_Ban(nppId_ban);
				String VAT = util.antiSQLInspection(request.getParameter("VAT"));
		    	if (VAT == null)
		    		VAT = "";    	
		    	dhbean.setVAT(VAT);
				
				dhbean.setNguoisua(userId);
				String ngaysua = getDateTime();
		    	dhbean.setNgaysua(ngaysua);
		    	String ngaytao=getDateTime();
		    	dhbean.setNgaytao(ngaytao);
		    	
		    	String kbhid=util.antiSQLInspection(request.getParameter("kenhbh"));
		    	String khoid=util.antiSQLInspection(request.getParameter("khobh"));
		    	dhbean.setIdKho_Nhan(khoid);//mac dinh la kenh kho hang.
		    	dhbean.setIdKenh_Nhan(kbhid);// Can phai tim hieu them de set kenh 
		    	String ngaygd = util.antiSQLInspection(request.getParameter("tungay"));//tungay la ten cua o textbox ben form donhangnppnew
		    	if (ngaygd == null || ngaygd == "")
					ngaygd = this.getDateTime();			
		    	dhbean.setNgayGiaoDich(ngaygd);
		    	String nhappmua=util.antiSQLInspection(request.getParameter("nhappmua"));
		    	dhbean.setTenNPPMua(nhappmua);
		    	String[] masp = request.getParameterValues("masp");
				String[] tensp = request.getParameterValues("tensp");
				String[] soluong = request.getParameterValues("soluong");
				String[] soluongnhan=request.getParameterValues("soluongnhan");
				String[] donvitinh = request.getParameterValues("donvitinh");
				String[] dongia = request.getParameterValues("dongia");
				String[] thanhtien = request.getParameterValues("thanhtien");
			    List<ISanPhamDhNpp>	spList = new ArrayList<ISanPhamDhNpp>();
				  if(masp != null){		
					int m = 0;
					while(m < masp.length){
						ISanPhamDhNpp sanpham = new SanPhamDhNpp();
						if(masp[m] != ""){
							if(soluong[m] != ""){ //chi them nhung san pham co so luong > 0
								sanpham.setMaSanPham(masp[m]);
								//System.out.println("ma san pham"+ masp[m]);
								
								sanpham.setTenSanPham(tensp[m]);
								try
								{
								sanpham.setSoLuong(Integer.parseInt(soluong[m]));
								}catch(Exception er){
									loi=loi+1;
									dhbean.setthongbao("SO LUONG KHONG HOP LE TREN DONG SAN PHAM : " + masp[m]);
								}
								sanpham.setDVT(donvitinh[m]);
								try
								{
								sanpham.setSoLuongNhan(Integer.parseInt(soluongnhan[m]));
								}catch(Exception er){
									loi=loi+1;
									dhbean.setthongbao("SO LUONG NHAN KHONG HOP LE TREN DONG SAN PHAM : " + masp[m]);
								}
								//sanpham.setDonHangNPP(DongHangNpp)
								sanpham.setGiaMua(Double.parseDouble(dongia[m]));
								sanpham.setThanhTien(Double.parseDouble(thanhtien[m]));
								spList.add(sanpham);
							}
						}
						m++;
					}	
				  }
				  
				  dhbean.setListSanPhamNew(spList);//add cac chi tiet san pham  vao don hang
				  session.setAttribute("obj", dhbean);
				  session.setAttribute("userId", userId);	 
					  dhbean.setTrangthai("1");//1 la chot don hang
					  if(loi>1)
						 {
							 session.setAttribute("type", "0");
							 String nextJSP = "/TraphacoHYERP/pages/Distributor/NhanHangMuaNhaPP.jsp";
							 response.sendRedirect(nextJSP);
						 }
			  if(dhbean.ChotDonHangNPP_Mua())
			  {
				  dhbean.setListNhaPPMua("");
				  String nextJSP = "/TraphacoHYERP/pages/Distributor/NhanHangMuaNhaPP.jsp";
				  response.sendRedirect(nextJSP);
			  }
			  else
			  {
				  dhbean.createRs_BenNhanHang();
				  String nextJSP = "/TraphacoHYERP/pages/Distributor/NhanMuaHangNhaPPUpdate.jsp";
					 response.sendRedirect(nextJSP);
			  }
			  
		  }
		 else if(action.equals("timkiem")){
			 
			 	
				String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
				dhbean.setTrangthai(trangthai);
				
				String npptimkiem = util.antiSQLInspection(request.getParameter("nhappmuatk"));
				dhbean.setNppId_Ban(npptimkiem);
				String tungay=util.antiSQLInspection(request.getParameter("tungay"));
				dhbean.setTuNgay(tungay);
				
				String denngay=util.antiSQLInspection(request.getParameter("denngay"));
				dhbean.setDenNgay(denngay);
					
				
				dhbean.set_NppId_Mua(util.getIdNhapp(userId));
			 
				String sql1=" SELECT     A.PK_SEQ, M.TEN, T.TEN AS NGUOITAO, S.TEN AS NGUOISUA, A.NGAYNHAP, A.TRANGTHAI, A.NGAYTAO, A.NGAYSUA, "+
				" A.VAT, A.TONGGIATRI,  A.DATHANHTOAN, A.NPP_FK_MUA, A.NPP_FK_BAN, A.KHO_FK, A.KBH_FK "+
		        " FROM         dbo.DONHANG_NPP AS A INNER JOIN  dbo.NHAPHANPHOI AS M ON A.NPP_FK_BAN = M.PK_SEQ INNER JOIN "+
		                            "  dbo.NHANVIEN AS T ON T.PK_SEQ = A.NGUOITAO INNER JOIN "+
		                             "  dbo.NHANVIEN AS S ON S.PK_SEQ = A.NGUOISUA  "+
		       " WHERE   (A.NPP_FK_MUA = " + util.getIdNhapp(userId)+")";
				//Them cac dieu kien tim kiem
				   if(trangthai!="")
				    {
				    	sql1=sql1+ " and a.trangthai = "+ trangthai;
				    }
				   
					session.setAttribute("tungay", "");
					session.setAttribute("denngay","");//Truyen qua mac dinh 2 gia tri trong
				    if(tungay!="") {
				    	sql1=sql1+ " and A.NGAYNHAP >= '"+tungay+"'"; 
				    	session.setAttribute("tungay", tungay);//truyen qua tu ngay co gia tri
				    }
				    if(denngay!=""){
				     	sql1=sql1+ " and A.NGAYNHAP <= '"+denngay+"'"; 
				     
						session.setAttribute("denngay", denngay);//truyen qua den ngay co gia tri
				    }
				    	
				
					if(npptimkiem!="" && npptimkiem!=null){
						sql1=sql1+ " and A.NPP_FK_BAN=" + npptimkiem;
					}
					
					
					System.out.println("Khong vao day :");
					dhbean.setListNhaPPMua(sql1);
					dhbean.createRs_BenNhanHang();
				session.setAttribute("obj",dhbean );
				 String nextJSP = "/TraphacoHYERP/pages/Distributor/NhanHangMuaNhaPP.jsp";
				 response.sendRedirect(nextJSP);
		 }
		}
		
	}

}
