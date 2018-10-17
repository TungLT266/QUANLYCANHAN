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
  %>
<%  NumberFormat formatter = new DecimalFormat("#,###,###.##"); %>    
  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	<TITLE>TraphacoHYERP - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
    <LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
    <LINK rel="stylesheet" href="../css/main.css" type="text/css">
    <LINK rel="stylesheet" href="../css/datepicker.css" type="text/css">
    
   	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css"/>
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />

			<script type="text/javascript" src="../scripts/jquery.min.js"></script>
		<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
		<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
		<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
		<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
		<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
		<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>

	
	<script type="text/javascript">
		$(document).ready(function() {		
				$( ".days" ).datepicker({			    
						changeMonth: true,
						changeYear: true				
				});            
	        }); 		
			
	</script>
   
  	<script type="text/javascript" src="..scripts/jquery-1.js"></script>
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
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
		$(document).ready(function(){
            $(".button3").hover(function(){
                $(".button3 img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
    </script>
  
 <SCRIPT language="javascript" type="text/javascript">
						
	function newform()
	{ 	
		document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";
		document.forms['hdvForm'].action.value= 'save';
		document.forms['hdvForm'].submit();
	}

	function submitform()
	{ 	
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
	<% if (obj.getId().length() == 0) {%>
		<div style="width:70%; padding:5px; float:left" class="tbnavigation">&nbsp;Quản lý kế toán > Chức năng > Hợp đồng vay  &gt; Tạo mới
	<%}else{ %>
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">&nbsp;Quản lý kế toán > Chức năng > Hợp đồng vay  &gt; Cập nhật
    <%} %>
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
												    <TD width="30" align="left" >
												    <div id="btnSave">
												    <A href="javascript: newform()" >
												    <IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A>
												    </div></TD>
													<TD>&nbsp; </TD>						
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
										 	<TD  class="plainlabel" colspan = "2" >
										 		<INPUT name="sohd" type="text" size="40" value="<%= obj.getSoHD() %>" >
										 	</TD>
										 	
										</TR>

										<TR>
											<TD width="15%" class="plainlabel" >Diễn giải </TD>
										    <TD  class="plainlabel" colspan = "2"><INPUT name="diengiai" type="text" size="40" value="<%= obj.getDiengiai() %>" style = "width:400"></TD>
										</TR>

					                    <TR>
										  <TD  class="plainlabel" >Ngày</TD>
										  <TD  class="plainlabel" colspan = "2"><INPUT class = "days" name="ngay" type="text" size="20" value="<%= obj.getNgay() %>" readonly = readonly></TD>
										</TR> 
										
										<TR>
											<TD  class="plainlabel" >Ngân hàng</TD>
											<TD class="plainlabel" colspan = "2">
											<SELECT NAME = "nhId" onChange = "submitform();">
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
											<TD class="plainlabel" colspan = "2" onChange = "submitform();">
												<SELECT NAME = "cnId" >
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
										  <TD  class="plainlabel" >Số tiền </TD>
										  <TD  class="plainlabel" width = "10%"><INPUT name="tongtien" type="text" size="20" style = "text-align: right" value="<%=formatter.format(Double.parseDouble(obj.getTongtien()))%>" onkeyup="Dinhdang(this)" onkeypress="return isNumberKey2(event)"></TD>
										  <TD class="plainlabel"  colspan = "2">
												<SELECT NAME = "ttId" style = "width:100" onChange = "submitform();">
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
										  <TD class="plainlabel"  colspan = "2">
												<SELECT NAME = "ntId" style = "width:100" onChange = "submitform();">
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
												<SELECT NAME = "loai" >
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

										<%			} 
										
										%>	
												</SELECT>	
										</TR> 
					                    <TR>
										  <TD  class="plainlabel" >Ngày bắt đầu</TD>
										  <TD  class="plainlabel" colspan = "2"><INPUT class = "days" name="ngaybd" type="text" size="20" value="<%= obj.getNgayBD() %>" ></TD>
										</TR> 

					                    <TR>
										  <TD  class="plainlabel" >Thời hạn</TD>
										  <TD  class="plainlabel" colspan = "2"><INPUT name="thoihan" type="text" size="20" value="<%= obj.getThoihan() %>" onkeyup="Dinhdang(this)" onkeypress="return isNumberKey2(event)" > Tháng</TD>
										</TR> 

										<TR>
									 	
											<TD width="15%" class="plainlabel" >Thay thế cho hợp đồng </TD>
										 	<TD  class="plainlabel" colspan = "2">
										 		<INPUT name="hdtt" id = "hdtt" type="text" style="width:450px;heigth:100px" value="<%= obj.getHDTT() %>" >
										 	</TD>

										</TR>
											
									</TABLE>

								</FIELDSET>
								
								</TD>	
							</TR>
						</TABLE>
         </div>
	</div> 
	</div>
</div>
	
</form>

</BODY>
<script type="text/javascript">
	jQuery(function()
	{		
		$("#hdtt").autocomplete("ErpHDVayList.jsp");
	});	
	
</script>

</html>
<%
	try{
		if(cnRs != null) cnRs.close();
		if(nhRs != null) nhRs.close();
		if(ttRs != null) ttRs.close();
		
		if(ntRs != null) ntRs.close();
		
		if(obj != null){
			obj.DbClose();
		}
			
		session.setAttribute("obj",null);
	}catch(Exception err){
			
	}
}%>