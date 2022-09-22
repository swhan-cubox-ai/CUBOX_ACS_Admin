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
        if (${editMode eq 'edit'}) {
            $(".title_tx").html("출입문 알람 그룹 - 수정");
        } else {
            $(".title_tx").html("출입문 알람 그룹 - 등록");
        }
        modalPopup("doorListPopup", "출입문 목록", 450, 550);
        modalPopup("doorEditPopup", "출입문 목록", 750, 600);

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
        d.add("node_4_5_5", "node_4_5", '<span onclick="javascript:selectDoor(this);" value="사업장 > 사업장4 > 사업장4_5 > 사업장4_5_5">사업장 4_5_5</span>', '#');
        d.add("node_4_5_6", "node_4_5", '<span onclick="javascript:selectDoor(this);" value="사업장 > 사업장4 > 사업장4_5 > 사업장4_5_6">사업장 4_5_6</span>', '#');
        d.add("node_5", "root", '사업장 5');
        d.add("node_5_1", "node_5", '<span onclick="javascript:selectDoor(this);" value="사업장 > 사업장5 > 사업장5_1">사업장 5_1</span>', '#');

        $("#treeDiv").html(d.toString());
        d.openAll();

        // 출입문 추가
        $("#add_door").click(function() {
            let nodeSel = $(".nodeSel").children(); // span
            let path = nodeSel.attr("value");
            console.log(nodeSel.html()); // door name
            console.log(path); // path

            let doorSelected = $("#doorSelected").children();

            for (let i = 0; i < doorSelected.length; i++) {
                let doorPath = doorSelected.eq(i).children().html().replaceAll("&gt;", ">");
                if (doorPath == path) {
                    return;
                }
            }

            let tag = "<tr><td>" + path + "</td></tr>";
            $("#doorSelected").append(tag);

            //////// 같은 출입문 없을 때, /////////

            // $("input[name=chkAuth]:checked").each(function(i) {
            //     var tag = "<tr>" + $(this).closest("tr").html() + "</tr>";
            //     tag = tag.replace("chkAuth", "chkAuthConf");
            //     $("#tdAuthConf").append(tag);
            // });
            //
            // var ckd = $("input[name=chkAuth]:checked").length;
            // for(var i = ckd - 1; i > -1; i--) {
            //     $("input[name=chkAuth]:checked").eq(i).closest("tr").remove();
            // }
            //
            // totalCheck();
        });

        // 출입문 삭제
        $("#delete_door").click(function() {
            // $("input[name=chkAuthConf]:checked").each(function(i) {
            //     var tag = "<tr>" + $(this).closest("tr").html() + "</tr>";
            //     tag = tag.replace("chkAuthConf", "chkAuth");
            //     $("#tdAuthTotal").append(tag);
            // });
            //
            // var ckd = $("input[name=chkAuthConf]:checked").length;
            // for(var i = ckd - 1; i > -1; i--) {
            //     $("input[name=chkAuthConf]:checked").eq(i).closest("tr").remove();
            // }
            //
            // userCheck();
        });
    });

    // 수정 버튼
    function fnEdit() {
        f = document.detailForm;
        f.action = "/door/alarm_add.do";
        f.submit();
    }

    // 알람그룹 수정 취소
    function fnCancel() {
        if (${editMode eq 'edit'}) {
            $("#addForm").attr("action", "/door/alarm_detail.do");
        } else {
            $("#addForm").attr("action", "/door/alarm.do");
        }
        $("#addForm").submit();
    }

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
                    <input type="text" id="alNm" name="alNm" maxlength="50" value="작업자 통로" class="input_com w_600px">
                </td>
            </tr>
            <tr>
                <th>유형</th>
                <td>
                    <select id="alType" name="alType" class="form-control input_com w_600px" style="padding-left:10px;">
                        <option value="" name="selected">선택</option>
                        <option value="defaultTime" selected>기본 시간</option>
                        <option value="setTime">지정시간</option>
                    </select>
                </td>
            </tr>
            <tr>
                <th>시간</th>
                <td>
                    <input type="text" id="alTime" name="alTime" maxlength="10" value="30" class="input_com w_600px">&ensp;초
                </td>
            </tr>
            <tr>
                <th>사용</th>
                <td>
                    <select id="alUseYn" name="alUseYn" class="form-control input_com w_600px" style="padding-left:10px;">
                        <option value="" name="selected">선택</option>
                        <option value="yes" selected>Y</option>
                        <option value="no">N</option>
                    </select>
                </td>
            </tr>
            <tr>
                <th>출입문 수</th>
                <td>
                    <input type="text" id="alDoorCnt" name="alDoorCnt" maxlength="50" value="2" class="input_com w_600px">&ensp;
                    <button type="button" class="btn_small color_basic" onclick="openPopup('doorListPopup')">출입문 목록</button>
                    <button type="button" class="btn_small color_basic" onclick="openPopup('doorEditPopup')">출입문 수정</button>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</form>

<div class="right_btn mt_20">
    <button class="btn_middle ml_5 color_basic" onClick="fnAdd();">등록</button>
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
            <div class="com_box" style="border: 1px solid black; background-color: white; overflow: auto; height: 400px;">
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
                    <tbody id="doorSelected">
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
<%--  end of 출입문 수정 modal  --%>

