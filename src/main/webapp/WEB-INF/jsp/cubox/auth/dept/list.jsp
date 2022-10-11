<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!--검색박스 -->
<script type="text/javascript">
    $(function() {
        $(".title_tx").html("부서관리 - 목록");
    });

    $(function() {

    });

    function pageSearch(page){
        f = document.frmSearch;

        $("#srchPage").val(page);

        f.action = "/auth/dept/list.do";
        f.submit();
    }


</script>
<form id="frmSearch" name="frmSearch" method="post">
    <input type="hidden" id="srchPage" name="srchPage" value="${pagination.curPage}"/>
    <!--//검색박스 -->
    <div class="search_box mb_20">
        <div class="search_in">
            <div class="comm_search  mr_10">
                <input type="text" class="w_150px input_com" id="keyword" name="keyword" placeholder="기관명/부서명" value="<c:out value="${data.keyword}"/>">
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
        </div>	<!--버튼 -->
        <div class="r_btnbox  mb_10">
            <button type="button" class="btn_excel color_basic" id="excelDown">엑셀다운로드</button>
        </div>
        <!--//버튼  -->
    </div>
    <!--테이블 시작 -->
    <div class="tb_outbox">
        <table class="tb_list">
            <colgroup>
                <col width="3%" />
                <col width="7%"/>
                <col width="7%" />
                <col width="6%" />
            </colgroup>
            <thead>
            <tr>
                <th>No.</th>
                <th>기관</th>
                <th>부서</th>
                <th>등록일자</th>
            </tr>
            </thead>
            <tbody id="holidayListBody">
            <c:if test="${deptList == null || fn:length(deptList) == 0}">
                <tr>
                    <td class="h_35px" colspan="13">조회 목록이 없습니다.</td>
                </tr>
            </c:if>
            <c:forEach items="${deptList}" var="sList" varStatus="status">
                <tr>
                    <td>${(pagination.totRecord - (pagination.totRecord-status.index)+1)  + ( (pagination.curPage - 1)  *  pagination.recPerPage ) }</td>
                    <td><c:out value="${sList.insttNm}"/></td>
                    <td><c:out value="${sList.deptNm}"/></td>
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