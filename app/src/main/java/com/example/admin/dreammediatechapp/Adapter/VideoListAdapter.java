package com.example.admin.dreammediatechapp.Adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Admin on 2018/1/17.
 */

public class VideoListAdapter {
    public List<Map<String,Object>>getData(){
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        Map<String,Object>map = new HashMap<String,Object>();

        map.put("video_cover","deafult");
        map.put("video_title","deafult");
        map.put("video_owner","deafult");
        map.put("video_categories","deafult");
        map.put("video_watch","deafult");
        return list;
    }
}
