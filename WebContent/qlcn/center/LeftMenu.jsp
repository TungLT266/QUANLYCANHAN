<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="db.Dbutils"%>

<%
String userId = (String) session.getAttribute("userId");
%>

<html>
<head>
<LINK id="sitetheme" rel="stylesheet" href="../css/style1280.css" type="text/css">
<style type="text/css">
body {
	font-family: verdana, arial, sans-serif;
	font-size: 1em;
	margin: 1px;
	background-color: #ffffff;
}
</style>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>jQuery Accordion Example</title>

<style type="text/css">
/* A few IE bug fixes */
* {
	margin: 0;
	padding: 0;
}

* html ul ul li a {
	height: 80%;
}

* html ul li a {
	height: 80%;
}

* html ul ul li {
	margin-bottom: -1px;
}

body {
	padding-left: 0em;
	font-family: Arial, Helvetica, sans-serif;
}

#theMenu {
	width: 0px;
	height: 0px;
	margin: 0px;
	padding: 0;
}

/* Some list and link styling */
ul li {
	width: 210px;
	line-height: 20pt;
	margin-bottom: 0;
	border-left-width: 0px;
	border-left-style: none;
	border-left-color: #FFFFFF;
	background-color: #80CB9B;
	border-bottom-style: solid;
	border-bottom-color: #FFFFFF;
	border-bottom-width: thin;
	margin-left: 0px;
	padding-top: 0;
	padding-right: 0;
	padding-bottom: 0;
	padding-left: 5px;
	text-indent: 5px;
}

ul li a {
	color: #000000;
	font-size: 9pt;
	background-color: #80CB9B;
	width: 210px;
}

ul li a:hover {
	display: block;
	color: #fff;
	background-color: #80CB9B;
	font-size: small;
	width: 210px;
}

ul ul li {
	width: 210px;
	margin-left: 0px;
	background-position: left center;
	line-height: 18pt;
	border-top-style: none;
	border-right-style: none;
	border-bottom-style: none;
	border-left-style: none;
	border-top-color: #FFFFFF;
	border-right-color: #FFFFFF;
	border-bottom-color: #FFFFFF;
	border-left-color: #FFFFFF;
	background-color: #C5E8CD;
	border-bottom-width: thin;
	padding-left: 0;
	font-family: Arial, Helvetica, sans-serif;
}

ul ul li a {
	display: marker;
	color: #fff;
	padding: 0px;
	font-size: small;
	width: 210px;
}

ul ul li a:hover {
	display: block;
	color: #369;
	padding: 0px;
	font-size: small;
	width: 210px;
}

/* For the xtra menu */
ul ul li a.selected {
	display: marker;
	color: #369;
	padding: 0px;
	font-size: small;
}

ul ul ul li {
	padding: 0;
	width: 210px;
	margin-left: 0px;
	background-position: left center;
	line-height: 20pt;
	border-top-style: none;
	border-right-style: none;
	border-bottom-style: none;
	border-left-style: none;
	border-top-color: #FFFFFF;
	border-right-color: #FFFFFF;
	border-bottom-color: #FFFFFF;
	border-left-color: #FFFFFF;
	background-color: #FFFFFF;
	border-bottom-width: thin;
}

ul ul ul li a {
	display: block;
	color: #fff;
	padding: 0px;
	font-size: small;
	background-color: #FFFFFF;
}

ul ul ul li a:hover {
	display: block;
	color: #369;
	background-color: #CEF6F5;
	padding: 0px;
	font-size: small;
}

ul ul ul li a.selected {
	display: block;
	color: #369;
	background-color: #CEF6F5;
	padding: 0px;
	font-size: small;
}

/* For the sub menu */
ul ul ul ul li {
	border-left: none;
	border-bottom: none;
	padding: 0;
	width: 210px;
	margin-bottom: 0;
	margin-left: 5px;
	background-color: #F4F4F0;
}

ul ul ul ul li a {
	display: block;
	color: #fff;
	padding: 0px;
	font-size: small;
	background-color: #CCCCCC;
}

ul ul ul ul li a:hover {
	display: block;
	color: #369;
	background-color: #CEF6F5;
	padding: 3px 8px;
	font-size: small;
}

ul ul ul ul li a.selected {
	display: block;
	color: #369;
	background-color: #CEF6F5;
	padding: 3px 8px;
	font-size: small;
}

li {
	list-style-type: none;
}

h2 {
	margin-top: 0em;
}

ul li ul li ul li ul
{
	overflow-y:auto;
	overflow-x:hidden;
}

ul li ul li ul li ul
{
	overflow-y:auto;
	overflow-x:hidden;
}

</style>

<style>

header
{
	font-family: 'Lobster', cursive;
	text-align: center;
	font-size: 25px;	
}

#info
{
	font-size: 18px;
	color: #555;
	text-align: center;
	margin-bottom: 25px;
}

a{
	color: #074E8C;
}

.scrollbar
{
	/* margin-left: 30px;
	float: left;
	height: 300px;
	width: 65px;
	background: #F5F5F5;
	overflow-y: scroll;
	margin-bottom: 25px; */
	
	float: left;
	height: 100%;
	width: 210px;
	background: #F5F5F5;
	overflow-y: scroll;
}

.force-overflow
{
	min-height: 450px;
}

#wrapper
{
	text-align: center;
	width: 500px;
	margin: auto;
}

/*
 *  STYLE 1
 */

#style-1::-webkit-scrollbar-track
{
	-webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.3);
	border-radius: 10px;
	background-color: #F5F5F5;
}

#style-1::-webkit-scrollbar
{
	width: 12px;
	background-color: #F5F5F5;
}

#style-1::-webkit-scrollbar-thumb
{
	border-radius: 10px;
	-webkit-box-shadow: inset 0 0 6px rgba(0,0,0,.3);
	background-color: #555;
}

/*
 *  STYLE 2
 */

#style-2::-webkit-scrollbar-track
{
	-webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.3);
	border-radius: 10px;
	background-color: #F5F5F5;
}

#style-2::-webkit-scrollbar
{
	width: 12px;
	background-color: #F5F5F5;
}

#style-2::-webkit-scrollbar-thumb
{
	border-radius: 10px;
	-webkit-box-shadow: inset 0 0 6px rgba(0,0,0,.3);
	background-color: #D62929;
}

/*
 *  STYLE 3
 */

#style-3::-webkit-scrollbar-track
{
	-webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.3);
	background-color: #F5F5F5;
}

#style-3::-webkit-scrollbar
{
	width: 6px;
	background-color: #F5F5F5;
}

#style-3::-webkit-scrollbar-thumb
{
	background-color: #000000;
}

/*
 *  STYLE 4
 */

#style-4::-webkit-scrollbar-track
{
	-webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.3);
	background-color: #F5F5F5;
}

#style-4::-webkit-scrollbar
{
	width: 10px;
	background-color: #F5F5F5;
}

#style-4::-webkit-scrollbar-thumb
{
	background-color: #000000;
	border: 2px solid #555555;
}


/*
 *  STYLE 5
 */

#style-5::-webkit-scrollbar-track
{
	-webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.3);
	background-color: #F5F5F5;
}

#style-5::-webkit-scrollbar
{
	width: 10px;
	background-color: #F5F5F5;
}

#style-5::-webkit-scrollbar-thumb
{
	background-color: #0ae;
	background-image: -webkit-gradient(linear, 0 0, 0 100%, color-stop(.5, rgba(255, 255, 255, .2)), color-stop(.5, transparent), to(transparent));
}


/*
 *  STYLE 6
 */

#style-6::-webkit-scrollbar-track
{
	-webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.3);
	background-color: #F5F5F5;
}

#style-6::-webkit-scrollbar
{
	width: 10px;
	background-color: #F5F5F5;
}

#style-6::-webkit-scrollbar-thumb
{
	background-color: #F90;	
	background-image: -webkit-linear-gradient(45deg, rgba(255, 255, 255, .2) 25%, transparent 25%, transparent 50%,
			rgba(255, 255, 255, .2) 50%, rgba(255, 255, 255, .2) 75%, transparent 75%, transparent)
}


/*
 *  STYLE 7
 */

#style-7::-webkit-scrollbar-track
{
	-webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.3);
	background-color: #F5F5F5;
	border-radius: 10px;
}

#style-7::-webkit-scrollbar
{
	width: 10px;
	background-color: #F5F5F5;
}

#style-7::-webkit-scrollbar-thumb
{
	border-radius: 10px;
	background-image: -webkit-gradient(linear, left bottom, left top, color-stop(0.44, rgb(122,153,217)),
			color-stop(0.72, rgb(73,125,189)), color-stop(0.86, rgb(28,58,148)));
}

/*
 *  STYLE 8
 */

#style-8::-webkit-scrollbar-track
{
	border: 1px solid black;
	background-color: #F5F5F5;
}

#style-8::-webkit-scrollbar
{
	width: 10px;
	background-color: #F5F5F5;
}

#style-8::-webkit-scrollbar-thumb
{
	background-color: #000000;	
}


/*
 *  STYLE 9
 */

#style-9::-webkit-scrollbar-track
{
	-webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.3);
	background-color: #F5F5F5;
}

#style-9::-webkit-scrollbar
{
	width: 10px;
	background-color: #F5F5F5;
}

#style-9::-webkit-scrollbar-thumb
{
	background-color: #F90;	
	background-image: -webkit-linear-gradient(90deg, rgba(255, 255, 255, .2) 25%, transparent 25%, transparent 50%,
			rgba(255, 255, 255, .2) 50%, rgba(255, 255, 255, .2) 75%, transparent 75%, transparent)
}


/*
 *  STYLE 10
 */

#style-10::-webkit-scrollbar-track
{
	-webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.3);
	background-color: #F5F5F5;
	border-radius: 10px;
}

#style-10::-webkit-scrollbar
{
	width: 10px;
	background-color: #F5F5F5;
}

#style-10::-webkit-scrollbar-thumb
{
	background-color: #AAA;
	border-radius: 10px;
	background-image: -webkit-linear-gradient(90deg, rgba(0, 0, 0, .2) 25%, transparent 25%, transparent 50%,
			rgba(0, 0, 0, .2) 50%, rgba(0, 0, 0, .2) 75%, transparent 75%, transparent)
}


/*
 *  STYLE 11
 */

#style-11::-webkit-scrollbar-track
{
	-webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.3);
	background-color: #F5F5F5;
	border-radius: 10px;
}

#style-11::-webkit-scrollbar
{
	width: 10px;
	background-color: #F5F5F5;
}

#style-11::-webkit-scrollbar-thumb
{
	background-color: #3366FF;
	border-radius: 10px;
	background-image: -webkit-linear-gradient(0deg, rgba(255, 255, 255, 0.5) 25%, transparent 25%, transparent 50%,
			rgba(255, 255, 255, 0.5) 50%, rgba(255, 255, 255, 0.5) 75%, transparent 75%, transparent)
}

/*
 *  STYLE 12
 */

#style-12::-webkit-scrollbar-track
{
	-webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.9);
	border-radius: 10px;
	background-color: #444444;
}

#style-12::-webkit-scrollbar
{
	width: 12px;
	background-color: #F5F5F5;
}

#style-12::-webkit-scrollbar-thumb
{
	border-radius: 10px;
	background-color: #D62929;
	background-image: -webkit-linear-gradient(90deg, transparent, rgba(0, 0, 0, 0.4) 50%, transparent, transparent)
}

/*
 *  STYLE 13
 */

#style-13::-webkit-scrollbar-track
{
	-webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.9);
	border-radius: 10px;
	background-color: #CCCCCC;
}

#style-13::-webkit-scrollbar
{
	width: 12px;
	background-color: #F5F5F5;
}

#style-13::-webkit-scrollbar-thumb
{
	border-radius: 10px;
	background-color: #D62929;
	background-image: -webkit-linear-gradient(90deg, transparent, rgba(0, 0, 0, 0.4) 50%, transparent, transparent)
}

/*
 *  STYLE 14
 */

#style-14::-webkit-scrollbar-track
{
	-webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.6);
	background-color: #CCCCCC;
}

#style-14::-webkit-scrollbar
{
	width: 10px;
	background-color: #F5F5F5;
}

#style-14::-webkit-scrollbar-thumb
{
	background-color: #FFF;
	background-image: -webkit-linear-gradient(90deg, rgba(0, 0, 0, 1) 0%, rgba(0, 0, 0, 1) 25%, transparent 100%, rgba(0, 0, 0, 1) 75%, transparent)
}

/*
 *  STYLE 15
 */

#style-15::-webkit-scrollbar-track
{
	-webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.1);
	background-color: #F5F5F5;
	border-radius: 10px;
}

#style-15::-webkit-scrollbar
{
	width: 10px;
	background-color: #F5F5F5;
}

#style-15::-webkit-scrollbar-thumb
{
	border-radius: 10px;
	background-color: #FFF;
	background-image: -webkit-gradient(linear, 40% 0%, 75% 84%, from(#4D9C41), to(#19911D), color-stop(.6,#54DE5D))
}

/*
 *  STYLE 16
 */

#style-16::-webkit-scrollbar-track
{
	-webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.1);
	background-color: #F5F5F5;
	border-radius: 10px;
}

#style-16::-webkit-scrollbar
{
	width: 10px;
	background-color: #F5F5F5;
}

#style-16::-webkit-scrollbar-thumb
{
	border-radius: 10px;
	background-color: #FFF;
	background-image: -webkit-linear-gradient(top, #e4f5fc 0%, #bfe8f9 50%, #9fd8ef 51%, #2ab0ed 100%);
}



</style><style type="text/css">
body {
	font-family: verdana, arial, sans-serif;
	font-size: 1em;
	margin: 1px;
	background-color: #ffffff;
}
</style>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>jQuery Accordion Example</title>

<style type="text/css">

/* width */
::-webkit-scrollbar {
    width: 5px;
}

/* Track */
::-webkit-scrollbar-track {
    box-shadow: inset 0 0 5px grey; 
    border-radius: 10px;
}
 
/* Handle */
::-webkit-scrollbar-thumb {
    background: #DDDDDD;
    border-radius: 10px;
}

/* Handle on hover */
::-webkit-scrollbar-thumb:hover {
    background: #DDDDDD !important;
}

/* A few IE bug fixes */
* {
	margin: 0;
	padding: 0;
}

* html ul ul li a {
	height: 80%;
}

* html ul li a {
	height: 80%;
}

* html ul ul li {
	margin-bottom: -1px;
}

body {
	padding-left: 0em;
	font-family: Arial, Helvetica, sans-serif;
}

#theMenu {
	width: 0px;
	height: 0px;
	margin: 0px;
	padding: 0;
}

/* Some list and link styling */
ul li {
	width: 210px;
	line-height: 20pt;
	margin-bottom: 0;
	border-left-width: 0px;
	border-left-style: none;
	border-left-color: #FFFFFF;
	background-color: #DDDDDD;
	border-bottom-style: solid;
	border-bottom-color: #FFFFFF;
	border-bottom-width: thin;
	margin-left: 0px;
	padding-top: 0;
	padding-right: 0;
	padding-bottom: 0;
	padding-left: 5px;
	text-indent: 5px;
}

ul li ul li {
	width: 210px;
	line-height: 20pt;
	margin-bottom: 0;
	border-left-width: 0px;
	border-left-style: none;
	border-left-color: #FFFFFF;
	background-color: #DDDDDD;
	border-bottom-style: solid;
	border-bottom-color: #FFFFFF;
	border-bottom-width: thin;
	margin-left: 0px;
	padding-top: 0;
	padding-right: 0;
	padding-bottom: 0;
	padding-left: 5px;
	text-indent: 5px;
}

ul li ul li ul li {
	width: 210px;
	line-height: 20pt;
	margin-bottom: 0;
	border-left-width: 0px;
	border-left-style: none;
	border-left-color: #FFFFFF;
	background-color: #DDDDDD;
	border-bottom-style: solid;
	border-bottom-color: #FFFFFF;
	border-bottom-width: thin;
	margin-left: 0px;
	padding-top: 0;
	padding-right: 0;
	padding-bottom: 0;
	padding-left: -5px;
	text-indent: 5px;
}

ul li a {
	color: #000000;
	font-size: 9pt;
	background-color: #80CB9B;
	width: 210px;
}

ul li a:hover {
	display: block;
	color: #fff;
	background-color: #80CB9B;
	font-size: small;
	width: 210px;
}

ul ul li {
	width: 210px;
	margin-left: 0px;
	background-position: left center;
	line-height: 18pt;
	border-top-style: none;
	border-right-style: none;
	border-bottom-style: none;
	border-left-style: none;
	border-top-color: #FFFFFF;
	border-right-color: #FFFFFF;
	border-bottom-color: #FFFFFF;
	border-left-color: #FFFFFF;
	background-color: #DDDDDD;
	border-bottom-width: thin;
	padding-left: 0;
	font-family: Arial, Helvetica, sans-serif;
}

ul ul li a {
	display: marker;
	color: #fff;
	padding: 0px;
	font-size: small;
	width: 210px;
}

ul ul li a:hover {
	display: block;
	color: #369;
	padding: 0px;
	font-size: small;
	width: 210px;
}

/* For the xtra menu */
ul ul li a.selected {
	display: marker;
	color: #369;
	padding: 0px;
	font-size: small;
}

ul ul ul li {
	padding: 0;
	width: 210px;
	margin-left: 0px;
	background-position: left center;
	line-height: 20pt;
	border-top-style: none;
	border-right-style: none;
	border-bottom-style: none;
	border-left-style: none;
	border-top-color: #FFFFFF;
	border-right-color: #FFFFFF;
	border-bottom-color: #FFFFFF;
	border-left-color: #FFFFFF;
}

ul ul ul li a {
	display: block;
	color: #fff;
	padding: 0px;
	font-size: small;
	background-color: #FFFFFF;
}

ul ul ul li a:hover {
	display: block;
	color: #369;
	background-color: #CEF6F5;
	padding: 0px;
	font-size: small;
}

ul ul ul li a.selected {
	display: block;
	color: #369;
	background-color: #CEF6F5;
	padding: 0px;
	font-size: small;
}

/* For the sub menu */
ul ul ul ul li {
	border-left: none;
	border-bottom: none;
	padding: 0;
	width: 210px;
	margin-bottom: 0;
	margin-left: 5px;
	background-color: #F4F4F0;
}

ul ul ul ul li a {
	display: block;
	color: #fff;
	padding: 0px;
	font-size: small;
	background-color: #CCCCCC;
}

ul ul ul ul li a:hover {
	display: block;
	color: #369;
	background-color: #CEF6F5;
	padding: 3px 8px;
	font-size: small;
}

ul ul ul ul li a.selected {
	display: block;
	color: #369;
	background-color: #CEF6F5;
	padding: 3px 8px;
	font-size: small;
}

li {
	list-style-type: none;
}

h2 {
	margin-top: 0em;
}
ul li ul li ul li ul
{
 overflow-y:auto;
 overflow-x:hidden;
}

ul li ul li ul li ul
{
 overflow-y:auto;
 overflow-x:hidden;
}
</style>

</head>
<script type="text/javascript" src="../scripts/jquery.js"></script>
<script type="text/javascript" src="../scripts/accordion.js"></script>
<script type="text/javascript">
	jQuery().ready(function() {
		jQuery("a").click(function(e) {
			jQuery("a").css("color", "black");
			jQuery(this).css("color", "red");
			
			if (e.target.id.indexOf('Final')>=0){
				jQuery("h5.selected").toggleClass('selected');
				jQuery('#sub'+e.target.id).addClass('head selected');
			}
		});

		// applying the settings
		jQuery('#theMenu').Accordion({
			active : 'h3.selected',
			header : 'h3.head',
			alwaysOpen : false,
			animated : true,
			showSpeed : 400,
			hideSpeed : 800
		});
		jQuery('#xtraMenu1').Accordion({
			active : 'h4.selected',
			header : 'h4.head',
			alwaysOpen : false,
			animated : true,
			showSpeed : 400,
			hideSpeed : 800
		});

		jQuery('#xtraMenu2').Accordion({
			active : 'h4.selected',
			header : 'h4.head',
			alwaysOpen : false,
			animated : true,
			showSpeed : 400,
			hideSpeed : 800
		});

		jQuery('#xtraMenu3').Accordion({
			active : 'h4.selected',
			header : 'h4.head',
			alwaysOpen : false,
			animated : true,
			showSpeed : 400,
			hideSpeed : 800
		});

		jQuery('#xtraMenu4').Accordion({
			active : 'h4.selected',
			header : 'h4.head',
			alwaysOpen : false,
			animated : true,
			showSpeed : 400,
			hideSpeed : 800
		});

		jQuery('#xtraMenu5').Accordion({
			active : 'h4.selected',
			header : 'h4.head',
			alwaysOpen : false,
			animated : true,
			showSpeed : 400,
			hideSpeed : 800
		});

		jQuery('#xtraMenu6').Accordion({
			active : 'h4.selected',
			header : 'h4.head',
			alwaysOpen : false,
			animated : true,
			showSpeed : 400,
			hideSpeed : 800
		});
		
		jQuery('#xtraMenu7').Accordion({
			active : 'h4.selected',
			header : 'h4.head',
			alwaysOpen : false,
			animated : true,
			showSpeed : 400,
			hideSpeed : 800
		});
		
		jQuery('#xtraMenu8').Accordion({
			active : 'h4.selected',
			header : 'h4.head',
			alwaysOpen : false,
			animated : true,
			showSpeed : 400,
			hideSpeed : 800
		});
		
		jQuery('#xtraMenu9').Accordion({
			active : 'h4.selected',
			header : 'h4.head',
			alwaysOpen : false,
			animated : true,
			showSpeed : 400,
			hideSpeed : 800
		});
		jQuery('#xtraMenu10').Accordion({
			active : 'h4.selected',
			header : 'h4.head',
			alwaysOpen : false,
			animated : true,
			showSpeed : 400,
			hideSpeed : 800
		});
		jQuery('#xtraMenu11').Accordion({
			active : 'h4.selected',
			header : 'h4.head',
			alwaysOpen : false,
			animated : true,
			showSpeed : 400,
			hideSpeed : 800
		});
		jQuery('#xtraMenu12').Accordion({
			active : 'h4.selected',
			header : 'h4.head',
			alwaysOpen : false,
			animated : true,
			showSpeed : 400,
			hideSpeed : 800
		});
		
		jQuery('#subMenu').Accordion({
			active : 'h6.selected',
			header : 'h6.head',
			alwaysOpen : false,
			animated : true,
			showSpeed : 400,
			hideSpeed : 800
		});

	});
</script>

<body>
	<div  class="scrollbar" id="style-3" >
		<ul id="theMenu">
			<%
			Dbutils db = new Dbutils();
			String query = "SELECT A.NAME AS TENLV1, A.PARAMETERS AS THAMSOLV1, A.SERVLETS AS SVLLV1,"
					+ "\n	ISNULL(("
					+ "\n		SELECT COUNT(*) FROM MENUQLTS A1 LEFT JOIN MENUQLTS B1 ON B1.PARENTID = A1.ID AND B1.TrangThai = 1 WHERE A1.TRANGTHAI = 1 AND A1.PARENTID = A.ID"
					+ "\n	),0) AS SOLANLAP1, B.NAME AS TENLV2, B.PARAMETERS AS THAMSOLV2, B.SERVLETS AS SVLLV2,"
					+ "\n	ISNULL((SELECT COUNT(*) FROM MENUQLTS A2 WHERE A2.TRANGTHAI = 1 AND A2.PARENTID = B.ID),0) AS SOLANLAP2,"
					+ "\n 	C.NAME AS TENLV3, C.PARAMETERS AS THAMSOLV3, C.SERVLETS AS SVLLV3"
					+ "\n FROM MENUQLTS A"
					+ "\n LEFT JOIN MENUQLTS B ON B.PARENTID = A.ID AND B.TrangThai = 1"
					+ "\n LEFT JOIN MENUQLTS C ON C.PARENTID = B.ID AND C.TrangThai = 1"
					+ "\n WHERE A.LEVEL = 1 AND A.TRANGTHAI = 1 ORDER BY A.STT, B.STT, C.STT";
			System.out.println(query);
			ResultSet rs = db.get(query);
			
			int sttLv1 = 1;
			int sttCuoi = 1;
			%>
			
			<%if(rs != null) { %>
				<%while(rs.next()) { %>
					<%if(rs.getInt("SOLANLAP1") > 0) { %>
						<li style='position: static;'>
							<h3 class='head'><a href='' class='head'><%=rs.getString("TENLV1") %></a></h3>
							<ul style='display: block;'>
								<li>
									<ul id='xtraMenu<%=sttLv1 %>'>
										<%if(rs.getInt("SOLANLAP2") > 0) { %>
											<li>
												<h4 class='head' ><a href=''><%=rs.getString("TENLV2") %></a></h4>
												<ul style='display: none; max-height: 300px;'>
													<li>
														<h5 id='subFinal<%=sttCuoi + sttLv1 %>' class='head'>
															<a id='Final<%=sttCuoi + sttLv1 %>' href='/QUANLYCANHAN/<%=rs.getString("SVLLV3") %>?userId=<%=userId %>' target='content'><%=rs.getString("TENLV3") %></a>
														</h5>
													</li>
													<%sttCuoi++; %>
														
													<%for(int j = 1; j < rs.getInt("SOLANLAP2"); j++) { %>
														<%rs.next(); %>
														<li>
															<h5 id='subFinal<%=sttCuoi + sttLv1 %>' class='head'>
																<a id='Final<%=sttCuoi + sttLv1 %>' href='/QUANLYCANHAN/<%=rs.getString("SVLLV3") %>?userId=<%=userId %>' target='content'><%=rs.getString("TENLV3") %></a>
															</h5>
														</li>
														<%sttCuoi++; %>
													<%} %>
												</ul>
											</li>
										<%} else { %>
											<li>
												<h5 id='subFinal<%=sttCuoi + sttLv1 %>' class='head'>
													<a id='Final<%=sttCuoi + sttLv1 %>' href='/QUANLYCANHAN/<%=rs.getString("SVLLV2") %>?userId=<%=userId %>' target='content'><%=rs.getString("TENLV2") %></a>
												</h5>
											</li>
											<%sttCuoi++; %>
										<%} %>
									
										<%for(int i = 1; i < rs.getInt("SOLANLAP1"); i++) { %>
											<%rs.next(); %>
											<%if(rs.getInt("SOLANLAP2") > 0) { %>
												<li>
													<h4 class='head' ><a href=''><%=rs.getString("TENLV2") %></a></h4>
													<ul style='display: none; max-height: 300px;'>
														<li>
															<h5 id='subFinal<%=sttCuoi + sttLv1 %>' class='head'>
																<a id='Final<%=sttCuoi + sttLv1 %>' href='/QUANLYCANHAN/<%=rs.getString("SVLLV3") %>?userId=<%=userId %>' target='content'><%=rs.getString("TENLV3") %></a>
															</h5>
														</li>
														<%sttCuoi++; %>
															
														<%for(int j = 1; j < rs.getInt("SOLANLAP2"); j++) { %>
															<%rs.next(); %>
															<li>
																<h5 id='subFinal<%=sttCuoi + sttLv1 %>' class='head'>
																	<a id='Final<%=sttCuoi + sttLv1 %>' href='/QUANLYCANHAN/<%=rs.getString("SVLLV3") %>?userId=<%=userId %>' target='content'><%=rs.getString("TENLV3") %></a>
																</h5>
															</li>
															<%sttCuoi++; %>
														<%} %>
													</ul>
												</li>
											<%} else { %>
												<li>
													<h5 id='subFinal<%=sttCuoi + sttLv1 %>' class='head'>
														<a id='Final<%=sttCuoi + sttLv1 %>' href='/QUANLYCANHAN/<%=rs.getString("SVLLV2") %>?userId=<%=userId %>' target='content'><%=rs.getString("TENLV2") %></a>
													</h5>
												</li>
												<%sttCuoi++; %>
											<%} %>
										<%} %>
									</ul>
								</li>
							</ul>
						</li>
					<%} else { %>
						<li>
							<h5 id='subFinal<%=sttCuoi + sttLv1 %>' class='head'>
								<a id='Final<%=sttCuoi + sttLv1 %>' href='/QUANLYCANHAN/<%=rs.getString("SVLLV1") %>?userId=<%=userId %>' target='content'><%=rs.getString("TENLV1") %></a>
							</h5>
						</li>
						<%sttCuoi++; %>
					<%} %>
					<%sttLv1++; %>
				<%} %>
				<%rs.close(); %>
			<%} %>
		</ul>
	</div>
</body>
</html>
<%
db.shutDown();
%>

