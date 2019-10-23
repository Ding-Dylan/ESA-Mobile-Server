package com.dingyongfei.util;

/**
 * @Author: Ding Yongfei
 */
public enum SeverityUtils {


    MAJOR("Major")
    {
        @Override
        public String getSeverityUrl() {
            return "https://asdwiki.isus.emc.com:8443/download/attachments/130514952/Major.png?version=2&modificationDate=1571547279603&api=v2";
        }
    },
    MINOR("Minor")
    {
        @Override
        public String getSeverityUrl() {
            return "https://asdwiki.isus.emc.com:8443/download/attachments/130514952/Minor.png?version=2&modificationDate=1571547291231&api=v2";
        }
    },
    INFO("Info")
    {
        @Override
        public String getSeverityUrl() {
            return "https://asdwiki.isus.emc.com:8443/download/attachments/130514952/Info.png?version=3&modificationDate=1571547308353&api=v2";
        }
    },
    NONE("None")
    {
        @Override
        public String getSeverityUrl() {
            return "https://asdwiki.isus.emc.com:8443/download/attachments/130514952/None.png?version=2&modificationDate=1571547324673&api=v2";
        }
    };

    private final String key;


    SeverityUtils(String key)
    {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public abstract String getSeverityUrl();
}
