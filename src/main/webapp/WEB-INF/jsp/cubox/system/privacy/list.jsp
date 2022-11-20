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

<script type="text/javascript">
    $(function() {
        $(".title_tx").html("개인정보 폐기");

        modalPopup("empDetailLayerPop", "인사정보-상세", 610, 400);
    });

    $(function() {
        $("#fromDt, #toDt").datepicker({
            dateFormat: 'yy-mm-dd'
        });

        $("#checkAll").click(function() {
            if ($("#checkAll").prop("checked")) {
                $("input[name=checkEmp]").prop("checked", true);
            } else {
                $("input[name=checkEmp]").prop("checked", false);
            }
        });
    });

    function pageSearch(page){
        const f = document.frmSearch;
        $("#srchPage").val(page);
        f.action = "/system/privacy/list.do";
        f.submit();
    }

    function fnViewEmpDetailPop(key) {
        let paramKey = key;

        $.ajax({
            type:"POST",
            url:"/system/privacy/getEmpDetail.do",
            data:{
                "empCd": paramKey
            },
            dataType:'json',
            success:function(returnData, status){

                if(status == "success") {
                    let data =  returnData.data;
                    $("#empPopNm").text(data.empNm);
                    $("#belongPopNm").text(data.belongNm);
                    $("#insttPopNm").text(data.insttNm);
                    $("#deptPopNm").text(data.deptNm);
                    $("#expiredPopAt").text(data.expiredDt);

                    $("#empDetailLayerPop").PopupWindow("open");

                }else{ alert("ERROR!");return;}
            }
        });
    }

    function fnDelAll(){
        if($("#totalCnt").val() == "0"){
            Swal.fire({
                icon: 'info',
                text: '조회후 삭제를 진행하여 주십시요.'
            });
        }else{
            Swal.fire({
                text: '조회된 ' + $("#totalCnt").val() + '건의 정보를 일괄삭제 하시겠습니까?',
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'OK',
                cancelButtonText: 'CANCEL',
                reverseButtons: true,

            }).then((result) => {
                if (result.isConfirmed) {
                    fnDelAllProc();
                }
            });
        }
    }

    function fnDelAllProc(){
        let params = $("#frmSearch").serialize();

        $.ajax({
            type: "POST",
            data: params,
            url: "/system/privacy/delAllPrivacy.do",
            dataType: 'json',
            success: function (returnData) {
                if (returnData.result == "success") {
                    Swal.fire({
                        icon: 'success',
                        text: '조회된 ' + $("#totalCnt").val() + '건의 정보가 일괄삭제 되었습니다.'
                    }).then((result) => {
                        window.location.href = '/system/privacy/list.do';
                    });
                } else {
                    alert("ERROR!");
                    return;
                }
            }
        });
    }

    function fnDelSelect(){

        let ckd = $("input[name=checkEmp]:checked").length;

        if(ckd > 0){
            Swal.fire({
                text: '선택된 정보를 삭제 하시겠습니까?',
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'OK',
                cancelButtonText: 'CANCEL',
                reverseButtons: true,

            }).then((result) => {
                if (result.isConfirmed) {
                    fnDelSelectProc();
                }
            });
        }else{
            Swal.fire({
                icon: 'info',
                text: '선택된 정보가 없습니다',
            });
        }
    }

    function fnDelSelectProc(){
        let checkedArray = [];

        $("input[name=checkEmp]:checked").each(function(i) {
            checkedArray.push($(this).val());
        });

        $("#checkedEmpArray").val(checkedArray.join(","));

        let params = $("#frmSearch").serialize();

        $.ajax({
            type: "POST",
            data: params,
            url: "/system/privacy/delSelectedPrivacy.do",
            dataType: 'json',
            success: function (returnData) {
                if (returnData.result == "success") {
                    Swal.fire({
                        icon: 'success',
                        text: '선택된 정보가 삭제 되었습니다.'
                    }).then((result) => {
                        window.location.href = '/system/privacy/list.do';
                    });
                } else {
                    alert("ERROR!");
                    return;
                }
            }
        });
    }

    function fnClosePop() {
        $("#empDetailLayerPop").PopupWindow("close");
    }

    function fnExcelDownLoad() {
        let url = '/system/privacy/excelDownload.do';
        $("#frmSearch").attr('action', url).submit();
    }

</script>
<form id="frmSearch" name="frmSearch" method="post">
    <input type="hidden" id="srchPage" name="srchPage" value="${pagination.curPage}"/>
    <input type="hidden" id="totalCnt"  value="${pagination.totRecord}"/>
    <input type="hidden" id="checkedEmpArray" name="checkedEmpArray"/>

    <!--//검색박스 -->
    <div class="search_box mb_20">
        <div class="search_in">
            <span style="float:left;margin-top:5px;margin-right:15px;">만료일자</span>
            <div class="comm_search mr_10">
                <p>
                    <input class="w_150px input_com" type="text" readonly="readonly" id="fromDt" name="fromDt" placeholder="조회 시작일자" value="${data.fromDt}"> ~
                    <input class="w_150px input_com" type="text" readonly="readonly" id="toDt" name="toDt" placeholder="조회 종료일자" value="${data.toDt}">
                </p>
            </div>
            <div class="comm_search  mr_10">
                <input type="text" class="w_150px input_com" id="keyword1" name="keyword1" placeholder="소속명/부서명" value="<c:out value="${data.keyword1}"/>">
            </div>
            <div class="comm_search  mr_10">
                <input type="text" class="w_150px input_com" id="keyword2" name="keyword2" placeholder="인사번호/성명" value="<c:out value="${data.keyword2}"/>">
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
            <button type="button" class="btn_middle color_basic" onclick="fnDelAll()">일괄삭제</button>
            <button type="button" class="btn_middle color_basic" onclick="fnDelSelect()">선택삭제</button>
            <button type="button" class="btn_excel" onclick="fnExcelDownLoad()">엑셀다운로드</button>
        </div>
        <!--//버튼  -->
    </div>
    <!--테이블 시작 -->
    <div class="tb_outbox">
        <table class="tb_list">
            <colgroup>
                <col width="3%" />
                <col width="3%" />
                <col width="5%"/>
                <col width="7%" />
                <col width="7%" />
                <col width="7%" />
                <col width="6%" />
                <col width="9%" />
            </colgroup>
            <thead>
            <tr>
                <th><input type="checkbox" id="checkAll"></th>
                <th>No.</th>
                <th>인사번호</th>
                <th>소속</th>
                <th>부서</th>
                <th>성명</th>
                <th>사용자 타입</th>
                <th>만료일자</th>
            </tr>
            </thead>
            <tbody>
            <c:if test="${privacyList == null || fn:length(privacyList) == 0}">
                <tr>
                    <td class="h_35px" colspan="8">조회 목록이 없습니다.</td>
                </tr>
            </c:if>
            <c:forEach items="${privacyList}" var="sList" varStatus="status">
                <tr>
                    <td><input type="checkbox" name="checkEmp" value="${sList.empCd}"/></td>
                    <td>${(pagination.totRecord - (pagination.totRecord-status.index)+1)  + ( (pagination.curPage - 1)  *  pagination.recPerPage ) }</td>
                    <td><a style="cursor:pointer" onclick="fnViewEmpDetailPop(${sList.empCd})"><c:out value="${sList.empCd}"/></a></td>
                    <td><c:out value="${sList.belongNm}"/></td>
                    <td><c:out value="${sList.deptNm}"/></td>
                    <td><c:out value="${sList.empNm}"/></td>
                    <td><c:out value="${sList.userTypNm}"/></td>
                    <td><c:out value="${sList.expiredDt}"/></td>
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


<!-- 사원정보 레이어 팝업 -->
<div id="empDetailLayerPop" class="example_content">
    <div class="popup_box box_w3">

        <div class="com_box ">
            <div class="txbox">
                <b class="fl mr_10">인사정보-상세</b>
            </div>
            <!--테이블 시작 -->
            <div class="tb_outbox">
                <table class="tb_list" >
                    <tr>
                        <th rowspan="5" style="width:30%"><img src="/img/empty.png"></th>
                        <th style="width:25%">성명</th>
                        <td><span id="empPopNm"></span></td>
                    </tr>
                    <tr>
                        <th>소속</th>
                        <td><span id="belongPopNm"></span></td>
                    </tr>
                    <tr>
                        <th>기관</th>
                        <td><span id="insttPopNm"></span></td>
                    </tr>
                    <tr>
                        <th>부서</th>
                        <td><span id="deptPopNm"></span></td>
                    </tr>
                    <tr>
                        <th>만료일자</th>
                        <td><span id="expiredPopAt"></span></td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
    <div style="text-align:center;">
        <button type="button" class="btn_middle color_basic ml_5" onclick="fnClosePop()">닫기</button>
    </div>
</div>
<!-- /사원정보 레이어 팝업 -->