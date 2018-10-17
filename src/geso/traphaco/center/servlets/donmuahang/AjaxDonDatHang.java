package geso.traphaco.center.servlets.donmuahang;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

@WebServlet("/AjaxDonDatHang")
public class AjaxDonDatHang extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	NumberFormat formatter = new DecimalFormat("#,###,###");

	public AjaxDonDatHang()
	{
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

	}
	
	
class NhaPhanPhoi
{
	String kbhId;
	public String getKbhId()
	{
		return kbhId;
	}
	public void setKbhId(String kbhId)
	{
		this.kbhId = kbhId;
	}
	public String getNppId()
	{
		return nppId;
	}
	public void setNppId(String nppId)
	{
		this.nppId = nppId;
	}
	public String getTuvanchuyen()
	{
		return tuvanchuyen;
	}
	public void setTuvanchuyen(String tuvanchuyen)
	{
		this.tuvanchuyen = tuvanchuyen;
	}
	public String getHinhthucvanchuyen()
	{
		return hinhthucvanchuyen;
	}
	public void setHinhthucvanchuyen(String hinhthucvanchuyen)
	{
		this.hinhthucvanchuyen = hinhthucvanchuyen;
	}
	public String getGiavanchuyen()
	{
		return giavanchuyen;
	}
	public void setGiavanchuyen(String giavanchuyen)
	{
		this.giavanchuyen = giavanchuyen;
	}
	public String getDvkdId()
	{
		return dvkdId;
	}
	public void setDvkdId(String dvkdId)
	{
		this.dvkdId = dvkdId;
	}
	String nppId;
	String tuvanchuyen;
	String hinhthucvanchuyen;
	String giavanchuyen;
	String dvkdId;
	String chietkhau;
	String nccId;
	public String getNccId()
	{
		return nccId;
	}
	public void setNccId(String nccId)
	{
		this.nccId = nccId;
	}
	public String getChietkhau()
	{
		return chietkhau;
	}
	public void setChietkhau(String chietkhau)
	{
		this.chietkhau = chietkhau;
	}
}



	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		Utility util = new Utility();
		Gson gson = new Gson();
		String type = request.getParameter("type") == null ? "" : request.getParameter("type");
		String ngaydat = request.getParameter("ngaydat") == null ? "" : request.getParameter("ngaydat");
		if (type.equals("GetKenhBanHang"))
		{
			String nppId = util.antiSQLInspection(request.getParameter("nppId")==null?"":request.getParameter("nppId"));
			dbutils db = new dbutils();

			String query = 
			"	select a.PK_SEQ as nppId,a.GiaVanChuyen, case when b.kbh_fk !=100025 then a.ChietKhau else 3.5 end As ChietKhau,a.TuVanChuyen,b.KBH_FK,d.DVKD_FK,case when a.TUVANCHUYEN=1 then 'KHVC' else 'CTVC' "+
			"			end as HinhThucVanChuyen,d.NCC_FK "+
			"	From NhaPhanPhoi a "+
			"		left join nhapp_kbh b on b.NPP_FK=a.PK_SEQ "+
			"		left join NHAPP_NHACC_DONVIKD c on c.NPP_FK=b.NPP_FK "+
			"		left join NHACUNGCAP_DVKD d on d.PK_SEQ=c.NCC_DVKD_FK  "+
			"	where a.pk_Seq="+nppId+"";
			System.out.println("__Lay quy doi: " + query);
			
			String kenhId = "";
			String dvkdId = "";
			String tuvanchuyen = "";
			ResultSet rs = db.get(query);
			try
			{
				while(rs.next())
				{
					NhaPhanPhoi nppJSON = new NhaPhanPhoi();
					kenhId = rs.getString("kbh_fk");
					dvkdId = rs.getString("dvkd_fk");
					tuvanchuyen = rs.getString("TuVanChuyen");
					nppJSON.setKbhId(kenhId);
					nppJSON.setDvkdId(dvkdId);
					nppJSON.setGiavanchuyen(rs.getString("GiaVanChuyen") );
					nppJSON.setTuvanchuyen(tuvanchuyen);
					nppJSON.setHinhthucvanchuyen(rs.getString("HinhThucVanChuyen"));
					nppJSON.setChietkhau(rs.getString("ChietKhau"));
					nppJSON.setNccId(rs.getString("ncc_fk"));
					
					response.setContentType("application/json");
					response.setCharacterEncoding("UTF-8");
					response.getWriter().write(gson.toJson(nppJSON));
				}
			} catch (SQLException e)
			{			
				e.printStackTrace();
			}
			finally
			{
				if(db!=null)db.shutDown();
			}
			
			session.setAttribute("ngaygiaodich", ngaydat);
			session.setAttribute("kenhid", kenhId);
			session.setAttribute("nhappid", nppId);
			session.setAttribute("dvkdid", dvkdId);
			session.setAttribute("tuvanchuyen",tuvanchuyen);
		}
	}
}
