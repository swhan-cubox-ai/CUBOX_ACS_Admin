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
    #tdGroupDetail tr th {
        text-align: center;
    }
    thead {
        position: sticky;
        top: 0;
    }

</style>

<script type="text/javascript">
    $(function() {
        $(".title_tx").html("출입문 그룹 관리 - 상세");

        modalPopup("doorEditPopup", "출입문 선택", 900, 600);

        // 출입문 그룹 명 유효성 체크
        $("#gpNm").focusout(function() {
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
            $("#gpNm").focus(); return;
        } else if (fnIsEmpty(gpSchedule)) {
            alert("출입문 스케쥴을 선택해주세요.");
            $("#gpSchedule").focus(); return;
        } else if (fnIsEmpty(gpDoor)) {
            alert("출입문을 선택해주세요."); return;
        }



        let id = "1"; // example - doorgroup id

        $.ajax({
            type: "post",
            url: "/door/group/modify/" + id,
            data :{
                nm: "춥입문 테스트 그룹1",
                scheduleId: "1",
                doorIds: "1/2/3"
            },
            dataType: 'json',
            success: function (data, status) {
                if (data.resultCode == "Y") {
                    $('#gpNm').prop('disabled', true);
                    $('#gpSchedule').prop('disabled', true);
                    $('#gpDoor').prop('disabled', true);

                    $('#btnSelDoor').hide();
                    $('#saveBtn').hide();
                    $('#cancelBtn').hide();
                    $('#listBtn').show();

                    alert("저장되었습니다.");
                } else {
                    alert("저장 중 오류가 발생하였습니다.");
                }
            }
        });


        // TODO : gpDoor 출입문 disabled 해제!
        // location.href = "/door/group/detail.do";
    }

    // 수정 취소
    function fnCancel() {
        $(".title_tx").html("출입문 그룹 관리 - 상세");
        $("#btnboxDetail").css("display", "block");
        $("#btnboxEdit").css("display", "none");
        $("#btnEdit").css("display", "none");
        $("[name=detail]").attr("disabled", true);
    }

    // 수정 버튼
    function fnEditMode() {
        $(".title_tx").html("출입문 그룹 관리 - 수정");
        $("#btnEdit").css("display", "block");
        $("#btnboxDetail").css("display", "none");
        $("#btnboxEdit").css("display", "block");
        $("[name=detail]").attr("disabled", false);

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

        let id="1"; // example - doorgroup id

        $.ajax({
            type: "post",
            url: "/door/group/delete/"+id,
            dataType: 'json',
            success: function (data, status) {
                if (data.resultCode == "Y") {
                    location.href = "/door/group/list.do";
                    alert("삭제가 완료되었습니다.");
                } else {
                    alert("삭제 중 오류가 발생하였습니다.");
                }
            }
        });
        // location.href = "/door/group/listView.do"; // 임시 : 저장되었다고 생각하고 list로 돌아감
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
                    <input type="text" id="gpNm" name="detail" maxlength="50" value="${doorGroupDetail.nm}" class="input_com w_600px" disabled>
                </td>
            </tr>
            <tr>
                <th>출입문 스케쥴</th>
                <td>
                    <input type="text" id="gpSchedule" name="detail" maxlength="50" value="3${doorGroupDetail.nm}" class="input_com w_600px" disabled>
                </td>
            </tr>
            <tr>
                <th>출입문</th>
                <td style="display: flex;">
                    <textarea id="gpDoor" name="detail" rows="10" cols="33" class="w_600px"
                              style="border-color: #ccc; border-radius: 2px;
                              font-size: 14px; line-height: 1.5; padding: 2px 10px;" disabled>16동 현관</textarea>
                    <div class="ml_10" style="position: relative;">
                        <button id="btnEdit" type="button" class="btn_small color_basic" style="position: absolute; bottom: 0; width: 80px; display: none;" onclick="openPopup('doorEditPopup')" id="btnSelDoor">출입문 선택</button>
                    </div>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</form>


<div class="right_btn mt_20" id="btnboxDetail">
    <button class="btn_middle color_basic" onclick="location='/door/group/list.do'">목록</button>
    <button class="btn_middle ml_5 color_basic" onclick="fnEditMode();">수정</button>
    <button class="btn_middle ml_5 color_basic" onclick="fnDelete();">삭제</button>
</div>
<div class="right_btn mt_20" id="btnboxEdit" style="display:none;">
<button class="btn_middle ml_5 color_basic" onclick="location='/door/group/list.do'" id="listBtn">목록</button>
    <button class="btn_middle ml_5 color_basic" onclick="fnSave();" id="saveBtn">저장</button>
    <button class="btn_middle ml_5 color_basic" onclick="fnCancel();" id="cancelBtn">취소</button>
</div>
