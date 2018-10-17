<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="geso.traphaco.center.util.*"%>
<%@ page import="geso.traphaco.erp.beans.taisancodinh.IErp_CongCuDungCu"%>
<%@ page import="geso.traphaco.erp.beans.taisancodinh.imp.Erp_CongCuDungCu"%>
<%
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
%>
<%


	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{	
		IErp_CongCuDungCu obj = (IErp_CongCuDungCu) session.getAttribute("obj");
		ResultSet RsCcdc = obj.getRsCcdc();
		ResultSet RsLccdc = obj.getRsLoaiCCDC() ;
		ResultSet RsNccdc = obj.getRsNccdc();
	
	 	int[] quyen = new  int[5];
	 	quyen = util.Getquyen("Erp_CongCuDungCuSvl","",userId);


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
    document.ccdcForm.ma.value = "";
    document.ccdcForm.lccdcId.value = "";
    document.ccdcForm.nccdcId.value = "";
    submitform();
}

function submitform()
{
	document.forms['ccdcForm'].action.value= 'search';
	document.forms['ccdcForm'].submit();
}

function newform()
{
	document.forms['ccdcForm'].action.value= 'new';
	document.forms['ccdcForm'].submit();
}

</script>
</head>
<body leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="ccdcForm" method="post" action="../../Erp_CongCuDungCuSvl">
		<input type="hidden" name="userId" value='<%=userId%>'> <input type="hidden" name="action" value='1'>
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
										<td align="left" colspan="2" class="tbnavigation">Quản lý Chi phí trả trước &gt; Chi phí trả trước</td>
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
													<tr>
														<td class="plainlabel" width="15%">Mã Chi phí trả trước</td>
														<TD class="plainlabel"><input type="text" name="ma" value="<%=obj.getMa()%>" onchange="submitform()"></td>
													</tr>
													<tr>
														<td class="plainlabel" width="15%">Loại Chi phí trả trước</td>
														<td class="plainlabel"><select name="lccdcId" onchange="submitform()">
																<option value=""></option>
																<%if (RsLccdc != null) {
																while (RsLccdc.next()) {
															if (obj.getLccdcId().equals(RsLccdc.getString("pk_seq"))) {%>
																<option selected="selected" value="<%=RsLccdc.getString("pk_seq")%>"><%=RsLccdc.getString("ten")%></option>
																<%} else {%>
																<option value="<%=RsLccdc.getString("pk_seq")%>"><%=RsLccdc.getString("ten")%></option>
																<%}

															}
														}%>
														</select></td>
													</tr>
													<tr>
														<td class="plainlabel" width="15%">Nhóm Chi phí trả trước</td>
														<td class="plainlabel"><select name="nccdcId" onchange="submitform()">
																<option value=""></option>
																<%if (RsNccdc != null) {
														while (RsNccdc.next()) {
															if (obj.getNccdcId().equals(RsNccdc.getString("pk_seq"))) {%>
															<option selected="selected" value="<%= RsNccdc.getString("pk_seq")%>"><%= RsNccdc.getString("ma") + " - " + RsNccdc.getString("diengiai")%></option>
															<%} else {%>
															<option value="<%= RsNccdc.getString("pk_seq")%>"><%= RsNccdc.getString("ma") + " - " + RsNccdc.getString("diengiai") %></option>
															<%}
												
														}
													}%>
														</select></td>
													</tr>
													<tr>
														<td colspan="2" class="plainlabel"><a class="button2" href="javascript:xoaform()"> <img style="top: -4px;"
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
									
										&nbsp;Chi phí trả trước &nbsp;&nbsp;&nbsp;  
										<%if(quyen[0]!=0){ %>
										<a class="button3" href="javascript:newform()"> <img style="top: -4px;" src="../images/New.png" alt="">Tạo mới
										</a>
										<%} %>
									</legend>
									<table class="tabletitle" width="100%" border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td align="left" colspan="4" class="legendtitle">
												<table width="100%" border="0" cellspacing="1" cellpadding="4">
													<tr class="tbheader">
														<th width="10%">Mã </th>
														<th width="25%">Diễn giải</th>
														<th width="15%">Trạng thái</th>
														<th width="15%">Phòng ban </th>
														<th width="15%">Loại chi phí</th>
														<th width="5%" align="center">&nbsp;Tác vụ</th> 
														
													</tr>
								<%	int m = 0;
									String lightrow = "tblightrow";
									String darkrow = "tbdarkrow";
									if (RsCcdc != null)
										while (RsCcdc.next()) {
											if (m % 2 != 0) {%>
													<tr class=<%=lightrow%>>
														<%} else {%>
													
													<tr class=<%=darkrow%>>
														<%}%>
														<td align="center"><%=RsCcdc.getString("ma")%></td>
														<td align="left"><%=RsCcdc.getString("diengiai")%></td>
														<%if (RsCcdc.getString("trangthai").equals("1")) {%>
														<td align="center">Hoạt Động</td>
														<%} else {%>
														<td align="center">Ngưng Hoạt Động</td>
														<%}%>
														
														<td align="left"><%=RsCcdc.getString("DVTH")%></td>
														<td align="left"><%=RsCcdc.getString("LOAICCDC")%></td>

														<td align="center">
															<table border=0 cellpadding="0" cellspacing="0">
																<tr>
															<% if(obj.isKhauhao(RsCcdc.getString("PK_SEQ"))){
															
															System.out.print("aaaaaaaaaaaa"+obj.isKhauhao(RsCcdc.getString("PK_SEQ")));
															%>	
																	<td> 
																	<%if(quyen[2]!=0){ %>
																	<a href="../../Erp_CongCuDungCuUpdateSvl?userid=<%=userId%>&update=<%=RsCcdc.getString("pk_seq")%>"><img
																			src="../images/Edit20.png" alt="Chinh sua" width="20" height="20" longdesc="Chinh sua" border=0></A>
																			<%} %>
																			</td>
																	<td>&nbsp;&nbsp;</td>
																	<td>
																	<%if(quyen[1]!=0){ 
																		if(RsCcdc.getString("trangthai").equals("0")){
																	%>  
																	
																	<a href="../../Erp_CongCuDungCuSvl?userid=<%=userId%>&delete=<%=RsCcdc.getString("pk_seq")%>"><img src="../images/Delete20.png"
																			alt="Xoa" width="20" height="20" longdesc="Xoa" border=0
																			onclick="if(!confirm('Bạn muốn xóa Chi phí trả trước này ?')) return false;"></a> 
																	<%   }
																	 } %>
																	</td>
															<%}else{ %>
																	<td>
																	<%if(quyen[2]!=0){ %>
																	<a href="../../Erp_CongCuDungCuUpdateSvl?userid=<%=userId%>=<%=userId%>&display=<%=RsCcdc.getString("pk_seq")%>"><img
																			src="../images/Display20.png" alt="Hiển thị" width="20" height="20" longdesc="Hiển thị" border=0></A>
																			<%} %>
																			</td>
																	<td>&nbsp;&nbsp;</td>
																	<td>&nbsp;</td>
															
															
															
															<%} %>
																</tr>
															</table>
													</tr>
													<%m++;
											}%>
													<tr>
														<td height="26" colspan="16" align="center" class="tbfooter">&nbsp;</td>
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
		if(RsLccdc!=null){
			RsLccdc.close();
		}
		if(RsNccdc!=null){
			RsNccdc.close();
		}
		if(RsCcdc!=null){
			RsCcdc.close();
		}
		session.setAttribute("obj",null);
		obj.DBClose();
	}catch(Exception er){
		
	}
	}
	%>
</body>
</html>

