<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.sql.SQLException"%>
<%@ page import="java.sql.ResultSet"%>
<%@page import="geso.traphaco.erp.beans.doituongkhac.*"%>
<%@page import="geso.traphaco.erp.beans.doituongkhac.imp.*"%>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@ page import="geso.traphaco.center.util.Erp_Item"%>
<%@ page import="geso.traphaco.center.util.Erp_ListItem"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{
	IErpDoiTuongKhacList obj = (IErpDoiTuongKhacList) session.getAttribute("obj");
	List<Erp_Item> nppList = obj.getNppList();
	List<Erp_Item> chiNhanhList = obj.getChiNhanhList();
	List<Erp_Item> nganHangList = obj.getNganHangList();
	List<Erp_ListItem> viewList = obj.getViewList();

	 int[] quyen = new  int[5];
	 quyen = util.Getquyen("ErpDoiTuongKhacSvl","",userId);
	 System.out.println();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Style-Type" content="text/css">
<title>Đối tượng khác</title>
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
		//document.objForm.congty.value = "";
		document.objForm.loaiNCC.value = "";
		document.objForm.taikhoan.value = "";
		document.objForm.nganhang.value = "";
		document.objForm.chinhanh.value = "";
		document.objForm.tenNCC.value = "";
		document.objForm.submit();
	}
	function Submit() {
		document.forms['objForm'].submit();
	}
	function xuatexcel()
	{
		document.forms['objForm'].action.value= 'excel';
		document.forms['objForm'].submit();
		document.forms['objForm'].action.value= '';
	}

	function newform() {
		document.forms['objForm'].action.value = 'new';
		document.forms['objForm'].submit();
	}
	
	function thongbao()
	{
		if(document.getElementById("msg").value != '' && document.getElementById("msg").value!=null)
		{
			alert(document.getElementById("msg").value);
		}
	}
	
	</script>
</head>
<body leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="objForm" method="post" action="../../ErpDoiTuongKhacSvl">
	
		<input type="hidden" name="userId" value='<%=userId%>'/> 
		<input type="hidden" name="userTen" value='<%=userTen%>'/> 
		<input type="hidden" name="action" value='1'/>
		 <input type="hidden" id="msg" value="<%=obj.getMsg() %>"/>
		 
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
										<td align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền &gt; Kế toán &gt; Đối tượng khác</td>
										<td align="right" colspan="2" class="tbnavigation">Chào mừng bạn <%=userTen%>&nbsp;
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td>
							<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td width="100%" align="center">
											<fieldset>
												<legend class="legendtitle">Tiêu chí tìm kiếm &nbsp;</legend>
												<TABLE class="tblight" width="100%" cellpadding="6" cellspacing = "0">
													<tr>
														<td class="plainlabel" width="120px" style="display:none">Chi nhánh / HO</td>
														<TD class="plainlabel" width = "220px"  style="display:none">
															<select name="nppId" id="nppId" onchange="Submit()">
																<option value=""></option>
																<% if (obj.getNppList() != null)
																{
																	for (Erp_Item item : obj.getNppList())
																	{
																		if (item.getValue().equals(obj.getNppId()))
																		{
																		%>
																			<option value="<%=item.getValue()%>" selected="selected"><%=item.getName()%></option>
																		<% } else { %>
																			<option value="<%=item.getValue()%>"><%=item.getName()%></option>
																<% } }} %>
															</select>
														</td>
													
														<td class="plainlabel" width="170px" >Ngân hàng</td>
														<TD class="plainlabel" colspan="3">
															<select name="nganHangId" id="nganHangId" onchange="Submit()" style="width: 400px;">
																<option value=""></option>
																<% if (obj.getNganHangList() != null)
																{
																	for (Erp_Item item : obj.getNganHangList())
																	{
																		if (item.getValue().equals(obj.getNganHangId()))
																		{
																		%>
																			<option value="<%=item.getValue()%>" selected="selected"><%=item.getName()%></option>
																		<% } else { %>
																			<option value="<%=item.getValue()%>"><%=item.getName()%></option>
																<% } }} %>
															</select>
														</td>
													</tr>
													<tr>
														<td class="plainlabel"  >Thuộc Chi nhánh</td>
														<TD class="plainlabel"  colspan="3">
															<select name="chiNhanhId" id="chiNhanhId" onchange="Submit()" id="nganhang" style="width: 400px;">
																<option value=""></option>
																<% if (obj.getChiNhanhList() != null)
																{
																	for (Erp_Item item : obj.getChiNhanhList())
																	{
																		if (item.getValue().equals(obj.getChiNhanhId()))
																		{
																		%>
																			<option value="<%=item.getValue()%>" selected="selected"><%=item.getName()%></option>
																		<% } else { %>
																			<option value="<%=item.getValue()%>"><%=item.getName()%></option>
																<% } }} %>
															</select>
														</td>
													</tr>
													
													<tr>
														<td class="plainlabel" >Mã / Tên Đối tượng khác</td>
														<td class="plainlabel" colspan="3" >
															<input type="text" name="maTenDoiTuong" id="maTenDoiTuong" value="<%= obj.getMaTenDoiTuong() %>" style="width: 620px;" onchange="Submit();"  >
														</td>
													</tr>
<!-- 													<tr> -->
<!-- 														<td class="plainlabel">Số items -->
<!-- 								                        </td> -->
<!-- 								                        <td class="plainlabel" colspan=3> -->
<%-- 															<input type="text" name="soItems" value="<%= obj.getSoItems() %>" onchange="Submit();"> --%>
<!-- 								                        </td> -->
<!-- 													</tr> -->
													
													<tr>
														<td colspan="4" class="plainlabel">
															<a class="button2" href="javascript:xoaform()"> <img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>
															&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
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
									<legend class="legendtitle">Đối tượng khác&nbsp;&nbsp;&nbsp;
									
										<%if( quyen[0] != 0 ){ %>
										<a class="button3" href="javascript:newform()"> <img style="top: -4px;" src="../images/New.png" alt="">Tạo mới
										</a>
										<%} %>
									</legend>
									<table width="100%" border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td align="left" colspan="4">
												<table width="100%" border="0" cellspacing="1" cellpadding="4">
													<tr class="tbheader">

														<th width="5%" align="center">STT</th>
														<th width="5%" align="center">Mã đối tượng</th>
														<th width="20%" align="center">Tên đối tượng</th>
														
														<th width="10%" align="center">Trạng thái</th>
														
														<th width="10%" align="center">Người tạo</th>
														<th width="10%"  align="center">Ngày tạo</th>
														
														<th width="10%"  align="center">Người sửa</th>
														<th width="10%"  align="center">Ngày sửa</th>
														
														<th width="20%" align="center">&nbsp;Tác vụ</th>
													</tr>
													<%
			String lightrow = "tblightrow";
			String darkrow = "tbdarkrow";
			if (viewList != null)
				for (int i = 0; i < viewList.size(); i++) {
					Erp_ListItem item = viewList.get(i);
					String trangThai = item.getTrangThai();
					if (trangThai.equals("0"))
						trangThai = "Ngưng hoạt động";
					else
						trangThai = "Hoạt động";
					if (i % 2 != 0) {%>
													<tr class=<%=lightrow%>>
														<%} else {%>
													
													<tr class=<%=darkrow%>>
														<%}%>
														<td align="center"><%= i + 1%></td>
														<td align="left"><%=item.getMa()%></td>
														
														<td align="center"><%= item.getTen()%></td>
														<td align="center"><%=trangThai%></td>
														
														<td align="center"><%=item.getNguoiTao()%></td>
														<td align="center"><%=item.getNgayTao()%></td>
														
														<td align="center"><%=item.getNguoitSua()%></td>
														<td align="center"><%=item.getNgaySua()%></td>
														
														<td align="center">
															<table border=0 cellpadding="0" cellspacing="0">
																<tr>
																	<td> 
																		<%if(quyen[2]!=0){ %>
																	<a href="../../ErpDoiTuongKhacUpdateSvl?userid=<%=userId%>&update=<%=item.getId()%>"><img
																			title="Cập nhật" src="../images/Edit20.png" alt="Chinh sua" width="20" height="20" longdesc="Chinh sua" border=0></A>&nbsp; 
																			<%} %>
																	</td>
																	
																	<td> 
																	<%	{ %>
																	<a href="../../ErpDoiTuongKhacSvl?userid=<%=userId%>&delete=<%=item.getId()%>"><img
																			title="Xóa" src="../images/Delete20.png" alt="Xoa" width="20" height="20" longdesc="Xoa" border=0
																			onclick="if(!confirm('Bạn muốn xóa đối tượng này?')) return false;"> </a>&nbsp;  
																			<%} %>
																	</td>
																	<!-- QUYEN VIEW DLN --> 
																	<td> 
																	<a href="../../ErpDoiTuongKhacUpdateSvl?userid=<%=userId%>&display=<%=item.getId()%>"><img src="../images/Display20.png" title="Hiển thị" alt="Hien thi" width="20" height="20" longdesc="Hien thi" border = 0></A> 
																	</td>
																	<!-- END QUYEN VIEW DLN -->
																</tr>
															</table>
													</tr>
													<%
				}%>
													<tr class="tbfooter">
								<TD align="center" valign="middle" colspan="13" class="tbfooter">
									<% obj.setNextSplittings(); %> <script type="text/javascript"
										src="../scripts/phanTrang.js"></script> <input type="hidden"
									name="crrApprSplitting"
									value="<%= obj.getCrrApprSplitting() %>"> <input
									type="hidden" name="nxtApprSplitting"
									value="<%= obj.getNxtApprSplitting() %>"> <%if(obj.getNxtApprSplitting() >1) {%>
									<img alt="Trang Dau" src="../images/first.gif"
									style="cursor: pointer;"
									onclick="View(document.forms[0].name, 1, 'view')"> &nbsp;
									<%}else {%> <img alt="Trang Dau" src="../images/first.gif">
									&nbsp; <%} %> <% if(obj.getNxtApprSplitting() > 1){ %> <img
									alt="Trang Truoc" src="../images/prev.gif"
									style="cursor: pointer;"
									onclick="View(document.forms[0].name, eval(document.forms[0].nxtApprSplitting.value) -1, 'view')">
									&nbsp; <%}else{ %> <img alt="Trang Truoc"
									src="../images/prev_d.gif"> &nbsp; <%} %> <%
 													int[] listPage = obj.getNextSplittings();
 													for(int i = 0; i < listPage.length; i++){
 												%> <% 
												
												System.out.println("Current page:" + obj.getNxtApprSplitting());
												System.out.println("List page:" + listPage[i]);
												
												if(listPage[i] == obj.getNxtApprSplitting()){ %> <a
									style="color: white;"><%= listPage[i] %>/ <%=obj.getTheLastSplitting() %></a>
									<%}else{ %> <a
									href="javascript:View(document.forms[0].name, <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
									<%} %> <input type="hidden" name="list"
									value="<%= listPage[i] %>" /> &nbsp; <%} %> <% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
									&nbsp; <img alt="Trang Tiep" src="../images/next.gif"
									style="cursor: pointer;"
									onclick="View(document.forms[0].name, eval(document.forms[0].nxtApprSplitting.value) +1, 'view')">
									&nbsp; <%}else{ %> &nbsp; <img alt="Trang Tiep"
									src="../images/next_d.gif"> &nbsp; <%} %> <%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
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
	
</body>
</html>
<%}%>