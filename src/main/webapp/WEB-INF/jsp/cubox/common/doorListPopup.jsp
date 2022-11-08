<%--
  Created by IntelliJ IDEA.
  User: USER
  Date: 2022-10-11
  Time: 오전 10:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--  출입문 목록 modal  --%>
<div id="doorListPopup" class="example_content" style="display: none;">
  <div class="popup_box box_w3">

    <%--  테이블  --%>
    <div style="width:100%;">
      <div class="com_box" style="border: 1px solid black; background-color: white; overflow: auto; height: 330px;">
        <table class="tb_list tb_write_02 tb_write_p1">
          <tbody id="tdGroupTotal">
<%--          <tr>--%>
<%--            <td>12동 > C구역 > 1층 > 현관 출입문</td>--%>
<%--          </tr>--%>
<%--          <tr>--%>
<%--            <td>12동 > D구역 > 2층 > 계단</td>--%>
<%--          </tr>--%>
          </tbody>
        </table>
      </div>
    </div>
    <%--  end of 테이블  --%>

    <div class="c_btnbox center mt_30">
      <div style="display: inline-block;">
        <button type="button" class="comm_btn mr_20" onclick="closePopup('doorListPopup');">확인</button>
      </div>
    </div>
  </div>
</div>

<%--  end of 출입문 목록 modal  --%>
