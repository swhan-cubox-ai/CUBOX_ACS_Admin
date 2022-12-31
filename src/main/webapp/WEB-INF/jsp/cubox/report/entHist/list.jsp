<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:include page="/WEB-INF/jsp/cubox/report/entHist/detail.jsp" flush="false"/>

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
    /*overflow: auto;*/
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

<style>
  .multiselect {
    width: 200px;
  }

  .selectBox {
    position: relative;
  }

  .selectBox select {
    width: 100%;
    font-weight: bold;
  }

  .overSelect {
    position: absolute;
    left: 0;
    right: 0;
    top: 0;
    bottom: 0;
  }

  #checkboxes {
    display: none;
    border: 1px #dadada solid;
  }

  #checkboxes label {
    display: block;
  }

  #checkboxes label:hover {
    background-color: #1e90ff;
  }
</style>

<script type="text/javascript">
  $(function() {
    $(".title_tx").html("출입이력");

    modalPopup("deptListLayerPop", "조회부서선택", 410, 620);
    modalPopup("entHistDetail", "출입이력상세", 1100, 1000);

    $("#checkDeptAll").click(function() {
      if ($("#checkDeptAll").prop("checked")) {
        $("input[name=checkDept]").prop("checked", true);
      } else {
        $("input[name=checkDept]").prop("checked", false);
      }
    });
  });

  $(function() {
    $("#fromDt, #toDt").datepicker({
      dateFormat: 'yy-mm-dd'
    });
  });

  function pageSearch(page){
    f = document.frmSearch;

    $("#srchPage").val(page);

    var fromDt = $("#fromDt").val();
    var toDt =$("#toDt").val();

    // if(fromDt == "" || toDt ){
    //   alert("조회 일자는 필수입니다.")
    //   return
    // }


    f.action = "/report/entHist/list.do";
    f.submit();
  }

  function detail(id) {
    $("#id").val(id);
    <c:forEach items="${entHistList}" var="item" varStatus="status">
    if(id == ${item.id}) {
      openPopup("entHistDetail");
      $("#id").text("${item.id}");
      $("#evtDt").text("${item.evtDt}");
      $("#entEvtTyp").text("${item.entEvtTyp}");
      $("#entEvtTypNm").text("${item.entEvtTypNm}");
      $("#empCd").text("${item.empCd}");
      $("#empNm").text("${item.empNm}");
      $("#faceId").text("${item.faceId}");
      $("#cardNo").text("${item.cardNo}");
      $("#cardClassTyp").text("${item.cardClassTyp}");
      $("#cardClassTypNm").text("${item.cardClassTypNm}");
      $("#cardStateTyp").text("${item.cardStateTyp}");
      $("#cardStateTypNm").text("${item.cardStateTypNm}");
      $("#cardTagTyp").text("${item.cardTagTyp}");
      $("#cardTagTypNm").text("${item.cardTagTypNm}");
      $("#begDt").text("${item.begDt}");
      $("#endDt").text("${item.endDt}");
      $("#authWayTyp").text("${item.authWayTyp}");
      $("#authWayTypNm").text("${item.authWayTypNm}");
      $("#matchScore").text("${item.matchScore}");
      $("#faceThreshold").text("${item.faceThreshold}");
      $("#captureAt").text("${item.captureAt}");
      $("#tagAt").text("${item.tagAt}");
      $("#tagCardNo").text("${item.tagCardNo}");
      $("#tagEmpCd").text("${item.tagEmpCd}");
      $("#temper").text("${item.temper}");
      $("#maskConfidence").text("${item.maskConfidence}");
      $("#terminalTyp").text("${item.terminalTyp}");
      $("#buildingCd").text("${item.buildingCd}");
      $("#buildingNm").text("${item.buildingNm}");
      $("#doorCd").text("${item.doorCd}");
      $("#doorNm").text("${item.doorNm}");
      $("#deptCd").text("${item.deptCd}");
      $("#deptNm").text("${item.deptNm}");
      $("#createdAt").text("${item.createdAt}");
      $("#updatedAt").text("${item.updatedAt}");
      var empCd = $("#empCd").text();
      var faceId = $("#faceId").text();
      var img = fnGetFaceImage(id, empCd, faceId);
    }
    </c:forEach>
  }

  function openPopup(popupNm) {
    $("#" + popupNm).PopupWindow("open");
    document.getElementById("imagePreview").src = '/images/loading.gif'
    document.getElementById("imageReg").src = '/images/loading.gif'
  }
  function closePopup(popupNm) {
    $("#" + popupNm).PopupWindow("close");
    document.getElementById("imagePreview").src = '/images/loading.gif'
    document.getElementById("imageReg").src = '/images/loading.gif'

  }

  function fnGetFaceImage(id, empCd, faceId) {
    $.ajax({
      type: "POST",
      url: "<c:url value='detail'/>",
      data: {
        id: id,
        empCd : empCd,
        faceId : faceId
      },
      dataType: "json",
      success: function(result) {
        if(!result.bioFace){
          document.getElementById("imagePreview").src = '/images/no-img.jpg'
        } else {
          document.getElementById("imagePreview").src = "data:image/png;base64," + result.bioFace;
        }

        if(!result.regFace){
          document.getElementById("imageReg").src = '/images/no-img.jpg'
        } else {
          document.getElementById("imageReg").src = "data:image/png;base64," + result.regFace;
        }

      }
    });
  }

  let expanded = false;

  function showCheckboxes() {
    let checkboxes = document.getElementById("checkboxes");
    if (!expanded) {
      checkboxes.style.display = "block";
      expanded = true;
    } else {
      checkboxes.style.display = "none";
      expanded = false;
    }
  }


  function showDeptPop() {
    $.ajax({
      type:"POST",
      url:"/common/getDeptList.do",
      dataType:'json',
      success:function(returnData, status){

        if(status == "success") {
          $("#deptListTb").empty();

          let result = returnData.list;
          let str = '';

          $.each(result, function(i){
            str += '<tr style="height: 3px;"><td><input type="checkbox" name="checkDept" value="';
            str += result[i].cd;
            str += '"/></td><td>';
            str += result[i].cd;
            str += '</td><td>';
            str += result[i].cdNm;
            str += '</td></tr>';
          });

          $("#deptListTb").append(str);

          $("#deptListLayerPop").PopupWindow("open");

        }else{ alert("ERROR!");return;}
      }
    });
  }

  function closeDeptPopup() {
    let checkedArray = [];

    $("input[name=checkDept]").each(function(i) {
      if($(this).is(":checked")){
        checkedArray.push($(this).val());
      }
    });

    $("#deptArray").val(checkedArray.join(","));

    $("#deptListLayerPop").PopupWindow("close");
  }

  function fnExcelDownLoad() {
    let url = '/report/entHist/excelDownload.do';
    $("#frmSearch").attr('action', url).submit();
  }

</script>



<form id="frmSearch" name="frmSearch" method="post">
  <input type="hidden" id="srchPage" name="srchPage" value="${pagination.curPage}"/>
  <input type="hidden" id="deptArray" name="deptArray" value="${data.deptArray}"/>

  <div class="search_box mb_20" style="height:68px;">

    <div class="comm_search mr_10">
    <div class="search_in">
      <div class="comm_search mr_10">
        <select name="srchCond1" id="srchCond1" size="1" class="w_150px input_com">
          <option value="">전체</option>
          <c:forEach var="list" items="${entEvtTypCombList}">
            <option value="${list.cd}" <c:if test="${data.srchCond1 eq list.cd}">selected</c:if>>${list.cdNm}</option>
          </c:forEach>
        </select>
      </div>
      <div class="comm_search mr_10">
        <p>
          <input class="w_150px input_datepicker" type="text" readonly="readonly" id="fromDt" name="fromDt" placeholder="조회 시작일자" value="${data.fromDt}"> ~
          <input class="w_150px input_datepicker" type="text" readonly="readonly" id="toDt" name="toDt" placeholder="조회 종료일자" value="${data.toDt}">
        </p>
      </div>



      <div class="comm_search mr_10">
        <select name="srchCond2" id="srchCond2" size="1" class="w_150px input_com">
          <option value="">전체</option>
          <c:forEach var="list" items="${buildingCombList}">
            <option value="${list.cd}" <c:if test="${data.srchCond2 eq list.cd}">selected</c:if>>${list.cdNm}</option>
          </c:forEach>
        </select>
      </div>
      <div class="comm_search  mr_10">
        <input type="text" class="w_150px input_com" id="keyword" name="keyword" value="<c:out value="${data.keyword}"/>" placeholder="사원명/사원코드">
      </div>
      <div class="comm_search ml_3">
        <button type="button" class="btn_middle color_basic ml_3" onclick="showDeptPop();">부서선택</button>
      </div>
      <div class="comm_search ml_40" style="margin-left: 53px;">
        <div class="search_btn2" onclick="pageSearch('1')"></div>
      </div>
    </div>
  </div>
  </div>
</form>

<div class="com_box ">
  <div class="totalbox" style="margin-top:20px;">
    <div class="txbox">
      <b class="fl mr_10">전체 : <c:out value="${pagination.totRecord}"/>건</b>
    </div>
    <div class="r_btnbox  mb_10">
      <button type="button" class="btn_excel" id="excelDown" onclick="fnExcelDownLoad()">엑셀다운로드</button>
    </div>
  </div>
  <!--테이블 시작 -->
  <div class="tb_outbox">
    <table class="tb_list">
      <colgroup>
        <col width="3%" />
        <col width="6%"/>
        <col width="6%" />
        <col width="6%" />
        <col width="6%" />
        <col width="6%" />
        <col width="9%" />
        <col width="9%" />
        <col width="9%" />
        <col width="5%" />
        <col width="6%" />
        <col width="6%" />
        <col width="6%" />
        <col width="6%" />
        <col width="6%" />
        <col width="6%" />
      </colgroup>
      <thead>
        <tr>
          <th>No.</th>
          <th>출입일시</th>
          <th>출입유형</th>
          <th>단말기코드</th>
          <th>이름</th>
          <th>부서</th>
          <th>카드번호</th>
          <th>카드유형</th>
          <th>카드상태</th>
          <th>태그유형</th>
          <th>시작일시</th>
          <th>종료일시</th>
          <th>인증유형</th>
          <th>건물</th>
          <th>출입문</th>
          <th>상세</th>
        </tr>
      </thead>
      <tbody id="entHistListBody">
      <c:if test="${entHistList == null || fn:length(entHistList) == 0}">
        <tr>
          <td class="h_35px" colspan="16">조회 목록이 없습니다.</td>
        </tr>
      </c:if>
      <c:forEach items="${entHistList}" var="sList" varStatus="status">
        <tr>
          <td>${(pagination.totRecord - (pagination.totRecord-status.index)+1)  + ( (pagination.curPage - 1)  *  pagination.recPerPage ) }</td>
          <td><c:out value="${sList.evtDt}"/></td>
          <td><c:out value="${sList.entEvtTypNm}"/></td>
          <td><c:out value="${sList.terminalCd}"/></td>
          <td><c:out value="${sList.empNm}"/></td>
          <td><c:out value="${sList.deptNm}"/></td>
          <td><c:out value="${sList.cardNo}"/></td>
          <td><c:out value="${sList.cardClassTypNm}"/></td>
          <td><c:out value="${sList.cardStateTypNm}"/></td>
          <td><c:out value="${sList.cardTagTypNm}"/></td>
          <td><c:out value="${sList.begDt}"/></td>
          <td><c:out value="${sList.endDt}"/></td>
          <td><c:out value="${sList.authWayTypNm}"/></td>
          <td><c:out value="${sList.buildingNm}"/></td>
          <td><c:out value="${sList.doorNm}"/></td>
          <td onclick="detail(<c:out value='${sList.id}'/>)">상세보기</td>
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


<!-- 부서선택 목록 레이어 팝업 -->
<div id="deptListLayerPop" class="example_content">
  <div class="popup_box box_w3">
  <table class="tb_list tb_write_02 tb_write_p1">
    <colgroup>
      <col style="width:5%">
      <col style="width:5%">
      <col style="width:15%">
    </colgroup>
    <thead>
    <tr>
      <th><input type="checkbox" id="checkDeptAll"></th>
      <th>부서코드</th>
      <th>부서명</th>
    </tr>
    </thead>
    <tbody id="deptListTb">
    </tbody>
  </table>
  </div>
  <div style="text-align: center;">
    <button type="button" class="btn_middle color_basic ml_5" onclick="closeDeptPopup()">확인</button>
  </div>
</div>
