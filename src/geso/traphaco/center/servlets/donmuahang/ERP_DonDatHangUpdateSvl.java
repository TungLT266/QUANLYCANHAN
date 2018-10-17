package geso.traphaco.center.servlets.donmuahang;

import geso.traphaco.center.beans.donmuahang.IDonmuahangList;
import geso.traphaco.center.beans.donmuahang.IERP_DonDatHang;
import geso.traphaco.center.beans.donmuahang.IERP_DonDatHang_SP;
import geso.traphaco.center.beans.donmuahang.imp.DonmuahangList;
import geso.traphaco.center.beans.donmuahang.imp.ERP_DonDatHang;
import geso.traphaco.center.beans.donmuahang.imp.ERP_DonDatHang_SP;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.servlets.reports.ThanhToanKhyenMaiSvl;
import geso.traphaco.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;




@WebServlet("/ERP_DonDatHangUpdateSvl")
public class ERP_DonDatHangUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public ERP_DonDatHangUpdateSvl()
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

		String userId = util.getUserId(querystring);
		//System.out.println("Action : " + action);
		if (action.equals("edit"))
		{
			IERP_DonDatHang dhBean = new ERP_DonDatHang(ddhId);
			dhBean.Init();
			session.setAttribute("kenhid", dhBean.getIDKenhBanHang());
			session.setAttribute("nhappid", dhBean.getNppId());
			session.setAttribute("dvkdid", dhBean.getdvkdid());
			session.setAttribute("donhangid", dhBean.getId());
			session.setAttribute("tuvanchuyen", dhBean.getTuVanChuyen());
			session.setAttribute("ngaygiaodich", dhBean.getNgaygiaodich());
			String nextJSP = "/TraphacoHYERP/pages/Center/Erp_DonDatHangUpdate.jsp";
			session.setAttribute("obj", dhBean);
			response.sendRedirect(nextJSP);

		} else if (action.equals("display"))
		{
			IERP_DonDatHang dhBean = new ERP_DonDatHang();
			dhBean.setId(ddhId);
			dhBean.initDisplay();
			String nextJSP = "/TraphacoHYERP/pages/Center/Erp_DonDatHangDisplay.jsp";
			session.setAttribute("obj", dhBean);
			response.sendRedirect(nextJSP);
		}else if(action.equals("chotCKTM"))
		{
			IERP_DonDatHang dhBean = new ERP_DonDatHang(ddhId);
			dhBean.DuyetCKTM();
			String nextJSP = "/TraphacoHYERP/pages/Center/Erp_DonDatHangUpdate.jsp";
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
		String tenform = request.getParameter("tenform");
		String nextJSP = "";
		if (tenform.equals("newform"))
		{
			nextJSP = "/TraphacoHYERP/pages/Center/Erp_DonDatHangNew.jsp";
		} else
		{
			nextJSP = "/TraphacoHYERP/pages/Center/Erp_DonDatHangUpdate.jsp";
		}
		IERP_DonDatHang dhBean = new ERP_DonDatHang();		
		String ngaygiaodich = util.antiSQLInspection(request.getParameter("ngaygiaodich"));
		dhBean.setNgaygiaodich(ngaygiaodich);
		dhBean.setId(id);
		
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

		
		
		
		

		String kenhbanhang = util.antiSQLInspection(request.getParameter("kenhbanhang"));
		if (kenhbanhang == null)
		{
			kenhbanhang = "";
		}
		System.out.println("Kenh nek: "+kenhbanhang);
			
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
		
		
		String tuvanchuyen = util.antiSQLInspection(request.getParameter("tuvanchuyen"));
		if (tuvanchuyen == null)
		{
			tuvanchuyen = "0";
		}		
		dhBean.setTuVanChuyen(tuvanchuyen);		
		
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
		
		String isDuyetCKTM = util.antiSQLInspection(request.getParameter("isDuyetCKTM")==null?"0":request.getParameter("isDuyetCKTM"));
		dhBean.setIsDuyetCKTM(isDuyetCKTM);
 
		session.setAttribute("kenhid", kenhbanhang);
		session.setAttribute("nhappid", nhappid);
		session.setAttribute("dvkdid", dvkdid);
		session.setAttribute("donhangid", id);
		session.setAttribute("ngaygiaodich", ngaygiaodich);
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
		if(tuvanchuyen.equals("1"))
		{
			hinhthucvanchuyen="KHVC";
		}
		dhBean.setHinhthucvanchuyen(hinhthucvanchuyen);
		List<IERP_DonDatHang_SP> spList = new ArrayList<IERP_DonDatHang_SP>();
		
		String[]spId=request.getParameterValues("spId");
		String[] masp = request.getParameterValues("masp");
		String[] tensp = request.getParameterValues("tensp");
		String[] soluong = request.getParameterValues("soluong");
		String[] donvitinh = request.getParameterValues("donvitinh");
		String[] donviduyetId = request.getParameterValues("donviduyetId");
		String[] soluongduyet = request.getParameterValues("soluongduyet");
		String[] chietkhaudh = request.getParameterValues("chietkhaudh");
		Hashtable<String, Integer> spThieuList = new Hashtable<String, Integer>();

		if (action.equals("reload") || action.equals("save") || action.equals("update"))
		{
			if (masp != null)
			{
				int m = 0;
				while (m < masp.length)
				{
					if (masp[m] != "")
					{
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
						"			SELECT B.PK_SEQ,B.KENH_FK,B.DVKD_FK,C.NPP_FK  \n"+ 
						"			FROM BANGGIAMUANPP B  \n"+
						"				INNER JOIN BANGGIAMUANPP_NPP C ON B.PK_SEQ=C.BANGGIAMUANPP_FK \n"+  
						"			WHERE  B.TUNGAY <='"+ngaygiaodich+"' AND   \n"+ 
						"				B.PK_SEQ = \n"+ 
						"			(   \n"+
						"			SELECT TOP(1) BG.PK_SEQ FROM BANGGIAMUANPP BG  \n"+
						"				INNER JOIN BANGGIAMUANPP_NPP BGNPP ON BG.PK_SEQ=BGNPP.BANGGIAMUANPP_FK  \n"+
						"			WHERE BG.TUNGAY <= '"+ngaygiaodich+"' AND BGNPP.NPP_FK = C.NPP_FK AND BG.KENH_FK=B.KENH_FK \n"+
						"			ORDER BY TUNGAY DESC \n"+
						"			)  AND C.NPP_FK="+nhappid+" AND B.DVKD_FK="+dvkdid+" AND B.KENH_FK="+kenhbanhang+" \n"+
						"		) AS B	INNER JOIN BGMUANPP_SANPHAM D ON B.PK_SEQ=D.BGMUANPP_FK \n"+ 
						"	 )b  \n"+
						"	 inner join SANPHAM sp on sp.PK_SEQ=b.SANPHAM_FK \n"+ 
						"	 left join quycach qc on qc.sanpham_fk=b.SANPHAM_FK and qc.DVDL2_FK='100018' \n"+  
						"	 left join quycach qcvc on qcvc.sanpham_fk=b.SANPHAM_FK and qcvc.DVDL2_FK='100052' \n"+ 
						"	 left join quycach qct on qct.sanpham_fk=b.sanpham_fk and qct.dvdl2_fk=100018    \n"+
						"  where    sp.ma='"+masp[m]+"'" ;
					//	System.out.println("Don Gia Nek :" + sql);
						ResultSet rs = db.get(sql);
						try
						{
							if (rs.next())
							{
								sanpham.setIdSanPham(rs.getString("sanpham_fk"));
								sanpham.setQuyCach(rs.getDouble("qc"));
								if (dhBean.getTuVanChuyen().equals("1"))
								{
									sanpham.setDonGia((  rs.getDouble("giamuanpp")* sanpham.getQuyCach()  - rs.getDouble("goivc")* giavanchuyen )/ sanpham.getQuyCach() );
								} else
								{
									sanpham.setDonGia(rs.getDouble("giamuanpp"));
								}
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
						
						sanpham.setDonviduyetId(donviduyetId[m]);
						sanpham.setMaSanPham(masp[m]);
						sanpham.setTenSanPham(tensp[m]);
						sanpham.setDonViTinh(donvitinh[m]);
						float soluong1 = 0;
						try
						{
							soluong1 = Float.parseFloat(soluong[m]);
						} catch (Exception er)
						{

						}
						float soluong2 = 0;
						try
						{
							soluong2 =  Float.parseFloat(soluongduyet[m]);
						} catch (Exception er)
						{

						}
						sanpham.setSoLuong(soluong1);
						sanpham.setSoluongduyet(soluong2);
						
						double chietkhau_dh = 0;
						try
						{
							chietkhau_dh = Double.parseDouble(chietkhaudh[m]);
						} catch (Exception er)
						{

						}
						sanpham.setChietKhau(chietkhau_dh);

						spList.add(sanpham);

						if (soluong[m] == "")
							soluong[m] = "0";

					}
					m++;
				}
			}
			dhBean.setListSanPham(spList);
			dhBean.setSpThieuList(spThieuList);
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
				if (!(dhBean.Save()))
				{
					session.setAttribute("obj", dhBean);
					response.sendRedirect(nextJSP);
				} else
				{
					IDonmuahangList obj = new DonmuahangList();
					obj.setUserId(userId);
					obj.createDdhlist("");
					session.setAttribute("obj", obj);
					nextJSP = "/TraphacoHYERP/pages/Center/DonMuaHang.jsp";
					response.sendRedirect(nextJSP);
				}
			} else
			{
				String ischot = request.getParameter("ischot");
				if (!dhBean.Edit(ischot))
				{
					session.setAttribute("obj", dhBean);
					response.sendRedirect(nextJSP);
				} else
				{
					IDonmuahangList obj = new DonmuahangList();
					obj.setUserId(userId);
					obj.createDdhlist("");
					session.setAttribute("obj", obj);
					nextJSP = "/TraphacoHYERP/pages/Center/DonMuaHang.jsp";
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
		}else if(action.equals("chotCKTM"))
		{
			
			dhBean.Edit("0");
			/*
			 * Lưu lai những thông tin thay đổi.
			 * Duyet chiet khau TKM.
			 * Load lai thong tin đơn hàng.  
			 */
			
			dhBean.DuyetCKTM();
			dhBean = new ERP_DonDatHang(id);
			dhBean.Init();
			session.setAttribute("kenhid", dhBean.getIDKenhBanHang());
			session.setAttribute("nhappid", dhBean.getNppId());
			session.setAttribute("dvkdid", dhBean.getdvkdid());
			session.setAttribute("donhangid", dhBean.getId());
			session.setAttribute("tuvanchuyen", dhBean.getTuVanChuyen());
			session.setAttribute("ngaygiaodich", dhBean.getNgaygiaodich());
			nextJSP = "/TraphacoHYERP/pages/Center/Erp_DonDatHangUpdate.jsp";
			session.setAttribute("obj", dhBean);
			response.sendRedirect(nextJSP);
		}
	}
	
	
	public String IsTuVanChuyen(String nppId)
	{
		dbutils db = new dbutils();
		String query ="select isnull(TuVanChuyen,'0') as TuVanChuyen From NhaPhanPhoi where pk_Seq='"+nppId+"'";
		ResultSet rs = db.get(query);
		String TuVanChuyen="";
		try
		{
			while(rs.next())
			{
				TuVanChuyen=rs.getString("TuVanChuyen");
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			db.shutDown();
		}
		return TuVanChuyen;
	}
	
	/*
	 * Kiểm tra tháng trước đã tính CKTM hay chưa
	 * Nếu có rồi thì lấy Thưởng-Sử Dụng để đưa vào CKTM cho đơn hàng này 
	 * Nếu chưa có thì tính CKTM đồng thời chèn vào bảng ThanhToanCKTM
	 */
	public double getChietKhauThuongMai(String nppId,String ngaydat,String ddhId)
	{
		dbutils db=new dbutils();
		double thuong=0;
		boolean flag=false;
		String thoigian="";
		String query="select convert(varchar(10),DATEADD(month,-1,'"+ngaydat.split("-")[0]+"-"+ngaydat.split("-")[1]+"-01'),20) as ThoiGian ";		
		
		ResultSet rs=db.get(query);
		try
		{
			while(rs.next())
			{
				thoigian=rs.getString("thoigian");
			}rs.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		String ngaycuoithang ="";
		 query ="select convert(varchar(10),DATEADD(day,-1,'"+ngaydat.split("-")[0]+"-"+ngaydat.split("-")[1]+"-01'),20) as ThoiGian ";
		 rs=db.get(query);
		try
		{
			while(rs.next())
			{
				ngaycuoithang=rs.getString("thoigian");
			}rs.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		
		query="select DISTINCT t.THANG,t.NAM,t.KBH_FK,t.DVKD_FK,b.Thuong,b.Tu from TIEUCHITINHTHUONG t "+ 
				" inner join TIEUCHITINHTHUONG_CHITIET b on t.PK_SEQ=b.TIEUCHITINHTHUONG_FK  "+
				" where t.THANG="+thoigian.split("-")[1]+" and t.NAM="+thoigian.split("-")[0]+" and t.LOAI=5 and b.STT=1 and b.Tieuchi=1 and t.TRANGTHAI=1";
					
			String mucngay="0";
			String thuongdunghan="0";
			rs=db.get(query);
			try{
				if(rs.next())
				{
					mucngay=rs.getString("Tu");
					thuongdunghan=""+(rs.getDouble("Thuong")/100);
				}
			}catch(Exception er)
			{
				er.printStackTrace();
			}
		
		
		//System.out.println("[Check CKTM 112]"+query);
		String nam=thoigian.split("-")[0];
		String thang=thoigian.split("-")[1];
		
		 query=
		"select count(*) from ThanhToanCKTM WHERE NPP_FK='"+nppId+"' and thang='"+thang+"' and nam='"+nam+"' " ;
		 rs=db.get(query);
		try
		{
			while(rs.next())
			{
				if(rs.getInt(1)<=0)
				flag=true;
			}
			rs.close();
		} catch (Exception e1)
		{
			e1.printStackTrace();
		} 
		System.out.println("[Check CKTM 111]"+query);
		if(flag)
		{
			 query=
 			" select sum(thuongdspri)as thuongdspri  \n" +
			"  from  \n" +
			"  ( 		  \n" +
			" 	select isnull(thuong.thuongdspri,0) * isnull(thucdat.doanhso,0) +isnull(thucdat.doanhso,0)*ThuongDungHan.Thuong/100 as thuongdspri \n" +
			" 	from  \n" +
			" 	(	 \n" +
			" 		select ctprinpp.CHITIEU_FK,thang, nam, ctprinpp.nhapp_fk as npp_fk, kenh_fk as kbh_fk, ctpri.dvkd_fk, ctprinpp.chitieu       \n" +
			" 		from chitieu ctpri inner join chitieu_nhapp ctprinpp on ctpri.pk_seq = ctprinpp.chitieu_fk      \n" +
			" 		where ctpri.trangthai<> '2'   and   ctpri.thang ='"+thang+"' and ctpri.nam ='"+nam+"' and ctprinpp.NHAPP_FK='"+nppId+"' \n" +
			" 	)as chitieu left join  \n" +
			" 	(  \n" +
			" 			select nh.npp_fk,nh.kbh_fk,sp1.dvkd_fk , sum(isnull(nhsp.gianet,0)* isnull(nhsp.soluong,0)) -sum(isnull(cktt,0))  as doanhso        \n" +
			" 			from  \n" +
			" 		(    \n" +
			" 			select * from nhaphang where NGAYCHUNGTU>= '"+thoigian+"' and NGAYCHUNGTU <='"+nam+"-"+thang+"-31' and npp_fk='"+nppId+"'     \n" +
			" 			and trangthai !=2 and pk_seq not in(select nhaphang_fk from huynhaphang where TRANGTHAI=1 ) and loaihoadon=0    \n" +
			" 		) nh inner join nhaphang_sp nhsp on nhsp.nhaphang_fk = nh.pk_seq  left join sanpham sp1 on sp1.ma = nhsp.sanpham_fk           \n" +
			" 		group by nh.npp_fk,nh.kbh_fk,sp1.dvkd_fk   \n" +
			" 	)as thucdat  on thucdat.dvkd_fk=chitieu.dvkd_fk and thucdat.kbh_fk=chitieu.kbh_fk  and thucdat.npp_fk=chitieu.npp_fk  \n" +
			" left join  \n" +
			" ( \n" +
			" 	select  npp_fk ,"+thuongdunghan+" as thuong    		 \n" +
			"  \n" +
			" from khoasongay    		 \n" +
			" 	where ngayks='"+ngaycuoithang+"' and  replace(convert(char(10),  ngaygio  , 102) , '.', '-' ) <= replace(convert(char(10), dateadd(day, "+mucngay+", cast ('"+ngaycuoithang+"' as datetime)) , 102) , '.', '-' )     \n" +
			" 	and npp_fk="+nppId+" \n" +
			" )  KhoaSo on KhoaSo.NPP_FK =chitieu.npp_fk \n" +
			" left join \n" +
			" ( \n" +
			" 	select DISTINCT t.THANG,t.NAM,t.KBH_FK,t.DVKD_FK,b.Thuong,b.Tu from TIEUCHITINHTHUONG t \n" +
			" 		inner join TIEUCHITINHTHUONG_CHITIET b on t.PK_SEQ=b.TIEUCHITINHTHUONG_FK  \n" +
			" 	where t.THANG="+thang+" and t.NAM="+nam+" and t.LOAI=5 and b.STT=1 and b.Tieuchi=1 and t.TRANGTHAI=1 \n" +
			" ) as ThuongDungHan on ThuongDungHan.DVKD_FK=chitieu.DVKD_FK and ThuongDungHan.KBH_FK=chitieu.kbh_fk  \n" +
			" left join  \n" +
			" (	  \n" +
			" 	select  t.thang,t.nam,t.kbh_fk,t.dvkd_fk,b.thuong /100  as thuongdspri,b.tieuchi,b.tu,b.den  \n" +
			" 	from tieuchitinhthuong t    \n" +
			" 		inner join tieuchitinhthuong_chitiet b on t.pk_seq=b.tieuchitinhthuong_fk    \n" +
			" 	where t.thang='"+thang+"' and t.nam='"+nam+"' and t.loai=5  and b.tieuchi=4 and t.trangthai=1  \n" +
			" )as thuong on thuong.dvkd_fk=chitieu.dvkd_fk  \n" +
			" and thuong.kbh_fk=chitieu.kbh_fk and isnull(thucdat.doanhso,0) *100 / (isnull(chitieu.chitieu,0)+0.001) > = thuong.tu  " +
			" and   isnull(thucdat.doanhso,0) *100 / (isnull(chitieu.chitieu,0)+0.001) <  thuong.den )as thuong  " ;
			 System.out.println("[Check CKTM 113]"+query);
			 rs=db.get(query);
			 try
			{
				while(rs.next())
				 {
					thuong= rs.getDouble("thuongdspri");
					if(thuong>0)
					{
						 query=
						" insert into ThanhToanCKTM(Thang,Nam,npp_fk,thuong,sudung) " +
					 	" select '"+thang+"','"+nam +"','"+nppId+"','"+thuong+"',0  ";
						 if(!db.update(query))
						 {
							 System.out.println("Error"+query);
						 }
					}
					 System.out.println("[Check CKTM 114]"+query);
				 }
				rs.close();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
			
		}else
		{
			query=
				"select (Thuong-isnull(SuDung,0)  	" ;
			if(ddhId!=null&&ddhId.length()>0 )
			query+=
				" + ISNULL(	(SELECT ChietKhauThuongMai 	"+
				"	FROM 								"+  
				"	DonDatHang   						"+
				"	WHERE PK_SEQ="+ddhId+") ,0) 		";	
			query+=" ) as Thuong From ThanhToanCKTM WHERE NPP_FK='"+nppId+"' and thang='"+thang+"' and nam='"+nam+"' ";
			rs=db.get(query);
			System.out.println("[CKTM 115]"+query);
			try
			{
				while(rs.next())
				{
					thuong=rs.getDouble("thuong");
				}rs.close();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		return thuong;
	}
}
