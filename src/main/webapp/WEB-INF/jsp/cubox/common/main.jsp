<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:include page="/WEB-INF/jsp/cubox/common/checkPasswd.jsp" flush="false"/>
<script type="text/javascript" src="/js/charts/loader.js"></script>
<script type="text/javascript" src="/js/charts/charts.min.js"></script>
<script type="text/javascript" src="/js/charts/utils.js"></script>
<style>
canvas {
	-moz-user-select: none;
	-webkit-user-select: none;
	-ms-user-select: none;
}
</style>
<script type="text/javascript" src="/js/jquery.simple-calendar.js"></script>
<script type="text/javascript">

	let threadRefresh;
	const data = [
		{exp_day : "12-13", tot_log_cnt : "80", success_log_cnt : "50", fail_log_cnt : "30"},
		{exp_day : "12-12", tot_log_cnt : "90", success_log_cnt : "60", fail_log_cnt : "30"},
		{exp_day : "12-11", tot_log_cnt : "80", success_log_cnt : "60", fail_log_cnt : "20"},
		{exp_day : "12-10", tot_log_cnt : "130", success_log_cnt : "120", fail_log_cnt : "10"},
		{exp_day : "12-09", tot_log_cnt : "95", success_log_cnt : "80", fail_log_cnt : "15"},
		{exp_day : "12-08", tot_log_cnt : "80", success_log_cnt : "20", fail_log_cnt : "60"},
		{exp_day : "12-07", tot_log_cnt : "100", success_log_cnt : "90", fail_log_cnt : "10"},
		{exp_day : "12-06", tot_log_cnt : "25", success_log_cnt : "20", fail_log_cnt : "5"},
		{exp_day : "12-05", tot_log_cnt : "50", success_log_cnt : "35", fail_log_cnt : "15"},
	];

	$(function() {
		// 숫자만 입력가능
		$(".onlyNumber").keyup(function(event) {
			if (!(event.keyCode >=37 && event.keyCode<=40)) {
				let inputVal = $(this).val();
				$(this).val(inputVal.replace(/[^0-9]/gi,''));
			}
		});	
		
		//자동새로고침 증가
		$("#btnRefreshIncrease").click(function() {
			if (parseInt($("#intervalSecond").val()) < 999) {
				$("#intervalSecond").val( parseInt($("#intervalSecond").val())+1);
			}
		});
		
		reload();
		fnEntHistoryChartDraw(data);
		fnCardTypeChartDraw(data);
	});
	

	//새로고침
	function reload() {
		//chart
		$.ajax({
			type:"GET",
			url:"",
			data:{},
			dataType: "json",
			success:function(result) {
				var arrStatDt = [];
				arrStatDt.push(['Month', '출입이력', '출입실패', '출입성공' ]);
				if(result != null && result.statLit != null) {
					/* for(var i in result.statLit) {
						var arr = [result.statLit[i].exp_day, parseInt(result.statLit[i].tot_log_cnt), parseInt(result.statLit[i].fail_log_cnt), parseInt(result.statLit[i].success_log_cnt), parseInt(result.statLit[i].user_log_cnt)];
						arrStatDt.push(arr);
					}
					fnChartDraw (arrStatDt); */
					// fnChartCanvasDraw(result.statLit);
					fnEntHistoryChartDraw(result.statLit);
					fnCardTypeChartDraw("");
				} else {

				}
			}
		});

		//log list
		$.ajax({
			type:"GET",
			url: "<c:url value='/main/entHist'/>",
			data:{},
			dataType: "json",
			success:function(result) {
				fnLogInfoListSet(result);
			}
		});

		//log cnt
		<%--$.ajax({--%>
		<%--	type:"GET",--%>
		<%--	url:"' />",--%>
		<%--	data:{},--%>
		<%--	dataType: "json",--%>
		<%--	success:function(result) {--%>
		<%--		fnLogInfoCntSet(result);--%>
		<%--	}--%>
		<%--});--%>
		
		<%--//notice list--%>
		<%--$.ajax({--%>
		<%--	type:"GET",--%>
		<%--	url:"<c:url value='/main/getMainNoticeList.do' />",--%>
		<%--	data:{},--%>
		<%--	dataType: "json",--%>
		<%--	success:function(result) {--%>
		<%--		fnNoticeInfoListSet(result);--%>
		<%--	}--%>
		<%--});--%>
		
		<%--//q&a list--%>
		<%--$.ajax({--%>
		<%--	type:"GET",--%>
		<%--	url:"<c:url value='/main/getMainQaList.do' />",--%>
		<%--	data:{},--%>
		<%--	dataType: "json",--%>
		<%--	success:function(result) {--%>
		<%--		fnQaListSet(result);--%>
		<%--	}--%>
		<%--});--%>
		
		//근태관리 달력
		// $.ajax({
		// 	type : "POST"
		// 	, url : ""
		// 	, data : { "nowMonth" : "" }
		// 	, dataType : "JSON"
		// 	, success : function (data) {
		// 		var event = [];
		// 		$(".today_tx").html( "<em>" + moment().format('YYYY.MM.DD') + "</em>");
		//
		// 		$.each(data.workEventList, function(index, item){
		// 			if(moment().format('YYYYMMDD') == item.fstdde){
		// 				$(".today_tx").html( $(".today_tx").html() + "<br>" + item.title );
		// 			}
		//
		// 			event.push({
		// 				startDate: new Date(moment(item.fstdde).format('YYYY-MM-DD'))
		// 				, endDate: new Date(moment(item.fstdde).format('YYYY-MM-DD'))
		// 				, summary: item.title
		// 			});
		// 		});
		//
		// 		$("#container").simpleCalendar({///참고 : https://github.com/brospars/simple-calendar
		// 			months: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월']
		// 			, days: ['일', '월', '화', '수', '목', '금', '토']
		// 			, displayYear : true// 헤더에 연도 표시
		// 			, fixedStartDay: 0// 주는 항상 월요일 또는 숫자로 설정된 요일에 시작됩니다. 0 = 일요일, 7 = 토요일, false = 월은 항상 해당 월의 첫 번째 날부터 시작
		// 			, disableEmptyDetails: true// 빈 날짜 세부 정보 표시 활성화
		// 			, events: event
		// 			, onEventCreate : function ( $el ) {// HTML 이벤트가 생성 될 때 콜백이 실행 됨-$ (this) .data ( 'event') 참조
		// 				$el.find(".event-hour").text("");//시간 지우기
		//
		// 				var text = $el.find(".event-date").text().split("-");//시작일과 종료일 분리
		//
		// 				$el.find(".event-date").text(
		// 					moment(text[0]).format('YYYY-MM-DD')
		// 				);
		// 			}
		// 		});
		// 	}
		// });
	}

	// 출입카드 차트
	function fnCardTypeChartDraw(data) {
		console.log("fnCardTypeChartDraw");
		console.log(data);

		let ctx = $("#canvas2").get(0).getContext("2d");

		let dataObj = {
			labels : [],
			datasets: [
				{
					data: [40, 60],      // 섭취량, 총급여량 - 섭취량
					backgroundColor: [
						'#9DCEFF',
						'#F2F3F6'
					],
					borderWidth: 0,
					scaleBeginAtZero: true,
				}
			]
		}

		let myChart = new Chart(ctx, {
			type: 'doughnut',
			data: dataObj
		});
	}

	// 출입이력 차트
	function fnEntHistoryChartDraw(data) {
		console.log("fnEntHistoryChartDraw");

		let dtLabel = [],
			data1 = [],
			data2 = [],
			data3 = [],
			data4 = [];

		for (let i in data) {
			dtLabel.push(data[i].exp_day);
			data1.push(parseInt(data[i].tot_log_cnt));
			data2.push(parseInt(data[i].fail_log_cnt));
			data3.push(parseInt(data[i].success_log_cnt));
			data4.push(parseInt(data[i].user_log_cnt));
		}

		console.log(dtLabel);

		let color = Chart.helpers.color;
		let chartData = {
			labels: dtLabel, // x축 데이터 라벨
			datasets: [{
				type: 'line',
				label: '출입이력',
				borderColor: '#173d93',		//window.chartColors.blue
				borderWidth: 2,
				fill: false,
				data: data1
			}, {
				type: 'bar',
				label: '출입성공',
				backgroundColor: color('#016879').alpha(0.8).rgbString(),	//window.chartColors.green
				data: data3,
				borderWidth: 2
			}, {
				type: 'bar',
				label: '출입실패',
				backgroundColor: color('#c13838').alpha(0.8).rgbString(),		//window.chartColors.red
				data: data2,
				/* borderColor: 'white', */
				borderWidth: 2
			}
		/* 		, {
					type: 'bar',
					label: '단말기출입자',
					backgroundColor: '#a7c846',	//window.chartColors.gray
					data: data4,
					borderWidth: 2
				} */
				]
		};

		let ctx = document.getElementById('canvas1').getContext('2d');
		let myMixedChart = new Chart(ctx, {
			type: 'bar',
			data: chartData,
			options: {
				/* indexAxis: 'y', */
				responsive: true,				/*자동크기 조정*/
				maintainAspectRatio : false, 	/*가로세로 비율*/
				elements: {
					line: {
						tension: 0.000001		/*line 곡선 조절*/
					}
				},
				legend: {
					position: 'left',
					align : "end",
					labels: {
						fontSize : 11,
						padding : 5,
					}
				},
				title: {
					display: false,
					text: '출입이력현황'
				},
				tooltips: {
					mode: 'index',
					intersect: true
				},
				layout: {
					padding: {
						left: 0,
						right: 0,
						top: 0,
						bottom: 10
					}
				}
			}
		});
		//myMixedChart.update();
	}

	// function fnLogInfoCntSet (data) {
	// 	if(data == null) {
	// 		$("#visitReqCnt").html("0");
	// 		$("#dayCnt").html("0");
	// 		$("#dayUserCnt").html("0");
	// 		$("#obsCnt").html("0");
	// 		$("#inmateCnt").html("0");
	// 	} else {
	// 		$("#visitReqCnt").html(data.visitReqCnt==null||data.visitReqCnt==""?"0":data.visitReqCnt);
	// 		$("#dayCnt").html(data.dayCnt==null||data.dayCnt==""?"0":data.dayCnt);
	// 		$("#dayUserCnt").html(data.dayUserCnt==null||data.dayUserCnt==""?"0":data.dayUserCnt);
	// 		$("#obsCnt").html(data.obsCnt==null||data.obsCnt==""?"0":data.obsCnt);
	// 		$("#inmateCnt").html(data.inmateCnt==null||data.inmateCnt==""?"0":data.inmateCnt);
	// 	}
	// }

	function fnLogInfoListSet (data) {
		console.log("fnLogInfoListSet");
		console.log(data);

		data.entHistList = [
			{id : "188", evtDtStr : "12-13 19:57:50", doorNm : "1동 지하1층 피난 공동구 239번(2동방향) OUT", empNm : "홍희경", cardStateTypNm : "사용중", entEvtTypNm : "성공"},
			{id : "187", evtDtStr : "12-12 22:53:44", doorNm : "1동 지하1층 피난 공동구 239번(2동방향) OUT", empNm : "홍희경", cardStateTypNm : "사용중", entEvtTypNm : "성공"},
			{id : "186", evtDtStr : "12-12 22:44:10", doorNm : "1동 지하1층 피난 공동구 239번(2동방향) OUT", empNm : "홍희경", cardStateTypNm : "사용중", entEvtTypNm : "성공"},
			{id : "185", evtDtStr : "12-12 22:42:09", doorNm : "1동 지하1층 피난 공동구 239번(2동방향) OUT", empNm : "홍희경", cardStateTypNm : "사용중", entEvtTypNm : "성공"},
			{id : "184", evtDtStr : "12-12 22:13:42", doorNm : "1동 지하1층 피난 공동구 239번(2동방향) OUT", empNm : "홍희경", cardStateTypNm : "사용중", entEvtTypNm : "성공"},
			{id : "183", evtDtStr : "12-12 21:59:21", doorNm : "1동 지하1층 피난 공동구 239번(2동방향) OUT", empNm : "홍희경", cardStateTypNm : "사용중", entEvtTypNm : "성공"},
			{id : "182", evtDtStr : "12-12 21:56:15", doorNm : "1동 지하1층 피난 공동구 239번(2동방향) OUT", empNm : "홍희경", cardStateTypNm : "사용중", entEvtTypNm : "성공"},
			{id : "181", evtDtStr : "12-12 21:48:03", doorNm : "1동 지하1층 피난 공동구 239번(2동방향) OUT", empNm : "홍희경", cardStateTypNm : "사용중", entEvtTypNm : "성공"},
			{id : "110", evtDtStr : "12-11 21:00:00", doorNm : "1동 지하1층 피난 공동구 239번(2동방향) OUT", empNm : "홍희경", cardStateTypNm : "사용중", entEvtTypNm : "성공"},
			{id : "109", evtDtStr : "12-11 20:58:30", doorNm : "1동 지하1층 피난 공동구 239번(2동방향) OUT", empNm : "홍희경", cardStateTypNm : "사용중", entEvtTypNm : "성공"},
			{id : "108", evtDtStr : "12-11 20:48:00", doorNm : "1동 지하1층 피난 공동구 239번(2동방향) OUT", empNm : "홍희경", cardStateTypNm : "사용중", entEvtTypNm : "성공"},
			{id : "107", evtDtStr : "12-11 15:35:20", doorNm : "1동 지하1층 피난 공동구 239번(2동방향) OUT", empNm : "홍희경", cardStateTypNm : "사용중", entEvtTypNm : "성공"},
			{id : "106", evtDtStr : "12-11 15:20:01", doorNm : "1동 지하1층 피난 공동구 239번(2동방향) OUT", empNm : "홍희경", cardStateTypNm : "사용중", entEvtTypNm : "성공"},
			{id : "105", evtDtStr : "12-11 13:48:03", doorNm : "1동 지하1층 피난 공동구 239번(2동방향) OUT", empNm : "홍희경", cardStateTypNm : "사용중", entEvtTypNm : "성공"},
			{id : "104", evtDtStr : "12-10 13:31:10", doorNm : "1동 지하1층 피난 공동구 239번(2동방향) OUT", empNm : "홍희경", cardStateTypNm : "사용중", entEvtTypNm : "성공"},
			{id : "103", evtDtStr : "12-10 13:30:00", doorNm : "1동 지하1층 피난 공동구 239번(2동방향) OUT", empNm : "홍희경", cardStateTypNm : "사용중", entEvtTypNm : "성공"},
			{id : "102", evtDtStr : "12-10 12:50:00", doorNm : "1동 지하1층 피난 공동구 239번(2동방향) OUT", empNm : "홍희경", cardStateTypNm : "사용중", entEvtTypNm : "성공"},
			{id : "101", evtDtStr : "12-09 10:30:03", doorNm : "1동 지하1층 피난 공동구 239번(2동방향) OUT", empNm : "홍희경", cardStateTypNm : "사용중", entEvtTypNm : "성공"},
			{id : "100", evtDtStr : "12-09 10:10:10", doorNm : "1동 지하1층 피난 공동구 239번(2동방향) OUT", empNm : "홍희경", cardStateTypNm : "사용중", entEvtTypNm : "성공"},
			{id : "99", evtDtStr : "12-09 08:49:03", doorNm : "1동 지하1층 피난 공동구 239번(2동방향) OUT", empNm : "홍희경", cardStateTypNm : "사용중", entEvtTypNm : "성공"},
			{id : "98", evtDtStr : "12-09 08:48:03", doorNm : "1동 지하1층 피난 공동구 239번(2동방향) OUT", empNm : "홍희경", cardStateTypNm : "사용중", entEvtTypNm : "성공"},
			{id : "97", evtDtStr : "12-09 07:30:03", doorNm : "1동 지하1층 피난 공동구 239번(2동방향) OUT", empNm : "홍희경", cardStateTypNm : "사용중", entEvtTypNm : "성공"},
		];

		if(data == null || data.entHistList == null) {
			$("#entHistListBody").html("<tr><th class='h_35px' colspan='6'>조회 목록이 없습니다.</th></tr>");
		} else {
			var histList = data.entHistList;
			var str = "";
			for(var i in histList) {
				str += "<tr>";
				str += "<td>"+histList[i].id+"</td>";
				str += "<td>"+histList[i].evtDtStr+"</td>";
				str += "<td>"+histList[i].doorNm+"</td>";
				str += "<td>"+histList[i].empNm+"</td>";
				str += "<td>"+histList[i].cardStateTypNm+"</td>";
				str += "<td>"+histList[i].entEvtTypNm+"</td>";
				str += "</tr>";
			}
			$("#entHistListBody").html(str);
		}
	}
	
	function fnNoticeInfoListSet (data) {
		if(data == null || data.noticeList == null) {
			$("#noticeListBody").html("<tr><th class='h_35px' colspan='7'>조회 목록이 없습니다./th></tr>");
		} else {
			data = data.noticeList;
			var str2 = "";
			for(var i in data) {
				str2 += "<tr onclick='fnBoardDetail("+data[i].nttId+",00000000000000000001)'>";
				str2 += "<td>"+data[i].nttSj.substr(0,45)+(data[i].nttSj!=null && data[i].nttSj.length>45?"..":"")+"</td>";
				//str2 += "<td>"+data[i].nttCn.replace("<p>","").replace("</p>","").replace("<br>","").substr(0,25)+(data[i].nttCn!=null && data[i].nttCn.length>30?"...":"")+"</td>";
				str2 += "<td>" + data[i].nttCn.replace(/(<([^>]+)>)/ig, "").substr(0,25) + (data[i].nttCn != null && data[i].nttCn.length > 30 ? "..." : "") + "</td>";
				str2 += "<td>"+data[i].registNm+"</td>";
				str2 += "<td>"+(data[i].registDt!=null && data[i].registDt.length>16?data[i].registDt.substr(0,16):data[i].registDt)+"</td>";
				str2 += "</tr>";
			}
			
			$("#noticeListBody").html(str2);
		}
	}
	
	// function fnQaListSet (data) {
	// 	if(data == null || data.qaList == null) {
	// 		$("#qaListBody").html("<tr><th class='h_35px' colspan='7'>조회 목록이 없습니다.</th></tr>");
	// 	} else {
	// 		data = data.qaList;
	// 		var str2 = "";
	// 		for(var i in data) {
	// 			str2 += "<tr onclick='fnBoardDetail("+data[i].nttId+",00000000000000000002)'>";
	// 			str2 += "<td>"+data[i].nttSj.substr(0,45)+(data[i].nttSj!=null && data[i].nttSj.length>45?"..":"")+"</td>";
	// 			//str2 += "<td>"+data[i].nttCn.replace("<p>","").replace("</p>","").replace("<br>","").substr(0,25)+(data[i].nttCn!=null && data[i].nttCn.length>30?"...":"")+"</td>";
	// 			str2 += "<td>" + data[i].nttCn.replace(/(<([^>]+)>)/ig, "").substr(0,25) + (data[i].nttCn != null && data[i].nttCn.length > 30 ? "..." : "") + "</td>";
	// 			str2 += "<td>"+data[i].registNm+"</td>";
	// 			str2 += "<td>"+(data[i].registDt!=null && data[i].registDt.length>16?data[i].registDt.substr(0,16):data[i].registDt)+"</td>";
	// 			str2 += "</tr>";
	// 		}
	// 		$("#qaListBody").html(str2);
	// 	}
	// }

	// function fnChartDraw (arrStatDt) {
	// 	google.charts.load('current', {'packages':['corechart']});
	// 	google.charts.setOnLoadCallback(function () {
	// 		var data = google.visualization.arrayToDataTable(arrStatDt);
	// 		var options = {
	// 			title : '',
	// 			vAxis: {title: ''},
	// 			hAxis: {title: ''},
	// 			seriesType: 'bars',
	// 				colors: ['#173d93', '#77b8bd', '#016879', '#a7c846'],
	// 				series: {0: {type: 'line'}}
	// 		};
	// 		var chart = new google.visualization.ComboChart(document.getElementById('chart_div'));
	// 	        chart.draw(data, options);
	// 	});
	// }

	function autoRefresh(value) {
		var reloadYn = $("#reloadYn");
		if(value == "N"){
			reloadYn.val("Y");
			var sec = parseInt($("#intervalSecond").val());
			alert(sec +  "초마다 자동 새로고침이 시작됩니다.");
			$("#btnReloadYn").val("Y");
			$("#btnReloadYn").html("중지");
			threadRefresh = setInterval(function(){this.reload()}, sec * 1000);
			reload();
		} else {
			reloadYn.val("N");
			alert("자동 새로고침이 중지되었습니다.");
			$("#btnReloadYn").val("N");
			$("#btnReloadYn").html("시작");
			clearInterval(threadRefresh);
		}
	}

	function fnGateLog(){
		f = document.frmSearch;
		f.action = "report/entHist/list.do";
		f.submit();
	}
	
	function fnBoardList(bbsId){
		f = document.frmSearch;
		f.action = "/boardInfo/"+pad(bbsId,20)+"/list.do";
		f.submit();
	}

	function fnBoardDetail(nttId, bbsId){
		var f = document.frmSearch;
		
		$("input:hidden[id=hidNttId]").val(nttId);
		f.action = "/boardInfo/"+pad(bbsId,20)+"/detail.do";
		f.submit();
	}

	function pad(n, width) {
		  n = n + '';
		  return n.length >= width ? n : new Array(width - n.length + 1).join('0') + n;
	}

</script>
<div class="main_a">

	<div class="w_200px"></div>
	<form id="frmSearch" name="frmSearch" method="post" onsubmit="return false;">
	<input type="hidden" id="hidNttId" name="hidNttId">
		<input type="hidden" id="reloadYn" name="reloadYn" value='<c:out value="${reloadYn}"/>'>
		<div class="w_180px">
			<div class="comm_search mr_5">
	            <input type="text" class="w_90px input_com onlyNumber" id="intervalSecond" name="intervalSecond" value="5" maxlength="3"  placeholder="">
	            <div class="plus_btn" id="btnRefreshIncrease"></div>
	        </div>
	        <button type="button" id="btnReloadYn" value='N' onclick="autoRefresh(this.value)" class="btn_middle color_gray">시작</button>
		</div>
	</form>

</div>

<div class="main_b">

	<div class="main_left" style="width: 49%;">
		<div class="inbox1" style="width: 100%; height: 360px;">
			<div class="title">
				출입이력 현황
			</div>
			<div class="gr">
				<div style="height: 320px; margin-left: 10px;">
					<canvas id="canvas1" width="" height="160"></canvas>
				</div>
			</div>
		</div>
		<div class="inbox7" style="width: 100%; height: 360px; margin-top:40px;">
			<div class="title">
				출입카드 유형
			</div>
			<div class="gr">
				<div style="height: 320px; margin-left: 10px;">
					<canvas id="canvas2" width="" height="160"></canvas>
				</div>
			</div>
		</div>
	</div>
	<div class="main-right" style="width: 49%;">
		<jsp:include page="/WEB-INF/jsp/cubox/common/main_ent_hist.jsp" flush="false"/>
	</div>

</div>
