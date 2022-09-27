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
    $(".title_tx").html("메뉴권한 관리 - 목록");

    modalPopup("userListLayerPop", "사용자 목록", 910, 520);
  });


  function closePopup(popupNm) {
    $("#userListLayerPop").PopupWindow("close");
  }

  function fnGetUserRoleList(key) {
    let paramKey = key;

    $.ajax({
      type:"POST",
      url:"/menuAuth/getUserRoleList.do",
      data:{
        "roleId": paramKey
      },
      dataType:'json',
      success:function(returnData, status){

        if(status == "success") {
          $("#userRoleListDiv").empty();

          let result = returnData.list;
          let str = '';

          $.each(result, function(i){
            str += '<tr>';
            str += '<td>' + i + '</td>';
            str += '<td>' + result[i].userId + '</td>';
            str += '<td><a href="/user/detail/' + result[i].id + '">' + result[i].userNm + '</a></td>';
            str += '<td>' + result[i].deptNm + '</td>';
            str += '</tr>';
          });

          $("#userRoleListDiv").append(str);

          $("#userListLayerPop").PopupWindow("open");

        }else{ alert("ERROR!");return;}
      }
    });
  }


  function pageSearch(page){
    f = document.frmSearch;

    $("#srchPage").val(page);

    f.action = "/menuAuth/list.do";
    f.submit();
  }

  function fnAddMenuAuth(){
    window.location.href='/menuAuth/add.do';
  }

</script>
<form id="frmSearch" name="frmSearch" method="post">
  <input type="hidden" id="srchPage" name="srchPage" value="${pagination.curPage}"/>
  <!--//검색박스 -->
  <div class="search_box mb_20">
    <div class="search_in">
      <div class="comm_search mr_10">
        <select name="srchCond" id="srchCond" size="1" class="w_150px input_com">
          <option value="">전체</option>
        </select>
      </div>
      <div class="comm_search  mr_10">
        <input type="text" class="w_150px input_com" id="keyword" name="keyword" value="${data.roleNm}">
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
      <button type="button" class="btn_middle color_basic" id="excelDown">엑셀다운로드</button>
      <button type="button" class="btn_middle color_basic" id="addSiteHoliday" onclick="fnAddMenuAuth();">신규등록</button>
    </div>
    <!--//버튼  -->
  </div>
  <!--테이블 시작 -->
  <div class="tb_outbox">
    <table class="tb_list">
      <colgroup>
        <col width="3%" />
        <col width="20%" />
        <col width="9%" />
        <col width="9%" />
      </colgroup>
      <thead>
        <tr>
          <th>No.</th>
          <th>메뉴 권한 명</th>
          <th>사용자 수</th>
          <th>등록일자</th>
        </tr>
      </thead>
      <tbody>
      <c:if test="${menuAuthList == null || fn:length(menuAuthList) == 0}">
        <tr>
          <td class="h_35px" colspan="13">조회 목록이 없습니다.</td>
        </tr>
      </c:if>
      <c:forEach items="${menuAuthList}" var="sList" varStatus="status">
        <tr>
          <td>${(pagination.totRecord - (pagination.totRecord-status.index)+1)  + ( (pagination.curPage - 1)  *  pagination.recPerPage ) }</td>
          <td><a href='/menuAuth/detail/<c:out value="${sList.id}"/>'><c:out value="${sList.roleNm}"/></a></td>
          <td><a onclick="fnGetUserRoleList(${sList.id})"><c:out value="${sList.userCnt}"/></a></td>
          <td><c:out value="${sList.createdAt}"/></td>
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

<!-- 사용자 목록 레이어 팝업 -->
<div id="userListLayerPop" class="example_content">
  <div class="popup_box box_w3">

  <div class="com_box ">
      <div class="txbox">
        <b class="fl mr_10">사용자 목록</b>
      </div>
      <!--테이블 시작 -->
      <div class="tb_outbox" id="userRoleDiv">
          <table class="tb_list" >
            <colgroup>
              <col width="3%" />
              <col width="9%" />
              <col width="9%" />
              <col width="15%" />
            </colgroup>
            <thead>
            <tr>
              <th>No.</th>
              <th>아이디</th>
              <th>사용자명</th>
              <th>소속</th>
            </tr>
            </thead>
            <tbody id="userRoleListDiv"></tbody>
          </table>
      </div>
      <div style="margin-top:300px;text-align: center;">
        <button type="button" class="btn_middle color_basic ml_5" onclick="closePopup()">확인</button>
      </div>
    </div>
  </div>
</div>
<!-- /사용자 목록 레이어 팝업 -->


