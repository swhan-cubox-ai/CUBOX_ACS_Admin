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
    $(function() {
        $(".title_tx").html("출입문 알람 그룹 - 상세");
        modalPopup("doorListPopup", "출입문 목록", 450, 550);
    });

    // 목록 버튼
    function fnList() {
        location.href = "/door/alarm.do";
    }

    // 수정 버튼
    function fnEdit() {
        f = document.detailForm;
        f.action = "/door/alarm_add.do";
        $("#tdAlarmDetail input").attr("disabled", false);
        f.submit();
    }

    // 삭제 버튼
    function fnDelete() {
        // 연결된 출입문 존재 시
        if ($("#alDoorCnt").val() != "0" || $("#alDoorCnt").val() != "") {
            alert("연결된 출입문을 모두 해제한 후 삭제하세요.");
            return;
        }

        if (!confirm("삭제하시겠습니까?")) {
            return;
        }
        // location.href = "/door/alarm_delete.do";
        location.href = "/door/alarm.do"; // 임시 : 저장되었다고 생각하고 list로 돌아감
    }

    // popup open (공통)
    function openPopup(popupNm) {
        $("#" + popupNm).PopupWindow("open");
    }

    // popup close (공통)
    function closePopup(popupNm) {
        $("#" + popupNm).PopupWindow("close");
    }

</script>
<form id="detailForm" name="detailForm" method="post" enctype="multipart/form-data">
    <input type="hidden" id="editMode" name="editMode" value="edit"/>
    <div class="tb_01_box">
        <table class="tb_write_02 tb_write_p1 box">
            <colgroup>
                <col style="width:10%">
                <col style="width:90%">
            </colgroup>
            <tbody id="tdAlarmDetail">
            <tr>
                <th>출입문 알람 그룹 명</th>
                <td>
                    <input type="text" id="alNm" name="alNm" maxlength="50" value="작업자 통로" class="input_com w_600px" disabled>
                </td>
            </tr>
            <tr>
                <th>유형</th>
                <td>
                    <input type="text" id="alType" name="alType" maxlength="50" value="기본시간" class="input_com w_600px" disabled>
                </td>
            </tr>
            <tr>
                <th>시간</th>
                <td>
                    <input type="text" id="alTime" name="alTime" maxlength="10" value="30" class="input_com w_600px" disabled>&ensp;초
                </td>
            </tr>
            <tr>
                <th>사용</th>
                <td>
                    <input type="text" id="alUseYn" name="alUseYn" maxlength="5" value="Y" class="input_com w_600px" disabled>
                </td>
            </tr>
            <tr>
                <th>출입문 수</th>
                <td>
                    <input type="text" id="alDoorCnt" name="alDoorCnt" maxlength="50" value="2" class="input_com w_600px" disabled>&ensp;
                    <button type="button" class="btn_small color_basic" onclick="openPopup('doorListPopup')">출입문 목록</button>
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
</div>


<%--  출입문 목록 modal  --%>
<div id="doorListPopup" class="example_content">
    <div class="popup_box box_w3">

        <%--  테이블  --%>
        <div style="width:100%;">
            <div class="com_box" style="border: 1px solid black; background-color: white; overflow: auto; height: 330px;">
                <table class="tb_list tb_write_02 tb_write_p1">
                    <tbody id="tdGroupTotal">
                        <tr>
                            <td>12동 > C구역 > 1층 > 현관 출입문</td>
                        </tr>
                        <tr>
                            <td>12동 > D구역 > 2층 > 계단</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <%--  end of 테이블  --%>

        <div class="c_btnbox center mt_30">
            <div style="display: inline-block;">
                <button type="button" class="comm_btn mr_20" onclick="closePopup('doorListPopup');">확인</button>
            </div>
        </div>
    </div>
</div>
<%--  end of 출입문 목록 modal  --%>

