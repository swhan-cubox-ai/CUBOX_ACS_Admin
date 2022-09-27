<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jstree/3.2.1/themes/default/style.min.css" />

<script src="https://cdnjs.cloudflare.com/ajax/libs/jstree/3.2.1/jstree.min.js"></script>

<!--검색박스 -->
<script type="text/javascript">

  const _isModify = ${isModify};

  $(function() {

    if(_isModify){
      $(".title_tx").html("메뉴권한 관리 - 수정");
    }else{
      $(".title_tx").html("메뉴권한 관리 - 상세");
    }

    getAuthMenuTree();
  });

  let _dataList;
  let _myList;

  function getAuthMenuTree(){
    let paramKey = $("#id").val();

    $.ajax({
      type:'get',
      data:{
        "roleId": paramKey
      },
      url:'/menuAuth/getAuthMenuTree.do',
      dataType:'json',
      success: function(data) {
        let menuArr = new Array();
        _dataList = data.allList;
        _myList = data.myList;

        $.each(_dataList, function(idx, item){
          menuArr[idx] = {id:item.menuId, parent:item.parentMenuId, text:item.menuNm};
        });

        $('#tree').jstree({
          checkbox : {
            "keep_selected_style": true,
            "visible" : true,
            "whole_node" : true,
            'three_state': true
          },
          core: {
            data: menuArr
          },
          themes: {
            'name': 'proton',
            'responsive': true
          },
          types: {
            'default': {
              'icon': 'jstree-folder'
            }
          },
          plugins: ['wholerow', 'types', "themes", "html_data", "checkbox", "sort", "ui"]
        })
          .on('open_node.jstree', function(event, obj ) {
            initTree();
          })
          .on("loaded.jstree", function (e, data){
             $('#tree').jstree('open_all');
          })
          .bind('loaded.jstree', function(event, data){

          })
          .bind('select_node.jstree', function(event, data){
            //노드 선택 이벤트

          })
      },
      error:function (data) {
        alert("에러");
      }
    });
  }

  function initTree(){
    let comp;
    let pNode;

    $("#tree").find("ul").find("li").each(function (i){
      pNode = $(this);
      comp  = $(this).attr("id");

      $.each(_myList, function(idx, item){
        if(item.menuId === comp && item.parentMenuId !== "#"){
          pNode.attr('aria-selected', true);
         // pNode.find("a").attr('aria-selected', true);

          pNode.find("a").addClass('jstree-clicked');
          pNode.find("a").addClass('jstree-wholerow-clicked');
        }
      });

      if(_isModify){
        changeStatus(comp, 'enable');
      }else{
        changeStatus(comp, 'disable');
      }

    });
  }

  function changeStatus(node_id, changeTo) {
    let node = $("#tree").jstree().get_node(node_id);

    if (changeTo === 'enable') {
      $("#tree").jstree().enable_node(node);
      node.children.forEach(function(child_id) {
        changeStatus(child_id, changeTo);
      })
    } else {
      $("#tree").jstree().disable_node(node);
      node.children.forEach(function(child_id) {
        changeStatus(child_id, changeTo);
      })
    }
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
        window.location.href='/menuAuth/detail/'+$("#id").val();
      }
    })
  }

  function fnDelete(){
    Swal.fire({
      text: '정말로 삭제 하시겠습니까?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'OK',
      cancelButtonText: 'CANCEL',
      reverseButtons: true,

    }).then((result) => {
      if (result.isConfirmed) {
        fnDeleteProc();
      }
    })
  }

  function fnDeleteProc(){
    let param = $("#id").val();

    $.ajax({
      type: "POST",
      data: {id : param},
      url: "/menuAuth/deleteMenuAuth.do",
      dataType: 'json',
      //timeout:(1000*30),
      success: function (returnData) {
        if (returnData.result == "success") {
          Swal.fire({
            icon: 'success',
            text: '메뉴권한이 삭제 되었습니다.'
          }).then((result) => {
            window.location.href = '/menuAuth/list.do';
          });
        } else {
          alert("ERROR!");
          return;
        }
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
    let parentMenuCd;

   $(".jstree-leaf").each(function (idx) {
      if($(this).attr("aria-selected") == "true"){
        parentMenuCd = $(this).parent("ul").parent("li").attr("id");
        checkedArray.push(parentMenuCd);
        checkedArray.push(this.id);
      }
    });

    const set = new Set(checkedArray);
    const uniqueArr = [...set];

    $("#menuArray").val(uniqueArr.join(","));

    let params = $("#modifyMenuAuthFrm").serialize();

       $.ajax({
         type: "POST",
         data: params,
         url: "/menuAuth/modifyMenuAuth.do",
         dataType: 'json',
         //timeout:(1000*30),
         success: function (returnData) {
           if (returnData.result == "success") {
             Swal.fire({
               icon: 'success',
               text: '메뉴권한 정보가 변경 되었습니다.'
             }).then((result) => {
               window.location.href = '/menuAuth/detail/' + $("#id").val();
             });
           } else {
             alert("ERROR!");
             return;
           }
         }
       });
  }

</script>

<form id="modifyMenuAuthFrm" name="modifyMenuAuthFrm" method="post">
  <input type="hidden" id="id" name="id" value="<c:out value='${data.id}'/>" >
  <input type="hidden" id="menuArray" name="menuArray" value="" >

  <div class="search_box mb_20">
    <div class="search_in" style="width: 600px;margin-left: 100px;">
      <div class="comm_search w_100p mb_20" style="line-height: 30px">
        <div class="w_150px fl"><em>메뉴권한 명</em></div>
        <input type="text" id="roleNm" name="roleNm" class="w_250px input_com l_radius_no" readonly="readonly" value="<c:out value='${data.roleNm}'/>" placeholder="메뉴권한 명" maxlength="20" style="border:1px solid #ccc;"/>
      </div>
      <div style="border:1px solid #ccc;margin-top:50px;padding:15px 15px 15px 15px;">
        <div id="tree"></div>
      </div>
      <div style="margin-top:50px;margin-left: 50px;">
        <c:if test="${isModify}">
          <button type="button" class="btn_middle color_basic ml_5" style="float:left;margin-left: 30px;" onclick="fnSave();">저장</button>
          <button type="button" class="btn_middle color_basic ml_5" style="float:left;margin-left: 30px;" onclick="fnCancel();">취소</button>
        </c:if>
        <c:if test="${! isModify}">
          <button type="button" class="btn_middle color_basic ml_5" style="float:left" onclick="window.location.href='/menuAuth/list.do';">목록</button>
          <button type="button" class="btn_middle color_basic ml_5" style="float:left;margin-left: 30px;" onclick="window.location.href='/menuAuth/modify/${data.id}';">수정</button>
          <button type="button" class="btn_middle color_basic ml_5" style="float:left;margin-left: 30px;" onclick="fnDelete();">삭제</button>
        </c:if>

      </div>
    </div>
  </div>
</form>