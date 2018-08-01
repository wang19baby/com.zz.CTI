package com.zz.CTI.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class AnalyzeStringtoJsonUtlis {


/**
* json字符串转list
* @param jsonFile
* @return
* @throws Exception
* @author 64k-chen
*/
public static List<Map<String, String>> GetJsonListByString(String jsonFile) {
        JSONArray arry = JSONArray.fromObject(jsonFile);
        List<Map<String, String>> rsList = new ArrayList<Map<String, String>>();
        for (int i = 0; i < arry.size(); i++)
        {
            JSONObject jsonObject = arry.getJSONObject(i);
            Map<String, String> map = new HashMap<String, String>();
            for (Iterator<?> iter = jsonObject.keys(); iter.hasNext();)
            {
                String key = (String) iter.next();
                String value = jsonObject.get(key).toString();
                map.put(key, value);
            }
            rsList.add(map);
        }
        return rsList;
    }
} 