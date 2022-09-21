<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script type="text/javascript">

    $(function() {
        var isModify = ${isModify};

        if(isModify){
            $(".title_tx").html("사용자 관리 - 수정");
        }else{
            $(".title_tx").html("사용자 관리 - 상세");
        }

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

