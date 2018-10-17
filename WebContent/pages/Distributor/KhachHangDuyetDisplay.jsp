<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.distributor.beans.khachhang.IKhachhang" %>
<%@ page  import = "geso.traphaco.distributor.beans.khachhang.*" %>
<%@ page  import = "geso.traphaco.distributor.beans.khachhang.imp.*" %>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.util.Hashtable"%>
<%@ page import="geso.traphaco.center.util.*"%>
<%@ page  import = "java.util.List" %>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.NumberFormat"%>

<%
	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{
%>
<%
	NumberFormat formatter = new DecimalFormat("#,###,###");
	IKhachhang khBean = (IKhachhang)session.getAttribute("khBean");
	ResultSet quocgiaRs = khBean.getQgiaRs();
	ResultSet phuongxaRs = khBean.getPhuongxaRs();
	ResultSet tp = (ResultSet)khBean.getTp() ;
	ResultSet qh = (ResultSet)khBean.getQh() ;
	ResultSet hch = (ResultSet)khBean.getHangcuahang();
	ResultSet kbh = (ResultSet)khBean.getKenhbanhang();
	ResultSet bgst = (ResultSet)khBean.getBgsieuthi();
	ResultSet vtch = (ResultSet)khBean.getVtcuahang();
	ResultSet nch = (ResultSet)khBean.getNhomcuahang();
	ResultSet mck = (ResultSet)khBean.getMucchietkhau();
	ResultSet nvgn = (ResultSet)khBean.getNvgnRs();
	ResultSet diadiemRs = (ResultSet)khBean.getDiadiemRs();
	ResultSet khoRs = (ResultSet)khBean.getKhoRs();
	ResultSet nkh_khList = (ResultSet)khBean.getNkh_khList();
	Hashtable<Integer, String> nkh_khIds = (Hashtable<Integer, String>)khBean.getNkh_KhIds();
	ResultSet tbhRs = (ResultSet)khBean.getTbhRs();
	ResultSet DiaBanRs = (ResultSet)khBean.getDiaBanRS();
	ResultSet ddkdRs = (ResultSet)khBean.getDdkdRs();
	
	ResultSet tructiepRs = (ResultSet)khBean.getTructtiepRs();
	ResultSet giantiepRs = (ResultSet)khBean.getGiantiepRs();
	
	ResultSet phuongthucTTRs = (ResultSet)khBean.getPhuongthucTTRs();
	String[] tenxuatHD = khBean.getTenXuatHD();
	ResultSet loaiChRs = (ResultSet)khBean.getLoaicuahang();
	ResultSet ctvRs = (ResultSet)khBean.getCtvRs();
	ResultSet nppRs = (ResultSet)khBean.getNppRs();
	ResultSet nganhangRs = (ResultSet)khBean.getNganhangRs();
	ResultSet chinhanhRs = (ResultSet)khBean.getChinhanhRs();
	ResultSet taikhoanRs = (ResultSet)khBean.getTaikhoanRs();
	ResultSet tkkqRs = khBean.getTkkqRs();
	ResultSet kmcpRs = (ResultSet)khBean.getKhoanmuccpRs();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Phanam - Project</TITLE>

<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<script type="text/javascript"	src="../scripts/jquery.min.1.7.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>

<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {		
		$( ".days" ).datepicker({			    
				changeMonth: true,
				changeYear: true				
		});            
	});	
</script>

<script type="text/javascript" src="../scripts/ajax.js"></script>
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs.css">
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs-panes.css">

<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
	$(document).ready(function()
	{
		//$("select:not(.notuseselect2)").select2({ width: 'resolve' }); 
		$(".select2").select2();
		
		$("ul.tabs").tabs("div.panes > div");
		
	});
</script>

<script type="text/javascript" src="../scripts/ajax_erp_KH_SP_CK.js"></script>

<style type="text/css">
#mainContainer {
	width: 660px;
	margin: 0 auto;
	text-align: left;
	height: 100%;
	border-left: 3px double #000;
	border-right: 3px double #000;
}

#formContent {
	padding: 5px;
}
/* END CSS ONLY NEEDED IN DEMO */

/* Big box with list of options */
#ajax_listOfOptions {
	position: absolute; /* Never change this one */
	width: auto; /* Width of box */
	height: 200px; /* Height of box */
	overflow: auto; /* Scrolling features */
	border: 1px solid #317082; /* Dark green border */
	background-color: #C5E8CD; /* White background color */
	color: black;
	text-align: left;
	font-size: 1.0em;
	z-index: 100;
}

#ajax_listOfOptions div {
	/* General rule for both .optionDiv and .optionDivSelected */
	margin: 1px;
	padding: 1px;
	cursor: pointer;
	font-size: 1.0em;
}

#ajax_listOfOptions .optionDiv { /* Div for each item in list */
	
}

#ajax_listOfOptions .optionDivSelected { /* Selected item in the list */
	background-color: #317082; /*mau khi di chuyen */
	color: #FFF;
}

#ajax_listOfOptions_iframe {
	background-color: #F00;
	position: absolute;
	z-index: 5;
}

form {
	display: inline;
}

#dhtmltooltip {
	position: absolute;
	left: -300px;
	width: 150px;
	border: 1px solid black;
	padding: 2px;
	background-color: lightyellow;
	visibility: hidden;
	z-index: 100;
	/*Remove below line to remove shadow. Below line should always appear last within this CSS*/
	filter: progid:DXImageTransform.Microsoft.Shadow(color=gray, direction=135
		);
}

#dhtmlpointer {
	position: absolute;
	left: -300px;
	z-index: 101;
	visibility: hidden;
}
}
</style>

<SCRIPT language="javascript" type="text/javascript">
	function submitform() {
		document.forms["khForm"].submit();
	}

	function saveform() {

		document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";

		document.forms['khForm'].action.value = 'duyet';
		document.forms['khForm'].submit();
	}
	
	function showElement(id) {
		var element = document.getElementById(id);
		element.style.display = "";
	}

	function hideElement(id) {
		var element = document.getElementById(id);
		element.style.display = "none";
	}

	function SubmitAgain() 
	{
		var type = document.forms['khForm'].type.value;
		if (type == "1") {
			if (!confirm('Địa chỉ khách hàng này đã bị trùng,Bạn muốn tiếp tục lưu khách hàng này?')) {
				document.forms['khForm'].type.value = "0";
				return false;
			} else {
				saveform();
			}
		}
	}
	
	function replaces()
	{				
		//SP CHIET KHAU
		var idspCK = document.getElementsByName("idSanpham");
		var maspCK = document.getElementsByName("maSanpham");
		var tenspCK = document.getElementsByName("tenSanpham");
		var dvtCK = document.getElementsByName("dvSanpham");

		if( idspCK != null )
		{
			for(i = 0; i < maspCK.length; i++)
			{
				if(maspCK.item(i).value != "")
				{
					var sp = maspCK.item(i).value;
					var pos = parseInt(sp.indexOf(" - "));
			
					if(pos > 0)
					{
						maspCK.item(i).value = sp.substring(0, pos);
						sp = sp.substr(parseInt(sp.indexOf(" - ")) + 3);
				
						pos = parseInt(sp.indexOf(" - "));
						
						tenspCK.item(i).value = sp.substring(0, pos);
						
						sp = sp.substr(parseInt(sp.indexOf(" - ")) + 3);
				
						dvtCK.item(i).value = sp.substring(0, parseInt(sp.indexOf("] [")));
						sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
				
						idspCK.item(i).value = sp.substring(0, parseInt(sp.indexOf("]")));
					}
				}
				else
				{
					idspCK.item(i).value = "";
					maspCK.item(i).value = "";
					tenspCK.item(i).value = "";
					dvtCK.item(i).value = "";
				}
			}
		}
		 
		setTimeout(replaces, 500);
	}
	
</SCRIPT>


</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="khForm" method="post" action="../../KhachhangUpdateSvl">
<input type="hidden" name="userId" value='<%=userId%>'> 
<input type="hidden" name="nppId" value='<%=khBean.getNppId()%>'>
<input type="hidden" name="action" value='1'> 
<input type="hidden" name="type" value='<%=khBean.gettype()%>'>
<input type="hidden" name="id" value='<%= khBean.getId() %>'>
<input type="hidden" name="capduyet" id='capduyet' value='<%= khBean.getCapduyet() %>'>

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="2">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr height="22">
								<TD align="left" colspan="2" class="tbnavigation">&nbsp;Thiết lập dữ liệu nền > Dữ liệu nền kinh doanh > Khách hàng > Duyệt khách hàng (<%= khBean.getCapduyet() %>)
								<TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %>&nbsp;
							</tr>
						</table>
					</TD>
				</TR>
			</TABLE>
			<TABLE width="100%" cellpadding="0" cellspacing="2">
				<TR>
					<TD>
						<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
							<TR class="tbdarkrow">
								<TD width="30" align="left">
									<A href="../../KhachhangDuyetSvl?userId=<%=userId%>&capduyet=<%= khBean.getCapduyet() %>">
										<img src="../images/Back30.png" alt="Quay ve" title="Quay ve" width="30" height="30" border="1" longdesc="Quay ve" style="border-style: outset"> </A>
								</TD>
								<TD width="2" align="left"></TD>
								<TD width="30" align="left">
									<div id="btnSave">
										<A href="javascript:saveform()">
											<IMG src="../images/Chot.png" title="Duyet khach hang" alt="Duyet khach hang" border="1" style="border-style: outset"> </A>
									</div>
								</TD>
								<TD align="left">&nbsp;</TD>
							</TR>
						</TABLE>
					</TD>
				</TR>
			</TABLE>
			
			
			
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
							<LEGEND class="legendtitle">Thông báo </LEGEND>
							<textarea name="dataerror" style="width: 100%; font-weight: bold" rows="1"><%=khBean.getMessage()%></textarea>
						</FIELDSET>
					</TD>
				</tr>

				<TR>
					<TD height="100%" width="100%">
						<FIELDSET>
							<LEGEND class="legendtitle" style="color: black">Thông tin khách hàng</LEGEND>
							<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">

								<TR>
									<TD colspan="6" >
										
										<ul class="tabs">
											<li><a href="#" style="color: red; font-weight: bold; " >TT hợp đồng</a></li>
											<li><a href="#" style="color: red; font-weight: bold; " >TT xuất hóa đơn</a></li>
											<li><a href="#" style="color: blue; font-weight: bold; " >TT Bán hàng</a></li>
											<li><a href="#" style="color: blue; font-weight: bold; " >Đặt hàng</a></li>
											<li><a href="#" style="color: blue; font-weight: bold; " >Giao hàng</a></li>
											<li><a href="#" style="color: blue; font-weight: bold; " >Công nợ</a></li>
				                  			<li><a href="#">Tuyến bán hàng</a></li>
				                  			<li><a href="#">Phân nhóm</a></li>
				                  		</ul>
				                  		
				                  		<div class="panes">
				                  		
				                  			<!-- THÔNG TIN HỢP ĐỒNG  -->
											<div>
												
											<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
												<TR>
													<TD class="plainlabel">Trực tiếp</TD>
													<TD class="plainlabel" >
													
													<%= util.getSelectBox(tructiepRs, "width:240px;", "tructiepId", "submitform();", khBean.getTructiepId(), "PK_SEQ", "TEN", "0", false, true) %>
													
													</TD>
													<TD class="plainlabel">Gián tiếp</TD>
													<TD class="plainlabel" colspan="3" >
													
													<%= util.getSelectBox(giantiepRs, "width:400px;", "giantiepId", "submitform();", khBean.getGiantiepId(), "PK_SEQ", "TEN", "0", true, true) %>
													
													</TD>
												</tr>
												<TR>
													<TD width="120" class="plainlabel">Tên khách hàng<FONT class="erroralert"> *</FONT>
													</TD>
													<TD width="250" class="plainlabel">
														<INPUT type="text" name="khTen" id="khTen" value="<%=khBean.getTen()%>">
													</TD>
													<TD width="120" class="plainlabel">Mã cũ<FONT class="erroralert"> *</FONT>
													</TD>
													<TD width="250" class="plainlabel">
														<INPUT type="text" name="maFAST" value="<%= khBean.getMaFAST() %>"></TD>
													
													<TD width="120" class="plainlabel">Điện thoại</TD>
													<TD class="plainlabel">
														<INPUT type="text" name="dienthoai" value="<%= khBean.getSodienthoai() %>" >
													
													</TD>
												</TR>
											
											<TR>
												<TD class="plainlabel">Quốc gia<FONT class="erroralert"> *</FONT></TD>
												<TD class="plainlabel">
												
													<%= util.getSelectBox(quocgiaRs, "", "quocgiaId", "submitform();", khBean.getQgiaId(), "PK_SEQ", "TENQG", "0", false, true) %>
												
												</TD>
												<TD class="plainlabel">Tỉnh /Thành phố<FONT class="erroralert"> *</FONT>
												</TD>
												<TD class="plainlabel">
												
												<%= util.getSelectBox(tp, "", "tpId", "submitform();", khBean.getTpId(), "tpId", "tpTen", "0", false, true) %>
													
												</TD>
	
												<TD class="plainlabel">Quận/Huyện <FONT class="erroralert"> *</FONT>
												</TD>
												<TD class="plainlabel">
												
												<%= util.getSelectBox(qh, "", "qhId", "submitform();", khBean.getQhId(), "qhId", "qhTen", "0", false, true) %>
												
												</TD>
											</TR>
	
											<TR>
												<TD class="plainlabel">Phường xã<FONT class="erroralert">*</FONT></TD>
												<TD class="plainlabel">
												
												<%= util.getSelectBox(phuongxaRs, "", "phuongxaId", "", khBean.getPhuongxaId(), "PK_SEQ", "TEN", "0", false, true) %>
												
												</TD>
												
												<TD class="plainlabel">Địa chỉ</TD>
												<TD class="plainlabel"  >
													<INPUT type="text" name="diachi" value="<%= khBean.getDiachi() %>" >
												</TD>
												<TD class="plainlabel">Trạng thái</TD>	
												<TD class="plainlabel">
													<% if (khBean.getTrangthai().equals("1")){ %>
														<input name="trangthai" type="checkbox" value="1" checked="checked"> 
													<% } else { %> 
														<input name="trangthai" type="checkbox" value="0"> 
													<% }  %>	
													Hoạt động 
												</TD>
											</TR>
											
											<tr>
												<TD class="plainlabel">Địa chỉ giao hàng</TD>
												<TD class="plainlabel" colspan="5" >
													<INPUT type="text" name="diachigiaohang" value="<%= khBean.getDiachigiaohang() %>" >
													
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													In số VISA <input type="checkbox" value="1" name="insoVISA" <%= khBean.getInsovisa().equals("1") ? "checked" : "" %> > &nbsp;&nbsp;&nbsp;
													In số hợp đồng <input type="checkbox" value="1" name="insoHD" <%= khBean.getInsohopdong().equals("1") ? "checked" : "" %> > &nbsp;&nbsp;&nbsp;
													In nhà nhập khẩu <input type="checkbox" value="1" name="innhaNK" <%= khBean.getInnhanhapkhau().equals("1") ? "checked" : "" %> > &nbsp;&nbsp;&nbsp;
													In nhà sản xuất <input type="checkbox" value="1" name="innhaSX" <%= khBean.getInnhasanxuat().equals("1") ? "checked" : "" %> > &nbsp;&nbsp;&nbsp;
													Bán hàng qua điện thoại <input type="checkbox" value="1" name="inbhdt" <%= khBean.getInbhdt().equals("1") ? "checked" : "" %> > &nbsp;&nbsp;&nbsp;
												</TD>
											</tr>
											
											<tr>
												<TD class="plainlabel">Số tài khoản NH</TD>
												<TD class="plainlabel" >
													<INPUT type="text" name="sotaikhoanNH" value="<%= khBean.getSotaikhoan() %>" >
												</TD>
												<TD class="plainlabel">Người đại diện</TD>
												<TD class="plainlabel" >
													<INPUT type="text" name="nguoidaidien" value="<%= khBean.getNguoidaidien() %>" >
												</TD>
												<TD class="plainlabel">Chức vụ</TD>
												<TD class="plainlabel" >
													<INPUT type="text" name="chucvu" value="<%= khBean.getChucvu() %>" >
												</TD>
											</tr>
											
											<Tr>
												<TD class="plainlabel">Ngân hàng</TD>
												<TD class="plainlabel" >
												
													<%= util.getSelectBox(nganhangRs, "", "nganhangId", "", khBean.getNganhangId(), "PK_SEQ", "TEN", "0", false, true) %>
													
												</TD>
												<TD class="plainlabel">Chi nhánh</TD>
												<TD class="plainlabel" >
												
													<%= util.getSelectBox(chinhanhRs, "", "chinhanhId", "", khBean.getChinhanhId(), "PK_SEQ", "TEN", "0", false, true) %>
												
												</TD>
												<TD class="plainlabel">Tài khoản kế toán</TD>
												<TD class="plainlabel" >
												
												<select name="taikhoanId" id="taikhoanId" style="width: 300px" >
						                        	<%
						                        		if(taikhoanRs != null)
						                        		{
						                        			try
						                        			{
						                        			while(taikhoanRs.next())
						                        			{  
						                        			if( taikhoanRs.getString("pk_seq").equals(khBean.getTaikhoanId())){ %>
						                        				<option value="<%= taikhoanRs.getString("pk_seq") %>" selected="selected" ><%= taikhoanRs.getString("TEN") %></option>
						                        			<%}else { %>
						                        				<option value="<%= taikhoanRs.getString("pk_seq") %>" ><%= taikhoanRs.getString("TEN") %></option>
						                        		 <% } } taikhoanRs.close();} catch(SQLException ex){}
						                        		}
						                        	%>
						                        </select>
											</Tr>
											
											<tr>
												<TD class="plainlabel">Trình dược viên</TD>
												<TD class="plainlabel" > 
												
												<%= util.getSelectBox(ddkdRs, "", "ddkdId", "submitform();", khBean.getDdkdId(), "ddkdId", "ddkdTen", "0", true, true) %>
												
												</TD>
												
												<TD class="plainlabel">Địa bàn</TD>
												<TD class="plainlabel"  >
													
													<%= util.getSelectBox(DiaBanRs, "", "diaban", "", khBean.getdiaban(), "PK_SEQ", "TEN", "0", false, true) %>
												
												</TD>
												
												<TD class="plainlabel">Cộng tác viên</TD>
												<TD class="plainlabel">
												
													<%= util.getSelectBox(ctvRs, "", "ctvId", "", khBean.getCTVId(), "PK_SEQ", "TEN", "0", true, true) %>
												
												</TD>
											</TR>
	
											<TR>
												<TD class="plainlabel">Kênh bán hàng<FONT class="erroralert">*</FONT>
												</TD>
												<TD class="plainlabel">
												
												<%= util.getSelectBox(kbh, "", "kbhTen", "submitform();", khBean.getKbhId(), "kbhId", "kbhTen", "0", true, true) %>
												
												</TD>
												
												<TD  class="plainlabel">Loại khách hàng</TD>
												<TD class="plainlabel"  > 
												
													<%= util.getSelectBox(loaiChRs, "", "lchId", "submitform();", khBean.getLchId(), "PK_SEQ", "TEN", "0", false, true) %>
													
												</TD>
												
												<TD class="plainlabel">Hạng khách hàng</TD>
												<TD class="plainlabel">
												
												<%= util.getSelectBox(hch, "", "hangcuahangId", "", khBean.getHchId(), "hchId", "hchTen", "0", false, true) %>
												
												</TD>
											</TR>

											<TR>
												<TD class="plainlabel">Mã số thuế cá nhân</TD>
												<TD class="plainlabel">
													<INPUT type="text" name="mst_canhan" value="<%=khBean.getMst()%>" >
												</TD>
												<TD class="plainlabel">Ngày Sinh</TD>
												<TD class="plainlabel" >
													<INPUT type="text" class="days" name="ngaysinh" value="<%=khBean.getNgaysinh()%>">
												</TD>
												<TD class="plainlabel">Tên ký hợp đồng:</TD>
												<TD class="plainlabel">
													<INPUT type="text" name="tenkyhd" id="tenkyhd" value="<%=khBean.getTenKyHd()%>" >
												</TD>
											</TR>
											
											<TR>
												<TD class="plainlabel">CMND</TD>
												<TD class="plainlabel" colspan="5" >
													<INPUT type="text" name="cmnd" value="<%=khBean.getCmnd()%>" >
													
													&nbsp;&nbsp;&nbsp;
													Khách hàng đặc biệt 
													<%  if (khBean.getIsDacbiet().equals("1")){%>
														<input name="dacbiet" type="checkbox" value="1" checked="checked"> 
													<%} else {%> 
														<input name="dacbiet" type="checkbox" value="0"> 
													<%} %>	
													
													<% if( khBean.getKbhId().contains("100052") || khBean.getKbhId().contains("100056") ) { %>
													
														&nbsp;&nbsp;&nbsp;
														Tách hóa đơn theo sản phẩm 
														<%  if (khBean.getTachhoadon().equals("1")){%>
															<input name="tachhoadon" type="checkbox" value="1" checked="checked"> 
														<%} else {%> 
															<input name="tachhoadon" type="checkbox" value="1"> 
														<%} %>	
													
													<% } %>
													
													&nbsp;&nbsp;&nbsp;
													Tách hóa đơn theo VAT
													<%  if (khBean.getTachhoadonTheoVAT().equals("1")){%>
														<input name="tachhoadonTHEOVAT" type="checkbox" value="1" checked="checked"> 
													<%} else {%> 
														<input name="tachhoadonTHEOVAT" type="checkbox" value="1"> 
													<%} %>	
													
												</TD>
											</TR>
											
											<TR style="display: none;" >
												<TD class="plainlabel">Đơn vị xuất HĐ</TD>
												<TD class="plainlabel">
													
													<%= util.getSelectBox(nppRs, "", "nppXHdId", "", khBean.getNppXHDId(), "PK_SEQ", "TEN", "0", false, false) %>
													
												</TD>
												<TD class="plainlabel">Đơn vị xuất kho</TD>
												<TD class="plainlabel">
													
													<% if( nppRs != null ) nppRs.beforeFirst(); %>
													<%= util.getSelectBox(nppRs, "", "nppXkId", "", khBean.getNppXHDId(), "PK_SEQ", "TEN", "0", false, true) %>
													
												</TD>
												
											</TR>
											
											<TR>
												<TD class="plainlabel">Ghi chú</TD>
												<TD class="plainlabel" colspan="5" >
													<INPUT type="text" name="ghichu" value="<%= khBean.getGhichu() %>" style="width: 100%;" >
										  		</TD>
											</TR>
											
											<TR>
												<TD class="plainlabel" colspan="2" >Xung quanh bệnh viện
												
													<%  if (khBean.getIsXqBenhvien().equals("1")){%>
														<input name="xqbenhvien" type="checkbox" value="1" checked="checked" onchange="submitform();" > 
													<%} else {%> 
														<input name="xqbenhvien" type="checkbox" value="1" onchange="submitform();" > 
													<%} %>
										  		</TD>
										  		
										  		<%  if (khBean.getIsXqBenhvien().equals("1")){%>
											  		<TD class="plainlabel">Bệnh viện</TD>
											  		<TD class="plainlabel">
											  			<INPUT type="text" name="benhvien" id="benhvien" value="<%= khBean.getBenhvien() %>" placeholder="Chọn bệnh viện" >
											  		</TD>
										  		<% } %>
										  		
											  	<TD  class="plainlabel" >Tài khoản ký quỹ</TD>
											  	<TD  class="plainlabel" <%= khBean.getIsXqBenhvien().equals("1") ? "" : " colspan='3' " %> >
											  	
											  		<%= util.getSelectBox(tkkqRs, "", "tkkqId", "", khBean.getTkkqId(), "TKKTID", "TAIKHOANKT", "0", false, true) %>
											 	
											  	</TD>
											  	
											  	
											</TR> 
											
											<TR>
												<TD class="plainlabel" >Khoản mục chi phí </TD>
						                        <TD class="plainlabel" colspan="6">
						                        
						                        	<%= util.getSelectBox(kmcpRs, "", "kmcpId", "", khBean.getKhoanmuccpId(), "pk_seq", "diengiai", "0", false, true) %>
						                        	
						                        </TD>
						                        
											</TR>
										
											<% if( khBean.getIsXqBenhvien().equals("1") ) { 
														
														String[] maSanphamTDV = khBean.getMaSanphamTDV();
														String[] tenSanphamTDV = khBean.getTenSanphamTDV();
														String[] dvSanphamTDV = khBean.getDonviSanphamTDV();
														String[] sanphamTDV = khBean.getSanphamTDV();
														
														%>
													<tr>
														<td class="plainlabel" colspan="6" >
															
															<TABLE width="100%" border="0" cellspacing="1px" cellpadding="3px" >
																
																<TR class="plainlabel">	
																	<TH width="15%">Mã sản phẩm </TH>
																	<TH width="35%">Tên sản phẩm</TH>
																	<TH width="15%">Đơn vị tính</TH>
																	<TH width="35%">Trình dược viên</TH>
																</TR>
																
																<% if( maSanphamTDV != null ) { 
																	for( int i = 0; i < maSanphamTDV.length; i++ ) { %>
																	
																	<TR class= 'tblightrow'>
																		<TD>
																			<input name = "maSanphamTDV" type="text" value="<%= maSanphamTDV[i] %>" autocomplete='off' onkeyup="ajax_showOptions(this,'abc', event)" style="width:100%" >
																		</TD>
																		<TD>
																			<input name = "tenSanphamTDV" type="text" readonly value="<%= tenSanphamTDV[i] %>" style="width:100%;" >
																		</TD>
																		<TD>
											    							<input name = "dvSanphamTDV" type="text" value="<%= dvSanphamTDV[i] %>" readonly style="width:100%; text-align: center;" >
											    						</TD>
											    						<TD>
											    							<input name= "sanphamTDV" type="text" value="<%= sanphamTDV[i] %>" style="width:100%;" autocomplete='off' onkeyup="ajax_showOptions(this,'tdv', event)" >
											    						</TD>
																	</TR>
																	
																<% }  } %>
																
																<% for( int i = 0; i <= 50; i++ ) { %>
																	
																	<TR class= 'tblightrow'>
																		<TD>
																			<input name = "maSanphamTDV" type="text" value="" autocomplete='off' onkeyup="ajax_showOptions(this,'abc', event)" style="width:100%" >
																		</TD>
																		<TD>
																			<input name = "tenSanphamTDV" type="text" readonly value="" style="width:100%;" >
																		</TD>
																		<TD>
											    							<input name = "dvSanphamTDV" type="text" value="" readonly style="width:100%; text-align: center;" >
											    						</TD>
											    						<TD>
											    							<input name= "sanphamTDV" type="text" value="" style="width:100%;" autocomplete='off' onkeyup="ajax_showOptions(this,'tdv', event)" >
											    						</TD>
																	</TR>
																	
																<% } %>
																
															</TABLE>
															
														</td>
													</tr>
											<% } %>
										
											
											</TABLE>
												
												
											</div>
											
											<!-- THÔNG TIN XUẤT HÓA ĐƠN -->
											<div>
												
												<TABLE border="0" width="100%" cellpadding="1" cellspacing="1">
													<tr>
														<TD class="plainlabel">Mẫu hóa đơn</TD>
														<TD class="plainlabel" colspan="4" >
															<SELECT name="mauhoadon" id="mauhoadon">
																<% if (khBean.getmauhd().equals("1")){ %>
																	<option value="1" selected>Mẫu 1(CN)</option>
																	<option value="2">Mẫu 2(HO)</option>
																<% }else if(khBean.getmauhd().equals("2")) { %>
																	<option value="1">Mẫu 1(CN)</option>
																	<option value="2" selected>Mẫu 2(HO)</option>
																<% } else { %>
																	<option value="1">Mẫu 1(CN)</option>
																	<option value="2">Mẫu 2(HO)</option>
																<% } %>
															</SELECT>
														</TD>
													</tr>
													<tr>
														<th class="plainlabel" style="width: 25%;" >Họ tên người mua hàng</th>
														<th class="plainlabel" style="width: 25%;" >Đơn vị</th>
														<th class="plainlabel" style="width: 35%;" >Địa chỉ</th>
														<th class="plainlabel" style="width: 15%;" >Mã số thuế</th>
													</tr>
													
													<%
														String[] tennguoimuahangXHD = khBean.getTennguoimuahangXHD();
														String[] donviXHD = khBean.getDonviXHD();
														String[] diachiXHD = khBean.getDiachiXHD();
														String[] masothueXHD = khBean.getMasothueXHD();
														
														int stt = 0;
													%>
													
													<% if( donviXHD != null ) { 
														for( int i = 0; i < donviXHD.length; i++ ) {  %>
														
														<TR>
															<td class="plainlabel">
																<input type="text" style="width: 100%" name="hotennguoimuahangXHD" value="<%= tennguoimuahangXHD[i] %>" >
															</td>
															<td class="plainlabel">
																<input type="text" style="width: 100%" name="donviXHD" value="<%= donviXHD[i] %>" >
															</td>
															<td class="plainlabel">
																<input type="text" style="width: 100%" name="diachiXHD" value="<%= diachiXHD[i] %>" >
															</td>
															<td class="plainlabel">
																<input type="text" style="width: 100%" name="masothueXHD" value="<%= masothueXHD[i] %>" >
															</td>
														</TR>	
														
													<% stt++; } } %>
													
													<% for( int i = stt; i < 10; i++ ) { %>
														<TR>
															<td class="plainlabel">
																<input type="text" style="width: 100%" name="hotennguoimuahangXHD" >
															</td>
															<td class="plainlabel">
																<input type="text" style="width: 100%" name="donviXHD" >
															</td>
															<td class="plainlabel">
																<input type="text" style="width: 100%" name="diachiXHD" >
															</td>
															<td class="plainlabel">
																<input type="text" style="width: 100%" name="masothueXHD" >
															</td>
														</TR>		
													<% } %>
													
													<tr>
														<th class="plainlabel" colspan="4" ></th>
													</tr>
												</TABLE>
												
											</div>
											
											<!-- THÔNG TIN BÁN HÀNG -->
											<div>
												
												<TABLE border="0" width="100%" cellpadding="1" cellspacing="1">
													<tr>
														<TD class="plainlabel" colspan="7" >Người phụ trách từ khoa</TD>
													</tr>
													<tr>
														<th class="plainlabel" style="width: 15%;" >Khoa</th>
														<th class="plainlabel" style="width: 20%;" >Họ tên người phụ trách</th>
														<th class="plainlabel" style="width: 10%;" >Chuyên môn</th>
														<th class="plainlabel" style="width: 20%;" >Đang / Đã từng làm ở đâu</th>
														<th class="plainlabel" style="width: 10%;" >Vị trí</th>
														<th class="plainlabel" style="width: 10%;" >Số BS của khoa</th>
														<th class="plainlabel" style="width: 15%;" >Số buồng khám của khoa</th>
													</tr>
													
													<%
														String[] ttbh_khoa = khBean.getTTBH_khoa();
														String[] ttbh_nguoiphutrach = khBean.getTTBH_nguoiphutrach();
														String[] ttbh_chuyenmon = khBean.getTTBH_chuyenmon();
														String[] ttbh_danglamodau = khBean.getTTBH_danglamodau();
														String[] ttbh_vitri = khBean.getTTBH_vitri();
														String[] ttbh_sobs = khBean.getTTBH_sobscuakhoa();
														String[] ttbh_sobuongkham = khBean.getTTBH_sobuongkhamcuakhoa();
														
														stt = 0;
													%>
													
													<% if( ttbh_khoa != null ) { 
														for( int i = 0; i < ttbh_khoa.length; i++ ) {  %>
														
														<TR>
															<td class="plainlabel">
																<input type="text" style="width: 100%" name="ttbh_khoa" value="<%= ttbh_khoa[i] %>" >
															</td>
															<td class="plainlabel">
																<input type="text" style="width: 100%" name="ttbh_tennguoiphutrach" value="<%= ttbh_nguoiphutrach[i] %>" >
															</td>
															<td class="plainlabel">
																<input type="text" style="width: 100%" name="ttbh_chuyenmon" value="<%= ttbh_chuyenmon[i] %>" >
															</td>
															<td class="plainlabel">
																<input type="text" style="width: 100%" name="ttbh_danglamodau" value="<%= ttbh_danglamodau[i] %>" >
															</td>
															<td class="plainlabel">
																<input type="text" style="width: 100%" name="ttbh_vitri" value="<%= ttbh_vitri[i] %>" >
															</td>
															<td class="plainlabel">
																<input type="text" style="width: 100%" name="ttbh_sobscuakhoa" value="<%= ttbh_sobs[i] %>" >
															</td>
															<td class="plainlabel">
																<input type="text" style="width: 100%" name="ttbh_sobuongkhamcuakhoa" value="<%= ttbh_sobuongkham[i] %>" >
															</td>
														</TR>	
														
													<% stt++; } } %>
													
													<% for( int i = stt; i < 10; i++ ) { %>
														<TR>
															<td class="plainlabel">
																<input type="text" style="width: 100%" name="ttbh_khoa" >
															</td>
															<td class="plainlabel">
																<input type="text" style="width: 100%" name="ttbh_tennguoiphutrach" >
															</td>
															<td class="plainlabel">
																<input type="text" style="width: 100%" name="ttbh_chuyenmon" >
															</td>
															<td class="plainlabel">
																<input type="text" style="width: 100%" name="ttbh_danglamodau" >
															</td>
															<td class="plainlabel">
																<input type="text" style="width: 100%" name="ttbh_vitri" >
															</td>
															<td class="plainlabel">
																<input type="text" style="width: 100%" name="ttbh_sobscuakhoa" >
															</td>
															<td class="plainlabel">
																<input type="text" style="width: 100%" name="ttbh_sobuongkhamcuakhoa" >
															</td>
														</TR>		
													<% } %>
													
													<tr>
														<th class="plainlabel" colspan="7" >&nbsp;</th>
													</tr>
													
													<tr>
														<TD class="plainlabel" colspan="7" >Bác sĩ khám chính của khoa</TD>
													</tr>
													<tr>
														<th class="plainlabel" style="width: 15%;" >Khoa</th>
														<th class="plainlabel" style="width: 20%;" >Họ tên người phụ trách</th>
														<th class="plainlabel" style="width: 10%;" >Chuyên môn</th>
														<th class="plainlabel" style="width: 20%;" >Đang / Đã từng làm ở đâu</th>
														<th class="plainlabel" style="width: 10%;" >Vị trí</th>
														<th class="plainlabel" style="width: 10%;" colspan="2" >Số bệnh nhân của khoa</th>
													</tr>
													
													<%
														ttbh_khoa = khBean.getTTBH_khoa2();
														ttbh_nguoiphutrach = khBean.getTTBH_nguoiphutrach2();
														ttbh_chuyenmon = khBean.getTTBH_chuyenmon2();
														ttbh_danglamodau = khBean.getTTBH_danglamodau2();
														ttbh_vitri = khBean.getTTBH_vitri2();
														ttbh_sobs = khBean.getTTBH_sobscuakhoa2();
														
														stt = 0;
													%>
													
													<% if( ttbh_khoa != null ) { 
														for( int i = 0; i < ttbh_khoa.length; i++ ) {  %>
														
														<TR>
															<td class="plainlabel">
																<input type="text" style="width: 100%" name="ttbh_khoa2" value="<%= ttbh_khoa[i] %>" >
															</td>
															<td class="plainlabel">
																<input type="text" style="width: 100%" name="ttbh_tennguoiphutrach2" value="<%= ttbh_nguoiphutrach[i] %>" >
															</td>
															<td class="plainlabel">
																<input type="text" style="width: 100%" name="ttbh_chuyenmon2" value="<%= ttbh_chuyenmon[i] %>" >
															</td>
															<td class="plainlabel">
																<input type="text" style="width: 100%" name="ttbh_danglamodau2" value="<%= ttbh_danglamodau[i] %>" >
															</td>
															<td class="plainlabel">
																<input type="text" style="width: 100%" name="ttbh_vitri2" value="<%= ttbh_vitri[i] %>" >
															</td>
															<td class="plainlabel">
																<input type="text" style="width: 100%" name="ttbh_sobscuakhoa2" value="<%= ttbh_sobs[i] %>" >
															</td>
														</TR>	
														
													<% stt++; } } %>
													
													<% for( int i = stt; i < 10; i++ ) { %>
														<TR>
															<td class="plainlabel">
																<input type="text" style="width: 100%" name="ttbh_khoa2" >
															</td>
															<td class="plainlabel">
																<input type="text" style="width: 100%" name="ttbh_tennguoiphutrach2" >
															</td>
															<td class="plainlabel">
																<input type="text" style="width: 100%" name="ttbh_chuyenmon2" >
															</td>
															<td class="plainlabel">
																<input type="text" style="width: 100%" name="ttbh_danglamodau2" >
															</td>
															<td class="plainlabel">
																<input type="text" style="width: 100%" name="ttbh_vitri2" >
															</td>
															<td class="plainlabel" colspan="2" >
																<input type="text" style="width: 100%" name="ttbh_sobscuakhoa2" >
															</td>
														</TR>		
													<% } %>
													
													<tr>
														<th class="plainlabel" colspan="7" ></th>
													</tr>
													
													
												</TABLE>
												
											</div>
											
											<!-- THÔNG TIN ĐẶT HÀNG -->
											<div>
												
												<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
													<TR>
														<TD class="plainlabel">Kho đặt hàng</TD>
														<TD class="plainlabel" >
														
														<%= util.getSelectBox(khoRs, "", "khoId", "", khBean.getkhoId(), "PK_SEQ", "TEN", "0", false, true) %>
														
														</TD>
														
														<TD class="plainlabel">Người mua hàng</TD>
														<TD class="plainlabel">
															<INPUT type="text" name="chucuahieu" id="chucuahieu" value="<%= khBean.getChucuahieu()%>" >
														</TD>
													</TR>
													<TR>
														<TD class="plainlabel">Hợp đồng</TD>
														<TD class="plainlabel">
															<INPUT type="text" name="hopdong" value="<%=khBean.getHopdong()%>" >
														</TD>
														<TD class="plainlabel">Ngày ký HĐ</TD>
														<TD class="plainlabel">
															<INPUT type="text" name="ngaykyhd" value="<%=khBean.getNgaykyHd()%>" class="days">
														</TD>
													</TR>
													<TR>
														<TD class="plainlabel">Người liên hệ đặt hàng</TD>
														<TD class="plainlabel" >
															<INPUT type="text" name="nguoilhdathang" value="<%= khBean.getNguoiLhDathang() %>">
														</TD>
														<TD class="plainlabel">Phương thức thanh toán</TD>
														<TD class="plainlabel">
															
															<%= util.getSelectBox(phuongthucTTRs, "", "phuongthuctt", "", khBean.getPhuongthucTTId(), "PK_SEQ", "TEN", "0", false, true) %>
														
														</TD>
													</TR>
												
													<TR>
														<TD class="plainlabel">Cấp độ giao hàng</TD>
														<TD class="plainlabel" >
															<SELECT name="capdogiaohang" id="capdogiaohang">
																<%
																	if (khBean.getCapdogiaohang().equals("4")){
																%>
																<option value="4" selected>4 tiếng</option>
																<option value="8">8 tiếng</option>
																<option value="24">24 tiếng</option>
																<%
																	}else if(khBean.getCapdogiaohang().equals("8")) {
																%>
																<option value="4">4 tiếng)</option>
																<option value="8" selected>8 tiếng</option>
																<option value="24">24 tiếng</option>
																<%
																	} else if(khBean.getCapdogiaohang().equals("24")) {
																%>
																<option value="4">4 tiếng</option>
																<option value="8">8 tiếng</option>
																<option value="24" selected>24 tiếng</option>
																<%
																	} else {
																%>
																<option value="4">4 tiếng</option>
																<option value="8">8 tiếng</option>
																<option value="24">24 tiếng</option>
																<%
																	}
																%>
															</SELECT>
														</TD>
														<TD class="plainlabel">Số ngày vận chuyển</TD>
														<TD class="plainlabel">
															<INPUT type="text" name="songayvanchuyen" value="<%=khBean.getSongayvanchuyen()%>">
														</TD>
													</TR>
													
													<tr>
														<TD class="plainlabel">SĐT người đặt hàng</TD>
														<TD class="plainlabel" >
															<input type="text" name="dienthoaiNDH" value="<%=khBean.getSdtnguoidathang()%>" >
														</TD>
														<TD class="plainlabel">Phần trăm chiết khấu</TD>
														<TD class="plainlabel" >
															<input type="text" name="ptCHIETKHAU" value="<%=khBean.getPT_Chietkhau()%>" style="text-align: right;"> %
														</TD>
													</tr>
													
													<% if( khBean.getKbhId().contains("100052") ) { 
														
														String[] idSanpham = khBean.getIdSanpham();
														String[] maSanpham = khBean.getMaSanpham();
														String[] tenSanpham = khBean.getTenSanpham();
														String[] dvSanpham = khBean.getDonviSanpham();
														String[] ckSanpham = khBean.getChietkhauSanpham();
														
														%>
													<tr>
														<td class="plainlabel" colspan="4" >
															
															<TABLE width="100%" border="0" cellspacing="1px" cellpadding="3px" >
																
																<TR class="plainlabel">	
																	<TH width="25%">Mã sản phẩm </TH>
																	<TH width="45%">Tên sản phẩm</TH>
																	<TH width="15%">Đơn vị tính</TH>
																	<TH width="15%">Phần trăm chiết khấu</TH>
																</TR>
																
																<% if( idSanpham != null ) { 
																	for( int i = 0; i < idSanpham.length; i++ ) { %>
																	
																	<TR class= 'tblightrow'>
																		<TD>
																			<input name = "idSanpham" value="<%= idSanpham[i] %>"  type='hidden' >
																			<input name = "maSanpham" type="text" value="<%= maSanpham[i] %>" autocomplete='off' onkeyup="ajax_showOptions(this,'abc',event)" style="width:100%" >
																		</TD>
																		<TD>
																			<input name = "tenSanpham" type="text" readonly value="<%= tenSanpham[i] %>" style="width:100%">
																		</TD>
																		<TD>
											    							<input name = "dvSanpham" type="text" value="<%= dvSanpham[i] %>" readonly style="width:100%; text-align: center;">
											    						</TD>
											    						<TD>
											    							<input name= "ckSanpham" type="text" value="<%= ckSanpham[i] %>" style="width:100%;text-align:right">
											    						</TD>
																	</TR>
																	
																<% }  } %>
																
																<% for( int i = 0; i <= 50; i++ ) { %>
																	
																	<TR class= 'tblightrow'>
																		<TD>
																			<input name = "idSanpham" value=""  type='hidden' >
																			<input name = "maSanpham" type="text" value="" autocomplete='off' onkeyup="ajax_showOptions(this,'abc',event)" style="width:100%" >
																		</TD>
																		<TD>
																			<input name = "tenSanpham" type="text" readonly value="" style="width:100%">
																		</TD>
																		<TD>
											    							<input name = "dvSanpham" type="text" value="" readonly style="width:100%; text-align: center;">
											    						</TD>
											    						<TD>
											    							<input name= "ckSanpham" type="text" value="" style="width:100%;text-align:right">
											    						</TD>
																	</TR>
																	
																<% } %>
																
															</TABLE>
															
														</td>
													</tr>
													<% } %>
													
												</TABLE>
												
											</div>
											
											<!-- THÔNG TIN GIAO HÀNG -->
											<div>
												
												<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
													<tr>
														<TD class="plainlabel" style="width: 130px;" >Nhân viên giao nhận </TD>
														<TD class="plainlabel">
														
														<%= util.getSelectBox(nvgn, "width:600px;", "nvgnTen", "", khBean.getNvgnId(), "nvgnId", "nvgnTen", "0", true, true) %>
															
														</TD>
													</tr>
													<tr>
														<TD class="plainlabel">Phương thức giao hàng</TD>
														<TD class="plainlabel">
															<INPUT type="text" name="ptgiaohang" value="<%=khBean.getPtgiaohang()%>" >
														</TD>
													</tr>
													<tr>				
														<TD class="plainlabel">Chành xe</TD>
													
														<TD class="plainlabel">
															<input type="text" name="chanhxe" value="<%=khBean.getChanhxe()%>" >
														</TD>	
													</tr>
													<tr>				
														<TD class="plainlabel">Địa chỉ chành xe</TD>
													
														<TD class="plainlabel">
															<input type="text" name="chanhxeDIACHI" value="<%= khBean.getDiachiChanhxe() %>" >
														</TD>	
													</tr>
													<tr>				
														<TD class="plainlabel">SĐT chành xe</TD>
													
														<TD class="plainlabel">
															<input type="text" name="chanhxeSDT" value="<%= khBean.getSdtChanhxe() %>" >
														</TD>	
													</tr>
												</TABLE>
												
											</div>
											
											<!-- THÔNG TIN CÔNG NỢ -->
											<div>
												
												<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
												
													<TR>
														<TD class="plainlabel">Tên người thanh toán</TD>
														<TD class="plainlabel">
															<INPUT type="text" name="nguoitt" value="<%=khBean.getNguoiTT()%>">
														</TD>
														<TD class="plainlabel">SĐT người thanh toán</TD>
														<TD class="plainlabel">
															<INPUT type="text" name="sdtnguoitt" value="<%=khBean.getSoDTNguoiTT()%>">
														</TD>
													</tr>
													<tr>
														<TD class="plainlabel">Thời hạn nợ</TD>
														<TD class="plainlabel" >
															<% if(khBean.getThoihanno().length()>0) { %> 
																<INPUT type="text" name="thoihanno" value="<%=khBean.getThoihanno()%>" > 
															<% } else { %> 
																<INPUT type="text" name="thoihanno" value="0" size="15"> 
															<% } %> (số ngày)
														</TD>
														<TD class="plainlabel">Ghi nhận công nợ</TD>
														<TD class="plainlabel">
															<INPUT type="text" name="ghinhancongno"value="<%=khBean.getGhinhancongno()%>">
														</TD>
													</TR>
													<TR>
														<TD class="plainlabel">Chiết khấu thanh toán (%)</TD>
														<TD class="plainlabel" >
															<INPUT type="text" name="chietkhauthanhtoan"value="<%= khBean.getCktt() %>">								
														</TD>
														
														<TD class="plainlabel">Hạn mức nợ</TD>
														<TD class="plainlabel" >
															<% if(khBean.getThoihanno().length()>0) {  %> 
																<INPUT type="text" name="hanmucno" value="<%=formatter.format(Double.parseDouble(khBean.getHanmucno()))%>"  onkeyup="Dinhdang(this)" onkeypress="return keypress(event);" > 
															<% } else { %> 
																<INPUT type="text" name="hanmucno" value="0" size="15" onkeyup="Dinhdang(this)" onkeypress="return keypress(event);"> 
															<% } %> (số tiền)
														</TD>
													</TR>
													
												</TABLE>
			
											</div>
											
											<!-- THÔNG TIN TUYẾN BÁN HÀNG -->
											<div>
												
												<TABLE width="100%" border="0" cellspacing="1" cellpadding="6">
													<TR class="tbheader">
														<TH width="10%">Số thứ tự</TH>
														<TH width="60%">Tuyến</TH>
														<TH width="20%">Tần số</TH>
														<TH width="10%">Chọn</TH>
													</TR>
			
													<%
													int i = 0;
													String lightrow = "tblightrow";
													String darkrow = "tbdarkrow";
													if(tbhRs != null){
													try{while(tbhRs.next()){ 
														if (i % 2 != 0) {%>
															<TR class=<%=lightrow%>>
														<%} else {%>
															<TR class=<%= darkrow%>>
														<%}%>
														<TD align="center">
															<input name='sott' type="text" value="<%= tbhRs.getString("sott") %>" readonly="readonly" style="width: 100%">
														</TD>
														<TD align="center">
															<%= tbhRs.getString("tbhTen") %>
														</TD>
			
														<TD align="center">
														<% 
											  			String[] tenloaicuahang = new  String[] {"F1-1","F1-2","F1-3","F1-4","F2","F4"}; 
											  			String[] idloaicuahang = new  String[] {"F1-1","F1-2","F1-3","F1-4","F2","F4"}; %> 
											  			
											  			<select style="width: 130px" name="<%= tbhRs.getString("tbhId") %>_tanso">
																<option value=""></option>
																<% for( int j=0;j<idloaicuahang.length;j++) { 
											    			if(idloaicuahang[j].equals(tbhRs.getString("tanso" ))){ %>
																<option value='<%=idloaicuahang[j]%>' selected><%=tenloaicuahang[j] %></option>
																<%}else{ %>
																<option value='<%=idloaicuahang[j]%>'><%=tenloaicuahang[j] %></option>
																<%} 
											      		 } %>
														</select></TD>
			
														<% if (khBean.getTbhId() != null && khBean.getTbhId().contains(tbhRs.getString("tbhId"))){ %>
															<TD align="center">
																<input name="tbhId" type="checkbox" value="<%= tbhRs.getString("tbhId") %>" checked>
															</TD>
														<%}else{%>
															<TD align="center">
																<input name="tbhId" type="checkbox" value="<%= tbhRs.getString("tbhId") %>">
															</TD>
														<%}%>
													</TR>
													<% i++; } } catch(Exception e) { e.printStackTrace(); }
													} %>
													<tr class="tbfooter">
														<td colspan="4">&nbsp;</td>
													</tr>
												</TABLE>
												
											</div>
											
											<!-- THÔNG TIN PHÂN NHÓM -->
											<div>
												
												<TABLE width="100%" border="0" cellspacing="1" cellpadding="6">
													<TR class="tbheader">
														<TH width="20%">Nhóm khách hàng</TH>
														<TH width="10%">Chọn</TH>
													</TR>
			
													<%
													 i = 0;
													 lightrow = "tblightrow";
													 darkrow = "tbdarkrow";
													if(nkh_khList != null)
													{
														try{while(nkh_khList.next())
														{ 
														if (i % 2 != 0) {%>
														<TR class=<%=lightrow%>>
															<%} else {%>
														
														<TR class=<%= darkrow%>>
															<%}%>
															<TD align="center">
																<%= nkh_khList.getString("nkhTen") %>
															</TD>
															<% if (nkh_khIds.contains(nkh_khList.getString("nkhId"))){ %>
															<TD align="center">
																<input name="nkh_khList" type="checkbox" value="<%= nkh_khList.getString("nkhId") %>" checked>
															</TD>
															<%}else{%>
															<TD align="center">
																<input name="nkh_khList" type="checkbox" value="<%= nkh_khList.getString("nkhId") %>">
															</TD>
															<%}%>
														</TR>
														<% i++; }}catch(Exception e){}
													}
										  			%>
													<tr class="tbfooter">
														<td colspan="4">&nbsp;</td>
													</tr>
												</TABLE>
												
											</div>
											
										</div>
										
									</TD>
								</TR>
								
							</TABLE>
						</FIELDSET>
					</TD>
				</TR>

			</TABLE>
		
			
			
		</TD>
	</TR>
</TABLE>
</form>


<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>
<script type="text/javascript">
	//dropdowncontent.init('tenxuatHD', "left-top", 300, "click");
	replaces();
	
	jQuery(function()
	{		
		$("#khTen").autocomplete("KhachHangTaoMoiList.jsp");	
		$("#benhvien").autocomplete("KhachHangTaoMoiList.jsp");	
	});	
	
</script>
	
</BODY>
</HTML>

<%
	if (khBean != null) 
	{
		khBean.DBclose();
		khBean = null;
	}

	try {
		if (bgst != null)
			bgst.close();
		if (hch != null)
			hch.close();
		if (kbh != null)
			kbh.close();
		if (loaiChRs != null)
			loaiChRs.close();
		if (mck != null)
			mck.close();
		if (nkh_khList != null)
			nkh_khList.close();
		if (tp != null)
			tp.close();
		if (qh != null)
			qh.close();
		if (vtch != null)
			vtch.close();
		if (nch != null) {
			nch.close();
		}
		if (nvgn != null) {
			nvgn.close();
		}
		if (nkh_khList != null) {
			nkh_khList.close();
		}
		if (nkh_khIds != null) {
			nkh_khIds.clear();
		}
		if (quocgiaRs != null) {
			quocgiaRs.close();
		}
		if (phuongthucTTRs != null) {
			phuongthucTTRs.close();
		}
		if (phuongxaRs != null) {
			phuongxaRs.close();
		}
		session.setAttribute("khBean", null);
	} 
	catch (Exception e) { }
%>
<%
	}
%>
