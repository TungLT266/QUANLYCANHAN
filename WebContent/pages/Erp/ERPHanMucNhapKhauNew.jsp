<%@page import="java.sql.SQLException"%>
<%@ page import="java.sql.ResultSet"%>
<%@page import="geso.traphaco.erp.beans.hanmucnhapkhau.IErpHanMucNhapKhau"%>
<%@page import="geso.traphaco.erp.beans.hanmucnhapkhau.imp.ErpHanMucNhapKhau"%>
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
	IErpHanMucNhapKhau lhmnk = (IErpHanMucNhapKhau) session.getAttribute("obj");
	ResultSet Rslistsp = lhmnk.getListSanPham();
	 int[] quyen = new  int[5];
		quyen = util.Getquyen("ErpHanMucNhapKhauUpdateSvl","",userId);
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Style-Type" content="text/css">
<title>Loại tài khoản kế toán</title>
<link rel="stylesheet" href="../css/main.css" type="text/css">
<link rel="stylesheet" href="../css/calendar.css" type="text/css">
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<script src="../scripts/ui/jquery.ui.datepicker.js"
		type="text/javascript"></script>
		 <link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
<script type="text/javascript">
		$(document).ready(function() {		
				$( ".days" ).datepicker({			    
						changeMonth: true,
						changeYear: true,
						yearRange: "2015:2030"
				});            
	        }); 		
		$(document).ready(function()
				{
				 $("#listsanpham").select2();
				
				});
	</script>
<script language="javascript" type="text/javascript">
	// ham chi cho phep nhap so
	function isNumberKey(evt)
	{
	   var charCode = (evt.which) ? evt.which : event.keyCode;
	   if(charCode == 59 || charCode == 46)
	    return true;
	   if (charCode > 31 && (charCode < 48 || charCode > 57))
	      return false;
	   return true;
	}
	// ham kiem tra du lieu nhap vao
	function ValidateForm()
	 {

		
		var sanpham=document.getElementById('listsanpham');
		if (sanpham.value.trim().length == 0) {
			alert("Chọn sản phẩm");
			sanpham.focus();
			return false;
		}
		
		var soluong=document.getElementById('soluong');
		if ((soluong.value.trim().length == 0) || (soluong.value==0)) {
			alert("Nhập số lượng");
			soluong.focus();
			return false;
		}
		
		var tungay=document.getElementById('tungay');
		if (tungay.value.trim().length == 0) {
			alert("Nhập từ ngày");
			tungay.focus();
			return false;
		}
		
		var denngay=document.getElementById('denngay');
		if (denngay.value.trim().length == 0) {
			alert("Nhập đến ngày");
			denngay.focus();
			return false;
		}
		if(((Date.parse(tungay.value))>(Date.parse(denngay.value))))
			{
				alert("Nhập đến ngày phải lớn hơn từ ngày");
				denngay.focus();
				return false;
			}
		return true;
	 }
	function saveform() 
	{
		 var check = ValidateForm();
		 if(check == false)
			 {
			 	return false;
			 }
		document.forms['HanMucNhapKhauForm'].action.value = 'save';
		document.forms['HanMucNhapKhauForm'].submit();

	}
	function msg(){
		if (!msg.equal("")){
			alert(msg);
		}
	}
	
	function thongbao()
	{
		if(document.getElementById("msg").value != ''&& document.getElementById("msg").value!=null)
		{alert(document.getElementById("msg").value);
		}
	}
</script>
</head>
<body leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="HanMucNhapKhauForm" method="post" action="../../ErpHanMucNhapKhauUpdateSvl">
		<input type="hidden" name="userId" value='<%=userId%>'> <input type="hidden" name="userTen" value='<%=userTen%>'> <input
			type="hidden" name="action" value='1'>
			<input type="hidden" name="id" value="<%=lhmnk.getId()%>">
			<input type="hidden" id="msg" value="<%=lhmnk.getMsg() %>">
			
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
										<td align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền &gt; Mua hàng &gt;Tạo mới hạn mức nhập khẩu  </td>
										<td align="right" colspan="2" class="tbnavigation">Chào mừng bạn <%=userTen%>&nbsp;
										</td>
									</tr>
							</table>
							</td>
						</tr>
						<tr>
							<td>
							<table width="100%" border="0" cellpadding="3" cellspacing="0">
						<tr>
							<td>
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr class="tbdarkrow">
										<td width="30" align="left"><a href="../../ErpHanMucNhapKhauSvl"> <img src="../images/Back30.png" alt="Quay ve" title="Quay ve"
												border="1" longdesc="Quay ve" style="border-style: outset">
										</a></td>
										<td width="2" align="left"></td>
										<td width="30" align="left"><a href="javascript: saveform()"><img src="../images/Save30.png" title="Luu lai" alt="Luu lai" border="1"
												style="border-style: outset"></a></td>
										<td>&nbsp;</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
							</td>
						</tr>
						<tr>
							<td>
								<table border="0" width="100%" cellpadding="0" cellspacing="0">
								<tr>
									<td align="left" colspan="4" class="legendtitle">
										<fieldset>
											<legend class="legendtitle">Thông báo </legend>
											<textarea name="dataerror" style="width: 100%" readonly="readonly" rows="1"><%= lhmnk.getMsg()%></textarea>
										</fieldset>
									</td>
								</tr>
									<tr>
										<td width="100%" align="center">
											<fieldset>
												<legend class="legendtitle">Tạo mới hạn mức nhập khẩu &nbsp; &nbsp; &nbsp;</legend>
												<table width="100%" cellpadding="6" cellspacing="0">
													<tr>
														<td class="plainlabel">Sản phẩm</td>
														<TD class="plainlabel" >
									                      <select name="listsanpham" id="listsanpham" style="width:200px" onchange="Search()" class="select2" style="width:200px">
									                      <option value=""></option>
									                       <%
									                        if(Rslistsp!= null)
									                        {   	 
									                         while(Rslistsp.next())
									                         {
										                          if(Rslistsp.getString("PK_SEQ").equals(lhmnk.getSanPham_Fk()))
										                          {
										                         %>
										                         <option selected="selected" value="<%= Rslistsp.getString("PK_SEQ") %>"><%= Rslistsp.getString("MA")+"-"+Rslistsp.getString("TEN") %></option>
										                         <%} else { 
										                         %>
										                          	<option value="<%= Rslistsp.getString("PK_SEQ") %>"><%= Rslistsp.getString("MA")+"-"+Rslistsp.getString("TEN") %></option>
										                          <%} %>
										                         <%} %>
										                        <% Rslistsp.close(); }%>
									                                          
									                  		 </select>     
								                        </TD>
								                        <td class="plainlabel">Số lượng/Khối lượng</td>
														<TD class="plainlabel"><input type="text" name="soluong" id="soluong" value='<%=lhmnk.getSoluong()%>' onkeypress="return isNumberKey(event)"></td>
													</tr>
													<tr>
														<td class="plainlabel">Từ ngày</td>
														<TD class="plainlabel"><input type="text" name="tungay" id="tungay" value='<%=lhmnk.getTuNgay()%>' class="days"></td>
														<td class="plainlabel">Đến ngày</td>
														<TD class="plainlabel"><input type="text" name="denngay" id="denngay" value='<%=lhmnk.getDenNgay()%>' class="days"></td>
													</tr>
		
													<tr>
														<td class="plainlabel">Số đăng ký</td>
														<TD class="plainlabel" colspan="3"><input type="text" name="soDangKy" id="soDangKy" value='<%=lhmnk.getSoDangKy()%>'></td>
													</tr>
												</table>
											</fieldset>
										</td>
									</tr>
								</table>
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
	}
%>
