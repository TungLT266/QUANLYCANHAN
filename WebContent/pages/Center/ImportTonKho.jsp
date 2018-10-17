r<%@page import="java.sql.ResultSet"%>
<%@page import="geso.traphaco.center.beans.upload.IUpload"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.Date"%>
<%@page import="geso.traphaco.center.util.Utility" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	IUpload obj = (IUpload) session.getAttribute("obj");
	Utility util = new Utility();
	ResultSet npp=(ResultSet)obj.getRsNpp();
	ResultSet vungRs=(ResultSet)obj.getVungRs();
	ResultSet khuvuc=(ResultSet)obj.getKhuvucRs();
	ResultSet rsTonkho=(ResultSet)obj.getNppCoTonKhoRs();
	int[] quyen = new  int[6];
	quyen = util.Getquyen("ImportTonKhoSvl","",userId);
	Utility util1 = (Utility) session.getAttribute("util");
	String url="";
	url = util1.getUrl("ImportTonKhoSvl","");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>OneOne - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">


<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />

<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$(".days").datepicker({
			changeMonth : true,
			changeYear : true
		});
		$(".button").hover(function() {
			$(".button img").animate({
				top : "-10px"
			}, 200).animate({
				top : "-4px"
			}, 200) // first jump
			.animate({
				top : "-7px"
			}, 100).animate({
				top : "-4px"
			}, 100) // second jump
			.animate({
				top : "-6px"
			}, 100).animate({
				top : "-4px"
			}, 100); // the last jump
		});
	});
	$(document).ready(function() {
		$(".button1").hover(function() {
			$(".button1 img").animate({
				top : "-10px"
			}, 200).animate({
				top : "-4px"
			}, 200) // first jump
			.animate({
				top : "-7px"
			}, 100).animate({
				top : "-4px"
			}, 100) // second jump
			.animate({
				top : "-6px"
			}, 100).animate({
				top : "-4px"
			}, 100); // the last jump
		});
	});
</script>
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
$(document).ready(function()
{
	$("#nppMa").select2();
	$("#vungId").select2();
	$("#khuvucId").select2();
	$("#month").select2();
	$("#year").select2();
});
</script>



<script type="text/javascript" src="../scripts/report-js/action-report.js"></script>
<script language="javascript" type="text/javascript">
function upload()
{
 	document.forms['frm'].action="../../ImportTonKhoSvl";
	document.getElementById("btUpload").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";
	document.forms['frm'].setAttribute('enctype', "multipart/form-data", 0);
    document.forms['frm'].submit();
}
function thongbao(){
	var tt = document.forms['frm'].msg.value;
	if(tt.length>0)
    alert(document.forms['frm'].msg.value);
}


function excel()
{
 	document.forms['frm'].action.value="excel";
    document.forms['frm'].submit();
}

function newform()
{   
	document.forms["frm"].action.value = "Tao moi";
	document.forms["frm"].submit();
}


</script>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="frm" method="post" action="../../ImportTonKhoSvl">
		<input type="hidden" name="userId" value='<%= userId %>'>
		<input type="hidden" name="msg" value='<%=obj.getMsg()%>'>
		<script language="javascript" type="text/javascript">
    		thongbao();
		</script>
		<input type="hidden" name="action" value='1'> <input type="hidden" name="userId" value='<%=obj.getUserId()%>'>
		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 70%; padding: 5px; float: left" class="tbnavigation"><%=url %></div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng bạn
					<%=userTen%></div>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Báo lỗi nhập liệu</legend>
					<textarea id="errors" name="errors" rows="1" style="width: 100%">
						<%=obj.getMsg()%></textarea>
				</fieldset>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left; font-size: 0.7em">
				<fieldset>
					<legend class="legendtitle">Upload Tồn Kho</legend>
					<div style="width: 100%; float: none" align="left">
						<div style="width: 100%; float: none" align="left" class="plainlabel">
							<TABLE width="70%" cellpadding="6" cellspacing="0">
							<TR>
								<TD class="plainlabel">Miền</TD>
								<TD class="plainlabel">
										<select name="vungId" id="vungId" onchange="seach();" style="width:150px">
											<option value="" selected>All</option>
											<%
												if (vungRs != null)
													while (vungRs.next()) {
														if (vungRs.getString("vungId").equals(obj.getVungId()  )) {
											%>
											   <option value="<%=vungRs.getString("vungId")%>" selected><%=vungRs.getString("vungten")%></option>
											   <%
											   	} else {
											   %>
											   <option value="<%=vungRs.getString("vungId")%>"><%=vungRs.getString("vungten")%></option>
											<%
												}
													}
											%>
										</select>
										</TD>
								<TD class="plainlabel">Chi nhánh / Đối tác</TD>
								<TD class="plainlabel"><select name="nppMa" id="nppMa" style="width:200px;" >
											<option value="" selected>All</option>
											<%
												if (npp != null)
													while (npp.next()) {
														if (npp.getString("nppMa").equals(obj.getNppId()  )) {
											%>
											   <option value="<%=npp.getString("nppMa")%>" selected><%=npp.getString("nppMa") +"--"+npp.getString("nppTen")%></option>
											   <%
											   	} else {
											   %>
											   <option value="<%=npp.getString("nppMa")%>"><%=npp.getString("nppMa") +"--"+npp.getString("nppTen")%></option>
											<%
												}
													}
											%>
									</select></TD>
						   </TR>
						   
							<TR>									
								<TD class="plainlabel">Khu Vực</TD>
								<TD class="plainlabel">
									<select name="khuvucId" id="khuvucId" onchange="seach();" style="width:150px">
											<option value="" selected>All</option>
											<%
												if (khuvuc != null)
													while (khuvuc.next()) {
														if (khuvuc.getString("kvId").equals(obj.getKhuvucId()   )) {
											%>
											   <option value="<%=khuvuc.getString("kvId")%>" selected><%=khuvuc.getString("kvTen")%></option>
											   <%
											   	} else {
											   %>
											   <option value="<%=khuvuc.getString("kvId")%>"><%=khuvuc.getString("kvTen")%></option>
											<%
												}
													}
											%>
									</select>
									</TD>
						   </TR>
								<%-- 	<TR class="plainlabel">
											  <TD class="plainlabel">Ngày giao dịch </TD>
											  <TD class="plainlabel">                               
                                                 <input type="text" size="10" class="days" id="ngaykhoaso" name="ngaykhoaso" value="<%=obj.getNgaykhoaso()%>" maxlength="10"   /> 
											    </TD>
											    </TR> --%>
							<tr>
								<td class="plainlabel">Tháng</td>
								<td class="plainlabel">
									<select name="month" id="month"  style="width:150px">
										<option value="" selected="selected"></option>
										<option value="1">1</option>
										<option value="2">2</option>
										<option value="3">3</option>
										<option value="4">4</option>
										<option value="5">5</option>
										<option value="6">6</option>
										<option value="7">7</option>
										<option value="8">8</option>
										<option value="9">9</option>
										<option value="10">10</option>
										<option value="11">11</option>
										<option value="12">12</option>
								</td>
							</tr>
							<tr>	
								<td class="plainlabel">Năm</td>
								<td class="plainlabel">
									<select name="year" id="year"  style="width:150px">
										<option value="" selected="selected"></option>
										<option value="2013">2013</option>
										<option value="2014">2014</option>
										<option value="2015">2015</option>
										<option value="2016">2016</option>
								
								</td>
							</tr>				    
											    
							<TR>
									<TD class="plainlabel">Chọn tập tin</TD>
									<TD class="plainlabel"><INPUT type="file" name="filename" size="40" value=''></TD>
							</TR>
							<TR>
								 
									<td colspan="1">
										<label id="btUpload">
											<a class="button" href="javascript:upload();"> <img style="top: -4px;" src="../images/button.png" alt=""> Upload </a>
										</label>
									</td>

								</TR>
							</TABLE>
						</div>
					</div>
				</fieldset>
			</div>
		</div>
        
        <div style="width:100%; float:none" align="left">
        	<fieldset>
	        	<legend><span class="legendtitle"> Tồn kho</span>&nbsp;&nbsp;&nbsp;
	    		
	        		<a class="button3" href="javascript:newform()" style="display:none">
	                <img style="top: -4px;" src="../images/New.png" alt="">Tạo mới</a>
	            
	        	</legend>
	            <TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
	                <TR class="tbheader">
	                	<TH align="center">Mã Chi nhánh / Đối tác</TH>
	                    <TH align="center">Nhà phân phồi</TH>
	                    <TH align="center">Tác vụ</TH>
	                </TR>
	                <%
	                String tt="";
	                                    if (rsTonkho != null) 
	                                    {									
	                                        int m = 0;
	                                        while (rsTonkho.next()) 
	                                        {
	                                            if ((m % 2) == 0) {
	                                %>
	                <TR class='tbdarkrow'>
	                    <%
	                                        } else {
	                                    %>
	                
	                <TR class='tblightrow'>
	                    <%
	                                        }
	                                    %>
	                    <TD align="center"><%=rsTonkho.getString("nppMa")%></TD>
	                    <TD align="center"><%=rsTonkho.getString("nppTen")%></TD>
	                    <TD align="center">
	                        <A href="../../ImportTonKhoSvl?userId=<%=userId%>&excel=<%=rsTonkho.getString("nppId")%>"> <IMG src="../images/excel.gif" alt="Excel" title="Excel" border=0 width=20  height=20></A>&nbsp;
	                        <A href="../../ImportTonKhoSvl?userId=<%=userId%>&delete=<%=rsTonkho.getString("nppId")%>"> <IMG src="../images/Delete20.png" alt="Chốt" title="Xóa" border=0 width=20  height=20 onclick="if(!confirm('Bạn có muốn xóa tồn kho này?')) return false;"></A>&nbsp;
	                    </TD>
	                </tr>
	                <%   } }%>
	            </TABLE>
			</fieldset>
       </div>
	</form>

</body>
</html>

<% 
	if(rsTonkho != null) rsTonkho.close();
%>