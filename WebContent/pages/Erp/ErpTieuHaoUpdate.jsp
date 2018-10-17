 
<%@page import="geso.traphaco.erp.beans.tieuhao.ISanphamLo"%>
<%@page import="geso.traphaco.erp.beans.tieuhao.IErpTieuHao"%>
<%@page import="geso.traphaco.erp.beans.tieuhao.ISanpham"%>
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
<% NumberFormat formater_3le = new DecimalFormat("#,###,###.###"); %>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>SalesUp - Project</TITLE>
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
	
	
	function Reload_Lo()
	 {	
 
	 	 document.forms['tieuhaoForm'].action.value='reload';
	     document.forms['tieuhaoForm'].submit();
	 }
	function tinhSoLuong(spMa){
		var soluongChiTiet = document.getElementsByName(spMa+".soluong");
		var sum = 0;
		for( var i = 0; i < soluongChiTiet.length; i++ ){
			sum = parseFloat(sum) + parseFloat(soluongChiTiet.item(i).value);
		}
		var soluongTong = document.getElementById(spMa+".soluongTong");
		soluongTong.value  = sum;
	}
	 function saveform()
	 {	
		 
		 document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho luu..' border='1' longdesc='cho luu..' style='border-style:outset'> Processing...</a>";
	 	 document.forms['tieuhaoForm'].action.value='save';
	     document.forms['tieuhaoForm'].submit();
	 }
		  function replaces()
		{
	 
 
				var masp1 = document.getElementsByName("vattuMa");
				var tensp1 = document.getElementsByName("vattuTen");
			 
				 
				var j;
	 
				for(j = 0; j < masp1.length; j++)
				{
					
					if(masp1.item(j).value != "")
					{
	 
							var sp = masp1.item(j).value;
							var pos = parseInt(sp.indexOf(" -- "));
						
							if(pos > 0)
							{
	 
								masp1.item(j).value = sp.substring(0, pos);
								sp = sp.substr(parseInt(sp.indexOf(" -- ")) + 3);
								tensp1.item(j).value = sp.substring(0, parseInt(sp.indexOf(" [")));
								
								/* sp = sp.substr(parseInt(sp.indexOf(" [")) + 2);
								donvitinh1.item(j).value = sp.substring(0, parseInt(sp.indexOf("] ["))); */
			 					
							}
					
					}else{
						masp1.item(j).value="";
						tensp1.item(j).value="";
					}
					
				}
				 
			 

			setTimeout(replaces, 500);
		}
	 
</script>
	
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="tieuhaoForm" method="post" action="../../ErpTieuHaoUpdateSvl">
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="id" value='<%= tieuhaoBean.getId() %>'>

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:60%; padding:5px; float:left" class="tbnavigation">
        	Quản lý mua hàng > Mua hàng trong nước > Phiếu tiêu hao > Cập nhật
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn <%= userTen %> &nbsp;
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<A href= "../../ErpTieuHaoSvl?userId=<%= userId %>" >
        	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1px" longdesc="Quay ve" style="border-style:outset"></A>
        <span id="btnSave">
	        <A href="javascript:saveform()" >
	        	<IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border ="1px" style="border-style:outset"></A>
	        	     	<A href="../../ErpTieuHaoSvl?userId=<%=userId%>&print=<%= tieuhaoBean.getId() %>" >
	        <IMG src="../images/Printer30.png" title="In phiếu tiêu hao" alt="In phiếu tiêu hao" border ="1px" style="border-style:outset"></A>
        </span>
        <!-- <A id = "btnPrint" href="../../ErpNhanHangPdfSvl?userId=<%=userId %>&nhId=<%=tieuhaoBean.getId() %>" >
        	<img src="../images/Printer30.png" alt="In"  title="In" border="1" longdesc="In" style="border-style:outset"></A>  -->
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
                    			value="<%= tieuhaoBean.getSoMuaHang() %>"  readonly /></TD>
				</TR>
                
				   <TR>
                    <TD   class="plainlabel" valign="top">Ngày ghi nhận NV</TD>
                    <TD    align="left" class="plainlabel" valign="top">
                    	<input type="text"  class="days" id="ngaytieuhao" onchange="Reload_Lo()"  name="ngaytieuhao" value="<%=tieuhaoBean.getNgaytieuhao()   %>" 
                    		maxlength="10" readonly />
                    </TD>
                    </TR>
                    
                     <TR>
                    <TD   class="plainlabel" valign="top">Ngày thực hiện</TD>
                    <TD    align="left" class="plainlabel" valign="top">
                    	<input type="text"  class="days" id="ngaychot"  name="ngaychot" value="<%=tieuhaoBean.getNgaychot()   %>" 
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
                    			value="<%= tieuhaoBean.getSoLuong() %>"  maxlength="10" readonly /></TD>
				</TR>
				
				<TR>
                    <TD width="150px" class="plainlabel" valign="top">Ghi chú</TD>
                    <TD class="plainlabel" valign="top">
                    	<input type="text" id="ghichu" name="ghichu" 
                    			value="<%= tieuhaoBean.getGhichu() %>"   /></TD>
				</TR>
                
            </TABLE>
            <hr> 
		</div>
           
        <div align="left" style="width:100%; float:none; clear:none;" class="plainlabel">
            <TABLE class="tabledetail" width="100%" border="0" cellpadding="1" cellspacing="1" >
                <TR class="tbheader"> 
                	<TH align="center" width="3%">STT</TH>
               		<TH align="center" width="15%">Mã vật tư</TH>
                	<TH align="center" width="38%">Tên vật tư</TH>
                	<TH align="center" width="10%">SL đã tiêu hao</TH>
                	<TH align="center" width="10%">SL xuất </TH>
                	<TH align="center" width="10%">SL định mức BOM</TH>
                	
               	 	<TH align="center" width="10%" style="display:none;">SL định mức tiêu hao </TH>
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
           	 					<input type="text" style="width: 100%; text-align: right;" value="<%= sp.getSoLuongDaTieuHao() %>" name= "soluongdatieuhao" readonly="readonly" >
           	 				</td>
           	 				<td align="right">
           	 					<input type="text" style="width: 100%; text-align: right;" value="<%=formater_3le.format( sp.getsoluongXuat()) %>" name= "soluongxuat" readonly="readonly" >
           	 				</td>
           	 				
           	 				<td align="right">
           	 					<input type="text" style="width: 100%; text-align: right;" value="<%= formater_3le.format(sp.getSoLuongChuan()) %>" name= "vattuSoLuongChuan" readonly="readonly" >
           	 				</td>
           	 				<td align="right" style="display:none;">
           	 					<input type="text" style="width: 100%; text-align: right;" value="<%=formater_3le.format( sp.getsoluongDMTieuhao() )%>" name= "vattuSoLuongdmth" readonly="readonly" >
           	 				</td>
           	 				
           	 				<td align="right">
           	 					<input type="text" style="width: 100%; text-align: right;" value="<%=formater_3le.format(sp.getSoLuongThucTe()+0.0000001) %>" name= "vattuSoLuongThucTe" id = "<%=sp.getMa()+".soluongTong" %>" onkeypress="return keypress(event, this);" >
           	 				</td>
           	 				<td align="center">
							  	<a href="" id="<%= sp.getMa() %>pobup" rel="subcontent<%= i %>">
	           	 							<img alt="Số lô - Vị trí hàng hóa xuất" src="../images/vitriluu.png"></a>
	           	 				<DIV id="subcontent<%= i %>" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
				                             background-color: white; width: 800px; padding: 1px;">
				                    <table width="100%" align="center">
				                        <tr>
				                         <th width="5%">Vị trí</th>
				                         <th width="8%">MARQUETTE</th>
				                          <th width="10%">Số lô</th>
				                           <th width="8%">Ngày hết hạn</th>
				                            <th width="8%">Ngày nhập kho </th>
				                         <th width="5%">Mã thùng</th>
				                         <th width="5%">Mã mẻ</th>
				                         <th width="10%">Mã phiếu</th>
				                         <th width="10%">Phiếu EO</th>
				                         <th width="10%">Phiếu định tính</th>
					                     <th width="5%">Hàm ẩm</th>
				                         <th width="5%">Hàm lượng</th>
					                    
					                     <th width="8%">Số lượng tồn</th>
					                     <th width="8%">Số lượng tiêu hao</th>
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
							                           		 	<input type="text" style="width: 100%" name="<%=  sp.getMa() + ".vitri" %>" value="<%= spDetail.getVitri() %>" readonly="readonly" />
							                           		 	<input type="hidden" style="width: 100%" name="<%=  sp.getMa() + ".vitriid" %>" value="<%= spDetail.getVitriId() %>" readonly="readonly" />
							                           		  
							                           		 </td>
							                           		 <td>
							                           		 	<input type="text" style="width: 100%" name="<%=  sp.getMa() + ".MARQ" %>" value="<%= spDetail.getMARQ() %>" readonly="readonly" />
							                           		 	<input type="hidden" style="width: 100%" name="<%=  sp.getMa() + ".MARQid" %>" value="<%= spDetail.getIDMARQUETTE() %>" readonly="readonly" />
							                           		 </td>
							                           		  <td>
							                            		<input type="text" style="width: 100%" name="<%=  sp.getMa() + ".solo" %>" value="<%= spDetail.getsolo() %>" readonly="readonly" /></td>
							                            	 
							                            	 <td>
							                            		<input type="text" style="width: 100%" name="<%=  sp.getMa() + ".ngayhethan" %>" value="<%= spDetail.getNgayhethan() %>" readonly="readonly" /></td>
							                            		<td>
							                            		<input type="text" style="width: 100%" name="<%=  sp.getMa() + ".ngaynhapkho" %>" value="<%= spDetail.getNgaynhapkho() %>" readonly="readonly" /></td>
							                            		 
							                            	 <td>
							                            		<input type="text" style="width: 100%" name="<%=  sp.getMa() + ".mathung" %>" value="<%= spDetail.getMathung() %>" readonly="readonly" /></td>
							                            
							                            	 
							                            	 <td>
							                            		<input type="text" style="width: 100%" name="<%=  sp.getMa() + ".mame" %>" value="<%= spDetail.getMame() %>" readonly="readonly" /></td>
							                             
							                            	 <td>
							                            	<input type="text" style="width: 100%" name="<%=  sp.getMa() + ".maphieu" %>" value="<%= spDetail.getMaphieu() %>" readonly="readonly" /></td>
							                             
							                            	 <td>
							                            	<input type="text" style="width: 100%" name="<%=  sp.getMa() + ".phieueo" %>" value="<%= spDetail.getPHIEUEO() %>" readonly="readonly" /></td>
							                            	<td>
							                            	 
							                            	<input type="text" style="width: 100%" name="<%=  sp.getMa() + ".maphieudinhtinh" %>" value="<%= spDetail.getMAPHIEUDINHTINH() %>" readonly="readonly" />
							                            	</td>
							                            	<td>
							                            	 
							                            	<input type="text" style="width: 100%" name="<%=  sp.getMa() + ".hamam" %>" value="<%= spDetail.getHamam() %>" readonly="readonly" />
							                            	</td>
							                            	<td>
							                            	 
							                            	<input type="text" style="width: 100%" name="<%=  sp.getMa() + ".hamluong" %>" value="<%= spDetail.getHamluong() %>" readonly="readonly" />
							                            	</td>
							                            	
							                            	<td>							                            	
							                            		<input type="text" style="width: 100%"  name="<%=  sp.getMa() + ".soluongton" %>" value="<%= spDetail.getSoLuongton() %>" readonly="readonly" />
							                            		</td>
							                           		 <td>
							                            	<input type="text" style="width: 100%"  name="<%=  sp.getMa()+ ".soluong" %>" value="<%= spDetail.getSoLuong() %>" onchange="tinhSoLuong('<%=sp.getMa() %>')"  />
							                            	</td>
 
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
           	 	 
            </TABLE> 
        	</div>      
     
     </fieldset>	
    </div>
</div>
</form>

 
<script type="text/javascript">

	replaces();

	<% 
	for(int i = 0; i < spList.size(); i++)
		{
		ISanpham sp = spList.get(i);
	%>
		dropdowncontent.init('<%=sp.getMa()+ "pobup"%>', "left-top", 600, "click");
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