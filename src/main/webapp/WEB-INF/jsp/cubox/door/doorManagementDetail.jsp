<%--
  Created by IntelliJ IDEA.
  User: USER
  Date: 2022-08-31
  Time: 오후 1:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:include page="/WEB-INF/jsp/cubox/common/checkPasswd.jsp" flush="false"/>

<style>
    .tb_write_p1 tbody th {
        text-align: center;
    }
    .tb_write_02 tbody tr {
        /*height: 70px;*/
        /*height: 51px;*/
        height: 57.5px;
    }
    .gateDetailList tr td {
        text-align: center;
    }
    .gateDetailList tr td select {
        float: none;
    }
    .gateDetailList tr td input,
    .gateDetailList tr td select,
    .gateDetailList tr td textarea {
        width: 100%;
    }
    .title_s {
        font-size: 20px;
    }
    #gateInfo {
        width: 100%;
        height: 690px;
        border: 1px solid black;
    }
    #treeDiv {
        width: 100%;
        height: 631px;
        border-bottom: 1px solid #ccc;
        padding: 10px 45px;
        padding: 10px 45px;
        overflow: auto;
    }
    #btnGatePick, #btnAuthPick {
        /*width: 100px;*/
        /*width: 20%;*/
        width: 100%;
        border: none;
    }
    .tb_list tr td {
        text-align: center;
    }
    thead {
        position: sticky;
        top: 0;
    }
    #tdAuthConf tr, #tdAuthTotal tr {
        height: 40px;
        text-align: center;
    }
    .disabled {
        pointer-events: none;
        background-color: lightgray;
    }
    .title_box {
        margin-top: 10px;
    }
</style>

<script type="text/javascript">

    let doorId;

    $(function () {
        $(".title_tx").html("출입문 관리");

        fnGetDoorListAjax();    //출입문 목록

        modalPopup("doorPickPopup", "단말기 선택", 910, 520);
        modalPopup("authPickPopup", "권한그룹 선택", 910, 550);

        fnAdd(); // 최초 등록 상태

        let pathArr = [];

        // 빌딩 선택 시,
        $("#dBuilding").change(function() {
            let val = $(this).val();

            // 초기화
            $(".dArea").css("display", "block");
            $("#dArea option[name=selected]").prop("selected", true);
            $("#dFloor option[name=selected]").prop("selected", true);

            $("#dArea option").each(function(i, option) { // 빌딩 id와 구역과 area의 bId 속성이 같지 않으면 option에서 제거
                if (val != $(option).attr("bId")) {
                    $(option).css("display", "none");
                }
            });
            // $("#pathBuilding").text($("#dBuilding option:checked").text());
            pathArr = [];
            pathArr[0] = $("#dBuilding option:checked").text();
            $("#doorPath").text(pathArr.join(" > "));

            // 구역 disabled 해제
            $("#dArea").prop("disabled", false);
        });

        // 구역 선택 시,
        $("#dArea").change(function() {
            let val = $(this).val();

            // 초기화
            $(".dFloor").css("display", "block");
            $("#dFloor option[name=selected]").prop("selected", true);

            $("#dFloor option").each(function(i, option) {
                if (val != $(option).attr("aId")) {
                    $(option).css("display", "none");
                }
            });
            // let txt = $("#doorPath").text() + " > " + $("#dArea option:checked").text();
            // $("#doorPath").text(txt);
            // $("#pathArea").text($("#dArea option:checked").text());
            pathArr.splice(2, 1);
            pathArr[1] = $("#dArea option:checked").text();
            $("#doorPath").text(pathArr.join(" > "));


            // 층 disabled 해제
            $("#dFloor").prop("disabled", false);
        });

        // 층 선택 시,
        $("#dFloor").change(function() {
            // let txt = $("#doorPath").text() + " > " + $("#dFloor option:checked").text();
            // $("#doorPath").text(txt);
            $("#pathFloor").text($("dFloor option:checked").text());
            pathArr[2] = $("#dFloor option:checked").text();
            $("#doorPath").text(pathArr.join(" > "));

        });

        // 단말기 선택 확인
        $('#doorPickConfirm').click(function () {
            let chkTerminal = $("input[name=checkOne]:checked").closest("tr").children();

            // TODO : 등록된 단말기 여부 확인
            //  alert("이미 등록된 단말기입니다.");

            // $("#terminalCd").attr("tId", $("input[name=checkOne]:checked").attr("id"));
            $("#terminalId").val($("input[name=checkOne]:checked").val());
            $("#terminalCd").val(chkTerminal.eq(1).html()); // 단말기 코드
            $("#mgmtNum").val(chkTerminal.eq(2).html());    // 단말기 관리번호
            closePopup('doorPickPopup');
        });

        // 권한그룹 추가
        $("#add_auth").click(function () {
            $("input[name=chkAuth]:checked").each(function (i) {
                var tag = "<tr>" + $(this).closest("tr").html() + "</tr>";
                tag = tag.replace("chkAuth", "chkAuthConf");
                $("#tdAuthConf").append(tag);
            });

            var ckd = $("input[name=chkAuth]:checked").length;
            for (var i = ckd - 1; i > -1; i--) {
                $("input[name=chkAuth]:checked").eq(i).closest("tr").remove();
            }

            totalCheck();
        });

        // 권한그룹 삭제
        $("#delete_auth").click(function () {
            $("input[name=chkAuthConf]:checked").each(function (i) {
                var tag = "<tr>" + $(this).closest("tr").html() + "</tr>";
                tag = tag.replace("chkAuthConf", "chkAuth");
                $("#tdAuthTotal").append(tag);
            });

            var ckd = $("input[name=chkAuthConf]:checked").length;
            for (var i = ckd - 1; i > -1; i--) {
                $("input[name=chkAuthConf]:checked").eq(i).closest("tr").remove();
            }

            userCheck();
        });

        $("#totalAuthCheckAll").click(function () {
            if ($("#totalAuthCheckAll").prop("checked")) {
                $("input[name=chkAuth]").prop("checked", true);
            } else {
                $("input[name=chkAuth]").prop("checked", false);
            }
        });

        $("#userAuthCheckAll").click(function () {
            if ($("#userAuthCheckAll").prop("checked")) {
                $("input[name=chkAuthConf]").prop("checked", true);
            } else {
                $("input[name=chkAuthConf]").prop("checked", false);
            }
        });

        // 단말기 검색 버튼 클릭시
        $("#btnSearchTerminal").click(function () {
            // param1 : 검색어, param2 : 출입문 미등록 체크박스 value값 Y or N
            let chk = "Y";
            fnGetTerminalListAjax($("#srchMachine").val(), chk);
        });

        // 단말기 검색 버튼 클릭시
        $("#btnSearchAuthGroup").click(function () {
            // param1 : 검색어, param2 : 출입문 미등록 체크박스 value값 Y or N
            fnGetAuthGroupListAjax($("#srchAuth").val());
        });

    });

    // 속성 값 초기화
    function initDetail() {
        $("#doorPath").text("");
        $("#doorNm").val("");
        $("option[name='selected']").prop("selected", true);
        $("#terminalCd").val("");
        $("#mgmtNum").val("");
        $("#doorGroup").val("");

        $("#terminalId").val("");
        $("#authGroupId").val("");
    }

    // 속성 보여주기
    function viewDetail() {
        $(".gateDetailList").css("display", "table-row-group");
        $("#btn_wrapper").css("display", "block");
    }

    // 속성 숨기기
    function hideDetail() {
        $(".gateDetailList").css("display", "none");
        $("#btn_wrapper").css("display", "none");
    }

    // 속성 뿌려주기
    function getGateDetail(dId) {
        doorId = dId // TODO : id 넘기기

        initDetail();
        fnCancelEdit();
        viewDetail();

        //출입문 정보
        $.ajax({
            type: "GET",
            url: "<c:url value='/door/detail.do' />",
            data: { doorId: doorId },
            dataType: "json",
            success: function (result) {
                // TODO : 알람그룹,권한그룹 가져오기
                console.log(result);
                let dInfo = result.doorInfo;
                // TODO: terminalId
                $("#doorId").val(dInfo.id);                                 // doorId
                $("#doorPath").text(dInfo.door_nm.replaceAll(" ", " > "));  // 경로
                $("#doorNm").val(dInfo.door_nm);                            // 출입문 명
                $("#dBuilding").val(dInfo.building_id);                     // 빌딩
                $("#dArea").val(dInfo.area_id);                             // 구역
                $("#dFloor").val(dInfo.floor_id);                           // 층
                $("#doorSchedule").val(dInfo.sch_id);                       // 스케쥴
                $("#terminalId").val(dInfo.terminal_id);                    // 단말기 id
                $("#terminalCd").val(dInfo.terminal_cd);                    // 단말기 코드
                $("#mgmtNum").val(dInfo.mgmt_num);                          // 단말기 관리번호
                $("#authGroupId").val(dInfo.auth_id);                       // 권한그룹 id
                $("#doorGroup").val(dInfo.auth_nm);            // 권한그룹
            }
        });
    }

    // 출입문 관리 - 취소
    function fnCancelEdit() {
        // [확인, 취소] --> [수정, 삭제] 버튼으로 변환
        $("#btnEdit").css("display", "inline-block");
        $("#btnDelete").css("display", "inline-block");
        $("#btnSave").css("display", "none");
        $("#btnCancel").css("display", "none");
        // $("#btnGatePick").css("display", "none");
        // $("#btnAuthPick").css("display", "none");
        $("#btnGatePick, #btnAuthPick").addClass("disabled");
        $("[name=doorEdit]").prop("disabled", true);
        $("[name=doorEditSelect]").prop("disabled", true);
    }

    // 출입문 관리 - 수정
    function fnEdit() {
        // [수정, 삭제] --> [확인, 취소] 버튼으로 변환
        $("#btnEdit").css("display", "none");
        $("#btnDelete").css("display", "none");
        $("#btnSave").css("display", "inline-block");
        $("#btnCancel").css("display", "inline-block");
        // $("#btnGatePick").css("display", "inline-block");
        // $("#btnAuthPick").css("display", "inline-block");
        $("#btnGatePick, #btnAuthPick").removeClass("disabled");
        $("[name=doorEdit]").prop("disabled", false);
        if (doorId !== "") {$("#dArea").prop("disabled", false);}
        // $("[name=doorEditSelect]").prop("disabled", false);
    }

    // 출입문 관리 - 추가
    function fnAdd() {
        fnEdit();
        initDetail();
        viewDetail();
        $("#doorNm").focus();
        $(".nodeSel").toggleClass("nodeSel node");
        doorId = "";
        // $("option[name='selected']").prop("selected", true);
    }

    // 출입문 관리 - 취소
    function fnCancel() {
        if (doorId === undefined || doorId === "") {
            // 추가 저장 시 취소
            initDetail();
            hideDetail();
        } else {
            // 수정 시 취소
            getGateDetail(doorId);
        }
    }

    // 출입문 저장
    function fnSave() {
        // validation
        if (fnIsEmpty($("#doorNm").val())) {
            alert("출입문 명칭을 입력하세요.");
            $("#doorNm").focus();
            return;
        }
        if (fnIsEmpty($("#dBuilding"))) {
            alert("빌딩을 선택해주세요");
            $("#dBuilding").focus();
            return;
        }
        if (fnIsEmpty($("#doorSchedule"))) {
            alert("스케쥴을 선택해주세요.");
            $("#doorSchedule").focus();
            return;
        }
        if (fnIsEmpty($("#doorAlarmGroup"))) {
            alert("알람 그룹을 선택해주세요.");
            $("#doorAlarmGroup").focus();
            return;
        }
        if (fnIsEmpty($("#terminalCd")) || fnIsEmpty($("#mgmtNum"))) {
            alert("단말기를 선택해주세요.");
            return;
        }
        if (fnIsEmpty($("#doorGroup"))) {
            alert("권한그룹을 선택해주세요.");
            return;
        }

        // disabled 해제
        $(":disabled").prop("disabled", false);

        fnSaveDoorAjax();
    }

    // 출입문 관리 - 삭제
    function fnDelete() {
        console.log(doorId);

        // 출입문에 연결된 단말기/ 권한 그룹 없을 경우
        // if (confirm("연결된 단말기 또는 권한 그룹이 존재 합니다. 삭제 하시겠습니까?")) {
        //     alert("해당 출입문 정보를 삭제하였습니다.");
        // } else {
        //     alert("취소하였습니다.");
        // }

        fnDeleteDoorAjax();
    }

    // 권한그룹 반영
    function authSave() {
        $("#authGroupId").val($("input[name=chkAuthConf]").val());

        var authGroup = [];
        $("input[name=chkAuthConf]").each(function (i) {
            var auth = $(this).closest("tr").children().eq(1).html();
            authGroup.push(auth);
        });
        console.log(authGroup);

        // 권한그룹 textarea에 뿌려주기
        $("#doorGroup").val(authGroup.join("\r\n"));
    }

    // 권한그룹 선택 저장
    function authConf() {
        authSave();
        closePopup("authPickPopup");
    }

    function totalCheck() {
        if ($("#totalAuthCheckAll").prop("checked")) {
            $("#totalAuthCheckAll").prop("checked", false);
        }
    }

    function userCheck() {
        if ($("#userAuthCheckAll").prop("checked")) {
            $("#userAuthCheckAll").prop("checked", false);
        }
    }

    // popup open (공통)
    function openPopup(popupNm) {
        $("#" + popupNm).PopupWindow("open");

        if (popupNm === "doorPickPopup") {
            fnGetTerminalListAjax(); // 단말기 목록
        }
        if (popupNm === "authPickPopup") {
            fnGetAuthGroupListAjax(); // 권한그룹 목록
        }
    }

    // popup close (공통)
    function closePopup(popupNm) {
        if (popupNm == "doorPickPopup") {
            console.log("closepopup - doorpickpopup");
            // 단말기 선택 팝업창 초기화
            // $("input[name='checkOne']:checked").attr("checked", false);
        } else if (popupNm == "authPickPopup") {
            // $("input[name='chkAuth']:checked").attr("checked", false);
            // $("input[name='chkAuthConf']:checked").attr("checked", false);
            totalCheck();
            userCheck();
        }
        $("#" + popupNm).PopupWindow("close");
    }


    /////////////////  출입문 목록 ajax - start  /////////////////////


    function fnGetDoorListAjax() {
        console.log("fnGetDoorListAjax1");

        $.ajax({
            type: "GET",
            data: {},
            dataType: "json",
            url: "<c:url value='/door/list.do' />",
            success: function (result) {
                console.log(result);
                // tree 생성
                createTree(true, result, $("#treeDiv"));
            }
        });
    }

    /////////////////  출입문 목록 ajax - end  /////////////////////


    /////////////////  단말기 목록 ajax - start  /////////////////////


    function fnGetTerminalListAjax(param1, param2) {

        console.log("fnGetTerminalListAjax");

        $.ajax({
            type: "GET",
            url: "<c:url value='/door/terminal/list.do' />",
            data: {
                keyword: param1
                , registrationionStatus: param2
            },
            dataType: "json",
            success: function (result) {
                // 값 초기화
                $("#tbTerminal").empty();
                $("#srchMachine").val("");
                console.log(result.terminalList);

                if (result.terminalList.length > 0) {
                    $.each(result.terminalList, function (i, terminal) {
                        let tag = "<tr class='h_35px' style='text-align:center'><td style='padding:0 14px;'>";
                        tag += "<input type='radio' value='" + terminal.id + "' name='checkOne'></td>";
                        tag += "<td>" + terminal.terminalCd + "</td>";
                        tag += "<td>" + terminal.mgmtNum + "</td>";
                        tag += "<td>" + terminal.terminalTyp + "</td>";
                        tag += "<td>" + terminal.doorNm + "</td></tr>";
                        $("#tbTerminal").append(tag);
                    });

                    // if (doorId !== "" && $("#terminalId").val() !== "") {
                    if ($("#terminalId").val() !== "") {
                        let terminalId = $("#terminalId").val(); // TODO: '/' 다중으로 오는 데이터는 앞의 데어터만 적용
                        $('input[name=checkOne]:input[value=' + terminalId + ']').attr("checked", true);
                    }
                    // if (doorId !== "" && $("#terminalCd").attr("tId") !== "") {
                    //     let terminalCd = $("#terminalCd").attr("tId").split("/")[0]; // TODO: '/' 다중으로 오는 데이터는 앞의 데어터만 적용
                    //     $('input[name=checkOne]:input[value=' + terminalCd + ']').attr("checked", true);
                    // }
                }
            }
        });
    }

    /////////////////  단말기 목록 조회 ajax - end  /////////////////////


    /////////////////  권한그룹 목록 ajax - start  /////////////////////


    function fnGetAuthGroupListAjax(param1) {

        console.log("fnGetAuthGroupListAjax");

        $.ajax({
            type: "GET",
            url: "<c:url value='/door/authGroup/list.do' />",
            data: { keyword: param1 },
            dataType: "json",
            success: function (result) {
                console.log(result);
                $("#tdAuthTotal").empty();
                $("#tdAuthConf").empty();
                $("#srchAuth").val("");

                if (result.authList.length > 0) {
                    $.each(result.authList, function (i, authGroup) {
                        let tag = "<tr><td style='padding: 0 14px;'><input type='checkbox' value='" + authGroup.id + "' name='chkAuth'/></td>";
                        tag += "<td>" + authGroup.authNm + "</td></tr>";
                        $("#tdAuthTotal").append(tag);
                    });

                    // if (doorId !== "" && $("#authGroupId").val() !== "") {
                    if ($("#authGroupId").val() !== "") {
                        let authGroupId = $("#authGroupId").val();
                        $('input[name=chkAuth]:input[value=' + authGroupId + ']').prop("checked", true);
                        $("#add_auth").click();
                    }
                    // if (doorId !== "" && $("#doorGroup").attr("aId") !== "") {
                    //     $("#tdAuthConf").empty();
                    //     let doorGroup = $("#doorGroup").attr("aId");
                    //     $('input[name=chkAuth]:input[value=' + doorGroup + ']').prop("checked", true);
                    //     $("#add_auth").click();
                    //     authSave();
                    // }
                }
            }
        });
    }

    /////////////////  권한그룹 목록 ajax - end  /////////////////////



    /////////////////  출입문 저장 ajax - start  /////////////////////


    function fnSaveDoorAjax() {
        let url = "";
        let data = {
            doorNm: $("#doorNm").val(),
            buildingId: $("#dBuilding").val(),
            areaId: $("#dArea").val(),
            floorId: $("#dFloor").val(),
            scheduleId: $("#doorSchedule").val(),
            alarmGroupId: $("#doorAlarmGroup").val(),
            terminalIds: $("#terminalId").val(),
            authGrIds: $("#authGroupId").val()
        };

        let doorId = "";
        doorId = $("#doorId").val();

        if (doorId === undefined || doorId === "") { // 등록 시
            url = "<c:url value='/door/add.do' />";
            data = data;
        } else { // 수정 시
            url = "<c:url value='/door/update.do' />";
            data.doorId = doorId;
        }

        $.ajax({
            type: "POST",
            url: url,
            data: data,
            dataType: "json",
            success: function (returnData) {
                console.log("fnSave: ");
                console.log(returnData);

                if (returnData.resultCode == "Y") {
                    //등록이 완료되었습니다.
                    alert("저장되었습니다.");
                    fnGetDoorListAjax();

                    if(returnData.newDoorId != "" ){
                        getGateDetail(returnData.newDoorId); //
                    }

                } else {
                    //등록에 문제가 발생
                    alert("등록에 실패하였습니다.");
                }
            }
        });

        fnCancel();
    }

    /////////////////  출입문 저장 ajax - end  /////////////////////



    /////////////////  출입문 삭제 ajax - start  /////////////////////


    function fnDeleteDoorAjax() {
        if (confirm("삭제 하시겠습니까?")) {
            $.ajax({
                type: "POST",
                url: "<c:url value='/door/delete.do' />",
                data: { id: doorId },
                dataType: "json",
                success: function (returnData) {
                    console.log("fnDelete: ");
                    console.log(returnData);

                    if (returnData.resultCode == "Y") {
                        // 삭제 성공
                        alert("해당 출입문 정보를 삭제하였습니다.");
                        fnGetDoorListAjax();
                        initDetail();
                        hideDetail();
                    } else {
                        // 삭제 실패
                        // TODO: 실패감지가 안됨
                        alert("삭제 실패");
                    }
                }
            });
        }
    }

    /////////////////  출입문 삭제 ajax - end  /////////////////////


</script>

<div class="com_box">
    <div class="totalbox" style="justify-content: end">
        <div class="r_btnbox mb_10">
            <button type="button" class="btn_excel" data-toggle="modal" id="excelDownload" onclick="openExcelDownload();">일괄등록 양식 다운로드</button>
            <button type="button" class="btn_excel" data-toggle="modal">일괄등록</button>
        </div>
    </div>
</div>

<div class="box_w3 mb_20" style="width:1200px; margin-top:0;">

    <%--  출입문  --%>
    <div style="width:550px;">
        <div class="totalbox mb_20">
            <div class="title_s w_50p fl" style="margin-bottom:7px;">
                <img src="/img/title_icon1.png" alt=""/>출입문
            </div>
        </div>
        <%--  출입문 tree  --%>
        <div class="com_box" style="border: 1px solid black; background-color: white;">
            <div id="treeDiv"></div>
            <div class="c_btnbox center mt_10 mb_10" style="height: 35px;">
                <div style="display: inline-block;">
                    <button type="button" class="comm_btn mr_20" onclick="fnAdd();">추가</button>
                </div>
            </div>
        </div>
        <%--  end of 출입문 tree  --%>
    </div>
    <%--  end of 출입문  --%>

    <%--  속성  --%>
    <div style="width:550px;">
        <div class="totalbox mb_20">
            <div class="title_s w_50p fl"><img src="/img/title_icon1.png" alt=""/>속성</div>
        </div>

        <%--  테이블  --%>
        <div class="com_box mt_5" style="background-color: white;">
            <div id="gateInfo" class="tb_outbox">
                <table class="tb_write_02 tb_write_p1">
                    <colgroup>
                        <col style="width:30%">
                        <col style="width:57%">
                        <col style="width:13%">
                    </colgroup>

                    <tbody class="gateDetailList" style="display: none;">
                    <input type="hidden" id="doorId" value="">
                    <input type="hidden" id="terminalId" value="">
                    <input type="hidden" id="authGroupId" value="">
                    <tr>
                        <td colspan="3" style="font-size: 17px; text-align: left; padding-left: 20px">
                            <b id="doorPath"></b>
                        </td>
                    </tr>
                    <tr>
                        <th>출입문 명</th>
                        <td colspan="2">
                            <input type="text" id="doorNm" name="doorEdit" maxlength="30" class="input_com doorNm" value="" disabled/>
                        </td>
                    </tr>
                    <tr>
                        <th>빌딩(동)</th>
                        <td colspan="2">
                            <select name="doorEdit" id="dBuilding" class="form-control" style="padding-left:10px;" disabled>
                                <option value="" name="selected">선택</option>
                                <c:forEach items="${buildingList}" var="building" varStatus="status">
                                    <option value='<c:out value="${building.id}"/>'><c:out value="${building.building_nm}"/></option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <th>구역</th>
                        <td colspan="2">
                            <select name="doorEditSelect" id="dArea" class="form-control" style="padding-left:10px;" disabled>
                                <option value="" name="selected">선택</option>
                                <c:forEach items="${areaList}" var="area" varStatus="status">
                                    <option value='<c:out value="${area.id}"/>' class="dArea" bId='<c:out value="${area.building_id}"/>'>
                                        <c:out value="${area.area_nm}"/></option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <th>층</th>
                        <td colspan="2">
                            <select name="doorEditSelect" id="dFloor" class="form-control" style="padding-left:10px;" disabled>
                                <option value="" name="selected">선택</option>
                                <c:forEach items="${floorList}" var="floor" varStatus="status">
                                    <option value='<c:out value="${floor.id}"/>' class="dFloor" aId='<c:out value="${floor.area_id}"/>'><c:out value="${floor.floor_nm}"/></option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <th>스케쥴</th>
                        <td colspan="2">
                            <select name="doorEdit" id="doorSchedule" class="form-control" style="padding-left:10px;" disabled>
                                <option value="" name="selected">선택</option>
                                <c:forEach items="${scheduleList}" var="schedule" varStatus="status">
                                    <option value='<c:out value="${schedule.id}"/>'><c:out value="${schedule.door_sch_nm}"/></option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <th>알람 그룹</th>
                        <td colspan="2">
                            <select name="doorEdit" id="doorAlarmGroup" class="form-control" style="padding-left:10px;" disabled>
                                <option value="" name="selected">선택</option>
                                <c:forEach items="${doorAlarmGrpList}" var="doorAlarmGroup" varStatus="status">
                                    <option value='<c:out value="${doorAlarmGroup.id}"/>'><c:out value="${doorAlarmGroup.nm}"/></option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <th>단말기 코드</th>
                        <td style="border-right:none; padding-right:0; padding-left:12px;">
                            <input type="text" id="terminalCd" name="terminalCd" maxlength="30" class="input_com" value="" disabled/>
                        </td>
                        <td>
                            <button type="button" id="btnGatePick" class="btn_gray3 btn_small disabled" onclick="openPopup('doorPickPopup');">선택</button>
                        </td>
                    </tr>
                    <tr>
                        <th>단말기 관리번호</th>
                        <td colspan="2">
                            <input type="text" id="mgmtNum" name="mgmtNum" maxlength="30" class="input_com" value="" disabled/>
                        </td>
                    </tr>
                    <tr>
                        <th>권한 그룹</th>
                        <td style="border-right:none; padding-right:0; padding-left:12px;">
                            <textarea id="doorGroup" name="doorGroup" rows="4" cols="33" class="mt_5 mb_5" style="font-size: 14px; line-height: 1.5; padding: 1px 10px;" disabled>보건 복지부 &#10;12동 전체</textarea>
                        </td>
                        <td>
                            <button type="button" id="btnAuthPick" class="btn_gray3 btn_small disabled" onclick="openPopup('authPickPopup');">선택</button>
                        </td>
                    </tr>
<%--                    <tr>--%>
<%--                        <td colspan="2" class="c_btnbox center">--%>
<%--                            <div style="display: inline-block">--%>
<%--                                <button type="button" id="btnGatePick" class="comm_btn btn_small mr_10" onclick="openPopup('doorPickPopup');" style="display:none;">단말기 선택</button>--%>
<%--                                <button type="button" id="btnAuthPick" class="comm_btn btn_small" onclick="openPopup('authPickPopup');" style="display:none;">권한그룹 선택</button>--%>
<%--                            </div>--%>
<%--                        </td>--%>
<%--                    </tr>--%>
                    </tbody>
                </table>

                <div class="c_btnbox center mt_10 mb_10" id="btn_wrapper" style="position: absolute; bottom: 0; display: none;">
                    <div style="display: inline-block;">
                        <button type="button" id="btnEdit" class="comm_btn mr_20" onclick="fnEdit();">수정</button>
                        <button type="button" id="btnDelete" class="comm_btn" onclick="fnDelete();">삭제</button>
                        <button type="button" id="btnSave" class="comm_btn mr_20" onclick="fnSave();" style="display:none">확인</button>
                        <button type="button" id="btnCancel" class="comm_btn" onclick="fnCancel();" style="display:none">취소</button>
                    </div>
                </div>
            </div>
        </div>
        <%--  end of 테이블  --%>
    </div>
    <%--  end of 속성  --%>
</div>

<%--  단말기 선택 modal  --%>
<div id="doorPickPopup" class="example_content" style="display: none;">
    <div class="popup_box">
        <%--  검색 박스 --%>
        <div class="search_box mb_20">
            <div class="search_in">
                <div class="comm_search mr_10">
                    <input type="text" class="input_com" id="srchMachine" name="srchMachine" value="" placeholder="단말기명 / 관리번호 / 단말기 코드" maxlength="30" style="width: 629px;">
                </div>
                <div class="comm_search ml_5 mr_10">
                    <input type="checkbox" id="unregisteredDoor" name="unregisteredDoor" value="unregistered">
                    <label for="unregisteredDoor" class="ml_5" style="position: relative; top: -12px;">출입문 미등록</label><br>
                </div>
                <div class="comm_search ml_40">
                    <div class="search_btn2" id="btnSearchTerminal"></div>
                </div>
            </div>
        </div>
        <%--  end of 검색 박스 --%>
        <div class="tb_outbox mb_20" style="height: 255px; overflow: auto;">
            <table class="tb_list tb_write_02 tb_write_p1">
                <colgroup>
                    <col style="width:5%">
                    <col style="width:28%">
                    <col style="width:20%">
                    <col style="width:22%">
                    <col style="width:25%">
                </colgroup>
                <thead>
                <tr>
                    <th>선택</th>
                    <th>단말기코드</th>
                    <th>관리번호</th>
                    <th>단말기유형</th>
                    <th>출입문명</th>
                </tr>
                </thead>
                <tbody id="tbTerminal">
                </tbody>
            </table>
        </div>

        <div class="c_btnbox">
            <div style="display: inline-block;">
                <button type="button" id="doorPickConfirm" class="comm_btn mr_20">확인</button>
                <button type="button" class="comm_btn" onclick="closePopup('doorPickPopup');">취소</button>
            </div>
        </div>
    </div>
</div>
<%--  end of 단말기 선택 modal  --%>

<%--  권한그룹 선택 modal  --%>
<div id="authPickPopup" class="example_content" style="display: none;">
    <div class="popup_box box_w3">
        <%--  검색 박스 --%>
        <div class="search_box mb_20">
            <div class="search_in">
                <div class="comm_search mr_10">
                    <input type="text" class="input_com" id="srchAuth" name="srchAuth" value="" placeholder="권한그룹명" maxlength="30" style="width: 765px;">
                </div>
                <div class="comm_search ml_40">
                    <div class="search_btn2" id="btnSearchAuthGroup"></div>
                </div>
            </div>
        </div>
        <%--  end of 검색 박스 --%>

        <%--  왼쪽 box  --%>
        <div style="width:45%;">
            <div class="com_box" style="border: 1px solid black; overflow: auto; height: 250px;">
                <table class="tb_list tb_write_02 tb_write_p1">
                    <colgroup>
                        <col style="width:15%">
                        <col style="width:85%">
                    </colgroup>
                    <thead>
                    <tr>
                        <th><input type="checkbox" id="totalAuthCheckAll"></th>
<%--                        <th>권한코드</th>--%>
                        <th>권한그룹 목록</th>
                    </tr>
                    </thead>
                    <tbody id="tdAuthTotal"></tbody>
                </table>
            </div>
        </div>
        <%--  end of 왼쪽 box  --%>

        <%--  화살표 이동  --%>
        <div class="box_w3_2" style="height: 250px;">
            <div class="btn_box">
                <img src="/img/ar_r.png" alt="" id="add_auth"/>
            </div>
            <div class="btn_box">
                <img src="/img/ar_l.png" alt="" id="delete_auth"/>
            </div>
        </div>
        <%--  end of 화살표 이동  --%>

        <%--  오른쪽 box  --%>
        <div style="width:45%;">
            <%--  테이블  --%>
            <div class="com_box" style="border: 1px solid black; overflow: auto; height: 250px;">
                <table class="tb_list tb_write_02 tb_write_p1">
                    <colgroup>
                        <col style="width:15%">
                        <col style="width:85%">
                    </colgroup>
                    <thead>
                    <tr>
                        <th><input type="checkbox" id="userAuthCheckAll"></th>
                        <th>선택된 권한그룹</th>
                    </tr>
                    </thead>
                    <tbody id="tdAuthConf"></tbody>
                </table>
            </div>
        </div>
        <%--  end of 오른쪽 box  --%>

        <div class="c_btnbox center mt_30">
            <div style="display: inline-block;">
                <button type="button" class="comm_btn mr_20" onclick="authConf();">확인</button>
                <button type="button" class="comm_btn" onclick="closePopup('authPickPopup');">취소</button>
            </div>
        </div>
    </div>
</div>
<%--  end of 권한그룹 선택 modal  --%>
