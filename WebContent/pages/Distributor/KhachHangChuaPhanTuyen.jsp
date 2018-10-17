
<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.distributor.beans.khachhang.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<% IKhachhangList obj = (IKhachhangList)session.getAttribute("obj"); %>
<%-- <% List<IKhachhang> khlist = (List<IKhachhang>)obj.getKhList(); %> --%>
<% ResultSet khlist = (ResultSet)obj.getKhList(); %>
<% ResultSet hch = (ResultSet)obj.getHangcuahang(); %>
<% ResultSet kbh = (ResultSet)obj.getKenhbanhang();  %>
<% ResultSet vtch = (ResultSet)obj.getVitricuahang();  %>
<% ResultSet lch = (ResultSet)obj.getLoaicuahang(); %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<script type="text/javascript" language="JavaScript" src="../scripts/simplecalendar.js"></script>

<SCRIPT language="javascript" type="text/javascript">
function clearform()
{
	document.khForm.khTen.value = "";
	document.khForm.mafast.value = "";
	document.khForm.hchTen.selectedIndex = 0;
	document.khForm.kbhTen.selectedIndex = 0;
	document.khForm.vtchTen.selectedIndex = 0;
	document.khForm.lchTen.selectedIndex = 0;
	submitform();
}

function submitform()
{
	document.forms['khForm'].action.value= 'search';
	document.forms['khForm'].submit();
}

function newform()
{
	document.forms['khForm'].action.value= 'new';
	document.forms['khForm'].submit();
}



</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="khForm" method="post" action="../../KhachhangChuaPhanTuyenSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="nppId" value="<%= obj.getNppId() %>" >
<input type="hidden" name="action" value="1" >


<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" 	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="2">
				<TR>
					<TD align="left" class="tbnavigation">
					   <table width="100%" border="0" cellpadding="0" cellspacing="0">
						  	<tr height="22">
							   <TD align="left" colspan="2" class="tbnavigation">&nbsp;Thiết lập dữ liệu nền > Dữ liệu nền kinh doanh > Khách hàng chưa phân tuyến</TD>
							   <TD colspan="2" align="right" class="tbnavigation">Chào mừng  <%= obj.getNppTen() %> &nbsp;</TD>
							</tr>
						</table>
					</TD>
		  		</TR>	
			</TABLE>
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
				<TR>
					<TD >
						<FIELDSET><LEGEND class="legendtitle">&nbsp;Tiêu chí tìm kiếm&nbsp;</LEGEND>
							<TABLE class="tblight" width="100%" cellspacing="0" cellpadding="6">
								
								<TR>
									<TD width="17%" class="plainlabel">Tên khách hàng</TD>
								    <TD width="29%" class="plainlabel">
											<INPUT name="khTen" type="text" value="<%= obj.getTen() %>" size="40" onChange = "submitform();">
								  </TD>
								  <TD class="plainlabel">
									</TD>
									<TD class="plainlabel">
									</TD>
									<%-- <TD width="16%" class="plainlabel" >Hạng cửa hàng</TD>
 
									<TD width="38%" class="plainlabel">
										<SELECT name="hchTen" onChange = "submitform();">
										  <option value=""> </option>
										  <% try{ while(hch.next()){ 
								    			if(hch.getString("hchId").equals(obj.getHchId())){ %>
								      				<option value='<%=hch.getString("hchId")%>' selected><%=hch.getString("hchTen") %></option>
								      			<%}else{ %>
								     				<option value='<%=hch.getString("hchId")%>'><%=hch.getString("hchTen") %></option>
								     			<%}}}catch(java.sql.SQLException e){} %>	 
                                        </SELECT>
								  </TD> --%>
								</TR>
								<TR>
								  	<TD class="plainlabel">Kênh bán hàng</TD>
								  	<TD  class="plainlabel"><SELECT name="kbhTen" onChange = "submitform();">
								    	<option value=""> </option>
									    <% try{ while(kbh.next()){ 
								    			if(kbh.getString("kbhId").equals(obj.getKbhId())){ %>
								      				<option value='<%=kbh.getString("kbhId")%>' selected><%=kbh.getString("kbhTen") %></option>
								      			<%}else{ %>
								     				<option value='<%=kbh.getString("kbhId")%>'><%=kbh.getString("kbhTen") %></option>
								     			<%}}}catch(java.sql.SQLException e){} %>
									  	</SELECT>
									</TD>
									<TD class="plainlabel">
									</TD>
									<TD class="plainlabel">
									</TD>
									<%-- <TD class="plainlabel">Vị trí cửa hàng</TD>
	                                <TD  class="plainlabel"><SELECT name="vtchTen" onChange = "submitform();">
    		                            <option value=""> </option>
            		                    <% try{ while(vtch.next()){ 
								    			if(vtch.getString("vtchId").equals(obj.getVtchId())){ %>
								      				<option value='<%=vtch.getString("vtchId")%>' selected><%=vtch.getString("vtchTen") %></option>
								      			<%}else{ %>
								     				<option value='<%=vtch.getString("vtchId")%>'><%=vtch.getString("vtchTen") %></option>
								     			<%}}}catch(java.sql.SQLException e){} %>
                                        </SELECT></TD> --%>
								</TR>
								<TR>
									<TD class="plainlabel">Loại cửa hàng</TD>
								  	<TD  class="plainlabel" ><SELECT name="lchTen"  onChange = "submitform();">
								    	<option value=""> </option>
								    	<% try{ while(lch.next()){ 
								    			if(lch.getString("lchId").equals(obj.getLchId())){ %>
								      				<option value='<%=lch.getString("lchId")%>' selected><%=lch.getString("lchTen") %></option>
								      			<%}else{ %>
								     				<option value='<%=lch.getString("lchId")%>'><%=lch.getString("lchTen") %></option>
								     			<%}}}catch(java.sql.SQLException e){} %>
										</SELECT>
									</TD>
									<TD class="plainlabel">Mã FAST</TD>
								    <TD  class="plainlabel">
											<INPUT name="mafast" type="text" value="<%= obj.getMaFAST()%>"  onChange = "submitform();">
								  </TD>
										
								</TR>
								<TR>
								    <TD class="plainlabel" colspan="4">
								    	<a class="button2" href="javascript:clearform()">
	<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;	    
								    <!--  
                                      <INPUT name="reinitialiser" type="button" value="Nhap lai" onClick="clearform();"> 
                                     --> </TD>
								</TR>
							</TABLE>
					  </FIELDSET>
					</TD>	
				</TR>
			</TABLE>
			<TABLE width="100%" border="0" cellspacing="0" cellpadding="2">
				<TR>
					<TD width="100%">
					<FIELDSET>
					<LEGEND class="legendtitle">&nbsp;Khách hàng chưa phân tuyến &nbsp;&nbsp;&nbsp;
					                          
					
					<!-- <INPUT name="new" type="button" value="Tao moi" onClick="newform();"> -->
					</LEGEND>
					<TABLE class="" width="100%">
						<TR>
							<TD width="98%">
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
								<TR class="tbheader">
									<TH width="13%">Mã khách hàng</TH>
									<TH width="13%">Mã FAST</TH>									
									<TH width="18%">Tên khách hàng</TH>
									<TH width="10%">Trạng thái</TH>
									<TH width="9%">Ngày tạo</TH>
									<TH width="15%">Người tạo </TH>
									<TH width="9%">Ngày sửa</TH>
									<TH width="15%">Người sửa</TH> 
									
								</TR>
								
						<%  int m = 0;
	                        String lightrow = "tblightrow";
	                        String darkrow = "tbdarkrow";
							if(khlist!=null)
							{%>				
								<% try{								
	                                while (khlist.next()){
	                                    	
	                                   	if (m % 2 != 0) {%>                     
	                                    	<TR class= <%=lightrow%> >
	                                    <%} else {%>
	                                       	<TR class= <%= darkrow%> >
	                                    	<%}%>
											<TD align="left"><div align="center"><%=khlist.getString("khId") %></div></TD>     
											<TD align="left"><div align="center"><%=khlist.getString("mafast") %></div></TD>                                
											<TD><div align="left"><%= khlist.getString("khTen")%></div></TD>
											<%
											/* if(khlist.getTrangthai() ==null ){
												khlist.setTrangthai("0");
								      		} */
											if (khlist.getString("trangthai").equals("1")){ %>
												<TD align="center">Hoạt động</TD>
											<%}else{%>
												<TD align="center">Ngưng hoạt động</TD>
											<%}%>
											
											<TD align="center"><%=khlist.getString("ngaytao")%></TD>
											<TD align="center"><%=khlist.getString("nguoitao")%></TD>
											<TD align="center"><%=khlist.getString("ngaysua")%></TD>
											<TD align="center"><%=khlist.getString("nguoisua")%></TD>
											
										</TR>
								<%m++; }}catch(java.sql.SQLException e){}								
								}%>
								

										<tr class="tbfooter" > 
											 <TD align="center" valign="middle" colspan="13" class="tbfooter">
											 <% obj.setNextSplittings(); %>
												 <script type="text/javascript" src="../scripts/phanTrang.js"></script>
												<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>" >
												<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>" >
												<input type="hidden" name="items" value="30">
												<input type="hidden" name="splittings" value="20">
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
											</TD>
										 </tr>  							</TABLE>
							</TD>
						</TR>
					</TABLE>
					</FIELDSET>
					</TD>
				</TR>
			</TBODY>
		</TABLE>

	</TR>
</TABLE>
</form>
</BODY>
</HTML>

<% 	

	try{
		
		if(hch != null)
			hch.close();
		if(kbh != null)
			kbh.close();
		if(lch != null)
			lch.close();
		if(vtch!=null){
			vtch.close();
		}
		
		if(obj != null){
			obj.DBclose();
			obj = null;
		}	
		/* if(khlist!=null){
			khlist.clear();
		} */
		session.setAttribute("obj",null);
	
	}
	catch (SQLException e) {}
	

%>
<%}%>