
<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.distributor.beans.khachhang.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<% IKhachhangList obj = (IKhachhangList)session.getAttribute("obj"); %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ 
	int[] quyen = new  int[6];
		quyen = util.Getquyen("KhachhangDuyetSvl", "&capduyet=" + obj.getCapduyet(), userId);%>

<% ResultSet khlist = (ResultSet) obj.getKhList(); %>
<% ResultSet hch = (ResultSet)obj.getHangcuahang(); %>
<% ResultSet kbh = (ResultSet)obj.getKenhbanhang();  %>
<% ResultSet vtch = (ResultSet)obj.getVitricuahang();  %>
<% ResultSet lch = (ResultSet)obj.getLoaicuahang(); %>
<% ResultSet diadiemRs = (ResultSet)obj.getDiadiemRs()  ; %>
<% ResultSet tbhRs = (ResultSet)obj.getTbhRs()  ; %>
<% ResultSet ddkdRs = (ResultSet)obj.getDdkdRs()  ; %>
<% ResultSet tructhuocRs = (ResultSet)obj.getTructhuocRs(); %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
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
	document.khForm.khTen.value = "";
//	document.khForm.hchTen.selectedIndex = 0;
	document.khForm.kbhTen.selectedIndex = 0;
// 	document.khForm.vtchTen.selectedIndex = 0;
// 	document.khForm.lchTen.selectedIndex = 0;
	document.khForm.maFAST.value = "";
	document.khForm.diachi.value = "";
	document.khForm.ddkdId.value = "";
	document.khForm.trangthai.value = "";
	document.khForm.tbhId.value = "";
	document.khForm.tungay.value = "";
	document.khForm.denngay.value = "";
	document.khForm.loaikh.value = "";
	document.khForm.hopdong.value = "";
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

function saveform()
{
	var trangthaiNEW = document.getElementsByName("trangthaiNEW");
	var ttNEW = '';
	for( i = 0; i < trangthaiNEW.length; i++ )
	{
		if( trangthaiNEW.item(i).checked == true )
			ttNEW += trangthaiNEW.item(i).value + '-1,';
		else
			ttNEW += trangthaiNEW.item(i).value + '-0,';
	}
	
	//alert( 'TT NEW: ' + ttNEW );
	document.getElementById("trangthaiCAPNHAT").value = ttNEW;
	
	document.forms['khForm'].action.value= 'changeTRANGTHAI';
	document.forms['khForm'].submit();
}

function checkALL()
{
	 var chkALL = document.getElementById("chkALL");
	 var khIds = document.getElementsByName("khIds");
	 
	 if(chkALL.checked == true )
	 {
		 for(var i=0; i < khIds.length; i++){
			 khIds[i].checked = true;
		 }
	 }
	 else
	 {
		 for(var i=0; i < khIds.length; i++){
			 khIds[i].checked = false;
		 }
	 }
}

function DuyetALL()
{
	 var donhangIds = document.getElementsByName("khIds");
	 var _dhIds = '';
	 for(var i=0; i < donhangIds.length; i++){
		 
		 _dhIds += donhangIds[i].value + ',';
	 }
	 
	 if(_dhIds == '' )
	 {
		 alert('Vui lòng chọn khách hàng');
		 return;
	 }		
	 
	 
	 var r = confirm("Bạn chắc chắn muốn duyệt tất cả khách hàng? ");
	 if (r == false) {		 
	     return;
	 }
	 
	document.forms["khForm"].action.value = "duyetALL";
 	document.forms["khForm"].submit();
		       		
}

</SCRIPT>
</HEAD>

<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="khForm" method="post" action="../../KhachhangDuyetSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="nppId" value="<%= obj.getNppId() %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="currentPage" value="<%= obj.getCurrentPage() %>" >
<input type="hidden" name="msg" id="msg" value='<%= obj.getMsg() %>'>
<input type="hidden" name="trangthaiCAPNHAT" id='trangthaiCAPNHAT' value=''>
<input type="hidden" name="capduyet" id='capduyet' value='<%= obj.getCapduyet() %>'>

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
							   <TD align="left" colspan="2" class="tbnavigation">Dữ liệu nền > Kinh doanh > Duyệt khách hàng (<%= obj.getCapduyet() %>)</TD>
							   <TD colspan="2" align="right" class="tbnavigation">Chào mừng  <%= userTen %> </TD>
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
								<TR >
									<TD class="plainlabel" valign="top" >Chọn MTV / NPP</TD>
								  	 <TD class="plainlabel" colspan="3" >
									  	<SELECT name="tructhuocId" onChange = "submitform();" class = "select2" style = "width:200px">
									    	<option value=""> </option>
										    <% try{ while(tructhuocRs.next()){ 
								    			if(tructhuocRs.getString("PK_SEQ").equals( obj.getTructhuocId() )){ %>
								      				<option value='<%=tructhuocRs.getString("PK_SEQ")%>' selected><%= tructhuocRs.getString("TEN") %></option>
								      			<%}else{ %>
								     				<option value='<%= tructhuocRs.getString("PK_SEQ")%>'><%= tructhuocRs.getString("TEN") %></option>
								     			<%}}} catch(Exception e){} %>
										 </SELECT>
									</TD>
								</TR>
								<TR>
									<TD width="130px" class="plainlabel">Mã / Tên khách hàng</TD>
								    <TD width="300px" class="plainlabel">
										<INPUT name="khTen" type="text" value="<%= obj.getTen() %>" size="40" onChange = "submitform();">
								  	</TD>
								  	<TD width="120px" class="plainlabel" valign="top" >Kênh bán hàng</TD>
								  	  <TD class="plainlabel" valign="top">
										  	<SELECT name="kbhTen" onChange = "submitform();">
										    	<option value=""> </option>
											    <% try{ while(kbh.next()){ 
										    			if(kbh.getString("kbhId").equals(obj.getKbhId())){ %>
										      				<option value='<%=kbh.getString("kbhId")%>' selected><%=kbh.getString("kbhTen") %></option>
										      			<%}else{ %>
										     				<option value='<%=kbh.getString("kbhId")%>'><%=kbh.getString("kbhTen") %></option>
										     			<%}}}catch(java.sql.SQLException e){} %>
											 </SELECT>
									</TD>
								  
								</TR>
								
								<TR>
									<TD class="plainlabel" valign="top" >Mã cũ</TD>
								    <TD class="plainlabel" valign="top" >
									<INPUT name="maFAST" type="text" value="<%= obj.getMaFAST() %>" size="40" onChange = "submitform();">
								    </TD>
									<TD width="120px" class="plainlabel" valign="top" >Trình dược viên</TD>
								  	  <TD class="plainlabel" valign="top">
										  	<SELECT name="ddkdId" onChange = "submitform();">
										    	<option value=""> </option>
											    <% try{ while(ddkdRs.next()){ 
										    			if(ddkdRs.getString("ddkdId").equals(obj.getDdkdId()    )){ %>
										      				<option value='<%=ddkdRs.getString("ddkdId")%>' selected>
										      				<%=ddkdRs.getString("ddkdTen") %></option>
										      			<%}else{ %>
										     				<option value='<%=ddkdRs.getString("ddkdId")%>'>
										     				<%=ddkdRs.getString("ddkdTen") %></option>
										     			<%}}}catch(java.sql.SQLException e){} %>
											 </SELECT>
									</TD>
								</TR>
								
								
								<TR>
								<TD class="plainlabel" valign="top" >Địa chỉ</TD>
								    <TD class="plainlabel" valign="top" >
										<INPUT name="diachi" type="text" value="<%= obj.getDiachi() %>" size="40" onChange = "submitform();">
								  </TD>
									  <TD width="120px" class="plainlabel" valign="top" >Tuyến bán hàng</TD>
												  	  <TD class="plainlabel" valign="top">
														  	<SELECT name="tbhId" onChange = "submitform();">
														    	<option value=""> </option>
															    <% try{ while(tbhRs.next()){ 
														    			if(tbhRs.getString("tbhId").equals(obj.getTbhId()  )){ %>
														      				<option value='<%=tbhRs.getString("tbhId")%>' selected><%=tbhRs.getString("tbhTen") %></option>
														      			<%}else{ %>
														     				<option value='<%=tbhRs.getString("tbhId")%>'><%=tbhRs.getString("tbhTen") %></option>
														     			<%}}}catch(java.sql.SQLException e){} %>
															 </SELECT>
									 </TD>
								  </TR>
								  
								  
								  <TR>
								  	 <TD class="plainlabel" >Từ ngày </TD>
								  	 <TD class="plainlabel" >
								  	 	<input type="text" class="days" name="tungay" value="<%= obj.getTungay() %>" maxlength="10" onchange="submitform();" />
								  	 </TD>
								  	 
								  	 <TD class="plainlabel" >Đến ngày </TD>
								  	 <TD class="plainlabel" >
								  	 	<input type="text" class="days" name="denngay" value="<%= obj.getDenngay() %>" maxlength="10" onchange="submitform();" />
								  	 </TD>
								  	 
								  	 
								  </TR>
								  
								  <TR>
								  	 <TD class="plainlabel" > Loại khách hàng </TD>
								  	 <TD class="plainlabel" >
								  	 	<select name="loaikh" onchange="submitform();">
								  	 	<option value="" > </option>
								  	 	
								  	 	</select>
								  	 </TD>
								  	 	 <TD class="plainlabel" > Hợp đồng </TD>
								  	 	<TD class="plainlabel" >
								  	 		<select name="hopdong" onchange="submitform();">
								  	 		<%if(obj.getHopdong().equals("0")){ %>
									  	 		<option></option>
									  	 		<option value="0" selected="selected"> Có hợp đồng</option>
									  	 		<option value="1"> Không có hợp đồng</option>
									  	 	<%} else if(obj.getHopdong().equals("1")){ %>
									  	 		<option></option>
									  	 		<option value="0"> Có hợp đồng</option>
									  	 		<option value="1" selected="selected"> Không có hợp đồng</option>	
									  	 	<% } else{ %>
									  	 		<option></option>
									  	 		<option value="0"> Có hợp đồng</option>
									  	 		<option value="1"> Không có hợp đồng</option>
									  	 	<%} %>
								  	 		</select>
								  	 </TD>
								  </TR>
								  
								  <TR>
								
					  				<TD width="120px" class="plainlabel" valign="top" >Trạng thái</TD>
									 <TD  class="plainlabel" valign="top">
										 <SELECT name ="trangthai" onChange = "submitform();">
                               
	                                        <% if (obj.getTrangthai().equals("1")){%>
	                                              <option value="1" selected>Hoạt động</option>
	                                              <option value="0">Ngưng hoạt động</option>
	                                              <option value=""></option>
	                                              
	                                        <%}else if(obj.getTrangthai().equals("0")) {%>
	                                              <option value="0" selected>Ngưng hoạt động</option>
	                                              <option value="1" >Hoạt động</option>
	                                              <option value="" > </option>
	                                              
	                                        <%}else{%>                                                                                        
	                                              <option value="1" >Hoạt động</option>
	                                              <option value="0" >Ngưng hoạt động</option>
	                                              <option value="" selected> </option>
	                                        <%}%>
	                                     </SELECT>
									 </TD>
									 
							<TD width="120px" class="plainlabel" valign="top" >Mã số thuế</TD>
										<TD class="plainlabel"colspan="4" >
								  	 	<input type="text" name="mst" value="<%= obj.getMst() %>" maxlength="15" onchange="submitform();" />
								  	 </TD>
								  </TR>
								<TR>
									<td class="plainlabel">Số items
			                        </td>
			                        <td class="plainlabel" colspan=3>
										<input type="text" name="soItems" value="<%= obj.getSoItems() %>" onchange="submitform()">
			                        </td>	
								</TR>
								<TR>
								    <TD class="plainlabel" colspan="4">
								    	<a class="button2" href="javascript:clearform()">
										<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;	    
								     	
								     	<!-- <a class="button2" href="javascript:xuatExcel()"> <img style="top: -4px;" src="../images/button.png" alt="">Xuất Excel </a> &nbsp;&nbsp;&nbsp;&nbsp; -->
								     
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
					<LEGEND class="legendtitle">&nbsp;Khách hàng &nbsp;&nbsp;&nbsp;
					</LEGEND>
					<TABLE class="" width="100%">
						<TR>
							<TD width="98%">
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
								<TR class="tbheader">
									<TH width="10%">Mã khách hàng</TH>
									<TH width="10%">Mã cũ</TH>
									<TH width="20%">Tên khách hàng</TH>
									<TH width="10%">Mã HĐ</TH>
									<TH width="10%">Trạng thái</TH>
									<TH width="20%">Nhân viên bán hàng</TH>
									<TH width="10%">Tác vụ</TH>
									
									<TH align="center" style="width: 10%" >Duyệt tất cả<br /> 
				                    	<input type="checkbox" id="chkALL" onchange="checkALL();" >  
				                    	<A href = "javascript:DuyetALL();"><IMG src="../images/Chot.png" title="Duyệt tất cả khách hàng" border=0></A>
				                    </TH>
								</TR>
								
						<%  														
	                        int m = 0;
	                        String lightrow = "tblightrow";
	                        String darkrow = "tbdarkrow";
							if(khlist!=null)
							{%>
							<% try {								
                                while (khlist.next()){
                                    	
                                	String trangthai = "Chờ duyệt <br />";
                                	boolean duocphepduyet = true;
                     				
                     				if( obj.getCapduyet().equals("TL") ) {
                     					
    	                 				if(khlist.getString("TL_DUYET").equals("1"))
    	                 					trangthai = " Đã duyệt <br /> ";
    	                 					
    	                 				if(khlist.getString("CS_DUYET").equals("1"))
    	                 					trangthai = " <i>CS Đã duyệt </i><br /> ";
    	                 				
    	                 				if( khlist.getString("ASM_DUYET").equals("1") || khlist.getString("CS_DUYET").equals("1") )
    	                 					duocphepduyet = false;
    	                 					
                     				}	
                     				else if( obj.getCapduyet().equals("ASM") ) {
                     					
    	                 				if(khlist.getString("ASM_DUYET").equals("1"))
    	                 					trangthai = " Đã duyệt <br /> ";
    	                 					
    	                 				if(khlist.getString("CS_DUYET").equals("1"))
    	                 					trangthai = " <i>CS Đã duyệt </i><br /> ";
    	                 					
    	                 				if( khlist.getString("CS_DUYET").equals("1") )
    	                 					duocphepduyet = false;
                     				}	
                     				else
                     				{
                     					if(khlist.getString("CS_DUYET").equals("1"))
                         					trangthai = "Đã duyệt <br /> ";
                         				else
                        				{
	   	                 					if(khlist.getString("TL_DUYET").equals("0"))
	   		                 					trangthai += " <i>( TL chưa duyệt )</i><br /> ";	
	                    				
	   	                 					if(khlist.getString("ASM_DUYET").equals("0"))
	   		                 					trangthai += " <i>( ASM chưa duyệt )</i><br /> ";	
                        				}
                     				}
                                	
                                   	if (m % 2 != 0) {  
  
                                   	%>                     
                                    	<TR class= <%=lightrow%> >
                                    <%} else {%>
                                       	<TR class= <%= darkrow%> >
                                    	<%}%>
											<TD align="left"><div align="center"><%=khlist.getString("MA") %></div></TD>     
											<TD align="left"><div align="center"><%=khlist.getString("mafast") %></div></TD>
											<TD><%= khlist.getString("khTen")%></TD>  
											<TD align="left"><%= khlist.getString("mahd")==null?" ":khlist.getString("mahd")%></TD>     
											<TD align="left"><%= trangthai %></TD>                                              
											<TD align="left"><%=khlist.getString("tbhTen")==null?" ":khlist.getString("tbhTen")%></TD>

											<TD align="center">
											
												<% if(quyen[Utility.CHOT] != 0) { 
	                   				
					                   				if(obj.getCapduyet().equals("TL")) { %>
					                   				
					                   				<% if(!khlist.getString("TL_DUYET").equals("0") || !duocphepduyet ){ %>
				                             			<A href = "../../KhachhangUpdateSvl?userId=<%=userId%>&display=<%= khlist.getString("khId") %>&capduyet=<%= obj.getCapduyet() %>"><IMG src="../images/Display20.png" alt="Xem thông tin khách hàng" title="Xem thông tin khách hàng" border=0></A>
					                    			<%} else { %>
				                             			<A href = "../../KhachhangUpdateSvl?userId=<%=userId%>&duyet=<%= khlist.getString("khId") %>&capduyet=<%= obj.getCapduyet() %>"><IMG src="../images/Chot.png" alt="Duyệt khách hàng" title="Duyệt khách hàng" border=0></A>
					                    			
						                   			<%  } } else if(obj.getCapduyet().equals("ASM")) { %>
						                   				
						                   				<% if( khlist.getString("ASM_DUYET").equals("1") || !duocphepduyet ){ %>
					                             			<A href = "../../KhachhangUpdateSvl?userId=<%=userId%>&display=<%= khlist.getString("khId") %>&capduyet=<%= obj.getCapduyet() %>"><IMG src="../images/Display20.png" alt="Xem thông tin khách hàng" title="Xem thông tin khách hàng" border=0></A>
						                    			<%} else { %>
					                             			<A href = "../../KhachhangUpdateSvl?userId=<%=userId%>&duyet=<%= khlist.getString("khId") %>&capduyet=<%= obj.getCapduyet() %>"><IMG src="../images/Chot.png" alt="Duyệt khách hàng" title="Duyệt khách hàng" border=0></A>
						                   				
						                   			<% } } else { %> 
					                   			
					                   				<% if( khlist.getString("CS_DUYET").equals("1") || !duocphepduyet ){ %>
				                             			<A href = "../../KhachhangUpdateSvl?userId=<%=userId%>&display=<%= khlist.getString("khId") %>&capduyet=<%= obj.getCapduyet() %>"><IMG src="../images/Display20.png" alt="Xem thông tin khách hàng" title="Xem thông tin khách hàng" border=0></A>
					                    			<%} else { %>
				                             			<A href = "../../KhachhangUpdateSvl?userId=<%=userId%>&duyet=<%= khlist.getString("khId") %>&capduyet=<%= obj.getCapduyet() %>"><IMG src="../images/Chot.png" alt="Duyệt khách hàng" title="Duyệt khách hàng" border=0></A>
					                   				
					                   			<% } } } %>
												
											</TD>
											
											<Td align="center" >
						                    	
	                   						<% if(quyen[Utility.CHOT] != 0) { %>
					                   			   <% if(obj.getCapduyet().equals("TL")) { %>
					                   				
					                   					<% if( khlist.getString("TL_DUYET").equals("0") && duocphepduyet ){ %>
								                    		<input type="checkbox" name="khIds" value="<%= khlist.getString("khId") %>" >
								                    	<% } %>
					                    			
						                   			<%  } else if(obj.getCapduyet().equals("ASM") && duocphepduyet ) { %>
						                   				
						                   				<% if(khlist.getString("ASM_DUYET").equals("0")){ %>
								                    		<input type="checkbox" name="khIds" value="<%= khlist.getString("khId") %>" >
								                    	<% } %>
						                   				
						                   			<% } else { %> 
					                   			
					                   					<% if(khlist.getString("CS_DUYET").equals("0") && duocphepduyet ){ %>
								                    		<input type="checkbox" name="khIds" value="<%= khlist.getString("khId") %>" >
								                    	<% } %>
					                   				
					                   				<% } %>
						                    <% } %>	
						                    	
						                    </Td>
						                    
										</TR>
								<% m++; }} catch(Exception e){e.printStackTrace();}
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