<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="geso.traphaco.center.beans.tieuchithuong.imp.*" %>
<%@page import="geso.traphaco.center.beans.tieuchithuong.*" %>
<%@ page  import = "geso.traphaco.center.beans.dieukienkhuyenmai.ISanpham" %>
<%@ page  import = "geso.traphaco.center.beans.dieukienkhuyenmai.imp.Sanpham" %>
<%@page import="java.util.Calendar" %>
<%@page import="java.util.Date" %>
<%@page import="java.util.List" %>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@page import="java.sql.SQLException" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<%
 	ITieuchithuongTB obj =(ITieuchithuongTB)session.getAttribute("tctskuBean");
	
	String[] diengiaiMuc = (String[])obj.getDiengiaiMuc();
	String[] tumuc = (String[])obj.getTumuc();
	String[] denmuc = (String[])obj.getDenmuc();
	String[] thuongSR = (String[])obj.getThuongSR();
	String[] thuongTDSR = (String[])obj.getThuongTDSR();
	String[] thuongSS = (String[])obj.getThuongSS();
	String[] thuongTDSS = (String[])obj.getThuongTDSS();
	String[] thuongASM = (String[])obj.getThuongASM();
	String[] thuongTDASM = (String[])obj.getThuongTDASM();
	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">

<script type="text/javascript" src="..scripts/jquery-1.js"></script>
   <LINK rel="stylesheet" type="text/css" href="../css/style.css" />
<style type="text/css">
	#mainContainer{
		width:auto;
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
		z-index:100200;
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
<script type="text/javascript" src="../scripts/nhomSchemethuong.js"></script>

<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="../scripts/Timepicker/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/Timepicker/jquery-ui.min.js"></script>

<link media="screen" rel="stylesheet" href="../css/colorbox.css">
    <script src="../scripts/colorBox/jquery.colorbox.js"></script>
    <script>
        $(document).ready(function()
        {
        	$(".nhomdieukien").colorbox({width:"500px", inline:true, href:"#nhomdieukien"});
            //Example of preserving a JavaScript event for inline calls.
            $("#click").click(function(){ 
                $('#click').css({"background-color":"#f00", "color":"#fff", "cursor":"inherit"}).text("OneOne - Project.");
                return false;
            });
           
        });
    </script>

<SCRIPT language="JavaScript" type="text/javascript">
	function submitform()
	{
	    document.forms["tctsku"].submit();
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
			{//Phím Delete và Phím Back
				return;
			}
			return false;
		}
	}
	
	function save()
	{
	  var thang = document.getElementById("thang").value;
	  var nam = document.getElementById("nam").value;

	  if( thang == '' )
	  {
		  alert("Chọn tháng cần đặt chỉ tiêu");
		  return;
	  }
	  	
	  if( nam == '' )
	  {
		  alert("Chọn năm cần đặt chỉ tiêu");
		  return;
	  }
	  
	  document.forms["tctsku"].action.value = "save";
	  document.forms["tctsku"].submit(); 
  }
	
	function replaces()
	{
		var masp = document.getElementsByName('schemeTB');
		var tensp = document.getElementsByName('schemeDG');
		
		for(p=0; p < masp.length; p++)
		{
			if(masp.item(p).value != "")
			{
				var sp = masp.item(p).value;

				var pos = parseInt(sp.indexOf(" --> "));
				if(pos > 0)
				{
					masp.item(p).value = sp.substring(0, pos);
					tensp.item(p).value = sp.substr(parseInt(sp.indexOf(" --> ")) + 5);					
				}
			}
			else
			{
				tensp.item(p).value = "";
			}			
		}
		
		setTimeout(replaces, 200);
	}	
	
	function FormatNumber(e)
	{
		e.value = DinhDangTien(e.value.replace(/,/g,""));
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

</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name ="tctsku" method="post" action="../../TieuchithuongTBUpdateSvl" >
<input type="hidden" name="userId" value='<%= userId %>' >
<input type="hidden" name="action" value="0">
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý trưng bày > Tiêu chí thưởng trưng bày > Tạo mới</TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %></TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<TR ><TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
							<TD width="30" align="left"><A href="../../TieuchithuongTBSvl?userId=<%=userId%>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
						    <TD width="2" align="left" ></TD>
						    <TD width="30" align="left" >
						    <div id="btnSave">
						    <A href="javascript: save()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A>
						    </div>
						    </TD>
							<TD >&nbsp; </TD>						
						</TR>
					</TABLE>
			</TD></TR>
		</TABLE>
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="1" >
			  	<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>
				
	    				<textarea name="dataerror"  style="width: 100% ; font-weight:bold"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1"><%= obj.getMsg() %></textarea>
						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Thông tin tiêu chí thưởng độ phủ </LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
							<TR>
								<TD width="150px" class="plainlabel" >Tháng <FONT class="erroralert"> *</FONT></TD>
								<TD class="plainlabel" colspan="3">
									<select name="thang" id="thang" style="width: 70px">
									<option value= ""> </option>  
									<%
									int k=1;
									for(k=1; k <= 12; k++ ){
										
									  if(obj.getThang().equals(Integer.toString(k)) ) {
									%>
										<option value=<%= k %> selected="selected" > <%= k %></option> 
									<%  }else{  %>
										<option value=<%= k %> > <%= k %></option> 
									<% } }%>
									</select>
								</TD>
							</TR>
							<TR>
							  	<TD class="plainlabel">Năm <FONT class="erroralert"> *</FONT></TD>
						  	  	<TD class="plainlabel" colspan="3">
									<select name="nam" id="nam" style="width: 70px" >
									<option value= ""> </option>  
									<%
									Calendar cal=Calendar.getInstance();
									int year_=cal.get(Calendar.YEAR);
									for(int n=2008; n<year_+3; n++) {
									  if(obj.getNam().equals( Integer.toString(n)) ){									  
									%>
										<option value=<%=n %> selected="selected" > <%=n %></option> 
									<%
									  }else{
									 %>
										<option value=<%=n %> ><%=n %></option> 
									<% } }
									%>
									</select>
						  	  	</TD>
						  </TR>
						  <TR>
						  	  	<TD class="plainlabel">Scheme <FONT class="erroralert"> *</FONT></TD>
						  	  	<TD class="plainlabel" colspan="3">
						  	  		<input type="text" name="scheme" id="scheme" value="<%= obj.getScheme() %>"> 
						  	  	</TD>
						  </TR>
						  <TR>
						  	  	<TD class="plainlabel">Diễn giải</TD>
						  	  	<TD class="plainlabel" colspan="3">
						  	  		<input type="text" name="diengiai" id="diengiai" value="<%= obj.getDiengiai() %>"> 
						  	  	</TD>
						  </TR>
						  <TR>
						  	  	<TD class="plainlabel">Chương trình áp dụng</TD>
						  	  	<TD class="plainlabel" colspan="3">
						  	  		
						  	  		<a class="nhomdieukien" href="#">
			                        		<img style="top: -4px;" src="../images/vitriluu.png" title="Tạo mới nhóm điều kiện"></a>
					                <div style='display:none; '>
				                        <div id='nhomdieukien' style='padding:0px 5px; background:#fff;'>
				                        	<h4 align="left">Chọn chương trình áp dụng</h4>
											<table cellpadding="4px" cellspacing="2px" width="100%" align="center">
											
				                              <tr>
				                                	<td colspan="2">
				                                		<table align="left" cellpadding="0px" cellspacing="1px">
					                                		<tr class="tbheader">
					                                			<td width="100px" align="center" ><span style="font-size: 0.9em">Scheme</span></td>
					                                			<td width="330px" align="center" ><span style="font-size: 0.9em">Diễn giải</span></td>		                                			
					                                		</tr>
					                                	</table>
					                                	<div id="tbsanpham" style="width: 100%; max-height: 180px; overflow: auto">
					                                	<table align="left" cellpadding="0px" cellspacing="1px">
					                                	<% 
					                                		int count = 0;
					                                		String[] schemeTB = (String[])obj.getSchemeTB();
					                                		String[] schemeDG = (String[])obj.getSchemeDiengiai();
					                                		
					                                		if(schemeTB != null)
					                                		{
					                                			for(int i = 0; i < schemeTB.length; i++)
					                                			{
					                                				%>
					                                				
					                                				<tr>
							                                			<td width="100px" align="center">
							                                				<input type="text" value="<%= schemeTB[i] %>" style="width: 100px" name="schemeTB" onkeyup="ajax_showOptions(this,'sanpham',event)" AUTOCOMPLETE="off"  >
							                                			</td>
							                                			<td width="200px" align="left">
							                                				<input type="text" value="<%= schemeDG[i] %>" name="schemeDG" style="width: 300px" readonly="readonly">
							                                			</td>                   			
							                                		</tr>
					                                				
					                                			<% count ++;
					                                			}
					                                		}
					                                		for(int pos = count; pos < 15; pos++){ %>
					                                		<tr>
					                                			<td width="100px" align="center">
					                                				<input type="text" value="" style="width: 100px" name="schemeTB" onkeyup="ajax_showOptions(this,'sanpham',event)" AUTOCOMPLETE="off">
					                                			</td>
					                                			<td width="200px" align="left">
					                                				<input type="text" value="" name="schemeDG" style="width: 300px" readonly="readonly">
					                                			</td>                   			
					                                		</tr>
					                                	<%} %>
				                                		</table>
				                                		</div>
				                                	</td>
				                                </tr>
				                            </table>
										</div>
					                </div>
						  	  		
						  	  	</TD>
						  </TR>
						  
						</TABLE>
						<hr />
						
						<TABLE class="tabledetail" width="100%" border="0" cellspacing="1px" cellpadding="0px">
		                    <TR class="tbheader">
		                        <TH align="center" width="20%" rowspan="2" >Diễn giải</TH>
		                        <TH align="center" width="20%" colspan="2" >Phần trăm đạt</TH>
		                        <td align="center" width="10%" colspan="2">Thưởng SR</td>
		                    	<td align="center" width="20%" colspan="2" >Thưởng SS</td>
		                    	<td align="center" width="20%" colspan="2" >Thưởng ASM</td>
		                    </TR>
		                    <TR class="tbheader">
		                    	<TH align="center" width="10%" >Từ mức</TH>
		                        <TH align="center" width="10%" >Đến mức</TH>
		                        <td align="center" width="10%">T. SR</td>
		                    	<td align="center" width="10%" >T. TĐ SR</td>
		                    	<td align="center" width="10%" >T. SS</td>
		                    	<td align="center" width="10%" >T. TĐ SS</td>
		                    	<td align="center" width="10%" >T. ASM</td>
		                    	<td align="center" width="10%" >T. TĐ ASM</td>
		                    </TR>
		                    
		                    <%
		                    	count = 0;
		                    	if( diengiaiMuc != null ) 
		                    	{
		                    		for(int i = 0; i < diengiaiMuc.length; i++)
		                    		{
										%>   
										
										<tr>
											<td>
												<input type="text" name="diengiaiMuc" value="<%= diengiaiMuc[i] %>" >
											</td>
											<td>
												<input type="text" name="tumuc" value="<%= tumuc[i] %>" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
											</td>
											<td>
												<input type="text" name="denmuc" value="<%= denmuc[i] %>" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
											</td>
											<td>
												<input type="text" name="thuongSR" value="<%= thuongSR[i] %>" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
											</td>
											<td>
												<input type="text" name="thuongTDSR" value="<%= thuongTDSR[i] %>" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
											</td>
											<td>
												<input type="text" name="thuongSS" value="<%= thuongSS[i] %>" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
											</td>
											<td>
												<input type="text" name="thuongTDSS" value="<%= thuongTDSS[i] %>" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
											</td>
											<td>
												<input type="text" name="thuongASM" value="<%= thuongASM[i] %>" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
											</td>
											<td>
												<input type="text" name="thuongTDASM" value="<%= thuongTDASM[i] %>" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
											</td>
										</tr>
										                 			
		                    		<% }
		                    		
		                    	}
		                    	
		                    	for(int i = count; i < 15; i++)
	                    		{
	                    			%>
	                    			
	                    			<tr>
										<td>
											<input type="text" name="diengiaiMuc" value="" >
										</td>
										<td>
											<input type="text" name="tumuc" value="" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
										</td>
										<td>
											<input type="text" name="denmuc" value="" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
										</td>
										<td>
											<input type="text" name="thuongSR" value="" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
										</td>
										<td>
											<input type="text" name="thuongTDSR" value="" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
										</td>
										<td>
											<input type="text" name="thuongSS" value="" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
										</td>
										<td>
											<input type="text" name="thuongTDSS" value="" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
										</td>
										<td>
											<input type="text" name="thuongASM" value="" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
										</td>
										<td>
											<input type="text" name="thuongTDASM" value="" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
										</td>
									</tr>
	                    			
	                    		<%  }	
		                    	
		                    %>
							
		                    <TR>
		                        <TD align="center" colspan="15" class="tbfooter">&nbsp;</TD>
		                    </TR>
						</TABLE>
									
						</FIELDSET>						
					</TD>
				</TR>
			</TABLE>
		</TD>
	</TR>
	</TABLE>
</form>
<script type="text/javascript">
	replaces();
</script>
</BODY>
</HTML>
<%}%>
