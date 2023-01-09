<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!--검색박스 -->
<script type="text/javascript">
  $(function() {
    $(".title_tx").html("단말기 관리 - 목록");
  });

  $(function() {
    $("#startDt, #endDt").datepicker({
      dateFormat: 'yy-mm-dd'
    });
  });

  function pageSearch(page){
    const f = document.frmSearch;
    $("#srchPage").val(page);
    f.action = "/terminal/list.do";
    f.submit();
  }

  function fnAddTerminal(){
    window.location.href='/terminal/add.do';
  }

  function fnExcelDownLoad() {
    let url = '/terminal/excelDownload.do';
    $("#frmSearch").attr('action', url).submit();
  }

</script>
<form id="frmSearch" name="frmSearch" method="post">
  <input type="hidden" id="srchPage" name="srchPage" value="${pagination.curPage}"/>
  <!--//검색박스 -->
  <div class="search_box mb_20">
    <div class="search_in">
      <div class="comm_search mr_10">
        <select name="srchCond1" id="srchCond1" size="1" class="w_150px input_com">
          <option value="">전체</option>
           <c:forEach var="list" items="${buildingCombList}">
              <option value="${list.cd}" <c:if test="${data.srchCond1 eq list.cd}">selected</c:if>>${list.cdNm}</option>
            </c:forEach>
        </select>
      </div>
      <div class="comm_search mr_10">
        <select name="srchCond2" id="srchCond2" size="1" class="w_150px input_com">
          <option value="">전체</option>
          <c:forEach var="list" items="${terminalTypCombList}">
            <option value="${list.cd}" <c:if test="${data.srchCond2 eq list.cd}">selected</c:if>>${list.cdNm}</option>
          </c:forEach>
        </select>
      </div>
      <div class="comm_search  mr_10">
        <input type="text" class="w_150px input_com" id="keyword" name="keyword" placeholder="단말기코드/관리번호" value="${data.keyword}">
      </div>
      <div class="comm_search ml_40">
        <div class="search_btn2" onclick="pageSearch('1')"></div>
      </div>
    </div>
  </div>
  <!--//검색박스 -->
</form>

<div class="com_box ">
  <div class="totalbox">
    <div class="txbox">
      <b class="fl mr_10">전체 : <c:out value="${pagination.totRecord}"/>건</b>
      <!-- 건수 -->
     <%-- <select name="srchRecPerPage" id="srchRecPerPage" class="input_com w_80px">
        <c:if test="${cntPerPage == null || fn:length(cntPerPage) == 0}">
          <c:forEach items="${cntPerPage}" var="cntPerPage" varStatus="status">
            <option value='<c:out value="${cntPerPage.fvalue}"/>' <c:if test="${cntPerPage.fvalue eq pagination.recPerPage}">selected</c:if>><c:out value="${cntPerPage.fkind3}"/></option>
          </c:forEach>
        </c:if>
      </select>--%>
    </div>	<!--버튼 -->
    <div class="r_btnbox  mb_10">
      <button type="button" class="btn_excel" id="excelFormDown">일괄등록 양식다운로드</button>
      <button type="button" class="btn_excel" id="excelDown" onclick="fnExcelDownLoad()">엑셀다운로드</button>
      <button type="button" class="btn_middle color_basic" id="addTerminal" onclick="fnAddTerminal()">신규등록</button>
      <button type="button" class="btn_middle color_basic" id="addAllTerminal">일괄등록</button>
    </div>
    <!--//버튼  -->
  </div>
  <!--테이블 시작 -->
  <div class="tb_outbox">
    <table class="tb_list">
      <colgroup>
        <col width="3%" />
        <col width="6%"/>
        <col width="6%" />
        <col width="15%" />
        <col width="9%" />
        <col width="6%" />
        <col width="6%" />
        <col width="15%" />
        <col width="6%" />
        <col width="6%" />
        <col width="3%" />
        <col width="3%" />
        <col width="9%" />
        <col width="3%" />
      </colgroup>
      <thead>
        <tr>
          <th>No.</th>
          <th>건물</th>
          <th>층</th>
          <th>출입문명</th>
          <th>단말기코드</th>
          <th>관리번호</th>
          <th>단말기유형</th>
          <th>IP</th>
          <th>출입인증방식</th>
          <th>얼굴인증방식</th>
          <th>Black List</th>
          <th>White List</th>
          <th>등록일자</th>
          <th>사용</th>
        </tr>
      </thead>
      <tbody id="terminalListBody">
      <c:if test="${terminalList == null || fn:length(terminalList) == 0}">
        <tr>
          <td class="h_35px" colspan="13">조회 목록이 없습니다.</td>
        </tr>
      </c:if>
      <c:forEach items="${terminalList}" var="sList" varStatus="status">
        <tr>
          <td>${(pagination.totRecord - (pagination.totRecord-status.index)+1)  + ( (pagination.curPage - 1)  *  pagination.recPerPage ) }</td>
          <td><c:out value="${sList.buildingNm}"/></td>
          <td><c:out value="${sList.floorNm}"/></td>
          <td><c:out value="${sList.doorNm}"/></td>
          <td><a href='/terminal/detail/<c:out value="${sList.id}"/>'><c:out value="${sList.terminalCd}"/></a></td>
          <td><c:out value="${sList.mgmtNum}"/></td>
          <td><c:out value="${sList.terminalTypNm}"/></td>
          <td><c:out value="${sList.ipAddr}"/></td>
          <td><c:out value="${sList.complexAuthTypNm}"/></td>
          <td><c:out value="${sList.faceAuthTypNm}"/></td>
          <td><c:out value="${sList.blackListCnt}"/></td>
          <td><c:out value="${sList.whiteListCnt}"/></td>
          <td><c:out value="${sList.createdAt}"/></td>
          <td><c:out value="${sList.useYn}"/></td>
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