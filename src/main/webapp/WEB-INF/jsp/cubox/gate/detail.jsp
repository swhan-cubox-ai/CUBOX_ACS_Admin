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

<script src="/js/timepicker/jquery.timepicker.min.js"></script>
<link rel="stylesheet" href="/js/timepicker/jquery.timepicker.min.css" />

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
                // let start = "";
                // let end = "";

                // console.log("this val= " + $(this).val()); //00:01:00
                // console.log("this id= " + this.id); //tue_2_end

                // console.log("시작 시간 val = " + $("#" + startId).val());
                // console.log("종료 시간 val = " + el.val());

                // let start_hour = $("#" + startId).val().split(":")[0];      // 시작시간
                // let end_hour = el.val().split(":")[0];

                if (ifEnd) {
                    console.log("ifEnd");

                    let startId = this.id.replace("end", "start");
                    // start = $("#" + startId).val().split(":")[0];      // 시작시간
                    // end = el.val().split(":")[0];

                    start.hour = $("#" + startId).val().split(":")[0];
                    start.min = $("#" + startId).val().split(":")[1];
                    start.sec = $("#" + startId).val().split(":")[2];
                    end.hour = el.val().split(":")[0];
                    end.min = el.val().split(":")[1];
                    end.sec = el.val().split(":")[2];

                    if (timeValid(this.id, start, end, startId, day, schNum)) {
                        if (ifValid(this.id, start, end, day, schNum, startId)) {
                            console.log("색칠하기_ifEnd");
                            colorSchedule(start, end, day, schNum);
                        } else {
                            console.log("색칠안하기_ifEnd");
                            alert("중복된 시간대가 존재합니다.");
                            initTimepicker(startId, this.id);
                            return;
                        }
                    }

                } else {  // 수정
                    console.log("ifStart");

                    let endId = this.id.replace("start", "end");

                    // start = el.val().split(":")[0];
                    // end = $("#" + endId).val().split(":")[0];

                    start.hour = el.val().split(":")[0];
                    start.min = el.val().split(":")[1];
                    start.sec = el.val().split(":")[2];
                    end.hour = $("#" + endId).val().split(":")[0];
                    end.min = $("#" + endId).val().split(":")[1];
                    end.sec = $("#" + endId).val().split(":")[2];

                    // if (start.hour != "" || start.min != "") {
                    //     console.log("색칠하기2");
                    //     colorSchedule(start, end, day, schNum);
                    // }

                    if (ifValid(endId, start, end, day, schNum, this.id)) {
                        console.log("색칠하기_ifStart");
                        colorSchedule(start, end, day, schNum);
                    } else {
                        console.log("색칠안하기_ifStart");
                        alert("중복된 시간대가 존재합니다.");
                        initTimepicker(this.id, endId);
                        return;
                    }
                }


                // if ((start_hour != "" && end_hour != "") && start_hour < end_hour) { // 시작시간이 종료시간보다 작을 때,
                //
                //     if (ifValid(start_hour, end_hour, day)) {
                //         console.log("색칠하기");
                //     } else {
                //         console.log("색칠안하기");
                //         // alert("중복된 시간대가 존재합니다.");
                //         // initTimepicker(startId, el);
                //         // return;
                //     }
                //
                // } else { // 시작시간 값 없을 경우
                //
                //     alert("시작 시간을 먼저 선택해주세요.");
                //     initTimepicker(startId, el);
                //     return;
                //
                // }


                // if (ifEnd) {  // 끝나는 시간 선택 시
                //     if ($("#" + startId).val() != "") { // 시작시간 값 있을 경우
                //         // color 반영
                //         let start_hour = $("#" + startId).val().split(":")[0];      // 시작시간
                //         let end_hour = el.val().split(":")[0];                      // 종료시간
                //
                //         if (start_hour < end_hour) { // 시작시간이 종료시간보다 작을 때,
                //             if (ifValid(start_hour, end_hour, day)) {
                //                 console.log("색칠하기");
                //                 colorSchedule(start_hour, end_hour, day, schNum);
                //
                //             } else {
                //                 console.log("색칠안하기");
                //                 alert("중복된 시간대가 존재합니다.");
                //                 initTimepicker(startId, el);
                //                 return;
                //             }
                //
                //         } else {
                //             alert("종료시간이 시작시간보다 빠릅니다. 다시 선택해주세요.");
                //             initTimepicker(startId, el);
                //             return;
                //         }
                //
                //     } else { // 시작시간 값 없을 경우
                //         alert("시작 시간을 먼저 선택해주세요.");
                //         initTimepicker(startId, el);
                //         return;
                //     }
                //
                // } else { // 시작시간 선택 시
                //
                // }
            }
        });

    });

    // time선택 초기화
    function initTimepicker(start, end) {
        console.log("initTimepicker");
        // console.log("start = " + start);
        // console.log("end = " + end);
        $("#" + start).val("");
        $("#" + end).val("");
        // end.val("");
    }

    // 이미 색칠되어 있는지 여부확인
    function ifValid(endId, start, end, day, schNum, startId) {
        console.log("ifValid");

        let result = true;

        for (let i = start.hour; i <= end.hour; i++) {

            if (i == start.hour) { // 시작 hour 칸 (분단위 coloring)
                for (let j = start.min; j < 60; j++) {
                    let divToColor = $(".timeline_" + day + ("00" + i).slice(-2) + "_" + j); // 색칠할 div
                    if ((divToColor.hasClass("colored")) && (!divToColor.hasClass(day + "_" + schNum))) {
                        console.log("1. 색칠되어있고 같은 스케쥴 아님");
                        result = false;
                        break;
                    }
                }
            } else if (i == end.hour) { // 종료 hour 칸 (분단위 coloring)
                for (let j = 0; j < end.min; j++) {
                    let divToColor = $(".timeline_" + day + ("00" + i).slice(-2) + "_" + j); // 색칠할 div
                    if ((divToColor.hasClass("colored")) && (!divToColor.hasClass(day + "_" + schNum))) {
                        console.log("2. 색칠되어있고 같은 스케쥴 아님");
                        result = false;
                        break;
                    }
                }
            } else {
                for (let j = 0; j < 60; j++) { // (분단위 coloring)
                    let divToColor = $(".timeline_" + day + ("00" + i).slice(-2) + "_" + j); // 색칠할 div
                    if ((divToColor.hasClass("colored")) && (!divToColor.hasClass(day + "_" + schNum))) {
                        console.log("3. 색칠되어있고 같은 스케쥴 아님");
                        result = false;
                        break;
                    }
                }
            }
        }

            // let divToColor = $(".timeline_" + day + ("00" + i).slice(-2));
            // console.log("divToColor = " +  ".timeline_" + day + ("00" + i).slice(-2));

            // // 이미 색칠되어 있는 경우
            // if (divToColor.hasClass("colored")) {
            //     // if (divToColor.hasClass(day + "_" + schNum)) { // 해당 스케쥴인 경우
            //     //     console.log("변경을 원함");
            //     //     // result = true;
            //     //     // 수정로직
            //     //     // result = timeValid(endId, start, end, startId, day, schNum); // true 반환하면 시간 체크 완료, false 반환하면 alert창 띄우고 init완료
            //     // } else { // 다른 스케쥴인 경우 중복으로 간주
            //     //     console.log("다른 스케쥴");
            //     //     result = false;
            //     //     break;
            //     // }
            //     console.log(divToColor.hasClass(day + "_" + schNum));
            //     if (!divToColor.hasClass(day + "_" + schNum)) { // 다른 스케쥴인 경우 중복으로 간주
            //         console.log("중복 스케쥴");
            //         result = false;
            //         break;
            //     }
            // }
        // }

        console.log("ifValid 결과: " + result);
        return result;
    }

    // 시간 유효성 체크
    function timeValid(endId, start, end, startId, day, schNum) {
        console.log("timeValid");

        let result = true;
        // if (start.hour == "") {
        if ($("#" + startId).val() == "") {
            alert("시작 시간을 먼저 선택해주세요.");
            initTimepicker(startId, endId);
            result = false;
        // } else if (start.hour != "" && (start.hour > end.hour)) { // 시작 시간이 종료시간보다 클 때
        } else if ($("#" + startId).val() != "" && $("#" + startId).val() >= $("#" + endId).val()) { // 시작 시간이 종료시간보다 클 때
            alert("시작시간은 종료시간보다 빨라야합니다.");

            $("#" + startId).val(start.hour + ":" + start.min + ":" + start.sec); // 수정 시
            $("#" + endId).val(end.hour + ":" + end.min + ":" + end.sec);
            initTimepicker(startId, endId);  // 추후에 스케쥴 리로드로 대체 - 최초 등록 시
            result = false;
        }
        console.log("timeValid 결과: " + result);
        return result;
    }

    // 해당 범위 색칠
    function colorSchedule(start, end, day, schNum) {
        console.log("colorschedule");
        // console.log(start);
        // console.log(end);

        initColor(day, schNum);

        // for (let i = start.hour; i < end.hour; i++) {  // hour
        //     // let divToColor = $(".timeline_" + day + ("00" + i).slice(-2)); // 색칠할 div
        //     let divToColor = $(".timeline_" + day + ("00" + i).slice(-2)); // 색칠할 div
        //     divToColor.addClass("colored");
        //     divToColor.addClass(day + "_" + schNum); // mon_2
        // }
        console.log("colorSchedule2");
        for (let i = start.hour; i <= end.hour; i++) {  // hour
            // let divToColor = $(".timeline_" + day + ("00" + i).slice(-2)); // 색칠할 div

            if (i == start.hour) { // 시작 hour 칸 (분단위 coloring)
                console.log("i === start.hour  " + i);
                for (let j = start.min; j < 60; j++) {
                    let divToColor = $(".timeline_" + day + ("00" + i).slice(-2) + "_" + j); // 색칠할 div
                    divToColor.addClass("colored");
                    divToColor.addClass(day + "_" + schNum); // mon_2
                }
            } else if (i == end.hour) { // 종료 hour 칸 (분단위 coloring)
                console.log("i === end.hour  " + i);
                for (let j = 0; j < end.min; j++) {
                    let divToColor = $(".timeline_" + day + ("00" + i).slice(-2) + "_" + j); // 색칠할 div
                    divToColor.addClass("colored");
                    divToColor.addClass(day + "_" + schNum); // mon_2
                }
            } else { // (시단위 coloring)
                // $(".timeline_" + day + ("00" + i).slice(-2)).addClass("colored");
                // $(".timeline_" + day + ("00" + i).slice(-2)).addClass(day + "_" + schNum);
                for (let j = 0; j < 60; j++) { // (분단위 coloring)
                    let divToColor = $(".timeline_" + day + ("00" + i).slice(-2) + "_" + j); // 색칠할 div
                    divToColor.addClass("colored");
                    divToColor.addClass(day + "_" + schNum); // mon_2
                }
            }
        }
    }

    // 색칠 초기화
    function initColor(day, schNum) {
        console.log("initColor");

        let editSch = $("." + day + "_" + schNum);
        console.log("editSch length = " + editSch.length);

        if (editSch.length != 0) {
            for (let i in editSch) {
                editSch.eq(i).removeClass("colored");
                editSch.eq(i).removeClass(day + "_" + schNum);
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
        location.href = "/gate/schedule_delete.do";

        // $.ajax({
        //     type : "post",
        //     url  : "/gate/schedule_delete.do",
        //     data : {
        //        "id" = id
        //     },
        //     dataType :'json',
        //     success  : function(data, status) {
        //         if(data.result == "Y"){
        //             location.href = "/gate/schedule.do";
        //         } else {
        //             alert("삭제 중 오류가 발생하였습니다.");
        //         }
        //     }
        // });
    }

    // popup open (공통)
    function openPopup(popupNm) {
        $("#" + popupNm).PopupWindow("open");
    }

    // popup close (공통)
    function closePopup(popupNm) {
        $("#" + popupNm).PopupWindow("close");

        if (popupNm == "addByDayPopup") {
            // 단말기 선택 팝업창 초기화
            // $("input[name='checkOne']:checked").attr("checked", false);
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
<div id="addByDayPopup" class="example_content">
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
                                            <div name="timeline" class="timeline_${day_eng}${no}_${min}" style="height:100%;flex-basis:1.666%;"></div>
                                        </c:forEach>
<%--                                        <div name="timeline" class="timeline_${day_eng}${no}" style="height:100%;flex-basis:16.6%;"></div>--%>
<%--                                        <div name="timeline" class="timeline_${day_eng}${no}" style="height:100%;flex-basis:16.6%;"></div>--%>
<%--                                        <div name="timeline" class="timeline_${day_eng}${no}" style="height:100%;flex-basis:16.6%;"></div>--%>
<%--                                        <div name="timeline" class="timeline_${day_eng}${no}" style="height:100%;flex-basis:16.6%;"></div>--%>
<%--                                        <div name="timeline" class="timeline_${day_eng}${no}" style="height:100%;flex-basis:16.6%;"></div>--%>
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
                    <c:forEach var="day" items="${days_eng}" varStatus="status">
                        <tr>
                            <c:forEach begin="1" end="3" varStatus="status">
                                <fmt:formatNumber var="no" value="${status.index}" type="number"/>
<%--                                <td><input type="text" id="${day}_${no}_start" name="${day}_${no}_start" class="form-control"> ~--%>
<%--                                <input type="text" id="${day}_${no}_end" name="${day}_${no}_end" class="form-control"></td>--%>

<%--                                <form>--%>
                                <td>
                                    <input type="time" id="${day}_${no}_start" name="${day}_${no}_start" value="" min="00:00:00" max="23:59:59" step="1"><br>~
                                    <input type="time" id="${day}_${no}_end" name="${day}_${no}_end" value="" min="00:00:00" max="23:59:59" step="1" >
<%--                                    <input type="submit" value="-" style="display: none;">--%>
                                </td>
<%--                                </form>--%>
                            </c:forEach>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
        <%--  end of 오른쪽 box  --%>

        <div class="c_btnbox center mt_30">
            <div style="display: inline-block;">
                <button type="button" class="comm_btn mr_20" onclick="authSave();">확인</button>
                <button type="button" class="comm_btn" onclick="closePopup('addByDayPopup');">취소</button>
            </div>
        </div>
    </div>
</div>
<%--  end of 권한그룹 선택 modal  --%>

