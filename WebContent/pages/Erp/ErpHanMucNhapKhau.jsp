<%@page import="java.sql.SQLException"%>
<%@ page import="java.sql.ResultSet"%>
<%@page import="geso.traphaco.erp.beans.hanmucnhapkhau.IErpHanMucNhapKhauList"%>
<%@page import="geso.traphaco.erp.beans.hanmucnhapkhau.imp.ErpHanMucNhapKhauList"%>
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
	IErpHanMucNhapKhauList lhmnk = (IErpHanMucNhapKhauList) session.getAttribute("obj");
	ResultSet Rslistsp = lhmnk.getListSanPham();
	ResultSet Rslisthm = lhmnk.getListHanMuc();
	 int[] quyen = new  int[5];
		quyen = util.Getquyen("ErpLoaiNhaCungCapSvl","",userId);
	
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
	function xoaform() {
		document.HanMucNhapKhauForm.listsanpham.value = "";
		document.HanMucNhapKhauForm.tungay.value = "";
		document.HanMucNhapKhauForm.denngay.value = "";
		Search();
	}

	function Search() {
		document.forms['HanMucNhapKhauForm'].action.value = 'Search';
		document.forms['HanMucNhapKhauForm'].submit();
	}

	function newform() {
		document.forms['HanMucNhapKhauForm'].action.value = 'New';
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
	<form name="HanMucNhapKhauForm" method="post" action="../../ErpHanMucNhapKhauSvl">
		<input type="hidden" name="userId" value='<%=userId%>'> <input type="hidden" name="userTen" value='<%=userTen%>'> <input
			type="hidden" name="action" value='1'>
			
			<input type="hidden" id="msg" value="<%=lhmnk.getMsg() %>">
			
			<script language="javascript" type="text/javascript">
			    thongbao();
			</script> 
							
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr height="22">
						<td align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền &gt; Mua hàng &gt; Hạn mức nhập khẩu </td>
						<td align="right" colspan="2" class="tbnavigation">Chào mừng bạn <%=userTen%>&nbsp;
						</td>
					</tr>
				</table>			
				<fieldset>
					<legend class="legendtitle">Tiêu chí tìm kiếm &nbsp;</legend>
					<table width="100%" cellpadding="6" cellspacing="0" style="text-align: left">
						<tr>
							<td class="plainlabel">Sản phẩm</td>
							<TD class="plainlabel" >
		                      <select name="listsanpham" id="listsanpham" style="width:200px" onchange="Search()" class="select2" >
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
						</tr>
						<tr>
							<td class="plainlabel">Từ ngày</td>
							<TD class="plainlabel"><input type="text"  name="tungay" value='<%=lhmnk.getTuNgay()%>' class="days" onchange="Search()"></td>
						</tr>
						<tr>
							<td class="plainlabel">Đến ngày</td>
							<TD class="plainlabel"><input type="text"  name="denngay" value='<%=lhmnk.getDenNgay()%>' class="days" onchange="Search()"></td>
						</tr>
						<tr>
							<td colspan="2" class="plainlabel">
								<a class="button2" href="javascript:xoaform()"> <img style="top: -4px;" src="../images/button.png" alt="">Nhập lại
								</a>
							</td>
						</tr>
					</table>
				</fieldset>					
				<fieldset>
					<legend class="legendtitle">
						&nbsp;Hạn mức nhập khẩu  &nbsp;&nbsp;&nbsp; 
				
						 <a class="button3" href="javascript:newform()"> <img style="top: -4px;"
							src="../images/New.png" alt="">Tạo mới
						</a>
		
					</legend>
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td align="left" colspan="4">
								<table width="100%" border="0" cellspacing="1" cellpadding="4">
									<tr class="tbheader">
										<th width="10%" align="center">Mã sản phẩm</th>
										<th width="20%" align="center">Tên sản phẩm</th>
										<th width="15%" align="center">Trạng thái</th>
										<th width="15%" align="center">Từ ngày</th>
										<th width="15%" align="center">Đến ngày</th>
										<th width="15%" align="center">Số lượng</th>
										<th width="10%" align="center">&nbsp;Tác vụ</th>
										
							
									</tr>
									<%
										int m = 0;
										String lightrow = "tblightrow";
										String darkrow = "tbdarkrow";
										if (Rslisthm != null)
											while (Rslisthm.next())
											{
												String trangthai = Rslisthm.getString("TRANGTHAI");
												if (trangthai.equals("1"))
													trangthai = "Hoạt động";
												else if(trangthai.equals("0"))
													trangthai = "Không hoạt động";
												if (m % 2 != 0) {
									%>
									<tr class=<%=lightrow%>>
										<%
											} else {
										%>
									
									<tr class=<%=darkrow%>>
										<%
											}
										%>
										<td align="center"><%=Rslisthm.getString("MA")%></td>
										<td align="left"><%=Rslisthm.getString("TEN")%></td>
										<td align="center"><%=trangthai%></td>
										<td align="center"><%=Rslisthm.getString("TUNGAY")%></td>
										<td align="center"><%=Rslisthm.getString("DENNGAY")%></td>
										<td align="center"><%=Rslisthm.getString("SOLUONG")%></td>
					
										<td align="center">
											<table border=0 cellpadding="0" cellspacing="0">
												<tr>
												<% String tt = Rslisthm.getString("TRANGTHAI");%>
												<%if(tt.equals("1")){ %>
													<td> 
														
													   <a href="../../ErpHanMucNhapKhauUpdateSvl?userid=<%=userId%>&update=<%=Rslisthm.getString("PK_SEQ")%>"><img
														title="Cập nhật" src="../images/Edit20.png" alt="Chinh sua" width="20" height="20" longdesc="Chinh sua" border=0></A>&nbsp;

													</td>
												
													<td>   
														
												        <a href="../../ErpHanMucNhapKhauSvl?userid=<%=userId%>&delete=<%=Rslisthm.getString("PK_SEQ")%>"><img
														title="Xóa" src="../images/Delete20.png" alt="Xoa" width="20" height="20" longdesc="Xoa" border=0
														onclick="if(!confirm('Bạn muốn xóa loại hạn mức này?')) return false;"> </a>&nbsp;  
														
													</td>
														<%} %>
												</tr>
											</table>
									</tr>
									<%
										m++;
											}
									%>
									<tr class="tbfooter" > 
									 <TD align="center" valign="middle" colspan="13" class="tbfooter">
									 	<%if(lhmnk.getNxtApprSplitting() >1) {%>
											<img alt="Trang Dau" src="../images/first.gif" style="cursor: pointer;" onclick="View('erpDmhForm', 1, 'view')"> &nbsp;
										<%}else {%>
											<img alt="Trang Dau" src="../images/first.gif" > &nbsp;
											<%} %>
										<% if(lhmnk.getNxtApprSplitting() > 1){ %>
											<img alt="Trang Truoc" src="../images/prev.gif" style="cursor: pointer;" onclick="Prev('erpDmhForm', 'prev')"> &nbsp;
										<%}else{ %>
											<img alt="Trang Truoc" src="../images/prev_d.gif" > &nbsp;
										<%} %>
										
										<%
											int[] listPage = lhmnk.getNextSplittings();
										 if(listPage!=null){
											for(int i = 0; i < listPage.length; i++){
										%>
										
										<% 							
										
										if(listPage[i] == lhmnk.getNxtApprSplitting()){ %>
										
											<a  style="color:white;"><%= listPage[i] %>/ <%=lhmnk.getTheLastSplitting() %></a>
										<%}else{ %>
											<a href="javascript:View('erpDmhForm', <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
										<%} %>
											<input type="hidden" name="list" value="<%= listPage[i] %>" />  &nbsp;
										<%}
											}%>
										
										<% if(lhmnk.getNxtApprSplitting() < lhmnk.getTheLastSplitting()){ %>
											&nbsp; <img alt="Trang Tiep" src="../images/next.gif" style="cursor: pointer;" onclick="Next('erpDmhForm', 'next')"> &nbsp;
										<%}else{ %>
											&nbsp; <img alt="Trang Tiep" src="../images/next_d.gif" > &nbsp;
										<%} %>
										<%if(lhmnk.getNxtApprSplitting() == lhmnk.getTheLastSplitting()) {%>
									   		<img alt="Trang Cuoi" src="../images/last.gif" > &nbsp;
								   		<%}else{ %>
								   			<img alt="Trang Cuoi" src="../images/last.gif" style="cursor: pointer;" onclick="View('erpDmhForm', -1, 'view')"> &nbsp;
								   		<%} %>
									</TD>
								 </tr> 
								</table>
							</td>
						</tr>
					</table>
				</fieldset>					
	</form>
</body>
</html>
<%

	lhmnk.closeDB();
	}
%>
