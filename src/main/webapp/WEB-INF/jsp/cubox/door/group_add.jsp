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

<script type="text/javascript" src="/js/dTree/dtree.js"></script>
<script type="text/javascript">

    const defaultTime = 60; // 기본 시간 설정

    $(function() {

        if (${editMode eq 'edit'}) {
            $(".title_tx").html("출입문 그룹 관리 - 수정");
        } else {
            $(".title_tx").html("출입문 그룹 관리 - 등록");
            $("#gpNm").focus();
        }

        modalPopup("doorEditPopup", "출입문 선택", 900, 600);

        d = new dTree('d'); //dtree선언
        d.add("root", -1, '사업장'); //최상위 루트, 참조가 없기때문에 -1
        d.add("node_1", "root", '사업장 1');
        d.add("node_1_1", "node_1", '<span onclick="javascript:selectDoor(this);" value="사업장 > 사업장1 > 사업장1_1">사업장 1_1</span>', '#');
        d.add("node_2", "root", '사업장 2');
        d.add("node_2_1", "node_2", '사업장 2_1');
        d.add("node_2_1_1", "node_2_1", '사업장 2_1_1');
        d.add("node_2_1_1_1", "node_2_1_1", '<span onclick="javascript:selectDoor(this);" value="사업장 > 사업장2 > 사업장2_1 > 사업장2_1_1 > 사업장2_1_1_1">사업장 2_1_1_1</span>', '#');
        d.add("node_2_1_1_2", "node_2_1_1", '<span onclick="javascript:selectDoor(this);" value="사업장 > 사업장2 > 사업장2_1 > 사업장2_1_1 > 사업장2_1_1_2">사업장 2_1_1_2</span>', '#');
        d.add("node_3", "root", '사업장 3');
        d.add("node_3_1", "node_3", '사업장 3_1');
        d.add("node_3_1_1", "node_3_1", '사업장 3_1_1');
        d.add("node_3_1_1_1", "node_3_1_1", '<span onclick="javascript:selectDoor(this);" value="사업장 > 사업장3 > 사업장3_1 > 사업장3_1_1 > 사업장3_1_1_1">사업장 3_1_1_1</span>', '#');
        d.add("node_3_1_1_2", "node_3_1_1", '<span onclick="javascript:selectDoor(this);" value="사업장 > 사업장3 > 사업장3_1 > 사업장3_1_1 > 사업장3_1_1_2">사업장 3_1_1_2</span>', '#');
        d.add("node_3_1_1_3", "node_3_1_1", '<span onclick="javascript:selectDoor(this);" value="사업장 > 사업장3 > 사업장3_1 > 사업장3_1_1 > 사업장3_1_1_3">사업장 3_1_1_3</span>', '#');
        d.add("node_3_1_1_4", "node_3_1_1", '<span onclick="javascript:selectDoor(this);" value="사업장 > 사업장3 > 사업장3_1 > 사업장3_1_1 > 사업장3_1_1_4">사업장 3_1_1_4</span>', '#');
        d.add("node_4", "root", '사업장 4');
        d.add("node_4_1", "node_4", '<span onclick="javascript:selectDoor(this);" value="사업장 > 사업장4 > 사업장4_1">사업장 4_1</span>', '#');
        d.add("node_4_2", "node_4", '<span onclick="javascript:selectDoor(this);" value="사업장 > 사업장4 > 사업장4_2">사업장 4_2</span>', '#');
        d.add("node_4_3", "node_4", '<span onclick="javascript:selectDoor(this);" value="사업장 > 사업장4 > 사업장4_3">사업장 4_3</span>', '#');
        d.add("node_4_4", "node_4", '<span onclick="javascript:selectDoor(this);" value="사업장 > 사업장4 > 사업장4_4">사업장 4_4</span>', '#');
        d.add("node_4_5", "node_4", '사업장 4_5');
        d.add("node_4_5_1", "node_4_5", '<span onclick="javascript:selectDoor(this);" value="사업장 > 사업장4 > 사업장4_5 > 사업장4_5_1">사업장 4_5_1</span>', '#');
        d.add("node_4_5_2", "node_4_5", '<span onclick="javascript:selectDoor(this);" value="사업장 > 사업장4 > 사업장4_5 > 사업장4_5_2">사업장 4_5_2</span>', '#');
        d.add("node_4_5_3", "node_4_5", '<span onclick="javascript:selectDoor(this);" value="사업장 > 사업장4 > 사업장4_5 > 사업장4_5_3">사업장 4_5_3</span>', '#');
        d.add("node_4_5_4", "node_4_5", '사업장 4_5_4');
        d.add("node_4_5_4_1", "node_4_5_4", '<span onclick="javascript:selectDoor(this);" value="사업장 > 사업장4 > 사업장4_5 > 사업장4_5_4 > 사업장4_5_4_1">사업장 4_5_4_1</span>', '#');
        d.add("node_4_5_4_2", "node_4_5_4", '<span onclick="javascript:selectDoor(this);" value="사업장 > 사업장4 > 사업장4_5 > 사업장4_5_4 > 사업장4_5_4_2">사업장 4_5_4_2</span>', '#');
        d.add("node_4_5_4_3", "node_4_5_4", '<span onclick="javascript:selectDoor(this);" value="사업장 > 사업장4 > 사업장4_5 > 사업장4_5_4 > 사업장4_5_4_3">사업장 4_5_4_3</span>', '#');
        d.add("node_4_5_4_4", "node_4_5_4", '<span onclick="javascript:selectDoor(this);" value="사업장 > 사업장4 > 사업장4_5 > 사업장4_5_4 > 사업장4_5_4_4">사업장 4_5_4_4</span>', '#');
        d.add("node_4_5_4_5", "node_4_5_4", '<span onclick="javascript:selectDoor(this);" value="사업장 > 사업장4 > 사업장4_5 > 사업장4_5_4 > 사업장4_5_4_5">사업장 4_5_4_5</span>', '#');
        d.add("node_5", "root", '사업장 5');
        d.add("node_5_1", "node_5", '<span onclick="javascript:selectDoor(this);" value="사업장 > 사업장5 > 사업장5_1">사업장 5_1</span>', '#');

        $("#treeDiv").html(d.toString());
        d.openAll();

        // 출입문 추가
        $("#add_door").click(function() {
            let nodeSel = $(".nodeSel").children(); // span
            let path = nodeSel.attr("value");
            console.log(nodeSel.html()); // door name

            let doorSelected = $("#doorSelected").children();

            // 이미 같은 출입문 있을 경우 return
            for (let i = 1; i < doorSelected.length; i++) {

                let doorPath = doorSelected.eq(i).children().last().html().replaceAll("&gt;", ">");
                if (doorPath == path) { // TODO : id로 비교?
                    return;
                }
            }

            let tag = "<tr><td><input type='checkbox' name='chkDoorConf'></td><td>" + path + "</td></tr>";
            $("#doorSelected").append(tag);
        });

        // 출입문 삭제
        $("#delete_door").click(function() {
            let ckd = $("input[name=chkDoorConf]:checked").length;
            for (let i = ckd -1; i > -1; i--) {
                $("input[name=chkDoorConf]:checked").eq(i).closest("tr").remove();
            }

            if ($("#chkDoorConfAll").prop("checked")) {
                $("#chkDoorConfAll").prop("checked", false);
            }
        });

        $("#chkDoorConfAll").click(function() {
            if ($("#chkDoorConfAll").prop("checked")) {
                $("input[name=chkDoorConf]").prop("checked", true);
            } else {
                $("input[name=chkDoorConf]").prop("checked", false);
            }
        });

        // 출입문 그룹 명 유효성 체크
        $("#gpNm").focusout(function() {
            console.log("이름 input을 벗어남");

            // TODO : 출입문 그룹 명 유효성 체크 (ajax)
        });

    });

    // 수정 버튼
    function fnEdit() {
        f = document.detailForm;
        f.action = "/door/group_add.do";
        f.submit();
    }

    // 출입문 저장, 등록
    function fnSave() {
        console.log("fnSave");
        let gpNm = $("#gpNm").val();
        let gpSchedule = $("#gpSchedule").val();
        let gpDoor = $("#gpDoor").val();
        console.log(gpNm);

        // 입력값 유효성 체크
        if (fnIsEmpty(gpNm)) {
            alert("출입문 그룹 명을 입력해주세요.");
            $("#gpNm").focus(); return;
        } else if (fnIsEmpty(gpSchedule)) {
            alert("출입문 스케쥴을 선택해주세요.");
            $("#gpSchedule").focus(); return;
        } else if (fnIsEmpty(gpDoor)) {
            alert("출입문을 선택해주세요.");
            return;
        }

        // TODO : gpDoor 출입문 disabled 해제!
        location.href = "/door/group_detail.do";

    }

    // 알람그룹 수정 취소
    function fnCancel() {
        if (${editMode eq 'edit'}) {
            $("#detailForm").attr("action", "/door/group_detail.do");
        } else {
            $("#detailForm").attr("action", "/door/group.do");
        }
        $("#detailForm").submit();
    }

    // 출입문 선택
    function selectDoor(self) {
        let door = $(self);
        console.log(door.attr("value"));
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
    <input type="hidden" id="editMode" name="editMode" value="edit"/>
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
    <button class="btn_middle ml_5 color_basic" onClick="fnSave();">등록</button>
    <button class="btn_middle ml_5 color_basic" onClick="fnCancel();">취소</button>
</div>


<%--  출입문 선택 modal  --%>
<div id="doorEditPopup" class="example_content" style="display: none;">
    <div class="popup_box box_w3">
        <div style="width:45%;">
            <div class="com_box" style="border: 1px solid black; background-color: white; overflow: auto; height: 400px; padding: 2px 10px;">
                <div id="treeDiv"></div>
            </div>
        </div>

        <%--  화살표 이동  --%>
        <div style="height: 400px; display: flex; justify-content: center; flex-wrap: wrap; flex-direction: column; align-items: center;">
            <div class="btn_box" style="margin:5px 0;">
                <img src="/img/ar_r.png" alt="" id="add_door"/>
            </div>
            <div class="btn_box" style="margin:5px 0;">
                <img src="/img/ar_l.png" alt="" id="delete_door"/>
            </div>
        </div>
        <%--  end of 화살표 이동  --%>

        <%--  테이블  --%>
        <div style="width:45%;">
            <div class="com_box" style="border: 1px solid black; background-color: white; overflow: auto; height: 400px;">
                <table class="tb_list tb_write_02 tb_write_p1">
                    <colgroup>
                        <col style="width:10%">
                        <col style="width:90%">
                    </colgroup>
                    <tbody id="doorSelected">
                    <tr>
                        <th><input type="checkbox" id="chkDoorConfAll"></th>
                        <th>출입문</th>
                    </tr>
                    <c:if test="${editMode eq 'edit'}">${schName}
                        <tr>
                            <td><input type="checkbox" name="chkDoorConf"></td>
                            <td>12동 > C구역 > 1층 > 현관 출입문</td>
                        </tr>
                        <tr>
                            <td><input type="checkbox" name="chkDoorConf"></td>
                            <td>12동 > D구역 > 2층 > 계단</td>
                        </tr>
                    </c:if>

                    </tbody>
                </table>
            </div>
        </div>
        <%--  end of 테이블  --%>

        <div class="c_btnbox center mt_30">
            <div style="display: inline-block;">
                <button type="button" class="comm_btn mr_20" onclick="closePopup('doorEditPopup');">확인</button>
            </div>
        </div>
    </div>
</div>
<%--  end of 출입문 선택 modal  --%>
