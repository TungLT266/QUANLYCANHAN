package geso.traphaco.erp.servlets.phieuxuatkhoTH;
 
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.phieuxuatkhoTH.*;
import geso.traphaco.erp.beans.phieuxuatkhoTH.imp.*;

import java.io.IOException;
import java.io.PrintWriter;
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



public class ErpPhieuxuatkhoTHUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    
	PrintWriter out;
	
    public ErpPhieuxuatkhoTHUpdateSvl() {
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
		    
		    out.println(userId);
		    
		    if (userId.length()==0)
		    	userId = util.antiSQLInspection(request.getParameter("userId"));
		    
		    String id = util.getId(querystring);  
		    
		    
			IErpPhieuxuatkho pxkBean = new ErpPhieuxuatkho(id);
	        pxkBean.setUserId(userId); //phai co UserId truoc khi Init
	         
	        String ctyId = (String)session.getAttribute("congtyId");
	        pxkBean.setCongtyId(ctyId);
	        String nextJSP="";
	        
	        if(request.getQueryString().indexOf("display") >= 0 ) 
	        {
	        	pxkBean.init();
	        	nextJSP = "/TraphacoHYERP/pages/Erp/ErpPhieuXuatKhoDisplay.jsp";
	        }
	        else if(request.getQueryString().indexOf("create_nhanhang")  >= 0){
	        	pxkBean = new ErpPhieuxuatkho();
	        	String ndxid=request.getParameter("ndxid");
		        pxkBean.setUserId(userId); //phai co UserId truoc khi Init
		        pxkBean.setTrahangNccId(id);
		 
		        pxkBean.setLoaixuatkho("DH");
		        pxkBean.setNdxId(ndxid);
		        // lấy thông số NCC và thông tin hóa đơn
		        pxkBean.InitXuatKho_From_DDH();
		        
		        // tạo Rs 
		        pxkBean.createRs();
		        nextJSP = "/TraphacoHYERP/pages/Erp/ErpPhieuXuatKhoNew.jsp";
		        
	        }else 
	        {
	        	pxkBean.init();
	        	nextJSP = "/TraphacoHYERP/pages/Erp/ErpPhieuXuatKhoNew.jsp";
	        }
	 
	        session.setAttribute("pxkBean", pxkBean);
	        response.sendRedirect(nextJSP);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
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
			request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
			
			session.setMaxInactiveInterval(30000);
			
			this.out = response.getWriter();
			IErpPhieuxuatkho pxkBean;
			
			Utility util = new Utility();
			String id = (request.getParameter("id"));
			
		    if(id == null)
		    {  	
		    	pxkBean = new ErpPhieuxuatkho("");
		    }
		    else
		    {
		    	pxkBean = new ErpPhieuxuatkho(id);
		    }
		    String ctyId = (String)session.getAttribute("congtyId");
	        pxkBean.setCongtyId(ctyId);
		    pxkBean.setUserId(userId);
			
		    String ngayxk = util.antiSQLInspection(request.getParameter("ngayxuatkho"));
			if (ngayxk == null || ngayxk == ""){
				ngayxk ="";	
			}
	    	pxkBean.setNgayxuatkho(ngayxk);
	    	
	    	
	    	String ngayhoadon = util.antiSQLInspection(request.getParameter("ngayhoadon"));
			if (ngayhoadon == null)
				ngayhoadon = "";				
	    	pxkBean.setNgayhoadon(ngayhoadon);
	    	
	    	String ngaychot = util.antiSQLInspection(request.getParameter("ngaychot"));
			if (ngaychot == null || ngaychot == ""){
				//ngaychot = this.getDateTime();
				ngaychot="";
			}
	    	pxkBean.setNgaychotNV(ngaychot);
	    
	    	String lydoxuat = util.antiSQLInspection(request.getParameter("lydoxuat"));
			if (lydoxuat == null)
				lydoxuat = "";				
	    	pxkBean.setLydoxuat(lydoxuat);
	    	
	    	String noidungxuat = util.antiSQLInspection(request.getParameter("noidungxuat"));
			if (noidungxuat == null)
				noidungxuat = "";				
	    	pxkBean.setNdxId(noidungxuat);
	    	
	    	String nppId = util.antiSQLInspection(request.getParameter("nppId"));
			if (nppId == null)
				nppId = "";				
			System.out.println("NppId:" + nppId);
			
	    	pxkBean.setNppId(nppId);
	    	
	    	String nppTen = util.antiSQLInspection(request.getParameter("nppTen"));
			if (nppTen == null)
				nppTen = "";				
	    	pxkBean.setNppTen(nppTen);
	    	
	    	String hdtcId = util.antiSQLInspection(request.getParameter("hdtcId"));
			if (hdtcId == null)
				hdtcId = "";				
	    	pxkBean.setHDTCId(hdtcId);
	    	
	    	
	    	
	    	/*
	    	String loaixuatkho = util.antiSQLInspection(request.getParameter("loaixuatkho"));
			if (loaixuatkho == null)*/
			String	loaixuatkho = "DH";				
	    	pxkBean.setLoaixuatkho(loaixuatkho);  	
	    	
	    	String ddhid = request.getParameter("ddhid");
			if (ddhid == null)
				ddhid = "";				
	    	pxkBean.setDDHId(ddhid);
	    	
	    	String nccId = request.getParameter("nccId");
			if (nccId == null)
				nccId = "";				
	    	pxkBean.setNccId(nccId);
	    	
	    	String trahangId = request.getParameter("trahangId");
			if (trahangId == null)
				trahangId = "";				
	    	pxkBean.setTrahangNccId(trahangId);
	    	
	    	String khoxuat = util.antiSQLInspection(request.getParameter("khoxuat"));	    	
			if (khoxuat == null)
				khoxuat = "";				
	    	pxkBean.setKhoId(khoxuat);
	    	//System.out.println("Kho xuat :"+khoxuat);
	    	
	    	String ghichu = util.antiSQLInspection(request.getParameter("ghichu"));
			if (ghichu == null)
				ghichu = "";
	    	pxkBean.setGhichu(ghichu);
	    	List<ISanpham> spList =  new ArrayList<ISanpham>();
	    	
	    	String changeDdh = request.getParameter("changeDdh");
	    	
	    	//System.out.println("changeDdh:" + changeDdh);
	    	if(changeDdh.equals("true"))
	    	{
//	    		System.out.println("Ich bin");
	    		pxkBean.changeDdh(); //tinh lai san pham va kho luu khi thay doi don dat hang
	    	}
	    	else
	    	{
		    	//Luu lai san pham
				String[] idhangmua = request.getParameterValues("idhangmua");
				String[] mahangmua = request.getParameterValues("mahangmua");
				String[] diengiai = request.getParameterValues("diengiai");
				String[] soluong = request.getParameterValues("soluong");
				String[] bean = request.getParameterValues("bean");
				String[] soluonghoadon = request.getParameterValues("soluonghoadon");
				String[] soluongdaxuat = request.getParameterValues("soluongdaxuat");
				
				if(mahangmua != null)
				{		
					ISanpham sanpham = null;
					String[] param = new String[11];
					int m = 0;
					while(m < mahangmua.length)
					{
						if(idhangmua[m] != "")
						{
							param[0] = idhangmua[m];
							param[1] = mahangmua[m];
							param[2] = diengiai[m];
							param[3] = soluong[m].replaceAll(",", "");
							param[4] = "";
							
							String isBean = bean[m];
							System.out.println("IsBean = " + isBean);
							
							sanpham = new Sanpham(param);
						 
							sanpham.setSoluongDaXuat(soluongdaxuat[m].replaceAll(",", ""));
							sanpham.setSoluongTotal(soluonghoadon[m].replaceAll(",", ""));
							
							String[] khochitietId = request.getParameterValues( idhangmua[m] +".khochitietId");
							String[] khuId = request.getParameterValues(idhangmua[m] + ".khu");
							String[] tenkhu = request.getParameterValues(idhangmua[m] + ".tenkhu");
							String[] solo = request.getParameterValues(idhangmua[m] + ".solo");
							String[] slg = request.getParameterValues(idhangmua[m] + ".soluong");
							String[] ngaynhapkho = request.getParameterValues(idhangmua[m] + ".ngaynhapkho");
							String[] ngaysanxuat = request.getParameterValues(idhangmua[m] + ".ngaysanxuat");
							String[] ngayhethan = request.getParameterValues(idhangmua[m] + ".ngayhethan");
							
							String[] phieueo = request.getParameterValues(idhangmua[m] + ".phieueo");
							
							String[] maphieudinhtinh = request.getParameterValues(idhangmua[m] + ".maphieudinhtinh");
							String[] maphieu = request.getParameterValues(idhangmua[m] + ".maphieu");
							
							String[] hamluong = request.getParameterValues(idhangmua[m] + ".hamluong");
							String[] hamam = request.getParameterValues(idhangmua[m] + ".hamam");
							
							
							String[] soluongton = request.getParameterValues(idhangmua[m] + ".soluongton");
							String[] mame = request.getParameterValues(idhangmua[m] + ".mame");
							String[] mathung = request.getParameterValues(idhangmua[m] + ".mathung");
							String[] mamarquette = request.getParameterValues(idhangmua[m] + ".mamarquette");
							String[] idmarquette = request.getParameterValues(idhangmua[m] + ".idmarquette");
							String[] nsxid = request.getParameterValues(idhangmua[m] + ".idnsx");
							String[] nsxten = request.getParameterValues(idhangmua[m] + ".tennsx");
							List<ISpDetail> spConList = new ArrayList<ISpDetail>();
							ISpDetail spCon = null;
							int n = 0;
							double  totalslx=0;
							if(slg != null)
							{
								while(n < slg.length)
								{
									 double sllo=0;
									 try{
										 sllo= Double.parseDouble(slg[n].replaceAll(",", ""));
									 }catch(Exception er){
										 er.printStackTrace();
									 }
									 
									 spCon = new SpDetail();
									 spCon.setKhoChiTietId(khochitietId[n]);
									 
									 spCon.setId(idhangmua[m]);
									 spCon.setSoluong(sllo+"");
									 if(khuId!=null){
										 spCon.setKhuId(khuId[n].trim());
										 spCon.setKhu(tenkhu[n].trim());
									 }
									 spCon.setSolo(solo[n].trim());
									 spCon.setIdnsx(nsxid[n]);
									 spCon.setTennsx(nsxten[n]);
									 spCon.setNgayhethan(ngayhethan[n]);
									 spCon.setNgaysanxuat(ngaysanxuat[n]);
									 spCon.setMame(mame[n]);
									 spCon.setMathung(mathung[n]);
									 spCon.setIdmarquette(idmarquette[n]);
									 spCon.setMamarquette(mamarquette[n]);
									 spCon.setMaphieu(maphieu[n]);
									 spCon.setMaphieudinhtinh(maphieudinhtinh[n]);
									 
									 spCon.setPhieuEO(phieueo[n]);
									 spCon.setNgaynhapkho(ngaynhapkho[n]);
									 
									 spCon.setHamam(hamam[n]);
									 spCon.setHamluong(hamluong[n]);
									 
									 spCon.setSoluongton(soluongton[n].replaceAll(",", ""));
									 spConList.add(spCon);
								 
									 totalslx =totalslx+ sllo;
									 n++;
								}
							}
							sanpham.setSoluong(totalslx+"");
							sanpham.setSpDetailList(spConList);	
							spList.add(sanpham);
						}
					m++;
					}
					
				}	
				
				pxkBean.setSpList(spList);
				
			}
		 
			
			String action = request.getParameter("action");
			
			System.out.println("Action : "+action);
			
			if(action.equals("changendx"))
			{
				pxkBean.setSpList(new ArrayList<ISanpham>());
				pxkBean.setNccId("");
				pxkBean.setTrahangNccId("");
			}
			
			if(action.equals("locchungtu"))
			{
				pxkBean.setSpList(new ArrayList<ISanpham>());
				pxkBean.setTrahangNccId("");
			}
			if(action.equals("submit"))
			{
				System.out.println("VÀO ĐÂY");
				pxkBean.setSpList(new ArrayList<ISanpham>());
 
			}
			
			if(action.equals("save"))
			{	
				if(id == null || id.trim().length() == 0)
				{
					if(!pxkBean.createPxk())
					{
						 
	    		    	pxkBean.createRs();
	    		    	session.setAttribute("pxkBean", pxkBean);
	    				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpPhieuXuatKhoNew.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IErpPhieuxuatkhoList obj = new ErpPhieuxuatkhoList();
					    obj.setUserId(userId);
					    obj.init("");
					    
						session.setAttribute("obj", obj);
								
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpPhieuXuatKho.jsp";
						response.sendRedirect(nextJSP);
					}
				}
				else
				{
					if(!pxkBean.updatePxk())
					{
 
						pxkBean.createRs();
						session.setAttribute("pxkBean", pxkBean);
	    				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpPhieuXuatKhoNew.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IErpPhieuxuatkhoList obj = new ErpPhieuxuatkhoList();
					    obj.setUserId(userId);
					    obj.init("");
					    
						session.setAttribute("obj", obj);
								
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpPhieuXuatKho.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			}
			else
			{
				String nextJSP;

				String msg="";//CheckNPP(nppIds);
				if(msg.trim().length() > 0)
				{
					pxkBean.setMsg(msg);
				}
				else
				{
					pxkBean.createRs();
				}
				
				if (id == null){
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpPhieuXuatKhoNew.jsp";
				}
				else{
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpPhieuXuatKhoUpdate.jsp";   						
				}
				
				session.setAttribute("pxkBean", pxkBean);
				response.sendRedirect(nextJSP);
			}
		}
	}
	

	private String CheckNPP(String nppIds) {

		dbutils db = new dbutils();
		String[] tmp = nppIds.split(",");
		String kbhId="";
		String msg="";
		
		String query = "SELECT MA, TEN, KBH_FK FROM NHAPHANPHOI WHERE PK_SEQ in (" + nppIds + ") ";
		
		ResultSet rs = db.get(query);
		
		if(rs != null){
			try{
				while(rs.next())
				{
					kbhId = rs.getString("KBH_FK")== null ? "":rs.getString("KBH_FK") ;
					if(kbhId.trim().length() <=0)
					{
						msg ="Nhà phân phối <"  + rs.getString("TEN")+ "> chưa chọn kênh bán hàng trong dữ liệu nền.Vui lòng kiểm tra lại" ; 
					}
				}
				rs.close();
			}catch(java.sql.SQLException e){
				
			}
		}
		
		return msg;
	}

	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

}
