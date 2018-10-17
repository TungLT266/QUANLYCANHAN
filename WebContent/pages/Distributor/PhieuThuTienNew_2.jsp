<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.distributor.beans.phieuthutien.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<% IPhieuthutien obj = (IPhieuthutien)session.getAttribute("pttBean"); %>
<% ResultSet nvgn = (ResultSet)obj.getNvgn();%>
<% ResultSet pxk = (ResultSet)obj.getPxk();%>
<% ResultSet kh = (ResultSet)obj.getKh();%>
<% ResultSet khSelected = (ResultSet)obj.getKhSelected();%>
<%session.setAttribute("tungay", obj.getTungay()); %>
<%session.setAttribute("denngay", obj.getDenngay()); %>

<% String userId = (String) session.getAttribute("userId");  %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>Best - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
    <LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
    <LINK rel="stylesheet" href="../css/main.css" type="text/css">
    <LINK rel="stylesheet" href="../css/datepicker.css" type="text/css">
    <LINK rel="stylesheet" type="text/css" href="../css/style.css" />
    
    <script language="javascript" src="../scripts/datepicker.js"></script>
   	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css"/>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery.min.js"></script>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery-ui.min.js"></script>
  	<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>
   <link type="text/css" rel="stylesheet" href="../css/mybutton.css">
   	<style type="text/css">
	#mainContainer{
		width:660px;
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
	</style>
	<script type="text/javascript" src="../scripts/ajax.js"></script>
	<script type="text/javascript" src="../scripts/ajax-dynamic-list-kh.js"></script>
  	<script type="text/javascript" src="..scripts/jquery-1.js"></script>
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
		$(document).ready(function(){
            $(".button2").hover(function(){
                $(".button2 img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
    </script>
    <script language="javascript" type="text/javascript">
     	
  
	function submitform()
	{	
		document.forms['pttForm'].action.value='submit';
	    document.forms['pttForm'].submit();
	}

	function noclaim()
	{	
		document.forms['pttForm'].action.value='noclaim';
	    document.forms['pttForm'].submit();
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
			if (keypressed == 8 || keypressed == 10 || keypressed == 11 || keypressed == 127)
			{//Phím Delete và Phím Back
				return;
			}
			return false;
		}

		
	}
	
	
	function checkedAll(chk) {
		for(i=0; i<chk.length; i++){
		 	if(document.pttForm.chon.checked==true){
				chk[i].checked = true;
			}else{
				chk[i].checked = false;
			}
		 }
	}

	function saveform(){
		var dadung = "0";
		var khIds = document.getElementsByName("khIds");
		for(i = 0; i < khIds.length; i++){			
			
			var khObj = document.getElementsByName("khIds").item(i);
			khObj.checked = true;
			var khId = khObj.value;
			
			var tn = document.getElementById("tn" + khId).value;
			
			var tt = document.getElementById("tt" + khId).value;
				
			if (tt.length == 0) 
				tt = "0";
			
			var sd = parseFloat(tt) - parseFloat(tn);
			document.getElementById("sd" + khId).value = sd;

			dadung = parseFloat(dadung) + parseFloat(tt);
		}
		
		var tongsotien = document.getElementById('tongsotien').value;
		if (tongsotien.length == 0)
			tongsotien = "0";

		var conlai;

		if(parseFloat(tongsotien) - parseFloat(dadung)>0){
			alert("Tong so tien phai duoc phan bo het cho khach hang!");
			return;
		}
			
		if(parseFloat(tongsotien) - parseFloat(dadung)< 0){
			alert("Tong so tien thu tu khach hang khong duoc vuot qua " + tongsotien );
			return;
		}

		document.forms['pttForm'].action.value='save';
	    document.forms['pttForm'].submit();
	}

	function SuaTongSoTien(){
		var dadung = "0";
		var khIds = document.getElementsByName("khIds");		
		for(i = 0; i < khIds.length; i++){			
			
			var khObj = document.getElementsByName("khIds").item(i);			
			var khId = khObj.value;
			
			var tn = document.getElementById("tn" + khId).value;
			
			var tt = document.getElementById("tt" + khId).value;
				
			if (tt.length == 0) 
				tt = "0";
			
			var sd = parseFloat(tt) - parseFloat(tn);						
			document.getElementById("sd" + khId).value = sd;

			dadung = parseFloat(dadung) + parseFloat(tt);
		}
		
		var tongsotien = document.getElementById('tongsotien').value;
		if (tongsotien.length == 0)
			tongsotien = "0";

		var conlai;

		if(parseFloat(tongsotien) - parseFloat(dadung)>=0){
			conlai = parseFloat(tongsotien) - parseFloat(dadung);			
			document.getElementById("conlai").value = conlai;
				
		}else{		
			alert("Tong so tien thu tu khach hang khong duoc vuot qua " + tongsotien );
			document.getElementById("conlai").value = tongsotien;

			for(i = 0; i < khIds.length; i++){			
				
				var khObj = document.getElementsByName("khIds").item(i);
				var khId = khObj.value;
				
				var tn = document.getElementById("tn" + khId).value;
				
				document.getElementById("tt" + khId).value = "0";
									
				var sd = parseFloat("0") - parseFloat(tn);
				document.getElementById("sd" + khId).value = sd;

			}

		}	
		setTimeout(SuaTongSoTien, 20);	
	}
	SuaTongSoTien();
	
	function SuaThuTien(){
		var dadung = "0";
		var khIds = document.getElementsByName("khIds");
		for(i = 0; i < khIds.length; i++){			
			
			var khObj = document.getElementsByName("khIds").item(i);
			var khId = khObj.value;
			
			var tn = document.getElementById("tn" + khId).value;
			
			var tt = document.getElementById("tt" + khId).value;
				
			if (tt.length == 0) 
				tt = "0";
			
			var sd = parseFloat(tt) - parseFloat(tn);
			document.getElementById("sd" + khId).value = sd;

			dadung = parseFloat(dadung) + parseFloat(tt);
		}
		
		var tongsotien = document.getElementById('tongsotien').value;
		if (tongsotien.length == 0)
			tongsotien = "0";

		var conlai;
		var hople = false;
		if(parseFloat(tongsotien) - parseFloat(dadung)>=0){
			conlai = parseFloat(tongsotien) - parseFloat(dadung);
			hople = true;		 			
		}else{		
			alert("Tong so tien thu tu khach hang khong duoc vuot qua " + tongsotien );
			conlai = 0;
		}	

		if (hople){
			document.getElementById("conlai").value = conlai;
			for(i = 0; i < khIds.length; i++){			
				
				var khObj = document.getElementsByName("khIds").item(i);
				var khId = khObj.value;
				
				var tn = document.getElementById("tn" + khId).value;
				
				var tt = document.getElementById("tt" + khId).value;
					
				if (tt.length == 0) 
					tt = "0";
				
				var sd = parseFloat(tt) - parseFloat(tn);
				document.getElementById("sd" + khId).value = sd;

			}
		}
	}

	
	function Tinhtoan(posKH){
		var dadung = "0";
		var khIds = document.getElementsByName("khIds");
		for(i = 0; i < khIds.length; i++){			
			
			var khObj = document.getElementsByName("khIds").item(i);
			var khId = khObj.value;
			
			var tn = document.getElementById("tn" + khId).value;
			
			var tt = document.getElementById("tt" + khId).value;
				
			if (tt.length == 0) 
				tt = "0";
			
			var sd = parseFloat(tt) - parseFloat(tn);
			document.getElementById("sd" + khId).value = sd;

			dadung = parseFloat(dadung) + parseFloat(tt);
//			alert(dadung);			
		}
		
		var tongsotien = document.getElementById('tongsotien').value;
		if (tongsotien.length == 0)
			tongsotien = "0";

		var conlai;
		conlai = parseFloat(tongsotien) - parseFloat(dadung);

//		alert("tong so tien:" + tongsotien);
				
		var obj = document.getElementsByName("khIds").item(posKH);
		var khgId = obj.value;

//		alert("khId:" + khgId);
			
		if(obj.checked){
//			alert("I am here");
			
			var tgno = document.getElementById("tn" + khgId).value;
//			alert("tgno:" + tgno);
			
			var thtien = document.getElementById("tt" + khgId).value;
			if (thtien.length == null) 
				thtien = "0";

//			alert("tt:" + thtien);
			
			var sodu = document.getElementById("sd" + khgId).value;
			if (sodu.length == 0) 
				sodu = "0";

//			alert("so du:" + sodu);
			
			if(parseFloat(conlai) > 0){
				if(parseFloat(conlai) - parseFloat(tgno) > 0){
						thtien = tgno;
						conlai = parseFloat(conlai) - parseFloat(thtien);						
				}else{
					thtien = conlai;
					conlai = "0";					
				}

//				alert("tt:" + thtien);
				document.getElementById("tt" + khgId).value = thtien;
				sudung = parseFloat(thtien) - parseFloat(tgno);
				
//				alert("sd:" + sudung);
				document.getElementById("sd" + khgId).value = sudung;

//				alert("con lai:" + conlai);
				document.getElementById("conlai").value = conlai;
							
			}		
			
		}else{
			var tgno = document.getElementById("tn" + khgId).value;

			var thtien = document.getElementById("tt" + khgId).value;
			if (thtien.length == 0) 
				thtien = "0";

			var sudung = document.getElementById("sd" + khgId).value;
			if (sudung.length == 0) 
				sd = "0";

			conlai = parseFloat(conlai) + parseFloat(thtien);
			thtien = 0;
					
			document.getElementById("tt" + khgId).value = thtien;
			sudung = (-1)*tgno;
			document.getElementById("sd" + khgId).value = sudung;
			document.getElementById("conlai").value = conlai;
		}

	}
	
	function tinhtongno()
	{
		var khIds = document.getElementsByName("khIds");		
		var tongno;
		var tongcongno = "0";
		for(k = 0; k < khIds.length; k++)
		{			
			
			var khObj = document.getElementsByName("khIds").item(k);
			var sodong = khObj.value;			
			tongno = document.getElementById("tn" + sodong).value;
			//alert(tongno);
			tongcongno = parseFloat(tongcongno) + parseFloat(tongno);
			//alert(tongcongno);
		}
		//alert(tongcongno);
		document.getElementById("tongnox").value = tongcongno;				
	}
	
	function sellectAll()
	 {
		var i;
		 var chkAll = document.getElementById("chkAll");
		 var khIds = document.getElementsByName("khIds");
		 
		 if(chkAll.checked)
		 {
			 for(i = 0; i < khIds.length; i++)
			 {
				 khIds.item(i).checked = true;
				 Tinhtoan(i);
			 }
		 }
		 else
		 {
			 for(i = 0; i < khIds.length; i++)
			 {
				 khIds.item(i).checked = false;
				 Tinhtoan(i);
			 }
		 }
	 }

	
	</script>
</head>
<BODY onload="tinhtongno()" leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="pttForm" method="post" action="../../PhieuthutienUpdateSvl">
	
		<INPUT name="userId" type="hidden" value='<%=userId %>' size="30">
		<input type="hidden" name="nppId" value='<%=obj.getNppId() %>'>
		<input type="hidden" name="nvgnId" value='<%=obj.getNvgnId() %>'>
		<input type="hidden" name="action" value='1'>
		<input type="hidden" name="tungay" id="tungay" value='<%=session.getAttribute("tungay")%>'>
		<input type="hidden" name="denngay" id="denngay" value='<%=session.getAttribute("denngay")%>'>
		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 70%; padding: 5px; float: left"
					class="tbnavigation">Quản lý công nợ > Phiếu thu tiền> Tạo
					mới</div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng 
					<%= obj.getNppTen() %>
					&nbsp;
				</div>
			</div>

			<div align="left" id="button"
				style="width: 100%; height: 32px; padding: 0px; float: none"
				class="tbdarkrow">
				<A href="../../PhieuthanhtoanSvl?userId= <%= userId %>"> <img
					src="../images/Back30.png" alt="Quay ve" title="Quay ve"
					border="1px" longdesc="Quay ve" style="border-style: outset"></A>
				<A href="javascript:saveform()"> <IMG
					src="../images/Save30.png" title="Luu lai" alt="Luu lai"
					border="1px" style="border-style: outset"></A>
			</div>
			<div align="left" style="width: 70%; float: none">
				<fieldset>
					<legend class="legendtitle">Báo lỗi nhập liệu </legend>
					<textarea name="dataerror"
						style="width: 100%; color: #F00; font-weight: bold" cols="90"
						rows="1" readonly="readonly"><%=obj.getMessage() %></textarea>

				</fieldset>
			</div>
			<div align="left" style="width: 70%; float: none">
				<fieldset>
					<legend class="legendtitle">Phiếu thu tiền</legend>
					<TABLE width="100%" cellpadding="5px" cellspacing="0px">
						<TR>
							<TD class="plainlabel" width="25%">Ngày thu tiền</TD>
							<TD class="plainlabel" colspan="2"><input type="text"
								size="10"
								class="w8em format-y-m-d divider-dash highlight-days-12"
								name="ngay" id="ngay" value="<%=obj.getNgay() %>" maxlength="10" />
							</TD>
						</TR>


						<TR>
							<TD class="plainlabel">Nhân viên giao nhận</TD>
							<TD class="plainlabel" colspan="2"><select name="nvId"
								id="nvId" disabled=disabled>
									<option value="">&nbsp;</option>
									<% if(nvgn != null){
					  				try{ while(nvgn.next()){ 
		    							if(nvgn.getString("pk_seq").equals(obj.getNvgnId())){ %>
									<option value='<%=nvgn.getString("pk_seq")%>' selected><%=nvgn.getString("ten") %></option>
									<%}else{ %>
									<option value='<%=nvgn.getString("pk_seq")%>'><%=nvgn.getString("ten") %></option>
									<%}
		    						} nvgn.close(); 
		    						}catch(java.sql.SQLException e){} 
		    					}%>
							</select></TD>
						</TR>
						<TR>
							<TD class="plainlabel">Diễn giải</TD>
							<TD class="plainlabel" colspan="2"><input type="text"
								name="diengiai" id="diengiai" size="50"
								value='<%= obj.getDiengiai() %>'></TD>
						</TR>

						<TR>
							<TD class="plainlabel">Tổng số tiền</TD>
							<TD class="plainlabel" colspan="2"><input type="text"
								name="tongsotien" size="10" id="tongsotien"
								value="<%= obj.getTongsotien() %>"
								onkeypress="return keypress(event);" onChange=SuaTongSoTien();
								style="text-align: right" /> VND</TD>
						</TR>

						<TR>
							<TD class="plainlabel">Còn lại</TD>
							<TD class="plainlabel" colspan="2"><input type="text"
								name="conlai" size="10" id="conlai" value="0" readonly
								style="text-align: right" /> VND</TD>
						</TR>
						
						<TR>
							<TD class="plainlabel">Tổng nợ</TD>
							<TD class="plainlabel" colspan="2"><input type="text"
								name="tongnox" size="10" id="tongnox" value="0" readonly
								style="text-align: right" /> VND</TD>
						</TR>
						
						<TR class="tblightrow">
							<TD class="plainlabel" colspan=3><a class="button2"
								href="javascript:noclaim()"> <img style="top: -4px;"
									src="../images/button.png" alt="">Chọn khách hàng

							</a>&nbsp;&nbsp;&nbsp;&nbsp;</TD>
						</TR>


					</TABLE>
					<hr>
					<div style="width: 100%; float: none" align="left">
						<table class="tabledetail"
							style="width: 100%; border-collapse: collapse;" cellpadding="0px"
							cellspacing="0px" border="1px">
							<tr class="plainlabel">
								<th width="15%" align="center">Mã khách hàng</th>
								<th width="25%" align="center">Tên khách hàng</th>
								<th width="15%" align="center">Tổng nợ</th>

								<th width="10%" align="center">Trả hết <input type="checkbox" id="chkAll" onchange="sellectAll();" ></th>
								<th width="18%" align="center">Số tiền thu</th>
								<th width="17%" align="center">Số dư</th>
							</tr>
							<% int m = 0;
                	try{
                		if(khSelected != null){
                  			while(khSelected.next()) {
                    		if (m % 2 != 0) {%>
							<TR class="tblightrow">
								<%} else {%>
							
							<TR class="tbdarkrow">
								<%}%>
								<TD align="center"><%=khSelected.getString("khId") %></TD>
								<TD align="left"><%=khSelected.getString("khTen") %></TD>
								<TD align="right"><input type="text"
									name=<%="tn" + khSelected.getString("khId") %>
									id=<%="tn" + khSelected.getString("khId") %> size="10"
									value='<%=Math.round(Double.parseDouble(khSelected.getString("tongno")))%>'
									readonly></TD>

								<TD align="center"><input type="checkbox" name="khIds"
									value='<%=khSelected.getString("khId") %>' id="khIds"
									onClick="Tinhtoan(<%=m %>)"></TD>
								<TD align="center"><input type="text" size="10"
									name=<%="tt" + khSelected.getString("khId") %>
									id=<%="tt" + khSelected.getString("khId") %> value="0"
									onChange="SuaThuTien();"></TD>

								<TD align="center"><input type="text" size="10"
									name=<%="sd" + khSelected.getString("khId") %>
									id=<%="sd" + khSelected.getString("khId") %>
									value='<%=(-1)*Math.round(Double.parseDouble(khSelected.getString("tongno")))%>'
									readonly></TD>

							</TR>
							<% m++;}
                  		
                  			khSelected.close();
                		}

                		
                  }catch(java.sql.SQLException e){}%>
							<tr class="plainlabel">
								<td colspan="9">&nbsp;</td>
							</tr>
						</table>
					</div>
				</fieldset>
			</div>

			<script>
	jQuery(function()
	{		
			$("#khId").autocomplete("KhachHangList_PTT.jsp");		
	});	
	
</script>
		</div>
	</form>
</BODY>
</html>

