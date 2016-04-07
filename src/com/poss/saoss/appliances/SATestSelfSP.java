package com.poss.saoss.appliances;

import poss.client.common.SPBase;
import poss.xml.*;
import java.util.*;


public final class SATestSelfSP extends SPBase
{
/*
<<<<< com.poss.saoss.data.aftersale.TSATestSelf class Info Start >>>>>
Summary:
---------------------------------
DataBaseName = boss_data
TableName  = SAOSS_T_SA_TESTSELF
TableType  = TABLE
TableRemark= 家电自测

Detail:
--------------------------------------------------------------------------------------------------------
| COLUMN_NAME         | TYPE_NAME | DATA_TYPE | JAVA_CLASS | COLUMN_SIZE | DECIMAL_DIGITS | NO_NULLABLE |
--------------------------------------------------------------------------------------------------------
| TEST_ID             | BIGINT    | -5        | Long       | 19          | 0              | true        |
| PROVIDER_ID         | VARCHAR   | 12        | String     | 32          | 0              | true        |
| TEST_TIME           | BIGINT    | -5        | Long       | 19          | 0              | true        |
| SA_TYPE             | VARCHAR   | 12        | String     | 32          | 0              | true        |
| SA_CODE             | VARCHAR   | 12        | String     | 64          | 0              | true        |
| MODULE_ID           | VARCHAR   | 12        | String     | 32          | 0              | true        |
| USER_ID             | VARCHAR   | 12        | String     | 32          | 0              | true        |
| TEST_RESULT         | VARCHAR   | 12        | String     | 16          | 0              | true        |
| ERROR_STATION       | VARCHAR   | 12        | String     | 64          | 0              | false       |
| ERROR_DETAIL        | VARCHAR   | 12        | String     | 512         | 0              | false       |
| ERROR_LOG           | VARCHAR   | 12        | String     | 1024        | 0              | false       |
| RESULT_STATUS       | VARCHAR   | 12        | String     | 16          | 0              | true        |
| NOTE                | VARCHAR   | 12        | String     | 64          | 0              | false       |
| REMARK              | VARCHAR   | 12        | String     | 256         | 0              | false       |
--------------------------------------------------------------------------------------------------------
<<<<< com.poss.saoss.data.aftersale.TSATestSelf class Info End >>>>>
*/


private static final String business = "satestselfserv";
public static final XMLObject packAddList(String pIP,String pRouteNote, String pOP,String pOPPIN,String pCustomer, String pCustomerPIN,ArrayList<XMLElement> pInfoList)
{
    return getXMLObject(pIP, pRouteNote, pOP, pOPPIN, pCustomer, pCustomerPIN,business, "add", pInfoList);
}

public static final XMLObject packAddSingle(String pIP,String pRouteNote, String pOP,String pOPPIN,String pCustomer, String pCustomerPIN,
             /* String pTEST_ID,  */ String pPROVIDER_ID, String pSA_TYPE, String pSA_CODE, String pMODULE_ID, String pUSER_ID, String pTEST_RESULT, String pERROR_STATION, String pERROR_DETAIL, String pERROR_LOG, String pRESULT_STATUS, String pNOTE, String pREMARK)
{
    XMLElement information = getInformation();
//     information.addAttribute("test_id", format("800"));
    information.addAttribute("provider_id", format(pPROVIDER_ID));
//    information.addAttribute("test_time", format("1452841388205"));
    information.addAttribute("sa_type", format(pSA_TYPE));
    information.addAttribute("sa_code", format(pSA_CODE));
    information.addAttribute("module_id", format(pMODULE_ID));
    information.addAttribute("user_id", format(pUSER_ID));
    information.addAttribute("test_result", format(pTEST_RESULT));
    information.addAttribute("error_station", format(pERROR_STATION));
    information.addAttribute("error_detail", format(pERROR_DETAIL));
    information.addAttribute("error_log", format(pERROR_LOG));
    information.addAttribute("result_status", format(pRESULT_STATUS));
    information.addAttribute("note", format(pNOTE));
    information.addAttribute("remark", format(pREMARK));
    return getXMLObject(pIP, pRouteNote, pOP, pOPPIN, pCustomer, pCustomerPIN,business, "add", information);
    }

public static final XMLObject packAddSuccessSingle(String pIP,String pRouteNote, String pOP,String pOPPIN,String pCustomer, String pCustomerPIN,
        /* String pTEST_ID,  */ String pPROVIDER_ID, String pSA_TYPE, String pSA_CODE, String pMODULE_ID, String pUSER_ID, String pTEST_RESULT, String pERROR_STATION, String pERROR_DETAIL, String pERROR_LOG, String pRESULT_STATUS, String pNOTE, String pREMARK)
{
XMLElement information = getInformation();
//information.addAttribute("test_id", format(pTEST_ID));
information.addAttribute("provider_id", format(pPROVIDER_ID));
//information.addAttribute("test_time", format(pTEST_TIME));
information.addAttribute("sa_type", format(pSA_TYPE));
information.addAttribute("sa_code", format(pSA_CODE));
information.addAttribute("module_id", format(pMODULE_ID));
information.addAttribute("user_id", format(pUSER_ID));
information.addAttribute("test_result", format(pTEST_RESULT));
information.addAttribute("error_station", format(pERROR_STATION));
information.addAttribute("error_detail", format(pERROR_DETAIL));
information.addAttribute("error_log", format(pERROR_LOG));
information.addAttribute("result_status", format(pRESULT_STATUS));
information.addAttribute("note", format(pNOTE));
information.addAttribute("remark", format(pREMARK));
return getXMLObject(pIP, pRouteNote, pOP, pOPPIN, pCustomer, pCustomerPIN,business, "addSuccess", information);
}

public static final XMLObject packDelList(String pIP,String pRouteNote, String pOP,String pOPPIN,String pCustomer, String pCustomerPIN,
        ArrayList<XMLElement> pInfoList)
{
    return getXMLObject(pIP, pRouteNote, pOP, pOPPIN, pCustomer, pCustomerPIN,business, "del", pInfoList);
}

public static final XMLObject packDelSingle(String pIP,String pRouteNote, String pOP,String pOPPIN,String pCustomer, String pCustomerPIN,
        String pTEST_ID /* String pPROVIDER_ID,  */  /* String pTEST_TIME,  */  /* String pSA_TYPE,  */  /* String pSA_CODE,  */  /* String pMODULE_ID,  */  /* String pUSER_ID,  */  /* String pTEST_RESULT,  */  /* String pERROR_STATION,  */  /* String pERROR_DETAIL,  */  /* String pERROR_LOG,  */  /* String pRESULT_STATUS,  */  /* String pNOTE,  */  /* String pREMARK */ )
{
    XMLElement information = getInformation();
    information.addAttribute("test_id", format(pTEST_ID));
//    information.addAttribute("provider_id", format(pPROVIDER_ID));
//    information.addAttribute("test_time", format(pTEST_TIME));
//    information.addAttribute("sa_type", format(pSA_TYPE));
//    information.addAttribute("sa_code", format(pSA_CODE));
//    information.addAttribute("module_id", format(pMODULE_ID));
//    information.addAttribute("user_id", format(pUSER_ID));
//    information.addAttribute("test_result", format(pTEST_RESULT));
//    information.addAttribute("error_station", format(pERROR_STATION));
//    information.addAttribute("error_detail", format(pERROR_DETAIL));
//    information.addAttribute("error_log", format(pERROR_LOG));
//    information.addAttribute("result_status", format(pRESULT_STATUS));
//    information.addAttribute("note", format(pNOTE));
//    information.addAttribute("remark", format(pREMARK));
    return getXMLObject(pIP, pRouteNote, pOP, pOPPIN, pCustomer, pCustomerPIN,business, "del", information);
}

public static final XMLObject packQuery(String pIP,String pRouteNote, String pOP,String pOPPIN,String pCustomer, String pCustomerPIN,
            String pTEST_ID, String pPROVIDER_ID, String pTEST_TIME, String pSA_TYPE, String pSA_CODE, String pMODULE_ID, String pUSER_ID, String pTEST_RESULT, String pERROR_STATION,String pRESULT_STATUS,  /* String pNOTE,  */  /* String pREMARK,  */ String pQueryType,String pPage,String pSize)
{
    XMLElement information = getInformationAddPage(pQueryType,pPage,pSize);
    information.addAttribute("test_id", format(pTEST_ID));
    information.addAttribute("provider_id", format(pPROVIDER_ID));
    information.addAttribute("test_time", format(pTEST_TIME));
    information.addAttribute("sa_type", format(pSA_TYPE));
    information.addAttribute("sa_code", format(pSA_CODE));
    information.addAttribute("module_id", format(pMODULE_ID));
    information.addAttribute("user_id", format(pUSER_ID));
    information.addAttribute("test_result", format(pTEST_RESULT));
    information.addAttribute("error_station", format(pERROR_STATION));
//    information.addAttribute("error_detail", format(pERROR_DETAIL));
//    information.addAttribute("error_log", format(pERROR_LOG));
    information.addAttribute("result_status", format(pRESULT_STATUS));
//    information.addAttribute("note", format(pNOTE));
//    information.addAttribute("remark", format(pREMARK));
    return getXMLObject(pIP, pRouteNote, pOP, pOPPIN, pCustomer, pCustomerPIN,business, "query", information);
}

public static final XMLObject packUpdate(String pIP,String pRouteNote, String pOP,String pOPPIN,String pCustomer, String pCustomerPIN,
        String pTEST_ID,  /* String pPROVIDER_ID,  */  /* String pTEST_TIME,  */  /* String pSA_TYPE,  */  /* String pSA_CODE,  */  /* String pMODULE_ID,  */  /* String pUSER_ID,  */  /* String pTEST_RESULT,  */  /* String pERROR_STATION,  */  /* String pERROR_DETAIL,  */  /* String pERROR_LOG,  */  /* String pRESULT_STATUS,  */  /* String pNOTE,  */  /* String pREMARK,  */ 
         /* String pNewTEST_ID,  */ String pNewSA_TYPE, String pNewSA_CODE, String pNewMODULE_ID, String pNewUSER_ID, String pNewTEST_RESULT, String pNewERROR_STATION, String pNewERROR_DETAIL, String pNewERROR_LOG, String pNewRESULT_STATUS, String pNewNOTE, String pNewREMARK)
{
    XMLElement information = getInformation();
    information.addAttribute("test_id", format(pTEST_ID));
 //     information.addAttribute("provider_id", format(pPROVIDER_ID));
 //     information.addAttribute("test_time", format(pTEST_TIME));
 //     information.addAttribute("sa_type", format(pSA_TYPE));
 //     information.addAttribute("sa_code", format(pSA_CODE));
 //     information.addAttribute("module_id", format(pMODULE_ID));
 //     information.addAttribute("user_id", format(pUSER_ID));
 //     information.addAttribute("test_result", format(pTEST_RESULT));
 //     information.addAttribute("error_station", format(pERROR_STATION));
 //     information.addAttribute("error_detail", format(pERROR_DETAIL));
 //     information.addAttribute("error_log", format(pERROR_LOG));
 //     information.addAttribute("result_status", format(pRESULT_STATUS));
 //     information.addAttribute("note", format(pNOTE));
 //     information.addAttribute("remark", format(pREMARK));

         //     information.addAttribute("new_test_id", format(pNewTEST_ID));
//    information.addAttribute("new_provider_id", format(pNewPROVIDER_ID));
//    information.addAttribute("new_test_time", format(pNewTEST_TIME));
    information.addAttribute("new_sa_type", format(pNewSA_TYPE));
    information.addAttribute("new_sa_code", format(pNewSA_CODE));
    information.addAttribute("new_module_id", format(pNewMODULE_ID));
    information.addAttribute("new_user_id", format(pNewUSER_ID));
    information.addAttribute("new_test_result", format(pNewTEST_RESULT));
    information.addAttribute("new_error_station", format(pNewERROR_STATION));
    information.addAttribute("new_error_detail", format(pNewERROR_DETAIL));
    information.addAttribute("new_error_log", format(pNewERROR_LOG));
    information.addAttribute("new_result_status", format(pNewRESULT_STATUS));
    information.addAttribute("new_note", format(pNewNOTE));
    information.addAttribute("new_remark", format(pNewREMARK));
    return getXMLObject(pIP, pRouteNote, pOP, pOPPIN, pCustomer, pCustomerPIN,business, "update", information);
}

}
