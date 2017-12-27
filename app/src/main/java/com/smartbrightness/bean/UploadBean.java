package com.smartbrightness.bean;

import java.util.List;

/**
 * Created by MMM on 2017/12/25.
 */
public class UploadBean {

    /**
     * item : {"state":1}
     * items : [{"imageName":"20171204144215000_16_img.jpg"}]
     */

    private ItemBean item;

    private List<ItemsBean> items;

    public ItemBean getItem() {
        return item;
    }

    public void setItem(ItemBean item) {
        this.item = item;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemBean {
        /**
         * state : 1
         */

        private int state;

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        @Override
        public String toString() {
            return "ItemBean{" +
                    "state=" + state +
                    '}';
        }
    }

    public static class ItemsBean {
        /**
         * imageName : 20171204144215000_16_img.jpg
         */

        private String imageName;

        public String getImageName() {
            return imageName;
        }

        public void setImageName(String imageName) {
            this.imageName = imageName;
        }

        @Override
        public String toString() {
            return "ItemsBean{" +
                    "imageName='" + imageName + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "UploadBean{" +
                "item=" + item +
                ", items=" + items +
                '}';
    }
}

