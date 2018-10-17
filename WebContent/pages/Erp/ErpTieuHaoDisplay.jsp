 
<%@page import="geso.traphaco.erp.beans.tieuhao.ISanphamLo"%>
<%@page import="geso.traphaco.erp.beans.tieuhao.ISanpham"%>
<%@page import="geso.traphaco.erp.beans.tieuhao.IErpTieuHao"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>

<% IErpTieuHao tieuhaoBean = (IErpTieuHao)session.getAttribute("tieuhaoBean"); %>
<% List<ISanpham> spList = tieuhaoBean.getSpList();  %>
<% NumberFormat formater = new DecimalFormat("#,###,###"); %>

<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  %>

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

<link rel="stylesheet" type="text/css" href="../css/speechbubbles.css" />
<script type="text/javascript" src="../scripts/ajax.js"></script>
<script type="text/javascript" src="../scripts/erp-spbomList.js"></script>
 
<script type="text/javascript" src="../scripts/cool_DHTML_tootip.js"></script>
<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>


<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
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
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { $("#nccId").select2(); });
     
 </script>

<script language="javascript" type="text/javascript">
function taotieuhaonew()
{	
	 
	 
		 document.forms['tieuhaoForm'].action.value='tieuhaothem';
  	  document.forms['tieuhaoForm'].submit();
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
	
	function roundNumber(num, dec) 
	{
		var result = Math.round(num*Math.pow(10,dec))/Math.pow(10,dec);
		return result;
	}
	
	function keypress(e, textbox) //Hàm dùng d? ngan ngu?i dùng nh?p các ký t? khác ký t? s? vào TextBox
	{
		
		var keypressed = null;
		if (window.event)
			keypressed = window.event.keyCode; //IE
		else
			keypressed = e.which; //NON-IE, Standard
		

		if(textbox.value.indexOf(".") >= 0 && keypressed == 46 ) {
			return false;
		}			
	
		if (keypressed < 48 || keypressed > 57)
		{ 
			if (keypressed == 8 || keypressed == 127 || keypressed == 37 || keypressed == 39 || keypressed == 0 || keypressed == 46)
			{//Phím Delete và Phím Back
				return;
			}
			return false;
		}
	}
 
</script>
	
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="tieuhaoForm" method="post" action="../../ErpTieuHaoSvl">
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="id" value='<%= tieuhaoBean.getId() %>'>

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:60%; padding:5px; float:left" class="tbnavigation">
        	Quản lý cung ứng > Quản lý mua hàng > Phiếu tiêu hao > Cập nhật
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn <%= userTen %> &nbsp;
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<A href= "../../ErpTieuHaoSvl?userId=<%= userId %>" >
        	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1px" longdesc="Quay ve" style="border-style:outset"></A>
      	<A href="../../ErpTieuHaoSvl?userId=<%=userId%>&print=<%= tieuhaoBean.getId() %>" >
	        <IMG src="../images/Printer30.png" title="In phiếu tiêu hao" alt="In phiếu tiêu hao" border ="1px" style="border-style:outset"></A>
       
    </div>
  	
  	<div align="left" style="width:100%; float:none; clear:left">
  		<fieldset>
    		<legend class="legendtitle"> Thông báo</legend>
    		<textarea name="dataerror"  rows="1" readonly="readonly" style ="width:100%"><%= tieuhaoBean.getMsg() %></textarea>
		    <% tieuhaoBean.setMsg(""); %>
    	</fieldset>
  	</div>
  	
    <div align="left" style="width:100%; float:none; clear:left">
    <fieldset>
    	<legend class="legendtitle"> Phiếu tiêu hao</legend>
        	<div style="float:none; width:100%" align="left">
            <TABLE width="100%" cellpadding="4" cellspacing="0">
            	 <TR>
                    <TD width="150px" class="plainlabel" valign="top">Mã nhận hàng</TD>
                    <TD class="plainlabel" valign="top">
                    	<input type="text" id="manhanhang" name="manhanhang" 
                    			value="<%= tieuhaoBean.getManhanhang() %>"  maxlength="10" readonly /></TD>
				</TR>
				<TR>
                    <TD width="150px" class="plainlabel" valign="top">Chứng từ mua hàng</TD>
                    <TD class="plainlabel" valign="top">
                    	<input type="text" id="manhanhang" name="manhanhang" 
                    			value="<%= tieuhaoBean.getSoMuaHang() %>"   readonly /></TD>
				</TR>
               
				   <TR>
                    <TD   class="plainlabel" valign="top">Ngày thực hiện</TD>
                    <TD    align="left" class="plainlabel" valign="top">
                    	<input type="text"  class="days" id="ngaytieuhao" name="ngaytieuhao" value="<%=tieuhaoBean.getNgaytieuhao()   %>" 
                    		maxlength="10" readonly />
                    </TD>
                    </TR>
                    
                     <TR>
                    <TD   class="plainlabel" valign="top">Ngày ghi nhận NV</TD>
                    <TD    align="left" class="plainlabel" valign="top">
                    	<input type="text"  class="days" id="ngaychot" name="ngaychot" value="<%=tieuhaoBean.getNgaychot()   %>" 
                    		maxlength="10" readonly />
                    </TD>
                    </TR>
                <TR>
                    <TD width="150px" class="plainlabel" valign="top">Mã phiếu</TD>
                    <TD class="plainlabel" valign="top">
                    	<input type="text" id="maphieu" name="maphieu" 
                    			value="<%= tieuhaoBean.getId() %>"  maxlength="10" readonly /></TD>
				</TR>
       
                <TR>
                    <TD width="150px" class="plainlabel" valign="top">Mã sản phẩm</TD>
                    <TD class="plainlabel" valign="top">
                    	<input type="hidden" value="<%= tieuhaoBean.getSanphamId() %>" name="sanphamId">
                    	<input type="text" id="sanphamMa" name="sanphamMa" value="<%= tieuhaoBean.getSanphamMa()+"--"+tieuhaoBean.getGhiChuMuaHang() %>"  maxlength="10" readonly /></TD>
				</TR>
                <TR>
                    <TD width="150px" class="plainlabel" valign="top">Tên sản phẩm</TD>
                    <TD class="plainlabel" valign="top">                    	
                    	<input type="text" id="sanphamten" name="sanphamTen" value="<%= tieuhaoBean.getSanphamTen() %>"  maxlength="10" readonly /></TD>
				</TR>
                <TR>
                    <TD width="150px" class="plainlabel" valign="top">Số lượng</TD>
                    <TD class="plainlabel" valign="top">
                    	<input type="text" id="soluong" name="soluong" 
                    			value="<%= tieuhaoBean.getSoLuong() %>"  maxlength="10" readonly /> 
                    			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    			<a
									class="button2" href="javascript:taotieuhaonew()"> <img
										style="top: -4px;" src="../images/button.png" alt="">Tiêu hao thêm
								</a>&nbsp;&nbsp;&nbsp;&nbsp;
								
                    			</TD>
				</TR>
				<TR>
                    <TD width="150px" class="plainlabel" valign="top">Ghi chú</TD>
                    <TD class="plainlabel" valign="top">
                    	<input type="text" id="ghichu" name="ghichu" 
                    			value="<%= tieuhaoBean.getGhichu() %>"  readonly /></TD>
				</TR>
                
            </TABLE>
            <hr> 
		</div>
           
        <div align="left" style="width:100%; float:none; clear:none;" class="plainlabel">
            <TABLE class="tabledetail" width="100%" border="0" cellpadding="1" cellspacing="1" >
                <TR class="tbheader"> 
                	<TH align="center" width="5%">STT</TH>
               		<TH align="center" width="25%">Mã vật tư</TH>
                	<TH align="center" width="50%">Tên vật tư</TH>
               	 	<TH align="center" width="10%">SL định mức</TH>
               	 	<TH align="center" width="10%">SL thực tế</TH>
               	 	<TH align="center" width="8%"  >Chọn lô</TH>
                </TR>
                <%
                	for(int i = 0; i < spList.size(); i++)
                	{
                		ISanpham sp = spList.get(i);
	               		%>
	               		<tr>
           	 				<td align="center">
           	 					<input type="text" style="width: 100%; text-align:center; " value="<%= i + 1 %>" name= "stt" readonly="readonly">
           	 				</td>
           	 				<td align="left">
           	 					<input type="text" style="width: 100%; text-align: left;" value="<%= sp.getMa() %>" name= "vattuMa" onkeyup="ajax_showOptions(this,'abc',event)" autocomplete='off'  >
           	 					<input type="hidden" value="<%= sp.getId() %>" name= "vattuId">
           	 				</td>
           	 				<td align="left">
           	 					<input type="text" style="width: 100%; text-align: left;" value="<%= sp.getTen() %>" name= "vattuTen" readonly="readonly" >
           	 				</td>
           	 				<td align="right">
           	 					<input type="text" style="width: 100%; text-align: right;" value="<%= sp.getSoLuongChuan() %>" name= "vattuSoLuongChuan" readonly="readonly" >
           	 				</td>
           	 				<td align="right">
           	 					<input type="text" style="width: 100%; text-align: right;" value="<%= sp.getSoLuongThucTe() %>" name= "vattuSoLuongThucTe" onchange="javascript:Reload_Lo()" onkeypress="return keypress(event, this);" >
           	 				</td>
           	 				<td align="center">
							  	<a href="" id="<%= sp.getMa() %>pobup" rel="subcontent<%= i %>">
	           	 							<img alt="Số lô - Vị trí hàng hóa xuất" src="../images/vitriluu.png"></a>
	           	 				<DIV id="subcontent<%= i %>" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
				                             background-color: white; width: 350px; padding: 4px;">
				                    <table width="90%" align="center">
				                        <tr>
				                            <th width="100px">Số lô</th>
				                            <th width="50px">Số lượng tồn</th>
				                            <th width="50px">Số lượng tiêu hao</th>
				                          
				                        </tr>
				                        <%
				                      
				                        List<ISanphamLo> spDetailList = sp.getSpList();
				                        	System.out.println("Size day:  "+spDetailList.size());
				                        	int stt = 1; 
				                        	if(spDetailList.size() > 0)
				                        	{
				                        		for(int sd = 0; sd < spDetailList.size(); sd ++)
				                        		{
				                        			ISanphamLo spDetail = spDetailList.get(sd);
				                        		%>
				                        			<tr>
							                            <td>
							                            	<input type="text" style="width: 100%" name="<%=  sp.getMa() + ".solo" %>" value="<%= spDetail.getsolo() %>" readonly="readonly" /></td>
							                            	<td>
							                            	<input type="text" style="width: 100%"  name="<%=  sp.getMa() + ".soluongton" %>" value="<%= spDetail.getSoLuongton() %>" readonly="readonly" /></td>
							                            <td>
							                            	<input type="text" style="width: 100%"  name="<%=  sp.getMa()+ ".soluong" %>" value="<%= spDetail.getSoLuong() %>"   /></td>
 
							                        </tr>
				                        		<%}
				                        	}
				                        %>
				                    </table>
				                     <div align="right"><a href="javascript:dropdowncontent.hidediv('subcontent<%= i %>')">Đóng lại</a></div>
				                </DIV>
	        	 			</td>
	        	 			
           	 			</tr>
           	 	<%} %>
           	 	 <%
                	for(int i = spList.size(); i < spList.size()+5; i++)
                	{
                		  
	               		%>
	               		<tr>
           	 				<td align="center">
           	 					<input type="text" style="width: 100%; text-align:center; " value="<%= i + 1 %>" name= "stt" readonly="readonly">
           	 				</td>
           	 				<td align="left">
           	 					<input type="text" style="width: 100%; text-align: left;" value="" name= "vattuMa" onkeyup="ajax_showOptions(this,'abc',event)" autocomplete='off'  >
           	 					<input type="hidden" value="" name= "vattuId">
           	 				</td>
           	 				<td align="left">
           	 					<input type="text" style="width: 100%; text-align: left;" value="" name= "vattuTen" readonly="readonly" >
           	 				</td>
           	 				<td align="right">
           	 					<input type="text" style="width: 100%; text-align: right;" value="" name= "vattuSoLuongChuan" readonly="readonly" >
           	 				</td>
           	 				<td align="right">
           	 					<input type="text" style="width: 100%; text-align: right;" value="" onchange="javascript:Reload_Lo()" name=  "vattuSoLuongThucTe" onkeypress="return keypress(event, this);" >
           	 				</td>
           	 				<td align="center">
							  
	        	 			</td>
	        	 			
           	 			</tr>
           	 	<%} %>
           	 	
            </TABLE> 
        	</div>      
     
     </fieldset>	
    </div>
</div>
</form>

 
<script type="text/javascript">

 
	<% 
	for(int i = 0; i < spList.size(); i++)
		{
		ISanpham sp = spList.get(i);
	%>
		dropdowncontent.init('<%=sp.getMa()+ "pobup"%>', "left-top", 300, "click");
	<%} %>


</script>
<%
try {

	if(spList!=null){
		spList.clear();
	}
	tieuhaoBean.close();
	
} catch(Exception er){
	
}

%>
</BODY>
</html>