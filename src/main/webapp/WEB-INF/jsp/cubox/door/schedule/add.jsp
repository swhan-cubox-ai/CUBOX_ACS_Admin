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
<jsp:include page="/WEB-INF/jsp/cubox/common/doorGroupPickPopup.jsp" flush="false"/>

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

    // 출입문 스케줄 등록 저장
    function fnAdd() {
        // 입력값 유효성 체크
        if (fnIsEmpty($("#schNm").val())) {
            alert ("출입문 스케쥴 명을 입력하세요.");
            $("#schNm").focus(); return;
        }
        if (confirm("출입문 스케쥴을 저장하시겠습니까?")) {
            fnSaveScheduleAjax();
        } else {
            return;
        }
    }

    // popup open
    function openPopup(popupNm) {
        $("#" + popupNm).PopupWindow("open");
        if (popupNm === "doorGroupPickPopup") {
            fnGetDoorGroupListAjax();
        }
    }

    // popup close
    function closePopup(popupNm) {
        $("#" + popupNm).PopupWindow("close");
        $("input[name='chkGroup']:checked").attr("checked", false);
        $("input[name='chkGroupConf']:checked").attr("checked", false);
        totalCheck();
        userCheck();
    }


    /////////////////  출입문 스케쥴 저장 ajax - start  /////////////////////

    function fnSaveScheduleAjax() {
        let doorSchNm = $("#schNm").val();
        let useYn = $("#schUseYn").val();
        let doorIds = $("#doorIds").val();
        let url = "<c:url value='/door/schedule/save.do'/>";

        console.log(doorSchNm);
        console.log(useYn);
        console.log(doorIds);
        console.log(url);

        $.ajax({
            type : "POST",
            data : {
                nm: doorSchNm,
                doorGroupIds: doorIds
                // ,useYn: useYn
                // ,doorIds: doorIds
            },
            dataType : "json",
            url : url,
            success : function(result) {
                console.log(result);
                if (result.resultCode === "Y" && result.newScheduleId !== "") {
                    alert("등록이 완료되었습니다.");
                    window.location.href = '/door/schedule/detail/' + result.newScheduleId;
                } else {
                    console.log("스케쥴 등록에 실패하였습니다.");
                }
            }
        });

    }
    /////////////////  출입문 스케쥴 저장 ajax - end  /////////////////////

</script>

<form id="addForm" name="addForm" method="post" enctype="multipart/form-data">
    <div class="tb_01_box">
        <table class="tb_write_02 tb_write_p1 box">
            <colgroup>
                <col style="width:10%">
                <col style="width:90%">
            </colgroup>
            <tbody id="tdScheduleAdd">
                <input type="hidden" id="scheduleId" value="${doorScheduleDetail.id}">
                <input type="hidden" id="doorIds" value="">
                <tr>
                    <th>출입문 스케쥴 명</th>
                    <td>
                        <input type="text" id="schNm" name="schNm" maxlength="35" size="50" value='' class="w_600px input_com">
                    </td>
                </tr>
                <tr>
                    <th>사용</th>
                    <td>
                        <select id="schUseYn" name="schUseYn" class="form-control w_600px" style="padding-left:10px;">
                            <option value="" name="selected">선택</option>
                            <option value="Y">사용</option>
                            <option value="N">미사용</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <th>출입문 그룹</th>
                    <td style="display: flex;">
                        <textarea id="doorGroup" name="doorGroup" rows="10" cols="33" class="w_600px" style="border-color: #ccc; border-radius: 2px;
                                    font-size: 14px; line-height: 1.5; padding: 2px 10px;" disabled></textarea>
                        <div class="ml_10" style="position:relative;">
                            <button type="button" class="btn_small color_basic" onclick="openPopup('doorGroupPickPopup')" style="width:60px; position:absolute; bottom:0;">선택</button>
                        </div>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</form>

<div class="right_btn mt_20">
    <button class="btn_middle color_basic" onclick="fnAdd();">저장</button>
    <button class="btn_middle ml_5 color_basic" onclick="location='/door/schedule/list.do'">취소</button>
</div>

