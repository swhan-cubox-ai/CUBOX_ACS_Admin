<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>
    #imgBox {
        text-align: center;
        padding-top: 10px;
    }
    .eTd {
        text-align: left; !important;
    }
    #imagePreview{
        margin: auto;
        width:500px;
        height: 500px;
    }


</style>
<%--  사진 특징점 추출 오류 상세 modal  --%>
<div id="faceErrPopup" class="example_content" style="display: none;">
    <div class="popup_box box_w3">
        <div style="width:100%;">
            <div class="com_box" style="border: 1px solid black; background-color: white; overflow: auto; height: 100%;">
                <table class="tb_list tb_write_02 tb_write_p1">
                    <colgroup>
                        <col style="width:15%">
                        <col style="width:35%">
                        <col style="width:15%">
                        <col style="width:35%">
                    </colgroup>
                    <thead>
                    </thead>
                    <tbody>
                        <tr>
                            <th>사원코드</th>
                            <td id="sEmpCd"></td>
                            <th class="eTd">사원명</th>
                            <td id="sEmpNm"></td>
                        </tr>
                        <tr>
                            <th>부서명</th>
                            <td id="sDeptNm" colspan="3" class="eTd"></td>
                        </tr>
                        <tr>
                            <th>특징점 추출<br/> 상태 코드</th>
                            <td id="sFaceStateTyp" class="eTd"></td>
                            <th>특징점 추출 상태</th>
                            <td id="sFaceStateTypNm" class="eTd"></td>
                        </tr>
                        <tr>
                            <th>추출 타입 코드</th>
                            <td id="sFaceFeatureTyp" class="eTd"></td>
                            <th>추출 타입</th>
                            <td id="sFaceFeatureTypNm" class="eTd"></td>
                        </tr>
                        <tr>
                            <th>에러</th>
                            <td id="sError" colspan="3" class="eTd"></td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <div id="imgBox">
            <img id="imagePreview"/>
        </div>

        <div class="c_btnbox center mt_30">
            <div style="display: inline-block;">
                <button type="button" class="comm_btn mr_20" onclick="closePopup('faceErrPopup');">확인</button>
            </div>
        </div>
    </div>
</div>
<%--  end of 사진 특징점 추출 오류 상세 modal  --%>
