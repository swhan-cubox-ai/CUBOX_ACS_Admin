<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tr>
  <th>구역</th>
  <td colspan="2">
    <select name="doorEditSelect" id="dArea" class="form-control selectArea" style="padding-left:10px;" disabled>
      <option value="" name="selected">선택</option>
      <c:forEach items="${areaList}" var="area" varStatus="status">
        <option value='<c:out value="${area.id}"/>' class="dArea" bId='<c:out value="${area.building_id}"/>'>
          <c:out value="${area.area_nm}"/></option>
      </c:forEach>
    </select>
  </td>
</tr>

