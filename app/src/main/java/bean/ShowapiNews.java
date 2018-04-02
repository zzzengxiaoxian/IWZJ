package bean;

import java.security.PublicKey;
import java.util.List;

/**
 * Created by dell on 2016/10/18.
 */
public class ShowapiNews {

    public int showapi_res_code;
    public String showapi_res_error;
    public Showapi_res_body showapi_res_body;

    // 但是为了处理业务，我们还需要在子分类中保存父分类，最终会变成下面的情况
//但是上面的parent字段是因业务需要增加的，那么在序列化是并不需要，所以在序列化时就必须将其排除
//  public ShowapiNews parent;
    public Showapi_res_body getShowapi_res_body() {
        return showapi_res_body;
    }

    public void setShowapi_res_body(Showapi_res_body showapi_res_body) {
        this.showapi_res_body = showapi_res_body;
    }

    public String getShowapi_res_error() {

        return showapi_res_error;
    }

    public void setShowapi_res_error(String showapi_res_error) {
        this.showapi_res_error = showapi_res_error;
    }

    public int getShowapi_res_code() {

        return showapi_res_code;
    }

    public void setShowapi_res_code(int showapi_res_code) {
        this.showapi_res_code = showapi_res_code;
    }

    @Override
    public String toString() {
        return "ShowapiNews{" +
                "showapi_res_code=" + showapi_res_code +
                ", showapi_res_error='" + showapi_res_error + '\'' +
                ", showapi_res_body=" + showapi_res_body +
                '}';
    }
    public static class Showapi_res_body {


        public int ret_code;
        public Pagebean pagebean;


        public int getRet_code() {
            return ret_code;
        }

        public void setRet_code(int ret_code) {
            this.ret_code = ret_code;
        }

        public Pagebean getPagebean() {
            return pagebean;
        }

        public void setPagebean(Pagebean pagebean) {
            this.pagebean = pagebean;
        }

        @Override
        public String toString() {
            return "Showapi_res_body{" +
                    "ret_code=" + ret_code +
                    ", pagebean=" + pagebean +
                    '}';
        }

        public static class Pagebean {

            public int allNum;
            public int allPages;
            public int currentPage;
            public int maxResult;
            public List<Contentlist> contentlist;

            public int getAllNum() {
                return allNum;
            }

            public void setAllNum(int allNum) {
                this.allNum = allNum;
            }

            public int getAllPages() {
                return allPages;
            }

            public void setAllPages(int allPages) {
                this.allPages = allPages;
            }

            public int getCurrentPage() {
                return currentPage;
            }

            public void setCurrentPage(int currentPage) {
                this.currentPage = currentPage;
            }

            public int getMaxResult() {
                return maxResult;
            }

            public void setMaxResult(int maxResult) {
                this.maxResult = maxResult;
            }

            public List<Contentlist> getContentlist() {
                return contentlist;
            }

            public void setContentlist(List<Contentlist> contentlist) {
                this.contentlist = contentlist;
            }

            @Override
            public String toString() {
                return "Pagebean{" +
                        "allNum=" + allNum +
                        ", allPages=" + allPages +
                        ", currentPage=" + currentPage +
                        ", maxResult=" + maxResult +
                        ", contentlist=" + contentlist +
                        '}';
            }

            public static class Contentlist {
                public String channelId;
                public String channelName;
                public String content;
                public String desc;
                public String link;
                public String nid;
                public String pubDate;
                public int sentiment_display;
                public String source;
                public String title;
                public List<Imageurls> imageurls;

                public String getChannelId() {
                    return channelId;
                }

                public void setChannelId(String channelId) {
                    this.channelId = channelId;
                }

                public String getChannelName() {
                    return channelName;
                }

                public void setChannelName(String channelName) {
                    this.channelName = channelName;
                }

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }

                public String getDesc() {
                    return desc;
                }

                public void setDesc(String desc) {
                    this.desc = desc;
                }

                public String getLink() {
                    return link;
                }

                public void setLink(String link) {
                    this.link = link;
                }

                public String getNid() {
                    return nid;
                }

                public void setNid(String nid) {
                    this.nid = nid;
                }

                public String getPubDate() {
                    return pubDate;
                }

                public void setPubDate(String pubDate) {
                    this.pubDate = pubDate;
                }

                public int getSentiment_display() {
                    return sentiment_display;
                }

                public void setSentiment_display(int sentiment_display) {
                    this.sentiment_display = sentiment_display;
                }

                public String getSource() {
                    return source;
                }

                public void setSource(String source) {
                    this.source = source;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public List<Imageurls> getImageurls() {
                    return imageurls;
                }

                public void setImageurls(List<Imageurls> imageurls) {
                    this.imageurls = imageurls;
                }

                @Override
                public String toString() {
                    return "Contentlist{" +
                            "channelId='" + channelId + '\'' +
                            ", channelName='" + channelName + '\'' +
                            ", content='" + content + '\'' +
                            ", desc='" + desc + '\'' +
                            ", link='" + link + '\'' +
                            ", nid='" + nid + '\'' +
                            ", pubDate='" + pubDate + '\'' +
                            ", sentiment_display=" + sentiment_display +
                            ", source='" + source + '\'' +
                            ", title='" + title + '\'' +
                            ", imageurls=" + imageurls +
                            '}';
                }


                public static class Imageurls {

                    public int height;
                    public String url;
                    public int width;

                    public int getHeight() {
                        return height;
                    }

                    public void setHeight(int height) {
                        this.height = height;
                    }

                    public String getUrl() {
                        return url;
                    }

                    public void setUrl(String url) {
                        this.url = url;
                    }

                    public int getWidth() {
                        return width;
                    }

                    public void setWidth(int width) {
                        this.width = width;
                    }

                    @Override
                    public String toString() {
                        return "Imageurls{" +
                                "height=" + height +
                                ", url='" + url + '\'' +
                                ", width=" + width +
                                '}';
                    }
                }
            }
        }
    }

}
