<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:include page="/WEB-INF/jsp/cubox/report/entHist/detail.jsp" flush="false"/>

<script type="text/javascript">
  $(function() {
    $(".title_tx").html("통계");
    // modalPopup("actLogDetail", "사용자활동로그상세", 1100, 1000);
  });

  $(function() {
    $("#fromDt, #toDt").datepicker({
      changeMonth: true,
      changeYear: true,
      dateFormat: 'yy-mm-dd',
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


    f.action = "/system/stat/list.do";
    f.submit();
  }

  function detail(id) {
    $("#id").val(id);

  }

  function openPopup(popupNm) {
    $("#" + popupNm).PopupWindow("open");
  }
  function closePopup(popupNm) {
    $("#" + popupNm).PopupWindow("close");
  }

  function fnChangeSrchCond(obj){
    if(obj.value == "month"){
      $("#fromDt, #toDt").datepicker("destroy");
      $('#fromDt, #toDt').monthpicker({
        pattern: 'yyyy-mm', // Default is 'mm/yyyy' and separator char is not mandatory
        monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월']
      });
    } else {
      $("#fromDt, #toDt").monthpicker("destroy");
      $("#fromDt, #toDt").datepicker({
        changeMonth: true,
        changeYear: true,
        dateFormat: 'yy-mm-dd',
      });
    }
  }




</script>

<form id="frmSearch" name="frmSearch" method="post">
  <input type="hidden" id="srchPage" name="srchPage" value="${pagination.curPage}"/>
  <div class="search_box mb_20">
    <div class="search_in">
      <div class="comm_search mr_10">
        <select name="srchCond4" id="srchCond" size="1" class="w_150px input_com" onchange="fnChangeSrchCond(this)">
          <option value="day"  <c:if test="${data.srchCond4 eq 'day'}">selected</c:if>>일별</option>
          <option value="month"  <c:if test="${data.srchCond4 eq 'month'}">selected</c:if>>월별</option>
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
          <option value="">건물 전체</option>
          <c:forEach var="list" items="${buildingCombList}">
            <option value="${list.cd}" <c:if test="${data.srchCond2 eq list.cd}">selected</c:if>>${list.cdNm}</option>
          </c:forEach>
        </select>
      </div>
      <div class="comm_search mr_10">
        <select name="srchCond1" id="srchCond1" size="1" class="w_150px input_com">
          <option value="">인증수단 전체</option>
          <c:forEach var="list" items="${cardTagTypList}">
            <option value="${list.cd}" <c:if test="${data.srchCond1 eq list.cd}">selected</c:if>>${list.cdNm}</option>
          </c:forEach>
        </select>
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
        <col width="12%" />
        <col width="10%" />
        <col width="10%"/>
      </colgroup>
      <thead>
        <tr>
          <th>일자/월</th>
          <th>인증수단</th>
          <th>건수</th>
        </tr>
      </thead>
      <tbody id="statListBody">
      <c:if test="${statList == null || fn:length(statList) == 0}">
        <tr>
          <td class="h_35px" colspan="3">조회 목록이 없습니다.</td>
        </tr>
      </c:if>
      <c:forEach items="${statList}" var="sList" varStatus="status">
        <tr>

          <td><c:out value="${sList.totDate}"/></td>
          <td><c:out value="${sList.cardTagTyp}"/></td>
          <td><c:out value="${sList.cnt}"/></td>
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
