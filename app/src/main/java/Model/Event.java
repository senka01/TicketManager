package Model;

import android.support.annotation.Nullable;

import com.github.gfx.android.orma.annotation.Column;
import com.github.gfx.android.orma.annotation.PrimaryKey;
import com.github.gfx.android.orma.annotation.Table;

import java.util.Date;

@Table
public class Event {

    @PrimaryKey(autoincrement = true)
    public long id;

    @Column(indexed = true)
    public String name;

    @Column
    @Nullable
    public Date date;

    @Column
    @Nullable
    public int price;

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

    public void setName(String name) {
        this.name = name;
    }

    @Nullable
    public int getPrice() {
        return price;
    }

    public void setPrice(@Nullable int price) {
        this.price = price;
    }


}
