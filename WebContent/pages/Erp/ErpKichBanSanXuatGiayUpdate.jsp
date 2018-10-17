<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="geso.traphaco.erp.beans.kichbansanxuatgiay.imp.*"%>
<%@ page import="geso.traphaco.erp.beans.kichbansanxuatgiay.*"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="geso.traphaco.center.util.*"%>
<%@page import="java.sql.SQLException"%>
<%
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if (!util.check(userId, userTen, sum))
	{
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	} else
	{
%>
<%
		IErpKichBanSanXuatGiay obj = (IErpKichBanSanXuatGiay) session.getAttribute("kbsxBean");
		ResultSet spRs = (ResultSet) obj.getSpRs();
		ResultSet rsNhaMay = (ResultSet) obj.getRsNhaMay();
		ResultSet rsVattu=(ResultSet)obj.getRsVattu();
		ResultSet rsCongdoan=(ResultSet  )obj.getRsCongdoan();
		List<IKichBan_CongDoanSx> lstCdsx = obj.getCongdoanSxList();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs.css">
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs-panes.css">
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
        $(document).ready(function() 
        {
        	$("#sanpham").select2(); 
        }
        );
        
        $(document).ready(function() 
                {
        			for(var i=0;i<10;i++)
        				{
        				$("#vattuId_"+i).select2();
        				$("#congdoanId_"+i).select2();
        				}
                }
                );

        
        
      //perform JavaScript after the document is scriptable.
        $(function() {
         // setup ul.tabs to work as tabs for each div directly under div.panes
         	$("ul.tabs").tabs("div.panes > div");
        });
      
       
      
      
</script>


        

<SCRIPT language="JavaScript" type="text/javascript">
	function submitform()
	{
	    document.forms["khtt"].submit();
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
		var error = document.getElementById("error");
		if(error.value != "")
		{
			alert(error.value);
		}
		else
		{
	  		document.forms["khtt"].action.value = "save";
		  	document.forms["khtt"].submit();
		}
    }
	
	function AjaxTINHSOLUONGSXBOM()
	{
		var spId = document.getElementById("sanpham");
		var soluongchuan = document.getElementById("soluongchuan");
    	var vattuId = document.getElementsByName("vattuId");
    	var thutu = document.getElementsByName("thutu");
    	var nhapkho = document.getElementsByName("nhapkho");
    	var param = "";
    	for(var i = 0; i < vattuId.length; i++)
   		{
    		if(vattuId.item(i).value != "" && nhapkho.item(i).checked)
    			param = param + thutu.item(i).value + '-' + vattuId.item(i).value + '--';
   		}
    	console.log("&spId=" + spId.value + "&soluongchuan=" + soluongchuan.value.replace(/,/g,"") + "&param=" + param);
		var xmlhttp;
		if (window.XMLHttpRequest)
		{
		  	xmlhttp=new XMLHttpRequest();
		}
		else
		{
		  	xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		
		xmlhttp.onreadystatechange=function()
		{
		  	if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
		  	{
		 	 	var soluong = document.getElementsByName("soluong");
		 	 	var error = document.getElementById("error");
				var kq = xmlhttp.responseText;
				console.log("::: kq : " + kq);
				if(parseInt(kq.indexOf("ERROR")) >= 0)
				{
					error.value = kq.substr(parseInt(kq.indexOf("--")) + 2);
					console.log("::: ERROR : " + error.value);
				}
				else
				{
					while(parseInt(kq.indexOf("][")) >= 0)
					{
						var _thutu = kq.substring(0, parseInt(kq.indexOf("-")));
						kq = kq.substr(parseInt(kq.indexOf("-")) + 1);
						var _vattu = kq.substring(0, parseInt(kq.indexOf("-")));
						kq = kq.substr(parseInt(kq.indexOf("-")) + 1);
						var _soluong = kq.substring(0, parseInt(kq.indexOf("][")));
						kq = kq.substr(parseInt(kq.indexOf("][")) + 2);
						soluong.item(_thutu - 1).value = _soluong;
						//console.log("tt=" + _thutu + "vt=" + _vattu + "sl=" + _soluong);
					}
					error.value = "";
				}
		  	}
		}
		xmlhttp.open("POST","../../AjaxTINHSOLUONGSXBOM?action=tinhsoluong&spId=" + spId.value + "&soluongchuan=" + soluongchuan.value.replace(/,/g,"") + "&param=" + param, true);
		xmlhttp.send();
	}
	
</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="khtt" method="post" action="../../ErpKichBanSanXuatGiayUpdateSvl">
		<input type="hidden" name="userId" value='<%=userId%>'> <input type="hidden" name="action" value="0">
		<input type="hidden" name="id" value="<%=obj.getId() %>">
		<input type="hidden" name="error" id="error" value="">
		<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
			<TR>
				<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<TR>
							<TD align="left" class="tbnavigation">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr height="22">
										<TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền > Sản xuất > Kịch bản sản xuất > Cập nhật</TD>
										<TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen%></TD>
									</tr>
								</table>
							</TD>
						</TR>
					</TABLE>
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<TR>
							<TD>
								<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
									<TR class="tbdarkrow">
										<TD width="30" align="left"><A href="../../ErpKichBanSanXuatGiaySvl?userId=<%=userId%>"><img src="../images/Back30.png"
												alt="Quay ve" title="Quay ve" border="1" longdesc="Quay ve" style="border-style: outset"></A></TD>
										<TD width="2" align="left"></TD>
										<TD width="30" align="left">
											<div id="btnSave">
												<A href="javascript: save()"><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border="1" style="border-style: outset"></A>
											</div>
										</TD>
										<TD>&nbsp;</TD>
									</TR>
								</TABLE>
							</TD>
						</TR>
					</TABLE>
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<tr>
							<TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
									<LEGEND class="legendtitle">Thông báo </LEGEND>
									<textarea name="dataerror" style="width: 100%; color: red; background-color: white; font-weight: bold" style="width: 100% ; color:#F00 ; font-weight:bold"
										readonly="readonly" rows="1"><%=obj.getMsg()%></textarea>
								</FIELDSET>
							</TD>
						</tr>
						<TR>
							<TD height="100%" width="100%">
								<FIELDSET>
									<LEGEND class="legendtitle" style="color: black">Thông tin dây chuyền sản xuất </LEGEND>
									<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
										<TR>
											<TD class="plainlabel" valign="middle" width="125px">Sản phẩm</TD>
											<TD class="plainlabel"><select name="sanpham" id="sanpham" style="width: 350px; text-align: left;">
													<option value=""></option>
													<%
														if (spRs != null)
															{
																while (spRs.next())
																{
																	if (obj.getSpSelected().equals(spRs.getString("pk_seq")))
																	{
													%>
													<option value="<%=spRs.getString("pk_seq")%>" selected="selected"><%=spRs.getString("ten")%>
														
													</option>
													<%
														} else
																	{
													%>
													<option value="<%=spRs.getString("pk_seq")%>" ><%=spRs.getString("ten")%>
													</option>
													<%
														}
																}
																spRs.close();
															}
													%>
											</select></TD>
										</TR>
										<TR>
											<TD class="plainlabel" valign="middle" width="120px" >Diễn giải</TD>
											<TD class="plainlabel" valign="middle"><input type="text" name="diengiai" value="<%=obj.getDiengiai()%>"></TD>
										</TR>
										<%-- <TR>
											<TD class="plainlabel" valign="middle" width="120px" >Phân xưởng </TD>
											<TD class="plainlabel" valign="middle"><select name="nhamayId" id="nhamayId" onchange="submitform();"> 
													<option value=""></option>
													<%
														if (rsNhaMay != null)
															{
																while (rsNhaMay.next())
																{
																	if (rsNhaMay.getString("pk_seq").equals(obj.getNhaMayId()))
																	{
													%>
													<option value="<%=rsNhaMay.getString("pk_seq")%>" selected="selected"><%=rsNhaMay.getString("Ten")%></option>
													<%
														} else
																	{
													%>
													<option value="<%=rsNhaMay.getString("pk_seq")%>"><%=rsNhaMay.getString("Ten")%></option>
													<%
														}
																}
																rsNhaMay.close();
															}
													%>
											</select></TD>
										</TR> --%>
										<TR>
											<TD class="plainlabel" valign="middle" width="120px" >Số lượng chuẩn</TD>
											<TD class="plainlabel" valign="middle">
												<input type="text" name="soluongchuan" id="soluongchuan" value="<%=obj.getSoluongchuan()%>" onchange="AjaxTINHSOLUONGSXBOM();"
												style="text-align: right;" onkeypress="return keypress(event);"></TD>
										</TR>
										<TR>
											<TD class="plainlabel" valign="middle" width="120px" >Số ngày sản xuất</TD>
											<TD class="plainlabel" valign="middle"><input type="text" name="songaysanxuat" value="<%=obj.getSongaysanxuat()%>"
												style="text-align: right;" onkeypress="return keypress(event);"></TD>
										</TR>
										
										<TR>
											<TD class="plainlabel" valign="middle" width="120px" >Hoạt động</TD>
											<TD class="plainlabel" valign="middle">
												<%
													if (obj.getTrangThai().equals("1"))
														{
												%> <input type="checkbox" checked="checked" name="trangthai" value="1"
												style="text-align: right;"> <%
												 	} else
												 		{
												 %> <input type="checkbox" name="trangthai" value="1"
																								style="text-align: right;"> <%
												 	}
												 %>
											</TD>
										</TR>
									</TABLE>
								</FIELDSET>
							</TD>
						</TR>
						<TR>
							<TD colspan="6">
								<ul class="tabs">
									<li><a href="#" class="legendtitle">Công đoạn sản xuất</a></li>
								</ul>
									<div>
										<table cellpadding="0px" cellspacing="1px" width="100%">
											<tr class="tbheader">
												<th width="30%"  align="center">Công đoạn</th>
												<th width="5%" align="center">Thời gian</th>
												<th width="5%" align="center" >Thứ tự</th>
												<th width="20%" align="center" >BOM</th>
												<th width="20%" align="center" >Số lượng</th>
												<th width="20%" align="center" >Nhập kho công đoạn</th>
											</tr>
											<%
												int count=0; 
												for (int pos = 0; lstCdsx.size() > pos; pos++)
													{
														IKichBan_CongDoanSx e = lstCdsx.get(pos);
											%>
											<tr>
												<td>
												  <select name ="congdoanId" style="width: 100%" id="congdoanId_<%=count %>" onchange="submitform();">
													<option></option>	
												  	<%if(rsCongdoan!=null){
												  		rsCongdoan.beforeFirst();
												  		while(rsCongdoan.next()){
												  			if(e.getId().equals(rsCongdoan.getString("PK_SEQ"))){
												  	%>
												  	<option value="<%=rsCongdoan.getString("PK_SEQ")%>" selected="selected"><%=rsCongdoan.getString("DienGiai") %></option>
												  	<%}else { %>
													<option value="<%=rsCongdoan.getString("PK_SEQ")%>"><%=rsCongdoan.getString("DienGiai") %></option>
												  	<%} }}%>
											  		</select>
												</td>
												<td><input type="text" value="<%=e.getThoiGian()%>" name="thoigian" style="width:100%;text-align:right;" onkeypress="return keypress(event);"></td>
												<td><input type="text" value="<%=e.getThuTu()%>" name="thutu" style="width:100%;text-align:right;" onkeypress="return keypress(event);"></td> 
												<td>
												  <select name ="vattuId"   id="vattuId_<%=count%>" style="width:100%" onchange="AjaxTINHSOLUONGSXBOM();">
													<option></option>	
												  	<%if(rsVattu!=null){
												  		rsVattu.beforeFirst();
												  		while(rsVattu.next()){
												  		if(e.getVattuId().equals(rsVattu.getString("PK_SEQ"))){
												  		%>
												 	 	<option value="<%=rsVattu.getString("PK_SEQ")%>" selected="selected"><%=rsVattu.getString("DienGiai") %></option>
												  	<%}else{ %>
												  		<option value="<%=rsVattu.getString("PK_SEQ")%>"><%=rsVattu.getString("DienGiai") %></option>
												  	<%} }}%>
											  		</select>
												</td>
												<td><input type="text" value="<%= e.getSoluong() %>" name="soluong" style="width:100%;text-align:right;" readonly></td>
												<td><input type="checkbox" name='nhapkho' <%= e.getNhapkho().equals("1") ? " checked " : "" %> value="<%= e.getId() %>" onchange="AjaxTINHSOLUONGSXBOM();"></td>
											</tr>
											<%
											count++;} 
												 while(count <= 10)
												{
													%>
											<tr>
												<td>
												  <select name ="congdoanId" style="width: 100%" id="congdoanId_<%=count %>" onchange="submitform();">
													<option></option>	
												  	<%if(rsCongdoan!=null){
												  		rsCongdoan.beforeFirst();
												  		while(rsCongdoan.next()){%>
												  	<option value="<%=rsCongdoan.getString("PK_SEQ")%>"><%=rsCongdoan.getString("DienGiai") %></option>
												  	<%} }%>
											  		</select>
												</td>
												<td><input type="text" name="thoigian"  style="width:100%;text-align:right;"   onkeypress="return keypress(event);"> </td>
												<td><input type="text" name="thutu" style="width:100%;text-align:right;"  onkeypress="return keypress(event);" > </td>
												<td>
												  <select name ="vattuId"   id="vattuId_<%=count%>" style="width:100%">
													<option></option>	
												  	<%if(rsVattu!=null){
												  		rsVattu.beforeFirst();
												  		while(rsVattu.next()){%>
												  	<option value="<%=rsVattu.getString("PK_SEQ")%>"><%=rsVattu.getString("DienGiai") %></option>
												  	<%} }%>
											  		</select>
												</td>
												<td><input type="text" value="" name="soluong" style="width:100%;text-align:right;" readonly></td>
												<td><input type="checkbox" name='nhapkho' value="1" ></td>
											</tr>
											<%	count++;}
											%>
										</TABLE>
									</div>
								
						</TR>
					</TABLE>
				</TD>
			</TR>
		</TABLE>
	</FORM>
</BODY>
</HTML>
<%
try{
	if (rsVattu != null)
		rsVattu.close();
	if (rsCongdoan != null)
		rsCongdoan.close();
	if (lstCdsx != null)
		lstCdsx.clear();
	if (spRs != null)
			spRs.close();
	if (rsNhaMay != null)
		rsNhaMay.close();
}catch (Exception ex)
{
	ex.printStackTrace();
}
finally
{
	if (obj != null)
	{
		obj.DbClose();
		obj = null;
	}
	session.removeAttribute("kbsxBean");	
}}
%>
