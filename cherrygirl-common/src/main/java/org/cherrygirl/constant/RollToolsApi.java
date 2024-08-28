package org.cherrygirl.constant;

import org.cherrygirl.pojo.RollToolsApiVO;

import java.util.HashMap;
import java.util.Map;

/**
 * @author nannanness
 */
public class RollToolsApi {

    public static final String APP_ID = "mpwfdsknood8kohh";

    public static final String APP_SECRET = "RzBKWFRqbzVxZ3BHNmdkbWtmYXNFQT09";

    public static final String API_BASE_URL = "https://www.mxnzp.com/api";

    public static Map<String, RollToolsApiVO>  apiMap = new HashMap<>();

    static {
        apiMap.put("fl_img",new RollToolsApiVO("/image/girl/list/random", null));
        apiMap.put("jokes",new RollToolsApiVO("/jokes/list/random", null));
    }


}
