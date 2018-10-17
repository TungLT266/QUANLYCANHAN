<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="geso.traphaco.center.util.*"%>
<%@ page import="geso.traphaco.erp.beans.danhgialaichiphitratruoc.IErp_DanhGiaLaiCPTTList"%>
<%@ page import="geso.traphaco.erp.beans.danhgialaichiphitratruoc.imp.Erp_DanhGiaLaiCPTTList"%>
<%
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{	
		IErp_DanhGiaLaiCPTTList obj = (IErp_DanhGiaLaiCPTTList) session.getAttribute("obj");
		ResultSet RsDc = obj.getRsDc();
		ResultSet RsDvth = obj.getPbRs();
	
	 	int[] quyen = new  int[5];
	 	quyen = util.Getquyen("Erp_DanhGiaLaiCPTTSvl","",userId);


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
function thongbao()
{
	 var tt = document.forms["tsForm"].msg.value;
	 if(tt.length>0)
    	alert(document.forms["tsForm"].msg.value);
}

</script>
</head>
<body leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="tsForm" method="post" action="../../Erp_DanhGiaLaiCPTTSvl">
		<input type="hidden" name="userId" value='<%=userId%>'> <input type="hidden" name="action" value='1'>
		<input type="hidden" name="msg" value='<%=obj.getMsg()%>'>
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
										<td align="left" colspan="2" class="tbnavigation">Quản lý chi phí trả trước > Đánh giá lại chi phí trả trước</td>
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
													
													<TR>
							                        <TD class="plainlabel" width="150px">Từ ngày </TD>
							                        <TD class="plainlabel" width="210px">
							                            <input type="text" class="days" 
							                                   id="tungay" name="tungay" value="<%= obj.getTungay() %>" maxlength="10" onchange="submitform()" />
							                        </TD>
							                        <TD class="plainlabel" width="150px" >Đến ngày </TD>
							                        <TD class="plainlabel">
							                            <input type="text" class="days" 
							                                   id="denngay" name="denngay" value="<%= obj.getDenngay() %>" maxlength="10" onchange="submitform()" />
							                        </TD>
							                    	</TR>
													<TR>
								                    <TD class="plainlabel" valign="middle">Số chứng từ </TD>
							                        <TD class="plainlabel" valign="middle">
							                            <input type="text" name="sochungtu" value="<%= obj.getSochungtu() %>" onchange="submitform()">
							                        </TD>
							
							                        <TD class="plainlabel" valign="middle">Số đánh giá </TD>
							                        <TD class="plainlabel" valign="middle">
							                            <input type="text" name="sodieuchuyen" value="<%= obj.getSodieuchuyen() %>" onchange="submitform()">
							                        </TD>                        
							                                                
							                    	</TR>
							                    	<TR>
													<TD class="plainlabel" >Trạng thái</TD>
													<TD class="plainlabel" colspan="3">
														<select name = "trangthai" id= "trangthai" style = "width:200px" onchange="submitform()">
															<%if(obj.getTrangthai().equals("")){ %>
															<option value=""></option>
															<option value="0">Chưa chốt</option>
															<option value="1">Đã chốt</option>
															<option value="2">Đã xóa</option>
															<%}else if(obj.getTrangthai().equals("0")){ %>
															<option value=""></option>
															<option value="0" selected>Chưa chốt</option>
															<option value="1">Đã chốt</option>
															<option value="2">Đã xóa</option>
															<%} else if(obj.getTrangthai().equals("1")){ %>
															<option value=""></option>
															<option value="0">Chưa chốt</option>
															<option value="1" selected>Đã chốt</option>
															<option value="2">Đã xóa</option>
															<%} else if(obj.getTrangthai().equals("2")){ %>
															<option value=""></option>
															<option value="0">Chưa chốt</option>
															<option value="1">Đã chốt</option>
															<option value="2" selected>Đã xóa</option>
															<%}%>
														</select>
													</TD>
												 
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
									
										&nbsp;Đánh giá lại tài sản &nbsp;&nbsp;&nbsp;  
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
														<th width="10%">Số đánh giá</th>
														<th width="15%">Tài sản</th>
														<th width="15%">Trạng thái</th>
														<th width="25%">Ngày đánh giá</th>
														<th width="15%">Số chứng từ</th>
														<th width="5%" align="center">&nbsp;Tác vụ</th> 
													</tr>
								<%	int m = 0;
									String lightrow = "tblightrow";
									String darkrow = "tbdarkrow";
									if (RsDc != null)
										while (RsDc.next()) {
											if (m % 2 != 0) {%>
													<tr class=<%=lightrow%>>
														<%} else {%>
													
													<tr class=<%=darkrow%>>
														<%} %>
														<td align="center"><%=RsDc.getString("PK_SEQ")%></td>
														<td align="left"><%=RsDc.getString("TAISAN")%></td>
														<%if (RsDc.getString("trangthai").equals("1")) {%>
														<td align="center">Đã chốt</td>
														<%} else if (RsDc.getString("trangthai").equals("0")){%>
														<td align="center">Chưa chốt</td>
														<%}else { %>
														<td align="center">Đã xóa</td>
														<%} %>
														<td align="center"><%=RsDc.getString("NGAYCHUNGTU")%></td>
														<td align="center"><%=RsDc.getString("SOCHUNGTU")%></td>
														<td align="center">
															<table border=0 cellpadding="0" cellspacing="0">
																<tr>
																	
																	<td>&nbsp;&nbsp;</td>
																
																	<td> 
																	<% 	if(RsDc.getString("trangthai").equals("0")){ %>											
																	<%if(quyen[2]!=0){ %>
																	<a href="../../Erp_DanhGiaLaiCPTTUpdateSvl?userid=<%=userId%>&update=<%=RsDc.getString("pk_seq")%>"><img
																			src="../images/Edit20.png" alt="Chinh sua" width="20" height="20" longdesc="Chinh sua" border=0></A>
																			<%} %>
																	</td>
																	
																	<td> 
																													
																	<%if(quyen[3]!=0){ %>
																	<a href="../../Erp_DanhGiaLaiCPTTSvl?userid=<%=userId%>&chot=<%=RsDc.getString("pk_seq")%>"><img
																			src="../images/Chot.png" alt="Chốt" width="20" height="20" longdesc="Chot" border=0></A>
																			<%} %>
																	</td>
																	
																		<td>
																	
																	<%if(quyen[1]!=0){ %>  
																	
																	<a href=../../Erp_DanhGiaLaiCPTTSvl?userid=<%=userId%>&delete=<%=RsDc.getString("pk_seq")%>><img src="../images/Delete20.png"
																			alt="Xoa" width="20" height="20" longdesc="Xoa" border=0
																			onclick="if(!confirm('Bạn muốn xóa đánh giá tài sản này ?')) return false;"></a> 
																	<%   } %>
																	</td>	
																	
																																		<td>
																	<% } %>	
																	<% if(RsDc.getString("trangthai").equals("1")||RsDc.getString("trangthai").equals("2")){ %>
																	
																	
													
																	<td>
																	<%if(quyen[2]!=0){ %>
																	<a href="../../Erp_DanhGiaLaiCPTTUpdateSvl?userid=<%=userId%>&display=<%=RsDc.getString("pk_seq")%>"><img
																			src="../images/Display20.png" alt="Hiển thị" width="20" height="20" longdesc="Hiển thị" border=0></A>
																			<%} %>
																			</td>
																	<%} %>
																	<td>&nbsp;&nbsp;</td>
																	<td>&nbsp;</td>
													
																	
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
		if(RsDvth!=null){
			RsDvth.close();
		}
		if(RsDc!=null){
			RsDc.close();
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

