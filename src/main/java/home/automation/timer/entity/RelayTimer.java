package home.automation.timer.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Entity
@Table(name = "relay_timer")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor()
public class RelayTimer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    private String name;
    @Column(name = "begin_time")
    private LocalTime beginTime;
    @Column(name = "end_time")
    private LocalTime endTime;
    @Column(name = "power_consumption")
    private float powerConsumption;
    @Column(name = "periods_quantity")
    private int periodsQuantity;
    @Column(name = "heating_power")
    private int heatingPower;
    @Column(name = "controller_hostname")
    private String controllerHostname;
    @Transient
    @JsonIgnore
    private int[] enableAtSecondOfDay;

    public void init() {
        if (beginTime != null && endTime != null && periodsQuantity > 0) {
            enableAtSecondOfDay = new int[periodsQuantity];
            int beginTimeSeconds = beginTime.toSecondOfDay();
            int enabledTime = endTime.toSecondOfDay() - beginTimeSeconds;
            int period = enabledTime / periodsQuantity;
            int initSecond = beginTimeSeconds;
            for (int i = 0; i < periodsQuantity; i++) {
                enableAtSecondOfDay[i] = initSecond;
                initSecond += period;
            }
        }
    }

    @JsonIgnore
    public int[] getEnableAtSecondOfDay() {
        if (enableAtSecondOfDay == null) {
            init();
        }
        return enableAtSecondOfDay;
    }

    public Time getBeginTime() {
        return new Time(beginTime.getHour(), beginTime.getMinute(), beginTime.getSecond());
    }

    public Time getEndTime() {
        return new Time(endTime.getHour(), endTime.getMinute(), endTime.getSecond());
    }
}

@Data
class Time {
    public Time(int hour, int minute, int second) {
        this.minute = minute;
        this.hour = hour;
        this.second = second;
    }

    private int minute;
    private int hour;
    private int second;
}