<%@page import="geso.traphaco.center.util.Utility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page  import = "geso.traphaco.erp.beans.danhgiatigia.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>

<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  

	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{	
		 int[] quyen = new  int[5];
		 quyen = util.Getquyen("Erp_danhgiatigiaSvl","",userId);


%>
  
<% IDanhgiatigiaList dgtigiaBean = (IDanhgiatigiaList)session.getAttribute("obj"); %>
<% ResultSet namList = (ResultSet) dgtigiaBean.getNamRS(); %>
<% ResultSet quyList = (ResultSet) dgtigiaBean.getQuyRS(); %>
<% ResultSet dgtigiaList = (ResultSet) dgtigiaBean.getDanhgiaRS(); %>

<% ResultSet tienteRS =  (ResultSet) dgtigiaBean.getTienteRS(); %> 
<% Hashtable<String, String> ht = (Hashtable<String, String>)dgtigiaBean.getTigiaHashtable(); %>
<% NumberFormat formatter = new DecimalFormat("#,###,###"); %>    

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>Traphaco - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs.css">
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs-panes.css">
<script type="text/javascript"	src="../scripts/jquery.min.1.7.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>
<!-- <script type="text/javascript" src="../scripts/maskedinput.js"></script> -->
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
<style type="text/css">

#diengiai {

    width: 300px;
}
#tab tr:HOVER{
cursor:pointer;
background: #E2F0D9;
}
#tabc tr input:HOVER{
cursor:pointer;
background: #E2F0D9;
}
</style>


<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<script type="text/javascript" language="javascript">
	$(document).ready(function() {
		$( ".days" ).datepicker({			    
				changeMonth: true,
				changeYear: true				
		});            
	});
	
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

	var flag=false;
	function isNumberKey(evt)
	{
	   var charCode = (evt.which) ? evt.which : event.keyCode;
	   if (charCode > 31 && (charCode < 48 || charCode > 57) && charCode!='46')
	      return false;
	
	   return true;
	}
	
	function isNumberKey2(evt)
	{
	   var charCode = (evt.which) ? evt.which : event.keyCode;
	   if (charCode > 31 && (charCode < 48 || charCode > 57) )
	      return false;
	
	   return true;
	}
	
	 function thuchienform()
	 { 
		 document.forms['dgtigiaForm'].action.value='thuchien';
	     document.forms['dgtigiaForm'].submit();
	 }

	function submitform()
	 { 
		/* if (kiemTraNgay() == false){
			return;
		} */
		document.forms['dgtigiaForm'].action.value= 'new';
	    document.forms['dgtigiaForm'].submit();
	 }

	function newform()
	{
		if (kiemTraNgay() == false){
			return;
		}
		document.forms['dgtigiaForm'].action.value= 'new';
		document.forms['dgtigiaForm'].submit();
	}
	
	function saveform()
	{
		if (kiemTraNgay() == false){
			return;
		}
		document.forms['dgtigiaForm'].action.value= 'save';
		document.forms['dgtigiaForm'].submit();
	}
	 
	function chotform()
	{
		if (kiemTraNgay() == false){
			return;
		}
		document.forms['dgtigiaForm'].action.value= 'chot';
		document.forms['dgtigiaForm'].submit();
	}

	function excel()
	{
		document.forms['dgtigiaForm'].action.value= 'excel';
		document.forms['dgtigiaForm'].submit();
	}
	
	function DinhDangDonGia(num) 
	{
		num = num.toString().replace(/\$|\,/g,'');
			
		//num = (Math.round( num * 1000 ) / 1000).toString();
			
		var sole = '';
		if(num.indexOf(".") >= 0)
		{
			sole = num.substring(num.indexOf('.'));
		}
			
		if(isNaN(num))
			num = "0";
		sign = (num == (num = Math.abs(num)));
		num = Math.floor(num*100);
		num = Math.floor(num/100).toString();
		for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
			num = num.substring(0,num.length-(4*i+3)) + ',' + num.substring(num.length-(4*i+3));
	
		var kq;
		if(sole.length >= 0)
		{
			kq = (((sign)?'':'-') + num) + sole;
		}
		else
			kq = (((sign)?'':'-') + num);
			
			//alert(kq);
		return kq;
	}
	
	function DinhdangNT(element)
	{
		var giatrinhap = element.value;
		giatrinhap = parseFloat(giatrinhap);
		element.value = DinhDangDonGia(giatrinhap.toFixed(2));
	}
	
	function Dinhdang(element)
	{
		element.value = DinhDangTien(element.value);
		if(element.value== '' )
		{
			element.value= '';
		}
	}	
	
	function DinhDangTien(num) 
	{
		num = num.toString().replace(/\$|\,/g,'');
		
		//num = (Math.round( num * 1000 ) / 1000).toString();
		
		var sole = '';
		if(num.indexOf(".") >= 0)
		{
			sole = num.substring(num.indexOf('.'));
		}
		
		if(isNaN(num))
			num = "0";
		sign = (num == (num = Math.abs(num)));
		num = Math.floor(num*100);
		num = Math.floor(num/100).toString();
		for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
			num = num.substring(0,num.length-(4*i+3)) + ',' + num.substring(num.length-(4*i+3));

		var kq;
		if(sole.length >= 0)
		{
			kq = (((sign)?'':'-') + num) ;
		}
		else
			kq = (((sign)?'':'-') + num);
		
		//alert(kq);
		return kq;
	}
	
	
	 function kiemTraNgay(){
		var quy = document.getElementById("quy").value;
		var nam = document.getElementById("nam").value;
		var ngayChungTu = document.getElementById("ngaychungtu").value;
		if(quy.trim().length <= 0  || nam.trim().length <= 0 || ngayChungTu.trim().length <= 0){
			alert ("Vui lòng nhập lại đầy đủ thông tin");
			return false;
		}
		var ngayDauQuy;
		var ngayCuoiQuy;
		if(quy == '1'){
			ngayDauQuy = nam + '-01-01';
			ngayCuoiQuy = nam + '-03-31';
		}
		else if (quy =='2'){
			ngayDauQuy = nam + '-04-01';
			ngayCuoiQuy = nam + '-6-30';
		}
		else if (quy == '3'){
			ngayDauQuy = nam + '-07-01';
			ngayCuoiQuy = nam + '-09-30';
		}
		else if (quy =='4'){
			ngayDauQuy = nam + '-10-01';
			ngayCuoiQuy = nam + '-12-31';
		}
		ngayDauQuy = new Date(ngayDauQuy);
		ngayCuoiQuy = new Date(ngayCuoiQuy);
		ngayChungTu = new Date(ngayChungTu);
		if (ngayChungTu > ngayCuoiQuy || ngayChungTu < ngayDauQuy){
			alert("Vui lòng nhập lại ngày chứng từ (ngày chứng từ phải nằm trong quý)");
			return false;
		}
		return true;
	} 
	 
</script>
	
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0"> <!-- onload ="toBottom()"  -->
<form name="dgtigiaForm" method="post" action="../../Erp_danhgiatigiaSvl">
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value='1'>


	<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:60%; padding:5px; float:left" class="tbnavigation">Quản lý kế toán &gt; Chức năng &gt; Đánh giá tỉ giá
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn <%= userTen %> &nbsp;
        </div>
    </div>
	<div align="left" id="button" style="width: 100%; height: 32px; padding: 0px; float: none" class="tbdarkrow">
		<% if(!dgtigiaBean.getTrangthai().equals("1")){ %>
			<A href="javascript: saveform();"> <img src="../images/Save.png" alt="Lưu lại" title="Lưu lại" border="1px"
			longdesc="Lưu lại" style="border-style: outset"></A> <span id="btnSave"> 
			
			<A href="javascript: chotform();"> <img src="../images/Chot.png" alt="Chốt" title="Chốt" border="1px"
			longdesc="Chốt" style="border-style: outset"></A>
		<% } %>
			<A href="javascript: excel()"> 
			<IMG src="../images/excel30.gif" title="Xuất ra Excel" alt="Xuất ra Excel" border="1px" style="border-style: outset"></A>
		</span>
	</div>
	
  	<div align="left" style="width:100%%; float:none; clear:left">
  		<fieldset>
    		<legend class="legendtitle"> Thông báo</legend>
    		<textarea name="dataerror"  rows="1" readonly="readonly" style ="width:100%%"><%= dgtigiaBean.getMsg() %></textarea>
		         <% dgtigiaBean.setMsg(""); %>
    	</fieldset>
  	</div>
  	
    <DIV align="left" style="width:100%; float:none; clear:left">
    <FIELDSET>
    	<LEGEND class="legendtitle">Tiêu chí chọn lọc</LEGEND>
        	<DIV style="float:none; width:100%" align="left">
            <TABLE width="100%" cellpadding="4" cellspacing="0">
                  						
                 <TR>
                        <TD class="plainlabel" valign="middle" >Quý</TD>
                        <TD class="plainlabel" valign="middle" colspan = 3>
                            <SELECT id="quy"  name="quy" onChange = "submitform();" >
                            		<OPTION value=""></option>
					<% 
						try
						{
							if(quyList != null){
								while(quyList.next()){
					  				if (dgtigiaBean.getQuy().equals(quyList.getString("QUY"))){ %>
									<OPTION value = <%= quyList.getString("QUY")%> selected><%= quyList.getString("QUYTEN")%> </OPTION>
							<%  	}else{ %>
							  	  	<OPTION value = <%= quyList.getString("QUY")%> ><%= quyList.getString("QUYTEN")%></OPTION>
						  	 <% 	} 
								}
						  	}
						}catch(java.sql.SQLException e){
							e.printStackTrace();
							}%>	  					
                            	
                            </SELECT>
						</TD>
                 </TR>
                 <TR>
                        <TD class="plainlabel" valign="middle" width = "20%">Năm</TD>
                        <TD class="plainlabel" valign="middle" >
                            <SELECT id="nam"  name="nam" onChange = "submitform();" >
                            		<OPTION value=""></option>
					<% 
						try
						{
							if(namList != null){
								while(namList.next()){
					  				if (dgtigiaBean.getNam().equals(namList.getString("NAM"))){ %>
									<OPTION value = <%= namList.getString("NAM")%> selected><%= namList.getString("NAM")%> </OPTION>
							<%  	}else{ %>
							  	  	<OPTION value = <%= namList.getString("NAM")%> ><%= namList.getString("NAM")%></OPTION>
						  	 <% 	} 
								}
						  	}
						}catch(java.sql.SQLException e){}%>	  					
                            	
                            </SELECT>

                 </TR>

				<TR>
                        <TD class="plainlabel" valign="middle" >Cho ghi đảo</TD>
                        <TD class="plainlabel" valign="middle" >
                 		<% if(dgtigiaBean.getGhidao().equals("1")){ %>
                 			<INPUT type="checkbox" name="ghidao" value="1" CHECKED >
                 		<%}else{ %>
                 			<INPUT type="checkbox" name="ghidao" value="1" >
                 		<%} %>
                        </TD>
				</TR>
				
				<TR>
                        <TD class="plainlabel" valign="middle" >Ngày chứng từ</TD>
                        <TD class="plainlabel" valign="middle" >
               			<INPUT type="text" class = "days" name="ngaychungtu" value="<%= dgtigiaBean.getNgaychungtu() %>" id="ngaychungtu" >
                        </TD>
				</TR>
				<TR>
                        <TD class="plainlabel" valign="middle" >Diễn giải chứng từ </TD>
                        <TD class="plainlabel" valign="middle" >
               			<INPUT type="text" name="dienGiaiCT" value="<%= dgtigiaBean.getDienGiaiCT() %>" id="dienGiaiCT" >
                        </TD>
				</TR>
				
				<TR>
                        <TD class="plainlabel" valign="middle" >Ngoại tệ </TD>
                        <TD class="plainlabel" valign="middle" >
              			    <a href="" id="1" rel="subcontent_1">
							<img alt="Ngoại tệ" src="../images/vitriluu.png"></a>
							<DIV id="subcontent_1" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
								background-color: white; width: 500px; padding: 4px;">
								<TABLE width="100%" align="center">
									<TR>
										<TH width="50px">Ngoại tệ</TH>
										<TH width="25px">Tỉ giá đánh giá</TH>
			                        </TR>
											                        
				              <% if(tienteRS != null){ 
                		    	 	int i = 0;
                        			while(tienteRS.next()){
                        
                        			%>
						                              					    
               					    <TR>
						            	<TD>
						                   <INPUT name = "ttIds" type="hidden" style="width: 100%" value="<%= tienteRS.getString("ID") %>" >
						                   <INPUT name = "tiente" type="text" style="width: 100%" value="<%= tienteRS.getString("TIENTE") %>" disabled> 
						                </TD>
						                <TD>
						                   <INPUT name = "tigia" type="text" style="width: 100%;text-align:right" value = "<%= tienteRS.getString("TIGIA") %>" onchange="DinhdangNT(this);" >
										</TD>
              					    </TR>
						                              					    
                   			<% 		}
                 				    tienteRS.close();
				              	 }             				
										%>
											                        
			                    </TABLE>
								<DIV align="right">
								  	<label style="color: red" ></label>
								   	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								   	<br />
								   	<a href="javascript:dropdowncontent.hidediv('subcontent_1')" >Đóng lại</a>
			                    </DIV>
							</DIV>	   
				                              				
                        </TD>
				</TR>

				<TR>
                        <TD class="plainlabel" valign="middle" colspan = 4>
				
						<A class="button3" href="javascript:newform()">
    					<IMG style="top: -4px;" src="../images/New.png" alt="">Đánh giá tỉ giá</A>
						

    		    		</TD>

            </TABLE>
            </DIV>          
     </FIELDSET>
    </DIV>
 <DIV align="left" style="width:100%; float:none; clear:left">
	 <FIELDSET>
		<LEGEND class="legendtitle">Đánh giá tỉ giá&nbsp;&nbsp;&nbsp;&nbsp;

		</LEGEND>
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
				<TR>
					<TD align="left" colspan="4">
						<TABLE width="80%" border="0" cellspacing="1" cellpadding="4">

							<TR class="tbheader">
								<TH width="8%" align="center">Tài khoản</th>
								<TH width="8%" align="center">Ngoại tệ</th>							
								<TH width="10%" align="center">Số dư ngoại tệ</th>
								<TH width="10%" align="center">Số dư trước đánh giá (VNĐ)</th>
								<TH width="10%" align="center">Số dư sau đánh giá (VNĐ)</th>
								<TH width="10%" align="center">Chênh lệch</TH>
							</TR>
							
			<%	
			try{ 
				if(dgtigiaList != null){	
					int m = 0;
					String lightrow = "tblightrow";
					String darkrow = "tbdarkrow";
					
					while (dgtigiaList.next()){
						if (m % 2 != 0) {%>
							<TR class=<%=lightrow%>>
					<%  } else {%>												
							<TR class=<%=darkrow%>>
				    <%  }%>
				    <%	
				    if(dgtigiaBean.getAction().equals("new")){ %>
							<TD align="center" ><%= dgtigiaList.getString("SOHIEU") %></TD>
							<TD align="center" ><%= dgtigiaList.getString("TIENTE") %></TD>
							<TD align="left"><%= formatter.format(dgtigiaList.getDouble("SODUNGOAITE")) %></TD>
							<TD align="left"><%= formatter.format(dgtigiaList.getDouble("SODUVND_TRUOCDG")) %></TD>
							<TD align="left"><%= formatter.format(dgtigiaList.getDouble("SODUNGOAITE")*Double.parseDouble((String)ht.get(dgtigiaList.getString("TTID")))) %></TD>
							
							<% double chenhlech = dgtigiaList.getDouble("SODUNGOAITE")*Double.parseDouble((String)ht.get(dgtigiaList.getString("TTID"))) -  dgtigiaList.getDouble("SODUVND_TRUOCDG"); %>
							<TD align="left"><%= formatter.format(chenhlech) %></TD>
					<% }else{ %>		
							<TD align="center" ><%= dgtigiaList.getString("SOHIEU") %></TD>
							<TD align="center" ><%= dgtigiaList.getString("TIENTE") %></TD>
							<TD align="left"><%= formatter.format(dgtigiaList.getDouble("SODUNGOAITE")) %></TD>
							<TD align="left"><%= formatter.format(dgtigiaList.getDouble("SODU_TRUOC_DG")) %></TD>
							<TD align="left"><%= formatter.format(dgtigiaList.getDouble("SODU_SAU_DG")) %></TD>
							<TD align="left"><%= formatter.format(dgtigiaList.getDouble("CHENHLECH")) %></TD>							
					
					<% } %>		
							</TR>
							
			<%				
						m++;
					}
				}
			}catch(java.sql.SQLException e) {
				e.printStackTrace();
			}%>
					</TABLE>
				</TD>
			</TR>
		</TABLE>
   </FIELDSET>
</DIV>
</div>
</FORM>

</BODY>
<script type="text/javascript">
	dropdowncontent.init('1', "right-bottom", 300, "click");
</script>
</HTML>
<% 	
try{
	if(namList != null) namList.close();
	if(quyList != null) quyList.close();
	if(dgtigiaList != null) dgtigiaList.close(); 
}catch (Exception ex)
{
	ex.printStackTrace();
}finally{
	dgtigiaBean.DBClose();
	session.setAttribute("obj", null) ; 
}
}%>
