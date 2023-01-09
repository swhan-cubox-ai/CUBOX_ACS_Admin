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

<!--검색박스 -->
<script type="text/javascript">
  $(function() {
    const _isModify = ${isModify};

    if(_isModify){
      $(".title_tx").html("단말기 관리 - 수정");
    }else{
      $(".title_tx").html("단말기 관리 - 상세");
    }

    modalPopup("blackListLayerPop", "Black List", 910, 520);
    modalPopup("whiteListLayerPop", "White List", 910, 520);


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
        window.location.href='/terminal/detail/'+ $("#id").val();
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

    const params = $("#modifyTerminalFrm").serialize();

    $.ajax({
      type:"POST",
      data:params,
      url:"/terminal/modifyTerminal.do",
      dataType:'json',
      //timeout:(1000*30),
      success:function(returnData, status){
        if(returnData.result == "success") {
          Swal.fire({
            icon: 'success',
            text: '단말기 정보가 변경 되었습니다.',
          }).then((result) => {
            window.location.href='/terminal/detail/'+$("#id").val();
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
      url: "/terminal/deleteTerminal.do",
      dataType: 'json',
      //timeout:(1000*30),
      success: function (returnData) {
        if (returnData.result == "success") {
          Swal.fire({
            icon: 'success',
            text: '단말기가 삭제 되었습니다.'
          }).then((result) => {
            window.location.href = '/terminal/list.do';
          });
        } else {
          alert("ERROR!");
          return;
        }
      }
    });
  }


  function fnGetBlackList() {
    let paramKey = $("#id").val();

    $.ajax({
      type:"POST",
      url:"/terminal/getBlackList.do",
      data:{
        "id": paramKey
      },
      dataType:'json',
      success:function(returnData, status){

        if(status == "success") {
          $("#blackListDiv").empty();

          let result = returnData.list;
          let str = '';

          $.each(result, function(i){
            str += '<tr>';
            str += '<td>' + i + '</td>';
            str += '<td>' + result[i].empNo + '</td>';
            str += '<td>' + result[i].belongNm + '</td>';
            str += '<td>' + result[i].deptNm + '</td>';
            str += '<td>' + result[i].empNm + '</td>';
            str += '</tr>';
          });

          $("#blackListDiv").append(str);

          $("#blackListLayerPop").PopupWindow("open");

        }else{ alert("ERROR!");return;}
      }
    });
  }

  function fnGetWhiteList() {
    let paramKey = $("#id").val();

    $.ajax({
      type:"POST",
      url:"/terminal/getWhiteList.do",
      data:{
        "id": paramKey
      },
      dataType:'json',
      success:function(returnData, status){

        if(status == "success") {
          $("#whiteListDiv").empty();

          let result = returnData.list;
          let str = '';

          $.each(result, function(i){
            str += '<tr>';
            str += '<td>' + i + '</td>';
            str += '<td>' + result[i].empNo + '</td>';
            str += '<td>' + result[i].belongNm + '</td>';
            str += '<td>' + result[i].deptNm + '</td>';
            str += '<td>' + result[i].empNm + '</td>';
            str += '</tr>';
          });

          $("#whiteListDiv").append(str);

          $("#whiteListLayerPop").PopupWindow("open");

        }else{ alert("ERROR!");return;}
      }
    });
  }

  function closePopup(popupNm) {
    $("#"+popupNm).PopupWindow("close");
  }

</script>
<form id="modifyTerminalFrm" name="modifyTerminalFrm">
  <input type="hidden" id="id" name="id" value='<c:out value="${data.id}"/>'/>

  <div class="search_box mb_20">
    <div class="search_in" style="width: 600px;margin-left: 100px;">
      <div class="comm_search mb_20 mt_10">
        <div class="w_150px fl" style="line-height: 30px"><em>건물</em></div>
        <input type="text" id="buildingNm" name="buildingNm" class="w_250px input_com l_radius_no" readonly="readonly" value='<c:out value="${data.buildingNm}"/>' placeholder="건물" maxlength="20" style="border:1px solid #ccc;"/>
      </div>
      <div class="comm_search w_100p mb_20" style="line-height: 30px">
        <div class="w_150px fl"><em>층</em></div>
        <input type="text" id="floorNm" name="floorNm" class="w_250px input_com l_radius_no" readonly="readonly" value="<c:out value='${data.floorNm}'/>" placeholder="층" maxlength="20" style="border:1px solid #ccc;"/>
      </div>
      <div class="comm_search w_100p mb_20" style="line-height: 30px">
        <div class="w_150px fl"><em>출입문 명</em></div>
        <c:if test="${!isModify}">
          <input type="text" id="doorNm" name="doorNm" class="w_250px input_com l_radius_no" readonly="readonly" value="<c:out value='${data.doorNm}'/>" placeholder="출입문 명" maxlength="20" style="border:1px solid #ccc;"/>
        </c:if>
        <c:if test="${isModify}">
          <select name="doorId" id="doorId" size="1" class="w_150px input_com">
            <c:forEach var="list" items="${doorCombList}">
              <option <c:if test="${data.doorId == list.cd}">selected</c:if> value="${list.cd}">${list.cdNm}</option>
            </c:forEach>
          </select>
        </c:if>
      </div>
      <div class="comm_search w_100p mb_20" style="line-height: 30px">
        <div class="w_150px fl"><em>단말기 코드</em></div>
        <input type="text" id="terminalCd" name="terminalCd" class="w_250px input_com l_radius_no" <c:if test="${!isModify}">readonly="readonly"</c:if> value="<c:out value='${data.terminalCd}'/>" placeholder="단말기 코드" maxlength="20" style="border:1px solid #ccc;"/>
      </div>
      <div class="comm_search w_100p mb_20" style="line-height: 30px">
        <div class="w_150px fl"><em>관리번호</em></div>
        <input type="text" id="mgmtNum" name="mgmtNum" class="w_250px input_com l_radius_no" <c:if test="${!isModify}">readonly="readonly"</c:if> value="<c:out value='${data.mgmtNum}'/>" placeholder="관리번호" maxlength="20" style="border:1px solid #ccc;"/>
      </div>
      <div class="comm_search w_100p mb_20" style="line-height: 30px">
        <div class="w_150px fl"><em>단말기 유형</em></div>
        <c:if test="${!isModify}">
          <input type="text" id="terminalTypNm" name="terminalTypNm" class="w_250px input_com l_radius_no" readonly="readonly" value="<c:out value='${data.terminalTypNm}'/>" placeholder="단말기 유형" maxlength="20" style="border:1px solid #ccc;"/>
        </c:if>
        <c:if test="${isModify}">
           <select name="terminalTyp" id="terminalTyp" size="1" class="w_150px input_com">
             <c:forEach var="list" items="${terminalTypCombList}">
               <option <c:if test="${data.terminalTyp == list.cd}">selected</c:if> value="${list.cd}">${list.cdNm}</option>
             </c:forEach>
           </select>
        </c:if>
      </div>
      <div class="comm_search w_100p mb_20" style="line-height: 30px">
        <div class="w_150px fl"><em>모델명</em></div>
        <input type="text" id="modelNm" name="modelNm" class="w_250px input_com l_radius_no" <c:if test="${!isModify}">readonly="readonly"</c:if> value="<c:out value='${data.modelNm}'/>" placeholder="모델명" maxlength="20" style="border:1px solid #ccc;"/>
      </div>
      <div class="comm_search w_100p mb_20" style="line-height: 30px">
        <div class="w_150px fl"><em>IP</em></div>
        <input type="text" id="ipAddr" name="ipAddr" class="w_250px input_com l_radius_no" <c:if test="${!isModify}">readonly="readonly"</c:if> value="<c:out value='${data.ipAddr}'/>" placeholder="IP" maxlength="20" style="border:1px solid #ccc;"/>
      </div>
      <div class="comm_search w_100p mb_20" style="line-height: 30px">
        <div class="w_150px fl"><em>출입인증방식</em></div>
        <c:if test="${!isModify}">
          <input type="text" id="complexAuthTypNm" name="complexAuthTypNm" class="w_250px input_com l_radius_no" readonly="readonly" value="<c:out value='${data.complexAuthTypNm}'/>" placeholder="출입인증방식" maxlength="20" style="border:1px solid #ccc;"/>
        </c:if>
        <c:if test="${isModify}">
          <select name="complexAuthTyp" id="complexAuthTyp" size="1" class="w_150px input_com">
            <c:forEach var="list" items="${complexAuthTypCombList}">
              <option <c:if test="${data.complexAuthTyp == list.cd}">selected</c:if> value="${list.cd}">${list.cdNm}</option>
            </c:forEach>
          </select>
        </c:if>
      </div>
      <div class="comm_search w_100p mb_20" style="line-height: 30px">
        <div class="w_150px fl"><em>얼굴인증방식</em></div>
        <c:if test="${!isModify}">
          <input type="text" id="faceAuthTypNm" name="faceAuthTypNm" class="w_250px input_com l_radius_no" readonly="readonly" value="<c:out value='${data.faceAuthTypNm}'/>" placeholder="얼굴인증방식" maxlength="20" style="border:1px solid #ccc;"/>
        </c:if>
        <c:if test="${isModify}">
          <select name="faceAuthTyp" id="faceAuthTyp" size="1" class="w_150px input_com">
            <c:forEach var="list" items="${faceAuthTypCombList}">
              <option <c:if test="${data.faceAuthTyp == list.cd}">selected</c:if> value="${list.cd}">${list.cdNm}</option>
            </c:forEach>
          </select>
        </c:if>
      </div>
      <div class="comm_search w_100p mb_20" style="line-height: 30px">
        <div class="w_150px fl"><em>운영모드방식</em></div>
        <c:if test="${!isModify}">
          <input type="text" id="opModeTypNm" name="opModeTypNm" class="w_250px input_com l_radius_no" readonly="readonly" value="<c:out value='${data.faceAuthTypNm}'/>" placeholder="얼굴인증방식" maxlength="20" style="border:1px solid #ccc;"/>
        </c:if>
        <c:if test="${isModify}">
          <select name="OpModeTyp" id="OpModeTyp" size="1" class="w_150px input_com">
            <c:forEach var="list" items="${opModeTypCombList}">
              <option <c:if test="${data.OpModeTyp == list.cd}">selected</c:if> value="${list.cd}">${list.cdNm}</option>
            </c:forEach>
          </select>
        </c:if>
      </div>
      <div class="comm_search w_100p mb_20" style="line-height: 30px">
        <div class="w_150px fl"><em>Black List</em></div>
        <input type="text" id="blackListCnt" name="blackListCnt" class="w_250px input_com l_radius_no" readonly="readonly" value="<c:out value='${data.blackListCnt}'/>" placeholder="Black List" maxlength="20" style="border:1px solid #ccc;"/>
        <button type="button" class="btn_small color_basic ml_5" style="margin-left: 10px;" onclick="fnGetBlackList();">보기</button>
        <button type="button" class="btn_small color_basic ml_5" style="margin-left: 5px;" onclick="window.location.href='/terminal/black/modify/${data.id}';">수정/등록</button>
      </div>
      <div class="comm_search w_100p mb_20" style="line-height: 30px">
        <div class="w_150px fl"><em>White List</em></div>
        <input type="text" id="whiteListCnt" name="whiteListCnt" class="w_250px input_com l_radius_no" readonly="readonly" value="<c:out value='${data.whiteListCnt}'/>" placeholder="White List" maxlength="20" style="border:1px solid #ccc;"/>
        <button type="button" class="btn_small color_basic ml_5" style="margin-left: 10px;" onclick="fnGetWhiteList();">보기</button>
        <button type="button" class="btn_small color_basic ml_5" style="margin-left: 5px;" onclick="window.location.href='/terminal/white/modify/${data.id}';">수정/등록</button>
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

      <div style="margin-top:300px;margin-left: 50px;">
        <c:if test="${isModify}">
          <button type="button" class="btn_middle color_basic ml_5" style="float:left;margin-left: 30px;" onclick="fnSave();">저장</button>
          <button type="button" class="btn_middle color_basic ml_5" style="float:left;margin-left: 30px;" onclick="fnCancel();">취소</button>
        </c:if>
        <c:if test="${!isModify}">
          <button type="button" class="btn_middle color_basic ml_5" style="float:left" onclick="window.location.href='/terminal/list.do';">목록</button>
          <button type="button" class="btn_middle color_basic ml_5" style="float:left;margin-left: 30px;" onclick="window.location.href='/terminal/modify/${data.id}';">수정</button>
          <button type="button" class="btn_middle color_basic ml_5" style="float:left;margin-left: 30px;" onclick="fnDelete();">삭제</button>
        </c:if>
      </div>

    </div>
  </div>
</form>

<!-- 블랙리스트 목록 레이어 팝업 -->
<div id="blackListLayerPop" class="example_content">
  <div class="popup_box box_w3">

    <div class="com_box ">
      <div class="txbox">
        <b class="fl mr_10">Black List</b>
      </div>
      <!--테이블 시작 -->
      <div class="tb_outbox" style="margin-bottom: 30px;">
        <table class="tb_list" >
          <colgroup>
            <col width="3%" />
            <col width="9%" />
            <col width="10%" />
            <col width="15%" />
            <col width="15%" />
          </colgroup>
          <thead>
          <tr>
            <th>No.</th>
            <th>사원번호</th>
            <th>소속</th>
            <th>부서</th>
            <th>성명</th>
          </tr>
          </thead>
          <tbody id="blackListDiv"></tbody>
        </table>
      </div>
      <div style="margin-top:300px;text-align: center;">
        <button type="button" class="btn_middle color_basic ml_5" onclick="closePopup('blackListLayerPop')">확인</button>
      </div>
    </div>
  </div>
</div>
<!-- /블랙리스트 목록 레이어 팝업 -->

<!-- 화이트리스트 목록 레이어 팝업 -->
<div id="whiteListLayerPop" class="example_content">
  <div class="popup_box box_w3">

    <div class="com_box ">
      <div class="txbox">
        <b class="fl mr_10">White List</b>
      </div>
      <!--테이블 시작 -->
      <div class="tb_outbox" style="margin-bottom: 30px;">
        <table class="tb_list" >
          <colgroup>
            <col width="3%" />
            <col width="9%" />
            <col width="10%" />
            <col width="15%" />
            <col width="15%" />
          </colgroup>
          <thead>
          <tr>
            <th>No.</th>
            <th>사원번호</th>
            <th>소속</th>
            <th>부서</th>
            <th>성명</th>
          </tr>
          </thead>
          <tbody id="whiteListDiv"></tbody>
        </table>
      </div>
      <div style="margin-top:300px;text-align: center;">
        <button type="button" class="btn_middle color_basic ml_5" onclick="closePopup('whiteListLayerPop')">확인</button>
      </div>
    </div>
  </div>
</div>
<!-- /화이트리스트 목록 레이어 팝업 -->