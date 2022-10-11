<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<style>
    .tb_write_p1 tbody th {
        text-align: center;
    }
    .tb_write_02 tbody tr {
        height: 70px;
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
    .title_s {
        font-size: 20px;
    }
    #gateInfo {
        width: 100%;
        height: 690px;
        border: 1px solid black;
    }
    #treeDiv {
        width: 100%;
        height: 611px;
        border-bottom: 1px solid #ccc;
        padding: 10px 45px;
        padding: 10px 45px;
        overflow: auto;
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
    .title_box {
        margin-top: 10px;
    }
</style>

<script type="text/javascript">
    $(function() {
        const _isModify = ${isModify};

        if(_isModify){
            $(".title_tx").html("인사정보 관리 - 수정");
        }else{
            $(".title_tx").html("인사정보 관리 - 상세");
        }

        modalPopup("authEntGroupListLayerPop", "출입권한그룹 선택", 910, 520);
    });

    function fnCancel(){
        Swal.fire({
            text: '작업을 취소하시겠습니까?',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'OK',
            cancelButtonText: 'CANCEL',
            reverseButtons: true, // 버튼 순서 거꾸로

        }).then((result) => {
            if (result.isConfirmed) {
                window.location.href='/auth/emp/detail/'+ $("#id").val();
            }
        });
    }

    function fnSave(){
        Swal.fire({
            text: '변경된 정보를 저장 하시겠습니까?',
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
        })
    }

    function fnSaveProc(){

        const params = $("#modifyEmpFrm").serialize();

        $.ajax({
            type:"POST",
            data:params,
            url:"/auth/emp/modifyEmp.do",
            dataType:'json',
            //timeout:(1000*30),
            success:function(returnData, status){
                if(returnData.result == "success") {
                    Swal.fire({
                        icon: 'success',
                        text: '인사정보가 변경 되었습니다.',
                    }).then((result) => {
                        window.location.href='/auth/emp/detail/'+$("#id").val();
                    });
                }else{ alert("ERROR!");return;}
            }
        });
    }

    function fnDelete(){
        Swal.fire({
            text: '정말로 삭제 하시겠습니까?',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'OK',
            cancelButtonText: 'CANCEL',
            reverseButtons: true,

        }).then((result) => {
            if (result.isConfirmed) {
                fnDeleteProc();
            }
        });
    }

    function fnDeleteProc(){
        let param = $("#id").val();

        $.ajax({
            type: "POST",
            data: {id : param},
            url: "/auth/emp/deleteEmp.do",
            dataType: 'json',
            //timeout:(1000*30),
            success: function (returnData) {
                if (returnData.result == "success") {
                    Swal.fire({
                        icon: 'success',
                        text: '인사정보가 삭제 되었습니다.'
                    }).then((result) => {
                        window.location.href = '/auth/emp/list.do';
                    });
                } else {
                    alert("ERROR!");
                    return;
                }
            }
        });
    }

    //출입권한그룹선택 레이어팝업열기
    function fnPopAuthEnt(){
        const param = $("#id").val();

        $.ajax({
            type:"POST",
            data:{"id" : param},
            url:"/auth/emp/getAuthEntList.do",
            dataType:'json',
            success:function(returnData, status){
                if(returnData.result == "success") {
                    $("#authEntGroupListDiv").empty();

                    const allList = returnData.authEntAllList;
                    const myList = returnData.authEntMyList;

                    let str = '';

                    $.each(allList, function(i){
                        str += '<tr>';
                        str += '<td><input type="checkbox" name="authEntGroup" data="' + allList[i].authNm + '" value="' + allList[i].authId + '" ';
                        $.each(myList, function (k){
                            if(allList[i].authId == myList[k].authId){
                                str += 'checked';
                                return false;
                            }
                        });
                        str += '/></td>';
                        str += '<td>' + allList[i].authNm + '</td>';
                        str += '</tr>';
                    });

                    $("#authEntGroupListDiv").append(str);

                    $("#authEntGroupListLayerPop").PopupWindow("open");

                }else{ alert("ERROR!");return;}
            }
        });
    }

    function fnSavePop(){
        const len = $("input[name='authEntGroup']:checked").length;

        let str1 = "";
        let str2 = "";
        let cnt = 0;

        $("#isModAuthEnt").val("Y");

        if(len > 0){
            $("input[name='authEntGroup']:checked").each(function(e){
                cnt++;
                str1 += $(this).val()
                if(cnt < len) str1 += ",";

                str2 += $.trim($(this).attr("data"));
                str2 += '\n';

            });
            $("#authEntStr").val(str1);

            $("#authEntGroupTxt").empty();
            $("#authEntGroupTxt").append(str2);
        }else{
            $("#authEntStr").val("nan");
            $("#authEntGroupTxt").empty();
        }

        fnClosePop();
    }


    function fnClosePop() {
        $("#authEntGroupListLayerPop").PopupWindow("close");
    }

</script>
<form id="modifyEmpFrm" name="modifyEmpFrm">
    <input type="hidden" id="id" name="id" value='<c:out value="${data.id}"/>'/>
    <input type="hidden" id="isModAuthEnt" name="isModAuthEnt" value=''/>
    <input type="hidden" id="authEntStr" name="authEntStr" value=''/>

    <div class="search_box mb_20">
        <div class="search_in" style="width: 600px;margin-left: 100px;">
            <div class="comm_search mb_20 mt_10">
                <div class="w_150px fl" style="line-height: 30px"><em>소속</em></div>
                <input type="text" id="belongNm" name="belongNm" class="w_250px input_com l_radius_no" readonly="readonly" value='<c:out value="${data.belongNm}"/>' placeholder="소속" maxlength="20" style="border:1px solid #ccc;"/>
            </div>
            <div class="comm_search mb_20 mt_10">
                <div class="w_150px fl" style="line-height: 30px"><em>기관</em></div>
                <input type="text" id="insttNm" name="insttNm" class="w_250px input_com l_radius_no" readonly="readonly" value='<c:out value="${data.insttNm}"/>' placeholder="기관" maxlength="20" style="border:1px solid #ccc;"/>
            </div>
            <div class="comm_search w_100p mb_20" style="line-height: 30px">
                <div class="w_150px fl"><em>부서</em></div>
                <input type="text" id="deptNm" name="deptNm" class="w_250px input_com l_radius_no" readonly="readonly" value="<c:out value='${data.deptNm}'/>" placeholder="부서" maxlength="20" style="border:1px solid #ccc;"/>
            </div>
            <div class="comm_search w_100p mb_20" style="line-height: 30px">
                <div class="w_150px fl"><em>인사번호</em></div>
                <input type="text" id="empNo" name="empNo" class="w_250px input_com l_radius_no" readonly="readonly" value="<c:out value='${data.empCd}'/>" placeholder="사원번호" maxlength="20" style="border:1px solid #ccc;"/>
            </div>
            <div class="comm_search w_100p mb_20" style="line-height: 30px">
                <div class="w_150px fl"><em>성명</em></div>
                <input type="text" id="empNm" name="empNm" class="w_250px input_com l_radius_no" readonly="readonly" value="<c:out value='${data.empNm}'/>" placeholder="성명" maxlength="20" style="border:1px solid #ccc;"/>
            </div>
            <div class="comm_search w_100p mb_20" style="line-height: 30px">
                <div class="w_150px fl"><em>유효일자</em></div>
                <input type="text" id="expiredDt" name="expiredDt"  class="w_250px input_com l_radius_no" readonly="readonly" value="<c:out value='${data.expiredDt}'/>" placeholder="유효일자" maxlength="20" style="border:1px solid #ccc;"/>
            </div>
            <div class="comm_search w_100p mb_20" style="line-height: 30px">
                <div class="w_150px fl"><em>출입권한</em></div>
                <div style="float:left;">
          <textarea id="authEntGroupTxt" name="authEntGroupTxt" readonly="readonly" placeholder="" rows="10" cols="50" style="border:1px solid #ccc;">
            <c:forEach items="${authEntMyList}" var="sList" varStatus="status">
                <c:out value="${sList.authNm}" ></c:out>
            </c:forEach>
          </textarea>
                </div>
                <c:if test="${isModify}">
                    <button type="button" class="btn_small color_basic ml_5" style="margin-left: 5px;" onclick="fnPopAuthEnt();">변경/선택</button>
                </c:if>
            </div>


            <div style="margin-top:300px;margin-left: 50px;">
                <c:if test="${isModify}">
                    <button type="button" class="btn_middle color_basic ml_5" style="float:left;margin-left: 30px;" onclick="fnSave();">저장</button>
                    <button type="button" class="btn_middle color_basic ml_5" style="float:left;margin-left: 30px;" onclick="fnCancel();">취소</button>
                </c:if>
                <c:if test="${!isModify}">
                    <button type="button" class="btn_middle color_basic ml_5" style="float:left" onclick="window.location.href='/auth/emp/list.do';">목록</button>
                    <button type="button" class="btn_middle color_basic ml_5" style="float:left;margin-left: 30px;" onclick="window.location.href='/auth/emp/modify/${data.id}';">수정</button>
                </c:if>
            </div>

        </div>
    </div>
</form>

<!-- 출입권한그룹 목록 레이어 팝업 -->
<div id="authEntGroupListLayerPop" class="example_content">
    <div class="popup_box box_w3">

        <div class="com_box ">
            <div class="txbox">
                <b class="fl mr_10">출입권한그룹 선택</b>
            </div>
            <!--테이블 시작 -->
            <div class="tb_outbox" style="margin-bottom: 30px;">
                <table class="tb_list" >
                    <colgroup>
                        <col width="3%" />
                        <col width="15%" />
                    </colgroup>
                    <thead>
                    <tr>
                        <th>선택</th>
                        <th>출입권한그룹</th>
                    </tr>
                    </thead>
                    <tbody id="authEntGroupListDiv"></tbody>
                </table>
            </div>
            <div style="margin-top:300px;text-align: center;">
                <button type="button" class="btn_middle color_basic ml_5" onclick="fnSavePop()">확인</button>
                <button type="button" class="btn_middle color_basic ml_5" onclick="fnClosePop()">취소</button>
            </div>
        </div>
    </div>
</div>
<!-- /출입권한그룹 목록 레이어 팝업 -->