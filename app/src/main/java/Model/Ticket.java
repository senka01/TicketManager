package Model;

import android.support.annotation.Nullable;

import com.github.gfx.android.orma.SingleAssociation;
import com.github.gfx.android.orma.annotation.Column;
import com.github.gfx.android.orma.annotation.PrimaryKey;
import com.github.gfx.android.orma.annotation.Table;

import java.util.Date;

@Table
public class Ticket {

    @PrimaryKey(autoincrement = true)
    public long id;

    @Column
    @Nullable
    public int sellPrice;

    @Column
    @Nullable
    public SingleAssociation<Event> event;

    @Column
    @Nullable
    public boolean receivedFlg;

    @Column
    @Nullable
    public boolean paidFlg;

    @Column
    @Nullable
    public String seat;

    @Column
    @Nullable
    public int ticketNameId ;

    @Column
    @Nullable
    public SingleAssociation<User> opponent ;

    @Column
    @Nullable
    public int sellSite ;

    @Column
    public int number ;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Column
    @Nullable

    public String detail ;


    @Nullable
    public String getDetail() {
        return detail;
    }

    public void setDetail(@Nullable String detail) {
        this.detail = detail;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Nullable
    public int getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(@Nullable int sellPrice) {
        this.sellPrice = sellPrice;
    }

    @Nullable
    public boolean isReceivedFlg() {
        return receivedFlg;
    }

    public void setReceivedFlg(@Nullable boolean receivedFlg) {
        this.receivedFlg = receivedFlg;
    }

    @Nullable
    public boolean isPaidFlg() {
        return paidFlg;
    }

    public void setPaidFlg(@Nullable boolean paidFlg) {
        this.paidFlg = paidFlg;
    }

    @Nullable
    public String getSeat() {
        return seat;
    }

    public void setSeat(@Nullable String seat) {
        this.seat = seat;
    }

    @Nullable
    public int getTicketNameId() {
        return ticketNameId;
    }

    public void setTicketNameId(@Nullable int ticketNameId) {
        this.ticketNameId = ticketNameId;
    }

    @Nullable
    public int getSellSite() {
        return sellSite;
    }

    public void setSellSite(@Nullable int sellSite) {
        this.sellSite = sellSite;
    }
}
