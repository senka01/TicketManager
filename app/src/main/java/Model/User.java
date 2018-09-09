package Model;

import android.support.annotation.Nullable;

import com.github.gfx.android.orma.annotation.Column;
import com.github.gfx.android.orma.annotation.PrimaryKey;
import com.github.gfx.android.orma.annotation.Table;

import java.util.Date;

@Table
public class User {

    @PrimaryKey(autoincrement = true)
    public long id;

    @Column(indexed = true)
    public String name;

    @Column
    @Nullable
    public String twitterId;

    @Column
    @Nullable
    public Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(@Nullable Date date) {
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @Nullable
    public String getTwitterId() {
        return twitterId;
    }

    public void setTwitterId(@Nullable String twitterId) {
        this.twitterId = twitterId;
    }

    public void setName(String name) {
        this.name = name;
    }


}
