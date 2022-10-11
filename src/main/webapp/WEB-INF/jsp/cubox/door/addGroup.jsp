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

    $(function() {

        $(".title_tx").html("출입문 그룹 관리 - 등록");
        $("#gpNm").focus();

        modalPopup("doorEditPopup", "출입문 선택", 900, 600);

        // // dTree
        // let fnName = "selectDoor(this);";
        // createTree($("#treeDiv"), fnName);
        //
        // // 출입문 추가
        // $("#add_door").click(function() {
        //     let nodeSel = $(".nodeSel").children(); // span
        //     let path = nodeSel.attr("value");
        //     console.log(nodeSel.html()); // door name
        //
        //     let doorSelected = $("#doorSelected").children();
        //
        //     // 이미 같은 출입문 있을 경우 return
        //     for (let i = 1; i < doorSelected.length; i++) {
        //
        //         let doorPath = doorSelected.eq(i).children().last().html().replaceAll("&gt;", ">");
        //         if (doorPath == path) { // TODO : id로 비교?
        //             return;
        //         }
        //     }
        //
        //     let tag = "<tr><td><input type='checkbox' name='chkDoorConf'></td><td>" + path + "</td></tr>";
        //     $("#doorSelected").append(tag);
        // });
        //
        // // 출입문 삭제
        // $("#delete_door").click(function() {
        //     let ckd = $("input[name=chkDoorConf]:checked").length;
        //     for (let i = ckd -1; i > -1; i--) {
        //         $("input[name=chkDoorConf]:checked").eq(i).closest("tr").remove();
        //     }
        //
        //     if ($("#chkDoorConfAll").prop("checked")) {
        //         $("#chkDoorConfAll").prop("checked", false);
        //     }
        // });
        //
        // $("#chkDoorConfAll").click(function() {
        //     if ($("#chkDoorConfAll").prop("checked")) {
        //         $("input[name=chkDoorConf]").prop("checked", true);
        //     } else {
        //         $("input[name=chkDoorConf]").prop("checked", false);
        //     }
        // });

        // 출입문 그룹 명 유효성 체크
        $("#gpNm").focusout(function() {
            console.log("이름 input을 벗어남");

            // TODO : 출입문 그룹 명 유효성 체크 (ajax)
        });

    });

    // 수정 버튼
    // function fnEdit() {
    //     f = document.detailForm;
    //     f.action = "/door/group/add.do";
    //     f.submit();
    // }

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

        // TODO : gpDoor 출입문 disabled 해제!
        location.href = "/door/group/detail.do";

    }

    // 출입문 선택
    function selectDoor(self) {
        let door = $(self);
        console.log("selectDoor");
    }

    // popup open (공통)
    function openPopup(popupNm) {
        $("#" + popupNm).PopupWindow("open");
    }

    // popup close (공통)
    function closePopup(popupNm) {
        let doorGroup = [];

        $("input[name=chkDoorConf]").each(function(i) {
            let chkDoor = $(this).closest("tr").children().eq(1).html().replaceAll("&gt;", ">");
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
                    <input type="text" id="gpNm" name="gpNm" maxlength="50" value="<c:if test="${editMode eq 'edit'}">3동 현관</c:if>" class="input_com w_600px">
                </td>
            </tr>
            <tr>
                <th>출입문 스케쥴</th>
                <td>
                <c:choose>
                    <c:when test="${editMode eq 'edit'}">
                        <select id="gpSchedule" name="gpSchedule" class="form-control input_com w_600px" value=${alType} style="padding-left:10px;">
                            <option value="">선택</option>
                            <option value="default" selected>3동 평일</option>
                            <option value="setTime">3동 주말</option>
                        </select>
                    </c:when>
                    <c:otherwise>
                        <select id="gpSchedule" name="gpSchedule" class="form-control input_com w_600px" style="padding-left:10px;">
                            <option value="" selected>선택</option>
                            <option value="default">3동 평일</option>
                            <option value="setTime">3동 주말</option>
                        </select>
                    </c:otherwise>
                </c:choose>
                </td>
            </tr>
            <tr>
                <th>출입문</th>
                <td style="display: flex;">
                    <textarea id="gpDoor" name="gpDoor" rows="10" cols="33" class="w_600px"
                              style="border-color: #ccc; border-radius: 2px;
                              font-size: 14px; line-height: 1.5; padding: 2px 10px;" disabled><c:if test="${editMode eq 'edit'}">16동 현관</c:if></textarea>
                    <div class="ml_10" style="position: relative;">
                        <button type="button" class="btn_small color_basic" style="position: absolute; bottom: 0; width: 80px;" onclick="openPopup('doorEditPopup')">출입문 선택</button>
                    </div>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</form>

<div class="right_btn mt_20">
    <button class="btn_middle ml_5 color_basic" onclick="fnSave();">등록</button>
    <button class="btn_middle ml_5 color_basic" onclick="location='/door/group/listView.do'">취소</button>
</div>


