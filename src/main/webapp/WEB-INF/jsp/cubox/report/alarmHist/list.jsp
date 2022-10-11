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
    $(".title_tx").html("알람이력");

    modalPopup("doorDetailLayerPop", "출입문 상세", 610, 520);
  });

  $(function() {
    $("#fromDt, #toDt").datepicker({
      dateFormat: 'yy-mm-dd'
    });
  });

  function pageSearch(page){
    f = document.frmSearch;

    $("#srchPage").val(page);

    f.action = "/report/alarmHist/list.do";
    f.submit();
  }

  function fnViewDoor(key) {
    let paramKey = key;

    $.ajax({
      type:"POST",
      url:"/report/alarmHist/getDoorDetail.do",
      data:{
        "doorId": paramKey
      },
      dataType:'json',
      success:function(returnData, status){

        if(status == "success") {
          let data =  returnData.data;
          $("#spnBuilding").text(data.buildingNm);
          $("#spnArea").text(data.areaNm);
          $("#spnFloor").text(data.floorNm);
          $("#spnDoorNm").text(data.doorNm);

          $("#doorDetailLayerPop").PopupWindow("open");

        }else{ alert("ERROR!");return;}
      }
    });
  }

  function closePopup(popupNm) {
    $("#doorDetailLayerPop").PopupWindow("close");
  }

</script>

<form id="frmSearch" name="frmSearch" method="post">
  <input type="hidden" id="srchPage" name="srchPage" value="${pagination.curPage}"/>
  <div class="search_box mb_20">
    <div class="search_in">
      <div class="comm_search mr_10">
        <select name="srchCond" id="srchCond" size="1" class="w_150px input_com">
          <option value="">전체</option>
          <c:forEach var="list" items="${doorAlarmEvtTypCombList}">
            <option value="${list.cd}" <c:if test="${data.srchCond eq list.cd}">selected</c:if>>${list.cdNm}</option>
          </c:forEach>
        </select>
      </div>
      <div class="comm_search mr_10">
        <p>
          <input class="w_150px input_com" type="text" readonly="readonly" id="fromDt" name="fromDt" placeholder="조회 시작일자" value="${data.fromDt}"> ~
          <input class="w_150px input_com" type="text" readonly="readonly" id="toDt" name="toDt" placeholder="조회 종료일자" value="${data.toDt}">
        </p>
      </div>
      <div class="comm_search  mr_10">
        <input type="text" class="w_150px input_com" id="keyword" name="keyword" value="<c:out value="${data.keyword}"/>" placeholder="출입문명">
      </div>
      <div class="comm_search ml_40">
        <div class="search_btn2" onclick="pageSearch('1')"></div>
      </div>
    </div>
  </div>
</form>

<div class="com_box ">
  <div class="totalbox">
    <div class="txbox">
      <b class="fl mr_10">전체 : <c:out value="${pagination.totRecord}"/>건</b>
    </div>
  </div>
  <!--테이블 시작 -->
  <div class="tb_outbox">
    <table class="tb_list">
      <colgroup>
        <col width="3%" />
        <col width="15%"/>
        <col width="10%" />
        <col width="10%" />
        <col width="20%" />
        <col width="15%" />
        <col width="3%" />
      </colgroup>
      <thead>
        <tr>
          <th>No.</th>
          <th>알람일시</th>
          <th>알람유형</th>
          <th>건물명</th>
          <th>출입문명</th>
          <th>출입인증방식</th>
          <th>단말기코드</th>
          <th>관리번호</th>
        </tr>
      </thead>
      <tbody id="entHistListBody">
      <c:if test="${alarmHistList == null || fn:length(alarmHistList) == 0}">
        <tr>
          <td class="h_35px" colspan="8">조회 목록이 없습니다.</td>
        </tr>
      </c:if>
      <c:forEach items="${alarmHistList}" var="sList" varStatus="status">
        <tr>
          <td>${(pagination.totRecord - (pagination.totRecord-status.index)+1)  + ( (pagination.curPage - 1)  *  pagination.recPerPage ) }</td>
          <td><c:out value="${sList.evtDt}"/></td>
          <td><c:out value="${sList.doorAlarmTypNm}"/></td>
          <td><c:out value="${sList.buildingNm}"/></td>
          <td><a onclick="fnViewDoor(${sList.doorId})"><c:out value="${sList.doorNm}"/></a></td>
          <td><c:out value="${sList.complexAuthTypNm}"/></td>
          <td><c:out value="${sList.terminalCd}"/></td>
          <td><c:out value="${sList.mgmtNum}"/></td>
          <td></td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
  </div>
  <!--------- //목록--------->
  <!-- 페이징 -->
  <jsp:include page="/WEB-INF/jsp/cubox/common/pagination.jsp" flush="false"/>
  <!-- /페이징 -->
</div>
</form>

<!-- 출입문정보 레이어 팝업 -->
<div id="doorDetailLayerPop" class="example_content">
  <div class="popup_box box_w3">

    <div class="com_box ">
      <div class="txbox">
        <b class="fl mr_10">출입문 상세</b>
      </div>
      <!--테이블 시작 -->
      <div class="tb_outbox">
        <table class="tb_list" >
          <colgroup>
            <col width="3%" />
            <col width="9%" />
          </colgroup>
          <tr>
            <th>건물</th>
            <td><span id="spnBuilding"></span></td>
          </tr>
          <tr>
            <th>구역</th>
            <td><span id="spnArea"></span></td>
          </tr>
          <tr>
            <th>층</th>
            <td><span id="spnFloor"></span></td>
          </tr>
          <tr>
            <th>출입문명</th>
            <td><span id="spnDoorNm"></span></td>
          </tr>
        </table>
      </div>
      <div style="margin-top:300px;text-align: center;">
        <button type="button" class="btn_middle color_basic ml_5" onclick="closePopup()">확인</button>
      </div>
    </div>
  </div>
</div>
<!-- /사용자 목록 레이어 팝업 -->
