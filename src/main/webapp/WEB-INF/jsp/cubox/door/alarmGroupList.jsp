<%--
  Created by IntelliJ IDEA.
  User: USER
  Date: 2022-09-21
  Time: 오전 10:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:include page="/WEB-INF/jsp/cubox/common/checkPasswd.jsp" flush="false"/>

<style>
    .title_box {
        margin-top: 10px;
        margin-bottom: 20px;
    }
    .tb_list tr {
        height: 55px;
    }
</style>

<script type="text/javascript">
    $(function() {
        $(".title_tx").html("출입문 알람 그룹 - 목록");

        fnGetAlarmGroupListAjax();

        $("#searchBtn").click(function() {
            //param1 : 검색어, param2 : 출입문 미등록 체크박스 value값 Y or N
            fnGetAlarmGroupListAjax( $("#srchAlarmGroup").val()  );
        });

    });

    function fnDetail(self) {
        location.href = "/door/alarmGroup/detail.do";
    }

    function fnGetAlarmGroupListAjax(param1) {
        console.log("fnGetScheduleListAjax");

        $.ajax({
            type: "GET",
            data: { keyword: param1},
            dataType: "json",
            url: "<c:url value='/door/alarmGroup/list.do' />",
            success: function (result) {
                console.log("ajax success-[alarmGroupListAjax]" + result.scheduleList);
            }
        });
    }

    function pageSearch(page) {
        alert(page);
        f = document.frmSearch;
        f.action = "/diligenceAndLazinessManagement/certificationHistory.do?srchPage=" + page;
        f.submit();
    }
</script>

<%--  검색 박스 --%>
<form id="frmAlarmGroup" name="frmAlarmGroup" method="post">
    <div class="com_box">
        <%--  테이블  --%>
        <div class="tb_outbox">
            <table class="tb_list">
                <colgroup>
                    <col style="width:5%">
                    <col style="width:43%">
                    <col style="width:12%">
                    <col style="width:12%">
                    <col style="width:8%">
                    <col style="width:8%">
                    <col style="width:12%">
                </colgroup>
                <thead>
                <tr>
                    <th>No.</th>
                    <th>출입문 알람 그룹 명</th>
                    <th>유형</th>
                    <th>시간</th>
                    <th>사용</th>
                    <th>출입문 수</th>
                    <th>등록일자</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="i" begin="1" end="10" varStatus="status">
                    <tr>
                        <td><c:out value="${i}" /></td>
                        <td><a href="#none" onclick="fnDetail(this)"><c:out value="${i}"/>동 현관 출입문</a></td>
                        <td>기본시간</td>
                        <td>180초</td>
                        <td>Y</td>
                        <td>3</td>
                        <td>2022-09-01</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <%--  end of 테이블  --%>
            <!-- paging -->
        <jsp:include page="/WEB-INF/jsp/cubox/common/pagination.jsp" flush="false" />
    </div>

    <div class="search_box mb_20 mt_20" style="width: 40%;">
        <div class="search_in" style="width: 100%;">
            <div class="comm_search ml_10 mr_10" style="width: 90%;">
                <input type="text" class="input_com" id="srchAlarmGroup" name="srchAlarmGroup" value="" placeholder="출입문 알람그룹 명"/>
            </div>
            <div class="comm_search ml_40">
                <%--                <div class="search_btn2" onclick="pageSearch('1')"></div>--%>
                <div class="search_btn2" id="searchBtn"></div>
            </div>
        </div>
    </div>
<%--  end of 검색 박스 --%>

    <div class="totalbox mb_20 mt_20" style="justify-content: end; width: 60%; height: 65px;">
        <div class="r_btnbox mb_10">
            <button type="button" class="btn_excel" data-toggle="modal" id="excelDownload" onclick="openExcelDownload();">엑셀다운로드</button>
        </div>
        <div class="r_btnbox ml_10 mb_10">
            <button type="button" class="btn_excel" data-toggle="modal" onclick="location='/door/alarmGroup/addView.do'">신규등록</button>
        </div>
    </div>

    <jsp:include page="/WEB-INF/jsp/cubox/common/pagination.jsp" flush="false"/>
</form>