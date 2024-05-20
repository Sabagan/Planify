package hellofx;

import java.sql.Date;

public class planData {

    private Integer planID;
    private String plan;
    private Date startDate;
    private Date endDate;
    private Date createdDate;
    private String status;
    private String planner;

    public planData(Integer planID, String plan, Date startDate, Date endDate, Date createdDate, String status, String planner) {
        this.planID = planID;
        this.plan = plan;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdDate = createdDate;
        this.status = status;
        this.planner = planner;
    }

    public Integer getPlanID() {
        return planID;
    }

    public void setPlanID(Integer planID) {
        this.planID = planID;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPlanner() {
        return planner;
    }

    public void setPlanner(String planner) {
        this.planner = planner;
    }

}
