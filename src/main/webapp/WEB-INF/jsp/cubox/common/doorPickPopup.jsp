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
    $(".add_door").click(function () {
        let nodeSel = $(".nodeSel").html();
        let nodeSelId = $(".nodeSel").find("span").attr("id");
        let doorSelected = $("#doorSelected").children();
        console.log(nodeSelId);
        // 이미 같은 출입문 있을 경우 return
        for (let i = 0; i < doorSelected.length; i++) {
            let doorPath = doorSelected.eq(i).children().last().html();
            if (doorPath == nodeSel) {
                return;
            }
        }
        if (nodeSelId != null || nodeSelId != undefined) {
            let tag = "<tr><td><input type='checkbox' id='" + nodeSelId + "' name='chkDoorConf'></td><td>" + nodeSel + "</td></tr>";
            $("#doorSelected").append(tag);
        }

    });

    // 출입문 삭제
    $(".delete_door").click(function () {
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


// 출입문선택 반영
function setDoors() {

    let doorGpIds = "";
    let doorGpHtml = [];
    $("input[name=chkDoorConf]").each(function (i) {
        let ids = $(this).attr("id");
        let html = $(this).closest("tr").children().eq(1).find("span").html();
        if (i == 0) {
            doorGpIds += ids;
        } else if (i > 0) {
            doorGpIds += ("/" + ids);
        }
        doorGpHtml.push(html);
    });
    console.log(doorGpIds);
    console.log(doorGpHtml);

    $("#gpDoorIds").val(doorGpIds);
    $("#gpDoorNms").val(doorGpHtml.join("\r\n"));

}

    /////////////////  출입문 목록 ajax - start  /////////////////////


    function fnGetDoorListAjax(type) {
        console.log( "fnGetDoorListAjax2");

        $.ajax({
            type : "GET",
            data : { },
            dataType : "json",
            url : "<c:url value='/door/list.do' />",
            success : function(result) {
                console.log(result);
                createTree(false, result, $("#treeDiv"));

                $("#doorSelected").empty();

                let doorList;
                if (type === "Group" && $("#gpDoorIds").val() != "") { // 수정일 떄
                    doorList = $("#gpDoorIds").val().split("/");
                } else if (type === "AlarmGroup" && $("#doorIds").val() !== "") {
                    doorList = $("#doorIds").val().split("/");
                }

                $.each(doorList, function(i, door) {
                    $("span[id=" + door + "]").parent().toggleClass("node nodeSel");
                    $(".add_door").click();
                    $("span[id=" + door + "]").parent().toggleClass("nodeSel node");
                })
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
                <img src="/img/ar_r.png" alt="" class="add_door"/>
            </div>
            <div class="btn_box" style="margin:5px 0;">
                <img src="/img/ar_l.png" alt="" class="delete_door"/>
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
                    <thead>
                        <tr>
                            <th><input type="checkbox" id="chkDoorConfAll"></th>
                            <th>출입문</th>
                        </tr>
                    </thead>
                    <tbody id="doorSelected"></tbody>
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
