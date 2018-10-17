<%@page import="geso.traphaco.center.util.Utility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.SQLException"%>
<%@ page  import = "geso.traphaco.distributor.beans.xuatkho.*" %>
<%@ page  import = "geso.traphaco.distributor.beans.xuatkho.imp.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.util.List" %>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.text.DecimalFormat"%>

<% IErpXuathoadonKM lsxBean = (IErpXuathoadonKM)session.getAttribute("lsxBean"); %>
<% ResultSet nppRs = lsxBean.getKhRs(); %>
<% ResultSet khonhapRs = lsxBean.getKhoNhapRs(); %>
<% ResultSet ddhRs = lsxBean.getDondathangRs(); %>
<% Hashtable<String, String> sanpham_soluong = lsxBean.getSanpham_Soluong(); %>

<% ResultSet tinhthanhRs = lsxBean.getTinhthanhRs(); %>
<% ResultSet quanhuyenRs = lsxBean.getQuanhuyenRs(); %>
<% ResultSet nvbhRs = lsxBean.getNVBHRs(); %>
<% ResultSet nvgnRs = lsxBean.getNVGNRs(); %>

<% 
	String[] spId = lsxBean.getSpId();
	String[] spMa = lsxBean.getSpMa(); 
	String[] spTen = lsxBean.getSpTen();
	String[] spDonvi = lsxBean.getSpDonvi();
	String[] spSoluongdat = lsxBean.getSpSoluongDat();
	String[] spTonkho = lsxBean.getSpTonKho();
	String[] spDaxuat = lsxBean.getSpDaXuat();
	String[] spSoluong = lsxBean.getSpSoluong();
	String[] spLoai = lsxBean.getSpLoai();
	String[] spSCheme = lsxBean.getSpScheme();
	NumberFormat formater = new DecimalFormat("##,###,###");
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
	
	function replaces()
	{
		var spHansd = document.getElementsByName("spHansd");
		var spMa = document.getElementsByName("spMa");
		var spTen = document.getElementsByName("spTen");  
		var donvi = document.getElementsByName("donvi");
		var soluong = document.getElementsByName("soluong");
		var dongia = document.getElementsByName("dongia");
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
					
					dongia.item(i).value = DinhDangTien(sp.substring(0, parseInt(sp.indexOf("]")))); 
				}
			}
			else
			{
				spMa.item(i).value = "";
				spTen.item(i).value = "";
				donvi.item(i).value = "";
				soluong.item(i).value = "";
				dongia.item(i).value = "";
				thanhtien.item(i).value = "";		
			}
		}	
		
		TinhTien();
		setTimeout(replaces, 300);
	}
	
	
	 function TinhTien()
	 {
		var spMa = document.getElementsByName("spMa");
		var soluong = document.getElementsByName("soluong");
		var dongia = document.getElementsByName("dongia");
		var thanhtien = document.getElementsByName("thanhtien");
		
		var tongtien = 0;
		for(var i = 0; i < spMa.length; i++)
		{
			if(spMa.item(i).value != "" && dongia.item(i).value != "" && soluong.item(i).value != "" )
			{
				var tt = parseFloat(dongia.item(i).value.replace(/,/g,"")) * parseFloat(soluong.item(i).value.replace(/,/g,""));
				thanhtien.item(i).value = DinhDangTien(tt);
				
				tongtien += tt;
			}
		}
		
		var chietkhau = document.getElementById("txtPTChietKhau").value;
		if(chietkhau == '')
			chietkhau = '0';
		
		var vat = document.getElementById("txtVAT").value;
		if(vat == '')
			vat = '0';
		
		var tongtienCK = parseFloat(chietkhau) * parseFloat(tongtien) / 100;
		var tongtienSAUCK = parseFloat(tongtien) - parseFloat(tongtienCK);
		
		document.getElementById("txtTongCK").value = DinhDangTien(tongtienCK);
		document.getElementById("txtBVAT").value = DinhDangTien(tongtienSAUCK);
		document.getElementById("txtTongSauCK").value = DinhDangTien(tongtienSAUCK);
		
		var tongtienSAUVAT = parseFloat(tongtienSAUCK) + ( parseFloat(vat) * parseFloat(tongtienSAUCK) / 100 );
		document.getElementById("txtSauVAT").value = DinhDangTien(tongtienSAUVAT);
		
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
	
	function Update_SoLuong()
	{
		var spTen = document.getElementsByName("spTen");
		var soluong = document.getElementsByName("soluong");
		var soluong2 = document.getElementsByName("soluong2");
		
		var soluongDAT = document.getElementsByName("soluongDAT");
		var soluongDANHAN = document.getElementsByName("daxuat");
		
		for(var i = 0; i < soluong.length; i++ )
		{
			var toida = parseFloat(soluongDAT.item(i).value.replace(/,/g,"")) - parseFloat(soluongDANHAN.item(i).value.replace(/,/g,""));
			
			if( parseFloat(soluong.item(i).value.replace(/,/g,"")) > toida )
			{
				soluong.item(i).value = DinhDangTien(toida);
				soluong2.item(i).value = soluong.item(i).value;
				
				alert('Số lượng xuất của sản phẩm (' +  spTen.item(i).value + ') không được phép vượt quá tổng đặt và đã xuất ( ' + toida + ' )');
			}
			
			soluong2.item(i).value = soluong.item(i).value;
		}
		
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
<form name="hctmhForm" method="post" action="../../ErpXuathoadonKMUpdateSvl">
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="nppId" value='<%= lsxBean.getNppId() %>'>
<input type="hidden" name="id" value='<%= lsxBean.getId() %>'>

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:60%; padding:5px; float:left" class="tbnavigation">
        	Quản lý bán hàng > Báo cáo đại lý > Xuất hóa đơn KM > Hiển thị
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn <%= userTen %> &nbsp;
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<A href= "../../ErpXuathoadonKMSvl?userId=<%= userId %>" >
        	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1px" longdesc="Quay ve" style="border-style:outset"></A>
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
    	<legend class="legendtitle">Sổ xuất hàng </legend>
        	<div style="float:none; width:100%" align="left">
            <TABLE width="100%" cellpadding="4" cellspacing="0">							
                <TR>
                    <TD class="plainlabel" >Ngày hóa đơn </TD>
                    <TD class="plainlabel" colspan="3" >
                    	<input type="text" readonly="readonly"  name="ngaychuyen" value="<%= lsxBean.getNgayyeucau() %>"/>
                    </TD>
                    
                </TR>
                
                <TR>
                    <TD width="130px" class="plainlabel" >Từ ngày </TD>
                    <TD width="230px" class="plainlabel" >
                    	<input type="text" class="days" readonly="readonly"  name="tungay" value="<%= lsxBean.getTungay() %>" onchange="LocLaiPO();"  />
                    </TD>
                    <TD width="130px" class="plainlabel" >Đến ngày </TD>
                    <TD class="plainlabel" >
                    	<input type="text" class="days" readonly="readonly"  name="denngay" value="<%= lsxBean.getDenngay() %>" onchange="LocLaiPO();" />
                    </TD>
                    
                </TR>
                
                 <TR>
                    <TD width="130px" class="plainlabel" >Ký hiệu </TD>
                    <TD width="230px" class="plainlabel" >
                    	<input type="text" name="kyhieu" value="<%= lsxBean.getKyhieu() %>"  />
                    </TD>
                    <TD width="130px" class="plainlabel" >Số hóa đơn </TD>
                    <TD class="plainlabel" >
                    	<input type="text" name="sohoadon" value="<%= lsxBean.getSohoadon() %>" readonly="readonly"  />
                    </TD>
                    
                </TR>                
                
                <TR>
                	<TD class="plainlabel" >Nhà phân phối </TD>
                    <TD class="plainlabel" colspan="3" >
                    	<select name = "khId" class="select2" style="width: 700px" onchange="LocLaiPO();" >
                    		<option value=""> </option>
                        	<%
                        		if(nppRs != null)
                        		{
                        			try
                        			{
                        			while(nppRs.next())
                        			{  
                        			if( nppRs.getString("pk_seq").equals(lsxBean.getKhId())){ %>
                        				<option value="<%= nppRs.getString("pk_seq") %>" selected="selected" ><%= nppRs.getString("ten") %></option>
                        			<%}else { %>
                        				<option value="<%= nppRs.getString("pk_seq") %>" ><%= nppRs.getString("ten") %></option>
                        		 <% } } nppRs.close();} catch(SQLException ex){}
                        		}
                        	%>
                    	</select>
                    </TD>      	
                </TR>
                <TR>
                	<TD class="plainlabel" >Ghi chú </TD>
                    <TD class="plainlabel" colspan="3" >
                    	<input type="text"  name="ghichu" value="<%= lsxBean.getGhichu() %>" style="width: 700px;" />
                    </TD>
                </TR>
                <TR>
                	<TD class="plainlabel" >Scheme </TD>
                    <TD class="plainlabel" colspan="3" >
                    	
                    		<table style="width: 100%" cellpadding="1" cellspacing="1" >
                    			<TR class="tbheader">
                    				<TH align="center" style="width: 25%" >Scheme</TH>
				                    <TH align="center" style="width: 45%" >Diễn giải</TH>
				                    <TH align="center" style="width: 10%" >Từ ngày</TH>
				                    <TH align="center" style="width: 10%" >Đến ngày</TH>
				                    <TH align="center" style="width: 10%" >Chọn</TH>
				                </TR>
				             </table>
				             
				             <div style="width: 100%; max-height: 150px; overflow: auto;" >
					             <table style="width: 100%" cellpadding="1" cellspacing="1"  >
					                <% if( ddhRs != null ) { 
					                	while( ddhRs.next() ) { %>
					                		
					                		<tr>
					                			<td style="width: 25%"><input type="text" value="<%= ddhRs.getString("SCHEME") %>" disabled="disabled" style="width: 100%;" ></td>
					                			<td style="width: 45%"><input type="text" value="<%= ddhRs.getString("DIENGIAI") %>" disabled="disabled" style="width: 100%;" ></td>
					                			<td style="width: 10%"><input type="text" value="<%= ddhRs.getString("TUNGAY") %>" disabled="disabled" style="width: 100%;" ></td>
					                			<td style="width: 10%"><input type="text" value="<%= ddhRs.getString("DENNGAY") %>" disabled="disabled" style="width: 100%;" ></td>
					                			<td style="width: 10%" align="center" >
					                				<% if( lsxBean.getDondathangId().contains(ddhRs.getString("pk_seq")) ) { %>
					                					<input type="checkbox" name = "ddhIds" value="<%= ddhRs.getString("PK_SEQ") %>"  onchange="changePOform();" checked="checked" >
					                				<% } else { %>
					                					<input type="checkbox" name = "ddhIds" value="<%= ddhRs.getString("PK_SEQ") %>"  onchange="changePOform();" >
					                				<% } %> 
					                			</td>
					                		</tr>
					                		
					                	<% }
					                	ddhRs.close();
					                } %>
					                
	                    		</table>
                    		</div>
                    </TD>   
                </TR>
                
            </TABLE>
			<hr />
			
			<table cellpadding="0px" cellspacing="1px" width="100%">
				<tr class="tbheader">
					<th align="center" width="15%" >Mã sản phẩm</th>
					<th align="center" width="40%" >Tên sản phẩm</th>
					<th align="center" width="10%" >Đơn vị</th>
					<th align="center" width="15%" >Tổng số lượng</th>
					<th align="center" width="10%" >Đã xuất</th>
					<th align="center" width="10%" >Số lượng xuất</th>
				</tr>
				
				<%
					int count = 0;
					if(spMa != null)
					{
						for(int i = 0; i < spMa.length; i++)
						{
							String style = ""; %>
					
						<tr <%= style %> >
							<td >
								<input type="hidden" name="spId" value="<%= spId[i] %>" style="width: 100%"   > 
								<input type="text" name="spMa" value="<%= spMa[i] %>" style="width: 100%"  readonly="readonly"  > 
							</td>
							<td> <input type="text" name="spTen" value="<%= spTen[i] %>" style="width: 100%" readonly="readonly"> </td>
							<td>
								<input type="text" name="donvi" value="<%= spDonvi[i] %>" style="width: 100%; text-align: center;" readonly="readonly">							
							</td>
							<td>
								<input type="text" name="soluongDAT" value="<%= spSoluongdat[i] %>" style="width: 100%; text-align: right;" readonly="readonly">							
							</td>
							<td>
								<input type="text" name="daxuat" value="<%= spDaxuat[i] %>" style="width: 100%; text-align: right;" readonly="readonly">							
							</td>
							<td>
								<input type="text" name="soluong" value="<%= spSoluong[i] %>" style="width: 100%; text-align: right;" onkeypress="return keypress(event); " onkeyup="Dinhdang(this);"  >							
							</td>
						</tr>	
							
					<% count ++; } } %>
				
			</table>
				
            </div>
     </fieldset>	
    </div>
</div>

<script type="text/javascript">
	//replaces();
</script>

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