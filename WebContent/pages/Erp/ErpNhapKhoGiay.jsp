<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.erp.beans.nhapkho.giay.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<% IErpNhapkhoList obj = (IErpNhapkhoList)session.getAttribute("obj"); %>
<% ResultSet nhapkhoList = (ResultSet)obj.getNhapKhoList(); %>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  %>
<% ResultSet xuongRs = (ResultSet)obj.getXuongRs(); %>
<% obj.setNextSplittings(); 
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{
		int[] quyen = new  int[5];
		quyen = util.Getquyen("ErpNhapkhoLsxSvl","&loaiNPP=0&isKH=0",userId);
		
		ResultSet rsdvkd =obj.getRsDvkd();
		
	%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Canfoco - Project</title>
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
	    document.forms["erpNkForm"].submit();
	 }
	 function newform()
	 {   
		document.forms["erpNkForm"].action.value = "Tao moi";
	    document.forms["erpNkForm"].submit();
	 }
	 function clearform()
	 {   
		document.forms["erpNkForm"].LsxId.value = "";
	    document.forms["erpNkForm"].sonhanhang.value = "";
	    document.forms["erpNkForm"].tungay.value = "";
	    document.forms["erpNkForm"].denngay.value = "";
	    document.forms["erpNkForm"].dvkdid.value = "";
	    document.forms["erpNkForm"].xuongId.value = "";
	    document.forms["erpNkForm"].submit();
	 }
	 function thongbao()
	 {
		 var tt = document.forms["erpNkForm"].msg.value;
	 	 if(tt.length>0)
	     	alert(document.forms["erpNkForm"].msg.value);
	 }
	 

	 function processing(id,chuoi)
	 {
 	    document.getElementById(id).innerHTML = "<a href='#'><img src='../images/waiting.gif' width = '20' height = '20' title='cho thuc hien..' border='0' longdesc='cho thuc hien..' style='border-style:outset'> Proc...</a>"; 		  
 	 	document.getElementById(id).href=chuoi;
 	 }
	</SCRIPT>
	<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
    	$(document).ready(function() { $("select").select2(); });
     
 	</script>	
</head>
<body>
<form name="erpNkForm" method="post" action="../../ErpNhapkhoLsxSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="currentPage" value="<%= obj.getCurrentPage() %>" >

<input type="hidden" name="msg" value='<%= obj.getmsg() %>'>
<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">
        	Quản lý cung ứng > Sản xuất > Nhập kho 
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
                        <TD class="plainlabel" width="200px">Từ ngày </TD>
                        <TD class="plainlabel">
                            <input type="text" class="days" 
                                   id="tungay" name="tungay" value="<%= obj.getTungay() %>" maxlength="10" onchange="submitform()" />
                        </TD>
                        
                        <TD class="plainlabel" width="200px">Đến ngày </TD>
                        <TD class="plainlabel">
                            <input type="text" class="days" 
                                   id="denngay" name="denngay" value="<%= obj.getDenngay() %>" maxlength="10" onchange="submitform()" />
                        </TD>
                        
                    </TR>
                    
                    <TR>
                        <TD class="plainlabel" valign="middle">Số nhập kho</TD>
                        <TD class="plainlabel" valign="middle">
                            <input type="text" name="sonhanhang" value="<%= obj.getSoPnh() %>" onchange="submitform()">
                        </TD>  
                        
                        <TD class="plainlabel" valign="middle">Số lệnh sản xuất</TD>
                        <TD class="plainlabel" valign="middle">
                            <input type="text" name="LsxId" value="<%= obj.getSoLSX() %>" onchange="submitform()">
                        </TD>                            
                    </TR>                    
                    
                       <TR>
                        <TD class="plainlabel" valign="middle">Đơn vị kinh doanh</TD>
                        <TD class="plainlabel" valign="middle">
                             <select name="dvkdid" onchange="submitform()" style = "width:200px">
                            	<option value="">Tất cả</option>
                            	<%
	                        		if(rsdvkd != null)
	                        		{
	                        			while(rsdvkd.next())
	                        			{  
	                        			  if( rsdvkd.getString("pk_seq").equals(obj.getIdDvkd() )){ %>
	                        				<option value="<%= rsdvkd.getString("pk_seq") %>" selected="selected" ><%= rsdvkd.getString("ten") %></option>
	                        			<%}else { %>
	                        				<option value="<%= rsdvkd.getString("pk_seq") %>" ><%= rsdvkd.getString("ten") %></option>
	                        		 <% } } rsdvkd.close();
	                        		}
	                        	%>
                            </select>
                        </TD> 
                        
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
            	<legend><span class="legendtitle"> Nhập kho </span>&nbsp;&nbsp;
                </legend>
            	<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
					<TR class="tbheader">
	                    <TH align="center">Mã nhập kho</TH>
	                    <TH align="center">Ngày nhập kho</TH>
	                    <TH align="center">Số lệnh sản xuất</TH>
	                  <!--   <TH align="center">Công đoạn</TH> -->
	                    <TH align="left">Nội dung nhập</TH>
	                    <TH align="center">Trạng thái</TH>
	                    <TH align="center">Ngày tạo</TH>
	                    <TH align="left"> Người tạo </TH>
	                    <TH align="center"> Ngày sửa </TH>
	                    <TH align="left"> Người sửa </TH>
	                    <TH align="center" >Tác vụ</TH>
	                </TR>
					<%
                 		if(nhapkhoList != null)
                 		{
                 			int m = 0;
                 			while(nhapkhoList.next())
                 			{  		
                 				if((m % 2 ) == 0) {%>
		                         	<TR class='tbdarkrow'>
		                        <%}else{ %>
		                          	<TR class='tblightrow'>
		                        <%} %>
		                    <TD align="center"><%= nhapkhoList.getString("SONHAPKHO") %></TD>
		                    <TD align="center"><%= nhapkhoList.getString("NGAYNHAPKHO") %></TD>
		                    <TD align="center"><%= nhapkhoList.getString("SOCHUNGTU") %></TD>	
		                 <%--    <TD align="center"><%= nhapkhoList.getString("CongDoanId") %></TD> --%>
		                    <TD align="left"><%= nhapkhoList.getString("ndnTen") %></TD>	
		                   <TD align="center">
						<%
						        String trangthai = "";
						        String tt = nhapkhoList.getString("trangthai");
						        if(tt.equals("0"))
						        {
						           %>
						              <span>Chưa chốt</span>
						        <%  }
						        else
						        {
			                        if(tt.equals("1"))
			                        {
                                        %>
                                        <span>Đã chốt</span>
			                        <% }
			                        else
			                        {
                                       
                                       if(tt.equals("2"))
                                        {
                                    	   %>
                                             		<span style="color: red">Đã hủy</span>
                                       <%  }
			                        }
						        }
						%>
						</TD>               
                         
		                    <TD align="center"><%= nhapkhoList.getString("ngaytao") %></TD>
		                    <TD align="left"><%= nhapkhoList.getString("nguoitao") %></TD>
		                    <TD align="center"><%= nhapkhoList.getString("ngaysua") %></TD>	
		                    <TD align="left"><%= nhapkhoList.getString("nguoisua") %></TD>				
		                    <TD align="center"> 
		                    <% if(tt.equals("2")){ %>
		                    
		                    	 <A href = "../../ErpNhapKhoLsxUpdateSvl?userId=<%=userId%>&display=<%= nhapkhoList.getString("nhId") %>&sonhaphang=<%= nhapkhoList.getString("SOCHUNGTU") %>"><IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0></A>&nbsp;
                              <% }else { if(quyen[3]!=0){%>
		                    	<A href = "../../ErpNhapKhoLsxUpdateSvl?userId=<%=userId%>&display=<%= nhapkhoList.getString("nhId") %>&sonhaphang=<%= nhapkhoList.getString("SOCHUNGTU") %>"><IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0></A>&nbsp;
		                    	<%--  <A href = "../../ErpNhapkhoLsxSvl?userId=<%=userId%>&delete=<%= nhapkhoList.getString("nhId") %>&sonhaphang=<%= nhapkhoList.getString("SOCHUNGTU") %>&noidungnhap=<%= nhapkhoList.getString("NOIDUNGNHAP") %>"><img src="../images/Delete20.png" alt="Xóa nhập kho" title="Xóa nhập kho" width="20" height="20" border=0 onclick="if(!confirm('Bạn có muốn xóa nhập kho này?')) return false;"></A> --%>  
		                    <%}} %>
		                    </TD>
		                </TR>
                     <% m++; } nhapkhoList.close(); } %>
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
</body>
</html>
<%obj.DBclose(); 
session.setAttribute("obj", null) ; 
	} %>
