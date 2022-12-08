package aero.cubox.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("menuUrlSetting")
public class MenuUrlSetting {

    private static final Logger LOGGER = LoggerFactory.getLogger(MenuUrlSetting.class);

    public static String getMenuUrl(String menu_cd){
        String menuUrl = "";

        if("0100".equals(menu_cd)) return  "";    // 시스템관리
        else if ("0101".equals(menu_cd)) return  "";           // 환경설정
        else if ("0102".equals(menu_cd)) return "/user/list.do";       // 사용자관리
        else if ("0103".equals(menu_cd)) return "/menuAuth/list.do";   // 메뉴권한관리
        else if ("0104".equals(menu_cd)) return "/system/actLog/list.do";           // 사용자 활동 로그
        else if ("0105".equals(menu_cd)) return "/system/privacy/list.do";           // 개인정보폐기
        else if ("0106".equals(menu_cd)) return "/system/stat/list.do";           // 통계

        if ("0200".equals(menu_cd)) return "";                              // 출입문 관리
        else if ("0201".equals(menu_cd)) return "/door/management.do";      // 출입문 관리
        else if ("0206".equals(menu_cd)) return "/door/group/list.do";  // 출입문 그룹관리
        else if ("0202".equals(menu_cd)) return "/door/schedule/list.do";        // 출입문 스케쥴
        else if ("0203".equals(menu_cd)) return "/door/alarm/list.do"; // 출입문 알람그룹
        else if ("0204".equals(menu_cd)) return "/holiday/list.do";         // 공휴일 관리
        else if ("0205".equals(menu_cd)) return "/terminal/list.do";        // 단말기관리

        if ("0300".equals(menu_cd)) return "";                // 권한관리
        else if ("0301".equals(menu_cd)) return "/auth/emp/list.do";           // 인사정보관리
        else if ("0302".equals(menu_cd)) return "/auth/dept/list.do";           // 부서관리
        else if ("0303".equals(menu_cd)) return "/auth/door/list.do";           // 출입문 권한그룹 관리

        if ("0400".equals(menu_cd)) return "";                // 보고서
        else if ("0401".equals(menu_cd)) return "/report/entHist/list.do";           // 출입이력
        else if ("0402".equals(menu_cd)) return "/report/alarmHist/list.do";           // 알람이력
        else if ("0403".equals(menu_cd)) return "";           // 시간대별 출입이력
        else if ("0404".equals(menu_cd)) return "";           // 장시간개방 알람이력
        else if ("0405".equals(menu_cd)) return "";           // 대량조회신청결과
        else if ("0406".equals(menu_cd)) return "/report/err/faceFeatureList.do";           // 특징점추출 실패 조회


        return  menuUrl;
    }
}
