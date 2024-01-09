package ma.fstt.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "volontaring")
public class VolontaringEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long volontaringId;

    private String skill;
    private String availability;

    private Integer userId;

    public VolontaringEntity() {
    }

    public VolontaringEntity(Long volontaringId, String skill, String availability, Integer userId) {
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
