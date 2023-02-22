package com.example.eims.dto.user;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
@Data
@NamedNativeQuery(name="GetUserDetail",
        query="CALL user_and_facility(?)",
        resultClass = UserDetailDTO.class)
@SqlResultSetMapping(
        name = "UserDetailMapping",
        classes = @ConstructorResult(
                targetClass = UserDetailDTO.class,
                columns = {@ColumnResult(name = "userId", type = Long.class),
                        @ColumnResult(name = "userRoleName", type = String.class),
                        @ColumnResult(name = "username", type = String.class),
                        @ColumnResult(name = "userDob", type = Date.class),
                        @ColumnResult(name = "userEmail", type = String.class),
                        @ColumnResult(name = "userSalary", type = Double.class),
                        @ColumnResult(name = "userAddress", type = String.class),
                        @ColumnResult(name = "userStatus", type = Boolean.class),
                        @ColumnResult(name = "facilityId", type = Long.class),
                        @ColumnResult(name = "facilityName", type = String.class),
                        @ColumnResult(name = "facilityAddress", type = String.class),
                        @ColumnResult(name = "facilityFoundDate", type = Date.class),
                        @ColumnResult(name = "hotline", type = String.class),
                        @ColumnResult(name = "facilityStatus", type = Boolean.class),
                        @ColumnResult(name = "subscriptionExpirationDate", type = Date.class),
                        @ColumnResult(name = "subscriptionId", type = Long.class),
                            }))

//@SqlResultSetMapping(name="UserDetailMapping",
//        entities=@EntityResult(
//                entityClass= UserDetailDTO.class,
//                fields={@FieldResult(name="userId", column="user_id"),
//                        @FieldResult(name="userRoleName", column="role_name"),
//                        @FieldResult(name="username", column="username"),
//                        @FieldResult(name="userDob", column="dob"),
//                        @FieldResult(name="userEmail", column="email"),
//                        @FieldResult(name="userSalary", column="salary"),
//                        @FieldResult(name="userAddress", column="address"),
//                        @FieldResult(name="userStatus", column="USER_STATUS"),
//                        @FieldResult(name="facilityId", column="facilityId"),
//                        @FieldResult(name="facilityName", column="facility_name"),
//                        @FieldResult(name="facilityAddress", column="facility_address"),
//                        @FieldResult(name="facilityFoundDate", column="facility_found_date"),
//                        @FieldResult(name="hotline", column="hotline"),
//                        @FieldResult(name="facilityStatus", column="FACILITY_STATUS"),
//                        @FieldResult(name="subscriptionExpirationDate", column="subscription_expiration_date"),
//                        @FieldResult(name="subscriptionId", column="subscription_id")
//                }
//        )
//)
@Entity
public class UserDetailDTO {
    // User account's detail
    @Id
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "role_name")
    private String userRoleName;
    private String username;
    @Column(name = "dob")
    private Date userDob;
    @Column(name = "email")
    private String userEmail;
    @Column(name = "salary")
    private Float userSalary;
    @Column(name = "address")
    private String userAddress;
    @Column(name = "USER_STATUS")
    private boolean userStatus;
    // Facility details
    @Column(name = "facility_id")
    private Long facilityId;
    @Column(name = "facilityName")
    private String facilityName;
    @Column(name = "facility_address")
    private String facilityAddress;
    @Column(name = "facility_found_date")
    private Date facilityFoundDate;
    private String hotline;
    @Column(name = "FACILITY_STATUS")
    private boolean facilityStatus;
    @Column(name = "subscription_expiration_date")
    private Date subscriptionExpirationDate;
    @Column(name = "subscription_id")
    private Long subscriptionId;

    public UserDetailDTO(Long userId, String userRoleName, String username, Date userDob, String userEmail,
                         Float userSalary, String userAddress, boolean userStatus, Long facilityId, String facilityName,
                         String facilityAddress, Date facilityFoundDate, String hotline, boolean facilityStatus,
                         Date subscriptionExpirationDate, Long subscriptionId) {
        this.userId = userId;
        this.userRoleName = userRoleName;
        this.username = username;
        this.userDob = userDob;
        this.userEmail = userEmail;
        this.userSalary = userSalary;
        this.userAddress = userAddress;
        this.userStatus = userStatus;
        this.facilityId = facilityId;
        this.facilityName = facilityName;
        this.facilityAddress = facilityAddress;
        this.facilityFoundDate = facilityFoundDate;
        this.hotline = hotline;
        this.facilityStatus = facilityStatus;
        this.subscriptionExpirationDate = subscriptionExpirationDate;
        this.subscriptionId = subscriptionId;
    }

    public UserDetailDTO() {
    }

}
