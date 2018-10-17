<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.beans.nhiemvu.*" %>
<%@ page  import = "geso.traphaco.center.beans.nhiemvu.imp.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.util.List" %>
<% INhiemVu nvBean = (INhiemVu)session.getAttribute("nvBean"); %>

<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>Best - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">

	<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
	<LINK rel="stylesheet" href="../css/main.css" type="text/css">
   	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css"/>
  	<script type="text/javascript" src="../scripts/jquery.min.js"></script>
    <script type="text/javascript" src="..scripts/jquery-1.js"></script>
    <link type="text/css" rel="stylesheet" href="../css/mybutton.css">
    
    <LINK rel="stylesheet" type="text/css" href="../css/style.css" />
	<style type="text/css">
		#mainContainer{
			width:600px;
			margin:0 auto;
			text-align:left;
			height:100%;
			border-left:3px double #000;
			border-right:3px double #000;
		}
		#formContent{
			padding:5px;
		}
		/* END CSS ONLY NEEDED IN DEMO */
			
		/* Big box with list of options */
		#ajax_listOfOptions{
			position:absolute;	/* Never change this one */
			width:auto;	/* Width of box */
			height:200px;	/* Height of box */
			overflow:auto;	/* Scrolling features */
			border:1px solid #317082;	/* Dark green border */
			background-color:#C5E8CD;	/* White background color */
	    	color: black;
			text-align:left;
			font-size:1.0em;
			z-index:100;
		}
		#ajax_listOfOptions div{	/* General rule for both .optionDiv and .optionDivSelected */
			margin:1px;		
			padding:1px;
			cursor:pointer;
			font-size:1.0em;
		}
		#ajax_listOfOptions .optionDiv{	/* Div for each item in list */
			
		}
		#ajax_listOfOptions .optionDivSelected{ /* Selected item in the list */
			background-color:#317082; /*mau khi di chuyen */
			color:#FFF;
		}
		#ajax_listOfOptions_iframe{
			background-color:#F00;
			position:absolute;
			z-index:5;
		}
		form{
			display:inline;
		}
		#dhtmltooltip
		{
			position: absolute;
			left: -300px;
			width: 150px;
			border: 1px solid black;
			padding: 2px;
			background-color: lightyellow;
			visibility: hidden;
			z-index: 100;
			/*Remove below line to remove shadow. Below line should always appear last within this CSS*/
			filter: progid:DXImageTransform.Microsoft.Shadow(color=gray,direction=135);
		}	
		#dhtmlpointer
		{
			position:absolute;
			left: -300px;
			z-index: 101;
			visibility: hidden;
		}	
	</style>
    
	<script language="javascript" type="text/javascript">
	
	if(!String.prototype.trim) {
	  String.prototype.trim = function () {
		return this.replace(/^\s+|\s+$/g,'');
	  };
	}
	
	function changeDoiTuong() {
		var doituong = $("#doituongSlt");
		
		$("#loaitieuchiSR").hide(0);
		$("#loaitieuchiSS").hide(0);
		$("#loaitieuchiASM").hide(0);
		
		switch(doituong.val()) {
			case "SR":
				$("#loaitieuchiSR").show(0);
				break;
				
			case "SS":
				$("#loaitieuchiSS").show(0);
				break;
				
			case "ASM":
				$("#loaitieuchiASM").show(0);
				break;
				
		}
	}
	
	function changeIsTuDong() {
		var isTuDong = $("#istudong").attr("checked") ? "1" : "0"; //0 | 1
		
		if(isTuDong == "1") {
			$("#loaitieuchiTR").show(300);
		} else {
			$("#loaitieuchiTR").hide(200);
		}
	}
	
	function submitform()
	{   
	   document.forms["nvForm"].submit();
	}
	
	function saveform()
	{
		var tieuchi = $("#tieuchi").val().trim();
		
		if(tieuchi.length <= 0) {
			alert("Bạn chưa nhập tiêu chí!");
			return;
		}
		
		document.forms["nsptbForm"].action.value = "save";
		document.forms["nsptbForm"].submit();		
	}
	
</script>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="nsptbForm" method="post" action="../../NhiemVuUpdateSvl">
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value='1'>
<div id="main" style="width:99%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:55%; padding:5px; float:left" class="tbnavigation">
        	Quản lý chỉ tiêu > Nhiệm vụ > Tạo mới
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn <%= userTen %> &nbsp;
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<A href= "../../NhiemVuSvl?userId=<%= userId %>" >
        	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1px" longdesc="Quay ve" style="border-style:outset"></A>
        <A href="javascript:saveform()" >
        	<IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border ="1px" style="border-style:outset"></A>
    </div>
  	
  	<div align="left" style="width:100%; float:none; clear:left">
  		<fieldset>
    		<legend class="legendtitle"> Báo lỗi nhập liệu</legend>
    		<textarea name="dataerror"  style="width: 100% ; font-weight:bold" readonly rows="1"><%= nvBean.getMessage() %></textarea>
		    <% nvBean.setMessage(""); %>
    	</fieldset>
  	</div>
  	
    <div align="left" style="width:100%; float:none; clear:left">
    <fieldset>
    	<legend class="legendtitle"> Nhiệm Vụ </legend>
        	<div style="float:none; width:100%" align="left">
            <TABLE width="100%" cellpadding="6" cellspacing="0">							
                <TR>
                    <TD width="15%" class="plainlabel" valign="top">Tiêu chí <FONT class="erroralert"> *</FONT></TD>
                    <TD class="plainlabel" valign="top">
                    	<input type="text" name="tieuchi" id="tieuchi" value="<%= nvBean.getTieuChi() %>" size="40">
                    </TD>
                </TR> 						
              <TR>
                    <TD width="15%" class="plainlabel" valign="top">Diễn giải </TD>
                    <TD class="plainlabel" valign="top"><textarea name="diengiai" id="diengiai" style="width:500px;" rows="1"><%= nvBean.getDienGiai() %></textarea></TD>
                </TR> 
                
                <TR>
                    <TD class="plainlabel">Đối tượng<FONT class="erroralert"> *</FONT></TD>
                    <TD class="plainlabel">
                        <select name="doituong" id="doituongSlt" onChange="changeDoiTuong()">
                            <option value="SR">SR</option>
                            <option value="SS">SS</option>
                            <option value="ASM">ASM</option>
							<!--<option value="RSM">RSM</option>-->
                        </select>
                        <script>
                        var selected = "<%= nvBean.getDoiTuong() %>".trim();
							$("#doituongSlt option[value="+selected+"]").attr("selected", "selected");
						</script>
                     </TD>
                </TR>
                <TR>
                	<TD class="plainlabel">&nbsp;</TD>
                    <TD class="plainlabel">
						<input type="checkbox" name="istudong" id="istudong" <%= nvBean.getIsTuDong() ? "checked=\"checked\"" : "" %> onChange="changeIsTuDong();"/>
						<label for="istudong">Hệ thống tính</label>
                    </TD>
                </TR>
                <TR id="loaitieuchiTR">
                    <TD class="plainlabel">Loại tiêu chí<FONT class="erroralert"> *</FONT></TD>
                    <TD class="plainlabel">
                        <select name="loaitieuchiSR" id="loaitieuchiSR">
                            <option value="0">Viếng thăm</option>
                            <option value="1">6 đh/ngày</option>
                            <option value="2">Mở 5 điểm bán 1 tháng</option>
                            <option value="3">Bán Ra cao hơn tháng trước</option>
                        </select>
                        <select name="loaitieuchiSS" id="loaitieuchiSS">
                            <option value="0">Báo cáo</option>
                            <option value="1">Giải phóng hàng cận date</option>
                            <option value="2">Hoàn thành chỉ tiêu SKU In</option>
                            <option value="3">Chỉ tiêu trọng tâm tháng</option>
                            <option value="4">Bán Ra cao hơn tháng trước</option>
                            <option value="5">Mua Vào cao hơn tháng trước</option>
                            <option value="6">Tồn kho của các NPP</option>
                        </select>
                        <select name="loaitieuchiASM" id="loaitieuchiASM">
                            <option value="0">Báo cáo</option>
                            <option value="1">Giải phóng hàng cận date</option>
                            <option value="2">Hỗ trợ</option>
                            <option value="3">Hoàn thành chỉ tiêu SKU In</option>
                            <option value="4">Mở thêm Chi nhánh / Đối tác</option>
                            <option value="5">Bán Ra cao hơn tháng trước</option>
                            <option value="6">Mua Vào cao hơn tháng trước</option>
                        </select>
                        <script>
							changeDoiTuong();
							changeIsTuDong();
							var dt = $("#doituongSlt").val();
							var ltc = "<%= nvBean.getLoaiTieuChi() %>";
							$("#loaitieuchi"+ dt + " option[value="+ltc+"]").attr("selected", "selected");
						</script>
                     </TD>
                </TR>
                <TR>
                    <TD class="plainlabel">Trạng thái<FONT class="erroralert"> *</FONT></TD>
                    <TD class="plainlabel">
						<input type="checkbox" name="trangthai" id="trangthai" <%= nvBean.getTrangThai().equals("") || nvBean.getTrangThai().equals("1") ? "checked=\"checked\"" : "" %>/>
						<label for="trangthai">Hoạt động</label>
                    </TD>
                </TR>
                
                				
            </TABLE>
            <hr> 
            </div>
           
     </fieldset>	
    </div>
</div>

</form>
</BODY>
</html>