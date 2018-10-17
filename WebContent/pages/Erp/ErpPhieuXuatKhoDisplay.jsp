<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.NumberFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.SQLException"%>
<%@ page  import = "geso.traphaco.erp.beans.phieuxuatkhoTH.*" %>
<%@ page  import = "geso.traphaco.erp.beans.phieuxuatkhoTH.imp.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.util.List" %>
<% IErpPhieuxuatkho pxkBean = (IErpPhieuxuatkho)session.getAttribute("pxkBean"); %>
<% ResultSet khoList = pxkBean.getKhoList(); %>
<% ResultSet nppList = pxkBean.getNppList(); %>
<% //ResultSet nppList2 = pxkBean.getNppList2(); %>
<% ResultSet ndxList = pxkBean.getNdxList(); %>

<% ResultSet hdtcList = pxkBean.getHDTCList(); %>
<%  ResultSet ddhList = pxkBean.getRsDonhang(); %>

<% ResultSet nccList = pxkBean.getNccList(); %>
<% ResultSet trahangList = pxkBean.getTrahangList(); %>
<% List<ISanpham> spList = pxkBean.getSpList(); %>

<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen"); 
	NumberFormat formatter = new DecimalFormat("#,###,###.###"); 
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>TraphacoERP - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
	<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
	<LINK rel="stylesheet" href="../css/main.css" type="text/css">
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
	<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
	
	<script type="text/javascript" src="../scripts/jquery.min.js"></script>
	<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>
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

<script language="javascript" type="text/javascript">

$(document).ready(function() { $("#nppId").select2(); });
function saveform()
{
		 
	 	var tensp = document.getElementsByName("diengiai");
		var sanpham = document.getElementsByName("idhangmua");
		var soluonghoadon = document.getElementsByName("soluonghoadon");
		var soluongdaxuat = document.getElementsByName("soluongdaxuat");
		var soluongxuat = document.getElementsByName("soluong");
		
		for(var i = 0; i < sanpham.length; i++)
		{
			var slxuat=parseFloat(soluongxuat.item(i).value.replace(/,/g,"") );
			
			var slgCothexuat  = parseFloat(soluonghoadon.item(i).value.replace(/,/g,"") )-parseFloat(soluongdaxuat.item(i).value.replace(/,/g,"")) ;
			if(slgCothexuat >0 && slxuat==0 ){
				if(!confirm('Sản phảm '+tensp.item(i).value +' số lượng xuất =0, Bạn có tiếp tục muốn lưu phiếu xuất kho này?'))
				{
					return false;  
				}
			}
			
		}
	 document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho luu..' border='1' longdesc='cho luu..' style='border-style:outset'> Processing...</a>";
	 document.forms['nkForm'].action.value='save';
	 document.forms['nkForm'].submit();
}
function replaces()
{
	///TinhTongSoLuongXuat();
	setTimeout(replaces, 200);
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
function TinhTongSoLuongXuat()
{

		var sanpham = document.getElementsByName("idhangmua");
		 
		var soluonghoadon = document.getElementsByName("soluonghoadon");
		var soluongdaxuat = document.getElementsByName("soluongdaxuat");
		var soluongxuat = document.getElementsByName("soluong");
		
		for(var i = 0; i < sanpham.length; i++)
		{
			
			var slgCothexuat  = parseFloat(soluonghoadon.item(i).value.replace(/,/g,"") )- parseFloat(soluongdaxuat.item(i).value.replace(/,/g,"")) ;
			 
			
			if(sanpham.item(i).value != "")
			{
				var tongluong = 0;
				var id =  sanpham.item(i).value; 
				 
				var solo = document.getElementsByName(id + '.solo');
				var soluong = document.getElementsByName(id + '.soluong');
				var ngaysanxuat = document.getElementsByName(id  + '.ngaysanxuat');
				var ngayhethan = document.getElementsByName(id + '.ngayhethan');
				
				for( var j = 0; j < soluong.length; j++)
				{
					if(solo.item(j).value != "" && soluong.item(j).value != "")
					{

						var slg = soluong.item(j).value.replace(/,/g,"");
						tongluong = parseFloat(tongluong) + parseFloat(slg);
						 
						if(parseFloat(tongluong) > parseFloat(slgCothexuat))
						{
							tongluong = parseFloat(tongluong) - parseFloat(slg);
							
							var msg = 'Lô ' + solo.item(j).value + ' với số lượng ' + soluong.item(j).value + ' đã vượt quá tổng số lượng có thể xuất của hóa đơn';
							alert(msg);
						 
							soluong.item(j).value = "";
							 
						}
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
				
				soluongxuat.item(i).value = tongluong;
			}
		}
		
}
	 function sellectAll()
	 {
		 var chkAll = document.getElementById("chkAll");
		 var chonnpp = document.getElementsByName("chonnpp");
		 
		 if(chkAll.checked)
		 {
			 for(i = 0; i < chonnpp.length; i++)
			 {
				 chonnpp.item(i).checked = true;
			 }
		 }
		 else
		 {
			 for(i = 0; i < chonnpp.length; i++)
			 {
				 chonnpp.item(i).checked = false;
			 }
		 }
	 }
	 
	 function sellectAll_SO()
	 {
		 var chkAll = document.getElementById("chkAll_SO");
		 var chonddh = document.getElementsByName("chonddh");
		 
		 if(chkAll.checked)
		 {
			 for(i = 0; i < chonddh.length; i++)
			 {
				 chonddh.item(i).checked = true;
			 }
		 }
		 else
		 {
			 for(i = 0; i < chonddh.length; i++)
			 {
				 chonddh.item(i).checked = false;
			 }
		 }
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
//		 document.forms['nkForm'].action.value = 'submit';
		 document.forms['nkForm'].changeDdh.value = 'true';
	     document.forms["nkForm"].submit();
	 }
	 
	 function changeNdx()
	 { 
		 document.forms['nkForm'].action.value='changendx';
	     document.forms["nkForm"].submit();
	 }
	 
</script>

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { $("select").select2(); });
     
</script>
	
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="nkForm" method="post" action="../../ErpPhieuxuatkhoTHUpdateSvl">
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="id" value='<%= pxkBean.getId() %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="changeDdh" value='false'>

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:60%; padding:5px; float:left" class="tbnavigation">
        	Quản lý mua hàng > Nghiệp vụ khác > Phiếu xuất kho > Hiển thị
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn <%= userTen %> &nbsp;
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<A href= "../../ErpPhieuxuatkhoSvl?userId=<%= userId %>" >
        	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1px" longdesc="Quay ve" style="border-style:outset"></A>
    </div>
  	
  	<div align="left" style="width:100%; float:none; clear:left">
  		<fieldset>
    		<legend class="legendtitle"> Thông báo</legend>
    		<textarea name="dataerror"  rows="1" readonly="readonly" style ="width:100%"><%= pxkBean.getMsg() %></textarea>
    	</fieldset>
  	</div>
  	
    <div align="left" style="width:100%; float:none; clear:left">
    <fieldset>
    	<legend class="legendtitle"> Phiếu xuất kho </legend>
        	<div style="float:none; width:100%" align="left">
            <TABLE width="100%" cellpadding="4" cellspacing="0" border = '0'>
           		 <TR style="display:none" >
          	          <TD width="20%" class="plainlabel" valign="top">Loại xuất kho</TD>
                    <TD class="plainlabel" valign="top" colspan="3">
                    	<select name="loaixuatkho" onchange="LocChungTu()" style="width: 300px">
                    		 <% 
						   String[] mangid=new String[]{"HD","DH"};
             		  						   String[] mangten=new String[]{"Hóa đơn","Đơn đặt hàng"};
             		  
						   for(int k=0;k < mangid.length;k ++ ){
							   
							   if(pxkBean.getLoaixuatkho().equals(mangid[k])) {
								   %>
								    	<option value="<%=mangid[k] %>" selected="selected"><%=mangten[k] %> </option>
								   <%
							   }else{
								   %>
							    	<option value="<%=mangid[k] %>" ><%=mangten[k] %> </option>
							  		 <%  
							   }
						   }
							 %>
                    	</select>
                    </TD>
                </TR> 
                							
                <TR>
                    <TD width="20%" class="plainlabel" valign="top">Ngày xuất kho</TD>
                    <TD class="plainlabel" valign="top" colspan="3">
                    	<input type="text"  class="days" id="ngayxuatkho" name="ngayxuatkho" value="<%= pxkBean.getNgayxuatkho() %>" 
                    		maxlength="10" readonly />
                    </TD>
                </TR> 
                <TR style="display: none">
                    <TD width="120px" class="plainlabel" valign="top">Lý do xuất</TD>
                    <TD class="plainlabel" valign="top" colspan="3">
                    	<input type="text" id="lydoxuat" name="lydoxuat" value="<%= pxkBean.getLydoxuat() %>" />
                    </TD>
                </TR> 
                <TR>
                    <TD class="plainlabel">Nội dung xuất</TD>
                    <TD class="plainlabel" colspan="3">
                        <select name="noidungxuat" id="noidungxuat" onChange="changeNdx();" style="width: 400px;"  disabled="disabled">
                        	<option value=""> </option>
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
                <% if(pxkBean.getNdxId().length() > 0) { if(!pxkBean.getNdxId().equals("100062")){ %>
                
                <TR class="plainlabel" >
					<TD >Khách hàng </TD>
					<TD>
					<select name="nppId" id="nppId"  onchange="LocChungTu()" style="width: 300px;" >
					<option value=""> </option>
                        	<%
                        		if(nppList != null)
                        		{
                        			try
                        			{
                        			while(nppList.next())
                        			{  
                        			if( nppList.getString("PK_SEQ").equals(pxkBean.getNppId())){ %>
                        				<option value="<%= nppList.getString("PK_SEQ") %>" selected="selected" ><%= nppList.getString("Ten") %></option>
                        			<%}else { %>
                        				<option value="<%= nppList.getString("PK_SEQ") %>" ><%= nppList.getString("Ten") %></option>
                        		 <% } } nppList.close();} catch(SQLException ex){}
                        		}
                        	%>
                        	</select>
                        </TD>
                        <TD class="plainlabel" width = "70%"></TD>
						  
				</TR>
				 
			    <TR>
			    
			    	<%if(pxkBean.getLoaixuatkho().equals("HD")){ %>
                    <TD class="plainlabel" valign="top">Số chứng từ HĐ</TD>
                    <TD class="plainlabel" >
                        <select name="hdtcId" id="hdtcId" onchange="changeform()" style="width: 200px;" >
                        	<option value=""> </option>
                        	<%
                        		if(hdtcList != null)
                        		{
                        			try
                        			{
                        			while(hdtcList.next())
                        			{  
                        			if( hdtcList.getString("PK_SEQ").equals(pxkBean.getHDTCId())){ %>
                        				<option value="<%= hdtcList.getString("PK_SEQ") %>" selected="selected" ><%= hdtcList.getString("SOHOADON") %></option>
                        			<%}else { %>
                        				<option value="<%= hdtcList.getString("PK_SEQ") %>" ><%= hdtcList.getString("SOHOADON") %></option>
                        		 <% } } hdtcList.close();} catch(SQLException ex){}
                        		}
                        	%>
                        </select>
                     </TD>
                     <TD class="plainlabel" width = "70%"></TD>
                     
                    <%}else{ %>
                    	<TD class="plainlabel" valign="top">Số đơn đặt hàng</TD>
                    <TD class="plainlabel" >
                        <select name="ddhid" id="ddhid" onchange="changeform()" style="width: 200px;" >
                        	<option value=""> </option>
                        	<%
                        		if(ddhList != null)
                        		{
                        			try
                        			{
                        			while(ddhList.next())
                        			{  
                        			if( ddhList.getString("PK_SEQ").equals(pxkBean.getDDHId())){ %>
                        				<option value="<%= ddhList.getString("PK_SEQ") %>" selected="selected" ><%= ddhList.getString("SOHOADON") %></option>
                        			<%}else { %>
                        				<option value="<%= ddhList.getString("PK_SEQ") %>" ><%= ddhList.getString("SOHOADON") %></option>
                        		 <% } } ddhList.close();} catch(SQLException ex){}
                        		}
                        	%>
                        </select>
                     </TD>
                     <TD class="plainlabel" width = "70%"></TD>
                    <%} %>  
                </TR>  
                <%}else{ %>
                <TR>
                    <TD class="plainlabel" valign="top">Nhà cung cấp</TD>
                    <TD class="plainlabel" colspan="3">
                        <select name="nccId" id="nccId" onchange="LocChungTu()" style="width: 400px;"  disabled="disabled">
                        	<option value=""> </option>
                        	<%
                        		if(nccList != null)
                        		{
                        			try
                        			{
                        			while(nccList.next())
                        			{  
                        			if( nccList.getString("nccId").equals(pxkBean.getNccId())){ %>
                        				<option value="<%= nccList.getString("nccId") %>" selected="selected" ><%= nccList.getString("nccTen") %></option>
                        			<%}else { %>
                        				<option value="<%= nccList.getString("nccId") %>" ><%= nccList.getString("nccTen") %></option>
                        		 <% } } nccList.close();} catch(SQLException ex){}
                        		}
                        	%>
                        </select>
                     </TD> 
                </TR>  
                <TR>
                    <TD class="plainlabel" valign="top">Đơn trả hàng</TD>
                    <TD class="plainlabel" colspan="3" >
                        <select name="trahangId" id="trahangId" onchange="changeform()" style="width: 400px;" disabled="disabled">
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
                <%}} %>
                <tr>
                	<td class="plainlabel">Kho xuất</td>
                    <td class="plainlabel" colspan="3" >
                    	<select name="khoxuat" id="khoxuat" onChange="changeform();" style="width: 400px;" disabled="disabled" >
                        	<option value=""> </option>
                        	<%
                        		if(khoList != null)
                        		{
                        			try
                        			{
                        			while(khoList.next())
                        			{  
	                        			if( khoList.getString("pk_seq").equals(pxkBean.getKhoId())){ %>
	                        				<option value="<%= khoList.getString("pk_seq") %>" selected="selected" ><%= khoList.getString("ten") %></option>
	                        		  <%}else{ %>
	                        				<option value="<%= khoList.getString("pk_seq") %>" ><%= khoList.getString("ten") %></option>
	                        		 <% } 
                        			} khoList.close();} catch(SQLException ex){}
                        		}
                        	%>
                        </select>
                    </td>
                </tr>
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
                	<TH align="left" width="10%">&nbsp;&nbsp;Mã sản phẩm</TH>
                	<TH align="left" width="30%">&nbsp;&nbsp;Tên sản phẩm</TH>
                	<th align="left" width="15%">Số lượng HĐ </th>
                	<th align="left" width="15%">Số lượng đã xuất </th>
               	 	<TH align="center" width="10%">Số lượng xuất</TH>
               	 	<TH align="center" width="10%">Chọn lô xuất</TH>
                </TR>
                <%
                	for(int i = 0; i < spList.size(); i++)
                	{
                		ISanpham sp = spList.get(i);
	               		%>
	               		<tr>
           	 				<td align="left">
           	 					<input type="text" style="width: 100%; text-align: center;" value="<%= i+1 %>" name= "stt" readonly="readonly">
           	 				</td>
           	 				<td align="left">
           	 					<input type="text" style="width: 100%; text-align: left;" value="<%= sp.getMa() %>" name= "mahangmua" readonly="readonly" >
           	 					<input type="hidden" value="<%= sp.getId() %>" name= "idhangmua">
           	 				</td>
           	 				<td align="left">
           	 					<input type="text" style="width: 100%; text-align: left;" value="<%= sp.getDiengiai() %>" name= "diengiai" readonly="readonly" >
           	 				</td>
           	 				<td>
           	 				  <input type="text" style="width: 100%; text-align: right;" value="<%= sp.getSoluongTotal() %>" name= "soluonghoadon" readonly="readonly" >
           	 				</td>
           	 				<td>
           	 				  <input type="text" style="width: 100%; text-align: right;" value="<%= sp.getSoluongDaXuat() %>" name= "soluongdaxuat" readonly="readonly" >
           	 				</td>
           	 				
           	 				<td>
           	 				  <input type="text" style="width: 100%; text-align: right;" value="<%= sp.getSoluong() %>" name= "soluong" readonly="readonly" >
           	 				</td>
           	 				<td align="center">
           	 					<input type="hidden" name="bean" value="<%= sp.getIsBean() %>" readonly="readonly" />
	           	 				<a href="" id="<%=sp.getId()%>pubup_ton" rel="subcontent<%= i %>">
	           	 							<img alt="Số lô - Vị trí hàng hóa xuất" src="../images/vitriluu.png"></a>
	           	 				<DIV id="subcontent<%= i %>" style=" position:absolute; visibility: hidden; border: 9px solid #80CB9B;
				                             background-color: white; max-height:300px;overflow:auto ; width: 800px; padding: 4px;">
				                    <table width="90%" align="center">
				                        <tr>
				                        	<%if(pxkBean.getIsKhoXuatQuanLyKV().equals("1")) {%>
				                        	<th width="100px">Khu vực</th>
				                        	<%} %>
				                          <th width="100px">Số lô</th>
				                            <th width="100px">Mã mẻ</th>
				                            <th width="100px">Mã thùng</th>		
				                            <th width="100px">Mã marquette</th>			                            
				                            <th width="100px">Ngày hết hạn </th>
				                            <th width="100px">Ngày nhập kho </th>
				                            <th width="100px">Mã phiếu </th>
				                          
				                            <th width="100px">Phiếu định tính </th>
				                            
				                            <th width="100px">Phiếu EO </th>
				                              <th width="50px">Hàm lượng </th>
				                               <th width="50px">Hàm ẩm </th>
				                                <th width="100px">Nhà sản xuất </th>
				                            <th width="50px">SL tồn</th>
				                            <th width="100px">Số lượng </th>
				                        </tr>
				                        <%
				                        	List<ISpDetail> spDetailList = sp.getSpDetailList();
				                        	int stt = 1; 
				                        	if(spDetailList.size() > 0)
				                        	{
				                        		for(int sd = 0; sd < spDetailList.size(); sd ++)
				                        		{
				                        			ISpDetail spDetail = spDetailList.get(sd);
				                        		%>
				                        			<tr>
				                        				<%if(pxkBean.getIsKhoXuatQuanLyKV().equals("1")) {%>
				                        				<td>
				                        				
							                            	<input type="hidden" name="<%= sp.getId() + ".khu" %>" value="<%= spDetail.getKhuId() %>" readonly="readonly" />
							                            	<input type="text" size="100px" name="<%= sp.getId() + ".tenkhu" %>" value="<%= spDetail.getKhu() %>" readonly="readonly" /></td>
							                            <%} %>
							                            <td>
							                             <input type="hidden" size="100px" name="<%= sp.getId() + ".khochitietId" %>" value="<%= spDetail.getKhoChiTietId() %>" readonly="readonly" />
							                            <input type="hidden" size="100px" name="<%= sp.getId() + ".ngaysanxuat" %>" value="<%= spDetail.getNgaysanxuat() %>" readonly="readonly" />
							                            <input type="text" size="100px" name="<%= sp.getId() + ".solo" %>" value="<%= spDetail.getSolo() %>" readonly="readonly" /></td>
							                            <td>
							                            	<input type="text" size="100px" name="<%= sp.getId() + ".mame" %>" value="<%= spDetail.getMame() %>" readonly="readonly" />
							                            </td>
							                            <td>
							                            	<input type="text" size="100px" name="<%= sp.getId() + ".mathung" %>" value="<%= spDetail.getMathung() %>" readonly="readonly" />
							                            </td>
							                            <td>
							                            	<input type="text" size="100px" name="<%= sp.getId() + ".mamarquette" %>" value="<%= spDetail.getMamarquette() %>" readonly="readonly" />
							                            	<input type="hidden" name="<%= sp.getId() + ".idmarquette" %>" value="<%= spDetail.getIdmarquette() %>" />
							                            </td>
							                            
							                            <td>
							                            	<input type="text" size="100px" name="<%= sp.getId() + ".ngayhethan" %>" value="<%= spDetail.getNgayhethan() %>" readonly="readonly" /></td>
							                            	
							                            								                            <td>
							                            	<input type="text" size="100px" name="<%= sp.getId() + ".ngaynhapkho" %>" value="<%= spDetail.getNgaynhapkho() %>" readonly="readonly" /></td>
							                            								                            <td>
							                            	<input type="text" size="100px" name="<%= sp.getId() + ".maphieu" %>" value="<%= spDetail.getMaphieu() %>" readonly="readonly" /></td>
							                            								                            <td>
							                            	<input type="text" size="100px" name="<%= sp.getId() + ".maphieudinhtinh" %>" value="<%= spDetail.getMaphieudinhtinh() %>" readonly="readonly" /></td>
							                            	
							                            	<td>
							                            	<input type="text" size="100px" name="<%= sp.getId() + ".phieueo" %>" value="<%= spDetail.getPhieuEO() %>" readonly="readonly" /></td>
							                            	
							                            	<td>
							                            	<input type="text" size="100px" name="<%= sp.getId() + ".hamluong" %>" value="<%= spDetail.getHamluong() %>" readonly="readonly" /></td>
							                            	<td>
							                            	<input type="text" size="100px" name="<%= sp.getId() + ".hamam" %>" value="<%= spDetail.getHamam() %>" readonly="readonly" /></td>
							                            	
							                            	<td>
							                            	<input type="hidden" size="100px" name="<%= sp.getId() + ".idnsx" %>" value="<%= spDetail.getIdnsx() %>" readonly="readonly" />
							                            	<input type="text" size="100px" name="<%= sp.getId() + ".tennsx" %>" value="<%= spDetail.getTennsx() %>" readonly="readonly" />
							                            	</td>
							                            	
							                            	
							                            	
							                            <td>
							                            	<input type="text" size="50px"  name="<%= sp.getId() + ".soluongton" %>" value="<%=  spDetail.getSoluongton() %>" readonly="readonly" /></td>
							                             <td>
							                            	<input type="text" size="50px"  name="<%= sp.getId() + ".soluong" %>" value="<%= spDetail.getSoluong() %>"  
							                            		onKeyPress = "return keypress(event);" onchange ="replaces();" readonly/></td>
							                        </tr>
				                        		<%}
				                        	}
				                        %>
				                    </table>
				                     <div align="right"><a href="javascript:dropdowncontent.hidediv('subcontent<%= i %>');">Hoàn tất</a></div>
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
<script type="text/javascript" src="../scripts/loadingv2.js"></script>
<script type="text/javascript">
	//dropdowncontent.init('npp', "right-bottom", 300, "click");
	//dropdowncontent.init('ddh', "right-bottom", 300, "click");

		<% 
		for(int i = 0; i < spList.size(); i++)
		{
			ISanpham sp = spList.get(i);
		%>
			dropdowncontent.init("<%=sp.getId()%>pubup_ton", "left-top", 300, "click");
		<%} %>
		replaces();
</script>
</BODY>
</html>

<% 
	if(khoList != null) khoList.close();
	if(nppList != null) nppList.close();
	if(ndxList != null) ndxList.close(); 
	if(hdtcList != null) hdtcList.close();
	if(nccList != null) nccList.close();
	if(trahangList != null) trahangList.close();
	pxkBean.DBClose();
%>