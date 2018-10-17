<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.beans.upload.*" %>
<%@ page  import = "geso.traphaco.center.beans.upload.imp.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.util.List" %>
<% IUpload uploadBean = (IUpload)session.getAttribute("uploadBean"); %>

<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  %>

<% String spnppStr = uploadBean.getSpNpp(); 
	if(spnppStr == null) spnppStr = "";	
	else spnppStr = spnppStr.trim();
%>

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
    <script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
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
	
	//Danh sach san pham
	var spStr = "<%=spnppStr %>".trim();
	var npps = [];
	var spNpps = "";
	if(spStr.length !== 0) {
		//Danh sach load tu session truoc do
		spNpps = spStr;
	} else {
		//Danh sach npp + cac san pham + ton dau sp do
		spNpps = "<%=uploadBean.getSpNpp() %>";
	}
	
	spNpps = spNpps.split(";");
	for(var i = 0; i < spNpps.length; i++) {
		if(spNpps[i].length > 0) {
			var npp = {};
			var str = spNpps[i].split("#");
			var nppInfo = str[0].split(",");
			var spInfos = str[1].split("|");
			npp.id = nppInfo[0];
			npp.ten = nppInfo[1];
			npp.sps = [];
			for(var j = 0; j < spInfos.length; j++) {
				if(spInfos[j].length > 0) {
					npp.sps.push(spInfos[j].split(","));
				}
			}
			npps.push(npp);
		}
	}
	
	
	
	if(!String.prototype.trim) {
		  String.prototype.trim = function () {
			return this.replace(/^\s+|\s+$/g,'');
		  };
	}
	
	function tinhBan(nppId, spId) {
		var id = nppId + "_" + spId; 
		
		var openId = "sanpham_"+nppId+"_"+spId+"_open";
		var inId = "sanpham_"+nppId+"_"+spId+"_in";
		var closeId = "sanpham_"+nppId+"_"+spId+"_close";
		var outId = "sanpham_"+nppId+"_"+spId+"_out";
		
		var openValue = parseInt(document.getElementById(openId).value);
		var inValue = parseInt(document.getElementById(inId).value);
		var closeValue = parseInt(document.getElementById(closeId).value);
		var outValue = openValue + inValue - closeValue;
		if(isNaN(outValue)) {
			outValue = "";
		} else if(outValue < 0) {
			$("#" + inId).css("color", "red");
			$("#" + closeId).css("color", "red");
			$("#" + outId).css("color", "red");
			
		} else {
			$("#" + inId).css("color", "black");
			$("#" + closeId).css("color", "black");
			$("#" + outId).css("color", "black");
		}
		
		document.getElementById(outId).value = outValue;
	}
	
	/* function tinhBan(nppId, spId) {
		var openId = "sanpham_"+nppId+"_"+spId+"_open";
		var inId = "sanpham_"+nppId+"_"+spId+"_in";
		var closeId = "sanpham_"+nppId+"_"+spId+"_close";
		var outId = "sanpham_"+nppId+"_"+spId+"_out";
		
		var openValue = parseInt(document.getElementById(openId).value);
		var inValue = parseInt(document.getElementById(inId).value);
		var closeValue = parseInt(document.getElementById(closeId).value);
		var outValue = openValue + inValue - closeValue;
		if(isNaN(outValue)) {outValue = "";}
		
		document.getElementById(outId).value = outValue;
	} */
	
	function checkFail() {
		for(var i = 0; i < npps.length; i++) {
			var _npp = npps[i];
			for(var j = 0; j < _npp.sps.length; j++) {
				var _sp = _npp.sps[j];
				
				var _open = parseInt(_sp[3]);
				var _in = parseInt(document.getElementById("sanpham_"+_npp.id+"_"+_sp[0]+"_in").value.trim());
				var _close = parseInt(document.getElementById("sanpham_"+_npp.id+"_"+_sp[0]+"_close").value.trim());
				
				if(_open + _in - _close < 0) {
					return false;
				}
				
			}
		}
		return true;
	}
	
	
	function getNppString() {
		var result = "";
		for(var i = 0; i < npps.length; i++) {
			//Headers for tab
			var _npp = npps[i];
			var currentNppId = _npp.id;
			var currentNppTen = _npp.ten;
			
			var str = currentNppId + "," + currentNppTen;
			if(_npp.sps.length > 0) {
				str += "#";
			}
			result += str;
			
			for(var j = 0; j < _npp.sps.length; j++) {
				var _sp = _npp.sps[j];
				str = _sp[0] + "," + _sp[1] + "," + _sp[2] + "," + _sp[3] + "," + document.getElementById("sanpham_"+_npp.id+"_"+_sp[0]+"_in").value.trim() + "," + document.getElementById("sanpham_"+_npp.id+"_"+_sp[0]+"_close").value.trim();
				if(j < _npp.sps.length -1) {
					str +="|";
				}
				result += str;
			}
			
			if( i < npps.length - 1) {
				result += ";";
			}
		}
		return result;
	}
	
	function submitform()
	{   
	   document.forms["nsptbForm"].submit();
	}
	
	function checking()
	{
		/* document.forms["nsptbForm"].spnppList.value = getNppString();
		document.forms["nsptbForm"].action.value = "checking";
		document.forms["nsptbForm"].submit(); */
	}
	
	function saveform()
	{
		if(!checkFail()) {
			alert("Tồn tại sản phẩm có số tồn cuối lớn hơn số lượng nhập! Không thể lưu!");
			return;
		} else {
			document.forms["nsptbForm"].spnppList.value = getNppString();
			document.forms["nsptbForm"].action.value = "save";
			document.forms["nsptbForm"].submit();
		}

	}
	
	$(function() {
		
		var html = "<ul>";
		var i;
		for(i = 0; i < npps.length; i++) {
			//Headers for tab
			var _npp = npps[i];
			html += " <li><a href=\"#nhaphanphoi_"+_npp.id+"\">"+_npp.ten+"</a></li>";
		}
		html += "</ul>";
		
		//Details
		for(i = 0; i < npps.length; i++) {
			var spsHtml = "";
			var _npp = npps[i];
			for(var j = 0; j < _npp.sps.length; j++) {
				var _sp = _npp.sps[j];
				var spHtml = 
					"<tr>" +
					"	<td><input id=\"sanpham_"+_npp.id+"_"+_sp[0]+"_id\" sp=\""+_sp[0]+"\" npp=\""+_npp.id+"\" xx=\"id\" style=\"width:100%; font-size: 12px;\" type=\"text\" value=\"[" + _sp[1] + "] " + _sp[2] + "\" readonly>" +
					"	<td><input id=\"sanpham_"+_npp.id+"_"+_sp[0]+"_open\" sp=\""+_sp[0]+"\" npp=\""+_npp.id+"\" xx=\"open\" style=\"width:100%; text-align:center; background-color:#EEE; font-size: 12px;\" type=\"text\" value=\"" + _sp[3] + "\" readonly>" +
					"	<td><input id=\"sanpham_"+_npp.id+"_"+_sp[0]+"_in\" sp=\""+_sp[0]+"\" npp=\""+_npp.id+"\" xx=\"in\" style=\"width:100%; text-align:center; font-size: 12px;\" type=\"text\" value=\"" + _sp[4] + "\"  onChange=\"tinhBan("+_npp.id+","+_sp[0]+")\">" +
					"	<td><input id=\"sanpham_"+_npp.id+"_"+_sp[0]+"_close\" sp=\""+_sp[0]+"\" npp=\""+_npp.id+"\" xx=\"close\" style=\"width:100%; text-align:center; font-size: 12px;\" type=\"text\" value=\"" + _sp[5] + "\"  onChange=\"tinhBan("+_npp.id+","+_sp[0]+")\">" +
					"	<td><input id=\"sanpham_"+_npp.id+"_"+_sp[0]+"_out\" sp=\""+_sp[0]+"\" npp=\""+_npp.id+"\" xx=\"out\" style=\"width:100%; text-align:center; background-color:#EEE; font-size: 12px;\" type=\"text\" value=\"\" readonly>" +
					"</tr>";
				spsHtml += spHtml;
			}
			
			html +=
				"<div id=\"nhaphanphoi_"+_npp.id+"\">" +
				"    <table border=\"0\" cellpadding=\"1\" cellspacing=\"1\" width=\"100%\">" +
				"        <tbody> "+
				"			<tr class=\"tbheader\">" +
				"            <th align=\"center\" width=\"60%\">Sản Phẩm</th>" +
				"            <th align=\"center\" width=\"10%\">Tồn Đầu</th>" +
				"            <th align=\"center\" width=\"10%\">Nhập</th>" +
				"            <th align=\"center\" width=\"10%\">Tồn Cuối</th>" +
				"            <th align=\"center\" width=\"10%\">Bán</th>" +
				"        	</tr>" +
								spsHtml +
				"        	<tr class=\"tbfooter\">" +
				"            	<td colspan=\"5\">&nbsp;</td>" +
				"        	</tr>" +
				"    	</tbody> " +
				" 	</table> " +
				"</div>";
		}

		$( "#accordion" ).html(html);
		$( "#accordion" ).tabs();
		
		for(i = 0; i < npps.length; i++) {			
			var _npp = npps[i];
			for(var j = 0; j < _npp.sps.length; j++) {
				var _sp = _npp.sps[j];
				tinhBan(_npp.id, _sp[0]);
			}
		}
	});
	
</script>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="nsptbForm" method="post" action="../../TonKhoStoreUpdateSvl">
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="id" value='<%= uploadBean.getId() %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="npplist" id="npplist" value="">
<input type="hidden" name="spnppList" id="spnppList" value="">
<div id="main" style="width:99%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:55%; padding:5px; float:left" class="tbnavigation">
        	Quản lý tồn kho > Nhập tồn kho store > Cập nhật
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn <%= userTen %> &nbsp;
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<A href= "../../TonKhoStoreSvl?userId=<%= userId %>" >
        	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1px" longdesc="Quay ve" style="border-style:outset"></A>
        <A href="javascript:saveform()" >
        	<IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border ="1px" style="border-style:outset"></A>
    </div>
  	
  	<div align="left" style="width:100%; float:none; clear:left">
  		<fieldset>
    		<legend class="legendtitle"> Báo lỗi nhập liệu</legend>
    		<textarea name="dataerror"  style="width: 100% ; font-weight:bold" readonly rows="1"><%= uploadBean.getMsg() %></textarea>
		    <% uploadBean.setMsg(""); %>
    	</fieldset>
  	</div>
  	
    <div align="left" style="width:100%; float:none; clear:left">
    <fieldset>
    	<legend class="legendtitle"> Nhập tồn kho</legend>
        	<div style="float:none; width:100%" align="left">
            <TABLE width="100%" cellpadding="3" cellspacing="0">				
           	  	
                <TR>
                    <TD class="plainlabel">Tháng<FONT class="erroralert"> *</FONT></TD>
                    <TD class="plainlabel"> 
                      <select name="thang" id="thangSlt">
                        </select>
                      <script>
							//Select thang from session
							var selectedThang = "<%= uploadBean.getThang() %>".trim();
							$("#thangSlt").html($("#thangSlt").html() + "<option value=\"" + selectedThang + "\">"+selectedThang+"</option>");
						</script>
                  	</TD>
                </TR>
                <TR>
                    <TD class="plainlabel">Năm<FONT class="erroralert"> *</FONT></TD>
                    <TD class="plainlabel"> 
                      <select name="nam" id="namSlt">
                      </select>
                      <script>
							//Create years option
							var selectedNam = "<%= uploadBean.getNam() %>".trim();
							$("#namSlt").html($("#namSlt").html() + "<option value=\"" + selectedNam + "\">"+selectedNam+"</option>");							
						</script>
                  </TD>
                </TR>                
                <!--<TR>
                    <TD class="plainlabel"><a class="button" href="javascript:checking();"> <img style="top: -4px;" src="../images/button.png" alt=""> Kiểm tra</a></TD>
                    <TD class="plainlabel"> </TD>
                </TR>  -->
                
                				
            </TABLE>
            <hr>
            
        </div>
            
        <div id="accordion">
            
        </div> 
           
     </fieldset>	
    </div>
</div>

</form>

</BODY>
</html>


<% 
	//if(rsNpp != null) rsNpp.close();
	//if(dvkdList != null) dvkdList.close();

	//uploadBean.closeDB();
%>