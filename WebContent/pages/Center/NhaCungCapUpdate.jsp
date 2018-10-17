<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.beans.nhacungcap.INhacungcap" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<% INhacungcap ncc = (INhacungcap)session.getAttribute("ncc"); %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print"
	href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">

<SCRIPT language="javascript" type="text/javascript">
 function confirmLogout(){
    if(confirm("Ban co muon dang xuat?")){
top.location.href = "home.html";
    }
    return
  }
</SCRIPT>
<SCRIPT language="JavaScript" type="text/javascript">
<!--HPB_SCRIPT_CODE_40
//-->
function submitform()
{
    document.forms["nccForm"].submit();
}

  
</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="nccForm" method="post" action="../../NhacungcapUpdateSvl" charset="UTF-8">
<INPUT name="nccId" type="hidden" value='<%=ncc.getId() %>' size="30">
<INPUT name="userId" type="hidden" value='<%=userId %>' size="30">
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#ffffff">
			<TABLE width="100%" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">

					   	<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							   <TD align="left" colspan="2" class="tbnavigation">
							   		&nbsp;Dữ liệu nền &gt; &nbsp; Cơ bản &gt; Nhà cung cấp
							   &gt; Cập nhật</TD>
							   <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %> &nbsp;</td> 
						    </tr>
   					</TABLE>
					</TD>
				</TR>
			</TABLE>	
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR ><TD >
						<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
								<TR class = "tbdarkrow">
									<TD width="30" align="left"><A href="../../NhacungcapSvl?userId=<%=userId %>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
								    <TD width="2" align="left" ></TD>
								    <TD width="30" align="left" ><A href="javascript: submitform()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A></TD>
									<TD >&nbsp; </TD>						
								</TR>
						</TABLE>
				</TD></TR>
			</TABLE>
				
			<TABLE width = 100% cellpadding = "3" cellspacing = "0" border = "0">
				  	<tr>
							<TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
								<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>
				
			    				<textarea name="dataerror"  style="width: 100% ; font-weight:bold"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1"  ><%=ncc.getMessage() %></textarea>
								</FIELDSET>
						   </TD>
					</tr>

				  	<tr>
						   <TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
								<LEGEND class="legendtitle">Thông tin nhà cung cấp
								</LEGEND>
								<TABLE class="tblight" width="100%" cellspacing="0" cellpadding="6">
									<TR>
									  <TD class="plainlabel" >Tên nhà cung cấp <FONT class="erroralert">*</FONT></TD>
									  <TD class="plainlabel" ><INPUT name="TenNCC_long" type="text" value='<%=ncc.getTen()%>' style="width:200px" ></TD>
								  	  <TD class="plainlabel" >Địa chỉ<FONT class="erroralert">*</FONT></TD>
									  <TD class="plainlabel" ><INPUT name="DiaChi" type="text" value='<%=ncc.getDiachi() %>' style="width:200px" ></TD>
								  </TR>
								  
								  
									<TR>
									  <TD class="plainlabel" >Tên viết tắt <FONT class="erroralert">*</FONT></TD>
									  <TD class="plainlabel" ><input name="TenNCC_short" type="text" style="width:200px" value='<%=ncc.getTenviettat() %>'></TD>
								 	  <TD class="plainlabel" >Mã số thuế<FONT class="erroralert">*</FONT></TD>
									  <TD class="plainlabel" ><INPUT name="MaSoThue" type="text" value='<%=ncc.getMasothue() %>'  ></TD>
								  </TR>
								  
								  
								  <TR>
									  <TD class="plainlabel" >Địa chỉ<FONT class="erroralert">*</FONT></TD>
									  <TD class="plainlabel" ><INPUT name="DiaChi" type="text" value='<%=ncc.getDiachi() %>' style="width:200px" ></TD>
									  <TD class="plainlabel" >Ký hiệu hóa đơn<FONT class="erroralert">*</FONT></TD>
									  <TD class="plainlabel" ><INPUT name="kyhieuhoadon" type="text" value='<%=ncc.getKyhieuhoadon() %>'  ></TD>
								  </TR>
									
									  
								
								  <TR>
									  <TD class="plainlabel" >Số hóa đơn: Từ<FONT class="erroralert">*</FONT></TD>
									  <TD class="plainlabel" ><INPUT name="sohoadontu" type="text" value='<%=ncc.getSohoadontu() %>'  ></TD>
								      <TD class="plainlabel" >             Đến<FONT class="erroralert">*</FONT></TD>
									  <TD class="plainlabel" ><INPUT name="sohoadonden" type="text" value='<%=ncc.getSohoadonden() %>'  ></TD>
								  </TR>
								  
									<TR>
										<TD  class="plainlabel" ><label>
										<%  if (ncc.getTrangthai().equals("1")){%>
										  	<input name="trangthai" type="checkbox" value ="1" checked>
										<%} else {%>
											<input name="trangthai" type="checkbox" value ="0">
										<%} %>
									    Hoạt động</label></TD>
										
										<TD class="plainlabel"></TD>
										<TD class="plainlabel"></TD>
										<TD class="plainlabel"></TD>
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
</BODY>
</HTML>
<%
ncc.DBClose();
ncc = null;
session.setAttribute("ncc", null);
}
%>