/**
 * Author : KHOAND 
 * class name : MuaHangNhaPPKhacSvl
 * Date : 2011-10-20
 */

package geso.traphaco.distributor.servlets.donhangnpp;

import geso.traphaco.distributor.beans.donhangnhapp.IDonhangnpp;
import geso.traphaco.distributor.beans.donhangnhapp.ISanPhamDhNpp;
import geso.traphaco.distributor.beans.donhangnhapp.imp.DonHangNPP;
import geso.traphaco.distributor.beans.donhangnhapp.imp.SanPhamDhNpp;
import geso.traphaco.distributor.db.sql.dbutils;

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

public class DonHangNppUpdateSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public DonHangNppUpdateSvl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	/**
	 * Ham tra ve chuoi thoi gian theo dinh dang dd-MMM-yyyy
	 * @return
	 */
	private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}else{
		IDonhangnpp dhbean;
		List<ISanPhamDhNpp> spList;
		PrintWriter out; 
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		
		userId = request.getParameter("userId");
		String madh=request.getParameter("id");
		String action=request.getParameter("action");
			dhbean=new DonHangNPP();
		dhbean.setUserId(userId);
		dhbean.setId(madh);
		String manhapp_ban=cutil.getIdNhapp(userId);
		dhbean.setNppId_Ban(manhapp_ban);
		out = response.getWriter();
	    String nppId_mua ;
		 nppId_mua = request.getParameter("nppId");
		if (nppId_mua == null)
			nppId_mua = "";
		dhbean.set_NppId_Mua(nppId_mua);
		    	
    	dhbean.setVAT("0");
		
		dhbean.setNguoisua(userId);
		String ngaysua = getDateTime();
    	dhbean.setNgaysua(ngaysua);
    	String ngaytao=getDateTime();
    	dhbean.setNgaytao(ngaytao);
    	String kbhid=request.getParameter("kenhbh");
    	String khoid=request.getParameter("khobh");
    	dhbean.setKho(khoid);//mac dinh la kenh kho hang.
    	dhbean.setKenhBanHang(kbhid);// Can phai tim hieu them de set kenh 
    	
    	dhbean.createRs();
    	String ngaygd = request.getParameter("tungay");//tungay la ten cua o textbox ben form donhangnppnew
    	if (ngaygd == null || ngaygd == "")
			ngaygd = this.getDateTime();			
    	dhbean.setNgayGiaoDich(ngaygd);
    	String nhappmua=request.getParameter("nhappmua");
    	dhbean.set_NppId_Mua(nhappmua);
    	
    	String formname=request.getParameter("formname");
    	
    	String[] masp = request.getParameterValues("masp");
		String[] tensp = request.getParameterValues("tensp");
		String[] soluong = request.getParameterValues("soluong");
		String[] donvitinh = request.getParameterValues("donvitinh");
		String[] dongia = request.getParameterValues("dongia");
		String[] thanhtien = request.getParameterValues("thanhtien");
		dbutils db=new dbutils();
		spList = new ArrayList<ISanPhamDhNpp>();
		  if(masp != null){		
			
			int m = 0;
			while(m < masp.length){
				ISanPhamDhNpp sanpham = new SanPhamDhNpp();
				if(masp[m] != ""){
					if(soluong[m] != ""){ 
						sanpham.setMaSanPham(masp[m]);
						sanpham.setTenSanPham(tensp[m]);
						sanpham.setSoLuong(Integer.parseInt(soluong[m]));
						sanpham.setDVT(donvitinh[m]);
						sanpham.setGiaMua(Double.parseDouble(dongia[m]));
						sanpham.setThanhTien(Double.parseDouble(thanhtien[m]));
						spList.add(sanpham);
					}
				}
				m++;
			}	
		  }
		  dhbean.setListSanPhamNew(spList);
	
		  java.util.Hashtable<String,Integer> spThieuList = new java.util.Hashtable<String,Integer>();
		  if(masp!= null){
				int m = 0;			
				while(m < masp.length)
				{			
					String sl="0";
					int soluongcu=0;
					String spid="";
					if(masp[m].length() > 0)
					{	 
						String query = "select sanpham_fk ,available from nhapp_kho where npp_fk='" + manhapp_ban +"' and kho_fk = '"+khoid+"' and kbh_fk="+kbhid+" ";
						query = query + " and sanpham_fk in (select pk_seq from sanpham where ma='" + masp[m].trim() + "')";
						
						System.out.println("DonHangNppUpdateSvl : 158  : "+query);
						ResultSet rs = db.get(query);
						if(rs != null)
						{
							try
							{
								if(rs.next()){
									sl = rs.getString("available");
									spid=rs.getString("sanpham_fk");
								}
								rs.close();
							}
							catch(Exception ex)
							{
									ex.printStackTrace();
							}
						}
						
						if(dhbean.getId()!="")
						{
								
							String sql_hangtrenphieu=" select soluong as soluongcu from   "+
													 " donhangnpp_sanpham b inner join donhang_npp a  on a.pk_seq=b.donhangnpp_fk  "+
													 " where  a.kbh_fk="+kbhid+" and a.kho_fk="+khoid+" and b.donhangnpp_fk="+dhbean.getId()+"" +
													 		"  and b.sanpham_fk= " +spid;
								
								
								 rs=db.get(sql_hangtrenphieu);
								
								if(rs!=null)
								{
									try
									{
										if(rs.next())
										{
									    	soluongcu = rs.getInt("soluongcu");
									    	
										}
										rs.close();
									}
									catch(Exception ex)
									{
											ex.printStackTrace();
									}
								}
						}
					}
					int soluongnhap=0;
					try
					{
						soluongnhap=Integer.parseInt(soluong[m]);
					}
					catch(Exception er)
					{
						
					}
					int soluongton=0;
					try
					{
					 soluongton=Integer.parseInt(sl);
					}
					catch(Exception er)
					{
						
					}
					 if(soluongnhap>soluongton+soluongcu)//truong hop them moi, chi can xem luong hang con ton trong kho co du voi luong xuat khong
					{ 
							spThieuList.put(masp[m],soluongton);
					}
					m++;
				}
		  }
		  dhbean.setSPThieuList(spThieuList);
		  session.setAttribute("obj", dhbean);
		  session.setAttribute("userId", userId);
		  dhbean.createRs();
		  if(action.equals("chotdonhang")) {
			  dhbean.setTrangthai("1");//1 la chot don hang
		  }
		  else{
			  dhbean.setTrangthai("0");//0 chua chot don hang
		  }
		  //System.out.println("ten dang nhap moi la ;" +userId );
		  if(action.equals("new"))//Truong hop su ly save 1 donhang moi
		  {
			  dhbean.setNguoitao(userId);// Truong hop them moi thi moi set thuoc tinh nguoi tao
			  if( dhbean.saveDonHangNPP())//da luu thanh cong
			  {
				
				  dhbean.SetDonHangNPP("");
				  
				  String nextJSP = "/TraphacoHYERP/pages/Distributor/DonHangNhaPP.jsp";
				  response.sendRedirect(nextJSP);
			  }
			  else//truong hop luu khong dc
			  {
				
				  String nextJSP = "/TraphacoHYERP/pages/Distributor/DonHangNhaPPNew.jsp";
				  response.sendRedirect(nextJSP);
			  }
			  
		  }
		  else if(action.equals("update") || action.equals("chotdonhang"))
		  {
			  //set tinh trang cua don hang
			  
			  if(dhbean.editDonHangNPP())
			  {
				  dhbean.SetDonHangNPP("");
				
				  String nextJSP = "/TraphacoHYERP/pages/Distributor/DonHangNhaPP.jsp";
					 response.sendRedirect(nextJSP);
			  }
			  else
			  {
				
				// System.out.println("Khong du so luong : " + dhbean.getthongbao());
				  String nextJSP = "/TraphacoHYERP/pages/Distributor/DonHangNPPUpdate.jsp";
					 response.sendRedirect(nextJSP);
			  }
			  
		  }else{
		 
			  String nextJSP="";
			 if(!formname.equals("formnew")){
				  nextJSP = "/TraphacoHYERP/pages/Distributor/DonHangNPPUpdate.jsp";
			 }else{
				   nextJSP = "/TraphacoHYERP/pages/Distributor/DonHangNhaPPNew.jsp";
			 }
				 response.sendRedirect(nextJSP);
		
		  }
		
		  
		}
	}

}
