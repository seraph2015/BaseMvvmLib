package org.seraph.demo.data.db.table;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 搜索历史表
 * date：2017/3/3 10:30
 * author：xiongj
 * mail：417753393@qq.com
 **/
@Entity
public class SearchHistoryTable {

    /**
     * id
     */
    @Id(autoincrement = true)
    private Long _id;

    /**
     * 所属的用户id
     */
    @NotNull
    private int userId;

    /**
     * 搜索的类型标签(区分不同的地方)
     */
    private String type;

    /**
     * 搜索的键约束唯一
     */
    private String searchKey;

    /**
     * 搜索时间
     */
    private long searchTime;

    @Generated(hash = 965629725)
    public SearchHistoryTable(Long _id, int userId, String type, String searchKey,
            long searchTime) {
        this._id = _id;
        this.userId = userId;
        this.type = type;
        this.searchKey = searchKey;
        this.searchTime = searchTime;
    }

    @Generated(hash = 1689503406)
    public SearchHistoryTable() {
    }

    public Long get_id() {
        return this._id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSearchKey() {
        return this.searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public long getSearchTime() {
        return this.searchTime;
    }

    public void setSearchTime(long searchTime) {
        this.searchTime = searchTime;
    }


}
