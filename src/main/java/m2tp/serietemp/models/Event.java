package m2tp.serietemp.models;

import java.text.DateFormat;
import java.util.Date;

public class Event {

    private Long id;
    private Double record;
    private Long serieId;
    private Date moment = new Date();

    private String comment;

    public Date getMoment() {
        return moment;
    }

    public void setMoment(Date moment) {
        this.moment = moment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getRecord() {
        return record;
    }

    public void setRecord(Double record) {
        this.record = record;
    }

    public Long getSerieId() {
        return serieId;
    }

    public void setSerieId(Long serieId) {
        this.serieId = serieId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
