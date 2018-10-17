<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="geso.traphaco.center.beans.nhomfocus.imp.*" %>
<%@page import="geso.traphaco.center.beans.nhomfocus.*" %>
<%@ page  import = "geso.traphaco.center.beans.dieukienkhuyenmai.ISanpham" %>
<%@ page  import = "geso.traphaco.center.beans.dieukienkhuyenmai.imp.Sanpham" %>
<%@page import="java.util.Calendar" %>
<%@page import="java.util.Date" %>
<%@page import="java.util.List" %>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@ page  import = "java.util.Hashtable" %>
<%@page import="java.sql.SQLException" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<%
 	INhomfocus obj =(INhomfocus)session.getAttribute("ckBean");
	ResultSet sanphamRs = obj.getSanphamRs();
	ResultSet vungRs = obj.getVungRs();
	ResultSet kvRs = obj.getKhuvucRs();
	ResultSet nppRs = obj.getNppRs();
	

	String[] tumuc = (String[])obj.getTumuc();
	String[] denmuc = (String[])obj.getDenmuc();
	String[] thuong = (String[])obj.getThuong();
	String[] donvi_tinh_ds = (String[])obj.getDonvi_tinh_ds();
	String[] donvi_tinh_thuong = (String[])obj.getDonvi_tinh_thuong();
	
%>
<% Hashtable<String, String> npp_chitieu = (Hashtable<String, String>)obj.getNpp_chitieu(); %>
<% Hashtable<String, String> npp_donvi_chitieu = (Hashtable<String, String>)obj.getNpp_donvi_chitieu(); %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
	<TITLE>Traphaco - Project</TITLE>
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
	
	function SelectALL()
	{
		var checkAll = document.getElementById("checkAll");
		var spIds = document.getElementsByName("spIds");
		
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
	
	function SelectALL2()
	{
		var checkAll = document.getElementById("checkAll2");
		var spIds = document.getElementsByName("nppIds");
		
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
<form name ="tctsku" method="post" action="../../NhomFocusUpdateSvl" >
<input type="hidden" name="userId" value='<%= userId %>' >
<input type="hidden" name="action" value="0">
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý khuyến mại > Khuyến mại tích lũy theo mức > Tạo mới</TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %></TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<TR ><TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
							<TD width="30" align="left"><A href="../../NhomFocusSvl?userId=<%=userId%>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
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
						<LEGEND class="legendtitle" style="color:black">Nhóm Focus </LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
							
				<TR>
						
                    
                    	<TD   width="130px" class="plainlabel"  valign="top">Thời gian áp dụng </TD>
                    <TD width="300px" class="plainlabel" valign="top"  >
                  
                    	
                    	<% 	String[] trangthai = new  String[] {"Tháng","Qúy","Năm"  } ;
							String[] idTrangThai = new  String[] {"0","1","2"} ;
						%> 
						<SELECT name="apdung" onchange="submitform();" >
		      		 <% for( int i=0;i<trangthai.length;i++) { 
		    			if(idTrangThai[i].equals(obj.getDoituong()   ) ){ %>
		      				<option value='<%=idTrangThai[i]%>' selected><%=trangthai[i] %></option>
		      		 	<%}else{ %>
		     				<option value='<%=idTrangThai[i]%>'><%=trangthai[i] %></option>
		     			<%} 
		      		 }
		      		 	%>
					       	</SELECT>
                    	
                    </TD>         
                    
                       <TD   width="120px" class="plainlabel" valign="top"> </TD>
                    <TD class="plainlabel" valign="top"   >
                    
                    	<% if(obj.getApdung().equals("0")){ %>
								Tháng
								<select name="thang"  style="width :50px" >
									<option > </option>  
									<%
  										int k=1;
  									for(k=1;k<=12;k++){
  										String chuoi=k<10?"0"+k:""+k;
  									  if(obj.getThang().equals(k+"")){
  									%>
									<option value=<%=k%> selected="selected" > <%=chuoi%></option> 
									<%
 										}else{
 									%>
									<option value=<%=k%> ><%=chuoi%></option> 
									<%
 										}
 									  }
 									%>
									</select>
								
		      		 	<%}else if(obj.getApdung().equals("1")) { %>
		      		 	Qúy
								
								<% 	 trangthai = new  String[] {"Qúy 1","Qúy 2","Qúy 3","Qúy 4"  } ;
									 idTrangThai = new  String[] {"1","2","3","4"} ;
								%> 
								<SELECT name="quy" >
								<option> </option>
				      		 <% for( int i=0;i<trangthai.length;i++) { 
				    			if(idTrangThai[i].equals(obj.getQuy() ) ){ %>
				      				<option value='<%=idTrangThai[i]%>' selected><%=trangthai[i] %></option>
				      		 	<%}else{ %>
				     				<option value='<%=idTrangThai[i]%>'><%=trangthai[i] %></option>
				     			<%} 
				      		 }
				      		 	%>
				      		 	</SELECT>
		      		 	
		      		 	
		      		 	
		      		 	
		      		 	<%} %>
								Năm	
									<select name="nam"  style="width :75px" >
									<option > </option>  
									<%
									  
  										int n;
  										for(n=2014;n<2025;n++){
  										if(obj.getNam().equals(""+n)){
  									%>
									<option value=<%=n%> selected="selected" > <%=n%></option> 
									<%
 										}else{
 									%>
									<option value=<%=n%> ><%=n%></option> 
									<%
 										}
 									 }
 									%>									
                    </TD>   	
                    
				</TR>
							
						  <TR>
						  	  	<TD class="plainlabel">Scheme <FONT class="erroralert"> *</FONT></TD>
						  	  	<TD class="plainlabel" colspan="3">
						  	  		<input type="text" name="scheme" id="scheme" value="<%= obj.getScheme() %>"> 
						  	  	</TD>
						  </TR>
						  <TR>
						  	  	<TD class="plainlabel">Diễn giải</TD>
						  	  	<TD class="plainlabel" colspan="3">
						  	  		<input type="text" name="diengiai" id="diengiai" value="<%= obj.getDiengiai() %>"> 
						  	  		
						  	  		&nbsp;&nbsp;&nbsp;&nbsp;
						  	  	</TD>
						  </TR>
						</TABLE>
						
						<ul class="tabs">
							<li><a href="#">Tiêu chí thưởng</a></li>
							<li><a href="#">Sản phẩm </a></li>
							<li><a href="#">CN/ĐT</a></li>
						</ul>
						<div class="panes">
							<div>
								<TABLE class="tabledetail" width="100%" border="0" cellspacing="1px" cellpadding="0px">
				                    
				                    <TR class="tbheader">
				                        <TH align="center" width="40%" colspan="2" >Doanh số phát sinh</TH>
				                        <td align="center" width="20%" rowspan="2" >Đơn vị</td>
				                        <td align="center" width="20%" rowspan="2" >Thưởng</td>
				                        <td align="center" width="20%" rowspan="2" > Đơn vị  </td>
				                    </TR>
				                    
				                    <TR class="tbheader">
				                        <TH align="center" width="20%" >Từ mức</TH>
				                        <TH align="center" width="20%" >Đến mức</TH>
				                    </TR>
				                    <% trangthai = new  String[] {"%","VND","Sản phẩm","Số cửa hiệu"  } ;
										 idTrangThai = new  String[] {"0","1","2","3"} ;
									%> 
				                    <%
				                    	int count = 0;
				                    	if( thuong != null ) 
				                    	{
				                    		for(int i = 0; i < thuong.length; i++)
				                    		{
												%>   
												<tr>
													<td>
														<input type="text" name="tumuc" value="<%= tumuc[i] %>" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
													</td>
													<td>
														<input type="text" name="denmuc" value="<%= denmuc[i] %>" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
													</td>
													<td>
													
														<select name="donvi_tinh_ds" style="width: 100%;" >
														
														<% for( int j=0;j<trangthai.length;j++) { 
		    												if(idTrangThai[j].equals(donvi_tinh_ds[i] ) ){ %>
		      											<option value='<%=idTrangThai[j]%>' selected><%=trangthai[j] %></option>
		      		 								<%}else{ %>
		     												<option value='<%=idTrangThai[j]%>'><%=trangthai[j] %></option>
		     														<%} 
		      		 }
		      		 	%>
															
														</select>
													</td>
													<td>
														<input type="text" name="thuong" value="<%= thuong[i] %>" style="text-align: right;"  onkeypress="return keypress(event);" >
													</td>
													
													  <% trangthai = new  String[] {"% Doanh số","VNĐ"  } ;
													 	idTrangThai = new  String[] {"0","1"} ;
													%> 
													
													<td>
													<select name="donvi_tinh_thuong" style="width: 100%;" >
															<% for( int j=0;j<trangthai.length;j++) { 
		    												if(idTrangThai[j].equals(donvi_tinh_ds[i] ) ){ %>
		      											<option value='<%=idTrangThai[j]%>' selected><%=trangthai[j] %></option>
		      		 								<%}else{ %>
		     												<option value='<%=idTrangThai[j]%>'><%=trangthai[j] %></option>
		     														<%}} %>
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
													<input type="text" name="tumuc" value="" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
												</td>
												<td>
													<input type="text" name="denmuc" value="" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
												</td>
												<td>
													<select name="donvi_tinh_ds" style="width: 100%;" >
														<option value="0" > % </option>
														<option value="1" > VNĐ </option>	
														<option value="2" > Sản phẩm </option>							
													</select>
												</td>
												<td>
													<input type="text" name="thuong" value="" style="text-align: right;"  onkeypress="return keypress(event);" >
												</td>
												<td>
													<select name="donvi_tinh_thuong" style="width: 100%;" >
														<option value="0" > % Doanh Số</option>
														<option value="1" > VNĐ </option>	
													</select>
												</td>
											</tr>
			                    			
			                    		<%  }	
				                    	
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
				                    	<td align="center" width="10%">Chọn <input type="checkbox" name="checkAll" id="checkAll" onchange="SelectALL();" > </td>
				                    </TR>
				                    
				                    <%
				                    	if(sanphamRs != null)
				                    	{
				                    		while(sanphamRs.next())
				                    		{
				                    			%>
				                    			<tr>
				                    				<td><input type="text" value="<%= sanphamRs.getString("ma") %>" style="width: 100%;" readonly="readonly" ></td>
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
				                    
				                    <tr>
				                		<td style="font-size: 12px; padding: 8px; " colspan="3" >Vùng &nbsp;&nbsp;   
				                		
				                			<select name="vungId" style="width: 200px;" onchange="submitform();"  >
												<option value="" ></option>
												<%
													if(vungRs != null)
													{
														while(vungRs.next())
														{
															if(obj.getVungIds().indexOf(vungRs.getString("pk_seq")) >= 0 )
															{
																%>
																<option value="<%= vungRs.getString("pk_seq") %>" selected="selected"  ><%= vungRs.getString("ten") %></option>
															<% } else { %>
																<option value="<%= vungRs.getString("pk_seq") %>"  ><%= vungRs.getString("ten") %></option>
															<%}
														}
														vungRs.close();
													}
												%>							
											</select>
				                		&nbsp;&nbsp; 
				                	Khu vực
				                		&nbsp;&nbsp; 
				                			<select name="khuvucId" style="width: 200px;" onchange="submitform();" >
												<option value="" ></option>
												<%
													if(kvRs != null)
													{
														while(kvRs.next())
														{
															if(obj.getKhuvucIds().indexOf(kvRs.getString("pk_seq")) >= 0 )
															{
																%>
																<option value="<%= kvRs.getString("pk_seq") %>" selected="selected"  ><%= kvRs.getString("ten") %></option>
															<% } else { %>
																<option value="<%= kvRs.getString("pk_seq") %>"  ><%= kvRs.getString("ten") %></option>
															<%}
														}
														kvRs.close();
													}
												%>							
											</select>
				                		</td>	
				                    </tr>
				                    
				                    <TR class="tbheader">
				                        <td align="center" width="20%">Mã nhà phân phối</td>
				                    	<td align="center" width="40%">Tên nhà phân phối</td>
				                    	<td align="center" width="15%">Chỉ tiêu</td>
				                    	<td align="center" width="10%">Đơn vị</td>
				                    	<td align="center" width="10%">Chọn <input type="checkbox" name="checkAll2" id="checkAll2" onchange="SelectALL2();" > </td>
				                    </TR>
				                    
				                    	
									<% trangthai = new  String[] {"Doanh số","Sản phẩm","Số cửa hiệu"  } ;
										idTrangThai = new  String[] {"1","2","3"} ;
									%> 
				                    
				                    
				                    <%
				                    	if(nppRs != null)
				                    	{
				                    		while(nppRs.next())
				                    		{
				                    			String chitieu = npp_chitieu.get(nppRs.getString("pk_seq")) != null ? npp_chitieu.get(nppRs.getString("pk_seq")) : "";
				                    			String donvi_chitieu = npp_donvi_chitieu.get(nppRs.getString("pk_seq")) != null ? npp_donvi_chitieu.get(nppRs.getString("pk_seq")) : "";
				                    			%>
				                    			<tr>
				                    				<td><input type="text" value="<%= nppRs.getString("ma") %>" style="width: 100%;" readonly="readonly" ></td>
				                    				<td><input type="text" value="<%= nppRs.getString("ten") %>" style="width: 100%;" readonly="readonly" ></td>
				                    				<td> <input type="text" name="npp_chitieu" value="<%=chitieu %>" style="width: 100%;"  > </td>
				                    				<td>
				                    				<select name="npp_donvi_chitieu" style="width: 100%;" >
				                    				
				                    					<% for( int j=0;j<trangthai.length;j++) { 
		    												if(idTrangThai[j].equals(donvi_chitieu ) ){ %>
		      											<option value='<%=idTrangThai[j]%>' selected><%=trangthai[j] %></option>
		      		 								<%}else{ %>
		     												<option value='<%=idTrangThai[j]%>'><%=trangthai[j] %></option>
		     										<%}} %>
				                    				
													<!-- 	<option value="1" > VNĐ </option>	
														<option value="2" > Sản phẩm </option>
														<option value="3" > Số cửa hiệu </option>				 -->				
													</select>
				                    				</td>
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
