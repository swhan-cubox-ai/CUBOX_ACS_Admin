<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jstree/3.2.1/themes/default/style.min.css" />

<script src="https://cdnjs.cloudflare.com/ajax/libs/jstree/3.2.1/jstree.min.js"></script>


<script type="text/javascript">
    $(function() {
        $(".title_tx").html("메뉴 관리 - 등록");

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
                window.location.href='/menuAuth/list.do';
            }
        })
    }

    function fnSave(){
        Swal.fire({
            text: "메뉴권한을 등록 하시겠습니까?",
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

        if($("#roleNm").val() == ""){
            Swal.fire({
                icon: 'warning',
                text: '메뉴권한 명이 누락되었습니다',
            });
            return;
        }

        let checkedArray = [];
        let parentMenuCd;

        $(".jstree-leaf").each(function (idx) {
            if($(this).attr("aria-selected") == "true"){
                parentMenuCd = $(this).parent("ul").parent("li").attr("id");
                checkedArray.push(parentMenuCd);
                checkedArray.push(this.id);
            }
        });

        if(checkedArray.length == 0){
            Swal.fire({
                icon: 'warning',
                text: '한개 이상의 메뉴권한을 체크해야 합니다.',
            })
            return;
        }

        const set = new Set(checkedArray);
        const uniqueArr = [...set];

        $("#menuArray").val(uniqueArr.join(","));

        let params = $("#addMenuAuthFrm").serialize();

        $.ajax({
            type: "POST",
            data: params,
            url: "/menuAuth/addMenuAuth.do",
            dataType: 'json',
            //timeout:(1000*30),
            success: function (returnData) {
                if (returnData.result == "success") {
                    Swal.fire({
                        icon: 'success',
                        text: '신규 메뉴권한이 등록 되었습니다.',
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


    function fnValidInput(){
        if($("#roleNm").val() == ""){
            Swal.fire({
                icon: 'warning',
                title: '메뉴권한 명이 누락되었습니다',
            });
            return false;
        }
        return true;
    }


</script>

<form id="addMenuAuthFrm" name="addMenuAuthFrm" method="post">
    <input type="hidden" id="menuArray" name="menuArray" value="" >

    <div class="search_box mb_20">
        <div class="search_in" style="width: 600px;margin-left: 100px;">
            <div class="comm_search w_100p mb_20" style="line-height: 30px">
                <div class="w_150px fl"><em>메뉴권한 명</em></div>
                <input type="text" id="roleNm" name="roleNm" class="w_250px input_com l_radius_no" value="" placeholder="메뉴권한 명" maxlength="20" style="border:1px solid #ccc;"/>
            </div>
            <div style="border:1px solid #ccc;margin-top:50px;padding:15px 15px 15px 15px;">
                <div id="tree"></div>
            </div>
            <div style="margin-top:50px;margin-left: 50px;">
                <button type="button" class="btn_middle color_basic ml_5" style="float:left;margin-left: 30px;" onclick="fnSave();">등록</button>
                <button type="button" class="btn_middle color_basic ml_5" style="float:left;margin-left: 30px;" onclick="fnCancel();">취소</button>
            </div>
        </div>
    </div>
</form>