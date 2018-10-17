<%@page import="geso.traphaco.erp.beans.tamung.INhanvien"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
  <%INhanvien obj = (INhanvien)session.getAttribute("obj") ;
  	ResultSet nhRs = obj.getNhRs();
  	ResultSet cnRs = obj.getCnRs();
  	ResultSet pbRs = obj.getPbRs();
  	ResultSet tkktRs = obj.getTkktRs();
  %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<head>
	<TITLE>Nhân viên</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
    <LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
    <LINK rel="stylesheet" href="../css/main.css" type="text/css">
    <LINK rel="stylesheet" href="../css/datepicker.css" type="text/css">
      	
  	 <script language="javascript" src="../scripts/datepicker.js"></script>
   	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css"/>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery.min.js"></script>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery-ui.min.js"></script>
  	<script type="text/javascript" src="../scripts/phanTrang.js"></script>
   
   	<script type="text/javascript" src="../scripts/jquery.min.js"></script>
	<script type="text/javascript" src="../scripts/speechbubbles.js"></script>
	<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
	<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>

	<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
	<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
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
		var error = document.getElementById("error");
		var tkkId = document.getElementById("tkktId");
		if(tkkId.value ==""){
			error.value="Vui lòng nhập thông tin tài khoản";
			alert("tài khoản asd");
			return
		}
// 		var tkduId = document.getElementById("tkduId");
// 		if(tkduId.value ==""){
// 			error.value="Vui lòng nhập thông tin tài khoản ứng hàng";
// 			return
// 		}
// 		var tkkqId = document.getElementById("tkkqId");
// 		if(tkkqId.value ==""){
// 			error.value="Vui lòng nhập thông tin tài khoản trích quỹ";
// 			return
// 		}
		document.forms['nccForm'].action.value = 'save';
		document.forms['nccForm'].submit();
	}
	function goBack() {
	    window.history.back();
	}
</SCRIPT>
	
	<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
    $(document).ready(function() { 
     $(".select2").select2();
 });
</script>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">

<form name="nccForm" method="post" action="../../NhanvienUpdateSvl">
<input type="hidden" name="userId" value='<%=userId%>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="Id" value='<%= obj.getId()%>'>

<div id="main" style="width:100%">
	<div align="left" id="header" style="width:100%; float:none">
	<% if (obj.getId().length() == 0) {%>
		<div style="width:70%; padding:5px; float:left" class="tbnavigation">&nbsp;Dữ liệu nền &gt; Kế toán &gt; Nhân viên công ty  &gt; Tạo mới
	<%}else{ %>
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">&nbsp;Dữ liệu nền &gt; Kế toán &gt; Nhân viên công ty &gt; Cập nhật
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
													<TD width="30" align="left"><A href="javascript:goBack();" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
												    <TD width="2" align="left" ></TD>
												    <TD width="30" align="left" ><A href="javascript: newform()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A></TD>
													<TD>&nbsp; </TD>						
												</TR>
										</TABLE>
								</TD></TR>
								<tr>
							<TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
								<LEGEND class="legendtitle">Thông báo </LEGEND>
				
			    				<textarea name="dataerror" id="error" name="error" cols="100%" rows="1" ><%=obj.getmsg() %> </textarea>
								
								</FIELDSET>
						   </TD>
					     </tr>
					     <tr>
					     <td>
					     <FIELDSET>
								<LEGEND class="legendtitle">Nhân viên công ty</LEGEND>
									<TABLE class="tblight" width="100%" cellpadding="6" cellspacing = "0">
										<TR>
											<TD width="15%" class="plainlabel" >Mã nhân viên </TD>
										  <TD  class="plainlabel" ><INPUT name="ma" type="text" size="40" value="<%=obj.getMa() %>" ></TD>
										</TR>

										<TR>
											<TD width="15%" class="plainlabel" >Họ tên </TD>
										  <TD  class="plainlabel" ><INPUT name="ten" type="text" size="40" value="<%=obj.getTen()%>" ></TD>
										</TR>
										<TR>
											<TD class="plainlabel" >Địa chỉ</TD>
										  <TD  class="plainlabel" ><INPUT name="diachi" type="text" size="20" value="<%=obj.getdiachi() %>" ></TD>
										</TR>
										
										<TR>
											<TD class="plainlabel" >Số CMND</TD>
										  <TD  class="plainlabel" ><INPUT name="cmnd" type="text" size="20" value="<%=obj.getcmnd() %>" ></TD>
										</TR>
										
										<TR>
										 	<TD class="plainlabel" >Ngày cấp </TD>
				                        	<TD class="plainlabel">
				                            <input type="text" class="days" 
				                                   id="tungay" name="ngaycap" value="<%= obj.getngaycap() %>" maxlength="10" onchange="submitform()" />
				                        	</TD>
										</TR>	
										<TR>
											<TD class="plainlabel" >Nơi cấp</TD>
										  <TD  class="plainlabel" ><INPUT name="noicap" type="text" size="20" value="<%=obj.getnoicap() %>" ></TD>
										</TR>
					                    <TR>
										  <TD  class="plainlabel" >Tài khoản</TD>
										  <TD  class="plainlabel" >
										 	<SELECT class="select2" name = "tkktId" id="tkktId" style="width: 200px;">
														<OPTION VALUE = "">&nbsp;</OPTION>
										<% 
										tkktRs.beforeFirst();
										if (tkktRs != null){
												while(tkktRs.next()){
													if(tkktRs.getString("TKKTID").equals(obj.getTkktId())){
											%>																	
														<OPTION VALUE = "<%= tkktRs.getString("TKKTID") %>" SELECTED><%= tkktRs.getString("TAIKHOANKT")%></OPTION>
														
										<%			}else{ %>
													
														<OPTION VALUE = "<%= tkktRs.getString("TKKTID") %>" ><%= tkktRs.getString("TAIKHOANKT")%></OPTION>
																
										<% 			}
												}
											}%>
											
											</SELECT>
										  </TD>
										</TR> 
										
										<TR style="display: none;">
										  <TD  class="plainlabel" >Tài khoản KT ứng hàng</TD>
										  <TD  class="plainlabel" >
										 	<SELECT class="select2" name = "tkduId" id="tkduId" style="width: 200px;">
														<OPTION VALUE = "">&nbsp;</OPTION>
										<%
										tkktRs.beforeFirst();
										
										if (tkktRs != null){
												while(tkktRs.next()){
													if(tkktRs.getString("TKKTID").equals(obj.getTkduId())){
											%>																	
														<OPTION VALUE = "<%= tkktRs.getString("TKKTID") %>" SELECTED><%= tkktRs.getString("TAIKHOANKT")%></OPTION>
														
										<%			}else{ %>
													
														<OPTION VALUE = "<%= tkktRs.getString("TKKTID") %>" ><%= tkktRs.getString("TAIKHOANKT")%></OPTION>
																
										<% 			}
												}
											}%>
											
											</SELECT>
										  </TD>
										</TR> 
										
										<TR style="display: none;">
										  <TD  class="plainlabel" >Tài khoản trích quỹ</TD>
										  <TD  class="plainlabel" >
										 	<SELECT class="select2" name = "tkkqId" id="tkkqId" style="width: 200px;">
												<OPTION VALUE = "">&nbsp;</OPTION>
										<%
										tkktRs.beforeFirst();
										
										if (tkktRs != null){
												while(tkktRs.next()){
													if(tkktRs.getString("TKKTID").equals(obj.getTkkqId())){
											%>																	
														<OPTION VALUE = "<%= tkktRs.getString("TKKTID") %>" SELECTED><%= tkktRs.getString("TAIKHOANKT")%></OPTION>
														
										<%			}else{ %>
													
														<OPTION VALUE = "<%= tkktRs.getString("TKKTID") %>" ><%= tkktRs.getString("TAIKHOANKT")%></OPTION>
																
										<% 			}
												}
											}%>
											
											</SELECT>
										  </TD>
										</TR> 
										
										<TR>
											<TD class="plainlabel" >Email</TD>
										  <TD  class="plainlabel" ><INPUT name="email" type="text" size="20" value="<%=obj.getEmail() %>" ></TD>
										</TR>
					                    <TR>
										  <TD  class="plainlabel" >Điện thoại</TD>
										  <TD  class="plainlabel" ><INPUT name="dienthoai" type="text" size="20" value="<%=obj.getdienthoai() %>" ></TD>
										</TR> 

					                    <TR>
										  <TD  class="plainlabel" >Số tài khoản</TD>
										  <TD  class="plainlabel" ><INPUT name="sotaikhoan" type="text" size="20" value="<%=obj.getSotaikhoan() %>" ></TD>
										</TR> 
										
										
										
										<TR>
											<TD  class="plainlabel" >Bộ phận / Chi nhánh</TD>
											<TD class="plainlabel" >
											<SELECT class="select2" NAME = "pbId" style="width: 200px;">
														<OPTION VALUE = "">&nbsp;</OPTION>
										<% if (pbRs != null){
												while(pbRs.next()){
													if(pbRs.getString("PBID").equals(obj.getPbId())){
											%>																	
														<OPTION VALUE = "<%= pbRs.getString("PBID") %>" SELECTED><%= pbRs.getString("PB")%></OPTION>
														
										<%			}else{ %>
													
														<OPTION VALUE = "<%= pbRs.getString("PBID") %>" ><%= pbRs.getString("PB")%></OPTION>
																
										<% 			}
												}
											}%>
											
											</SELECT>
											</TD>											
										</TR>

										<TR>
											<TD  class="plainlabel" >Ngân hàng</TD>
											<TD class="plainlabel" >
											<SELECT class="select2"  NAME = "nhId" style="width: 200px;">
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
										</TR>
										<TR>
											<TD  class="plainlabel" >Chi nhánh ngân hàng</TD>
											<TD class="plainlabel" >
											<SELECT class="select2" NAME = "cnId" style="width: 200px;">
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
										</TR>
										<tr>
											<TD class="plainlabel">Trạng thái</TD>
											  <TD class="plainlabel" >
											  <% if(obj.gettrangthai().trim().equals("1") || obj.getId().length() == 0) {%>
											    <input name="trangthai" type="checkbox" value="1" checked > Hoạt động
										        <%} else { %>
										        <input name="trangthai" type="checkbox" value="1"> Hoạt động
										      <%} %>
										  
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
</html>
<%
	try{
		if(cnRs != null) cnRs.close();
		if(nhRs != null) nhRs.close();
		if(pbRs != null) pbRs.close();
		if(tkktRs != null) tkktRs.close();
	}catch(Exception err){
		err.printStackTrace();		
	}

	if(obj != null){
		obj.DbClose();
	}
		
	session.setAttribute("obj",null);
}%>