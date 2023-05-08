package org.zerock.b01.domain;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Table(name = "persistent_logins")
public class PersistentLogins {

    @Id
    private String series;

    private String username;

    private String token;

    private Date lastUsed;
}
