<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!--검색박스 -->
<script type="text/javascript">
	$(function() {
		$(".title_tx").html("사용자관리 - 목록");
	});

	//fnGetUserList();

	function fnGetUserList() {
		var srchCond = "";
		var keyword = "";


		$.ajax({
			type:"POST",
			url:"<c:url value='/user/getUserList.do' />",
			data:{
				"srchCond": srchCond,
				"keyword": keyword
			},
			dataType:'json',
			//timeout:(1000*30),
			success:function(returnData, status){
				if(status == "success") {
					location.reload();
				}else{ alert("ERROR!");return;}
			}
		});
	}

	function pageSearch(page){
		f = document.frmSearch;

		$("#srchPage").val(page);

		f.action = "/user/userManagement.do";
		f.submit();
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
					<option value="id">아이디</option>
					<option value="user_nm">사용자명</option>
				</select>
			</div>
			<div class="comm_search  mr_10">
				<input type="text" class="w_150px input_com" id="keyword" name="keyword">
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
			<select name="srchRecPerPage" id="srchRecPerPage" class="input_com w_80px">
				<c:forEach items="${cntPerPage}" var="cntPerPage" varStatus="status">
					<option value='<c:out value="${cntPerPage.fvalue}"/>' <c:if test="${cntPerPage.fvalue eq pagination.recPerPage}">selected</c:if>><c:out value="${cntPerPage.fkind3}"/></option>
				</c:forEach>
			</select>
		</div>	<!--버튼 -->
		<div class="r_btnbox  mb_10">
			<button type="button" class="btn_middle color_basic" id="excelDown">엑셀다운로드</button>
			<button type="button" class="btn_middle color_basic" id="addSiteUser">신규등록</button>
		</div>
		<!--//버튼  -->
	</div>
	<!--테이블 시작 -->
	<div class="tb_outbox">
		<table class="tb_list">
			<colgroup>
				<col width="20px" />
				<col width="9%"/>
				<col width="9%" />
				<col width="9%" />
				<col width="9%" />
				<col width="9%" />
				<col width="9%" />
			</colgroup>
			<thead>
			<tr>
				<th>No.</th>
				<th>아이디</th>
				<th>사용자명</th>
				<th>소속</th>
				<th>연락처</th>
				<th>등록일자</th>
				<th>상태</th>
			</tr>
			</thead>
			<tbody>
			<c:if test="${siteUserList == null || fn:length(siteUserList) == 0}">
				<tr>
					<td class="h_35px" colspan="13">조회 목록이 없습니다.</td>
				</tr>
			</c:if>
			<c:forEach items="${siteUserList}" var="sList" varStatus="status">
				<tr>
					<td>${(pagination.totRecord - (pagination.totRecord-status.index)+1)  + ( (pagination.curPage - 1)  *  pagination.recPerPage ) }</td>
					<td><c:out value="${sList.fsiteid}"/></td>
					<td><c:out value="${sList.fname}"/></td>
					<td><c:out value="${sList.fphone}"/></td>
					<td><c:out value="${sList.fuid}"/></td>
					<td><c:out value="${sList.funm}"/></td>
					<td><c:out value="${sList.site_nm}"/></td>
					<td><c:out value="${sList.author_nm}"/></td>
					<td><c:out value="${sList.fpasswdyn}"/></td>
					<td><c:out value="${sList.fregdt2}"/></td>
					<td>
						<c:if test="${sList.fuseyn eq 'Y'}"><button type="button" class="btn_small color_basic" onclick="fnSiteUserFuseynChangeSave('<c:out value="${sList.fsiteid}"/>','<c:out value="${sList.fuseyn}"/>');">사용중</button></c:if>
						<c:if test="${sList.fuseyn eq 'N'}"><button type="button" class="btn_small color_gray" onclick="fnSiteUserFuseynChangeSave('<c:out value="${sList.fsiteid}"/>','<c:out value="${sList.fuseyn}"/>');">사용안함</button></c:if>
					</td>
					<td><button type="button" class="btn_small color_basic" data-toggle="modal" onclick="fnSiteUserInfoChange('<c:out value="${sList.fsiteid}"/>','<c:out value="${sList.fkind3}"/>','<c:out value="${sList.femergency}"/>','<c:out value="${sList.fname}"/>','<c:out value="${sList.fphone}"/>','<c:out value="${sList.fauthcd}"/>','<c:out value="${sList.fuid}"/>','<c:out value="${sList.funm}"/>','<c:out value="${sList.site_id}"/>','<c:out value="${sList.author_id}"/>');">편집</button></td>
					<td><button type="button" class="btn_small color_color1" onclick="fnSiteUserPasswdReset('<c:out value="${sList.fsiteid}"/>');">초기화</button></td>
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