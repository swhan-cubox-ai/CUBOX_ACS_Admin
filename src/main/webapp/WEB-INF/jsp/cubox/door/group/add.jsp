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
        // 입력값 유효성 체크
        if (fnIsEmpty($("#gpNm").val())) {
            alert("출입문 그룹 명을 입력해주세요.");
            $("#gpNm").focus();
            return;
        }
        fnSaveGroupAjax();
    }

    // // 출입문선택 반영
    // function setDoors() {
    //
    //     let doorGpIds = "";
    //     let doorGpHtml = [];
    //     $("input[name=chkDoorConf]").each(function (i) {
    //         let ids = $(this).attr("id");
    //         let html = $(this).closest("tr").children().eq(1).find("span").html();
    //         if (i == 0) {
    //             doorGpIds += ids;
    //         } else if (i > 0) {
    //             doorGpIds += ("/" + ids);
    //         }
    //         doorGpHtml.push(html);
    //     });
    //     console.log(doorGpIds);
    //     console.log(doorGpHtml);
    //
    //     $("#gpDoorIds").val(doorGpIds);
    //     $("#gpDoorNms").val(doorGpHtml.join("\r\n"));
    //
    // }

    // popup open (공통)
    function openPopup(popupNm) {
        $("#" + popupNm).PopupWindow("open");
        fnGetDoorListAjax(); //출입문 목록
    }

    // popup close (공통)
    function closePopup(popupNm) {
        setDoors();
        $("#" + popupNm).PopupWindow("close");
    }


    /////////////////  출입문 그룹 저장 ajax - start  /////////////////////

    function fnSaveGroupAjax() {
        let gpNm = $("#gpNm").val();
        let scheduleId = $("#gpSchedule").val();
        let doorIds = $("#gpDoorIds").val();

        console.log(gpNm);
        console.log(scheduleId);
        console.log(doorIds);

        $.ajax({
            type: "POST",
            url: "<c:url value='/door/group/save.do' />",
            data:{
                nm: gpNm,
                scheduleId: scheduleId,
                doorIds: doorIds // 1, 1/2  2개 이상일 경우 "/" 으로 구분자 추가
            },
            dataType: "json",
            success: function (result) {
                console.log("fnSave:" + result.resultCode);

                if( result.resultCode == "Y" ) {
                    if (result.newDoorId !== "" ) {
                        alert("등록이 완료되었습니다.");
                        window.location.href = '/door/group/detail/' + result.newDoorId;
                    }
                    // $('#gpNm').prop('disabled', true);
                    // $('#gpSchedule').prop('disabled', true);
                    // $('#gpDoorNms').prop('disabled', true);
                    //
                    // $('#btnSelDoor').hide();
                    // $('#saveBtn').hide();
                    // $('#cancelBtn').hide();
                    // $('#listBtn').show();
                    //

                } else {
                    alert("등록에 실패하였습니다.");
                }

            }
        });
    }

    /////////////////  출입문 그룹 저장 ajax - end  /////////////////////

</script>
<form id="detailForm" name="detailForm" method="post" enctype="multipart/form-data">
    <div class="tb_01_box">
        <table class="tb_write_02 tb_write_p1 box">
            <colgroup>
                <col style="width:10%">
                <col style="width:90%">
            </colgroup>

            <tbody id="tdGroupDetail">
            <input type="hidden" id="gpDoorIds" value="">
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
                        <c:forEach items="${scheduleList}" var="schedule" varStatus="status">
                            <option value='<c:out value="${schedule.id}"/>'><c:out value="${schedule.door_sch_nm}"/></option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <th>출입문</th>
                <td style="display: flex;">
                    <textarea id="gpDoorNms" name="gpDoorNms" rows="10" cols="33" class="w_600px" style="border-color: #ccc; border-radius: 2px; font-size: 14px; line-height: 1.5; padding: 2px 10px;" disabled></textarea>
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


