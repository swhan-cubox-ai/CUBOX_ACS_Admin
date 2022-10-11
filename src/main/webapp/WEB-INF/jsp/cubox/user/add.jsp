<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<style>
    .layer {
        position: fixed;
        width: 40%;
        left: 50%;
        margin-left: -20%; /* half of width */
        height: 400px;
        top: 50%;
        margin-top: -150px; /* half of height */
        overflow: auto;

        /* decoration */
        border: 1px solid #000;
        background-color: #eee;
        padding: 1em;
        box-sizing: border-box;
        z-index: 2;
    }

    #userRoleDiv {
        white-space: pre;
        overflow-y: auto;
        height: 250px;
    }

    @media (max-width: 600px) {
        .layer {
            width: 80%;
            margin-left: -40%;
        }
    }
    .hidden {
        display: none;
    }
</style>


<script type="text/javascript">
    $(function() {
        $(".title_tx").html("사용자 관리 - 등록");
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
                window.location.href='/user/list.do';
            }
        })
    }

    function fnClosePop(){
        $("#roleListLayerPop").addClass("hidden");
    }

    function fnGetRoleList() {
        var userId = $("#userId").val();

        $.ajax({
            type:"POST",
            url:"/user/getRoleList.do",
            dataType:'json',
            //timeout:(1000*30),
            success:function(returnData, status){
                if(status == "success") {
                    $("#roleListDiv").empty();

                    var result = returnData.list;
                    var str = '';

                    $.each(result, function(i){
                        str += '<tr>';
                        str += '<td><input type="checkbox" name="role" data="' + result[i].roleNm + '" value="' + result[i].id + '"/></td>'
                        str += '<td>' + result[i].roleNm + '</td>';
                        str += '</tr>';
                    });

                    $("#roleListDiv").append(str);

                    $("#roleListLayerPop").removeClass("hidden");

                }else{ alert("ERROR!");return;}
            }
        });
    }

    function fnSavePop(){
        var len = $("input[name='role']:checked").length;
        var str1 = "";
        var str2 = "";
        var cnt = 0;

        if(len > 0){
            $("#isAddRole").val("Y");

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
        if(fnValidInput()){
            Swal.fire({
                title: '신규사용자를 등록 하시겠습니까?',
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
            });
        }
    }

    function fnSaveProc(){
        var params = $("#addUserFrm").serialize();
        $.ajax({
            type:"POST",
            data:params,
            url:"/user/addUser.do",
            dataType:'html',
            contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
            //timeout:(1000*30),
            success:function(returnData, status){
                if(status == "success") {
                    Swal.fire({
                        icon: 'success',
                        title: '사용자가 등록되었습니다.',
                        text: '',
                    }).then((result) => {
                        window.location.href='/user/list.do';
                    });
                }else{ alert("ERROR!");return;}
            }
        });
    }


    function fnValidInput(){
        if($("#loginId").val() == "" || $("#userNm").val() == "" || $("#deptNm").val() == ""
            || $("#loginId").val() == ""){
            Swal.fire({
                icon: 'warning',
                title: '필수입력 값이 누락되었습니다',
                text: '아이디, 사용자명, 소속 정보는 필수입력 입니다.',
            });
            return false;
        }
        return true;
    }

    function fnValidLoginId(){
        var param = $("#loginId").val();

        if(param == "") return;

        $.ajax({
            type:"POST",
            data:{"checkId":param},
            url:"/user/checkLoginId.do",
            dataType:'json',
            //timeout:(1000*30),
            success:function(returnData, status){
                if(status == "success") {
                    if(returnData.isDup == "Y"){
                        Swal.fire({
                            icon: 'warning',
                            title: '이미 사용중인 아이디 입니다.',
                            text: '',
                        }).then((result) => {
                            $("#loginId").focus();
                        });
                    }else{
                        Swal.fire({
                            icon: 'success',
                            title: '사용 가능한 아이디 입니다.',
                            text: '',
                        });
                    }

                }else{ alert("ERROR!");return;}
            }
        });
    }

</script>
<form id="addUserFrm" name="addUserFrm">
    <input type="hidden" id="roleStr" name="roleStr" value=''/>
    <input type="hidden" id="isAddRole" name="isAddRole" value=''/>

    <div class="search_box mb_20">
        <div class="search_in" style="width: 600px;margin-left: 100px;">
            <div class="comm_search mb_20 mt_10">
                <div class="w_150px fl" style="line-height: 30px"><em>아이디</em></div>
                <input type="text" id="loginId" name="loginId" class="w_250px input_com l_radius_no" value="" required placeholder="아이디" maxlength="20" style="border:1px solid #ccc;"/>
                <button type="button" class="btn_small color_basic ml_3" style="float:right;margin-left: 30px;" onclick="fnValidLoginId();">중복체크</button>
            </div>
            <div class="comm_search w_100p mb_20" style="line-height: 30px">
                <div class="w_150px fl"><em>사용자 명</em></div>
                <input type="text" id="userNm" name="userNm" class="w_250px input_com l_radius_no" value="" required placeholder="사용자 명" maxlength="20" style="border:1px solid #ccc;"/>
            </div>
            <div class="comm_search w_100p mb_20" style="line-height: 30px">
                <div class="w_150px fl"><em>소속</em></div>
                <input type="text" id="deptNm" name="deptNm" class="w_250px input_com l_radius_no" value="" required placeholder="소속" maxlength="20" style="border:1px solid #ccc;"/>
            </div>
            <div class="comm_search w_100p mb_20" style="line-height: 30px">
                <div class="w_150px fl"><em>연락처</em></div>
                <input type="text" id="contactNo" name="contactNo" class="w_250px input_com l_radius_no" value="" placeholder="연락처" maxlength="20" style="border:1px solid #ccc;"/>
            </div>
            <div class="comm_search w_100p mb_20" style="line-height: 30px">
                <div class="w_150px fl"><em>상태</em></div>
                <select name="activeYn" id="activeYn" size="1" class="w_150px input_com">
                    <option value="Y">승인</option>
                    <option value="N">미승인</option>
                </select>
            </div>
            <div class="comm_search w_100p mb_20" style="line-height: 30px">
                <div class="w_150px fl"><em>권한목록</em></div>
                <div style="float:left;">
                    <textarea id="userAuthListTxt" name="userAuthListTxt" readonly="readonly" value="" placeholder="" rows="10" cols="50" style="border:1px solid #ccc;"></textarea>
                </div>
                <button type="button" class="btn_small color_basic ml_3" style="float:left;margin-left: 30px;" onclick="fnGetRoleList();">선택</button>
            </div>


            <div style="margin-top:300px;margin-left: 50px;">
                <button type="button" class="btn_middle color_basic ml_5" style="float:left;margin-left: 30px;" onclick="fnSave();">저장</button>
                <button type="button" class="btn_middle color_basic ml_5" style="float:left;margin-left: 30px;" onclick="fnCancel();">취소</button>
            </div>

        </div>
    </div>
</form>


<!-- 사용자 권한 목록 레이어 팝업 -->
<div id="roleListLayerPop" class="js-layer  layer  hidden">
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
        <div style="margin-top:300px;text-align: center;">
            <button type="button" class="btn_middle color_basic ml_5" onclick="fnSavePop()">확인</button>
            <button type="button" class="btn_middle color_basic ml_5" onclick="fnClosePop()">취소</button>
        </div>
    </div>
</div>
<!-- /사용자 목록 레이어 팝업 -->