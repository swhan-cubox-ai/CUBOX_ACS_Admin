<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:include page="/WEB-INF/jsp/cubox/report/entHist/detail.jsp" flush="false"/>

<script type="text/javascript">
  $(function() {
    $(".title_tx").html("사용자 활동 로그");
    alert("단위테스트준비작업을 위한 임시 화면입니다. 기능 미적용상태입니다.");
    modalPopup("actLogDetail", "사용자활동로그상세", 1100, 1000);
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


    f.action = "/system/actLog/list.do";
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



</script>

<form id="frmSearch" name="frmSearch" method="post">
  <input type="hidden" id="srchPage" name="srchPage" value="${pagination.curPage}"/>
  <div class="search_box mb_20">
    <div class="search_in">
      <div class="comm_search mr_10">
        <p>
          <input class="w_150px input_datepicker" type="text" readonly="readonly" id="fromDt" name="fromDt" placeholder="조회 시작일자"> ~
          <input class="w_150px input_datepicker" type="text" readonly="readonly" id="toDt" name="toDt" placeholder="조회 종료일자" >
        </p>
      </div>
      <div class="comm_search mr_10">
        <select name="srchCond" id="srchCond" size="1" class="w_150px input_com">
          <option value="">전체</option>
        </select>
      </div>
      <div class="comm_search  mr_10">
        <input type="text" class="w_150px input_com" id="keyword" name="keyword"  placeholder="아이디/이름">
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
        <col width="3%" />
        <col width="5%"/>
        <col width="5%" />
        <col width="5%" />
        <col width="12%" />
        <col width="5%" />
        <col width="5%" />
      </colgroup>
      <thead>
        <tr>
          <th>일시</th>
          <th>No.</th>
          <th>활동유형</th>
          <th>아이디</th>
          <th>사용자명</th>
          <th>소속</th>
          <th>IP</th>
          <th>상태</th>
        </tr>
      </thead>
      <tbody id="actLogListBody">
        <tr>
          <td>2022-12-07 12:33:25</td>
          <td>34</td>
          <td>권한관리수정</td>
          <td>test</td>
          <td>김테스</td>
          <td>테스트부서</td>
          <td>172.45.12.122</td>
          <td style="background-color: black !important; color:white">정상</td>
        </tr>
        <tr>
          <td>2022-12-07 12:32:31</td>
          <td>33</td>
          <td>로그인</td>
          <td>test</td>
          <td>김테스</td>
          <td>테스트부서</td>
          <td>172.45.12.122</td>
          <td style="background-color: black !important; color:white">정상</td>
        </tr>
        <tr>
          <td>2022-12-07 12:32:07</td>
          <td>32</td>
          <td>스케줄 수정</td>
          <td>test</td>
          <td>김테스</td>
          <td>테스트부서</td>
          <td>172.45.12.122</td>
          <td style="background-color: black !important; color:white">정상</td>
        </tr>
        <tr>
          <td>2022-12-07 12:32:06</td>
          <td>31</td>
          <td>사용자생성</td>
          <td>test2</td>
          <td>이테스</td>
          <td>테스트부서</td>
          <td>172.45.12.133</td>
          <td style="background-color: black !important; color:white">정상</td>
        </tr>
        <tr>
          <td>2022-12-07 12:30:00</td>
          <td>30</td>
          <td>로그인</td>
          <td>test</td>
          <td>김테스</td>
          <td>테스트부서</td>
          <td>172.45.12.122</td>
          <td style="background-color: black !important; color:white">정상</td>
        </tr>
        <tr>
          <td>2022-12-07 12:29:44</td>
          <td>29</td>
          <td>로그인</td>
          <td>test</td>
          <td>김테스</td>
          <td>테스트부서</td>
          <td>172.45.12.122</td>
          <td style="background-color: black !important; color:white">정상</td>
        </tr>
        <tr>
          <td>2022-12-07 12:29:31</td>
          <td>28</td>
          <td>로그인</td>
          <td>test</td>
          <td>김테스</td>
          <td>테스트부서</td>
          <td>172.45.12.122</td>
          <td style="background-color: black !important; color:white">정상</td>
        </tr>
        <tr>
          <td>2022-12-06 12:33:31</td>
          <td>27</td>
          <td>로그인</td>
          <td>test</td>
          <td>김테스</td>
          <td>테스트부서</td>
          <td>172.45.12.122</td>
          <td style="background-color: black !important; color:white">정상</td>
        </tr>
        <tr>
          <td>2022-12-06 11:33:31</td>
          <td>26</td>
          <td>로그인</td>
          <td>test</td>
          <td>김테스</td>
          <td>테스트부서</td>
          <td>172.45.12.122</td>
          <td style="background-color: black !important; color:white">정상</td>
        </tr>
        <tr>
          <td>2022-12-06 11:53:31</td>
          <td>25</td>
          <td>로그인</td>
          <td>test</td>
          <td>김테스</td>
          <td>테스트부서</td>
          <td>172.45.12.122</td>
          <td style="background-color: black !important; color:white">정상</td>
        </tr>
        <tr>
          <td>2022-12-06 10:44:04</td>
          <td>24</td>
          <td>로그인</td>
          <td>test</td>
          <td>김테스</td>
          <td>테스트부서</td>
          <td>172.45.12.122</td>
          <td style="background-color: red !important; color:white">중지</td>
        </tr>
      </tbody>
    </table>
  </div>
  <!--------- //목록--------->
  <!-- 페이징 -->
  <jsp:include page="/WEB-INF/jsp/cubox/common/pagination.jsp" flush="false"/>
  <!-- /페이징 -->
</div>
</form>
