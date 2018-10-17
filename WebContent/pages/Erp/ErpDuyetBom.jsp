<%@page import="java.sql.SQLException"%>
<%@ page import="java.sql.ResultSet"%>
<%@page import="geso.traphaco.erp.beans.duyetbom.*"%>
<%@page import="geso.traphaco.erp.beans.duyetbom.imp.*"%>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
<%
	IErpDuyetbom duyetbom = (IErpDuyetbom) session.getAttribute("duyetbom");
	ResultSet chungloaiRs = duyetbom.getChungloaiRs();
	ResultSet spRs = duyetbom.getSanphamRs();
	int[] quyen = new  int[5];
	quyen = util.Getquyen("ErpDuyetbomSvl","&loaiNPP=0&isKH=0",userId);	
	int m = 0;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>Duyệt bom</title>
<link rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<script type="text/javascript" src="../scripts/ajax.js"></script>
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<link media="screen" rel="stylesheet" href="../css/colorbox.css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
    $(document).ready(function() { 
     $(".select2").select2();
 });
</script>
<script src="../scripts/colorBox/jquery.colorbox.js"></script>
<script language="javascript" type="text/javascript">
	$(document).ready(function()
    {
		<%for(int i=0;i<1000;i++){%>
			$(".bomInit<%= i %>").colorbox({width:"70%", height:300, inline:true, href:"#subcontent<%= i%>"});
	        //Example of preserving a JavaScript event for inline calls.
	        $("#click").click(function(){ 
	            $('#click').css({"background-color":"#f00", "color":"#fff", "cursor":"inherit"}).text("Duyệt BOM - Project.");
	            return false;
	        });
		<%}%>
	});
	function Submit() {
		document.forms['kvkListForm'].submit();
	}
	function clearform()
	{ 
		document.forms['kvkListForm'].diengiai.value = "";
		document.forms['kvkListForm'].tensanpham.value = "";
		document.forms['kvkListForm'].chungloai.value = "";

		document.forms['kvkListForm'].submit();
	}
	function thongbao()
	{
	 var tt = document.forms["kvkListForm"].msg.value;
		 if(tt.length>0)
	    	alert(document.forms["kvkListForm"].msg.value);
	}
	function AjaxDuyet(spma, vitri)
	{
		console.log("vao ajax");
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
	 			var idTableSp =document.getElementById("subcontent"+vitri);
			 	if(spma != '')
				{
				 	idTableSp.innerHTML = xmlhttp.responseText;
				}
		  	}
		};
		
		xmlhttp.open("POST","../../AjaxDuyetbom?action=AjaxDuyet&iddmvt="+spma+"&vt="+vitri, true);
		
		xmlhttp.send();
	}
	function save(spma){
		document.forms['kvkListForm'].spma.value = spma;
		document.forms['kvkListForm'].action.value = 'save';
		document.forms['kvkListForm'].submit();
	}
	function boduyet(spma){
		document.forms['kvkListForm'].spma.value = spma;
		document.forms['kvkListForm'].action.value = 'boduyet';
		document.forms['kvkListForm'].bomid.value = '';
		document.forms['kvkListForm'].submit();
	}
	
	function radioClick(radio){
		//alert(radio.value);
		document.forms['kvkListForm'].bomid.value = radio.value;
	}
</script>
</head>
<body leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0" onload="thongbao()">
	<form name="kvkListForm" method="post" action="../../ErpDuyetbomSvl">
		<input type="hidden" name="userId" value='<%=userId%>'> 
		<input type="hidden" name="userTen" value='<%=userTen%>'> 
		<input type="hidden" name="action" value='1'>
		<input type="hidden" name="spma" value=''>
		<input type="hidden" name="bomid" value=''>
		<input type="hidden" name="msg" value='<%= duyetbom.getMsg()%>'>
		
		<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
			<tr>
				<td colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
					<table width="100%" cellpadding="0" cellspacing="1">
						<tr>
							<td align="left">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr height="22">
										<td align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền > Sản xuất > Duyệt BOM</td>
										<td align="right" colspan="2" class="tbnavigation">Chào mừng bạn <%=userTen%>&nbsp;
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td>
								<table border="0" width="100%" cellpadding="0" cellspacing="0">
									<tr>
										<td width="100%" align="left">
											<fieldset>
												<legend class="legendtitle">Tiêu chí tìm kiếm &nbsp;</legend>
												<table width="100%" cellpadding="6" cellspacing="0">
													 <TR>
						                             	<TD width="10%" class="plainlabel" >Sản phẩm </TD>
														<TD width="25%"  class="plainlabel">
															<input type="text" name="tensanpham" value="<%=duyetbom.getTenSanPham() %>" size="20" onchange=Submit()>
														</TD>
														<TD width="10%" class="plainlabel" >Diễn giải </TD>
														<TD class="plainlabel">
															<input type="text" name="diengiai" value="<%=duyetbom.getDienGiai() %>" size="20" onchange=Submit()>
														</TD>
						                             </TR> 
													<tr>
														<td class="plainlabel">Chủng loại
														</td>
														<TD  class="plainlabel"  colspan="3"> 
															<select  style="width:200px"  name="chungloai" id="chungloai"  class="select2" onchange="Submit()" >
																	<option value=""></option>
																	<%if (chungloaiRs != null)
																		while (chungloaiRs.next()) {
																			if (duyetbom.getChungloaiId().equals(
																					chungloaiRs.getString("PK_SEQ"))) {%>
																			<option value="<%=chungloaiRs.getString("PK_SEQ")%>" selected="selected"><%=chungloaiRs.getString("Ten")%></option>
																			<%} else {%>
																			<option value="<%=chungloaiRs.getString("PK_SEQ")%>"><%=chungloaiRs.getString("Ten")%></option>
																			<%}
																		}%>
															</select>
														</td>
														
													</tr>
													<tr class="plainlabel"> 
					                             	<td colspan="4" > 
					                           			<a class="button2" href="javascript:clearform()">
															<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;
													</td> 
					                             </tr>
													
												</table>
											</fieldset>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td width="100%">
								<fieldset>
									<table width="100%" border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td align="left" colspan="4">
												<table width="100%" border="0" cellspacing="1" cellpadding="4">
													<tr class="tbheader">
														<th width="10%" align="center">Mã sản phẩm</th>
														<th width="20%" align="center">Tên sản phẩm</th>
														<th width="10%" align="center">Duyệt ưu tiên</th>
													</tr>
													<%m = 0;
													String lightrow = "tblightrow";
													String darkrow = "tbdarkrow";
													if (spRs != null)
														while (spRs.next()) {															
															if (m % 2 != 0) {%>
													<tr class=<%=lightrow%>>
														<%} else {%>
													
													<tr class=<%=darkrow%>>
														<%}%>
														<td align="center"><%=spRs.getString("MA")%></td>
														<td align="left"><%=spRs.getString("TEN")%></td>
														<td align="center">
															<a class = "bomInit<%= m %>" href="#" onclick ="AjaxDuyet('<%=spRs.getString("iddmvt")%>',<%= m %> )">
		                        								<img style="top: -4px;" src="../images/vitriluu.png" title="Chọn BOM ưu tiên"></a>
		                        							<div style='display:none;'>
			                        							<DIV id="subcontent<%= m%>" style='padding:0px 5px; background:#fff;'>
			                    	                    			<h4 align='left'>Duyệt ưu tiên</h4>
												                </DIV>
												            </div>
														</td>
													</tr>
													<%m++;
												}%>
													<tr>
														<td height="26" colspan="11" align="center" class="tbfooter">&nbsp;</td>
													</tr>
												</table>
											</td>
										</tr>
									</table>
								</fieldset>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
<%
	if(spRs != null) spRs.close(); 
	if(chungloaiRs != null) chungloaiRs.close();

	duyetbom.Close();
	session.setAttribute("duyetbom", null) ; 
	
}	
%>

