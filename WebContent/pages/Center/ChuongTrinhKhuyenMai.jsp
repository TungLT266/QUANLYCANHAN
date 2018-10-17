<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.NumberFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.center.beans.ctkhuyenmai.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<% ICtkhuyenmaiList obj = (ICtkhuyenmaiList)session.getAttribute("obj"); %>
<% ResultSet ctkmList = (ResultSet)obj.getCtkmList(); %>

<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen"); 
String sum = (String) session.getAttribute("sum");
Utility util = (Utility) session.getAttribute("util");
if(!util.check(userId, userTen, sum)){
	response.sendRedirect("/Traphaco/index.jsp");
}else{
	ResultSet vungRs=obj.getVungRs();
	ResultSet khuvucRs=obj.getKhuvucRs();
	ResultSet nppRs=obj.getNppRs();
	obj.setNextSplittings(); 
	int[] quyen = new  int[6];
	quyen = util.Getquyen("CtkhuyenmaiSvl","",userId);


%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>OneOne - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
    <LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
    <LINK rel="stylesheet" href="../css/main.css" type="text/css">
    <LINK rel="stylesheet" href="../css/datepicker.css" type="text/css">
    <link type="text/css" rel="stylesheet" href="../css/mybutton.css">
     <script type="text/javascript" src="../scripts/cool_DHTML_tootip.js"></script>
     <script type="text/javascript" src="../scripts/phanTrang.js"></script>
     
    
     
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
  

  
    <script language="javascript" src="../scripts/datepicker.js"></script>
   	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css"/>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery.min.js"></script>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery-ui.min.js"></script>
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
	 <script type="text/javascript" src="../scripts/jquery-latest.js"></script> 
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
		    document.forms["ctkmForm"].submit();
		 }
		 function seach()
		 {   
		    document.forms["ctkmForm"].submit();
		 }
		 function newform()
		 {   
			document.forms["ctkmForm"].action.value = "Tao moi";
		    document.forms["ctkmForm"].submit();
		 }
		 function clearform()
		 {   
		    document.forms["ctkmForm"].diengiai.value = "";		   
		    document.forms["ctkmForm"].tungay.value = "";
		    document.forms["ctkmForm"].denngay.value = "";
		    document.forms["ctkmForm"].trangthai.value="";		    
		    document.forms["ctkmForm"].vungId.value="";
		    document.forms["ctkmForm"].nppId.value="";
		    document.forms["ctkmForm"].khuvucId.value="";		    
		    document.forms["ctkmForm"].phanbo.value="";
		    submitform();
		 }
		 function thongbao()
		 {
			 var tt = document.forms["ctkmForm"].msg.value;
		 	 if(tt.length>0)
		     	alert(document.forms["ctkmForm"].msg.value);
	 	 }		 
		 function xuatExcel()
		 {
		 	document.forms['ctkmForm'].action.value= 'toExcel';
		 	document.forms['ctkmForm'].submit();
		 	document.forms['ctkmForm'].action.value= '';
		 }
		 
	</SCRIPT>
	
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
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
$(document).ready(function()
{
	$("#nppId").select2();
	$("#vungId").select2();
	$("#khuvucId").select2();
});

</script>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="ctkmForm" method="post" action="../../CtkhuyenmaiSvl">
<input type="hidden" name="userId" value="<%= userId %>" >

<input type="hidden" name="msg" value='<%=obj.getMessage() %>'>
<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<input type="hidden" name="action" value="1" >
<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>" >
<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>" >

<div id="main" style="width:99%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:55%; padding:5px; float:left" class="tbnavigation">
        	Quản lý khuyễn mãi > Khai báo > Chương trình khuyến mãi
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn <%= userTen %> &nbsp;
        </div>
    </div>
   	
    <div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
    <fieldset>
    	<legend class="legendtitle">Tiếu chí tìm kiếm</legend>
        <div style="width:100%; float:none" align="left">
                <TABLE width="100%" cellpadding="6" cellspacing="0">								
                    <TR>
                        <TD width="6%" class="plainlabel">Mã CTKM </TD>
                        <TD class="plainlabel" width="20%"><input type="text" value="<%= obj.getDiengiai() %>" name="diengiai" size="40"></TD>
                        <TD class="plainlabel" width="9%">Vùng </TD>
							<TD class="plainlabel">
								<select name="vungId" onchange="seach();" id="vungId" style="width:200px;">
									<option value="" selected>All</option>
									<%if (vungRs != null)
											while (vungRs.next()) {
												if (vungRs.getString("vungId").equals(obj.getVungId()   )) {%>
													<option value="<%=vungRs.getString("vungId")%>" selected><%=vungRs.getString("vungTen")%></option>
											<%} else {%>
												<option value="<%=vungRs.getString("vungId")%>"><%=vungRs.getString("vungTen")%></option>
											<%}}%>
								</select>
							</TD>
                    </TR>               
                    <TR>
                        <TD class="plainlabel" >Từ ngày </TD>
                        <TD class="plainlabel">
                            <input type="text" size="10" class="days" 
                                        id="tungay" name="tungay" value="<%=obj.getTungay()%>" maxlength="10" />
                        </TD>
                        <TD class="plainlabel">Khu vực </TD>
							<TD class="plainlabel">
								<select name="khuvucId" onchange="seach();" id="khuvucId" style="width:200px;">
									<option value="" selected>All</option>
									<%if (khuvucRs != null)
											while (khuvucRs.next()) {
												if (khuvucRs.getString("khuvucId").equals(obj.getKhuvucId()  )) {%>
													<option value="<%=khuvucRs.getString("khuvucId")%>" selected><%=khuvucRs.getString("khuvucTen")%></option>
											<%} else {%>
												<option value="<%=khuvucRs.getString("khuvucId")%>"><%=khuvucRs.getString("khuvucTen")%></option>
											<%}}%>
								</select>
							</TD>
                    </TR>
                    <TR>
                        <TD class="plainlabel" >Đến ngày </TD>
                        <TD class="plainlabel">
                            <input type="text" size="10" class="days"
                                        id="denngay" name="denngay" value="<%=obj.getDenngay() %>" maxlength="10" />
                        </TD>
                        <TD class="plainlabel">Chi nhánh / Đối tác </TD>
							<TD class="plainlabel">
								<select name="nppId" onchange="seach();" id="nppId" style="width:200px;">
									<option value="" selected>All</option>
									<%if (nppRs != null)
											while (nppRs.next()) {
												if (nppRs.getString("nppId").equals(obj.getNppId()   )) {%>
													<option value="<%=nppRs.getString("nppId")%>" selected><%=nppRs.getString("nppTen")%></option>
											<%} else {%>
												<option value="<%=nppRs.getString("nppId")%>"><%=nppRs.getString("nppTen")%></option>
											<%}}%>
								</select>
							</TD>
                    </TR>
                    
                    <TR>
                        <TD class="plainlabel" >Trạng thái</TD>
						<TD colspan="1" class="plainlabel"> 
				 			<SELECT name="trangthai" id="trangthai" class="legendtitle" onChange = submitform();>
					 			 <option value=""></option>
								  <% 											 
											if(obj.getTrangthai().equals("1")){ %>
												<option value='1' selected>Còn hiệu lực</option>
										  <%}else{ %>
												<option value='1'>Còn hiệu lực</option>
										  <%} %>
										  
								  <%		if(obj.getTrangthai().equals("2")){ %>
												<option value='2' selected>Hết hiệu lực</option>
										  <%}else{ %>
												<option value='2'>Hết hiệu lực</option>
										  <%} %>	 
										  	 
                            </SELECT>
                        </TD>
                        <TD class="plainlabel" >Phân bổ</TD>
						<TD colspan="1" class="plainlabel"> 
				 			<SELECT name="phanbo" id="phanbo" class="legendtitle" onChange = submitform();>
					 			 <option value=""></option>
								  <% 											 
										if(obj.getPhanbo().equals("1")){ %>
											<option value='1' selected>Đã phân bổ</option>
									  <%}else{ %>
											<option value='1'>Đã phân bổ</option>
									  <%} %>
								  <%		if(obj.getPhanbo().equals("0")){ %>
												<option value='0' selected>Chưa phân bổ</option>
										  <%}else{ %>
												<option value='0'>Chưa phân bổ</option>
										  <%} %>	 
										  	 
                            </SELECT>
                        </TD>
                    </TR>
                    
						
						
                    <tr style="background-color:#C5E8CD">
                        <td colspan="4">
                          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  <a class="button" href="javascript:submitform()">
                                <img style="top: -4px;" src="../images/Search30.png" alt="">Tìm kiếm </a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <a class="button2" href="javascript:clearform()">
                                <img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>
                                &nbsp;&nbsp;&nbsp;&nbsp;
							<a class="button2" href="javascript:xuatExcel()"> <img style="top: -4px;" src="../images/button.png" alt="">Xuất Excel </a>&nbsp;&nbsp;&nbsp;&nbsp;
                        </td>
                    </tr>      				
                </TABLE>       
         </div>
      </fieldset>  
    </div>
    <div align="left" style="width:100%; float:none; clear:left">
    <fieldset>
    	<legend><span class="legendtitle"> Chương trình khuyến mại </span>&nbsp;&nbsp;&nbsp;
    	<%if(quyen[Utility.THEM]!=0) {%>
        	<a class="button3" href="javascript:newform()">
                    <img style="top: -4px;" src="../images/New.png" alt="">Tạo mới</a>
                    <%} %>
        </legend>
        <div style="width:100%; float:none" align="left">
            <TABLE  width="100%" id="table" class="tabledetail sortable" border="0" cellspacing="1" cellpadding="1">
				<thead>
				<TR class="tbheader">				
                    <TH class="nosort" width="9%" align="center">Mã CTKM</TH>		
                    <TH width="25%" align="center">Điễn giải</TH>
                    <TH width="7%" align="center">Ngày bắt đầu</TH>
                    <TH width="7%" align="center">Ngày kết thúc</TH>
                    <TH width="7%" align="center">Ngân sách</TH>
                    <TH width="10%" align="center">Loại CT</TH>
                    <TH width="7%" align="center">Ngày tạo</TH>
                    <TH width="7%" align="center">Người tạo</TH>
                    <TH width="7%" align="center">Ngày sửa</TH>
                    <TH width="7%" align="center">Người sửa</TH>
                    <TH width="7%" align="center">Tác vụ</TH>                    
                </TR></thead>
                <tbody>
                <%      
                NumberFormat formatter = new DecimalFormat("#,###,###"); 
                    int m = 0;
                    try{
                    	if(ctkmList != null)
                    		
                    	while (ctkmList.next()){
                        	if(m%2==0){
                %> 
                  		<TR class='tbdarkrow'>
                  		<% }else{%>
                	  
                	  	<TR class='tblightrow'>
                	  	<%
                  		   }
                        
                        String loaict="";
                     	
                        if( ctkmList.getString("type").equals("1")){
                    	 	loaict="Bình thường";
                     	}else if(ctkmList.getString("type").equals("2")){
                    	 		loaict="On top";
                     		}else{
                    	 	loaict="Tích lũy";
                     	}
                        int loaingansach=ctkmList.getInt("loaingansach");
	                  	String diengiai = "";
	                  	double ngansach=0;
	                  	ngansach= Double.parseDouble(ctkmList.getString("ngansach"));
	                  	
	                  	int sonpp=ctkmList.getInt("sonpp");
	                  	String style="";
	                  	if(sonpp>0)
	                  		style="style=\"color:red\"";
	                  	
	                  	if(ctkmList.getString("diengiai") != null ) 
	                	  	diengiai = ctkmList.getString("scheme")+ " -- " + ctkmList.getString("diengiai");
	                  	else
	                  		diengiai = ctkmList.getString("scheme"); %>
                  <TD align="center" onMouseover="ddrivetip('<%= diengiai %>', 150)"; onMouseout="hideddrivetip()">
	                  <span>
	                  <i <%=style %>>
	                  	<%= ctkmList.getString("scheme") %>
	                  </i>
                  </span>
                  </TD>
                  <TD align="left"><%=ctkmList.getString("diengiai") %></TD>
                  <TD align="center"><%= ctkmList.getString("tungay") %></TD>
                  <TD align="center"><%=  ctkmList.getString("denngay") %></TD>							                                    
                  <TD align="right"><%= formatter.format(ngansach)%></TD>
                  <TD align="center"><%=loaict %></TD>
                  <TD align="center"><%= ctkmList.getString("ngaytao") %></TD>
                  <TD align="center"><%= ctkmList.getString("nguoitao") %></TD>
                  <TD align="center"><%= ctkmList.getString("ngaysua") %></TD>	
                  <TD align="center"><%= ctkmList.getString("nguoisua") %></TD>				
                <TD align="center">   
                  <%if(quyen[Utility.XEM]!=0){ %>                  
                      <A href = "../../CtkhuyenmaiUpdateSvl?userId=<%=userId%>&copy=<%= ctkmList.getString("ctkmId")%>"><IMG src="../images/copy20.png" alt="Copy" title="Copy" border="0"></A>
                      <%} %>
                   <%if(quyen[Utility.XOA]!=0){ %>    
                  <A href = "../../CtkhuyenmaiSvl?userId=<%=userId%>&delete=<%= ctkmList.getString("ctkmId") %>"><img src="../images/Delete20.png" alt="Xóa" title="Xóa" width="20" height="20" longdesc="Xóa" border=0 onclick="if(!confirm('Bạn Có Muốn Xóa Chương Trình Khuyễn Mãi Này?')) return false;"></A>       
                  <%} %>
                   <%if(quyen[Utility.CHOT]!=0 &&loaingansach==0){ %>    
                  <A href = "../../CtkhuyenmaiSvl?userId=<%=userId%>&phanbo=<%= ctkmList.getString("ctkmId") %>"><img src="../images/Chot.png" alt="Phân bổ KM" title="Phân bổ KM" width="20" height="20" longdesc="Phân bổ KM" border=0 onclick="if(!confirm('Bạn Có Muốn phân bổ Chương Trình Khuyễn Mãi Này?')) return false;"></A>       
					<%} %>
                  
                  <%if(quyen[Utility.SUA]!=0){ %>                  
                      <A href = "../../CtkhuyenmaiUpdateSvl?userId=<%=userId%>&update=<%= ctkmList.getString("ctkmId")%>"><IMG src="../images/Edit20.png" alt="Cập nhật" title="Cập nhật" border="0"></A>
                      <%} %>
                      
                        <%if(quyen[Utility.XEM]!=0){ %>                  
                      <A href = "../../CtkhuyenmaiUpdateSvl?userId=<%=userId%>&display=<%= ctkmList.getString("ctkmId")%>"><IMG src="../images/Display20.png" alt="Xem" title="Xem" border="0"></A>
                      <%} %>                                                    								
                  </TD>                 
              	</TR>
                 	<%
                 	m++ ;
                 	}
                 }catch(java.sql.SQLException e){
                 }
                 %>
                </tbody>
                <tfoot>
				 <tr class="tbfooter" > 
											 <TD align="center" valign="middle" colspan="13" class="tbfooter">
											 	<%if(obj.getNxtApprSplitting() >1) {%>
													<img alt="Trang Dau" src="../images/first.gif" style="cursor: pointer;" onclick="View('ctkmForm', 1, 'view')"> &nbsp;
												<%}else {%>
													<img alt="Trang Dau" src="../images/first.gif" > &nbsp;
													<%} %>
												<% if(obj.getNxtApprSplitting() > 1){ %>
													<img alt="Trang Truoc" src="../images/prev.gif" style="cursor: pointer;" onclick="View('ctkmForm', eval(ctkmForm.nxtApprSplitting.value) -1, 'view')"> &nbsp;
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
													<a href="javascript:View('ctkmForm', <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
												<%} %>
													<input type="hidden" name="list" value="<%= listPage[i] %>" />  &nbsp;
												<%} %>
												
												<% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
													&nbsp; <img alt="Trang Tiep" src="../images/next.gif" style="cursor: pointer;" onclick="View('ctkmForm', eval(ctkmForm.nxtApprSplitting.value) +1, 'view')"> &nbsp;
												<%}else{ %>
													&nbsp; <img alt="Trang Tiep" src="../images/next_d.gif" > &nbsp;
												<%} %>
												<%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
											   		<img alt="Trang Cuoi" src="../images/last.gif" > &nbsp;
										   		<%}else{ %>
										   			<img alt="Trang Cuoi" src="../images/last.gif" style="cursor: pointer;" onclick="View('ctkmForm', -1, 'view')"> &nbsp;
										   		<%} %>
											</TD>
										 </tr> </tfoot> 

            </TABLE>						
         </div>
      </fieldset>
  </div> 
</div>

</form>
<script type="text/javascript" src="../scripts/loadingv2.js"></script>
<!-- <script type="text/javascript" src="../scripts/script-table-sorter.js"></script>
	<script type="text/javascript">
		var sorter = new TINY.table.sorter("sorter");
		sorter.head = "head";
		sorter.asc = 'asc'; //ascending header class name
		sorter.desc = 'desc'; //descending header class name
		sorter.even = "tblightrow";
		sorter.odd = "tbdarkrow";
		sorter.evensel = "evenselected";
		sorter.oddsel = "oddselected";
		sorter.paginate = true;
		sorter.currentid = "currentpage";
		sorter.limitid = "pagelimit";
		sorter.init("table",6);
	</script>  -->
</BODY>
<% obj.DBclose();} %>
</html>