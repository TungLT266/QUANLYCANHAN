package geso.traphaco.erp.servlets.nhapkho.giay;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.lenhsanxuatgiay.IErpLenhsanxuat;
import geso.traphaco.erp.beans.lenhsanxuatgiay.imp.ErpLenhsanxuat;
import geso.traphaco.erp.beans.nhapkho.giay.IErpNhapKhoLsx;
import geso.traphaco.erp.beans.nhapkho.giay.IErpNhapkho;
import geso.traphaco.erp.beans.nhapkho.giay.ISanpham;
import geso.traphaco.erp.beans.nhapkho.giay.imp.ErpNhapKhoLsx;
import geso.traphaco.erp.beans.nhapkho.giay.imp.ErpNhapkho;
import geso.traphaco.erp.beans.nhapkho.giay.imp.Sanpham;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ErpNhapKhoLsxUpdateSvl")
public class ErpNhapKhoLsxUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	PrintWriter out;

	public ErpNhapKhoLsxUpdateSvl()
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

			this.out = response.getWriter();
			Utility util = new Utility();
			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);

			out.println(userId);

			if (userId.length() == 0)
				userId = util.antiSQLInspection(request.getParameter("userId"));

			String id = util.getId(querystring);
			IErpNhapkho nkBean = new ErpNhapkho(id);
			nkBean.setCongtyId((String) session.getAttribute("congtyId"));
			nkBean.setUserId(userId); // phai co UserId truoc khi Init
			nkBean.init();

			String nextJSP;

			if (request.getQueryString().indexOf("display") >= 0)
			{
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhapKhoGiayDisplay.jsp";
			} else
			{
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhapKhoGiayUpdate.jsp";
			}

			session.setAttribute("nkBean", nkBean);
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
		} else
		{
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");

			session.setMaxInactiveInterval(30000);
			this.out = response.getWriter();

			String task = request.getParameter("task");
			if (task == null)
				task = "";
			System.out.println("task : "+task);
			
			if (task.equals("nhapKho"))
			{
				this.nhapKhoLSX(userId, session, request, response);
			}  
		}

	}

	private void nhapKhoLSX(String userId, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		IErpNhapKhoLsx nkLsxBean = null;
		nkLsxBean = new ErpNhapKhoLsx();
		Utility util = new Utility();
		String ngaynhap =  util.antiSQLInspection(request.getParameter("ngaynhapkho"));
		nkLsxBean.setNgaynhapkho(ngaynhap);
		String lsxId = util.antiSQLInspection(request.getParameter("lsxId"));
		String congdoanId = util.antiSQLInspection(request.getParameter("congdoanId"));
		String khoId = util.antiSQLInspection(request.getParameter("khoId"));
		String noidungnhap = util.antiSQLInspection(request.getParameter("noidungnhap"));
		
		String khonhanhangdatid  =util.antiSQLInspection(request.getParameter("khonhanhangdatid"));
		if(khonhanhangdatid==null){
			khonhanhangdatid="";
		}
		nkLsxBean.setKhoNhanTP(khonhanhangdatid);
		
		
		String[] nhapKhoId = request.getParameterValues("nhapkhoId");
		String isKhuvuc = util.antiSQLInspection(request.getParameter("isQlKhuvuc"));
		String Loaisanphamid=util.antiSQLInspection(request.getParameter("loaisanpham"));
		String khongkiemdinh=util.antiSQLInspection(request.getParameter("khongkiemdinh"));
		
		if(khongkiemdinh==null){
			khongkiemdinh="0";
		}
		nkLsxBean.setKhongkiemdinh(khongkiemdinh);
		
		String donvitinh=util.antiSQLInspection(request.getParameter("donvitinh"));
		
		nkLsxBean.setDonViTinh(donvitinh);
		
		String soluongsanxuat=util.antiSQLInspection(request.getParameter("soluongsanxuat"));
		
		nkLsxBean.setSoLuongSanXuat(soluongsanxuat);
		
		String islsxcongnghe=util.antiSQLInspection(request.getParameter("islsxcongnghe"));
		nkLsxBean.setIsLsxCongNghe(islsxcongnghe);
		
		
		String BTPID=util.antiSQLInspection(request.getParameter("BTPID"));
		if(BTPID==null){
			BTPID="";
		}
		 
		nkLsxBean.setBTPId(BTPID);
		
		String thuphamid=util.antiSQLInspection(request.getParameter("thuphamid"));
		if(thuphamid==null){
			thuphamid="";
		}
		 
		nkLsxBean.setThuphamId(thuphamid);
		
		
		nkLsxBean.setLoaisanpham(Loaisanphamid);
		nkLsxBean.setLsxId(lsxId);
		nkLsxBean.setCongDoanId(congdoanId);
		nkLsxBean.setUserId(userId);
		nkLsxBean.setNdnId(noidungnhap);
		nkLsxBean.setKhoId(khoId);
		nkLsxBean.setIsQLKV(isKhuvuc);
		nkLsxBean.setCongTyId((String) session.getAttribute("congtyId"));
		nkLsxBean.CreateRs();
		List<IErpNhapkho> nkList = new ArrayList<IErpNhapkho>();
		
		String action = request.getParameter("action");
		
		
		if (nhapKhoId != null && !action.equals("changeloaisp"))
		{
			
			IErpNhapkho nkBean = null;
			for (int i = 0; i < nhapKhoId.length; i++)
			{
				nkBean = new ErpNhapkho(nhapKhoId[i]);
				nkBean.setCongtyId((String) session.getAttribute("congtyId"));
				nkBean.setUserId(userId);
				nkBean.setSoLenhsx(lsxId);
				nkBean.setNdnId(noidungnhap);
				nkBean.setKhoId(khoId);
				nkBean.setCongDoanId(congdoanId);
			
				nkBean.setNgaychotNV(ngaynhap);
				nkBean.setNgaynhapkho(ngaynhap);
				nkBean.setCongtyId((String) session.getAttribute("congtyId"));
				String[] sanphamId = request.getParameterValues("sanphamId_" + nhapKhoId[i]);
				String[] mahangmua = request.getParameterValues("mahangmua_" + nhapKhoId[i]);
				String[] diengiai = request.getParameterValues("diengiai_" + nhapKhoId[i]);
				String[] soluongnhan = request.getParameterValues("soluongsx_" + nhapKhoId[i]);
				String[] soluongnhap = request.getParameterValues("soluongnhap_" + nhapKhoId[i]);
				String[] khuvuc = request.getParameterValues("kvKhoId_" + nhapKhoId[i]);
				String[] solo = request.getParameterValues("solo_" + nhapKhoId[i]);
				String[] ngaysanxuat = request.getParameterValues("ngaysanxuat_" + nhapKhoId[i]);
				String[] ngayhethan = request.getParameterValues("ngayhethan_" + nhapKhoId[i]);
				String[] dvdlid= request.getParameterValues("dvdlid_" + nhapKhoId[i]); 
				
				String[] dongia = request.getParameterValues("dongia_" + nhapKhoId[i]);
				String[] dongiaViet = request.getParameterValues("dongiaViet_" + nhapKhoId[i]);
				String[] tiente = request.getParameterValues("tiente_" + nhapKhoId[i]);
				String[] soluonglaymau = request.getParameterValues("soluonglaymau_" + nhapKhoId[i]);
				
				String[] dvdl_mau_ = request.getParameterValues("dvdl_mau_" + nhapKhoId[i]);
				String[] dvdl_mau_Id_ = request.getParameterValues("dvdl_mau_Id_" + nhapKhoId[i]);
				
				
				
				List<ISanpham> spList = new ArrayList<ISanpham>();
//				dbutils db = new dbutils();
				if (mahangmua != null)
				{
					ISanpham sanpham = null;
					String[] param = new String[11];
					int m = 0;
					while (m < mahangmua.length)
					{
//						double soluong=0;
//						try{
//							 soluong=Double.parseDouble(soluongnhap[m]);
//							
//						}catch( Exception err){
//							
//						}
						if (mahangmua[m] != "" && solo[m].trim().length() > 0)
						{
							
							param[0] = "";
							param[1] = mahangmua[m];
							param[2] = diengiai[m];
							param[3] = solo[m];
							param[4] = soluongnhan[m];
							param[5] = soluongnhap[m];
							if(isKhuvuc.equals("1"))
								param[6] = khuvuc[m];
							else
								param[6] = "NULL";
							sanpham = new Sanpham(param);
							sanpham.setId(sanphamId[m]);
							sanpham.setDongia(dongia[m]);
							sanpham.setDongiaViet(dongiaViet[m]);
							sanpham.setTiente(tiente[m]);
							sanpham.setNgaySanXuat(ngaysanxuat[m] != null && ngaysanxuat[m].trim().length() <= 0 ? ngaynhap
								: ngaysanxuat[m]);
							sanpham.setNgayNhapKho(ngaynhap);
							sanpham.setNgayhethan(ngayhethan[m] != null && ngayhethan[m].trim().length() <= 0 ? ngaynhap
								: ngayhethan[m]);
							
							sanpham.setDvdlId(dvdlid[m]);
							sanpham.setRsDvld(nkLsxBean.getRsdvdl(sanphamId[m]));
							
							spList.add(sanpham);
						 
							if(khoId != null && khoId.length() > 0){
								sanpham.setKhuvucRs(nkLsxBean.getQuery("select * from ERP_KHUVUCKHO where KHOTT_FK = '"+khoId+"' and TRANGTHAI = 1"));
							}
							
							double soluonglm=0;
							try{
								soluonglm=Double.parseDouble(soluonglaymau[m].replace(",",""));
							}catch(Exception er){
								
							}
							sanpham.setSoluonglaymau(soluonglm+"");
							sanpham.setDvdl_Mau(dvdl_mau_[m]);
							sanpham.setDvdl_Mau_Id(dvdl_mau_Id_[m]);
							
						}
						m++;
					}
				}
				//db.shutDown();
				nkBean.setSpList(spList);
				nkBean.setKhongkiemdinh(khongkiemdinh);
				nkList.add(nkBean);
				nkLsxBean.setListNhapKho(nkList);
			}
		}
 
		if (action.equals("save"))
		{
			if (!nkLsxBean.createNhapKhoLSX())
			{ 
				session.setAttribute("nkBean", nkLsxBean);
				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhapKhoLenhSanXuatGiay.jsp";
				response.sendRedirect(nextJSP);
				 
			} else
			{
				IErpLenhsanxuat lsxBean = new ErpLenhsanxuat(lsxId);
				lsxBean.setUserId(userId);
				lsxBean.setCongDoanCurrent(congdoanId);
				lsxBean.init();
				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpLenhSanXuatGiayUpdate.jsp";
				session.setAttribute("lsxBean", lsxBean);
				response.sendRedirect(nextJSP);
			}
		} else
		{
			
			nkLsxBean.initNhapKhoLsx();
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhapKhoLenhSanXuatGiay.jsp";
			session.setAttribute("nkBean", nkLsxBean);
			response.sendRedirect(nextJSP);
		}
	}
 
}
