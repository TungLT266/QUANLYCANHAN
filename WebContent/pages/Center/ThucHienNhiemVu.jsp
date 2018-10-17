<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="geso.traphaco.center.beans.thuchiennhiemvu.imp.ThucHienNhiemVu"%>
<%@ page import="geso.traphaco.center.beans.thuchiennhiemvu.IThucHienNhiemVu"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="geso.traphaco.center.util.*"%>
<%@ page import="java.util.Hashtable"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="java.text.NumberFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.Date"%>
<%
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");

%>
<%
	IThucHienNhiemVu thnvBean = (IThucHienNhiemVu) session.getAttribute("thnvBean");
		ResultSet rsKbh = (ResultSet) thnvBean.getRsKbh();
		ResultSet rsDvkd = (ResultSet) thnvBean.getRsDvkd();
		ResultSet rsNv = (ResultSet) thnvBean.getRsNv();
%>

<%
	NumberFormat formatter = new DecimalFormat("#,###,###");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="java.sql.SQLException"%>
<HTML>
<HEAD>
<TITLE>OneOne - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<SCRIPT language="JavaScript" type="text/javascript">
function submitform()
{
	document.forms['dckmForm'].submit();
}

function saveform()
{	
	document.dckmForm.action.value= "save";
	document.forms['dckmForm'].submit();
}


function upload()
{
 	document.forms['dckmForm'].action="../../ThucHienNhiemVuSvl";
	document.forms['dckmForm'].setAttribute('enctype', "multipart/form-data", 0);
    document.forms['dckmForm'].submit();
}

function xuatExcel() {
	document.forms['dckmForm'].action.value = 'excel';
	document.forms['dckmForm'].submit();
}
function thongbao(){
	var tt = document.forms['dckmForm'].msg.value;
	if(tt.length>0)
    alert(document.forms['dckmForm'].msg.value);
}

function keypress(e) //Hàm dùng d? ngan ngu?i dùng nh?p các ký t? khác ký t? s? vào TextBox
{    
	var keypressed = null;
	if (window.event)
		keypressed = window.event.keyCode; //IE
	else
		keypressed = e.which; //NON-IE, Standard
	
	if (keypressed < 48 || keypressed > 57)
	{ 
		if (keypressed == 8 || keypressed == 127 || keypressed == 37 || keypressed == 39)
		{//Phím Delete và Phím Back
			return;
		}
		return false;
	}
}
</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="dckmForm" method="post" action="../../ThucHienNhiemVuSvl">
		<input type="hidden" name="userId" value="<%=userId%>">
		<input type="hidden" name="action" value="0">
		<input type="hidden" name="msg" value='<%=thnvBean.getMsg()%>'>
		<script language="javascript" type="text/javascript">
    thongbao();
</script>
		<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
			height="100%">
			<TR>
				<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<TR>
							<TD align="left" class="tbnavigation">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr height="22">
										<TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản
											lý chỉ tiêu &gt; Đánh giá nhiệm vụ</TD>
										<TD colspan="2" align="right" class="tbnavigation">Chào
											mừng Bạn <%=userTen%>&nbsp;
										</TD>
									</tr>
								</table>
							</TD>
						</TR>
					</TABLE>
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<TR>
							<TD>
								<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
									<TR class="tbdarkrow">
										<TD width="30" align="left"><A
											href="javascript:saveform();"><img
												src="../images/Save30.png" alt="Quay về" title="Lưu lại"
												border="1" longdesc="Lưu lại" style="border-style: outset"></A></TD>

										<TD>&nbsp;</TD>
									</TR>
								</TABLE>
							</TD>
						</TR>
					</TABLE>
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<tr>
							<TD>
								<FIELDSET>
									<LEGEND class="legendtitle">Thông báo </LEGEND>
									<textarea name="dataerror" style="width: 100%"
										readonly="readonly" rows="1"><%=thnvBean.getMsg()%></textarea>
									<TABLE border="0" width="100%" cellpadding="4" cellspacing="0">
										<TR>
										<tr>
											<TD class="plainlabel" style="width:150px">Đối tượng</TD>
											<TD class="plainlabel" colspan="2">
												<select name="doituong" onchange="submitform()">
												   <option value=""></option>
													<%if(thnvBean.getDoituong().equals("SR")) {%>
												   <option value="SR" selected="selected">SR</option>
												   <option value="SS">SS</option>
												   <option value="ASM">ASM</option>
												   <%}else if(thnvBean.getDoituong().equals("SS")){ %>
												   <option value="SR" >SR</option>
												   <option value="SS" selected="selected">SS</option>
												   <option value="ASM">ASM</option>
												   
												   <%}else if(thnvBean.getDoituong().equals("ASM")){ %>
												   <option value="SR" >SR</option>
												   <option value="SS" >SS</option>
												   <option value="ASM" selected="selected">ASM</option>
												   
												   <%}else { %>
												   <option value="SR" >SR</option>
												   <option value="SS" >SS</option>
												   <option value="ASM" >ASM</option>
												   <%} %>
												</select>														
											</TD>

										</tr>
										 <TR>
                    <TD class="plainlabel">Tháng</TD>
                    <TD class="plainlabel" colspan="2"> 
                     <select name="thang" style="width: 150px"  onchange="submitform()">
											<option value=""></option>
											<%
												
												for (int n = 1; n <=12; n++) {
													if (n == Integer.parseInt(thnvBean.getThang())) {
												%>
												<option value=<%=n%> selected="selected">
													<%=n%></option>
												<%
													} else {
												%>
												<option value=<%=n%>><%=n%></option>
												<%
												}
												}
											%>
									</select>
                  	</TD>
                </TR>
				<TR>
                    <TD class="plainlabel">Năm</TD>
                    <TD class="plainlabel" colspan="2"> 
                   <select name="nam" style="width: 150px"  onchange="submitform()">
											<option value=""></option>
											<%
												Calendar cal = Calendar.getInstance();
												int year_ = cal.get(Calendar.YEAR);
												for (int n = 2012; n < year_ + 3; n++) {
													if (n == Integer.parseInt(thnvBean.getNam())) {
											%>
											<option value=<%=n%> selected="selected">
												<%=n%></option>
											<%
												} else {
											%>
											<option value=<%=n%>><%=n%></option>
											<%
												}
												}
											%>
									</select>
                  </TD>
                </TR>
										<tr>
											<TD class="plainlabel">Kênh bán hàng</TD>
											<TD class="plainlabel" colspan="2"><select name="kbhId">
													<%
														if (rsKbh != null) {
																while (rsKbh.next()) {
													%>
													<option value="<%=rsKbh.getString("pk_seq")%>"><%=rsKbh.getString("ten")%>
													</option>
													<%
														}
															}
													%>
											</select></TD>
										</tr>
										<tr>
											<TD class="plainlabel">Đơn vị kinh doanh</TD>
											<TD class="plainlabel" colspan="2"><select name="dvkdId">
													<%
														if (rsDvkd != null) {
																while (rsDvkd.next()) {
													%>
													<option value="<%=rsDvkd.getString("pk_seq")%>"><%=rsDvkd.getString("ten")%>
													</option>
													<%
														}
															}
													%>
											</select></TD>
										</tr>

										<tr>
											<TD class="plainlabel" >Nhiệm vụ tháng</TD>
											<TD class="plainlabel" colspan="2">
											<select name="nvId">
												<option value=""></option>
													<%
														if (rsNv != null) {
																while (rsNv.next()) {
													 if(thnvBean.getNvId().equals(rsNv.equals("pk_seq"))) { %>
													<option value="<%=rsNv.getString("pk_seq")%>" selected="selected"><%=rsNv.getString("ten")%></option>
													<%
														}else {  %>
														<option value="<%=rsNv.getString("pk_seq")%>"><%=rsNv.getString("ten")%></option>
														<%}	}}
													%>
											</select></TD>
										</tr>
										
										<TR>
											<TD class="plainlabel">Chọn tập tin</TD>
											<TD class="plainlabel" colspan="2"><INPUT type="file" name="filename" size="40" value=''></TD>
										</TR>
										<TR>
											<TD class="plainlabel" colspan="2">&nbsp;&nbsp;
												<a class="button2" href="javascript:xuatExcel()"> <img style="top: -4px;" src="../images/button.png" alt="">Xuất Excel</a>
														 &nbsp;&nbsp;&nbsp;&nbsp;
												<a class="button" href="javascript:upload();"> <img style="top: -4px;" src="../images/button.png" alt=""> Upload </a>
											</TD>
											<td class="plainlabel"></td>
										</TR>
										<TR>
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
<%
	thnvBean.DBClose();
%>
</HTML>
<%
	
%>
