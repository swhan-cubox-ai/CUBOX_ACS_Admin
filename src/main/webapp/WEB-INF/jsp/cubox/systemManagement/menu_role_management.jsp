<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!--검색박스 -->
<script type="text/javascript">
	$(function() {
		$(".title_tx").html("test 변경");
		$("#currentpasswd").focus();
	});


</script>
<form id="frmSearch" name="frmSearch" method="post">
<input type="hidden" id="chkValueArray" name="chkValueArray" value=""/>
<input type="hidden" id="chkTextArray" name="chkTextArray" value="" >
<input type="hidden" id="fdownresn" name="fdownresn" value=""/>
<div class="search_box mb_20">
	<p>테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트</p>
	<div class="search_in" style="width: 600px;">
		<div class="comm_search mb_20 mt_10">
			<div class="w_150px fl" style="line-height: 30px"><em>현재 비밀번호</em></div>
			<input type="password" id="currentpasswd" name="currentpasswd" class="w_250px input_com l_radius_no" placeholder="현재 비밀번호" maxlength="20" style="border:1px solid #ccc;"/>
		</div>
		<div class="comm_search w_100p mb_20" style="line-height: 30px">
			<div class="w_150px fl"><em>변경 비밀번호</em></div>
			<input type="password" id="fpasswd" name="fpasswd" class="w_250px input_com l_radius_no" placeholder="변경 비밀번호" maxlength="20" style="border:1px solid #ccc;"/>
		</div>
		<div class="comm_search w_100p mb_20" style="line-height: 30px">
			<div class="w_150px fl"><em>변경 비밀번호 확인</em></div>
			<input type="password" id="fpasswdcon" name="fpasswdcon"  class="w_250px input_com l_radius_no" placeholder="변경 비밀번호 확인" maxlength="20" style="border:1px solid #ccc;"/>
		</div>
	</div>
</div>
<!--//검색박스 -->
</form>