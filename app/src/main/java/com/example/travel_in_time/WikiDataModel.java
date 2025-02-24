package com.example.travel_in_time;

import java.util.List;

public class WikiDataModel {
    private String text;
    private String year;
    private List<Page> pages;


    public WikiDataModel(String text, String year, List<Page> pages) {
        this.text = text;
        this.year = year;
        this.pages = pages;
    }

    public String getText() {
        return text;
    }

    public void setText(String title) {
        this.text = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public List<Page> getPages() {
        return pages;
    }

    public void setPages(List<Page> pages) {
        this.pages = pages;
    }

    public static class Page {
//        private String displaytitle;
        private Thumbnail thumbnail;
        private String extract;

        public Page( Thumbnail thumbnail, String extract) {

            this.thumbnail = thumbnail;
            this.extract = extract;
        }

        public Thumbnail getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(Thumbnail thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getExtract() {
            return extract;
        }

        public void setExtract(String extract) {
            this.extract = extract;
        }

        public static class Thumbnail{
            private String source;
            private Integer width;
            private Integer height;

            public Thumbnail(String source, Integer width, Integer height) {
                this.source = source;
                this.width = width;
                this.height = height;
            }

            public String getSource() {
                return source;
            }

            public void setSource(String source) {
                this.source = source;
            }

            public Integer getWidth() {
                return width;
            }

            public void setWidth(Integer width) {
                this.width = width;
            }

            public Integer getHeight() {
                return height;
            }

            public void setHeight(Integer height) {
                this.height = height;
            }
        }

    }
}
