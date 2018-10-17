<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.sql.SQLException"%>
<%@ page import="java.sql.ResultSet"%>
<%@page import="geso.traphaco.erp.beans.nhacungcap.*"%>
<%@page import="geso.traphaco.erp.beans.nhacungcap.imp.*"%>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<%
	IErpNhaCungCap nccList = (IErpNhaCungCap) session.getAttribute("nccList");
	ResultSet rsCongTy = nccList.getCongTyRs();
	ResultSet rsLoaiNCC = nccList.getLoaiNCCRs();
	ResultSet rsTaiKhoan = nccList.getTaiKhoanRs();
	ResultSet rsNganHang = nccList.getNganHangRs();
	ResultSet rsChiNhanh = nccList.getChiNhanhRs();
	ResultSet rsNCC = nccList.getNhaCungCapRs();
	 int[] quyen = new  int[5];
	 quyen = util.Getquyen("ErpNhaCungCapSvl","",userId);
	 System.out.println();

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Style-Type" content="text/css">
<title>Nhà cung cấp</title>
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
		//document.nccListForm.congty.value = "";
		document.nccListForm.loaiNCC.value = "";
		document.nccListForm.taikhoan.value = "";
		document.nccListForm.nganhang.value = "";
		document.nccListForm.chinhanh.value = "";
		document.nccListForm.tenNCC.value = "";
		document.nccListForm.submit();
	}
	function Submit() {
		document.forms['nccListForm'].submit();
	}
	function xuatexcel()
	{
		document.forms['nccListForm'].action.value= 'excel';
		document.forms['nccListForm'].submit();
		document.forms['nccListForm'].action.value= '';
	}

	function newform() {
		document.forms['nccListForm'].action.value = 'New';
		document.forms['nccListForm'].submit();
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
	<form name="nccListForm" method="post" action="../../ErpNhaCungCapSvl">
	
		<input type="hidden" name="userId" value='<%=userId%>'/> 
		<input type="hidden" name="userTen" value='<%=userTen%>'/> 
		<input type="hidden" name="action" value='1'/>
		 <input type="hidden" id="msg" value="<%=nccList.getMsg() %>"/>
		 <input type="hidden" name="chixem" value='<%= nccList.getChixem() %>'>
		 
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
										<td align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền &gt; Mua hàng &gt; Nhà cung cấp</td>
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
														<td class="plainlabel" width="120px" >Loại nhà cung cấp</td>
														<TD class="plainlabel" width = "220px" ><select name="loaiNCC" onchange="Submit()">
																<option value=""></option>
																<%if (rsLoaiNCC != null)
																		while (rsLoaiNCC.next()) {
																			if (nccList.getLoaiNCC().equals(
																					rsLoaiNCC.getString("PK_SEQ"))) {%>
																<option value="<%=rsLoaiNCC.getString("PK_SEQ")%>" selected="selected"><%=rsLoaiNCC.getString("Ten")%></option>
																<%} else {%>
																<option value="<%=rsLoaiNCC.getString("PK_SEQ")%>"><%=rsLoaiNCC.getString("Ten")%></option>
																<%}
				}%>
														</select></td>
													
														<td class="plainlabel" width="170px" >Tài Khoản ghi nhận công nợ</td>
														<TD class="plainlabel"  ><select name="taikhoan" onchange="Submit()">
																<option value=""></option>
																<%if (rsTaiKhoan != null)
				while (rsTaiKhoan.next()) {
					if (nccList.getTaiKhoan().equals(
							rsTaiKhoan.getString("PK_SEQ"))) {%>
																<option value="<%=rsTaiKhoan.getString("PK_SEQ")%>" selected="selected"><%=rsTaiKhoan.getString("Ten")%></option>
																<%} else {%>
																<option value="<%=rsTaiKhoan.getString("PK_SEQ")%>"><%=rsTaiKhoan.getString("Ten")%></option>
																<%}
				}%>
														</select></td>
													</tr>
													<tr>
														<td class="plainlabel"  >Thuộc Ngân hàng</td>
														<TD class="plainlabel"  ><select name="nganhang" onchange="Submit()" id="nganhang">
																<option value=""></option>
																<%if (rsNganHang != null)
				while (rsNganHang.next()) {
					if (nccList.getNganHang().equals(
							rsNganHang.getString("PK_SEQ"))) {%>
																<option value="<%=rsNganHang.getString("PK_SEQ")%>" selected="selected"><%=rsNganHang.getString("Ten")%></option>
																<%} else {%>
																<option value="<%=rsNganHang.getString("PK_SEQ")%>"><%=rsNganHang.getString("Ten")%></option>
																<%}
				}%>
														</select></td>
													
														<TD class="plainlabel"  >Thuộc chi nhánh</TD>
														<TD class="plainlabel"  ><select name="chinhanh" id="chinhanh" onchange="Submit()">
																<option value=""></option>
																<%if (rsChiNhanh != null)
				while (rsChiNhanh.next()) {
					if (nccList.getChiNhanh().equals(
							rsChiNhanh.getString("PK_SEQ"))) {%>
																<option value="<%=rsChiNhanh.getString("PK_SEQ")%>" selected="selected"><%=rsChiNhanh.getString("Ten")%></option>
																<%} else {%>
																<option value="<%=rsChiNhanh.getString("PK_SEQ")%>"><%=rsChiNhanh.getString("Ten")%></option>
																<%}
				}%>
														</select></td>
													</tr>
													
													<tr>
														<td class="plainlabel" >Mã / Tên NCC</td>
														<td class="plainlabel" colspan="3" >
															<input type="text" name="tenNCC" value="<%= nccList.getTen() %>" style="width: 620px;" onchange="Submit();"  >
														</td>
													</tr>
													<tr>
														<td class="plainlabel">Số items
								                        </td>
								                        <td class="plainlabel" colspan=3>
															<input type="text" name="soItems" value="<%= nccList.getSoItems() %>" onchange="Submit();">
								                        </td>
													</tr>
													
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
									<legend class="legendtitle">Nhà cung cấp&nbsp;&nbsp;&nbsp;
									
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
														<th width="5%" align="center">Mã NCC</th>
														<th  align="center">Tên Nhà Cung Cấp</th>
														
														<th width="7%" align="center">Loại </th>
														<th width="20%" align="center">TK ghi nhận công nợ</th>
														<th width="7%" align="center">Trạng thái</th>
														
														<th width="10%" align="center">Người tạo</th>
														<th width="5%"  align="center">Ngày tạo</th>
														
														<th width="10%"  align="center">Người sửa</th>
														<th width="5%"  align="center">Ngày sửa</th>
														
														<th width="11%" align="center">&nbsp;Tác vụ</th>
													</tr>
													<%int m = 0;
			String lightrow = "tblightrow";
			String darkrow = "tbdarkrow";
			if (rsNCC != null)
				while (rsNCC.next()) {
					String trangthai = rsNCC.getString("TrangThai");
					if (trangthai.equals("0"))
						trangthai = "Ngưng hoạt động";
					else
						trangthai = "Hoạt động";
					if (m % 2 != 0) {%>
													<tr class=<%=lightrow%>>
														<%} else {%>
													
													<tr class=<%=darkrow%>>
														<%}%>
														<td align="center"><%=rsNCC.getString("Ma")%></td>
														<td align="left"><%=rsNCC.getString("Ten")%></td>
														
														<td align="center"><%= nccList.getLoaiNCC(rsNCC.getString("Loai_NCC"))%></td>
														<td align="left"><%=rsNCC.getString("TaiKhoan")%></td>
														<td align="center"><%=trangthai%></td>
														
														<td align="center"><%=rsNCC.getString("nguoitao")%></td>
														<td align="center"><%=rsNCC.getString("ngaytao")%></td>
														
														<td align="center"><%=rsNCC.getString("nguoisua")%></td>
														<td align="center"><%=rsNCC.getString("ngaysua")%></td>
														
														
														<td align="center">
															<table border=0 cellpadding="0" cellspacing="0">
																<tr>
																	<td> 
																		<%if(quyen[2]!=0){ %>
																	<a href="../../ErpNhaCungCapUpdateSvl?userid=<%=userId%>&update=<%=rsNCC.getString("PK_SEQ")%>"><img
																			title="Cập nhật" src="../images/Edit20.png" alt="Chinh sua" width="20" height="20" longdesc="Chinh sua" border=0></A>&nbsp; 
																			<%} %>
																	</td>
																	<td>
																	<%if(quyen[4]!=0){
																		if(rsNCC.getString("duyet").equals("0")){%>
																	<a href="../../ErpNhaCungCapSvl?userid=<%=userId%>&duyet=<%=rsNCC.getString("PK_SEQ")%>"><img
																			title="Duyệt" src="../images/Chot.png" alt="Duyet" width="20" height="20" longdesc="Xoa" border=0
																			onclick="if(!confirm('Bạn muốn duyệt nhà cung cấp này?')) return false;"> </a>&nbsp;  
																		<%}else{ %>
																	<a href="../../ErpNhaCungCapSvl?userid=<%=userId%>&boduyet=<%=rsNCC.getString("PK_SEQ")%>"><img
																			title="Bỏ duyệt" src="../images/unChot.png" alt="BoDuyet" width="20" height="20" longdesc="Xoa" border=0
																			onclick="if(!confirm('Bạn muốn bỏ duyệt nhà cung cấp này?')) return false;"> </a>&nbsp;		
																		<%	} 
																			}%>
																	</td>
																	<td> 
																	<%	if(quyen[1] != 0){ %>
																	<a href="../../ErpNhaCungCapSvl?userid=<%=userId%>&delete=<%=rsNCC.getString("PK_SEQ")%>"><img
																			title="Xóa" src="../images/Delete20.png" alt="Xoa" width="20" height="20" longdesc="Xoa" border=0
																			onclick="if(!confirm('Bạn muốn xóa hoạt động nhà cung cấp này?')) return false;"> </a>&nbsp;  
																			<%} %>
																	</td>
																	<!-- QUYEN VIEW DLN --> 
																	<td> 
																	<a href="../../ErpNhaCungCapUpdateSvl?userid=<%=userId%>&display=<%=rsNCC.getString("PK_SEQ")%>"><img src="../images/Display20.png" title="Hiển thị" alt="Hien thi" width="20" height="20" longdesc="Hien thi" border = 0></A> 
																	</td>
																	<!-- END QUYEN VIEW DLN -->
																</tr>
															</table>
													</tr>
													<%m++;
				}%>
													<tr class="tbfooter">
								<TD align="center" valign="middle" colspan="13" class="tbfooter">
									<% nccList.setNextSplittings(); %> <script type="text/javascript"
										src="../scripts/phanTrang.js"></script> <input type="hidden"
									name="crrApprSplitting"
									value="<%= nccList.getCrrApprSplitting() %>"> <input
									type="hidden" name="nxtApprSplitting"
									value="<%= nccList.getNxtApprSplitting() %>"> <%if(nccList.getNxtApprSplitting() >1) {%>
									<img alt="Trang Dau" src="../images/first.gif"
									style="cursor: pointer;"
									onclick="View(document.forms[0].name, 1, 'view')"> &nbsp;
									<%}else {%> <img alt="Trang Dau" src="../images/first.gif">
									&nbsp; <%} %> <% if(nccList.getNxtApprSplitting() > 1){ %> <img
									alt="Trang Truoc" src="../images/prev.gif"
									style="cursor: pointer;"
									onclick="View(document.forms[0].name, eval(document.forms[0].nxtApprSplitting.value) -1, 'view')">
									&nbsp; <%}else{ %> <img alt="Trang Truoc"
									src="../images/prev_d.gif"> &nbsp; <%} %> <%
													int[] listPage = nccList.getNextSplittings();
													for(int i = 0; i < listPage.length; i++){
												%> <% 
												
												System.out.println("Current page:" + nccList.getNxtApprSplitting());
												System.out.println("List page:" + listPage[i]);
												
												if(listPage[i] == nccList.getNxtApprSplitting()){ %> <a
									style="color: white;"><%= listPage[i] %>/ <%=nccList.getTheLastSplitting() %></a>
									<%}else{ %> <a
									href="javascript:View(document.forms[0].name, <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
									<%} %> <input type="hidden" name="list"
									value="<%= listPage[i] %>" /> &nbsp; <%} %> <% if(nccList.getNxtApprSplitting() < nccList.getTheLastSplitting()){ %>
									&nbsp; <img alt="Trang Tiep" src="../images/next.gif"
									style="cursor: pointer;"
									onclick="View(document.forms[0].name, eval(document.forms[0].nxtApprSplitting.value) +1, 'view')">
									&nbsp; <%}else{ %> &nbsp; <img alt="Trang Tiep"
									src="../images/next_d.gif"> &nbsp; <%} %> <%if(nccList.getNxtApprSplitting() == nccList.getTheLastSplitting()) {%>
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
<%
	if(rsCongTy != null) rsCongTy.close();
	if(rsLoaiNCC != null) rsLoaiNCC.close();
	if(rsTaiKhoan != null) rsTaiKhoan.close();
	if(rsNganHang != null) rsNganHang.close();
	if(rsChiNhanh != null) rsChiNhanh.close();
	if(rsNCC != null) rsNCC.close();
 	session.setAttribute("nccList",null);
	nccList.close();
	session.setAttribute("backAttribute",nccList);
	
}%>