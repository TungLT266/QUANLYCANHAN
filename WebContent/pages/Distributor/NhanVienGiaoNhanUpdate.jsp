<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.distributor.beans.nhanviengiaonhan.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>


<%@ page  import = "geso.traphaco.center.util.*" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
<% INhanviengiaonhan nvgnBean = (INhanviengiaonhan)session.getAttribute("nvgnBean"); %>
<% ResultSet ddkd = (ResultSet)nvgnBean.getDdkdList(); %>
<% ResultSet tbh = (ResultSet)nvgnBean.getTuyenbanhang(); %>
<% ResultSet kh = (ResultSet)nvgnBean.getKhList(); %>
<% ResultSet khSelected = (ResultSet)nvgnBean.getKhSelectedList(); %>

<% Hashtable<Integer, String> tbhIds = (Hashtable<Integer, String>)nvgnBean.getTbhIds(); %>
<% Hashtable<Integer, String> ddkdIds = (Hashtable<Integer, String>)nvgnBean.getDdkdIds(); %>
<% Hashtable<Integer, String> khIds = (Hashtable<Integer, String>)nvgnBean.getKhIds(); %>


<% ResultSet ttRs = (ResultSet)nvgnBean.getTtRs(); %>
<% ResultSet qhRs = (ResultSet)nvgnBean.getQhRs(); %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/jquery-1.js"></script>
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
    <script type="text/javascript">
        $(document).ready(function(){
            $(".button").hover(function(){
                $(".button img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
</script>
<SCRIPT language="javascript" type="text/javascript">
	function confirmLogout()
	{
	   if(confirm("Bạn có muốn đăng xuất ?"))
	   {
			top.location.href = "home.jsp";
	   }
	   return
	}
	function submitform()
	{
		document.forms['nvgnForm'].action.value='submitForm';
	   	document.forms['nvgnForm'].submit();
	}


	function changeDdkd()
	{
//		if(CheckDdkdList() == false)
//		{
//			alert("Vui long chon dai dien kinh doanh...");
//			return;
//		}
		document.forms['nvgnForm'].action.value='submitForm';
	   	document.forms['nvgnForm'].submit();
	}

	function changeTbh()
	{
//		if(CheckTbhList() == false)
//		{
//			alert("Vui long chon tuyen ban hang...");
//			return;
//		}
		document.forms['nvgnForm'].action.value='submitForm';
	   	document.forms['nvgnForm'].submit();
	}
	
	function saveform()
	{	  
		 var nvgnTen = document.getElementById("nvgnTen");
		 var diachi = document.getElementById("diachi");
		 var dienthoai = document.getElementById("dienthoai");
		 var ddkd = document.getElementById("ddkdTen");
		 
		 if(nvgnTen.value == "")
		 {
			// <link type="text/css" rel="stylesheet" href="../css/mybutton.css">
			alert("Vui lòng nhập tên nhân viên...");
			return;
		 }
		 if(diachi.value == "")
		 {
			 alert("Vui lòng nhập địa chỉ nhân viên...");
			return;
		 }
		 if(dienthoai.value == "")
		 {
			 alert("Vui lòng nhập số điện thoại nhân viên...");
			return;
		 }

//		 if(CheckKhList() == false)
//		 {
//			alert("Vui long chon khach hang...");
//			return;
//		 }
				 
		document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";
		 document.forms['nvgnForm'].action.value='save';
	     document.forms['nvgnForm'].submit();
	}
	
	function CheckKhList()
	{
		if(document.getElementsByName("khIds") == null)
			return false;
		var khList = document.getElementsByName("khIds");
		for(k=0; k < khList.length; k++)
		{
			if(khList.item(k).checked)
				return true;
		}
		return false;
	}
	
	function CheckTbhList()
	{
		if(document.getElementsByName("tbhIds") == null)
			return false;
		var tbhList = document.getElementsByName("tbhIds");
		for(j=0; j < tbhList.length; j++)
		{
			if(tbhList.item(j).checked)
				return true;
		}
		return false;
	}

	function CheckDdkdList()
	{
		if(document.getElementsByName("ddkdIds") == null)
			return false;
		var ddkdList = document.getElementsByName("ddkdIds");
		for(j=0; j < ddkdList.length; j++)
		{
			if(ddkdList.item(j).checked)
				return true;
		}
		return false;
	}
	
	function CheckAll()
	{
		var selectAll = document.getElementById("selectAll");
		var chon = document.getElementsByName("khIds");
		if(selectAll.checked)
		{
			for(i = 0; i < chon.length; i++)
				chon.item(i).checked = true;
		}
		else
		{
			for(i = 0; i < chon.length; i++)
				chon.item(i).checked = false;
		}
	}
	
	function UnCheckedAll()
	{
		var selectAll = document.getElementById("selectAll");
		selectAll.checked = false;
	}
	
	function CheckTbhAll()
	{
		var selectAll = document.getElementById("selectTbhAll");
		var chon = document.getElementsByName("tbhIds");
		if(selectAll.checked)
		{
			for(i = 0; i < chon.length; i++)
				chon.item(i).checked = true;
		}
		else
		{
			for(i = 0; i < chon.length; i++)
				chon.item(i).checked = false;
		}
	}
	
	function UnCheckedTbhAll()
	{
		var selectAll = document.getElementById("selectTbhAll");
		selectAll.checked = false;
	}
	
	function ajaxOption(id)
	{
		var nppId = document.getElementById("nppId").value;
		var str = '';
		var ddkdIds = document.getElementById("ddkdIds");
		for(i = 0; i < ddkdIds.options.length ; i++)
		{
			if(ddkdIds.options[i].selected)
				str += ddkdIds.options[i].value + ',';
		}
		
		var str2 = '';
		var tbhIds = document.getElementById("tbhIds");
		for(j = 0; j < tbhIds.options.length ; j++)
		{
			if(tbhIds.options[j].selected)
				str2 += tbhIds.options[j].value + ',';
		}
		
		var str3 = '';
		var ttId = document.getElementById("ttId");
		for(j = 0; j < ttId.options.length ; j++)
		{
			if(ttId.options[j].selected)
				str3 += ttId.options[j].value + ',';
		}
		
		var str4 = '';
		var qhId = document.getElementById("qhId");
		for(j = 0; j < qhId.options.length ; j++)
		{
			if(qhId.options[j].selected)
				str4 += qhId.options[j].value + ',';
		}
		
		
		
		//alert(str2);
		var xmlhttp;
		
		if (window.XMLHttpRequest)
		{// code for IE7+, Firefox, Chrome, Opera, Safari
		   xmlhttp = new XMLHttpRequest();
		}
		else
		{// code for IE6, IE5
		   xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		document.getElementById(id).innerHTML="";
		xmlhttp.onreadystatechange=function()
		{
		   if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
		   {
		      document.getElementById(id).innerHTML = xmlhttp.responseText;
		   }
		}
<%-- 		xmlhttp.open("GET","../../ajaxNhanVienGN?id=" + id + "&ddkdIds=" + str + "&tbhIds=" + str2 + "&nppId=" + nppId + "&update=0&nvgnId=<%=nvgnBean.getId()%>",true); --%>
		
		xmlhttp.open("GET","../../ajaxNhanVienGN?id=" + id + "&ddkdIds=" + str + "&ttId="+str3+"&qhId="+str4+"&tbhIds=" + str2 + "&nppId=" + nppId + "&update=0&nvgnId=<%=nvgnBean.getId()%>",true);
		
		xmlhttp.send();
	}
</SCRIPT>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name ="nvgnForm" method="post" action="../../NhanviengnUpdateSvl" >
<input type="hidden" name="nppId" id="nppId" value='<%= nvgnBean.getNppId() %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="id" value='<%= nvgnBean.getId() %>'>
<input name="userId" type="hidden" value='<%=userId %>' size="30">
<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:55%; padding:5px; float:left" class="tbnavigation">
        	Thiết lập dữ liệu nền > Dữ liệu nền kinh doanh > Nhân viên giao nhận > Cập nhật
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng  <%= nvgnBean.getNppTen() %> &nbsp; 
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<A href= "../../NhanviengiaonhanSvl?userId=<%=userId %>">		 		
	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" width="30" height="30" border="1" longdesc="Quay ve" style="border-style:outset"></A>
        <label id="btnSave">
        <A href="javascript:saveform()">
        	<IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A>
        	
        	</label>
    </div>
    
  	<div align="left" style="width:100%; float:none; clear:left">
  		<fieldset>
    		<legend class="legendtitle"> Báo lỗi nhập liệu</legend>
    		<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold" style="width: 100%" readonly="readonly" rows="1" ><%= nvgnBean.getMessage() %></textarea>
		         <% nvgnBean.setMessage(""); %>
    	</fieldset>
  	</div>
  	
    <div align="left" style="width:100%; float:none; clear:left">
    <fieldset>
    	<legend class="legendtitle">Thông tin nhân viên giao nhận  </legend>       	
        <div style="float:none; width:100%" align="left">
            <table width="100%" cellspacing="0" cellpadding="6px">
                <TR>
                    <TD width="20%" class="plainlabel" > Tên nhân viên <FONT class="erroralert"> *</FONT></TD>
                    <TD width="60%" class="plainlabel">
                        <INPUT type="text" name="nvgnTen" id="nvgnTen" size="40" value="<%= nvgnBean.getTennv()%>"></TD>
                    <TD class="plainlabel">Hoạt động
                        <input name="trangthai" type="checkbox" value="1" checked readonly="readonly" >
                    </TD>                                                                                     	                               
                </TR>
                <TR>
                    <TD class="plainlabel">Địa chỉ<FONT class="erroralert"> *</FONT></TD>
                    <TD colspan = "2" class="plainlabel">
                        <INPUT type="text" name="diachi" id="diachi" size="60" value="<%= nvgnBean.getDiachi()%>"></TD>
                </TR>
                <TR>
                     <TD class="plainlabel">Điện thoại <FONT class="erroralert"> *</FONT></TD>
                     <TD colspan="2" class="plainlabel">
                        <INPUT type="text" name="dienthoai" id="dienthoai" size="15" value="<%= nvgnBean.getDienthoai() %>"></TD>
                </TR>
             </table>
              
             <hr>
             <table width="100%" cellspacing="0px" cellpadding="4px">
              <TR class="plainlabel" valign="bottom">
              <td width="100%">
                <fieldset style="width: 40%; float: left;"> 
			       <legend>Tỉnh thành &nbsp;</legend> 
			       <select id="ttId" name="ttId" multiple="multiple" style="width: 100%; height: 200px" onChange = "ajaxOption('qhId')">
                        <% if(ttRs != null) {
                         while(ttRs.next()) 
                         {
                           if(nvgnBean.getTtId().indexOf(ttRs.getString("pk_seq")) >= 0 ){ %>
                             <option value="<%= ttRs.getString("pk_seq") %>" selected style="padding: 2px" ><%= ttRs.getString("ten") %></option>
                         <%}else { %>
                         	<option value="<%= ttRs.getString("pk_seq") %>" style="padding: 2px" ><%= ttRs.getString("ten")  %></option>
                         <%} }}%>        	
                    </select>
                    </fieldset>
                <fieldset style="width: 55%; float: right;">
			       <legend>Quận Huyện &nbsp;</legend> 
			       <select name="qhId" id="qhId" multiple="multiple" style="width: 100%; height: 200px"  onChange = "ajaxOption('khachhangIds')">
                        <% if(qhRs != null) {
                         while(qhRs.next()) 
                         {
                           if(nvgnBean.getQhId().indexOf(qhRs.getString("pk_seq")) >= 0 ){ %>
                             <option value="<%= qhRs.getString("pk_seq") %>" selected style="padding: 2px" ><%= qhRs.getString("ten")  %></option>
                         <%}else { %>
                         	<option value="<%= qhRs.getString("pk_seq") %>" style="padding: 2px"><%= qhRs.getString("ten") %></option>
                         <%} }}%>        	
                    </select>
                    </fieldset>
              <td>
              </TR>
             </table>  
             <hr>
             
             
             
             <hr>
             <table width="100%" cellspacing="0px" cellpadding="4px">
              <TR class="plainlabel" valign="bottom">
              <td width="100%">
                <fieldset style="width: 40%; float: left;"> 
			       <legend>Trình dược viên &nbsp;</legend> 
			       <select id="ddkdIds" name="ddkdIds" multiple="multiple" style="width: 100%; height: 200px" onChange = "ajaxOption('tbhIds')">
                        <% if(ddkd != null) {
                         while(ddkd.next()) 
                         {
                           if(nvgnBean.getDdkdId().indexOf(ddkd.getString("ddkdId")) >= 0 ){ %>
                             <option value="<%= ddkd.getString("ddkdId") %>" selected style="padding: 2px" ><%= ddkd.getString("ddkdTen") + " - " + ddkd.getString("dienthoai") %></option>
                         <%}else { %>
                         	<option value="<%= ddkd.getString("ddkdId") %>" style="padding: 2px" ><%= ddkd.getString("ddkdTen") + " - " + ddkd.getString("dienthoai") %></option>
                         <%} }}%>        	
                    </select>
                    </fieldset>
                <fieldset style="width: 55%; float: right;">
			       <legend>Tuyến bán hàng &nbsp;</legend> 
			       <select name="tbhIds" id="tbhIds" multiple="multiple" style="width: 100%; height: 200px"  onChange = "ajaxOption('khachhangIds')">
                        <% if(tbh != null) {
                         while(tbh.next()) 
                         {
                           if(nvgnBean.getTbhId().indexOf(tbh.getString("tbhId")) >= 0 ){ %>
                             <option value="<%= tbh.getString("tbhId") %>" selected style="padding: 2px" ><%= tbh.getString("ten") + " - " + tbh.getString("tbhTen") + " - " + tbh.getString("ngaylamviec") %></option>
                         <%}else { %>
                         	<option value="<%= tbh.getString("tbhId") %>" style="padding: 2px"><%= tbh.getString("ten") + " - " + tbh.getString("tbhTen") + " - " + tbh.getString("ngaylamviec") %></option>
                         <%} }}%>        	
                    </select>
                    </fieldset>
              <td>
              </TR>
             </table> 
             <hr>
             <div id="khachhangIds" style="width: 100%">
             <table width="100%" border="0" cellspacing="1" cellpadding="3px">
                    <tr class="tbheader">
                        <th width="150px" align="center">Mã khách hàng </th>
                        <th width="200px" align="left"> Họ tên </th>
                        <th width="400px" align="left">Địa chỉ </th>
                        <th width="150px" align="left">Điện thoại </th>
                        <th align="center">chọn <input type="checkbox" id="selectAll" onChange="CheckAll()"/></th>
                    </tr>
                    <% 
                    int n = 0;
                    if(khSelected != null){
                    	
					try{ 
						while(khSelected.next())
						{ %>
					<% if (n % 2 == 0){ %>
					<TR class= "tblightrow" >
					<%}else{ %>
					<TR class= "tbdarkrow" >
					<%} %>

						<TD align="center"><%= khSelected.getString("smartid") %></TD>
						<TD align="left"><%= khSelected.getString("khTen") %></TD>
						<TD align="left"><%= khSelected.getString("diachi") %></TD>
						<TD align="left"><%= khSelected.getString("dienthoai") %></TD>									
					    <TD align="center">
							<input name="khIds" type="checkbox" value ="<%= khSelected.getString("khId") %>" checked onChange="UnCheckedAll()">
						</TD>
 
                    </TR> <%
                    	n++;
						} %> 
				   	<% khSelected.close(); }catch(java.sql.SQLException e){} }%>   
				   </table>    	
			</div>
			
         </div>      
    </fieldset>	
    </div>
</div>
</form>
</body>
</html>


<% 	
	try{
		if(ddkd != null)
			ddkd.close();
		if(kh != null)
			kh.close();
		if(khSelected != null)
			khSelected.close();
		if(tbh != null)
			tbh.close();
		if(tbh != null)
			tbh.close();
		if(nvgnBean!=null){
		nvgnBean.DBclose();
		nvgnBean=null;
		}
		if(tbhIds!=null){
			tbhIds.clear();
		}
		if(ddkdIds!=null){
			ddkdIds.clear();
		}
		if(khIds!=null){
			khIds.clear();
		}
	}
	catch (SQLException e) {}

%>
<%}%>