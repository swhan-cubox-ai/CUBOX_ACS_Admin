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
        margin: 0 250px;
        width:500px;
        height: 500px;
        display: block;
    }
</style>
<%--  출입이력 상세 modal  --%>
<div id="entHistDetail" class="example_content" style="display: none;">
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
                            <th>출입기록 ID</th>
                            <td id="id"></td>
                            <th class="eTd">이벤트일시</th>
                            <td id="evtDt"></td>
                        </tr>
                        <tr>
                            <th>출입이벤트유형</th>
                            <td id="entEvtTyp" class="eTd"></td>
                            <th>출입이벤트유형명</th>
                            <td id="entEvtTypNm" class="eTd"></td>
                        </tr>
                        <tr>
                            <th>사원코드</th>
                            <td id="empCd" class="eTd"></td>
                            <th>사원명</th>
                            <td id="empNm" class="eTd"></td>
                        </tr>
                        <tr>
                            <th>Face Id</th>
                            <td id="faceId" class="eTd"></td>
                            <th>카드번호</th>
                            <td id="cardNo" class="eTd"></td>
                        </tr>
                        <tr>
                            <th>카드유형 코드</th>
                            <td id="cardClassTyp" class="eTd"></td>
                            <th>카드유형명</th>
                            <td id="cardClassTypNm" class="eTd"></td>
                        </tr>
                        <tr>
                            <th>카드상태코드</th>
                            <td id="cardStateTyp" class="eTd"></td>
                            <th>카드번호</th>
                            <td id="cardStateTypNm" class="eTd"></td>
                        </tr>
                        <tr>
                            <th>카드태그타입</th>
                            <td id="cardTagTyp" class="eTd"></td>
                            <th>카드태그 타입명</th>
                            <td id="cardTagTypNm" class="eTd"></td>
                        </tr>
                        <tr>
                            <th>시작일시</th>
                            <td id="begDt" class="eTd"></td>
                            <th>종료일시</th>
                            <td id="endDt" class="eTd"></td>
                        </tr>
                        <tr>
                            <th>인증방법유형</th>
                            <td id="authWayTyp" class="eTd"></td>
                            <th>인증방법명</th>
                            <td id="authWayTypNm" class="eTd"></td>
                        </tr>
                        <tr>
                            <th>matchScore</th>
                            <td id="matchScore" class="eTd"></td>
                            <th>faceThreshold</th>
                            <td id="faceThreshold" class="eTd"></td>
                        </tr>
                        <tr>
                            <th>captureAt</th>
                            <td id="captureAt" class="eTd"></td>
                            <th>tagAt</th>
                            <td id="tagAt" class="eTd"></td>
                        </tr>
                        <tr>
                            <th>tagCardNo</th>
                            <td id="tagCardNo" class="eTd"></td>
                            <th>tagEmpCd</th>
                            <td id="tagEmpCd" class="eTd"></td>
                        </tr>
                        <tr>
                            <th>온도</th>
                            <td id="temper" class="eTd"></td>
                            <th>maskConfidence</th>
                            <td id="maskConfidence" class="eTd"></td>
                        </tr>
                        <tr>
                            <th>터미널유형</th>
                            <td colspan="3" id="terminalTyp" class="eTd"></td>
                        </tr>
                        <tr>
                            <th>건물코드</th>
                            <td id="buildingCd" class="eTd"></td>
                            <th>건물명</th>
                            <td id="buildingNm" class="eTd"></td>
                        </tr>
                        <tr>
                            <th>출입문코드</th>
                            <td id="doorCd" class="eTd"></td>
                            <th>출입문명</th>
                            <td id="doorNm" class="eTd"></td>
                        </tr>
                        <tr>
                            <th>부서코드</th>
                            <td id="deptCd" class="eTd"></td>
                            <th>부서명</th>
                            <td id="deptNm" class="eTd"></td>
                        </tr>
                        <tr>
                            <th>생성일시</th>
                            <td id="createdAt" class="eTd"></td>
                            <th>수정일시</th>
                            <td id="updatedAt" class="eTd"></td>
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
                <button type="button" class="comm_btn mr_20" onclick="closePopup('entHistDetail');">확인</button>
            </div>
        </div>
    </div>
</div>
<%--  end of 사진 특징점 추출 오류 상세 modal  --%>
