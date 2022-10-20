<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<script type="text/javascript">
  $(function() {
    $(".title_tx").html("출입이력");
  });

  $(function() {
    $("#fromDt, #toDt").datepicker({
      dateFormat: 'yy-mm-dd'
    });
  });

  function pageSearch(page){
    f = document.frmSearch;

    $("#srchPage").val(page);

    f.action = "/report/entHist/list.do";
    f.submit();
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
          <input class="w_150px input_com" type="text" readonly="readonly" id="fromDt" name="fromDt" placeholder="조회 시작일자" value="${data.fromDt}"> ~
          <input class="w_150px input_com" type="text" readonly="readonly" id="toDt" name="toDt" placeholder="조회 종료일자" value="${data.toDt}">
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
        <input type="text" class="w_150px input_com" id="keyword" name="keyword" value="<c:out value="${data.keyword}"/>" placeholder="사원명">
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
        <col width="30%" />
      </colgroup>
      <thead>
        <tr>
          <th>No.</th>
          <th>출입일자</th>
          <th>출입유형</th>
          <th>단말기코드</th>
          <th>모델명</th>
          <th>관리번호</th>
          <th>IP</th>
          <th>출입인증방식</th>
          <th>출입문 명</th>
          <th>건물</th>
          <th>성명</th>
          <th>사원번호</th>
          <th>부서</th>
          <th>소속</th>
          <th>출입사진</th>
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
          <td><c:out value="${sList.modelNm}"/></td>
          <td><c:out value="${sList.mgmtNum}"/></td>
          <td><c:out value="${sList.ipAddr}"/></td>
          <td><c:out value="${sList.complexAuthTypNm}"/></td>
          <td><c:out value="${sList.doorNm}"/></td>
          <td><c:out value="${sList.buildingNm}"/></td>
          <td><c:out value="${sList.empNm}"/></td>
          <td><c:out value="${sList.empNo}"/></td>
          <td><c:out value="${sList.deptNm}"/></td>
          <td><c:out value="${sList.belongNm}"/></td>
          <td><img src="/report/imagView/<c:out value="${sList.id}"/>" width="50px"></td>
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