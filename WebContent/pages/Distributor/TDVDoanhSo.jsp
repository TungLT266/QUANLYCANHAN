
<%@page import="geso.traphaco.distributor.beans.tdvdoanhso.ITdvdoanhsoList"%>
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
	String url="";
	url = util.getUrl("TdvdoanhsoSvl","");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ 
	int[] quyen = new  int[6];
		quyen = util.Getquyen("TdvdoanhsoSvl","",userId);%>

<% ITdvdoanhsoList obj = (ITdvdoanhsoList)session.getAttribute("obj"); %>
<% ResultSet khlist = (ResultSet) obj.getKhList(); %>
<% ResultSet hch = (ResultSet)obj.getHangcuahang(); %>
<% ResultSet kbh = (ResultSet)obj.getKenhbanhang();  %>
<% ResultSet vtch = (ResultSet)obj.getVitricuahang();  %>
<% ResultSet lch = (ResultSet)obj.getLoaicuahang(); %>
<% ResultSet diadiemRs = (ResultSet)obj.getDiadiemRs()  ; %>
<% ResultSet tbhRs = (ResultSet)obj.getTbhRs()  ; %>
<% ResultSet ddkdRs = (ResultSet)obj.getDdkdRs()  ; %>

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



	<script type="text/javascript" src="../scripts/Timepicker/jquery.min.js"></script>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery-ui.min.js"></script>
  	<script type="text/javascript" src="../scripts/phanTrang.js"></script>
	<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
	<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
	<script type="text/javascript" src="../scripts/phanTrang.js"></script>
	<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>




<SCRIPT language="javascript" type="text/javascript">
$(document).ready(function() {		
	$( ".days" ).datepicker({			    
			changeMonth: true,
			changeYear: true				
	});            
}); 		


function clearform()
{


	document.khForm.thang.value = "";
	document.khForm.nam.value = "";
	document.khForm.ddkdId.value = "";
	document.khForm.trangthai.value = "";
	
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


function xuatExcel()
{
	document.forms['khForm'].action.value= 'excel';
	document.forms['khForm'].submit();
	
}


function xuatExcel_HoaDon()
{
	document.forms['khForm'].action.value= 'excel_hoadon';
	document.forms['khForm'].submit();
	
}

function thongbao()
{
	if(document.getElementById("msg").value != '')
		alert(document.getElementById("msg").value);
}
</SCRIPT>
</HEAD>

<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="khForm" method="post" action="../../TdvdoanhsoSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="nppId" value="<%= obj.getNppId() %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="currentPage" value="<%= obj.getCurrentPage() %>" >
<input type="hidden" name="msg" id="msg" value='<%= obj.getMsg() %>'>
<script type="text/javascript">
	thongbao();
</script>

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" 	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="2">
				<TR>
					<TD align="left" class="tbnavigation">
					   <table width="100%" border="0" cellpadding="0" cellspacing="0">
						  	<tr height="22">
							   <TD align="left" colspan="2" class="tbnavigation">&nbsp;<%=url %></TD>
							   <TD colspan="2" align="right" class="tbnavigation">Chào mừng  <%= userTen %> &nbsp;</TD>
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
									<TD class="plainlabel">Tháng<FONT class="erroralert">
													*</FONT></TD>
											<TD class="plainlabel"><SELECT name="thang"
												id="thang" onChange="submitform();">
													<option value=""></option>
													<%
														try{
															String []thang = {"1","2","3","4","5","6","7","8","9","10","11","12"};
															int t = 0;
												while(t < thang.length){ 
												if(thang[t].equals(obj.getThang())){
													%>
													<option value='<%=thang[t]%>' selected><%=thang[t]%></option>
													<%
														}else{
													%>
													<option value='<%=thang[t]%>'><%=thang[t]%></option>
													<%
														}
												t++;
												}}catch(Exception e ){e.printStackTrace();}
													%>
											</SELECT></TD>
											<TD class="plainlabel">Năm<FONT
												class="erroralert"> *</FONT>
											</TD>
											<TD class="plainlabel"><SELECT name="nam" id="TP"
												onChange="submitform();">
													<option value=""></option>
													<%
													int nam = 2015;
														try{while(nam < 4000){ 
													if(nam == Integer.parseInt(obj.getNam()))
													{
													%>
													<option value='<%=nam%>' selected><%=nam%></option>
													<%
														}else{
													%>
													<option value='<%=nam%>'><%=nam%></option>
													<%
														}
													nam++;
														}}catch(Exception e ){e.printStackTrace();}
													%>
											</SELECT></TD>
					
								</TR>
								
								
								<TR>
								
											<TD class="plainlabel">Trình dược viên</TD>
											<TD class="plainlabel" colspan="1"> <SELECT name="ddkdId"
												onChange="submitform();" >
													<option value=""></option>
													<%
														try{ while(ddkdRs.next()){ 
													if( obj.getDdkdId().indexOf(ddkdRs.getString("ddkdId")) >=0){
													%>
													<option value='<%=ddkdRs.getString("ddkdId")%>' selected>
														<%=ddkdRs.getString("ddkdTen")%></option>
													<%
														}else{
													%>
													<option value='<%=ddkdRs.getString("ddkdId")%>'>
														<%=ddkdRs.getString("ddkdTen")%></option>
													<%
														}}
														
														}catch(java.sql.SQLException e){}
													%>
											</SELECT> </TD>
											<TD width="120px" class="plainlabel" valign="top" >Trạng thái</TD>
									 <TD  class="plainlabel" valign="top">
										 <SELECT name ="trangthai" onChange = "submitform();">
                               
	                                        <% if (obj.getTrangthai().equals("1")){%>
	                                              <option value="1" selected>Đã chốt</option>
	                                              <option value="0">Chưa chốt</option>
	                                              <option value=""></option>
	                                              
	                                        <%}else if(obj.getTrangthai().equals("0")) {%>
	                                              <option value="0" selected>Chưa chốt</option>
	                                              <option value="1" >Đã chốt</option>
	                                              <option value="" > </option>
	                                              
	                                        <%}else{%>                                                                                        
	                                              <option value="1" >Đã chốt</option>
	                                              <option value="0" >Chưa chốt</option>
	                                              <option value="" selected> </option>
	                                        <%}%>
	                                     </SELECT>
									 </TD>
								  </TR>
								
								<TR>
								    <TD class="plainlabel" colspan="4">
								    	<a class="button2" href="javascript:clearform()">
										<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;	    
								     	
                                     </TD>
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
					<LEGEND class="legendtitle">&nbsp;Doanh số &nbsp;&nbsp;&nbsp;
					
					<%if(quyen[Utility.THEM]!=0){ %>
						<a class="button3" href="javascript:newform()">
	    				<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a> 
    				<%} %>
					</LEGEND>
					<TABLE class="" width="100%">
						<TR>
							<TD width="98%">
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
								<TR class="tbheader">
									<TH width="8%">Mã trình dược viên</TH>
									<TH width="8%">Tên trình dược viên</TH>
									<TH width="8%">Tháng</TH>
									<TH width="8%">Năm</TH>
									<TH width="12%">Trạng thái</TH>
									<TH width="12%">Ngày tạo</TH>
									<TH width="13%">Người tạo</TH>
									<TH width="12%">Ngày sửa</TH>
									<TH width="13%">Người sửa</TH>
									<TH width="12%">Tác vụ</TH>
								</TR>
								
						<%  														
	                        int m = 0;
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
											<TD align="left"><div align="center"><%=khlist.getString("MATDV") %></div></TD>     
											<TD align="left"><div align="center"><%=khlist.getString("TenTDV") %></div></TD>
											<TD><div align="left"><%= khlist.getString("thang")%></div></TD>  
											<TD align="left"><%=khlist.getString("nam")==null?" ":khlist.getString("nam")%></TD>            
											
											<%
										
											if (khlist.getString("trangthai").equals("1")){ %>
												<TD align="center">Đã chốt</TD>
											<%}else{%>
												<TD align="center"> Chưa chốt</TD>
											<%}%>
											<TD align="center"><%=khlist.getString("ngaytao")%></TD>
											<TD align="center"><%=khlist.getString("nguoitao")%></TD>
											<TD align="center"><%=khlist.getString("ngaysua")%></TD>
											<TD align="center"><%=khlist.getString("nguoisua")%></TD>
											<TD align="center">
											<TABLE border = 0 cellpadding="0" cellspacing="0">
												<TR>
												<TD>
												<%if(quyen[Utility.THEM]!=0 && khlist.getString("trangthai").equals("0")){ %>
													<A href = "../../TdvdoanhsoupdateSvl?userId=<%=userId%>&update=<%=khlist.getString("matdv") %>&thang =<%=khlist.getString("thang")%>&nam =<%=khlist.getString("nam")%>"> <img src="../images/Edit20.png" alt="Cap nhat" width="20" height="20" longdesc="Cap nhat" border = 0></A>
												<%} %>
												</TD>
												<TD>&nbsp;</TD>
												<TD>
												<%if(quyen[Utility.XOA]!=0 && khlist.getString("trangthai").equals("0")){ %>
													<A href = "../../TdvdoanhsoSvl?userId=<%=userId%>&delete=<%=khlist.getString("matdv") %>&thang =<%=khlist.getString("thang")%>&nam =<%=khlist.getString("nam")%>"><img src="../images/Delete20.png" alt="Xoa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Ban Muon Xoa doanh so nay?')) return false;"></A>
												<%} %>	
													</TD>
													<TD>
														<%if(khlist.getString("trangthai").equals("0")){ %>
											<A id='chotphieu<%= khlist.getString("matdv")%>' href="../../TdvdoanhsoSvl?userId=<%=userId%>&chot=<%= khlist.getString("matdv") %>&thang =<%=khlist.getString("thang")%>&nam =<%=khlist.getString("nam")%>">
												
												<img src="../images/Chot.png" alt="Chốt" width="20" height="20" title="Chốt" border="0" onclick="if(!confirm('Bạn có muốn chốt doanh số này?')) {return false ;}">	
											</A>
											<% } %>
													</TD>
													<TD>
														<% if(quyen[3]!=0 && khlist.getString("trangthai").equals("1")){ %><A href="../../TdvdoanhsoSvl?userId=<%=userId%>&display=<%= khlist.getString("matdv")%>&thang =<%=khlist.getString("thang")%>&nam =<%=khlist.getString("nam")%>">
											<IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0></A>
										 <% } %>
													</TD>
												</TR></TABLE>
											</TD>
										</TR>
								<%m++; }}catch(java.sql.SQLException e){e.printStackTrace();}
							}%>
								
										 <tr class="tbfooter" > 
											 <TD align="center" valign="middle" colspan="13" class="tbfooter">
											 <% obj.setNextSplittings(); %>
												 <script type="text/javascript" src="../scripts/phanTrang.js"></script>
												<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>" >
												<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>" >

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
		
		session.setAttribute("obj",null);
	
	}
	catch (SQLException e) {}
	

%>
<%}%>