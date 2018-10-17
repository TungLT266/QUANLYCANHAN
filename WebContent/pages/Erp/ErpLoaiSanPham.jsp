<%@page import="java.sql.SQLException"%>
<%@ page import="java.sql.ResultSet"%>
<%@page import="geso.traphaco.center.util.Utility"%>
<%@page import="geso.traphaco.erp.beans.loaisanpham.*"%>
<%@page import="geso.traphaco.erp.beans.loaisanpham.imp.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String userId = (String) session.getAttribute("userId");
String userTen = (String) session.getAttribute("userTen");
String sum = (String) session.getAttribute("sum");
Utility util = (Utility) session.getAttribute("util");
if(!util.check(userId, userTen, sum)){
	response.sendRedirect("/TraphacoHYERP/index.jsp");
}else{ 

	IErpLoaiSanPham lspList = (IErpLoaiSanPham) session
			.getAttribute("lspList");

	ResultSet rsTaiKhoan = lspList.getTaiKhoanRs();
	ResultSet rsLSP = lspList.getLoaiSanPhamRs();
	
	 int[] quyen = new  int[5];
	 quyen = util.Getquyen("ErpLoaiSanPhamSvl","",userId);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Style-Type" content="text/css">
<title>Loại sản phẩm</title>
<link rel="stylesheet" href="../css/main.css" type="text/css">
<link rel="stylesheet" href="../css/calendar.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">

<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js"
	type="text/javascript"></script>
<script language="javascript" type="text/javascript">
	function xoaform() 
	{
		document.lspListForm.ma.value = "";
		document.lspListForm.ten.value = "";
		document.lspListForm.submit();
	}
	function Submit() {
		document.forms['lspListForm'].submit();
	}

	function newform() {
		document.forms['lspListForm'].action.value = 'New';
		document.forms['lspListForm'].submit();
	}
 	function thongbao() {
		var tt = document.forms["lspListForm"].msg.value;
		if (tt.length > 0)
			alert(document.forms["lspListForm"].msg.value);
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
	
	<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
	$(document).ready(function()
	{
		$(".select2").select2();
	});
</script>

<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>

</head>
<body leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">

	<form name="lspListForm" method="post" action="../../ErpLoaiSanPhamSvl">
		<input type="hidden" name="userId" value='<%=userId%>'> <input
			type="hidden" name="msg" value="<%=lspList.getMsg()%>"> <input
			type="hidden" name="userTen" value='<%=userTen%>'> <input
			type="hidden" name="action" value='1'>
		<input type="hidden" name="chixem" value='<%= lspList.getChixem() %>'>
		<script language="javascript" type="text/javascript">
			thongbao();
			</script>
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			height="100%">
			<tr>
				<td colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
					<table width="100%" cellpadding="0" cellspacing="1">
						<tr>
							<td align="left">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr height="22">
										<td align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền > Kế toán > Loại sản phẩm</td>
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
												<legend class="legendtitle">Tiêu chí tìm kiếm
													&nbsp;</legend>
												<table width="100%" cellpadding="6" cellspacing="0">
													<tr>
														<td width="15%" class="plainlabel" >Mã loại sản phẩm</td>
														<td class="plainlabel"><input type="text" name="ma"
															onchange="Submit()" value="<%=lspList.getMa()%>" />
													</tr>
													<tr>
														<td width="15%" class="plainlabel" >Tên loại sản phẩm</td>
														<td class="plainlabel"><input type="text" name="ten"
															onchange="Submit()" value="<%=lspList.getTen()%>" />
													</tr>
													<tr style="display: none">
														<td class="plainlabel">Thuộc tài khoản kế toán</td>
														<TD class="plainlabel"><select name="taikhoan"
															onchange="Submit()">
																<option value=""></option>
																<%if (rsTaiKhoan != null)
				while (rsTaiKhoan.next()) {
					if (lspList.getTaiKhoan().equals(
							rsTaiKhoan.getString("PK_SEQ"))) {%>
																<option value="<%=rsTaiKhoan.getString("PK_SEQ")%>"
																	selected="selected"><%=rsTaiKhoan.getString("Ten")%></option>
																<%} else {%>
																<option value="<%=rsTaiKhoan.getString("PK_SEQ")%>"><%=rsTaiKhoan.getString("Ten")%></option>
																<%}
				}%>
														</select></td>
													</tr>

													<tr>
														<td colspan="2" class="plainlabel">&nbsp;&nbsp; <a class="button2"
															href="javascript:xoaform()"> <img style="top: -4px;"
																src="../images/button.png" alt="">Nhập lại
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
									 <legend class="legendtitle">
										&nbsp;&nbsp; 
											<%if(quyen[0]!=0){ %>
											<a class="button3" href="javascript:newform()">
												<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới
											</a>
											<%} %>
									</legend>
									<table width="100%" border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td align="left" colspan="4">
												<table width="100%" border="0" cellspacing="1"
													cellpadding="4">
													<tr class="tbheader">
														<th width="10%" align="center">Mã loại</th>
														<th width="20%" align="center">Tên loại</th>
														<th width="12%" align="center">Trạng thái</th>
														<th width="10%" align="center">Ngày tạo</th>
														<th width="13%" align="center">Người tạo</th>
														<th width="10%" align="center">Ngày sửa</th>
														<th width="13%" align="center">Người sửa</th>
														<th width="13%" align="center">Tác vụ</th>
													</tr>
													<%int m = 0;
			String lightrow = "tblightrow";
			String darkrow = "tbdarkrow";
			if (rsLSP != null)
				while (rsLSP.next()) {
					String trangthai = rsLSP.getString("TrangThai");
					if (trangthai.equals("0"))
						trangthai = "Ngưng hoạt động";
					else
						trangthai = "Hoạt động";
					if (m % 2 != 0) {%>
													<tr class=<%=lightrow%>>
														<%} else {%>
													
													<tr class=<%=darkrow%>>
														<%}%>
														<td align="center"><%=rsLSP.getString("Ma")%></td>
														<td align="left"><%=rsLSP.getString("Ten")%></td>
														<td align="center" style="display: none"><%=rsLSP.getString("TaiKhoan")%></td>
														<td align="center"><%=trangthai%></td>
														<td align="center"><%=rsLSP.getString("NgayTao")%></td>
														<td align="left"><%=rsLSP.getString("NguoiTao")%></td>
														<td align="center"><%=rsLSP.getString("NgaySua")%></td>
														<td align="left"><%=rsLSP.getString("NguoiSua")%></td>

														<td align="center">
															<table border=0 cellpadding="0" cellspacing="0">
																<tr>
																	<td>
																		<%if(quyen[2]!=0 && lspList.getChixem().equals("0") ){ %>
																	<a
																		href="../../ErpLoaiSanPhamUpdateSvl?userid=<%=userId%>&update=<%=rsLSP.getString("PK_SEQ")%>"><img
																			title="Cập nhật" src="../images/Edit20.png" alt="Chinh sua" width="20"
																			height="20" longdesc="Chinh sua" border=0></A>&nbsp;
																			<%} %>
																	</td>
																	<td>
																	<%if(quyen[1]!=0 && lspList.getChixem().equals("0") ){ %>
																		<a
																		href="../../ErpLoaiSanPhamSvl?userid=<%=userId%>&delete=<%=rsLSP.getString("PK_SEQ")%>"><img
																			title="Xóa" src="../images/Delete20.png" alt="Xoa" width="20"
																			height="20" longdesc="Xoa" border=0
																			onclick="if(!confirm('Bạn muốn xóa loại sản phẩm này?')) return false;">
																		</a>&nbsp;
																	<%} %>
																	</td>
																	<td>
																	<!-- QUYEN VIEW DLN -->
																	<a href="../../ErpLoaiSanPhamUpdateSvl?userid=<%=userId%>&display=<%=rsLSP.getString("PK_SEQ")%>">
																	<img src="../images/Display20.png" title="Hiển thị" alt="Hien thi" width="20" height="20" longdesc="Hien thi" border = 0></A>
																	<!-- END QUYEN VIEW DLN -->
																	</td>
																</tr>
															</table>
													</tr>
													<%m++;
				}%>
													<tr>
														<td height="26" colspan="11" align="center"
															class="tbfooter">&nbsp;</td>
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
	<%lspList.Close();%>
</body>
</html>
<%} %>
