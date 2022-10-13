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
        height: 51px;
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
        width: 95%;
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
        width: 100px;
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

        $("input[name=checkAll]").click(function () {
            if ($(this).prop("checked")) {
                $("input[name=excelColumn]").prop("checked", true);
            } else {
                $("input[name=excelColumn]").prop("checked", false);
            }
        });

        // 단말기 선택 확인
        $('#doorPickConfirm').click(function () {
            let chkTerminal = $("input[name=checkOne]:checked").closest("tr").children();

            // TODO : 등록된 단말기 여부 확인
            //  alert("이미 등록된 단말기입니다.");

            $("#terminalCd").val(chkTerminal.eq(1).html());
            $("#mgmtNum").val(chkTerminal.eq(2).html());
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
        // 명칭은 테스트를 위한 가칭으로 필요시 수정해서 사용하세요
        $("#searchTeminalBtn").click(function () {
            //param1 : 검색어, param2 : 출입문 미등록 체크박스 value값 Y or N
            let chk = "Y"
            fnGetTerminalListAjax($("#srchMachine").val(), chk);
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
                $("#doorPath").text(dInfo.door_nm.replaceAll(" ", " > "));  // 경로
                $("#doorNm").val(dInfo.door_nm);                            // 출입문 명
                $("#dBuilding").val(dInfo.building_id);                     // 빌딩
                $("#dArea").val(dInfo.area_id);                             // 구역
                $("#dFloor").val(dInfo.floor_id);                           // 층
                $("#doorSchedule").val(dInfo.sch_id);                       // 스케쥴
                $("#terminalCd").val(dInfo.terminal_cd);                    // 단말기 코드
                $("#mgmtNum").val(dInfo.mgmt_num);                          // 단말기 관리번호
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
        $("#btnGatePick").css("display", "none");
        $("#btnAuthPick").css("display", "none");
        $("[name=doorEdit]").prop("disabled", true);
    }

    // 출입문 관리 - 수정
    function fnEdit() {
        // [수정, 삭제] --> [확인, 취소] 버튼으로 변환
        $("#btnEdit").css("display", "none");
        $("#btnDelete").css("display", "none");
        $("#btnSave").css("display", "inline-block");
        $("#btnCancel").css("display", "inline-block");
        $("#btnGatePick").css("display", "inline-block");
        $("#btnAuthPick").css("display", "inline-block");
        $("[name=doorEdit]").prop("disabled", false);
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

    // 출입문 관리 - 수정 저장 확인
    function fnSave() {
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

        //출입문 정보
        $.ajax({
            type: "POST",
            url: "<c:url value='/door/add.do' />",
            data:
            //파라미터 수정
                {
                    doorNm: "1",
                    buildingId: "2",
                    areaId: "3",
                    floorId: "4",
                    scheduleId: "5",
                    alarmGroupId: "6",
                    terminalCd: "7",
                    mgmtNum: "8",
                    doorGrId: "9"
                },
            dataType: "json",
            success: function (returnData) {

                console.log("fnSave:" + returnData.result);

                if (returnData.result == "success") {
                    //등록이 완료되었습니다.
                } else {
                    //등록에 문제가 발생
                }

            }
        });


        // TODO : 출입문 속성 저장 ajax
        // getGateDetail();
        fnCancel();
    }

    // 출입문 관리 - 삭제
    function fnDelete() {
        if (confirm("삭제 하시겠습니까?")) {
            alert("해당 출입문 정보를 삭제하였습니다.");
            initDetail();
            hideDetail();
        } else {
            alert("취소하였습니다.");
        }

        // 출입문에 연결된 단말기/ 권한 그룹 없을 경우
        // if (confirm("연결된 단말기 또는 권한 그룹이 존재 합니다. 삭제 하시겠습니까?")) {
        //     alert("해당 출입문 정보를 삭제하였습니다.");
        // } else {
        //     alert("취소하였습니다.");
        // }

    }

    // 권한그룹 선택 저장
    function authSave() {
        var authGroup = [];
        $("input[name=chkAuthConf]").each(function (i) {
            var auth = $(this).closest("tr").children().eq(1).html();
            authGroup.push(auth);
        });
        console.log(authGroup);

        // 권한그룹 textarea에 뿌려주기
        $("#doorGroup").val(authGroup.join("\r\n"));
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
            fnGetTerminalListAjax(); //단말기 목록
        }
    }

    // popup close (공통)
    function closePopup(popupNm) {
        $("#" + popupNm).PopupWindow("close");

        if (popupNm == "doorPickPopup") {
            // 단말기 선택 팝업창 초기화
            $("input[name='checkOne']:checked").attr("checked", false);
        } else if (popupNm == "authPickPopup") {
            fnGetAuthGroupListAjax();
            $("input[name='chkAuth']:checked").attr("checked", false);
            $("input[name='chkAuthConf']:checked").attr("checked", false);
            totalCheck();
            userCheck();
        }
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
                $("#tbTerminal").empty();
                if (result.terminalList.length > 0) {
                    $.each(result.terminalList, function (i, terminal) {
                        console.log("단말기 목록 : " + terminal.id + "/" + terminal.doorNm + "/" + terminal.terminalCd + "/" + terminal.terminalTyp + "/" + terminal.mgmtNum);
                        // 단말기 코드, 관리번호, 단말기 유형, 출입문명
                        // terminalCd, mgmtNum, terminalTyp, doorNm
                        let tag = "<tr class='h_35px' style='text-align:center'><td style='padding:0 14px;'><input type='radio' name='checkOne'></td>";
                        tag += "<td>" + terminal.terminalCd + "</td>";
                        tag += "<td>" + terminal.mgmtNum + "</td>";
                        tag += "<td>" + terminal.terminalTyp + "</td>";
                        tag += "<td>" + terminal.doorNm + "</td></tr>";

                        $("#tbTerminal").append(tag);
                    });
                }
            }
        });
    }

    /////////////////  단말기 목록 조회 ajax - end  /////////////////////


    /////////////////  권한그룹 목록 ajax - start  /////////////////////


    function fnGetAuthGroupListAjax() {
        console.log("fnGetAuthGroupListAjax");
        let checkYn = "Y";
        $.ajax({
            type: "GET",
            url: "<c:url value='/door/AuthGroup/list.do' />",
            data: {checkYn: checkYn},
            dataType: "json",
            success: function (result) {
                console.log(result);
                if (result.authGroupList.length > 0) {
                    $.each(result.authGroupList, function (i, authGroup) {
                        console.log(authGroup.id + "/" + authGroup.authNm + "/" + authGroup.deptAuthYn);
                    });
                }
            }
        });
    }


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
            <div class="title_s w_50p fl">
                <img src="/img/title_icon1.png" alt=""/>속성
            </div>
        </div>

        <%--  테이블  --%>
        <div class="com_box mt_5" style="background-color: white;">
            <div id="gateInfo" class="tb_outbox">
                <%--  <input type="hidden" id="fsiteid2" name="fsiteid" />--%>
                <table class="tb_write_02 tb_write_p1">
                    <colgroup>
                        <col style="width:30%">
                        <col style="width:70%">
                    </colgroup>

                    <tbody class="gateDetailList" style="display: none;">
                    <tr>
                        <td colspan="2" style="font-size: 17px; text-align: left; padding-left: 20px"><b id="doorPath"></b></td>
                    </tr>
                    <tr>
                        <th>출입문 명</th>
                        <td>
                            <input type="text" id="doorNm" name="doorEdit" maxlength="30" class="input_com doorNm" value="" disabled/>
                        </td>
                    </tr>

                    <tr>
                        <th>빌딩(동)</th>
                        <td>
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
                        <td>
                            <select name="doorEdit" id="dArea" class="form-control" style="padding-left:10px;" disabled>
                                <option value="" name="selected">선택</option>
                                <c:forEach items="${areaList}" var="area" varStatus="status">
                                    <option value='<c:out value="${area.id}"/>'><c:out value="${area.area_nm}"/></option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <th>층</th>
                        <td>
                            <select name="doorEdit" id="dFloor" class="form-control" style="padding-left:10px;" disabled>
                                <option value="" name="selected">선택</option>
                                <c:forEach items="${floorList}" var="floor" varStatus="status">
                                    <option value='<c:out value="${floor.id}"/>'><c:out value="${floor.floor_nm}"/></option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <th>스케쥴</th>
                        <td>
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
                        <td>
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
                        <td>
                            <input type="text" id="terminalCd" name="terminalCd" maxlength="30" class="input_com" value="" disabled/>
                        </td>
                    </tr>
                    <tr>
                        <th>단말기 관리번호</th>
                        <td>
                            <input type="text" id="mgmtNum" name="mgmtNum" maxlength="30" class="input_com" value="" disabled/>
                        </td>
                    </tr>
                    <tr>
                        <th>권한 그룹</th>
                        <td>
                            <textarea id="doorGroup" name="doorGroup" rows="5" cols="33" style="font-size: 14px; line-height: 1.5; padding: 1px 10px;" disabled>보건 복지부 &#10;12동 전체</textarea>
                        </td>
                    </tr>
                    <tr>
                        <%-- <td colspan="2" class="c_btnbox center" style="border:none;">--%>
                        <td colspan="2" class="c_btnbox center">
                            <div style="display: inline-block">
                                <button type="button" id="btnGatePick" class="comm_btn btn_small mr_10" onclick="openPopup('doorPickPopup');" style="display:none;">단말기 선택</button>
                                <button type="button" id="btnAuthPick" class="comm_btn btn_small" onclick="openPopup('authPickPopup');" style="display:none;">권한그룹 선택</button>
                            </div>
                        </td>
                    </tr>
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
                    <div class="search_btn2" id="searchTeminalBtn"></div>
                </div>
            </div>
        </div>
        <%--  end of 검색 박스 --%>
        <div class="tb_outbox mb_20" style="height: 255px; overflow: auto;">
            <%-- <input type="hidden" id="fsiteid2" name="fsiteid" />--%>
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
                    <div class="search_btn2"></div>
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
                        <col style="width:20%">
                        <col style="width:65%">
                    </colgroup>
                    <thead>
                    <tr>
                        <th><input type="checkbox" id="totalAuthCheckAll"></th>
                        <th>권한코드</th>
                        <th>권한그룹명</th>
                    </tr>
                    </thead>
                    <tbody id="tdAuthTotal">
                    <c:forEach var="i" begin="1" end="10" varStatus="status">
                        <tr>
                            <td style="padding: 0 14px;"><input type="checkbox" name="chkAuth"/></td>
                            <td>CUBOX123</td>
                            <td>보건 복지부</td>
                        </tr>
                    </c:forEach>
                    </tbody>
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
                        <col style="width:20%">
                        <col style="width:65%">
                    </colgroup>
                    <thead>
                    <tr>
                        <th><input type="checkbox" id="userAuthCheckAll"></th>
                        <th>권한코드</th>
                        <th>권한그룹명</th>
                    </tr>
                    </thead>
                    <tbody id="tdAuthConf">
                    <tr>
                        <td style="padding: 0 14px;"><input type="checkbox" name="chkAuthConf"/></td>
                        <td>CUBOX123</td>
                        <td>보건 복지부</td>
                    </tr>
                    <tr>
                        <td style="padding: 0 14px;"><input type="checkbox" name="chkAuthConf"/></td>
                        <td>CUBOX123</td>
                        <td>보건 복지부</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <%--  end of 오른쪽 box  --%>

        <div class="c_btnbox center mt_30">
            <div style="display: inline-block;">
                <button type="button" class="comm_btn mr_20" onclick="authSave();">확인</button>
                <button type="button" class="comm_btn" onclick="closePopup('authPickPopup');">취소</button>
            </div>
        </div>
    </div>
</div>
<%--  end of 권한그룹 선택 modal  --%>
