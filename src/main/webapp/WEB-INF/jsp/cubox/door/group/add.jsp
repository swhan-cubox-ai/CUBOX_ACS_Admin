<%--
  Created by IntelliJ IDEA.
  User: USER
  Date: 2022-09-23
  Time: 오후 3:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="/WEB-INF/jsp/cubox/common/checkPasswd.jsp" flush="false"/>
<jsp:include page="/WEB-INF/jsp/cubox/common/doorPickPopup.jsp" flush="false"/>

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
    #doorSelected tr th {
        text-align: center;
    }
</style>

<script type="text/javascript">

    const defaultTime = 60; // 기본 시간 설정

    $(function () {

        $(".title_tx").html("출입문 그룹 관리 - 등록");
        $("#gpNm").focus();

        $("#listBtn").hide();
        modalPopup("doorEditPopup", "출입문 선택", 900, 600);

        // 출입문 그룹 명 유효성 체크
        $("#gpNm").focusout(function () {
            console.log("이름 input을 벗어남");

            // TODO : 출입문 그룹 명 유효성 체크 (ajax)
        });

    });

    // 출입문 저장, 등록
    function fnSave() {
        console.log("fnSave");
        let gpNm = $("#gpNm").val();
        let gpSchedule = $("#gpSchedule").val();
        let gpDoor = $("#gpDoor").val();

        // 입력값 유효성 체크
        if (fnIsEmpty(gpNm)) {
            alert("출입문 그룹 명을 입력해주세요.");
            $("#gpNm").focus();
            return;
        } else if (fnIsEmpty(gpSchedule)) {
            alert("출입문 스케쥴을 선택해주세요.");
            $("#gpSchedule").focus();
            return;
        } else if (fnIsEmpty(gpDoor)) {
            alert("출입문을 선택해주세요.");
            return;
        }

        //출입문 정보
        $.ajax({
            type: "POST",
            url: "<c:url value='/door/group/save.do' />",
            data:{ //example
                   nm: "춥입문 테스트 그룹1",
                   scheduleId: "1",
                   doorIds: "1/2/3", // 1, 1/2  2개 이상일 경우 "/" 으로 구분자 추가
            },
            dataType: "json",
            success: function (returnData) {
                console.log("fnSave:" + returnData.result);

                if( returnData.resultCode == "Y" ) {
                    $('#gpNm').prop('disabled', true);
                    $('#gpSchedule').prop('disabled', true);
                    $('#gpDoor').prop('disabled', true);

                    $('#btnSelDoor').hide();
                    $('#saveBtn').hide();
                    $('#cancelBtn').hide();
                    $('#listBtn').show();

                    alert("등록이 완료되었습니다.")

                } else {
                    //등록에 문제가 발생
                }

            }
        });


    }

    // popup open (공통)
    function openPopup(popupNm) {
        $("#" + popupNm).PopupWindow("open");
        if (popupNm === "doorEditPopup") {
            fnGetDoorListAjax(); //출입문 목록
        }
    }

    // popup close (공통)
    function closePopup(popupNm) {
        let doorGroup = [];

        $("input[name=chkDoorConf]").each(function (i) {
            let chkDoor = $(this).closest("tr").children().eq(1).html();
            doorGroup.push(chkDoor);
        });
        $("#gpDoor").val(doorGroup.join("\r\n"));

        $("#" + popupNm).PopupWindow("close");
    }

</script>
<form id="detailForm" name="detailForm" method="post" enctype="multipart/form-data">
    <div class="tb_01_box">
        <table class="tb_write_02 tb_write_p1 box">
            <colgroup>
                <col style="width:10%">
                <col style="width:90%">
            </colgroup>
            <tbody id="tdGroupDetail">
            <tr>
                <th>출입문 그룹 명</th>
                <td>
                    <input type="text" id="gpNm" name="gpNm" maxlength="50" value="" class="input_com w_600px">
                </td>
            </tr>
            <tr>
                <th>출입문 스케쥴</th>
                <td>
                    <select id="gpSchedule" name="gpSchedule" class="form-control input_com w_600px" style="padding-left:10px;">
                        <option value="">선택</option>
                        <option value="default" selected>3동 평일</option>
                        <option value="setTime">3동 주말</option>
                    </select>
                </td>
            </tr>
            <tr>
                <th>출입문</th>
                <td style="display: flex;">
                    <textarea id="gpDoor" name="gpDoor" rows="10" cols="33" class="w_600px" style="border-color: #ccc; border-radius: 2px; font-size: 14px; line-height: 1.5; padding: 2px 10px;" disabled><c:if test="${editMode eq 'edit'}">16동 현관</c:if></textarea>
                    <div class="ml_10" style="position: relative;">
                        <button type="button" class="btn_small color_basic" style="position: absolute; bottom: 0; width: 80px;" onclick="openPopup('doorEditPopup')" id="btnSelDoor">출입문 선택</button>
                    </div>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</form>

<div class="right_btn mt_20">
    <button class="btn_middle ml_5 color_basic" onclick="location='/door/group/list.do'" id="listBtn">목록</button>
    <button class="btn_middle ml_5 color_basic" onclick="fnSave();" id="saveBtn">등록</button>
    <button class="btn_middle ml_5 color_basic" onclick="location='/door/group/list.do'" id="cancelBtn" >취소</button>
</div>


