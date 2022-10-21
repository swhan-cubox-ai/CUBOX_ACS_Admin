<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tr>
  <th>빌딩(동)</th>
  <td colspan="2">
    <select name="doorEdit" id="dBuilding" class="form-control selectBuilding" style="padding-left:10px;" disabled>
      <option value="" name="selected">선택</option>
      <c:forEach items="${buildingList}" var="building" varStatus="status">
        <option value='<c:out value="${building.id}"/>'><c:out value="${building.building_nm}"/></option>
      </c:forEach>
    </select>
  </td>
</tr>

