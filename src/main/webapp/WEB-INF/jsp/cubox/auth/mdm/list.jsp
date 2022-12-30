<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!--검색박스 -->
<script type="text/javascript">
    $(function() {
        $(".title_tx").html("mdm정보 - 목록");
    });

    function pageSearch(page){
        const f = document.frmSearch;
        $("#srchPage").val(page);
        f.action = "/auth/mdm/list.do";
        f.submit();
    }


</script>
<form id="frmSearch" name="frmSearch" method="post">
    <input type="hidden" id="srchPage" name="srchPage" value="${pagination.curPage}"/>
    <!--//검색박스 -->
    <div class="search_box mb_20">
        <div class="search_in">
            <div class="comm_search  mr_10">
                <input type="text" class="w_150px input_com" id="keyword1" name="keyword1" placeholder="카드번호" value="<c:out value="${data.keyword1}"/>">
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
        <!--//버튼  -->
    </div>
    <!--테이블 시작 -->
    <div class="tb_outbox">
        <table class="tb_list">
            <colgroup>
                <col width="3%" />
                <col width="5%"/>
                <col width="5%"/>
                <col width="7%" />
                <col width="7%" />
                <col width="7%" />
                <col width="7%" />
                <col width="6%" />
                <col width="9%" />
                <col width="9%" />
            </colgroup>
            <thead>
            <tr>
                <th>cgpn_hr_sn</th>
                <th>테이블</th>
                <th>인사번호</th>
                <th>성명</th>
                <th>카드상태</th>
                <th>카드번호</th>
                <th>시작일시</th>
                <th>만료일시</th>
                <th>등록일자</th>
                <th>동기화여부</th>
            </tr>
            </thead>
            <tbody id="mdmListBody">
            <c:if test="${mdmList == null || fn:length(mdmList) == 0}">
                <tr>
                    <td class="h_35px" colspan="13">조회 목록이 없습니다.</td>
                </tr>
            </c:if>
            <c:forEach items="${mdmList}" var="sList" varStatus="status">
                <tr>
                    <td><c:out value="${sList.cgpn_hr_sn}"/></td>
                    <td><c:out value="${sList.tableTyp}"/></td>
                    <td><c:out value="${sList.hr_no}"/></td>
                    <td><c:out value="${sList.cgpn_nm}"/></td>
                    <td><c:out value="${sList.card_se}"/></td>
                    <td><c:out value="${sList.issu_no}"/></td>
                    <td><c:out value="${sList.cmg_begin_dt}"/></td>
                    <td><c:out value="${sList.cmg_end_dt}"/></td>
                    <td><c:out value="${sList.creat_dt}"/></td>
                    <td><c:out value="${sList.process_yn_mdmsjsc}"/></td>
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