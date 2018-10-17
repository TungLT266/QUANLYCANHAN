<%@page import="com.lowagie.tools.split_pdf"%>
<%@page import="geso.traphaco.center.beans.banggiablkh.IGiaKh"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.beans.banggiablkh.IBanggiablKh" %>
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

<% IBanggiablKh bgblcBean = (IBanggiablKh)session.getAttribute("bgblcBean");
	NumberFormat formatter = new DecimalFormat("#,###,####");
	NumberFormat formatter2 = new DecimalFormat("#,###,###.####");
	List<IGiaKh> khList = bgblcBean.getKhlist();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/ajax.js"></script>
<script type="text/javascript" src="../scripts/ajax-banggia-list-kh.js"></script>
<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>
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
	
}
</style>

<SCRIPT language="javascript" type="text/javascript">
function submitform()
{   
   document.forms["bgblcForm"].submit();
}

 function saveform()
{
	document.forms['bgblcForm'].action.value='save';
    document.forms["bgblcForm"].submit();
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
	function Dinhdang(element)
	{
		element.value=DinhDangTien(element.value);
		if(element.value== '' ||element.value=='0' )
		{
			element.value= '';
		}
	}
	function replaces1()
	{    
		
		var spTen = document.getElementsByName("spTen"); 
		var spMa = document.getElementsByName("spMa");
		var id = document.getElementById("id");
		
		for(i=0; i < spMa.length; i++)
		{
			var tem = spMa.item(0).value;
			if(parseInt(tem.indexOf("-->[")) > 0)
			{
				var tmp = tem.substring(0, parseInt(tem.indexOf("-->[")));
				document.getElementById("id").value = tmp.substring(parseInt(tem.indexOf("-")+1), tmp.length);
				spMa.item(0).value = tmp.substring(0, parseInt(tem.indexOf("-")));
				tem = tem.substr(parseInt(tem.indexOf("-->[")) + 4);
				spTen.item(0).value = tem.substring(0, parseInt(tem.indexOf("]")));
			}
		}
		
		var khid = document.getElementsByName("khid"); 
		var khma = document.getElementsByName("khma");
		var khten = document.getElementsByName("khten");
		for(i=0; i < khma.length; i++)
		{
			var ma = khma.item(i).value;
			if(parseInt(ma.indexOf(" -- ")) > 0)
			{
				var tmp = ma.substring(0, parseInt(ma.indexOf(" -- ")));
				khid.item(i).value = tmp;
				tmp = ma.substring(parseInt(ma.indexOf(" -- ")+4), ma.length);
				//document.getElementById("id").value = tmp.substring(parseInt(tem.indexOf("-")+1, tmp.length));
				khma.item(i).value = tmp.substring(0, parseInt(tmp.indexOf(" -- ")));
				tmp = tmp.substring(parseInt(tmp.indexOf(" -- ")+4), tmp.length);
				khten.item(i).value = tmp;
			}
			if(khma.item(i).value == ""){
				khid.item(i).value = "";
				khma.item(i).value = "";
				khten.item(i).value = "";
			}
		}
		
		setTimeout(replaces1, 300);
	} 
</SCRIPT>

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="bgblcForm" method="post" action="../../BanggiabanleKhUpdateSvl">
<input type="hidden" name="userId" value='<%=bgblcBean.getUserId() %>'>
<input type="hidden" name="id" id = "id" value="<%=bgblcBean.getSpId() %>">
<input type="hidden" name="action" value='1'>
 
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
		
			<TABLE width="100%" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
					   <table width="100%" border="0" cellpadding="0" cellspacing="0">
						   <tr height="22">
 							   <TD align="left" colspan="2" class="tbnavigation">
 							   		&nbsp;Dữ liệu nền &gt;Sản phẩm &gt; Bảng giá bán lẻ khách hàng&gt; Cập nhật </TD>
 							   <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;</td>
					     </tr>
						</table>
					 </TD>
				  </TR>	
		  	</TABLE>
			<TABLE width="100%" cellpadding="0" cellspacing="1">
			<TR ><TD >
				<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
					<TR class = "tbdarkrow">
						<TD width="30" align="left"><A href="../../BanggiabanleKhSvl?userId=<%=userId %>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
					    <TD width="2" align="left" ></TD>
					    <TD width="30" align="left"><A href="javascript: saveform()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A></TD>
				    	<TD align="left" >&nbsp;</TD>
					</TR>
				</TABLE>
			</TD></TR>
			</TABLE>

		<TABLE width="100%"  border = "0" cellspacing="0" cellpadding="0">
			  	<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Bão lỗi nhập liệu </LEGEND>
				
	    				<textarea name="dataerror"  style="width: 100% ; font-weight:bold" rows="1" style="width: 100%" readonly="readonly" ><%=bgblcBean.getMessage()%></textarea>
						<% bgblcBean.setMessage(""); %>
						</FIELDSET>
				   </TD>
				</tr>			
		 	
			<TR>
		 	
			<TR>
				<TD >
        			
				<FIELDSET>
				<LEGEND class="legendtitle">&nbsp;Bảng giá bán &nbsp;</LEGEND>
				<TABLE width="100%" cellpadding="0" cellspacing="1">
					<TR><TD>					
						<TABLE  width="100%" cellpadding="4" cellspacing="0">
							<TR>
								<TD  width="15%" class="plainlabel">Mã Sản phẩm<FONT class="erroralert">*</FONT></TD>
								<TD class="plainlabel">
								  <input name="spMa" id = "spMa" type="text" value="<%= bgblcBean.getSpMa() %>" style="width:300px" readonly="readonly">
							  	</TD>
							</TR>
							<TR>
								<TD  width="15%" class="plainlabel">Tên Sản phẩm<FONT class="erroralert">*</FONT></TD>
								<TD class="plainlabel">
								  <input name="spTen" id = "spTen" type="text" value="<%= bgblcBean.getSpTen() %>" style="width:300px" readonly="readonly">
							  	</TD>
							</TR>
							
							<%-- <TR>							
							    <TD  class="plainlabel" colspan=2 align=left>  									
							    <% if (bgblcBean.getTrangthai().equals("1")){ %>
									<input name="trangthai" type="checkbox" value = "1" checked >
									<%}else{ %>
									<input name="trangthai" type="checkbox" value = "0"  >
								<%} %>
								Hoạt động </TD>
							</TR>	 --%>
							
						</TABLE>
					</TD></TR>
				</TABLE>
				<TABLE class="tabledetail" cellpadding="0" cellspacing="0" width="100%">
						<TR id="spdvkd" bgcolor="#FFFFFF">
							<TD width="100%">
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="0">
								<TR class="tbheader">
									<TH width="25%">Mã khách hàng</TH>
									<TH width="45%">Tên khách hàng</TH>
									<TH width="10%">Giá bán</TH>
								</TR>
								
								<%
								String lightrow = "tblightrow";
								String darkrow = "tbdarkrow";
								int k = 0;
								if (khList != null)
								{
									try{ 
										for(int i=0; i< khList.size(); i++)
										{ 
											if (i % 2 != 0) 
											{%>						
												<TR class= <%=lightrow%> >
										    <%} else 
										    {%>
												<TR class= <%= darkrow%> >
											<%}%>
												<TD align="center">
													<input type='hidden' name='khid' value="<%=khList.get(i).getKhId() %>" />
													<input name="khma" type="text" value="<%=khList.get(i).getKhMa()%>"	onkeyup="ajax_showOptions(this,'abc',event)" style="width: 100%" AUTOCOMPLETE="off">
												</TD>
												<TD align="center"><input type="text" name = "khten" value="<%=khList.get(i).getKhTen()%>" readonly="readonly"/></TD>
												<TD align="center">
													<input type='text' name='dongia'  value="<%= formatter2.format(Double.parseDouble(khList.get(i).getGiaban())) %>" style="text-align: right"/>
												</TD>
													
								     		<%k++;
										}
									}catch(Exception e){ e.printStackTrace(); } 
								}%>
								<%for(int i = k; i< 30; i++)
								{ 
									if (i % 2 != 0) 
									{%>						
										<TR class= <%=lightrow%> >
								    <%} else 
								    {%>
										<TR class= <%= darkrow%> >
									<%}%>
											<TD align="center">
												<input type='hidden' name='khid' value="" />
												<input name="khma" type="text" value=""	onkeyup="ajax_showOptions(this,'abc',event)" style="width: 100%" AUTOCOMPLETE="off">
											</TD>
											<TD align="center"><input type="text" name = "khten"  value=""/></TD>
											<TD align="center">
												<input type='text' name='dongia'  value="" style="text-align: right"/>
											</TD>
											
						     		<%
								}%>
							</TABLE>

							</TD>
						</TR>
					</TABLE>
					</FIELDSET>
				</td>
			</TR>
		</TABLE>
	
	</TD>
	</TR>
</Table>
<script type="text/javascript">
	replaces1();
	jQuery(function()
	{		
		$("#spMa").autocomplete("SanphamBgList.jsp");		
	});	
</script>
</form>
</BODY>
</html>
<%
	bgblcBean.closeDB();
%>
<%}%>