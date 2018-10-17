<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page  import = "geso.traphaco.erp.beans.lapngansach.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<% IDubaokinhdoanhNam_Giay dbkdBean = (IDubaokinhdoanhNam_Giay)session.getAttribute("dbkdBean"); %>
<% ResultSet spRs = (ResultSet)dbkdBean.getSanPhamRs(); %>
<% ResultSet nhRs = (ResultSet)dbkdBean.getNhanhangList(); %>
<% ResultSet clRs = (ResultSet)dbkdBean.getChungloaiList(); %>
<% String[][] data = dbkdBean.getData() ; %>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  %>
<% 

	String splist = dbkdBean.getMaspstr();
	int count = dbkdBean.getCount();
	//ResultSet dvkd = dbkdBean.getDvkdRs();
%>
	<%NumberFormat formatter = new DecimalFormat("#,###,###"); %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>TraphacoHYERP - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs.css">
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs-panes.css">
<script type="text/javascript"	src="../scripts/jquery.min.1.7.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>
<script type="text/javascript" src="../scripts/maskedinput.js"></script>
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
	



	function Upload()
	{
		var ngaydubao = document.getElementById("danhsachsp");
		var capnhapbut = document.getElementById("capnhapbut");
		ngaydubao.style.display='none';
		capnhapbut.style.display='none';
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
	
	 function saveform()
	 { 
		 document.forms['ckForm'].action.value='save';
	     document.forms['ckForm'].submit();
	 }
	 
	 function readExcel()
	 {
		 document.forms['ckForm'].action.value='readExcel';
		 document.forms['ckForm'].setAttribute('enctype', "multipart/form-data", 0);
	     document.forms['ckForm'].submit();
	 }
	 
	 function exportExcel()
	 {
		 document.forms['ckForm'].action.value='exportExcel';
	     document.forms['ckForm'].submit();
	 }


	 function sellectAll()
	 {
		 var chkAll = document.getElementById("chkAll");
		 var spIds = document.getElementsByName("spIds");
		 
		 if(chkAll.checked)
		 {
			 for(i = 0; i < spIds.length; i++)
			 {
				 spIds.item(i).checked = true;
			 }
		 }
		 else
		 {
			 for(i = 0; i < spIds.length; i++)
			 {
				 spIds.item(i).checked = false;
			 }
		 }
	 }
	 
		function DinhDangTien(num) 
		 {
		    num = num.toString().replace(/\$|\,/g,'');
		    if(isNaN(num))
		    num = "0";
		    sign = (num == (num = Math.abs(num)));
		    num = Math.floor(num*100);
		    num = Math.floor(num/100).toString();
		    for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
		    num = num.substring(0,num.length-(4*i+3))+','+
		    num.substring(num.length-(4*i+3));
		    return (((sign)?'':'-') + num);
		 }

		 function Dinhdang(element)
		 {
		 
		 	element.value = DinhDangTien(element.value);
		 	if(element.value== '' )
		 	{
		 		element.value= '';
		 	}
		 	
		 }
	 
</script>
	
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0" >
<form name="ckForm" method="post" action="../../DubaoNam_Giay_UpdateSvl">
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="id" value='<%= dbkdBean.getId() %>' >
<input type="hidden" name="dvkdId" value='<%= dbkdBean.getDvkdId() %>' >
<input type="hidden" name="kbhId" value='<%= dbkdBean.getKenhId() %>' >

<input type="hidden" name="action" value='1'>


<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:60%; padding:5px; float:left" class="tbnavigation">&nbsp;Quản lý ngân sách > Dự báo kinh doanh > Cập nhật
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn <%= userTen %> &nbsp;
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="">
    	<A href= "../../DubaoNam_GiaySvl?userId=<%= userId %>" >
        	<img src="../images/Back30.png" alt="Quay về"  title="Quay về" border="1px" longdesc="Quay ve" style="border-style:outset"></A>
        <span id="btnSave">
	        <A href="javascript:saveform()" >
	        	<IMG src="../images/Save30.png" title="Lưu lại" alt="Lưu lại" border ="1px" style="border-style:outset"></A>
        </span>
    </div>
  	
  	<div align="left" style="width:100%; float:none; clear:left">
  		<fieldset>
    		<legend class="legendtitle"> Thông báo</legend>
    		<textarea name="dataerror"  rows="2" readonly="readonly" style ="width:100%"><%= dbkdBean.getMsg() %></textarea>
		         <% dbkdBean.setMsg(""); %>
    	</fieldset>
  	</div>
  	
    <div align="left" style="width:100%; float:none; clear:left">
    <fieldset>
    	<legend class="legendtitle">Dự báo kinh doanh</legend>
        	<div style="float:none; width:100%" align="left">
            <TABLE width="100%" cellpadding="6" cellspacing="0">
                <TR>
	                 <TD class="plainlabel" width="15%">Kế hoạch ngân sách</TD>
	                 <TD class="plainlabel">
	                    <input type="text"  id="ngansach"  name="ngansach" value="<%= dbkdBean.getNgansach() %>" maxlength="200" width="300px" readonly=readonly/></TD>
		                </TD>
                </TR>	
                  						
                <TR>
                    <TD width="20%"  class="plainlabel" valign="middle">Diễn giải<FONT class="erroralert">*</FONT></TD>
                    <TD width="40%"  class="plainlabel" >
                    	<input type="text"  id="diengiai" autocomplete="off" name="diengiai" value="<%= dbkdBean.getDiengiai() %>" maxlength="200" width="300px" /></TD>
                    	                    	
                </TR>  
                <TR>
                   <TD width="20%"  class="plainlabel" valign="middle">Năm</TD>
                   <TD width="80%"  align="left" class="plainlabel" >
                   	<input type="text"  autocomplete="off" id="nam" readonly="readonly" name="nam" value="<%= dbkdBean.getNam()%>" 
                   		maxlength="10" ></TD>
                </TR>  

                <TR>
                   	<TD width="20%"  class="plainlabel" valign="middle">Đơn vị kinh doanh </TD>
                   	<TD width="40%"  class="plainlabel" >
                   		<input type="text"  id="dvkd" name="dvkd" value="<%= dbkdBean.getDvkd() %>" maxlength="200" width="300px" readonly="readonly"/></TD>
                    	
					</TD>
                    	
                </TR>  
                     <TR >
                        <TD class="plainlabel" valign="middle">Kênh bán hàng </TD>
                        <TD  valign="middle" class="plainlabel">
                   		<input type="text"  id="kbh" name="kbh" value="<%= dbkdBean.getKBH() %>" maxlength="200" width="300px" readonly="readonly"/></TD>
                        </TD>                        
                    </TR>    


               <TR>
                    <TD class="plainlabel" valign="middle" >Hiển thị sản phẩm thuộc nhãn hàng</TD>
                    <TD class="plainlabel" valign="middle" >
                        <select id="nhanhang" name="nhanhang" onChange = "saveform();">
                          	<option value=""></option>
                           	<%
                        		if(nhRs  != null )
                        		{
                        			while(nhRs.next())
                        			{  
        	                			if( nhRs.getString("pk_seq").equals(dbkdBean.getNhanhang())){ %>
		                       				<option value="<%= nhRs .getString("pk_seq") %>" selected="selected" ><%= nhRs.getString("ten") %></option>
	                        			<%}else 
		                        			{ %>
		                        				<option value="<%= nhRs .getString("pk_seq") %>" ><%= nhRs.getString("ten") %></option>
		                        		 <% } 
	                        			} nhRs .close();
	                        		}
	                        	%>
                            </select>
                        </TD>                        
                    </TR>
                    <TR>
                        <TD class="plainlabel" valign="middle" >Hiển thị sản phẩm thuộc chủng loại</TD>
                        <TD class="plainlabel" valign="middle" >
                            <select  id="chungloai" name="chungloai" onChange = "saveform();">
                            	<option value=""></option>
                            	<%
	                        		if(clRs  != null)
	                        		{
	                        			while(clRs.next())
	                        			{  
	                        			if( clRs.getString("pk_seq").equals(dbkdBean.getChungloai())){ %>
	                        				<option value="<%= clRs .getString("pk_seq") %>" selected="selected" ><%= clRs.getString("ten") %></option>
	                        			<%}else 
		                        			{ %>
		                        				<option value="<%= clRs .getString("pk_seq") %>" ><%= clRs.getString("ten") %></option>
		                        		 <% } 
	                        			} clRs .close();
	                        		}
	                        	%>
                            </select>
                        </TD>                        
                    </TR>
                    <TR>
				  	  	<TD class="plainlabel">Đọc dữ liệu từ tập tin Excel</TD>
				  	  	<TD class="plainlabel" colspan = 3>
				  	  		<INPUT onchange="Upload()" type="file" name="filename" id="filename"  size="40" value=''>
				  	  	  &nbsp;&nbsp;&nbsp;	
				  	  	  <a href="javascript:readExcel();" class="button"> <img alt="" src="../images/button.png" style="top: -4px;"> Đọc tập tin</a>
				  	  	  
				  	  	  &nbsp;&nbsp;&nbsp;
				  	  	  <a href="javascript:exportExcel();" class="button"> <img alt="" src="../images/button.png" style="top: -4px;"> Xuất ra Excel</a>
				  	  	</TD>
						  	
				  	</TR>
            </TABLE>
            <hr> 
            </div>  
                    
           	<div align="left" >
           	<%  
            int th=0;
           	int[] tenthang = new int[12]; 
           	String width = "100%";
           	%>
           	
           	<table id="tab" border="0" cellpadding="6" cellspacing="1" width="<%= width %>">
           	<tr>
           	<td class = "tbheader" width = "15%" >Tên sản phẩm</td>
           	<td class = "tbheader" width = "5%" align = "center">Đơn vị</td>
           	
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
				
				System.out.println("DATA BEN JSP: " + data.length);
				int sodong = 0;
				for(int n = 0; n < dbkdBean.getCount(); n++)	
               	{ 
					//System.out.println("So dong: " + sodong + "  -- N la: " + n + " -- DATA: " + data[n][0] + "  -- DATA1: " + data[n+1][0] + " -- DATA2: " + data[n+2][0] );
               		sodong++;
					if(data[n][0] != null)
					{
           	%>

		
					<%} %>
			<% 
			
			if (sodong % 2 != 0){ %>					
				<tr class="tblightrow">
			<%}else{ %>
				<tr class="tbdarkrow">
			<%} %>
		         <%
		           	String spId = "";
		           	spId	= data[n][0].substring(0, data[n][0].indexOf(";"));		           	%>
					
				<td ><%= data[n][0].substring(data[n][0].indexOf(";") + 1, data[n][0].length()) %>

					<input type="hidden" style="width: 100%;text-align: left;border: none;background-color: #80CB9B" value="<%= spId  %>" name= "spId" readonly>
				</td>
			<%		n++; %>
			
				<TD align = "center" ><%= data[n][0] %></TD>			
		        <%
		        	n++;
		            for(int i = 0; i < 12 ;i++)
		            {
		           	%>
				        <td align="right" style="cursor: pointer;" >
				         	<input type="text" maxlength="15" onkeyup="Dinhdang(this)"  onkeypress="return isNumberKey2(event)" style="width: 100%; text-align:right;"  value="<%= formatter.format(Double.parseDouble(data[n][i])) %>" id="dukienban_<%= spId %>_<%= i %>" name= "dukienban_<%= spId %>_<%= i %>" >
				        </td>                 			
			    	<% } %>	
			</tr>
							
			<%	}  
				} catch(Exception e) { e.printStackTrace(); } %>
			</table>
        </div>      
     
     </fieldset>	
    </div>
</div>
<script type="text/javascript">
	
	
</script>

</form>

</BODY>

</html>
<% 
//dropdowncontent.init('spId', "right-top", 300, "click");
try{
	if(spRs!=null){
		spRs.close();
	}
	if(nhRs!=null){
		nhRs.close();
	}
	if(clRs!=null){
		clRs.close();
	}
}catch(Exception er){
	
}
finally{
	dbkdBean.close();
}
session.setAttribute("dbkdBean", null) ;  
  %>
