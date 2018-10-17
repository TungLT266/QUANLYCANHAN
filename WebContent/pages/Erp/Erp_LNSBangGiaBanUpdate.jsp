<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.erp.beans.lapngansach.ILNSBanggiaban" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<% ILNSBanggiaban bgbanBean = (ILNSBanggiaban)session.getAttribute("bgbanBean"); %>
<% String nam = "" + (Integer.parseInt(bgbanBean.getDateTime().substring(0, 4)) + 1); %>
<% ResultSet dvkd = (ResultSet)bgbanBean.getDvkdIds(); %>
<% ResultSet kenh = (ResultSet)bgbanBean.getKenhIds(); %>
<% String[] spIds = bgbanBean.getSpIds() ; %>
<% String[] masp = bgbanBean.getMasp() ; %>
<% String[] tensp = bgbanBean.getTensp() ; %>
<% String[] tensp2 = bgbanBean.getTensp2() ; %>
<% String[] giamua = bgbanBean.getGiaban() ; %>
<% String[] tthai = bgbanBean.getTthai(); %>
<% String[] dv = bgbanBean.getDonvi(); %>
<% String[][] data = bgbanBean.getData() ; %>
<%NumberFormat formatter = new DecimalFormat("#,###,###"); %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>TraphacoHYERP - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">

<SCRIPT language="javascript" type="text/javascript">
function submitform()
{   
   document.forms["bgbanForm"].submit();
}

function saveform()
{
	document.forms['bgbanForm'].action.value='save';
    document.forms["bgbanForm"].submit();
}

function inExcel()
{
	document.forms['bgbanForm'].action.value='excel';
    document.forms["bgbanForm"].submit();
}

</SCRIPT>
 <script type="text/javascript">
 
 function checkedAll() {
		var spIds = new Array(<%= bgbanBean.getMaspstr() %>);	
		for (var i =0; i < spIds.length; i++) 
	 	{
		 	var cb = "chbox" + spIds[i];
		 	var objCheckBoxes = document.forms["bgbanForm"].elements[cb];
			if (document.forms["bgbanForm"].elements["chon"].checked == false){
				objCheckBoxes.checked = false;
			}else{
				objCheckBoxes.checked = true;
			}
	 	}
 }
 
	function DinhDangTien(num) 
	 {
	    num = num.toString().replace(/\$|\,/g,'');
	    if(isNaN(num))
	    num = "0";
	    sign = (num == (num = Math.abs(num)));
	    num = Math.floor(num*100+0.50000000001);
	    num = Math.floor(num/100).toString();
	    for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
	    num = num.substring(0,num.length-(4*i+3))+','+
	    num.substring(num.length-(4*i+3));
	    return (((sign)?'':'-') + num);
	}
	
	 function FormatNumber(e)
		{
			e.value = DinhDangTien(e.value);
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
			if (keypressed == 8 || keypressed == 127 || keypressed == 37 || keypressed == 39 || keypressed == 0 || keypressed == 46)
			{//Phím Delete và Phím Back va dau cham, phim Tab
				return;
			}
			return false;
		}
	}

	 function readExcel()
	 {
		 document.forms['bgbanForm'].action.value='readExcel';
		 document.forms['bgbanForm'].setAttribute('enctype', "multipart/form-data", 0);
	     document.forms['bgbanForm'].submit();
	 }


 </script>

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="bgbanForm" method="post" action="../../ErpLNSBanggiabanUpdateSvl">
<input type="hidden" name="userId" value='<%=bgbanBean.getUserId() %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="id" value='<%= bgbanBean.getId() %>'>

<TABLE width="100%" border="0" cellspacing="1" cellpadding="1"	>
	
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
					   <table width="100%" border="0" cellpadding="0" cellspacing="0">
						   <tr height="22">
 							   <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý ngân sách &gt; Bảng giá bán &gt; Cập nhật</TD>
 							   <TD colspan="2" align="right" class="tbnavigation">Chào mừng Bạn <%= bgbanBean.getUserTen() %>&nbsp;  </TD>
					     </tr>
						</table>
					 </TD>
				  </TR>	
		  	</TABLE>


			<TABLE width="100%" cellpadding="0" cellspacing="1">
			<TR ><TD >
				<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
					<TR class = "tbdarkrow">
						<TD width="30" align="left"><A href="../../ErpLNSBanggiabanSvl?userId=<%=userId %>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
					    <TD width="2" align="left" ></TD>
					    <TD width="30" align="left" ><A href="javascript: saveform()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A></TD>
				    	<TD width="2" align="left" >&nbsp;</TD>
				    	<TD width="30" height="30" align="left" ><A href="javascript: inExcel()" ><IMG src="../images/excel.gif" title="In file Excel" alt="In file Excel" border = "1"  style="border-style:outset" width="30" height="30"></A></TD>
				    	 
				    	 <TD align="left" >&nbsp;</TD>
					</TR>
				</TABLE>
			</TD></TR>
			</TABLE>

		<TABLE width="100%"  border = "0" cellspacing="0" cellpadding="0">
			  	<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Thông báo </LEGEND>
				
	    				<textarea name="dataerror"  style="width: 100%" readonly="readonly" rows="3" ><%=bgbanBean.getMessage()%></textarea>
						<% bgbanBean.setMessage(""); %>
						</FIELDSET>
				   </TD>
				</tr>			

		 	<TR>
				<TD >
			        <FIELDSET>
			        <LEGEND class="legendtitle">&nbsp;Bảng giá bán </LEGEND>
					<TABLE width="100%"cellpadding="0" cellspacing="1">
						<TR>
							<TD>				
								<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
									<TR>
										<TD width="100%" align="center">
											<TABLE class="tblight" width="100%" cellpadding="6" cellspacing="0">
												<TR>
													<TD width="15%" class="plainlabel">Tên bảng giá <FONT class="erroralert">*</FONT></TD>
													<TD  class="plainlabel"><INPUT name="bgTen" value="<%= bgbanBean.getTen() %>"
										type="text"  style="width:300px"></TD>
												</TR>
												
												<TR>
													<TD width="15%" class="plainlabel">Năm<FONT class="erroralert">*</FONT></TD>
													<TD  class="plainlabel"><INPUT name="nam" value="<%= nam %>" 
														type="text"  style="width:300px" readonly></TD>
												</TR>
												
												<TR>
								    				<TD class="plainlabel">Đơn vị kinh doanh</TD>
								      				<TD class="plainlabel">
								      				<SELECT name="dvkdId" onChange = "submitform();" style="width:300px">								      
								  	 					<option value =""></option>
								  	 					
								  	 					<%
								  	 					//System.out.println("DVKD :"+bgbanBean.getDvkdId());
								  	 					try{ while(dvkd.next()){ 
								  	 						//System.out.println("DVKD select :"+dvkd.getString("dvkdId"));
								  	 							if(dvkd.getString("dvkdId").trim().equals(bgbanBean.getDvkdId().trim())){ %>
								      								<option value='<%=dvkd.getString("dvkdId")%>' selected><%=dvkd.getString("dvkd") %></option>
								      						   <%}else{ %>
								     								<option value='<%=dvkd.getString("dvkdId")%>' ><%=dvkd.getString("dvkd")  %></option>
								     							<%}}}catch(java.sql.SQLException e){} %>	
								     	
													</SELECT></TD>
												</TR>
												<TR>
								  					<TD class="plainlabel">Kênh bán hàng </TD>
								  					<TD class="plainlabel">
								      					<SELECT name="kenhId" onChange = "submitform(); " style="width:300px">								      
								  	 						<option value =""></option>
								  	 					<% try{ while(kenh.next()){ 
								    							if((kenh.getString("kenhId").trim()).equals(bgbanBean.getKenhId().trim())){ %>
								      								<option value='<%=kenh.getString("kenhId")%>' selected><%=kenh.getString("kenh") %></option>
								      						  <%}else{ %>
								     								<option value='<%=kenh.getString("kenhId")%>'><%=kenh.getString("kenh") %></option>
								     						<%}}}catch(java.sql.SQLException e){} %>	
								     	
                                  						</SELECT></TD>
									  			</TR>
												<TR>							
							    					<TD  class="plainlabel" colspan=2 align=left>  									
							    						<% if (bgbanBean.getTrangthai().equals("1")){ %>
															<input name="trangthai" type="checkbox" value = "1" checked >
														<%}else{ %>
															<input name="trangthai" type="checkbox" value = "0"  >
														<%} %>
														Hoạt động</TD>
												</TR>	
							                    <TR>
											  	  	<TD class="plainlabel">Chọn tập tin Excel</TD>
											  	  	<TD class="plainlabel" colspan = 3>
											  	  		<INPUT type="file" name="filename" id="filename"  size="40" value=''>
											  	  	  &nbsp;&nbsp;&nbsp;	
											  	  	  <a href="javascript:readExcel();" class="button"> <img alt="" src="../images/button.png" style="top: -4px;"> Đọc dữ liệu</a>
											  	  	  
											  	  	</TD>
													  	
											  	</TR>

											</TABLE>								
										</TD>
									</TR>
								</TABLE>
							</TD>
						</TR>
				</TABLE>
				</FIELDSET>
				
				
				<div align="left" >
           	<%  
            int th=0;
           	int[] tenthang = new int[12]; 
           	String width = "100%";
           	%>
           	
           	<table id="tab" border="0" cellpadding="6" cellspacing="1" width="<%= width %>">
           	<tr>
           	<td align="center" class = "tbheader" style="background-color: #80CB9B" width = "20%">Sản phẩm
           	<td align="center" class = "tbheader" style="background-color: #80CB9B" width = "5%">Đơn vị
           	</td>
			           	
<%          String tmp = "2013;12;31";
			String[] ngaythang = tmp.split(";"); //chua nam, thang, ngay
			
           	if(ngaythang.length!=1)

           	for(int i = 0; i< 12 ; i++)
          	 { 
          	 	%>
				
				<td align="center" class = "tbheader" style="background-color: #80CB9B" >
				<span ><span >T.<%= (i+1) %></span></span></td>
			
		<%	} %>
			</tr>
			
		<%             	
		try
		{

				String lightrow = "tblightrow";
				String darkrow = "tbdarkrow";
				
//				System.out.println("DATA BEN JSP: " + data.length);
//				System.out.println("Count ben JSP: " + bgbanBean.getCount());
				int sodong = 0;
				for(int n = 0; n < bgbanBean.getCount(); n++)	
               	{ 
					//System.out.println("  -- N la: " + n + " -- DATA: " + data[n][0] + "  -- DATA1: " + data[n+1][0] + " " );
					sodong++;
					if(data[n][0] != null)
					{
           	%>


					<%} %>
			   		
			<% if (sodong%2 != 0){ %>					
				<tr class="tblightrow">
			<%}else{ %>
				<tr class="tbdarkrow">
			<%} %>
		         <%
		         	
		           	String spId = "";
		           	spId	= data[n][0].substring(0, data[n][0].indexOf(";"));
		           	String spTen =  data[n][0].substring(data[n][0].indexOf(";") + 1, data[n][0].length()) ;
		           	%>
					
				<td ><%= spTen %>
					<input type="hidden" style="width: 100%;text-align: left;border: none;background-color: #80CB9B" value="<%= spTen  %>" name= "spTen" readonly>
					<input type="hidden" style="width: 100%;text-align: left;border: none;background-color: #80CB9B" value="<%= spId  %>" name= "spId" readonly>
				</td>
				
				<% n++; %>
				<td align = "center" ><%=  data[n][0] %></td>
				
		        <%
		        	n++;
		            for(int i = 0; i < 12 ;i++)
		            {
		           	%>
				        <td align="right" style="cursor: pointer;" >
				         	<input type="text" maxlength="15" onkeypress="return keypress(event);" style="width: 100%; text-align:right;"  value="<%= formatter.format(Double.parseDouble(data[n][i])) %>" id="gia_<%= spId %>_<%= i %>" name= "gia_<%= spId %>_<%= i %>" onkeyup = "FormatNumber(this);">
				        </td>                 			
			    	<% } %>	
			</tr>
							
			<%	}  
				
		} catch(Exception e) { e.printStackTrace(); } %>
			</table>
        </div>   
				
			</TD>
			</TR>
		</TABLE>
	</TR>
</TABLE>
</FORM>
</BODY>
</HTML>
<% dvkd.close(); %>
<% kenh.close(); %>

<%bgbanBean.closeDB(); 
session.setAttribute("bgbanBean", null) ; 
%>
<%}%>