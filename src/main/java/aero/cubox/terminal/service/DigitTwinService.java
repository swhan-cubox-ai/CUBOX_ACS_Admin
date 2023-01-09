package aero.cubox.terminal.service;

import aero.cubox.terminal.service.impl.TerminalDAO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

@Service("DigitTwinService")
public class DigitTwinService extends EgovAbstractServiceImpl {

    @Autowired
    TerminalService terminalService;

//    @Value("#{property['Globals.digitwin_host']}")
//    private String digitwin_host;


    private static String txtKey = "Yk1g690tRe1bMk12";
    private static String txtIv = "H21nBU97L19Vc122";

    public String sendToDigitTwin(Map<String, Object> param, String flag){
        String result = "fail";
        try{
            String terminalCd = String.valueOf(param.get("terminalCd"));
            Map<String, String> terminalInfo = terminalService.getTerminalInfo(String.valueOf(param.get("doorId")));

            JSONObject[] arr = new JSONObject[1];

            JSONObject jObject = new JSONObject();
            jObject.put("terminalCd", terminalCd);
            jObject.put("terminalTyp", param.get("terminalTyp"));
            jObject.put("buildingCd", terminalInfo.get("building_cd"));
            jObject.put("floorCd", terminalInfo.get("floor_cd"));
            jObject.put("doorNm", terminalInfo.get("door_nm"));
            jObject.put("ipAddr", param.get("ipAddr"));
            jObject.put("complexAuthTyp", param.get("complexAuthTyp"));
            jObject.put("opModeTyp", param.get("opModeTyp"));
            jObject.put("useYn", param.get("useYn"));
            if("D".equals(flag)){
                jObject.put("deleteYn", "Y");
            } else {
                jObject.put("deleteYn", "N");
            }
            if("C".equals(flag)) jObject.put("createdAt", new Timestamp(new Date().getTime()));
            jObject.put("updatedAt", new Timestamp(new Date().getTime()));

            arr[0] = jObject;

            String data = Arrays.toString(arr);
            String encodedData = strEncode(data);

            HttpEntity<String> requestEntity = new HttpEntity<>(encodedData);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange("http://172.31.101.45:10002/acs/cubox/deviceChange", HttpMethod.POST, requestEntity , String.class);

            result = "success";
        } catch (Exception e){
            return "fail";
        }

        return result;

    }



    public static String strEncode(String planeText) throws Exception {

        byte[] key = txtKey.getBytes( "UTF-8" );
        byte[] iv = txtIv.getBytes( "UTF-8" );
        SecretKeySpec secretkey = new SecretKeySpec( key, "AES" );
        IvParameterSpec param = new IvParameterSpec( iv );
        Cipher cipher = Cipher.getInstance( "AES/CBC/PKCS5Padding" );
        cipher.init( Cipher.ENCRYPT_MODE, secretkey, param );
        byte[] encrypted = cipher.doFinal( planeText.getBytes( "UTF-8" ) );
        byte[] encode = Base64.encodeBase64( encrypted );
        String encodeText = new String( encode, "UTF-8" );

        return encodeText;
    }


}
