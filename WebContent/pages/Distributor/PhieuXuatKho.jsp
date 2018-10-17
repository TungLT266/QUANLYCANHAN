<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.distributor.beans.phieuxuatkho.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ 
		
		int[] quyen = new  int[6];
		quyen = util.Getquyen("PhieuxuatkhoSvl","",userId);
	%>


<% IPhieuxuatkhoList obj = (IPhieuxuatkhoList)session.getAttribute("obj"); %>
<% ResultSet rs = obj.getRsPXK(); %>
<% ResultSet nvgn = (ResultSet)obj.getNhanvienGN();
   
%>

<% obj.setNextSplittings(); %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">

<script type="text/javascript" language="JavaScript" src="../scripts/simplecalendar.js"></script>
<script type="text/javascript" src="../scripts/phanTrang.js"></script>

<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js"
	type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {		
			$( ".days" ).datepicker({			    
					changeMonth: true,
					changeYear: true				
			});            
        }); 		
		
</script>

<SCRIPT language="javascript" type="text/javascript">
	function confirmLogout()
	{
	   if(confirm("Ban co muon dang xuat?"))
	   {
			top.location.href = "home.jsp";
	   }
	   return;
	 }
	function processing(id,chuoi){
		 document.getElementById(id).innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Proc...</a>";
		 document.getElementById(id).href = chuoi;
		  
	}
	function searchphieu()
	{   
		document.forms["pxkForm"].action.value="search";
	    document.forms["pxkForm"].submit();
	}
	function clearform()
	{
		
		 document.forms["pxkForm"].nvgnTen.selectedIndex = -1;
		 document.forms["pxkForm"].trangthai.selectedIndex = -1;
		 document.forms["pxkForm"].tungay.value = "";
		 document.forms["pxkForm"].mapxk.value = "";
		 document.forms["pxkForm"].khId.value = "";
		 document.forms["pxkForm"].denngay.value = "";
		 document.forms["pxkForm"].mafast.value = "";
		 document.forms["pxkForm"].madonhang.value = "";
		 document.forms["pxkForm"].action.value = "clear";
		 document.forms["pxkForm"].submit();
	}
	function newform(){
		
		 document.forms["pxkForm"].action.value="Tao moi";
		 document.forms["pxkForm"].submit();
	}
	function thongbao()
	{
		if(document.getElementById("msg").value != '')
			alert(document.getElementById("msg").value);
	}
</SCRIPT>

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="pxkForm" method="post" action="../../PhieuxuatkhoSvl">
		<input type="hidden" name="userId" value="<%= userId %>"> <input
			type="hidden" name="nppId" value="<%= obj.getNppId() %>"> <input
			type="hidden" name="action" value="1"> 
		<input type="hidden"name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>">
		<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>"> <input
			type="hidden" name="msg" id="msg" value='<%= obj.getMsg() %>'>
		<script type="text/javascript">
	thongbao();
</script>
		<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
			height="100%">
			<TR>
				<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
					<!--begin body Dossier-->
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="2">
						<TR>
							<TD align="left" class="tbnavigation">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr height="22">
										<TD align="left" colspan="2" class="tbnavigation">
										Quản lý bán hàng > Bán hàng OTC > Phiếu xuất kho
										
										</TD>

										<TD colspan="2" align="right" class="tbnavigation">Chào
											mừng Chi nhánh / Đối tác <%= obj.getNppTen() %></TD>
									</tr>
								</table></TD>
						</TR>
					</TABLE>
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="2">
						<TR>
							<TD>
								<FIELDSET>
									<LEGEND class="legendtitle">&nbsp;Tiêu chí tìm
										kiếm&nbsp;</LEGEND>
									<TABLE width="100%" cellspacing="0" cellpadding="3">
										<TR class="tblightrow">
											<TD class="plainlabel">Nhân viên giao nhận</TD>
											<TD class="plainlabel"><SELECT name="nvgnTen" onChange="searchphieu();">
												<option value="">&nbsp;&nbsp;</option>
													<% if(nvgn != null){
													  try{ while(nvgn.next()){ 
										    			if(nvgn.getString("nvgnId").equals(obj.getNvgnId())){ %>
																	<option value='<%=nvgn.getString("nvgnId")%>' selected><%=nvgn.getString("nvgnTen") %></option>
																	<%}else{ %>
																	<option value='<%=nvgn.getString("nvgnId")%>'><%=nvgn.getString("nvgnTen") %></option>
																	<%}}}catch(java.sql.SQLException e){} }
													%>
											</SELECT></TD>
											<TD class="plainlabel"> Mã PXK </TD>
											<TD class="plainlabel"><input  type="text" name="mapxk" value="<%= obj.getmaPXK() %>" size="11"  onChange="searchphieu();"> </TD>
										</TR>
										
										<TR>
											<TD class="plainlabel">Trạng thái</TD>
											<TD class="plainlabel"><SELECT
												name="trangthai" onChange="searchphieu();">
													<% if (obj.getTrangthai().equals("1")){%>
													<option value="1" selected>Đã chốt</option>
													<option value="0">Chưa chốt</option>
													<option value=""></option>
													<%}else if(obj.getTrangthai().equals("0")) {%>
													<option value="0" selected>Chưa chốt</option>
													<option value="1">Đã chốt</option>
													<option value=""></option>
													<%}else{%>
													<option value="" selected></option>
													<option value="1">Đã chốt</option>
													<option value="0">Chưa chốt</option>
													<%}%>
											</SELECT></TD>
											<TD class="plainlabel"> Mã/ Tên khách hàng </TD>
											<TD class="plainlabel"><input  type="text" name="khId" value="<%= obj.getkhachHang() %>" size="11" onChange="searchphieu();"> </TD>
										</TR>
										<TR>
											<TD class="plainlabel">Từ ngày</TD>
											<TD class="plainlabel">
												<TABLE cellpadding="0" cellspacing="0">
													<TR>
														<TD><input class="days" type="text" name="tungay" value="<%= obj.getTungay() %>" size="11"  onChange="searchphieu();"></TD>
													</TR>
												</TABLE></TD>
											<TD class="plainlabel">Đến ngày</TD>
											<TD class="plainlabel">
												<TABLE cellpadding="0" cellspacing="0">
													<TR>
														<TD><input class="days" type="text" name="denngay" value="<%= obj.getDenngay() %>" size="11" onChange="searchphieu();"></TD>
													</TR>
												</TABLE></TD>
										</TR>
										<TR>
											<TD class="plainlabel"> Mã cũ </TD>
											<TD class="plainlabel" "><input  type="text" name="mafast" value="<%= obj.getmaFast() %>" size="11"  onChange="searchphieu();"> </TD>
											<TD class="plainlabel"> Mã đơn hàng </TD>
											<TD class="plainlabel" ><input  type="text" name="madonhang" value="<%= obj.getmaDonhang() %>" size="11"  onChange="searchphieu();"> </TD>
										
										</TR>
										
										<TR>
											<TD class="plainlabel" colspan="4"><a class="button2"
												href="javascript:searchphieu()"> <img style="top: -4px;"
													src="../images/Search30.png" alt="">Tìm kiếm </a>&nbsp;&nbsp;&nbsp;&nbsp;

												<a class="button2" href="javascript:clearform()"> <img
													style="top: -4px;" src="../images/button.png" alt="">Nhập
													lại</a>&nbsp;&nbsp;&nbsp;&nbsp; <!-- 
							  <INPUT name="action" type="submit" value="Tim kiem">&nbsp;
                              <INPUT name="reinitialiser" type="reset" value="Nhap lai"></TD>
                               -->
										</TR>
									</TABLE>

								</FIELDSET></TD>
						</TR>
					</TABLE>
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR>
							<TD width="100%">
								<FIELDSET>
									<LEGEND class="legendtitle">
										Phiếu xuất kho &nbsp;&nbsp;&nbsp;&nbsp;
										<%if(quyen[Utility.THEM]!=0){ %>
										 <a class="button3" href="javascript:newform()"> 
										 <img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>
										 <%} %>

										<!--  <INPUT name="action" type="submit" value="Tao moi">-->
									</LEGEND>
									<TABLE class="" width="100%" cellpadding="0" cellspacing="0">
										<TR>
											<TD>
												<TABLE width="100%" border="0" cellspacing="1"
													cellpadding="2">
													<TR class="tbheader">
														<TH align="center">Mã phiếu</TH>
														<TH align="center">NV giao nhận</TH>
														<TH align="center">Khách hàng</TH>
														<TH align="center">Mã cũ</TH>
														<TH align="center">Ngày lập</TH>
														<TH align="center">Trạng thái</TH>
														<TH align="center">Ngày tạo</TH>
														<TH align="center">Người tạo</TH>
														<TH align="center">Ngày sửa</TH>
														<TH align="center">Người sửa</TH>
														<TH align="center">Tác vụ</TH>
													</TR>
													<% 
									
									int m = 0;
									if(rs!=null)					
									while (rs.next()){
										
										if (m % 2 != 0) {%>
													<TR class="tblightrow">
														<%} else {%>
													
													<TR class="tbdarkrow">
														<%}%>
														<TD align="center"><%=rs.getString("pxkId") %></TD>
														<TD align="left"><%= rs.getString("nvgnTen") %></TD>
														<TD align="center"><%=rs.getString("khachhang") %></TD>
														<TD align="center"><%=rs.getString("mafast") %></TD>
														<TD align="center"><%= rs.getString("ngaylapphieu") %></TD>
														<% if (rs.getString("trangthai").equals("1")){ %>
														<TD align="center"><b>Đã chốt</b></TD>
														<%}else{ if(rs.getString("trangthai").trim().equals("0")){ %>
														<TD align="center">Chưa chốt</TD>
														<%}else{ %>
														<TD align="center"><i>Đã hủy</i>
														</TD>
														<% }}%>
														<TD align="center"><%=  rs.getString("ngaytao")%></TD>
														<TD align="left"><%=  rs.getString("nguoitao")%></TD>
														<TD align="center"><%=  rs.getString("ngaysua")%></TD>
														<TD align="left"><%=  rs.getString("nguoisua")%></TD>
														<TD align="center" valign="middle">
															<% if(rs.getString("trangthai").trim().equals("0")) {%>
															 
															 <%if(quyen[Utility.SUA]!=0){ %>
															<A id='<%="update"+rs.getString("pxkId")%>'
															   href=""><img
															   src="../images/Edit20.png" alt="Cap nhat" width="20"
															   height="20" longdesc="Cap nhat" border=0 onclick="processing('<%="update"+rs.getString("pxkId")%>' , '../../PhieuxuatkhoUpdateSvl?userId=<%=userId%>&update=<%= rs.getString("pxkId") %>')" >
															</A>&nbsp; 
															<%} %>
														
														
															<%if(quyen[Utility.XOA]!=0){ %>
															<A href="../../PhieuxuatkhoSvl?userId=<%=userId%>&delete=<%= rs.getString("pxkId") %>"><img
																src="../images/Delete20.png" alt="Xoa" width="20"
																height="20" longdesc="Xoa" border=0
																onclick="if(!confirm('Bạn có muốn xóa phiếu xuất kho này?')) return false;">
															</A>&nbsp;
															<%} %> 
															
															<%if(quyen[Utility.CHOT]!=0){ %>
															<A id='<%="chotpxkid" + rs.getString("pxkId") %>'
														   href=""><img 
														   src="../images/Chot.png" alt="Chot phieu xuat" width="20" height="20" longdesc="Chot phieu xuat"
														   border=0	onclick="if(!confirm('Bạn có muốn chốt phiếu xuất kho này?')) {return false ;}else{ processing('<%="chotpxkid"+rs.getString("pxkId")%>' , '../../PhieuxuatkhoSvl?userId=<%=userId%>&chotphieu=<%= rs.getString("pxkId") %>&nppId=<%= obj.getNppId() %>&ngaylap=<%= rs.getString("ngaylapphieu").trim() %>');}" >
															</A>
														  <%} %>	   
														 
														 
														<%}else{ if(rs.getString("trangthai").equals("1")) { %>
														
														<%if(quyen[Utility.XEM]!=0){ %>
														 <A
															href="../../PhieuxuatkhoUpdateSvl?userId=<%=userId%>&display=<%= rs.getString("pxkId") %>"><img
																src="../images/Display20.png" alt="Hien thi" width="20"
																height="20" longdesc="Hien thi" border=0>
														</A>
														<%} %>
														
														<%if(quyen[Utility.XEM]!=0){ %>
														 <A
															href="../../PhieuxuatkhoPdfSvl?userId=<%=userId%>&pdf=<%=rs.getString("pxkId") %>"><img
																src="../images/pdf.jpg" alt="In file Pdf" width="20"
																height="20" longdesc="In file Pdf" border=0>
																<A href="../../Phieuxuatkho_DonhangPdfSvl?userId=<%=userId%>&pdf=<%=rs.getString("pxkId") %>"><img
																src="../images/Printer30.png" alt="In đơn hàng chi tiết Pdf" width="20"
																height="20" longdesc="In file Pdf" border=0>
																</A>
														<%} %>
																
														<%if(quyen[Utility.XEM]!=0){ %>
																<A href="../../PhieuXuatKhoExcelSvl?userId=<%=userId%>&excel=<%=rs.getString("pxkId") %>">
																<img src="../images/excel.gif" alt="In đơn hàng chi tiết Pdf" width="20"
																height="20" longdesc="In file Pdf" border=0>
																</A>
														<%} %>	
														
														 <% } else { %> 
														 <%if(quyen[Utility.XEM]!=0){ %>
														 <A href="../../PhieuxuatkhoUpdateSvl?userId=<%=userId%>&display=<%= rs.getString("pxkId") %>"><img
																src="../images/Display20.png" alt="Hien thi" width="20"
																height="20" longdesc="Hien thi" border=0>
														</A> <%} %>
														<%}} %>
														</TD>
													</TR>
													<%m++; }%>

													<tr class="tbfooter">
														<TD align="center" valign="middle" colspan="13"
															class="tbfooter">
															<%if(obj.getNxtApprSplitting() >1) {%> <img alt="Trang Dau"
															src="../images/first.gif" style="cursor: pointer;"
															onclick="View('pxkForm', 1, 'view')"> &nbsp; <%}else {%>
															<img alt="Trang Dau" src="../images/first.gif">
															&nbsp; <%} %> <% if(obj.getNxtApprSplitting() > 1){ %> <img
															alt="Trang Truoc" src="../images/prev.gif"
															style="cursor: pointer;"
															onclick="Prev('pxkForm', 'prev')"> &nbsp; <%}else{ %>
															<img alt="Trang Truoc" src="../images/prev_d.gif">
															&nbsp; <%} %> <%
													int[] listPage = obj.getNextSplittings();
													for(int i = 0; i < listPage.length; i++){
												%> <% 							
											
												if(listPage[i] == obj.getNxtApprSplitting()){ %> <a
															style="color: white;"><%= listPage[i] %>/ <%=obj.getTheLastSplitting() %></a>
															<%}else{ %> <a
															href="javascript:View('pxkForm', <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
															<%} %> <input type="hidden" name="list"
															value="<%= listPage[i] %>" /> &nbsp; <%} %> <% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
															&nbsp; <img alt="Trang Tiep" src="../images/next.gif"
															style="cursor: pointer;"
															onclick="Next('pxkForm', 'next')"> &nbsp; <%}else{ %>
															&nbsp; <img alt="Trang Tiep" src="../images/next_d.gif">
															&nbsp; <%} %> <%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
															<img alt="Trang Cuoi" src="../images/last.gif">
															&nbsp; <%}else{ %> <img alt="Trang Cuoi"
															src="../images/last.gif" style="cursor: pointer;"
															onclick="View('pxkForm', -1, 'view')"> &nbsp; <%} %>
														</TD>
													</tr>
												</TABLE></TD>
										</TR>
									</TABLE>
								</FIELDSET></TD>
						</TR>
					</TABLE> <!--end body Dossier-->
				</TD>
			</TR>
		</TABLE>
	</form>
</BODY>
</html>
<%
	try
	{
		if(!(nvgn == null))
			nvgn.close();
		if(rs!=null){
			rs.close();
		}
		if(obj != null){
			obj.DBclose(); 
			obj = null
		;}
		util=null;

		session.setAttribute("obj",null);
		
	}catch(Exception  e){}
	}
%>