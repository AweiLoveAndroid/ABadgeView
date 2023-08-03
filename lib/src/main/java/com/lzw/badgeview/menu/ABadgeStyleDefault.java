package com.lzw.badgeview.menu;

import android.graphics.Color;

import com.lzw.badgeview.R;

public enum ABadgeStyleDefault {
    
    GREY_ROUND("ROUND", new ABadgeStyleBean(R.drawable.drawable_default_action_item_badge_round, Color.parseColor("#e0e0e0"), Color.parseColor("#c7c7c7"))),
    DARK_GREY_ROUND("ROUND", new ABadgeStyleBean(R.drawable.drawable_default_action_item_badge_round, Color.parseColor("#606060"), Color.parseColor("#3e3e3e"))),
    RED_ROUND("ROUND", new ABadgeStyleBean(R.drawable.drawable_default_action_item_badge_round, Color.parseColor("#FF4444"), Color.parseColor("#CC0000"))),
    BLUE_ROUND("ROUND", new ABadgeStyleBean(R.drawable.drawable_default_action_item_badge_round, Color.parseColor("#33B5E5"), Color.parseColor("#0099CC"))),
    GREEN_ROUND("ROUND", new ABadgeStyleBean(R.drawable.drawable_default_action_item_badge_round, Color.parseColor("#99CC00"), Color.parseColor("#669900"))),
    PURPLE_ROUND("ROUND", new ABadgeStyleBean(R.drawable.drawable_default_action_item_badge_round, Color.parseColor("#AA66CC"), Color.parseColor("#9933CC"))),
    YELLOW_ROUND("ROUND", new ABadgeStyleBean(R.drawable.drawable_default_action_item_badge_round, Color.parseColor("#FFBB33"), Color.parseColor("#FF8800"))),

    GREY_ROUND_RECT("ROUND_RECT", new ABadgeStyleBean(R.drawable.drawable_default_action_item_badge_round_rect, Color.parseColor("#e0e0e0"), Color.parseColor("#c7c7c7"))),
    DARK_GREY_ROUND_RECT("ROUND_RECT", new ABadgeStyleBean(R.drawable.drawable_default_action_item_badge_round_rect, Color.parseColor("#606060"), Color.parseColor("#3e3e3e"))),
    RED_ROUND_RECT("ROUND_RECT", new ABadgeStyleBean(R.drawable.drawable_default_action_item_badge_round_rect, Color.parseColor("#FF4444"), Color.parseColor("#CC0000"))),
    BLUE_ROUND_RECT("ROUND_RECT", new ABadgeStyleBean(R.drawable.drawable_default_action_item_badge_round_rect, Color.parseColor("#33B5E5"), Color.parseColor("#0099CC"))),
    GREEN_ROUND_RECT("ROUND_RECT", new ABadgeStyleBean(R.drawable.drawable_default_action_item_badge_round_rect, Color.parseColor("#99CC00"), Color.parseColor("#669900"))),
    PURPLE_ROUND_RECT("ROUND_RECT", new ABadgeStyleBean(R.drawable.drawable_default_action_item_badge_round_rect, Color.parseColor("#AA66CC"), Color.parseColor("#9933CC"))),
    YELLOW_ROUND_RECT("ROUND_RECT", new ABadgeStyleBean(R.drawable.drawable_default_action_item_badge_round_rect, Color.parseColor("#FFBB33"), Color.parseColor("#FF8800")));


    private String tagName;
    private ABadgeStyleBean style;

    ABadgeStyleDefault(String tagName, ABadgeStyleBean style) {
        this.style = style;
        this.tagName = tagName;
    }

    public String getTagName() {
        return tagName;
    }

    public ABadgeStyleBean getStyle() {
        return style;
    }
    
}
