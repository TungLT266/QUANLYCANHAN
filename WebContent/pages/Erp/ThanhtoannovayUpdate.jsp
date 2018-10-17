<%@page import="geso.traphaco.erp.beans.vayvon.IThanhtoannovay"%>
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
  	IThanhtoannovay obj = (IThanhtoannovay)session.getAttribute("obj") ;
  	ResultSet HDRS = obj.getHDRS();
  	ResultSet NTVRS = obj.getNTVRS();
  	ResultSet TKCTYRS = obj.getTKCTYRS();
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
 	function tinhTien()
 	{
 		var tiengoc = document.getElementById("tiengoc").value.replace(/,/g,"");
 		var tyGia = document.getElementById("tyGia").value.replace(/,/g,"");
 		document.getElementById("tiengoc").value = DinhDangTien(parseFloat(tiengoc));
 		document.getElementById("tiengocK").value = DinhDangTien(parseFloat(tiengoc) * parseFloat(tyGia));

 		var tienlai = document.getElementById("tienlai").value;
 		tienlai = tienlai.replace(/,/g,"");
 		document.getElementById("tienlai").value = DinhDangTien(parseFloat(tienlai));
 		document.getElementById("tienlaiK").value = DinhDangTien(parseFloat(tienlai) * parseFloat(tyGia));
 		
 		
 		var tienphat = document.getElementById("tienphat").value.replace(/,/g,"");
 		document.getElementById("tienphat").value = DinhDangTien(parseFloat(tienphat));
 		document.getElementById("tienphatK").value = DinhDangTien(parseFloat(tienphat) * parseFloat(tyGia));
 		
 		var phikhac = document.getElementById("phikhac").value.replace(/,/g,"");
 		document.getElementById("phikhac").value = DinhDangTien(parseFloat(phikhac));
 		document.getElementById("phikhacK").value = DinhDangTien(parseFloat(phikhac) * parseFloat(tyGia));
 	}
 	
	function submitform()
	{ 	
		document.forms['ttnvForm'].submit();
	}

 	function newform()
	{ 	
 		document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";
		document.forms['ttnvForm'].action.value= 'save';
		document.forms['ttnvForm'].submit();
	}

 	function capnhathinhthuc()
	{ 	
 		var s = document.getElementById('hinhthuc');
 		var hinhthuc = s.options[s.selectedIndex].value;
 		
 		if(hinhthuc == '2'){
 			document.getElementById('tkctyId').disabled = true;
 		}else{
 			document.getElementById('tkctyId').disabled = false;
 		}
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
 
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { $("select").select2(); });
     
</script>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">

<form name="ttnvForm" method="post" action="../../ThanhtoannovayUpdateSvl">
<input type="hidden" name="userId" value='<%=userId%>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="Id" value='<%= obj.getId()%>'>
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#ffffff">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" >
					   <table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							   <TD align="left" colspan="2" class="tbnavigation">
								<% if (obj.getId().length() == 0) {%>
									&nbsp;Quản lý kế toán > Chức năng > Thanh toán nợ vay  &gt; Tạo mới
								<%}else{ %>
    								&nbsp;Quản lý kế toán > Chức năng > Thanh toán nợ vay  &gt; Cập nhật
    							<%} %>
							   </TD>
							   <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %> &nbsp;</td> 
						    </tr>
						</table>
					</TD>
				</TR>
				<TR>
					<TD>
             			<TABLE border="0" width="100%" >
							<TR>
						  		<TR ><TD >
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
												<TR class = "tbdarkrow">
													<TD width="30" align="left"><A href="../../ThanhtoannovaySvl?userId=<%=userId %>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
												    <TD width="2" align="left" ></TD>
												    <TD width="30" align="left" >
												    <div id="btnSave"><A href="javascript: newform()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A></div></TD>
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
								<LEGEND class="legendtitle">Thanh toán nợ vay</LEGEND>
									<TABLE class="tblight" width="100%" cellpadding="6" cellspacing = "0" border = "0">
					                    <TR>
										  <TD  class="plainlabel" >Ngày</TD>
										  <TD  class="plainlabel" colspan="2"><INPUT class = "days" name="ngay" type="text" style = "width:100" value="<%= obj.getNgay() %>" onChange="submitform();"></TD>
										</TR> 

										<TR>
											<TD width="15%" class="plainlabel" >Hợp đồng vay</TD>
											<TD  class="plainlabel"  colspan="2">
											<SELECT NAME = "hdId" style = "width:300px;" onChange = "submitform();">
														<OPTION VALUE = "">&nbsp;</OPTION>
										<% if (HDRS != null){
												while(HDRS.next()){
													if(HDRS.getString("HDID").equals(obj.getSoHD())){
											%>																	
														<OPTION VALUE = "<%= HDRS.getString("HDID") %>" SELECTED><%= HDRS.getString("HD")%></OPTION>
														
										<%			}else{ %>
													
														<OPTION VALUE = "<%= HDRS.getString("HDID") %>" ><%= HDRS.getString("HD")%></OPTION>
																
										<% 			}
												}
											}%>
											</SELECT>
										  </TD>
										</TR>

										<TR>
											<TD width="15%" class="plainlabel" >Lần giải ngân</TD>
											<TD  class="plainlabel"  colspan="2">
											<SELECT NAME = "ntvId" style = "width:300px" onChange = "submitform();">
														<OPTION VALUE = "">&nbsp;</OPTION>
										<% if (NTVRS != null){
												while(NTVRS.next()){
													if(NTVRS.getString("NTVID").equals(obj.getNtvId())){
											%>																	
														<OPTION VALUE = "<%= NTVRS.getString("NTVID") %>" SELECTED><%= NTVRS.getString("NTV")%></OPTION>
														
										<%			}else{ %>
													
														<OPTION VALUE = "<%= NTVRS.getString("NTVID") %>" ><%= NTVRS.getString("NTV")%></OPTION>
																
										<% 			}
												}
											}%>
											</SELECT>
										  </TD>
										</TR>

					                    <TR>
										  <TD  class="plainlabel" >Hình thức</TD>
										  <TD  class="plainlabel"   colspan="2">
												<SELECT NAME = "hinhthuc" ID = "hinhthuc"  style = "width:300px">
														<OPTION VALUE = "">&nbsp;</OPTION>

										<%			if(obj.getHinhthuc().equals("1")){
											%>																	
														<OPTION VALUE = "1" SELECTED>Chuyển khoản</OPTION>
														<OPTION VALUE = "2"  >Tiền mặt</OPTION>
										<%			}else if(obj.getHinhthuc().equals("2")){ %>
														<OPTION VALUE = "1" >Chuyển khoản</OPTION>
														<OPTION VALUE = "2" SELECTED >Tiền mặt</OPTION>
																
										<% 			}else{ %>
														<OPTION VALUE = "1" >Chuyển khoản</OPTION>
														<OPTION VALUE = "2" >Tiền mặt</OPTION>

										<%			} %>	
											</SELECT>
										</TR> 
										
										<TR>
											<TD  class="plainlabel" >Tài khoản ngân hàng</TD>
											<TD class="plainlabel"  colspan="2">

											<SELECT NAME = "tkctyId" Id = "tkctyId" style = "width:300px">
														<OPTION VALUE = "">&nbsp;</OPTION>
										<% if (TKCTYRS != null){
												while(TKCTYRS.next()){
													if(TKCTYRS.getString("TKID").equals(obj.getTkCtyId())){
											%>																	
														<OPTION VALUE = "<%= TKCTYRS.getString("TKID") %>" SELECTED><%= TKCTYRS.getString("TK")%></OPTION>
														
										<%			}else{ %>
													
														<OPTION VALUE = "<%= TKCTYRS.getString("TKID") %>" ><%= TKCTYRS.getString("TK")%></OPTION>
																
										<% 			}
												}
											}%>
											
											</SELECT>
											</TD>											
										</TR>

					                    <TR>
					                      <!-- onkeyup="Dinhdang(this)"  -->
										  <TD  class="plainlabel" >Tiền tệ</TD>
										  <TD  class="plainlabel"  colspan="2">
											<SELECT NAME = "ttId" style = "width:100px" onChange="submitform();">
														<OPTION VALUE = "">&nbsp;</OPTION>
														
										<%		ResultSet ttRs = obj.getTtRs();
												boolean vnd_exist = false;
												if (ttRs != null){
													while(ttRs.next()){
// 														if(ttRs.getString("TTID").equals("100000")){
// 															vnd_exist = true;
// 														}
														
														if(ttRs.getString("TTID").equals(obj.getTtId())){
											%>																	
														<OPTION VALUE = "<%= ttRs.getString("TTID") %>" SELECTED><%= ttRs.getString("TT") %></OPTION>
														
										<%				}else{  %>
													
														<OPTION VALUE = "<%= ttRs.getString("TTID") %>" ><%= ttRs.getString("TT") %></OPTION>
																
										<% 				}		%>
																
										<% 			}
															
													
													ttRs.close();
												}
											
											%>
											
											</SELECT>

										  </TD>
										  
										</TR> 
										
										<tr>
											<TD  class="plainlabel" >
											Số tiền gốc
											<INPUT name="tyGia" id="tyGia" type="hidden" value="<%=obj.getTyGia()%>">
											</TD>
											<%if (!obj.getTtId().trim().equals("100000")){ System.out.println("obj.getTiengoc(): " + obj.getTiengoc());%>
											<TD  class="plainlabel" >
												<INPUT name="tiengoc" id="tiengoc"type="text" style = "width:100;text-align: right" 
												  	value="<%=formatter.format(Double.parseDouble(obj.getTiengoc()))%>" onchange="tinhTien();"
												  	  ><%=obj.getTiente() %>
											</TD>
											<TD  class="plainlabel" >
												<INPUT name="tiengocK" id="tiengocK" type="text" style = "width:100;text-align: right" 
												  	value="<%=formatter.format(obj.getTyGia() * Double.parseDouble(obj.getTiengoc()))%>" 
												  	  >VND
									  	  	</TD>
											<%}else{ %>
											
											<TD  class="plainlabel" >
												<INPUT name="tiengoc" type="text" style = "width:100;text-align: right" 
												  	value="<%=formatter.format(Double.parseDouble(obj.getTiengoc()))%>" 
												  	  >VND
									  	  	</TD>
									  	  	<%} %>
										</tr>
					                    <TR>
										  <TD  class="plainlabel" >Số tiền lãi</TD>

										  <%if (!obj.getTtId().trim().equals("100000")){ %>
										  <TD  class="plainlabel" >											
												<INPUT name="tienlai" id="tienlai" onchange="tinhTien();" type="text" style = "width:100;text-align: right" value="<%=formatter.format(Double.parseDouble(obj.getTienlai()))%>" onkeyup="Dinhdang(this)" onkeypress="return isNumberKey2(event)">
										  <%= obj.getTiente() %>
										  </TD>
										  <TD  class="plainlabel">
											  <INPUT name="tienlaiK" id="tienlaiK" type="text" style = "width:100;text-align: right" value="<%=formatter.format(Double.parseDouble(obj.getTienlai()) * obj.getTyGia())%>" onkeyup="Dinhdang(this)" onkeypress="return isNumberKey2(event)"
											  >VND
										  </TD>
											<%} else{%>
											
										  <TD  class="plainlabel">
										  <INPUT name="tienlai" type="text" style = "width:100;text-align: right" value="<%=formatter.format(Double.parseDouble(obj.getTienlai()))%>" onkeyup="Dinhdang(this)" onkeypress="return isNumberKey2(event)"
										  >VND
										  </TD>		
										  <%} %>								  
										</TR> 

					                    <TR>
										  <TD  class="plainlabel" >Số tiền phạt</TD>
										  <%if (!obj.getTtId().trim().equals("100000")){ %>
										  <TD  class="plainlabel" >										  
											<INPUT name="tienphat" id="tienphat" onchange="tinhTien();" type="text" style = "width:100;text-align: right" value="<%=formatter.format(Double.parseDouble(obj.getTienphat()))%>" onkeyup="Dinhdang(this)" onkeypress="return isNumberKey2(event)">
										  <%= obj.getTiente() %>
										  </TD>
										  <TD  class="plainlabel">
											  <INPUT name="tienphatK" id="tienphatK" type="text" style = "width:100;text-align: right" value="<%=formatter.format(Double.parseDouble(obj.getTienphat()) * obj.getTyGia())%>" onkeyup="Dinhdang(this)" onkeypress="return isNumberKey2(event)">
											  VND
										  </TD>
											<%} else{%>
										  <TD  class="plainlabel">
										  <INPUT name="tienphat" type="text" style = "width:100;text-align: right" value="<%=formatter.format(Double.parseDouble(obj.getTienphat()))%>" onkeyup="Dinhdang(this)" onkeypress="return isNumberKey2(event)">
										  VND
										 </TD>
										 <%} %>
										</TR> 

					                    <TR>
										  <TD  class="plainlabel" >Phí khác</TD>
										  <%if (!obj.getTtId().trim().equals("100000")){ %>
										  <TD  class="plainlabel" >
											<INPUT name="phikhac" id="phikhac" onchange="tinhTien();" type="text" style = "width:100;text-align: right" value="<%=formatter.format(Double.parseDouble(obj.getTienphat()))%>" onkeyup="Dinhdang(this)" onkeypress="return isNumberKey2(event)">
										  <%= obj.getTiente() %>
										  <TD  class="plainlabel"  colspan="2">
										  <INPUT name="phikhacK" id="phikhacK" type="text" style = "width:100;text-align: right" value="<%=formatter.format(Double.parseDouble(obj.getPhikhac()) * obj.getTyGia())%>" onkeyup="Dinhdang(this)" onkeypress="return isNumberKey2(event)">
										  VND
										  </TD>
											<%} else{%>
										  <TD  class="plainlabel"  colspan="2">
										  <INPUT name="phikhac" type="text" style = "width:100;text-align: right" value="<%=formatter.format(Double.parseDouble(obj.getPhikhac()))%>" onkeyup="Dinhdang(this)" onkeypress="return isNumberKey2(event)">
										  VND
										  </TD>
										  <%} %>
										</TR> 
											
					                    <TR>
										  <TD  class="plainlabel" >Diễn giải chứng từ</TD>
										  <TD  class="plainlabel"  colspan="2">
										  <INPUT name="ghichu" type="text" style = "width:300;text-align: left" value="<%= obj.getGhichu() %>" >
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
        	</TD>
       	</TR>
      </TABLE>
     
</form>
</BODY>
</html>
<%
	try{
		if(HDRS != null) HDRS.close();
			
		if(obj != null){
			obj.DbClose();
		}
			
		session.setAttribute("obj",null);
	}catch(Exception err){
			
	}
}%>