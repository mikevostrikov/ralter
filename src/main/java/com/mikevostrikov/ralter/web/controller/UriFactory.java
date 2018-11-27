/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mikevostrikov.ralter.web.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 
 * @author m.vostrikov
 */
public abstract class UriFactory {
    
    public abstract String getUri(Controller servlet, UriParameter... params);

    public abstract String getUri(Jsp jsp, UriParameter... params);
    
    public String getAppBaseUri() {
        return Application.RALTER_APP.getPath();
    };
    
    protected String toUri(Application context, Controller servlet, Jsp jsp, UriParameter... params){
        String result = "";
        if (context != null) result += context.getPath();
        if (servlet != null) result += servlet.getPath();
        if (jsp != null) result += jsp.getUri();
        if (params != null && params.length > 0) {
            String paramsGlued = getGluedParams(params);
            result += "?" + paramsGlued;
        }
        return result;
    };

    private String getGluedParams(UriParameter... params) {
        String[] paramsNameValuePairsGlued = Arrays.asList(params)
                .stream()
                .map(x -> x.getName() + "=" + urlEncode(x.getValue()))
                .collect(Collectors.toSet())
                .toArray(new String[0]);
        return String.join("&", paramsNameValuePairsGlued);
    }
    
    private String urlEncode(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex);
        }
    }
            
}
