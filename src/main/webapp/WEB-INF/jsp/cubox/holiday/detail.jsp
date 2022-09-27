<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!--검색박스 -->
<script type="text/javascript">
  $(function() {
    const _isModify = ${isModify};

    if(_isModify){
      $(".title_tx").html("공휴일 관리 - 수정");
    }else{
      $(".title_tx").html("공휴일 관리 - 상세");
    }

    $("#holiday, #holidayEnd").datepicker({
      dateFormat: 'yy-mm-dd'
    });
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
        window.location.href='/holiday/detail/'+ $("#id").val();
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

    const params = $("#modifyHolidayFrm").serialize();

    $.ajax({
      type:"POST",
      data:params,
      url:"/holiday/modifyHoliday.do",
      dataType:'json',
      //timeout:(1000*30),
      success:function(returnData, status){
        if(returnData.result == "success") {
          Swal.fire({
            icon: 'success',
            text: '공휴일 정보가 변경 되었습니다.',
          }).then((result) => {
            window.location.href='/holiday/detail/'+$("#id").val();
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
      url: "/holiday/deleteHoliday.do",
      dataType: 'json',
      //timeout:(1000*30),
      success: function (returnData) {
        if (returnData.result == "success") {
          Swal.fire({
            icon: 'success',
            text: '공휴일이 삭제 되었습니다.'
          }).then((result) => {
            window.location.href = '/holiday/list.do';
          });
        } else {
          alert("ERROR!");
          return;
        }
      }
    });
  }

</script>
<form id="modifyHolidayFrm" name="modifyHolidayFrm">
  <input type="hidden" id="id" name="id" value='<c:out value="${data.id}"/>'/>

  <div class="search_box mb_20">
    <div class="search_in" style="width: 600px;margin-left: 100px;">
      <div class="comm_search mb_20 mt_10">
        <div class="w_150px fl" style="line-height: 30px"><em>유형</em></div>
        <input type="text" id="holidayTyp" name="holidayTyp" class="w_250px input_com l_radius_no" readonly="readonly" value='<c:out value="${data.holidayTypNm}"/>' placeholder="유형" maxlength="20" style="border:1px solid #ccc;"/>
        <%--<c:if test="${!isModify}">
          <input type="text" id="holidayTyp" name="holidayTyp" class="w_250px input_com l_radius_no" readonly="readonly" value='<c:out value="${data.holidayTypNm}"/>' placeholder="유형" maxlength="20" style="border:1px solid #ccc;"/>
        </c:if>--%>
       <%-- <c:if test="${isModify}">
          <select name="holidayTyp" id="holidayTyp" size="1" class="w_150px input_com">
            <c:forEach var="list" items="${holidayTypList}">
              <option <c:if test="${data.holidayTyp == list.cd}">selected</c:if> value="${list.cd}">${list.cdNm}</option>
            </c:forEach>
          </select>
        </c:if>--%>
      </div>
      <div class="comm_search w_100p mb_20" style="line-height: 30px">
        <div class="w_150px fl"><em>공휴일 명</em></div>
        <input type="text" id="holidayNm" name="holidayNm" class="w_250px input_com l_radius_no" readonly="readonly" value="<c:out value='${data.holidayNm}'/>" placeholder="공휴일 명" maxlength="20" style="border:1px solid #ccc;"/>
      </div>
      <div class="comm_search w_100p mb_20" style="line-height: 30px">
        <div class="w_150px fl"><em>사용</em></div>
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
        <div class="w_150px fl"><em>일자</em></div>

        <c:if test="${!isModify}">
          <c:if test="${data.holidayTyp eq 'HD0003'}">
            <input type="text" id="holiday" name="holiday"  class="w_100px input_com l_radius_no" readonly="readonly" value="<c:out value='${data.holiday}'/>" placeholder="시작일자" maxlength="20" style="border:1px solid #ccc;"/>
             ~
            <input type="text" id="holidayEnd" name="holidayEnd"  class="w_100px input_com l_radius_no" readonly="readonly" value="<c:out value='${data.holidayEnd}'/>" placeholder="종료일자" maxlength="20" style="border:1px solid #ccc;"/>
          </c:if>
          <c:if test="${data.holidayTyp ne 'HD0003'}">
            <input type="text" id="holiday" name="holiday"  class="w_100px input_com l_radius_no" readonly="readonly" value="<c:out value='${data.holiday}'/>" placeholder="일자" maxlength="20" style="border:1px solid #ccc;"/>
          </c:if>
        </c:if>

        <c:if test="${isModify}">
          <c:if test="${data.holidayTyp eq 'HD0003'}">
            <input class="w_100px input_com l_radius_no" type="text" id="holiday" name="holiday" value="<c:out value='${data.holiday}'/> " placeholder="시작일자">
            ~
            <input class="w_100px input_com l_radius_no" type="text" id="holidayEnd" name="holidayEnd" value="<c:out value='${data.holidayEnd}'/>" placeholder="종료일자">
          </c:if>
          <c:if test="${data.holidayTyp ne 'HD0003'}">
            <input class="w_100px input_com l_radius_no" type="text" id="holiday" name="holiday" value="<c:out value='${data.holiday}'/>" placeholder="일자">
          </c:if>
        </c:if>
      </div>

      <div style="margin-top:300px;margin-left: 50px;">
        <c:if test="${isModify}">
          <button type="button" class="btn_middle color_basic ml_5" style="float:left;margin-left: 30px;" onclick="fnSave();">저장</button>
          <button type="button" class="btn_middle color_basic ml_5" style="float:left;margin-left: 30px;" onclick="fnCancel();">취소</button>
        </c:if>
        <c:if test="${!isModify}">
          <button type="button" class="btn_middle color_basic ml_5" style="float:left" onclick="window.location.href='/holiday/list.do';">목록</button>
          <button type="button" class="btn_middle color_basic ml_5" style="float:left;margin-left: 30px;" onclick="window.location.href='/holiday/modify/${data.id}';">수정</button>
          <button type="button" class="btn_middle color_basic ml_5" style="float:left;margin-left: 30px;" onclick="fnDelete();">삭제</button>
        </c:if>
      </div>

    </div>
  </div>
</form>