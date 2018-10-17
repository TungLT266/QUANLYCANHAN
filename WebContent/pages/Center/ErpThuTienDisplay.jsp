<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.beans.thutien.*" %>
<%@ page  import = "geso.traphaco.center.beans.thutien.imp.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>

<% IErpThutien tthdBean = (IErpThutien)session.getAttribute("tthdBean"); %>
<% ResultSet nppList = tthdBean.getNppRs(); %>
<% ResultSet hdTCList = tthdBean.getHdTCRs(); %>
<% ResultSet ndList = tthdBean.getNoidungRs(); %>
<% ResultSet htttRs = tthdBean.getHtttRs(); %>
<% ResultSet nganhangList = tthdBean.getNganhangRs(); %>
<% ResultSet chinhanhList = tthdBean.getChinhanhRs(); %>
<% ResultSet nccList = tthdBean.getNccRs(); %>
<% ResultSet nvtutList = tthdBean.getNvtuRs(); %>
<% List<IHoadon> hoadonList = tthdBean.getHoadonRs(); %>
<% ResultSet sotkRs = tthdBean.getSotkRs(); %>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  %>
<%	NumberFormat formatter = new DecimalFormat("#,###,###.##"); %>

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

<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>
<script type="text/javascript">
	$(document).ready(function() {		
		$( ".days" ).datepicker({			    
				changeMonth: true,
				changeYear: true				
		});            
	});	
</script>


<script language="javascript" type="text/javascript">
	function replaces(stt)
	{
		 var trahet = document.getElementById("trahet" + stt);
		 var avat = document.getElementById("avat" + stt);
		 var conlai = document.getElementById("conlai" + stt);
		 var thanhtoan = document.getElementById("thanhtoan" + stt);
		
		 var tienthanhtoan = thanhtoan.value.replace(/,/g,"");
		 if(tienthanhtoan == '')
			 tienthanhtoan = '0';
		 
		 thanhtoan.value = DinhDangTien(tienthanhtoan);

		 tienAvat = avat.value.replace(/,/g,"");
		 
		 if(parseFloat(tienthanhtoan) >= parseFloat(tienAvat))
		 {
			 thanhtoan.value = DinhDangTien(tienAvat);
			 conlai.value = "0";
			 trahet.checked = true;
		 }
		 else
		 {
			 conlai.value = DinhDangTien(parseFloat(tienAvat) - parseFloat(tienthanhtoan));
			 trahet.checked = false;
		 }
				
		 document.getElementById("sotienthanhtoan").value = DinhDangTien(TongTienThanhToan());
		 ThanhToan(100);
	}
	
	function TongTienThanhToan()
	{
		var tienthanhtoan = document.getElementsByName("thanhtoan");
		var tongthanhtoan = 0;
		for(k = 0; k < tienthanhtoan.length; k++)
		{
			if(tienthanhtoan.item(k).value != "")
			{
				var ttt = tienthanhtoan.item(k).value.replace(/,/g,""); 
				tongthanhtoan = parseFloat(ttt) + parseFloat(tongthanhtoan);
			}
		}
		return tongthanhtoan;
	}
	
	function DinhDangTienTT()
	{
		var giatrinhap = document.getElementById("sotienthanhtoan");
		giatrinhap.value = DinhDangTien(giatrinhap.value);
		
//		PhanBoTien();
	}
	
	function PhanBoTien()
	{	
		// Kiem tra so tien thu <= so tien no
		
		var tongtienAvat=document.getElementById("sotienthanhtoan").value.replace(/,/g,"");
		var tienthu = document.getElementById("thuduoc").value.replace(/,/g,"");
		
		if(parseFloat(tongtienAvat)- parseFloat(tienthu) < 0)
		{
			 document.getElementById("thuduoc").value= DinhDangTien(roundNumber(tongtienAvat,0));
		}
		
		 var kyhieuhd = document.getElementsByName("kyhieuhd");
		 var trahet = document.getElementsByName("trahet");
		 var avat = document.getElementsByName("avat");
		 var thanhtoan = document.getElementsByName("thanhtoan");
		 var conlai = document.getElementsByName("conlai");
		 
		 
		 for(j = 0; j < kyhieuhd.length; j++)
		 {
			 thanhtoan.item(j).value = "";
			 conlai.item(j).value = "";
			 trahet.item(j).checked = false;
		 }
		 
		 var sotienphanbo = document.getElementById("thuduoc").value.replace(/,/g,"");
		 //alert(sotienphanbo);

		 var tongtien = 0;
		 var tongthanhtoan=0;
		 for(i = 0; i < kyhieuhd.length ; i++)
		 {
			tienAvat =  avat.item(i).value.replace(/,/g, "");
			tongtien = parseFloat(tongtien) + parseFloat(tienAvat);
			
			if(tongtien < parseFloat(sotienphanbo))
			{
				thanhtoan.item(i).value = DinhDangTien(roundNumber(tienAvat, 0));
				tongthanhtoan = tongthanhtoan + parseFloat(tienAvat);
				conlai.item(i).value = 0;
				trahet.item(i).checked = true;
			}
			else
			{
				tongtien = parseFloat(tongtien) - parseFloat(tienAvat);
				var tienconlai = parseFloat(sotienphanbo) - parseFloat(tongtien);
				
				thanhtoan.item(i).value = DinhDangTien(roundNumber(tienconlai, 0));
				tongthanhtoan = tongthanhtoan + tienconlai;
				
				conlai.item(i).value = DinhDangTien(roundNumber(parseFloat(tienAvat) - parseFloat(tienconlai), 0));
				if(parseFloat(tienAvat) - parseFloat(tienconlai) <= 0)
					trahet.item(i).checked = true;
				else
					trahet.item(i).checked = false;
				
				break;
			}
		 }
		 document.getElementById("thuduoc").value= DinhDangTien(roundNumber(sotienphanbo,0));
		 document.getElementById("SoTienconlai").value = DinhDangTien((parseFloat(tongtienAvat)-parseFloat(document.getElementById("thuduoc").value.replace(/,/g, ""))),0); 
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
	
	function keypress(e) //Hàm dùng d? ngan ngu?i dùng nh?p các ký t? khác ký t? s? vào TextBox
	{    
		var keypressed = null;
		if (window.event)
			keypressed = window.event.keyCode; //IE
		else
			keypressed = e.which; //NON-IE, Standard
		
		if (keypressed < 48 || keypressed > 57)
		{ 
			if (keypressed == 8 || keypressed == 127 || keypressed == 37 || keypressed == 39 || keypressed == 0 || keypressed == 46|| keypressed == 45)
			{//Phím Delete và Phím Back
				return;
			}
			return false;
		}
	}
	
	 function saveform()
	 {	
		 var ngayghinhan = document.getElementById("ngaychungtu");
		 if(ngayghinhan.value == "")
		 {
			alert("Vui lòng nhập ngày chứng từ");
			return;
		 }

		 var thuduoc = document.getElementById("thuduoc");
		 if(thuduoc.value == "")
		 {
			alert("Vui lòng nhập số tiền thực thu của khách hàng");
			return;
		 }
		 
		 
		 document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho luu..' border='1' longdesc='cho luu..' style='border-style:outset'> Processing...</a>";
	 	 document.forms['tthdForm'].action.value='save';
	     document.forms['tthdForm'].submit();
	 }

	 function FormatNumber(e)
	{
		e.value = DinhDangDonGia(e.value);
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
	 
	 
	 function ThanhToan(n)
	 {
		 var trahet = document.getElementsByName("trahet");
		 var avat = document.getElementsByName("avat");
		 var thanhtoan = document.getElementsByName("thanhtoan");
		 var conlai = document.getElementsByName("conlai");		 
		 
		 var thuduoc = document.getElementsByName("thuduoc");
         var tongtienAvat=0;
		 var tongtienVND = 0;

		 
		 	for(i = 0; i < trahet.length; i++)
		 	{
				//alert(i); 
				tongtienAvat =parseFloat(tongtienAvat) + parseFloat(avat.item(i).value.replace(/,/g,""));
				if(trahet.item(i).checked ) // truong hop chon tra het
				{
					if(n == 100){  // tuong hop click chon tra het nhung thay doi thanh toan
						var tt;
						if(thanhtoan.item(i).value != ''){
							tt = thanhtoan.item(i).value.replace(/,/g,"");
						}else{
							tt = 0;
						}																	
						
							if((parseFloat(avat.item(i).value.replace(/,/g,"")) - parseFloat(tt)) > 0){
								
								conlai.item(i).value = DinhDangTien(roundNumber(parseFloat(avat.item(i).value.replace(/,/g,"")) - parseFloat(tt), 0));

								tongtienVND = parseFloat(tongtienVND) + parseFloat(tt);
					
								thanhtoan.item(i).value = DinhDangTien(roundNumber(tt, 0));
								avat.item(i).value = DinhDangTien(roundNumber(avat.item(i).value.replace(/,/g,""), 0));
								trahet.item(i).checked = false;
							
							}else{
								
								conlai.item(i).value = "0";
															
								var tt = thanhtoan.item(i).value.replace(/,/g,"");

								tongtienVND = parseFloat(tongtienVND) + parseFloat(tt);					
								
								avat.item(i).value = DinhDangTien(roundNumber(avat.item(i).value.replace(/,/g,""), 0));

								thanhtoan.item(i).value = avat.item(i).value;

								trahet.item(i).checked = true;
							}
						
					}else{ // Chon tra het
						
//							alert("I am here 1");
							conlai.item(i).value = "0";
							
							var tt = avat.item(i).value.replace(/,/g,"");

							tongtienVND = parseFloat(tongtienVND) + parseFloat(tt);							
							
							thanhtoan.item(i).value = avat.item(i).value;
							
							

					}
				}
				else if(i == n){ // truong hop bo chon tra het
					thanhtoan.item(i).value = "0";							
						conlai.item(i).value = avat.item(i).value;
					
				}else  // Thay doi tai cac field du lieu
				{
					var tt;
					if(thanhtoan.item(i).value != ''){
						tt = thanhtoan.item(i).value.replace(/,/g,"");
						 
					}else{
						tt = 0;
					}					
					
						if( (parseFloat(avat.item(i).value.replace(/,/g,"")) - parseFloat(tt)) >= 0 ){
							conlai.item(i).value = DinhDangTien(roundNumber(parseFloat(avat.item(i).value.replace(/,/g,"")) - parseFloat(tt), 0));
					
							tongtienVND = parseFloat(tongtienVND) + parseFloat(tt);

							thanhtoan.item(i).value = DinhDangTien(roundNumber(tt, 0));
							
							avat.item(i).value = DinhDangTien(roundNumber(avat.item(i).value.replace(/,/g,""), 0));
						
							
						}else{
							thanhtoan.item(i).value = avat.item(i).value;
							conlai.item(i).value = parseFloat(avat.item(i).value) - parseFloat(thanhtoan.item(i).value);
							
							var tt = thanhtoan.item(i).value.replace(/,/g,"");

							tongtienVND = parseFloat(tongtienVND) + parseFloat(tt);
							
							trahet.item(i).checked = true;

							var temp = sotienNT.item(i).value.replace(/,/g,"");
							sotienNT.item(i).value = DinhDangDonGia((parseFloat(temp)).toFixed(2));
						
						    
						}
				}
				
			}
		 			
		 
		 	var thu = 0;
		 
		 	var cp = 0;
		 	
		 	var ck = 0;
		 
			 	if(thuduoc.item(0).value != ''){
					 thu = thuduoc.item(0).value.replace(/,/g,"");
				 
				 }else{
					 thu = 0;
				 }

		 		document.getElementById("thuduoc").value = DinhDangTien(roundNumber(tongtienVND, 0));
	 			
		 		document.getElementById("sotienthanhtoan").value = DinhDangTien(roundNumber(tongtienAvat, 0));
		 		
				var conlai = parseFloat(tongtienAvat) - parseFloat(tongtienVND);
				document.getElementById("SoTienconlai").value = DinhDangTien(roundNumber(conlai, 0));
		 
	 }
	 
	 function ChangeTienTe(){
		 document.forms['tthdForm'].action.value= 'changeTT';
	     document.forms["tthdForm"].submit();
		 
	 }

	 function submitform()
	 { 
		 ThanhToan(100);
		 document.forms['tthdForm'].action.value='submit';
	     document.forms["tthdForm"].submit();
	 }
	 
	 function nextform()
	 { 
		 document.forms['tthdForm'].action.value='next';
	     document.forms["tthdForm"].submit();
	 }
	 function changePO()
	 { 
		 document.forms['tthdForm'].action.value='changePO';
	     document.forms["tthdForm"].submit();
	 }
	 function sellectAlls(selectId, checkboxName)
	 {
		 var chkAll = document.getElementById(selectId);
		 var spIds = document.getElementsByName(checkboxName);
		 var avat = document.getElementsByName("avat");
		 var thanhtoan = document.getElementsByName("thanhtoan");
		 var conlai = document.getElementsByName("conlai");	
		 
		 var sotienno = document.getElementsByName("sotienthanhtoan");
		 var phinganhang = document.getElementsByName("phinganhang");
		 
		 var tongtien = sotienno.item(0).value.replace(/,/g,"");
		 
		 var tt=0;
		 
		 if(chkAll.checked)
		 {
			 for(i = 0; i < spIds.length; i++)
			 {
				    conlai.item(i).value = "0";			
					
					avat.item(i).value = DinhDangTien(roundNumber(avat.item(i).value.replace(/,/g,""), 0));

					thanhtoan.item(i).value = avat.item(i).value;
					
				    spIds.item(i).checked = true;
			 }
			tt= tongtien; 
		 }
		 else
		 {
			 for(i = 0; i < spIds.length; i++)
			 {
				    thanhtoan.item(i).value = "0";			
					
					avat.item(i).value = DinhDangTien(roundNumber(avat.item(i).value.replace(/,/g,""), 0));

					conlai.item(i).value = avat.item(i).value;
					spIds.item(i).checked = false;
			 }
			 
		 }
		 
		 document.getElementById("thuduoc").value = DinhDangTien(roundNumber(tt, 0)); 
		 var phiNH = 0;
	 		if(phinganhang.item(0).value.replace(/,/g,"") !='')
	 		{
	 			phiNH =phinganhang.item(0).value.replace(/,/g,"");;
	 		}
	 				 		
		var conlai = parseFloat(tongtien) - parseFloat(tt) - parseFloat(phiNH);
			
		document.getElementById("phinganhang").value = DinhDangTien(roundNumber(phiNH, 0));
			
		document.getElementById("SoTienconlai").value = DinhDangTien(roundNumber(conlai, 0));
	 }
</script>

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { $("select").select2(); });
     
</script>

</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="tthdForm" method="post" action="">
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="id" value='<%= tthdBean.getId() %>'>
<input type="hidden" name="action" value='1'>

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:60%; padding:5px; float:left" class="tbnavigation">
        	Quản lý công nợ > Thu tiền khách hàng > Hiển thị
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn <%= userTen %> &nbsp;
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<A href= "../../ErpThutienSvl?userId=<%= userId %>" >
        	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1px" longdesc="Quay ve" style="border-style:outset"></A>
        <A href="../../ErpThutienPdfSvl?userId=<%= tthdBean.getUserId() %>&id=<%= tthdBean.getId() %>" >
	        <IMG src="../images/Printer30.png" title="In phieu" alt="In phieu" border ="1px" style="border-style:outset"></A>
    </div>
  	
  	<div align="left" style="width:100%%; float:none; clear:left">
  		<fieldset>
    		<legend class="legendtitle"> Thông báo</legend>
    		<textarea name="dataerror"  rows="1" readonly="readonly" style ="width:100%"><%= tthdBean.getMsg() %></textarea>
		         <% tthdBean.setMsg(""); %>
    	</fieldset>
  	</div>
  	
    <div align="left" style="width:100%; float:none; clear:left">
    <fieldset>
    	<legend class="legendtitle"> Thu tiền</legend>
        	<div style="float:none; width:100%" align="left">
            <TABLE width="100%" cellpadding="4" cellspacing="0" border = "0">							
                <TR>
                    <TD width="15%" class="plainlabel" valign="top">Ngày thu tiền</TD>
                    <TD class="plainlabel"  valign="top" width="15%">
                    	<input type="text"  class="days" id="ngaychungtu" name="ngaychungtu" value="<%= tthdBean.getNgaychungtu() %>" 
                    		 readonly /></TD>                                     
                    <TD class="plainlabel"  width="15%" >Diễn giải </TD>
                    <TD class="plainlabel" >                   
                        <input type="text" name="ghichu" value="<%= tthdBean.getNoidungtt() %>" style = "width:300px"> 
                     </TD> 
 
                </TR>                                        
       		
       		<TR>
       			    <TD class="plainlabel">Người nộp tiền</TD>
                    <TD class="plainlabel">
                        <input type="text" name="nguoinoptien" value="<%= tthdBean.getNguoinoptien() %>" >
                    </TD>
                    <TD class="plainlabel">Bộ phận kinh doanh</TD>
                    <TD class="plainlabel">
                        <input type="text" name="bpkinhdoanh" value="<%= tthdBean.getSochungtu() %>" >
                    </TD>
       		</TR>
               <TR>
               	<TD class="plainlabel" >Hình thức thanh toán</TD>
               	<TD class="plainlabel" >
               		<select name="htttId" id= "htttId" onChange="ChangeTienTe();" style="width: 200px">
               			<option value=""></option>
               			<%if(htttRs!= null){
               				try
               				{
               			     while(htttRs.next()){%>
               			     <%if(tthdBean.getHtttId().equals(htttRs.getString("pk_seq"))){ %>
               			       <option value="<%=htttRs.getString("pk_seq")%>" selected="selected"><%=htttRs.getString("ten") %></option>
               			     <%}else{ %>
               			      <option value="<%=htttRs.getString("pk_seq")%>" ><%=htttRs.getString("ten") %></option>
               			<%}}htttRs.close();}catch (SQLException ex){}
               			} %>
               		</select>
               	</TD>
               	<%if(tthdBean.getHtttId().equals("100002")){ // CHUYENKHOAN%>
	               	<TD class="plainlabel">Số tài khoản</TD>
	                <TD class="plainlabel">
	                        <input type="text" name="sotaikhoan" value="<%=tthdBean.getSotaikhoan()%>" >
	                </TD>
                <%}else{ %>
                 	<TD class="plainlabel" colspan="3"></TD>
                <%} %>
              </TR>
            <%if(tthdBean.getHtttId().equals("100002")){ // CHUYENKHOAN%> 
              <TR>
               	   <TD class="plainlabel">Ngân hàng</TD>
                   <TD class="plainlabel" colspan="3">
                        <input type="text" name="nganhang" style="width: 300px" value="<%=tthdBean.getNganhangTen()%>" >
                   </TD>
              </TR>
              <TR>
                   <TD class="plainlabel">Chi nhánh</TD>
                   <TD class="plainlabel" colspan="3">
                        <input type="text" name="chinhanh" style="width: 300px" value="<%=tthdBean.getChinhanhTen()%>" >
                   </TD>
               </TR>
             <%} %>
             
             <TR>
                    <TD class="plainlabel">Tìm khách hàng</TD>
                  		<TD class="plainlabel" colspan="3">                        
                  		<select name="nppSearchId" style="width: 300px" multiple="multiple" onChange="ChangeTienTe();">
                           <option value=""> </option>
                        	<%
                        		if(nppList != null)
                        		{
                        			try
                        			{
                        			while(nppList.next())
                        			{  
                        			if( tthdBean.getNppIds().contains(nppList.getString("pk_seq")) ){ %>
                        				<option value="<%= nppList.getString("pk_seq") %>" selected="selected" ><%= nppList.getString("nppTen") %></option>
                        			<%}else { %>
                        				<option value="<%= nppList.getString("pk_seq") %>" ><%= nppList.getString("nppTen") %></option>
                        		 <% } } nppList.close();} catch(SQLException ex){}
                        		}
                        	%>
                        </select>
                     </TD> 
                </TR>
                <TR>
                      <TD class="plainlabel">Tìm hóa đơn tài chính</TD>
                  		<TD class="plainlabel" colspan="3">                        
                  		<select name="hdId" id="hdId" style="width: 300px" multiple="multiple" onChange="ChangeTienTe();">
                            <option value=""> </option>
                        	<%
                        		if(hdTCList != null)
                        		{
                        			try
                        			{
                        			while(hdTCList.next())
                        			{  
                        			if( tthdBean.getHdIds().contains(hdTCList.getString("pk_seq")) ){ %>
                        				<option value="<%= hdTCList.getString("pk_seq") %>" selected="selected" ><%= hdTCList.getString("Ten") %></option>
                        			<%}else { %>
                        				<option value="<%= hdTCList.getString("pk_seq") %>" ><%= hdTCList.getString("Ten") %></option>
                        		 <% } } hdTCList.close();} catch(SQLException ex){}
                        		}
                        	%>
                        </select>
                     </TD>                     
                </TR> 
             <%if(tthdBean.getHtttId().equals("100002")){ // CHUYENKHOAN%> 
                <TR>
               		<TD  width="200px" class="plainlabel">Phí ngân hàng</TD>
                    <TD class="plainlabel" colspan ="3">
                        <input type="text" style="text-align: right;" name="phinganhang" id = "phinganhang" 
                        		value="<%=tthdBean.getChiphinganhang() %>"  AUTOCOMPLETE="off" onchange="ThanhToan(100)" > 
                     </TD>
               </TR>
             <% }else{%>
              <TR style="display: none">
               		<TD  width="200px" class="plainlabel">Phí ngân hàng</TD>
                    <TD class="plainlabel" colspan ="3">
                        <input type="text" style="text-align: right;" name="phinganhang" id = "phinganhang" 
                        		value="0"  AUTOCOMPLETE="off" onchange="ThanhToan(100)"> 
                     </TD>
               </TR>
             <%} %>
                
             
       			<TR>
                    <TD  width="200px" class="plainlabel">Số tiền nợ </TD>
                    <TD class="plainlabel" colspan = 5>
                        <input type="text" style="text-align: right;" name="sotienthanhtoan" id = "sotienthanhtoan" 
                        		value="<%= formatter.format(Double.parseDouble(tthdBean.getSotientt().replaceAll(",","")))%>"  AUTOCOMPLETE="off" > 
                     </TD>

       			</TR>
                <TR>
	                 <TD width="200px" class="plainlabel" valign="top">Số tiền thu</TD>
    	             <TD class="plainlabel" valign="top" colspan = 5>
						<input type="text"  id="thuduoc" name="thuduoc" value="<%= formatter.format(Double.parseDouble(tthdBean.getThuduoc().replaceAll(",",""))) %>"  style="text-align: right;"  onchange="PhanBoTien();" onKeyPress = "return keypress(event);"/>
					 </TD>
                </TR>
                
            	<TR>
                    <TD width="200px" class="plainlabel" valign="top">Còn lại</TD>
                    <TD class="plainlabel" valign="top" colspan = 5>
                    	<input type="text"  id="SoTienconlai" name="SoTienconlai" value=""  style="text-align: right;"  onchange="ThanhToan(100);" onKeyPress = "return keypress(event);"/>
                    </TD>

                </TR>
                
            <TR>
       		    <TD class="plainlabel">Lý do nộp</TD>
                    <TD class="plainlabel" colspan="3">
                        <input type="text" name="lydonop" value="<%= tthdBean.getLydonop() %>" >
                    </TD>   			
       		</TR>
               

            </TABLE>
       
            </div>
        
            <hr> 
         
           	<div align="left" style="width:100%; float:none; clear:none;" class="plainlabel">
            <TABLE class="tabledetail" width="100%" border="0" cellpadding="1" cellspacing="1" >
  
                <TR class="tbheader">
                	<TH align="center" width="10%">Mã khách hàng</TH> 
                	<TH align="center" width="10%">Số chứng từ</TH>
                	<TH align="center" width="10%">Ký hiệu hóa đơn</TH>
                	<TH align="center" width="10%">Số hóa đơn</TH>
                	<TH align="center" width="10%">Ngày hóa đơn</TH>
                	<TH align="center" width="10%">Tổng số tiền (đã có VAT)</TH>
               	 	<TH align="center" width="10%">Thanh toán (VNĐ)</TH>
               	 	<TH align="center" width="10%">Còn lại</TH>
               	 	<TH align="center" width="5%">Trả hết
               	 		<input type="checkbox" onchange="sellectAlls('chkAllLtt', 'trahet')" id="chkAllLtt" >
               	 	</TH>
                </TR>
                <%  
					int i;
    	            for(i = 0; i < hoadonList.size(); i++)
                	{
                		IHoadon hoadon = hoadonList.get(i);
	               		%>
	               		<tr>
	               			<td align="center">
           	 					<input type="hidden" style="width: 100%;" value="<%= hoadon.getNppId() %>" name= "nppId"  >
           	 					<input type="text" style="width: 100%;" value="<%= hoadon.getNppMa() %>" name= "nppMa"  >          					
           	 				</td>
	               			<td align="center">
           	 					<input type="text" style="width: 100%;" value="<%= hoadon.getId() %>" name= "idHd"  >          					
           	 				</td>
           	 				<td align="center">          	 				
           	 					<input type="text" style="width: 100%;" value="<%= hoadon.getKyhieu() %>" name= "kyhieuhd" readonly="readonly" >
           	 				</td>
           	 				<td align="center">          	 				
           	 					<input type="text" style="width: 100%;" value="<%= hoadon.getSo() %>" name= "sohd" readonly="readonly" >          	 				
           	 				
           	 				</td>
           	 				<td align="center">
							           	 				
           	 					<input type="text" style="width: 100%; text-align: left;" value="<%= hoadon.getNgay() %>" name= "ngayhd" readonly="readonly" >
           	 				
           	 				</td>
           	 				        	 		
           	 				<td align="center">
           	 					<input type="text" style="width: 100%; text-align: right;" value="<%= formatter.format(Double.parseDouble(hoadon.getTongtiencoVAT().replaceAll(",",""))) %>" name= "avat" id="avat<%= i %>" readonly="readonly" >
           	 				</td>         	 				
           	 				
           	 				<td align="center">
           	 					<input type="text" style="width: 100%; text-align: right;" value="<%= formatter.format(Double.parseDouble(hoadon.getThanhtoan().replaceAll(",",""))) %>" name= "thanhtoan" id="thanhtoan<%= i %>"  onchange="ThanhToan(100)" onKeyPress = "return keypress(event);">
           	 				</td>
           	 			
           	 				<td align="center">
           	 					<input type="text" style="width: 100%; text-align: right;" value="<%= hoadon.getConlai() %>" name= "conlai" id="conlai<%= i %>" readonly="readonly" >
           	 		    	</td>
           	 				<td align="center">
           	 			<% 	if(hoadon.getConlai().equals("0")){ %>
           	 					<input type="checkbox" value="<%= hoadon.getId() %>" name = "trahet" id="trahet<%= i %>" checked="checked" onchange="ThanhToan(<%= i %>)" >
           	 			<%} else {%>
           	 					<input type="checkbox"  value="<%= hoadon.getId() %>" name = "trahet" id="trahet<%= i %>" onchange="ThanhToan(<%= i %>)" >
           	 			<%} %>
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
jQuery(function()
		{		
			$("#dinhkhoanco").autocomplete("ErpTaiKhoanKeToanList.jsp");
			$("#doituongdinhkhoan").autocomplete("ErpDoiTuongTaiKhoanKeToanList.jsp");
						
		});
	ThanhToan(100);
</script>
</BODY>
</html>
<%
	if(nppList != null) nppList.close(); 
	if(ndList != null) ndList.close(); 
	if(nccList != null) nccList.close();
	if(nvtutList != null) nvtutList.close();
	if(nganhangList != null) nganhangList.close(); 
	if(chinhanhList != null) chinhanhList.close();
	if(sotkRs != null) sotkRs.close();
	if(htttRs != null) htttRs.close(); 
	tthdBean.DBclose();
	%>


