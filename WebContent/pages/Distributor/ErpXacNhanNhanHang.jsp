<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.distributor.beans.xuatkho.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.sql.*" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<% IErpXacnhannhanhangList obj = (IErpXacnhannhanhangList)session.getAttribute("obj"); %>
<% ResultSet nhapkhoRs =  obj.getDondathangRs(); %>
<% ResultSet nppRs = obj.getKhRs(); %>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  %>
<% obj.setNextSplittings();
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{
		int[] quyen = new  int[6];
		quyen = util.Getquyen("ErpXacnhannhanhangSvl","",userId);
		ResultSet rskhoid=obj.getRskhoid();
		ResultSet rsnvbanhang=obj.getRsnvbanhang();
		ResultSet nguoixacnhanRs = obj.getNguoixacnhanRs();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>OneOne - Project</title>
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
	<script type="text/javascript" src="../scripts/phanTrang.js"></script>
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
    </script>
    <SCRIPT language="javascript" type="text/javascript">
	 function submitform()
	 {   
	    document.forms["ckParkForm"].submit();
	 }
	 function newform()
	 {   
		document.forms["ckParkForm"].action.value = "Tao moi";
	    document.forms["ckParkForm"].submit();
	 }
	 function clearform()
	 {   
	    document.forms["ckParkForm"].tungay.value = "";
	    document.forms["ckParkForm"].denngay.value = "";
	    document.forms["ckParkForm"].trangthai.value = "";
	    document.forms["ckParkForm"].nppId.value = "";
	    document.forms["ckParkForm"].submit();
	 }
	 function thongbao()
	 {
		 var tt = document.forms["ckParkForm"].msg.value;
	 	 if(tt.length>0)
	     	alert(document.forms["ckParkForm"].msg.value);
	 }
	</SCRIPT>
	<script type="text/javascript" src="../scripts/cool_DHTML_tootip.js"></script>
	<style type="text/css">
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
			font-size: 13;
		}	
		#dhtmlpointer
		{
			position:absolute;
			left: -300px;
			z-index: 101;
			visibility: hidden;
		}
		th {
		cursor: pointer;
		}	
  	</style>
	<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
	$(document).ready(function()
	{
		$(".select2").select2();
	});
</script>
</head>
<body>
<form name="ckParkForm" method="post" action="../../ErpXacnhannhanhangSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="msg" value='<%= obj.getMsg() %>'>
<input type="hidden" name="nppId" value='<%= obj.getNppId() %>'>
<input type="hidden" name="currentPage" value="<%= obj.getCurrentPage() %>" >
<input type="hidden" name="phanloai" value='<%= obj.getPhanloai() %>'>
<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:55%; padding:5px; float:left" class="tbnavigation">
   
        	&nbsp;Quản lý bán hàng > Bán hàng > Biên bản nhận hàng

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
                        <TD class="plainlabel" width="130px">Từ ngày</TD>
                        <TD class="plainlabel" width="250px" >
                            <input type="text" class="days" name="tungay" value="<%= obj.getTungay() %>" maxlength="10" onchange="submitform();" />
                        </TD>
                    
                        <TD class="plainlabel" width="130px">Đến ngày</TD>
                        <TD class="plainlabel">
                            <input type="text" class="days" name="denngay" value="<%= obj.getDenngay() %>" maxlength="10" onchange="submitform();" />
                        </TD>
                    </TR>
                    <TR>
                     
                     	<TD class="plainlabel" width="100px">
                     		Mã / tên đối tượng
                     	</TD>
                        <TD class="plainlabel">
                            <%-- <select name = "khId" class="select2" style="width: 200px" onchange="submitform();" >
	                    		<option value="">All</option>
	                        	<%
	                        		if(nppRs != null)
	                        		{
	                        			try
	                        			{
	                        			while(nppRs.next())
	                        			{  
	                        			if( nppRs.getString("pk_seq").equals(obj.getKhTen())){ %>
	                        				<option value="<%= nppRs.getString("pk_seq") %>" selected="selected" ><%= nppRs.getString("ten") %></option>
	                        			<%}else { %>
	                        				<option value="<%= nppRs.getString("pk_seq") %>" ><%= nppRs.getString("ten") %></option>
	                        		 <% } } nppRs.close();} catch(Exception ex){}
	                        		}
	                        	%>
	                    	</select> --%>
	                    	<input type="text" name = "khId" value="<%= obj.getKhTen() %>" onchange="submitform();" >
                        </TD>
                     	
                        <TD class="plainlabel" valign="middle">Loại đơn hàng </TD>
                        <TD class="plainlabel" valign="middle">
                           <select name="loaidonhang" class="select2" style="width: 200px" onchange="submitform();" >
								<% if (obj.getLoaidonhang().equals("0")){%>
								  	<option value="0" selected>Đơn hàng NPP</option>
								  	<option value="1">ETC</option>
								  	<option value="2" >OTC</option>
								  	<option value="" >ALL</option>
								<%}else if(obj.getLoaidonhang().equals("1")) {%>
								 	<option value="0" >Đơn hàng NPP</option>
								  	<option value="1" selected>ETC</option>
								  	<option value="2" >OTC</option>
								  	<option value="" >ALL</option>
								<%}else if(obj.getLoaidonhang().equals("2")) {%>
								 	<option value="0" >Đơn hàng NPP</option>
								  	<option value="1">ETC</option>
								  	<option value="2" selected >OTC</option>
								  	<option value="" >ALL</option>
								<%} else  {%>
								 	<option value="0" >Đơn hàng NPP</option>
								  	<option value="1">ETC</option>
								  	<option value="2">OTC</option>
								  	<option value="" selected >ALL</option>
							<%} %>
                           </select>
                        </TD>  
                    </TR> 
                    
                     <TR>
                     
                        <TD class="plainlabel" valign="middle">Trạng thái </TD>
                        <TD class="plainlabel" valign="middle"  >
                           <select name="trangthai" class="select2" style="width: 200px" onchange="submitform();"  >
								<% if (obj.getTrangthai().equals("0")){%>
								  	<option value="0" selected>Chưa chốt</option>
								  	<option value="1">Đã chốt</option>
								  	<option value="" >ALL</option>
								<%}else if(obj.getTrangthai().equals("1")) {%>
								 	<option value="0" >Chưa chốt</option>
								  	<option value="1" selected>Đã chốt</option>
								  	<option value="" >ALL</option>
								<%} else  {%>
								 	<option value="0" >Chưa chốt</option>
								  	<option value="1">Đã chốt</option>
								  	<option value="" selected >ALL</option>
							<%} %>
                           </select>
                        </TD> 
                        
                        <TD class="plainlabel" width="100px">
                     		Người tạo
                     	</TD>
                        <TD class="plainlabel">
                           
	                    	<input type="text" name = "nguoitao"  value="<%=obj.getNguoitao() %>" onchange="submitform();" >
                        </TD> 
                        
                                              
                    </TR> 
                    
                         <TR>
                    
                    	<TD  class="plainlabel">Kho hàng hóa</TD>
							<TD  class="plainlabel" >
							<select name="khohhid" class="select2"  style="width: 200px;" onchange="submitform();">
							
								<option value="">ALL</option>
								<%if(rskhoid!=null){ while (rskhoid.next()){ %>
								<option value="<%=rskhoid.getString("pk_seq")%>" <%if(rskhoid.getString("pk_seq").equals(obj.getKhohh())){%> selected="selected" <%}%>><%=rskhoid.getString("ten") %>  </option>
								<%} }%>
								
							</select>
							</TD>
							
							
							 <TD class="plainlabel" width="100px">
                     		Người giao
                     	</TD>
                     	
                        <TD class="plainlabel">
                           
	            			<input type="text" name = "nguoigiao"  value="<%=obj.getNguoigiao() %>" onchange="submitform();" >
                        </TD> 
							
                    
                    </TR>   
           
           <TR>
           	<TD  class="plainlabel">Nhân viên bán hàng</TD>
				<TD  class="plainlabel" >
			
				<select name="nvbanhang" class="select2" style="width: 200px" onchange="submitform();" >
					<option value="" >ALL</option>
						<%if(rsnvbanhang!=null)
							{ while (rsnvbanhang.next()){%>
							<option value="<%=rsnvbanhang.getString("pk_seq")%>" <%if(rsnvbanhang.getString("pk_Seq").equals(obj.getNvbanhang())){ %> selected="selected" <%} %>><%=rsnvbanhang.getString("ten") %></option>
							<%}} %>
				</select>
				
				</TD>
				<TD class="plainlabel" > Người xác nhận</TD>
                    <TD class="plainlabel" width="250px" >
                    	<select name = "nguoixacnhan" class="select2" style="width: 200px" onchange="submitform();" >
                    		<option value=""> Tất cả </option>
                        	<%
                        		if(nguoixacnhanRs != null)
                        		{
                        			try
                        			{
                        			while(nguoixacnhanRs.next())
                        			{  
                        			if( nguoixacnhanRs.getString("pk_seq").equals(obj.getNguoixacnhan())){ %>
                        				<option value="<%= nguoixacnhanRs.getString("pk_seq") %>" selected="selected" ><%= nguoixacnhanRs.getString("ten") %></option>
                        			<%}else { %>
                        				<option value="<%= nguoixacnhanRs.getString("pk_seq") %>" ><%= nguoixacnhanRs.getString("ten") %></option>
                        		 <% } } nguoixacnhanRs.close();} catch(SQLException ex){}
                        		}
                        	%>
                    	</select>
                    </TD>  				
           </TR>
           <tr>
                 <TD class="plainlabel" width="100px"> Ghi chú</TD>
                 <TD class="plainlabel" >
                      <input type="text" name = "ghichu"  value="<%=obj.getGhichu() %>" onchange="submitform();" >
                 </TD> 
                 <TD class="plainlabel" width="100px"> Mã chứng từ</TD>
                 <TD class="plainlabel" >
                      <input type="text" name = "machungtu"  value="<%=obj.getMachungtu() %>" onchange="submitform();" >
                 </TD> 
           </tr>
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
            	<legend><span class="legendtitle"> Biên bản nhận hàng </span>&nbsp;&nbsp;
            	<%if(quyen[Utility.THEM]!=0){ %>
                	<a class="button3" href="javascript:newform()">
                           <img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a><%} %>
                </legend>
            	<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
					<TR class="tbheader">
	                    <TH align="center" style="width: 12%" >Mã chứng từ</TH>
	                    <TH align="center" style="width: 8%" >Ngày xuất</TH>
	                    <TH align="center" style="width: 20%" >
	                    	<% if(obj.getPhanloai().equals("0")) { %>
	                     		Nhà phân phối
	                     	<% } else { %>
	                     		Khách hàng
	                     	<% } %>
	                    </TH>
	                    <!-- <TH align="center" style="width: 10%" >Kho xuất</TH> -->
	                    <TH align="center" style="width: 20%" >Số hóa đơn</TH>
	                    <TH align="center" style="width: 8%" >Trạng thái</TH>
	                    <!-- <TH align="center" style="width: 5%" >Ngày tạo</TH>
	                    <TH align="center" style="width: 10%" >Người tạo</TH> -->
	                    <TH align="center" style="width: 8%" >Ngày sửa</TH>
	                    <TH align="center" style="width: 12%" >Người sửa</TH>
	                    <TH align="center" style="width: 10%" >Tác vụ</TH>
	                </TR>
					<%
                 		if(nhapkhoRs != null)
                 		{
                 			int m = 0;
                 			while(nhapkhoRs.next())
                 			{  	
                 				String msg="Mã số: " + nhapkhoRs.getString("PK_SEQ") + "</br> Ngày xuất: " + nhapkhoRs.getString("ngayyeucau") +
                 						"</br>Khách hàng: " + nhapkhoRs.getString("nppTEN") + "</br>Kho xuất: " + nhapkhoRs.getString("khoTEN") +
                  						"</br> Lệnh xuất hàng: " + nhapkhoRs.getString("ddhIds");
                 				if((m % 2 ) == 0) {%>
		                         	<TR class='tbdarkrow'>
		                        <%}else{ %>
		                          	<TR class='tblightrow'>
		                        <%} %>
		                    <TD align="center" onMouseover="ddrivetip('<%=msg %>', 300)" onMouseout="hideddrivetip()"><%= nhapkhoRs.getString("machungtu") %></TD>
		                    <TD align="center"><%= nhapkhoRs.getString("NGAYYEUCAU") %></TD>
		                    <TD ><%= nhapkhoRs.getString("nppTEN") %></TD>  
		                    <%-- <TD ><%= nhapkhoRs.getString("khoTEN") %></TD>   --%>
		                    <TD style="width: 100px;" ><%= nhapkhoRs.getString("ddhIds") %></TD>  
		                    	 <TD align="center">
		                    	<%
		                    		String trangthai = "";
		                    		String tt = nhapkhoRs.getString("trangthai");
		                    		if(tt.equals("0")) //NPP TAO
		                    			trangthai = "Chưa chốt";
		                    		else
		                    		{
		                    			trangthai = "Đã chốt";
		                    		}
		                    		
		                    	%>
		                    	<%= trangthai %>
		                    </TD>   									                                    
					     	<%-- <TD align="center"><%= nhapkhoRs.getString("NGAYTAO") %></TD>	
		                    <TD align="center"><%= nhapkhoRs.getString("NGUOITAO") %></TD> --%>
         					<TD align="center"><%= nhapkhoRs.getString("NGAYSUA") %></TD>
							<TD align="center"><%= nhapkhoRs.getString("NGUOISUA") %></TD>
									
		                    <TD align="center"> 
		                    <% if(tt.equals("0")){ %>
		                    
		                    <%if(quyen[Utility.SUA]!=0){ %>
                                <A href = "../../ErpXacnhannhanhangUpdateSvl?userId=<%=userId%>&update=<%=nhapkhoRs.getString("PK_SEQ") %>&phanloai=<%= obj.getPhanloai() %>"><IMG src="../images/Edit20.png" alt="Cập nhật" title="Cập nhật" border=0></A>&nbsp;
 							<% } %>     
 							                           
                             <%if(quyen[Utility.CHOT]!=0){ %>
                                <A href = "../../ErpXacnhannhanhangSvl?userId=<%=userId%>&chot=<%= nhapkhoRs.getString("PK_SEQ") %>&nppId=<%= obj.getNppId() %>&loai=<%= obj.getPhanloai() %>"><img src="../images/Chot.png" alt="Chốt" title="Chốt" width="20" height="20" border=0 onclick="if(!confirm('Bạn có muốn chốt xác nhận nhận hàng này?')) return false;"></A>&nbsp;
							<% } %>                                     
                                
                                <%if(quyen[Utility.XOA]!=0){ %>
                              	<A href = "../../ErpXacnhannhanhangSvl?userId=<%=userId%>&delete=<%= nhapkhoRs.getString("PK_SEQ") %>&nppId=<%= obj.getNppId() %>&loai=<%= obj.getPhanloai() %>"><img src="../images/Delete20.png" alt="Xóa" title="Xóa" width="20" height="20" border=0 onclick="if(!confirm('Bạn có muốn xóa xác nhận nhận hàng này?')) return false;"></A>
                              	<% } %>
                              	                   									
		                    <%} else{ %>
		                    
		                    		<%if(quyen[Utility.XEM]!=0){ %>
		                    		<A href = "../../ErpXacnhannhanhangUpdateSvl?userId=<%=userId%>&display=<%= nhapkhoRs.getString("PK_SEQ") %>&phanloai=<%= obj.getPhanloai() %>"><IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0></A>
		                    		<% } %>
		                    		
		                    		<%if(quyen[Utility.XEM]!=0){ %>
		                    		<A href = "../../ErpXacnhannhanhangSvl?userId=<%=userId%>&id=<%= nhapkhoRs.getString("PK_SEQ") %>&type=inPDF"><IMG src="../images/Printer30.png" alt="In" title="In" border=0></A>
		                    		<% } %>
		                    		
		                    <% } %>
		                    </TD>
		                </TR>
                     <% m++; } nhapkhoRs.close(); } %>
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
</html><%
	if(obj!=null)
	{
		obj.DBclose();
		session.setAttribute("obj",null);
	}
	if(rskhoid!=null)
		rskhoid.close();
	}%>