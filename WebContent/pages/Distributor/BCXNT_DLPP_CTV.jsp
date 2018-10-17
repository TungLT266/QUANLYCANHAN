<%@page import="geso.traphaco.center.util.Utility"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="geso.traphaco.center.beans.stockintransit.IStockintransit"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	
	<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
<%
	
	IStockintransit obj = (IStockintransit) session.getAttribute("obj");
	ResultSet npp = obj.getnpp();
	ResultSet npplist = obj.getDlpp();
	ResultSet rsKhuvuc = obj.getkhuvuc();
	ResultSet rsTinh = obj.getTinhthanh();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">

<script type="text/javascript" src="../scripts/jquery-1.js"></script>
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />

<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
$(document).ready(function()
{
	$(".select2").select2();
});
</script>
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
            $(".button").hover(function(){
                $(".button img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
		$(document).ready(function(){
            $(".button1").hover(function(){
                $(".button1 img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        });
		
    </script>

<script type="text/javascript"
	src="../scripts/report-js/action-report.js"></script>
<script language="javascript" type="text/javascript">	
	function search()
	{
		document.forms['frm'].action.value= 'search';
		document.forms["frm"].submit();
	}
	function submitform() {
		
		if (document.forms["frm"]["Sdays"].value.length == 0) {
			setErrors("Vui lòng kiểm tra lại thời gian lấy báo cáo");
			return ;
		}
		if (document.forms["frm"]["Edays"].value.length == 0) {
			setErrors("Vui lòng kiểm tra lại thời gian lấy báo cáo");
			return ;
		}
		var fieldShow = document.getElementsByName("fieldsHien");
		for ( var i = 0; i < fieldShow.length; ++i) {
			fieldShow.item(i).checked = true;
		}
		//document.getElementById("btnTaoBC").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";

		document.forms['frm'].action.value= 'tao';
		document.forms["frm"].submit();
		reset();

	}
	function setErrors(errorMsg) {
		var errors = document.getElementById("errors");
		errors.value = errorMsg;
	}
	function reset() {
		var fieldShow = document.getElementsByName("fieldsHien");
		var fieldHidden = document.getElementsByName("fieldsAn");
		for ( var i = 0; i < fieldShow.length; ++i) {
			fieldShow.item(i).checked = false;
		}
		for ( var i = 0; i < fieldHidden.length; ++i) {
			fieldHidden.item(i).checked = false;
		}
	};
	
 
	function capnhat_check(){
		
		
		var layctnxsolo = document.getElementById("layctnxsolo");
		var layctnxsolo_tong = document.getElementById("layctnxsolo_tong");
		 
		if(layctnxsolo.checked  && layctnxsolo_tong.checked ){
			layctnxsolo.checked =!layctnxsolo_tong.checked;
		}
		
	}
	
	function capnhat_check1(){
		
		
		var layctnxsolo = document.getElementById("layctnxsolo");
		var layctnxsolo_tong = document.getElementById("layctnxsolo_tong");
		 
		if(layctnxsolo.checked  && layctnxsolo_tong.checked ){
			 layctnxsolo_tong.checked=false ;
		}
		
	}
	
	
	function checkedAll(){
		var chkAll = document.getElementById("chkAll");
	 	var nppId = document.getElementsByName("nppId");
		 console.log("vao day");
		 if(chkAll.checked)
		 {
			 for(i = 0; i < nppId.length; i++)
			 {
				 nppId.item(i).checked = true;
			 }
		 }
		 else
		 {
			 for(i = 0; i < nppId.length; i++)
			 {
				 nppId.item(i).checked = false;
			 }
		 }
	 }
</script>
<link media="screen" rel="stylesheet" href="../css/colorbox.css">
    <script src="../scripts/colorBox/jquery.colorbox.js"></script>
    <script>
        $(document).ready(function()
        {
        	$(".kholist").colorbox({width:"46%", inline:true, href:"#kholist"});
            //Example of preserving a JavaScript event for inline calls.
            $("#click").click(function(){ 
                $('#click').css({"background-color":"#f00", "color":"#fff", "cursor":"inherit"}).text("Viethung - Project.");
                return false;
            });
        });
    </script>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="frm" method="post" action="../../XNTDLPPSvl">
		<input type="hidden" name="action" value='1'> 
		<input type="hidden" id="userId" name="userId" value='<%=userId%>'>
		<input type="hidden" name="isdlpp" value="<%= obj.getIsDlpp() %>" >
		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 70%; padding: 5px; float: left" class="tbnavigation">
				<% if( obj.getIsDlpp().equals("1") ) { %>
					Quản lý tồn kho &#62; Báo cáo &#62; Xuất nhập tồn đại lý phân phối
				<% } else if( obj.getIsDlpp().equals("0") ) { %>
					Quản lý tồn kho &#62; Báo cáo &#62; BC tồn kho Bệnh Viện
				<% } else { %>
					Quản lý tồn kho &#62; Báo cáo &#62; BC cộng tác viên (KSNB)
				<% } %>
				</div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng bạn
					<%=userTen%></div>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Báo lỗi nhập liệu</legend>
					<textarea id="errors" name="errors" rows="1" style="width: 100% ; color:#F00 ; font-weight:bold"><%=obj.getMsg()%></textarea>
				</fieldset>
			</div>
			<div align="left"
				style="width: 100%; float: none; clear: left; font-size: 0.7em">
				<fieldset>
					<legend class="legendtitle">Xuất nhập tồn</legend>
					<div style="width: 100%; float: none" align="left">
						<div style="width: 100%; float: none" align="left"
							class="plainlabel">

							<TABLE width="70%" cellpadding="6" cellspacing="0">
							
								<% if( obj.getIsDlpp().equals("0") || obj.getIsDlpp().equals("2") ) { %>
								
								<TR>
									<TD class="plainlabel">Tháng</TD>
									<TD class="plainlabel">
										<select name="Sdays"  class="select2" style="width: 200px" >
											<option value="" ></option>
											<% for( int i = 1; i <= 12; i++ ) { %>
												<% if( obj.getMonth().equals( Integer.toString(i) ) ) { %>
													<option value="<%= i %>" selected="selected" ><%= i %></option>
												<% } else { %>
													<option value="<%= i %>" ><%= i %></option>
												<% } %>
											<% } %>
										</select>
									</TD>
										
									<TD class="plainlabel">Năm</TD>
									<td>
										<select name="Edays"  class="select2" style="width: 200px" >
											<option value="" ></option>
											<% for( int i = 2015; i <= 2030; i++ ) { %>
												<% if( obj.getYear().equals( Integer.toString(i) ) ) { %>
													<option value="<%= i %>" selected="selected" ><%= i %></option>
												<% } else { %>
													<option value="<%= i %>" ><%= i %></option>
												<% } %>
											<% } %>
										</select>
									</td>
								</TR>
								
								<TR>
									<TD class="plainlabel">Khu vực</TD>
									<TD class="plainlabel">
										<select name="khuvucId" class="select2" style="width: 200px" >
											<option value="">All</option>
											<%if (rsKhuvuc != null)
													while (rsKhuvuc.next()) 
													{
														if (rsKhuvuc.getString("pk_seq").equals(obj.getkhuvucId())) {%>
														<option value="<%=rsKhuvuc.getString("pk_seq")%>" selected><%=rsKhuvuc.getString("ten")%></option>
													<%} else {%>
														<option value="<%=rsKhuvuc.getString("pk_seq")%>"><%=rsKhuvuc.getString("ten")%></option>
											<%}}%>
										</select>
									</TD>
									<TD class="plainlabel">Tỉnh</TD>
									<TD class="plainlabel" >
										<select name="tinhthanhId" class="select2" style="width: 200px" >
											<option value="" selected>All</option>
											<% if(rsTinh != null)
											   while(rsTinh.next()){
											   if(rsTinh.getString("pk_seq").equals(obj.getTinhthanhid()))
											   { %>
											<option value="<%= rsTinh.getString("pk_seq") %>" selected><%= rsTinh.getString("ten") %></option>
											<%}else { %>
											<option value="<%= rsTinh.getString("pk_seq") %>"><%= rsTinh.getString("ten") %></option>
											<%} }%>
										</select>
									</TD>
								</TR>
								<TR>
			                     	<TD class="plainlabel" >Khách hàng</TD>
			                        <TD class="plainlabel" >
			                            <input type="text" name="khachang" value="<%= obj.getkhTen() %>"  />
			                        </TD>
			                        <TD class="plainlabel" >Sản phẩm</TD>
			                        <TD class="plainlabel" >
			                            <input type="text" name="tenSP" value="<%= obj.getsanphamId() %>"   />
			                        </TD>
			                    </TR>
			                    <% } %>
			                    
								<% if( obj.getIsDlpp().equals("1") ) { %>
								<TR>
									<TD class="plainlabel">Từ ngày</TD>
									<TD class="plainlabel">
										<input type="text" name="Sdays" class="days" value='<%=obj.gettungay()%>' /></TD>
									<TD class="plainlabel">Đến ngày</TD>
									<td>
										<input type="text" name="Edays" class="days" value='<%=obj.getdenngay()%>' /></td>
								</TR>
                           		<TR  >
                           			<TD class="plainlabel" colspan="2" >
                           			 <%if(obj.getChiTietXNT_Lo().trim().equals("1")){ %>
						  				<input onclick="capnhat_check1()" name="layctnxsolo" id="layctnxsolo" checked="checked" value="1"  type="checkbox" /> Nhập xuất theo lô 			
							  		 <%}else{ %>
						  				<input name="layctnxsolo"  onclick="capnhat_check1()" id="layctnxsolo" value="1"  type="checkbox" /> Nhập xuất theo lô
							  		  <%} %>
							  		   
                           			</TD>
                           		</TR> 
                           		<% } %>
                           		
								<TR>
									<td colspan="4">
										<div id="btnTaoBC">
											<a class="button" href="javascript:submitform();"> <img style="top: -4px;" src="../images/button.png" alt=""> Tạo báo cáo </a>
										</div>
									</td>
								</TR>
								
							</TABLE>
						</div>
						<hr>
						
						<% if( obj.getIsDlpp().equals("1") ) { %>
						<div style="width: 100%; float: none;">
							<div style="width: 50%; float: left">
								<fieldset style="min-height: 200px">
									<legend class="legendtitle"> Đại lý phân phối </legend>
									<TABLE width="100%" border="0" cellspacing="1" cellpadding="4"
										id="tbShowFields">
										<tbody id="ShowFields">
											<tr class="tbheader">
												<th align="center">Mã</th>
												<th align="center">Tên</th>
												<th align="center">Chọn 
													<input type="checkbox" onchange="checkedAll()" id="chkAll">
												</th>
											</tr>
											<% 
										if(npplist != null)
										{
											try{
												int m = 0;
												String lightrow = "tblightrow";
												String darkrow = "tbdarkrow";
												while (npplist.next()){
												
													if (m % 2 != 0) { %>						
														<TR class= <%=lightrow%> >
													<%} else { %>
														<TR class= <%= darkrow%> >
													<%}%>							
														<TD align="center"><%=npplist.getString("mafast") %></TD>
														<TD align="right"><div align="left"><%=npplist.getString("ten") %> </div></TD>
														<TD align="center">
															<input type="checkbox" name="nppId" value="<%=npplist.getString("pk_seq") %>" > 
														</TD>
												
													<%m++; 
												}
											}catch(Exception e){
												e.printStackTrace();
											}}%>
										</tbody>
									</TABLE>
								</fieldset>
							</div>

						</div>
						<% } %>
						
					</div>
				</fieldset>
			</div>
		</div>
		<br /> <br /> <br /> <br />
	</form>
</body>
</html>

<% 
	 if(obj != null)  obj.DBclose(); 
	session.setAttribute("obj",null);
}%>