<%@page import="geso.traphaco.center.util.Utility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.SQLException"%>
<%@ page  import = "geso.traphaco.erp.beans.phieuxuatkhoTSCD.*" %>
<%@ page  import = "geso.traphaco.erp.beans.phieuxuatkhoTSCD.imp.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.util.List" %>
<% IErpPhieuxuatkhoTSCD pxkBean = (IErpPhieuxuatkhoTSCD)session.getAttribute("pxkBean"); %>
<% ResultSet khoList = pxkBean.getKhoList(); %>
<% ResultSet ndxList = pxkBean.getNdxList(); %>
<% ResultSet ddhList = pxkBean.getDdhList(); %>
<% ResultSet trahangList = pxkBean.getTrahangList(); %>
<% List<ISanpham> spList = pxkBean.getSpList(); %>

<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");
	String sum = (String) session.getAttribute("sum");
	Utility  util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

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
	<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>


<script language="javascript" type="text/javascript">
	
	function replaces()
	{
		
		var maNcc = document.getElementById("nccId");
		
		if(maNcc != null)
		{
			if(maNcc.value != "")
			{
				if( maNcc.value.indexOf(" -- ") > 0 )
				{
					var str = maNcc.value;
					maNcc.value = str.substring(0, maNcc.value.indexOf(" -- "));
					document.getElementById("nccTen").value = str.substring(str.indexOf(" -- ") + 4);
					
					submitform();
				}
			}
			else
			{
				document.getElementById("nccTen").value = "";
			}
		}
		
		
		setTimeout(replaces, 200);
	}

	 function saveform()
	 {	
		 if(document.getElementById("ngayxuatkho").value == "")
		 {
			 alert('Ban phai chon ngay xuat kho');
			 return;
		 }
		 
		
			 if(document.getElementById("trahangId").value == "")
			 {
				 alert('Ban phai chon so don tra hang');
				 return;
			 }
		 
		 
		 if(document.getElementById("noidungxuat").value == "")
		 {
			 alert('Ban phai chon noi dung xuat');
			 return;
		 }
		 
		 
		 document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho luu..' border='1' longdesc='cho luu..' style='border-style:outset'> Processing...</a>";
	 	 document.forms['nkForm'].action.value='save';
	     document.forms['nkForm'].submit();
	 }
	 
	 function submitform()
	 { 
		 document.forms['nkForm'].action.value='submit';
	     document.forms["nkForm"].submit();
	 }
	 
	 function LocChungTu()
	 { 
		 document.forms['nkForm'].action.value='locchungtu';
	     document.forms["nkForm"].submit();
	 }
	 
	 function changeform()
	 { 
		 document.forms['nkForm'].action.value = 'submit';
		 document.forms['nkForm'].changeDdh.value = 'true';
	     document.forms["nkForm"].submit();
	 }
	 
	 function changeNdx()
	 { 
		 document.forms['nkForm'].action.value='changendx';
	     document.forms["nkForm"].submit();
	 }
	 
	 function DinhDangTienTT(e)
	 {
		e.value = DinhDangDonGia( e.value.replace(/,/g,""));
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
		
		num = (Math.round( num * 1000 ) / 1000).toString();
		
		var sole = '';
		if(num.indexOf(".") >= 0)
		{
			sole = num.substring(num.indexOf('.'));
		}
		
		//alert('So le: ' + sole);
		
		if(isNaN(num))
			num = "0";
		sign = (num == (num = Math.abs(num)));
		//num = Math.floor(num*100+0.50000000001);
		num = Math.floor( num * 100);
		num = Math.floor(num/100).toString();
		for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
			num = num.substring(0,num.length-(4*i+3)) + ',' + num.substring(num.length-(4*i+3));

		var kq;
		if(sole.length > 0)
		{
			kq = (((sign)?'':'-') + num) + sole;
		}
		else
			kq = (((sign)?'':'-') + num);
		
		//alert(kq);
		return kq;
	}
	 
</script>
	
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="nkForm" method="post" action="../../ErpPhieuxuatkhoTSCDUpdateSvl">
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="id" value='<%= pxkBean.getId() %>'>
<input type="hidden" name="dungsai" value='<%= pxkBean.getDungsai() %>'>
<input type="hidden" name="changeDdh" value='false'>

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:60%; padding:5px; float:left" class="tbnavigation">
        	Quản lý mua hàng > Xuất trả TSCĐ > Cập nhật
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn <%= userTen %> &nbsp;
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<A href= "../../ErpPhieuxuatkhoTSCDSvl?userId=<%= userId %>" >
        	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1px" longdesc="Quay ve" style="border-style:outset"></A>
        <span id="btnSave">
	        <A href="javascript:saveform()" >
	        	<IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border ="1px" style="border-style:outset"></A>
        </span>
    </div>
  	
  	<div align="left" style="width:100%; float:none; clear:left">
  		<fieldset>
    		<legend class="legendtitle"> Thông báo</legend>
    		<textarea name="dataerror"  rows="1" readonly="readonly" style ="width:100%%"><%= pxkBean.getMsg() %></textarea>
    	</fieldset>
  	</div>
  	
    <div align="left" style="width:100%; float:none; clear:left">
    <fieldset>
    	<legend class="legendtitle"> Phiếu xuất kho </legend>
        	<div style="float:none; width:100%" align="left">
            <TABLE width="100%" cellpadding="4" cellspacing="0">							
                <TR>
                    <TD width="100px" class="plainlabel" valign="top">Ngày xuất kho</TD>
                    <TD class="plainlabel" valign="top" colspan="3">
                    	<input type="text"  class="days" id="ngayxuatkho" name="ngayxuatkho" value="<%= pxkBean.getNgayxuatkho() %>" 
                    		maxlength="10" readonly />
                    </TD>
                </TR> 
                <TR>
                    <TD class="plainlabel">Lý do xuất</TD>
                    <TD class="plainlabel" colspan="3">
                        <select name="noidungxuat" id="noidungxuat" onchange="changeNdx();">
                        	<option value=""></option>
                        	<%
                        		if(ndxList != null)
                        		{
                        			try
                        			{
                        			while(ndxList.next())
                        			{  
                        			if( ndxList.getString("pk_seq").equals(pxkBean.getNdxId())){ %>
                        				<option value="<%= ndxList.getString("pk_seq") %>" selected="selected" ><%= ndxList.getString("ten") %></option>
                        			<%}else { %>
                        				<option value="<%= ndxList.getString("pk_seq") %>" ><%= ndxList.getString("ten") %></option>
                        		 <% } } ndxList.close();} catch(SQLException ex){}
                        		}
                        	%>
                        </select>
                     </TD> 
                </TR>

                <TR >
					<TD class="plainlabel">Nhà cung cấp</TD>
					<TD class="plainlabel" width="210px">
						<input type="text" name="nccId" id="nccId" value="<%= pxkBean.getNccId() %>" >
					</TD>
					<TD class="plainlabel" width="100px">Tên nhà cung cấp</TD>
					<Td class="plainlabel" width="400px">
						<input type="text" name="nccTen" id="nccTen" value="<%= pxkBean.getNccTen() %>" readonly="readonly" style="width: 100%" >
					</Td>
			    </TR>
                <TR>
                    <TD class="plainlabel" valign="top">Đơn trả hàng</TD>
                    <TD class="plainlabel" colspan="3">
                        <select name="trahangId" id="trahangId" onchange="changeform()">
                        	<option value=""> </option>
                        	<%
                        		if(trahangList != null)
                        		{
                        			try
                        			{
                        			while(trahangList.next())
                        			{  
                        			if( trahangList.getString("dmhId").equals(pxkBean.getTrahangNccId())){ %>
                        				<option value="<%= trahangList.getString("dmhId") %>" selected="selected" ><%= trahangList.getString("dmhTen") %></option>
                        			<%}else { %>
                        				<option value="<%= trahangList.getString("dmhId") %>" ><%= trahangList.getString("dmhTen") %></option>
                        		 <% } } trahangList.close();} catch(SQLException ex){}
                        		}
                        	%>
                        </select>
                     </TD> 
                </TR>  
              
                <TR>
                    <TD class="plainlabel" >Ghi chú</TD>
                    <TD class="plainlabel" colspan="3" >
                    	<input type="text" id="ghichu" name="ghichu" value="<%= pxkBean.getGhichu() %>" />
                    </TD>
                </TR> 
            </TABLE>
            <hr> 
            </div>
           
           <div align="left" style="width:100%; float:none; clear:none;">
           <TABLE class="tabledetail" width="100%" border="0" cellpadding="1" cellspacing="1" >
                <TR class="tbheader"> 
                	<TH align="center" width="5%">STT</TH>
                	<TH align="center" width="15%">Mã tài sản</TH>
                	<TH align="center" width="30%">Tên tài sản</TH>
                	<TH align="center" width="8%">Đơn vị</TH>
                	<TH align="center" width="8%">Số lượng</TH>
                	<TH align="center" width="8%">SL đã xuất</TH>
               	 	<TH align="center" width="5%">SL xuất</TH>
               	 	<TH align="center" width="10%">Ghi chú</TH>
                </TR>
                <%
                	for(int i = 0; i < spList.size(); i++)
                	{
                		ISanpham sp = spList.get(i);
	               		%>
	               		<tr>
           	 				<td align="left">
           	 					<input type="text" style="width: 100%; text-align: left;" value="<%= i+1 %>" name= "stt" readonly="readonly">
           	 				</td>
           	 				<td align="left">
           	 					<input type="text" style="width: 100%; text-align: left;" value="<%= sp.getMa() %>" name= "mahangmua" readonly="readonly" >
           	 					<input type="hidden" value="<%= sp.getId() %>" name= "idhangmua">
           	 				</td>
           	 				<td align="left">
           	 					<input type="text" style="width: 100%; text-align: left;" value="<%= sp.getDiengiai() %>" name= "diengiai" readonly="readonly" >
           	 				</td>
           	 				<td align="left">
           	 					<input type="text" style="width: 100%; text-align: left;" value="<%= sp.getDVT() %>" name= "donvi" readonly="readonly" >
           	 				</td>
           	 				<td align="left">
           	 					<input type="text" style="width: 100%; text-align: right;" value="<%= sp.getSoluongTotal() %>" name= "soluongToltal" readonly="readonly" >
           	 				</td>
           	 				       	 				
           	 				<td align="left">
           	 					<input type="text" style="width: 100%; text-align: right;" value="<%= sp.getSoluongDaXuat() %>" name= "soluongDaXuat" readonly="readonly" >
           	 				</td>
           	 				<td align="left">
           	 					<input type="text" style="width: 100%; text-align: right;" value="<%= sp.getSoluong() %>" name= "soluong" >
           	 				</td>
           	 				<td align="left">
           	 					<input type="text" style="width: 100%; text-align: right;" value="<%= sp.getGhiChu() %>" name= "ghichusp" >
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
		dropdowncontent.init('<%= sp.getMa() %>', "left-top", 300, "click");
	<%} %>
</script>

<script type="text/javascript">
	replaces();
	jQuery(function()
	{		
		$("#nccId").autocomplete("Erp_KhachHangList.jsp");
	});	
</script>

<%
	pxkBean.DbClose();
	
	pxkBean = null;
	session.setAttribute("pxkBean", null);
	}
%>

</BODY>
</html>