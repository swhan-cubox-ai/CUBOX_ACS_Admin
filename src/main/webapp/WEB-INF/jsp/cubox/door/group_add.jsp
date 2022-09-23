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
        let popupNm = "";

        if (${editMode eq 'edit'}) {
            $(".title_tx").html("출입문 알람 그룹 - 수정");
            popupNm = "출입문 수정";
        } else {
            $(".title_tx").html("출입문 알람 그룹 - 등록");
            popupNm = "출입문 등록";
        }

        modalPopup("doorListPopup", "출입문 목록", 450, 550);
        modalPopup("doorEditPopup", popupNm, 900, 600);

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

        chkAlType();

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

        // 출입문 알람그룹 명 유효성 체크
        $("#alNm").focusout(function() {
            console.log("이름 input을 벗어남");

            // TODO : 출입문스케쥴명 유효성 체크 (ajax)
        });

        // 유형 - 기본시간
        $("#alType").change(function() {
            console.log("유형");
            console.log(this);
            console.log($(this).val());

            chkAlType();
        });

    });

    // 유형:기본시간 --> 시간 고정
    function chkAlType() {
        if ($("#alType").val() == "default") {
            $("#alTime").val(defaultTime).attr("disabled", true);
        } else {
            $("#alTime").val("").attr("disabled", false);
        }
    }

    // 수정 버튼
    function fnEdit() {
        f = document.detailForm;
        f.action = "/door/group_add.do";
        f.submit();
    }

    // 출입문 저장, 등록
    function fnSave() {
        console.log("fnSave");
        let alNm = $("#alNm").val();
        let alType = $("#alType").val();
        let alTime = $("#alTime").val();
        let alUseYn = $("#alUseYn").val();
        let alDoorCnt = $("#alDoorCnt").val();
        // TODO : 저장할 때 #alTime disabled 된 것 풀어줘야 함.

        // 입력값 유효성 체크
        if (alNm == "") {
            alert("출입문 알람 그룹 명을 입력해주세요.");
            $("#alNm").focus(); return;
        } else if (alType == "") {
            alert("유형을 선택해주세요.");
            $("#alType").focus(); return;
        } else if (alTime == "") {
            alert("시간을 입력해주세요.");
            $("#alTime").focus(); return;
        } else if (alUseYn == "") {
            alert("사용여부를 선택해주세요.");
            $("#alUseYn").focus(); return;
        } else if (alDoorCnt == "" || alDoorCnt == 0) {
            alert("출입문을 선택해주세요.");
            return;
        }

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
        console.log(door);
        console.log(door.html());
        console.log(door.attr("value"));
    }

    // popup open (공통)
    function openPopup(popupNm) {
        $("#" + popupNm).PopupWindow("open");
    }

    // popup close (공통)
    function closePopup(popupNm) {
        $("#" + popupNm).PopupWindow("close");

        if (popupNm == "doorEditPopup") { // 출입문 수정 팝업
            // TODO : 출입문 저장 로직

            let cntDoor = $("#doorSelected").children().length - 1;
            $("#alDoorCnt").val(cntDoor);
        }
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
                    <input type="text" id="alNm" name="alNm" maxlength="50" value="<c:if test="${editMode eq 'edit'}">${alNm}</c:if>" class="input_com w_600px">
                </td>
            </tr>
            <tr>
                <th>유형</th>
                <td>
                    <c:choose>
                        <c:when test="${editMode eq 'edit'}">
                            <select id="alType" name="alType" class="form-control input_com w_600px" value=${alType} style="padding-left:10px;">
                                <option value="">선택</option>
                                <option value="default" selected>기본 시간</option>
                                <option value="setTime">지정시간</option>
                            </select>
                        </c:when>
                        <c:otherwise>
                            <select id="alType" name="alType" class="form-control input_com w_600px" style="padding-left:10px;">
                                <option value="" selected>선택</option>
                                <option value="default">기본 시간</option>
                                <option value="setTime">지정시간</option>
                            </select>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
            <tr>
                <th>시간</th>
                <td>
                    <input type="number" id="alTime" name="alTime" maxlength="10" min="1" value="<c:if test="${editMode eq 'edit'}">${alTime}</c:if>" class="input_com w_600px">&ensp;초
                </td>
            </tr>
            <tr>
                <th>사용</th>
                <td>
                    <c:choose>
                        <c:when test="${editMode eq 'edit'}">
                            <select id="alUseYn" name="alUseYn" class="form-control input_com w_600px" value="${alUseYn}" style="padding-left:10px;">
                                <option value="">선택</option>
                                <option value="yes" selected>Y</option>
                                <option value="no">N</option>
                            </select>
                        </c:when>
                        <c:otherwise>
                            <select id="alUseYn" name="alUseYn" class="form-control input_com w_600px" style="padding-left:10px;">
                                <option value="" selected>선택</option>
                                <option value="yes">Y</option>
                                <option value="no">N</option>
                            </select>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
            <tr>
                <th>출입문 수</th>
                <td>
                    <input type="number" id="alDoorCnt" name="alDoorCnt" maxlength="50"
                           value="<c:if test="${editMode eq 'edit'}">${alDoorCnt}</c:if>" class="input_com w_600px" disabled>&ensp;
                    <button type="button" class="btn_small color_basic" onclick="openPopup('doorListPopup')">출입문 목록</button>
                    <c:choose>
                        <c:when test="${editMode eq 'edit'}">
                            <button type="button" class="btn_small color_basic" onclick="openPopup('doorEditPopup')">출입문 수정</button>
                        </c:when>
                        <c:otherwise>
                            <button type="button" class="btn_small color_basic" onclick="openPopup('doorEditPopup')">출입문 등록</button>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</form>

<div class="right_btn mt_20">
    <c:choose>
        <c:when test="${editMode eq 'edit'}">
            <button class="btn_middle ml_5 color_basic" onClick="fnSave();">저장</button>
        </c:when>
        <c:otherwise>
            <button class="btn_middle ml_5 color_basic" onClick="fnSave();">등록</button>
        </c:otherwise>
    </c:choose>
    <button class="btn_middle ml_5 color_basic" onClick="fnCancel();">취소</button>
</div>


<%--  출입문 목록 modal  --%>
<div id="doorListPopup" class="example_content">
    <div class="popup_box box_w3">

        <%--  테이블  --%>
        <div style="width:100%;">
            <div class="com_box" style="border: 1px solid black; background-color: white; overflow: auto; height: 330px;">
                <table class="tb_list tb_write_02 tb_write_p1">
                    <tbody>
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


<%--  출입문 수정 modal  --%>
<div id="doorEditPopup" class="example_content">
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
<%--  end of 출입문 수정 modal  --%>
