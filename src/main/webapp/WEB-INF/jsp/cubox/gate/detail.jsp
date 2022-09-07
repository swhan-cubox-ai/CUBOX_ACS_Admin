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
                let startId = this.id.replace("end", "start");
                let day = this.id.split("_")[0];
                let schNum = this.id.split("_")[1];
                let ifEnd = this.id.split("_")[2] == "end";


                // console.log("this val= " + $(this).val()); //00:01:00
                // console.log("this id= " + this.id); //tue_2_end

                console.log("시작 시간 val = " + $("#" + startId).val());

                if (ifEnd) {  // 끝나는 시간 선택 시
                    if ($("#" + startId).val() != "") { // 시작시간 값 있을 경우
                        // color 반영
                        let start_hour = $("#" + startId).val().split(":")[0];      // 시작시간
                        let end_hour = el.val().split(":")[0];                      // 종료시간

                        if (start_hour < end_hour) { // 시작시간이 종료시간보다 작을 때,
                            if (ifValid(start_hour, end_hour, day)) {
                                console.log("색칠하기");
                                colorSchedule(start_hour, end_hour, day, schNum);

                            } else {
                                console.log("색칠안하기");
                                alert("중복된 시간대가 존재합니다.");
                                initTimepicker(startId, el);
                            }

                        } else {
                            alert("종료시간이 시작시간보다 빠릅니다. 다시 선택해주세요.");
                            initTimepicker(startId, el);
                            return;
                        }

                    } else { // 시작시간 값 없을 경우
                        alert("시작 시간을 먼저 선택해주세요.");
                        initTimepicker(startId, el);
                        return;
                    }

                } else { // 시작시간 선택 시

                }
            }
        });

    });

    // time선택 초기화
    function initTimepicker(start, end) {
        $("#" + start).val("");
        end.val("");
    }

    // 이미 색칠되어 있는지 여부확인
    function ifValid(start, end, day) {
        let result = true;
        for (let i = start; i < end; i++) {
            let divToColor = $(".timeline_" + day + ("00" + i).slice(-2));

            // 이미 색칠되어 있는 경우, 중복 시간대로 인식
            if (divToColor.hasClass("colored")) {
                result = false;
                break;
            }
        }
        return result;
    }

    // 해당 범위 색칠
    function colorSchedule(start, end, day, schNum) {
        for (let i = start; i < end; i++) {
            let divToColor = $(".timeline_" + day + ("00" + i).slice(-2)); // 색칠할 div
            divToColor.addClass("colored");
            divToColor.addClass(day + "_" + schNum); // mon_2
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

    function scheduleLoad(self) {
        //
        // //
        // // $('input.form-control').timepicker({
        // //     timeFormat: 'HH:mm:ss p',
        // //     startTime: '0',
        // //     maxTime: '23:59:59',
        // //     // defaultTime: '0', // 나중에 옵션 off
        // //     interval: 30,
        // //     dynamic: false,
        // //     change: function(time) {
        //         let el = $(this);
        //         let startId = el.attr("id").replace("end", "start");
        //         let day = el.attr("id").split("_")[0];
        //         let ifEnd = el.attr("id").split("_")[2] == "end";
        //         console.log(el);
        //         // console.log(el.val()); // 12:00:00 AM
        //         // console.log(time); // Sun Dec 31 1899 01:30:00 GMT+0827 (한국 표준시)
        //         // console.log(time);
        //
        //         // if (day == "mon") {
        //         //     console.log("월");
        //         // } else if (day == "tue") {
        //         //     console.log("화");
        //         // } else if (day == "wed") {
        //         //     console.log("수");
        //         // } else if (day == "thu") {
        //         //     console.log("목");
        //         // } else if (day == "fri") {
        //         //     console.log("금");
        //         // } else if (day == "sat") {
        //         //     console.log("토");
        //         // } else if (day == "sun") {
        //         //     console.log("일");
        //         // }
        //
        //         console.log("시작 시간 val = " + $("#" + startId).val());
        //
        //         if (ifEnd) {  // 끝나는 시간 선택 시
        //             if ($("#" + startId).val() != "") { // 시작시간 값 있을 경우
        //                 // color 반영
        //                 let start_hour = $("#" + startId).val().split(":")[0]; // 시작시간
        //                 let end_hour = el.val().split(":")[0]; // 종료시간
        //                 // console.log(start_hour);
        //                 // console.log(end_hour);
        //
        //                 if (start_hour < end_hour) { // 시작시간이 종료시간보다 작을 때,
        //
        //                     for (let i = start_hour; i < end_hour; i++) { // 해당 범위 색칠
        //                         let divToColor = $(".timeline_" + day + ("00" + i).slice(-2)); // 색칠할 div
        //
        //                         if (divToColor.hasClass("colored")) { // 이미 색칠되어 있는 경우, 중복 시간대로 인식
        //                             alert("중복된 시간대가 존재합니다.");
        //                             // initialize timepicker
        //                             $("#" + startId).val("");
        //                             el.val("");
        //                             return;
        //
        //                         } else {
        //                             divToColor.addClass("colored");
        //                         }
        //
        //                     }
        //
        //                 } else {
        //                     alert("중복된 스케쥴이 존재합니다.");
        //                     return;
        //                 }
        //
        //             } else { // 시작시간 값 없을 경우
        //                 alert("시작 시간을 먼저 선택해주세요.");
        //                 // initialize timepicker
        //                 $("#" + startId).val("");
        //                 el.val("");
        //                 return;
        //             }
        //
        //         } else { // 시작시간 선택 시
        //
        //         }
        //
        //         //중복된 시간대가 있는 경우 팝업 출력
        // //     }
        // // });

    }

    // popup open (공통)
    function openPopup(popupNm) {
        $("#" + popupNm).PopupWindow("open");
        // scheduleLoad();
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
                                <td><div name="timeline" class="timeline_${day_eng}${no}" style="height:50%;"></div></td>
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
                                    <input type="time" id="${day}_${no}_start" name="${day}_${no}_start" value="" min="00:00:00" max="23:59:59" step="1">
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

