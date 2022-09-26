<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring"    	uri="http://www.springframework.org/tags"%>
<%@ page import="java.util.*"%>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>CUBOX</title>
<link rel="stylesheet" type="text/css" href="/css/all.css?version=<%= Math.random()%>" media="all">
<script type="text/javascript" src="/js/jquery-2.2.4.min.js"></script>
<script type="text/javascript" src="/js/jquery.blockUI.js"></script>
<script type="text/javascript" src="/js/common.js?version=<%= Math.random()%>"></script>

    <!-- sweet alert -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11.4.10/dist/sweetalert2.min.css">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.4.10/dist/sweetalert2.min.js"></script>

    <!-- date picker -->
    <link rel="stylesheet" href="http://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

    <!-- 왼쪽메뉴 스크립트 -->
    <script src="/js/leftmenu/jquery.cookie.js"></script>
    <script type="text/javascript" src="/js/leftmenu/jquery.navgoco.js"></script>
    <script type="text/javascript" id="demo1-javascript">


        $(document).ready(function() {
            $("#demo1").navgoco({
                caret: '<span class="caret"></span>',
                accordion: true,
                openClass: 'open',
                save: true,
                cookie: {
                    name: 'navgoco',
                    expires: false,
                    path: '/'
                },
                slide: {
                    duration: 400,
                    easing: 'swing'
                }
            });
        });
    </script>
    <!-- //왼쪽메뉴 스크립트 -->
    <!-- 달력 input -->
    <script src="/js/datetimepicker.js"></script>
    <script>
        jQuery(document).ready(function () {
            'use strict';
            //$('#startDate, #expireDate').datetimepicker();
        });
    </script>
    <!-- //달력 input -->

<!-- 탭 -->
<script src="/js/tabcontent.js" type="text/javascript"></script>
<!-- thead 고정 js -->
<script type="text/javascript" src="/js/table/jquery.chromatable.js"></script>
<!-- 모달팝업 -->
<script src="/js/popupwindow.js"></script>

<!-- 파일 업로드 -->
<script type="text/javascript" src="/js/jquery.MultiFile.js"></script>
<!-- 트리구조 -->
<script type="text/javascript" src="/js/dTree/dtree.js"></script>

</head>