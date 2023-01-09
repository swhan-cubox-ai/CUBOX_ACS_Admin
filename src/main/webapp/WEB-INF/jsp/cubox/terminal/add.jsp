<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script type="text/javascript">
    $(function() {
        $(".title_tx").html("단말기 관리 - 등록");

        $('#doorId').on('change', function(){
            fnGetDoorInfo($(this).val());
        });
    });

    function fnGetDoorInfo(key) {
        $.ajax({
            type:"POST",
            url:"/terminal/getDoorInfo.do",
            data:{
                "doorId": key
            },
            dataType:'json',
            success:function(returnData){

                if(returnData.result == "success") {
                    $('#buildingNm').val(returnData.data.buildingNm);
                    $('#floorNm').val(returnData.data.floorNm);

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
                window.location.href='/terminal/list.do';
            }
        })
    }


    function fnSave(){
        if(fnIsValid()){
            Swal.fire({
                text: '단말기를 등록 하시겠습니까?',
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
    }

    function fnSaveProc(){
        const params = $("#addTerminalFrm").serialize();

        $.ajax({
            type:"POST",
            data:params,
            url:"/terminal/addTerminal.do",
            dataType:'json',
            //timeout:(1000*30),
            success:function(returnData){
                if(returnData.result == "success") {
                    Swal.fire({
                        icon: 'success',
                        text: '단말기가 등록되었습니다.'
                    }).then((result) => {
                        window.location.href='/terminal/list.do';
                    });
                }else{ alert("ERROR!");return;}
            }
        });
    }


    function fnIsValid(){

        if($("#terminalTyp").val() == ""){
            Swal.fire({
                icon: 'warning',
                text: '단말기 유형 필수입력 값이 누락되었습니다'
            });
            return false;
        }

        return true;
    }

</script>
<form id="addTerminalFrm" name="addTerminalFrm">

    <div class="search_box mb_20">
        <div class="search_in" style="width: 700px;margin-left: 100px;">
            <div class="comm_search mb_20 mt_10">
                <div class="w_150px fl" style="line-height: 30px"><em>건물</em></div>
                <input type="text" id="buildingNm" name="buildingNm" class="w_250px input_com l_radius_no" readonly="readonly" value='' placeholder="건물" maxlength="20" style="border:1px solid #ccc;"/>
            </div>
            <div class="comm_search w_100p mb_20" style="line-height: 30px">
                <div class="w_150px fl"><em>층</em></div>
                <input type="text" id="floorNm" name="floorNm" class="w_250px input_com l_radius_no" readonly="readonly" value="" placeholder="층" maxlength="20" style="border:1px solid #ccc;"/>
            </div>
            <div class="comm_search w_100p mb_20" style="line-height: 30px">
                <div class="w_150px fl"><em>출입문 명</em></div>
                <select name="doorId" id="doorId" size="1" class="w_150px input_com">
                    <option value="">선택</option>
                    <c:forEach var="list" items="${doorCombList}">
                        <option value="${list.cd}">${list.cdNm}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="comm_search w_100p mb_20" style="line-height: 30px">
                <div class="w_150px fl"><em>단말기 코드</em></div>
                <input type="text" id="terminalCd" name="terminalCd" class="w_250px input_com l_radius_no" value="" placeholder="단말기 코드" maxlength="20" style="border:1px solid #ccc;"/>
            </div>
            <div class="comm_search w_100p mb_20" style="line-height: 30px">
                <div class="w_150px fl"><em>관리번호</em></div>
                <input type="text" id="mgmtNum" name="mgmtNum" class="w_250px input_com l_radius_no" value="" placeholder="관리번호" maxlength="20" style="border:1px solid #ccc;"/>
            </div>
            <div class="comm_search w_100p mb_20" style="line-height: 30px">
                <div class="w_150px fl"><em>단말기 유형</em></div>
                <select name="terminalTyp" id="terminalTyp" size="1" class="w_150px input_com">
                    <option value="">선택</option>
                    <c:forEach var="list" items="${terminalTypCombList}">
                        <option value="${list.cd}">${list.cdNm}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="comm_search w_100p mb_20" style="line-height: 30px">
                <div class="w_150px fl"><em>모델명</em></div>
                <input type="text" id="modelNm" name="modelNm" class="w_250px input_com l_radius_no" value="" placeholder="모델명" maxlength="20" style="border:1px solid #ccc;"/>
            </div>
            <div class="comm_search w_100p mb_20" style="line-height: 30px">
                <div class="w_150px fl"><em>IP</em></div>
                <input type="text" id="ipAddr" name="ipAddr" class="w_250px input_com l_radius_no" value="" placeholder="IP" maxlength="20" style="border:1px solid #ccc;"/>
            </div>
            <div class="comm_search w_100p mb_20" style="line-height: 30px">
                <div class="w_150px fl"><em>출입인증방식</em></div>
                <select name="complexAuthTyp" id="complexAuthTyp" size="1" class="w_150px input_com">
                    <option value="">선택</option>
                    <c:forEach var="list" items="${complexAuthTypCombList}">
                        <option value="${list.cd}">${list.cdNm}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="comm_search w_100p mb_20" style="line-height: 30px">
                <div class="w_150px fl"><em>얼굴인증방식</em></div>
                <select name="faceAuthTyp" id="faceAuthTyp" size="1" class="w_150px input_com">
                    <option value="">선택</option>
                    <c:forEach var="list" items="${faceAuthTypCombList}">
                        <option value="${list.cd}">${list.cdNm}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="comm_search w_100p mb_20" style="line-height: 30px">
                <div class="w_150px fl"><em>운영모드방식</em></div>
                <select name="opModeTyp" id="opModeTyp" size="1" class="w_150px input_com">
                    <option value="">선택</option>
                    <c:forEach var="list" items="${opModeTypCombList}">
                        <option value="${list.cd}">${list.cdNm}</option>
                    </c:forEach>
                </select>
            </div>
            <%--<div class="comm_search w_100p mb_20" style="line-height: 30px">
                <div class="w_150px fl"><em>Black List</em></div>
                <input type="text" id="blackListCnt" name="blackListCnt" class="w_250px input_com l_radius_no" readonly="readonly" value="" placeholder="Black List" maxlength="20" style="border:1px solid #ccc;"/>
                <button type="button" class="btn_small color_basic ml_5" style="margin-left: 10px;" onclick="fnGetBlackList();">보기</button>
                <button type="button" class="btn_small color_basic ml_5" style="margin-left: 5px;" onclick="window.location.href='/terminal/black/modify/${data.id}';">수정/등록</button>
            </div>
            <div class="comm_search w_100p mb_20" style="line-height: 30px">
                <div class="w_150px fl"><em>White List</em></div>
                <input type="text" id="whiteListCnt" name="whiteListCnt" class="w_250px input_com l_radius_no" readonly="readonly" value="" placeholder="White List" maxlength="20" style="border:1px solid #ccc;"/>
                <button type="button" class="btn_small color_basic ml_5" style="margin-left: 10px;" onclick="fnGetWhiteList();">보기</button>
                <button type="button" class="btn_small color_basic ml_5" style="margin-left: 5px;" onclick="window.location.href='/terminal/white/modify/${data.id}';">수정/등록</button>
            </div>--%>
            <div class="comm_search w_100p mb_20" style="line-height: 30px">
                <div class="w_150px fl"><em>사용</em></div>
                <select name="useYn" id="useYn" size="1" class="w_150px input_com">
                    <option value="Y" selected>Y</option>
                    <option value="N">N</option>
                </select>
            </div>

            <div style="margin-top:300px;margin-left: 50px;">
                <button type="button" class="btn_middle color_basic ml_5" style="float:left;margin-left: 30px;" onclick="fnSave();">저장</button>
                <button type="button" class="btn_middle color_basic ml_5" style="float:left;margin-left: 30px;" onclick="fnCancel();">취소</button>
            </div>

        </div>
    </div>
</form>