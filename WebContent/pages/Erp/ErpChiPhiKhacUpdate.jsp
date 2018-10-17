<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="geso.traphaco.erp.beans.chiphikhac.imp.*" %>
<%@ page import="geso.traphaco.erp.beans.chiphikhac.*" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>

<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@page import="java.sql.SQLException" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<%
 	IErpChiphikhac obj =(IErpChiphikhac)session.getAttribute("cpkBean");
%>
<% ResultSet tienteList = obj.getTienteRs(); %>
<%	NumberFormat formatter = new DecimalFormat("#,###,###"); %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
<style type="text/css">
#mainContainer {
	width: 660px;
	margin: 0 auto;
	text-align: left;
	height: 100%;
	border-left: 3px double #000;
	border-right: 3px double #000;
}

#formContent {
	padding: 5px;
}
/* END CSS ONLY NEEDED IN DEMO */

/* Big box with list of options */
#ajax_listOfOptions {
	position: absolute; /* Never change this one */
	width: auto; /* Width of box */
	height: 200px; /* Height of box */
	overflow: auto; /* Scrolling features */
	border: 1px solid #317082; /* Dark green border */
	background-color: #C5E8CD; /* White background color */
	color: black;
	text-align: left;
	font-size: 1.0em;
	z-index: 100;
}

#ajax_listOfOptions div {
	/* General rule for both .optionDiv and .optionDivSelected */
	margin: 1px;
	padding: 1px;
	cursor: pointer;
	font-size: 1.0em;
}

#ajax_listOfOptions .optionDiv { /* Div for each item in list */
	
}

#ajax_listOfOptions .optionDivSelected { /* Selected item in the list */
	background-color: #317082; /*mau khi di chuyen */
	color: #FFF;
}

#ajax_listOfOptions_iframe {
	background-color: #F00;
	position: absolute;
	z-index: 5;
}

form {
	display: inline;
}

#dhtmltooltip {
	position: absolute;
	left: -300px;
	width: 150px;
	border: 1px solid black;
	padding: 2px;
	background-color: lightyellow;
	visibility: hidden;
	z-index: 100;
	/*Remove below line to remove shadow. Below line should always appear last within this CSS*/
	filter: progid:DXImageTransform.Microsoft.Shadow(color=gray, direction=135
		);
}

#dhtmlpointer {
	position: absolute;
	left: -300px;
	z-index: 101;
	visibility: hidden;
}


.error {
	color: #f00;
}

input[readonly] {
	color: #111;
	background: #f7f7f7;
}

</style>
<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/speechbubbles.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>

<link rel="stylesheet" type="text/css" href="../css/speechbubbles.css" />
<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/speechbubbles.js"></script>
<script type="text/javascript">
	jQuery(function($){ 
		 $('.addspeech').speechbubble();})
</script>
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
<script type="text/javascript" src="../scripts/ajax.js"></script>
<script type="text/javascript" src="../scripts/erp-chiphi.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script type="text/javascript" src="../scripts/cool_DHTML_tootip.js"></script>
<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>

<SCRIPT language="JavaScript" type="text/javascript">

	function hoantat(element){
		dropdowncontent.hidediv(element);
		save();
	}
	
	function submitform()
	{
	    document.forms["khtt"].submit();
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
	  document.forms["khtt"].action.value = "save";
	  document.forms["khtt"].submit(); 
    }
	
	function replacesNCC()
	{
		var nccId = document.getElementById("nccId");
		var nccTen = document.getElementById("nccTen");
		
		var temp = nccId.value;
		if(temp == "")
		{
			nccTen.value = "";
		}
		else
		{
			if(parseInt(temp.indexOf(" - ")) > 0)
			{
				nccId.value = temp.substring(0, parseInt(temp.indexOf(" - ")));
				
				temp = temp.substr(parseInt(temp.indexOf(" - ")) + 3);
				temp = temp.substr(parseInt(temp.indexOf(", ")) + 2);
				nccTen.value = temp.substring(0, temp.indexOf(" [ ") );

			}
						
		}

		var chiphi = document.getElementsByName("chiphi");
		var diengiaicp = document.getElementsByName("diengiaicp");
		
		var sodong =  chiphi.length;
		
		var i;
		for(i = 0; i < sodong; i++)
		{
			if(chiphi.item(i).value != "")
			{
				var cp = chiphi.item(i).value;
				var pos = parseInt(cp.indexOf(" -- "));
				
				if(pos > 0)
				{
					chiphi.item(i).value = cp.substring(0, pos);
					cp = cp.substr(parseInt(cp.indexOf(" -- ")) + 3);
					diengiaicp.item(i).value = cp.substring(0, parseInt(cp.indexOf(" [")));
				}
				
			}	
		}

		setTimeout(replacesNCC, 500);
	}
	
	function replacesNV()
	{
		var nvId = document.getElementById("nvId");
		var nvTen = document.getElementById("nvTen");
		
		var temp = nvId.value;
		if(temp == "")
		{
			nvTen.value = "";
		}
		else
		{
			if(parseInt(temp.indexOf(" - ")) > 0)
			{
				nvId.value = temp.substring(0, parseInt(temp.indexOf(" - ")));
				
				temp = temp.substr(parseInt(temp.indexOf(" - ")) + 3);
				temp = temp.substr(parseInt(temp.indexOf(", ")) + 2);
				nvTen.value = temp.substring(0, temp.indexOf(" [ ") );

			}
		}
		var chiphi = document.getElementsByName("chiphi");
		var diengiaicp = document.getElementsByName("diengiaicp");
		
		var sodong =  chiphi.length;
		
		var i;
		for(i = 0; i < sodong; i++)
		{
			if(chiphi.item(i).value != "")
			{
				var cp = chiphi.item(i).value;
				var pos = parseInt(cp.indexOf(" -- "));
				
				if(pos > 0)
				{
					chiphi.item(i).value = cp.substring(0, pos);
					cp = cp.substr(parseInt(cp.indexOf(" -- ")) + 3);
					diengiaicp.item(i).value = cp.substring(0, parseInt(cp.indexOf(" [")));
				}
				
			}	
		}

		setTimeout(replacesNV, 500);
		
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

	 function Dinhdangdukien(element)
	 {
	 	element.value=DinhDangTien(element.value);
	 	if(element.value== '' )
	 	{
	 		element.value= '';
	 	}
	 	
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
	
	function tinhthue(){
		var stt = document.getElementsByName("stt");
		
		var tienchuathue = document.getElementsByName("tienchuathue");
		var tongtienthue = document.getElementsByName("thue");
		var tongcong = document.getElementsByName("cong");
			
		for(i = 0; i < stt.length; i++){
			var tongtien = 0;
			var tongthue = 0;
			
			var th = "tienhang" + stt.item(i).value;		
			var tienhang = document.getElementsByName(th);
			
			var ts = "thuesuat" + stt.item(i).value;
			var thuesuat = document.getElementsByName(ts);
			
			var t = "thue" + stt.item(i).value;		
			var thue = document.getElementsByName(t);
			
			var c = "cong" + stt.item(i).value;
			var cong = document.getElementsByName(c);
			
			for(j = 0; j < tienhang.length; j++){
				var temp = parseFloat(tienhang.item(j).value.replace(/\$|\,/g,''))*parseFloat(thuesuat.item(j).value.replace(/\$|\,/g,''))/100;
				thue.item(j).value = DinhDangTien(temp);
				tongthue = tongthue + temp;
				
				cong.item(j).value =  DinhDangTien(parseFloat(tienhang.item(j).value.replace(/\$|\,/g,'')) + temp);
				tongtien = tongtien + parseFloat(cong.item(j).value.replace(/\$|\,/g,'')); 
			}
			tienchuathue.item(i).value = DinhDangTien(tongtien - tongthue);
			tongtienthue.item(i).value = DinhDangTien(tongthue);
			tongcong.item(i).value = DinhDangTien(tongtien);
		}
		
	}
	
	function tinhthue_new(){
		var stt = document.getElementsByName("stt");
		var tienchuathue = document.getElementsByName("tienchuathue");
		var tongtienthue = document.getElementsByName("thue");
		var tongcong = document.getElementsByName("cong");
	 
		for(i = 0; i < stt.length; i++){
			var tongtien = 0;
			var tongthue = 0;
			
			var th = "tienhang" + stt.item(i).value;		
			var tienhang = document.getElementsByName(th);
			
			var ts = "thuesuat" + stt.item(i).value;
			var thuesuat = document.getElementsByName(ts);
			
			var t = "thue" + stt.item(i).value;		
			var thue = document.getElementsByName(t);
			
			var c = "cong" + stt.item(i).value;
			var cong = document.getElementsByName(c);
			
			for(j = 0; j < tienhang.length; j++){
				
				var thuesuat_tmp= parseFloat(thue.item(j).value.replace(/\$|\,/g,'')) * 100 /parseFloat(tienhang.item(j).value.replace(/\$|\,/g,''));
				thuesuat.item(j).value=DinhDangTien(thuesuat_tmp);
				 
				var temp =   parseFloat(thue.item(j).value.replace(/\$|\,/g,'')) ;
				 
				tongthue = tongthue + temp;
				thue.item(j).value=DinhDangTien(parseFloat(temp));
				
				
				cong.item(j).value =  DinhDangTien(parseFloat(tienhang.item(j).value.replace(/\$|\,/g,'')) + temp);
				tongtien = tongtien + parseFloat(cong.item(j).value.replace(/\$|\,/g,'')); 
			}
			tienchuathue.item(i).value = DinhDangTien(tongtien - tongthue);
			tongtienthue.item(i).value = DinhDangTien(tongthue);
			tongcong.item(i).value = DinhDangTien(tongtien);
		}
		
	}
</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name ="khtt" method="post" action="../../ErpChiphikhacUpdateSvl" >
<input type="hidden" name="userId" value='<%= userId %>' >
<input type="hidden" name="Id" value='<%= obj.getId() %>' >
<input type="hidden" name="action" value="0">
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý công nợ > Công nợ phải trả > Chi phí khác > Cập nhật</TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %></TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<TR ><TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
							<TD width="30" align="left"><A href="../../ErpChiphikhacSvl?userId=<%= userId%>" ><img src="../images/Back30.png" alt="Quay về"  title="Quay về" border="1" longdesc="Quay về" style="border-style:outset"></A></TD>
						    <TD width="2" align="left" ></TD>
						    <TD width="30" align="left" >
						    <div id="btnSave">
						    <A href="javascript: save();" ><IMG src="../images/Save30.png" title="Lưu lại" alt="Lưu lại" border = "1"  style="border-style:outset"></A>
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
						<LEGEND class="legendtitle">Thông báo </LEGEND>
				
	    				<textarea name="dataerror"  style="width: 100% ; font-weight:bold"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1"><%= obj.getMsg() %></textarea>
						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Chi phí khác </LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
                          
                          <TR>
                          		<TD class="plainlabel" valign="middle" width="120px" >Ngày nhập</TD>   
		                        <TD class="plainlabel" valign="middle"  colspan = 3>
		                        	<input type="text" name="ngaynhap" value="<%= obj.getNgaynhap() %>" class="days"  > 
		                        </TD>          
		                  </TR> 
                         
		                  <TR>
                          		<TD class="plainlabel" valign="middle" >Diễn giải </TD>   
		                        <TD class="plainlabel" valign="middle"  colspan = 3>
		                        	<input type="text" name="diengiai" value="<%= obj.getDiengiai() %>"  > 
		                        </TD>          
		                  </TR> 
		                 
		                  <TR>
                          		<TD class="plainlabel" valign="middle" >Loại đối tượng </TD>   
		                        <TD class="plainlabel" valign="middle"  >
		                        	<SELECT NAME = "loai" onChange = "submitform();">
		                        	<% if(obj.getLoai().equals("0")){ %>
		                      			<OPTION VALUE = "0" SELECTED >Nhà cung cấp</OPTION>
		                      			<OPTION VALUE = "1" >Nhân viên</OPTION>
		                      		<%}else{ %>
		                      			<OPTION VALUE = "0" >Nhà cung cấp</OPTION>
		                      			<OPTION VALUE = "1" SELECTED>Nhân viên</OPTION>
									<%} %>		                      		
		                        	</SELECT> 
		                        </TD>          
                    			<TD class="plainlabel">Tiền tệ</TD>
                    			<TD class="plainlabel" style = "width:100px">
                    	
			                        <select name="tienteId" id="tienteId" style = "width:150px" onChange = "ChangeTienTe();">
                        		
            	            	<%
                	        		if(tienteList != null)
                    	    		{
                        				try
                        				{
                        					while(tienteList.next())
                        					{  
                        						if( tienteList.getString("pk_seq").equals(obj.getTienteId())){ %>
                        						<option value="<%= tienteList.getString("pk_seq") %>" selected="selected" ><%= tienteList.getString("ten")%></option>
                        					<%	}else { %>
                        						<option value="<%= tienteList.getString("pk_seq") %>" ><%= tienteList.getString("ten") %></option>
                        		 			<%  } 
                        					} 
                        					tienteList.close();
                        				} catch(SQLException ex){}
                        		}
                        	%>
                        </select>
                     </TD> 
			                        
		                  </TR> 
		                  
		                  <% if(obj.getLoai().equals("0")){ %>
		                  <TR>
								<TD class="plainlabel">Mã nhà cung cấp</TD>
								<TD class="plainlabel" width = "20%">								
									<input type="text" id="nccId" name="nccId" value="<%= obj.getNccId() %>" >								
								</TD>
								<TD class="plainlabel" width = "10%">Tên nhà cung cấp</TD>
								<TD class="plainlabel">
									<input type="text" id="nccTen" name="nccTen" value="<%= obj.getNccTen() %>" readonly="readonly" >
								</TD>
		                  </TR> 
		                  
		                  <% }else{ %>

		                  <TR>
								<TD class="plainlabel">Mã nhân viên</TD>
								<TD class="plainlabel" width = "20%">								
									<input type="text" id="nvId" name="nvId" value="<%= obj.getNvId() %>" >								
								</TD>
								<TD class="plainlabel" width = "10%">Tên nhân viên</TD>
								<TD class="plainlabel">
									<input type="text" id="nvTen" name="nvTen" value="<%= obj.getNvTen() %>" readonly="readonly" >
								</TD>
		                  </TR> 

						  <%} %>

		                  <TR>
		                  		<td colspan="4">

									<table width="100%" cellpadding="0px" cellspacing="1px">
										<tr class="tbheader">
											<th align="center" width="10%">Mã chi phí</th>
											<th align="center" width="40%">Diễn giải chi phí</th>											
											<th align="center" width="15%">Tổng tiền chưa thuế</th>
											<th align="center" width="15%">Thuế</th>
											<th align="center" width="15%">Cộng</th>
											<th align="center" width="5%">Hóa đơn</th>
										</tr>
																			
										
										<% 
										String[] chiphi;
										String[] diengiaicp;
										String[] tongtien;
										String[] tongthue;
										String[] maHD;
										String[] mausoHD;
										String[] kyhieu;
										String[] sohd;
										String[] ngayhd;
										String[] ncc;
										String[] mst;
										String[] tienhang;
										String[] thuesuat;
										String[] tienthue;
										String[] ghichu;
											
										chiphi = obj.getChiphi();
										diengiaicp = obj.getDiengiaicp();
										tongtien = obj.getTongtien();
										tongthue = obj.getTongthue();
										
										int i = 0;
										if(chiphi != null){
											for(i = 0; i < chiphi.length; i++){ 
												if(chiphi[i].trim().length() > 0){
											%>
												<TR>
												<td>
													<input type="text" name="chiphi" value = "<%= chiphi[i] %>" style="width: 100%" onkeyup="ajax_showOptions(this,'chiphi',event)" AUTOCOMPLETE="off"  id="chiphi_<%=i %>">
													<input type="hidden" name="stt" value = "<%= i %>" >
												</td>

												<td>
													<input type="text" name="diengiaicp" value = "<%= diengiaicp[i] %>" style="width: 100%" >
												</td>


												<td>
													<input type="text" name="tienchuathue" value = "<%= formatter.format(Double.parseDouble(tongtien[i])) %>" style="width: 100%" readonly = readonly>
												</td>

												<td>
													<input type="text" name="thue" value = "<%= formatter.format(Double.parseDouble(tongthue[i])) %>" style="width: 100%" readonly = readonly>
												</td>

												<td>
													<input type="text" name="cong" value = "<%= formatter.format(Double.parseDouble(tongtien[i]) + Double.parseDouble(tongthue[i])) %>" style="width: 100%; text-align: right;" readonly = readonly>
												</td>

												<td align = "center">
	                   								<A href="" id="hd<%= i %>" rel="subcontent<%= i %>"><img alt="Chi tiết hóa đơn" title="Chi tiết hóa đơn" src="../images/vitriluu.png"></a>
	                   								<DIV id="subcontent<%= i %>" style="position:absolute; visibility: hidden; border: 2px solid #80CB9B; background-color: white; width: 1000px; padding: 4px; max-height: 300px; overflow: auto; ">
					          							
											<%
													obj.getData(chiphi[i]);
													maHD = obj.getMaHD();
													mausoHD = obj.getMausoHD();
													kyhieu = obj.getKyhieu();
													sohd = obj.getSohd();
													ngayhd = obj.getNgayhd();
													ncc = obj.getTenNcc();
													mst = obj.getMST();
													tienhang = obj.getTienhang();
													thuesuat = obj.getThuesuat();
													tienthue = obj.getTienthue();
													ghichu = obj.getGhichu();
	 										%>
					          					
													<table width="100%" align="center">
						        						<tr class="tbheader">
						        							<th width="80px" >Mã hóa đơn</th>
						        							<th width="80px" >Mẫu hóa đơn</th>
						        							<th width="80px" >Ký hiệu</th>
						         							<th width="100px">Số hóa đơn</th>
						         							<th width="100px">Ngày hóa đơn </th>
							                  <% 
								                  // Nếu đối tượng là Nhân viên
								                  	if(obj.getLoai().equals("1")){ %>

						         							<th width="100px">Tên NCC </th>
						         							<th width="100px">Mã số thuế </th>
						         			 <%		} %>
						         							
						         							<th width="100px">Tiền hàng </th>
						         							<th width="100px">Thuế suất </th>
						         							<th width="100px">Tiền thuế </th>
						         							<th width="100px">Cộng </th>
						         							<th width="100px">Ghi chú </th>
						        						</tr>
											      <% 
											      	int m;
											      	if(kyhieu != null){
											      		
											      		for(m = 0; m < kyhieu.length; m++){ 
											      			if(kyhieu[m].trim().length() > 0){		%>
											      		<tr>
											      		<td>
											      			<input type="text" name=<%= "maHD" + i %> value = "<%= maHD[m] %>" style="width: 100%" readonly="readonly" >
											      		</td>
											      		<td>
											      			<input type="text" name=<%= "mausoHD" + i %> value = "<%= mausoHD[m] %>" style="width: 100%" >
											      		</td>
											      		<td>
											      			<input type="text" name=<%= "kyhieu" + i %> value = "<%= kyhieu[m] %>" style="width: 100%" >
											      		</td>
											      		
											      		<td>
											      			<input type="text" name=<%= "sohd" + i  %> value = "<%= sohd[m] %>" style="width: 100%" >
											      		</td>

											      		<td>
											      			<input type="text" name=<%= "ngayhd" + i %> value = "<%= ngayhd[m] %>" style="width: 100%" class="days">
											      		</td>
							                  	<% 
								                  // Nếu đối tượng là Nhân viên
								                  		if(obj.getLoai().equals("1")){ %>
											      		<td>
											      			<input type="text" name=<%= "tenncc" + i %> value = "<%= ncc[m] %>" style="width: 100%" >
											      		</td>
											      		<td>
											      			<input type="text" name=<%= "mst" + i  %> value = "<%= mst[m] %>" style="width: 100%" >
											      		</td>
											      		
						         			 	<%		} %>

											      		<td>
											      			<input type="text" name=<%= "tienhang" + i %> value = "<%= formatter.format(Double.parseDouble(tienhang[m])) %>" style="width: 100%" onkeypress="return keypress(event);" onkeyup="Dinhdangdukien(this)" onChange="tinhthue();">
											      		</td>

											      		<td>
											      			<input type="text" name=<%= "thuesuat" + i  %> value = "<%= formatter.format(Double.parseDouble(thuesuat[m])) %>" style="width: 100%" onkeypress="return keypress(event);" onkeyup="Dinhdangdukien(this)" onChange="tinhthue();">
											      		</td>

											      		<td>
											      			<input type="text" name=<%= "thue" + i %> value = "<%= formatter.format(Double.parseDouble(tienthue[m])) %>" style="width: 100%"  onChange="tinhthue_new();">
											      		</td>

											      		<td>
											      			<input type="text" name=<%= "cong" + i %> value = "<%= formatter.format(Double.parseDouble(tienhang[m]) + Double.parseDouble(tienthue[m])) %>" style="width: 100%" readonly=readonly>
											      		</td>

											      		<td>
											      			<input type="text" name=<%= "ghichu" + i %> value = "<%= ghichu[m] %>" style="width: 100%" >
											      		</td>

														</tr>
											      	<% 	}  			// Ket thuc vong lap
											      	   }
											      	 }				// Ket thuc if(kyhieu != null)
											      		 
											      // Thêm 5 dòng trống để có thể thêm hóa đơn	 

										      		for(int n = 0; n < 10; n++){ %>
										      		<tr>
										      		<td>
										      			<input type="text" name=<%= "maHD" + i %> value = "" style="width: 100%" readonly="readonly" >
										      		</td>
										      		<td>
										      			<input type="text" name=<%= "mausoHD" + i %> value = "" style="width: 100%" >
										      		</td>
										      		<td>
										      			<input type="text" name=<%= "kyhieu" + i %> value = "" style="width: 100%" >
										      		</td>
										      		
										      		<td>
										      			<input type="text" name=<%= "sohd" + i  %> value = "" style="width: 100%" >
										      		</td>

										      		<td>
										      			<input type="text" name=<%= "ngayhd" + i %> value = "" style="width: 100%" class="days">
										      		</td>
						                  	<% 
							                  // Nếu đối tượng là Nhân viên
							                  		if(obj.getLoai().equals("1")){ %>
										      		<td>
										      			<input type="text" name=<%= "tenncc" + i %> value = "" style="width: 100%" >
										      		</td>
										      		<td>
										      			<input type="text" name=<%= "mst" + i  %> value = "" style="width: 100%" >
										      		</td>
										      		
					         			 	<%		} %>

										      		<td>
										      			<input type="text" name=<%= "tienhang" + i %> value = "0" style="width: 100%" onkeypress="return keypress(event);" onkeyup="Dinhdangdukien(this)" onChange="tinhthue();">
										      		</td>

										      		<td>
										      			<input type="text" name=<%= "thuesuat" + i  %> value = "0" style="width: 100%" onkeypress="return keypress(event);" onkeyup="Dinhdangdukien(this)" onChange="tinhthue();">
										      		</td>

										      		<td>
										      			<input type="text" name=<%= "thue" + i %> value = "0" style="width: 100%"  onChange="tinhthue_new();">
										      		</td>

										      		<td>
										      			<input type="text" name=<%= "cong" + i %> value = "0" style="width: 100%" readonly=readonly>
										      		</td>

										      		<td>
										      			<input type="text" name=<%= "ghichu" + i %> value = "" style="width: 100%" >
										      		</td>

													</tr>
													
										      	<% }  			// Ket thuc vong lap for cho 5 dong hoa don trang
										      	%>
													</table>
						       							<div align="right">
					                     					<label style="color: red" ></label>
					                     					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				    	                 					<a href="javascript:dropdowncontent.hidediv('subcontent<%= i %>')">Hoàn tất</a>
							                     		</div>
						     				
						     						</DIV>

													</td>

													</TR>

									   <%		}	
									   		} // Kết thúc vòng for cho các chi phí đã nhập
										}	  // Kết thúc if(chiphi != null)				 
									   %>
										
										<%	// Thêm 5 dòng trong cho chi phí
											int j = i;
											while(j <= i + 4) { %>
										
											<TR>
												<td>
													<input type="text" name="chiphi" style="width: 100%" value= "" onkeyup="ajax_showOptions(this,'chiphi',event)" AUTOCOMPLETE="off"  id="chiphi_<%=i %>">
													<input type="hidden" name="stt" value = "<%= j %>" >
												</td>

												<td>
													<input type="text" name="diengiaicp" value= "" style="width: 100%" >
												</td>


												<td>
													<input type="text" name="tienchuathue" value= "0" style="width: 100%" readonly = readonly>
												</td>

												<td>
													<input type="text" name="thue" value= "0" style="width: 100%" class="days"  onChange="tinhthue_new();">
												</td>

												<td>
													<input type="text" name="cong" value= "0" style="width: 100%; text-align: right;" readonly = readonly>
												</td>

												<td align = "center">
	                   								<A href="" id="hd<%= j %>" rel="subcontent<%= j %>"><img alt="Chi tiết hóa đơn" title="Chi tiết hóa đơn" src="../images/vitriluu.png"></a>
	                   								
	                   								<DIV id="subcontent<%= j %>" style="position:absolute; visibility: hidden; border: 2px solid #80CB9B; background-color: white; width: 1000px; padding: 4px; max-height: 300px; overflow: auto; ">
					          							<table width="100%" align="center">
					          							
					          					
							                  
						        						<tr class="tbheader">
						        							<th width="80px" >Mã hóa đơn</th>
						        							<th width="80px" >Mẫu hóa đơn</th>
						        							<th width="80px" >Ký hiệu</th>
						         							<th width="100px">Số hóa đơn</th>
						         							<th width="100px">Ngày hóa đơn </th>
							                  <% 
							                  // Nếu đối tượng là Nhân viên

						         						if(obj.getLoai().equals("1")){ %>

						         							<th width="100px">Tên NCC </th>
						         							<th width="100px">Mã số thuế </th>
						         			<%			} %>
						         			
						         							
						         							<th width="100px">Tiền hàng </th>
						         							<th width="100px">Thuế suất </th>
						         							<th width="100px">Tiền thuế </th>
						         							<th width="100px">Cộng </th>
						         							<th width="100px">Ghi chú </th>
						        						</tr>
		
											      <% for(int m = 0; m < 10; m++){ %>
											      		<tr>
											      		<td>
											      			<input type="text" name=<%= "maHD" + j %> value = "" style="width: 100%" readonly="readonly" >
											      		</td>
											      		<td>
											      			<input type="text" name=<%= "mausoHD" + j %> value = "" style="width: 100%" >
											      		</td>
											      		<td>
											      			<input type="text" name=<%= "kyhieu" + j %> value = "" style="width: 100%" >
											      		</td>
											      		
											      		<td>
											      			<input type="text" name=<%= "sohd" + j  %> value = "" style="width: 100%" >
											      		</td>

											      		<td>
											      			<input type="text" name=<%= "ngayhd" + j %> value = "" style="width: 100%" class="days">
											      		</td>

						                  	<% 
							                  // Nếu đối tượng là Nhân viên
							                  		if(obj.getLoai().equals("1")){ %>
											      		<td>
											      			<input type="text" name=<%= "tenncc" + j %> value = "" style="width: 100%" >
											      		</td>
											      		<td>
											      			<input type="text" name=<%= "mst" + j  %> value = "" style="width: 100%" >
											      		</td>
											      		
					         			 	<%		} %>



											      		<td>
											      			<input type="text" name=<%= "tienhang" + j %> value = "0" style="width: 100%" onkeypress="return keypress(event);" onkeyup="Dinhdangdukien(this)" onChange="tinhthue();">
											      		</td>

											      		<td>
											      			<input type="text" name=<%= "thuesuat" + j  %> value = "0" style="width: 100%" onkeypress="return keypress(event);" onkeyup="Dinhdangdukien(this)" onChange="tinhthue();">
											      		</td>

											      		<td>
											      			<input type="text" name=<%= "thue" + j %> value = "0" style="width: 100%"  onChange="tinhthue_new();">
											      		</td>

											      		<td>
											      			<input type="text" name=<%= "cong" + j %> value = "0" style="width: 100%" readonly=readonly>
											      		</td>

											      		<td>
											      			<input type="text" name=<%= "ghichu" + j %> value = "" style="width: 100%" >
											      		</td>

														</tr>
											      <% } %>
											      		</table>
						       							
						       							<div align="right">
					                     					<label style="color: red" ></label>
					                     					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				    	                 					<a href="javascript:dropdowncontent.hidediv('subcontent<%= j %>')">Hoàn tất</a>
							                     		</div>
						     				
						     						</DIV>
													
												</td>

											</TR>
										
										<% j++; 
											} %>
										
										
									</table>
		                  			
		                  		</td>
		                  </tr>
		                  
						</TABLE>
							
						</FIELDSET>						
					</TD>
				</TR>
			</TABLE>
		</TD>
	</TR>
	</TABLE>
<script type="text/javascript">
	replacesNCC();
	jQuery(function()
	{		
		$("#nccId").autocomplete("ErpNccMuaHangListGiay.jsp");
	});	
	
</script>

<script type="text/javascript">
	<% 
	for(int k = 0; k < j; k++)
	{
	%>
		dropdowncontent.init("hd<%= k %>", "left-top", 300, "click");
	<% } %>


</script>
	
<script type="text/javascript">
	replacesNV();
	jQuery(function()
	{		
		$("#nvId").autocomplete("ErpNhanvienList.jsp");
	});	

</script>

</form>

</BODY>
</HTML>
<%
obj.DbClose();}%>
