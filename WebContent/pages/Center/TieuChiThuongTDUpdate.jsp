<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="geso.traphaco.center.beans.tieuchithuong.imp.*" %>
<%@page import="geso.traphaco.center.beans.tieuchithuong.*" %>
<%@ page  import = "geso.traphaco.center.beans.dieukienkhuyenmai.ISanpham" %>
<%@ page  import = "geso.traphaco.center.beans.dieukienkhuyenmai.imp.Sanpham" %>
<%@page import="java.util.Calendar" %>
<%@page import="java.util.Date" %>
<%@page import="java.util.List" %>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@page import="java.sql.SQLException" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/OneOne/index.jsp");
	}else{ %>

<%
 	ITieuchithuongTD obj =(ITieuchithuongTD)session.getAttribute("tctskuBean");
	ResultSet nhomkhachhangRs = obj.getNhomkhachhangRs();
	ResultSet nppRs = obj.getNppRs();
	
	ResultSet kenhRs = obj.getKbhRs();
	ResultSet loaiRs = obj.getLoaikhRs();
	ResultSet sanphamRs = obj.getSanphamRs();
	ResultSet tichluyRs = obj.getTichluyRs();
	
	String[] diengiaiMuc = (String[])obj.getDiengiaiMuc();
	String[] tumuc = (String[])obj.getTumuc();
	String[] denmuc = (String[])obj.getDenmuc();
	String[] thuongSR = (String[])obj.getThuongSR();
	String[] thuongTDSR = (String[])obj.getThuongTDSR();
	ResultSet kmcpRs = (ResultSet)obj.getKhoanmuccpRs();
	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
	<TITLE>OneOne - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
	
	<LINK rel="stylesheet" href="../css/main.css" type="text/css">
	<script type="text/javascript"	src="../scripts/jquery.min.1.7.js"></script>
	<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>
	
	<script type="text/javascript" src="../scripts/ajax.js"></script>
	<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs.css">
	<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs-panes.css">
	
	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
	<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>

	<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
	<style type="text/css">
		#mainContainer{
			width:600px;
			margin:0 auto;
			text-align:left;
			height:100%;
			border-left:3px double #000;
			border-right:3px double #000;
		}
		#formContent{
			padding:5px;
		}
		/* END CSS ONLY NEEDED IN DEMO */
			
		/* Big box with list of options */
		#ajax_listOfOptions{
			position:absolute;	/* Never change this one */
			width:auto;	/* Width of box */
			height:200px;	/* Height of box */
			overflow:auto;	/* Scrolling features */
			border:1px solid #317082;	/* Dark green border */
			background-color:#C5E8CD;	/* White background color */
	    	color: black;
			text-align:left;
			font-size:1.0em;
			z-index:100;
		}
		#ajax_listOfOptions div{	/* General rule for both .optionDiv and .optionDivSelected */
			margin:1px;		
			padding:1px;
			cursor:pointer;
			font-size:1.0em;
		}
		#ajax_listOfOptions .optionDiv{	/* Div for each item in list */
			
		}
		#ajax_listOfOptions .optionDivSelected{ /* Selected item in the list */
			background-color:#317082; /*mau khi di chuyen */
			color:#FFF;
		}
		#ajax_listOfOptions_iframe{
			background-color:#F00;
			position:absolute;
			z-index:5;
		}
		form{
			display:inline;
		}
		#dhtmltooltip
		{
			position: absolute;
			left: -300px;
			width: 150px;
			border: 1px solid black;
			padding: 2px;
			background-color: lightyellow;
			visibility: hidden;
			z-index: 100;
			/*Remove below line to remove shadow. Below line should always appear last within this CSS*/
			filter: progid:DXImageTransform.Microsoft.Shadow(color=gray,direction=135);
		}	
		#dhtmlpointer
		{
			position:absolute;
			left: -300px;
			z-index: 101;
			visibility: hidden;
		}	
	</style>
	<script type="text/javascript" src="../scripts/ajax.js"></script>
	<script type="text/javascript" src="../scripts/dkkhuyenmai_sanpham.js"></script>

	<script type="text/javascript">
		$(document).ready(function() {
			$(".days").datepicker({
				changeMonth : true,
				changeYear : true
			});
			
			//$("ul.tabs").tabs("div.panes > div");
		});
	</script> 
	
	<script>
	$(function() {
	 	$("ul.tabs").tabs("div.panes > div");
	});
	</script>


	<SCRIPT language="JavaScript" type="text/javascript">
		function replaces()
		{
			var masp = document.getElementsByName("maspTra");
			var tensp = document.getElementsByName("tenspTra");
			var type = document.getElementById("hinhthuctra");
	
			var i;
			for(i=0; i < masp.length; i++)
			{
				if(masp.item(i).value != "")
				{
					var sp = masp.item(i).value;
					var pos = parseInt(sp.indexOf(" - "));
					if(pos > 0)
					{
						masp.item(i).value = sp.substring(0, pos);
						tensp.item(i).value = sp.substr(parseInt(sp.indexOf(" - ")) + 3);					
					}
				}
				else
				{
					tensp.item(i).value = "";
					if(type.value == "1")
					{
						var soluong = document.getElementsByName("soluong");
						soluong.item(i).value = "";
					}
				}			
			}
			setTimeout(replaces, 300);
		}	
		
	
		
		function keypress(e) //Hàm dùng d? ngan ngu?i dùng nh?p các ký t? khác ký t? s? vào TextBox
		{    
			var keypressed = null;
			if (window.event)
				keypressed = window.event.keyCode; //IE
			else
				keypressed = e.which; //NON-IE, Standard
			
			if (keypressed < 48 || keypressed > 57)
			{ 
				if (keypressed == 8 || keypressed == 127 || keypressed == 37 || keypressed == 39 || keypressed == 0 || keypressed == 46)
				{//Phím Delete và Phím Back
					return;
				}
				return false;
			}
		}
		
		function save()
		{
		  var thang = document.getElementById("thang").value;
		  var nam = document.getElementById("nam").value;
	
		  if( thang == '' )
		  {
			  alert("Bạn phải nhập từ ngày");
			  return;
		  }
		  	
		  if( nam == '' )
		  {
			  alert("Bạn phải nhập đến ngày");
			  return;
		  }
		  
		  document.forms["tctsku"].action.value = "save";
		  document.forms["tctsku"].submit(); 
	  }
	
		function submitform()
		{
			document.forms["tctsku"].action.value = "submit";
			document.forms["tctsku"].submit(); 
		}
		
	function FormatNumber(e)
	{
		e.value = DinhDangTien(e.value.replace(/,/g,""));
	}
	
	function DinhDangTien(num) 
	 {
	    num = num.toString().replace(/\$|\,/g,'');
	    if(isNaN(num))
	    num = "0";
	    sign = (num == (num = Math.abs(num)));
	    num = Math.floor(num*100+0.50000000001);
	    num = Math.floor(num/100).toString();
	    for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
	    num = num.substring(0,num.length-(4*i+3))+','+
	    num.substring(num.length-(4*i+3));
	    return (((sign)?'':'-') + num);
	}
	
	function SelectALL( idSelect, selectedIds )
	{
		var checkAll = document.getElementById(idSelect);
		var spIds = document.getElementsByName(selectedIds);
		
		if(checkAll.checked == true)
		{
			for(i = 0; i < spIds.length; i++)
				spIds.item(i).checked = true;
		}
		else
		{
			for(i = 0; i < spIds.length; i++)
				spIds.item(i).checked = false;
		}
		
	}
	
</SCRIPT>

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name ="tctsku" method="post" action="../../TieuchithuongTDUpdateSvl" >
<input type="hidden" name="userId" value='<%= userId %>' >
<input type="hidden" name="action" value="0">
<input type="hidden" name="id" value='<%= obj.getId() %>' >

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền > Kinh doanh > Chính sách bán hàng > Cập nhật </TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %></TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<TR ><TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
							<TD width="30" align="left"><A href="../../TieuchithuongTDSvl?userId=<%=userId%>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
						    <TD width="2" align="left" ></TD>
						    <TD width="30" align="left" >
						    <div id="btnSave">
						    <A href="javascript: save()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A>
						    </div>
						    </TD>
							<TD >&nbsp; </TD>						
						</TR>
					</TABLE>
			</TD></TR>
		</TABLE>
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="1" >
			  	<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>
				
	    				<textarea name="dataerror"  style="width: 100% ; font-weight:bold"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1"><%= obj.getMsg() %></textarea>
						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Chính sách bán hàng </LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
							<TR>
								<TD width="120px" class="plainlabel" >Từ ngày<FONT class="erroralert"> *</FONT></TD>
								<TD width="250px" class="plainlabel" >
									<input type="text" name="thang" id="thang" class="days" value="<%= obj.getThang() %>" readonly="readonly" >
								</TD>
						  	  	<TD width="120px" class="plainlabel" >Đến ngày<FONT class="erroralert"> *</FONT></TD>
								<TD class="plainlabel" >
									<input type="text" name="nam" id="nam" class="days" value="<%= obj.getNam() %>" readonly="readonly" >
								</TD>
						  </TR>
						  
						  <TR>
						  	  	<TD class="plainlabel">Diễn giải</TD>
						  	  	<TD class="plainlabel" >
						  	  		<input type="text" name="diengiai" id="diengiai" value="<%= obj.getDiengiai() %>"> 
						  	  	</TD>
						  	  	<TD class="plainlabel" ></TD>
								<TD class="plainlabel" >
		
						  	  		<% if(obj.getApdungchodaily().equals("1")) { %>
						  	  			<input type="checkbox" name="apdungchodaily" checked="checked" value="1" > <i>Áp dụng cho đại lý</i>
						  	  		<% } else { %>
						  	  			<input type="checkbox" name="apdungchodaily" value="1" > <i>Áp dụng cho đại lý</i>
						  	  		<% } %>
						  	  		
						  	  		&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						  	  		<% if(obj.getApdungchohopdong().equals("1")) { %>
						  	  			<input type="checkbox" name="apdungchohopdong" checked="checked" value="1" onchange="submitform();" > <i>Áp dụng cho hợp đồng</i>
						  	  		<% } else { %>
						  	  			<input type="checkbox" name="apdungchohopdong" value="1" onchange="submitform();" > <i>Áp dụng cho hợp đồng</i>
						  	  		<% } %>
						  	  		
						  	  		&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						  	  		<% if(obj.getChiavaodongia().equals("1")) { %>
						  	  			<input type="checkbox" name="chiavaodongia" checked="checked" value="1"  > <i>Chia vào đơn giá</i>
						  	  		<% } else { %>
						  	  			<input type="checkbox" name="chiavaodongia" value="1" > <i>Chia vào đơn giá</i>
						  	  		<% } %>
									
								</TD>
						  </TR>
						  
						   <tr>
						  		<TD class="plainlabel" >Khoản mục chi phí </TD>
		                        <TD class="plainlabel" colspan="3">
		                            <select name="kmcpId">
		                            <option value=""> </option>
		                            <% while(kmcpRs.next()) 
		                            {
		                              if(kmcpRs.getString("pk_seq").equals(obj.getKhoanmuccpId())){ %>
		                                <option value="<%= kmcpRs.getString("pk_seq") %>" selected><%= kmcpRs.getString("diengiai") %></option>
		                                
		                            <%}else { %>
		                            	<option value="<%= kmcpRs.getString("pk_seq") %>"><%= kmcpRs.getString("diengiai") %></option>
		                            <%} } kmcpRs.close(); %>
		                            	
		                            </select>
		                        </TD>
						  </tr>
						  
						  <% if(obj.getApdungchohopdong().equals("1")) { %>
						  <TR>
						  		<TD class="plainlabel">Scheme</TD>
						  	  	<TD class="plainlabel" colspan="3" >
						  	  		<select name="tichluyId" >
						  	  			<option value="" ></option>
						  	  			<% if( tichluyRs != null ) { 
						  	  				while( tichluyRs.next() ) { 
						  	  					if(tichluyRs.getString("pk_seq").equals(obj.getTichluyIds()) ) { %>
						  	  				<option value="<%= tichluyRs.getString("pk_seq") %>" selected="selected" ><%= tichluyRs.getString("SCHEME") %></option>
						  	  			<% } else { %> 
						  	  				<option value="<%= tichluyRs.getString("pk_seq") %>"  ><%= tichluyRs.getString("SCHEME") %></option>
						  	  			<%  } } tichluyRs.close();  } %>
						  	  		</select>
						  	  	</TD>
						  </TR>
						  <% } %>
						  
						</TABLE>
						
						
						<ul class="tabs">
							<li><a href="#">Chính sách bán hàng</a></li>
							<li><a href="#">Áp dụng cho</a></li>
							<li><a href="#">Sản phẩm áp dụng</a></li>
							<li><a href="#">Kênh áp dụng</a></li>
							<li><a href="#">Loại KH áp dụng</a></li>
							<li><a href="#">Nhóm không áp dụng</a></li>
						</ul>
						
						<div class="panes">
							
							<div>
								
								<TABLE class="tabledetail" width="100%" border="0" cellspacing="1px" cellpadding="0px">
				                    
				                    <TR class="tbheader">
				                        <TH align="center" width="30%" rowspan="2" >Ghi chú</TH>
				                        <TH align="center" width="40%" colspan="2" >Doanh số phát sinh</TH>
				                        <td align="center" width="20%" rowspan="2" >Chiết khấu</td>
				                    	<td align="center" width="10%" rowspan="2" >Đơn vị</td>
				                    </TR>
				                    
				                    <TR class="tbheader">
				                        <TH align="center" width="20%" >Từ mức</TH>
				                        <TH align="center" width="20%" >Đến mức</TH>
				                    </TR>
				                    
				                    <%
				                    	int count = 0;
				                    	if( diengiaiMuc != null ) 
				                    	{
				                    		for(int i = 0; i < diengiaiMuc.length; i++)
				                    		{
												%>   
												
												<tr>
													<td>
														<input type="text" name="diengiaiMuc" value="<%= diengiaiMuc[i] %>"   >
													</td>
													<td>
														<input type="text" name="tumuc" value="<%= tumuc[i] %>" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
													</td>
													<td>
														<input type="text" name="denmuc" value="<%= denmuc[i] %>" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
													</td>
													<td>
														<input type="text" name="chietkhau" value="<%= thuongSR[i] %>" style="text-align: right;"  onkeypress="return keypress(event);" >
													</td>
													<td>
														<select name="donvi" style="width: 100%;" >
															
															<% if( thuongTDSR[i].trim().equals("0") ) { %>
																<option value="0" selected="selected" > % </option>
															<% } else { %>
																<option value="0" > % </option>
															<% } %>
															
															<%-- <% if( thuongTDSR[i].trim().equals("1") ) { %>
																<option value="1" selected="selected" > VNĐ </option>
															<% } else { %>
																<option value="1" > VNĐ </option>
															<% } %>
															
															<% if( thuongTDSR[i].trim().equals("2") ) { %>
																<option value="2" selected="selected" >Sản phẩm </option>
															<% } else { %>
																<option value="2" >Sản phẩm </option>
															<% } %> --%>
															
														</select>
													</td>
													
												</tr>
												                 			
				                    		<% count++; }
				                    		
				                    	}
				                    	
				                    	for(int i = count; i < 10; i++)
			                    		{
			                    			%>
			                    			
			                    			<tr>
												<td>
													<input type="text" name="diengiaiMuc" value=""   >
												</td>
												<td>
													<input type="text" name="tumuc" value="" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
												</td>
												<td>
													<input type="text" name="denmuc" value="" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
												</td>
												<td>
													<input type="text" name="chietkhau" value="" style="text-align: right;"  onkeypress="return keypress(event);" >
												</td>
												<td>
													<select name="donvi" style="width: 100%;" >
														<option value="0" > % </option>
														<!-- <option value="1" > VNĐ </option>	
														<option value="2" > Sản phẩm </option>			 -->				
													</select>
												</td>
												
											</tr>
			                    			
			                    		<%  }	
				                    	
				                    %>
									
									<tr>
										<td>
											<input type="text" name="diengiaiMucVuot" value="Mức vượt quy định"   >
										</td>
										<td>
											<input type="text" value=">" style="text-align: center;" readonly="readonly" >
										</td>
										<td>
											<input type="text" name="mucVuot" value="<%= obj.getMucvuot() %>" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
										</td>
										<td>
											<input type="text" name="chietkhauMucVuot" value="<%= obj.getChietkhauMucvuot() %>" style="text-align: right;"  onkeypress="return keypress(event);" >
										</td>
										<td>
											<select name="donviMucVuot" style="width: 100%;" >
												<% if(obj.getDonviMucvuot().equals("0")) { %>
													<option value="0" selected="selected" > % </option>
												<% } else { %> 
													<option value="0"  > % </option>
												<% } %>
												
												<%-- <% if(obj.getDonviMucvuot().equals("1")) { %>
													<option value="1" selected="selected" > VNĐ </option>
												<% } else { %> 
													<option value="1"  > VNĐ </option>
												<% } %>	
												
												<% if(obj.getDonviMucvuot().equals("2")) { %>
													<option value="2" selected="selected" > Sản phẩm </option>
												<% } else { %> 
													<option value="2"  > Sản phẩm </option>
												<% } %>		 --%>			
											</select>
										</td>
										
									</tr>
									
				                    <TR>
				                        <TD align="center" colspan="15" class="tbfooter">&nbsp;</TD>
				                    </TR>
								</TABLE>
								
							</div>
									
							<div>
								
								<TABLE class="tabledetail" width="100%" border="0" cellspacing="1px" cellpadding="0px">
 
				                    <TR class="tbheader">
				                        <td align="center" width="20%">Mã nhóm</td>
				                    	<td align="center" width="70%">Tên nhóm</td>
				                    	<td align="center" width="10%">Chọn <input type="checkbox" id="checkAll_NPP" onchange="SelectALL('checkAll_NPP', 'nppIds');" > </td>
				                    </TR>
				                    
				                    <%
				                    	if(nppRs != null)
				                    	{
				                    		while(nppRs.next())
				                    		{
				                    			%>
				                    			<tr>
				                    				<td><input type="text" value="<%= nppRs.getString("ma") %>" style="width: 100%;" readonly="readonly" ></td>
				                    				<td><input type="text" value="<%= nppRs.getString("ten") %>" style="width: 100%;" readonly="readonly" ></td>
				                    				<td align="center">
				                    					<% if(obj.getNppIds().contains(nppRs.getString("pk_seq"))) { %>
				                    						<input type="checkbox" name="nppIds" value="<%= nppRs.getString("pk_seq") %>" checked="checked" >
				                    					<% } else { %>
				                    						<input type="checkbox" name="nppIds" value="<%= nppRs.getString("pk_seq") %>"  >
				                    					<% } %>
				                    				</td>
				                    			</tr>
				                    		<% }
				                    		nppRs.close();
				                    	}
				                    %>
									
				                    <TR>
				                        <TD align="center" colspan="15" class="tbfooter">&nbsp;</TD>
				                    </TR>
								</TABLE>
								
							</div>
							
							<div>
								
								<TABLE class="tabledetail" width="100%" border="0" cellspacing="1px" cellpadding="0px">
 
				                    <TR class="tbheader">
				                        <td align="center" width="20%">Mã sản phẩm</td>
				                    	<td align="center" width="70%">Tên sản phẩm</td>
				                    	<td align="center" width="10%">Chọn <input type="checkbox" id="checkAll_SP" onchange="SelectALL('checkAll_SP', 'spIds');" > </td>
				                    </TR>
				                    
				                    <%
				                    	if(sanphamRs != null)
				                    	{
				                    		while(sanphamRs.next())
				                    		{
				                    			%>
				                    			<tr>
				                    				
				                    				<td><input type="text" value="<%= sanphamRs.getString("pk_seq") %>" style="width: 100%;" readonly="readonly" ></td>
				                    				<td><input type="text" value="<%= sanphamRs.getString("ten") %>" style="width: 100%;" readonly="readonly" ></td>
				                    				<td align="center">
				                    					<% if(obj.getSpIds().contains(sanphamRs.getString("pk_seq"))) { %>
				                    						<input type="checkbox" name="spIds" value="<%= sanphamRs.getString("pk_seq") %>" checked="checked" >
				                    					<% } else { %>
				                    						<input type="checkbox" name="spIds" value="<%= sanphamRs.getString("pk_seq") %>"  >
				                    					<% } %>
				                    				</td>
				                    				
				                    			</tr>
				                    		<% }
				                    		sanphamRs.close();
				                    	}
				                    %>
									
				                    <TR>
				                        <TD align="center" colspan="15" class="tbfooter">&nbsp;</TD>
				                    </TR>
								</TABLE>
								
							</div>
							
							<div>
								
								<TABLE class="tabledetail" width="100%" border="0" cellspacing="1px" cellpadding="0px">
 
				                    <TR class="tbheader">
				                        <td align="center" width="20%">Mã kênh</td>
				                    	<td align="center" width="70%">Tên kênh</td>
				                    	<td align="center" width="10%">Chọn <input type="checkbox" id="checkAll_KBH" onchange="SelectALL('checkAll_KBH', 'kbhIds');" > </td>
				                    </TR>
				                    
				                    <%
				                    	if(kenhRs != null)
				                    	{
				                    		while(kenhRs.next())
				                    		{
				                    			%>
				                    			<tr>
				                    				
				                    				<td><input type="text" value="<%= kenhRs.getString("pk_seq") %>" style="width: 100%;" readonly="readonly" ></td>
				                    				<td><input type="text" value="<%= kenhRs.getString("ten") %>" style="width: 100%;" readonly="readonly" ></td>
				                    				<td align="center">
				                    					<% if(obj.getKbhIds().contains(kenhRs.getString("pk_seq"))) { %>
				                    						<input type="checkbox" name="kbhIds" value="<%= kenhRs.getString("pk_seq") %>" checked="checked" >
				                    					<% } else { %>
				                    						<input type="checkbox" name="kbhIds" value="<%= kenhRs.getString("pk_seq") %>"  >
				                    					<% } %>
				                    				</td>
				                    				
				                    			</tr>
				                    		<% }
				                    		kenhRs.close();
				                    	}
				                    %>
									
				                    <TR>
				                        <TD align="center" colspan="15" class="tbfooter">&nbsp;</TD>
				                    </TR>
								</TABLE>
								
							</div>
							
							<div>
								
								<TABLE class="tabledetail" width="100%" border="0" cellspacing="1px" cellpadding="0px">
 
				                    <TR class="tbheader">
				                        <td align="center" width="20%">Mã loại</td>
				                    	<td align="center" width="70%">Tên loại</td>
				                    	<td align="center" width="10%">Chọn <input type="checkbox" id="checkAll_LOAI" onchange="SelectALL('checkAll_LOAI', 'loaiIds');" > </td>
				                    </TR>
				                    
				                    <%
				                    	if(loaiRs != null)
				                    	{
				                    		while(loaiRs.next())
				                    		{
				                    			%>
				                    			<tr>
				                    				
				                    				<td><input type="text" value="<%= loaiRs.getString("pk_seq") %>" style="width: 100%;" readonly="readonly" ></td>
				                    				<td><input type="text" value="<%= loaiRs.getString("ten") %>" style="width: 100%;" readonly="readonly" ></td>
				                    				<td align="center">
				                    					<% if(obj.getLoaikhIds().contains(loaiRs.getString("pk_seq"))) { %>
				                    						<input type="checkbox" name="loaiIds" value="<%= loaiRs.getString("pk_seq") %>" checked="checked" >
				                    					<% } else { %>
				                    						<input type="checkbox" name="loaiIds" value="<%= loaiRs.getString("pk_seq") %>"  >
				                    					<% } %>
				                    				</td>
				                    				
				                    			</tr>
				                    		<% }
				                    		loaiRs.close();
				                    	}
				                    %>
									
				                    <TR>
				                        <TD align="center" colspan="15" class="tbfooter">&nbsp;</TD>
				                    </TR>
								</TABLE>
								
							</div>
										
							<div>
								
								<TABLE class="tabledetail" width="100%" border="0" cellspacing="1px" cellpadding="0px">
 
				                    <TR class="tbheader">
				                        <td align="center" width="20%">Mã nhóm</td>
				                    	<td align="center" width="70%">Tên nhóm</td>
				                    	<td align="center" width="10%">Chọn <input type="checkbox" id="checkAll_NHOM" onchange="SelectALL('checkAll_NHOM', 'nhomkhIds');" > </td>
				                    </TR>
				                    
				                    <%
				                    	if(nhomkhachhangRs != null)
				                    	{
				                    		while(nhomkhachhangRs.next())
				                    		{
				                    			%>
				                    			<tr>
				                    				
				                    				<td><input type="text" value="<%= nhomkhachhangRs.getString("pk_seq") %>" style="width: 100%;" readonly="readonly" ></td>
				                    				<td><input type="text" value="<%= nhomkhachhangRs.getString("ten") %>" style="width: 100%;" readonly="readonly" ></td>
				                    				<td align="center">
				                    					<% if(obj.getNhomkhachhangIds().contains(nhomkhachhangRs.getString("pk_seq"))) { %>
				                    						<input type="checkbox" name="nhomkhIds" value="<%= nhomkhachhangRs.getString("pk_seq") %>" checked="checked" >
				                    					<% } else { %>
				                    						<input type="checkbox" name="nhomkhIds" value="<%= nhomkhachhangRs.getString("pk_seq") %>"  >
				                    					<% } %>
				                    				</td>
				                    				
				                    			</tr>
				                    		<% }
				                    		nhomkhachhangRs.close();
				                    	}
				                    %>
									
				                    <TR>
				                        <TD align="center" colspan="15" class="tbfooter">&nbsp;</TD>
				                    </TR>
								</TABLE>
								
							</div>
							
						</div>
									
						</FIELDSET>						
					</TD>
				</TR>
			</TABLE>
		</TD>
	</TR>
	</TABLE>
</form>
<script type="text/javascript">
	replaces();
</script>
</BODY>
</HTML>
<%}%>
