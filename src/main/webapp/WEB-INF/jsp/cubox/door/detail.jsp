<%--
  Created by IntelliJ IDEA.
  User: USER
  Date: 2022-09-02
  Time: 오후 3:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="/WEB-INF/jsp/cubox/common/checkPasswd.jsp" flush="false"/>

<%--<script src="/js/timepicker/jquery.timepicker.min.js"></script>--%>
<%--<link rel="stylesheet" href="/js/timepicker/jquery.timepicker.min.css" />--%>

<style>
    .title_box {
        margin-top: 10px;
        margin-bottom: 20px;
    }
    .box {
        border: 1px solid #ccc;
    }
    #tdScheduleDetail tr th {
        text-align: center;
    }
    thead {
        position: sticky;
        top: 0;
    }
    #tdTimePick tr td input {
        width: 130px;
        height: 25px;
        text-align: center;
        margin: 0 auto;
    }
    #tb_Schedule tr td,
    #tb_SchTimepick tr th, #tb_SchTimepick tr td {
        background: none;
    }
    #tb_SchTimepick tr th, #tb_SchTimepick tr td {
        border: none;
        text-align: end;
    }
    .tb_list tbody tr:nth-child(2n) td {
        background-color: transparent !important;
    }
    .col_days {
        font-weight: bold;
    }
    .colored {
        background-color: coral;
    }

</style>

<script type="text/javascript">
    $(function() {
        $(".title_tx").html("출입문 스케쥴 - 상세");
        modalPopup("addByDayPopup", "요일 별 스케쥴 등록", 2000, 1100);

        $("input[type=time]").on({
            change: function() {

                let el = $(this);
                let day = this.id.split("_")[0];
                let schNum = this.id.split("_")[1];
                let ifEnd = this.id.split("_")[2] == "end";
                let start = {hour : "", min : ""};
                let end = {hour : "", min : ""};

                // console.log("this val= " + $(this).val()); //00:01:00

                if (ifEnd) {
                    console.log("ifEnd");

                    let startId = this.id.replace("end", "start");

                    start.hour = $("#" + startId).val().split(":")[0];
                    start.min = $("#" + startId).val().split(":")[1];
                    start.sec = $("#" + startId).val().split(":")[2];
                    end.hour = el.val().split(":")[0];
                    end.min = el.val().split(":")[1];
                    end.sec = el.val().split(":")[2];

                    if (timeValid(startId, this.id, start, end, day, schNum)) {
                        validCheck(startId, this.id, start, end, day, schNum);
                    }

                } else {  // 수정
                    console.log("ifStart");

                    let endId = this.id.replace("start", "end");

                    start.hour = el.val().split(":")[0];
                    start.min = el.val().split(":")[1];
                    start.sec = el.val().split(":")[2];
                    end.hour = $("#" + endId).val().split(":")[0];
                    end.min = $("#" + endId).val().split(":")[1];
                    end.sec = $("#" + endId).val().split(":")[2];

                    validCheck(this.id, endId, start, end, day, schNum);
                }
            }
        });

    });

    // time선택 초기화
    function initTimepicker(start, end) {
        $("#" + start).val("");
        $("#" + end).val("");
    }

    // 색칠 초기화
    function initColor(day, schNum) {
        let editSch = $("." + day + "_" + schNum);

        if (editSch.length != 0) {
            for (let i in editSch) {
                if (i == 0) editSch.eq(i).val("");
                if (i == editSch.length - 1) editSch.eq(i).val("");
                editSch.eq(i).removeClass("colored");
                editSch.eq(i).removeClass(day + "_" + schNum);
            }
        }
    }

    function validCheck(startId, endId, start, end, day, schNum) {
        if (ifValid(startId, endId, start, end, day, schNum)) {
            console.log("색칠하기_ifEnd");
            colorSchedule(start, end, day, schNum);
        } else {
            console.log("색칠안하기_ifEnd");
        }
    }

    // 이미 색칠되어 있는지 여부확인
    function ifValid(startId, endId, start, end, day, schNum) {
        let result = true;

        if (start.hour != end.hour) { // 시작시간과 종료시간이 다른 hour 칸에 있을 때
            for (let i = Number(start.hour); i <= Number(end.hour); i++) {

                if (i == Number(start.hour)) { // 시작 hour 칸
                    for (let j = Number(start.min); j < 60; j++) {
                        let divToColor = $(".timeline_" + day + ("00" + i).slice(-2) + "_" + ("00" + j).slice(-2)); // 색칠할 div
                        if ((divToColor.hasClass("colored")) && (!divToColor.hasClass(day + "_" + schNum))) {
                            console.log("1. 색칠되어있고 같은 스케쥴 아님");
                            result = false;
                            break;
                        }
                    }
                } else if (i == end.hour) { // 종료 hour 칸
                    for (let j = 0; j <= Number(end.min); j++) {
                        let divToColor = $(".timeline_" + day + ("00" + i).slice(-2) + "_" + ("00" + j).slice(-2)); // 색칠할 div
                        if ((divToColor.hasClass("colored")) && (!divToColor.hasClass(day + "_" + schNum))) {
                            console.log("2. 색칠되어있고 같은 스케쥴 아님");
                            result = false;
                            break;
                        }
                    }
                } else {
                    for (let j = 0; j < 60; j++) {
                        let divToColor = $(".timeline_" + day + ("00" + i).slice(-2) + "_" + ("00" + j).slice(-2)); // 색칠할 div
                        if ((divToColor.hasClass("colored")) && (!divToColor.hasClass(day + "_" + schNum))) {
                            console.log("3. 색칠되어있고 같은 스케쥴 아님");
                            result = false;
                            break;
                        }
                    }
                }
            }

        } else if (start.hour == end.hour) {  // 시작시간과 종료시간이 같은 hour 칸에 있을 때
            for (let i = Number(start.hour); i <= Number(end.hour); i++) {
                for (let j = Number(start.min); j <= Number(end.min); j++) {
                    let divToColor = $(".timeline_" + day + ("00" + i).slice(-2) + "_" + ("00" + j).slice(-2)); // 색칠할 div

                    if ((divToColor.hasClass("colored")) && (!divToColor.hasClass(day + "_" + schNum))) {
                        console.log("4. 색칠되어있고 같은 스케쥴 아님");
                        result = false;
                        break;
                    }
                }
            }
        }

        if (!result) isFirstReg(day, schNum, startId, endId);

        console.log("ifValid 결과: " + result);
        return result;
    }

    // 최초 등록인지 체크
    function isFirstReg(day, schNum, startId, endId) {

        let isFirst = ($("." + day + "_" + schNum).length > 0) ? false : true;  // 최츠 등록?

        if (isFirst) {
            console.log("최초등록");
            alert("중복된 스케쥴이 존재합니다. 다시 선택해주세요.");
            initTimepicker(startId, endId); // timepicker 초기화

        } else {
            console.log("이미 같은 시간대에 존재");
            alert("중복된 스케쥴이 존재합니다."); // timepicker 초기화 아닌 원상복구
            let tmpStart = $("div." + day + "_" + schNum).first().val();
            let tmpEnd = $("div." + day + "_" + schNum).last().val();
            $("#" + startId).val(tmpStart.hour + ":" + tmpStart.min + ":" + tmpStart.sec); // 수정 시
            $("#" + endId).val(tmpEnd.hour + ":" + tmpEnd.min + ":" + tmpEnd.sec);
            return;
        }
    }


    // 시간 유효성 체크
    function timeValid(startId, endId, start, end, day, schNum) {
        let result = true;

        if ($("#" + startId).val() == "") {
            alert("시작 시간을 먼저 선택해주세요.");
            initTimepicker(startId, endId);
            result = false;
        } else if ($("#" + startId).val() != "" && $("#" + startId).val() >= $("#" + endId).val()) { // 시작 시간이 종료시간보다 클 때

            if (!($("." + day + "_" + schNum).length > 0)) { // 최초등록
                console.log("timevalid 최초등록");
                alert("시작시간은 종료시간보다 빨라야합니다.");
                initTimepicker(startId, endId);
            } else {
                console.log("timevalid 최초등록 아님");
                alert("시작시간은 종료시간보다 빨라야합니다.");
                let tmpStart = $("div." + day + "_" + schNum).first().val();
                let tmpEnd = $("div." + day + "_" + schNum).last().val();
                $("#" + startId).val(tmpStart.hour + ":" + tmpStart.min + ":" + tmpStart.sec); // 수정 시
                $("#" + endId).val(tmpEnd.hour + ":" + tmpEnd.min + ":" + tmpEnd.sec);
            }

            result = false;
        }
        console.log("timeValid 결과: " + result);
        return result;
    }

    // 해당 범위 색칠
    function colorSchedule(start, end, day, schNum) {
        initColor(day, schNum);

        if (start.hour != end.hour) {
            for (let i = start.hour; i <= end.hour; i++) {  // hour

                // 분단위 coloring
                if (i == start.hour) { // 시작 hour 칸
                    for (let j = Number(start.min); j < 60; j++) {
                        let divToColor = $(".timeline_" + day + ("00" + i).slice(-2) + "_" + ("00" + j).slice(-2));
                        if (j == Number(start.min)) divToColor.val(start); // 첫번째 div에 시작시간 value 넣기
                        divToColor.addClass("colored");
                        divToColor.addClass(day + "_" + schNum); // mon_2
                    }
                } else if (i == end.hour) { // 종료 hour 칸
                    for (let j = 0; j <= Number(end.min); j++) {
                        let divToColor = $(".timeline_" + day + ("00" + i).slice(-2) + "_" + ("00" + j).slice(-2));
                        if (j == Number(end.min)) divToColor.val(end); // 마지막 div에 종료시간 value 넣기
                        divToColor.addClass("colored");
                        divToColor.addClass(day + "_" + schNum);
                    }
                } else {
                    for (let j = 0; j < 60; j++) {
                        let divToColor = $(".timeline_" + day + ("00" + i).slice(-2) + "_" + ("00" + j).slice(-2));
                        divToColor.addClass("colored");
                        divToColor.addClass(day + "_" + schNum);
                    }
                }
            }
        } else if (start.hour == end.hour) {
            for (let i = Number(start.hour); i <= Number(end.hour); i++) {  // hour
                // 분단위 coloring
                if (i == Number(start.hour)) { // 시작 hour 칸
                    for (let j = Number(start.min); j <= Number(end.min); j++) {
                        let divToColor = $(".timeline_" + day + ("00" + i).slice(-2) + "_" + ("00" + j).slice(-2));
                        if (j == Number(start.min)) divToColor.val(start); // 첫번째 div에 시작시간 value 넣기
                        if (j == Number(end.min)) divToColor.val(end); // 마지막 div에 종료시간 value 넣기
                        divToColor.addClass("colored");
                        divToColor.addClass(day + "_" + schNum); // mon_2
                    }
                }
            }
        }
    }

    // 목록 버튼
    function fnList() {
        // 출입문 목록으로 페이지 이동
        location.href = "/gate/schedule.do";
    }

    // 수정 버튼
    function fnEdit() {
        // location.href = "/gate/schedule_add.do?mode=edit";
        f = document.detailForm;
        f.action = "/gate/schedule_add.do";
        $("input[name=schName]").attr("disabled", false);
        $("select[name=schUseYn]").attr("disabled", false);
        $("textarea[name=gateGroup]").attr("disabled", false);
        f.submit();
    }

    // 삭제 버튼
    function fnDelete() {
        // 출입문 그룹에 그룹이 있으면,
        if ($("gateGroup").html() == "") {
            alert("연결된 출입문 그룹을 해제 후 삭제 하시기 바랍니다.");
            return;
        }

        if (!confirm("삭제하시겠습니까?")) {
            return;
        }
        location.href = "/door/schedule_delete.do";

        // $.ajax({
        //     type : "post",
        //     url  : "/door/schedule_delete.do",
        //     data : {
        //        "id" = id
        //     },
        //     dataType :'json',
        //     success  : function(data, status) {
        //         if(data.result == "Y"){
        //             location.href = "/door/schedule.do";
        //         } else {
        //             alert("삭제 중 오류가 발생하였습니다.");
        //         }
        //     }
        // });
    }

    // 스케쥴 저장
    function saveSchedule() {
        closePopup("addByDayPopup");
    }

    // popup open (공통)
    function openPopup(popupNm) {
        $("#" + popupNm).PopupWindow("open");
    }

    // popup close (공통)
    function closePopup(popupNm) {
        $("#" + popupNm).PopupWindow("close");

        // 종료시간 없는 스케쥴은 종료시간 clear
        let timeList = $("input[type=time]");
        for (let i = 0; i <= timeList.length; i++) {
            let isStart = timeList.eq(i).hasClass("start");
            if (isStart) {
                let endId = timeList.eq(i).attr("id").replace("start", "end");
                let endPick = $("#" + endId).val();

                if (timeList.eq(i).val() != "" && endPick == "") { // start 값 있음, end 값 없음
                    console.log("startVal 있음, endVal 없음");
                    timeList.eq(i).val("");
                }
            }
        }
    }

</script>
<div id="timepicker-wrapper"></div>
<form id="detailForm" name="detailForm" method="post" enctype="multipart/form-data">
    <input type="hidden" id="editMode" name="editMode" value="edit"/>
    <div class="tb_01_box">
        <table class="tb_write_02 tb_write_p1 box">
            <colgroup>
                <col style="width:10%">
                <col style="width:90%">
            </colgroup>
            <tbody id="tdScheduleDetail">
            <tr>
                <th>출입문 스케쥴 명</th>
                <td>
                    <input type="text" id="schName" name="schName" maxlength="50" size="50" value="16동 현관 출입문 남" class="w_600px input_com" disabled>
                </td>
            </tr>
            <tr>
                <th>사용</th>
                <td>
                    <select id="schUseYn" name="schUseYn" class="form-control w_600px" style="padding-left:10px;" disabled>
                        <option value="" name="selected">선택</option>
                        <option value="yes" selected>Y</option>
                        <option value="no">N</option>
                    </select>
                </td>
            </tr>
            <tr>
                <th>출입문 그룹</th>
                <td style="display: flex;">
                    <textarea id="gateGroup" name="gateGroup" rows="10" cols="33"
                              style="border-color: #ccc; border-radius: 2px; width: 95%; min-width: 95%;
                              font-size: 14px; line-height: 1.5; padding: 2px 10px;" disabled>출입문 그룹 A</textarea>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</form>

<div class="right_btn mt_20">
    <button class="btn_middle color_basic" onClick="fnList();">목록</button>
    <button class="btn_middle ml_5 color_basic" onClick="fnEdit();">수정</button>
    <button class="btn_middle ml_5 color_basic" onClick="fnDelete();">삭제</button>
    <button class="btn_middle ml_5 color_basic" onClick="openPopup('addByDayPopup');">요일 별 스케쥴 등록</button>
</div>

<%--  요일 별 스케쥴 등록 modal  --%>
<div id="addByDayPopup" class="example_content" style="display: none;">
    <div class="popup_box box_w3">
        <%--  검색 박스 --%>
        <div class="search_box mb_20">
            <div class="search_in">
                <div class="comm_search mr_10">
                    <label for="gateSchNm" class="mr_10">출입문 스케쥴 명</label>
                    <input type="text" class="w_600px input_com" id="gateSchNm" name="gateSchNm" value="" placeholder="출입문 스케쥴 명" maxlength="30" disabled>
                </div>
            </div>
        </div>
        <%--  end of 검색 박스 --%>

        <%--  왼쪽 box  --%>
        <div class="mt_20" style="width:73%;">
            <div class="com_box" style="border:1px solid black;">
                <table class="tb_list" id="tb_Schedule" style="height:750px;">
                    <colgroup>
                    <c:forEach var="i" begin="0" end="24" varStatus="status">
                        <col style="width:3%">
                    </c:forEach>
                    </colgroup>
                    <thead>
                        <tr>
                            <th>구분</th>
                        <c:forEach var="i" begin="0" end="23" varStatus="status">
                            <fmt:formatNumber var="no" minIntegerDigits="2" value="${status.index}" type="number"/>
                            <th>${no}</th>
                        </c:forEach>
                        </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${days}" var="day" varStatus="status">
                        <c:set var="day_eng" value="${days_eng[status.index]}"></c:set>
                        <tr>
                            <td class="col_days" style="background-color:#EAECF0 !important;">${day}</td>
                            <c:forEach var="j" begin="0" end="23" varStatus="status">
                                <fmt:formatNumber var="no" minIntegerDigits="2" value="${status.index}" type="number"/>
                                <td>
                                    <div name="timeline" class="timeline_${day_eng}${no}" style="height:50%;display:flex;flex-wrap:wrap;">
                                        <c:forEach var="min" begin="0" end="59" varStatus="status">
                                            <fmt:formatNumber var="min" minIntegerDigits="2" value="${status.index}" type="number"/>
                                            <div name="timeline" class="timeline_${day_eng}${no}_${min}" style="height:100%;flex-basis:1.666%;"></div>
                                        </c:forEach>
                                    </div>
                                </td>
                            </c:forEach>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
        <%--  end of 왼쪽 box  --%>

        <%--  오른쪽 box  --%>
        <div class="mt_20 mr_10" style="width:26%;">
            <%--  테이블  --%>
            <div class="com_box">
                <table id="tb_SchTimepick" class="tb_list" style="height:750px;">
                    <colgroup>
                        <c:forEach var="i" begin="0" end="2" varStatus="status">
                            <col style="width:33%">
                        </c:forEach>
                    </colgroup>
                    <thead>
                    <tr>
                        <th colspan="3" style="color: transparent !important; background-color: transparent;"> -</th>
                    </tr>
                    </thead>
                    <tbody id="tdTimePick">
                    <form id="formSchedule" name="formSchedule">
                    <c:forEach var="day" items="${days_eng}" varStatus="status">
                        <tr>
                            <c:forEach begin="1" end="3" varStatus="status">
                                <fmt:formatNumber var="no" value="${status.index}" type="number"/>
                                <td>
                                    <input type="time" id="${day}_${no}_start" name="${day}_${no}_start" class="start" value="" min="00:00:00" max="23:59:59" step="1"><br>~
                                    <input type="time" id="${day}_${no}_end" name="${day}_${no}_end" class="end" value="" min="00:00:00" max="23:59:59" step="1" >
                                </td>
                            </c:forEach>
                        </tr>
                    </c:forEach>
                    </form>
                    </tbody>
                </table>
            </div>
        </div>
        <%--  end of 오른쪽 box  --%>

        <div class="c_btnbox center mt_30">
            <div style="display: inline-block;">
                <button type="button" class="comm_btn mr_20" onclick="saveSchedule();">확인</button>
                <button type="button" class="comm_btn" onclick="closePopup('addByDayPopup');">취소</button>
            </div>
        </div>
    </div>
</div>
<%--  end of 권한그룹 선택 modal  --%>

