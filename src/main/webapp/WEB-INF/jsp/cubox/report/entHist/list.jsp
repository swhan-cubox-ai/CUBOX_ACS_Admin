<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:include page="/WEB-INF/jsp/cubox/report/entHist/detail.jsp" flush="false"/>

<script type="text/javascript">
  $(function() {
    $(".title_tx").html("출입이력");


    modalPopup("entHistDetail", "출입이력상세", 1100, 1000);
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

</script>

<form id="frmSearch" name="frmSearch" method="post">
  <input type="hidden" id="srchPage" name="srchPage" value="${pagination.curPage}"/>
  <div class="search_box mb_20">
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
          <td class="h_35px" colspan="15">조회 목록이 없습니다.</td>
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
