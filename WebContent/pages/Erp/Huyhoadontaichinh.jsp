<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import = "java.util.Iterator" %>
<%@ page import = "java.util.List" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.sql.ResultSet" %>
<%@ page import = "geso.traphaco.distributor.beans.hoadontaichinhNPP.*" %>
<%@ page import = "geso.traphaco.center.util.*" %>

<%	
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
<%
	IErpHuyhoadontaichinhNPPList obj =(IErpHuyhoadontaichinhNPPList)session.getAttribute("obj");
	ResultSet huyhoadontaichinhRs = obj.getHuyhoadontaichinhRs();
	ResultSet khRs = obj.getkhachhang();
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
	
	
	<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
	     $(document).ready(function() { $("#khachhangId").select2(); });
	     
	 </script>
	

	<SCRIPT language="javascript" type="text/javascript">
	function clearform()
	{
	document.forms["khtt"].submit();
	}

	function searchform()
	{
		document.forms["khtt"].action.value= "search";
		document.forms["khtt"].submit();
	}

	function newform()
	{
		document.forms["khtt"].action.value= "new";
		document.forms["khtt"].submit();
	}
	
	function clearform()
	{
		document.forms["khtt"].tungay.value= "";
		document.forms["khtt"].denngay.value= "";
		document.forms["khtt"].sochungtu.value= "";
		document.forms["khtt"].khachhang.value= "";
		document.forms["khtt"].trangthai.value= "";
		document.forms["khtt"].submit();
	}
	function processing(id,chuoi)
	 {
	    document.getElementById(id).innerHTML = "<a href='#'><img src='../images/waiting.gif' width = '20' height = '20' title='cho thuc hien..' border='0' longdesc='cho thuc hien..' style='border-style:outset'> Proc...</a>"; 		  
	 	document.getElementById(id).href=chuoi;
	 }
	
	</SCRIPT>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="khtt" method="post" action="../../HuyhoadontaichinhSvl">
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
								<TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý cung ứng > Quản lý bán hàng > Hủy hóa đơn tài chính </TD>
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
												<TD class="plainlabel" width="120px;"> Từ ngày </TD>
												<TD class="plainlabel" width="200px;">
													<input  type="text" class="days" name="tungay" value="<%= obj.gettungay() %>" onchange="searchform();" >
												</TD>
												
												<TD class="plainlabel " width="120px;" > Đến ngày </TD>
												<TD class="plainlabel">
													<input  type="text" class="days" name="denngay" value="<%= obj.getdenngay() %>" onchange="searchform();" >
												</TD>
											</TR>
											
											<TR>
												<TD class="plainlabel" width="120px;"> Số chứng từ </TD>
												<TD class="plainlabel" width="200px;">
													<input  type="text"  name="sochungtu" value="<%= obj.getsochungtu() %>" onchange="searchform();">
												</TD>
												
												<TD class="plainlabel " width="120px;" > Khách hàng </TD>
												<TD class="plainlabel">
													<select name=khachhang id="khachhangId" style="width: 200px;" onchange="searchform();">
														<option></option>
														<%try{
															
															while(khRs.next()){
																if(khRs.getString("PK_SEQ").equals(obj.getkhId())){
														%>			
															<option value="<%=khRs.getString("PK_SEQ") %>" selected="selected"><%=khRs.getString("MA") %> - <%=khRs.getString("TEN") %></option>	
														<% }else{%>
															<option value="<%=khRs.getString("PK_SEQ") %>"><%=khRs.getString("MA") %> - <%=khRs.getString("TEN") %></option>
														<%}}}catch(Exception e){ e.printStackTrace();} %>
													</select>
												</TD>
											</TR>
											
											
											<TR>
												<TD class="plainlabel" width="120px;">Trạng thái </TD>
												<TD class="plainlabel" width="200px;" colspan="3">
													<select name="trangthai" onchange="searchform();">
														<%if(obj.getTrangthai().equals("0")){ %>
														<option value=""></option>
														<option value="0" selected="selected"> Chưa chốt</option>
														<option value="1"> Đã chốt</option>
														<option value="2"> Đã xóa</option>
														<%} else if(obj.getTrangthai().equals("1")){%>
														<option value=""></option>
														<option value="0"> Chưa chốt</option>
														<option value="1" selected="selected"> Đã chốt</option>
														<option value="2"> Đã xóa</option>
														<%} else if(obj.getTrangthai().equals("2")){%>
														<option value=""></option>
														<option value="0"> Chưa chốt</option>
														<option value="1"> Đã chốt</option>
														<option value="2" selected="selected"> Đã xóa</option>
														<%} else {%>
														<option value="" selected="selected"></option>
														<option value="0"> Chưa chốt</option>
														<option value="1"> Đã chốt</option>
														<option value="2"> Đã xóa</option>
														<%} %>
														
													</select>
												</TD>
												
												
											</TR>
											
											

											<tr class="plainlabel" > 
												<td  colspan="4" >
													<a class="button2" href="javascript:clearform()">
														<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;
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
							<LEGEND class="legendtitle">&nbsp;Hủy hóa đơn tài chính &nbsp;&nbsp;
								<a class="button3" href="javascript:newform()">
									<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>
							</LEGEND>

							<TABLE class="" width="100%">
								<TR>
									<TD width="98%">
										<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
											<TR class="tbheader">
												<th width="10%" align = "center" > Số chứng từ </th>
												<th width="30%" align = "center" > Khách hàng </th>
												<th width="10%" align = "center" > Trạng thái </th>
												<th width="10%" align = "center" > Ngày tạo </th>
												<th width="10%" align = "center" > Người tạo </th>
												<th width="10%" align = "center" > Ngày sửa </th>
												<th width="10%" align = "center" > Người sửa </th>
												<th width="10%" align = "center" > Tác vụ </th>
											</TR>
											
											<% if(huyhoadontaichinhRs!=null){ 
												try{
												int m=0;
												while(huyhoadontaichinhRs.next()){
													
												
											
											if((m % 2 ) == 0) {%>
					                         	<TR class='tbdarkrow'>
					                        <%}else{ %>
					                          	<TR class='tblightrow'>
					                        <%} %>
												<td align="center"><%= huyhoadontaichinhRs.getString("HOADON_FK") %></td>
												<td align="left"><%= huyhoadontaichinhRs.getString("KHTEN") %></td>
												<% if(huyhoadontaichinhRs.getString("TRANGTHAI").equals("0")){ %>
												<td align="center"> Chưa chốt</td>
												<%}else if(huyhoadontaichinhRs.getString("TRANGTHAI").equals("1")){%>
												<td align="center"> Đã chốt </td>
												<%} else{ %>
													<td align="center"> Đã xóa </td>
												<%} %>
												
												<td align="center"><%= huyhoadontaichinhRs.getString("NGAYTAO") %></td>
												<td align="center"><%= huyhoadontaichinhRs.getString("NVTEN1") %></td>
												<td align="center"><%= huyhoadontaichinhRs.getString("NGAYSUA") %></td>
												<td align="center"><%= huyhoadontaichinhRs.getString("NVTEN2") %></td>
												<td align="center">
													<A href = "../../HuyhoadontaichinhUpdateSvl?userId=<%=userId%>&display=<%= huyhoadontaichinhRs.getString("PK_SEQ")  %>">
													<IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0></A>&nbsp;
													
													<% if(huyhoadontaichinhRs.getString("TRANGTHAI").equals("0")){ %>
					                                <A id='chotphieu<%=huyhoadontaichinhRs.getString("PK_SEQ")%>'
													      href=""><img
													      src="../images/Chot.png" alt="Chốt"
													      width="20" height="20" title="Chốt" 
													      border="0" onclick="if(!confirm('Bạn có muốn chốt hủy hóa đơn này?')) {return false ;}else{ processing('<%="chotphieu"+huyhoadontaichinhRs.getString("PK_SEQ")%>' , '../../HuyhoadontaichinhSvl?userId=<%=userId%>&chot=<%= huyhoadontaichinhRs.getString("PK_SEQ")%>');}"  >
										  			  </A>
					                                
					                                <%} %>
					                                
					                                
					                                <% if((huyhoadontaichinhRs.getString("TRANGTHAI").equals("0"))){ %>
					                               			 <A href = "../../HuyhoadontaichinhSvl?userId=<%=userId%>&delete=<%= huyhoadontaichinhRs.getString("PK_SEQ")  %>">
													<IMG src="../images/Delete20.png" alt="Xóa" title="Xóa" border=0></A>&nbsp;
					               
					                                  <%} %>
					                                
					                                </A>&nbsp;
												</td>
											</tr>
											<% m++;}}catch(Exception e){e.printStackTrace();}} %>
											
											<tr class="tbfooter" >
														<td colspan="8" align="center" class="tbfooter">
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
		if(huyhoadontaichinhRs != null)
			huyhoadontaichinhRs.close();

		if(obj != null)
		{
			obj.DbClose();
			obj = null;
		}
		session.setAttribute("obj", null);
	}
	catch (SQLException e) {} %>
<% }%>
