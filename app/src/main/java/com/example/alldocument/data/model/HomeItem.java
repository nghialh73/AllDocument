package com.example.alldocument.data.model;

import com.example.alldocument.R;

public class HomeItem {
    String name = "";
    HomeItemType type = HomeItemType.TEXT;
    int icon = R.drawable.ic_txt;
    int background = R.drawable.background_color_txt;
    int count = 0;
    public HomeItem(String name, HomeItemType type, int icon, int background, int count) {
        this.name = name;
        this.type = type;
        this.icon = icon;
        this.background = background;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HomeItemType getType() {
        return type;
    }

    public void setType(HomeItemType type) {
        this.type = type;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getBackground() {
        return background;
    }

    public void setBackground(int background) {
        this.background = background;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

