<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="geso.traphaco.erp.beans.thongtinsanphamgiay.*"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="geso.traphaco.center.util.*"%>
<%
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if (!util.check(userId, userTen, sum)) {
		response.sendRedirect("/Canfoco/index.jsp");
	} else {
		int[] quyen = new  int[5];
		/* quyen = util.Getquyen("33",userId); */
		quyen = util.Getquyen("ThongtinsanphamgiaySvl","",userId);
%>
<%
	IThongtinsanphamgiayList obj = (IThongtinsanphamgiayList) session.getAttribute("obj");
%>
<% obj.setNextSplittings(); %>

<%
	ResultSet splist = (ResultSet) obj.getThongtinsanphamList();
%>
<%
	ResultSet dvkd = obj.getDvkd();
%>
<%
	ResultSet nh = obj.getNh();
%>
<%
	ResultSet cl = obj.getCl();
%>
<%
	ResultSet lspRs = obj.getLspRs();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>TraphacoHYERP - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<script type="text/javascript"	src="../scripts/jquery.min.1.7.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />

<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css"/>
	<link href="../css/select2.css" rel="stylesheet" />
	 <script src="../scripts/select2.js"></script>
<!-- 	 <script>
	     $(document).ready(function() { 
	      $("select:not(.notuseselect2)").select2({ width: 'resolve' });   
	     });
	    </script> -->
<SCRIPT language="JavaScript" type="text/javascript">
	function submitform() {
		document.spForm.action.value = 'search';
		document.forms["spForm"].submit();
	}

	function clearform() {
		document.spForm.masp.value = "";
		document.spForm.tennoibo.value = "";
		document.spForm.tensp.value = "";
		/* document.spForm.dvkdId.selectedIndex = 0;
		document.spForm.nhId.selectedIndex = 0;
		document.spForm.clId.selectedIndex = 0; */
		document.spForm.lspId.selectedIndex = 0;
		document.spForm.trangthai.selectedIndex = 0;
		 submitform();
	}

	function xuatExcel() {
		document.forms['spForm'].action.value = 'excel';
		document.forms['spForm'].submit();
	}

	function newform() {
		document.forms['spForm'].action.value = 'new';
		document.forms['spForm'].submit();
	}
	function thongbao() {
		 
		var tt = document.forms['spForm'].msg1.value;
		if (tt.length > 0)
			alert(document.forms['spForm'].msg1.value);
}



</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="spForm" method="post" action="../../ThongtinsanphamgiaySvl">
		<input type="hidden" name="userId" value='<%=obj.getUserId()%>'> <input type="hidden" name="action" value='1'>  
		<input type="hidden" 	name="msg1" value='<%=obj.getMsg()%>'> <input type="hidden" name="currentPage" value="<%=obj.getCurrentPage()%>">
		<script language="JavaScript">
    	thongbao();
		</script>
		<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
			<TR>
				<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
					<TABLE width="100%" cellpadding="0" cellspacing="1">
						<TR>
							<TD align="left" class="tbnavigation">
								<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
									<TR height="22">
										<TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền > Sản phẩm > Thông tin sản phẩm</TD>
										<TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn<%=userTen%>&nbsp;
										</TD>
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
										<TD width="100%" align="left">
											<FIELDSET>
												<LEGEND class="legendtitle">&nbsp;Tiêu chí tìm kiếm &nbsp;</LEGEND>
												<TABLE class="tblight" width="100%" cellpadding="6" cellspacing="0">
													<TR>
														<TD width="10%" class="plainlabel">Mã sản phẩm</TD>
														<TD class="plainlabel"  width="25%" ><INPUT name="masp" type="text" value='<%=obj.getMasp()%>' onChange="submitform();"></TD>
														<TD width="10%" class="plainlabel">Tên đăng ký SX</TD>
														<TD class="plainlabel" ><INPUT name="tennoibo" type="text" size="15" value='<%=obj.getTennoibo()%>' onChange="submitform();"></TD>
													
													</TR>
										
													<TR>
														<TD class="plainlabel">Tên sản phẩm</TD>
														<TD class="plainlabel"  colspan="3"><INPUT name="tensp" type="text" size="30" value='<%=obj.getTensp()%>' onChange="submitform();"></TD>
														
														<%-- <TD class="plainlabel"  >Đơn vị kinh doanh</TD>
														<TD class="plainlabel" ><SELECT name="dvkdId" onChange="submitform();" >
																<option value="">Tất cả</option>
																<%
																	try {
																			while (dvkd.next()) {
																				if (obj.getDvkdId().equals(dvkd.getString("dvkdId"))) {
																%>
																<option value="<%=dvkd.getString("dvkdId")%>" selected><%=dvkd.getString("dvkd")%></option>
																<%
																	} else {
																%>
																<option value="<%=dvkd.getString("dvkdId")%>"><%=dvkd.getString("dvkd")%></option>
																<%
																	}
																			}
																		} catch (java.sql.SQLException e) {
																		}
																%>
														</SELECT></TD> --%>
													</TR>
							
													<TR>
														<TD class="plainlabel">Loại sản phẩm</TD>
														<TD class="plainlabel" colspan="3"><SELECT name="lspId" onChange="submitform();">
																<option value="">Tất cả</option>
																<%
																 
																	try {
																			while (lspRs.next()) {
																				if (obj.getLspId().equals(lspRs.getString("pk_seq"))) {
																%>
																<option value="<%=lspRs.getString("pk_seq")%>" selected><%=lspRs.getString("ten")%></option>
																<%
																	} else {
																%>
																<option value="<%=lspRs.getString("pk_seq")%>"><%=lspRs.getString("ten")%></option>
																<%
																	}
																			}
																		} catch (java.sql.SQLException e) {
																		}
																%>
														</SELECT></TD>
														<%-- <TD class="plainlabel" style="display: none"> Nhãn hàng</TD>
														<TD class="plainlabel" style="display: none"><SELECT name="nhId" onChange="submitform();">
																<option value="">Tất cả</option>
																<%
																	try {
																			while (nh.next()) {
																				if (obj.getNhId().equals(nh.getString("pk_seq"))) {
																%>
																<option value="<%=nh.getString("pk_seq")%>" selected><%=nh.getString("ten")%></option>
																<%
																	} else {
																%>
																<option value="<%=nh.getString("pk_seq")%>"><%=nh.getString("ten")%></option>
																<%
																	}
																			}
																		} catch (java.sql.SQLException e) {
																		}
																%>
														</SELECT></TD> --%>
													</TR>
											
													<TR>
														<TD class="plainlabel">Trạng thái</TD>
														<TD class="plainlabel" colspan = "3" ><select name="trangthai" onChange="submitform();">
																<%
																
																	if (obj.getTrangthai().equals("0")) {
																%>
																<option value="">Tất cả</option>
																<option value="1">Hoạt động</option>
																<option value="0" selected>Ngưng hoạt động</option>
																<%
																	} else
																			if (obj.getTrangthai().equals("1")) {
																%>
																<option value="" >Tất cả</option>
																<option value="1" selected>Hoạt động</option>
																<option value="0">Ngưng hoạt động</option>
																<%
																	} else
																			if (obj.getTrangthai().equals("")) {
																%>
																<option value="" selected>Tất cả</option>
																<option value="1">Hoạt động</option>
																<option value="0">Ngưng hoạt động</option>
																<%
																	} 	
																%>
														</select></TD>
													</TR>
												
													
													
													<TR>
														<TD class="plainlabel" colspan="4"><a class="button2" href="javascript:clearform()"> <img style="top: -4px;"
																src="../images/button.png" alt="">Nhập lại
														</a>&nbsp;&nbsp;&nbsp;&nbsp; <a class="button2" href="javascript:xuatExcel()"> <img style="top: -4px;" src="../images/button.png" alt="">Xuất
																Excel
														</a></TD>
													</TR>
												</TABLE>
											</FIELDSET>
										</TD>
									</TR>
								</TABLE>
								<TABLE width="100%" cellpadding="0" cellspacing="1">
									<TR>
										<TD width="100%">
											<FIELDSET>
											<%if(quyen[Utility.THEM]!=0) {%>
												<LEGEND class="legendtitle">
													&nbsp;Sản phẩm &nbsp;&nbsp;
													 <a class="button3" href="javascript:newform()"> <img style="top: -4px;" src="../images/New.png" alt="">Tạo mới
													</a>
												</LEGEND>
												<%} %>
												<TABLE width="100%">
													<TR>
														<TD width="98%">
															<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
																<TR class="tbheader">
																	<TH width="6%">Mã sản phẩm</TH>
																	<!-- <TH width="15%">Tên nội bộ</TH> -->
																	<TH width="15%">Tên đăng ký SX</TH>
																	
																	<TH>Tên sản phẩm</TH>
																	
																	<TH width="6%">Đơn vị đo lường</TH>
																<!-- 	<TH width="7%">Đơn vị kinh doanh</TH> -->
																	
																	<TH width="10%">Người tạo</TH>
																	<TH width="7%">Ngày tạo</TH>
																	<TH width="10%">Người sửa</TH>
																	<TH width="7%">Ngày sửa</TH>
																	
																	<!-- <TH width="7%">Nhãn hàng</TH>
																	<TH width="7%">Chủng loại</TH> -->
																	<TH width="7%">Trạng thái</TH>
																	<!-- <TH width="7%">Giá bán lẻ chuẩn</TH> -->
																	<TH width="6%">Tác vụ</TH>
																</TR>
																<%
																	if (splist != null) {
																			try {
																				int m = 0;
																				String lightrow = "tblightrow";
																				String darkrow = "tbdarkrow";
																				while (splist.next()) {
																					String Style="";
																					if(splist.getString("trangthai").equals("0")){
																						Style="color:red;";
																					}
																					
																					if (m % 2 != 0) {
																			
																						
																%>
																<TR style="<%=Style%>" class=<%=lightrow%>>
																	<%
																		} else {
																	%>
																
																<TR style="<%=Style%>" class=<%=darkrow%>>
																	<%
																		}
																	%>
																	<TD align="center"><%=splist.getString("ma")%></TD>
																	<TD align="left"><%=splist.getString("tennoibo")%></TD>
																	<TD align="left"><%=splist.getString("ten")%></TD>
																	<TD align="center"><%=splist.getString("donvi")%></TD>
																	<%-- <TD align="center"><%=splist.getString("dvkd")%></TD> --%>
																	
																	
																	<TD align="center"><%=splist.getString("nguoitao")%></TD>
																	<TD align="center"><%=splist.getString("ngaytao")%></TD>
																	<TD align="center"><%=splist.getString("nguoisua")%></TD>
																	<TD align="center"><%=splist.getString("ngaysua")%></TD>
																	
																	
																	<%-- <TD align="center"><%=splist.getString("nhanhang")%></TD>
																	<TD align="center"><%=splist.getString("chungloai")%></TD> --%>
																	<%
																		if (splist.getString("trangthai").equals("1")) {
																	%>
																	<TD align="center">Hoạt động</TD>
																	<%
																		} else {
																	%>
																	<TD align="center">Ngưng hoạt động</TD>
																	<%
																		}
																	%>
																	<%-- <TD align="right"><%=splist.getString("giablc")%></TD> --%>
																	<TD align="center">
																	<TABLE>
											<TR><TD>
												<%if(quyen[2]!=0){ %>
												<A href = "../../ThongtinsanphamgiayUpdateSvl?userId=<%=userId%>&update=<%=splist.getString("pk_seq") %>"><img src="../images/Edit20.png" alt="Cap nhat" title="Cập nhật" width="20" height="20" longdesc="Cap nhat" border = 0></A>&nbsp;
												<%} %>
												<%if(quyen[3]!=0){ %>
												<A href = "../../ThongtinsanphamgiayUpdateSvl?userId=<%=userId%>&display=<%=splist.getString("pk_seq") %>"><img src="../images/Display20.png" alt="Hien thi" title="Hiển thị" width="20" height="20" longdesc="Hien thi" border = 0></A>&nbsp;
												<%} %>
												<%if(quyen[1]!=0 && userId.equals("100436") ){ %>
												<A href = "../../ThongtinsanphamgiaySvl?userId=<%=userId%>&delete=<%=splist.getString("pk_seq") %>"><img src="../images/Delete20.png" alt="Xoa" title="Xóa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn có muốn xóa sản phẩm này ?')) return false;"></A>
												<%} %>
											</TD></TR>												
										</TABLE>	
																</TR>
																<%
																				m++;
																				}
																			} catch (Exception e) {
																				System.out.println("Loix nek ban : "+e.getMessage());
																				e.printStackTrace();
																			}
																		}
																%>
																<tr class="tbfooter">
																	<TD align="center" valign="middle" colspan="13" class="tbfooter">
																		<%
																			obj.setNextSplittings();
																		%> <script type="text/javascript" src="../scripts/phanTrang.js"></script> <input type="hidden" name="crrApprSplitting"
																		value="<%=obj.getCrrApprSplitting()%>"> <input type="hidden" name="nxtApprSplitting" value="<%=obj.getNxtApprSplitting()%>">
																		<%
																			if (obj.getNxtApprSplitting() > 1) {
																		%> <img alt="Trang Dau" src="../images/first.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, 1, 'view')">
																		&nbsp; <%
																		 	} else {
																		 %> <img alt="Trang Dau" src="../images/first.gif"> &nbsp; <%
																		 	}
																		 %> <%
																		 	if (obj.getNxtApprSplitting() > 1) {
																		 %> <img alt="Trang Truoc" src="../images/prev.gif" style="cursor: pointer;"
																																				onclick="View(document.forms[0].name, eval(document.forms[0].nxtApprSplitting.value) -1, 'view')"> &nbsp; <%
																		 	} else {
																		 %> <img alt="Trang Truoc" src="../images/prev_d.gif"> &nbsp; <%
																		 	}
																		 %> <%
																		 	int[] listPage = obj.getNextSplittings();
																		 		for (int i = 0; i < listPage.length; i++) {
																		 %> <%
																		 	System.out.println("Current page:"
																		 					+ obj.getNxtApprSplitting());
																		 			System.out.println("List page:" + listPage[i]);
																		
																		 			if (listPage[i] == obj.getNxtApprSplitting()) {
																		 %> <a style="color: white;"><%=listPage[i]%>/ <%=obj.getTheLastSplitting()%></a> <%
																		 	} else {
																		 %> <a href="javascript:View(document.forms[0].name, <%=listPage[i]%>, 'view')"><%=listPage[i]%></a> <%
																		 	}
																		 %> <input type="hidden" name="list" value="<%=listPage[i]%>" /> &nbsp; <%
																		 	}
																		 %> <%
																		 	if (obj.getNxtApprSplitting() < obj.getTheLastSplitting()) {
																		 %> &nbsp; <img alt="Trang Tiep" src="../images/next.gif" style="cursor: pointer;"
																																				onclick="View(document.forms[0].name, eval(document.forms[0].nxtApprSplitting.value) +1, 'view')"> &nbsp; <%
																		 	} else {
																		 %> &nbsp; <img alt="Trang Tiep" src="../images/next_d.gif"> &nbsp; <%
																		 	}
																		 %> <%
																		 	if (obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {
																		 %> <img alt="Trang Cuoi" src="../images/last.gif"> &nbsp; <%
																		 	} else {
																		 %> <img alt="Trang Cuoi" src="../images/last.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, -1, 'view')"> &nbsp; <%
																		 	}
																		 %>
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
				</TD>
			</TR>
		</TABLE>
	</form>
</BODY>
</HTML>
<%
try{
	if (splist != null)
			splist.close();
	if (dvkd != null)
			dvkd.close();
	if (nh != null)
			nh.close();
	if (cl != null)
			cl.close();
	if ( lspRs != null)
		lspRs.close();
	obj.DBClose();
	session.removeAttribute("obj") ; 
	session.setAttribute("obj", null) ; 
	session.setAttribute("backAttribute",obj);
}catch (Exception ex)
{
	ex.printStackTrace();
}
%>
<%
	}
%>
