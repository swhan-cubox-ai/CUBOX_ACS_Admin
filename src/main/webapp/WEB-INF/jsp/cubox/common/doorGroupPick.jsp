<%--
  Created by IntelliJ IDEA.
  User: USER
  Date: 2022-10-04
  Time: 오후 3:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">
    $(function () {

        // 권한그룹 추가
        $("#add_group").click(function () {
            $("input[name=chkGroup]:checked").each(function (i) {
                let tag = "<tr>" + $(this).closest("tr").html() + "</tr>";
                tag = tag.replace("chkGroup", "chkGroupConf");
                $("#tdGroupConf").append(tag);
            });

            let ckd = $("input[name=chkGroup]:checked").length;
            for (let i = ckd - 1; i > -1; i--) {
                $("input[name=chkGroup]:checked").eq(i).closest("tr").remove();
            }

            totalCheck();
        });

        // 권한그룹 삭제
        $("#delete_group").click(function () {
            $("input[name=chkGroupConf]:checked").each(function (i) {
                let tag = "<tr>" + $(this).closest("tr").html() + "</tr>";
                tag = tag.replace("chkGroupConf", "chkGroup");
                $("#tdGroupTotal").append(tag);
            });

            let ckd = $("input[name=chkGroupConf]:checked").length;
            for (let i = ckd - 1; i > -1; i--) {
                $("input[name=chkGroupConf]:checked").eq(i).closest("tr").remove();
            }

            userCheck();
        });

        // 전체 선택(왼쪽)
        $("#totalGroupCheckAll").click(function () {
            if ($("#totalGroupCheckAll").prop("checked")) {
                $("input[name=chkGroup]").prop("checked", true);
            } else {
                $("input[name=chkGroup]").prop("checked", false);
            }
        });

        // 전체 선택(오른쪽)
        $("#userGroupCheckAll").click(function () {
            if ($("#userGroupCheckAll").prop("checked")) {
                $("input[name=chkGroupConf]").prop("checked", true);
            } else {
                $("input[name=chkGroupConf]").prop("checked", false);
            }
        });

    });

    // 출입문그룹 선택 저장
    function fnGroupSave() {
        let gateGroup = [];
        $("input[name=chkGroupConf]").each(function (i) { // 체크된 것 말고 오른쪽 박스에 들어온 모든 항목
            let auth = $(this).closest("tr").children().eq(1).html();
            gateGroup.push(auth);
        });
        console.log(gateGroup);

        // 권한그룹 textarea에 뿌려주기
        $("#gateGroup").val(gateGroup.join("\r\n"));
        closePopup("doorGroupPickPopup");
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

</script>

<%--  출입문그룹 선택 modal  --%>
<div id="doorGroupPickPopup" class="example_content" style="display: none;">
    <div class="popup_box box_w3">

        <%--  왼쪽 box  --%>
        <div style="width:45%;">
            <div class="com_box"
                 style="border: 1px solid black; background-color: white; overflow: auto; height: 330px;">
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
            <div class="com_box"
                 style="border: 1px solid black; background-color: white; overflow: auto; height: 330px;">
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
                <button type="button" class="comm_btn" onclick="closePopup('doorGroupPickPopup');">취소</button>
            </div>
        </div>
    </div>
</div>
<%--  end of 출입문그룹 선택 modal  --%>