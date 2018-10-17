<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.sql.SQLException"%>
<%@ page import="java.sql.ResultSet"%>
<%@page import="geso.traphaco.erp.beans.doituongkhac.*"%>
<%@page import="geso.traphaco.erp.beans.doituongkhac.imp.*"%>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page import="geso.traphaco.center.util.Erp_Item"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%	
	System.out.println("");
	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	String action = (String)session.getAttribute("action");
	
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
<%
	IErpDoiTuongKhac obj = (IErpDoiTuongKhac) session.getAttribute("obj");
	List<Erp_Item> nppList;
	List<Erp_Item> chiNhanhList;
	List<Erp_Item> nganHangList;

	int[] quyen = new  int[5];
	quyen = util.Getquyen("ErpDoiTuongKhacUpdateSvl","",userId);
	NumberFormat format = new DecimalFormat("#,###,###");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Style-Type" content="text/css">
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
	<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
	<LINK rel="stylesheet" href="../css/main.css" type="text/css">
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
	<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
<title>Đối tượng khác</title>
<link rel="stylesheet" href="../css/main.css" type="text/css">
<link rel="stylesheet" href="../css/calendar.css" type="text/css">
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>
<script type="text/javascript" src="..scripts/jquery-1.js"></script>


<script language="javascript" type="text/javascript">
	function xoaform() {
		document.objForm.ma.value = "";
		document.objForm.ten.value = "";
		document.objForm.submit();
	}
	
	function Submit() {
		document.forms['objForm'].submit();
	}

	function Save() 
	{
		var maDoiTuong = document.getElementById('maDoiTuong');
		var tenDoiTuong = document.getElementById('tenDoiTuong');
		 if(maDoiTuong.value == "")
		 {
			 alert("Vui lòng nhập vào 'Mã đối tượng'");
			 maDoiTuong.focus();
			 return;
		 }
		 
		 if(tenDoiTuong.value == "")
		 {
			 alert("Vui lòng nhập vào 'Tên đối tượng'");
			 tenDoiTuong.focus();
			 return;
		 }
		
		document.forms['objForm'].action.value = 'save';
		document.forms['objForm'].submit();
	}
	
	    num = num.toString().replace(/\$|\,/g,'');
	function BoDuyet()
	{
		
		document.forms['objForm'].action.value = 'BoDuyet';
		document.forms['objForm'].submit();
	}
	
	function DinhDangTien(num) 
	{
	    if(isNaN(num))
	    num = "0";
	    sign = (num == (num = Math.abs(num)));
	    num = Math.floor(num*100+0.50000000001);
	    num = Math.floor(num/100).toString();
	    for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
	    num = num.substring(0,num.length-(4*i+3))+','+
	    num.substring(num.length-(4*i+3));
	    return (((sign)?'':'-') + num); 
	}
	 
	function Dinhdang(element)
	{
		element.value = DinhDangTien(element.value);
		if(element.value== '' )
		{
			element.value= '';
		}
	}	
	
	function kiemTraMST()
	{
		 var maSoThue = document.getElementById("mst").value;
		 var id = document.getElementById("Id").value;
		 
		if (mst != "")
		{
			var xmlhttp;
			if (window.XMLHttpRequest)
			{
			   xmlhttp = new XMLHttpRequest();
			}
			else
			{
			   xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
			}
				
			xmlhttp.onreadystatechange=function()
			{
			   if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
			   {
				   var resultStr = xmlhttp.responseText;
				   if (resultStr != "")
				   document.getElementById("msg").value = "'Mã số thuế' đã có trong hệ thống: " + xmlhttp.responseText;
			   }
			};
			xmlhttp.open("POST","../../AjaxKiemTraMaSoThueSvl?maSoThue=" + maSoThue + "&id=" + id, true);
			xmlhttp.send();
		}
	}
	
	function backButton() 
	{
		window.history.back();
	}
	
	function replaces()
	{
		var TenNh =  document.getElementsByName("tenNganHang");
		var NhId =  document.getElementsByName("nganHangId");
	
		if(TenNh.item(0).value != "" )
		{
			var temp = TenNh.item(0).value;
			var pos = parseInt(temp.indexOf(" - "));
			if(pos > 0 && temp.indexOf(" [ ")>0)
			{
				/// PK_SEQ VÀ nppTEN phân cách bằng ' - ', mã và tên phân cách bằng ' -- '
				
				NhId.item(0).value = temp.substring(0, pos);

				temp = temp.substr(parseInt(temp.indexOf(" [ ")) + 3);
				TenNh.item(0).value = temp.substring(0, parseInt(temp.indexOf(" ]")));
				
			}
		}
		else
		{
			NhId.item(0).value = "";
			TenNh.item(0).value = "";
		}
			
		var TenCN =  document.getElementsByName("tenChiNhanh");
		var CNId =  document.getElementsByName("chiNhanhId");
	
		if(TenCN.item(0).value != "" )
		{
			var temp = TenCN.item(0).value;
			var pos = parseInt(temp.indexOf(" - "));
			if(pos > 0 && temp.indexOf(" [ ")>0)
			{
				/// PK_SEQ VÀ nppTEN phân cách bằng ' - ', mã và tên phân cách bằng ' -- '
				
				CNId.item(0).value = temp.substring(0, pos);

				temp = temp.substr(parseInt(temp.indexOf(" [ ")) + 3);
				TenCN.item(0).value = temp.substring(0, parseInt(temp.indexOf(" ]")));
			
			}
		}
		else
		{
			CNId.item(0).value = "";
			TenCN.item(0).value = "";
		}
			
		setTimeout(replaces, 500);
	}
</script>

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { $("select").select2(); });
</script>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="objForm" method="post" action="../../ErpDoiTuongKhacUpdateSvl">
		<input type="hidden" name="userId" value='<%=userId%>' />
		<input type="hidden" name="id" id="id" value='<%=obj.getId() %>' /> 
		<input type="hidden" name="action" value='1' />
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td colspan="4" align='left' valign='top' bgcolor="#ffffff">
					<table width="100%" cellpadding="0" cellspacing="1">
						<tr>
							<td align="left" class="tbnavigation">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr height="22">
										<TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền &gt; Kế toán &gt; Đối tượng khác &gt; Cập nhật</TD>
										<td colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen%> &nbsp;
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
					<table width="100%" border="0" cellpadding="3" cellspacing="0">
						<tr>
							<td>
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr class="tbdarkrow">
										<td width="30" align="left">
										<a href="javascript:backButton();"> <img src="../images/Back30.png" alt="Quay ve" title="Quay ve"
												border="1" longdesc="Quay ve" style="border-style: outset">
										</a></td>
										<td width="2" align="left"></td>
										<%if (action != null && !action.trim().equals("display")) {%> 
										<td width="30" align="left">
											<a href="javascript: Save()">
												<img src="../images/Save30.png" title="Luu lai" alt="Luu lai" border="1" style="border-style: outset">
											</a>
										</td>
										<%} %>
										<td >&nbsp;</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
					
					<table width="100%" cellspacing="0" cellpadding="6">
						<tr>
							<td align="left" colspan="4" class="legendtitle">
								<fieldset>
									<legend class="legendtitle">Thông báo </legend>
									<textarea id="msg" name="dataerror" style="width: 100%" readonly="readonly" rows="1"><%=obj.getMsg()%></textarea>
								</fieldset>
							</td>
						</tr>
						<tr>
							<TD align="left" colspan="4" class="legendtitle">
								<fieldset>
									<LEGEND class="legendtitle">Đối tượng khác</LEGEND>
									<TABLE width="100%" cellspacing="0" cellpadding="6">
										<tr>
											<td width="15%" class="plainlabel">Mã đối tượng<font class="erroralert">*</font></td>
											<TD class="plainlabel"><input type="text" name="maDoiTuong" id="maDoiTuong" value="<%=obj.getMaDoiTuong()%>" /></TD>
											
											<TD class="plainlabel">Tên đối tượng</TD>
											<td class="plainlabel"><input type="text" name="tenDoiTuong" id="tenDoiTuong" value="<%=obj.getTenDoiTuong()%>" /></td>
										</tr>
										
										<tr>
											<td class="plainlabel">Tại Ngân hàng</td>
											<TD class="plainlabel">
												 <input type="text" id="tenNganHang" name="tenNganHang" value = "<%= obj.getTenNganHang() %>" style="width: 300px"  >  
			                    				 <input type="hidden" id="nganHangId" name="nganHangId" onChange="ChangeNganHang();" value = "<%= obj.getNganHangId() %>" style="width: 300px"  >
											</td>
											
											<TD class="plainlabel">Thuộc chi nhánh</TD>
											<TD class="plainlabel">
												 <input type="text" id="tenChiNhanh" name="tenChiNhanh" value = "<%= obj.getTenChiNhanh() %>" style="width: 300px"  >  
	                    						 <input type="hidden" id="chiNhanhId" name="chiNhanhId" value = "<%= obj.getChiNhanhId() %>" style="width: 300px"  >
											</TD>
										</tr>

										<tr>
										<td class="plainlabel">Địa chỉ</td>
											<td class="plainlabel"><input type="text" name="diaChi" id="diaChi" value="<%=obj.getDiaChi()%>" /></td>
											
											<TD class="plainlabel">Điện thoại</TD>
											<td class="plainlabel"><input type="text" name="dienThoai" id="dienThoai" value="<%=obj.getDienThoai()%>" /></td>
										</tr>
										
										<tr>
											<td class="plainlabel">Số tài khoản ngân hàng</td>
											<td class="plainlabel"><input type="text" name="sotaikhoan" id="sotaikhoan" value="<%=obj.getSoTaiKhoan()%>" /></td>
											
											<td class="plainlabel">Mã số thuế</td>
											<td class="plainlabel"><input type="text" name="maSoThue" id="maSoThue" value="<%=obj.getMaSoThue()%>" onchange="kiemTraMST();"/></td>
										</tr>

										<TR>
											<TD class="plainlabel">Tên người liên hệ</TD>
											<td class="plainlabel"><input type="text" name="tenNguoiLienHe" id="tenNguoiLienHe" value="<%=obj.getTenNguoiLienHe()%>" /></td>
										
											<TD class="plainlabel">Điện thoại người liên hệ</TD>
											<td class="plainlabel"><input type="text" name="dtNguoiLienHe" id="dtNguoiLienHe" value="<%=obj.getDtNguoiLienHe()%>" /></td>
										</TR>
										
										<TR>
											<TD class="plainlabel">Email người liên hệ</TD>
											<td class="plainlabel"><input type="text" name="emailNguoiLienHe" id="emailNguoiLienHe" value="<%=obj.getEmailNguoiLienHe()%>" /></td>	
											
											<TD class="plainlabel">Hoạt động</TD>
											<TD class="plainlabel">
											<% if(obj.getTrangThai().equals("1")) { %>
												<input type="checkbox" name="trangThai" value="1" checked = "checked">
											<% } else { %>
												<input type="checkbox" name="trangThai" value="1" >
											<% } %>
											</td>
										</TR>
										
										<TR>
											<TD class="plainlabel">Quản lý công nợ</TD>
											<TD class="plainlabel">
											<% if(obj.getQuanLyCongNo().equals("1")) { %>
												<input type="checkbox" name="quanLyCongNo" value="1" checked = "checked">
											<% } else {  %>
												<input type="checkbox" name="quanLyCongNo" value="1" >
											<% } %>
											</td>
												
											<TD class="plainlabel">Số Fax</TD>
											<td class="plainlabel"><input type="text" name="fax" id="fax" value="<%=obj.getFax()%>" /></td>	
										</tr>
										
										<TR>
										  <TD class="plainlabel" valign="top" width="150px"> Công ty / Chi nhánh</TD>
										  <TD class="plainlabel" valign="top" colspan="3">
										  	<select name="nppId" id="nppId" style="width: 500px;">
										  	<option value="0">All</option>
										<% if (obj.getNppList() != null)
										{
											for (Erp_Item item : obj.getNppList())
											{
												if (item.getValue().equals(obj.getNppId()))
												{
												%>
													<option value="<%=item.getValue()%>" selected="selected"><%=item.getName()%></option>
												<% } else { %>
													<option value="<%=item.getValue()%>"><%=item.getName()%></option>
										<% } }} %>
										  	</select>
										  </TD>
										</TR> 
									</table>
								</fieldset>
							</TD>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</form>

<script type="text/javascript">
	dropdowncontent.init('spId', "left-top", 300, "click");
	replaces();
	jQuery(function()
	{		
		$("#tenNganHang").autocomplete("ErpNganHangHOList.jsp");
		$("#tenChiNhanh").autocomplete("ErpChiNhanhHOList.jsp");
	});	
</script>
	
</body>
</html>
<%
}
%>