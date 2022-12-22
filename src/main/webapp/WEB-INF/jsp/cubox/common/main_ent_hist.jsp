<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="inbox7" style="width: 100%; height: 760px;">
  <div class="title">
    출입이력
    <div class="more">
      <img src="/img/main/icon_more.png" alt="" onclick="fnGateLog();"/>
    </div>
  </div>
  <div class="tb_outbox" style="overflow:auto; height:93%;">
    <table class="tb_list_main">
      <col width="12%" />
      <col width="15%" />
      <col width="45%" />
      <col width="10%" />
      <col width="10%" />
      <col width="8%" />
      <thead>
      <tr>
        <th>출입기록번호</th>
        <th>시간</th>
        <th>출입장소</th>
        <th>이름</th>
        <th>카드상태</th>
        <th>결과</th>
      </tr>
      </thead>
      <tbody id="entHistListBody">
      </tbody>
    </table>
  </div>
</div>
