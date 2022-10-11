<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<script type="text/javascript">
    $(function() {
        const _isModify = ${isModify};

        if(_isModify){
            $(".title_tx").html("출입문 권한 그룹 관리 - 수정");
        }else{
            $(".title_tx").html("출입문 권한 그룹 관리 - 상세");
        }

        fnGetAuthItem();

        $("input:radio[name=rdoAuthTyp]").click(function()
        {
            fnGetAuthItem();
        });

    });

    //유형별 권한대상 조회
    function fnGetAuthItem(){
        const param1 = $("input[name='rdoAuthTyp']:checked").val()
        const param2 = $("#id").val();

        $.ajax({
            type:"POST",
            data:{"authTyp" : param1, "id" : param2},
            url:"/auth/door/getAuthItem.do",
            dataType:'json',
            success:function(returnData, status){
                if(returnData.result == "success") {
                    $("#authTypNmTxt").empty();

                    const itemList = returnData.authItemList;

                    let str = '';

                    $.each(itemList, function(i){
                        str +=  itemList[i].itemNm
                        str += '\n';
                    });

                    $("#authTypNmTxt").append(str);

                }else{ alert("ERROR!");return;}
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

        const params = $("#modifyDoorFrm").serialize();

        $.ajax({
            type:"POST",
            data:params,
            url:"/auth/door/modifyDoor.do",
            dataType:'json',
            //timeout:(1000*30),
            success:function(returnData, status){
                if(returnData.result == "success") {
                    Swal.fire({
                        icon: 'success',
                        text: '출입문 권한정보가 변경 되었습니다.',
                    }).then((result) => {
                        window.location.href='/auth/door/detail/'+$("#id").val();
                    });
                }else{ alert("ERROR!");return;}
            }
        });
    }

    function fnCancel(){
        Swal.fire({
            text: '변경을 취소하시겠습니까?',
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

</script>

<form id="modifyDoorFrm" name="modifyDoorFrm">
    <input type="hidden" id="id" name="id" value='<c:out value="${data.id}"/>'/>

    <div class="search_box mb_20">
        <div class="search_in" style="width: 600px;margin-left: 100px;">
            <div class="comm_search mb_20 mt_10">
                <div class="w_150px fl" style="line-height: 30px"><em>출입권한그룹 명</em></div>
                <input type="text" id="authNm" name="authNm" class="w_250px input_com l_radius_no" <c:if test="${!isModify}">readonly="readonly"</c:if> value='<c:out value="${data.authNm}"/>' placeholder="출입권한그룹 명" maxlength="20" style="border:1px solid #ccc;"/>
            </div>
            <div class="comm_search mb_20 mt_10">
                <div class="w_150px fl" style="line-height: 30px"><em>사용</em></div>
                <c:if test="${!isModify}">
                    <input type="text" id="useYn" name="useYn"  class="w_250px input_com l_radius_no" readonly="readonly" value="<c:out value='${data.useYn}'/>" placeholder="사용" maxlength="20" style="border:1px solid #ccc;"/>
                </c:if>
                <c:if test="${isModify}">
                    <select name="useYn" id="useYn" size="1" class="w_150px input_com">
                        <option value="Y" <c:if test="${fn:contains(data.useYn, 'Y')}"> selected</c:if>>Y</option>
                        <option value="N" <c:if test="${fn:contains(data.useYn, 'N')}"> selected</c:if>>N</option>
                    </select>
                </c:if>
            </div>
            <div class="comm_search w_100p mb_20" style="line-height: 30px">
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
                <div class="comm_search mb_5 mt_5" style="margin-left: 140px;">
          <textarea id="authTypNmTxt" name="authTypNmTxt" readonly="readonly" placeholder="" rows="10" cols="50" style="border:1px solid #ccc;">
          </textarea>
                </div>
            </div>


            <div style="margin-top:300px;margin-left: 50px;">
                <c:if test="${isModify}">
                    <button type="button" class="btn_middle color_basic ml_5" style="float:left;margin-left: 30px;" onclick="fnSave();">저장</button>
                    <button type="button" class="btn_middle color_basic ml_5" style="float:left;margin-left: 30px;" onclick="fnCancel();">취소</button>
                </c:if>
                <c:if test="${!isModify}">
                    <button type="button" class="btn_middle color_basic ml_5" style="float:left" onclick="window.location.href='/auth/door/list.do';">목록</button>
                    <button type="button" class="btn_middle color_basic ml_5" style="float:left;margin-left: 30px;" onclick="window.location.href='/auth/door/modify/${data.id}';">수정</button>
                </c:if>
            </div>

        </div>
    </div>
</form>
