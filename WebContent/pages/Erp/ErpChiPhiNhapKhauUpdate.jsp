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
	
	function kiemTraPhanBo(){
		var phieunhan = document.getElementsByName("pnkIds");
		var phanboSanPham = document.getElementsByName("phanboSanPham");
		var tongtienhang = document.getElementById("tongtienhang").value.replace(/,/g,"");
		var tongTruoc = 0;
		
		var tongTienPhanBo = 0;
		if(phanboSanPham.item(0).value !=''){
			for( var i = 0; i < phanboSanPham.length; i++){
				if(phanboSanPham.item(i).value != ''){
					tongTienPhanBo += parseFloat(phanboSanPham.item(i).value.replace(/,/g,""));
				}
			}
		}
		console.log("tongTienPhanBo: " + tongTienPhanBo + ", parseFloat(tongtienhang): " + parseFloat(tongtienhang));
		if (tongTienPhanBo != parseFloat(tongtienhang))
			return false;
		return true;
	}
	
	
	
	function save()
	{
		if (kiemTraPhanBo() == false)
		{
			document.forms["cpnk"].dataerror.value = '"Tổng tiền hàng" phải bằng tổng tiền "Phân bổ"';
			return;
		}
		
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
		var idsp = document.getElementsByName("sanPhamId");
		var masp = document.getElementsByName("masp");
		var tensp = document.getElementsByName("tenSanPham");
		
		
		var sodong =  masp.length;

		var i;
		for(i = 0; i < sodong; i++)
		{
			if(  masp.item(i).value != "")
			{
				var sp = masp.item(i).value;
				var pos = parseInt(sp.indexOf("--"));
				var vtridauma = parseInt(sp.indexOf("-- [")) + 4;
				var vitricuoima = parseInt(sp.indexOf("]["));
				var vitridauten = vitricuoima + 2;
				var vitricuoiten = sp.length-1;
				
				
				if(pos > 0)
				{
					idsp.item(i).value = sp.substring(0, pos);
					masp.item(i).value = sp.substring(vtridauma, vitricuoima);
					tensp.item(i).value = sp.substring(vitridauten, vitricuoiten);

				}
			 
			}
			else 
			{
				
						idsp.item(i).value = "";
						tensp.item(i).value = "";
						
			}
		}  
		
	var mst = document.getElementsByName("mst");	
	var tenncc = document.getElementsByName("nhacungcap");	
	var diaChiNCC = document.getElementsByName("diaChiNCC");	
	var sodongMST = mst.length;
	console.log(sodongMST);
	var i1 ;
	for(i1 = 0; i1 < sodongMST; i1++){
		var msti = mst.item(i1);
		var masothue = msti.value;   
		if(masothue != "" && masothue != null){ 
//			var tenncc = document.getElementById("nhacungcap" +i1);
//		    var diaChiNCC = document.getElementById("diaChiNCC" +i1); 
		    
		    var pos = parseInt(masothue.indexOf("[ "));
		     
		     if(pos>0)
		     {
		      msti.value  = masothue.substring(0, pos);
		      
		      masothue = masothue.substr(parseInt(masothue.indexOf("[")) + 2);
		      tenncc.item(i1).value = masothue.substring(0, parseInt(masothue.indexOf("]"))); 
		      
		      masothue = masothue.substr(parseInt(masothue.indexOf("[")) + 2);
		      diaChiNCC.item(i1).value = masothue.substring(0, parseInt(masothue.indexOf("]"))); 
		     }
		}
	}

		setTimeout(replaces, 300);
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
	
	function MyDinhDangTien(p)
	{
		p.value = DinhDangTien(parseFloat(p.value.replace(/,/g,"")));
	}
	
	function Tinhtienthue(n)
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
		
		if (tienhang.item(n) != null && tienhang.item(n).value == "")
			tienhang.item(n).value = "0";
		if (thuesuat.item(n) != null && thuesuat.item(n).value == "")
			thuesuat.item(n).value = "0";
		if (tienthue.item(n) != null && tienthue.item(n).value == "")
			tienthue.item(n).value = "0";

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
		
		tinhPhanBo(n);

	}
	
	function tinhPhanBo(n){
		var phieunhan = document.getElementsByName("pnkIds");
		var phanTramSanPham = document.getElementsByName("phantramSanPham");
		var phanboSanPham = document.getElementsByName("phanboSanPham");
		var tongtienhang = document.getElementById("tongtienhang").value.replace(/,/g,"");
		var tongTruoc = 0;
		


		var tienSanPham = document.getElementsByName("tienSanPham");
		var tongTienSanPham = 0;
		if(phieunhan.item(0).value !=''){
			for( var i = 0; i < tienSanPham.length; i++){
				if(tienSanPham.item(i).value != ''){
					tongTienSanPham += parseFloat(tienSanPham.item(i).value.replace(/,/g,""));
				}
			}
		}
		var tongPtSanPham = 0;
		if(phieunhan.item(0).value !=''){
			for( var i = 0; i < tienSanPham.length; i++){
				if(tienSanPham.item(i).value != ''){
					var tienSanPhamI = parseFloat(tienSanPham.item(i).value.replace(/,/g,""));
					if (tienSanPhamI > 0)
					{
						if (i == (phanTramSanPham.length - 1))


						{
							phanTramSanPham.item(i).value = 100 - tongPtSanPham;
						}else{
							var ptSp = tienSanPhamI / tongTienSanPham *100;
							tongPtSanPham += ptSp;
							phanTramSanPham.item(i).value =  ptSp;
						}
					}
				}
			}
		}
		
		if(phieunhan.item(0).value !=''){
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
	}
	
	function Tinhthuesuat()
	{
		console.log("Tinhthuesuat");
		var tienhang = document.getElementsByName("tienhang");
		//var thuesuat = document.getElementsByName("thuesuat");	
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
				
				//thue = parseFloat(ttThue) / parseFloat(TH) * 100 ;
				//thuesuat.item(i).value = DinhDangDonGia(thue.toFixed(3));
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
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý mua hàng > Nghiệp vụ khác > Chi phí nhận hàng > Cập nhật</TD> 
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
		                        	<input type="text" name="ngaynhap" value="<%= obj.getNgaynhap() %>" class="days" onchange= submitform(); readonly="readonly"> 
		                        </TD>   
		                        <TD class="plainlabel"></TD>
								<TD class="plainlabel"></TD>       
		                  </TR> 
                          <TR>
								<TD class="plainlabel" valign="top" width="150px">Số chứng từ </TD>
								<TD class="plainlabel" valign="top" colspan="5">
									<input type="text" id="soChungTu_Chu" name="soChungTu_Chu" value="<%= obj.getSoChungTu_Chu() %>" maxlength="10" style="border-radius:4px; height: 20px; width: 100px;"/>
									<input type="text" id="soChungTu_So" name="soChungTu_So" value="<%= obj.getSoChungTu_So() %>" maxlength="10" style="border-radius:4px; height: 20px; width: 100px;"/>
								</TD>
                          </TR>	
                    	  <TR>
                        		<TD class="plainlabel" valign="middle" width = "20%">Nhà cung cấp dịch vụ vận chuyển</TD>
                        		<TD class="plainlabel" valign="middle">
                    			<select name="nccId_cn" id="nccId_cn" style = "width:400px" onchange= submitform();>
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
                        				} catch(SQLException ex){}
                        		}
                        	%>
                      			  </select>
                        		
                        		</TD>  
                        		<TD class="plainlabel"></TD>
								<TD class="plainlabel"></TD>                      
                    	  </TR>

                    	  <TR>
                        		<TD class="plainlabel" valign="middle" >Nhà cung cấp bán hàng</TD>
                        		<TD class="plainlabel" valign="middle">
                   				<select name="nccId" id="nccId" style = "width:500px" onChange = "submitform();">
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
                    	
			                        <select name="pnkIds" id="pnkIds" multiple style = "width:400px" onchange= submitform();>
                        		<option value="" >All</option>
            	            	<%
                	        		if(pnkList != null)
                    	    		{
                        				try
                        				{
                        					while(pnkList.next())
                        					{  
                        						 if( obj.getPnkIds().contains(pnkList.getString("pnkId")) ==true){ %>
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
							                        					<input type = "hidden" style="width: 100px" name="idSP" id="idSP" value="<%= spList.get(i).getIdSP() %>"   >
							                        				</td>
							                        				<td><input style="width: 150px" name="tenSanPham" id="tenSanPham" value="<%= spList.get(i).getTenSp() %>" readonly="readonly"  ></td>
							                        				<td><input style="width: 90px" name="soloSanPham" id="soloSanPham" value="<%= spList.get(i).getSoLo() %>" readonly="readonly"  ></td>
							                        				<td ><input style="width: 100%; text-align: right;" name="tienSanPham" id="tienSanPham" value="<%= formatter.format(spList.get(i).getTien()) %>" readonly="readonly"  ></td>
							                        				<td ><input style="width: 60px; text-align: right;" name="phantramSanPham" id="phantramSanPham" value="<%= formatter2.format(spList.get(i).getPhanTram()) %>" readonly="readonly" ></td>
							                        				<td><input style="width: 100%; text-align: right;"  name="phanboSanPham" id="phanboSanPham" value="<%= formatter.format(spList.get(i).getPhanBo()) %>" readonly="readonly" onChange = "MyDinhDangTien(this);"></td>
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
											<td class="plainlabel" valign="top"60%">
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
							                        					<input type="text" name = "phanboSanPham" style="width: 100%; border-radius:4px; height: 20px;text-align:right" value="<%= formatter.format(spList.get(i).getPhanBo()) %>"  onChange = "MyDinhDangTien(this);">
							                        				</td>
							                        			</tr>
							                        										                        			
							                        		 <% 
							                        		
					                        				
					                        			 	}
							                        		for(int m = i;m<30;m++){
							                        			%>
							                        			<tr align="center" >
																	<td>
								                        				<input type = "hidden" name="soloSanPham" id="soloSanPham" value="" readonly="readonly"  >
								                        				<input type = "hidden" name="phantramSanPham" id="phantramSanPham" value="0" readonly="readonly"  >
							                        					<input type = "hidden" style="width: 100px" name="nhanhangId" id="nhanhangId" value=""   >
							                        					<input type="text" name = "masp"  style="width: 100%; border-radius:4px; height: 20px;" value="" onkeyup="ajax_showOptions(this,'spchiphinhanhang',event)"  AUTOCOMPLETE="off" >
																		<input type="hidden" name = "sanPhamId" style="width: 100%; border-radius:4px; height: 20px;"  >
																		
																	</td>
																	<td>
																		<input type="text" name = "tenSanPham" style="width: 100%; border-radius:4px; height: 20px;"  >
																	</td>

																	<td>
							                        					<input type="text" name = "phanboSanPham" style="width: 100%; border-radius:4px; height: 20px;text-align:right" onchange="MyDinhDangTien(this);">
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
                    	
			                        <select name="tienteId" id="tienteId" style = "width:200px" onchange= submitform();>
                        		
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
	                     		<input type= "hidden" id="tigia" name= "tigia" value= "<%= obj.getTigia()%>">
	                     	</TD>
	                     		<TD class="plainlabel"></TD>
								<!-- <TD class="plainlabel"></TD> -->
		                </TR>
		                  
		                  <TR>
                          		<TD class="plainlabel" valign="middle" >Diễn giải chứng từ</TD>   
		                        <TD class="plainlabel" valign="middle"  >
		                        	<input type="text" name="ghichu" value="<%= obj.getGhichu() %>"  > 
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
														<input type="text" name="mausohoadon" style="width: 100%" value="<%= mausoHD[count] %>" >
													</td>
													<td>
														<input type="text" name="kyhieu" style="width: 100%" value="<%= kyhieuct[count] %>" >
													</td>
													<td>
														<input type="text" name="sochungtu" style="width: 100%" value="<%= sochungtu[count] %>" >
													</td>
													<td>
														<input type="text" name="ngaychungtu" style="width: 100%" value="<%= ngaychungtu[count] %>" class="days" >
													</td>
													<td>
														<input type="text" name="nhacungcap" id = <%= "nhacungcap"  + count %> style="width: 100%; text-align: right;" value="<%= nhacungcap[count] %>" >
													</td>
													<td>
														<input type="text" name="diaChiNCC" id = <%= "diaChiNCC"  + count %> style="width: 100%; text-align: right;" value="<%= diaChiNCC[count] %>" >
													</td>
													<td>
														<input type="text" name="mst" id = <%= "mst"  + count %> style="width: 100%; text-align: right;" value="<%= mst[count] %>" >
														<script type="text/javascript">
																	jQuery(function()
																		{		
																			$("#mst<%="" + count %>").autocomplete("MST_NCC_DNTT.jsp?");
																		});	
																</script>	
													</td>
													<td>
														<input type="text" name="tienhang" style="width: 100%; text-align: right;" value="<%= tienhang[count].length() == 0?"":formatter.format(Double.parseDouble(tienhang[count].replaceAll(",", ""))) %>"  onkeypress="return keypress(event);" onchange="Tinhtienthue(<%=count%>);">
													</td>
													<td>
														<input type="text" name="thuesuat" style="width: 100%; text-align: right;" value="<%= thuesuat[count].length() == 0?"":formatter.format(Double.parseDouble(thuesuat[count].replaceAll(",",""))) %>"   onkeypress="return keypress(event);" onchange="Tinhtienthue(<%=count%>);">
													</td>
													<td>
														<input type="text" name="tienthue" style="width: 100%; text-align: right;" value="<%= tienthue[count].length() == 0?"":formatter.format(Double.parseDouble(tienthue[count].replaceAll(",",""))) %>"   onkeypress="return keypress(event);" onchange="Tinhthuesuat(<%=count%>);">
													</td>
													<td>
														<input type="text" name="cong" style="width: 100%; text-align: right;" value="<%= tongtien[count].length() == 0?"":formatter.format(Double.parseDouble(tongtien[count].replaceAll(",",""))) %>" readonly="readonly" >
													</td>
												</TR>
												
											<% count ++; }
										} %>
										
										
										<% int i = 0; 
											while(i <= 15 - count) { 
											int dem = count + i;%>
										
											<TR>
												<td>
													<input type="text" name="diengiai" style="width: 100%" >
													<input type="hidden" name="mahoadon" readonly="readonly" style="width: 100%"  >
												</td>
												<td>
														<input type="text" name="mausohoadon" style="width: 100%"  >
												</td>
												<td>
													<input type="text" name="kyhieu" style="width: 100%" >
												</td>
												<td>
													<input type="text" name="sochungtu" style="width: 100%" >
												</td>
												<td>
													<input type="text" name="ngaychungtu" style="width: 100%" class="days" >
												</td>
												<td>
													<input type="text" name="nhacungcap" id = <%= "nhacungcap"  + dem%> style="width: 100%; text-align: right;" value="" >
												</td>
												<td>
													<input type="text" name="diaChiNCC" id = <%= "diaChiNCC"  + dem %> style="width: 100%; text-align: right;" value="" >
												</td>
												<td>
											      			<input type="text" name= <%= "mst" %> id = <%= "mst"  + dem%>  value = "" style="width: 100%; border-radius:4px; height: 20px;">
												      			<script type="text/javascript">
																	jQuery(function()
																		{		
																			$("#mst<%="" + dem %>").autocomplete("MST_NCC_DNTT.jsp?");
																		});	
																</script>	
											      		</td>
												<td>
													<input type="text" name="tienhang" style="width: 100%; text-align: right;"  onkeypress="return keypress(event);" onchange="Tinhtienthue(<%=i + count%>);">
												</td>
												<td>
													<input type="text" name="thuesuat" style="width: 100%; text-align: right;"  onkeypress="return keypress(event);" onchange="Tinhtienthue(<%=i + count%>);">
												</td>
												<td>
													<input type="text" name="tienthue" style="width: 100%; text-align: right;"   onkeypress="return keypress(event);" onchange="Tinhthuesuat(<%=i + count%>);">
												</td>
												<td>
													<input type="text" name="cong" style="width: 100%; text-align: right;" readonly="readonly" >
												</td>
											</TR>
										
										<% i++; } %>
										
										
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
<script>
	 replaces();
</script>
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