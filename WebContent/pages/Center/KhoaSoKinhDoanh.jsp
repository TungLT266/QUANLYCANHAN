<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Calendar" %>
<%@page import="java.util.Date" %>
<%@page import="java.util.List" %>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@page import="java.sql.SQLException" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "geso.traphaco.center.beans.khoasokd.*" %>
<% IKhoasokinhdoanh kskdBean = (IKhoasokinhdoanh)session.getAttribute("obj"); %>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
	<TITLE>Phanam - Project</TITLE>
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

	<script>
	$(document).ready(function() {

	    //When page loads...
	    $(".tab_content").hide(); //Hide all content
	    var index = $("ul.tabs li.current").show().index(); 
	    $(".tab_content").eq(index).show();
	    //On Click Event
	    $("ul.tabs li").click(function() {
	  
	        $("ul.tabs li").removeClass("current"); //Remove any "active" class
	        $(this).addClass("current"); //Add "active" class to selected tab
	        $(".tab_content").hide(); //Hide all tab content  
	        var activeTab = $(this).find("a").attr("href"); //Find the href attribute value to identify the active tab + content  
	        $(activeTab).show(); //Fade in the active ID content
	        return false;
	    });

	});
	</script>

	<SCRIPT language="JavaScript" type="text/javascript">
				
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
		
		var active =$(".tabs li.current").index();
		document.forms["ckdhForm"].activeTab.value =active;
		document.forms["ckdhForm"].action.value = "save";
		
		 var ngaytt = document.forms["ckdhForm"].ngaykskdtt.value;
		 var giott = document.forms["ckdhForm"].giokskdtt.value;
		 var phuttt = document.forms["ckdhForm"].phutkskdtt.value;
		
		 var ngaynpp = document.forms["ckdhForm"].ngaykskdnpp.value;
		 var gionpp = document.forms["ckdhForm"].giokskdnpp.value;
		 
		 var ngayctv = document.forms["ckdhForm"].ngaykskdctv.value;
		 var gioctv = document.forms["ckdhForm"].giokskdctv.value;
		 
		 if(ngaytt.length <= 0)
			 document.forms["ckdhForm"].dataerror.value = "Lỗi trống ngày thiết lập trung tâm";
		 else
			 if(giott.length <= 0)
				 document.forms["ckdhForm"].dataerror.value = "Lỗi trống giờ thiết lập trung tâm";
			 else
				 if(phuttt.length <= 0)
					 document.forms["ckdhForm"].dataerror.value = "Lỗi trống phút thiết lập trung tâm";
				 else
				 if(ngaynpp.length <= 0)
					 document.forms["ckdhForm"].dataerror.value = "Lỗi trống ngày thiết lập nhà phân phối";
				 else
				 	if(gionpp.length <= 0)
			 			document.forms["ckdhForm"].dataerror.value = "Lỗi trống giờ thiết lập nhà phân phối";
				 	 else
						 	if(ngayctv.length <= 0)
					 			document.forms["ckdhForm"].dataerror.value = "Lỗi trống ngày thiết lập cộng tác viên";
						 	 else
								 	if(gioctv.length <= 0)
							 			document.forms["ckdhForm"].dataerror.value = "Lỗi trống giờ thiết lập cộng tác viên";
								 	else
								 		if(parseInt(ngaytt) > 31 || parseInt(ngaytt) < 1 )
								 			document.forms["ckdhForm"].dataerror.value = "Lỗi ! Ngày thiết lập trung tâm phải từ 1 - 31";
								 		else
									 		if(parseInt(giott) > 24 || parseInt(giott) < 0 )
									 			document.forms["ckdhForm"].dataerror.value = "Lỗi ! Giờ thiết lập trung tâm phải từ 0 - 24";
									 		else
									 			if(parseInt(phuttt) > 59 || parseInt(phuttt) < 0 )
										 			document.forms["ckdhForm"].dataerror.value = "Lỗi ! Phút thiết lập trung tâm phải từ 0 - 59";
										 		else
										 			if(isNaN(ngaynpp) == true || parseInt(ngaynpp) > 31 || parseInt(gionpp) < 1 )
										 				document.forms["ckdhForm"].dataerror.value = "Lỗi ! Ngày thiết lập nhà phân phối phải từ 1 - 31";
										 		else
											 		if(isNaN(gionpp) == true || parseInt(gionpp) > 24 || parseInt(gionpp) < 0 )
											 			document.forms["ckdhForm"].dataerror.value = "Lỗi ! Giờ thiết lập nhà phân phối phải từ 0 - 24";
											 		else
												 		if(isNaN(ngayctv) == true ||parseInt(ngayctv) > 31 || parseInt(ngayctv) < 1 )
												 			document.forms["ckdhForm"].dataerror.value = "Lỗi ! Ngày thiết lập cộng tác viên phải từ 1 - 31";
												 		else
													 		if(isNaN(gioctv) == true || parseInt(gioctv) > 24 || parseInt(gioctv) < 0 )
													 			document.forms["ckdhForm"].dataerror.value = "Lỗi ! Giờ thiết lập cộng tác viên phải từ 0 - 24";
													 		else
		  														document.forms["ckdhForm"].submit(); 
	  }
	
		function submitform()
		{
			var active =$(".tabs li.current").index();
			document.forms["ckdhForm"].activeTab.value =active;
			document.forms["ckdhForm"].action.value = "submit";
			document.forms["ckdhForm"].submit(); 
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
</SCRIPT>

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name ="ckdhForm" method="post" action="../../khoasokinhdoanhSvl">
<input type="hidden" name="userId" value='<%= userId %>' >
<input type="hidden" id="activeTab" name="activeTab" value='<%=kskdBean.getActiveTab()%>'>
<input type="hidden" name="action" value="0">
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền > Cơ bản > Thiết lập khóa sổ kinh doanh</TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %></TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<TR ><TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
							<TD width="30" align="left"><A href="../../khoasokinhdoanhSvl?userId=<%=userId%>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
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
				
	    				<textarea name="dataerror"  style="width: 100% ; font-weight:bold"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1"><%= kskdBean.getMsg() %></textarea>
						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >

						
						<ul class="tabs">	
							<li <%=kskdBean.getActiveTab().equals("0") ? "class='current'" : "" %>><a href="#tabTT">Thiết lập trực tiếp</a></li>
							<li <%=kskdBean.getActiveTab().equals("1") ? "class='current'" : "" %>><a href="#tabNPP">Thiết lập gián tiếp</a></li>
							<li <%=kskdBean.getActiveTab().equals("2") ? "class='current'" : "" %>><a href="#tabCTV">Thiết lập cộng tác viên</a></li>
						</ul>
						
						<div class="panes">
							
							<div id="tabTT" class="tab_content">
									<TABLE class="plainlabel" width="100%" border="0" cellspacing="1px" cellpadding="0px">
				                    <tr >
				                    	<td style="width: 10%;" >Ngày thiết lập</td>
				                		<td><input name ="ngaykskdtt" type="text" value="<%= kskdBean.getNgayKSKDTT()%>" style="width: 40%;" onkeypress="return keypress(event);" ></td>	
				                		<td  style="width: 10%;" >Giờ thiết lập</td>
										<td ><input name = "giokskdtt" type="text" value="<%= kskdBean.getGioTT()%>" style="width: 40%;" onkeypress="return keypress(event);" ></td>
										<td  style="width: 10%;" >Phút thiết lập</td>
										<td ><input name = "phutkskdtt" type="text" value="<%= kskdBean.getPhutTT()%>" style="width: 40%;" onkeypress="return keypress(event);" ></td>	
				                    </tr> 
				                   
									<TR>
									  <TD width="15%" class="plainlabel" ><label>
										<%  if (kskdBean.getTrangthai1().equals("Hoat dong")){%>
										  	<input name="trangthai1" type="checkbox" value ="1" checked>
										<%} else {%>
											<input name="trangthai1" type="checkbox" value ="0">
										<%} %>
									   Tự động</label></TD>
										<TD  class="plainlabel" >&nbsp;</TD>
								  </TR>
								</TABLE>
							</div>
							<div id="tabNPP" class="tab_content">
								
								<TABLE class="plainlabel" width="100%" border="0" cellspacing="1px" cellpadding="0px">
				                    <tr >
				                    	<td style="width: 10%;"    >Ngày thiết lập</td>
				                		<td><input name ="ngaykskdnpp" type="text" value="<%=kskdBean.GetNgayKSKDNPP().trim()%> "onkeypress="return keypress(event);" style="width: 50%;"  ></td>	
				                			<td  style="width: 10%;" >Giờ thiết lập</td>
										<td><input name = "giokskdnpp" type="text" value="<%=kskdBean.getGioNPP().trim()%> "onkeypress="return keypress(event);" style="width: 50%;" ></td>
										<td  style="width: 10%;" >Phút thiết lập</td>
										<td ><input name = "phutkskdnpp" type="text" value="<%= kskdBean.getPhutNPP() %>" style="width: 40%;" onkeypress="return keypress(event);" ></td>		
				                    </tr> 
									<TR>
									  <TD width="15%" class="plainlabel" ><label>
										<%  if (kskdBean.getTrangthai2().equals("Hoat dong")){%>
										  	<input name="trangthai2" type="checkbox" value ="1" checked>
										<%} else {%>
											<input name="trangthai2" type="checkbox" value ="0">
										<%} %>
									   Tự động</label></TD>
										<TD  class="plainlabel" >&nbsp;</TD>
								  </TR>
								</TABLE>
								
							</div>
							<div id="tabCTV" class="tab_content">
								
										<TABLE class="plainlabel" width="100%" border="0" cellspacing="1px" cellpadding="0px">
				                    <tr >
				                 	<td style="width: 10%;"   >Ngày thiết lập</td>
				                		<td><input name ="ngaykskdctv" type="text" value="<%= kskdBean.GetNgayKSKDCTV()%> "onkeypress="return keypress(event);" style="width: 50%;"  ></td>	
				                			<td  style="width: 10%;" >Giờ thiết lập</td>
										<td><input name = "giokskdctv" type="text" value="<%=kskdBean.getGioCTV()%> "onkeypress="return keypress(event);" style="width: 50%;" ></td>
										<td  style="width: 10%;" >Phút thiết lập</td>
										<td ><input name = "phutkskdctv" type="text" value="<%= kskdBean.getPhutCTV() %>" style="width: 40%;" onkeypress="return keypress(event);" ></td>		
				                    </tr> 
									<TR>
									  <TD width="15%" class="plainlabel" ><label>
										<%  if (kskdBean.getTrangthai3().equals("Hoat dong")){%>
										  	<input name="trangthai3" type="checkbox" value ="1" checked>
										<%} else {%>
											<input name="trangthai3" type="checkbox" value ="0">
										<%} %>
									   Tự động</label></TD>
										<TD  class="plainlabel" >&nbsp;</TD>
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
</BODY>
</HTML>
