<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="geso.traphaco.erp.beans.phieuphache.IErpPhieuPhaCheList"%>
<%@page import="geso.traphaco.center.util.*"%>

<%String userId = (String) session.getAttribute("userId");  
String userTen = (String) session.getAttribute("userTen");  	
String sum = (String) session.getAttribute("sum");
Utility util = (Utility) session.getAttribute("util");
if(!util.check(userId, userTen, sum)){
	response.sendRedirect("/TraphacoHYERP/index.jsp");
} else {
	int[] quyen = new int[11];
	quyen = util.GetquyenNew("ErpPhieuPhaCheSvl","",userId);
	IErpPhieuPhaCheList obj = (IErpPhieuPhaCheList)session.getAttribute("obj");
	ResultSet PhieuphacheRs = obj.getPhieuphacheRs();
	ResultSet SanphamRs = obj.getSanphamRs(); %>

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
	    document.forms['phieuphache'].ngaychungtu.value= "";
	    document.forms['phieuphache'].sanpham.value= "";
	    document.forms['phieuphache'].nguoiphache.value= "";
	    document.forms['phieuphache'].trangthai.value = "";
	    document.forms['phieuphache'].action.value= 'search';
		document.forms['phieuphache'].submit();
	}
	
	function submitform() {
		document.forms['phieuphache'].action.value= 'search';
		document.forms['phieuphache'].submit();
	}
	
	function newform() {
		document.forms['phieuphache'].action.value= 'new';
		document.forms['phieuphache'].submit();
	}
	
	if(<%=obj.getMsg().length() %> > 0) {
     	alert("<%=obj.getMsg() %>");
	}
</SCRIPT>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="phieuphache" method="post" action="../../ErpPhieuPhaCheSvl">
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
										<TD align="left" colspan="2" class="tbnavigation">&nbsp;Hồ sơ kiểm nghiệm > Chức năng > Phiếu pha chế, hiệu chuẩn thuốc</TD>
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
														<TD width="15%" class="plainlabel">Ngày chứng từ</TD>
														<TD class="plainlabel" valign="middle">
															<input class="days" type="text" name="ngaychungtu" value="<%=obj.getNgaychungtu() %>" style="width: 300px" onchange=submitform();>
														</TD>
													</TR>
													
													<TR>
														<TD width="15%" class="plainlabel">Sản phẩm</TD>
														<TD class="plainlabel">
															<select name="sanpham" style="width: 300px" class="select2" onChange="submitform();">
								                        		<option value=""></option>
								                        		<%if(SanphamRs != null){
																	while(SanphamRs.next()){ 
																		if(SanphamRs.getString("pk_seq").equals(obj.getSanpham())){ %>
																			<option value="<%=SanphamRs.getString("pk_seq") %>" selected><%=SanphamRs.getString("ten") %></option>
																		<%} else { %>
																			<option value="<%= SanphamRs.getString("pk_seq") %>" ><%=SanphamRs.getString("ten") %></option>
																		<%}
																	}
																} %>
								                        	</select>
														</TD>
													</TR>
													
													<TR>
														<TD width="15%" class="plainlabel">Người pha chế</TD>
														<TD class="plainlabel" valign="middle">
															<input type="text" name="nguoiphache" value="<%=obj.getNguoiphache() %>" style="width: 300px" onchange=submitform();>
														</TD>
													</TR>
													
													<TR>
														<TD width="15%" class="plainlabel">Trạng thái</TD>
														<TD class="plainlabel" colspan="3">
															<select style="width: 300px" name="trangthai" onChange="submitform();">
																<option value=""></option>
																<option value="0" <%if(obj.getTrangthai().equals("0")){ %>selected<%} %>>Chưa chốt</option>
																<option value="1" <%if(obj.getTrangthai().equals("1")){ %>selected<%} %>>Đã chốt</option>
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
									<LEGEND class="legendtitle">&nbsp;Phiếu pha chế, hiệu chuẩn thuốc thử &nbsp;&nbsp;
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
														<TH width="10%">Ngày chứng từ</TH>
														<TH width="20%">Sản phẩm</TH>
														<TH width="15%">Người pha chế</TH>
														<TH width="8%">Trạng thái</TH>
														<TH width="8%">Ngày tạo</TH>
														<TH width="12%">Người tạo</TH>
														<TH width="8%">Ngày sửa</TH>
														<TH width="12%">Người sửa</TH>
														<TH width="7%">Tác vụ</TH>
													</TR>
													<%int m = 0;
			                                    	String lightrow = "tblightrow";
			                                    	String darkrow = "tbdarkrow";
			                                    	if(PhieuphacheRs != null){
				                                    	while(PhieuphacheRs.next()){
				                                    		String tt = PhieuphacheRs.getString("trangthai").trim();
				                                    	
				                                        	if (m % 2 != 0) { %>
																<TR class=<%=lightrow%>>
															<%} else {%>
																<TR class=<%= darkrow%>>
															<%}%>
															<TD align="center"><%= PhieuphacheRs.getString("ngaychungtu") %></TD>
															<TD><%= PhieuphacheRs.getString("sanpham")%></TD>
															<TD><%= PhieuphacheRs.getString("nguoiphache")%></TD>
															<%if(tt.equals("0")) { %>
																<TD align="center">Chưa chốt</TD>
															<%} else if(tt.equals("2")){ %>
																<TD align="center" style="color: red;">Đã xóa</TD>
															<%} else { %>
																<TD align="center">Đã chốt</TD>
															<%} %>

															<TD align="center"><%= PhieuphacheRs.getString("NGAYTAO")%></TD>
															<TD><%= PhieuphacheRs.getString("NGUOITAO")%></TD>
															<TD align="center"><%= PhieuphacheRs.getString("NGAYSUA")%></TD>
															<TD><%= PhieuphacheRs.getString("NGUOISUA")%></TD>
															<TD align="center">
																<%if(tt.equals("0")){ %>
																	<%if(quyen[Utility.SUA]!=0){ %>
																		<A href="../../ErpPhieuPhaCheUpdateSvl?userId=<%=userId%>&update=<%= PhieuphacheRs.getString("pk_seq") %>">
																			<img title="Cập nhật" src="../images/Edit20.png" alt="Cap nhat" width="20" height="20" longdesc="Cap nhat" border=0>
																		</A> &nbsp;
																	<%} %>
																	<%if(quyen[Utility.CHOT]!=0){ %>
																		<A href="../../ErpPhieuPhaCheSvl?userId=<%=userId%>&chot=<%= PhieuphacheRs.getString("pk_seq") %>">
																			<img title="Chốt" src="../images/Chot.png" alt="Chot" width="20" height="20" longdesc="Chot" border=0 onclick="if(!confirm('Bạn muốn chốt phiếu pha chế này?')) return false;">
																		</A>&nbsp;
																	<%} %>
																	<%if(quyen[Utility.XOA]!=0){ %>
																		<A href="../../ErpPhieuPhaCheSvl?userId=<%=userId%>&delete=<%= PhieuphacheRs.getString("pk_seq") %>">
																			<img title="Xóa" src="../images/Delete20.png" alt="Xoa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn muốn xóa phiếu pha chế này?')) return false;">
																		</A>&nbsp;
																	<%} %>
																	<%if(quyen[Utility.XEM]!=0){ %>
																		<A href="../../ErpPhieuPhaCheUpdateSvl?userId=<%=userId%>&display=<%= PhieuphacheRs.getString("pk_seq") %>">
																			<IMG title="Hiển thị" src="../images/Display20.png" alt="Hien thi" title="Hien thi" border=0>
																		</A>
																	<%} %>
																<%} else if(tt.equals("1")){ %>
																	<%if(quyen[Utility.HUYCHOT]!=0){ %>
																		<A href="../../ErpPhieuPhaCheSvl?userId=<%=userId%>&bochot=<%= PhieuphacheRs.getString("pk_seq") %>">
																			<img title="Bỏ chốt" src="../images/unChot.png" alt="Bochot" width="20" height="20" longdesc="Bochot" border=0 onclick="if(!confirm('Bạn muốn bỏ chốt phiếu pha chế này?')) return false;">
																		</A>&nbsp;
																	<%} %>
																	<%if(quyen[Utility.XEM]!=0){ %>
																		<A href="../../ErpPhieuPhaCheUpdateSvl?userId=<%=userId%>&display=<%= PhieuphacheRs.getString("pk_seq") %>">
																			<IMG title="Hiển thị" src="../images/Display20.png" alt="Hien thi" title="Hien thi" border=0>
																		</A>
																	<%} %>
																<%} else { %>
																	<%if(quyen[Utility.XEM]!=0){ %>
																		<A href="../../ErpPhieuPhaCheUpdateSvl?userId=<%=userId%>&display=<%= PhieuphacheRs.getString("pk_seq") %>">
																			<IMG title="Hiển thị" src="../images/Display20.png" alt="Hien thi" title="Hien thi" border=0>
																		</A>
																	<%} %>
																<%} %>
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
		if(PhieuphacheRs != null)
			PhieuphacheRs.close();
		
		if(obj != null) {
			obj.DBClose();
			obj = null;
		}
		session.setAttribute("obj", null);
	}
	catch (Exception e) {}
}%>