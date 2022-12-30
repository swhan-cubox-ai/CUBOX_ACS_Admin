<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:include page="/WEB-INF/jsp/cubox/common/doorPickPopup.jsp" flush="false"/>

<style>
    .tb_write_p1 tbody th {
        text-align: center;
    }
    .gateDetailList tr td {
        text-align: center;
    }
    .gateDetailList tr td select {
        float: none;
    }
    .gateDetailList tr td input,
    .gateDetailList tr td select,
    .gateDetailList tr td textarea {
        width: 95%;
    }


    .tb_list tr td {
        text-align: center;
    }
    thead {
        position: sticky;
        top: 0;
    }
    #tdAuthConf tr, #tdAuthTotal tr {
        height: 40px;
        text-align: center;
    }

</style>


<script type="text/javascript">

    $(function() {

        $(".title_tx").html("출입문권한 그룹관리 - 등록 (건물)");

        modalPopup("doorListLayerPop", "출입문 명", 500, 520);
        modalPopup("doorEditPopup", "출입문 선택", 900, 600);
        fnGetAuthCnts();

        $("input:radio[name=rdoAuthTyp]").click(function()
        {
            let checkedVal = $("input[name='rdoAuthTyp']:checked").val();

            if(checkedVal == "EAT001"){
                $(".title_tx").html("출입문권한 그룹관리 - 등록 (건물)");

                $("#divAuth1").css("display", "block");
                $("#divAuth2").css("display", "none");
                $("#divAuth3").css("display", "none");
                fnGetAuthCnts();
            }else if(checkedVal == "EAT002"){
                $(".title_tx").html("출입문권한 그룹관리 - 등록 (출입문 그룹)");

                $("#divAuth1").css("display", "none");
                $("#divAuth2").css("display", "block");
                $("#divAuth3").css("display", "none");
            }else{
                $(".title_tx").html("출입문권한 그룹관리 - 등록 (출입문)");

                $("#divAuth1").css("display", "none");
                $("#divAuth2").css("display", "none");
                $("#divAuth3").css("display", "block");
            }
        });

        $("#addAuthList").click(function() {
            let comp;
            let pass = false;
            $("input[name=sourceDoorGrp]:checked").each(function(i) {
                comp = $(this).val();
                $("input[name=targetDoorGrp]").each(function(i) {
                    if(comp == $(this).val()){
                        pass = true;
                    }
                });
                if(!pass){
                    let tag = "<tr>" + $(this).closest("tr").html() + "</tr>";
                    tag = tag.replace("sourceDoorGrp", "targetDoorGrp");
                    $("#targetDoorGrpTr").append(tag);
                }
            });

            let ckd = $("input[name=sourceDoorGrp]:checked").length;
            for(let i = ckd - 1; i > -1; i--) {
                $("input[name=sourceDoorGrp]:checked").eq(i).closest("tr").remove();
            }

            totalSourceCheck();
        });

        $("#delAuthList").click(function() {
            $("input[name=targetDoorGrp]:checked").each(function(i) {
                let tag = "<tr>" + $(this).closest("tr").html() + "</tr>";
                tag = tag.replace("targetDoorGrp", "sourceDoorGrp");
                $("#sourceDoorGrpTr").append(tag);
            });

            let ckd = $("input[name=targetDoorGrp]:checked").length;
            for(let i = ckd - 1; i > -1; i--) {
                $("input[name=targetDoorGrp]:checked").eq(i).closest("tr").remove();
            }

            totalTargetCheck();
        });

        $("#sourceDoorGrpCheckAll").click(function() {
            if($("#sourceDoorGrpCheckAll").prop("checked")) {
                $("input[name=sourceDoorGrp]").prop("checked", true);
            } else {
                $("input[name=sourceDoorGrp]").prop("checked", false);
            }
        });

        $("#targetDoorGrpCheckAll").click(function() {
            if ($("#targetDoorGrpCheckAll").prop("checked")) {
                $("input[name=targetDoorGrp]").prop("checked", true);
            } else {
                $("input[name=targetDoorGrp]").prop("checked", false);
            }
        });

        $("#buildingAllCheck").click(function() {
            if($("#buildingAllCheck").prop("checked")) {
                $("input[name=authBuildingData]").prop("checked", true);
            } else {
                $("input[name=authBuildingData]").prop("checked", false);
            }
        });
    });

    function totalSourceCheck() {
        if ($("#sourceDoorGrpCheckAll").prop("checked")) {
            $("#sourceDoorGrpCheckAll").prop("checked", false);
        }
    }

    function totalTargetCheck() {
        if ($("#targetDoorGrpCheckAll").prop("checked")) {
            $("#targetDoorGrpCheckAll").prop("checked", false);
        }
    }

    function fnGetAuthCnts(){
        $.ajax({
            type:"POST",
            url:"/auth/door/getBuildingList.do",
            dataType:'json',
            success:function(returnData, status){
                if(returnData.result == "success") {
                    $("#buildingTr").empty();

                    let result = returnData.list;
                    let str = '';

                    $.each(result, function(i){
                        str += '<tr>';
                        str += '<td><input type="checkbox" name="authBuildingData" value="' + result[i].id + '"></td>';
                        str += '<td>' + result[i].buildingNm + '</td>';
                        str += '</tr>';
                    });

                    $("#buildingTr").append(str);
                }else{ alert("ERROR!");return;}
            }
        });
    }

    function fnViewDoorList(id) {
        $.ajax({
            type:"POST",
            url:"/auth/door/getDoorList.do",
            data:{
                "id": id
            },
            dataType:'json',
            success:function(returnData, status){

                if(status == "success") {
                    $("#doorListDiv").empty();

                    let result = returnData.list;
                    let str = '';

                    $.each(result, function(i){
                        str += '<tr>';
                        str += '<td>' + i + '</td>';
                        str += '<td>' + result[i].doorNm + '</td>';
                        str += '</tr>';
                    });

                    $("#doorListDiv").append(str);

                    $("#doorListLayerPop").PopupWindow("open");

                }else{ alert("ERROR!");return;}
            }
        });
    }

    function fnSearch() {
        let param = $("#keywordGrpNm").val();

        $.ajax({
            type:"POST",
            url:"/auth/door/getDoorGrpList.do",
            data:{"keyword" : param},
            dataType:'json',
            success:function(returnData, status){

                if(status == "success") {

                    $("#sourceDoorGrpTr").empty();

                    let result = returnData.list;
                    let str = '';

                    $.each(result, function(i){
                        str += '<tr>';
                        str += '<td><input type="checkbox" name="sourceDoorGrp" value="' + result[i].id + '"></td>';
                        str += '<td>' + result[i].doorGrpNm + '</td>';
                        str += '<td><a onclick="fnViewDoorList(' + result[i].id + ')">' + result[i].doorCnt + '</a></td>';
                        str += '</tr>';
                    });

                    $("#sourceDoorGrpTr").append(str);

                }else{ alert("ERROR!");return;}
            }
        });
    }

    function fnCancel(){
        Swal.fire({
            text: '등록을 취소하시겠습니까?',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'OK',
            cancelButtonText: 'CANCEL',
            reverseButtons: true, // 버튼 순서 거꾸로

        }).then((result) => {
            if (result.isConfirmed) {
                window.location.href='/auth/door/list.do';
            }
        })
    }


    function fnSave(){
        Swal.fire({
            text: '출입문권한을 신규등록 하시겠습니까?',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'OK',
            cancelButtonText: 'CANCEL',
            reverseButtons: true,

        }).then((result) => {
            if (result.isConfirmed) {
                fnSaveProc();
            }
        });
    }

    function fnSaveProc(){
        fnSetData();
        const params = $("#addDoorFrm").serialize();

        if(fnIsValid){
            $.ajax({
                type:"POST",
                data:params,
                url:"/auth/door/addDoor.do",
                dataType:'json',
                //timeout:(1000*30),
                success:function(returnData){
                    if(returnData.result == "success") {
                        Swal.fire({
                            icon: 'success',
                            text: '신규등록이 완료되었습니다.'
                        }).then((result) => {
                            window.location.href='/auth/door/list.do';
                        });
                    }else{ alert("ERROR!");return;}
                }
            });
        }
    }

    function fnSetData(){
        let checkedVal = $("input[name='rdoAuthTyp']:checked").val();
        let checkedArray = [];

        if(checkedVal == 'EAT001'){
            $("input[name=authBuildingData]:checked").each(function(i) {
                checkedArray.push($(this).val());
            });
            $("#authItemArray").val(checkedArray.join(","));
        }else if(checkedVal == 'EAT002'){
            $("input[name=targetDoorGrp]").each(function(i) {
                checkedArray.push($(this).val());
            });
            $("#authItemArray").val(checkedArray.join(","));
        }else{
            // todo
        }
    }

    function fnIsValid(){
        if($("#authNm").val() == ""){
            Swal.fire({
                icon: 'warning',
                text: '출입권한그룹명을 입력해주세요'
            });
            return false;
        }
        if($("#authItemArray").val() == ""){
            Swal.fire({
                icon: 'warning',
                text: '출입권한 유형별 정보를 등록해주세요'
            });
            return false;
        }

        return true;
    }

    // 출입문선택 반영
    function setDoors(type) {

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

        if (type === "Group") {                 // 그룹관리
            $("#gpDoorIds").val(doorGpIds);
            $("#gpDoorNms").val(doorGpHtml.join("\r\n"));
        } else if (type === "AlarmGroup") {     // 알람그룹
            $("#doorIds").val(doorGpIds);
            $("#tdGroupTotal").empty();
            $("#alDoorCnt").val($("input[name=chkDoorConf]").length);   // 출입문 수
            $.each(doorGpHtml, function(i, html) {                      // 출입문 목록에 반영
                let tag = "<tr><td>" + html + "</td></tr>";
                $("#tdGroupTotal").append(tag);
            });
        }
    }





    // popup open (공통)
    function openPopup(popupNm) {
        $("#" + popupNm).PopupWindow("open");
        if (popupNm === "doorEditPopup") {
            fnGetDoorListAjax("Group"); //출입문 목록
        }
    }

    // popup close (공통)
    function closePopup(popupNm) {
        setDoors("Group");
        $("#" + popupNm).PopupWindow("close");
    }

</script>
<form id="addDoorFrm" name="addDoorFrm">
    <input type="hidden" id="authTyp" name="authTyp" value='<c:out value="${authTyp}"/>'/>
    <input type="hidden" id="authItemArray" name="authItemArray"/>

    <div class="search_box mb_20">
        <div class="search_in" style="width: 900px;margin-left: 100px;">

            <div class="comm_search mb_20 mt_10">
                <div class="w_150px fl" style=""><em>부서</em></div>
                <select name="deptCd" id="deptCd" size="1" class="w_150px input_com">
                    <option value="">부서선택</option>
                    <c:forEach var="list" items="${deptComboList}">
                        <option value="${list.cd}">${list.cdNm}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="comm_search mb_20 mt_10">
                <div class="w_150px fl" style="line-height: 30px"><em>출입권한그룹 명</em></div>
                <input type="text" id="authNm" name="authNm" class="w_250px input_com l_radius_no" value="" placeholder="출입권한그룹 명" maxlength="20" style="border:1px solid #ccc;"/>
            </div>
            <div class="comm_search mb_20 mt_10" style="float:none;margin-top:69px;">
                <div class="w_150px fl"><em>사용</em></div>
                <select name="useYn" id="useYn" size="1" class="w_150px input_com">
                    <option value="Y" selected>Y</option>
                    <option value="N">N</option>
                </select>
            </div>
            <div class="comm_search mb_20 mt_10" style="float:none;margin-top:124px;">
                <div class="w_150px fl"><em>유형</em></div>
                <div class="comm_search mb_5 mt_5">
                    <input type="radio" style="float:left;width:15px;" id="rdoBuilding" name="rdoAuthTyp"class="input_com" value="EAT001" checked><label for="rdoBuilding" style="margin-right:20px">빌딩</label>
                </div>
                <div class="comm_search mb_5 mt_5">
                    <input type="radio" style="float:left;width:15px;" id="rdoDoorGrp" name="rdoAuthTyp" class="input_com" value="EAT002"><label for="rdoDoorGrp" style="margin-right:20px">출입문그룹</label>
                </div>
                <div class="comm_search mb_5 mt_5">
                    <input type="radio" style="float:left;width:15px;" id="rdoDoor" name="rdoAuthTyp" class="input_com" value="EAT003"><label for="rdoDoor">출입문</label>
                </div>
            </div>

            <div id="divAuth1" style="display:block">
                <div class="popup_box box_w3">
                    <div style="width:65%;">
                        <div class="com_box" style="border: 1px solid black; overflow: auto; height: 250px;">
                            <table class="tb_list tb_write_02 tb_write_p1">
                                <colgroup>
                                    <col style="width:3%">
                                    <col style="width:15%">
                                </colgroup>
                                <thead>
                                <tr>
                                    <th><input type="checkbox" id="buildingAllCheck"></th>
                                    <th>건물명</th>
                                </tr>
                                </thead>
                                <tbody id="buildingTr">
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>

            <div id="divAuth2" style="display:none">
                <div class="search_box mb_20" style="margin-top:30px;">
                    <div class="popup_box box_w3">
                        <%--  검색 박스 --%>
                        <div class="search_in" style="padding-left: 0px;width: 80%;">
                            <div class="comm_search mr_10">
                                <input type="text" class="input_com" id="keywordGrpNm" name="keywordGrpNm" value="" placeholder="출입문그룹명" maxlength="30" style="width: 182px;">
                            </div>
                            <div class="comm_search ml_40">
                                <div class="search_btn2" onclick="fnSearch();"></div>
                            </div>
                        </div>
                        <%--  end of 검색 박스 --%>

                        <%--  왼쪽 box  --%>
                        <div style="width:45%;">
                            <div class="com_box" style="border: 1px solid black; overflow: auto; height: 250px;">
                                <table class="tb_list tb_write_02 tb_write_p1">
                                    <colgroup>
                                        <col style="width:3%">
                                        <col style="width:15%">
                                        <col style="width:5%">
                                    </colgroup>
                                    <thead>
                                    <tr>
                                        <th><input type="checkbox" id="sourceDoorGrpCheckAll"></th>
                                        <th>출입문 그룹명</th>
                                        <th>출입문 수</th>
                                    </tr>
                                    </thead>
                                    <tbody id="sourceDoorGrpTr">
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <%--  end of 왼쪽 box  --%>

                        <%--  화살표 이동  --%>
                        <div class="box_w3_2" style="height: 250px;">
                            <div class="btn_box">
                                <img src="/img/ar_r.png" alt="" id="addAuthList"/>
                            </div>
                            <div class="btn_box">
                                <img src="/img/ar_l.png" alt="" id="delAuthList"/>
                            </div>
                        </div>
                        <%--  end of 화살표 이동  --%>

                        <%--  오른쪽 box  --%>
                        <div style="width:45%;">
                            <%--  테이블  --%>
                            <div class="com_box" style="border: 1px solid black; overflow: auto; height: 250px;">
                                <table class="tb_list tb_write_02 tb_write_p1">
                                    <colgroup>
                                        <col style="width:3%">
                                        <col style="width:15%">
                                        <col style="width:5%">
                                    </colgroup>
                                    <thead>
                                    <tr>
                                        <th><input type="checkbox" id="targetDoorGrpCheckAll"></th>
                                        <th>출입문 그룹명</th>
                                        <th>출입문 수</th>
                                    </tr>
                                    </thead>
                                    <tbody id="targetDoorGrpTr">
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div id="divAuth3" style="display:none">
                <textarea id="gpDoorNms" name="gpDoorNms" rows="10" cols="33" class="w_600px color_disabled" style="border-color: #ccc; border-radius: 2px;
                              font-size: 14px; line-height: 1.5; padding: 2px 10px;" disabled><c:set var="nm" value="${fn:split(doorGroupDetail.door_nms,'/')}" /><c:forEach items="${nm}" var="dName" varStatus="varStatus">
                    ${dName}</c:forEach></textarea>
                <div class="ml_10" style="position: relative;">
                    <button id="btnEdit" type="button" class="btn_small color_basic" style="position: absolute; bottom: 0; width: 80px;" onclick="openPopup('doorEditPopup')" id="btnSelDoor">출입문 선택</button>
                </div>
            </div>

            <div style="margin-top:300px;margin-left: 50px;">
                <button type="button" class="btn_middle color_basic ml_5" style="float:left;margin-left: 30px;" onclick="fnSave();">등록</button>
                <button type="button" class="btn_middle color_basic ml_5" style="float:left;margin-left: 30px;" onclick="fnCancel();">취소</button>
            </div>

        </div>
    </div>
</form>

