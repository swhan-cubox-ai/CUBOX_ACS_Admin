<%--
  Created by IntelliJ IDEA.
  User: USER
  Date: 2022-10-07
  Time: 오후 3:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
    #doorSelected tr th {
        text-align: center;
    }
</style>

<script type="text/javascript">
    $(function () {

        // 출입문 추가
        $("#add_door").click(function () {
            let nodeSel = $(".nodeSel").html();
            let doorSelected = $("#doorSelected").children();

            // 이미 같은 출입문 있을 경우 return
            for (let i = 1; i < doorSelected.length; i++) {
                let doorPath = doorSelected.eq(i).children().last().html();
                if (doorPath == nodeSel) {
                    return;
                }
            }
            let tag = "<tr><td><input type='checkbox' name='chkDoorConf'></td><td>" + nodeSel + "</td></tr>";
            $("#doorSelected").append(tag);
        });

        // 출입문 삭제
        $("#delete_door").click(function () {
            let ckd = $("input[name=chkDoorConf]:checked").length;
            for (let i = ckd - 1; i > -1; i--) {
                $("input[name=chkDoorConf]:checked").eq(i).closest("tr").remove();
            }

            if ($("#chkDoorConfAll").prop("checked")) {
                $("#chkDoorConfAll").prop("checked", false);
            }
        });

        $("#chkDoorConfAll").click(function () {
            if ($("#chkDoorConfAll").prop("checked")) {
                $("input[name=chkDoorConf]").prop("checked", true);
            } else {
                $("input[name=chkDoorConf]").prop("checked", false);
            }
        });
    });

    /////////////////  출입문 목록 ajax - start  /////////////////////


    function fnGetDoorListAjax() {
        console.log( "fnGetDoorListAjax2");

        $.ajax({
            type : "GET",
            data : { },
            dataType : "json",
            url : "<c:url value='/door/list.do' />",
            success : function(result) {
                console.log(result);
                createTree(false, result, $("#treeDiv"));
            }
        });
    }
    /////////////////  출입문 목록 ajax - end  /////////////////////

</script>

<%--  출입문 선택 modal  --%>
<div id="doorEditPopup" class="example_content" style="display: none;">
    <div class="popup_box box_w3">
        <div style="width:45%;">
            <div class="com_box"
                 style="border: 1px solid black; background-color: white; overflow: auto; height: 400px; padding: 2px 10px;">
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
            <div class="com_box"
                 style="border: 1px solid black; background-color: white; overflow: auto; height: 400px;">
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
