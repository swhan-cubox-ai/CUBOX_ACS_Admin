<%--
  Created by IntelliJ IDEA.
  User: USER
  Date: 2022-09-21
  Time: 오전 11:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="/WEB-INF/jsp/cubox/common/checkPasswd.jsp" flush="false"/>
<jsp:include page="/WEB-INF/jsp/cubox/common/doorPickPopup.jsp" flush="false"/>
<jsp:include page="/WEB-INF/jsp/cubox/common/doorListPopup.jsp" flush="false"/>

<style>
    .title_box {
        margin-top: 10px;
        margin-bottom: 20px;
    }
    .box {
        border: 1px solid #ccc;
        /*width: 715px;*/
    }
    #tdAlarmDetail tr th {
        text-align: center;
    }
    thead {
        position: sticky;
        top: 0;
    }

</style>

<script type="text/javascript">

    const defaultTime = 60; // 기본 시간 설정

    $(function() {
        $(".title_tx").html("출입문 알람 그룹 - 상세");

        modalPopup("doorListPopup", "출입문 목록", 450, 550);
        modalPopup("doorEditPopup", "출입문 수정", 900, 600);

        // chkAlType();

        // 출입문 알람그룹 명 유효성 체크
        $("#alNm").focusout(function() {
            console.log("이름 input을 벗어남");

            // TODO : 출입문스케쥴명 유효성 체크 (ajax)
        });

        // 유형 - 기본시간
        $("#alType").change(function() {
            console.log("alType 유형");
            console.log($(this).val());

            // chkAlType();
        });

    });

    // 유형:기본시간 --> 시간 고정
    // function chkAlType() {
    //     if ($("#alType").val() == "default") {
    //         $("#alTime").val(defaultTime).attr("disabled", true);
    //     } else {
    //         $("#alTime").val("").attr("disabled", false);
    //     }
    // }

    // 수정 확인
    function fnSave() {
        console.log("fnSave");

        // 입력값 유효성 체크
        if (fnIsEmpty($("#alNm").val())) {
            alert("출입문 알람 그룹 명을 입력해주세요.");
            $("#alNm").focus(); return;
        } else if (fnIsEmpty($("#alType").val())) {
            alert("유형을 선택해주세요.");
            $("#alType").focus(); return;
        } else if (fnIsEmpty($("#alTime").val())) {
            alert("시간을 입력해주세요.");
            $("#alTime").focus(); return;
        } else if (fnIsEmpty($("#alUseYn").val())) {
            alert("사용여부를 선택해주세요.");
            $("#alUseYn").focus(); return;
        } else if (fnIsEmpty($("#doorIds").val() || $("#alDoorCnt").val()) == 0) {
            alert("출입문을 선택해주세요.");
            return;
        }
        fnUpdateAlarmGroupAjax();
    }


    /////////////////  출입문 알람그룹 저장 ajax - start  /////////////////////

    function fnUpdateAlarmGroupAjax() {
        let alNm = $("#alNm").val();
        let alType = $("#alType").val();
        let alTime = $("#alTime").val();
        let alUseYn = $("#alUseYn").val();
        let doorIds = $("#doorIds").val();
        let url = "<c:url value='/door/alarm/modify/${doorGroupDetail.id}'/>"
        // TODO : 저장할 때 #alTime disabled 된 것 풀어줘야 함.

        console.log(alNm);
        console.log(alType);
        console.log(alTime);
        console.log(alUseYn);
        console.log(doorIds);
        console.log(url);

        $.ajax({
            type: "POST",
            url: url,
            data: {
                nm: alNm,
                type: alType,
                time: alTime,
                useYn: alUseYn,
                doorIds: doorIds
            },
            dataType: "json",
            success: function(result) {
                console.log("fnSave : " + result.resultCode);
                if (result.resultCode === "Y") {
                    alert("수정이 완료되었습니다.");
                    window.location.href = '/door/alarm/detail/${doorGroupDetail.id}';
                } else {
                    alert("수정에 실패하였습니다.");
                }
            }
        });
    }

    /////////////////  출입문 알람그룹 저장 ajax - end  /////////////////////


    // 수정 취소
    function fnCancel() {
        // $(".title_tx").html("출입문 스케쥴 - 상세");
        // $("#btnEdit").css("display", "none");
        // $("#btnboxDetail").css("display", "block");
        // $("#btnboxEdit").css("display", "none");
        // $("[name=detail]").attr("disabled", true);
        window.location.href = '/door/alarm/detail/${doorGroupDetail.id}';
    }

    // 수정 버튼
    function fnEditMode() {
        $(".title_tx").html("출입문 알람 그룹 - 수정");
        $("#btnEdit").css("display", "inline-block");
        $("#btnboxDetail").css("display", "none");
        $("#btnboxEdit").css("display", "block");
        $("[name=detail]").attr("disabled", false);
    }

    // 삭제 버튼
    function fnDelete() {
        // 연결된 출입문 존재 시
        if (("#doorIds").val() !== "") {
            alert("연결된 출입문을 모두 해제한 후 삭제하세요.");
            return;
        }

        if (!confirm("삭제하시겠습니까?")) {
            return;
        }

        let id = $("#alarmGroupId").val();

        $.ajax({
           type: "post",
           url: "/door/alarm/delete/" + id,
           dataType: 'json',
           success: function(result, status) {
               if (result.resultCode === "Y") {
                   alert("삭제가 완료되었습니다.");
                   location.href = "/door/alarm/list.do";
               } else {
                   alert("삭제 중 오류가 발생했습니다.");
               }
           }
        });
    }

    // popup open (공통)
    function openPopup(popupNm) {
        $("#" + popupNm).PopupWindow("open");
        if (popupNm === "doorEditPopup") { // 출입문 수정 팝업
            fnGetDoorListAjax("AlarmGroup"); //출입문 목록
        }
    }

    // popup close (공통)
    function closePopup(popupNm) {
        $("#" + popupNm).PopupWindow("close");

        if (popupNm === "doorEditPopup") { // 출입문 수정 팝업
            // TODO : 출입문 저장 로직
            setDoors("AlarmGroup");
            $("#alDoorCnt").val($("input[name=chkDoorConf]").length);
        }
    }

</script>
<form id="detailForm" name="detailForm" method="post" enctype="multipart/form-data">
    <div class="tb_01_box">
        <table class="tb_write_02 tb_write_p1 box">
            <colgroup>
                <col style="width:10%">
                <col style="width:90%">
            </colgroup>
            <tbody id="tdAlarmDetail">
            <input type="hidden" id="alarmGroupId" value="${doorGroupDetail.id}">
            <input type="hidden" id="doorIds" value="">
            <tr>
                <th>출입문 알람 그룹 명</th>
                <td>
                    <input type="text" id="alNm" name="detail" maxlength="50" value="${doorGroupDetail.nm}" class="input_com w_600px" disabled>
                </td>
            </tr>
            <tr>
                <th>유형</th>
                <td>
                    <select id="alType" name="detail" class="form-control input_com w_600px" style="padding-left:10px;" disabled>
                        <option value="">선택</option>
                        <option value="Y" selected>Y</option>
<%--                        <c:choose>--%>
<%--                            <c:when test="${doorGroupDetail.env_yn eq 'Y'}">--%>
<%--                                <option value="Y" selected>Y</option>--%>
<%--                            </c:when>--%>
<%--                            <c:otherwise>--%>
<%--                                <option value="default" selected>기본 시간</option>--%>
<%--                                <option value="setTime">지정 시간</option>--%>
<%--                            </c:otherwise>--%>
<%--                        </c:choose>--%>
                    </select>
                </td>
            </tr>
            <tr>
                <th>시간</th>
                <td>
                    <input type="number" id="alTime" name="detail" maxlength="10" min="1" value="${doorGroupDetail.time}" class="input_com w_600px"
                           oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');" disabled>&ensp;초
                </td>
            </tr>
            <tr>
                <th>사용</th>
                <td>
                    <select id="alUseYn" name="detail" class="form-control input_com w_600px" style="padding-left:10px;" disabled>
                        <option value="">선택</option>
                        <option value="yes" selected>Y</option>
                        <option value="no">N</option>
                    </select>
                </td>
            </tr>
            <tr>
                <th>출입문 수</th>
                <td>
                    <input type="text" id="alDoorCnt" name="alDoorCnt" maxlength="50" value="${doorGroupDetail.door_cnt}" class="input_com w_600px" disabled>
                    <button type="button" class="btn_small color_basic" onclick="openPopup('doorListPopup')">출입문 목록</button>
                    <button type="button" id="btnEdit" class="btn_small color_basic" onclick="openPopup('doorEditPopup')" style="display: none">출입문 수정</button>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</form>

<div class="right_btn mt_20" id="btnboxDetail">
    <button class="btn_middle color_basic" onclick="location='/door/alarm/list.do'">목록</button>
    <button class="btn_middle ml_5 color_basic" onclick="fnEditMode();">수정</button>
    <button class="btn_middle ml_5 color_basic" onclick="fnDelete();">삭제</button>
</div>
<div class="right_btn mt_20" id="btnboxEdit" style="display: none;">
    <button class="btn_middle ml_5 color_basic" onclick="fnSave();">저장</button>
    <button class="btn_middle ml_5 color_basic" onclick="fnCancel();">취소</button>
</div>

