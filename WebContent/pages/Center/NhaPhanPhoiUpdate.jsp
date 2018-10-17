<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.beans.nhaphanphoi.INhaphanphoi" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
<% INhaphanphoi nppBean = (INhaphanphoi)session.getAttribute("nppBean"); %>
<% ResultSet tp = (ResultSet)nppBean.getTp() ; %>
<% ResultSet qh = (ResultSet)nppBean.getQh() ; %>
<% ResultSet kv = (ResultSet)nppBean.getKhuvuc(); %>
<% ResultSet rs_khott = (ResultSet)nppBean.getrs_khott(); %>
<% ResultSet tructhuocRs = (ResultSet)nppBean.getTructhuoc(); %>
<% ResultSet rs_gsbh = (ResultSet)nppBean.getrs_gsbh(); %>
<% ResultSet rs_nvbh = (ResultSet)nppBean.getrs_nvbh(); %>
<% ResultSet rs_loaicn = (ResultSet)nppBean.getLoaiCNRs();
	NumberFormat formatter=new DecimalFormat("#,###,###"); 
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>TraphacoERP - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>

<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">

<SCRIPT language="javascript" type="text/javascript">

 function submitform()
 {   
    document.forms["nppForm"].submit();
 }

 function saveform()
 {
	/*  var ma = document.getElementById('maSAP'); */
	 var ten = document.getElementById('nppTen');
	 var diachi = document.getElementById('DiaChi');
	 var tp = document.getElementById('TP');
	 var qh = document.getElementById('QH');
	 var khuvuc = document.getElementById('KhuVuc');
	// var gsbhId = document.getElementById('gsbhId');	 
	 var isKH = document.getElementById('isKH').value;
	 var xuattaikho = document.getElementById('xuattaikho');
	 
	 /* if(ma.value == "")
	 {
		 alert('Vui lòng nhập mã Chi nhánh / Đối tác');
		 return;
	 } */
	 
	 if(ten.value == "")
	 {
		 alert('Vui lòng nhập tên Chi nhánh / Đối tác');
		 return;
	 }
	 if(diachi.value == "")
	 {
		 alert('Vui lòng nhập địa chỉ');
		 return;
	 }	

	 if(tp.value == "")
	 {
		 alert('Vui lòng chọn thành phố');
		 return;
	 }	
	 
	 if(qh.value == "")
	 {
		 alert('Vui lòng chọn quận huyện');
		 return;
	 }	

/* 	 if(gsbhId.value == "")
	 {
		 alert('Vui lòng chọn phụ trách tỉnh / GĐCN');
		 return;
	 }	 */
	 
	 if(khuvuc.value == "")
	 {
		 alert('Vui lòng chọn khu vực');
		 return;
	 }	 
	 
	 if(isKH == '0')
	 {
		 var tructhuocId = document.getElementById('tructhuocId');
		 if(tructhuocId.value == "")
		 {
			 alert('Vui lòng chọn đơn vị trực thuộc');
			 return;
		 }	
	 }
	  
	 if(isKH == '1')
	 {
		 var nvbhId = document.getElementById('nvbhId');
		 if(nvbhId.value == "")
		 {
			 alert('Vui lòng chọn trình dược viên');
			 return;
		 }	
	 }
	 
	 if(xuattaikho.value == "")
	 {
		 alert('Vui lòng nhập xuất tại kho');
		 return;
	 }	 
	 
	 
 	 document.forms['nppForm'].action.value='save';
     document.forms['nppForm'].submit();
 }
 
 	function DinhDang()
	{
		var sotien = document.getElementById("hanmucno").value.replace(/,/g,"");
		document.getElementById("hanmucno").value= DinhDangTien(sotien);
		
		var songayno = document.getElementById("songayno").value.replace(/,/g,"");
		document.getElementById("songayno").value= DinhDangTien(songayno);
	}
	
	function DinhDangTien(num) 
	 {
	    num = num.toString().replace(/\$|\,/g,'');
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
			element.value=DinhDangTien(element.value);
			if(element.value== '' ||element.value=='0' )
			{
				element.value= '';
			}
		}
	
</SCRIPT>

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
	$(document).ready(function()
	{
		$(".select2").select2();
		
	});
</script>

<style type="text/css">
	input
	{
		padding: 2px 0px;
	}
</style>

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="nppForm" method="post" action="../../NhaphanphoiUpdateSvl">
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="isKH" id="isKH" value='<%= nppBean.getIsKhachhang() %>'>
<input type="hidden" name="id"  value='<%= nppBean.getId() %>'>
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Thiết lập dữ liệu nền &gt; Dữ liệu nền kinh doanh &gt; <%= nppBean.getIsKhachhang().equals("1") ? "Khách hàng" : "Chi nhánh / Đối tác" %> &gt; Cập nhật</TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;  </TD> 
						  </tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
					<TR class = "tbdarkrow">
						<TD width="30" align="center"><A href="../../NhaphanphoiSvl?userId=<%=userId %>&isKH=<%= nppBean.getIsKhachhang() %>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" width="30" height="30" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
					    <TD width="2" align="left" ></TD>
					    <TD width="30" align="left" ><A href="javascript:saveform()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A></TD>
				    	<TD align="left" >&nbsp;</TD>
					</TR>
			</TABLE>
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="0">
			  	<tr>
					<TD align="left" colspan="6" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>
	    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold"  style="width:100%" rows="1" ><%= nppBean.getMessage() %></textarea>
                                      <%nppBean.setMessage(""); %>
						</FIELDSET>
				   </TD>
				</tr>

				  <TR>
				  <TD height="100%" width="100%">
						<FIELDSET>
						<LEGEND class="legendtitle" style="color:black">Thông tin <%= nppBean.getIsKhachhang().equals("1") ? "Khách hàng" : "Chi nhánh / Đối tác" %> </LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
							<TR>
								<TD class="plainlabel" width="130px"  >Mã FAST</TD>
								<TD class="plainlabel" width="220px" >
									<INPUT name="maSAP" id="maSAP" type="text" value="<%= nppBean.getMaSAP() %>" >
								</TD>
								<TD class="plainlabel" width="140px" > Tên <FONT class="erroralert"> *</FONT></TD>
                                <TD class="plainlabel"  >
                                	<INPUT name="nppTen" id="nppTen" type="text" value="<%= nppBean.getTen() %>" >
                                </TD>
                                
                                <TD class="plainlabel" width="140px" >Xuất tại kho<FONT class="erroralert"> *</FONT></TD>
                                <TD class="plainlabel" >
                                	<INPUT name="xuattaikho" id="xuattaikho" type="text" value="<%= nppBean.getXuattaikho() %>" >
                                </TD>
                                
							</TR>
							<TR>
							  	<TD class="plainlabel">Địa chỉ giao hàng <FONT class="erroralert"> *</FONT></TD>
                                <TD class="plainlabel" >
                                	<INPUT name="DiaChi" id="DiaChi" type="text" value="<%= nppBean.getDiachi() %>"  >
                                </TD>
                                
							   	 <TD class="plainlabel">Tỉnh/Thành phố <FONT class="erroralert"> *</FONT></TD>
								 <TD class="plainlabel" width="220px" >
								 	<SELECT name="tpId" id="TP" onChange="submitform();" class="select2" style="width: 200px;" >
								    	<option value=""></option>
								      	<% if(tp!=null)
								      	try{while(tp.next()){ 
								    	if(tp.getString("tpId").equals(nppBean.getTpId())){ %>
								      		<option value='<%=tp.getString("tpId")%>' selected><%=tp.getString("tpTen") %></option>
								      	<%}else{ %>
								     		<option value='<%=tp.getString("tpId")%>'><%=tp.getString("tpTen") %></option>
								     	<%}}}catch(java.sql.SQLException e){} %>	  
	                       			</SELECT>	
	                       		</TD>
	
							   	 <TD class="plainlabel">Quận/Huyện <FONT class="erroralert"> *</FONT></TD>
								 <TD class="plainlabel" >
								 	<SELECT name="qhId" id="QH" class="select2" style="width: 200px;">
								    	<option value=""></option>
									      <%if(qh != null){ 
									      		try{while(qh.next()){ 
									    			if(qh.getString("qhId").equals(nppBean.getQhId())){ %>
									      				<option value='<%=qh.getString("qhId")%>' selected><%=qh.getString("qhTen") %></option>
									      		 <%}else{ %>
									     				<option value='<%=qh.getString("qhId")%>'><%=qh.getString("qhTen") %></option>
									     		<%}}}catch(java.sql.SQLException e){} 
									      }	%>	  
	                       			</SELECT>	
	                       		</TD>	
						  </TR>

						  <TR>
						  		<TD class="plainlabel">Địa chỉ xuất HĐ </TD>
                                <TD  class="plainlabel" ><INPUT name="diachixhd" id="diachixhd" type="text" value="<%= nppBean.getDiaChiXuatHoaDon() %>"  ></TD>
                                
                                <td class="plainlabel">Người đại diện trên HĐ</td>
                                <td class="plainlabel" >
                                	<INPUT name="ddthd" id="ddthd" type="text" value="<%= nppBean.getTenNguoiDaiDien() %>">
                                </td>
                                <TD class="plainlabel">Mã số thuế </TD>
                                <TD class="plainlabel"><INPUT name="masothue" id="masothue" type="text" value="<%= nppBean.getmaSoThue() %>" ></TD>
						  </TR>	
						   <TR>
                                
						  		<TD class="plainlabel">Điện thoại </TD>
                                <TD  class="plainlabel">
                                	<INPUT name="DienThoai" id="DienThoai" type="text" value="<%= nppBean.getSodienthoai() %>" >
                                </TD>
                                <td class="plainlabel">FAX</td>
						  		<TD class="plainlabel"><input name="fax" id="fax" type="text" value="<%= nppBean.getFAX() %>"></TD>
							  	
						  		<TD class="plainlabel">Email </TD>
                        		<TD class="plainlabel"><input name="email" id="email" type="text" value="<%= nppBean.getEmail() %>"></TD>
						  </TR>	
						  <TR>
							   	<td class="plainlabel">Hình thức TT</td>
                        		<td class="plainlabel"><input type="text" name="httt" id="httt" value="<%= nppBean.getHinhThucThanhToan() %>"></td>
                        		
                        		<TD class="plainlabel">Tên ngân hàng</TD>
								<TD class="plainlabel"><INPUT name="nganhang" id="nganhang" value="<%= nppBean.getNganHang() %>" type="text"></TD>	
								<TD class="plainlabel">Số tài khoản</TD>
								<TD class="plainlabel"><INPUT type="text" name="sotaikhoan" id="sotaikhoan" value="<%= nppBean.getSoTK() %>"></TD>
							</TR>
							
							<%//Khai bao Ky hieu hoa don %>
							<TR>
								<td class="plainlabel">Ký hiệu hóa đơn</td>
                        		<td class="plainlabel">
                        			<input type="text" name="kyhieuhd" id="kyhieuhd" value="<%= nppBean.getKyhieuHD() %>">
                        		</td>
                        		<td class="plainlabel">Số hóa đơn : Từ :</td>
                        		<td class="plainlabel">
                        			<input type="text" name="soHDTu" id="soHDTu" value="<%= nppBean.getSoHDTu() %>" >
                        		</td>
                        		<td class="plainlabel">Đến :</td>
                        		<td class="plainlabel">
                        			<input type="text" name="soHDDen" id="soHDDen" value="<%= nppBean.getSoHDDen() %>" >
                        		</td>
							</TR>
							
							<TR>
								<td class="plainlabel">Mã kho</td>
                        		<td class="plainlabel">
                        			<input type="text" name="makho" id="makho" value="<%= nppBean.getMaKho() %>">
                        		</td>
                        		<td class="plainlabel">Mã nhập xuất</td>
                        		<td class="plainlabel">
                        			<input type="text" name="manx" id="manx" value="<%= nppBean.getMaNX() %>" >
                        		</td>
                        		<td class="plainlabel">CMTND</td>
                        		<td class="plainlabel">
                        			<input type="text" name="cmnd" id="cmnd" value="<%= nppBean.getCMTND() %>" >
                        		</td>
							</TR>
							
							
						  <TR>
						  		<%-- <TD class="plainlabel" >Phụ trách tỉnh / GĐCN</TD>
								<TD class="plainlabel" >
									<SELECT name="gsbhId" id="gsbhId" class="select2" style="width: 200px;" >
								    <option value=""></option>
								      <% if(rs_gsbh != null) 
								      try{while(rs_gsbh.next()){ 
								    	if(rs_gsbh.getString("pk_seq").trim().equals(nppBean.getGsbhId().trim())){ %>
								      		<option value='<%=rs_gsbh.getString("pk_seq")%>' selected><%=rs_gsbh.getString("ten") %></option>
								      	<%}else{ %>
								     		<option value='<%=rs_gsbh.getString("pk_seq")%>'><%=rs_gsbh.getString("ten") %></option>
								     	<%}}}catch(java.sql.SQLException e){} %>	  
                        			</SELECT>	
								</TD> --%>
						  		<TD class="plainlabel">Khu vực <FONT class="erroralert"> *</FONT></TD>
								 <TD class="plainlabel">
								 	<SELECT name="kvId" id="KhuVuc" class="select2" style="width: 200px;">
								    <option value=""></option>
								      <%
								      if(kv!=null)
								      try{while(kv.next()){ 
								    	if(kv.getString("kvId").equals(nppBean.getKvId())){ %>
								      		<option value='<%=kv.getString("kvId")%>' selected><%=kv.getString("kvTen") %></option>
								      	<%}else{ %>
								     		<option value='<%=kv.getString("kvId")%>'><%=kv.getString("kvTen") %></option>
								     	<%}}}catch(java.sql.SQLException e){} %>	  
                        				</SELECT>	
                        		</TD>
							   	 <% if( nppBean.getLoaiNPP().equals("4") || nppBean.getLoaiNPP().equals("5") ) { %>
                        		<TD class="plainlabel">Kho đặt hàng </TD>
								 <TD class="plainlabel" >
								 	<SELECT name="khottid" id="khottid" class="select2" style="width: 200px;" >
								      <% if(rs_khott!=null) 
								      try{while(rs_khott.next()){ 
								    	if(rs_khott.getString("pk_seq").trim().equals(nppBean.getKhoTT().trim())){ %>
								      		<option value='<%=rs_khott.getString("pk_seq")%>' selected><%=rs_khott.getString("ten") %></option>
								      	<%}else{ %>
								     		<option value='<%=rs_khott.getString("pk_seq")%>'><%=rs_khott.getString("ten") %></option>
								     	<%}}}catch(java.sql.SQLException e){
								     		e.printStackTrace();
								     	} %>	  
                        			</SELECT>	
                        		</TD>
                        		<% } else { %>
                        			<td class="plainlabel" colspan="2" >	
                        				<input type="hidden" value="100001" >
                        			</td>
                        		<% } %>
                        		
                        		<Td class="plainlabel" colspan="2" >
                        			
                        			<% if(nppBean.getLoaiNPP().equals("5")) { %>
	                                
		                                Quản lý tồn kho 
		                               	<%  if (nppBean.getPriSec().equals("1")){%>
		                                      <input name="prisec" type="checkbox" value ="1" checked>
		                                <%} else {%>
		                                       <input name="prisec" type="checkbox" value ="0">
		                                <%} %>    
		                                
	                                <% } else { %>
	                                	
	                                	Tự chốt xuất kho OTC 
		                               	<%  if (nppBean.getPriSec().equals("1")){%>
		                                      <input name="prisec" type="checkbox" value ="1" checked>
		                                <%} else {%>
		                                       <input name="prisec" type="checkbox" value ="1">
		                                <%} %> 
		                                
		                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		                                
		                                Tự xuất kho ETC 
		                               	<%  if (nppBean.getTuxuatkhoETC().equals("1")){%>
		                                      <input name="tuxuatETC" type="checkbox" value ="1" checked>
		                                <%} else {%>
		                                       <input name="tuxuatETC" type="checkbox" value ="1">
		                                <%} %> 
		                                  <br />
		                                
		                                Tự tạo hóa đơn  
		                               	<%  if (nppBean.getTutaohoadonOTC().equals("1")){%>
		                                      <input name="tutaohoadon" type="checkbox" value ="1" checked>
		                                <%} else {%>
		                                       <input name="tutaohoadon" type="checkbox" value ="1">
		                                <%} %> 
		                                
		                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		                                
		                                Không phân biệt kênh OTC và ETC  
		                               	<%  if (nppBean.getDungchungkenh().equals("1")){%>
		                                      <input name="dungchungkenh" type="checkbox" value ="1" checked>
		                                <%} else {%>
		                                       <input name="dungchungkenh" type="checkbox" value ="1">
		                                <%} %> 
	                                <% } %>
                        			
                        		</Td>
                        		
							</TR>
							
							<% if(nppBean.getIsKhachhang().equals("0")) { %>
							<TR>
								<TD class="plainlabel" style="color: red;" >Loại</TD>
								<TD class="plainlabel" >
									<select name="loaiNPP" class="select2" style="width: 200px;color: red;" onChange="submitform();" >
										<option value="" ></option>
										<% if(nppBean.getLoaiNPP().equals("0") ) { %>
											<option value="0" selected="selected" >Chi nhánh cấp 1</option>
										<% } else { %>
											<option value="0" >Chi nhánh cấp 1</option>
										<% } %>
										
										<% if(nppBean.getLoaiNPP().equals("1") ) { %>
											<option value="1" selected="selected" >Chi nhánh cấp 2</option>
										<% } else { %>
											<option value="1" >Chi nhánh cấp 2</option>
										<% } %>
										
										<% if(nppBean.getLoaiNPP().equals("2") ) { %>
											<option value="2" selected="selected" >Quầy bán buôn</option>
										<% } else { %>
											<option value="2" >Quầy bán buôn</option>
										<% } %>
										
										<% if(nppBean.getLoaiNPP().equals("3") ) { %>
											<option value="3" selected="selected" >Quầy TraphacoDMS</option>
										<% } else { %>
											<option value="3" >Quầy TraphacoDMS</option>
										<% } %>
										
										<% if(nppBean.getLoaiNPP().equals("4") ) { %>
											<option value="4" selected="selected" >Đối tác</option>
										<% } else { %>
											<option value="4" >Đối tác</option>
										<% } %>
										
										<% if(nppBean.getLoaiNPP().equals("5") ) { %>
											<option value="5" selected="selected" >Chi nhánh đối tác</option>
										<% } else { %>
											<option value="5" >Chi nhánh đối tác</option>
										<% } %>
									</select>
								</TD>
								
								<TD class="plainlabel" >Trực thuộc</TD>
								<TD class="plainlabel"  >
									<select name="tructhuocId" id="tructhuocId" class="select2" style="width: 200px;" >
										<option value="" ></option>
										<% if(tructhuocRs != null) { 
											while(tructhuocRs.next())
											{
												if(tructhuocRs.getString("Id").equals(nppBean.getTructhuocId())) { %>
													<option value="<%= tructhuocRs.getString("Id") %>" selected="selected" ><%= tructhuocRs.getString("TEN") %></option>
												<% }
												else { %>
													<option value="<%= tructhuocRs.getString("Id") %>" ><%= tructhuocRs.getString("TEN") %></option>
												<% }
											}
											tructhuocRs.close();
										} %>
									</select>
								</TD>
								
								<TD class="plainlabel" colspan="2" >
                                	Hoạt động 
                                	<%  if (nppBean.getTrangthai().equals("1")){%>
	                                      <input name="TrangThai" type="checkbox" value ="1" checked>
	                                <%} else {%>
	                                       <input name="TrangThai" type="checkbox" value ="0">
	                                <%} %>  
	                                      
                                </TD>
															
							</TR>
							<% } else { %>
								
								<TR>
								
								<TD class="plainlabel" >Trình dược viên</TD>
								<TD class="plainlabel"  >
									<select name="nvbhId" id="nvbhId" class="select2" style="width: 200px;" >
										<option value="" ></option>
										<% if(rs_nvbh != null) { 
											while(rs_nvbh.next())
											{
												if(rs_nvbh.getString("Id").equals(nppBean.getNvbhId())) { %>
													<option value="<%= rs_nvbh.getString("Id") %>" selected="selected" ><%= rs_nvbh.getString("TEN") %></option>
												<% }
												else { %>
													<option value="<%= rs_nvbh.getString("Id") %>" ><%= rs_nvbh.getString("TEN") %></option>
												<% }
											}
											rs_nvbh.close();
										} %>
									</select>
								</TD>
								
								<TD class="plainlabel" colspan="4" >
                                	Hoạt động 
                                	<%  if (nppBean.getTrangthai().equals("1")){%>
	                                      <input name="TrangThai" type="checkbox" value ="1" checked>
	                                <%} else {%>
	                                       <input name="TrangThai" type="checkbox" value ="0">
	                                <%} %>  
	                                                            
                                </TD>
								
							</TR>
								
							<% } %>
							
							<% if(true || nppBean.getLoaiNPP().equals("0") || nppBean.getLoaiNPP().equals("4")) { %>
							<tr class="plainlabel">
							 <!-- <TD class="plainlabel">Loại công nợ </TD> -->
                               <!-- <TD  class="plainlabel" >
                               	<SELECT name ="trangthai" onChange = "submitform();">
                                         <option value="1" class="select2"  selected>Công nợ theo hạn mức</option>
                                   </SELECT>     --> 
                                  <%--  <% if (nppBean.getLoaiCN().equals("1")){%>
                                 		  <option value=""></option>
                                         <option value="1" selected>Công nợ tối đa</option>
                                         <option value="0">Công nợ theo mức trần</option>
                                         <option value="2">Công nợ theo hạn mức</option>
                                         
                                   <%}else if(nppBean.getLoaiCN().equals("0")) {%>
                                         <option value="" > </option>
                                         <option value="1" >Công nợ tối đa</option>
                                         <option value="0" selected>Công nợ theo mức trần</option>
                                         <option value="2">Công nợ theo hạn mức</option>
                                         
                                   <%}else if(nppBean.getLoaiCN().equals("2")) {%>                                                                                        
                                        <option value=""></option>
                                         <option value="1" >Công nợ tối đa</option>
                                         <option value="0">Công nợ theo mức trần</option>
                                         <option value="2" selected>Công nợ theo hạn mức</option>
                                   <%}else {%>
                                   	  <option value="" selected></option>
                                         <option value="1" >Công nợ tối đa</option>
                                         <option value="0">Công nợ theo mức trần</option>
                                         <option value="2">Công nợ theo hạn mức</option>
                                   <%} %> --%>                                        
                                <!--  </TD>   -->
								<TD class="plainlabel">Hạn mức nợ </TD>
								<TD class="plainlabel" >
									<INPUT type="text" name="hanmucno" id="hanmucno" value="<%=formatter.format(Double.parseDouble(nppBean.getHanmucno()))%>"  size="40" onKeyPress = "return keypress(event);"  onchange="DinhDang();">
								</TD>
								
								<TD class="plainlabel">Thời hạn nợ</TD>
								<TD class="plainlabel" colspan="3">
									<INPUT type="text" name="songayno" id="songayno" value="<%=formatter.format(Double.parseDouble(nppBean.getSongayno())) %>"  size="40" onKeyPress = "return keypress(event);"  onchange="DinhDang();">
								</TD>	
								
						</TR>
				<%} %>
					<TR>
						<TD class="plainlabel">Tên ký hợp đồng:	</TD>
						<TD class="plainlabel" >
							<INPUT type="text" name="tenkyhd" id="tenkyhd" value="<%= nppBean.getTenKyHd() %>" size="40">
						</TD>	
						<TD class="plainlabel">Hạn mức doanh thu KH:	</TD>
						<TD class="plainlabel" colspan="3">
							<INPUT type="text" name="hanmucdoanhthu" id="hanmucdoanhthu" onkeyup="Dinhdang(this)"   value="<%= nppBean.getHanmucdoanhthu() %>" size="40">
						</TD>
						
						
					</TR>
					
					<TR>
						<TD class="plainlabel">Tên thủ kho:	</TD>
						<TD class="plainlabel" >
							<INPUT type="text" name="tenthukho" id="tenthukho" value="<%= nppBean.getThuKho() %>" size="40">
						</TD>
							
						<TD class="plainlabel">Tài khoản công nợ</TD>
						<TD class="plainlabel">
							<select name="taiKhoanCongNo" id="taiKhoanCongNo" class="select2" style="width: 200px;" >
								<option value=""></option>
								<% if (nppBean.getTaiKhoanCongNoList() != null)
								{
									for (Erp_Item item : nppBean.getTaiKhoanCongNoList())
									{
										if (item.getValue().equals(nppBean.getTaiKhoanCongNo()))
										{
										%>
											<option value="<%=item.getValue()%>" selected="selected"><%=item.getName()%></option>
										<% } else { %>
											<option value="<%=item.getValue()%>"><%=item.getName()%></option>
								<% } }} %>
							</select>
						</TD>	
						
						<TD class="plainlabel">Tài khoản khách hàng nội bộ</TD>
						<TD class="plainlabel">
							<select name="taiKhoanNoiBo" id="taiKhoanNoiBo" class="select2" style="width: 200px;" >
								<option value=""></option>
								<% if (nppBean.getTaiKhoanNoiBoList() != null)
								{
									for (Erp_Item item : nppBean.getTaiKhoanNoiBoList())
									{
										if (item.getValue().equals(nppBean.getTaiKhoanNoiBo()))
										{
										%>
											<option value="<%=item.getValue()%>" selected="selected"><%=item.getName()%></option>
										<% } else { %>
											<option value="<%=item.getValue()%>"><%=item.getName()%></option>
								<% } }} %>
							</select>
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
</HTML>
<%}%>