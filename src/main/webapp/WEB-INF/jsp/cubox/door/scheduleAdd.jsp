<%--
  Created by IntelliJ IDEA.
  User: USER
  Date: 2022-09-01
  Time: 오후 3:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="/WEB-INF/jsp/cubox/common/checkPasswd.jsp" flush="false"/>
<jsp:include page="/jsp/cubox/common/doorGroupPick.jsp" flush="false"/>

<style>
    .title_box {
        margin-top: 10px;
        margin-bottom: 20px;
    }
    .box {
        border: 1px solid #ccc;
    }
    #tdScheduleAdd tr th {
        text-align: center;
    }
    thead {
        position: sticky;
        top: 0;
    }
</style>

<script type="text/javascript">
    $(function() {
        $(".title_tx").html("출입문 스케쥴 - 등록");
        modalPopup("doorGroupPickPopup", "출입문 그룹 선택", 910, 550);
    });

    // 출입문그룹 선택 저장
    // function fnGroupSave() {
    //     var gateGroup = [];
    //     $("input[name=chkGroupConf]").each(function(i) { // 체크된 것 말고 오른쪽 박스에 들어온 모든 항목
    //         var auth = $(this).closest("tr").children().eq(1).html();
    //         gateGroup.push(auth);
    //     });
    //     console.log(gateGroup);
    //
    //     // 권한그룹 textarea에 뿌려주기
    //     $("#gateGroup").val(gateGroup.join("\r\n"));
    //     closePopup("doorGroupPickPopup");
    // }

    // 출입문 스케줄 등록 저장
    function fnAdd() {
        let sName = $("#schName");
        let sUseYn = $("#schUseYn");
        let sGroup = $("#gateGroup");

        if (fnIsEmpty(sName.val())) {
            alert ("출입문 스케쥴 명을 입력하세요.");
            sName.focus();
            return;
        }
        // 이미 등록된 스케쥴 이름일 경우,
        if (sName.val() == "testName") {
            alert("이미 등록된 출입문 스케쥴 명 입니다.");
            sName.val("");
            sName.focus();
            return;
        }

        // 데이터 저장
        // 출입문 목록으로 페이지 이동
        location.href = "/door/schedule/detail.do";

        // $.ajax({
        //     url: "gate/schedule_save.do",
        //     type: "POST",
        //     data: {
        //         "sName": sName,
        //         "sUseYn": sUseYn,
        //         "sGroup": sGroup
        //     },
        //     dataType: "json",
        //     success: function(data) {
        //         if(data.result == "1") {
        //             location.href = "/door/schedule.do";
        //         } else {
        //             alert(data.message);
        //         }
        //         return;
        //     },
        //     error: function (jqXHR) {
        //         alert("저장에 실패했습니다.");
        //         return;
        //     }
        // });

    }

    // 출입문 스케줄 등록 취소
    function fnCancel() {
        if (${editMode eq 'edit'}) { //
            $("#addForm").attr("action", "/door/schedule/detail.do");
            $("#addForm").submit();
        } else {
            // $("#addForm").attr("action", "/door/schedule.do");
            location.href = "/door/schedule.do";
        }

    }

    // function totalCheck() {
    //     if ($("#totalGroupCheckAll").prop("checked")) {
    //         $("#totalGroupCheckAll").prop("checked", false);
    //     }
    // }
    //
    // function userCheck() {
    //     if ($("#userGroupCheckAll").prop("checked")) {
    //         $("#userGroupCheckAll").prop("checked", false);
    //     }
    // }

    // popup open
    function openPopup(popupNm) {
        $("#" + popupNm).PopupWindow("open");
    }

    // popup close
    function closePopup(popupNm) {
        $("#" + popupNm).PopupWindow("close");
        $("input[name='chkGroup']:checked").attr("checked", false);
        $("input[name='chkGroupConf']:checked").attr("checked", false);
        totalCheck();
        userCheck();
    }
</script>

<form id="addForm" name="addForm" method="post" enctype="multipart/form-data">
    <input type="hidden" id="editMode" name="editMode" value="edit"/>
    <div class="tb_01_box">
        <table class="tb_write_02 tb_write_p1 box">
            <colgroup>
                <col style="width:10%">
                <col style="width:90%">
            </colgroup>
            <tbody id="tdScheduleAdd">
                <tr>
                    <th>출입문 스케쥴 명</th>
                    <td>
                        <input type="text" id="schName" name="schName" maxlength="50" size="50"
                               value='' class="w_600px input_com">
                    </td>
                </tr>
                <tr>
                    <th>사용</th>
                    <td>
                        <select id="schUseYn" name="schUseYn" class="form-control w_600px" style="padding-left:10px;">
                            <option value="" name="selected">선택</option>
                            <option value="yes">Y</option>
                            <option value="no">N</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <th>출입문 그룹</th>
                    <td style="display: flex;">
                        <textarea id="gateGroup" name="gateGroup" rows="10" cols="33" class="w_600px" style="border-color: #ccc; border-radius: 2px;
                                    font-size: 14px; line-height: 1.5; padding: 2px 10px;" disabled></textarea>
                        <div class="ml_10" style="position:relative;">
                            <button type="button" class="btn_middle color_basic" onclick="openPopup('doorGroupPickPopup')" style="position:absolute; bottom:0;">선택</button>
                        </div>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</form>

<div class="right_btn mt_20">
    <button class="btn_middle color_basic" onclick="fnAdd();">확인</button>
    <button class="btn_middle ml_5 color_basic" onclick="fnCancel();">취소</button>
</div>

