package geso.traphaco.center.servlets.nhomnhaphanphoi;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/AjaxNhomNpp")
public class AjaxNhomNpp extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public AjaxNhomNpp() {
        super();
       
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
		PrintWriter out = response.getWriter();
		String kenhId = request.getParameter("kenhId");
		String vungId = request.getParameter("vungId");
		String kvId = request.getParameter("kvId");
		String tinhId = request.getParameter("tinhId");
		String qhId = request.getParameter("qhId");
		String nhomId=request.getParameter("nhomId");
		String id = request.getParameter("id");
		dbutils db = new dbutils();
		
		System.out.println("[Ajax Nhom]");
		String str="";
		if(id.equals("tbNpp")) 
		{
			String query = 
					"select " + 
					"	isnull(kbh.TEN,N'Chưa xác định') as Kenh, " + 
					"	isnull(v.TEN,N'Chưa xác định') as Vung, " + 
					"	isnull(kv.TEN,N'Chưa xác định') as KhuVuc, " + 
					"	isnull(tt.TEN,N'Chưa xác định') as Tinh, "+ 
					"	isnull(qh.TEN,N'Chưa xác định') as QuanHuyen, " + 
					"	npp.PK_SEQ,npp.MA,npp.TEN,npp.DIACHI " + 
					"from NHAPHANPHOI npp " + 
					"left join KHUVUC kv on " + 
					"kv.PK_SEQ=npp.KHUVUC_FK " + 
					"left join VUNG v on v.PK_SEQ=kv.VUNG_FK "+ 
					"left join NHAPP_KBH nppK on nppK.NPP_FK=npp.PK_SEQ " + 
					"left join QUANHUYEN qh on qh.PK_SEQ=npp.QUANHUYEN_FK " + 
					"left join TINHTHANH tt on tt.PK_SEQ=qh.TINHTHANH_FK " + 
					"left join KENHBANHANG kbh on kbh.PK_SEQ=nppK.KBH_FK where 1=1  ";
			query+=" and npp.pk_seq not in (select npp_fk from nhomnpp_npp   ";
			if(nhomId.length()>0)
				query+=" where  nhomnpp_fk!='"+nhomId+"'  ";
			query+="  )  ";
			if(kenhId.length()>0)
			{
				kenhId = kenhId.substring(0, kenhId.length() - 1);
				query += " and npp.pk_seq in( select npp_fk from NHAPP_KBH where kbh_fk in(" +kenhId + ") )";
			}
			if(vungId.length()>0)
			{
				vungId = vungId.substring(0, vungId.length() - 1);
				query += " and v.pk_seq in( "+vungId+" )";
			}
			if(kvId.length()>0)
			{
				kvId = kvId.substring(0, kvId.length() - 1);
				query += " and npp.khuvuc_fk in( "+kvId+" )";
			}
			if(tinhId.length()>0)
			{
				tinhId = tinhId.substring(0, tinhId.length() - 1);
				query += " and tt.pk_seq in( "+tinhId+" )";
			}
			if(qhId.length()>0)
			{
				qhId = qhId.substring(0, qhId.length() - 1);
				query += " and qh.pk_seq in( "+qhId+" )";
			}
			System.out.println("[Ajax Nhom]"+query);
			
			ResultSet rs = db.get(query);
			str+="<TABLE width=\"100%\" border=\"0\" cellspacing=\"1\" cellpadding=\"4\"  > \n"+							
			"<TR class=\"tbheader\"> \n"+
			"<TH width=\"10%\">Kênh bán hàng</TH> \n"+
			"<TH width=\"10%\">Vùng</TH> \n"+
			"<TH width=\"10%\">Khu vực</TH> \n"+
			"<TH width=\"10%\">Tỉnh thành</TH> \n"+
			"<TH width=\"10%\">Quận huyện</TH> \n"+
			"<TH width=\"10%\">Mã nhà phân phối</TH> \n"+
			"<TH width=\"25%\">Nhà phân phối</TH> \n"+
			"<TH width=\"15%\">Địa chỉ</TH> \n"+
			"<TH width=\"15%\">Chọn"+
			"<input type=\"checkbox\" name=\"chon\" onClick=\"checkedAll(document.nspForm.nppId)\"> " +
			"</TH> ";
			try 
			{
				int i = 0;
				while(rs.next())
				{
					if (i % 2 == 0)
						str += "<TR class= \"tblightrow\" > ";
					else
						str +=	"<TR class= \"tbdarkrow\" > ";
					str +="<TD align=\"left\" class=\"textfont\">"+ rs.getString("kenh")+"</TD>";
					str+="<TD align=\"left\" class=\"textfont\">"+rs.getString("vung") +"</TD>";	
					str+="<TD align=\"left\" class=\"textfont\">"+ rs.getString("khuvuc") +"</TD>";
					str+="<TD align=\"left\" class=\"textfont\">"+ rs.getString("tinh")+"</TD>";
					str+="<TD align=\"left\" class=\"textfont\">"+ rs.getString("quanhuyen") +"</TD>";
					str+="<TD align=\"left\" class=\"textfont\">"+ rs.getString("ma") +"</TD>";
					str+="<TD align=\"center\"><div align=\"left\">"+ rs.getString("ten")+"</div></TD>";
					str+="<TD align=\"center\"><div align=\"left\">"+ rs.getString("diachi")+"</div></TD>";
					str+="<TD align=\"center\"><input type=\"checkbox\" name=\"nppId\" value='"+rs.getString("pk_seq") +"'></TD> </tr>"; 
				   	i++;
				}
				rs.close();
				str += "<tr><td class=\"plainlabel\" colspan=\"9\">&nbsp;</td></tr>";
				System.out.println("Ket qua la: " + str + "\n");
			} 
			catch(Exception e) {}
			str += "</table>";
			out.write(str);
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");
    response.setContentType("text/html; charset=UTF-8");
    
		String type = request.getParameter("type") == null ? "" : request.getParameter("type");
		String khId = request.getParameter("khId") == null ? "" : request.getParameter("khId");
		PrintWriter out = response.getWriter();	
		Utility util = new Utility();
	
		if(type.equals("qhId"))
		{
			String tpId = util.antiSQLInspection(request.getParameter("tpId")==null?"":request.getParameter("tpId"));
			String qhId = util.antiSQLInspection(request.getParameter("qhId"));
			if(qhId == null||qhId.equals("null")) qhId = ""; else qhId = qhId.trim();
			if(tpId == null||tpId.equals("null")) tpId = ""; else tpId = tpId.trim();
			
			
			if(tpId.length()>0)
			{
				tpId = tpId.substring(0, tpId.length() - 1);
			}
			if(qhId.length()>0)
			{
				qhId = qhId.substring(0, qhId.length() - 1);
			}
			dbutils db = new dbutils();
			
			String query = "select ten as qhTen, pk_seq as qhId from quanhuyen where 1=1  ";
			if(tpId.length()>0)
				query += " and tinhthanh_fk in ("+ tpId +") ";
			query += " order by ten " ;
			
			ResultSet rs = db.get(query);
			
			System.out.println("____Action___"+query);
			
			String str = "<option value=''></option>";
			if(rs != null)
			{
				try 
				{
					while(rs.next())
					{
						if(qhId.equals(rs.getString("qhId"))) {
							str += "<option  value='" + rs.getString("qhId") + "' selected=\"selected\">" + rs.getString("qhTen") +"</option>";
						} else {
							str += "<option  value='" + rs.getString("qhId") + "'>" + rs.getString("qhTen") +"</option>";
						}
					}
					rs.close();
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
					return;
				}
			}
			out.write(str);
			db.shutDown();
		}
	}

}
