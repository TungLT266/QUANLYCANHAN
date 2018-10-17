<%@page import="geso.traphaco.center.util.Utility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.SQLException"%>
<%@ page  import = "geso.traphaco.distributor.beans.hoadontaichinh.*" %>
<%@ page  import = "geso.traphaco.distributor.beans.donhang.ISanpham" %>
<%@ page  import = "geso.traphaco.distributor.beans.hoadontaichinh.imp.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@page import="java.util.Enumeration"%>
<%@ page  import = "java.util.List" %>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.text.DecimalFormat"%>

<%  
	IHoadontaichinh lsxBean = (IHoadontaichinh)session.getAttribute("lsxBean"); 
 	ResultSet khRs = lsxBean.getKhRs();  
	NumberFormat formatter2 = new DecimalFormat("#,###,###.####"); 
    NumberFormat formater = new DecimalFormat("##,###,###");
    ResultSet ddhRs = lsxBean.getDondathangRs(); 
	
	String ck = lsxBean.getChietkhau();
	String[] tichluy_scheme = lsxBean.getTichLuy_Scheme();
	String[] tichluy_vat = lsxBean.getTichLuy_VAT();
	String[] tichluy_tongtien = lsxBean.getTichLuy_Tongtien();

	String[] spId = lsxBean.getSpId();
	String[] spMa = lsxBean.getSpMa(); 
	String[] spTen = lsxBean.getSpTen();
	String[] spDonvi = lsxBean.getSpDonvi();
	String[] spLoai = lsxBean.getSpLoai();
	String[] spSoluong = lsxBean.getSpSoluong();
	String[] spDongia = lsxBean.getSpDongia();
	String[] spChietkhau = lsxBean.getSpChietkhau();
	String[] spSCheme = lsxBean.getSpScheme();
	String[] spVat= lsxBean.getSpVat();
	String[] spThue= lsxBean.getSpThue();
	
	String[] dhCk_diengiai = lsxBean.getDhck_diengiai();
	String[] dhCk_giatri = lsxBean.getDhck_giatri();
	String[] dhCk_loai = lsxBean.getDhck_loai();
%>

<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	} else { %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>TraphacoHYERP - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>


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

.mySCHME tr,td input
{
	color: red;
}

.mySCHME input
{
	color: red;
}

</style>

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

<script type="text/javascript" src="../scripts/ajax.js"></script>
<script type="text/javascript" src="../scripts/erp-SpListDonDatHang.js"></script>

<script language="javascript" type="text/javascript">

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
	

	 function saveform()
	 {	
		 document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho luu..' border='1' longdesc='cho luu..' style='border-style:outset'> Processing...</a>";
	 	 document.forms['hctmhForm'].action.value = 'save';
	     document.forms['hctmhForm'].submit();
	 }
	 
	 function chotform()
	 {	
		 document.getElementById("btnSave2").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho luu..' border='1' longdesc='cho luu..' style='border-style:outset'> Processing...</a>";
	 	 document.forms['hctmhForm'].action.value = 'chot';
	     document.forms['hctmhForm'].submit();
	 }
	 
	 function submitform()
	 { 
		 document.forms['hctmhForm'].action.value='submit';
	     document.forms["hctmhForm"].submit();
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
	 
	 
	function Dinhdang(element)
	{
		element.value = DinhDangTien(element.value);
		if(element.value== '' )
		{
			element.value= '';
		}
		
		//UPDATE
		Update_SoLuong();
		
	}	
	
	 function goBack()
	 {
	  	window.history.back();
	 }

	 
</script>

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
	$(document).ready(function()
	{
		$(".select2").select2();
	});
</script>

<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>

</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="hctmhForm" method="post" action="../../HoadontaichinhUpdateSvl">
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="id" value='<%= lsxBean.getId() %>'>

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:60%; padding:5px; float:left" class="tbnavigation">
        	Quản lý bán hàng > Bán hàng OTC > Xuất HĐTC > Hiển thị
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn <%= userTen %> &nbsp;
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<A href="../../HoadontaichinhSvl?userId=<%=userId %>" >
        	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1px" longdesc="Quay ve" style="border-style:outset"></A>
         <span id="btnSave">
	        <A href="javascript:saveform()" >
	        	<IMG src="../images/Save30.png" title="Chỉnh sửa thông tin YC" alt="Luu lai" border ="1px" style="border-style:outset"></A>
        </span>	
     <%--      <A href="../../HoadontaichinhPdfSvl?userId=<%= lsxBean.getUserId() %>&id=<%= lsxBean.getId() %>&task=hoadon" >
	        	<IMG src="../images/Printer30.png" title="In phieu hoa don" alt="In phieu hoa don" border ="1px" style="border-style:outset"></A>
     --%>	
    </div>
  	
  	<div align="left" style="width:100%; float:none; clear:left">
  		<fieldset>
    		<legend class="legendtitle"> Thông báo</legend>
    		<textarea name="dataerror"  rows="1" readonly="readonly" style ="width:100%"><%= lsxBean.getMsg() %></textarea>
		         <% lsxBean.setMsg(""); %>
    	</fieldset>
  	</div>
  	
    <div align="left" style="width:100%; float:none; clear:left">
    <fieldset>
    	<legend class="legendtitle">Xuất hóa đơn tài chính </legend>
        	<div style="float:none; width:100%" align="left">
            <TABLE width="100%" cellpadding="4" cellspacing="0">							
                <TR>
                    <TD width="130px" class="plainlabel" valign="top">Ngày xuất hóa đơn </TD>
                    <TD class="plainlabel" valign="top">
                    	<input type="text" class="days" readonly="readonly"  name="ngayxuat" value="<%= lsxBean.getNgayxuatHD() %>"/>
                    </TD>
                    
                    <TD width="150px" class="plainlabel" valign="top">Ngày ghi nhận công nợ </TD>
                    <TD class="plainlabel" valign="top">
                    	<input type="text" class="days" readonly="readonly"  name="ngayghinhan" value="<%= lsxBean.getNgayghinhanCN() %>"/>
                    </TD>
                </TR>
         
                
                <TR>
                	<TD class="plainlabel">Ký hiệu hóa đơn</TD>
                	<TD class="plainlabel" valign="top">
                    	<input type="text"  name="kyhieuhoadon" value="<%= lsxBean.getKyhieuhoadon() %>"/>
                    </TD>
                    
                    <TD class="plainlabel">Số hóa đơn</TD>
                	<TD class="plainlabel" valign="top">
                    	<input type="text"   name="sohoadon" value="<%= lsxBean.getSohoadon() %>"/>
                    </TD>
                </TR>
                
                 <TR>
                	<TD class="plainlabel" >Khách hàng </TD>
                    <TD class="plainlabel"  width="250px" >
                    	<select name = "khId" class="select2" style="width: 200px" onchange="submitform();" >
                    		<option value=""> </option>
                        	<%
                        		if(khRs != null)
                        		{
                        			try
                        			{
                        			while(khRs.next())
                        			{  
                        			if( khRs.getString("pk_seq").equals(lsxBean.getKhId())){ %>
                        				<option value="<%= khRs.getString("pk_seq") %>" selected="selected" ><%= khRs.getString("ten") %></option>
                        			<%}else { %>
                        				<option value="<%= khRs.getString("pk_seq") %>" ><%= khRs.getString("ten") %></option>
                        		 <% } } khRs.close();} catch(SQLException ex){}
                        		}
                        	%>
                    	</select>
                    </TD> 
                    
                   <TD class="plainlabel" >Hình thức thanh toán </TD>
                    <TD class="plainlabel" >
                    	<select name="hinhthuctt" id="hinhthuctt" style="width: 200px;" >
							    <% 
							   String[] mangchuoi=new String[]{"TM/CK","Chuyển khoản","Tiền mặt"};
							   for(int k=0;k < mangchuoi.length;k ++ ){
								   
								   if(lsxBean.getHinhthucTT().equals(mangchuoi[k])) {
									   %>
									    	<option value="<%=mangchuoi[k] %>" selected="selected"><%=mangchuoi[k] %> </option>
									   <%
								   }else{
									   %>
								    	<option value="<%=mangchuoi[k] %>" ><%=mangchuoi[k] %> </option>
								  		 <%  
								   }
							   }
							  %>
												   
						</select>
                    </TD>               	                   
                </TR>
                
                
                <TR>
                	<TD class="plainlabel" >Người mua hàng </TD>
                    <TD class="plainlabel">
                    	<input type="text"  name="nguoimuahang" value="<%= lsxBean.getNguoimua() %>" style="width: 200px" />
                    </TD>
                    
                    <%if(lsxBean.getNppId().equals("106210")){  // NẾU CN HCM: khi có check In Người mua hàng --> hiện trong phiếu in  %>
                    <TD class="plainlabel" >In Người mua hàng</TD>
                    <TD class="plainlabel" >
                    	<%if(lsxBean.getInNguoimua().equals("1")){%>                   	
                    		 <input type="checkbox"  name="innguoimuahang" value="1" checked="checked" />
                    	 <%}else{ %>
                    		 <input type="checkbox"  name="innguoimuahang" value="0"  />
                         <%}%>
                    </TD>     
                    <%}else{ %>
                    <TD class="plainlabel" ></TD>
                    <TD class="plainlabel" >
                    	<input type="hidden"  name="innguoimuahang" value="1"  />
                    </TD>
                    <%} %>
                </TR>
                
                
                 <TR>
                	<TD class="plainlabel" >Ghi chú </TD>
                    <TD class="plainlabel" colspan="3" >
                    	<input type="text"  name="ghichu" value="<%= lsxBean.getGhichu() %>" style="width: 500px"  readonly="readonly"/>
                    </TD>
                </TR>
                 </TR>
                   <TR>
                	<TD class="plainlabel" >Mã vụ việc </TD>
                    <TD class="plainlabel" colspan="3" >
                    	<input type="text"  name="mavv" value="<%= lsxBean.getMavuviec() %>" style="width: 500px" />
                    </TD>
                   
                </TR>
                <TR>
                	<TD class="plainlabel" >Đơn đặt hàng </TD>
                    <TD class="plainlabel" colspan="2" >
                    	<fieldset  >
							<legend> Chọn đơn đặt hàng</legend>
							<table width="100%" cellpadding="4" cellspacing="0">
								<TR class="tbheader">
						             <TH align="center" width="30%">Số đơn hàng</TH>
						             <TH align="left" width="50%"> Ngày </TH>
						             <TH align="center" width="20%"> Chọn </TH>
               					</TR>                  								
             					<%
         							if(ddhRs != null)
         							{
         								while(ddhRs.next()){ %>
           								<tr class= 'tblightrow'>
           									<td>
           										 <%=ddhRs.getString("pk_seq") %> 		
           									</td>
           									<td>                   										
           										 <%=ddhRs.getString("NgayDonHang") %> 	
           									</td>
  										<% 
   										 if(lsxBean.getDondathangId().contains(ddhRs.getString("pk_seq").trim())){%>
       										<TD align="center"><input type ="checkbox" checked="checked" name ="ddhid" value ="<%= ddhRs.getString("pk_seq") %>" onclick="return false;" ></TD>
       									<%}else{ %>
       										<TD align="center"><input type ="checkbox" id="ddhid" name ="ddhid" value ="<%= ddhRs.getString("pk_seq") %>" onclick="return false;" ></TD>
       									<%}%>
   										</tr>
   									<% } ddhRs.close(); } %>                   								                  							
                   			</table>
                   		</fieldset>
                    </TD> 
                    
                    <TD class="plainlabel" colspan="2" valign="bottom" >
                		<table class="plainlabel" width="100%"  style="padding-top:0 ; margin-top:0">                 								                   								                   								
                  				<tr>
                  					<td>Tổng tiền </td>
                  					<td>
                  						<input type="text" style="text-align:right" id="tongtien" name="tongtien" value="<%= lsxBean.getTongtienBVAT() %>" >
                  					</td>
                  				</tr>
                  				<tr>
                  					<td>Tiền chiết khấu </td>
                  					<td>
                  						<input type="text" style="text-align:right" id="tienKM" name="tienKM" value="<%= lsxBean.getTongCK() %>">
                  					</td>
                  				</tr>                 				  
                  				
                  				<tr>
                  					<td>Tiền vat </td>
                  					<td>
                  						<input type="text" style="text-align:right" id="tienvat" name="tienvat" value="<%= lsxBean.getTongVAT() %>">
                  					</td>
                  				</tr>
                  				
                  				<tr>
                  					<td>Tiền thực thu </td>
                  					<td>
                  						<input type="text" style="text-align:right" id="tiensauvat" name="tiensauvat" value="<%= lsxBean.getTongtienAVAT() %>">
                  					</td>
                  				</tr>
                  		</table>
                  	</TD>  
                </TR>
                
               
               <!--  <TR class="plainlabel">
					<TD colspan="4">
						<a class="button2" href="javascript:loadcontent()">
                        <img style="top: -4px;" src="../images/button.png" alt="">Xem chi tiết</a>&nbsp;&nbsp;&nbsp;&nbsp;
					</TD>
				</TR> -->
            </TABLE>
			<hr />
			
			 <table cellpadding="0px" cellspacing="1px" width="100%">
				<tr class="tbheader">
					<th align="center" width="15%" >Mã sản phẩm</th>
					<th align="center" width="20%" >Tên sản phẩm</th>
					<th align="center" width="10%" >Đơn vị</th>
					<th align="center" width="8%" >Số lượng </th>
					<th align="center" width="10%" >Đơn giá</th>
					<th align="center" width="8%" >VAT</th>
					<th align="center" width="10%" >Chiết khấu</th>
					<th align="center" width="10%" >Tiền thuế</th>
					<th align="center" width="10%" >Thành tiền</th>
					<th align="center" width="10%" >CTKM</th>
				</tr>
				
				<%
					int count = 0;
					if(spMa != null)
					{
						for(int i = 0; i < spMa.length; i++)
						{ %>
					
						<tr>
							<td>
								<input type="hidden" name="spId" value="<%= spId[i] %>" style="width: 100%"   > 
								<%-- <input type="hidden" name="spLoai" value="<%= spLoai[i] %>" style="width: 100%"   >  --%>
								<input type="text" name="spMa" value="<%= spMa[i] %>" style="width: 100%"  readonly="readonly"  > 
							</td>
							<td> <input type="text" name="spTen" value="<%= spTen[i] %>" style="width: 100%" readonly="readonly"> </td>
							<td>
								<input type="text" name="donvi" value="<%= spDonvi[i] %>" style="width: 100%; text-align: right;" readonly="readonly">							
							</td>
							<td>
								<input type="text" name="soluong" value="<%= formater.format(Double.parseDouble(spSoluong[i])) %>" style="width: 100%; text-align: right;" readonly="readonly">							
							</td>
							<td>
								<input type="text" name="spDongia" value="<%= formatter2.format(Double.parseDouble(spDongia[i])) %>" style="width: 100%; text-align: right;" readonly="readonly">							
							</td>
							<td>
								<input type="text" name="spVat" value="<%= formater.format(Double.parseDouble(spVat[i])) %>" style="width: 100%; text-align: right;" readonly="readonly">							
							</td>
							<td>
								<input type="text" name="spChietkhau" value="<%= formater.format(Double.parseDouble(spChietkhau[i])) %>" style="width: 100%; text-align: right;" readonly="readonly">							
							</td>
							<td>
								<input type="text" name="spTienthue" value="<%= formater.format(Double.parseDouble(spThue[i])) %>" style="width: 100%; text-align: right;" readonly="readonly">							
							</td>
							<td>
								<input type="text" name="thanhtien" value="<%= formater.format(Double.parseDouble(spSoluong[i])*Double.parseDouble(spDongia[i])- Double.parseDouble(spChietkhau[i])+ Double.parseDouble(spThue[i]) ) %>" style="width: 100%; text-align: right;" onkeypress="return keypress(event); " >							
							</td>
							<td>
								<input type="text" name="scheme"  value="<%=0 %>" style="width: 100%; text-align: right;" onkeypress="return keypress(event); " >							
							</td>
							
						</tr>	
							
					<% count ++; } } %>
											
					<% if(tichluy_scheme != null) 
					{ 
						for(int i = 0; i < tichluy_scheme.length; i++)
						{
							double _vat = Double.parseDouble(tichluy_vat[i].replaceAll(",", ""));
							double _chietkhau = Double.parseDouble(tichluy_tongtien[i].replaceAll(",", ""));
							
							double _tienthue = _vat * _chietkhau / 100;
							double _thanhtien = _chietkhau + _tienthue;
							System.out.println("Tiền CN5 "+tichluy_tongtien[i]);
							%>
							
							<TR class= 'tblightrow'>
							    <TD align = "right" colspan="5"  ><input type="text" value="<%= tichluy_scheme[i] %>" style="width:100%; text-align: right;" disabled="disabled"></TD>
							    <TD align = "center" ><input type="text" value="<%= tichluy_vat[i] %>"  style="width:100%; text-align: right;" disabled="disabled" ></TD>
							    <TD align = "center" ><input  name = "ttTrakm_chuaVat" type="text" value="<%= tichluy_tongtien[i] %>"  style="width:100%; text-align: right;" disabled="disabled"  ></TD>
							    <TD align = "center" >
							       <input name = "ttTrakm_Vat1" type="text" value="<%= formater.format(_tienthue) %>"  style="width:100%; text-align: right;" disabled="disabled" >
							       <input name = "ttTrakm_Vat" type="hidden" value="<%= _tienthue %>"  style="width:100%; ">
							    </TD>
							    <TD align = "center" ><input name="ttTrakm" type="text" value="<%= formater.format(_thanhtien) %>"  style="width:100%;text-align:right" disabled="disabled" ></TD>
							    <TD align = "center" ><input name="trakmSpScheme" type="text" value=" " style="width:100%" readonly></TD>
								<TD align = "center" style="display: none;" >
							    	<input name="khoNVBH1" type="checkbox"  value="0" onclick="return false;" >
							    	<input name="khoNVBH" type="hidden"  value="0"  >
							    </TD>
							</TR>
							
						<% } } %>
			</table> 
				
            </div>
     </fieldset>	
    </div>
</div>

<%
	try
	{
		lsxBean.DBclose(); 
	}
	catch(Exception er){ }

	} %>
</form>
</BODY>
</html>