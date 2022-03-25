// Java program for the above approach

import java.time.*;
import java.util.*;

class Info {
    LocalDate End_Vacc_Event, Start_Vacc_Event; // ช่วงเวลาการจัดการฉีดวัคซีน
    String sex;
    LocalDate Birth_Date, Service_Start_Date, Service_End_Date; // วันเกิดที่กรอก, วันที่เริ่มฉีดได้,
                                                                // วันที่สิ้นสุดการรับวัคซีน
    boolean Eligible_Flag;

    public Info(String sex, LocalDate Birth_Date, LocalDate Start_Vacc_Event, LocalDate End_Vacc_Event) {
        this.sex = sex;
        this.Birth_Date = Birth_Date;
        this.Start_Vacc_Event = Start_Vacc_Event;
        this.End_Vacc_Event = End_Vacc_Event;
        this.Eligible_Flag = false;
    }

    void Find_End_Vaccine_Event() {
        Period std = Period.between(Birth_Date, Start_Vacc_Event); // check age birthdate in start event
        Period ed = Period.between(Birth_Date, End_Vacc_Event); // check age birthdate in end event
        Period month_event = Period.between(Start_Vacc_Event, End_Vacc_Event); // find range event
        if (ed.toTotalMonths() < 6) { // check Age < 6 month
            System.out.println("You are 6 months in " + Birth_Date.plusMonths(6 - std.toTotalMonths()));
        } else if (std.getYears() >= 65) { // check Age > 65 Y
            Service_Start_Date = Start_Vacc_Event;
            Service_End_Date = End_Vacc_Event;

        } else if (std.getYears() > 2 && std.getYears() < 65) { // check Age>2Y and Age<65
            System.out.println("You are 65 in " + Birth_Date.plusYears(65));
        } else if (std.toTotalMonths() >= 6 || ed.toTotalMonths() >= 6) { // chek another
            for (int i = 0; i <= month_event.toTotalMonths(); i++) {
                Service_Start_Date = Start_Vacc_Event.plusMonths(i);
                System.out.println("Service start date has changed to " + Service_Start_Date + " and i is : " + i);
                Period aa = Period.between(Birth_Date, Service_Start_Date);
                System.out.println(aa);
                if (aa.getDays() >= 1)
                    aa = aa.plusMonths(1);
                if (aa.toTotalMonths() >= 6 || Service_Start_Date == End_Vacc_Event) {
                    if (std.toTotalMonths() <= 6)
                        Service_Start_Date = LocalDate.of(Service_Start_Date.getYear(),
                                Service_Start_Date.getMonth(), Birth_Date.getDayOfMonth());
                    break;
                }
            }
            if (Service_Start_Date != null)
                Eligible_Flag = true;

            for (int i = 0; i <= month_event.toTotalMonths(); i++) {
                Service_End_Date = Start_Vacc_Event.plusMonths(i);
                Period aa = Period.between(Birth_Date, Service_End_Date);
                if (aa.getYears() >= 2 || Service_End_Date == End_Vacc_Event) {
                    if (aa.getYears() >= 2)
                        System.out.println("getYears " + aa.getYears());
                    if (ed.toTotalMonths() == 6)
                        Service_End_Date = LocalDate.of(Service_End_Date.getYear(),
                                Service_End_Date.getMonth(), Birth_Date.getDayOfMonth());
                    break;
                } else if (ed.toTotalMonths() >= 6) {
                    if (Service_End_Date.getMonthValue() / 2 == 1)
                        Service_End_Date = LocalDate.of(Service_End_Date.getYear(),
                                Service_End_Date.getMonth(), 31);
                    else
                        Service_End_Date = LocalDate.of(Service_End_Date.getYear(),
                                Service_End_Date.getMonth(), 30);
                }
            }
        }
    }

    void print() {
        System.out.println("Your sex is : " + sex);
        System.out.println("Your service start is : " + Service_Start_Date);
        System.out.println("Your service End is : " + Service_End_Date);
        System.out.println("boolean is : " + Eligible_Flag);
    }
}

public class Covid_Vaccine_Event {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter your sex [M or FM] :> ");
        String sex = scan.nextLine();
        System.out.print("Enter your Birth Day [yyyy-MM-dd] :> ");
        String input = scan.nextLine(); // scan bithdate
        String[] result = input.split("-");
        Arrays.toString(result); // sprit date
        LocalDate Birth_Date = LocalDate.of(Integer.valueOf(result[0]), Integer.valueOf(result[1]),
                Integer.valueOf(result[2])); // set birthdate
        LocalDate Start_Vacc_Event = LocalDate.of(2564, 6, 1); // start vacciens Event
        LocalDate End_Vacc_Event = LocalDate.of(2564, 8, 31); // end vacciens Event
        Info info = new Info(sex, Birth_Date, Start_Vacc_Event, End_Vacc_Event);
        info.Find_End_Vaccine_Event();
        info.print();
    }
}
