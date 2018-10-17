<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="geso.traphaco.center.beans.hoadonphelieu.imp.*" %>
<%@ page import="geso.traphaco.center.beans.hoadonphelieu.*" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@page import="java.sql.SQLException" %>
<%@ page import="java.util.Hashtable"%>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
<%
 	IErpXuatkmkhonghd obj =(IErpXuatkmkhonghd)session.getAttribute("csxBean");
	ResultSet khRs = obj.getKhRs();  
	String[] tenSanpham = obj.getTensansham();
	String[] donvitinh = obj.getDvt();
	String[] soluong = obj.getSoluong();
	String[] dongia = obj.getDongia();
	String[] thanhtien = obj.getTongtien();
	List<IErpHoaDonPL_SP> splist =obj.GetSanPhamList();
	
	Hashtable<String, String> sp_chitiet = (Hashtable<String, String>)obj.getSp_Chitiet();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>TraphacoHYERP - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
<script type="text/javascript" src="../scripts/dropdowncontent2.js"></script>

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
}
</style>

<script type="text/javascript" src="../scripts/ajax.js"></script>
<script type="text/javascript" src="../scripts/erp-SpListNhapKho.js"></script>

<SCRIPT language="JavaScript" type="text/javascript">
	
	function submitform()
	{
		document.forms['khtt'].action.value = '';
	    document.forms["khtt"].submit();
	}
	
	function changePO()
	{ 
		document.forms['khtt'].action.value = 'changePO';
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
			if (keypressed == 8 || keypressed == 127 || keypressed == 37 || keypressed == 39 || keypressed == 0 || keypressed == 45|| keypressed == 46)
			{//Phím Delete và Phím Back
				return;
			}
			return false;
		}
	}
	
	function FormatTien(e)
	{
		if(e.value == '')
			e.value = '0';
		else
		{
			e.value = DinhDangDonGia(e.value);
		}
	}
	
	function DinhDangDonGia(num) 
	{
		num = num.toString().replace(/\$|\,/g,'');
		 
		var sole = '';
		if(num.indexOf(".") >= 0)
		{
			sole = num.substring(num.indexOf('.'));
		}
		
		if(isNaN(num))
		num = "0";
		sign = (num == (num = Math.abs(num)));
		num = Math.floor(num*100+0.50000000001);
		num = Math.floor(num/100).toString();
		for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
		num = num.substring(0,num.length-(4*i+3)) + ',' + num.substring(num.length-(4*i+3));

		var kq;
		if(sole.length >= 0)
		{
			kq = (((sign)?'':'-') + num) + sole;
		}
		else
			kq = (((sign)?'':'-') + num);
		
		//alert(kq);
		return kq;
	}
	
	function save()
	{
		document.forms["khtt"].action.value = "save";
		document.forms["khtt"].submit(); 
    }
	
</SCRIPT>

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

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
	$(document).ready(function()
	{
		$(".select2").select2();
	});
</script>

<script type="text/javascript">
function replaces()
{
	var spMa = document.getElementsByName("spMa");
	var spTen = document.getElementsByName("spTen");  
	var donvi = document.getElementsByName("donvi");
	var soluong = document.getElementsByName("soluong");
	var dongia = document.getElementsByName("dongia");
	
	var thuevat = document.getElementsByName("thuevat");
	var vat = document.getElementsByName("vat");
	var thanhtien = document.getElementsByName("thanhtien");
	
	var i;
	for(i = 0; i < spMa.length; i++)
	{
		if(spMa.item(i).value != "")
		{
			var sp = spMa.item(i).value;
			var pos = parseInt(sp.indexOf(" - "));

			if(pos > 0)
			{
				spMa.item(i).value = sp.substring(0, pos);
				sp = sp.substr(parseInt(sp.indexOf(" - ")) + 3);
				
				spTen.item(i).value = sp.substring(0, parseInt(sp.indexOf(" [")));
				sp = sp.substr(parseInt(sp.indexOf(" [")) + 2);
				
				donvi.item(i).value = sp.substring(0, parseInt(sp.indexOf("] [")));
				sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
				
				thuevat.item(i).value = sp.substring(0, parseInt(sp.indexOf("]")));
				
				//dongia.item(i).value = DinhDangTien(sp.substring(0, parseInt(sp.indexOf("]")))); 
				dongia.item(i).value = '';
				
				submitform();
			}
		}
		else
		{
			spMa.item(i).value = "";
			spTen.item(i).value = "";
			donvi.item(i).value = "";
			soluong.item(i).value = "";
			dongia.item(i).value = "";
			
			thuevat.item(i).value = "";
			vat.item(i).value = "";
			thanhtien.item(i).value = "";	
		}
	}	
	
	TinhTien();
	setTimeout(replaces, 500);
}


 function TinhTien()
 {
	var spMa = document.getElementsByName("spMa");
	var soluong = document.getElementsByName("soluong");
	var dongia = document.getElementsByName("dongia");
	var thueVAT = document.getElementsByName("thuevat");
	var VAT = document.getElementsByName("vat");
	var thanhtien = document.getElementsByName("thanhtien");

	var tongtienBVAT = 0;
	var tongtienVAT = 0;
	var tongtienAVAT = 0;
	
	for(var i = 0; i < spMa.length; i++)
	{
		if( spMa.item(i).value != "" && dongia.item(i).value != "" && soluong.item(i).value != "" )
		{
			var _thueVAT = thueVAT.item(i).value.replace(/,/g,"");
			if(_thueVAT == '')
				_thueVAT = '0';	
			
			var _tienBVAT = parseFloat( dongia.item(i).value.replace(/,/g,"") ) * parseFloat( soluong.item(i).value.replace(/,/g,"") );
			var _tienVAT = parseFloat(_tienBVAT) * (  parseFloat(_thueVAT) / 100 );
			var _tienAVAT = parseFloat(_tienBVAT) + parseFloat(_tienVAT);
			
			VAT.item(i).value = DinhDangTien(_tienVAT);
			thanhtien.item(i).value = DinhDangTien(_tienAVAT);
			
			tongtienBVAT += parseFloat( _tienBVAT );
			tongtienVAT += parseFloat( _tienVAT );
			tongtienAVAT += parseFloat( _tienAVAT );
		}		
	}
	
	document.getElementById("bvat").value = DinhDangDonGia( tongtienBVAT );
	document.getElementById("tienvat").value = DinhDangDonGia( tongtienVAT );
	document.getElementById("avat").value = DinhDangDonGia( tongtienAVAT );
	
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
 
</script>


</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name ="khtt" method="post" action="../../ErpXuatkmkhonghdUpdateSvl" >
<input type="hidden" name="userId" value='<%= userId %>' >
<input type="hidden" name="id" value='<%= obj.getId() %>' >
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
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý bán hàng > Chức năng > Xuất khuyến mại không hóa đơn > Cập nhật</TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %></TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<TR ><TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
							<TD width="30" align="left"><A href="../../ErpXuatkmkhonghdSvl?userId=<%= userId%>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
						    <TD width="2" align="left" ></TD>
						    <TD width="30" align="left" >
						    <div id="btnSave">
						    <A href="javascript: save();" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A>
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
				
	    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1"><%= obj.getMsg() %></textarea>
						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Thông tin hóa đơn phế liệu</LEGEND>
						
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
							 
	                          <TR>
	                          		<TD class="plainlabel" valign="middle" width="140px" >Ngày ghi nhận</TD>   
			                        <TD class="plainlabel" valign="middle" width="280px" >
			                        	<input type="text" class="days" name="ngayghinhan" value="<%= obj.getNgayghinhan() %>"   > 
			                        </TD>          
	                          		<TD width="100px" class="plainlabel" >Xuất cho </TD>
				                    <TD class="plainlabel"  >
				                    	<select name="xuatchoId" class="select2" style="width: 200px" onchange="submitform();" >
				                    		<% if(obj.getXuatcho().equals("0")) { %>
				                    			<option value="0" selected="selected" >Đối tác</option>
				                    		<% } else { %>
				                    			<option value="0" >Đối tác</option>
				                    		<% } %>
				                    		<% if(obj.getXuatcho().equals("1")) { %>
				                    			<option value="1" selected="selected" >Khách hàng ETC</option>
				                    		<% } else { %>
				                    			<option value="1" >Khách hàng ETC</option>
				                    		<% } %>
				                    	</select>
				                    </TD>      
			                  </TR> 
	                          <TR>
	                          		<TD class="plainlabel" >Đối tác / ETC </TD>
				                    <TD class="plainlabel"  >
				                    	<select name = "khId" class="select2" style="width: 200px"  >
				                    		<option value=""> </option>
				                        	<%
				                        		if(khRs != null)
				                        		{
				                        			try
				                        			{
				                        			while(khRs.next())
				                        			{  
				                        			if( khRs.getString("pk_seq").equals(obj.getKhId())){ %>
				                        				<option value="<%= khRs.getString("pk_seq") %>" selected="selected" ><%= khRs.getString("ten") %></option>
				                        			<%}else { %>
				                        				<option value="<%= khRs.getString("pk_seq") %>" ><%= khRs.getString("ten") %></option>
				                        		 <% } } khRs.close();} catch(SQLException ex){}
				                        		}
				                        	%>
				                    	</select>
				                    </TD>
				                    <TD class="plainlabel" valign="middle"  >Diễn giải </TD>   
			                        <TD class="plainlabel" valign="middle" >
			                        	<input type="text" name="diengiai" value="<%= obj.getDiengiai() %>"  > 
			                        </TD>            
			                  </TR> 
			                  <TR>                          		
				                    <TD class="plainlabel" valign="middle"  >Mã vụ việc </TD>   
			                        <TD class="plainlabel" valign="middle" colspan="3" >
			                        	<select name="mavuviec" >
			                        		<% if( obj.getMavuviec().equals("KM") ) { %>
			                        			<option value="KM" selected="selected" >KM</option>
			                        		<% } else { %>
			                        			<option value="KM" >KM</option>
			                        		<% } %>
			                        		<% if( obj.getMavuviec().equals("Hàng Đề Nghị") ) { %>
			                        			<option value="Hàng Đề Nghị" selected="selected" >Hàng Đề Nghị</option>
			                        		<% } else { %>
			                        			<option value="Hàng Đề Nghị" >Hàng Đề Nghị</option>
			                        		<% } %>
			                        	</select>
			                        </TD>            
			                  </TR> 
			                 
			                  <TR  >
	                          		<TD class="plainlabel" valign="middle" >Số tiền trước VAT</TD>   
			                        <TD class="plainlabel" valign="middle" >
			                        	<input type="text" id="bvat"  name="bvat"  style="text-align:right;" value="<%= obj.getBvat() %>" readonly="readonly"  > 
			                        </TD>          
	                          		<TD class="plainlabel" valign="middle" width="120px" >Số tiền VAT</TD>   
			                        <TD class="plainlabel" valign="middle"  >
			                        	<input type="text"  id="tienvat" name="tienvat" style="text-align:right;" value="<%= obj.getVat() %>"   > 
			                        </TD>          
			                  </TR> 
			                  
			                  <TR  >
	                          		<TD class="plainlabel" valign="middle" >Số tiền sau VAT</TD>   
			                        <TD class="plainlabel"  valign="middle" colspan="3" >
			                        	<input type="text" id="avat"  name="avat"  style="text-align:right;" value="<%= obj.getAvat() %>"  readonly="readonly" > 
			                        </TD>  
			                                       
			                  </TR> 
 
			                  <TR>
			                  	<TD colspan="4">
			                  	
			                  		<table width="100%; " cellpadding="0" cellspacing="1"  >
			                  		
			                  			<tr class="tbheader" >
			                  				<th width="10%" >Mã sản phẩm</th>
			                  				<th width="25%" >Tên sản phẩm</th>
			                  				<th width="8%" >Đơn vị</th>
			                  				<th width="8%" >Số lượng</th>
			                  				<th width="10%" >Đơn giá</th>
			                  				<th width="10%" >% VAT</th>
			                  				<th width="10%" >Tiền VAT</th>
			                  				<th width="10%" >Thành tiền</th>
			                  				<th width="10%" >Ghi chú</th>
			                  			</tr>

			                  			<% 
			                  			  int m = 0 ;
			                  			  System.out.println("::: SP LIST: " + splist.size() );
			                  			  if(splist != null)
			                  			  {			                  		
			                  				IErpHoaDonPL_SP sanpham;
			    							int stt = 0;
			                  			    
			    							int count = 0;
			                  				while(m < splist.size()) 
			                  				{ 
			                  					sanpham = splist.get(m); 
			                  					//System.out.println("::: MA: " + sanpham.getMaSanPham() + " -- TEN: " + sanpham.getTenSanPham() );
			                  					%> 
			                  				<tr>
			                  					<td>
			                  						<input type="text" name="spMa" value="<%= sanpham.getMaSanPham() %>" style="width: 100%"  onkeyup="ajax_showOptions(this,'nhapkho',event)" AUTOCOMPLETE="off"  > 
			                  					</td>
			                  					<td>
			                  						<input type="text" name="spTen" value="<%= sanpham.getTenSanPham() %>" style="width: 100%;" readonly="readonly" >
			                  					</td>
			                  					<td>
			                  						<input type="text" name="donvi" value="<%= sanpham.getDonViTinh() %>" style="width: 100%; " readonly="readonly"  >
			                  					</td>
			                  					<td>
			                  						<input type="text" name="soluong" value="<%= sanpham.getSoLuong() %>" style="width: 60%; text-align: right; "  onkeyup="FormatTien(this);" >
			                  						<a href="" id="sanpham_<%=count%>" rel="subcontent_<%=count%>"> &nbsp; <img alt="Chọn số lô" src="../images/vitriluu.png"></a>

													<DIV id="subcontent_<%=count%>" style="position: absolute; visibility: hidden; border: 9px solid #80CB9B; background-color: white; width: 300px; max-height: 400px; overflow: auto; padding: 4px;">
														
														
														<table width="300px" align="center">
															<tr>
																<!-- <th width="100px" style="font-size: 11px">Số lượng</th> -->
																<th width="100px" style="font-size: 11px; text-align: center;">Số lô</th>
																<th width="100px" style="font-size: 11px; text-align: center;">Ngày hết hạn</th>
																<th width="100px" style="font-size: 11px; text-align: center;">Chọn</th>
																<!-- <th width="100px" style="font-size: 11px">Ngày sản xuất</th>
																<th width="100px" style="font-size: 11px">Ngày hết hạn</th> -->
															</tr>
														</table>
				
														<div style="max-height: 100px; overflow: auto;">
															<table width="95%" align="center">
																<%
																	ResultSet rsLO = obj.getSoloTheoSp(sanpham.getMaSanPham(), "", "");
																	
																	//int stt = 0;
										                        	//String ct = sp_chitiet.get(spMa[i]);
										                        	//System.out.println("CHuoi SP: " + ct);
										                        	if(rsLO != null)
										                        	{
										                        		//String[] ctARR = ct.substring(0, ct.length() - 3).split("___");
										                        		
										                        		String selected = "";
										                        		while( rsLO.next()  )
										                        		{
										                        			String key = sanpham.getMaSanPham() + "__" + rsLO.getString("solo") + "__" + rsLO.getString("ngayhethan");
										                        			//String[] _ct = ctARR[j].split("__");
										                        			
										                        			//System.out.println("::: KEY JSP: " + key);
										                        			if( sp_chitiet.get(key) != null )
										                        				selected = " checked='checked' ";
										                        			else
										                        				selected = "";
																%>
				
																<tr>
																	<td width="100px" >
																		<input type="text" style="width: 100%; text-align: center;" name="<%= sanpham.getMaSanPham() %>_spSOLO" value="<%= rsLO.getString("SOLO") %>" readonly="readonly" >
																	</td>
																	<td width="100px" >
																		<input type="text" style="width: 100%; text-align: center;" name="<%= sanpham.getMaSanPham() %>_spNGAYHETHAN" value="<%= rsLO.getString("NGAYHETHAN") %>" readonly="readonly" >
																	</td>
																	<td width="100px" style="text-align: center;" >
																		<input type="radio"  name="<%= sanpham.getMaSanPham() %>_spCHON" value="<%= rsLO.getString("solo") + "__" + rsLO.getString("ngayhethan") %>" <%= selected %> >
																	</td>
																</tr>
				
																<%  } rsLO.close(); } %>
				
															</table>
														</div>
														
													</DIV> 
													<script type="text/javascript">
										            	dropdowncontent.init('sanpham_<%=count%>', "left-top", 300, "click");
										            </script>
			                  						
			                  					</td>
			                  					<td>
			                  						<input type="text" name="dongia" value="<%= sanpham.getDonGia() %>" style="width: 100%; text-align: right; "  onkeyup="FormatTien(this);" >
			                  					</td>
			                  					<td>
			                  						<input type="text" name="thuevat" value="<%= sanpham.getThuevat() %>" style="width: 100%; text-align: right; " readonly="readonly" >
			                  					</td>
			                  					<td>
			                  						<input type="text" name="vat" value="<%= sanpham.getVat() %>" style="width: 100%; text-align: right; "  readonly="readonly">
			                  					</td>
			                  					<td>
			                  						<input type="text" name="thanhtien" value="<%= sanpham.getThanhTien() %>" style="width: 100%; text-align: right; " readonly="readonly" >
			                  					</td>
					                  			<td >
				           	 						<input type="text" style="width: 100%;" name="ghichusanpham" value="<%= sanpham.getGhiChu1() %>" />						               
				           	 		     		</td>
			                  				</tr>
			                  			
			                  			<% m++; count++; } }%>
			                  			
			                  			<%  while (m < 50) { %>
			                  				<tr>
			                  					<td>
			                  						<input type="text" name="spMa" value="" style="width: 100%"  onkeyup="ajax_showOptions(this,'nhapkho',event)" AUTOCOMPLETE="off"  > 
			                  					</td>
			                  					<td>
			                  						<input type="text" name="spTen" value="" style="width: 100%;" onchange="DinhDangTT();">
			                  					</td>
			                  					<td>
			                  						<input type="text" name="donvi" value="" style="width: 100%;" readonly="readonly" >
			                  					</td>
			                  					<td>
			                  						<input type="text" name="soluong" value="" style="width: 100%; text-align: right; "  onKeyPress = "return keypress(event);" onkeyup="FormatTien(this);" >
			                  					</td>
			                  					<td>
			                  						<input type="text" name="dongia" value="" style="width: 100%; text-align: right; "  onKeyPress = "return keypress(event);" onkeyup="FormatTien(this);" >
			                  					</td>
			                  					<td>
			                  						<input type="text" name="thuevat" value="" style="width: 100%; text-align: right; "  readonly="readonly" >
			                  					</td>
			                  					<td>
			                  						<input type="text" name="vat" value="" style="width: 100%; text-align: right; "  readonly="readonly">
			                  					</td>
			                  					<td>
			                  						<input type="text" name="thanhtien" value="" style="width: 100%; text-align: right; "  readonly="readonly" >
			                  					</td>
			                  					<td align="center">
				           	 						<input type="text" style="width: 100%;" name="ghichusanpham" value="" />							               
				           	 		     		</td>
			                  				</tr>
			                  			<%  m++ ;} %>
			                  			
			                  		</table>
			                    		
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
<script type="text/javascript" src="../scripts/loadingv2.js"></script>

<script type="text/javascript">
	replaces();
</script>

</BODY>
</HTML>
<%}%>
