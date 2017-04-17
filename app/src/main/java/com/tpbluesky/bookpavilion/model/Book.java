package com.tpbluesky.bookpavilion.model;

import com.google.gson.annotations.Since;

/**
 * Created by tpbluesky on 2017/2/10.
 */

public class Book {

    public Book() {

    }

    //书籍ID
    public String id;

    //书籍名称
    public String name;

    //书籍图片
    public String image_src;

    //原价
    public String price_origin;

    //售价
    public String price_sell;

    //购价
    public String price_buy;

    //剩余库存
    public String stock_left;

    //最大库存
    public String stock_max;

    //简介
    public String introduce;

    //isbn
    public String isbn;

    //出版社
    public String publish;

    //作者
    public String author;

    //出版时间
    public String publish_date;

    //总页数
    public String page_num;

    //销量
    public String sale_num;

    //订购量(专为购物车数据)
    public String order_num;

}
