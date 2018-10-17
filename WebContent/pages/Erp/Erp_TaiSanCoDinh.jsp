<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="geso.traphaco.center.util.*"%>
<%@ page import="geso.traphaco.erp.beans.taisancodinh.IErp_TaiSanCoDinh"%>
<%@ page import="geso.traphaco.erp.beans.taisancodinh.imp.Erp_TaiSanCoDinh"%>
<%
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{	
		IErp_TaiSanCoDinh obj = (IErp_TaiSanCoDinh) session.getAttribute("obj");
		ResultSet RsTs = obj.getRsts();
		ResultSet RsLts = obj.getRsLoaitaisan() ;
		ResultSet RsNts = obj.getRsNts();
		ResultSet RsTtcp = obj.getRsTtcp();
	
	 	int[] quyen = new  int[5];
	 	quyen = util.Getquyen("Erp_TaiSanCoDinhSvl","",userId);


%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>Best - Project</title>
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<meta http-equiv="Content-Style-Type" content="text/css">
<link rel="stylesheet" href="../css/main.css" type="text/css">
<link rel="stylesheet" href="../css/calendar.css" type="text/css">
<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
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
<script language="javascript" type="text/javascript">

function xoaform()
{
    document.tsForm.ma.value = "";
    document.tsForm.ltsId.value = "";
    document.tsForm.ntsId.value = "";
    document.tsForm.trangthai.value = "";
    document.tsForm.ttcpId.value = "";
    submitform();
}

function submitform()
{
	document.forms['tsForm'].action.value= 'search';
	document.forms['tsForm'].submit();
}

function newform()
{
	document.forms['tsForm'].action.value= 'new';
	document.forms['tsForm'].submit();
}

</script>
</head>
<body leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="tsForm" method="post" action="../../Erp_TaiSanCoDinhSvl">
		<input type="hidden" name="userId" value='<%=userId%>'> <input type="hidden" name="action" value='1'>
		<script language="javascript" type="text/javascript">

			thongbao();
		</script>
		<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
			<tr>
				<td colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
					<table width="100%" cellpadding="0" cellspacing="1">
						<tr>
							<td align="left">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr height="22">
										<td align="left" colspan="2" class="tbnavigation">Quản lý tài sản > Tài sản</td>
										<td align="right" colspan="2" class="tbnavigation">Chào mừng bạn <%=userTen%> &nbsp;
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td>
								<table border="0" width="100%" cellpadding="0" cellspacing="0">
									<tr>
										<td width="100%" align="center">
											<fieldset>
												<legend class="legendtitle">Tiêu chí tìm kiếm &nbsp;</legend>
												<table width="100%" cellpadding="6" cellspacing="0">
													<tr>
														<td class="plainlabel" width="15%">Mã tài sản</td>
														<TD class="plainlabel" colspan="3"><input type="text" name="ma" value="<%=obj.getMa()%>" onchange="submitform()"></td>
																												
<!-- 													<td class="plainlabel"><select name="phanloai" onchange="submitform()"> -->
<!-- 															<option value=""></option> -->
<%-- 													<% if(obj.getPhanloai().equals("1")){ %> --%>
<!-- 															<option value="1" SELECTED >Tài sản cố định</option> -->
															
<!-- 															<option value="2" >Tài sản cố định xây dựng dở dang</option> -->
<%-- 													<%}else if(obj.getPhanloai().equals("2")){ %> --%>
<!-- 															<option value="1" >Tài sản cố định</option> -->
															
<!-- 															<option value="2" SELECTED >Tài sản cố định xây dựng dở dang</option>												 -->
														
<%-- 													<%}else{ %> --%>
<!-- 															<option value="1" >Tài sản cố định</option> -->
															
<!-- 															<option value="2" >Tài sản cố định xây dựng dở dang</option>		 -->
<%-- 													<%} %> --%>
<!-- 														</select></td>														 -->
														
													</tr>
													<tr>
														<td class="plainlabel" width="15%">Loại tài sản</td>
														<td class="plainlabel"><select name="ltsId" onchange="submitform()">
																<option value=""></option>
																<%if (RsLts != null) {
														while (RsLts.next()) {
															if (obj.getLtsId().equals(RsLts.getString("pk_seq"))) {%>
																<option selected="selected" value="<%=RsLts.getString("pk_seq")%>"><%=RsLts.getString("diengiai")%></option>
																<%} else {%>
																<option value="<%=RsLts.getString("pk_seq")%>"><%=RsLts.getString("diengiai")%></option>
																<%}

															}
														}%>
														</select></td>
	
														<td class="plainlabel" width="15%">Nhóm tài sản</td>
														<td class="plainlabel"><select name="ntsId" onchange="submitform()">
																<option value=""></option>
																<%if (RsNts != null) {
														while (RsNts.next()) {
															if (obj.getNtsId().equals(RsNts.getString("pk_seq"))) {%>
															<option selected="selected" value="<%= RsNts.getString("pk_seq")%>"><%= RsNts.getString("ma") + " - " + RsNts.getString("diengiai")%></option>
															<%} else {%>
															<option value="<%= RsNts.getString("pk_seq")%>"><%= RsNts.getString("ma") + " - " + RsNts.getString("diengiai") %></option>
															<%}
												
														}
													}%>
														</select></td>
													</tr>
													<TR>
													<TD class="plainlabel" >Trạng thái</TD>
													<TD class="plainlabel" >
														<select name = "trangthai" id= "trangthai" style = "width:200px" onchange="submitform()">
															<%if(obj.getTrangthai().equals("")){ %>
															<option value=""></option>
															<option value="0">Ngưng hoạt động</option>
															<option value="1">Hoạt động</option>
															<option value="2">Đã thanh lý</option>
															<%}else if(obj.getTrangthai().equals("0")){ %>
															<option value=""></option>
															<option value="0" selected>Ngưng hoạt động</option>
															<option value="1">Hoạt động</option>
															<option value="2">Đã thanh lý</option>
															<%} else if(obj.getTrangthai().equals("1")){ %>
															<option value=""></option>
															<option value="0">Ngưng hoạt động</option>
															<option value="1" selected>Hoạt động</option>
															<option value="2">Đã thanh lý</option>
															<%} else if(obj.getTrangthai().equals("2")){ %>
															<option value=""></option>
															<option value="0">Ngưng hoạt động</option>
															<option value="1">Hoạt động</option>
															<option value="2" selected>Đã thanh lý</option>
															<%}  %>
														</select>
													</TD>
													
														<td class="plainlabel">Trung tâm chi phí</td>
												<TD class="plainlabel" colspan="3"><select name="ttcpId" id="ttcpId" style="width: 200px" onchange="submitform()" >
														<option value=""></option>
														<%
															if (RsTtcp != null){
																while (RsTtcp.next()) {
																	if (obj.getTtcp().equals(
																			RsTtcp.getString("PK_SEQ"))) 
																	{
														%>
															<option value="<%=RsTtcp.getString("PK_SEQ")%>" selected="selected"><%=RsTtcp.getString("Ten")%></option>
														<%
																	} else {
														%>
															<option value="<%=RsTtcp.getString("PK_SEQ")%>"><%=RsTtcp.getString("Ten")%></option>
														<%
																	}
																}
																RsTtcp.close();	
															}
														%>
												</select></td>
													</TR>
													<tr>
														<td colspan="4" class="plainlabel"><a class="button2" href="javascript:xoaform()"> <img style="top: -4px;"
																src="../images/button.png" alt="">Nhập lại
														</a>&nbsp;&nbsp;&nbsp;&nbsp;</td>
													</tr>
												</table>
											</fieldset>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td width="100%">
								<fieldset>
									<legend class="legendtitle">
									
										&nbsp;Tài sản &nbsp;&nbsp;&nbsp;  
										<%if(quyen[0]!=0){ %>
										<a class="button3" href="javascript:newform()"> <img style="top: -4px;" src="../images/New.png" alt="">Tạo
											mới
										</a>
										<%} %>
									</legend>
									<table class="tabletitle" width="100%" border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td align="left" colspan="4" class="legendtitle">
												<table width="100%" border="0" cellspacing="1" cellpadding="4">
													<tr class="tbheader">
														<th width="10%">Mã </th>
														<th width="25%">Diễn giải</th>
														<th width="15%">Phân loại</th>
														<th width="15%">Trạng thái</th>
														<th width="15%">Loại tài sản</th>
														<th width="15%">TT chi phí</th>
														<th width="5%" align="center">&nbsp;Tác vụ</th> 
													</tr>
								<%	int m = 0;
									String lightrow = "tblightrow";
									String darkrow = "tbdarkrow";
									if (RsTs != null)
										while (RsTs.next()) {
											if (m % 2 != 0) {%>
													<tr class=<%=lightrow%>>
														<%} else {%>
													
													<tr class=<%=darkrow%>>
														<%}%>
										<%	if((RsTs.getDouble("SOTHANGKH") == 0 || RsTs.getString("THANGBATDAUKH").trim().length() == 0) 
												& RsTs.getString("PHANLOAI").equals("TSCD") & RsTs.getString("TRANGTHAI").equals("1")){%>
												
														<td align="center"><FONT class="erroralert"><%=RsTs.getString("ma")%></FONT></td>
														<td align="left"><FONT class="erroralert"><%=RsTs.getString("diengiai")%></FONT></td>
														
														<td align="center"><FONT class="erroralert"><%=RsTs.getString("phanloai")%></FONT></td>
														
														<%if (RsTs.getString("trangthai").equals("1")) {%>
														<td align="center"><FONT class="erroralert">Hoạt Động</FONT></td>
														<%} else if (RsTs.getString("trangthai").equals("0")){%>
														<td align="center"><FONT class="erroralert">Ngưng Hoạt Động</FONT></td>
														<%}else{%>
														<td align="center"><FONT class="erroralert">Đã chuyển thành tài sản</FONT></td>
														
														<%} %>
														<td align="left"><FONT class="erroralert"><%=RsTs.getString("loaitaisan")%></FONT></td>
														<td align="left"><FONT class="erroralert"><%=RsTs.getString("nhomtaisan")%></FONT></td>
														<td align="left"><FONT class="erroralert"><%=RsTs.getString("ttcp")%></FONT></td>
											<%}else{ %>
														<td align="center"><%=RsTs.getString("ma")%></td>
														<td align="left"><%=RsTs.getString("diengiai")%></td>
														
														<td align="center"><%=RsTs.getString("phanloai")%></td>
														
														<%if (RsTs.getString("trangthai").equals("1")) {%>
														<td align="center">Hoạt Động</td>
 
														<%} else if(RsTs.getString("trangthai").equals("0")){%>
 
														<td align="center">Ngưng Hoạt Động</td>
 
														<%} else if(RsTs.getString("trangthai").equals("2")){%>
 
														<td align="center">Đã thanh lý</td>
 
														<%}else{%>
														<td align="center">Hoàn tất</td>
														<%} %>
												 
														<td  align="left"><%=RsTs.getString("loaitaisan")%></td>
														<td style="display: none;" align="left"><%=RsTs.getString("nhomtaisan")%></td>
														<td align="left"><%=RsTs.getString("ttcp")%></td>
											<%} %>

														<td align="center">
															<table border=0 cellpadding="0" cellspacing="0">
																<tr>
														<% if(obj.isKhauhao(RsTs.getString("PK_SEQ"))){%>		
																	<td> 
																													
																	<%if(quyen[2]!=0){ %>
																	<a href="../../Erp_TaiSanCoDinhUpdateSvl?userid=<%=userId%>&update=<%=RsTs.getString("pk_seq")%>&phanloai=<%=RsTs.getString("phanloai")%>"><img
																			src="../images/Edit20.png" alt="Chinh sua" width="20" height="20" longdesc="Chinh sua" border=0></A>
																			<%} %>
																			</td>
																	<td>&nbsp;&nbsp;</td>
																	<td>
																	<%if(quyen[1]!=0){ 
																		if(RsTs.getString("trangthai").equals("0")){
																	%>  
																	
																	<a href=../../Erp_TaiSanCoDinhSvl?userid=<%=userId%>&delete=<%=RsTs.getString("pk_seq")%>><img src="../images/Delete20.png"
																			alt="Xoa" width="20" height="20" longdesc="Xoa" border=0
																			onclick="if(!confirm('Bạn muốn xóa tài sản này ?')) return false;"></a> 
																	<%   }
																	 } %>
																	</td>	
																	
																																		<td>
																	<%if(quyen[1]!=0){ 
																		if(RsTs.getString("trangthai").equals("1") & RsTs.getString("phanloai").equals("TSDD") ){
																	%>  
																	
																	<a href=../../Erp_TaiSanCoDinhSvl?userid=<%=userId%>&chuyen=<%=RsTs.getString("pk_seq")%>>
																	<img src="../images/convert.gif" alt="Chuyển thành tài sản" width="20" height="20" longdesc="Chuyển thành tài sản" border=0 ></a>
																			 
																	<%   }
																	 } %>
																			</td>
																	
														<%}else{ %>			
																	<td>
																	<%if(quyen[2]!=0){ %>
																	<a href="../../Erp_TaiSanCoDinhUpdateSvl?userid=<%=userId%>&display=<%=RsTs.getString("pk_seq")%>&phanloai=<%=RsTs.getString("phanloai")%>""><img
																			src="../images/Display20.png" alt="Hiển thị" width="20" height="20" longdesc="Hiển thị" border=0></A>
																			<%} %>
																			</td>
																	<td>&nbsp;&nbsp;</td>
																	<td>&nbsp;</td>
														<%} %>
																	
																</tr>
															</table>
													</tr>
													<%m++;
														}%>
													<tr class="tbfooter" >
														<td colspan="7" align="center" class="tbfooter">
															<% obj.setNextSplittings(); %>
															 <script type="text/javascript" src="../scripts/phanTrang.js"></script>
															<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>" >
															<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>" >
								
														 	<%if(obj.getNxtApprSplitting() >1) {%>
																<img alt="Trang Dau" src="../images/first.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, 1, 'view')"> &nbsp;
															<%}else {%>
																<img alt="Trang Dau" src="../images/first.gif" > &nbsp;
																<%} %>
															<% if(obj.getNxtApprSplitting() > 1){ %>
																<img alt="Trang Truoc" src="../images/prev.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, eval(document.forms[0].nxtApprSplitting.value) -1, 'view')"> &nbsp;
															<%}else{ %>
																<img alt="Trang Truoc" src="../images/prev_d.gif" > &nbsp;
															<%} %>
															
															<%
																int[] listPage = obj.getNextSplittings();
																for(int i = 0; i < listPage.length; i++){
															%>
															
															<% 
															
															System.out.println("Current page:" + obj.getNxtApprSplitting());
															System.out.println("List page:" + listPage[i]);
															
															if(listPage[i] == obj.getNxtApprSplitting()){ %>
															
																<a  style="color:white;"><%= listPage[i] %>/ <%=obj.getTheLastSplitting() %></a>
															<%}else{ %>
																<a href="javascript:View(document.forms[0].name, <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
															<%} %>
																<input type="hidden" name="list" value="<%= listPage[i] %>" />  &nbsp;
															<%} %>
															
															<% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
																&nbsp; <img alt="Trang Tiep" src="../images/next.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, eval(document.forms[0].nxtApprSplitting.value) +1, 'view')"> &nbsp;
															<%}else{ %>
																&nbsp; <img alt="Trang Tiep" src="../images/next_d.gif" > &nbsp;
															<%} %>
															<%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
														   		<img alt="Trang Cuoi" src="../images/last.gif" > &nbsp;
													   		<%}else{ %>
													   			<img alt="Trang Cuoi" src="../images/last.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, -1, 'view')"> &nbsp;
													   		<%} %>
									
														</td>
													</tr>
												</table>
											</td>
										</tr>
									</table>
								</fieldset>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</form>
	<script type="text/javascript" src="../scripts/loadingv2.js"></script>
	<%
	try{
		if(RsLts!=null){
			RsLts.close();
		}
		if(RsNts!=null){
			RsNts.close();
		}
		if(RsTs!=null){
			RsTs.close();
		}
		session.setAttribute("obj",null);
		obj.DBClose();
	}catch(Exception er){
		er.printStackTrace();		
	}
}
	%>
</body>
</html>

