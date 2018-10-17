
<%@page import="geso.traphaco.center.beans.sitecode_conv.Isitecode_conv"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.List" %>
<%@ page  import = "geso.traphaco.center.beans.nhaphanphoi.INhaphanphoi" %>
<%@ page  import = "geso.traphaco.center.beans.nhaphanphoi.INhaphanphoiList" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ 
		int[] quyen = new  int[5];
		quyen = util.Getquyen("18",userId);
		
		
	%>
	

<% Isitecode_conv obj = (Isitecode_conv)session.getAttribute("obj"); 
	ResultSet rs_sitecode_conv=obj.getsistecode_conv();
	obj.setNextSplittings();
%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
	<script type="text/javascript" src="../scripts/phanTrang.js"></script>
<SCRIPT language="javascript" type="text/javascript">
function clearform()
{
	document.forms['nppForm'].nppTen.value="";
	document.forms['nppForm'].TrangThai.value="";
    
    submitform();    
}

function submitform()
{
	document.forms['nppForm'].action.value= 'search';
	document.forms['nppForm'].submit();
}


function thongbao()
{var tt = document.forms['nppForm'].msg.value;
	if(tt.length>0)
    alert(document.forms['nppForm'].msg.value);
	}
	
function xuatExcel()
{
	document.forms['nppForm'].action.value= 'excel';
	document.forms['nppForm'].submit();
	document.forms['nppForm'].action.value= '';
}
	
</SCRIPT>
</HEAD>

<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="nppForm" method="post" action="../../sitecode_convSvl">
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="msg" value='<%=obj.getMsg() %>'>
<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>">
<input type="hidden" name="nxtApprSplitting"value="<%= obj.getNxtApprSplitting() %>">
<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF"><!--begin body Dossier-->
		<!--begin common dossier info---> <!--End common dossier info--->
		<TABLE width="100%" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left">
					  <table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
						   <TD align="left" colspan="2" class="tbnavigation">&nbsp;Thiết lập dữ liệu nền &gt; Dữ liệu nền kinh doanh &gt; Chi nhánh / Đối tác ERP </TD>
   
						   <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;  </TD>
						  </tr>
 					  </table>
					</TD>
				</TR>
		</TABLE>
		<TABLE width="100%" cellpadding="0" cellspacing="0">
				<TR>
					<TD>
					<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
						<TR>
							<TD width="100%" align="center" >
							<FIELDSET>				
							  <LEGEND class="legendtitle">Tiêu chí tìm kiếm &nbsp;</LEGEND>
						    
							<TABLE  width="100%" cellpadding="6" cellspacing="0">
								    <tr>  <TD width="22%" class="plainlabel">Tên của Chi nhánh / Đối tác (NPP) </TD>
								        <TD colspan="3" class="plainlabel">
								        	<INPUT name="nppTen" type="text" size="40" value="<%=obj.getten()%>" onChange="submitform();"></TD>
								  </TR>
								  <TR>
								    <TD class="plainlabel">Trạng thái </TD>
								    <TD colspan="3" class="plainlabel"><select name="TrangThai" onChange = "submitform();">
											<option value="" ></option>    
											<%
											String trangthai[]= {"Chưa tạo","Đã tạo"};
											for(int i=0;i<trangthai.length;i++)
											{
												if(obj.gettrangthai().equals(i+"")){
											%>
												<option value="<%=i%>" selected="selected"><%=trangthai[i]%></option>
												<%}else{ %>
												<option value="<%=i%>" ><%=trangthai[i]%></option>    
											 <% }}%>
										    </select></TD>
						      </TR>
							    <TR>
									<TD colspan="4" class="plainlabel">
									<a class="button2" href="javascript:clearform()">
	<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;	
<a class="button2" href="javascript:xuatExcel()"> <img style="top: -4px;" src="../images/button.png" alt="">Xuất Excel </a>&nbsp;&nbsp;&nbsp;&nbsp; 

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
		<TABLE width="100%" cellpadding="0" cellspacing="0">		
				<TR>
					<TD width="100%">
						<FIELDSET>
							<LEGEND class="legendtitle">&nbsp;Chi nhánh / Đối tác ERP
							
						</LEGEND>
	
						    <TABLE class="" width="100%">
						<TR>
							<TD width="100%">
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
								<TR class="tbheader">
									<TH width="4%">STT</TH>
									<TH width="13%">Tên Đối tác ERP </TH>
									<TH width="10%">Sitecode ERP</TH>
									<TH width="10%">NPP DMS </TH>
									<TH width="10%">Sitecode DMS</TH>
									<TH width="9%">Ngày tạo</TH>
									<TH width="12%">Người tạo </TH>
									<TH width="9%">Ngày sửa</TH>
									<TH width="11%">Người sửa </TH>
									<TH width="9%">Trạng thái </TH>
									<TH width="8%" align="center">&nbsp;Tác vụ</TH>
								</TR>
								
								<% 
									
									int m = 0;
									String lightrow = "tblightrow";
									String darkrow = "tbdarkrow";
									try{
									while (rs_sitecode_conv.next()){
										if (m % 2 != 0) {%>						
											<TR class= <%=lightrow%> >
										<%} else {%>
											<TR class= <%= darkrow%> >
										<%}%>
												<TD align="center"><%=m+1 %></TD>
												<TD align="left"><div align="left"><%=rs_sitecode_conv.getString("ten")%></div></TD>                                   
												<TD><div align="center"><%=rs_sitecode_conv.getString("sitecode")%></div></TD>    
												<%if(!rs_sitecode_conv.getString("trangthai").trim().equals("0")){ %>                               
												<TD><div align="center"><%= rs_sitecode_conv.getString("npptiennhiem")%></div></TD>
												<%}else{ %>
												<TD><div align="center"><%= rs_sitecode_conv.getString("tenupdate")%></div></TD>
												<%} %>
												<TD align="center"><%=rs_sitecode_conv.getString("convsitecode")%></TD>
												<TD align="center"><%=rs_sitecode_conv.getString("ngaytao")%></TD>
												<TD align="center"><%=rs_sitecode_conv.getString("nguoitao")%></TD>
												<TD align="center"><%=rs_sitecode_conv.getString("ngaysua")%></TD>
												<TD align="center"><%=rs_sitecode_conv.getString("nguoisua")%></TD>
												<% if (rs_sitecode_conv.getString("trangthai").equals("0")){ %>
													<TD align="center">Chưa tạo</TD>
												<%}else if(rs_sitecode_conv.getString("trangthai").equals("1")){%>
													<TD align="center">Đã tạo</TD>
												<%} %>
												<TD align="center">
												<TABLE border = 0 cellpadding="0" cellspacing="2">
													<TR>
												<% if (rs_sitecode_conv.getString("trangthai").equals("0")){ 
													if(!rs_sitecode_conv.getString("convsitecode").toString().equals("")){
														%>
														<TD>
														<%if(quyen[4]!=0){ %>
														<A href = "../../sitecode_convUpdateSvl?userId=<%=userId%>&chot=<%=rs_sitecode_conv.getString("sitecode")%>"><img src="../images/Chot.png" alt="Cap nhat" width="20" height="20" longdesc="Cap nhat" border = 0></A>
														<%} %>
														</TD>
														<TD>
														<%if(quyen[2]!=0){ %>
														<A href = "../../sitecode_convUpdateSvl?userId=<%=userId%>&update=<%=rs_sitecode_conv.getString("sitecode")%>"><img src="../images/Edit20.png" alt="Cap nhat" width="20" height="20" longdesc="Cap nhat" border = 0></A>
														<%} %>
														</TD>
														<% 
													}else{
												%>
													<TD>
														<%if(quyen[2]!=0 && rs_sitecode_conv.getString("madms").equals("0")  ){ %>
															<A href = "../../sitecode_convUpdateSvl?userId=<%=userId%>&update=<%=rs_sitecode_conv.getString("sitecode")%>"><img src="../images/Edit20.png" alt="Cap nhat" width="20" height="20" longdesc="Cap nhat" border = 0></A>
														<%} %>
													<% }} %>
													<%if(quyen[3]!=0){ %>
														<TD>
														<A href = "../../sitecode_convUpdateSvl?userId=<%=userId%>&display=<%=rs_sitecode_conv.getString("sitecode")%>"><img src="../images/Display20.png" alt="Hien thi" width="20" height="20" longdesc="Hien thi" border = 0></A>
													</TD>
													<%} %>
													</TR></TABLE>
												</TD>
											</TR>
								<%m++; }}
									catch(Exception er)
									{
										er.printStackTrace();
									}
								%>
																			
<tr class="tbfooter" > 
                               			
                               			<TD align="center" valign="middle" colspan="13" class="tbfooter">
											 	<%if(obj.getNxtApprSplitting() >1) {%>
													<img alt="Trang Dau" src="../images/first.gif" style="cursor: pointer;" onclick="View('nppForm', 1, 'view')"> &nbsp;
												<%}else {%>
													<img alt="Trang Dau" src="../images/first.gif" > &nbsp;
													<%} %>
												<% if(obj.getNxtApprSplitting() > 1){ %>
													<img alt="Trang Truoc" src="../images/prev.gif" style="cursor: pointer;" onclick="View('nppForm', eval(nppForm.nxtApprSplitting.value) -1, 'view')"> &nbsp;
												<%}else{ %>
													<img alt="Trang Truoc" src="../images/prev_d.gif" > &nbsp;
												<%} %>
												
												<%
													int[] listPage = obj.getNextSplittings();
													for(int i = 0; i < listPage.length; i++){
												%>
												
												<% 
												
												
												if(listPage[i] == obj.getNxtApprSplitting()){ %>
												
													<a  style="color:white;"><%= listPage[i] %>/ <%=obj.getTheLastSplitting() %></a>
												<%}else{ %>
													<a href="javascript:View('nppForm', <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
												<%} %>
													<input type="hidden" name="list" value="<%= listPage[i] %>" />  &nbsp;
												<%} %>
												
												<% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
													&nbsp; <img alt="Trang Tiep" src="../images/next.gif" style="cursor: pointer;" onclick="View('nppForm', eval(nppForm.nxtApprSplitting.value) +1, 'view')"> &nbsp;
												<%}else{ %>
													&nbsp; <img alt="Trang Tiep" src="../images/next_d.gif" > &nbsp;
												<%} %>
												<%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
											   		<img alt="Trang Cuoi" src="../images/last.gif" > &nbsp;
										   		<%}else{ %>
										   			<img alt="Trang Cuoi" src="../images/last.gif" style="cursor: pointer;" onclick="View('nppForm', -1, 'view')"> &nbsp;
										   		<%} %>
											</TD>
                               			
                               			
                               			 </tr> 

							</TABLE>
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