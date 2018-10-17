<%@page import="java.sql.ResultSet"%>
<%@page import="geso.traphaco.distributor.beans.report.Ireport"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	Ireport obj = (Ireport) session.getAttribute("obj");
	ResultSet kvlist = (ResultSet) obj.getKvRs();
	ResultSet vungRs = obj.getVungRs();
	ResultSet npp = (ResultSet) obj.getNppRs();
	String nppId = obj.getnppId();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>OneOne - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print"
	href="../css/impression.css">
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
			$( ".days" ).datepicker({			    
					changeMonth: true,
					changeYear: true				
			});
            $(".button").hover(function(){
                $(".button img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
		$(document).ready(function(){
            $(".button1").hover(function(){
                $(".button1 img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        });
    </script>
<script type="text/javascript">
	function submitform() 
	{
		if (document.getElementById("Sdays").value == "")
		{
			alert("Vui lòng chọn ngày bắt đầu");
			return;
		}
		if (document.getElementById("Edays").value == "") 
		{
			alert("Vui lòng chọn ngày kết thúc");
			return;
		}

		document.forms['frm'].action.value = 'tao';
		document.forms["frm"].submit();
	}
	function search() 
	{
		document.forms["frm"].submit();
	}
	
</script>
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
$(document).ready(function()
{
	$("#nppId").select2();
	$("#vungId").select2();
	$("#khuvucId").select2();
	$("#nhanhangId").select2();
	$("#chungloaiId").select2();
	$("#programs").select2();
	$("#dvkdId").select2();
	$("#gsbhId").select2();
	$("#kenhId").select2();
});
</script>


<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="frm" method="post" action="../../HoaDonTrungGianSvl">
		<input type="hidden" name="action" value='1'> 
		<input type="hidden" name="userId" value='<%=userId%>'>
		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 70%; padding: 5px; float: left" class="tbnavigation">Quản trị hệ thống  &#62; Hóa đơn trung gian</div>
				<div align="right" style="padding: 5px" class="tbnavigation"> Chào mừng bạn <%=userTen%></div>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Báo lỗi nhập liệu</legend>
					<textarea id="errors" name="errors" rows="1" style="width: 100%; color: #F00; font-weight: bold">
						<%=obj.getMsg() %>
					</textarea>
				</fieldset>
			</div>
			<div align="left"
				style="width: 100%; float: none; clear: left; font-size: 0.7em">
				<fieldset>
					<legend class="legendtitle">Hóa đơn trung gian</legend>
					<div style="width: 100%; float: none" align="left"
						class="plainlabel">
						<TABLE width="70%" cellpadding="6" cellspacing="0">
							<TR>
								<TD class="plainlabel">Từ ngày</TD>
								<TD class="plainlabel">
									<input type="text" name="Sdays" id="Sdays" class="days" value='<%=obj.gettungay()%>' /></TD>
								<TD class="plainlabel">Đến ngày</TD>
								<TD class="plainlabel">
									<input type="text" name="Edays" id="Edays" class="days" value='<%=obj.getdenngay()%>' />
								</TD>
							</TR>


							<TR>
								<TD class="plainlabel">Chi nhánh / Đối tác</TD>
								<TD class="plainlabel" >
								<SELECT name="nppId" id="nppId" style="width: 350px">
								<option value=""> All </option>
								<%if (npp != null) {
								try {
									String optionGroup = "";
									String optionName = "";
									int i = 0;
									while (npp.next()) 
									{
										if (i == 0) 
										{
											optionGroup = npp.getString("kvTen");
											optionName = npp.getString("kvTen");%>
											<optgroup label="<%=optionName%>">
											<%}
											optionGroup = npp.getString("kvTen");
										if (optionGroup.trim().equals(optionName.trim())) 
										{%>
											<%if (npp.getString("nppMa").equals(nppId)) {%>
											<option value="<%=npp.getString("nppId")%>"
											selected="selected"><%=npp.getString("nppTen")%></option>
											<%} else {%>
											<option value="<%=npp.getString("nppMa")%>"><%=npp.getString("nppTen")%></option>
										<%}%>
											<%} else {%>
										</optgroup>
											<%optionName = optionGroup;%>
											<optgroup label="<%=optionName%>">
											<%if (npp.getString("nppMa").equals(nppId)) {%>
											<option value="<%=npp.getString("nppMa")%>"
												selected="selected"><%=npp.getString("nppTen")%></option>
											<%} else {%>
											<option value="<%=npp.getString("nppMa")%>"><%=npp.getString("nppTen")%></option>
											<%}%>
											<%}
										i++;
									}%>
														</optgroup>
														<%npp.close();
								} catch (java.sql.SQLException e) {
								}
							}%>
								</SELECT></TD>
							</TR>
							
							<TR>
								<td colspan="4"><a class="button"
									href="javascript:submitform();"> <img style="top: -4px;"
										src="../images/button.png" alt=""> Tạo báo cáo
								</a></td>
							</TR>
						</TABLE>
					</div>

				</fieldset>
			</div>
		</div>
		<br />
	</form>
</body>
</html>