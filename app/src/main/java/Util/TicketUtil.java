package Util;

import java.math.BigDecimal;

import Model.Ticket;

public class TicketUtil {

    public static int getPrice(Ticket ticket){
        int sellprice = ticket.getSellPrice();
        int number = ticket.getNumber();

        int sellsite = ticket.getSellSite();
        if(sellsite == 0){
            return sellprice * number;
        }

        int price = number * sellprice;
        double commission = SiteUtil.getCommissionPercent(sellsite);
        int tesuryo = getMultiplyResult(price,commission);
        return price - tesuryo;
    }

    public static int getMultiplyResult(int price,double commission){
        BigDecimal commissionBD = BigDecimal.valueOf(commission);
        BigDecimal tesuryo = commissionBD.multiply(BigDecimal.valueOf(price));
        return tesuryo.intValue();
    }

}
