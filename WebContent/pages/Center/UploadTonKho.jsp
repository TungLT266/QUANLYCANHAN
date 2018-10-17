<%@page import="java.sql.ResultSet"%>
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
	
	ResultSet rsTonkho=(ResultSet)obj.getRsTonkho();
	int[] quyen = util.Getquyen("107",userId);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>OneOne - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">

<script type="text/javascript" src="../scripts/jquery-1.js"></script>
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

<script type="text/javascript" src="../scripts/report-js/action-report.js"></script>
<script language="javascript" type="text/javascript">
function upload()
{
 	document.forms['frm'].action="../../UploadTonKhoSvl";
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
function xuatExcel()
{
	document.forms['frm'].action.value= 'toExcel';
	document.forms['frm'].submit();
	document.forms['frm'].action.value= '';
}

</script>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="frm" method="post" action="../../UploadTonKhoSvl">
		<input type="hidden" name="userId" value='<%= userId %>'>
		<input type="hidden" name="msg" value='<%=obj.getMsg()%>'>
		<script language="javascript" type="text/javascript">
    		thongbao();
		</script>
		<input type="hidden" name="action" value='1'> <input type="hidden" name="userId" value='<%=obj.getUserId()%>'>
		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 70%; padding: 5px; float: left" class="tbnavigation">Upload tồn kho store</div>
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
					<legend class="legendtitle">Upload Tồn kho store</legend>
					<div style="width: 100%; float: none" align="left">
						<div style="width: 100%; float: none" align="left" class="plainlabel">
							<TABLE width="70%" cellpadding="6" cellspacing="0">
								<TR>
									<TD class="plainlabel">Tháng</TD>
									<TD class="plainlabel"><select name="thang" style="width: 150px">
											<option value=0></option>
											<%
												int k = 1;
												for (k = 1; k <= 12; k++) {
													if (k == Integer.parseInt(obj.getThang())) {
											%>
											<option value=<%=k%> selected="selected">
												<%=k%></option>
											<%
												} else {
											%>
											<option value=<%=k%>><%=k%></option>
											<%
												}
												}
											%>
									</select></TD>
								</TR>
								<TR>
									<TD width="15%" class="plainlabel">Năm &nbsp;&nbsp; <FONT class="erroralert"> </FONT></TD>
									<TD class="plainlabel"><select name="nam" style="width: 150px">
											<option value=0></option>
											<%
												Calendar cal = Calendar.getInstance();
												int year_ = cal.get(Calendar.YEAR);
												for (int n = 2012; n < year_ + 5; n++) {
													if (n == Integer.parseInt(obj.getNam())) {
											%>
											<option value=<%=n%> selected="selected">
												<%=n%></option>
											<%
												} else {
											%>
											<option value=<%=n%>><%=n%></option>
											<%
												}
												}
											%>
									</select></TD>
								</TR>
								<TR>
									<TD class="plainlabel">Chọn tập tin</TD>
									<TD class="plainlabel"><INPUT type="file" name="filename" size="40" value=''></TD>
								</TR>


								<TR>
									<%if(quyen[0]!=0){ %>
									<td colspan="1">
										<label id="btUpload">
											<a class="button" href="javascript:upload();"> <img style="top: -4px;" src="../images/button.png" alt=""> Upload </a>
										</label>
									</td>
									<%} %>	
									<%if(quyen[3]!=0){ %>
									<td colspan="1">
									<a class="button" href="javascript:excel();"> <img style="top: -4px;" src="../images/button.png" alt=""> File Import
									</a>
									&nbsp;&nbsp;&nbsp;&nbsp;
									<a class="button2" href="javascript:xuatExcel()"> <img style="top: -4px;" src="../images/button.png" alt="">Xuất Excel </a>
									</td>
									<%} %>
									
									 	
								</TR>
							</TABLE>
						</div>
					</div>
				</fieldset>
			</div>
		</div>
        
        <div style="width:100%; float:none" align="left">
        	<fieldset>
	        	<legend><span class="legendtitle"> Tồn kho store</span>&nbsp;&nbsp;&nbsp;
	    		
	        		<a class="button3" href="javascript:newform()" style="display:none">
	                <img style="top: -4px;" src="../images/New.png" alt="">Tạo mới</a>
	            
	        	</legend>
	            <TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
	                <TR class="tbheader">
	                    <TH align="center">Tháng</TH>
	                    <TH align="center">Năm</TH>
	                    <TH align="center">Người tạo</TH>
	                    <TH align="center">Ngày tạo</TH>
	                    <TH align="center">Trạng thái</TH>
	                    <TH align="center">Tác vụ</TH>
	                </TR>
	                <%
	                String tt="";
	                                    if (rsTonkho != null) 
	                                    {									
	                                        int m = 0;
	                                        while (rsTonkho.next()) 
	                                        {
	                                            int trangthai=rsTonkho.getInt("trangthai");
	                                            if(trangthai==0)
	                                                tt="Chưa chốt";
	                                            else if(trangthai==1)
	                                                tt="Đã chốt";
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
	                    <TD align="center"><%=rsTonkho.getString("thang")%></TD>
	                    <TD align="center"><%=rsTonkho.getString("nam")%></TD>
	                    <TD align="center"><%=rsTonkho.getString("nguoitao")%></TD>
	                    <TD align="center"><%=rsTonkho.getString("ngaytao")%></TD>
	                    <TD align="center"><%=tt%></TD>
	                    <TD align="center">
	                    <%if(quyen[3]!=0) {%>
	                        <A href="../../UploadTonKhoSvl?userId=<%=userId%>&excel=<%=rsTonkho.getString("tonkhoId")%>"> <IMG src="../images/excel.gif" alt="Excel" title="Excel" border=0 width=20  height=20></A>&nbsp;
	                      <%} %>
	                     	<%if(quyen[4]!=0){ %>
	                     	<%if(trangthai==0){ %>
	                        <A href="../../UploadTonKhoSvl?userId=<%=userId%>&chot=<%=rsTonkho.getString("tonkhoId")%>"> <IMG src="../images/Chot.png" alt="Chốt" title="Chốt" border=0 width=20  height=20></A>&nbsp;
	                        <%} }%>
	                        <%if(quyen[1]!=0){ %>
	                        	<A href="../../UploadTonKhoSvl?userId=<%=userId%>&delete=<%=rsTonkho.getString("tonkhoId")%>"> <IMG src="../images/Delete20.png" alt="Xóa" title="Xóa" border=0 width=20  height=20   ></A>&nbsp;
	                        <%} %>
	                        <%if(quyen[4]!=0){ %>
	                        <%if(trangthai==1){ %>
	                        	<A href="../../UploadTonKhoSvl?userId=<%=userId%>&unchot=<%=rsTonkho.getString("tonkhoId")%>"> <IMG src="../images/unChot.png" alt="Hiển thị" title="Bỏ chốt" border=0 width=20  height=20></A>&nbsp;
	                        <%} }%>
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