package ice.server;

import Home.Heating.*;
import com.zeroc.Ice.Current;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ListIterator;

public class FurnaceI extends DeviceI implements Furnace {
    private final int minTemperature = 10;
    private final int maxTemperature = 40;
    private int actualTemperature = 20;
    private ArrayList<InformationAboutChangingTemperature> periods = new ArrayList<>();

    @Override
    public void setTemperature(int value, Current current) throws InvalidTemperature {
        System.out.println("Try set new temperature " + value);
        if (value < minTemperature || value > maxTemperature)
            throw new InvalidTemperature();
        actualTemperature = value;
        System.out.println("Temperature setted to " + actualTemperature);
    }

    private boolean checkDate(Date date) {
        if (date.minutes < 0 || date.minutes >= 60)
            return false;
        if (date.hour < 0 || date.hour >= 24)
            return false;
        if (date.year < 2000 || date.year >= 3000)
            return false;
        if (date.month < 1 || date.month > 12)
            return false;
        if (date.day < 1 || date.day > 31)
            return false;
        if (date.month == 2) {
            if (date.year % 4 == 0) {
                if (date.day > 29)
                    return false;
            }
            else {
                if (date.day > 28)
                    return false;
            }
        }
        if (date.month == 4 || date.month == 6 || date.month == 9 || date.month == 11)
            if (date.day > 30)
                return false;
        return true;
    }

    @Override
    public void addPeriod(Date start, Date end, int value, Current current) throws DayFromThePast, InvalidDate, InvalidTemperature {
        System.out.println("Try add new period");
        if (! compareDates(start, end))
            throw new InvalidDate();
        if (! checkDate(start))
            throw new InvalidDate();
        if (! checkDate(end))
            throw new InvalidDate();
        if (! checkDataWithPresent(start))
            throw new DayFromThePast();
        if (value < minTemperature || value > maxTemperature)
            throw new InvalidTemperature();
        System.out.println("Date is valid");

        ArrayList<InformationAboutChangingTemperature> tmp = new ArrayList<>();

        ListIterator<InformationAboutChangingTemperature> iter = periods.listIterator();
        while (iter.hasNext()) {
            InformationAboutChangingTemperature element = iter.next();
            if (compareDates(element.end, start) || compareDates(end, element.start)) continue;
            if (compareDates(start, element.start) && compareDates(element.end, end)) iter.remove();
            if (compareDates(element.start, start) && compareDates(end, element.end)) {
                Date oldEnd = element.end;
                element.end = start;
                tmp.add(new InformationAboutChangingTemperature(end, oldEnd, element.value));
                continue;
            }
            if (compareDates(element.start, start)) {
                element.end = start;
                continue;
            }
            if (compareDates(end, element.end)) {
                element.start = end;
                continue;
            }
        }
        periods.add(new InformationAboutChangingTemperature(start, end, value));
        for (InformationAboutChangingTemperature period : tmp)
            periods.add(period);

        System.out.println("New period added");
    }

    private boolean compareDates(Date start, Date end) {
        if (end.year > start.year)
            return true;
        if (end.year < start.year)
            return false;
        if (end.month > start.month)
            return true;
        if (end.month < start.month)
            return false;
        if (end.day > start.day)
            return true;
        if (end.day < start.day)
            return false;
        if (end.hour > start.hour)
            return true;
        if (end.hour < start.hour)
            return false;
        if (end.minutes > start.minutes)
            return true;
        return false;
    }

    private boolean checkDataWithPresent(Date date) {
        LocalDateTime now = LocalDateTime.now();
        Date current = new Date(now.getDayOfMonth(), now.getMonthValue(), now.getYear(), now.getHour(), now.getMinute());
        return compareDates(current, date);
    };

    @Override
    public InformationAboutChangingTemperature[] getAllPeriods(Current current) {
        System.out.println("Get all periods");
        return periods.stream().filter(e -> checkDataWithPresent(e.end)).toArray(InformationAboutChangingTemperature[]::new);
    }

    @Override
    public String getState(Current current) {
        System.out.println("GET STATE");
        return "Actual temperature is " + actualTemperature + ".";
    }
}
