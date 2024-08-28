package org.cherrygirl.service;

import java.util.Map;

/**
 * @author nannaness
 */
public interface CloudMusicService {

    /**
     * 搜索歌曲
     *
     * @param name
     * @param singer
     * @return
     */
    Map search(String name , String singer);

    /**
     * 搜索歌曲
     *
     * @param text
     * @return
     */
    Map search(String text);
}
