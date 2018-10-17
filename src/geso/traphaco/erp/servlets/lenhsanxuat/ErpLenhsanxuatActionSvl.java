package geso.traphaco.erp.servlets.lenhsanxuat;

import geso.dms.distributor.util.Utility;
import geso.traphaco.erp.beans.danhmucvattu.IDanhmucvattu_SP;
import geso.traphaco.erp.beans.danhmucvattu.imp.Danhmucvattu_SP;
import geso.traphaco.erp.beans.lenhsanxuat.IErpLenhsanxuat;
import geso.traphaco.erp.beans.lenhsanxuat.imp.ErpLenhsanxuat;
import geso.traphaco.erp.beans.nhapkho.*;
import geso.traphaco.erp.beans.nhapkho.imp.*;
import geso.traphaco.erp.beans.phieuxuatkho.ISpDetail;
import geso.traphaco.erp.beans.phieuxuatkho.imp.SpDetail;
import geso.traphaco.erp.db.sql.dbutils;
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

public class ErpLenhsanxuatActionSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
	PrintWriter out;
    public ErpLenhsanxuatActionSvl() 
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
		
			this.out = response.getWriter();
			Utility util = new Utility();
			
	    	String querystring = request.getQueryString();
		    userId = util.getUserId(querystring);
		    
		    if (userId.length()==0)
		    	userId = util.antiSQLInspection(request.getParameter("userId")); 	
		    String id = util.getId(querystring);  	
		    
		    IErpLenhsanxuat lsxBean = new ErpLenhsanxuat(id);
			lsxBean.setCongtyId((String) session.getAttribute("congtyId"));
		    lsxBean.setUserId(userId);
	        String nextJSP;
        	lsxBean.initDisplay();
        	nextJSP = "/TraphacoHYERP/pages/Erp/ErpLenhSanXuatDisplay.jsp";
	        
	        session.setAttribute("lsxBean", lsxBean);
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
			
			if(task.equals("tieuHao"))
			{
				this.tieuHaoNL(userId, session, request, response);
			}
			else
			{
				this.nhapKhoLSX(userId, session, request, response);
			}
		}
			
	}

	private void nhapKhoLSX(String userId, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		IErpNhapkho nkBean;

		Utility util = new Utility();
		String id = util.antiSQLInspection(request.getParameter("id"));

		if (id == null)
		{
			nkBean = new ErpNhapkho("");
		} else
		{
			nkBean = new ErpNhapkho(id);
		}

		nkBean.setCongtyId((String)session.getAttribute("congtyId"));
		nkBean.setUserId(userId);

		String ngaygd = util.antiSQLInspection(request.getParameter("ngaynhapkho"));
		if (ngaygd == null || ngaygd == "")
			ngaygd = this.getDateTime();
		nkBean.setNgaynhapkho(ngaygd);

    	String solenhsanxuat = util.antiSQLInspection(request.getParameter("solenhsanxuat"));
		if (solenhsanxuat == null)
			solenhsanxuat = "";				
    	nkBean.setSoLenhsx(solenhsanxuat);

		String noidungnhap = util.antiSQLInspection(request.getParameter("noidungnhap"));
		if (noidungnhap == null)
			noidungnhap = "";
		nkBean.setNdnId(noidungnhap);

		String khonhap = request.getParameter("khonhap");
		if (khonhap == null)
			khonhap = "";
		nkBean.setKhoId(khonhap);

		// Luu lai san pham
		String[] mahangmua = request.getParameterValues("mahangmua");
		String[] diengiai = request.getParameterValues("diengiai");
		String[] soluongnhan = request.getParameterValues("soluongnhan");
		String[] soluongnhap = request.getParameterValues("soluongnhap");
		String[] solo = request.getParameterValues("solo");
		String[] ngayhethan = request.getParameterValues("ngayhethan");
		String[] dongia = request.getParameterValues("dongia");
		String[] dongiaViet = request.getParameterValues("dongiaViet");
		String[] tiente = request.getParameterValues("tiente");

		List<ISanpham> spList = new ArrayList<ISanpham>();

		if (mahangmua != null)
		{
			ISanpham sanpham = null;
			String[] param = new String[11];
			int m = 0;
			while (m < mahangmua.length)
			{
				if (mahangmua[m] != "")
				{
					param[0] = "";
					param[1] = mahangmua[m];
					param[2] = diengiai[m];
					param[3] = solo[m];
					param[4] = soluongnhan[m];
					param[5] = soluongnhap[m];

					sanpham = new Sanpham(param);
					sanpham.setDongia(dongia[m]);
					sanpham.setDongiaViet(dongiaViet[m]);
					sanpham.setTiente(tiente[m]);
					sanpham.setNgayhethan(ngayhethan[m]);

					/*if (nkBean.getQuanLyBean())
					{
						String tem = mahangmua[m];
						String[] soluong = request.getParameterValues(tem + ".soluong");
						String[] khu = request.getParameterValues(tem + ".khuvuc");
						String[] vitri = request.getParameterValues(tem + ".vitri");

						List<ISanphamCon> spConList = new ArrayList<ISanphamCon>();
						ISanphamCon spCon = null;
						int n = 0;
						while (n < soluong.length)
						{
							if (soluong[n] != "")
							{
								spCon = new SanphamCon(mahangmua[m], soluong[n], khu[n], vitri[n]);
								spConList.add(spCon);
							}
							n++;
						}
						sanpham.setSpConList(spConList);
					}*/
					
					spList.add(sanpham);
				}
				m++;
			}
		}
		nkBean.setSpList(spList);

		String action = request.getParameter("action");

		if (action.equals("save"))
		{
			if (!nkBean.createNhapKhoLSX())
			{
				nkBean.createRs();

				session.setAttribute("nkBean", nkBean);
				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhapKhoLenhSanXuat.jsp";
				response.sendRedirect(nextJSP);
			} 
			else
			{
				IErpLenhsanxuat lsxBean = new ErpLenhsanxuat(solenhsanxuat);
			    lsxBean.setUserId(userId);
		        
	        	lsxBean.initDisplay();
	        	String nextJSP = "/TraphacoHYERP/pages/Erp/ErpLenhSanXuatDisplay.jsp";
		        
		        session.setAttribute("lsxBean", lsxBean);
		        response.sendRedirect(nextJSP);
			}
		}
		else
		{
			nkBean.createRs();

			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhapKhoLenhSanXuat.jsp";
			
			session.setAttribute("nkBean", nkBean);
			response.sendRedirect(nextJSP);
		}
	}


	private void tieuHaoNL(String userId, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		IErpLenhsanxuat lsxBean;
		
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
	    lsxBean.init();
    	
    	String spIds = util.antiSQLInspection(request.getParameter("spIds"));
		if (spIds == null)
			spIds = "";				
		lsxBean.setSpId(spIds);
    	
    	String soluongsx = util.antiSQLInspection(request.getParameter("soluongsx"));
		if (soluongsx == null)
			soluongsx = "";				
		lsxBean.setSoluong(soluongsx);
    	
		String soluongchuan = util.antiSQLInspection(request.getParameter("soluongchuan"));
		if (soluongchuan == null)
			soluongchuan = "";
		lsxBean.setSoluongchuan(soluongchuan);
		
		String chophepTT = util.antiSQLInspection(request.getParameter("chophepTT"));
		if (chophepTT == null)
			chophepTT = "0";
		lsxBean.setChophepTT(chophepTT);

		String[] mavt = request.getParameterValues("mavattu");
		String[] tenvt = request.getParameterValues("tenvattu");
		String[] donvitinh = request.getParameterValues("donvitinh");
		String[] soluong = request.getParameterValues("soluongDM");
		String[] mavtTT = request.getParameterValues("mavattuTT");
		String[] tenvtTT = request.getParameterValues("tenvattuTT"); 
		String[] dvtTT = request.getParameterValues("dvtTT");
		String[] tile = request.getParameterValues("tyle");
		String[] soluongTT = request.getParameterValues("soluongTT");
		
		String[] soluongTHThucte = request.getParameterValues("thucte");
		
		List<IDanhmucvattu_SP> spList = new ArrayList<IDanhmucvattu_SP>();
		dbutils db = new dbutils();
		
		//Kho tieu hao phai la kho san xuat
		String query = "select pk_seq from ERP_KHOTT  where TrangThai = '1' and LOAI = '1' and congty_fk = '100005'";
		ResultSet rsKho = db.get(query);
		
		String khoTieuhao_fk = "";
		if(rsKho != null)
		{
			try 
			{
				if(rsKho.next())
				{
					khoTieuhao_fk = rsKho.getString("pk_seq");
				}
				rsKho.close();
			} 
			catch (Exception e) {}
		}
		
		if(mavt != null)
		{	
			for(int m = 0; m < mavt.length; m++)
			{	
				if(mavt[m] != "")
				{	
					if(soluong[m].trim().length() >  0)
					{	
						IDanhmucvattu_SP sanpham = null;
						sanpham = new Danhmucvattu_SP();
						
						sanpham.setMaVatTu(mavt[m]);
						sanpham.setTenVatTu(tenvt[m]);
						sanpham.setDvtVT(donvitinh[m]);
						
						//System.out.println("___So luong Dinh Muc la: " + soluong[m]);
						//System.out.println("___So luong TT la: " + soluongTHThucte[m]);
						
						sanpham.setSoLuong(soluong[m]);
						sanpham.setMaVatTuThayThe(mavtTT[m]);
						sanpham.setTenVatTuThayThe(tenvtTT[m]);
						sanpham.setDvtThayThe(dvtTT[m]);
						sanpham.setTile(tile[m]);
						
						sanpham.setSoLuongTHThucTe(soluongTHThucte[m]);
						
						//Tao Bean / Lo
						if(soluongTHThucte[m].trim().length() > 0 && !soluongTHThucte[m].equals("0"))
						{
							List<ISpDetail> spDetail = new ArrayList<ISpDetail>();
							
							query = "select SANPHAM_FK, isnull(AVAILABLE, 0) as AVAILABLE, SOLO, b.pk_seq as vtId, b.MA as vitri, c.diengiai as khu " +
								    "from ERP_KHOTT_SP_CHITIET a inner join ERP_VITRIKHO b on a.BIN = b.PK_SEQ " +
								   		"inner join ERP_KHUVUCKHO c on b.KHU_FK = c.pk_seq  " +
									"where a.khott_fk = '" + khoTieuhao_fk + "' and a.sanpham_fk = ( select pk_seq from ERP_SanPham where ma = '" + mavt[m].trim() + "' ) " +
									"order by a.ngayhethan asc, a.AVAILABLE asc";
							
							System.out.println("1.Check soluong san pham: " + query);
							
							ResultSet rsSpDetail = db.get(query);
							if(rsSpDetail != null)
							{
								float tongluong = 0;
								try 
								{
									while(rsSpDetail.next())
									{
										int avaiD = rsSpDetail.getInt("AVAILABLE");
										String maspD = rsSpDetail.getString("SANPHAM_FK");
										String soloD = rsSpDetail.getString("SOLO");
										String vitriD = rsSpDetail.getString("vitri");
										String vitriIdD = rsSpDetail.getString("vtId");
										String khuD = rsSpDetail.getString("khu");
										
										if(avaiD > 0)
										{
											tongluong += avaiD;
											if(tongluong < Float.parseFloat(soluongTHThucte[m].trim()))
											{
												ISpDetail spDetail2 = new SpDetail(maspD, soloD, Integer.toString(avaiD), khuD, vitriD, vitriIdD);
												spDetail.add(spDetail2);
											}
											else
											{
												float slg = Float.parseFloat(soluongTHThucte[m].trim()) - (tongluong - avaiD);
												ISpDetail spDetail2 = new SpDetail(maspD, soloD, Float.toString(slg), khuD, vitriD, vitriIdD);
												spDetail.add(spDetail2);
												break;
											}
										}
									}
									rsSpDetail.close();
								} 
								catch (Exception e) 
								{
									System.out.println("116.Exception: " + e.getMessage());
								}
								
							}
							
							sanpham.setSpDetailList(spDetail);
						}
						
						spList.add(sanpham);												
					}
				}				
			}
			
			lsxBean.setListDanhMuc(spList);
			db.shutDown();
			
			//Check
			String msg = "";
			for(int i = 0; i < spList.size(); i++)
			{
				IDanhmucvattu_SP sp = spList.get(i);
				
				if(sp.getSoLuongTHThucTe().trim().length() > 0 && !sp.getSoLuongTHThucTe().equals("0") )
				{
					List<ISpDetail> spDetail = sp.getSpDetailList();
					
					float sum = 0;
					for(int j = 0; j < spDetail.size(); j++)
					{
						sum += Float.parseFloat(spDetail.get(j).getSoluong());
					}
					
					if(sum < Float.parseFloat(sp.getSoLuongTHThucTe()))
					{
						msg += "+ Sản phẩm " + sp.getMaVatTu() + " - " + sp.getTenVatTu() + ", không đủ số lượng trong kho sản xuất để tiêu hao, vui lòng kiểm tra lại \n";
					}
				}
			}
			
			lsxBean.setMsg(msg);
		}	
		
		
	    String action = request.getParameter("action");
	    if(action.equals("save"))
		{	
			if(!lsxBean.tieuhaoLsx(khoTieuhao_fk))
			{
				session.setAttribute("lsxBean", lsxBean);
				
				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpLenhSanXuatTieuHao.jsp";
				response.sendRedirect(nextJSP);
			}
			else
			{
				lsxBean = new ErpLenhsanxuat(id);
			    lsxBean.setUserId(userId);
		        
	        	lsxBean.initDisplay();
	        	String nextJSP = "/TraphacoHYERP/pages/Erp/ErpLenhSanXuatDisplay.jsp";
		        
		        session.setAttribute("lsxBean", lsxBean);
		        response.sendRedirect(nextJSP);
			}
		}
		else
		{
			session.setAttribute("lsxBean", lsxBean);
			
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpLenhSanXuatTieuHao.jsp";
			response.sendRedirect(nextJSP);
		}
	    
	}
	
	
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

}
