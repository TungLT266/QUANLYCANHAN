<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.erp.beans.nhapkhonhamay.*" %>
<%@ page  import = "geso.traphaco.erp.beans.nhapkhonhamay.imp.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@page import="geso.traphaco.center.util.Utility"%>

<% IErpNhapkhonhamay nhBean = (IErpNhapkhonhamay)session.getAttribute("nhBean"); %>
<% ResultSet dvthList = nhBean.getDvthList(); %>
<% ResultSet poList = nhBean.getDmhList(); %>
<% List<ISanpham> spList = nhBean.getSpList();  %>
<% NumberFormat formater = new DecimalFormat("#,###,###"); %>
<%	NumberFormat formatter2 = new DecimalFormat("#,###,###.##"); %>

<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  %>
<%	Utility util = (Utility) session.getAttribute("util"); 
	String sum = (String) session.getAttribute("sum");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{	
		int pb= util.getPhongBan(userId); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>Diageo - Project</TITLE>
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
     $(document).ready(function() { $("#nccId").select2(); });
     
 </script>

<script language="javascript" type="text/javascript">
	function replaces()
	{	
		var idhangmua = document.getElementsByName("idhangmua");
		var soluongnhan = document.getElementsByName("soluongnhan");
		var hansudung = document.getElementsByName("hansudung");
		
		for(var i = 0; i < idhangmua.length; i++)
		{
			var id = idhangmua.item(i).value;
			var solo = document.getElementsByName(id + '.solo');
			var soluong = document.getElementsByName(id + ".soluong");
			var ngaysanxuat = document.getElementsByName(id  + '.ngaysanxuat');
			var ngayhethan = document.getElementsByName(id + '.ngayhethan');
			
			var sodong =  soluong.length;
			var sln = 0;
			for(var j = 0; j < sodong; j++)
			{
				if(solo.item(j).value != "" && soluong.item(j).value != "")
				{
					var flag = false;
					for(var k = 0; k < j; k++)
					{
						if(solo.item(k).value == solo.item(j).value)
						{
							flag = true;
							break;
						}
					}
					
					if(flag)
					{
						var msg = 'Bạn đã nhập lô ' + solo.item(j).value + ' rồi, Vui lòng nhập số lô khác';
						alert(msg);
						
						solo.item(j).value = "";
						soluong.item(j).value = "";
						ngaysanxuat.item(j).value = "";
						ngayhethan.item(j).value = "";
					}
					
					if(ngaysanxuat.item(j).value != "" && hansudung.item(i).value != "" && ngayhethan.item(j).value=="")
					{
						var songay = parseFloat(hansudung.item(i).value) * 30;
						ngayhethan.item(j).value = TangDate(ngaysanxuat.item(j).value, songay);
					}
					
					sln = sln + parseInt(soluong.item(j).value);
				}
				else
				{
					if( solo.item(j).value == "" )
					{
						soluong.item(j).value = "";
						ngaysanxuat.item(j).value = "";
						ngayhethan.item(j).value = "";
					}
				}
			}
			soluongnhan.item(i).value = sln;
		}
	}	
	function TangDate(ngay, songay)
	{   
		var ArrNgay=ngay.split("-");
		var d = new Date(ArrNgay[0],ArrNgay[1]-1,ArrNgay[2]);
		d.setDate(d.getDate() + parseInt(songay));
		
		month = parseInt(d.getMonth()) + 1;
		if(month < 10)
			month = '0' + month;
		
		date = parseInt(d.getDate());
		if(date < 10)
			date = '0' + date;
					
		//alert('Ngay sau khi tang: ' + d.getFullYear() + '-' + month + '-' + date);
		return d.getFullYear() + '-' + month + '-' + date;
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
	 	 document.forms['nhForm'].action.value='save';
	     document.forms['nhForm'].submit();
	 }
	 
	 function submitform()
	 { 
		 document.forms['nhForm'].action.value='submit';
	     document.forms["nhForm"].submit();
	 }
	 function nextform()
	 { 
		 document.forms['nhForm'].action.value='next';
	     document.forms["nhForm"].submit();
	 }
	 function changePO()
	 { 
		 document.forms['nhForm'].action.value='changePO';
	     document.forms["nhForm"].submit();
	 }
</script>
	
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="nhForm" method="post" action="../../ErpNhapkhonhamayUpdateSvl">
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="id" value='<%= nhBean.getId() %>'>
<input type="hidden" value="<%= nhBean.getNcc() %>" name= "ncc">
<input type="hidden" name="action" value='1'>

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:60%; padding:5px; float:left" class="tbnavigation">
        	Quản lý mua hàng > Mua hàng trong nước > Nhập kho nhà máy > Tạo mới
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn <%= userTen %> &nbsp;
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<A href= "../../ErpNhapkhonhamaySvl?userId=<%= userId %>" >
        	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1px" longdesc="Quay ve" style="border-style:outset"></A>
        <span id="btnSave">
	        <A href="javascript:saveform()" >
	        	<IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border ="1px" style="border-style:outset"></A>
        </span>
    </div>
  	
  	<div align="left" style="width:100%; float:none; clear:left">
  		<fieldset>
    		<legend class="legendtitle"> Thông báo</legend>
    		<textarea name="dataerror"  rows="1" readonly="readonly" style ="width:100%"><%= nhBean.getMsg() %></textarea>
		         <% nhBean.setMsg(""); %>
    	</fieldset>
  	</div>
  	
    <div align="left" style="width:100%; float:none; clear:left">
    <fieldset>
    	<legend class="legendtitle"> Nhận hàng</legend>
        	<div style="float:none; width:100%" align="left">
            <TABLE width="100%" cellpadding="4" cellspacing="0">							
                <TR>
                    <TD width="150px" class="plainlabel" valign="top">Ngày nhận hàng dự kiến</TD>
                    <TD class="plainlabel" valign="top" >
                    	<input type="text"  class="days" id="ngaynhanhang" name="ngaynhanhang" 
                    			value="<%= nhBean.getNgaynhanhang() %>"  maxlength="10" readonly /></TD>
				</TR>
                <TR >
                    <TD class="plainlabel">Đơn vị thực hiện</TD>
                    <TD class="plainlabel">
                        <select name="dvthId" id="dvthId" onChange="submitform();">
                        	<option value=""> </option>
                        	<%
                        		if(dvthList != null)
                        		{
                        			try
                        			{
                        			while(dvthList.next())
                        			{  
                        			if( dvthList.getString("pk_seq").equals(nhBean.getDvthId())){ %>
                        				<option value="<%= dvthList.getString("pk_seq") %>" selected="selected" ><%= dvthList.getString("ten") %></option>
                        			<%}else { %>
                        				<option value="<%= dvthList.getString("pk_seq") %>" ><%= dvthList.getString("ten") %></option>
                        		 <% } } dvthList.close();} catch(SQLException ex){}
                        		}
                        	%>
                        </select>
                     </TD> 
                	 
                </TR>
                <TR>
                   
                    <TD class="plainlabel">Số PO</TD>
                    <TD class="plainlabel">
                        <select name="soPO" id="soPO" onChange="changePO();">
                        	<option value=""> </option>
                        	<%
                        		if(poList != null)
                        		{
                        			try
                        			{
                        			while(poList.next())
                        			{  
                        			if( poList.getString("poId").equals(nhBean.getDonmuahangId())){ %>
                        				<option value="<%= poList.getString("poId") %>" selected="selected" ><%= poList.getString("poTen")%></option>
                        			<%}else { %>
                        				<option value="<%= poList.getString("poId") %>" ><%= poList.getString("poTen") %></option>
                        		 <% } } poList.close();} catch(SQLException ex){}
                        		}
                        	%>
                        </select>
                     </TD> 
                </TR>

				<TR >
                     
                     <TD  class="plainlabel" valign="top" >Diễn giải</TD>
                     <TD class="plainlabel" valign="top">
                    	<input type="text" id="diengiai" name="diengiai" value="<%= nhBean.getDiengiai() %>" /></TD>
                      
                </TR>
			
            </TABLE>
            <hr> 
            </div>
           
        <div align="left" style="width:100%; float:none; clear:none;" class="plainlabel">
            <TABLE class="tabledetail" width="100%" border="0" cellpadding="1" cellspacing="1" >
                <TR class="tbheader"> 
                	<TH align="center" width="3%">STT</TH>
               		<TH align="center" width="10%">Mã sản phẩm</TH>
                	<TH align="center" width="20%">Tên sản phẩm</TH>
                	<!-- <TH align="center" width="8%">Ngày nhận</TH> -->
                	<TH align="center" width="8%">ĐVT</TH>
               	 	<TH align="center" width="6%">SL đặt</TH>
               	 	<TH align="center" width="8%">SL nhận</TH>                 	 	
                </TR>
                
                
                <!--  LOAD SẢN PHẨM RA -->
                
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
           	 					<input type="text" style="width: 100%; text-align: left;" value="<%= sp.getMa() %>" name= "mahangmua" readonly="readonly" >
           	 					<input type="hidden" value="<%= sp.getId() %>" name= "idhangmua">
           	 					<input type="hidden" value="<%= sp.getGia() %>" name= "gia">
           	 					<input type="hidden" value="<%= sp.getNhomkenh() %>" name= "nhomkenh">
           	 					<input type="hidden" value="<%= sp.getThuexuat() %>" name= "thuexuat">
           	 					<input type="hidden" value="<%= sp.getHansudung() %>" name= "hansudung" readonly="readonly" >
           	 				</td>
           	 				<td align="left">
           	 					<input type="text" style="width: 100%; text-align: left;" value="<%= sp.getDiengiai() %>" name= "tenhangmua" readonly="readonly" >
           	 				</td>
           	 				
           	 				<%-- <td align="right">
           	 					<input type="text" style="width: 100%; text-align: center; " value="<%= sp.getNgaynhandukien() %>" name= "ngaynhandukien" readonly="readonly" >
           	 				</td> --%>
           	 				<td align="left">
           	 					<input type="text" style="width: 100%; text-align: left;" value="<%= sp.getDvdl() %>" name= "dvdl" readonly="readonly" >
           	 				</td>
           	 				<td align="right">
           	 					<input type="text" style="width: 100%; text-align: right;" value="<%= sp.getSoluongdat() %>" name= "soluongdat" readonly="readonly" >
           	 					<input type="hidden" style="width: 100%; text-align: right;" value="<%= sp.getDungsai() %>" name= "dungsai" readonly="readonly" >
           	 				</td>
           	 				
           	 				<td align="left">
           	 				
           	 					<input type="text" style="width: 75%; text-align: right;" value="<%= sp.getSoluongnhan() %>" name= "soluongnhan" readonly="readonly" >
           	
				                <a href="" id="<%= sp.getId() %>" rel="subcontent<%= i %>">
	           	 				 <img alt="Số lô - Số lượng nhận" src="../images/vitriluu.png"></a>
	           	 					
	           	 							
	           	 				<DIV id="subcontent<%= i %>" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
				                             background-color: white; width: 450px; padding: 4px;">
				                    <table width="90%" align="center">
				                        <tr>
				                        	<th width="100px">Số lô</th>
				                            <th width="100px">Số lượng</th>
				                            <th width="100px">Ngày sản xuất</th>
				                            <th width="100px">Ngày hết hạn</th>
				                        </tr>
				                        <%
				                        	List<ISpDetail> spDetailList = sp.getSpDetail();
				                        	int stt = 0;
				                     				                      
				                        	if(spDetailList.size() > 0)
				                        	{
				                        		for(int sd = 0; sd < spDetailList.size(); sd++)
				                        		{
				                        			ISpDetail spDetail = spDetailList.get(sd);
				                        		%>
				                        			<tr>
							                            <td>
							                            	<input type="text" size="100px" name="<%= sp.getId() + ".solo" %>" value="<%= spDetail.getSolo() %>" onchange="replaces()"/>
							                            </td>
							                            <td>
							                            	<input type="text" size="100px"  name="<%= sp.getId() + ".soluong" %>" value="<%= spDetail.getSoluong() %>" style="text-align: right;" onchange="replaces()"/></td>
							                            <td>
							                            	<input type="text" size="100px"  class="days" 
							                            		name="<%= sp.getId() + ".ngaysanxuat" %>" value="<%= spDetail.getNgaySx() %>" readonly="readonly" onchange="replaces()" /></td>
							                            <td>
							                            	<input type="text" size="100px"  class="days" 
							                            		name="<%= sp.getId() + ".ngayhethan" %>" value="<%= spDetail.getNgayHethan() %>" readonly="readonly"   />
							                            </td>
							                        </tr>
				                        		<% stt++; }
				                        	} 
				                        %>
				                        <%
				                        	for(int k = 0; k < 6 - spDetailList.size(); k++)
				                        	{
				                        		%>
				                        		<tr>
						                            <td>
						                            	<input type="text" size="100px" name="<%= sp.getId() + ".solo" %>" value="" onchange="replaces()" /></td>
						                            <td>
						                            	<input type="text" size="100px"  name="<%= sp.getId() + ".soluong" %>" value="" style="text-align: right;" onchange="replaces()"/></td>
						                            <td>
						                            	<input type="text" size="100px"  class="days" name="<%= sp.getId() + ".ngaysanxuat" %>" 
						                            	  value="" readonly="readonly" onChange="replaces()" />
						                            </td>
						                            <td>
						                            	<input type="text" size="100px"  name="<%= sp.getId() + ".ngayhethan" %>"  class="days" 
						                            	 value="" readonly="readonly"  />
						                            </td>
						                        </tr>
				                        	<% stt++; }
				                     %>
				                    </table>
				                     <div align="right">
				                     	<label style="color: red" ></label>
				                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				                     	<a href="javascript:dropdowncontent.hidediv('subcontent<%= i %>')">Hoàn tất</a>
				                     </div>
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
	//replaces();
	<% 
		for(int i = 0; i < spList.size(); i++)
		{
			ISanpham sp = spList.get(i);
	%>
		   dropdowncontent.init('<%= sp.getId() %>', "left-top", 300, "click");
	<%} %>
</script>
<%
try{

	if(spList!=null){
		spList.clear();
	}
	nhBean.close();
	
}catch(Exception er){
	
}
		 }
%>
</BODY>
</html>