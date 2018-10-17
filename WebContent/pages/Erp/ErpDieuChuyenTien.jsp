<%@page import="geso.traphaco.center.util.Utility"%>
<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.erp.beans.dieuchuyentien.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@page import="geso.traphaco.center.util.IThongTinHienThi"%>
<%@page import="geso.traphaco.center.util.IDinhKhoanKeToan"%>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>

<%@page import="geso.traphaco.center.util.IThongTinHienThi"%>
<%@page import="geso.traphaco.center.util.IDinhKhoanKeToan"%>

<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>


<% IErpDieuchuyentienList obj = (IErpDieuchuyentienList)session.getAttribute("obj"); %>

<% ResultSet ttRs = (ResultSet)obj.getTienteRs(); %>

<% ResultSet NHChuyenRs = (ResultSet)obj.getNHChuyenRs(); %>

<% ResultSet NHNhanRs = (ResultSet)obj.getNHNhanRs(); %>

<% 	ResultSet dieutienRs = (ResultSet)obj.getDieutienRs(); 
	List<IThongTinHienThi> htList = (List<IThongTinHienThi>)obj.getHienthiList();
%>

<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  %>
<% NumberFormat formatter1 = new DecimalFormat("#,###,###.###"); %> 
<% NumberFormat formatter2 = new DecimalFormat("#,###,###"); %>
<%  
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/SalesUp/index.jsp");
	}else{	
		 obj.setNextSplittings();  
		 int[] quyen = new  int[5];
		 quyen = util.Getquyen("ErpDieuchuyentienSvl","",userId);
		 NumberFormat formatter = new DecimalFormat("#,###,###.##"); 

 %>

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
   <script type="text/javascript" src="../scripts/ajax.js"></script>
   <script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script> 
   
   
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
	    document.forms["erpDctForm"].submit();
	 }
	 function newform()
	 {   
		document.forms["erpDctForm"].action.value = "Tao moi";
	    document.forms["erpDctForm"].submit();
	 }

	 function clearform()
	 {   
	    document.forms["erpDctForm"].sochungtu.value = "";
	    document.forms["erpDctForm"].ngaychungtu.value = "";
	    document.forms["erpDctForm"].ttId.value = "";
	    document.forms["erpDctForm"].nhchuyenId.value = "";
	    document.forms["erpDctForm"].nhnhanId.value = "";
	    document.forms["erpDctForm"].trangthai.value = "";
	    document.forms["erpDctForm"].submit();
	 }
	 
	 function thongbao()
	 {
		 var tt = document.forms["erpDctForm"].msg.value;
	 	 if(tt.length>0)
	     	alert(document.forms["erpDctForm"].msg.value);
	 }
	 

	 function processing(id,chuoi)
	 {
 	    document.getElementById(id).innerHTML = "<a href='#'><img src='../images/waiting.gif' width = '20' height = '20' title='cho thuc hien..' border='0' longdesc='cho thuc hien..' style='border-style:outset'> Đang xử lý</a>"; 		  
 	 	document.getElementById(id).href=chuoi;
 	 }
	</SCRIPT>
</head>
<body>
<form name="erpDctForm" method="post" action="../../ErpDieuchuyentienSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >

<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>" >
<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>" >

<input type="hidden" name="msg" value='<%= obj.getMsg() %>'>
<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">
        	Quản lý kế toán > Chức năng > Điều chuyển tiền
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
                        <TD class="plainlabel" width="15%">Ngày chứng từ</TD>
                        <TD class="plainlabel">
                            <input type="text" class="days" 
                                   id="ngaychungtu" name="ngaychungtu" value="<%= obj.getNgaychungtu() %>" maxlength="10" onchange="submitform()" />
                        </TD>
                    </TR>
                    <TR>
                        <TD class="plainlabel" width="15%">Số chứng từ</TD>
                        <TD class="plainlabel">
                            <input type="text" 
                                   id="sochungtu" name="sochungtu" value="<%= obj.getSochungtu() %>"  onchange="submitform()" />
                        </TD>
                    </TR>

                    <TR>
                        <TD class="plainlabel" valign="middle" >Loại tiền </TD>
                        <TD class="plainlabel" valign="middle">
                            <select name="ttId" onchange="submitform()">
                            	<option value=""></option>
                            	<%
	                        		if(ttRs != null)
	                        		{
	                        			while(ttRs.next())
	                        			{  
	                        			if( ttRs.getString("TTID").equals(obj.getTtId())){ %>
	                        				<option value="<%= ttRs.getString("TTID") %>" selected="selected" ><%= ttRs.getString("TIENTE") %></option>
	                        			<%}else { %>
	                        				<option value="<%= ttRs.getString("TTID") %>" ><%= ttRs.getString("TIENTE") %></option>
	                        		 <% } } ttRs.close();
	                        		}
	                        	%>
                            </select>
                        </TD>                        
                    </TR> 

                    <TR>
                        <TD class="plainlabel" valign="middle" >Ngân hàng chuyển </TD>
                        <TD class="plainlabel" valign="middle">
                            <select name="nhchuyenId" onchange="submitform()">
                            	<option value=""></option>
                            	<%
	                        		if(NHChuyenRs != null)
	                        		{
	                        			while(NHChuyenRs.next())
	                        			{  
	                        			if( NHChuyenRs.getString("NHCTYID").equals(obj.getNhchuyenId())){ %>
	                        				<option value="<%= NHChuyenRs.getString("NHCTYID") %>" selected="selected" ><%= NHChuyenRs.getString("NGANHANG") %></option>
	                        			<%}else { %>
	                        				<option value="<%= NHChuyenRs.getString("NHCTYID") %>" ><%= NHChuyenRs.getString("NGANHANG") %></option>
	                        		 <% } } NHChuyenRs.close();
	                        		}
	                        	%>
                            </select>
                        </TD>                        
                    </TR> 
                    <TR>
                        <TD class="plainlabel" valign="middle" >Ngân hàng nhận </TD>
                        <TD class="plainlabel" valign="middle">
                            <select name="nhnhanId" onchange="submitform()">
                            	<option value=""></option>
                            	<%
	                        		if(NHNhanRs != null)
	                        		{
	                        			while(NHNhanRs.next())
	                        			{  
	                        			if( NHNhanRs.getString("NHCTYID").equals(obj.getNhnhanId())){ %>
	                        				<option value="<%= NHNhanRs.getString("NHCTYID") %>" selected="selected" ><%= NHNhanRs.getString("NGANHANG") %></option>
	                        			<%}else { %>
	                        				<option value="<%= NHNhanRs.getString("NHCTYID") %>" ><%= NHNhanRs.getString("NGANHANG") %></option>
	                        		 <% } } NHNhanRs.close();
	                        		}
	                        	%>
                            </select>
                        </TD>                        
                    </TR> 
                    
                    <TR>
                        <TD class="plainlabel" valign="middle" >Trạng thái </TD>
                        <TD class="plainlabel" valign="middle">
                            <SELECT name="trangthai" onchange="submitform()">
                            	<% if(obj.getTrangthai().equals("0")){ %>
											<option value="0" selected>Chưa chốt</option>
											<option value="1">Đã chốt</option>											
											<option value=""> </option>
										<%} else  if( obj.getTrangthai().equals("1") ) { %>										
											<option value="0" >Chưa chốt</option>
											<option value="1" selected>Đã chốt</option>																				
											<option value=""> </option>
										
											<% }else { %>
											<option value="0" >Chưa chốt</option>
											<option value="1">Đã chốt</option>																			
											<option value="" selected> </option>
										 <% }  %>
												
                            </SELECT>
                        </TD>                        
                    </TR> 

                    
                    <tr>
                        <td colspan="2" class="plainlabel">
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
            	<legend><span class="legendtitle"> Điều chuyển tiền&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
            	<%//if(quyen[0]!=0){ %>
                	<a class="button3" href="javascript:newform()">
                           <img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>
                           <%//} %>
                </legend>
            	<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
					<TR class="tbheader">
	                    <TH align="center">Số chứng từ</TH>
	                    <TH align="center">Ngày chứng từ</TH>
	                    <TH align="center">Loại tiền</TH>
	                    <TH align="center">NH chuyển</TH>
	                    <TH align="center">NH nhận</TH>
	                    <TH align="center">Số tiền</TH>
	                    <TH align="center">Trạng thái</TH>
	                    <TH align="center"> Ngày sửa </TH>
	                    <TH align="center"> Người sửa </TH>
	                    <TH align="center" >Tác vụ</TH>
	                </TR>
					<%
						int m = 0;								
						for(int i =0; i < htList.size(); i ++)
						{	
									IThongTinHienThi ht = htList.get(i);
									if((m % 2 ) == 0) {%>
		                         		<TR class="tbdarkrow">
				                    <%}else{ %>
				                          	<TR class="tblightrow">
				                    <%} %>
			                        
									<TD align="center"><%= ht.getSochungtu() %></TD>									
									<TD align="center"><%= ht.getNgaychungtu() %></TD>
									<TD align="center"><%= ht.getTiente() %> </TD>
									<TD align="center"><%= ht.getNHCHUYEN() %></TD>
									<TD align="center"><%= ht.getNHNHAN() %></TD>
									<TD align="right"><%= ht.getTTID().equals("100000")? formatter1.format(Double.parseDouble(ht.getSOTIENVND())):formatter2.format(Double.parseDouble(ht.getSOTIENNT())) %></TD>
									<TD align="center">
										<%
											String trangthai = "";
											String tt = ht.getTrangthai();
											if(tt.equals("0"))
												trangthai = "Chưa chốt";
											else{	
														trangthai = "Đã chốt";
												}
										%>
										<%= trangthai %>
									</TD>
									<TD align="center"><%= ht.getNgaysua() %></TD>
									<TD align="left"><%= ht.getNguoisua() %></TD>
									<TD align="center"> 
										<TABLE border = 0 cellpadding="0" cellspacing="0">
											<TR>
												<% if(tt.equals("0")){ %>
													
												<TD>
													<%if(quyen[2]!=0){ %>
													<A href = "../../ErpDieuchuyentienUpdateSvl?userid=<%=userId%>&update=<%= ht.getId()%>"><img src="../images/Edit20.png" alt="Chỉnh sửa" width="20" height="20" longdesc="Chỉnh sửa" border = 0></A>
													<%} %>
												</TD>
												<TD>&nbsp;&nbsp;</TD>
												<TD>
													<A href = "../../ErpDieuchuyentienSvl?userid=<%=userId%>&chot=<%=ht.getId()%>"><img src="../images/Chot.png" alt="Chốt" width="20" height="20" longdesc="Chốt" border = 0 onclick="if(!confirm('Bạn có muốn chốt Điều chuyển tiền này ?')) return false;"></A>												
												</TD>
												<TD>&nbsp;&nbsp;</TD>
												<TD>
													<%if(quyen[1]!=0){ %>
													<A href = ../../ErpDieuchuyentienSvl?userid=<%=userId%>&delete=<%=ht.getId()%>><img src="../images/Delete20.png" alt="Xóa" width="20" height="20" longdesc="Xóa" border=0 onclick="if(!confirm('Bạn có muốn xóa Điều chuyển tiền này ?')) return false;"></A>
												
													<%}%>													
												</TD>
												
												<%}else{ %>
												<TD>
													<A href = "../../ErpDieuchuyentienUpdateSvl?userid=<%=userId%>&display=<%=ht.getId()%>"><img src="../images/Display20.png" alt="Hiển thị" width="20" height="20" longdesc="Hiển thị" border = 0></A>
												</TD>												
												<%} %>
												
												<TD>
													<A href="" id="ktlist<%=m %>" rel="subcontentKT<%=m%>">&nbsp; <img alt="Định khoản kế toán" src="../images/vitriluu.png"> </A> &nbsp;
													
												<DIV id="subcontentKT<%=m%>" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B; background-color: white; width: 750px; max-height:250px; overflow-y:scroll; padding: 4px;">
	                    						<table width="90%" align="center">
							                        <tr>
								                        <th width="200px">Nợ/Có</th>
							                            <th width="150px">Số hiệu tài khoản</th>
							                            <th width="200px">Số tiền</th>
							                            <th width="150px">Đối tượng</th>
							                            <th width="150px">Trung tâm CP</th>	
							                            <th width="150px">Trung tâm DT</th>									                       
							                        </tr>
	                        
						                            <% 		
						                                 List<IDinhKhoanKeToan> ktList = ht.getLayDinhkhoanKT();
						                                 if(ktList.size() > 0)
											                {
								                        		for(int sd = 0; sd < ktList.size(); sd++)
								                        		{
								                        			IDinhKhoanKeToan kt = ktList.get(sd);
									                        		%>
									                        			<tr>
									                        				<td>
									                        					<input type="text" style="width: 100%" readonly="readonly" name="no_co" value="<%= kt.getNO_CO() %>" />
									                        				</td>
												                            <td>											                            	
												                            	<input type="text" style="width: 100%" readonly="readonly" name="sohieutk" value="<%= kt.getSoHieu() %>" />
												                            </td>
												                            <td>
												                            	<%if(kt.getSotien().trim().length() > 0){ %>
												                            		<input type="text" style="width: 100%" readonly="readonly" name="sotien" value="<%= formatter.format(Double.parseDouble(kt.getSotien())) %>" style="text-align: left" />
												                            	<%} else {%>
												                            		<input type="text" style="width: 100%" readonly="readonly" name="sotien" value="<%= kt.getSotien() %>" style="text-align: left" />
												                            	<%} %>												                            	
												                            <td>
												                            	<input type="text"  style="width: 100%" name="doituong" value="<%= kt.getDoiTuong() %>" />
												                            </td>
												                            <td>
												                            	<input type="text"  style="width: 100%" name="trungtamcp" value="<%= kt.getTrungtamCP()  %>" />
												                            </td>
												                            <td>
												                            	<input type="text"  style="width: 100%" name="trungtamdt" value="<%= kt.getTrungtamDT()  %>" />
												                            </td>
												                        </tr>
									                        <%  }
								                        		ktList.clear();
								                        		}
								        
								                         %>
			
	                    							</table>
								                     <div align="right">
								                     	<label style="color: red" ></label>
								                     		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								                     		<a href="javascript:dropdowncontent.hidediv('subcontentKT<%=m%>')">Hoàn tất</a>
								                     </div>
	                							</DIV>
												</TD>
											</TR>
										</TABLE>				
									
				                    </TD>
				                    </TR>
								<% 
								m++;
				             }
						%>
					
					<tr class="tbfooter" > 
						 <TD align="center" valign="middle" colspan="13" class="tbfooter">
						 	<%if(obj.getNxtApprSplitting() >1) {%>
								<img alt="Trang Dau" src="../images/first.gif" style="cursor: pointer;" onclick="View('erpDctForm', 1, 'view')"> &nbsp;
							<%}else {%>
								<img alt="Trang Dau" src="../images/first.gif" > &nbsp;
								<%} %>
							<% if(obj.getNxtApprSplitting() > 1){ %>
								<img alt="Trang Truoc" src="../images/prev.gif" style="cursor: pointer;" onclick="Prev('erpDctForm', 'prev')"> &nbsp;
							<%}else{ %>
								<img alt="Trang Truoc" src="../images/prev_d.gif" > &nbsp;
							<%} %>
							
							<%
								int[] listPage = obj.getNextSplittings();
							System.out.println("Chiều dài: "+listPage.length);
								for(int i = 0; i < listPage.length; i++){
							%>
							
							<% 							
						
							if(listPage[i] == obj.getNxtApprSplitting()){ %>
							
								<a  style="color:white;"><%= listPage[i] %>/ <%=obj.getTheLastSplitting() %></a>
							<%}else{ %>
								<a href="javascript:View('erpDctForm', <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
							<%} %>
								<input type="hidden" name="list" value="<%= listPage[i] %>" />  &nbsp;
							<%} %>
							
							<% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
								&nbsp; <img alt="Trang Tiep" src="../images/next.gif" style="cursor: pointer;" onclick="Next('erpDctForm', 'next')"> &nbsp;
							<%}else{ %>
								&nbsp; <img alt="Trang Tiep" src="../images/next_d.gif" > &nbsp;
							<%} %>
							<%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
						   		<img alt="Trang Cuoi" src="../images/last.gif" > &nbsp;
					   		<%}else{ %>
					   			<img alt="Trang Cuoi" src="../images/last.gif" style="cursor: pointer;" onclick="View('erpDctForm', -1, 'view')"> &nbsp;
					   		<%} %>
						</TD>
					 </tr> 
				</TABLE>
            </fieldset>
        </div>
    </div>  
</div>

<script type="text/javascript"> 
	  <%for(int k=0; k < m; k++) {%>	   
		<%-- dropdowncontent.init("ktlist<%=k%>", "left-bottom", 250, "click"); --%>
		dropdowncontent.init('ktlist<%= k %>', "left-top", 300, "click");
	   
	  <%}%>
</script>
<%


try{
	if(ttRs != null) ttRs.close();

	if(NHChuyenRs != null) NHChuyenRs.close();

	if(NHNhanRs != null) NHNhanRs.close();

	if(dieutienRs != null) dieutienRs.close();
	if(htList!=null){
		htList.clear();
	}
	obj.DBclose();  
	session.setAttribute("obj",null);
	session.setAttribute("obj", null) ;   ; 
}catch(Exception er){
	
}
}%>
</form>
</body>
</html>