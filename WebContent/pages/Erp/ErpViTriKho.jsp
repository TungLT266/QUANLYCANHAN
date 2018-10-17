<%@page import="java.sql.SQLException"%>
<%@ page import="java.sql.ResultSet"%>
<%@page import="geso.traphaco.erp.beans.khuvuckho.*"%>
<%@page import="geso.traphaco.erp.beans.khuvuckho.imp.*"%>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
<%
	IErpViTriKho kvkList = (IErpViTriKho) session.getAttribute("kvkList");
	ResultSet vitriRs = kvkList.getVitriRs();
	ResultSet rsKho = kvkList.getKhoRs();
	ResultSet rsKhuvuc = kvkList.getKhuVucKhoRs();
	 
	int[] quyen = new  int[5];
	quyen = util.Getquyen("ErpViTriKhoSvl","",userId);	
	 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Style-Type" content="text/css">
<title>Vị Trí kho</title>
<link rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">

<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<script language="javascript" type="text/javascript">
	function xoaform() {
		document.kvkListForm.makhu.value = "";
		document.kvkListForm.tenkhu.value = "";
		document.kvkListForm.makho.value = "";
		document.kvkListForm.submit();
	}
	function Submit() {
		document.forms['kvkListForm'].submit();
	}

	function newform() {
		document.forms['kvkListForm'].action.value = 'New';
		document.forms['kvkListForm'].submit();
	}
	 function thongbao()
	 {
		 var tt = document.forms["kvkListForm"].msg.value;
	 	 if(tt.length>0)
	     	alert(document.forms["kvkListForm"].msg.value);
	 }
	</script>
	<script type="text/javascript">
        $(document).ready(function(){
            $(".button").hover(function(){
                $(".button img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
		$(document).ready(function(){
            $(".button2").hover(function(){
                $(".button2 img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
		$(document).ready(function(){
            $(".button3").hover(function(){
                $(".button3 img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
    </script>
    
    <script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
	<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
	     $(document).ready(function() { $("select").select2();  });
	     
	</script>
	
</head>
<body leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0" onload="thongbao()">
	<form name="kvkListForm" method="post" action="../../ErpViTriKhoSvl">
		<input type="hidden" name="userId" value='<%=userId%>'> 
		<input type="hidden" name="userTen" value='<%=userTen%>'> 
		<input type="hidden" name="action" value='1'>
		<input type="hidden" name="msg" value='<%= kvkList.getMsg()%>'>
		
		<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
			<tr>
				<td colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
					<table width="100%" cellpadding="0" cellspacing="1">
						<tr>
							<td align="left">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr height="22">
										<td align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền > Kho vận > Khai báo vị trí kho</td>
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
										<td width="100%" align="left">
											<fieldset>
												<legend class="legendtitle">Tiêu chí tìm kiếm &nbsp;</legend>
												<table width="100%" cellpadding="6" cellspacing="0">
													<tr>
														<td width="100px" class="plainlabel">Mã vị trí </td>
														<td width="350px" class="plainlabel"><input type="text" name ="makhu" value="<%= kvkList.getMa() %>" style="width: 300px;" onchange="Submit()" />

														<td width="100px" class="plainlabel">Tên vị trí</td>
														<td class="plainlabel"><input type="text" name ="tenkhu" value="<%= kvkList.getDienGiai() %>" style="width: 300px;" onchange="Submit()" />
													</tr>
													<tr>
														<td class="plainlabel">Thuộc kho</td>
														<TD class="plainlabel"><select name="makho" class="select" style="width: 300px;" onchange="Submit()" >
																<option value=""></option>
																<%if (rsKho != null)
																	while (rsKho.next()) {
																		if (kvkList.getKhoId().equals(
																				rsKho.getString("PK_SEQ"))) {%>
																				<option value="<%=rsKho.getString("PK_SEQ")%>" selected="selected"><%=rsKho.getString("Ten")%></option>
																				<%} else {%>
																				<option value="<%=rsKho.getString("PK_SEQ")%>"><%=rsKho.getString("Ten")%></option>
																		<%}
																	}%>
														</select>
														</td>
														<td class="plainlabel">Thuộc khu vực <font class="erroralert">*</font></td>
														<TD class="plainlabel">
															<select name="KhuvucId" class="select" style="width: 300px;" onchange="Submit()" >
																<option value=""></option>
																<%if (rsKhuvuc != null) {
																while (rsKhuvuc.next()) {
																	if (kvkList.getKhuvucId().equals(rsKhuvuc.getString("PK_SEQ"))) {%>
																		<option value="<%=rsKhuvuc.getString("PK_SEQ")%>" selected="selected"><%=rsKhuvuc.getString("Ten")%></option>
																		<%} else {%>
																		<option value="<%=rsKhuvuc.getString("PK_SEQ")%>"><%=rsKhuvuc.getString("Ten")%></option>
																		<%}
																} rsKhuvuc.close(); } %>
															</select>
														</td>
													</tr>
													
													<tr>
														<td colspan="4" class="plainlabel">
														&nbsp;&nbsp;
														<a class="button2" href="javascript:xoaform()"> <img style="top: -4px;" src="../images/button.png" alt="">Nhập lại
														</a></td>
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
									<legend class="legendtitle">Vị trí kho&nbsp;&nbsp;&nbsp;&nbsp; 
										<%if(quyen[0]!=0){ %>
										<a class="button3" href="javascript:newform()">
											<img style="top: -4px;" src="../images/New.png" alt=""> Tạo mới
										</a>
										<%} %>
									</legend>
									<table width="100%" border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td align="left" colspan="4">
												<table width="100%" border="0" cellspacing="1" cellpadding="4">
													<tr class="tbheader">
														<th width="10%" align="center">Mã vị trí</th>
														<th align="center">Diễn giải</th>
														<th width="20%" align="center">Thuộc khu vực</th>
														<th width="20%" align="center">Thuộc kho</th>
														<th width="10%" align="center">Trạng thái</th>
														<!-- <th width="10%" align="center">Ngày tạo</th>
														<th width="10%" align="center">Người tạo</th> -->
														<th width="10%" align="center">Ngày sửa</th>
														<th width="10%" align="center">Người sửa</th>
														<th width="10%" align="center">&nbsp;Tác vụ</th>
													</tr>
													<%int m = 0;
			String lightrow = "tblightrow";
			String darkrow = "tbdarkrow";
			if (vitriRs != null)
				while (vitriRs.next()) {
					String trangthai = vitriRs.getString("TrangThai");
					if (trangthai.equals("0"))
						trangthai = "Ngưng hoạt động";
					else
						trangthai = "Hoạt động";
					if (m % 2 != 0) {%>
													<tr class=<%=lightrow%>>
														<%} else {%>
													
													<tr class=<%=darkrow%>>
														<%}%>
														<td align="center"><%=vitriRs.getString("Ma")%></td>
														<td align="left"><%=vitriRs.getString("TEN")%></td>
														<td align="center"><%=vitriRs.getString("Khuvuc")%></td>
														<td align="center"><%=vitriRs.getString("Kho")%></td>
														<td align="center"><%=trangthai%></td>
														<%-- <td align="center"><%=vitriRs.getString("NgayTao")%></td>
														<td align="center"><%=vitriRs.getString("NguoiTao")%></td> --%>
														<td align="center"><%=vitriRs.getString("NgaySua")%></td>
														<td align="center"><%=vitriRs.getString("NguoiSua")%></td>
														<td align="center">
															<table border=0 cellpadding="0" cellspacing="0">
																<tr>
																	<td>
																	<%if(quyen[2]!=0){ %>
																	<a href="../../ErpViTriKhoUpdateSvl?userid=<%=userId%>&update=<%=vitriRs.getString("PK_SEQ")%>"><img
																			src="../images/Edit20.png" alt="Chinh sua" width="20" height="20" longdesc="Chinh sua" border=0></A>&nbsp;
																	<%} %>
																			</td>
																	<td>
																	<%if(quyen[1]!=0){ %>
																	<a href="../../ErpViTriKhoSvl?userid=<%=userId%>&delete=<%=vitriRs.getString("PK_SEQ")%>"><img
																			src="../images/Delete20.png" alt="Xoa" width="20" height="20" longdesc="Xoa" border=0
																			onclick="if(!confirm('Bạn muốn xóa vị trí này?')) return false;"> </a>&nbsp;
																			<%} %>
																			</td>
																	<td>
																	<!-- QUYEN VIEW DLN --> 
																	<a href="../../ErpViTriKhoUpdateSvl?userid=<%=userId%>&display=<%=vitriRs.getString("PK_SEQ")%>"><img src="../images/Display20.png" title="Hiển thị" alt="Hien thi" width="20" height="20" longdesc="Hien thi" border = 0></A>
																	<!-- END QUYEN VIEW DLN --> 
																			</td>
																</tr>
															</table>
													</tr>
													<%m++;
				}%>
													<tr>
														<td height="26" colspan="11" align="center" class="tbfooter">&nbsp;</td>
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
try{
	if(vitriRs != null) vitriRs.close(); 
	if(rsKho != null) rsKho.close();
	
	if (kvkList != null) kvkList.Close();
	session.removeAttribute("kvkList");
	session.setAttribute("kvkList", null) ; 
	
}catch (Exception ex)
{
	ex.printStackTrace();
}
}	
%>

