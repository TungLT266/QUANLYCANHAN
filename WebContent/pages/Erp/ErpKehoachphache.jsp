<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="geso.traphaco.erp.beans.kehoachphache.IErpKeHoachPhaCheList"%>
<%@page import="geso.traphaco.center.util.*"%>

<%String userId = (String) session.getAttribute("userId");  
String userTen = (String) session.getAttribute("userTen");  	
String sum = (String) session.getAttribute("sum");
Utility util = (Utility) session.getAttribute("util");
if(!util.check(userId, userTen, sum)){
	response.sendRedirect("/TraphacoHYERP/index.jsp");
} else {
	int[] quyen = new int[11];
	quyen = util.GetquyenNew("ErpKeHoachPhaCheSvl","",userId);
	IErpKeHoachPhaCheList obj = (IErpKeHoachPhaCheList)session.getAttribute("obj");
	ResultSet KhuvucSXRs = obj.getKhuvucSXRs();
	ResultSet sanphamRs = obj.getSanphamRs();
	ResultSet KehoachphacheRs = obj.getKehoachphacheRs(); %>

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
	$(document).ready(function()
	{
		$(".select2").select2();
	});
</script>

<SCRIPT language="javascript" type="text/javascript">
function clearform() { 
    document.forms['kehoachphache'].ngaykehoach.value= "";
    document.forms['kehoachphache'].bophanthuchien.value= "";
    document.forms['kehoachphache'].loai.value= "";
    document.forms['kehoachphache'].sanpham.value= "";
    document.forms['kehoachphache'].trangthai.value = "";
    document.forms['kehoachphache'].action.value= 'search';
	document.forms['kehoachphache'].submit();
}

function submitform() {
	document.forms['kehoachphache'].action.value= 'search';
	document.forms['kehoachphache'].submit();
}

function newform() {
	document.forms['kehoachphache'].action.value= 'new';
	document.forms['kehoachphache'].submit();
}
</SCRIPT>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="kehoachphache" method="post" action="../../ErpKeHoachPhaCheSvl">
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
										<TD align="left" colspan="2" class="tbnavigation">&nbsp;Hồ sơ kiểm nghiệm > Chức năng > Kế hoạch pha chế, hiệu chuẩn thuốc</TD>
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
														<TD width="15%" class="plainlabel">Ngày kế hoạch</TD>
														<TD class="plainlabel" valign="middle">
															<input class="days" type="text" name="ngaykehoach" value="<%=obj.getNgayKeHoach() %>" style="width: 200px" onchange=submitform();>
														</TD>
														<TD width="15%" class="plainlabel">Bộ phận thực hiện</TD>
														<TD class="plainlabel">
															<select name="bophanthuchien" style="width: 300px" class="select2" onChange="submitform();">
								                        		<option value=""></option>
								                        		<%if(KhuvucSXRs != null){
																	while(KhuvucSXRs.next()){ 
																		if(KhuvucSXRs.getString("pk_seq").equals(obj.getBoPhanThucHien())){ %>
																			<option value="<%=KhuvucSXRs.getString("pk_seq") %>" selected><%=KhuvucSXRs.getString("ten") %></option>
																		<%} else { %>
																			<option value="<%= KhuvucSXRs.getString("pk_seq") %>" ><%=KhuvucSXRs.getString("ten") %></option>
																		<%}
																	}
																} %>
								                        	</select>
														</TD>
													</TR>
													<TR>
														<TD width="15%" class="plainlabel">Loại</TD>
														<TD class="plainlabel">
															<select name="loai" style="width:200px" onChange="submitform();">
																<option value=""></option>
																<option value="1" <%if(obj.getLoai().equals("1")){ %>selected<%} %>>Pha chế</option>
																<option value="2" <%if(obj.getLoai().equals("2")){ %>selected<%} %>>Hiệu chuẩn</option>
															</select>
														</TD>
														<TD width="15%" class="plainlabel">Sản phẩm</TD>
														<TD class="plainlabel">
															<select name="sanpham" style="width: 300px" class="select2" onChange="submitform();">
								                        		<option value=""></option>
								                        		<%if(sanphamRs != null){
																	while(sanphamRs.next()){ 
																		if(sanphamRs.getString("pk_seq").equals(obj.getSanPham())){ %>
																			<option value="<%=sanphamRs.getString("pk_seq") %>" selected><%=sanphamRs.getString("ten") %></option>
																		<%} else { %>
																			<option value="<%= sanphamRs.getString("pk_seq") %>" ><%=sanphamRs.getString("ten") %></option>
																		<%}
																	}
																} %>
								                        	</select>
														</TD>
													</TR>
													<TR>
														<TD width="15%" class="plainlabel">Trạng thái</TD>
														<TD class="plainlabel" colspan="3">
															<select style="width: 200px" name="trangthai" onChange="submitform();">
																<option value=""></option>
																<option value="0" <%if(obj.getTrangthai().equals("0")){ %>selected<%} %>>Ngưng hoạt động</option>
																<option value="1" <%if(obj.getTrangthai().equals("1")){ %>selected<%} %>>Hoạt động</option>
																<option value="2" <%if(obj.getTrangthai().equals("2")){ %>selected<%} %>>Đã xóa</option>
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
									<LEGEND class="legendtitle">&nbsp;Kế hoạch pha chế, hiệu chuẩn thuốc &nbsp;&nbsp;
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
														<TH width="7%">Số ID</TH>
														<TH width="10%">Ngày kế hoạch</TH>
														<TH width="14%">Bộ phận thực hiện</TH>
														<TH width="8%">Loại</TH>
														<TH width="13%">Sản phẩm</TH>
														<TH width="8%">Trạng thái</TH>
														<TH width="7%">Ngày tạo</TH>
														<TH width="9%">Người tạo</TH>
														<TH width="7%">Ngày sửa</TH>
														<TH width="9%">Người sửa</TH>
														<TH width="8%">Tác vụ</TH>
													</TR>
													<%int m = 0;
			                                    	String lightrow = "tblightrow";
			                                    	String darkrow = "tbdarkrow";
			                                    	if(KehoachphacheRs != null){
				                                    	while(KehoachphacheRs.next()){
				                                    		String tt = KehoachphacheRs.getString("trangthai").trim();
				                                    		String loai = KehoachphacheRs.getString("loai").trim();
				                                    	
				                                        	if (m % 2 != 0) { %>
																<TR class=<%=lightrow%>>
															<%} else {%>
																<TR class=<%= darkrow%>>
															<%}%>
															<TD align="center"><%= KehoachphacheRs.getString("pk_seq") %></TD>
															<TD align="center"><%= KehoachphacheRs.getString("ngaykehoach") %></TD>
															<TD><%= KehoachphacheRs.getString("phongbansx")%></TD>
															<%if(loai.equals("1")) { %>
																<TD align="center">Pha chế</TD>
															<%} else if(loai.equals("2")) { %>
																<TD align="center">Hiệu chuẩn</TD>
															<%} else if(loai.equals("3")) { %>
																<TD align="center">Môi trường</TD>
															<%} else { %>
																<TD align="center">Môi trường nuôi cấy</TD>
															<%} %>
															<TD><%= KehoachphacheRs.getString("sanpham")%></TD>
															<%if(tt.equals("0")) { %>
																<TD align="center" style="color: red;">Ngưng hoạt động</TD>
															<%} else if(tt.equals("2")){ %>
																<TD align="center" style="color: red;">Đã xóa</TD>
															<%} else { %>
																<TD align="center">Hoạt động</TD>
															<%} %>

															<TD align="center"><%= KehoachphacheRs.getString("NGAYTAO")%></TD>
															<TD><%= KehoachphacheRs.getString("NGUOITAO")%></TD>
															<TD align="center"><%= KehoachphacheRs.getString("NGAYSUA")%></TD>
															<TD><%= KehoachphacheRs.getString("NGUOISUA")%></TD>
															<TD align="center">
																<% if(tt.equals("1") || tt.equals("0")){
																	if(quyen[Utility.SUA]!=0){ %>
																		<A href="../../ErpKeHoachPhaCheUpdateSvl?userId=<%=userId%>&update=<%= KehoachphacheRs.getString("pk_seq") %>">
																			<img title="Cập nhật" src="../images/Edit20.png" alt="Cap nhat" width="20" height="20" longdesc="Cap nhat" border=0>
																		</A> &nbsp;
																	<%} %>
																	<%if(quyen[Utility.XOA]!=0){ %>
																		<A href="../../ErpKeHoachPhaCheSvl?userId=<%=userId%>&delete=<%= KehoachphacheRs.getString("pk_seq") %>">
																			<img title="Xóa" src="../images/Delete20.png" alt="Xoa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn muốn xóa loại tiêu chí kiểm nghiệm này?')) return false;">
																		</A>&nbsp;
																	<%} %>
																	<%if(quyen[Utility.XEM]!=0){ %>
																		<A href="../../ErpKeHoachPhaCheUpdateSvl?userId=<%=userId%>&display=<%= KehoachphacheRs.getString("pk_seq") %>">
																			<IMG title="Hiển thị" src="../images/Display20.png" alt="Hien thi" title="Hien thi" border=0>
																		</A>
																	<%}
																} else {%>
																	<%-- <%if(quyen[Utility.SUA]!=0){ %>
																		<A href="../../ErpKeHoachPhaCheUpdateSvl?userId=<%=userId%>&update=<%= YeucauKTRs.getString("pk_seq") %>">
																			<img title="Cập nhật" src="../images/Edit20.png" alt="Cap nhat" width="20" height="20" longdesc="Cap nhat" border=0>
																		</A> &nbsp;
																	<%} %> --%>
																	<%if(quyen[Utility.XEM]!=0){ %>
																		<A href="../../ErpKeHoachPhaCheUpdateSvl?userId=<%=userId%>&display=<%= KehoachphacheRs.getString("pk_seq") %>">
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
		if(KehoachphacheRs != null)
			KehoachphacheRs.close();
		
		if(obj != null) {
			obj.DBClose();
			obj = null;
		}
		session.setAttribute("obj", null);
	}
	catch (Exception e) {}
}%>