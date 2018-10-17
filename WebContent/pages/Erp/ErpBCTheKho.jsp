<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.NumberFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.erp.beans.bcthekho.*" %>
<%@ page  import = "geso.traphaco.erp.servlets.bcthekho.*" %>
<%@ page  import = "java.sql.ResultSet" %>

<% IBaocao obj = (IBaocao)session.getAttribute("obj"); %>
<% ResultSet lspRs = (ResultSet)obj.getLoaiSanPhamRs(); %>
<% ResultSet dvkdRs = (ResultSet)obj.getDvkdRs(); %>
<% ResultSet maspRs = (ResultSet)obj.getMaSanPhamRs(); %>
<% ResultSet spRs = (ResultSet)obj.getSanPhamRs(); %>
<% ResultSet spbooked = (ResultSet)obj.getSP_booked_detail(); %>
<% ResultSet spDetail = (ResultSet)obj.getSP_detailRs(); %>
<% ResultSet tondau = (ResultSet)obj.getTondau(); %>
<% ResultSet dvtRs = (ResultSet)obj.get_dvtRs(); %>
<% ResultSet khoRs = (ResultSet)obj.getKhoRs(); %>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  %>
<% List<TheKhoObj> ListTheKho = (List<TheKhoObj>)session.getAttribute("ListTheKho");  %>
<% String ListTheKho_check = (String) session.getAttribute("ListTheKho_check"); %>
<% Double ListTheKho_tondau = (Double) session.getAttribute("ListTheKho_tondau"); %>
<% String pattern = "###,###.###";
   DecimalFormat formatter = new DecimalFormat(pattern); %>
   

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<TITLE>TraphacoHYERP - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
    <LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
    <LINK rel="stylesheet" href="../css/main.css" type="text/css">
    <LINK rel="stylesheet" href="../css/datepicker.css" type="text/css">
    
    <script language="javascript" src="../scripts/datepicker.js"></script>
   	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css"/>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery.min.js"></script>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery-ui.min.js"></script>
  	<script type="text/javascript" src="../scripts/phanTrang.js"></script>
  	<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
   
   
    <LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs.css">
	<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs-panes.css">
    <script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>
<script  type="text/javascript">
//perform JavaScript after the document is scriptable.
$(document).ready(function() {

    //When page loads...
    $(".tab_content").hide(); //Hide all content
    var index = $("ul.tabs li.current").show().index(); 
    $(".tab_content").eq(index).show();

    //On Click Event
    $("ul.tabs li").click(function() {
  
        $("ul.tabs li").removeClass("current"); //Remove any "active" class
        $(this).addClass("current"); //Add "active" class to selected tab
 		$(".tab_content").hide(); //Hide all tab content  
        var activeTab = $(this).find("a").attr("href"); //Find the href attribute value to identify the active tab + content  
        $(activeTab).show(); //Fade in the active ID content
        return false;
    });

});
</script>

	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
	<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.datepicker.js"
		type="text/javascript"></script>
	<script type="text/javascript">
		$(document).ready(function() {		
				$( ".days" ).datepicker({			    
						changeMonth: true,
						changeYear: true				
				});            
	        }); 		
			
	</script>
 
 	<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { $(".select2").select2(); });
     
 </script>
 
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
		$(document).ready(function(){
            $(".button3").hover(function(){
                $(".button3 img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
    </script>
    <SCRIPT language="javascript" type="text/javascript">
	 function confirmLogout()
	 {
	    if(confirm("Bạn có muốn đăng xuất?"))
	    {
			top.location.href = "home.jsp";
	    }
	    return
	 }
	 
	 function submitform()
	 { 
		 if(document.getElementById("tungay").value == "")
		 {
			 alert('Vui lòng chọn thời gian bắt đầu lấy báo cáo');
			 return;
		 }
		 
		 if(document.getElementById("denngay").value == "")
		 {
			 alert('Vui lòng chọn thời gian kết thúc lấy báo cáo');
			 return;
		 }
		 
		 if(document.getElementById("khoId").value == "")
		 {
			 alert('Vui lòng chọn kho');
			 return;
		 }
		 
		 if(document.getElementById("sanpham").value == "")
		 {
			 alert('Vui lòng chọn sản phẩm');
			 return;
		 }
		 
		 document.forms["erpDmhForm"].action.value = "submit";
	     document.forms["erpDmhForm"].submit();
	 }
	 
	 function submitformthugon()
	 { 
		 if(document.getElementById("tungay").value == "")
		 {
			 alert('Vui lòng chọn thời gian bắt đầu lấy báo cáo');
			 return;
		 }
		 
		 if(document.getElementById("denngay").value == "")
		 {
			 alert('Vui lòng chọn thời gian kết thúc lấy báo cáo');
			 return;
		 }
		 
		 if(document.getElementById("khoId").value == "")
		 {
			 alert('Vui lòng chọn kho');
			 return;
		 }
		 
		 if(document.getElementById("sanpham").value == "")
		 {
			 alert('Vui lòng chọn sản phẩm');
			 return;
		 }
		 
		 document.forms["erpDmhForm"].action.value = "thugon";
	     document.forms["erpDmhForm"].submit();
	 }
	 
	 function submitweb()
	 { 
		 if(document.getElementById("tungay").value == "")
		 {
			 alert('Vui lòng chọn thời gian bắt đầu lấy báo cáo');
			 return;
		 }
		 
		 if(document.getElementById("denngay").value == "")
		 {
			 alert('Vui lòng chọn thời gian kết thúc lấy báo cáo');
			 return;
		 }
		 
		 if(document.getElementById("khoId").value == "")
		 {
			 alert('Vui lòng chọn kho');
			 return;
		 }
		 
		 if(document.getElementById("sanpham").value == "")
		 {
			 alert('Vui lòng chọn sản phẩm');
			 return;
		 }
		 
		 document.forms["erpDmhForm"].action.value = "web";
	     document.forms["erpDmhForm"].submit();
	 }
	 
	 function locsanpham()
	 {   
		 document.forms["erpDmhForm"].action.value = "search";
	     document.forms["erpDmhForm"].submit();
	 }
	
	 function thongbao()
	 {
		 var tt = document.forms["erpDmhForm"].msg.value;
	 	 if(tt.length>0)
	     	alert(document.forms["erpDmhForm"].msg.value);
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
	 
	 function sellectAll2()
	 {
		 var chkAll = document.getElementById("chkAll2");
		 var spIds = document.getElementsByName("clIds");
		 
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
	 
	 function sellectAll3()
	 {
		 var chkAll = document.getElementById("chkAll3");
		 var spIds = document.getElementsByName("loaisanpham");
		 
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
	 
	 function sellectAll4()
	 {
		 var chkAll = document.getElementById("chkAll4");
		 var dvkdIds = document.getElementsByName("dvkdId");
		 
		 if(chkAll.checked)
		 {
			 for(i = 0; i < dvkdIds.length; i++)
			 {
				 dvkdIds.item(i).checked = true;
			 }
		 }
		 else
		 {
			 for(i = 0; i < dvkdIds.length; i++)
			 {
				 dvkdIds.item(i).checked = false;
			 }
		 }
	 }
	 
	 function viewform(){
		 document.forms["erpDmhForm"].action.value = "view";
	     document.forms["erpDmhForm"].submit();
	 }
	 
	 function InPDF(){
		 document.forms["erpDmhForm"].action.value = "inPDF";
	     document.forms["erpDmhForm"].submit();
	 }
	 
	</SCRIPT>
</head>
<body>
<form name="erpDmhForm" method="post" action="../../ErpBCThekhoSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="userTen" value="<%= userTen %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="khoTen" id="khoTen" value="" >

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">
        	&#160; Quản lý tồn kho > Báo cáo > BC Thẻ kho
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng Bạn <%= userTen %> &nbsp;
        </div>
    </div>
  	<div id="cotent" style="width:100%; float:none">
    	<div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        <fieldset style="margin-top:5px" >
            <legend class="legendtitle"> Tiêu chí tìm kiếm</legend>
                <TABLE width="100%" cellpadding="6px" cellspacing="0px" style="margin-top: 5px " >								                          
                    <TR>
                        <TD class="plainlabel" width="120px;">Từ ngày </TD>
                        <TD class="plainlabel" width="230px;" >
                            <input type="text" class="days" 
                                   id="tungay" name="tungay" value="<%= obj.getTuNgay() %>" maxlength="10" />
                        </TD>
                        
                         <TD class="plainlabel" width="100px" >Đến ngày </TD>
                        <TD class="plainlabel" >
                            <input type="text" class="days" 
                                   id="denngay" name="denngay" value="<%= obj.getDenNgay() %>" maxlength="10" />
                        </TD>
                    </TR>
                    

                     <TR>
                        <TD class="plainlabel" valign="middle" >Kho </TD>
                        <TD class="plainlabel" valign="middle" colspan="3" >
                            <select name="khoId" id="khoId" onchange="locsanpham()" style="width: 600px;" class="select2" >
                            	<option value=""></option>
                            	<%
	                        		if(khoRs != null)
	                        		{
	                        			while(khoRs.next())
	                        			{  
	                        			if( khoRs.getString("pk_seq").equals(obj.getKhoIds())){ %>
	                        				<option value="<%= khoRs.getString("pk_seq") %>" selected="selected" ><%= khoRs.getString("khoTen") %></option>
	                        			<%}else { %>
	                        				<option value="<%= khoRs.getString("pk_seq") %>" ><%= khoRs.getString("khoTen") %></option>
	                        		 <% } } khoRs.close();
	                        		}
	                        	%>
                            </select>
                        </TD>
                      
                    <TR>
                    	<TD class="plainlabel" valign="middle" > Loại sản phẩm </TD>
                        <TD class="plainlabel" valign="middle" colspan="3">
                        	<select name="loaisanpham" onchange="locsanpham();" style="width: 600px;" class="select2" >
                        		<option></option>
                        		<%if(lspRs!=null){
                        			try{
                        				while(lspRs.next()){
                        				if(lspRs.getString("pk_seq").equals(obj.getLoaiSanPhamIds())){	%>
                        			<option value="<%= lspRs.getString("pk_seq") %>" selected="selected"> <%= lspRs.getString("ten") %></option>
                        		<%}else{ %>
                        			<option value="<%= lspRs.getString("pk_seq") %>"> <%= lspRs.getString("ten") %></option>
                        		
                        		<%}}}catch(Exception e){e.printStackTrace();}}%>
                        	</select>
                    </TR>
            
                    <TR>
                        <TD class="plainlabel" valign="middle" >Chọn sản phẩm </TD>
                        <TD class="plainlabel" valign="middle" colspan="3" >
                       
				            	<select name="sanpham" id="sanpham" style="width: 600px;" class="select2" >
				            		<option></option>
				            		<%if(spRs!=null){
				            			try{
				            				while(spRs.next()){ 
				            				if (spRs.getString("pk_seq").equals(obj.getspId()))	{
				            		%>
				            			<option value="<%= spRs.getString("pk_seq") %>" selected="selected"><%= spRs.getString("pk_seq")%> - <%= spRs.getString("ma")%> - <%= spRs.getString("ten") %> </option>
				            		<%}else{ %>	
				            			<option value="<%= spRs.getString("pk_seq") %>"><%= spRs.getString("pk_seq")%> - <%= spRs.getString("ma")%> - <%= spRs.getString("ten")%> </option>		
				            		<%}}}catch(Exception e){e.printStackTrace();}}%>
				            	</select>          
                        </TD>                        
                    </TR>
                    
                    <TR>
                        <TD class="plainlabel" valign="middle" >Số lô </TD>
                        <TD class="plainlabel" valign="middle" colspan="3" >
                       		
				            <input type="text" name="solo" value="<%= obj.getSolo() %>" >	    
                        </TD>                        
                    </TR>
                    
                    <tr class="plainlabel" style="display: none;" >
                    	<td>Đơn vị tính</td>
                    	<td colspan="3">
                    	<select name="donvitinh" id="donvitinh" style="width: 200px;" >
                    		<option value=""></option>
                    		<%
                    		if(dvtRs!=null){
                    			try{
                    				while(dvtRs.next()){
                    		%>	
                    			<% if( dvtRs.getString("DVDL_FK").equals(obj.get_dvtId())){ %>		
                    				<option value="<%= dvtRs.getString("DVDL_FK")%>" selected="selected"><%= dvtRs.getString("DIENGIAI")%></option>
                    			<%}else { %>
                    				<option value="<%= dvtRs.getString("DVDL_FK")%>"><%= dvtRs.getString("DIENGIAI")%></option>
                    			<%} %>
                    		<%}}catch(Exception e){e.printStackTrace();}}%>
                    	</select></td> 
                    </tr>
                    
                    
                    <TR>
             
                        <td colspan="4" class="plainlabel">
                            <a class="button" href="javascript:submitform();"> 
                            	<img style="top: -4px;" src="../images/button.png" alt=""> Tạo báo cáo  </a> &nbsp;&nbsp;&nbsp;
                            <a class="button" href="javascript:submitformthugon();"> 
                            	<img style="top: -4px;" src="../images/button.png" alt=""> Tạo báo cáo thu gọn  </a> &nbsp;&nbsp;&nbsp;
                            <a class="button" href="javascript:submitweb();"> 
                            	<img style="top: -4px;" src="../images/button.png" alt=""> Xem trên web </a> &nbsp;&nbsp;&nbsp;
                            	
                             <a class="button" href="javascript:viewform();" style="display: none;" > 
                            	<img style="top: -4px;" src="../images/button.png" alt=""> Xem chi tiết  </a> &nbsp;&nbsp;&nbsp;	
                            	
                            <a class="button" href="javascript:InPDF();" style="display: none;" > 
                            	<img style="top: -4px;" src="../images/button.png" alt="">In phiếu  </a>		
                        </td>
                    </tr> 
                    
                    </TABLE> 
                               
       </fieldset> 
       
         </div>
         
         <!-- dữ liệu trên nền web -->
         <% if(ListTheKho_check!=null){ %>
    	<div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        	<fieldset>
            	<legend><span class="legendtitle"> Kết quả  </span>&nbsp;&nbsp;
                </legend>
            	<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
            	
					<TR class="tbheader">
	                    <TH align="center" width="8%">Mã Chứng từ</TH>
	                    <TH align="center" width="8%">Loại chứng từ</TH>
	                 	<TH align="center" width="12%">Ngày chứng từ </TH>  
	                 	<TH align="center" width="12%">Ngày chốt </TH>  
	                 	
	                    <TH align="center" width="12%">Số lô</TH> 
	                    <TH align="center" width="10%">Ngày hết hạn</TH>
                   
                   
	                    <TH align="center" width="8%">Mã mẻ</TH>
	                    <TH align="center" width="8%">Mã thùng</TH>
	                    <TH align="center" width="10%">Vị trí</TH>
	                    <TH align="center" width="10%">Mã phiếu</TH>
	                    <TH align="center" width="10%" >Phiếu định tính</TH>
	                    <TH align="center" width="10%" >Phiếu EO</TH>
	                    <TH align="center" width="10%" >Mã marquette</TH>
	                    <TH align="center" width="10%" >Hàm lượng</TH>
	                    <TH align="center" width="10%" >Hàm ẩm</TH>
	                  <TH align="center" width="10%" >NSX</TH>
	                    
	                    <TH align="center" width="10%" >Xuất</TH>
	                    <TH align="center" width="10%" >Nhập</TH>
	                    <TH align="center" width="10%" >Tồn</TH>
	                </TR>
	                
	                <TR>
	                	<TD colspan="18" align="center">
	                		<span style="font-size: 12pt; font-family: Arial;">Đầu kỳ: +<%= formatter.format(ListTheKho_tondau.doubleValue()) %></span>
	                	</TD>
	                </TR>
	                <%
	                	if(ListTheKho!=null){
	                		for(int i=0; i< ListTheKho.size();i++){
	                			TheKhoObj temp = ListTheKho.get(i);
	                			
	                			if((i % 2 ) == 0) {%>
	                         	<TR class='tbdarkrow'>
	                        <%}else{ %>
	                          	<TR class='tblightrow'>
	                        <%} %>
								<TD align="center"><%=temp.getMachungtu() %></TD>
								<TD align="center"><%=temp.getLoaichungtu() %></TD>
								<TD align="center"><%=temp.getNgaychungtu() %></TD>
								<TD align="center"><%=temp.getNgaychot() %></TD>
								
								
								<TD align="center"><%=temp.getSolo() %></TD>
								<TD align="center"><%=temp.getNgayhethan() %></TD>
							
								
								
								<TD align="center"><%=temp.getMame() %></TD>
								<TD align="center"><%=temp.getMathung() %></TD>
								<TD align="center"><%=temp.getVitri() %></TD>
								<TD align="center"><%=temp.getMaphieu() %></TD>
								<TD align="center"><%=temp.getPhieuDT() %></TD>
								<TD align="center"><%=temp.getPhieuEO() %></TD>
								<TD align="center"><%=temp.getMarq() %></TD>
								<TD align="center"><%=temp.getHamluong()  %></TD>
								<TD align="center"><%=temp.getHamam()  %></TD>
								<TD align="center"><%=temp.getNsx()  %></TD>
							
								<TD align="center"><%= formatter.format(temp.getXuat())  %></TD>
								<TD align="center"><%= formatter.format(temp.getNhap())  %></TD>
								<TD align="center"><%= formatter.format(temp.getTon())  %></TD>
								
							</TR>	
	                		<%}
	                	}
	                %>
	                
	                
				</TABLE>
            </fieldset>
        </div>
        <%} %>
        
    </div>  
</div>
</form>
</body>
<script type="text/javascript">
	dropdowncontent.init('lspId', "right-bottom", 300, "click");
	dropdowncontent.init('dvkdId', "right-bottom", 300, "click");
	dropdowncontent.init('masanpham', "right-bottom", 300, "click");
	dropdowncontent.init('spId', "right-bottom", 300, "click");
	
</script>
</html>
<%

try
{
if(dvkdRs!=null)dvkdRs.close();
if(dvtRs!=null)dvtRs.close();
if(khoRs!=null)khoRs.close();
	session.removeAttribute("ListTheKho");
	if(ListTheKho_check!=null)
	session.removeAttribute("ListTheKho_check");
	if(ListTheKho_tondau!=null)
		session.removeAttribute("ListTheKho_tondau");
}
catch(Exception ex)
{
	ex.printStackTrace();
}
finally
{
	obj.close();
	obj=null;
	session.setAttribute("",null);
}

%>