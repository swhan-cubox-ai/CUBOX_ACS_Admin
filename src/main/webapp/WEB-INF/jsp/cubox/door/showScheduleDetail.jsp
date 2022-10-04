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
        width: 125px;
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
    let tmp = [
        {id : "mon_1", weekday : "mon", beg_tm : "13:40:27", end_tm : "19:40:30"},
        {id : "mon_2", weekday : "mon", beg_tm : "09:00:00", end_tm : "10:00:30"},
        {id : "mon_3", weekday : "mon", beg_tm : "", end_tm : ""},
        {id : "tue_1", weekday : "tue", beg_tm : "13:00:00", end_tm : "15:00:00"},
        {id : "tue_2", weekday : "tue", beg_tm : "", end_tm : ""},
        {id : "tue_3", weekday : "tue", beg_tm : "19:40:27", end_tm : "22:00:20"},
        {id : "wed_1", weekday : "wed", beg_tm : "01:50:00", end_tm : "08:20:30"},
        {id : "wed_2", weekday : "wed", beg_tm : "11:40:00", end_tm : "12:00:00"},
        {id : "wed_3", weekday : "wed", beg_tm : "13:40:50", end_tm : "23:59:59"},
        {id : "thu_1", weekday : "thu", beg_tm : "", end_tm : ""},
        {id : "thu_2", weekday : "thu", beg_tm : "", end_tm : ""},
        {id : "thu_3", weekday : "thu", beg_tm : "", end_tm : ""},
        {id : "fri_1", weekday : "fri", beg_tm : "10:30:00", end_tm : "19:30:00"},
        {id : "fri_2", weekday : "fri", beg_tm : "", end_tm : ""},
        {id : "fri_3", weekday : "fri", beg_tm : "", end_tm : ""},
        {id : "sat_1", weekday : "sat", beg_tm : "", end_tm : ""},
        {id : "sat_2", weekday : "sat", beg_tm : "07:00:00", end_tm : "16:30:30"},
        {id : "sat_3", weekday : "sat", beg_tm : "17:40:20", end_tm : "20:20:00"},
        {id : "sun_1", weekday : "sun", beg_tm : "", end_tm : ""},
        {id : "sun_2", weekday : "sun", beg_tm : "", end_tm : ""},
        {id : "sun_3", weekday : "sun", beg_tm : "01:00:00", end_tm : "23:59:59"}
    ];

    $(function() {
        $(".title_tx").html("출입문 스케쥴 - 상세");
        modalPopup("addByDayPopup", "요일 별 스케쥴 등록", 1590, 915);
        // modalPopup("addByDayPopup", "요일 별 스케쥴 등록", 2000, 1100);

        $("input[type=time]").on({
            change: function() {

                let el = $(this);
                let day = this.id.split("_")[0];
                let schNum = this.id.split("_")[1];
                let ifEnd = this.id.split("_")[2] == "end";
                let startId = "";
                let endId = "";
                let start = {hour : "", min : "", sec: ""};
                let end = {hour : "", min : "", sec: ""};

                if (ifEnd) {
                    console.log("ifEnd");

                    startId = this.id.replace("end", "start");
                    endId = this.id;

                    start.hour = $("#" + startId).val().split(":")[0];
                    start.min = $("#" + startId).val().split(":")[1];
                    start.sec = $("#" + startId).val().split(":")[2];
                    end.hour = el.val().split(":")[0];
                    end.min = el.val().split(":")[1];
                    end.sec = el.val().split(":")[2];

                } else {
                    console.log("ifStart");

                    startId = this.id;
                    endId = this.id.replace("start", "end");

                    start.hour = el.val().split(":")[0];
                    start.min = el.val().split(":")[1];
                    start.sec = el.val().split(":")[2];
                    end.hour = $("#" + endId).val().split(":")[0];
                    end.min = $("#" + endId).val().split(":")[1];
                    end.sec = $("#" + endId).val().split(":")[2];
                }

                if (timeValid(startId, endId, start, end, day, schNum)) {
                    validCheck(startId, endId, start, end, day, schNum);
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
            alert("중복된 스케쥴이 존재합니다. 다시 선택해주세요.");
        }
    }

    // 이미 색칠되어 있는지 여부확인
    function ifValid(startId, endId, start, end, day, schNum) {
        console.log("ifValid");
        let result = true; // 다른 스케쥴과 겹치는지 여부

        if (start.hour != end.hour) { // 시작시간과 종료시간이 다른 hour 칸에 있을 때
            for (let i = Number(start.hour); i <= Number(end.hour); i++) {

                if (i == Number(start.hour)) { // 시작 hour 칸
                    for (let j = Number(start.min); j < 60; j++) {
                        let divToColor = $(".timeline_" + day + ("00" + i).slice(-2) + "_" + ("00" + j).slice(-2)); // 색칠할 div
                        if ((divToColor.hasClass("colored")) && (!divToColor.hasClass(day + "_" + schNum))) {
                            console.log("1. 색칠되어있고 같은 스케쥴 아님"); ///// 걸리는 애가 시작
                            // 칠하고자 하는 시작점과 지금 확인중인 시작점 비교
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
        let isFirst = ($("." + day + "_" + schNum).length > 0) ? false : true;  // 최초등록?

        if (isFirst) {
            console.log("최초 등록");
            let sameDaySch = $("." + day + "_timepick");
            let schTime = [];
            let startVal = $("#" + startId).val();
            let endVal = $("#" + endId).val();

            // 다른 스케쥴들 schTime에 담기
            for (let i = 0; i < sameDaySch.length; i++) {
                let thisId = sameDaySch.eq(i).attr("id");
                if (thisId.split("_")[1] != schNum) {
                    console.log("=다른 스케쥴=");
                    console.log($("#" + thisId).val());

                    let isStart = sameDaySch.eq(i).hasClass("start");
                    if ((isStart && $("#" + thisId.replace("start", "end")).val() != "") || (!isStart && $("#" + thisId.replace("end", "start")).val())) {
                        schTime.push($("#" + thisId).val());
                    }
                }
            }
            console.log(schTime);

            if ((startVal <= schTime[0] && endVal >= schTime[1]) || (startVal <= schTime[2] && endVal >= schTime[3])) {
                console.log("둘다 오버된 시간");
                initTimepicker(startId, endId);
            }

            if ((startVal >= schTime[0] && startVal <= schTime[1]) || (startVal >= schTime[2] && startVal <= schTime[3])) {
                console.log("시작시간 겹침");
                $("#" + startId).val("");
            }

            if ((endVal >= schTime[0] && endVal <= schTime[1]) || (endVal >= schTime[2] && endVal <= schTime[3])) {
                console.log("종료시간 겹침");
                $("#" + endId).val("");
            }

        } else {
            console.log("이미 같은 시간대에 다른 스케쥴 존재");
            let tmpStart = $("div." + day + "_" + schNum).first().val();
            let tmpEnd = $("div." + day + "_" + schNum).last().val();
            $("#" + startId).val(tmpStart.hour + ":" + tmpStart.min + ":" + tmpStart.sec); // 수정 시
            $("#" + endId).val(tmpEnd.hour + ":" + tmpEnd.min + ":" + tmpEnd.sec);
        }

    }

    // 시간 유효성 체크
    function timeValid(startId, endId, start, end, day, schNum) {
        let result = true;
        let startVal = $("#" + startId).val();
        let endVal = $("#" + endId).val();

        if (startVal == "" && endVal != "") { // 시작 시간 입력 안했을 때
            alert("시작 시간을 먼저 선택해주세요.");
            initTimepicker(startId, endId);
            result = false;

        } else if ((startVal != "" && endVal !== "") && startVal >= endVal) { // 시작 시간이 종료시간보다 클 때

            if (!($("." + day + "_" + schNum).length > 0)) {    // 최초등록
                console.log("timevalid 최초등록");
                alert("종료시간이 시작시간보다 빠릅니다.");
                $("#" + endId).val("");
            } else {                                            // 수정 시
                console.log("timevalid 최초등록 아님");
                alert("종료시간이 시작시간보다 빠릅니다.");
                let tmpStart = $("div." + day + "_" + schNum).first().val();
                let tmpEnd = $("div." + day + "_" + schNum).last().val();
                $("#" + startId).val(tmpStart.hour + ":" + tmpStart.min + ":" + tmpStart.sec);
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
        location.href = "/door/schedule.do";
    }

    // 수정 버튼
    function fnEdit() {
        // location.href = "/door/schedule_add.do?mode=edit";
        f = document.detailForm;
        f.action = "/door/schedule_add.do";
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
        location.href = "/door/deleteSchedule.do";

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
        const days = ["mon", "tue", "wed", "thu", "fri", "sat", "sun"];
        const schNums = ["1", "2", "3"];
        let data = [];
        let schSet = {};

        clearPickers();
        closePopup("addByDayPopup");

        $.each($("input[name=timepicker]"), function (i, pick) {
            let pId = $(pick).attr("id");
            let weekday = pId.split("_")[0]; // 요일
            let schNum = pId.split("_")[1]; // 스케쥴번호

            $.each(days, function (j, day) {
                $.each(schNums, function (k, num) {
                    if (weekday === day && schNum === num) { // 같은 요일, 같은 번호
                        schSet.id = weekday + "_" + schNum;
                        schSet.weekday = weekday;
                        if ($(pick).hasClass("start")) {
                            schSet.beg_tm = $(pick).val();
                        } else if ($(pick).hasClass("end")) {
                            schSet.end_tm = $(pick).val();
                            data.push(schSet);
                            schSet = {};
                        }
                    }
                });
            });
        });
        console.log("data");
        console.log(data);
    }

    // start 또는 end 시간만 있는 스케쥴 clear
    function clearPickers() {
        let timeList = $("input[type=time]");
        for (let i = 0; i <= timeList.length; i++) {
            let isStart = timeList.eq(i).hasClass("start");
            if (isStart) {
                let endId = timeList.eq(i).attr("id").replace("start", "end");
                let endPick = $("#" + endId).val();

                if (timeList.eq(i).val() != "" && endPick == "") { // start 값 있음, end 값 없음
                    console.log("startVal 있음, endVal 없음");
                    timeList.eq(i).val("");
                } else if (timeList.eq(i).val() == "" && endPick != "") { // start 값 없음, end 값 있음
                    console.log("startVal 없음, endVal 있음");
                    $("#" + endId).val("");
                }
            }
        }
    }

    // popup open (공통)
    function openPopup(popupNm) {
        $("#" + popupNm).PopupWindow("open");

        if (popupNm === "addByDayPopup") {

            // 스케쥴 뿌려주기
            $.each(tmp, function(i, sch) {
                let beg_tm = sch.beg_tm.split(":");
                let end_tm = sch.end_tm.split(":");
                let start = {hour : beg_tm[0], min : beg_tm[1], sec: beg_tm[2]};
                let end = {hour : end_tm[0], min : end_tm[1], sec: end_tm[2]};
                let day = sch.weekday;
                let schNum = sch.id.split("_")[1];
                $("#" + sch.id + "_start").val(sch.beg_tm);
                $("#" + sch.id + "_end").val(sch.end_tm);

                colorSchedule(start, end, day, schNum);
            });
        }
    }

    // popup close (공통)
    function closePopup(popupNm) {
        $("#" + popupNm).PopupWindow("close");
        clearPickers();
    }

</script>

<%--<div id="timepicker-wrapper"></div>--%>
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
                    <textarea id="gateGroup" name="gateGroup" rows="10" cols="33" class="w_600px"
                              style="border-color: #ccc; border-radius: 2px;
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
    <div class="popup_box box_w3" style="margin-top:0px; padding:20px;">
        <%--  검색 박스 --%>
        <div class="search_box">
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
                <table class="tb_list" id="tb_Schedule" style="height:665px;">
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
        <div class="mt_20" style="width:26%;">
            <%--  테이블  --%>
            <div class="com_box">
                <table id="tb_SchTimepick" class="tb_list" style="height:665px;">
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
                                    <input type="time" id="${day}_${no}_start" name="timepicker" class="start ${day}_timepick" value="" min="00:00:00" max="23:59:59" step="1"><br>~
                                    <input type="time" id="${day}_${no}_end" name="timepicker" class="end ${day}_timepick" value="" min="00:00:00" max="23:59:59" step="1" >
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

        <div class="c_btnbox center mt_20">
            <div style="display: inline-block;">
                <button type="button" class="comm_btn mr_20" onclick="saveSchedule();">확인</button>
                <button type="button" class="comm_btn" onclick="closePopup('addByDayPopup');">취소</button>
            </div>
        </div>
    </div>
</div>
<%--  end of 권한그룹 선택 modal  --%>

