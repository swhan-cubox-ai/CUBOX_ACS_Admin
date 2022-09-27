<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script type="text/javascript">
    $(function() {
        $(".title_tx").html("공휴일 관리 - 등록");

        $("#holiday1, #holiday2, #holiday3").datepicker({
            dateFormat: 'yy-mm-dd'
        });

        $("#holiday1").datepicker("setDate", 'today');
        $("#holiday2").datepicker("setDate", 'today');
        $("#holiday3").datepicker("setDate", 'today');

        fnGenHolidayComboList();

        $("#holidayTxtDiv").css("display", "none");
        $("#holidayComboDiv").css("display", "block");
        $("#holiday1Div").css("display", "block");
        $("#holiday2Div").css("display", "none");

        //공휴일 유형 선택 이벤트
        $("#holidayTyp").change(function (){
            if($("#holidayTyp").val() == "HD0002"){ //임시
                $("#holidayTxtDiv").css("display", "block");
                $("#holidayComboDiv").css("display", "none");
                $("#holiday1Div").css("display", "block");
                $("#holiday2Div").css("display", "none");
            }else if($("#holidayTyp").val() == "HD0003"){ //연휴
                fnGenHolidayComboList();

                $("#holidayTxtDiv").css("display", "none");
                $("#holidayComboDiv").css("display", "block");
                $("#holiday1Div").css("display", "none");
                $("#holiday2Div").css("display", "block");
            }else{ //정기
                fnGenHolidayComboList();

                $("#holidayTxtDiv").css("display", "none");
                $("#holidayComboDiv").css("display", "block");
                $("#holiday1Div").css("display", "block");
                $("#holiday2Div").css("display", "none");
            }
        });
    });

    function fnGenHolidayComboList() {
        const param = $("#holidayTyp").val();

        $.ajax({
            type:"POST",
            data:{"holidayTyp" : param},
            url:"/holiday/getHolidayNmList.do",
            dataType:'json',
            //timeout:(1000*30),
            success:function(returnData, status){
                if(returnData.result == "success") {
                    $("#holidayComboDiv").empty();

                    const result = returnData.list;
                    let str = '<select name="holidayCd" id="holidayCd" size="1" class="w_150px input_com">';
                    str += '<option>선택</option>'
                    $.each(result, function(i){
                        str += '<option value="';
                        str += result[i].holidayCd;
                        str += '">';
                        str += result[i].holidayNm;
                        str += '</option>';
                    });

                    str += '</select>'

                    $("#holidayComboDiv").append(str);

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
                window.location.href='/holiday/list.do';
            }
        })
    }


    function fnSave(){
        if(fnIsValid()){
            Swal.fire({
                text: '공휴일을 등록 하시겠습니까?',
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
        const params = $("#addUserFrm").serialize();

        $.ajax({
            type:"POST",
            data:params,
            url:"/holiday/addHoliday.do",
            dataType:'json',
            //timeout:(1000*30),
            success:function(returnData){
                if(returnData.result == "success") {
                    Swal.fire({
                        icon: 'success',
                        text: '공휴일이 등록되었습니다.'
                    }).then((result) => {
                        window.location.href='/holiday/list.do';
                    });
                }else{ alert("ERROR!");return;}
            }
        });
    }


    function fnIsValid(){

        if($("#holidayTyp").val() == ""){
            Swal.fire({
                icon: 'warning',
                text: '필수입력 값이 누락되었습니다'
            });
            return false;
        }else{
            if($("#holidayTyp").val() == "HD0003"){
                if($("#useYn").val() == "" || $("#holiday2").val() == "" || $("#holiday3").val() == ""){
                    Swal.fire({
                        icon: 'warning',
                        text: '필수입력 값이 누락되었습니다'
                    });
                    return false;
                }
            }else{
                if($("#holidayTyp").val() == "HD0002"){
                    if($("#holidayNm").val() == "" || $("#useYn").val() == ""
                        || $("#holiday1").val() == ""){
                        Swal.fire({
                            icon: 'warning',
                            text: '필수입력 값이 누락되었습니다'
                        });
                        return false;
                    }
                }else{
                    if($("#useYn").val() == ""|| $("#holiday1").val() == ""){
                        Swal.fire({
                            icon: 'warning',
                            text: '필수입력 값이 누락되었습니다'
                        });
                        return false;
                    }
                }
            }
        }
        return true;
    }

</script>
<form id="addUserFrm" name="addUserFrm">

    <div class="search_box mb_20">
        <div class="search_in" style="width: 700px;margin-left: 100px;">
            <div class="comm_search w_100p mb_20" style="line-height: 30px">
                <div class="w_150px fl"><em>유형</em></div>
                <select name="holidayTyp" id="holidayTyp" size="1" class="w_150px input_com">
                    <c:forEach var="list" items="${holidayTypList}">
                        <option value="${list.cd}">${list.cdNm}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="comm_search w_100p mb_20" style="line-height: 30px">
                <div class="w_150px fl"><em>공휴일 명</em></div>
                <div id="holidayTxtDiv">
                    <input type="text" id="holidayNm" name="holidayNm" class="w_250px input_com l_radius_no" value="" required placeholder="공휴일 명" maxlength="20" style="border:1px solid #ccc;"/>
                </div>
                <div id="holidayComboDiv">
                </div>
            </div>
            <div class="comm_search w_100p mb_20" style="line-height: 30px">
                <div class="w_150px fl"><em>사용</em></div>
                <select name="useYn" id="useYn" size="1" class="w_150px input_com">
                    <option value="Y">Y</option>
                    <option value="N">N</option>
                </select>
            </div>
            <div class="comm_search w_100p mb_20" style="line-height: 30px">
                <div class="w_150px fl"><em>일자</em></div>
                <div id="holiday1Div">
                    <input class="w_100px input_com l_radius_no" type="text" id="holiday1" name="holiday1" value="">
                </div>
                <div id="holiday2Div">
                    <input class="w_100px input_com l_radius_no" type="text" id="holiday2" name="holiday2" value=""> ~
                    <input class="w_100px input_com l_radius_no" type="text" id="holiday3" name="holiday3" value="">
                </div>
            </div>

            <div style="margin-top:300px;margin-left: 50px;">
                <button type="button" class="btn_middle color_basic ml_5" style="float:left;margin-left: 30px;" onclick="fnSave();">저장</button>
                <button type="button" class="btn_middle color_basic ml_5" style="float:left;margin-left: 30px;" onclick="fnCancel();">취소</button>
            </div>

        </div>
    </div>
</form>