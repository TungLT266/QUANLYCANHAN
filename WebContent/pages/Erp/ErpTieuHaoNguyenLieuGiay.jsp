<%@page import="geso.traphaco.erp.beans.lenhsanxuatgiay.imp.ErpTieuhaonguyenlieuList"%>
<%@page import="geso.traphaco.erp.beans.lenhsanxuatgiay.IErpTieuhaonguyenlieulist"%>
<%@page import="geso.traphaco.center.util.Utility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "java.sql.ResultSet" %>


<% IErpTieuhaonguyenlieulist obj = (ErpTieuhaonguyenlieuList)session.getAttribute("obj"); %>
<% ResultSet lsxList = (ResultSet)obj.GetRsListTieuHao(); %>
<% ResultSet xuongRs = (ResultSet)obj.getXuongRs(); %>
<% String userId = (String) session.getAttribute("userId");  %>
<% String nppId = (String) session.getAttribute("nppId");  %>
<% String userTen = (String) session.getAttribute("userTen"); 

String sum = (String) session.getAttribute("sum");
Utility util = (Utility) session.getAttribute("util");
if(!util.check(userId, userTen, sum)){
	response.sendRedirect("/TueLinh/index.jsp");
}else{	
	 int[] quyen = new  int[11];
	 quyen = util.GetquyenNew("ErpTieuhaonguyenlieugiaySvl","&loaiNPP=0&isKH=0",userId);

	 obj.setNextSplittings(); %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>TueLinh - Project</title>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
    <LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
    <LINK rel="stylesheet" href="../css/main.css" type="text/css">
    
    <script type="text/javascript" src="../scripts/phanTrang.js"></script>
	<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
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
	 function submitform()
	 {   
	    document.forms["erpLsxForm"].submit();
	 }
	 function newform()
	 {   
		document.forms["erpLsxForm"].action.value = "Taomoi";
	    document.forms["erpLsxForm"].submit();
	 }
	 function clearform()
	 {   
	 
	    document.forms["erpLsxForm"].tungaySX.value = "";
	    document.forms["erpLsxForm"].denngaySX.value = "";
	    document.forms["erpLsxForm"].sochungtu.value = "";
	    document.forms["erpLsxForm"].trangthai.value = "";
	    document.forms["erpLsxForm"].xuongId.value = "";
	    document.forms["erpLsxForm"].solenhsx.value = "";
	    document.forms["erpLsxForm"].submit();
	 }
	 
	 function thongbao()
	 {
		 var tt = document.forms["erpLsxForm"].msg.value;
	 	 if(tt.length>0)
	     	alert(document.forms["erpLsxForm"].msg.value);
	 	}
	</SCRIPT>
	<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
    	$(document).ready(function() { $("select").select2(); });
     
 	</script>	
</head>
<body>
<form name="erpLsxForm" method="post" action="../../ErpTieuhaonguyenlieugiaySvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="nppId" value="<%= nppId %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="currentPage" value="<%= obj.getCurrentPage() %>" >

<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">
        	Quản lý cung ứng > Sản xuất > Tiêu hao nguyên liệu
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
                        <TD class="plainlabel" width="150px">Từ ngày </TD>
                        <TD class="plainlabel" width="300px">
                            <input type="text" class="days" 
                                   id="tungaySX" name="tungaySX" value="<%= obj.getNgayBanDau() %>" maxlength="10"  onchange="submitform();"/>
                        </TD>
                        
                        <TD class="plainlabel" width="150px">Đến ngày </TD>
                        <TD class="plainlabel">
                            <input type="text" class="days" id="denngaySX" name="denngaySX" value="<%= obj.getNgayKetThuc() %>" maxlength="10" onchange="submitform();"/>
                        </TD>
                    </TR>
                   
                    <TR>
                        <TD class="plainlabel" >Số chứng từ</TD>
                        <TD class="plainlabel">
                            <input type="text" 
                                   id="sochungtu" name="sochungtu" value="<%= obj.getSochungtu() %>" maxlength="10" onchange="submitform()" />
                        </TD>
                        
                        <TD class="plainlabel" >Số lệnh sản xuất</TD>
                        <TD class="plainlabel">
                            <input type="text" 
                                   id="solenhsx" name="solenhsx" value="<%= obj.getLsxId() %>" maxlength="10" onchange="submitform()" />
                        </TD>
                    </TR>
                    
                     <TR>
                        <TD class="plainlabel" valign="middle">Xưởng </TD>
                       <TD class="plainlabel">
								<select name="xuongId" id="xuongId" onChange="submitform();" style ="width:200px">
									<option value="">Tất cả </option>
									<%
										if(xuongRs != null)
										{
											try
											{
												while(xuongRs.next())
												{  									 
												if( xuongRs.getString("pk_seq").equals(obj.getXuongId())){ %>
												<option value="<%= xuongRs.getString("pk_seq") %>" selected="selected" ><%= xuongRs.getString("tennhamay") %></option>
												<%		}else { %>
												<option value="<%= xuongRs.getString("pk_seq") %>" ><%= xuongRs.getString("tennhamay") %> </option>
												<% 		} 
												} 
												xuongRs.close();
											}catch(java.sql.SQLException ex){}
										}
								   %>
								</select>
							</TD>	
                        
                        <TD class="plainlabel" valign="middle">Trạng thái </TD>
                        <TD class="plainlabel" valign="middle" >
                           <select name="trangthai" id="trangthai" onchange="submitform();" style ="width:200px">
                           			
								<% if (obj.getTrangthai().equals("1")){%>
									<option value="">Tất cả </option>
								  	<option value="1" selected>Đã tiêu hao</option>							
								  	<option value="2">Đã hủy</option>
								<%} else  if(obj.getTrangthai().equals("2")) { %>
									<option value="">Tất cả </option>
									<option value="1" >Đã tiêu hao</option>
								  
								  	<option value="2">Đã hủy</option>
								
								<% } else { %>
									<option value="">Tất cả </option>
									<option value="" selected="selected"> </option>
									<option value="1" >Đã tiêu hao</option>
								  	<option value="2">Đã hủy</option>
								<% }  %>
                           </select>
                        </TD>                     
                    </TR>    
                    <tr>
                        <td colspan="4" class="plainlabel">
                            <a class="button" href="javascript:submitform()">
                                <img style="top: -4px;" src="../images/Search30.png" alt="">Tìm kiếm </a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <a class="button2" href="javascript:clearform()">
                                <img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;
                        </td>
                    </tr>        					
                </TABLE>                      
        </fieldset>                      
    	</div>
        <div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        	<fieldset>
            	<legend><span class="legendtitle"> Tiêu hao nguyên liệu </span>&nbsp;&nbsp;
      								<%if(quyen[Utility.THEM]!=0){ %> 
									<a class="button3" href="javascript:newform()"> 
										<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới
									</a><%} %>
      
                </legend>
                
                
                
                
            	<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
					<TR class="tbheader">
					  <TH align="center" width="7%">Chứng từ</TH>
	                    <TH align="center" width="7%">Lệnh sản xuất</TH>
	                  <TH align="center" width="9%">Ngày tiêu hao</TH>
	                   
	                     <TH align="center" width="15%">Nhà máy</TH>
	                    <TH align="center" width="5%">Trạng thái</TH>
	                    <TH align="center" width="8%">Ngày tạo</TH>
	                    <TH align="center" width="10%"> Người tạo</TH>
	                    <TH align="center" width="8%">Ngày sửa</TH>
	                    <TH align="center" width="10%">Người sửa</TH>
	                    <TH align="center" width="10%" >Tác vụ</TH>
	                </TR>
					<%
                 		if(lsxList != null)
                 		{
                 			int m = 0;
                 			while(lsxList.next())
                 			{  		
                 				if((m % 2 ) == 0) {%>
		                         	<TR class='tbdarkrow'>
		                        <%}else{ %>
		                          	<TR class='tblightrow'>
		                        <%} %>
		                          <TD align="center"><%= lsxList.getString("CHUNGTU") %></TD>
		                    <TD align="center"><%= lsxList.getString("pk_seq") %></TD>
		                    <TD align="center"><%= lsxList.getString("ngaytieuhao") %></TD>
		                  <%--   <TD align="center"><%= lsxList.getString("diengiai") %></TD> --%>
		                
		                    <TD ><%= lsxList.getString("tennhamay") %></TD>  
		                    
		                    <TD align="center">
		                    	<%
		                    		String trangthai = "";
		                    		String tt = lsxList.getString("trangthai");
		                    		if(tt.equals("1"))
		                    			trangthai = "Đã tiêu hao";
		                    		else if(tt.equals("2"))
		                    		{
		                    			trangthai="Đã hủy";
		                    		}
		                    	 if (tt.equals("2")){%>
		                    		<span style="color: red;">
		                    			<%= trangthai %>
		                    		</span>
		                    	<%} else{ %>
		                    	<%= trangthai %>
		                    	<%} %>
		                    </TD>   									                                    
					     	<TD align="center"><%= lsxList.getString("Ngaytao") %></TD>	
		                    <TD ><%= lsxList.getString("NguoiTao") %></TD>
         					<TD align="center"><%= lsxList.getString("NgaySua") %></TD>
							<TD ><%= lsxList.getString("NguoiSua") %></TD>
									
		                    <TD align="center"> 
		                    <% if(tt.equals("0")){ %>
		                   		
 								<%if(quyen[Utility.SUA]!=0){ %>
                               
                                <A href = "../../ErpLenhsanxuatgiayActionSvl?userId=<%=userId%>&update=<%= lsxList.getString("CHUNGTU")%>&lsx=<%= lsxList.getString("pk_seq")%>"><IMG src="../images/Edit20.png" alt="Hiển thị" title="Hiển thị" border=0></A>&nbsp;	
                                <%} %>	
                                
                                <%if(quyen[Utility.XEM]!=0){ %>
                               
                                <A href = "../../ErpTieuhaonguyenlieugiaySvl?userId=<%=userId%>&display=<%= lsxList.getString("CHUNGTU")%>&lsx=<%= lsxList.getString("pk_seq")%>"><IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0></A>&nbsp;	
                                <%} %>	
                                <%if(quyen[Utility.CHOT]!=0){ %>
                               
                                <A href = "../../ErpTieuhaonguyenlieugiaySvl?userId=<%=userId%>&chot=<%= lsxList.getString("CHUNGTU")%>&lsx=<%= lsxList.getString("pk_seq")%>"><IMG src="../images/Chot.png" alt="Chốt phiếu" title="Chốt phiếu" border=0></A>&nbsp;	
                                <%} %>	
                                 <%if(quyen[Utility.XOA]!=0){ %>
                               
                                <A href = "../../ErpTieuhaonguyenlieugiaySvl?userId=<%=userId%>&delete=<%= lsxList.getString("CHUNGTU")%>&lsx=<%= lsxList.getString("pk_seq")%>"><IMG src="../images/Delete.png" alt="Xóa phiếu" title="Xóa phiếu" border=0></A>&nbsp;	
                                <%} %>	
                                
                                								
		                    <%} else{ if(quyen[Utility.XEM]!=0){ %>
		                    	<A href = "../../ErpTieuhaonguyenlieugiaySvl?userId=<%=userId%>&display=<%= lsxList.getString("CHUNGTU")%>&lsx=<%= lsxList.getString("pk_seq")%>"><IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0></A>&nbsp;	
		                    <% }}  %>
		                    </TD>
		                </TR>
                     <% m++; } lsxList.close(); } %>
					<tr class="tbfooter" > 
						 <TD align="center" valign="middle" colspan="13" class="tbfooter">
						 						<% obj.setNextSplittings(); %>
												 <script type="text/javascript" src="../scripts/phanTrang.js"></script>
												<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>" >
												<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>" >

											 	<%if(obj.getNxtApprSplitting() >1) {%>
													<img alt="Trang Dau" src="../images/first.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, 1, 'view')"> &nbsp;
												<%}else {%>
													<img alt="Trang Dau" src="../images/first.gif" > &nbsp;
													<%} %>
												<% if(obj.getNxtApprSplitting() > 1){ %>
													<img alt="Trang Truoc" src="../images/prev.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, eval(document.forms[0].nxtApprSplitting.value) -1, 'view')"> &nbsp;
												<%}else{ %>
													<img alt="Trang Truoc" src="../images/prev_d.gif" > &nbsp;
												<%} %>
												
												<%
													int[] listPage = obj.getNextSplittings();
													for(int i = 0; i < listPage.length; i++){
												%>
												
												<% 
												
												System.out.println("Current page:" + obj.getNxtApprSplitting());
												System.out.println("List page:" + listPage[i]);
												
												if(listPage[i] == obj.getNxtApprSplitting()){ %>
												
													<a  style="color:white;"><%= listPage[i] %>/ <%=obj.getTheLastSplitting() %></a>
												<%}else{ %>
													<a href="javascript:View(document.forms[0].name, <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
												<%} %>
													<input type="hidden" name="list" value="<%= listPage[i] %>" />  &nbsp;
												<%} %>
												
												<% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
													&nbsp; <img alt="Trang Tiep" src="../images/next.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, eval(document.forms[0].nxtApprSplitting.value) +1, 'view')"> &nbsp;
												<%}else{ %>
													&nbsp; <img alt="Trang Tiep" src="../images/next_d.gif" > &nbsp;
												<%} %>
												<%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
											   		<img alt="Trang Cuoi" src="../images/last.gif" > &nbsp;
										   		<%}else{ %>
										   			<img alt="Trang Cuoi" src="../images/last.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, -1, 'view')"> &nbsp;
										   		<%} %>
						</TD>
					 </tr>
				</TABLE>
            </fieldset>
        </div>
    </div>  
</div>
</form>
<%


try{
	if(xuongRs != null)
		xuongRs.close();
	if(lsxList != null)
		lsxList.close();
	obj.DBclose();
 	session.setAttribute("obj",null);

}catch(Exception er){
	
}
} %>
</body>
</html>