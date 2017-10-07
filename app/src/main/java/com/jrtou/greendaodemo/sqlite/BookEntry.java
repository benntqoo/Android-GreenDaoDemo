package com.jrtou.greendaodemo.sqlite;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by jrtou on 10/7/17.
 */

@Entity//告訴 greendao 自動生成工具這是 schema
public class BookEntry {
    @Id(autoincrement = true)//設置 sqlite id 為自動增長
    private Long id;

    @Property(nameInDb = "BookName")//設置欄位 並賦予名稱
    @Index(unique = true)//設置 索引
    @NotNull//設置 不能為空
    private String bookName;

    @Property(nameInDb = "BookPrice")
    @NotNull
    private String bookPrice;

    @Property(nameInDb = "Memo")
    private String memo;

    @Generated(hash = 1605761728)
    public BookEntry(Long id, @NotNull String bookName, @NotNull String bookPrice,
            String memo) {
        this.id = id;
        this.bookName = bookName;
        this.bookPrice = bookPrice;
        this.memo = memo;
    }

    @Generated(hash = 137482324)
    public BookEntry() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBookName() {
        return this.bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookPrice() {
        return this.bookPrice;
    }

    public void setBookPrice(String bookPrice) {
        this.bookPrice = bookPrice;
    }

    public String getMemo() {
        return this.memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
