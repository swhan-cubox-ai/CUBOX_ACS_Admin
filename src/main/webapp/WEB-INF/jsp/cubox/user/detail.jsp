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
        var isModify = ${isModify};

        if(isModify){
            $(".title_tx").html("사용자 관리 - 수정");
        }else{
            $(".title_tx").html("사용자 관리 - 상세");
        }

        modalPopup("roleListLayerPop", "권한목록 선택", 910, 520);

        fnGetUserAuthList();
    });

    function fnCancel(){
        Swal.fire({
            title: '작업을 취소하시겠습니까?',
            text: "",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'OK',
            cancelButtonText: 'CANCEL',
            reverseButtons: true, // 버튼 순서 거꾸로

        }).then((result) => {
            if (result.isConfirmed) {
                window.location.href='/user/detail/'+$("#userId").val();
            }
        })
    }

    function fnClosePop(){
        $("#roleListLayerPop").PopupWindow("close");
    }

    function fnGetUserAuthList() {
        var paramKey = $("#userId").val();

        $.ajax({
            type:"POST",
            url:"/user/getUserAuthList.do",
            data:{
                "id": paramKey
            },
            dataType:'json',
            //timeout:(1000*30),
            success:function(returnData, status){
                if(status == "success") {
                    $("#userRoleListDiv").empty();

                    var result = returnData.list;
                    var str = '';

                    $.each(result, function(i){
                        str += $.trim(result[i].roleNm);
                        str += '\n';
                    });

                    $("#userAuthListTxt").append(str);

                }else{ alert("ERROR!");return;}
            }
        });
    }

    function fnGetRoleList() {
        var param = $("#userId").val();

        $.ajax({
            type:"POST",
            data:{"userId" : param},
            url:"/user/getUserRoleList.do",
            dataType:'json',
            //timeout:(1000*30),
            success:function(returnData, status){
                if(status == "success") {
                    $("#roleListDiv").empty();

                    var result1 = returnData.list1;
                    var result2 = returnData.list2;

                    var str = '';

                    $.each(result1, function(i){
                        str += '<tr>';
                        str += '<td><input type="checkbox" name="role" data="' + result1[i].roleNm + '" value="' + result1[i].id + '" ';
                        $.each(result2, function (k){
                            if(result1[i].id == result2[k].id){
                                str += 'checked';
                                return false;
                            }
                        });
                        str += '/></td>';
                        str += '<td>' + result1[i].roleNm + '</td>';
                        str += '</tr>';
                    });



                    $("#roleListDiv").append(str);

                    $("#roleListLayerPop").PopupWindow("open");

                }else{ alert("ERROR!");return;}
            }
        });
    }

    function fnSavePop(){
        var len = $("input[name='role']:checked").length;
        var str1 = "";
        var str2 = "";
        var cnt = 0;

        $("#isModRole").val("Y");

        if(len > 0){
            $("input[name='role']:checked").each(function(e){
                cnt++;
                str1 += $(this).val()
                if(cnt < len) str1 += ",";

                str2 += $.trim($(this).attr("data"));
                str2 += '\n';

            });
            $("#roleStr").val(str1);

            $("#userAuthListTxt").empty();
            $("#userAuthListTxt").append(str2);
        }else{
            $("#roleStr").val("nan");
            $("#userAuthListTxt").empty();
        }

        fnClosePop();
    }



    function fnSave(){
        Swal.fire({
            title: '변경된 정보를 저장 하시겠습니까?',
            text: "",
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
        var params = $("#modifyUserFrm").serialize();
        $.ajax({
            type:"POST",
            data:params,
            url:"/user/modifyUser.do",
            dataType:'html',
            contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
            //timeout:(1000*30),
            success:function(returnData, status){
                if(status == "success") {
                    Swal.fire({
                        icon: 'success',
                        title: '사용자 정보가 변경 되었습니다.',
                        text: '',
                    }).then((result) => {
                        window.location.href='/user/detail/'+$("#userId").val();
                    });
                }else{ alert("ERROR!");return;}
            }
        });
    }
</script>
<form id="modifyUserFrm" name="modifyUserFrm">
    <input type="hidden" id="userId" name="userId" value='<c:out value="${data.id}"/>'/>
    <input type="hidden" id="isModRole" name="isModRole" value=''/>
    <input type="hidden" id="roleStr" name="roleStr" value=''/>

    <div class="search_box mb_20">
        <div class="search_in" style="width: 600px;margin-left: 100px;">
            <div class="comm_search mb_20 mt_10">
                <div class="w_150px fl" style="line-height: 30px"><em>아이디</em></div>
                <input type="text" id="loginId" name="loginId" class="w_250px input_com l_radius_no" readonly="readonly" value='<c:out value="${data.loginId}"/>' placeholder="아이디" maxlength="20" style="border:1px solid #ccc;"/>
            </div>
            <div class="comm_search w_100p mb_20" style="line-height: 30px">
                <div class="w_150px fl"><em>사용자 명</em></div>
                <input type="text" id="userNm" name="userNm" class="w_250px input_com l_radius_no" <c:if test="${!isModify}">readonly="readonly"</c:if> value="<c:out value='${data.userNm}'/>" placeholder="사용자 명" maxlength="20" style="border:1px solid #ccc;"/>
            </div>
            <div class="comm_search w_100p mb_20" style="line-height: 30px">
                <div class="w_150px fl"><em>소속</em></div>
                <input type="text" id="deptNm" name="deptNm" class="w_250px input_com l_radius_no" <c:if test="${!isModify}">readonly="readonly"</c:if> value="<c:out value='${data.deptNm}'/>" placeholder="소속" maxlength="20" style="border:1px solid #ccc;"/>
            </div>
            <div class="comm_search w_100p mb_20" style="line-height: 30px">
                <div class="w_150px fl"><em>연락처</em></div>
                <input type="text" id="contactNo" name="contactNo" class="w_250px input_com l_radius_no" <c:if test="${!isModify}">readonly="readonly"</c:if> value="<c:out value='${data.contactNo}'/>" placeholder="연락처" maxlength="20" style="border:1px solid #ccc;"/>
            </div>
            <div class="comm_search w_100p mb_20" style="line-height: 30px">
                <div class="w_150px fl"><em>등록일시</em></div>
                <input type="text" id="createdAt" name="createdAt" class="w_250px input_com l_radius_no" readonly="readonly" value="<c:out value='${data.createdAt}'/>" placeholder="등록일시" maxlength="20" style="border:1px solid #ccc;"/>
            </div>
            <%--<div class="comm_search w_100p mb_20" style="line-height: 30px">
                <div class="w_150px fl"><em>최근 접속일시</em></div>
                <input type="text" id="recActDt" name="recActDt"  class="w_250px input_com l_radius_no" readonly="readonly" value="" placeholder="최근 접속일시" maxlength="20" style="border:1px solid #ccc;"/>
            </div>--%>
            <div class="comm_search w_100p mb_20" style="line-height: 30px">
                <div class="w_150px fl"><em>상태</em></div>
                <select name="activeYn" id="activeYn" size="1" class="w_150px input_com"  <c:if test="${!isModify}">disabled="disabled"</c:if>>
                    <option value="Y" <c:if test="${fn:contains(data.activeYn, 'Y')}"> selected</c:if>>승인</option>
                    <option value="N" <c:if test="${fn:contains(data.activeYn, 'N')}"> selected</c:if>>미승인</option>
                </select>
            </div>
            <div class="comm_search w_100p mb_20" style="line-height: 30px">
                <div class="w_150px fl"><em>권한목록</em></div>
                <div style="float:left;">
                    <textarea id="userAuthListTxt" name="userAuthListTxt" readonly="readonly" value="" placeholder="" rows="10" cols="50" style="border:1px solid #ccc;"></textarea>
                </div>
                <c:if test="${isModify}">
                    <button type="button" class="btn_small color_basic ml_3" style="float:left;margin-left: 30px;" onclick="fnGetRoleList();">선택</button>
                </c:if>
            </div>


            <div style="margin-top:300px;margin-left: 50px;">
                <button type="button" class="btn_middle color_basic ml_5" style="float:left" onclick="window.location.href='/user/list.do';">목록</button>
                <c:if test="${isModify}">
                    <button type="button" class="btn_middle color_basic ml_5" style="float:left;margin-left: 30px;" onclick="fnSave();">저장</button>
                    <button type="button" class="btn_middle color_basic ml_5" style="float:left;margin-left: 30px;" onclick="fnCancel();">취소</button>
                </c:if>
                <c:if test="${!isModify}">
                    <button type="button" class="btn_middle color_basic ml_5" style="float:left;margin-left: 30px;" onclick="window.location.href='/user/modify/${data.id}';">수정</button>
                    <button type="button" class="btn_middle color_basic ml_5" style="float:left;margin-left: 30px;" onclick="fnDelte();">삭제</button>
                </c:if>
            </div>

        </div>
    </div>
</form>


<!-- 사용자 권한 목록 레이어 팝업 -->
<div id="roleListLayerPop" class="example_content">
    <div class="popup_box box_w3">
        <div class="com_box ">
            <div class="txbox">
                <b class="fl mr_10">권한목록 선택</b>
            </div>
            <!--테이블 시작 -->
            <div class="tb_outbox" id="roleDiv">
                <table class="tb_list" >
                    <colgroup>
                        <col width="3%" />
                        <col width="15%" />
                    </colgroup>
                    <thead>
                    <tr>
                        <th>선택</th>
                        <th>권한명</th>
                    </tr>
                    </thead>
                    <tbody id="roleListDiv"></tbody>
                </table>
            </div>
        </div>
    </div>
    <div style="margin-top:20px;text-align: center;">
        <button type="button" class="btn_middle color_basic ml_5" onclick="fnSavePop()">확인</button>
        <button type="button" class="btn_middle color_basic ml_5" onclick="fnClosePop()">취소</button>
    </div>
</div>
<!-- /사용자 목록 레이어 팝업 -->