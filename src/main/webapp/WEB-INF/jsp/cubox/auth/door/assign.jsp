<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!--검색박스 -->
<script type="text/javascript">
  $(function() {

    $(".title_tx").html("사원등록");

    $("#addWbList").click(function() {
      $("input[name=sourceEmp]:checked").each(function(i) {
        let tag = "<tr>" + $(this).closest("tr").html() + "</tr>";
        tag = tag.replace("sourceEmp", "targetEmp");
        $("#targetWbTb").append(tag);
      });

      let ckd = $("input[name=sourceEmp]:checked").length;
      for(let i = ckd - 1; i > -1; i--) {
        $("input[name=sourceEmp]:checked").eq(i).closest("tr").remove();
      }

      totalSourceCheck();
    });

    $("#delWbList").click(function() {
      $("input[name=targetEmp]:checked").each(function(i) {
        let tag = "<tr>" + $(this).closest("tr").html() + "</tr>";
        tag = tag.replace("targetEmp", "sourceEmp");
        $("#sourceWbTb").append(tag);
      });

      let ckd = $("input[name=targetEmp]:checked").length;
      for(let i = ckd - 1; i > -1; i--) {
        $("input[name=targetEmp]:checked").eq(i).closest("tr").remove();
      }

      totalTargetCheck();
    });

    $("#sourceWbCheckAll").click(function() {
      if($("#sourceWbCheckAll").prop("checked")) {
        $("input[name=sourceEmp]").prop("checked", true);
      } else {
        $("input[name=sourceEmp]").prop("checked", false);
      }
    });

    $("#targetWbCheckAll").click(function() {
      if ($("#targetWbCheckAll").prop("checked")) {
        $("input[name=targetEmp]").prop("checked", true);
      } else {
        $("input[name=targetEmp]").prop("checked", false);
      }
    });

    fnInit();

  });


  function totalSourceCheck() {
    if ($("#sourceWbCheckAll").prop("checked")) {
      $("#sourceWbCheckAll").prop("checked", false);
    }
  }

  function totalTargetCheck() {
    if ($("#targetWbCheckAll").prop("checked")) {
      $("#targetWbCheckAll").prop("checked", false);
    }
  }

  function fnInit() {
    let authId = $("#authId").val();

    $.ajax({
      type:"POST",
      url:"/auth/door/getEmpTargetList.do",
      data:{
        "authId": authId
      },
      dataType:'json',
      success:function(returnData, status){

        if(status == "success") {
          $("#targetWbTb").empty();

          let result = returnData.list;
          let str = '';

          $.each(result, function(i){
            str += '<tr>';
            str += '<td><input type="checkbox" name="targetEmp" value="' + result[i].id + '"></td>';
            str += '<td>' + i + '</td>';
            str += '<td>' + result[i].empNo + '</td>';
            str += '<td>' + result[i].belongNm + '</td>';
            str += '<td>' + result[i].deptNm + '</td>';
            str += '<td>' + result[i].empNm + '</td>';
            str += '</tr>';
          });

          $("#targetWbTb").append(str);

        }else{ alert("ERROR!");return;}
      }
    });
  }


  function fnSearch() {
    let params = $("#wbFrm").serialize();

    $.ajax({
      type:"POST",
      url:"/auth/door/getEmpSourceList.do",
      data:params,
      dataType:'json',
      success:function(returnData, status){

        if(status == "success") {

          fnInit();

          $("#sourceWbTb").empty();

          let result = returnData.list;
          let str = '';

          $.each(result, function(i){
            str += '<tr>';
            str += '<td><input type="checkbox" name="sourceEmp" value="' + result[i].id + '"></td>';
            str += '<td>' + i + '</td>';
            str += '<td>' + result[i].empNo + '</td>';
            str += '<td>' + result[i].belongNm + '</td>';
            str += '<td>' + result[i].deptNm + '</td>';
            str += '<td>' + result[i].empNm + '</td>';
            str += '</tr>';
          });

          $("#sourceWbTb").append(str);

        }else{ alert("ERROR!");return;}
      }
    });
  }

  function fnSave(){
    Swal.fire({
      text: '변경된 정보를 저장 하시겠습니까?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'OK',
      cancelButtonText: 'CANCEL',
      reverseButtons: true,

    }).then((result) => {
      if (result.isConfirmed) {
        fnSaveProc();
      }
    })
  }

  function fnSaveProc() {
    let checkedArray = [];

    $("input[name=targetEmp]").each(function(i) {
      checkedArray.push($(this).val());
    });

    $("#targetEmpArray").val(checkedArray.join(","));

    let params = $("#wbFrm").serialize();

    $.ajax({
      type: "POST",
      data: params,
      url: "/auth/door/assignAuthEmp.do",
      dataType: 'json',
      //timeout:(1000*30),
      success: function (returnData) {
        if (returnData.result == "success") {
          Swal.fire({
            icon: 'success',
            text: '정보가 변경 되었습니다.'
          }).then((result) => {
            window.location.href = '/auth/door/list.do';
          });
        } else {
          alert("ERROR!");
          return;
        }
      }
    });
  }

  function fnCancel(){
    Swal.fire({
      text: '작업을 취소하시겠습니까?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'OK',
      cancelButtonText: 'CANCEL',
      reverseButtons: true, // 버튼 순서 거꾸로

    }).then((result) => {
      if (result.isConfirmed) {
        window.location.href='/auth/door/list.do';
      }
    })
  }

</script>
<form id="wbFrm" name="wbFrm">
  <input type="hidden" id="authId" name="authId" value='<c:out value="${data.id}"/>'/>
  <input type="hidden" id="targetEmpArray" name="targetEmpArray" value=''/>

  <div class="search_box mb_20">
    <div class="popup_box box_w3">
      <%--  검색 박스 --%>
        <div style="margin-bottom: 25px;">권한그룹명 : <c:out value="${data.authNm}"/></div>
        <div class="search_in" style="padding-left: 0px;width: 100%;">
          <div class="comm_search mr_10">
            <input type="text" class="input_com" id="keyword1" name="keyword1" value="" placeholder="회사명/부서명" maxlength="30" style="width: 182px;">
          </div>
          <div class="comm_search mr_10">
            <input type="text" class="input_com" id="keyword2" name="keyword2" value="" placeholder="사원번호/성명" maxlength="30" style="width: 182px;">
          </div>
          <div class="comm_search ml_40">
            <div class="search_btn2" onclick="fnSearch();"></div>
          </div>
        </div>
      <%--  end of 검색 박스 --%>

      <%--  왼쪽 box  --%>
      <div style="width:45%;">
        <div class="com_box" style="border: 1px solid black; overflow: auto; height: 250px;">
          <table class="tb_list tb_write_02 tb_write_p1">
            <colgroup>
              <col style="width:5%">
              <col style="width:5%">
              <col style="width:15%">
              <col style="width:15%">
              <col style="width:20%">
              <col style="width:25%">
            </colgroup>
            <thead>
            <tr>
              <th><input type="checkbox" id="sourceWbCheckAll"></th>
              <th>No</th>
              <th>사원번호</th>
              <th>소속</th>
              <th>부서</th>
              <th>성명</th>
            </tr>
            </thead>
            <tbody id="sourceWbTb">
            </tbody>
          </table>
        </div>
      </div>
      <%--  end of 왼쪽 box  --%>

      <%--  화살표 이동  --%>
      <div class="box_w3_2" style="height: 250px;">
        <div class="btn_box">
          <img src="/img/ar_r.png" alt="" id="addWbList"/>
        </div>
        <div class="btn_box">
          <img src="/img/ar_l.png" alt="" id="delWbList"/>
        </div>
      </div>
      <%--  end of 화살표 이동  --%>

      <%--  오른쪽 box  --%>
      <div style="width:45%;">
        <%--  테이블  --%>
        <div class="com_box" style="border: 1px solid black; overflow: auto; height: 250px;">
          <table class="tb_list tb_write_02 tb_write_p1">
            <colgroup>
              <col style="width:5%">
              <col style="width:5%">
              <col style="width:15%">
              <col style="width:15%">
              <col style="width:20%">
              <col style="width:25%">
            </colgroup>
            <thead>
            <tr>
              <th><input type="checkbox" id="targetWbCheckAll"></th>
              <th>No</th>
              <th>사원번호</th>
              <th>소속</th>
              <th>부서</th>
              <th>성명</th>
            </tr>
            </thead>
            <tbody id="targetWbTb">
              <c:forEach items="${empTargetlist}" var="sList" varStatus="status">
                <tr>
                  <td><input type="checkbox" name="targetEmp" value="${sList.id}"/></td>
                  <td>${(pagination.totRecord - (pagination.totRecord-status.index)+1)  + ( (pagination.curPage - 1)  *  pagination.recPerPage ) }</td>
                  <td><c:out value="${sList.emp_no}"/></td>
                  <td><c:out value="${sList.belongNm}"/></td>
                  <td><c:out value="${sList.deptNm}"/></td>
                  <td><c:out value="${sList.empNm}"/></td>
                </tr>
              </c:forEach>
            </tbody>
          </table>
        </div>
      </div>
      <%--  end of 오른쪽 box  --%>

      <div class="c_btnbox center mt_30">
        <div style="display: inline-block;">
          <button type="button" class="comm_btn mr_20" onclick="fnSave();">확인</button>
          <button type="button" class="comm_btn" onclick="fnCancel();">취소</button>
        </div>
      </div>
    </div>
  </div>
</form>
