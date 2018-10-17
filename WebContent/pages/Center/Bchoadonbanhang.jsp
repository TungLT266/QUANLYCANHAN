<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import = "java.util.Iterator" %>
<%@ page import = "java.util.List" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.sql.ResultSet" %>
<%@ page import = "geso.traphaco.center.beans.bchoadonbanhang.*" %>
<%@ page import = "geso.traphaco.center.util.*" %>

<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>

<% NumberFormat formatter = new DecimalFormat("#,###,###"); %>

<%	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
<%
	IBchoadonbanhangList obj =(IBchoadonbanhangList)session.getAttribute("obj");
	ResultSet bchoadonbanhangRs = obj.getBchoadonbanhangRs();
	ResultSet khohangRs = obj.getKhohangRs();
	ResultSet trinhduocvienRs = obj.getTrinhduocvienRs();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>Best - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
	<LINK rel="stylesheet" href="../css/main.css" type="text/css">
	<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
	<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
	<script type="text/javascript" language="JavaScript" src="../scripts/Numberformat.js"></script>
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">

	<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
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

	<SCRIPT language="javascript" type="text/javascript">
	function clearform()
	{
	document.forms["khtt"].tungay.value= "";
	document.forms["khtt"].denngay.value= "";
	document.forms["khtt"].makh.value= "";
	document.forms["khtt"].mafast.value= "";
	document.forms["khtt"].trangthai.value= "";
	document.forms["khtt"].khohang.value= "";
	document.forms["khtt"].trinhduocvien.value= "";
	document.forms["khtt"].submit();
	}

	function submitform()
	{
		document.forms["khtt"].action.value= "search";
		document.forms["khtt"].submit();
	}

	function newform()
	{
		document.forms["khtt"].action.value= "new";
		document.forms["khtt"].submit();
	}
	
	function xuatExcel()
	 {
	 	document.forms['khtt'].action.value= 'excel';
	 	document.forms['khtt'].submit();
	 	
	 }
	</SCRIPT>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="khtt" method="post" action="../../BchoadonbanhangSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	<TR>
		<TD colspan="4" align="left" valign="top" bgcolor="#FFFFFF">
			<TABLE width="100%" cellpadding="0" cellspacing="2">
				<TR>
					<TD align="left" class="tbnavigation">
						<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
							<TR height="22">
								<TD align="left" colspan="2" class="tbnavigation">&nbsp; Báo cáo quản trị > Doanh số > Báo cáo hóa đơn bán hàng  </TD>
								<TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %></TD>
							</TR>
						</TABLE>
					</TD>
				</TR>
			</TABLE>
			<TABLE width="100%" cellpadding="0" cellspacing="1">
				<TR>
					<TD>
						<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
							<TR>
								<TD width="100%" align="center" >
									<FIELDSET>
										<LEGEND class="legendtitle">&nbsp;Tiêu chí tìm kiếm &nbsp;</LEGEND>

										<TABLE  width="100%" cellpadding="6" cellspacing="0">

											<TR>
												<TD class="plainlabel" >Từ ngày </TD>
												<TD class="plainlabel">
													<input type="text"  class="days" size="11" id="tungay" name="tungay" value="<%= obj.getTungay() %>" maxlength="10" readonly  onChange = "submitform();" />
												</TD>
												<TD class="plainlabel" >Đến ngày </TD>
												<TD class="plainlabel">
													<input type="text"  class="days" size="11" id="denngay" name="denngay" value="<%= obj.getDenngay() %>" maxlength="10" readonly  onChange = "submitform();" />
												</TD>
											</TR>


											<TR>
												<TD class="plainlabel" >Mã khách hàng </TD>
												<TD class="plainlabel">
													<input  type="text" name="makh" value="<%= obj.getMakh() %>" size="20" onChange = "submitform();">
												</TD>
												<TD class="plainlabel" >Mã cũ </TD>
												<TD class="plainlabel">
													<input  type="text" name="mafast" value="<%= obj.getMafast() %>" size="20" onChange = "submitform();">
												</TD>
											</TR>
											<TR>
												<TD class="plainlabel" >Trình dược viên </TD>
												<TD class="plainlabel">
													<select name="trinhduocvien" onChange = "submitform();">
													<option value = "" > </option>
														<%
														if (trinhduocvienRs != null)
														{
															try{
															while (trinhduocvienRs.next())
															{ %>
																<% if(trinhduocvienRs.getString("pk_seq").equals(obj.getTrinhduocvien())){ %>
																	<option value ="<%= trinhduocvienRs.getString("pk_seq") %>" selected="selected"> <%= trinhduocvienRs.getString("ten") %></option>
																<%}else{ %>
																	<option value ="<%= trinhduocvienRs.getString("pk_seq") %>"> <%= trinhduocvienRs.getString("ten") %></option>
																<%} %>
															<% }}catch(Exception e){}}%>
													</select>
												</TD>
												<TD class="plainlabel" >Kho hàng </TD>
												<TD class="plainlabel">
													<select name="khohang"  onChange = "submitform();">
														
														<%if(obj.getKhohang().equals("100000")){ %>
														<option value = "" > </option>
														<option value = "100000" selected="selected"> Kho hàng bán</option>
														<option value = "100001" > Kho khuyến mãi </option>
														<%} else if(obj.getKhohang().equals("100001")){ %>
														<option value = "" > </option>
														<option value = "100000"> Kho hàng bán</option>
														<option value = "100001" selected="selected" > Kho khuyến mãi </option>
														<%} else{ %>
														<option value = "" > </option>
														<option value = "100000"> Kho hàng bán</option>
														<option value = "100001"  > Kho khuyến mãi </option>
														<%} %>
													</select>
												</TD>
											</TR>


											<TR>
												<TD class="plainlabel" >Trạng thái </TD>
												<TD class="plainlabel" colspan="3">
													<select name ="trangthai" onChange = "submitform();">
														<option  value = ""></option>
													<% if(obj.getTrangthai().equals("0")) {%>
															
															<option value="0" selected="selected">Chưa chốt</option>
															<option value="1" > Đã chốt</option>
															<option value="2" > Đã hủy</option>
															<option value="3" > Đã xuât kho </option>
															
													<%} else if (obj.getTrangthai().equals("1")){%>
															<option value="0" >Chưa chốt</option>
															<option value="1" selected="selected"> Đã chốt</option>
															<option value="2" > Đã hủy</option>
															<option value="3" > Đã xuât kho </option>
															
													<%} else if (obj.getTrangthai().equals("2")){%>
															<option value="0" >Chưa chốt</option>
															<option value="1" > Đã chốt</option>
															<option value="2" selected="selected" > Đã hủy</option>
															<option value="3" > Đã xuât kho </option>
													<%}else{%>
															<option value="0" >Chưa chốt</option>
															<option value="1" > Đã chốt</option>
															<option value="2" > Đã hủy</option>
															<option value="3" > Đã xuât kho </option>
													<%}%>
													</select>
												</TD>
												
											</TR>

											
											

											<tr class="plainlabel"> 
												<td colspan="4" >
													<a class="button2" href="javascript:clearform()">
														<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;
													<a class="button2" href="javascript:xuatExcel()"> <img style="top: -4px;" src="../images/button.png" alt="">Xuất Excel </a>
												</td>
											</tr>
										</TABLE>
									</FIELDSET>
								</TD>
							</TR>
						</TABLE>
					</TD>
				</TR>

				<TR>
					<TD width="100%">
						<FIELDSET>
							<LEGEND class="legendtitle">&nbsp; &nbsp;&nbsp;
								<!-- <a class="button3" href="javascript:newform()">
									<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a> -->
							</LEGEND>

							<TABLE class="" width="100%">
								<TR>
									<TD width="98%">
										<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
											<TR class="tbheader">
												<TH width="4%" 	align="center">STT</TH>
												<TH width="10%" align="center"> Ngày đơn hàng</TH>
												<TH width="7%" align="center"> Số đơn hàng </TH>
												<TH width="7%" align="center"> Mã KH(FAST) </TH>
												<TH width="27%" 	align="center"> Tên đơn vị </TH>
												<TH width="13%" align="center"> Tiền trước thuế</TH>
												<TH width="9%" 	align="center"> Tiền thuế </TH>
												<TH width="13%" align="center"> Tổng tiền(sau VAT)</TH>
											</TR>
											
				 							<% if(bchoadonbanhangRs!=null){
												try{
												int m = 0;
												String lightrow = "tblightrow";
												String darkrow = "tbdarkrow";
												while(bchoadonbanhangRs.next()){
											
												if (m % 2 != 0) {%>						
													<TR class= <%=lightrow%> >
												<%} else {%>
													<TR class= <%= darkrow%> >
												<%}%>
												<TD align="center"><%=bchoadonbanhangRs.getString("_no")%></TD>
												<TD align="center"><%=bchoadonbanhangRs.getString("NGAYNHAP")%></TD>
												<TD align="center"><%=bchoadonbanhangRs.getString("PK_SEQ")%></TD>
												<TD align="center"><%=bchoadonbanhangRs.getString("maFAST")%></TD>
												<TD align="left"><%=bchoadonbanhangRs.getString("TEN")%></TD>
												<%
													double doanhthu=bchoadonbanhangRs.getDouble("Tientruocthue");
													double vat =bchoadonbanhangRs.getDouble("VAT");
													double tongtien =bchoadonbanhangRs.getDouble("Tongtien");
												%>
												
												<TD align="center"><%= formatter.format(doanhthu) %></TD>
												<TD align="center"><%= formatter.format(vat) %></TD>
												<TD align="center"><%= formatter.format(tongtien) %></TD>
												
												
											</tr>
											<%m++;}}catch(Exception e){}} %> 
											
											 			
										<tr class="tbfooter" > 
											 <TD align="center" valign="middle" colspan="8" class="tbfooter">
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
											</TD>
										 </tr>  
										</TABLE>
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
</BODY>
</html>

<%
	try
	{
		if(bchoadonbanhangRs != null)
			bchoadonbanhangRs.close();

		if(obj != null)
		{
			obj.DbClose();
			obj = null;
		}
		session.setAttribute("obj", null);
	}
	catch (SQLException e) {} %>
<%}%>
