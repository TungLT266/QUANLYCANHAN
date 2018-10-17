<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.erp.beans.huychungtuketoan.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>

<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>


<% IErpHuychungtuketoanList obj = (IErpHuychungtuketoanList)session.getAttribute("obj"); %>
<% ResultSet hctmhList = (ResultSet)obj.getHctMhRs(); %>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");
List<IThongTinHienThi> htList = (List<IThongTinHienThi>)obj.getHienthiList();
NumberFormat formatterNT = new DecimalFormat("#,###,###.##"); 
NumberFormat formatterVND = new DecimalFormat("#,###,###"); 
NumberFormat formatter = new DecimalFormat("#,###,###.##"); 
%>
<% obj.setNextSplittings(); 
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{
		int[] quyen = new  int[5];
		quyen = util.Getquyen("ErpHuychungtuketoanSvl","",userId);
		
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Diageo - Project</title>
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
	    document.forms["erpHctmhForm"].submit();
	 }
	 function newform()
	 {   
		document.forms["erpHctmhForm"].action.value = "Tao moi";
	    document.forms["erpHctmhForm"].submit();
	 }
	 function clearform()
	 {   
	    document.forms["erpHctmhForm"].tungay.value = "";
	    document.forms["erpHctmhForm"].denngay.value = "";
	    document.forms["erpHctmhForm"].trangthai.value = "";
	    document.forms["erpHctmhForm"].nguoitao.value = "";
	    document.forms["erpHctmhForm"].sochungtu.value = "";
	    document.forms["erpHctmhForm"].submit();
	 }
	 
	 function thongbao()
	 {
		 var tt = document.forms["erpHctmhForm"].msg.value;
	 	 if(tt.length>0)
	     	alert(document.forms["erpHctmhForm"].msg.value);
	 }
	 

	 function processing(id,chuoi)
	 {
 	    document.getElementById(id).innerHTML = "<a href='#'><img src='../images/waiting.gif' width = '20' height = '20' title='cho thuc hien..' border='0' longdesc='cho thuc hien..' style='border-style:outset'> Proc...</a>"; 		  
 	 	document.getElementById(id).href=chuoi;
 	 }
	</SCRIPT>
</head>
<body>
<form name="erpHctmhForm" method="post" action="../../ErpHuychungtuketoanSvl">
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
        	Quản lý kế toán > Chức năng > Hủy chứng từ
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
                        <TD class="plainlabel" width="15%">Từ ngày </TD>
                        <TD class="plainlabel">
                            <input type="text" class="days" 
                                   id="tungay" name="tungay" value="<%= obj.getTungay() %>" maxlength="10" onchange="submitform()" />
                        </TD>
                    </TR>
                     <TR>
                        <TD class="plainlabel" >Đến ngày </TD>
                        <TD class="plainlabel">
                            <input type="text" class="days" 
                                   id="denngay" name="denngay" value="<%= obj.getDenngay() %>" maxlength="10" onchange="submitform()" />
                        </TD>
                    </TR>
                    
                      <TR>
                        <TD class="plainlabel" >Người tạo</TD>
                        <TD class="plainlabel">
                            <input type="text" class="nguoitao" 
                                   id="nguoitao" name="nguoitao" value="<%= obj.getNguoitao() %>" maxlength="10" onchange="submitform()" />
                        </TD>
                    </TR>
                    
                     <TR>
                        <TD class="plainlabel" valign="middle">Trạng thái </TD>
                        <TD class="plainlabel" valign="middle">
                           <select name="trangthai" onChange="submitform();">
								<% if (obj.getTrangthai().equals("1")){%>
								  	<option value="1" selected>Đã chốt</option>
								  	<option value="0">Chưa chốt</option>
								  	<option value="2">Đã hủy</option>
								  	<option value=""> </option>
								  
								<%}else if(obj.getTrangthai().equals("0")) {%>
								 	<option value="0" selected>Chưa chốt</option>
								  	<option value="1" >Đã chốt</option>
								 	<option value="2" >Đã hủy</option>
								  	<option value="" > </option>
								  	
								<%}else{ if(obj.getTrangthai().equals("2")) { %>											
								 	<option value="2" selected>Đã hủy</option>										  	
								  	<option value="0" >Chưa chốt</option>
								  	<option value="1" >Đã chốt</option>
								  	<option value="" ></option> 					  		  	
								<%} else { %>
									<option value="" ></option> 									  	
								  	<option value="0" >Chưa chốt</option>
								  	<option value="1" >Đã chốt</option>
								  	<option value="2" >Đã hủy</option>	
								<% } } %>
                           </select>
                        </TD>                        
                    </TR>    
                    <TR>
                        <TD class="plainlabel" > Số chứng từ</TD>
                        <TD class="plainlabel">
                            <input type="text"  
                                   id="sochungtu" name="sochungtu" value="<%= obj.getsochungtu() %>" maxlength="10" onchange="submitform()" />
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
            	<legend><span class="legendtitle"> Hủy chứng từ </span>&nbsp;&nbsp;
            	<%if(quyen[0]!=0){ %>
                	<a class="button3" href="javascript:newform()">
                           <img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a><%} %>
                </legend>
            	<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
					<TR class="tbheader">
	                    <TH align="center">Số phiếu </TH>
	                    <TH align="center">Số chứng từ </TH>
	                     <TH align="center">Loại chứng từ </TH>
	                    <TH align="center">Trạng thái</TH>
	                    <TH align="center">Ngày tạo</TH>
	                    <TH align="center"> Người tạo </TH>
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
		                         	<TR class='tbdarkrow'>
		                        <%}else{ %>
		                          	<TR class='tblightrow'>
		                        <%} %>
		                    <TD align="center"><%= ht.getId() %></TD>
		                    <TD align="center"><%= ht.getSOCHUNGTU() %></TD>
		                    <% String loaichungtu = "";
		                       String lct = ht.getloaichungtu();
		                       if(lct.equals("1"))
		                    	   loaichungtu =  "Điều chuyển tiền";
		                     
		                    %>
		                    <TD align="center"><%= loaichungtu  %></TD>
		                    <TD align="center">
		                    	<%
		                    		String trangthai = "";
		                    		String tt = ht.getTRANGTHAI();
		                    		if(tt.equals("0"))
		                    			trangthai = "Chưa chốt";
		                    		else
		                    		{
		                    			if(tt.equals("1"))
		                    				trangthai = "Đã chốt";
		                    			else
		                    			{
			                    			trangthai = "Đã hủy";
		                    			}
		                    		}
		                    	%>
		                    	<%= trangthai %>
		                    </TD>									                                    
		                    <TD align="center"><%= ht.getNgaytao() %></TD>
		                    <TD align="center"><%= ht.getNguoitao() %></TD>
		                    <TD align="center"><%= ht.getNgaysua() %></TD>
		                    <TD align="center"><%= ht.getNguoisua() %></TD>				
		                    <TD align="center"> 
		                    <% if(tt.equals("0")){ %>
                                <%-- <A href = "../../ErpHuychungtuMhUpdateSvl?userId=<%=userId%>&update=<%= hctmhList.getString("SOPHIEU") %>"><img src="../images/Chot.png" alt="Chốt hủy chứng từ" title="Chốt hủy chứng từ" width="20" height="20" border=0 ></A>&nbsp; --%>
                                 <A href="" id="ktlist<%=m %>" rel="subcontentKT<%=m %>">&nbsp; 
											     <img alt="Định khoản kế toán" src="../images/vitriluu.png">
										 </A> &nbsp;
											 <DIV id="subcontentKT<%=m %>" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B; background-color: white; width: 750px; max-height:250px; overflow-y:scroll; padding: 4px;">
	                    						<table width="90%" align="center">
							                        <tr>
							                            <th width="100px">Nợ/Có</th>
							                            <th width="150px">Số hiệu tài khoản</th>
							                            <th width="150px">Số tiền</th>
							                            <th width="150px">Đối tượng</th>
							                            <th width="150px">Trung tâm CP</th>	
							                            <th width="150px">Trung tâm DT</th>												                       
							                        </tr>
	                        
						                            <% 		List<IDinhKhoanKeToan> ktList = ht.getLayDinhkhoanKT();							                       	 	
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
												                            <%if(kt.getSotien().trim().length() > 0 ){ %>
												                            	<input type="text" style="width: 100%" readonly="readonly" name="sotien" value="<%=  formatter.format(Double.parseDouble(kt.getSotien())) %>" style="text-align: left" />
												                           <%}else{ %>
												                           		<input type="text" style="width: 100%" readonly="readonly" name="sotien" value="<%=  kt.getSotien() %>" style="text-align: left" />
												                           <%} %>
												                           </td>
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
								        
								                         %>
			
	                    							</table>
								                     <div align="right">
								                     	<label style="color: red" ></label>
								                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								                     	<a href="javascript:dropdowncontent.hidediv('subcontentKT<%=m %>')">Hoàn tất</a>
								                     </div>
	                							</DIV>	
														
                                <%if(quyen[4]!=0){ %>
                                 <A id='chotphieu<%= ht.getId()%>'
							       href=""><img
							       src="../images/Chot.png" alt="Chốt"
							       width="20" height="20" title="Chốt"
							       border="0" onclick="if(!confirm('Bạn có muốn chốt hủy phiếu này?')) {return false ;}else{ processing('<%="chotphieu"+ht.getId()%>' , '../../ErpHuychungtuketoanSvl?userId=<%=userId%>&chot=<%= ht.getId() %>&loaict=<%=ht.getloaichungtu() %>&soct=<%=ht.getSOCHUNGTU()%>');}"  >
								</A><%} %>
								<A href = "../../ErpHuychungtuketoanUpdateSvl?userId=<%=userId%>&update=<%= ht.getId() %>"><IMG src="../images/Edit20.png" alt="Cập nhật" title="Cập nhật" border=0></A>
                                
                               <% } else {if(quyen[3]!=0){ %>
		                    	<A href = "../../ErpHuychungtuketoanUpdateSvl?userId=<%=userId%>&display=<%= ht.getId() %>"><IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0></A>
		                    	
		                    	 <A href="" id="ktlist<%=m %>" rel="subcontentKT<%=m %>">&nbsp; 
											     <img alt="Định khoản kế toán" src="../images/vitriluu.png">
										 </A> &nbsp;
											 <DIV id="subcontentKT<%=m %>" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B; background-color: white; width: 750px; max-height:250px; overflow-y:scroll; padding: 4px;">
	                    						<table width="90%" align="center">
							                        <tr>
							                            <th width="100px">Nợ/Có</th>
							                            <th width="150px">Số hiệu tài khoản</th>
							                            <th width="150px">Số tiền</th>
							                            <th width="150px">Đối tượng</th>
							                            <th width="150px">Trung tâm CP</th>	
							                            <th width="150px">Trung tâm DT</th>												                       
							                        </tr>
	                        
						                            <% 		List<IDinhKhoanKeToan> ktList = ht.getLayDinhkhoanKT();							                       	 	
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
												                            <%if(kt.getSotien().trim().length() > 0 ){ %>
												                            	<input type="text" style="width: 100%" readonly="readonly" name="sotien" value="<%=  formatter.format(Double.parseDouble(kt.getSotien())) %>" style="text-align: left" />
												                           <%}else{ %>
												                           		<input type="text" style="width: 100%" readonly="readonly" name="sotien" value="<%=  kt.getSotien() %>" style="text-align: left" />
												                           <%} %>
												                           </td>
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
								        
								                         %>
			
	                    							</table>
								                     <div align="right">
								                     	<label style="color: red" ></label>
								                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								                     	<a href="javascript:dropdowncontent.hidediv('subcontentKT<%=m %>')">Hoàn tất</a>
								                     </div>
	                							</DIV>	
														
		                    	<% }} %>
		                    </TD>
		                </TR>
                     <% m++; } %>
					<tr class="tbfooter" > 
						 <TD align="center" valign="middle" colspan="13" class="tbfooter">
						 	<%if(obj.getNxtApprSplitting() >1) {%>
								<img alt="Trang Dau" src="../images/first.gif" style="cursor: pointer;" onclick="View('erpHctmhForm', 1, 'view')"> &nbsp;
							<%}else {%>
								<img alt="Trang Dau" src="../images/first.gif" > &nbsp;
								<%} %>
							<% if(obj.getNxtApprSplitting() > 1){ %>
								<img alt="Trang Truoc" src="../images/prev.gif" style="cursor: pointer;" onclick="Prev('erpHctmhForm', 'prev')"> &nbsp;
							<%}else{ %>
								<img alt="Trang Truoc" src="../images/prev_d.gif" > &nbsp;
							<%} %>
							
							<%
								int[] listPage = obj.getNextSplittings();
								for(int i = 0; i < listPage.length; i++){
							%>
							
							<% 							
						
							if(listPage[i] == obj.getNxtApprSplitting()){ %>
							
								<a  style="color:white;"><%= listPage[i] %>/ <%=obj.getTheLastSplitting() %></a>
							<%}else{ %>
								<a href="javascript:View('erpHctmhForm', <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
							<%} %>
								<input type="hidden" name="list" value="<%= listPage[i] %>" />  &nbsp;
							<%} %>
							
							<% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
								&nbsp; <img alt="Trang Tiep" src="../images/next.gif" style="cursor: pointer;" onclick="Next('erpHctmhForm', 'next')"> &nbsp;
							<%}else{ %>
								&nbsp; <img alt="Trang Tiep" src="../images/next_d.gif" > &nbsp;
							<%} %>
							<%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
						   		<img alt="Trang Cuoi" src="../images/last.gif" > &nbsp;
					   		<%}else{ %>
					   			<img alt="Trang Cuoi" src="../images/last.gif" style="cursor: pointer;" onclick="View('erpHctmhForm', -1, 'view')"> &nbsp;
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
  
dropdowncontent.init("ktlist<%=k%>", "left-bottom", 250, "click");
  
 <%}%>
</script>

<%
	try{
		if (hctmhList != null)
			hctmhList.close();
		if (htList != null)
			htList.clear();
		
		if (obj != null)
			obj.DBclose();
		session.removeAttribute("obj");
		session.setAttribute("obj", null) ; 
		
	}catch (Exception ex)
	{
		ex.printStackTrace();
	}
}%>
</form>
<script type="text/javascript" src="../scripts/loadingv2.js"></script>
</body>
</html>