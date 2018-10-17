<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="geso.traphaco.erp.beans.chiphinhapkhau.imp.*" %>
<%@ page import="geso.traphaco.erp.beans.chiphinhapkhau.*" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@page import="java.sql.SQLException" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
<%
 	IErpChiphinhapkhau obj =(IErpChiphinhapkhau)session.getAttribute("cpnkBean");
	ResultSet pnkList = (ResultSet)obj.getPhieunhapRs();
	ResultSet nccList = (ResultSet)obj.getNccRs();
	ResultSet tienteList = (ResultSet)obj.getTienteRs();
	List<Erp_Item> sanPhamKhoList = obj.getSanPhamKhoList();
	String[] diengiai = obj.getDiengiai();
	String[] maHD = obj.getMaHD();
	String[] mausoHD = obj.getMausoHD();
	String[] kyhieuct = obj.getKyhieu();
	String[] sochungtu = obj.getSochungtu();
	String[] ngaychungtu = obj.getNgaychungtu();
	String[] nhacungcap = obj.getNhacungcap();
	String[] mst = obj.getMst();
	String[] tienhang = obj.getTienhang();
	String[] thuesuat = obj.getThuesuat();
	String[] tienthue = obj.getTienthue();
	String[] tongtien = obj.getTongtien();
	String[] diaChiNCC = obj.getDiaChiNCC();
	List<ISanPhamPhanBo> spList = obj.getSpList();
	NumberFormat formatter = new DecimalFormat("#,###,###");
	NumberFormat formatter1 = new DecimalFormat("#,###,###,###.###");
	NumberFormat formatter2=new DecimalFormat("#,###,###.##"); 
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Traphaco - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<script type="text/javascript"	src="../scripts/jquery.min.1.7.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>

<script type="text/javascript" src="../scripts/ajax.js"></script>
<script type="text/javascript" src="../scripts/erp-spList.js"></script>

<script type="text/javascript" src="../scripts/speechbubbles.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>

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
	font-size: 1.2em;
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
.hoaDonTable {
	width: 100%;

	min-height: 200px;
    float: none;
  	margin-top: 5px;
    padding-bottom: 1px;
}

</style>

<SCRIPT language="JavaScript" type="text/javascript">
	function submitform()
	{
	    document.forms["cpnk"].submit();
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
		var ngaynhap = document.getElementsByName("ngaynhap").item(0).value;
		var ncc = document.getElementById("nccId").value;
		var ncc_cn = document.getElementById("nccId_cn").value;
		if(ngaynhap.length == 0)
		  {
			
	
		  document.forms["cpnk"].dataerror.value = "Vui lòng nhập ngày nhận";
		  return;
		  }
	  
	 /*  if(ncc.length == 0)
		  {
		  	document.forms["cpnk"].dataerror.value = "Vui lòng chọn nhà cung cấp nhận";
		  	console.log('loi nhap');
			alert('Vui long nhap nha cung cap nhan');
		  	return;
		  } */
	  
	  if(ncc_cn.length == 0)
	  {
	 	 document.forms["cpnk"].dataerror.value = "Vui lòng chọn nhà cung cấp ghi công nợ";
	 	 return;
	  }
	  document.forms["cpnk"].action.value = "save";
	  document.forms["cpnk"].submit(); 
    }
	
	function replaces()
	{
		var ncc = document.getElementsByName("ncc");
		var nccId = document.getElementsByName("nccId");
		var nccId_old = nccId[0].value;
		var nccId_new = ncc[0].value.substring(0, parseInt(ncc[0].value.indexOf(" - ")));
			
		if(nccId_old != nccId_new){
			nccId[0].value = ncc[0].value.substring(0, parseInt(ncc[0].value.indexOf(" - ")));
			document.forms["cpnk"].submit();
		}
		
		var ncc_cn = document.getElementsByName("ncc_cn");
		var nccId_cn = document.getElementsByName("nccId_cn");
		var nccId_cn_old = nccId_cn[0].value;
		var nccId_cn_new = ncc_cn[0].value.substring(0, parseInt(ncc_cn[0].value.indexOf(" - ")));
			
		if(nccId_cn_old != nccId_cn_new){
			nccId_cn[0].value = ncc_cn[0].value.substring(0, parseInt(ncc_cn[0].value.indexOf(" - ")));
			document.forms["cpnk"].submit();
		}

		var tienhang = document.getElementsByName("tienhang");
		var thuesuat = document.getElementsByName("thuesuat");	
		var tienthue = document.getElementsByName("tienthue");	
		var cong = document.getElementsByName("cong");	
		
		
		for(var i = 0; i < tienhang.length; i++)
		{
			if(tienhang.item(i).value != '' )
			{
				var TH = tienhang.item(i).value.replace(/,/g,"");
				
				tienhang.item(i).value = DinhDangTien(TH);
					
				var thue = 0;
				var ttThue = 0 ;					
					thue = thuesuat.item(i).value.replace(/,/g,"");																	 	
					ttThue = parseFloat(thue) * parseFloat(TH) / 100 ;
					tienthue.item(i).value = DinhDangTien(ttThue);
				
				
				var temp = parseFloat(TH) + parseFloat(ttThue);
				
				cong.item(i).value = DinhDangTien(temp);
			}
			else
			{
				tienthue.item(i).value = "";
				cong.item(i).value = "";
			}
		}
		
		//setTimeout(replaces, 300);
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
	
	function DinhDangDonGia(num) 
	{
		num = num.toString().replace(/\$|\,/g,'');
			
		//num = (Math.round( num * 1000 ) / 1000).toString();
			
		var sole = '';
		if(num.indexOf(".") >= 0)
		{
			sole = num.substring(num.indexOf('.'));
		}
			
		if(isNaN(num))
			num = "0";
		sign = (num == (num = Math.abs(num)));
		num = Math.floor(num*100);
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
	
	function Tinhtienthue()
	{
		var tienhang = document.getElementsByName("tienhang");
		var thuesuat = document.getElementsByName("thuesuat");	
		var tienthue = document.getElementsByName("tienthue");	
		var cong = document.getElementsByName("cong");
		
		var tongtienhang = document.getElementsByName("tongtienhang");
		var tongtienthue = document.getElementsByName("tongtienthue");	
		var tongtien = document.getElementsByName("tongtien");
		
		var total_tienhang = 0;
		var total_tienthue = 0;
		var total_tongtien = 0; // tien hang + thue
		
		for(var i = 0; i < tienhang.length; i++)
		{
			if(tienhang.item(i).value != '' )
			{
				var TH = tienhang.item(i).value.replace(/,/g,"");
				
				tienhang.item(i).value = DinhDangTien(TH);
					
				var thue = 0;
				var ttThue = 0 ;	
				
				if(thuesuat.item(i).value != '')
					thue = thuesuat.item(i).value.replace(/,/g,"");	
				
				ttThue = Math.round(parseFloat(thue) * parseFloat(TH) / 100) ;
				tienthue.item(i).value = DinhDangTien(ttThue);
				
				
				var temp = parseFloat(TH) + parseFloat(ttThue);
				
				cong.item(i).value = DinhDangTien(temp);
				
				total_tienhang += parseFloat(TH);
				total_tienthue += parseFloat(ttThue);
				total_tongtien += temp;
			}
			else
			{
				tienthue.item(i).value = "";
				cong.item(i).value = "";
			}
		}
		
		tongtienhang.item(0).value = DinhDangTien(total_tienhang);
		tongtienthue.item(0).value = DinhDangTien(total_tienthue);
		tongtien.item(0).value = DinhDangTien(total_tongtien);
		
		tinhPhanBo();
		
	} 
	function tinhPhanBo(){
		var phanTramSanPham = document.getElementsByName("phantramSanPham");
		var phanboSanPham = document.getElementsByName("phanboSanPham");
		var tongtienhang = document.getElementById("tongtienhang").value.replace(/,/g,"");
		var tongTruoc = 0;
		for( var i = 0; i < phanTramSanPham.length; i++){
			if(phanTramSanPham.item(i).value != ''){
				if (i == (phanTramSanPham.length - 1)){
					
					
					var phanbo = tongtienhang - tongTruoc;
					phanboSanPham.item(i).value = DinhDangTien(phanbo);
					
				}else{
					
					
					var phantram = phanTramSanPham.item(i).value.replace(/,/g,"");
					var phanbo = parseFloat(phantram) * tongtienhang / 100;
					phanboSanPham.item(i).value = DinhDangTien(phanbo);
					tongTruoc = tongTruoc +  parseFloat(DinhDangTien(phanbo).replace(/,/g,""));
					
				}
				
			}
		}
	}
	function Tinhthuesuat()
	{
		var tienhang = document.getElementsByName("tienhang");
		var thuesuat = document.getElementsByName("thuesuat");	
		var tienthue = document.getElementsByName("tienthue");	
		var cong = document.getElementsByName("cong");
		
		var tongtienhang = document.getElementsByName("tongtienhang");
		var tongtienthue = document.getElementsByName("tongtienthue");	
		var tongtien = document.getElementsByName("tongtien");
		
		var total_tienhang = 0;
		var total_tienthue = 0;
		var total_tongtien = 0; // tien hang + thue
				
		
		for(var i = 0; i < tienhang.length; i++)
		{
			if(tienhang.item(i).value != '' )
			{
				var TH = tienhang.item(i).value.replace(/,/g,"");
				
				tienhang.item(i).value = DinhDangTien(TH);
					
				var thue = 0;
				var ttThue = 0 ;	
				
				if(tienthue.item(i).value != '')
					ttThue = tienthue.item(i).value.replace(/,/g,"");	
				
				thue = parseFloat(ttThue) / parseFloat(TH) * 100 ;
				thuesuat.item(i).value = DinhDangDonGia(thue.toFixed(3));
				tienthue.item(i).value = DinhDangTien(ttThue);
				
				
				var temp = parseFloat(TH) + parseFloat(ttThue);
				
				cong.item(i).value = DinhDangTien(temp);
				
				total_tienhang += parseFloat(TH);
				total_tienthue += parseFloat(ttThue);
				total_tongtien += temp;
			}
			else
			{
				tienthue.item(i).value = "";
				cong.item(i).value = "";
			}
		}
		
		tongtienhang.item(0).value = DinhDangTien(total_tienhang);
		tongtienthue.item(0).value = DinhDangTien(total_tienthue);
		tongtien.item(0).value = DinhDangTien(total_tongtien);
		
	}
	
</SCRIPT>

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { $("select").select2();  });
</script>

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name ="cpnk" method="post" action="../../ErpChiphinhapkhauUpdateSvl" >
<input type="hidden" name="userId" value='<%= userId %>' >
<input type="hidden" name="action" value="0">
<input type="hidden" name="id" value="<%= obj.getId() %>">
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý mua hàng > Nghiệp vụ khác > Chi phí nhận hàng > Hiển thị</TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %></TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<TR ><TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
							<TD width="30" align="left"><A href="../../ErpChiphinhapkhauSvl?userId=<%= userId%>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
						    <TD width="2" align="left" ></TD>
						    <TD width="30" align="left" >
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
				
	    				<textarea name="dataerror"  id ="dataerror"style="width: 100% ; color:#F00 ; font-weight:bold"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1"><%=obj.getMsg()%></textarea>
						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Chi phí nhận hàng </LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
                          
                          <TR>
                          		<TD class="plainlabel" valign="middle" width="20%" >Ngày nhập</TD>   
		                        <TD class="plainlabel" valign="middle"  >
		                        	<input type="text" name="ngaynhap" value="<%= obj.getNgaynhap() %>" class="days" readonly="readonly"> 
		                        </TD>   
		                        <TD class="plainlabel"></TD>
								<TD class="plainlabel"></TD>       
		                  </TR> 
                          <TR>
								<TD class="plainlabel" valign="top" width="150px">Số chứng từ </TD>
								<TD class="plainlabel" valign="top" colspan="5">
									<input type="text" id="soChungTu_Chu" name="soChungTu_Chu" value="<%= obj.getSoChungTu_Chu() %>" maxlength="10" style="border-radius:4px; height: 20px; width: 100px;" readonly="readonly"/>
									<input type="text" id="soChungTu_So" name="soChungTu_So" value="<%= obj.getSoChungTu_So() %>" maxlength="10" style="border-radius:4px; height: 20px; width: 100px;" readonly="readonly"/>
								</TD>
                          </TR>	
                    	  <TR>
                        		<TD class="plainlabel" valign="middle" width = "20%">Nhà cung cấp ghi công nợ</TD>
                        		<TD class="plainlabel" valign="middle">
                    			<select name="nccId_cn" id="nccId_cn" style = "width:400px" >
                        			<option value="" selected="selected" ></option>
            	            	<%
                	        		if(nccList != null)
                    	    		{
                        				try
                        				{
                        					nccList.beforeFirst();
                        					while(nccList.next())
                        					{  
                        						if(nccList.getString("pk_seq").equals(obj.getNccId_cn())){ %>
                        						<option value="<%= nccList.getString("pk_seq") %>" selected="selected" ><%= nccList.getString("nccTen")%></option>
                        					<%	}else { %>
                        						<option value="<%= nccList.getString("pk_seq") %>" ><%= nccList.getString("nccTen") %></option>
                        		 			<%  } 
                        					} 
                        					//nccList.close();
                        				} catch(SQLException ex){
                        					ex.printStackTrace();
                        				}
                        		}
                        	%>
                      			  </select>
                        		
                        		</TD>  
                        		<TD class="plainlabel"></TD>
								<TD class="plainlabel"></TD>                      
                    	  </TR>

                    	  <TR>
                        		<TD class="plainlabel" valign="middle" >Nhà cung cấp nhận</TD>
                        		<TD class="plainlabel" valign="middle">
                   				<select name="nccId" id="nccId" style = "width:500px" >
                        			<option value="" selected="selected" ></option>
            	            	<%
                	        		if(nccList != null)
                    	    		{
                        				try
                        				{
                        					nccList.beforeFirst();
                        					while(nccList.next())
                        					{  
                        						if(nccList.getString("pk_seq").equals(obj.getNccId())){ %>
                        						<option value="<%= nccList.getString("pk_seq") %>" selected="selected" ><%= nccList.getString("nccTen")%></option>
                        					<%	}else { %>
                        						<option value="<%= nccList.getString("pk_seq") %>" ><%= nccList.getString("nccTen") %></option>
                        		 			<%  } 
                        					} 
                        					nccList.close();
                        				} catch(SQLException ex){}
                        		}
                        	%>
                      			  </select>                        		
                         		</TD>
                         		<TD class="plainlabel"></TD>
								<TD class="plainlabel"></TD>                        
                    	  </TR>
     
		                  <TR>
		                  	<TD class="plainlabel" valign="middle">Phiếu nhận hàng </TD>
                    		<TD class="plainlabel" valign="middle" width = "20%">
                    	
			                        <select name="pnkIds" id="pnkIds" multiple style = "width:400px" >
                        		<option value="" ></option>
            	            	<%
                	        		if(pnkList != null)
                    	    		{
                        				try
                        				{
                        					System.out.println("I am here 1!");
                        					while(pnkList.next())
                        					{  
                        						if( (obj.getPnkIds()).indexOf(pnkList.getString("pnkId")) >= 0){ %>
                        						<option value="<%= pnkList.getString("pnkId") %>" selected="selected" ><%= pnkList.getString("pnk")%></option>
                        					<%	}else { %>
                        						<option value="<%= pnkList.getString("pnkId") %>" ><%= pnkList.getString("pnk") %></option>
                        		 			<%  } 
                        					} 
                        					pnkList.close();
                        				} catch(SQLException ex){
                        					ex.printStackTrace();
                        				}
	                        		}
	                        	%>
                      			  </select>
                     		</TD>
	                      	<!-- 	Start Sản phẩm kho -->
	                      	<TD class="plainlabel" valign="middle" width="10%">Sản phẩm </TD>
	                      	<% if(obj.getPnkIds().trim().length() > 0){ %>
											<td class="plainlabel" valign="top"  width="60%">
				           	 					<a href="" id="sanPhamKho_" rel="subcontentSanPhamKho_">
					           	 				       <img alt="Tên sản phẩm hóa đơn" src="../images/vitriluu.png"></a>
					           	 				<DIV  id="subcontentSanPhamKho_"  style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
								                             background-color: white; width: 600px; padding: 4px;  max-height: 200px; overflow: auto;" >
								                              
								                     <table  align="center"  class = "legendtitle"  >
								                        <tr>
								                            <th width="50px">Mã</th>
								                            <th width="100px">Tên</th>
								                            <th width="50px">Số lô</th>
								                           	<th width="70px">Tiền</th>
								                            <th width="30px">Phần trăm</th>
								                            <th width="70px">Phân bổ</th>
								                        </tr>
							                        	<%	 	for(int i =0; i< spList.size(); i++)
							                        			{  %>
							                        			<tr>
							                        				<td><input style="width: 100px" name="masp" id="masp" value="<%= spList.get(i).getMaLon() %>" readonly="readonly"  >
							                        					<input type = "hidden" style="width: 100px" name="nhanhangId" id="nhanhangId" value="<%= spList.get(i).getManhanHang() %>"   >
							                        					<input type = "hidden" style="width: 100px" name="loaisp" id="loaisp" value="<%= spList.get(i).getLoai() %>"   >
							                        					
							                        				</td>
							                        				<td><input style="width: 150px" name="tenSanPham" id="tenSanPham" value="<%= spList.get(i).getTenSp() %>" readonly="readonly"  ></td>
							                        				<td><input style="width: 90px" name="soloSanPham" id="soloSanPham" value="<%= spList.get(i).getSoLo() %>" readonly="readonly"  ></td>
							                        				<td ><input style="width: 100%; text-align: right;" name="tienSanPham" id="tienSanPham" value="<%= formatter.format(spList.get(i).getTien()) %>" readonly="readonly"  ></td>
							                        				<td ><input style="width: 60px; text-align: right;" name="phantramSanPham" id="phantramSanPham" value="<%= formatter2.format(spList.get(i).getPhanTram()) %>" readonly="readonly"  ></td>
							                        				<td><input style="width: 100%; text-align: right;"  name="phanboSanPham" id="phanboSanPham" value="<%= formatter.format(spList.get(i).getPhanBo()) %>" readonly="readonly"  ></td>
							                        			</tr>
							                        			
							                        		 <% } %>
								                    </table>
								                     
								                     <div align="right">
								                     	<label style="color: red" ></label>
								                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								                     	<a href="javascript:dropdowncontent.hidediv('subcontentSanPhamKho_')">Hoàn tất</a>
								                     </div>
								                </DIV>
								                   <script type="text/javascript">
						            					dropdowncontent.init('sanPhamKho_', "right-top", 300, "click");
						       					</script>
				           	 				</td>
				           	 	<%}else{ 
				           	 		
				           	 		
				           	 	%>
											<td class="plainlabel" valign="top"  width="60%">
				           	 					<a href="" id="sanPhamKho_" rel="subcontentSanPhamKho_">
					           	 				       <img alt="Tên sản phẩm hóa đơn" src="../images/vitriluu.png"></a>
					           	 				<DIV  id="subcontentSanPhamKho_"  style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
								                             background-color: white; width: 400px; padding: 2px;  max-height: 200px; overflow: auto;" >
								                              
								                     <table width="90%" align="center" class = "legendtitle">
								                        <tr>
								                            <th width="40px">Mã </th>
								                            <th width="90px">Tên </th>
								                            <th width="60px" align="center">Số tiền</th>
								                        </tr>
						                            	<%
					                            		int i = 0;
					                     			 	for(i =0; i< spList.size(); i++)
				                        				{  %>
							                        			
							                    				<tr align="center" >
																	<td>
								                        				<input type = "hidden" name="soloSanPham" id="soloSanPham" value="" readonly="readonly"  >
								                        				<input type = "hidden" name="phantramSanPham" id="phantramSanPham" value="0" readonly="readonly"  >
							                        					<input type = "hidden" style="width: 100px" name="nhanhangId" id="nhanhangId" value=""   >
							                        					<input type="text" name = "masanpham"  style="width: 100%; border-radius:4px; height: 20px;" value="<%= spList.get(i).getMaSp()%>"  readonly >
							                        					<input type="hidden" name = "masp"  value="<%= spList.get(i).getMaSp() %>"  >
																		<input type="hidden" name = "sanPhamId" style="width: 100%; border-radius:4px; height: 20px;" value="<%= spList.get(i).getIdSP() %>"  >
																		
																	</td>
																	<td>
																		<input type="text" name = "tenSanPham" style="width: 100%; border-radius:4px; height: 20px;" value="<%= spList.get(i).getTenSp() %>"  readonly >
																	</td>

																	<td>
							                        					<input type="text" name = "phanboSanPham" style="width: 100%; border-radius:4px; height: 20px;text-align:right" value="<%= formatter.format(spList.get(i).getPhanBo()) %>"  readonly onChange = "MyDinhDangTien(this);">
							                        				</td>
							                        			</tr>
							                        										                        			
							                        		 <% 	
							                        			
							                        		} 
							                        	
							                        		%>								                    
							                        </table>
								                     
								                     <div align="right">
								                     	<label style="color: red" ></label>
								                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								                     	<a href="javascript:dropdowncontent.hidediv('subcontentSanPhamKho_')">Hoàn tất</a>
								                     </div>
								                </DIV>
								                <script type="text/javascript">
						            					dropdowncontent.init('sanPhamKho_', "right-top", 300, "click");
						       					</script>
				           	 				</td>
				           	 				
				           	 	<%} %>
<!-- 											End Sản phẩm kho -->				           	 				
									
	                	</TR>
		                  
 	                  	<TR>
		                  	<TD class="plainlabel" valign="middle">Tiền tệ</TD>
                    		<TD class="plainlabel" valign="middle" >
                    	
			                        <select name="tienteId" id="tienteId" style = "width:200px" >
                        		
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
	                     	<TD class="plainlabel">
	                     		<input type= "hidden" id="tigia" name= "tigia" value= "<%= obj.getTigia()%>" readonly >
	                     	</TD>
	                     		<TD class="plainlabel"></TD>
								<!-- <TD class="plainlabel"></TD> -->
		                </TR>
		                  
		                  <TR>
                          		<TD class="plainlabel" valign="middle" >Diễn giải chứng từ </TD>   
		                        <TD class="plainlabel" valign="middle"  >
		                        	<input type="text" name="ghichu" value="<%= obj.getGhichu() %>" readonly > 
		                        </TD> 
		                        	<TD class="plainlabel"></TD>
								<TD class="plainlabel"></TD>         
		                  </TR> 
		                  
		                    <TR>
                          		<TD class="plainlabel" valign="middle" >Tổng tiền hàng </TD>   
		                        <TD class="plainlabel" valign="middle"  >
		                        	<input type="text" name="tongtienhang" id ="tongtienhang" value="<%= obj.getTongtienhang() %> "  readonly="readonly" > 
		                        </TD> 
		                        	<TD class="plainlabel"></TD>
								<TD class="plainlabel"></TD>         
		                  </TR>
		                  
		                  <TR>
                          		<TD class="plainlabel" valign="middle" >Tổng tiền thuế </TD>   
		                        <TD class="plainlabel" valign="middle"  >
		                        	<input type="text" name="tongtienthue" value="<%= obj.getTongtienthue() %>"  readonly="readonly"> 
		                        </TD>  
		                        	<TD class="plainlabel"></TD>
								<TD class="plainlabel"></TD>        
		                  </TR>
		                  
		                  <TR>
                          		<TD class="plainlabel" valign="middle" >Tổng tiền (có VAT) </TD>   
		                        <TD class="plainlabel" valign="middle"  >
		                        	<input type="text" name="tongtien" id = "tongtien" value="<%= obj.getTongtien_AVAT() %>"  readonly="readonly"> 
		                        </TD>  
		                        	<TD class="plainlabel"></TD>
								<TD class="plainlabel"></TD>        
		                  </TR>
		                 
		                  <tr>
		                  		<td colspan="4">

									<div class="hoaDonTable">      
									<table width="100%" cellpadding="0px" cellspacing="1px">
										<tr class="tbheader">
											<th align="center" width="15%">Diễn giải</th>
											<!-- <th align="center" width="8%">Mã HĐ</th> -->
											<th align="center" width="5%">Mẫu số HĐ</th>
											<th align="center" width="5%">Ký hiệu HĐ</th>
											<th align="center" width="5%">Số HĐ</th>
											<th align="center" width="7%">Ngày HĐ</th>
											<th align="center" width="10%">Nhà cung cấp</th>
											<th align="center" width="15%">Địa chỉ </th>
											<th align="center" width="8%">Mã số thuế</th>
											<th align="center" width="7%">Tiền hàng</th>
											<th align="center" width="3%">Thuế suất</th>
											<th align="center" width="5%">Tiền thuế</th>
											<th align="center" width="5%">Cộng</th>
										</tr>
										
										<%
										int count = 0;
										if(sochungtu != null) {  
											System.out.println("So chứng từ "+sochungtu.length);
											for(int i = 0; i < sochungtu.length; i++)
											{
												%>
												
												<TR>
													<td>
														<input type="text" name="diengiai" style="width: 100%" value="<%= diengiai[count] %>" >
														<input type="hidden" name="mahoadon" style="width: 100%" readonly="readonly" value="<%= maHD[count] %>" >
													</td>
													<td>
														<input type="text" name="mausohoadon" style="width: 100%" value="<%= mausoHD[count] %>" readonly >
													</td>
													<td>
														<input type="text" name="kyhieu" style="width: 100%" value="<%= kyhieuct[count] %>" readonly >
													</td>
													<td>
														<input type="text" name="sochungtu" style="width: 100%" value="<%= sochungtu[count] %>" readonly >
													</td>
													<td>
														<input type="text" name="ngaychungtu" style="width: 100%" value="<%= ngaychungtu[count] %>" class="days" readonly >
													</td>
													<td>
														<input type="text" name="nhacungcap" style="width: 100%; text-align: right;" value="<%= nhacungcap[count] %>" readonly >
													</td>
													<td>
														<input type="text" name="diaChiNCC" style="width: 100%; text-align: right;" value="<%= diaChiNCC[count] %>" >
													</td>
													<td>
														<input type="text" name="mst" style="width: 100%; text-align: right;" value="<%= mst[count] %>" readonly >
													</td>
													<td>
														<input type="text" name="tienhang" style="width: 100%; text-align: right;" value="<%= tienhang[count].length() == 0?"":formatter.format(Double.parseDouble(tienhang[count])) %>" readonly >
													</td>
													<td>
														<input type="text" name="thuesuat" style="width: 100%; text-align: right;" value="<%= thuesuat[count].length() == 0?"":formatter.format(Double.parseDouble(thuesuat[count])) %>"  readonly >
													</td>
													<td>
														<input type="text" name="tienthue" style="width: 100%; text-align: right;" value="<%= tienthue[count].length() == 0?"":formatter.format(Double.parseDouble(tienthue[count])) %>"  readonly >
													</td>
													<td>
														<input type="text" name="cong" style="width: 100%; text-align: right;" value="<%= tongtien[count].length() == 0?"":formatter.format(Double.parseDouble(tongtien[count])) %>" readonly="readonly" >
													</td>
												</TR>
												
											<% count ++; }
										} %>
										
										
									</table>
									</div>
		                  			
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
	
</form>

</BODY>
</HTML>
<%
	try{
		if(pnkList != null) pnkList.close();
		if(nccList != null) nccList.close();
		if(tienteList != null) tienteList.close();
	}catch(java.sql.SQLException e){
		e.printStackTrace();
	}
	obj.DbClose();
}%>