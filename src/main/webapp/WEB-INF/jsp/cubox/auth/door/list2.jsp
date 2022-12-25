<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!--검색박스 -->
<script type="text/javascript">
    $(function() {
        $(".title_tx").html("출입문 권한그룹 관리(부서) - 목록");
    });

    $(document).on('click', "input[name ='checkAuth']", function(){
        if(this.checked) {
            const checkboxes = $("input[name='checkAuth']");
            for(let ind = 0; ind < checkboxes.length; ind++){
                checkboxes[ind].checked = false;
            }
            this.checked = true;
        } else {
            this.checked = false;
        }
    });

    function pageSearch(page){
        const f = document.frmSearch;
        $("#srchPage").val(page);
        f.action = "/auth/door/list2.do";
        f.submit();
    }

    function fnAddAuth(){
        window.location.href = "/auth/door/add2.do";
    }

    function fnExcelDownLoad() {
        let url = '/auth/door/excelDownload.do';
        $("#frmSearch").attr('action', url).submit();
    }

    function fnAssignEmp() {
       let authId =  $('input:checkbox[name="checkAuth"]:checked').val();
       if(authId == undefined){
           Swal.fire({
               icon: 'info',
               text: '선택된 출입권한그룹이 없습니다',
           });
       }else{
           window.location.href = "/auth/door/assign2/" + authId;
       }
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
                    <c:forEach var="list" items="${authTypList}">
                        <option value="${list.cd}" <c:if test="${data.srchCond eq list.cd}">selected</c:if>>${list.cdNm}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="comm_search  mr_10">
                <input type="text" class="w_150px input_com" id="keyword" name="keyword" placeholder="출입권한그룹명" value="<c:out value="${data.keyword}"/>">
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
            <button type="button" class="btn_excel" id="excelDown" onclick="fnExcelDownLoad()">엑셀다운로드</button>
            <button type="button" class="btn_middle color_basic" id="assignEmp" onclick="fnAssignEmp()">사원등록</button>
        </div>
        <!--//버튼  -->
    </div>
    <!--테이블 시작 -->
    <div class="tb_outbox">
        <table class="tb_list">
            <colgroup>
                <col width="2%" />
                <col width="2%" />
                <col width="9%"/>
                <col width="5%" />
                <col width="3%" />
                <col width="7%" />
                <col width="3%" />
            </colgroup>
            <thead>
            <tr>
                <th>선택</th>
                <th>No.</th>
                <th>출입권한그룹 명</th>
                <th>유형</th>
                <th>사원수</th>
                <th>등록일자</th>
                <th>사용</th>
            </tr>
            </thead>
            <tbody>
            <c:if test="${authList == null || fn:length(authList) == 0}">
                <tr>
                    <td class="h_35px" colspan="13">조회 목록이 없습니다.</td>
                </tr>
            </c:if>
            <c:forEach items="${authList}" var="sList" varStatus="status">
                <tr>
                    <td><input type="checkbox" name="checkAuth" value="${sList.id}"/></td>
                    <td>${(pagination.totRecord - (pagination.totRecord-status.index)+1)  + ( (pagination.curPage - 1)  *  pagination.recPerPage ) }</td>
                    <td><a href='/auth/door/detail/<c:out value="${sList.id}"/>'><c:out value="${sList.authNm}"/></a></td>
                    <td><c:out value="${sList.authTypNm}"/></td>
                    <td><c:out value="${sList.authEmpCnt}"/></td>
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