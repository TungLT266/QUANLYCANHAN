<%@page import="java.sql.SQLException"%>
<%@ page import="java.sql.ResultSet"%>
<%@page import="geso.traphaco.erp.beans.khachhang.*"%>
<%@page import="geso.traphaco.erp.beans.khachhang.imp.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/SGF/index.jsp");
	}else{ %>

<%
	IErpKhachHang khList = (IErpKhachHang) session.getAttribute("khList");
	ResultSet rsTaiKhoan = khList.getTaiKhoanRs();
	ResultSet rsNganHang = khList.getNganHangRs();
	ResultSet rsChiNhanh = khList.getChiNhanhRs();
	ResultSet rsKenh = khList.getKenhRs();
	ResultSet rsKH = khList.getKhachHang();
	ResultSet rsNhomKh=khList.getNhom_KH_Rs();	
	ResultSet rsdvkd =khList.getDvkdRs();
	
	int[] quyen = new  int[5];
	 quyen = util.Getquyen("40",userId);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Style-Type" content="text/css">
<title>Khách hàng</title>
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<link rel="stylesheet" href="../css/main.css" type="text/css">
<link rel="stylesheet" href="../css/calendar.css" type="text/css">
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<script language="javascript" type="text/javascript">

	function xoaform() {
		document.forms['khListForm'].ma.value = "";
		document.forms['khListForm'].ten.value = "";
		document.forms['khListForm'].taikhoan.value = "";
		document.forms['khListForm'].nhom_kh.value = "";
		document.forms['khListForm'].trangthai.value = "";
		document.forms['khListForm'].dvkdid.value = "";
		document.forms['khListForm'].masothue.value = "";
		document.forms['khListForm'].kenhbanhang.value = "";
		document.khListForm.submit();
	}
	function Submit() {
		document.forms['khListForm'].submit();
	}

	function xuatexcel()
	{
		document.forms['khListForm'].action.value= 'excel';
		document.forms['khListForm'].submit();
		document.forms['khListForm'].action.value= '';
	}
	
	function newform() {
		document.forms['khListForm'].action.value = 'New';
		document.forms['khListForm'].submit();
	}
	 function thongbao()
	 {
		 var tt = document.forms["khListForm"].msg.value;
	 	 if(tt.length>0)
	     	alert(document.forms["khListForm"].msg.value);
	 	}
</script>
</head>
<body leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="khListForm" method="post" action="../../ErpKhachHangSvl">
		<input type="hidden" name="userId" value='<%=userId%>'>
		 <input type="hidden" name="userTen" value='<%=userTen%>'> 
		 <input type="hidden" name="action" value='1'>
		 <input type="hidden" name="msg" value='<%= khList.getMsg() %>'>
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
										<td align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền &gt; Kinh doanh &gt; Khách hàng</td>
										<td align="right" colspan="2" class="tbnavigation">Chào mừng bạn <%=userTen%>&nbsp;
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
														<td class="plainlabel" width="150px">Mã khách hàng</td>
														<td class="plainlabel"><input type="text" name="ma" value="<%=khList.getMa()%>" onchange="Submit()" />
														<td class="plainlabel">Thuộc nhóm khách hàng</td>
														<TD class="plainlabel">
														<select name="nhom_kh" onchange="Submit()">
																<option value=""></option>
																									<%if (rsNhomKh != null)
													while (rsNhomKh.next()) {
														if (khList.getNhom_KH().equals(
															rsNhomKh.getString("PK_SEQ"))) {%>
																<option value="<%=rsNhomKh.getString("PK_SEQ")%>" selected="selected"><%=rsNhomKh.getString("Ten")%></option>
																<%} else {%>
																<option value="<%=rsNhomKh.getString("PK_SEQ")%>"><%=rsNhomKh.getString("Ten")%></option>
																<%}
				}%>
														</select></td>
													</tr>
													<tr>
														<td class="plainlabel">Tên khách hàng</td>
														<td class="plainlabel"><input type="text" name="ten" value="<%=khList.getTen()%>"  onchange="Submit()"/>
													<td class="plainlabel">Thuộc tài khoản</td>
														<TD class="plainlabel"><select name="taikhoan" onchange="Submit()">
																<option value=""></option>
																<%if (rsTaiKhoan != null)
				while (rsTaiKhoan.next()) {
					if (khList.getTaiKhoan().equals(
							rsTaiKhoan.getString("PK_SEQ"))) {%>
																<option value="<%=rsTaiKhoan.getString("PK_SEQ")%>" selected="selected"><%=rsTaiKhoan.getString("Ten")%></option>
																<%} else {%>
																<option value="<%=rsTaiKhoan.getString("PK_SEQ")%>"><%=rsTaiKhoan.getString("Ten")%></option>
																<%}
				}%>
														</select></td>
													</tr>
														<tr>
														<td class="plainlabel">Trạng thái</td> 
														<td class="plainlabel">
															<select name="trangthai" onchange="Submit()">
																<option value=""></option>
																<% if(khList.getTrangThai().equals("0")){ %>
																<option value="1"> Hoạt động </option>
																<option value="0" selected="selected">Ngừng hoạt động </option>
																<%}else if(khList.getTrangThai().equals("1")){ %>
																	<option value="1" selected="selected"> Hoạt động </option>
																<option value="0" >Ngừng hoạt động </option>
																<%} else{%>
																	<option value="1"  > Hoạt động </option>
																<option value="0" >Ngừng hoạt động </option>
																<%} %>
																</select>
														</td>
														<TD class="plainlabel"> Đơn vị kinh doanh</TD>
														<TD class="plainlabel"><select name="dvkdid" id="dvkdid" onchange="Submit()">
																<option value=""></option>
																<%if (rsdvkd != null)
																while (rsdvkd.next()) {
																	if (khList.getDvkdId().equals(
																			rsdvkd.getString("PK_SEQ"))) {%>
																<option value="<%=rsdvkd.getString("PK_SEQ")%>" selected="selected"><%=rsdvkd.getString("Ten")%></option>
																<%} else {%>
																<option value="<%=rsdvkd.getString("PK_SEQ")%>"><%=rsdvkd.getString("Ten")%></option>
																<%}
																}%>
														</select></td>
													</tr>
													
													
													<tr style="display:none">
														<td class="plainlabel"> Ngân hàng</td>
														<TD class="plainlabel"><select name="nganhang" onchange="Submit()" id="nganhang">
																<option value=""></option>
																<%if (rsNganHang != null)
				while (rsNganHang.next()) {
					if (khList.getNganHang().equals(
							rsNganHang.getString("PK_SEQ"))) {%>
																<option value="<%=rsNganHang.getString("PK_SEQ")%>" selected="selected"><%=rsNganHang.getString("Ten")%></option>
																<%} else {%>
																<option value="<%=rsNganHang.getString("PK_SEQ")%>"><%=rsNganHang.getString("Ten")%></option>
																<%}
				}%>
														</select></td>
													</tr>
													<TR  style="display:none">
														<TD class="plainlabel"> Chi nhánh</TD>
														<TD class="plainlabel"><select name="chinhanh" id="chinhanh" onchange="Submit()">
																<option value=""></option>
																<%if (rsChiNhanh != null)
																while (rsChiNhanh.next()) {
																	if (khList.getChiNhanh().equals(
																			rsChiNhanh.getString("PK_SEQ"))) {%>
																<option value="<%=rsChiNhanh.getString("PK_SEQ")%>" selected="selected"><%=rsChiNhanh.getString("Ten")%></option>
																<%} else {%>
																<option value="<%=rsChiNhanh.getString("PK_SEQ")%>"><%=rsChiNhanh.getString("Ten")%></option>
																<%}
				}%>
														</select></td>
													</tr>
													<TR>
														<td class="plainlabel">Kênh bán hàng</td>
										         <td class="plainlabel" >
											          <select name="kenhbanhang" id= "kenhbanhang"  onchange="Submit()">
														 <option value=""></option>
														  <% if(rsKenh != null){
															  try{ while(rsKenh.next()){ 				
												    			if(rsKenh.getString("pk_seq").trim().equals(khList.getKenh())){ %>
												      				<option value='<%=rsKenh.getString("pk_seq")%>' selected><%=rsKenh.getString("ten") %></option>
												      			<%}else{ %>
												     				<option value='<%=rsKenh.getString("pk_seq")%>'><%=rsKenh.getString("ten") %></option>
												     			<%}}}catch(java.sql.SQLException e){}} %>	
														</select> 
										          </td>
										          
										          <td class="plainlabel"> Mã số thuế</td>
										           <td class="plainlabel">
										           		<input type="text" name="masothue" value="<%= khList.get_masothue()%>" onchange="Submit()">
										           </td>
												</tr>
													
												
													
													
													<tr>
														<td colspan="4" class="plainlabel">
														<a class="button2" href="javascript:xoaform()"> <img style="top: -4px;" src="../images/button.png" alt="">Nhập lại
														</a>
														<a class="button2" href="javascript:xuatexcel()"> <img style="top: -4px;" src="../images/button.png" alt="">Xuất Excel </a>
														</td>
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
									<legend class="legendtitle">Khách hàng &nbsp;&nbsp;&nbsp;
									
								<%if(quyen[0]!=0){ %>
										<a class="button3" href="javascript:newform()"> <img style="top: -4px;" src="../images/New.png" alt="">Tạo mới
										</a>	
										<%} %>
									</legend>
									<table width="100%" border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td align="left" colspan="4">
												<table width="100%" border="0" cellspacing="1" cellpadding="4">
													<tr class="tbheader">
														<th width="9%" align="center">Mã</th>
														<th width="15%" align="center">Tên </th>
														<th width="15%" align="center">Nhóm </th>
														<th width="15%" align="center">Tài khoản</th>
														<th width="15%" align="center" style="display: none">Giám sát</th>
														<th width="15%" align="center">Trạng thái</th>
														<th width="7%" align="center">&nbsp;Tác vụ</th>
													</tr>
													<%int m = 0;
			String lightrow = "tblightrow";
			String darkrow = "tbdarkrow";
			if (rsKH != null)
				while (rsKH.next()) {
					String trangthai = rsKH.getString("TrangThai");
					if (trangthai.equals("0"))
						trangthai = "Ngưng hoạt động";
					else
						trangthai = "Hoạt động";
					if (m % 2 != 0) {%>
													<tr class=<%=lightrow%>>
														<%} else {%>
													
													<tr class=<%=darkrow%>>
														<%}%>
														<td align="center"><%=rsKH.getString("Ma")%></td>
														<td align="left"><%=rsKH.getString("Ten")%></td>
														<td align="center"><%=rsKH.getString("NhomKhachHang")%></td>
														<td align="center"><%=rsKH.getString("TaiKhoan")%></td>
														<td align="center" style="display: none"><%=rsKH.getString("GiamSat")%></td>
														<td align="center"><%=trangthai%></td>
														<td align="center">
															<table border=0 cellpadding="0" cellspacing="0">
																<tr>
																	<td>  
																	<%if(quyen[2]!=0){ %>
																	<a href="../../ErpKhachHangUpdateSvl?userid=<%=userId%>&update=<%=rsKH.getString("PK_SEQ")%>"><img
																		title="Cập nhật" src="../images/Edit20.png" alt="Chinh sua" width="20" height="20" longdesc="Chinh sua" border=0></A>&nbsp;
																	<%} %>
																	</td>
																	
																	<td> 
																	<%if(quyen[1]!=0){ %>
																	<a href="../../ErpKhachHangSvl?userid=<%=userId%>&delete=<%=rsKH.getString("PK_SEQ")%>"><img
																		title="Xóa" src="../images/Delete20.png" alt="Xoa" width="20" height="20" longdesc="Xoa" border=0
																		onclick="if(!confirm('Bạn muốn xóa khách hàng này?')) return false;"> </a>&nbsp;
																	<%} %>
																	</td>
																	
																	<td>  
																	<!-- QUYEN VIEW DLN -->
																	<a href="../../ErpKhachHangUpdateSvl?userid=<%=userId%>&display=<%=rsKH.getString("PK_SEQ")%>"><img src="../images/Display20.png" title="Hiển thị" alt="Hien thi" width="20" height="20" longdesc="Hien thi" border = 0></A>
																	<!-- END QUYEN VIEW DLN --> 
																	</td>
																</tr>
															</table>
													</tr>
													<%m++;
				}%>
													 <tr class="tbfooter">
								<TD align="center" valign="middle" colspan="13" class="tbfooter">
									<% khList.setNextSplittings(); %> <script type="text/javascript"
										src="../scripts/phanTrang.js"></script> <input type="hidden"
									name="crrApprSplitting"
									value="<%= khList.getCrrApprSplitting() %>"> <input
									type="hidden" name="nxtApprSplitting"
									value="<%= khList.getNxtApprSplitting() %>"> <%if(khList.getNxtApprSplitting() >1) {%>
									<img alt="Trang Dau" src="../images/first.gif"
									style="cursor: pointer;"
									onclick="View(document.forms[0].name, 1, 'view')"> &nbsp;
									<%}else {%> <img alt="Trang Dau" src="../images/first.gif">
									&nbsp; <%} %> <% if(khList.getNxtApprSplitting() > 1){ %> <img
									alt="Trang Truoc" src="../images/prev.gif"
									style="cursor: pointer;"
									onclick="View(document.forms[0].name, eval(document.forms[0].nxtApprSplitting.value) -1, 'view')">
									&nbsp; <%}else{ %> <img alt="Trang Truoc"
									src="../images/prev_d.gif"> &nbsp; <%} %> <%
													int[] listPage = khList.getNextSplittings();
													for(int i = 0; i < listPage.length; i++){
												%> <% 
												
												System.out.println("Current page:" + khList.getNxtApprSplitting());
												System.out.println("List page:" + listPage[i]);
												
												if(listPage[i] == khList.getNxtApprSplitting()){ %> <a
									style="color: white;"><%= listPage[i] %>/ <%=khList.getTheLastSplitting() %></a>
									<%}else{ %> <a
									href="javascript:View(document.forms[0].name, <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
									<%} %> <input type="hidden" name="list"
									value="<%= listPage[i] %>" /> &nbsp; <%} %> <% if(khList.getNxtApprSplitting() < khList.getTheLastSplitting()){ %>
									&nbsp; <img alt="Trang Tiep" src="../images/next.gif"
									style="cursor: pointer;"
									onclick="View(document.forms[0].name, eval(document.forms[0].nxtApprSplitting.value) +1, 'view')">
									&nbsp; <%}else{ %> &nbsp; <img alt="Trang Tiep"
									src="../images/next_d.gif"> &nbsp; <%} %> <%if(khList.getNxtApprSplitting() == khList.getTheLastSplitting()) {%>
									<img alt="Trang Cuoi" src="../images/last.gif"> &nbsp; <%}else{ %>
									<img alt="Trang Cuoi" src="../images/last.gif"
									style="cursor: pointer;"
									onclick="View(document.forms[0].name, -1, 'view')">
									&nbsp; <%} %>
								</TD>
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
<%
	try{
	if(rsTaiKhoan != null) rsTaiKhoan.close();
	if(rsNganHang != null) rsNganHang.close();
	if(rsChiNhanh != null) rsChiNhanh.close();
	if(rsKenh != null) rsKenh.close();
	if(rsKH != null) rsKH.close();
	if(rsNhomKh != null) rsNhomKh.close();
	}catch(Exception er){
		
	}
	finally{
		khList.close();
	}
	}%>
</body>
</html>