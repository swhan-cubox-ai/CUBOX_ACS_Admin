<%--
  Created by IntelliJ IDEA.
  User: USER
  Date: 2022-09-01
  Time: 오후 3:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:include page="/WEB-INF/jsp/cubox/common/checkPasswd.jsp" flush="false"/>

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
        if (${editMode eq 'edit'}) {
            $(".title_tx").html("출입문 스케쥴 - 수정");
        } else {
            $(".title_tx").html("출입문 스케쥴 - 등록");
        }

        modalPopup("gateAuthPickPopup", "출입문 그룹 선택", 910, 550);

        // 권한그룹 추가
        $("#add_group").click(function() {
            $("input[name=chkGroup]:checked").each(function(i) {
                var tag = "<tr>" + $(this).closest("tr").html() + "</tr>";
                tag = tag.replace("chkGroup", "chkGroupConf");
                $("#tdGroupConf").append(tag);
            });

            var ckd = $("input[name=chkGroup]:checked").length;
            for(var i = ckd - 1; i > -1; i--) {
                $("input[name=chkGroup]:checked").eq(i).closest("tr").remove();
            }

            totalCheck();
        });

        // 권한그룹 삭제
        $("#delete_group").click(function() {
            $("input[name=chkGroupConf]:checked").each(function(i) {
                var tag = "<tr>" + $(this).closest("tr").html() + "</tr>";
                tag = tag.replace("chkGroupConf", "chkGroup");
                $("#tdGroupTotal").append(tag);
            });

            var ckd = $("input[name=chkGroupConf]:checked").length;
            for (var i = ckd - 1; i > -1; i--) {
                $("input[name=chkGroupConf]:checked").eq(i).closest("tr").remove();
            }

            userCheck();
        });

        // 전체 선택(왼쪽)
        $("#totalGroupCheckAll").click(function() {
            if($("#totalGroupCheckAll").prop("checked")) {
                $("input[name=chkGroup]").prop("checked", true);
            } else {
                $("input[name=chkGroup]").prop("checked", false);
            }
        });

        // 전체 선택(오른쪽)
        $("#userGroupCheckAll").click(function() {
            if ($("#userGroupCheckAll").prop("checked")) {
                $("input[name=chkGroupConf]").prop("checked", true);
            } else {
                $("input[name=chkGroupConf]").prop("checked", false);
            }
        });

    });

    // 출입문그룹 선택 저장
    function fnGroupSave() {
        var gateGroup = [];
        $("input[name=chkGroupConf]").each(function(i) { // 체크된 것 말고 오른쪽 박스에 들어온 모든 항목
            var auth = $(this).closest("tr").children().eq(1).html();
            gateGroup.push(auth);
        });
        console.log(gateGroup);

        // 권한그룹 textarea에 뿌려주기
        $("#gateGroup").val(gateGroup.join("\r\n"));
        closePopup("gateAuthPickPopup");
    }

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
        location.href = "/gate/schedule.do";
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
        //             location.href = "/gate/schedule.do";
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
        $("#addForm").attr("action", "/gate/schedule.do");
        $("#addForm").submit();
    }

    function totalCheck() {
        if ($("#totalGroupCheckAll").prop("checked")) {
            $("#totalGroupCheckAll").prop("checked", false);
        }
    }

    function userCheck() {
        if ($("#userGroupCheckAll").prop("checked")) {
            $("#userGroupCheckAll").prop("checked", false);
        }
    }

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
                    <c:choose>
                        <c:when test="${editMode eq 'edit'}">
                            <input type="text" id="schName" name="schName" maxlength="50" size="50" value="${schName}" class="w_600px input_com">
                        </c:when>
                        <c:otherwise>
                            <input type="text" id="schName" name="schName" maxlength="50" size="50" value="" class="w_600px input_com">
                        </c:otherwise>
                    </c:choose>
                    </td>
                </tr>
                <tr>
                    <th>사용</th>
                    <td>
                    <c:choose>
                    <c:when test="${editMode eq 'edit'}">
                        <select id="schUseYn" name="schUseYn" class="form-control w_600px" value="${schUseYn}" style="padding-left:10px;">
                            <option value="">선택</option>
                            <option value="yes" selected>Y</option>
                            <option value="no">N</option>
                        </select>
                    </c:when>
                    <c:otherwise>
                        <select id="schUseYn" name="schUseYn" class="form-control w_600px" style="padding-left:10px;">
                            <option value="" name="selected">선택</option>
                            <option value="yes">Y</option>
                            <option value="no">N</option>
                        </select>
                    </c:otherwise>
                    </c:choose>
                    </td>
                </tr>
                <tr>
                    <th>출입문 그룹</th>
                    <td style="display: flex;">
                    <c:choose>
                    <c:when test="${editMode eq 'edit'}">
                        <textarea id="gateGroup" name="gateGroup" rows="10" cols="33" style="border-color: #ccc; border-radius: 2px; width: 95%; min-width: 95%;
                                  font-size: 14px; line-height: 1.5; padding: 2px 10px;">${gateGroup}</textarea>
                    </c:when>
                    <c:otherwise>
                            <textarea id="gateGroup" name="gateGroup" rows="10" cols="33" style="border-color: #ccc; border-radius: 2px; width: 95%; min-width: 95%;
                            font-size: 14px; line-height: 1.5; padding: 2px 10px;">${gateGroup}</textarea>
                    </c:otherwise>
                    </c:choose>
                        <div style="width: 15%; text-align: center;">
                            <button type="button" class="btn_middle color_basic" onclick="openPopup('gateAuthPickPopup')">선택</button>
                        </div>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</form>

<div class="right_btn mt_20">
    <button class="btn_middle color_basic" onClick="fnAdd();">확인</button>
    <button class="btn_middle ml_5 color_basic" onClick="fnCancel();">취소</button>
</div>


<%--  출입문그룹 선택 modal  --%>
<div id="gateAuthPickPopup" class="example_content">
    <div class="popup_box box_w3">

        <%--  왼쪽 box  --%>
        <div style="width:45%;">
            <div class="com_box" style="border: 1px solid black; background-color: white; overflow: auto; height: 330px;">
                <table class="tb_list tb_write_02 tb_write_p1">
                    <colgroup>
                        <col style="width:15%">
                        <col style="width:20%">
                        <col style="width:65%">
                    </colgroup>
                    <thead>
                    <tr>
                        <th><input type="checkbox" id="totalGroupCheckAll"></th>
                        <th>권한코드</th>
                        <th>출입문그룹</th>
                    </tr>
                    </thead>
                    <tbody id="tdGroupTotal">
                    <c:forEach var="i" begin="1" end="10" varStatus="status">
                        <tr>
                            <td style="padding: 0 14px;"><input type="checkbox" name="chkGroup"/></td>
                            <td>CUBOX123</td>
                            <td>출입문그룹 A</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
        <%--  end of 왼쪽 box  --%>

        <%--  화살표 이동  --%>
        <div class="box_w3_2" style="height: 330px;">
            <div class="btn_box">
                <img src="/img/ar_r.png" alt="" id="add_group"/>
            </div>
            <div class="btn_box">
                <img src="/img/ar_l.png" alt="" id="delete_group"/>
            </div>
        </div>
        <%--  end of 화살표 이동  --%>

        <%--  오른쪽 box  --%>
        <div style="width:45%;">
            <%--  테이블  --%>
            <div class="com_box" style="border: 1px solid black; background-color: white; overflow: auto; height: 330px;">
                <table class="tb_list tb_write_02 tb_write_p1">
                    <colgroup>
                        <col style="width:15%">
                        <col style="width:20%">
                        <col style="width:65%">
                    </colgroup>
                    <thead>
                    <tr>
                        <th><input type="checkbox" id="userGroupCheckAll"></th>
                        <th>권한코드</th>
                        <th>출입문그룹</th>
                    </tr>
                    </thead>
                    <tbody id="tdGroupConf">
                    <tr>
                        <td style="padding: 0 14px;"><input type="checkbox" name="chkGroupConf"/></td>
                        <td>CUBOX123</td>
                        <td>출입문그룹 A</td>
                    </tr>
                    <tr>
                        <td style="padding: 0 14px;"><input type="checkbox" name="chkGroupConf"/></td>
                        <td>CUBOX123</td>
                        <td>출입문그룹 B</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <%--  end of 오른쪽 box  --%>

        <div class="c_btnbox center mt_30">
            <div style="display: inline-block;">
                <button type="button" class="comm_btn mr_20" onclick="fnGroupSave();">확인</button>
                <button type="button" class="comm_btn" onclick="closePopup('gateAuthPickPopup');">취소</button>
            </div>
        </div>
    </div>
</div>
<%--  end of 출입문그룹 선택 modal  --%>