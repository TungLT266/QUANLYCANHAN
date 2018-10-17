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
	int[] quyen = util.Getquyen("108",userId);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>Diageo - Project</TITLE>
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
 	document.forms['frm'].action="../../TonKhoStoreSvl";
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
	<form name="frm" method="post" action="../../TonKhoStoreSvl">
		<input type="hidden" name="userId" value='<%= userId %>'>
		<input type="hidden" name="msg" value='<%=obj.getMsg()%>'>
		<script language="javascript" type="text/javascript">
    		thongbao();
		</script>
		<input type="hidden" name="action" value='1'> <input type="hidden" name="userId" value='<%=obj.getUserId()%>'>
		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 70%; padding: 5px; float: left" class="tbnavigation">Nhập tồn kho store</div>
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
					<legend class="legendtitle">Nhập Tồn kho store</legend>
					<div style="width: 100%; float: none" align="left">
						<div style="width: 100%; float: none" align="left" class="plainlabel">
							<TABLE width="70%" cellpadding="6" cellspacing="0">
							</TABLE>
						</div>
					</div>
				</fieldset>
			</div>
		</div>
        
        <div style="width:100%; float:none" align="left">
        	<fieldset>
	        	<legend><span class="legendtitle"> Tồn kho store</span>&nbsp;&nbsp;&nbsp;
	    			<%if(quyen[0]!=0){ %>
	        		<a class="button3" href="javascript:newform()"> <img style="top: -4px;" src="../images/New.png" alt="">Tạo mới</a>
	            	<%} %>
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
	                     <%if(trangthai==0){ %>
	                     	<%if(quyen[2]!=0){ %>
	                     	<A href="../../TonKhoStoreUpdateSvl?userId=<%=userId%>&edit=<%=rsTonkho.getString("tonkhoId")%>"> <IMG src="../images/Edit.png" alt="Sửa" title="Sửa" border=0 width=20  height=20></A>&nbsp;
	                     	<%} %>	                       	                       
	                     <%}else { %>
	                     	<%if(quyen[3]!=0){ %>
	                     		<A href="../../TonKhoStoreSvl?userId=<%=userId%>&excel=<%=rsTonkho.getString("tonkhoId")%>"> <IMG src="../images/excel.gif" alt="Excel" title="Excel" border=0 width=20  height=20></A>&nbsp;
	                     	 	<A href="../../TonKhoStoreUpdateSvl?userId=<%=userId%>&display=<%=rsTonkho.getString("tonkhoId")%>"> <IMG src="../images/Display20.png" alt="Hiển thị" title="HienThi" border=0 width=20  height=20></A>&nbsp;
	                     	<%} %>
	                     <%} %>	 
	                    </TD>
	                </tr>
	                <%} }%>
	            </TABLE>
			</fieldset>
       </div>
	</form>

</body>
</html>

<% 
	if(rsTonkho != null) rsTonkho.close();
%>