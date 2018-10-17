package geso.traphaco.center.servlets.tieuchithuong;

import geso.traphaco.center.beans.tieuchithuong.ITieuchithuongTL;
import geso.traphaco.center.beans.tieuchithuong.ITieuchithuongTLList;
import geso.traphaco.center.beans.tieuchithuong.imp.TieuchithuongTL;
import geso.traphaco.center.beans.tieuchithuong.imp.TieuchithuongTLList;
import geso.traphaco.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class TieuchithuongTLUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out; 
	
    public TieuchithuongTLUpdateSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		ITieuchithuongTL tctskuBean;
		
		this.out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    	    
	    String id = util.getId(querystring);
	    
	    tctskuBean = new TieuchithuongTL(id);
	    tctskuBean.setId(id);
	    tctskuBean.setUserId(userId);
	    
        session.setAttribute("tctskuBean", tctskuBean);
        
        String phanoai = request.getParameter("phanloai");
        if(phanoai == null)
        	phanoai = "0";
        
        String nextJSP = "";
        if(querystring.indexOf("display") >= 0)
        {
        	tctskuBean.initDisplay();
        	
        	if(!phanoai.equals("3"))
        		nextJSP = "/TraphacoHYERP/pages/Center/TieuChiThuongTLDisplay.jsp";
        	else
        		nextJSP = "/TraphacoHYERP/pages/Center/TieuChiThuongTLGiaiDoanDisplay.jsp";
        }
        else
        {
        	tctskuBean.init();
        	
        	if(!phanoai.equals("3"))
        		nextJSP = "/TraphacoHYERP/pages/Center/TieuChiThuongTLUpdate.jsp";
        	else
        		nextJSP = "/TraphacoHYERP/pages/Center/TieuChiThuongTLGiaiDoanUpdate.jsp";
        }
        
        response.sendRedirect(nextJSP);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		ITieuchithuongTL tctskuBean;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		this.out = response.getWriter();
		Utility util = new Utility();
		
	   	String id = util.antiSQLInspection(request.getParameter("id"));
	   
	    if(id == null){  	
	    	tctskuBean = new TieuchithuongTL("");
	    }else{
	    	tctskuBean = new TieuchithuongTL(id);
	    }
	    
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		if(userId == null)
			userId = "";
		System.out.println("userid "+userId);
		tctskuBean.setUserId(userId);
		
		String phanloai = request.getParameter("phanloai");
		if(phanloai == null)
			phanloai = "0";
		tctskuBean.setPhanloai(phanloai);
		
		String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
		if (diengiai == null)
			diengiai = "";
		tctskuBean.setDiengiai(diengiai);
		
		String scheme = util.antiSQLInspection(request.getParameter("scheme"));
		if (scheme == null)
			scheme = "";
		tctskuBean.setScheme(scheme);
		
		String thang = util.antiSQLInspection(request.getParameter("thang"));
		if (thang == null)
			thang = "";
		tctskuBean.setThang(thang);
		String ghichu = util.antiSQLInspection(request.getParameter("ghichu"));
		if (ghichu == null)
			ghichu= "";
		tctskuBean.setGhichu(ghichu);
		
		String nam = util.antiSQLInspection(request.getParameter("nam"));
		if (nam == null)
			nam = "";
		tctskuBean.setNam(nam);
		
		String mucVuot = util.antiSQLInspection(request.getParameter("mucVuot"));
		if (mucVuot == null)
			mucVuot = "";
		tctskuBean.setMucvuot(mucVuot);
		
		String chietkhauMucVuot = util.antiSQLInspection(request.getParameter("chietkhauMucVuot"));
		if (chietkhauMucVuot == null)
			chietkhauMucVuot = "";
		tctskuBean.setChietkhauMucvuot(chietkhauMucVuot);
		
		String donviMucVuot = util.antiSQLInspection(request.getParameter("donviMucVuot"));
		if (donviMucVuot == null)
			donviMucVuot = "";
		tctskuBean.setDonviMucvuot(donviMucVuot);
		
		String phaidangky = util.antiSQLInspection(request.getParameter("phaidangky"));
		if (phaidangky == null)
			phaidangky = "0";
		tctskuBean.setPhandangky(phaidangky);
		
		String ngaytb_tungay = util.antiSQLInspection(request.getParameter("ngaytb_tungay"));
		if (ngaytb_tungay == null)
			ngaytb_tungay = "";
		tctskuBean.setNgayTB_Tungay(ngaytb_tungay);
		
		String ngaytb_denngay = util.antiSQLInspection(request.getParameter("ngaytb_denngay"));
		if (ngaytb_denngay == null)
			ngaytb_denngay = "";
		tctskuBean.setNgayTB_Denngay(ngaytb_denngay);
		
		String[] kbhId = request.getParameterValues("kbhId");
		String str3 = "";
		if(kbhId != null)
		{
			for(int i = 0; i < kbhId.length; i++)
				str3 += kbhId[i] + ",";
			if(str3.length() > 0)
				str3 = str3.substring(0, str3.length() - 1);
		}
		tctskuBean.setKbhIds(str3);
		
		String[] loaikhId = request.getParameterValues("loaikhId");
		String str4 = "";
		if(loaikhId != null)
		{
			for(int i = 0; i < loaikhId.length; i++)
				str4 += loaikhId[i] + ",";
			if(str4.length() > 0)
				str4 = str4.substring(0, str4.length() - 1);
		}
		tctskuBean.setLoaikhIds(str4);
		
		try
		{
		//Tra san pham
		String hinhthucTra = request.getParameter("hinhthuctra");
		tctskuBean.setHinhthuctra(hinhthucTra);
		
		String[] maspTra = request.getParameterValues("maspTra");
		tctskuBean.setMaspTra(maspTra);
		
		String[] tenspTra = request.getParameterValues("tenspTra");
		tctskuBean.setTenspTra(tenspTra);
		
		String[] soluongspTra = request.getParameterValues("soluongspTra");
		tctskuBean.setSoluongspTra(soluongspTra);
		
		String[] dongiaspTra = request.getParameterValues("dongiaspTra");
		tctskuBean.setDongiaspTra(dongiaspTra);
				
		String[] spIds = request.getParameterValues("spIds");
		if( !phanloai.equals("1") ) //TICH LUY VA TRUNG BAY
		{
			if(spIds != null)
			{
				String spId = "";
				for(int i = 0; i < spIds.length; i++)
				{
					spId += spIds[i] + ",";
				}

				if(spId.trim().length() > 0)
				{
					spId = spId.substring(0, spId.length() - 1);
					tctskuBean.setSpIds(spId);
				}
			}
		}
		else
		{
			String[] dieukien = request.getParameterValues("dieukien");
			String[] donviDIEUKIEN = request.getParameterValues("donviDIEUKIEN");

			String[] quydoi = request.getParameterValues("quydoi");
			String[] donviQUYDOI = request.getParameterValues("donviQUYDOI");

			if(maspTra != null)
			{
				Hashtable<String, String> htdieukien = new Hashtable<String, String>();
				Hashtable<String, String> htquydoi = new Hashtable<String, String>();

				//String spId = "";
				for(int i = 0; i < maspTra.length; i++)
				{
					//spId += spIds[i] + ",";

					if(maspTra[i].trim().length() > 0)
					{
						System.out.println(":::: MA SP: " + maspTra[i] );
						if(dieukien[i].trim().length() > 0 && donviDIEUKIEN[i].trim().length() > 0)
						{
							htdieukien.put(maspTra[i], dieukien[i].trim() + "__" + donviDIEUKIEN[i].trim());
							System.out.println(":::: DIEU KIEN: " + dieukien[i] + "  -- don vi dieu kien: " + donviDIEUKIEN[i] );
						}
						if(quydoi[i].trim().length() > 0 && donviQUYDOI[i].trim().length() > 0)
						{
							htquydoi.put(maspTra[i], quydoi[i].trim() + "__" + donviQUYDOI[i].trim());
							System.out.println(":::: QUY DOI: " + quydoi[i] + "  -- don vi quy doi: " + donviQUYDOI[i] );
						}
					}
				}
				
				tctskuBean.setDieukien(htdieukien);
				tctskuBean.setQuydoi(htquydoi);
			}
		}
		
		String mucNpp = util.antiSQLInspection(request.getParameter("mucNPP"));
		if (mucNpp == null)
			mucNpp = "";
		tctskuBean.setMucNPP(mucNpp);
		System.out.println("mucNpp "+mucNpp);
		String mucnpp1 = util.antiSQLInspection(request.getParameter("mucnpp1"));
		
		if(mucnpp1 == null )
			mucnpp1 = "";
		System.out.println("mucnpp1 "+mucnpp1);
		String[] nppIds1 = request.getParameterValues("nppIds1");
		
		if(nppIds1 != null)
		{
			String nppId = "";
			for(int i = 0; i < nppIds1.length; i++)
			{
				System.out.println("nppIds1[i] "+nppIds1[i]);
				if(nppIds1[i].length() > 0)
					nppId += nppIds1[i] + ",";
			}
			
			if(nppId.trim().length() > 0)
			{
				nppId = nppId.substring(0, nppId.length() - 1);
				tctskuBean.setNppIds1(nppId);
			}
		}
		String[] nppIds2 = request.getParameterValues("nppIds2");
		if(nppIds2 != null)
		{
			System.out.println("do dai nppIds2"+nppIds2.length);
			String nppId = "";
			for(int i = 0; i < nppIds2.length; i++)
			{
				if(nppIds2[i].length() > 0)
					nppId += nppIds2[i] + ",";
				System.out.println("nppIds2[i] "+nppIds2[i]);
			}
			
			if(nppId.trim().length() > 0)
			{
				nppId = nppId.substring(0, nppId.length() - 1);
				tctskuBean.setNppIds2(nppId);
			}
		}
		String[] nppIds3 = request.getParameterValues("nppIds3");
		if(nppIds3 != null)
		{
			String nppId = "";
			for(int i = 0; i < nppIds3.length; i++)
			{
				if(nppIds3[i].length() > 0)
					nppId += nppIds3[i] + ",";
				System.out.println("nppIds3[i] "+nppIds3[i]);
			}
			
			if(nppId.trim().length() > 0)
			{
				nppId = nppId.substring(0, nppId.length() - 1);
				tctskuBean.setNppIds3(nppId);
			}
		}
		String[] nppIds4 = request.getParameterValues("nppIds4");
		if(nppIds4 != null)
		{
			String nppId = "";
			for(int i = 0; i < nppIds4.length; i++)
			{
				if(nppIds4[i].length() > 0)
					nppId += nppIds4[i] + ",";
			}
			
			if(nppId.trim().length() > 0)
			{
				nppId = nppId.substring(0, nppId.length() - 1);
				tctskuBean.setNppIds4(nppId);
			}
		}
		String[] nppIds5 = request.getParameterValues("nppIds5");
		if(nppIds5 != null)
		{
			String nppId = "";
			for(int i = 0; i < nppIds5.length; i++)
			{
				if(nppIds5[i].length() > 0)
					nppId += nppIds5[i] + ",";
			}
			
			if(nppId.trim().length() > 0)
			{
				nppId = nppId.substring(0, nppId.length() - 1);
				tctskuBean.setNppIds5(nppId);
			}
		}
		String[] nppIds = request.getParameterValues("nppIds");
		if(nppIds != null)
		{
			String nppId = "";
			for(int i = 0; i < nppIds.length; i++)
			{
				if(nppIds[i].length() > 0)
					nppId += nppIds[i] + ",";
			}
			
			if(nppId.trim().length() > 0)
			{
				nppId = nppId.substring(0, nppId.length() - 1);
				System.out.println("nppId "+nppId);
				if(mucnpp1.equals("0"))
				{
					System.out.println("nppId1 "+nppId);
					tctskuBean.setNppIds1(nppId);
				}
				if(mucnpp1.equals("1"))
				{
					System.out.println("nppId2"+nppId);
					tctskuBean.setNppIds2(nppId);
				}
				if(mucnpp1.equals("2"))
				{
					System.out.println("nppId3"+nppId);
					tctskuBean.setNppIds3(nppId);
				}
				if(mucnpp1.equals("3"))
				{
					System.out.println("nppId4"+nppId);
					tctskuBean.setNppIds4(nppId);
				}
				if(mucnpp1.equals("4"))
				{
					System.out.println("nppId5"+nppId);
					tctskuBean.setNppIds5(nppId);
				}
				
			}
		}
		//LUU LAI SO LUONG PHAN BO
		String[] nppIds_ma = request.getParameterValues("nppIds_ma");
		String[] nppIds_phanbo = request.getParameterValues("nppIds_phanbo");
		
		// luu lai so luong phan bo theo muc
		Hashtable<String, String>PhanboTheoMucNPP1 = new Hashtable<String, String>();
		Hashtable<String, String>PhanboTheoMucNPP2 = new Hashtable<String, String>();
		Hashtable<String, String>PhanboTheoMucNPP3 = new Hashtable<String, String>();
		Hashtable<String, String>PhanboTheoMucNPP4 = new Hashtable<String, String>();
		Hashtable<String, String>PhanboTheoMucNPP5 = new Hashtable<String, String>();
		String[] soluongpb1 = request.getParameterValues("soluongpb1");
		String[] mapb1 = request.getParameterValues("mapb1");
		if(soluongpb1 != null)
		{
			for(int i = 0; i < soluongpb1.length; i++)
			{
				if(soluongpb1[i].trim().length() > 0)
				{
					PhanboTheoMucNPP1.put(mapb1[i], soluongpb1[i].trim());
					System.out.println("PhanboTheoMucNPP1 "+PhanboTheoMucNPP1.get(mapb1[i]));


				}
			}
		}	
		String[] soluongpb2 = request.getParameterValues("soluongpb2");
		String[] mapb2 = request.getParameterValues("mapb2");
		if(soluongpb2 != null)
		{
			for(int i = 0; i < soluongpb2.length; i++)
			{
				if(soluongpb2[i].trim().length() > 0)
				{
					PhanboTheoMucNPP2.put(mapb2[i], soluongpb2[i].trim());
					System.out.println("PhanboTheoMucNPP2 "+PhanboTheoMucNPP2.get(mapb2[i]));


				}
			}	
		}	

		String[] soluongpb3 = request.getParameterValues("soluongpb3");
		String[] mapb3 = request.getParameterValues("mapb3");
		if(soluongpb3 != null)
		{
			for(int i = 0; i < soluongpb3.length; i++)
			{
				if(soluongpb3[i].trim().length() > 0)
				{
					PhanboTheoMucNPP3.put(mapb3[i], soluongpb3[i].trim());
					System.out.println("PhanboTheoMucNPP3 "+PhanboTheoMucNPP3.get(mapb3[i]));


				}
			}
		}

		String[] soluongpb4 = request.getParameterValues("soluongpb4");
		String[] mapb4 = request.getParameterValues("mapb4");
		if(soluongpb3 != null)
		{
			for(int i = 0; i < soluongpb4.length; i++)
			{
				if(soluongpb4[i].trim().length() > 0)
				{
					PhanboTheoMucNPP4.put(mapb4[i], soluongpb4[i].trim());
					System.out.println("PhanboTheoMucNPP4 "+PhanboTheoMucNPP4.get(mapb4[i]));


				}
			}	
		}
		String[] soluongpb5 = request.getParameterValues("soluongpb5");
		String[] mapb5 = request.getParameterValues("mapb5");
		if(soluongpb3 != null)
		{
			for(int i = 0; i < soluongpb5.length; i++)
			{
				if(soluongpb5[i].trim().length() > 0)
				{
					PhanboTheoMucNPP5.put(mapb5[i], soluongpb5[i].trim());
					System.out.println("PhanboTheoMucNPP5 "+PhanboTheoMucNPP5.get(mapb5[i]));
				}
			}
		}

		if(nppIds_ma != null)
		{

			for(int i = 0; i < nppIds_ma.length; i++)
			{
				if(nppIds_phanbo[i].trim().length() > 0)
				{

					if(mucnpp1.equals("0"))
					{
						PhanboTheoMucNPP1.put(nppIds_ma[i], nppIds_phanbo[i].trim());
						System.out.println("PhanboTheoMucNPP1 "+nppIds_ma[i]+" "+PhanboTheoMucNPP1.get(nppIds_ma[i]));
					}
					else
						if(mucnpp1.equals("1"))
							PhanboTheoMucNPP2.put(nppIds_ma[i], nppIds_phanbo[i].trim());
						else
							if(mucnpp1.equals("2"))
								PhanboTheoMucNPP3.put(nppIds_ma[i], nppIds_phanbo[i].trim());
							else
								if(mucnpp1.equals("3"))
									PhanboTheoMucNPP4.put(nppIds_ma[i], nppIds_phanbo[i].trim());
								else
									if(mucnpp1.equals("4"))
										PhanboTheoMucNPP5.put(nppIds_ma[i], nppIds_phanbo[i].trim());
				}

			}

			tctskuBean.setPhanboTheoMucNPP1(PhanboTheoMucNPP1);

			tctskuBean.setPhanboTheoMucNPP2(PhanboTheoMucNPP2);

			tctskuBean.setPhanboTheoMucNPP3(PhanboTheoMucNPP3);
			tctskuBean.setPhanboTheoMucNPP4(PhanboTheoMucNPP4);

			tctskuBean.setPhanboTheoMucNPP5(PhanboTheoMucNPP5);

		}
		
		//Muc 1 thang
    	String[] diengiaiMuc = request.getParameterValues("diengiaiMuc");
    	tctskuBean.setDiengiaiMuc(diengiaiMuc);
    	
    	String[] httt1 = request.getParameterValues("httt1");
    	tctskuBean.sethttt1(httt1);
    	
		String[] tumuc = request.getParameterValues("tumuc");
		tctskuBean.setTumuc(tumuc);
		
		String[] denmuc = request.getParameterValues("denmuc");
		tctskuBean.setDenmuc(denmuc);
		
		String[] thuongSR = request.getParameterValues("chietkhau");
		tctskuBean.setThuongSR(thuongSR);
		
		String[] thuongTDSR = request.getParameterValues("donvi");
		tctskuBean.setThuongTDSR(thuongTDSR);
		
		String[] soluongtoidaMuc = request.getParameterValues("soluongtoidaMuc");
    	tctskuBean.setThuongASM(soluongtoidaMuc);
    	
    	String[] tungayMuc = request.getParameterValues("tungayMuc");
    	tctskuBean.setThuongSS(tungayMuc);
    	
    	String[] denngayMuc = request.getParameterValues("denngayMuc");
    	tctskuBean.setThuongTDSS(denngayMuc);
		
		//Muc 3 thang
		String[] diengiaiMuc3 = request.getParameterValues("diengiaiMuc3");
    	tctskuBean.setDiengiaiMuc3(diengiaiMuc3);
    	
		String[] thuongSR3 = request.getParameterValues("thuongSR3");
		tctskuBean.setThuongSR3(thuongSR3);
		
		String[] thuongTDSR3 = request.getParameterValues("thuongTDSR3");
		tctskuBean.setThuongTDSR3(thuongTDSR3);
		
		String[] thuongSS3 = request.getParameterValues("thuongSS3");
		tctskuBean.setThuongSS3(thuongSS3);
		
		String[] thuongTDSS3 = request.getParameterValues("thuongTDSS3");
		tctskuBean.setThuongTDSS3(thuongTDSS3);
		
		String[] thuongASM3 = request.getParameterValues("thuongASM3");
		tctskuBean.setThuongASM3(thuongASM3);
		
		String[] thuongTDASM3 = request.getParameterValues("thuongTDASM3");
		tctskuBean.setThuongTDASM3(thuongTDASM3);
		
		//VUNG 
		String[] vungId = request.getParameterValues("vungId");
		String str = "";
		if(vungId != null)
		{
			for(int i = 0; i < vungId.length; i++)
				str += vungId[i] + ",";
			if(str.length() > 0)
				str = str.substring(0, str.length() - 1);
		}
		tctskuBean.setVungIds(str);
	
		String[] khuvucId = request.getParameterValues("khuvucId");
		String str2 = "";
		if(khuvucId != null)
		{
			for(int i = 0; i < khuvucId.length; i++)
				str2 += khuvucId[i] + ",";
			if(str2.length() > 0)
				str2 = str2.substring(0, str2.length() - 1);
		}
		tctskuBean.setKhuvucIds(str2);
		
		
		String doanhsothung = request.getParameter("doanhsothung");
		if(doanhsothung == null)
			doanhsothung = "0";
		tctskuBean.setDoanhsotheoThung(doanhsothung);
		
		String httt = request.getParameter("httt");
		if(httt == null)
			httt = "0";
		tctskuBean.setHTTT(httt);
		
		String ptTRACK = request.getParameter("ptTRACK");
		if(ptTRACK == null)
			ptTRACK = "0";
		tctskuBean.setPT_TRACK(ptTRACK);
		
		//LUU LAI CAC MUC TICH LUY
		Hashtable<String, String> muc_tiendo = new Hashtable<String, String>();
		for(int i = 0; i < 5; i++)
		{
			String[] muc_tiendoTUNGAY = request.getParameterValues("muc" + i + ".tiendoTUNGAY");
			String[] muc_tiendoDENNGAY = request.getParameterValues("muc" + i + ".tiendoDENNGAY");
			String[] muc_tiendoPHAIDAT = request.getParameterValues("muc" + i + ".tiendoPHAIDAT");

			String tiendoCHITIET = "";
			if(muc_tiendoTUNGAY != null)
			{
				for(int j = 0; j < muc_tiendoTUNGAY.length; j++ )
				{
					if(muc_tiendoTUNGAY[j] != "" && muc_tiendoDENNGAY[j] != "" && muc_tiendoPHAIDAT[j] != "" )
					{
						tiendoCHITIET += muc_tiendoTUNGAY[j] + "_" + muc_tiendoDENNGAY[j] + "_" + muc_tiendoPHAIDAT[j] + "__";
					}
				}
			}

			if(tiendoCHITIET.trim().length() > 0)
			{
				tiendoCHITIET = tiendoCHITIET.substring(0, tiendoCHITIET.length() - 2);
				muc_tiendo.put(Integer.toString(i), tiendoCHITIET);

				System.out.println("---MUC:: " + i + " GIA TRI:: " + tiendoCHITIET );
			}
		}
		tctskuBean.setMuc_Tiendo(muc_tiendo);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		Hashtable<String, String> muc_spTRA = new Hashtable<String, String>();
		for(int i = 0; i < 5; i++)
		{
			String[] sanphamTRA_hinhthuc = request.getParameterValues("sanphamTRA" + i + ".hinhthuc");
			System.out.println("::: ID TRA: " + ( "sanphamTRA" + i + ".hinhthuc" ) );
			
			String[] sanphamTRA_masanpham = request.getParameterValues("sanphamTRA" + i + ".masanpham");
			String[] sanphamTRA_tensanpham = request.getParameterValues("sanphamTRA" + i + ".tensanpham");
			String[] sanphamTRA_soluong = request.getParameterValues("sanphamTRA" + i + ".soluong");
			
			String spTRACHITIET = "";
			if(sanphamTRA_masanpham != null)
			{
				for(int j = 0; j < sanphamTRA_masanpham.length; j++ )
				{
					if(sanphamTRA_masanpham[j] != ""  )
					{
						String SLG = sanphamTRA_soluong[j].trim().length() <= 0 ? " " : sanphamTRA_soluong[j].trim();
						spTRACHITIET += sanphamTRA_masanpham[j] + "_" + sanphamTRA_tensanpham[j] + "_" + SLG + ";;";
					}
				}
			}
			
			if(spTRACHITIET.trim().length() > 0)
			{
				System.out.println("---MUC:: " + i + " SP TRA:: " + ( sanphamTRA_hinhthuc[0] + "__" + spTRACHITIET) );
				
				spTRACHITIET = spTRACHITIET.substring(0, spTRACHITIET.length() - 2);
				muc_spTRA.put(Integer.toString(i), sanphamTRA_hinhthuc[0] + "__" + spTRACHITIET);
			}
		}
		tctskuBean.setMuc_SpTra(muc_spTRA);
		
		String tungay = request.getParameter("tungay");
		if(tungay == null)
			tungay = "";
		tctskuBean.setNgayDS_Tungay(tungay);
		
		String denngay = request.getParameter("denngay");
		if(denngay == null)
			denngay = "";
		tctskuBean.setNgayDS_Denngay(denngay);
		
		String khoId = request.getParameter("khoId");
		if(khoId == null)
			khoId = "";
		tctskuBean.setKhoIds(khoId);
		
		String activeTab = request.getParameter("activeTab");
		if(activeTab == null)
			activeTab = "";
		System.out.println("Tab "+activeTab);
		tctskuBean.setActiveTab(activeTab);
		
		
		String nspId = request.getParameter("nspId");
		if(nspId == null)
			nspId = "";
		tctskuBean.setNhomsanphamIds(nspId); 
		
		String chiavaodongia = request.getParameter("chiavaodongia");
		if(chiavaodongia == null)
			chiavaodongia = "0";
		tctskuBean.setChiavaodongia(chiavaodongia);
		
		String kmcpId = util.antiSQLInspection(request.getParameter("kmcpId"));
		if(kmcpId == null)
			kmcpId = "";
		tctskuBean.setKhoanmuccpId(kmcpId);
		
		String giobatdau = util.antiSQLInspection(request.getParameter("giobatdau"));
		if(giobatdau == null || giobatdau.trim().length() <= 0 )
			giobatdau = "0";
		tctskuBean.setGiobatdau(giobatdau);
		
		String gioketthuc = util.antiSQLInspection(request.getParameter("gioketthuc"));
		if(gioketthuc == null || giobatdau.trim().length() <= 0 )
			gioketthuc = "24";
		tctskuBean.setGioketthuc(gioketthuc);
		
		String khongcanduyettra = util.antiSQLInspection(request.getParameter("khongcanduyettra"));
		if(khongcanduyettra == null)
			khongcanduyettra = "0";
		tctskuBean.setKhongcanduyettraTL(khongcanduyettra);
		
 		String action = request.getParameter("action");
 		if(action.equals("save"))
 		{    
    		if (id == null )
    		{
    			if (!tctskuBean.createTctSKU())
    			{
    		    	tctskuBean.setUserId(userId);
    		    	
    		    	tctskuBean.createRs();
    		    	session.setAttribute("userId", userId);
    				session.setAttribute("tctskuBean", tctskuBean);
    				
    				String nextJSP = "";
    				if( !phanloai.equals("3") )
    					nextJSP = "/TraphacoHYERP/pages/Center/TieuChiThuongTLNew.jsp";
    				else
    					nextJSP = "/TraphacoHYERP/pages/Center/TieuChiThuongTLGiaiDoanNew.jsp";
    				
    				response.sendRedirect(nextJSP);
    			}
    			else
    			{
    				ITieuchithuongTLList obj = new TieuchithuongTLList();
				    obj.setUserId(userId);
				    obj.setPhanloai(phanloai);
				    
				    obj.init("");
					session.setAttribute("obj", obj);
				    
				    String nextJSP = "/TraphacoHYERP/pages/Center/TieuChiThuongTL.jsp";
					response.sendRedirect(nextJSP);
    			}	
    		}
    		else
    		{
    			if (!(tctskuBean.updateTctSKU()))
    			{			
    		    	tctskuBean.setUserId(userId);
    		    	tctskuBean.createRs();
    		    	session.setAttribute("userId", userId);
    				session.setAttribute("tctskuBean", tctskuBean);
    				
    				String nextJSP = "";
    				
    				if( !phanloai.equals("3") )
    					nextJSP = "/TraphacoHYERP/pages/Center/TieuChiThuongTLUpdate.jsp";
    				else
    					nextJSP = "/TraphacoHYERP/pages/Center/TieuChiThuongTLGiaiDoanUpdate.jsp";
    				
    				response.sendRedirect(nextJSP);
    			}
				else
				{
					ITieuchithuongTLList obj = new TieuchithuongTLList();
				    obj.setUserId(userId);
				    obj.setPhanloai(phanloai);
				    obj.init("");
					session.setAttribute("obj", obj);
				    
				    String nextJSP = "/TraphacoHYERP/pages/Center/TieuChiThuongTL.jsp";
					response.sendRedirect(nextJSP);
				}
    		}
	    }
		else
		{		
			tctskuBean.createRs();
			if(action.equals("loadSP_NHOM"))
			{
				tctskuBean.setMaspTra(null);
				tctskuBean.setTenspTra(null);
				
				tctskuBean.loadSP_NHOM();
			}
			
			session.setAttribute("userId", userId);
			session.setAttribute("tctskuBean", tctskuBean);
			
			String nextJSP;
			if (id == null){
				if(!phanloai.equals("3"))
					nextJSP = "/TraphacoHYERP/pages/Center/TieuChiThuongTLNew.jsp";
				else
					nextJSP = "/TraphacoHYERP/pages/Center/TieuChiThuongTLGiaiDoanNew.jsp";
			}
			else{
				if(!phanloai.equals("3"))
					nextJSP = "/TraphacoHYERP/pages/Center/TieuChiThuongTLUpdate.jsp"; 
				else
					nextJSP = "/TraphacoHYERP/pages/Center/TieuChiThuongTLGiaiDoanUpdate.jsp"; 
			}
			response.sendRedirect(nextJSP);

		}
	}

	public String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

}
