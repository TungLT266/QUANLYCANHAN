<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="geso.traphaco.erp.beans.loaihoso.IErpLoaiHoSoList"%>
<%@page import="geso.traphaco.center.util.*"%>

<%String userId = (String) session.getAttribute("userId");  
String userTen = (String) session.getAttribute("userTen");  	
String sum = (String) session.getAttribute("sum");
Utility util = (Utility) session.getAttribute("util");
if(!util.check(userId, userTen, sum)){
	response.sendRedirect("/TraphacoHYERP/index.jsp");
}else{ 
	int[] quyen = new int[11];
	quyen = util.GetquyenNew("ErpLoaiHoSoSvl","",userId);
	IErpLoaiHoSoList obj = (IErpLoaiHoSoList)session.getAttribute("obj");
	ResultSet LoaihosoRs = obj.getLoaihosoRs(); %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>TraphacoHYERP - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">

<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />

<SCRIPT language="javascript" type="text/javascript">
function clearform() { 
    document.forms['loaihoso'].maloaihoso.value= "";
    document.forms['loaihoso'].mabieumau.value= "";
    document.forms['loaihoso'].diengiai.value= "";
    document.forms['loaihoso'].loaimauin.value= "";
    document.forms['loaihoso'].trangthai.value = "";
    document.forms['loaihoso'].action.value= 'search';
	document.forms['loaihoso'].submit();
}

function submitform() {
	document.forms['loaihoso'].action.value= 'search';
	document.forms['loaihoso'].submit();
}

function newform() {
	document.forms['loaihoso'].action.value= 'new';
	document.forms['loaihoso'].submit();
}
</SCRIPT>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="loaihoso" method="post" action="../../ErpLoaiHoSoSvl">
		<input type="hidden" name="userId" value="<%= userId %>">
		<input type="hidden" name="action" value="1">
		<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
			<TR>
				<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
					<TABLE width="100%" cellpadding="0" cellspacing="2">
						<TR>
							<TD align="left" class="tbnavigation">
								<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
									<TR height="22">
										<TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền > Hồ sơ kiểm nghiệm > Biểu mẫu hồ sơ</TD>
										<TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %></TD>
									</tr>
								</TABLE>
							</TD>
						</TR>
					</TABLE>
					
					<TABLE width="100%" cellpadding="0" cellspacing="1">
						<TR>
							<TD>
								<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
									<TR>
										<TD width="100%" align="center">
											<FIELDSET>
												<LEGEND class="legendtitle">&nbsp;Tiêu chí tìm kiếm&nbsp;</LEGEND>
												<TABLE width="100%" cellpadding="6" cellspacing="0">
													<TR>
														<TD width="15%" class="plainlabel">Mã ID</TD>
														<TD class="plainlabel" colspan="4">
															<input type="text" name="maloaihoso" value="<%=obj.getMaLoaihoso() %>" size="20" onchange=submitform();>
														</TD>
													</TR>
													<TR>
														<TD width="15%" class="plainlabel">Mã biểu mẫu</TD>
														<TD class="plainlabel">
															<input type="text" name="mabieumau" value="<%=obj.getMaBieumau() %>" size="20" onchange=submitform();>
														</TD>
														
														<TD width="15%" class="plainlabel">Diễn giải</TD>
														<TD class="plainlabel">
															<input type="text" name="diengiai" value="<%=obj.getDiengiai() %>" size="20" onchange=submitform();>
														</TD>
													</TR>
													<TR>
														<TD width="15%" class="plainlabel">Trạng thái</TD>
														<TD class="plainlabel">
															<select name="trangthai" onChange="submitform();">
																<option value=""></option>
																<option value="0" <%if(obj.getTrangthai().equals("0")){ %>selected<%} %>>Ngưng hoạt động</option>
																<option value="1" <%if(obj.getTrangthai().equals("1")){ %>selected<%} %>>Hoạt động</option>
																<option value="2" <%if(obj.getTrangthai().equals("2")){ %>selected<%} %>>Đã xóa</option>
															</select>
														</TD>
														
														<TD width="15%" class="plainlabel">Loại mẫu in</TD>
														<TD class="plainlabel">
															<select name="loaimauin" style="width:200px" onchange=submitform();>
																<option value=""></option>
																<option value="0" <%if(obj.getLoaimauin().equals("0")){ %>selected<%} %>>Khác</option>
																<option value="1" <%if(obj.getLoaimauin().equals("1")){ %>selected<%} %>>Hồ sơ lấy mẫu</option>
																<option value="2" <%if(obj.getLoaimauin().equals("2")){ %>selected<%} %>>Hồ sơ kiểm nghiệm</option>
																<option value="3" <%if(obj.getLoaimauin().equals("3")){ %>selected<%} %>>Phiếu kiểm nghiệm</option>
															</select>
														</TD>
													</TR>

													<tr class="plainlabel">
														<td colspan="4">
															<a class="button2" href="javascript:clearform()">
																<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại
															</a>&nbsp;&nbsp;&nbsp;&nbsp;
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
									<LEGEND class="legendtitle">&nbsp;Biểu mẫu hồ sơ &nbsp;&nbsp;
										<% if(quyen[Utility.THEM]!=0) { %>
											<a class="button3" href="javascript:newform()">
												<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới
											</a>
										<% } %>
									</LEGEND>

									<TABLE class="" width="100%">
										<TR>
											<TD width="98%">
												<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
													<TR class="tbheader">
														<TH width="10%">Mã ID</TH>
														<TH width="10%">Mã biểu mẫu</TH>
														<TH width="14%">Diễn giải</TH>
														<TH width="12%">Loại mẫu in</TH>
														<TH width="10%">Trạng thái</TH>
														<TH width="8%">Ngày tạo</TH>
														<TH width="10%">Người tạo</TH>
														<TH width="8%">Ngày sửa</TH>
														<TH width="10%">Người sửa</TH>
														<TH width="8%">Tác vụ</TH>
													</TR>
													<%int m = 0;
			                                    	String lightrow = "tblightrow";
			                                    	String darkrow = "tbdarkrow";
			                                    	if(LoaihosoRs != null){
				                                    	while(LoaihosoRs.next()){
				                                    		String tt = LoaihosoRs.getString("trangthai").trim();
				                                    		String lmi = LoaihosoRs.getString("loaimauin").trim();
				                                    	
				                                        	if (m % 2 != 0) { %>
																<TR class=<%=lightrow%>>
															<%} else {%>
																<TR class=<%= darkrow%>>
															<%}%>
															<TD align="center"><%= LoaihosoRs.getString("ma") %></TD>
															<TD align="center"><%= LoaihosoRs.getString("mabieumau") %></TD>
															<TD><%= LoaihosoRs.getString("ten")%></TD>
															
															<%if(lmi.equals("0")){ %>
																<TD>Khác</TD>
															<%} else if(lmi.equals("1")){ %>
																<TD>Hồ sơ lấy mẫu</TD>
															<%} else if(lmi.equals("2")){ %>
																<TD>Hồ sơ kiểm nghiệm</TD>
															<%} else { %>
																<TD>Phiếu kiểm nghiệm</TD>
															<%} %>
															
															<%if(tt.equals("0")) { %>
																<TD align="center" style="color: red;">Ngưng hoạt động</TD>
															<%} else if(tt.equals("2")){ %>
																<TD align="center" style="color: red;">Đã xóa</TD>
															<%} else { %>
																<TD align="center">Hoạt động</TD>
															<%} %>

															<TD align="center"><%= LoaihosoRs.getString("NGAYTAO")%></TD>
															<TD><%= LoaihosoRs.getString("NGUOITAO")%></TD>
															<TD align="center"><%= LoaihosoRs.getString("NGAYSUA")%></TD>
															<TD><%= LoaihosoRs.getString("NGUOISUA")%></TD>
															<TD align="center">
																<% if(tt.equals("1") || tt.equals("0")){
																	if(quyen[Utility.SUA]!=0){ %>
																		<A href="../../ErpLoaiHoSoUpdateSvl?userId=<%=userId%>&update=<%= LoaihosoRs.getString("pk_seq") %>">
																			<img title="Cập nhật" src="../images/Edit20.png" alt="Cap nhat" width="20" height="20" longdesc="Cap nhat" border=0>
																		</A> &nbsp;
																	<%} %>
																	<%if(quyen[Utility.XOA]!=0){ %>
																		<A href="../../ErpLoaiHoSoSvl?userId=<%=userId%>&delete=<%= LoaihosoRs.getString("pk_seq") %>">
																			<img title="Xóa" src="../images/Delete20.png" alt="Xoa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn muốn xóa biểu mẫu hồ sơ này?')) return false;">
																		</A>&nbsp;
																	<%} %>
																	<%if(quyen[Utility.XEM]!=0){ %>
																		<A href="../../ErpLoaiHoSoUpdateSvl?userId=<%=userId%>&display=<%= LoaihosoRs.getString("pk_seq") %>">
																			<IMG title="Hiển thị" src="../images/Display20.png" alt="Hien thi" title="Hien thi" border=0>
																		</A>
																	<%}
																} else {%>
																	<%-- <%if(quyen[Utility.SUA]!=0){ %>
																		<A href="../../ErpLoaiHoSoUpdateSvl?userId=<%=userId%>&update=<%= LoaihosoRs.getString("pk_seq") %>">
																			<img title="Cập nhật" src="../images/Edit20.png" alt="Cap nhat" width="20" height="20" longdesc="Cap nhat" border=0>
																		</A> &nbsp;
																	<%} %> --%>
																	<%if(quyen[Utility.XEM]!=0){ %>
																		<A href="../../ErpLoaiHoSoUpdateSvl?userId=<%=userId%>&display=<%= LoaihosoRs.getString("pk_seq") %>">
																			<IMG title="Hiển thị" src="../images/Display20.png" alt="Hien thi" title="Hien thi" border=0>
																		</A>
																	<%}
																} %>
															</TD>
															</TR>
															<%m++;
														}
		                                    		} %>
													<TR class="tbfooter">
														<TD align="center" colspan="15">&nbsp;</TD>
													</TR>
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
	try {
		if(LoaihosoRs != null)
			LoaihosoRs.close();
		
		if(obj != null) {
			obj.DBClose();
			obj = null;
		}
		session.setAttribute("obj", null);
	}
	catch (Exception e) {}
}%>