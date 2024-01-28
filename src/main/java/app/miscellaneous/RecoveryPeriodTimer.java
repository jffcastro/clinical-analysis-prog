package app.miscellaneous;

import app.controller.App;
import app.controller.RecordVaccineAdministrationController;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class RecoveryPeriodTimer {

    public RecoveryPeriodTimer() {

    }

    private final RecordVaccineAdministrationController recordVaccineAdministrationController = new RecordVaccineAdministrationController();

    public void printUserRecoveryTimeSMS() {
        Timer timer = new Timer();

        int recoveryPeriod = App.getInstance().getRecoveryTime() * 60000;

       /* Calendar calendar = Calendar.getInstance();*/
       /* calendar.set(Calendar.HOUR_OF_DAY, LocalDateTime.now().getHour());*/
       /* calendar.set(Calendar.MINUTE, LocalDateTime.now().getMinute());*/
       /* calendar.set(Calendar.SECOND, LocalDateTime.now().getSecond() + (recoveryPeriod * 60));*/
       /* Date time = calendar.getTime();*/

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    recordVaccineAdministrationController.printRecoveryTime();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        timer.schedule(timerTask, recoveryPeriod);
    }
}
