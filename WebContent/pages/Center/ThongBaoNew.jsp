<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="geso.traphaco.center.db.sql.dbutils"%>
<%@page import="geso.traphaco.center.beans.thongbao.*"%>
<% IThongbao thongbao = (IThongbao)session.getAttribute("tbBean"); System.out.println("----View Mode ben trang JSP: " + thongbao.getViewMode() ); %>
<% ResultSet nvList = thongbao.getThongbaonhanvienList();%>
<% ResultSet kvList = thongbao.getKhuvucList();%>
<% ResultSet vList = thongbao.getVungList();%>
<% ResultSet vbhdList = thongbao.getVbhdRs(); %>
<% ResultSet vbccList = thongbao.getVbccRs(); %>
<% ResultSet vbttList = thongbao.getVbttRs(); %>
<% ResultSet vbsdbsList = thongbao.getVbsdbsRs(); %>
<% String userId = (String) session.getAttribute("userId");  %>

<% 
	String userTen = (String) session.getAttribute("userTen"); 
	ResultSet ddkdList = thongbao.getDdkdRs();
	ResultSet ttRs = thongbao.getTtRs();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>Insert title here</title>
	<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
	<LINK rel="stylesheet" href="../css/main.css" type="text/css">
	<script type="text/javascript"	src="../scripts/jquery.min.1.7.js"></script>
	<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>

	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
	<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>

	<script type="text/javascript" src="../scripts/ajax.js"></script>
	<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs.css">
	<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs-panes.css">
	
	<script>
		//perform JavaScript after the document is scriptable.
		$(function() {
		 // setup ul.tabs to work as tabs for each div directly under div.panes
		 	$("ul.tabs").tabs("div.panes > div");
		});
	</script>
	
	<script type="text/javascript">
		$(document).ready(function() {		
				$( ".days" ).datepicker({			    
						changeMonth: true,
						changeYear: true				
				});            
	        }); 		
			
	</script>
	
	<SCRIPT language="javascript" type="text/javascript">
		function LocNhanVien()
		{
			document.forms["congtyForm"].action.value="locnhanvien";
			document.forms["congtyForm"].submit();
		}
		
		 function saveform()
		 {
			 var tieude = document.getElementById('tieude');
			 var ngaybatdau= document.getElementById('ngaybatdau');
			 var ngayketthuc= document.getElementById('ngayketthuc');
			 var noidung= document.getElementById('noidung');
			 if(ngaybatdau.value=="")
			 {
				 alert("Vui lòng nhập ngày bắt đầu");
				 ctMa.focus();
				 return;
			 }
			 /* if(ngayketthuc.value=="")
			 {
				 alert("Vui lòng nhập ngày kết thúc");
				 ctMa.focus();
				 return;
			 } */
			 if(tieude.value=="")
			 {
				 alert("Vui lòng nhập tiêu đề thông báo");
				 ctMa.focus();
				 return;
			 }
			 if(noidung.value == "")
			 {
				 alert("Vui lòng nhập nội dung thông báo");
				 ctTen.focus();
				 return;
			 }
			 
			 document.forms["congtyForm"].action.value="save";
		     document.forms['congtyForm'].submit();
		 }
		 
		 function Xoafile()
		 {
		 	var xmlhttp;
		 	if (window.XMLHttpRequest)
		 	{// code for IE7+, Firefox, Chrome, Opera, Safari
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
		 		   if(xmlhttp.responseText=='')
		 			   {
		 	       			document.getElementById('tenfilea').innerHTML = xmlhttp.responseText;
		 	       			document.getElementById("valueten").value='0';
		 	       			alert('Đã xóa thành công');
		 			   }
		 		   else
		 			   {
		 			   alert(xmlhttp.responseText);
		 			   }
		 	   }
		 	}
		 	if(confirm("Bạn muốn xóa file đính kèm"))
		 	{
		 		xmlhttp.open("GET","../../ThongbaoUpdateSvl?task=" + "xoafilenew" + "&id=" + document.getElementById('valueten').value,true);
		 		xmlhttp.send();
		 	}
		 	else
		 		return false;
		 }
		 
/* 		 function sellectAll(id1, id2)
		 {
			 var chkAll = document.getElementById(id1);
			 var spIds = document.getElementsByName(id2);
			 
			 if(id2 == 'nvIds')
			 {
				 var ddkdIds = document.getElementsByName('ddkdIds');
			 }
			 
			 if(chkAll.checked)
			 {
				 for(i = 0; i < spIds.length; i++)
				 {
					 spIds.item(i).checked = true;
				 }
				 
				 if(id2 == 'nvIds')
				 {
					 for(i = 0; i < ddkdIds.length; i++)
					 {
					 	ddkdIds.item(i).checked = true;
					 }
				 }
				 
			 }
			 else
			 {
				 for(i = 0; i < spIds.length; i++)
				 {
					 spIds.item(i).checked = false;
				 }
				 
				 if(id2 == 'nvIds')
				 {
					 for(i = 0; i < ddkdIds.length; i++)
					 {
					 	ddkdIds.item(i).checked = false;
					 }
				 }
				 
			 }
		 }
		  */
		  
		  function sellectAll(id1, id2)
			 {
				 var chkAll = document.getElementById(id1);
				 var spIds = document.getElementsByName(id2);
				 
				 if(chkAll.checked)
				 {
					 for(i = 0; i < spIds.length; i++)
					 {
						 spIds.item(i).checked = true;
					 }
				 }
				 else
				 {
					 for(i = 0; i < spIds.length; i++)
					 {
						 spIds.item(i).checked = false;
					 }
				 }
			 }
 	</SCRIPT>
	
	<style type="text/css">
		.thongbao a { 
			color: blue;
		}
		.thongbao a:hover{
			color: red;
		}
	</style>
</head>

<body>
<form name="congtyForm" method="post" enctype="multipart/form-data" action="../../ThongbaoUpdateSvl" >
<input type="hidden" name="action" value="">
<input type="hidden" name="viewMode" value="<%= thongbao.getViewMode() %>" >
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#ffffff">
			<TABLE width="100%">
				<TR>
					<TD align="left" class="tbnavigation">

					   	<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							   <TD align="left" colspan="2" class="tbnavigation">
							   	Quản lý thông báo > <%= ( thongbao.getLoaithongbao().equals("5") ? "Thông báo" : "Văn bản" ) %> > Tạo mới </TD>
							   <TD colspan="2" align="right" class="tbnavigation"> Chào mừng bạn &nbsp;<%=userTen%>   </TD> 
						    </tr>
   
						   	<tr>
						   		<TD align="left" height="5" colspan="4" class=""></td>
   
  							</tr>
						</TABLE>
					</TD>
				</TR>
			</TABLE>	
			<TABLE width="100%" border="0" cellpadding="3" cellspacing="0">
				<TR ><TD >
						<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
								<TR class = "tbdarkrow">
									<TD width="30" align="left"><A href="../../ThongbaoSvl?userId=1000&loaivanban=<%= thongbao.getLoaithongbao() %>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
								    <TD width="2" align="left" ></TD>
								    <TD width="30" align="left" ><A href="javascript: saveform()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A></TD>
									<TD >&nbsp; </TD>						
								</TR>
						</TABLE>
				</TD></TR>
			</TABLE>
				
			<TABLE width = 100% cellpadding = "3" cellspacing = "0" border = "0">
			  	<TR>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>				
		    				<textarea name="dataerror"  style="width: 100% ; font-weight:bold" style="width: 100%" readonly="readonly" rows="1" ><%= thongbao.getMsg() %></textarea>
						</FIELDSET>
				   </TD>
				</TR>				
		  		<TR>
				   <TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Thông tin văn bản </LEGEND>
						<TABLE width="100%" cellspacing="0" cellpadding="6">
						<TR>
							  <TD width="120px" class="plainlabel" >Ngày bắt đầu</TD>
							  <TD width="250px" class="plainlabel" align="left">
							  	<input type="text" class="days"id="ngaybatdau" name="ngaybatdau" value="<%= thongbao.getNgaybatdau() %>">
							  </TD>
							  <TD width="120px" class="plainlabel" >Ngày kết thúc</TD>
							  <TD  class="plainlabel" >
							  	<input type="text" class="days" id="ngayketthuc" name="ngayketthuc" value="<%= thongbao.getNgayketthuc() %>">
							  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							  	<% if(thongbao.getHethieuluc().equals("1")) { %>
							  		<input type="checkbox" name="hethieuluc" checked="checked" value="1"  ><i>Hết hiệu lực</i>
							  	<% } else { %>
							  		<input type="checkbox" name="hethieuluc" value="1" ><i>Hết hiệu lực</i>
							  	<% } %>
							  </TD>
						</TR>
						<% if(!thongbao.getLoaithongbao().equals("5")) { %>
							<TR>
								  <TD class="plainlabel" >Loại văn bản</TD>
								  <TD  class="plainlabel" colspan="3" >
								  	 <select name="loaithongbao" >
								  	 	<option value="" ></option>
								  	 	<% if(thongbao.getLoaithongbao().equals("0")) { %>
								  	 		<option value="0" selected="selected" >Văn bản</option>
								  	 	<% } else { %> 
								  	 		<option value="0" >Văn bản</option>
								  	 	<% } %>
								  	 	<% if(thongbao.getLoaithongbao().equals("1")) { %>
								  	 		<option value="1" selected="selected" >Hướng dẫn</option>
								  	 	<% } else { %> 
								  	 		<option value="1" >Hướng dẫn</option>
								  	 	<% } %>
								  	 	<% if(thongbao.getLoaithongbao().equals("2")) { %>
								  	 		<option value="2" selected="selected" >Căn cứ</option>
								  	 	<% } else { %> 
								  	 		<option value="2" >Căn cứ</option>
								  	 	<% } %>
								  	 	<% if(thongbao.getLoaithongbao().equals("3")) { %>
								  	 		<option value="3" selected="selected" >Thay thế</option>
								  	 	<% } else { %> 
								  	 		<option value="3" >Thay thế</option>
								  	 	<% } %>
								  	 	<% if(thongbao.getLoaithongbao().equals("4")) { %>
								  	 		<option value="4" selected="selected" >Sửa đổi, bổ sung</option>
								  	 	<% } else { %> 
								  	 		<option value="4" >Sửa đổi, bổ sung</option>
								  	 	<% } %>
								  	 </select>
								  </TD>
							 </TR>
							 <TR>
								  <TD class="plainlabel" >Tiêu đề</TD>
								  <TD  class="plainlabel" colspan="3" >
								  	<textarea style="width: 600px; font-size: 14pt; font-weight : bold; color: blue;" class="txtsearch" id="tieude" name="tieude" ><%= thongbao.getTieude() %></textarea></TD>
							 </TR>
						 <% } else { %>
							 <TR>
								  <TD class="plainlabel" >Tiêu đề</TD>
								  <TD  class="plainlabel" colspan="3" >
								  	<input type="hidden" name="loaithongbao" value="5" >
								  	<textarea  style="width: 600px; font-size: 14pt; font-weight : bold; color: blue;" class="txtsearch" id="tieude" name="tieude"><%= thongbao.getTieude() %></textarea></TD>
							 </TR>
						 <% } %>
						 
						 <TR>
							  <TD class="plainlabel" valign="top" >Nội dung thông báo</TD>
							  <TD class="plainlabel" colspan="3">			
				    				<textarea  id="noidung" name="noidung" style="width: 100%;font-size: 14pt;" rows="8" cols="20" ><%= thongbao.getNoidung() %></textarea></TD>
						 </TR>
						 <TR>
							 <TD class="plainlabel"></TD>
							 <TD class="plainlabel">   	
							 <% if(thongbao.getChiNhanh().equals("1")) { %>
							  		<input type="checkbox" name="chinhanh" checked="checked" value="1" onchange="LocNhanVien()" ><i>Chi nhánh</i>
							  	<% } else { %>
							  		<input type="checkbox" name="chinhanh" value="1" onchange="LocNhanVien()" ><i>Chi nhánh</i>
							  	<% } %>
							 </TD>
							 <TD class="plainlabel" colspan="3">   	
							 <% if(thongbao.getDoiTac().equals("1")) { %>
								  		<input type="checkbox" name="doitac" checked="checked" value="1" onchange="LocNhanVien()" ><i>Đối tác</i>
								  	<% } else { %>
								  		<input type="checkbox" name="doitac" value="1" onchange="LocNhanVien()" ><i>Đối tác</i>
								  	<% } %>
							 </TD>
						 
						 
						 </TR>
						 <TR>
						  	  	<TD class="plainlabel">File đính kèm</TD>
						  	  	<TD class="plainlabel" colspan="3" >
						  	  		<INPUT type="file" name="filename" value="">
						  	  		<input type="hidden" id="valueten" name="tenfile" value="<%= thongbao.getFile() %>">
						  	  		<% if(thongbao.getFile().length() > 0 ) {	%>
						  	  			<div id="tenfilea"><p><%= thongbao.getFile() %><img src="../images/Delete20.png" onclick="Xoafile()" style="cursor: pointer;" alt="Xóa" width="20" height="20" longdesc="Cap nhat" border = 0></p></div>
						  	  		<%} %>
						  	  	</TD>
				  		</TR>
				  		<TR>
			  				<TD colspan="4" >
			  					<ul class="tabs">
			  						<li><a href="#">Nhân viên nhận</a></li>
			  					<% if(!thongbao.getLoaithongbao().equals("5")) { %>
		                  			<li><a href="#">Văn bản hướng dẫn</a></li>
		                  			<li><a href="#">Văn bản căn cứ</a></li>
		                  			<li><a href="#">Văn bản sửa đổi BS</a></li>
		                  			<li><a href="#">Văn bản thay thế</a></li>
		                  		<% } %>
		                  		</ul>
		                  		
		                  		<div class="panes">
		                  			
		                  			<div style="font-size: 1.0em; font-weight: 500" >
		                  				<TABLE width="100%" cellspacing="1" cellpadding="4">
		                  					<TR>
		                  						<td width="80px">Miền </td>
		                  						<td width="220px" >
		                  							<select name="vungId" onchange="LocNhanVien()">
														 <option value = "" > </option>
							                             <%
							                             if (vList != null)
							                             {
							                            	 while (vList.next())
							                            	 { %>
							                       				<% if(vList.getString("pk_seq").equals(thongbao.getVung())){ %>
							                       					<option value ="<%= vList.getString("pk_seq") %>" selected="selected"> <%= vList.getString("ten") %></option>
							                       				<%}else{ %>
							                       					<option value ="<%= vList.getString("pk_seq") %>"> <%= vList.getString("ten") %></option>
							                       				<%} %>
							                       			<% }
							                             }
							                             %>
						                             </select>  
						                             &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  
		                  				<%-- 		Khu vực 
		                  							 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
		                  							<select name="kvId" onchange="LocNhanVien()">
														 <option value =""> </option>
							                             <%
							                             if (kvList!=null)
							                             {
							                            	 while (kvList.next())
							                            	 { %>
							                       				<% if(kvList.getString("pk_seq").equals(thongbao.getKhuvuc())){ %>
							                       					<option value ="<%=kvList.getString("pk_seq") %>" selected="selected"> <%=kvList.getString("ten") %></option>
							                       				<%}else{ %>
							                       					<option value ="<%=kvList.getString("pk_seq") %>"> <%=kvList.getString("ten") %></option>
							                       				<%} %>
							                       			<% }
							                             }
						                             	 %>
						                             </select>     --%>  
						                             
						                             
						                             Địa bàn
		                  							 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
		                  							<select name="ttId" onchange="LocNhanVien()">
														 <option value =""> </option>
							                             <%
							                             if (ttRs!=null)
							                             {
							                            	 while (ttRs.next())
							                            	 { %>
							                       				<% if(ttRs.getString("pk_seq").equals(thongbao.getTtId() )){ %>
							                       					<option value ="<%=ttRs.getString("pk_seq") %>" selected="selected"> <%=ttRs.getString("ten") %></option>
							                       				<%}else{ %>
							                       					<option value ="<%=ttRs.getString("pk_seq") %>"> <%=ttRs.getString("ten") %></option>
							                       				<%} %>
							                       			<% }
							                             }
						                             	 %>
						                             </select> 
						                             
		                  						</td>
		                  						
		                  						<td width="80px"> Loại NV</TD>
												<td width="220px" >
												<select name="loaiNv" id="loaiNv" onchange="LocNhanVien();">
														<option value="0"
															<%=thongbao.getLoaiNv().equals("0") ? " selected " : ""%>></option>
														<option value="4"
															<%=thongbao.getLoaiNv().equals("4") ? " selected " : ""%>>Trình dược viên</option>
														<option value="5"
															<%=thongbao.getLoaiNv().equals("5") ? " selected " : ""%>>Ban Giám Đốc</option>
														<option value="1"
															<%=thongbao.getLoaiNv().equals("1") ? " selected " : ""%>>Giám Đốc Miền</option>
														<option value="2"
															<%=thongbao.getLoaiNv().equals("2") ? " selected " : ""%>>Phụ trách Vùng</option>
														<option value="3"
															<%=thongbao.getLoaiNv().equals("3") ? " selected " : ""%>>Phụ Trách Tỉnh/ GĐCN Cấp 2</option>																			
												</select></TD>
		                  						
		                  					</TR>
		                  					
		                  					<TR>
		                  						<TH width="15%" align="center" class="plainlabel" >Mã nhân viên</TH>
		                  						<TH width="65%" align="center" class="plainlabel" >Tên nhân viên</TH>
		                  						<TH width="10%" align="center" class="plainlabel" >Điện thoại</TH>
		                  						<TH width="10%" align="center" class="plainlabel" >Chọn hết <input type="checkbox" id='chkAll' onchange="sellectAll('chkAll', 'nvIds');"   > </TH>
		                  					</TR>
		                  					
		                  					<TR >
		                  						<td class="plainlabel" colspan="4" > NHÂN VIÊN ĐĂNG NHẬP </td>
		                  					</TR>
		                  					
		                  					<% if(nvList != null) 
		                  					{ 
		                  						int m = 0;
		                  						while(nvList.next()) 
		                  						{ 
		                  							%>
		                  							
		                  							<TR class="<%= m % 2 == 0 ? "tblightrow" : "tbdarkrow"  %>" >
		                  								<TD style="font-size: 1.0em" ><%= nvList.getString("dangnhap") %></TD>
		                  								<TD><%= nvList.getString("ten") %></TD>
		                  								<TD><%= nvList.getString("dienthoai") %></TD>
		                  								<TD align="center" >
		                  									<% if( thongbao.getNhanvienIds().indexOf(nvList.getString("pk_seq")) >= 0 ) { %>
		                  										<input type="checkbox" name="nvIds" value="<%= nvList.getString("pk_seq") %>" checked="checked"  >
		                  									<% } else { %> 
		                  										<input type="checkbox" name="nvIds" value="<%= nvList.getString("pk_seq") %>"   >
		                  									<% } %>
		                  								</TD>
		                  							</TR>
		                  							
		                  						<% m++; } 
		                  						nvList.close(); 
		                  					} %>
		                  					
		                  					<TR>
		                  						<TH width="15%" align="center" class="plainlabel" >Mã TDV</TH>
		                  						<TH width="65%" align="center" class="plainlabel" >Tên trình dược viên</TH>
		                  						<TH width="10%" align="center" class="plainlabel" >Điện thoại</TH>
		                  						<TH width="10%" align="center" class="plainlabel" >Chọn hết <input type="checkbox" id='chkAll2' onchange="sellectAll('chkAll2', 'ddkdIds');"   > </TH>
		                  					</TR>
		                  					<TR >
		                  						<td class="plainlabel" colspan="4" > TRÌNH DƯỢC VIÊN </td>
		                  					</TR>
		                  					
		                  					
		                  					<% if(ddkdList != null) 
		                  					{ 
		                  						int m = 0;
		                  						while(ddkdList.next()) 
		                  						{ 
		                  							%>
		                  							
		                  							<TR class="<%= m % 2 == 0 ? "tblightrow" : "tbdarkrow"  %>" >
		                  								<TD style="font-size: 1.0em" ><%= ddkdList.getString("MANHANVIEN") %></TD>
		                  								<TD><%= ddkdList.getString("ten") %></TD>
		                  								<TD><%= ddkdList.getString("dienthoai") %></TD>
		                  								<TD align="center" >
		                  									<% if( thongbao.getDdkdId().indexOf(ddkdList.getString("pk_seq")) >= 0 ) { %>
		                  										<input type="checkbox" name="ddkdIds" value="<%= ddkdList.getString("pk_seq") %>" checked="checked"  >
		                  									<% } else { %> 
		                  										<input type="checkbox" name="ddkdIds" value="<%= ddkdList.getString("pk_seq") %>"   >
		                  									<% } %>
		                  								</TD>
		                  							</TR>
		                  							
		                  						<% m++; } 
		                  						ddkdList.close(); 
		                  					}%>
		                  				
		                  					
		                  				</TABLE>
		                  				
		                  			</div>
		                  			
		                  		<% if(!thongbao.getLoaithongbao().equals("5")) { %>	
		                  			
		                  			<div style="font-size: 1.0em; font-weight: 500">
		                  				<TABLE width="100%" cellspacing="1" cellpadding="4">
		                  					<TR>
		                  						<TH width="10%" align="center" class="plainlabel" >Mã văn bản</TH>
		                  						<TH width="80%" align="center" class="plainlabel" >Nội dung</TH>
		                  						<TH width="10%" align="center" class="plainlabel" >Chọn hết <input type="checkbox" id='chkAll3' onchange="sellectAll('chkAll3', 'vbhdIds');"  > </TH>
		                  					</TR>
		                  					
		                  					<% if(vbhdList != null ) 
		                  					{ 
		                  						int m = 0;
		                  						while(vbhdList.next() ) 
		                  						{
		                  							%>
		                  							<TR class="<%= m % 2 == 0 ? "tblightrow" : "tbdarkrow"  %>" >
		                  								<TD style="font-size: 1.0em" ><%= vbhdList.getString("pk_seq") %></TD>
		                  								<TD class="thongbao" ><a href="../../ThongbaoUpdateSvl?task=view&id=<%= vbhdList.getString("pk_seq") %>&viewMode=<%= thongbao.getViewMode()  %>" target="target" ><%= vbhdList.getString("noidung") %></a></TD>
		                  								<TD align="center" >
		                  									<% if( thongbao.getVbhdId().indexOf(vbhdList.getString("pk_seq")) >= 0 ) { %>
		                  										<input type="checkbox" name="vbhdIds" value="<%= vbhdList.getString("pk_seq") %>" checked="checked"  >
		                  									<% } else { %> 
		                  										<input type="checkbox" name="vbhdIds" value="<%= vbhdList.getString("pk_seq") %>"   >
		                  									<% } %>
		                  								</TD>
		                  							</TR>
		                  						<% m++; } 
		                  						vbhdList.close();  
		                  					} %>
		                  					
		                  				</TABLE>
		                  			</div>
		                  			
		                  			<div style="font-size: 1.0em; font-weight: 500">
		                  				<TABLE width="100%" cellspacing="1" cellpadding="4">
		                  					<TR>
		                  						<TH width="10%" align="center" class="plainlabel" >Mã văn bản</TH>
		                  						<TH width="80%" align="center" class="plainlabel" >Nội dung</TH>
		                  						<TH width="10%" align="center" class="plainlabel" >Chọn hết <input type="checkbox" id='chkAll4' onchange="sellectAll('chkAll4', 'vbccIds');" > </TH>
		                  					</TR>
		                  					
		                  					<% if(vbccList != null ) 
		                  					{ 
		                  						int m = 0;
		                  						while(vbccList.next() ) 
		                  						{
		                  							%>
		                  							<TR class="<%= m % 2 == 0 ? "tblightrow" : "tbdarkrow"  %>" >
		                  								<TD style="font-size: 1.0em" ><%= vbccList.getString("pk_seq") %></TD>
		                  								<TD class="thongbao" ><a href="../../ThongbaoUpdateSvl?task=view&id=<%= vbccList.getString("pk_seq") %>&viewMode=<%= thongbao.getViewMode()  %>" target="target" ><%= vbccList.getString("noidung") %></a></TD>
		                  								<TD align="center" >
		                  									<% if( thongbao.getVbcId().indexOf(vbccList.getString("pk_seq")) >= 0 ) { %>
		                  										<input type="checkbox" name="vbccIds" value="<%= vbccList.getString("pk_seq") %>" checked="checked"  >
		                  									<% } else { %> 
		                  										<input type="checkbox" name="vbccIds" value="<%= vbccList.getString("pk_seq") %>"   >
		                  									<% } %>
		                  								</TD>
		                  							</TR>
		                  						<% m++; } 
		                  						vbccList.close();  
		                  					} %>
		                  					
		                  				</TABLE>
		                  			</div>
		                  			
		                  			<div style="font-size: 1.0em; font-weight: 500">
		                  				<TABLE width="100%" cellspacing="1" cellpadding="4">
		                  					<TR>
		                  						<TH width="10%" align="center" class="plainlabel" >Mã văn bản</TH>
		                  						<TH width="80%" align="center" class="plainlabel" >Nội dung</TH>
		                  						<TH width="10%" align="center" class="plainlabel" >Chọn hết <input type="checkbox" id='chkAll5' onchange="sellectAll('chkAll5', 'vbsdbsIds');"  > </TH>
		                  					</TR>
		                  					
		                  					<% if(vbsdbsList != null ) 
		                  					{ 
		                  						int m = 0;
		                  						while(vbsdbsList.next() ) 
		                  						{
		                  							%>
		                  							<TR class="<%= m % 2 == 0 ? "tblightrow" : "tbdarkrow"  %>" >
		                  								<TD style="font-size: 1.0em" ><%= vbsdbsList.getString("pk_seq") %></TD>
		                  								<TD class="thongbao" ><a href="../../ThongbaoUpdateSvl?task=view&id=<%= vbsdbsList.getString("pk_seq") %>&viewMode=<%= thongbao.getViewMode()  %>" target="target" ><%= vbsdbsList.getString("noidung") %></a></TD>
		                  								<TD align="center" >
		                  									<% if( thongbao.getVbsdbsId().indexOf(vbsdbsList.getString("pk_seq")) >= 0 ) { %>
		                  										<input type="checkbox" name="vbsdbsIds" value="<%= vbsdbsList.getString("pk_seq") %>" checked="checked"  >
		                  									<% } else { %> 
		                  										<input type="checkbox" name="vbsdbsIds" value="<%= vbsdbsList.getString("pk_seq") %>"   >
		                  									<% } %>
		                  								</TD>
		                  							</TR>
		                  						<% m++; } 
		                  						vbsdbsList.close();  
		                  					} %>
		                  					
		                  				</TABLE>
		                  			</div>
		                  			
		                  			<div style="font-size: 1.0em; font-weight: 500">
		                  				<TABLE width="100%" cellspacing="1" cellpadding="4">
		                  					<TR>
		                  						<TH width="10%" align="center" class="plainlabel" >Mã văn bản</TH>
		                  						<TH width="80%" align="center" class="plainlabel" >Nội dung</TH>
		                  						<TH width="10%" align="center" class="plainlabel" >Chọn hết <input type="checkbox" id='chkAll6' onchange="sellectAll('chkAll6', 'vbttIds');" > </TH>
		                  					</TR>
		                  					
		                  					<% if(vbttList != null ) 
		                  					{ 
		                  						int m = 0;
		                  						while(vbttList.next() ) 
		                  						{
		                  							%>
		                  							<TR class="<%= m % 2 == 0 ? "tblightrow" : "tbdarkrow"  %>" >
		                  								<TD style="font-size: 1.0em" ><%= vbttList.getString("pk_seq") %></TD>
		                  								<TD class="thongbao" ><a href="../../ThongbaoUpdateSvl?task=view&id=<%= vbttList.getString("pk_seq") %>&viewMode=<%= thongbao.getViewMode()  %>" target="target" ><%= vbttList.getString("noidung") %></a></TD>
		                  								<TD align="center" >
		                  									<% if( thongbao.getVbttId().indexOf(vbttList.getString("pk_seq")) >= 0 ) { %>
		                  										<input type="checkbox" name="vbttIds" value="<%= vbttList.getString("pk_seq") %>" checked="checked"  >
		                  									<% } else { %> 
		                  										<input type="checkbox" name="vbttIds" value="<%= vbttList.getString("pk_seq") %>"   >
		                  									<% } %>
		                  								</TD>
		                  							</TR>
		                  						<% m++; } 
		                  						vbttList.close();  
		                  					} %>
		                  					
		                  				</TABLE>
		                  			</div>
		                  		
		                  		<% } %>	
		                  			
		                  		</div>
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
<%thongbao.DBClose(); %>
</form>
</body>
</html>