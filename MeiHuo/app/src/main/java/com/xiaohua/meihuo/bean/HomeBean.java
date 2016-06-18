package com.xiaohua.meihuo.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/5/21.
 */
public class HomeBean{

    /**
     * id : 1
     * cid : 2
     * url : http://imgtu.chnhtp.com:8081/play/E2F8A43596F0F4540D2F06708441A11465BFE321/b3Tl.mp4
     * face : http://imgtu.chnhtp.com:8081/xin/1.jpg
     * title : エッチ4610青木 美愛
     * flag : 0
     */

    private List<BannerEntity> banner;
    /**
     * id : 43
     * cid : 1
     * url : http://ss.txt789.com//mp4/m12.mp4
     * face : http://qubo.kandou.cc:81/icons/201604/21/be64/5718cd3e64379.jpg
     * title : 恋人が情侣被人偷拍
     * flag : 1
     */

    private List<VideosEntity> videos;

    public void setBanner(List<BannerEntity> banner) {
        this.banner = banner;
    }

    public void setVideos(List<VideosEntity> videos) {
        this.videos = videos;
    }

    public List<BannerEntity> getBanner() {
        return banner;
    }

    public List<VideosEntity> getVideos() {
        return videos;
    }

    public static class BannerEntity {
        private String id;
        private String cid;
        private String url;
        private String face;
        private String title;
        private String flag;

        public void setId(String id) {
            this.id = id;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setFace(String face) {
            this.face = face;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getId() {
            return id;
        }

        public String getCid() {
            return cid;
        }

        public String getUrl() {
            return url;
        }

        public String getFace() {
            return face;
        }

        public String getTitle() {
            return title;
        }

        public String getFlag() {
            return flag;
        }
    }

    public static class VideosEntity {
        private String id;
        private String cid;
        private String url;
        private String face;
        private String title;
        private String flag;

        public void setId(String id) {
            this.id = id;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setFace(String face) {
            this.face = face;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getId() {
            return id;
        }

        public String getCid() {
            return cid;
        }

        public String getUrl() {
            return url;
        }

        public String getFace() {
            return face;
        }

        public String getTitle() {
            return title;
        }

        public String getFlag() {
            return flag;
        }
    }
}
