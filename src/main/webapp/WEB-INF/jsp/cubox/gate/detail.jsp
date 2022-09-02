<%--
  Created by IntelliJ IDEA.
  User: USER
  Date: 2022-09-02
  Time: 오후 3:07
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
    #tdScheduleDetail tr th {
        text-align: center;
    }
    thead {
        position: sticky;
        top: 0;
    }
</style>

<script type="text/javascript">
    $(function() {
        $(".title_tx").html("출입문 스케쥴 - 상세");
    });

    // 목록 버튼
    function fnList() {
        // 출입문 목록으로 페이지 이동
        location.href = "/gate/schedule.do";
    }

    // 수정 버튼
    function fnEdit() {
        // location.href = "/gate/schedule_add.do?mode=edit";
        f = document.detailForm;
        f.action = "/gate/schedule_add.do";
        $("input[name=schName]").attr("disabled", false);
        $("select[name=schUseYn]").attr("disabled", false);
        $("textarea[name=gateGroup]").attr("disabled", false);
        f.submit();
    }

    // 삭제 버튼
    function fnDelete() {
        // 출입문 그룹에 그룹이 있으면,
        if ($("gateGroup").html() == "") {
            alert("연결된 출입문 그룹을 해제 후 삭제 하시기 바랍니다.");
            return;
        }

        if (!confirm("삭제하시겠습니까?")) {
            return;
        }
        location.href = "/gate/schedule_delete.do";

        // $.ajax({
        //     type : "post",
        //     url  : "/gate/schedule_delete.do",
        //     data : {
        //        "id" = id
        //     },
        //     dataType :'json',
        //     success  : function(data, status) {
        //         if(data.result == "Y"){
        //             location.href = "/gate/schedule.do";
        //         } else {
        //             alert("삭제 중 오류가 발생하였습니다.");
        //         }
        //     }
        // });
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
            <tbody id="tdScheduleDetail">
            <tr>
                <th>출입문 스케쥴 명</th>
                <td>
                    <input type="text" id="schName" name="schName" maxlength="50" size="50" value="16동 현관 출입문 남" class="w_600px input_com" disabled>
                </td>
            </tr>
            <tr>
                <th>사용</th>
                <td>
                    <select id="schUseYn" name="schUseYn" class="form-control w_600px" style="padding-left:10px;" disabled>
                        <option value="" name="selected">선택</option>
                        <option value="yes" selected>Y</option>
                        <option value="no">N</option>
                    </select>
                </td>
            </tr>
            <tr>
                <th>출입문 그룹</th>
                <td style="display: flex;">
                    <textarea id="gateGroup" name="gateGroup" rows="10" cols="33"
                              style="border-color: #ccc; border-radius: 2px; width: 95%; min-width: 95%;
                              font-size: 14px; line-height: 1.5; padding: 2px 10px;" disabled>출입문 그룹 A</textarea>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</form>

<div class="right_btn mt_20">
    <button class="btn_middle color_basic" onClick="fnList();">목록</button>
    <button class="btn_middle ml_5 color_basic" onClick="fnEdit();">수정</button>
    <button class="btn_middle ml_5 color_basic" onClick="fnDelete();">삭제</button>
    <button class="btn_middle ml_5 color_basic" onClick="fnAddByDay();">요일 별 스케쥴 등록</button>
</div>

