<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="inbox7" style="width: 100%;height: 360px;margin-top:40px;">
  <div class="title">
    알람이력
    <div class="more">
      <img src="/img/main/icon_more.png" alt="" onclick="fnAlarmLog();"/>
    </div>
  </div>
  <div class="tb_outbox">
    <table class="tb_list_main">
      <col width="30%" />
      <col width="2%" />
      <col width="20%" />
      <col width="30%" />
      <thead>
      <tr>
        <th>알람일시</th>
        <th>알람유형</th>
        <th>건물명</th>
        <th>출입문명</th>
      </tr>
      </thead>
      <tbody id="alarmHistListBody">
      </tbody>
    </table>
  </div>
</div>
