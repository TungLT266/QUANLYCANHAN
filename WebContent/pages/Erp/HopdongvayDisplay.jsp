<%@page import="geso.traphaco.erp.beans.vayvon.IHopdongvay"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.List" %>
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
  <%
  	IHopdongvay obj = (IHopdongvay)session.getAttribute("obj") ;
  	ResultSet nhRs = obj.getNhRs();
  	ResultSet cnRs = obj.getCnRs();
  	ResultSet ttRs = obj.getTtRs1();
  	ResultSet ntRs = obj.getTtRs2();
  	ResultSet ntvlist = obj.getNtvRs();
  %>
<%  NumberFormat formatter = new DecimalFormat("#,###,###"); %>    
  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<head>
	<TITLE>TraphacoHYERP - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
    <LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
    <LINK rel="stylesheet" href="../css/main.css" type="text/css">
    <LINK rel="stylesheet" href="../css/datepicker.css" type="text/css">
	<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">	    
    <script language="javascript" src="../scripts/datepicker.js"></script>
   	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css"/>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery.min.js"></script>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery-ui.min.js"></script>
   
  	<script type="text/javascript" src="..scripts/jquery-1.js"></script>
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
	<script src="../scripts/ui/jquery.ui.datepicker.js"	type="text/javascript"></script>
	
	<script type="text/javascript">
	$(document).ready(function() {		
			$( ".days" ).datepicker({			    
					changeMonth: true,
					changeYear: true				
			});            
        }); 		
		
</script>
	
    <script type="text/javascript">
        $(document).ready(function(){
            $(".button").hover(function(){
                $(".button img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
		$(document).ready(function(){
            $(".button2").hover(function(){
                $(".button2 img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
    </script>
	
   <script>
	  $(document).ready(function() {
			$("#accordion").accordion({autoHeight: false}); //autoHeight content set false
			$( "#accordion" ).accordion( "option", "icons", { 'header': 'ui-icon-plus', 'headerSelected': 'ui-icon-minus' } );
	  });
  </script>
  
    <link media="screen" rel="stylesheet" href="../css/colorbox.css">
	<!-- <script src="../scripts/colorBox/jquery.min.js"></script> -->
    <script src="../scripts/colorBox/jquery.colorbox.js"></script>
    <script>
        $(document).ready(function()
        {			
            $(".button2").colorbox({width:"30%", inline:true, href:"#inline_example1"});
            //Example of preserving a JavaScript event for inline calls.
            $("#click").click(function(){ 
                $('#click').css({"background-color":"#f00", "color":"#fff", "cursor":"inherit"}).text("SalesUp - Project.");
                return false;
            });
        });
   </script>
 <SCRIPT language="javascript" type="text/javascript">
						
	function newform()
	{ 	
		document.forms['hdvForm'].action.value= 'save';
		document.forms['hdvForm'].submit();
	}

	function isNumberKey2(evt)
	{
	   var charCode = (evt.which) ? evt.which : event.keyCode;
	   if (charCode > 31 && (charCode < 48 || charCode > 57) )
	      return false;
	
	   return true;
	}
	
	function DinhDangTien(num) 
	 {
	    num = num.toString().replace(/\$|\,/g,'');
	    if(isNaN(num))
	    num = "0";
	    sign = (num == (num = Math.abs(num)));
	    num = Math.floor(num*100);
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
	
</SCRIPT>
 
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">

<form name="hdvForm" method="post" action="../../HopdongvayUpdateSvl">
<input type="hidden" name="userId" value='<%=userId%>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="Id" value='<%= obj.getId()%>'>

<div id="main" style="width:100%">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">&nbsp;Quản lý kế toán > Chức năng > Hợp đồng vay > Hiển thị
		</div>
		    
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn <%=userTen%>
        </div>
    </div>
    <div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        <div style="width:100%; float:none" align="left">
             			<TABLE border="0" width="100%" >
							<TR>
						  		<TR ><TD >
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
												<TR class = "tbdarkrow">
													<TD width="30" align="left"><A href="../../HopdongvaySvl?userId=<%=userId %>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
												    <TD width="2" align="left" ></TD>
												</TR>
										</TABLE>
								</TD></TR>
								<tr>
							<TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
								<LEGEND class="legendtitle">Thông báo </LEGEND>
				
			    				<textarea name="dataerror"  cols="100%" rows="1" ><%=obj.getmsg() %> </textarea>
								
								</FIELDSET>
						   </TD>
					     </tr>
					     <tr>
					     <td>
					     <FIELDSET>
								<LEGEND class="legendtitle">Hợp đồng vay</LEGEND>
									<TABLE class="tblight" width="100%" cellpadding="6" cellspacing = "0" border = "0">
										<TR>
											<TD width="15%" class="plainlabel" >Số hợp đồng </TD>
										  <TD  class="plainlabel" colspan = "2"><INPUT name="sohd" type="text" size="40" value="<%= obj.getSoHD() %>" readonly=readonly></TD>
										</TR>

										<TR>
											<TD width="15%" class="plainlabel" >Diễn giải </TD>
										  <TD  class="plainlabel" colspan = "2"><INPUT name="diengiai" type="text" size="40" value="<%= obj.getDiengiai() %>" style = "width:400" readonly=readonly></TD>
										</TR>

					                    <TR>
										  <TD  class="plainlabel" >Ngày</TD>
										  <TD  class="plainlabel" colspan = "2"><INPUT name="ngay" type="text" size="20" value="<%= obj.getNgay() %>" readonly = readonly></TD>
										</TR> 
										
										<TR>
											<TD  class="plainlabel" >Ngân hàng</TD>
											<TD class="plainlabel" colspan = "2">
											<SELECT NAME = "nhId" readonly = readonly>
														<OPTION VALUE = "">&nbsp;</OPTION>
										<% if (nhRs != null){
												while(nhRs.next()){
													if(nhRs.getString("NHID").equals(obj.getNhId())){
											%>																	
														<OPTION VALUE = "<%= nhRs.getString("NHID") %>" SELECTED><%= nhRs.getString("NH")%></OPTION>
														
										<%			}else{ %>
													
														<OPTION VALUE = "<%= nhRs.getString("NHID") %>" ><%= nhRs.getString("NH")%></OPTION>
																
										<% 			}
												}
											}%>
											
											</SELECT>
											</TD>											
										
										<TR>
											<TD  class="plainlabel" >Chi nhánh</TD>
											<TD class="plainlabel" colspan = "2">
												<SELECT NAME = "cnId" readonly=readonly >
														<OPTION VALUE = "">&nbsp;</OPTION>
										<% if (cnRs != null){
												while(cnRs.next()){
													if(cnRs.getString("CNID").equals(obj.getCnId())){
											%>																	
														<OPTION VALUE = "<%= cnRs.getString("CNID") %>" SELECTED><%= cnRs.getString("CN") %></OPTION>
														
										<%			}else{ %>
													
														<OPTION VALUE = "<%= cnRs.getString("CNID") %>" ><%= cnRs.getString("CN") %></OPTION>
																
										<% 			}
												}
											}%>
											
												</SELECT>
											</TD>
										<TR>

					                    <TR>
										  <TD  class="plainlabel" >Số tiền</TD>
										  <TD  class="plainlabel" width = "10%"><INPUT name="tongtien" type="text" size="20" value="<%=formatter.format(Double.parseDouble(obj.getTongtien()))%>" onkeyup="Dinhdang(this)" onkeypress="return isNumberKey2(event)" readonly=readonly></TD>
										  <TD class="plainlabel"  >
												<SELECT NAME = "ttId" style = "width:100" readonly=readonly>
														<OPTION VALUE = "">&nbsp;</OPTION>
										<% if (ttRs != null){
												while(ttRs.next()){
													if(ttRs.getString("TTID").equals(obj.getTtId())){
											%>																	
														<OPTION VALUE = "<%= ttRs.getString("TTID") %>" SELECTED><%= ttRs.getString("TT") %></OPTION>
														
										<%			}else{ %>
													
														<OPTION VALUE = "<%= ttRs.getString("TTID") %>" ><%= ttRs.getString("TT") %></OPTION>
																
										<% 			}
												}
											}%>
											
												</SELECT>
											</TD>
										  
										</TR> 

					                    <TR>
										  <TD  class="plainlabel" >Số tiền</TD>
										  <TD  class="plainlabel" width = "10%"><INPUT name="tongngoaite" type="text" size="20" style = "text-align: right" value="<%=formatter.format(Double.parseDouble(obj.getTongngoaite()))%>" onkeyup="Dinhdang(this)" onkeypress="return isNumberKey2(event)"></TD>
										  <TD class="plainlabel"  >
												<SELECT NAME = "ntId" style = "width:100">
														<OPTION VALUE = "">&nbsp;</OPTION>
										<% if (ntRs != null){
												while(ntRs.next()){
													if(ntRs.getString("TTID").equals(obj.getNtId())){
											%>																	
														<OPTION VALUE = "<%= ntRs.getString("TTID") %>" SELECTED><%= ntRs.getString("TT") %></OPTION>
														
										<%			}else{ %>
													
														<OPTION VALUE = "<%= ntRs.getString("TTID") %>" ><%= ntRs.getString("TT") %></OPTION>
																
										<% 			}
												}
											}%>
											
												</SELECT>
											</TD>
										  
										</TR> 

					                    <TR>
										  <TD  class="plainlabel" >Loại hình vay</TD>
										  <TD  class="plainlabel" colspan = "2" >
												<SELECT NAME = "loai" readonly=readonly>
														<OPTION VALUE = "">&nbsp;</OPTION>

										<%			if(obj.getLoai().equals("1")){
											%>																	
														<OPTION VALUE = "1" SELECTED>Ngắn hạn</OPTION>
														<OPTION VALUE = "2"  >Dài hạn</OPTION>
										<%			}else if(obj.getLoai().equals("2")){ %>
														<OPTION VALUE = "1" >Ngắn hạn</OPTION>
														<OPTION VALUE = "2" SELECTED >Dài hạn</OPTION>
																
										<% 			}else{ %>
														<OPTION VALUE = "1" >Ngắn hạn</OPTION>
														<OPTION VALUE = "2" >Dài hạn</OPTION>

										<%			} %>	
											
										</TR> 
					                    <TR>
										  <TD  class="plainlabel" >Ngày bắt đầu</TD>
										  <TD  class="plainlabel" colspan = "2"><INPUT  name="ngaybd" type="text" size="20" value="<%= obj.getNgayBD() %>" readonly=readonly></TD>
										</TR> 

					                    <TR>
										  <TD  class="plainlabel" >Thời hạn</TD>
										  <TD  class="plainlabel" colspan = "2"><INPUT name="thoihan" type="text" size="20" value="<%= obj.getThoihan() %>" onkeyup="Dinhdang(this)" onkeypress="return isNumberKey2(event)" readonly=readonly> Tháng</TD>
										</TR> 

											
									</TABLE>

								</FIELDSET>
								
						<% if(!obj.getTrangthai().equals("0")) {%>
					     		<FIELDSET>
								<LEGEND class="legendtitle">Giải ngân</LEGEND>
									<TABLE class="tblight" width="100%" cellpadding="4" cellspacing = "1" border = "0">
										<TR class="tbheader">
											<TH align = "center" >Tài khoản vay</TH>
											<TH align = "center" >Ngày nhận</TH>
											<TH align = "center">Số tiền</TH>
											<TH align = "center">Số tiền đã trả </TH>
											<TH align = center>Hình thức nhận</TH>
											<TH align = center>Lãi suất ( %/năm )</TH>
										</TR>
								<% if(ntvlist != null) {
										int m = 0;
										while(ntvlist.next()){
											if(m%2 != 0){		
								%>
											<TR class = "tblightrow">											
								<%			}else{ %>
											<TR class = "tbdarkrow">											
								<%			} %>
												<TD align = center><%=ntvlist.getString("TKVAY") %></TD>
												<TD align = center><%=ntvlist.getString("NGAYNHAN") %></TD>
												<TD align = right><%=formatter.format(Double.parseDouble(ntvlist.getString("SOTIEN"))) %></TD>
												<TD align = right><%=formatter.format(Double.parseDouble(ntvlist.getString("SOTIENDATRA"))) %></TD>
											<% if(ntvlist.getString("HINHTHUC").equals("1")){ %>
												<TD align = center>Chuyển khoản</TD>
											<%}else{ %>
												<TD align = center>Tiền mặt</TD>
											<%} %>	
												<TD align = center><%=ntvlist.getString("LAISUAT") %></TD>
														
								<%			m++;
										}
								}	%>		
									</TABLE>

								</FIELDSET>
						<%} %>
								
								</TD>	
							</TR>
						</TABLE>
         </div>
		 
				
				
</form>
</BODY>
</html>
<%
	try{
		if(cnRs != null) cnRs.close();
		if(nhRs != null) nhRs.close();
		if(ntvlist != null) ntvlist.close();
		if(ntRs != null) ntRs.close();
		
		if(obj != null){
			obj.DbClose();
		}
			
		session.setAttribute("obj",null);
	}catch(Exception err){
			
	}
}%>