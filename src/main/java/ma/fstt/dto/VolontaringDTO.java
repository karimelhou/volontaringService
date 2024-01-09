package ma.fstt.dto;

public class VolontaringDTO {
    private Long volontaringId;

    private String skill;
    private String availability;

    private Integer userId;

    public VolontaringDTO() {
    }

    public VolontaringDTO(Long volontaringId, String skill, String availability, Integer userId) {
        this.volontaringId = volontaringId;
        this.skill = skill;
        this.availability = availability;
        this.userId = userId;
    }

    public Long getVolontaringId() {
        return volontaringId;
    }

    public void setVolontaringId(Long volontaringId) {
        this.volontaringId = volontaringId;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
