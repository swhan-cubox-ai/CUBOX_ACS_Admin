<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:include page="/WEB-INF/jsp/cubox/errManagement/feature/errorPopup.jsp" flush="false"/>
<script type="text/javascript">
  $(function() {
    console.log("test");
    $(".title_tx").html("특징점추출오류");

    modalPopup("faceErrPopup", "추출 오류 상세", 1100, 1000);
  });

  function pageSearch(page){
    f = document.frmSearch;
    $("#srchPage").val(page);
    f.action = "/err/faceFeatureList.do";
    f.submit();
  }

  function detail(id) {
    $("#id").val(id);
    var item = null;
    <c:forEach items="${errList}" var="item" varStatus="status">
      if(id == ${item.id}) {
        openPopup("faceErrPopup");
        $("#sEmpCd").text("${item.emp_cd}");
        $("#sEmpNm").text("${item.emp_nm}");
        $("#sDeptNm").text("${item.dept_nm}");
        $("#sFaceStateTyp").text("${item.face_state_typ}");
        $("#sFaceStateTypNm").text("${item.face_state_typ_nm}");
        $("#sFaceFeatureTyp").text("${item.face_feature_typ}");
        $("#sFaceFeatureTypNm").text("${item.face_feature_typ_nm}");
        $("#sError").text('${item.error}');
        var img = fnGetFaceImage(id);
      }
    </c:forEach>
  }

  function openPopup(popupNm) {
    $("#" + popupNm).PopupWindow("open");
  }
  function closePopup(popupNm) {
    $("#" + popupNm).PopupWindow("close");
  }

  function fnGetFaceImage(id) {
    $.ajax({
      type: "POST",
      url: "<c:url value='faceFeature/detail'/>",
      data: {
        id: id,
      },
      dataType: "json",
      success: function(result) {
        document.getElementById("imagePreview").src = "data:image/png;base64," + result.errorFace;
      }
    });
  }

</script>

<form id="frmSearch" name="frmSearch" method="post">
  <input type="hidden" id="srchPage" name="srchPage" value="${pagination.curPage}"/>
  <input type="hidden" id="id" name="id"/>
  <div class="search_box mb_20">
    <div class="search_in_r">
      <div class="comm_search  mr_10">
        <input type="text" class="w_150px input_com" id="emp_cd" name="emp_cd" placeholder="사원코드"/>
        <input type="text" class="w_150px input_com" id="emp_nm" name="emp_nm" placeholder="사원명"/>
        <select name="feature_typ" id="feature_typ" size="1" class="w_150px input_com">
          <option value="">전체</option>
          <c:forEach var="list" items="${featureTypList}">
            <option value="${list.cd}" <c:if test="${data.face_feature_typ eq list.cd}">selected</c:if>>${list.cdNm}</option>
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
        <col width="25%"/>
        <col width="10%" />
        <col width="13%" />
        <col width="10%" />
        <col width="10%" />
        <col width="10%" />
      </colgroup>
      <thead>
        <tr>
          <th>일시</th>
          <th>특징점추출상태</th>
          <th>추출타입</th>
          <th>사원번호</th>
          <th>사원명</th>
          <th>부서명</th>
        </tr>
      </thead>
      <tbody id="errListBody">
      <c:if test="${errList == null || fn:length(errList) == 0}">
        <tr>
          <td class="h_35px" colspan="15">조회 목록이 없습니다.</td>
        </tr>
      </c:if>
      <c:forEach items="${errList}" var="item" varStatus="status">
        <tr>
          <td onclick="detail(<c:out value='${item.id}'/>)"><c:out value="${item.created_at}"/></td>
          <td><c:out value="${item.face_state_typ_nm}"/></td>
          <td><c:out value="${item.face_feature_typ_nm}"/></td>
          <td><c:out value="${item.emp_cd}"/></td>
          <td><c:out value="${item.emp_nm}"/></td>
          <td><c:out value="${item.dept_nm}"/></td>
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
