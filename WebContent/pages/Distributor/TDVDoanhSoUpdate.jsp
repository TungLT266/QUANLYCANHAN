<%@page import="geso.traphaco.distributor.beans.tdvdoanhso.ITDVDoanhso"%>
<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<% ITDVDoanhso khBean = (ITDVDoanhso)session.getAttribute("khBean"); %>


<%
	ResultSet tbhRs = (ResultSet)khBean.getTbhRs()  ;
%>

<%
	ResultSet kh_dsrs = (ResultSet)khBean.getKh_DsRs()  ;
%>
<%
	ResultSet ddkdRs = (ResultSet)khBean.getDdkdRs()  ;
	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">

	<LINK rel="stylesheet" href="../css/datepicker.css" type="text/css">
    <script language="javascript" src="../scripts/datepicker.js"></script>
   	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css"/>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery.min.js"></script>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery-ui.min.js"></script>
	<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
	<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
	<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
	<script type="text/javascript" src="../scripts/phanTrang.js"></script>
	<script src="../scripts/ui/jquery.ui.datepicker.js"
		type="text/javascript"></script>
	<script type="text/javascript">
		$(document).ready(function() {		
				$( ".days" ).datepicker({			    
						changeMonth: true,
						changeYear: true				
				});            
	        }); 		
			
	</script>
   

<SCRIPT language="javascript" type="text/javascript">

function submitform()
 {   
    document.forms["khForm"].submit();
 }

 function saveform()
 { 
	
		
 	document.forms['khForm'].action.value='save';
 	var doanhso = document.getElementsByName("doanhso");
 	var kt = 0;
	for(i = 0; i < doanhso.length; i++)
	{
		if(doanhso.item(i).value == "")
		{
			document.forms['khForm'].dataerror.value = 'Vui lòng điền doanh số bổ sung';
			kt = 1;
			break;
		}
		else
		{
			if(isNaN(doanhso.item(i).value) == true)
			{
				document.forms['khForm'].dataerror.value = 'Doanh số bổ sung phải là số';
				kt = 1;
				break;
			}
		}
		
	}
	if(kt == 0)
	{
		document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";
		 document.forms['khForm'].submit();
	}
 }
 function showElement(id)
 {
 	var element = document.getElementById(id);
 	element.style.display = "";
 }

 function hideElement(id)
 {
 	var element = document.getElementById(id);
 	element.style.display = "none";
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
			if (keypressed == 8 || keypressed == 127 || keypressed == 37 || keypressed == 39 || keypressed == 0 || keypressed == 46 || keypressed == 45 )
			{//Phím Delete và Phím Back
				return;
			}
			return false;
		}
	}
 function SubmitAgain(){
	 var type= document.forms['khForm'].type.value;
	 if(type=="1")
	 {
		 if(!confirm('Địa chỉ khách hàng này đã bị trùng,Bạn muốn tiếp tục lưu khách hàng này?')) 
		 {
			 document.forms['khForm'].type.value="0";
			 return false;
		 }else
		 {
		 	saveform();
		 }
	 }
 }
 
</SCRIPT>
<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
    	$(document).ready(function() { 
    		$("select:not(.notuseselect2)").select2({ width: 'resolve' });     
    	});
    </script>

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="khForm" method="post" action="../../TdvdoanhsoupdateSvl">
		<input type="hidden" name="userId" value='<%=userId %>'> 
		<input type="hidden" name="nppId" value='<%= khBean.getNppId() %>'>
		<input type="hidden" name="action" value='1'> 
		<input type="hidden" name="type" value='<%=khBean.gettype()%>'>
		<input type="hidden" name="id" value='<%=khBean.getId()%>'>

		<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
			height="100%">
			<TR>
				<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="2">
						<TR>
							<TD align="left" class="tbnavigation">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr height="22">
										<TD align="left" colspan="2" class="tbnavigation">&nbsp;Thiết
											lập dữ liệu nền> Dữ liệu nền kinh doanh > Cộng tác viên bổ sung doanh số > Cập nhật
										<TD colspan="2" align="right" class="tbnavigation">Chào
											mừng <%= khBean.getNppTen() %>&nbsp;
									</tr>
								</table></TD>
						</TR>
					</TABLE>
					<TABLE width="100%" cellpadding="0" cellspacing="2">
						<TR>
							<TD>
								<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
									<TR class="tbdarkrow">
										<TD width="30" align="left"><A
											href="../../TdvdoanhsoSvl?userId=<%=userId %>"><img
												src="../images/Back30.png" alt="Quay ve" title="Quay ve"
												width="30" height="30" border="1" longdesc="Quay ve"
												style="border-style: outset">
										</A>
										</TD>
										<TD width="2" align="left"></TD>
										<TD width="30" align="left">
											<div id="btnSave">
												<A href="javascript:saveform()"><IMG
													src="../images/Save30.png" title="Luu lai" alt="Luu lai"
													border="1" style="border-style: outset">
												</A>
											</div></TD>
										<TD align="left">&nbsp;</TD>
									</TR>
								</TABLE></TD>
						</TR>
					</TABLE>
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
									<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>
									<textarea name="dataerror"
										style="width: 100%; color: #F00; font-weight: bold"
										style="width:100%" rows="1"><%=khBean.getMessage()%></textarea>
									<%
										khBean.setMessage("");
									%>
								</FIELDSET>
							</TD>
						</tr>

						<TR>
							<TD height="100%" width="100%">
								<FIELDSET>
									<LEGEND class="legendtitle" style="color: black">Thông
										tin khách hàng</LEGEND>
									<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
										
										<TR>
											<TD class="plainlabel">Tháng<FONT class="erroralert">
													*</FONT></TD>
											<TD class="plainlabel"><SELECT name="thang" readonly = "readonly"
												id="thang">
													<option value=""></option>
													<%
														try{
															String []thang = {"1","2","3","4","5","6","7","8","9","10","11","12"};
															int t = 0;
												while(t < thang.length){ 
												if(thang[t].equals(khBean.getThang())){
													%>
													<option value='<%=thang[t]%>' selected><%=thang[t]%></option>
													<%
														}else{
													%>
													<option value='<%=thang[t]%>'><%=thang[t]%></option>
													<%
														}
												t++;
												}}catch(Exception e ){e.printStackTrace();}
													%>
											</SELECT></TD>
											<TD class="plainlabel">Năm<FONT
												class="erroralert"> *</FONT>
											</TD>
											<TD class="plainlabel"><SELECT name="nam" id="TP" readonly = "readonly"
												>
													<option value=""></option>
													<%
													int nam = 2015;
														try{while(nam < 4000){ 
													if(nam == Integer.parseInt(khBean.getNam()))
													{
													%>
													<option value='<%=nam%>' selected><%=nam%></option>
													<%
														}else{
													%>
													<option value='<%=nam%>'><%=nam%></option>
													<%
														}
													nam++;
														}}catch(Exception e ){e.printStackTrace();}
													%>
											</SELECT></TD>

											
										</TR>


									

										

										<TR>
											


											<TD class="plainlabel">Cộng tác viên</TD>
											<TD class="plainlabel" colspan="4"> <SELECT name="ddkdId" readonly = "readonly"
												 >
													<option value=""></option>
													<%
														try{ while(ddkdRs.next()){ 
													if( khBean.getDdkdId().indexOf(ddkdRs.getString("ddkdId")) >=0){
													%>
													<option value='<%=ddkdRs.getString("ddkdId")%>' selected>
														<%=ddkdRs.getString("ddkdTen")%></option>
													<%
														}else{
													%>
													<option value='<%=ddkdRs.getString("ddkdId")%>'>
														<%=ddkdRs.getString("ddkdTen")%></option>
													<%
														}}
														
														}catch(java.sql.SQLException e){}
													%>
											</SELECT> </TD>
											
											
											
										</TR>


										
									
										
									
									</TABLE>
								</FIELDSET>
							</TD>
						</TR>
						

					</TABLE>


					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR>
							<TD colspan="6" width="100%">
								<FIELDSET>
									<LEGEND class="legendtitle">Khách hàng</LEGEND>
									<TABLE width="100%" border="0" cellspacing="1" cellpadding="6">
										<TR class="tbheader">
											<TH width="10%">Mã khách hàng</TH>
											<TH width="50%">Tên</TH>
										
											<TH width="20%">Doanh số bổ sung</TH>
<!-- 											<TH width="20%">Doanh số bổ sung</TH> -->
										
										</TR>

										<%
											int i = 0;
											String lightrow = "tblightrow";
											String darkrow = "tbdarkrow";					
										if(kh_dsrs != null){
										try{while(kh_dsrs.next()){ 
											if (i % 2 != 0) {
										%>
										<TR class=<%=lightrow%>>
											<%
												} else {
											%>
										
										<TR class=<%=darkrow%>>
											<%
												}
											%>
											<TD align="center">
											<input type="hidden" name="makh" value='<%=kh_dsrs.getString("makh") %>'>
											
												<%=kh_dsrs.getString("ma") %></TD>
												
												
											<TD align="center"><div align="left"><%=kh_dsrs.getString("ten") %>
												</div>
											</TD>

											<TD align="center">
											<input name='doanhso' type="text" value="<%=kh_dsrs.getString("doanhso") %>"onkeypress="return keypress(event);" maxlength="10"  style="width: 100%">
											</TD>
<!-- 											<TD align="center"> -->
<%-- 											<input name='doanhsobosung' type="text" value="<%=kh_dsrs.getString("doanhsobosung") %>"onkeypress="return keypress(event);" maxlength="10"  style="width: 100%"> --%>
<!-- 											</TD> -->

										</TR>
										<%
											i++;}}catch(java.sql.SQLException e)
										{e.printStackTrace();}
																}
										%>
										<tr class="tbfooter">
											<td colspan="4">&nbsp;</td>
										</tr>
									</TABLE>

								</FIELDSET>
							</TD>
						</TR>
					</TABLE>
</TD>
</TR>
</TABLE>

					
<script type="text/javascript">
	dropdowncontent.init('tenxuatHD', "left-top", 300, "click");
</script>
	</form>
</BODY>
</HTML>

<% 	

if(khBean != null){
	khBean.DBclose();
	khBean = null;
}

%>
<%}%>
