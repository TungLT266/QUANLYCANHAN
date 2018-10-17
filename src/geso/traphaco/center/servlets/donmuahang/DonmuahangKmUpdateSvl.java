package geso.traphaco.center.servlets.donmuahang;

import geso.traphaco.center.beans.donmuahang.IDonmuahangList;
import geso.traphaco.center.beans.donmuahang.IERP_DonDatHang;
import geso.traphaco.center.beans.donmuahang.IERP_DonDatHang_SP;
import geso.traphaco.center.beans.donmuahang.imp.DonmuahangList;
import geso.traphaco.center.beans.donmuahang.imp.ERP_DonDatHang;
import geso.traphaco.center.beans.donmuahang.imp.ERP_DonDatHang_SP;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/DonmuahangKmUpdateSvl")
public class DonmuahangKmUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public DonmuahangKmUpdateSvl()
	{
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();

		HttpSession session = request.getSession();

		Utility util = new Utility();
		out = response.getWriter();

		String querystring = request.getQueryString();
		String action = util.getAction(querystring);
		out.println(action);
		String ddhId = util.getId(querystring);
		//String userId = util.getUserId(querystring);
		System.out.println("Action : " + action);
		if (action.equals("edit"))
		{
			IERP_DonDatHang dhBean = new ERP_DonDatHang();
			dhBean.setId(ddhId);
			dhBean.initDisplayKm();
			session.setAttribute("kenhid", dhBean.getIDKenhBanHang());
			session.setAttribute("nhappid", dhBean.getNppId());
			session.setAttribute("dvkdid", dhBean.getdvkdid());
			session.setAttribute("donhangid", dhBean.getId());
			String nextJSP = "/TraphacoHYERP/pages/Center/DonMuaHangKhuyenMaiUpdate.jsp";
			session.setAttribute("obj", dhBean);
			response.sendRedirect(nextJSP);
		}else if (action.equals("display"))
		{
			IERP_DonDatHang dhBean = new ERP_DonDatHang();
			dhBean.setId(ddhId);
			dhBean.initDisplayKm();
			String nextJSP = "/TraphacoHYERP/pages/Center/DonMuaHangKhuyenMaiDisplay.jsp";
			session.setAttribute("obj", dhBean);
			response.sendRedirect(nextJSP);
		}
	}
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		dbutils db = new dbutils();
		Utility util = new Utility();
		session.setAttribute("util", util);
		String id = request.getParameter("id");
		String userId = request.getParameter("userId");
		String userTen = request.getParameter("userTen");

		session.setAttribute("userId", userId);
		session.setAttribute("userTen", userTen);

		String action = request.getParameter("action");
		String nextJSP = "";
		IERP_DonDatHang dhBean = new ERP_DonDatHang();
		if (id==null)
		{
			nextJSP = "/TraphacoHYERP/pages/Center/DonMuaHangKhuyenMaiNew.jsp";
		} else
		{
			nextJSP = "/TraphacoHYERP/pages/Center/DonMuaHangKhuyenMaiUpdate.jsp";
			dhBean.setId(id);
		}
		String ngaygiaodich = util.antiSQLInspection(request.getParameter("ngaygiaodich"));
		dhBean.setNgaygiaodich(ngaygiaodich);
		

		String ngaytao = this.getDateTime();
		String ngaysua = ngaytao;
		dhBean.setNgaytao(ngaytao);
		dhBean.setNgaysua(ngaysua);
		dhBean.setNguoitao(userId);
		dhBean.setNguoisua(userId);
		
		String loaichietkhau = request.getParameter("loaick");		
		if (loaichietkhau == null)
		{
			loaichietkhau = "1";
		}
		dhBean.setloaichietkhau(loaichietkhau);
		
		String vat = util.antiSQLInspection(request.getParameter("VAT"));

		try
		{
			dhBean.setVAT(Double.parseDouble(vat));
		} catch (Exception er)
		{
			dhBean.setVAT(10);
		}

		String nhaccid = util.antiSQLInspection(request.getParameter("nhaccid"));

		if (nhaccid == null)
		{
			nhaccid = "";
		}
		dhBean.setIdNhaCungCap(nhaccid);

		String tuvanchuyen = util.antiSQLInspection(request.getParameter("tuvanchuyen"));

		if (tuvanchuyen == null)
		{
			tuvanchuyen = "0";
		}
		dhBean.setTuVanChuyen(tuvanchuyen);

		String kenhbanhang = util.antiSQLInspection(request.getParameter("kenhbanhang"));
		if (kenhbanhang == null)
		{
			kenhbanhang = "";
		}

		dhBean.setIDKenhBanHang(kenhbanhang);
		float giavanchuyen=0;
		try{
		 giavanchuyen=Float.parseFloat( util.antiSQLInspection(request.getParameter("GiaVanChuyen")));

		}catch(Exception er){
			
		}
		String nhappid = util.antiSQLInspection(request.getParameter("nhappid"));

		if (nhappid == null)
		{
			nhappid = "";
		}
		dhBean.setNppId(nhappid);
		
		String sotienCktm = request.getParameter("sotienCktm");

		if (sotienCktm == null)
		{
			sotienCktm = "0";
		}
		double cktm=0;
	
		dhBean.setChietKhauThuongMai( cktm );
		
		String tennpp = util.antiSQLInspection(request.getParameter("tennpp"));
		dhBean.setNppTen(tennpp);

		String dvkdid = util.antiSQLInspection(request.getParameter("dvkdid"));
		if (dvkdid == null)
		{
			dvkdid = "";
		}
		
		
		// set de lay bang gia.
		session.setAttribute("kenhid", kenhbanhang);
		session.setAttribute("nhappid", nhappid);
		session.setAttribute("dvkdid", dvkdid);
		session.setAttribute("donhangid", id);
		dhBean.setdvkdid(dvkdid);
		String chietkhau = request.getParameter("chietkhau");
		try
		{
			dhBean.setChietkhau(Double.parseDouble(chietkhau));
		} catch (Exception er)
		{
			dhBean.setChietkhau(0);
		}
		String hinhthucvanchuyen=util.antiSQLInspection(request.getParameter("hinhthucvanchuyen")==null?"":request.getParameter("hinhthucvanchuyen"));
		dhBean.setHinhthucvanchuyen(hinhthucvanchuyen);
		List<IERP_DonDatHang_SP> spList = new ArrayList<IERP_DonDatHang_SP>();
		
	//	String[]spId=request.getParameterValues("spId");
		String[] masp = request.getParameterValues("masp");
		String[] tensp = request.getParameterValues("tensp");
		String[] soluong = request.getParameterValues("soluong");
		String[] soluongle = request.getParameterValues("soluongle");
		String[] donvitinh = request.getParameterValues("donvitinh");
		String[] ctkmId = request.getParameterValues("ctkmId");
		System.out.println("[soSp]"+masp.length);
		if (action.equals("reload") || action.equals("save") || action.equals("update"))
		{
			if (masp != null)
			{
				int m = 0;
				while (m < masp.length)
				{
					if (masp[m] != "")
					{
						System.out.println("[spId]"+masp[m]+"[schemeId]"+ctkmId[m]+"[m]"+m);
						IERP_DonDatHang_SP sanpham = new ERP_DonDatHang_SP();
						String sql= 
								"	select npp_fk,b.sanpham_fk,giamuanpp as giamuanpp, \n"+ 
										"		giamuanpp_tuvc as giamuanpp_tuvc , isnull(qct.SOLUONG1,1)/isnull(qct.soluong2,1) as qc, \n"+ 
										"		isnull(qct.SOLUONG1,1)/isnull(qct.soluong2,1) * sp.trongluong as trongluong, isnull(qct.SOLUONG1,1)/isnull(qct.soluong2,1) *sp.thetich as thetich, \n"+ 
										"		qcvc.soluong2/qcvc.soluong1 as goivc  \n"+
										"	 from \n"+
										"	(  \n"+
										"		 SELECT  B.KENH_FK,B.DVKD_FK ,B.NPP_FK,D.SANPHAM_FK,D.GIAMUANPP,isnull(Giamuanpp_tuvc,0) as Giamuanpp_tuvc \n"+ 
										"		 FROM     \n"+
										"		( 			 \n"+
										"				SELECT B.PK_SEQ,B.KENH_FK,B.DVKD_FK,C.NPP_FK FROM BANGGIAMUANPP B \n"+ 
										"				INNER JOIN BANGGIAMUANPP_NPP C ON B.PK_SEQ=C.BANGGIAMUANPP_FK  \n"+
										"				WHERE C.NPP_FK="+nhappid+" AND B.TUNGAY <='"+ngaygiaodich+"' AND  \n"+
										"				B.TUNGAY =     \n"+
										"				(   \n"+
										"					 SELECT MAX(TUNGAY) FROM BANGGIAMUANPP B \n"+ 
										"					INNER JOIN BANGGIAMUANPP_NPP C ON B.PK_SEQ=C.BANGGIAMUANPP_FK \n"+ 
										"					WHERE C.NPP_FK="+nhappid+" AND B.TUNGAY <='"+ngaygiaodich+"'   \n"+
										"				)    \n"+
										"		) AS B	INNER JOIN BGMUANPP_SANPHAM D ON B.PK_SEQ=D.BGMUANPP_FK \n"+ 
										"	 )b  \n"+
										"	 inner join SANPHAM sp on sp.PK_SEQ=b.SANPHAM_FK \n"+ 
										"	 left join quycach qc on qc.sanpham_fk=b.SANPHAM_FK and qc.DVDL2_FK='100018' \n"+  
										"	 left join quycach qcvc on qcvc.sanpham_fk=b.SANPHAM_FK and qcvc.DVDL2_FK='100052' \n"+ 
										"	 left join quycach qct on qct.sanpham_fk=b.sanpham_fk and qct.dvdl2_fk=100018    \n"+
										"  where    sp.ma='"+masp[m]+"'" ;
						//System.out.println("Don Gia Nek :" + sql);
						ResultSet rs = db.get(sql);
						try
						{
							if (rs.next())
							{
								sanpham.setIdSanPham(rs.getString("sanpham_fk"));
								sanpham.setQuyCach(rs.getDouble("qc"));
								sanpham.setDonGia((  rs.getDouble("giamuanpp")* sanpham.getQuyCach()  - rs.getDouble("goivc")* giavanchuyen )/ sanpham.getQuyCach() );
								sanpham.setGiachuan(rs.getDouble("giamuanpp"));
								sanpham.setTrongluong(rs.getDouble("trongluong"));
								sanpham.setThetich(rs.getDouble("thetich"));
								sanpham.setGoiVc(rs.getDouble("goivc"));
							}
						} catch (Exception er)
						{
							er.printStackTrace();
							sanpham.setIdSanPham("");
						}						
						
						sanpham.setMaSanPham(masp[m]);
						sanpham.setTenSanPham(tensp[m]);
						sanpham.setDonViTinh(donvitinh[m]);
						if (soluong[m] == "")
							soluong[m] = "0";
						
						if (soluongle[m] == "")
							soluongle[m] = "0";
						sanpham.setSoluongle(Float.parseFloat(soluongle[m].replace(",", "")));
						sanpham.setSoLuong(Float.parseFloat(soluong[m].replace(",", "")));
						sanpham.setCtkmId(ctkmId[m]);
						spList.add(sanpham);
					}
					m++;
				}
			}
			dhBean.setListSanPham(spList);
		}

		String[] scheme = request.getParameterValues("ckDetail.scheme");
		String[] scheme_sotien = request.getParameterValues("ckDetail.sotien");

		String ghichu = request.getParameter("ghichu");
		if (ghichu == null)
			ghichu = "";
		dhBean.setGhichu(ghichu);

		String noidungchietkhau = request.getParameter("noidungchietkhau");
		if (noidungchietkhau == null)
			noidungchietkhau = "";
		dhBean.setNoidungchietkhau(noidungchietkhau);

		dhBean.setScheme(scheme);
		dhBean.setSotien(scheme_sotien);
		session.setAttribute("tuvanchuyen", dhBean.getTuVanChuyen());
		dhBean.Init();
		if (action.equals("save"))
		{
			
			if (id == null)
			{
				if (!(dhBean.SaveKm()))
				{
					session.setAttribute("obj", dhBean);
					response.sendRedirect(nextJSP);
				} else
				{
					IDonmuahangList obj = new DonmuahangList();
					obj.setUserId(userId);
					obj.initDonHangKm("");
					session.setAttribute("obj", obj);
					nextJSP = "/TraphacoHYERP/pages/Center/DonMuaHangKhuyenMai.jsp";
					response.sendRedirect(nextJSP);
				}
			} else
			{
				String ischot = request.getParameter("ischot");
				if (!dhBean.Editkm(ischot))
				{
					session.setAttribute("obj", dhBean);
					response.sendRedirect(nextJSP);
				} else
				{
					IDonmuahangList obj = new DonmuahangList();
					obj.setUserId(userId);
					obj.initDonHangKm("");
					session.setAttribute("obj", obj);
					nextJSP = "/TraphacoHYERP/pages/Center/DonMuaHangKhuyenMai.jsp";
					response.sendRedirect(nextJSP);
				}
			}
		} else if (action.equals("reload"))
		{
			session.setAttribute("obj", dhBean);
			response.sendRedirect(nextJSP);
		} else if (action.equals("reload_npp"))
		{
			dhBean.load_tudongchuyen();
			session.setAttribute("obj", dhBean);
			session.setAttribute("tuvanchuyen", dhBean.getTuVanChuyen());
			response.sendRedirect(nextJSP);
		}
	}
}
